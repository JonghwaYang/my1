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
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
</head>
<script type="text/javascript">
	//원하는 답변 얻기
	function sendMessage(){
		var form = {
				userQuery : $("#myContent").val()
		};
		//내가 입력한 내용 추가
		var myTag = '<li class="user"><span class="txt">' + $("#myContent").val() + '</span></li>';
		$("#talk_box").append(myTag);
		scrollToBottom($('#talk_box'));
		
		//서버 들려서 챗봇 답변 출력
		$.ajax({
			url: "message",
			method: "post",
			type: "json",
			contentType : "application/json; charset=UTF-8",
			data : JSON.stringify(form),
			success: function(data){
				//챗봇에게 받을 내용
				var tag = '<li class="bot"><span class="bot_img"><img src="img/bot_img.png" /></span> <span class="txt"> ' + data.answer + ' </span></li>';
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
		sendMessage();
		}
	}
</script>

<body style="background: #d3d3d3">
	<div class="demoview">
		<div class="inner">
			<div class="tit">기상청 챗봇</div>
			<ul class="talk_box" id="talk_box" style="background: white">
				<li class="bot"><span class="bot_img"><img
						src="img/bot_img.png" /></span> <span class="txt"> 
						안녕하세요? 저는 oo봇입니다. 무엇을 도와드릴까요?<br>
						(아래 테스트 질문과 같이 질문해보세요!)<br>
						-서울시 날씨 알려줘<br>
						-부산 지역 미세먼지 정보 알려줘<br>
						-오늘 우산들고 외출할까?<br>
						</span></li>
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
							<li><a class="btn_bot on" href="#">업무분야 선택</a></li>
							<li><a class="btn_bot" href="#">소송분야 선택</a></li>
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
				<a onclick="javascript:sendMessage()">전송</a>
			</div>
		</div>
	</div>
</body>

</html>