package util;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class DBUtil {
	// 定义用于存储配置文件中的信息的属性
	private static String driver;
	private static String conn_str;
	private static String user;
	private static String pwd;
	// 连接属性
	private static Connection conn;

	public DBUtil() throws IOException, ClassNotFoundException, SQLException {
		loadProperties("DBConfig.properties");
		getConnection();
	}

	// 加载配置文件,以获取数据库连接相关的常量
	private static void loadProperties(String pFile) throws IOException {
		// 定义Properties对象
		Properties prop = new Properties();
		// 获取配置文件对应的输入流
		InputStream in = DBUtil.class.getClassLoader().getResourceAsStream(pFile);
		// 加载配置文件
		prop.load(in);
		// 给数据库连接做相关的属性赋值
		driver = prop.getProperty("driver");
		conn_str = prop.getProperty("connStr");
		user = prop.getProperty("user");
		pwd = prop.getProperty("pwd");
	}

	// 获取数据库连接
	private static void getConnection() throws ClassNotFoundException, SQLException {
		if (null == conn) {
			// 加载驱动
			Class.forName(driver);
			// 获取数据库连接对象
			conn = DriverManager.getConnection(conn_str, user, pwd);
		}
	}

	/**
	 * 判断数据是否存在
	 * 
	 * @param sql 查询的sql语句
	 * @return 存在则返回true，否则返回false
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public boolean isExist(String sql) throws ClassNotFoundException, SQLException {
		Statement stmt = conn.createStatement();
		// 执行查询
		ResultSet rs = stmt.executeQuery(sql);
		return rs.next();
	}

	/**
	 * 插入数据库
	 * 
	 * @throws SQLException
	 */
	public int addToTable(String sql) throws SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(sql);
	}

	/**
	 * 查询数据
	 * 
	 * @param sql 查询数据的sql语句
	 * @return 查询到的数据集
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public ResultSet queryDate(String sql) throws ClassNotFoundException, SQLException {
		Statement stmt = conn.createStatement();
		// 执行查询
		ResultSet rs = stmt.executeQuery(sql);
		return rs;
	}

	/**
	 * 修改或删除数据
	 * 
	 * @param sql 待操作的SQL语句
	 * @return 修改或删除的记录行数
	 * @throws SQLException
	 * @throws ClassNotFoundException
	 */
	public int updateData(String sql) throws ClassNotFoundException, SQLException {
		Statement stmt = conn.createStatement();
		return stmt.executeUpdate(sql);
	}
}
