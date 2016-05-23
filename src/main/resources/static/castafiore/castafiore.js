var requestId = 0;
var loadingTimeout;
var loadingActive = -1;

jQuery.getCSS = function(url) {
	jQuery(document.createElement('link')).attr({
		href : url,
		media : 'screen',
		type : 'text/css',
		rel : 'stylesheet'
	}).appendTo('head');
};

$.fn.castafiore = function(app, params) {
	var me = $(this);
	me.append("<div id='root_" + app + "'>");
	me.append("<div id='script_" + app + "'>");
	// $("#script_" + app).mask('Please wait....');
	var url = "castafiore/ui/?casta_applicationid=" + app;
	var curUrl = window.location.href;
	var p = curUrl.split('?')[1];
	if (p) {
		url = url + '&' + p;
	}

	if (!params) {
		params = {};
	}

	$.ajax({
		url : url,
		type : 'POST',
		dataType : 'text',
		data : params,
		success : function(data) {
			$("#script_" + app).html(data);
		}
	});
};

function loading() {
	if (loadingActive == -1) {
		// loadingTimeout = setTimeout('$.blockUI({message:"<h3
		// class=\'ui-widget-header\'>Please wait...</h3>"})', 1000);
		loadingTimeout = setTimeout('$(".loader").fadeIn(100)', 10);
		loadingActive = 1;
	}
}

function hideloading() {
	$(".loader").fadeOut(100);
	if (loadingActive == 1) {
		loadingActive = -1;
		if (loadingTimeout)
			clearTimeout(loadingTimeout);
	}
}

function getParameterByName(name, url) {
	if (!url)
		url = window.location.href;
	name = name.replace(/[\[\]]/g, "\\$&");
	var regex = new RegExp("[?&]" + name + "(=([^&#]*)|&|#|$)"), results = regex
			.exec(url);
	if (!results)
		return null;
	if (!results[2])
		return '';
	return decodeURIComponent(results[2].replace(/\+/g, " "));
}

(function($) {

	$.fn.select = function(url) {

		var me = $(this);
		$.get(url, function() {
			
			
			//alert("success");
		}).done(function(data_) {
			var data = JSON.parse(data_);
			var i;
			for(i in data){
				var line = data[i];
				
				me.append($('<option></option>').attr('value', line['key']).html(line['value']));
			}
			
			//alert("second success");
		}).fail(function() {
			alert("error");
		}).always(function() {
			//alert("finished");
		});

	};

}(jQuery));

function sCall(params) {
	loading();
	$('textarea').each(function(i) {
		try {
			var ed = CKEDITOR.instances[$(this).attr('id')];
			if (ed != undefined) {
				params["casta_value_" + $(this).attr("id")] = ed.getData();
			}
		} catch (err) {
			// alert(err);
		}
	});
	$(':checkbox').each(function(i) {
		if ($(this).is(':checked')) {
			params["casta_value_" + $(this).attr("id")] = "checked";
		} else {
			params["casta_value_" + $(this).attr("id")] = "";
		}

	});
	$(" *[stf]").each(function(i) {
		var id = $(this).attr("id");
		var valueKey = "casta_value_" + id;
		if (params[valueKey] == undefined) {
			var value = $(this).prop("value");
			if (value == undefined || value == '') {
				value = $(this).val();
			}
			params[valueKey] = value;
		}
	});
	params.requestId = requestId++;
	$.post("castafiore/ui", params, function(data) {
		$("#script_" + params['casta_applicationid']).append(data);
	}, "text");
}