package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;

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

public class BMPerformance extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BMPerformance()
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
		String nextJSP = "/TraphacoHYERP/pages/Center/BMPerformance.jsp";
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

		obj.setBMId(util.antiSQLInspection(request.getParameter("bmId")) == null ? "" : util.antiSQLInspection(request.getParameter("bmId")));

		obj.setASMId(util.antiSQLInspection(request.getParameter("asmId")) == null ? "" : util.antiSQLInspection(request.getParameter("asmId")));
		
		obj.setFromMonth(util.antiSQLInspection(request.getParameter("tuthang")) == null ? "" : util.antiSQLInspection(request.getParameter("tuthang")));
		obj.setFromYear(util.antiSQLInspection(request.getParameter("tunam")) == null ? "" : util.antiSQLInspection(request.getParameter("tunam")));
		
		obj.setToMonth(util.antiSQLInspection(request.getParameter("denthang")) == null ? "" : util.antiSQLInspection(request.getParameter("denthang")));
		obj.setToYear(util.antiSQLInspection(request.getParameter("dennam")) == null ? "" : util.antiSQLInspection(request.getParameter("dennam")));
		obj.settype(util.antiSQLInspection(request.getParameter("type")) == null ? "" : util.antiSQLInspection(request.getParameter("type")));

		String nextJSP = "/TraphacoHYERP/pages/Center/BMPerformance.jsp";

		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieu_RSM_" + util.setTieuDe(obj) + ".xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQuery(obj);
				CreatePivotTable(out, obj, query);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				obj.setMsg(ex.getMessage());
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());
			response.sendRedirect(nextJSP);
		}

	}

	private String setQuery(IStockintransit obj)
	{
		
		obj.setToYear(obj.getFromYear());
		
		if(obj.gettype().equals("1"))
		{
			obj.setFromMonth("01");
			obj.setToMonth("03");
		}else if(obj.gettype().equals("2"))
		{
			obj.setFromMonth("04");
			obj.setToMonth("06");
		}
		else if(obj.gettype().equals("3"))
		{
			obj.setFromMonth("07");
			obj.setToMonth("09");
		}
		else if(obj.gettype().equals("4"))
		{
			obj.setFromMonth("10");
			obj.setToMonth("12");
		}
		String query = "";
		query=
				"select dvkd.DONVIKINHDOANH,kbh.TEN as Kenh,kv.ten,asm.manhanvien as gsbhid  ,asm.ten as gsbhten  ,   "+   
				"   	ctpri.chitieu as chitieupri ,dspri.ds_pri,  "+   
				"   isnull(  "+   
				"   (   "+   
				"   	select top(1)  case when b.LoaiThuong=0 then     "+   
				"   			(  b.Thuong* ( isnull(dspri.ds_pri ,0)- ctpri.chitieu*b.TuMuc/100  )   /100    )  "+   
				"   		when b.LoaiThuong=1 then b.Thuong   end      "+   
				"   		from TieuChiThuong a inner join TieuChiThuong_TieuChi b on b.TieuChiThuong_FK=a.pk_Seq     "+   
				"   			inner join TieuChiThuong_Vung c on c.TieuChiThuong_FK=b.TieuChiThuong_FK     "+   
				"   		where a.ChucVu='RSM' and    "+   
				"   		 ( (isnull(dspri.ds_pri ,0)/ctpri.chitieu)*100 ) >=b.TuMuc       "+   
				"   			and  b.NguonGoc='1' and c.Vung_FK=kv.PK_SEQ       "+   
				"   		and	a.TuThang>='"+obj.getFromMonth()+"' and a.TuNam>='"+obj.getFromYear()+"' and a.DenThang<='"+obj.getToMonth()+"' and a.DenNam<='"+obj.getFromYear()+"'   "+   
				"   		AND A.LoaiTieuChi=1    and b.LoaiThuong=0 and a.DVKD_FK=ctpri.DVKD_FK AND A.KBH_FK=ctpri.KBH_FK "+   
				"   		order by b.TuMuc desc  "+   
				"   ),0)+  "+   
				"   isnull(  "+   
				"   (   "+   
				"   	select top(1)  case when b.LoaiThuong=0 then     "+   
				"   			(  b.Thuong* ( isnull(dspri.ds_pri ,0)- ctpri.chitieu*b.TuMuc/100  )   /100    )   "+   
				"   		when b.LoaiThuong=1 then b.Thuong   end      "+   
				"   		from TieuChiThuong a inner join TieuChiThuong_TieuChi b on b.TieuChiThuong_FK=a.pk_Seq     "+   
				"   			inner join TieuChiThuong_Vung c on c.TieuChiThuong_FK=b.TieuChiThuong_FK     "+   
				"   		where a.ChucVu='RSM' and    "+   
				"   		 ( (isnull(dspri.ds_pri ,0)/ctpri.chitieu)*100 ) >=b.TuMuc         "+   
				"   			and  b.NguonGoc='1' and c.Vung_FK=kv.PK_SEQ       "+   
				"   		and	a.TuThang>='"+obj.getFromMonth()+"' and a.TuNam>='"+obj.getFromYear()+"' and a.DenThang<='"+obj.getToMonth()+"' and a.DenNam<='"+obj.getFromYear()+"'   "+   
				"   		AND A.LoaiTieuChi=1   and b.LoaiThuong=1   and a.DVKD_FK=ctpri.DVKD_FK AND A.KBH_FK=ctpri.KBH_FK "+   
				"   		order by b.TuMuc desc  "+   
				"   ),0)as Thuong_Pri,  (dspri.ds_pri/ctpri.chitieu )*100 as PhanTram  "+   
				"    from  "+   
				"   (       "+   
				"   	select	ct.kenh_fk  as kbh_fk,ct.dvkd_fk, asm.pk_seq as  asm_fk , asm_kv.VUNG_FK , sum(ctnpp.chitieu) as chitieu	     "+   
				"   	from chitieu ct						       "+   
				"   		inner join CHITIEU_BM ctnpp on ct.pk_seq = ctnpp.chitieu_fk        "+   
				"   		inner join BM asm on asm.pk_seq = ctnpp.BM_FK      "+   
				"   		inner join BM_CHINHANH asm_kv on asm_kv.BM_FK=ctnpp.BM_FK      "+   
				"   	where ct.trangthai=1    and ct.thang >= "+obj.getFromMonth()+"  and ct.nam >='"+obj.getFromYear()+"'     "+   
				"   		and ct.thang <= "+obj.getToMonth()+"  and ct.nam <='"+obj.getFromYear()+"'    and asm_kv.ngayketthuc >= '"+obj.getFromYear()+"-"+obj.getToMonth()+"%'    "+   
				"   	group by    ct.kenh_fk ,ct.dvkd_fk, asm.pk_seq, asm_kv.VUNG_FK      "+   
				"   ) ctpri       "+   
				"   left join       "+   
				"   (       "+   
				"   	select nh.kbh_fk, sp2.dvkd_fk as dvkd_fk, asm.pk_seq as asm_fk ,      "+   
				"   		kv.VUNG_FK as vung_fk, sum(nh_sp.soluong * nh_sp.DONGIA ) as 'ds_pri'      "+   
				"   	from ERP_DONDATHANG nh inner join ERP_DONDATHANG_SANPHAM nh_sp on nh.pk_seq = nh_sp.dondathang_fk      "+   
				"   		inner join sanpham sp2 on sp2.PK_SEQ = nh_sp.sanpham_fk	 	     "+   
				"   		inner join nhaphanphoi npp on npp.pk_seq = nh.npp_fk     "+   
				"   		inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK    "+   
				"   		inner join BM_CHINHANH asm_kv on asm_kv.VUNG_FK = kv.VUNG_FK       "+   
				"   		inner join bm asm on asm.pk_seq = asm_kv.BM_FK 	      "+   
				"   	where    nh.LoaiDonHang=0 and "+   
				"   	SUBSTRING( nh.NgayDonHang,1,7)  >= '"+obj.getFromYear()+"-"+obj.getFromMonth()+"'  "+   
				"   	and   SUBSTRING( nh.NgayDonHang,1,7)  <= '"+obj.getFromYear()+"-"+obj.getToMonth()+"'       "+   
				"   	and SUBSTRING( asm_kv.NGAYKETTHUC,1,7) >='"+obj.getFromYear()+"-"+obj.getToMonth()+"'    "+   
				"   	and nh.NgayDonHang>=asm_kv.ngaybatdau and nh.NgayDonHang <=asm_kv.ngayketthuc   AND NH.TRANGTHAI IN (2,4)    "+   
				"   	group by nh.kbh_fk, sp2.dvkd_fk,asm.pk_seq , kv.VUNG_FK  "+   
				"   ) as dspri on dspri.dvkd_fk=ctpri.dvkd_fk    "+   
				"   and dspri.asm_fk=  ctpri.asm_fk and dspri.kbh_fk=ctpri.kbh_fk   "+   
				"   and ctpri.VUNG_FK= dspri.vung_fk        "+   
				"   left join DONVIKINHDOANH dvkd on dvkd.PK_SEQ=ctpri.DVKD_FK  "+   
				"   left join KENHBANHANG kbh on kbh.PK_SEQ=ctpri.kbh_fk  "+   
				"   left join VUNG kv on kv.pk_seq=ctpri.VUNG_FK    "+   
				"   left join BM ASM  on asm.pk_seq=ctpri.asm_fk   "+   
				"   where 1=1  ";
						 
		if (obj.getkenhId().length() > 0)
			query += " and ct.kbh_fk ='" + obj.getkenhId() + "'";

		if (obj.getvungId().length() > 0)
			query += " and kv.vung_fk = '" + obj.getvungId() + "'";

		if (obj.getdvkdId().length() > 0)
			query += " and ct.dvkd_fk = '" + obj.getdvkdId() + "'";

		if (obj.getkhuvucId().length() > 0)
			query += " and kv.pk_seq = '" + obj.getkhuvucId() + "'";

		if (obj.getvungId().length() > 0)
			query += " and kv.pk_seq = '" + obj.getvungId() + "'";

		if (obj.getBMId().length() > 0)
			query += " and bm.pk_seq = '" + obj.getBMId() + "'";
		query = query + query;
		System.out.println("1.Query SalesRep : " + query);
		return query;
	}

	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\BM_ThucHienChiTieuTT.xlsm";
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

		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Qúy : " + obj.gettype() + "");

		cells.setRowHeight(3, 18.0f);
		cell = cells.getCell("B3");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Năm : " + obj.getYear() + "");

		cells.setRowHeight(4, 18.0f);
		cell = cells.getCell("A4");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));

		cells.setRowHeight(5, 18.0f);
		cell = cells.getCell("A5");
		ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
		
		
	
		cell = cells.getCell("DA1"); cell.setValue("MaNhanVien");
		cell = cells.getCell("DB1"); cell.setValue("TenNhanVien");
		cell = cells.getCell("DC1"); cell.setValue("Vung");
		cell = cells.getCell("DD1"); cell.setValue("ChiTieuMuaVao");
		cell = cells.getCell("DE1"); cell.setValue("ThucDatMuaVao");
		cell = cells.getCell("DF1"); cell.setValue("ThuongMuaVao");
		cell = cells.getCell("DG1"); cell.setValue("DonViKinhDoanh");
		cell = cells.getCell("DH1"); cell.setValue("KenhBanHang");
		
		
	}

	private void FillData(Workbook workbook, String[] fieldShow, String query, IStockintransit obj) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		Cells cells = worksheet.getCells();
		dbutils db = new dbutils();
		int indexRow = 2;
		
		
		
		
		
		ResultSet   rs=db.get(query);
		try
		{
			Cell cell = null;
			while (rs.next())
			{
				
				cell = cells.getCell("DA" + Integer.toString(indexRow));cell.setValue(rs.getString("GSBHID"));
				cell = cells.getCell("DB" + Integer.toString(indexRow));cell.setValue(rs.getString("GSBHTEN"));
				cell = cells.getCell("DC" + Integer.toString(indexRow));cell.setValue(rs.getString("TEN"));
				cell = cells.getCell("DD" + Integer.toString(indexRow));cell.setValue(rs.getDouble("CHITIEUPRI"));
				cell = cells.getCell("DE" + Integer.toString(indexRow));cell.setValue(rs.getDouble("DS_PRI"));
				cell = cells.getCell("DF" + Integer.toString(indexRow));cell.setValue(rs.getDouble("THUONG_PRI"));				
				cell = cells.getCell("DG" + Integer.toString(indexRow));cell.setValue(rs.getString("DonViKinhDoanh"));
				cell = cells.getCell("DH" + Integer.toString(indexRow));cell.setValue(rs.getString("Kenh"));
				
				indexRow++;
				
			}
			if (rs != null)
				rs.close();
			if(indexRow==2)
			{
				throw new Exception("Khôn có báo cáo tron thời gian này !");
			}
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

	
}
