package nhis.chat.service;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import lombok.extern.slf4j.Slf4j;
import nhis.chat.vo.ResponseSimulation;
import nhis.chat.vo.ResponseTalk;

@Slf4j
@Service
public class ResponseProcessingService {

	public String answerProcess(String answer){
		String processedAnswer = answer;
		log.debug("answer :::::::::: " + answer);
		
		//띄워쓰기 해결
		if(processedAnswer.contains("\n"))
			processedAnswer = doLineSkipProcess(processedAnswer);
		
		//시나리오 형식의 버튼 만들기 해결
		if(processedAnswer.contains("[button1]")){
			processedAnswer = doScenarioProcess(processedAnswer);
		}
		
		//이미지 편집
		if(answer.contains("{") && answer.contains("}"))
			processedAnswer = doImageProcess(processedAnswer);
		
		//파일 다운로드 프로세스
		/*if(processedAnswer.contains("href=\"download\"")){
			processedAnswer = doDownloadFileProcess(processedAnswer);
		}*/
				
		log.debug("answer : " + processedAnswer);
		return processedAnswer;
	}
	
	//줄바꿈 작업
	public String doLineSkipProcess(String answer){
		String returnString = "";
		returnString = answer.replace("\n", "<br>");
		log.debug("returnString :::::::::: " + returnString);
		return returnString;
	}
	
	//이미지 버튼 가공
	public String doImageProcess(String answer){
		String returnString = "";
		//answer = "막 내용내용내용<br> {ㅂㅈㄷㄱㅂㅈㄷㄱ,ㅂㅈㄷㄱㅂㄷㄱㅂㅈㄷㄱ}<br> 또내용내용내용있고 <br>{123412342134,ㅂㅈㄷㄱㅈㄷㄱㅂㅈㄷㄱㄷㅂㅈㄱ}";
		
		String contentArr[] = null;
			int startBracketCount = StringUtils.countOccurrencesOf(answer, "{");
			int endBracketCount = StringUtils.countOccurrencesOf(answer, "}");
			if(startBracketCount == endBracketCount){
				String contentData = answer.replaceAll("\\{(.*?)\\}", "★☆※");
				contentArr = contentData.split("★☆※");
			}
			
			String dataArr[] = new String[startBracketCount];
			int startBracketIndex = 0;
			int endBracketIndex = 0;
			
			for(int i=0; i < startBracketCount; i++){
				int startBracket = answer.indexOf("{",startBracketIndex);
				int endBracket = answer.indexOf("}", endBracketIndex);
				String textOfDatas = answer.substring(startBracket + 1, endBracket).trim();
				dataArr[i] = textOfDatas;
				
				startBracketIndex = startBracket + 1;
				endBracketIndex = endBracket + 1;
			}
			
			
			for(int i=0; i < contentArr.length; i++){
				returnString += contentArr[i];
				
				if(i < dataArr.length){
					String imgDatas[] = dataArr[i].split(",");
					String htmlTag = "<button class=\"btn_bot\" onclick=\"javascript:clickImage('img/intent/" + imgDatas[0] + "')\">" + imgDatas[1] + "</button>";;
					//String htmlTag = "<img class=\"myImg\" src=\"img/intent/" + imgDatas[0] + "\" onclick=\"javascript:clickImage('img/intent/" +imgDatas[0] + "')\">";
					//<img class="myImg" src="img/intent/intent_01_16_0.jpg"  onclick="javascript:clickImage('img/intent/intent_01_29_3.jpg')">
					returnString += htmlTag;
				}
			}
			returnString = returnString.replaceAll("(<button)*(<br>)(?=<button)", "");
		log.debug("returnString :::::::::: " + returnString);
		return returnString;
	}
	
	//시나리오 버튼 가공
//	public String doScenarioProcess(String answer){
//		String returnString = answer;
//		int buttonCount = StringUtils.countOccurrencesOf(answer, "[button]");
//		log.debug("buttonCount :::::::::: " + buttonCount);
//		
//		String arr[] = new String[buttonCount];
//		int buttonTagIndex = 0;
//		int buttonEndTagIndex = 0;
//		
//		for(int i=0; i < buttonCount; i++){
//			int buttonStart = answer.indexOf("<button>",buttonTagIndex);
//			int buttonStop = answer.indexOf("</button>",buttonEndTagIndex);
//			String textOfButton = answer.substring(buttonStart + 8, buttonStop).trim();
//			arr[i] = textOfButton;
//			log.debug("arr[i] :::::::::: " + arr[i]);
//			
//			buttonTagIndex = buttonStart + 1;
//			buttonEndTagIndex = buttonStop + 1;
//		}
//		
//		returnString = returnString.replaceAll("(<button>)*(<br>)(?=<button>)", "");
//		
//		for(int i = 0; i < buttonCount; i++){
//			returnString = returnString.replaceFirst("<button>", "<button class=\"btn_bot\" onclick=\"javascript:sendMessage('" + arr[i] + "')\">");
//		}
//		
//		log.debug("returnString :::::::::: " + returnString);
//		return returnString;
//	}
	
	public String doScenarioProcess(String answer){
		String returnString = answer;
		int buttonCount = StringUtils.countOccurrencesOf(answer, "|");
		log.debug("buttonCount :::::::::: " + buttonCount);
		
		String arr[] = new String[buttonCount];
		
		String[] temp = answer.split(",");
		
		for(int i=0; i<temp.length; i++) {
			System.out.println(temp[i]);
		}
		
		returnString = returnString.replaceAll("(<button>)*(<br>)(?=<button>)", "");
		
		for(int i = 0; i < buttonCount; i++){
			returnString = returnString.replaceFirst("<button>", "<button class=\"btn_bot\" onclick=\"javascript:sendMessage('" + arr[i] + "')\">");
		}
		
		log.debug("returnString :::::::::: " + returnString);
		return returnString;
	}
	
	/*//다운로드 버튼 생성 --> 다운로드 파일 1개일 경우만
	public String doDownloadFileProcess(String answer){
		String returnString = "";
		returnString = answer.replace("<a", "<a class=\"btn_bot\" href=\"download?fileName=기상청 정보화사업 표준 가이드(2018년 개정판)_최종본.pdf\"");
		log.debug("returnString :::::::::: " + returnString);
		return returnString;
	}*/
	
	public void fallbackAnswer(ResponseSimulation responseSimulation, ResponseTalk responseTalk, String answer) {
		// 챗봇으로 부터 받은 response에서 intentList에 담긴 값이 "Default_Fallback_Intent"일 때 시간체크하여
		// 업무시간인지 여부에 따라 다르게 답변처리.
		if ("Default_Fallback_Intent".equals(responseSimulation.getResponse().getAnswer())) {
			Date date = new Date();
			SimpleDateFormat hourFormat = new SimpleDateFormat("HH");
			SimpleDateFormat minuteFormat = new SimpleDateFormat("mm");
			int hour = Integer.parseInt(hourFormat.format(date));
			int minute = Integer.parseInt(minuteFormat.format(date));

			// 업무시간 09:00 - 18:00
			if (hour >= 9 && hour <= 18) {
				// 시간이 09시 또는 18시 정각일 경우
				if (hour == 18) {
					if (minute == 0) {
						responseTalk.setAnswer("죄송합니다. 잘 모르는 내용이네요.<br>"
								+ "업무 시간 중에는<br>"
								+ "아래 담당자에게 문의해주세요.<br>"
								+ "그럼 전 더 공부하러 가겠습니다.<br><br>"
								+ "○ 국민건강보험공단 전사 BPR/ISP추진단<br>"
								+ "○ 과장 박성행<br>"
								+ "○ 전화 : 033-736-4234<br>"
								+ "○ 팩스 : 033-749-9654<br>");
					} else {
						responseTalk.setAnswer("죄송합니다.<br>어떤 의도인지<br>잘 이해하지 못했습니다.");
					}
				} else {
					responseTalk.setAnswer("죄송합니다. 잘 모르는 내용이네요.<br>"
							+ "업무 시간 중에는<br>"
							+ "아래 담당자에게 문의해주세요.<br>"
							+ "그럼 전 더 공부하러 가겠습니다.<br><br>"
							+ "○ 국민건강보험공단 전사 BPR/ISP추진단<br>"
							+ "○ 과장 박성행<br>"
							+ "○ 전화 : 033-736-4234<br>"
							+ "○ 팩스 : 033-749-9654<br>");
				}
			} else {
				// 업무시간이 아닌 경우
				responseTalk.setAnswer("죄송합니다.<br>어떤 의도인지<br>잘 이해하지 못했습니다.");
			}
		} else {
			// "Default_Fallback_Intent" 아닐때
			log.debug("else");
			responseTalk.setAnswer(answer);
		}
	}
	
}