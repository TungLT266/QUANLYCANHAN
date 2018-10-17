package geso.traphaco.erp.servlets.phieuthanhtoan;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class AjaxChangePhongBan_DNTT_Svl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public AjaxChangePhongBan_DNTT_Svl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String dvthId = request.getParameter("dvthId") == null ? "" : request.getParameter("dvthId").replace(" ", "");
	    request.getSession().setAttribute("dvthId", dvthId);
	    System.out.println("dvthId: " + dvthId);
	    
	    PrintWriter out = response.getWriter();	
	    out.write(dvthId);
	}
}
