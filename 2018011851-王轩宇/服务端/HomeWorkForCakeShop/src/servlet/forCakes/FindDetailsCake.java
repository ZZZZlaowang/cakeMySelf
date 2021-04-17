package servlet.forCakes;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import services.CakeService;

/**
 * 根据蛋糕的名字查询详细信息
 */
@WebServlet("/findDetailsCake")
public class FindDetailsCake extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FindDetailsCake() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String name = request.getParameter("name");
		CakeService service = null;
		try {
			service = new CakeService();
			ResultSet rs = service.findDetailsCake(name);
			while(rs.next()) {
				String price = rs.getString("price");
				String size = rs.getString("size");
				String material = rs.getString("material");
				String packing = rs.getString("packing");
				String brand = rs.getString("brand");
				String place = rs.getString("place");
				String give = rs.getString("give");
				response.getWriter().write(price+"&&"+size+"&&"+material+"&&"+packing+"&&"+brand+"&&"+place+"&&"+give+"");
			}
		} catch (ClassNotFoundException e) {
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
