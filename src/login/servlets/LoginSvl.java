package login.servlets;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import login.beans.ILogin;
import login.beans.imp.Login;
import center.util.Utility;

/**
 * Servlet implementation class LoginSvl
 */
@WebServlet("/LoginSvl")
public class LoginSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public LoginSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String action = util.getAction(querystring);
		if(action.equals("logout")){
			session.removeAttribute("userId");
			String nextJSP = "/QUANLYCANHAN/";
			response.sendRedirect(nextJSP);
			return;
		}
		
		ILogin obj = new Login();
		
		session.setAttribute("obj", obj);
	    String nextJSP = "/QUANLYCANHAN/pages/Login.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		HttpSession session = request.getSession();
		ILogin obj = new Login();
		
		String username = request.getParameter("username");
		if(username != null)
			obj.setUsername(username);
		
		String password = request.getParameter("password");
		if(password != null)
			obj.setPassword(password);
		
		String nextJSP = "";
		
		if(obj.login()) {
			session.setAttribute("userTen", obj.getUserTen());
			session.setAttribute("userId", obj.getUserId());
			nextJSP = "/QUANLYCANHAN/";
		} else {
			session.setAttribute("obj", obj);
			nextJSP = "/QUANLYCANHAN/pages/Login.jsp";
		}
		
		response.sendRedirect(nextJSP);
	}

}
