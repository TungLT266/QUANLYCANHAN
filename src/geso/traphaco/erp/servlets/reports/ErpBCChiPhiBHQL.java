package geso.traphaco.erp.servlets.reports;

import geso.traphaco.erp.beans.stockintransit.IStockintransit;
import geso.traphaco.erp.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.text.DecimalFormat;
import java.text.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;


import com.aspose.cells.BorderType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.HorizontalAlignmentType;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class ErpBCChiPhiBHQL extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
   
    public ErpBCChiPhiBHQL() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String querystring = request.getQueryString();
		Utility util = new Utility();
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setErpCongtyId(ctyId);

		obj.setuserId(util.getUserId(querystring));
		obj.setuserTen((String) session.getAttribute("userTen"));
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		String view = util.antiSQLInspection(request.getParameter("view"));
		if(view == null) view = "";
		
		obj.setView(view);		
		obj.InitErp();
		session.setAttribute("obj", obj);
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiPhiBHQL.jsp";
		response.sendRedirect(nextJSP);
		
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		obj.setdiscount("1");
		obj.setvat("1");
		
		OutputStream out = response.getOutputStream();
		Utility util = new Utility();
		String[] ctyIds = request.getParameterValues("ctyIds");
		obj.setCtyIds(ctyIds);
		obj.setView(util.antiSQLInspection(request.getParameter("view")));

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		
		String ctyId = (String)session.getAttribute("congtyId");
		obj.setErpCongtyId(ctyId);		
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")));
		
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")));
		
		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
		
		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpBCChiPhiBHQL.jsp";
		obj.InitErp();
		
		System.out.println("Action la: " + action);
		if (action.equals("Taomoi")) 
		{			
			response.setContentType("application/xls");
			response.setHeader("Content-Disposition", "attachment; filename=BCQuyDauTu.xls");
	        try 
	        {
	        	String query = setQuery(obj, "");
	        	
				if(!CreatePivotTable(out, response, request, obj, query))
				{
					response.setContentType("text/html");
				    PrintWriter writer = new PrintWriter(out);
				    writer.print("Xin loi khong co bao cao trong thoi gian nay");
				    writer.close();
				}
			} 
	        catch (Exception e) 
	        {
				obj.setMsg("Khong the tao bao cao " + e.getMessage());
				System.out.println(e.toString());
			}
		}
		else {
		session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
		return;
		}
	}
	

	
	private boolean CreatePivotTable(OutputStream out,HttpServletResponse response,HttpServletRequest request,IStockintransit obj, String query) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();		
		
		fstream = new FileInputStream(getServletContext().getInitParameter("path") + "\\BCQuyDauTu.xlsm");		
				
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		
		isFillData = FillData(workbook, obj, query);
   
		if (!isFillData)
		{
			if(fstream != null)
				fstream.close();
			return false;
		}
		
		workbook.save(out);
		fstream.close();
		return true;	
	}
	
	private void setStyleColorGray(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
        style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleColorRed(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Z1");
		Style style;	
		 style = cell1.getStyle();
       //style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
       // style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleColorNormar(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("X1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	private void setStyleColorLeft(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("P1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private void setStyleColorCenter(Cells cells ,Cell cell)
	{
		Cell cell1 = cells.getCell("Q1");
		Style style;	
		 style = cell1.getStyle();
		 style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
                cell.setStyle(style);
        
        
	}
	
	private void setStyleColorGray(Cells cells ,Cell cell, String leftright)
	{
		Cell cell1 = cells.getCell("Y1");
		Style style;	
		style = cell1.getStyle();
        if(leftright.equals("1")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.THIN);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.THIN);
            cell.setStyle(style);
        }
        else if(leftright.equals("2")){
        	style.setBorderLine(BorderType.TOP, BorderLineType.NONE);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.NONE);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);
        }
        else{
        	style.setBorderLine(BorderType.TOP, BorderLineType.THIN);
        	style.setBorderLine(BorderType.BOTTOM, BorderLineType.THIN);
        	style.setBorderLine(BorderType.LEFT, BorderLineType.NONE);
        	style.setBorderLine(BorderType.RIGHT, BorderLineType.NONE);
            cell.setStyle(style);        	
        }
        
	}
	
	private String setQuery(IStockintransit obj, String sql) 
	{
		Utility util = new Utility();
		String query= "";

		String condition = "";
		String condition1 = "";
		String condition2 = "";
		String conditionCTV = "";
		
		String conditionCHUYENSALE = "";
		String conditionCHUYENSALE_DLPP = "";

		if(obj.gettungay().trim().length() > 0)
		{
			//condition += " and a.NgayDonHang >= '" + obj.gettungay() + "' ";
			condition += " and a.NGAYXUATHD >= '" + obj.gettungay() + "' ";
			condition2 += " and a.NGAYNHAP >= '" + obj.gettungay() + "' ";
			conditionCTV += " and a.NGAYNHAP >= convert( varchar(10), DATEADD(mm, 1, '" + obj.gettungay() + "' ), 120 ) ";
		}
		if(obj.getdenngay().trim().length() > 0)
		{
			//condition += " and a.NgayDonHang <= '" + obj.getdenngay() + "' ";
			condition += " and a.NGAYXUATHD <= '" + obj.getdenngay() + "' ";
			condition2 += " and a.NGAYNHAP <= '" + obj.getdenngay() + "' ";
			conditionCTV += " and a.NGAYNHAP <= convert( varchar(10), DATEADD(mm, 1, '" + obj.getdenngay() + "' ), 120 ) ";
		}
		
		//PHAN QUYEN TDV
		condition += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "dh_sp", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		condition1 += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		condition2 += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCTV += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "dh_sp", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE_DLPP += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		condition += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "dh_sp", "ddkd_fk", obj.getLoainhanvien(), obj.getDoituongId() );

		if(obj.gettungay().trim().length() > 0)
		{
			conditionCHUYENSALE += " and substring( a.NGAYXUATHD, 0, 8 ) = ( select substring( convert( varchar(10), DATEADD(m, -1, '" + obj.gettungay() + "'), 120 ), 0, 8) ) ";
			conditionCHUYENSALE_DLPP += " and substring( a.NGAYNHAP, 0, 8 ) = ( select substring( convert( varchar(10), DATEADD(m, -1, '" + obj.gettungay() + "'), 120 ), 0, 8) ) ";
		}

		String sqlsolo="'' as solo";

		//LẤY TỪ SỔ XUẤT HÀNG ĐÃ CHỐT
		condition += " and a.pk_seq in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where TRANGTHAI != 0 ) ) ";
		conditionCHUYENSALE += " and a.pk_seq in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where TRANGTHAI != 0 ) ) ";
		
		query = "\n select data.KBH_FK, data.KV_FK , SUM(data.SOLUONG *  round( data.dongiagoc * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)) AS TONGDOANHSO "+
				"\n from ( " +
				"\n select data1.tendiaban,donvi,hoadonId,SOHOADON,ngayxuatHD, ngaydonhang, tenkho,loaidonhang,makhachhangOLD,makhachhangNEW,tenkhachhang,ddkdTen,tinhthanhTen,quanhuyenTEN,khuvucTen,vungTen,masanphamOLD,masanphamNEW,tensanpham,solo,SUM(soluong) as soluong,SUM(SoLuong_Chuan) soluong_chuan,dongia,scheme,dongiaGOC,thueVAT,kbhTEN,daily,gsbhTEN,asmTEN, KBH_FK , KV_FK " +
				"\n	from (  ";
		
		query += "\n	 select  dba.ten as tendiaban, dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.NGAYDONHANG, d.TEN as tenkho,     "+
				 "\n			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
				 "\n			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
				 "\n			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
				 "\n			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
				 "\n			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
				 "\n			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
				 "\n			 dh_sp.DONGIA, b.scheme,    "+
				 "\n			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as daily,    "+
				 "\n 		 	 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN,  a.KBH_FK ,  isnull(CAST (kv.PK_SEQ AS NVARCHAR(50)), '') KV_FK  "+
				
				 "\n	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
				 "\n		left join SANPHAM c on b.MA = c.MA  "+
				 "\n		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
				 "\n		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
				 "\n		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
				 "\n		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
				 "\n		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
				 "\n		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK      "+
				 "\n		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
				 "\n		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
				 "\n		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
				 "\n		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
				 "\n		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
				 "\n		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
				 "\n	 left join DONVIDOLUONG dv on dv.PK_SEQ = c.DVDL_FK 	 "+
				 "\n	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and a.chuyenSALES = '0' and dbo.trim( dh_sp.scheme ) = ''    " + condition;
		
		//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
		//query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
		query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";

		sql = query + condition;

		//ĐƠN HÀNG CTV
		sql += " \n  union ALL  "+
				"\n  select '' as tendiaban, dv.diengiai as donvi, a.pk_seq as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD,  a.NGAYNHAP as NGAYDONHANG, N'Hàng bán' as tenkho,    "+
				"\n 		 N'Cộng tác viên' as loaidonhang,   "+
				"\n 		 e.maFAST as makhachhangOLD,    "+
				"\n 		 e.ma as makhachhangNEW,    "+
				"\n 		 e.TEN as tenkhachhang,    "+
				"\n 	 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, '' as khuvucTen, '' as vungTen,   "+
				"\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, '' as SOLO, b.SOLUONG, b.SOLUONG,    "+
				"\n 	 b.DONGIA, '' as scheme, b.dongia as dongiaGOC, 0 thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
				"\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN , khkbh.kbh_fk , '' KV_FK "+
				"\n  from DONHANGCTV a inner join DONHANGCTV_SP b on a.PK_SEQ = b.DONHANGCTV_FK   "+
				"\n 	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ   "+
				"\n 	inner join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
				"\n 	left join CONGTACVIEN ctv on ctv.PK_SEQ = b.CTV_FK     "+
				"\n 	left join TINHTHANH tt on tt.PK_SEQ = e.TINHTHANH_FK   "+
				"\n 	left join QUANHUYEN qh on qh.PK_SEQ = e.QUANHUYEN_FK      "+
				"\n 	left join KHACHHANG_kenhbanhang khkbh on khkbh.KHACHHANG_FK = e.PK_SEQ   "+
				"\n 	left join kenhbanhang kenh on khkbh.kbh_fk = kenh.PK_SEQ   "+
				"\n 	left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
				"\n 	inner join DAIDIENKINHDOANH ddkd on a.DDKD_FK = ddkd.PK_SEQ   "+
				"\n where a.TRANGTHAI in ( 2 )  " + conditionCTV ;

		sql += util.getPhanQuyen_TheoNhanVien("KHACHHANG", "e", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );

		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "pk_seq", obj.getLoainhanvien(), obj.getDoituongId() );


		//ĐƠN HÀNG NPP TRỰC THUỘC HÀNG BÁN
		sql +=  "\n  UNION ALL " + 
				"\n  select  dba.ten as tendiaban,dv.diengiai as donvi, a.pk_seq as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD,  a.NGAYNHAP as NGAYDONHANG, d.TEN as tenkho,   "+
				"\n 		 N'Đơn hàng đại lý' as loaidonhang,   "+
				"\n 		 e.maFAST as makhachhangOLD,    "+
				"\n 		 e.ma as makhachhangNEW,    "+
				"\n 	 e.TEN as tenkhachhang,    "+
				"\n 		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
				"\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham,"+sqlsolo+", b.SOLUONG, b.soluong as SoLuong_Chuan,    "+
				"\n 	 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
				"\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, npp.TEN as daily,   "+
				"\n 	isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN , a.KBH_FK , isnull(CAST (kv.PK_SEQ AS NVARCHAR(50)), '') KV_FK "+
				"\n  from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
				"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
				"\n 	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
				"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
				"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
				"\n 	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
				"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
				"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
				"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
				"\n		left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
				"\n		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
				"\n		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
				"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
				"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
				"\n  where ( a.CS_DUYET = '1' or a.SS_DUYET = '1' ) and a.chuyenSALES = '0'  and isnull(b.scheme, '') = '' and kenh.PK_SEQ in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100001' ) " + condition2+condition1;

		//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
		sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );	
		
		sql += "\n and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";

		sql += "\n UNION ALL " + 
			   "\n	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD,  a.NGAYDONHANG,  d.TEN as tenkho,     "+
			   "\n			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
			   "\n			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
			   "\n			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
			   "\n			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
			   "\n			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
			   "\n			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
			   "\n			 dh_sp.DONGIA, b.scheme,    "+
			   "\n			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as daily,    "+
			   "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN , a.KBH_FK , isnull(CAST (kv.PK_SEQ AS NVARCHAR(50)), '') KV_FK "+
			   "\n	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
			   "\n		left join SANPHAM c on b.MA = c.MA  "+
			   "\n		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
			   "\n		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
			   "\n		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
			   "\n		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
			   "\n		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
			   "\n		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK      "+
			   "\n		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
			   "\n		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
			   "\n		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
			   "\n		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
			   "\n		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
			   "\n		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
			   "\n	 left join DONVIDOLUONG dv on dv.PK_SEQ = c.DVDL_FK 	 "+
			   "\n	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and  dbo.trim( dh_sp.scheme ) = ''  and a.chuyenSALES = '1'  " + conditionCHUYENSALE;
			
		sql += "\n and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";

			
			//CHUYỂN SALES CỦA ĐƠN DLPP
		sql +=  "\n  UNION ALL " + 
				"\n  select  dba.ten as tendiaban,dv.diengiai as donvi, a.PK_SEQ as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD,  a.NGAYNHAP as NGAYDONHANG, d.TEN as tenkho,   "+
				"\n 		 N'Đơn hàng đại lý' as loaidonhang,   "+
				"\n 		 e.maFAST as makhachhangOLD,    "+
				"\n 		 e.ma as makhachhangNEW,    "+
				"\n 	 e.TEN as tenkhachhang,    "+
				"\n 		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
				"\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham,"+sqlsolo+", b.SOLUONG, b.soluong as SoLuong_Chuan,    "+
				"\n 	 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
				"\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, npp.TEN as daily,   "+
				"\n 	isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN , a.KBH_FK , isnull(CAST (kv.PK_SEQ AS NVARCHAR(50)), '') KV_FK "+
				"\n  from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
				"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
				"\n 	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
				"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
				"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
				"\n 	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
				"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
				"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
				"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
				"\n		left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
				"\n		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
				"\n		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
				"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
				"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
				"\n  where ( a.CS_DUYET = '1' or a.SS_DUYET = '1' ) and a.chuyenSALES = '1'  and isnull(b.scheme, '') = '' and kenh.PK_SEQ in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100001' ) " + conditionCHUYENSALE_DLPP;

			//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
			sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";
		
			sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
			
		sql += "\n ) as data1  " +
			   "\n group by data1.tendiaban,donvi,hoadonId,SOHOADON,ngayxuatHD, ngaydonhang, tenkho,loaidonhang,makhachhangOLD,makhachhangNEW,tenkhachhang,ddkdTen,tinhthanhTen,quanhuyenTEN,khuvucTen,vungTen,masanphamOLD,masanphamNEW,tensanpham,solo,dongia,scheme,dongiaGOC,thueVAT,kbhTEN,daily,gsbhTEN,asmTEN, KBH_FK, KV_FK " +
			   "\n ) as data where  data.soluong<>0  " +
			   "\n  GROUP BY data.KBH_FK, data.KV_FK ";
		System.out.println("cau query la  "+sql);

		return sql;
	}	
	
	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		
		Cells cells = worksheet.getCells();	
		
		Cell cell = null;
		
		cell = cells.getCell("D3");		
		cell.setValue("Tháng "+obj.getMonth()+" năm "+obj.getYear() );	
		 		 
		Style style;
		
		
		ResultSet  rs = null;
		
		rs.getString(query);
		
		double QuyDauTu1_kbh_INS = 0;
		double QuyDauTu1_kbh = 0;
		
		double QuyDauTu2_HCM_INS = 0;
		double QuyDauTu2_HN_INS = 0;
		double QuyDauTu2 = 0;
		
		
		if(rs!=null)
		{
			try
			{
				while (rs.next())
				{
					if(rs.getString("KBH_FK").equals("100056")) // KÊNH INS
						QuyDauTu1_kbh_INS += rs.getDouble("TONGDOANHSO");
					else
						QuyDauTu1_kbh += rs.getDouble("TONGDOANHSO");
					
					if(rs.getString("KBH_FK").equals("100056") && rs.getString("KV_FK").equals("100036")) // KÊNH INS + KV HCM
						QuyDauTu2_HCM_INS += rs.getDouble("TONGDOANHSO");
					else if(rs.getString("KBH_FK").equals("100056") && !rs.getString("KV_FK").equals("100036")) // KÊNH INS + KV CÒN LẠI
						QuyDauTu2 += rs.getDouble("TONGDOANHSO");
						
				}
				rs.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
		}
		
		System.out.println("QuyDauTu1_kbh_INS:"+QuyDauTu1_kbh_INS);
		System.out.println("QuyDauTu1_kbh:"+QuyDauTu1_kbh);
		System.out.println("QuyDauTu2_HCM_INS:"+QuyDauTu2_HCM_INS);
		System.out.println("QuyDauTu2:"+QuyDauTu2);
		
		// QUỸ ĐẦU TƯ 1
		
		cell = cells.getCell("D6");		
		cell.setValue(QuyDauTu1_kbh);
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("D7");		
		cell.setValue(QuyDauTu1_kbh_INS);
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F6");
		cell.setFormula("=D6*E6");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F7");
		cell.setFormula("=D7*E7");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("D8");
		cell.setFormula("=D6+D7");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F8");
		cell.setFormula("=F6+F7");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		
		// QUỸ ĐẦU TƯ 2		
		
		
		cell = cells.getCell("D11");		
		cell.setValue("Tháng "+obj.getMonth()+" năm "+obj.getYear() );	
		
		cell = cells.getCell("D15");		
		cell.setValue(QuyDauTu2_HCM_INS);
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("D16");		
		cell.setValue(QuyDauTu2_HN_INS);
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("D18");
		cell.setValue(QuyDauTu2);					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F15");
		cell.setFormula("=D15*E15");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("H15");
		cell.setFormula("=D15*G15");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F16");
		cell.setFormula("=D16*E16");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("H16");
		cell.setFormula("=D16*G16");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("D17");
		cell.setFormula("=D15+D16");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F17");
		cell.setFormula("=F15+F16");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("H17");
		cell.setFormula("=H15+H16");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F18");
		cell.setFormula("=D18*E18");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("H18");
		cell.setFormula("=D18*G18");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("D19");
		cell.setFormula("=D17+D18");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("F19");
		cell.setFormula("=F17+F18");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("D19");
		cell.setFormula("=D17+D18");					
		style = cell.getStyle();
		cell.setStyle(style);
		
		cell = cells.getCell("H19");
		cell.setFormula("=H17+H18");					
		style = cell.getStyle();
		cell.setStyle(style);
				
		if(db != null)
			db.shutDown();
		
		return true;
			
	}	
	
	private void setCellBorderStyle(Cell cell, short alignment) 
	{
		Style style = cell.getStyle();
		//style.setHAlignment(HorizontalAlignmentType.CENTRED);
		style.setHAlignment(alignment);
		/*style.setBorderLine(BorderType.TOP, 1);
		style.setBorderLine(BorderType.RIGHT, 1);
		style.setBorderLine(BorderType.BOTTOM, 1);
		style.setBorderLine(BorderType.LEFT, 1);*/
		cell.setStyle(style);
	}
}
