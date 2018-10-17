package geso.traphaco.erp.beans.baocaocandoiketoan.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.DKLocBaoCaoKeToan;
import geso.traphaco.center.util.PhatSinhKeToanItem;
import geso.traphaco.erp.beans.baocaocandoiketoan.IErp_BaoCaoCanDoiKeToanList;
import geso.traphaco.erp.db.sql.dbutils;

import java.io.FileInputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Erp_BaoCaoCanDoiKeToanList  extends Phan_Trang implements IErp_BaoCaoCanDoiKeToanList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String congTyId;
	private String msg;
	
	private DKLocBaoCaoKeToan dieuKienLoc;
	private List<String> colunmNameList;
	private List<PhatSinhKeToanItem> viewList;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	private dbutils db;
	private String month;
	private String year;
	private String monthdk;
	private String yeardk;
	
	private ResultSet RsBCCDKT; 
	private ResultSet RsBCHDKD; 
	private ResultSet RsBCLCTT; 
	private ResultSet RsBCDPS; 
	
	public Erp_BaoCaoCanDoiKeToanList()
	{
		this.congTyId = "";
		this.msg= ""; 
		
		this.dieuKienLoc = new DKLocBaoCaoKeToan();
		this.colunmNameList = new ArrayList<String>();
		this.viewList = new ArrayList<PhatSinhKeToanItem>();
		this.month = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));		
		this.year = getDate().substring(0, 4);
		
		this.monthdk = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));		
		this.yeardk = getDate().substring(0, 4);
		
		this.currentPages = 1;
		this.num = 1;
		this.db = new dbutils();
	}
	
	public void init()
	{
		this.dieuKienLoc.init(db);
		// EXEC REPORT_CDKT '2016-08-01','2016-07-01','0'
		
		if(Integer.parseInt(this.monthdk)<10)
		{
			this.monthdk = "0"+this.monthdk;
		}
		
		if(Integer.parseInt(this.month)<10)
		{
			this.month = "0"+this.month;
		}
		
		String dauky = this.yeardk+"-"+this.monthdk +"-01";
		String cuoiky = this.year+"-"+this.month+"-01";
				
		
		String sql = " SELECT DATEADD(month, 1, '"+dauky+"') DAUKY, DATEADD(month, 1, '"+cuoiky+"') CUOIKY ";
		
		try
		{
			ResultSet rs = db.get(sql);
			
			if(rs!=null)
			{
				while(rs.next())
				{
					dauky = rs.getString("DAUKY").substring(0, 10);
					cuoiky = rs.getString("CUOIKY").substring(0, 10);
				}
				rs.close();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		
		
		{ // REPORT_CDKT
			String query = "EXEC [REPORT_CDKT] '" + cuoiky + "' , '" + dauky  + "' , " + this.congTyId;
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			try{
				this.RsBCCDKT = this.db.get(query);				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		{ // REPORT_KQHDKD
			String query = "EXEC [REPORT_HDKD] '" + cuoiky.substring(0, 7) + "' , '" + dauky.substring(0, 7)  + "' , " + this.congTyId;
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			try{
				this.RsBCHDKD = this.db.get(query);				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		{// REPORT_LCTT
			String query = "EXEC [REPORT_LCTT] '" + cuoiky.substring(0, 7) + "' , '" + dauky.substring(0, 7)  + "' , " + this.congTyId;
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			try{
				this.RsBCLCTT = this.db.get(query);				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		{ // REPORT CDPS
			
			dauky = this.year+"-"+this.month +"-01";					
			
			sql = " SELECT DATEADD(month, 1, '"+dauky+"') CUOIKY ";
			
			try
			{
				ResultSet rs = db.get(sql);
				
				if(rs!=null)
				{
					while(rs.next())
					{
						cuoiky = rs.getString("CUOIKY").substring(0, 8)+"00";
					}
					rs.close();
				}
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			
			String query = "EXEC [REPORT_CANDOIPHATSINH] '" + dauky + "' , '" + cuoiky + "' , " + this.congTyId;
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			try{
				this.RsBCDPS = this.db.get(query);				
				
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		}
		
		
	}
	
	public boolean exportExcel(ServletOutputStream out, String fileName)
	{
		try{
			

			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			
			fstream = new FileInputStream(fileName);
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			String query =
				"EXEC [REPORT_CANDOIPHATSINH] " +
				"'" + this.dieuKienLoc.getTuNgay() + "'" +
				", '" + this.dieuKienLoc.getDenNgay()  + "'" +
				", " + this.dieuKienLoc.getCongTyId();
			
			System.out.println("query init: \n" + query + "\n------------------------------------------------------------");
			ResultSet rs = this.db.get(query);
			
			////////////////////
			Font font = new Font();
		    font.setColor(Color.RED);//mau chu
		    font.setSize(16);// size chu
		   	font.setBold(true);
			cells.setRowHeight(0, 20.0f);
		    Cell cell = cells.getCell("A1");
		    Style style = cell.getStyle();
		    style.setFont(font); 
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
		    
		    String tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG THEO NGÀY";
//		    if(obj.getFromMonth().length() > 0)
//		    	tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG THEO THÁNG";
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
		            
//			cells.setRowHeight(2, 18.0f);
//			cell = cells.getCell("A2");
//			ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A4");
		    
		    cell = cells.getCell("A4");
		    cell.setValue("Công ty: " + this.dieuKienLoc.getSoHieuTaiKhoanNo() + " - " + this.dieuKienLoc.getTenTaiKhoanNo(db));
		    
             cell = cells.getCell("A5"); 
             cell.setValue("Khách hàng: " + this.dieuKienLoc.getSoHieuTaiKhoanNo() + " - " + this.dieuKienLoc.getTenTaiKhoanNo(db));  
             
//		    icell.setValue("Tài khoản: " + this.dieuKienLoc.getSoHieuTaiKhoanNo() + " - " + this.dieuKienLoc.getTenTaiKhoanNo(db));   f(obj.getFromMonth() == "")
//		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
////		    else
////		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );
//		    
//		    cells.setRowHeight(3, 18.0f);
//		    cell = cells.getCell("B4"); 
//		    if(obj.getFromMonth() == "")
//		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
////		    else
////		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );
//			
//		    cells.setRowHeight(4, 18.0f);
//		    cell = cells.getCell("A5");
//		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
//		    
//		    cells.setRowHeight(5, 18.0f);
//		    cell = cells.getCell("A6");
//		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());

			////////////////////
			ResultSetMetaData rsmd = rs.getMetaData();
			int rowNumber = rsmd.getColumnCount();
			int countRow = 12;
			
//			Cell cell1 = cells.getCell(3, 7);
//			cell1.setValue("Tháng " + thang + " Năm " + nam);
			
			int stt = 0;
			while(rs.next())
			{
				stt++;
				for(int i = 1;i <=rowNumber ; i ++)
				{
					cell = cells.getCell(countRow, i - 1);
					if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL)
					{
						cell.setValue(rs.getDouble(i));
						cell = ReportAPI.CreateBorderSetting2(cell);
					}
					else
					{
						cell.setValue(rs.getString(i));
						cell = ReportAPI.CreateBorderSetting2(cell);
					}
				}
				++countRow;
			}
			
			for(int i = 1; i <= rowNumber ; i++)
			{
				ReportAPI.CreateBorderSetting3(cells.getCell(countRow - 1,i - 1));
			}
			
			workbook.save(out);
			fstream.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void DBClose()
	{
		if (this.db != null)
		{
			this.db.shutDown();
		}
	}
	
	public int getNum() {
		return num;
	}
	public void setNum(int num) {
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}
	public int[] getListPages() {
		return listPages;
	}
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}
	public int getCurrentPages() {
		return currentPages;
	}
	public void setCurrentPages(int currentPages) {
		this.currentPages = currentPages;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getMsg() {
		return msg;
	}

	public void setCongTyId(String congTyId) {
		this.congTyId = congTyId;
	}

	public String getCongTyId() {
		return congTyId;
	}

	public void setDieuKienLoc(DKLocBaoCaoKeToan dieuKienLoc) {
		this.dieuKienLoc = dieuKienLoc;
	}

	public DKLocBaoCaoKeToan getDieuKienLoc() {
		return dieuKienLoc;
	}

	public void setViewList(List<PhatSinhKeToanItem> viewList) {
		this.viewList = viewList;
	}

	public List<PhatSinhKeToanItem> getViewList() {
		return viewList;
	}

	public void setColunmNameList(List<String> colunmNameList) {
		this.colunmNameList = colunmNameList;
	}

	public List<String> getColunmNameList() {
		return colunmNameList;
	}

	
	public void setMonth(String month) {
		
		this.month = month;
	}

	
	public String getMonth() {
		
		if(this.month.length() >0){
			return this.month;	
		}else{
			return this.getDate().substring(5, 7);
		}
	}

	
	public void setYear(String year) {
		
		this.year = year;
	}

	public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getYear() {
		
		if(this.year.length()>0){
			return this.year;	
		}else{
			return this.getDate().substring(0, 4);
		}
	}

	
	public void setMonthDK(String monthdk) {
		
		this.monthdk = monthdk;
	}

	
	public String getMonthDK() {
		
		return this.monthdk;
	}

	
	public void setYearDK(String yeardk) {
		
		this.yeardk = yeardk;
	}

	
	public String getYearDK() {
		
		return this.yeardk;
	}

	
	public ResultSet getRsBCCDKT() {		
		return this.RsBCCDKT;
	}

	
	public ResultSet getRsBCHDKD() {
		
		return this.RsBCHDKD;
	}
	
	public ResultSet getRsBCLCTT() {
		
		return this.RsBCLCTT;
	}

	
	public ResultSet getRsBCCDPS() {
		
		return this.RsBCDPS;
	}
}