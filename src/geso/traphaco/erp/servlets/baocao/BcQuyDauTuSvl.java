package geso.traphaco.erp.servlets.baocao;
import geso.traphaco.erp.beans.bangcandoiphatsinh.IBangcandoiphatsinh;
import geso.traphaco.erp.beans.bangcandoiphatsinh.imp.*;
import geso.traphaco.center.util.Utility;
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
import java.io.FileInputStream;

public class BcQuyDauTuSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public BcQuyDauTuSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		
		IBangcandoiphatsinh obj = new Bangcandoiphatsinh();
		obj.setuserId(userId);
		
		String ctyId = (String)session.getAttribute("congtyId");
		
		obj.setErpCongtyId(ctyId);
		
		String view = Ult.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);
		obj.init();
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/BCQuyDauTu.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Utility util=new Utility();
		   
		HttpSession session = request.getSession();
		
		String userId = (String) session.getAttribute("userId");  
		String userTen = (String) session.getAttribute("userTen");  	
		String sum = (String) session.getAttribute("sum");
		Utility cutil = (Utility) session.getAttribute("util");

		if(!cutil.check(userId, userTen, sum)){
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		}else{
		
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");

			OutputStream out = response.getOutputStream();
			IBangcandoiphatsinh obj = new Bangcandoiphatsinh();

			
			String ctyId = (String)session.getAttribute("congtyId");
			obj.setuserId(userId);		
			obj.setCtyId(ctyId);
			
			String[] ctyIds = request.getParameterValues("ctyIds");
			obj.setCtyIds(ctyIds);
			obj.setView(util.antiSQLInspection(request.getParameter("view")));
			
			obj.setErpCongtyId(ctyId);
			
			String year = util.antiSQLInspection(request.getParameter("year"));
			if (year == null)
				year = "";
			obj.setYear(year);

			String month = util.antiSQLInspection(request.getParameter("month"));
			if (month == null)
				month = "";
			
			obj.setMonth(month);
			obj.init();

			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/BCQuyDauTu.jsp";

			if (action.equals("tao")) {
				try {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=BCQuyDauTu.xls");
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

	private void CreateReport(OutputStream out, IBangcandoiphatsinh obj)throws Exception {

		try{
			String file = getServletContext().getInitParameter("path") + "\\BCQuyDauTu.xlsm";
			
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

			cells.setColumnWidth(0, 1.4f);
			cells.setColumnWidth(1, 10.14f);
			cells.setColumnWidth(2, 52.4f);
			cells.setColumnWidth(3, 13.29f);
			cells.setColumnWidth(4, 13.29f);
			cells.setColumnWidth(5, 13.29f);
			cells.setColumnWidth(6, 13.29f);
			cells.setColumnWidth(7, 13.29f);
			cells.setColumnWidth(8, 13.29f);
			
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
			
			cells.merge(6, 1, 6, 8);
			cell = cells.getCell("B7");
			font.setColor(Color.MAGENTA);
			cell.setValue("BẢNG CÂN ĐỐI SỐ PHÁT SINH");			
			font.setSize(16);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
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
			
			cell = cells.getCell("I9");
			font.setColor(Color.NAVY);
			cell.setValue("Đơn vị: VNĐ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);

			cells.merge(9, 1, 10, 1);
			cell = cells.getCell("B10");
			font.setColor(Color.NAVY);
			cell.setValue("Số hiệu tài khoản");
			font.setSize(10);
			font.setBold(true);		
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setTextWrapped(true);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("B11"));
			
			cells.merge(9, 2, 10, 2);
			cell = cells.getCell("C10");
			font.setColor(Color.NAVY);
			cell.setValue("Tên tài khoản");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("C11"));
			
			cells.merge(9, 3, 9, 4);
			cell = cells.getCell("D10");
			font.setColor(Color.NAVY);
			cell.setValue("Số dư đầu kỳ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("E10"));
			
			cells.merge(9, 5, 9, 6);
			cell = cells.getCell("F10");
			font.setColor(Color.NAVY);
			cell.setValue("Phát sinh trong kỳ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("G10"));
			
			cells.merge(9, 7, 9, 8);
			cell = cells.getCell("H10");
			font.setColor(Color.NAVY);
			cell.setValue("Số dư cuối kỳ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("I10"));
			
			cell = cells.getCell("D11");
			font.setColor(Color.NAVY);
			cell.setValue("Nợ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);			
			
			cell = cells.getCell("E11");
			font.setColor(Color.NAVY);
			cell.setValue("Có");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("F11");
			font.setColor(Color.NAVY);
			cell.setValue("Nợ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("G11");
			font.setColor(Color.NAVY);
			cell.setValue("Có");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("H11");
			font.setColor(Color.NAVY);
			cell.setValue("Nợ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("I11");
			font.setColor(Color.NAVY);
			cell.setValue("Có");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("B12");
			font.setColor(Color.NAVY);
			cell.setValue("A");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("C12");
			font.setColor(Color.NAVY);
			cell.setValue("B");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("D12");
			font.setColor(Color.NAVY);
			cell.setValue(1);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("E12");
			font.setColor(Color.NAVY);
			cell.setValue(2);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("F12");
			font.setColor(Color.NAVY);
			cell.setValue(3);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("G12");
			font.setColor(Color.NAVY);
			cell.setValue(4);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("H12");
			font.setColor(Color.NAVY);
			cell.setValue(5);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("I12");
			font.setColor(Color.NAVY);
			cell.setValue(6);			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			ResultSet rs = obj.getData();
			int row = 13;
			double daukyno = 0;
			double daukyco = 0;
			double phatsinhno = 0;
			double phatsinhco = 0;
			double cuoikyno = 0;
			double cuoikyco = 0;
			double cuoiky = 0;
			while(rs.next()){
				cell = cells.getCell("B" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("SOHIEUTAIKHOAN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("C" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TENTAIKHOAN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				cell = cells.getCell("D" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("DAUKYNOVND"));				
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				daukyno = daukyno + rs.getDouble("DAUKYNOVND");

				cell = cells.getCell("E" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("DAUKYCOVND"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				daukyco = daukyco + rs.getDouble("DAUKYCOVND");
				
				cell = cells.getCell("F" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("PHATSINHNOVND"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				phatsinhno = phatsinhno + rs.getDouble("PHATSINHNOVND");
				
				cell = cells.getCell("G" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("PHATSINHCOVND"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				phatsinhco = phatsinhco + rs.getDouble("PHATSINHCOVND");
				
				cell = cells.getCell("H" + row);
				font.setColor(Color.BLACK);

				if(rs.getDouble("CUOIKY") > 0){
					cell.setValue(rs.getDouble("CUOIKY"));
					cuoikyno = cuoikyno + rs.getDouble("CUOIKY");
				}else {
					cell.setValue(0);
				}

				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);

				cell = cells.getCell("I" + row);
				font.setColor(Color.BLACK);

				if(rs.getDouble("CUOIKY") < 0){
					cell.setValue(rs.getDouble("CUOIKY")*(-1));
					cuoikyco = cuoikyco + rs.getDouble("CUOIKY")*(-1);
				}else {
					cell.setValue(0);
				}
				
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				row++;
			}
			
			cell = cells.getCell("B" + row);
			font.setColor(Color.RED);
			cell.setValue("");
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("C" + row);
			font.setColor(Color.RED);
			cell.setValue("Tổng cộng");
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("D" + row);
			font.setColor(Color.RED);
			cell.setValue(daukyno);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("E" + row);
			font.setColor(Color.RED);
			cell.setValue(daukyco);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("F" + row);
			font.setColor(Color.RED);
			cell.setValue(phatsinhno);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("G" + row);
			font.setColor(Color.RED);
			cell.setValue(phatsinhco);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("H" + row);
			font.setColor(Color.RED);
			cell.setValue(cuoikyno);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cell = cells.getCell("I" + row);
			font.setColor(Color.RED);
			cell.setValue(cuoikyco);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setPatternColor(Color.CYAN);
			style.setPatternStyle(Short.parseShort("0"));
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			row = row + 2;
			cells.merge(row - 1, 6, row - 1, 8);
			cell = cells.getCell("G" + row );
			font.setColor(Color.BLACK);
			cell.setValue("Ngày " + obj.getDate().substring(8, 10) + " tháng " + obj.getDate().substring(5, 7) + " năm " + obj.getDate().substring(0, 4) + "");
			font.setSize(10);
			font.setItalic(true);
			font.setBold(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			row = row + 1;
			cell = cells.getCell("C" + row );
			font.setColor(Color.BLACK);
			cell.setValue("Người lập");
			font.setSize(10);
			font.setBold(true);
			font.setItalic(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			cells.merge(row - 1, 6, row - 1, 8);
			cell = cells.getCell("G" + row );
			font.setColor(Color.BLACK);
			cell.setValue("Giám đốc");
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			row = row + 1;
			cell = cells.getCell("C" + row );
			font.setColor(Color.BLACK);
			cell.setValue("(Ký, họ tên)");
			font.setSize(10);
			font.setItalic(true);
			font.setBold(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);

			cells.merge(row - 1, 6, row - 1, 8);
			cell = cells.getCell("G" + row );
			font.setColor(Color.BLACK);
			cell.setValue("(Ký, họ tên)");
			font.setSize(10);
			font.setItalic(true);
			font.setBold(false);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);			
			cell.setStyle(style);			
			workbook.save(out);
			fstream.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
			System.out.println(ex.toString());
			throw new Exception("Khong tao duoc header cho bao cao");
		}
	}

		private String getDate() 
		{
	        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	        Date date = new Date();
	        return dateFormat.format(date);	
		}
		
		
		private String queryBCDauTu() 
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
