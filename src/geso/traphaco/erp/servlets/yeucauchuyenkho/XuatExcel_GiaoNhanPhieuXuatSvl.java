package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.dms.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class XuatExcel_GiaoNhanPhieuXuatSvl  extends HttpServlet  {
	private static final long serialVersionUID = 1L;

	public XuatExcel_GiaoNhanPhieuXuatSvl() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	  
				
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpReportBCChuyenKho.jsp";
		response.sendRedirect(nextJSP);

	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		String baocao = request.getParameter("baocao");
		if (baocao == null) {
			baocao= "";
		}
		
		String tungay = request.getParameter("tungay");
		if (tungay == null) {
			tungay= "";
		}
		
		String denngay = request.getParameter("denngay");
		if (denngay == null) {
			denngay= "";
		}
		
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		
		System.out.println("\n id:" + id);
		System.out.println("Action = " + baocao+"  "+denngay);

		if(baocao.equals("3")){
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=GiaoNhanPhieuXuat.xlsm");
			try {
				CreateExcel(baocao,response, request,out,id,tungay,denngay,"GiaoNhanPhieuXuat.xlsm");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}else if(baocao.equals("1")){
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=PhieuNhapThanhPham.xlsm");
			try {
				CreateExcel(baocao,response, request,out,id,tungay,denngay,"PhieuNhapThanhPham.xlsm");
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		else{
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=PhieuNhapVatTu.xlsm");
			try {
				CreateExcel(baocao,response, request,out,id,tungay,denngay,"PhieuNhapVatTu.xlsm");

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private boolean CreateExcel(String baocao,HttpServletResponse response, HttpServletRequest request, OutputStream out,
			String id,String tungay,String denngay,String nameFile) throws Exception {

		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\"+nameFile);
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			dbutils db = new dbutils();
		
			String query="select * from FN_BCChuyenKho("+baocao+") where ngaychuyen>='"+tungay+"' and ngaychuyen <='"+denngay+"'" ;
			System.out.println("xuat bc "+query);
			
			///
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;

			cell = cells.getCell("A4");
			cell.setValue("Từ ngày: "+tungay);
			
			cell = cells.getCell("A5");
			cell.setValue("Đến ngày: "+denngay);
			
			ResultSet rs=db.get(query);
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			//int vitri1=30; //cot
			//int vitri2=30; //hang
			int row=7;
			while(rs.next()){
				for(int i = 1; i <= socottrongSql+2; i ++)
				{
					cell = cells.getCell(row,i-1);
					
					if(i<6)
					{
						geso.traphaco.erp.servlets.baocao.ReportAPI.getCellStyle_wraptext(cell, "Times New Roman",Color.BLACK, false, 11, rs.getString(i));
					}
					else
						if(i==6){
							geso.traphaco.erp.servlets.baocao.ReportAPI.getCellStyle_wraptext2(cell, "Times New Roman",Color.BLACK, false, 11, "☐");
						}
						else
							geso.traphaco.erp.servlets.baocao.ReportAPI.getCellStyle_wraptext(cell, "Times New Roman",Color.BLACK, false, 11, "");
				}
				row++;	
			}
			rs.close();
			System.out.println("xuat "+row);
			
			
			workbook.save(out);
			fstream.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
/*
public String LayQuery(String tungay,String denngay,String baocao){
	// lay thong tin don hang
		String query="select ck.NgayChuyen as ngaychuyen ,ck.pk_seq as sophieu,  \n"+ 
				"   isnull(khoxuat.ten,'') as khoxuat,isnull(khonhan.ten,'') as khonhan,isnull(ck.LyDo,'') as lydo \n"+ 
				"   from ERP_CHUYENKHO ck \n"+ 
				"   left join ERP_KHOTT khoxuat on ck.KhoXuat_FK=khoxuat.pk_seq \n"+ 
				"   left join ERP_KHOTT khonhan on ck.khonhan_fk=khonhan.pk_seq \n"+ 
				"   where ck.ngaychuyen>='"+tungay+"' and ck.ngaychuyen <='"+denngay+"'" ;
				
	if(baocao.equals("1")){
		
	}else if(baocao.equals("2")){
		query+=" and noidungxuat_fk in ('100066','100068') ";
	}else{
		query+=" and noidungxuat_fk in ('100059','100060') ";
	}
	System.out.println("GiaoNhanPhieuXuat: "+query);
	
	return query;
	
}*/
}
