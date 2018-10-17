package geso.traphaco.center.servlets.hoadonphelieu;

import geso.traphaco.center.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.center.beans.hoadonphelieu.IErpHoadonphelieuList;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpHoadonphelieu;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpHoadonphelieuList;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpHoaDonPL_SP;
import geso.traphaco.center.beans.hoadonphelieu.IErpHoadonphelieu;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadonphelieuUpdateSvlTT extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpHoadonphelieuUpdateSvlTT() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpHoadonphelieu csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new ErpHoadonphelieu(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    csxBean.setCongtyId(ctyId);
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    System.out.println("vao get::::::::::::::::::");
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieuUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieuDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		   System.out.println("vao post::::::::::::::::::");
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpHoadonphelieu csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new ErpHoadonphelieu();
	    }else{
	    	csxBean = new ErpHoadonphelieu(id);
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
		
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		csxBean.setKhId(khId);
		
		String bvat = util.antiSQLInspection(request.getParameter("bvat"));
		if (bvat == null)
			bvat = "0";
		csxBean.setBvat(bvat.replaceAll(",", ""));
		
		String avat = util.antiSQLInspection(request.getParameter("avat"));
		if (avat == null)
			avat = "0";
		csxBean.setAvat(avat.replaceAll(",", ""));
		
		String tienvat = util.antiSQLInspection(request.getParameter("tienvat"));
		if (tienvat == null)
			tienvat = "0";
		csxBean.setVat(tienvat.replaceAll(",", ""));
		
		
		List<IErpHoaDonPL_SP> spList = new ArrayList<IErpHoaDonPL_SP>();
		
		String[] tenSanpham = request.getParameterValues("diengiai_sanpham");
		
		String[] donvitinh = request.getParameterValues("donvitinh");
		
		String[] soluong = request.getParameterValues("soluong");
		
		String[] dongia = request.getParameterValues("dongia");
		
		String[] thuevat = request.getParameterValues("thuevat");
		
		String[] vat = request.getParameterValues("vat");
		
		String[] thanhtien = request.getParameterValues("thanhtien");
		
		String[] ghichu = request.getParameterValues("ghichusanpham");

		if(tenSanpham!= null)
		{
			int m = 0;
			
			while(m < tenSanpham.length)
			{				
				if(tenSanpham[m].trim().length() > 0)
				{
					IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
					sanpham.setTenSanPham(tenSanpham[m]);
					sanpham.setDonViTinh(donvitinh[m]);
					sanpham.setDonGia(dongia[m]);
					sanpham.setThuevat(thuevat[m]);
					sanpham.setVat(vat[m]);
					sanpham.setSoLuong(soluong[m]);
					sanpham.setThanhTien(thanhtien[m]);
					sanpham.setGhiChu1(ghichu[m]);
					spList.add(sanpham);
				}
				m++ ;
			}
		}
		
		csxBean.setSanPhamList(spList);
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(csxBean.createGiamgia()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
	 	    		csxBean.createRS();
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieuNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpHoadonphelieuList obj = new ErpHoadonphelieuList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieu.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.updateGiamgia()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
	 	    		csxBean.createRS();
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieuUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpHoadonphelieuList obj = new ErpHoadonphelieuList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieu.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			csxBean.createRS();
			
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieuNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonPheLieuUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
