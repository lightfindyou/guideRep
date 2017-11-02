package myUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;

import bean.User;

/** 
 * cookie的增加、删除、查询 
 */  
public class CookieUtils {

    public static final String USER_COOKIE = "user.cookie";  
  
    // 添加一个cookie  
    public static void addCookie(HttpServletResponse httpServletResponse,User user) {  
        Cookie cookie = new Cookie(USER_COOKIE, user.getPhoneNumber());  
        System.out.println("添加cookie");  
        cookie.setMaxAge(60 * 60 * 24 * 14);// cookie保存两周  
        httpServletResponse.addCookie(cookie);
        System.out.println("获取的cookies：   "+cookie.toString());
    }  
  
    // 检查cookie  
    public static String getCookie(HttpServletRequest request) {  
        Cookie[] cookies = request.getCookies();  
        System.out.println("cookies: " + cookies);  
        if (cookies != null) { 
            for (Cookie cookie : cookies) {  
                System.out.println("cookie: " + cookie.getName());  
                if (CookieUtils.USER_COOKIE.equals(cookie.getName())) {  
                    String value = cookie.getValue(); 
                    System.out.println("user.cookie="+value);
                    if (StringUtils.isNotBlank(value)) {   
                       return value;
                    }
                } 
            }  
        }  
        return "";
    } 
}  

