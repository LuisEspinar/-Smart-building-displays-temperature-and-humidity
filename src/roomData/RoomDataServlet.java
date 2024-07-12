package roomData;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import utils.DBTool;
import utils.ShareData;

/**
 * Servlet implementation class RoomDataServlet
 */
@WebServlet("/RoomDataServlet")
public class RoomDataServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    DBTool dbTool = new DBTool();
    Gson gson = new Gson();
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RoomDataServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取当前登陆人的角色
		HashMap<String,String> info = (HashMap<String,String>)request.getSession().getAttribute("userinfo");
		//获取当前角色对应的房间ids
		String sql = "select * from sys_power where roleId="+info.get("flag");
		if(dbTool==null){
			dbTool = new DBTool();
		}
		String ids = dbTool.query(sql).get(0).get("roomIds");
		//根据ids获取房间里列表数据
		List<HashMap<String, String>> list = ShareData.getRoomList(ids);
		request.setAttribute("list", list);
		request.getRequestDispatcher("/roomData/roomData.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//获取房间id
		String roomId = request.getParameter("type");
		//根据房间id获取最近24条数据 因为每小时存一次 所以最新24条就是近24小时内的
		String sql = "select * from sys_data where roomId="+roomId+" order by createtime desc limit 24";
		System.out.println(sql);
		if(dbTool==null){
			dbTool = new DBTool();
		}
		ArrayList<HashMap<String, String>> list = dbTool.query(sql);
	    //指定返回的格式为JSON格式
		response.setContentType("application/json;charset=utf-8");
		//将数据转成json
		String gson = new Gson().toJson(list);
		//返回数据
		PrintWriter out = response.getWriter();
		out.write(gson);
		out.close();
	}
}
