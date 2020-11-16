package nhis.chat.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.util.FileCopyUtils;
import org.springframework.web.servlet.view.AbstractView;

public class DownloadView extends AbstractView {
	
	public void Download(){
		setContentType("application/download; utf-8");
	}
	
	@Override
	protected void renderMergedOutputModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		// TODO Auto-generated method stub
		File file = (File)model.get("downloadFile");
         
        response.setContentType(getContentType());
        response.setContentLength((int)file.length());
        
        String fileName = file.getName();
         
        String browser = request.getHeader("User-Agent");
        if (browser.indexOf("MSIE") > -1) {
        	fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

        } else if (browser.indexOf("Trident") > -1) {       // IE11
        	fileName = URLEncoder.encode(fileName, "UTF-8").replaceAll("\\+", "%20");

		} else if (browser.indexOf("Firefox") > -1) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		//    	 fileName = URLDecoder.decode(fileName);
		
		} else if (browser.indexOf("Opera") > -1) {
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1") + "\"";
		
		} else if (browser.indexOf("Chrome") > -1) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < fileName.length(); i++) {
		       char c = fileName.charAt(i);
		       if (c > '~') {
		             sb.append(URLEncoder.encode("" + c, "UTF-8"));
		               } else {
		                     sb.append(c);
		               }
		        }
		        fileName = sb.toString();
		
		} else if (browser.indexOf("Safari") > -1){
			fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
//			fileName = URLDecoder.decode(fileName);
			 
		} else {
			 fileName = "\"" + new String(fileName.getBytes("UTF-8"), "8859_1")+ "\"";
		}    
        
        response.setHeader("Content-Disposition", "attachment; filename=\"" + fileName + "\";");
        
        response.setHeader("Content-Transfer-Encoding", "binary");
         
        OutputStream out = response.getOutputStream();
         
        FileInputStream fis = null;
         
        try {
             
            fis = new FileInputStream(file);
             
            FileCopyUtils.copy(fis, out);
             
             
        } catch(Exception e){
             
            e.printStackTrace();
             
        }finally{
             
            if(fis != null){
                 
                try{
                    fis.close();
                }catch(Exception e){}
            }
             
        }// try end;
         
        out.flush();
	}

}
