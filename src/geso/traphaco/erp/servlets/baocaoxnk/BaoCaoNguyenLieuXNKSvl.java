package geso.traphaco.erp.servlets.baocaoxnk;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.text.DecimalFormat;
import java.text.NumberFormat;

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
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;

public class BaoCaoNguyenLieuXNKSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public BaoCaoNguyenLieuXNKSvl()
	{
		super();
	}

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

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj = new Baocao();
		obj.setUserId(userId);
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Erp/BaoCaoNguyenLieuXK.jsp";
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

		String userTen = (String) session.getAttribute("userTen");
		String userId = request.getParameter("userId");

		obj = new Baocao();
		obj.setUserId(userId);
		obj.setUserTen(userTen);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);

		String action = request.getParameter("action");
		
		response.setContentType("application/xlsm");
		response.setHeader("Content-Disposition", "attachment; filename=BaoCaoNguyenLieuXK.xlsx");
		
		try
		{
			OutputStream outstream = response.getOutputStream();
			this.CreateExcel(outstream, obj);
			return;
		} 
		catch (Exception e)
		{
			obj.setMsg(e.getMessage());
			e.printStackTrace();
		}
	}
	
	private void CreateExcel(OutputStream outstream,IBaocao tthdBean){

		try
		{
			NumberFormat formatter = new DecimalFormat("#,###,###");

			// get data from db
			dbutils db = new dbutils();
			String query =  "select sp.TEN, isnull((select top(1) SODANGKY from ERP_HANMUCNHAPKHAU temp "
					+ " \n where temp.SANPHAM_FK =b.sanpham_fk and temp.trangthai =1 ),'') as sodangky,"
					+ " \n isnull((select top(1) TUNGAY from ERP_HANMUCNHAPKHAU temp "
					+ " \n where temp.SANPHAM_FK =b.sanpham_fk and temp.trangthai =1 ),'') as ngaycap, "
					+ " \n isnull( (select TENNHASANXUAT from ERP_MUAHANG  "
					+ " \n where PK_SEQ in( select PO_NHAPKHAU "
					+ " \n from ERP_PARK where pK_seq = (select park_fk "
					+ " \n from ERP_HOADONNCC where PK_SEQ =a.HOADONNCC))),'') as TENNHASANXUAT "
					+ " ,'' as NUOCSX, a.MAHS,dv.DONVI, b.SOLUONG,b.DONGIANT,a.LOAIHINH , "
					+ " \n b.THANHTIEN,'Traphaco' as csNK,  "
					+ " \n isnull( (select TENNHASANXUAT from ERP_MUAHANG  "
					+ " where PK_SEQ in( select PO_NHAPKHAU "
					+ " from ERP_PARK where pK_seq = (select park_fk "
					+ " from ERP_HOADONNCC where PK_SEQ =a.HOADONNCC))),'') as TENNHASANXUAT, "
					+ " '' as NUOCXK, a.NGAYCHUNGTU,d.TEN as CUAKHAU "
					+ " \n from ERP_THUENHAPKHAU a inner join  "
					+ " ERP_THUENHAPKHAU_CHITIET b on a.PK_SEQ = b.THUENHAPKHAU_FK "
					+ " inner join ERP_SANPHAM sp on sp.PK_SEQ = b.SANPHAM_FK "
					+ " \n inner join ERP_HOADONNCC hd on hd.PK_SEQ = a.HOADONNCC "
					+ " left join ERP_NHACUNGCAP c on c.PK_SEQ = a.NCC_FK "
					+ " \n left join DONVIDOLUONG dv on dv.PK_SEQ = b.DVT "
					+ " left join ERP_NHACUNGCAP d on d.PK_SEQ = a.COQUANTHUE_FK "
						  + " where  a.NGAYCHUNGTU >='"+tthdBean.getTuNgay()+"' and a.NGAYCHUNGTU <= '"+tthdBean.getDenNgay()+"'";
			System.out.println("[BC.XuatExcel] query 1 = " + query);
			
			ResultSet rs = db.get(query);
			
			
			
			
			// insert data
			FileInputStream fstream;
			Cell cell = null;
			
			fstream = new FileInputStream(getServletContext().getInitParameter("pathTemplate") + "\\BCNguyenLieuNK.xlsx");
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007);
			workbook.open(fstream);
			
			Worksheets worksheets = workbook.getWorksheets();
			
			//Sheet 1
		    Worksheet worksheet1 = worksheets.getSheet(0);
		    Cells cells1 = worksheet1.getCells();
		    
		    cell = cells1.getCell("I23"); cell.setValue(formatter.format(0)); 
		    
		    // date to date
		    cell = cells1.getCell("C4"); cell.setValue("Từ ngày: "+tthdBean.getTuNgay());
		    cell = cells1.getCell("C5"); cell.setValue("Đến ngày: "+tthdBean.getDenNgay());
		    
	
		    // insert data from db
		    // cell bat dau la 
		    int countRow = 9;
		    int column  =0;
		    if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						ResultSetMetaData rsmd = rs.getMetaData();
				        for (int i = 0; i <= rsmd.getColumnCount(); i++) {
				        	if(i==0){
				        		cell = cells1.getCell(countRow,0);
				        		cell.setValue(i+1+countRow-9);
				        		ReportAPI.setCellBackground(cell, new Color(255,255,255), BorderLineType.THIN, true, 0);
				        		continue;
				        	}
				        	cell = cells1.getCell(countRow,column + i);
				        	
				            int type = rsmd.getColumnType(i);
				            if(type == Types.DOUBLE || type == Types.INTEGER || type == Types.DECIMAL ){
				            	cell.setValue(rs.getDouble(i));
					               // set Float
					            	ReportAPI.setCellBackground(cell, new Color(255,255,255), BorderLineType.THIN, true, 41);
				            } else {
				            	
				            	cell.setValue(rs.getString(i));
					               // set String
					            ReportAPI.setCellBackground(cell, new Color(255,255,255), BorderLineType.THIN, true, 0);
				            }
				       
				        }
				        countRow++;
					}
				} 
				catch (SQLException e) 
				{
					System.out.println("[ErpTTHoaDonPdfSvl.CreateUyNhiemChi] Exception: " + e.getMessage());
				}
			}
		    db.shutDown();
		    
			workbook.save(outstream);
			fstream.close();
		
		}catch (Exception e) 
		{
			e.printStackTrace();
		}
	
	}
}
