package userinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBTool;
import utils.HTool;
import utils.ShareData;

/**
 * Servlet implementation class CheckUserInfoServlet
 */
@WebServlet("/CheckUserInfoServlet")
public class CheckUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//操作数据库工具类
	DBTool dbTool = new DBTool();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CheckUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取用户id和标识 
		String uid = request.getParameter("id");
		String type = request.getParameter("type");
		if(dbTool==null){
			dbTool=new DBTool();
		}
		//type1
		if(type.equals("1")){
			//查出所有未标记删除所有用户信息
			String sql = "select * from sys_user where status=1 and username!='admin'";
			ArrayList<HashMap<String,String>> list = dbTool.query(sql);
			//跳转页面携带数据
			request.setAttribute("list", list);
			request.getRequestDispatcher("/userinfo/checkys.jsp").forward(request, response);
		}
		//查看某个用户信息
		else if(type.equals("2")){
			//获取角色列表数据
			List<HashMap<String, String>> rolelist = ShareData.getRoleList();
			//查出单个信息
			String sql = "select * from sys_user where status=1 and id="+uid;
			ArrayList<HashMap<String,String>> list = dbTool.query(sql);
			//跳转页面携带数据
			request.setAttribute("info", list.get(0));
			request.setAttribute("rolelist", rolelist);
			request.getRequestDispatcher("/userinfo/checkys_one.jsp").forward(request, response);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决post请求汉字乱码问题
		request.setCharacterEncoding("utf-8");
		//获取jsp页面传递过来的用户数据
		String uid = request.getParameter("id");
		String password = request.getParameter("password");
		String truename = request.getParameter("truename");
		String sex = request.getParameter("sex");
		String flag = request.getParameter("flag");
		String tel = request.getParameter("tel");
		String age = request.getParameter("age");
		String address = request.getParameter("address");
		//操作数据库工具类
		DBTool dbTool = new DBTool();
		String sql = "update sys_user set password=?,truename=?,sex=?,age=?,tel=?,address=?,flag=? where id=?";
		if(dbTool.update(sql, new String[]{password,truename,sex,age,tel,address,flag,uid})>0){
			request.setAttribute("message", "修改成功！");
			response.sendRedirect("CheckUserInfoServlet?type=1");
		}else{
			request.setAttribute("message", "修改失败！");
			response.sendRedirect("CheckUserInfoServlet?type=1");
		}
	}
}
