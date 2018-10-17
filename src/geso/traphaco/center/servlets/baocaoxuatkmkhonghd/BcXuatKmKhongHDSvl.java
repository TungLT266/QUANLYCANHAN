package geso.traphaco.center.servlets.baocaoxuatkmkhonghd;

import geso.traphaco.center.beans.hoadonphelieu.IErpXuatkmkhonghd;
import geso.traphaco.center.beans.hoadonphelieu.IErpXuatkmkhonghdList;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpXuatkmkhonghd;
import geso.traphaco.center.beans.hoadonphelieu.imp.ErpXuatkmkhonghdList;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

/**
 * Servlet implementation class BcXuatKmKhongHDSvl
 */
public class BcXuatKmKhongHDSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	PrintWriter out;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BcXuatKmKhongHDSvl() {
        super();
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    this.out  = response.getWriter();
	    
	    HttpSession session = request.getSession();	
	    
	    Utility util = new Utility();
	    out = response.getWriter();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    out.println(userId);
	    
	    if (userId.length() == 0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    IErpXuatkmkhonghdList obj = new ErpXuatkmkhonghdList();
	    String ctyId = (String)session.getAttribute("congtyId");
	    obj.setUserId(userId);
	    obj.setCongtyId(ctyId);
	    
//	    String action = util.getAction(querystring);
//	    String khlId = util.getId(querystring);
	    String msg = "";
	    
//	    System.out.println(":: ACTION: " + action);
//	    if(action.trim().equals("delete"))
//	    {
//	    	dbutils db = new dbutils();
//	    	if(!db.update("update ERP_XUATKMKHONGHD set trangthai = '2' where pk_seq = '" + khlId + "'"))
//	    	{
//	    		msg = "Không thể xóa ERP_XUATKMKHONGHD";
//	    	}
//	    	db.shutDown();
//	    }
//	    else if(action.trim().equals("chot"))
//	    {
//	    	msg = ChotHoaDon(khlId, userId);
//	    	System.out.println(":: KQ CHOT: " + msg);
//	    }
	    
	    obj.initBC("");
	    obj.setMsg(msg); 
		session.setAttribute("obj", obj);
	    
	    String nextJSP = "/TraphacoHYERP/pages/Center/BcXuatKmKhongHD.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    OutputStream outS = response.getOutputStream();
	    
	    HttpSession session = request.getSession();	

	    Utility util = new Utility();
	    
	    String userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    
	    IErpXuatkmkhonghdList obj;
	    
		String action = request.getParameter("action");
	    if (action == null) {
	    	action = "";
	    }
	    
    	if(action.equals("view") || action.equals("next") || action.equals("prev")) {
    		obj = new ErpXuatkmkhonghdList();
		    obj.setUserId(userId);

		    session.setAttribute("nppId", "");
		    
    		obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

    		String search = getSearchQuery(request, obj);
	    	
	    	obj.initBC(search);

	    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
	    	
	    	session.setAttribute("obj", obj);
	    	
	    	response.sendRedirect("/TraphacoHYERP/pages/Center/BcXuatKmKhongHD.jsp");	
	    } else {
	    	
	    	if (action.equals("excel")) {
	    		obj = new ErpXuatkmkhonghdList();
			    obj.setUserId(userId);
			    String search = getSearchQuery(request, obj);
			    
	    		response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoXuatKMKhongHD.xlsx");
				
				FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BaoCaoXuatKMKhongHD.xlsx");
				Workbook workbook = new Workbook();
				workbook.open(fstream);
				workbook.setFileFormatType(FileFormatType.EXCEL2007);

				CreateStaticHeader(workbook, obj);
				CreateStaticData(workbook, obj, search);
				
				workbook.save(outS);
				fstream.close();
				return;
	    	} else {
	    		obj = new ErpXuatkmkhonghdList();
			    obj.setUserId(userId);
		
		    	String search = getSearchQuery(request, obj);
		    	
		    	obj.setUserId(userId);
		    	session.setAttribute("nppId", "");
		    	
		    	obj.initBC(search);
					
		    	session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);
			
				response.sendRedirect("/TraphacoHYERP/pages/Center/BcXuatKmKhongHD.jsp");
	    	}
    	}
	}
	
	private void CreateStaticData(Workbook workbook, IErpXuatkmkhonghdList obj, String search) {
		dbutils db = new dbutils();
		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    
	    Cells cells = worksheet.getCells();
		   
	    Cell cell;
	    Style style;
		
	    ResultSet rs = db.get(search);
	    
	    if (rs != null) {
	    	try {
	    		int count = 1;
	    		int row = 4;
				while (rs.next()) {
					cell = cells.getCell("A" + Integer.toString(row)); cell.setValue(count); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("B" + Integer.toString(row)); cell.setValue(rs.getString("Donvi")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("C" + Integer.toString(row)); cell.setValue(rs.getString("Madonhang")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("D" + Integer.toString(row)); cell.setValue(rs.getString("Ngayxuat")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("E" + Integer.toString(row)); cell.setValue(rs.getString("Mavattu")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("F" + Integer.toString(row)); cell.setValue(rs.getString("Tenvattu")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("G" + Integer.toString(row)); cell.setValue(rs.getString("SOLO")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("H" + Integer.toString(row)); cell.setValue(rs.getString("NGAYHETHAN")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("I" + Integer.toString(row)); cell.setValue(rs.getString("soluong")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("J" + Integer.toString(row)); cell.setValue(rs.getString("DVT")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("K" + Integer.toString(row)); cell.setValue(""); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					cell = cells.getCell("L" + Integer.toString(row)); cell.setValue(rs.getString("mavuviec")); setCellBorderStyle(cell, HorizontalAlignmentType.LEFT);
					
					count++;
					row++;
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
	    }
	}

	private void CreateStaticHeader(Workbook workbook, IErpXuatkmkhonghdList obj) {		
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    
	    Cells cells = worksheet.getCells();
		   
	    Cell cell ;  
	  
	    Font font2 = new Font();
	    font2.setBold(true);
	    
	    cell = cells.getCell("A2");
	    cell.setValue("Từ ngày: " + obj.getTungay() + " đến ngày: " + obj.getDenngay());
	}

	public static String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpXuatkmkhonghdList obj) {
		Utility util = new Utility();
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		String txtTungay = request.getParameter("txtTungay");
		if (txtTungay == null) {
			txtTungay = "";
		}
		obj.setTungay(txtTungay);
		
		String txtDenngay = request.getParameter("txtDenngay");
		if (txtDenngay == null) {
			txtDenngay = "";
		}
		obj.setDenngay(txtDenngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String khachhang = request.getParameter("khachhang");
		if(khachhang == null)
			khachhang = "";
		obj.setKhachhang(khachhang);
		
		String sql =  "SELECT npp.TEN AS Donvi, km.pk_seq AS Madonhang, km.ngayghinhan AS Ngayxuat, sp.MA AS Mavattu, sp.TEN AS Tenvattu, SUM(kmsp.soluong) AS soluong, kmspct.SOLO, kmspct.NGAYHETHAN, dvdl.DONVI AS DVT, km.mavuviec " +
						"FROM ERP_XUATKMKHONGHD km  " +
						"	INNER JOIN NHAPHANPHOI npp ON km.doituongId = npp.PK_SEQ " + 
						"	INNER JOIN ERP_XUATKMKHONGHD_SANPHAM kmsp ON km.pk_seq = kmsp.xuatkm_fk " +
						"	INNER JOIN ERP_SANPHAM sp ON sp.PK_SEQ = kmsp.sanpham_fk " +
						"	INNER JOIN ERP_XUATKMKHONGHD_SANPHAM_CHITIET kmspct ON km.pk_seq = kmspct.xuatkm_fk AND kmspct.sanpham_fk = sp.PK_SEQ " +
						"	INNER JOIN DONVIDOLUONG dvdl ON dvdl.PK_SEQ = sp.DVDL_FK  " +
						"WHERE 1 = 1 ";
		
		if (!txtTungay.equals("")) {
			sql += "AND km.ngayghinhan >= '" + txtTungay + "' ";
		}
		
		if (!txtDenngay.equals("")) {
			sql += "AND km.ngayghinhan <= '" + txtDenngay + "' ";
		}
		
		if (!trangthai.equals("")) {
			sql += "AND km.trangthai = " + trangthai + " ";
		}
		
		if (!khachhang.equals("")) {
			sql += "AND npp.PK_SEQ = " + khachhang + " ";
		}
		
		sql += "GROUP BY npp.TEN, km.pk_seq, km.ngayghinhan, sp.MA, sp.TEN, kmspct.SOLO, kmspct.NGAYHETHAN, dvdl.DONVI, km.mavuviec ";
		
		return sql;
	}
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);
		
		cell.setStyle(style);
	}
	
	private void setCellBorderStyle_TB(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		style.setHAlignment(alignment);
		style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		
		cell.setStyle(style);
	}

}
