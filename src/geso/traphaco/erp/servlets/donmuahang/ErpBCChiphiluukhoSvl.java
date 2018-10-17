package geso.traphaco.erp.servlets.donmuahang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangList;
import geso.traphaco.erp.beans.donmuahang.imp.ErpDonmuahangList;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import sun.security.krb5.internal.tools.Ktab;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;

public class ErpBCChiphiluukhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpBCChiphiluukhoSvl() {
        super();
    }   

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDonmuahangList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpDonmuahangList();
	    obj.setCongtyId((String)session.getAttribute("congtyId"));
	    obj.setUserId(userId);
	    obj.initBaoCao();
	    
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiPhiLuuKho.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		IErpDonmuahangList obj = new ErpDonmuahangList();
		obj.setCongtyId((String)session.getAttribute("congtyId"));
		
		String userId = request.getParameter("userId");
		obj.setUserId(userId);
		
		String loaingay = request.getParameter("loaingay");
		if(loaingay == null)
			loaingay = "";
		obj.setLoaingay(loaingay);
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dvthId = request.getParameter("dvth");
		if(dvthId == null)
			dvthId = "";
		obj.setDvthId(dvthId);
		
		String sanpham = request.getParameter("sanpham");
		if(sanpham == null)
			sanpham = "";
		obj.setSanphamId(sanpham);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";

		String pivot = request.getParameter("pivot");
		if (pivot == null)
			pivot = "0";
		obj.setPivot(pivot);
		
		String[] nccIds = request.getParameterValues("nccIds");
		String str = "";
		if(nccIds != null)
		{
			for(int i = 0; i < nccIds.length; i++)
				str += nccIds[i] + ",";
			
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
			
			obj.setNccIds(str);
		}
		
		
		
		String action = request.getParameter("action");
		if(action.equals("taoBC"))
		{
			//XEM BÁO CÁO THEO DÕI
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCTheodoiCPLK.xlsm");
			
			try 
			{
				CreatePivotTable(out, obj);
			} 
			catch (Exception e) 
			{
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiPhiLuuKho.jsp";
				obj.setmsg("Không thể tạo báo cáo - " + e.getMessage());
				
				response.sendRedirect(nextJSP);	
			}
		}
		else 
			{
				obj.initBaoCao();
			    
				session.setAttribute("obj", obj);
						
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiPhiLuuKho.jsp";
				response.sendRedirect(nextJSP);
			}
		
	}
	
	private boolean CreatePivotTable(OutputStream out, IErpDonmuahangList obj) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		 

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpTheoDoiChiPhiLuuKho.xlsm");
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			     
	    boolean isTrue = CreateStaticData(workbook, obj);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
	
	private boolean CreatePivotTableTheoDoiNhan(OutputStream out, IErpDonmuahangList obj, String trangthai, String tungay, String denngay) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		 

		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\ErpNhacNhanHang.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		boolean isTrue = CreateStaticDataTheoDoiNhan(workbook, obj);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
		
	private void setStyleColorNormar(Cells cells ,Cell cell, int so)
	{
		Cell cell1 = cells.getCell("V1");
		Style style;	
		style = cell1.getStyle();
		if(so == 1)
		{
	        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
	        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		}
        cell.setStyle(style);        
	}
	
	private boolean CreateStaticData(Workbook workbook, IErpDonmuahangList obj) throws Exception
	{
		
		
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
				
		
		Font font = new Font();
	 
		try
		{
			String sql = "";
			// LẤY TỔNG QUÁT SỐ HỢP ĐỒNG
			String query = 
					" SELECT MH.PK_SEQ AS MHID,  NKSP.SANPHAM_FK AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, \n"+
					"   	 MH.NGAYMUA, NKSP.SOLUONGNHAN, isnull(MH.SOHOPDONG, '') SOHOPDONG, SP.TEN \n"+
					" FROM ERP_NHAPKHONHAPKHAU NK  \n"+   
					" INNER JOIN ERP_MUAHANG MH ON NK.MUAHANG_FK = MH.PK_SEQ \n"+
					" INNER JOIN ERP_NHAPKHONHAPKHAU_SANPHAM NKSP ON  NK.PK_SEQ = NKSP.NHAPKHO_FK \n"+
					" LEFT JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK = NCC.PK_SEQ \n"+
					" INNER JOIN ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ \n"+
					" INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK = DVTH.PK_SEQ  \n"+
					" INNER JOIN SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK \n"+
					" WHERE ISNULL(MH.ISDNTT,0) = 0 AND NKSP.SANPHAM_FK is not null AND LEN(MH.SOHOPDONG) > 0 AND MH.TRANGTHAI NOT IN (3,4) \n";
			
			ResultSet Rs_HOPDONG = db.get(query);
			
			int dongTIEUDE_sohopdong = 4; // CỐ ĐỊNH
			int cotTIEUDE_sohopdong = 4; // NHẢY 6 BƯỚC (SỐ NGÀY LƯU, SL XUẤT, SL TỒN, TỒN M3, PHÍ HÀNG XUẤT, PHÍ HÀNG TỒN)
			
			int dongTIEUDE_sp = 5; // CỐ ĐỊNH
			int cotTIEUDE_sp = 5; // NHẢY 6 BƯỚC (SỐ NGÀY LƯU, SL XUẤT, SL TỒN, TỒN M3, PHÍ HÀNG XUẤT, PHÍ HÀNG TỒN)
			
			int dongTIEUDE_ctcp = 6;
			int cotTIEUDE_ctcp = 6;
			
			int dong_SP = 8 ;
			int cot_SP = 8;
			
			Cell cell = null;		
	
			if(Rs_HOPDONG != null)
			{
				while (Rs_HOPDONG.next())
				{				
					//CHÈN VÀO EXCEL				
					cells.merge( dongTIEUDE_sohopdong - 1, 1, cotTIEUDE_sohopdong -1 , 6 );		
					cell = cells.getCell("B" + String.valueOf(dongTIEUDE_sohopdong));
					cell.setValue(Rs_HOPDONG.getString("SOHOPDONG"));	
					this.setStyleColorNormar(cells, cell, 0);
					
					cells.merge( dongTIEUDE_sp - 1, 1, dongTIEUDE_sp - 1, 6 );		
					cell = cells.getCell("B" + String.valueOf(dongTIEUDE_sp));
					cell.setValue(Rs_HOPDONG.getString("TEN"));	
					this.setStyleColorNormar(cells, cell, 0);
						
					cotTIEUDE_sohopdong ++;
					dongTIEUDE_sohopdong ++;
					dongTIEUDE_ctcp ++;
					
					// LẤY CHI TIẾT
					/*cell = cells.getCell("B" + String.valueOf(dongTIEUDE_ctcp));
					cell.setValue("SỐ NGÀY LƯU");	
					this.setStyleColorNormar(cells, cell, 0);
					
					cell = cells.getCell("C" + String.valueOf(dongTIEUDE_ctcp));
					cell.setValue("SL XUẤT");	
					this.setStyleColorNormar(cells, cell, 0);
					
					cell = cells.getCell("D" + String.valueOf(dongTIEUDE_ctcp));
					cell.setValue("SL TỒN");	
					this.setStyleColorNormar(cells, cell, 0);
					
					cell = cells.getCell("E" + String.valueOf(dongTIEUDE_ctcp));
					cell.setValue("TỒN M3");	
					this.setStyleColorNormar(cells, cell, 0);
					
					cell = cells.getCell("F" + String.valueOf(dongTIEUDE_ctcp));
					cell.setValue("PHÍ HÀNG XUẤT");	
					this.setStyleColorNormar(cells, cell, 0);
					
					cell = cells.getCell("G" + String.valueOf(dongTIEUDE_ctcp));
					cell.setValue("PHÍ HÀNG TỒN");	
					this.setStyleColorNormar(cells, cell, 0);*/
					
					sql = 
						" SELECT CASE WHEN ( DATEDIFF (day, DATHANG.NGAYNHAP, NHANHANG.NGAYNHAN) - DATHANG.SONGAYLUUKHO ) < 0 THEN 0 \n"+
						"   	 ELSE ( DATEDIFF (day, DATHANG.NGAYNHAP, NHANHANG.NGAYNHAN) - DATHANG.SONGAYLUUKHO ) END  SONGAYTINHLUUKHO, \n"+
						"		 DATHANG.SONGAYLUUKHO, DATHANG.SOLUONGNHAN, NHANHANG.PHILUUKHO, DATHANG.NGAYNHAP \n"+
						" FROM \n"+ 
						" ( \n"+
						"	SELECT MH.PK_SEQ AS MHID,  NKSP.SANPHAM_FK AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, \n"+
						"		   ISNULL(SP.MA_FAST,'') AS MAFAST, ISNULL(SP.MA,'') AS MASP, MH.NGAYMUA ,  NKSP.SOLUONGNHAN, isnull(MH.SOHOPDONG, '') SOHOPDONG, \n"+
						"		   NK.NGAYNHAP, isnull(NKSP.SONGAYLUUKHO,0) SONGAYLUUKHO \n"+
						
						"	FROM ERP_NHAPKHONHAPKHAU NK \n"+
						"	INNER JOIN ERP_MUAHANG MH ON NK.MUAHANG_FK = MH.PK_SEQ \n"+
						"	INNER JOIN ERP_NHAPKHONHAPKHAU_SANPHAM NKSP ON  NK.PK_SEQ = NKSP.NHAPKHO_FK \n"+
						"	LEFT JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK = NCC.PK_SEQ \n"+
						"	LEFT JOIN SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK \n"+
						"	INNER JOIN ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ \n"+
						"	INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK = DVTH.PK_SEQ  \n"+
						
						"	WHERE ISNULL(MH.ISDNTT,0) = 0 AND NKSP.SANPHAM_FK is not null AND LEN(MH.SOHOPDONG) > 0 AND NK.TRANGTHAI = 1 \n"+
						" ) DATHANG \n"+   
						" LEFT  JOIN \n"+
						"( \n"+	 	
						"	SELECT DISTINCT  NHSP.SANPHAM_FK AS SPID, NH.PK_SEQ AS NHID  ,ISNULL(NHSPCT.SOLUONG,0) AS SOLUONGNHAN , ISNULL(NHSPCT.SOLO,'') SOLO, \n"+
						"	MH.PK_SEQ AS MHID, NHSP.DONGIA, MH.SOPO AS POID, MH.PK_SEQ, NHSP.DIENGIAI,TIENTE.TEN as TIENTE, KHO.TEN KHONHAN, \n"+
						"	NH.NGAYNHAN, ISNULL((SELECT THANHTIEN FROM ERP_CHIPHILUUKHO_SP_CHITIET WHERE NHANHANG_FK = NH.PK_SEQ AND SANPHAM_FK = NHSP.SANPHAM_FK),0) PHILUUKHO \n"+
							
						"	FROM ERP_NHANHANG NH  INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK \n"+
						"	INNER JOIN ERP_NHANHANG_SP_CHITIET NHSPCT ON NHSP.NHANHANG_FK = NHSPCT.NHANHANG_FK AND NHSP.SANPHAM_FK = NHSPCT.SANPHAM_FK  \n"+
						"	INNER JOIN KHO  ON KHO.PK_SEQ = NHSP.KHONHAN  \n"+
						"	INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK = MH.PK_SEQ \n"+
						"	LEFT JOIN SANPHAM SP ON SP.PK_SEQ = NHSP.SANPHAM_FK \n"+
						"	INNER JOIN ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ   \n"+
						"	WHERE NHSP.SOLUONGNHAN > 0 AND NH.TRANGTHAI not in (3,4) and NHSP.SANPHAM_FK is not null  \n"+
							
						" ) NHANHANG  ON  DATHANG.MHID = NHANHANG.MHID AND DATHANG.SPID = NHANHANG.SPID \n"+
						" LEFT JOIN \n"+
						" ( \n"+
						"	SELECT NH.PK_SEQ NHID , CPLK.MUAHANG_FK MHID, isnull(CPLK.THANHTIEN,0) CHIPHI, CPLK.SANPHAM_FK AS SPID  \n"+		
						"	FROM ERP_NHANHANG NH INNER JOIN ERP_CHIPHILUUKHO_SP_CHITIET CPLK ON NH.PK_SEQ = CPLK.NHANHANG_FK	\n"+ 
						" ) CPLK ON  DATHANG.MHID = CPLK.MHID AND DATHANG.SPID = CPLK.SPID AND NHANHANG.NHID = CPLK.NHID  	\n"+
						" WHERE 1 = 1 AND DATHANG.SPID = "+Rs_HOPDONG.getString("SPID") +" AND  DATHANG.SOHOPDONG  = '" + Rs_HOPDONG.getString("SOHOPDONG") +"'";
					
					ResultSet Rs_NHAPKHO = db.get(sql);
					
					if(Rs_NHAPKHO!=null)
					{						
						while(Rs_NHAPKHO.next())
						{		
							cell = cells.getCell("B" + String.valueOf(cot_SP));
							cell.setValue(Rs_NHAPKHO.getString("SONGAYLUUKHO"));	
							this.setStyleColorNormar(cells, cell, 0);
								
							cell = cells.getCell("C" + String.valueOf(cot_SP));
							cell.setValue(Rs_NHAPKHO.getString("SOLUONGNHAN"));	
							this.setStyleColorNormar(cells, cell, 0);
							
							cell = cells.getCell("D" + String.valueOf(cot_SP));
							cell.setValue("");	
							this.setStyleColorNormar(cells, cell, 0);
							
							cell = cells.getCell("E" + String.valueOf(cot_SP));
							cell.setValue("");	
							this.setStyleColorNormar(cells, cell, 0);
							
							cell = cells.getCell("F" + String.valueOf(cot_SP));
							cell.setValue(Rs_NHAPKHO.getString("PHILUUKHO"));	
							this.setStyleColorNormar(cells, cell, 0);
							
							cell = cells.getCell("G" + String.valueOf(cot_SP));
							cell.setValue("");	
							this.setStyleColorNormar(cells, cell, 0);
							
						}
					}
					cot_SP ++;
				}
			}
		}
		catch(Exception Ex)
		{
			Ex.printStackTrace();
		}
		
		
		/*
		if (trang_thai.length() > 0){
			if(trang_thai.equals("-1")){ // Đã nhận hàng			
				query += " and A.NHID is not null ";
			}else{
				query += " and A.TRANGTHAI = '" + trang_thai + "'";
			}
		}
		
		if (obj.getDvthId().length() > 0){
			query += " and A.donvithuchien_fk = '" + obj.getDvthId() + "'";
		}
		
		if (obj.getNccIds().length() > 0){
			query += " and A.NHACUNGCAP_FK in (" + obj.getNccIds() + ")";
		}
		
		// 0 : ngày mua; 1: ngày nhận; 2: ngày hóa đơn
		if(loaingay.equals("0")){
			query += " and A.NGAYMUA >='"+tungay+"'   AND A.NGAYMUA <='"+denngay+"' \n";
		}else if(loaingay.equals("1")){
			query += " and A.NGAYNHAN >='"+tungay+"'   AND A.NGAYNHAN <='"+denngay+"' \n";
		}else if (loaingay.equals("2")){
			query += " and A.NGAYHOADON >='"+tungay+"'   AND A.NGAYHOADON <='"+denngay+"' \n";
		}*/
		
	/*	query +=" ORDER BY A.MUAHANG_FK, A.SOHOADON, A.NHID " ;
		
		System.out.println("Theo doi Don Mua Hang TT hien tai 1: " + query);
		ResultSet rs = db.get(query);*/
		
		
		
		// --- đổ dữ liệu ---//
		
		//int index =6;
		try{/*
			//CHÈN VÀO EXCEL
			int indexHeader = index-1;
			Cells cells = worksheet.getCells();	
			
			Cell cell = cells.getCell("A2");
		    cell.setValue(" Từ ngày :  " + tungay + " 			Đến ngày : " + denngay);
		    cell = cells.getCell("A3");
		    cell.setValue("Người tạo: ");	
		    cell = cells.getCell("A4");
		    cell.setValue("Đơn vị thực hiện: " );
		    cell = cells.getCell("A5");
		    cell.setValue("Loại sản phẩm: ");
		    cell = cells.getCell("A6");
		    cell.setValue("Nhà cung cấp: ");
		    
			cell=null;
			Style style= null;
			
			
			if(obj.getPivot().equals("1")){
				cell = cells.getCell("BA" + String.valueOf(indexHeader));		
				cell.setValue("SODONMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BB" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BC" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BD" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BE" + String.valueOf(indexHeader));		
				cell.setValue("TENSANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BF" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BG" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONG");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BH" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BI" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BJ" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BK" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BL" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BM" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BN" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BO" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
			}else{
				cell = cells.getCell("A" + String.valueOf(indexHeader));		
				cell.setValue("SODONMUA");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("B" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("C" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("D" + String.valueOf(indexHeader));		
				cell.setValue("SONHANHANG");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("E" + String.valueOf(indexHeader));		
				cell.setValue("SOHOADON");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("F" + String.valueOf(indexHeader));		
				cell.setValue("KHONHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("G" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("H" + String.valueOf(indexHeader));		
				cell.setValue("MAFAST");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("I" + String.valueOf(indexHeader));		
				cell.setValue("TENSANPHAM");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("J" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("K" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGDAT");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("L" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGHD");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("M" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("N" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);				
				
				cell = cells.getCell("O" + String.valueOf(indexHeader));		
				cell.setValue("SOLO");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("P" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("Q" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();	
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("R" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("S" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);		
				
				cell = cells.getCell("T" + String.valueOf(indexHeader));		
				cell.setValue("NGAYHOADON");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);		
				
				cell = cells.getCell("U" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("V" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("W" + String.valueOf(indexHeader));		
				cell.setValue("SỐ HỢP ĐỒNG NGOẠI");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("X" + String.valueOf(indexHeader));		
				cell.setValue("CHI PHÍ LƯU KHO");	
				style = cell.getStyle();
				style.setFont(font); 
				font.setBold(true);
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
			}
			
			
			while(rs.next()){
				cell=null;
				style= null;
				NumberFormat formatter = new DecimalFormat("#,###,###.###");

				String MUAHANG_FK = rs.getString("POID");
				String NGAYMUA = rs.getString("NGAYMUA");
				String TENNCC = rs.getString("TENNCC");
				String SONHANHANG = rs.getString("NHID");
				String SOHOADON = rs.getString("SOHOADON");
				String KHONHAN = rs.getString("KHONHAN");
				String MASANPHAM = rs.getString("MASANPHAM");
				String MAFAST = rs.getString("MAFAST");
				String TENSP = rs.getString("TENSP");
				String DONVI = rs.getString("DONVI");
				String SOLUONG = rs.getString("SOLUONG");
				String SOLUONGHD = rs.getString("SOLUONGHD");
				String DONGIA = rs.getString("DONGIA");
				String THANHTIEN = rs.getString("THANHTIEN");
				String TIENTE = rs.getString("TIENTE");
				String NGAYYEUCAUNHAN = rs.getString("NGAYYEUCAUNHAN");
				String NGAYNHAN = rs.getString("NGAYNHAN");
				String NGAYHOADON = rs.getString("NGAYHOADON");
				String SOLO = rs.getString("SOLO");
				String SOLUONGNHAN = rs.getString("SOLUONGDANHAN");
				String SOLUONGCONLAI = rs.getString("SOLUONGCONLAI");
				String GHICHU = rs.getString("GHICHU");		
				String HDNGOAI = rs.getString("SOHOPDONG");	
				String CPLK = rs.getString("CHIPHI");	
				int ROW = rs.getInt("ROW");
				int ROWHD = rs.getInt("ROWHD");
				
				if(ROW != 1) SOLUONG = "0";
				if(ROWHD != 1) SOLUONGHD = "0";
				
				//CHÈN VÀO EXCEL
				
				if(obj.getPivot().equals("1")){
					cell = cells.getCell("BA" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BB" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BC" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BD" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BE" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BF" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BG" + String.valueOf(index));		
					cell.setValue(SOLUONG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BH" + String.valueOf(index));		
					cell.setValue(DONGIA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BI" + String.valueOf(index));		
					cell.setValue(THANHTIEN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BJ" + String.valueOf(index));		
					cell.setValue(TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BK" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BL" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BM" + String.valueOf(index));		
					cell.setValue(SOLUONGNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BN" + String.valueOf(index));		
					cell.setValue(SOLUONGCONLAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BO" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
				}else{
					cell = cells.getCell("A" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("B" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("D" + String.valueOf(index));		
					cell.setValue(SONHANHANG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("E" + String.valueOf(index));		
					cell.setValue(SOHOADON);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("F" + String.valueOf(index));		
					cell.setValue(KHONHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("G" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("H" + String.valueOf(index));		
					cell.setValue(MAFAST);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("I" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("J" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("K" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(SOLUONG));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("L" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(SOLUONGHD));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("M" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(DONGIA));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("N" + String.valueOf(index));		
					cell.setValue( Double.parseDouble(THANHTIEN));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("O" + String.valueOf(index));		
					cell.setValue(SOLO);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("P" + String.valueOf(index));		
					cell.setValue(Double.parseDouble( SOLUONGNHAN ));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("Q" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(SOLUONGCONLAI));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("R" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("S" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("T" + String.valueOf(index));		
					cell.setValue( NGAYHOADON);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					
					cell = cells.getCell("U" + String.valueOf(index));		
					cell.setValue( TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("V" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("W" + String.valueOf(index));		
					cell.setValue(HDNGOAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("X" + String.valueOf(index));		
					cell.setValue(Double.parseDouble(CPLK));	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
				}
				
				
				index++;
				
			}
			
		*/
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
		
	}
	
	private boolean CreateStaticDataTheoDoiNhan(Workbook workbook, IErpDonmuahangList obj) throws Exception
	{		
		
		
		dbutils db = new dbutils();
		Utility util = new Utility();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		
		Font font = new Font();
	 	
	
		String query="	SELECT   Distinct A.*,(A.SOLUONG-A.SOLUONGDANHAN) as SOLUONGCONLAI  FROM( "+
				 	"	(SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK,ISNULL(DATHANG.SPID,NHANHANG.SPID) AS MASANPHAM,  "+
					"	 DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , "+
					"	  DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, DATHANG.THANHTIEN ,DATHANG.TIENTE, "+
					"	 ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, "+
					"	 NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  "+
					"	 ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, "+
					"	 ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, "+
					"	 ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.NGUOITAO, "+
					"	 DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU      "+
					"	 FROM   "+
					"	 	( "+
					"	 		SELECT MH.PK_SEQ AS MHID,  MHSP.SANPHAM_FK AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, "+
					"	 		ISNULL(SP.MA,'') AS ITEM, MH.NGAYMUA   ,  MHSP.DONVI, MHSP.SOLUONG,    "+
					"	 		MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, "+
					"	 		ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI,   "+
					"	 		DVTH.MA, SP.TEN, MHSP.DIENGIAI, MH.NGUOITAO, SP.LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU "+
					"	 		FROM ERP_MUAHANG MH     "+
					"	 		INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK   "+   
					"	 		INNER JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=MHSP.SANPHAM_FK "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "+
					"	 		INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ  "+  
					"	 		LEFT JOIN(  SELECT 	MUAHANG_FK AS DMHID,  "+
					"	 		CASE WHEN SUM(QUYETDINH) > 0 THEN  "+
					"	 		(CASE WHEN  ( SELECT SUM(TRANGTHAI)    FROM ERP_DUYETMUAHANG  	 "+
					"	 		WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  ELSE 1  END)	  "+
					"	 		ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	  END AS DUYET    "+
					"	 		FROM ERP_DUYETMUAHANG DUYETMUAHANG  "+
					"	 		GROUP BY MUAHANG_FK  ) DUYET ON DUYET.DMHID = MH.PK_SEQ     "+
					"	 		WHERE MHSP.SANPHAM_FK is not null "+//and MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) DATHANG   "+
					"	 LEFT  JOIN       "+
					"	 ( "+
					"	 		SELECT NHSP.SANPHAM_FK AS SPID,NH.PK_SEQ AS NHID  ,ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN , "+
					"	 		MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, MH.SOPO AS POID,  NH.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI,TIENTE.TEN as TIENTE   "+
					"	 		FROM ERP_NHANHANG NH     INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NH.MUAHANG_FK=MH.PK_SEQ     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ  "+
					"	 		LEFT JOIN ERP_HOADONNCC_PHIEUNHAP HDPN ON  NH.PK_SEQ= HDPN.PHIEUNHAN_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= HDPN.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is not null   "+ //AND MH.NGAYMUA >='"+tungay+"'    AND MH.NGAYMUA <='"+denngay+"' "+
					"	 		UNION ALL      "+
					"	 		SELECT  "+
					"	 		NHSP.SANPHAM_FK AS SPID,NH.PK_SEQ AS NHID, "+
					"	 		ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN,  NHSP.MUAHANG_FK AS MHID, NH.NGAYNHAN, NHSP.DONGIA, MH.SOPO AS POID, "+
					"	 		NH.SOHOADON, HD.NGAYHOADON  , MH.PK_SEQ ,TIENTE.TEN as TIENTE, NHSP.DIENGIAI       "+
					"	 		FROM ERP_NHANHANG NH    "+
					"	 		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_THUENHAPKHAU  TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK=MH.PK_SEQ    "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "+
					"	 		INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNK_HD ON TNK.PK_SEQ= TNK_HD.THUENHAPKHAU_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON HD.PK_SEQ= TNK_HD.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is not null  AND NH.MUAHANG_FK IS NULL  AND NHSP.MUAHANG_FK IS NOT NULL "+  
					//"	 		AND MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) NHANHANG  ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID  ) "+

					"	 union all "+
	 
					"	 (SELECT ISNULL(DATHANG.MHID,NHANHANG.MHID) AS MUAHANG_FK,ISNULL(DATHANG.SPID,NHANHANG.SPID) AS MASANPHAM,  "+
					"	 DATHANG.NGAYMUA, DATHANG.POID,DATHANG.NCC as TENNCC, ISNULL(DATHANG.TEN,DATHANG.DIENGIAI)  AS TENSP , "+
					"	  DATHANG.DONVI,   DATHANG.SOLUONG, DATHANG.DONGIA, (DATHANG.SOLUONG* DATHANG.DONGIA) as THANHTIEN,DATHANG.TIENTE, "+
					"	 ISNULL(DATHANG.NGAYDENGHI,'') AS NGAYYEUCAUNHAN, ISNULL(NHANHANG.SOLUONGNHAN,0) AS SOLUONGDANHAN, "+
					"	 NHANHANG.NHID, ISNULL(NHANHANG.NGAYNHAN,'') AS NGAYNHAN,  "+
					"	 ISNULL(NHANHANG.DONGIA, DATHANG.DONGIA) AS DONGIANHAN, "+
					"	 ISNULL(NHANHANG.SOHOADON,'') AS SOHOADON, "+
					"	 ISNULL(NHANHANG.NGAYHOADON,'') AS NGAYHOADON,  DATHANG.NGUOITAO, "+
					"	 DATHANG.DONVITHUCHIEN_FK,  DATHANG.NHACUNGCAP_FK,DATHANG.GHICHU      "+
					"	 FROM   "+
					"	 	( "+
					"	 		SELECT MH.PK_SEQ AS MHID,  isnull(MHSP.SANPHAM_FK, MHSP.CHIPHI_FK) AS SPID, MH.NGAYTAO, MH.SOPO AS POID, ISNULL(NCC.TEN,'') AS NCC, "+
					"	 		ISNULL(SP.MA,'') AS ITEM, MH.NGAYMUA   , MHSP.DONVI, MHSP.SOLUONG,    "+
					"	 		MHSP.DONGIA, (MHSP.DONGIA*MHSP.SOLUONG) AS THANHTIEN,TIENTE.TEN as TIENTE, ISNULL( MH.SOTHAMCHIEU,'')AS SPYC,  "+
					"	 		ISNULL(MHSP.NGAYNHAN,'') AS NGAYDENGHI,    SUBSTRING(CAST(MHSP.NGAYNHAN AS VARCHAR(10)),6,2)  AS THANGDAT , "+
					"	 		DVTH.MA, SP.TEN, MHSP.DIENGIAI, MH.NGUOITAO, SP.LOAISANPHAM_FK , MH.DONVITHUCHIEN_FK, MH.NHACUNGCAP_FK,MH.GHICHU "+
					"	 		FROM ERP_MUAHANG MH     "+
					"	 		INNER JOIN ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK      "+
					"	 		INNER JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK=NCC.PK_SEQ "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=MHSP.SANPHAM_FK "+
					"	 		INNER JOIN ERP_DONVITHUCHIEN DVTH ON MH.DONVITHUCHIEN_FK= DVTH.PK_SEQ    "+
					"	 		LEFT JOIN(  SELECT 	MUAHANG_FK AS DMHID,  "+
					"	 		CASE WHEN SUM(QUYETDINH) > 0 THEN  "+
					"	 		(CASE WHEN  ( SELECT SUM(TRANGTHAI)    FROM ERP_DUYETMUAHANG  	 "+
					"	 		WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0  ELSE 1  END)	  "+
					"	 		ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	  END AS DUYET    "+
					"	 		FROM ERP_DUYETMUAHANG DUYETMUAHANG  "+
					"	 		GROUP BY MUAHANG_FK  ) DUYET ON DUYET.DMHID = MH.PK_SEQ     "+
					"	 		WHERE MHSP.SANPHAM_FK is null "+// and MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) DATHANG   "+
					"	 LEFT  JOIN       "+
					"	 ( "+
					"	 		SELECT isnull(NHSP.SANPHAM_FK, NHSP.CHIPHI_FK) AS SPID,NH.PK_SEQ AS NHID  ,ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN , "+
					"	 		MH.PK_SEQ AS MHID, NH.NGAYNHAN, NHSP.DONGIA, TIENTE.TEN as TIENTE,MH.SOPO AS POID,  NH.SOHOADON, HD.NGAYHOADON, MH.PK_SEQ, NHSP.DIENGIAI   "+
					"	 		FROM ERP_NHANHANG NH     INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NH.MUAHANG_FK=MH.PK_SEQ "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC_PHIEUNHAP HDPN ON  NH.PK_SEQ= HDPN.PHIEUNHAN_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON  HD.PK_SEQ= HDPN.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is null "+//   AND MH.NGAYMUA >='"+tungay+"'    AND MH.NGAYMUA <='"+denngay+"' "+
					"	 		UNION ALL      "+
					"	 		SELECT  "+
					"	 		isnull(NHSP.SANPHAM_FK, NHSP.CHIPHI_FK) AS SPID,NH.PK_SEQ AS NHID, "+
					"	 		ISNULL(NHSP.SOLUONGNHAN,0) AS SOLUONGNHAN,  NHSP.MUAHANG_FK AS MHID, NH.NGAYNHAN, NHSP.DONGIA,TIENTE.TEN as TIENTE, MH.SOPO AS POID, "+
					"	 		NH.SOHOADON, HD.NGAYHOADON  , MH.PK_SEQ , NHSP.DIENGIAI       "+
					"	 		FROM ERP_NHANHANG NH    "+
					"	 		INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ= NHSP.NHANHANG_FK     "+
					"	 		INNER JOIN ERP_THUENHAPKHAU  TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK     "+
					"	 		LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK     "+
					"	 		INNER JOIN ERP_MUAHANG MH ON NHSP.MUAHANG_FK=MH.PK_SEQ  "+
					"			inner join ERP_TIENTE TIENTE on MH.TIENTE_FK = TIENTE.PK_SEQ   "+
					"	 		INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNK_HD ON TNK.PK_SEQ= TNK_HD.THUENHAPKHAU_FK   "+
					"	 		LEFT JOIN ERP_HOADONNCC HD ON HD.PK_SEQ= TNK_HD.HOADONNCC_FK   "+
					"	 		WHERE NHSP.SOLUONGNHAN >0 AND NH.TRANGTHAI not in(3,4) and NHSP.SANPHAM_FK is null  AND NH.MUAHANG_FK IS NULL  AND NHSP.MUAHANG_FK IS NOT NULL   "+
					//"	 		AND MH.NGAYMUA >='"+tungay+"'   AND MH.NGAYMUA <='"+denngay+"' "+
					"	 ) NHANHANG  "+
					"	 ON   DATHANG.MHID=NHANHANG.MHID AND DATHANG.SPID= NHANHANG.SPID   and DATHANG.DIENGIAI=NHANHANG.DIENGIAI) "+
					"	 )A WHERE 1=1  and A.NGAYYEUCAUNHAN < CONVERT(VARCHAR(10),DATEADD(DAY,10,GETDATE()),126) and (A.SOLUONG-A.SOLUONGDANHAN) > 0 ";
						 			
		
		if(obj.getNguoitaoIds().trim().length() > 0){
			query += " and A.NGUOITAO = '" + obj.getNguoitaoIds() + "' ";
		}
		
		if (obj.getDvthId().length() > 0){
			query += " and A.donvithuchien_fk = '" + obj.getDvthId() + "'";
		}
		
		if (obj.getNspIds().length() > 0){
			query += " and A.LOAISANPHAM_FK in (" + obj.getNspIds() + ")";
		}
		
		if (obj.getNccIds().length() > 0){
			query += " and A.NHACUNGCAP_FK in (" + obj.getNccIds() + ")";
		}
		
		query +=" ORDER BY A.MUAHANG_FK, A.SOHOADON " ;
		
		System.out.println("Theo dõi nhắc nhở nhận hàng 2: " + query);
		ResultSet rs = db.get(query);
		
		
		
		// --- đổ dữ liệu ---//
		
		int index =6;
		try{
			//CHÈN VÀO EXCEL
			int indexHeader = index-1;
			Cells cells = worksheet.getCells();	
			
			//LẤY NGÀY HIỆN TẠI & 10 NGÀY TIẾP THEO
			
			DateFormat dateFormat= new SimpleDateFormat("dd-MM-yyyy");
			Calendar c = Calendar.getInstance();    
			c.setTime(new Date());
			c.add(Calendar.DATE, 10);
			
			Cell cell = cells.getCell("A2");
		    cell.setValue(" Từ ngày :  " + dateFormat.format(Calendar.getInstance().getTime()) + " 			Đến ngày : " + dateFormat.format(c.getTime()));
		    
		    /*  cell = cells.getCell("A3");
		    cell.setValue("Người tạo: "+util.getTenNhaPP());	
		    cell = cells.getCell("A4");
		    cell.setValue("Đơn vị thực hiện: " );
		    cell = cells.getCell("A5");
		    cell.setValue("Loại sản phẩm: ");
		    cell = cells.getCell("A6");
		    cell.setValue("Nhà cung cấp: ");*/
		    
			cell=null;
			Style style= null;
			
			if(obj.getPivot().equals("1")){
				cell = cells.getCell("BA" + String.valueOf(indexHeader));		
				cell.setValue("MUAHANG_FK");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BB" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BC" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BD" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BE" + String.valueOf(indexHeader));		
				cell.setValue("TENSP");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BF" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BG" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONG");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BH" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BI" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BJ" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BK" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BL" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BM" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("BN" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("BO" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
			}else{
				
				cell = cells.getCell("A" + String.valueOf(indexHeader));		
				cell.setValue("MUAHANG_FK");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("B" + String.valueOf(indexHeader));		
				cell.setValue("NGAYMUA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("C" + String.valueOf(indexHeader));		
				cell.setValue("TENNCC");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("D" + String.valueOf(indexHeader));		
				cell.setValue("MASANPHAM");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("E" + String.valueOf(indexHeader));		
				cell.setValue("TENSP");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("F" + String.valueOf(indexHeader));		
				cell.setValue("DONVI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("G" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONG");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("H" + String.valueOf(indexHeader));		
				cell.setValue("DONGIA");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("I" + String.valueOf(indexHeader));		
				cell.setValue("THANHTIEN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("J" + String.valueOf(indexHeader));		
				cell.setValue("TIENTE");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("K" + String.valueOf(indexHeader));		
				cell.setValue("NGAYYEUCAUNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.RIGHT);					
				cell.setStyle(style);
				
				cell = cells.getCell("L" + String.valueOf(indexHeader));		
				cell.setValue("NGAYNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("M" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGNHAN");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.LEFT);					
				cell.setStyle(style);
				
				cell = cells.getCell("N" + String.valueOf(indexHeader));		
				cell.setValue("SOLUONGCONLAI");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
				
				cell = cells.getCell("O" + String.valueOf(indexHeader));		
				cell.setValue("GHICHU");	
				style = cell.getStyle();				
				style.setHAlignment(TextAlignmentType.CENTER);					
				cell.setStyle(style);
			}
			
			
			while(rs.next()){
				cell=null;
				style= null;
				NumberFormat formatter = new DecimalFormat("#,###,###.###");

				String MUAHANG_FK = rs.getString("MUAHANG_FK");
				String NGAYMUA = rs.getString("NGAYMUA");
				String TENNCC = rs.getString("TENNCC");
				String MASANPHAM = rs.getString("MASANPHAM");
				String TENSP = rs.getString("TENSP");
				String DONVI = rs.getString("DONVI");
				String SOLUONG = rs.getString("SOLUONG");
				String DONGIA = rs.getString("DONGIA");
				String THANHTIEN = rs.getString("THANHTIEN");
				String TIENTE = rs.getString("TIENTE");
				String NGAYYEUCAUNHAN = rs.getString("NGAYYEUCAUNHAN");
				String NGAYNHAN = rs.getString("NGAYNHAN");
				String SOLUONGNHAN = rs.getString("SOLUONGDANHAN");
				String SOLUONGCONLAI = rs.getString("SOLUONGCONLAI");
				String GHICHU = rs.getString("GHICHU");
				
				//CHÈN VÀO EXCEL
				
				if(obj.getPivot().equals("1")){
					
			
					cell = cells.getCell("BA" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BB" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BC" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BD" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BE" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BF" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BG" + String.valueOf(index));		
					cell.setValue(SOLUONG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BH" + String.valueOf(index));		
					cell.setValue(DONGIA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BI" + String.valueOf(index));		
					cell.setValue(THANHTIEN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BJ" + String.valueOf(index));		
					cell.setValue(TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BK" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BL" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BM" + String.valueOf(index));		
					cell.setValue(SOLUONGNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("BN" + String.valueOf(index));		
					cell.setValue(SOLUONGCONLAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("BO" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
				}else{
					cell = cells.getCell("A" + String.valueOf(index));		
					cell.setValue(MUAHANG_FK);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("B" + String.valueOf(index));		
					cell.setValue(NGAYMUA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(TENNCC);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("D" + String.valueOf(index));		
					cell.setValue(MASANPHAM);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("E" + String.valueOf(index));		
					cell.setValue(TENSP);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("F" + String.valueOf(index));		
					cell.setValue(DONVI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("G" + String.valueOf(index));		
					cell.setValue(SOLUONG);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("H" + String.valueOf(index));		
					cell.setValue(DONGIA);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("I" + String.valueOf(index));		
					cell.setValue(THANHTIEN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("J" + String.valueOf(index));		
					cell.setValue(TIENTE);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("K" + String.valueOf(index));		
					cell.setValue(NGAYYEUCAUNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					cell = cells.getCell("L" + String.valueOf(index));		
					cell.setValue(NGAYNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("M" + String.valueOf(index));		
					cell.setValue(SOLUONGNHAN);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);					
					cell.setStyle(style);
					
					cell = cells.getCell("N" + String.valueOf(index));		
					cell.setValue(SOLUONGCONLAI);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
					
					cell = cells.getCell("O" + String.valueOf(index));		
					cell.setValue(GHICHU);	
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);					
					cell.setStyle(style);
				}
				
				
				index++;
				
			}
			return true;
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		
		
		
	}
	private String getdatetime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
