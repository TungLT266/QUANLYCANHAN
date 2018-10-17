package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
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

public class ErpBcTonhientaiSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpBcTonhientaiSvl() {
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
		
		//obj.init();
		session.setAttribute("obj", obj);		
		session.setAttribute("userId", obj.getuserId());
		session.setAttribute("userTen", obj.getuserTen());
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpTonHienTai.jsp";
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
		obj.setuserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		
		String action = request.getParameter("action") != null? util.antiSQLInspection(request.getParameter("action")) : "";
		String nextJSP = "/TraphacoHYERP/pages/Center/ErpTonHienTai.jsp";
		
		if(action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ErpTonHienTaiTT.xlsm");
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
		String chuoi = getServletContext().getInitParameter("path") + "\\ErpTonHienTaiTT.xlsm";
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
		    cell.setValue("BÁO CÁO TỒN HIỆN TẠI TRUNG TÂM");   	
		    
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A2");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A3");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
			
		    cell = cells.getCell("DA1"); 	cell.setValue("Kho");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DB1"); 	cell.setValue("DonViKinhDanh");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DC1"); 	cell.setValue("NhanHang");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DD1"); 	cell.setValue("ChungLoai");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DE1"); 	cell.setValue("MaSanPham");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DF1"); 	cell.setValue("TenSanPham");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DG1"); 	cell.setValue("DonVi");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DH1"); 	cell.setValue("SoLo");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DI1"); 	cell.setValue("NgaySanXuat");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DJ1"); 	cell.setValue("NgayHetHan");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DK1"); 	cell.setValue("SoLuong");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DL1"); 	cell.setValue("Booked");  ReportAPI.setCellHeader(cell);
		    cell = cells.getCell("DM1"); 	cell.setValue("HienHuu");  ReportAPI.setCellHeader(cell);

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
		
	    String query =  " select b.TEN as khoTEN, d.DONVIKINHDOANH as dvkdTEN, e.TEN as nhTEN, f.TEN as clTEN, c.MA, c.TEN, g.DIENGIAI as dvTEN, " +
					    " 		a.SOLO, a.NGAYSANXUAT, a.NGAYHETHAN, a.SOLUONG, a.BOOKED, (a.SOLUONG - a.BOOKED) as AVAILABLE " +
					    " from ERP_KHOTT_SP_CHITIET a inner join ERP_KHOTT b on a.KHOTT_FK = b.PK_SEQ " +
					    " 			inner join SANPHAM c on a.SANPHAM_FK = c.PK_SEQ " +
					    " 			left join DONVIKINHDOANH d on c.DVKD_FK = d.PK_SEQ " +
					    " 			left join NHANHANG e on c.NHANHANG_FK = e.PK_SEQ " +
					    " 			left join CHUNGLOAI f on c.CHUNGLOAI_FK = f.PK_SEQ " +
					    " 			inner join DONVIDOLUONG g on c.DVDL_FK = g.PK_SEQ " +
					     " where 1=1 ";
	    
	  	Utility util =new Utility(); 
		query +=  " AND  a.KHOTT_FK IN "+util.quyen_khoTT(obj.getuserId())+" ";
	    
		query += " order by b.TEN, c.MA, a.NGAYHETHAN, a.SOLO, a.MAPHIEU, dbo.ftConvertToNumber(a.MAME), dbo.ftConvertToNumber(a.MATHUNG) ";
	    
		System.out.println("1.Bao cao TON HIEN TAI: " + query);
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
					String khoTEN = rs.getString("khoTEN");
					String dvkdTEN = rs.getString("dvkdTEN");
					String nhTEN = rs.getString("nhTEN");
					String clTEN = rs.getString("clTEN");
					String ma = rs.getString("MA");
					String ten = rs.getString("TEN");
					String dvTEN = rs.getString("dvTEN");
					String solo = rs.getString("SOLO");
					String NGAYSANXUAT = rs.getString("NGAYSANXUAT");
					String NGAYHETHAN = rs.getString("NGAYHETHAN");
					Double soluong = rs.getDouble("SOLUONG");										
					Double booked = rs.getDouble("BOOKED");
					Double avai = rs.getDouble("AVAILABLE")<0?0:rs.getDouble("AVAILABLE");	
            		
					cell = cells.getCell("DA" + Integer.toString(i)); 	cell.setValue(khoTEN);
					cell = cells.getCell("DB" + Integer.toString(i)); 	cell.setValue(dvkdTEN);
					cell = cells.getCell("DC" + Integer.toString(i)); 	cell.setValue(nhTEN);
					cell = cells.getCell("DD" + Integer.toString(i)); 	cell.setValue(clTEN);
					cell = cells.getCell("DE" + Integer.toString(i)); 	cell.setValue(ma);
					cell = cells.getCell("DF" + Integer.toString(i)); 	cell.setValue(ten);
					cell = cells.getCell("DG" + Integer.toString(i)); 	cell.setValue(dvTEN);
					cell = cells.getCell("DH" + Integer.toString(i)); 	cell.setValue(solo);
					cell = cells.getCell("DI" + Integer.toString(i)); 	cell.setValue(NGAYSANXUAT);
					cell = cells.getCell("DJ" + Integer.toString(i)); 	cell.setValue(NGAYHETHAN);
					cell = cells.getCell("DK" + Integer.toString(i)); 	cell.setValue(soluong);
					cell = cells.getCell("DL" + Integer.toString(i)); 	cell.setValue(booked);
					cell = cells.getCell("DM" + Integer.toString(i)); 	cell.setValue(avai);
				
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
