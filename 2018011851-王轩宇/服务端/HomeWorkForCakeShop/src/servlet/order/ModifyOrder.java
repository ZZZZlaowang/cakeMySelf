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
 * �����û����͵������޸Ķ�����״̬
 */
@WebServlet("/ModifyOrder")
public class ModifyOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ModifyOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		String sellUser = request.getParameter("user");
		String ifOrder =request.getParameter("ifOrder");
		//��ѯ������������ҵ��û���
		try {
			CakeService cakeService = new CakeService();
			DBUtil db = new DBUtil();
			String buyUser = cakeService.findBuyUser(name, sellUser);
			//�޸���Һ����ҵĶ����е����״̬
			String sqlSell = "update "+sellUser+"forsell set ifOrder='"+ifOrder+"'where name='"+name+"'and user='"+buyUser+"'";
			String sqlBuy = "update "+buyUser+"fororder set ifOrder='"+ifOrder+"'where name='"+name+"'";
			if(db.updateData(sqlBuy)>0 && db.updateData(sqlSell)>0) {
				response.getWriter().write("�ɹ�");
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
