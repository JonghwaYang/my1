<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jstl/fmt_rt"%>
<jsp:useBean id="toDay" class="java.util.Date" />

<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta name="viewport"
	content="width=device-width, initial-scale=1, minimum-scale=1, maximum-scale=1, user-scalable=no">
<title>건이봇</title>
<link rel="shortcut icon" href="img/icon_bot.jpg">
<link rel="stylesheet" type="text/css" href="css/default.css">
<link href="css/ichat.css" rel="stylesheet" type="text/css">
<link rel="stylesheet" href="font-awesome/css/all.css">
<!-- jQuery CDN -->
<script src="https://code.jquery.com/jquery-3.2.1.min.js"></script>
<script type="text/javascript" src="js/ichat.js"></script>
</head>
<body>
	<div class="chat_wrap">
		<!-- chat_top -->
		<div class="chat_top clearfix">
			<h1 class="fL">&nbsp;</h1>
			<a href="#" class="btn_close fR"><img src="img/btn_close.png"
				alt="챗봇닫기"></a>
		</div>
		<!--  //chat_top -->
		<!-- talk_wrap -->
		<div class="talk_wrap">
			<!-- chat_date -->
			<div class="chat_date">
				<div class="start">
					<span> <fmt:formatDate value="${toDay}" pattern="yyyy.MM.dd E" />
					</span>
				</div>
			</div>
			<!-- //chat_date -->
		</div>
		<!--// talk_wrap -->

		<!-- chat_bottom -->
		<div class="chat_bottom">
			<!-- law_case_list -->
			<!-- <div class="law_case_list">
				<ul class="case">
					<li><a href="#" onclick="statusChange(this);">화재사건 관련 추심금청구소송 판례</a></li>
					<li><a href="#" onclick="statusChange(this);">화재사건 관련 구상소송 판례</a></li>
					<li><a href="#" onclick="statusChange(this);">화재사건 관련 손해배상청구소송 판례</a></li>
				</ul>
			</div>
			<div class="law_case_list_short"></div>
			//law_case_list
			new_btn_wrap
			<div class="new_btn_wrap">
				<ul class="cbt_btn_list clearfix">
					<li><a href="#" class="reset">초기화</a></li>
					<li><a href="#" class="qustion">연관질문</a></li>
					연관질문 비활성화 
					<li class="disabled"><a href="#" class="qustion">연관질문</a></li>
					
					<li><a href="#" class="learning">연관지식</a></li>
				</ul>
			</div> -->
			<!-- //new_btn_wrap -->
			<!-- chat_box -->
			<div class="chat_box clearfix">
				<div class="chat_write_box">
					<input type="text" placeholder="30자 이내로  내용을 입력해주세요."
						id="myContent" onkeypress="pressEnter(this.value);"> <a
						class="send_btn" onclick="sendMessage(myContent.value);"
						style='cursor: pointer;'>전송</a>
				</div>
			</div>
			<!--// chat box-->
		</div>
		<!-- //chat_bottom -->

		<!-- layer_popup : 연관질문 리스트-->

		<!-- // layer_popup : 연관질문 리스트 -->

		<!-- layer_popup : 연관지식 리스트-->

		<!-- // layer_popup : 연관지식 리스트 -->

		<!-- coach_marks: 챗봇도움말 화면 -->

		<!-- // coach_marks: 챗봇도움말 화면 -->

	</div>
</body>
</html>