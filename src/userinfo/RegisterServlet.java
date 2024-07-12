package userinfo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBTool;
import utils.HTool;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
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
		String truename = request.getParameter("truename");
		String sex = request.getParameter("sex");
		String flag = request.getParameter("flag");
		String tel = request.getParameter("tel");
		String age = request.getParameter("age");
		String address = request.getParameter("address");
		//操作数据库工具类
		DBTool dbTool = new DBTool();
		//操作数据库sql语句
		String sql = "insert into sys_user (username,password,truename,tel,age,address,createtime,sex,flag) values (?,?,?,?,?,?,?,?,?)";
		//判断不为空
		if(username==null||username.equals("")){
			request.setAttribute("message", "帐号不能为空！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		else if(password==null||password.equals("")){
			request.setAttribute("message", "密码不能为空！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		else if(truename==null||truename.equals("")){
			request.setAttribute("message", "姓名不能为空！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		else if(tel==null||tel.equals("")){
			request.setAttribute("message", "电话不能为空！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		else if(address==null||address.equals("")){
			request.setAttribute("message", "地址不能为空！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		else if(age==null||age.equals("")){
			request.setAttribute("message", "年龄不能为空！");
			request.getRequestDispatcher("/register.jsp").forward(request, response);
		}
		else{
			//判断账号是否已经存在
			if((dbTool.query("select * from sys_user where status=1 and username='"+username+"'").size()>0)||username.equals("admin")){
				request.setAttribute("message", "该账号已注册过！");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
			//插入数据
			else if(dbTool.update(sql, new String[]{username,password,truename,tel,age,address,HTool.getTimeString(),sex,flag})>0){
				request.setAttribute("message", "注册成功，请登录！");
				request.getRequestDispatcher("/login.jsp").forward(request, response);
			}else{
				request.setAttribute("message", "注册失败！");
				request.getRequestDispatcher("/register.jsp").forward(request, response);
			}
		}
	}

}
