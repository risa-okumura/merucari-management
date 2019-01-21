$(function() {
	$("#pulldown2").change(function() {
		var value = $("#pulldown2 option:selected").val();
		var url = "pulldown2/" + value;
		console.log(url);
		
//		$.ajax({
//		 dataType:"json",
//		 url 	 :url,
//		 type	 :"POST",
//		 data	 :{
//			 "value" : value
//		 },
//		 success : function(data){
//			success(data);
//			},
//		 error	: function(XMLHttpRequest, textStatus, errorThrown){
//			error(XMLHttpRequest,textStatus,errorThrow);
//			}
//		
//		})
		$.get(url,function(data){
			success(data);
		})
	})
})

 function success(data){
	console.log(data);
	var obj = $.parseJSON(data);
	$("#pulldown3").html("");
	for(var i=0;i<obj.length;i++){
		$("#pulldown3").append("<option value="+obj[i].childValue+">"+obj[i].childLabel+"</option>");
	}
}

