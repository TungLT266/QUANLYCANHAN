package geso.traphaco.erp.servlets.kiemnghiemchitiet;

import geso.traphaco.distributor.util.Utility;
 
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhieuKiemDinh;
import geso.traphaco.erp.beans.kiemnghiemchitiet.IErpKiemNghiemChiTiet;
import geso.traphaco.erp.beans.kiemnghiemchitiet.IErpKiemNghiemChiTietList;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.ErpKiemNghiemChiTiet;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.ErpKiemNghiemChiTietList;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class YCLayMauKiemNghiemSvl
 */
@WebServlet("/ErpKiemNghiemChiTietSvl")
public class ErpKiemNghiemChiTietSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ErpKiemNghiemChiTietSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String id = util.getId(querystring); 
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		PrintWriter out;
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		
		if(id != null && id.trim().length() > 0){
			
			IErpKiemNghiemChiTiet obj = new ErpKiemNghiemChiTiet(id);
			obj.init();
			obj.createRs();
			String nextJSP;
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTietDisplay.jsp";

			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			out = response.getWriter();
			 
		    userId = util.getUserId(querystring);
		    String ctyId = (String)session.getAttribute("congtyId");
		    String nppId = (String)session.getAttribute("nppId");
		    out.println(userId);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    
		    id = util.getId(querystring);  
		    if(id == null) id = "";
		    IErpKiemNghiemChiTietList obj = new ErpKiemNghiemChiTietList(id);
	        obj.setCtyId(ctyId);
			obj.setUserId(userId);			
			obj.setNppId(nppId);
			obj.createRs();
			obj.init();
	        String nextJSP;
	        nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTiet.jsp";
	 
	        session.setAttribute("obj", obj);
	        response.sendRedirect(nextJSP);
		}
	}

 
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  
		String ctyId = (String)session.getAttribute("congtyId");
		String sum = (String) session.getAttribute("sum");
		
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		String action = request.getParameter("action");
		String nextJSP;
		PrintWriter out;
		IErpKiemNghiemChiTietList khlist;
		Utility util = new Utility();
		String querystring = request.getQueryString();
		
		String id = util.antiSQLInspection(request.getParameter("id"));
		if (id == null)
			id = ""; 
		
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		if(action.equals("new")){
			IErpKiemNghiemChiTiet obj = new ErpKiemNghiemChiTiet(id);
			obj.createRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTietNew.jsp";
			session.setAttribute("obj", obj);
	        response.sendRedirect(nextJSP);
		}
		else if(action.equals("chot")){
			 
			IErpKiemNghiemChiTiet obj = new ErpKiemNghiemChiTiet (id);
			obj.setCtyId(ctyId);
			
			khlist = new ErpKiemNghiemChiTietList();
			khlist.setUserId(userId);
			khlist.setCtyId(ctyId);
			
			if(!obj.chot(id))
    		{
				khlist.setMsg(obj.getMsg());
    		}	    	
    		
			khlist.init(); 
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTiet.jsp";
			session.setAttribute("obj", khlist);
	        response.sendRedirect(nextJSP);
		}
		else if(action.equals("bochot")){
			 
			IErpKiemNghiemChiTiet obj = new ErpKiemNghiemChiTiet (id);
			obj.setCtyId(ctyId);
			
			khlist = new ErpKiemNghiemChiTietList();
			khlist.setUserId(userId);
			khlist.setCtyId(ctyId);
			
			if(!obj.bochot(id))
    		{
				khlist.setMsg(obj.getMsg());
    		}	    	
    		
			khlist.init(); 
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTiet.jsp";
			session.setAttribute("obj", khlist);
	        response.sendRedirect(nextJSP);
		}
		else if(action.equals("xoa")){
			 
			IErpKiemNghiemChiTiet obj = new ErpKiemNghiemChiTiet (id);
			obj.setCtyId(ctyId);
			
			khlist = new ErpKiemNghiemChiTietList();
			khlist.setUserId(userId);
			khlist.setCtyId(ctyId);
			
			if(!obj.xoa(id))
    		{
				khlist.setMsg(obj.getMsg());
    		}	    	
    		
			khlist.init(); 
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTiet.jsp";
			session.setAttribute("obj", khlist);
	        response.sendRedirect(nextJSP);
		}
		else
		{ 
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    
			session.setMaxInactiveInterval(30000);
			out = response.getWriter();
 
		    userId = util.getUserId(querystring);
		    ctyId = (String)session.getAttribute("congtyId");
		    String nppId = (String)session.getAttribute("nppId");
		    out.println(userId);
		    IErpKiemNghiemChiTietList obj = new ErpKiemNghiemChiTietList(id);
	        obj.setCtyId(ctyId);
			obj.setUserId(userId);			
			obj.setNppId(nppId);
			getSearchQuery(obj, request);
			obj.createRs(); 
			obj.init();
	        nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemNghiemChiTiet.jsp";
	 
	        session.setAttribute("obj", obj);
	        response.sendRedirect(nextJSP);
		}
	}
	
	private void getSearchQuery(IErpKiemNghiemChiTietList obj, HttpServletRequest request) {
		Utility util = new Utility();
		String ngayct = util.antiSQLInspection(request.getParameter("ngayct"));
		if (ngayct == null)
			ngayct = "";					
		obj.setNgayChungTu(ngayct);;
 
		String phongbanthId = util.antiSQLInspection(request.getParameter("phongbanthId"));
		if (phongbanthId == null)
			phongbanthId = "";				
		obj.setPhongBanId(phongbanthId);
 
		String sanphamkiemdinh = util.antiSQLInspection(request.getParameter("sanphamkiemdinh"));
		if (sanphamkiemdinh == null)
			sanphamkiemdinh = "";				
		obj.setSanphamId(sanphamkiemdinh);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";				
		obj.setTrangthai(trangthai);
 
	}
}
