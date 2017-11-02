package service;

import dao.OpenIdDao;

public class OpenIdService {
	
	/**
	 * 向映射里添加键值对
	 * @param openId
	 * @param token
	 */
	public static void putOpenIdMap(String openId,String token){
		OpenIdDao.getOpenIdMap().put(openId, token);
	}
	/**
	 * 检查token是否已经用过
	 * @param token
	 * @return
	 */
	public static boolean isUsed(String token){
		return OpenIdDao.getTokenSet().contains(token);
	}
}
