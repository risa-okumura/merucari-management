$(function(){
	$("#detail").on('click',function(){
		var path = location.pathname;
		$.cookie("path",path,{ path: '/' });
		console.log($.cookie("path"))
	});
});