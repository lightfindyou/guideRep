package myUtils;

import java.util.HashMap;

public class StaticData {
	
	static{
		verificationMap = new HashMap<>();
	}
	
	//验证码长度
	public static int verificationCodeLength = 6;
	//验证码的有效时间
	public static int verificationValidityPeriod = 5;
	//保存用户验证码 key为用户电话号  value为 验证码:timeMills
	private static HashMap<String,String> verificationMap;
	
	public static HashMap<String, String> getVerificationMap() {
		return verificationMap;
	}
	
	public static void setVerificationMap(HashMap<String, String> verificationMap) {
		StaticData.verificationMap = verificationMap;
	}
}
