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

public class LapngansachCPUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LapngansachCPUpdateSvl() {
        super();
    }
    String URL="";
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		ILapngansach lnsBean = (ILapngansach) new Lapngansach();
		lnsBean.setCtyId(ctyId);
		
		String userId;
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		lnsBean.setUserId(userId);
		
/*		String Id = util.getId(querystring) ;
		if(Id == null) Id = "";
		lnsBean.setLnsId(Id);*/
				
		lnsBean.init();			
		session.setAttribute("lnsBean", lnsBean);
		URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSachCP_Update.jsp";			
		
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");

		Utility util = new Utility();
		
		String action = request.getParameter("action");

		ILapngansach lnsBean = (ILapngansach) new Lapngansach();
		lnsBean.setCtyId(ctyId);
		
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		lnsBean.setUserId(userId);
		
		String nsId =  util.antiSQLInspection(request.getParameter("nsId"));
		lnsBean.setLnsId(nsId);
	
		String view =  util.antiSQLInspection(request.getParameter("view"));
		System.out.println("View: " + view);
		
		if(view == null) lnsBean.setView("1");
		else lnsBean.setView("2");
		
		String bpId =  util.antiSQLInspection(request.getParameter("bpId"));
		if(bpId == null) bpId = "";
		lnsBean.setBpId(bpId);
		
		String ncpId =  util.antiSQLInspection(request.getParameter("ncpId"));
		if(ncpId == null) ncpId = "";
		lnsBean.setNcpId(ncpId);


		String[] cpIds = request.getParameterValues("cpId");
		lnsBean.setCpIds(cpIds);
		
		if(action.equals("save")){
			lnsBean.save_CP(request);
		}
		
		lnsBean.init();
		
		session.setAttribute("lnsBean", lnsBean);
		URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSachCP_Update.jsp";			
		
		response.sendRedirect(URL);
	}

}
