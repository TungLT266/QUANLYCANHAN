package geso.traphaco.erp.servlets.baocao;

import geso.traphaco.erp.beans.baocaotaisancodinh.IBcTaisancodinh;
import geso.traphaco.erp.beans.baocaotaisancodinh.imp.*;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
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

public class TheodoitaisancodinhSvl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	public TheodoitaisancodinhSvl() {
        super();
        
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session = request.getSession();

		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "100005";

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		
		IBcTaisancodinh obj = new BcTaisancodinh();
		obj.setCtyId(ctyId);
		obj.setuserId(userId);
//		obj.init_TheodoiTSCD();
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiTSCD.jsp";
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
			IBcTaisancodinh obj = new BcTaisancodinh();
		
			obj.setuserId(userId);		
			
			String nam = util.antiSQLInspection(request.getParameter("nam"));
			if (nam == null)
				nam = "";
			obj.setNam(nam);
			
			String loai = util.antiSQLInspection(request.getParameter("loai"));
			if (loai == null)
				loai = "";
			obj.setLoai(loai);
				
			obj.init_TheodoiTSCD();
			
			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpTheoDoiTSCD.jsp";

			if (action.equals("tao")) {
				try {
					response.setContentType("application/xlsm");
					if(obj.getLoai().equals("1"))
					{
					response.setHeader("Content-Disposition", "attachment; filename=TheodoiTSCD.xlsm");
					}
					else
					{
						response.setHeader("Content-Disposition", "attachment; filename=TheodoiCCDC.xlsm");
					}
					CreateReport(out, obj);
					obj.DBClose();

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

	private void CreateReport(OutputStream out, IBcTaisancodinh obj)throws Exception {

		try{
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			if(obj.getLoai().equals("1"))
			{
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TheodoiTSCD.xlsm");
			}
			else 
			{
				fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TheodoiCCDC.xlsm");
			}
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();

			Style style;		
			Font font = new Font();
			font.setColor(Color.BLACK);
			font.setName("Times New Roman");
			font.setSize(12);
			font.setBold(true);

			Cell cell = cells.getCell("A1");			
			cell.setValue(""); 

			cells.setRowHeight(0, 20.0f);
			cell = cells.getCell("B1");			
			cell.setValue("Công ty: " + obj.getCtyTen());
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
			
			cells.setRowHeight(2, 20.0f);
			cell = cells.getCell("N5");
			cell.setValue("NĂM: " + obj.getNam()); 
			style = cell.getStyle();
			font.setBold(true);
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.CENTER);
			cell.setStyle(style);
			font.setBold(false);
			
			ResultSet ts = obj.getTscdlist();
			int index = 10;
			font.setSize(11);
			if(ts != null){
				while(ts.next()){
					cell = cells.getCell("B" + index);
					cell.setValue(index - 9);			
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("C" + index);
					cell.setValue(ts.getString("MA"));
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
				
					cell = cells.getCell("D" + index);
					cell.setValue(ts.getString("DIENGIAI"));
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("E" + index);
					cell.setValue(ts.getString("DONVI"));
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("F" + index);
					cell.setValue(ts.getDouble("SOLUONG"));
					style.setFont(font);
					style.setNumber(3);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("G" + index);
					cell.setValue(ts.getString("NGAY"));
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("H" + index);
					cell.setValue(ts.getString("SOCHUNGTU"));	
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.LEFT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("I" + index);
					cell.setValue(ts.getString("TK"));	
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("J" + index);
					cell.setValue(ts.getString("TKKH"));	
					style.setFont(font);
					style.setNumber(0);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("K" + index);
					cell.setValue(ts.getString("TKNKH"));			
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("L" + index);
					cell.setValue(ts.getDouble("VND"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("M" + index);
					cell.setValue(ts.getDouble("NGUYENTE"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("N" + index);
					cell.setValue("");			
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("O" + index);
					cell.setValue("");			
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("P" + index);
					cell.setValue("");			
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("Q" + index);
					cell.setValue(ts.getDouble("VND"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("R" + index);
					cell.setValue(ts.getDouble("SOTHANGSUDUNG"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);


					cell = cells.getCell("S" + index);
					cell.setValue(ts.getDouble("KHAUHAOTHANG"));			
					style.setFont(font);
					style.setNumber(3);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("T" + index);
					cell.setValue(ts.getString("THANGBATDAUKH"));			
					style.setFont(font);
					style.setHAlignment(TextAlignmentType.CENTER);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("U" + index);
					cell.setValue(ts.getDouble("SOTHANGDAKH"));			
					style.setFont(font);
					style.setNumber(3);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("V" + index);
					cell.setValue(ts.getDouble("SOTHANGCONLAI"));			
					style.setFont(font);
					style.setNumber(3);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("W" + index);
					cell.setValue(ts.getDouble("GIATRIDAUKY"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("X" + index);
					cell.setValue(ts.getDouble("LUYKEDAUKY"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("Y" + index);
					cell.setValue(ts.getDouble("T1"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("Z" + index);
					cell.setValue(ts.getDouble("T2"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("AA" + index);
					cell.setValue(ts.getDouble("T3"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AB" + index);
					cell.setValue(ts.getDouble("T4"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AC" + index);
					cell.setValue(ts.getDouble("T5"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AD" + index);
					cell.setValue(ts.getDouble("T6"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AE" + index);
					cell.setValue(ts.getDouble("T7"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AF" + index);
					cell.setValue(ts.getDouble("T8"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AG" + index);
					cell.setValue(ts.getDouble("T9"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AH" + index);
					cell.setValue(ts.getDouble("T10"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AI" + index);
					cell.setValue(ts.getDouble("T11"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AJ" + index);
					cell.setValue(ts.getDouble("T12"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AK" + index);
					cell.setValue(ts.getDouble("LUYKETRONGKY"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);

					cell = cells.getCell("AL" + index);
					cell.setValue(ts.getDouble("LUYKEDAUKY") + ts.getDouble("LUYKETRONGKY"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					cell = cells.getCell("AM" + index);
					cell.setValue(ts.getDouble("CONLAICUOIKY"));			
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);
					cell.setStyle(style);
					cell = CreateBorderSetting2(cell);
					
					index++;
				}
//				cell = CreateBorderSetting(cell);
				cell = CreateBorderSetting3(cells.getCell("B" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("C" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("D" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("E" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("F" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("G" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("H" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("I" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("J" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("K" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("L" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("M" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("N" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("O" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("P" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("Q" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("R" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("S" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("T" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("U" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("V" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("W" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("X" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("Y" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("Z" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AA" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AB" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AC" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AD" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AE" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AF" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AG" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AH" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AI" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AJ" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AK" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AL" + (index - 1)));
				cell = CreateBorderSetting3(cells.getCell("AM" + (index - 1)));
				
				ts.close();
			}
			workbook.save(out);
			fstream.close();
			
		}catch(Exception ex){
			throw new Exception("Khong tao duoc bao cao:" + ex.toString());
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

		 public Cell CreateBorderSetting3(Cell cell) throws IOException
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
		        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);

		        cell.setStyle(style);
		        return cell;
		    }

		 private void setStyleColor1(Cells cells ,Cell cell)
			{
				Cell cell1 = cells.getCell("Z1");
				Style style;	
				style = cell1.getStyle();
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		        cell.setStyle(style);
			}
			
			private void setStyleColor2(Cells cells ,Cell cell)
			{
				Cell cell1 = cells.getCell("AA1");
				Style style;	
				style = cell1.getStyle();
		        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
		        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
		        cell.setStyle(style);
			}
		 
}
