package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

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
import com.aspose.cells.Font;
import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class DailySalesSvl extends HttpServlet
{
	
	private static final long serialVersionUID = 1L;
	public DailySalesSvl()
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
		String nextJSP = "/TraphacoHYERP/pages/Center/DailySalesReport.jsp";
		
		String view = request.getParameter("view");
		if (view == null)
			view = "";
		
		if (!view.equals("TT"))
		{
			nextJSP = "/TraphacoHYERP/pages/Distributor/DailySalesReport.jsp";
			response.sendRedirect(nextJSP);
		} else
		{
			nextJSP = "/TraphacoHYERP/pages/Center/DailySalesReport.jsp";
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
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		
		if (userId == null)
			userId = "";
		
		obj.setuserId(userId);
		
		Utility util = new Utility();
		
		String view = request.getParameter("view");
		if (view == null)
			view = "";
		
		String nppId = "";
		if (view.equals("TT"))
		{
			nppId = util.antiSQLInspection(request.getParameter("nppId"));
			if (nppId == null)
				nppId = "";
			obj.setnppId(nppId);
		} else
		{
			nppId = util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}
		
		obj.setuserTen(userTen);
		String kenhId = util.antiSQLInspection(request.getParameter("kenhId"));
		if (kenhId == null)
			kenhId = "";
		obj.setkenhId(kenhId);
		
		String dvkdId = util.antiSQLInspection(request.getParameter("dvkdId"));
		
		if (dvkdId == null)
			dvkdId = "";
		obj.setdvkdId(dvkdId);
		
		obj.setMonth(util.antiSQLInspection(request.getParameter("thang") == null ? "" : request.getParameter("thang")));
		obj.setYear(util.antiSQLInspection(request.getParameter("nam") == null ? "" : request.getParameter("nam")));
		obj.setQuy(util.antiSQLInspection(request.getParameter("quy") == null ? "" : request.getParameter("quy")));
		obj.settype(util.antiSQLInspection(request.getParameter("apdung") == null ? "" : request.getParameter("apdung")));
		
		System.out.println("__Thang__"+request.getParameter("thang")+"___"+obj.getMonth());
		String vungId = util.antiSQLInspection(request.getParameter("vungId"));
		if (vungId == null)
			vungId = "";
		
		obj.setvungId(vungId);
		
		String khuvucId = util.antiSQLInspection(request.getParameter("khuvucId"));
		if (khuvucId == null)
			khuvucId = "";
		obj.setkhuvucId(khuvucId);
		
		String dvdlId = util.antiSQLInspection(request.getParameter("dvdlId"));
		if (dvdlId == null)
			dvdlId = "";
		obj.setdvdlId(dvdlId);
		
		String sql = "";
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		
		if (action.equals("Taomoi"))
		{
			try
			{
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucDatSoVoiChiTieuNPP_" + util.setTieuDe(obj) + ".xlsm");
				OutputStream out = response.getOutputStream();
				String query = setQuery(sql, obj, userId,request);
				CreatePivotTable(out, obj, query);
			} catch (Exception ex)
			{
				obj.setMsg(ex.getMessage());
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				if (!view.equals("TT"))
				{
					String nextJSP = "/TraphacoHYERP/pages/Distributor/DailySalesReport.jsp";
					response.sendRedirect(nextJSP);
				} else
				{
					String nextJSP = "/TraphacoHYERP/pages/Center/DailySalesReport.jsp";
					response.sendRedirect(nextJSP);
				}
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			if (!view.equals("TT"))
			{
				String nextJSP = "/TraphacoHYERP/pages/Distributor/DailySalesReport.jsp";
				response.sendRedirect(nextJSP);
			} else
			{
				String nextJSP = "/TraphacoHYERP/pages/Center/DailySalesReport.jsp";
				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) throws Exception 
	{
		try
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
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO THỰC ĐẠT SO VỚI CHỈ TIÊU NHÀ PHÂN PHỐI");
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("C3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + obj.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
					    
		   
		    cell = cells.getCell("FA1"); cell.setValue("Mien");
		    cell = cells.getCell("FB1"); cell.setValue("Vung");
		    cell = cells.getCell("FC1"); cell.setValue("Tinh/Thanh");
		    cell = cells.getCell("FD1"); cell.setValue("Quan/Huyen");
		    cell = cells.getCell("FE1"); cell.setValue("MaNhaPhanPhoi");
		    cell = cells.getCell("FF1"); cell.setValue("NhaPhanPhoi");
		    cell = cells.getCell("FG1"); cell.setValue("MaChiTieu");
		    cell = cells.getCell("FH1"); cell.setValue("TenChiTieu");
		    cell = cells.getCell("FI1"); cell.setValue("ChiTieu");
		    cell = cells.getCell("FJ1"); cell.setValue("DonViChiTieu");
		    cell = cells.getCell("FK1"); cell.setValue("DoanhSo");
		    cell = cells.getCell("FL1"); cell.setValue("SoCuaHieu");
		    cell = cells.getCell("FM1"); cell.setValue("SoSKU_Ban");
		    cell = cells.getCell("FN1"); cell.setValue("SoSKU_Phu");
		    cell = cells.getCell("FO1"); cell.setValue("SoHoaDon");
		    cell = cells.getCell("FP1"); cell.setValue("Thuong");
		    
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}
	
	
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
	{
		try
		{
			String chuoi = getServletContext().getInitParameter("path") + "\\ThucDatSoVoiChiTieuNPP.xlsm";
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateStaticHeader(workbook,obj); 		
	 		FillData(workbook,obj.getFieldShow(),query,obj); 	
			workbook.save(out);
			fstream.close();
		} catch (Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}
	}
	
	private String setQuery(String sql, IStockintransit obj, String userId,HttpServletRequest request)
	{
		
		String view = request.getParameter("view");
		if (view == null)
			view = "";
		
		String condition ="";
		if(obj.getnppId().length()>0)
		{
			if (!view.equals("TT"))
				condition+=" and npp.TRUCTHUOC_FK='"+obj.getnppId()+"'";
			else 
				condition+=" and npp.pk_Seq='"+obj.getnppId()+"'";
			
		}
		if(obj.getvungId().length()>0)
		{
			condition+=" and v.pk_seq='"+obj.getvungId()+"'";
		}
		if(obj.getkhuvucId().length()>0)
		{
			condition+=" and kv.pk_seq='"+obj.getvungId()+"'";
		}
		
		String condition_CT ="";
		if(obj.getYear().length()>0)
		{
			condition_CT+=" and a.nam='"+obj.getYear()+"'";
		}
		if(obj.getMonth().length()>0&&!obj.gettype().equals("2"))
		{
			condition_CT+=" and a.thang='"+obj.getMonth()+"'";
		}
		if(obj.getQuy().length()>0)
		{
			condition_CT+=" and a.Quy='"+obj.getQuy()+"'";
		}
		
		String condition_TD ="";
		if(obj.getYear().length()>0)
		{
			condition_TD+=" AND DATEPART(YEAR,a.ngayxuathd)='"+obj.getYear()+"' and d.Nam='"+obj.getYear()+"' ";
		}
		if(obj.getMonth().length()>0&&!obj.gettype().equals("2"))
		{
			condition_TD+=" AND DATEPART(MONTH,a.ngayxuathd)='"+obj.getMonth()+"' and d.THANG='"+obj.getMonth()+"'  ";
		}
		if(obj.getQuy().length()>0)
		{
			if(obj.getQuy().equals("1"))
			{
				condition_TD+=" and D.Quy='"+obj.getQuy()+"' and DATEPART(MONTH,a.ngayxuathd) >='1' and DATEPART(MONTH,a.ngayxuathd) <='3' ";
			}else  if(obj.getQuy().equals("2"))
			{
				condition_TD+="  and D.Quy='"+obj.getQuy()+"' and DATEPART(MONTH,a.ngayxuathd) >='4' and DATEPART(MONTH,a.ngayxuathd) <='6' ";
			} else if(obj.getQuy().equals("3"))
			{
				condition_TD+=" and D.Quy='"+obj.getQuy()+"' and DATEPART(MONTH,a.ngayxuathd) >='7' and DATEPART(MONTH,a.ngayxuathd) <='9' ";
			}
			 else if(obj.getQuy().equals("4"))
				{
				 condition_TD+=" and D.Quy='"+obj.getQuy()+"' and DATEPART(MONTH,a.ngayxuathd) >='10' and DATEPART(MONTH,a.ngayxuathd) <='12' ";
				}
		}
		
		
		String query=
				 "select v.TEN as vTEN,kv.TEN as kvTEN,tt.TEN as ttTEN,qh.TEN as qhTEN,npp.MA as nppMA,npp.TEN as nppTEN   \n "+      
			   "   		,ct.ctMA,ct.ctTEN,ct.dvCT,ct.chitieu,td.*,   \n "+  
			   " (  "+
		     "			select thuong from v_TieuChiThuong v "+
		     "			where  "+
		     "		case when ct.dvCT=N'Doanh số' then (AVAT_DoanhSo/ct.ChiTieu)*100.0 "+
		     "				when ct.dvCT=N'Sản phẩm' then (SoSKU/ct.ChiTieu)*100.0 "+
		     "				when ct.dvCT=N'Số cửa hiệu' then (SoCuaHang/ct.ChiTieu)*100.0 end >=v.TuMuc "+
		     "			and case when ct.dvCT=N'Doanh số' then (AVAT_DoanhSo/ct.ChiTieu)*100.0 "+
		     "				when ct.dvCT=N'Sản phẩm' then (SoSKU/ct.ChiTieu)*100.0 "+
		     "				when ct.dvCT=N'Số cửa hiệu' then (SoCuaHang/ct.ChiTieu)*100.0 end <=v.DenMuc and v.chitieu_fk=ct.ctId "+
		     " 	)as Thuong   "+ 
			   "   from    \n "+      
			   "   (   \n "+      
			   "   	select a.pk_Seq as ctId,A.Ma as ctMa,a.Ten as ctTEN,b.npp_fk,b.chitieu,c.ten as dvCT   \n "+      
			   "   	from chitieu a inner join chitieu_doituong b on b.chitieu_fk=a.pk_seq   \n "+      
			   "   		inner join DonViChiTieu  c on c.PK_sEQ=b.DonViTinh_ChiTieu   \n "+      
			   "   	where  b.npp_fk is not null and a.loaichitieu=0 and b.chitieu>0 "+condition_CT+"  \n "+      
			   "   )as ct inner join   \n "+      
			   "   (	   \n "+      
			   "   	select ctId,NPP_FK,SUM(BVAT_DoanhSo) as BVAT_DoanhSo,   \n "+      
			   "   			sum(VAT_DoanhSo) as VAT_DoanhSo,sum(AVAT_DoanhSo) as AVAT_DoanhSo,sum(SoCuaHang) as SoCuaHang,   \n "+      
			   "   			sum(SoSKU) as SoSKU,sum(SKU) as SKU,sum(SoHoaDon) as SoHoaDon   \n "+      
			   "   	from    \n "+      
			   "   	(   \n "+      
			   "   		select	c.chitieu_fk as ctId,a.NPP_FK,SUM(ROUND(b.SOLUONG*b.DONGIA,0)) as BVAT_DoanhSo,   \n "+      
			   "   				SUM(ROUND( ROUND(b.SOLUONG*b.DONGIA,0)*(b.VAT/100.0),0)) as VAT_DoanhSo,   \n "+      
			   "   				SUM(ROUND( ROUND(b.SOLUONG*b.DONGIA,0)*(1+b.VAT/100.0),0)) as AVAT_DoanhSo,   \n "+      
			   "   				COUNT(distinct a.khachhang_fk) as SoCuaHang,   \n "+      
			   "   				COUNT(distinct b.SANPHAM_FK) as SoSKU,   \n "+      
			   "   				COUNT(b.SANPHAM_FK) as SKU,   \n "+      
			   "   				COUNT(distinct b.HOADON_FK) as SoHoaDon   \n "+      
			   "   		from HOADON a inner join HOADON_SP b on b.HOADON_FK=a.PK_SEQ   \n "+      
			   "   			inner join CHITIEU_SANPHAM c  on c.sanpham_fk=b.SANPHAM_FK   \n "+      
			   "   			inner join CHITIEU d on d.pk_Seq=c.chitieu_fk   \n "+      
			   "   		where	a.TRANGTHAI not in (1,3,5) and a.LOAIHOADON=0 and  d.loaichitieu=0   "+condition_TD+" \n "+      
			   "   		group by a.NPP_FK,c.chitieu_fk   \n "+      
			   "      \n "+      
			   "   		union all	   \n "+      
			   "   		select	c.chitieu_fk as ctId,a.NPP_FK,SUM(ROUND(b.SOLUONG*b.DONGIA,0)) as BVAT_DoanhSo,   \n "+      
			   "   				SUM(ROUND( ROUND(b.SOLUONG*b.DONGIA,0)*(b.VAT/100.0),0)) as VAT_DoanhSo,   \n "+      
			   "   				SUM(ROUND( ROUND(b.SOLUONG*b.DONGIA,0)*(1+b.VAT/100.0),0)) as AVAT_DoanhSo,   \n "+      
			   "   				COUNT(distinct a.khachhang_fk) as SoCuaHang,   \n "+      
			   "   				COUNT(distinct b.SANPHAM_FK) as SoSKU,   \n "+      
			   "   				COUNT(b.SANPHAM_FK) as SKU,   \n "+      
			   "   				COUNT(distinct b.HOADON_FK) as SoHoaDon   \n "+      
			   "   		from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP b on b.HOADON_FK=a.PK_SEQ   \n "+      
			   "   			inner join CHITIEU_SANPHAM c  on c.sanpham_fk=b.SANPHAM_FK   \n "+      
			   "   			inner join CHITIEU d on d.pk_Seq=c.chitieu_fk   \n "+      
			   "   		where	a.TRANGTHAI not in (1,3,5) and a.LOAIHOADON=0   \n "+      
			   "   				and d.loaichitieu=0   "+condition_TD+"  \n "+      
			   "   		group by a.NPP_FK,c.chitieu_fk   \n "+      
			   "   	) as td    \n "+      
			   "   	group by td.NPP_FK,td.ctId   \n "+      
			   "   )as td on ct.npp_fk=td.npp_fk and ct.ctId=td.ctId   \n "+      
			   "   inner join NHAPHANPHOI npp on npp.PK_SEQ=ct.npp_fk   \n "+      
			   "   inner join KHUVUC kv on kv.PK_SEQ=npp.KHUVUC_FK   \n "+      
			   "   inner join VUNG v on v.PK_SEQ=kv.VUNG_FK   \n "+      
			   "   inner join TINHTHANH tt on tt.PK_SEQ=npp.TINHTHANH_FK   \n "+      
			   "   inner join QUANHUYEN qh on qh.PK_SEQ=npp.QUANHUYEN_Fk " +
			   " where 1=1 "+condition+" ";
		
		System.out.println("_BAO CAO_THUC_DAT_CHI_TIEU__"+query);
		
		return query;
	}
	
	private void FillData(Workbook workbook,String[] fieldShow, String query,IStockintransit obj)throws Exception 
	{
		try
		{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    dbutils db = new dbutils();
		    ResultSet    rs = db.get(query);
		    int index = 2;
		    Cell cell = null;	    
		    while (rs.next()) 
		    {		    	
		    	cell = cells.getCell("FA" + Integer.toString(index));cell.setValue(rs.getString("vTEN"));
		    	cell = cells.getCell("FB" + Integer.toString(index));cell.setValue(rs.getString("kvTEN"));
		    	cell = cells.getCell("FC" + Integer.toString(index));cell.setValue(rs.getString("ttTEN"));
		    	cell = cells.getCell("FD" + Integer.toString(index));cell.setValue(rs.getString("kvTEN"));
		    	cell = cells.getCell("FE" + Integer.toString(index));cell.setValue(rs.getString("nppMa"));
		    	cell = cells.getCell("FF" + Integer.toString(index));cell.setValue(rs.getString("nppTEN"));
		    	cell = cells.getCell("FG" + Integer.toString(index));cell.setValue(rs.getString("ctMa"));
		    	cell = cells.getCell("FH" + Integer.toString(index));cell.setValue(rs.getString("ctTEN"));
		    	cell = cells.getCell("FI" + Integer.toString(index));cell.setValue(rs.getDouble("ChiTieu"));
		    	cell = cells.getCell("FJ" + Integer.toString(index));cell.setValue(rs.getString("dvCT"));
		    	cell = cells.getCell("FK" + Integer.toString(index));cell.setValue(rs.getDouble("AVAT_DoanhSo"));
		    	cell = cells.getCell("FL" + Integer.toString(index));cell.setValue(rs.getDouble("SoCuaHang"));
		    	cell = cells.getCell("FM" + Integer.toString(index));cell.setValue(rs.getDouble("SKU"));
		    	cell = cells.getCell("FN" + Integer.toString(index));cell.setValue(rs.getDouble("SoSKU"));
		    	cell = cells.getCell("FO" + Integer.toString(index));cell.setValue(rs.getDouble("SoHoaDon"));
		    	cell = cells.getCell("FP" + Integer.toString(index));cell.setValue(rs.getDouble("Thuong"));
		    	
				index++;
			}
		    if(rs!=null)
		    {
		    	rs.close();
		    }
		    
		    if(db != null) db.shutDown();
		    
		    if(index==2)
		    {
		    	throw new Exception("Xin loi. Khong co bao cao voi dieu kien da chon...!!!");
		    }
		    ReportAPI.setHidden(workbook, 50);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
		}	
	}
}
