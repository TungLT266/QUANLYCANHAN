package geso.traphaco.erp.servlets.lapkehoach;

import geso.traphaco.erp.beans.lapkehoach.IErpKiemtrakehoach;
import geso.traphaco.erp.beans.lapkehoach.imp.ErpKiemtrakehoach;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class ErpXemkehoachUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpXemkehoachUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		IErpKiemtrakehoach xkhBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    xkhBean = new ErpKiemtrakehoach();
	    xkhBean.setCtyId(ctyId);
	    xkhBean.setUserId(userId);
	    
	    xkhBean.createRs();
        session.setAttribute("xkhBean", xkhBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXemKeHoachTongThe.jsp";
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		IErpKiemtrakehoach xkhBean = new ErpKiemtrakehoach();
    	xkhBean.setCtyId(ctyId);
    	
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		xkhBean.setUserId(userId);	       
    			
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		if (thang == null)
			thang = "";
		xkhBean.setThang(thang);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if (nam == null)
			nam = "";
		xkhBean.setNam(nam);
				
		String loaiId = util.antiSQLInspection(request.getParameter("loaiId"));
		if (loaiId == null)
			loaiId = "";
		xkhBean.setLoaiId(loaiId);

		String clId = util.antiSQLInspection(request.getParameter("clId"));
		if (clId == null)
			clId = "";
		xkhBean.setClId(clId);
		
		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if (spId == null)
			spId = "";
		xkhBean.setSpId(spId);
		
		xkhBean.createRs();
		session.setAttribute("userId", userId);
		session.setAttribute("xkhBean", xkhBean);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpXemKeHoachTongThe.jsp";
			
		response.sendRedirect(nextJSP);
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
