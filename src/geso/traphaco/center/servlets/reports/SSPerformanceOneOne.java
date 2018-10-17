package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;

import com.aspose.cells.Style;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class SSPerformanceOneOne extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	public SSPerformanceOneOne()
	{
		super();
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformanceOneOne.jsp";
		response.sendRedirect(nextJSP);
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility util = new Utility();
		
		obj.setuserId((String) session.getAttribute("userId") == null ? "" : (String) session.getAttribute("userId"));
		
		obj.setuserTen((String) session.getAttribute("userTen") == null ? "" : (String) session.getAttribute("userTen"));
		
		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId")) == null ? "" : util.antiSQLInspection(request.getParameter("nppId")));
		
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId")) == null ? "" : util.antiSQLInspection(request.getParameter("kenhId")));
		
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvkdId")));
		
		obj.setMonth(util.antiSQLInspection(request.getParameter("month")) == null ? "" : util.antiSQLInspection(request.getParameter("month")));
		
		obj.setYear(util.antiSQLInspection(request.getParameter("year")) == null ? "" : util.antiSQLInspection(request.getParameter("year")));
		
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId")) == null ? "" : util.antiSQLInspection(request.getParameter("vungId")));
		
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId")) == null ? "" : util.antiSQLInspection(request.getParameter("khuvucId")));
		
		obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvdlId")));
		
		obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("ddkdId")));
		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId")) == null ? "" : util.antiSQLInspection(request.getParameter("gsbhId")));
		
		String[] fieldsHien = request.getParameterValues("fieldsHien");
		obj.setFieldShow(fieldsHien);
		
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformanceOneOne.jsp";
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieuSS_" + util.setTieuDe(obj) + ".xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQuery(obj);
				CreatePivotTable(out, obj, query);
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());
				response.sendRedirect(nextJSP);
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());
			response.sendRedirect(nextJSP);
		}
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	private String setQuery(IStockintransit obj)
	{
		String query = 
				"		select dvkd.DONVIKINHDOANH,kbh.TEN as Kenh,v.TEN as vungTen,kv.TEN as kvTen ,gsbh.MANHANVIEN as gsbhMa,gsbh.TEN as gsbhTen,    "+   
				"   	ct.soNgayLamViec,ct.chitieu as ctSec, dssec.doanhso as tdSec, dssec.SoDonHang,dssec.SoNhanVien,dssec.soSKU,ctpri.chitieu as ctPri,ds_pri as tdPri,  "+   
				"   isnull(  "+   
				"   (   "+   
				"   	select top(1) case when b.LoaiThuong=0 then     "+   
				"   			(  b.Thuong*( ( isnull(dssec.doanhso ,0)-ct.chitieu*b.TuMuc/100)   )/100    )  "+   
				"   		when b.LoaiThuong=1 then b.Thuong   end      "+   
				"   		from TieuChiThuong a inner join TieuChiThuong_TieuChi b on b.TieuChiThuong_FK=a.pk_Seq     "+   
				"   			inner join TieuChiThuong_KhuVuc c on c.TieuChiThuong_FK=b.TieuChiThuong_FK     "+   
				"   		where a.ChucVu='SS' and    "+   
				"   		 ( (isnull(dssec.doanhso ,0)/ct.chitieu)*100 ) >=b.TuMuc      "+   
				"   			and  b.NguonGoc='1' and c.KhuVuc_FK=kv.PK_SEQ and      "+   
				"   			a.TuThang='"+obj.getMonth()+"' and a.TuNam='"+obj.getYear()+"' and a.DenThang='"+obj.getMonth()+"' and a.DenNam='"+obj.getYear()+"'   "+   
				"   		AND A.LoaiTieuChi=0   and a.KBH_FK=isnull(ctpri.kbh_fk,ct.kbh_fk) and a.DVKD_FK=isnull(ctpri.DVKD_FK,ct.DVKD_FK)  "+   
				"   		and b.LoaiThuong=1  "+   
				"   		order by b.TuMuc desc  "+   
				"   ),0)+  "+   
				"   isnull(  "+   
				"   (   "+   
				"   	select top(1) case when b.LoaiThuong=0 then     "+   
				"   			(  b.Thuong*( ( isnull(dssec.doanhso ,0)-ct.chitieu*b.TuMuc/100)   )/100    )  "+   
				"   		when b.LoaiThuong=1 then b.Thuong   end      "+   
				"   		from TieuChiThuong a inner join TieuChiThuong_TieuChi b on b.TieuChiThuong_FK=a.pk_Seq     "+   
				"   			inner join TieuChiThuong_KhuVuc c on c.TieuChiThuong_FK=b.TieuChiThuong_FK     "+   
				"   		where a.ChucVu='SS' and    "+   
				"   		 ( (isnull(dssec.doanhso ,0)/ct.chitieu)*100 ) >=b.TuMuc      "+   
				"   			and  b.NguonGoc='1' and c.KhuVuc_FK=kv.PK_SEQ and      "+   
				"   			a.TuThang='"+obj.getMonth()+"' and a.TuNam='"+obj.getYear()+"' and a.DenThang='"+obj.getMonth()+"' and a.DenNam='"+obj.getYear()+"'   "+   
				"   		AND A.LoaiTieuChi=0   and a.KBH_FK=isnull(ctpri.kbh_fk,ct.kbh_fk) and a.DVKD_FK=isnull(ctpri.DVKD_FK,ct.DVKD_FK)  "+   
				"   		and b.LoaiThuong=0  "+   
				"   		order by b.TuMuc desc  "+   
				"   ),0) as Thuong_Sec  ,  "+   
				"   isnull((   "+   
				"   	select top(1) case when b.LoaiThuong=0 then     "+   
				"   		(  b.Thuong*( ( isnull(ds_pri ,0)-ctpri.chitieu*b.TuMuc/100  ))   /100    )  "+   
				"   	when b.LoaiThuong=1 then b.Thuong     end      "+   
				"   	from TieuChiThuong a inner join TieuChiThuong_TieuChi b on b.TieuChiThuong_FK=a.pk_Seq     "+   
				"   		inner join TieuChiThuong_KhuVuc c on c.TieuChiThuong_FK=b.TieuChiThuong_FK     "+   
				"   	where a.ChucVu='SS' and    ( (isnull(ds_pri ,0)/ctpri.chitieu)*100 ) >=b.TuMuc      "+   
				"   		and  b.NguonGoc='1' and c.KhuVuc_FK=kv.PK_SEQ and      "+   
				"   		a.TuThang='"+obj.getMonth()+"' and a.TuNam='"+obj.getYear()+"' and a.DenThang='"+obj.getMonth()+"' and a.DenNam='"+obj.getYear()+"'   "+   
				"   		AND A.LoaiTieuChi=1    and a.KBH_FK=isnull(ctpri.kbh_fk,ct.kbh_fk) and a.DVKD_FK=isnull(ctpri.DVKD_FK,ct.DVKD_FK)  "+   
				"   		and b.LoaiThuong=0  "+   
				"   	order by b.TuMuc desc  "+   
				"   ),0)   "+   
				"   +isnull((   "+   
				"   	select top(1) case when b.LoaiThuong=0 then     "+   
				"   		(  b.Thuong*( ( isnull(ds_pri ,0)-ctpri.chitieu*b.TuMuc/100  ))   /100    )  "+   
				"   	when b.LoaiThuong=1 then b.Thuong     end      "+   
				"   	from TieuChiThuong a inner join TieuChiThuong_TieuChi b on b.TieuChiThuong_FK=a.pk_Seq     "+   
				"   		inner join TieuChiThuong_KhuVuc c on c.TieuChiThuong_FK=b.TieuChiThuong_FK     "+   
				"   	where a.ChucVu='SS' and    ( (isnull(ds_pri ,0)/ctpri.chitieu)*100 ) >=b.TuMuc      "+   
				"   		and  b.NguonGoc='1' and c.KhuVuc_FK=kv.PK_SEQ       "+   
				"   		and a.TuThang='"+obj.getMonth()+"' and a.TuNam='"+obj.getYear()+"' and a.DenThang='"+obj.getMonth()+"' and a.DenNam='"+obj.getYear()+"'   "+   
				"   		AND A.LoaiTieuChi=1    and a.KBH_FK=isnull(ctpri.kbh_fk,ct.kbh_fk) and a.DVKD_FK=isnull(ctpri.DVKD_FK,ct.DVKD_FK)  "+   
				"   		and b.LoaiThuong=1  "+   
				"   	order by b.TuMuc desc  "+   
				"   ),0)  as Thuong_Pri  "+   
				"     "+   
				"   from           "+   
				"   (          "+   
				"   	select	ctnpp.kenh_fk as kbh_fk, ctnpp.dvkd_fk,kv.khuvuc_fk as kvid, gsbh.pk_seq as gsbhid,  "+   
				"   		sum(ct_gs.chitieu) as chitieu , sum(ctnpp.SONGAYLAMVIEC) as soNgayLamViec    "+   
				"   	from chitieu_sec ctnpp inner join chitieusec_gsbh ct_gs on ctnpp.pk_seq =     ct_gs.chitieusec_fk   	          "+   
				"   		inner join giamsatbanhang gsbh on gsbh.pk_seq = ct_gs.gsbh_fk      "+   
				"   		inner join gsbh_khuvuc kv on    kv.gsbh_fk = gsbh.pk_seq 	      "+   
				"   	where ctnpp.trangthai=1  and ctnpp.thang ="+obj.getMonth()+" and ctnpp.nam ="+obj.getYear()+"      "+   
				"   	group by  ctnpp.kenh_fk , ctnpp.dvkd_fk, kv.khuvuc_fk, gsbh.pk_seq       "+   
				"   ) as ct           "+   
				"   full outer join           "+   
				"   (          "+   
				"   	select	ct.kenh_fk as kbh_fk, ct.dvkd_fk,     "+   
				"   		gsbh.pk_seq as gsbhid,sum(ctnpp.chitieu) as chitieu,kv.KHUVUC_FK	          "+   
				"   	from chitieu ct						          "+   
				"   		inner join chitieu_gsbh ctnpp on ct.pk_seq = ctnpp.chitieu_fk            "+   
				"   		inner join giamsatbanhang gsbh on gsbh.pk_seq = ctnpp.gsbh_fk        "+   
				"   		inner join gsbh_khuvuc kv on    kv.gsbh_fk = gsbh.pk_seq 	        "+   
				"   	where ct.trangthai=1 and  ct.thang ='"+obj.getMonth()+"' and ct.nam ="+obj.getYear()+" 		    "+   
				"   	GROUP BY  CT.KENH_FK , CT.DVKD_FK, GSBH.PK_SEQ,kv.KHUVUC_FK					          "+   
				"   ) ctpri on ct.dvkd_fk=ctpri.dvkd_fk and ct.gsbhid=ctpri.gsbhid and ct.kbh_fk=ctpri.kbh_fk  and ct.kvid=ctpri.KHUVUC_FK        "+   
				"   left join          "+   
				"   (          "+   
				"   	select npp.khuvuc_fk, dh.kbh_fk, sp2.dvkd_fk as dvkd_fk, dh.gsbh_fk as gsbhid,            "+   
				"   		sum(dh_sp.soluong * dh_sp.giamua) as doanhso ,COUNT(dh_sp.sanpham_fk) as soSKU,    "+   
				"   		COUNT(distinct dh_sp.DONHANG_FK) as SoDonHang,COUNT(distinct dh.ddkd_fk)as SoNhanVien    "+   
				"   	from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq = dh_sp.donhang_fk  	         "+   
				"   		inner join sanpham sp2 on sp2.pk_seq = dh_sp.sanpham_fk	         "+   
				"   		inner join nhaphanphoi npp on npp.pk_seq=dh.npp_fk         "+   
				"   		inner join CHITIEU_SEC ct on ct.KENH_FK=dh.KBH_FK and ct.DVKD_FK=sp2.DVKD_FK  "+   
				"   	where   dh.trangthai ='1'   and dh.NGAYNHAP>=ct.NgayBatDau and dh.NGAYNHAP<=ct.NGAYKETTHUC  "+   
				"   	and ct.THANG="+obj.getMonth()+" and ct.NAM="+obj.getYear()+"  "+   
				"   		and dh.pk_seq not in(select donhang_fk from donhangtrave where trangthai=3 and donhang_fk is not null)         "+   
				"   	group by dh.kbh_fk, sp2.dvkd_fk, dh.gsbh_fk,npp.khuvuc_fk     "+   
				"   ) as dssec on dssec.dvkd_fk=  isnull(ct.dvkd_fk,ctPri.dvkd_fk)      "+   
				"   and dssec.gsbhid= isnull(ct.gsbhid,ctPri.gsbhid) and isnull(ct.kbh_fk,ctpri.kbh_fk)=dssec.kbh_fk      "+   
				"   and dssec.khuvuc_fk =isnull(ct.kvid,ctpri.KHUVUC_FK)         "+   
				"   left join      "+   
				"   (         "+   
				"				select npp.khuvuc_fk,  nh.kbh_fk, sp2.dvkd_fk as dvkd_fk, gs.GSBH_FK as gsbhid,           "+
				"				sum(nh_sp.soluong * nh_sp.DONGIA) as ds_pri       				    "+
				"			from ERP_DONDATHANG nh inner join ERP_DONDATHANG_SANPHAM nh_sp on nh.pk_seq = nh_sp.dondathang_fk				         "+
				"				inner join sanpham sp2 on sp2.PK_SEQ = nh_sp.sanpham_fk	          "+
				"				inner join nhaphanphoi npp on npp.pk_seq=nh.npp_fk           "+
				"				inner join CHITIEU ct on ct.KENH_FK=nh.KBH_FK and ct.DVKD_FK=sp2.DVKD_FK   "+
				"				inner join  NHAPP_GIAMSATBH gs on gs.NPP_FK=nh.NPP_FK   "+
				"			where   nh.trangthai !='2'   and nh.NgayDonHang>=ct.NgayBatDau and nh.NgayDonHang <=ct.NGAYKETTHUC   "+
				"			and ct.THANG=04 and ct.NAM=2014 and nh.trangthai in (2,4)  and nh.LoaiDonHang=0   "+
				"			and nh.NgayDonHang>=gs.NGAYBATDAU and nh.NgayDonHang<=gs.NGAYKETTHUC  "+
				"			group by nh.kbh_fk, sp2.dvkd_fk, gs.gsbh_fk ,npp.khuvuc_fk     "+
				"   ) dspri on dspri.dvkd_fk=isnull(ct.dvkd_fk,ctpri.DVKD_FK)  and dspri.gsbhid=isnull(ct.gsbhid,ctpri.gsbhid)     "+   
				"   and dspri.kbh_fk=isnull(ct.kbh_fk,ctpri.kbh_fk)         "+   
				"   and ct.kvid=dspri.khuvuc_fk          "+   
				"   left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ=isnull(ct.DVKD_FK,ctpri.DVKD_FK)  "+   
				"   left join KENHBANHANG kbh on kbh.PK_SEQ=ISNULL(ct.kbh_fk,ctpri.kbh_fk)  "+   
				"   left join khuvuc kv on kv.pk_seq= isnull(ct.kvid ,ctpri.KHUVUC_FK)           "+   
				"   left join VUNG v on v.PK_SEQ=kv.VUNG_FK    "+   
				"   left join giamsatbanhang gsbh on gsbh.pk_seq=isnull(ct.gsbhid,ctpri.gsbhid)    "+   
				"   where 1=1  " ;
				
				
		if (obj.getkenhId().length() > 0)
			query += " and ct.kbh_fk ='" + obj.getkenhId() + "'";
		
		if (obj.getvungId().length() > 0)
			query += " and kv.vung_fk = '" + obj.getvungId() + "'";
		if (obj.getdvkdId().length() > 0)
			query += " and ct.dvkd_fk = '" + obj.getdvkdId() + "'";
		if (obj.getkhuvucId().length() > 0)
			query += " and kv.pk_seq = '" + obj.getkhuvucId() + "'";
		
		if (obj.getgsbhId().length() > 0)
			query += " and GSBH.pk_seq = '" + obj.getgsbhId() + "'";
		
		query = query + query;
		
		System.out.println("[Query]" + query);
		return query;
	}
	
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\SSPerformanceOneOne.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			
			CreateStaticHeader(workbook, obj);
			FillData(workbook, obj.getFieldShow(), query, obj);
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)
	{
		Hashtable<Integer, String> htb = this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
		Style style;
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("N1");
		style = cell.getStyle();
		
		cell.setStyle(style);
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("A3");
		
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Tháng : " + obj.getMonth() + "");
		
		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("B3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Năm : " + obj.getYear() + "");
		
		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
		
		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
		
		int i = 1;
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("Vung\\Mien");
		cell.setStyle(style);
		i++;
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("KhuVuc");
		cell.setStyle(style);
		i++;
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("MaGiamSat");
		cell.setStyle(style);
		i++;
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("TenGiamSat");
		cell.setStyle(style);
		i++;
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("ChiTieuBanRa");
		cell.setStyle(style);
		i++;
		
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("ThucDatBanRa");
		cell.setStyle(style);
		i++;
		
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("TienThuongBanRa");
		cell.setStyle(style);
		i++;
		
		
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("ChiTieuMuaVao");
		cell.setStyle(style);
		i++;
		
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("ThucDatMuaVao");
		cell.setStyle(style);
		i++;
		
		
		cell = cells.getCell(htb.get(i) + "1");
		cell.setValue("TienThuongMuaVao");
		cell.setStyle(style);
		i++;
		
		
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("DonViKinhDoanh");cell.setStyle(style);i++;
		cell = cells.getCell(htb.get(i) + "1");cell.setValue("KenhBanHang");cell.setStyle(style);i++;
		
		
	
		cell = cells.getCell("M1");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, (26 * 4 + i - 1) + "");
	}
	
	private void FillData(Workbook workbook, String[] fieldShow, String query, IStockintransit obj) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		
		dbutils db = new dbutils();
		
		Cell cell1 = cells.getCell("M1");
		Style style;
		style = cell1.getStyle();
		
		/*
		 * Tu Dong Chen Giam Sat Cho nhung nhap hang ma chua co gs,de tinh ra thuc
		 * dat PRI chinh xac
		 */
		String sql = 
				"update NHAPHANG set GSBH_FK= " + 
				"	( " + "		SELECT TOP(1) A.GSBH_FK " + 
				"		FROM NHAPP_GIAMSATBH A INNER JOIN GIAMSATBANHANG B ON B.PK_SEQ=A.GSBH_FK " + 
				"		WHERE NPP_FK=NH.NPP_FK AND  " + 
				"		A.NGAYBATDAU <='" + getDateTime() + "' AND A.NGAYKETTHUC >='" + getDateTime() + "' AND  B.TRANGTHAI=1 " + 
				"		ORDER BY A.NGAYBATDAU DESC " + "	)  " + 
				"	FROM NHAPHANG NH " + 
				"	WHERE GSBH_FK IS NULL AND TRANGTHAI=0 ";
		db.update(sql);
		
		ResultSet rs = db.get(query);
		
		int indexRow = 2;
		try
		{
			Cell cell = null;

			while (rs.next())
			{
				cell = cells.getCell("DA" + Integer.toString(indexRow));cell.setValue(rs.getString("VungTen"));cell.setStyle(style);
				cell = cells.getCell("DB" + Integer.toString(indexRow));cell.setValue(rs.getString("kvTen"));cell.setStyle(style);
				cell = cells.getCell("DC" + Integer.toString(indexRow));cell.setValue(rs.getString("gsbhMa"));cell.setStyle(style);
				cell = cells.getCell("DD" + Integer.toString(indexRow));cell.setValue(rs.getString("gsbhTen"));cell.setStyle(style);
				cell = cells.getCell("DE" + Integer.toString(indexRow));cell.setValue(rs.getDouble("ctSec"));cell.setStyle(style);
				cell = cells.getCell("DF" + Integer.toString(indexRow));cell.setValue(rs.getDouble("tdSec"));cell.setStyle(style);
				cell = cells.getCell("DG" + Integer.toString(indexRow));cell.setValue(rs.getDouble("thuong_Sec"));cell.setStyle(style);
				
				cell = cells.getCell("DH" + Integer.toString(indexRow));cell.setValue(rs.getDouble("ctPri"));cell.setStyle(style);
				cell = cells.getCell("DI" + Integer.toString(indexRow));cell.setValue(rs.getDouble("tdPri"));cell.setStyle(style);
				cell = cells.getCell("DJ" + Integer.toString(indexRow));cell.setValue(rs.getDouble("thuong_Pri"));cell.setStyle(style);
				
				
				
				cell = cells.getCell("DK" + Integer.toString(indexRow));cell.setValue(rs.getString("DonViKinhDoanh"));
				cell = cells.getCell("DL" + Integer.toString(indexRow));cell.setValue(rs.getString("Kenh"));
				
				indexRow++;
			}
			if (indexRow == 2)
			{
				throw new Exception("Không có báo cáo với điều kiện đã chọn !");
			}
			if (rs != null)
				rs.close();
			if (db != null)
			{
				db.shutDown();
			}
		} catch (Exception err)
		{
			err.printStackTrace();
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :" + err.toString());
		}
	}
	
	private Hashtable<Integer, String> htbValueCell()
	{
		Hashtable<Integer, String> htb = new Hashtable<Integer, String>();
		htb.put(1, "DA");
		htb.put(2, "DB");
		htb.put(3, "DC");
		htb.put(4, "DD");
		htb.put(5, "DE");
		htb.put(6, "DF");
		htb.put(7, "DG");
		htb.put(8, "DH");
		htb.put(9, "DI");
		htb.put(10, "DJ");
		htb.put(11, "DK");
		htb.put(12, "DL");
		htb.put(13, "DM");
		htb.put(14, "DN");
		htb.put(15, "DO");
		htb.put(16, "DP");
		htb.put(17, "DQ");
		htb.put(18, "DR");
		htb.put(19, "DS");
		htb.put(20, "DT");
		htb.put(21, "DU");
		htb.put(22, "DV");
		htb.put(23, "DW");
		htb.put(24, "DX");
		htb.put(25, "DY");
		htb.put(26, "DZ");
		htb.put(27, "EA");
		htb.put(28, "EB");
		htb.put(29, "EC");
		htb.put(30, "ED");
		htb.put(31, "EE");
		htb.put(32, "EF");
		htb.put(33, "EG");
		htb.put(34, "EH");
		htb.put(35, "EI");
		htb.put(36, "EJ");
		htb.put(37, "EK");
		htb.put(38, "EL");
		htb.put(39, "EM");
		htb.put(40, "EN");
		
		htb.put(41, "EO");
		htb.put(42, "EP");
		htb.put(43, "EQ");
		htb.put(44, "ER");
		htb.put(45, "ES");
		htb.put(46, "ET");
		htb.put(47, "EU");
		htb.put(48, "EV");
		htb.put(49, "EW");
		htb.put(50, "EX");
		htb.put(51, "EY");
		htb.put(52, "EZ");
		htb.put(53, "FA");
		htb.put(54, "FB");
		htb.put(55, "FC");
		
		return htb;
	}
	
}
