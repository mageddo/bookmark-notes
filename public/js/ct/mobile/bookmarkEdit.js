(function(){


	/**
	 * Events
	 */
	var items = new function(){
		this.btnEditor = $(".btn-edit")
		this.btnVisualize = $(".btn-visualize")
		this.btnFullScreen = $(".btn-fullscreen")
		this.editor = $("#md-editor")
		this.preview = $(".editor-preview")
		this.form = $("#bookmarkForm")
		this.modal = $(".modal-editor")
		this.btnCloseEditor = this.modal.find(".btn-close-modal")
		this.linkContainer = this.modal.find(".link-container")
		this.btnLink = this.modal.find(".btn-public-link")
		this.iptVisible = this.form.find("#visible")
		this.btnLineWrap = this.modal.find(".btn-linewrap")
		this.btnTab = this.modal.find(".btn-tab")
	}

	var ctx = {
		editMode: items.form.data('edit-mode')
	};

	items.btnFullScreen.click(function(){
		$(".fields").slideToggle();
		$(this).toggleClass("active");
	})

	items.btnCloseEditor.click(function(){
		items.modal.addClass("hidden");
	})

	items.btnEditor.click(function(){

		items.btnEditor.addClass("active");
		items.btnVisualize.removeClass("active");

		items.editor.removeClass("hidden");
		items.preview.addClass("hidden");

	})

	items.btnVisualize.click(function(){

		items.btnEditor.removeClass("active");
		items.btnVisualize.addClass("active");

		items.editor.addClass("hidden");
		items.preview.removeClass("hidden").html(parseCode(items.editor.val()))
	})

	items.btnLineWrap.click(function(){

		if(items.editor.hasClass("line-wrap-on")){
			items.editor.removeClass("line-wrap-on")
			items.editor.addClass("line-wrap-off")
		}else{
			items.editor.removeClass("line-wrap-off")
			items.editor.addClass("line-wrap-on")
		}
		$(this).toggleClass("png-active");

	});

	items.btnTab.click(function(e){
		e.preventDefault();
		mg.insertAtCaret(items.editor.get(0), '\t');
	});

	// tag search
	var combo = $(".js-data-example-ajax")
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
	})

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

	if(ctx.editMode){
		items.btnFullScreen.trigger("click");
		editionMode();
	}

	items.form.submit(function(e){

		e.preventDefault();
		var submitBtn = items.form.find("*[type=submit]").prop("disabled", true);
		(ctx.editMode ? function(e){

			e.preventDefault();
			$.ajax({
				url: "/api/bookmark",
				type: 'POST',
				data: getFormData(),
				success: function () {
					successEvent(true);
					editionMode();
					console.debug("editado");
				}
			}).always(function(){
				submitBtn.prop("disabled", false);
			})
		} : function(e){

			$.ajax({
				url: "/api/bookmark",
				type: 'PUT',
				data: getFormData(),
				success: function (id) {
					console.debug("cadastrado");
					items.form.prop("id").value = id;
					successEvent(false);

					items.btnFullScreen.trigger("click");
					editionMode();
				}
			}).always(function(){
				submitBtn.prop("disabled", false);
			})
		})(e)
	});

	function editionMode(){

		ctx.editMode = true;
		var formData = getFormData();


		if(items.iptVisible.prop("checked")){
			items.btnLink.removeClass('hidden');
			items.btnLink.prop("href", '/bookmark/' + formData[0].value + '/' + (formData[1].value.replace(/\s/g, '-')))
		}
	}

	function successEvent(editMode){
		if(editMode)
			mg.notify.info('Bookmark salvo com sucesso');
		else
			mg.notify.success('Bookmark cadastrado com sucesso');

		refreshBookmarkList();
	}

	function getFormData(){
		var data = items.form.serializeArray();
		data.push({
			name: "html",
			value: getEditorValue()
		});
		return data;
	}

	function getEditorValue(){
		return items.editor.val();
	}


})();
