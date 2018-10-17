package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.beans.lapngansach.imp.LapngansachList;
import geso.traphaco.erp.beans.lapngansach.ILapngansachList;
import geso.traphaco.center.util.*;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class KhoitaongansachSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public KhoitaongansachSvl() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		ILapngansachList nsList = (ILapngansachList) new LapngansachList();
		nsList.setCtyId(ctyId);
		
		nsList.setUserId(userId);
		
		if (action.equals("delete"))
		{
    		if(Id != null)		
    		nsList.Delete(Id);
		}

		if (action.equals("update"))
		{
    		if(Id != null)		
    		nsList.setId(Id);
    		nsList.initUpdate();
    		
    		session.setAttribute("nsList", nsList);
    		URL="../TraphacoHYERP/pages/Erp/Erp_KhoitaongansachUpdate.jsp";
    		response.sendRedirect(URL);
    		return;
		}

		
		nsList.init();			
		
		session.setAttribute("nsList", nsList);
		URL="../TraphacoHYERP/pages/Erp/Erp_Khoitaongansach.jsp";			
		
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		
		
		Utility util = new Utility();
		String URL;
		String action = request.getParameter("action");

		ILapngansachList nsList = (ILapngansachList) new LapngansachList();
		String ctyId = (String)session.getAttribute("congtyId");
		nsList.setCtyId(ctyId);
		
		String Id = util.antiSQLInspection(request.getParameter("Id"));
		if(Id == null) Id = "";
		nsList.setId(Id);

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nsList.setUserId(userId);
			
		String diengiai =  util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null) diengiai = "";
		nsList.setDiengiai(diengiai);
		
		String hieuluc =  util.antiSQLInspection(request.getParameter("hieuluc"));
		if(hieuluc == null) hieuluc = "0";
		nsList.setHieuluc(hieuluc);
		
		if(action.equals("copy")){
			String copyId = util.antiSQLInspection(request.getParameter("copyId")); 
			nsList.setCopyId(copyId);
			nsList.CopyNgansach();
		}

		if(action.equals("save")){
			nsList.createBudget();
		}
		
		nsList.init();
		
		session.setAttribute("nsList", nsList);
		
		if(action.equals("new")){
		
			URL="../TraphacoHYERP/pages/Erp/Erp_KhoitaongansachNew.jsp";
			
		}else{
			
			URL="../TraphacoHYERP/pages/Erp/Erp_Khoitaongansach.jsp";
		}
		response.sendRedirect(URL);
	}
	
}
