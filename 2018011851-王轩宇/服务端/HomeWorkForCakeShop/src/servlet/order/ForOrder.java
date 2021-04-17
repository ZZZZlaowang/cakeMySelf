package servlet.order;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Calendar;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.CakeService;
import util.DBUtil;

/**
 * 用户下单，在该用户的订单里面添加数据，在蛋糕的卖家的订单中添加信息，删除该用户购物车里的数据
 */
@WebServlet("/forOrder")
public class ForOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ForOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String user = request.getParameter("user");
		String addSql = "insert into "+user+"fororder(name,ifOrder) values('"+name+"','待发货')";
		String delSql = "delete from "+user+"forshop where name='"+name+"'";
		try {
			DBUtil db = new DBUtil();
			CakeService cakeService = new CakeService();
			String userSell = cakeService.findSellUserByName(name);
			String addSell = "insert into "+userSell+"forsell(user,ifOrder,name)"
					+ " values('"+user+"','待发货','"+name+"')";
			if(db.addToTable(addSql)>0 && db.updateData(delSql)>0 && db.addToTable(addSell)>0) {
				response.getWriter().write("成功");
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
