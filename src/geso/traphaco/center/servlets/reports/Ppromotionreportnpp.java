package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;



import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
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

public class Ppromotionreportnpp extends HttpServlet {	

	private static final long serialVersionUID = 1L;

	public Ppromotionreportnpp() {
        super();
       
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		
		Utility Ult = new Utility();
		String userId = Ult.getUserId(querystring);	
		IStockintransit obj = new Stockintransit();
		
		obj.setuserTen(userTen);
		obj.setuserId(userId);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/PromotionReportnpp.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream(); 
		Stockintransit obj = new Stockintransit();
		String nextJSP = "/TraphacoHYERP/pages/Distributor/PromotionReportnpp.jsp";
		HttpSession session = request.getSession();
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		Utility util = new Utility();
		try {	
	    	if(util.antiSQLInspection(request.getParameter("unghang"))!= null)
	    		obj.setUnghang("1");
			else
				obj.setUnghang("0");
	    	
	    	if(util.antiSQLInspection(request.getParameter("cohoadon"))!= null)
	    		obj.setCoHoadon("1");
			else
				obj.setCoHoadon("0");
	    				
			System.out.println(obj.getUnghang());
			
			obj.setuserTen((String) session.getAttribute("userTen")!=null?
						(String) session.getAttribute("userTen"):"");			
			
			obj.setuserId(util.antiSQLInspection(request.getParameter("userId"))!=null? 
					util.antiSQLInspection(request.getParameter("userId")):"");
			
			obj.setYear(util.antiSQLInspection(request.getParameter("year"))!=null? 
					util.antiSQLInspection(request.getParameter("year")):"" );
			
			obj.setMonth(util.antiSQLInspection(request.getParameter("month"))!=null?
					util.antiSQLInspection(request.getParameter("month")):"");
			
			
			obj.setPrograms(util.antiSQLInspection(request.getParameter("programs"))!=null? 
					util.antiSQLInspection(request.getParameter("programs")):"");
			
			obj.setFieldShow(request.getParameterValues("fieldsHien")!=null?
					request.getParameterValues("fieldsHien"):null);
			
			String sql = "";
			if(obj.getPrograms().length()>0){
				sql +=" AND ctkm.SCHEME = N'" + obj.getPrograms() +"'";
			}
			String action = request.getParameter("action");
			if(action.equals("create")){
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXuatKhuyenMai.xlsm");
				
				boolean isTrue = CreatePivotTable(out, obj,sql);
				
				if(!isTrue){
					PrintWriter writer = new PrintWriter(out);
					writer.write("Xin loi. Khong co bao cao trong thoi gian nay..");
					writer.close();
				}
			}		
			
		} catch (Exception ex) {
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
		obj.init();
		session.setAttribute("obj", obj);
	}
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String sql) throws Exception
    {  
		String chuoi=getServletContext().getInitParameter("path") + "\\XuatKhuyenMai.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);		
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		CreateStaticHeader(workbook, obj.getMonth(), obj.getYear(), obj.getuserTen());
		boolean isTrue = CreateStaticData(workbook, obj,sql);
		if(!isTrue){
			return false;
		}else{
			workbook.save(out);
		}	
		
		fstream.close();
		return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, 
				String month, String year, String UserName)throws Exception 
	{
		try{
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
		    
		    cell.setValue("BÁO CÁO XUẤT KHUYẾN MÃI ĐÃ SỬ DỤNG");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + month + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + year + "" );
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
		    cell.setValue("Ngày báo cáo: " + this.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
		    cell.setValue("Được tạo bởi Nhà phân phối:  " + UserName);
		   
		    cell = cells.getCell("AA1"); cell.setValue("Kenh ban hang"); 		   
		    cell = cells.getCell("AB1"); cell.setValue("Ma chuong trinh");
		    cell = cells.getCell("AC1"); cell.setValue("Ten chuong trinh");	
		    cell = cells.getCell("AD1"); cell.setValue("Loai chuong trinh");
		    cell = cells.getCell("AE1"); cell.setValue("Ma san pham");		  
		    cell = cells.getCell("AF1"); cell.setValue("Ten san pham");   
		    cell = cells.getCell("AG1"); cell.setValue("Nhan hang");
		    cell = cells.getCell("AH1"); cell.setValue("Chung loai");
		    cell = cells.getCell("AI1"); cell.setValue("Ma nha phan phoi");
		    cell = cells.getCell("AJ1"); cell.setValue("Tinh thanh");
		    cell = cells.getCell("AK1"); cell.setValue("Quan huyen");
		  	cell = cells.getCell("AL1"); cell.setValue("Ma khach hang");
		    cell = cells.getCell("AM1"); cell.setValue("Ten khach hang");
		    cell = cells.getCell("AN1"); cell.setValue("Ngay");
		    cell = cells.getCell("AO1"); cell.setValue("So luong");
		    cell = cells.getCell("AP1"); cell.setValue("Tong tien");
		    cell = cells.getCell("AQ1"); cell.setValue("So luong kien");		   
		}catch(Exception ex){
			throw new Exception("Khong tao duoc header cho bao cao");
		}
	}

	private boolean CreateStaticData(Workbook workbook, IStockintransit obj, String query)throws Exception {
		
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		Utility utl = new Utility();
		String manpp = "";
		manpp = utl.getIdNhapp(obj.getuserId());
		
		/*String sql = "\n select kbh.ten as Channel, tt.ten as Region, qh.ten as Area, npp.ten as " + 
					 		 "\n Distributor,dhtkm.spma as SKU_code, sp.ten as SKU, ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay  as Program_Program, " +
					 		 "\n nh.ten as Brand, cl.ten as Category,tt.ten as Province, qh.ten as Town, npp.pk_seq as Distributor_code, " +
					 		 "\n ctkm.scheme as Program_code,  case when ctkm.loaict = 1 then 'Binh Thuong' when ctkm.loaict = 2 then 'On Top' " +  
					 		 "\n when ctkm.loaict = 3 then 'Tich Luy' end as Program_Type, kh.pk_seq as Customer_Key, kh.ten as Customer,dh.ngaynhap as Date, " + 
					 		 "\n isnull(dhtkm.soluong,0) as Piece, dhtkm.tonggiatri  as Amount ,  case when (dhtkm.soluong/qc.soluong1) is null " +
					 		 "\n then 0 else (dhtkm.soluong/qc.soluong1) end as Quanlity  " +
					 "\n from donhang_ctkm_trakm dhtkm " +
					 "\n inner join ctkhuyenmai ctkm on dhtkm.ctkmId = ctkm.pk_seq and substring(ctkm.denngay, 1 , 7)  = '"+ obj.getYear() + "-" + obj.getMonth() + "' " +
					 "\n inner join donhang dh on dhtkm.donhangId = dh.pk_seq and dh.trangthai='1' and dh.npp_fk = '"+ manpp +"' and substring(dh.ngaynhap,1,7)='"+ obj.getYear() + "-" + obj.getMonth() + "' and substring(dh.ngaynhap,1,7)='"+ obj.getYear() + "-" + obj.getMonth() + "' " +	
					 "\n inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk " +
					 "\n inner join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk  " +
					 "\n left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk  " + 
					 "\n left join sanpham sp on dhtkm.spma = sp.ma left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk " +
					 "\n left join chungloai cl on cl.pk_seq = sp.chungloai_fk inner join khachhang kh on kh.pk_seq = dh.khachhang_fk " + 
					 "\n left join quycach qc on qc.sanpham_fk = sp.pk_seq and QC.dvdl2_fk=100018 and QC.DVDL1_FK=SP.DVDL_FK inner join kenhbanhang kbh on kbh.pk_seq = kh.kbh_fk ";
		if(obj.getCoHoadon().equals("1"))
			sql += "\n inner join (select B.DDH_FK from HOADON a "+
					  "inner join HOADON_DDH B ON B.HOADON_FK = A.PK_SEQ  "+
					  "where a.LOAIHOADON =1 and a.NPP_FK = '"+ manpp +"' and a.TRANGTHAI not in(3, 5) ) h on h.DDH_FK = dh.PK_SEQ ";
		sql +=
					 "\n where isnull(dhtkm.soluong, '0') >= 0 and dh.pk_seq not in " +
					 "\n ( " +
						"\n select donhang_fk from donhangtrave " + 
						"\n where donhang_fk is not null and trangthai = '3' and npp_fk = '"+ manpp +"' " + 
		 			 "\n ) " ;
		
		if(obj.getUnghang().equals("1")){
			sql = sql + "\n  and ctkm.kho_fk='100000' ";
		}*/
		
		
		String sql = " select kbh.ten as Channel, tt.ten as Region, qh.ten as Area, npp.ten as Distributor, "+
				 " 	 dhtkm.spma as SKU_code, sp.ten as SKU, ctkm.diengiai + '__' + ctkm.tungay + '-' + ctkm.denngay  as Program_Program, "+
				 " 	 nh.ten as Brand, cl.ten as Category,tt.ten as Province, qh.ten as Town, npp.pk_seq as Distributor_code, "+
				 " 	 ctkm.scheme as Program_code,  case when ctkm.loaict = 1 then 'Binh Thuong' when ctkm.loaict = 2 then 'On Top' "+
				 " 	 when ctkm.loaict = 3 then 'Tich Luy' end as Program_Type, " + 
				 " 	 	ISNULL( kh.maFAST, npp2.MaFAST ) as Customer_Key, ISNULL( kh.TEN, npp2.TEN ) as Customer, dh.NgayDonHang as Date, "+
				 " 	 isnull(dhtkm.soluong,0) as Piece, dhtkm.tonggiatri  as Amount ,  case when (dhtkm.soluong/qc.soluong1) is null "+
				 " 	 then 0 else (dhtkm.soluong/qc.soluong1) end as Quanlity  "+
				 "  from ERP_DONDATHANGNPP_CTKM_TRAKM dhtkm "+
				 " 	 inner join CTKHUYENMAI ctkm on dhtkm.ctkmId = ctkm.pk_seq "+
				 " 	 inner join ERP_DONDATHANGNPP dh on dhtkm.DONDATHANGID = dh.pk_seq and dh.trangthai != '3' "+
				 " 				and dh.npp_fk = '" + manpp + "' and substring(ctkm.denngay, 1 , 7)  = '"+ obj.getYear() + "-" + obj.getMonth() + "' "+
				 " 	 inner join nhaphanphoi npp on npp.pk_seq = dh.npp_fk "+
				 " 	 left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk "+
				 " 	 left join quanhuyen qh on qh.pk_seq = tt.pk_seq "+
				 " 	 left join sanpham sp on dhtkm.spma = sp.ma left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk "+
				 " 	 left join chungloai cl on cl.pk_seq = sp.chungloai_fk inner join khachhang kh on kh.pk_seq = dh.khachhang_fk "+
				 " 	 left join quycach qc on qc.sanpham_fk = sp.pk_seq and QC.dvdl2_fk=100018  "+
				 " 	 left join kenhbanhang kbh on kbh.pk_seq = dh.kbh_fk "+
				 "	 left join KHACHHANG kh on dh.khachhang_fk = kh.pk_seq	" +
				 "	 left join NHAPHANPHOI npp2 on dh.npp_dat_fk = npp2.pk_seq	" +
				 "  where isnull(dhtkm.soluong, '0') >= 0 ";
		
		if( obj.getctkmtlId().trim().length() > 0 )
			sql += " and dhtkm.ctkmId = '" + obj.getctkmtlId() + "' ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		String strQUYEN = utl.getPhanQuyen_TheoNhanVien("KHACHHANG", "dh", "khachhang_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		sql += " and ( ( dh.khachhang_fk is not null " + strQUYEN + " ) or ( dh.npp_dat_fk is not null and dh.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getuserId() + "' ) ) ) ";
		
		
		System.out.println("Xuat Khuyen Mai NPP: " + sql);
		ResultSet rs = db.get(sql);
		int i = 2;
		if (rs != null) {
			try {
				Cell cell = null;
				while (rs.next())// lap den cuoi bang du lieu
				{
					// lay tu co so du lieu, gan bien
					String Chanel = rs.getString("Channel");
					String PromotionProgram = rs.getString("Program_Program");
					
					String Brand = "" ;
					
					if(rs.getString("Brand") == null)
						Brand = "Khong xac dinh";
					else
						Brand = rs.getString("Brand");
					
					String Category = "";

					if(rs.getString("Category") == null)
						Category = "Khong xac dinh";
					else
						Category = rs.getString("Category");

					
					String Province = rs.getString("Province");
					String Town = rs.getString("Town");
					String DisCode = rs.getString("Distributor_code");
					String ProgramCode = rs.getString("Program_code");
					String ProgramType = rs.getString("Program_Type");

					String SKUCode = "";
					
					if(rs.getString("SKU_code") == null)
						SKUCode = "Khong xac dinh";
					else
						SKUCode = rs.getString("SKU_code");

					String SKU = "";
					if(rs.getString("SKU") == null)
						SKU = "Khong xac dinh";
					else
						SKU = rs.getString("SKU");

					String CustomerKey = rs.getString("Customer_Key");
					String Customer = rs.getString("Customer");
					String Date = rs.getString("Date");
					
					float Piece = rs.getFloat("Piece");

					float Amount = rs.getFloat("Amount");
					
					float Quanlity = rs.getFloat("Quanlity");
					
					cell = cells.getCell("AA" + Integer.toString(i));					cell.setValue(Chanel);
					cell = cells.getCell("AB" + Integer.toString(i));					cell.setValue(ProgramCode);
					cell = cells.getCell("AC" + Integer.toString(i));					cell.setValue(PromotionProgram);
					cell = cells.getCell("AD" + Integer.toString(i));					cell.setValue(ProgramType);
					cell = cells.getCell("AE" + Integer.toString(i));					cell.setValue(SKUCode); 
					cell = cells.getCell("AF" + Integer.toString(i));					cell.setValue(SKU); 
					cell = cells.getCell("AG" + Integer.toString(i));					cell.setValue(Brand);
					cell = cells.getCell("AH" + Integer.toString(i));					cell.setValue(Category);
					cell = cells.getCell("AI" + Integer.toString(i));					cell.setValue(DisCode);
					cell = cells.getCell("AJ" + Integer.toString(i));					cell.setValue(Province);
					cell = cells.getCell("AK" + Integer.toString(i));					cell.setValue(Town);
					cell = cells.getCell("AL" + Integer.toString(i));					cell.setValue(CustomerKey);
					
					cell = cells.getCell("AM" + Integer.toString(i));					
					//cell.setValue("Khong xac dinh");
					cell.setValue(Customer);
					
					cell = cells.getCell("AN" + Integer.toString(i));					cell.setValue(Date);
					cell = cells.getCell("AO" + Integer.toString(i));					cell.setValue(Piece);
					cell = cells.getCell("AP" + Integer.toString(i));					cell.setValue(Amount);
					cell = cells.getCell("AQ" + Integer.toString(i));					cell.setValue(Quanlity);
					i++;
				}
   				

				if (rs != null)
					rs.close();
				if (db != null)
					db.shutDown();
				if(i==2){
					throw new Exception("Xin loi. Khong co bao cao trong thoi gian nay..");
				}
				

			} catch (Exception e) {
				throw new Exception(e.getMessage());
			}
		} else {
			return false;
		}
		this.setAn(workbook, 49);
		return true;
	}

	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size) {
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

	private void setAn(Workbook workbook, int i) {
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();
		for (int j = 26; j <= i; j++) {
			cells.hideColumn(j);
		}

	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);
	}
}
