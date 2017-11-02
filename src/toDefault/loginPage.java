package toDefault;

import java.io.ByteArrayInputStream;
import javax.servlet.http.HttpServletResponse;
import org.apache.struts2.ServletActionContext;
import com.opensymphony.xwork2.ActionSupport;

public class loginPage extends ActionSupport{
	
	private static final long serialVersionUID = 7553078820527222274L;
	
	HttpServletResponse response = ServletActionContext.getResponse();
	ByteArrayInputStream inputStream;
	
	@Override
	public String execute() throws Exception {
		System.out.println("toAccount 的 Action");
		inputStream = new ByteArrayInputStream("toAccount 的 action".getBytes("utf-8"));
		return SUCCESS;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

}
