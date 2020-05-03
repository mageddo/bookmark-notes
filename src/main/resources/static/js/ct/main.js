// if you change here maybe also want to change  src/controller/BookmarkController.js#306
let languagesMap = null;

function transformCodeBlocks(content) {

  console.debug('m=parseCode, length=%s', content.length)
  if (languagesMap == null) {
    languagesMap = {};
    hljs.listLanguages().forEach(lang => languagesMap[lang] = true)
  }
  let renderer = new marked.Renderer();
  renderer.code = function (code, lang) {
    console.debug('onparse=begin, code=%s, lang=%s', code, lang)
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

  console.debug('parsing, ', marked(content, {renderer: renderer}));
  return marked(content, {
    renderer: renderer
  })
}
