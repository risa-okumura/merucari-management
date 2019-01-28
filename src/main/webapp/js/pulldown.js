$(function() {
	
	$("#pulldown1").change(function() {
		var value = $("#pulldown1 option:selected").val();
		var url = "pulldown/" + value;
		var id = "#pulldown2";
		
		$.get(url,function(data){
			success(data,id);
		})
		
		$("#pulldown3").html('<option value="">- grandChildCategory - </option>');
	})
	
	$("#pulldown2").change(function(){
		var value = $("#pulldown2 option:selected").val();
		var url = "pulldown/" + value;
		var id = "#pulldown3";
		
		$.get(url,function(data){
			success(data,id);
		})
		
		console.log(url);
		
	})
	
})

 function success(data,id){
	var obj = $.parseJSON(data);
	console.log(obj);
	var categoryName = null;
	
	if(id === "#pulldown2"){
		categoryName = "childCategory"
	}else{
		categoryName = "grandChildCategory";
	}
	console.log(categoryName);
	
	$(id).html("");
	$(id).append('<option value="">- ' + categoryName +'- </option>');
	for(var i=0;i<obj.length;i++){
		$(id).append("<option value="+obj[i].childValue+">"+obj[i].childLabel+"</option>");
	}
	
	
}

