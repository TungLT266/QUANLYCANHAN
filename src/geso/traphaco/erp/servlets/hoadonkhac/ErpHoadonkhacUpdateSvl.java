package geso.traphaco.erp.servlets.hoadonkhac;


import geso.traphaco.erp.beans.hoadonkhac.IErpHoadonkhac;
import geso.traphaco.erp.beans.hoadonphelieu.IErpHoaDonPL_SP;
import geso.traphaco.erp.beans.hoadonkhac.IErpHoadonkhacList;
import geso.traphaco.erp.beans.hoadonkhac.imp.ErpHoadonkhac;
import geso.traphaco.erp.beans.hoadonkhac.imp.ErpHoadonkhacList;
import geso.traphaco.erp.beans.hoadonphelieu.imp.ErpHoaDonPL_SP;
import geso.dms.center.util.Utility;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadonkhacUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	PrintWriter out; 
       
    public ErpHoadonkhacUpdateSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IErpHoadonkhac hdkBean;
		
		this.out = response.getWriter();
		Utility util = new Utility();
		
    	String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    	    
	    String id = util.getId(querystring);
	   
	    hdkBean = new ErpHoadonkhac(id);
	    String ctyId = (String)session.getAttribute("congtyId");
	    
	    hdkBean.setCongtyId(ctyId);
	    hdkBean.setId(id);
	    hdkBean.setUserId(userId);
	    
	    hdkBean.init();
        session.setAttribute("hdkBean", hdkBean);
        
        String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacUpdate.jsp";
        if(querystring.indexOf("display") >= 0)
        {
        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacDisplay.jsp";
        }
        
        response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IErpHoadonkhac hdkBean;

		Utility util = new Utility();
	    String id = util.antiSQLInspection(request.getParameter("id"));	
	    if(id == null){  	
	    	hdkBean = new ErpHoadonkhac();
	    }else{
	    	hdkBean = new ErpHoadonkhac(id);
	    }
	    	    
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		hdkBean.setUserId(userId);	       
		
		String ctyId = (String)session.getAttribute("congtyId");
		hdkBean.setCongtyId(ctyId);
		
		String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		if (ngayghinhan == null)
			ngayghinhan = "";
		hdkBean.setNgayghinhan(ngayghinhan);
		
		String ngayhoadon = util.antiSQLInspection(request.getParameter("ngayhoadon"));
		if (ngayhoadon == null)
			ngayhoadon = "";
		hdkBean.setNgayhoadon(ngayhoadon);
		
		String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		if (kyhieuhoadon == null)
			kyhieuhoadon = "";
		hdkBean.setKyhieuhoadon(kyhieuhoadon);
		
		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if (sohoadon == null)
			sohoadon = "";
		hdkBean.setSohoadon(sohoadon);
		
		String loaick = util.antiSQLInspection(request.getParameter("loaick"));
		if (loaick == null)
			loaick = "";
		hdkBean.setLoaiCK(loaick);
		
		String timsohoadon = util.antiSQLInspection(request.getParameter("timsohoadon"));
		if (timsohoadon == null)
			timsohoadon = "";
		hdkBean.settimHoadon(timsohoadon);
		
		String doanhthuId = util.antiSQLInspection(request.getParameter("doanhthuId"));
		if (doanhthuId == null)
			doanhthuId = "";
		hdkBean.setKhoanmucDTId(doanhthuId);
		
		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		hdkBean.setDiengiai(diengiai);
		
		/*String nccId = util.antiSQLInspection(request.getParameter("nccId"));
		if (nccId == null)
			nccId = "";
		hdkBean.setNccId(nccId);*/
		
		String khId = util.antiSQLInspection(request.getParameter("khId"));
		if (khId == null)
			khId = "";
		hdkBean.setkhId(khId);
		
		System.out.println("KHID: "+ khId);
		
		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		hdkBean.setTungay(tungay);
		
		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		hdkBean.setDenngay(denngay);
		
		String nccTen = util.antiSQLInspection(request.getParameter("nccTen"));
		if (nccTen == null)
			nccTen = "";
		hdkBean.setNccTen(nccTen);
		
		String vat = util.antiSQLInspection(request.getParameter("vat"));
		if (vat == null)
			vat = "0";
		hdkBean.setVat(vat.replaceAll(",", ""));

		
		String bvat = util.antiSQLInspection(request.getParameter("bvat"));
		if (bvat == null)
			bvat = "0";
		hdkBean.setBvat(bvat.replaceAll(",", ""));

		
		String avat = util.antiSQLInspection(request.getParameter("avat"));
		if (avat == null)
			avat = "0";
		hdkBean.setAvat(avat.replaceAll(",", ""));
		
		String thoihanno = util.antiSQLInspection(request.getParameter("thoihanno"));
		if (thoihanno == null)
			thoihanno = "0";
		hdkBean.setThoihanno(thoihanno.replace(",", ""));
		
		String tienVAT = util.antiSQLInspection(request.getParameter("tienVAT"));
		if(tienVAT == null)
			tienVAT = "0";
		hdkBean.setTienVat(tienVAT.replaceAll(",", ""));
		
		String poId = util.antiSQLInspection(request.getParameter("poId"));
		if (poId == null)
			poId = "";
		hdkBean.setPOId(poId);
		
		String tenhanghoadichvu = util.antiSQLInspection(request.getParameter("tenhanghoadichvu"));
		if (tenhanghoadichvu == null)
			tenhanghoadichvu = "";
		hdkBean.settenhanghoadichvu(tenhanghoadichvu);
		
		String hinhthuc = util.antiSQLInspection(request.getParameter("hinhthuc"));
		if (hinhthuc == null)
			hinhthuc = "";
		hdkBean.sethinhthucthanhtoan(hinhthuc);
		
		String nguoimuahang = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		if (nguoimuahang == null)
			nguoimuahang = "";
		hdkBean.setNguoiMuaHang(nguoimuahang);
		
		String donvi = util.antiSQLInspection(request.getParameter("donvi"));
		if (donvi == null)
			donvi = "";
		hdkBean.setDonVi(donvi);
		
		String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		if (diachi == null)
			diachi = "";
		hdkBean.setDiaChi(diachi);
		
		String masothue = util.antiSQLInspection(request.getParameter("masothue"));
		if (masothue == null)
			masothue = "";
		hdkBean.setMST(masothue);
		
		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		hdkBean.setKbhId(kbhId);
		
		String taikhoanghino = util.antiSQLInspection(request.getParameter("taikhoanghino"));
		if (taikhoanghino == null)
			taikhoanghino = "";
		hdkBean.setTaikhoanghinoId(taikhoanghino);
		
		
		String taikhoandoanhthuId = util.antiSQLInspection(request.getParameter("taikhoandoanhthu"));
		if (taikhoandoanhthuId == null)
			taikhoandoanhthuId = "";
		hdkBean.setTaikhoandoanhthuId(taikhoandoanhthuId);
		
		
		String thanhlytaisanId = util.antiSQLInspection(request.getParameter("tltsId"));
		if (thanhlytaisanId == null)
			thanhlytaisanId = "";
		hdkBean.setThanhlyId(thanhlytaisanId);
		
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
				hdkBean.setHdId(hd);
				System.out.println("hd"+hd);
			}
		}
		
		
		List<IErpHoaDonPL_SP> spList = new ArrayList<IErpHoaDonPL_SP>();
		
		String[] tenSanpham = request.getParameterValues("diengiai_sanpham");
		
		String[] idSP = request.getParameterValues("idSP");
		
		String[] maSP = request.getParameterValues("maSP");
		
		String[] tenSP = request.getParameterValues("tenSP");
		
		String[] donvitinh = request.getParameterValues("donvitinh");
		
		String[] soluong = request.getParameterValues("soluong");
		
		String[] dongia = request.getParameterValues("dongia");
		
		String[] thuesuat = request.getParameterValues("thuesuat");
		
		String[] tienthue = request.getParameterValues("tienthue");
		
		String[] is_khongthue = request.getParameterValues("is_khongthueHD");
		
		String[] dongiadagiam = request.getParameterValues("dongiadagiam");
		
		String[] thanhtien = request.getParameterValues("thanhtien");
		
		String[] ghichu = request.getParameterValues("ghichusanpham");

			
		boolean isValid = true;
		/*double total = 0;*/
		
		if(tenSanpham!= null)
		{
			int m = 0;

			if(dongiadagiam == null){
				dongiadagiam = new String[tenSanpham.length];
				for(int i = 0; i < tenSanpham.length; i++){
					dongiadagiam[i] = "0";
				}
			}
			
			while(m < tenSanpham.length)
			{				
				if( ((doanhthuId.trim().equals("400002") || doanhthuId.trim().equals("400003")) && idSP != null && idSP[m].trim().length() >0 ) ||tenSanpham[m].trim().length() > 0)
				{
					IErpHoaDonPL_SP sanpham = new ErpHoaDonPL_SP();
					sanpham.setTenSanPham(tenSanpham[m]);
					sanpham.setDonViTinh(donvitinh[m]);
					if(idSP == null){
						sanpham.setIdSP("null");
					}else{
						sanpham.setIdSP(idSP[m]);
						sanpham.setMaSP(maSP[m]);
						sanpham.setTenSP(tenSP[m]);
					}
					
					
					if(dongia[m].trim().length()<=0) dongia[m] = "0";
					sanpham.setDonGia(dongia[m].replaceAll(",", ""));
					
					if(thuesuat[m].trim().length()<=0) thuesuat[m] = "0";
					sanpham.setThuesuat(thuesuat[m].replaceAll(",", ""));
					
					
					if(tienthue[m].trim().length()<=0) tienthue[m] = "0";
					sanpham.setTienthue(tienthue[m].replaceAll(",", ""));
					
					if(is_khongthue[m].trim().length()<=0) is_khongthue[m] = "0";
					sanpham.setIS_KHONGTHUE(is_khongthue[m].replaceAll(",", ""));
									
					if(dongiadagiam[m].trim().length()<=0) dongiadagiam[m] = "0";
					sanpham.setDonGiaDaGiam(dongiadagiam[m].replaceAll(",", ""));

					if(soluong[m].trim().length()<=0) soluong[m] = "0";						
					sanpham.setSoLuong(soluong[m].replaceAll(",", ""));

					/*if (doanhthuId.trim().equals("100003") || doanhthuId.trim().equals("100012")){  
						double tt = Double.parseDouble(dongia[m].replaceAll(",", ""))*Double.parseDouble(soluong[m].replaceAll(",", ""));
						thanhtien[m] = "" + Math.round(tt);
						total = total + Math.round(tt);
						
					}else if (doanhthuId.trim().equals("400002")){ // HĐ ĐIỀU CHỈNH TĂNG

						double tt = (Double.parseDouble(dongiadagiam[m].replaceAll(",", "")) - Double.parseDouble(dongia[m].replaceAll(",", "")))*Double.parseDouble(soluong[m].replaceAll(",", ""));
						if(tt > 0){
							thanhtien[m] = "" + Math.round(tt);
							total = total + Math.round(tt);
						}else{
							thanhtien[m] = "0";
						}

					}else{ // HĐ ĐIỀU CHỈNH GIẢM
						
						double tt = (Double.parseDouble(dongiadagiam[m].replaceAll(",", "")) - Double.parseDouble(dongia[m].replaceAll(",", "")))*Double.parseDouble(soluong[m].replaceAll(",", ""));

						if(tt < 0){
							thanhtien[m] = "" + Math.round(tt);
							total = total + Math.round(tt);
						}else{
							thanhtien[m] = "0";
						}
					}*/
						
					sanpham.setThanhTien(thanhtien[m].replaceAll(",", ""));
					sanpham.setGhiChu1(ghichu[m]);
					spList.add(sanpham);
				}
				m++ ;
			}
		}
		
		/*hdkBean.setBvat("" + total);
		hdkBean.setAvat("" + (Math.round(Double.parseDouble(hdkBean.getVat())*total/100) + total));*/
		hdkBean.setSanPhamList(spList);
		
		if (isValid == false)
		{
				session.setAttribute("hdkBean", hdkBean);  	
	    		session.setAttribute("userId", userId);
			
	    		hdkBean.setMsg("Đơn giá phải < 0");
	    		hdkBean.createRS();
	    		
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNew.jsp";
			response.sendRedirect(nextJSP);
			return;
		}
		
 		String action = request.getParameter("action");
 		if(action.equals("save"))
 		{
 			if(id == null)
 			{
	 			if (!(hdkBean.createHDKhac()))
				{
	 				session.setAttribute("hdkBean", hdkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
	 	    		hdkBean.createRS();
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNew.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpHoadonkhacList obj = new ErpHoadonkhacList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					hdkBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhac.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
 			else
 			{
 				if (!(hdkBean.updateHDKhac()))
				{
	 				session.setAttribute("hdkBean", hdkBean);  	
	 	    		session.setAttribute("userId", userId);
	 			
	 	    		hdkBean.createRS();
	 	    		
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacUpdate.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					IErpHoadonkhacList obj = new ErpHoadonkhacList();
					obj.setCongtyId(ctyId);
					obj.setUserId(userId);
					obj.init("");
					hdkBean.DbClose();
					session.setAttribute("obj", obj);
				    
				    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhac.jsp";
					response.sendRedirect(nextJSP);
				}
 			}
	    }
 		else if(action.equals("savesohoadon")){
 			if(!(hdkBean.updateGiamgiaSohoadon()))
 			{	
 				session.setAttribute("hdkBean", hdkBean);  	
	    		session.setAttribute("userId", userId);
 				hdkBean.init();
 				
 				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacDisplay.jsp";
 				response.sendRedirect(nextJSP);
 			}
 			else{
 				IErpHoadonkhacList obj = new ErpHoadonkhacList();
				obj.setCongtyId(ctyId);
				obj.setUserId(userId);
				obj.init("");
				hdkBean.DbClose();
				session.setAttribute("obj", obj);
			    
			    String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhac.jsp";
				response.sendRedirect(nextJSP);
 			}
 		}
 		else if(action.equals("loadhd"))
 		{
 			hdkBean.createRS();
 			hdkBean.loadhd();
 			session.setAttribute("userId", userId);
			session.setAttribute("hdkBean", hdkBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
 		}
		else if(action.equals("changeKh")){
			hdkBean.createRS();
			hdkBean.createThoihanno_Hannucno();
			System.out.println("gogo");
			
			session.setAttribute("userId", userId);
			session.setAttribute("hdkBean", hdkBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}
		else
		{
			hdkBean.createRS();
			
			session.setAttribute("userId", userId);
			session.setAttribute("hdkBean", hdkBean);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacNew.jsp";
			
			if( id != null )
			{
				 nextJSP = "/TraphacoHYERP/pages/Erp/ErpHoadonkhacUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		}		
	}
	
	
}
