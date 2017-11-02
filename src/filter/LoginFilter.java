package filter;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import myUtils.CookieUtils;

import org.apache.struts2.StrutsStatics;

import com.opensymphony.xwork2.ActionContext;
import com.opensymphony.xwork2.ActionInvocation;
import com.opensymphony.xwork2.interceptor.AbstractInterceptor;

public class LoginFilter extends AbstractInterceptor {

	private static final long serialVersionUID = 7860106994935256809L;

	@Override
	public String intercept(ActionInvocation invocation) throws Exception {
		ActionContext actionContext = invocation.getInvocationContext();  
        HttpServletRequest request= (HttpServletRequest) actionContext.get(StrutsStatics.HTTP_REQUEST);
        //检测phoneNumber是否为空
    	Map<String,String[]> map = request.getParameterMap();
    	if(map.containsKey("phoneNumber")){
    		System.out.println("phoneNumber不为空");
    		return invocation.invoke(); 
    	}else{
        	//如果是空就检查cookie，如果cookie也是空就返回登陆页面
    		System.out.println("phoneNumber为空");
    		String cookieString = CookieUtils.getCookie(request);
    		if(cookieString.length()>0){
        		request.setAttribute("phoneNumber", "123345");
        		System.out.println("设置了phoneNumber");
        		Map<String,String[]> map1 = request.getParameterMap();
        		if(map1.containsKey("phoneNumber")){
            		System.out.println("设置后的phoneNumber不为空");
            	}
            	return invocation.invoke(); 
            }else{
            	return "loginPage";
            }
    	}
	}

}
