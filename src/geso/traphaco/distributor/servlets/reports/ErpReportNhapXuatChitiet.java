package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.sql.Types;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ErpReportNhapXuatChitiet extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ErpReportNhapXuatChitiet()
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
	    String ctyId = (String)session.getAttribute("congtyId");
	    
		obj.setuserId(userId);
		 obj.setErpCongtyId(ctyId);
		 
		obj.setdiscount("1");
		obj.setvat("0");
		obj.setdvdlId("");
		obj.settype("1");
		obj.InitErp();
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportNhapXuatChitiet.jsp";
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
		String ctyId = (String)session.getAttribute("congtyId");
		
		obj.setErpCongtyId(ctyId);
		
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
		int i=0;
		String[] kbhidchon = request.getParameterValues("kbhids");
		String strkbhid="0";
		 if(kbhidchon!=null){
			 for(  i=0;i<kbhidchon.length;i++){
				 strkbhid+=","+kbhidchon[i];
			 }
		 }
		 obj.setkenhId(strkbhid);
			//vùng
		 String[] vungids = request.getParameterValues("vungids");
			String strvungids="0";
			 if(vungids!=null){
				 for(  i=0;i<vungids.length;i++){
					 strvungids+=","+vungids[i];
				 }
			 }
		 obj.setvungId(strvungids);
			 
			//khu vực 
		 String[] khuvucids = request.getParameterValues("khuvucids");
			String strkhuvucids="0";
			 if(khuvucids!=null){
				 for(  i=0;i<khuvucids.length;i++){
					 strkhuvucids+=","+khuvucids[i];
				 }
			 }
		 obj.setkhuvucId(strkhuvucids);
	  
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String[] fieldsAn = request.getParameterValues("fieldsAn");
		obj.setFieldHidden(fieldsAn);
		String action = request.getParameter("action");
		
		if (action.equals("tao"))
		{
			
			
			
			request.setCharacterEncoding("utf-8");
			response.setCharacterEncoding("utf-8");
		 
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=ReportCongNo" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, fieldsHien, obj); 
					
				} catch (Exception ex)
				{
					obj.setMsg(ex.getMessage());
					request.getSession().setAttribute("errors", ex.getMessage());
					obj.InitErp();
					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportNhapXuatChitiet.jsp";
					response.sendRedirect(nextJSP);
					
				}
			 
		}else{
			obj.InitErp();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpReportNhapXuatChitiet.jsp";
			response.sendRedirect(nextJSP);
		}
		
	
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\ReportNhapXuatChiTiet.xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		
	  		 Cells cells = worksheet_2.getCells();
			   
	  		 	Cell	cell = cells.getCell("P1");
				Style style1=cell.getStyle();
				dbutils db = new dbutils();
				
			/*		
				
				
				String[] khidchon = request.getParameterValues("khIds");
				String khstr="0";
				String idkhstr="0";
				String idnppstr="0";
				String idnvstr="0";
				int i=0;
				 if(khidchon!=null){
					 for(  i=0;i<khidchon.length;i++){
						 System.out.println("khidchon[i]"+khidchon[i]);
							 khstr+=","+ khidchon[i];
							 if(khidchon[i].substring(0,2).equals("KH")){
								 idkhstr+=","+khidchon[i].substring(2);
							 }else  if(khidchon[i].substring(0,2).equals("NP")){
								 
								 idnppstr+=","+khidchon[i].substring(3);
							 }else{
								 idnvstr+=","+khidchon[i].substring(2);
							 }
						 
					 }
				 }
					 
					 
			 obj.setKhachhangIds(khstr);*/
		 /*
			 String query=" delete KHACHHANG_TIMKIEM  ";
			 db.update(query);
			 query=" INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM)  " +
			 		" SELECT  'KH'+ CAST(pk_seq AS VARCHAR(10))  FROM KHACHHANG where 1=1  " ;
			 if(i>0){
				 query +=" and PK_SEQ IN ("+idkhstr+") ";
			 }
			 
			 if(obj.getkenhId().length() > 1){	
				 query = query +	"AND KHACHHANG.PK_SEQ  in ( SELECT KHACHHANG_FK FROM KHACHHANG_KENHBANHANG WHERE KBH_FK IN(" + obj.getkenhId() + ")  ) ";
				}
				if(obj.getvungId().length() >1 ){
					query+=" and KHACHHANG.DIABAN IN ( "+ 
						 " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ=DB.KHUVUC_FK  "+ 
						 " WHERE KV.VUNG_FK IN ("+obj.getvungId()+")  " +
						 		"" +(obj.getkhuvucId().length() >1? " AND KV.PK_SEQ IN ("+obj.getkhuvucId()+") " : "") +
						 		" )";
					  	
				}else{
					if(obj.getkhuvucId().length() >1){
						query+=" and KHACHHANG.DIABAN IN ( "+ 
						 " SELECT DB.PK_SEQ FROM DIABAN DB INNER JOIN KHUVUC KV On  KV.PK_SEQ=DB.KHUVUC_FK  "+ 
						 " WHERE    KV.PK_SEQ IN ("+obj.getkhuvucId()+") "   +
						 		" )";
					}
					
				}
				
			 
			db.update(query);
			
			query=" INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM) SELECT  'NPP'+ CAST(pk_seq AS VARCHAR(10))  FROM NHAPHANPHOI  where 1 =1 " ;
			if(i>0){
				query +="  and  PK_SEQ IN ("+idnppstr+") ";
			} 
			
			if(obj.getvungId().length() >1){
				query+=" and PK_SEQ IN (SELECT NPP_FK FROM NHAPHANPHOI_VUNG where VUNG_FK IN ("+obj.getvungId()+"))";
			}
			if(obj.getkhuvucId().length()>1){
				query+=" and PK_SEQ IN (SELECT NPP_FK FROM NHAPHANPHOI_KHUVUC where KHUVUC_FK IN ("+obj.getkhuvucId()+"))";
			}
			if(obj.getkenhId().length()>1){
				query+=" and PK_SEQ IN (SELECT NPP_FK FROM NHAPP_KBH where kbh_fk IN ("+obj.getkenhId()+"))";
			}
			
			db.update(query);
			
			query="INSERT INTO KHACHHANG_TIMKIEM (MA_TIMKIEM) SELECT  'NV'+ CAST(pk_seq AS VARCHAR(10))  FROM ERP_NHANVIEN where 1=1  " ;
			if(i>0){
				query +=" and  PK_SEQ IN ("+idnvstr+") ";
			}
			db.update(query);*/
			
			 
			
			String[] param = new String[3];
		   
			param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
			param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			param[2] = obj.getErpCongtyId().equals("") ? null : obj.getnppId();
			ResultSet	rs = db.getRsByPro("GET_CHITIET_NHAP_XUAT", param);
		 
			   
	  		worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_REPORT_NHAPXUATCHITIET = worksheets.getSheet("sheet1");
	  		worksheet_REPORT_NHAPXUATCHITIET.setName("Report_Chitiet_NhapXuat");
	  		this.TaoBaoCao(db,rs,worksheet_REPORT_NHAPXUATCHITIET, obj,"CHI TIẾT NHẬP XUẤT ",style1);
		  		 
		  		/*
		  		rs = db.getRsByPro("GET_REPORT_DOANHTHU", param);
		  		Worksheet worksheet_REPORT_DoanhThu = worksheets.addSheet("DoanhThu");
		  		worksheet_REPORT_DoanhThu.setName("Report_DoanhThu");
		  		this.TaoBaoCao(db,rs,worksheet_REPORT_DoanhThu, obj,"DOANH THU",style1);
		  		 
		  		
		  		
		  		
		  		
		  		rs = db.getRsByPro("GET_TONGHOP_CONGNO", param);
		  		Worksheet worksheet_Report_tonghop_congno = worksheets.getSheet("sheet2");
		  		worksheet_Report_tonghop_congno.setName("Report_Tonghop_Congno");
		  		this.TaoBaoCao(db,rs,worksheet_Report_tonghop_congno, obj,"TỔNG HỢP CÔNG NỢ",style1);
		  		
		  		
		  		rs = db.getRsByPro("GET_TONGHOP_CONGNO_GROUP_KHUVUC", param);
		  		Worksheet worksheet_Report_CONGNO_TONGHOP = worksheets.addSheet("TONGHOP");
		  		this.TaoBaoCao(db,rs,worksheet_Report_CONGNO_TONGHOP, obj,"BÁO CÁO CÔNG NỢ- DOANH THU -DOANH SỐ ",style1);
		  		
		  		rs = db.getRsByPro("GET_DANHSACH_KH_NOXAU", param);
		  		Worksheet worksheet_Report_DANHSACH_KH_NOXAU = worksheets.addSheet("DANHSACH_KH_NOXAU");
		  		this.TaoBaoCao(db,rs,worksheet_Report_DANHSACH_KH_NOXAU, obj,"DANH SÁCH KHÁCH HÀNG NỢ XẤU",style1);*/
		  			
		  		
			workbook.save(out);
			fstream.close();
			db.shutDown();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void TaoBaoCao(dbutils db,ResultSet rs,Worksheet worksheet, IStockintransit obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   
			   for(int i=0;i<30;i++){
				   cells.setColumnWidth(i, 20.0f);   
			   }
			
			    cells.setRowHeight(0, 50.0f);
			    Cell cell = cells.getCell("A1");
			    ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);
			    
			    cell = cells.getCell("A3");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.gettungay() + "   Đến ngày: " + obj.getdenngay());
				cell = cells.getCell("A4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + obj.getDateTime());
				
				cell = cells.getCell("B4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getuserTen());
				
				 
				

			   worksheet.setGridlinesVisible(false);
			   
			 
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 4;

			   for( int i =1 ; i <=socottrongSql ; i ++  )
			   {
			    cell = cells.getCell(countRow,i-1 );
			    
		    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
		    	
		    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
		    	
			   }
			   countRow ++;
			   
			  
			   while(rs.next())
			   {
			    for(int i = 1; i <= socottrongSql; i ++)
			    {
			     cell = cells.getCell(countRow,i-1 );
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    	 cell.setStyle(style12);
			    	 ReportAPI.getCellStyle_double(cell, "Arial", Color.BLACK, false, 9,  rs.getDouble(i));
			     // ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell, "Arial", Color.BLACK, false, 9, rs.getString(i));
			    //  ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
			     }
			    }
			    ++countRow;
			   }
			   
			   if(rs!=null)rs.close();
			    

			 
			  }catch(Exception ex){
				  ex.printStackTrace();
				    
			  }
	}
 
}

