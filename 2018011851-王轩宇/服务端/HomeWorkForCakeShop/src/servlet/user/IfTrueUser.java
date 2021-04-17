package servlet.user;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import entitys.User;
import services.UserService;

/**
 *用户登录判断用户信息是否正确
 */
@WebServlet("/IfTrueUser")
public class IfTrueUser extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public IfTrueUser() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		InputStream in = request.getInputStream();
		BufferedReader reader = new BufferedReader(new InputStreamReader(in,"utf-8"));
		String string = reader.readLine();
		String[] strings = string.split("&");
		String user = strings[0];
		String pwd = strings[1];
		String identity = strings[2];
		User u = new User(user,pwd,identity);
		UserService service = null;
		try {
			service = new UserService();
			if(!service.isTrueUser(u)) {
				response.getWriter().write("错误");
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
