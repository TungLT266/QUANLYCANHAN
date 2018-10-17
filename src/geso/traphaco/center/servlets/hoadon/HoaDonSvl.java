package geso.traphaco.center.servlets.hoadon;

import geso.traphaco.center.beans.hoadon.IHoaDonList;
import geso.traphaco.center.beans.hoadon.imp.HoaDonList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.IOException;
import java.io.OutputStream;

import java.sql.ResultSet;
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


public class HoaDonSvl extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	public HoaDonSvl()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (cutil==null ||!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IHoaDonList obj;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
	
			Utility util = new Utility();
	
			String querystring = request.getQueryString();
			userId = util.getUserId(querystring);
			if (userId.length() == 0)
				userId = util.antiSQLInspection(request.getParameter("userId"));
			obj = new HoaDonList();
			obj.setUserId(userId);
			obj.createNhaphanglist("");
			session.setAttribute("obj", obj);
			String nextJSP = "/TraphacoHYERP/pages/Center/HoaDon.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		String sum = (String) session.getAttribute("sum");
		geso.traphaco.center.util.Utility cutil = (geso.traphaco.center.util.Utility) session.getAttribute("util");
		if (!cutil.check(userId, userTen, sum))
		{
			response.sendRedirect("/TraphacoHYERP/index.jsp");
		} else
		{
			IHoaDonList obj;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			obj = new HoaDonList();

			Utility util = new Utility();
			userId = util.antiSQLInspection(request.getParameter("userId"));
			String action = request.getParameter("action");
			if (action == null)
			{
				action = "";
			}
		
			String search = getSearchQuery(request, obj);
			if (action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				obj.setUserId(userId);
				obj.createNhaphanglist(search);
				System.out.println("[HoaDonList]"+search);
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/HoaDon.jsp";
				response.sendRedirect(nextJSP);
			} 
			else if(action.equals("toExcel"))
			{
				ToExcel(response,obj,search);
			}else 
			{
				obj.createNhaphanglist(search);
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Center/HoaDon.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private String getSearchQuery(HttpServletRequest request, IHoaDonList obj)
	{
		Utility util = new Utility();
		String userId = util.antiSQLInspection(request.getParameter("userId"));
		if (userId == null)
			userId = "";
		obj.setUserId(userId);

		String sohoadon = util.antiSQLInspection(request.getParameter("sohoadon"));
		if (sohoadon == null)
			sohoadon = "";
		obj.setSoHoaDon(sohoadon.trim());

		String tungay = util.antiSQLInspection(request.getParameter("tungay"));
		if (tungay == null)
			tungay = "";
		obj.setTungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("denngay"));
		if (denngay == null)
			denngay = "";
		obj.setDenngay(denngay);

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));
		if (nppId == null)
			nppId = "";
		obj.setNppId(nppId);
		
		
		String loaihd = util.antiSQLInspection(request.getParameter("loaihd"));
		if (loaihd == null)
			loaihd = "";
		obj.setLoaihd(loaihd);

		String trangthai = util.antiSQLInspection(request.getParameter("trangthai"));
		if (trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String ddhId = util.antiSQLInspection(request.getParameter("ddhId"));
		if (ddhId == null)
			ddhId = "";
		obj.setDdhId(ddhId);
	

		String query=
				" select kbh.TEN as KenhBanHang,dvkd.DONVIKINHDOANH,nh.PK_SEQ as NhapHangId,npp.MA as nppMa,npp.TEN as nppTen,nv.TEN as NguoiSua,\n  "+ 
						" 	nh.ngaychungtu,isnull(CAST(  ( select pk_seq from dondathang where soid=nh.soId ) as varchar(10)),'NA') as DatHang_FK,\n  "+ 
						"	nh.NgayNhan,ISNULL(SOCHUNGTUDOI,CHUNGTU) as SoHoaDon,	\n  "+ 
						"	( \n  "+ 
						"		select COUNT(distinct SanPham_FK) as SoSKU  from NHAPHANG_SP where NHAPHANG_FK=nh.PK_SEQ \n  "+ 
						"	)as SoSKU ,nh.SOTIENAVAT,\n  "+ 
						"	case when nh.TRANGTHAI=0 then N'Chưa nhận HĐ' \n  "+ 
						"	when nh.TRANGTHAI=1 and NH.PK_SEQ IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG  WHERE CHUNGTU=nh.CHUNGTU)THEN N'NPP hủy HĐ'\n  "+ 
						"	when nh.TRANGTHAI=1 and NH.PK_SEQ NOT IN(SELECT NHAPHANG_FK FROM HUYNHAPHANG  WHERE CHUNGTU=nh.CHUNGTU)THEN N'Đã nhận HĐ'\n  "+ 
						"	else N'HĐ Hủy' END as TrangThai,\n  "+ 
						"	case when nh.LOAIHOADON=0 then N'Hóa đơn bán'\n  "+ 
						"	when nh.LOAIHOADON=1 then N'Hóa đơn KM'\n  "+ 
						"	WHEN nh.LOAIHOADON=2 then N'Hóa đơn trả'  " +
						"   WHEN nh.LOAIHOADON=3 then N'Hóa đơn TB'            " +
						"   ELSE N'Không xác định'       end as LoaiHD             \n  "+ 
						"  from  nhaphang nh\n  "+ 
						"	inner join NHAPHANPHOI npp on npp.PK_SEQ=nh.NPP_FK \n  "+ 
						"	inner join DONVIKINHDOANH dvkd on dvkd.PK_SEQ=nh.DVKD_FK\n  "+ 
						"	inner join KENHBANHANG kbh on kbh.PK_SEQ=nh.KBH_FK\n  "+ 
						"	inner join 	NHANVIEN nv on nv.PK_SEQ=nh.NGUOISUA " +
		"where 1=1  "; 
		if (sohoadon.length() > 0)
		{
			query = query + " and isnull(nh.sochungtudoi,nh.chungtu) like '%" + sohoadon + "%'  ";
		}
		if (tungay.length() > 0)
		{
			query = query + " and nh.ngaychungtu >= '" + tungay + "'";
		}

		if (denngay.length() > 0)
		{
			query = query + " and nh.ngaychungtu <= '" + denngay + "'";
		}

		if (nppId.length() > 0)
		{
			query = query + " and nh.npp_fk = '" + nppId + "'";
		}
		if (trangthai.length() > 0)
		{
			query = query + " and nh.trangthai = '" + trangthai + "'";

		}
		if (loaihd.length() > 0)
		{
			query = query + " and nh.loaihoadon = '" + loaihd + "'";

		}
		if(ddhId.length()>0)
		{
			query = query + " and nh.soId in (select soId from dondathang where pk_seq = '" + ddhId + "') ";
		}
		return query;
	}
	
	private void ToExcel(HttpServletResponse response, IHoaDonList obj,String query) throws IOException
	{
		OutputStream out = null;
		try
		{
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=HoaDon.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 5;
		
			WritableSheet sheet=null;
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			sheet = w.createSheet("HoaDon", k);
			sheet.addCell(new Label(0, 1, "HÓA ĐƠN: ",new WritableCellFormat(cellTitle)));

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
			
			sheet.addCell(new Label(0, 4, "NGÀY HÓA ĐƠN", cellFormat));
			sheet.addCell(new Label(1, 4, "SỐ HÓA ĐƠN", cellFormatSpecical));
			sheet.addCell(new Label(2, 4, "LOẠI HĐ", cellFormat));
			sheet.addCell(new Label(3, 4, "SỐ ĐƠN ĐẶT HÀNG", cellFormat));
			sheet.addCell(new Label(4, 4, "ĐƠN VỊ KINH DOANH", cellFormat));
			sheet.addCell(new Label(5, 4, "KÊNH BÁN HÀNG", cellFormat));
			sheet.addCell(new Label(6, 4, "MÃ NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(7, 4, "NHÀ PHÂN PHỐI", cellFormat));
			sheet.addCell(new Label(8, 4, "TIỀN HĐ", cellFormat));
			sheet.addCell(new Label(9, 4, "SỐ SKU", cellFormat));
			sheet.addCell(new Label(10, 4, "TRẠNG THÁI", cellFormat));
			
			sheet.setRowView(100, 4);
			
			sheet.setColumnView(0, 15);
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
			query+=" order by  ngaychungtu desc, trangthai asc,nppMa, nhaphangId desc ";
			ResultSet rs = db.get(query);
			
			WritableCellFormat cellFormat2 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cellFormat3 = new WritableCellFormat(new  jxl.write.NumberFormat("#,###,###"));
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat =null;
			Label label;
			Number number;
			
			while (rs.next())
			{
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;
				
				label = new Label(0, j, rs.getString("NgayChungTu"), cformat);sheet.addCell(label);
				label = new Label(1, j, rs.getString("SoHoaDon"), cformat);sheet.addCell(label);
				label = new Label(2, j, rs.getString("LoaiHD"), cformat);sheet.addCell(label);
				label = new Label(3, j, rs.getString("DatHang_fk"), cformat);sheet.addCell(label);
				label = new Label(4, j, rs.getString("DonViKinhDoanh"), cformat);sheet.addCell(label);
				label = new Label(5, j, rs.getString("KenhBanHang"), cformat);sheet.addCell(label);
				label = new Label(6, j, rs.getString("nppMa"), cformat);sheet.addCell(label);
				label = new Label(7, j, rs.getString("nppTen"), cformat);sheet.addCell(label);
				
				number= new Number(8,j , rs.getDouble("soTienAVAT"),cformat);sheet.addCell(number);
				number= new Number(9,j , rs.getDouble("SoSKU"),cformat);sheet.addCell(number);
				label = new Label(10, j, rs.getString("TrangThai"), cformat);sheet.addCell(label);
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
