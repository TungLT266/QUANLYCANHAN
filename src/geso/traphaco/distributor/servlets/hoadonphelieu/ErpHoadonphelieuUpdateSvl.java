package geso.traphaco.distributor.servlets.hoadonphelieu;

import geso.traphaco.distributor.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.distributor.beans.hoadonphelieu.IErpHoadonphelieuList;
import geso.traphaco.distributor.beans.hoadonphelieu.imp.ErpHoadonphelieu;
import geso.traphaco.distributor.beans.hoadonphelieu.imp.ErpHoadonphelieuList;
import geso.traphaco.distributor.beans.hoadonphelieu.imp.ErpHoaDonPL_SP;
import geso.traphaco.distributor.beans.hoadonphelieu.IErpHoadonphelieu;
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

public class ErpHoadonphelieuUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpHoadonphelieuUpdateSvl() {
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
	    
	    csxBean.init();
        session.setAttribute("csxBean", csxBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
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
		
		String loaick = util.antiSQLInspection(request.getParameter("loaick"));
		if (loaick == null)
			loaick = "";
		csxBean.setLoaiCK(loaick);
		
		String timsohoadon = util.antiSQLInspection(request.getParameter("timsohoadon"));
		if (timsohoadon == null)
			timsohoadon = "";
		csxBean.settimHoadon(timsohoadon);
		
		String doanhthuId = util.antiSQLInspection(request.getParameter("doanhthuId"));
		if (doanhthuId == null)
			doanhthuId = "";
		csxBean.setKhoanmucDTId(doanhthuId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		csxBean.setDiengiai(diengiai);
		
		/*String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		csxBean.setNccId(nccId);*/
		
		String KhIdgoc = util.antiSQLInspection(request.getParameter("khIdgoc"));
		if (KhIdgoc == null)
			KhIdgoc = "";				
		csxBean.setkhIdGoc(KhIdgoc);
    	
    	System.out.println("KhIdgoc:"+KhIdgoc);
    		    	
    	if(KhIdgoc.length() > 0)
    	{
    		String[] KhId = KhIdgoc.split(" -- ");
    		
    		csxBean.setkhId(KhId[0]);
    		csxBean.setisNPP(KhId[1]);	    		

	    	System.out.println("khId:"+KhId[0]);
    	}
    /*	
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		csxBean.setkhId(khId);*/
		
		/*System.out.println("KHID: "+ khId);*/
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		csxBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		csxBean.setDenngay(denngay);
		
		String nccTen = util.antiSQLInspection(request.getParameter("nccTen"));
		if (nccTen == null)
			nccTen = "";
		csxBean.setNccTen(nccTen);
		
		String vat = util.antiSQLInspection(request.getParameter("vat"));
		if (vat == null)
			vat = "0";
		csxBean.setVat(vat);
		
		String bvat = util.antiSQLInspection(request.getParameter("bvat"));
		if (bvat == null)
			bvat = "0";
		csxBean.setBvat(bvat);
		
		String avat = util.antiSQLInspection(request.getParameter("avat"));
		if (avat == null)
			avat = "0";
		csxBean.setAvat(avat);
		
		String poId = util.antiSQLInspection(request.getParameter("poId"));
		if (poId == null)
			poId = "";
		csxBean.setPOId(poId);
		
		String tenhanghoadichvu = util.antiSQLInspection(request.getParameter("tenhanghoadichvu"));
		if (tenhanghoadichvu == null)
			tenhanghoadichvu = "";
		csxBean.settenhanghoadichvu(tenhanghoadichvu);
		
		String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
		if (hinhthuc == null)
			hinhthuc = "";
		csxBean.sethinhthucthanhtoan(hinhthuc);
		
		String nguoimuahang = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		if (nguoimuahang == null)
			nguoimuahang = "";
		csxBean.setNguoiMuaHang(nguoimuahang);
		
		String donvi = util.antiSQLInspection(request.getParameter("donvi"));
		if (donvi == null)
			donvi = "";
		csxBean.setDonVi(donvi);
		
		String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";
		csxBean.setDiaChi(diachi);
		
		String masothue = util.antiSQLInspection(request.getParameter("masothue"));
		if (masothue == null)
			masothue = "";
		csxBean.setMST(masothue);
		
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		csxBean.setKbhId(kbhId);
		
		String taikhoanghino = util.antiSQLInspection(request.getParameter("taikhoanghino"));
		if (taikhoanghino == null)
			taikhoanghino = "";
		csxBean.setTaikhoanghinoId(taikhoanghino);
		
		String taikhoandoanhthuId = util.antiSQLInspection(request.getParameter("taikhoandoanhthu"));
		if (taikhoandoanhthuId == null)
			taikhoandoanhthuId = "";
		csxBean.setTaikhoandoanhthuId(taikhoandoanhthuId);
		
		String[] hdid = request.getParameterValues("hdid");
		
		String hd = "";
		
		if (hdid != null) 
		{
			for(int i = 0; i < hdid.length; i++)
			{
				hd += hdid[i] + ",";
			}
			if(hd.trim().length() > 0)
			{
				hd = hd.substring(0, hd.length() - 1);
				csxBean.setHdId(hd);
				System.out.println("hd"+hd);
			}
		}
		
		
		List<IErpHoaDonPL_SP> spList = new ArrayList<IErpHoaDonPL_SP>();
		
		String[] tenSanpham = request.getParameterValues("diengiai_sanpham");
		
		String[] sanphamId = request.getParameterValues("sanphamId");
		
		String[] donvitinh = request.getParameterValues("donvitinh");
		
		String[] dvt_fk  = request.getParameterValues("dvt_fk");
		
		String[] soluong = request.getParameterValues("soluong");
		
		String[] dongia = request.getParameterValues("dongia");
		
		String[] dongiadagiam = request.getParameterValues("dongiadagiam");
		
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
					
					if(dongia[m].trim().length()<=0) dongia[m] = "0";
					sanpham.setDonGia(dongia[m]);
					
					if(dongiadagiam[m].trim().length()<=0) dongiadagiam[m] = "0";					
					sanpham.setDonGiaDaGiam(dongiadagiam[m]);
					
					if(soluong[m].trim().length()<=0) soluong[m] = "0";						
					sanpham.setSoLuong(soluong[m]);
					
					sanpham.setIdSanPham(sanphamId[m]);
					sanpham.setThanhTien(thanhtien[m]);
					sanpham.setGhiChu1(ghichu[m]);
					sanpham.setDVT_FK(dvt_fk[m]);
					
					System.out.println("DVT:"+dvt_fk[m]);
					System.out.println("SP:"+sanphamId[m]);
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
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpHoadonphelieuList obj = new ErpHoadonphelieuList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieu.jsp";
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
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpHoadonphelieuList obj = new ErpHoadonphelieuList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
	
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieu.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
 		else if(action.equals("loadhd"))
 		{
 			csxBean.createRS();
 			csxBean.loadhd();
 			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
 		}
 		
		else
		{
			csxBean.createRS();
			
			session.setAttribute("userId", userId);
			session.setAttribute("csxBean", csxBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonPheLieuUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
