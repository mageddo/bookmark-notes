(function (items) {

  var edition = new BookmarkEdition(items), ctx = edition.ctx;
  items = edition.items;

  items.editorPanel = items.editor.markdownEditor({
    preview: true,
    theme: "ace/theme/tomorrow_night",
    onPreview: function (content, callback) {
      callback(parseMarkdown(content));
    }
  });

  items.preview = $(".md-preview");
  items.btnVisualize = items.editorPanel.find(".btn-preview");

  edition.getEditorValue = function () {
    console.debug('m=getEditorValue');
    return this.items.editor.markdownEditor('content');
  };

  edition.setup();

  items.editorPanel.editor.setOption("showInvisibles", true); // fixme customize this option
  let session = items.editorPanel.editor.getSession();
  session.setUseSoftTabs(mg.settings.CODE_STYLE_TAB_STYLE === "SPACES");
  session.setTabSize(mg.settings.CODE_STYLE_TAB_SIZE);
  items.editorPanel.focus(function () {
    items.editorPanel.editor.focus();
  });


})(new function () {
});
