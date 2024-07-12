package power;

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
import utils.ShareData;

/**
 * Servlet implementation class SetPowerServlet
 */
@WebServlet("/SetPowerServlet")
public class SetPowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public SetPowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取要配置权限的角色value值
		String roleId = request.getParameter("id");
		//到数据库中查询该角色对应的房间id拼串
		DBTool dbTool = new DBTool();
		String sql = "select * from sys_power where roleId="+roleId;
		ArrayList<HashMap<String, String>> list = dbTool.query(sql);
		//拿到房间id拼串
		String ids = list.get(0).get("roomIds");
		
		request.setAttribute("ids", ids);
		request.setAttribute("roleName", ShareData.getRoleName(roleId));
		request.setAttribute("roleId", roleId);
		request.setAttribute("roomList", ShareData.getRoomList());
		request.getRequestDispatcher("/power/setPower.jsp").forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取角色id
		String roleId = request.getParameter("roleId");
		//获取房间ids
		String[] arr = request.getParameterValues("ids");
		if(arr==null){
			//到数据库中查询该角色对应的房间id拼串
			request.setAttribute("message", "请配置房间权限");
			request.setAttribute("roleName", ShareData.getRoleName(roleId));
			request.setAttribute("roleId", roleId);
			request.setAttribute("ids", "");
			request.setAttribute("roomList", ShareData.getRoomList());
			request.getRequestDispatcher("/power/setPower.jsp").forward(request, response);
		}else{
			//拼串
			String ids="";
			for (int i = 0; i < arr.length; i++) {
				ids+=arr[i]+",";
			}
			//去掉末尾逗号
			ids = ids.substring(0, ids.length()-1);
			//存储数据库
			DBTool dbTool = new DBTool();
			String sql = "update sys_power set roomIds='"+ids+"' where roleId="+roleId;
			dbTool.update(sql, new String[]{});
			//跳转到查询servlet
			response.sendRedirect("PowerServlet");
		}
		
		
	}

}
