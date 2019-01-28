$(function(){
	
	//URLにsearchItemが含まれる場合、submit先を検索用コントローラに変更する.
	$(window).bind("load",function(){
		if(document.URL.match("/searchItem")){
			$("#searchForm").attr("action","/searchItem/search");
		}
	});
})