package geso.traphaco.erp.servlets.chinhsachduyetmuahang;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahang;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahangList;
import geso.traphaco.erp.beans.chinhsachduyetmuahang.imp.*;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Erp_ChinhsachduyetmuahangUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public Erp_ChinhsachduyetmuahangUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    Utility util=new Utility();
	    HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    //out.println(userId);
		String action = util.getAction(querystring);
		String csId = util.getId(querystring);

	    if (userId.length()==0){
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    }
	    	
   	    IChinhsachduyetmuahang csBean = new Chinhsachduyetmuahang();
   	    csBean.SetUserId(userId);
   	    csBean.setCtyId(ctyId);
   	    csBean.setCsId(csId);
   	    csBean.init();
   	    
		// Data is saved into session
		session.setAttribute("csBean", csBean);
		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangUpdate.jsp";
		if(querystring.contains("display"))
			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangDisplay.jsp";
   		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    Utility util = new Utility();
	    
	    HttpSession session = request.getSession();
	    String ctyId = (String)session.getAttribute("congtyId");
	    String userId = util.antiSQLInspection(request.getParameter("userId"));

	    IChinhsachduyetmuahang csBean = new Chinhsachduyetmuahang();
	    csBean.SetUserId(userId);
   	    csBean.setCtyId(ctyId);

   	    String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangUpdate.jsp";
	    
	    String csId = util.antiSQLInspection(request.getParameter("csId"));
	    if(csId == null) csId = "";
	    	
	    String dvthId = request.getParameter("dvthId");
	    if(dvthId == null) dvthId = "";
	    System.out.println("dvthId = " + dvthId);
	    String kcpId = util.antiSQLInspection(request.getParameter("kcpId"));
	    if(kcpId == null) kcpId = "";
	    
	    String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
	    if(diengiai == null) diengiai = "";
	    String hoatdong = util.antiSQLInspection(request.getParameter("hoatdong"));
	    
	    String tab = util.antiSQLInspection(request.getParameter("current_tab"));
	    
		System.out.println("HOATDONG: "+hoatdong);
		if("on".equalsIgnoreCase(hoatdong))
		{
			hoatdong = "1";
		}else{
			hoatdong = "0";
		}
		System.out.println("KCPID = " + kcpId);
	    
	    String[] chon = request.getParameterValues("chon" + kcpId);
	    String[] quyetdinh = request.getParameterValues("quyetdinh" + kcpId);

	    String nccId;
	    String spId;

	    //if(chon == null || quyetdinh == null) return;
   	    csBean.setCsId(csId);
   	    csBean.setDvthId(dvthId);
   	    csBean.setKcpId(kcpId);

   	    csBean.setRequest(request);
   	    csBean.setDiengiai(diengiai);   	 
   	    csBean.setHoatDong(hoatdong);

	    csBean.setTab(tab);
		String action = request.getParameter("action"); 
	
	    System.out.println("csId:" + csId);
   	    if(action.equals("dvth")){
   	    	csBean.init_new();
   	   	    
   			// Data is saved into session
   			session.setAttribute("csBean", csBean);
   			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangNew.jsp";
   			response.sendRedirect(nextJSP);
   	    }
   	    
   	    if(action.equals("cpluulai")){
   	    	csBean.setKcpId(kcpId);
   	   	    csBean.setChon(chon);
   	   	    csBean.setQuyetdinh(quyetdinh);
   	    	csBean.CpSave();
   	    	
  	   	    csBean.init();
   			
   			session.setAttribute("csBean", csBean);
   			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangUpdate.jsp";
   	   		response.sendRedirect(nextJSP);
   	    }
   	    
   	    if(action.equals("nccluulai")){
   	    	csBean.setKcpId(kcpId);
	    	nccId = util.antiSQLInspection(request.getParameter("mancc" + kcpId));
		    String[] nccchon = request.getParameterValues("nccchon" + kcpId);
		    String[] nccquyetdinh = request.getParameterValues("nccquyetdinh" + kcpId);
	   	    csBean.setNccChon(nccchon);
	   	    csBean.setNccQuyetdinh(nccquyetdinh);
	    	csBean.setNccId(nccId);
	    	
	    	csBean.NccSave();
	    	
  	   	    csBean.init();
   			
   			session.setAttribute("csBean", csBean);
   			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangUpdate.jsp";
   	   		response.sendRedirect(nextJSP);
	    }
   	    if(action.equals("spluulai")){
	    	csBean.setKcpId(kcpId);
	    	spId = util.antiSQLInspection(request.getParameter("masp" + kcpId));
	    	csBean.setSpId(spId);
		    String[] spchon = request.getParameterValues("spchon" + kcpId);
		    String[] spquyetdinh = request.getParameterValues("spquyetdinh" + kcpId);
	   	    csBean.setSpChon(spchon);
		    csBean.setSpQuyetdinh(spquyetdinh);
	    	csBean.SpSave();
	    	
  	   	    csBean.init();
   			
   			session.setAttribute("csBean", csBean);
   			nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangUpdate.jsp";
   	   		response.sendRedirect(nextJSP);
	    }
   	    
   	    if(action.equals("SaveAll")){
   	    	csBean.setMessage("Lưu dữ liệu thành công");
   	    	String[] kcpIds = request.getParameterValues("kcpIds");
   	    	csBean.setKcpIds(kcpIds);
   	    	
   	    	if(kcpIds != null){
   	    		for(int i = 0; i < kcpIds.length; i++){
   	    			chon = request.getParameterValues("chon" + kcpIds[i]);
   	    			quyetdinh = request.getParameterValues("quyetdinh" + kcpIds[i]);
   	    			csBean.setKcpId(kcpIds[i]);
   	    	    	csBean.setChon(chon);
   	    	    	csBean.setQuyetdinh(quyetdinh);
   	    	    	csBean.setRequest(request);
   	    	    	csBean.CpSave();
   	    		}
   	    	}else{
   	    		csBean.CpSave();
   	    		csBean.init();
   	    		session.setAttribute("csBean", csBean);
   	    		nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangUpdate.jsp";
   	    		response.sendRedirect(nextJSP);
   	    	}
   	    	
   	    	String[] nccthutu = request.getParameterValues("nccthutu");
   	    	if(nccthutu != null){
   	    		for(int i = 0; i < nccthutu.length; i++){
   	    			csBean.setKcpId(nccthutu[i]);
   	    			nccId = util.antiSQLInspection(request.getParameter("mancc" + nccthutu[i]));
   	    			csBean.setNccId(nccId);
   		    	
   	    			String[] nccchon = request.getParameterValues("nccchon" + nccthutu[i]);
   			    	String[] nccquyetdinh = request.getParameterValues("nccquyetdinh" + nccthutu[i]);
   			    	csBean.setNccChon(nccchon);
   			    	csBean.setNccQuyetdinh(nccquyetdinh);
   		   	    
   			    	csBean.NccSave();
   	    		}
   	    	}
   	    	
   	    	String[] spthutu = request.getParameterValues("spthutu");
   	    	if(spthutu != null){
   	    		for(int i = 0; i < spthutu.length; i++){
   	    			csBean.setKcpId(spthutu[i]);
   	    			spId = util.antiSQLInspection(request.getParameter("masp" + spthutu[i]));
   	    			csBean.setSpId(spId);
   		    	
   	    			String[] spchon = request.getParameterValues("spchon" + spthutu[i]);
   	    			String[] spquyetdinh = request.getParameterValues("spquyetdinh" + spthutu[i]);
   	    			csBean.setSpChon(spchon);
   	    			csBean.setSpQuyetdinh(spquyetdinh);
   	    			csBean.SpSave();
   	    		}
   	    	}
   	    	nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangUpdate.jsp";
   	   	    csBean.init();
   			
   			session.setAttribute("csBean", csBean);
   			
   	   		response.sendRedirect(nextJSP);
   	    }
   	    if(action.equals("search")){
   	    	System.out.println("SEARCH");
   	    	
   	    	if(csId.length() == 0){
   	    		csBean.init_new();
   				session.setAttribute("csBean", csBean);
   				nextJSP = "/TraphacoHYERP/pages/Erp/Erp_ChinhSachDuyetMuaHangNew.jsp";
   		   		response.sendRedirect(nextJSP);
   		   		return;
   	    	}
   	    }



	}

}
