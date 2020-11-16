<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!doctype html>
<html>

<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta content="IE=Edge" http-equiv="X-UA-Compatible" />
<title></title>
<link href="css/style_01.css" rel="stylesheet" type="text/css">
<link href="css/reset.css" rel="stylesheet" type="text/css">
<link href="css/modal.css" rel="stylesheet" type="text/css">
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<script type="text/javascript">

//sessionId 담을 unique Key
var id;
$(document).ready(function(){
	id = '_' + Math.random().toString(36).substr(2,9);
	console.log(id);
})


	//원하는 답변 얻기
	function sendMessage(query){
		var form = {
				userQuery : query,
				sessionId : id
		};
		//내가 입력한 내용 추가
		var myTag = '<li class="user"><span class="txt">' + query + '</span></li>';
		$("#talk_box").append(myTag);
		scrollToBottom($('#talk_box'));
		
		//서버 들려서 챗봇 답변 출력
		$.ajax({
			url: "messageTest",
			type: "GET",
			data : form,
			success: function(data){
				//챗봇에게 받을 내용
				var tag = '<li class="bot"><span class="bot_img"><img src="img/bot_img.png" /></span> <span class="txt"> ' + data + ' </span></li>';
				$("#talk_box").append(tag);
				$("#myContent").val("");
				scrollToBottom($('#talk_box'));
			},
			error: function(){
				alert("err");
			}
		});
	}
	
	//data 받아 오기전 내가 입력한 질의문 보냈을때
	//data 받아 온 후 챗봇의 답변을 출력해줄때
	function scrollToBottom(list){
		list.scrollTop(list[0].scrollHeight);
	}
	
	//textarea에서 엔터키 눌렀을때 
	function pressEnter(content){
		if(window.event.keyCode === 13){
		sendMessage($('#myContent').val());
		}
	}
	
	//url버튼 클릭 연결
	function repeat(url){
		window.open(url,'_blank');
	}
	
	//클릭시 모달로 이미지 보여주기
	function clickImage(src){
		$('#myModal').css("display","block");
		$('#img01').attr("src",src);
	}

	//모달 닫기
	function clickClose(){
		$('#myModal').css("display","none");
	}
</script>

<body style="background: #d3d3d3">
	<div class="demoview">
		<div class="inner">
			<div class="tit">
					메뉴얼봇(기상청)
			</div>
			<div class="tit_sub">
				<span>
					업무추진근거, 업무추진절차
					정보를 안내해드립니다..
				</span>
			</div>
			<ul class="talk_box" id="talk_box" style="background: white">
				<li class="bot"><span class="bot_img"><img
						src="img/bot_img.png" /></span> <span class="txt"> 
						안녕하세요<br>
						업무관련 법제도 지침 전문 챗봇서비스인 “매뉴얼봇”입니다.<br>
						업무추진근거 및 절차 등에 대한 궁금증을 무엇이든 물어봐주세요~ 아래와 같이 입력하시면 됩니다.<br>
						- 제안요청서 작성 방법<br>
						- SW영향평가는 어떻게 하는가<br>
						</span></li>
						
						 <!--li class="bot"><span class="bot_img"><img
						src="img/bot_img.png" /></span> <span class="txt">이미지 테스트
							<button class="btn_bot" onclick="javascript:clickImage('img/intent/intent_01_04_0.jpg')">이미지 크게보기</button>
							<img class="myImg" src="img/intent/intent_01_16_0.jpg"  onclick="javascript:clickImage('img/intent/intent_01_29_3.jpg')">
							
				</span></li-->
				<!-- <li class="bot"><span class="bot_img"><img
						src="img/bot_img.png" /></span> <span class="txt"> 궁금하신 분야의 번호를
						입력해주세요.
						<ol>
							<li>1. IT 헬프 데스크</li>
							<li>2. 인사</li>
							<li>3. 경영지원</li>
						</ol> <a href="#" class="btn_bot">버튼샘플</a>
				</span></li> 
				<li class="bot"><span class="bot_img"><img
						src="img/bot_img.png" /></span> <span class="txt"> 아래 업무 분야 또는 소송
						분야를 선택해 주시거나, 법률과 관련하여 궁금한 사항을 입력창에 직접 입력해주세요.
						<ul>
							<li><button class="btn_bot">업무분야 선택</button></li>
							<li><button class="btn_bot">소송분야 선택</button></li>
						</ul>
				</span></li>
				
				<li class="user"><span class="txt"> 소송분야 </span></li>
				<li class="bot"><span class="bot_img"><img
						src="img/bot_img.png" /></span> <span class="txt"> loading
						<div class="three-balls">
							<div class="ball ball1"></div>
							<div class="ball ball2"></div>
							<div class="ball ball3"></div>
						</div>
				</span></li>
				<li class="user"><span class="txt"> 오늘 점심메뉴는 무엇인가요?오늘
						점심메뉴는 무엇인가요?오늘 점심메뉴는 무엇인가요?오늘 점심메뉴는 무엇인가요? </span></li> -->
			</ul>
			<div class="chat_box">
				<textarea id="myContent" onkeypress="pressEnter(this);"></textarea>
				<a onclick="javascript:sendMessage($('#myContent').val())">전송</a>
			</div>
		</div>
	</div>
	
	<!-- The Modal -->
<div id="myModal" class="modal">
  <span class="close" onclick="javascript:clickClose()">&times;</span>
  <img class="modal-content" id="img01">
  <div id="caption"></div>
</div>
</body>

</html>