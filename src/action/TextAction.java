package action;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;

import net.sf.json.JSONArray;
import service.TextService;
import bean.Text;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class TextAction extends ActionSupport implements ModelDriven<Text> {

	private static final long serialVersionUID = -7285774864458811992L;
	
	Text text;
	private ByteArrayInputStream inputStream;
	private TextService textService = new TextService();
	private String courseId;
	
	@Override
	public Text getModel() {
		if(text == null)
			text = new Text();
		return text;
	}

	/**
	 * 发送通知
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@Override
	public String execute() throws Exception {	
		inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		try {
			textService.sendInform(text,courseId);
			inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.execute();
	}
	
	/**
	 * 获取已发送信息
	 * @return
	 * @throws Exception
	 */
	public String getSentText() throws Exception{
		inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
		String queryData = JSONArray.fromObject(textService.getSentText()).toString();
		System.out.println("查询结果是 ："+queryData);
		
		try {
			inputStream = new ByteArrayInputStream(queryData.getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 给班级成员发送短信
	 * @deprecated 已经被TextAction.execute取代
	 * @return
	 */
	/*public String sendMessageToClasses(){
		//TODO查询班级成员并发送
		try {
			inputStream = new ByteArrayInputStream("failed".getBytes("utf-8"));
			textService.sendInform(text,classes);
			inputStream = new ByteArrayInputStream("success".getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}
*/
	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}
	
	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public String getCourseId() {
		return courseId;
	}

	public void setCourseId(String courseId) {
		this.courseId = courseId;
	}

}
