package geso.traphaco.erp.servlets.baocao;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.bangchitiettaikhoan.INhatkytaikhoan;
import geso.traphaco.erp.beans.bangchitiettaikhoan.imp.Nhatkytaikhoan;

import java.io.FileInputStream;
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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCell;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


public class NhatkytaikhoanSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public NhatkytaikhoanSvl() {
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
		
		INhatkytaikhoan obj = new Nhatkytaikhoan();
		String ctyId = (String)session.getAttribute("congtyId");

		obj.setCtyId(ctyId);
		obj.setErpCongtyId(ctyId);
		obj.setuserId(userId);
		
		String view = Ult.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);

		obj.init();
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Nhatkytaikhoan.jsp";
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
			INhatkytaikhoan obj = new Nhatkytaikhoan();
			String ctyId = (String)session.getAttribute("congtyId");
			
			obj.setuserId(userId);		
			obj.setCtyId(ctyId);
			obj.setErpCongtyId(ctyId);
			String[] ctyIds = request.getParameterValues("ctyIds");
			obj.setCtyIds(ctyIds);
			
			obj.setView(util.antiSQLInspection(request.getParameter("view")));
			
			String year = util.antiSQLInspection(request.getParameter("year"));
			if (year == null)
				year = "";
			obj.setYear(year);

			String month = util.antiSQLInspection(request.getParameter("month"));
			if (month == null)
				month = "";
			
			obj.setMonth(month);
			
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

			String nhomTaiKhoanId = util.antiSQLInspection(request.getParameter("nhomTaiKhoanId"));
			if (nhomTaiKhoanId == null)
				nhomTaiKhoanId = "";
			obj.setNhomTaiKhoanId(nhomTaiKhoanId);
			
			String loaiNghiepVu = util.antiSQLInspection(request.getParameter("loaiNghiepVu"));
			if (loaiNghiepVu == null)
				loaiNghiepVu = "";
			obj.setLoaiNghiepVu(loaiNghiepVu);
			
			obj.init();
			
			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/Nhatkytaikhoan.jsp";

			if (action.equals("tao")) {
				try {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=Nhatkytaikhoan.xls");

				} catch (Exception ex) {
					obj.setMsg(ex.getMessage());
					session.setAttribute("obj", obj);
					response.sendRedirect(nextJSP);
					return;
				}
			}
			if(action.equals("excel")){
				
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=NhatKyTaiKhoan.xlsm");
				
				//ToExcel(response, obj.getTimkiem());
				
				 try 
			        {
						if(!CreatePivotTable(out, response, request, obj))
						{
							response.setContentType("text/html");
						    PrintWriter writer = new PrintWriter(out);
						    writer.print("Xin loi khong co bao cao trong thoi gian nay");
						    writer.close();
						}
					} 
			        catch (Exception e) 
			        {
						obj.setMsg("Khong the tao bao cao " + e.getMessage());
						System.out.println(e.toString());
					}
				
			}
		
			session.setAttribute("obj", obj);
			
			response.sendRedirect(nextJSP);
		}
	}
	
	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,INhatkytaikhoan obj) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
				
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoNhatKyTaiKhoan.xlsm");
						
		System.out.println(getServletContext().getInitParameter("path") + "\\BaoCaoNhatKyTaiKhoan.xlsm");
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		//CreateHeader(workbook, obj);
		isFillData = FillData(workbook, obj, obj.getTimkiem());
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();		 
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       //style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
        cell.setStyle(style);
        
        
	}
	
	private void setStyleColorGray(Cells cells ,Cell cell, String leftright)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
        if(leftright.equals("1")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);        	
        }
        
	}
	
	
	private boolean FillData(Workbook workbook, INhatkytaikhoan obj, String sqlTimKiem) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
				
		ResultSet rs = db.get(sqlTimKiem);
		int index = 11;
		
		double NO = 0;
		double CO =0;
		
		double SUMNO = 0;
		double SUMCO =0;
		
		int STT = 0;
		if (rs != null) 
		{
			try 
			{
				Cell cell = null;
				cell = null;
				while (rs.next())
				{		
					STT++;
					cell = cells.getCell("A" + String.valueOf(index));		cell.setValue(STT);	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("B" + String.valueOf(index));		cell.setValue(rs.getString("NGAYCHUNGTU"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("C" + String.valueOf(index));		cell.setValue(rs.getString("SOCHUNGTU"));	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getString("NOIDUNG"));	
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getString("DIENGIAI"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("F" + String.valueOf(index));		cell.setValue(rs.getString("TAIKHOAN"));
					this.setStyleColorNormar(cells, cell);
					cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(rs.getString("DOIUNG"));
					this.setStyleColorNormar(cells, cell);	
					
					NO = rs.getDouble("NO");
					//SUMNO = SUMNO+ Math.round(NO);
					SUMNO = SUMNO+ NO;
					cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(NO);
					this.setStyleColorNormar(cells, cell);
					CO = rs.getDouble("CO");
					//SUMCO = SUMCO+ Math.round(CO);
					SUMCO = SUMCO+ CO;
					
					cell = cells.getCell("I" + String.valueOf(index));		cell.setValue(CO);	
					this.setStyleColorNormar(cells, cell);	
					cell = cells.getCell("J" + String.valueOf(index));		cell.setValue(rs.getString("DOITUONG"));
					this.setStyleColorNormar(cells, cell);					
					cell = cells.getCell("K" + String.valueOf(index));		cell.setValue(rs.getString("SOHOADON"));	
					this.setStyleColorNormar(cells, cell);
					index++;					
				}

				if (rs != null){
					rs.close();
				}
				cell = cells.getCell("A" + String.valueOf(index));		cell.setValue("");	
				this.setStyleColorGray(cells, cell,"0");
				cell = cells.getCell("B" + String.valueOf(index));		cell.setValue("Tổng cộng");
				this.setStyleColorGray(cells, cell,"0");
				cell = cells.getCell("C" + String.valueOf(index));		cell.setValue("");	
				this.setStyleColorGray(cells, cell,"0");
				cell = cells.getCell("D" + String.valueOf(index));		cell.setValue("");	
				this.setStyleColorGray(cells, cell,"0");
				cell = cells.getCell("E" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell,"0");
				cell = cells.getCell("F" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell,"0");
				cell = cells.getCell("G" + String.valueOf(index));		cell.setValue(SUMNO);
				this.setStyleColorGray(cells, cell, "1");			
				cell = cells.getCell("H" + String.valueOf(index));		cell.setValue(SUMCO);	
				this.setStyleColorGray(cells, cell, "1");
				cell = cells.getCell("I" + String.valueOf(index));		cell.setValue("");
				this.setStyleColorGray(cells, cell,"0");			
				cell = cells.getCell("J" + String.valueOf(index));		cell.setValue("");	
				this.setStyleColorGray(cells, cell,"0");	
				
				
				
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			
				throw new Exception(ex.getMessage());
			}
		}
		else
		{
			return false;
		}	
		if(db != null)
			db.shutDown();
		return true;
	}	
	
	@SuppressWarnings("null")
	private void ToExcel(HttpServletResponse response, String query)
	{
		System.out.println("Cau excel "+query);

		NumberFormat formatter = new DecimalFormat("#,###,###"); 
//		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=NhatKyTaiKhoan.xls"); //-----TÊN FILE LƯU RA MÁY---//
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("Nhật ký tài khoản", k);//ten sheet
			sheet.addCell(new Label(0, 1, "NHẬT KÝ TÀI KHOẢN: ", new WritableCellFormat(cellTitle)));

			sheet.addCell(new Label(0, 2, "Ngày tạo: "));// cột dòng
			sheet.addCell(new Label(1, 2, "" + getDateTime()));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "NGÀY", cellFormat));
			sheet.addCell(new Label(2, 4, "SỐ CHỨNG TỪ", cellFormat));
			sheet.addCell(new Label(3, 4, "NỘI DUNG", cellFormat));
			sheet.addCell(new Label(4, 4, "TÀI KHOẢN", cellFormat));
			sheet.addCell(new Label(5, 4, "TÀI KHOẢN ĐỐI ỨNG", cellFormat));
			sheet.addCell(new Label(6, 4, "SỐ TIỀN NỢ (VNĐ)", cellFormat));
			sheet.addCell(new Label(7, 4, "SỐ TIỀN CÓ (VNĐ)", cellFormat));
			sheet.addCell(new Label(8, 4, "ĐỐI TƯỢNG", cellFormat));
			sheet.addCell(new Label(9, 4, "SỐ HÓA ĐƠN", cellFormat));
						
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 30);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 35);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 30);
			sheet.setColumnView(9, 25);
			
			dbutils db = new dbutils();

			ResultSet rs = db.get(query);

			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat4 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			//cellFormat4.setBackground(jxl.format.Colour.YELLOW);
			cellFormat4.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat4.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat4.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat4.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat5 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			//cellFormat5.setBackground(jxl.format.Colour.YELLOW);
			cellFormat5.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat5.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat5.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat5.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat6 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			//cellFormat6.setBackground(jxl.format.Colour.YELLOW);
			cellFormat6.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat6.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat6.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat6.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat7 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			//cellFormat7.setBackground(jxl.format.Colour.YELLOW);
			cellFormat7.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat7.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat7.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat7.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			
			WritableCellFormat cellFormat8 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			//cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat8.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat8.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat8.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat8.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat = null;
		
			
			
			Label label;
			Number number;
			
			double NO = 0;
			double CO = 0;
			
			double SUMNO = 0;
			double SUMCO = 0;
			
			int stt = 0;
			while (rs.next())
			{
				stt++;
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell((WritableCell) number);
				label = new Label(1, j, rs.getString("NGAYCHUNGTU"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("SOCHUNGTU"), cformat);
				sheet.addCell(label);
				label = new Label(3, j, rs.getString("NOIDUNG"), cformat);
				sheet.addCell(label);
				label = new Label(4, j, rs.getString("TAIKHOAN"), cformat);
				sheet.addCell(label);	
				label = new Label(5, j, rs.getString("DOIUNG"), cformat);
				sheet.addCell(label);	
				NO = rs.getDouble("NO");
				SUMNO = SUMNO+ NO;
				label = new Label(6, j, formatter.format(NO), cformat);
				sheet.addCell(label);	
				CO = rs.getDouble("CO");
				SUMCO = SUMNO+ CO;
				label = new Label(7, j, formatter.format(CO), cformat);
				sheet.addCell(label);	
				label = new Label(8, j, rs.getString("DOITUONG"), cformat);
				sheet.addCell(label);
				label = new Label(9, j, rs.getString("SOHOADON"), cformat);
				sheet.addCell(label);	
				j++;
			}
			
			rs.close();
			
			stt++;
			
			System.out.println("j++:"+j);
			String type = "0";
			
			System.out.println("SUMNO:"+SUMNO);
			System.out.println("SUMCO:"+SUMCO);
			
			cformat = type.equals("1") ? cellFormat4 : cellFormat4;
			number = new Number(0, j, stt, cformat);
			sheet.addCell((WritableCell) number);
			label = new Label(1, j, "", cellFormat5);
			sheet.addCell(label);
			label = new Label(2, j, "", cellFormat7);
			sheet.addCell(label);
			label = new Label(3, j, "Tổng cộng", cellFormat7);
			sheet.addCell(label);
			label = new Label(4, j, "", cellFormat7);
			sheet.addCell(label);	
			label = new Label(5, j, "", cellFormat6);
			sheet.addCell(label);
			label = new Label(6, j, formatter.format(SUMNO), cellFormat8);
			sheet.addCell(label);	
			label = new Label(7, j, formatter.format(SUMCO), cellFormat8);
			sheet.addCell(label);	
			label = new Label(8, j, "", cellFormat7);
			sheet.addCell(label);
			label = new Label(9, j, "", cellFormat7);
			sheet.addCell(label);
			
			w.write();
			w.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		}
	}

	private String getDateTime() 
	 {
	        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
	        Date date = new Date();
	        return dateFormat.format(date); 
	 }
}