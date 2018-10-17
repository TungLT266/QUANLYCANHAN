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
import java.sql.ResultSetMetaData;
import java.sql.Types;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.BorderLineType;
import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;
import com.aspose.cells.TextAlignmentType;

import com.aspose.cells.Style;

import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class BCBanHangTDV extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public BCBanHangTDV()
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
		String nextJSP = "/TraphacoHYERP/pages/Center/BCBanHangTDV.jsp";
		
		   String view = request.getParameter("view");
		    if(view == null)
		    	view = "";
		response.sendRedirect(nextJSP);   
		/*if(!view.equals("TT"))
		{
			nextJSP = "/TraphacoHYERP/pages/Distributor/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			 nextJSP = "/TraphacoHYERP/pages/Center/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}*/
		
		
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

		String userId = request.getParameter("userId");
		String view=request.getParameter("view");
		if(view == null)
			view = "";
		
	    String nppId ="";
		if(view.equals("TT"))
		{
			 nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		}else
		{
			nppId=util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}
     	
	

		String action = util.antiSQLInspection(request.getParameter("action"));
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=BaoCaoBanHangTDV_TQ.xlsm");
				OutputStream out = response.getOutputStream();
				
				String query = setQuery(obj,request,"100002");
				ExportToExcel(out, obj, query);
			} catch (Exception ex)
			{
				ex.printStackTrace();
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());
				obj.setMsg(ex.getMessage());
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());
		}
		String nextJSP = "/TraphacoHYERP/pages/Center/BCBanHangTDV.jsp";
		response.sendRedirect(nextJSP);
		
		/*if(!view.equals("TT"))
		{
			String	nextJSP = "/TraphacoHYERP/pages/Distributor/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String	 nextJSP = "/TraphacoHYERP/pages/Center/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}*/
		
	}



	private String setQuery(IStockintransit obj,HttpServletRequest request,String vung_fk)
	{
		String view = request.getParameter("view");
		if (view == null)
			view = "";
		String query = 
					// Khai báo 
						 "\n declare @chucvu varchar(10) = 'TDV'  " + 
						 "\n declare @colCT nvarchar(max),@colTD nvarchar(max),@colCTKM nvarchar(max),@colCTKM_Pivot nvarchar(max),@colCT_Value nvarchar(max),@colTD_Value nvarchar(max)  " + 
						 "\n declare @thang int ="+ obj.getMonth() + 
						 "\n declare @nam int = "+obj.getYear()+ 
						 "\n declare @dauthang varchar(10) ='"+( obj.getYear() +"-" + (obj.getMonth().trim().length()< 2? "0"+obj.getMonth():obj.getMonth()+"" ) +"-01"  )+"'  " + 
						 "\n declare @cuoithang varchar(10)   = CONVERT(char(10),DATEADD(DAY,-(DAY(@dauthang)),DATEADD(MONTH,1,@dauthang)),120)  " + 
						 "\n SET @colCT =ISNULL(  STUFF(  " + 
						 "\n 		(SELECT distinct ',' +  QUOTENAME('CT_' +nsp.DIENGIAI )   " + 
						 "\n             from CHITIEUSEC_NHANVIEN_NHOMSP ct_nhom inner join CHITIEU_NHANVIEN_NPP ct on ct_nhom.CHITIEUSEC_NV_FK = ct.PK_SEQ  " + 
						 "\n 				inner join NHOMSANPHAM nsp on ct_nhom.NHOMSP_FK = nsp.PK_SEQ  " + 
						 "\n 		where ct.TRANGTHAI =@thang and ct.NAM = @nam and CHUCVU = 'TDV' and ct_nhom.THANG = 1  " + 
						 "\n             FOR XML PATH(''), TYPE  " + 
						 "\n             ).value('.', 'NVARCHAR(MAX)')   " + 
						 "\n         ,1,1,'') +',[CT_KHAC],[CT_DS KHOAN THANG]' ,'[CT_KHAC],[CT_DS KHOAN THANG]')    " + 

						 
						 "\n SET @colCT_Value =ISNULL(  STUFF(  " + 
						 "\n 		(SELECT distinct ',' + 'isnull(' +  QUOTENAME('CT_' +nsp.DIENGIAI ) +',0) '+ QUOTENAME(' CT_'+ nsp.DIENGIAI)  " + 
						 "\n             from CHITIEUSEC_NHANVIEN_NHOMSP ct_nhom inner join CHITIEU_NHANVIEN_NPP ct on ct_nhom.CHITIEUSEC_NV_FK = ct.PK_SEQ  " + 
						 "\n 				inner join NHOMSANPHAM nsp on ct_nhom.NHOMSP_FK = nsp.PK_SEQ  " + 
						 "\n 		where ct.TRANGTHAI =@thang and ct.NAM = @nam and CHUCVU = 'TDV' and ct_nhom.THANG = 1  " + 
						 "\n             FOR XML PATH(''), TYPE  " + 
						 "\n             ).value('.', 'NVARCHAR(MAX)')   " + 
						 "\n         ,1,1,'') +',isnull([CT_KHAC],0) [CT_KHAC],isnull([CT_DS KHOAN THANG],0)[CT_DS KHOAN THANG]' ,'isnull([CT_KHAC],0) [CT_KHAC],isnull([CT_DS KHOAN THANG],0)[CT_DS KHOAN THANG]')             " + 

						 
						 "\n  SET @colTD = ISNULL(STUFF(  " + 
						 "\n 		(SELECT distinct ',' + QUOTENAME('TD_' + nsp.DIENGIAI )   " + 
						 "\n             from CHITIEUSEC_NHANVIEN_NHOMSP ct_nhom inner join CHITIEU_NHANVIEN_NPP ct on ct_nhom.CHITIEUSEC_NV_FK = ct.PK_SEQ  " + 
						 "\n 				inner join NHOMSANPHAM nsp on ct_nhom.NHOMSP_FK = nsp.PK_SEQ  " + 
						 "\n 		where ct.TRANGTHAI = @thang and ct.NAM = @nam and CHUCVU = 'TDV' and ct_nhom.THANG = 1  " + 
						 "\n             FOR XML PATH(''), TYPE  " + 
						 "\n             ).value('.', 'NVARCHAR(MAX)')   " + 
						 "\n         ,1,1,'')  +',[TD_KHAC],[TD_DS KHOAN THANG]' ,'[TD_KHAC],[TD_DS KHOAN THANG]')   " + 
						 "\n    " + 
						 "\n  SET @colTD_Value =ISNULL(  STUFF(  " + 
						 "\n 		(SELECT distinct ',' + 'isnull(' +  QUOTENAME('TD_' +nsp.DIENGIAI ) +',0) '+ QUOTENAME(' TD_'+ nsp.DIENGIAI)  " + 
						 "\n             from CHITIEUSEC_NHANVIEN_NHOMSP ct_nhom inner join CHITIEU_NHANVIEN_NPP ct on ct_nhom.CHITIEUSEC_NV_FK = ct.PK_SEQ  " + 
						 "\n 				inner join NHOMSANPHAM nsp on ct_nhom.NHOMSP_FK = nsp.PK_SEQ  " + 
						 "\n 		where ct.TRANGTHAI =@thang and ct.NAM = @nam and CHUCVU = 'TDV' and ct_nhom.THANG = 1  " + 
						 "\n             FOR XML PATH(''), TYPE  " + 
						 "\n             ).value('.', 'NVARCHAR(MAX)')   " + 
						 "\n         ,1,1,'') +',isnull([TD_KHAC],0) [TD_KHAC],isnull([TD_DS KHOAN THANG],0)[TD_DS KHOAN THANG]' ,'isnull([TD_KHAC],0) [TD_KHAC],isnull([TD_DS KHOAN THANG],0)[TD_DS KHOAN THANG]')             " + 

						 
						 "\n    SET @colCTKM =STUFF(  " + 
						 "\n 		(SELECT distinct ',' + 'isnull(' +QUOTENAME('DH_' + SCHEME )+',0) '+ QUOTENAME(' DH_'+ SCHEME)    " + 
						 "\n 						 + ',' + 'isnull(' +QUOTENAME('NT_' + SCHEME )+',0) '+ QUOTENAME(' NT_'+ SCHEME)    " + 
						 "\n 						 + ',' + 'isnull(' +QUOTENAME('DS_' + SCHEME )+',0) '+ QUOTENAME(' DS_'+ SCHEME)    " + 
						 "\n             from CTKHUYENMAI  " + 
						 "\n             FOR XML PATH(''), TYPE  " + 
						 "\n             ).value('.', 'NVARCHAR(MAX)')   " + 
						 "\n         ,1,1,'')     " + 
						 "\n      " + 
						 "\n  set  @colCTKM_Pivot =STUFF(  " + 
						 "\n 		(SELECT distinct ',' + QUOTENAME('PivotStyle_' + SCHEME )   " + 
						 "\n             from CTKHUYENMAI  " + 
						 "\n             FOR XML PATH(''), TYPE  " + 
						 "\n             ).value('.', 'NVARCHAR(MAX)')   " + 
						 "\n         ,1,1,'')     " + 

						 
						// câu query  
						 "\n declare @sql nvarchar(max)  " + 
						 "\n   " + 
						 "\n set @sql =  " + 
						 "\n N' select ROW_NUMBER ( )OVER (  PARTITION BY  1 order by v.kv  ) [STT] ,v.kv as [KV], ddkd.TEN,kbh.KENH ,''NA'' as CV ,db.ten as [Địa bàn] , '+@colCT_Value+', '+@colTD_Value+', case when isnull([CT_DS KHOAN THANG],0) = 0 then 0 else isnull([TD_DS KHOAN THANG],0) /isnull([CT_DS KHOAN THANG],0)end as [%TH/KH] ,  " + 
						 "\n 	 isnull( DP.sodonhang,0)  [D HANG],isnull(DP.sonhathuoc,0) [N THUOC]  , '+@colCTKM+'  " + 
						 "\n from DAIDIENKINHDOANH ddkd   " +
						 "\n INNER JOIN	" +
						 "\n ( SELECT distinct ddkd_fk, b.ten as KENH FROM DAIDIENKINHDOANH_KBH a	" +
						 "\n	inner join kenhbanhang b on b.pK_seq = a.kbh_fk " +
						 "\n ) kbh on kbh.ddkd_fk = ddkd.PK_SEQ	" + 
						 "\n  inner join  " + 
						 "\n  (  " + 
						 "\n 	select distinct a.ddkd_fk,c.VUNG_FK, c.ten as kv from DAIDIENKINHDOANH_NPP a   " + 
						 "\n 	inner join NHAPHANPHOI b on a.npp_fk = b.PK_SEQ  " + 
						 "\n 	inner join KHUVUC c on c.PK_SEQ = b.KHUVUC_FK and c.VUNG_FK ="+vung_fk+"  " + 
						 "\n  )  v on ddkd.PK_SEQ = v.ddkd_fk  " +
						 "\n  inner join diaban db on db.pK_seq = ddkd.diaban_fk	" + 
						 "\n left join  " + 
						 "\n (  " + 
						 "\n 	select * from  " + 
						 "\n 	(  " +
						 // chi tiêu nhóm
						 "\n 		select NV_FK as ddkd_fk,  ''CT_'' +nsp.DIENGIAI as nhom, sum(ct_nhom.CHITIEU) as chitieu  " + 
						 "\n 		from CHITIEUSEC_NHANVIEN_NHOMSP ct_nhom inner join CHITIEU_NHANVIEN_NPP ct on ct_nhom.CHITIEUSEC_NV_FK = ct.PK_SEQ  " + 
						 "\n 				inner join NHOMSANPHAM nsp on ct_nhom.NHOMSP_FK = nsp.PK_SEQ  " + 
						 "\n 		where ct.TRANGTHAI = 1 and ct.NAM = '+CAST(@nam as varchar)+' and CHUCVU = '''+ @chucvu +''' and ct_nhom.THANG = '+CAST(@thang as varchar)+'  " + 
						 "\n 		group by NV_FK, nsp.DIENGIAI  " + 
						 "\n  		union all  " + // chitiêu DS tổng
						 "\n  		select  NhanVien_FK as ddkd_fk,  ''CT_DS KHOAN THANG'' as nhom, chitieubanra as chitieu  " + 
						 "\n  		from CHITIEU_NV_CHITIET ctnv inner join  CHITIEU_NHANVIEN_NPP ct on  ct.PK_SEQ = ctnv.CHITIEU_NV_FK  " + 
						 "\n  		where ct.TRANGTHAI = 1 and ct.NAM = '+CAST(@nam as varchar)+' and CHUCVU = '''+ @chucvu +''' and ctnv.THANG = '+CAST(@thang as varchar)+'  " + 
						 "\n  		union all  " + //Chỉ tiêu DS tổng - DS nhóm
						 "\n  		select  NhanVien_FK as ddkd_fk,  ''CT_KHAC'' as nhom  " + 
						 "\n  				, isnull(chitieubanra,0) - (select sum(CHITIEU) from CHITIEUSEC_NHANVIEN_NHOMSP where CHITIEUSEC_NV_FK = ct.PK_SEQ  and CHUCVU = '''+ @chucvu +''' and THANG = '+CAST(@thang as varchar)+'      )    as chitieu  " + 
						 "\n  		from CHITIEU_NV_CHITIET ctnv inner join  CHITIEU_NHANVIEN_NPP ct on  ct.PK_SEQ = ctnv.CHITIEU_NV_FK  " + 
						 "\n  		where ct.TRANGTHAI = 1 and ct.NAM = '+CAST(@nam as varchar)+' and CHUCVU = '''+ @chucvu +''' and ctnv.THANG = '+CAST(@thang as varchar)+'  " + 	
						 "\n 	)  " + 
						 "\n 	dt PIVOT ( SUM(chitieu) FOR nhom IN ( ' + @colCT + ' ) ) AS pvt  " + 
						 "\n )  " + 
						 "\n CT on ddkd.PK_SEQ = CT.ddkd_fk left join  " + 
						 "\n (  " + 
						 "\n 	select * from  " + // thực đạt DS nhóm
						 "\n 	(  " + 
						 "\n 		select ddh.DDKD_FK, ''TD_'' + nsp.DIENGIAI as nhom, SUM( hd_sp.SOLUONG * hd_sp.DONGIA ) as thucdat  " + 
						 "\n 		from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK  " + 
						 "\n 		  inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ  " + 
						 "\n 		  inner join ERP_HOADONNPP_SP hd_sp on hd.PK_SEQ = hd_sp.HOADON_FK  " + 
						 "\n 		  inner join SANPHAM sp on hd_sp.SANPHAM_FK = sp.PK_SEQ  " + 
						 "\n 		  inner join NHOMSANPHAM_SANPHAM nsp_sp on sp.PK_SEQ = nsp_sp.SP_FK  " + 
						 "\n 		  inner join NHOMSANPHAM nsp on nsp_sp.NSP_FK = nsp.PK_SEQ  " + 
						 "\n 		where hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null and nsp.tinhthuong = 1  " + 
						 "\n 			and hd.NgayXuatHD >='''+ @dauthang +''' and  hd.NgayXuatHD <='''+ @cuoithang +'''  " + 
						 "\n 		group by ddh.DDKD_FK, nsp.DIENGIAI  " + 
						 
						 "\n  		union all  " +  // thực đạt DS tổng
						 "\n  		  " + 
						 "\n  		select ddh.DDKD_FK, ''TD_DS KHOAN THANG''  as nhom, SUM( hd_sp.SOLUONG * hd_sp.DONGIA ) as thucdat    " + 
						 "\n  		from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK    " + 
						 "\n  		inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ    " + 
						 "\n  		inner join ERP_HOADONNPP_SP hd_sp on hd.PK_SEQ = hd_sp.HOADON_FK    " + 
						 "\n  		where hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null   " + 
						 "\n  		and hd.NgayXuatHD >='''+ @dauthang +''' and  hd.NgayXuatHD <='''+ @cuoithang +'''    " + 
						 "\n  		group by ddh.DDKD_FK  " + 
						 "\n  		union all  " +  // thực đạt DS tổng - DS nhóm
						 "\n  		  " + 
						 "\n  		select ddh.DDKD_FK, ''TD_KHAC''  as nhom, SUM( hd_sp.SOLUONG * hd_sp.DONGIA ) as thucdat    " + 
						 "\n  		from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK    " + 
						 "\n  		  inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ    " + 
						 "\n  		  inner join ERP_HOADONNPP_SP hd_sp on hd.PK_SEQ = hd_sp.HOADON_FK    " + 
						 "\n  		  where  hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null   " + 
						 "\n  			and hd.NgayXuatHD >='''+ @dauthang +''' and  hd.NgayXuatHD <='''+ @cuoithang +'''    " + 
						 "\n  			and hd_sp.SANPHAM_FK not in  " + 
						 "\n  			(  " + 
						 "\n  				select SANPHAM_FK from NHOMSANPHAM_SANPHAM nsp_sp  " + 
						 "\n  								inner join NHOMSANPHAM nsp on nsp_sp.NSP_FK = nsp.PK_SEQ  " + 
						 "\n  								inner join CHITIEUSEC_NHANVIEN_NHOMSP ct_nhom on ct_nhom.NHOMSP_FK = nsp.PK_SEQ    " + 
						 "\n  								inner join  CHITIEU_NHANVIEN_NPP ct on ct_nhom.CHITIEUSEC_NV_FK = ct.PK_SEQ    " + 
						 "\n  								where  ct.TRANGTHAI = 1 and ct.NAM = '+CAST(@nam as varchar)+' and CHUCVU = '''+ @chucvu +''' and ct_nhom.THANG = '+CAST(@thang as varchar)+'    " + 
						 "\n  										and  ct_nhom.NV_FK = ddh.DDKD_FK  " + 
						 "\n  			)  " + 
						 "\n  			  " + 
						 "\n  		group by ddh.DDKD_FK  " + 
						 
						 
						 "\n 	)  " + 
						 "\n 	dt PIVOT ( SUM(thucdat) FOR nhom IN ( '+@colTD+' ) ) AS pvt  " + 
						 "\n )  " + 
						 "\n TD on ddkd.PK_SEQ = TD.ddkd_fk  " + 
						 "\n left join  " + // số đơn hàng, độ phủ
						 "\n (  " + 
						 "\n 	select ddh.DDKD_FK, COUNT(ddh.PK_SEQ) as sodonhang, COUNT(distinct ddh.khachhang_fk) as sonhathuoc  " + 
						 "\n 	from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK  " + 
						 "\n 	  inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ  " + 
						 "\n 	  inner join ERP_HOADONNPP_SP hd_sp on hd.PK_SEQ = hd_sp.HOADON_FK  " + 
						 "\n 	where hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null and ddh.LoaiDonHang != 0  " +
						 "\n			and hd.NgayXuatHD >='''+ @dauthang +''' and  hd.NgayXuatHD <='''+ @cuoithang +''' " + 
						 "\n 	group by ddh.DDKD_FK  " + 
						 "\n )  " + 
						 "\n DP on ddkd.PK_SEQ = DP.ddkd_fk   " + 
						 "\n left join  " +  // DS các CTKM đang chạy
						 "\n (  " + 
						 "\n 	select * from  " + 
						 "\n 	(  " + 
						 "\n 		select ddh.DDKD_FK, ''DS'' + ctkm.SCHEME as  scheme, 				  " + 
						 "\n 				SUM( ddh_ctkm.TONGGIATRI ) as DSOCTKM  " + 
						 "\n 		from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK  " + 
						 "\n 		  inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ  " + 
						 "\n 		  inner join ERP_DONDATHANGNPP_CTKM_TRAKM ddh_ctkm on ddh.PK_SEQ = ddh_ctkm.DONDATHANGID  " + 
						 "\n 		  inner join CTKHUYENMAI ctkm on ddh_ctkm.CTKMID = ctkm.PK_SEQ  " + 
						 "\n 		where hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null   " +
						 "\n			and hd.NgayXuatHD >='''+ @dauthang +''' and  hd.NgayXuatHD <='''+ @cuoithang +''' " + 
						 "\n 		group by ddh.DDKD_FK, ctkm.SCHEME  " + 
						 "\n 	)  " + 
						 "\n 	dt PIVOT ( SUM(DSOCTKM) FOR scheme IN ( '+REPLACE( @colCTKM_Pivot,'PivotStyle_','DS_')+' ) ) AS pvt	  " + 
						 "\n )  " + 
						 "\n CTKMDS on ddkd.PK_SEQ = CTKMDS.ddkd_fk   " + 
						 "\n left join  " +  // số đơn hàng các CTKM đang chạy
						 "\n (  " + 
						 "\n 	select * from  " + 
						 "\n 	(  " + 
						 "\n 		select ddh.DDKD_FK, ''DH_''  + ctkm.SCHEME  as  scheme,   " + 
						 "\n 				COUNT(ddh.PK_SEQ) as sodonhang  " + 
						 "\n 		from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK  " + 
						 "\n 		  inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ  " + 
						 "\n 		  inner join ERP_DONDATHANGNPP_CTKM_TRAKM ddh_ctkm on ddh.PK_SEQ = ddh_ctkm.DONDATHANGID  " + 
						 "\n 		  inner join CTKHUYENMAI ctkm on ddh_ctkm.CTKMID = ctkm.PK_SEQ  " + 
						 "\n 		where hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null   " +
						 "\n			and hd.NgayXuatHD >='''+ @dauthang +''' and  hd.NgayXuatHD <='''+ @cuoithang +''' 	" + 
						 "\n 		group by ddh.DDKD_FK, ctkm.SCHEME  " + 
						 "\n 	)  " + 
						 "\n 	dt PIVOT ( SUM(sodonhang) FOR scheme IN ( '+REPLACE( @colCTKM_Pivot,'PivotStyle_','DH_')+' ) ) AS pvt	  " + 
						 "\n )  " + 
						 "\n CTKMDH on ddkd.PK_SEQ = CTKMDH.ddkd_fk   " + 
						 "\n left join  " + //  độ phủ các CTKM đang chạy
						 "\n (  " + 
						 "\n 	select * from  " + 
						 "\n 	(  " + 
						 "\n 		select ddh.DDKD_FK,  ''NT_''  + ctkm.SCHEME as scheme,   " + 
						 "\n 				 COUNT(distinct ddh.khachhang_fk) as sonhathuoc  " + 
						 "\n   " + 
						 "\n 		from ERP_HOADONNPP hd inner join ERP_HOADONNPP_DDH hd_dh on hd.PK_SEQ = hd_dh.HOADONNPP_FK  " + 
						 "\n 		  inner join ERP_DONDATHANGNPP ddh on  hd_dh.DDH_FK = ddh.PK_SEQ  " + 
						 "\n 		  inner join ERP_DONDATHANGNPP_CTKM_TRAKM ddh_ctkm on ddh.PK_SEQ = ddh_ctkm.DONDATHANGID  " + 
						 "\n 		  inner join CTKHUYENMAI ctkm on ddh_ctkm.CTKMID = ctkm.PK_SEQ  " + 
						 "\n 		where hd.TRANGTHAI = 4 and ddh.DDKD_FK is not null  " +
						 "\n			and hd.NgayXuatHD >='''+ @dauthang +''' and  hd.NgayXuatHD <='''+ @cuoithang +'''	 " + 
						 "\n 		group by ddh.DDKD_FK, ctkm.SCHEME  " + 
						 "\n 	)  " + 
						 "\n 	dt PIVOT ( SUM(sonhathuoc) FOR scheme IN ( '+REPLACE( @colCTKM_Pivot,'PivotStyle_','NT_')+' ) ) AS pvt	  " + 
						 "\n )  " + 
						 "\n CTKMNT on ddkd.PK_SEQ = CTKMNT.ddkd_fk '" +
						 "\n  execute (@sql)   " ;		

			System.out.println("1.Query SalesRep : " + query);
		return query;
	}

	private void TaoBaoCao(Workbook workbook,IStockintransit obj,String query )throws Exception
	{
		try{
			
			
			Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");

			Cells cells = worksheet.getCells();
			cells.setRowHeight(0, 50.0f);
			Cell cell = cells.getCell("A1");
			ReportAPI.getCellStyle(cell, Color.RED, true, 16,"Bán hàng TDV Toàn quốc");

			cell = cells.getCell("A3");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10, "Ngày tạo : "+ obj.getDateTime());
			cell = cells.getCell("A4");
			ReportAPI.getCellStyle(cell, Color.BLUE, true, 10,"Người tạo : " + obj.getuserTen());

			worksheet.setGridlinesVisible(false);
			dbutils db = new dbutils();
			ResultSet rs = db.get(query);
			
			ResultSetMetaData rsmd = rs.getMetaData();
			int socottrongSql = rsmd.getColumnCount();
			
			int countRow = 8;
			int cotbatdauChiTieu =0;
			int cotketthucChiTieu =0;
			int cotbatdauThucDat =0;
			int cotketthucThucDat =0;
			int cotbatdaudonhang = 0;
			int cotbatdaukhuyenmai = 0;
			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				if(cotbatdauChiTieu == 0 && rsmd.getColumnName(i).indexOf("CT_") >=0 )
					cotbatdauChiTieu = i;
				if(cotbatdauThucDat == 0 && rsmd.getColumnName(i).indexOf("TD_") >=0 )
				{	
					cotketthucChiTieu = i-1;
					cotbatdauThucDat = i;
				}
				if(rsmd.getColumnName(i).indexOf("%TH/KH") >=0)
				{
					cotketthucThucDat = i;
					cotbatdaudonhang = i +1;
					cotbatdaukhuyenmai = i +3;
				}
				
				String columnName = rsmd.getColumnName(i);
				columnName = columnName.replace("CT_", "").replace("TD_", "");
				if(columnName.indexOf("DH_")>=0)
					columnName = "Đ HANG";
				else if(columnName.indexOf("NT_")>=0)
					columnName = "N THUOC";
				else if(columnName.indexOf("DS_")>=0)
					columnName = "DSO CTKM";
				
				cell = cells.getCell(countRow,i-1 );cell.setValue(columnName);
			 
			}
			
			cells.merge(countRow -1 , cotbatdauThucDat - 1, countRow -1  ,cotketthucThucDat- 1 );cell = cells.getCell(countRow -1 ,cotbatdauThucDat - 1);
			cell.setValue("DSO THUC HIEN UPDATE "+ (obj.getYear() +"-" + obj.getMonth()));		
			ReportAPI.setCellBackground(cell,  new Color(242,221,220), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
			ReportAPI.setBorder_Style_MergerCell(cells,countRow -1 ,countRow -1 ,cotbatdauThucDat - 1,cotketthucThucDat - 1,BorderLineType.THIN,Color.BLACK,cell.getStyle());

			cells.merge(countRow -1 , cotbatdaudonhang - 1, countRow -1  ,cotbatdaudonhang  );cell = cells.getCell(countRow -1 ,cotbatdaudonhang - 1);
			cell.setValue("SO LUONG	");		
			ReportAPI.setCellBackground(cell,  new Color(215,228,188), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
			ReportAPI.setBorder_Style_MergerCell(cells,countRow -1 ,countRow -1 ,cotbatdaudonhang -1 ,cotbatdaudonhang,BorderLineType.THIN,Color.BLACK,cell.getStyle());
			for(int i =cotbatdaukhuyenmai ; i <= socottrongSql;i++)
			{
				if((socottrongSql - i)%3==2 )
				{
					cells.merge(countRow -1 , i - 1, countRow -1  ,i + 1 );cell = cells.getCell(countRow -1 ,i - 1);
					cell.setValue(rsmd.getColumnName(i).replace("DH_", ""));		
					ReportAPI.setCellBackground(cell,  new Color(252,213,180), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
					ReportAPI.setBorder_Style_MergerCell(cells,countRow -1 ,countRow -1 ,i -1,i + 1,BorderLineType.THIN,Color.BLACK,cell.getStyle());
				}
			}
			

			for( int i =1 ; i <=socottrongSql ; i ++  )
			{
				cell = cells.getCell(countRow,i-1 );
				if(i <= 6)
					ReportAPI.setCellBackground(cell, new Color(146,208,80), BorderLineType.THIN, true, 0);	
				else if( i >= cotbatdauChiTieu && i <= cotketthucChiTieu )
					ReportAPI.setCellBackground(cell, new Color(141,180,227), BorderLineType.THIN, true, 0);
				else if( i >= cotbatdauThucDat && i <= cotketthucThucDat )
					ReportAPI.setCellBackground(cell, new Color(242,221,220), BorderLineType.THIN, true, 0);
				else if( i >= cotbatdaudonhang && i <= cotbatdaudonhang + 1 )
					ReportAPI.setCellBackground(cell, new Color(215,228,188), BorderLineType.THIN, true, 0);
				else if( i >= cotbatdaukhuyenmai  )
					ReportAPI.setCellBackground(cell, new Color(252,213,180), BorderLineType.THIN, true, 0);
			}
			List<Integer> listKV = new ArrayList<Integer>();
			
			
			countRow ++;
			
			String kvOld ="";
			int dongbatdautable = countRow;
			int batdaukhuvuc = countRow;
			int ketthuckhuvuc = 0;
			
			while(rs.next())
			{
				if(!kvOld.equals(rs.getString("KV")) && countRow >9 )
				{
					listKV.add(countRow);
					cells.merge(countRow , 0, countRow ,5 );cell = cells.getCell(countRow  ,0);
					cell.setValue(kvOld + "TOTAL" );		
					ReportAPI.setCellBackground(cell,  new Color(197,217,241), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
					ReportAPI.setBorder_Style_MergerCell(cells,countRow ,countRow ,0,5,BorderLineType.THIN,Color.BLACK,cell.getStyle());
					
					for(int i =7;i <=socottrongSql ; i ++)
					{
						
						
						cell = cells.getCell(countRow,i-1 );
						String formula = "=SUM("+ReportAPI.GetExcelColumnName(i)+(batdaukhuvuc+1 ) +":"+ReportAPI.GetExcelColumnName(i)+(countRow)+")";
						cell.setFormula(formula);
						ReportAPI.setCellBackground(cell, new Color(197,217,241), BorderLineType.THIN, false, 41);
					}
					++countRow;
					batdaukhuvuc = countRow;
				}
				kvOld = rs.getString("KV");
				
				for(int i =1;i <=socottrongSql ; i ++)
				{
					
					cell = cells.getCell(countRow,i-1 );
					if(rsmd.getColumnType(i) == Types.DOUBLE ||rsmd.getColumnType(i) == Types.INTEGER||rsmd.getColumnType(i) == Types.NUMERIC)
					{
						System.out.println(" Types.DOUBLE||||||||||  = "  +  rsmd.getColumnType(i) );
						cell.setValue(rs.getDouble(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 41);
					}
					else
					{
						cell.setValue(rs.getString(i));
						ReportAPI.setCellBackground(cell, Color.WHITE, BorderLineType.THIN, false, 0);
					}
				}
				++countRow;
			}
			
			if(countRow >9 )
			{
				listKV.add(countRow);
				cells.merge(countRow , 0, countRow ,5 );cell = cells.getCell(countRow  ,0);
				cell.setValue(kvOld + "TOTAL" );		
				ReportAPI.setCellBackground(cell,  new Color(197,217,241), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
				ReportAPI.setBorder_Style_MergerCell(cells,countRow ,countRow ,0,5,BorderLineType.THIN,Color.BLACK,cell.getStyle());
				for(int i =7;i <=socottrongSql ; i ++)
				{
					
					cell = cells.getCell(countRow,i-1 );
					String formula = "=SUM("+ReportAPI.GetExcelColumnName(i)+(batdaukhuvuc+1 ) +":"+ReportAPI.GetExcelColumnName(i)+(countRow)+")";
					cell.setFormula(formula);
					ReportAPI.setCellBackground(cell, new Color(197,217,241), BorderLineType.THIN, false, 41);
				}
				++countRow;
				
				
				
				cells.merge(countRow , 0, countRow ,5 );cell = cells.getCell(countRow  ,0);
				cell.setValue( "MIEN NAM" );		
				ReportAPI.setCellBackground(cell,  new Color(0,176,80), BorderLineType.THIN, true, 0,TextAlignmentType.CENTER);
				ReportAPI.setBorder_Style_MergerCell(cells,countRow ,countRow ,0,5,BorderLineType.THIN,Color.BLACK,cell.getStyle());
				for(int i =7;i <=socottrongSql ; i ++)
				{
					
					cell = cells.getCell(countRow,i-1 );
					String formula ="=";
					for( int k=0 ; k < listKV.size(); k ++)
					{
						if(k ==0) formula+=ReportAPI.GetExcelColumnName(i)+(listKV.get(k)+1 ) ;
						else formula+=" + " +ReportAPI.GetExcelColumnName(i)+(listKV.get(k)+1 ) ;
					}
					System.out.println("fômrla=" + formula);
					cell.setFormula(formula);
					ReportAPI.setCellBackground(cell, new Color(0,176,80), BorderLineType.THIN, false, 41);
				}
				++countRow;

				
			}
			
			if(rs!=null)rs.close();
			if(db!=null){
				db.shutDown();
			}

	
		}catch(Exception ex){
			
			System.out.println("Errrorr : "+ex.toString());
			throw new Exception("Qua trinh dien du lieu vao file Excel va tao PivotTable bi loi.!!!");
		}
	}
	
	private void ExportToExcel(OutputStream out,IStockintransit obj,String query )throws Exception
	 {
		try{ 			
			Workbook workbook = new Workbook();
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			TaoBaoCao(workbook,obj,query);		
			workbook.save(out);	

		}catch(Exception ex){
			throw new Exception(ex.getMessage());
		}
		
	}
	
	
	
}
