package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.beans.tienguinganhang.ISotienguinganhang;
import geso.traphaco.erp.beans.tienguinganhang.imp.*;
import geso.traphaco.center.util.Utility;

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

public class SotienguinganhangSvl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	public SotienguinganhangSvl() {
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
		
		ISotienguinganhang obj = new Sotienguinganhang();
		obj.setuserId(userId);
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setCtyId(ctyId);
		obj.setErpCongtyId(ctyId);
		
		String view = Ult.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		String chiNhanhId = Ult.antiSQLInspection(request.getParameter("chiNhanhId"));
		chiNhanhId = chiNhanhId != null ? chiNhanhId : "1";
		obj.setChiNhanh(chiNhanhId);
		
		obj.setView(view);
		obj.init();		

		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Sotienguinganhang.jsp";
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

			OutputStream out = response.getOutputStream();
			ISotienguinganhang obj = new Sotienguinganhang();
		
			obj.setuserId(userId);		
			
			String ctyId = (String)session.getAttribute("congtyId");
			
			obj.setCtyId(ctyId);

			obj.setErpCongtyId(ctyId);

			String chiNhanhId = cutil.antiSQLInspection(request.getParameter("chiNhanhId"));
			chiNhanhId = chiNhanhId != null ? chiNhanhId : "1";
			obj.setChiNhanh(chiNhanhId);
			String[] ctyIds = request.getParameterValues("ctyIds");
			obj.setCtyIds(ctyIds);
			
			obj.setView(util.antiSQLInspection(request.getParameter("view")));

			String tungay = util.antiSQLInspection(request.getParameter("tungay"));
			if (tungay == null) {
				tungay = "";
				obj.setMsg("Vui lòng chọn từ ngày");
			}
			obj.setTungay(tungay);

			String denngay = util.antiSQLInspection(request.getParameter("denngay"));
			if (denngay == null){
				denngay = "";
				obj.setMsg("Vui lòng chọn đến ngày");
			}
			obj.setDenngay(denngay);
				
			String tkId = util.antiSQLInspection(request.getParameter("tkId"));
			if (tkId == null){
				tkId = "";
				obj.setMsg("Vui lòng chọn tài khoản");
			}
			obj.setTkId(tkId);

			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/Sotienguinganhang.jsp";
			
			session.setAttribute("obj", obj);
			response.setContentType("text/html; charset=UTF-8");

			if (action.equals("tao")) {
				if(tungay.length() > 0 & denngay.length() > 0 & tkId.length() > 0){
					try {
						obj.init();
						response.setContentType("application/xlsm");
						response.setHeader("Content-Disposition", "attachment; filename=Sotienguinganhang.xlsm");
						CreateReport(out, obj);

					} catch (Exception ex) {
						obj.setMsg(ex.getMessage());
						session.setAttribute("obj", obj);
						response.sendRedirect(nextJSP);
						return;
					}
				}
			}

			obj.init();		
			response.sendRedirect(nextJSP);
		}
	}

	private void CreateReport(OutputStream out, ISotienguinganhang obj)throws Exception {

		try{
			String file = getServletContext().getInitParameter("path") + "\\Sotienguinganhang.xlsm";
			
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

			cells.setColumnWidth(0, 10.71f);
			cells.setColumnWidth(1, 10.71f);
			cells.setColumnWidth(2, 9.57f);
			cells.setColumnWidth(3, 9.57f);
			cells.setColumnWidth(4, 48.29f);
			cells.setColumnWidth(5, 12.14f);
			cells.setColumnWidth(6, 21.43f);
			cells.setColumnWidth(7, 21.43f);
			cells.setColumnWidth(8, 21.43f);
			
			Cell cell = cells.getCell("A1");			
			cell.setValue(""); 

			cells.setRowHeight(0, 20.0f);
			cell = cells.getCell("B1");			
			cell.setValue("Đơn vị: " + obj.getCtyTen());
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cell = cells.getCell("H1");			
			cell.setValue("Mẫu sổ : S09 -SKT/DNN");
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
			
			cell = cells.getCell("H2");			
			cell.setValue("Ban hành theo QĐ số 1177 - TC/QD/CDKT");
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
			
			cell = cells.getCell("H3");			
			cell.setValue("ngày 23/12/1996 của Bộ Tài Chính");
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.merge(4, 4, 4, 6);
			cell = cells.getCell("E5");
			font.setColor(Color.MAGENTA);
			cell.setValue("SỔ CHI TIẾT TIỀN GỬI NGÂN HÀNG");			
			font.setSize(16);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			cells.merge(5, 4, 5, 6);
			cell = cells.getCell("E6");
			font.setColor(Color.NAVY);
			cell.setValue("Từ ngày " + obj.getTungay() + " Đến ngày " + obj.getDenngay());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			
			cell = cells.getCell("E8");
			font.setColor(Color.NAVY);
			cell.setValue("Nơi mở tài khoản giao dịch: " + obj.getNganhang());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cell = cells.getCell("E9");
			font.setColor(Color.NAVY);
			cell.setValue("Số hiệu tài khoản: " + obj.getSoTaikhoan());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cell = cells.getCell("E10");
			font.setColor(Color.NAVY);
			cell.setValue("Loại tiền gửi: " + obj.getTiente());			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);

			cells.merge(11, 0, 12, 0);
			cell = cells.getCell("A12");
			font.setColor(Color.NAVY);
			cell.setValue("Tài khoản");
			font.setSize(10);
			font.setBold(true);		
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setTextWrapped(true);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("A13"));
			
			cells.merge(11, 1, 12, 1);
			cell = cells.getCell("B12");
			font.setColor(Color.NAVY);
			cell.setValue("Ngày");
			font.setSize(10);
			font.setBold(true);		
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			style.setTextWrapped(true);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("B13"));
			
			cells.merge(11, 2, 11, 3);
			cell = cells.getCell("C12");
			font.setColor(Color.NAVY);
			cell.setValue("Số phiếu");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("D12"));
			
			cell = cells.getCell("C13");
			font.setColor(Color.NAVY);
			cell.setValue("UN Thu");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("D13");
			font.setColor(Color.NAVY);
			cell.setValue("UN Chi");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cells.merge(11, 4, 12, 4);
			cell = cells.getCell("E12");
			font.setColor(Color.NAVY);
			cell.setValue("Diễn giải chứng từ");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("E13"));
			
			cells.merge(11, 5, 12, 5);
			cell = cells.getCell("F12");
			font.setColor(Color.NAVY);
			cell.setValue("Tài khoản đối ứng");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("F13"));
			
			cells.merge(11, 6, 11, 8);
			cell = cells.getCell("G12");
			font.setColor(Color.NAVY);
			cell.setValue("SỐ TIỀN");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("H12"));
			cell = CreateBorderSetting(cells.getCell("I12"));
			
			cell = cells.getCell("G13");
			font.setColor(Color.NAVY);
			cell.setValue("Gửi vào (VNĐ)");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);			
					
			cell = cells.getCell("H13");
			font.setColor(Color.NAVY);
			cell.setValue("Rút ra (VNĐ)");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			
			cell = cells.getCell("I13");
			font.setColor(Color.NAVY);
			cell.setValue("Còn lại (VNĐ)");			
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			cells.merge(13, 1, 13, 5);
			cell = cells.getCell("B14");
			font.setColor(Color.BLACK);
			cell.setValue("Số dư đầu kỳ");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);
			cell = CreateBorderSetting(cells.getCell("B14"));
			cell = CreateBorderSetting(cells.getCell("C14"));
			cell = CreateBorderSetting(cells.getCell("D14"));
			cell = CreateBorderSetting(cells.getCell("E14"));
			cell = CreateBorderSetting(cells.getCell("F14"));
			cell = CreateBorderSetting(cells.getCell("G14"));
			cell = CreateBorderSetting(cells.getCell("H14"));
			
			double dauky = Double.parseDouble(obj.getSodudau());
			
			cell = cells.getCell("I14");
			font.setColor(Color.BLACK);
			cell.setValue(dauky);			
			style.setNumber(3);
			font.setSize(10);
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
			cell.setStyle(style);
			cell = CreateBorderSetting(cell);

			font.setItalic(false);

			ResultSet rs = obj.getData();
			int row = 15;
			double tongthu = 0;
			double tongchi = 0;
			
			while(rs.next()){
				cell = cells.getCell("A" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TAIKHOAN"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("B" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("NGAY"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.CENTER);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				if(rs.getString("SOCHUNGTU_THU").trim().length() > 0){
					cell = cells.getCell("C" + row);
					font.setColor(Color.BLACK);
					cell.setValue(rs.getString("SOCHUNGTU_THU"));			
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
				}
				
				if(rs.getString("SOCHUNGTU_CHI").trim().length() > 0){
					cell = cells.getCell("D" + row);
					font.setColor(Color.BLACK);
					cell.setValue(rs.getString("SOCHUNGTU_CHI"));			
					font.setSize(10);
					font.setBold(false);
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
				}
			
				String diengiai = "";
				if(rs.getString("DIENGIAI_CHI").trim().length() > 0){
					diengiai = rs.getString("DIENGIAI_CHI");
				}else{
					diengiai = rs.getString("DIENGIAI_THU");
				}
				cell = cells.getCell("E" + row);
				font.setColor(Color.BLACK);
				cell.setValue(diengiai);			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.LEFT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				
				cell = cells.getCell("F" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getString("TK_DOIUNG"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(0);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				

				cell = cells.getCell("G" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("THU"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				tongthu = tongthu + rs.getDouble("THU");
				
				cell = cells.getCell("H" + row);
				font.setColor(Color.BLACK);
				cell.setValue(rs.getDouble("CHI"));			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
				style.setHAlignment(TextAlignmentType.RIGHT);
				cell.setStyle(style);
				cell = CreateBorderSetting2(cell);
				tongchi = tongchi + rs.getDouble("CHI");
				
				cell = cells.getCell("I" + row);
				font.setColor(Color.BLACK);
				cell.setValue(dauky + tongthu - tongchi);			
				font.setSize(10);
				font.setBold(false);
				style.setFont(font);
				style.setNumber(3);
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
			cell = CreateBorderSetting(cells.getCell("B" + row));
			cell = CreateBorderSetting(cells.getCell("C" + row));
			cell = CreateBorderSetting(cells.getCell("D" + row));
			cell = CreateBorderSetting(cells.getCell("E" + row));
			cell = CreateBorderSetting(cells.getCell("F" + row));
			cell = CreateBorderSetting(cells.getCell("I" + row));
			
			cell = cells.getCell("G" + row);
			font.setColor(Color.BLACK);
			cell.setValue(tongthu);			
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
			cell.setValue(tongchi);			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.RIGHT);
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
			cell = CreateBorderSetting(cells.getCell("B" + row));
			cell = CreateBorderSetting(cells.getCell("C" + row));			
			cell = CreateBorderSetting(cells.getCell("D" + row));
			cell = CreateBorderSetting(cells.getCell("E" + row));
			cell = CreateBorderSetting(cells.getCell("F" + row));
			cell = CreateBorderSetting(cells.getCell("G" + row));
			cell = CreateBorderSetting(cells.getCell("H" + row));
			
			double cuoiky = dauky + tongthu - tongchi;
			
			
			cell = cells.getCell("I" + row);
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
			
			System.out.println("Cuoi ky:" + cuoiky);
			row = row + 3; 
			
			cells.merge(row - 1 , 7, row - 1, 8);
			cell = cells.getCell("H" + row);
			font.setColor(Color.BLACK);
			cell.setValue("Ngày... Tháng... Năm 201...");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);				

			row++;
			cell = cells.getCell("E" + row);
			font.setColor(Color.BLACK);
			cell.setValue("Kế toán trưởng");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);				

			cells.merge(row - 1 , 7, row - 1, 8);
			cell = cells.getCell("H" + row);
			font.setColor(Color.BLACK);
			cell.setValue("Giám Đốc");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);				

			row++;
			cell = cells.getCell("E" + row);
			font.setColor(Color.BLACK);
			cell.setValue("(Ký, họ tên)");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);				

			cells.merge(row - 1 , 7, row - 1, 8);
			cell = cells.getCell("H" + row);
			font.setColor(Color.BLACK);
			cell.setValue("(Ký, họ tên)");			
			font.setSize(10);
			font.setBold(true);
			font.setItalic(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);				

			workbook.save(out);
			fstream.close();
			
		}catch(Exception ex){
			throw new Exception("Khong tao duoc header cho bao cao:" + ex.toString());
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
