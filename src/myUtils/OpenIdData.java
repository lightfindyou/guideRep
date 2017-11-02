package myUtils;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class OpenIdData {
	
	//openId和token对应的map
	private static HashMap<String,String> openIdMap = new HashMap<>();
	//存放用过的token
	private static HashSet<String> tokenSet = new HashSet<>();

	public static HashSet<String> getTokenSet() {
		return tokenSet;
	}

	public static void setTokenSet(HashSet<String> tokenSet) {
		OpenIdData.tokenSet = tokenSet;
	}

	public static HashMap<String, String> getOpenIdMap() {
		return openIdMap;
	}

	public static void setOpenIdMap(HashMap<String, String> openIdMap) {
		OpenIdData.openIdMap = openIdMap;
	}	
	
	public static String getInfo(){
		StringBuilder ret = new StringBuilder();
		try {
			ret.append("OpenIdData   Map \r\n");
			Set<Entry<String,String>> iterator = openIdMap.entrySet();
			for(Entry<String,String> entry:iterator){
				ret.append(entry.getKey());
				ret.append(" : ");
				ret.append(entry.getValue());
				ret.append("\r\n");
			}
			ret.append("Set   \r\n");
			for(String string:tokenSet){
				ret.append(string);
				ret.append("\r\n");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ret.toString();
	}
}
