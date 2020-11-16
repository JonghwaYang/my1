//sessionId 담을 unique Key [1]
var id;

var type1_start = "[button1]";
var type1_end = "[/button1]";
var type2_start = "[image]";
var type2_end = "[/image]";

$(document).ready(function(){
	// 슬라이드토글
	/*
	 * $(".law_case_list").click(function(){ $(".law_case_list").slideToggle();
	 * }); $(".law_case_list_short").click(function(){
	 * $(".law_case_list").slideToggle(); });
	 */
	
	// sessionId 담을 unique Key[2]
	id = '_' + Math.random().toString(36).substr(2, 9);
	
	// greeting Message
	greetingMessage();
});

// greeting Message
function greetingMessage(query) {
	
	//query가 null이면 greeting 멘트.
	if(!query) {
		var form = {
			userQuery : "hi",
			sessionId : id
		};
	}else{
	//query가 null이 아니면 사용자질의로 greeting 멘트가 들어왔을 때 처리.
		var form = {
			userQuery : query,
			sessionId : id
		};
		
		// 내가 입력한 내용 추가
		var myTag  = '<div class="user_wrap clearfix">';
			myTag += '	<div class="user_cont">';
			myTag += '		<div class="user_txt fR" style="padding-left:20px;">' + query + '</div>';
			myTag += '		<span class="time user FR">' + getRealTime() +'</span>';
			myTag += '	</div>';
			myTag += '</div>';
			
		$(".talk_wrap").append(myTag);
		scrollToBottom($('.talk_wrap'));
	}
	// 서버 들려서 챗봇 답변 출력
	$.ajax({
		url : "message",
		method : "post",
		type : "json",
		contentType : "application/json; charset=UTF-8",
		data : JSON.stringify(form),
		success : function(data) {
			var tag  = '<div class="bot_wrap clearfix">';
				tag += '	<div class="bot_img">';
				tag += '		<img src="img/icon_bot_2.png" alt="건이봇">';
				tag += '		<span>건이봇</span>';
				tag += '	</div>';
				tag += '	<div class="bot_cont">';
				tag += '		<div class="box_txt fL">';
				tag += '			<div class="cont_pd" style="letter-spacing: -0.5px;">' + data + '</div>';
				tag += '		</div>';
				tag += '		<span class="time bot">' + getRealTime() + '</span>';
				tag += '	</div>';
				tag += '</div>';
				tag += '<div class="btn_wrap">';
				tag += '	<ul class="btn_list clearfix">';
				tag += '		<li class="ca_01"><a href="#" onclick="sendMessage(\'장기요양 수급(신청)자에게 급여를 제공한 장기요양기관 및 요양기관의 청구에 대해 기본적인 안내를 받으실 수 있습니다.\');">청구공통</a></li>';
				tag += '		<li class="ca_02"><a href="#" onclick="sendMessage(\'장기요양기관 및 요양기관이 청구 후 반려, 심사조정 등의 결과를 확인할 수 있는 방법에 대해 안내받으실 수 있습니다.\');" style="letter-spacing: -1px;">청구결과 확인</a></li>';
				tag += '		<li class="ca_03"><a href="#" onclick="sendMessage(\'장기요양기관 및 요양기관이 급여 제공 후 청구시기에 대한 안내를 받으실 수 있습니다.\');">청구시기</a></li>';
				tag += '		<li class="ca_04 m_none"><a href="#" style="letter-spacing: -1px;" onclick="sendMessage(\'장기요양기관 및 요양기관이 청구를 진행과는 과정 중 발생하는 사항에 대한 안내를 받으실 수 있습니다.\');">청구진행과정</a></li>';
				tag += '	</ul>';
				tag += '</div>';
				
			$(".talk_wrap").append(tag);
			$("#myContent").val("");
			scrollToBottom($('.talk_wrap'));
		},
		error : function() {
			alert("err");
		}
	});
}

// 이미지 버튼 호출
function getImage(link) {
	if(link.indexOf("answer3") != -1){
		var tag  = '<div class="bot_wrap clearfix">';
		tag += '<div class="bot_cont_img">';
		tag += '<div class="cont_pd"><img src="img/answer/answer3_1.jpg" style="width:100%;"></div>';
		tag += '<div class="cont_pd"><img src="img/answer/answer3_2.jpg" style="width:100%;"></div>';
		tag += '<div class="case_search">';
		tag += '<a href="#" class="btn_search" onclick="removeDialogHistory();">처음으로</a>';
		tag += '</div>';
		tag += '</div>';
		tag += '</div>';
	}else{
		var tag  = '<div class="bot_wrap clearfix">';
		tag += '<div class="bot_cont_img">';
		tag += '<div class="cont_pd"><img src="' + link + '" style="width:100%;"></div>';
		tag += '<div class="case_search">';
		tag += '<a href="#" class="btn_search" onclick="removeDialogHistory();">처음으로</a>';
		tag += '</div>';
		tag += '</div>';
		tag += '</div>';
	}

	$(".talk_wrap").append(tag);
	scrollToBottom($('.talk_wrap'));
}

//대화 내역 삭제
function removeDialogHistory() {
	$(".talk_wrap").empty();
	greetingMessage();
}

// 날짜 표시하기
function getRealTime() {
	var currentNow = new Date();
	var theHours = currentNow.getHours();
	var theMinutes = currentNow.getMinutes();
	var nowTime;
	
	if(theMinutes < 10){
		theMinutes = "0" + theMinutes;
	}
	if(theHours > 12){
		theHours = theHours - 12;
		nowTime = "오후 " + theHours + ":" + theMinutes;
	}else{
		nowTime = "오전 " + theHours + ":" + theMinutes;
	}
	
	return nowTime;
};

// 챗봇으로부터 받은 답변 가공
function answerProcessing(responseAnswer){
	var returnString;
//	console.log('오리지널 답변 : ' + responseAnswer);
	
	//버튼 
	if(responseAnswer.indexOf(type1_start) != -1) {
		var btnCount = responseAnswer.split('|').length - 1;
//		console.log('버튼개수 : ' + btnCount);
		
		//추출한 [button1][/button1] 태그 벗겨내고 구분자 ','로 나누어 인텐트명과 발화 분리
		var btnArr = responseAnswer.replace(type1_start,'').replace(type1_end,'').split(',');
		
		returnString  = '<div class="bot_cont">';
		returnString += '<div class="box_txt fL">';
		returnString += '<div class="cont_pd"><p class="mb5" style="letter-spacing:-1.5px;">다음 중 궁금한 내용을 선택해주세요.</p></div>';
		returnString += '<ul class="txt_list">';
		for(var i=0; i<btnCount; i++){
//			console.log(i+1 + "번째 버튼 :" + btnArr[i]);
			var splitIntent = btnArr[i].split('|');
			// splitIntent[0]은 인텐트, splitIntent[1]은 발화
//			console.log(i+1+"번째 길이:"+splitIntent[0].length);
			if(splitIntent[0].length > 19) {
				returnString += '<li style="padding-right:15px;"><a class="txt_overflow" href="#" onclick="sendMessage(\'' + splitIntent[1] + '\')">' + splitIntent[0] + '</a></li>';
			}else {
				returnString += '<li style="padding-right:15px;"><a href="#" onclick="sendMessage(\'' + splitIntent[1] + '\')">' + splitIntent[0] + '</a></li>';
			}
		}
		returnString += '</ul>';
		returnString += '</div>';
		returnString += '<span class="time bot">' + getRealTime() + '</span>';
		returnString += '</div>';
		
		return returnString;
	}else if(responseAnswer.indexOf(type2_start) != -1) { 
		//이미지
		var returnString;
//		console.log('오리지널 답변 : ' + responseAnswer);
		
		var extractImg_start = responseAnswer.indexOf(type2_start);
		var extractImg_end = responseAnswer.indexOf(type2_end);
		
		//답변에서 [image][/image]태그로 감싸져있는 이미지 버튼명 및 링크 추출
		var extractImg = responseAnswer.substring(extractImg_start, extractImg_end+8);
		
		//추출한 [image][/image] 태그 벗겨내고 '|' 구분자로 분리하여 이미지 버튼명과 링크 분리
		var splitImg = extractImg.replace(type2_start,'').replace(type2_end,'').split('|');
		
		//기존 답변에서 extractImg로 추출한 태그내용 제거
		var editedAnswer = responseAnswer.replace(extractImg, "");
		
		returnString  = '<div class="bot_cont">';
		returnString += '	<div class="box_txt fL">';
		returnString += '		<div class="cont_pd"><p class="mb5" style="letter-spacing:-1px;">' + editedAnswer + '</p></div>';
		returnString += '		<div class="case_search">';
		returnString += '			<a href="#" class="btn_search" onclick="getImage(\'' + splitImg[1] + '\')">' + splitImg[0] + '</a>';
		returnString += '		</div>';
		returnString += '	</div>';
		returnString += '	<span class="time bot">' + getRealTime() + '</span>';
		returnString += '</div>';
		
		return returnString;
	}else {
		// 자유질의
		returnString  = '<div class="bot_cont">';
		returnString += '	<div class="box_txt fL">';
		returnString += '		<div class="cont_pd">';
		returnString += '			<p class="mb5">' + responseAnswer + '</p>';
		returnString += '		</div>';
		returnString += '		<div class="case_search">';
		returnString += '			<a href="#" class="btn_search" onclick="removeDialogHistory();">처음으로</a>';
		returnString += '		</div>';
		returnString += '	</div>';
		returnString += '	<span class="time bot">' + getRealTime() + '</span>';
		returnString += '</div>';
	}
	return returnString;
}

// 원하는 답변 얻기
function sendMessage(query) {
//	console.log("사용자질의 : " + query);
	if (!query) {
		// 사용자 질의가 공백이거나 없을 때, 질문을 입력하라는 알림창 출력
		alert("내용을 입력해 주세요.");
	} else if(query == "hi" || query == "hello" || query == "안녕") {
		greetingMessage(query);
	} else {
		// 사용자 질의가 공백이 아닐때, 정상동작
		var form = {
			userQuery : query,
			sessionId : id
		};
		// 내가 입력한 내용 추가
		var myTag  = '<div class="user_wrap clearfix">';
			myTag += '	<div class="user_cont">';
			myTag += '		<div class="user_txt fR" style="padding-left:20px;">' + query + '</div>';
			myTag += '		<span class="time user FR">' + getRealTime() +'</span>';
			myTag += '	</div>';
			myTag += '</div>';
			
		$(".talk_wrap").append(myTag);
		scrollToBottom($('.talk_wrap'));
		
		// 서버 들려서 챗봇 답변 출력
		$.ajax({
			url : "message",
			method : "post",
			type : "json",
			contentType : "application/json; charset=UTF-8",
			data : JSON.stringify(form),
			success : function(data) {
				var tag  = '<div class="bot_wrap clearfix">';
					tag += '	<div class="bot_img">';
					tag += '		<img src="img/icon_bot_2.png" alt="건이봇">';
					tag += '		<span>건이봇</span>';
					tag += '	</div>';
					tag += answerProcessing(data);
					tag += '</div>';

				$(".talk_wrap").append(tag);
				$("#myContent").val("");
				scrollToBottom($('.talk_wrap'));
			},
			error : function() {
				alert("err");
			}
		});
	}
}

// data 받아 오기전 내가 입력한 질의문 보냈을때
// data 받아 온 후 챗봇의 답변을 출력해줄때
function scrollToBottom(list) {
	list.scrollTop(list[0].scrollHeight);
}

// textarea에서 엔터키 눌렀을때
function pressEnter(content) {
	if (window.event.keyCode === 13) {
		sendMessage(content);
	}
}
