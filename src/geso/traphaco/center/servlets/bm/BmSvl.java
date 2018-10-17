package geso.traphaco.center.servlets.bm;


import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.bm.imp.*;
import geso.traphaco.center.beans.bm.*;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.db.sql.dbutils;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class BmSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BmSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		IBmList obj = new BmList();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);

		out.println(userId);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String action = util.getAction(querystring);
		out.println(action);

		String idlist = util.getId(querystring);

		if (action.equals("delete"))
		{
			String bmId = idlist.split(";")[0];
			String vungId = idlist.split(";")[1];

			obj.Delete(bmId, vungId);
		}

		obj.init();

		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/BM.jsp";
		response.sendRedirect(nextJSP);

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		Utility util = new Utility();
		IBmList obj = new BmList();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();

		String querystring = request.getQueryString();

		String userId = util.getUserId(querystring);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String action = util.antiSQLInspection(request.getParameter("action"));
		System.out.println(action);
		String ten = util.antiSQLInspection(request.getParameter("bmTen"));
		if (ten == null)
			ten = "";
		obj.setTen(ten);

		String dienthoai = util.antiSQLInspection(request.getParameter("DienThoai"));
		if (dienthoai == null)
			dienthoai = "";
		obj.setDienthoai(dienthoai);

		String kbhId = util.antiSQLInspection(request.getParameter("kbhId"));
		if (kbhId == null)
			kbhId = "";
		obj.setKbhId(kbhId);

		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		if (dvkdId == null)
			dvkdId = "";
		obj.setDvkdId(dvkdId);

		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);

		String trangthai = util.antiSQLInspection(request.getParameter("TrangThai"));
		if (trangthai == null)
			trangthai = "2";

		obj.setTrangthai(trangthai);
		
		String maFAST = util.antiSQLInspection(request.getParameter("maFAST"));
    	if (maFAST == null)
    		maFAST = "";    	
    	obj.setMaFAST(maFAST);

		userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
			userId = "";

		if (action.equals("new"))
		{
			IBm bmBean = new Bm();
			if (userId.length() == 0)
				userId = request.getParameter("userId");

			bmBean.init_New();

			session.setAttribute("bmBean", bmBean);

			String nextJSP = "/TraphacoHYERP/pages/Center/BMNew.jsp";

			response.sendRedirect(nextJSP);

			return;
		}
		String search=setQuery(obj,request);
		if (action.equals("excel"))
		{
			ToExcel(response, obj, search);
		}
		obj.init();
		String nextJSP = "/TraphacoHYERP/pages/Center/BM.jsp";
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		response.sendRedirect(nextJSP);
	}

	



	public String setQuery(IBmList obj, HttpServletRequest request)
	{
		String query =		
		"SELECT ROW_NUMBER() OVER(ORDER BY bm.pk_seq DESC) AS stt,BM.PK_SEQ  AS BMID,BM.PK_SEQ AS MANHANVIEN, BM.TEN AS BMTEN, BM.DIENTHOAI, BM.TRANGTHAI,  " + 
		"BM.NGAYTAO, NV1.TEN AS NGUOITAO, BM.NGAYSUA, NV2.TEN AS NGUOISUA, VUNG.PK_SEQ AS VUNGID," +
		"VUNG.TEN AS VUNG, DVKD.DONVIKINHDOANH AS DVKD " +
		"FROM BM BM " +
		"INNER JOIN BM_CHINHANH BM_CN ON BM_CN.BM_FK = BM.PK_SEQ " +
		"INNER JOIN VUNG VUNG ON VUNG.PK_SEQ = BM_CN.VUNG_FK " +
		"INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ = BM.DVKD_FK " +
		"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = BM.NGUOITAO " +
		"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = BM.NGUOISUA " +
		"WHERE BM.TRANGTHAI >= '0' ";
		
		System.out.println("Xuất Trình dược viên : " + query);
		return query;
	}
	private void ToExcel(HttpServletResponse response, IBmList obj, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=RSM.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("RSM", k);
			sheet.addCell(new Label(0, 1, "RSM: ", new WritableCellFormat(cellTitle)));

			sheet.addCell(new Label(0, 2, "Ngày tạo: "));
			sheet.addCell(new Label(1, 2, "" + getDateTime()));

			sheet.addCell(new Label(2, 4, "Đơn vị tiền tệ:VND"));

			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.LIME);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			sheet.addCell(new Label(1, 4, "MÃ NHÂN VIÊN", cellFormat));
			sheet.addCell(new Label(2, 4, "TÊN NHÂN VIÊN", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "SỐ ĐT", cellFormat));
			sheet.addCell(new Label(4, 4, "KÊNH  BÁN HÀNG", cellFormat));
			sheet.addCell(new Label(5, 4, "ĐVKD", cellFormat));
			sheet.addCell(new Label(6, 4, "VÙNG MIỀN", cellFormat));
			sheet.addCell(new Label(7, 4, "TRẠNG THÁI", cellFormat));
			sheet.addCell(new Label(8, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(9, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(10, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(11, 4, "NGƯỜI SỬA", cellFormat));

			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 10);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 25);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 15);
			sheet.setColumnView(7, 35);
			sheet.setColumnView(8, 15);
			sheet.setColumnView(9, 15);
			sheet.setColumnView(10, 15);
			sheet.setColumnView(11, 15);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 15);
			sheet.setColumnView(14, 60);
			dbutils db = new dbutils();

			ResultSet rs = db.get(query);

			WritableCellFormat cellFormat2 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(new jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = null;
			Label label;
			Number number;

			while (rs.next())
			{
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, rs.getDouble("STT"), cformat);sheet.addCell(number);
				label = new Label(1, j, rs.getString("MANHANVIEN"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("bmten"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("dienthoai"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("kbh"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getString("dvkd"), cformat); sheet.addCell(label);
				label = new Label(6, j, rs.getString("vung"), cformat); sheet.addCell(label);
				label = new Label(7, j, rs.getInt("trangthai") == 0 ? "Ngưng hoạt động" : "Hoạt động", cformat);sheet.addCell(label);
				label = new Label(8, j, rs.getString("ngaytao"), cformat);sheet.addCell(label);
				label = new Label(9, j, rs.getString("NguoiTao"), cformat);sheet.addCell(label);
				label = new Label(10, j, rs.getString("NgaySua"), cformat);sheet.addCell(label);
				label = new Label(11, j, rs.getString("NguoiSua"), cformat);sheet.addCell(label);

				j++;
			}
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Error Cac Ban : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
