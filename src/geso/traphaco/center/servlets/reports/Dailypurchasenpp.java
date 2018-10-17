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


public class Dailypurchasenpp extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

    public Dailypurchasenpp() {
        super();
    }
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util=new Utility();
		HttpSession session = request.getSession();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		IStockintransit obj = new Stockintransit();
		
		obj.init();
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    session.setAttribute("loi", "");
    session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/DailyPurchasenpp.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();
		boolean bfasle= true;
		Utility util = new Utility();
		try
		{
			HttpSession session = request.getSession();
			String userTen = (String)session.getAttribute("userTen");
			
			String tungay=util.antiSQLInspection(request.getParameter("tungay"));
			String denngay=util.antiSQLInspection(request.getParameter("denngay"));
			
			obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?
			util.antiSQLInspection(request.getParameter("kenhId")):"");
			 
			String userId =util.antiSQLInspection(request.getParameter("userId"));
			if(userId ==null)
				userId ="";
			obj.setuserId(userId);
			
			String khoId =util.antiSQLInspection( request.getParameter("khoId") ==null?"":request.getParameter("khoId"));
			obj.setkhoId(khoId);
			
			obj.setNppId(util.getIdNhapp(userId));
		
			String action = (String) util.antiSQLInspection(request.getParameter("action"));
		    if(userTen == null)
		    	userTen ="";
		    obj.setuserTen(userTen);
		    if(tungay == null) 
		    	tungay ="";
		    obj.settungay(tungay);
		    if(denngay == null)
		    	denngay ="";
		    obj.setdenngay(denngay);
		    
		    response.setContentType("application/xlsm");
		    response.setHeader("Content-Disposition", "attachment; filename=Dailypurchase(NPP).xlsm");
		    
			if (action.equals("NhapHang")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=Dailypurchase(NPP).xlsm");
		        try 
		        {
							CreatePivotTable(out,response,request,obj,bfasle);
		        } 
		        catch (Exception e) 
		        {
		        	obj.setMsg("Khong the tao bao cao " + e.getMessage());
		        }
			}else 
			{
				CreatePivotTable_XCNB(out,obj,get_XuatChuyenNoiBo(obj));
			}
	     }
	    catch (Exception ex)
	    {
	        ex.printStackTrace();
	        response.setContentType("text/html");
	        PrintWriter writer = new PrintWriter(out);
	        writer.println("<html>");
	        writer.println("<head>");
	        writer.println("<title>sorry</title>");
	        writer.println("</head>");
	        writer.println("<body>");
	        writer.println("<h1>Xin loi, khong the tao pivot table...</h1>");
	        ex.printStackTrace(writer);
	        writer.println("</body>");
	        writer.println("</html>");
	        writer.close();
	    }
	}
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj,boolean bfasle) throws IOException
    {    //khoi tao de viet pivottable
	   
		//buoc 1
		FileInputStream fstream;
		
		String fstreamstr = getServletContext().getInitParameter("path") + "\\Dailypurchase(NPP).xlsm";
		fstream = new FileInputStream(fstreamstr);	

			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
	     //Buoc2 tao khung
	    //ham tao khu du lieu
	     CreateStaticHeader(workbook,obj.gettungay(),obj.getdenngay(),obj.getuserTen());
	     //Buoc3 
	     // day du lieu vao
	     CreateStaticData(workbook,obj,bfasle);
	     if(!bfasle){
	    	String loi="Chưa có báo cáo trong thời gian này, vui lòng chọn lại thời gian lấy báo cáo";
	    	
	    	 HttpSession session = request.getSession();
	 		session.setAttribute("loi", loi);
	 		session.setAttribute("tungay",obj.gettungay());
	 		session.setAttribute("denngay",obj.getdenngay());
	    	 session.setAttribute("userTen",obj.getuserTen());
	 		String nextJSP = "/TraphacoHYERP/pages/Center/DailyPurchasenpp.jsp";
	 		response.sendRedirect(nextJSP);
	     }else{
	    	 //Saving the Excel file
	    	 workbook.save(out);
	     }
	     fstream.close();
   }
	
	private void CreateStaticHeader(Workbook workbook, String dateFrom, String dateTo, String UserName) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   
	    
	    
	   	Style style;
	    //cells.setColumnWidth(0, 200.0f);
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");  
	    cell.setValue("HÀNG NHẬP KHO");   	
	    
	  
	    style = cell.getStyle();
	   
	   Font font2 = new Font();
       font2.setColor(Color.RED);//mau chu
       font2.setSize(14);// size chu
       style.setFont(font2); 
       style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu       
       cell.setStyle(style);
	    cell = cells.getCell("A2"); getCellStyle(workbook,"A2",Color.NAVY,true,9);
	    cell.setValue("Từ ngày " + dateFrom + "      Đến ngày " + dateTo);    
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,9);
	     cell.setValue("Ngày Tạo: " + this.getDateTime());
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Tạo bởi :  " + UserName);
	    
	  
	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	   
	    cell = cells.getCell("BA1"); cell.setValue("Don Vi Kinh Doanh");
	    cell = cells.getCell("BB1"); cell.setValue("Ma Nha Phan Phoi");
	    cell = cells.getCell("BC1"); cell.setValue("Nha Phan Phoi");
	    cell = cells.getCell("BD1"); cell.setValue("Nhan Hang");	  
	    cell = cells.getCell("BE1"); cell.setValue("Chung Loai");
	    cell = cells.getCell("BF1"); cell.setValue("So Chung Tu");;
	    cell = cells.getCell("BG1"); cell.setValue("Ngay Nhap Hang");
	    cell = cells.getCell("BH1"); cell.setValue("Ngay Chung Tu");
	    cell = cells.getCell("BI1"); cell.setValue("Ma San Pham");
	    cell = cells.getCell("BJ1"); cell.setValue("Ten San Pham");
	    cell = cells.getCell("BK1"); cell.setValue("Kho");
	    
	    cell = cells.getCell("BL1"); cell.setValue("So Luong");
	    cell = cells.getCell("BM1"); cell.setValue("So Tien");
	    cell = cells.getCell("BN1"); cell.setValue("So Luong Kien");
	    cell = cells.getCell("BO1"); cell.setValue("Ma So Nhap Hang");
	    cell = cells.getCell("BP1"); cell.setValue("Kenh Ban Hang");

	    worksheet.setName("Sheet1");
	}
	
	private void CreateStaticData(Workbook workbook,IStockintransit obj,boolean bfasle) 
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	   Utility Ult = new Utility();
	    String manpp = Ult.getIdNhapp(obj.getuserId());
	    
	    String condition = "";
	    if (obj.getkenhId().length() > 0)
	    	condition += " and kbh.pk_seq ='" + obj.getkenhId() + "' ";
	    
	    if(obj.getkhoId().length()>0)
	    {
	    	condition += " and nhsp.khonhan_fk ='" + obj.getkhoId() + "' ";
	    }
	    
	    
	    System.out.println(" ma nha phan phoi"+ manpp);
				String sql ="select  kbh.ten as Channel, "+
				" v.ten as Region,kv.ten as Area, "+
				 " tt.ten as Province,qh.ten as Town , "+ 
				 " npp.sitecode as Distributor_code, "+
			     " npp.ten as Distributor, "+
			     " dvkd.donvikinhdoanh as Business_unit, "+
			     " sp.ma as SKU_code,kho.ten as tenkho, "+
			     " sp.ten as SKU,nh.ngaynhan as Purdate,nh.ngaychungtu , "+
			     " nhah.ten as Brands,cl.ten as Category, "+
			     " nhsp.soluong as Piece, nh.PK_SEQ as NhapHangId,nh.SoChungTu as Series_number, "+
			     " nhsp.dongia as Amount, " +
			     " isnull((nhsp.soluong * qc.SOLUONG2 / SOLUONG1),0)  as soluongThung  "+
			    " from nhaphang nh "+
			    " inner join nhaphang_sp nhsp on nhsp.nhaphang_fk = nh.pk_seq "+  
				" left  join kenhbanhang kbh on kbh.pk_seq = nh.kbh_fk "+
				" inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk  "+
				" inner join sanpham sp on sp.pk_Seq = nhsp.sanpham_fk  "+
				"  left join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk "+  
				" inner join kho on kho.pk_seq=nhsp.khonhan_fk	"+
				" left join nhanhang nhah on nhah.pk_seq = sp.nhanhang_fk "+ 
				" left join chungloai cl on cl.pk_seq = sp.chungloai_fk  "+
				" left join quycach qc on qc.sanpham_fk = sp.pk_seq and sp.dvdl_fk = qc.dvdl1_fk and qc.dvdl2_fk=100018 "+  
				" left join khuvuc kv on kv.pk_seq = npp.khuvuc_fk "+
				" left join vung v on v.pk_seq = kv.vung_fk "+
				" left join quanhuyen qh on qh.pk_seq = npp.quanhuyen_fk "+  
				" left join tinhthanh tt on tt.pk_seq = npp.tinhthanh_fk "+		
				
				" where nh.trangthai = '1' and npp.pk_seq = '"+ manpp +"' "+
			    " and nh.ngaynhan >= '"+ obj.gettungay()+"'"+
			    " and nh.ngaynhan <= '"+ obj.getdenngay() +"'" + condition ;
				
				 
				
         System.out.println(sql);
		ResultSet rs = db.get(sql);
		
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
				cells.setColumnWidth(7, 15.0f);
			
				
				Cell cell = null;
				while(rs.next())// lap den cuoi bang du lieu
				{
				
					//lay tu co so du lieu, gan bien
					String BusinessUnit = rs.getString("Business_unit");
					String Brands =rs.getString("Brands");
					String Category =rs.getString("Category");			
										
					String SKUcode = rs.getString("SKU_Code");
					String SKU =rs.getString("SKU");
					String Purdate = rs.getString("Purdate");
					String ngaychungtu = rs.getString("ngaychungtu");
					float Amount = rs.getFloat("Amount");
					String DistributorCode = rs.getString("Distributor_code");
					String Distributor = rs.getString("Distributor");
					String SeriesNumber = rs.getString("Series_number");
					float Piece = rs.getFloat("Piece");
					String tenkho=rs.getString("tenkho");
					float thung = rs.getFloat("soluongThung");

					cell = cells.getCell("BA" + Integer.toString(i)); cell.setValue(BusinessUnit);
					cell = cells.getCell("BB" + Integer.toString(i)); cell.setValue(DistributorCode);
					cell = cells.getCell("BC" + Integer.toString(i)); cell.setValue(Distributor);
					cell = cells.getCell("BD" + Integer.toString(i)); cell.setValue(Brands);
					cell = cells.getCell("BE" + Integer.toString(i)); cell.setValue(Category);
					cell = cells.getCell("BF" + Integer.toString(i)); cell.setValue(SeriesNumber);
					cell = cells.getCell("BG" + Integer.toString(i)); cell.setValue(Purdate);
					cell = cells.getCell("BH" + Integer.toString(i)); cell.setValue(ngaychungtu);
					cell = cells.getCell("BI" + Integer.toString(i)); cell.setValue(SKUcode);
					cell = cells.getCell("BJ" + Integer.toString(i)); cell.setValue(SKU);				
					
					cell = cells.getCell("BK" + Integer.toString(i)); cell.setValue(tenkho);	
					
					cell = cells.getCell("BL" + Integer.toString(i)); cell.setValue(Piece);
					cell = cells.getCell("BM" + Integer.toString(i)); cell.setValue(Amount);
					cell = cells.getCell("BN" + Integer.toString(i)); cell.setValue(thung);
					
					cell = cells.getCell("BO" + Integer.toString(i)); cell.setValue(rs.getString("nhapHANGID"));
					cell = cells.getCell("BP" + Integer.toString(i)); cell.setValue(rs.getString("cHANNEL"));
				
					i++;
				}

		 if(rs!=null){
			 rs.close();
			 
		 }
		 if(db!=null){
			 db.shutDown();
		 }
	    bfasle=true;
			}
			catch (Exception e){
				bfasle=false;
				e.printStackTrace(); 
				System.out.println("Error : Inventory Report : " +e.toString());
			}
		}else{
			bfasle=false;
		}
		
	}
	
	private String get_XuatChuyenNoiBo(IStockintransit obj)
	{
		String query=
				"select	f.ten as kbhTen,g.ten as khoTen,a.PK_SEQ as ckId,a.TrangThai,a.SoChungTu,a.NgayChuyen,a.GHICHU,c.MA as nppMa,c.TEN as nppTen,d.MA as nppNhanMa,d.TEN as nppNhanTen,  "+
				"		e.ma as spMa,e.ten as spTen,h.DONVI as dvdlTEN,b.soluong,b.SoLo,b.NgayHetHan,b.DonGia,b.ptVat  "+
				"from ERP_CHUYENKHONPP a inner join ERP_CHUYENKHONPP_SANPHAM_CHITIET b on b.chuyenkho_fk=a.PK_SEQ  "+
				"	inner join NHAPHANPHOI c on c.PK_SEQ=a.NPP_FK  "+
				"	inner join NHAPHANPHOI d on d.PK_SEQ=a.NPP_DAT_FK  "+
				"	inner join SANPHAM e on e.PK_SEQ=b.SANPHAM_FK  "+
				"	left join KENHBANHANG f on f.PK_SEQ=a.KBH_FK  "+
				"	inner join KHO g on g.PK_SEQ=a.KhoXuat_FK  "+
				"	inner join donvidoluong h on h.pk_Seq=e.dvdl_Fk " +
				" where 1=1   ";
		
		if(obj.gettungay().length()>0)
		{
			query+=" and a.NgayChuyen>='"+obj.gettungay()+"'";
		}
		
		if(obj.getdenngay().length()>0)
		{
			query+=" and a.NgayChuyen <='"+obj.getdenngay()+"'";
		}
		if(obj.getkhoId().length()>0)
		{
			query+=" and a.KhoXuat_FK ='"+obj.getkhoId()+"'";
		}
		
		if(obj.getkenhId().length()>0)
		{
			query+=" and a.KBH_FK ='"+obj.getkenhId()+"'";
		}
		
		if(obj.getNppId().length()>0)
		{
			query+=" and a.npp_fk ='"+obj.getNppId()+"'";
		}
		System.out.println("____Query___"+query);
		return query;
	}
	
	private void CreatePivotTable_XCNB(OutputStream out,IStockintransit obj, String query) throws Exception
	{
		try 
		{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BcXuatChuyenNoiBo.xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateHeader_XCNB(workbook,obj); 
			FillData_XCNB(workbook, query, obj);			
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void FillData_XCNB(Workbook workbook, String query, IStockintransit obj) throws Exception
  {
		ResultSet rs = null;
		dbutils db = new dbutils();
		try
		{	
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			
			for(int i=0;i<15;++i)
			{
		    	cells.setColumnWidth(i, 15.0f);	    	
		    }	
			 rs = db.get(query);
			int index = 2;
		    Cell cell = null;
			while (rs.next())
			{
				  double ThanhTien=rs.getDouble("SoLuong")*rs.getDouble("DonGia");
				  double ptThue=rs.getDouble("ptVat")/100;
					cell = cells.getCell("FA" + String.valueOf(index));		cell.setValue(rs.getString("kbhTen"));		
					cell = cells.getCell("FB" + String.valueOf(index));		cell.setValue(rs.getString("khoTEN"));
					cell = cells.getCell("FC" + String.valueOf(index));		cell.setValue(rs.getString("ckId"));
					cell = cells.getCell("FD" + String.valueOf(index));		cell.setValue(rs.getString("SoChungTu"));
					cell = cells.getCell("FE" + String.valueOf(index));		cell.setValue(rs.getString("NgayChuyen"));
				
					String TrangThai="";
					if(rs.getInt("Trangthai")==0)
					{
						TrangThai="Chưa chốt";
					}else 	
					if(rs.getInt("Trangthai")==1)
					{
						TrangThai="Đã chốt";
					}else if(rs.getInt("Trangthai")==2)
					{
						TrangThai="Đã Hủy";
					}
					cell = cells.getCell("FF" + String.valueOf(index));		cell.setValue(TrangThai);
				
					cell = cells.getCell("FG" + String.valueOf(index));		cell.setValue(rs.getString("GhiChu"));
				
			  cell = cells.getCell("FH" + String.valueOf(index));		cell.setValue(rs.getString("nppNhanMa"));
			  cell = cells.getCell("FI" + String.valueOf(index));		cell.setValue(rs.getString("nppNhanTEN"));
			  cell = cells.getCell("FJ" + String.valueOf(index));		cell.setValue(rs.getString("nppMa"));
			  cell = cells.getCell("FK" + String.valueOf(index));		cell.setValue(rs.getString("nppTEN"));
			  
			  cell = cells.getCell("FL" + String.valueOf(index));		cell.setValue(rs.getString("spMa"));
			  cell = cells.getCell("FM" + String.valueOf(index));		cell.setValue(rs.getString("spTen"));
			  cell = cells.getCell("FN" + String.valueOf(index));		cell.setValue(rs.getString("dvdlTEN"));
			  cell = cells.getCell("FO" + String.valueOf(index));		cell.setValue(rs.getDouble("SoLuong"));
			  cell = cells.getCell("FP" + String.valueOf(index));		cell.setValue(rs.getString("SoLo"));
			  cell = cells.getCell("FQ" + String.valueOf(index));		cell.setValue(rs.getString("NgayHetHan"));
			  cell = cells.getCell("FR" + String.valueOf(index));		cell.setValue(rs.getDouble("DonGia"));
				
			  cell = cells.getCell("FS" + String.valueOf(index));		cell.setValue(  ThanhTien );
			  cell = cells.getCell("FT" + String.valueOf(index));		cell.setValue(rs.getDouble("ptVat"));
			  cell = cells.getCell("FU" + String.valueOf(index));		cell.setValue(ptThue*ThanhTien);
			  cell = cells.getCell("FV" + String.valueOf(index));		cell.setValue((1+ptThue)*ThanhTien);
			  
				index++;
			}
			if(rs!=null){
				rs.close();
			}	
			
			ReportAPI.setHidden(workbook,14);
			 
		
		    			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			if(rs != null)
			{
				rs.close();
			}
			ex.printStackTrace();
		
			
			throw new Exception(ex.getMessage());
		}
		finally
		{
			if(db != null)
				db.shutDown();
		}
	  
  }

	private void CreateHeader_XCNB(Workbook workbook, IStockintransit obj)
  {
		Worksheets worksheets = workbook.getWorksheets();
    Worksheet worksheet = worksheets.getSheet(0);	    
    worksheet.setName("Sheet1");
    Cells cells = worksheet.getCells();	 
    
    cells.setRowHeight(0, 20.0f);	    
    Cell cell = cells.getCell("A1");	
    ReportAPI.getCellStyle(cell,Color.RED, true, 16, "Báo Cáo Xuất Chuyển Nội Bộ");
    
    cell = cells.getCell("A3");
    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Từ ngày: " + obj.gettungay() + "  Đến ngày : " + obj.getdenngay());
    cell = cells.getCell("A4");
    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Ngày tạo : " + this.getDateTime()); 
    cell = cells.getCell("A5");
    ReportAPI.getCellStyle(cell,Color.NAVY,true,10,"Người tạo : " + obj.getuserTen());
    
	  cell = cells.getCell("FA1");		cell.setValue("KenhBanHang");
	  cell = cells.getCell("FB1");		cell.setValue("Kho");		
	  cell = cells.getCell("FC1");		cell.setValue("MaSo");
	  cell = cells.getCell("FD1");		cell.setValue("SoChungTu");
	  cell = cells.getCell("FE1");		cell.setValue("NgayChungTu");
	  cell = cells.getCell("FF1");		cell.setValue("TrangThai");
	  cell = cells.getCell("FG1");		cell.setValue("GhiChu");
	  cell = cells.getCell("FH1");		cell.setValue("MaCN_DT_Nhan");
	  cell = cells.getCell("FI1");		cell.setValue("CN_DT_Nhan");
	  cell = cells.getCell("FJ1");		cell.setValue("MaCN_DT_Xuat");
	  cell = cells.getCell("FK1");		cell.setValue("CN_DT_Xuat");
	  cell = cells.getCell("FL1");		cell.setValue("MaSanPham");
	  cell = cells.getCell("FM1");		cell.setValue("TenSanPham");
	  cell = cells.getCell("FN1");		cell.setValue("DonVi");
	  cell = cells.getCell("FO1");		cell.setValue("SoLuong");
	  cell = cells.getCell("FP1");		cell.setValue("SoLo");
	  cell = cells.getCell("FQ1");		cell.setValue("NgayHetHan");
	  cell = cells.getCell("FR1");		cell.setValue("DonGia");
	  cell = cells.getCell("FS1");		cell.setValue("ThanhTien");
	  cell = cells.getCell("FT1");		cell.setValue("PtThue");
	  cell = cells.getCell("FU1");		cell.setValue("TienThue");
	  cell = cells.getCell("FV1");		cell.setValue("TienSauThue");
	  
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
