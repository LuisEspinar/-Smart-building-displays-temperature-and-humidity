package userinfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBTool;

/**
 * Servlet implementation class LoginServlet
 */
@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决传递数据中文乱码问题
		request.setCharacterEncoding("UTF-8");
		//获取jsp页面传递过来的账号和密码
		String username = request.getParameter("username");
		String password = request.getParameter("password");
		//操作数据库工具类
		DBTool dbTool = new DBTool();
		//操作数据库查询sql语句
		String sql = "select * from sys_user where status=1 and username='"+username+"' and password='"+password+"'";
		//从数据库中获取数据
		ArrayList<HashMap<String,String>> list = dbTool.query(sql);
		//判断不为空
		if(username==null||username.equals("")||password==null||password.equals("")){
			request.setAttribute("message", "帐号/密码不能为空！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
		//判断如果数据库中存在则是普通用户
		else if(list.size()>0){
			//登录成功 存储用户id和用户账号
			request.getSession().setAttribute("userinfo",list.get(0));
			//管理员登录
			response.sendRedirect(request.getContextPath()+"/index.jsp");
		}
		else{
			request.setAttribute("message", "帐号或者密码错误！");
			request.getRequestDispatcher("/login.jsp").forward(request, response);
		}
	}

}
