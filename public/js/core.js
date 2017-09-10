(function(window, document, $){

	mg.notify = {
		info: function(msg, opt){
			var st = {
				message: msg,
				type: "info"
			};
			$.extend(st, opt);
			return $.notify(st, st);
		},
		warn: function(msg, opt){
			var st = {
				message: msg,
				type: "warning"
			};
			$.extend(st, opt);
			return $.notify(st, st);
		},
		error: function(msg, opt){
			var st = {
				message: msg,
				type: "danger"
			};
			$.extend(st, opt);
			return $.notify(st, st);
		},
		success: function(msg, opt){
			var st = {
				message: msg,
				type: "success"
			};
			$.extend(st, opt);
			return $.notify(st, st);
		}
	};
	$.notifyDefaults({
		mouse_over: 'pause',
		z_index: 5000,
		delay: 5000,
		placement: {
			from: "bottom",
			align: "right"
		},
		animate: {
			enter: 'animated fadeInRight',
			exit: 'animated fadeOutRight'
		}

	})

	/**
	 * adiciona programaticamente os items no select2
	 */
	$.fn.addSelect2Items = function(items, config){
			var that = this;
			that.select2("destroy");
			for(var k in items){
					var data = items[k];
					that.append("<option value='"+ data.id +"' selected >" + data.text + "</option>");
			}
			that.select2(config || {});
	};

	mg.error = function(message, title){
		mg.notify.error(message);
	}

	mg.popUpScreen = function(url, options,  container){
		container = container || ".popuparea";
		options = options || {};
		options.url = url;
		$.ajax(options).done(function(data){
			$(container)
				.html(data)
				.modal("show");
		}).error(function(error){
			// TODO EFS fazer isso buscar de um template
			$(container).html("Error on load " + url + "<br/>" + error);
		});
	}
	mg.clearSelection = function() {
		if(document.selection && document.selection.empty) {
			document.selection.empty();
		} else if(window.getSelection) {
			var sel = window.getSelection();
			sel.removeAllRanges();
		}
	}

	mg.delay = (function(){
		var timer = 0;
		return function(callback, ms){
			clearTimeout (timer);
			timer = setTimeout(callback, ms);
		};
	})();

	mg.modal = {
		modal: function(){
			return $('#modal');
		},
		close: function(cCallback){
			console.debug('m=modal-close', this, arguments);
			this.modal().on('hidden.bs.modal', callback);
			function callback(e) {
				console.debug('m=modal-close', this, arguments);
				mg.modal.modal().off('hidden.bs.modal', callback);
				cCallback.call(this, e);
			}
		}
	};

	mg.insertAtCaret = function(selec, text) {
		var txtarea = $(selec).get(0);
		if (!txtarea) { return; }
		console.debug('m=insertAtCaret, status=begin');
		var scrollPos = txtarea.scrollTop;
		var strPos = 0;
		var br = ((txtarea.selectionStart || txtarea.selectionStart == '0') ?
			"ff" : (document.selection ? "ie" : false ) );
		if (br == "ie") {
			txtarea.focus();
			var range = document.selection.createRange();
			range.moveStart ('character', -txtarea.value.length);
			strPos = range.text.length;
		} else if (br == "ff") {
			strPos = txtarea.selectionStart;
		}

		var front = (txtarea.value).substring(0, strPos);
		var back = (txtarea.value).substring(strPos, txtarea.value.length);
		txtarea.value = front + text + back;
		strPos = strPos + text.length;
		if (br == "ie") {
			txtarea.focus();
			var ieRange = document.selection.createRange();
			ieRange.moveStart ('character', -txtarea.value.length);
			ieRange.moveStart ('character', strPos);
			ieRange.moveEnd ('character', 0);
			ieRange.select();
		} else if (br == "ff") {
			txtarea.selectionStart = strPos;
			txtarea.selectionEnd = strPos;
			txtarea.focus();
		}

		txtarea.scrollTop = scrollPos;
	}

	mg.isMobile = /(android|bb\d+|meego).+mobile|avantgo|bada\/|blackberry|blazer|compal|elaine|fennec|hiptop|iemobile|ip(hone|od)|ipad|iris|kindle|Android|Silk|lge |maemo|midp|mmp|netfront|opera m(ob|in)i|palm( os)?|phone|p(ixi|re)\/|plucker|pocket|psp|series(4|6)0|symbian|treo|up\.(browser|link)|vodafone|wap|windows (ce|phone)|xda|xiino/i.test(navigator.userAgent)
			|| /1207|6310|6590|3gso|4thp|50[1-6]i|770s|802s|a wa|abac|ac(er|oo|s\-)|ai(ko|rn)|al(av|ca|co)|amoi|an(ex|ny|yw)|aptu|ar(ch|go)|as(te|us)|attw|au(di|\-m|r |s )|avan|be(ck|ll|nq)|bi(lb|rd)|bl(ac|az)|br(e|v)w|bumb|bw\-(n|u)|c55\/|capi|ccwa|cdm\-|cell|chtm|cldc|cmd\-|co(mp|nd)|craw|da(it|ll|ng)|dbte|dc\-s|devi|dica|dmob|do(c|p)o|ds(12|\-d)|el(49|ai)|em(l2|ul)|er(ic|k0)|esl8|ez([4-7]0|os|wa|ze)|fetc|fly(\-|_)|g1 u|g560|gene|gf\-5|g\-mo|go(\.w|od)|gr(ad|un)|haie|hcit|hd\-(m|p|t)|hei\-|hi(pt|ta)|hp( i|ip)|hs\-c|ht(c(\-| |_|a|g|p|s|t)|tp)|hu(aw|tc)|i\-(20|go|ma)|i230|iac( |\-|\/)|ibro|idea|ig01|ikom|im1k|inno|ipaq|iris|ja(t|v)a|jbro|jemu|jigs|kddi|keji|kgt( |\/)|klon|kpt |kwc\-|kyo(c|k)|le(no|xi)|lg( g|\/(k|l|u)|50|54|\-[a-w])|libw|lynx|m1\-w|m3ga|m50\/|ma(te|ui|xo)|mc(01|21|ca)|m\-cr|me(rc|ri)|mi(o8|oa|ts)|mmef|mo(01|02|bi|de|do|t(\-| |o|v)|zz)|mt(50|p1|v )|mwbp|mywa|n10[0-2]|n20[2-3]|n30(0|2)|n50(0|2|5)|n7(0(0|1)|10)|ne((c|m)\-|on|tf|wf|wg|wt)|nok(6|i)|nzph|o2im|op(ti|wv)|oran|owg1|p800|pan(a|d|t)|pdxg|pg(13|\-([1-8]|c))|phil|pire|pl(ay|uc)|pn\-2|po(ck|rt|se)|prox|psio|pt\-g|qa\-a|qc(07|12|21|32|60|\-[2-7]|i\-)|qtek|r380|r600|raks|rim9|ro(ve|zo)|s55\/|sa(ge|ma|mm|ms|ny|va)|sc(01|h\-|oo|p\-)|sdk\/|se(c(\-|0|1)|47|mc|nd|ri)|sgh\-|shar|sie(\-|m)|sk\-0|sl(45|id)|sm(al|ar|b3|it|t5)|so(ft|ny)|sp(01|h\-|v\-|v )|sy(01|mb)|t2(18|50)|t6(00|10|18)|ta(gt|lk)|tcl\-|tdg\-|tel(i|m)|tim\-|t\-mo|to(pl|sh)|ts(70|m\-|m3|m5)|tx\-9|up(\.b|g1|si)|utst|v400|v750|veri|vi(rg|te)|vk(40|5[0-3]|\-v)|vm40|voda|vulc|vx(52|53|60|61|70|80|81|83|85|98)|w3c(\-| )|webc|whit|wi(g |nc|nw)|wmlb|wonu|x700|yas\-|your|zeto|zte\-/i.test(navigator.userAgent.substr(0,4));

	$.fn.overflown = function(){
			var e = this[0];
			return e.scrollHeight > e.clientHeight || e.scrollWidth > e.clientWidth;
	}
	$.fn.selectRange = function(start, end) {
		if (end === undefined) {
			end = start;
		}
		return this.each(function() {
			if ('selectionStart' in this) {
				this.selectionStart = start;
				this.selectionEnd = end;
			} else if (this.setSelectionRange) {
				this.setSelectionRange(start, end);
			} else if (this.createTextRange) {
				var range = this.createTextRange();
				range.collapse(true);
				range.moveEnd('character', end);
				range.moveStart('character', start);
				range.select();
			}
		});
	};

	mg.cancelAjax = (ajaxReq) => {
		console.debug('m=cancelAjax, req=%o', ajaxReq);
		if(ajaxReq){
			ajaxReq.abort();
		}
	}

	/**
	 * Make a ajax request and allow only just request per key.
	 *
	 * ex: ajax('a', {url: '/abc'}); ajax({key: 'a', url: '/abc'})
	 *
	 */
	var requests = {};
	mg.ajax = function(){

		var key, opts;
		if (arguments.length == 1) {
			opts = arguments['0']
			key = opts.key;
		} else {
			key = arguments['0']
			opts = arguments['1']
		}

		console.debug('m=ajax, key=%s, opts=%o, foundKey=%o', key, opts, requests[key]);

		// cancel request with that key
		mg.cancelAjax(requests[key]);

		// new request
		requests[key] = $.ajax(opts);

	}

})(window, document, jQuery);
