package geso.traphaco.center.servlets.khaosat;

import geso.traphaco.center.beans.khaosat.IThongkekhaosatList;
import geso.traphaco.center.beans.khaosat.imp.ThongkekhaosatList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ThongkekhaosatSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	OutputStream out;
	
    public ThongkekhaosatSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");

	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();

	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String khaosatId = request.getParameter("khaosatId");
	    if(khaosatId == null)
	    	khaosatId = "";
	    
	    IThongkekhaosatList obj = new ThongkekhaosatList();
	    obj.setUserId(userId);
	    obj.setKhaosatId(khaosatId);
	    
	    obj.createRs();
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/ThongKeKhaoSat.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		OutputStream out = response.getOutputStream(); 
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));  
	    
	    IThongkekhaosatList obj = new ThongkekhaosatList();
	    obj.setUserId(userId);
	    
		String action = request.getParameter("action");
	    if (action == null){
	    	action = "";
	    }
	    
	    String khaosatId = request.getParameter("khaosatId");
		if(khaosatId == null)
			khaosatId = "";
		obj.setKhaosatId(khaosatId);
		
		String tennguoiKS = request.getParameter("tennguoiKS");
		if(tennguoiKS == null)
			tennguoiKS = "";
		obj.setTennguoitraloi(tennguoiKS);
	    
		if(action.equals("xuatBC"))
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=KhaoSat.xlsm");
			
	    	boolean isTrue = false;
			try 
			{
				isTrue = CreatePivotTable(out, obj, "");	
			} 
			catch (Exception e) 
			{
				System.out.println("___Exception xuat BC: " + e.getMessage());
				isTrue = false;
			}
			
			if(!isTrue)
			{
				obj.createRs();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				obj.setMsg("Không thể tạo báo cáo");
				
				String nextJSP = "/TraphacoHYERP/pages/Center/ThongKeKhaoSat.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		else
		{
			obj.createRs();
			
	    	session.setAttribute("obj", obj);  	
			session.setAttribute("userId", userId);
		
			response.sendRedirect("/TraphacoHYERP/pages/Center/ThongKeKhaoSat.jsp");	
		}
	    
	}

	private boolean CreatePivotTable(OutputStream out, IThongkekhaosatList obj, String userName) throws IOException 
	{
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\KhaoSat.xlsm");

		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
	    CreateStaticHeader(workbook, "", userName);	     
	    boolean isTrue = false;
		try {
			isTrue = CreateStaticData(workbook, obj);
		} catch (Exception e) {
			isTrue = false;
		}
	    if(!isTrue){
	    	 return false;
	    }
	    workbook.save(out);
			
		fstream.close();
	    return true;
	}

	private void CreateStaticHeader(Workbook workbook, String string, String userName)
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
	    
	    String tieude = "KẾT QUẢ THỐNG KÊ KHẢO SÁT";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	           
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A2");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A3");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + userName);
	    

	    //tieu de, hang dau tien cua bang du lieu, cell la toa do cua exel
	    cell = cells.getCell("AA1"); 	cell.setValue("TieuDe");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AB1"); 	cell.setValue("NguoiDuocKhaoSat");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AC1"); 	cell.setValue("DienThoai");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AD1"); 	cell.setValue("DiaChi");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AE1"); 	cell.setValue("GioiTinh");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AF1"); 	cell.setValue("MucTieu");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AG1"); 	cell.setValue("Tuoi");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AH1"); 	cell.setValue("ThuNhap");   ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AI1"); 	cell.setValue("CauHoi");  ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AJ1"); 	cell.setValue("DapAn1"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AK1"); 	cell.setValue("DapAn2"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AL1"); 	cell.setValue("DapAn3"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AM1"); 	cell.setValue("DapAn4"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AN1"); 	cell.setValue("DapAn5"); ReportAPI.setCellHeader(cell);
	    cell = cells.getCell("AO1"); 	cell.setValue("TraLoi");	   ReportAPI.setCellHeader(cell);
		
	}

	private boolean CreateStaticData(Workbook workbook, IThongkekhaosatList obj) throws Exception
	{
		dbutils db = new dbutils();
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
		String query = "select a.TIEUDE, c.ten, c.sodienthoai, c.diachi, case when c.gioitinh = 0 then N'Nữ' else N'Nam' end as gioitinh, " +
							"c.muctieu, c.tuoi, c.thunhap, b.CAUHOI, c.luachon1_chon as dapan1, c.luachon2_chon as dapan2,  " +
							"c.luachon3_chon as dapan3, c.luachon4_chon as dapan4, c.luachon5_chon as dapan5, c.traloi " +
						"from KHAOSAT a inner join KHAOSAT_CAUHOI b on a.PK_SEQ = b.KHAOSAT_FK " +
							"inner join KHAOSAT_CAUHOI_THUCHIEN c on b.PK_SEQ = c.khaosat_cauhoi_fk " +
						"where a.PK_SEQ > 0 " ;
		if(obj.getKhaosatId().trim().length() > 0)
			query += " and a.pk_seq = '" + obj.getKhaosatId() + "' ";
		
		query += " order by a.PK_SEQ asc, c.ten asc, c.sodienthoai asc, b.STT asc";
	
		System.out.println("1.Thong ke khao sat: " + query);
		ResultSet rs = db.get(query);
		
		int i = 2;
		if(rs != null)
		{
			try 
			{
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
				while(rs.next())// lap den cuoi bang du lieu
				{
					String tieude = rs.getString("TIEUDE");
					String ten = rs.getString("TEN");
					String dienthoai = rs.getString("SoDienThoai");
					String diachi = rs.getString("DiaChi");
					String gioitinh = rs.getString("GioiTinh");
					String muctieu = rs.getString("muctieu");
					String tuoi = rs.getString("tuoi");
					String thunhap = rs.getString("thunhap");
					String cauhoi = rs.getString("cauhoi");		
					String dapan1 = rs.getString("dapan1");	
					String dapan2 = rs.getString("dapan2");	
					String dapan3 = rs.getString("dapan3");	
					String dapan4 = rs.getString("dapan4");
					String dapan5 = rs.getString("dapan5");
					String traloi = rs.getString("traloi");

					cell = cells.getCell("AA" + Integer.toString(i)); 	cell.setValue(tieude);
					cell = cells.getCell("AB" + Integer.toString(i)); 	cell.setValue(ten);
					cell = cells.getCell("AC" + Integer.toString(i)); 	cell.setValue(dienthoai);
					cell = cells.getCell("AD" + Integer.toString(i)); 	cell.setValue(diachi);
					cell = cells.getCell("AE" + Integer.toString(i)); 	cell.setValue(gioitinh);
					cell = cells.getCell("AF" + Integer.toString(i)); 	cell.setValue(muctieu);
					cell = cells.getCell("AG" + Integer.toString(i)); 	cell.setValue(tuoi);
					cell = cells.getCell("AH" + Integer.toString(i)); 	cell.setValue(thunhap);
					cell = cells.getCell("AI" + Integer.toString(i)); 	cell.setValue(cauhoi);
					cell = cells.getCell("AJ" + Integer.toString(i)); 	cell.setValue(Double.parseDouble(dapan1));				
					cell = cells.getCell("AK" + Integer.toString(i)); 	cell.setValue(Double.parseDouble(dapan2));
					cell = cells.getCell("AL" + Integer.toString(i)); 	cell.setValue(Double.parseDouble(dapan3));
					cell = cells.getCell("AM" + Integer.toString(i)); 	cell.setValue(Double.parseDouble(dapan4)); 
					cell = cells.getCell("AN" + Integer.toString(i)); 	cell.setValue(Double.parseDouble(dapan5)); 
					cell = cells.getCell("AO" + Integer.toString(i)); 	cell.setValue(traloi); 
					
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
				System.out.println("115.Exception: " + e.getMessage());
				throw new Exception(e.getMessage());
			}
		} else {
			if(db != null) db.shutDown();
			return false;
		}
		
		if(db != null) db.shutDown();
		return true;
		
		
	}
	
	
}
