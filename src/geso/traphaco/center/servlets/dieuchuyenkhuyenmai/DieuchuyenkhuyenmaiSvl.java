package geso.traphaco.center.servlets.dieuchuyenkhuyenmai;

import geso.traphaco.center.beans.dieuchuyenkhuyenmai.IDieuchuyenkhuyenmai;
import geso.traphaco.center.beans.dieuchuyenkhuyenmai.imp.Dieuchuyenkhuyenmai;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class DieuchuyenkhuyenmaiSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public DieuchuyenkhuyenmaiSvl() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    String schemeId;
	    Utility util = new Utility();
	    
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IDieuchuyenkhuyenmai dckmBean = new Dieuchuyenkhuyenmai();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    System.out.println(userId);
	    dckmBean.setUserId(userId);
	    dckmBean.init();	    
		session.setAttribute("dckm", dckmBean);
		session.setAttribute("schemeId", "0");
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/DieuChuyenKhuyenMai.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();					
		
		IDieuchuyenkhuyenmai dckmBean = new Dieuchuyenkhuyenmai();
		Utility util = new Utility();
		
		String schemeId = util.antiSQLInspection(request.getParameter("schemeId"));
		dckmBean.setSchemeId(schemeId);
		
		String kvId = util.antiSQLInspection(request.getParameter("kvId"));
		dckmBean.setKvId(kvId);
		
		String tieuchi = util.antiSQLInspection(request.getParameter("tieuchi"));
		dckmBean.setTieuchi(tieuchi);

		String userId = (String)session.getAttribute("userId");		
		dckmBean.setUserId(userId);
		
		

		dckmBean.init();
		
		String action = request.getParameter("action");		
	/*	if(action.equals("adjust")){
			System.out.print("Adjustment has been done");
			dckmBean.createAdjust();
		}	
		*/		
		if(action.equals("save")){
			if(dckmBean.save(request)){
				dckmBean.setMessage("Dieu chuyen khuyen mai da duoc luu thanh cong");
				dckmBean.init();
			}else{
				dckmBean.setMessage("Dieu chuyen khuyen mai vuot / duoi ngan sach cho phep");
			}
		}
		
		session.setAttribute("dckm", dckmBean);
		session.setAttribute("schemeId", schemeId);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/DieuChuyenKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

}
