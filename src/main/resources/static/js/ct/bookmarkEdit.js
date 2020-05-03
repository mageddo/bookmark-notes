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

  var session = items.editorPanel.editor.getSession();
  session.setUseSoftTabs(false);
  session.setTabSize(2);
  items.editorPanel.focus(function () {
    items.editorPanel.editor.focus();
  });


})(new function () {
});
