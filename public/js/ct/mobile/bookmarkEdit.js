(function(items){

	var edition = new BookmarkEdition(), items = edition.items, ctx = edition.ctx;
	Object.assign(items, edition);

	items.btnTab.click(function(e){
		e.preventDefault();
		mg.insertAtCaret(items.editor.get(0), '\t');
	});

})(new function(){
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
});
