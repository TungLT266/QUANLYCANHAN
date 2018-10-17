package geso.traphaco.erp.servlets.lenhsanxuat;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.lenhsanxuat.IErpChuyenkhoSX;
import geso.traphaco.erp.beans.lenhsanxuat.imp.*;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.Types;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import com.aspose.cells.BorderLineType;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;

public class ErpPhieuxuatchuyennoiboPdfSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpPhieuxuatchuyennoiboPdfSvl() {
        super();
    }

    float CONVERT = 28.346457f;  // =1cm
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{	
		IErpChuyenkhoSX obj;
		String userId;
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();	    
	    String Id = util.getId(querystring);
	    
	    userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    obj = new ErpChuyenkhoSX();
	    //obj.initdisplay(ddhId);
	     
		if(querystring.contains("print"))
		{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=YeuCauChuyenKho.xlsm");
			ServletOutputStream outstream = response.getOutputStream();			
			
			try 
			{
				ExportToExcel(outstream, Id);
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			obj.DBclose();
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		this.doGet(request, response);
	}
	
	private void ExportToExcel(OutputStream out, String id ) throws Exception
	{
		try
		{ 		
			String strfstream = getServletContext().getInitParameter("path") + "\\DonDuTru.xlsm";
			
			FileInputStream fstream = new FileInputStream(strfstream);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

			String query = " select '' STT, c.MA , c.TEN, d.DONVI, b.soluongyeucau as soluong, a.LYDO as GHICHU " + 
						   " from ERP_YEUCAUCHUYENKHO a inner join ERP_YEUCAUCHUYENKHO_SANPHAM b on a.PK_SEQ = b.yeucauchuyenkho_fk " + 
						   " 	inner join ERP_SANPHAM c on c.PK_SEQ = b.sanpham_fk \n"+
						   "	inner join DONVIDOLUONG d on d.PK_SEQ=b.dvdl_fk \n"+
						   "	where a.PK_SEQ = " + id;
		
			
			System.out.println("::: Query chi tiet la: " + query);
			TaoBaoCao(workbook, id, query, 0);
			workbook.save(out);			
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}

	}
	
	private void TaoBaoCao(com.aspose.cells.Workbook workbook,String id,String query,int sheetNum ) throws Exception
	{
		try
		{
			com.aspose.cells.Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(sheetNum);
			com.aspose.cells.Cells cells = worksheet.getCells();
		
			com.aspose.cells.Cell cell = cells.getCell("B3");
			
			dbutils db = new dbutils();

			String sql = " select SUBSTRING (a.NgayYeuCau, 9, 2 ) as ngay, SUBSTRING (a.NgayYeuCau, 6, 2) as thang, SUBSTRING (a.NgayYeuCau, 0, 5) as nam, b.Ma, b.TEN " + 
						 " from ERP_YEUCAUCHUYENKHO a left join NHAPHANPHOI b on a.doituongnhan_fk = b.PK_SEQ " + 
						 " where a.PK_SEQ = " + id;
	
			System.out.println(":: INFO: " + sql);
			ResultSet rssql=db.get(sql);
			rssql.next();
			
		
			cell = cells.getCell(2,0);	
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
			System.out.println("Ngày "+rssql.getString("ngay")+" Tháng "+rssql.getString("thang")+" Năm "+rssql.getString("nam"));
			cell.setValue("Ngày "+rssql.getString("ngay")+" Tháng "+rssql.getString("thang")+" Năm "+rssql.getString("nam"));
			
			cell = cells.getCell(3,0);
			ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, true, 41);
			cell.setValue("Đơn vị dự trù :"+rssql.getString("ma")+"-"+rssql.getString("ten"));
			rssql.close();
			
			ResultSet rs = db.get(query);

			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();

			int countRow = 5;
			int column = -1;
	
			rs = db.get(query);
			int j=1;
			while(rs.next())
			{
				for(int i =1;i <=socottrongSql ; i ++)
				{
					Color c = Color.WHITE;
					cell = cells.getCell(countRow,column + i);
					
					if(rsmd.getColumnName(i).equals("STT"))
					{
						cell.setValue(j);
						ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
					}
					else
					{
						if(rsmd.getColumnType(i) == Types.DOUBLE || rsmd.getColumnType(i) == Types.INTEGER || rsmd.getColumnType(i) == Types.DECIMAL )
						{
							cell.setValue(rs.getDouble(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 41);
						}
						else
						{
							cell.setValue(rs.getString(i));
							ReportAPI.setCellBackground(cell, c, BorderLineType.THIN, true, 0);
						}
					}
					
				}
				j++;
				++countRow;
			}
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
}



