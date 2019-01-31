$(function(){
	
	$(document).on('click','#detail',function(){
//	$(window).bind("load",function(){
		
		//フォームで送ったリクエストパラメータをシリアライズし、セッションストレージに保存する.
		var params = $('form').serialize();
		window.sessionStorage.setItem('search_param',params);
		var session_storage = sessionStorage.getItem('search_param');
	
		//セッションストレージからページ番号のリクエストパラメータを取得しクッキーへ保存する.
		var parameters = session_storage.split('&');
			
		var result = new Object();
			
			for(var i = 0 ; i < parameters.length; i++){
				
				var element = parameters[i].split('=');
				
				var paramName = decodeURIComponent(element[0]);
                var paramValue = decodeURIComponent(element[1]);
				
                result[paramName] = decodeURIComponent(paramValue);
                
			}
		var pageNum = result.pageNum;
		if(pageNum == null){
			var pageNum = 1;
		}
		$.removeCookie("pageNum",{ path:'/'});
		$.cookie("pageNum",pageNum, { path: '/' });
		
		//クリックした際のURLのパスを取得し、クッキーに保存する.
		$.removeCookie("path",{ path:'/'});
		var pathName = window.location.pathname;
		$.cookie("path",pathName,{ path: '/' });
		var prePath = $.cookie("path");
		
	});
	

});