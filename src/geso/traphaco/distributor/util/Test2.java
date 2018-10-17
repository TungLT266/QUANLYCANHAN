package geso.traphaco.distributor.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.regex.Matcher;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Test2 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Test2() {
        super();

    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession(false);
	    

	    if(session != null && !session.isNew()) {
	    	out.println(request.getRequestedSessionId());
	    } else {
	        out.println("expired");
	    }


	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

	}
	
	public static void main(String[] arg)
	{
		String a="100004,5,6,8,9,10,11,12,13,15,";
		System.out.println(a.indexOf("3"));
	}
	

}
