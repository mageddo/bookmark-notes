(function(items){

	var edition = new BookmarkEdition(items), ctx = edition.ctx;
	items = edition.items;

	items.editorPanel = items.editor.markdownEditor({
		preview: true,
		theme: "ace/theme/tomorrow_night",
		onPreview: function (content, callback) {
			callback(parseCode(content, callback));
			$(".md-preview a").not('.skipped').click(function(e){
				e.stopPropagation();
				this.target = "_blank";
			});
		}
	});

	items.btnVisualize = items.editorPanel.find(".btn-preview");

	edition.getEditorValue = function(){
		console.debug('m=getEditorValue');
		return this.items.editor.markdownEditor('content');
	}

	edition.setup();

	var session = items.editorPanel.editor.getSession();
	session.setUseSoftTabs(false);
	session.setTabSize(2);
	items.editorPanel.focus(function(){
		items.editorPanel.editor.focus();
	});


})(new function(){
	this.btnEditor = $(".btn-edit")
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
