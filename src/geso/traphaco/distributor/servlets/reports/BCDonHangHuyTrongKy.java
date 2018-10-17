package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCDonHangHuyTrongKy extends HttpServlet
{
	private static final long serialVersionUID = 1L;

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
	    
	    String view = request.getParameter("view");
	    if(view == null)
	    	view = "";
	    
	    obj.setuserId(userId);
	    
	    String nppId ="";
		if(view.equals("TT"))
		{
			nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}
		else
		{
			nppId=util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}
	      
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
	    obj.init();	
	    obj.setDongiagoc("1");
	    
		if(!view.equals("TT"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BCDonHangHuyTrongKy.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/BCDonHangHuyTrongKyNPP.jsp";
			response.sendRedirect(nextJSP);
		}
	}

	
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		Utility  util = new Utility();
		
		String userId = (String) session.getAttribute("userId");
		if (userId == null)
			userId = "";
		obj.setuserId(userId);
		
		String userTen = (String) session.getAttribute("userTen");
		obj.setuserTen(userTen);
		
		String view = request.getParameter("view");
		if(view == null)
			view = "";
		
		String nppId = "";
		if(view.equals("TT"))
		{
			nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}
		else
		{
			nppId=util.getIdNhapp(userId);
		}
		
		System.out.println("::: NPP: " + nppId );
		obj.setnppId(nppId);
		
		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
		if (ddkdId == null)
			ddkdId = "";
		obj.setDdkd(ddkdId);

		String tungay = util.antiSQLInspection(request.getParameter("Sdays"));
		if (tungay == null)
			tungay = "";
		obj.settungay(tungay);

		String denngay = util.antiSQLInspection(request.getParameter("Edays"));
		if (denngay == null)
			denngay = "";
		obj.setdenngay(denngay);
		
		String laygiaGoc = request.getParameter("laygiaGoc");
		if(laygiaGoc == null)
			laygiaGoc = "0";
		obj.setDongiagoc(laygiaGoc);
		
		String laychuyenSale = request.getParameter("laychuyenSale");
		if(laychuyenSale == null)
			laychuyenSale = "0";
		obj.setChuyenSale(laychuyenSale);
		
		String laykhuyenMai = request.getParameter("laykhuyenMai");
		if(laykhuyenMai == null)
			laykhuyenMai = "0";
		obj.setKhuyenmai(laykhuyenMai);
		
		String kbhId = request.getParameter("kbhId");
		if(kbhId == null)
			kbhId = "";
		obj.setkenhId(kbhId);
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?util.antiSQLInspection(request.getParameter("vungId")):"");			
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?util.antiSQLInspection(request.getParameter("khuvucId")):"");
		obj.setTtId(util.antiSQLInspection(request.getParameter("ttId"))!=null?util.antiSQLInspection(request.getParameter("ttId")):"");	
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");
		obj.setTrangthai(util.antiSQLInspection(request.getParameter("trangthai")) != null ? util.antiSQLInspection(request.getParameter("trangthai")) : "");
		obj.setkhoId(util.antiSQLInspection(request.getParameter("khoId")) != null ? util.antiSQLInspection(request.getParameter("khoId")) : "");
		
		String action = request.getParameter("action");
		if (action.equals("tao")) 
		{
			try 
			{
				request.setCharacterEncoding("utf-8");
				response.setContentType("application/xlsm");

				response.setHeader("Content-Disposition", "attachment; filename=BCDonHangHuyTrongKyNPP.xlsm");

				OutputStream out = response.getOutputStream();

				response.setHeader("Content-Disposition", "attachment; filename=BCDonHangHuyTrongKyNPP.xlsm");
				String query = "";
				if( session.getAttribute("congtyId").toString().trim().length() >= 6 )
					query = setQueryMTV("", obj);
				else
					query = setQueryNPP("", obj);
				
				CreatePivotTable(out, obj, query, session.getAttribute("congtyId").toString() ) ;
			} 
			catch (Exception ex) 
			{
				request.getSession().setAttribute("errors", ex.getMessage());
			}
		}else{
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);

			if(!view.equals("TT"))
			{
				String nextJSP = "/TraphacoHYERP/pages/Distributor/BCDonHangHuyTrongKy.jsp";
				response.sendRedirect(nextJSP);
			}
			else
			{
				String nextJSP = "/TraphacoHYERP/pages/Center/BCDonHangHuyTrongKyNPP.jsp";
				response.sendRedirect(nextJSP);
			}
		}
		
	}

	private String setQueryMTV(String sql, IStockintransit obj) 
	{
		Utility util = new Utility();
		
		String condition = " and a.npp_fk = '" + obj.getnppId() + "' ";
		if(obj.gettungay().trim().length() > 0)
			condition += " and a.NgayDonHang >= '" + obj.gettungay() + "' ";
		if(obj.getdenngay().trim().length() > 0)
			condition += " and a.NgayDonHang <= '" + obj.getdenngay() + "' ";
		if(obj.getTrangthai().trim().length() > 0)
			condition += " and a.trangthai = '" + obj.getTrangthai() + "' ";
		if(obj.getkenhId().trim().length() > 0)
			condition += " and a.kbh_fk = '" + obj.getkenhId() + "' ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		//condition += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		condition += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + obj.getuserId() + "' ) ) ) ";
		
		condition += " and a.kbh_fk in " + util.quyen_kenh( obj.getuserId() );
		
	  	sql =  "select a.PK_SEQ as ddhId, a.trangthai, " + 
	  			 "			 '' as SOHOADON, a.NgayDonHang, d.TEN as tenkho,    " + 
	  			 "			 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   " + 
	  			 "			 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    " + 
	  			 "			 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    " + 
	  			 "			 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    " + 
	  			 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   " + 
	  			 "			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, '' SOLO, b.SOLUONG, b.SOLUONG as soluong_chuan,   " + 
	  			 "			 b.dongia * round( ( 1 + b.thueVAT / 100.0 ), 0 ) as dongia, round( b.dongiaGOC * ( 1 + b.thueVAT / 100.0 ), 0) as dongiaGOC, '' as scheme, a.CS_DUYET, a.SS_DUYET, " + 
	  			 " 		ISNULL ( ( select count( hd.PK_SEQ ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) as daraHOADON, " +
				 " 		ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) as daraPGH, " +
				 " 		ISNULL ( ( select TEN from KENHBANHANG where pk_seq = a.kbh_fk ), '' ) as kbh " +
	  			 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM b on a.PK_SEQ = b.dondathang_fk   " + 
	  			 "		left join SANPHAM c on b.sanpham_fk = c.PK_SEQ   " + 
	  			 "		left join KHO d on a.Kho_FK = d.PK_SEQ   " + 
	  			 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   " + 
	  			 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   " + 
	  			 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   " + 
	  			 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     " + 
	  			 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     " + 
	  			 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      " + 
	  			 "		left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    " + 
	  			 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   " + 
	  			 "	where a.TRANGTHAI = 3    " + condition;
	  	
	  	if(obj.getKhuyenmai().equals("1"))
	  	{
	  			 sql += "union ALL " + 
			  			 "	select a.PK_SEQ as ddhId, case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Chờ duyệt' when 2 then N'Đã duyệt' when 4 then N'Hoàn tất' end as trangthai, " + 
			  			 "			 '' as SOHOADON, a.NgayDonHang, d.TEN as tenkho,    " + 
			  			 "			 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   " + 
			  			 "			 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    " + 
			  			 "			 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    " + 
			  			 "			 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    " + 
			  			 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   " + 
			  			 "			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, '' SOLO, b.SOLUONG, b.SOLUONG as soluong_chuan,    " + 
			  			 "			 0 dongia, 0 dongiaGOC, f.SCHEME, a.CS_DUYET, a.SS_DUYET, " + 
			  			" 		ISNULL ( ( select count( hd.PK_SEQ ) from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK where hd.TRANGTHAI not in ( 3, 5 ) and hd_dh.DDH_FK = a.pk_seq ), 0 ) as daraHOADON, " +
						 " 		ISNULL ( ( select COUNT( PK_SEQ ) from ERP_YCXUATKHONPP where TRANGTHAI != 2 and DDH_FK = a.pk_seq ), 0 ) as daraPGH, " +
						 " 		ISNULL ( ( select TEN from KENHBANHANG where pk_seq = a.kbh_fk ), '' ) as kbh " +
			  			 "	from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_CTKM_TRAKM b on a.PK_SEQ = b.DONDATHANGID   " + 
			  			 "		left join SANPHAM c on b.SPMA = c.MA   " + 
			  			 "		left join KHO d on a.Kho_FK = d.PK_SEQ   " + 
			  			 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   " + 
			  			 "		left join CTKHUYENMAI f on b.CTKMID = f.PK_SEQ " + 
			  			 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   " + 
			  			 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   " + 
			  			 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     " + 
			  			 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     " + 
			  			 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      " + 
			  			 "		left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    " + 
			  			 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   " + 
			  			 "	where a.TRANGTHAI = 3  " + condition;		
	  	}
	  	
	  	sql +=  "order by NgayDonHang asc ";
	  	
	  	System.out.println("::: QUERY BAO CAO: " + sql);
		return sql;
	}
	

	private String setQueryNPP(String sql, IStockintransit obj) 
	{
		Utility util = new Utility();
		
		String condition = " and a.npp_fk = '" + obj.getnppId() + "' ";
		if(obj.gettungay().trim().length() > 0)
			condition += " and a.NGAYNHAP >= '" + obj.gettungay() + "' ";
		if(obj.getdenngay().trim().length() > 0)
			condition += " and a.NGAYNHAP <= '" + obj.getdenngay() + "' ";
		if(obj.getTrangthai().trim().length() > 0)
			condition += " and a.trangthai = '" + obj.getTrangthai() + "' ";
		if(obj.getkenhId().trim().length() > 0)
			condition += " and a.kbh_fk = '" + obj.getkenhId() + "' ";
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		condition += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "a", "khachhang_fk", obj.getLoainhanvien(), obj.getDoituongId() );
				
	  	sql =   "select a.PK_SEQ as ddhId, case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã duyệt' when 3 then N'Đã duyệt' when 4 then N'Hoàn tất' end as trangthai,   "+
	  			 "		 '' as SOHOADON, a.NGAYNHAP as NgayDonHang, d.TEN as tenkho,      "+
	  			 "		 N'Đơn hàng bán' loaidonhang,    e.maFAST as makhachhangOLD,  e.MA as makhachhangNEW, e.TEN as tenkhachhang,      "+
	  			 "		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,     "+
	  			 "		 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, '' SOLO, b.SOLUONG, b.SOLUONG as soluong_chuan,     "+
	  			 "		 b.GIAMUA * round( ( 1 + b.thueVAT / 100.0 ), 0 ) as dongia, b.dongiaGOC * round( ( 1 + b.thueVAT / 100.0 ), 0 ) as dongiaGOC, '' as scheme,   "+
	  			 " 		ISNULL ( ( select TEN from KENHBANHANG where pk_seq = a.kbh_fk ), '' ) as kbh " +
	  			 "from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK     "+
	  			 "	left join SANPHAM c on b.sanpham_fk = c.PK_SEQ     "+
	  			 "	left join KHO d on a.Kho_FK = d.PK_SEQ     "+
	  			 "	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ     "+
	  			 "	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK       "+
	  			 "	left join TINHTHANH tt on tt.PK_SEQ = e.TINHTHANH_FK     "+
	  			 "	left join QUANHUYEN qh on qh.PK_SEQ = e.QUANHUYEN_FK  "+
	  			 "	inner join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ      "+
	  			 "	left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK      "+
	  			 "	left join VUNG v on v.PK_SEQ = kv.VUNG_FK where a.trangthai = 2 " + condition;
	  	if(obj.getKhuyenmai().equals("1"))
	  	{
	  			 sql += "union ALL " + 
	  					 "select a.PK_SEQ as ddhId, case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã duyệt' when 3 then N'Đã duyệt' when 4 then N'Hoàn tất' end as trangthai,   "+
	  					 "		 '' as SOHOADON, a.NGAYNHAP as NgayDonHang, d.TEN as tenkho,      "+
	  					 "		 N'Khuyến mại' as loaidonhang,     "+
	  					 "		 e.maFAST as makhachhangOLD,      "+
	  					 "		 e.ma as makhachhangNEW,      "+
	  					 "		 e.TEN as tenkhachhang,      "+
	  					 "		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,     "+
	  					 "		 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, '' SOLO, b.SOLUONG, b.SOLUONG as soluong_chuan,      "+
	  					 "		 0 dongia, 0 dongiaGOC, f.SCHEME,   "+
	  					 " 		ISNULL ( ( select TEN from KENHBANHANG where pk_seq = a.kbh_fk ), '' ) as kbh " +
	  					 "from DONHANG a inner join DONHANG_CTKM_TRAKM b on a.PK_SEQ = b.DONHANGID     "+
	  					 "	left join SANPHAM c on b.SPMA = c.MA     "+
	  					 "	left join KHO d on a.Kho_FK = d.PK_SEQ     "+
	  					 "	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ     "+
	  					 "	left join CTKHUYENMAI f on b.CTKMID = f.PK_SEQ   "+
	  					 "	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ     "+
	  					 "	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK       "+
	  					 "	left join TINHTHANH tt on tt.PK_SEQ = e.TINHTHANH_FK     "+
	  					 "	left join QUANHUYEN qh on qh.PK_SEQ = e.QUANHUYEN_FK       "+
	  					 "	left join KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK      "+
	  					 "	left join VUNG v on v.PK_SEQ = kv.VUNG_FK " + 
			  			 "	where a.TRANGTHAI = 2  " + condition;		
	  	}
	  	
	  	sql +=  "order by NgayDonHang asc ";
	  	
	  	System.out.println("::: QUERY BAO CAO: " + sql);
		return sql;
	}
	
	
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query, String congtyId) throws Exception 
	{
		try 
		{
			FileInputStream fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCDonHangHuyTrongKyNPP.xlsm");
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateHeader(workbook,obj); 
			FillData(workbook, query, obj, congtyId);			
			workbook.save(out);
			fstream.close();
		} 
		catch (Exception ex) 
		{
			ex.printStackTrace();
			throw new Exception("Error Message: " + ex.getMessage());
		}
	}
	
	
	private boolean FillData(Workbook workbook, String query, IStockintransit obj, String congtyId) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		
		if (rs != null) {
			try 
			{
				Cell cell = null;

				while (rs.next()) 
				{					
					double dongia = Math.round( rs.getDouble("dongia") );
					if( obj.getDongiagoc().equals("1") )
						dongia = Math.round( rs.getDouble("dongiaGOC") );
					
					String scheme = rs.getString("scheme") == null ? "" : rs.getString("scheme");
					if( scheme.trim().length() > 0 )
						dongia = 0;
					
					cell = cells.getCell("FA" + Integer.toString(i));	cell.setValue(rs.getString("vungTen"));
					cell = cells.getCell("FB" + Integer.toString(i));	cell.setValue(rs.getString("khuvucTen"));
					cell = cells.getCell("FC" + Integer.toString(i));	cell.setValue(rs.getString("tinhthanhTen"));
					cell = cells.getCell("FD" + Integer.toString(i));	cell.setValue(rs.getString("quanhuyenTen"));
					
					cell = cells.getCell("FE" + Integer.toString(i));	cell.setValue(rs.getString("loaidonhang"));
					cell = cells.getCell("FF" + Integer.toString(i));	cell.setValue(rs.getString("ddhId"));
					cell = cells.getCell("FG" + Integer.toString(i));	cell.setValue(rs.getString("ngaydonhang"));
					
					cell = cells.getCell("FH" + Integer.toString(i));	cell.setValue(rs.getString("makhachhangOLD"));
					cell = cells.getCell("FI" + Integer.toString(i));	cell.setValue(rs.getString("makhachhangNEW"));
					cell = cells.getCell("FJ" + Integer.toString(i));	cell.setValue( rs.getString("tenkhachhang"));
					cell = cells.getCell("FK" + Integer.toString(i));	cell.setValue(rs.getString("ddkdTen"));
					
					cell = cells.getCell("FL" + Integer.toString(i));	cell.setValue(rs.getString("masanphamOLD"));
					cell = cells.getCell("FM" + Integer.toString(i));	cell.setValue(rs.getString("masanphamNEW"));
					cell = cells.getCell("FN" + Integer.toString(i));	cell.setValue(rs.getString("tensanpham"));
					
					//cell = cells.getCell("FO" + Integer.toString(i));	cell.setValue(rs.getString("solo") );
			
					cell = cells.getCell("FO" + Integer.toString(i));	cell.setValue( rs.getDouble("soluong") );
					cell = cells.getCell("FP" + Integer.toString(i));	cell.setValue( rs.getDouble("soluong_chuan") );
					
					cell = cells.getCell("FQ" + Integer.toString(i));	cell.setValue( dongia );
					cell = cells.getCell("FR" + Integer.toString(i));	cell.setValue( dongia * rs.getDouble("soluong") );
					
					cell = cells.getCell("FS" + Integer.toString(i));	cell.setValue( rs.getString("scheme") );
					
					String trangthai = rs.getString("trangthai");
					if( congtyId.trim().length() >= 6 ) //Trạng thái của MTV khác NPP
					{
						String tt = rs.getString("trangthai");
						int daraPGH = rs.getInt("daraPGH");
                		int daraHOADON = rs.getInt("daraHOADON");
                		int cs_duyet = rs.getInt("CS_DUYET");
                		int ss_duyet = rs.getInt("SS_DUYET");
                		
						if( tt.equals("3") )
                			trangthai = "Đã hủy";
                		else if( daraPGH > 0 )
                			trangthai = "Đã ra PGH";
                		else if( daraHOADON > 0 )
                			trangthai = "Đã ra hóa đơn";
                		else if ( cs_duyet > 0 )
                			trangthai = "Đã duyệt CS";
                		else if ( ss_duyet > 0 )
                			trangthai = "Đã duyệt SS";
                		else if( tt.equals("0") )
                			trangthai = "Mới tạo";
                		else
                			trangthai = "NA";
					}
					
					cell = cells.getCell("FT" + Integer.toString(i));	cell.setValue( trangthai );
					cell = cells.getCell("FU" + Integer.toString(i));	cell.setValue( rs.getString("kbh") );
							
					++i;					
				}

				if (rs != null) rs.close();
				
				
				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				  
			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
			finally
			{
				db.shutDown();
			}
		}else{
			return false;
		}		
		return true;
	}
	
	
	private void CreateHeader(Workbook workbook,IStockintransit obj) 
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();
		
	    Style style;
	    Font font = new Font();
	    font.setColor(Color.RED);//mau chu
	    font.setSize(16);// size chu
	   	font.setBold(true);
	   	
	    cells.setRowHeight(0, 20.0f);
	    Cell cell = cells.getCell("A1");
	    style = cell.getStyle();
	    style.setFont(font); 
	    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
	    
	    String tieude = "BÁO CÁO ĐƠN HÀNG BÁN TRONG KỲ";
	    if(obj.getFromMonth().length() > 0)
	    	tieude = "BÁO CÁO ĐƠN HÀNG BÁN TRONG KỲ";
	    ReportAPI.getCellStyle(cell,Color.RED, true, 14, tieude);
	    
	    String message = "";
		cells.setRowHeight(2, 18.0f);
		cell = cells.getCell("A3");
		ReportAPI.getCellStyle(cell, Color.RED, true, 9, message);   

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A4");
	    
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ tháng : " + obj.getFromMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B4"); 
	    if(obj.getFromMonth() == "")
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
	    else
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến tháng : " + obj.getToMonth() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A6");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	    		
		
		cell = cells.getCell("FA1");		cell.setValue("Vung");
		cell = cells.getCell("FB1");		cell.setValue("KhuVuc");
		cell = cells.getCell("FC1");		cell.setValue("TinhThanh");
		cell = cells.getCell("FD1");		cell.setValue("QuanHuyen");
		
		cell = cells.getCell("FE1");		cell.setValue("LoaiDonHang");
		cell = cells.getCell("FF1");		cell.setValue("SoDonHang");
		cell = cells.getCell("FG1");		cell.setValue("NgayDonHang");
		
		cell = cells.getCell("FH1");		cell.setValue("MaKhachHangCu");
		cell = cells.getCell("FI1");		cell.setValue("MaKhachHangMoi");
		cell = cells.getCell("FJ1");		cell.setValue("TenKhachHang");
		cell = cells.getCell("FK1");		cell.setValue("TrinhDuocVien");
		
		cell = cells.getCell("FL1");		cell.setValue("MaSanPhamCu");
		cell = cells.getCell("FM1");		cell.setValue("MaSanPhamMoi");		
		cell = cells.getCell("FN1");		cell.setValue("TenSanPham");
		
		//cell = cells.getCell("FO1");		cell.setValue("SoLo");
		
		cell = cells.getCell("FO1");		cell.setValue("SoLuong");
		cell = cells.getCell("FP1");		cell.setValue("SoLuongChuan");
		cell = cells.getCell("FQ1");		cell.setValue("DonGia");
		cell = cells.getCell("FR1");		cell.setValue("ThanhTien");
		cell = cells.getCell("FS1");		cell.setValue("Scheme");
		cell = cells.getCell("FT1");		cell.setValue("TrangThai");
		cell = cells.getCell("FU1");		cell.setValue("KenhBanHang");
		
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}
