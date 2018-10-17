package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.WorkbookSettings;
import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.DateFormat;
import jxl.write.DateTime;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;



@WebServlet("/BcDuLieuImport")
public class BcDuLieuImport extends HttpServlet
{
	private static final long serialVersionUID = 1L;
	
	public BcDuLieuImport()
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
		
		String view = request.getParameter("view");
		if(view == null)
			view = "";
		
		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		obj.setuserId(userId);
		obj.init();
		
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		
		if(!view.equals("TT"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BcDuLieuImport.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/BcDuLieuImport.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();
		String userId = (String) session.getAttribute("userId");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		
		Utility util = new Utility();
		
		obj.setuserId((String) session.getAttribute("userId") == null ? "" : (String) session.getAttribute("userId"));
		
		obj.setuserTen((String) session.getAttribute("userTen") == null ? "" : (String) session.getAttribute("userTen"));
		
		String view=request.getParameter("view");
		if(view == null)
			view = "";
		
		String nppId ="";
		if(view.equals("TT"))
		{
			obj.setnppId(util.antiSQLInspection(request.getParameter("nppId")) == null ? "" : util.antiSQLInspection(request.getParameter("nppId")));
		}else
		{
			nppId=util.getIdNhapp(userId);
			obj.setnppId(nppId);
		}
		
		
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId")) == null ? "" : util.antiSQLInspection(request.getParameter("kenhId")));
		
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvkdId")));
		
		obj.setMonth(util.antiSQLInspection(request.getParameter("month")) == null ? "" : util.antiSQLInspection(request.getParameter("month")));
		
		obj.setYear(util.antiSQLInspection(request.getParameter("year")) == null ? "" : util.antiSQLInspection(request.getParameter("year")));
		
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId")) == null ? "" : util.antiSQLInspection(request.getParameter("vungId")));
		
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId")) == null ? "" : util.antiSQLInspection(request.getParameter("khuvucId")));
		
		obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlId")) == null ? "" : util.antiSQLInspection(request.getParameter("dvdlId")));
		
		obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId")) == null ? "" : util.antiSQLInspection(request.getParameter("ddkdId")));
		
		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId")) == null ? "" : util.antiSQLInspection(request.getParameter("gsbhId")));
		
		obj.setBMId(util.antiSQLInspection(request.getParameter("bmId")) == null ? "" : util.antiSQLInspection(request.getParameter("bmId")));
		
		obj.setASMId(util.antiSQLInspection(request.getParameter("asmId")) == null ? "" : util.antiSQLInspection(request.getParameter("asmId")));
		
		obj.setFromMonth(util.antiSQLInspection(request.getParameter("tuthang")) == null ? "" : util.antiSQLInspection(request.getParameter("tuthang")));
		obj.setFromYear(util.antiSQLInspection(request.getParameter("tunam")) == null ? "" : util.antiSQLInspection(request.getParameter("tunam")));
		
		obj.setToMonth(util.antiSQLInspection(request.getParameter("denthang")) == null ? "" : util.antiSQLInspection(request.getParameter("denthang")));
		obj.setToYear(util.antiSQLInspection(request.getParameter("dennam")) == null ? "" : util.antiSQLInspection(request.getParameter("dennam")));
		
		obj.settype(util.antiSQLInspection(request.getParameter("type")) == null ? "" : util.antiSQLInspection(request.getParameter("type")));
		
		obj.settungay(util.antiSQLInspection(request.getParameter("Sdays")) == null ? "" : util.antiSQLInspection(request.getParameter("Sdays")));
		obj.setdenngay(util.antiSQLInspection(request.getParameter("Edays")) == null ? "" : util.antiSQLInspection(request.getParameter("Edays")));
		
		
		obj.setkhoId(util.antiSQLInspection(request.getParameter("khoId")) == null ? "" : util.antiSQLInspection(request.getParameter("khoId")));
		
		obj.setLaytheo(util.antiSQLInspection(request.getParameter("laysolo")) != null ? util.antiSQLInspection(request.getParameter("laysolo")) : "");
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		
		if (action.equals("Taomoi"))
		{
			try
			{
				if(obj.gettype().equals("0"))
				{
					PhieuDieuChuyen(response, obj);
				}
				else if(obj.gettype().equals("1")||obj.gettype().equals("5"))
				{ 
					if(!obj.getLaytheo().equals("1"))
					{
						HoaDon(response, obj);
					}
					else
					{
						HoaDon_ChiTiet(response, obj);
					}
				}
				else if(obj.gettype().equals("2")||obj.gettype().equals("3")||obj.gettype().equals("4"))
				{ 
					PhieuThu(response, obj);
				}
				
			} catch (Exception ex)
			{
				ex.printStackTrace();
				obj.setMsg(ex.getMessage());
				obj.init();
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Center/BcDuLieuImport.jsp";
				response.sendRedirect(nextJSP);
			}
		} else
		{
			obj.init();
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Center/BcDuLieuImport.jsp";
			response.sendRedirect(nextJSP);
		}
		
	}
	
	private void PhieuThu(HttpServletResponse response,IStockintransit obj) throws Exception
	{
		OutputStream outstream = null;
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));			
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=PhieuThu.xls");
		outstream = response.getOutputStream();
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(response.getOutputStream());
		
		WritableSheet 	sheet = workbook.createSheet("PhieuThu",1);
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		cellFont.setColour(Colour.BLACK);
		
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		
		cellFormat.setBackground(jxl.format.Colour.GRAY_50);
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		
		sheet.addCell(new Label(0, 0, "Ngay_ct",cellFormat));
		sheet.addCell(new Label(1, 0, "So_ct",cellFormat));
		sheet.addCell(new Label(2, 0, "Ma_kh",cellFormat));
		sheet.addCell(new Label(3, 0, "Ong_ba",cellFormat));
		sheet.addCell(new Label(4, 0, "Dien_giai",cellFormat));
		
		sheet.addCell(new Label(5, 0, "Tk",cellFormat));
		sheet.addCell(new Label(6, 0, "Ma_nt",cellFormat));
		sheet.addCell(new Label(7, 0, "Ty_gia",cellFormat));
		sheet.addCell(new Label(8, 0, "Tk_i",cellFormat));
		sheet.addCell(new Label(9, 0, "Ma_vv_i",cellFormat));
		
		sheet.addCell(new Label(10, 0, "Ma_phi",cellFormat));
		sheet.addCell(new Label(11, 0, "ma_bpht_i",cellFormat));
		sheet.addCell(new Label(12, 0, "Tien_nt",cellFormat));
		sheet.addCell(new Label(13, 0, "Tien",cellFormat));
		
		dbutils db = new dbutils();
		
		String query = 
				"		select a.NGAYCHUNGTU as Ngay_ct,a.pk_seq as So_ct,c.maFAST as Ma_kh, "+
						"		c.TEN as Ong_ba,a.GhiChu as Dien_giai,case when HINHTHUCTT=N'Tiền mặt' THEN '1111'   when HINHTHUCTT=N'Chuyển khoản' THEN '1121' ELSE '' END as TK,'VND' as Ma_NT,1 as Ty_Gia,(select MaNX from NhaPhanPHOI where pk_Seq=a.npp_fk) as tk_i,'' as Ma_vv_i,'' as Ma_phi,  "+
						"		'' as ma_bpht_i,b.SOTIENTT as Tien_nt,b.SOTIENTT as Tien "+ 
						"	from ERP_THUTIENNPP a inner join ERP_THUTIENNPP_HOADON b on b.THUTIENNPP_FK=a.PK_SEQ "+
						"	inner join KHACHHANG c on c.PK_SEQ=b.KHACHHANG_FK where a.trangthai=1  ";
		if(obj.gettungay().length()>0)
		{
			query+=" and a.NGAYCHUNGTU>='"+obj.gettungay()+"' ";
		}
		
		if(obj.getdenngay().length()>0)
		{
			query+=" and a.NGAYCHUNGTU<='"+obj.getdenngay()+"' ";
		}
		if(obj.getnppId().length()>0)
		{
			query+=" and a.NPP_FK ='"+obj.getnppId()+"' ";
		}
		
		if(obj.gettype().equals("2"))
		{
			query+=" and a.HINHTHUCTT=N'Tiền mặt' ";
		}
		
		if(obj.gettype().equals("3"))
		{
			query+=" and HINHTHUCTT=N'Chuyển khoản' ";
		}
		
		
		if(obj.gettype().equals("4"))
		{
			query=
					" SELECT  ct.NGAYCHUNGTU as Ngay_ct,ct.pk_seq as So_CT ,  "+ 
							"	kh.maFAST as Ma_kh, kh.TEN as Ong_ba  ,CT.GHICHU as Dien_Giai,'635' as TK,'VND' as MA_NT,1 AS Ty_Gia,(select MaNX from NhaPhanPHOI where pk_Seq=ct.npp_fk) as tk_i,  "+
							"  '' as Ma_vv_i,'' as Ma_phi,  	  "+
							"	'' as ma_bpht_i,ct_hd.SOTIENCANTRU as Tien_nt,ct_hd.SOTIENCANTRU as Tien  "+
							"  FROM 	  CANTRUCONGNO  ct inner join CANTRUCONGNO_HOADON ct_hd on ct.PK_SEQ = ct_hd.CANTRUCONGNO_FK  "+ 
							"	 			   inner join HOADON hd on ct_hd.HOADON_FK = hd.PK_SEQ   "+
							"	 			   inner join KHACHHANG kh on ct_hd.KHACHHANG_FK = kh.PK_SEQ  "+ 
							"  WHERE   ct.trangthai=1   ";
			
			if(obj.gettungay().length()>0)
			{
				query+=" and ct.NGAYCHUNGTU>='"+obj.gettungay()+"' ";
			}
			
			if(obj.getdenngay().length()>0)
			{
				query+=" and ct.NGAYCHUNGTU<='"+obj.getdenngay()+"' ";
			}
			if(obj.getnppId().length()>0)
			{
				query+=" and ct.NPP_FK ='"+obj.getnppId()+"' ";
			}
		}
		System.out.println("[Query]"+query);
		int rowIndex=1;
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					
					sheet.addCell(new Label(0, rowIndex, getFormatDate( rs.getString("Ngay_ct")))); 
					sheet.addCell(new Label(1, rowIndex, rs.getString("So_ct")));
					sheet.addCell(new Label(2, rowIndex, rs.getString("Ma_kh")));
					/*sheet.addCell(new Label(3, rowIndex, rs.getString("Ong_ba")));*/
					sheet.addCell(new Label(4, rowIndex,""));
					sheet.addCell(new Label(5, rowIndex, rs.getString("Tk")));
					sheet.addCell(new Label(6, rowIndex, rs.getString("Ma_nt")));
					sheet.addCell(new Label(7, rowIndex, rs.getString("Ty_gia")));
					sheet.addCell(new Label(8, rowIndex, rs.getString("Tk_i")));
					sheet.addCell(new Label(9, rowIndex, rs.getString("Ma_vv_i")));
					sheet.addCell(new Label(10, rowIndex, rs.getString("Ma_phi")));
					sheet.addCell(new Label(11, rowIndex, rs.getString("ma_bpht_i")));
					sheet.addCell(new Number(12, rowIndex,  rs.getDouble("Tien_nt")));											
					sheet.addCell(new Number(13, rowIndex,  rs.getDouble("Tien")));
					
					rowIndex++;
					
				}
				rs.close();
				workbook.write();		
				workbook.close();
				if(rowIndex==1)
				{					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
			} catch (Exception e)
			{
				
				e.printStackTrace();
			}
			finally
			{
				if(db!=null)db.shutDown();
				if (outstream != null)
					outstream.close();
			}
		}
	}
	
	private void HoaDon(HttpServletResponse response,IStockintransit obj) throws Exception
	{
		System.out.println("vao TTT");
		OutputStream outstream = null;
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));			
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=HoaDon.xls");
		outstream = response.getOutputStream();
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(response.getOutputStream());
		
		WritableSheet 	sheet = workbook.createSheet("HoaDon",1);
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		cellFont.setColour(Colour.BLACK);
		
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setBackground(jxl.format.Colour.GRAY_50);
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		sheet.addCell(new Label(0, 0, "Ma_kh",cellFormat));
		sheet.addCell(new Label(1, 0, "Ong_ba",cellFormat));
		sheet.addCell(new Label(2, 0, "Dia_chi",cellFormat));
		sheet.addCell(new Label(3, 0, "So_seri",cellFormat));
		sheet.addCell(new Label(4, 0, "So_ct",cellFormat));
		sheet.addCell(new Label(5, 0, "Ngay_ct",cellFormat));
		sheet.addCell(new Label(6, 0, "Ma_NT",cellFormat));
		sheet.addCell(new Label(7, 0, "Ty_gia",cellFormat));
		sheet.addCell(new Label(8, 0, "So_luong",cellFormat));
		sheet.addCell(new Label(9, 0, "Gia_nt2",cellFormat));
		sheet.addCell(new Label(10, 0, "Tien_nt2",cellFormat));
		sheet.addCell(new Label(11, 0, "sl_td1_i",cellFormat));
		sheet.addCell(new Label(12, 0, "Ma_thue",cellFormat));
		sheet.addCell(new Label(13, 0, "Gia_nt",cellFormat));
		sheet.addCell(new Label(14, 0, "Tien_nt",cellFormat));
		sheet.addCell(new Label(15, 0, "Ma_nx",cellFormat));
		sheet.addCell(new Label(16, 0, "Tk_dt",cellFormat));
		sheet.addCell(new Label(17, 0, "Tk_vt",cellFormat));
		sheet.addCell(new Label(18, 0, "Tk_gv",cellFormat));
		sheet.addCell(new Label(19, 0, "Tk_ck",cellFormat));
		sheet.addCell(new Label(20, 0, "Tk_thue_co",cellFormat));
		sheet.addCell(new Label(21, 0, "Ma_kho",cellFormat));
		sheet.addCell(new Label(22, 0, "Ma_vt",cellFormat));
		sheet.addCell(new Label(23, 0, "Dien_giai",cellFormat));
		sheet.addCell(new Label(24, 0, "Han_tt",cellFormat));
		sheet.addCell(new Label(25, 0, "Ma_gd",cellFormat));
		sheet.addCell(new Label(26, 0, "Ma_vv_i",cellFormat));
		sheet.addCell(new Label(27, 0, "Ma_phi",cellFormat));
		sheet.addCell(new Label(28, 0, "ma_bpht_i",cellFormat));
		
		
		int rowIndex = 1;
		
		
		dbutils db = new dbutils();
		
		
		String condition ="";
		if(obj.gettungay().length()>0)
		{
			condition+=" and a.NgayXuatHD>='"+obj.gettungay()+"' ";
		}
		
		if(obj.getdenngay().length()>0)
		{
			condition+=" and a.NgayXuatHD <='"+obj.getdenngay()+"' ";
		}
		
		if(obj.getnppId().length()>0)
		{
			condition+=" and a.npp_fk ='"+obj.getnppId()+"' ";
		}
		
		if(obj.getkhoId().length()>0)
		{
			condition+=" and a.pk_Seq in (select hoadon_fk from hoadon_ddh where ddh_fk in (select pk_seq from donhang where kho_fk='"+obj.getkhoId()+"' ))  ";
		}
		
		
		String conditionE ="";
		if(obj.gettungay().length()>0)
		{
			conditionE+=" and a.NgayXuatHD >='"+obj.gettungay()+"' ";
		}
		
		if(obj.getdenngay().length()>0)
		{
			conditionE+=" and a.NgayXuatHD <='"+obj.getdenngay()+"' ";
		}
		
		if(obj.getnppId().length()>0)
		{
			conditionE+=" and a.npp_fk ='"+obj.getnppId()+"' ";
		}
		
		if(obj.getkhoId().length()>0)
		{
			conditionE+=" and a.pk_Seq in (select hoadonnpp_Fk from erp_hoadonnpp_ddh where ddh_fk in (select pk_seq from Erp_DonDatHangNpp where kho_fk='"+obj.getkhoId()+"' ))  ";
		}
		
		if(obj.getkhoId().length()>0&&obj.getnppId().equals("106210"))
		{
			String makho="KHO03";
			if(obj.getkhoId().equals("100000"))
			{
				makho="KHO60";
			}else 	if(obj.getkhoId().equals("100002"))
			{
				makho="KHO59";
			}
			String query="update NHAPHANPHOI set MaKho='"+makho+"'     where PK_SEQ='"+obj.getnppId()+"'";   
			db.update(query);
		}
		
		String query = 
				"select c.HoaDon_Fk , b.mafast as ma_kh,b.ten as ong_ba,b.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,     \n  "+    
						"   	a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,c.soluong as so_luong,cast(ROUND(c.dongia ,4) as numeric(18,4)) as gia_nt2,      \n  "+    
						"   	cast(ROUND( c.soluong*c.dongia ,0) as numeric(18,2)) as tien_nt2,0 as sl_td1_i,CAST(c.VAT AS INT) as ma_thue,E.MaNX as ma_nx,     \n  "+    
						"   	c.soluong as so_luong,c.donvitinh,0 as gia_nt , 0 as tien_nt,c.vat,c.chietkhau,     \n  "+    
						"   	d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,     \n  "+    
						"   	e.makho as ma_kho,d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i ,1 as Type     \n  "+    
						"   from hoadon a inner join khachhang b on b.pk_seq=a.khachhang_fk     \n  "+    
						"   	inner join hoadon_sp c on c.hoadon_fk=a.pk_seq     \n  "+    
						"   	inner join sanpham d on d.pk_seq=c.sanpham_fk     \n  "+    
						"   	inner join nhaphanphoi e on e.pk_seq=a.npp_fk     \n  "+    
						"   where a.trangthai in (2,4) and a.SoHoaDon!='NA' and a.LoaiHoaDon=0 "+condition+"     \n  "+    
						"        \n  "+    
						"   union all     \n  "+    
						"        \n  "+    
						"   select c.HoaDon_Fk , b.mafast as ma_kh,b.ten as ong_ba,b.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,      \n  "+    
						"   	a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,c.soluong as so_luong,      \n  "+    
						"   	cast(ROUND(c.dongia ,4) as numeric(18,4)) as gia_nt2,cast(ROUND( c.	soluong*c.dongia ,0) as numeric(18,2)) as tien_nt2,     \n  "+    
						"   	0 as sl_td1_i,CAST(c.VAT AS INT) as ma_thue,'6418' as ma_nx, c.soluong as so_luong,c.DONVI as DonViTinh,     \n  "+    
						"   	0 as gia_nt , 0 as tien_nt,c.vat,0 as ck, d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,     \n  "+    
						"   	d.tkthueco as tk_thue_co ,e.makho as ma_kho ,d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,     \n  "+    
						"   	'' as ma_bpht_i ,1 as Type     \n  "+    
						"   from hoadon a inner join khachhang b on b.pk_seq=a.khachhang_fk     \n  "+    
						"   inner join HOADON_CTKM_TRAKM c on c.hoadon_fk=a.pk_seq     \n  "+    
						"   inner join sanpham d on d.pk_seq=c.sanpham_fk     \n  "+    
						"   inner join nhaphanphoi e on e.pk_seq=a.npp_fk     \n  "+    
						"   where a.trangthai in (2,4) and a.SoHoaDon!='NA' and a.LOAIHOADON=1 "+condition+"     \n  "+    
						"   union all     \n  "+    
						"			select b.HoaDon_Fk ,e.maFAST as ma_kh,e.TEN as Ong_Ba,e.DIACHI as Dia_Chi,a.KYHIEU as So_Seri,a.SOHOADON as So_CT,a.NGAYXUATHD as Ngay_CT,'VND' as Ma_NT,  \n "+
						"				1 as Ty_Gia,1 as SoLuong, (-1)* SUM( cast(ROUND( b.chietkhau,2) as numeric(18,0))) as Gia_NT2,   \n "+
						"				(-1)* SUM( cast(ROUND( b.chietkhau ,0) as numeric(18,0))) as Tien_NT2,       \n "+
						"				0 as	SL_TD1_I, b.THUEVAT as Ma_Thue,f.MaNX AS Ma_NX,1 as SoLuong,'' as DonViTinh, 0 as Gia_NT,0 AS Tien_NT,b.THUEVAT,0 as CK,'' as TK_DT,  \n "+     
						"				l.TK_VT AS TK_VT,l.tk_gv as TK_GV,l.TK_CK as TK_CK,'' AS TK_THUE_Co,f.MaKho as Ma_Kho,       \n "+
						"				b.diengiai as Ma_VT,l.Ten As DienGiai,0 as Han_TT,'' as Ma_GD,'' as Ma_VV_I,'' as Ma_Phi,'' as Ma_BPHT_I,2 as Type \n "+     
						"			from HOADON a inner join HOADON_CHIETKHAU b on b.hoadon_fk=a.PK_SEQ   \n "+
						"			inner join KHACHHANG e on e.PK_SEQ=a.KHACHHANG_FK       \n "+
						"				inner join NHAPHANPHOI f on f.PK_SEQ=a.NPP_FK       \n "+
						"				left join LoaiCK l on l.Ma=b.diengiai  \n "+
						"			where  a.TRANGTHAI in (2,4) and a.SoHoaDon!='NA' and a.LOAIHOADON=0  "+condition+"  \n "+  
						"			and isnull(b.HIENTHI,0)=1  \n "+
						"			group by e.maFAST ,e.TEN ,e.DIACHI ,a.KYHIEU ,a.SOHOADON ,a.NGAYXUATHD ,f.MaNX,f.MaKho,b.THUEVAT , b.HoaDon_Fk ,l.Ten,b.diengiai  ,l.TK_VT,l.tk_gv,l.TK_CK  \n "+
						"        \n  "+    
						"   union all     \n  "+    
						"        \n  "+    				   
						"			select c.HOADON_FK,f.mafast as ma_kh,f.ten as ong_ba,f.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,  \n"+     
						"							a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,      \n"+
						"						case when c.donvitinh = e.donvi then c.soluong else c.soluong *   \n"+
						"								( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as so_luong,  \n"+ 
						"						case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and  \n"+ 
						"									DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as gia_nt2,  \n"+
						"						cast(ROUND(( c.soluong*c.dongia-isnull(c.ChietKhau,0) ),2) as numeric(18,0) ) as tien_nt2,0 as	sl_td1_i,  \n"+
						"								CAST(c.VAT AS INT) as ma_thue,g.MaNX as ma_nx,c.soluong as so_luong,e.DONVI as DonViTinh, 0 as gia_nt ,       \n"+
						"							0 as tien_nt,c.vat,c.chietkhau,d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,g.makho as ma_kho ,  \n"+     
						"							d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i,1 as Type  \n"+     		
						"				from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  \n"+ 
						"					inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n"+
						"					inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
						"					inner join KHACHHANG f on f.PK_SEQ=a.KHACHHANG_FK   \n"+
						"					inner join NHAPHANPHOI g on g.PK_SEQ=a.NPP_FK  "+
						"				where 1=1 and a.SoHoaDon!='NA' and a.trangthai not in ( 1 , 3, 5 ) and isnull(LoaiHoaDon,0) =0 and c.SoLuong>0 "+conditionE+"  "+
						"   union all     \n  "+ 
						"			select c.HOADON_FK,f.mafast as ma_kh,f.ten as ong_ba,f.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,  \n"+     
						"							a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,      \n"+
						"						case when c.donvitinh = e.donvi then c.soluong else c.soluong *   \n"+
						"								( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as so_luong,  \n"+ 
						"						case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and  \n"+ 
						"									DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as gia_nt2,  \n"+
						"						cast(ROUND(( c.soluong*c.dongia-isnull(c.ChietKhau,0) ),2) as numeric(18,0) ) as tien_nt2,0 as	sl_td1_i,  \n"+
						"								CAST(c.VAT AS INT) as ma_thue,'6418' as ma_nx,c.soluong as so_luong,e.DONVI as DonViTinh, 0 as gia_nt ,       \n"+
						"							0 as tien_nt,c.vat,c.chietkhau,d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,g.makho as ma_kho ,  \n"+     
						"							d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i,1 as Type  \n"+     		
						"				from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  \n"+ 
						"					inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n"+
						"					inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
						"					inner join KHACHHANG f on f.PK_SEQ=a.npp_dat_fk   \n"+
						"					inner join NHAPHANPHOI g on g.PK_SEQ=a.NPP_FK  "+
						"				where 1=1 and a.SoHoaDon!='NA' and a.trangthai not in ( 1 , 3, 5 )  and isnull(LoaiHoaDon,0) =1  and c.SoLuong>0 "+conditionE+"  "+
						"   union all     \n  "+ 
						"			select c.HOADON_FK,f.mafast as ma_kh,f.ten as ong_ba,f.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,  \n"+     
						"							a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,      \n"+
						"						case when c.donvitinh = e.donvi then c.soluong else c.soluong *   \n"+
						"								( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as so_luong,  \n"+ 
						"						case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and  \n"+ 
						"									DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as gia_nt2,  \n"+
						"						cast(ROUND(( c.soluong*c.dongia-isnull(c.ChietKhau,0) ),2) as numeric(18,0) ) as tien_nt2,0 as	sl_td1_i,  \n"+
						"								CAST(c.VAT AS INT) as ma_thue,'6418' as ma_nx,c.soluong as so_luong,e.DONVI as DonViTinh, 0 as gia_nt ,       \n"+
						"							0 as tien_nt,c.vat,c.chietkhau,d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,g.makho as ma_kho ,  \n"+     
						"							d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i,1 as Type  \n"+     		
						"				from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  \n"+ 
						"					inner join SANPHAM d on c.sanpham_fk = d.pk_seq   \n"+
						"					inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
						"					inner join nhaphanphoi f on f.PK_SEQ=a.npp_dat_fk   \n"+
						"					inner join NHAPHANPHOI g on g.PK_SEQ=a.NPP_FK  "+
						"				where 1=1 and a.SoHoaDon!='NA' and a.trangthai not in ( 1 , 3, 5 )    and c.SoLuong>0 "+conditionE+"  ";

		if(obj.gettype().equals("5"))
			query=
			"   select c.HoaDon_Fk ,b.mafast as ma_kh,b.ten as ong_ba,b.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,     \n  "+    
					"   	a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,c.soluong as so_luong,     \n  "+    
					"   	dongia as gia_nt2,c.soluong*c.dongia as tien_nt2,0 as sl_td1_i,CAST(nh.ThueXuat AS INT) as ma_thue,'6418' as ma_nx,     \n  "+    
					"   	c.soluong as so_luong,c.donvitinh,c.dongia as gia_nt ,round(c.soluong*c.dongia,0) as tien_nt,c.thuesuat,c.chietkhau,     \n  "+    
					"   	d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,e.makho as ma_kho     \n  "+    
					"   	,d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i ,1 as Type     \n  "+    
					"   from erp_hoadon a inner join nhaphanphoi b on b.pk_seq=a.npp_fk     \n  "+    
					"   	inner join erp_hoadon_sp c on c.hoadon_fk=a.pk_seq     \n  "+    
					"   	inner join sanpham d on d.pk_seq=c.sanpham_fk     \n  "+    
					"   	inner join NGANHHANG nh on nh.PK_SEQ=d.NGANHHANG_FK     \n  "+    
					"   	inner join nhaphanphoi e on e.pk_seq=a.npp_fk     \n  "+    
					"   where a.trangthai in (2,4) "+condition+"     \n  "+    
					"        \n  "+    
					"   order by hoadon_fk,type     \n  ";
		
		
		System.out.println("[Query]"+query);
		ResultSet rs = db.get(query);
		DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat); 
		{
			try
			{
				while (rs.next())
				{
					sheet.addCell(new Label(0, rowIndex, rs.getString("Ma_kh")));
					/*sheet.addCell(new Label(1, rowIndex, rs.getString("Ong_ba")));
						sheet.addCell(new Label(2, rowIndex, rs.getString("Dia_chi")));*/
					sheet.addCell(new Label(3, rowIndex, rs.getString("So_seri")));
					sheet.addCell(new Label(4, rowIndex, rs.getString("So_ct")));
					sheet.addCell(new DateTime(5, rowIndex,  rs.getDate("Ngay_ct") ,dateFormat));
					sheet.addCell(new Label(6, rowIndex, 	 rs.getString("Ma_NT")));
					sheet.addCell(new Number(7, rowIndex,  rs.getDouble("Ty_gia")));
					sheet.addCell(new Number(8, rowIndex,  rs.getDouble("So_luong")));
					sheet.addCell(new Number(9, rowIndex, rs.getDouble("Gia_NT2") ));
					sheet.addCell(new Number(10, rowIndex, rs.getDouble("tien_nt2")  ));
					sheet.addCell(new Number(11, rowIndex, rs.getDouble("sl_td1_i")));
					sheet.addCell(new Label(12, rowIndex,  rs.getInt("Ma_thue")<10?"0"+rs.getInt("Ma_thue") : rs.getInt("Ma_thue")+""  ));
					sheet.addCell(new Number(13, rowIndex,	 0  ));
					sheet.addCell(new Number(14, rowIndex,  0 ));
					sheet.addCell(new Label(15, rowIndex,  rs.getString("Ma_nx")));
					sheet.addCell(new Label(16, rowIndex,  rs.getString("Tk_dt")));
					sheet.addCell(new Label(17, rowIndex,  rs.getString("Tk_vt")));
					sheet.addCell(new Label(18, rowIndex,  rs.getString("Tk_gv")));
					sheet.addCell(new Label(19, rowIndex,  rs.getString("Tk_ck")));
					sheet.addCell(new Label(20, rowIndex,  rs.getString("Tk_thue_co")));
					sheet.addCell(new Label(21, rowIndex,  rs.getString("Ma_kho")));
					sheet.addCell(new Label(22, rowIndex,  rs.getString("Ma_vt")));
					sheet.addCell(new Label(23, rowIndex,"" ));
					sheet.addCell(new Number(24, rowIndex, rs.getDouble("Han_tt")));
					sheet.addCell(new Label(25, rowIndex, rs.getString("Ma_gd")));
					sheet.addCell(new Label(26, rowIndex, rs.getString("Ma_vv_i")));
					sheet.addCell(new Label(27, rowIndex, rs.getString("Ma_phi")));
					sheet.addCell(new Label(28, rowIndex, rs.getString("ma_bpht_i")));
					
					rowIndex++;
				}
				rs.close();
				workbook.write();		
				workbook.close();
				if(rowIndex==1)
				{					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
				if(obj.getkhoId().length()>0)
				{
					query="update nhaphanphoi set makho='KHO03' from NHAPHANPHOI where pk_Seq=106210 ";
					db.update(query);
				}
			} catch (Exception e)
			{
				e.printStackTrace();
			}
			finally
			{
				if(db!=null)db.shutDown();
				if (outstream != null)
					outstream.close();
			}
		}
	}
	
	private void HoaDon_ChiTiet(HttpServletResponse response,IStockintransit obj) throws Exception
	{
		System.out.println("vao tttttttttttt ct");
		OutputStream outstream = null;
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));			
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=HoaDon.xls");
		outstream = response.getOutputStream();
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(response.getOutputStream());
		
		WritableSheet 	sheet = workbook.createSheet("HoaDon",1);
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		cellFont.setColour(Colour.BLACK);
		
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		cellFormat.setBackground(jxl.format.Colour.GRAY_50);
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		
		sheet.addCell(new Label(0, 0, "Ma_kh",cellFormat));
		sheet.addCell(new Label(1, 0, "Ong_ba",cellFormat));
		sheet.addCell(new Label(2, 0, "Dia_chi",cellFormat));
		sheet.addCell(new Label(3, 0, "So_seri",cellFormat));
		sheet.addCell(new Label(4, 0, "So_ct",cellFormat));
		sheet.addCell(new Label(5, 0, "Ngay_ct",cellFormat));
		sheet.addCell(new Label(6, 0, "Ma_NT",cellFormat));
		sheet.addCell(new Label(7, 0, "Ty_gia",cellFormat));
		sheet.addCell(new Label(8, 0, "So_luong",cellFormat));
		sheet.addCell(new Label(9, 0, "Gia_nt2",cellFormat));
		sheet.addCell(new Label(10, 0, "Tien_nt2",cellFormat));
		sheet.addCell(new Label(11, 0, "sl_td1_i",cellFormat));
		sheet.addCell(new Label(12, 0, "Ma_thue",cellFormat));
		sheet.addCell(new Label(13, 0, "Gia_nt",cellFormat));
		sheet.addCell(new Label(14, 0, "Tien_nt",cellFormat));
		sheet.addCell(new Label(15, 0, "Ma_nx",cellFormat));
		sheet.addCell(new Label(16, 0, "Tk_dt",cellFormat));
		sheet.addCell(new Label(17, 0, "Tk_vt",cellFormat));
		sheet.addCell(new Label(18, 0, "Tk_gv",cellFormat));
		sheet.addCell(new Label(19, 0, "Tk_ck",cellFormat));
		sheet.addCell(new Label(20, 0, "Tk_thue_co",cellFormat));
		sheet.addCell(new Label(21, 0, "Ma_kho",cellFormat));
		sheet.addCell(new Label(22, 0, "Ma_vt",cellFormat));
		sheet.addCell(new Label(23, 0, "Dien_giai",cellFormat));
		sheet.addCell(new Label(24, 0, "Han_tt",cellFormat));
		sheet.addCell(new Label(25, 0, "Ma_gd",cellFormat));
		sheet.addCell(new Label(26, 0, "Ma_vv_i",cellFormat));
		sheet.addCell(new Label(27, 0, "Ma_phi",cellFormat));
		sheet.addCell(new Label(28, 0, "ma_bpht_i",cellFormat));
		sheet.addCell(new Label(29, 0, "Ma_td_i",cellFormat));
		sheet.addCell(new Label(30, 0, "Ngay_td_i",cellFormat));
		
		
		int rowIndex = 1;
		
		
		dbutils db = new dbutils();
		
		
		String condition ="";
		if(obj.gettungay().length()>0)
		{
			condition+=" and a.NgayXuatHD>='"+obj.gettungay()+"' ";
		}
		
		if(obj.getdenngay().length()>0)
		{
			condition+=" and a.NgayXuatHD <='"+obj.getdenngay()+"' ";
		}
		
		if(obj.getnppId().length()>0)
		{
			condition+=" and a.npp_fk ='"+obj.getnppId()+"' ";
		}
		
		if(obj.getkhoId().length()>0)
		{
			condition+=" and a.pk_Seq in (select hoadon_fk from hoadon_ddh where ddh_fk in (select pk_seq from donhang where kho_fk='"+obj.getkhoId()+"' ))  ";
		}
		
		
		String conditionE ="";
		if(obj.gettungay().length()>0)
		{
			conditionE+=" and a.NgayXuatHD >='"+obj.gettungay()+"' ";
		}
		
		if(obj.getdenngay().length()>0)
		{
			conditionE+=" and a.NgayXuatHD <='"+obj.getdenngay()+"' ";
		}
		
		if(obj.getnppId().length()>0)
		{
			conditionE+=" and a.npp_fk ='"+obj.getnppId()+"' ";
		}
		
		if(obj.getkhoId().length()>0)
		{
			conditionE+=" and a.pk_Seq in (select hoadonnpp_Fk from erp_hoadonnpp_ddh where ddh_fk in (select pk_seq from Erp_DonDatHangNpp where kho_fk='"+obj.getkhoId()+"' ))  ";
		}
		
		if(obj.getkhoId().length()>0&&obj.getnppId().equals("106210"))
		{
			String makho="KHO03";
			if(obj.getkhoId().equals("100000"))
			{
				makho="KHO60";
			}else 	if(obj.getkhoId().equals("100002"))
			{
				makho="KHO59";
			}
			String query="update NHAPHANPHOI set MaKho='"+makho+"'     where PK_SEQ='"+obj.getnppId()+"'";   
			db.update(query);
		}
		
		String query = 
				"select isnull (c.ngayhethan,'') as ngayhethan,c.HoaDon_Fk , b.mafast as ma_kh,b.ten as ong_ba,b.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,     \n "+      
						"    		a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,c.soluong as so_luong,cast(ROUND(c.dongia ,4) as numeric(18,4)) as gia_nt2,      \n "+      
						"    		cast(ROUND( c.soluong*c.dongia ,0) as numeric(18,2)) as tien_nt2,0 as sl_td1_i,CAST(c.THUEVAT AS INT) as ma_thue,E.MaNX as ma_nx,     \n "+      
						"    		c.soluong as so_luong,c.DONVI as DonViTinh,0 as gia_nt , 0 as tien_nt,c.THUEVAT as Vat  ,c.chietkhau,     \n "+      
						"    		d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,     \n "+      
						"    		e.makho as ma_kho,d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i ,1 as Type ,c.SOLO    \n "+      
						"    from hoadon a inner join khachhang b on b.pk_seq=a.khachhang_fk     \n "+      
						"    	inner join HOADON_SP_CHITIET c on c.hoadon_fk=a.pk_seq     \n "+      
						"    	inner join sanpham d on d.ma=c.MA     \n "+      
						"    	inner join nhaphanphoi e on e.pk_seq=a.npp_fk     \n "+      
						"    where  a.trangthai in (2,4) and a.SoHoaDon!='NA' and a.LoaiHoaDon=0  "+condition+"     \n "+      
						"           union all              \n "+      
						"    select isnull (c.ngayhethan,'') as ngayhethan,c.HoaDon_Fk , b.mafast as ma_kh,b.ten as ong_ba,b.diachi as dia_chi,a.kyhieu as so_seri,a.sohoadon as so_ct ,      \n "+      
						"    		a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,c.soluong as so_luong,      \n "+      
						"    		cast(ROUND(c.dongia ,4) as numeric(18,4)) as gia_nt2,cast(ROUND( c.	soluong*c.dongia ,0) as numeric(18,2)) as tien_nt2,     \n "+      
						"    		0 as sl_td1_i,CAST(g.THUEXUAT AS INT) as ma_thue,'6418' as ma_nx, c.soluong as so_luong,f.DONVI as DonViTinh,     \n "+      
						"    		0 as gia_nt , 0 as tien_nt,g.THUEXUAT  AS vat,0 as ck, d.tkdt as tk_dt ,d.tkvt as tk_vt ,d.tkgv as tk_gv,d.tkck as tk_ck ,     \n "+      
						"    		d.tkthueco as tk_thue_co ,e.makho as ma_kho ,d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,     \n "+      
						"    		'' as ma_bpht_i ,1 as Type     ,c.solo\n "+      
						"    from hoadon a inner join khachhang b on b.pk_seq=a.khachhang_fk     \n "+      
						"   	inner join HOADON_CTKM_TRAKM_CHITIET c on c.hoadon_fk=a.pk_seq     \n "+      
						"   	inner join sanpham d on d.pk_seq=c.sanpham_fk     \n "+      
						"   	inner join nhaphanphoi e on e.pk_seq=a.npp_fk   \n "+      
						"   	inner join DONVIDOLUONG f on f.PK_SEQ=d.DVDL_FK\n "+      
						"   	inner join NGANHHANG g on g.PK_SEQ=d.NGANHHANG_FK\n "+      
						"    where a.trangthai in (2,4) and a.SoHoaDon!='NA' and a.LOAIHOADON=1  "+condition+"     \n "+      
						"     union all     \n "+      
						"   select '' as ngayhethan,b.HoaDon_Fk ,e.maFAST as ma_kh,e.TEN as Ong_Ba,e.DIACHI as Dia_Chi,a.KYHIEU as So_Seri,a.SOHOADON as So_CT,\n "+      
						"   	a.NGAYXUATHD as Ngay_CT,'VND' as Ma_NT,\n "+      
						"   	1 as Ty_Gia,1 as SoLuong, (-1)* SUM( cast(ROUND( b.chietkhau,2) as float)) as Gia_NT2,   \n "+      
						"   	(-1)* SUM( cast(ROUND( b.chietkhau ,0) as numeric(18,0))) as Tien_NT2,       \n "+      
						"   	0 as	SL_TD1_I, b.THUEVAT as Ma_Thue,f.MaNX AS Ma_NX,1 as SoLuong,'' as DonViTinh, 0 as Gia_NT,0 AS Tien_NT,b.THUEVAT,0 as CK,'' as TK_DT,  \n "+      
						"   	l.TK_VT AS TK_VT,l.tk_gv as TK_GV,l.TK_CK as TK_CK,'' AS TK_THUE_Co,f.MaKho as Ma_Kho,       \n "+      
						"   	b.diengiai as Ma_VT,l.Ten As DienGiai,0 as Han_TT,'' as Ma_GD,'' as Ma_VV_I,'' as Ma_Phi,'' as Ma_BPHT_I,2 as Type ,'' as SoLo\n "+      
						"   from HOADON a inner join HOADON_CHIETKHAU b on b.hoadon_fk=a.PK_SEQ   \n "+      
						"   	inner join KHACHHANG e on e.PK_SEQ=a.KHACHHANG_FK       \n "+      
						"   	inner join NHAPHANPHOI f on f.PK_SEQ=a.NPP_FK       \n "+      
						"   	left join LoaiCK l on l.Ma=b.diengiai  \n "+      
						"   where  a.TRANGTHAI in (2,4) and a.SoHoaDon!='NA' and a.LOAIHOADON=0  "+condition+"   \n "+      
						"   	and isnull(b.HIENTHI,0)=1  \n "+      
						"   group by e.maFAST ,e.TEN ,e.DIACHI ,a.KYHIEU ,a.SOHOADON ,a.NGAYXUATHD ,f.MaNX,f.MaKho,b.THUEVAT , b.HoaDon_Fk ,l.Ten,b.diengiai  ,\n "+      
						"   	l.TK_VT,l.tk_gv,l.TK_CK       \n "+      
						"   union all     \n "+      
						"   select isnull (b.ngayhethan,'') as ngayhethan, b.hoadon_fk,ISNULL(f.mafast,h.mafast) as ma_kh,ISNULL(f.TEN,h.TEN) as Ong_ba,ISNULL(f.DIACHI,h.DIACHI) as dia_chi,\n "+      
						"   	a.kyhieu as so_seri,a.sohoadon as so_ct ,  a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,\n "+      
						"   	b.SoLuong_Chuan as So_Luong,b.DonGia_Chuan   as gia_nt2,b.SoLuong_Chuan*b.DonGia_Chuan-b.CHIETKHAU  tien_nt2,\n "+      
						"   	0 as sl_td1_i ,b.THUEVAT as ma_thue,\n "+      
						"   	g.MaNX as ma_nx,b.SoLuong_Chuan as so_luong,e.DONVI as DonViTinh, 0 as gia_nt ,\n "+      
						"   	0 as tien_nt,b.THUEVAT as vat ,b.CHIETKHAU ,d.tkdt as tk_dt ,d.tkvt as tk_vt ,\n "+      
						"   	d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,g.makho as ma_kho ,  \n "+      
						"   	d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i,1 as Type        ,b.SOLO\n "+      
						"   from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on b.hoadon_fk=a.PK_SEQ\n "+      
						"   	inner join SANPHAM d on  d.MA= b.ma \n "+      
						"   	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n "+      
						"   	left join KHACHHANG f on f.PK_SEQ=a.KHACHHANG_FK   \n "+      
						"   	left join NHAPHANPHOI h on h.PK_SEQ=a.NPP_DAT_FK\n "+      
						"   	inner join NHAPHANPHOI g on g.PK_SEQ=a.NPP_FK  	\n "+      
						"   where 1=1 and a.SoHoaDon!='NA' and a.trangthai not in ( 1 , 3, 5 ) and b.SoLuong>0 and isnull(a.LoaiHoaDon,0)=0  \n "+      
						"   	"+conditionE+"  \n "+      
						"   union all     \n "+      
						"   select isnull (b.ngayhethan,'') as ngayhethan,b.hoadon_fk,ISNULL(f.mafast,h.mafast) as ma_kh,ISNULL(f.TEN,h.TEN) as Ong_ba,ISNULL(f.DIACHI,h.DIACHI) as dia_chi,\n "+      
						"   	a.kyhieu as so_seri,a.sohoadon as so_ct ,  a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,\n "+      
						"   	b.SoLuong_Chuan as So_Luong,b.DonGia_Chuan   as gia_nt2,b.SoLuong_Chuan*b.DonGia_Chuan-b.CHIETKHAU  tien_nt2,\n "+      
						"   	0 as sl_td1_i ,b.THUEVAT as ma_thue,\n "+      
						"   	'6418' as ma_nx,b.SoLuong_Chuan as so_luong,e.DONVI as DonViTinh, 0 as gia_nt ,\n "+      
						"   	0 as tien_nt,b.THUEVAT as vat ,b.CHIETKHAU ,d.tkdt as tk_dt ,d.tkvt as tk_vt ,\n "+      
						"   	d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,g.makho as ma_kho ,  \n "+      
						"   	d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i,1 as Type        ,b.SOLO\n "+      
						"   from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on b.hoadon_fk=a.PK_SEQ\n "+      
						"   	inner join SANPHAM d on  d.MA= b.ma \n "+      
						"   	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n "+      
						"   	left join KHACHHANG f on f.PK_SEQ=a.KHACHHANG_FK   \n "+      
						"   	left join NHAPHANPHOI h on h.PK_SEQ=a.NPP_DAT_FK\n "+      
						"   	inner join NHAPHANPHOI g on g.PK_SEQ=a.NPP_FK  	\n "+      
						"   where 1=1 and a.SoHoaDon!='NA' and a.trangthai not in ( 1 , 3, 5 ) and isnull(a.LoaiHoaDon,0)=1 and b.SoLuong>0  \n "+      
						"   	"+conditionE+"  \n "+     
				/*		" union all \n"+      
						"   select isnull (b.ngayhethan,'') as ngayhethan,b.hoadon_fk,ISNULL(f.mafast,g.mafast) as ma_kh,ISNULL(f.TEN,g.TEN) as Ong_ba,ISNULL(f.DIACHI,g.DIACHI) as dia_chi,\n "+      
						"   	a.kyhieu as so_seri,a.sohoadon as so_ct ,  a.ngayxuathd as ngay_ct,'VND' as ma_nt, 1 as ty_gia,\n "+      
						"   	b.SoLuong_Chuan as So_Luong,b.DonGia_Chuan   as gia_nt2"+
						
						 ",b.SoLuong_Chuan*b.DonGia_Chuan-b.CHIETKHAU  tien_nt2,\n "+      
						"   	0 as sl_td1_i ,b.THUEVAT as ma_thue,\n "+      
						"   	'6418' as ma_nx,b.SoLuong_Chuan as so_luong,e.DONVI as DonViTinh, 0 as gia_nt ,\n "+      
						"   	0 as tien_nt,b.THUEVAT as vat ,b.CHIETKHAU ,d.tkdt as tk_dt ,d.tkvt as tk_vt ,\n "+      
						"   	d.tkgv as tk_gv,d.tkck as tk_ck ,d.tkthueco as tk_thue_co ,g.makho as ma_kho ,  \n "+      
						"   	d.ma as ma_vt,d.ten as dien_giai,0 as han_tt,'' as ma_gd,'' as ma_vv_i,'' as ma_phi,'' as ma_bpht_i,1 as Type        ,b.SOLO\n "+      
						"   from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on b.hoadon_fk=a.PK_SEQ\n "+      
						"   	inner join SANPHAM d on  d.MA= b.ma \n "+      
						"   	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n "+      
						"		left join nhaphanphoi f on f.PK_SEQ=a.npp_dat_fk   \n"+
						"   	inner join NHAPHANPHOI g on g.PK_SEQ=a.NPP_FK  	\n "+      
						"   where 1=1 and a.SoHoaDon!='NA' and a.trangthai not in ( 1 , 3, 5 ) and isnull(a.LoaiHoaDon,0)=0   and b.SoLuong>0  \n "+      
						"   	"+conditionE+"  \n "+ */  
						"   order by hoadon_fk,type  ";
		System.out.println("[Query]"+query);
		
		ResultSet rs = db.get(query);
		
		DateFormat customDateFormat = new DateFormat ("dd/MM/yyyy");
		WritableCellFormat dateFormat = new WritableCellFormat (customDateFormat); 
		
		DecimalFormat df2 = new DecimalFormat( "#,###,###,##" );
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					
					sheet.addCell(new Label(0, rowIndex, rs.getString("Ma_kh")));
					/*sheet.addCell(new Label(1, rowIndex, rs.getString("Ong_ba")));
						sheet.addCell(new Label(2, rowIndex, rs.getString("Dia_chi")));*/
					sheet.addCell(new Label(3, rowIndex, rs.getString("So_seri")));
					sheet.addCell(new Label(4, rowIndex, rs.getString("So_ct")));
					sheet.addCell(new DateTime(5, rowIndex,  rs.getDate("Ngay_ct") ,dateFormat));
					sheet.addCell(new Label(6, rowIndex, 	 rs.getString("Ma_NT")));
					sheet.addCell(new Number(7, rowIndex,  rs.getDouble("Ty_gia")));
					sheet.addCell(new Number(8, rowIndex,  rs.getDouble("So_luong")));
					//System.out.println("___"+rs.getFloat("Gia_NT2"));
					sheet.addCell(new Number(9, rowIndex, rs.getDouble("Gia_NT2") ));
					sheet.addCell(new Number(10, rowIndex, rs.getDouble("tien_nt2")  ));
					
					sheet.addCell(new Number(11, rowIndex, rs.getDouble("sl_td1_i")));
					sheet.addCell(new Label(12, rowIndex,  rs.getInt("Ma_thue")<10?"0"+rs.getInt("Ma_thue") : rs.getInt("Ma_thue")+""  ));
					sheet.addCell(new Number(13, rowIndex,	 0  ));
					sheet.addCell(new Number(14, rowIndex,  0 ));
					sheet.addCell(new Label(15, rowIndex,  rs.getString("Ma_nx")));
					sheet.addCell(new Label(16, rowIndex,  rs.getString("Tk_dt")));
					sheet.addCell(new Label(17, rowIndex,  rs.getString("Tk_vt")));
					sheet.addCell(new Label(18, rowIndex,  rs.getString("Tk_gv")));
					sheet.addCell(new Label(19, rowIndex,  rs.getString("Tk_ck")));
					sheet.addCell(new Label(20, rowIndex,  rs.getString("Tk_thue_co")));
					
					
					
					
					sheet.addCell(new Label(21, rowIndex,  rs.getString("Ma_kho")));
					sheet.addCell(new Label(22, rowIndex,  rs.getString("Ma_vt")));
					sheet.addCell(new Label(23, rowIndex,"" ));
					sheet.addCell(new Number(24, rowIndex, rs.getDouble("Han_tt")));
					sheet.addCell(new Label(25, rowIndex, rs.getString("Ma_gd")));
					sheet.addCell(new Label(26, rowIndex, rs.getString("Ma_vv_i")));
					sheet.addCell(new Label(27, rowIndex, rs.getString("Ma_phi")));
					sheet.addCell(new Label(28, rowIndex, rs.getString("ma_bpht_i")));
					sheet.addCell(new Label(29, rowIndex, rs.getString("SoLo")));
					//System.out.println("+:::::::::::::::::::::::::::::::+"+  rs.getString("ngayhethan"));
					sheet.addCell(new Label(30, rowIndex, rs.getString("ngayhethan").length()< 2?"":getFormatDate(rs.getString("ngayhethan"))));
				
					
					rowIndex++;
				}
				rs.close();
				workbook.write();		
				workbook.close();
				if(rowIndex==1)
				{					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
				if(obj.getkhoId().length()>0)
				{
					query="update nhaphanphoi set makho='KHO03' from NHAPHANPHOI where pk_Seq=106210 ";
					db.update(query);
				}
				
				
			} catch (Exception e)
			{
				
				e.printStackTrace();
			}
			finally
			{
				if(db!=null)db.shutDown();
				if (outstream != null)
					outstream.close();
			}
		}
	}
	
	
	private void PhieuDieuChuyen(HttpServletResponse response,IStockintransit obj) throws Exception
	{
		OutputStream outstream = null;
		WorkbookSettings wbSettings = new WorkbookSettings();
		wbSettings.setLocale(new Locale("en", "EN"));			
		response.setContentType("application/vnd.ms-excel");
		response.setHeader("Content-Disposition", "attachment; filename=PhieuDieuChuyen.xls");
		outstream = response.getOutputStream();
		WritableWorkbook workbook = jxl.Workbook.createWorkbook(response.getOutputStream());
		
		WritableSheet 	sheet = workbook.createSheet("PhieuDieuChuyen",1);
		WritableFont cellFont = new WritableFont(WritableFont.TIMES, 12);
		cellFont.setColour(Colour.BLACK);
		
		WritableCellFormat cellFormat = new WritableCellFormat(cellFont);
		
		cellFormat.setBackground(jxl.format.Colour.GRAY_50);
		cellFormat.setWrap(true);
		cellFormat.setAlignment(Alignment.CENTRE);
		cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
		cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
		cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
		
		sheet.addCell(new Label(0, 0, "Ma_kho",cellFormat));
		sheet.addCell(new Label(1, 0, "Ma_khon",cellFormat));
		sheet.addCell(new Label(2, 0, "Ong_ba",cellFormat));
		sheet.addCell(new Label(3, 0, "Dien_giai",cellFormat));
		sheet.addCell(new Label(4, 0, "So_ct",cellFormat));
		sheet.addCell(new Label(5, 0, "Ngay_ct",cellFormat));
		sheet.addCell(new Label(6, 0, "Ma_NT",cellFormat));
		sheet.addCell(new Label(7, 0, "Ty_gia",cellFormat));
		sheet.addCell(new Label(8, 0, "Ma_vt",cellFormat));
		sheet.addCell(new Label(9, 0, "So_luong",cellFormat));
		sheet.addCell(new Label(10, 0, "Ma_thue",cellFormat));
		sheet.addCell(new Label(11, 0, "Gia_nt",cellFormat));
		sheet.addCell(new Label(12, 0, "Tien_nt",cellFormat));
		sheet.addCell(new Label(13, 0, "Ma_nx_i",cellFormat));
		sheet.addCell(new Label(14, 0, "Tk_vt",cellFormat));
		sheet.addCell(new Label(15, 0, "Ma_gd",cellFormat));
		
		int rowIndex = 1;
		dbutils db = new dbutils();
		
		String query = 
				"   select b.MaKho as MaKho,b.MaFAST as MaKhachHang,b.TEN as TenKhachHang,a.GHICHU as DienGiai,a.PK_SEQ as SoChungTu,  "+   
						"   	a.NgayChuyen as NgayChungTu,'VND' as MaNT,1 AS TyGia ,e.MA as spMa,d.soluongchuyen as SoLuong,e.tkvt ,b.MaNX "+   
						"   from ERP_CHUYENKHO a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK  "+   
						"   inner join ERP_KHOTT c on c.PK_SEQ=a.KhoXuat_FK  "+   
						"   inner join ERP_CHUYENKHO_SANPHAM d on d.chuyenkho_fk=a.PK_SEQ  "+   
						"   inner join SANPHAM e on e.PK_SEQ=d.sanpham_fk  "+   
						"   where a.TRANGTHAI=1  ";   
		if(obj.gettungay().length()>0)
		{
			query+=" and a.NgayChuyen>='"+obj.gettungay()+"' ";
		}
		
		if(obj.getdenngay().length()>0)
		{
			query+=" and a.NgayChuyen<='"+obj.getdenngay()+"' ";
		}
		if(obj.getnppId().length()>0)
		{
			query+=" and a.NPP_FK ='"+obj.getnppId()+"' ";
		}
		
		query+=
				"   union all  "+   
						"     "+   
						"   select b.MaKho as MaKho,b.MaFAST as MaKhachHang,b.TEN as TenKhachHang,a.GHICHU as DienGiai,a.PK_SEQ as SoChungTu,  "+   
						"   	a.NgayChuyen as NgayChungTu,'VND' as MaNT,1 AS TyGia ,e.MA as Ma_vt,d.soluongchuyen as SoLuong,e.tkvt,b.MaNX  "+   
						"   from ERP_CHUYENKHONPP a inner join NHAPHANPHOI b on b.PK_SEQ=a.NPP_FK  "+   
						"   inner join KHO c on c.PK_SEQ=a.KhoXuat_FK  "+   
						"   inner join ERP_CHUYENKHONPP_SANPHAM d on d.chuyenkho_fk=a.PK_SEQ  "+   
						"   inner join SANPHAM e on e.PK_SEQ=d.sanpham_fk  "+   
						"   where a.TRANGTHAI in (1,3)  ";
		if(obj.gettungay().length()>0)
		{
			query+=" and a.NgayChuyen>='"+obj.gettungay()+"' ";
		}
		
		if(obj.getdenngay().length()>0)
		{
			query+=" and a.NgayChuyen<='"+obj.getdenngay()+"' ";
		}
		if(obj.getnppId().length()>0)
		{
			query+=" and a.NPP_FK ='"+obj.getnppId()+"' ";
		}
		
		System.out.println("[Query]"+query);
		
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				while (rs.next())
				{
					sheet.addCell(new Label(0, rowIndex, rs.getString("MaKho")));
					sheet.addCell(new Label(1, rowIndex, rs.getString("MaKho")));
					sheet.addCell(new Label(2, rowIndex, rs.getString("TenKhachHang")));
					sheet.addCell(new Label(3, rowIndex,""));
					sheet.addCell(new Label(4, rowIndex, rs.getString("SoChungTu")    ));
					sheet.addCell(new Label(5, rowIndex, getFormatDate(rs.getString("NgayChungTu"))));
					sheet.addCell(new Label(6, rowIndex,"VND"   ));
					sheet.addCell(new Label(7, rowIndex, "1" ) );
					sheet.addCell(new Label(8, rowIndex, rs.getString("spMa")    ));
					sheet.addCell(new Number(9, rowIndex, rs.getDouble("soLuong")));
					sheet.addCell(new Label(10, rowIndex,""));
					sheet.addCell(new Label(11, rowIndex,""));
					sheet.addCell(new Label(12, rowIndex,""));
					sheet.addCell(new Label(13, rowIndex,rs.getString("MaNX")));
					sheet.addCell(new Label(14, rowIndex, rs.getString("tkvt")    ));
					sheet.addCell(new Label(15, rowIndex, rs.getString("tkvt")    ));
					
					rowIndex++;									
				}
				rs.close();
				workbook.write();		
				workbook.close();
				if(rowIndex==1)
				{					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}
				
			} catch (Exception e)
			{
				
				e.printStackTrace();
			}
			finally
			{
				if(db!=null)db.shutDown();
				if (outstream != null)
					outstream.close();
			}
			
		}
	}
	
	
	public String getFormatDate(String date) 
	{
		String[] arr = date.split("-");
		
		return arr[2] + "/" + arr[1] + "/" + arr[0];
	}
	
	
	public static void main(String[] arg) throws Exception
	{
		/*BcDuLieuImport bc= new BcDuLieuImport();
		IStockintransit  obj = new Stockintransit();
		bc.Export_Excel_HoaDon( obj);*/
		
		
		
	}
	
}
