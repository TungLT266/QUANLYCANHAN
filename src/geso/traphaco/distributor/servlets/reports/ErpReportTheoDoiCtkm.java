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

public class ErpReportTheoDoiCtkm extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpReportTheoDoiCtkm()
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
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportTheodoiCtkm.jsp";
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
					
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportTheodoiCtkm.jsp";
					response.sendRedirect(nextJSP);
					
				}
			}
		}else{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportTheodoiCtkm.jsp";
			response.sendRedirect(nextJSP);
		}
		
	
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			dbutils db=new dbutils();
			
			
			String strfstream = getServletContext().getInitParameter("path") + "\\ReportCTKM.xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
			
			Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		worksheet_2.setName("REPORT_THEODOI_CTKM");
	  		 
				 
			 
	  		this.Dodulieu_excel(worksheet_2,obj,db);
				  
	  		Worksheet worksheet_tonghop = worksheets.getSheet("sheet2");
	  		
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
	    ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BÁO CÁO DOANH SỐ TỔNG VÀ DOANH SỐ KHÁNG SINH");
	    
	    cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
		
		cell = cells.getCell("B4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());

		String[] param = new String[2];
  		param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
		param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();

		
		 
		 String query=" select PK_SEQ,DIENGIAI from NHOMSANPHAM where LOAITHANHVIEN='1' and TRANGTHAI=1 ";
	  		
	  		ResultSet rs=db.get(query);
	  		
	  		String chuoingoac= "[0]";
	  		String chuoiselect ="";
	  		String macty="";
	  		int i=0;
	  		while (rs.next()){
	  			
	  			String id=rs.getString("PK_SEQ");
	  			String ma=rs.getString("DIENGIAI");
	  			macty= macty + (macty.length() >0?";"+macty: macty);
	  			
	  			chuoingoac=chuoingoac+", ["+id+"]"; 
	  			chuoiselect=chuoiselect+ " , ISNULL(DS_NHOM.["+id+"],0) AS    'DS_NHOM"+id+"'";
	  			i++;
	  		}
	  		rs.close();
	  		
	  		 
		cell = cells.getCell("AX1");
		 Style style1_number=cell.getStyle();
		 
	 	 	 cell = cells.getCell("AZ1");
		 Style style1_header=cell.getStyle();
	 	 cell = cells.getCell("AY1");
		 Style style_footer=cell.getStyle();
		 cell = cells.getCell("BB1");
		 Style style_ctkm =cell.getStyle();
		 
		 cell = cells.getCell("A11");
		 cell.setStyle(style1_header);
		 cell.setValue("STT");
		 cell = cells.getCell("B11");
		 cell.setStyle(style1_header);
		 cell.setValue("Khu vực");
		 cell = cells.getCell("C11");
		 cell.setStyle(style1_header);
		 cell.setValue("ASM");
		 
		 cell = cells.getCell("D11");
		 cell.setStyle(style1_header);
		 cell.setValue("Kế hoạch DS");
		 cell = cells.getCell("E11");
		 cell.setStyle(style1_header);
		 cell.setValue("Thực hiện DS");
		 cell = cells.getCell("F11");
		 cell.setStyle(style1_header);
		 cell.setValue("TH/KH");
		 
		 	query="   SELECT PK_SEQ,DIENGIAI FROM NHOMSANPHAM WHERE TUNGAY IS NOT NULL AND DENNGAY IS NOT NULL   ORDER BY SOTT desc ";
	  		rs=db.get(query);
	  		int tam_ =7;
	  		String[] mangtennhom =new String[i];
	  		System.out.println("thu tu i : "+i);
	  		System.out.println("mang ten nhom : "+mangtennhom.length);
	  		int k=0;
	  		while(rs.next()){
	  		  String vitri_cot="";
	  		  if(rs.getString("PK_SEQ").equals("100020")){
	  			  // nếu là nhóm kháng sinh
	  			  		vitri_cot=GetExcelColumnName(tam_);
					 cell = cells.getCell(vitri_cot+"11");
					 cell.setStyle(style1_header);
					 cell.setValue("Kế hoạch kháng sinh");
					  tam_++;
					  vitri_cot=GetExcelColumnName(tam_);
					 cell = cells.getCell(vitri_cot+"11");
					 cell.setStyle(style1_header);
					 cell.setValue("Thực hiện kháng sinh");
					 
					  tam_++;
					  vitri_cot=GetExcelColumnName(tam_);
					 cell = cells.getCell(vitri_cot+"11");
					 cell.setStyle(style1_header);
					 cell.setValue("TH/KH kháng sinh");
					  tam_++;
		  		   
	  		  }else{
	  			 vitri_cot=GetExcelColumnName(tam_);
		  		 cell = cells.getCell(vitri_cot+"11");
				 cell.setStyle(style1_header);
				 cell.setValue(rs.getString("DIENGIAI"));
				 tam_++;
	  		  }
	  		  	mangtennhom[k] = rs.getString("PK_SEQ");
	  			k++;
	  		}
	  		rs.close();
	  		
		 
		 int rowindex=12;
		 
		  
		  	  
		   query=	 " SELECT DATA.KHUVUC ,DATA.KHUVUCID ,DATA.TEN AS ASM , 0 AS KEHOACH_DS,  "+ 
						 " 	ISNULL(DS.DOANHSOTONG,0) AS THUCDAT_DS  "+ 
						 " 	  "+ chuoiselect+
						 " 	 FROM (  "+ 
						 " 	 "+ 
						 " 		SELECT KHU.TEN AS KHUVUC, ASM.TEN ,KHU.PK_SEQ AS KHUVUCID  FROM ASM_KHUVUC  "+ 
						 " 		INNER JOIN ASM ON ASM.PK_SEQ=ASM_KHUVUC.ASM_FK "+ 
						 " 		INNER JOIN KHUVUC KHU ON KHU.PK_SEQ=ASM_KHUVUC.KHUVUC_FK "+ 
						 "  ) DATA  "+ 
						 "  LEFT JOIN  "+ 
						 "  (  "+ 
						 " 	SELECT  KHU.PK_SEQ AS KHUVUCID,   "+ 
						 " 	SUM(HDSP.SOLUONG* HDSP.DONGIA) AS DOANHSOTONG "+ 
						 " 	FROM ERP_HOADONNPP HD INNER JOIN  "+ 
						 " 	ERP_HOADONNPP_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK  "+ 
						 " 	INNER JOIN SANPHAM SP ON SP.PK_SEQ=HDSP.SANPHAM_FK "+ 
						 " 	INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=HD.NPP_FK "+ 
						 " 	INNER JOIN KHUVUC KHU ON KHU.PK_SEQ=NPP.KHUVUC_FK "+ 
						 " 	LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=HD.KBH_FK "+ 
						 " 	WHERE HD.TRANGTHAI NOT IN ( 0, 1, 3, 5) "+ 
						 " 	AND HD.NGAYXUATHD >='"+obj.gettungay()+"' AND HD.NGAYXUATHD <='"+obj.getdenngay()+"'   and KHU.VUNG_FK in (100012) "+ 
						 " 	GROUP BY    KHU.PK_SEQ "+ 
						 " 	 "+ 
						 "  ) DS ON DATA.KHUVUCID=DS.KHUVUCID "+ 
						 "   "+ 
						 "  LEFT JOIN  "+ 
						 "  ( "+ 
						 " 	 "+ 
						 " 		SELECT   DS.*  FROM  "+ 
						 " 		( "+ 
						 " 			SELECT  KHU.PK_SEQ AS KHUVUCID,  NHOM.NSP_FK, "+ 
						 " 			SUM(HDSP.SOLUONG* HDSP.DONGIA) AS DOANHSOTONG "+ 
						 " 			FROM ERP_HOADONNPP HD INNER JOIN  "+ 
						 " 			ERP_HOADONNPP_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK  "+ 
						 " 			INNER JOIN  SANPHAM SP ON SP.PK_SEQ=HDSP.SANPHAM_FK "+ 
						 " 			INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=HD.NPP_FK "+ 
						 " 			INNER JOIN KHUVUC KHU ON KHU.PK_SEQ=NPP.KHUVUC_FK "+ 
						 " 			LEFT JOIN NHOMSANPHAM_SANPHAM NHOM ON NHOM.SP_FK=SP.PK_SEQ "+ 
						 " 			WHERE HD.TRANGTHAI NOT IN ( 0, 1, 3, 5) "+ 
						 " 			AND HD.NGAYXUATHD >='"+obj.gettungay()+"' AND HD.NGAYXUATHD <='"+obj.getdenngay()+"'   and KHU.VUNG_FK in (100012) "+ 
						 " 			GROUP BY    KHU.PK_SEQ, NHOM.NSP_FK "+ 
						 " 		) A "+ 
						 " 		PIVOT  "+ 
						 " 		(  "+ 
						 " 		SUM(DOANHSOTONG) "+ 
						 " 		FOR NSP_FK IN   "+ 
						 " 		( "+ 
						 " 			"+chuoingoac+"  "+ 
						 " 		)   "+ 
						 " 		) AS DS "+ 
						 "  ) AS DS_NHOM ON DS_NHOM.KHUVUCID=DATA.KHUVUCID ";
		   
		   System.out.println("Du lieu : "+query);
		   rs=db.get(query);
		   int sott=1;
		   while(rs.next()){
			   
			   	 cell = cells.getCell("A"+rowindex);
				 cell.setValue(sott);
				 
			   	 cell = cells.getCell("B"+rowindex);
				 cell.setValue(rs.getString("KHUVUC"));
				 
				 cell = cells.getCell("C"+rowindex);
				 cell.setValue(rs.getString("ASM"));
				 
				 cell = cells.getCell("D"+rowindex);
				 cell.setStyle(style1_number);
				 cell.setValue(rs.getDouble("KEHOACH_DS"));
				 
				 cell = cells.getCell("E"+rowindex);
				 cell.setStyle(style1_number);
				 cell.setValue(rs.getDouble("THUCDAT_DS"));
				 
				 cell = cells.getCell("F"+rowindex);
				 cell.setStyle(style1_number);
				 double phantram_KEHOACH_DS=0;
				 if(rs.getDouble("KEHOACH_DS") >0){
					 phantram_KEHOACH_DS= rs.getDouble("THUCDAT_DS")*100/rs.getDouble("KEHOACH_DS");
				 }
				 cell.setValue(phantram_KEHOACH_DS);
				   tam_ =7;
				 for(int j=0;j<mangtennhom.length;j++ ){
					 String idnhom=mangtennhom[j];
					 String vitri_cot="";
					 double doanhso =rs.getDouble("DS_NHOM"+idnhom);
			  		  if(idnhom.equals("100020")){
			  			  // nếu là nhóm kháng sinh
						 vitri_cot=GetExcelColumnName(tam_);
						 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_number);
						 cell.setValue(0);
						  tam_++;
						  vitri_cot=GetExcelColumnName(tam_);
						 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_number);
						 cell.setValue(doanhso);
						 
						  tam_++;
						  vitri_cot=GetExcelColumnName(tam_);
						 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_number);
						 cell.setValue(0);
						  tam_++;
				  		   
			  		  }else{
			  			 vitri_cot=GetExcelColumnName(tam_);
				  		 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_number);
						 cell.setValue(doanhso);
						 tam_++;
			  		  }
			  		   
				 }
				 sott++;
				 rowindex ++;
				 
				 
		   }
		   rs.close();
		   	//total
		   	 cell = cells.getCell("A"+rowindex);
		   	cell.setStyle(style1_header);
			 cell.setValue("TOTAL : ");
			 cell = cells.getCell("B"+rowindex);
			   	cell.setStyle(style1_header);
				 cell.setValue("");
				 cell = cells.getCell("C"+rowindex);
				   	cell.setStyle(style1_header);
					 cell.setValue("");
					 
			 cell = cells.getCell("D"+rowindex);
			 cell.setStyle(style1_header);
			 cell.setFormula("=SUM(D12"+":D" +  String.valueOf(rowindex-1) + ")");
			 
			 
			 cell = cells.getCell("E"+rowindex);
			 cell.setStyle(style1_header);
			 cell.setFormula("=SUM(E12"+":E" +  String.valueOf(rowindex-1) + ")");
			 
			 cell = cells.getCell("F"+rowindex);
			 cell.setStyle(style1_header);
			 cell.setFormula("=SUM(F12"+":F" +  String.valueOf(rowindex-1) + ")");
			 
			 
			 cell = cells.getCell("F"+rowindex);
			 cell.setStyle(style1_header);
			 cell.setFormula("=SUM(F12"+":F" +  String.valueOf(rowindex-1) + ")");
			 
			   tam_ =7;
				 for(int j=0;j<mangtennhom.length;j++ ){
					 
					 String idnhom=mangtennhom[j];
					 String vitri_cot="";
					  
			  		  if(idnhom.equals("100020")){
			  			  // nếu là nhóm kháng sinh
						 vitri_cot=GetExcelColumnName(tam_);
						 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_header);
						 cell.setFormula("=SUM("+vitri_cot+"12"+":"+vitri_cot+""+ String.valueOf(rowindex-1) + ")");
						  tam_++;
						  vitri_cot=GetExcelColumnName(tam_);
						 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_header);
						 cell.setFormula("=SUM("+vitri_cot+"12"+":"+vitri_cot+"" +  String.valueOf(rowindex-1) + ")");
						 
						  tam_++;
						  vitri_cot=GetExcelColumnName(tam_);
						 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_header);
						 cell.setFormula("=SUM("+vitri_cot+"12"+":"+vitri_cot+"" +  String.valueOf(rowindex-1) + ")");
						  tam_++;
				  		   
			  		  }else{
			  			 vitri_cot=GetExcelColumnName(tam_);
				  		 cell = cells.getCell(vitri_cot+""+rowindex);
						 cell.setStyle(style1_header);
						 cell.setFormula("=SUM("+vitri_cot+"12"+":"+vitri_cot+"" +  String.valueOf(rowindex-1) + ")");
						 tam_++;
			  		  }
			  		   
				 }
			 
	 
		}catch(Exception err){
			err.printStackTrace();
		}
	}

	private void Dodulieu_excel(Worksheet worksheet_2, IStockintransit obj,
			dbutils db) {
				try{
		
						Cells cells = worksheet_2.getCells();
			  			cells.setRowHeight(0, 50.0f);
					    Cell cell = cells.getCell("A1");
					    ReportAPI.getCellStyle(cell, Color.RED, true, 14, "BÁO CÁO DOANH SỐ TỔNG VÀ DOANH SỐ KHÁNG SINH");
					    
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
						 
		  		 	 	 cell = cells.getCell("AZ1");
						 Style style1_header=cell.getStyle();
					 	 cell = cells.getCell("AY1");
						 Style style_footer=cell.getStyle();
						 cell = cells.getCell("BB1");
						 Style style_ctkm =cell.getStyle();
						 
						 ResultSet	rs = db.getRsByPro("GET_REPORT_THEODOI_CTKM", param);
						 int cot=3;
						 int rowindex=7;
						 // lưu doanh số này vào 1 bảng hashtable
						 
						 // THỰC HIỆN VẼ DOANH SỐ TRƯỚC
						 rowindex=9;
						 String ctkm_bk="";
						 String ctkm="";
						 int i=0;
						 int vitri_dau_bk =0;
						 
						 while (rs.next()){
							 if(i==0){
								 ctkm=rs.getString("scheme");
							 }
							 if(!ctkm_bk.equals(ctkm)){
								 // thêm dòng total
								 if(rowindex>9){
								   cell = cells.getCell("A"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setValue(" MN TOTAL");
								   
								   cell = cells.getCell("B"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(B"+(vitri_dau_bk)+":B" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("C"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(C"+(vitri_dau_bk)+":C" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("D"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(D"+(vitri_dau_bk)+":D" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("E"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(E"+(vitri_dau_bk)+":E" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("F"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(F"+(vitri_dau_bk)+":F" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("G"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(G"+(vitri_dau_bk)+":G" +  String.valueOf(rowindex-1) + ")");
								   
								   
								   cell = cells.getCell("H"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(H"+(vitri_dau_bk)+":H" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("I"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(I"+(vitri_dau_bk)+":I" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("J"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(J"+(vitri_dau_bk)+":J" +  String.valueOf(rowindex-1) + ")");
								   

								   cell = cells.getCell("K"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(K"+(vitri_dau_bk)+":K" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("L"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(L"+(vitri_dau_bk)+":L" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("M"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(M"+(vitri_dau_bk)+":M" +  String.valueOf(rowindex-1) + ")");
								   

								   cell = cells.getCell("N"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(N"+(vitri_dau_bk)+":N" +  String.valueOf(rowindex-1) + ")");
								   
								   cell = cells.getCell("O"+rowindex);
								   cell.setStyle(style_footer);
								   cell.setFormula("=SUM(O"+(vitri_dau_bk)+":O" +  String.valueOf(rowindex-1) + ")");
								   
								 
								   
								 }
								   
								 
								 // thiết lập cái tiêu đề
								 rowindex=rowindex+6;
								 vitri_dau_bk= rowindex+1;
								 this.createTieuDe(db,rowindex,worksheet_2,ctkm,style1_header,style_ctkm);
								 ctkm_bk = ctkm;
								 rowindex++;
								 
							 }
							  
								
							   cell = cells.getCell("A"+rowindex);
							   cell.setValue(rs.getString("khuvuc"));
							   cell = cells.getCell("B"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(rs.getDouble("NPP_THAMGIA"));
							   
							   cell = cells.getCell("C"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(rs.getDouble("DOANHSO_THAMGIA"));
							   
							   cell = cells.getCell("D"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(rs.getDouble("XUATDONHANG"));
							   
							   cell = cells.getCell("E"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(rs.getDouble("NT_THAMGIA"));
							   
							   cell = cells.getCell("F"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(rs.getDouble("THANHTIENDS"));
							   
							   cell = cells.getCell("G"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(rs.getDouble("SOXUAT"));
							   
							   cell = cells.getCell("H"+rowindex);
							   cell.setStyle(style1_number);
							   double phantram_NPP_THAMGIA=0;
							   double phantram_XUATDONHANG=0;
							   double phantram_THANHTIENDS=0;
							   
							   if(rs.getDouble("NPP_THAMGIA") >0){
								   phantram_NPP_THAMGIA =rs.getDouble("NT_THAMGIA") *100/ rs.getDouble("NPP_THAMGIA");
							   }
							   if(rs.getDouble("DOANHSO_THAMGIA") >0){
								   phantram_THANHTIENDS =rs.getDouble("THANHTIENDS") *100/ rs.getDouble("DOANHSO_THAMGIA");
							   }
							   if(rs.getDouble("XUATDONHANG") >0){
								   phantram_XUATDONHANG =rs.getDouble("SOXUAT") *100/ rs.getDouble("XUATDONHANG");
							   }
							    
							   cell.setValue(phantram_NPP_THAMGIA);
							   
							   cell = cells.getCell("I"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(phantram_THANHTIENDS);

							   cell = cells.getCell("J"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(phantram_XUATDONHANG);

							   cell = cells.getCell("K"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(0);
							   
							   cell = cells.getCell("L"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(0);

							   cell = cells.getCell("M"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(0);

							   cell = cells.getCell("N"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(0);
							   
							   cell = cells.getCell("O"+rowindex);
							   cell.setStyle(style1_number);
							   cell.setValue(0);
							   
							   rowindex++;
							 
						 }
						 rs.close();
						 // thực hiện dòng cuối cùng 
				 
						   
				}catch(Exception er){
					
					er.printStackTrace();
					}
		
	}

 
	
	private void createTieuDe(dbutils db, int rowindex, Worksheet worksheet_2, String SCHEME, Style style1_header, Style style_ctkm) {
		try{
		// TODO Auto-generated method stub
			Cells cells = worksheet_2.getCells();
		 
		 	Cell cell = cells.getCell("A"+(rowindex-4));
		 
		 	String query= " select pk_seq ,diengiai,tungay,denngay from ctkhuyenmai where scheme='"+SCHEME+"' ";
			ResultSet rs=db.get(query);
			String diengiai="";
			String hieuluc ="";
			String ctkmid="";
			if(rs.next()){
				diengiai=rs.getString("diengiai");
				hieuluc="Từ ngày: "+rs.getString("tungay")+ " --> "+ rs.getString("denngay")  ;
				ctkmid=rs.getString("pk_seq");
				
			}
			rs.close();
			cell.setStyle(style_ctkm);
			cell.setValue(diengiai);
			
			cell = cells.getCell("A"+(rowindex-3));
			cell.setValue(hieuluc);
			
			
			
			query=  " SELECT DIENGIAI FROM DIEUKIENKHUYENMAI  WHERE PK_SEQ IN " +
					" (SELECT A.DKKHUYENMAI_FK FROM CTKM_DKKM A WHERE CTKHUYENMAI_FK="+ctkmid+" )";
			ResultSet rsdk=db.get(query);
			String diengiaidieukien="";
			if(rsdk.next()){
				diengiaidieukien= rsdk.getString("DIENGIAI");
			}
			rsdk.close();
		
			cell = cells.getCell("A"+(rowindex-2));
			cell.setValue(diengiaidieukien);
			
			query= 	" SELECT sp.ma,sp.ten FROM DIEUKIENKHUYENMAI dk "+
					" inner join DIEUKIENKM_SANPHAM dk_sp on dk.pk_seq=dk_sp.DIEUKIENKHUYENMAI_FK "+
					" inner join sanpham sp on sp.pk_seq=dk_sp.sanpham_fk "+
					" WHERE dk.PK_SEQ IN (SELECT A.DKKHUYENMAI_FK FROM CTKM_DKKM A WHERE CTKHUYENMAI_FK="+ctkmid+" ) ";
			String sp_ten="";
			rs=db.get(query);
			while (rs.next()){
				sp_ten=sp_ten+ ","+rs.getString("ten");
				
			}
			rs.close();
		 
			cell = cells.getCell("B"+(rowindex-2));
			cell.setStyle(style1_header);
			cell.setValue("Giỏ hàng : "+sp_ten);
			
		   cell = cells.getCell("A"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("Khu Vực (ASM)");
		   cell = cells.getCell("B"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("SLNT TGIA");
		   cell = cells.getCell("C"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("DSO TGIA  CT (1000 đồng)");
		   
		   cell = cells.getCell("D"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("SUAT ĐƠN HÀNG");
		   
		   cell = cells.getCell("E"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("SLNT TGIA");
		   
		   cell = cells.getCell("F"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("DSO THAM GIA");
		   
		   cell = cells.getCell("G"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("SUAT ĐƠN HÀNG");
		   
		   cell = cells.getCell("H"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("SLNT TGIA ");
		   
		   cell = cells.getCell("I"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("DSO ");

		   cell = cells.getCell("J"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("SUẤT ĐƠN HÀNG");

		   cell = cells.getCell("K"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("TỶ TRỌNG CÁC KV");
		   
		   cell = cells.getCell("L"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue(" CP ");

		   cell = cells.getCell("M"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("% CP");

		   cell = cells.getCell("N"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("OSLA");
		   
		   cell = cells.getCell("O"+rowindex);
		   cell.setStyle(style1_header);
		   cell.setValue("SIX-BB");

		}catch(Exception er){
			
			er.printStackTrace();
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

