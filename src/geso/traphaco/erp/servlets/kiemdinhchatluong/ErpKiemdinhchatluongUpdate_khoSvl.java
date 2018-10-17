package geso.traphaco.erp.servlets.kiemdinhchatluong;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.kiemdinhchatluong.IErpKiemdinhchatluong_kho;
import geso.traphaco.erp.beans.kiemdinhchatluong.imp.ErpKiemdinhchatluong_kho;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpKiemdinhchatluongUpdate_khoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpKiemdinhchatluongUpdate_khoSvl() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong_kho kdcl;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    kdcl = new ErpKiemdinhchatluong_kho(id);
	    kdcl.setId(id);
	    kdcl.setUserId(userId);
	    kdcl.setCongtyId((String)session.getAttribute("congtyId"));
	    
	    kdcl.init("");
        session.setAttribute("kdcl", kdcl);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_KhoUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_KhoDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		NumberFormat formatter = new DecimalFormat("#######.###");  
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpKiemdinhchatluong_kho kdcl;
		
		Utility util = new Utility();
		String id = util.antiSQLInspection(request.getParameter("id"));
		
		String loaispId = util.antiSQLInspection(request.getParameter("loaispId"));
		if(loaispId == null) loaispId = "";
		
		String khoId = util.antiSQLInspection(request.getParameter("khoId"));
		if(khoId == null) khoId = "";
		
		String khochoxulyId = util.antiSQLInspection(request.getParameter("khochoxulyId"));
		if(khochoxulyId == null) khochoxulyId = "";

		String spId = util.antiSQLInspection(request.getParameter("spId"));
		if(spId == null) spId = "";
			
		String solo = util.antiSQLInspection(request.getParameter("solo"));
		if(solo == null) solo = "";
		
		String spTen = util.antiSQLInspection(request.getParameter("spTen"));
		if(spTen == null) spTen = "";
		
		String ngaykiem = util.antiSQLInspection(request.getParameter("ngaykiem"));
		if(ngaykiem == null) ngaykiem = "";

		String soluongkodat = util.antiSQLInspection(request.getParameter("soluongkodat"));
		if(soluongkodat == null) soluongkodat = "";
		
		String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		if(ghichu == null) ghichu = "";
		
		if (id == null)
		{
			kdcl = new ErpKiemdinhchatluong_kho();
		} 
		else
		{
			kdcl = new ErpKiemdinhchatluong_kho(id);
		}

		String userId = util.antiSQLInspection(request.getParameter("userId"));
		kdcl.setUserId(userId);
		String congtyId = ((String) session.getAttribute("congtyId"));
		kdcl.setCongtyId(congtyId);
		
		kdcl.setSolo(solo);
		kdcl.setLOAISPID(loaispId);
		kdcl.setKhoId(khoId);
		kdcl.setKhoChoXuLyId(khochoxulyId);
		kdcl.setSpId(spId);
		kdcl.setSpTen(spTen);
		kdcl.setNgayKiem(ngaykiem);
		kdcl.setsoluongkhongDat(soluongkodat);
		kdcl.setGhichu(ghichu.trim());
		
		String action = request.getParameter("action");
		if (action.equals("save"))
		{
			if (!(kdcl.updateKiemdinh( request)))
			{
				kdcl.createRs();
				session.setAttribute("kdcl", kdcl);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_kho.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				//System.out.println("Kiem dinh OK...");
				IErpKiemdinhchatluong_kho obj = new ErpKiemdinhchatluong_kho();
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_kho.jsp";
				response.sendRedirect(nextJSP);
			}
		} 
		else if (action.equals("duyet"))
		{
			if ( !kdcl.updateKiemdinh( request) || !(kdcl.duyetKiemDinh()))
			{
				kdcl.createRs();
				session.setAttribute("kdcl", kdcl);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_KhoUpdate.jsp";
				response.sendRedirect(nextJSP);
			} 
			else
			{
				IErpKiemdinhchatluong_kho obj = new ErpKiemdinhchatluong_kho();
			    obj.setUserId(userId);
			    obj.init("");
				session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_kho.jsp";
			    response.sendRedirect(nextJSP);
			}
		}else{
	    	
			kdcl.setUserId(userId);
			kdcl.createRs();
    		
	    	session.setAttribute("kdcl", kdcl);  	
    		session.setAttribute("userId", userId);
    		if(id.length() == 0){
    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_KhoNew.jsp");
    		}else{
    			response.sendRedirect("/TraphacoHYERP/pages/Erp/ErpKiemDinhChatLuong_KhoUpdate.jsp");
    		}

		}
	}
	
	
}
