package geso.traphaco.erp.servlets.uynhiemchi;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thanhtoanhoadon.IDuyetthanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.IHoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Duyetthanhtoanhoadon;
import geso.traphaco.erp.beans.thanhtoanhoadon.imp.Hoadon;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchi;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchiList;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchi;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchiList;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpUynhiemchiUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
    
	PrintWriter out;
	dbutils db;
	
    public ErpUynhiemchiUpdateSvl() {
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

			this.out = response.getWriter();
			Utility util = new Utility();
			
	    	String querystring = request.getQueryString();
		    userId = util.getUserId(querystring);
		    
		   
		    
		    if (userId.length()==0)
		    	userId = util.antiSQLInspection(request.getParameter("userId"));
		    
		    String id = util.getId(querystring);  	
			IErpUynhiemchi tthdBean = new ErpUynhiemchi(id);
			String ctyId = (String)session.getAttribute("congtyId");
			String nppId = (String)session.getAttribute("nppId");
			System.out.println("ctyIdctyId:"+ctyId+" "+nppId);
			tthdBean.setCtyId(ctyId);
	        tthdBean.setUserId(userId);
	        tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	        
	    	String duyetchi = request.getParameter("duyetchi");
	    	
	    	if(duyetchi == null)
				duyetchi = "";
	        tthdBean.setDuyetchi(duyetchi);  

	        String nextJSP;
	       
	        if(request.getQueryString().indexOf("display") >= 0 ) 
	        {
	        	tthdBean.init();
	        	nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiDisplay.jsp";
	        }
	        else
	        {
	        	tthdBean.init();
	        	if (tthdBean.getDaDuyet() == 1 && tthdBean.getTrangThai() == 0 && request.getQueryString().indexOf("updateTiGia") >= 0)
	        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiDisplay.jsp";
	        	else
	        		nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiUpdate.jsp";
	        }
	        

	        session.setAttribute("tthdBean", tthdBean);
	        response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen"); 
		String ctyId = (String)session.getAttribute("congtyId");
		String nppId = (String)session.getAttribute("nppId");
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
			
			String action = request.getParameter("action");
			System.out.println("Action la: " + action);
			
			
			
			IErpUynhiemchi tthdBean;

			Utility util = new Utility();
			String id = util.antiSQLInspection(request.getParameter("Id"));
			
			System.out.println("Id: " + id);
		    if(id == null)
		    {  	
		    	tthdBean = new ErpUynhiemchi("");
		    }
		    else
		    {
		    	tthdBean = new ErpUynhiemchi(id);
		    }
		    
		    
		    String duyetchi = request.getParameter("duyetchi");
			if(duyetchi == null)
				duyetchi = "";
			tthdBean.setDuyetchi(duyetchi);
		  //System.out.println("isNPP:"+isNPP);
	    	String isDNTT = util.antiSQLInspection(request.getParameter("isDNTT"));
			if (isDNTT == null || isDNTT == "")
				isDNTT = "0";				
	    	tthdBean.setIsDNTT(isDNTT);
	    	if (isDNTT.trim().equals("1"))
	    	{
	    		ctyId = (String)session.getAttribute("congtyId");
				System.out.println("ctyIdctyId:"+ctyId);
				tthdBean.setCtyId(ctyId);
		        tthdBean.setUserId(userId);
		        tthdBean.setnppdangnhap(util.getIdNhapp(userId));
	    		tthdBean.init();
	    	}
	    	if (isDNTT.trim().equals("1"))
	    	{
		    	session.setAttribute("nhomncccn", tthdBean.getNhomNCCCN());			
		    	session.setAttribute("doituong",tthdBean.getDoiTuongTamUng());
		    	session.setAttribute("loaithanhtoan", tthdBean.getLoaiThanhToan());	    	
		    	session.setAttribute("doituongkhac",tthdBean.getDoiTuongChiPhiKhac());	    	
		    	session.setAttribute("doituongdinhkhoan",tthdBean.getDoiTuongDinhKhoan());
	    	}
	    	
	    	tthdBean.setCtyId(ctyId);
		    tthdBean.setUserId(userId);
	        tthdBean.setnppdangnhap(util.getIdNhapp(userId));	  
	        
	        String ngayghinhan = util.antiSQLInspection(request.getParameter("ngayghinhan"));
			if (ngayghinhan == null || ngayghinhan == "")
				ngayghinhan = this.getDateTime();				
	    	tthdBean.setNgayghinhan(ngayghinhan);
	    	
	    	String soChungTu = util.antiSQLInspection(request.getParameter("soChungTu"));
			if (soChungTu == null || soChungTu == "")
				soChungTu = "";				
	    	tthdBean.setSoChungTu(soChungTu);
	    	
	    	String soChungTu_Chu = util.antiSQLInspection(request.getParameter("soChungTu_Chu"));
	    	if(soChungTu_Chu == null)
	    		soChungTu_Chu = "";
	    	tthdBean.setSoChungTu_Chu(soChungTu_Chu);
	    	
	    	String soChungTu_So = util.antiSQLInspection(request.getParameter("soChungTu_So"));
	    	if(soChungTu_So == null)
	    		soChungTu_So = "";
	    	tthdBean.setSoChungTu_So(soChungTu_So);
	    	
	    	String noidungthanhtoan = util.antiSQLInspection(request.getParameter("noidungthanhtoan"));
	    	if(noidungthanhtoan == null)
	    		noidungthanhtoan = "";
	    	tthdBean.setNoidungtt(noidungthanhtoan);	
	    	
	    	String tenSoTaiKhoan = (String)session.getAttribute("tenSoTaiKhoan");
		    if (tenSoTaiKhoan == null)
		    	tenSoTaiKhoan = "";
		    tthdBean.setTenSoTaiKhoan(tenSoTaiKhoan);
		    
		    String sotaikhoan = util.antiSQLInspection(request.getParameter("sotaikhoan"));
	    	if(sotaikhoan == null)
	    		sotaikhoan = "";
	    	tthdBean.setSotaikhoan(sotaikhoan);
	    	
	    	///////////////////
	    	boolean error = false;
	    	if (isDNTT.trim().equals("0"))
	    	{
		    String daDuyet = (String)session.getAttribute("daDuyet");
		    if (daDuyet == null)
		    	daDuyet = "0";
		    tthdBean.setDaDuyet(Integer.parseInt(daDuyet));
		    
		    String trangThai = (String)session.getAttribute("trangThai");
		    if (trangThai == null)
		    	trangThai = "0";
		    tthdBean.setTrangThai(Integer.parseInt(trangThai));
		    
		    String tenKhachHang = (String)session.getAttribute("tenKhachHang");
		    if (tenKhachHang == null)
		    	tenKhachHang = "";
		    tthdBean.setTenKhachHang(tenKhachHang);
		    
		    String tenHinhThucThanhToan = (String)session.getAttribute("tenHinhThucThanhToan");
		    if (tenHinhThucThanhToan == null)
		    	tenHinhThucThanhToan = "";
		    tthdBean.setTenHinhThucThanhToan(tenHinhThucThanhToan);
		    
		    String tenTienTe = (String)session.getAttribute("tenTienTe");
		    if (tenTienTe == null)
		    	tenTienTe = "";
		    tthdBean.setTenTienTe(tenTienTe);
		    
		    String tenSoTaiKhoanTrichPhi = (String)session.getAttribute("tenSoTaiKhoanTrichPhi");
		    if (tenSoTaiKhoanTrichPhi == null)
		    	tenSoTaiKhoanTrichPhi = "";
		    tthdBean.setTenSoTaiKhoanTrichPhi(tenSoTaiKhoanTrichPhi);
			
	    	String doituongtamung = util.antiSQLInspection(request.getParameter("doituongtamung"));
	    	if(doituongtamung == null)
	    		doituongtamung = "1";
	    	tthdBean.setDoiTuongTamUng(doituongtamung);
	    	System.out.println("doituongtamung : "+doituongtamung);
	    	
	    	String doituongthanhtoan = util.antiSQLInspection(request.getParameter("doituongthanhtoan"));
	    	if(doituongthanhtoan == null)
	    		doituongthanhtoan = "1";
	    	tthdBean.setDoiTuongChiPhiKhac(doituongthanhtoan);
	    	System.out.println("doituongthanhtoan : "+doituongthanhtoan);
	    	
	    	
	    	String Nhomncccn = util.antiSQLInspection(request.getParameter("nhomncccn"));
			if(Nhomncccn == null)
				Nhomncccn = "";
	    	tthdBean.setNhomNCCCN(Nhomncccn);
	    	
	    	String nccId = request.getParameter("nccId");
			if (nccId == null){
				nccId = "";		
			}
			tthdBean.setNccId(nccId);

			String nhomnccId = request.getParameter("nhomnccId");
			if (nhomnccId == null){
				nhomnccId = "";		
			}
			tthdBean.setNhomNCCCNId(nhomnccId);
	    	
			String doiTuongKhacId = request.getParameter("doiTuongKhacId");
			tthdBean.setDoiTuongKhacId(doiTuongKhacId);	
			
			String khachHang_NPP_FK = request.getParameter("khachHang_NPP_FK");
			tthdBean.setKhachHang_NPP_FK(khachHang_NPP_FK);	
			
			String dinhkhoanno = util.antiSQLInspection(request.getParameter("dinhkhoanno"));
			if(dinhkhoanno == null)
				dinhkhoanno = "";
			tthdBean.setDinhkhoanno(dinhkhoanno);
			
	    	System.out.println("Dinh khoan no: " + dinhkhoanno);
	    	dinhkhoanno = dinhkhoanno.split("--")[0];
	    	
	    	tthdBean.setDinhkhoannoId(dinhkhoanno);
	    	
	    	if(dinhkhoanno.length() > 1){
	    		String check = "";
	    		check = CheckDoiTuongDinhKhoan(dinhkhoanno, ctyId);
	    		tthdBean.setDoiTuongDinhKhoan(check);
	    		System.out.println(check);
	    	}
	    	
	    	String matendoituongdinhkhoan = util.antiSQLInspection(request.getParameter("doituongdinhkhoan"));
	    	if (matendoituongdinhkhoan == null)
	    		matendoituongdinhkhoan = "";
	    	tthdBean.setMaTenDoiTuongDinhKhoan(matendoituongdinhkhoan);
	    	
	    	matendoituongdinhkhoan = matendoituongdinhkhoan.split("--")[0];
	    	tthdBean.setDoituongdinhkhoanId(matendoituongdinhkhoan);
	    	
	    	System.out.println("Dinh khoan no "+tthdBean.getMaTenDoiTuongDinhKhoan());
	    		    	
	    	
	    	String htttId = util.antiSQLInspection(request.getParameter("htttId"));
			if (htttId == null)
				htttId = "";				
	    	tthdBean.setHtttId(htttId);
	    	
	    	String thanhtoantuTV = util.antiSQLInspection(request.getParameter("thanhtoantuTV"));
	    	if(thanhtoantuTV == null)
	    		thanhtoantuTV = "0";
	    	tthdBean.setCheckThanhtoantuTV(thanhtoantuTV);
	    	System.out.println("Check: "+ thanhtoantuTV);
	    	
	    	String sotaikhoan_tp = util.antiSQLInspection(request.getParameter("sotaikhoan_tp"));
	    	if(sotaikhoan_tp == null)
	    		sotaikhoan_tp = "";
	    	tthdBean.setSotaikhoan_tp(sotaikhoan_tp);

	    	String ttId = util.antiSQLInspection(request.getParameter("tienteId"));
	    	if(ttId == null)
	    		ttId = "";
	    	tthdBean.setTienteId(ttId);

	    	String tigia = util.antiSQLInspection(request.getParameter("tigia"));
	    	if(tigia == null)
	    		tigia = "1";
	    	tthdBean.setTigia(tigia);
	    	
	    	String sotienHDNT = util.antiSQLInspection(request.getParameter("sotienHDNT"));
	    	if(sotienHDNT == null)
	    		sotienHDNT = "0";
	    	tthdBean.setSotienHDNT(sotienHDNT);

	    	String sotienHDVND = util.antiSQLInspection(request.getParameter("sotienHDVND"));
	    	if(sotienHDVND == null)
	    		sotienHDVND = "0";
	    	tthdBean.setSotienHD(sotienHDVND);

	    	String trichphi = util.antiSQLInspection(request.getParameter("trichphi"));
	    	if(trichphi == null)
	    		trichphi = "0";
	    	System.out.println("trich phi:" + trichphi);
	    	
	    	tthdBean.setTrichphi(trichphi);

	    	String pnganhangNT = util.antiSQLInspection(request.getParameter("pnganhangNT"));
	    	if(pnganhangNT == null)
	    		pnganhangNT = "0";
	    	tthdBean.setPhinganhangNT(pnganhangNT);

	    	String pnganhangVND = util.antiSQLInspection(request.getParameter("pnganhangVND"));
	    	if(pnganhangVND == null)
	    		pnganhangVND = "0";
	    	tthdBean.setPhinganhang(pnganhangVND);

	    	String maHD_VAT = util.antiSQLInspection(request.getParameter("maHD_VAT"));
	    	if(maHD_VAT == null)
	    		maHD_VAT = "";
	    	tthdBean.setMahoadon(maHD_VAT);

	    	String mauHD_VAT = util.antiSQLInspection(request.getParameter("mauHD_VAT"));
	    	if(mauHD_VAT == null)
	    		mauHD_VAT = "";
	    	tthdBean.setMauhoadon(mauHD_VAT);
	    	
	    	String kyhieu_VAT = util.antiSQLInspection(request.getParameter("kyhieu_VAT"));
	    	if(kyhieu_VAT == null)
	    		kyhieu_VAT = "";
	    	tthdBean.setKyhieu(kyhieu_VAT);

	    	String sohd_VAT = util.antiSQLInspection(request.getParameter("sohd_VAT"));
	    	if(sohd_VAT == null)
	    		sohd_VAT = "";
	    	tthdBean.setSohoadon(sohd_VAT);
	    	
	    	String ngayhd_VAT = util.antiSQLInspection(request.getParameter("ngayhd_VAT"));
	    	if(ngayhd_VAT == null)
	    		ngayhd_VAT = "";
	    	tthdBean.setNgayhoadon(ngayhd_VAT);	    	

	    	String nghangTen = util.antiSQLInspection(request.getParameter("nghangTen"));
	    	if(nghangTen == null)
	    		nghangTen = "";
	    	tthdBean.setTenNCC_VAT(nghangTen);
	    	
	    	String nhId_VAT =(request.getParameter("nhId_VAT"));
	    	if(nhId_VAT == null)
	    		nhId_VAT = "";
	    	tthdBean.setNhId_VAT(nhId_VAT);
	    	
	    	
	    	String cnId_VAT = (request.getParameter("cnId_VAT"));
	    	if(cnId_VAT == null)
	    		cnId_VAT = "";
	    	tthdBean.setCnId_VAT(cnId_VAT);
	    	
	    	String mst_VAT = util.antiSQLInspection(request.getParameter("mst_VAT"));
	    	if(mst_VAT == null)
	    		mst_VAT = "";
	    	tthdBean.setMST(mst_VAT);
	    	
	    	String tienhang_VAT = util.antiSQLInspection(request.getParameter("tienhang_VAT"));
	    	if(tienhang_VAT == null)
	    		tienhang_VAT = "0";
	    	tthdBean.setTienhang(tienhang_VAT);
	    	
	    	String thuesuat_VAT = util.antiSQLInspection(request.getParameter("thuesuat_VAT"));
	    	if(thuesuat_VAT == null)
	    		thuesuat_VAT = "0";
	    	tthdBean.setThuesuat(thuesuat_VAT);
	    	
	    	String tienthue_VAT = util.antiSQLInspection(request.getParameter("tienthue_VAT"));
	    	if(tienthue_VAT == null)
	    		tienthue_VAT = "0";
	    	tthdBean.setTienthue(tienthue_VAT);
	    	
	    	
	    	String vatNT = util.antiSQLInspection(request.getParameter("vatNT"));
	    	if(vatNT == null)
	    		vatNT = "0";
	    	tthdBean.setThueVATNT(vatNT);

	    	String vatVND = util.antiSQLInspection(request.getParameter("vatVND"));
	    	if(vatVND == null)
	    		vatVND = "0";
	    	tthdBean.setThueVAT(vatVND);
	    	
	    	String tongtienNT = util.antiSQLInspection(request.getParameter("tongtienNT"));
	    	if(tongtienNT == null)
	    		tongtienNT = "0";
	    	tthdBean.setSotienttNT(tongtienNT);

	    	String tongtienVND = util.antiSQLInspection(request.getParameter("tongtienVND"));
	    	if(tongtienVND == null)
	    		tongtienVND = "0";
	    	tthdBean.setSotientt(tongtienVND);
	    	
	    	String chenhlechVND = util.antiSQLInspection(request.getParameter("chenhlechVND"));
	    	if(chenhlechVND == null)
	    		chenhlechVND = "0";
	    	tthdBean.setChenhlech(chenhlechVND);

	    	String NhanVienId = util.antiSQLInspection(request.getParameter("NhanvienId"));
	    	if(NhanVienId == null){
	    		NhanVienId = "";
	    	}	    				
			tthdBean.setNhanVienId(NhanVienId);
			
			String bpId = util.antiSQLInspection(request.getParameter("bpId"));
			if(bpId == null)
				bpId = "";
			tthdBean.setBophanId(bpId);
			
			String bpTen = util.antiSQLInspection(request.getParameter("bpTen"));
			if(bpTen == null)
				bpTen = "";
			tthdBean.setBophanTen(bpTen);
			
			String khId = util.antiSQLInspection(request.getParameter("khid"));
	    	if(khId == null){
	    		khId = "";
	    	}		
	    	
	    	String isNPP ="";
	    	if(khId.trim().length()>0)
	    	{
	    		String kh[] = khId.split(" - ");
	    		//isNPP = kh[1].substring(0, kh[1].length()) ;
	    		khId = kh[0].substring(0,kh[0].length() );
	    	}

			tthdBean.setKhachhangId(khId);
	    	tthdBean.setisNPP(isNPP);
	    	
	    	//Nếu là cp Khác - doituongthanhtoan = 3
	    	
	    	if(doituongthanhtoan.equals("3"))
	    	{
	    		String[] TaiKhoanIds = request.getParameterValues("TaiKhoanId");
		    	String[] PhongbanIds = request.getParameterValues("PhongbanId");
		    	String[] KenhIds = request.getParameterValues("KenhId");
		    	String[] TinhthanhIds = request.getParameterValues("TinhthanhId");
		    	String[] SanphamIds = request.getParameterValues("SanphamId");
		    	String[] MavvIds = request.getParameterValues("mavvId");
		    	String[] DiabanIds = request.getParameterValues("diabanId");
		    	String[] TinhThanhIds = request.getParameterValues("TinhthanhId");
		    	String[] BenhvienIds = request.getParameterValues("BenhvienId");
		    	
		    	if(TaiKhoanIds!=null){
					String[] dcIds = new String[TaiKhoanIds.length];
					String[] loais = new String[TaiKhoanIds.length];
					String[] ttcps = new String[TaiKhoanIds.length];
					
					tthdBean.setCount(TaiKhoanIds.length);
					tthdBean.setTaiKhoanIds(TaiKhoanIds);				
					
					for(int i = 0; i < TaiKhoanIds.length; i++)
					{
						if(request.getParameter("dcIds_" + i) != null){
							if(request.getParameter("dcIds_" + i).trim().length() > 0)
							{
								String[] tam = request.getParameter("dcIds_" + i).split("_");
								dcIds[i] = tam[0];
								loais[i] = tam[1];
							}else
							{
								dcIds[i] = "";
								loais[i] = "";
							}
						}else{
							dcIds[i] = "";
							loais[i] = "";
						}
						
						if(request.getParameter("ttcps_" + i) != null){
							if(request.getParameter("ttcps_" + i).trim().length() > 0)
							{
								ttcps[i] = request.getParameter("ttcps_" + i).trim();
							} else {
								ttcps[i] = "";
							}
						}else{
							ttcps[i] = "";
						}
					}

					tthdBean.setTtcpIds(ttcps);
					tthdBean.setDcIds(dcIds);
					tthdBean.setLoais(loais);
		    	}
		    	else
		    	{
		    		tthdBean.setCount(0);
		    	}
		    	
		    	System.out.println("Count::::"+tthdBean.getCount());
		    	for(int i = 0; i < tthdBean.getCount(); i++)
				{	    		
					if(PhongbanIds[i] == null) PhongbanIds[i] = "";
					if(KenhIds[i] == null) KenhIds[i] = "";
					if(TinhthanhIds[i] == null) TinhthanhIds[i] = "";
					if(SanphamIds[i] == null) SanphamIds[i] = "";
					if(MavvIds[i] == null) MavvIds[i] = "";
					if(DiabanIds[i] == null) DiabanIds[i] = "";
					if(TinhThanhIds[i] == null) TinhThanhIds[i] = "";
					if(BenhvienIds[i] == null) BenhvienIds[i] = "";
				}
		    	
		    	String[] Kyhieuhd = new String[tthdBean.getCount()];
		    	String[] Mauhd = new String[tthdBean.getCount()];
		    	String[] Sohd = new String[tthdBean.getCount()];
	    		String[] Ngayhd = new String[tthdBean.getCount()];
	    		String[] TenNCChd = new String[tthdBean.getCount()];
	    		String[] diaChiNCC = new String[tthdBean.getCount()];
	    		String[] MSThd = new String[tthdBean.getCount()];
	    		String[] Thuesuathd = new String[tthdBean.getCount()];
	    		String[] Tienthuehd = new String[tthdBean.getCount()];
	    		String[] Tienhanghd = new String[tthdBean.getCount()];
	    		String[] Diengiaihd = new String[tthdBean.getCount()];
	    		
		    	for(int i = 0; i < tthdBean.getCount(); i++)
				{		    		
					if(request.getParameter("mauHoaDon_VAT_" + i) != null) 		
						Mauhd[i] = request.getParameter("mauHoaDon_VAT_" + i);
					else
						Mauhd[i] = "";
					
					if(request.getParameter("kyhieu_VAT_" + i) != null) 		
						Kyhieuhd[i] = request.getParameter("kyhieu_VAT_" + i);
					else
						Kyhieuhd[i] = "";
					
					if(request.getParameter("sohd_VAT_" + i) != null) 		
						Sohd[i] = request.getParameter("sohd_VAT_" + i);
					else
						Sohd[i] = "";
					
					if(request.getParameter("ngayhd_VAT_" + i) != null) 		
						Ngayhd[i] = request.getParameter("ngayhd_VAT_" + i);
					else
						Ngayhd[i] = "";
					
					if(request.getParameter("tenNCC_VAT_" + i) != null) 		
						TenNCChd[i] = request.getParameter("tenNCC_VAT_" + i);
					else
						TenNCChd[i] = "";
								
					if(request.getParameter("diaChiNCC_" + i) != null) 		
						diaChiNCC[i] = request.getParameter("diaChiNCC_" + i);
					else
						diaChiNCC[i] = "";
					
					if(request.getParameter("mst_VAT_" + i) != null) 		
						MSThd[i] = request.getParameter("mst_VAT_" + i);
					else
						MSThd[i] = "";
					
					if(request.getParameter("thuesuat_VAT_" + i) != null) 		
						Thuesuathd[i] = request.getParameter("thuesuat_VAT_" + i).replaceAll(",","");
					else
						Thuesuathd[i] = "";
					
					if(request.getParameter("tienthue_VAT_" + i) != null) 		
						Tienthuehd[i] = request.getParameter("tienthue_VAT_" + i).replaceAll(",","");
					else
						Tienthuehd[i] = "";
					
					if(request.getParameter("tienhang_VAT_" + i) != null) 		
						Tienhanghd[i] = request.getParameter("tienhang_VAT_" + i).replaceAll(",","");
					else
						Tienhanghd[i] = "";
					
					if(request.getParameter("diengiai_VAT_" + i) != null) 		
						Diengiaihd[i] = request.getParameter("diengiai_VAT_" + i);
					else
						Diengiaihd[i] = "";
					
					System.out.println("request.getParameter:"+request.getParameter("sohd_VAT_" + i));
				}
		    	
		    	
		    	tthdBean.setPhongBanIds(PhongbanIds);
		    	tthdBean.setMavvIds(MavvIds);
		    	tthdBean.setDiaBanIds(DiabanIds);
		    	tthdBean.setBenhVienIds(BenhvienIds);
		    	tthdBean.setKenhIds(KenhIds);
		    	tthdBean.setTinhThanhIds(TinhthanhIds);
		    	tthdBean.setSanPhamIds(SanphamIds);
		    	tthdBean.setKyhieuhd(Kyhieuhd);
		    	tthdBean.setMauhd(Mauhd);
		    	tthdBean.setSohd(Sohd);
		    	tthdBean.setNgayhd(Ngayhd);
		    	tthdBean.setTenNCChd(TenNCChd);
		    	tthdBean.setDiaChiHd(diaChiNCC);
		    	tthdBean.setMSThd(MSThd);
		    	tthdBean.setThuesuathd(Thuesuathd);
		    	tthdBean.setTienthuehd(Tienthuehd);
		    	tthdBean.setTienhanghd(Tienhanghd);
		    	tthdBean.setDiengiaihd(Diengiaihd);
	    	}
	    	
	    	
	    	//Luu lai hoa don
	    	String[] idHd = request.getParameterValues("idHd");
	    	String[] tienteId = request.getParameterValues("ttId");
	    	String[] kyhieuhd = request.getParameterValues("kyhieuhd");
			String[] sohd = request.getParameterValues("sohd");
			String[] ngayhd = request.getParameterValues("ngayhd");			
			String[] avat = request.getParameterValues("avat");
			String[] sotienNT = request.getParameterValues("sotienNT");
			String[] thanhtoan = request.getParameterValues("thanhtoan");
//			String[] conlai = request.getParameterValues("conlai");
			String[] mancc = request.getParameterValues("mancc");
//			String[] loaihd = request.getParameterValues("loaihd");
			String[] tigiahd = request.getParameterValues("tigiaHD");
			String[] loaihd_ = request.getParameterValues("loaihdId"); // Loại hóa đơn vs  ngày ghi nhận : >= 8/2014
			String[] loaiThue = request.getParameterValues("loaiThue");
			String[] doituong = request.getParameterValues("doituong");
			String[] doituongId = request.getParameterValues("doituongId");
			String[] diaChi = request.getParameterValues("diaChiHd");
			
			List<IHoadon> hdList =  new ArrayList<IHoadon>();
			
			if(thanhtoan != null)
			System.out.println("size cua thanh toan" + thanhtoan.length);
			

				if(kyhieuhd != null & tienteId != null)
				{		
					IHoadon hoadon = null;
					int m = 0;
					while(m < kyhieuhd.length)
					{				
							if(Nhomncccn.equals("1")){
								if(!ttId.equals("100000")){ //Là ngoại tệ														
									if(sotienNT == null){ // truong hop moi tao moi va chuyen doi tien te
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);										
										hoadon.setMancc(mancc[m]);
										hoadon.setLoaiThue(loaiThue[m]);
									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), sotienNT[m].replaceAll(",", ""), thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
										hoadon.setTigia(tigiahd[m]);
										hoadon.setMancc(mancc[m]);
										hoadon.setLoaiThue(loaiThue[m]);
									}
								}else{
									if(avat == null){
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0", "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
										hoadon.setMancc(mancc[m]);
										hoadon.setLoaiThue(loaiThue[m]);
									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
										hoadon.setMancc(mancc[m]);
										hoadon.setLoaiThue(loaiThue[m]);
									}
								}
							}
							else{
								if(!ttId.equals("100000")){ //Là ngoại tệ
								
									if(sotienNT == null){ // truong hop moi tao moi va chuyen doi tien te
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);										
										hoadon.setLoaiThue(loaiThue[m]);
									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), sotienNT[m].replaceAll(",", ""), thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
										hoadon.setTigia(tigiahd[m]);
										hoadon.setLoaiThue(loaiThue[m]);
									}
								
								}else{
									if(avat == null){
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], "0", "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
										hoadon.setLoaiThue(loaiThue[m]);
									}else{
										hoadon = new Hoadon(idHd[m], kyhieuhd[m], sohd[m], ngayhd[m], avat[m].replaceAll(",", ""), "0", thanhtoan[m].replaceAll(",", ""), tienteId[m], diaChi[m]);
										System.out.print("Loại thuế : "+ loaiThue[m]);
										hoadon.setLoaiThue(loaiThue[m]);
									}
								}
								
								if(tthdBean.getBophanId().trim().length() > 0)
								{
									hoadon.setDoituong(doituong[m]);
									hoadon.setDoituongId(doituongId[m]);
									hoadon.setLoaiThue(loaiThue[m]);
								}
							}
						
						hoadon.setLoaihd1(loaihd_[m]);
						hdList.add(hoadon);
					
						m++;
					}	
				}
				tthdBean.setHoadonRs(hdList);
	    	}
			
	    	List<IHoadon> hdList = tthdBean.getHoadonRs();
	    	String ServerletName = this.getServletName();
			if(action.equals("changncc") || action.equals("loaithanhtoan") )
			{
				//Thay doi loai thanh toan, ncc
				tthdBean.setHoadonRs(new  ArrayList<IHoadon>());
			}
			
			///////
			if(action.equals("saveTiGia")  & error == false)
			{
				if(!tthdBean.updateTTHD_TiGia())
				{
					System.out.println("Cập nhật tỉ giá không thành công " + tthdBean.getMsg());
					tthdBean.createRs();
	    		    
					session.setAttribute("tthdBean", tthdBean);
					String nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiDisplay.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{	IErpUynhiemchiList obj;
					obj = new ErpUynhiemchiList();
		    		obj.setcongtyId(ctyId);
		    		obj.setUserId(userId);
		    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
		    		obj.init("");
		    		session.setAttribute("obj", obj);
					
		    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChi.jsp";
		    		response.sendRedirect(nextJSP);
				}
				///
			}else if(action.equals("save")  & error == false)
			{	
				if(id == null) //tao moi
				{
					if(!tthdBean.createTTHD())
					{
						System.out.println("[ErpTTHoadonUpdateSvl.doPost] Saved fail. msg = " + tthdBean.getMsg());
	    	    		tthdBean.createRs();
	    		    
	    	    		session.setAttribute("tthdBean", tthdBean);
	    	    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiNew.jsp";
	    	    		response.sendRedirect(nextJSP);
	    	    		
	    	    		
	    	    		
					}
					
					else
					{
						
				
						IErpUynhiemchiList obj;
						obj = new ErpUynhiemchiList();
			    		obj.setcongtyId(ctyId);
			    		obj.setUserId(userId);
			    		String searchQuery=util.getSearchFromHM(userId,ServerletName, session);
		    			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
			    		obj.init("");
			    		
			    		session.setAttribute("obj", obj);
						
			    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChi.jsp";
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					if(!tthdBean.updateTTHD())
					{
						System.out.println("[ErpTTHoadonUpdateSvl.doPost] Saved fail. msg = " + tthdBean.getMsg());
						tthdBean.createRs();
		    		    
						session.setAttribute("tthdBean", tthdBean);
						String nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiUpdate.jsp";
						response.sendRedirect(nextJSP);
					}
					else
					{
						if(duyetchi.equals("1"))
						{
							IDuyetthanhtoanhoadon ddmhBean = new Duyetthanhtoanhoadon();
					   	    ddmhBean.setCtyId((String)session.getAttribute("congtyId"));
					   	    ddmhBean.setUserId(userId);
					   	    ddmhBean.setnppdangnhap(util.getIdNhapp(userId));
					    	String searchQuery=util.getSearchFromHM(userId,"ErpDuyetThanhToanHoaDonSvl", session);
					    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(ddmhBean, searchQuery);
					   	    ddmhBean.init();
							// Data is saved into session
							session.setAttribute("dtthdBean", ddmhBean);
							String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDuyetThanhToanHoaDon.jsp";
					   		response.sendRedirect(nextJSP);	
						}else{
						IErpUynhiemchiList obj;
						obj = new ErpUynhiemchiList();
			    		obj.setcongtyId(ctyId);
			    		obj.setUserId(userId);
			    		String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
		    			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
			    		obj.init("");
			    		
			    		session.setAttribute("obj", obj);
						
			    		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChi.jsp";
			    		response.sendRedirect(nextJSP);
						}
					}
				}
			}
			else if(action.equals("changeTT")){
				//tthdBean.setSotaikhoan("");
				tthdBean.setTigia("");
				/*tthdBean.setSotienHDNT("0");
				tthdBean.setSotienHD("0");
				tthdBean.setPhinganhangNT("0");
				tthdBean.setPhinganhang("0");
				tthdBean.setThueVATNT("0");
				tthdBean.setThueVAT("0");
				tthdBean.setSotienttNT("0");
				tthdBean.setSotientt("0");
				tthdBean.setChenhlech("0");*/
				tthdBean.taoMoiSoChungTu();
				
				hdList.clear();
				tthdBean.setHoadonRs(hdList);

				String nextJSP;
				tthdBean.createRs();

				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiUpdate.jsp";   						
				}
			
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}else if(action.equals("changeTP")){
		    	tthdBean.setPhinganhangNT("0");
		    	tthdBean.setPhinganhang("0");
		    	tthdBean.setThueVATNT("0");
		    	tthdBean.setThueVAT("0");
		    	tthdBean.setSotienttNT(tthdBean.getSotienHDNT());
		    	tthdBean.setSotientt(tthdBean.getSotienHD());
		    	
		    	double chenhlech = Double.parseDouble(tthdBean.getSotienHDNT().replaceAll(",", ""))*Double.parseDouble(tthdBean.getTigia().replaceAll(",", "")) - Double.parseDouble(tthdBean.getSotienHD().replaceAll(",", ""));
		    	tthdBean.setChenhlech("" + chenhlech);
				hdList.clear();
				tthdBean.setHoadonRs(hdList);

				tthdBean.createRs();
				String nextJSP;
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else if(action.equals("changeHTTT")){
				//tthdBean.setSotaikhoan("");

				String nextJSP;
				hdList.clear();
				tthdBean.createRs();
				tthdBean.setHoadonRs(hdList);
				
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
				
			}else
			{
				String nextJSP;
				if(!error) {
					hdList.clear();
					tthdBean.setHoadonRs(hdList);
				}
		
				tthdBean.createRs();
		
				if (id == null){
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiNew.jsp";
				}
				else{
					nextJSP = "/TraphacoHYERP/pages/Erp/ErpUyNhiemChiUpdate.jsp";   						
				}
				
				session.setAttribute("tthdBean", tthdBean);
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String CheckDoiTuongDinhKhoan(String dinhkhoanno, String congtyId) 
	{
		db = new dbutils();
		
		String doituongdinhkhoan= "";
		String dungchokho = "";
		String dungchonganhang = "";
		String dungchoncc = "";
		String dungchotaisan = "";
		String dungchokhachhang = "";
		String dungchonhanvien = "";
		
		String query= "";
		query = "Select isnull(dungchokho,0) dungchokho, isnull(dungchonganhang, 0)dungchonganhang, " +
				" isnull(dungchoncc,0) dungchoncc, isnull(dungchotaisan,0) dungchotaisan, isnull(dungchokhachhang,0) dungchokhachhang , isnull(dungchonhanvien, 0) dungchonhanvien "+
				"from ERP_TAIKHOANKT where  SOHIEUTAIKHOAN = '" + dinhkhoanno +"' and CONGTY_FK = "+congtyId;
		System.out.println(query);
		ResultSet dtrs = this.db.get(query);
		try {
			while(dtrs.next())
			{
				dungchokho = dtrs.getString("dungchokho");
				dungchonganhang = dtrs.getString("dungchonganhang");
				dungchoncc = dtrs.getString("dungchoncc");
				dungchotaisan = dtrs.getString("dungchotaisan");
				dungchokhachhang = dtrs.getString("dungchokhachhang");
				dungchonhanvien = dtrs.getString("dungchonhanvien");				
			}
			dtrs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		if(dungchokho.equals("1"))
		{
			doituongdinhkhoan = "1";
		}
		else if(dungchonganhang.equals("1"))
		{
			doituongdinhkhoan = "2";
		}
		else if(dungchoncc.equals("1"))
		{
			doituongdinhkhoan = "3";
		}
		else if(dungchotaisan.equals("1"))
		{
			doituongdinhkhoan = "4";
		}
		else if(dungchokhachhang.equals("1"))
		{
			doituongdinhkhoan = "5";
		}
		else if(dungchonhanvien.equals("1"))
		{
			doituongdinhkhoan = "6";
		}
		
		return doituongdinhkhoan;
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}