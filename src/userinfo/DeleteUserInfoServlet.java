package userinfo;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import utils.DBTool;

/**
 * Servlet implementation class DeleteUserInfoServlet
 */
@WebServlet("/DeleteUserInfoServlet")
public class DeleteUserInfoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteUserInfoServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取要删除的id
		String id = request.getParameter("id");
		//操作数据库工具类
		DBTool dbTool = new DBTool();
		String sql = "update sys_user set status=0 where id="+id;
		//删除成功
		if(dbTool.update(sql, new String[]{})>0){
			response.sendRedirect("CheckUserInfoServlet?type=1");
		}else{
			response.sendRedirect("/sysdocument/error.jsp");
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
