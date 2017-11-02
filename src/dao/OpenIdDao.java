package dao;

import java.util.HashMap;
import java.util.HashSet;

import myUtils.OpenIdData;

public class OpenIdDao {
	
	public static HashMap<String,String> getOpenIdMap(){
		return OpenIdData.getOpenIdMap();
	}
	
	public static void setOpenIdMap(HashMap<String,String> openIdMap){
		OpenIdData.setOpenIdMap(openIdMap);
	}
	
	public static HashSet<String> getTokenSet(){
		return OpenIdData.getTokenSet();
	}
	
	public static void setTokenSet(HashSet<String> tokenSet){
		OpenIdData.setTokenSet(tokenSet);
	}
}
