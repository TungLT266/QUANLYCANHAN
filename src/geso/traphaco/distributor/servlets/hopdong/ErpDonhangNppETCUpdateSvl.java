package geso.traphaco.distributor.servlets.hopdong;

import geso.traphaco.center.beans.doctien.doctienrachu;
import geso.traphaco.center.beans.dondathang.imp.XLkhuyenmaiTT;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.ctkhuyenmai.ICtkhuyenmai;
import geso.traphaco.distributor.beans.hopdong.IErpDonhangNppETC;
import geso.traphaco.distributor.beans.hopdong.imp.ErpDonhangNppETC;
import geso.traphaco.distributor.beans.trakhuyenmai.ITrakhuyenmai;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.PageSize;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

public class ErpDonhangNppETCUpdateSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	PrintWriter out;
    public ErpDonhangNppETCUpdateSvl() 
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
			
			String type = request.getParameter("type");
			if(type == null)
				type = "";
			String task = request.getParameter("task");
			if(task == null)
				task = "";
			String isMTV = request.getParameter("MTV");
			if(isMTV == null)
				isMTV = "0";
			
			String congtyId= session.getAttribute("congtyId").toString();
			 
			
			if(type.equals("checkDACBIET"))
			{
				PrintWriter out = response.getWriter();
				int khDACBIET = 0;
				
				try 
				{
					String khId = request.getParameter("khId");
					if(khId == null)
						khId = "";
	
					String dhIds = request.getParameter("dhIds");
					if(dhIds == null)
						dhIds = "";
					
					dbutils db = new dbutils();
					
					String query = "";
					
					if(khId.trim().length() > 0)
						query = "select ISDACBIET from KHACHHANG where PK_SEQ = '" + khId + "'";
					else
					{
						dhIds = dhIds.substring(0, dhIds.length() - 1);
						query = "select max(ISDACBIET) as ISDACBIET from KHACHHANG where PK_SEQ in ( select khachhang_fk from ERP_DONDATHANGNPP where pk_seq in ( " + dhIds + " ) ) ";
					}
					System.out.println("CHECK KH DAC BIET: " + query );
					ResultSet rs = db.get(query);
					if(rs != null)
					{
						
							if(rs.next())
							{
								khDACBIET = rs.getInt("ISDACBIET");
							}
							rs.close();
						
					}
					
					System.out.println("KH DAC BIET: " + khDACBIET );
				} 
				catch(Exception e) { }
				
				out.write(Integer.toString(khDACBIET));
			}
			else
			{
		    	String querystring = request.getQueryString();
			    userId = util.getUserId(querystring);
			    
			    if (userId.length()==0)
			    	userId = util.antiSQLInspection(request.getParameter("userId")); 
			    
			    String id = util.getId(querystring);  	
			    IErpDonhangNppETC lsxBean = new ErpDonhangNppETC(id);
			    
			    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
			    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			    
			    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
		    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
		    	
			    lsxBean.setUserId(userId); 
			    lsxBean.setCtyId(congtyId);
			    
			    String capduyet = request.getParameter("capduyet");
			    if(capduyet == null)
			    	capduyet = "CS";
			    lsxBean.setCapduyet(capduyet);
			    
			    String nextJSP = "";
				lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
	
				session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId());
				session.setAttribute("khoId", lsxBean.getKhoNhapId());
				session.setAttribute("nppId", lsxBean.getNppId());
				session.setAttribute("khId", lsxBean.getKhId());
				session.setAttribute("hopdongId", lsxBean.getMahopdong());
				session.setAttribute("loaidonhang", lsxBean.getLoaidonhang());
	
				if(task.equals("print"))
				{
					Create_DH_PDF(response, request, lsxBean, isMTV);
				}
				else if(querystring.contains("display"))
	    		{
	    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDisplay.jsp";
	    		}
	    		else if(querystring.contains("duyet"))
	    		{
	    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDuyetDisplay.jsp";
	    		}
	    		else
	    		{
	    			nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
	    		}
	    		
		        session.setAttribute("lsxBean", lsxBean);
		        response.sendRedirect(nextJSP);
			}
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
			
			IErpDonhangNppETC lsxBean;
			
			Utility util = new Utility();	
			String id = util.antiSQLInspection(request.getParameter("id"));
		    if(id == null)
		    {  	
		    	lsxBean = new ErpDonhangNppETC("");
		    }
		    else
		    {
		    	lsxBean = new ErpDonhangNppETC(id);
		    }
	
		    lsxBean.setUserId(userId);
		    lsxBean.setCtyId(session.getAttribute("congtyId").toString());
		    
		    String tdv_dangnhap_id = session.getAttribute("tdv_dangnhap_id") == null ? "" : session.getAttribute("tdv_dangnhap_id").toString();
		    String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
		    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
		    
		    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
	    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
	    	
		    String tungay = util.antiSQLInspection(request.getParameter("hopdong_tungay"));
		    if(tungay == null || tungay.trim().length() <= 0 )
		    	tungay = getDateTime();
		    lsxBean.setTungay(tungay);
		    
		    String denngay = util.antiSQLInspection(request.getParameter("hopdong_denngay"));
		    if(denngay == null || denngay.trim().length() <= 0)
		    	denngay = getDateTime();
		    lsxBean.setDenngay(denngay);
		    	    
		    String mahopdong = util.antiSQLInspection(request.getParameter("mahopdong"));
		    if(mahopdong == null)
		    	mahopdong = "";
		    lsxBean.setMahopdong(mahopdong);
		    
		    String ghichu = util.antiSQLInspection(request.getParameter("ghichu"));
		    if(ghichu == null)
		    	ghichu = "";
		    lsxBean.setGhichu(ghichu);
		    
			String khonhapId = util.antiSQLInspection(request.getParameter("khonhapId"));
			if (khonhapId == null)
				khonhapId = "";				
			lsxBean.setKhoNhapId(khonhapId);
			
			String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
			if (dvkdId == null)
				dvkdId = "";				
			lsxBean.setDvkdId(dvkdId);
			
			String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
			if (kbhId == null)
				kbhId = "";				
			lsxBean.setKbhId(kbhId);
			
			String gsbhId = util.antiSQLInspection(request.getParameter("gsbhId"));
			if (gsbhId == null)
				gsbhId = "";				
			lsxBean.setGsbhId(gsbhId);
			
			String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
			if (ddkdId == null)
				ddkdId = "";				
			lsxBean.setDdkdId(ddkdId);
			
			String nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";				
			lsxBean.setNppId(nppId);
			
			String khId = util.antiSQLInspection(request.getParameter("khId"));
			if (khId == null)
				khId = "";				
			lsxBean.setKhId(khId);
			
			String vat = util.antiSQLInspection(request.getParameter("ptVat"));
			if (vat == null)
				vat = "";				
			lsxBean.setVat(vat);
			
			String ptChietkhau = util.antiSQLInspection(request.getParameter("ptChietkhau"));
			if (ptChietkhau == null)
				ptChietkhau = "0";				
			lsxBean.setChietkhau(ptChietkhau);
			
			String loaidonhang = util.antiSQLInspection(request.getParameter("loaidonhang"));
			if (loaidonhang == null)
				loaidonhang = "0";				
			lsxBean.setLoaidonhang(loaidonhang); 
			
			String donhangmuon = util.antiSQLInspection(request.getParameter("donhangmuon"));
			if (donhangmuon == null)
				donhangmuon = "0";				
			lsxBean.setDonhangmuon(donhangmuon);
			
			String khKGId = util.antiSQLInspection(request.getParameter("khKGId"));
			if (khKGId == null)
				khKGId = "";				
			lsxBean.setKhachhangKGId(khKGId);
			
			String isKhuyenmai = util.antiSQLInspection(request.getParameter("isKhuyenmai"));
			if (isKhuyenmai == null)
				isKhuyenmai = "0";				
			lsxBean.setIsKhuyenmai(isKhuyenmai);
			
			String sohopdong = util.antiSQLInspection(request.getParameter("sohopdong"));
			if (sohopdong == null)
				sohopdong = "";				
			lsxBean.setSohopdong(sohopdong);
			
			String capdogiaohang = util.antiSQLInspection(request.getParameter("capdogiaohang"));
			if (capdogiaohang == null)
				capdogiaohang = "";				
			lsxBean.setCapdogiaohang(capdogiaohang);
			
			String maphieuMH = util.antiSQLInspection(request.getParameter("maphieuMH"));
			if (maphieuMH == null)
				maphieuMH = "";				
			lsxBean.setMaphieuMH(maphieuMH);
			
			String tratichluy = util.antiSQLInspection(request.getParameter("tratichluy"));
			if (tratichluy == null)
				tratichluy = "0";				
			lsxBean.setTratichluy(tratichluy);
			
			String tientichluy = util.antiSQLInspection(request.getParameter("tientichluy"));
			if (tientichluy == null)
				tientichluy = "0";				
			lsxBean.setTientichluy(tientichluy);
			
			String capnhatTDV = util.antiSQLInspection(request.getParameter("capnhatTDV"));
			if (capnhatTDV == null)
				capnhatTDV = "0";				
			lsxBean.setCapnhatTDV(capnhatTDV);
			
			String lydokhongduyet = util.antiSQLInspection(request.getParameter("capnhatTDV"));
			if (lydokhongduyet == null)
				lydokhongduyet = "";				
			lsxBean.setLydokhongduyet(lydokhongduyet);
			
			String[] spMa = request.getParameterValues("spMa");
			lsxBean.setSpMa(spMa);
			
			String[] spTen = request.getParameterValues("spTen");
			lsxBean.setSpTen(spTen);
			
			String[] dvt = request.getParameterValues("donvi");
			lsxBean.setSpDonvi(dvt);
			
			String[] soluong = request.getParameterValues("soluong");
			lsxBean.setSpSoluong(soluong);
			
			String[] soluongton = request.getParameterValues("soluongton");
			lsxBean.setSpSoluongton(soluongton);

			String[] dongia = request.getParameterValues("dongia");
			lsxBean.setSpGianhap(dongia);
			
			String[] dongiaGOC = request.getParameterValues("dongiaGOC");
			lsxBean.setSpGianhapGOC(dongiaGOC);
			
			String[] spQuyDoi = request.getParameterValues("spQuyDoi");
			lsxBean.setSpQuyDoi(spQuyDoi);
			
			String[] spChietkhau = request.getParameterValues("chietkhau");
			lsxBean.setSpChietkhau(spChietkhau);
			
			String[] spVat = request.getParameterValues("spvat");
			lsxBean.setSpVat(spVat);
			
			String[] trongluong = request.getParameterValues("spTrongLuong");
			lsxBean.setSpTrongluong(trongluong);
			
			String[] thetich = request.getParameterValues("spTheTich");
			lsxBean.setSpThetich(thetich);
			
			String[] spTungay = request.getParameterValues("tungay");
			lsxBean.setSpTungay(spTungay);
			
			String[] spDenngay = request.getParameterValues("denngay");
			lsxBean.setSpDenngay(spDenngay);
			
			String[] spScheme = request.getParameterValues("scheme");
			lsxBean.setSpScheme(spScheme);
			
			String[] ptchietkhauBHKM = request.getParameterValues("ptchietkhauBHKM"); 
			lsxBean.setSpChietkhauBHKM(ptchietkhauBHKM);
			
			String[] spTDV = request.getParameterValues("spTDV"); 
			lsxBean.setSpTDV(spTDV);
			
			if( capnhatTDV.equals("1") && spTDV != null && ddkdId != null )
			{
				for( int i = 0; i < spTDV.length; i++ )
					spTDV[i] = ddkdId;
			}
			
			String[] soluongDAGIAO = request.getParameterValues("soluongDAGIAO");
			lsxBean.setSpDagiao(soluongDAGIAO);
			
			//THEM CAC LOAI CHIET KHAU
			String[] dhCK_diengiai = request.getParameterValues("dhCK_diengiai");
			lsxBean.setDhck_Diengiai(dhCK_diengiai);
			String[] dhCK_giatri = request.getParameterValues("dhCK_giatri");
			lsxBean.setDhck_giatri(dhCK_giatri);
			String[] dhCK_loai = request.getParameterValues("dhCK_loai");
			lsxBean.setDhck_loai(dhCK_loai);
			
		    String action = request.getParameter("action");
			System.out.println(" action :" + action);

			if(action.equals("save"))
			{	
				if(id == null)
				{
					boolean kq = lsxBean.createNK();
					if(!kq)
					{
						lsxBean.createRs( session.getAttribute("tdv_dangnhap_id").toString() );
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpDonhangNppETCList obj = new ErpDonhangNppETCList();
						//obj.setLoaidonhang(loaidonhang);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    
						String msg = "Số đơn hàng bạn vừa lưu: " + lsxBean.getId();
						if( lsxBean.getMsg().trim().length() > 0 )
							msg += ", " + lsxBean.getMsg();
						obj.setMsg(msg);
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETC.jsp";	
			    		response.sendRedirect(nextJSP);*/
						
						id = lsxBean.getId();
					    lsxBean = new ErpDonhangNppETC(id);
					    lsxBean.setUserId(userId); 
					    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
					 
					    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
					    lsxBean.setDoituongId(session.getAttribute("doituongId"));
					    
						lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
						
						String msg = "Số đơn hàng bạn vừa lưu: " + id;
						if( lsxBean.getMsg().trim().length() > 0 )
							msg += ", " + lsxBean.getMsg();
						lsxBean.setMsg(msg);
						
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
			    		
				        session.setAttribute("lsxBean", lsxBean);
				        response.sendRedirect(nextJSP);
					}
				}
				else
				{
					boolean kq = lsxBean.updateNK("1");
					if(!kq)
					{
						lsxBean.createRs( session.getAttribute("tdv_dangnhap_id").toString() );
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpDonhangNppETCList obj = new ErpDonhangNppETCList();
						//obj.setLoaidonhang(loaidonhang);
						obj.setTdv_dangnhap_id(tdv_dangnhap_id);
						obj.setNpp_duocchon_id(npp_duocchon_id);
						
						obj.setLoainhanvien(session.getAttribute("loainhanvien"));
					    obj.setDoituongId(session.getAttribute("doituongId"));
					    
						String msg = "Số đơn hàng bạn vừa lưu: " + id;
						if( lsxBean.getMsg().trim().length() > 0 )
							msg += ", " + lsxBean.getMsg();
						obj.setMsg(msg);
						
						obj.setUserId(userId);
						obj.init("");  
				    	session.setAttribute("obj", obj);  	
			    		session.setAttribute("userId", userId);
			    		
			    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETC.jsp";	
			    		response.sendRedirect(nextJSP);*/
						
						lsxBean = new ErpDonhangNppETC(id);
					    lsxBean.setUserId(userId); 
					    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
					    
					    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
					    lsxBean.setDoituongId(session.getAttribute("doituongId"));
					    
						lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
						lsxBean.setMsg("Số đơn hàng bạn vừa lưu: " + id);
						
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
			    		
				        session.setAttribute("lsxBean", lsxBean);
				        response.sendRedirect(nextJSP);
					}
				}
			} 
			else if(action.equals("taomoi") )
			{
				lsxBean = new ErpDonhangNppETC();
		    	//lsxBean.setLoaidonhang(loaidonhang);
		    	lsxBean.setUserId(userId);
		    	lsxBean.setNpp_duocchon_id(npp_duocchon_id);

		    	lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
		    	lsxBean.setDoituongId(session.getAttribute("doituongId"));
		    	
		    	lsxBean.createRs( tdv_dangnhap_id );
		    	lsxBean.checkKSKD();
		    	
		    	session.setAttribute("dvkdId", "100001");
				session.setAttribute("kbhId", "");
				session.setAttribute("nppId", request.getParameter("nppId"));
				session.setAttribute("hopdongId", "");
				session.setAttribute("khoId", "");
	    		
		    	session.setAttribute("lsxBean", lsxBean);
		    	
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
	    		response.sendRedirect(nextJSP);
			}
			else if( action.equals("xoaSCHEME") )
			{
				String schemeXOA = request.getParameter("schemeXOA"); 
				
				lsxBean = new ErpDonhangNppETC(id);
			    lsxBean.setUserId(userId); 
			    lsxBean.setNpp_duocchon_id(npp_duocchon_id);
			 
			    lsxBean.setLoainhanvien(session.getAttribute("loainhanvien"));
			    lsxBean.setDoituongId(session.getAttribute("doituongId"));
			    
			    lsxBean.XoaKhuyenMai( schemeXOA );
				lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
				
				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
	    		
		        session.setAttribute("lsxBean", lsxBean);
		        response.sendRedirect(nextJSP);
			}
			else if ( action.equals("duyet") || action.equals("submitDUYET_CS") )
			{
				Hashtable<String, String> sanpham_soluong = new Hashtable<String, String>();
				for(int i = 0; i < spMa.length; i++ )
				{
					String temID = spMa[i] + "__" + spScheme[i];
					System.out.println("---SP MA LA: " + spMa[i] + "  - TEM ID: " + temID );
					
					String[] spSOLO = request.getParameterValues(temID + "_spSOLO");
					String[] soLUONGXUAT = request.getParameterValues(temID + "_spSOLUONG");
					
					String[] spNgayHetHan = request.getParameterValues(temID + "_spNGAYHETHAN");
					
					if(soLUONGXUAT != null)
					{
						for(int j = 0; j < soLUONGXUAT.length; j++ )
						{
							if(soLUONGXUAT[j] != null && soLUONGXUAT[j].trim().length() > 0)
							{
								System.out.println("---KEY SVL: " + ( spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j] )  + "   --- GIA TRI: " + soLUONGXUAT[j].replaceAll(",", "") );
								sanpham_soluong.put(spMa[i] + "__" + spScheme[i] + "__" + spSOLO[j]+ "__" + spNgayHetHan[j], soLUONGXUAT[j].replaceAll(",", "") );								
							}
						}
					}
				}

				lsxBean.setSanpham_Soluong(sanpham_soluong);
				
				String dungchungkenh = request.getParameter("dungchungkenh");
				if(dungchungkenh == null)
					dungchungkenh = "0";
				lsxBean.setDungchungKenh(dungchungkenh);
				
				String capduyet = util.antiSQLInspection(request.getParameter("capduyet"));
				if (capduyet == null)
					capduyet = "";				
				lsxBean.setCapduyet(capduyet);
				
				if( action.equals("submitDUYET_CS") )
				{
					lsxBean.ApTichLuy();
					lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
					lsxBean.setSanpham_Soluong(sanpham_soluong);
					session.setAttribute("lsxBean", lsxBean);
    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDuyetDisplay.jsp";
    				response.sendRedirect(nextJSP);
				}
				else
				{
					if(!lsxBean.duyetETC( session.getAttribute("congtyId").toString(), "" ))
					{
						lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
						lsxBean.setSanpham_Soluong(sanpham_soluong);
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDuyetDisplay.jsp";
	    				response.sendRedirect(nextJSP);
					}
					else
					{
						/*IErpDuyetddhNppList obj = new ErpDuyetddhNppList();
						obj.setUserId(userId);
						//obj.setLoaidonhang(loaidonhang);
						//obj.setPhanloai(loaidonhang);
						obj.setCapduyet(capduyet);
						
						obj.init("");
						session.setAttribute("obj", obj); 
						String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangNppDuyet.jsp" ;
						response.sendRedirect(nextJSP);*/
						
						String msg = "Số đơn hàng bạn vừa duyệt: " + id;
						lsxBean.setMsg(msg);
						
						lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
						lsxBean.setSanpham_Soluong(sanpham_soluong);
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCDisplay.jsp";
	    				response.sendRedirect(nextJSP);
					}
				}
			}
			else if(action.equals("apkhuyenmai"))
			{
				//Save donhang truoc -- AP CHIẾT KHẤU THEO CHÍNH SÁCH BÁN HÀNG CHỖ NÀY LUÔN
				
				if(id == null)
				{   
					boolean tao = lsxBean.createNK();
					if (!tao)
					{
						lsxBean.createRs( session.getAttribute("tdv_dangnhap_id").toString() );
	    		    	session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
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
					boolean temp = lsxBean.updateNK("1");
					if (!temp)
					{
						lsxBean.createRs( session.getAttribute("tdv_dangnhap_id").toString() );
						session.setAttribute("lsxBean", lsxBean);
	    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
	    				response.sendRedirect(nextJSP);
	    				
	    				return;
					}
				}
				
				//Nếu có CK trong DLN thi ko ap KM
				dbutils db = new dbutils();
				String query = "";
				if(loaidonhang.equals("1") || loaidonhang.equals("2") )
					query = "select ISNULL(PT_CHIETKHAU, 0) as PT_CHIETKHAU from KHACHHANG where pk_seq = '" + khId + "' ";
				else
					query = "select ISNULL(PT_CHIETKHAU, 0) as PT_CHIETKHAU from NHAPHANPHOI where pk_seq = '" + khId + "' ";
				
				float PT_CHIETKHAU = 0;
				ResultSet rsCK = db.get(query);
				if( rsCK != null )
				{
					try 
					{
						if( rsCK.next() )
						{
							PT_CHIETKHAU = rsCK.getFloat("PT_CHIETKHAU");
						}
						rsCK.close();
					} 
					catch (Exception e) { }
				}
				
				//TÍCH LŨY GIAI ĐOẠN VẪN ĐƯỢC GIỮ LẠI NẾU CÓ CHECK TRẢ TÍCH LŨY
				if( !( PT_CHIETKHAU > 0 || donhangmuon.equals("1") || loaidonhang.equals("0") ) )
				{
					lsxBean.ApTichLuy_TheoGiaiDoan(id, db, "0");
					//return;
				}
				
				if( loaidonhang.equals("0") || PT_CHIETKHAU > 0 || tratichluy.equals("1") || donhangmuon.equals("1") ) //Đơn hàng bán cho NPP, thì chỉ áp chiết khấu, không áp KM, mà để lại màn hình cập nhật
				{
					lsxBean.ApChietKhau(id, new geso.traphaco.distributor.db.sql.dbutils(), "1", "1" );
			    	lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
			    	if( lsxBean.getMsg().trim().length() <= 0 )
			    		lsxBean.setMsg("Số đơn hàng bạn vừa lưu: " + id);

			    	session.setAttribute("lsxBean", lsxBean);
			        String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
			        response.sendRedirect(nextJSP);
			        return;
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
					//dbutils db = new dbutils();
					
					//XOA HET KM CU NEU CO
					try 
					{
						db.getConnection().setAutoCommit(false);
						String msg = capNhatKM(id, nppId, khId, "0", db);
					
						//System.out.println("cau Lenh Cap Nhat KM Tra Ve: "+msg);
						if(msg.length() > 0)
						{
							//db.getConnection().rollback();
							
							lsxBean.createRs( session.getAttribute("tdv_dangnhap_id").toString() );
							lsxBean.setMsg("3.Khong The Cap Nhat Khuyen Mai Cua Don Hang: " + msg);
							session.setAttribute("lsxBean", lsxBean);
		    				String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
		    				response.sendRedirect(nextJSP);
		    				
							return;
						}
						else
						{
							db.getConnection().commit();
							db.getConnection().setAutoCommit(true);
						}
					} 
					catch (Exception e) 
					{
						try 
						{
							db.getConnection().rollback();
						}
						catch (Exception e1) {}
					}
					
					query = "  select ( select ma from SANPHAM where PK_SEQ = a.sanpham_fk ) as spMA, a.dvdl_fk, b.DVDL_FK as dvCHUAN,  " +
							" 		case when a.dvdl_fk = b.DVDL_FK then a.soluong  " +
							"  			 else  a.soluong * ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as soluong, " +
							"  		case when a.dvdl_fk = b.DVDL_FK then a.dongiaGOC  " +
							"  			 else  a.dongiaGOC / ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as dongia,  " +
							"  		case when a.dvdl_fk = b.DVDL_FK then 1  " +
							"  			 else  ( select SOLUONG1 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk )   end as quycach,  isnull( a.thueVAT, 0) as  thueVAT   " +
							"  from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ     " +
							"  where a.dondathang_fk = '" + id + "'  " ;
					ResultSet rsSP = db.get(query);
					
					if(rsSP != null)
					{
						try 
						{
							while(rsSP.next())
							{
								float dongiaSAUVAT = Math.round( rsSP.getFloat("dongia") * ( 1 + rsSP.getFloat("thueVAT") / 100 ) );
								
								sanpham_soluong.put(rsSP.getString("spMA"), rsSP.getFloat("soluong"));
								sanpham_dongia.put(rsSP.getString("spMA"), dongiaSAUVAT  );
								sanpham_quycach.put(rsSP.getString("spMA"), rsSP.getFloat("quycach"));
								
								spMaKM += rsSP.getString("spMA") + "__";
								spSOLUONGKM += rsSP.getInt("soluong") + "__";
								spDONGIAKM += dongiaSAUVAT + "__";
								
								soluong1 += rsSP.getString("quycach") + "__";
								tongiatriDH += rsSP.getInt("soluong") * dongiaSAUVAT;
							}
							rsSP.close();
						} 
						catch (Exception e) {}	
					}
					
					db.shutDown();
				}
				
				
				XLkhuyenmaiTT xlkm = new XLkhuyenmaiTT(userId, tungay, nppId, khId, id); //ngay giao dich trong donhang
				
				xlkm.setMasp(spMaKM.substring(0, spMaKM.length() - 2).split("__"));
				xlkm.setSoluong(spSOLUONGKM.substring(0, spSOLUONGKM.length() - 2).split("__"));
				xlkm.setDongia(spDONGIAKM.substring(0, spDONGIAKM.length() - 2).split("__"));
				xlkm.setQuycach(soluong1.substring(0, soluong1.length() - 2).split("__"));
				
				xlkm.setTonggiatriDh(tongiatriDH);
				xlkm.setIdDonhang(id);
				xlkm.setNgaygiaodich(tungay);
				xlkm.setLoaiDonHang("0");
				
				xlkm.setHashA(sanpham_soluong);
				xlkm.setHashB(sanpham_dongia);
				xlkm.setHash_QuyCach(sanpham_quycach);
				
				xlkm.setDieuchinh(false); //Lay truong hop ngau nhien /*****OneOne set lai la True******/
				
				xlkm.ApKhuyenMai();
				
			    List<ICtkhuyenmai> ctkmResual = xlkm.getCtkmResual();
			    System.out.println("+++So xuat khuyen mai duoc huong: " + ctkmResual.size() + "\n");
			    
			    if(xlkm.checkConfirm()) //bi dung --> sang trang lua chon
			    {
			    	//IN THU
			    	for( int i = 0; i < xlkm.getCtkmResual().size(); i++  )
			    	{
			    		System.out.println("::::: SCHEME: " + xlkm.getCtkmResual().get(i).getscheme() + " -- DUNG VOI NO: " + xlkm.getCtkmResual().get(i).getSchemeDungChung() );
			    	}
			    	
			    	System.out.println("Bi dung san pham roi...\n");
			    	session.setAttribute("xlkm", xlkm);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMaiNppTT.jsp";
					response.sendRedirect(nextJSP);
					return;
			    }
			    
			    String msg = ""; //nhung ctkm khong thoa
				for(int i = 0; i < ctkmResual.size(); i++)
			    {
			    	ICtkhuyenmai ctkhuyenmai = ctkmResual.get(i);
			    	
			    	//System.out.println("ConFi laf: "+ctkhuyenmai.getConfirm());
			    	if(ctkhuyenmai.getConfirm() == false) //khong dung chung san pham
			    	{	
			    		msg += CreateKhuyenmai(ctkhuyenmai, id, nppId, tungay, Math.round(tongiatriDH), sanpham_soluong, sanpham_dongia);
			    		
				    	//remove khoi danh sach
			    		ctkmResual.remove(i);	
			    		i = i -1;
			    	}
			    }
				
				if(msg.length() > 0)
	    		{
	    			lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
					
					xlkm.DBclose();
	    			lsxBean.setMsg("khong the them moi 'dondathang_ctkm_trakm' " + msg);
			    	session.setAttribute("lsxBean", lsxBean);
			        String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
			        response.sendRedirect(nextJSP);
			        return;
	    		}
			    
			    String nextJSP = "";
			    
			    if(ctkmResual.size() > 0)
			    {
					session.setAttribute("xlkm", xlkm);
					nextJSP = "/TraphacoHYERP/pages/Distributor/KhuyenMaiNppTT.jsp";
					response.sendRedirect(nextJSP);
			    }
			    else
			    {	
			    	xlkm.DBclose();
			    	lsxBean.ApChietKhau(id, new geso.traphaco.distributor.db.sql.dbutils(), "1", "1" );
			    	lsxBean.init( session.getAttribute("tdv_dangnhap_id").toString() );
			    	if( lsxBean.getMsg().trim().length() <= 0 )
			    		lsxBean.setMsg("Số đơn hàng bạn vừa lưu: " + id);
			    	
			    	session.setAttribute("lsxBean", lsxBean);
			        nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
			        response.sendRedirect(nextJSP);
			        return;
			    }
			}
			else
			{
				if(action.equals("changeLOAIDONHANG"))
				{
					lsxBean.setKbhId("");
					//lsxBean.setKhId("");
					
					//RESET lai danh sach san pham
					lsxBean.setSpMa(null);
					lsxBean.setSpSoluong(null);
					lsxBean.setSpGianhap(null);
				}
				else if(action.equals("changeHOPDONG"))
				{
					//RESET lai danh sach san pham
					lsxBean.setSpMa(null);
					lsxBean.setSpSoluong(null);
					lsxBean.setSpGianhap(null);
				}
				else if(action.equals("changeTDV"))
				{
					lsxBean.setKhId("");
				}
				
				System.out.println(":::: KHACH HANG ID: " + khId + " -- HOP DONG ID: " + mahopdong + " -- KH ID: " + lsxBean.getKhId() );
				lsxBean.createRs( session.getAttribute("tdv_dangnhap_id").toString() );
				
				session.setAttribute("dvkdId", lsxBean.getDvkdId());
				session.setAttribute("kbhId", lsxBean.getKbhId() );
				session.setAttribute("khoId", lsxBean.getKhoNhapId());
				session.setAttribute("nppId", request.getParameter("nppId"));
				session.setAttribute("khId", khId);
				session.setAttribute("hopdongId", mahopdong);
				session.setAttribute("loaidonhang", loaidonhang);
				
				session.setAttribute("lsxBean", lsxBean);
				
				String nextJSP = "";
				nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCNew.jsp";
				if(id != null)
				{
					nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonHangNppETCUpdate.jsp";
				}
				
				response.sendRedirect(nextJSP);
			}
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
					if(trakm.getHinhthuc() == 1)
					{
						/*String sql = "select f.pk_seq as spId, a.soluong, e.GIAMUANPP as dongia, f.ma as spMa  " +
									"from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
									"	inner join BGMUANPP_SANPHAM e on a.sanpham_fk = e.SANPHAM_FK " +
									"where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' " +
										"and e.BGMUANPP_FK in ( select top(1) a.PK_SEQ " +
															  "from BANGGIAMUANPP a inner join BANGGIAMUANPP_NPP b on a.PK_SEQ = b.BANGGIAMUANPP_FK  " +
															  "where a.TUNGAY <= '" + tungay + "' and b.NPP_FK = '" + nppId + "' and a.KENH_FK = ( select KBH_FK from ERP_DONDATHANG where PK_SEQ = '" + id + "' ) and a.DVKD_FK = ( select DVKD_FK from ERP_DONDATHANG where PK_SEQ = '" + id + "' ) " +
															  "order by a.TUNGAY desc  ) -- and e.GIAMUANPP > 0  and e.trangthai = '1'  ";*/
						
						String sql = "";
						
						String spKhuyenmaiDacbiet = "";
						String ctkmId = "";
						if( ctkm.getId().contains("_") )
						{
							String[] arr = ctkm.getId().split("_");
							ctkmId = arr[0];
							spKhuyenmaiDacbiet = arr[1];
						}
						else
							ctkmId = ctkm.getId();
						
						
						if( spKhuyenmaiDacbiet.trim().length() <= 0 )
						{
							sql = "select f.pk_seq as spId, a.soluong, ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.sanpham_fk and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia, f.ma as spMa  " +
								  "from Trakhuyenmai_sanpham a inner join SANPHAM f on a.SANPHAM_FK = f.PK_SEQ " +
								  "where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' ";
						}
						else
						{
							sql = "select f.pk_seq as spId, a.soluong, ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.sanpham_fk and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia, f.ma as spMa  " +
								  "from Trakhuyenmai_sanpham a, SANPHAM f  " +
								   "where a.TRAKHUYENMAI_FK = '" + trakm.getId() + "' and f.pk_seq = '" + spKhuyenmaiDacbiet + "' ";
						}
						
						//System.out.println("Query lay gia san pham co dinh la: " + sql + "\n");
						
						int index = 0;
						ResultSet rsSQl = db.get(sql);
						if(rsSQl != null)
						{
							while(rsSQl.next())
							{
								int slg = Integer.parseInt(rsSQl.getString("soluong")) * (int)ctkm.getSoxuatKM();
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
								//String query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tongtienTraKM) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								String query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) " + 
											   " values('" + id + "','" + ctkmId + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + Long.toString(tonggtri) + "', '" + rsSQl.getString("spMa").trim() + "', '" + Integer.toString(slg) + "')";
								System.out.println("1.Chen khuyen mai co dinh: " + query);
								
								if(db.updateReturnInt(query) < 1 )
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								//cap nhat kho
								/*query = "Update nhapp_kho set available = available - '" + Integer.toString(slg) + "', booked = booked + '" + Integer.toString(slg) + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + rsSQl.getString("spId") + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
								System.out.println("2.Cap nhat kho: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
							
								query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + Long.toString(tongtienTraKM) + "' where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
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
							
							/*String query = "select a.sanpham_fk as spId, c.MA as spMa, isnull(bgmnpp.dongia, '0') as dongia, isnull(b.TONGLUONG, 0) as tongluong " +
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
											"order by a.thutuuutien asc";*/
							
							String query = "select a.sanpham_fk as spId, c.MA as spMa, ISNULL ( ( select dongia from BANGGIABANLENPP_SANPHAM where sanpham_fk = a.sanpham_fk and BANGGIABLNPP_FK in ( select pk_seq from BANGGIABANLENPP where DVKD_FK = '100001' and pk_seq in ( select BANGGIABLNPP_FK from BANGGIABANLENPP_NPP where NPP_FK = '" + nppId +  "' ) ) ), 0) as dongia, isnull(b.TONGLUONG, 0) as tongluong " +
									"from TRAKM_NHAPP a inner join TRAKHUYENMAI b on a.trakm_fk = b.PK_SEQ " +
									" inner join SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
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
								query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri, spMa, soluong) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + arr[2].replaceAll(",", "") + "', '" + arr[3] + "', '" + arr[1].replaceAll(",", "") + "')";
								System.out.println("6.Chen khuyen mai Npp chon truoc: " + query);
								
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								//cap nhat kho
								/*query = "Update nhapp_kho set available = available - '" + arr[1].replaceAll(",", "") + "', booked = booked + '" + arr[1].replaceAll(",", "") + "' where kho_fk = (select kho_fk from ctkhuyenmai where pk_seq = '" + ctkm.getId() + "') and npp_fk = '" + nppId + "' and sanpham_fk = '" + arr[0] + "' and kbh_fk = (select kbh_fk from khachhang where pk_seq = '" + khId+ "')";   							
								System.out.println("7.Cap nhat npp_kho: " + query);
								if(!db.update(query))
								{
									db.getConnection().rollback();
									str = query;
									return str;
								}
								
								query = "update Phanbokhuyenmai set DASUDUNG = DASUDUNG + '" + arr[2].replaceAll(",", "") + "' where ctkm_fk = '" + ctkm.getId() + "' and npp_fk = '" + nppId + "'";
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
						String query = "Insert into ERP_DONDATHANGNPP_CTKM_TRAKM(DONDATHANGID, ctkmId, trakmId, soxuat, tonggiatri) values('" + id + "','" + ctkm.getId() + "','" + trakm.getId() + "','" + ctkm.getSoxuatKM() + "','" + tongtienTraKM + "')";
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
			
			//KHÔNG ĐỤNG SẢN PHẨM NÊN TẤT CẢ SP TRONG ĐƠN HÀNG SẼ ĐƯỢC DÙNG TRONG ĐIỀU KIỆN
			String query = "";
			
			if( !ctkm.getId().contains("_") )
			{
				query =  " insert ERP_DONDATHANGNPP_CTKM_SUDUNG(dondathangId, ctkmId, dkkmId, spMa, sudung, sanpham_fk  ) "+
						 " select b.dondathang_fk, '" + ctkm.getId() + "' as ctkmId, a.DIEUKIENKHUYENMAI_FK, c.MA, b.soluong, b.sanpham_fk "+
						 " from DIEUKIENKM_SANPHAM a inner join ERP_DONDATHANGNPP_SANPHAM b on a.SANPHAM_FK = b.sanpham_fk"+
						 " 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ"+
						 " where b.dondathang_fk = '" + id + "' and a.DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkm.getId() + "' )";
				
				System.out.println(":::: Chen so su dung KMBT: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					str = query;
					return str;
				}
			}
			else
			{
				String[] arr = ctkm.getId().split("_");
				String ctkmId = arr[0];
				String spKhuyenmaiDacbiet = arr[1];
				
				query =  " insert ERP_DONDATHANGNPP_CTKM_SUDUNG(dondathangId, ctkmId, dkkmId, spMa, sudung, sanpham_fk  ) "+
						 " select b.dondathang_fk, '" + ctkmId + "' as ctkmId, a.DIEUKIENKHUYENMAI_FK, c.MA, b.soluong, b.sanpham_fk "+
						 " from DIEUKIENKM_SANPHAM a inner join ERP_DONDATHANGNPP_SANPHAM b on a.SANPHAM_FK = b.sanpham_fk"+
						 " 		inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ"+
						 " where b.dondathang_fk = '" + id + "' and c.pk_seq = '" + spKhuyenmaiDacbiet + "' and a.DIEUKIENKHUYENMAI_FK in ( select DKKHUYENMAI_FK from CTKM_DKKM where CTKHUYENMAI_FK = '" + ctkmId + "' )";
				
				System.out.println(":::: Chen so su dung KM DAC BIET: " + query);
				if(!db.update(query))
				{
					db.getConnection().rollback();
					str = query;
					return str;
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
			//db.shutDown();
		}
		
		return str;
	}
	
	private String[] checkTonKhoTraKm(String nppId, ICtkhuyenmai ctkm, String[] spId, String[] dongia, String[] tongluong, String[] spma) 
	{
		String[] kq = new String[4];
		
		String msg = "";
		try
		{
			for(int i = 0; i < spId.length; i++)
			{
				int slg = Integer.parseInt(tongluong[i]) * ctkm.getSoxuatKM();
				msg = "";
				if(msg == "")  //thoa ton kho
				{
					kq[0] = spId[i];
					kq[1] = Integer.toString(slg);
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

	private String  capNhatKM(String id, String nppId, String khId, String trangthai, dbutils db)
	{
		//delete neu ton tai, cap nhat lai kho voi so luong tang
		String query = "delete from ERP_DONDATHANGNPP_CTKM_TRAKM where dondathangid = '" + id + "' and tichluyGD = 0 ";
		if(!db.update(query))
		{
			System.out.println("Error :"+ query);
			return "Error :"+ query;
		}
		
		query = "delete ERP_DONDATHANGNPP_CTKM_SUDUNG where dondathangid = '" + id + "' ";
		if(!db.update(query))
		{
			System.out.println("Error :"+ query);
			return "Error :"+ query;
		}
		
		//CẬP NHẬT TOOLTIP
		db.execProceduce2("CapNhatTooltip", new String[] { id } );
		
		//ben nay chu book kho khi lam don hang
		return "";	
	
	}


	private void Create_DH_PDF(HttpServletResponse response, HttpServletRequest request, IErpDonhangNppETC lsxBean, String isMTV) 
	{
		response.setContentType("application/pdf");
		response.setHeader("Content-Disposition"," inline; filename=PhuLucHopDong.pdf");
		
		float CONVERT = 28.346457f;
		float PAGE_LEFT = 2.0f*CONVERT, PAGE_RIGHT = 1.5f*CONVERT, PAGE_TOP = 1.5f*CONVERT, PAGE_BOTTOM = 0.0f*CONVERT; //cm
		Document document = new Document(PageSize.A4, PAGE_LEFT, PAGE_RIGHT, PAGE_TOP, PAGE_BOTTOM);
		ServletOutputStream outstream;
		try 
		{
			outstream = response.getOutputStream();
			
			String id = request.getParameter("id");
			dbutils db = new dbutils();
			
			if(!isMTV.trim().equals("0"))
			{
				this.CreateDH(document, outstream, response, request, db, lsxBean);
			}
			else
				this.CreateDH2(document, outstream, response, request, db, lsxBean);
				
			//db.update("Update ERP_MuaHang set DaInPdf = 1 where pk_seq = '" + id + "'");
			db.shutDown();
			
		} 
		catch (IOException e) 
		{
			e.printStackTrace();
			System.out.println("___Exception PO Print: " + e.getMessage());
		}
	}
	
	private void CreateDH(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db, IErpDonhangNppETC lsxBean) 
	{
		HttpSession session = request.getSession();
		
		String id = request.getParameter("id");
		String ctyId = (String)session.getAttribute("congtyId");
		
		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###"); 
			NumberFormat formatter_2sole = new DecimalFormat("#,###,###.##"); 
			
			PdfWriter.getInstance(document, outstream);
			document.open();
			//document.setPageSize(new Rectangle(210.0f, 297.0f));

			float CONVERT = 28.346457f; // = 1cm
			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\times.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font9bold = new Font(bf, 9, Font.BOLD);
			Font font10bold = new Font(bf, 10, Font.BOLD);
			Font font11bold = new Font(bf, 11, Font.BOLD);
			Font font12bold = new Font(bf, 12, Font.BOLD);
			Font font13bold = new Font(bf, 13, Font.BOLD);
			Font font14bold = new Font(bf, 14, Font.BOLD);
			Font font15bold = new Font(bf, 15, Font.BOLD);
			Font font16bold = new Font(bf, 16, Font.BOLD);
			Font font20bold = new Font(bf, 20, Font.BOLD);
			Font font6green = new Font(bf, 6, Font.NORMAL, BaseColor.GREEN);
			Font font8 = new Font(bf, 8, Font.NORMAL);
			Font font9 = new Font(bf, 9, Font.NORMAL);
			Font font10 = new Font(bf, 10, Font.NORMAL);
			Font font11 = new Font(bf, 11, Font.NORMAL);
			Font font12 = new Font(bf, 12, Font.NORMAL);
			Font font13 = new Font(bf, 13, Font.NORMAL);
			Font font14 = new Font(bf, 14, Font.NORMAL);
			Font font16 = new Font(bf, 16, Font.NORMAL);
			Font font8italic = new Font(bf, 8, Font.ITALIC);
			Font font10italic = new Font(bf, 10, Font.ITALIC);
			Font font11italic = new Font(bf, 11, Font.ITALIC);
			Font font12italic = new Font(bf, 12, Font.ITALIC);
			Font font14italic = new Font(bf, 14, Font.ITALIC);
			Font font11boldItalic = new Font(bf, 11, Font.BOLDITALIC);
			Font font12boldItalic = new Font(bf, 12, Font.BOLDITALIC);
			Font font11underline = new Font(bf, 11, Font.UNDERLINE);
			
			/********************** INFO CONGTY *******************************/
			
			PdfPTable tbHeader = new PdfPTable(1);
			tbHeader.setWidthPercentage(100);
			tbHeader.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbHeader.setSpacingBefore(20f);
			tbHeader.getDefaultCell().setBorder(0);
			
			PdfPCell cell = new PdfPCell(new Paragraph("PHỤ LỤC HỢP ĐỒNG", font16bold));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbHeader.addCell(cell);
					
			cell = new PdfPCell(new Paragraph("Số: " + lsxBean.getSohopdong(), font14));
			cell.setPaddingLeft(5.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbHeader.addCell(cell);
			
			document.add(tbHeader);
			
			/********************** END INFO CONGTY *******************************/
			String query = "select a.TEN, b.diachi, b.dienthoai, b.fax, c.TEN as nganhang, b.sotaikhoan, b.MASOTHUE, b.giamdoc "
					+ "from NHAPHANPHOI a inner join ERP_CONGTY b on a.congty_fk = b.PK_SEQ "
					+ "left join ERP_NGANHANG c on b.NganHang_FK = c.PK_SEQ " 
					+ " where a.pk_seq = '" + lsxBean.getNppId() + "' " ;
			
			System.out.println("thong tin npp : " + query);
			String tennppdat = "", diachi = "", dienthoai = "", fax = "", nganhang = "", sotaikhoan = "", masothue = "", giamdoc = "";
			ResultSet rs = db.get(query);
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						tennppdat = rs.getString("ten");
						diachi = rs.getString("diachi")==null?"":rs.getString("diachi");
						dienthoai = rs.getString("dienthoai")==null?"":rs.getString("dienthoai");
						fax = rs.getString("fax")==null?"":rs.getString("fax");
						nganhang = rs.getString("nganhang")==null?"":rs.getString("nganhang");
						sotaikhoan = rs.getString("sotaikhoan")==null?"":rs.getString("sotaikhoan");
						masothue = rs.getString("MASOTHUE")==null?"":rs.getString("MASOTHUE");
						giamdoc = rs.getString("giamdoc")==null?"":rs.getString("giamdoc");
					}
					rs.close();
				}
				catch (Exception e)
				{
					System.out.println("__Exception: " + e.getMessage());
				}
			}
			
			PdfPTable tbA = new PdfPTable(2);
			tbA.setWidthPercentage(100);
			tbA.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbA.getDefaultCell().setBorder(0);
			tbA.setSpacingBefore(5f);
			float[] crA = {50f, 50f};
			tbA.setWidths(crA);
			
			cell = new PdfPCell(new Phrase("BÊN A (BÊN BÁN): " + lsxBean.getNppTen(), font14bold));
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			tbA.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Địa chỉ: "+diachi, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			tbA.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Điện thoại: "+dienthoai, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbA.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Fax: "+fax, font14));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbA.addCell(cell);

			cell = new PdfPCell(new Phrase("Mã số thuế: "+masothue, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			tbA.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Tài khoản: "+sotaikhoan, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbA.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Tại: "+nganhang, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbA.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Do Ông/ Bà: "+giamdoc, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbA.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Chức vụ: Tổng Giám đốc, làm đại diện", font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbA.addCell(cell);
			
			document.add(tbA);
			
			PdfPTable tbB = new PdfPTable(2);
			tbB.setWidthPercentage(100);
			tbB.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbB.getDefaultCell().setBorder(0);
			tbB.setSpacingBefore(5f);
			float[] crB = {50f, 50f};
			tbA.setWidths(crB);
			
			query = " select a.TEN, b.diachi, b.dienthoai, b.fax, c.TEN as nganhang, b.sotaikhoan, b.MASOTHUE, b.giamdoc "
					+ "from NHAPHANPHOI a inner join ERP_CONGTY b on a.congty_fk = b.PK_SEQ "
					+ "left join ERP_NGANHANG c on b.NganHang_FK = c.PK_SEQ " 
					+ " where a.pk_seq = '" + lsxBean.getKhId() + "' " ;
			
			System.out.println("thong tin npp dat : " + query);
			tennppdat = ""; diachi = ""; dienthoai = ""; fax = ""; nganhang = ""; sotaikhoan = ""; masothue = ""; giamdoc = "";
			rs = db.get(query);
			if (rs != null)
			{
				try
				{
					while (rs.next())
					{
						tennppdat = rs.getString("ten");
						diachi = rs.getString("diachi")==null?"":rs.getString("diachi");
						dienthoai = rs.getString("dienthoai")==null?"":rs.getString("dienthoai");
						fax = rs.getString("fax")==null?"":rs.getString("fax");
						nganhang = rs.getString("nganhang")==null?"":rs.getString("nganhang");
						sotaikhoan = rs.getString("sotaikhoan")==null?"":rs.getString("sotaikhoan");
						masothue = rs.getString("MASOTHUE")==null?"":rs.getString("MASOTHUE");
						giamdoc = rs.getString("giamdoc")==null?"":rs.getString("giamdoc");
					}
					rs.close();
				}
				catch (Exception e)
				{
					System.out.println("__Exception: " + e.getMessage());
				}
			}
		
			cell = new PdfPCell(new Phrase("BÊN B (BÊN MUA): "+tennppdat, font14bold));
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			tbB.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Địa chỉ: "+diachi, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			tbB.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Điện thoại: "+dienthoai, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbB.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Fax: "+fax, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbB.addCell(cell);

			cell = new PdfPCell(new Phrase("Mã số thuế: "+masothue, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setColspan(2);
			tbB.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Tài khoản: "+sotaikhoan, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbB.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Tại: "+nganhang, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbB.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Do Ông/ Bà: "+giamdoc, font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbB.addCell(cell);
			
			cell = new PdfPCell(new Phrase("Chức vụ: Tổng Giám đốc, làm đại diện", font12));
			cell.setPaddingLeft(20.0f);
			cell.setBorderWidth(0);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			tbB.addCell(cell);
			
			document.add(tbB);
			
			/********************** INFO DENGHI *******************************/
			
			PdfPTable tbThongtin = new PdfPTable(7);
			tbThongtin.setWidthPercentage(100);
			tbThongtin.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbThongtin.getDefaultCell().setBorder(1);
			tbThongtin.setSpacingBefore(5f);
			float[] crThongtin = {10f, 40f, 10f, 15f, 20f, 20f, 15f};
			tbThongtin.setWidths(crThongtin);
			
			cell = new PdfPCell(new Paragraph("STT", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("TÊN THUỐC", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("ĐVT", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("SỐ LƯỢNG", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("ĐƠN GIÁ (CHƯA CÓ VAT)", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("THÀNH TIỀN", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("GHI CHÚ", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setVerticalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			query =  "select b.MA, isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, a.soluong, a.dongia, a.dongiaGOC, isnull(a.chietkhau, 0) as chietkhau, " +
					 "ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, isnull(a.tungay, '') as tungay, isnull(a.denngay, '') as denngay, " +
					 "a.thueVAT, (select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018) as spQuyDoi, " +
					 "a.ddkd_fk, round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH "+
					 "from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					 "INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK " +
					 "where a.DONDATHANG_FK = '" + id + "' ";
			
			System.out.println(" San pham init: " + query);
			ResultSet spRs = db.get(query);
			String spten = "", donvi = "", soluong = "", dongiagoc = "";
			double sl = 0, dggoc = 0, dongia = 0, ttgoc = 0, thanhtien = 0, tongtienbvat = 0, tongtienavat = 0, tienvat = 0;
			NumberFormat formatter2 = new DecimalFormat("#,###,###.###"); 
			if (spRs != null)
			{
				try
				{
					int i = 1;
					while (spRs.next())
					{
						cell = new PdfPCell(new Paragraph(""+i, font12));
						cell.setPaddingLeft(5.0f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbThongtin.addCell(cell);
						
						spten = spRs.getString("TEN");
						cell = new PdfPCell(new Paragraph(spten, font12));
						cell.setPaddingLeft(5.0f);
						cell.setHorizontalAlignment(Element.ALIGN_LEFT);
						tbThongtin.addCell(cell);
						
						donvi = spRs.getString("donvi");
						cell = new PdfPCell(new Paragraph(donvi, font12));
						cell.setPaddingLeft(5.0f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbThongtin.addCell(cell);
						
						sl = spRs.getDouble("soluong");
						soluong = formatter.format(sl);
						cell = new PdfPCell(new Paragraph(soluong, font12));
						cell.setPaddingLeft(5.0f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbThongtin.addCell(cell);
						
						dggoc = spRs.getDouble("dongiaGOC");
						dongia = spRs.getDouble("dongia");
						dongiagoc = formatter.format(dggoc);
						cell = new PdfPCell(new Paragraph(dongiagoc, font12));
						cell.setPaddingLeft(5.0f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbThongtin.addCell(cell);
						
						thanhtien = sl * dongia;
						ttgoc = sl * dggoc;
						tongtienbvat += thanhtien;
						cell = new PdfPCell(new Paragraph(formatter.format(ttgoc), font12));
						cell.setPaddingLeft(5.0f);
						cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
						tbThongtin.addCell(cell);
						
						tienvat = tienvat + (thanhtien * spRs.getDouble("thueVat") / 100);
						cell = new PdfPCell(new Paragraph(spRs.getString("THUEVAT"), font12));
						cell.setPaddingLeft(5.0f);
						cell.setHorizontalAlignment(Element.ALIGN_CENTER);
						tbThongtin.addCell(cell);
						
						i++;
					}
					spRs.close();
				}
				catch (Exception e)
				{
					e.printStackTrace();
					System.out.println("Khong the tao san Pham " + e.getMessage());
				}
			}
			
			cell = new PdfPCell(new Paragraph("", font12));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(5);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(formatter.format(tongtienbvat), font12));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font12));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("VAT", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(5);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph(formatter.format(tienvat), font12));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font12));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("TỔNG CỘNG", font12bold));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			cell.setColspan(5);
			tbThongtin.addCell(cell);
			
			tongtienavat = tongtienbvat + tienvat;
			cell = new PdfPCell(new Paragraph(formatter.format(tongtienavat), font12));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			tbThongtin.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("", font12));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_CENTER);
			tbThongtin.addCell(cell);
			
			document.add(tbThongtin);
			
			/********************** END INFO DENGHI *******************************/
			
			/********************** INFO NGUOI DENGHI, DUYET *******************************/
			
			Paragraph p = new Paragraph("- Phụ lục hợp đồng này là một phần không thể thiếu của Hợp đồng nguyên tắc số 05/HĐNT2013/PN", font12);
			p.setSpacingBefore(5f);
			document.add(p);
			
			p = new Paragraph("- Phụ lục hợp đồng này lập thành hai (02) bản, mỗi bên giữ một (01) bản có giá trị như nhau.", font12);
			p.setSpacingBefore(5f);
			document.add(p);
			
			String[] s = getDateTime().split("-");
			
			PdfPTable tbFooter = new PdfPTable(2);
			tbFooter.setWidthPercentage(100);
			tbFooter.setHorizontalAlignment(Element.ALIGN_CENTER);
			float[] crFooter = {50f, 50f};
			tbFooter.setWidths(crFooter);
			tbFooter.getDefaultCell().setBorder(0);
			tbFooter.setSpacingBefore(5f);
			
						
			cell = new PdfPCell(new Paragraph("TP.HCM, ngày "+s[2]+" tháng "+s[1]+" năm "+s[0], font12italic));
			cell.setPaddingLeft(5.0f);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(0);
			cell.setColspan(2);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("BÊN A", font12bold));
			cell.setPaddingLeft(60.0f);
			cell.setHorizontalAlignment(Element.ALIGN_LEFT);
			cell.setBorderWidth(0);
			tbFooter.addCell(cell);
			
			cell = new PdfPCell(new Paragraph("BÊN B", font12bold));
			cell.setPaddingRight(60.0f);
			cell.setHorizontalAlignment(Element.ALIGN_RIGHT);
			cell.setBorderWidth(0);
			tbFooter.addCell(cell);
			
			document.add(tbFooter);
			
			/********************** END INFO NGUOI DENGHI, DUYET *******************************/
			document.close(); 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception In PDF: " + e.getMessage());
		}
		
	}
	
	private void CreateDH2(Document document, ServletOutputStream outstream, HttpServletResponse response, HttpServletRequest request, dbutils db, IErpDonhangNppETC lsxBean)
	{
		Utility util = new Utility();

		NumberFormat formatter2 = new DecimalFormat("#,###,###");
		try
		{

			BaseFont bf = BaseFont.createFont("c:\\windows\\fonts\\arial.TTF", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
			Font font = new Font(bf, 15, Font.BOLD);
			Font font2 = new Font(bf, 9, Font.BOLD);
			Font font8normal = new Font(bf, 9, Font.NORMAL);
			Font font8bold = new Font(bf, 9, Font.BOLD);
			Font font4 = new Font(bf, 7, Font.BOLD);
			Font font4normal = new Font(bf, 7, Font.NORMAL);
			PdfWriter.getInstance(document, outstream);
			document.open();
			
			String tencongty = "";
			String diachi = "", dienthoainpp = "", fax="";
			String query = "select ten, diachi, dienthoai, fax from nhaphanphoi where pk_seq = " + lsxBean.getNppId();
			System.out.println("::: THONG TIN NPP: " + query);
			ResultSet rs = db.get(query);
			try 
			{
				rs.next();
				tencongty = rs.getString("ten");
				diachi = rs.getString("diachi");
				dienthoainpp = rs.getString("dienthoai");
				fax = rs.getString("fax");
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			query = " select b.ten, c.ten as nguoitao, a.machungtu, CONVERT(VARCHAR(20), a.Created_Date, 120) as ngaylap " + 
					" from ERP_DONDATHANGNPP a inner join KHO b on a.Kho_FK = b.pk_seq inner join NHANVIEN c on a.nguoitao = c.pk_seq  where a.PK_SEQ = " + lsxBean.getId();
			ResultSet khors = db.get(query);
			
			String khohanghoa = "";
			String nguoilap = "";
			String machungtu = "";
			String ngaylap = "";
			try 
			{
				if(khors.next())
				{
					khohanghoa = khors.getString("ten");
					nguoilap = khors.getString("nguoitao");
					machungtu = khors.getString("machungtu");
					ngaylap = khors.getString("ngaylap");
				}
				khors.close();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}

			Paragraph tieude1 = new Paragraph(tencongty, font4);
			tieude1.setIndentationRight(0);
			document.add(tieude1);

			Paragraph tieude2 = new Paragraph("Kho " + khohanghoa, font4normal);
			tieude2.setIndentationRight(0);
			document.add(tieude2);

			Paragraph tieude3 = new Paragraph(diachi.trim(), font4normal);
			tieude3.setIndentationRight(0);
			document.add(tieude3);
			
			/*Paragraph tieude4 = new Paragraph("Tel: " + dienthoainpp.trim() + " - Fax: " + fax, font4normal);
			tieude4.setIndentationRight(0);
			document.add(tieude4);*/

			Paragraph tieudebm = new Paragraph("BM-20-1" , font4normal);
			tieudebm.setIndentationLeft(10);			
			tieudebm.setAlignment(Element.ALIGN_RIGHT);
			document.add(tieudebm);
			String str_tieude = "ĐƠN ĐẶT HÀNG";
			Paragraph tieude = new Paragraph(str_tieude, font);
			tieude.setAlignment(Element.ALIGN_CENTER);
			tieude.setSpacingAfter(10);
			document.add(tieude);


			String makh = "",tenkh = "",diachikh = "",nvbh = "",ghichu = "",dienthoai = "",nguoilienhe ="";
			query = "select b.ma as makh,b.NGUOI_LIENHE_DH, b.TEN, isnull(b.DIACHI,'') as diachi,c.ten as nvbh,isnull(a.GHICHU,'') as ghichu, isnull(b.DIENTHOAI,'') as dienthoai "+
					"from ERP_DONDATHANGNPP a inner join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ inner join DAIDIENKINHDOANH c on a.DDKD_FK = c.PK_SEQ "+
					" where a.PK_SEQ = " + lsxBean.getId() ;
			ResultSet ttkhrs = db.get(query);
			try 
			{
				if(ttkhrs.next())
				{
					makh = ttkhrs.getString("makh");
					tenkh = ttkhrs.getString("ten");
					diachi = ttkhrs.getString("DIACHI");
					nvbh = ttkhrs.getString("nvbh");
					ghichu = ttkhrs.getString("ghichu");
					dienthoai = ttkhrs.getString("dienthoai");
					nguoilienhe = ttkhrs.getString("NGUOI_LIENHE_DH");
				}
				ttkhrs.close();
			} 
			catch (Exception e1)
			{
				e1.printStackTrace();
			}
			
			float[] withs1 = { 4.0f};
			PdfPTable table1 = new PdfPTable(withs1);
			table1.setWidthPercentage(100);
			table1.setHorizontalAlignment(Element.ALIGN_LEFT);

			PdfPCell[] cell1 = new PdfPCell[1];
			cell1[0] = new PdfPCell(new Paragraph("THÔNG TIN CHỨNG TỪ", font2));
			cell1[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell1[0].setBackgroundColor(BaseColor.LIGHT_GRAY);
			table1.addCell(cell1[0]);
			document.add(table1);
			float[] withs2 = { 50f,50f };
			PdfPTable table2 = new PdfPTable(withs2);
			table2.setWidthPercentage(100);
			table2.setHorizontalAlignment(Element.ALIGN_LEFT);
			PdfPCell[] cell2 = new PdfPCell[2];
			Paragraph p1 = new Paragraph();
			p1.add(new Phrase( "Mã chứng từ: ", font8bold));
			p1.add(new Phrase( machungtu , font8normal));
			cell2[0] = new PdfPCell(p1);
			table2.addCell(cell2[0]);
			
			Paragraph p2 = new Paragraph();
			p2.add(new Phrase( "Ngày lập: ", font8bold));
			p2.add(new Phrase( ngaylap, font8normal));
			cell2[1] = new PdfPCell(p2);
			table2.addCell(cell2[1]);
			
			Paragraph p3 = new Paragraph();
			p3.add(new Phrase( "Mã khách hàng: ", font8bold));
			p3.add(new Phrase( "" + makh, font8normal));
			cell2[0] = new PdfPCell(p3);
			table2.addCell(cell2[0]);
			
			Paragraph p4 = new Paragraph();
			p4.add(new Phrase("Khách hàng: ", font8bold));
			p4.add(new Phrase( ""+tenkh, font8normal));
			cell2[1] = new PdfPCell(p4);
			table2.addCell(cell2[1]);

			
			Paragraph p5 = new Paragraph();
			p5.add(new Phrase("Địa chỉ: ", font8bold));
			p5.add(new Phrase( ""+diachi, font8normal));
			cell2[0] = new PdfPCell(p5);
			table2.addCell(cell2[0]);
		
			Paragraph p6 = new Paragraph();
			p6.add(new Phrase("Địa chỉ giao hàng: ", font8bold));
			p6.add(new Phrase( ""+diachi, font8normal));
			cell2[1] = new PdfPCell(p6);
			table2.addCell(cell2[1]);
			
			p5 = new Paragraph();
			p5.add(new Phrase("Nhân viên bán hàng: ", font8bold));
			p5.add(new Phrase( "" + nvbh, font8normal));
			cell2[0] = new PdfPCell(p5);
			table2.addCell(cell2[0]);
		
			p6 = new Paragraph();
			p6.add(new Phrase("Người giao hàng: ", font8bold));
			p6.add(new Phrase( "", font8normal));
			cell2[1] = new PdfPCell(p6);
			table2.addCell(cell2[1]);
			
			p5 = new Paragraph();
			p5.add(new Phrase("Điện thoại: ", font8bold));
			p5.add(new Phrase( "" + dienthoai, font8normal));
			cell2[0] = new PdfPCell(p5);
			table2.addCell(cell2[0]);
		
			p6 = new Paragraph();
			p6.add(new Phrase("Ghi chú: ", font8bold));
			p6.add(new Phrase( "" + ghichu, font8normal));
			cell2[1] = new PdfPCell(p6);
			table2.addCell(cell2[1]);
			
			/*Paragraph p9 = new Paragraph();
			p9.add(new Phrase("Nhân viên bán hàng: ", font8bold));
			p9.add(new Phrase( nvbh, font8normal));
			cell2[0] = new PdfPCell(p9);
			table2.addCell(cell2[0]);

			Paragraph p10 = new Paragraph();
			p10.add(new Phrase("Người giao hàng: ", font8bold));
			p10.add(new Phrase( "", font8normal));
			cell2[1] = new PdfPCell(p10);
			table2.addCell(cell2[1]);
			
			Paragraph p11 = new Paragraph();
			p9.add(new Phrase("Điện thoại: ", font8bold));
			p9.add(new Phrase( dienthoai, font8normal));
			cell2[0] = new PdfPCell(p11);
			table2.addCell(cell2[0]);

			Paragraph p12 = new Paragraph();
			p10.add(new Phrase("Ghi chú: ", font8bold));
			p10.add(new Phrase( ghichu, font8normal));
			cell2[1] = new PdfPCell(p12);
			table2.addCell(cell2[1]);*/

			document.add(table2);
			
			
			float[] withs3 = { 4.0f};
			PdfPTable table3 = new PdfPTable(withs3);
			table3.setWidthPercentage(100);
			table3.setHorizontalAlignment(Element.ALIGN_LEFT);
			
			PdfPCell[] cell3 = new PdfPCell[1];
			cell3[0] = new PdfPCell(new Paragraph("DANH MỤC SẢN PHẨM ", font2));
			cell3[0].setHorizontalAlignment(Element.ALIGN_LEFT);
			cell3[0].setBackgroundColor(BaseColor.WHITE);
			table3.addCell(cell3[0]);
			document.add(table3);	
			
			float[] withs4 = {8.0f, 15.0f, 25.0f, 10.0f, 13.0f, 20.0f, 20.0f };
			PdfPTable table4 = new PdfPTable(withs4);
			table4.setWidthPercentage(100);
			table4.setHorizontalAlignment(Element.ALIGN_LEFT);
			String[] th = new String[] { "STT", "Mã sản phẩm", "Tên sản phẩm", "ĐVT", "Số lượng", "Giá sản phẩm", "Thành tiền"};
			PdfPCell[] cell4 = new PdfPCell[7];
			for (int i = 0; i < th.length; i++)
			{
				cell4[i] = new PdfPCell(new Paragraph(th[i], font2));
				cell4[i].setHorizontalAlignment(Element.ALIGN_CENTER);
				cell4[i].setVerticalAlignment(Element.ALIGN_MIDDLE);
				cell4[i].setBackgroundColor(BaseColor.LIGHT_GRAY);
				table4.addCell(cell4[i]);
			}	

			double tongtien = 0;
			double pt_chietkhau = 0;
			double tongtien_chietkhau = 0;
			
			query = "select b.MA, 0 as soluongton,  isnull(a.sanphamTEN, b.TEN) as TEN, DV.donvi, a.soluong, a.dongia, a.dongiaGOC, isnull(a.chietkhau, 0) as chietkhau, a.thueVAT, round( ( isnull(a.chietkhau_CSBH, 0) + isnull(a.chietkhau_KM, 0) ) * 100.0 / a.dongiaGOC, 1 ) as ptChietkhau_KMBH, a.chietkhau as chietkhauKH  "+
					 "	from ERP_DONDATHANGNPP_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ       "+
					 "			INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK        "+
					 "	where a.DONDATHANG_FK = '" + lsxBean.getId() + "'   "+
					 "union ALL  "+
					 "	select b.MA, 0 as soluongton, b.TEN, DV.donvi, a.soluong, 0 dongia, 0 dongiaGOC, isnull(a.chietkhau, 0) as chietkhau, 0 thueVAT, 0 as ptChietkhau_KMBH, 0 as chietkhauKH  "+
					 "	from ERP_DONDATHANGNPP_CTKM_TRAKM a inner Join SanPham b on a.SPMA = b.MA       "+
					 "			INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK        "+
					 "	where a.DONDATHANGID = '" + lsxBean.getId() + "'  ";
			
			System.out.println("::: LAY SAN PHAM: " + query);
			ResultSet Sprs = db.get(query);
			int stt = 0;
			try {
				
				String masp = "";
				String tensp = "";
				String donvi = "";
				double soluong = 0;
				double dongiaGOC = 0;
				double thanhtien = 0;
				
				while (Sprs.next())
				{
					PdfPCell[] cell5 = new PdfPCell[9];
					
					masp = Sprs.getString("ma");
					tensp = Sprs.getString("ten");
					donvi =  Sprs.getString("donvi");
					
					soluong = Sprs.getDouble("soluong");
					dongiaGOC = Math.round( Sprs.getDouble("dongiaGOC") * ( 1 + Sprs.getDouble("thueVAT") / 100.0 ) );
					
					pt_chietkhau = Sprs.getDouble("ptChietkhau_KMBH");
					if( Sprs.getDouble("chietkhauKH") > 0 )
						pt_chietkhau = Sprs.getDouble("chietkhauKH");
					
					thanhtien = Math.round( dongiaGOC * soluong );
					tongtien_chietkhau += ( thanhtien * pt_chietkhau / 100.0 );
					tongtien += thanhtien;
					
					stt++;
					
					cell5[0] = new PdfPCell(new Paragraph("" + stt, font8normal));
					cell5[0].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[0].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[0]);

					cell5[1] = new PdfPCell(new Paragraph(masp, font8normal));
					cell5[1].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[1].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[1]);
					
					cell5[2] = new PdfPCell(new Paragraph(tensp, font8normal));
					cell5[2].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[2].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[2]);
					
					cell5[5] = new PdfPCell(new Paragraph(donvi, font8normal));
					cell5[5].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[5].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[5]);

					cell5[6] = new PdfPCell(new Paragraph(formatter2.format(soluong), font8normal));
					cell5[6].setHorizontalAlignment(Element.ALIGN_CENTER);
					cell5[6].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[6]);

					cell5[7] = new PdfPCell(new Paragraph(formatter2.format(dongiaGOC), font8normal));
					cell5[7].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell5[7].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[7]);

					cell5[8] = new PdfPCell(new Paragraph(formatter2.format(thanhtien), font8normal));
					cell5[8].setHorizontalAlignment(Element.ALIGN_RIGHT);
					cell5[8].setVerticalAlignment(Element.ALIGN_MIDDLE);
					table4.addCell(cell5[8]);
						
				}
			} 
			catch (Exception e1) {

				e1.printStackTrace();
			}
			
			// Tien bang chu
			doctienrachu doctien = new doctienrachu();
			//String tien = doctien.docTien(Long.parseLong( stt ));
			
			//Viết hoa ký tự đầu tiên
		    //String TienIN = (tien.substring(0,1)).toUpperCase() + tien.substring(1);
			
			try 
			{
				PdfPCell cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph("Tổng đơn vị sản phẩm " + stt + " - (Bằng chữ ): " + doctien.docSo((long)stt) + " sản phẩm", font8normal));
				cell6.setColspan(5);
				cell6.setPadding(2.0f);
				cell6.setBorderWidthRight(0);
				table4.addCell(cell6);
				
				cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph("Thành tiền: ", font8bold));
				cell6.setPadding(2.0f);
				cell6.setBorderWidthLeft(0);
				cell6.setBorderWidthRight(0);
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell6);
				
				cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph(formatter2.format(tongtien), font8bold));
				cell6.setPadding(2.0f);
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell6);
				
				//IN CHIET KHAU
				cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph("Chiết khấu(%): ", font8bold));
				cell6.setColspan(6);
				cell6.setPadding(2.0f);
				cell6.setBorderWidthRight(0);
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell6);
				
				cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph(formatter2.format(pt_chietkhau), font8bold));
				cell6.setPadding(2.0f);
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell6);

				
				//IN TỔNG TIỀN
				cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph("Tổng tiền (Bằng chữ): " + doctien.docTien( (long)( tongtien - tongtien_chietkhau )), font8normal));
				cell6.setColspan(5);
				cell6.setPadding(2.0f);
				cell6.setBorderWidthRight(0);
				table4.addCell(cell6);
				
				cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph("Tổng tiền: ", font8bold));
				cell6.setPadding(2.0f);
				cell6.setBorderWidthLeft(0);
				cell6.setBorderWidthRight(0);
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell6);
				
				cell6 = new PdfPCell();
				cell6 = new PdfPCell(new Paragraph(formatter2.format(tongtien - tongtien_chietkhau), font8bold));
				cell6.setPadding(2.0f);
				cell6.setHorizontalAlignment(Element.ALIGN_RIGHT);
				table4.addCell(cell6);
				
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			document.add(table4);		

			document.close();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
