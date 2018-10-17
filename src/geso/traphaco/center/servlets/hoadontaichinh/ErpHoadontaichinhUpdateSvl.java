package geso.traphaco.center.servlets.hoadontaichinh;

import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinh;
import geso.traphaco.center.beans.hoadontaichinh.IErpHoadontaichinhList;
import geso.traphaco.center.beans.hoadontaichinh.imp.ErpHoadontaichinh;
import geso.traphaco.center.beans.hoadontaichinh.imp.ErpHoadontaichinhList;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpHoadontaichinhUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpHoadontaichinhUpdateSvl() 
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			session.setMaxInactiveInterval(30000);
		
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpHoadontaichinh lsxBean = new ErpHoadontaichinh(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		System.out.println("aaaaaa"+lsxBean.getNgayxuatHD());
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhDisplay.jsp";
    		}
    		
	        session.setAttribute("lsxBean", lsxBean);
	        response.sendRedirect(nextJSP);
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}
		else
		{
			request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
			
			session.setMaxInactiveInterval(30000);
			
			this.out = response.getWriter();
			IErpHoadontaichinh lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpHoadontaichinh("");
		    }
		    else
		    {
		    	lsxBean = new ErpHoadontaichinh(id);
		    }
	
		    lsxBean.setUserId(userId);
		    
		    String ngayxuatHD = util.antiSQLInspection(request.getParameter("ngayxuat"));
		    if(ngayxuatHD == null || ngayxuatHD.trim().length() <= 0)
		    	ngayxuatHD = getDateTime();
		    lsxBean.setNgayxuatHD(ngayxuatHD);

		    String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		    if(ngayghinhan == null || ngayghinhan.trim().length() <= 0)
		    	ngayghinhan = getDateTime();
		    lsxBean.setNgayghinhanCN(ngayghinhan);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
		    String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
		    if(loaidonhang == null)
		    	loaidonhang = "0";
		    lsxBean.setLoaidonhang(loaidonhang);
		    
		    String nguoimua = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		    if(nguoimua == null)
		    	nguoimua = "";
		    lsxBean.setNguoimua(nguoimua);
		    
		    String innguoimua = util.antiSQLInspection(request.getParameter("innguoimuahang"));
		    if(innguoimua == null || innguoimua.trim().length() <= 0)
		    {
		    	innguoimua = "0";
		    }else
		    {
		    	innguoimua = "1";
		    }
		    lsxBean.setInNguoimua(innguoimua);
		    
		    String loaiXHD = util.antiSQLInspection(request.getParameter("loaiXHD"));
		    if(loaiXHD == null)
		    	loaiXHD = "";
		    lsxBean.setLoaiXHD(loaiXHD);
		    
		    String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		    if(nppId == null)
		    	nppId = "";
		    lsxBean.setNppId(nppId);
		    
		    String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		    if(kyhieuhoadon == null)
		    	kyhieuhoadon = "";
		    lsxBean.setKyhieuhoadon(kyhieuhoadon);
		    
		    String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		    if(sohoadon == null)
		    	sohoadon = "";
		    lsxBean.setSohoadon(sohoadon);
		    
		    String hinhthuctt = util.antiSQLInspection(request.getParameter("hinhthuctt"));
		    if(hinhthuctt == null)
		    	hinhthuctt = "";
		    lsxBean.setHinhthucTT(hinhthuctt);
		    
		    
		    String bvat = util.antiSQLInspection(request.getParameter("tongtien"));
		    if(bvat == null)
		    	bvat = "0";
		    lsxBean.setTongtienBVAT(bvat);
		    
		    String tongchietkhau = util.antiSQLInspection(request.getParameter("tongchietkhau"));
		    if(tongchietkhau == null)
		    	tongchietkhau = "0";
		    lsxBean.setTongCK(tongchietkhau);
		    
		    String tienvat = util.antiSQLInspection(request.getParameter("tienvat"));
		    if(tienvat == null)
		    	tienvat = "0";
		    lsxBean.setTongVAT(tienvat);
		    
		    String tiensauvat = util.antiSQLInspection(request.getParameter("tiensauvat"));
		    if(tiensauvat == null)
		    	tiensauvat = "0";
		    lsxBean.setTongtienAVAT(tiensauvat);
		    
		    String mavv = util.antiSQLInspection(request.getParameter("mavv"));
		    if(mavv == null)
		    	mavv = "";
		    lsxBean.setMavuviec(mavv);
		    
		    String noibo = util.antiSQLInspection(request.getParameter("NOIBO"));
		    if(noibo == null)
		    	noibo = "";
		    lsxBean.setNOIBO(noibo);
		    
		    String[] ddhid = request.getParameterValues("ddhid");
		    
		    String ddh = "";
			if (ddhid != null) 
			{
				for(int i = 0; i < ddhid.length; i++)
				{
					ddh += ddhid[i] + ",";
				}
				
				if(ddh.trim().length() > 0)
				{
					ddh = ddh.substring(0, ddh.length() - 1);
					lsxBean.setDondathangId(ddh);
				}
			}
			
			String[] spId = request.getParameterValues("spId");
			lsxBean.setSpId(spId);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] spDongia = request.getParameterValues("spDongia");
			lsxBean.setSpDongia(spDongia);
						
			String[] spChietkhau = request.getParameterValues("spChietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			lsxBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			String[] spVat = request.getParameterValues("spVat");
			lsxBean.setSpVat(spVat);
			
			String[] spTienthue = request.getParameterValues("spTienthue");
			lsxBean.setSpTienthua(spTienthue);
			
			//THEM CAC LOAI CHIET KHAU
			String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
			lsxBean.setDhck_Diengiai(dhCK_diengiai);
			String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
			lsxBean.setDhck_giatri(dhCK_giatri);
			String[] dhCK_loai = request.getParameterValues("dhCK_loai");
			lsxBean.setDhck_loai(dhCK_loai);
			String[] is_khongthue = request.getParameterValues("is_khongthue");
			if(spMa != null && is_khongthue ==null)
	    	{
	    		is_khongthue = new String[spMa.length];
	    		for(int i =0;i<is_khongthue.length;i++)
	    		{
	    			is_khongthue[i] = "0";
	    		}
	    	}
			lsxBean.setSpIskhongthue(is_khongthue);
		    String action = request.getParameter("action");
		    
		    Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
		    if(spMa!=null)
		    {
				for(int i = 0; i < spMa.length; i++ )
				{
					System.out.println("---SP MA LA: " + spMa[i]);
					String temID = spMa[i];
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO"+i);
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG_"+i);
					
					String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN"+i);
					String[] spNgaynhapkho = request.getParameterValues(temID + "_spNGAYNHAPKHO"+i);
					String[] spVitri = request.getParameterValues(temID + "_spVITRI"+i);
					String[] spMATHUNG = request.getParameterValues(temID + "_spMATHUNG"+i);
					String[] spMAME = request.getParameterValues(temID + "_spMAME"+i);
					String[] spMAPHIEU = request.getParameterValues(temID + "_spMAPHIEU"+i);
					String[] spMARQ = request.getParameterValues(temID + "_spMARQ"+i);
					String[] spNSX = request.getParameterValues(temID + "_spNSX"+i);
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								sanpham_soluong.put(spMa[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j] + "__" + spVitri[j] + "__" + spMATHUNG[j] + "__" + spMAME[j] + "__" + spMAPHIEU[j] + "__" + spMARQ[j] + "__" + spNSX[j] + "__" + spNgaynhapkho[j], soLUONGXUAT[j].replaceAll(",", "") );								
							}
						}
					}
				}
		    }
			lsxBean.setSanpham_Soluong(sanpham_soluong);
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!lsxBean.create())
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHoadontaichinhList obj = new ErpHoadontaichinhList();
						
						obj.setUserId(userId);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!lsxBean.update())
					{
						lsxBean.init();
						lsxBean.setSohoadon(sohoadon);
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHoadontaichinhList obj = new ErpHoadontaichinhList();
						
						obj.setUserId(userId);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.setLoaikm(loaidonhang);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("chot"))
				{
					if(!lsxBean.chot_update())
					{
						lsxBean.init();
						lsxBean.setSohoadon(sohoadon);
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpHoadontaichinhList obj = new ErpHoadontaichinhList();
						
					    obj.setUserId(userId);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
						response.sendRedirect(nextJSP);*/
						IErpHoadontaichinhList obj = new ErpHoadontaichinhList();
						
						obj.setUserId(userId);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
						obj.setLoaikm(loaidonhang);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinh.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					String nextJSP ="";
				
					session.setAttribute("userId", userId);
					
					//BO NHUNG THONG TIN CUA HOA DON CU
					lsxBean.setTongtienBVAT("0");
					lsxBean.setTongCK("0");
					lsxBean.setTongVAT("0");
					lsxBean.setTongtienBVAT("0");
					lsxBean.setSpMa(null);
					
					if(id == null)
					{
						lsxBean.createRs();	
						session.setAttribute("lsxBean", lsxBean);					
						nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhNew.jsp";
					}
					if(id != null)
					{
						lsxBean.init();
						lsxBean.setNgayxuatHD(ngayxuatHD);
						
						session.setAttribute("lsxBean", lsxBean);
						nextJSP = "/TraphacoHYERP/pages/Center/ErpHoaDonTaiChinhUpdate.jsp";
					}
						 					
					response.sendRedirect(nextJSP);
				}
				
			}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
