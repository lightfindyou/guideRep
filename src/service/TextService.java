package service;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import myUtils.Tools;
import bean.InformBean;
import bean.Text;
import dao.TextDao;

public class TextService {

	TextDao textDao = new TextDao();
	Text mytext;
	
	/**
	 * 发送通知
	 * @param text
	 */
	public void sendInform(Text text,final String courseId){

		mytext = text;
		TimerTask task = new TimerTask() {  
            @Override  
            public void run() {
            	List<InformBean> li = textDao.getInformObject(courseId);
            	Tools.sendInform(mytext.getContent(), li);
                textDao.addTextRecord(mytext);
            }  
        };  
        Timer timer = new Timer();

        Calendar calendar = Calendar.getInstance();
        calendar.set(Integer.parseInt(new SimpleDateFormat("yyyy").format(new Date())), text.getMonth()-1,
        		text.getDay(), text.getHour(), text.getMinutes());
        System.out.println("现在的时间是：  "+new Date());
        System.out.println("获取的时间是：  "+calendar.getTime().toString());
        timer.schedule(task,calendar.getTime());
	}
	
	/**
	 * 获取已发送的信息
	 * @return
	 */
	public List<Text> getSentText(){
		return textDao.getSentText();
	}
}
