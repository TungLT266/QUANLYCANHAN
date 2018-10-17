package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


/**
 * Servlet implementation class XuatExcel_PhieuNhapThanhPhamSvl
 */
@WebServlet("/XuatExcel_PhieuNhapThanhPhamSvl")
public class XuatExcel_PhieuNhapThanhPhamSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public XuatExcel_PhieuNhapThanhPhamSvl() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		OutputStream out = response.getOutputStream();
		HttpSession session = request.getSession();
		Utility util = new Utility();
		String querystring = request.getQueryString();
		String congTyId = (String) session.getAttribute("congtyId");
		System.out.println("congTyId" + congTyId);
		String userId = util.getUserId(querystring);
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));
		System.out.println("userId" + userId);
		
		String id = util.getId(querystring);
		if (id.length() == 0)
			id = util.antiSQLInspection(request.getParameter("id"));
		System.out.println("id" + id);
		
		IErpChuyenkho bean = new ErpChuyenkho(id);
		
		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		 
			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition", "attachment; filename=PhieuNhapThanhPham.xlsm");
			try {
				CreateExcel(response, request,out, bean);
				
			} catch (Exception e) {
				e.printStackTrace();
			}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	
	private boolean CreateExcel(HttpServletResponse response, HttpServletRequest request, OutputStream out, IErpChuyenkho bean) throws Exception {
		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\PhieuNhapThanhPham.xlsm");
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			dbutils db = new dbutils();
			
		
			String query = "select CK.PK_SEQ as sophieu, CK.NgayChuyen as ngaychuyen, isnull(KHOXUAT.Ten,'') as khochuyen, "
					+ " isnull(KHONHAN.Ten,'') as khonhan,\n"+ 
						 " isnull(CK.LyDo,'') as lydo\n"+ 
						 " from ERP_CHUYENKHO CK \n"+ 
						 " left join ERP_KHOTT KHOXUAT on KHOXUAT.PK_SEQ=CK.KhoXuat_FK\n"+ 
						 " left join ERP_KHOTT KHONHAN on KHONHAN.PK_SEQ=CK.KhoNhan_FK\n"+  
						 " where CK.PK_SEQ='"+bean.getId()+"'";
			System.out.println("___Init------------------------: " + query);
			
			ResultSet info = db.get(query);
			String sophieu = "";
			String ngaychuyen = "";
			String khochuyen = "";
			String khonhan = "";
			String lydo = "";
			
			while(info.next()){
				sophieu=info.getString("sophieu");
				ngaychuyen=info.getString("ngaychuyen");
				khochuyen=info.getString("khochuyen");
				khonhan=info.getString("khonhan");
				lydo=info.getString("lydo");
			}
			info.close();
			
			System.out.print("SO PHIEU:" + sophieu);
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			Cells cells = worksheet.getCells();
			Cell cell = null;
			
			cell = cells.getCell("A4");
			cell.setValue("Từ ngày: "+ngaychuyen);
			
			cell = cells.getCell("A5");
			cell.setValue("Đến ngày: "+ngaychuyen);
			 
			cell = cells.getCell("A8");
			cell.setValue(sophieu);
			 
			cell = cells.getCell("B8");
			cell.setValue(ngaychuyen);
			 
			cell = cells.getCell("C8");
			cell.setValue(khochuyen);
			 
			cell = cells.getCell("D8");
			cell.setValue(khonhan);
			 
			cell = cells.getCell("E8");
			cell.setValue(lydo);
			
			
			workbook.save(out);
			fstream.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}
	
	 
}
