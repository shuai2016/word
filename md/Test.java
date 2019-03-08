package cn.d9ing.fpa.api.controller;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.text.MessageFormat;
import java.util.UUID;

/**
 * @ClassName Test
 * @Description TODO
 * @Author shuai
 * @Date 2018/10/25 14:53
 * @Version 1.0
 **/
public class Test {
	public static void main(String[] args) throws Exception {
		String initFile = "E:" + File.separator + "myFile" + File.separator + "test" + File.separator + "{0}.jpg";
		String saveFile = MessageFormat.format(initFile,UUID.randomUUID());


		//new一个URL对象
		URL url = new URL("http://img.hexun.com/2011-06-21/130726386.jpg");
		//打开链接
		HttpURLConnection conn = (HttpURLConnection)url.openConnection();
		//设置请求方式为"GET"
		conn.setRequestMethod("GET");
		//超时响应时间为5秒
		conn.setConnectTimeout(50 * 1000);
		//通过输入流获取图片数据
		InputStream input = conn.getInputStream();

		//FileInputStream input = new FileInputStream(file);
		OutputStream output = new FileOutputStream(saveFile);
		byte data[] = new byte[2048];
		int len = input.read(data);
		while(len == 2048){
			output.write(data);
			len = input.read(data);
		}
		if (len != -1){
			output.write(data,0,len);
		}
		input.close();
		output.close();
	}
}
