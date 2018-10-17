package geso.traphaco.erp.servlets.tailieuphachethuoc;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tailieuphachethuoc.IErpTaiLieuPhaCheThuoc;
import geso.traphaco.erp.beans.tailieuphachethuoc.IErpTaiLieuPhaCheThuocList;
import geso.traphaco.erp.beans.tailieuphachethuoc.imp.ErpTaiLieuPhaCheThuoc;
import geso.traphaco.erp.beans.tailieuphachethuoc.imp.ErpTaiLieuPhaCheThuocList;
import geso.traphaco.erp.beans.tailieuphachethuoc.imp.ErpTaiLieuPhaCheThuoc_ThongTin;

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
 * Servlet implementation class ErpTaiLieuPhaCheThuocUpdateSvl
 */
@WebServlet("/ErpTaiLieuPhaCheThuocUpdateSvl")
public class ErpTaiLieuPhaCheThuocUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpTaiLieuPhaCheThuocUpdateSvl() {
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
	    
	    IErpTaiLieuPhaCheThuoc obj = new ErpTaiLieuPhaCheThuoc();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String Id = util.getId(querystring);
    	obj.setId(Id);
    	
    	obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuocNew.jsp";
	    if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuocDisplay.jsp";
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
	    
	    IErpTaiLieuPhaCheThuoc obj = new ErpTaiLieuPhaCheThuoc();
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
		
		String noidung = util.antiSQLInspection(request.getParameter("noidung"));
		if (noidung == null)
			noidung = "";
		obj.setNoidung(noidung);
		
		String thuocthu = util.antiSQLInspection(request.getParameter("thuocthu"));
		if (thuocthu == null)
			thuocthu = "";
		obj.setThuocthu(thuocthu);
		
		String congthuc = util.antiSQLInspection(request.getParameter("congthuc"));
		if (congthuc == null)
			congthuc = "";
		obj.setCongthuc(congthuc);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null || trangthai.length() == 0)
			trangthai = "0";
		obj.setTrangthai(trangthai);
		
		String[] sanpham = request.getParameterValues("sanphamid");
		String[] soluong = request.getParameterValues("soluong");
		List<ErpTaiLieuPhaCheThuoc_ThongTin> thongtinList = new ArrayList<ErpTaiLieuPhaCheThuoc_ThongTin>();
		ErpTaiLieuPhaCheThuoc_ThongTin thongtin;
		for(int i = 0; i < sanpham.length; i++){
			thongtin = new ErpTaiLieuPhaCheThuoc_ThongTin();
			
			thongtin.setSanphamid(sanpham[i]);
			thongtin.setSoluong(soluong[i]);
			
			thongtinList.add(thongtin);
		}
		obj.setThongtinList(thongtinList);
	    
	    if(action.equals("save")) {
	    	if(id.length() > 0){
	    		if(obj.update()){
	    			obj.DBClose();
	    			IErpTaiLieuPhaCheThuocList objList = new ErpTaiLieuPhaCheThuocList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuoc.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuocNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	} else {
	    		if(obj.create()){
	    			obj.DBClose();
	    			IErpTaiLieuPhaCheThuocList objList = new ErpTaiLieuPhaCheThuocList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuoc.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTailieuphachethuocNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	}

}
