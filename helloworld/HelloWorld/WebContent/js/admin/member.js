 

	/*$("#memberFindTable").hide();*/
 	$("#memberFindModi").hide();

$(function(){
	
	$("#pageBar").click(function(e){
		
		if($("#leave").prop("checked")){
			leave="member_del";
		}else if($("#nonleave").prop("checked")){
			leave="member";
		}
		
		var memberName= $("#memberName").val();
		var memberId= $("#findmemberId").val();
		var phone= $("#phone").val();
		
		if($("#gender_m").prop("checked")){
			gender="M";
		}else if($("#gender_f").prop("checked")){
			gender="F";
		}else{
			gender ="";
		}

		var cPage=$(e.target).attr("cpage");
		var numPerPage=$(e.target).attr("numperpage");
		
		var urlStr="";
		var dataStr="";
		if($(e.target).attr("bName")=="all"){
			urlStr="/helloworld/admin/member";
			dataStr="cPage="+cPage+"&numPerPage="+numPerPage;
		}else if($(e.target).attr("bName")=="finder"){
			urlStr="/helloworld/admin/memberFinder";
			dataStr="memberName="+memberName+"&memberId="+memberId+"&phone="+phone+"&gender="+gender+"&leave="+leave+"&cPage="+cPage+"&numPerPage="+numPerPage;
		}else{
			return;
		}
		
		
		$.ajax({
			url:urlStr,
			data:dataStr,
			success:function(data){
				$("#adminBody").html(data);
			},
			error: function(jqxhr,textStatus, errorThrown){
				console.log("ajax처리 실패!");
				console.log(jqxhr);
				console.log(textStatus);
				console.log(errorThrown);
			},
			complete : function(data){      //처리가 실패했건, 성공했건 완료되었을때
				if(leave=="member_del"){
					$("#leave").prop("checked",true)
				}else if(leave=="member"){
					$("#nonleave").prop("checked",true);
				}
				
				$("#memberName").val(memberName);
				$("#findmemberId").val(memberId);
				$("#phone").val(phone);
				
				if(gender=="M"){
					$("#gender_m").prop("checked",true);
				}else if(gender=="F"){
					$("#gender_f").prop("checked",true);
				}
			}
		});
	});
	
	$("#findBtn").click(function(e){
		
		$("#memberFindTableHead").nextAll().remove();
		$("#memberFindModi").hide(500);
		
		if($("#leave").prop("checked")){
			leave="member_del";
		}else if($("#nonleave").prop("checked")){
			leave="member";
		}
		
		var memberName= $("#memberName").val();
		var memberId= $("#findmemberId").val();
		
		var phone= $("#phone").val();
		
		if($("#gender_m").prop("checked")){
			gender="M";
		}else if($("#gender_f").prop("checked")){
			gender="F";
		}else{
			gender ="";
		}
		
		var total=memberName+memberId+phone+gender+leave;
		if(total.length==0){
			alert("검색할 조건을 입력하세요");
			return;
		}
		
		var html;
		
		$.ajax({
			url:"/helloworld/admin/memberFinder",
			data:"memberName="+memberName+"&memberId="+memberId+"&phone="+phone+"&gender="+gender+"&leave="+leave,
			success:function(data){
				
				var table = $("#memberFindTablebo");
				
				if(data.length==0){
					alert("회원이 존재하지 않습니다.");
					$("#memberFindTable").hide(500);  
				}else{
					$("#adminBody").html(data);
					/*for(var i in data){
						var member = data[i];
						var html = "<tr><td id='"+member.memberId+"'>"+member.memberName+"</td>";
						html+="<td id='"+member.memberId+"'>"+member.memberId+"</td>";
						html+="<td id='"+member.memberId+"'>"+member.phone+"</td>";
						html+="<td id='"+member.memberId+"'>"+member.gender+"</td></tr>";
						table.append(html);
					}
					
 					$("#memberFindTable").html(table);
					$("#memberFindTable").show(500);   
					
			 		$("#memberFindTablebo").on('click',function(e){
 			 			var memberId=e.target.id; 
 			 			
						$.ajax({
			 				url:"/helloworld/admin/memberInfo",
			 				data:"&memberId="+memberId,
			 				success:function(data){
		 			 			
			 					console.log(data);
			 					$("#memberIdModi").val(data.memberId);
								$("#memberNameModi").val(data.memberName);
								$("#telModi").val(data.tel);
	 							$("#birthModi").val(data.birthday); 
 							
								if(data.gender=="M"){
									$("#genderMModi").prop("checked",true);
								}else{
									$("#genderFModi").prop("checked",true);
								}
			 				},
							error: function(jqxhr,textStatus, errorThrown){
								console.log("ajax처리 실패!");
								console.log(jqxhr);
								console.log(textStatus);
								console.log(errorThrown);
							}
						});
			 				
			 				$("#memberFindModi").show(500);
			 		});*/
				}
			},
			error: function(jqxhr,textStatus, errorThrown){
				console.log("ajax처리 실패!");
				console.log(jqxhr);
				console.log(textStatus);
				console.log(errorThrown);
			},
			complete : function(data){      //처리가 실패했건, 성공했건 완료되었을때
				if(leave=="member_del"){
					$("#leave").prop("checked",true)
				}else if(leave=="member"){
					$("#nonleave").prop("checked",true);
				}
				
				$("#memberName").val(memberName);
				$("#findmemberId").val(memberId);
				$("#phone").val(phone);
				
				if(gender=="M"){
					$("#gender_m").prop("checked",true);
				}else if(gender=="F"){
					$("#gender_f").prop("checked",true);
				}
			}
		});
		
		$("#memberFindModiCancel").click(function(e){
			$("#memberFindModi").hide(500);
 			});
 		})
 	});
 
 	
 	

 
 
 
 
 	