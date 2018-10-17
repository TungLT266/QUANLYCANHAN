package geso.traphaco.center.servlets.hoadonphelieu;

import geso.traphaco.center.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.center.beans.hoadonphelieu.IErpXuatkmkhonghdList;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpXuatkmkhonghd;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpXuatkmkhonghdList;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpHoaDonPL_SP;
import geso.traphaco.center.beans.hoadonphelieu.IErpXuatkmkhonghd;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpXuatkmkhonghdUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpXuatkmkhonghdUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpXuatkmkhonghd csxBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    csxBean = new ErpXuatkmkhonghd(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    csxBean.setCongtyId(ctyId);
	    csxBean.setId(id);
	    csxBean.setUserId(userId);
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHDUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHDDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpXuatkmkhonghd csxBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	csxBean = new ErpXuatkmkhonghd();
	    }else{
	    	csxBean = new ErpXuatkmkhonghd(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		csxBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		csxBean.setCongtyId(ctyId);
		
		String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		if (ngayghinhan == null)
			ngayghinhan = "";
		csxBean.setNgayghinhan(ngayghinhan);
		
		String xuatchoId = util.antiSQLInspection(request.getParameter("xuatchoId"));
		if (xuatchoId == null)
			xuatchoId = "";
		csxBean.setXuatcho(xuatchoId);
				
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		csxBean.setKhId(khId);
		
		String mavuviec = util.antiSQLInspection(request.getParameter("mavuviec"));
		if (mavuviec == null)
			mavuviec = "";
		csxBean.setMavuviec(mavuviec);
		
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
		
		String[] spMa = request.getParameterValues("spMa");
		String[] spTen = request.getParameterValues("spTen");		
		String[] donvi = request.getParameterValues("donvi");
		String[] soluong = request.getParameterValues("soluong");
		String[] dongia = request.getParameterValues("dongia");
		String[] thuevat = request.getParameterValues("thuevat");
		String[] vat = request.getParameterValues("vat");
		String[] thanhtien = request.getParameterValues("thanhtien");
		String[] ghichu = request.getParameterValues("ghichusanpham");

		if( spMa != null )
		{
			int m = 0;
			
			Hashtable<String, String> sp_chitiet = new Hashtable<String, String>();
			while(m < spMa.length)
			{		
				if(spMa[m].trim().length() > 0)
				{
					IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
					sanpham.setMaSanPham(spMa[m]);
					sanpham.setTenSanPham(spTen[m]);
					sanpham.setDonViTinh(donvi[m]);
					sanpham.setDonGia(dongia[m]);
					sanpham.setThuevat(thuevat[m]);
					sanpham.setVat(vat[m]);
					sanpham.setSoLuong(soluong[m]);
					sanpham.setThanhTien(thanhtien[m]);
					sanpham.setGhiChu1(ghichu[m]);
					
					spList.add(sanpham);
					
					String[] spChon = request.getParameterValues(spMa[m] + "_spCHON");	
					
					if( spChon != null )
					{
						for(int j = 0; j < spChon.length; j++)
						{
							String key = spMa[m] + "__" + spChon[j];
							//System.out.println("::: KEY SVL: " + key);
							sp_chitiet.put(key, "1");
						}
					}
				}
				m++ ;
			}
			csxBean.setSp_Chitiet(sp_chitiet);
		}
		csxBean.setSanPhamList(spList);
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(csxBean.create()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
	 	    		csxBean.createRS();
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHDNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpXuatkmkhonghdList obj = new ErpXuatkmkhonghdList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHD.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(csxBean.update()))
				{
	 				session.setAttribute("csxBean", csxBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
	 	    		csxBean.createRS();
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHDUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpXuatkmkhonghdList obj = new ErpXuatkmkhonghdList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHD.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
		else
		{
			csxBean.createRS();
			
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHDNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Center/ErpXuatKMKhongHDUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
