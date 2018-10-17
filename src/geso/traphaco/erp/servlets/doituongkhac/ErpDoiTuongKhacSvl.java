package geso.traphaco.erp.servlets.doituongkhac;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
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

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.doituongkhac.IErpDoiTuongKhac;
import geso.traphaco.erp.beans.doituongkhac.IErpDoiTuongKhacList;
import geso.traphaco.erp.beans.doituongkhac.imp.ErpDoiTuongKhac;
import geso.traphaco.erp.beans.doituongkhac.imp.ErpDoiTuongKhacList;
import geso.traphaco.erp.beans.nhacungcap.IErpNhaCungCap;

public class ErpDoiTuongKhacSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;	
	
	public ErpDoiTuongKhacSvl()
	{
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
		
		String action = util.getAction(querystring);
		session.setAttribute("action", action);
		
		String id = util.getId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpDoiTuongKhacList obj = new ErpDoiTuongKhacList();
		obj.setCongTyId(ctyId);
		
		if (action.equals("delete"))
		{
			ErpDoiTuongKhac ob = new ErpDoiTuongKhac(id);
			ob.setCongTyId(ctyId);

			ob.delete();
			obj.setMsg(ob.getMsg());
		}
		
		obj.init();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhac.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpDoiTuongKhacSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		
		IErpDoiTuongKhacList obj = new ErpDoiTuongKhacList();
		obj.setCongTyId(ctyId);
		
		String nextJSP = "";
		String action = request.getParameter("action");
		session.setAttribute("action", action);
		
		String nganHangId = util.antiSQLInspection(request.getParameter("nganHangId"));
		String chiNhanhId = util.antiSQLInspection(request.getParameter("chiNhanhId"));
		String maTenDoiTuong = util.antiSQLInspection(request.getParameter("maTenDoiTuong"));
		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		
		if (action == null)
			action = "";
		
		if (nganHangId == null)
			nganHangId = "";
		
		if (chiNhanhId == null)
			chiNhanhId = "";

		if (maTenDoiTuong == null)
			maTenDoiTuong = "";
		
		if (nppId == null)
			nppId = "";

		obj.setNganHangId(nganHangId);
		obj.setChiNhanhId(chiNhanhId);
		obj.setMaTenDoiTuong(maTenDoiTuong);
		obj.setNppId(nppId);
		
		String userId = "";
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		
		if (action.equals("new"))
		{
			System.out.println("Vao trang tao moi");
			IErpDoiTuongKhac dtk = new ErpDoiTuongKhac();
			dtk.setUserId(userId);
			dtk.setCongTyId(ctyId);
			dtk.init();
			session.setAttribute("obj", dtk);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhacUpdate.jsp";
		}
		else
			if(action.equals("excel")) 
			{
			    OutputStream out = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DoiTuongKhac.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			    CreateStaticHeader(workbook);
			    CreateStaticData(workbook,obj );
			
			    //Saving the Excel file
			    workbook.save(out);
			  //  nccList.search();
				//nccList.createaRs();
			    obj.init();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhac.jsp";
			}
		else
		{
			if(request.getParameter("nxtApprSplitting") != null)
			{
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			}
			obj.init();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiTuongKhac.jsp";
			session.setAttribute("obj", obj);
		}
		response.sendRedirect(nextJSP);
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
	
	private void CreateStaticHeader(Workbook workbook) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    
	    Cells cells = worksheet.getCells();
   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Đối tượng khác"); SetStyleForTitleCell(cell);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo: " + this.getDateTime()); SetStyleForTitleCell(cell);
	    
	    cell = cells.getCell("A8");
	    cell.setValue("Mã đối tượng"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("B8");
	    cell.setValue("Tên đối tượng"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("C8");
	    cell.setValue("Trạng thái"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("D8");
	    cell.setValue("Người tạo"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("E8");
	    cell.setValue("Ngày tạo"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("F8");
	    cell.setValue("Người sửa"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("G8");
	    cell.setValue("Ngày sửa"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("H8");	    
	    
	    worksheet.setName("DoiTuongKhac");
	    
	}
	
	private void SetStyleForDataCell(Cell cell) {
		SetStyleForCell(cell, 1, Color.GRAY, Color.WHITE, Color.BLACK, false);
	}
	
	private void CreateStaticData(Workbook workbook, IErpDoiTuongKhacList nccList) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    NumberFormat formatter = new DecimalFormat("#,###,###");
		dbutils db = new dbutils();
		
		String query = 
			"select dtk.PK_SEQ id, dtk.MADOITUONG ma, dtk.TENDOITUONG ten, dtk.TRANGTHAI, npp.ten tenNpp, nvt.TEN NGUOITAO, nvs.TEN NGUOISUA, dtk.NGAYTAO, dtk.NGAYSUA \n" +
			"from ERP_DOITUONGKHAC dtk\n" +
			"left join NHANVIEN nvt on nvt.PK_SEQ = dtk.NGUOITAO\n" +
			"left join NHANVIEN nvs on nvs.PK_SEQ = dtk.NGUOISUA\n" +
			"left join NHAPHANPHOI npp on npp.PK_SEQ = dtk.NPP_FK\n" +
			"where 1 = 1\n";
		
		if (nccList.getId() != null && nccList.getId().trim().length() > 0)
			query += "and dtk.pk_seq = " + nccList.getId() + "\n";

		if (nccList.getMaTenDoiTuong() != null && nccList.getMaTenDoiTuong().trim().length() > 0)
			query += "and (dtk.tendoituong like N'%" + nccList.getMaTenDoiTuong() + "%' or dtk.madoituong like N'%" + nccList.getMaTenDoiTuong()+ "%' )\n";

		if (nccList.getNganHangId() != null && nccList.getNganHangId().trim().length() > 0)
			query += "and (dtk.nganHang_FK like N'%" + nccList.getNganHangId() + "%')\n";

		if (nccList.getChiNhanhId() != null && nccList.getChiNhanhId().trim().length() > 0)
			query += "and (dtk.chiNhanh_FK like N'%" +nccList.getChiNhanhId() + "%')\n";

		if (nccList.getNppId() != null && nccList.getNppId().trim().length() > 0)
			query += "and (dtk.npp_FK like N'%" + nccList.getNppId() + "%')\n";

		//ResultSet rs = createSplittingDataNew(this.db, 50, 10, "NGAYTAO desc, NGAYSUA desc, id desc", query);
		System.out.println("Init ncc: \n" + query + "\n-----------------------");
		System.out.println("[ErpNhaCungCapSvl.CreateStaticData] query = " + query);
		
		ResultSet rs = db.get(query);

		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 40.0f);
				cells.setColumnWidth(2, 10.0f);
				cells.setColumnWidth(3, 20.0f);
				cells.setColumnWidth(4, 20.0f);
				cells.setColumnWidth(5, 20.0f);
				cells.setColumnWidth(6, 20.0f);
				cells.setColumnWidth(7, 25.0f);
				cells.setColumnWidth(8, 40.0f);
				cells.setColumnWidth(9, 40.0f);
		
				
				for(int j = 0; j < 9; j++)
					cells.setRowHeight(j, 13.0f);
				
				int dem=1;
				Cell cell = null;
				while(rs.next())
				{	
					String trangthai = rs.getString("TRANGTHAI");
					cell = cells.getCell("A" + Integer.toString(i)); SetStyleForDataCell(cell); 
					cell.setValue(rs.getString("ma")); 
					
					
					cell = cells.getCell("B" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("ten")); 
					
					cell = cells.getCell("C" + Integer.toString(i)); SetStyleForDataCell(cell);
					if(trangthai.equals("1")) 
						cell.setValue("Hoạt động");
					else
						cell.setValue("Kết thúc");
					
										
					cell = cells.getCell("D" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("NGUOITAO")); 
					
					cell = cells.getCell("E" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("NGAYTAO")); 
					
					cell = cells.getCell("F" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("NGUOISUA"));
					
					cell = cells.getCell("G" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("NGAYSUA")); 
									
					
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