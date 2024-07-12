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
@WebServlet("/PowerServlet")
public class PowerServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PowerServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		DBTool dbTool = new DBTool();
		//获取角色列表 传递到check页面
		List<HashMap<String, String>> list = ShareData.getRoleList();
		for (int i = 0; i < list.size(); i++) {
			//查询角色对应的房间ids
			String sql = "select * from sys_power where roleId="+list.get(i).get("value");
			String ids = dbTool.query(sql).get(0).get("roomIds");
			//根据房间ids获取名称拼串
			String roomNames = ShareData.getRoomNames(ids);
			//将names存储到list中
			list.get(i).put("roomNames", roomNames);
		}
		request.setAttribute("list", list);
		request.getRequestDispatcher("/power/check.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
