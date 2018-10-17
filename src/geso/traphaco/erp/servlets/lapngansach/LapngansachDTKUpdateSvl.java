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

public class LapngansachDTKUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LapngansachDTKUpdateSvl() {
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
		
		String Id = util.getId(querystring) ;
		if(Id == null) Id = "";
		lnsBean.setLnsId(Id);
				
		lnsBean.init_doanhthu();			
		session.setAttribute("lnsBean", lnsBean);
		URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSachDTK_Update.jsp";			
		
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
			
		String bpId =  util.antiSQLInspection(request.getParameter("bpId"));
		if(bpId == null) bpId = "";
		lnsBean.setBpId(bpId);
		
		String[] dtIds = request.getParameterValues("dtId");
		lnsBean.setDtIds(dtIds);
		
		if(action.equals("save")){
			lnsBean.save_DT(request);
		}
		
		lnsBean.init_doanhthu();
		
		session.setAttribute("lnsBean", lnsBean);
		URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSachDTK_Update.jsp";			
		
		response.sendRedirect(URL);
	}

}
