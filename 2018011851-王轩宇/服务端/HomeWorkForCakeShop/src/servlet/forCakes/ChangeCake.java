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
 * 修改蛋糕信息
 */
@WebServlet("/ChangeCake")
public class ChangeCake extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ChangeCake() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String price = request.getParameter("price");
		String size = request.getParameter("size");
		String material = request.getParameter("material");
		String packing = request.getParameter("packing");
		String brand = request.getParameter("brand");
		String place = request.getParameter("place");
		String give = request.getParameter("give");
		String name = request.getParameter("name");
		String sql = "update cake set price='"+price+
				"',size='"+size+
				"',material='"+material+
				"',packing='"+packing+
				"',brand='"+brand+
				"',place='"+place+
				"',give='"+give+"' where name='"+name+"'";
		try {
			DBUtil db = new DBUtil();
			if(db.updateData(sql)!=-1){
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
