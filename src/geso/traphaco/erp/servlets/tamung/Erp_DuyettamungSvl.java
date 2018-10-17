package geso.traphaco.erp.servlets.tamung;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tamung.IDuyettamung;
import geso.traphaco.erp.beans.tamung.imp.Duyettamung;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Erp_DuyettamungSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Erp_DuyettamungSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util = new Utility();
	    HttpSession session = request.getSession();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	  
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));

   	    IDuyettamung ddmhBean = new Duyettamung();
   	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
   	    ddmhBean.setUserId(userId);
   	    
   	    ddmhBean.init();
		// Data is saved into session
   	    util.setSearchToHM(userId, session, this.getServletName(), "");
		session.setAttribute("ddmhBean", ddmhBean);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiTamUng.jsp";
   		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
//	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String ctyId = util.antiSQLInspection(request.getParameter("ctyId"));
	    
	    String dvthId = util.antiSQLInspection(request.getParameter("dvthId"));
	    
	    String lspId = util.antiSQLInspection(request.getParameter("lspId"));

	    String Id = util.antiSQLInspection(request.getParameter("Id"));
	    
	    /*String ngaytao = util.antiSQLInspection(request.getParameter("ngaytao"));*/
	    
	    String maDNTU = util.antiSQLInspection(request.getParameter("maDNTU"));
	    
	    String nccId = util.antiSQLInspection(request.getParameter("nccId"));
	    
	    String action = util.antiSQLInspection(request.getParameter("action")); 
	    
	    String loaicap = util.antiSQLInspection(request.getParameter("loaicap")); 
	    	
	    String nguoitaoIds = util.antiSQLInspection(request.getParameter("nguoitao")); 
	    
	    String tungay = util.antiSQLInspection(request.getParameter("tungay")); 
	    
	    String denngay = util.antiSQLInspection(request.getParameter("denngay")); 
	    	
	    String trangthai = util.antiSQLInspection(request.getParameter("trangthai")); 

	    System.out.println("DvthId:" + dvthId);
	    System.out.println("CtyId:" + ctyId);
	    
	    IDuyettamung ddmhBean = new Duyettamung();
	    ddmhBean.setCongtyId((String)session.getAttribute("congtyId"));
	    ddmhBean.setUserId(userId);
	    ddmhBean.setCtyId((String)session.getAttribute("congtyId"));
   	    ddmhBean.setDvthId(dvthId);
   	    ddmhBean.setNccId(nccId);
   	    ddmhBean.setMaDNTU(maDNTU);
   	    /*ddmhBean.setNgaytao(ngaytao);*/
   	    ddmhBean.setLspId(lspId);
   	    ddmhBean.setNguoitaoIds(nguoitaoIds);
   	    ddmhBean.setTuNgay(tungay);
   	    ddmhBean.setDenNgay(denngay);
   	    ddmhBean.setTrangThai(trangthai);
   	    
   	    String lydoxoa = request.getParameter("lydoxoa");
		if( lydoxoa == null )
			lydoxoa = "";
		ddmhBean.setLydoxoa(lydoxoa);
		
		String lydomo = request.getParameter("lydomo");
		if( lydomo == null )
			lydomo = "";
		ddmhBean.setLydomoduyet(lydomo);
		
		String lydosua = request.getParameter("lydosua");
		if( lydosua == null )
			lydosua = "";
		ddmhBean.setLydosua(lydosua);		
   	 
   	    ddmhBean.setRequest(request);
   	    if(action.equals("duyetTP") || action.equals("duyetKTV") || action.equals("duyetKTT")){ // CÁC CẤP DUYỆT
   	    	ddmhBean.Duyettamung(Id, action);

			/*String params = util.getSearchFromHM(userId, this.getServletName(), session);
			GiuDieuKienLoc.setParamsToOject(ddmhBean, params);
			////////////////

			ddmhBean.init();
			session.setAttribute("ddmhBean", ddmhBean);
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiTamUng.jsp";
			response.sendRedirect(nextJSP);*/
   	    }
   	    else if(action.equals("boduyet")) // BỎ DUYỆT => CHẮC CHẮN QUYỀN CỦA USER ĐÃ CÓ TRONG ERP_DUYETTAMUNG
   	    {
   	    	ddmhBean.BoDuyetmuahang(Id);

   	    }
   	    else if(action.equals("xoaphieu")) // XÓA => CÓ || CHƯA CÓ USER TRONG BẢNG ERP_DUYETTAMUNG
	    {
	    	ddmhBean.Xoamuahang(Id);
   	    	
	    }
   	    else if(action.equals("suaphieu"))  // SỬA => CÓ || CHƯA CÓ USER TRONG BẢNG ERP_DUYETTAMUNG
	    {
   	    	ddmhBean.Suamuahang(Id);
   	    	String nextJSP = "ErpTamUngUpdateSvl?userId=" + userId + "&update=" + Id + "&chucnang=" + loaicap+"&duyettu=1";
   	    	response.sendRedirect(nextJSP); 
   	    	return;

	    }
   	   /* else if(action.equals("boChot")) // XÓA => CÓ || CHƯA CÓ USER TRONG BẢNG ERP_DUYETMUAHANG
	    {
	    	ddmhBean.boChot(Id);
	    	
	    	
	    }*/
	    else if(action.equals("boChotNhanVien")||action.equals("boChotTruongPhong")) // XÓA => CÓ || CHƯA CÓ USER TRONG BẢNG ERP_DUYETMUAHANG
	    {
	    	ddmhBean.boChot(Id,action);
	    	
	    	
	    }


   	    ddmhBean.init();
		// Data is saved into session
		session.setAttribute("ddmhBean", ddmhBean);
//		session.setAttribute("userId", userId);
		GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, ddmhBean);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetDeNghiTamUng.jsp";
   		response.sendRedirect(nextJSP);

	}
}