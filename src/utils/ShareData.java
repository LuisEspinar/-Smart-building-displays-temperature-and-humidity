package utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ShareData {

	//存储角色的列表
	private static List<HashMap<String,String>> roleList;
	
	//存储房间的列表
	private static List<HashMap<String,String>> roomList;
	
	public static List<HashMap<String,String>> getRoleList(){
		if(roleList==null||roleList.size()==0){
			roleList = new ArrayList<>();
			HashMap<String,String> map1 = new HashMap<String,String>();
			map1.put("value", "1");
			map1.put("label", "管理员");
			roleList.add(map1);
			
			HashMap<String,String> map2 = new HashMap<String,String>();
			map2.put("value", "2");
			map2.put("label", "职工");
			roleList.add(map2);
			
			HashMap<String,String> map3 = new HashMap<String,String>();
			map3.put("value", "3");
			map3.put("label", "学生");
			roleList.add(map3);
			
			HashMap<String,String> map4 = new HashMap<String,String>();
			map4.put("value", "4");
			map4.put("label", "外来人员");
			roleList.add(map4);
		}
		return roleList;
	}
	
	public static List<HashMap<String,String>> getRoomList(){
		if(roomList==null||roomList.size()==0){
			roomList = new ArrayList<>();
			HashMap<String,String> map1 = new HashMap<String,String>();
			map1.put("value", "1");
			map1.put("label", "大厅");
			roomList.add(map1);
			
			HashMap<String,String> map2 = new HashMap<String,String>();
			map2.put("value", "2");
			map2.put("label", "办公室");
			roomList.add(map2);
			
			HashMap<String,String> map3 = new HashMap<String,String>();
			map3.put("value", "3");
			map3.put("label", "茶水间");
			roomList.add(map3);
			
			HashMap<String,String> map4 = new HashMap<String,String>();
			map4.put("value", "4");
			map4.put("label", "机房");
			roomList.add(map4);
		}
		return roomList;
	}
	
	/**
	 * 根据角色id获取角色名称
	 * @param roleId
	 * @return
	 */
	public static String getRoleName(String roleId){
		String roleName="";
		List<HashMap<String,String>> list = getRoleList();
		for (int i = 0; i < list.size(); i++) {
			if(list.get(i).get("value").equals(roleId)){
				roleName = list.get(i).get("label");
			}
		}
		return roleName;
	}
	
	/**
	 * 根据房间ids 返回房间名称拼串
	 * @param roomIds
	 * @return
	 */
	public static String getRoomNames(String roomIds){
		String roomNames="";
		List<HashMap<String,String>> list = getRoomList();
		for (int i = 0; i < list.size(); i++) {
			//不等于-1说明包含该房间
			if(roomIds.indexOf(list.get(i).get("value"))!=-1){
				roomNames += list.get(i).get("label")+",";
			}
		}
		return roomNames;
	}
	
	/**
	 * 根据ids获取房间列表
	 * @param roomIds
	 * @return
	 */
	public static List<HashMap<String,String>> getRoomList(String roomIds){
		List<HashMap<String,String>> temp = new ArrayList<>();
		List<HashMap<String,String>> list = getRoomList();
		for (int i = 0; i < list.size(); i++) {
			//不等于-1说明包含该房间
			if(roomIds.indexOf(list.get(i).get("value"))!=-1){
				temp.add(list.get(i));
			}
		}
		return temp;
	}
	
}
