package geso.traphaco.center.servlets.khaosat;

import geso.traphaco.center.beans.khaosat.IThongkekhaosat;
import geso.traphaco.center.beans.khaosat.imp.Thongkekhaosat;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ThongkekhaosatUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ThongkekhaosatUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IThongkekhaosat csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = request.getParameter("khaosatId");
	    String tennguoiKs = request.getParameter("hoten");
	    String dienthoaiKs = request.getParameter("dienthoai");
	   
	    csxBean = new Thongkekhaosat(id);
	    csxBean.setTennguoiks(tennguoiKs);
	    csxBean.setSodienthoai(dienthoaiKs);
	    
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/ThongKeKhaoSatDisplay.jsp";
       
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			
	}
	
	
}
