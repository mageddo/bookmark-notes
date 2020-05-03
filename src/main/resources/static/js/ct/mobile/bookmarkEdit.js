(function (items) {

  var edition = new BookmarkEdition(items), ctx = edition.ctx;
  items = edition.items;

  edition.postCreate = function () {
    items.btnFullScreen.trigger("click");
  }

  items.btnTab.click(function (e) {
    e.preventDefault();
    mg.insertAtCaret(items.editor.get(0), '\t');
  });

  items.btnFullScreen.click(function () {
    $(".fields").slideToggle();
    $(this).toggleClass("active");
  })

  items.btnEditor.click(function () {

    items.btnEditor.addClass("active");
    items.btnVisualize.removeClass("active");

    items.editor.removeClass("hidden");
    items.preview.addClass("hidden");

  })

  items.btnVisualize.click(function () {

    items.btnEditor.removeClass("active");
    items.btnVisualize.addClass("active");

    items.editor.addClass("hidden");
    items.preview.removeClass("hidden").html(transformCodeBlocks(items.editor.val()))
  })

  items.btnLineWrap.click(function () {

    if (items.editor.hasClass("line-wrap-on")) {
      items.editor.removeClass("line-wrap-on")
      items.editor.addClass("line-wrap-off")
    } else {
      items.editor.removeClass("line-wrap-off")
      items.editor.addClass("line-wrap-on")
    }
    $(this).toggleClass("png-active");

  });

  edition.setup();
  if (ctx.editMode) {
    items.btnFullScreen.trigger("click");
  } else {
    console.log(items.editor);
    items.editor.trigger("focus");
  }


})(new function () {
  this.modal = $(".modal-editor")
  this.btnEditor = $(".btn-edit")
  this.btnVisualize = $(".btn-visualize")
  this.btnFullScreen = $(".btn-fullscreen")
  this.btnLineWrap = this.modal.find(".btn-linewrap")
  this.btnTab = this.modal.find(".btn-tab")
});
