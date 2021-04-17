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
	
	//���ݵ�������ֲ�ѯ�������ϸ��Ϣ
	public ResultSet findDetailsCake(String name) throws ClassNotFoundException, SQLException {
		String sql = "select price,size,material,packing,brand,place,give from cake where name='"+name+"'";
		return dbUtil.queryDate(sql);
	}
	
	//���ݵ�������ֲ鿴����
	public String findSellUserByName(String name) throws ClassNotFoundException, SQLException {
		String sql = "select user from cake where name='"+name+"'";
		ResultSet rs = dbUtil.queryDate(sql);
		rs.next();
		return rs.getString("user");
	}
	
	//�������Һ͵����� ���������
	public String findBuyUser(String name,String user) throws ClassNotFoundException, SQLException {
		String sql = "select user from "+user+"forsell where name='"+name+"'";
		ResultSet rs = dbUtil.queryDate(sql);
		rs.next();
		return rs.getString("user");
	}
	//��ѯ��������ơ��ߴ硢�۸�
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
				return "��";
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
