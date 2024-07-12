package listener;

import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import utils.DBTool;
import utils.HTool;

/**
 * Application Lifecycle Listener implementation class RoomDataListener
 *
 */
@WebListener
public class RoomDataListener implements ServletContextListener {

	DBTool dbTool = new DBTool();

    /**
     * Default constructor. 
     */
    public RoomDataListener() {
        // TODO Auto-generated constructor stub
    }

    Timer timer;
    public void contextInitialized(ServletContextEvent arg0) {
        //定时器获取数据  
    	timer = new Timer(); 
    	timer.schedule(new TimerTask() {
			@Override
			public void run() {
				//留坑 获取数据，现在这里写的是假数据
				System.out.println("触发获取假数据函数");
				createData();
			}
		},0, 
		//间隔时间
		1000*60*60);
    }
    
    private void createData(){
    	//假数据开始
    	//温度
    	String[] wenduArr = new String[]{"20","21","22","23","24","25","26","27","28","29","30","31","32"};
    	//湿度%rh
    	String[] shiduArr = new String[]{"50","55","60","65","70","75","80","85","90","95","100"};
    	//二氧化碳浓度ppm
    	String[] coArr = new String[]{"50","55","60","65","70","75","80","85","90","95","100"};
    	//光照强度 lx
    	String[] sunArr = new String[]{"50","55","60","65","70","75","80","85","90","95","100"};
    	//假数据结束
    	
    	
    	//随机取数据存数据库
    	String sql1 = "insert into sys_data (roomId,wendu,shidu,co,sun,createtime) values (1,?,?,?,?,?)";
    	String sql2 = "insert into sys_data (roomId,wendu,shidu,co,sun,createtime) values (2,?,?,?,?,?)";
    	String sql3 = "insert into sys_data (roomId,wendu,shidu,co,sun,createtime) values (3,?,?,?,?,?)";
    	String sql4 = "insert into sys_data (roomId,wendu,shidu,co,sun,createtime) values (4,?,?,?,?,?)";
    	//真数据的时候 需要替换到下面的随机假数据
    	dbTool.update(sql1, new String[]{
    			wenduArr[new Random().nextInt(wenduArr.length)],
    			shiduArr[new Random().nextInt(shiduArr.length)],
    			coArr[new Random().nextInt(coArr.length)],
    			sunArr[new Random().nextInt(sunArr.length)],
    			HTool.getTimeString()
    	});
    	dbTool.update(sql2, new String[]{
    			wenduArr[new Random().nextInt(wenduArr.length)],
    			shiduArr[new Random().nextInt(shiduArr.length)],
    			coArr[new Random().nextInt(coArr.length)],
    			sunArr[new Random().nextInt(sunArr.length)],
    			HTool.getTimeString()
    	});
    	dbTool.update(sql3, new String[]{
    			wenduArr[new Random().nextInt(wenduArr.length)],
    			shiduArr[new Random().nextInt(shiduArr.length)],
    			coArr[new Random().nextInt(coArr.length)],
    			sunArr[new Random().nextInt(sunArr.length)],
    			HTool.getTimeString()
    	});
    	dbTool.update(sql4, new String[]{
    			wenduArr[new Random().nextInt(wenduArr.length)],
    			shiduArr[new Random().nextInt(shiduArr.length)],
    			coArr[new Random().nextInt(coArr.length)],
    			sunArr[new Random().nextInt(sunArr.length)],
    			HTool.getTimeString()
    	});
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0) {
    	System.out.println("监听器结束！！！");
    	timer.cancel();
    }
	
}
