package geso.traphaco.erp.servlets.kehoachphache;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaChe;
import geso.traphaco.erp.beans.kehoachphache.IErpKeHoachPhaCheList;
import geso.traphaco.erp.beans.kehoachphache.imp.ErpKeHoachPhaChe;
import geso.traphaco.erp.beans.kehoachphache.imp.ErpKeHoachPhaCheList;
import geso.traphaco.erp.beans.kehoachphache.imp.ErpKeHoachPhaChe_ChiTiet;

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
 * Servlet implementation class ErpKeHoachPhaCheUpdateSvl
 */
@WebServlet("/ErpKeHoachPhaCheUpdateSvl")
public class ErpKeHoachPhaCheUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpKeHoachPhaCheUpdateSvl() {
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
	    
	    IErpKeHoachPhaChe obj = new ErpKeHoachPhaChe();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String Id = util.getId(querystring);
    	obj.setId(Id);
    	
    	obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphacheNew.jsp";
	    if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphacheDisplay.jsp";
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
	    
	    IErpKeHoachPhaChe obj = new ErpKeHoachPhaChe();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    	action = "";
	    
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null)
	    	id = "";
	    obj.setId(id);
	    
	    String ngaykehoach = util.antiSQLInspection(request.getParameter("ngaykehoach"));
		if (ngaykehoach == null)
			ngaykehoach = "";
		obj.setNgaykehoach(ngaykehoach);
		
		String bophanthuchien = util.antiSQLInspection(request.getParameter("bophanthuchien"));
		if (bophanthuchien == null)
			bophanthuchien = "";
		obj.setBophanthuchien(bophanthuchien);
		
		String loai = util.antiSQLInspection(request.getParameter("loai"));
		if (loai == null)
			loai = "";
		obj.setLoai(loai);
		
		String sanpham = util.antiSQLInspection(request.getParameter("sanpham"));
		if (sanpham == null)
			sanpham = "";
		obj.setSanpham(sanpham);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String[] ngayphache = request.getParameterValues("ngayphache");
		String[] ghichu = request.getParameterValues("ghichu");
		
		List<ErpKeHoachPhaChe_ChiTiet> KhpcChitietList = new ArrayList<ErpKeHoachPhaChe_ChiTiet>();
		ErpKeHoachPhaChe_ChiTiet KhpcChitiet;
		for(int i=0; i<ngayphache.length; i++){
			KhpcChitiet = new ErpKeHoachPhaChe_ChiTiet();
			
			KhpcChitiet.setNgayphache(ngayphache[i]);
			KhpcChitiet.setGhichu(ghichu[i]);
			
			KhpcChitietList.add(KhpcChitiet);
		}
		obj.setKhpcChitietList(KhpcChitietList);
	    
	    if(action.equals("save")) {
	    	if(id.length() > 0){
	    		if(obj.update()){
	    			obj.DBClose();
	    			IErpKeHoachPhaCheList objList = new ErpKeHoachPhaCheList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphache.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphacheNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	} else {
	    		if(obj.create()){
	    			obj.DBClose();
	    			IErpKeHoachPhaCheList objList = new ErpKeHoachPhaCheList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphache.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			obj.createRs();
	    			
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKehoachphacheNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	}

}
