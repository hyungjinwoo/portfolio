$(function(){
	
	
	$(".qnaBtn").hide();
	$(".qnaAns").hide();
	
	$(".memberQnaTitle").on("click",function(e){
		$(e.target).parent().parent().siblings("div.question").hide(500);
		
		$(".qnaBtn").show(500);
		$(".qnaAns").show(500);
		$("#pageBar").hide(200);
	});
	
	$("#qnaBody #cancel").on("click",function(e){
		$(".qnaBtn").hide();
		$(".qnaAns").hide();
		$(e.target).parent().prev().children().val("");
		$(e.target).parent().parent().siblings("div.question").show(500);
		$("#pageBar").show(200);
	});
	
	$(".qnaAnsText").on("focusin",function(){
		$("#qnaBody .question .qnaAns").animate({"height":"35%"},500);
		$("#qnaBody .question .qnaAns textarea").animate({"width":"90%"},500);
	});
	
	$(".qnaAnsText").on("focusout",function(){
		$("#qnaBody .question .qnaAns").animate({"height":"20%"},500);
		$("#qnaBody .question .qnaAns textarea").animate({"width":"50%"},500);
	});
	
	
	$(".pageBar i").on("click",function(e){
		if(e.target.id=='currentP'){
			return;
		}
		
		var cPage=$(e.target).attr("cpage");
		var numPerPage=$(e.target).attr("numperpage");
		
		$.ajax({
			url:"/admin/qnaList",
			data:"cPage="+cPage+"&numPerPage="+numPerPage,
			dataType:"html",
			success:function(data){
				$("#adminBody").html(data);
			},
			error: function(jqxhr,textStatus, errorThrown){
				console.log("ajax처리 실패!");
				console.log(jqxhr);
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
	
	$("#qnaBody #enter").on("click",function(e){
		
		
		var qNo = $(e.target).parent().parent()[0].id;
		var qnaAns = $(e.target).parent().prev().children().val();
		
		var cPage = $("#currentP").attr("cPage");
		var numPerPage=$("#currentP").attr("numPerPage");
		
		if(qnaAns.length<10){
			alert("답변을 10자이상 입력하세요");
			return;
		}
		
		$.ajax({
			url:"/admin/qnaAnswer",
			data:"qNo="+qNo+"&qnaAns="+qnaAns+"&cPage="+cPage+"&numPerPage="+numPerPage,
			dataType:"html",
			success:function(data){
				$("#adminBody").html(data);
				alert("답변처리 성공!!!");
				//$("#adminQna").trigger("click");
//				$(".qnaBtn").hide();
//				$(".qnaAns").hide();
//				$(e.target).parent().parent().siblings("div.question").show(1000);
			},
			error: function(jqxhr,textStatus, errorThrown){
				console.log("ajax처리 실패!");
				console.log(jqxhr);
				console.log(textStatus);
				console.log(errorThrown);
			}
		});
	});
});