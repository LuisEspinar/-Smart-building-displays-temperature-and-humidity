package utils;

import java.text.SimpleDateFormat;

public class HTool {
	
	
	/**
	 * 获取当前系统时间  yyyy-MM-dd HH:mm:ss格式
	 * @author 韩韩
	 * @date   2018年2月9日上午9:49:50
	 * @return
	 */
	public static String getTimeString(){
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
		return df.format(System.currentTimeMillis());
	}
}
