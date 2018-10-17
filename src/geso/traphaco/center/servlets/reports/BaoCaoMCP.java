package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BaoCaoMCP extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public BaoCaoMCP() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		
		Utility util = new Utility();
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setnppId(util.getIdNhapp(obj.getuserId()));
		
		obj.init();
		obj.settype("1");
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoMCP.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
	
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(request.getParameter("kenhId")!=null?
				util.antiSQLInspection(request.getParameter("kenhId")):"");
		
		obj.setvungId(request.getParameter("vungId")!=null?
				util.antiSQLInspection(request.getParameter("vungId")):"");
			
		obj.setkhuvucId(request.getParameter("khuvucId")!=null?
				util.antiSQLInspection(request.getParameter("khuvucId")):"");
		
		obj.setnppId(request.getParameter("nppId")!=null?
				util.antiSQLInspection(request.getParameter("nppId")):"");

		String action = request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action")) : "";
		String nextJSP = "/TraphacoHYERP/pages/Center/BaoCaoMCP.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoMCP_"+util.setTieuDe(obj)+".xlsm");
		        if(!CreatePivotTable(out,obj))
		        {
		        	obj.setMsg("Không có dữ liệu trong thời gian này.");
		        	obj.init();
		    		session.setAttribute("obj", obj);	
		    		response.sendRedirect(nextJSP);
		        }
		        
			}
			catch(Exception ex)
			{
				obj.setMsg(ex.getMessage());
			}
		}
	
		obj.init();
		session.setAttribute("obj", obj);	
		response.sendRedirect(nextJSP);
	}
	
	private boolean CreatePivotTable(OutputStream out, IStockintransit obj) throws Exception 
	{
		String chuoi = getServletContext().getInitParameter("path") + "\\BaoCaoMCP.xlsm";
		FileInputStream fstream = new FileInputStream(chuoi);
			
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook, obj);

		boolean isFill = CreateStaticData(workbook, obj);
		
		if (!isFill){
			fstream.close();
			return false;
		}else {
			workbook.save(out);
			fstream.close();
			return true;
		}
		
	}
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception 
	{
		try{
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();

			cells.setRowHeight(0, 20.0f);
			Cell cell = cells.getCell("A1");
		    cell.setValue("BÁO CÁO MCP");   	
		    
		    cells.setRowHeight(2, 18.0f);
		    cell = cells.getCell("A2"); 
		    getCellStyle(workbook,"A2",Color.NAVY,true,10);
		    cell.setValue("Từ ngày: " + obj.gettungay());	
		    	
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("B2"); 
		    getCellStyle(workbook,"B2",Color.NAVY,true,9);
			cell.setValue("Đến ngày: " + obj.getdenngay());  
			 
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("Vung");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("KhuVuc");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("MaNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("TenNhaPhanPhoi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("NhomSP");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("SoDonHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("SoShops");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("DoanhSo");  ReportAPI.setCellHeader(cell);

		}
		catch(Exception ex)
		{
			throw new Exception("Bao cao bi loi khi khoi tao");
		}
	}
	
	private boolean CreateStaticData(Workbook workbook, IStockintransit obj) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    String query =  " select g.PK_SEQ as vungId, g.TEN as vungTen, f.PK_SEQ as kvId, f.TEN as kvTen, e.PK_SEQ as nppId, e.MA as nppMa, e.TEN as nppTen, " +
						" d.TEN as nspTen, count(distinct a.PK_SEQ) as SoDonHang,  COUNT(distinct a.KhachHang_fk) as SoShops,  " +
						" SUM(b.SOLUONG * b.GIAMUA - b.CHIETKHAU) - isnull( ( select SUM(dhtv_sp.soluong * dhtv_sp.giamua) " +
						"  from DONHANGTRAVE dhtv inner join DONHANGTRAVE_SANPHAM dhtv_sp on dhtv.PK_SEQ = dhtv_sp.DONHANGTRAVE_FK   " +
						" where dhtv.DONHANG_FK is null and dhtv.TRANGTHAI = '3' and '" + obj.gettungay() + "' <= dhtv.NGAYDUYET  " +
						" and dhtv.NGAYDUYET <= '" + obj.getdenngay() + "' and dhtv.NPP_FK = e.PK_SEQ  " +
						" group by dhtv.NPP_FK ), 0 ) as DoanhSo    " +
						" from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK    " +
						" inner join NHOMSANPHAM_SANPHAM c on b.SANPHAM_FK = c.SP_FK    " +
						" inner join NHOMSANPHAM d on c.NSP_FK = d.PK_SEQ   " +
						" inner join NHAPHANPHOI e on a.NPP_FK = e.PK_SEQ    " +
						" inner join KHUVUC f on e.KHUVUC_FK = f.PK_SEQ    " +
						" inner join VUNG g on f.VUNG_FK = g.PK_SEQ    " +
						" where d.TYPE = 0 and d.loainhom = '0' and a.TRANGTHAI = '1' and a.NGAYNHAP >= '" + obj.gettungay() + "' and a.NGAYNHAP <= '" + obj.getdenngay() + "'  " +
						" and a.PK_SEQ not in ( select DONHANG_FK from DONHANGTRAVE where DONHANG_FK is not null and TRANGTHAI = '3' and '" + obj.gettungay() + "' <= NGAYDUYET and NGAYDUYET <= '" + obj.getdenngay() + "' )   " ;
	    
	    if(obj.getvungId().length() > 0)
	    	query += " and g.pk_seq = '" + obj.getvungId() + "' ";
	    
	    if(obj.getkhuvucId().length() > 0)
	    	query += " and f.pk_seq = '" + obj.getkhuvucId() + "' ";

	    query += " group by g.PK_SEQ, g.TEN, f.PK_SEQ, f.TEN, d.TEN, e.PK_SEQ, e.MA, e.TEN   " +
				 " order by g.PK_SEQ asc, f.PK_SEQ asc";
		System.out.println("1.Bao cao MCP: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs!=null)
		{
			try 
			{
				for(int j = 0; j < 15; j++)
					cells.setColumnWidth(i, 15.0f);
				
				Cell cell = null;
				while(rs.next())
				{
					String vung = rs.getString("vungTen");
					String khuvuc = rs.getString("kvTen");
					String nppMa = rs.getString("nppMa");
					String nppTen = rs.getString("nppTen");
					String nhomsp = rs.getString("nspTen");
					float sodonhang = rs.getFloat("SoDonHang");										
					float soshops = rs.getFloat("soshops");
					float doanhso = rs.getFloat("doanhso");	
            		
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(vung);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(khuvuc);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(nppMa);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(nppTen);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(nhomsp);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(sodonhang);
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(soshops);
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(doanhso);				
				
					i++;
				}
				if(rs!=null)
					rs.close();
				if(db != null) 
					db.shutDown();
				if(i==2){
					throw new Exception("Khong co bao cao trong thoi gian nay...");
				}
			
			} 
			catch (Exception e) 
			{
				System.out.println("115.Error: " + e.getMessage());
			}
		} 
		else 
		{
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

}
