package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.beans.lapngansach.imp.Lapngansach;
import geso.traphaco.erp.beans.lapngansach.ILapngansach;
import geso.traphaco.center.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class CandoiketoanSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public CandoiketoanSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session = request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		String URL;
		Utility util = new Utility();
		
	    String userId;
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);

	    String Id = util.getId(querystring);
    	if (Id == null)
    		Id = "";
	    
    	String action = util.getAction(querystring);
    	if (action == null)
    		action = "";

		ILapngansach lnsBean = (ILapngansach) new Lapngansach();
		lnsBean.setCtyId(ctyId);
		lnsBean.setUserId(userId);
		if( lnsBean.getNam().trim().length() > 0 )
		{
			lnsBean.setNam( Integer.toString(Integer.parseInt(lnsBean.getNam()) - 1));
		}

		//lnsBean.initCDKT();
		
		session.setAttribute("lnsBean", lnsBean);
		URL = "../TraphacoHYERP/pages/Erp/ErpCanDoiKeToan.jsp";
		response.sendRedirect(URL);
		return;

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		
		Utility util = new Utility();
		String URL;
		String ctyId = (String)session.getAttribute("congtyId");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

	    String Id = util.antiSQLInspection(request.getParameter("Id"));
    	if (Id == null)
    		Id = "";
	    
    	String action = util.antiSQLInspection(request.getParameter("action"));
    	if (action == null)
    		action = "";
    	System.out.println("action: " + action);
    	

		userId = util.antiSQLInspection(request.getParameter("userId"));
	
		/*if(action.equals("excel")){
			URL="../TraphacoHYERP/Erp_PLExportSvl?Id=" + Id;
			response.sendRedirect(URL);
			return;
		}*/
		
		ILapngansach lnsBean = (ILapngansach) new Lapngansach();
		lnsBean.setCtyId(ctyId);
		lnsBean.setUserId(userId);
		lnsBean.setId(Id);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		lnsBean.setNam(nam);
		
		String thang = util.antiSQLInspection(request.getParameter("thang"));
		//lnsBean.setThangtheodoi(thang);
		
		//lnsBean.initCDKT();
		
		session.setAttribute("lnsBean", lnsBean);
		URL = "../TraphacoHYERP/pages/Erp/ErpCanDoiKeToan.jsp";
		response.sendRedirect(URL);
		
	}
}
