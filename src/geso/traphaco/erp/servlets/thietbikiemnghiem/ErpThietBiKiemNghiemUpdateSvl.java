package geso.traphaco.erp.servlets.thietbikiemnghiem;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.IErpLoaiHoSoList;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSo;
import geso.traphaco.erp.beans.loaihoso.imp.ErpLoaiHoSoList;
import geso.traphaco.erp.beans.thietbikiemnghiem.IThietBiKiemNghiem;
import geso.traphaco.erp.beans.thietbikiemnghiem.IThietBiKiemNghiemList;
import geso.traphaco.erp.beans.thietbikiemnghiem.imp.ThietBiKiemNghiem;
import geso.traphaco.erp.beans.thietbikiemnghiem.imp.ThietBiKiemNghiemList;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.oreilly.servlet.MultipartRequest;

/**
 * Servlet implementation class ErpThietBiKiemNghiemUpdateSvl
 */
@WebServlet("/ErpThietBiKiemNghiemUpdateSvl")
public class ErpThietBiKiemNghiemUpdateSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpThietBiKiemNghiemUpdateSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
	    
	    IThietBiKiemNghiem obj = new ThietBiKiemNghiem();
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
	    String Id = util.getId(querystring);
    	obj.setId(Id);
    	
    	obj.init();
		session.setAttribute("obj", obj);
	    String nextJSP = "/TraphacoHYERP/pages/Erp/ThietbikiemnghiemNew.jsp";
	    
	    if(querystring.indexOf("display") >= 0) {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ThietbikiemnghiemDisplay.jsp";
        }
	    
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();
	    Utility util = new Utility();
	    IThietBiKiemNghiem obj = new ThietBiKiemNghiem();
	    
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setCongtyId(ctyId);
	    
    	String userId = util.antiSQLInspection(request.getParameter("userId"));
    	obj.setUserId(userId);
    	
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
		
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if (ghichu == null)
			ghichu = "";
		obj.setGhichu(ghichu);
		
		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null || trangthai == "")
			trangthai = "0";
		obj.setTrangthai(trangthai);
    	
	    if(action.equals("save")) {
	    	if(id.length() > 0){
	    		if(obj.update()){
	    			obj.DBClose();
	    			IThietBiKiemNghiemList objList = new ThietBiKiemNghiemList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/Thietbikiemnghiem.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			session.setAttribute("obj", obj);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/ThietbikiemnghiemNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	} else {
	    		if(obj.create()){
	    			obj.DBClose();
	    			IThietBiKiemNghiemList objList = new ThietBiKiemNghiemList();
	    			
	    			objList.setUserId(userId);
	    			objList.setCongtyId(ctyId);
	    			objList.init();
	    		    
	    			session.setAttribute("obj", objList);
	    		    String nextJSP = "/TraphacoHYERP/pages/Erp/Thietbikiemnghiem.jsp";
	    			response.sendRedirect(nextJSP);
	    		} else {
	    			session.setAttribute("obj", obj);
	    			String nextJSP = "/TraphacoHYERP/pages/Erp/ThietbikiemnghiemNew.jsp";
	    			response.sendRedirect(nextJSP);
	    		}
	    	}
	    }
	}

}
