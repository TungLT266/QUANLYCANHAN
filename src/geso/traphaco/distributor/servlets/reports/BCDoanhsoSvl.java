package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
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

public class BCDoanhsoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public BCDoanhsoSvl() 
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

		obj.setuserTen((String) session.getAttribute("userTen"));
		String userId = util.getUserId(querystring);
		String nppId= util.getIdNhapp(userId);

		obj.setuserId(userId);

		String view = request.getParameter("view");
		if(view == null)
			view = "";

		System.out.println("view :"+view);

		if(nppId != null)
			view = "NPP";
		if(nppId == null)
			view = "TT";
		System.out.println("view 2:"+view + "NPP ID: "+nppId);

		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init_doanhso();
		obj.setDongiagoc("1");
		obj.setChuyenSale("1");

		String loaisalesin = request.getParameter("phanloai");
		if(loaisalesin == null)
			loaisalesin = "0";
		obj.setLoaiSalesIn(loaisalesin);
		
		session.setAttribute("obj", obj);
		if(!view.equals("TT"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Distributor/BCDoanhSo.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/BCDoanhSo.jsp";
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
		OutputStream out = response.getOutputStream();

		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();

		String tungay =util.antiSQLInspection(request.getParameter("Sdays"));
		obj.settungay(tungay);

		String denngay =util.antiSQLInspection(request.getParameter("Edays"));
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
		
		String loaisalesin = request.getParameter("loaisalesin");
		if(loaisalesin == null)
			loaisalesin = "0";
		obj.setLoaiSalesIn(loaisalesin);
		System.out.println("::: 1.PHAN LOAI: " + loaisalesin);
		
		
		String laygiavon=request.getParameter("laygiavon");
		if(laygiavon==null){
			laygiavon="";
		}
		obj.setLaygiavon(laygiavon);
		

		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?util.antiSQLInspection(request.getParameter("kenhId")):"");
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?util.antiSQLInspection(request.getParameter("vungId")):"");
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?util.antiSQLInspection(request.getParameter("khuvucId")):"");		
		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhs"))!=null?util.antiSQLInspection(request.getParameter("gsbhs")):"");
		obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId"))!=null?util.antiSQLInspection(request.getParameter("ddkdId")):"");
		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?util.antiSQLInspection(request.getParameter("nppId")):"");
		obj.setTinhthanhid(util.antiSQLInspection(request.getParameter("tinhthanhId"))!=null?util.antiSQLInspection(request.getParameter("tinhthanhId")):"");
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null?util.antiSQLInspection(request.getParameter("nhanhangId")):"");
		obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?util.antiSQLInspection(request.getParameter("chungloaiId")):"");

		obj.setkhId(util.antiSQLInspection(request.getParameter("khId"))!=null?util.antiSQLInspection(request.getParameter("khId")):"");
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");
		
		obj.setsanphamId(util.antiSQLInspection(request.getParameter("spId"))!=null?util.antiSQLInspection(request.getParameter("spId")):"");
		obj.setkhoId(util.antiSQLInspection(request.getParameter("khoid"))!=null?util.antiSQLInspection(request.getParameter("khoid")):"");
		obj.setDiabanid(util.antiSQLInspection(request.getParameter("diabanid"))!=null?util.antiSQLInspection(request.getParameter("diabanid")):"");
		
		String nppId= util.getIdNhapp(userId);
		String nppID = util.antiSQLInspection(request.getParameter("nppId"));

		if(nppID==null)
			nppID = "";

		String view=request.getParameter("view");
		if(view == null)
			view = "";

		if(nppId!=null)
		{
			view = "NPP";
			obj.setnppId(nppId);
		}

		if(nppId==null)
		{
			view ="TT";
			obj.setnppId(nppID);   
		}
		
		obj.init_doanhso();

		session.setAttribute("obj", obj);	
		String level = request.getParameter("level")==null?"":request.getParameter("level");
		obj.settungay(tungay);
		obj.setdenngay(denngay);

		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Center/BCDoanhSo.jsp";
		if(!view.equals("TT"))
		{
			nextJSP = "/TraphacoHYERP/pages/Distributor/BCDoanhSo.jsp";
		}
		else
		{
			nextJSP = "/TraphacoHYERP/pages/Center/BCDoanhSo.jsp";
		}

		System.out.println("Action la: " + action);
		try
		{
			if (action.equals("Taomoi")) 
			{			
				obj.setLoaiSalesIn(loaisalesin);
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoMuaHang.xlsm");
				
				String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
				String query = setQuery("", obj,level,"", view, npp_duocchon_id, session);
				
				System.out.println("LAY DATA: " + query + "\n");
				
				if(!CreatePivotTable(out,obj,query,level, view))
				{
					response.setContentType("text/html");
					PrintWriter writer = new PrintWriter(out);
					writer.print("Xin loi khong co bao cao trong thoi gian nay");
					writer.close();
				}
			}
			else if(action.equals("search"))
			{
				obj.setuserId(userId);
				obj.setLoaiSalesIn(loaisalesin);
				
				String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
				
				String query = setQuery("", obj, level,"search", view, npp_duocchon_id, session);
				System.out.println("LAY DATA: " + query + "\n");
				obj.init_doanhso();
				obj.searquery(query);
				
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				response.sendRedirect(nextJSP);
			}			

		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
	}	

	private String setQuery(String sql, IStockintransit obj, String level, String search, String view, String npp_duocchon_id, HttpSession session) 
	{
		Utility util = new Utility();
		String query = "";

		String condition = "";
		String condition1 = "";
		String condition2 = "";
		String conditionCK = "";
		String conditionHANGMUON = "";
			
		String conditionCHUYENSALE = "";
		if(obj.gettungay().trim().length() > 0)
		{
			//conditionCHUYENSALE += " and substring( a.NgayDonHang, 0, 8 ) = ( select substring( convert( varchar(10), DATEADD(m, -1, '" + obj.gettungay() + "'), 120 ), 0, 8) ) ";
			conditionCHUYENSALE += " and substring( a.NGAYXUATHD, 0, 8 ) = ( select substring( convert( varchar(10), DATEADD(m, -1, '" + obj.gettungay() + "'), 120 ), 0, 8) ) ";
		}

		if(obj.gettungay().trim().length() > 0)
		{
			if( obj.getLoaiSalesIn().equals("0") )
			{
				condition += " and a.NGAYXUATHD >= '" + obj.gettungay() + "' ";
				condition2 += " and a.NGAYNHAP >= '" + obj.gettungay() + "' ";
				conditionCK += " and a.NGAYXUATHD >= '" + obj.gettungay() + "' ";
				conditionHANGMUON += " and a.NgayDonHang >= '" + obj.gettungay() + "' ";
			}
			else
			{
				condition += " and a.NGAYXUATHD >= '" + obj.gettungay() + "' ";
				condition2 += " and a.NGAYXUATHD >= '" + obj.gettungay() + "' ";
				conditionCK += " and a.NGAYXUATHD >= '" + obj.gettungay() + "' ";
				conditionHANGMUON += " and a.NgayDonHang >= '" + obj.gettungay() + "' ";
			}
		}
		
		if(obj.getdenngay().trim().length() > 0)
		{
			if( obj.getLoaiSalesIn().equals("0") )
			{
				condition += " and a.NGAYXUATHD <= '" + obj.getdenngay() + "' ";
				condition2 += " and a.NGAYNHAP <= '" + obj.getdenngay() + "' ";
				conditionCK += " and a.NGAYXUATHD <= '" + obj.getdenngay() + "' ";
				conditionHANGMUON += " and a.NgayDonHang <= '" + obj.getdenngay() + "' ";
			}
			else
			{
				condition += " and a.NGAYXUATHD <= '" + obj.getdenngay() + "' ";
				condition2 += " and a.NGAYXUATHD <= '" + obj.getdenngay() + "' ";
				conditionCK += " and a.NGAYXUATHD <= '" + obj.getdenngay() + "' ";
				conditionHANGMUON += " and a.NgayDonHang <= '" + obj.getdenngay() + "' ";
			}
		}
		
		System.out.println("::: KHACH HANG ID: " + obj.getkhId());
		if(obj.getkhId().trim().length() > 0)
		{
			condition += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
			condition2 += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
			conditionCHUYENSALE  += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
			conditionCK += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
		}
		
		if(obj.getDdkd().trim().length() > 0)
		{
			condition += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
			condition1 += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
			conditionCHUYENSALE += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
			//conditionCK += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
		}
		
		//PHAN QUYEN TDV
		condition += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		condition1 += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		//conditionCK += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionHANGMUON += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
	
		//PHAN QUYEN CS
		condition += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		condition1 += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		condition2 += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionHANGMUON += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );

		//PHAN QUYEN KBH
		condition += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		condition1 += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		condition2 += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		conditionCHUYENSALE += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		conditionHANGMUON += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());

		
		//PHAN QUYEN THEO NPP DANG NHAP VAO
		System.out.println("::: NPP LAY DUOC: " + obj.getnppId() + " VIEW: " + view);
		if( obj.getnppId().trim().length() <= 0 && npp_duocchon_id.trim().length() <= 0 )
		{
			if( !view.equals("TT") )
			{
				String congtyId = session.getAttribute("congtyId").toString();
				condition += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				condition1 += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				conditionCHUYENSALE += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				conditionCK += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
			}
		}
		else
		{
			obj.setnppId(npp_duocchon_id);
			condition += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			condition1 += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			conditionCHUYENSALE += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			conditionCK += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
		}
		
		if(obj.getkhoId().trim().length() > 0)
		{
			condition += " and a.kho_fk = '" + obj.getkhoId() + "' ";
			condition1 += " and a.kho_fk = '" + obj.getkhoId() + "' ";
			conditionCHUYENSALE += " and a.kho_fk = '" + obj.getkhoId() + "' ";
			conditionCK += " and a.kho_fk = '" + obj.getkhoId() + "' ";
		}
		
		if(obj.getDiabanid().trim().length() > 0)
		{
			condition += " and dba.pk_seq = '" + obj.getDiabanid() + "' ";
			condition1 += " and dba.pk_seq = '" + obj.getDiabanid() + "' ";
			conditionCHUYENSALE += " and a.kho_fk = '" + obj.getkhoId() + "' ";
			conditionCK += " and dba.pk_seq = '" + obj.getDiabanid() + "' ";
		}
		
		if( obj.getkhuvucId().trim().length() > 0 )
		{
			condition += " and dba.khuvuc_fk = '" + obj.getkhuvucId() + "' ";
			condition1 += " and dba.khuvuc_fk = '" + obj.getkhuvucId() + "' ";
			conditionCHUYENSALE += " and a.kho_fk = '" + obj.getkhoId() + "' ";
			conditionCK += " and dba.khuvuc_fk = '" + obj.getkhuvucId() + "' ";
		}
		
		if( obj.getTinhthanhid().trim().length() > 0 )
		{
			condition += " and tt.pk_seq = '" + obj.getTinhthanhid() + "' ";
			condition1 += " and tt.pk_seq = '" + obj.getTinhthanhid() + "' ";
			conditionCHUYENSALE += " and a.kho_fk = '" + obj.getkhoId() + "' ";
			conditionCK += " and tt.pk_seq = '" + obj.getTinhthanhid() + "' ";
		}
		
		if( obj.getsanphamId().trim().length() > 0 )
		{
			condition += " and c.pk_seq = '" + obj.getsanphamId() + "' ";
			condition1 += " and c.pk_seq = '" + obj.getsanphamId() + "' ";
			conditionCHUYENSALE += " and a.kho_fk = '" + obj.getkhoId() + "' ";
			//conditionCK += " and c.pk_seq = '" + obj.getsanphamId() + "' ";
		}
		
		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		condition += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "dh_sp", "ddkd_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "dh_sp", "ddkd_fk", obj.getLoainhanvien(), obj.getDoituongId() );
		
		String sql1 = "";
		if(obj.getDongiagoc().equals("1"))
		{
			sql1 += " round( data.dongiagoc * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)";
		}
		else
		{
			sql1 += " round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)";
		}
		
		String sqlsolo = "b.solo";
		if(search.length()>0)
		{
			sqlsolo = " '' as solo";
		}
		
		//LẤY TỪ SỔ XUẤT HÀNG ĐÃ CHỐT
		condition += " and a.pk_seq in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where TRANGTHAI != 0 ) ) ";
		conditionCHUYENSALE += " and a.pk_seq in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where TRANGTHAI != 0 ) ) ";
		conditionCK += " and a.pk_seq in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where TRANGTHAI != 0 ) ) ";
		
		System.out.println(":: 22.PHAN LOAI: " + obj.getphanloai() );
		if( obj.getLoaiSalesIn().equals("0") ) //BÁO CÁO SALEIN PHÒNG BÁN HÀNG
		{
			 query = "\n select ROW_NUMBER() OVER (PARTITION BY data.ddkdten order by data.ddkdten desc)as stt,DATA.*  ,"+
 
					 "\n SUM(data.SOLUONG * " + sql1 + ") OVER (PARTITION BY data.ddkdten) AS TONGDOANHSO,"+sql1+" as dongiadoanhso,(data.SOLUONG * "+sql1+") as thanhtiensauvat "+
					 "\n from (  select SPID,data1.tendiaban,donvi,hoadonId,SOHOADON,ngayxuatHD, ngaydonhang, tenkho,loaidonhang,makhachhangOLD,makhachhangNEW,tenkhachhang,ddkdTen,tinhthanhTen,quanhuyenTEN,khuvucTen,vungTen,masanphamOLD,masanphamNEW,tensanpham,solo,SUM(soluong) as soluong,SUM(SoLuong_Chuan) soluong_chuan,dongia,scheme,dongiaGOC,thueVAT,kbhTEN,daily,gsbhTEN,asmTEN from (   ";
			 
			 query += "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
					 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
					 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
					 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
					 "			C.PK_SEQ AS SPID, c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
					 "			 dh_sp.DONGIA, b.scheme,    "+
					 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
					 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
					 "		left join SANPHAM c on b.MA = c.MA  "+
					 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
					 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
					 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
					 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
					 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
					 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
					 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
					 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
					 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
					 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
					 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
					 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
					 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
					 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '1' and dbo.trim( dh_sp.scheme ) = ''  and a.chuyenSALES = '0'   " + condition;
			 
			 
			 if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM
			 {
				 /* query += "\n UNION ALL " + 
						 "\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
						 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
						 "\n		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
						 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
						 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
						 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
						 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
						 "\n		 c.pk_seq as SPID ,c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
						 "\n		 dh_sp.DONGIA, b.scheme,   "+
						 "\n		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
						 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
						 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk  and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '') "+
						 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
						 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
						 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
						 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
						 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
						 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
						 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
						 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
						 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
						 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
						 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
						 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
						 "\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 1 ) and dbo.trim( b.scheme ) != ''  " + condition ;*/
						 
				 query += "\n UNION ALL " + 
						 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
						 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
						 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
						 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
						 "			C.PK_SEQ AS SPID, c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
						 "			 dh_sp.DONGIA, b.scheme,    "+
						 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
						 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
						 "		left join SANPHAM c on b.MA = c.MA  "+
						 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
						 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
						 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
						 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
						 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
						 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
						 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
						 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
						 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
						 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
						 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
						 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
						 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
						 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '1' and dbo.trim( dh_sp.scheme ) != '' and a.chuyenSALES = '0'   " + condition;	 
			 }
			 
			 
			sql = query;
			
			//ĐƠN HÀNG CTV --> TAM THOI BO RA
			/*sql += "\n union ALL  "+
					 " \n select '' as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD, N'Hàng bán' as tenkho,    "+
					 "\n		 N'Cộng tác viên' as loaidonhang,   "+
					 " \n		 e.maFAST as makhachhangOLD,    "+
					 " \n		 e.ma as makhachhangNEW,    "+
					 "	\n	 e.TEN as tenkhachhang,    "+
					 "\n		 isnull(ctv.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, '' as khuvucTen, '' as vungTen,   "+
					 "	\n	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, '' as SOLO, b.SOLUONG, b.SOLUONG,    "+
					 "	\n	 b.DONGIA, '' as scheme, 0 as dongiaGOC, 0 thueVAT, 'ETC' as kbhTEN, '' as daily   "+
					 " \n from DONHANGCTV a inner join DONHANGCTV_SP b on a.PK_SEQ = b.DONHANGCTV_FK   "+
					 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ   "+
					 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					 "\n	left join CONGTACVIEN ctv on ctv.PK_SEQ = b.CTV_FK     "+
					 "\n	left join TINHTHANH tt on tt.PK_SEQ = e.TINHTHANH_FK   "+
					 "\n	left join QUANHUYEN qh on qh.PK_SEQ = e.QUANHUYEN_FK      "+
					 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					 " \n where a.TRANGTHAI in ( 1 )  " + condition2 ;*/
			
			//THEM LOAI BAN CHO NPP nhóm không thầu, khong tinh loai NPP MTV
			/*sql += "\n UNION ALL " + 
					 "\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
					 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
					 "\n		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
					 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
					 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
					 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
					 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					 "\n		 c.pk_seq as SPID ,c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
					 "\n		 dh_sp.DONGIA, b.scheme,   "+
					 "\n		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
					 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
					 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk  and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '') "+
					 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
					 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
					 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
					 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					 "\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 0 ) and a.kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and a.NPP_DAT_FK not in ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' ) and dbo.trim( b.scheme ) = ''  " + condition ;*/
			
			sql += "\n UNION ALL " + 
					 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
					 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
					 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
					 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
					 "			C.PK_SEQ AS SPID, c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
					 "			 dh_sp.DONGIA, b.scheme,    "+
					 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
					 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
					 "		left join SANPHAM c on b.MA = c.MA  "+
					 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
					 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
					 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
					 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
					 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
					 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
					 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
					 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
					 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
					 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
					 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
					 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
					 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
					 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '0'  and a.kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and a.NPP_DAT_FK not in ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' ) and dbo.trim( dh_sp.scheme ) = ''  and a.chuyenSALES = '0'  " + condition;
			
			 if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM
			 {
				 /*sql += "\n UNION ALL " + 
						 "\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
						 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
						 "\n		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
						 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
						 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
						 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
						 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
						 "\n		c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
						 "\n		 dh_sp.DONGIA, b.scheme,   "+
						 "\n		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
						 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
						 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk  and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '') "+
						 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
						 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
						 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
						 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
						 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
						 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
						 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
						 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
						 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
						 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
						 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
						 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
						 "\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 0 ) and a.kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and a.NPP_DAT_FK not in ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' ) and dbo.trim( b.scheme ) != ''  " + condition ;*/
				 
				 sql += "\n UNION ALL " + 
						 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
						 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
						 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
						 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
						 "			C.PK_SEQ AS SPID, c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
						 "			 dh_sp.DONGIA, b.scheme,    "+
						 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
						 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
						 "		left join SANPHAM c on b.MA = c.MA  "+
						 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
						 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
						 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
						 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
						 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
						 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
						 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
						 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
						 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
						 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
						 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
						 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
						 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
						 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '0'  and a.kbh_fk in ( select kbh_fk from NHOMKENH_KENHBANHANG where nk_fk = '100001' ) and a.NPP_DAT_FK not in ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' ) and dbo.trim( dh_sp.scheme ) != ''  and a.chuyenSALES = '0'  " + condition;
				 
			 }
	
			//ĐƠN HÀNG NPP TRỰC THUỘC HÀNG BÁN
			sql +=  "\n UNION ALL " + 
					 "\n select dba.ten as tendiaban,dv.diengiai,a.PK_SEQ as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD, a.ngaynhap as ngaydonhang, d.TEN as tenkho,   "+
					 "\n		 N'Đơn hàng đại lý' as loaidonhang,   "+
					 "\n		 e.maFAST as makhachhangOLD,    "+
					 "\n		 e.ma as makhachhangNEW,    "+
					 "\n		 e.TEN as tenkhachhang,    "+
					 "	\n	 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					 "	\n	 c.pk_seq as SPID ,c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham,"+sqlsolo+", b.SOLUONG, b.soluong as SoLuong_Chuan,    "+
					 "	\n	 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
					 "	\n	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as congty, npp.TEN as daily,   "+
					 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "\n  from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
					 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					 "\n	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
					 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					 "\n	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
					 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
					 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					 "\n where a.CS_DUYET = '1'  and isnull(b.scheme, '') = ''  and a.chuyenSALES = '0' " + condition2 + condition1;
					 //"\n where a.CS_DUYET = '1'  and isnull(b.scheme, '') = '' and kenh.PK_SEQ in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) and a.chuyenSALES = '0' " + condition2 + condition1;
			
			sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );	
			
			if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM
			{
				sql +=  "\n UNION ALL " + 
						 "\n select dba.ten as tendiaban,dv.diengiai as donvi, a.PK_SEQ as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD, a.NGAYNHAP as ngaydonhang, d.TEN as tenkho,   "+
						 "\n		 N'Đơn hàng đại lý' as loaidonhang,   "+
						 "\n		 e.maFAST as makhachhangOLD,    "+
						 "\n		 e.ma as makhachhangNEW,    "+
						 "\n		 e.TEN as tenkhachhang,    "+
						 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
 
						 "\n		c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
 
						 "\n		 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
						 "\n		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as congty, npp.TEN as daily,   "+
						 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "\n  from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
						 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						 "\n	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
						 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
						 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
						 "\n	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
						 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
						 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
						 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
						 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
						 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
						 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
						 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
						 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
						 "\n  where a.CS_DUYET = '1'  and isnull(b.scheme, '') != ''  and a.chuyenSALES = '0' " + condition2 + condition1;
						 //"\n  where a.CS_DUYET = '1'  and isnull(b.scheme, '') != '' and kenh.PK_SEQ in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100000' ) and a.chuyenSALES = '0' " + condition2 + condition1;
			
				sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );	
			}
			
			System.out.println("::: CONDITION::: " + condition2 + condition1);
			
			if( obj.getChuyenSale().equals("1") )
			{
				/*sql += "\n UNION ALL select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
								 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
								 "\n		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
								 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
								 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
								 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
								 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
								 "\n		c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
								 "	\n	 dh_sp.DONGIA, b.scheme,   "+
								 "\n		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
								 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
								 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
								 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
								 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '')  "+
								 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
								 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
								 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
								 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
								 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
								 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
								 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
								 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
								 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
								 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
								 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
								 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
								 "\n where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 1 ) and dbo.trim( b.scheme ) = '' AND a.chuyenSALES = '1'  " + conditionCHUYENSALE;*/
			
				sql += "\n UNION ALL " + 
						 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
						 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
						 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
						 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
						 "			C.PK_SEQ AS SPID, c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
						 "			 dh_sp.DONGIA, b.scheme,    "+
						 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
						 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
						 "		left join SANPHAM c on b.MA = c.MA  "+
						 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
						 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
						 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
						 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
						 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
						 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
						 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
						 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
						 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
						 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
						 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
						 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
						 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
						 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '1' and  dbo.trim( dh_sp.scheme ) = ''  and a.chuyenSALES = '1'  " + conditionCHUYENSALE;
				
				if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM
				{
					/*sql += "\n UNION ALL " + 
							 "\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
							 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
							 "\n		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
							 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
							 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
							 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
							 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
							 "\n		c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
							 "\n		 dh_sp.DONGIA, b.scheme,   "+
							 "	\n	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
							 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
							 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
							 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
							 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk  and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '') "+
							 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
							 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
							 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
							 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
							 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
							 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
							 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
							 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
							 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
							 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
							 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
							 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
							 "\n where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 1 ) and dbo.trim( b.scheme ) != '' AND a.chuyenSALES = '1'  " + conditionCHUYENSALE;*/
					
					sql += "\n UNION ALL " + 
							 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
							 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
							 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
							 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
							 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
							 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
							 "			C.PK_SEQ AS SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
							 "			 dh_sp.DONGIA, b.scheme,    "+
							 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
							 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
							 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
							 "		left join SANPHAM c on b.MA = c.MA  "+
							 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
							 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
							 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
							 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
							 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
							 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
							 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
							 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
							 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
							 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
							 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
							 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
							 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
							 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '1' and  dbo.trim( dh_sp.scheme ) != ''  and a.chuyenSALES = '1'  " + conditionCHUYENSALE;
				}		
			}
			
			//TINH THEM DOANH SO XUNG QUANH BENH VIEN
			/*query = "\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
					 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
					 "\n		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
					 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
					 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
					 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
					 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					 "\n		 c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
					 "\n	 dh_sp.DONGIA, b.scheme,   "+
					 "\n	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
					 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
					 "\n	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '')  "+
					 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
					 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
					 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
					 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					 "\n where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 2 ) and dbo.trim( b.scheme ) = ''  " + condition ;
			 
					//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
					query += " and c.pk_seq in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";*/
			 
			query = "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
					 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
					 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
					 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
					 "			C.PK_SEQ AS SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
					 "			 dh_sp.DONGIA, b.scheme,    "+
					 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
					 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
					 "		left join SANPHAM c on b.MA = c.MA  "+
					 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
					 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
					 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
					 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
					 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
					 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
					 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
					 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
					 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
					 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
					 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
					 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
					 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
					 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and  dbo.trim( dh_sp.scheme ) = ''  and a.chuyenSALES = '0'  " + condition;
			
			//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
			query += " and c.pk_seq in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
			
			 if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM
			 {
				 /*query += "\n UNION ALL " + 
						 "\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
						 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
						 "	\n	 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
						 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
						 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
						 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
						 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
						 "\n		 c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
						 "\n		 dh_sp.DONGIA, b.scheme,   "+
						 "\n		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
						 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
						 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk  and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '') "+
						 "\n left join KHO d on a.Kho_FK = d.PK_SEQ   "+
						 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
						 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
						 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
						 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
						 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
						 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
						 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
						 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
						 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
						 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
						 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
						 "\n where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 2 ) and dbo.trim( b.scheme ) != ''  " + condition ;
	
						//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
						query += " and c.pk_seq in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";*/
						
					query += "\n UNION ALL " + 
							"	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
							 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
							 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
							 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
							 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
							 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
							 "			  C.PK_SEQ AS SPID ,c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
							 "			 dh_sp.DONGIA, b.scheme,    "+
							 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' congty, '' as daily,    "+
							 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
							 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
							 "		left join SANPHAM c on b.MA = c.MA  "+
							 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
							 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
							 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
							 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
							 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
							 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
							 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
							 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
							 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
							 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
							 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
							 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
							 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
							 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and  dbo.trim( dh_sp.scheme ) = ''  and a.chuyenSALES = '0'  " + condition;
		
						//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
						query += " and c.pk_seq in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
			 }
			
			//ĐƠN HÀNG MƯỢN
			 sql += "\n UNION ALL " + 
					 "\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
					 "\n											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, a.NgayDonHang, d.TEN as tenkho,    "+
					 "\n		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
					 "\n		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
					 "\n		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
					 "\n		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
					 "\n		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					 "\n		 c.pk_seq as SPID ,c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
					 "\n		 dh_sp.DONGIA, b.scheme,   "+
					 "\n		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as congty, isnull( npp.ten, '' ) as daily,   "+
					 "\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "\n from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
					 "\n	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					 "\n	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk  and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '') "+
					 "\n	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					 "\n	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					 "\n	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
					 "\n	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
					 "\n	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
					 "\n	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					 "\n	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					 "\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					 "\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					 "\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					 "\n	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					 "\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					 "\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 1 ) and a.donhangmuon = '1'  " + conditionHANGMUON ;
			 

			//System.out.println(":::: XUNG QUANH BENH VIEN: " + query );
			sql += " UNION ALL " + query;
			
			sql += " ) as data1  group by data1.tendiaban,donvi,hoadonId,SOHOADON,ngayxuatHD, ngaydonhang,tenkho,loaidonhang,makhachhangOLD,makhachhangNEW,tenkhachhang,ddkdTen,tinhthanhTen,quanhuyenTEN,khuvucTen,vungTen,SPID ,masanphamOLD,masanphamNEW,tensanpham,solo,dongia,scheme,dongiaGOC,thueVAT,kbhTEN,daily,gsbhTEN,asmTEN  " +
					" ) as data where  data.soluong<>0 ";
			
			
			//LOẠI SALES IN
			//Sales in OTC: DLPP không thầu bán khách lẻ
			//Sales in ETC: MTV bán Bệnh viện + MTV bán DLPP thầu + DLPP không thầu bán bệnh viện
			System.out.println(":::: TYPE: " + obj.gettype());
			if( obj.gettype().trim().length() > 0 )
			{
				if( obj.gettype().equals("1") ) //Sales in OTC
				{
					sql += " and data.loaidonhang in ( N'Đơn hàng đại lý' ) and data.kbhTEN in ( N'TPC', N'SPC', N'SI', N'Khác' ) ";
				}
				else if( obj.gettype().equals("2") ) //Sales in ETC
				{
					sql += " and (    data.loaidonhang in ( N'ETC' ) " + 
									"   or ( data.loaidonhang in ( N'Bán hàng NPP' ) and data.kbhTEN in ( N'CLC', N'INS', N'DLPP' ) ) " + 
									"   or ( data.loaidonhang in ( N'Đơn hàng đại lý' ) and data.kbhTEN in ( N'CLC', N'INS', N'DLPP' ) ) ) ";;
				}
			}

		}
		else if( obj.getLoaiSalesIn().equals("1") ) //SALES IN BAN TONG GIAM DOC
		{
			sql = "select ROW_NUMBER() OVER (PARTITION BY data.ddkdten order by data.ddkdten desc)as stt, DATA.*,   "+
					 "		SUM(data.SOLUONG *  round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)) OVER (PARTITION BY data.ddkdten) AS TONGDOANHSO,  round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0) as dongiadoanhso,(data.SOLUONG *  round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)) as thanhtiensauvat  "+
					 " from  "+
					 " ( "+
					 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
					 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
					 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
					 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
					 "			c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
					 "			 dh_sp.DONGIA, b.scheme,    "+
					 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
					 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
					 "		left join SANPHAM c on b.MA = c.MA  "+
					 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
					 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
					 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
					 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
					 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
					 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
					 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
					 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
					 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
					 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
					 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
					 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
					 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
					 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.chuyenSALES = '0'   " + condition + 
					 "		and a.PK_SEQ not in ( select PK_SEQ from ERP_HOADONNPP where NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' )	 ) ";
					
			if( obj.getChuyenSale().equals("1") )
			{
				sql += 	 "	UNION ALL	" +
						 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
						 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
						 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
						 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
						 "			c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
						 "			 dh_sp.DONGIA, b.scheme,    "+
						 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
						 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
						 "		left join SANPHAM c on b.MA = c.MA  "+
						 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
						 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
						 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
						 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
						 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
						 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
						 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
						 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
						 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
						 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
						 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
						 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
						 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
						 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 )  and a.chuyenSALES = '1'  " + conditionCHUYENSALE + 
						 "		and a.PK_SEQ not in ( select PK_SEQ from ERP_HOADONNPP where NPP_DAT_FK in ( select PK_SEQ from NHAPHANPHOI where loaiNPP = '0' )	 ) ";
			}
			
			sql +=	 " ) "+
					 " DATA ";
			
		}
		else if( obj.getLoaiSalesIn().equals("2") ) //SALES IN KE TOAN
		{
			sql = "select ROW_NUMBER() OVER (PARTITION BY data.ddkdten order by data.ddkdten desc)as stt, DATA.*, GIAVON.DONGIA AS DONGIAVON,   "+
					 "		SUM(data.SOLUONG *  round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)) OVER (PARTITION BY data.ddkdten) AS TONGDOANHSO,  round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0) as dongiadoanhso, (data.SOLUONG *  round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)) as thanhtiensauvat  "+
					 " from  "+
					 " ( "+
					 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,     "+
					 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
					 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
					 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
					 "			c.pk_seq as SPID , c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
					 "			 dh_sp.DONGIA, b.scheme,    "+
					 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,    "+
					 " 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					 "	 from ERP_HOADONNPP a inner join ERP_HOADONNPP_SP_CHITIET b on a.PK_SEQ = b.hoadon_fk    "+
					 "		left join SANPHAM c on b.MA = c.MA  "+
					 "		inner join ERP_HOADONNPP_SP dh_sp on c.PK_SEQ = dh_sp.sanpham_fk and b.hoadon_fk = dh_sp.hoadon_fk    "+
					 "		left join KHO d on a.Kho_FK = d.PK_SEQ    "+
					 "		left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    "+
					 "		left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ    "+
					 "		left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ    "+
					 "		left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK      "+
					 "		left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      "+
					 "		left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       "+
					 "		left join DIABAN dba on e.diaban = dba.pk_seq 		    "+
					 "		left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     "+
					 "		left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    "+
					 "		left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    "+
					 "	 left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 "+
					 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 )    " + condition;
			
			//Lấy KM chiết khấu không chia vào đơn giá
			sql +=  "	union ALL	" +
					"  select dba.ten as tendiaban, '' as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.ngaydonhang, d.TEN as tenkho,      " + 
					"  		 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,     " + 
					"  		 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,      " + 
					"  		 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,      " + 
					"  		 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,      " + 
					"  		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,     " + 
					"  		 0 as SPID , '' as masanphamOLD, '' as masanphamNEW, '' as tensanpham, '' SOLO, 1 as SOLUONG, 1 as SoLuong_Chuan,      " + 
					"  		 -1 * b.GIATRI as DONGIA, b.scheme,     " + 
					"  		 -1 * b.GIATRI as dongiaGOC, 0 as thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,     " + 
					"  	 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN    " + 
					"  from ERP_HOADONNPP a inner join ERP_HOADONNPP_CHIETKHAU b on a.PK_SEQ = b.hoadon_fk        " + 
					"  	left join KHO d on a.Kho_FK = d.PK_SEQ     " + 
					"  	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ     " + 
					"  	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ     " + 
					"  	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ     " + 
					"  	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK       " + 
					"  	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)       " + 
					"  	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)        " + 
					"  	left join DIABAN dba on e.diaban = dba.pk_seq 		     " + 
					"  	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK      " + 
					"  	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		     " + 
					"  	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    	  " + 
					"  where a.TRANGTHAI not in ( 0, 1, 3, 5 )  " + conditionCK;
			
			sql +=	 " ) "+
					 " DATA  " ;
			// kết với bảng giá vốn
			sql += " LEFT JOIN  " +
				 " ERP_BANGGIA_VON  GIAVON ON DATA.SOLO=GIAVON.SOLO  AND GIAVON.SANPHAM_FK=DATA.SPID and GIAVON.THANG= "+obj.gettungay().substring(5, 7)+" and giavon.nam="+obj.gettungay().substring(0,4)+ " AND GIAVON.NPP_FK="+obj.getnppId();
					 
		}

		//System.out.println("cau truy van la "+sql);
		return sql;
	}	

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String query, String level, String view) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		String chuoi = "";
		if(obj.getLoaiSalesIn().equals("2") && obj.getLaygiavon().equals("1")){
			
			if( obj.getDongiagoc().equals("0") )
				chuoi = getServletContext().getInitParameter("path") + "\\DoanhSoTT_GiaVon.xlsm";
			else
				chuoi = getServletContext().getInitParameter("path") + "\\DoanhSoTT_GiaGoc_GiaVon.xlsm";
 
			
		}else{
			if( obj.getDongiagoc().equals("0") )
				chuoi = getServletContext().getInitParameter("path") + "\\DoanhSoTT.xlsm";
			else
				chuoi = getServletContext().getInitParameter("path") + "\\DoanhSoTT_GiaGoc.xlsm";
		}
		fstream = new FileInputStream(chuoi);		
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
		CreateHeader(workbook,obj);
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

	private void CreateHeader(Workbook workbook, IStockintransit obj)throws Exception
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

		String tieude = "BÁO CÁO DOANH SỐ MUA HÀNG";
		if(obj.getFromMonth().length() > 0)
			tieude = "BÁO CÁO DOANH SỐ MUA HÀNG";
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

		cell = cells.getCell("FE1");		cell.setValue("LoaiHoaDon");
		cell = cells.getCell("FF1");		cell.setValue("SoHoaDon");
		cell = cells.getCell("FG1");		cell.setValue("NgayHoaDon");

		cell = cells.getCell("FH1");		cell.setValue("MaKhachHangCu");
		cell = cells.getCell("FI1");		cell.setValue("MaKhachHangMoi");
		cell = cells.getCell("FJ1");		cell.setValue("TenKhachHang");
		cell = cells.getCell("FK1");		cell.setValue("TrinhDuocVien");

		cell = cells.getCell("FL1");		cell.setValue("MaSanPhamCu");
		cell = cells.getCell("FM1");		cell.setValue("MaSanPhamMoi");		
		cell = cells.getCell("FN1");		cell.setValue("TenSanPham");

		cell = cells.getCell("FO1");		cell.setValue("SoLo");

		cell = cells.getCell("FP1");		cell.setValue("SoLuong");
		cell = cells.getCell("FQ1");		cell.setValue("SoLuongChuan");
		cell = cells.getCell("FR1");		cell.setValue("DonGia");
		cell = cells.getCell("FS1");		cell.setValue("ThanhTien");
		
		if( obj.getDongiagoc().equals("0") )
		{
			cell = cells.getCell("FT1");		cell.setValue("TienThue");
			cell = cells.getCell("FU1");		cell.setValue("ThanhTienSauThue");
			cell = cells.getCell("FV1");		cell.setValue("Scheme");
			cell = cells.getCell("FW1");		cell.setValue("Kenh");
			cell = cells.getCell("FX1");		cell.setValue("DaiLy");
			
			cell = cells.getCell("FY1");		cell.setValue("DonGiaChuan");
			cell = cells.getCell("FZ1");		cell.setValue("DonHangID");
			cell = cells.getCell("GA1");		cell.setValue("DIABAN");
			cell = cells.getCell("GB1");		cell.setValue("GiamSat");
			cell = cells.getCell("GC1");		cell.setValue("ASM");
			cell = cells.getCell("GD1");		cell.setValue("NgayDonHang");
			
			if(obj.getLaygiavon().equals("1"))
			{
				cell = cells.getCell("GE1");		cell.setValue("GiaVon");
				cell = cells.getCell("GF1");		cell.setValue("LaiGop");
			}
			
		}
		else
		{
			cell = cells.getCell("FT1");		cell.setValue("Scheme");
			cell = cells.getCell("FU1");		cell.setValue("Kenh");
			cell = cells.getCell("FV1");		cell.setValue("DaiLy");
			
			cell = cells.getCell("FW1");		cell.setValue("DonGiaChuan");
			cell = cells.getCell("FX1");		cell.setValue("DonHangID");
			cell = cells.getCell("FY1");		cell.setValue("DIABAN");
			
			cell = cells.getCell("FZ1");		cell.setValue("GiamSat");
			cell = cells.getCell("GA1");		cell.setValue("ASM");
			cell = cells.getCell("GB1");		cell.setValue("NgayDonHang");
			
			if(obj.getLaygiavon().equals("1"))
			{
				cell = cells.getCell("GB1");		cell.setValue("GiaVon");
				cell = cells.getCell("GC1");		cell.setValue("LaiGop");
			}	
		}
	}

	private boolean FillData(Workbook workbook, IStockintransit obj, String query) throws Exception
	{	
		dbutils db = new dbutils();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);

		Cells cells = worksheet.getCells();		
		ResultSet rs = db.get(query);
		int i = 2;
		double thanhtien_von=0;
		double laigop=0;
		
		if (rs != null) {
			try 
			{
				Cell cell = null;

				while (rs.next()) 
				{		
					double soluong = rs.getDouble("soluong");
					double soluongCHUAN = rs.getDouble("soluong_chuan");
					double dongia = rs.getDouble("dongia");
					double dongiaGOC = rs.getDouble("dongiaGOC");
					//if( obj.getDongiagoc().equals("1") )
						//dongia = rs.getDouble("dongiaGOC") ;

					String scheme = rs.getString("scheme") == null ? "" : rs.getString("scheme");
					if( scheme.trim().length() > 0 )
					{
						dongia = 0;
						dongiaGOC = 0;
						
						if( rs.getString("tensanpham").trim().length() <= 0 )
						{
							soluong = 0;
							soluongCHUAN = 0;
						}
					}

					double VAT = 0;
					if( obj.getDongiagoc().equals("0") )
						VAT = rs.getString("thueVAT") == null ? 0 : rs.getDouble("thueVAT");
					else //Nếu lấy theo giá gốc thì đơn giá đã có VAT
					{
						//dongia = Math.round( dongia * ( 1 + rs.getDouble("thueVAT") / 100.0 ) );
					
						dongia = Math.round( dongia * ( 1 + rs.getDouble("thueVAT") / 100.0 ) );
						dongiaGOC = Math.round( dongiaGOC * ( 1 + rs.getDouble("thueVAT") / 100.0 ) );
					}

					double thanhtien = Math.round( dongiaGOC * rs.getDouble("soluong_chuan") );
					double thanhtienSAUVAT = 0;
					
					if( rs.getString("tensanpham").trim().length() <= 0 || obj.getDongiagoc().equals("0") )
						thanhtienSAUVAT = Math.round( rs.getDouble("dongiaGOC") * rs.getDouble("soluong_chuan") );
					else
						thanhtienSAUVAT = Math.round( ( dongiaGOC * rs.getDouble("soluong_chuan") ) * ( 1 + VAT / 100.0 ) );
					
					double tienVAT = 0;
					if( obj.getDongiagoc().equals("0") )
						tienVAT = Math.round(  thanhtien * ( VAT / 100.0 ) );

					cell = cells.getCell("FA" + Integer.toString(i));	cell.setValue(rs.getString("vungTen"));
					cell = cells.getCell("FB" + Integer.toString(i));	cell.setValue(rs.getString("khuvucTen"));
					cell = cells.getCell("FC" + Integer.toString(i));	cell.setValue(rs.getString("tinhthanhTen"));
					cell = cells.getCell("FD" + Integer.toString(i));	cell.setValue(rs.getString("quanhuyenTen"));

					cell = cells.getCell("FE" + Integer.toString(i));	cell.setValue(rs.getString("loaidonhang"));
					cell = cells.getCell("FF" + Integer.toString(i));	cell.setValue(rs.getString("sohoadon"));
					cell = cells.getCell("FG" + Integer.toString(i));	cell.setValue(rs.getString("ngayxuatHD"));

					cell = cells.getCell("FH" + Integer.toString(i));	cell.setValue(rs.getString("makhachhangOLD"));
					cell = cells.getCell("FI" + Integer.toString(i));	cell.setValue(rs.getString("makhachhangNEW"));
					cell = cells.getCell("FJ" + Integer.toString(i));	cell.setValue( rs.getString("tenkhachhang"));
					cell = cells.getCell("FK" + Integer.toString(i));	cell.setValue(rs.getString("ddkdTen"));

					cell = cells.getCell("FL" + Integer.toString(i));	cell.setValue(rs.getString("masanphamOLD"));
					cell = cells.getCell("FM" + Integer.toString(i));	cell.setValue(rs.getString("masanphamNEW"));
					cell = cells.getCell("FN" + Integer.toString(i));	cell.setValue(rs.getString("tensanpham"));

					cell = cells.getCell("FO" + Integer.toString(i));	cell.setValue(rs.getString("solo") );

					cell = cells.getCell("FP" + Integer.toString(i));	cell.setValue( soluong );
					cell = cells.getCell("FQ" + Integer.toString(i));	cell.setValue( soluongCHUAN );

					cell = cells.getCell("FR" + Integer.toString(i));	cell.setValue( dongia );

					//double dongiaCHUAN = 0;
					//if( rs.getDouble("soluong_chuan") != 0 )
						//dongiaCHUAN = Math.round( dongia * rs.getDouble("soluong") ) / rs.getDouble("soluong_chuan");
					// don gia trong bang giá vốn là đơn giá chuẩn
					
					if(obj.getLaygiavon().equals("1"))
					{
						thanhtien_von = rs.getDouble("soluong_chuan")* rs.getDouble("dongiavon");  
						laigop = dongia * rs.getDouble("soluong") -thanhtien_von;
					}
					
					if( obj.getDongiagoc().equals("0") )
					{
						cell = cells.getCell("FS" + Integer.toString(i));	cell.setValue( thanhtien );
						
						cell = cells.getCell("FT" + Integer.toString(i));	cell.setValue( tienVAT );
						cell = cells.getCell("FU" + Integer.toString(i));	cell.setValue( thanhtienSAUVAT );
	
						cell = cells.getCell("FV" + Integer.toString(i));	cell.setValue( rs.getString("scheme") );
						cell = cells.getCell("FW" + Integer.toString(i));	cell.setValue( rs.getString("kbhTEN") );
						cell = cells.getCell("FX" + Integer.toString(i));	cell.setValue( rs.getString("daily") );
						
						cell = cells.getCell("FY" + Integer.toString(i));	cell.setValue( dongiaGOC );
						cell = cells.getCell("FZ" + Integer.toString(i));	cell.setValue( rs.getString("hoadonId") );
						cell = cells.getCell("GA" + Integer.toString(i));	cell.setValue( rs.getString("tendiaban")==null?"": rs.getString("tendiaban") );
						
						cell = cells.getCell("GB" + Integer.toString(i));	cell.setValue( rs.getString("gsbhTen") );
						cell = cells.getCell("GC" + Integer.toString(i));	cell.setValue( rs.getString("asmTen") );
						cell = cells.getCell("GD" + Integer.toString(i));	cell.setValue( rs.getString("ngaydonhang") );
						
						if(obj.getLaygiavon().equals("1"))
						{
							cell = cells.getCell("GE" + Integer.toString(i));	cell.setValue(thanhtien_von );
							cell = cells.getCell("GF" + Integer.toString(i));	cell.setValue( laigop);
						}
					}
					else
					{
						cell = cells.getCell("FS" + Integer.toString(i));	cell.setValue( Math.round( thanhtienSAUVAT ) );
						
						cell = cells.getCell("FT" + Integer.toString(i));	cell.setValue( rs.getString("scheme") );
						cell = cells.getCell("FU" + Integer.toString(i));	cell.setValue( rs.getString("kbhTEN") );
						cell = cells.getCell("FV" + Integer.toString(i));	cell.setValue( rs.getString("daily") );
						
						cell = cells.getCell("FW" + Integer.toString(i));	cell.setValue( dongiaGOC );
						cell = cells.getCell("FX" + Integer.toString(i));	cell.setValue( rs.getString("hoadonId") );
						cell = cells.getCell("FY" + Integer.toString(i));	cell.setValue( rs.getString("tendiaban")==null?"": rs.getString("tendiaban") );
					
						cell = cells.getCell("FZ" + Integer.toString(i));	cell.setValue( rs.getString("gsbhTen") );
						cell = cells.getCell("GA" + Integer.toString(i));	cell.setValue( rs.getString("asmTen") );
						cell = cells.getCell("GB" + Integer.toString(i));	cell.setValue( rs.getString("ngaydonhang") );
						
						if(obj.getLaygiavon().equals("1"))
						{
							cell = cells.getCell("GC" + Integer.toString(i));	cell.setValue(thanhtien_von );
							cell = cells.getCell("GD" + Integer.toString(i));	cell.setValue( laigop);
						}
						
					}
					
					++i;					
				}

				if (rs != null) rs.close();

				if(db != null) db.shutDown();

				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}

			}catch(Exception ex){
				if(db != null) db.shutDown();
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}	

	
	
}

