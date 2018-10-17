package geso.traphaco.erp.servlets.tamung;

import geso.traphaco.erp.beans.doituongkhac.IErpDoiTuongKhacList;
import geso.traphaco.erp.beans.tamung.INhanvien;
import geso.traphaco.erp.beans.tamung.INhanvienList;
import geso.traphaco.erp.beans.tamung.imp.Nhanvien;
import geso.traphaco.erp.beans.tamung.imp.NhanvienList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class NhanvienSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public NhanvienSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		INhanvienList obj;
		HttpSession session = request.getSession();
		String ctyId = (String) session.getAttribute("congtyId");

		obj = new NhanvienList();
		obj.setCtyId(ctyId);

		String chixem = request.getParameter("chixem");
		obj.setChixem(chixem);

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);
		obj.init();
		session.setAttribute("obj", obj);
		response.sendRedirect("/TraphacoHYERP/pages/Erp/NhanVienTamUng.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		INhanvienList obj;
		obj = new NhanvienList();

		HttpSession session = request.getSession();
		String ctyId = (String) session.getAttribute("congtyId");

		System.out.println("Cogty:" + ctyId);
		obj.setCtyId(ctyId);

		String chixem = request.getParameter("chixem");
		obj.setChixem(chixem);

		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String ma = util.antiSQLInspection(request.getParameter("ma"));
		if (ma == null)
			ma = "";
		obj.setMa(ma);

		String ten = util.antiSQLInspection(request.getParameter("ten"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);

		String Trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (Trangthai == null)
			Trangthai = "";
		obj.setTrangthai(Trangthai);

		String pbId = util.antiSQLInspection(request.getParameter("pbId"));
		if (pbId == null)
			pbId = "";
		obj.setPbId(pbId);

		obj.init();

		String action = request.getParameter("action");
		if (action.equals("xoa")) {
			obj = new NhanvienList();
			obj.setCtyId(ctyId);
			obj.init();
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/NhanVienTamUng.jsp");
		} else if (action.equals("new")) {
			INhanvien objnv = new Nhanvien();
			objnv.setuserId(userId);
			objnv.setCtyId(ctyId);
			objnv.init();

			session.setAttribute("obj", objnv);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/NhanVienTamUngUpdate.jsp");
		}
		else 
			if(action.equals("excel")) 
			{
			    OutputStream out = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachNhanVienCongTy.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			    CreateStaticHeader(workbook);
			    CreateStaticData(workbook, obj);
			
			    //Saving the Excel file
			    workbook.save(out);
			   /* nccList.search();
				nccList.createaRs();*/
				response.sendRedirect("/TraphacoHYERP/pages/Erp/NhanVienTamUng.jsp");
			}
		else {
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Erp/NhanVienTamUng.jsp");
		}
	}
	
	private void SetStyleForCell(Cell cell, int borderWidth, Color borderColor, Color backgroundColor, Color textColor, boolean bold) {
		Style style = cell.getStyle();
		style.setBorderColor(BorderType.TOP, borderColor);
		style.setBorderColor(BorderType.BOTTOM, borderColor);
		style.setBorderColor(BorderType.LEFT, borderColor);
		style.setBorderColor(BorderType.RIGHT, borderColor);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setColor(backgroundColor);
		Font font = new Font();
		font.setColor(textColor);
		font.setBold(bold);
		style.setFont(font);
		cell.setStyle(style);
	}
	
	private void SetStyleForTitleCell(Cell cell) {
		Style style = cell.getStyle();
		Font font = new Font();
		font.setColor(Color.RED);
		font.setBold(true);
		style.setFont(font);
		cell.setStyle(style);
	}
	
	private void SetStyleForHeaderCell(Cell cell) {
		SetStyleForCell(cell, 1, Color.BLUE, Color.BLUE, Color.WHITE, true);
	}
	
	private void SetStyleForDataCell(Cell cell) {
		SetStyleForCell(cell, 1, Color.GRAY, Color.WHITE, Color.BLACK, false);
	}
	
	private void CreateStaticHeader(Workbook workbook) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Danh sách Nhân Viên"); SetStyleForTitleCell(cell);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo: " + this.getDateTime()); SetStyleForTitleCell(cell);
	    
	    cell = cells.getCell("A8");
	    cell.setValue("Mã"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("B8");
	    cell.setValue("Tên"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("C8");
	    cell.setValue("Email"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("D8");
	    cell.setValue("Điện thoại"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("E8");
	    cell.setValue("Trạng thái"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("F8");
	    cell.setValue("Phòng ban"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("G8");
	    cell.setValue("Người sửa"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("H8");
	    cell.setValue("Ngày sửa"); SetStyleForHeaderCell(cell);    
	   
	    worksheet.setName("NhanVien");
	}
	
	private void CreateStaticData(Workbook workbook, INhanvienList obj) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    NumberFormat formatter = new DecimalFormat("#,###,###");
		dbutils db = new dbutils();
		
		String sql =	
			"SELECT	NVTU.PK_SEQ, NVTU.MA, NVTU.TEN, ISNULL(NVTU.EMAIL, '') AS EMAIL, \n" +
			"ISNULL(NVTU.DIACHI, '') AS DIACHI, ISNULL(NVTU.DIENTHOAI, '') AS DIENTHOAI, \n" +
			"NVTU.TRANGTHAI, NV2.TEN AS NGUOISUA, NVTU.NGAYSUA, isnull(DVTH.TEN, isNull(npp.ten, '')) TENPB \n" +
			"FROM ERP_NHANVIEN NVTU \n" +
			"left JOIN NHANVIEN NV1 ON NV1.PK_SEQ = NVTU.NGUOITAO \n" + 
			"left JOIN NHANVIEN NV2 ON NV2.PK_SEQ = NVTU.NGUOISUA \n" +
			"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON NVTU.DVTH_FK = DVTH.PK_SEQ \n"+
			"left JOIN NhaPhanPhoi npp ON NVTU.npp_FK = npp.PK_SEQ \n"+
			"WHERE NVTU.PK_SEQ > 0 \n";

	Utility util = new Utility();
	if (obj.getMa().length() > 0) {
		sql = sql + " and upper(NVTU.MA) like upper(N'%" + util.replaceAEIOU(obj.getMa()) + "%')\n";
	}

	if (obj.getTen().length() > 0) {
		sql = sql + " and dbo.ftBoDau(upper(NVTU.TEN)) like upper(N'%" + util.replaceAEIOU(obj.getTen()) + "%')\n";
	}

	if (obj.getTrangthai().length() > 0) {
		sql = sql + " and NVTU.TRANGTHAI <='" + obj.getTrangthai() + "'\n";
	}

	if (obj.getPbId().length() > 0) {
		String [] arr = obj.getPbId().split(",");
		if (arr.length > 1)
		{
			if (arr[0].trim().equals("1"))
				sql = sql + " and NVTU.DVTH_FK ='" + arr[1] + "'\n";
			else
				sql = sql + " and npp.pk_seq ='" + arr[1] + "'\n";
		}
		else
			sql = sql + " and NVTU.DVTH_FK ='" + obj.getPbId()+ "'\n";
	}
		
	System.out.println("chuoi: \n" + sql);
	
	if(!obj.getCtyId().equals("100000")) //TONG CONG TY
		sql += " AND NVTU.CONGTY_FK = " + obj.getCtyId() + "\n";
	
	sql += " ORDER BY NVTU.MA \n";


		
		ResultSet rs = db.get(sql);

		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 40.0f);
				cells.setColumnWidth(2, 30.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 15.0f);
				cells.setColumnWidth(5, 20.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 15.0f);
				cells.setColumnWidth(8, 10.0f);
				cells.setColumnWidth(9, 10.0f);
				
				
				for(int j = 0; j < 9; j++)
					cells.setRowHeight(j, 13.0f);
				
				int dem=1;
				Cell cell = null;
				while(rs.next())
				{	
					String trangthai=rs.getString("trangthai");
					
					cell = cells.getCell("A" + Integer.toString(i)); SetStyleForDataCell(cell); 
					cell.setValue(rs.getString("ma")); 
					
					
					cell = cells.getCell("B" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("ten")); 
					
					cell = cells.getCell("C" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("email"));
					
					cell = cells.getCell("D" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("dienthoai")); 
					
					cell = cells.getCell("E" + Integer.toString(i)); SetStyleForDataCell(cell);
					if(trangthai.equals("1")) 
						cell.setValue("Hoạt động");
					else
						cell.setValue("Kết thúc");
					
					
					cell = cells.getCell("F" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("TENPB")); 
									
					cell = cells.getCell("G" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("nguoisua"));
					
					cell = cells.getCell("H" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("ngaysua")); 
					
					i++;
				}
				rs.close();
			}
			catch (Exception e){ e.printStackTrace(); }
			finally
			{
				db.shutDown();
			}
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
