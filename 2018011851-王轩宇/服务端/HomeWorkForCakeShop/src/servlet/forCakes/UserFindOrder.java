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
 * �����û�����ѯ���û��Ķ���
 */
@WebServlet("/UserFindOrder")
public class UserFindOrder extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UserFindOrder() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String user = request.getParameter("user");
		String sql1;
		String sql2;
		sql1 = "select name,ifOrder,user from "+user+"forsell where ifOrder='������'";
		sql2 = "select name,ifOrder,user from "+user+"forsell where ifOrder='�ѷ���'";
		try {
			DBUtil db = new DBUtil();
			ResultSet rs1 = db.queryDate(sql1);
			ResultSet rs2 = db.queryDate(sql2);
			String cake = null;
			String cakes = null;
			while(rs1.next()) {
				String name = rs1.getString("name");
				String ifOrder = rs1.getString("ifOrder");
				String user1 = rs1.getString("user");
				cake = name+"&"+ifOrder+"&"+user1;
				if(cakes==null) {
					cakes = cake;
				}else {
					cakes = cakes+"&&"+cake;
				}
			}
			while(rs2.next()) {
				String name = rs2.getString("name");
				String ifOrder = rs2.getString("ifOrder");
				String user2 = rs2.getString("user");
				cake = name+"&"+ifOrder+"&"+user2;
				if(cakes==null) {
					cakes = cake;
				}else {
					cakes = cakes+"&&"+cake;
				}
			}
			if(rs1.first() || rs2.first()) {
				response.getWriter().write(cakes);
			}else {
				response.getWriter().write("��");
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
