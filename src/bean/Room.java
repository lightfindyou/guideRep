package bean;

public class Room {
	private String phoneNumber;
	private String roomNumber;
	private boolean empty;
	private String roomType;
	private String applyDate;

	@Override
	public String toString() {
		return "{  phoneNumber:"+phoneNumber+",\r\nroomNumber : " + roomNumber + ",\r\nempty : " + empty
				+ ",\r\nroomType : " + roomType + " }";
	}

	public String getRoomNumber() {
		return roomNumber;
	}

	public void setRoomNumber(String roomNumber) {
		this.roomNumber = roomNumber;
	}

	public boolean isEmpty() {
		return empty;
	}

	public void setEmpty(boolean empty) {
		this.empty = empty;
	}

	public String getRoomType() {
		return roomType;
	}
	
	public void setRoomType(String roomType) {
		this.roomType = roomType;
	}

	public String getApplyDate() {
		return applyDate;
	}

	public void setApplyDate(String applyDate) {
		this.applyDate = applyDate;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

}
