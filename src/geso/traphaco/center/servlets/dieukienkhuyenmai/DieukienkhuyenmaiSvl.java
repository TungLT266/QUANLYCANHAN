package geso.traphaco.center.servlets.dieukienkhuyenmai;

import geso.traphaco.center.beans.dieukienkhuyenmai.IDieukienkhuyenmai;
import geso.traphaco.center.beans.dieukienkhuyenmai.IDkkhuyenmaiList;
import geso.traphaco.center.beans.dieukienkhuyenmai.imp.Dieukienkhuyenmai;
import geso.traphaco.center.beans.dieukienkhuyenmai.imp.DkkhuyenmaiList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;
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

public class DieukienkhuyenmaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public DieukienkhuyenmaiSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDkkhuyenmaiList obj;
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();

		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = util.getAction(querystring);
		out.println(action);

		String dkkmId = util.getId(querystring);

		obj = new DkkhuyenmaiList();

		obj.setRequestObj(request);
		if (action.equals("delete"))
		{
			String msg = Delete(dkkmId);
			if (msg.length() > 0)
				obj.setmsg(msg);
		}

		obj.setUserId(userId);
		obj.init("");

		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/DieuKienKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		IDkkhuyenmaiList obj = new DkkhuyenmaiList();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();

		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId"));

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		

		String search = getSearchQuery(request, obj);

		obj.setRequestObj(request);
		if (action.equals("Tao moi"))
		{
			IDieukienkhuyenmai dkkmBean = (IDieukienkhuyenmai) new Dieukienkhuyenmai("");
			dkkmBean.setUserId(userId);
			dkkmBean.createRS();
			session.setAttribute("dkkmBean", dkkmBean);

			String nextJSP = "/TraphacoHYERP/pages/Center/DieuKienKhuyenMaiNew.jsp";
			response.sendRedirect(nextJSP);

		}

		else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.setUserId(userId);

			obj.init(search);
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			session.setAttribute("obj", obj);
			response.sendRedirect("/TraphacoHYERP/pages/Center/DieuKienKhuyenMai.jsp");
		} else if (action.equals("toExcel"))
		{
			ToExcel(response, obj, search);
		} else
		{
			// obj = new DaidienkinhdoanhList(search);
			obj.init(search);
			obj.setUserId(userId);

			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			session.setAttribute("abc", search);

			response.sendRedirect("/TraphacoHYERP/pages/Center/DieuKienKhuyenMai.jsp");

		}
	}

	private String getSearchQuery(HttpServletRequest request, IDkkhuyenmaiList obj)
	{
		Utility util = new Utility();

		String diengiai = util.antiSQLInspection(request.getParameter("diengiai"));
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String dieukienId = util.antiSQLInspection(request.getParameter("dieukienId"));
		if(dieukienId==null)
			dieukienId="";
		obj.setDieukienId(dieukienId);
		
		String maSp = util.antiSQLInspection(request.getParameter("masp"));
		if(maSp==null)
			maSp="";
		obj.setMasp(maSp);
		
		String tenSp = util.antiSQLInspection(request.getParameter("tensp"));
		if(tenSp==null)
			tenSp="";
		obj.setTensp(tenSp);

		String query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS stt, a.PK_SEQ as dkkmId, a.DIENGIAI, ISNULL(a.TONGLUONG, 0) as tongluong, ISNULL(a.TONGTIEN, 0) as tongtien, a.LOAI, convert(char(10), a.NGAYTAO, 103) as ngaytao, convert(char(10), a.NGAYSUA, 103) as ngaysua, b.TEN as nguoitao, c.TEN as nguoisua ";
		query = query + " from DIEUKIENKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ where a.pk_seq > 0";
	
		if (diengiai.length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper(N'%" + util.replaceAEIOU(diengiai) + "%')";
		}
		if (dieukienId.length()>0)
		{
			query+=" and a.PK_SEQ LIKE '%"+ dieukienId +"%'"; 
		}
		if (maSp.length()>0)
		{
			query+=" and a.PK_SEQ In (SELECT a.PK_SEQ FROM (DIEUKIENKHUYENMAI a inner join DIEUKIENKM_SANPHAM as b on a.PK_SEQ = b.DIEUKIENKHUYENMAI_FK)"
					+ " inner join ERP_SANPHAM as c on b.SANPHAM_FK = c.PK_SEQ WHERE c.MA  LIKE '%"+maSp+"%')";
		}
		if (tenSp.length()>0)
		{
			query+= " and a.PK_SEQ In (SELECT a.PK_SEQ FROM (DIEUKIENKHUYENMAI a inner join DIEUKIENKM_SANPHAM as b on a.PK_SEQ = b.DIEUKIENKHUYENMAI_FK)"
					+ " inner join ERP_SANPHAM sp on b.SANPHAM_FK = sp.PK_SEQ WHERE sp.TEN LIKE N'%"+tenSp+"%')";
		}
		if (tungay.length() > 0)
		{
			query = query + " and a.ngaytao >= '" + convertDate(tungay) + " '";
		}

		if (denngay.length() > 0)
		{
			query = query + " and a.ngaytao <= '" + convertDate(denngay) + " '";
		}
		System.out.println(query);
		return query;
	}

	private String convertDate(String date)
	{
		// chuyen dinh dang dd-MM-yyyy sang dinh dang yyyy-MM-dd
		if (!date.contains("-"))
			return "";
		String[] arr = date.split("-");
		if (arr[0].length() < arr[2].length())
			return arr[2] + "-" + arr[1] + "-" + arr[0];
		return date;
	}

	private String Delete(String dkkmId)
	{
		dbutils db = new dbutils();

		String sql = "select count(*) as num from ctkm_dkkm where dkkhuyenmai_fk ='" + dkkmId + "'";
		ResultSet rs = db.get(sql);

		try
		{
			rs.next();
			if (!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Dieu kien khuyen mai nay da duoc su dung trong khai bao ct khuyen mai";
			}
			rs.close();

			db.update("delete from dieukienkm_sanpham where dieukienkhuyenmai_fk='" + dkkmId + "'");
			db.update("delete from ctkm_dkkm where dkkhuyenmai_fk='" + dkkmId + "'");
			// Xoa Bang Cha
			db.update("delete from dieukienkhuyenmai where pk_seq = '" + dkkmId + "'");
			db.update("commit");
			return "";

		} catch (Exception e)
		{
			return "Khong the xoa tra khuyen mai nay";
		}
	}

	private void ToExcel(HttpServletResponse response, IDkkhuyenmaiList obj, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=DieuKienKhuyenMai.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("DieuKienKM", k);
			sheet.addCell(new Label(0, 1, "ĐIỀU KIỆN KHUYẾN MÃI: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "MÃ ĐIỀU KIỆN", cellFormat));
			sheet.addCell(new Label(2, 4, "DIỄN GIẢI", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "TỔNG LƯỢNG", cellFormat));
			sheet.addCell(new Label(4, 4, "TỔNG TIỀN", cellFormat));
			sheet.addCell(new Label(5, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(6, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(7, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(8, 4, "NGƯỜI SỬA", cellFormat));

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
			//query += " order by  ngaychungtu desc, trangthai asc,nppMa, nhaphangId desc ";
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
				label = new Label(1, j, rs.getString("dkkmId"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("DienGiai"), cformat);sheet.addCell(label);
				number = new Number(3, j, rs.getDouble("TongLuong"), cformat);sheet.addCell(number);
				number = new Number(4, j, rs.getDouble("TongTien"), cformat);sheet.addCell(number);
				label = new Label(5, j, rs.getString("NgayTao"), cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("NguoiTao"), cformat);sheet.addCell(label);
				label = new Label(7, j, rs.getString("NgaySua"), cformat);sheet.addCell(label);
				label = new Label(8, j, rs.getString("NguoiSua"), cformat);sheet.addCell(label);
				
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


