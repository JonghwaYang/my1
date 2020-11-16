package nhis.chat.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import lombok.extern.slf4j.Slf4j;
import nhis.chat.service.IChatService;
import nhis.chat.service.ResponseProcessingService;
import nhis.chat.vo.RequestTalk;
import nhis.chat.vo.ResponseSimulation;
import nhis.chat.vo.ResponseTalk;

@Slf4j
@Controller
public class IChatController {

	@Autowired
	private IChatService iChatService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test() {
		return "new_home";
	}

	// (post)
	@RequestMapping(value = "/message")
	@ResponseBody
	public String message(@RequestBody RequestTalk requestTalk) {
		String userQuery = requestTalk.getUserQuery();
		log.debug("userQuery : " + userQuery);

		// ichatResponse api 사용시 sessionKey parameter로(필수 아님)
		String userSessionKey = iChatService.getDmApiCommonSessionRequest("");

		ResponseSimulation responseSimulation = iChatService.getDmApiCommonWiseIChatResponse(userQuery, userSessionKey);

		String answer = responseSimulation.getResponse().getAnswer();

		ResponseTalk responseTalk = new ResponseTalk();
		
		//=====================================
		ResponseProcessingService rps = new ResponseProcessingService();
		rps.fallbackAnswer(responseSimulation, responseTalk, answer);
		//=====================================
		// 업무시간에 따라디폴트폴백 답변처리 된 부분이 위 표시부분이며, 롤백시 윗부분 지우고 이 주석 해제 responseTalk.setAnswer(answer);
		
		boolean sessionTerminationCheck = iChatService.getDmApiCommonSessionTermination(userSessionKey);
		log.debug("sessionTerminationCheck : " + sessionTerminationCheck);

		return responseTalk.getAnswer();
	}


/*	@RequestMapping(value = "/download")
	public ModelAndView download(@RequestParam("fileName") String fileName, HttpServletRequest request) {
		String pt = request.getSession().getServletContext().getRealPath("/pdf/" + fileName);
		log.debug("path :::::::::: " + pt);
		// String fullPath = path + "\\" + fileName;
		String fullPath = pt;
		File file = new File(fullPath);
		return new ModelAndView("download", "downloadFile", file);
	}*/

}
