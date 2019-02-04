$(function(){
	
	$(document).on('click','#detail',function(){
		
		//フォームで送ったリクエストパラメータをシリアライズし、セッションストレージに保存する.
		var params = $('form').serialize();
		window.sessionStorage.setItem('search_param',params);
		var session_storage = sessionStorage.getItem('search_param');
	
		//セッションストレージからページ番号のリクエストパラメータを取得しクッキーへ保存する.
		var pageNum = session_storage.match(/pageNum=(.*?)(&|$)/)[1];
		if(pageNum == null){
			var pageNum = 1;
		}
		$.removeCookie("pageNum",{ path: '/' });
		$.cookie("pageNum",pageNum, { path: '/' });
		
		//クリックした際のURLのパスを取得し、クッキーに保存する.
		$.removeCookie("path",{ path: '/' });
		var pathName = location.pathname;
		$.cookie("path",pathName,{ path: '/' });
	});
	

});