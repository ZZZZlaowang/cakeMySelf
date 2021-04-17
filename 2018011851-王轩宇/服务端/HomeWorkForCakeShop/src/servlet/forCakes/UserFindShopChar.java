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
 * 根据用户名查看该用户的购物车
 */
@WebServlet("/UserFindShopChar")
public class UserFindShopChar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserFindShopChar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String sql = "select name,size,price from "+user+"forshop";
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
				response.getWriter().write(cakes);
			}else {
				response.getWriter().write("空");
			}
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (SQLException e) {
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
