function cb(editMode){

	var isMobile = mg.isMobile,
		st = getSt(editMode),
		editorEl = $("#txtDescription"),
		combo = $(".js-data-example-ajax"),
		frm = $("#frmBookmarkEdit").submit(function(e){
			st.save.call(this, e);
		});

	universal();
	if(isMobile){
		mobile();
	}else{
		web();
	}
	var originalData = JSON.stringify(getFormData());

	function web(){

		var ed = editorEl.markdownEditor({
				preview: true,
				theme: "ace/theme/tomorrow_night",
				onPreview: function (content, callback) {
					callback(parseCode(content, callback));
					$(".md-preview a").not('.skipped').click(function(e){
						e.stopPropagation();
						this.target = "_blank";
					});
				}
			}),
		session = ed.editor.getSession();
		session.setUseSoftTabs(false);
		session.setTabSize(2);
		ed.focus(function(){
			ed.editor.focus();
		});
		if(editMode){
			ed.find(".btn-preview").trigger("click");
		}
	}

	function mobile(){
		var text = editorEl.text();
		editorEl.empty();
		editorEl.append(getMobileEditor("text-editor", "editor-preview", "btn-visualize", "btn-edit", "btn-fullscren", text, false));

		var editorMb = $(".mobile-full-screen-modal");
		editorMb.html(getMobileEditor("editor-mb", "editor-preview", "btn-visualize-mb", "btn-edit-mb", "btn-fullscren-mb", text, true));

		$("#text-editor").height(($(window).height() - $(".header-editor-container").height()) + "px");

		$(".btn-fullscren").click(function(){
			editorMb.show();
			window.modalCloseClick = true;
			$('#modal').modal('toggle');
		});
		$(".btn-fullscren-mb").click(function(){
			$("#text-editor").val($("#editor-mb").val());
			$('#modal').modal('toggle');
			editorMb.hide();
		});
		$('.btn-bk-save').click(function(e){
			var mbText = $("#editor-mb").val();
			$("#text-editor").val(mbText);
			st.save.call(this, e);
		});
	}

	function universal(){

		$("body").on('click', '.close', function(){
			window.modalCloseClick = true;
		});

		combo.select2({
			tags: true, tokenSeparators: [',', '\t', '	'],
			ajax: {
				url: "/api/tag/search",
				dataType: 'json',
				delay: 340,
				data: function (params) {
					return {
						q: params.term,
						page: params.page
					};
				},
				processResults: function (data, page) {
					return {
						results: data.map(function(o){
							o.text = o.name;
							o.id = o.name;
							return o;
						})
					};
				},
				cache: true
			}
		});
		combo.val(tagsToEdit).trigger("change");
		var i = combo.next().find(".select2-selection ul");
		i.mousedown(function(){
			this.hasClick = true;
		});
		i.focus(function(e){
			if(!this.hasClick){
				combo.select2("open");
			}
			this.hasClick = false;
		});
		i.attr("tabindex", '3');

		setTimeout(function(){
			$(".btn-fullscren").trigger("click");
			$('#editor-mb').trigger('focus').selectRange(0).scrollTop(0);
		}, 200);

		mg.modal.close(function(){
			console.debug('m=modal-close-cb, modalCloseClicked=%s', window.modalCloseClick);
			if(!window.modalCloseClick){
				if((!editMode && getEditorValue())){
					frm.trigger("submit");
				}else if(editMode){
					if(originalData != JSON.stringify(getFormData())){
						console.debug('m=save-trigger, status=different-values, original=%s, current=%s', originalData, JSON.stringify(getFormData()));
						frm.trigger("submit");
					}
				}
			}
			window.modalCloseClick = false;
		});

	}

	/*
		Mount mobile painel
	*/
	function getMobileEditor(editorId, editorPreviewSlc, btnVisualizeSlc, btnEditSlc, btnFullScreenSlc, text, fullscreen){
		var editorPanel = $('<div class="editor-panel" />'),
				btnEditorPanel = $('<div class="btn-editor-panel" />');

		editorPanel.append(btnEditorPanel);
		btnEditorPanel.append('<a class="' + btnEditSlc + '  btn btn-default btn-edit-common glyphicon glyphicon-pencil active" href="#"></a>');
		btnEditorPanel.append('&nbsp;');
		btnEditorPanel.append('<a class="'+ btnVisualizeSlc +'  btn btn-default btn-visualize-common glyphicon glyphicon-eye-open" href="#"></a>');
		btnEditorPanel.append('&nbsp;');
		btnEditorPanel.append('<a class=" btn-tab  btn btn-default glyphicon glyphicon-indent-left" href="#"></a>');
		btnEditorPanel.append('&nbsp;');
		btnEditorPanel.append('<a class="'+ btnFullScreenSlc +' btn-fullscren-common  btn btn-default glyphicon glyphicon-fullscreen" href="#"></a>');
		if(fullscreen){
			btnEditorPanel.append('<a class="btn-bk-save btn btn-success glyphicon glyphicon-floppy-save pull-right" href="#"></a>');
		}
		editorPanel.append('<textarea id="'+ editorId  +'" class="editor-common"></textarea><div class="'+ editorPreviewSlc +'"></div>');

		var editor = editorPanel.find("#" + editorId);
		var btnVisualize = btnEditorPanel.find("." + btnVisualizeSlc);
		var btnEdit = btnEditorPanel.find("." + btnEditSlc);


		editor.val(text);

		btnEdit.click(function(){
			$(".btn-edit-common").addClass("active");
			$(".btn-visualize-common").removeClass("active");
			$(".editor-common").show();
			$("." + editorPreviewSlc).hide();
		}).trigger("click");

		btnVisualize.click(function(){
			$(".btn-edit-common").removeClass("active");
			$(".btn-visualize-common").addClass("active");
			$("." + editorPreviewSlc).html(parseCode(editor.val()));

			$(".editor-common").hide();
			$("." + editorPreviewSlc).show();
		});

		btnEditorPanel.find('.btn-fullscren-common').bind('click', function(){
			$(".btn-fullscren-common").toggleClass("active");
			$("." + editorPreviewSlc).html(parseCode(editor.val()));
			$(".editor-common").val(editor.val());
		});

		btnEditorPanel.find('.btn-tab').click(function(e){
		e.preventDefault();
			mg.insertAtCaret('#' + editorId, '\t');
		})

		return editorPanel;
	}


	function getFormData(){
		var data = $("#frmBookmarkEdit").serializeArray();
		data.push({
			name: "html",
			value: getEditorValue()
		});
		return data;
	}
	function getEditorValue(){
		var mobileValue = $("#text-editor").val(), value = $("#txtDescription").markdownEditor('content');
		console.debug('m=getEditorValue, isMobile=%s', isMobile);
		if(isMobile){
			return mobileValue;
		}
		return value;
	}
	function setEditorValue(val){
		return $("#txtDescription").markdownEditor('setContent', val);
	}
	function successEvent(editMode){
		if(editMode)
			mg.notify.info('Bookmark salvo com sucesso');
		else
			mg.notify.success('Bookmark cadastrado com sucesso');

		refreshBookmarkList();
	}
	function getSt(editMode){
		var title = mg.modal.modal().find(".title");
		if(editMode){
			title.html("Edit");
		}else{
			title.html("New");
		}

		return editMode ? {
			save: function(e){
				e.preventDefault();
				$.ajax({
					url: "/api/bookmark",
					type: 'POST',
					data: getFormData(),
					success: function () {
						successEvent(1);
						console.debug("editado");
						originalData = JSON.stringify(getFormData());
					}
				})
			}
			} : {
			save: function(e){
				e.preventDefault();
				$.ajax({
					url: "/api/bookmark",
					type: 'PUT',
					data: getFormData(),
					success: function (id) {
						st = getSt(editMode = true);
						console.debug("cadastrado");
						frm.prop("id").value = id;
						successEvent();
						originalData = JSON.stringify(getFormData());
					}
				})
			}
		};
	};

	$('body').off('click', '.painel-acoes li .toggle-scroll').on('click', '.painel-acoes li .toggle-scroll', function(e){
		$(this).parent().toggleClass("active");
		$(this).parents(".mg-code").find('pre').toggleClass("with-scroll");
		e.preventDefault();
		e.stopPropagation();
	});

var languagesMap = null;
function parseCode(content){
	if(languagesMap == null){
		languagesMap = {};
		hljs.listLanguages().forEach(lang => languagesMap[lang] = true)
	}
	var renderer = new marked.Renderer();
	renderer.code = function(code, lang){
		var parsedCode = languagesMap[lang] ? hljs.highlight(lang, code) : hljs.highlightAuto(code);
		return Mustache.render($('#tplCodeBlock').html(), {lang: lang, code: parsedCode.value, overflown: parsedCode.value.split(/\r\n|\r|\n/).length > 7 });
	};
	return marked(content, {
		renderer: renderer
	})
}

};cb($(".ctBookmarkNew").data("edit-mode"));
