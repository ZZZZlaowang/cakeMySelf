package servlet.order;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

/**
 * 点击加入购物车，查询该用户的订单和购物车中有无此数据，若无，在该用户的购物车表中添加数据
 */
@WebServlet("/forShopCar")
public class ForShopCar extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForShopCar() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String user = request.getParameter("user");
		String price = request.getParameter("price");
		String size = request.getParameter("size");
		try {
			DBUtil db = new DBUtil();
			String findShop = "select * from "+user+"forshop where name='"+name+"'";
			String findOrder = "select * from "+user+"fororder where name='"+name+"'";
			if(!db.isExist(findShop) && !db.isExist(findOrder)) {
				String sql = "insert into "+user+"forshop(name,price,size) values('"+name+"','"+price+"','"+size+"')";
				if(db.addToTable(sql)>0) {
					response.getWriter().write("成功");
				}
			}else {
				response.getWriter().write("存在");
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
