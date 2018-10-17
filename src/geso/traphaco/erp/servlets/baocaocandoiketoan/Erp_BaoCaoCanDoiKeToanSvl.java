package geso.traphaco.erp.servlets.baocaocandoiketoan;

import geso.dms.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.baocaocandoiketoan.IErp_BaoCaoCanDoiKeToanList;
import geso.traphaco.erp.beans.baocaocandoiketoan.imp.Erp_BaoCaoCanDoiKeToanList;

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
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.aspose.cells.TextAlignmentType;

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

public class Erp_BaoCaoCanDoiKeToanSvl extends HttpServlet 
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	   
	   public Erp_BaoCaoCanDoiKeToanSvl() {
			super();
		}   
	   
	   protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	   {
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    PrintWriter out = response.getWriter();
		    	    
		    HttpSession session = request.getSession();	    
	      
		    Utility util = new Utility();
		    out = response.getWriter();
		    
		    Erp_BaoCaoCanDoiKeToanList obj = new Erp_BaoCaoCanDoiKeToanList();
		    
		    String querystring = request.getQueryString();
		    String userId = util.getUserId(querystring);
		    
		    String congTyId = (String)session.getAttribute("congtyId");
		    if (congTyId == null)
		    	congTyId = "0";
		    obj.setCongTyId(congTyId);

		    String action = util.getAction(querystring);
		    out.println(action);
		    

	    	if (action.equals("exportExcel"))
	    	{
//	    		obj.export
	    	}
		   	
			obj.init();
			obj.DBClose();
			
	    	session.setAttribute("obj", obj);

			session.setAttribute("userId", userId);
	    		
			response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCanDoiKeToan.jsp");
		}  	
		
		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
		{
		    request.setCharacterEncoding("UTF-8");
		    response.setCharacterEncoding("UTF-8");
		    response.setContentType("text/html; charset=UTF-8");
		    Utility util = new Utility();
		    String querystring = request.getQueryString();
		    
			HttpSession session = request.getSession();
			IErp_BaoCaoCanDoiKeToanList obj = new Erp_BaoCaoCanDoiKeToanList();
			 
		    String userId = request.getParameter("userId");
		    
		    String action = request.getParameter("action");
		    if (action == null){
		    	action = util.getAction(querystring);
		    	if (action == null)
		    		action = "";
		    }
		      
		    String congTyId = (String)session.getAttribute("congtyId");
		    if (userId.length()==0)
		    	userId = request.getParameter("userId");
		    obj.setCongTyId(congTyId);
		    
		    String yearCK = request.getParameter("yearCK");
		    if (yearCK == null)
		    	yearCK = "";
		    obj.setYear(yearCK);
		    
		    String monthCK = request.getParameter("monthCK");
		    if (monthCK == null)
		    	monthCK = "";
		    obj.setMonth(monthCK);
		    
		    String yearDK = request.getParameter("yearDK");
		    if (yearDK == null)
		    	yearDK = "";
		    obj.setYearDK(yearDK);
		    
		    String monthDK = request.getParameter("monthDK");
		    if (monthDK == null)
		    	monthDK = "";
		    obj.setMonthDK(monthDK);
		    		    		    
		    String congTyLId = request.getParameter("congTyLId");
		    if (congTyLId == null)
		    	congTyLId = "0";
		    obj.setCongTyId(congTyLId);
		    
		    if (action.equals("exportExcel"))
		    {
		    	OutputStream out = response.getOutputStream();
		    	response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoTaiChinh_" + geso.traphaco.center.util.Utility.getCurrentDate()+ ".xlsm");
				
		    	obj.init();
		    	
		        try 
		        {
		        	System.out.println("VAodday");
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
		        	session.setAttribute("obj", obj);
		    		response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCanDoiKeToan.jsp");
		    		e.printStackTrace();
					obj.setMsg("Khong the tao bao cao " + e.getMessage());
				}
		        
		    }
		    
		    session.setAttribute("userId", userId);
	    	session.setAttribute("obj", obj);
	    	response.sendRedirect("/TraphacoHYERP/pages/Erp/Erp_BaoCaoCanDoiKeToan.jsp");
	    	
		    obj.DBClose();
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
		
		private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IErp_BaoCaoCanDoiKeToanList obj) throws Exception 
		{
			boolean isFillData = false;
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();			
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoTaiChinh.xlsm");
							
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			isFillData = FillData(workbook, obj);
	   
			
			
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
			Cell cell1 = cells.getCell("D13");
			Style style;	
			style = cell1.getStyle();		 
	        cell.setStyle(style);
	        
	        
		}
		
		private boolean FillData(Workbook workbook, IErp_BaoCaoCanDoiKeToanList obj) throws Exception
		{
			dbutils db = new dbutils();
			Worksheets worksheets = workbook.getWorksheets();
			
			Worksheet worksheet = worksheets.getSheet("CDKT");
			
			Cells cells = worksheet.getCells();	
			Cell cell = null;
			
			int index = 11;
						
			ResultSet rs = obj.getRsBCCDKT();
			
			if ( rs != null) 
			{
				try 
				{
					cell = null;
					while (rs.next())
					{		
						if(rs.getString("ISCONGTHUCEXCEL").equals("0"))
						{
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs.getDouble("kynay"));		
							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs.getDouble("kytruoc"));	
							
						}
						index++;
					}

					if (rs != null){
						rs.close();
					}					
					
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
			
			
			worksheet = worksheets.getSheet("KQKD");
			cells = worksheet.getCells();	
			
			index = 12;
			
			ResultSet rs_HDKD = obj.getRsBCHDKD();
			
			if ( rs_HDKD != null) 
			{
				try 
				{
					cell = null;
					while (rs_HDKD.next())
					{		
						if(rs_HDKD.getString("ISCONGTHUCEXCEL").equals("0"))
						{
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs_HDKD.getDouble("kynay"));		
							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs_HDKD.getDouble("kytruoc"));
						}
						index++;
					}

					if (rs_HDKD != null){
						rs_HDKD.close();
					}					
					
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
			
			
			worksheet = worksheets.getSheet("LCTT");
			cells = worksheet.getCells();	
			
			index = 11;
			
			ResultSet rs_LCTT = obj.getRsBCLCTT();
			
			if ( rs_LCTT != null) 
			{
				try 
				{
					cell = null;
					while (rs_LCTT.next())
					{		
						if(rs_LCTT.getString("ISCONGTHUCEXCEL").equals("0"))
						{
							cell = cells.getCell("D" + String.valueOf(index));		cell.setValue(rs_LCTT.getDouble("kynay"));		
							cell = cells.getCell("E" + String.valueOf(index));		cell.setValue(rs_LCTT.getDouble("kytruoc"));
						}
						index++;
					}

					if (rs_LCTT != null){
						rs_LCTT.close();
					}					
					
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
			
			worksheet = worksheets.getSheet("CDPS");
			cells = worksheet.getCells();	
						
			int row = 13;
			double daukyno = 0;
			double daukyco = 0;
			double phatsinhno = 0;
			double phatsinhco = 0;
			double cuoikyno = 0;
			double cuoikyco = 0;
			double cuoiky = 0;
			
			Style style;		
			Font font = new Font();
			font.setColor(Color.NAVY);
			font.setSize(10);
			font.setBold(true);
			
			cells.setRowHeight(0, 20.0f);
			style = cell.getStyle();
			style.setFont(font);
			style.setHAlignment(TextAlignmentType.LEFT);
			cell.setStyle(style);
			
			ResultSet rs_CDPS = obj.getRsBCCDPS();
			
			if ( rs_CDPS != null) 
			{
				try 
				{
					cell = null;
					while (rs_CDPS.next())
					{
						cell = cells.getCell("B" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getDouble("SOHIEUTAIKHOAN"));			
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
						
						cell = cells.getCell("C" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getString("TENTAIKHOAN"));			
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(0);
						style.setHAlignment(TextAlignmentType.LEFT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
		
						cell = cells.getCell("D" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getDouble("NO_DK"));				
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(3);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
		
						daukyno = daukyno + rs_CDPS.getDouble("NO_DK");
		
						cell = cells.getCell("E" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getDouble("CO_DK"));			
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(3);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
						
						daukyco = daukyco + rs_CDPS.getDouble("CO_DK");
						
						cell = cells.getCell("F" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getDouble("PS_NO"));			
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(3);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
						
						phatsinhno = phatsinhno + rs_CDPS.getDouble("PS_NO");
						
						cell = cells.getCell("G" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getDouble("PS_CO"));			
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(3);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
						
						phatsinhco = phatsinhco + rs_CDPS.getDouble("PS_CO");
						
						cell = cells.getCell("H" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getDouble("NO_CK"));			
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(3);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
						
						cuoikyno = cuoikyno + rs_CDPS.getDouble("NO_CK");
						
						cell = cells.getCell("I" + row);
						font.setColor(Color.BLACK);
						cell.setValue(rs_CDPS.getDouble("CO_CK"));			
						font.setSize(10);
						font.setBold(false);
						style.setFont(font);
						style.setNumber(3);
						style.setHAlignment(TextAlignmentType.RIGHT);
						cell.setStyle(style);
						cell = CreateBorderSetting2(cell);
						
						cuoikyco = cuoikyco + rs_CDPS.getDouble("CO_CK");
						
						row++;
					}

					if (rs_CDPS != null){
						rs_CDPS.close();
					}					
					
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
	}