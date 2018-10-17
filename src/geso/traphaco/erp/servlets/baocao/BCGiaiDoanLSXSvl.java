package geso.traphaco.erp.servlets.baocao;

import geso.dms.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.baocao.IBaocao;
import geso.traphaco.erp.beans.baocao.imp.Baocao;
import geso.traphaco.erp.beans.lenhsanxuat.IBaoCaoTheoDoiLSX;
import geso.traphaco.erp.beans.lenhsanxuat.imp.BaoCaoTheoDoiLSX;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.ss.usermodel.CellStyle;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;
import com.sun.xml.internal.ws.api.streaming.XMLStreamReaderFactory.Woodstox;

/**
 * Servlet implementation class BCGiaiDoanLSXSvl
 */
@WebServlet("/BCGiaiDoanLSXSvl")
public class BCGiaiDoanLSXSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BCGiaiDoanLSXSvl() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IBaocao obj = new Baocao();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		// String ctyId = (String)session.getAttribute("congtyId");

		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);
		obj.createRsBCGDLSX();

		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/BCGiaiDoanLSX.jsp";
		response.sendRedirect(nextJSP);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		OutputStream out = response.getOutputStream();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		
		IBaocao obj = new Baocao();
		Utility util = new Utility();
		// String ctyId = (String)session.getAttribute("congtyId");

	    String userId = request.getParameter("userId");
		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		obj.setUserId(userId);
		
		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTuNgay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenNgay(denngay);

		String khoId = request.getParameter("khoId");
		if (khoId == null)
			khoId = "";
		obj.setKhoIds(khoId);

		String sanpham = request.getParameter("sanpham");
		if (sanpham == null)
			sanpham = "";
		obj.setSanPhamIds(sanpham);

		String action = request.getParameter("action");
		if (action == null) {
			action = "";
		}
		if (action.equals("search")) {
			obj.createRs();

			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Erp/BCGiaiDoanLSX.jsp";
			response.sendRedirect(nextJSP);

		} else {

			response.setContentType("application/xlsm");
			response.setHeader("Content-Disposition",
					"Attachment; filename=BaoCaoGiaiDoanLsx.xlsm");

			try {
				CreatePivotTable(out, response, request, obj);

			} catch (Exception ex) {
				obj.setMsg(ex.getMessage());
				request.getSession().setAttribute("errors", ex.getMessage());

				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/BCGiaiDoanLSX.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private void CreatePivotTable(OutputStream out,
			HttpServletResponse response, HttpServletRequest request,
			IBaocao obj) throws Exception {
		try {
			FileInputStream fstream = null;
			Workbook workbook = new Workbook();

			String strfstream = getServletContext().getInitParameter("path")
					+ "\\BaoCaoGiaiDoanLSX.xlsm";

			fstream = new FileInputStream(strfstream);

			workbook.open(fstream);
			createReport(workbook, obj);

			workbook.save(out);

			fstream.close();

			/*
			 * workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			 * Worksheets worksheets = workbook.getWorksheets(); Worksheet
			 * worksheet_2 = worksheets.getSheet("sheet1");
			 * worksheet_2.setName("REPORT_LENHSANXUAT"); Cells cells =
			 * worksheet_2.getCells();
			 * 
			 * Cell cell = cells.getCell("P1"); Style style1=cell.getStyle();
			 * 
			 * dbutils db = new dbutils();
			 * 
			 * String[] param = new String[2];
			 * 
			 * param[0] = obj.gettungay().equals("") ? null : obj.gettungay();
			 * param[1] = obj.getdenngay().equals("") ? null : obj.getdenngay();
			 * 
			 * ResultSet rs = db.getRsByPro("GET_REPORT_LENHSANXUAT", param);
			 * worksheets = workbook.getWorksheets();
			 * 
			 * this.TaoBaoCao(db,rs,worksheet_2,
			 * obj,"REPORT_LENHSANXUAT",style1);
			 * 
			 * 
			 * workbook.save(out);
			 */
			fstream.close();
		} catch (Exception ex) {
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}

	private void createReport(Workbook workbook, IBaocao obj) {

		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		worksheet.setName("Sheet1");

		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();

		String queryGC = " select ck.ngaychuyen, sp.MA as MASP, sp.TEN as TENSP,dvth.TEN as tennhamay, mh.GHICHUGC as solo, mh.sopo as solsx, \n"
				+ "(  isnull(kho.TEN,'')  ) as kho  \n"
				+ " from ERP_MUAHANG mh \n"
				+ " left join ERP_CHUYENKHO ck on ck.MUAHANG_FK = mh.PK_SEQ \n"
				+ " inner join ERP_MUAHANG_SP mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK \n"
				+ " inner join ERP_DONVITHUCHIEN dvth on mh.DONVITHUCHIEN_FK = dvth.PK_SEQ \n"
				+ " inner join ERP_SANPHAM sp on mhsp.SANPHAM_FK = sp.PK_SEQ \n" 
				+ " inner join erp_khott kho on kho.PK_SEQ = ck.KhoXuat_FK  and kho.TEN not like N'%sản xuất%' \n"
				+ " where ck.TRANGTHAI in(1,2,3) and mh.ISGIACONG = 1 \n"
				+ " and  ck.NgayChuyen >= '"
				+ obj.getTuNgay()
				+ "' and  ck.NgayChuyen <=  '" + obj.getDenNgay() + "' \n";
		if (obj.getKhoIds().length() > 0) {
			queryGC += " and ck.khoxuat_fk = " + obj.getKhoIds();
		}
		if (obj.getSanPhamIds().length() > 0) {
			queryGC += " and sp.pk_seq = " + obj.getSanPhamIds();
		}

		String queryLSX = " select ck.ngaychuyen, sp.MA as MASP, sp.TEN as TENSP, nm.tennhamay, "
				+ "lsx.diengiai as soLo ,  CAST( lsx.PK_SEQ as varchar ) as soLSX,   \n"
				+ "(  isnull(kho.TEN,'')  ) as kho  \n"
				+ " from ERP_LENHSANXUAT_GIAY lsx "
				+ " left join  ERP_CHUYENKHO ck on ck.LENHSANXUAT_FK = lsx.PK_SEQ \n"
				+ " inner join ERP_LENHSANXUAT_SANPHAM lsxsp on lsx.PK_SEQ = lsxsp.LENHSANXUAT_FK \n"
				+ " inner join ERP_NHAMAY nm on lsx.NHAMAY_FK = nm.pk_seq \n"
				+ " inner join ERP_SANPHAM sp on lsxsp.sanpham_fk = sp.PK_SEQ \n"
				+ " inner join erp_khott kho on kho.PK_SEQ = ck.KhoXuat_FK  and kho.TEN not like N'%sản xuất%' \n"
				+ " where ck.TRANGTHAI in(1,2,3) and ck.LENHSANXUAT_FK is not null \n "
				+ " and  ck.NgayChuyen >= '"
				+ obj.getTuNgay()
				+ "' and  ck.NgayChuyen <=  '" + obj.getDenNgay() + "' \n";
		if (obj.getKhoIds().length() > 0) {
			queryLSX += " and ck.khoxuat_fk = " + obj.getKhoIds();
		}
		if (obj.getSanPhamIds().length() > 0) {
			queryLSX += " and sp.pk_seq = " + obj.getSanPhamIds();
		}

		String query = " SELECT * INTO #giaidoanlsx_temp FROM (" + queryLSX
				+ "\n UNION ALL \n " + queryGC + " ) a \n";

		query += "select result.NgayChuyen, result.MASP, result.TENSP, result.tennhamay, \n"
				+ " STUFF( (select DISTINCT  ','+r1.soLo from  \n"
				+ "#giaidoanlsx_temp r1 where r1.NgayChuyen = result.NgayChuyen and r1.MASP = result.MASP \n"
				+ " FOR XML PATH ('') ) , 1, 1, '') as solo, \n"
				+ " STUFF( (select DISTINCT  ','+cast(r1.soLSX as nvarchar) from  \n"
				+ "#giaidoanlsx_temp r1 where r1.NgayChuyen = result.NgayChuyen and r1.MASP = result.MASP \n"
				+ " FOR XML PATH ('') ) , 1, 1, '') as soLSX, \n"
				+ " STUFF( (select DISTINCT  ', '+r1.kho from  \n"
				+ "#giaidoanlsx_temp r1 where r1.NgayChuyen = result.NgayChuyen and r1.MASP = result.MASP \n"
				+ " FOR XML PATH ('') ) , 1, 1, '') as kho \n"
				+ " from  #giaidoanlsx_temp  result \n"
				+ " group by result.NgayChuyen, result.MASP, result.TENSP, result.tennhamay "
				+ " order by NgayChuyen";

		System.out.println(":::: INFO : " + query);
		ResultSet rsInfo = db.get(query);

		Style style;
		Font font = new Font();
		font.setName("Times New Roman");
		Cell cell;

		cell = cells.getCell("C3");
		String tieude = "Từ ngày " + obj.getTuNgay() + " Đến ngày "
				+ obj.getDenNgay();
		ReportAPI.getCellStyle(cell, Color.BLUE, false, 12, tieude);

		int index = 8;
		int sum = 0;
		

		

		try {
			if (rsInfo != null) {
				while (rsInfo.next()) {
					String ngaychuyen = rsInfo.getString("ngaychuyen");
					String tennhamay = rsInfo.getString("tennhamay");
					String masp = rsInfo.getString("MASP");
					String tensp = rsInfo.getString("TENSP");
					String solo = rsInfo.getString("solo");
					String solsx = rsInfo.getString("soLsx");
					String kho = rsInfo.getString("kho");
					cell = cells.getCell("A" + Integer.toString(index));
					cell.setValue(ngaychuyen);

					cell = cells.getCell("B" + Integer.toString(index));
					cell.setValue(masp);

					cell = cells.getCell("C" + Integer.toString(index));
					cell.setValue(tensp);

					cell = cells.getCell("D" + Integer.toString(index));
					cell.setValue(tennhamay);

					cell = cells.getCell("E" + Integer.toString(index));
					cell.setValue(formatSoLo(solsx));

					cell = cells.getCell("F" + Integer.toString(index));
					cell.setValue(formatSoLo(solo));

					cell = cells.getCell("G" + Integer.toString(index));
					cell.setValue(formatTenKho(kho));
					style = cell.getStyle();
					style.setTextWrapped(true);
					cell.setStyle(style);

					index++;
				}
				rsInfo.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}
	private String formatSoLo(String soLos){
		String result = "";
		System.out.println(soLos);
		if(soLos.length() ==0 ){
			return soLos;
		}
		if( soLos.contains(",") == false){
			return soLos;
		}
		String[] a = soLos.split(",");
		if(a.length <= 2){
			return soLos;
		}
		
		return a[a.length -2 ]+ ", " + a[a.length -1];
	}
	private String formatTenKho(String tenkhos){
		String result = "";
		if(tenkhos.length() ==0 ){
			return tenkhos;
		}
		if( tenkhos.contains(",") == false){
			return tenkhos.trim();
		}
		String[] a = tenkhos.split(",");
		
		for(int i = 0; i < a.length; i++){
			if(i == a.length-1){
				result += a[i].trim();
			}else{
				result += a[i].trim()+ "\n ";
			}
		}
		return result;
	}

}
