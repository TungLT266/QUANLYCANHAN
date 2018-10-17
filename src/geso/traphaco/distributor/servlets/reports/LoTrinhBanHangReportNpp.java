package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.distributor.beans.lotrinh.*;
import geso.traphaco.distributor.beans.lotrinh.imp.*;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.CodingErrorAction;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.write.Label;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class LoTrinhBanHangReportNpp extends HttpServlet
{

	private static final long serialVersionUID = 1L;
	Utility util = new Utility();
	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	public LoTrinhBanHangReportNpp()
	{
		super();
	}	

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
		session.setAttribute("denngay", "");
		session.setAttribute("loi", "");
		session.setAttribute("userTen", userTen);

		ILoTrinh obj = new LoTrinh();
		obj.setUserId(userId);
		obj.setStatus("1");
		obj.init();

		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/LoTrinhBanHangReport.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		// PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		ILoTrinh obj = new LoTrinh();
		
		String nnId = (String)session.getAttribute("nnId");

		String nppId = util.antiSQLInspection(request.getParameter("nppId"));

		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		obj.setKenhId(kenhId);

		if (nppId == null)
			nppId = "";
		obj.setnppId(nppId);

		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setddkdId(ddkdId);

		String tuyenId = util.antiSQLInspection(request.getParameter("tuyenId"));
		if (tuyenId == null)
			tuyenId = "";
		obj.settuyenId(tuyenId);
		
		 String userId = util.antiSQLInspection(request.getParameter("userId")==null?"":request.getParameter("userId"));    
		 obj.setUserId(userId);
		
		String tungay = request.getParameter("tungay");
    	if(tungay == null) tungay = ""; else tungay = tungay.trim();
    	obj.setTungay(tungay);
    	
    	String denngay = request.getParameter("denngay");
    	if(denngay == null || denngay.trim().length() <= 0) 
    		denngay = getDateTime();
    	else 
    		denngay = denngay.trim();
    	obj.setDenngay(denngay);

    	/*String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
    	
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
    	
		if (vungId == null)
			vungId = "";
		obj.setVungId(vungId);
		
		
		String tinhthanhId = util.antiSQLInspection(request.getParameter("tinhthanhId"));
    	
		if (tinhthanhId == null)
			tinhthanhId = "";
		obj.setTinhthanhId(tinhthanhId);
		
		
		*/
		
		session.setAttribute("loi", "");
		
		obj.init();
		String action = request.getParameter("action");

		if (action.equals("exportmcp"))
		{
			System.out.println("XuatMcp__");
			if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0){
				session.setAttribute("loi", "Bạn phải chọn ngày xem báo cáo"); 
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/LoTrinhBanHangReport.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				XuatFileExcelSR(response, obj, nnId);
			}
		}  
		else
		{
			if(action.equals("chitiet"))
			{
				System.out.println("XuatDetail__");
				if(tungay.trim().length() <= 0 || denngay.trim().length() <= 0){
					session.setAttribute("loi", "Bạn phải chọn ngày bắt đầu xem báo cáo"); 
					session.setAttribute("obj", obj);
					String nextJSP = "/TraphacoHYERP/pages/Distributor/LoTrinhBanHangReport.jsp";
					response.sendRedirect(nextJSP);
				}
				else
				{
					System.out.println("VO Xuat FILE Detail__");
					XuatFileExcelChiTiet(response, obj, nnId);
				}
			}
			else
			{
				String status = util.antiSQLInspection(request.getParameter("status"));
				obj.setStatus(status);
			//	obj.createNPP();
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/LoTrinhBanHangReport.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}

	private void XuatFileExcelChiTiet(HttpServletResponse response, ILoTrinh lotrinh, String nnId) throws IOException
	{
		OutputStream out1 = null;
		
		try
		{
			NumberFormat formater = new DecimalFormat("#,###,###.##");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LoTrinhBanHangChiTiet.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());
			
			WritableSheet sheet = w.createSheet("LoTrinhBanHang", 0);
			
			
			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 14);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			sheet.addCell(new Label(0, 0, "BÁO CÁO LỘ TRÌNH BÁN HÀNG CHI TIẾT" , new WritableCellFormat(cellTitle))); 
			sheet.addCell(new Label(0, 1, "TỪ NGÀY: " + lotrinh.getTungay())); //TỪ NGÀY
			sheet.addCell(new Label(0, 2, "ĐẾN NGÀY: " + lotrinh.getDenngay() )); //ĐẾN NGÀY
			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
			cellFont.setColour(Colour.BLACK);

			WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

			cellFormat.setBackground(jxl.format.Colour.GREY_50_PERCENT);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			sheet.addCell(new Label(0, 4, "STT", cellFormat));
			//sheet.addCell(new Label(1, 4, "ĐỊA BÀN", cellFormat));
			//sheet.addCell(new Label(2, 4, "CHI NHÁNH / ĐỐI TÁC", cellFormat));
			sheet.addCell(new Label(1, 4, "MÃ NVBH", cellFormat));
			sheet.addCell(new Label(2, 4, "NHÂN VIÊN BÁN HÀNG", cellFormat)); 
			sheet.addCell(new Label(3, 4, "MÃ KH", cellFormat)); 
			sheet.addCell(new Label(4, 4, "LOẠI KH", cellFormat)); 
			sheet.addCell(new Label(5, 4, "KHÁCH HÀNG", cellFormat)); 
			sheet.addCell(new Label(6, 4, "ĐỊA CHỈ", cellFormat));
			sheet.addCell(new Label(7, 4, "TUYẾN BH", cellFormat)); 
			sheet.addCell(new Label(8, 4, "NGÀY", cellFormat));
			sheet.addCell(new Label(9, 4, "PHẢI VT", cellFormat));
			sheet.addCell(new Label(10, 4, "ĐÃ VT", cellFormat)); 
			sheet.addCell(new Label(11, 4, "SỐ ĐH", cellFormat)); 
			sheet.addCell(new Label(12, 4, "ĐH CÓ VT", cellFormat)); 
			sheet.addCell(new Label(13, 4, "ĐH KHÔNG VT", cellFormat)); 
			sheet.addCell(new Label(14, 4, "DOANH THU", cellFormat)); 
			sheet.addCell(new Label(15, 4, "DS nhóm HH-BG", cellFormat)); 
			sheet.addCell(new Label(16, 4, "DT nhóm XANH", cellFormat)); 
			sheet.addCell(new Label(17, 4, "QUÃNG ĐƯỜNG", cellFormat));
			sheet.addCell(new Label(18, 4, "TG ĐẾN" , cellFormat)); 
			sheet.addCell(new Label(19, 4, "TG ĐI", cellFormat)); 
			sheet.addCell(new Label(20, 4, "TỔNG TG", cellFormat)); 
			
			sheet.setColumnView(0, 5);
			sheet.setColumnView(1, 10);
			sheet.setColumnView(2, 30);
			sheet.setColumnView(3, 15);
			sheet.setColumnView(4, 15);
			sheet.setColumnView(5, 30);
			sheet.setColumnView(6, 30);
			sheet.setColumnView(7, 10);
			sheet.setColumnView(8, 12);
			sheet.setColumnView(9, 10);
			sheet.setColumnView(10, 10);
			sheet.setColumnView(11, 10);
			sheet.setColumnView(12, 15);
			sheet.setColumnView(13, 20);
			sheet.setColumnView(14, 20);
			sheet.setColumnView(15, 20);
			sheet.setColumnView(16, 20);
			sheet.setColumnView(17, 20);
			sheet.setColumnView(18, 20);
			
			WritableCellFormat cellFormat2 = new WritableCellFormat();
			cellFormat2.setBackground(jxl.format.Colour.YELLOW);
			
			WritableCellFormat cellFormat3 = new WritableCellFormat();
			cellFormat3.setBackground(jxl.format.Colour.GRAY_25);
			
			String condition = "";
			
			
			if(lotrinh.getddkdId().trim().length() > 0)
				condition += "\n and a.DDKD_FK = '" + lotrinh.getddkdId() + "' ";
			
			
			if(lotrinh.getnppId().trim().length() > 0)
				condition += "\n and c.PK_SEQ = '" + lotrinh.getnppId() + "' ";
			else
				if(lotrinh.getTinhthanhId().trim().length() > 0)
					condition += "\n and  c.tinhthanh_fk ='" + lotrinh.getTinhthanhId() + "'";	
			
			if(lotrinh.getTungay().trim().length() > 0)
				condition += "\n and CONVERT(varchar(10), a.thoidiem, 121) >= '" + lotrinh.getTungay() + "' ";
			if(lotrinh.getDenngay().trim().length() > 0)
				condition += "\n and CONVERT(varchar(10), a.thoidiem, 121) <= '" + lotrinh.getDenngay() + "' ";
			/*if(lotrinh.getkhuvucId().trim().length() > 0)
				condition += " and a.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk = '" + lotrinh.getkhuvucId() + "' ) ";*/
			
			
			String condition2 = "";
			String sub = "";
			if(lotrinh.getddkdId().trim().length() > 0)
				condition2 += "\n and b.PK_SEQ = '" + lotrinh.getddkdId() + "' ";
			
				if(lotrinh.getnppId().trim().length() > 0)
					condition2 += "\n and c.PK_SEQ  = '" + lotrinh.getnppId() + "' ";
				else
					if(lotrinh.getTinhthanhId().trim().length() > 0)
						condition2 += "\n and  c.tinhthanh_fk = '" + lotrinh.getTinhthanhId() + "'  ";
			
			if(lotrinh.getTungay().trim().length() > 0){
				condition2 += "\n and CONVERT(varchar(10), a.thoidiem, 121) >= '" + lotrinh.getTungay() + "' ";
				sub += "\n and dh.NGAYNHAP >= '" + lotrinh.getTungay() + "' ";
			}
			if(lotrinh.getDenngay().trim().length() > 0){
				condition2 += "\n and CONVERT(varchar(10), a.thoidiem, 121) <= '" + lotrinh.getDenngay() + "' ";
				sub += "\n and dh.NGAYNHAP <= '" + lotrinh.getDenngay() + "' ";
			}
			/*if(lotrinh.getkhuvucId().trim().length() > 0)
				condition2 += " and b.NPP_FK in (  select pk_seq from nhaphanphoi where khuvuc_fk = '" + lotrinh.getkhuvucId() + "' ) ";*/
			
			
			
			String query =  "\n select CODI.NPP_FK, CODI.nppTen, CODI.ddkd_fk, CODI.ddkdTen, CODI.TINHTHANH, CODI.VUNG_FK, " +
							"\n 		DDKD.ThoiDiemDen, DDKD.ThoiDiemDi, CODI.MANHANVIEN, " +
							"\n 		ISNull(DATEDIFF(MINUTE, ThoiDiemDen, ThoiDiemDi), -1) as OLai, CODI.NGAY, vtham.SOVT, pvt.PHAIVT, ISNULL(pvt.NGAYID,0) as NGAYID, " +
							"\n			ISNULL((select SUM(sp.SOLUONG*sp.GIAMUA + (sp.SOLUONG * sp.GIAMUA * NH.THUEXUAT / 100)) from DONHANG_SANPHAM sp inner join DONHANG dh on dh.PK_SEQ = sp.DONHANG_FK INNER JOIN SANPHAM s ON s.PK_SEQ = sp.SANPHAM_FK  INNER JOIN NGANHHANG NH ON s.NGANHHANG_FK = NH.PK_SEQ WHERE dh.DDKD_FK = CODI.DDKD_FK AND dh.NPP_FK = CODI.NPP_FK AND dh.NGAYNHAP = CODI.NGAY AND dh.TRANGTHAI != 2  and dh.KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM ddkd_khachhang WHERE CONVERT(varchar(10), thoidiem, 126) = dh.NGAYNHAP)),0) as DOANHSO, " +
							"\n			(select COUNT(PK_SEQ) from DONHANG dh where dh.TRANGTHAI != 2 and dh.IsPDA = 1 and dh.DDKD_FK = CODI.DDKD_FK AND dh.NPP_FK = CODI.NPP_FK AND dh.NGAYNHAP = CODI.NGAY)as SODH, " +
							"\n			(select COUNT(PK_SEQ) from DONHANG dh where dh.TRANGTHAI != 2 and dh.IsPDA = 1 and dh.DDKD_FK = CODI.DDKD_FK AND dh.NPP_FK = CODI.NPP_FK AND dh.NGAYNHAP = CODI.NGAY and dh.KHACHHANG_FK in (select distinct KHACHHANG_FK from ddkd_khachhang where CONVERT(varchar(10), THOIDIEM, 126)= CODI.NGAY))as DHCOVT, " +
							"\n		 	(select COUNT(dh.PK_SEQ) from DONHANG dh left join ddkd_khachhang vt on vt.khachhang_fk = dh.KHACHHANG_FK and CONVERT(varchar(10), vt.THOIDIEM, 126) = dh.NGAYNHAP and vt.ddkd_fk = dh.DDKD_FK " +
							"\n		 		where vt.khachhang_fk IS NULL AND dh.TRANGTHAI != 2 and dh.IsPDA = 1 and dh.DDKD_FK = CODI.DDKD_FK AND dh.NPP_FK = CODI.NPP_FK AND dh.NGAYNHAP = CODI.NGAY ) as DHKOVT, " +
							"\n		 	isnull(nhomX.DS,0) as NHOMX, isnull(nhomH.DS,0) as NHOMH " +
							"\n from  " +
							"\n (  " +
							"\n 	select a.NPP_FK, c.TEN as nppTen, a.DDKD_FK, b.TEN as ddkdTen, b.MANHANVIEN, min(a.TOI) as ThoiDiemDen,  " +
							"\n 		( select top(1) MAX(DI) from DDKD_NPP where NPP_FK = a.NPP_FK and DDKD_FK = a.DDKD_FK and CONVERT(varchar(10), TOI, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by NPP_FK, DDKD_FK, TOI  ) as ThoiDiemDi,	    " +
							"\n 		CONVERT(varchar(10), a.thoidiem, 121) as NGAY   " +
							"\n 	from DDKD_NPP a inner join DAIDIENKINHDOANH b on a.DDKD_FK = b.PK_SEQ   " +
							"\n 	inner join NHAPHANPHOI c on a.NPP_FK = c.PK_SEQ  " +
							"\n 	where a.npp_fk > 0 " + condition +
							"\n 	 group by a.NPP_FK, c.TEN, a.DDKD_FK, b.TEN, CONVERT(varchar(10), a.thoidiem, 121), b.MANHANVIEN " +
							"\n ) " +
							"\n DDKD right join " +
							"\n (   " +
							"\n 	select distinct z.NPP_FK, c.TEN as nppTen, a.ddkd_fk, b.ten as ddkdTen, b.MANHANVIEN, CONVERT(varchar(10), a.thoidiem, 121) as NGAY, tt.TEN AS TINHTHANH, tt.VUNG_FK " +
							"\n 	from ddkd_khachhang a inner join daidienkinhdoanh b on a.ddkd_fk = b.PK_SEQ " +
							"\n 	inner join daidienkinhdoanh_npp z on z.ddkd_fk = b.PK_SEQ " +
							"\n 	inner join NHAPHANPHOI c on z.NPP_FK = c.PK_SEQ " +
							"		inner join TINHTHANH tt on tt.PK_SEQ = c.TINHTHANH_FK " +
							"\n 	where 1 = 1 " + condition2 +
							"\n ) " +
							"\n CODI on DDKD.NPP_FK = CODI.NPP_FK and DDKD.DDKD_FK = CODI.ddkd_fk and DDKD.NGAY = CODI.NGAY " +
							"\n left join (" +
							"\n	SELECT DDKH.ddkd_fk, COUNT(DISTINCT KHACHHANG_FK) AS SOVT, CONVERT(VARCHAR(10), DDKH.THOIDIEM , 126) as NGAY, kh.NPP_FK " +
							" FROM ddkd_khachhang DDKH inner join KHACHHANG kh on kh.PK_SEQ = DDKH.khachhang_fk " +
							"\n group by DDKH.ddkd_fk, CONVERT(VARCHAR(10), DDKH.THOIDIEM , 126), kh.NPP_FK" +
							"\n ) vtham on vtham.ddkd_fk = CODI.ddkd_fk and vtham.NGAY = CODI.NGAY and vtham.NPP_FK = CODI.npp_fk " +
							"\n left join (" +
							"\n SELECT DDKD_FK, COUNT(KHACHHANG_FK) AS PHAIVT, TBH.NGAYID, KH.NPP_FK,theongay.ngay  "+
		                    "\n     FROM KHACHHANG_TUYENBH KHTBH "+
		                    "\n     INNER JOIN TUYENBANHANG TBH ON TBH.PK_SEQ = KHTBH.TBH_FK "+
		                    "\n     INNER JOIN KHACHHANG KH ON KHTBH.KHACHHANG_FK = KH.PK_SEQ "+
		                    "\n     inner join dbo.ufn_ThuTheoNgay('"+lotrinh.getTungay()+"','"+lotrinh.getDenngay()+"') theongay on theongay.ngayId = TBH.NGAYID "+
		                    "\n     WHERE KH.TRANGTHAI = 1 and dbo.CoTinhF1(KHTBH.TANSO,theongay.ngay) = 1 and dbo.TuanChanLe(KHTBH.TANSO,theongay.ngay) = 1"+
		                    "\n     GROUP BY TBH.DDKD_FK, TBH.NGAYID, KH.NPP_FK,theongay.ngay  " +
							"\n ) pvt on pvt.DDKD_FK = CODI.DDKD_FK   and CODI.NGAY = pvt.ngay  and pvt.NPP_FK = CODI.npp_fk  " +
							"left join ( " + 
							"	select SUM(sp.SOLUONG*sp.GIAMUA + (sp.SOLUONG * sp.GIAMUA * NH.THUEXUAT / 100)) AS DS, dh.DDKD_FK, dh.NPP_FK, dh.NGAYNHAP, nh_sp.VUNG_FK, nh_sp.NSP_FK FROM DONHANG_SANPHAM sp inner join DONHANG dh on dh.PK_SEQ = sp.DONHANG_FK " +
							"	INNER JOIN SANPHAM s ON s.PK_SEQ = sp.SANPHAM_FK  INNER JOIN NGANHHANG NH ON s.NGANHHANG_FK = NH.PK_SEQ inner join " + 
							"	( " + 
							"		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_SANPHAM where NSP_FK = 100002 " + 
							"		UNION ALL " + 
							"		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENNAM_SANPHAM where NSP_FK = 100002 " + 
							"		UNION ALL " + 
							"		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENTRUNG_SANPHAM where NSP_FK = 100002 " + 
							"	) nh_sp on nh_sp.SP_FK = sp.SANPHAM_FK " + 
							"	where 1=1 and dh.KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM ddkd_khachhang WHERE CONVERT(varchar(10), thoidiem, 126) = dh.NGAYNHAP) " + sub +
							"	group by dh.DDKD_FK, dh.NPP_FK, dh.NGAYNHAP, nh_sp.VUNG_FK, nh_sp.NSP_FK " + 
							"		 " + 
							" ) nhomX on nhomX.DDKD_FK = CODI.ddkd_fk and nhomX.NPP_FK = CODI.npp_fk and nhomX.NGAYNHAP = CODI.NGAY AND nhomX.VUNG_FK = CODI.VUNG_FK " + 
							"  left join ( " + 
							"	select SUM(sp.SOLUONG*sp.GIAMUA + (sp.SOLUONG * sp.GIAMUA * NH.THUEXUAT / 100)) AS DS, dh.DDKD_FK, dh.NPP_FK, dh.NGAYNHAP, nh_sp.VUNG_FK, nh_sp.NSP_FK FROM DONHANG_SANPHAM sp inner join DONHANG dh on dh.PK_SEQ = sp.DONHANG_FK " +
							"	INNER JOIN SANPHAM s ON s.PK_SEQ = sp.SANPHAM_FK  INNER JOIN NGANHHANG NH ON s.NGANHHANG_FK = NH.PK_SEQ	inner join " + 
							"	( " + 
							"		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_SANPHAM where NSP_FK = 100003 " + 
							"		UNION ALL " + 
							"		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENNAM_SANPHAM where NSP_FK = 100003 " + 
							"		UNION ALL " + 
							"		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENTRUNG_SANPHAM where NSP_FK = 100003 " + 
							"	) nh_sp on nh_sp.SP_FK = sp.SANPHAM_FK " + 
							"	where 1=1 and dh.KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM ddkd_khachhang WHERE CONVERT(varchar(10), thoidiem, 126) = dh.NGAYNHAP) " + sub +
							"	group by dh.DDKD_FK, dh.NPP_FK, dh.NGAYNHAP, nh_sp.VUNG_FK, nh_sp.NSP_FK " + 
							"	" + 
							" ) nhomH on nhomH.DDKD_FK = CODI.ddkd_fk and nhomH.NPP_FK = CODI.npp_fk and nhomH.NGAYNHAP = CODI.NGAY AND nhomH.VUNG_FK = CODI.VUNG_FK " + 
							"\n where len(isnull(CODI.NGAY, '')) > 0 and vtham.SOVT is not null ";
			if(lotrinh.gettuyenId().length() > 0)
				query += " and pvt.NGAYID = " + lotrinh.gettuyenId() + " ";
			query += "\n order by CODI.NPP_FK asc, codi.DDKD_FK asc, codi.NGAY asc ";

			System.out.println("LAY BC: " + query);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			int count = 5;
			int stt = 1;
			if(rs != null)
			{
				String ngayOLD = "";
				String ttTenOLD = "";
				String ddkdTenOLD = "";
				Label label;
				
				while(rs.next())
				{
					String vung_fk = rs.getString("VUNG_FK");
					//String diaban = rs.getString("TINHTHANH");
					//String nppTen = rs.getString("nppTen");
					String ddkdId = rs.getString("ddkd_fk");
					String maddkd = rs.getString("MANHANVIEN");
					String ddkdTen = rs.getString("ddkdTen");
					String ngay = rs.getString("NGAY");
					String ngayId = rs.getString("NGAYID");
					String pvt = rs.getString("PHAIVT");
					String dvt = rs.getString("SOVT");
					String sodh = rs.getString("SODH");
					String sodhcovt = rs.getString("DHCOVT");
					String sodhkovt = rs.getString("DHKOVT");
					float ds = rs.getFloat("DOANHSO");
					float dsX = rs.getFloat("NHOMX");
					float dsH = rs.getFloat("NHOMH");
					String nppId = rs.getString("NPP_FK");
					//TINH QUANG DUONG DI
					query = " select khachhang_fk, lat, long  " +
							" from ddkd_khachhang where lat is not null and long is not null and ddkd_fk = '" + rs.getString("DDKD_FK") + "' " +
							" and CONVERT(varchar(10), thoidiem, 121) = '" + ngay + "' " +
							" and khachhang_fk in ( select pk_seq  from khachhang where npp_fk ='"+ nppId +"'  ) " +
							" order by ThoiDiem asc";
					String lattitude = "";
					String longtitude = "";
					ResultSet rsQuangduong = db.get(query);
					
					double quangduong = 0;
					if(rsQuangduong != null)
					{
						while(rsQuangduong.next())
						{
							lattitude += rsQuangduong.getString("lat") + "__";
							longtitude += rsQuangduong.getString("long") + "__";
						}
						rsQuangduong.close();
						
						if(lattitude.trim().length() > 0)
						{
							String[] _lat = lattitude.substring(0, lattitude.length() - 2).split("__");
							String[] _long = longtitude.substring(0, longtitude.length() - 2).split("__");
							
							if( _lat.length >= 2 )
							{
								for(int i = 0; i < _lat.length - 1; i++)
								{
									double lat = Double.parseDouble(_lat[i]);
									double lon = Double.parseDouble(_long[i]);
									double lat2 = Double.parseDouble(_lat[i + 1]);
									double lon2 = Double.parseDouble(_long[i + 1]);
									
									quangduong += getKhoangCachHaversine(lat, lon, lat2, lon2) / 1000;
								}
							}
						}
					}
					
					String soKM = formater.format(quangduong);
					String olai = rs.getString("OLai");
					String den = rs.getString("ThoiDiemDen") == null ? "" : rs.getString("ThoiDiemDen") ;
					String roidi = rs.getString("ThoiDiemDi") == null ? "" : rs.getString("ThoiDiemDi") ;
					
					
					if(!ddkdTenOLD.equals(ddkdTen) || !ngayOLD.equals(ngay))
					{
						
						label = new Label(2, count, "Tổng", cellFormat3);
						sheet.addCell(label);
						label = new Label(3, count, "", cellFormat3);
						sheet.addCell(label);
						label = new Label(4, count, "", cellFormat3);
						sheet.addCell(label);
						label = new Label(5, count, "", cellFormat3);
						sheet.addCell(label);							
						label = new Label(6, count, "", cellFormat3);
						sheet.addCell(label);
						
						label = new Label(7, count, ngayId, cellFormat3);
						sheet.addCell(label);
						label = new Label(8, count, ngay, cellFormat3);
						sheet.addCell(label);
						label = new Label(9, count, pvt, cellFormat3);
						sheet.addCell(label);
						label = new Label(10, count, dvt, cellFormat3);
						sheet.addCell(label);
						label = new Label(11, count, sodh, cellFormat3);
						sheet.addCell(label);
						label = new Label(12, count, sodhcovt, cellFormat3);
						sheet.addCell(label);
						label = new Label(13, count, sodhkovt, cellFormat3);
						sheet.addCell(label);
						label = new Label(14, count, formatter.format(ds), cellFormat3);
						sheet.addCell(label);
						label = new Label(15, count, formatter.format(dsH), cellFormat3);
						sheet.addCell(label);
						label = new Label(16, count, formatter.format(dsX), cellFormat3);
						sheet.addCell(label);
						
						label = new Label(17, count, soKM, cellFormat3);
						sheet.addCell(label);
						
						ddkdTenOLD = ddkdTen;
						ngayOLD = ngay;
						count ++;
					}
					
					
					/*query = "select DDKD.*, isnull(DATEDIFF(MINUTE, ThoiDiemDen, ThoiDiemDi), -1) as OLai " +
							"from " +
							"( " +
								"select l.ten as loaikh, b.mafast, b.TEN as khTen, b.DIACHI, min(a.thoidiem) as ThoiDiemDen, " +
									"( select top(1) MAX(THOIDIEMDI) from ddkd_khachhang where khachhang_fk = a.khachhang_fk and CONVERT(varchar(10), THOIDIEMDI, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by khachhang_fk, THOIDIEM  ) as ThoiDiemDi,	  " +
									"CONVERT(varchar(10), a.thoidiem, 121) as NGAY  " +
								"from ddkd_khachhang a inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ  " +
								" inner join LOAIKHACHHANG l on l.pk_seq = b.xuatkhau "+
								"where a.ddkd_fk = '" + rs.getString("DDKD_FK") + "' and CONVERT(varchar(10), a.thoidiem, 121) = '" + ngay + "'  " +
								"  and   b.PK_SEQ in ( select pk_seq  from khachhang where  npp_fk ='"+ nppId +"'  ) " +
								"group by l.ten, b.mafast, a.khachhang_fk, b.TEN, b.DIACHI, CONVERT(varchar(10), a.thoidiem, 121)  " +
							")  " +
							"DDKD " +
							"order by ThoiDiemDen asc";*/
					query = "\nselect DDKD.*, isnull(DATEDIFF(MINUTE, ThoiDiemDen, ThoiDiemDi), -1) as OLai, " + 
							"\n	(select COUNT(PK_SEQ) from DONHANG where DDKD_FK = '"+ddkdId+"' AND NPP_FK = '"+ nppId +"' and KHACHHANG_FK = DDKD.KHACHHANG_FK AND NGAYNHAP = DDKD.NGAY AND TRANGTHAI != 2 AND IsPDA = 1) as SODH, " + 
							"\n	(select COUNT(PK_SEQ) from DONHANG where DDKD_FK = '"+ddkdId+"' AND NPP_FK = '"+ nppId +"' and KHACHHANG_FK = DDKD.KHACHHANG_FK AND NGAYNHAP = DDKD.NGAY AND TRANGTHAI != 2 AND IsPDA = 1 AND KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM ddkd_khachhang WHERE CONVERT(varchar(10), thoidiem, 126) = DONHANG.NGAYNHAP)) as DHCOVT, " + 
							"\n	(select COUNT(dh.PK_SEQ) from DONHANG dh left join ddkd_khachhang vt on vt.khachhang_fk = dh.KHACHHANG_FK and CONVERT(varchar(10), vt.THOIDIEM, 126) = dh.NGAYNHAP and vt.ddkd_fk = dh.DDKD_FK " + 
							"\n 			where vt.khachhang_fk IS NULL AND dh.TRANGTHAI != 2 and dh.IsPDA = 1 and dh.DDKD_FK = '"+ddkdId+"'  AND dh.NPP_FK = '"+ nppId +"'  AND dh.KHACHHANG_FK = DDKD.KHACHHANG_FK AND NGAYNHAP = DDKD.NGAY ) as DHKOVT, " + 
							"\n	isnull((select SUM(sp.SOLUONG * sp.GIAMUA + (sp.SOLUONG * sp.GIAMUA * NH.THUEXUAT / 100)) from DONHANG dh inner join DONHANG_SANPHAM sp on sp.DONHANG_FK = dh.PK_SEQ INNER JOIN SANPHAM s ON s.PK_SEQ = sp.SANPHAM_FK  INNER JOIN NGANHHANG NH ON s.NGANHHANG_FK = NH.PK_SEQ" + 
							"\n		 where DDKD_FK = '"+ddkdId+"' and KHACHHANG_FK = DDKD.KHACHHANG_FK AND NGAYNHAP = DDKD.NGAY AND dh.TRANGTHAI != 2),0) as DS, " + 
							"\n	isnull((select SUM(sp.SOLUONG*sp.GIAMUA + (sp.SOLUONG * sp.GIAMUA * NH.THUEXUAT / 100)) FROM DONHANG_SANPHAM sp inner join DONHANG dh on dh.PK_SEQ = sp.DONHANG_FK " +
							"\n	INNER JOIN SANPHAM s ON s.PK_SEQ = sp.SANPHAM_FK  INNER JOIN NGANHHANG NH ON s.NGANHHANG_FK = NH.PK_SEQ inner join " + 
							"\n	( " + 
							"\n		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_SANPHAM where NSP_FK = 100003 and VUNG_FK = '"+ vung_fk +"' " + 
							"\n		UNION ALL " + 
							"\n		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENNAM_SANPHAM where NSP_FK = 100003 and VUNG_FK = '"+ vung_fk +"' " + 
							"\n		UNION ALL " + 
							"\n		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENTRUNG_SANPHAM where NSP_FK = 100003 and VUNG_FK = '"+ vung_fk +"' " + 
							"\n	)nh_sp on nh_sp.SP_FK = sp.SANPHAM_FK where dh.DDKD_FK = '"+ddkdId+"' AND dh.NPP_FK = '"+ nppId +"' and dh.KHACHHANG_FK = DDKD.KHACHHANG_FK AND dh.NGAYNHAP = DDKD.NGAY AND dh.TRANGTHAI != 2 AND dh.IsPDA = 1 ),0) as NHOMH, " + 
							"\n	isnull((select SUM(sp.SOLUONG*sp.GIAMUA + (sp.SOLUONG * sp.GIAMUA * NH.THUEXUAT / 100)) FROM DONHANG_SANPHAM sp inner join DONHANG dh on dh.PK_SEQ = sp.DONHANG_FK " +
							"\n	INNER JOIN SANPHAM s ON s.PK_SEQ = sp.SANPHAM_FK  INNER JOIN NGANHHANG NH ON s.NGANHHANG_FK = NH.PK_SEQ" + 
							"\n	 inner join ( " + 
							"\n		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_SANPHAM where NSP_FK = 100002 and VUNG_FK = '"+ vung_fk +"' " + 
							"\n		UNION ALL " + 
							"\n		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENNAM_SANPHAM where NSP_FK = 100002 and VUNG_FK = '"+ vung_fk +"' " + 
							"\n		UNION ALL " + 
							"\n		select SP_FK, NSP_FK, VUNG_FK from NHOMSANPHAM_MIENTRUNG_SANPHAM where NSP_FK = 100002 and VUNG_FK = '"+ vung_fk +"' " + 
							"\n	)nh_sp on nh_sp.SP_FK = sp.SANPHAM_FK where dh.DDKD_FK = '"+ddkdId+"' AND dh.NPP_FK = '"+ nppId +"' and dh.KHACHHANG_FK = DDKD.KHACHHANG_FK AND dh.NGAYNHAP = DDKD.NGAY AND dh.TRANGTHAI != 2 AND dh.IsPDA = 1 ),0) as NHOMX " + 
							"\nfrom   " + 
							"\n(   " + 
							"\n	select l.ten as loaikh, b.PK_SEQ AS KHACHHANG_FK, b.mafast, b.TEN as khTen, b.DIACHI, min(a.thoidiem) as ThoiDiemDen,   " + 
							"\n		( select top(1) MAX(THOIDIEMDI) from ddkd_khachhang where khachhang_fk = a.khachhang_fk and CONVERT(varchar(10), THOIDIEMDI, 121) = CONVERT(varchar(10), a.thoidiem, 121) group by khachhang_fk, THOIDIEM  ) as ThoiDiemDi,	    " + 
							"\n		CONVERT(varchar(10), a.thoidiem, 121) as NGAY    " + 
							"\n	from ddkd_khachhang a inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ    " + 
							"\n	 inner join LOAIKHACHHANG l on l.pk_seq = b.xuatkhau  " + 
							"\n	where a.ddkd_fk = '"+ddkdId+"' and CONVERT(varchar(10), a.thoidiem, 121) = '" + ngay + "'    " + 
							"\n	  and   b.PK_SEQ in ( select pk_seq  from khachhang where  npp_fk ='"+ nppId +"'  )   " + 
							"\n	group by l.ten, b.mafast, a.khachhang_fk, b.TEN, b.DIACHI, CONVERT(varchar(10), a.thoidiem, 121), b.PK_SEQ  " + 
							"\n)    " + 
							"\nDDKD   " + 
							"\norder by ThoiDiemDen asc ";
					
					//System.out.println("--LO TRINH CHI TIET: \n" + query);
					ResultSet rsKh = db.get(query);
					int tmp = 0;
					if(rsKh != null)
					{
						while(rsKh.next())
						{
							tmp ++;
							label = new Label(0, count, String.valueOf(stt));
							sheet.addCell(label);
							
							/*label = new Label(1, count, diaban);
							sheet.addCell(label);
							
							label = new Label(2, count, nppTen);
							sheet.addCell(label);
							*/
							label = new Label(1, count, maddkd);
							sheet.addCell(label);
							
							label = new Label(2, count, ddkdTen);
							sheet.addCell(label);
							
							
							olai = rsKh.getString("OLai");
							den = rsKh.getString("ThoiDiemDen") == null ? "" : rsKh.getString("ThoiDiemDen") ;
							roidi = rsKh.getString("ThoiDiemDi") == null ? "" : rsKh.getString("ThoiDiemDi") ;
							
							label = new Label(3, count, rsKh.getString("mafast"));
							sheet.addCell(label);
							label = new Label(4, count, rsKh.getString("loaikh"));
							sheet.addCell(label);
							label = new Label(5, count, rsKh.getString("khTen"));
							sheet.addCell(label);							
							label = new Label(6, count, rsKh.getString("DIACHI"));
							sheet.addCell(label);
							/*label = new Label(9, count, ngayId);
							sheet.addCell(label);*/
							/*label = new Label(10, count, ngay);
							sheet.addCell(label);*/
							/*label = new Label(13, count, rsKh.getString("SODH"));
							sheet.addCell(label);*/
							label = new Label(12, count, rsKh.getString("DHCOVT"));
							sheet.addCell(label);
							label = new Label(13, count, rsKh.getString("DHKOVT"));
							sheet.addCell(label);
							label = new Label(14, count, formatter.format(rsKh.getFloat("DS")));
							sheet.addCell(label);
							label = new Label(15, count, formatter.format(rsKh.getFloat("NHOMH")));
							sheet.addCell(label);
							label = new Label(16, count, formatter.format(rsKh.getFloat("NHOMX")));
							sheet.addCell(label);
							
							label = new Label(18, count, den);
							sheet.addCell(label);
							
							label = new Label(19, count, roidi);
							sheet.addCell(label);
							
							label = new Label(20, count, olai);
							sheet.addCell(label);
							
							count ++;
							stt++;
						}
						rsKh.close();
					}
					if(tmp == 0)
						count --;
				}
				rs.close();
			}
			
			w.write();
			w.close();
		} 
		catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} 
		finally
		{
			if (out1 != null)
				out1.close();
		}

	}

	private void XuatFileExcelSR(HttpServletResponse response, ILoTrinh lotrinh, String nnId) throws IOException
	{
		OutputStream out1 = null;
		try
		{
			String NppId = lotrinh.getnppId();
			String DdkdId = lotrinh.getddkdId();
			String tuyenId = lotrinh.gettuyenId();
			String tungay = lotrinh.getTungay();
			String denngay = lotrinh.getDenngay();
			String tinhthanhId = lotrinh.getTinhthanhId();
			String vungId = lotrinh.getVungId();
			
			
			String[] tungays = tungay.split("-");
			String[] denngays = denngay.split("-");
			
			String ngayId = lotrinh.gettuyenId();
			if(ngayId == null) ngayId = ""; else ngayId = ngayId.trim();
			int ngayIdi = 0;
			try {
				ngayIdi = Integer.parseInt(ngayId);
				if(ngayIdi < 1 || ngayIdi > 7) {
					ngayIdi = 0;
				}
			} catch(Exception e) { }
			
			
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=LoTrinhBanHang.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			dbutils db = new dbutils();
			String sql = "select npp.ten as tennpp ,npp.ma,kv.ten as tenkv,ddkd.pk_seq as ddkdid,ddkd.ten  as ddkdten " +
				" from nhaphanphoi npp  inner join tinhthanh kv  on kv.pk_Seq=npp.tinhthanh_fk " +
				" inner join daidienkinhdoanh_npp ddkd_npp on 	ddkd_npp.npp_fk = npp.pk_seq " +
				" inner join daidienkinhdoanh  ddkd on ddkd.pk_seq = ddkd_npp.ddkd_fk   " +
				" where 1=1 ";
			
			
			if (DdkdId.trim().length() > 0)
			{
				sql += " and ddkd.pk_seq=" + DdkdId;
			}
			
			if(NppId.trim().length() > 0)
			{
				sql+=" and npp.pk_seq=" + NppId +"  ";
			}else
			if(tinhthanhId.trim().length() > 0)
			{
				sql+=" and kv.pk_seq=" + tinhthanhId +"  ";
			}
			else
			if(vungId.trim().length() > 0)
			{
				sql+=" and kv.pk_seq in ( select pk_seq from tinhthanh where vung_fk ='"+vungId+"')";
			}
			/*if(lotrinh.getkhuvucId().trim().length() > 0)
			{
				sql += " and kv.pk_seq = '" + lotrinh.getkhuvucId() + "' ";
			}*/
			System.out.println("XuatFileExcelSR thong tin chinh' = "  + sql);
			ResultSet rs = db.get(sql);
			int k = 0;
			while (rs.next())
			{
				WritableSheet sheet = w.createSheet(rs.getString("ddkdid"), k);

				sheet.addCell(new Label(0, 1, "NPP : ")); //NPP
				sheet.addCell(new Label(1, 1, "" + rs.getString("tennpp")));

				sheet.addCell(new Label(0, 2, "ĐỊA BÀN : ")); //KHU VỰC 
				sheet.addCell(new Label(1, 2, "" + rs.getString("tenkv")));

				sheet.addCell(new Label(0, 3, "NVBH : ")); //NVBH 
				sheet.addCell(new Label(1, 3, "" + rs.getString("ddkdten")));
				
				sheet.addCell(new Label(2, 3, "Đơn vị tiền tệ: VND")); //Đơn vị tiền tệ				

				WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
				cellFont.setColour(Colour.BLACK);

				WritableCellFormat cellFormat = new WritableCellFormat(cellFont);

				cellFormat.setBackground(jxl.format.Colour.GRAY_25);
				cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

				// cellFormat.setFont()
				sheet.addCell(new Label(0, 4, "KÊNH", cellFormat)); //"KÊNH"
				sheet.addCell(new Label(1, 4, "ĐỊA BÀN", cellFormat)); //"KHU VỰC"
				sheet.addCell(new Label(2, 4, "CHI NHÁNH / ĐỐI TÁC", cellFormat)); //"NHÀ PHÂN PHỐI"
				sheet.addCell(new Label(3, 4, "NVBH", cellFormat)); //"NVBH"
				sheet.addCell(new Label(4, 4, "TUYẾN BÁN HÀNG", cellFormat)); //"TUYẾN BÁN HÀNG"
				sheet.addCell(new Label(5, 4, "NGÀY", cellFormat)); //"NGÀY"
				sheet.addCell(new Label(6, 4, "STT", cellFormat)); //"STT"
				sheet.addCell(new Label(7, 4, "KHÁCH HÀNG", cellFormat)); //"KHÁCH HÀNG"
				sheet.addCell(new Label(8, 4, "ĐỊA CHỈ", cellFormat)); //"ĐỊA CHỈ"
				sheet.addCell(new Label(9, 4, "DOANH SỐ", cellFormat)); //"DOANH SỐ"
				sheet.addCell(new Label(10, 4, "THỜI GIAN", cellFormat)); //"THỜI GIAN"
				sheet.addCell(new Label(11, 4, "ĐỘ LỆCH", cellFormat)); //"ĐỘ LỆCH"
				sheet.addCell(new Label(12, 4, "THỜI ĐIỂM ĐI", cellFormat)); //"THỜI ĐIỂM ĐI"
				sheet.setColumnView(0, 16);
				sheet.setColumnView(1, 16);
				sheet.setColumnView(2, 20);
				sheet.setColumnView(3, 20);
				sheet.setColumnView(4, 20);
				sheet.setColumnView(5, 20);
				sheet.setColumnView(6, 20);
				sheet.setColumnView(7, 20);
				sheet.setColumnView(8, 20);
				sheet.setColumnView(9, 20);
				sheet.setColumnView(10, 20);
				sheet.setColumnView(11, 20);
				
				sheet.setColumnView(12, 20);
				
				sql = 
					" select   vt.vitri ,hch.hang as hangcuahang,lch.loai as loaicuahang,kh.phuongXa as Phuong, viengtham.ngayvt as NGAYTHANG, " +
					" 		kh.chucuahieu,kh.tinhthanh_fk,kh.quanhuyen_fk,   " +
					" 		kh.ten as tencuahieu, kh.smartid, kh.diachi, kh.dienthoai, viengtham.NGAYID, " +
					" 		kbh.DIENGIAI AS KBH, npp.TEN AS NPP, KV.TEN AS KHUVUC, ddkd.TEN, isnull(kh.LAT, '0') as khLat, isnull(kh.long, '0') as khLong,  " +
					" 		ISNULL ( ( select sum(soluong*giamua + (SOLUONG * GIAMUA * NH.THUEXUAT / 100)) 	from donhang_sanpham dhsp inner join donhang dh on dh.pk_seq=dhsp.donhang_fk INNER JOIN SANPHAM s ON s.PK_SEQ = dhsp.SANPHAM_FK  INNER JOIN NGANHHANG NH ON s.NGANHHANG_FK = NH.PK_SEQ where dh.khachhang_fk=kh.pk_seq and dh.trangthai = 1 and dh.ngaynhap = viengtham.ngayvt    	),0 )  as DoanhSo  , " +
					" 		viengtham.*  " +
					" from DAIDIENKINHDOANH ddkd " +
					" left join " +
					" ( " +
					" 	 select c.ThoidiemDi , a.pk_seq as ddkdId, a.TEN as ddkdTen, b.DIENGIAI, b.NGAYID, thoidiem, datepart(dw, c.thoidiem) as ngayidvt, 0 as type, c.khachhang_fk as khId, b.NPP_FK as nppId, b.PK_SEQ as tbhId, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ) as  ngayvt, kh_tbh.SOTT as sott, kh_tbh.TANSO as tanso, isnull(c.lat, '0') as lat, isnull(c.long, '0') as long, isnull(c.dolech, '') as dolech " +
					" 	 from DAIDIENKINHDOANH a inner join TUYENBANHANG b on a.PK_SEQ = b.DDKD_FK " +
					" 	 left join ddkd_khachhang c on a.PK_SEQ = c.ddkd_fk and b.NGAYID = datepart(dw, c.thoidiem) " +
					" 	 inner join KHACHHANG_TUYENBH kh_tbh on b.PK_SEQ = kh_tbh.TBH_FK and kh_tbh.KHACHHANG_FK = c.khachhang_fk  " +
					" 	 left join  " +
					" 	 ( " +
					" 		select   MAX(thoidiem) as thoidiemmax,ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ) as ngay from ddkd_khachhang  " +
					"  		group by ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' )  " +
					" 	 )  " +
					" 	 maxtd on maxtd.thoidiemmax = c.thoidiem and maxtd.ddkd_fk = c.ddkd_fk and maxtd.khachhang_fk = c.khachhang_fk  " +
					"  " +
					" union all " +
					" 	 select  c.ThoidiemDi,a.pk_seq as ddkdId, a.TEN as ddkdTen, b.DIENGIAI, b.NGAYID, thoidiem, datepart(dw, c.thoidiem), 1, c.khachhang_fk, b.NPP_FK, b.PK_SEQ, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ), kh_tbh.SOTT as sott, kh_tbh.TANSO as tanso, isnull(c.lat, '0') as lat, isnull(c.long, '0') as long, isnull(c.dolech, '') as dolech " +
					" 	 from DAIDIENKINHDOANH a inner join TUYENBANHANG b on a.PK_SEQ = b.DDKD_FK " +
					" 	 left join ddkd_khachhang c on a.PK_SEQ = c.ddkd_fk and b.NGAYID != datepart(dw, c.thoidiem) " +
					" 	 inner join KHACHHANG_TUYENBH kh_tbh on b.PK_SEQ = kh_tbh.TBH_FK and kh_tbh.KHACHHANG_FK = c.khachhang_fk  " +
					" 	 left join  " +
					" 	 ( " +
					" 		select   MAX(thoidiem) as thoidiemmax,ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' ) as ngay from ddkd_khachhang  " +
					"  		group by ddkd_fk,khachhang_fk, Replace(convert(nvarchar(10), thoidiem , 102) , '.', '-' )  " +
					" 	 )  " +
					" 	 maxtd on maxtd.thoidiemmax = c.thoidiem and maxtd.ddkd_fk = c.ddkd_fk and maxtd.khachhang_fk = c.khachhang_fk  " +
					" ) " +
					" viengtham on viengtham.ddkdId = ddkd.PK_SEQ " +
					"  " +
					" left join khachhang kh on kh.pk_seq = viengtham.khId " +
					" left join hangcuahang hch on hch.pk_seq = kh.hch_fk  " +
					" left join loaicuahang lch on lch.pk_seq = kh.lch_fk  " +
					" left join vitricuahang vt on vt.pk_seq = kh.vtch_fk " +
					" left join NHAPHANPHOI npp on npp.PK_SEQ = viengtham.nppId " +
					" left join DDKD_GSBH on DDKD_GSBH.DDKD_FK = ddkd.PK_SEQ  " +
					" left join GIAMSATBANHANG gsbh on gsbh.PK_SEQ = DDKD_GSBH.GSBH_FK  " +
					" left join KENHBANHANG kbh on kbh.PK_SEQ = gsbh.KBH_FK  " +
					" left join TINHTHANH KV on NPP.TINHTHANH_FK = KV.PK_SEQ  " +
					" where viengtham.ddkdId = '"+rs.getString("ddkdid")+"' and '" + tungay + "' <= ngayvt and ngayvt <= '" + denngay + "' " + (ngayIdi != 0 ? " and viengtham.ngayid = '" + lotrinh.gettuyenId().trim() + "' " : " " );
					
					
					if(NppId.trim().length() > 0)
					{
						sql+=" and npp.pk_seq=" + NppId +"  ";
					}else
					if(tinhthanhId.trim().length() > 0)
					{
						sql+=" and kv.pk_seq=" + tinhthanhId +"  ";
					}
					else
						if(vungId.trim().length() > 0)
						{
							sql+=" and kv.pk_seq in ( select pk_seq from tinhthanh where vung_fk ='"+vungId+"')";
						}
				sql +=	" order by ddkdTen, thoidiem asc, type asc, viengtham.NGAYID asc, sott ";
				
				
				
				System.out.println("[LoTrinhBanHangReport.XuatExcel] sql = \n" + sql);

				ResultSet rs1 = db.get(sql);
				Label label;

				int j = 5;
				// set style to cell data
				WritableCellFormat cellFormat2 = new WritableCellFormat();

				cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				WritableCellFormat cellFormat3 = new WritableCellFormat();
				cellFormat3.setBackground(jxl.format.Colour.YELLOW);
				cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
				cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
				
				WritableCellFormat cformat;
				
				String thoidiemTruoc = "";

				if (rs1 != null)
					while (rs1.next())
					{
						String thoidiemTam = rs1.getString("thoidiem");
						if(thoidiemTam == null) thoidiemTam = ""; else thoidiemTam = thoidiemTam.trim();
						
						
						if(!thoidiemTam.trim().equals(thoidiemTruoc)) {
							thoidiemTruoc = thoidiemTam;
						
							//Xử lý độ lệch
							String dolech = "";
							String dolechDb = rs1.getString("dolech"); //Lấy độ lệch trong db
							
							if (dolechDb == null) {
								double kc = -200;						
								double khlat = rs1.getDouble("khLat");
								double khlon = rs1.getDouble("khLong");
								double lat = rs1.getDouble("lat");
								double lon = rs1.getDouble("long");
								
								if(khlat != 0 && khlon != 0 && lat != 0 && lon != 0) {
									kc = getKhoangCachHaversine(khlat, khlon, lat, lon);
								}
								
								if(kc >= 0) {
									dolech = kc + "";
								}
							} else {
								dolech = dolechDb;
							}
							
							double _dolech = -1;
							try {
								_dolech = Double.parseDouble(dolech);
								if(_dolech > 30) {
									_dolech = 30;
								}
							} catch(Exception e) {
								
							}
							
							
							String type = rs1.getString("type") == null ? "0" : rs1.getString("type").trim();
							
							cformat = type.equals("1") ? cellFormat3 : cellFormat2;
							
							//Vẽ giao diện
							label = new Label(0, j, rs1.getString("KBH"), cformat);
							sheet.addCell(label);
	
							label = new Label(1, j, rs1.getString("KHUVUC"), cformat);
							sheet.addCell(label);
	
							label = new Label(2, j, rs1.getString("NPP"), cformat);
							sheet.addCell(label);
	
							label = new Label(3, j, rs1.getString("TEN"), cformat);
							sheet.addCell(label);
	
							label = new Label(4, j, rs1.getString("NGAYID"), cformat);
							sheet.addCell(label);
							
							label = new Label(5, j, rs1.getString("NGAYTHANG"), cformat);
							sheet.addCell(label);
	
							label = new Label(6, j, rs1.getString("SOTT"), cformat);
							sheet.addCell(label);
	
							label = new Label(7, j, rs1.getString("TENCUAHIEU"), cformat);
							sheet.addCell(label);
	
							label = new Label(8, j, rs1.getString("DIACHI"), cformat);
							sheet.addCell(label);
							
							label = new Label(9, j, formatter.format( rs1.getDouble("DoanhSo") )  , cformat);
							sheet.addCell(label);						
	
							label = new Label(10, j, rs1.getString("THOIDIEM"), cformat);
							sheet.addCell(label);
							
							label = new Label(11, j, String.valueOf(_dolech), cformat);
							sheet.addCell(label);
							
							label = new Label(12, j, rs1.getString("thoidiemdi") , cformat);
							sheet.addCell(label);
							j++;
							
						}
					}

				k++;
			}
			w.write();
			w.close();
		} catch (Exception e)
		{
			System.out.println("Error: " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out1 != null)
				out1.close();
		}
	}
	
	public double getKhoangCachHaversine(double lat1, double long1, double lat2, double long2)
    {
        double R = 6371.00;
        double dLat, dLon, a, c;

        dLat = toRad(lat2 - lat1);
        dLon = toRad(long2 - long1);
        lat1 = toRad(lat1);
        lat2 = toRad(lat2);
        a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
            Math.sin(dLon / 2) * Math.sin(dLon / 2) * Math.cos(lat1) * Math.cos(lat2);
        c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return Math.abs(R * c * 1000  ); //m
    }
	
	private double toRad(double value)
    {
        return value * Math.PI / 180;
    }
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getMonth() 
	{
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getYear() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	

	public static void main(String[] args)
	{
		Calendar tu_ngay = new GregorianCalendar(2013, 1, 28);		
		
		tu_ngay.add(Calendar.DATE, 1);
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.YEAR));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.MONTH));
		System.out.println("COMPARE: " + tu_ngay.get(Calendar.DAY_OF_MONTH));
	}
	
	
}
