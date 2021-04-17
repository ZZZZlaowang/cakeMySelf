package servlet.forCakes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

/**
 * 根据卖家用户名查找该卖家的蛋糕
 */
@WebServlet("/FindNameBuySell")
public class FindNameBuySell extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindNameBuySell() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String sql = "select name from cake where user='"+user+"'";
		try {
			DBUtil db = new DBUtil();
			ResultSet rs = db.queryDate(sql);
			String names= null;
			while(rs.next()) {
				if(names==null) {
					names = rs.getString("name");
				}else {
					names = names+"&"+rs.getString("name");
				}
			}
			if(rs.first()) {
				response.getWriter().write(names);
			}else {
				response.getWriter().write("空");
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
