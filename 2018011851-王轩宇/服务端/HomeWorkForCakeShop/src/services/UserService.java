package services;

import java.io.IOException;
import java.sql.SQLException;

import entitys.User;
import util.DBUtil;

public class UserService {
	
	private DBUtil dbUtil;

	public UserService() throws ClassNotFoundException, IOException, SQLException {
		dbUtil = new DBUtil();
	}
	
	//根据用户名和身份判断是否存在该用户
	public boolean isExistUser(String user,String identity) throws ClassNotFoundException, SQLException {
		String sql = "select * from users where user='"+user
				+"'and identity='"+identity+"'";
		return dbUtil.isExist(sql);
	}
	
	//判断用户信息是否正确
	public boolean isTrueUser(User u) throws ClassNotFoundException, SQLException {
		String user = u.getUser();
		String pwd = u.getPwd();
		String identity = u.getIdentity();
		String sql = "select * from users where user='"+user
				+"'and pwd='"+pwd
				+"'and identity='"+identity+"'";
		return dbUtil.isExist(sql);
	}
	
	//注册用户
	public boolean registerUser(User u) throws SQLException, ClassNotFoundException {
		String user = u.getUser();
		String pwd = u.getPwd();
		String identity = u.getIdentity();
		String sql = "insert into users(user,pwd,identity) values('"+user+"','"+pwd+"','"+identity+"')";
		//为用户创建属于自己的表
		String table1;
		String table2;
		if(identity.equals("buy")) {
			table1 = "create table "+user+"forshop(name varchar(20),size varchar(10),price varchar(10),primary key(name))";
			table2 = "create table "+user+"fororder(name varchar(20),ifOrder varchar(10),primary key(name))";
			if(dbUtil.updateData(table1)!=-1 && dbUtil.addToTable(table2)!=-1 && dbUtil.addToTable(sql)>0) {
				return true;
			}else {
				return false;
			}
		}else {
			table1 = "create table "+user+"forsell(user varchar(20),ifOrder varchar(20),name varchar(20),primary key(user,name))";
			if(dbUtil.updateData(table1)!=-1 && dbUtil.addToTable(sql)>0) {
				return true;
			}else {
				return false;
			}
		}
		
	}
	
	

}
