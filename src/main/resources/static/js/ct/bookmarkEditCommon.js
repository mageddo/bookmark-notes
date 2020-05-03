function BookmarkEdition(items) {
  this.items = items = Object.assign(this.getItems(), items);
  this.ctx = {
    editMode: items.form.data('edit-mode')
  };
  $('body').off('click', '.painel-acoes li .toggle-scroll').on('click', '.painel-acoes li .toggle-scroll', function (e) {
    $(this).parent().toggleClass("active");
    $(this).parents(".mg-code").find('pre').toggleClass("with-scroll");
    e.preventDefault();
    e.stopPropagation();
  });
}

BookmarkEdition.prototype.setup = function () {

  var items = this.items, that = this;
  var ctx = this.ctx;

  items.btnCloseEditor.on('dblclick' + (mg.defaults.debug ? ' click' : ''), function () {
    console.debug('closing');
    items.modal.addClass("hidden");
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
          results: data.map(function (o) {
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
  i.mousedown(function () {
    this.hasClick = true;
  });
  i.focus(function (e) {
    if (!this.hasClick) {
      combo.select2("open");
    }
    this.hasClick = false;
  });
  i.attr("tabindex", '3');

  if (ctx.editMode) {
    items.btnVisualize.trigger("click");
    editionMode();
  }

  items.form.submit(function (e) {

    e.preventDefault();
    var submitBtn = items.form.find("*[type=submit]").prop("disabled", true);
    (ctx.editMode ? function (e) {

      e.preventDefault();
      $.ajax({
        url: "/api/bookmark",
        type: 'PUT',
        data: JSON.stringify(that.getFormData()),
        contentType: 'application/json',
        success: function () {
          successEvent(true);
          editionMode();
          console.debug("editado");
        }
      }).always(function () {
        submitBtn.prop("disabled", false);
      })
    } : function (e) {

      $.ajax({
        url: "/api/bookmark",
        type: 'POST',
        data: JSON.stringify(that.getFormData()),
        contentType: 'application/json',
        success: function (id) {
          console.debug("cadastrado");
          items.form.prop("id").value = id;
          successEvent(false);

          that.postCreate();
          editionMode();
        }
      }).always(function () {
        submitBtn.prop("disabled", false);
      })
    })(e)
  });

  function isHashClick(href) {
    let currentUriHashIndex = window.location.href.indexOf("#");
    if(currentUriHashIndex < 0){
      currentUriHashIndex = window.location.href.length;
    }
    return href.substring(currentUriHashIndex).startsWith('#');
  }

  items.preview.on('click', 'a:not(.skipped)', function (e) {
    if(isHashClick(this.href)){
      return ;
    }
    e.stopPropagation();
    this.target = "_blank";
  });

  function editionMode() {

    ctx.editMode = true;
    var formData = that.getFormData();

    if (items.iptVisible.prop("checked")) {
      items.btnLink.removeClass('hidden');
      items.btnLink.prop("href", '/bookmark/' + formData['id'] + '/' + (formData['name'].replace(/\s/g, '-')))
    } else {
      items.btnLink.addClass('hidden');
    }
  }

  function successEvent(editMode) {
    if (editMode)
      mg.notify.info('Bookmark salvo com sucesso');
    else
      mg.notify.success('Bookmark cadastrado com sucesso');

    refreshBookmarkList();
  }
};

BookmarkEdition.prototype.postCreate = function () {

};

BookmarkEdition.prototype.getFormData = function () {
  var data = this.items.form.serializeArray();
  data.push({
    name: "html",
    value: this.getEditorValue()
  });

  var jsonObject = {};
  data.forEach(function (it) {

    var prop;
    if (prop = jsonObject[it.name]) {
      if (!Array.isArray(prop)) {
        prop = jsonObject[it.name] = [prop];
      }
      prop.push(it.value)
    } else {
      jsonObject[it.name] = it.value;
    }
  });
  return jsonObject;
};

BookmarkEdition.prototype.getEditorValue = function () {
  return this.items.editor.val();
};

BookmarkEdition.prototype.getItems = function getItems() {
  return new function () {
    this.editor = $("#md-editor")
    this.preview = $(".editor-preview")
    this.form = $("#bookmarkForm")
    this.modal = $(".modal-editor")
    this.btnCloseEditor = this.modal.find(".btn-close-modal")
    this.linkContainer = this.modal.find(".link-container")
    this.btnLink = this.modal.find(".btn-public-link")
    this.iptVisible = this.form.find("#visible")
  };
};
