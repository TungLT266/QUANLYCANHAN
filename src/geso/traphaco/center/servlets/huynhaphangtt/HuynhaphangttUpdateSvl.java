package geso.traphaco.center.servlets.huynhaphangtt;

import geso.traphaco.center.beans.huynhaphangtt.*;
import geso.traphaco.center.beans.huynhaphangtt.imp.*;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class HuynhaphangttUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
 
	
    public HuynhaphangttUpdateSvl() {
        super();
    }
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IHuynhaphang dhtvBean;
	 
		PrintWriter out;
		HttpSession session = request.getSession();
		
		out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
 
	    
	    if (userId.length()==0)
	    	userId = request.getParameter("userId");
	    
	    String id = util.getId(querystring);

	    dhtvBean = new Huynhaphang(id);
	    
        dhtvBean.setUserId(userId); 
        dhtvBean.init();
     
        session.setAttribute("dhtvBean", dhtvBean);
  
        String	nextJSP = "/TraphacoHYERP/pages/Center/Huynhaphangdisplaytt.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
  
	}
  
}
