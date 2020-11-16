package nhis.chat.vo;

import lombok.Data;

@Data
public class SessionTermination {
	boolean isSuccess;
	String api;
	String status;
	int errorCode;
	String errorMessage;
}
