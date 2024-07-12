package userinfo;

import java.io.IOException;
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
 * Servlet implementation class AddUserInfoServlet
 */
@WebServlet("/AddUserInfoServlet")
public class AddUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取角色列表数据
		List<HashMap<String, String>> list = ShareData.getRoleList();
		//传递数据 跳转页面
		request.setAttribute("list", list);
		request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//解决post请求汉字乱码问题
		request.setCharacterEncoding("utf-8");
		//获取jsp页面传递过来的用户数据
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
		if(username==null||username.equals("")){
			request.setAttribute("message", "帐号不能为空！");
			request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
		}
		else if(password==null||password.equals("")){
			request.setAttribute("message", "密码不能为空！");
			request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
		}
		else if(truename==null||truename.equals("")){
			request.setAttribute("message", "姓名不能为空！");
			request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
		}
		else if(tel==null||tel.equals("")){
			request.setAttribute("message", "电话不能为空！");
			request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
		}
		else if(address==null||address.equals("")){
			request.setAttribute("message", "地址不能为空！");
			request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
		}
		else if(age==null||age.equals("")){
			request.setAttribute("message", "年龄不能为空！");
			request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
		}
		else{
			String sql = "insert into sys_user (username,password,truename,sex,age,tel,address,flag,createtime) values (?,?,?,?,?,?,?,?,?)";
			//判断账号是否已经存在
			if((dbTool.query("select * from sys_user where status=1 and username='"+username+"'").size()>0)||username.equals("admin")){
				request.setAttribute("message", "该账号已存在！");
				request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
			}
			//插入数据
			else if(dbTool.update(sql, new String[]{username,password,truename,sex,age,tel,address,flag,HTool.getTimeString()})>0){
				request.setAttribute("message", "添加成功！");
				response.sendRedirect("CheckUserInfoServlet?type=1");
			}else{
				request.setAttribute("message", "添加失败！");
				request.getRequestDispatcher("/userinfo/add.jsp").forward(request, response);
			}
		}
	}

}
