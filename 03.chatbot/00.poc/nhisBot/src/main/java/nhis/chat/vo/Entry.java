package nhis.chat.vo;

import lombok.Data;

@Data
public class Entry {
	private String first = "";
	private Second second = new Second(); 
	private int third = 0;
	private int fourth = 0;
}
