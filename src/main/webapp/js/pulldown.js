$(function() {
	
	//ページを読み込んだら処理を行う.
	$(window).bind("load",function(){
		
		//もし開いたページがパスにvieItemListを含む場合は、カテゴリーに関するクッキーを全て削除する.
		if(document.URL.match("/viewItemList")){
			$.removeCookie("parentId",{ path:'/'});
			$.removeCookie("childId",{ path:'/'});
			$.removeCookie("grandChildId",{ path:'/'});
		}
		
			//親カテゴリーが変更されたとき、紐づく子カテゴリーのプルダウンを生成する.
				$("#pulldown1").on("change",function() {
					var parentvalue = $("#pulldown1 option:selected").val();
					var parentUrl = "pulldown/" + parentvalue;
					var id = "#pulldown2";
					
				　　　 $.get(parentUrl,function(data){
							success(data, id);
							//もし親カテゴリーのIDを保存したクッキーがある場合は、クッキーの値で子カテゴリーのプルダウンを選択状態にする.
							if(parentvalue == $.cookie("parentId")){
								var childvalue = $.cookie("childId");
								$('#pulldown2').val(childvalue);
							}
				　　　});
				 
				   //親カテゴリーが変更されたとき、子カテゴリーと孫カテゴリーを初期状態に戻す.
					$("#pulldown3").html('<option value="">- grandChildCategory - </option>');
					$("#pulldown2").html('<option value="">- childCategory - </option>');
					
				});
				
				//もし親カテゴリーがクッキーに保存されていたら、チェンジイベントを実行する.
				if($.cookie("parentId")){
					$('#pulldown1').trigger('change');
				}
				//子カテゴリーが変更されたとき、紐づく孫カテゴリーのプルダウンを生成する.
				$("#pulldown2").on("change",function(){
					
					//これを消すと孫カテゴリーが保存されないので注意.
					var childvalue = $("#pulldown2 option:selected").val();
					if(childvalue == $.cookie("childId") || childvalue == ""){
						var childvalue = $.cookie("childId");
						console.log(childvalue);
					}
					
					var childUrl = "pulldown/" + childvalue;
					var id = "#pulldown3";
					
					 $.get(childUrl,function(data){
							success(data, id);
							
							//もし子カテゴリーのIDを保存したクッキーがある場合は、孫カテゴリーのIDの入ったクッキーの値で孫カテゴリーのプルダウンを選択状態にする.
							if(childvalue == $.cookie("childId")){
								var grandChildvalue = $.cookie("grandChildId");
								$('#pulldown3').val(grandChildvalue);
							}
							
				　　　  });
					 
				});
				//もし子カテゴリーがクッキーに保存されていたら、チェンジイベントを実行する.
				if($.cookie("childId")){
					$('#pulldown2').trigger('change');
				}
				$("#pulldown3").html('<option value="">- grandChildCategory - </option>');
				
	});
	
	//検索ボタンを押した際、カテゴリーに関する古いクッキーを破棄し、現在選択されているカテゴリーの情報をクッキーに保存する.
	$(document).on('click','#search',function(){
		
		$.removeCookie("parentId",{ path:'/'});
		$.removeCookie("childId",{ path:'/'});
		$.removeCookie("grandChildId",{ path:'/'});
		
		var parentvalue = $("#pulldown1 option:selected").val();
		var childvalue = $("#pulldown2 option:selected").val();
		var grandChildvalue = $("#pulldown3 option:selected").val();
		
		$.cookie("parentId",parentvalue, { path: '/' });
		$.cookie("childId",childvalue, { path: '/' });
		$.cookie("grandChildId",grandChildvalue, { path: '/' });
		
	});	
	
	//商品一覧のカテゴリー名がクリックされたら、古いクッキーを破棄し、各カテゴリーIDをクッキーに詰める.
    $(document).on('click','.category',function(){
		
		$.removeCookie("parentId",{ path:'/'});
		$.removeCookie("childId",{ path:'/'});
		$.removeCookie("grandChildId",{ path:'/'});
		
		var url = $(this).attr("href");
		
		//URLからリクエストパラメータを取得する.
		var parentId = url.match(/parentId=(.*?)(&|$)/)[1];
		var childId = url.match(/childId=(.*?)(&|$)/)[1];
		var grandChildId = url.match(/grandChildId=(.*?)(&|$)/)[1];
		
		$.cookie("parentId",parentId, { path: '/' });
		$.cookie("childId",childId, { path: '/' });
		$.cookie("grandChildId",grandChildId, { path: '/' });
		
	});	
    
    $(document).on('click','#detail',function(){
		
		$.removeCookie("parentId",{ path:'/'});
		$.removeCookie("childId",{ path:'/'});
		$.removeCookie("grandChildId",{ path:'/'});
		
		var url = $(this).attr("href");
		
		//セッションからリクエストパラメータを取得する.
		var parentId = session_storage.match(/parentId=(.*?)(&|$)/)[1];
		var childId = session_storage.match(/childId=(.*?)(&|$)/)[1];
		var grandChildId = session_storage.match(/grandChildId=(.*?)(&|$)/)[1];
		alert(grandChildId);
		
		$.cookie("parentId",parentId, { path: '/' });
		$.cookie("childId",childId, { path: '/' });
		$.cookie("grandChildId",grandChildId, { path: '/' });
		
	});	
	
	
});

 //非同期処理が成功した場合、直属の親カテゴリーIDに紐づく子カテゴリーの詰まったプルダウンを生成する.
 function success(data,id){
	var obj = $.parseJSON(data);
	var categoryName = null;
	
	if(id === "#pulldown2"){
		categoryName = "childCategory"
	}else{
		categoryName = "grandChildCategory";
	}
	
	$(id).html("");
	$(id).append('<option value="">- ' + categoryName +' - </option>');
	for(var i=0;i<obj.length;i++){
			
		$(id).append("<option value="+obj[i].pulldownValue+">"+obj[i].pulldownLabel+"</option>");

	}
	
}