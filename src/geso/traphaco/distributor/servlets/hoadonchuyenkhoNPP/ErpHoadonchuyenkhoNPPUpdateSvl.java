package geso.traphaco.distributor.servlets.hoadonchuyenkhoNPP;

import geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.IErpHoadonchuyenkhoNPP;
import geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.IErpHoadonchuyenkhoNPPList;
import geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.imp.ErpHoadonchuyenkhoNPP;
import geso.traphaco.distributor.beans.hoadonchuyenkhoNPP.imp.ErpHoadonchuyenkhoNPPList;
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

public class ErpHoadonchuyenkhoNPPUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpHoadonchuyenkhoNPPUpdateSvl() 
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
		    IErpHoadonchuyenkhoNPP hdBean = new ErpHoadonchuyenkhoNPP(id);
		    hdBean.setUserId(userId); 
		    hdBean.setcongtyId((String)session.getAttribute("congtyId"));
		    
		    String phanloai = request.getParameter("loai");
			if(phanloai == null)
				phanloai = "";
			hdBean.setLoaiXHD(phanloai);
		    
		    String nextJSP = "";
		    
    		hdBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPDisplay.jsp";
    		}
    		
	        session.setAttribute("hdBean", hdBean);
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
			IErpHoadonchuyenkhoNPP hdBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	hdBean = new ErpHoadonchuyenkhoNPP("");
		    }
		    else
		    {
		    	hdBean = new ErpHoadonchuyenkhoNPP(id);
		    }
	
		    hdBean.setUserId(userId);
		    
		    String ngayxuatHD = util.antiSQLInspection(request.getParameter("ngayxuat"));
		    if(ngayxuatHD == null || ngayxuatHD.trim().length() <= 0)
		    	ngayxuatHD = getDateTime();
		    hdBean.setNgayxuatHD(ngayxuatHD);
		    
		    String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
		    if(ngayghinhan == null || ngayghinhan.trim().length() <= 0)
		    	ngayghinhan = getDateTime();
		    hdBean.setNgayghinhanCN(ngayghinhan);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    hdBean.setGhichu(ghichu);
		    
		    String ghichu2 = util.antiSQLInspection(request.getParameter("ghichu2"));
		    if(ghichu2 == null)
		    	ghichu2 = "";
		    hdBean.setGhichu2(ghichu2);
		    
		    String tthdId = util.antiSQLInspection(request.getParameter("tthdId"));
		    if(tthdId == null)
		    	tthdId = "";
		    hdBean.setTthdId(tthdId);
		    
		    String nguoimuahang = util.antiSQLInspection(request.getParameter("nguoimuahang"));
		    if(nguoimuahang == null)
		    	nguoimuahang = "";
		    hdBean.setNguoimuahang(nguoimuahang);

		    String donvinmh = util.antiSQLInspection(request.getParameter("donvinmh"));
		    if(donvinmh == null)
		    	donvinmh = "";
		    hdBean.setDonvimua(donvinmh);
		    
		    String diachi = util.antiSQLInspection(request.getParameter("diachi"));
		    if(diachi == null)
		    	diachi = "";
		    hdBean.setDiachi(diachi);
		    
		    String masothue = util.antiSQLInspection(request.getParameter("masothue"));
		    if(masothue == null)
		    	masothue = "";
		    hdBean.setMasothue(masothue);
		    
		    String innguoimua = util.antiSQLInspection(request.getParameter("innguoimuahang"));
		    if(innguoimua == null || innguoimua.trim().length() <= 0)
		    	innguoimua = "0";
		    else
		    	innguoimua = "1";
		    hdBean.setInNguoimua(innguoimua);
		    
		    String nppDNId = util.antiSQLInspection(request.getParameter("nppDNId"));
		    if(nppDNId == null)
		    	nppDNId = "";
		    hdBean.setnppDangnhap(nppDNId);
		    
		    String loaiXHD = util.antiSQLInspection(request.getParameter("loaiXHD"));
		    if(loaiXHD == null)
		    	loaiXHD = "";
		    hdBean.setLoaiXHD(loaiXHD);
		    
		    String khId = util.antiSQLInspection(request.getParameter("khId"));
		    if(khId == null)
		    	khId = "";
		    hdBean.setKhId(khId);
		    
		    String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		    if(nppId == null)
		    	nppId = "";
		    hdBean.setNppId(nppId);
		    
		    String doituong = util.antiSQLInspection(request.getParameter("doituong"));
		    if(doituong == null)
		    	doituong = "";
		    hdBean.setDoituong(doituong);
		    
		    String kyhieuhoadon = util.antiSQLInspection(request.getParameter("kyhieuhoadon"));
		    if(kyhieuhoadon == null)
		    	kyhieuhoadon = "";
		    hdBean.setKyhieuhoadon(kyhieuhoadon);
		    
		    String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		    if(sohoadon == null)
		    	sohoadon = "";
		    hdBean.setSohoadon(sohoadon);
		    
		    String hinhthuctt = util.antiSQLInspection(request.getParameter("hinhthuctt"));
		    if(hinhthuctt == null)
		    	hinhthuctt = "";
		    hdBean.setHinhthucTT(hinhthuctt);
		    
		    String bvat = util.antiSQLInspection(request.getParameter("tongtien"));
		    if(bvat == null)
		    	bvat = "0";
		    hdBean.setTongtienBVAT(bvat);
		    
		    String tongchietkhau = util.antiSQLInspection(request.getParameter("tongchietkhau"));
		    if(tongchietkhau == null)
		    	tongchietkhau = "0";
		    hdBean.setTongCK(tongchietkhau);
		    
		    String tienvat = util.antiSQLInspection(request.getParameter("tienvat"));
		    if(tienvat == null)
		    	tienvat = "0";
		    hdBean.setTongVAT(tienvat);
		    
		    String tiensauvat = util.antiSQLInspection(request.getParameter("tiensauvat"));
		    if(tiensauvat == null)
		    	tiensauvat = "0";
		    hdBean.setTongtienAVAT(tiensauvat);
		    
		    String mavv = util.antiSQLInspection(request.getParameter("mavv"));
		    if(mavv == null)
		    	mavv = "";
		    hdBean.setMavuviec(mavv);
		    
		    String KHghino = util.antiSQLInspection(request.getParameter("KHghinoId"));
		    if(KHghino == null)
		    	KHghino = "";
		    hdBean.setKhGhiNo(KHghino);
		    
		    String nhanvienId = util.antiSQLInspection(request.getParameter("nhanvienId"));
		    if(nhanvienId == null)
		    	nhanvienId = "";
		    hdBean.setnhanvienId(nhanvienId); 
		    
		    String khachhangkgId = util.antiSQLInspection(request.getParameter("khachhangkgId"));
		    if(khachhangkgId == null)
		    	khachhangkgId = "";
		    hdBean.setKhachhangKGId(khachhangkgId);
		    
		    hdBean.setcongtyId((String)session.getAttribute("congtyId"));
		    
		    String[] ddhid = request.getParameterValues("ddhid");
		    
		    String ddh = "";
			if (ddhid != null) 
			{
				for(int i = 0; i < ddhid.length; i++)
				{
					ddh += ddhid[i] + ",";
				}
				
				System.out.println("::: DON DAT HANG: " + ddh );
				if(ddh.trim().length() > 0)
				{
					ddh = ddh.substring(0, ddh.length() - 1);
					hdBean.setDondathangId(ddh);
				}
			}
			
			String[] spCKID = request.getParameterValues("ckId");
			hdBean.setCkId(spCKID);
			
			String[] spId = request.getParameterValues("spId");
			hdBean.setSpId(spId);
			
			String[] spMa = request.getParameterValues("spMa");
			hdBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			hdBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			hdBean.setSpDonvi(dvt);
			
			String[] spDongia = request.getParameterValues("spDongia");
			hdBean.setSpDongia(spDongia);
						
			String[] spChietkhau = request.getParameterValues("spChietkhau");
			hdBean.setSpChietkhau(spChietkhau);
			
			String[] soluong = request.getParameterValues("soluong");
			hdBean.setSpSoluong(soluong);
			
			String[] spLoai = request.getParameterValues("spLoai");
			hdBean.setSpLoai(spLoai);
			
			String[] spScheme = request.getParameterValues("scheme");
			hdBean.setSpScheme(spScheme);
			
			String[] spVat = request.getParameterValues("spVat");
			hdBean.setSpVat(spVat);
			
			String[] spTienthue = request.getParameterValues("spTienthue");
			hdBean.setSpTienthua(spTienthue);
					
			
			//THEM CAC LOAI CHIET KHAU
			String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
			hdBean.setDhck_Diengiai(dhCK_diengiai);
			String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
			hdBean.setDhck_giatri(dhCK_giatri);
			String[] dhCK_loai = request.getParameterValues("dhCK_loai");
			hdBean.setDhck_loai(dhCK_loai);
			
		    String action = request.getParameter("action");
		    
		    Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
		    if(spMa!=null)
		    {
		    	for(int i = 0; i < spMa.length; i++ )
		    	{					
		    		String temID = spMa[i] + "_" + spScheme[i] + "_" + spCKID[i];

		    		String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
		    		String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
		    		String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");
		    		
		    		if(soLUONGXUAT != null)
		    		{
		    			for(int j = 0; j < soLUONGXUAT.length; j++ )
		    			{
		    				if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
		    				{
		    					System.out.println("::: KEY SVL: " + ( spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j] ) + " gia tri: " +  soLUONGXUAT[j].replaceAll(",", "") );
		    					sanpham_soluong.put( spMa[i] + "__" + spScheme[i] + "__" + spCKID[i]  +  "__" + spSOLO[j]+ "__" + spNgayHetHan[j]   , soLUONGXUAT[j].replaceAll(",", "")  );								
		    				}
		    			}
		    		}
		    	}
		    }
		    
		    hdBean.setSanpham_Soluong(sanpham_soluong);
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					if(!hdBean.create( session.getAttribute("congtyId").toString() ))
					{
						hdBean.createRs();
	    		    	session.setAttribute("hdBean", hdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHoadonchuyenkhoNPPList obj = new ErpHoadonchuyenkhoNPPList();
						
						obj.setUserId(userId);
						obj.setPhanloai(loaiXHD);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPP.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!hdBean.update())
					{
						hdBean.init();
						hdBean.setSohoadon(sohoadon);
						session.setAttribute("hdBean", hdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHoadonchuyenkhoNPPList obj = new ErpHoadonchuyenkhoNPPList();
						
						obj.setUserId(userId);
						obj.setPhanloai(loaiXHD);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPP.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("chot"))
				{
					if(!hdBean.chot("1"))
					{
						hdBean.init();
						session.setAttribute("hdBean", hdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpHoadonchuyenkhoNPPList obj = new ErpHoadonchuyenkhoNPPList();
						
					    obj.setUserId(userId);
					    obj.setPhanloai(loaiXHD);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPP.jsp";
						response.sendRedirect(nextJSP);
					}
				}
				else if(action.equals("reload"))
				{	
					String nextJSP ="";
					
					session.setAttribute("userId", userId);
					
					//BO NHUNG THONG TIN CUA HOA DON CU
					hdBean.setTongtienBVAT("0");
					hdBean.setTongCK("0");
					hdBean.setTongVAT("0");
					hdBean.setTongtienBVAT("0");
					hdBean.setSpMa(null);
					
					hdBean.loadContents();
					
					System.out.println();
					
					if(id == null)
					{
						session.setAttribute("hdBean", hdBean);					
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPNew.jsp";
					}
					if(id != null)
					{
						session.setAttribute("hdBean", hdBean);
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPUpdate.jsp";
					}
					
					response.sendRedirect(nextJSP);
				}
				else
				{
					String nextJSP ="";
				
					session.setAttribute("userId", userId);
					
					//BO NHUNG THONG TIN CUA HOA DON CU
					hdBean.setTongtienBVAT("0");
					hdBean.setTongCK("0");
					hdBean.setTongVAT("0");
					hdBean.setTongtienBVAT("0");
					hdBean.setSpMa(null);
					
					if(id == null)
					{
						hdBean.createRs();	
						session.setAttribute("hdBean", hdBean);					
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPNew.jsp";
					}
					if(id != null)
					{
						//hdBean.loadContents();
						hdBean.createRs();	
						session.setAttribute("hdBean", hdBean);
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonChuyenKhoNPPUpdate.jsp";
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
