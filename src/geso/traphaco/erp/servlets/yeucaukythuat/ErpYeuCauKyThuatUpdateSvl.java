package geso.traphaco.erp.servlets.yeucaukythuat;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucaukythuat.IErpYeuCauKyThuat;
import geso.traphaco.erp.beans.yeucaukythuat.IErpYeuCauKyThuatList;
import geso.traphaco.erp.beans.yeucaukythuat.imp.ErpYeuCauKyThuat;
import geso.traphaco.erp.beans.yeucaukythuat.imp.ErpYeuCauKyThuatList;
import geso.traphaco.erp.beans.yeucaukythuat.imp.ErpYeuCauKyThuat_HoaChat;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ErpYeuCauKyThuatUpdateSvl
 */
@WebServlet("/ErpYeuCauKyThuatUpdateSvl")
public class ErpYeuCauKyThuatUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpYeuCauKyThuatUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId == null || userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpYeuCauKyThuat obj = new ErpYeuCauKyThuat();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String Id = util.getId(querystring);
    	obj.setId(Id);
    	
    	obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuatNew.jsp";
	    if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuatDisplay.jsp";
        }
	    
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    IErpYeuCauKyThuat obj = new ErpYeuCauKyThuat();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null)
	    	id = "";
	    obj.setId(id);
	    
	    String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		obj.setMa(ma);
		
		String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String thongsotu = util.antiSQLInspection(request.getParameter("thongsotu"));
		if (thongsotu == null)
			thongsotu = "";
		obj.setThongsoTu(thongsotu);
		
		String thongsoden = util.antiSQLInspection(request.getParameter("thongsoden"));
		if (thongsoden == null)
			thongsoden = "";
		obj.setThongsoDen(thongsoden);
		
		String donvitinh = util.antiSQLInspection(request.getParameter("donvitinh"));
		if (donvitinh == null)
			donvitinh = "";
		obj.setDvt(donvitinh);
		
		String gioihan = util.antiSQLInspection(request.getParameter("gioihan"));
		if (gioihan == null)
			gioihan = "";
		obj.setGioihan(gioihan);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null || trangthai.length() == 0)
			trangthai = "0";
		obj.setTrangthai(trangthai);
		
		String[] hoachatid = request.getParameterValues("hoachat");
		String[] soluong = request.getParameterValues("soluong");
		String[] maso = request.getParameterValues("maso");
		String[] cachpha = request.getParameterValues("cachpha");
		
		List<ErpYeuCauKyThuat_HoaChat> hoachatList = new ArrayList<ErpYeuCauKyThuat_HoaChat>();
		ErpYeuCauKyThuat_HoaChat hoachat;
		for(int i=0; i<hoachatid.length; i++){
			hoachat = new ErpYeuCauKyThuat_HoaChat();
			
			hoachat.setHoachat(hoachatid[i]);
			hoachat.setSoluong(soluong[i]);
			hoachat.setMaso(maso[i]);
			hoachat.setCachpha(cachpha[i]);
			
			hoachatList.add(hoachat);
		}
		obj.setHoachatList(hoachatList);
	    
	    if(action.equals("save")) {
	    	if(id.length() > 0){
	    		if(obj.update()){
	    			obj.DBClose();
	    			IErpYeuCauKyThuatList objList = new ErpYeuCauKyThuatList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuat.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuatNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	} else {
	    		if(obj.create()){
	    			obj.DBClose();
	    			IErpYeuCauKyThuatList objList = new ErpYeuCauKyThuatList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuat.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpYeucaukythuatNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	}

}
