package action;

import java.io.ByteArrayInputStream;

import service.OpenIdService;
import bean.OpenId;
import myUtils.*;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class OpenIdAction extends ActionSupport implements ModelDriven<OpenId> {

	private static final long serialVersionUID = 91636403150309568L;

	private OpenId openId;
	private ByteArrayInputStream inputStream;

	@Override
	public OpenId getModel() {
		if (openId == null) {
			openId = new OpenId();
		}
		return openId;
	}

	@Override
	public String execute() throws Exception {
		
		try {
			OpenIdService.putOpenIdMap(openId.getOpenId(), openId.getToken());
			System.out.println(openId);
			inputStream = new ByteArrayInputStream("添加成功".getBytes("utf-8"));
		} catch (Exception e) {
			inputStream = new ByteArrayInputStream("添加失败".getBytes("utf-8"));
			e.printStackTrace();
		}
		System.out.println(openId);
		System.out.print(OpenIdData.getInfo());
		return SUCCESS;
	}

	public OpenId getOpenId() {
		return openId;
	}

	public void setOpenId(OpenId openId) {
		this.openId = openId;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

}
