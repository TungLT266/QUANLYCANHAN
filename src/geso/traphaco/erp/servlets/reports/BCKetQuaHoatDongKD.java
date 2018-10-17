package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.dms.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCKetQuaHoatDongKD extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public BCKetQuaHoatDongKD() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		
		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.InitErp();
		
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCKetQuaKinhDoanh.jsp";
		response.sendRedirect(nextJSP);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();
		
		obj.setErpCongtyId(util.antiSQLInspection(request.getParameter("congtyid")));
		session.setAttribute("congtyId", obj.getErpCongtyId());
		
		
		obj.setYear(util.antiSQLInspection(request.getParameter("nam")));
		
		obj.setMonth(util.antiSQLInspection(request.getParameter("thang")));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCKetQuaKinhDoanh.jsp";
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=BCKetQuaHoatDongKD.xlsm");
	        try 
	        {
				if(!CreatePivotTable(out, response, request, obj, ""))
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
		
		obj.InitErp();
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
	}
	

	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		
		
			fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoKetQuaKinhDoanh.xlsm");
		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		
		isFillData = FillData(workbook, obj, query);
   
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
	
	private void setStyleColorGray(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		 style = cell1.getStyle();
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleColorRed(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Z1");
		Style style;	
		 style = cell1.getStyle();
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}

	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		for(int i = 0;i < 7; ++i)
		{
	    	cells.setColumnWidth(i, 15.0f);
	    	if(i == 4)
	    		cells.setColumnWidth(i, 30.0f);
	    }	
		Cell cell = null;
		
		int thang=Integer.parseInt(obj.getMonth());
		int tuthang=thang;
		int namdk=0;
		int denthangdk=0;
		int tuthangdk=0;
		
		if(thang==0){
			//lay ky theo nam
			thang=12;
			tuthang=12;
			tuthangdk=12;
			denthangdk=12;
			namdk=Integer.parseInt(obj.getYear())-1;
		}else{
			if(thang==1){
				denthangdk=12;
				tuthangdk=denthangdk;
				namdk=Integer.parseInt(obj.getYear())-1;
			}else{
				denthangdk=thang-1;
				tuthangdk=denthangdk;
				namdk=Integer.parseInt(obj.getYear());
			}
		}
		
		cell = cells.getCell("D4");		cell.setValue("Kỳ Tính Thuế : "+obj.getYear()+"-"+ ((thang+"").length() >1 ?thang:"0"+thang) );	
		String sql="select ten,masothue  from erp_congty where pk_seq="+obj.getErpCongtyId();
		 ResultSet rs=db.get(sql);
		 
		if(rs!=null){
			if(rs.next()){
				cell = cells.getCell("C5");		cell.setValue(rs.getString("masothue"));
				cell = cells.getCell("C6");		cell.setValue(rs.getString("ten"));
			}
			rs.close();
		}
		 
		
		
			System.out.println("tuthang : "+tuthang);
			System.out.println("tuthang :"+thang);
			System.out.println("year  :"+obj.getYear());
			
			
			System.out.println("tuthang : "+tuthangdk);
			System.out.println("tuthang : "+denthangdk);
			System.out.println("namdk : "+namdk);
			
		 String[] Param={tuthang+"",""+thang,obj.getYear(),obj.getErpCongtyId()};
		 
		 

		 rs=db.getRsByPro("GETBAOCAOKETQUAKINHDOANH", Param);
		 
		 
		 if (rs != null) 
		 {
			try 
			{
				
				while (rs.next())
				{		
					
					
					cell = cells.getCell("H10") ;cell.setValue(rs.getDouble("511"));	
					cell = cells.getCell("H11") ;cell.setValue(rs.getDouble("521"));

					cell = cells.getCell("H13") ;cell.setValue(rs.getDouble("632"));

					//5
					cell = cells.getCell("H15") ;cell.setValue(rs.getDouble("515"));
					//6
					cell = cells.getCell("H16") ;cell.setValue(rs.getDouble("635"));
					//7
					cell = cells.getCell("H17") ;cell.setValue(rs.getDouble("635100"));
					//8
					cell = cells.getCell("H18") ;cell.setValue(rs.getDouble("641"));
					//9
					cell = cells.getCell("H19") ;cell.setValue(rs.getDouble("642"));
	
					//11
					cell = cells.getCell("H21") ;cell.setValue(rs.getDouble("711"));
					//12
					cell = cells.getCell("H22") ;cell.setValue(rs.getDouble("811"));
					//15
					cell = cells.getCell("H25") ;cell.setValue(rs.getDouble("821100"));
					
					//16
					cell = cells.getCell("H26") ;cell.setValue(rs.getDouble("821200"));
				}
					//cho dau ky
				 String[] Param1={tuthangdk+"",""+denthangdk,namdk+"",obj.getErpCongtyId()};
				 
				 

				 rs=db.getRsByPro("GETBAOCAOKETQUAKINHDOANH", Param1);
				 
				 
				
						
						while (rs.next())
						{		
							
							
							cell = cells.getCell("I10") ;cell.setValue(rs.getDouble("511"));	
							cell = cells.getCell("I11") ;cell.setValue(rs.getDouble("521"));

							cell = cells.getCell("I13") ;cell.setValue(rs.getDouble("632"));

							//5
							cell = cells.getCell("I15") ;cell.setValue(rs.getDouble("515"));
							//6
							cell = cells.getCell("I16") ;cell.setValue(rs.getDouble("635"));
							//7
							cell = cells.getCell("I17") ;cell.setValue(rs.getDouble("635100"));
							//8
							cell = cells.getCell("I18") ;cell.setValue(rs.getDouble("641"));
							//9
							cell = cells.getCell("I19") ;cell.setValue(rs.getDouble("642"));
			
							//11
							cell = cells.getCell("I21") ;cell.setValue(rs.getDouble("711"));
							//12
							cell = cells.getCell("I22") ;cell.setValue(rs.getDouble("811"));
							//15
							cell = cells.getCell("I25") ;cell.setValue(rs.getDouble("821100"));
							
							//16
							cell = cells.getCell("I26") ;cell.setValue(rs.getDouble("821200"));
						}
				if (rs != null){
					rs.close();
				}
				
				
				
			}
			catch(Exception ex)
			{
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
