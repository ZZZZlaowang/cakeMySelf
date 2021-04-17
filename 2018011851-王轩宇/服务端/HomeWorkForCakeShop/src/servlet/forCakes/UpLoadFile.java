package servlet.forCakes;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * ÃÌº”Õº∆¨
 */
@WebServlet("/UpLoadFile")
public class UpLoadFile extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public UpLoadFile() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("utf-8");
		response.setContentType("text/html;charset=utf-8");
		InputStream in = request.getInputStream();
		String path = this.getServletContext().getRealPath("/");
		String name = request.getParameter("name");
		FileOutputStream out = new FileOutputStream(new File(path+"images/"+name+".jpg"));
		int n =-1;
		byte[] b = new byte[1024];
		while((n=in.read(b))!=-1) {
			out.write(b,0,n);
		}
		response.getWriter().write("≥…π¶");
		out.close();
		in.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
