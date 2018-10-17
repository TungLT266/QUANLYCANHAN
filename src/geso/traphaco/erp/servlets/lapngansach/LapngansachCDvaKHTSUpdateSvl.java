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

public class LapngansachCDvaKHTSUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public LapngansachCDvaKHTSUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		String ctyId = (String)session.getAttribute("congtyId");
		
		ILapngansach lnsBean = (ILapngansach) new Lapngansach();
		lnsBean.setCtyId(ctyId);
		
		lnsBean.init_phanbovakhauhaotaisan() ;
		
		session.setAttribute("lnsBean", lnsBean);
		String URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSach_CongdungvaKhauhao_Taisan.jsp";			
		
		response.sendRedirect(URL);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		HttpSession session=request.getSession();
		Utility util = new Utility();
		
		String ctyId = (String)session.getAttribute("congtyId");
		String action = request.getParameter("action");

		ILapngansach lnsBean = (ILapngansach) new Lapngansach();
		lnsBean.setCtyId(ctyId);
		
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		lnsBean.setUserId(userId);
		
		String lnsId =  util.antiSQLInspection(request.getParameter("nsId"));
		lnsBean.setLnsId(lnsId);
	
		String loai =  util.antiSQLInspection(request.getParameter("loai"));
		lnsBean.setLoai(loai);

		String[] tsIds = request.getParameterValues("tsIds");
		lnsBean.setTsIds(tsIds);
				
		if(action.equals("save")){
			if(lnsBean.save_khvapbtaisan(request)){
				lnsBean.setMsg("Dữ liệu đã được lưu");
			}else{
				lnsBean.setMsg("Không thể thực hiện lưu dữ liệu");
			}
		}
		
		lnsBean.init_phanbovakhauhaotaisan() ;
		
		session.setAttribute("lnsBean", lnsBean);
		String URL="../TraphacoHYERP/pages/Erp/Erp_LapNganSach_CongdungvaKhauhao_Taisan.jsp";			
		
		response.sendRedirect(URL);
	}

}
