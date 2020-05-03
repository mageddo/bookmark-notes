// if you change here maybe also want to change  src/controller/BookmarkController.js#306
let languagesMap = null;
let renderer = new marked.Renderer();

function parseMarkdown(content) {
  console.debug('m=parseCode, length=%s', content.length);
  initializeLanguagesMapCache();

  renderer.code = function (code, lang) {
    console.debug('onparse=begin, code=%s, lang=%s', code, lang);
    let parsedCode = languagesMap[lang] ? hljs.highlight(lang, code) : {value: code, plain: true}; // MG-40 hljs.highlightAuto(code) it is slow (1-2 seconds to parse)
    console.debug('onparse=parsed, parsedCode=%o, length=%s', parsedCode, parsedCode.value.split(/\r\n|\r|\n/).length)
    return Mustache.render($('#tplCodeBlock-2').html(), {
      lang: lang, code: parsedCode.value, plain: parsedCode.plain,
      overflown: mg.defaults.maxHeight / parsedCode.value.split(/\r\n|\r|\n/).length < 20,
      maxHeight: mg.defaults.maxHeight
    });
  };

  renderer.table = function (header, body) {
    return '<table class="table table-bordered table-striped">\n'
      + '<thead>\n'
      + header
      + '</thead>\n'
      + '<tbody>\n'
      + body
      + '</tbody>\n'
      + '</table>\n';
  };

  let parsedHtml = marked(content, {
    renderer: renderer
  });
  console.debug('parsing, ', parsedHtml);
  parsedHtml = prependTableOfContents(parsedHtml);
  return parsedHtml;
}

function initializeLanguagesMapCache() {
  if (languagesMap == null) {
    languagesMap = {};
    hljs.listLanguages().forEach(lang => languagesMap[lang] = true)
  }
}

function prependTableOfContents(html) {

  function findHtmlHeaders(html) {

    function getTagLevel(tagName ) {
      console.debug("m=getTagLevel, tagName=", tagName);
      return parseInt(/[\w](\d+)/.exec(tagName)[1]);
    }

    return $("<div/>")
      .html(html)
      .find("h1,h2,h3,h4,h5,h6")
      .map(function (i, o) {
        let item = {};
        let that = $(this);
        item.id = that.prop("id");
        item.tagName = that.prop("tagName");
        item.tagLevel = getTagLevel(item.tagName);
        item.text = that.text();
        return item;
      })
      .toArray();
  }

  let listTypeChar = '*';

  function indent(levels) {
    console.debug("levels=", levels);
    let buff = '';
    for (let i = 1; i < levels; i++) {
      buff += '  ';
    }
    return buff
  }

  let headersMarkdown = findHtmlHeaders(html)
    .map(function (it, i) {
      return indent(it.tagLevel) + listTypeChar + ' [' + it.text + '](#' + it.id + ')';
    })
    .join("\n")
  ;
  let headersHtml = marked(headersMarkdown, {
    renderer: renderer
  });
  return '<div class="collapse" id="toc-contents">\n'
    + '<a href="#toc-contents" data-toggle="collapse" aria-expanded="false" class="skipped"><i class="glyphicon glyphicon-list-alt"></i> Table Of Contents </a>\n'
    + headersHtml
    + '\n</div>'
    + '\n'
    + html
    ;
}

