package servlet.order;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.CakeService;
import util.DBUtil;

/**
 * 根据用户名和蛋糕名删除订单
 */
@WebServlet("/DeleteOrder")
public class DeleteOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeleteOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String name = request.getParameter("name");
		String identity = request.getParameter("identity");
		String sql;
		if(identity.equals("buy")) {//买家删除，根据蛋糕名字删除
			sql = "delete from "+user+"fororder where name='"+name+"'";
		}else {//卖家删除，根据买家名和蛋糕名删除
			String buyUser = null;
			try {//根据卖家名和蛋糕名查找买家名
				CakeService cakeService = new CakeService();
				buyUser = cakeService.findBuyUser(name, user);
			} catch (ClassNotFoundException | IOException | SQLException e) {
				e.printStackTrace();
			}
			sql = "delete from "+user+"forsell where name='"+name+"'and user='"+buyUser+"'and ifOrder='已发货'";
		}
		try {
			DBUtil db = new DBUtil();
			if(db.updateData(sql)>0) {
				response.getWriter().write("成功");
			}
		} catch (ClassNotFoundException | IOException | SQLException e) {
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
