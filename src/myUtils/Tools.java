package myUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.WeakHashMap;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import bean.InformBean;

public class Tools {
	
	static String appid = "14830";
	static String veriProject = "ABrLC1";
	static String informProject = "Cn3xd4";
	static String signature = "b4d9ecc491c6ff65547956285f2182fc";
	static HashMap<String,String> jsonMap = new HashMap<>();
	static WeakHashMap<String,String> hashMap = new WeakHashMap<>();
	static int responseCode;
	static JSONObject jsonObject;
	static LocalDateTime localDate;
	static Calendar calendar;
	
	static String type = "application/x-www-form-urlencoded";
	static HttpURLConnection conn;
	static String encodedData;
	static URL url;
	
	static{
		localDate = LocalDateTime.now();
		calendar = Calendar.getInstance();
	}
	
	/**
	 * 发送验证码并存储验证码
	 * @param userName 用户手机号
	 * @param verificationCode 验证码
	 * @param time 有效时间
	 */
	public static void sendMessage(String to,String code,String time){
		
		OutputStream os;
		
		hashMap.put("appid", appid);
		hashMap.put("project",veriProject);
		hashMap.put("signature", signature);
		
		jsonMap.put("code", code);
		jsonMap.put("time", time);
		jsonObject = JSONObject.fromObject(jsonMap);
		
		hashMap.put("vars", jsonObject.toString());
		hashMap.put("to", to);
		//记录验证码
		HashMap<String,String> verMap = StaticData.getVerificationMap();
		verMap.put(to, code);
		StaticData.setVerificationMap(verMap);
		
		try {
			encodedData = parseParameters(hashMap);
			url = new URL("https://api.mysubmail.com/message/xsend.json");
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));
			os = conn.getOutputStream();
			os.write(encodedData.getBytes());
			os.flush();
	        os.close();
	        responseCode = conn.getResponseCode();
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        
	        System.out.println("Sending 'POST' request to URL : " + url);
	        System.out.println("Post parameters : " + parseParameters(hashMap));
	        System.out.println("Response Code : " + responseCode);
	        System.out.println("Response : " + response);
	        
		} catch (UnsupportedEncodingException e) {
			System.out.println("发送短信编码错误");
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			System.out.println("发送短信url异常");
			e1.printStackTrace();
		}catch (ProtocolException e) {
			System.out.println("协议错误");
			e.printStackTrace();
		}catch (IOException e1) {
			System.out.println("输入输出错误");
			e1.printStackTrace();
		}
	}
	
	/**
	 * 发送短信通知
	 * @param content 短信通知的内容
	 * @param li 从数据库中查找出的发送人列表
	 */
	public static void sendInform(String content,List<InformBean> li){
		System.out.println("调用sendinform");
		OutputStream os;
		
		hashMap.put("appid", appid);
		hashMap.put("project",informProject);
		//编辑inform
		addInformContent(li, content);
		hashMap.put("multi", JSONArray.fromObject(li).toString());
		hashMap.put("signature", signature);

		try {
			encodedData = parseParameters(hashMap);
			url = new URL("https://api.mysubmail.com/message/multixsend.json");
			conn = (HttpURLConnection) url.openConnection();
			conn.setDoOutput(true);
			conn.setRequestMethod("POST");
			conn.setRequestProperty( "Content-Type", type );
			conn.setRequestProperty( "Content-Length", String.valueOf(encodedData.length()));
			os = conn.getOutputStream();
			os.write(encodedData.getBytes("utf-8"));
			os.flush();
	        os.close();
	        responseCode = conn.getResponseCode();
	        
	        BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
	        String inputLine;
	        StringBuffer response = new StringBuffer();

	        while ((inputLine = in.readLine()) != null) {
	            response.append(inputLine);
	        }
	        in.close();
	        
	        System.out.println("Sending 'POST' request to URL : " + url);
	        System.out.println("Post parameters : " + parseParameters(hashMap));
	        System.out.println("Response Code : " + responseCode);
	        System.out.println("Response : " + response);
	        
		} catch (UnsupportedEncodingException e) {
			System.out.println("发送短信编码错误");
			e.printStackTrace();
		} catch (MalformedURLException e1) {
			System.out.println("发送短信url异常");
			e1.printStackTrace();
		}catch (ProtocolException e) {
			System.out.println("协议错误");
			e.printStackTrace();
		}catch (IOException e1) {
			System.out.println("输入输出错误");
			e1.printStackTrace();
		}
	}
	
	/**
	 * 编码url中的数据
	 * @param hashMap2
	 * @return
	 */
	static String parseParameters(WeakHashMap<String, String> hashMap2){
		
		StringBuilder ret = new StringBuilder();
		String retString;
		
		Set<Map.Entry<String, String>> entity = hashMap2.entrySet();
		for(Map.Entry<String, String> name:entity){
			ret.append(name.getKey());
			ret.append("=");
			ret.append(name.getValue());
			ret.append("&");
		}
		retString = ret.toString();

		return  retString.substring(0, retString.length()-1);
	}
	
	/**
	 * 把map变成json格式的string
	 * @param jsonMap
	 * @return
	 */
	static String map2JsonString(HashMap<String,String> jsonMap){
		
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		
		Set<Entry<String,String>> entrySet = jsonMap.entrySet();
		
		try {
			for(Entry<String, String> entry:entrySet){
				sb.append(entry.getKey().getBytes("utf-8"));
				sb.append(" = ".getBytes("GB18030"));
				sb.append(entry.getValue().getBytes("gbk"));
				sb.append(";".getBytes());
			}	
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		sb.append("}");
		System.out.println("map2jsonString   "+sb.toString());
		return sb.toString();
	}
	
	/**
	 * 向multi添加发送内容
	 * @return
	 */
	static List<InformBean> addInformContent(List<InformBean> li,String content){
		
		HashMap<String, String> hashMap = new HashMap<String,String>();
		hashMap.put("content", content);
		//迭代添加
		Iterator<InformBean> iterator = li.iterator();
		while(iterator.hasNext()){
			iterator.next().setVars(hashMap);
		}
		System.out.println("addInformContent添加数据后："+JSONArray.fromObject(li).toString());
		return li;
	}
	
	/**
	 * 清除map中大于规定时间的验证码
	 * @param hashMap
	 */
	public static void clearVerificationCache(HashMap<String,String> hashMap){
		
		String value;
		String time;
		
		Set<Entry<String,String>> hashEntry = hashMap.entrySet();
		
		for(Entry<String,String> entry:hashEntry){
			value = entry.getValue();
			time = value.substring(value.lastIndexOf(":")+1);
			if((System.currentTimeMillis()-Integer.valueOf(time)) >
					(StaticData.verificationValidityPeriod*60*1000)){
				hashMap.remove(entry.getKey());
			}
		}
	}
	
	/**
	 * 清除map中大于规定时间的验证码
	 * @param hashMap
	 */
	public static void clearVerificationCache(String key){
		
		StaticData.getVerificationMap().remove(key);
		
	}

}
