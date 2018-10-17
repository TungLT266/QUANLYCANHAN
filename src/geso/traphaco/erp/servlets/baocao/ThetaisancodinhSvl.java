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

public class ThetaisancodinhSvl extends HttpServlet 
{
	
	private static final long serialVersionUID = 1L;

	public ThetaisancodinhSvl() {
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
		
		IBcTaisancodinh obj = new BcTaisancodinh();
		obj.setuserId(userId);
		obj.init();
		
		String ctyId = (String)session.getAttribute("congtyId");
		if(ctyId == null) ctyId = "100005";
		obj.setCtyId(ctyId);
		
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TheTaiSanCoDinh.jsp";
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
			
			String ltsId = util.antiSQLInspection(request.getParameter("ltsId"));
			if (ltsId == null)
				ltsId = "";
			obj.setLtsId(ltsId);
			session.setAttribute("ltsId", ltsId);
			
			String tsId = util.antiSQLInspection(request.getParameter("tsId"));
			if (tsId == null)
				tsId = "";
			System.out.println(tsId);
			
			obj.setTsId(tsId);
				
			obj.init();
			
			String action = request.getParameter("action");
		
			String nextJSP = "/TraphacoHYERP/pages/Erp/Erp_TheTaiSanCoDinh.jsp";

			if (action.equals("tao")) {
				try {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=TheTSCD.xlsm");
					CreateReport(out, obj);
					obj.DBClose();

				} catch (Exception ex) {
					ex.printStackTrace();
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
			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\TheTSCD.xlsm");
					
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			
			Cells cells = worksheet.getCells();
			Cell cell = null;
			int index = 3;
			Style style;		
			Font font = new Font();
			font.setColor(Color.BLACK);
			font.setName("Times New Roman");
			font.setSize(12);
			font.setBold(false);
						
			ResultSet rs = obj.getTaisan();
			if(rs != null){
				while(rs.next()){
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getString("LTS"));	  
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			
					
					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getString("MA"));	  
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			
					
					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getString("TENTAISAN"));	  
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			

					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getString("DONVI"));	  
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			

					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getDouble("SOLUONG"));	  
					style = cell.getStyle();
					style.setNumber(3);
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			
					
					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getString("NGAYBATDAUDUNG"));	  
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			
					
					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getDouble("NGUYENGIA"));	  
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			
					
					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getDouble("SOTHANGKH"));	  
					style = cell.getStyle();
					style.setNumber(3);
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			
					
					index++;
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getDouble("KHAUHAOTHANG"));	  
					style = cell.getStyle();
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.LEFT);
					style.setFont(font);
					cell.setStyle(style);			
					
				}
				rs.close();
			}
			
			
			rs = obj.getKhauhao();			
			index = 16;
//			double dakhauhao = 0;
//			double conkhauhao = 0;
			
			if(rs != null){
				while(rs.next()){
					System.out.println("vô đây");
					cell = cells.getCell("B" + String.valueOf(index));		
					cell.setValue(rs.getString("THANGTHU"));	  
					cell = CreateBorderSetting(cell);
					this.setStyleColor1(cells, cell);
					style = cell.getStyle();				
					style.setHAlignment(TextAlignmentType.CENTER);
					style.setFont(font);
					cell.setStyle(style);			
					
					cell = cells.getCell("C" + String.valueOf(index));		
					cell.setValue(rs.getDouble("NGUYENGIA"));	  
					cell = CreateBorderSetting(cell);
					this.setStyleColor1(cells, cell);
					style = cell.getStyle();
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
//					dakhauhao = dakhauhao + rs.getDouble("SOTIENDAKHAUHAO");
					
					cell = cells.getCell("D" + String.valueOf(index));		
					cell.setValue(rs.getDouble("KHAUHAOLUYKE"));	  
					cell = CreateBorderSetting(cell);
					this.setStyleColor1(cells, cell);
					style = cell.getStyle();
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
//					conkhauhao = conkhauhao + rs.getDouble("KHAUHAODUKIEN");
					
					cell = cells.getCell("E" + String.valueOf(index));		
					cell.setValue(rs.getDouble("GIATRICONLAI"));	  
					cell = CreateBorderSetting(cell);
					this.setStyleColor1(cells, cell);
					style = cell.getStyle();		
					style.setFont(font);
					style.setNumber(4);
					style.setHAlignment(TextAlignmentType.RIGHT);					
					cell.setStyle(style);
					
					
					index++;
				}
				rs.close();
			}
			

//			font.setBold(true);
//			cell = cells.getCell("B" + String.valueOf(index));		
//			cell.setValue("Cộng");	  
//			cell = CreateBorderSetting(cell);
//			style = cell.getStyle();				
//			style.setHAlignment(TextAlignmentType.CENTER);
//			style.setFont(font);
//			cell.setStyle(style);			
//			
//			cell = cells.getCell("C" + String.valueOf(index));		
//			cell.setValue(dakhauhao);	  
//			cell = CreateBorderSetting(cell);
//			style = cell.getStyle();
//			style.setFont(font);
//			style.setNumber(4);
//			style.setHAlignment(TextAlignmentType.RIGHT);					
//			cell.setStyle(style);
//
//			cell = cells.getCell("D" + String.valueOf(index));		
//			cell.setValue(conkhauhao);	  
//			cell = CreateBorderSetting(cell);
//			style = cell.getStyle();
//			style.setFont(font);
//			style.setNumber(4);
//			style.setHAlignment(TextAlignmentType.RIGHT);					
//			cell.setStyle(style);
//			
//			cell = cells.getCell("E" + String.valueOf(index));		
//			cell.setValue("");	  
//			cell = CreateBorderSetting(cell);
//			style = cell.getStyle();		
//			style.setFont(font);
//			style.setHAlignment(TextAlignmentType.RIGHT);					
//			cell.setStyle(style);
			
			workbook.save(out);
			fstream.close();
			
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Khong tao duoc header cho bao cao");
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
