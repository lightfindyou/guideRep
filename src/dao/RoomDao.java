package dao;

import java.sql.SQLException;
import java.util.ArrayList;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanHandler;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import bean.Room;
import cn.itcast.jdbc.TxQueryRunner;

public class RoomDao {
	private QueryRunner qr = new TxQueryRunner();
	/**
	 * 查询房间状态
	 * @param room
	 * @return
	 */
	public Room queryRoom(Room room){
		String sql = "select * from room where roomNumber=?";
		try {
			room = qr.query(sql, new BeanHandler<Room>(Room.class),room.getRoomNumber());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return room;
	}
	
	/**
	 * 更新房间状态
	 * @param room
	 * @return
	 */
	public void updateRoom(Room room){
		String sql = "update room set empty=? where roomNumber=?";
		try {
			qr.update(sql,room.isEmpty(),room.getRoomNumber());
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 按类型查询空房间
	 * @param room
	 * @return
	 */
	public ArrayList<Room> queryRoomByType(Room room){
		String sql = "select * from room where roomType=? and empty='1'";
		ArrayList<Room> roomlist = new ArrayList<Room>();
		try {
			roomlist = (ArrayList<Room>) qr.query(sql,new BeanListHandler<Room>(Room.class),room.getRoomType());
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return roomlist;
	}
	
	/**
	 * 添加纪录到申请记录
	 * @param room
	 * @return
	 */
	public String addRoomApplyRecord(Room room){
		int i=0;
		System.out.println("room:  "+room);
		String sql = "insert into roomapply set phoneNumber=?,roomType=?,applyDate=?";
		try {
			i = qr.update(sql,room.getPhoneNumber(),room.getRoomType(),room.getApplyDate());
		} catch (SQLException e) {
			System.out.println("添加记录出错");
		}
		if(i==0)
			return "failed";
		return "success";
	}

}
