function cb(editMode){

	/**
	 * Events
	 */
	 var items = {
		btnEditor: $(".btn-edit"),
		btnVisualize: $(".btn-visualize"),
		editor: $("#md-editor"),
		preview: $(".editor-preview")
	 }

	$(".btn-fullscreen").click(function(){
		$(".fields").slideToggle();
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

};cb($(".ctBookmarkNew").data("edit-mode"));
