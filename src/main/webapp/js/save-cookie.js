$(function(){
	
	$("#search").on('click',function(){
		var path = location.pathname;
		$.cookie("path",path, { path:'/' });
		alert($.cookie("path"));
	});

});