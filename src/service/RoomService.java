package service;

import bean.Room;
import dao.RoomDao;

public class RoomService {
	RoomDao roomDao = new RoomDao();
	
	public Room queryRoom(Room room){
		return roomDao.queryRoom(room);
	}
	
	public void updateRoom(Room room){
		roomDao.updateRoom(room);
	}
	
	/**
	 * 添加申请记录
	 * @param room
	 * @return
	 */
	public String applyRoom(Room room){
		return roomDao.addRoomApplyRecord(room); 
	}
}
