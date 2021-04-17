package services;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import util.DBUtil;

public class CakeService {
	
	private DBUtil dbUtil;

	public  CakeService() throws ClassNotFoundException, IOException, SQLException {
		dbUtil = new DBUtil();
	}
	
	//根据蛋糕的名字查询蛋糕的详细信息
	public ResultSet findDetailsCake(String name) throws ClassNotFoundException, SQLException {
		String sql = "select price,size,material,packing,brand,place,give from cake where name='"+name+"'";
		return dbUtil.queryDate(sql);
	}
	
	//根据蛋糕的名字查看卖家
	public String findSellUserByName(String name) throws ClassNotFoundException, SQLException {
		String sql = "select user from cake where name='"+name+"'";
		ResultSet rs = dbUtil.queryDate(sql);
		rs.next();
		return rs.getString("user");
	}
	
	//根据卖家和蛋糕名 查找买家名
	public String findBuyUser(String name,String user) throws ClassNotFoundException, SQLException {
		String sql = "select user from "+user+"forsell where name='"+name+"'";
		ResultSet rs = dbUtil.queryDate(sql);
		rs.next();
		return rs.getString("user");
	}
	//查询蛋糕的名称、尺寸、价格
	public String findNameSizePrice(String sql) {
		try {
			DBUtil db = new DBUtil();
			ResultSet rs = db.queryDate(sql);
			String cake = null;
			String cakes = null;
			while(rs.next()) {
				String name = rs.getString("name");
				String size = rs.getString("size");
				String price = rs.getString("price");
				cake = name+"&"+size+"&"+price;
				if(cakes==null) {
					cakes = cake;
				}else {
					cakes = cakes+"&&"+cake;
				}
			}
			if(rs.first()) {
				return cakes;
			}else {
				return "空";
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return null;
	}

}
