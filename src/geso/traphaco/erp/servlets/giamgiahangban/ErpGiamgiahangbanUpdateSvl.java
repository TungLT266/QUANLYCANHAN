package geso.traphaco.erp.servlets.giamgiahangban;

import geso.traphaco.erp.beans.giamgiahangban.IErpGiamgiahangban;
import geso.traphaco.erp.beans.giamgiahangban.IErpGiamgiahangbanList;
import geso.traphaco.erp.beans.giamgiahangban.imp.ErpGiamgiahangban;
import geso.traphaco.erp.beans.giamgiahangban.imp.ErpGiamgiahangbanList;
import geso.traphaco.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpGiamgiahangbanUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpGiamgiahangbanUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpGiamgiahangban csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new ErpGiamgiahangban(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    csxBean.setCongtyId(ctyId);
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBanUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBanDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpGiamgiahangban csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new ErpGiamgiahangban();
	    }else{
	    	csxBean = new ErpGiamgiahangban(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		csxBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		csxBean.setCongtyId(ctyId);
		
		String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		if (ngayghinhan == null)
			ngayghinhan = "";
		csxBean.setNgayghinhan(ngayghinhan);
		
		String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
		if (ngayhoadon == null)
			ngayhoadon = "";
		csxBean.setNgayhoadon(ngayhoadon);
		
		String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		if (kyhieuhoadon == null)
			kyhieuhoadon = "";
		csxBean.setKyhieuhoadon(kyhieuhoadon);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if (sohoadon == null)
			sohoadon = "";
		csxBean.setSohoadon(sohoadon);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		csxBean.setNccId(nccId);
		
		String nccTen = util.antiSQLInspection(request.getParameter("nccTen"));
		if (nccTen == null)
			nccTen = "";
		csxBean.setNccTen(nccTen);
		System.out.println("Ten NCC: " + nccTen);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "0";
		csxBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "0";
		csxBean.setDenngay(denngay);
		
		String tienteId = util.antiSQLInspection(request.getParameter("tienteId"));
		if (tienteId == null)
			tienteId = "0";
		csxBean.setTienteId(tienteId);

		String[] poIds = request.getParameterValues("poIds");
		if (poIds != null)
		{
			String poId = "";
			for(int i = 0; i < poIds.length; i++)
			{
				poId += poIds[i] + ",";
			}
			if(poId.trim().length() > 0)
				poId = poId.substring(0, poId.length() - 1);
			csxBean.setPOId(poId);
		}
		
		String[] hoadonid = request.getParameterValues("hoadonid");
		csxBean.setHoadonId(hoadonid);
		String[] hdSanpham = request.getParameterValues("hdSanpham");
		csxBean.setHoadonTen(hdSanpham);
		
		String[] kyhieuhd = request.getParameterValues("kyhieuhd");
		csxBean.setKyhieu(kyhieuhd);
		
		String[] ngayhd = request.getParameterValues("ngayhd");
		csxBean.setNgayHoaDon(ngayhd);
		
		String[] ghichuhd = request.getParameterValues("ghichuhd");
		csxBean.setGhichu(ghichuhd);
 
		String[] thanhtien = request.getParameterValues("thanhtienhd");
		csxBean.setTongtien(thanhtien);
		
		String[] sotiengiam = request.getParameterValues("sotientanggiam");
		csxBean.setSotien(sotiengiam);
		
		String[] loaihoadon = request.getParameterValues("loaihoadon");
		csxBean.setLoaihoadon(loaihoadon);
		
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(csxBean.createGiamgia()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBanNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpGiamgiahangbanList obj = new ErpGiamgiahangbanList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBan.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.updateGiamgia()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBanUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpGiamgiahangbanList obj = new ErpGiamgiahangbanList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBan.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			csxBean.createRS();
			
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBanNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpGiamGiaHangBanUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
