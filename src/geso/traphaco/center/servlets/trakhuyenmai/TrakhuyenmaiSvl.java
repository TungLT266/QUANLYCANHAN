package geso.traphaco.center.servlets.trakhuyenmai;

import geso.traphaco.center.beans.trakhuyenmai.*;
import geso.traphaco.center.beans.trakhuyenmai.imp.*;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

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

public class TrakhuyenmaiSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	PrintWriter out;

	public TrakhuyenmaiSvl()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		this.out = response.getWriter();

		HttpSession session = request.getSession();
		ITrakhuyenmaiList obj;
		Utility util = new Utility();
		out = response.getWriter();

		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		out.println(userId);

		if (userId.length() == 0)
			userId = request.getParameter("userId");

		String action = util.getAction(querystring);
		out.println(action);

		String trakmId = util.getId(querystring);

		obj = new TrakhuyenmaiList();
		obj.setRequestObj(request);

		if (action.equals("delete"))
		{
			String msg = Delete(trakmId);
			if (msg.length() > 0)
			{
				obj.setmsg(msg);
			}
		}
		obj.setUserId(userId);
		obj.init("");
		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Center/TraKhuyenMai.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		HttpSession session = request.getSession();
		String userId = request.getParameter("userId");

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}
		ITrakhuyenmaiList obj;
		obj = new TrakhuyenmaiList();
		obj.setRequestObj(request);

		String search = getSearchQuery(request, obj);
		if (action.equals("Tao moi"))
		{
			ITrakhuyenmai trakmBean = (ITrakhuyenmai) new Trakhuyenmai("");
			trakmBean.setUserId(userId);
			trakmBean.createRS();
			session.setAttribute("trakmBean", trakmBean);
			String nextJSP = "/TraphacoHYERP/pages/Center/TraKhuyenMaiNew.jsp";
			response.sendRedirect(nextJSP);

		} else if (action.equals("view") || action.equals("next") || action.equals("prev"))
		{
			obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
			obj.init("");
			obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			response.sendRedirect("/TraphacoHYERP/pages/Center/TraKhuyenMai.jsp");
		} else if (action.equals("toExcel"))
		{
			ToExcel(response, obj, search);
		} else
		{
			obj.init(search);
			obj.setUserId(userId);
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			response.sendRedirect("/TraphacoHYERP/pages/Center/TraKhuyenMai.jsp");
		}
	}

	private String getSearchQuery(HttpServletRequest request, ITrakhuyenmaiList obj)
	{
		String diengiai = request.getParameter("diengiai");
		if (diengiai == null)
			diengiai = "";
		obj.setDiengiai(diengiai);
		
		String maTrakm = request.getParameter("matrakm");
		if (maTrakm == null)
			maTrakm = "";
		obj.setMatrakm(maTrakm);
		
		String maSp = request.getParameter("masp");
		if (maSp == null)
			maSp = "";
		obj.setMasp(maSp);
		
		String tenSp = request.getParameter("tensp");
		if (tenSp == null)
			tenSp = "";
		obj.setTensp(tenSp);

		String tungay = request.getParameter("tungay");
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = request.getParameter("denngay");
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS stt, a.PK_SEQ as trakmId, a.diengiai, isnull(a.TONGLUONG, 0) as tongluong, isnull(a.TONGTIEN, 0) as tongtien, isnull(a.CHIETKHAU, 0) as chietkhau, a.loai, a.hinhthuc, a.ngaytao, a.ngaysua, b.TEN as nguoitao, c.TEN as nguoisua  ";
		query = query + " from TRAKHUYENMAI a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ  where a.pk_seq > 0";

		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();

		if (diengiai.length() > 0)
		{
			query = query + " and upper(dbo.ftBoDau(a.diengiai)) like upper('%" + util.replaceAEIOU(diengiai) + "%') or upper(a.pk_seq) like upper('%" + diengiai + "%')";
		}
		if (maTrakm.length()>0)
		{
			query+=" and a.PK_SEQ LIKE '%"+ maTrakm +"%'"; 
			
		}
		if (maSp.length()>0)
		{
			query+=" and a.PK_SEQ In (SELECT a.PK_SEQ FROM (TRAKHUYENMAI a inner join TRAKHUYENMAI_SANPHAM as b on a.PK_SEQ = b.TRAKHUYENMAI_FK) inner join SANPHAM as c on b.SANPHAM_FK = c.PK_SEQ "
					+ "WHERE c.MA  LIKE '%"+maSp+"%')";
		}
		if (tenSp.length()>0)
		{
			query+=  " and a.PK_SEQ In (SELECT a.PK_SEQ FROM (TRAKHUYENMAI a inner join TRAKHUYENMAI_SANPHAM as b on a.PK_SEQ = b.TRAKHUYENMAI_FK)	"
					+ "inner join SANPHAM sp on b.SANPHAM_FK = sp.PK_SEQ WHERE sp.TEN LIKE N'%"+tenSp+"%')";
		}
		if (tungay.length() > 0)
		{
			query = query + " and a.ngaytao >= '" + convertDate(tungay) + " '";
		}

		if (denngay.length() > 0)
		{
			query = query + " and a.ngaytao <= '" + convertDate(denngay) + " '";
		}

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

	private String Delete(String trakmId)
	{
		dbutils db = new dbutils();
		ResultSet rs = db.get("select count(*) as num from ctkm_trakm where trakhuyenmai_fk='" + trakmId + "'");
		try
		{
			rs.next();
			if (!rs.getString("num").equals("0"))
			{
				rs.close();
				return "Tra khuyen mai nay da duoc su dung trong khai bao ct khuyen mai";
			}
			rs.close();

			ResultSet rs1 = db.get("select count(*) as num from donhang_ctkm_trakm where trakmId='" + trakmId + "'");
			if (rs1 != null)
			{
				rs1.next();
				if (!rs1.getString("num").equals("0"))
				{
					rs1.close();
					return "Tra khuyen mai nay da duoc su dung trong don hang ap khuyen mai";
				}
			}

			db.update("delete from trakhuyenmai_sanpham where trakhuyenmai_fk='" + trakmId + "'");
			db.update("delete from ctkm_trakm where trakhuyenmai_fk='" + trakmId + "'");
			db.update("delete from donhang_ctkm_trakm where trakhuyenmai_fk='" + trakmId + "'");
			// Xoa Bang Cha
			db.update("delete from trakhuyenmai where pk_seq = '" + trakmId + "'");
			db.update("commit");
			return "";
		
		} catch (SQLException e)
		{
			return "Khong the xoa tra khuyen mai nay";
		}
		finally
		{
			db.shutDown();
		}

	}

	private void ToExcel(HttpServletResponse response, ITrakhuyenmaiList obj, String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=TraKhuyenMai.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);

			sheet = w.createSheet("TraKM", k);
			sheet.addCell(new Label(0, 1, "TRẢ KHUYẾN MÃI: ", new WritableCellFormat(cellTitle)));

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
			sheet.addCell(new Label(1, 4, "MÃ TRẢ KM", cellFormat));
			sheet.addCell(new Label(2, 4, "DIỄN GIẢI", cellFormatSpecical));
			sheet.addCell(new Label(3, 4, "TỔNG LƯỢNG", cellFormat));
			sheet.addCell(new Label(4, 4, "TỔNG TIỀN", cellFormat));
			sheet.addCell(new Label(5, 4, "CHIẾT KHẤU", cellFormat));
			sheet.addCell(new Label(6, 4, "NGÀY TẠO", cellFormat));
			sheet.addCell(new Label(7, 4, "NGƯỜI TẠO", cellFormat));
			sheet.addCell(new Label(8, 4, "NGÀY SỬA", cellFormat));
			sheet.addCell(new Label(9, 4, "NGƯỜI SỬA", cellFormat));

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

				number = new Number(0, j, rs.getDouble("STT"), cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("trakmId"), cformat);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("DienGiai"), cformat);
				sheet.addCell(label);
				number = new Number(3, j, rs.getDouble("TongLuong"), cformat);
				sheet.addCell(number);
				number = new Number(4, j, rs.getDouble("TongTien"), cformat);
				sheet.addCell(number);
				number = new Number(5, j, rs.getDouble("ChietKhau"), cformat);
				sheet.addCell(number);
				label = new Label(6, j, rs.getString("NgayTao"), cformat);
				sheet.addCell(label);
				label = new Label(7, j, rs.getString("NguoiTao"), cformat);
				sheet.addCell(label);
				label = new Label(8, j, rs.getString("NgaySua"), cformat);
				sheet.addCell(label);
				label = new Label(9, j, rs.getString("NguoiSua"), cformat);
				sheet.addCell(label);

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
