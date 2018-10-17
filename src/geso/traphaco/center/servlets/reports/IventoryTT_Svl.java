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

public class IventoryTT_Svl extends HttpServlet 
{	 
	private static final long serialVersionUID = 1L;
	public IventoryTT_Svl() 
    {
        super();
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
		obj.setuserId(userId);
		obj.init();
		
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
			
			obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlid"))!=null?
					util.antiSQLInspection(request.getParameter("dvdlid")):"");
			
			String avalible = util.antiSQLInspection(request.getParameter("piece"));
			String condition = "";
			if(avalible.equals("1"))
			{
				condition += " and tkn.AVAILABLE > 0";
			}
			String laysolo = util.antiSQLInspection(request.getParameter("laysolo"));
			if(laysolo==null)
			{
				laysolo="0";
			}
			obj.settype(laysolo);
			
			String date = util.antiSQLInspection(request.getParameter("date"));
			if(date==null)
			{
				date="0";
			}
			obj.setxemtheo(date);
			
			
			obj.init();
			session.setAttribute("obj", obj);
			
			String action = util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("taomoi"))
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=TonHienTai_"+util.setTieuDe(obj)+".xlsm");
				
				boolean isTrue = CreatePivotTable(out, obj, condition);
				if(!isTrue)
				{
					PrintWriter writer = new PrintWriter(out);
					writer.write("Khong tao duoc bao cao trong thoi gian nay...!!!");
				}
			}
			
			response.sendRedirect("/TraphacoHYERP/pages/Center/InventoryTT.jsp");
		} catch (Exception ex) {
			ex.printStackTrace();
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
	}
	
	private boolean CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {   
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		String fstreamstr = getServletContext().getInitParameter("path") + "\\TonHienTaiTT.xlsm";
		if(obj.gettype().trim().equals("1"))
		{
	
			fstreamstr = getServletContext().getInitParameter("path") + "\\TonHienTaiTT_SoLo.xlsm";
		}
		fstream = new FileInputStream(fstreamstr);
		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	     CreateStaticHeader(workbook, obj);	     
	     boolean isTrue = CreateStaticData(workbook, obj, condition);
	     if(!isTrue){
	    	 return false;
	     }
	     workbook.save(out);
			
		fstream.close();
	     return true;
   }
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    worksheet.setName("Sheet1");
	    
	    Cells cells = worksheet.getCells();
		
	    String dateFrom =obj.gettungay(); 
	    String dateTo =obj.getdenngay();
	    String UserName =obj.getuserTen();
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
		cell = cells.getCell("AE1");	cell.setValue("MaCN/DT");				ReportAPI.setCellHeader(cell);
		cell = cells.getCell("AF1");	cell.setValue("CN/DT");			ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AG1"); 	cell.setValue("NhanHang");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AH1"); 	cell.setValue("ChungLoai");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AI1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AJ1"); 	cell.setValue("TenSanPham"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AK1"); 	cell.setValue("Kho");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AL1"); 	cell.setValue("SoLuong");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AM1"); 	cell.setValue("SoLuongKien");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AN1"); 	cell.setValue("SoTien");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AO1"); 	cell.setValue("NganhHang");	   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AP1"); 	cell.setValue("MaChuan");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AQ1"); 	cell.setValue("PackSize");  ReportAPI.setCellHeader(cell);
	    
	    if(obj.gettype().trim().equals("1"))
	    {
	    	 cell = cells.getCell("AR1"); 	cell.setValue("SoLo");  ReportAPI.setCellHeader(cell);
	 	     cell = cells.getCell("AS1"); 	cell.setValue("NgayHetHan");  ReportAPI.setCellHeader(cell);
	 	     cell = cells.getCell("AT1"); 	cell.setValue("HanSuDung");  ReportAPI.setCellHeader(cell);
	    }
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
		if(obj.getdvdlId().length()>0)
		{
			sql+= " and sp.dvdl_fk='"+obj.getdvdlId()+"' "; 
		}
		
		String bang="select kho_fk,kbh_fk,sanpham_fk,npp_fk,soluong,AVAILABLE , '' as solo, '' as ngayhethan,100 as HanSuDung  from nhapp_kho";
		if(obj.gettype().trim().equals("1"))
		{
			bang="select kho_fk,kbh_fk,sanpham_fk,npp_fk,soluong,AVAILABLE ,solo, ngayhethan,case when ISDATE(NGAYHETHAN)=1 then DATEDIFF(day,GETDATE(),ngayhethan) else '0' end  as HanSuDung from nhapp_kho_chitiet ";
			if(obj.getxemtheo().equals("1"))
			{
				sql+=" and (case when ISDATE(NGAYHETHAN)=1 then DATEDIFF(day,GETDATE(),ngayhethan) else 0 end) >=1  "+
							"and (case when ISDATE(NGAYHETHAN)=1 then DATEDIFF(day,GETDATE(),ngayhethan) else 0 end) <=60 ";
			}else if(obj.getxemtheo().equals("2"))
			{
				sql += " and ngayhethan <'"+getDate()+"' ";
			}
		}
		String query = 
		"select solo, ngayhethan,packsize.ten as packsize, SP.MACHUAN,nganh.ten as nganhhang ,kbh.ten as Channel, sp.ma as Sku_code, sp.ten as SKU, tkn.AVAILABLE as Piece,k.ten as Warehouse, \n" +
		" V.PK_SEQ AS VUNGID, V.TEN AS VUNGTEN, KV.PK_SEQ AS KVID, KV.TEN AS KVTEN, 	\n" +
		" NPP.MA AS NPPID, NPP.TEN AS NPPTEN, 	\n" +
        " nh.ten as Brans,cast( tkn.AVAILABLE /cast(qc.soluong1 as Float) as Float) as Quantily,	\n" +
		" dvkd.donvikinhdoanh as Business_unit,cl.ten as Category, 	\n" +
		"  tkn.AVAILABLE ,tkn.HanSuDung ," +
		"  ISNULL( "+
		"			( "+
		"				SELECT TOP(1) BGMSP.GIAMUANPP "+
		"				FROM BANGGIAMUANPP  BGM INNER JOIN BANGGIAMUANPP_NPP BGMNPP ON BGM.PK_SEQ=BGMNPP.BANGGIAMUANPP_FK "+
		"					INNER JOIN BGMUANPP_SANPHAM BGMSP ON BGMSP.BGMUANPP_FK=BGM.PK_SEQ "+
		"				WHERE BGMSP.SANPHAM_FK=SP.PK_SEQ "+
		"					AND BGMNPP.NPP_FK=TKN.NPP_FK AND BGM.TUNGAY <='"+getDate()+"'  "+
		"					AND BGM.KENH_FK=TKN.KBH_FK AND BGM.DVKD_FK = SP.DVKD_FK  "+
		"				ORDER BY BGM.TUNGAY DESC "+
		"			),0)AS DonGia "+  
		" from  ( " +bang + "  "+   
		" where npp_fk in " + Uti_center.quyen_npp(obj.getuserId()) +"	\n" +
				" and kbh_fk in "+ Uti_center.quyen_kenh(obj.getuserId()) ;
		if(obj.getnppId().length() > 0)
			query = query + " and npp_fk = '" + obj.getnppId()+"'	\n" ;		
		query = query + ") tkn  inner join kenhbanhang kbh on kbh.pk_seq = tkn.kbh_fk \n" +
		" inner join sanpham sp on sp.pk_seq = tkn.sanpham_fk \n" +
		" inner join donvikinhdoanh dvkd on dvkd.pk_seq = sp.dvkd_fk \n" +
		" inner join kho k on k.pk_seq = tkn.kho_fk \n" +
		" left join quycach qc on  qc.sanpham_fk = sp.pk_seq  and dvdl2_fk=100018  and qc.dvdl1_fk=sp.dvdl_fk "+
		" left join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk \n" +
		" left join chungloai cl on cl.pk_seq = sp.chungloai_fk \n" +
		" left join nhanhang nh on nh.pk_seq = sp.nhanhang_fk \n" +
		" left join nganhhang  nganh on nganh.pk_seq=sp.nganhhang_fk \n" +
		" left join packsize on packsize.pk_seq=sp.packsize_fk   \n" +
		" inner join nhaphanphoi npp on npp.pk_seq = tkn.npp_fk 	\n" +
		" left join khuvuc kv on npp.khuvuc_fk = kv.pk_seq  \n" +
 		" left join vung v on kv.vung_fk = v.pk_seq \n" +
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
				cells.setColumnWidth(10, 15.0f);
				cells.setColumnWidth(11, 15.0f);
				cells.setColumnWidth(12, 15.0f);
				
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
					double DonGia = rs.getDouble("DonGia");
					double Amount = rs.getDouble("AVAILABLE")*DonGia;
					
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
					cell = cells.getCell("AO" + Integer.toString(i)); 	cell.setValue(rs.getString("nganhhang")); 
					cell = cells.getCell("AP" + Integer.toString(i)); 	cell.setValue(rs.getString("machuan"));
					cell = cells.getCell("AQ" + Integer.toString(i)); 	cell.setValue(rs.getString("packsize"));
					
					 if(obj.gettype().trim().equals("1"))
					 {
				    	cell = cells.getCell("AR"+ Integer.toString(i)); 	cell.setValue(rs.getString("solo"));  
				 	    cell = cells.getCell("AS"+ Integer.toString(i)); 	cell.setValue(rs.getString("ngayhethan")); 
				 	    cell = cells.getCell("AT"+ Integer.toString(i)); 	cell.setValue(rs.getInt("HanSuDung"));
				    }
					 
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
				
			} catch (Exception e) {
				e.printStackTrace();
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
	
	private String getDate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
}
