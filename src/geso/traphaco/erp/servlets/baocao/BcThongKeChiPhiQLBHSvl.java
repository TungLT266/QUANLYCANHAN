package geso.traphaco.erp.servlets.baocao;
import geso.traphaco.erp.beans.bangchitiettaikhoan.IBcThongKeChiPhiQLBH;
import geso.traphaco.erp.beans.bangchitiettaikhoan.imp.*;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
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

public class BcThongKeChiPhiQLBHSvl extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;

	public BcThongKeChiPhiQLBHSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		String ctyId = (String)session.getAttribute("congtyId");
		
		IBcThongKeChiPhiQLBH obj = new BcThongKeChiPhiQLBH();
		obj.setuserId(userId);
		
		obj.setCtyId(ctyId);
		obj.setErpCongtyId(ctyId);
		
		String view = Ult.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);
		obj.init();
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/BcThongKeChiPhiQLBH.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");

		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			OutputStream out = response.getOutputStream();
			IBcThongKeChiPhiQLBH obj = new BcThongKeChiPhiQLBH();
			String ctyId = (String)session.getAttribute("congtyId");
			obj.setCtyId(ctyId);
			obj.setuserId(userId);		
			obj.setErpCongtyId(ctyId);
			
			String[] ctyIds = request.getParameterValues("ctyIds");
			obj.setCtyIds(ctyIds);
			
			obj.setView(util.antiSQLInspection(request.getParameter("view")));

			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null)
				tungay = "";
			obj.setTungay(tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null)
				denngay = "";
			obj.setDenngay(denngay);
				
			String tkId = util.antiSQLInspection(request.getParameter("tkId"));
			if (tkId == null)
				tkId = "";			
			obj.setTkId(tkId);
			
			String nhomtkId = util.antiSQLInspection(request.getParameter("nhomtkId"));
			if (nhomtkId == null)
				nhomtkId = "";			
			obj.setTkNhomId(nhomtkId);

			obj.init();
			
			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/BcThongKeChiPhiQLBH.jsp";

			if (action.equals("excel")) {
				try {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=Bangchitiettaikhoan.xls");
					CreateReport(out, obj);

				} catch (Exception ex) {
					obj.setMsg(ex.getMessage());
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
				}
			}
			
			session.setAttribute("obj", obj);
			response.sendRedirect(nextJSP);
		}
	}

	private void CreateReport(OutputStream out, IBcThongKeChiPhiQLBH obj)throws Exception {

		try{
			String file = getServletContext().getInitParameter("path") + "\\BangChiTietTaiKhoanKeToan.xlsm";
			
			System.out.println(file);
			
			FileInputStream fstream = new FileInputStream(file);
				
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();

			Style style;		
			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(10);
			font.setBold(true);
			
			Cell cell = cells.getCell("A1");			
			cell.setValue(""); 

			cells.setRowHeight(0, 20.0f);
			cell = cells.getCell("B1");			
			cell.setValue("Đơn vị: " + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.setRowHeight(1, 20.0f);
			cell = cells.getCell("B2");
			cell.setValue("Địa chỉ: " + obj.getDiachi());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			
			cells.setRowHeight(2, 20.0f);
			cell = cells.getCell("B3");
			cell.setValue("Mã số thuế: " + obj.getMasothue()); 
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			
			cells.merge(7, 1, 7, 8);
			cell = cells.getCell("B8");
			font.setColor(Color.NAVY);
			cell.setValue("Tháng " + obj.getMonth() + " Năm " + obj.getYear());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			cells.merge(8, 1, 8, 8);
			cell = cells.getCell("B9");
			font.setColor(Color.NAVY);
			cell.setValue(obj.getSohieu());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
						
			cell = cells.getCell("G12");
			font.setColor(Color.BLACK);
			cell.setValue(Double.parseDouble(obj.getDaukyno()));			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("H12");
			font.setColor(Color.BLACK);
			cell.setValue(Double.parseDouble(obj.getDaukyco()));			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			ResultSet rs = obj.getData();
			int row = 13;
			double tongno = 0;
			double tongco = 0;
			while(rs.next()){
				
				cell = cells.getCell("A" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("TAIKHOAN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("B" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("NGAYCHUNGTU"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("C" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("SOCHUNGTU"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("D" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("SOHOADON"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				cell = cells.getCell("E" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("DIENGIAI"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("F" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("DOIUNG"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				cell = cells.getCell("G" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("NO"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				tongno = tongno + rs.getDouble("NO");
				
				cell = cells.getCell("H" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("CO"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				tongco = tongco + rs.getDouble("CO");
				
				cell = cells.getCell("I" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("NOIDUNG"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);				
				
				cell = cells.getCell("J" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("DOITUONG"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("K" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TEN_PB"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("L" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TEN_KBH"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("M" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TEN_VV"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("N" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TEN_DIABAN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("O" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TEN_TINHTHANH"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("P" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TEN_BENHVIEN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("Q" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TEN_SANPHAM"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("R" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("KYHIEU"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("S" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("NGAYHOADON"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("T" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TENNCC"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("U" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("MST"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("V" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("DIENGIAI_CT"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("W" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("Tienhang"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				//style.setNumber(0);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("X" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("thuesuat"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				//style.setNumber(0);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("Y" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("vat"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				//style.setNumber(0);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("Z" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("soluong"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				//style.setNumber(0);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("AA" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("mahang"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("AB" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("tenhang"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("AC" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("donvi"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				//style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				row++;
			}

			cells.merge(row - 1, 1, row - 1, 5);
			cell = cells.getCell("B" + row);
			font.setColor(Color.BLACK);
			cell.setValue("Cộng phát sinh trong kỳ");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("C" + row));
			cell = CreateBorderSetting(cells.getCell("D" + row));
			cell = CreateBorderSetting(cells.getCell("E" + row));
			cell = CreateBorderSetting(cells.getCell("F" + row));
			
			cell = cells.getCell("G" + row);
			font.setColor(Color.BLACK);
			cell.setValue(tongno);			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setNumber(3);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("H" + row);
			font.setColor(Color.BLACK);
			cell.setValue(tongco);			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("I" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("J" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			row++;
			cells.merge(row - 1, 1, row - 1, 5);
			cell = cells.getCell("B" + row);
			font.setColor(Color.BLACK);
			cell.setValue("Số dư cuối kỳ");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("C" + row));
			cell = CreateBorderSetting(cells.getCell("D" + row));
			cell = CreateBorderSetting(cells.getCell("E" + row));
			cell = CreateBorderSetting(cells.getCell("F" + row));
			
			double cuoiky = Double.parseDouble(obj.getDaukyno()) - Double.parseDouble(obj.getDaukyco()) + tongno - tongco;
			
			if(cuoiky > 0){
				cell = cells.getCell("G" + row);
				font.setColor(Color.BLACK);
				cell.setValue(cuoiky);			
				font.setSize(10);
				font.setBold(true);
				font.setItalic(true);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting(cell);
				
				cell = cells.getCell("H" + row);
				font.setColor(Color.BLACK);
				cell.setValue(0);			
				font.setSize(10);
				font.setBold(true);
				font.setItalic(true);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);				
				cell = CreateBorderSetting(cell);
				
			}else if(cuoiky <= 0){
				cell = cells.getCell("G" + row);
				font.setColor(Color.BLACK);
				cell.setValue(0);			
				font.setSize(10);
				font.setBold(true);
				font.setItalic(true);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);				
				cell = CreateBorderSetting(cell);
				
				cell = cells.getCell("H" + row);
				font.setColor(Color.BLACK);
				cell.setValue(cuoiky*(-1));			
				font.setSize(10);
				font.setBold(true);
				font.setItalic(true);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);				
				cell = CreateBorderSetting(cell);
			}
			System.out.println("Cuoi ky:" + cuoiky);

			cell = cells.getCell("I" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);				
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("J" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);				
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("K" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);				
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("L" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);				
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("M" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);				
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("N" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);				
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("O" + row);
			font.setColor(Color.BLACK);
			cell.setValue("");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);				
			cell = CreateBorderSetting(cell);

			workbook.save(out);
			fstream.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}

		private String getDate() 
		{
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}
		
		 public Cell CreateBorderSetting(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

		 public Cell CreateBorderSetting2(Cell cell) throws IOException
		    {
			
		        Style style;
		        style = cell.getStyle();

		        //Set border color
		        style.setBorderColor(BorderType.TOP, Color.BLACK);
		        style.setBorderColor(BorderType.BOTTOM, Color.BLACK);
		        style.setBorderColor(BorderType.LEFT, Color.BLACK);
		        style.setBorderColor(BorderType.RIGHT, Color.BLACK);

		        //Set the cell border type
		        style.setBorderLine(BorderType.TOP, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.DOTTED);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

}
