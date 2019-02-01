$(function() {
	
	// 1つ前のページのパスにsearchItemが入る場合は、セッションストレージに保存した検索条件を取得し、リクエストパラメータに詰めてsearchItemに戻す.
	$("#back").on('click', function() {
		var prePath = $.cookie("path");
		

		if(prePath.match("/searchItem/search")){
			var session_storage = sessionStorage.getItem('search_param');
			window.location.href = '../searchItem/search?' + session_storage;
		}else{
			var pageNum = $.cookie("pageNum");
			if(pageNum.match("undefined")){
				alert("不明");
				pageNum = "1";
			}
			window.location.href = '../viewItemList/list?pageNum=' + pageNum;
		}
     // searchItemが入らない場合は、クッキーに保存したページ番号を取得し、リクエストパラメータに詰めてviewItemに戻す.
	});
});