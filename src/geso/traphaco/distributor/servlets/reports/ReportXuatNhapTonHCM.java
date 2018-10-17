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
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import java.util.*;

public class ReportXuatNhapTonHCM extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public ReportXuatNhapTonHCM()
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
		String nextJSP = "/TraphacoHYERP/pages/Distributor/ReportXuatNhapTonHCM.jsp";
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
		
		String ddkd = request.getParameter("ddkdId");// <!---
		if (ddkd == null)
			ddkd = "";
		obj.setDdkd(ddkd);
		System.out.println("ddkd id "+obj.getDdkd());
		
		String[] khoId = request.getParameterValues("khoId"); 
		String str = "";
		if (khoId != null)
		{
			for(int i = 0; i < khoId.length; i++)
				str += khoId[i] + ",";
			if(str.length() > 0)
				str = str.substring(0, str.length() - 1);
		}
		System.out.println("khoID " + str);
		obj.setkhoId(str);
		
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
		
		obj.setChiTietXNT_Lo_Tong(util.antiSQLInspection(request.getParameter("layctnxsolo_tong")) != null ? util.antiSQLInspection(request.getParameter("layctnxsolo_tong")) : "");
		
		
		obj.setTrangthai(util.antiSQLInspection(request.getParameter("laytien")) != null ? util.antiSQLInspection(request.getParameter("laytien")) : "");
		
		obj.setHangdiduong(util.antiSQLInspection(request.getParameter("hangdiduong")) != null ? util.antiSQLInspection(request.getParameter("hangdiduong")) : "");
		
		
		
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
				response.setHeader("Content-Disposition", "Attachment; filename=BaoCaoXuatNhapTon(NPP)" + this.getPiVotName() + ".xlsm");
				OutputStream out = response.getOutputStream();
				try
				{
					CreatePivotTable(out, response, request, fieldsHien, obj); 
					
				} catch (Exception ex)
				{
					obj.init();
					obj.setMsg(ex.getMessage());

					session.setAttribute("obj", obj);
					session.setAttribute("userId", userId);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/ReportXuatNhapTonHCM.jsp";
					response.sendRedirect(nextJSP);
					
				}
			}else{
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/TraphacoHYERP/pages/Distributor/ReportXuatNhapTonHCM.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		
	
		
	}
	
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request, String[] fieldsHien, IStockintransit obj) throws Exception
	{
		try
		{
			
			 
			String  strfstream = getServletContext().getInitParameter("path") + "\\BaoCaoXuatNhapTon_ChiTiet.xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		
	  		Cells cells = worksheet_2.getCells();
			   
	  		Cell cell = cells.getCell("P1");
			Style style1=cell.getStyle();
				
			 
				dbutils db = new dbutils(); 
		  		worksheets = workbook.getWorksheets();
		  		
		  		
				 
				ResultSet rs;
				String[] param = new String[5];
				param[0] = obj.getkhoId().equals("") ? null : obj.getkhoId();
				param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
				param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
				param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
				if(obj.getkhId().trim().length() > 0)
				{
					param[4] = obj.getkhId().equals("") ? null : obj.getkhId();
					rs = db.getRsByPro("REPORT_XUATNHAPTON_KYGUI_CHITIET_COLUMN", param);
				}
				else if(obj.getNccId().trim().length() > 0)
				{
					param[4] = obj.getNccId().equals("") ? null : obj.getNccId();
					rs = db.getRsByPro("REPORT_XUATNHAPTON_NCC_CHITIET_COLUMN", param);
				}
				else if(obj.getDdkd().trim().length() > 0)
				{
					System.out.println("ddkd id "+obj.getDdkd());
					param[4] = obj.getDdkd().equals("") ? null : obj.getDdkd();
					rs = db.getRsByPro("REPORT_XUATNHAPTON_DDKD_CHITIET_COLUMN", param);
				}
				else
				{
					param = new String[4];
			
					param[0] = "100000";
					param[1] = obj.gettungay().equals("") ? null : obj.gettungay();
					param[2] = obj.getdenngay().equals("") ? null : obj.getdenngay();
					param[3] = obj.getnppId().equals("") ? null : obj.getnppId();
			
					//rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_COLUMN", param);
					
					String query = "delete kho_tmp";
					db.update(query);
					if(obj.getkhoId().length() > 0){
						query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (" + obj.getkhoId() + ")";
					}
					else {
						query = "insert into kho_tmp(kho_fk) select pk_seq from kho where pk_seq in (select pk_seq from kho where trangthai = 1)";
					}
					db.update(query);

						//param[0] = param[0].substring(0, 6);
						rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_HCM", param);
					 
				}
				
				this.TaoBaoCao(db, rs, worksheet_2, obj,"XUẤT NHẬP TỒN CHI TIẾT", style1);
			 
				
			
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
 
	private String[] getColumnExportExcel(ResultSetMetaData rsmd,
			String[] hideColumnArray, int socottrongSql) {
		
		// TODO Auto-generated method stub
		String[] mangtrave ;
		String strmang="";
		try{
			
		for(int i = 1 ; i <= socottrongSql; i++)
		{
			 
			String tencot=rsmd.getColumnName(i);
			// System.out.println("ten cot: "+tencot);
			 
			 boolean bien=false;
			 for(int j=0;j<hideColumnArray.length;j++ ){
					 if(tencot.equals(hideColumnArray[j])){
						 //System.out.println("ten cot: "+tencot);
						 //System.out.println("hideColumnArray["+j+"] : "+hideColumnArray[j]);
						 bien=true;
					 }
			 }
			 if(!bien){
				 if(strmang.length()==0){
					 strmang= i+"";
				 }else{
					 strmang=strmang+ ","+i;
				 }
			 }
		 
		}
		}catch(Exception er){
			er.printStackTrace();
		}
		
		//System.out.println("str mang: "+strmang);
		mangtrave=strmang.split(",");
		
		return mangtrave;
	}
 

}

