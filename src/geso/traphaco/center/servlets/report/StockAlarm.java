package geso.traphaco.center.servlets.report;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

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
import com.aspose.cells.PivotFieldDataDisplayFormat;
import com.aspose.cells.PivotFieldType;
import com.aspose.cells.PivotTable;
import com.aspose.cells.PivotTableAutoFormatType;
import com.aspose.cells.PivotTables;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class StockAlarm extends HttpServlet {
	private static final long serialVersionUID = 1L;


	public String getQuery(HttpServletRequest request, String sql, String userId, String lessday, String moreday){
		String query = "";		
		
		query  = "SELECT * FROM (" +
		   " SELECT KBH.PK_SEQ AS KBHID, KBH.TEN AS KBHTEN,VUNG.PK_SEQ AS VUNGID, VUNG.TEN AS VUNGTEN,TT.TEN AS TINHTHANH," +
			"KV.PK_SEQ AS KVID, KV.TEN AS KVTEN," +
			"ISNULL(QH.PK_SEQ,0) AS QHID, ISNULL(QH.TEN,'') AS QUANHUYEN, NPP.MA AS NPPID,NPPKHO.SOLO, NPP.TEN AS NPPTEN,DVKD.PK_SEQ AS DVKDID, DVKD.DONVIKINHDOANH AS DVKD," +
			"NHAN.PK_SEQ AS NHANID, NHAN.TEN AS NHANTEN,CHUNGLOAI.PK_SEQ AS CHUNGLOAIID, CHUNGLOAI.TEN AS CHUNGLOAITEN," +
			"SP.MA AS MASP, SP.TEN AS TENSP,NPPKHO.SOLUONG  + ISNULL(STOCKINTRANS.INS,0) AS INV,ISNULL(STOCKINTRANS.INS,0) AS INS,cast(ISNULL(AVGSALES.AVGSALES,0) as float) AS AVGSALES," +
		//	"CASE WHEN (ISNULL(AVGSALES.AVGSALES,0) > 0) THEN CAST((NPPKHO.SOLUONG/ISNULL(AVGSALES.AVGSALES,0)) AS NUMERIC(18,0)) " +
			"CASE WHEN NPPKHO.SOLUONG  + ISNULL(STOCKINTRANS.INS,0) =0 THEN 0 WHEN (ISNULL(AVGSALES.AVGSALES,0) > 0) THEN" +
			" cast( (NPPKHO.SOLUONG + ISNULL(STOCKINTRANS.INS,0))/ISNULL(AVGSALES.AVGSALES,0) as int) " +
			" ELSE 1000 " +
			" END AS INVDAY " +
			" ,qc.soluong1 as qc1,qc.soluong2 as qc2 "+
			" FROM NHAPP_KHO_CHITIET NPPKHO " +
			"	INNER JOIN SANPHAM SP ON NPPKHO.SANPHAM_FK = SP.PK_SEQ" +
			" left join quycach qc on NPPKHO.sanpham_fk = qc.sanpham_fk and qc.dvdl2_fk=100018 and qc.dvdl1_fk=sp.dvdl_fk "+
			"	left JOIN NHANHANG NHAN ON NHAN.PK_SEQ = SP.NHANHANG_FK" +
			"	left JOIN CHUNGLOAI CHUNGLOAI ON CHUNGLOAI.PK_SEQ = SP.CHUNGLOAI_FK" +
			"	INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = SP.DVKD_FK" +
			"	INNER JOIN NHAPHANPHOI NPP ON NPPKHO.NPP_FK = NPP.PK_SEQ" +
			"	INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NPPKHO.KBH_FK	" +
			"	left JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK" +
			"	left JOIN VUNG VUNG ON VUNG.PK_SEQ = KV.VUNG_FK" +
			"	LEFT  JOIN QUANHUYEN QH on QH.PK_SEQ = NPP.QUANHUYEN_FK " +
			"   LEFT JOIN TINHTHANH TT ON TT.PK_SEQ = NPP.TINHTHANH_FK" +
			"	LEFT JOIN" +
			"	( " +
			"		SELECT KBH.PK_SEQ AS KBHID,NPP.PK_SEQ AS NPPID,100000  as kho_Fk ,SP.MA AS MASP,SUM(CONVERT(INT,NHAPHANG_SP.SOLUONG)) AS INS " +
			"      FROM NHAPHANG NHAPHANG  " +
			"			INNER JOIN NHAPHANG_SP NHAPHANG_SP ON NHAPHANG_SP.NHAPHANG_FK = NHAPHANG.PK_SEQ" +
			"			INNER JOIN SANPHAM SP ON NHAPHANG_SP.SANPHAM_FK = SP.pk_seq	" +
			"			INNER JOIN NHAPHANPHOI NPP ON NHAPHANG.NPP_FK = NPP.PK_SEQ" +
			"			INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = NHAPHANG.KBH_FK" +
			"		  WHERE NHAPHANG.TRANGTHAI = '0'   " +
			"		GROUP BY KBH.PK_SEQ,NPP.PK_SEQ,SP.MA " +
			"	)STOCKINTRANS ON STOCKINTRANS.KBHID = KBH.PK_SEQ AND STOCKINTRANS.NPPID = NPP.PK_SEQ AND STOCKINTRANS.MASP = SP.MA AND NPPKHO.KHO_FK = STOCKINTRANS.KHO_FK" +
			"	LEFT JOIN" +
			"	( " +
			"		SELECT" +
			"		KBH.PK_SEQ AS KBHID,NPP.PK_SEQ AS NPPID,SP.MA AS MASP,DH_SP.KHO_FK, " +
			"		CAST(SUM(DH_SP.SOLUONG)/72 AS NUMERIC(18,3)) AS AVGSALES" +
			"		FROM DONHANG_SANPHAM DH_SP" +
			"		INNER JOIN DONHANG DH ON DH.PK_SEQ = DH_SP.DONHANG_FK" +
			"		INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = DH.NPP_FK" +
			"		INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = DH.KBH_FK" +
			"		INNER JOIN SANPHAM SP ON SP.PK_SEQ = DH_SP.SANPHAM_FK" +
			"		WHERE DH.TRANGTHAI = '1'" +
			"			  AND DH.NGAYNHAP > (SELECT CONVERT(VARCHAR(10),DATEADD(day,-12*7-2,GETDATE()),120))" +
			"			  AND DH.NGAYNHAP <= (SELECT CONVERT(VARCHAR(10),DATEADD(day,-2,GETDATE()),120))" +
			"		GROUP BY KBH.PK_SEQ,NPP.PK_SEQ,SP.MA,DH_SP.KHO_FK" +
			"	)AVGSALES ON AVGSALES.KBHID = KBH.PK_SEQ AND AVGSALES.NPPID = NPP.PK_SEQ AND AVGSALES.MASP = SP.MA AND AVGSALES.KHO_FK = NPPKHO.KHO_FK " +
			"WHERE SP.TRANGTHAI = '1' and NPPKHO.kho_fk ='100000'";//chi la o kho chinh


		 if(sql.length() > 0)
	        	query = query + sql;
	       
	    	//phanquyen
			geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
			query += " and npp.pk_seq in "+ ut.quyen_npp(userId) + " and kbh.pk_seq in " + ut.quyen_kenh(userId)
								+" and sp.pk_seq in "+ ut.quyen_sanpham(userId);
           ///query +=" ) TONG where TONG.INVDAY <= '" + lessday + "' or TONG.INVDAY >= '" + moreday + "' ";
           query +=" ) TONG where (INV + INS != 0 or AVGSALES != 0) and (TONG.INVDAY <= '" + lessday + "' or TONG.INVDAY >= '" + moreday +"')";
     
        System.out.println("Stock Alarm: " + query);
        
        return query;
	}
      
   
    public StockAlarm() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
//	    System.out.println(userId);
	    obj.setuserId(userId);
	    
	    obj.init();	   
	    
	    System.out.println("Bao Cao Canh Bao Hang Ton Kho");
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/StockAlarm.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 
  
	    if(userId ==null)
	    	userId ="";
	    obj.setuserId(userId);
	    
	    obj.setuserTen(userTen);
   	 	String nppId=request.getParameter("nppId");
   	 	
   	 	if(nppId ==null) 
   	 		nppId ="";
   	 	obj.setnppId(nppId);
   	    	 
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
	   	 
	   	String lessday=request.getParameter("lessday");
	   	obj.setlessday(lessday);
	   	
	   	String moreday=request.getParameter("moreday");
	    obj.setlessday(lessday);
	     
	    String sql ="";
		if(kenhId.length()>0) sql = sql +" and kbh.pk_seq ='"+ kenhId +"'";
		if(vungId.length()>0) sql =sql +" and vung.pk_seq ='"+ vungId +"'";
		if(khuvucId.length()>0)sql = sql + " and kv.pk_seq ='"+ khuvucId +"'";
		if(dvkdId.length()>0) sql = sql +" and sp.dvkd_fk ='"+ dvkdId +"'";
		if(nppId.length()>0)sql =sql +" and npp.pk_seq ='"+ nppId +"'";
		if(gsbhId.length()>0) sql = sql +" and gsbh.pk_seq ='"+ gsbhId +"'";
		if(nhanhangId.length()>0) sql = sql +" and nhan.pk_seq ='"+ nhanhangId +"'";
		if(chungloaiId.length()>0)sql = sql +" and chungloai.pk_seq ='"+ chungloaiId +"'";

		System.out.println("lenh them:"+ sql);
		String action=request.getParameter("action");
		
		if(action.equals("tao"))
		{
			try{
				Utility util= new Utility();
				request.setCharacterEncoding("utf-8");
				response.setCharacterEncoding("utf-8");			
				response.setContentType("application/xlsm");
			    response.setHeader("Content-Disposition", "attachment; filename=StockAlarm(TT)_"+util.setTieuDe(obj)+".xlsm");
				OutputStream out = response.getOutputStream();
				        
				String query = getQuery(request,sql,userId, lessday, moreday);        //Thiết lập chuỗi truy vấn vào CSDL lấy tham số từ Client
				CreatePivotTable(out, response, request,obj, query);	// Create PivotTable 
				request.getSession().setAttribute("errors", "");
			}catch(Exception ex){
						obj.setMsg("Thong Bao Khong The Lay Bao Cao. Loi : "+ ex.toString());
			}
		}
		
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/StockAlarm.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private void CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception
	{
		try 
		{
			
			String chuoi=getServletContext().getInitParameter("path") + "\\StockAlarm(TT).xlsm";
			
			FileInputStream fstream = new FileInputStream(chuoi);
			
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			 
			CreateHeader(workbook, obj); 
			FillData(workbook, query); 
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) {
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	private void CreateHeader(Workbook workbook,IStockintransit obj ) 
	{

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();

		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
	    cell.setValue("CẢNH BÁO TỒN KHO");
	    
		/* Utility  util = new  Utility();
		 cells.setRowHeight(3, 18.0f);
		 cell = cells.getCell("A2");
		 ReportAPI.getCellStyle(cell,Color.RED,true, 9, util.setTieuDe(obj));*/
		 
		 
	    cells.setRowHeight(2, 20.0f);
	    cell = cells.getCell("A3");getCellStyle(workbook,"A3",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDateTime());
	    
	    cells.setRowHeight(3, 20.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
		
	    
	    cell = cells.getCell("AA1");		cell.setValue("KenhBanHang");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AB1");		cell.setValue("DonViKinhDoanh");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AC1");		cell.setValue("ChiNhanh");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AD1");		cell.setValue("KhuVuc");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AE1");		cell.setValue("MaNhaPhanPhoi");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");		cell.setValue("NhaPhanPhoi");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AG1");		cell.setValue("MaSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AH1");		cell.setValue("TenSanPham");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AI1");		cell.setValue("NhanHang");		ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AJ1");		cell.setValue("ChungLoai");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AK1");		cell.setValue("TinhThanh");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AL1");		cell.setValue("QuanHuyen");					ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AM1");		cell.setValue("TonKho(+Hangdangdiduong)");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AN1");		cell.setValue("TonKhoThung(+Hangdangdiduong)");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AO1");		cell.setValue("DoanhSoBanTB");			ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AP1");		cell.setValue("SoNgayTonKho");				ReportAPI.setCellHeader(cell);	
		cell = cells.getCell("AQ1");		cell.setValue("Solo");				ReportAPI.setCellHeader(cell);		

	}
	
	private void FillData(Workbook workbook, String query) throws Exception 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		for(int i = 0; i < 6; i++)
		{
	    	cells.setColumnWidth(i, 15.0f);	
	    }
		
		dbutils db = new dbutils();
		ResultSet rs = db.get(query);
		int index = 2;
	    Cell cell = null;	    
	    while (rs.next()) 
	    {
	    	double soluong=rs.getDouble("INV");
			int qc1=rs.getInt("qc1");
			int qc2=rs.getInt("qc2");
			double slThg=soluong/qc1/qc2;
			
	    	
	    	cell = cells.getCell("AA" + String.valueOf(index));		cell.setValue(rs.getString("KBHTEN"));	
	    	cell = cells.getCell("AB" + String.valueOf(index));		cell.setValue(rs.getString("DVKD"));				//Don vi kinh doanh
			cell = cells.getCell("AC" + String.valueOf(index));		cell.setValue(rs.getString("VUNGTEN"));
			cell = cells.getCell("AD" + String.valueOf(index));		cell.setValue(rs.getString("KVTEN"));
			cell = cells.getCell("AE" + String.valueOf(index));		cell.setValue(rs.getString("NPPID"));
			cell = cells.getCell("AF" + String.valueOf(index));		cell.setValue(rs.getString("NPPTEN"));
			cell = cells.getCell("AG" + String.valueOf(index));		cell.setValue(rs.getString("MASP"));
			cell = cells.getCell("AH" + String.valueOf(index));		cell.setValue(rs.getString("TENSP"));
			cell = cells.getCell("AI" + String.valueOf(index));		cell.setValue(rs.getString("NHANTEN"));
			cell = cells.getCell("AJ" + String.valueOf(index));		cell.setValue(rs.getString("CHUNGLOAITEN"));
			cell = cells.getCell("AK" + String.valueOf(index));		cell.setValue(rs.getString("TINHTHANH"));
			cell = cells.getCell("AL" + String.valueOf(index));		cell.setValue(rs.getString("QUANHUYEN"));		
			cell = cells.getCell("AM" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INV")));
			cell = cells.getCell("AN" + String.valueOf(index));		cell.setValue(  slThg) ;
			cell = cells.getCell("AO" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("AVGSALES")));
			cell = cells.getCell("AP" + String.valueOf(index));		cell.setValue(Float.parseFloat(rs.getString("INVDAY")));					
			cell = cells.getCell("AQ" + String.valueOf(index));		cell.setValue(rs.getString("solo"));					
			
			index++;
		}
	    if(rs!=null) 
	    	rs.close();
	    db.shutDown();

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

	public String getDateTime() 
	{
		 DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
         Date date = new Date();
         return dateFormat.format(date);	
	}
}
