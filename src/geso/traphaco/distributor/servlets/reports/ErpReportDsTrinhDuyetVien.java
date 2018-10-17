package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.RowId;
 
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
 
  
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
 
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ErpReportDsTrinhDuyetVien extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpReportDsTrinhDuyetVien()
	{
		super();
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		obj.setuserId(userId);
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.init();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportDSTrinhDuyetVien.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String getPiVotName()
	{
		String DATE_FORMAT_NOW = "yyyy-MM-dd HH:mm:ss";
		Calendar cal = Calendar.getInstance();
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT_NOW);
		String name = sdf.format(cal.getTime());
		name = name.replaceAll("-", "");
		name = name.replaceAll(" ", "_");
		name = name.replaceAll(":", "");
		return "_" + name;
		
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		Utility util = new Utility();
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		geso.traphaco.distributor.util.Utility Ult = new geso.traphaco.distributor.util.Utility();
		
		String nppId = request.getParameter("nppId");
		nppId = Ult.getIdNhapp(userId);
		obj.setnppId(nppId);
		
		String nppTen = Ult.getTenNhaPP();
		obj.setuserTen(userTen);
		
		String khId = request.getParameter("khId"); // <!---
		if (khId == null)
			khId = "";
		obj.setkhId(khId);
		
		String nccId = request.getParameter("nccId"); // <!---
		if (nccId == null)
			nccId = "";
		obj.setNccId(nccId);
		
		String kenhId = request.getParameter("kenhId"); // <!---
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);
		
		String dvkdId = request.getParameter("dvkdId"); // <!---
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);
		
		String nhanhangId = request.getParameter("nhanhangId"); // <!---
		if (nhanhangId == null)
			nhanhangId = "";
		obj.setnhanhangId(nhanhangId);
		
		String chungloaiId = request.getParameter("chungloaiId");// <!---
		if (chungloaiId == null)
			chungloaiId = "";
		obj.setchungloaiId(chungloaiId);
		
		String tungay = request.getParameter("Sdays"); // <!---
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);
		
		String denngay = request.getParameter("Edays");// <!---
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String khoId = request.getParameter("khoId"); // <!---
		if (khoId == null)
			khoId = "";
		obj.setkhoId(khoId);
		
		String vat = request.getParameter("vat");
		obj.setvat(vat);
		
		String discount = request.getParameter("discount");
		obj.setdiscount(discount);
		
		String instransit = request.getParameter("instransit");
		obj.setHangDiDuong(instransit);
		
		obj.setsanphamId(util.antiSQLInspection(request.getParameter("sanphamId")) != null ? util.antiSQLInspection(request.getParameter("sanphamId")) : "");
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nganhhangId")) != null ? util.antiSQLInspection(request.getParameter("nganhhangId")) : "");
		obj.setHoaDonKmDb(util.antiSQLInspection(request.getParameter("hdkmdb")) != null ? util.antiSQLInspection(request.getParameter("hdkmdb")) : "");
		obj.setHangDiDuong(util.antiSQLInspection(request.getParameter("instransit")) != null ? util.antiSQLInspection(request.getParameter("instransit")) : "");
		
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");	
		obj.setLaytheo(util.antiSQLInspection(request.getParameter("laysolo")) != null ? util.antiSQLInspection(request.getParameter("laysolo")) : "");
		
		obj.setChiTietXNT_Lo(util.antiSQLInspection(request.getParameter("layctnxsolo")) != null ? util.antiSQLInspection(request.getParameter("layctnxsolo")) : "");
		
		
		
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);
		if (!tungay.equals("") && !denngay.equals(""))
		{
			
			String action = request.getParameter("action");
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
			if (action.equals("tao"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=ErpReportChiPhi(NPP)" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, fieldsHien, obj); 
					
				} catch (Exception ex)
				{
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
					obj.init();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportDSTrinhDuyetVien.jsp";
					response.sendRedirect(nextJSP);
					
				}
			}
		}else{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportDSTrinhDuyetVien.jsp";
			response.sendRedirect(nextJSP);
		}
		
	
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			dbutils db=new dbutils();
			
			
			String strfstream = getServletContext().getInitParameter("path") + "\\ReportDSTrinhDuyetVien.xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			
			Worksheet worksheet_tonghop = worksheets.getSheet("sheet1");
	  		
	  		this.Dodulieu_worksheet_tonghop(worksheet_tonghop,obj,db);
	  		worksheet_tonghop.setName("REPORT_THEODOI_DS");
	  		 
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
 
	private void Dodulieu_worksheet_tonghop(Worksheet worksheet_2,
			IStockintransit obj, dbutils db) {
		try{
		// TODO Auto-generated method stub
		Cells cells = worksheet_2.getCells();
			cells.setRowHeight(0, 50.0f);
	    Cell cell = cells.getCell("A1");
	    ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BÁO CÁO KH - TH CHI TIEU DSO OTC_MIEN NAM");
	    
	    cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
		
		cell = cells.getCell("B4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());

		String[] param = new String[2];
  		param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
		param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();

		cell = cells.getCell("AX1");
		 Style style1_number=cell.getStyle();
		 
	 	 	 cell = cells.getCell("BA1");
		 Style style1_header=cell.getStyle();
	 	 cell = cells.getCell("AY1");
		 Style style_footer=cell.getStyle();
		 
		 cell = cells.getCell("AZ1");
		 Style style_header_thucdat =cell.getStyle();
		 
		 cell = cells.getCell("BB1");
		 Style style_ctkm =cell.getStyle();
		 
		 cell = cells.getCell("BC1");
		 Style style_total_gsbh =cell.getStyle();
		 
		 
		 String query="SELECT PK_SEQ,DIENGIAI FROM NHOMSANPHAM WHERE TUNGAY IS NOT NULL AND DENNGAY IS NOT NULL   ORDER BY SOTT desc ";
	  		
	  		ResultSet rs=db.get(query);
	  		
	  		String chuoingoac= "[0]";
	  		String chuoiselect ="";
	  		String chuoiselect_ct ="";
	  		
	  		String macty="";
	  		int i=0;
	  		while (rs.next()){
	  			
	  			String id=rs.getString("PK_SEQ");
	  			String ma=rs.getString("DIENGIAI");
	  			macty= macty + (macty.length() >0?";"+macty: macty);
	  			
	  			chuoingoac=chuoingoac+", ["+id+"]"; 
	  			chuoiselect=chuoiselect+ " , ISNULL(DS_NHOM.["+id+"],0) AS    'DS_NHOM"+id+"'";
	  			
	  			chuoiselect_ct=chuoiselect_ct+ " , ISNULL(CT.["+id+"],0) AS    'CT_NHOM"+id+"'";
	  			i++;
	  		}
	  		rs.close();
	  		 
		 	query=" SELECT PK_SEQ,DIENGIAI FROM NHOMSANPHAM WHERE TUNGAY IS NOT NULL AND DENNGAY IS NOT NULL   ORDER BY SOTT desc ";
	  		rs=db.get(query);
	  		int tam_ =6;
	  		String[] mangtennhom =new String[i];
	  		String vitri_cot="";
	  		int k=0;
		  		while(rs.next()){
		  		  
		  		 
		  			 vitri_cot=GetExcelColumnName(tam_);
			  		 cell = cells.getCell(vitri_cot+"11");
					 cell.setStyle(style1_header);
					 cell.setValue(rs.getString("DIENGIAI"));
					 tam_++;
			  		  	mangtennhom[k] = rs.getString("PK_SEQ");
			  			k++;
		  		}
		  		rs.close();
	  		
	  		 vitri_cot=GetExcelColumnName(tam_);
	  		 cell = cells.getCell(vitri_cot+"11");
			 cell.setStyle(style1_header);
			 cell.setValue("CT KHOÁN THÁNG");
			 tam_++;
			 rs=db.get(query);
			 while(rs.next()){
		  		  
		  		 
  			 vitri_cot=GetExcelColumnName(tam_);
	  		 cell = cells.getCell(vitri_cot+"11");
			 cell.setStyle(style_header_thucdat);
			 cell.setValue(rs.getString("DIENGIAI"));
			 tam_++;
  		  	 
	  		}
	  		rs.close();
	  		
	  		
	  		 vitri_cot=GetExcelColumnName(tam_);
	  		 cell = cells.getCell(vitri_cot+"11");
			 cell.setStyle(style_header_thucdat);
			 cell.setValue("DS KHOÁN THÁNG");
				 
			 tam_++;
			 
			 vitri_cot=GetExcelColumnName(tam_);
	  		 cell = cells.getCell(vitri_cot+"11");
			 cell.setStyle(style_header_thucdat);
			 cell.setValue("%TH/KH");
				 
			 
			 
			 	tam_++;
			 
			 vitri_cot=GetExcelColumnName(tam_);
	  		 cell = cells.getCell(vitri_cot+"11");
			 cell.setStyle(style_header_thucdat);
			 cell.setValue("SỐ ĐH");
			 
			 	tam_++;
			 
			 vitri_cot=GetExcelColumnName(tam_);
	  		 cell = cells.getCell(vitri_cot+"11");
			 cell.setStyle(style_header_thucdat);
			 cell.setValue("SỐ NT THAM GIA");
			 
			 
			 
		 int rowindex=12;
		 
		 String thang=obj.gettungay().substring(5, 7);
		 String nam=obj.gettungay().substring(0, 4);
		 
		  
		  	  
		   query=	 " SELECT ISNULL(DATA.SO_NT_THAMGIA,0) AS SO_NT_THAMGIA ,ISNULL(DATA.SO_DONHANG,0) AS SO_DONHANG  , DATA.KBH_TEN,DATA.GIAMSAT, DATA.DDKD_FK,DATA.KHUVUC,DATA.TEN,DATA.TRINHDUOCVIEN,DATA.DOANHSO "+chuoiselect_ct+""+chuoiselect + 
					 "  FROM ( "+ 
					 " 	SELECT  KBH.TEN AS KBH_TEN ,KBH.PK_SEQ AS KENHBANHANG,KHU.VUNG_FK,  KHU.TEN AS KHUVUC,  NPP.TEN,HD.DDKD_FK ,ISNULL( "+ 
					 " 	(SELECT TOP 1 TEN FROM GIAMSATBANHANG GSBH INNER JOIN DDKD_GSBH A ON A.GSBH_FK=GSBH.PK_SEQ  "+ 
					 " 	WHERE A.DDKD_FK=DDKD.PK_SEQ ),'')  AS GIAMSAT,DDKD.TEN AS TRINHDUOCVIEN, "+ 
					 " 	SUM(HDSP.SOLUONG* HDSP.DONGIA)  AS DOANHSO ,  COUNT(DISTINCT HD.KHACHHANG_FK) AS SO_NT_THAMGIA,COUNT(DISTINCT HD.PK_SEQ) AS SO_DONHANG  "+ 
					 " 	FROM ERP_HOADONNPP HD INNER JOIN  "+ 
					 " 	ERP_HOADONNPP_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK  "+ 
					 " 	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=HDSP.SANPHAM_FK "+ 
					 " 	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=HD.NPP_FK "+ 
					 " 	INNER JOIN KHUVUC KHU ON KHU.PK_SEQ=NPP.KHUVUC_FK "+ 
					 " 	LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=HD.KBH_FK "+ 
					 " 	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ=HD.DDKD_FK  "+ 
					 " 	WHERE HD.TRANGTHAI NOT IN ( 0, 1, 3, 5) "+ 
					 " 	AND  HD.NGAYXUATHD >='"+obj.gettungay()+"' AND  HD.NGAYXUATHD <='"+obj.getdenngay()+"' " +
					 "  AND KHU.VUNG_FK IN (100012) "+ 
					 " 	GROUP BY HD.DDKD_FK,NPP.TEN,KHU.TEN , KHU.VUNG_FK,DDKD.TEN,DDKD.PK_SEQ ,KBH.TEN,KBH.PK_SEQ "+ 
					 "  "+ 
					 " ) AS DATA "+ 
					 " LEFT JOIN  "+ 
					 " ( "+ 
					 " 	SELECT  * FROM "+ 
					 " 	 ( "+ 
					 " 	SELECT NHANVIEN_FK,NHOMSP_FK,CAST(CHITIEU AS FLOAT) AS CHITIEU  FROM Chitieunhanvien_OTC_SR_NSP "+ 
					 " 	WHERE THANG="+thang+" AND Nam ="+nam+" "+ 
					 " 	) CT "+ 
					 " 	PIVOT  "+ 
					 " 	(  "+ 
					 " 	SUM(CHITIEU) "+ 
					 " 	FOR NHOMSP_FK IN   "+ 
					 " 	( "+ 
					 "   "+ chuoingoac+ 
					 " 	)   "+ 
					 " 	) AS DS "+ 
					 " 	 "+ 
					 " ) CT ON CT.NHANVIEN_FK=DATA.DDKD_FK "+ 
					 " LEFT JOIN  "+ 
					 "  "+ 
					 " (  "+ 
					 " SELECT * FROM ( "+ 
					 " 	SELECT HD.KBH_FK , HD.DDKD_FK  ,NHOM.NSP_FK, "+ 
					 " 	SUM(HDSP.SOLUONG* HDSP.DONGIA)  AS DOANHSO "+ 
					 " 	FROM ERP_HOADONNPP HD INNER JOIN  "+ 
					 " 	ERP_HOADONNPP_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK  "+ 
					 " 	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=HDSP.SANPHAM_FK "+ 
					 " 	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=HD.NPP_FK "+ 
					 " 	INNER JOIN KHUVUC KHU ON KHU.PK_SEQ=NPP.KHUVUC_FK "+ 
					 " 	LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=HD.KBH_FK "+ 
					 " 	INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ=HD.DDKD_FK  "+ 
					 " 	LEFT JOIN NHOMSANPHAM_SANPHAM NHOM ON NHOM.SP_FK=SP.PK_SEQ "+ 
					 " 	 "+ 
					 " 	WHERE HD.TRANGTHAI NOT IN ( 0, 1, 3, 5) "+ 
					 " 	AND HD.NGAYXUATHD >='"+obj.gettungay()+"' AND  HD.NGAYXUATHD <='"+obj.getdenngay()+"'    AND KHU.VUNG_FK IN (100012) "+ 
					 " 	GROUP BY HD.DDKD_FK  ,NHOM.NSP_FK ,HD.KBH_FK "+ 
					 " 	) A "+ 
					 " 	PIVOT  "+ 
					 " 	(  "+ 
					 " 	SUM(DOANHSO) "+ 
					 " 	FOR NSP_FK IN   "+ 
					 " 	( "+ 
					 " 	"+chuoingoac+"  "+ 
					 " 	)   "+ 
					 " 	) AS DS "+ 
					 " 	  "+ 
					 " ) DS_NHOM ON DS_NHOM.DDKD_FK=DATA.DDKD_FK AND DS_NHOM.KBH_FK=DATA.KENHBANHANG "+ 
					 " ORDER BY DATA.GIAMSAT ";
							   
		   System.out.println("Du lieu : "+query);
		   rs=db.get(query);
		   int sott=1;
		   
		   		String gsbh_bk="";
		   		String gsbh="";
		   		
		   		int vitri_dau_bk =rowindex;
		   		
		   		String khuvuc_bk="";
		   
		   while(rs.next()){
			    
			   gsbh=rs.getString("GIAMSAT");
			    
			   if(!gsbh_bk.equals(gsbh)){
					 // thêm dòng total
					 if(rowindex>12){
			 
					   cell = cells.getCell("B"+rowindex);
					   cell.setStyle(style_total_gsbh);
					   cell.setValue(khuvuc_bk);
					   
					   cell = cells.getCell("C"+rowindex);
					   cell.setStyle(style_total_gsbh);
					   cell.setValue(gsbh_bk);
					   
					  
					   
					   cell = cells.getCell("D"+rowindex);
					   cell.setStyle(style_total_gsbh);
					   cell.setValue("");
					   
					   cell = cells.getCell("E"+rowindex);
					   cell.setStyle(style_total_gsbh);
					   cell.setValue("GSBH");
					   
					   cell = cells.getCell("F"+rowindex);
					   cell.setStyle(style_total_gsbh);
					   cell.setValue("");
					   
					   tam_ =6;
						 
						 double total_ctnhom=0;
						 for(int j=0;j<mangtennhom.length;j++ ){
							String idnhom=mangtennhom[j];
							vitri_cot=GetExcelColumnName(tam_);
							cell = cells.getCell(vitri_cot+""+rowindex);
							cell.setStyle(style_total_gsbh);
							cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
							tam_++;
					  		  
						 }
					   
							vitri_cot=GetExcelColumnName(tam_);
							cell = cells.getCell(vitri_cot+""+rowindex);
							cell.setStyle(style_total_gsbh);
							cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
							tam_++;
						 
						 
					   
						 for(int j=0;j<mangtennhom.length;j++ ){
							   String idnhom=mangtennhom[j];
							   vitri_cot=GetExcelColumnName(tam_);
							   cell = cells.getCell(vitri_cot+""+rowindex);
							   cell.setStyle(style_total_gsbh);
							   cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
							   tam_++;
					  		  
						 }
					   
						   vitri_cot=GetExcelColumnName(tam_);
			  			   cell = cells.getCell(vitri_cot+""+rowindex);
						   cell.setStyle(style_total_gsbh);
						   cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
						   tam_++;
						 
					    vitri_cot=GetExcelColumnName(tam_);
		  			 	cell = cells.getCell(vitri_cot+""+rowindex);
					    cell.setStyle(style_total_gsbh);
					    cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
					    tam_++;
					    vitri_cot=GetExcelColumnName(tam_);
		  			  	cell = cells.getCell(vitri_cot+""+rowindex);
					    cell.setStyle(style_total_gsbh);
					    cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
					    tam_++;
					    vitri_cot=GetExcelColumnName(tam_);
		  			 	cell = cells.getCell(vitri_cot+""+rowindex);
					    cell.setStyle(style_total_gsbh);
					    cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
					    tam_++;
							  
					   rowindex++;
					 }
					 vitri_dau_bk= rowindex;
					 
					 khuvuc_bk=rs.getString("KHUVUC");
					 
					 gsbh_bk=gsbh;
					 
					 }
				   	 cell = cells.getCell("A"+rowindex);
					 cell.setValue(sott);
					 
				   	 cell = cells.getCell("B"+rowindex);
					 cell.setValue(rs.getString("KHUVUC"));
					 
					 cell = cells.getCell("C"+rowindex);
					 cell.setValue(rs.getString("TRINHDUOCVIEN"));
					 
					 cell = cells.getCell("D"+rowindex);
					 cell.setStyle(style1_number);
					 cell.setValue(rs.getString("KBH_TEN"));
					 
					 cell = cells.getCell("E"+rowindex);
					 cell.setStyle(style1_number);
					 cell.setValue("TDV");
				 
				 tam_ =6;
				 
				 double total_ctnhom=0;
				 for(int j=0;j<mangtennhom.length;j++ ){
					 String idnhom=mangtennhom[j];
					
					 double doanhso =rs.getDouble("CT_NHOM"+idnhom);
					 total_ctnhom=total_ctnhom+doanhso;
					 
			  			 vitri_cot=GetExcelColumnName(tam_);
				  		 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_number);
						 cell.setValue(doanhso);
						 tam_++;
			  		  
				 }
				 
				 vitri_cot=GetExcelColumnName(tam_);
		  		 cell = cells.getCell(vitri_cot+""+rowindex);
				 cell.setStyle(style1_number);
				 cell.setValue(total_ctnhom);
				 tam_++;
				 
				 double total_dsnhom=0;
				 for(int j=0;j<mangtennhom.length;j++ ){
					 String idnhom=mangtennhom[j];
					 
					 double doanhso =rs.getDouble("DS_NHOM"+idnhom);
					 total_dsnhom=total_dsnhom+ doanhso;
			  			 vitri_cot=GetExcelColumnName(tam_);
				  		 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_number);
						 cell.setValue(doanhso);
						 tam_++;
			  		  
				 }
				 vitri_cot=GetExcelColumnName(tam_);
		  		 cell = cells.getCell(vitri_cot+""+rowindex);
				 cell.setStyle(style1_number);
				 cell.setValue(total_dsnhom);
				 tam_++;
				 
				 double phantram_ds=0;
				 if(total_ctnhom >0){
					 phantram_ds= total_dsnhom *100 /total_ctnhom;
				 }
				 vitri_cot=GetExcelColumnName(tam_);
		  		 cell = cells.getCell(vitri_cot+""+rowindex);
				 cell.setStyle(style1_number);
				 cell.setValue(phantram_ds);
				 tam_++;
			 
				 vitri_cot=GetExcelColumnName(tam_);
		  		 cell = cells.getCell(vitri_cot+""+rowindex);
				 cell.setStyle(style1_number);
				 cell.setValue(rs.getDouble("SO_DONHANG"));
				 tam_++;
				 
				 vitri_cot=GetExcelColumnName(tam_);
		  		 cell = cells.getCell(vitri_cot+""+rowindex);
				 cell.setStyle(style1_number);
				 cell.setValue(rs.getDouble("SO_NT_THAMGIA"));
				 tam_++;
				 
				 sott++;
				 rowindex ++;
				 
				 
		   }
		   rs.close();
		   	//total

			 
		   cell = cells.getCell("B"+rowindex);
		   cell.setStyle(style_total_gsbh);
		   cell.setValue(khuvuc_bk);
		   
		   cell = cells.getCell("C"+rowindex);
		   cell.setStyle(style_total_gsbh);
		   cell.setValue(gsbh_bk);
		   
		  
		   
		   cell = cells.getCell("D"+rowindex);
		   cell.setStyle(style_total_gsbh);
		   cell.setValue("");
		   
		   cell = cells.getCell("E"+rowindex);
		   cell.setStyle(style_total_gsbh);
		   cell.setValue("GSBH");
		   
		   cell = cells.getCell("F"+rowindex);
		   cell.setStyle(style_total_gsbh);
		   cell.setValue("");
		   
		   tam_ =6;
			 
			 double total_ctnhom=0;
			 for(int j=0;j<mangtennhom.length;j++ ){
				String idnhom=mangtennhom[j];
				vitri_cot=GetExcelColumnName(tam_);
				cell = cells.getCell(vitri_cot+""+rowindex);
				cell.setStyle(style_total_gsbh);
				cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
				tam_++;
		  		  
			 }
		   
				vitri_cot=GetExcelColumnName(tam_);
				cell = cells.getCell(vitri_cot+""+rowindex);
				cell.setStyle(style_total_gsbh);
				cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
				tam_++;
			 
			 
		   
			 for(int j=0;j<mangtennhom.length;j++ ){
				   String idnhom=mangtennhom[j];
				   vitri_cot=GetExcelColumnName(tam_);
				   cell = cells.getCell(vitri_cot+""+rowindex);
				   cell.setStyle(style_total_gsbh);
				   cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
				   tam_++;
		  		  
			 }
		   
			   vitri_cot=GetExcelColumnName(tam_);
  			   cell = cells.getCell(vitri_cot+""+rowindex);
			   cell.setStyle(style_total_gsbh);
			   cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
			   tam_++;
			 
			    vitri_cot=GetExcelColumnName(tam_);
				 	cell = cells.getCell(vitri_cot+""+rowindex);
			    cell.setStyle(style_total_gsbh);
			    cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
			    tam_++;
			    vitri_cot=GetExcelColumnName(tam_);
				  	cell = cells.getCell(vitri_cot+""+rowindex);
			    cell.setStyle(style_total_gsbh);
			    cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
			    tam_++;
			    vitri_cot=GetExcelColumnName(tam_);
				 	cell = cells.getCell(vitri_cot+""+rowindex);
			    cell.setStyle(style_total_gsbh);
			    cell.setFormula("=SUM("+vitri_cot+""+(vitri_dau_bk)+":"+vitri_cot +  String.valueOf(rowindex-1) + ")");
			    tam_++;
					  
			   rowindex++;
		 
			 //total tổng cuối cùng
			   
	 
		}catch(Exception err){
			err.printStackTrace();
		}
	}

   
	public static String GetExcelColumnName(int columnNumber)
	 {
		  int dividend = columnNumber;
		  String columnName = "";
		  int modulo;
	
		  while (dividend > 0)
		  {
		   modulo = (dividend - 1) % 26;
		   columnName = (char)(65 + modulo) + columnName;
		   dividend = (int)((dividend - modulo) / 26);
		  } 
	
		  return columnName;
	 }
 
}

