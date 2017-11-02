package dao;

import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.dbutils.QueryRunner;
import org.apache.commons.dbutils.handlers.BeanListHandler;

import bean.Course;
import bean.InformBean;
import bean.Text;
import cn.itcast.jdbc.TxQueryRunner;

public class TextDao {
	
	private QueryRunner qr = new TxQueryRunner();
	Date date = new Date();
	SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd:HH:mm:ss");
	String dateString = sdf.format(date);
	String[] dateArray = dateString.split(":");
	/**
	 * 添加到已发送记录
	 * @param text
	 * @return
	 */
	public void addTextRecord(Text text){
		String sql = "insert into sentText set content=?,year=?,month=?,day=?,hour=?,minutes=?";
		try {
			qr.update(sql,text.getContent(),dateArray[0],dateArray[1],dateArray[2],dateArray[3],dateArray[4]);
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return ;
	}
	
	/**
	 * 查询通知记录
	 * @param text
	 * @return
	 */
	public List<Text> getSentText(){
		String sql = "select * from sentText";
		List<Text> lt = new ArrayList<Text>();
		try {
			lt = qr.query(sql,new BeanListHandler<Text>(Text.class));
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return lt;
	}
	
	/**
	 * 获取发送对象
	 * @param text
	 * @return
	 */
	public List<InformBean> getInformObject(String courseId){
		List<InformBean> li = new ArrayList<InformBean>();
		List<Course> lc = new ArrayList<Course>();
		try {
			if(courseId != null && courseId.length()>0 ){
				String sql = " select *  from course where courseId=?";
				lc = qr.query(sql, new BeanListHandler<Course>(Course.class),courseId);
				String studPhone = lc.get(0).getStudPhone();
				try {
					if(studPhone == null || studPhone.length()<1){
						studPhone = "";
					}
				} catch (NullPointerException e) {
					System.out.println("课程学生名单为空");
				}
				String[] string = studPhone.split(";");
				for(int i=0;i<string.length;i++){
					InformBean ib = new InformBean();
					ib.setTo(string[i]);
					li.add(ib);
				}
			}else{
				String sql = " select phoneNumber as 'to' from user";
				li = qr.query(sql, new BeanListHandler<InformBean>(InformBean.class));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return li;
	}
	
}
