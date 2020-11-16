package nhis.chat.vo;

import lombok.Data;

@Data
public class SessionRequest {

	private String errorMessage;
	private String sessionKey;
	private String status;
	private int errorCode;
	private String api;
	private Boolean isValid = false; 
}
