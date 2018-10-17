package geso.traphaco.center.servlets.dondathang;

import geso.traphaco.center.beans.dondathang.IErpDondathang;
import geso.traphaco.center.beans.dondathang.IErpDondathangList;
import geso.traphaco.center.beans.dondathang.imp.ErpDondathang;
import geso.traphaco.center.beans.dondathang.imp.ErpDondathangList;
import geso.traphaco.center.beans.dondathang.imp.ErpThongtinkho;
import geso.traphaco.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.TimeZone;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.CellType;
import jxl.DateCell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.Cell;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.oreilly.servlet.MultipartRequest;

public class ErpDondathangUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDondathangUpdateSvl() 
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
		    IErpDondathang lsxBean = new ErpDondathang(id);
		    lsxBean.setUserId(userId); 
		    
		    String nextJSP = "";
		    
    		lsxBean.init();
    		
    		session.setAttribute("dvkdId", lsxBean.getDvkdId());
    		session.setAttribute("khoId", lsxBean.getKhoNhapId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khachhangId", lsxBean.getNppId());
			session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());
			session.setAttribute("loaidonhang", lsxBean.getLoaidonhang());
    		
    		if(!querystring.contains("display"))
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
    			if(lsxBean.getLoaidonhang().equals("4"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacUpdate.jsp";
    			else if(lsxBean.getLoaidonhang().equals("2"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
    			else if(lsxBean.getLoaidonhang().equals("1"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangUpdate.jsp";
    			else if(lsxBean.getLoaidonhang().equals("3"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVLUpdate.jsp";
    			else if(lsxBean.getLoaidonhang().equals("5"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoUpdate.jsp";
    		}
    		else
    		{
    			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangDisplay.jsp";
    			if(lsxBean.getLoaidonhang().equals("4"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacDisplay.jsp";
    			else if(lsxBean.getLoaidonhang().equals("2"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKDisplay.jsp";
    			else if(lsxBean.getLoaidonhang().equals("1"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangDisplay.jsp";
    			else if(lsxBean.getLoaidonhang().equals("3"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVLDisplay.jsp";
    			else if(lsxBean.getLoaidonhang().equals("5"))
    				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoDisplay.jsp";
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
			
			String contentType = request.getContentType();
			if ((contentType != null) && (contentType.indexOf("multipart/form-data") >= 0))
			{
				GET_DATA_FORM_EXCEL( request, response, cutil );
				return;
			}
			
			IErpDondathang lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDondathang("");
		    }
		    else
		    {
		    	lsxBean = new ErpDondathang(id);
		    }
	
		    lsxBean.setUserId(userId);
		    String ETC = util.antiSQLInspection(request.getParameter("ETC") == null? "0" : request.getParameter("ETC"));
			lsxBean.setETC(ETC);
			
		    String ngayyeucau = util.antiSQLInspection(request.getParameter("ngaychuyen"));
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = util.antiSQLInspection(request.getParameter("ngaydenghi"));
		    if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			session.setAttribute("khoId", lsxBean.getKhoNhapId());
			
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang);
			session.setAttribute("loaidonhang", loaidonhang);
			
			String danhmucKH = util.antiSQLInspection(request.getParameter("danhmucKH"));
			if (danhmucKH == null)
				danhmucKH = "0";				
			lsxBean.setDanhmucKH(danhmucKH);
			
			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";				
			lsxBean.setTungay(tungay);
			
			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";				
			lsxBean.setDenngay(denngay);
			
			String iskm = util.antiSQLInspection(request.getParameter("iskm") == null?"0":request.getParameter("iskm"));
			lsxBean.setIsKm(iskm);

			String isdhk = util.antiSQLInspection(request.getParameter("isdhk") == null?"0":request.getParameter("isdhk"));
			lsxBean.setIsdhk(isdhk);

			String isgia = util.antiSQLInspection(request.getParameter("isgia") == null?"0":request.getParameter("isgia"));
			lsxBean.setIsgia(isgia);
			
			String ishm = util.antiSQLInspection(request.getParameter("ishm") == null?"0":request.getParameter("ishm"));
			lsxBean.setIshm(ishm);
			
			String phanloai = util.antiSQLInspection(request.getParameter("phanloai") == null?"":request.getParameter("phanloai"));
			lsxBean.setPhanloai(phanloai);
			
			String mahopdong = util.antiSQLInspection(request.getParameter("mahopdong")==null?"":request.getParameter("mahopdong"));
			lsxBean.setMahopdong(mahopdong);
			String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId") == null ? "" : request.getParameter("gsbhId"));
			lsxBean.setGsbhId(gsbhId);
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId") == null? "" : request.getParameter("ddkdId"));
			lsxBean.setDdkdId(ddkdId);
			System.out.println("lsxBean.getDdkdId(): "+ lsxBean.getDdkdId());
			String[] schemeIds = request.getParameterValues("schemeIds");
			if (schemeIds != null)
			{
				String _scheme = "";
				for(int i = 0; i < schemeIds.length; i++)
				{
					_scheme += schemeIds[i] + ",";
				}
				
				if(_scheme.trim().length() > 0)
				{
					_scheme = _scheme.substring(0, _scheme.length() - 1);
					lsxBean.setSchemeId(_scheme);
				}
			}
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] spvat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spvat);
			
			String[] sptienvat = request.getParameterValues("sptienvat");
			lsxBean.setSptienvat(sptienvat);
			
			
			String[] spQuyDoi = request.getParameterValues("spQuyDoi");
			lsxBean.setSpQuyDoi(spQuyDoi);
			
			String[] chietkhau = request.getParameterValues("chietkhau");
			lsxBean.setSpChietkhau(chietkhau);
			
			
			String[] chietkhaugia = request.getParameterValues("chietkhaugia");
			lsxBean.setSpchietkhaugia(chietkhaugia);
			
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			String[] trongluong = request.getParameterValues("spTrongLuong");
			lsxBean.setSpTrongluong(trongluong);
			
			String[] thetich = request.getParameterValues("spTheTich");
			lsxBean.setSpThetich(thetich);

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
		    String stt = request.getParameter("sttupdate");
		    
		    if(spMa != null && !loaidonhang.equals("5")  )  //LUU LAI THONG TIN NGUOI DUNG NHAP
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++ )
				{
					System.out.println("action = " + action + "; i = " + i + "; stt = " + stt);
					if(!action.equals("submitSP") || (action.equals("submitSP") && i != Integer.parseInt(stt)))
					{
						String temID = spMa[i] + "__" + spScheme[i];
						//System.out.println("---TEMP ID: " + temID);
						
						String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
						String[] spNGAYHETHAN = request.getParameterValues(temID + "_spNGAYHETHAN");
						String[] spNGAYNHAPKHO = request.getParameterValues(temID + "_spNGAYNHAPKHO");
						String[] spVITRI = request.getParameterValues(temID + "_spVITRI");
						String[] spMATHUNG = request.getParameterValues(temID + "_spMATHUNG");
						String[] spMAME = request.getParameterValues(temID + "_spMAME");
						String[] spMAPHIEU = request.getParameterValues(temID + "_spMAPHIEU");
						String[] spMARQ = request.getParameterValues(temID + "_spMARQ");
						String[] spNSX = request.getParameterValues(temID + "_spNSX");
						String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
						
						if(soLUONGXUAT != null)
						{
							for(int j = 0; j < soLUONGXUAT.length; j++ )
							{
								//1 số sp không có lô
								//if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0 && spSOLO[j].trim().length() > 0 )
								if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0  )
								{
									System.out.println("---KEY SVL: " + ( spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spVITRI[j]  + "__" + spMATHUNG[j] + "__" + spMAME[j] ) + "__" + spMAPHIEU[j] + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
									sanpham_soluong.put(spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j] + "__" + spNGAYHETHAN[j] + "__" + spVITRI[j] + "__" + spMATHUNG[j] + "__" + spMAME[j] + "__" + spMAPHIEU[j] + "__" + spMARQ[j] + "__" + spNSX[j]+ "__" + spNGAYNHAPKHO[j], soLUONGXUAT[j].replaceAll(",", "") );
								}
							}
						}
					}
				}
				
				lsxBean.setSanpham_Soluong(sanpham_soluong);
			}
		    else if( loaidonhang.equals("5") ) //ĐƠN NỘI BỘ LƯU LẠI THÔNG TIN KHUYẾN MẠI
		    {
		    	String[] thanhtienNB = request.getParameterValues("thanhtienNB");
		    	String[] thanhtienNB_vat = request.getParameterValues("thanhtienNB_vat");
		    	String[] thanhtienNB_bvat = request.getParameterValues("thanhtienNB_bvat");
		    	String[] schemeNB = request.getParameterValues("schemeNB");
		    	
		    	Hashtable<String, String> schemeNoibo = new Hashtable<String, String>();
		    	Hashtable<String, String> schemeNoibo_vat = new Hashtable<String, String>();
		    	Hashtable<String, String> schemeNoibo_bvat = new Hashtable<String, String>();
		    	if( thanhtienNB != null )
		    	{
		    		for( int i = 0; i < thanhtienNB.length; i++ )
		    		{
		    			schemeNoibo.put(schemeNB[i], thanhtienNB[i]);
		    			schemeNoibo_vat.put(schemeNB[i], thanhtienNB_vat[i]);
		    			schemeNoibo_bvat.put(schemeNB[i], thanhtienNB_bvat[i]);
		    		}
		    		lsxBean.setSchemeNoibo(schemeNoibo);
		    		lsxBean.setSchemeNoibo_vat(schemeNoibo_vat);
		    		lsxBean.setSchemeNoibo_bvat(schemeNoibo_bvat);
		    	}
		    }
		    
			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = false;
					/*if(loaidonhang.equals("2"))
						kq = lsxBean.createKMTichLuy();
					else if(loaidonhang.equals("1"))
						kq = lsxBean.createKMUngHang();
					else if(loaidonhang.equals("3"))
						kq = lsxBean.createTrungBay();
					else */
					if(loaidonhang.equals("5"))
						kq = lsxBean.createNoiBo();
					else
						kq = lsxBean.createNK();
					
					if(!kq)
					{
						lsxBean.createRs();
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangNew.jsp";
	    				if(loaidonhang.equals("4"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacNew.jsp";
	    				else if(loaidonhang.equals("2"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKNew.jsp";
	    				else if(loaidonhang.equals("1"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangNew.jsp";
	    				else if(loaidonhang.equals("3"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVLNew.jsp";
	    				else if(loaidonhang.equals("5"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoNew.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangList obj = new ErpDondathangList();
						obj.setLoaidonhang(loaidonhang);
						
						obj.setUserId(userId);
						obj.setETC(ETC);
						
						obj.init("");  
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHang.jsp";
			    		if(loaidonhang.equals("4"))
							nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhac.jsp";
			    		/*else if(loaidonhang.equals("2"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNK.jsp";
			    		else if(loaidonhang.equals("1"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHang.jsp";
			    		else if(loaidonhang.equals("3"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVL.jsp";*/
			    		else if(loaidonhang.equals("5"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBo.jsp";
			    		
			    		response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = false;
					/*if( loaidonhang.equals("2") )
						kq = lsxBean.updateKMTichLuy();
					else if(loaidonhang.equals("1"))
						kq = lsxBean.updateKMTichLuy();
					else if(loaidonhang.equals("3"))
						kq = lsxBean.updateTrungBay();
					else */if(loaidonhang.equals("5"))
						kq = lsxBean.updateNoiBo();
					else
						kq = lsxBean.updateNK("1");
					
					if(!kq)
					{
						lsxBean.createRs();
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
	    				if(loaidonhang.equals("4"))
							nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacUpdate.jsp";
	    				else if(loaidonhang.equals("2"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
	    				else if(loaidonhang.equals("1"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangUpdate.jsp";
	    				else if(loaidonhang.equals("3"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVLUpdate.jsp";
	    				else if(loaidonhang.equals("5"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoUpdate.jsp";
	    				
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						IErpDondathangList obj = new ErpDondathangList();
						obj.setLoaidonhang(loaidonhang);
						
					    obj.setUserId(userId);
					    obj.setETC(ETC);
						GiuDieuKienLoc.getSetDieuKienLoc(session, this.getServletName(), Thread.currentThread().getStackTrace()[1].getMethodName(), action, userId, obj);
					    obj.init("");
						session.setAttribute("obj", obj);							
						String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHang.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhac.jsp";
						/*else if(loaidonhang.equals("2"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNK.jsp";
						else if(loaidonhang.equals("1"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHang.jsp";
						else if(loaidonhang.equals("3"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVL.jsp";*/
						else if(loaidonhang.equals("5"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBo.jsp";
						
						response.sendRedirect(nextJSP);
					}
				}
			}
			else
			{
				if(action.equals("apkhuyenmai"))
				{
					//Save donhang truoc
					if(id == null)
					{   
						boolean tao = lsxBean.createNK();
						if (!tao)
						{
							lsxBean.createRs();
		    		    	session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangNew.jsp";
		    				if(loaidonhang.equals("2"))
		    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKNew.jsp";
		    				response.sendRedirect(nextJSP);
		    				return;
						}
						else
						{
							id = lsxBean.getId();		
						}						
					}
					else
					{
						boolean temp = lsxBean.updateNK("0");
						
						if (!temp)
						{
							lsxBean.createRs();
							session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
		    				if(loaidonhang.equals("2"))
		    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
		    				response.sendRedirect(nextJSP);
		    				return;
						}
					}
					
					Hashtable<String, Float> sanpham_soluong = new Hashtable<String, Float>();
					Hashtable<String, Float> sanpham_dongia = new Hashtable<String, Float>();
					Hashtable<String, Float> sanpham_quycach = new Hashtable<String, Float>();
					
					String soluong1 = "";
					String spMaKM = "";
					String spSOLUONGKM = "";
					String spDONGIAKM = "";
					float tongiatriDH = 0;

					if(id.trim().length() > 0)
					{	
						//INIT SP VOI QUY CACH NEU TRUONG HOP KHONG PHAI LA DV CHUAN
						dbutils db = new dbutils();
						try
						{
							db.getConnection().setAutoCommit(false);
							//XOA HET KM CU NEU CO --> kho đã tăng ngược lại lúc vào cập nhật đơn hàng rồi
							String query = " delete ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID = '" + id + "' ";
							if(!db.update(query))
							{
								db.getConnection().rollback();
								lsxBean.init();
				    			lsxBean.setMsg("khong the them moi 'dondathang_ctkm_trakm' " + query);
						    	session.setAttribute("lsxBean", lsxBean);
						        String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
						        response.sendRedirect(nextJSP);
						        return;
							}
							query = "  select b.NgayDonHang, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
									" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuongCHUAN ) as soluong  " + 
									"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.PK_SEQ " + 
									"  where b.PK_SEQ = '" + id + "' and isnull(a.scheme, '') <> '' " + 
									"  group by b.NgayDonHang, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
							
							System.out.println("::: CAP NHAT KHO: " + query);
							ResultSet rs = db.get(query);
							if( rs != null )
							{
								while( rs.next() )
								{
									String khoId = rs.getString("Kho_FK");
									String spId = rs.getString("SanPham_fk");
									String solo = rs.getString("SoLo");
									String ngayhethan = rs.getString("NgayHetHan");
									String ngaynhapkho = rs.getString("ngaynhapkho");
									
									String loaidoituong = "";
									String doituongId = "";
									
									String mame = rs.getString("mame");
									String mathung = rs.getString("mathung");
									String bin_fk = rs.getString("bin_fk");
									
									String maphieu = rs.getString("maphieu");
									String phieudt = rs.getString("phieudt");
									String phieueo = rs.getString("phieueo");
									
									String marq = rs.getString("marq");
									String hamluong = rs.getString("hamluong");
									String hamam = rs.getString("hamam");
	
									double booked = rs.getDouble("soluong");
									
									String msg = util.Update_KhoTT(rs.getString("NgayDonHang"), "Cập nhật DH - Tăng kho ngược lại trước khi áp khuyến mãi ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
											mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * booked, booked, 
											"", "", "" );
									
									if(msg.length() > 0)
						    		{
										db.getConnection().rollback();
						    			lsxBean.init();
						    			lsxBean.setMsg("khong the them moi 'dondathang_ctkm_trakm' " + msg);
								    	session.setAttribute("lsxBean", lsxBean);
								        String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
								        if(loaidonhang.equals("2"))
					    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
								        response.sendRedirect(nextJSP);
								        return;
						    		}
								}
								rs.close();
							}
							query = " delete ERP_DONDATHANG_SANPHAM_CHITIET where DONDATHANG_FK = '" + id + "' and isnull(scheme, '') <> '' ";
							if(!db.update(query))
							{
								db.getConnection().rollback();
								lsxBean.init();
				    			lsxBean.setMsg("khong the them moi 'dondathang_ctkm_trakm' " + query);
						    	session.setAttribute("lsxBean", lsxBean);
						        String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
						        if(loaidonhang.equals("2"))
			    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
						        response.sendRedirect(nextJSP);
						        return;
							}
							db.getConnection().commit();
							db.getConnection().setAutoCommit(true);
						}
						catch(Exception e)
						{
							e.printStackTrace();
						}
						
						String query = "  select ( select ma from ERP_SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, a.dvdl_fk, b.DVDL_FK as dvCHUAN,  " +
								" 		case when a.dvdl_fk = b.DVDL_FK then a.soluong  " +
								"  			 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as soluong, " +
								"  		case when a.dvdl_fk = b.DVDL_FK then a.dongia  " +
								"  			 else  a.dongia / ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as dongia,  " +
								"  		1 as quycach    " +
								"  from ERP_DONDATHANG_SANPHAM a inner join ERP_SANPHAM b on a.sanpham_fk = b.PK_SEQ    " +
								"  where a.dondathang_fk = '" + id + "'  " ;
						System.out.println("---LAY QUY CACH: " + query);
						ResultSet rsSP = db.get(query);
						
						if(rsSP != null)
						{
							try 
							{
								while(rsSP.next())
								{
									sanpham_soluong.put(rsSP.getString("spMA"), rsSP.getFloat("soluong"));
									sanpham_dongia.put(rsSP.getString("spMA"), rsSP.getFloat("dongia"));
									sanpham_quycach.put(rsSP.getString("spMA"), rsSP.getFloat("quycach"));
									
									spMaKM += rsSP.getString("spMA") + "__";
									spSOLUONGKM += rsSP.getInt("soluong") + "__";
									spDONGIAKM += rsSP.getString("dongia") + "__";
									
									soluong1 += rsSP.getString("quycach") + "__";
									tongiatriDH += rsSP.getInt("soluong") * rsSP.getFloat("dongia");
								}
								rsSP.close();
							} 
							catch (Exception e) {}	
						}
						
						db.shutDown();
					}
					
					XLkhuyenmaiTT xlkm = new XLkhuyenmaiTT(userId, ngayyeucau, nppId, id); //ngay giao dich trong donhang
					xlkm.setKhachhang(nppId);
					xlkm.setMasp(spMaKM.substring(0, spMaKM.length() - 2).split("__"));
					xlkm.setSoluong(spSOLUONGKM.substring(0, spSOLUONGKM.length() - 2).split("__"));
					xlkm.setDongia(spDONGIAKM.substring(0, spDONGIAKM.length() - 2).split("__"));
					
					xlkm.setQuycach(soluong1.substring(0, soluong1.length() - 2).split("__"));
					xlkm.setTonggiatriDh(tongiatriDH);
					xlkm.setIdDonhang(id);
					xlkm.setNgaygiaodich(ngayyeucau);
					xlkm.setLoaiDonHang("0");
					xlkm.setETC(ETC);
					
					xlkm.setHashA(sanpham_soluong);
					xlkm.setHashB(sanpham_dongia);
					xlkm.setHash_QuyCach(sanpham_quycach);
					
					xlkm.setDieuchinh(false); //Lay truong hop ngau nhien /*****OneOne set lai la True******/
					
					xlkm.ApKhuyenMai();
					
				    List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
				    System.out.println("+++So xuat khuyen mai duoc huong: " + ctkmResual.size() + "\n");
				    
				    if( ctkmResual.size() > 0 )
				    {
					    if(xlkm.checkConfirm()) //bi dung --> sang trang lua chon
					    {
					    	System.out.println("Bi dung san pham roi...\n");
					    	session.setAttribute("xlkm", xlkm);
							String nextJSP = "/TraphacoHYERP/pages/Center/KhuyenMaiTT.jsp";
							response.sendRedirect(nextJSP);
							return;
					    }
					    
					    String msg = ""; //nhung ctkm khong thoa
						for(int i = 0; i < ctkmResual.size(); i++)
					    {
					    	ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);
					    	
					    	System.out.println("ConFi laf: "+ctkhuyenmai.getConfirm());
					    	if(ctkhuyenmai.getConfirm() == false) //khong dung chung san pham
					    	{	
					    		msg += CreateKhuyenmai(ctkhuyenmai, id, nppId, ngayyeucau, Math.round(tongiatriDH), sanpham_soluong, sanpham_dongia);
					    		
						    	//remove khoi danh sach
					    		ctkmResual.remove(i);	
					    		i = i -1;
					    	}
					    }

						if(msg.length() > 0)
			    		{
			    			lsxBean.init();
							
							xlkm.DBclose();
			    			lsxBean.setMsg("khong the them moi 'dondathang_ctkm_trakm' " + msg);
					    	session.setAttribute("lsxBean", lsxBean);
					        String nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
					        if(loaidonhang.equals("2"))
		    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
					        response.sendRedirect(nextJSP);
					        return;
			    		}
				    }
				    
				    String nextJSP = "";
				    if(ctkmResual.size() > 0)
				    {
						session.setAttribute("xlkm", xlkm);
						nextJSP = "/TraphacoHYERP/pages/Center/KhuyenMaiTT.jsp";
						response.sendRedirect(nextJSP);
				    }
				    else
				    {	
				    	xlkm.DBclose();
				    	lsxBean.init();

				    	session.setAttribute("lsxBean", lsxBean);
				        nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
				        if(loaidonhang.equals("2"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
				        response.sendRedirect(nextJSP);
				        return;
				    }
				}
				else
				{
					if( action.equals("loadsanpham") )
					{
						lsxBean.setSpMa(null);
					}
						
					lsxBean.createRs();
					
					session.setAttribute("dvkdId", lsxBean.getDvkdId());
					session.setAttribute("kbhId", lsxBean.getKbhId());
					session.setAttribute("khachhangId", lsxBean.getNppId());
					session.setAttribute("ngaydonhang", lsxBean.getNgayyeucau());
		    		
					session.setAttribute("lsxBean", lsxBean);
					
					String nextJSP = "";
					
					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangNew.jsp";
					if(loaidonhang.equals("4"))
						nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacNew.jsp";
					else if(loaidonhang.equals("2"))
						nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKNew.jsp";
					else if(loaidonhang.equals("1"))
						nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangNew.jsp";
					else if(loaidonhang.equals("3"))
						nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVLNew.jsp";
					else if(loaidonhang.equals("5"))
						nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoNew.jsp";
					
					if(id != null)
					{
						nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangUpdate.jsp";
						if(loaidonhang.equals("4"))
							nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacUpdate.jsp";
						else if(loaidonhang.equals("2"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangXNKUpdate.jsp";
						else if(loaidonhang.equals("1"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangKMUngHangUpdate.jsp";
						else if(loaidonhang.equals("3"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNVLUpdate.jsp";
						else if(loaidonhang.equals("5"))
	    					nextJSP = "/TraphacoHYERP/pages/Center/ErpDonHangNoiBoUpdate.jsp";
					}
					
					response.sendRedirect(nextJSP);
				}
			}
		}
	}
	
	
	private void GET_DATA_FORM_EXCEL(HttpServletRequest request, HttpServletResponse response, geso.traphaco.center.util.Utility util) 
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");  
		
		String UPLOAD_DIRECTORY = "C:\\upload\\excel\\";
		MultipartRequest multi;
		try 
		{
			multi = new MultipartRequest(request, UPLOAD_DIRECTORY, 20000000, "UTF-8");
			
			String action = util.antiSQLInspection(multi.getParameter("action"));
			if (action == null)
				action = "";
			System.out.println("Action Request encrypt" + action);
			String id = util.antiSQLInspection(multi.getParameter("id"));
			IErpDondathang lsxBean;
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDondathang("");
		    }
		    else
		    {
		    	lsxBean = new ErpDondathang(id);
		    }

		    lsxBean.setUserId(userId);
		    
		    String ngayyeucau = multi.getParameter("ngaychuyen");
		    if(ngayyeucau == null || ngayyeucau.trim().length() <= 0)
		    	ngayyeucau = getDateTime();
		    lsxBean.setNgayyeucau(ngayyeucau);
		    
		    String ngaydenghi = multi.getParameter("ngaydenghi");
		    if(ngaydenghi == null || ngaydenghi.trim().length() <= 0)
		    	ngaydenghi = "";
		    lsxBean.setNgaydenghi(ngaydenghi);
		    	    
		    String ghichu = multi.getParameter("ghichu");
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = multi.getParameter("khonhapId");
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = multi.getParameter("dvkdId");
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = multi.getParameter("kbhId");
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String nppId = multi.getParameter("nppId");
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);

			String loaidonhang = multi.getParameter("loaidonhang");
			if (loaidonhang == null)
				loaidonhang = "";				
			lsxBean.setLoaidonhang(loaidonhang);
			
			//DOC DU LIEU TU EXCEL FILE
			Enumeration files = multi.getFileNames();
			String filename = "";
			while (files.hasMoreElements())
			{
				String name = (String) files.nextElement();
				filename = multi.getFilesystemName(name);
				System.out.println("File  " + UPLOAD_DIRECTORY + filename);
			}
			
			lsxBean.createRs();
			if (filename != null && filename.length() > 0)
			{
				this.readExcel(lsxBean, UPLOAD_DIRECTORY + filename);
			}
			
			session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("khachhangId", lsxBean.getNppId());
			
			session.setAttribute("lsxBean", lsxBean);
			
			String nextJSP = "";
			nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacNew.jsp";
			if(id != null)
			{
				nextJSP = "/TraphacoHYERP/pages/Center/ErpDonDatHangKhacUpdate.jsp";
			}
			
			response.sendRedirect(nextJSP);
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}

	}

	private void readExcel(IErpDondathang lsxBean, String fileName) 
	{
		File inputWorkbook = new File(fileName);
		Workbook w;
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			System.out.println("So dong " + sodong + "socot " + socot );
			String _ma = "";
			String _ten = "";
			String _dvt = "";
			String _soluong = "";
			String _dongia = "";
			String _chietkhau = "";
			String _trongluong = "";
			String _thetich = "";
			String _scheme = "";
			
			for (int i = 3; i < sodong; i++)
			{
				String maSP = sheet.getCell(0, i).getContents();
				String tenSP = sheet.getCell(1, i).getContents();
				String soluongSP = sheet.getCell(2, i).getContents().replaceAll(",", "");
				
				if(maSP.trim().length() > 0 && tenSP.trim().length() > 0 && soluongSP.trim().length() > 0  && !soluongSP.trim().equals("0") )
				{
					//System.out.println("MA SP: " +  maSP + "  -- TEN SP: " + tenSP + " -- SO LUONG: " + soluongSP );
					
					_ma += maSP + "__";
					_ten += tenSP + "__";
					_dvt += " __";
					_soluong += soluongSP + "__";
					_dongia += "0__";
					_chietkhau += " __";
					_trongluong += "0__";
					_thetich += "0__";
					_scheme += " __";
				}
				
			}
			
			if(_ma.trim().length() > 0)
			{
				String[] spMa = _ma.substring(0, _ma.length() - 2).split("__");
				lsxBean.setSpMa(spMa);
				
				String[] spTen = _ten.substring(0, _ten.length() - 2).split("__");
				lsxBean.setSpTen(spTen);
				
				String[] dvt = _dvt.substring(0, _dvt.length() - 2).split("__");
				lsxBean.setSpDonvi(dvt);
				
				String[] soluong = _soluong.substring(0, _soluong.length() - 2).split("__");
				lsxBean.setSpSoluong(soluong);
				
				String[] dongia = _dongia.substring(0, _dongia.length() - 2).split("__");
				lsxBean.setSpGianhap(dongia);
				
				String[] chietkhau = _chietkhau.substring(0, _chietkhau.length() - 2).split("__");
				lsxBean.setSpChietkhau(chietkhau);
				
				String[] trongluong = _trongluong.substring(0, _trongluong.length() - 2).split("__");
				lsxBean.setSpTrongluong(trongluong);
				
				String[] thetich = _thetich.substring(0, _thetich.length() - 2).split("__");
				lsxBean.setSpThetich(thetich);
				
				String[] spScheme = _scheme.substring(0, _scheme.length() - 2).split("__");
				lsxBean.setSpScheme(spScheme);
			}
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			lsxBean.setMsg("Loi doc file Excel" + e.getMessage());
		}
		
	}

	private String CreateKhuyenmai(ICtkhuyenmai ctkm, String id, String nppId, String tungay, long tongGtridh, Hashtable<String, Float> sp_sl, Hashtable<String, Float> sp_dg)
	{
		String str = "";
		dbutils db = new dbutils();
		
		try 
		{ 
			db.getConnection().setAutoCommit(false);
		
			List<ITrakhuyenmai> trakmList = ctkm.getTrakhuyenmai();
			for(int count = 0; count < trakmList.size(); count++)
			{
				//ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(0);			
				ITrakhuyenmai trakm = ctkm.getTrakhuyenmai().get(count);
				
				long tongtienTraKM = 0;
				if(trakm.getType() == 1)
					tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongtien());
				else
				{
					if(trakm.getType() == 2) //tra chiet khau
					{
						System.out.println("___Tong tien tra km: " + ctkm.getTongTienTheoDKKM() + " -- Chiet khau: " + trakm.getChietkhau());
						//Tinh tong gia tri tra khuyen mai theo dieu kien (chu khong phai tren tong gia tri don hang)
						long tonggiatriTrakm = ctkm.getTongTienTheoDKKM();
						tongtienTraKM = Math.round(tonggiatriTrakm * (trakm.getChietkhau() / 100));
						//tongtienTraKM = Math.round(tongGtridh * (trakm.getChietkhau() / 100));
					}
					else
					{
						if(trakm.getHinhthuc() == 1)
						{
							tongtienTraKM = Math.round(ctkm.getSoxuatKM() * trakm.getTongGtriKm());
							System.out.println("Tong tien trakm co dinh: " + tongtienTraKM + "\n");
						}
					}
				}
				
				/*********************************************************************************/
				if(ctkm.getPhanbotheoDH().equals("0"))
				{
					String msg = "";
					if(msg.trim().length() > 0)
					{
						db.getConnection().rollback();
						return msg;
					}
				}
				/*********************************************************************************/
					
				if(trakm.getType() == 3) //san pham co so luong co dinh
				{
					String query = "select kho_fk, ngaydonhang from ERP_DONDATHANG where pk_seq = '" + id + "'";
					String kho_fk = "", ngaygiaodich = "";
					ResultSet KhoRS = db.get(query);
					if(KhoRS != null)
					{
						KhoRS.next();
						kho_fk = KhoRS.getString("kho_fk");
						ngaygiaodich = KhoRS.getString("ngaydonhang");
						KhoRS.close();
					}
					if(trakm.getHinhthuc() == 1)
					{

						String sql = "select f.pk_seq as spId, a.soluong, 0 as dongia, f.ma as spMa  " +
									"from Trakhuyenmai_sanpham a inner join ERP_SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
									//"	inner join BGMUANPP_SANPHAM e on a.sanpham_fk = e.SANPHAM_FK " +
									"where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' ";
									//"	and e.BGMUANPP_FK in ( select top(1) a.PK_SEQ " +
									//"							from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
									//"							where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = '" + id + "' ) and a.DVKD_FK = f.DVKD_FK " +
									//"							order by a.TUNGAY desc  ) -- and e.GIAMUANPP > 0  and e.trangthai = '1'  ";
						
						System.out.println("Query lay gia san pham co dinh la: " + sql + "\n");
						
						int index = 0;
						ResultSet rsSQl = db.get(sql);
						if(rsSQl != null)
						{
							while(rsSQl.next())
							{
								float slg = Float.parseFloat(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
								long tonggtri = Math.round(slg * rsSQl.getFloat("dongia"));
								
								
								/*********************************************************************************/
								if(ctkm.getPhanbotheoDH().equals("1"))
								{
									String msg = "";
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return msg;
									}
								}
								/*********************************************************************************/
								
								
								String error = "";
								if(error.length() > 0)
								{
									return error;
								}
								
								//luu tong gia tri o moi dong sanpham
								//String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tongtienTraKM) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, inchung, kho_fk) select '" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "'," + Long.toString(tonggtri) + ", '" + rsSQl.getString("spMa").trim() + "', " + Float.toString(slg) + ", inchung, kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "'";
								System.out.println("1.Chen khuyen mai co dinh: " + query);
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								//KIỂM TRA VÀ ĐỀ XUẤT SỐ LÔ MẶC ĐỊNH
								error = this.KiemTraVSDeXuatLo( db, id, ngaygiaodich, kho_fk, ctkm.getId(), rsSQl.getString("spMa").trim(), slg );
								if( error.trim().length() > 0 )
								{
									db.getConnection().rollback(); 
    								return error;
								}
							
								/*query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtienTraKM) + "' where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
								System.out.println("4.Cap nhat phanbo Phanbokhuyenmai: " + query); 
								if(!db.update(query))
								{
									db.getConnection().rollback(); 
									str = query;
									return str;
								}*/
								
								index ++;
							}
						}
						rsSQl.close();
					}
					else //tinh so luong san pham nhapp da chon, phai check ton kho tung buoc
					{
						if(trakm.getHinhthuc() == 2)
						{
							
							query = "select a.sanpham_fk as spId, c.MA as spMa, isnull(bgmnpp.dongia, '0') as dongia, isnull(b.TONGLUONG, 0) as tongluong " +
											"from TRAKM_NHAPP a inner join TRAKHUYENMAI b on a.trakm_fk = b.PK_SEQ " +
											" inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
											" left join (  select sanpham_fk, GIAMUANPP as dongia  " +
											"				from BGMUANPP_SANPHAM   " +
											"				where BGMUANPP_FK in (  select top(1) a.PK_SEQ " +
											"										from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK   " +
											"										where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select kbh_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) and a.DVKD_FK = ( select dvkd_fk from ERP_DONDATHANG where pk_seq='" + id + "' ) " +
											"										order by a.TUNGAY desc  )   " +
														") bgmnpp on bgmnpp.sanpham_fk=a.sanpham_fk" + 
												   " where a.ctkm_fk = '" + ctkm.getId() + "' and a.npp_fk = '" + nppId + "' and a.trakm_fk = '" + trakm.getId() + "' " +
											"order by a.thutuuutien asc";
							
							System.out.println("5.Query tinh gia km npp chon truoc: " + query);
							
							ResultSet spkm = db.get(query);
							
							String sp = "";
							String ma = "";
							String dg = "";
							String tg = "";
							while(spkm.next())
							{
								sp += spkm.getString("spId") + ",";
								dg += spkm.getString("dongia") + ",";
								tg += spkm.getString("tongluong") + ",";
								ma += spkm.getString("spMa") + ",";
							}
						
							String[] spId = sp.split(",");
							String[] dongia = dg.split(",");
							String[] tongluong = tg.split(",");
							String[] spMa = ma.split(",");
							
							//CheckTonKho nhung tra khuyen mai da duoc npp chon truoc
							String[] arr = checkTonKhoTraKm(nppId, ctkm, spId, dongia, tongluong, spMa);
							if(arr == null)  //nhung san pham da chon truoc cua tra khuyen mai da het hang trong kho
							{
								db.getConnection().rollback();
								str = "Số lượng những sản phẩm bạn chọn trước trong thiết lập sản phẩm trả khuyến mãi không đủ trong kho";
								System.out.println("Error: " + str + "\n");
								return str;
							}
							else
							{
								/*********************************************************************************/
								if(ctkm.getPhanbotheoDH().equals("1"))
								{
									String msg = "";
									if(msg.trim().length() > 0)
									{
										db.getConnection().rollback();
										return msg;
									}
								}
								/*********************************************************************************/
								
								//luu tong gia tri o moi dong sanpham
								query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong, inchung, kho_fk) select '" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + arr[2].replaceAll(",", "") + "', '" + arr[3] + "', '" + arr[1].replaceAll(",", "") + "' from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "'";
								System.out.println("6.Chen khuyen mai Npp chon truoc: " + query);
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								//KIỂM TRA VÀ ĐỀ XUẤT SỐ LÔ MẶC ĐỊNH
								String error = this.KiemTraVSDeXuatLo( db, id, ngaygiaodich, kho_fk, ctkm.getId(), arr[3], Double.parseDouble(arr[1].replaceAll(",", "")) );
								if( error.trim().length() > 0 )
								{
									db.getConnection().rollback(); 
    								return error;
								}
								
								/*query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + arr[2].replaceAll(",", "") + "' where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
								System.out.println("9.Cap nhat ngan sach Phanbokhuyenmai: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}*/
							}
						}
					}
				}
				else
				{
					if(trakm.getType() != 3)//tra tien, tra chiet khau
					{
						String query = "Insert into ERP_DONDATHANG_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + tongtienTraKM + "')";
						System.out.println("10.Chen khuyen mai tien / ck: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}
					
						/*query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtienTraKM) + "' where ctkm_fk ='" + ctkm.getId() + "' and npp_fk='" + nppId + "'";
						System.out.println("12.Cap nhat ngan sach Phanbokhuyenmai: " + query);
						if(!db.update(query))
						{
							db.getConnection().rollback();
							str = query;
							return str;
						}*/
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e1)
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e) {}
			return "Loi khi tao moi ctkm " + ctkm + ", " + e1.toString(); 
		}
		finally
		{
			db.shutDown();
		}
		
		return str;
	}
	
	private String KiemTraVSDeXuatLo(Idbutils db, String dhId, String ngaygiaodich, String kho_fk, String scheme, String masanpham, double soluong) 
	{
		String msg = "";
		geso.traphaco.center.util.Utility util = new geso.traphaco.center.util.Utility();
		List<ErpThongtinkho> kq = new ArrayList<ErpThongtinkho>();
		try
		{
			String query = " 	select ct.KHOTT_FK, ct.sanpham_fk, ct.AVAILABLE, NGAYHETHAN, ngaynhapkho, SOLO, isnull(MAME, '') as MAME, isnull(MATHUNG, '') as MATHUNG, isnull(MAPHIEU, '') as MAPHIEU, isnull(MARQ, '') as MARQ, isnull(HAMLUONG, 100) as HAMLUONG, isnull(HAMAM, 0) as HAMAM, isnull( bin_fk, 0 ) as vitri, isnull( maphieudinhtinh, '' ) as phieudt, isnull( phieueo, '' ) as phieueo  "+
							"\n 	from ERP_KHOTT_SP_CHITIET ct left join ERP_BIN bin on ct.bin_fk = bin.pk_seq  "+
							"\n 	where ct.KHOTT_FK = (select kho_fk from ctkhuyenmai where pk_seq = " + scheme + ") and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + masanpham + "' )   " +
							"\n 			and ngaynhapkho <= '" + ngaygiaodich + "' and AVAILABLE > 0 " + 
							"\n order by ngaynhapkho asc  ";
			
			System.out.println("::: DE XUAT LO: " + query);
			ResultSet rs = db.get(query);
			
			double total = 0;
			double ttotalXUAT = 0;
			while(rs.next())
			{
				//Element element = (Element) rs.item(i);
				
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				//double avai = WebService.getDouble(element, "AVAILABLE");
				total += avai;
				
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- AVAI: " + avai );
				if(total < soluong)
				{
					slg = avai;
				}
				else
				{
					slg = soluong - ( total - avai );
				}
					
				//System.out.println("---- TOTAL: " + total + " -- TONG LUONG: " + tongluong + " -- SOLUONG: " + slg );
				if(slg > 0)
				{
					ttotalXUAT += slg;
					
					//query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT union ALL ";
					ErpThongtinkho ttKho = new ErpThongtinkho();
					
					ttKho.khoId = rs.getString("KHOTT_FK");;
					ttKho.spId = rs.getString("sanpham_fk");
					ttKho.solo = rs.getString("solo");
					ttKho.ngayhethan = rs.getString("ngayhethan");
					ttKho.ngaynhapkho = rs.getString("ngaynhapkho");
					
					ttKho.MARQ = rs.getString("MARQ");
					ttKho.hamluong = rs.getString("hamluong");
					ttKho.hamam = rs.getString("hamam");
					
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.phieudt = rs.getString("phieudt");
					ttKho.phieueo = rs.getString("phieueo");
					
					ttKho.vitriId = rs.getString("vitri");
					ttKho.mame = rs.getString("mame");
					ttKho.mathung = rs.getString("mathung");
					ttKho.maphieu = rs.getString("MAPHIEU");
					ttKho.soluong = slg;						
					
					kq.add(ttKho);
				}
				else
				{
					break;
				}
			}
			rs.close();
			
			if( ttotalXUAT != soluong )
			{
				return "Lỗi đề xuất mã hàng ( " + masanpham + " ) số chi tiết " + ttotalXUAT + " đang khác số tổng " + soluong;
			}
			
			for( int j = 0; j < kq.size(); j++  )
			{
				ErpThongtinkho tt = kq.get(j);
				
				/*query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, mame, mathung, soluong ) " +
						"select '" + this.id + "', pk_seq, N'" + _sp[1] + "', N'" + _sp[2] + "', '" + _sp[3] + "', '" + _sp[4] + "', '" + _sp[5] + "', '" + _sp[6] + "', '" + _soluongCT.replaceAll(",", "") + "' " +
						"from SANPHAM where MA = '" + spMa[i] + "' ";*/
				
				query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, soluong, soluongCHUAN,IS_KHONGTHUE,TIENHANG,TIENVAT,TIENHANGSAUVAT, inchung, kho_fk ) " +
						"select '" + dhId + "', '" + tt.spId + "', scheme, N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', '" + tt.phieudt + "', '" + tt.phieueo + "', " + tt.soluong + ", '" + tt.soluong + "','0' "+
						", 0 AS TIENHANG, 0 AS TIENVAT, 0 AS TIENHANGSAUVAT, inchung, kho_fk from ctkhuyenmai where pk_seq = '" + scheme + "' ";
				
				System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
				if(!db.update(query))
				{
					return "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
				}
				
				//CẬP NHẬT KHO
				//this.msg = util.Update_KhoTT(this.ngaydenghi, "HO / Bán đối tác", db, this.khoNhanId, "( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' )", _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
				msg = util.Update_KhoTT(ngaygiaodich, "Áp khuyến mãi", db, tt.khoId, tt.spId, tt.solo, tt.ngayhethan, tt.ngaynhapkho, 
								tt.mame, tt.mathung, tt.vitriId, tt.maphieu, tt.phieudt, tt.phieueo, tt.MARQ, tt.hamluong, tt.hamam, "", "", 0, tt.soluong, -1 * tt.soluong, 
								"", "", "");
				
				if( msg.trim().length() > 0 )
				{
					return msg;
				}
				String querycheck = "SELECT ISNULL(BOOKED,0) as BOOKED FROM ERP_KHOTT_SP_CHITIET WHERE KHOTT_FK = '"+ tt.khoId+"' and SANPHAM_FK ='"+ tt.spId+"' and isnull(SOLO,'') = '"+ tt.solo+"' \n"+
				"   and NGAYNHAPKHO = '"+ tt.ngaynhapkho+"' and NGAYHETHAN = '"+ tt.ngayhethan+"' and isnull(MAME,'') = '"+ tt.mame+"'\n"+
				"   and isnull(MAPHIEU,'') = '"+ tt.maphieu+"' and isnull(MATHUNG,'') = '"+ tt.mathung+"' and isnull(MAPHIEUDINHTINH,'') = '"+ tt.phieudt+"' \n"+
				"   and isnull(PHIEUEO,'') = '"+ tt.phieueo+"' and isnull(HAMAM,0) ='"+ tt.hamam+"' and isnull(HAMLUONG,100)  ='"+ tt.hamluong+"' and isnull(BIN_FK,0) = '"+ tt.vitriId+"' \n"+
				" \n";
				ResultSet rscheck = db.get(querycheck);
				if(rscheck.next())
				{
					if(rscheck.getDouble("BOOKED") <tt.soluong)
					{
						return "Lỗi trong quá trình booked kho. Vui lòng kiểm tra lại hoặc liên hệ admin để xửa lý";
					}
				}
				rscheck.close();
			}
			
		}
		catch(Exception ex )
		{
			ex.printStackTrace();
			msg = ex.getMessage();
		}
		
		return msg;
	}
	
	private String[] checkTonKhoTraKm(String nppId, ICtkhuyenmai ctkm, String[] spId, String[] dongia, String[] tongluong, String[] spma) 
	{
		String[] kq = new String[4];
		
		String msg = "";
		try
		{
			for(int i = 0; i < spId.length; i++)
			{
				float slg = Integer.parseInt(tongluong[i]) * ctkm.getSoxuatKM();
				msg = "";
				if(msg == "")  //thoa ton kho
				{
					kq[0] = spId[i];
					kq[1] = Float.toString(slg);
					kq[2] = Long.toString(Math.round(slg * Float.parseFloat(dongia[i])));
					//System.out.println("Don gia: " + spId[i] + "- dongia: " + dongia[i] + " - Tong gia tri o buoc nay: " + kq[2] + "\n");
					kq[3] = spma[i];
				
					return kq;
				}
			}
		}
		catch (Exception e) {
			return null;
		}
		return null;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	

	private void importExcelFILE(String fileName) 
	{
		File inputWorkbook = new File(fileName);
		File output_Workbook = new File("D:\\importExcelFILE.xls");
		Workbook w;
		dbutils db = new dbutils();
		try
		{
			w = Workbook.getWorkbook(inputWorkbook);
			Sheet sheet = w.getSheet(0);
			
			WritableSheet w_sheet =null;
			WritableWorkbook workbook = jxl.Workbook.createWorkbook(output_Workbook);
			w_sheet = workbook.createSheet("importExcelFILE",1);
			
			int sodong = sheet.getRows();
			int socot = sheet.getColumns();
			
			System.out.println("So dong " + sodong + "  -- socot " + socot );
			
			
			String query = "";
			
			//sodong = 18;
			
			String donhangId="";
			String kbhId="";
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);
			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			
			WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
			cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
			cformat1.setAlignment(Alignment.RIGHT);
			cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			
			
			w_sheet.addCell(new Label(0, 8, "Chứng từ\\Ngày",cellFormat));
			w_sheet.addCell(new Label(1, 8, "Số",cellFormat));
			w_sheet.addCell(new Label(2, 8, "Diễn giải",cellFormat));
			w_sheet.addCell(new Label(3, 8, "Mã BP\\ĐVT",cellFormat));
			w_sheet.addCell(new Label(4, 8, "Mã kho\\Mã NX",cellFormat));
			w_sheet.addCell(new Label(5, 8, "Số lượng",cellFormat));
			w_sheet.addCell(new Label(6, 8, "Giá bán",cellFormat));
			w_sheet.addCell(new Label(7, 8, "Doanh thu",cellFormat));
			w_sheet.addCell(new Label(8, 8, "Chưa có mã khách hàng",cellFormat));
			w_sheet.addCell(new Label(9, 8, "Chưa phân tuyến",cellFormat));
			w_sheet.addCell(new Label(10, 8, "Sản phẩm chưa có trong hệ thống",cellFormat));
			w_sheet.addCell(new Label(11, 8, "Có Lỗi IMPORT",cellFormat));
			w_sheet.addCell(new Label(12, 8, "Số đơn hàng",cellFormat));
			w_sheet.addCell(new Label(13, 8, "Kênh bán hàng",cellFormat));
		

			String userId="100230";
			String nppId ="";
			String khoId="100000";
			query=" Select pk_Seq from nhaphanphoi b  WHERE TimKiem like N'%TRUNG%' AND PK_SEQ=106231  ";
			ResultSet rs =db.get(query);
			while(rs.next())
			{
				nppId=rs.getString("pk_Seq");
			}
			
			for (int i=9; i < sodong ; i++)
			{
				String ngayHD="";
				Cell valueCell = sheet.getCell(0, i);  
				if (valueCell.getType() == CellType.DATE) 
				{  
					DateCell dCell = (DateCell) valueCell;  
			        TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
			        DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
			        destFormat.setTimeZone(gmtZone);  
			        ngayHD = destFormat.format(dCell.getDate());  
				}   
				String SoHD = sheet.getCell(1, i).getContents();
				String maKH_SP = sheet.getCell(2, i).getContents();
				String donvi = sheet.getCell(3, i).getContents();
				String soluong = sheet.getCell(5, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","");
				
				String MaKho = sheet.getCell(4, i).getContents().trim().replaceAll(",", "").replaceAll("\\.", "").replaceAll(" ", "").replaceAll("\\-","");
				
				String giaban = sheet.getCell(6, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","");
				String doanhthu = sheet.getCell(7, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","");
				
				
				w_sheet.addCell(new Label(0, i, ngayHD,cellFormat2));
				w_sheet.addCell(new Label(1, i, SoHD,cellFormat2));
				w_sheet.addCell(new Label(2, i, maKH_SP,cellFormat2));
				w_sheet.addCell(new Label(3, i, donvi,cellFormat2));
				w_sheet.addCell(new Label(5, i, soluong,cellFormat2));
				w_sheet.addCell(new Label(6, i, giaban,cellFormat2));
				w_sheet.addCell(new Label(7, i, doanhthu,cellFormat2));
				
				
				String maKH = "";
				String maSP="";
				Utility util = new Utility();
				boolean exitMAKH = false;	
					
				if(SoHD.trim().length() >  0 && maKH_SP.indexOf("(") >= 0 )  //Mã khách hàng
				{
					donhangId="";
					kbhId="";
					maKH =maKH_SP.substring(maKH_SP.lastIndexOf("(")+1,maKH_SP.lastIndexOf(")") );					
					//INSERT DON HANG  --> CHECK KHACH HANG NAY DA CO TRONG HE THONG CHUA
					query = "select count(pk_Seq) as soDONG,kbh_fk,pk_Seq from KHACHHANG where npp_FK='"+nppId+"' and maFAST = '" + maKH + "'group by pk_Seq,kbh_fk ";
					ResultSet rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						if(rsCHECK.getInt("soDONG") > 0)
						{
							exitMAKH = true;
							kbhId=rsCHECK.getString("kbh_fk");
						}
					}
					rsCHECK.close();
					
					if(!exitMAKH)
					{
						System.out.println("[KhachHangChuaTonTai]"+maKH);
						w_sheet.addCell(new Label(8, i, maKH,cellFormat2));
					}
					
					exitMAKH = false;
					query = "select COUNT(*) as SoDong from KHACHHANG_TUYENBH where KHACHHANG_FK in (select pk_Seq from KHACHHANG where maFAST='"+maKH+"'  and  npp_FK='"+nppId+"' ) ";
					rsCHECK = db.get(query);
					if(rsCHECK.next())
					{
						if(rsCHECK.getInt("soDONG") > 0)
						{
							exitMAKH = true;
						}
					}
					rsCHECK.close();
					
					if(!exitMAKH)
					{
						w_sheet.addCell(new Label(9, i, maKH,cellFormat2));
						System.out.println("[KHACHHANG_TUYENBH]"+maKH);
					}
					
					if(exitMAKH)
					{
						w_sheet.addCell(new Label(13, i,kbhId,cellFormat2));
						//KENH OTC 
						if(kbhId.equals("100025"))
						{
								donhangId=	DonHangOTC(SoHD, maKH, ngayHD, db,userId,nppId,khoId);
								System.out.println("donhangId___"+donhangId);
								if(donhangId.length()>0)
								{
									query="delete from DonHang_SanPham where DonHang_fk='"+donhangId+"' ";
									
									if(!db.update(query))
									{
										System.out.println("DonHang_SanPham"+query);
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
									}
									query="delete from DonHang_CTKM_TRAKM where DonHangId='"+donhangId+"' ";
									if(!db.update(query))
									{
										System.out.println("DonHang_CTKM_TRAKM"+query);
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
									}
									
									query="delete from DUYETTRAKHUYENMAI_DONHANG where donhang_fk='"+donhangId+"' ";
									if(!db.update(query))
									{
										query="delete from DUYETTRAKHUYENMAI_DONHANG where DonHangId='"+donhangId+"' ";
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
									}
								}
						}else 
						{
							donhangId=DonHangETC(SoHD,maKH,ngayHD,db,userId,nppId,khoId);
							query = "delete ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + donhangId+ "'";
							if(!db.update(query))
							{
								System.out.println("ERP_DONDATHANGNPP_SANPHAM"+query);
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
							}
						}
								w_sheet.addCell(new Label(12, i,donhangId,cellFormat2));
					}
					
					
				}
				
				/*****/				
				if( maKH_SP.indexOf("-") > 0 && donvi.trim().length() > 0) 
				{
						maSP = maKH_SP.substring(0,maKH_SP.indexOf("-")).trim();
						w_sheet.addCell(new Label(12, i,donhangId));
						if(donhangId.length()>0 && ! ( util.replaceAEIOU( maKH_SP.toUpperCase()).contains("CHIET-KHAU")  || util.replaceAEIOU( maKH_SP.toUpperCase()).contains("GIAM-TRU"))  )
						{
							int SoDong=0;
							query = "select COUNT(*) as SoDong from SanPham where MA='"+maSP+"' ";
							ResultSet	rsCHECK = db.get(query);
							if(rsCHECK.next())
							{
								SoDong=rsCHECK.getInt("SoDong");
							}
							if(rsCHECK!=null)rsCHECK.close();
							
							if(SoDong==0)
							{
								w_sheet.addCell(new Label(10, i,maSP,cellFormat2));
							}
							
							//********************************************************HANG BAN ****************************************************//*
							
						if(kbhId.equals("100025"))
							{
								//KHI CHAY HOA DON HANG KM THI BO DONG HANG BAN
								
								query="select count(*) as SoDong from DONHANG_SANPHAM where donhang_fk='"+donhangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') ";
								rs= db.get(query);
								
								while(rs.next())
								{
									SoDong=rs.getInt("SoDong");
								}
								if(rs!=null)rs.close();
								if(SoDong>0)
								{
									query= "update DONHANG_SANPHAM set soluong=soluong+'"+soluong+"' where donhang_fk='"+donhangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') ";
									SoDong =db.updateReturnInt(query);
									if(SoDong<=0)
									{
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
										System.out.println("DONHANG_SANPHAM"+query);
									}
								}
								else 
								{
									query=
									"INSERT INTO DONHANG_SANPHAM(DONHANG_FK,SANPHAM_FK,SOLUONG,GIAMUA,KHO_FK,CHIETKHAU,THUEVAT,THANHTIEN,TIENVAT,DonGiaGoc) "+
									"select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+"/(1+nh.ThueXuat/100.0) as giamua,100000 as kho  "+
									"	,0 as ck,nh.ThueXuat as thueVAT,'"+doanhthu+"' AS thanhtien,0 as tienvat,"+giaban+"/(1+nh.ThueXuat/100.0)  "+
									"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk where MA='"+maSP+"' ";
									SoDong =db.updateReturnInt(query);
							
									if(SoDong<=0)
									{
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
										System.out.println("DONHANG_SANPHAM"+query);
									}
								}
							
							}
							else 
							{
								
								
								query="select count(*) as SoDong from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk='"+donhangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') ";
								rs= db.get(query);
								
								while(rs.next())
								{
									SoDong=rs.getInt("SoDong");
								}
								if(rs!=null)rs.close();
								
								if(SoDong>0)
								{
									query= "update ERP_DONDATHANGNPP_SANPHAM set soluong=soluong+'"+soluong+"' where dondathang_fk='"+donhangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') ";
									SoDong =db.updateReturnInt(query);
									if(SoDong<=0)
									{
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
										System.out.println("DONHANG_SANPHAM"+query);
									}
								}else 
								{
									query=
											 "insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) " +
												"select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+"/(1+nh.ThueXuat/100.0) as giamua  "+
												"	,0 as ck,nh.ThueXuat as thueVAT,sp.dvdl_fk,'','' "+
												"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk where MA='"+maSP+"' ";
											SoDong	=db.updateReturnInt(query);
											if(SoDong<=0)
											{
												w_sheet.addCell(new Label(11, i,query,cellFormat2));
												System.out.println("ERP_DONDATHANGNPP_SANPHAM"+query);
											}
								}
								System.out.println("ERP_DONDATHANGNPP_SANPHAM");
							}
							
							//********************************************************HANG BAN ****************************************************//*
							
								//KHI CHAY HOA DON HANG BAN THINBO DONG KM, VA NGUOC LAI
						/*		query=
							"	insert into DONHANG_CTKM_TRAKM(DONHANGID,CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,LOAI,KHONVBH)  "+ 
							"	select '"+donhangId+"' as donhangid,   "+
							"	( select top(1) ctkhuyenmai_fk from ctkm_trakm where trakhuyenmai_fk =   "+
							"	(select top(1) trakhuyenmai_fk from trakhuyenmai_sanpham where sp.pk_seq=sanpham_fk)  ) as ctkmid,   "+
							"	(select top(1) trakhuyenmai_fk from trakhuyenmai_sanpham where sp.pk_seq=sanpham_fk) as trakmid   "+
							"	,0 as soxuat ,"+doanhthu+"/(1+nh.thuexuat/100) as tonggiatri ,'"+maSP+"'  as spma ,'"+soluong+"' as soluong ,0,0   "+  
							"	from sanpham sp inner join  nganhhang nh on nh.pk_seq=sp.nganhhang_fk where ma='"+maSP+"' "; 										   
										
								SoDong	=db.updateReturnInt(query);
								if(SoDong<=0)
								{
									w_sheet.addCell(new Label(11, i,query,cellFormat2));
									System.out.println("DONHANG_CTKM_TRAKM"+query);
								}*/

							//********************************************************KM****************************************************//*
							
						}
				}
				System.out.println(":::" +util.replaceAEIOU( maKH_SP.toUpperCase()));
		
			
				if( donhangId.length()>0 && ( util.replaceAEIOU( maKH_SP.toUpperCase()).contains("CHIET-KHAU")  || util.replaceAEIOU( maKH_SP.toUpperCase()).contains("GIAM-TRU")))
				{
					String ckNgay="CN5,CN10";
					if(ckNgay.indexOf(maSP)>=0 && maSP.trim().length()>0)
					{
						String ptThue=maSP.substring(2,maSP.length());
						
						if(kbhId.equals("100025"))
						{
							query= 
							"	update b set chietkhau=( "+doanhthu+" /(1.0 + (SELECT cast(VAT as float) FROM LOAICHIETKHAU WHERE maloai='"+maSP+"')/100 ) ) "+
							"				*( (soluong*giamua)/( select sum(SOLUONG*GIAMUA) from DONHANG_SANPHAM where DONHANG_FK=b.donhang_fk and THUEVAT= '"+ptThue+"'  )  )  "+
							"	from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK =a.PK_SEQ "+
							"	where b.donhang_fk= '"+donhangId+"' and thuevat= '"+ptThue+"'  ";
						}else  if(kbhId.equals("100052"))
						{
							query= 
							"		update b set chietkhau=( "+doanhthu+" /(1.0 + (SELECT cast(VAT as float) FROM LOAICHIETKHAU WHERE maloai='"+maSP+"')/100 ) )  "+ 
							"			*( (soluong*DONGIA)/( select sum(SOLUONG*dongia) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk=b.dondathang_fk and THUEVAT='"+ptThue+"'  ))  "+   
							"		from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on b.dondathang_fk =a.PK_SEQ   "+
							"		where b.dondathang_fk = '"+donhangId+"'  and thuevat= '"+ptThue+"'    ";
						}
						
						System.out.println("CHIET_KHAU");
						int	SoDong	=db.updateReturnInt(query);
						if(SoDong<=0)
						{
							w_sheet.addCell(new Label(11, i,query,cellFormat2));
							System.out.println("CK_ERROR"+query);
						}
					}
					else if(kbhId.equals("100025"))
					{
							String duyetId="100008";
							if(maSP.indexOf("CQ")>=0)
							{
								duyetId="100011";
							}
							String tichluyQUY="0";
							if(maSP.indexOf("CQ")>=0)
							{
								tichluyQUY="1";
							}
							query=
							"	insert into DUYETTRAKHUYENMAI_DONHANG(duyetkm_fk,khachhang_fk,donhang_fk,thanhtoan,tichluyQUY,DIENGIAI,ptthue,HienThi )  "+
							"	select '"+duyetId+"' as duyetId,KHACHHANG_FK,PK_SEQ as DonHangId,'"+doanhthu+"' as ThanhToan,'"+tichluyQUY+"' as Quy,'"+maSP+"' as DienGiai,  "+
							"		(SELECT VAT FROM LOAICHIETKHAU WHERE maloai='"+maSP+"') as ptThue ,1 as HienThi " +
							"from DONHANG where pk_seq='"+donhangId+"' and '"+maSP+"' in ( select maloai from LOAICHIETKHAU WHERE maloai not in ('CN5','CN10')  )  ";
							
							System.out.println("DUYETTRAKHUYENMAI_DONHANG");
							if(!db.update(query))
							{
								System.out.println("ERROR_DUYETTRAKHUYENMAI_DONHANG_"+query);
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
							}
					}
				}
				
				
				
			}
			
			
			query=
			 "update DH set DH.TONGGIATRI = case TGT.thanhtoan when 1 then TGT.tongTIEN - TGT.tongTL else TGT.tongTIEN end  " +
				"from DONHANG DH  " +
				"inner join " +
				"( " +
				"	select d.thanhtoan, a.pk_seq as donhangID,  " +
				"		( (          " +
				"					select cast( sum( (soluong * giamua -  ( soluong * giamua * cast( chietkhau * 100 / ( soluong * giamua * 1.0 ) as numeric(18, 0) ) / 100 ) ) * ( 1 + thueVAT / 100 ) ) as numeric(18, 0) )  as tienBvat          " +
				"					from donhang_sanpham           " +
				"					where donhang_fk = a.pk_seq  and ( soluong * giamua ) != 0 )  " +
				"		- isnull( ( select sum(tonggiatri) from donhang_ctkm_trakm where donhangId = a.pk_seq and SPMA is null ), 0 )     " +
				"		- isnull( ( select sum(thanhtien)  from DONHANG_CHIETKHAUBOSUNG where donhang_fk = a.pk_seq )	, 0 )  ) as tongTIEN, " +
				"		isnull( ( select sum(thanhtoan)  from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = a.pk_seq )	, 0 ) 	as tongTL					         " +
				"	from donhang a inner join khachhang d on a.khachhang_fk = d.pk_seq         " +
				"	where a.import = '1' and a.trangthai != 2  " +
				") " +
				"TGT on DH.pk_seq = TGT.donhangID where DH.npp_fk = '" + nppId + "' ";
			
			if(!db.update(query))
			{
				w_sheet.addCell(new Label(11, sodong,query,cellFormat2));
			}
			
			
			workbook.write();
			workbook.close();
			if(db!=null)db.shutDown();
			
			System.out.println("**************************CHAY XONG ROI********************************");
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(db!=null)db.shutDown();
		}
	}
	
	public static void main(String[] arg)
	{
		ErpDondathangUpdateSvl ddh = new ErpDondathangUpdateSvl();
	
		/*ddh.importExcelFILE("D:\\Traphaco\\DaNang\\Bang ke hoa don tu ngay 1-05.11.xls");*/ 		
	/*	ddh.importExcelFILE("D:\\Traphaco\\HaiPhong\\HaiPhong_BoSung_.xls");*/
			/*ddh.UpLoad_HoaDon("D:\\Traphaco\\HCM\\Kho_59\\BANG KE TONG 131 DEN 151014 (dang co ETC)__2003.xls");*/
			/*ddh.UpLoad_HoaDon("D:\\Traphaco\\KhanhHoa\\KhanhHoa_dddd.xls");*/
		
		ddh.UpLoad_HoaDon("D:\\Traphaco\\QuangNgai\\HDON QUANG NGAI.BS_2003.xls");
		
			/*ddh.UpLoad_HoaDon("D:\\Traphaco\\CanTho\\HD HANG BAN CAN THO THANG 10_2003.xls");*/
		
//		ddh.importExcelFILE_HCM("D:\\Traphaco\\DongNai\\HD Hang ban 01.30.09_2003.xls");
		
			/*ddh.UpLoad_HoaDon("D:\\Traphaco\\DongNai\\HD 18.10 DONG NAI_2003.xls");*/
		
		/*ddh.UpLoad_HoaDon("D:\\Traphaco\\DanNang\\HD 18.10 DONG NAI_2003.xls");*/
			
		/*ddh.UpLoad_HoaDon("D:\\Traphaco\\DaNang\\Hoa don thieu Da Nang_2003.xls");*/
			
	/*	ddh.importExcelFILE_HCM("D:\\Traphaco\\DongNai\\HD HANG BAN T78_2003.xls");*/
/*		ddh.importExcelFILE_HCM("D:\\Traphaco\\DaNang\\HDON THANG 10_2003.xls");*/
	/*	ddh.importExcelFILE_HCM("D:\\Traphaco\\KhanhHoa\\HD 01.10 to 17.11 KHOA_2003.xls");*/
		
	}
	
		public String DonHangOTC(String SoHD,String maKH,String ngayHD,dbutils db,String userId,String nppId, String khoId) throws SQLException 
		{
			String donhangId="";
			db.getConnection().setAutoCommit(false);
			try 
			{
				
				String 
				query=
				"\n	insert into DONHANG(NGAYNHAP,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,VAT,TONGGIATRI,DATHANHTOAN,DDKD_FK,GSBH_FK,KHACHHANG_FK,NPP_FK,KHO_FK,TINHTRANG,CHIETKHAU,IsDonHangLe,IsSampling,ptTichLuyTheoMuc,huongTLNgay,DADUYET,Import,HopDong,KBH_FK,DAXUATHOADON,LOAIKHACHHANG,GhiChu)  "+
				"\n	select top(1)   "+
				"\n		'"+ngayHD+"',1,'"+getDateTime()+"' as NgayTao,'"+getDateTime()+"' as NgaySua,'"+userId+"' as NguoiTao,'"+userId+"' as NguoiSua,  "+
				"\n	0 as vat,0 as TongGiaTri,0 as DaThanhToan, "+
				"\n	( "+
				"\n		select top(1) a.PK_SEQ from DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ "+ 
				"\n			inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ " +
				"       inner join khachhang d on d.pk_Seq=c.khachhang_Fk                                                       "+
				"\n			where d.mafast='"+maKH+"' and d.npp_fk='"+nppId+"' "+
				"\n	 ) as ddkdId,   "+
				"\n	 ( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK='"+nppId+"' order by NGAYBATDAU desc ) "+
				"\n	 as gsbhId,(select pk_Seq from khachhang where mafast='"+maKH+"' and npp_fk='"+nppId+"' ) as khId,'"+nppId+"',"+khoId+",0 as tinhtrang,0 as CK,0 Le,0 as Sam,0 as ptTl,0 as hngay,0 as Daduyet,  1  ,'"+SoHD+"',100025,1 as DaXuatHD,(select xuatkhau from KhachHang where mafast='"+maKH+"' and npp_fk='"+nppId+"')    ,N'Import From Excel "+SoHD+" - "+maKH+"  - "+ngayHD+" ' "+
				"\n	where not exists (select HopDong from DonHang a  where HopDong is not null  and NGAYNHAP='"+ngayHD+"' and  a.npp_Fk='"+nppId+"' and hopdong='"+SoHD+"' ) ";
		
				int SoDong =db.updateReturnInt(query);
				if(SoDong==1)
				{
					query = "select scope_identity()  as dhId ";
				} 
				else if(SoDong==0) 
					query = "select a.pk_seq as dhId from DonHang a where HopDong='"+SoHD+"' and ngaynhap='"+ngayHD+"' and a.npp_Fk='"+nppId+"'  ";
				
				System.out.println("DonHangOTC____"+query);
				
				if(SoDong<=1)
				{
					ResultSet rs = db.get(query);
					try
		            {
			            rs.next();
			            donhangId = rs.getString("dhId");
			            if(rs!=null)rs.close();
		            } 
					catch (SQLException e)
		            {
			            e.printStackTrace();
			          
			            db.getConnection().rollback();
			            System.out.println("SQLException");
		            }
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (Exception e1) 
			{
				e1.printStackTrace();
				db.getConnection().rollback();
				System.out.println("Exception");
				return "";
			}
			finally
			{
				System.out.println("ShutDown");
			}
			return donhangId;
		}
		
		
		public String HoaDonOTC(String SoHD,String maKH,String ngayHD,dbutils db,String userId,String nppId,String khoId)
		{
			String donhangId="";
			try
      {
	      db.getConnection().setAutoCommit(false);
				String 
				query=
						"	insert into HoaDon(NgayXuatHD,TRANGTHAI,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,VAT,DDKD_FK,GSBH_FK,KHACHHANG_FK,NPP_FK,KHO_FK,HopDong,KBH_FK,GhiChu,SoHoaDon,KyHieu,HINHTHUCTT,LoaiHoaDon)    \n"+
						"	select top(1)   \n"+
						"		'"+ngayHD+"',4,'"+getDateTime()+"' as NgayTao,'"+getDateTime()+"' as NgaySua,'"+userId+"' as NguoiTao,'"+userId+"' as NguoiSua,0 as vat,  \n"+
						"	(  \n"+
						"		select top(1) a.PK_SEQ from DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+ 
						"			inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ  \n"+
						"       inner join khachhang d on d.pk_Seq=c.khachhang_Fk                                                       "+
						"\n			where d.mafast='"+maKH+"' and d.npp_fk='"+nppId+"' "+
						"	 ) as ddkdId,   \n"+
						"\n	 ( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK='"+nppId+"' order by NGAYBATDAU desc ) "+
						"	 as gsbhId,(select pk_Seq from khachhang where mafast='"+maKH+"' and npp_fk='"+nppId+"' )    as khId,'"+nppId+"',"+khoId+",'"+SoHD+"',100025  ,N'Import From Excel "+SoHD+" "+ngayHD+" "+maKH+"', " +		
      	  /*  " case    \n"+
			      "		WHEN LEN(dbo.Trim('"+SoHD+"')) < 1 THEN  '000000' +  dbo.Trim('"+SoHD+"')  \n"+   
			      "		WHEN LEN(dbo.Trim('"+SoHD+"')) < 2 THEN  '00000' +  dbo.Trim('"+SoHD+"')   \n"+
			      "		WHEN LEN(dbo.Trim('"+SoHD+"')) < 3 THEN  '0000' +  dbo.Trim('"+SoHD+"')    \n"+
			      "		WHEN LEN(dbo.Trim('"+SoHD+"')) < 4 THEN  '000' +  dbo.Trim('"+SoHD+"')   \n"+
			      "		WHEN LEN(dbo.Trim('"+SoHD+"')) < 5 THEN  '00' +  dbo.Trim('"+SoHD+"')   \n"+
			      "		WHEN LEN(dbo.Trim('"+SoHD+"')) < 6 THEN  '0' +  dbo.Trim('"+SoHD+"') else dbo.Trim('"+SoHD+"') end as SoHoaDon \n"+*/
					"	case    "+ 
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) ) < 1 THEN  '000000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') ))  \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 2 THEN  '00000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 3 THEN  '0000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 4 THEN  '000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 5 THEN  '00' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 6 THEN  '0' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) else SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) end as SoHoaDon \n"+
					"		,'TR/14P','TM/CK',0  "+ 
					"  \n"+
						"	where not exists  (select HopDong from HoaDon a  where HopDong is not null  and NGAYXUATHD='"+ngayHD+"' and a.npp_Fk='"+nppId+"' and hopdong='"+SoHD+"' ) ";
				
					System.out.println("__________"+query);
					
						int SoDong =db.updateReturnInt(query);
						if(SoDong==1)
						{
							query = "select scope_identity()  as dhId ";
						} 
						else if(SoDong==0) 
							query = "select a.pk_seq as dhId from HoaDon a   where HopDong='"+SoHD+"' and NGAYXUATHD='"+ngayHD+"'  and a.npp_Fk='"+nppId+"'  ";
						
						System.out.println("DONHANG"+query);
						
						if(SoDong<=1)
						{
							ResultSet rs = db.get(query);
							try
	            {
		            rs.next();
		            donhangId = rs.getString("dhId");
		            if(rs!=null)rs.close();
	            } catch (SQLException e)
	            {
		            e.printStackTrace();
		            db.getConnection().rollback();
	            }
						}
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
      } catch (Exception e1)
      {
	      e1.printStackTrace();
      }
			return donhangId;			
			
		}
		
		
		public String HoaDonETC(String SoHD,String maKH,String ngayHD,dbutils db,String userId,String nppId,String khoId)
		{
			/*		" case    \n"+
			"		WHEN LEN(dbo.Trim('"+SoHD+"')) < 1 THEN  '000000' +  dbo.Trim('"+SoHD+"')  \n"+   
			"		WHEN LEN(dbo.Trim('"+SoHD+"')) < 2 THEN  '00000' +  dbo.Trim('"+SoHD+"')   \n"+
			"		WHEN LEN(dbo.Trim('"+SoHD+"')) < 3 THEN  '0000' +  dbo.Trim('"+SoHD+"')    \n"+
			"		WHEN LEN(dbo.Trim('"+SoHD+"')) < 4 THEN  '000' +  dbo.Trim('"+SoHD+"')   \n"+
			"		WHEN LEN(dbo.Trim('"+SoHD+"')) < 5 THEN  '00' +  dbo.Trim('"+SoHD+"')   \n"+
			"		WHEN LEN(dbo.Trim('"+SoHD+"')) < 6 THEN  '0' +  dbo.Trim('"+SoHD+"') else dbo.Trim('"+SoHD+"') end as SoHoaDon "+*/
		
			String donhangId="";
			String 
			query=		
					"			insert ERP_HOADONNPP( ngayxuatHD, trangthai , ngaytao, nguoitao, ngaysua , nguoisua, kyhieu, sohoadon, hinhthuctt , "+
					"				chietkhau, tongtienbvat , tongtienavat, vat, ghichu , loaixuathd, npp_fk, khachhang_fk,HopDong" +
					"								,DONDATHANGNPP_FK,kho_fk,Npp_Dat_FK,NGUOIMUA,TENKHACHHANG,DIACHI,MASOTHUE)  "+
					"	select '"+ngayHD+"' ,4, '"+getDateTime()+"' as NgayTao,'"+userId+"','"+getDateTime()+"' ,'"+userId+"' ,  "+ 
					"			'TR/14P'  "+
					"	,case    "+ 
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) ) < 1 THEN  '000000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') ))  \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 2 THEN  '00000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 3 THEN  '0000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 4 THEN  '000' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 5 THEN  '00' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) \n"+
					"		WHEN LEN (SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"')))) < 6 THEN  '0' +  SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) else SUBSTRING(dbo.Trim('"+SoHD+"'),4,len(dbo.Trim('"+SoHD+"') )) end as SoHoaDon \n"+
					"			,'TM/CK' ,0 as ck,0 as bvat, 0 as avat ,0 as vat,N'Import From Excel "+SoHD+" "+ngayHD+" "+maKH+"' ,1, '"+nppId+"',(select pk_Seq from khachhang where  maFAST='"+maKH+"' and npp_fk='"+nppId+"') ,'"+SoHD+"',NULL,'"+khoId+"',(select pk_Seq from NhaPhanPhoi where  maFAST='"+maKH+"' and tructhuoc_fk='"+nppId+"')" +
					" 			,(select ISNULL(chucuahieu,'') as chucuahieu from khachhang where  maFAST='"+maKH+"' and npp_fk='"+nppId+"') "+
					" 			,(select ten from khachhang where  maFAST='"+maKH+"' and npp_fk='"+nppId+"') "+
					" 			,(select ISNULL(DIACHI,'') as diachi from khachhang where  maFAST='"+maKH+"' and npp_fk='"+nppId+"') "+
					" 			,(select ISNULL(MASOTHUE,'') as masothue from khachhang where  maFAST='"+maKH+"' and npp_fk='"+nppId+"') "+
					
					"	where not exists  (select HopDong from Erp_HoaDonNpp a where HopDong is not null and  a.npp_fk='"+nppId+"'  and ngayxuathd='"+ngayHD+"' and hopdong='"+SoHD+"'  )  ";


					int SoDong =db.updateReturnInt(query);
					if(SoDong==1)
					{
						query = "select scope_identity() as dhId ";
					} else if(SoDong==0) 
						query = "select a.pk_Seq as dhId from Erp_HoaDonNpp a  where HopDong='"+SoHD+"'  and a.npp_fk='"+nppId+"' and NgayXuatHD='"+ngayHD+"' ";
					
					System.out.println("HoaDonETC"+query);
					if(SoDong<=1)
					{
						ResultSet rs = db.get(query);
						try
            {
	            rs.next();
	            donhangId = rs.getString("dhId");
	            if(rs!=null)rs.close();
            } catch (SQLException e)
            {
	            e.printStackTrace();
            }
					}
					return donhangId;
		}
		
		public String DonHangETC(String SoHD,String maKH,String ngayHD,dbutils db,String userId,String nppId,String khoId) throws SQLException 
		{
			String donhangId="";
			try 
			{
				db.getConnection().setAutoCommit(false);
				String 
				query=
				"	insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, npp_dachot, ghichu, trangthai, dvkd_fk, kbh_fk, gsbh_fk,  "+ 
				"			ddkd_fk, khachhang_fk, npp_fk, kho_fk, chietkhau, vat, hopdong_fk, ngaytao, nguoitao, ngaysua, nguoisua,HopDong,Import)   "+
				"		select   "+
				"		 '"+ngayHD+"' as ngayDH, '"+ngayHD+"' as ngayDN, 0 as LoaiDH, 1 nppChot, N'Import From Excel "+SoHD+" "+ngayHD+"' "+maKH+" , 4,   "+ 
				"		 '100001' as dvkdId, 100052 ,    "+
				"		 ( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK='"+nppId+"' order by NGAYBATDAU desc )  as gsbhId,  "+
				"		 (    "+
				"			select top(1) a.PK_SEQ from DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ   "+  
				"				inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ   " +
				"       inner join khachhang d on d.pk_Seq=c.khachhang_fk                                                      "+
				"			where  d.mafast='"+maKH+"' and d.npp_fk='"+nppId+"'   "+
				"		) as ddkdId ,  "+
				"		  (select pk_Seq from khachhang where mafast='"+maKH+"' and npp_fk='"+nppId+"' ) as khId, '"+nppId+"' , '"+khoId+"' as khoId, 0 as ck,  "+
				"		   0 as VAT, '-1' hopdongId, '"+getDateTime()+"', '"+userId+"', '"+getDateTime()+"', '"+userId+"','"+SoHD+"',1  "+ 
				"	where 	 not exists (select HopDong from ERP_DondathangNPP a  where HopDong is not null and ngaydonhang='"+ngayHD+"' and hopdong='"+SoHD+"' and npp_fk='"+nppId+"' )  ";
						int SoDong =db.updateReturnInt(query);
						if(SoDong==1)
						{
							query = "select scope_identity() as dhId ";
						} else if(SoDong==0) 
							query = "select a.pk_Seq as dhId from ERP_DondathangNPP a  where HopDong='"+SoHD+"'  and a.npp_fk='"+nppId+"' and ngaydonhang='"+ngayHD+"' ";
						
						System.out.println("DonHangETC___"+query);
						
						if(SoDong<=1)
						{
							ResultSet rs = db.get(query);
							try
				            {
					            rs.next();
					            donhangId = rs.getString("dhId");
					            if(rs!=null)rs.close();
				            } catch (SQLException e)
				            {
				            	System.out.println("SQLException");
					            e.printStackTrace();
					            return "";
				            }
						}
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
			}catch (Exception e) 
			{
				db.getConnection().rollback();
				System.out.println("__Exception__");
				e.printStackTrace();
				return "";
			}
			finally
			{
				System.out.println("___SHUTDOWN__");
			}
			return donhangId;
		}
		
		public String DonHangETC_DoiTac(String SoHD,String maKH,String ngayHD,dbutils db,String userId,String nppId,String khoId) throws SQLException 
		{
			String donhangId="";
			try 
			{
				db.getConnection().setAutoCommit(false);
				String 
				query=
				"	insert ERP_DondathangNPP(ngaydonhang, ngaydenghi, loaidonhang, npp_dachot, ghichu, trangthai, dvkd_fk, kbh_fk, gsbh_fk,  \n"+ 
				"			ddkd_fk, khachhang_fk, npp_fk, kho_fk, chietkhau, vat, hopdong_fk, ngaytao, nguoitao, ngaysua, nguoisua,HopDong,Import,NPP_DAT_FK)   \n"+
				"		select   \n"+
				"		 '"+ngayHD+"' as ngayDH, '"+ngayHD+"' as ngayDN, 0 as LoaiDH, 1 nppChot, N'Import From Excel "+SoHD+"  "+ngayHD+" "+maKH+" ', 4,   \n"+
				"		 '100001' as dvkdId, 100052 ,    \n"+
				"		 ( NULL )  as gsbhId,  \n"+
				"		 (    \n"+
				"			NULL  \n"+
				"		) as ddkdId , \n"+
				"		  NULL   , '"+nppId+"'    , '"+khoId+"' as khoId, 0 as ck,  0 as VAT, '-1' hopdongId, '"+getDateTime()+"', '"+userId+"', '"+getDateTime()+"', '"+userId+"','"+SoHD+"',1 ,(select pk_seq from nhaphanphoi where mafast='"+maKH+"' and tructhuoc_fk='"+nppId+"' ) as NPP_DAT_FK  \n"+ 
				"	where not exists (select HopDong from ERP_DondathangNPP a where  HopDong is not null and ngaydonhang='"+ngayHD+"' and hopdong='"+SoHD+"' and npp_fk='"+nppId+"' and NPP_DAT_FK is not null )  ";
						int SoDong =db.updateReturnInt(query);
						if(SoDong==1)
						{
							query = "select scope_identity() as dhId ";
						} else if(SoDong==0) 
							query = "select a.pk_Seq as dhId from ERP_DondathangNPP a inner join Nhaphanphoi b on b.pk_Seq=a.NPP_DAT_FK where HopDong='"+SoHD+"' and b.mafast='"+maKH+"' and a.npp_fk='"+nppId+"' ";
						
						System.out.println("DonHangETC_DoiTac______"+query);
						
						if(SoDong<=1)
						{
							ResultSet rs = db.get(query);
							try
				            {
					            rs.next();
					            donhangId = rs.getString("dhId");
					            if(rs!=null)rs.close();
				            } catch (SQLException e)
				            {
				            	System.out.println("SQLException");
					            e.printStackTrace();
					            db.shutDown();
					            return "";
				            }
						}
						db.getConnection().commit();
						db.getConnection().setAutoCommit(true);
			}catch (Exception e) 
			{
				db.getConnection().rollback();
				System.out.println("__Exception__");
				return "";
			}
			finally
			{
				System.out.println("___SHUTDOWN__");
			}
			return donhangId;
		}
		
		
		
		private void importExcelFILE_HCM(String fileName) 
		{
			File inputWorkbook = new File(fileName);
			File output_Workbook = new File("D:\\importExcelFILE.xls");
			Workbook w;
			try
			{
				w = Workbook.getWorkbook(inputWorkbook);
				Sheet sheet = w.getSheet(0);
				
				WritableSheet w_sheet =null;
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(output_Workbook);
				w_sheet = workbook.createSheet("importExcelFILE",1);
				
				int sodong = sheet.getRows();
				int socot = sheet.getColumns();
				
				System.out.println("So dong " + sodong + "  -- socot " + socot );
				
				dbutils db = new dbutils();
				String query = "";
				
				//sodong = 18;
				
				String donhangId="";
				String kbhId="";
				
				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);
				WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
				cellTitle.setColour(Colour.BLACK);
				cellTitle.setBoldStyle(WritableFont.BOLD);
				
				WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
				celltieude.setAlignment(Alignment.CENTRE);
				WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
				cellFontWhite.setColour(Colour.WHITE);
				
				WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
				
				WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

				cellFormat.setBackground(jxl.format.Colour.GRAY_80);
				cellFormat.setWrap(true);
				cellFormat.setAlignment(Alignment.CENTRE);
				cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

				WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

				cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
				cellFormatSpecical.setWrap(true);
				cellFormatSpecical.setAlignment(Alignment.CENTRE);
				cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
				cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
				cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
				WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);
				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
				cellFormat3.setBackground(jxl.format.Colour.YELLOW);
				cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				
				WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
				cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
				cformat1.setAlignment(Alignment.RIGHT);
				cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				
				w_sheet.addCell(new Label(0, 8, "Chứng từ\\Ngày",cellFormat));
				w_sheet.addCell(new Label(1, 8, "Số",cellFormat));
				w_sheet.addCell(new Label(2, 8, "Diễn giải",cellFormat));
				w_sheet.addCell(new Label(3, 8, "Mã BP\\ĐVT",cellFormat));
				w_sheet.addCell(new Label(4, 8, "Mã kho\\Mã NX",cellFormat));
				w_sheet.addCell(new Label(5, 8, "Số lượng",cellFormat));
				w_sheet.addCell(new Label(6, 8, "Giá bán",cellFormat));
				w_sheet.addCell(new Label(7, 8, "Doanh thu",cellFormat));
				w_sheet.addCell(new Label(8, 8, "Chưa có mã khách hàng",cellFormat));
				w_sheet.addCell(new Label(9, 8, "Chưa phân tuyến",cellFormat));
				w_sheet.addCell(new Label(10, 8, "Sản phẩm chưa có trong hệ thống",cellFormat));
				w_sheet.addCell(new Label(11, 8, "Có Lỗi IMPORT",cellFormat));
				w_sheet.addCell(new Label(12, 8, "Số đơn hàng",cellFormat));
				w_sheet.addCell(new Label(13, 8, "Kênh bán hàng",cellFormat));
			
				String userId="100230";
				String nppId ="";
				String khoId="100000";
				query=" Select pk_Seq from nhaphanphoi b where   TEN LIKE N'%Chi nhánh Khánh Hòa%'  ";	
				ResultSet rs =db.get(query);
				while(rs.next())
				{
					nppId=rs.getString("pk_Seq");
				}
				
				for (int i= 9 ; i <sodong;i++)
				{
					String ngayHD="";
					Cell valueCell = sheet.getCell(0, i);  
					if (valueCell.getType() == CellType.DATE) 
					{  
						DateCell dCell = (DateCell) valueCell;  
		        TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
		        DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
		        destFormat.setTimeZone(gmtZone);  
		        ngayHD = destFormat.format(dCell.getDate());  
					}   
					
					/*if(ngayHD.contains("2014-09"))
					{
						ngayHD="2014-08-31";
					}*/
					
					/*System.out.println("___________"+ngayHD);*/
					
					
					String SoHD = sheet.getCell(1, i).getContents().trim();
					String maKH_SP = sheet.getCell(2, i).getContents().trim();
					String donvi = sheet.getCell(3, i).getContents();
					String soluong = sheet.getCell(5, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					
					String MaKho = sheet.getCell(4, i).getContents().trim().replaceAll(",", "").replaceAll("\\.", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					
					String giaban = sheet.getCell(6, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					String doanhthu = sheet.getCell(7, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					
					
					w_sheet.addCell(new Label(0, i, ngayHD,cellFormat2));
					w_sheet.addCell(new Label(1, i, SoHD,cellFormat2));
					w_sheet.addCell(new Label(2, i, maKH_SP,cellFormat2));
					w_sheet.addCell(new Label(3, i, donvi,cellFormat2));
					w_sheet.addCell(new Label(4, i, MaKho,cellFormat2));
					w_sheet.addCell(new Label(5, i, soluong,cellFormat2));
					w_sheet.addCell(new Label(6, i, giaban,cellFormat2));
					w_sheet.addCell(new Label(7, i,  doanhthu,cellFormat2));
					
					
					String maKH = "";
					String maSP="";
					Utility util = new Utility();
					boolean exitMAKH = false;
					
					if( maKH_SP.indexOf("(") >= 0 && SoHD.trim().length() >  0  )  //Mã khách hàng
					{
						donhangId="";
						
						System.out.println("___NEW:::::::::::::"+util.replaceAEIOU( maKH_SP.toUpperCase()));
					}
						
					Hashtable<String, String> htbDoiTac =getDoiTacId(nppId, db);
					
					String doitacId="";
					
					
					System.out.println("____________"+i);
					
					/*if(maKH_SP.indexOf("(") >= 0 && SoHD.trim().length() >  0 && MaKho.equals("KHO60") )  //Mã khách hàng*/
					if(maKH_SP.indexOf("(") >= 0 && SoHD.trim().length() >  0 )  //Mã khách hàng*
					{
						System.out.println("___NEW_______________"+maKH_SP);
						donhangId="";
						kbhId="";
						maKH =maKH_SP.substring(maKH_SP.lastIndexOf("(")+1,maKH_SP.lastIndexOf(")") );					
						//INSERT DON HANG  --> CHECK KHACH HANG NAY DA CO TRONG HE THONG CHUA
						query = "select count(pk_Seq) as soDONG,kbh_fk from KHACHHANG where npp_FK='"+nppId+"' and maFAST = '" + maKH + "'group by pk_Seq,kbh_fk ";
						ResultSet rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							if(rsCHECK.getInt("soDONG") > 0)
							{
								exitMAKH = true;
								kbhId=rsCHECK.getString("kbh_fk");
							}
						}
						rsCHECK.close();
						
						doitacId=htbDoiTac.get(maKH);
						if(doitacId!=null)
						{
							exitMAKH = true;
							System.out.println("__DOI_TAC_"+maKH);
						}
						
						if(doitacId==null)
						{
							if(!exitMAKH)
							{
								System.out.println("[KhachHangChuaTonTai]"+maKH);
								w_sheet.addCell(new Label(8, i, maKH,cellFormat2));
							}
							
							exitMAKH = false;
							query = "select COUNT(*) as SoDong from KHACHHANG_TUYENBH where KHACHHANG_FK in (select pk_Seq from KHACHHANG where maFAST='"+maKH+"'  and  npp_FK='"+nppId+"' ) ";
							rsCHECK = db.get(query);
							if(rsCHECK.next())
							{
								if(rsCHECK.getInt("soDONG") > 0)
								{
									exitMAKH = true;
								}
							}
							rsCHECK.close();
							
							if(!exitMAKH)
							{
								w_sheet.addCell(new Label(9, i, maKH,cellFormat2));
								System.out.println("[KHACHHANG_TUYENBH]"+maKH);
							}
							
						}
						exitMAKH=false;
							
						if(exitMAKH)
						{
							w_sheet.addCell(new Label(13, i,kbhId,cellFormat2));
							//KENH OTC 
							if(kbhId.equals("100025"))
							{
									donhangId=	DonHangOTC(SoHD, maKH, ngayHD, db,userId,nppId,khoId);
									System.out.println("donhangId___"+donhangId);
									if(donhangId.length()>0)
									{
										query="delete from DonHang_SanPham where DonHang_fk='"+donhangId+"' ";
										
										if(!db.update(query))
										{
											System.out.println("DonHang_SanPham"+query);
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
										query="delete from DonHang_CTKM_TRAKM where DonHangId='"+donhangId+"' ";
										if(!db.update(query))
										{
											System.out.println("DonHang_CTKM_TRAKM"+query);
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
										
										query="delete from DUYETTRAKHUYENMAI_DONHANG where donhang_fk='"+donhangId+"' ";
										if(!db.update(query))
										{
											query="delete from DUYETTRAKHUYENMAI_DONHANG where DonHangId='"+donhangId+"' ";
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
									}
							}else if(!kbhId.equals("100025")&& doitacId==null)
							{
								donhangId=DonHangETC(SoHD,maKH,ngayHD,db,userId,nppId,khoId);
								query = "delete ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + donhangId+ "'";
								if(!db.update(query))
								{
									System.out.println("ERP_DONDATHANGNPP_SANPHAM"+query);
									w_sheet.addCell(new Label(11, i,query,cellFormat2));
								}
							}else if(doitacId!=null && doitacId.length()>0)
							{
								donhangId=DonHangETC_DoiTac(SoHD,maKH,ngayHD,db,userId,nppId,khoId);
								System.out.println(":::Doi tac :::::");
								query = "delete ERP_DONDATHANGNPP_SANPHAM where dondathang_fk = '" + donhangId+ "'";
								if(!db.update(query))
								{
									System.out.println("ERP_DONDATHANGNPP_SANPHAM"+query);
									w_sheet.addCell(new Label(11, i,query,cellFormat2));
								}
							}
								w_sheet.addCell(new Label(12, i,donhangId,cellFormat2));
						}
						
						/**************/
					}
					
					
					if( maKH_SP.indexOf("-") > 0 && donvi.trim().length() > 0) 
					{
							maSP = maKH_SP.substring(0,maKH_SP.indexOf("-")).trim().replace(".", "");
							w_sheet.addCell(new Label(12, i,donhangId));
							if(donhangId.length()>0 && ! ( util.replaceAEIOU( maKH_SP.toUpperCase()).contains("CHIET-KHAU")  || util.replaceAEIOU( maKH_SP.toUpperCase()).contains("GIAM-TRU"))  )
							{
								int SoDong=0;
								query = "select COUNT(*) as SoDong from SanPham where MA='"+maSP+"' ";
								ResultSet	rsCHECK = db.get(query);
								if(rsCHECK.next())
								{
									SoDong=rsCHECK.getInt("SoDong");
								}
								if(rsCHECK!=null)rsCHECK.close();
								
								if(SoDong==0)
								{
									w_sheet.addCell(new Label(10, i,maSP,cellFormat2));
								}
								
								//********************************************************HANG BAN ***************************************************/*
								
							if(kbhId.equals("100025"))
								{
									//KHI CHAY HOA DON HANG KM THI BO DONG HANG BAN
									
									query="select count(*) as SoDong from DONHANG_SANPHAM where donhang_fk='"+donhangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') ";
									rs= db.get(query);
									
									while(rs.next())
									{
										SoDong=rs.getInt("SoDong");
									}
									if(rs!=null)rs.close();
									if(SoDong>0)
									{
										query= "update DONHANG_SANPHAM set soluong=soluong+'"+soluong+"' where donhang_fk='"+donhangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') ";
										SoDong =db.updateReturnInt(query);
										if(SoDong<=0)
										{
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
											System.out.println("DONHANG_SANPHAM_____________"+query);
										}
									}
									else 
									{
										
										query=
												"INSERT INTO DONHANG_SANPHAM(DONHANG_FK,SANPHAM_FK,SOLUONG,GIAMUA,KHO_FK,CHIETKHAU,THUEVAT,THANHTIEN,TIENVAT,DonGiaGoc) "+
												"select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+"/(1+nh.ThueXuat/100.0) as giamua,100000 as kho  "+
												"	,0 as ck,nh.ThueXuat as thueVAT,'"+doanhthu+"' AS thanhtien,0 as tienvat,"+giaban+"/(1+nh.ThueXuat/100.0)  "+
												"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk where MA='"+maSP+"' ";
										
									/*	query=
										"INSERT INTO DONHANG_SANPHAM(DONHANG_FK,SANPHAM_FK,SOLUONG,GIAMUA,KHO_FK,CHIETKHAU,THUEVAT,THANHTIEN,TIENVAT,DonGiaGoc) "+
										"select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+" as giamua,'"+khoId+"' as kho,0 as ck,  "+
										" nh.ThueXuat as thueVAT,'"+doanhthu+"' AS thanhtien,0 as tienvat,'"+giaban+"'  as DG_GOC "+
										"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk where MA='"+maSP+"' ";*/
										
										
										SoDong =db.updateReturnInt(query);
										System.out.println("DONHANG_SANPHAM"+query);
										if(SoDong<=0)
										{
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
											System.out.println("DONHANG_SANPHAM__________________"+query);
										}
									}
								}
								else 
								{
									query="select count(*) as SoDong from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk='"+donhangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') ";
									rs= db.get(query);
									
									while(rs.next())
									{
										SoDong=rs.getInt("SoDong");
									}
									if(rs!=null)rs.close();
									
									if(SoDong>0)
									{
										query= "update ERP_DONDATHANGNPP_SANPHAM set soluong=soluong+'"+soluong+"' where dondathang_fk='"+donhangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') ";
										SoDong =db.updateReturnInt(query);
										if(SoDong<=0)
										{
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
											System.out.println("ERP_DONDATHANGNPP_SANPHAM"+query);
										}
									}else 
									{
									/*	query=
												 "insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) " +
													"select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+" as giamua  "+
													"	,0 as ck,nh.ThueXuat as thueVAT,sp.dvdl_fk,'','' "+
													"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk where MA='"+maSP+"' ";*/
										
										query=
												 "insert ERP_DONDATHANGNPP_SANPHAM( dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) " +
													"select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+"/(1+nh.ThueXuat/100.0) as giamua  "+
													"	,0 as ck,nh.ThueXuat as thueVAT,sp.dvdl_fk,'','' "+
													"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk where MA='"+maSP+"' ";
										
												SoDong	=db.updateReturnInt(query);
												System.out.println("ERP_DONDATHANGNPP_SANPHAM");
												if(SoDong<=0)
												{
													w_sheet.addCell(new Label(11, i,query,cellFormat2));
													System.out.println("DONHANG_SANPHAM"+query);
												}
									}
									System.out.println("ERP_DONDATHANGNPP_SANPHAM");
								}	
							
								//********************************************************HANG BAN ****************************************************//*
								
									//KHI CHAY HOA DON HANG BAN THINBO DONG KM, VA NGUOC LAI
							/*		query=
								"	insert into DONHANG_CTKM_TRAKM(DONHANGID,CTKMID,TRAKMID,SOXUAT,TONGGIATRI,SPMA,SOLUONG,LOAI,KHONVBH)  "+ 
								"	select '"+donhangId+"' as donhangid,   "+
								"	( select top(1) ctkhuyenmai_fk from ctkm_trakm where trakhuyenmai_fk =   "+
								"	(select top(1) trakhuyenmai_fk from trakhuyenmai_sanpham where sp.pk_seq=sanpham_fk)  ) as ctkmid,   "+
								"	(select top(1) trakhuyenmai_fk from trakhuyenmai_sanpham where sp.pk_seq=sanpham_fk) as trakmid   "+
								"	,0 as soxuat ,"+doanhthu+"/(1+nh.thuexuat/100) as tonggiatri ,'"+maSP+"'  as spma ,'"+soluong+"' as soluong ,0,0   "+  
								"	from sanpham sp inner join  nganhhang nh on nh.pk_seq=sp.nganhhang_fk where ma='"+maSP+"' "; 										   
											
									SoDong	=db.updateReturnInt(query);
									if(SoDong<=0)
									{
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
										System.out.println("DONHANG_CTKM_TRAKM"+query);
									}*/
								
								//********************************************************KM****************************************************//*
								
							}
					}
				
				if( donhangId.length()>0 && ( util.replaceAEIOU( maKH_SP.toUpperCase()).contains("CHIET-KHAU")  || util.replaceAEIOU( maKH_SP.toUpperCase()).contains("GIAM-TRU")))
					{
						String ckNgay="CN5,CN10";
						System.out.println("____"+maSP);
						if(ckNgay.indexOf(maSP.trim())>=0)
						{
							String ptThue=maSP.substring(2,maSP.length()).trim();
							
							if(kbhId.equals("100025"))
							{
								query= 
								"	update b set chietkhau=( "+doanhthu+" /(1.0 + (SELECT cast(VAT as float) FROM LOAICHIETKHAU WHERE maloai='"+maSP+"')/100 ) ) "+
								"				*( (soluong*giamua)/( select sum(SOLUONG*GIAMUA) from DONHANG_SANPHAM where DONHANG_FK=b.donhang_fk and THUEVAT= '"+ptThue+"'  )  )  "+
								"	from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK =a.PK_SEQ "+
								"	where b.donhang_fk= '"+donhangId+"' and thuevat= '"+ptThue+"'  ";
							}else  if(kbhId.equals("100052"))
							{
								query= 
								"		update b set chietkhau=( "+doanhthu+" /(1.0 + (SELECT cast(VAT as float) FROM LOAICHIETKHAU WHERE maloai='"+maSP+"')/100 ) )  "+ 
								"			*( (soluong*DONGIA)/( select sum(SOLUONG*dongia) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk=b.dondathang_fk and THUEVAT='"+ptThue+"'  ))  "+   
								"		from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on b.dondathang_fk =a.PK_SEQ   "+
								"		where b.dondathang_fk = '"+donhangId+"'  and thuevat= '"+ptThue+"'    ";
							}
							
							/*
							if(kbhId.equals("100025"))
							{
								query= 
								"	update b set chietkhau=(( "+doanhthu+" "+
								"				*( (soluong*giamua)/( select sum(SOLUONG*GIAMUA) from DONHANG_SANPHAM where DONHANG_FK=b.donhang_fk and THUEVAT= '"+ptThue+"'  )  ) )) "+
								"	from DONHANG a inner join DONHANG_SANPHAM b on b.DONHANG_FK =a.PK_SEQ "+
								"	where b.donhang_fk= '"+donhangId+"' and thuevat= '"+ptThue+"'  ";
							}else  if(kbhId.equals("100052"))
							{
								query= 
								"		update b set chietkhau=(( "+doanhthu+"   "+ 
								"			*( (soluong*DONGIA)/( select sum(SOLUONG*dongia) from ERP_DONDATHANGNPP_SANPHAM where dondathang_fk=b.dondathang_fk and THUEVAT='"+ptThue+"'  ) ))) "+   
								"		from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on b.dondathang_fk =a.PK_SEQ   "+
								"		where b.dondathang_fk = '"+donhangId+"'  and thuevat= '"+ptThue+"'    ";
							}*/
							w_sheet.addCell(new Label(13, i,query,cellFormat2));
							/*System.out.println("CHIET_KHAU__"+query);*/
							int	SoDong	=db.updateReturnInt(query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								System.out.println("CK_ERROR"+query);
							}
						}
						else if(kbhId.equals("100025"))
						{
								String duyetId="100008";
								if(maSP.trim().indexOf("CQ")>=0)
								{
									duyetId="100011";
								}
								String tichluyQUY="0";
								if(maSP.trim().indexOf("CQ")>=0)
								{
									tichluyQUY="1";
								}
								
								query=
										"	insert into DUYETTRAKHUYENMAI_DONHANG(duyetkm_fk,khachhang_fk,donhang_fk,thanhtoan,tichluyQUY,DIENGIAI,ptthue,HienThi )  "+
										"	select '"+duyetId+"' as duyetId,KHACHHANG_FK,PK_SEQ as DonHangId,'"+doanhthu+"' as ThanhToan,'"+tichluyQUY+"' as Quy,'"+maSP+"' as DienGiai,  "+
										"		(SELECT VAT FROM LOAICHIETKHAU WHERE maloai='"+maSP+"') as ptThue ,1 as HienThi " +
										"from DONHANG where pk_seq='"+donhangId+"' and '"+maSP+"' in ( select maloai from LOAICHIETKHAU WHERE maloai not in ('CN5','CN10')  )  ";
										
								
								/*query=
								"	insert into DUYETTRAKHUYENMAI_DONHANG(duyetkm_fk,khachhang_fk,donhang_fk,thanhtoan,tichluyQUY,DIENGIAI,ptthue,HienThi )  "+
								"	select '"+duyetId+"' as duyetId,KHACHHANG_FK,PK_SEQ as DonHangId,"+doanhthu+"*(1+(SELECT VAT FROM LOAICHIETKHAU WHERE maloai='"+maSP+"')/100.0)    as ThanhToan,'"+tichluyQUY+"' as Quy,'"+maSP+"' as DienGiai,  "+
								"		(SELECT VAT FROM LOAICHIETKHAU WHERE maloai='"+maSP+"') as ptThue,1 as HienThi " +
								"from DONHANG where pk_seq='"+donhangId+"' and '"+maSP+"' in ( select maloai from LOAICHIETKHAU WHERE maloai not in ('CN5','CN10')  )  ";*/
								
								/*System.out.println("DUYETTRAKHUYENMAI_DONHANG"+query);*/
								int	SoDong	=db.updateReturnInt(query);
								if(SoDong<=0)
								{
									w_sheet.addCell(new Label(11, i,query,cellFormat2));
									System.out.println("ERR___DUYETTRAKHUYENMAI_DONHANG"+query);
								}
						}
					}
				}
			/*	query=
				 "update DH set DH.TONGGIATRI = case TGT.thanhtoan when 1 then TGT.tongTIEN - TGT.tongTL else TGT.tongTIEN end  " +
					"from DONHANG DH  " +
					"inner join " +
					"( " +
					"	select d.thanhtoan, a.pk_seq as donhangID,  " +
					"		( (          " +
					"					select cast( sum( (soluong * giamua -  ( soluong * giamua * cast( chietkhau * 100 / ( soluong * giamua * 1.0 ) as numeric(18, 0) ) / 100 ) ) * ( 1 + thueVAT / 100 ) ) as numeric(18, 0) )  as tienBvat          " +
					"					from donhang_sanpham           " +
					"					where donhang_fk = a.pk_seq  and ( soluong * giamua ) != 0 )  " +
					"		- isnull( ( select sum(tonggiatri) from donhang_ctkm_trakm where donhangId = a.pk_seq and SPMA is null ), 0 )     " +
					"		- isnull( ( select sum(thanhtien)  from DONHANG_CHIETKHAUBOSUNG where donhang_fk = a.pk_seq )	, 0 )  ) as tongTIEN, " +
					"		isnull( ( select sum(thanhtoan)  from DUYETTRAKHUYENMAI_DONHANG where donhang_fk = a.pk_seq )	, 0 ) 	as tongTL					         " +
					"	from donhang a inner join khachhang d on a.khachhang_fk = d.pk_seq         " +
					"	where a.import = '1' and a.trangthai != 2  " +
					") " +
					"TGT on DH.pk_seq = TGT.donhangID where DH.npp_fk = '" + nppId + "' ";
				
				if(!db.update(query))
				{
					w_sheet.addCell(new Label(11, sodong,query,cellFormat2));
				}*/
				
		/*		query = "update hd set tongtienavat = a.AVAT, tongtienbvat = a.BVAT, vat = a.VAT  \n" +
						"from \n" +
						"( \n" +
						"	select hd.PK_SEQ AS HDID, \n" +
						"		  ( HOADON_CT.tongtien - HOADON_CT.Chietkhau) as BVAT, ( HOADON_CT.VAT - HOADON_CT. VAT_KM) as VAT, \n" +
						"		  round((HOADON_CT.tongtien + HOADON_CT.VAT ) - ( HOADON_CT.Chietkhau  + HOADON_CT.VAT_KM), 0) AS AVAT   \n" +
						"	from HOADON hd inner join \n" +
						"	(     \n" +
						"		select AA.HOADON_FK, AA.tongtien, AA.VAT,ISNULL( BB.Chietkhau,0) AS Chietkhau , ISNULL(BB.VAT,0) as VAT_KM \n" +
						"		from \n" +
						"		( \n" +
						"		   select HOADON_fk, SUM( ROUND(SOLUONG * DONGIA, 0 ) ) as tongtien, \n" +
						"				SUM ( ROUND ( ( ROUND(SOLUONG * DONGIA, 0 ) * VAT / 100), 0 ) ) as VAT " +
						"   	from hoadon_sp															 \n" +
						"		   group by HOADON_FK \n" +
						"		 )  \n" +
						"		 AA LEFT join \n" +
						"		 ( \n" +
						"			select hoadon_fk, sum(chietkhau) as chietkhau, sum(ROUND(chietkhau * thueVAT / 100,0)) as VAT   \n" +
						"			from HOADON_CHIETKHAU \n" +
						"			group by hoadon_fk  \n" +
						"		)  \n" +
						"		BB  ON AA. HOADON_FK = BB.HOADON_FK  \n" +
						"	)  \n" +
						"	HOADON_CT on hd.PK_SEQ = HOADON_CT.HOADON_FK \n" +
						"       left join KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n" +
						") \n" +
						"as a inner join HOADON hd on hd.PK_SEQ= a.HDID \n" +
						" WHERE HD.NPP_FK='"+nppId+"' and HopDong is not null  and HD.LOAIHOADON=0 " ;
				
				System.out.println(":::UPDATE:::"+query);
				
				int 	SoDong =db.updateReturnInt(query);
				if(SoDong<=0)
				{
					w_sheet.addCell(new Label(11, 0,query,cellFormat2));
					System.out.println("HOADON___"+query);
				}*/
				
				
				workbook.write();
				workbook.close();
				if(db!=null)db.shutDown();
				
				System.out.println("**************************CHAY XONG ROI********************************");
			} 
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				
			}
			
		}
		
		
		
		private void UpLoad_HoaDon(String fileName) 
		{
			File inputWorkbook = new File(fileName);
			File output_Workbook = new File("D:\\importExcelFILE.xls");
			Workbook w;
			try
			{
				w = Workbook.getWorkbook(inputWorkbook);
				Sheet sheet = w.getSheet(0);
				
				WritableSheet w_sheet =null;
				WritableWorkbook workbook = jxl.Workbook.createWorkbook(output_Workbook);
				w_sheet = workbook.createSheet("importExcelFILE",1);
				
				int sodong = sheet.getRows();
				int socot = sheet.getColumns();
				
				System.out.println("So dong " + sodong + "  -- socot " + socot );
				
				dbutils db = new dbutils();
				String query = "";
				
				//sodong = 18;
				
				String donhangId="";
				String kbhId="";
				
				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);
				WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
				cellTitle.setColour(Colour.BLACK);
				cellTitle.setBoldStyle(WritableFont.BOLD);
				
				WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
				celltieude.setAlignment(Alignment.CENTRE);
				WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
				cellFontWhite.setColour(Colour.WHITE);
				
				WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
				
				WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

				cellFormat.setBackground(jxl.format.Colour.GRAY_80);
				cellFormat.setWrap(true);
				cellFormat.setAlignment(Alignment.CENTRE);
				cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

				WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

				cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
				cellFormatSpecical.setWrap(true);
				cellFormatSpecical.setAlignment(Alignment.CENTRE);
				cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
				cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
				cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
				cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
				WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);
				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
				cellFormat3.setBackground(jxl.format.Colour.YELLOW);
				cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				
				WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
				cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
				cformat1.setAlignment(Alignment.RIGHT);
				cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				
				w_sheet.addCell(new Label(0, 8, "Chứng từ\\Ngày",cellFormat));
				w_sheet.addCell(new Label(1, 8, "Số",cellFormat));
				w_sheet.addCell(new Label(2, 8, "Diễn giải",cellFormat));
				w_sheet.addCell(new Label(3, 8, "Mã BP\\ĐVT",cellFormat));
				w_sheet.addCell(new Label(4, 8, "Mã kho\\Mã NX",cellFormat));
				w_sheet.addCell(new Label(5, 8, "Số lượng",cellFormat));
				w_sheet.addCell(new Label(6, 8, "Giá bán",cellFormat));
				w_sheet.addCell(new Label(7, 8, "Doanh thu",cellFormat));
				w_sheet.addCell(new Label(8, 8, "Chưa có mã khách hàng",cellFormat));
				w_sheet.addCell(new Label(9, 8, "Chưa phân tuyến",cellFormat));
				w_sheet.addCell(new Label(10, 8, "Sản phẩm chưa có trong hệ thống",cellFormat));
				w_sheet.addCell(new Label(11, 8, "Có Lỗi IMPORT",cellFormat));
				w_sheet.addCell(new Label(12, 8, "Số đơn hàng",cellFormat));
				w_sheet.addCell(new Label(13, 8, "Kênh bán hàng",cellFormat));
			

				String userId="100240";
				String nppId ="";
				String khoId="100000";
				/*query=" Select pk_Seq from nhaphanphoi b  WHERE TimKiem like N'%TRUNG%' AND PK_SEQ=106231  ";*/
				query=" Select pk_Seq from nhaphanphoi b  WHERE TEN like N'%Chi Nhánh Công Ty CP Traphaco tại Quảng Ngãi%'  ";
				ResultSet rs =db.get(query);
				while(rs.next())
				{
					nppId=rs.getString("pk_Seq");
				}
				
				for (int i=9; i < sodong ; i++)
				{
					System.out.println("_______________"+i);
					String ngayHD="";
					Cell valueCell = sheet.getCell(0, i);  
					if (valueCell.getType() == CellType.DATE) 
					{  
						DateCell dCell = (DateCell) valueCell;  
		        TimeZone gmtZone = TimeZone.getTimeZone("GMT");  
		        DateFormat destFormat = new SimpleDateFormat("yyyy-MM-dd");  
		        destFormat.setTimeZone(gmtZone);  
		        ngayHD = destFormat.format(dCell.getDate());  
					}   
					
					String SoHD = sheet.getCell(1, i).getContents().trim();
					String maKH_SP = sheet.getCell(2, i).getContents().trim();
					String donvi = sheet.getCell(3, i).getContents().trim();
					String soluong = sheet.getCell(5, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					
					String MaKho = sheet.getCell(4, i).getContents().trim().replaceAll(",", "").replaceAll("\\.", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					
					String giaban = sheet.getCell(6, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					String doanhthu = sheet.getCell(7, i).getContents().trim().replaceAll(",", "").replaceAll(" ", "").replaceAll("\\-","").trim();
					
					
					w_sheet.addCell(new Label(0, i, ngayHD,cellFormat2));
					w_sheet.addCell(new Label(1, i, SoHD,cellFormat2));
					w_sheet.addCell(new Label(2, i, maKH_SP,cellFormat2));
					w_sheet.addCell(new Label(3, i, donvi,cellFormat2));
					w_sheet.addCell(new Label(4, i, MaKho,cellFormat2));
					w_sheet.addCell(new Label(5, i, soluong,cellFormat2));
					w_sheet.addCell(new Label(6, i, giaban,cellFormat2));
					w_sheet.addCell(new Label(7, i,  doanhthu,cellFormat2));
					
					
					String maKH = "";
					String maSP="";
					Utility util = new Utility();
					boolean exitMAKH = false;
					
					Hashtable<String, String> htbDoiTac =getDoiTacId(nppId, db);
					
					String doitacId="";
					
					
					if( maKH_SP.indexOf("(") >= 0 && SoHD.trim().length() >  0  )  //Mã khách hàng
					{
						
						if(donhangId.length()>0)
						{
							query = "update hd set tongtienavat = a.AVAT, tongtienbvat = a.BVAT, vat = a.VAT  \n" +
									"from 		\n" +
									"( 	\n" +
									"	select hd.PK_SEQ AS HDID, \n" +
									"		  ( HOADON_CT.tongtien - HOADON_CT.Chietkhau) as BVAT, ( HOADON_CT.VAT - HOADON_CT. VAT_KM) as VAT, \n" +
									"		  round((HOADON_CT.tongtien + HOADON_CT.VAT ) - ( HOADON_CT.Chietkhau  + HOADON_CT.VAT_KM), 0) AS AVAT \n" +
									"	from HOADON hd inner join \n" +
									"	(     \n" +
									"		select AA.HOADON_FK, AA.tongtien, AA.VAT,ISNULL( BB.Chietkhau,0 ) as  Chietkhau ,isnull( BB.VAT,0)  as VAT_KM \n" +
									"		from \n" +
									"		( \n" +
									"		   select HOADON_fk, SUM( ROUND(SOLUONG * DONGIA, 0 ) ) as tongtien, \n" +
									"				SUM ( ROUND ( ( ROUND(SOLUONG * DONGIA, 0 ) * VAT / 100), 0 ) ) as VAT  \n" +
									"		   from HOADON_SP where HOADON_fk = '" + donhangId + "' \n" +
									"		   group by HOADON_FK \n" +
									"		 )  \n" +
									"		 AA LEFT join \n" +
									"		 ( \n" +
									"			select hoadon_fk, sum(chietkhau) as chietkhau, sum(ROUND(chietkhau * thueVAT / 100,0)) as VAT   \n" +
									"			from HOADON_CHIETKHAU where HOADON_fk = '" + donhangId + "'  " +
									"			group by hoadon_fk \n" +
									"		)  \n" +
									"		BB  ON AA. HOADON_FK = BB.HOADON_FK  \n" +
									"	)  \n" +
									"	HOADON_CT on hd.PK_SEQ = HOADON_CT.HOADON_FK \n" +
									"       left join KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n" +
									"	where  hd.PK_SEQ = '" + donhangId + "'  " +
									") " +
									"as a inner join HOADON hd on hd.PK_SEQ= a.HDID " +
									"where  hd.PK_SEQ = '" + donhangId + "' ";
							if(!db.update(query))
							{
								System.out.println("CapNhat_Tien"+query);
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
							}
						}
						donhangId="";
						System.out.println("___NEW:::::::::::::"+util.replaceAEIOU( maKH_SP.toUpperCase()));
					}
						
					/*if(maKH_SP.indexOf("(") >= 0 && SoHD.trim().length() >  0 && MaKho.equals("KHO59")  && donvi.equals("OTC") )*/ 
					if(maKH_SP.indexOf("(") >= 0 && SoHD.trim().length() >  0  )
					{
						
						System.out.println("___NEW_______________"+maKH_SP);
						donhangId="";
						kbhId="";
						maKH =maKH_SP.substring(maKH_SP.lastIndexOf("(")+1,maKH_SP.lastIndexOf(")") );					
						//INSERT DON HANG  --> CHECK KHACH HANG NAY DA CO TRONG HE THONG CHUA
						query = "select count(pk_Seq) as soDONG,kbh_fk from KHACHHANG where npp_FK='"+nppId+"' and maFAST = '" + maKH + "'group by pk_Seq,kbh_fk ";
						ResultSet rsCHECK = db.get(query);
						if(rsCHECK.next())
						{
							if(rsCHECK.getInt("soDONG") > 0)
							{
								exitMAKH = true;
								kbhId=rsCHECK.getString("kbh_fk");
							}
						}
						rsCHECK.close();
						
						doitacId=htbDoiTac.get(maKH);
						if(doitacId!=null)
						{
							exitMAKH = true;
							System.out.println("__DOI_TAC_"+maKH);
						}
						
						if(!exitMAKH)
						{
							System.out.println("[KhachHangChuaTonTai]"+maKH);
							w_sheet.addCell(new Label(8, i, maKH,cellFormat2));
						}
						
						if(doitacId==null)
						{
							exitMAKH = false;
							query = "select COUNT(*) as SoDong from KHACHHANG_TUYENBH where KHACHHANG_FK in (select pk_Seq from KHACHHANG where maFAST='"+maKH+"'  and  npp_FK='"+nppId+"' ) ";
							rsCHECK = db.get(query);
							if(rsCHECK.next())
							{
								if(rsCHECK.getInt("soDONG") > 0)
								{
									exitMAKH = true;
								}
							}
							rsCHECK.close();
							
							if(!exitMAKH)
							{
								w_sheet.addCell(new Label(9, i, maKH,cellFormat2));
								System.out.println("[KHACHHANG_TUYENBH]"+maKH);
							}
						}
						
						/*************************/
						
						/*exitMAKH=false;*/
						
						if(exitMAKH)
						{
							w_sheet.addCell(new Label(13, i,kbhId,cellFormat2));
							//KENH OTC 
							if(kbhId.equals("100025"))
							{
									donhangId=HoaDonOTC(SoHD, maKH, ngayHD, db,userId,nppId,khoId);
									if(donhangId.length()>0)
									{
										query="delete from HoaDon_Sp where HoaDon_fk='"+donhangId+"' ";
										if(!db.update(query))
										{
											System.out.println("HoaDon_Sp"+query);
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
										
										query="delete from HoaDon_Sp_ChiTiet where HoaDon_fk='"+donhangId+"' ";
										if(!db.update(query))
										{
											System.out.println("HoaDon_Sp"+query);
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
										
										query="delete from HOADON_CTKM_TRAKM_CHITIET where HoaDon_fk='"+donhangId+"' ";
										if(!db.update(query))
										{
											System.out.println("HoaDon_Sp"+query);
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
										
									/*	query="delete from HoaDon_DDH where HoaDon_fk='"+donhangId+"' ";
										if(!db.update(query))
										{
											System.out.println("HoaDon_DDH"+query);
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}*/
										
										query="delete from HOADON_CTKM_TRAKM where HoaDon_fk='"+donhangId+"' ";
										if(!db.update(query))
										{
											query="delete from HOADON_CTKM_TRAKM where DonHangId='"+donhangId+"' ";
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
										
										query="delete from HOADON_CHIETKHAU where HoaDon_fk='"+donhangId+"' ";
										if(!db.update(query))
										{
											query="delete from HOADON_CHIETKHAU where DonHangId='"+donhangId+"' ";
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
										}
										
									}
							}else 
							{
								donhangId=HoaDonETC(SoHD,maKH,ngayHD,db,userId,nppId,khoId);
								query = "delete ERP_HOADONNPP_SP where hoadon_Fk = '" + donhangId+ "'";
								if(!db.update(query))
								{
									System.out.println("ERP_HOADONNPP_SP"+query);
									w_sheet.addCell(new Label(11, i,query,cellFormat2));
								}
								
							/*	query = "delete ERP_HOADONNPP_DDH where hoadonnpp_Fk = '" + donhangId+ "'";
								if(!db.update(query))
								{
									System.out.println("ERP_HOADONNPP_SP"+query);
									w_sheet.addCell(new Label(11, i,query,cellFormat2));
								}*/
								
							}
									w_sheet.addCell(new Label(12, i,donhangId,cellFormat2));
						}
						
					/*********j*****/
					}
					
					System.out.println("donhangId___"+donhangId);
					
					
			/*	*********/	
					if( maKH_SP.indexOf("-") > 0 && donvi.trim().length() > 0) 
					{
							maSP = maKH_SP.substring(0,maKH_SP.indexOf("-")).trim().replace(".", "");
							w_sheet.addCell(new Label(12, i,donhangId));
							if(donhangId.length()>0 && ! ( util.replaceAEIOU( maKH_SP.toUpperCase()).contains("CHIET-KHAU")  || util.replaceAEIOU( maKH_SP.toUpperCase()).contains("GIAM-TRU"))  )
							{
								int SoDong=0;
								query = "select COUNT(*) as SoDong from SanPham where MA='"+maSP+"' ";
								ResultSet	rsCHECK = db.get(query);
								if(rsCHECK.next())
								{
									SoDong=rsCHECK.getInt("SoDong");
								}
								if(rsCHECK!=null)rsCHECK.close();
								
								if(SoDong==0)
								{
									w_sheet.addCell(new Label(10, i,maSP,cellFormat2));
								}
								
								//********************************************************HANG BAN ****************************************************//*
							if(kbhId.equals("100025"))
								{
									//KHI CHAY HOA DON HANG KM THI BO DONG HANG BAN
									
									query="select count(*) as SoDong from HOADON_SP where hoadon_fk='"+donhangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') ";
									rs= db.get(query);
									
									while(rs.next())
									{
										SoDong=rs.getInt("SoDong");
									}
									if(rs!=null)rs.close();
									if(SoDong>0)
									{
										query= "update HOADON_SP set soluong=soluong+'"+soluong+"' where hoadon_fk='"+donhangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') ";
										SoDong =db.updateReturnInt(query);
										if(SoDong<=0)
										{
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
											System.out.println("HOADON_SP"+query);
										}
									}
									else 
									{
										
										query=
												"insert into HOADON_SP(HOADON_FK,SANPHAM_FK,SOLUONG,DONGIA,VAT,DONVITINH,SCHEME,CHIETKHAU,THANHTIEN,KHO_FK,KBH_FK,DonGiaGoc) \n"+
												"	select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+"/(1+nh.ThueXuat/100) as DONGIA,nh.THUEXUAT,dvdl.DONVI, '' as Scheme ,0  as ck,"+doanhthu+"   ,"+khoId+" as kho,100025,"+giaban+"/(1+nh.ThueXuat/100)  \n"+  
												"	from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk    \n"+
												"	inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_Fk      where MA='"+maSP+"'    \n";
								/*		
									query=
												"insert into HOADON_SP(HOADON_FK,SANPHAM_FK,SOLUONG,DONGIA,VAT,DONVITINH,SCHEME,CHIETKHAU,THANHTIEN,KHO_FK,KBH_FK,DonGiaGoc) \n"+
												"	select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+" as DONGIA,nh.THUEXUAT,dvdl.DONVI, '' as Scheme ,0  as ck,"+doanhthu+"   ,"+khoId+" as kho,100025 ,"+giaban+"   \n"+  
												"	from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk    \n"+
												"	inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_Fk      where MA='"+maSP+"'    \n";
										*/
										SoDong =db.updateReturnInt(query);
										System.out.println("HOADON_SP "+query);
										if(SoDong<=0)
										{
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
											System.out.println("_______________HOADON_SP____________"+query);
										}
										
										
										query=
												"insert into HOADON_SP_CHITIET(hoadon_fk,MA,TEN,DONVI,DONGIA,SOLO,NGAYHETHAN,THUEVAT,SOLUONG,CHIETKHAU,DonGiaGoc) \n"+
												"select '"+donhangId+"' as dhId,sp.MA as spId,sp.ten as spTEN,dvdl.donvi,"+giaban+" /(1+nh.ThueXuat/100) , '' as SoLo,'' as NgayHetHan,nh.ThueXuat,'"+soluong+"',0 ,'"+giaban+"'  \n"+ 
												"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk \n"+
												"inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_Fk    \n"+
												"  where MA='"+maSP+"' ";
										
									
										/*query=
										"insert into HOADON_SP_CHITIET(hoadon_fk,MA,TEN,DONVI,DONGIA,SOLO,NGAYHETHAN,THUEVAT,SOLUONG,CHIETKHAU,DonGiaGoc) \n"+
										"select '"+donhangId+"' as dhId,sp.MA as spId,sp.ten as spTEN,dvdl.donvi,"+giaban+", '' as SoLo,'' as NgayHetHan,nh.ThueXuat,'"+soluong+"',0,'"+giaban+"' \n"+ 
										"from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk \n"+
										"inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_Fk    \n"+
										"  where MA='"+maSP+"' ";*/
										
										SoDong =db.updateReturnInt(query);
										System.out.println("HOADON_SP_CHITIET");
										if(SoDong<=0)
										{
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
											System.out.println("_______________HOADON_SP_CHITIET____________"+query);
										}
										
									}
								}
								else 
								{
									
									
									query="select count(*) as SoDong from ERP_HOADONNPP_SP where hoadon_fk='"+donhangId+"' and sanpham_Fk =(select pk_Seq from sanpham where ma='"+maSP+"') ";
									rs= db.get(query);
									
									while(rs.next())
									{
										SoDong=rs.getInt("SoDong");
									}
									if(rs!=null)rs.close();
									
									if(SoDong>0)
									{
										query= "update ERP_HOADONNPP_SP set soluong=soluong+'"+soluong+"' where hoadon_fk='"+donhangId+"' and sanpham_fk=(select pk_Seq from sanpham where ma='"+maSP+"') ";
										SoDong =db.updateReturnInt(query);
										if(SoDong<=0)
										{
											w_sheet.addCell(new Label(11, i,query,cellFormat2));
											System.out.println("ERP_HOADONNPP_SP__"+query);
										}
									}else 
									{
										
										query=
												 "	insert into ERP_HOADONNPP_SP(HOADON_FK,SANPHAM_FK,SOLUONG,DONGIA,VAT,DONVITINH,SCHEME,CHIETKHAU,THANHTIEN,KHO_FK,sanphamTEN) " +
												 "	select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+"/(1+nh.ThueXuat/100) as DONGIA, nh.ThueXuat,dvdl.donvi,'' as Scheme ,0 as ck,"+doanhthu+"   ,100000 as kho,sp.Ten  "+
												 "	from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk " +
												 "		inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_Fk    " +
												 "  where MA='"+maSP+"' ";
																				
								/*	query=
												 "	insert into ERP_HOADONNPP_SP(HOADON_FK,SANPHAM_FK,SOLUONG,DONGIA,VAT,DONVITINH,SCHEME,CHIETKHAU,THANHTIEN,KHO_FK,sanphamTEN) " +
												 "	select '"+donhangId+"' as dhId,sp.pk_Seq as spId,'"+soluong+"' as soluong,"+giaban+" as DONGIA, nh.ThueXuat,dvdl.donvi,'' as Scheme ,0 as ck, "+doanhthu+"   ,100000 as kho,sp.Ten  "+
												 "	from SANPHAM sp inner join  NGANHHANG nh on nh.pk_Seq=sp.nganhhang_fk " +
												 "		inner join donvidoluong dvdl on dvdl.pk_Seq=sp.dvdl_Fk    " +
												 "  where MA='"+maSP+"' ";*/
										
												SoDong	=db.updateReturnInt(query);
												
												if(SoDong<=0)
												{
													w_sheet.addCell(new Label(11, i,query,cellFormat2));
													System.out.println("ERP_HOADONNPP_SP___"+query);
												}
									}
									System.out.println("ERP_HOADONNPP_SP");
								}
							
								//********************************************************HANG BAN ****************************************************//*
								
									//KHI CHAY HOA DON HANG BAN THINBO DONG KM, VA NGUOC LAI
								/*	query=
							"		insert into HOADON_CTKM_TRAKM(HOADON_FK,SANPHAM_FK,SANPHAMMA,SOLUONG,CTKM,DONVI,DONGIA,VAT)  "+
							"			select 1 as hdId,sp.PK_SEQ,sp.MA spMA,"+soluong+" as SoLuong,  "+
							"			(select top(1) trakhuyenmai_fk from trakhuyenmai_sanpham where sp.pk_seq=sanpham_fk) as ctkmId,  "+
							"				dv.DONVI,"+giaban+" as DonGia,nh.THUEXUAT   "+
							"			from sanpham sp inner join  nganhhang nh on nh.pk_seq=sp.nganhhang_fk  "+	
							"				inner join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK  "+
							"			where ma='"+maSP+"'  ";
									SoDong	=db.updateReturnInt(query);
									if(SoDong<=0)
									{
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
										System.out.println("HOADON_CTKM_TRAKM"+query);
									}
									
									query=
							"		insert into HOADON_CTKM_TRAKM_CHITIET(hoadon_fk,sanpham_fk,soluong,scheme,solo,ngayhethan,kbh_fk,kho_fk,dongia,ptThue)  "+
							"		select 1 as hdId,sp.PK_SEQ,sp.MA spMA,"+soluong+"  as SoLuong,   "+
							"		(select top(1) trakhuyenmai_fk from trakhuyenmai_sanpham where sp.pk_seq=sanpham_fk) as ctkmId,'NA' as SoLo,'' as NgayHetHan,10000 as kho,  "+
							"		dv.DONVI,"+giaban+" as DonGia,nh.THUEXUAT  "+
							"		from sanpham sp inner join  nganhhang nh on nh.pk_seq=sp.nganhhang_fk  "+	
							"		inner join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK  "+
							"		where ma='"+maSP+"' ";
									SoDong	=db.updateReturnInt(query);
									if(SoDong<=0)
									{
										w_sheet.addCell(new Label(11, i,query,cellFormat2));
										System.out.println("HOADON_CTKM_TRAKM_CHITIET"+query);
									}*/
								
								//********************************************************KM****************************************************//*
								
					}
				}
				
					System.out.println("___________"+maKH_SP);
					
					if( donhangId.length()>0 && ( util.replaceAEIOU( maKH_SP.toUpperCase()).contains("CHIET-KHAU")  || util.replaceAEIOU( maKH_SP.toUpperCase()).contains("GIAM-TRU")))
					{
							maSP = maKH_SP.substring(0,maKH_SP.indexOf("-")).trim().replace(".", "");
							String ptThue=maSP.substring(2,maSP.length()).trim();
							if(kbhId.equals("100025"))
							{
								query= 
								"	update b set chietkhau=(( "+doanhthu+" "+
								"				*( (soluong*DonGia)/( select sum(SOLUONG*DonGia) from HoaDon_SP where HoaDon_FK=b.HoaDon_FK and b.VAT= '"+ptThue+"'  )  ) )) "+
								"	from HoaDon a inner join HoaDon_SP b on b.HoaDon_FK =a.PK_SEQ "+
								"	where b.HoaDon_FK= '"+donhangId+"' and b.VAT= '"+ptThue+"'  ";
							}else  if(kbhId.equals("100052"))
							{
								query= 
								"		update b set chietkhau=(( "+doanhthu+"   "+ 
								"			*( (soluong*DONGIA)/( select sum(SOLUONG*dongia) from ERP_HOADONNPP_SP where HoaDon_FK=b.HoaDon_FK and VAT='"+ptThue+"'  ) ))) "+   
								"		from Erp_HoaDonNpp a inner join ERP_HOADONNPP_SP b on b.dondathang_fk =a.PK_SEQ   "+
								"		where b.HoaDon_FK = '"+donhangId+"'  and VAT= '"+ptThue+"'    ";
							}
							w_sheet.addCell(new Label(13, i,query,cellFormat2));
							
							System.out.println("CHIET_KHAU");
							
							int	SoDong	=db.updateReturnInt(query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								System.out.println("______CHIET_KHAU____"+query) ;
							}
							
							String duyetId="100008";
							if(maSP.trim().indexOf("CQ")>=0)
							{
								duyetId="100011";
							}
							String tichluyQUY="0";
							if(maSP.trim().indexOf("CQ")>=0)
							{
								tichluyQUY="1";
							}
							query=
							"	insert into HOADON_CHIETKHAU(hoadon_fk,diengiai,chietkhau,thueVAT,stt,tichluyQUY,HienThi)  "+
							" select "+donhangId+",'"+maSP+"' as DienGia,"+doanhthu+"/(1 + (SELECT VAT FROM LOAICHIETKHAU WHERE maloai= '"+maSP+"'  )/100.0) as CK,(SELECT VAT FROM LOAICHIETKHAU WHERE maloai= '"+maSP+"'  ),(SELECT SOTT FROM LOAICHIETKHAU WHERE maloai= '"+maSP+"'  ),"+tichluyQUY+" as tl,1 " +
							" where '"+maSP+"' in (select maLoai from loaichietkhau)    ";
							
						/*	query=
									"	insert into HOADON_CHIETKHAU(hoadon_fk,diengiai,chietkhau,thueVAT,stt,tichluyQUY,HienThi)  "+
									" select "+donhangId+",'"+maSP+"' as DienGia,"+doanhthu+" as CK,(SELECT VAT FROM LOAICHIETKHAU WHERE maloai= '"+maSP+"'  ),(SELECT SOTT FROM LOAICHIETKHAU WHERE maloai= '"+maSP+"'  ),"+tichluyQUY+" as tl,1 " +
									" where '"+maSP+"' in (select maLoai from loaichietkhau)    ";*/
							System.out.println("HOADON_CHIETKHAU"+query);
								SoDong	=db.updateReturnInt(query);
							if(SoDong<=0)
							{
								w_sheet.addCell(new Label(11, i,query,cellFormat2));
								System.out.println("ERROR______"+query);
							}
						
					}
				
			
				}
				
				
				query = "update hd set tongtienavat = a.AVAT, tongtienbvat = a.BVAT, vat = a.VAT  \n" +
						"from \n" +
						"( \n" +
						"	select hd.PK_SEQ AS HDID, \n" +
						"		  ( HOADON_CT.tongtien - HOADON_CT.Chietkhau) as BVAT, ( HOADON_CT.VAT - HOADON_CT. VAT_KM) as VAT, \n" +
						"		  round((HOADON_CT.tongtien + HOADON_CT.VAT ) - ( HOADON_CT.Chietkhau  + HOADON_CT.VAT_KM), 0) AS AVAT   \n" +
						"	from HOADON hd inner join \n" +
						"	(     \n" +
						"		select AA.HOADON_FK, AA.tongtien, AA.VAT,ISNULL( BB.Chietkhau,0) AS Chietkhau , ISNULL(BB.VAT,0) as VAT_KM \n" +
						"		from \n" +
						"		( \n" +
						"		   select HOADON_fk, SUM( ROUND(SOLUONG * DONGIA, 0 ) ) as tongtien, \n" +
						"				SUM ( ROUND ( ( ROUND(SOLUONG * DONGIA, 0 ) * VAT / 100), 0 ) ) as VAT " +
						"   	from hoadon_sp															 \n" +
						"		   group by HOADON_FK \n" +
						"		 )  \n" +
						"		 AA LEFT join \n" +
						"		 ( \n" +
						"			select hoadon_fk, sum(chietkhau) as chietkhau, sum(ROUND(chietkhau * thueVAT / 100,0)) as VAT   \n" +
						"			from HOADON_CHIETKHAU \n" +
						"			group by hoadon_fk  \n" +
						"		)  \n" +
						"		BB  ON AA. HOADON_FK = BB.HOADON_FK  \n" +
						"	)  \n" +
						"	HOADON_CT on hd.PK_SEQ = HOADON_CT.HOADON_FK \n" +
						"       left join KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n" +
						") \n" +
						"as a inner join HOADON hd on hd.PK_SEQ= a.HDID \n" +
						" WHERE HD.NPP_FK='"+nppId+"' and HopDong is not null  and HD.LOAIHOADON=0 " ;
				
				System.out.println(":::UPDATE:::"+query);
				
				int 	SoDong =db.updateReturnInt(query);
				if(SoDong<=0)
				{
					w_sheet.addCell(new Label(11, 0,query,cellFormat2));
					System.out.println("DONHANG_SANPHAM"+query);
				}
				workbook.write();
				workbook.close();
				if(db!=null)db.shutDown();
				
				System.out.println("**************************CHAY XONG ROI********************************");
			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				
			}
			
		}
	
		
		public static Hashtable<String, String> getDoiTacId(String nppId,dbutils db )
		{
			Hashtable<String, String> htb = new Hashtable<String, String>();
			ResultSet rs = db.get("SELECT PK_SEQ,mafast as Ma FROM Nhaphanphoi where tructhuoc_fk='"+nppId+"'");
			try
			{
				while (rs.next())
				{
					htb.put(rs.getString("MA"), rs.getString("PK_SEQ"));
				}
				rs.close();
			} catch (Exception e)
			{
				e.printStackTrace();
			} 
			return htb;
		}
		
}
