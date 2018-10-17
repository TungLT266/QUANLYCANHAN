package geso.traphaco.erp.servlets.lapngansach;

import geso.traphaco.erp.beans.lapngansach.imp.LapngansachCPList;
import geso.traphaco.erp.beans.lapngansach.ILapngansachCPList;
import geso.traphaco.center.util.*;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class LapngansachCPSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LapngansachCPSvl() {
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

		ILapngansachCPList nsList = (ILapngansachCPList) new LapngansachCPList();
		nsList.setCtyId(ctyId);
		
		nsList.setUserId(userId);
		
		if (action.equals("delete"))
		{
    		if(Id != null)		
    		nsList.Delete(Id);
		}

		
		nsList.init();			
		
		session.setAttribute("nsList", nsList);
		URL="../TraphacoHYERP/pages/Erp/Erp_LapngansachCP.jsp";			
		
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset = UTF-8");
		HttpSession session=request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		String URL;
		String action = request.getParameter("action");

		ILapngansachCPList nsList = (ILapngansachCPList) new LapngansachCPList();
		nsList.setCtyId(ctyId);
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		nsList.setUserId(userId);
				
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if(diengiai == null) diengiai = "";
		nsList.setDiengiai(diengiai);
		
		String nam = util.antiSQLInspection(request.getParameter("nam"));
		if(nam == null) nam = "";
		nsList.setNam(nam);
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if(dvkdId == null) dvkdId = "";
		nsList.setDvkdId(dvkdId);

		String hieuluc =  util.antiSQLInspection(request.getParameter("hieuluc"));
		if(hieuluc == null) hieuluc = "";
		nsList.setHieuluc(hieuluc);

		
		String copyId = util.antiSQLInspection(request.getParameter("copyId"));
		
		if(action.equals("copy")){
			if(copyId.length() > 0){
				nsList.setCopyId(copyId);
				nsList.Copy();
			}
		}
		
		if(action.equals("new")){

			session.setAttribute("nsList", nsList);
			URL="../TraphacoHYERP/pages/Erp/Erp_LapngansachCPNew.jsp";			
			
			response.sendRedirect(URL);
			return;
			
		}else if(action.equals("save")){

			nsList.createBudget();
			nsList.setDiengiai("");
		}
		

		nsList.init();
		
		session.setAttribute("nsList", nsList);
				
		URL="../TraphacoHYERP/pages/Erp/Erp_LapngansachCP.jsp";
		
		response.sendRedirect(URL);
	}
	
}
