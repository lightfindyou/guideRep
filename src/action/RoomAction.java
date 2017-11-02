package action;

import java.io.ByteArrayInputStream;

import javax.servlet.http.HttpServletRequest;

import myUtils.CookieUtils;

import org.apache.struts2.ServletActionContext;

import service.RoomService;
import bean.Room;

import com.opensymphony.xwork2.ActionSupport;
import com.opensymphony.xwork2.ModelDriven;

public class RoomAction extends ActionSupport implements ModelDriven<Room>{
	
	private static final long serialVersionUID = 620506027021414304L;
	
	Room room;
	RoomService roomService = new RoomService();
	private ByteArrayInputStream inputStream;
	private HttpServletRequest hsrt;
	
	@Override
	public Room getModel() {
		if(room == null){
			room = new Room();
		}
		return room;
	}

	private Room getCookie(Room room){
		try{
			hsrt = ServletActionContext.getRequest();
			room.setPhoneNumber(CookieUtils.getCookie(hsrt));
		}catch(NullPointerException e){
			e.printStackTrace();
		}
		return room;
	}
	
	/**
	 * 查询房间信息
	 */
	@Override
	public String execute() {
		getCookie(room);
		System.out.println("execute1");
		try {
			room = roomService.queryRoom(room);
			inputStream = new ByteArrayInputStream(room.toString().getBytes("utf-8"));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return SUCCESS;
	}
	
	/**
	 * 更新房间信息
	 */
	public String updateRoom() throws Exception {
		getCookie(room);
		roomService.updateRoom(room);
		inputStream = new ByteArrayInputStream("更新成功".getBytes("utf-8"));
		return SUCCESS;
	}
	
	/**
	 * 按照类型申请房间
	 * @param room
	 * @return
	 * @throws Exception
	 */
	public String apply() throws Exception {
		getCookie(room);
		System.out.println("room======"+room);
		String ret = roomService.applyRoom(room);
		inputStream = new ByteArrayInputStream(ret.getBytes("utf-8"));
		return SUCCESS;
	}

	public ByteArrayInputStream getInputStream() {
		return inputStream;
	}

	public void setInputStream(ByteArrayInputStream inputStream) {
		this.inputStream = inputStream;
	}

	
	
}
