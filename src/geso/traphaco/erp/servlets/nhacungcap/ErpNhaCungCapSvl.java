package geso.traphaco.erp.servlets.nhacungcap;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.nhacungcap.*;
import geso.traphaco.erp.beans.nhacungcap.imp.*;

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

public class ErpNhaCungCapSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;	
	
	public ErpNhaCungCapSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do get ErpnhacungcapSvl");
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
		
		String id = util.getId(querystring);
		
		String ctyId = (String)session.getAttribute("congtyId");
		
		IErpNhaCungCap nccList = new ErpNhaCungCap();
		nccList.setCongTy(ctyId);
		
		String chixem = request.getParameter("chixem");
		nccList.setChixem(chixem);
		
		if (action.equals("delete"))
		{
			IErpNhaCungCap nccBean = new ErpNhaCungCap(id);
			nccBean.setCongTy(ctyId);
			
			nccBean.DeleteNcc();
			nccList.setMsg(nccBean.getMsg());

			System.out.println("Xoa Nha Cung cap");
		}else if(action.equals("duyet"))
		{
			IErpNhaCungCap nccBean = new ErpNhaCungCap(id);
			nccBean.setCongTy(ctyId);
			
			nccBean.DuyetNcc();
			nccList.setMsg(nccBean.getMsg());
			
		}else if(action.equals("boduyet"))
		{
			IErpNhaCungCap nccBean = new ErpNhaCungCap(id);
			nccBean.setCongTy(ctyId);
			
			nccBean.BoDuyetNcc();
			nccList.setMsg(nccBean.getMsg());
		}
		
		nccList.search();
		nccList.createaRs();
		
		session.setAttribute("nccList", nccList);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCap.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		System.out.println("Do post ErpnhacungcapSvl");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		
		String ctyId = (String)session.getAttribute("congtyId");
		
		Utility util = new Utility();
		
		IErpNhaCungCap nccList = new ErpNhaCungCap();
		
		nccList.setCongTy(ctyId);
		
		String nextJSP = "";
		String action = request.getParameter("action");
		String nganhang = util.antiSQLInspection(request.getParameter("nganhang"));
		String chinhanh = util.antiSQLInspection(request.getParameter("chinhanh"));
		String loaiNCC = util.antiSQLInspection(request.getParameter("loaiNCC"));
		String taikhoan = util.antiSQLInspection(request.getParameter("taikhoan"));
		String tenNCC = util.antiSQLInspection(request.getParameter("tenNCC"));
		
		if (action == null)
			action = "";
		
		if (nganhang == null)
			nganhang = "";
		
		if (chinhanh == null)
			chinhanh = "";

		if (loaiNCC == null)
			loaiNCC = "";
		if (taikhoan == null)
			taikhoan = "";
		System.out.println("action " + action);

		nccList.setChiNhanh(chinhanh);
		nccList.setNganHang(nganhang);
		nccList.setLoaiNCC(loaiNCC);
		nccList.setTaiKhoan(taikhoan);
		nccList.setTen(tenNCC);
		
		String soItem = request.getParameter("soItems");
		if(soItem == null)
			soItem = "25";
		int soItems = Integer.parseInt(soItem);
		nccList.setSoItems(soItems);
		
		if (action.equals("New"))
		{
			System.out.println("Vao trang tao moi");
			IErpNhaCungCap nccBean = new ErpNhaCungCap();
			nccBean.setCongTy(ctyId);
			nccBean.createaRs();
			session.setAttribute("nccBean", nccBean);
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCapNew.jsp";
		}
		else
			if(action.equals("excel")) 
			{
			    OutputStream out = response.getOutputStream();
				response.setContentType("application/vnd.ms-excel");
		        response.setHeader("Content-Disposition", "attachment; filename=DanhSachNhaCungCap.xls");
		        
		        Workbook workbook = new Workbook();
		    	
			    CreateStaticHeader(workbook);
			    CreateStaticData(workbook, nccList);
			
			    //Saving the Excel file
			    workbook.save(out);
			    nccList.search();
				nccList.createaRs();
				nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCap.jsp";
			}
		else
		{
			System.out.println("Search------- ");
			String chixem = request.getParameter("chixem");
			nccList.setChixem(chixem);
			
			if(request.getParameter("nxtApprSplitting") != null)
			{
				nccList.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			}
			nccList.search();
			nccList.createaRs();
			nextJSP = "/TraphacoHYERP/pages/Erp/ErpNhaCungCap.jsp";
		}
		session.setAttribute("nccList", nccList);
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
	
	private void SetStyleForDataCell(Cell cell) {
		SetStyleForCell(cell, 1, Color.GRAY, Color.WHITE, Color.BLACK, false);
	}

	private void CreateStaticHeader(Workbook workbook) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	   	    
	    Cell cell = cells.getCell("A1"); 
	    cell.setValue("Danh sách Nhà cung cấp"); SetStyleForTitleCell(cell);
	   
	    cell = cells.getCell("A3");
	    cell.setValue("Ngày tạo: " + this.getDateTime()); SetStyleForTitleCell(cell);
	    
	    cell = cells.getCell("A8");
	    cell.setValue("Mã nhà cung cấp"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("B8");
	    cell.setValue("Tên nhà cung cấp"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("C8");
	    cell.setValue("Địa chỉ nhà cung cấp"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("D8");
	    cell.setValue("Điện thoại nhà cung cấp"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("E8");
	    cell.setValue("Mã số thuế"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("F8");
	    cell.setValue("Tên người liên hệ"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("G8");
	    cell.setValue("Điện thoại người liên hệ"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("H8");
	    cell.setValue("Email người liên hệ"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("I8");	    
	    cell.setValue("Tên NV phụ trách"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("J8");
	    cell.setValue("Số tài khoản ngân hàng"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("K8");
	    cell.setValue("Mã  ngân hàng"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("L8");
	    cell.setValue("Mã chi nhánh NH"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("M8");
	    cell.setValue("Tài khoản ghi nhận công nợ"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("N8");
	    cell.setValue("Thời hạn nợ"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("O8");
	    cell.setValue("Hạn mức nợ"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("P8");
	    cell.setValue("Loại NCC"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("Q8");
	    cell.setValue("Cung cấp sản phẩm"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("R8");
	    cell.setValue("Thời gian đặt hàng"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("S8");
	    cell.setValue("Đặt hàng tối thiểu"); SetStyleForHeaderCell(cell);
	    cell = cells.getCell("T8");
	    cell.setValue("Đơn vị đặt hàng"); SetStyleForHeaderCell(cell);
	  
	    worksheet.setName("NhaCungCap");
	}

	private void CreateStaticData(Workbook workbook, IErpNhaCungCap nccList) {
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
	    
	    NumberFormat formatter = new DecimalFormat("#,###,###");
		dbutils db = new dbutils();
		
		String query = " select NCC.MA,NCC.TEN,NCC.DIACHI,NCC.DIENTHOAI,NCC.MASOTHUE,NCC.TEN_NGUOILIENHE,NCC.DT_NGUOILIENHE,NCC.EMAIL_NGUOILIENHE,'' as NGUOIPHUTRACH,NCC.SOTAIKHOAN, "+
				" ISNULL(NH.MA,'') as MANH, ISNULL(CN.MA,'') as MACN,TKKT.SOHIEUTAIKHOAN,NCC.THOIHANNO,NCC.HANMUCNO, LOAINCC.TEN as LOAINCC,ISNULL(SP.TEN,'') as TENSP, "+
				" SPNCC.thoihangiaohang,SPNCC.luongdattoithieu,'' as DONVIDATHANG,'' as NGAYBATDAU, '' as NGAYKETTHUC "+
				" from ERP_NHACUNGCAP NCC "+
				" inner join ERP_LOAINHACUNGCAP LOAINCC on NCC.LOAINCC = LOAINCC.PK_SEQ "+
				" left join ERP_NGANHANG NH on NCC.NGANHANG_FK = NH.PK_SEQ "+
				" left join ERP_CHINHANH CN  on NCC.CHINHANH_FK =  CN.PK_SEQ  "+
				" LEFT JOIN Erp_TaiKhoanKT TKKT  ON  NCC.TAIKHOAN_FK =  TKKT.PK_SEQ  "+
				" left join ERP_SANPHAM_NHACUNGCAP SPNCC on  NCC.PK_SEQ = SPNCC.nhacungcap_fk "+
				" left join ERP_SANPHAM SP on SP.PK_SEQ = SPNCC.sanpham_fk "+
				" WHERE NCC.TRANGTHAI = 1 and NCC.congty_fk = " + nccList.getCongTy() + " ";
		
		if (nccList.getNganHang().length() > 0)
			query += " And NCC.NganHang_FK=" + nccList.getNganHang() + " ";
		if (nccList.getChiNhanh().length() > 0)
			query += " And NCC.ChiNhanh_FK= " + nccList.getChiNhanh() + " ";
		if (nccList.getLoaiNCC().length() > 0)
			query += " And NCC.LOAINCC =" + nccList.getLoaiNCC() + "";
		if (nccList.getTaiKhoan().trim().length() > 0)
			query += " And NCC.TaiKhoan_FK =" + nccList.getTaiKhoan() + "";
		if(nccList.getTen().trim().length() > 0)
			query += " and ( NCC.Ma like N'%" + nccList.getTen() + "%' or NCC.Ten like N'%" + nccList.getTen() + "%' ) ";
		
		query+= " ORDER BY NCC.MA ";
		
		ResultSet rs = db.get(query);
		System.out.println("[ErpNhaCungCapSvl.CreateStaticData] query = " + query);
		int i = 9;
		if(rs != null)
		{
			try 
			{
				cells.setColumnWidth(0, 15.0f);
				cells.setColumnWidth(1, 30.0f);
				cells.setColumnWidth(2, 30.0f);
				cells.setColumnWidth(3, 10.0f);
				cells.setColumnWidth(4, 10.0f);
				cells.setColumnWidth(5, 10.0f);
				cells.setColumnWidth(6, 15.0f);
				cells.setColumnWidth(7, 25.0f);
				cells.setColumnWidth(8, 40.0f);
				cells.setColumnWidth(9, 40.0f);
				cells.setColumnWidth(10, 40.0f);
				cells.setColumnWidth(11, 10.0f);
				cells.setColumnWidth(12, 10.0f);
				cells.setColumnWidth(13, 10.0f);
				cells.setColumnWidth(14, 10.0f);
				cells.setColumnWidth(15, 10.0f);
				cells.setColumnWidth(16, 40.0f);
				cells.setColumnWidth(17, 20.0f);
				cells.setColumnWidth(18, 10.0f);
				
				for(int j = 0; j < 18; j++)
					cells.setRowHeight(j, 13.0f);
				
				Cell cell = null;
				while(rs.next())
				{	
					
					cell = cells.getCell("A" + Integer.toString(i)); SetStyleForDataCell(cell); 
					cell.setValue(rs.getString("MA")); 
					
					cell = cells.getCell("B" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("TEN")); 
					
					cell = cells.getCell("C" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("DIACHI")); 
					
					cell = cells.getCell("D" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("DIENTHOAI"));
					
					cell = cells.getCell("E" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("MASOTHUE")); 
					
					cell = cells.getCell("F" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("TEN_NGUOILIENHE")); 
					
					cell = cells.getCell("G" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("DT_NGUOILIENHE"));
					
					cell = cells.getCell("H" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("EMAIL_NGUOILIENHE")); 
					
					cell = cells.getCell("I" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("NGUOIPHUTRACH")); 
					
					cell = cells.getCell("J" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("SOTAIKHOAN"));
					
					cell = cells.getCell("K" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("MANH"));

					cell = cells.getCell("L" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("MACN")); 
					
					cell = cells.getCell("M" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("SOHIEUTAIKHOAN")); 
					
					cell = cells.getCell("N" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("THOIHANNO"));
					
					cell = cells.getCell("O" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("HANMUCNO")); 
					
					cell = cells.getCell("P" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("LOAINCC"));
					
					cell = cells.getCell("Q" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("TENSP")); 
					
					cell = cells.getCell("R" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("thoihangiaohang"));
					
					cell = cells.getCell("S" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("luongdattoithieu"));
					
					cell = cells.getCell("T" + Integer.toString(i)); SetStyleForDataCell(cell);
					cell.setValue(rs.getString("DONVIDATHANG"));
					
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
