package myUtils;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

public class CoFilter implements Filter{  
	   
	@Override  
	public void init(FilterConfig filterConfig) throws ServletException {   
	   
	}  
	   
	@Override  
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,  
				ServletException {   
		HttpServletResponse res = (HttpServletResponse) response;
		//res.setContentType("text/html;charset=UTF-8");  
		   res.setHeader("Access-Control-Allow-Origin", "*");
		   chain.doFilter(request, res);  
	}  
	   
	@Override  
	public void destroy() {   
	   
	}  
	   
}  