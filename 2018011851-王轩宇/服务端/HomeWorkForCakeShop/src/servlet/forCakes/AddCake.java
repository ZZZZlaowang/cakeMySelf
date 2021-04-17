package servlet.forCakes;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import util.DBUtil;

/**
 * Ìí¼Óµ°¸â
 */
@WebServlet("/AddCake")
public class AddCake extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AddCake() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String cake = request.getParameter("cake");
		String[] string = cake.split("-");
		String name = string[0];
		String price = string[1];
		String size = string[2];
		String material = string[3];
		String packing = string[4];
		String brand = string[5];
		String place = string[6];
		String give = string[7];
		String user = string[8];
		String sql = "insert into cake values('"+name
				+ "','"+size
				+ "','"+price
				+ "','"+material
				+ "','"+packing
				+ "','"+brand
				+ "','"+place
				+ "','"+give
				+ "','"+user+"')";
		try {
			DBUtil db = new DBUtil();
			if(db.addToTable(sql)>0) {
				response.getWriter().write("³É¹¦");
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
