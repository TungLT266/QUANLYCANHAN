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
import java.sql.SQLException;
import java.util.Hashtable;

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

public class SrperformanceGroupSku extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public SrperformanceGroupSku()
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
		String nextJSP = "/TraphacoHYERP/pages/Center/SalesrepPerfomanceGroupSku.jsp";
		
		   String view = request.getParameter("view");
		    if(view == null)
		    	view = "";
		    
		if(!view.equals("TT"))
		{
			nextJSP = "/TraphacoHYERP/pages/Distributor/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			 nextJSP = "/TraphacoHYERP/pages/Center/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}
		
		
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
		//	String query = setQuery(obj,request);
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieuSR_" + util.setTieuDe(obj) + ".xlsm");
				OutputStream out = response.getOutputStream();
				
				String query = setQuery(obj,request);
				//String query="";
				CreatePivotTable(out, obj, query);
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
		if(!view.equals("TT"))
		{
			String	nextJSP = "/TraphacoHYERP/pages/Distributor/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String	 nextJSP = "/TraphacoHYERP/pages/Center/SalesrepPerfomanceGroupSku.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}



	private String setQuery(IStockintransit obj,HttpServletRequest request)
	{
		String view = request.getParameter("view");
		if (view == null)
			view = "";
		
		String condition ="";
		String conditionTDV ="";
		if(obj.getnppId().length()>0)
		{
			if (!view.equals("TT"))
				conditionTDV+="  and npp_fk='"+obj.getnppId()+"'";
			
		}
		
		if(obj.getkenhId().length()>0)
		{
			conditionTDV+=" and HD.kbh_fk="+obj.getkenhId();
			condition+=" and C.KENH_FK="+obj.getkenhId();
		}
		if(obj.getkhuvucId().length()>0)
		{
			condition+="  and kv.pk_seq='"+obj.getvungId()+"'";
		}
		
		String condition_CT ="";
	
		String query = "";
		String sql_header="select PK_SEQ,TEN from NHOMSANPHAM where tinhthuong=1";
		String sku= "select b.sanpham_fk,c.TEN from THUONGDAUTHUNG a \n"+
			    "inner join ThuongDauThung_Sp b on a.PK_SEQ=b.Thuong_fk  \n"+
			    "inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk \n"+
			    "where  a.thang="+obj.getMonth()+" and a.nam="+obj.getYear()+" and a.TRANGTHAI=1";
			dbutils db=new dbutils();
			String headersku="";
			String tsku="";
			ResultSet rshsk=db.get(sku);
			try {
				while(rshsk.next())
				{
					headersku+=",Tsku.["+ rshsk.getString("sanpham_fk")+"] as Tsku"+rshsk.getString("sanpham_fk");
					tsku+=",["+ rshsk.getString("sanpham_fk")+"]";
					System.out.println("__________"+tsku);
				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			try {
				rshsk.close();
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			ResultSet rsh=db.get(sql_header);
			String headernhomexcell="KPI-Bán ra số lượng,KPI-BÁN RA,KPI-Số lần viếng thăm có PS đơn hàng,KPI-Số cửa hàng mua hàng,KPI-Số đơn hàng trên ngày,KPI-Giá trị tối thiểu của đơn hàng,KPI-SKU/đơn hàng,KPI-% Giao hàng thành công";
			String headernhomsqlCT="";
			String headernhomsqlDS="";
			String headerpivot="";
			String headernhomsqlT_FK="";
			String headernhomsqlSL="";
			
		try {
			
			while (rsh.next())
			{
					headernhomexcell+=",KPI-"+rsh.getString("ten");
					headernhomsqlCT+=",CTNHOM.["+ rsh.getString("PK_SEQ")+"] as CTNHOM"+rsh.getString("PK_SEQ");
					headerpivot+=",["+ rsh.getString("PK_SEQ")+"]";
					headernhomsqlDS+=",DS_NHOM.["+ rsh.getString("PK_SEQ")+"] as DS_NHOM"+rsh.getString("PK_SEQ");
					headernhomsqlT_FK+=",CTNHOMTHUONG.["+ rsh.getString("PK_SEQ")+"] as CTTHUONG_Fk"+rsh.getString("PK_SEQ");
					headernhomsqlSL+=",DS_NHOMSLBANRA.["+ rsh.getString("PK_SEQ")+"] as DSSL_NHOM"+rsh.getString("PK_SEQ");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			rsh.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String sql="";
	/*	String	sql=" SELECT TD_SKU.sldhang,CT.KHUVUC,CT.kenhbh,CT.NHANVIEN_FK,CT.TEN,CT.CHITIEUBANRA,CT.CHITIEUMUAVAO,CT.GIAOHANGTHANHCONG,CT.GIATRIDONHANGTOITHIEU \n"+
			",CT.SKU,CT.SOCUAHANGMUAHANG,CT.SOLANVIENGTHAM"+headernhomsqlCT+headernhomsqlDS+headersku+" FROM \n"+
					"(\n"+
					"	SELECT KB.Ten as kenhbh,A.TEN,B.NHANVIEN_FK,B.CHITIEUBANRA,B.CHITIEUMUAVAO,B.GIAOHANGTHANHCONG,B.GIATRIDONHANGTOITHIEU \n"+
					"	,B.SKU,B.SOCUAHANGMUAHANG,B.SOLANVIENGTHAM,B.THANG,B.NAM,C.KENH_FK,KV.TEN AS KHUVUC FROM DAIDIENKINHDOANH A \n"+
					"	LEFT JOIN CHITIEU_NV_CHITIET B ON A.PK_SEQ=B.NHANVIEN_FK AND B.CHUCVU='TDV' \n"+
					"   left join CHITIEU_NHANVIEN_NPP C on C.PK_SEQ=B.CHITIEU_NV_FK \n"+
					" LEFT JOIN DIABAN DB ON DB.PK_SEQ=A.diaban_fk \n"+
					" LEFT JOIN KHUVUC KV ON KV.PK_SEQ=DB.KHUVUC_FK \n"+
					" LEFT JOIN KENHBANHANG KB ON KB.PK_SEQ=C.KENH_FK \n"+		
					"	WHERE B.THANG="+obj.getMonth()+" AND B.NAM="+obj.getYear()+ condition+" \n"+
					")	CT \n"+
					"LEFT JOIN  \n"+
					"(  \n"+
					"	SELECT NV_FK AS DDKD_FK, NSP.PK_SEQ AS NHOM , SUM(CT_NHOM.CHITIEU) AS CHITIEU,CT.LOAICHITIEU,CT.KENH_FK  \n"+
					"	FROM CHITIEUSEC_NHANVIEN_NHOMSP CT_NHOM INNER JOIN CHITIEU_NHANVIEN_NPP CT ON CT_NHOM.CHITIEUSEC_NV_FK = CT.PK_SEQ \n"+ 
					"	INNER JOIN NHOMSANPHAM NSP ON CT_NHOM.NHOMSP_FK = NSP.PK_SEQ  \n"+
					"	WHERE CT.TRANGTHAI = '1' AND CT.NAM = "+obj.getYear()+" AND CHUCVU = 'TDV' AND CT_NHOM.THANG="+obj.getMonth()+" \n"+
					"	GROUP BY NV_FK, NSP.DIENGIAI,CT.LOAICHITIEU ,NSP.PK_SEQ ,CT.KENH_FK\n"+
					"	)  \n"+
					"	DT PIVOT ( SUM(CHITIEU) FOR NHOM IN ([0]"+headerpivot+" ) \n"+ 
					") AS CTNHOM \n"+
					"ON CT.NHANVIEN_FK=CTNHOM.DDKD_FK \n"+
					"LEFT JOIN \n"+
					"(	\n"+
					"	SELECT DDH.DDKD_FK,NSP_FK,SUM(HD_SP.SOLUONG* HD_SP.DONGIA) AS THANHTIEN \n"+
					"FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK \n"+  
					"INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ \n"+
					"INNER JOIN ERP_HOADONNPP_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK \n"+
					"INNER JOIN SANPHAM SP ON SP.PK_SEQ=HD_SP.SANPHAM_FK  \n"+
					"INNER JOIN NHOMSANPHAM_SANPHAM NSP_SP ON SP.PK_SEQ =NSP_SP.SP_FK \n"+
					"WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+" and YEAR(HD.NGAYXUATHD)="+obj.getYear()+ conditionTDV+"\n"+  
					"GROUP BY DDH.DDKD_FK ,NSP_FK \n"+
					")  \n"+
					 "DT PIVOT ( SUM(THANHTIEN) FOR NSP_FK IN ([0]"+headerpivot+" )\n"+ 
					") AS DS_NHOM ON CT.NHANVIEN_FK=DS_NHOM.DDKD_FK \n"+
					" LEFT JOIN  \n"+
					" (	 \n"+
					" SELECT count(HD.tongtienavat) as sldhang,HD.DDKD_FK,HD.KBH_FK \n"+
					" FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK  \n"+
					" INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ  \n"+
					" WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+" and YEAR(HD.NGAYXUATHD)="+obj.getYear()+ conditionTDV+" \n"+
					" and HD.tongtienavat >=500000 \n"+
					" group by HD.DDKD_FK,HD.KBH_FK \n"+
					" )   AS TD_SKU ON TD_SKU.DDKD_FK=CT.NHANVIEN_FK \n"+
					" LEFT JOIN ( \n"+
					"		select DATA.DDKD_FK,DATA.SANPHAM_FK,case when DATA.DONVITINH=N'Thùng' then DATA.SOLUONG \n"+
					"		 else  ((select SOLUONG2/SOLUONG1 from QUYCACH where sanpham_fk=DATA.SANPHAM_FK and DVDL2_FK=100019  ) * DATA.SOLUONG)  end as soluong \n"+
					"		  from ( \n"+
					"		 SELECT HD.DDKD_FK,HD_SP.SANPHAM_FK,HD_SP.DONVITINH,sum(HD_SP.SOLUONG) as soluong \n"+
					"		 FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK  \n"+
					"		 INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ  \n"+
					"		 INNER JOIN ERP_HOADONNPP_SP HD_SP ON HD_SP.HOADON_FK=HD.PK_SEQ \n"+
					"		 INNER JOIN SANPHAM SP ON SP.PK_SEQ=HD_SP.SANPHAM_FK \n"+
					"		 INNER JOIN ThuongDauThung_Sp DTSP ON DTSP.sanpham_fk=HD_SP.SANPHAM_FK \n"+
					"		 INNER JOIN THUONGDAUTHUNG DT ON DT.PK_SEQ=DTSP.Thuong_fk \n"+
					"		 INNER JOIN ThuongDauThung_TieuChi DTTC ON DTTC.Thuong_fk=DT.PK_SEQ \n"+
					"		 WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+" \n"+
					"		  and YEAR(HD.NGAYXUATHD)="+obj.getYear()+"  AND DT.THANG="+obj.getMonth()+" AND DT.NAM="+obj.getYear()+"  \n"+
					"		 GROUP BY HD.DDKD_FK,HD_SP.SANPHAM_FK,HD_SP.DONVITINH) as DATA) as B \n"+
					"		   pivot ( SUM(B.soluong) FOR B.sanpham_fk IN ([0] "+tsku+" )) as Tsku \n"+
					"		 on Tsku.DDKD_FK=CT.NHANVIEN_FK "+
					"	left join ( \n"+
					"			 SELECT HD.DDKD_FK,kbh.TEN,HD.KBH_FK,sum(HD_SP.SOLUONG*HD_SP.DONGIA) as DSBANRA  \n"+
					"			 FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK \n"+  
					"			 INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ  \n"+
					"			 INNER JOIN ERP_HOADONNPP_SP HD_SP ON HD_SP.HOADON_FK=HD.PK_SEQ  \n"+
					"			 inner join hethongbanhang_kenhbanhang ht on HD.KBH_FK=ht.kbh_fk \n"+
					"			 inner join KENHBANHANG kbh on kbh.PK_SEQ=ht.kbh_fk \n"+
					"			 WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)=06 \n"+ 
					"			  and YEAR(HD.NGAYXUATHD)=2015  --and ht.htbh_fk=100001 \n"+
					"			 GROUP BY HD.DDKD_FK,HD.KBH_FK,kbh.TEN \n"+
					"	) as DABANRAT on CT.NHANVIEN_FK=DABANRAT.DDKD_FK ";*/
		
		sql= "SELECT DABANRASLT.SLBANRA,DABANRAT.TEN as KBH,CT.KHUVUC,DABANRAT.DSBANRA as TDDSBANRA,CT.chitieubanraSL,CT.NHANVIEN_FK,CT.TEN,CT.CHITIEUBANRA,CT.GIAOHANGTHANHCONG,CT.GIATRIDONHANGTOITHIEU    \n "+      
				   ",CT.SKU,CT.SOCUAHANGMUAHANG,CT.SOLANVIENGTHAM "+headernhomsqlCT+headernhomsqlDS+headernhomsqlT_FK+headersku+headernhomsqlSL+" FROM    \n "+      
				   "(   \n "+      
				   "	SELECT KB.Ten as kenhbh,A.TEN,B.NHANVIEN_FK,B.CHITIEUBANRA,B.chitieubanraSL,B.CHITIEUMUAVAO,B.GIAOHANGTHANHCONG,B.GIATRIDONHANGTOITHIEU    \n "+      
				   "	,B.SKU,B.SOCUAHANGMUAHANG,B.SOLANVIENGTHAM,B.THANG,B.NAM,C.KENH_FK,KV.TEN AS KHUVUC FROM DAIDIENKINHDOANH A    \n "+      
				   "	LEFT JOIN Chitieunhanvien_OTC_SR B ON A.PK_SEQ=B.NHANVIEN_FK AND B.CHUCVU='TDV'    \n "+      
				   "   left join Chitieunhanvien_OTC C on C.PK_SEQ=B.CHITIEU_NV_FK    \n "+      
				   " LEFT JOIN DIABAN DB ON DB.PK_SEQ=A.diaban_fk    \n "+      
				   " LEFT JOIN KHUVUC KV ON KV.PK_SEQ=DB.KHUVUC_FK    \n "+      
				   " LEFT JOIN KENHBANHANG KB ON KB.PK_SEQ=C.KENH_FK   \n "+      
				   " left join hethongbanhang_kenhbanhang ht on KB.PK_SEQ= ht.kbh_fk   \n "+      
				   "	WHERE B.THANG="+obj.getMonth()+" AND B.NAM="+obj.getYear()+"    \n "+      
				   ")	CT    \n "+      
				   "LEFT JOIN     \n "+      
				   "(     \n "+      
				   "	SELECT NHANVIEN_FK AS DDKD_FK, NSP.PK_SEQ AS NHOM , SUM(CT_NHOM.CHITIEU) AS CHITIEU,CT.LOAICHITIEU    \n "+      
				   "	FROM Chitieunhanvien_OTC_SR_NSP CT_NHOM INNER JOIN Chitieunhanvien_OTC CT ON CT_NHOM.CHITIEU_NV_FK = CT.PK_SEQ    \n "+      
				   "	INNER JOIN NHOMSANPHAM NSP ON CT_NHOM.NHOMSP_FK = NSP.PK_SEQ     \n "+      
				   "	WHERE   CT.NAM = "+obj.getYear()+"  AND CT_NHOM.THANG="+obj.getMonth()+"  and CT_NHOM.Trangthai=1   \n "+      
				   "	GROUP BY NHANVIEN_FK, NSP.DIENGIAI,CT.LOAICHITIEU ,NSP.PK_SEQ    \n "+      
				   "	)     \n "+      
				   "	DT PIVOT ( SUM(CHITIEU) FOR NHOM IN ([0] "+headerpivot+" )    \n "+      
				   ") AS CTNHOM    \n "+      
				   "ON CT.NHANVIEN_FK=CTNHOM.DDKD_FK    \n "+   
				   " LEFT JOIN \n"+    
				   " (     \n"+
				   " SELECT NHANVIEN_FK AS DDKD_FK, NSP.PK_SEQ AS NHOM ,CT_NHOM.LOAICHITIEU_FK    \n"+
				   "	FROM Chitieunhanvien_OTC_SR_NSP CT_NHOM INNER JOIN Chitieunhanvien_OTC CT ON CT_NHOM.CHITIEU_NV_FK = CT.PK_SEQ  \n"+   
				   "	INNER JOIN NHOMSANPHAM NSP ON CT_NHOM.NHOMSP_FK = NSP.PK_SEQ    \n"+ 
				   "	WHERE   CT.NAM = "+obj.getYear()+"  AND CT_NHOM.THANG="+obj.getMonth()+"  and CT_NHOM.Trangthai=1   \n"+
				   "	GROUP BY NHANVIEN_FK, NSP.DIENGIAI,CT_NHOM.LOAICHITIEU_FK ,NSP.PK_SEQ   \n"+ 
				   "	)     \n"+
				   "	DT PIVOT ( SUM(LOAICHITIEU_FK) FOR NHOM IN ([0] "+headerpivot+" )  \n"+  
				   " ) AS CTNHOMTHUONG    \n"+
				   " ON CT.NHANVIEN_FK=CTNHOM.DDKD_FK \n"+  
				   "LEFT JOIN    \n "+      
				   "(	   \n "+      
				   "	SELECT DDH.DDKD_FK,NSP_FK,SUM(HD_SP.SOLUONG* HD_SP.DONGIA) AS THANHTIEN    \n "+      
				   "FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK    \n "+      
				   "INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ    \n "+      
				   "INNER JOIN ERP_HOADONNPP_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK    \n "+      
				   "INNER JOIN SANPHAM SP ON SP.PK_SEQ=HD_SP.SANPHAM_FK     \n "+      
				   "INNER JOIN NHOMSANPHAM_SANPHAM NSP_SP ON SP.PK_SEQ =NSP_SP.SP_FK    \n "+      
				   "WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+" and YEAR(HD.NGAYXUATHD)="+obj.getYear()+"   \n "+      
				   "GROUP BY DDH.DDKD_FK ,NSP_FK    \n "+      
				   ")     \n "+      
				   "DT PIVOT ( SUM(THANHTIEN) FOR NSP_FK IN ([0] "+headerpivot+" )   \n "+      
				   ") AS DS_NHOM ON CT.NHANVIEN_FK=DS_NHOM.DDKD_FK    \n "+      
				   " LEFT JOIN (    \n "+      
				   "		select DATA.DDKD_FK,DATA.SANPHAM_FK,case when DATA.DONVITINH=N'Thùng' then DATA.SOLUONG    \n "+      
				   "		 else  ((select SOLUONG2/SOLUONG1 from QUYCACH where sanpham_fk=DATA.SANPHAM_FK and DVDL2_FK=100019  ) * DATA.SOLUONG)  end as soluong    \n "+      
				   "		  from (    \n "+      
				   "		 SELECT HD.DDKD_FK,HD_SP.SANPHAM_FK,HD_SP.DONVITINH,sum(HD_SP.SOLUONG) as soluong    \n "+      
				   "		 FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK     \n "+      
				   "		 INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ     \n "+      
				   "		 INNER JOIN ERP_HOADONNPP_SP HD_SP ON HD_SP.HOADON_FK=HD.PK_SEQ    \n "+      
				   "		 INNER JOIN SANPHAM SP ON SP.PK_SEQ=HD_SP.SANPHAM_FK    \n "+      
				   "		 INNER JOIN ThuongDauThung_Sp DTSP ON DTSP.sanpham_fk=HD_SP.SANPHAM_FK    \n "+      
				   "		 INNER JOIN THUONGDAUTHUNG DT ON DT.PK_SEQ=DTSP.Thuong_fk    \n "+      
				   "		 INNER JOIN ThuongDauThung_TieuChi DTTC ON DTTC.Thuong_fk=DT.PK_SEQ    \n "+ 
				   "  inner join hethongbanhang_kenhbanhang ht on HD.KBH_FK=ht.kbh_fk  \n"+
				   "		 WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+"    \n "+      
				   "		  and YEAR(HD.NGAYXUATHD)="+obj.getYear()+"  AND DT.THANG="+obj.getMonth()+" AND DT.NAM="+obj.getYear()+"   and ht.htbh_fk= 100001     \n "+      
				   "		 GROUP BY HD.DDKD_FK,HD_SP.SANPHAM_FK,HD_SP.DONVITINH) as DATA) as B    \n "+      
				   "		   pivot ( SUM(B.soluong) FOR B.sanpham_fk IN ([0]  "+tsku+" )) as Tsku    \n "+      
				   "		 on Tsku.DDKD_FK=CT.NHANVIEN_FK   \n "+      
				   "inner join (   \n "+      
				   "		 SELECT HD.DDKD_FK,kbh.TEN,HD.KBH_FK,sum(HD_SP.SOLUONG*HD_SP.DONGIA) as DSBANRA    \n "+      
				   "		 FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK     \n "+      
				   "		 INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ     \n "+      
				   "		 INNER JOIN ERP_HOADONNPP_SP HD_SP ON HD_SP.HOADON_FK=HD.PK_SEQ    \n "+      
				   "		 inner join hethongbanhang_kenhbanhang ht on HD.KBH_FK=ht.kbh_fk   \n "+      
				   "		 inner join KENHBANHANG kbh on kbh.PK_SEQ=ht.kbh_fk   \n "+      
				   "		 WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+"    \n "+      
				   "		  and YEAR(HD.NGAYXUATHD)="+obj.getYear()+"  and ht.htbh_fk=100001   \n "+      
				   "		 GROUP BY HD.DDKD_FK,HD.KBH_FK,kbh.TEN   \n "+      
				   ") as DABANRAT on CT.NHANVIEN_FK=DABANRAT.DDKD_FK   \n "+      
				 "	inner join (   \n"+
				"	 		 SELECT HD.DDKD_FK,kbh.TEN,HD.KBH_FK,count(HD.pk_seq) as SLBANRA    \n"+
				"	 		 FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK  \n"+   
				"	 		 INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ    \n"+ 
				"	 		 inner join hethongbanhang_kenhbanhang ht on HD.KBH_FK=ht.kbh_fk   \n"+
				"	 		 inner join KENHBANHANG kbh on kbh.PK_SEQ=ht.kbh_fk   \n"+
				"	 		 WHERE HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+"  \n"+   
				"	 		  and YEAR(HD.NGAYXUATHD)="+obj.getYear()+"  and ht.htbh_fk=100001   \n"+
				"	 		 GROUP BY HD.DDKD_FK,HD.KBH_FK,kbh.TEN   \n"+
				"	 ) as DABANRASLT on CT.NHANVIEN_FK=DABANRASLT.DDKD_FK \n"+
				"	 inner JOIN    \n"+
				"	 (	\n"+
				"	 select A.DDKD_FK,A.NSP_FK,COUNT(A.DDKD_FK) as SlBanra from(   \n"+
				"	 	SELECT DDH.DDKD_FK,NSP_FK,count(HD.PK_SEQ) AS soluongbanra   \n"+
				"	 FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_DDH HD_DH ON HD.PK_SEQ = HD_DH.HOADONNPP_FK  \n"+  
				"	 INNER JOIN ERP_DONDATHANGNPP DDH ON  HD_DH.DDH_FK = DDH.PK_SEQ   \n"+ 
				"	 INNER JOIN ERP_HOADONNPP_SP HD_SP ON HD.PK_SEQ = HD_SP.HOADON_FK    \n"+
				"	 INNER JOIN SANPHAM SP ON SP.PK_SEQ=HD_SP.SANPHAM_FK     \n"+
				"	 INNER JOIN NHOMSANPHAM_SANPHAM NSP_SP ON SP.PK_SEQ =NSP_SP.SP_FK   \n"+
				"	  inner join hethongbanhang_kenhbanhang ht on HD.KBH_FK=ht.kbh_fk    \n"+
				"	 		 inner join KENHBANHANG kbh on kbh.PK_SEQ=ht.kbh_fk    \n"+ 
				"	 WHERE ht.htbh_fk=100001  and HD.TRANGTHAI = 4 AND DDH.DDKD_FK IS NOT NULL AND DDH.LOAIDONHANG != 0 and month(HD.NGAYXUATHD)="+obj.getMonth()+" and YEAR(HD.NGAYXUATHD)="+obj.getYear()+"  \n"+  
				"	 GROUP BY DDH.DDKD_FK ,NSP_FK ,HD.PK_SEQ ) A \n"+
				"	   group by A.DDKD_FK,A.NSP_FK \n"+
				"	 )     \n"+
				"	 DT PIVOT ( SUM(SlBanra) FOR NSP_FK IN ([0]"+ headerpivot +"  \n"+ 
				"	 )) AS DS_NHOMSLBANRA ON CT.NHANVIEN_FK=DS_NHOMSLBANRA.DDKD_FK  ";
		 


		
		
			query += " where 1=1"  ;
			/*if (obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='" + obj.getkenhId() + "' ";
			if (obj.getnppId().length() > 0)
				query += " and npp.pk_seq = '" + obj.getnppId() + "' ";
			if (obj.getvungId().length() > 0)
				query += " and vung.pk_seq = '" + obj.getvungId() + "' ";
			if (obj.getdvkdId().length() > 0)
				query += " and dvkd.pk_seq = '" + obj.getdvkdId() + "' ";
			if (obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '" + obj.getkhuvucId() + "' ";
			if (obj.getDdkd().length() > 0)
				query += " and ddkd.pk_seq = '" + obj.getDdkd() + "'";
			
			
			Utility  util = new Utility();
			if(obj.getphanloai().equals("2")&& !obj.getLoaiNv().equals("3"))
			{
				query+= " and ddkd.pk_seq  in " + util.Quyen_Ddkd(obj.getuserId())+"";
			}
		*/
			System.out.println("bao cao :::::::::::"+headernhomexcell);
			System.out.println("bao cao :::::::::::"+sql);
			
			return sql;
	}

	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\test.xlsm";
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
	
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) throws Exception
	{
		try
		{
			
			String query = "";
			String sql_header="select PK_SEQ,TEN from NHOMSANPHAM where tinhthuong=1";
			String sku= "select b.sanpham_fk,c.TEN from THUONGDAUTHUNG a \n"+
					    "inner join ThuongDauThung_Sp b on a.PK_SEQ=b.Thuong_fk  \n"+
					    "inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk \n"+
					    "where  a.thang="+obj.getMonth()+" and a.nam="+obj.getYear()+" and a.TRANGTHAI=1";
			System.out.println(sql_header);
				dbutils db=new dbutils();
				ResultSet rsh=db.get(sql_header);
				String headernhomexcell="KPI-Bán ra SL,KPI-BÁN RA,KPI-Số lần viếng thăm có PS đơn hàng,KPI-Số cửa hàng mua hàng,KPI-Số đơn hàng trên ngày,KPI-Giá trị tối thiểu của đơn hàng,KPI-SKU/đơn hàng,KPI-% Giao hàng thành công";
				String headersku="test";
			try {
				
				while (rsh.next())
				{
						headernhomexcell+=",KPI-"+rsh.getString("ten");
				
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
				rsh.close();
				ResultSet rsh2=db.get(sql_header);
				try {
					
					while (rsh2.next())
					{
							headernhomexcell+=",KPI-"+rsh2.getString("ten")+"SL";
					
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
					rsh2.close();	
				
				System.out.println(":::::: SKU"+sku);
				ResultSet rsh1=db.get(sku);	
			try {
				
				int i=0;
				while (rsh1.next())
				{
			
					headersku+=",Thuong "+rsh1.getString("ten");
					System.out.println("san pham "+headersku);
					
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			rsh1.close();
			
			String title[]=headernhomexcell.split(",");
			
	 		Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet("OTC_KPI");
			worksheet.setName("OTC_KPI");
			Cells cells = worksheet.getCells();

			worksheet.getCells().setStandardWidth(20.5f);
		    Style style;
		    Font font = new Font();
		    font.setColor(Color.RED);//mau chu
		    font.setSize(16);// size chu
		   	font.setBold(true);
		   	
		    Cell cell = cells.getCell("A1");
		    style = cell.getStyle();
		    style.setFont(font); 
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BANG TINH THUONG KPI TDV OTC THANG "+obj.getMonth() +"/"+obj.getYear());
		
		    cell = cells.getCell("A2");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "** AP DUNG CHINH SACH MOI THANG 01.2015" );
		    
		 
		    cell = cells.getCell("A4"); 
			   ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "STT" );	
		  
			 cells.setRangeOutlineBorder(3,3, 0 , 0 ,BorderLineType.THIN,Color.BLACK);
			   
		
		    cell = cells.getCell("B4"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "KHU VUC" );	    

		    cells.setRangeOutlineBorder(3,3, 1 , 1 ,BorderLineType.THIN,Color.BLACK);
			
		   
		    cell = cells.getCell("C4"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "TDV" );	    
;
		    cell = cells.getCell("D4"); 
		    cells.setRangeOutlineBorder(3,3, 2 , 2 ,BorderLineType.THIN,Color.BLACK);
			
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "KENH" );	    
		
		  
		    cell = cells.getCell("E4"); 
		    cells.setRangeOutlineBorder(3,3, 3 , 3 ,BorderLineType.THIN,Color.BLACK);
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "CHUC VU" );	    
		  
		    cells.setRangeOutlineBorder(3,3, 4 , 4 ,BorderLineType.THIN,Color.BLACK);
		    	int j=5;
		    
		    	cells.hideColumn(5);
		    	cells.hideColumn(6);
		    for(int i=0;i<title.length;i++)
		    {
		    	
		    	if(i<8)
		    	{
		    		if(i!=1 && i!=0)	   
		    		{
		    		cells.hideColumn(j);
		    		cells.hideColumn(j+1);
		    		}
		    	cell = cells.getCell(3,j);
		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, title[i] );	
	
		    	ReportAPI.mergeCells(worksheet, 3, 3, j, j+1);  
		    	cells.setRangeOutlineBorder(3,3, j , j+1 ,BorderLineType.THIN,Color.BLACK);
		    	//cells.merge(3,7,1,2);
		    	//ReportAPI.mergeCells(worksheet, 3, 4, 1, 5);
		    	// cells.setRangeOutlineBorder(3,3, 0 , 5 ,BorderLineType.THIN,Color.BLACK);
		    	
		    	
		    	cell = cells.getCell(4,j);
		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "THUC DAT"  );	 
		    	cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
		    	j++;
		    	cell = cells.getCell(4,j);
		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "THUONG"  );	

		    	cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
		  
		    	j++;
		    	}
		    	else
		    	{
		    		
		    		cell = cells.getCell(3,j);
		    		ReportAPI.mergeCells(worksheet, 3, 3, j, j+3);  
			    	cells.setRangeOutlineBorder(3,3, j , j+3 ,BorderLineType.THIN,Color.BLACK);
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, title[i] );	

			    //	cells.merge(3,j,1,4);
			    	
		    		cell = cells.getCell(4,j);
		    		cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, title[i] );	
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "KE HOACH" );	

			    	j++;
			    	cell = cells.getCell(4,j);
			    	cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, title[i] );	
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "THUC HIEN" );	
	
			    	j++;
			    	cell = cells.getCell(4,j);
			    	cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, title[i] );	
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "TI LE DAT"  );	
	
			    	j++;
			    	cell = cells.getCell(4,j);
			    	cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, title[i] );	
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "THUONG"  );	
	
			    	j++;
			    	cell = cells.getCell(4,j);
			    	cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "" );	
			    	
			    	
		    	}
		    
		    }
		    
		    cell = cells.getCell(3,j);
		    cells.setRangeOutlineBorder(3,3, j , j ,BorderLineType.THIN,Color.BLACK);	    
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "TONG THUONG KPIS"  );	

	    	j++;
	    	String arrsku[]=headersku.split(",");
	    	if(arrsku.length>1)
	    	{
	    		for(int k=1;k<arrsku.length;k++)
	    		{
		    		cell = cells.getCell(3,j);
				    cells.setRangeOutlineBorder(3,3, j , j ,BorderLineType.THIN,Color.BLACK);	    
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, arrsku[k]  );	
		
			    	
			    	cell = cells.getCell(4,j);
			    	cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);
			    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "" );
			    	j++;
	    		}
	    	}
	    	 cell = cells.getCell(4,j);
			    cells.setRangeOutlineBorder(4,4, j , j ,BorderLineType.THIN,Color.BLACK);	    
		    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, ""  );	

		    cell = cells.getCell(3,j);
		    cells.setRangeOutlineBorder(3,3, j , j ,BorderLineType.THIN,Color.BLACK);	    
	    	ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "TONG THUONG"  );	

    
		   	    
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}
	
	
	private void FillData(Workbook workbook, String[] fieldShow, String query, IStockintransit obj) throws Exception
	{
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet("OTC_KPI");
		Cells cells = worksheet.getCells();
		worksheet.getCells().setStandardHeight(15f);
		worksheet.getCells().setStandardWidth(20.5f);
		dbutils db = new dbutils();
		String headersku="[0]";
		String skuT="[0]";
		String sku= "select b.sanpham_fk,c.TEN from THUONGDAUTHUNG a \n"+
			    "inner join ThuongDauThung_Sp b on a.PK_SEQ=b.Thuong_fk  \n"+
			    "inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk \n"+
			    "where  a.thang="+obj.getMonth()+" and a.nam="+obj.getYear()+" and a.TRANGTHAI=1";
		ResultSet rsh1=db.get(sku);
		try {
			
			while (rsh1.next())
			{
				headersku+=",Tsku"+rsh1.getString("sanpham_fk");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
			rsh1.close();
		
		System.out.println(":::::::::::::::::::::::::::::;;;;"+query);
		ResultSet rs = db.get(query);

		
		String sql = "";
		String sql_header="select PK_SEQ,TEN from NHOMSANPHAM where tinhthuong=1";
		
		float thuongKPInhom = 0;
		
		System.out.println(sql_header);
			ResultSet rsh=db.get(sql_header);
			//String headernhomexcell="KPI-MUA VÀO,KPI-BÁN RA,KPI-Số lần viếng thăm có PS đơn hàng,KPI-Số cửa hàng mua hàng,KPI-Số đơn hàng trên ngày,KPI-Giá trị tối thiểu của đơn hàng,KPI-SKU/đơn hàng,KPI-% Giao hàng thành công";
			String  headernhomsqlCT="[0]";
			String headernhomsqlDS="[0]";
			String headerCTT="[0]";
			String headernhomsqlSL="[0]";
		try {
			
			while (rsh.next())
			{
				headernhomsqlCT+=",CTNHOM"+rsh.getString("PK_SEQ");
				headernhomsqlDS+=",DS_NHOM"+rsh.getString("PK_SEQ");
				headerCTT+=",CTTHUONG_Fk"+rsh.getString("PK_SEQ");
				headernhomsqlSL+=",DSSL_NHOM"+rsh.getString("PK_SEQ");
				
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
			String sqlct[]=headernhomsqlCT.split(",");
			String sqlds[]=headernhomsqlDS.split(",");
			String sqlTCT[]=headerCTT.split(",");
			String sqlsl[]=headernhomsqlSL.split(",");
			String[] mangtieuchi=new String[200];
			sql = 
			"SELECT TC.DVKD_FK, TC.KBH_FK, TC.HETHONGBH_FK, TC.CONGTY_FK, CT.TIEUCHI, CT.NHOMSP_FK, CT2.STT, CT2.TU, CT2.DEN, CT2.THUONG FROM TIEUCHITINHTHUONG TC " +
			"INNER JOIN TIEUCHITHUONG_CHITIET CT ON CT.TIEUCHITINHTHUONG_FK = TC.PK_SEQ " +
			"INNER JOIN TIEUCHITHUONG_CT_MUCTHUONG CT2 ON CT2.TCTCT_FK = CT.PK_SEQ " +
			"WHERE TC.THANG = "+ obj.getMonth() +" AND TC.NAM = "+ obj.getYear() +" AND TC.LOAI = '1' AND TC.TRANGTHAI = '1' " +
			"ORDER BY TC.DVKD_FK,TC.KBH_FK, TC.HETHONGBH_FK, TC.CONGTY_FK, CT.TIEUCHI,CT2.STT ";
			int k = 0;
			ResultSet rs2 = db.get(sql);
			System.out.println("he thong kenh ban hang::::::::::::::::"+sql);
			while (rs2.next())
			{											
				mangtieuchi[k]=rs2.getString("HETHONGBH_FK")+";"+rs2.getString("NHOMSP_FK")+";"+rs2.getString("TIEUCHI")+";"+rs2.getString("STT")+";"+rs2.getString("TU")+";"+rs2.getString("DEN")+";"+rs2.getString("THUONG");
				k++;
			}
			
		int i = 5;
		int stt=1;
		try
		{
			Cell cell = null;
			while (rs.next())
			{
				float tongthuong=0;
				
			cell = cells.getCell(i,0);cell.setValue(stt);
			
			 cells.setRangeOutlineBorder(i,i, 0 , 0 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,1);cell.setValue(rs.getString("khuvuc"));
	    	
			 cells.setRangeOutlineBorder(i,i, 1 , 1 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,2);cell.setValue(rs.getString("TEN"));
	    	
			 cells.setRangeOutlineBorder(i,i, 2 , 2 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,3);cell.setValue(rs.getString("KBH"));
	    	
			 cells.setRangeOutlineBorder(i,i, 3 , 3 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,4);cell.setValue("TDV");
	    	
			 cells.setRangeOutlineBorder(i,i, 4 , 4 ,BorderLineType.THIN,Color.BLACK);
			
	    	
	    	cell = cells.getCell(i,5);cell.setValue(rs.getString("SLBANRA"));
	    	
			 cells.setRangeOutlineBorder(i,i, 5 , 5 ,BorderLineType.THIN,Color.BLACK);
			
			 float ctSL= rs.getFloat("SLBANRA") /rs.getFloat("chitieubanrasl");
				
			//	float thuongKPIDSL = (float) getThuongTongDS(mangtieuchi, "100001", "null","10",ctSL);
				
	    	cell = cells.getCell(i,6);cell.setValue("");
	    
			 cells.setRangeOutlineBorder(i,i, 6 , 6 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,7);cell.setValue(rs.getString("TDDSBANRA"));
	    	
			 cells.setRangeOutlineBorder(i,i, 7 , 7 ,BorderLineType.THIN,Color.BLACK);
			 float ct= rs.getFloat("TDDSBANRA") /rs.getFloat("chitieubanra");
			 
				float thuongKPIDSTOng = (float) getThuongTongDS(mangtieuchi, "100001", "null","10",ct);
				  
	    	cell = cells.getCell(i,8);cell.setValue(thuongKPIDSTOng+"");
	   
			 cells.setRangeOutlineBorder(i,i, 8 , 8 ,BorderLineType.THIN,Color.BLACK);
			
	    
	     	cell = cells.getCell(i,9);cell.setValue("");
	    
			 cells.setRangeOutlineBorder(i,i, 9 , 9 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,10);cell.setValue("");
	    
			 cells.setRangeOutlineBorder(i,i, 10 , 10 ,BorderLineType.THIN,Color.BLACK);
			
	    
	    	cell = cells.getCell(i,11);cell.setValue("");
	    	
			 cells.setRangeOutlineBorder(i,i, 11 , 11 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,12);cell.setValue("");
	    
			 cells.setRangeOutlineBorder(i,i, 12 , 12 ,BorderLineType.THIN,Color.BLACK);
			
	 
	    
	    	cell = cells.getCell(i,13);cell.setValue("");
	    	
			 cells.setRangeOutlineBorder(i,i, 13 , 13 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,14);cell.setValue("");
	    	 
			 cells.setRangeOutlineBorder(i,i, 14 , 14 ,BorderLineType.THIN,Color.BLACK);
			
	 
	    	cell = cells.getCell(i,15);cell.setValue("");
	    	
			 cells.setRangeOutlineBorder(i,i, 15 , 15 ,BorderLineType.THIN,Color.BLACK);
			
	    	cell = cells.getCell(i,16);cell.setValue("");
	    	
			 cells.setRangeOutlineBorder(i,i, 16 , 16 ,BorderLineType.THIN,Color.BLACK);
			
	    	
	      	cell = cells.getCell(i,17);cell.setValue("");
	      	
			 cells.setRangeOutlineBorder(i,i, 17 , 17 ,BorderLineType.THIN,Color.BLACK);
			
	    //  	float thuong=Trathuong(obj.getMonth(), obj.getYear(),7,("");
	  		
	    	cell = cells.getCell(i,18);cell.setValue("");
	    
			 cells.setRangeOutlineBorder(i,i, 18 , 18 ,BorderLineType.THIN,Color.BLACK);
			
			 
			 cell = cells.getCell(i,19);cell.setValue("");
			 cells.setRangeOutlineBorder(i,i, 19 , 19 ,BorderLineType.THIN,Color.BLACK);
			
			 cell = cells.getCell(i,20);cell.setValue("");
			 cells.setRangeOutlineBorder(i,i, 20 , 20 ,BorderLineType.THIN,Color.BLACK);
			
	    	//tongthuong+=thuong;
	    	
	    	
	 
	    	int index1=21;
	    	if(sqlct.length>1 || sqlds.length>1)
	    	{
	    		for(int j=1;j<sqlct.length;j++)
	    		{
	    			System.out.println();
	    			
	    			float chitieu=Float.parseFloat(rs.getString(sqlct[j])==null?"0":rs.getString(sqlct[j]));
	    			float thucdat=Float.parseFloat(rs.getString(sqlds[j])==null?"0":rs.getString(sqlds[j]));
	    			String CTthuongtungnhom=Float.parseFloat(rs.getString(sqlTCT[j])==null?"0":rs.getString(sqlTCT[j]))+"";
	    			System.out.println(sqlct[j]+"-------"+sqlds[j]+"tc thuong de map voi thuong"+CTthuongtungnhom);
	    			if(chitieu==0 || Float.parseFloat(CTthuongtungnhom)!=1)
	    			{
	    				cells.hideColumn(index1);
	    				cells.hideColumn(index1+1);
	    				cells.hideColumn(index1+2);
	    				cells.hideColumn(index1+3);
	    				cells.hideColumn(index1+4);
	    			}
	    	    	cell = cells.getCell(i,index1);cell.setValue(chitieu);
	    	    	
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    	    	index1++;
	    	    	cell = cells.getCell(i,index1);cell.setValue(thucdat);
	    	    
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    	      	index1++;
	    	      	float ptramdat = (float)((chitieu==0?0:thucdat/chitieu)*100);
	    	      	System.out.println("nhom SL"+ptramdat);
	    	    	cell = cells.getCell(i,index1);cell.setValue(ptramdat+"");
	    	  
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    	      	index1++;
	    	      	String nhom[]=sqlct[j].split("M");
	    	      //	float tt=Trathuongnhom(obj.getMonth(), obj.getYear(),1, chitieu==0?0:((thucdat/chitieu)*100), nhom[1]);
	    	    	 
	    			 thuongKPInhom = (float) getThuong(mangtieuchi, "100001", nhom[1], CTthuongtungnhom, ptramdat);
	    			 cell = cells.getCell(i,index1);cell.setValue(""+thuongKPInhom);
		    	    	
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    			
	    	    	index1++;
	    	    	tongthuong+=thuongKPInhom;
	    		}
	    		
	    	}	
	    
	    	
			if(sqlct.length>1 || sqlsl.length>1)
	    	{
	    		for(int j=1;j<sqlct.length;j++)
	    		{
	    			System.out.println();
	    			
	    			float chitieu=Float.parseFloat(rs.getString(sqlct[j])==null?"0":rs.getString(sqlct[j]));
	    			float thucdat=Float.parseFloat(rs.getString(sqlsl[j])==null?"0":rs.getString(sqlsl[j]));
	    			String CTthuongtungnhom=Float.parseFloat(rs.getString(sqlTCT[j])==null?"0":rs.getString(sqlTCT[j]))+"";
	    			System.out.println(sqlct[j]+"-------"+sqlsl[j]+"tc thuong de map voi thuong"+CTthuongtungnhom);
	    			if(chitieu==0 || Float.parseFloat(CTthuongtungnhom)!=9)
	    			{
	    				cells.hideColumn(index1);
	    				cells.hideColumn(index1+1);
	    				cells.hideColumn(index1+2);
	    				cells.hideColumn(index1+3);
	    				cells.hideColumn(index1+4);
	    			}
	    	    	cell = cells.getCell(i,index1);cell.setValue(chitieu);
	    	    	
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    	    	index1++;
	    	    	cell = cells.getCell(i,index1);cell.setValue(thucdat);
	    	    
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    	      	index1++;
	    	      	float ptramdat = (float)((chitieu==0?0:thucdat/chitieu)*100);
	    	    	cell = cells.getCell(i,index1);cell.setValue(ptramdat);
	    	  
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    	      	index1++;
	    	      	String nhom[]=sqlct[j].split("M");
	    	      //	float tt=Trathuongnhom(obj.getMonth(), obj.getYear(),1, chitieu==0?0:((thucdat/chitieu)*100), nhom[1]);
	    	    	 
	    			 thuongKPInhom = (float) getThuongSL(mangtieuchi, "100001", nhom[1], CTthuongtungnhom, ptramdat);
	    			 cell = cells.getCell(i,index1);cell.setValue(""+thuongKPInhom);
		    	    	
	    			 cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
	    			
	    			
	    	    	index1++;
	    	    	tongthuong+=thuongKPInhom;
	    		}
    		
    		
    	}
    	cell = cells.getCell(i,index1);cell.setValue(""+tongthuong);
    
    	cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
		index1++;
    	String arrsku[]=headersku.split(",");
    	for(int h=1;h<arrsku.length;h++)
    	{
    		
    		float thucdat=Float.parseFloat(rs.getString(arrsku[h])==null?"0":rs.getString(arrsku[h]));
    		String a[]=arrsku[h].split("u");
    		float thuong1=trathuong_SKU(obj.getMonth(), obj.getYear(), thucdat, 1, a[1]);
    		tongthuong+=thuong1;
    		
    		cell = cells.getCell(i,index1);cell.setValue(""+thuong1);
	    
	    	cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
			index1++;
    	}
	    	
	    	cell = cells.getCell(i,index1);cell.setValue(""+(tongthuong+thuongKPIDSTOng));
	   
	    	cells.setRangeOutlineBorder(i,i, index1 , index1 ,BorderLineType.THIN,Color.BLACK);
			index1++;
	    
	    	i++;
	       	
			}
			 if(rs!=null)
		    {
		    	rs.close();
		    }
		    
		    if(db != null) db.shutDown();
		    
		   
			}
			
		 catch (Exception err)
		{
			err.printStackTrace();
			throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :" + err.toString());
		}
	}
	
	private double getThuong(String[] mangtieuchi, String htbhid, String nhomspid, String tieuchi, float mucphantram) 
	{	double thuong = 0;
		for(int i=0;i<mangtieuchi.length;i++){
			if(mangtieuchi[i]!=null){
				String mang[]=mangtieuchi[i].split(";");
				if(  mang[0].trim().equals(htbhid.trim()) && mang[1].trim().equals(nhomspid.trim()) && mang[2].trim().equals("1"))
				{		
					System.out.println("nhomspid"+nhomspid+"vao adtatasdhsahgdhsa");
					if((Double.parseDouble(mang[4])<= mucphantram  && Double.parseDouble(mang[5])> mucphantram ) || mucphantram >= Double.parseDouble(mang[5]) && Double.parseDouble(mang[3]) == 5)
					{
							
						return Double.parseDouble(mang[6]);
					}
					else
					{	}
				}
				else
				{ //if(i == mangtieuchi.length) {return 0;} 
				}
			}
		}
		return thuong;
	}
	private double getThuongSL(String[] mangtieuchi, String htbhid, String nhomspid, String tieuchi, float mucphantram) 
	{	double thuong = 0;
		for(int i=0;i<mangtieuchi.length;i++){
			if(mangtieuchi[i]!=null){
				String mang[]=mangtieuchi[i].split(";");
				if(  mang[0].trim().equals(htbhid.trim()) && mang[1].trim().equals(nhomspid.trim()) && mang[2].trim().equals("9"))
				{		
					System.out.println("nhomspid"+nhomspid+"vao adtatasdhsahgdhsa");
					if((Double.parseDouble(mang[4])<= mucphantram  && Double.parseDouble(mang[5])> mucphantram ) || mucphantram >= Double.parseDouble(mang[5]) && Double.parseDouble(mang[3]) == 5)
					{
							
						return Double.parseDouble(mang[6]);
					}
					else
					{	}
				}
				else
				{ //if(i == mangtieuchi.length) {return 0;} 
				}
			}
		}
		return thuong;
	}
	
	private double getThuongTongDS(String[] mangtieuchi, String htbhid, String nhomspid, String tieuchi, float mucphantram) 
	{	double thuong = 0;
		for(int i=0;i<mangtieuchi.length;i++){
			if(mangtieuchi[i]!=null){
				String mang[]=mangtieuchi[i].split(";");
				System.out.println(mang[2]);
				if(  mang[0].trim().equals(htbhid.trim()) && mang[1].trim().equals(nhomspid.trim()) && mang[2].trim().equals(tieuchi))
				{		
					System.out.println("nhomspid"+nhomspid+"vao adtatasdhsahgdhsa");
					if((Double.parseDouble(mang[4])<= mucphantram  && Double.parseDouble(mang[5])> mucphantram ) || mucphantram >= Double.parseDouble(mang[5]) && Double.parseDouble(mang[3]) == 5)
					{
							
						return Double.parseDouble(mang[6]);
					}
					else
					{	}
				}
				else
				{ //if(i == mangtieuchi.length) {return 0;} 
				}
			}
		}
		return thuong;
	}
	
	
	public float Trathuong (String thang,String nam,int tieuchi,double d)
	{
		if(d>100)
		{
			d=100;
		}
		float thuong=0;
		String sql="select b.stt,b.thuong from TIEUCHITINHTHUONG a  inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ=b.TIEUCHITINHTHUONG_FK \n"+
				   "where a.trangthai=1 and  a.THANG="+thang+" and a.NAM="+nam+" and b.Thuong!=0 and b.tu< "+d +" and b.den >="+d +" and tieuchi="+tieuchi +" order by b.STT desc";
	//	System.out.println(":::::::::::::::::::::"+sql);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql);
		if(rs!=null)
		{
		try {
			while (rs.next())
			{
					thuong= rs.getFloat("thuong");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		return thuong;
	}
	
	public float Trathuongnhom (String thang,String nam,int tieuchi,float tyle,String nhomid)
	{
		if(tyle>100)
		{
			tyle=100;
		}
		float thuong=0;
		String sql="select b.stt,b.thuong from TIEUCHITINHTHUONG a  inner join TIEUCHITINHTHUONG_CHITIET b on a.PK_SEQ=b.TIEUCHITINHTHUONG_FK \n"+
				   "where a.trangthai=1 and a.THANG="+thang+" and a.NAM="+nam+" and a.nhomsp_fk="+nhomid+" and b.Thuong!=0 and b.tu< "+tyle +" and b.den >="+tyle +" and tieuchi="+tieuchi +" order by b.STT desc";
		System.out.println(":::::::+++++++++"+sql);
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql);
		if(rs!=null)
		{
		try {
			while (rs.next())
			{
					thuong= rs.getFloat("thuong");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		}
		try {
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("thuonggg"+thuong);
		return thuong;
	}
	
	public float trathuong_SKU(String thang,String nam,float thucdat,int loai,String sanpham_fk)
	{
		float thuong=0;
		System.out.println("thuc dat"+thucdat);
		String sku= "select b.trongso,b.sanpham_fk,c.TEN,d.ThuongSR,d.ThuongASM,d.ThuongBM,d.ThuongSS from THUONGDAUTHUNG a \n"+
			    "inner join ThuongDauThung_Sp b on a.PK_SEQ=b.Thuong_fk  \n"+
				"inner join ThuongDauThung_TieuChi d on d.Thuong_fk=a.PK_SEQ \n"+
			    "inner join SANPHAM c on c.PK_SEQ=b.sanpham_fk \n"+
			    "where  a.thang="+thang+" and a.nam="+nam+" and a.TRANGTHAI=1 and b.sanpham_fk="+sanpham_fk;
		dbutils db=new dbutils();
		ResultSet rs=db.get(sku);
		try {
			while (rs.next())
			{
				if(thucdat>=rs.getFloat("trongso"))
				{
					System.out.println(rs.getFloat("ThuongSR") +"----------"+thucdat);
				thuong=rs.getFloat("ThuongSR")*thucdat;
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return thuong;
	}
	
	public static void mergeCells(Worksheet source,int beginRow, int endRow, int beginColumn, int endColumn) 
	 {
	  Worksheet worksheet = source;  
	  Cells cells = worksheet.getCells();
	  cells.merge(beginRow, beginColumn, endRow,endColumn); 
	 }
	
	
}
