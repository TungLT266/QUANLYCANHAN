package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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


public class Dailypurchase_TTSvl extends HttpServlet 
{
	 /**
   * 
   */
  private static final long serialVersionUID = 1L;
	public Dailypurchase_TTSvl() {
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
	    obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/RDailypurchase_center.jsp";
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
		Utility util = new Utility();
	    IStockintransit obj = new Stockintransit();
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 
	    String action = util.antiSQLInspection(request.getParameter("action"));

	    if(userId ==null)
	    	userId ="";
	    obj.setuserId(userId);
	    
   	 	String nppId=request.getParameter("nppId");
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
     	
   	 	obj.setuserTen(userTen);
   	 	
	   	 String kenhId=request.getParameter("kenhId");
	   	 if(kenhId == null)
	   		 kenhId ="";
	   	 obj.setkenhId(kenhId);
	   	 
	   	 String dvkdId=request.getParameter("dvkdId");
	   	 if(dvkdId == null)
	   		 dvkdId ="";
	   	 obj.setdvkdId(dvkdId);
	   	 
	   	 String nhanhangId=request.getParameter("nhanhangId");
	   	 if(nhanhangId ==null)
	   		 nhanhangId = "";
	   	 obj.setnhanhangId(nhanhangId);
	   	 
	   	 String chungloaiId=request.getParameter("chungloaiId");
	   	 if(chungloaiId==null)
	   		chungloaiId = "";
	   	 obj.setchungloaiId(chungloaiId);
	   	 
	   	 String tungay=request.getParameter("Sdays");
	   	 if(tungay ==null)
	   		 tungay ="";
	   	 obj.settungay(tungay);
	   	 
	   	 String denngay=request.getParameter("Edays");
	   	 if(denngay == null)
	   		 denngay ="";
	   	 obj.setdenngay(denngay);
	   	 
	 	   	 String vungId=request.getParameter("vungId");
	   	 if(vungId ==null)
	   		 vungId = "";
	   	 obj.setvungId(vungId);
	   	 
	   	 String khuvucId=request.getParameter("khuvucId");
	   	 if(khuvucId == null)
	   		 khuvucId ="";
	   	 obj.setkhuvucId(khuvucId);
	   	 
	   	 String gsbhId=request.getParameter("gsbhId");
	   	 if(gsbhId ==null)
	   		 gsbhId ="";
	   	 obj.setgsbhId(gsbhId);
	   	 
	   	 String sanphamId=request.getParameter("sanphamId");
	   	 if(sanphamId == null)
	   		 sanphamId ="";
	   	 obj.setsanphamId(sanphamId);
	   	 
	   	 String dvdlId=request.getParameter("dvdlId");
		 if(dvdlId == null)
			 dvdlId ="";
		 obj.setdvdlId(dvdlId);
		 
		 String nextJSP = "/TraphacoHYERP/pages/Center/RDailypurchase_center.jsp";
		 OutputStream out = response.getOutputStream();
		 if(action.equals("tao")){
			 try
			 {				
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "Attachment; filename=HangNhapKhoTT" + this.getPiVotName() + ".xlsm");
	
				System.out.println("__Toi toi day...");
				
		        if(!CreatePivotTable(out, obj))
		        {
		        	PrintWriter writer = new PrintWriter(out);
		        	System.out.println("118.Loi.........: ");
					writer.println("Xin loi: Khong co bao cao trong thoi gian nay");			
					writer.close();
		        }
			}
			 catch(Exception ex)
			 {
				 System.out.println("115.Loi: " + ex.getMessage());
				 
				obj.setMsg(ex.getMessage());	
				response.sendRedirect(nextJSP);
			 }
		 }
		 else{	
			 obj.init();
			 session.setAttribute("obj", obj);
			 session.setAttribute("userId", obj.getuserId());
			 response.sendRedirect(nextJSP);
			 
		}
	}
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {  		
	    try
	    {
	    	
			Workbook workbook = new Workbook();		
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\Dailypurchase_TTSvl.xlsm");
			
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
		     CreateStaticHeader(workbook, obj.gettungay(),obj.getdenngay(), obj.getuserTen());	    
		     CreateStaticData(workbook,obj);
		     
		     workbook.save(out);
		     fstream.close();
	    }
	    catch(Exception ex)
	    {
	    	System.out.println("117.Loi: " + ex.getMessage());
	    	return false;
	    }
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
	    
	    String tieude = "BÁO CÁO HÀNG NHẬP KHO";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    String message = "có VAT";
		
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A2");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + dateFrom + "" );
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + dateTo + "" );
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + UserName);
	    
	   
	    cell = cells.getCell("CA1");		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("CB1");		cell.setValue("DonviKinhDoanh");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CC1");		cell.setValue("Mien");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CD1");		cell.setValue("Vung");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CE1");		cell.setValue("MaCN/DT");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CF1");		cell.setValue("CN/DT");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("CG1");		cell.setValue("NhanHang");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CH1");		cell.setValue("ChungLoai");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CI1");		cell.setValue("SoChungTu");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CJ1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CK1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CL1");		cell.setValue("Kho");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CM1");		cell.setValue("TinhThanh");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CN1");		cell.setValue("QuanHuyen");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("CO1");		cell.setValue("NgayChungTu");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CP1");		cell.setValue("SoLuong");			ReportAPI.setCellHeader(cell);	    
		cell = cells.getCell("CQ1");		cell.setValue("SoTien");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CR1");		cell.setValue("SoLuongKien");			ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("CS1");		cell.setValue("NgayHoaDon");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("CT1");		cell.setValue("Solo");			ReportAPI.setCellHeader(cell);
		    
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		String sql = 
				" select  kbh.ten as Channel, v.ten as Region,kv.ten as Area,nhsp.solo as solo,  tt.ten as Province,qh.ten as Town , "+   
				"   	npp.sitecode as Distributor_code, npp.ten as Distributor, "+   
				"   	dvkd.donvikinhdoanh as Business_unit, nhsp.sanpham_fk as SKU_code, "+   
				"   	sp.ten as SKU,nh.ngaynhan as Purdate,nh.ngaychungtu, "+   
				"   	nhan.ten as Brands, cl.ten as Category, kho.ten as khoTen, nhsp.soluong as Piece, nh.ycxk_fk as Series_number ,"+   
				"   	nhsp.SOLUONG*nhsp.DONGIA as Amount,"+   
				"   	isnull( cast( (nhsp.soluong * qc.SOLUONG2 / SOLUONG1) as numeric(18, 3)),0)  as soluongThung "+   
				"    from nhaphang nh "+   
				"   	 inner join nhaphang_sp nhsp on nhsp.nhaphang_fk = nh.pk_seq "+   
				"   	 inner join kho on kho.pk_seq = nhsp.KHONHAN_FK "+   
				"   	 left  join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk "+   
				"   	 inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk  "+   
				"   	 left join donvikinhdoanh dvkd on dvkd.pk_seq = nh.dvkd_fk "+   
				"   	 inner join sanpham sp on sp.pk_seq = nhsp.sanpham_fk  "+   
				"   	 left join nhanhang nhan on nhan.pk_seq = sp.nhanhang_fk "+   
				"   	 left join chungloai cl on cl.pk_seq = sp.chungloai_fk  "+   
				"   	 left join quycach qc on qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk = qc.dvdl1_fk and qc.dvdl2_fk=100018 "+   
				"   	 left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+   
				"   	 left join vung v on v.pk_seq = kv.vung_fk "+   
				"   	 left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk "+   
				"   	 left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk "+   
				" where nh.trangthai ='1'" + " and nh.ngaynhan >= '"+ obj.gettungay() + "'" + " and nh.ngaynhan <= '"+ obj.getdenngay() + "'";

		if (obj.getnppId() != "") {
			sql = sql + " and  npp.pk_seq=" + obj.getnppId();
		}

		// phanquyen
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		sql += " and npp.pk_seq in " + ut.quyen_npp(obj.getuserId())
				+ " and kbh.pk_seq in " + ut.quyen_kenh(obj.getuserId())
				+ " and sp.pk_seq in " + ut.quyen_sanpham(obj.getuserId());

		 if(obj.getkenhId().length() > 0) 
			 sql = sql + " and kbh.pk_seq ='" + obj.getkenhId() +"'";
		 if(obj.getvungId().length()>0) 
			 sql = sql +" and v.pk_seq ='" + obj.getvungId() +"'";
		 if(obj.getkhuvucId().length() > 0)
			 sql = sql + " and kv.pk_seq ='" + obj.getkhuvucId() +"'";
		 if(obj.getdvkdId().length()> 0) 
			 sql = sql +" and sp.dvkd_fk ='" + obj.getdvkdId() +"'";
		 if(obj.getnppId().length()>0) 
			 sql = sql +" and npp.pk_seq ='" + obj.getnppId() +"'";
		 if(obj.getnhanhangId().length()>0) 
			 sql = sql +" and nhan.pk_seq ='"+ obj.getnhanhangId() +"'";
		 if(obj.getchungloaiId().length()>0)
			 sql = sql +" and cl.pk_seq ='"+ obj.getchungloaiId() +"'";
		 if(obj.getdvdlId().length()>0) 
			 sql = sql + " and sp.dvdl_fk ='"+ obj.getdvdlId() +"'";
		 if(obj.getsanphamId().length()>0) 
			 sql = sql + " and sp.pk_seq = '"+ obj.getsanphamId() +"'";
		 
		 sql += " order by nh.ngaynhan";
		
		 System.out.println("___Hang Nhap Kho: " + sql + "\n");
		ResultSet rs = db.get(sql);

		int i = 2;
		if (rs != null) 
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

				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{

					// lay tu co so du lieu, gan bien
					String Channel = rs.getString("Channel");
					String Region = rs.getString("Region");
					String Area = rs.getString("Area");
					String Distributor = rs.getString("Distributor");
					double Amount = rs.getDouble("Amount");
					String DistributorCode = rs.getString("Distributor_code");
					String BusinessUnit = rs.getString("Business_unit");
					String Brands = rs.getString("Brands");
					String Category = rs.getString("Category");
					double Piece = rs.getDouble("Piece");
					String SKU = rs.getString("SKU");
					String khoTen = rs.getString("khoTen");
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String SKUcode = rs.getString("SKU_Code");
					String Purdate = rs.getString("Purdate");
					String SeriesNumber = rs.getString("Series_number");
					String ngayhoadon=rs.getString("NgayChungTu");
					String solo=rs.getString("solo");
					float thung = rs.getFloat("soluongThung");
					
					
					cell = cells.getCell("CA" + Integer.toString(i));	cell.setValue(Channel);
					cell = cells.getCell("CB" + Integer.toString(i)); 	cell.setValue(BusinessUnit);
					cell = cells.getCell("CC" + Integer.toString(i));	cell.setValue(Region);
					cell = cells.getCell("CD" + Integer.toString(i));	cell.setValue(Area);
					cell = cells.getCell("CE" + Integer.toString(i));	cell.setValue(DistributorCode);
					cell = cells.getCell("CF" + Integer.toString(i));	cell.setValue(Distributor);
					cell = cells.getCell("CG" + Integer.toString(i));	cell.setValue(Brands);
					cell = cells.getCell("CH" + Integer.toString(i));	cell.setValue(Category);
					cell = cells.getCell("CI" + Integer.toString(i));	cell.setValue(SeriesNumber);
					cell = cells.getCell("CJ" + Integer.toString(i));	cell.setValue(SKUcode);
					cell = cells.getCell("CK" + Integer.toString(i));	cell.setValue(SKU); //10
					cell = cells.getCell("CL" + Integer.toString(i));	cell.setValue(khoTen); //11
					cell = cells.getCell("CM" + Integer.toString(i));	cell.setValue(Province);
					cell = cells.getCell("CN" + Integer.toString(i));	cell.setValue(Town);
					cell = cells.getCell("CO" + Integer.toString(i));	cell.setValue(Purdate); //14
					
					cell = cells.getCell("CP" + Integer.toString(i));	cell.setValue(Piece); //15
					cell = cells.getCell("CQ" + Integer.toString(i));	cell.setValue(Amount);	 //16
					cell = cells.getCell("CR" + Integer.toString(i));	cell.setValue(thung);	
					cell = cells.getCell("CS" + Integer.toString(i));	cell.setValue(ngayhoadon);
					cell = cells.getCell("CT" + Integer.toString(i));	cell.setValue(solo);
							i++;
					/*if (i > 65000) 
					{
						if (rs != null)
						{
							rs.close();
						}
						if(db != null) 
							db.shutDown();
						throw new Exception("Du lieu vuot qua file Excel. Vui long chon dieu kien bao cao");
					}*/
				}
				if (rs != null) 
				{
					rs.close();
				}
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay....!!");
				}
				

			} 
			catch (Exception e) 
			{
				throw new Exception(
						"Xin loi. Da xay ra loi trong qua trinh dien du lieu vao file Excel");
			}
		} 
		else {
			if(db != null) 
				db.shutDown();			
			return false;
		}
		
		if(db != null) 
			db.shutDown();
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
