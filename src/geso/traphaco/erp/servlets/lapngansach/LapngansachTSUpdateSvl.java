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

public class LapngansachTSUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LapngansachTSUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		String userId;
		Utility util = new Utility();
		String querystring = request.getQueryString();
		userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String lnsId = util.getId(querystring) ;
				
		ILapngansach lnsBean = (ILapngansach) new Lapngansach();
		lnsBean.setCtyId(ctyId);
		
		lnsBean.setLnsId(lnsId);
		lnsBean.setUserId(userId);
		lnsBean.init_taisan();
		
		session.setAttribute("lnsBean", lnsBean);
		String URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSach_Taisan.jsp";			
		
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
		
		String lnsId =  util.antiSQLInspection(request.getParameter("nsId"));
		lnsBean.setLnsId(lnsId);
			
		String bpId =  util.antiSQLInspection(request.getParameter("bpId"));
		if(bpId == null) bpId = "";		
		lnsBean.setBpId(bpId);

		String loai =  util.antiSQLInspection(request.getParameter("loai"));
		if(loai == null) loai = "";		
		lnsBean.setLoai(loai);

		String[] tsIds = request.getParameterValues("tsId");
		lnsBean.setTsIds(tsIds);
		
		if(action.equals("save")){
			lnsBean.save_taisan(request);
		}
		
		lnsBean.init_taisan();
		
		session.setAttribute("lnsBean", lnsBean);
		String URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSach_Taisan.jsp";			
		
		response.sendRedirect(URL);
	}

}
