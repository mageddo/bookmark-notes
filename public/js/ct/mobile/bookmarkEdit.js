function cb(editMode){

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

};cb($(".ctBookmarkNew").data("edit-mode"));
