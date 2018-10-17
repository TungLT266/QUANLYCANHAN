package geso.traphaco.erp.servlets.baocao;

import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
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

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpReportFromStoreProcSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpReportFromStoreProcSvl() {
        super();
    } 
    
    NumberFormat formatter = new DecimalFormat("#,###,###");
    NumberFormat formatter2 = new DecimalFormat("#,###,###.#######");
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new Baocao();
	    obj.setUserId(userId);
	    obj.createRs();
		session.setAttribute("obj", obj);
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpReportFromStoreProc.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		OutputStream out = response.getOutputStream(); 
		IBaocao obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    String userId = request.getParameter("userId");

	    obj = new Baocao();
	    obj.setUserId(userId);
	    
	    String tungay = request.getParameter("tungay");
	    if(tungay == null)
	    	tungay = "";
	    obj.setTuNgay(tungay);
	    
	    String denngay = request.getParameter("denngay");
	    if(denngay == null)
	    	denngay = "";
	    obj.setDenNgay(denngay);
	    String khoId = request.getParameter("khoId");
	    if(khoId == null)
	    	khoId = "";
	    obj.setKhoIds(khoId);
	    
	    String khoTen = request.getParameter("khoTen");
	    if(khoTen == null)
	    	khoTen = "";
	    obj.setKhoTen(khoTen);
	    
	    String check_laysolieucophatsinh = request.getParameter("check_laysolieucophatsinh");
	    if(check_laysolieucophatsinh == null)
	    	check_laysolieucophatsinh = "";
	    obj.setCheck_SpCoPhatSinh(check_laysolieucophatsinh);
	    
	    String[] lspIds = request.getParameterValues("loaisanpham");
	    String lspId = "";
	    if(lspIds != null)
	    {
	    	for(int i = 0; i < lspIds.length; i++)
	    		lspId += lspIds[i] + ",";
	    	if(lspId.length() > 0)
	    		lspId = lspId.substring(0, lspId.length() - 1);
	    	obj.setLoaiSanPhamIds(lspId);
	    }
	    
		String[] maspIds = request.getParameterValues("maspIds");
		String maspId = "";
		if (maspIds != null)
		{
			for (int i = 0; i < maspIds.length; i++)
				maspId += "'" + maspIds[i] + "',";
			if (maspId.length() > 0)
				maspId = maspId.substring(0, maspId.length() - 1);
			obj.setMaSanPhamIds(maspId);
		}

		String[] dvkdIds = request.getParameterValues("dvkdIds");
		String dvkdId = "";
		if (dvkdIds != null)
		{
			for (int i = 0; i < dvkdIds.length; i++)
				dvkdId +=  dvkdIds[i] + ",";
			if (dvkdId.length() > 0)
				dvkdId = dvkdId.substring(0, dvkdId.length() - 1);
			obj.setDvkdIds(dvkdId);
		}
		
	    String[] spIds = request.getParameterValues("spIds");
	    String spId = "";
	    if(spIds != null)
	    {
	    	for(int i = 0; i < spIds.length; i++)
	    		spId += spIds[i] + ",";
	    	if(spId.length() > 0)
	    		spId = spId.substring(0, spId.length() - 1);
	    	obj.setSanPhamIds(spId);
	    }
	    
	    String action = request.getParameter("action");
	    //System.out.println("Action nhan duoc: " + action + " -- LOAI SP: " + obj.getLoaiSanPhamIds() );
	    
	    if(action.equals("search"))
	    {
	    	obj.createRs();
	    	session.setAttribute("obj", obj);
			
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpReportFromStoreProc.jsp";
			response.sendRedirect(nextJSP);
	    }
	    else
	    {
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "Attachment; filename=ReportFromStoreProc(NPP).xlsm");
			 
			try
			{
				CreatePivotTable(out, response, request, obj); 
				
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				request.getSession().setAttribute("errors", ex.getMessage());
			 
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpReportFromStoreProc.jsp";
				response.sendRedirect(nextJSP);
				
			}
		}
	}
 
	private void CreatePivotTable(OutputStream out, HttpServletResponse response, HttpServletRequest request,  IBaocao obj) throws Exception
	{
		try
		{
			
			String strfstream = getServletContext().getInitParameter("path") + "\\ReportFromStoreProc(NPP).xlsm";
			 
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			Worksheets worksheets = workbook.getWorksheets();
	  		Worksheet worksheet_2 = worksheets.getSheet("sheet1");
	  		worksheet_2.setName("REPORT_XUATNHAPTON_CHITIET_LO");
	  		 Cells cells = worksheet_2.getCells();
			   
	  		 	Cell	cell = cells.getCell("P1");
				Style style1=cell.getStyle();
				 
				dbutils db = new dbutils();
			
				String[] param = new String[3];
				param[0] = obj.getKhoIds().equals("") ? null : obj.getKhoIds();
				param[1] = obj.getTuNgay().equals("") ? null : obj.getTuNgay();
				param[2] = obj.getDenNgay().equals("") ? null : obj.getDenNgay();
				 
				ResultSet	rs = db.getRsByPro("REPORT_XUATNHAPTON_CHITIET_LO", param);
		  		worksheets = workbook.getWorksheets();
		  		//Worksheet worksheet_REPORT_XUATNHAPTON_CHITIET_LO = worksheets.addSheet("REPORT_XUATNHAPTON_CHITIET_LO");
		  		this.TaoBaoCao(db,rs,worksheet_2, obj,"REPORT_XUATNHAPTON_CHITIET_LO",style1);
		  		 
		  	 
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	public String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}
	private void TaoBaoCao(dbutils db,ResultSet rs,Worksheet worksheet, IBaocao obj, String diengiai, Style style12) 
	{
		  try{
			  
			   Cells cells = worksheet.getCells();
			   
			   for(int i=0;i<30;i++){
				   cells.setColumnWidth(i, 20.0f);   
			   }
			
			    cells.setRowHeight(0, 50.0f);
			    Cell cell = cells.getCell("A1");
			    ReportAPI.getCellStyle(cell, Color.RED, true, 14, diengiai);
			    
			    cell = cells.getCell("A3");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 10, "Từ ngày : " + obj.getTuNgay() + "   Đến ngày: " + obj.getDenNgay());
				cell = cells.getCell("A4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày tạo: " +this.getDateTime() );
				
				cell = cells.getCell("B4");
				ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tạo bởi : " + obj.getUserTen());
				
				cell = cells.getCell("AH2");
				Style style1=cell.getStyle();
				

			   worksheet.setGridlinesVisible(false);
			   
			 
			   
			   ResultSetMetaData rsmd = rs.getMetaData();
			   int socottrongSql = rsmd.getColumnCount();
			   
			   int countRow = 4;

			   for( int i =3 ; i <=socottrongSql ; i ++  )
			   {
			    cell = cells.getCell(countRow,i-3 );
			    
		    	ReportAPI.setCellBackground(cell, Color.GRAY, BorderLineType.THIN, false, 0);
		    	
		    	ReportAPI.getCellStyle(cell, "Arial", Color.WHITE, true, 9, rsmd.getColumnName(i));
		    	
			   }
			   countRow ++;
			   
			   NumberFormat formatter = new DecimalFormat("#,###,###");
			   while(rs.next())
			   {
				   Color color_=Color.BLACK;
				      if(rs.getDouble(2) <rs.getDouble(1) &&  rs.getDouble(20) != 0  ){
				    	  color_=Color.RED;
				      }
			    for(int i = 3; i <= socottrongSql; i ++)
			    {
			     
			     cell = cells.getCell(countRow,i-3 );
			     if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i)==Types.NUMERIC || rsmd.getColumnType(i)==Types.INTEGER )
			     {
			    	 if(rsmd.getColumnLabel(i).equals("") )
			    	 cell.setStyle(style12);
			    	 ReportAPI.getCellStyle_double(cell, "Arial", color_, false, 9,  rs.getDouble(i));
			     // ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
			     }
			     else
			     {
			    	 ReportAPI.getCellStyle(cell, "Arial", color_, false, 9, rs.getString(i));
			    //  ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
			     }
			    }
			    ++countRow;
			   }
			   
			   if(rs!=null)rs.close();
			    

			 
			  }catch(Exception ex){
				  ex.printStackTrace();
				    
			  }
	}

	 
 
}
