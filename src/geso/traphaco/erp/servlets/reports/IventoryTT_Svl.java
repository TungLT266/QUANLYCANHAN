package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.record.formula.functions.Npv;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;



public class IventoryTT_Svl extends HttpServlet 
{	 
    public IventoryTT_Svl() 
    {
        super();
    }
    
    private String gettenuser(String userId_)
    {
		dbutils db=new dbutils();
		String sql_getnam="select ten from nhanvien where pk_seq = "+ userId_;
		ResultSet rs_tenuser=db.get(sql_getnam);
		if(rs_tenuser!= null){
			try{
			  while(rs_tenuser.next()){
				  return rs_tenuser.getString("ten");
			  }
			}catch(Exception er){
				return "";
			}finally{
				try{
					if(rs_tenuser != null) rs_tenuser.close();
					if(db != null) db.shutDown();
					
				}catch(java.sql.SQLException e){
					
				}
			}
		}
		return "";
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		
		IStockintransit obj = new Stockintransit();	
		obj.settungay("");
		obj.setdenngay("");
		obj.setMsg("");
		obj.setnppId("");
		obj.init();
		obj.setuserId(userId);
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/InventoryTT.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		String nextJSP = "/TraphacoHYERP/pages/Center/InventoryTT.jsp";
		Utility util = new Utility();
		try {

			obj.setuserTen((String) session.getAttribute("userTen")!=null?
					(String) session.getAttribute("userTen"):"");
			
			obj.settungay(util.antiSQLInspection(request.getParameter("tungay"))!=null?
					util.antiSQLInspection(request.getParameter("tungay")):"");
			
			obj.setuserId(util.antiSQLInspection(request.getParameter("userId"))!=null?
				util.antiSQLInspection(request.getParameter("userId")):"");
			
			obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?
					util.antiSQLInspection(request.getParameter("nppId")):"");
			
			obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?
					util.antiSQLInspection(request.getParameter("kenhId")):"");
			
			obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null?
					util.antiSQLInspection(request.getParameter("dvkdId")):"");
			
			obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?
					util.antiSQLInspection(request.getParameter("vungId")):"");
			
			obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null?
					util.antiSQLInspection(request.getParameter("nhanhangId")):"");
			
			obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?
					util.antiSQLInspection(request.getParameter("khuvucId")):"");
			
			obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?
					util.antiSQLInspection(request.getParameter("chungloaiId")):"");
			
			
			String avalible = util.antiSQLInspection(request.getParameter("piece"));
			String condition = "";
			if(avalible.equals("1")){
				condition += " and tkn.AVAILABLE > 0";
			}
			
			obj.init();
			session.setAttribute("obj", obj);

			//response.setContentType("application/vnd.ms-excel");
			//response.setHeader("Content-Disposition", "attachment; filename=TonKhoHienTai.xls");
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=TonHienTai.xlsm");
			
			boolean isTrue = CreatePivotTable(out, obj, condition);
			if(!isTrue)
			{
				PrintWriter writer = new PrintWriter(out);
				writer.write("Khong tao duoc bao cao trong thoi gian nay...!!!");
			}			
		} catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
	}
	
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TonHienTaiTT.xlsm");
		
		//System.out.println("Duong dan file: " + getServletContext().getInitParameter("path") + "\\TonHienTaiTT.xlsm");
		// fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TonHienTaiTT.xlsm");		

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen());	     
	     boolean isTrue = CreateStaticData(workbook, obj, condition);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	  
	    
	    String tieude = "BÁO CÁO TỒN KHO HIỆN TẠI";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    

	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("AA1"); 	cell.setValue("KenhBanHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1"); 	cell.setValue("DonViKinhDoanh");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AC1");	cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");	cell.setValue("KhuVuc");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");	cell.setValue("MaNhaPhanPhoi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");	cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AG1"); 	cell.setValue("NhanHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AH1"); 	cell.setValue("ChungLoai");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AI1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AJ1"); 	cell.setValue("TenSanPham"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AK1"); 	cell.setValue("Kho");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AL1"); 	cell.setValue("SoLuongLe");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AM1"); 	cell.setValue("SoLuongThung");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AN1"); 	cell.setValue("SoTien");  ReportAPI.setCellHeader(cell);
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
       
	    geso.traphaco.center.util.Utility Uti_center = new geso.traphaco.center.util.Utility();
		
	 	String sql = "";
	 	if (obj.getkenhId().length() > 0)
			sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() + "'";
		if (obj.getvungId().length() > 0)
			sql = sql + " and v.pk_seq ='" + obj.getvungId() + "'";
		if (obj.getkhuvucId().length() > 0)
			sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() + "'";
		if (obj.getdvkdId().length() > 0)
			sql = sql + " and dvkd.PK_SEQ ='" + obj.getdvkdId() + "'";
		if (obj.getnhanhangId().length() > 0)
			sql = sql + " and nh.pk_seq ='" + obj.getnhanhangId() + "'";
		if (obj.getchungloaiId().length() > 0)
			sql = sql + " and cl.pk_seq ='" + obj.getchungloaiId() + "'";
		
		sql = sql + condition;
	    
		String query = "select kbh.ten as Channel, sp.ma as Sku_code, sp.ten as SKU, tkn.AVAILABLE as Piece,k.ten as Warehouse, " +
		" V.PK_SEQ AS VUNGID, V.TEN AS VUNGTEN, KV.PK_SEQ AS KVID, KV.TEN AS KVTEN, " +
		" NPP.PK_SEQ AS NPPID, NPP.TEN AS NPPTEN, " +
        " nh.ten as Brans,cast(tkn.AVAILABLE/cast(qc.soluong1 as int) as int) as Quantily,"+
		" dvkd.donvikinhdoanh as Business_unit,cl.ten as Category, "+
		" case when tkn.AVAILABLE * npk.giamua * 1.1 is null then 0 else tkn.AVAILABLE * npk.giamua*1.1 end as Amount "+
		" from (select * from nhapp_kho" +
		        " where npp_fk in " + Uti_center.quyen_npp(obj.getuserId()) +"" +
				" and sanpham_fk in " + Uti_center.quyen_sanpham(obj.getuserId()) +" " +
				" and kbh_fk in "+ Uti_center.quyen_kenh(obj.getuserId()) ;
		if(obj.getnppId().length() > 0)
			query = query + " and npp_fk = '" + obj.getnppId()+"'";
				
		query = query + ") tkn "+
		" inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk "+
		" inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk "+
		" inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+
		" inner join kho k on k.pk_seq = tkn.kho_fk "+
		" left join quycach qc on qc.dvdl1_fk = sp.dvdl_fk and qc.sanpham_fk = sp.pk_seq "+
		" left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " +
		" left join chungloai cl on cl.pk_seq = sp.chungloai_fk " +
		" left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk " +
		" inner join nhaphanphoi npp on npp.pk_seq = tkn.npp_fk " +
		" left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq  " +
 		" left join vung v on kv.vung_fk = v.pk_seq " +
	    " inner join (select distinct bgm.kenh_fk as kbh_fk,bgm_sp.sanpham_fk,bgmnpp.npp_fk,bgm_sp.giamuanpp as giamua from banggiamuanpp_npp bgmnpp "+ 
        " inner join banggiamuanpp bgm on bgm.pk_seq = bgmnpp.banggiamuanpp_fk "+
        " inner join bgmuanpp_sanpham bgm_sp on bgm_sp.bgmuanpp_fk = bgm.pk_seq ";
		if(obj.getnppId().length() > 0)
			query = query + " where bgmnpp.npp_fk = '"+  obj.getnppId() + "'" ;
		query = query + " ) npk on " +
        " npk.sanpham_fk = tkn.sanpham_fk and tkn.kbh_fk = npk.kbh_fk and tkn.npp_fk = npk.npp_fk " +
        " where 1 = 1 " + sql;
	
		System.out.println("Ton hien tai: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{// se do rong cho cac cot se dung
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 15.0f);
				cells.setColumnWidth(2, 15.0f);
				cells.setColumnWidth(3, 15.0f);
				cells.setColumnWidth(4, 15.0f);			
				cells.setColumnWidth(5, 15.0f);	
				cells.setColumnWidth(6, 15.0f);	
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 15.0f);
				cells.setColumnWidth(9, 15.0f);
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String SKU =rs.getString("SKU");
					String Piece =rs.getString("Piece");			
										
					String Warehouse = rs.getString("Warehouse");
					String SkuCode = rs.getString("SKU_code");		
					float Quantily = rs.getFloat("Quantily");
					String BusinessUnit = rs.getString("Business_unit");
					String Category = rs.getString("Category");
					String Brands = rs.getString("Brans");
					long Amount = Math.round(rs.getFloat("Amount"));
					//System.out.println("Amount la: " + Amount + "\n");

					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(Channel);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(BusinessUnit);
					cell = cells.getCell("AC" + Integer.toString(i));	cell.setValue(rs.getString("VUNGTEN"));
					cell = cells.getCell("AD" + Integer.toString(i));	cell.setValue(rs.getString("KVTEN"));
					cell = cells.getCell("AE" + Integer.toString(i));	cell.setValue(rs.getString("NPPID"));
					cell = cells.getCell("AF" + Integer.toString(i));	cell.setValue(rs.getString("NPPTEN"));
					cell = cells.getCell("AG" + Integer.toString(i)); 	cell.setValue(Brands);
					cell = cells.getCell("AH" + Integer.toString(i)); 	cell.setValue(Category);
					cell = cells.getCell("AI" + Integer.toString(i)); 	cell.setValue(SkuCode);
					cell = cells.getCell("AJ" + Integer.toString(i)); 	cell.setValue(SKU);
					cell = cells.getCell("AK" + Integer.toString(i)); 	cell.setValue(Warehouse);				
					cell = cells.getCell("AL" + Integer.toString(i)); 	cell.setValue(Float.parseFloat(Piece));
					cell = cells.getCell("AM" + Integer.toString(i)); 	cell.setValue(Quantily);
					cell = cells.getCell("AN" + Integer.toString(i)); 	cell.setValue(Amount); 
					i++;
					/*if(i > 65000)
					{						
						if(rs!=null) rs.close();
						if(db != null) 
							db.shutDown();
						throw new Exception("Xin loi.Du lieu vuot qua 65.000 dong. Vui long chon dieu kien lay bao cao");
					}*/
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
				/*
				getAn(workbook,49);
				PivotTables pivotTables = worksheet.getPivotTables();

			    //Adding a PivotTable to the worksheet
				String pos = Integer.toString(i-1);	
			    int index = pivotTables.add("=AA1:AN" + pos,"A9","PivotTableDemo");
		       
			    PivotTable pivotTable = pivotTables.get(index);//truyen index

			    pivotTable.setRowGrand(false);
			    pivotTable.setColumnGrand(true);
			    pivotTable.setAutoFormat(true);

			    pivotTable.setAutoFormatType(PivotTableAutoFormatType.TABLE10);
		 
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 0);	pivotTable.getRowFields().get(0).setAutoSubtotals(true);  
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 1);	pivotTable.getRowFields().get(1).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 2);	pivotTable.getRowFields().get(2).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 3);	pivotTable.getRowFields().get(3).setAutoSubtotals(true);  
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 5);	pivotTable.getRowFields().get(4).setAutoSubtotals(true);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 8);	pivotTable.getRowFields().get(5).setAutoSubtotals(false);
			    pivotTable.addFieldToArea(PivotFieldType.ROW, 9);	pivotTable.getRowFields().get(6).setAutoSubtotals(false);
			    
			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, 10); //kho
			    
			    pivotTable.addFieldToArea(PivotFieldType.DATA, 11);	
			    pivotTable.getDataFields().get(0).setDisplayName("SoLuongLe");
			    pivotTable.getDataFields().get(0).setNumber(3);
			    
			    pivotTable.addFieldToArea(PivotFieldType.DATA, 13);	
			    pivotTable.getDataFields().get(1).setDisplayName("SoTien");
			    pivotTable.getDataFields().get(1).setNumber(3);
			    
			    pivotTable.addFieldToArea(PivotFieldType.COLUMN, pivotTable.getDataField());*/
			
			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			if(db != null) db.shutDown();
			return false;
		}
		
		if(db != null) db.shutDown();
		return true;
		
	}
	
	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		 style = cell.getStyle();
	        Font font1 = new Font();
	        font1.setColor(mau);
	        font1.setBold(dam);
	        font1.setSize(size);
	        style.setFont(font1);
	        cell.setStyle(style);
	}
	
	private void getAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
