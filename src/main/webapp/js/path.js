$(function(){
	$("#detail").on('click',function(){
		var path = location.pathname;
		$.cookie("path",path,{ path: '/' });
		var prePath = $.cookie("path");
	});
});