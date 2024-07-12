package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class DBTool {
	String url = "jdbc:mysql://localhost:3306/autoroom";
	String user = "root";
	String password = "root";
	ResultSet rs;//结果集
	Statement stmt;//语句对象
	PreparedStatement pps;//预处理语句对象
	Connection conn;//链接
	ArrayList<HashMap<String,String>> list;//存储rs数据的
	
	/**
	 * 创建链接
	 */
	private void getConn(){
		try {
			//第一步  加载驱动
			Class.forName("com.mysql.jdbc.Driver");
			//第二步 创建链接 
			conn = DriverManager.getConnection(url, user, password);
			//第三与 创建语句对象
			//stmt = conn.createStatement();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 增删改
	 * @param sql
	 * @return 返回影响的行数 -1表示程序出错
	 */
	public int update(String sql,String[] values){
		getConn();
		try {
			//获取预处理语句对象
			pps = conn.prepareStatement(sql);
			//将数组中的数据 放到？的位置上
			for (int i = 0; i < values.length; i++) {
				//第一个参数表示第几个问号  第二个参数是用传进来的值把问号替换
				pps.setString(i+1, values[i]);
			}
			return pps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		} finally{
			close();
		}
		return -1;
	}
	
	/**
	 * 查询
	 * @param sql
	 * @return list套map
	 */
	public ArrayList<HashMap<String,String>> query(String sql,String[] values){
		getConn();
		//查询
		try {
			//通过sql语句生成pps预处理语句对象
			pps = conn.prepareStatement(sql);
			//将字符串数组中的数组 存储到pps对象中
			for (int i = 0; i < values.length; i++) {
				//第一个参数表示第几个问号  第二个参数是用传进来的值把问号替换
				pps.setString(i+1, values[i]);
			}
			//执行sql语句
			rs = pps.executeQuery();
			//通过rs拿到结果集的表结构
			ResultSetMetaData rsmd = rs.getMetaData();
			//获取结果集中的字段个数
			int count = rsmd.getColumnCount();
			//存储rs数据的
			list = new ArrayList<HashMap<String,String>>();
			//判断结果集是否有下一行
			while(rs.next()){
				//走到这里 说明有下一行 那么就需要一个map 一行数据 就是一个map 字段名是map的可以  字段对应的这行值是map的value
				HashMap<String,String> map = new HashMap<String,String>();
				for(int i=0;i<count;i++){
					//通过结果集的表结果 拿到字段的名字
					String key = rsmd.getColumnLabel(i+1);
					//通过结果集 和 字段名 拿到 这一行 这个字段所对应的值
					String value = rs.getString(key);
					map.put(key, value);
				}
				list.add(map);
			}
			return list;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 查询
	 * @param sql
	 * @return list套map
	 */
	public ArrayList<HashMap<String,String>> query(String sql){
		return query(sql, new String[]{});
	}
	
	/**
	 * 分页查询
	 * @param sql
	 * @param page 要显示的页码
	 * @param count 每页要显示的条数
	 * @return list套map
	 */
	public ArrayList<HashMap<String,String>> query(String sql,int page,int count){
		sql = sql + " limit " + (page-1)*count + "," + count;
		return query(sql);
	}
	/**
	 * 关闭资源
	 */
	private void close(){
		//关闭资源 从内到外
		try {
			if(rs!=null)
				rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(stmt!=null)
				stmt.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		try {
			if(conn!=null)
				conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
}
