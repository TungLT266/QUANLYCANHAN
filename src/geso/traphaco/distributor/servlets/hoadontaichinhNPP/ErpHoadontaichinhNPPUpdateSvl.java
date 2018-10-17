package geso.traphaco.distributor.servlets.hoadontaichinhNPP;

import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHoadontaichinhNPP;
import geso.traphaco.distributor.beans.hoadontaichinhNPP.imp.ErpHoadontaichinhNPP;
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

public class ErpHoadontaichinhNPPUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpHoadontaichinhNPPUpdateSvl() 
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
		
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			    
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId")); 
		    
		    String id = util.getId(querystring);  	
		    IErpHoadontaichinhNPP hdBean = new ErpHoadontaichinhNPP(id);
		    hdBean.setUserId(userId); 
		    hdBean.setcongtyId((String)session.getAttribute("congtyId"));
		    hdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	hdBean.setDoituongId(session.getAttribute("doituongId"));
	    	hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    String phanloai = request.getParameter("loai");
			if(phanloai == null)
				phanloai = "";
			hdBean.setLoaiXHD(phanloai);
		    
		    String nextJSP = "";
		    
    		hdBean.init();
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";	
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPDisplay.jsp";
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
			String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
			
			this.out = response.getWriter();
			IErpHoadontaichinhNPP hdBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	hdBean = new ErpHoadontaichinhNPP("");
		    }
		    else
		    {
		    	hdBean = new ErpHoadontaichinhNPP(id);
		    }
	
		    hdBean.setUserId(userId);
		    hdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	hdBean.setDoituongId(session.getAttribute("doituongId"));
	    	hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
	    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
		    
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

		    String khonhanId = util.antiSQLInspection(request.getParameter("khonhanId"));
		    if(khonhanId == null)
		    	khonhanId = "";
		    hdBean.setKhoNhapId(khonhanId);
		    
		    /*String tenxhdNEW = util.antiSQLInspection(request.getParameter("tenxhdNEW"));
		    if(tenxhdNEW == null)
		    	tenxhdNEW = "";
		    hdBean.setTenxuathdNew(tenxhdNEW);
		    System.out.println("::: TEN XUAT HOA DON NEW: " + tenxhdNEW );*/
		    
		    
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
				
			String[] spChonin = request.getParameterValues("spChonin");
			String spChon = "";
			if(spChonin != null)
			{				
				for(int n = 0; n < spChonin.length; n++)
				{
					spChon += spChonin[n] + "__"; 
				}		
			}
			
			System.out.println(":::: CHON IN: " + spChon );
			if(spChon.trim().length() > 0)
			{
				spChon = spChon.substring(0, spChon.length() - 2);
				hdBean.setSpChonin(spChon);
			}
			
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
					
		    		String temID = spMa[i] + "_" + spScheme[i];

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
		    					sanpham_soluong.put( spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", "") );								
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
						hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
						hdBean.createRs();
	    		    	session.setAttribute("hdBean", hdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{						
					    hdBean = new ErpHoadontaichinhNPP(id);
					    hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
					    hdBean.setUserId(userId); 
					    hdBean.setcongtyId((String)session.getAttribute("congtyId"));
					    hdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
				    	hdBean.setDoituongId(session.getAttribute("doituongId"));

			    		hdBean.init();
			    		hdBean.setMsg("Số hóa đơn bạn vừa lưu " + sohoadon);

			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";	

				        session.setAttribute("hdBean", hdBean);
				        response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!hdBean.update())
					{
						hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
						hdBean.init();
						hdBean.setSohoadon(sohoadon);
						session.setAttribute("hdBean", hdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpHoadontaichinhNPPList obj = new ErpHoadontaichinhNPPList();
						
						obj.setUserId(userId);
						obj.setPhanloai(loaiXHD);
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
						obj.setDoituongId(session.getAttribute("doituongId"));
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
			    		response.sendRedirect(nextJSP);*/
						
						hdBean = new ErpHoadontaichinhNPP(id);
					    hdBean.setUserId(userId); 
					    hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
					    hdBean.setcongtyId((String)session.getAttribute("congtyId"));
					    hdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
				    	hdBean.setDoituongId(session.getAttribute("doituongId"));

			    		hdBean.init();
			    		hdBean.setMsg("Số hóa đơn bạn vừa lưu " + sohoadon);

			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";	

				        session.setAttribute("hdBean", hdBean);
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
						hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
						hdBean.init();
						session.setAttribute("hdBean", hdBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpHoadontaichinhNPPList obj = new ErpHoadontaichinhNPPList();
						
					    obj.setUserId(userId);
					    obj.setPhanloai(loaiXHD);
					    obj.setLoainhanvien(session.getAttribute("loainhanvien"));
						obj.setDoituongId(session.getAttribute("doituongId"));
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPP.jsp";
						response.sendRedirect(nextJSP);*/
						
						hdBean = new ErpHoadontaichinhNPP(id);
						hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
				    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
					    hdBean.setUserId(userId); 
					    hdBean.setcongtyId((String)session.getAttribute("congtyId"));
					    hdBean.setLoainhanvien(session.getAttribute("loainhanvien"));
				    	hdBean.setDoituongId(session.getAttribute("doituongId"));

			    		hdBean.init();
			    		hdBean.setMsg("Số hóa đơn bạn vừa duyệt " + sohoadon);

			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPDisplay.jsp";	

				        session.setAttribute("hdBean", hdBean);
				        response.sendRedirect(nextJSP);
					}
				}
				else
				{
					String nextJSP ="";
				
					session.setAttribute("userId", userId);
					
					//BO NHUNG THONG TIN CUA HOA DON CU
					hdBean.setTdv_dangnhap_id(tdv_dangnhap_id);
			    	hdBean.setNpp_duocchon_id(npp_duocchon_id);
					hdBean.setTongtienBVAT("0");
					hdBean.setTongCK("0");
					hdBean.setTongVAT("0");
					hdBean.setTongtienBVAT("0");
					hdBean.setSpMa(null);
					
					if(id == null)
					{
						hdBean.createRs();	
						
						session.setAttribute("hdBean", hdBean);					
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPNew.jsp";
					}
					if(id != null)
					{
						//hdBean.loadContents();
						hdBean.createRs();
						hdBean.setSpChonin(spChon);
						
						session.setAttribute("hdBean", hdBean);
						nextJSP = "/TraphacoHYERP/pages/Distributor/ErpHoaDonTaiChinhNPPUpdate.jsp";
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
