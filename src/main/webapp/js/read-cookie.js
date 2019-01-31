//$(function(){
//	$(window).bind("load",function(){
//		
//		if($.cookie("parentId")){
//			
//			var parentvalue = $.cookie("parentId");
//			var parentUrl = "pulldown/" +  parentvalue;
//			var id = "#pulldown2";
////			$('#pulldown1').val( parentvalue + '');
//			$("#pulldown1").val(parentvalue).change;
//			$('#pulldown1').one('change',function(){
//				
//				$.get(parentUrl,function(data){
//					success(data,id);
//				});
//				
//			});
//			$('#pulldown1').trigger('change');
//			
//			
//			if($.cookie("childId")){
//				
//				var childvalue = $.cookie("childId");
//				var childUrl = "pulldown/" + childvalue;
//				var id = "#pulldown3";
//					
//				$('#pulldown2').val(childvalue + '');
////				$("#pulldown2").val(childvalue).change;
//				$('#pulldown2').one('change',function(){
//					
//					$.get(childUrl,function(data){
//						success(data,id);
//					});
//					
//				});
//					$('#pulldown2').trigger('change');
////				
////					
////					if($.cookie("grandChildId")){
////						alert("呼ばれたよ");
////						
////						var grandChildvalue = $.cookie("grandChildId");
////						$('#pulldown3').one('change',function(){
////							$("#pulldown3").val(grandChildvalue+'');
////						});
////						$('#pulldown3').trigger('change');
////						$.removeCookie("grandChildId",{ path:'/'});
////						$.cookie("grandChildId",grandChildvalue, { path: '/' });
////				    }
//		    }
//	   }
//						
//	});
//});
//
//function success(data,id){
//	var obj = $.parseJSON(data);
//	var categoryName = null;
//	
//	if(id === "#pulldown2"){
//		categoryName = "childCategory"
//	}else{
//		categoryName = "grandChildCategory";
//	}
//	
//	$(id).html("");
//	$(id).append('<option value=""> - ' + categoryName +' - </option>');
//	for(var i=0;i<obj.length;i++){
//		$(id).append("<option value="+obj[i].childValue+">"+obj[i].childLabel+"</option>");
//	}
//	console.log(id);
//	
//	
//	
//}
//
