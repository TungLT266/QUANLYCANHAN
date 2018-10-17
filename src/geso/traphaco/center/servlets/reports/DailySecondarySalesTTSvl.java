package geso.traphaco.center.servlets.reports;

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

public class DailySecondarySalesTTSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;

	public DailySecondarySalesTTSvl() 
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

		if(nppId!=null)
			view = "NPP";
		if(nppId==null)
			view ="TT";
		System.out.println("view 2:"+view + "NPP ID: "+nppId);

		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
		obj.setdiscount("1");
		obj.setvat("1");
		obj.init_doanhso();
		obj.setDongiagoc("1");
		obj.setChuyenSale("1");
		
		session.setAttribute("obj", obj);
		if(!view.equals("TT"))
		{
			String nextJSP = "/TraphacoHYERP/pages/Distributor/DailySecondarySales.jsp";
			response.sendRedirect(nextJSP);
		}
		else
		{
			String nextJSP = "/TraphacoHYERP/pages/Center/DailySecondarySales.jsp";
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

		String userId = (String) session.getAttribute("userId");
		String userTen = (String) session.getAttribute("userTen");
		Utility util = new Utility();

		obj.setLoainhanvien(session.getAttribute("loainhanvien"));
	    obj.setDoituongId(session.getAttribute("doituongId"));
	    
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

		obj.setuserId(userId!=null? userId:"");
		obj.setuserTen(userTen!=null? userTen:"");
		obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))!=null?util.antiSQLInspection(request.getParameter("kenhId")):"");
		obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))!=null?util.antiSQLInspection(request.getParameter("vungId")):"");
		obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))!=null?util.antiSQLInspection(request.getParameter("khuvucId")):"");		
		obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhs"))!=null?util.antiSQLInspection(request.getParameter("gsbhs")):"");
		obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))!=null?util.antiSQLInspection(request.getParameter("nppId")):"");
		obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))!=null?util.antiSQLInspection(request.getParameter("dvkdId")):"");
		obj.setnhanhangId(util.antiSQLInspection(request.getParameter("nhanhangId"))!=null?util.antiSQLInspection(request.getParameter("nhanhangId")):"");
		obj.setchungloaiId(util.antiSQLInspection(request.getParameter("chungloaiId"))!=null?util.antiSQLInspection(request.getParameter("chungloaiId")):"");
		obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlid"))!=null?util.antiSQLInspection(request.getParameter("dvdlid")):"");
		obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId"))!=null?util.antiSQLInspection(request.getParameter("ddkdId")):"");
		obj.setkhId(util.antiSQLInspection(request.getParameter("khId"))!=null?util.antiSQLInspection(request.getParameter("khId")):"");
		obj.settype(util.antiSQLInspection(request.getParameter("type")) != null ? util.antiSQLInspection(request.getParameter("type")) : "");
		obj.setMucCN_DT(util.antiSQLInspection(request.getParameter("cndt")) != null ? util.antiSQLInspection(request.getParameter("cndt")) : "1");
		obj.setMuc_KhachHang(util.antiSQLInspection(request.getParameter("kh")) != null ? util.antiSQLInspection(request.getParameter("kh")) : "1");
		obj.setCn1(util.antiSQLInspection(request.getParameter("cn1"))!=null?util.antiSQLInspection(request.getParameter("cn1")):"");
		obj.setkhoId(util.antiSQLInspection(request.getParameter("khoid"))!=null?util.antiSQLInspection(request.getParameter("khoid")):"");
		obj.setDiabanid(util.antiSQLInspection(request.getParameter("diabanid"))!=null?util.antiSQLInspection(request.getParameter("diabanid")):"");
		String nppId= util.getIdNhapp(userId);
		String nppID = util.antiSQLInspection(request.getParameter("nppId"));
		System.out.println("___CN1___"+obj.getCn1());

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
		
		// tỉnh
		String tinh = request.getParameter("tinh");
		if(tinh ==null){
			tinh = "";
		}
		obj.setTinh(tinh);
		// loại đơn hàng
		String loaidonhang = request.getParameter("loaidh");
		if(loaidonhang == null){
			loaidonhang = "";
		}
		obj.setLoaidh(loaidonhang);
		
		obj.init();
		String spid = request.getParameter("spid");
		if(spid == null)
			spid = "";
		obj.setsanphamId(spid);

		
		// lọc theo tỉnh , mã khách hàng, loại đơn hàng 22/12/2015
		session.setAttribute("obj", obj);	
		String level = request.getParameter("level")==null?"":request.getParameter("level");
		obj.settungay(tungay);
		obj.setdenngay(denngay);

		String action = (String) util.antiSQLInspection(request.getParameter("action"));
		String nextJSP = "/TraphacoHYERP/pages/Center/DailySecondarySales.jsp";
		if(!view.equals("TT"))
		{
			nextJSP = "/TraphacoHYERP/pages/Distributor/DailySecondarySales.jsp";
		}
		else
		{
			nextJSP = "/TraphacoHYERP/pages/Center/DailySecondarySales.jsp";
		}

		System.out.println("::: Action la: " + action + " --- MA TDV: " + obj.getDdkd() + "  -- REQUEST: " + request.getParameter("ddkdId") );
		try
		{
			if (action.equals("Taomoi")) 
			{			
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=DoanhSoBanHang.xlsm");
				
				String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
				String query = setQuery("", obj,level,"", view, npp_duocchon_id, session);
				System.out.println("LAY DATA: " + query + "\n");

				if(!CreatePivotTable(out,obj,query,level))
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
/*				String total_Query = getTotal(request, (IStockintransit) obj, view);
				obj.setTotal_Query(total_Query);*/
				
				String npp_duocchon_id = session.getAttribute("npp_duocchon_id") == null ? "" : session.getAttribute("npp_duocchon_id").toString();
				String query = setQuery("", obj,level,"search", view, npp_duocchon_id, session);
				obj.init_doanhso();
				obj.searquery(query);
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
			}			
			response.sendRedirect(nextJSP);
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
			obj.setMsg(ex.getMessage());
			response.sendRedirect(nextJSP);
		}
	}	


	private String setQuery(String sql, IStockintransit obj, String level,String search, String view, String npp_duocchon_id, HttpSession session) 
	{
		Utility util = new Utility();
		String query= "";

		String condition = "";
		String condition1 = "";
		String condition2 = "";
		String conditionCTV = "";
		
		String conditionCHUYENSALE = "";
		String conditionCHUYENSALE_DLPP = "";
		String conditionTRAHANG = "";

		if(obj.gettungay().trim().length() > 0)
		{
			//condition += " and a.NgayDonHang >= '" + obj.gettungay() + "' ";
			condition += " and a.NGAYXUATHD >= '" + obj.gettungay() + "' ";
			condition2 += " and a.NGAYNHAP >= '" + obj.gettungay() + "' ";
			conditionCTV += " and a.NGAYNHAP >= convert( varchar(10), DATEADD(mm, 1, '" + obj.gettungay() + "' ), 120 ) ";
			
			conditionTRAHANG += " and a.NGAYTRA >= '" + obj.gettungay() + "' ";
		}
		if(obj.getdenngay().trim().length() > 0)
		{
			//condition += " and a.NgayDonHang <= '" + obj.getdenngay() + "' ";
			condition += " and a.NGAYXUATHD <= '" + obj.getdenngay() + "' ";
			condition2 += " and a.NGAYNHAP <= '" + obj.getdenngay() + "' ";
			conditionCTV += " and a.NGAYNHAP <= convert( varchar(10), DATEADD(mm, 1, '" + obj.getdenngay() + "' ), 120 ) ";
			
			conditionTRAHANG += " and a.NGAYTRA <= '" + obj.getdenngay() + "' ";
		}

		if(obj.getkenhId().length() > 0)
		{
			condition += " and kenh.PK_SEQ = '"+obj.getkenhId()+"' ";
			condition2 += " and kenh.PK_SEQ = '"+obj.getkenhId()+"' ";
			conditionCTV += " and kenh.PK_SEQ = '"+obj.getkenhId()+"' ";
			conditionTRAHANG += " and kenh.PK_SEQ = '"+obj.getkenhId()+"' ";
		}

		if(obj.getsanphamId().length() > 0)
		{
			condition += " and c.PK_SEQ = '"+obj.getsanphamId()+"' ";
			condition2 += " and c.PK_SEQ = '"+obj.getsanphamId()+"' ";
			conditionCTV += " and c.PK_SEQ = '"+obj.getsanphamId()+"' ";
			conditionTRAHANG += " and kenh.PK_SEQ = '"+obj.getkenhId()+"' ";
		}

		if(obj.getkhId().trim().length() > 0)
		{
			condition += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
			condition2 += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
			conditionCTV += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
			conditionTRAHANG += " and a.KHACHHANG_FK = '" + obj.getkhId() + "' ";
		}

		if(obj.getDdkd().trim().length() > 0)
		{
			condition += " and dh_sp.DDKD_FK = '" + obj.getDdkd() + "' ";
			condition1 += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";

			condition2 += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
			conditionCTV += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
			
			conditionCHUYENSALE += " and dh_sp.DDKD_FK = '" + obj.getDdkd() + "' ";
			conditionCHUYENSALE_DLPP += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
			conditionTRAHANG += " and a.DDKD_FK = '" + obj.getDdkd() + "' ";
		}
		
		//PHAN QUYEN TDV
		condition += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "dh_sp", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		condition1 += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		condition2 += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCTV += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "dh_sp", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE_DLPP += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionTRAHANG += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "DDKD_FK", obj.getLoainhanvien(), obj.getDoituongId() );
	
		//PHAN QUYEN CS
		condition += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		condition1 += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		condition2 += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCTV += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionCHUYENSALE_DLPP += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		conditionTRAHANG += util.getPhanQuyen_TheoNhanVien("CS_BAOCAO", "dba", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
		
		//PHAN QUYEN KBH
		condition += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		condition1 += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		condition2 += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		conditionCTV += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		conditionCHUYENSALE += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		conditionCHUYENSALE_DLPP += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		conditionTRAHANG += " and kenh.pk_seq in " + util.quyen_kenh(obj.getuserId());
		
		//PHAN QUYEN THEO NPP DANG NHAP VAO
		if( obj.getnppId().trim().length() <= 0 && npp_duocchon_id.trim().length() <= 0 )
		{
			if( !view.equals("TT") )
			{
				String congtyId = session.getAttribute("congtyId").toString();
				condition += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' )  )  ";
				condition1 += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				condition2 += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				conditionCTV += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				conditionCHUYENSALE += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				conditionCHUYENSALE_DLPP += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
				conditionTRAHANG += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where congty_fk = '" + congtyId + "' or tructhuoc_fk in ( select PK_SEQ from NHAPHANPHOI where congty_fk = '" + congtyId + "' ) )  ";
			}
		}
		else
		{
			obj.setnppId(npp_duocchon_id);
			condition += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " )  )  ";
			condition1 += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			condition2 += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			conditionCTV += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			conditionCHUYENSALE += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			conditionCHUYENSALE_DLPP += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
			conditionTRAHANG += " and a.npp_fk in ( select pk_seq from NHAPHANPHOI where pk_seq = '" + obj.getnppId() + "' or tructhuoc_fk in ( " + obj.getnppId() + " ) )  ";
		}
		
		if(obj.getDiabanid().trim().length() > 0)
		{
			condition += " and dba.pk_seq = '" + obj.getDiabanid() + "' ";
			condition1 += " and dba.pk_seq = '" + obj.getDiabanid() + "' ";
		}
		if(obj.getDiabanid().trim().length() > 0)
		{
			condition += " and dba.pk_seq = '" + obj.getDiabanid() + "' ";
			condition1 += " and dba.pk_seq = '" + obj.getDiabanid() + "' ";
		}
		
		if(obj.getkhuvucId().length() > 0)
		{
			condition += " and kv.pk_seq = '" + obj.getkhuvucId() + "' ";
			condition1 += " and kv.pk_seq = '" + obj.getkhuvucId() + "' ";
		}

		if(obj.getTinh().length() >0 )
		{
			condition += " and tt.pk_seq = '" + obj.getTinh() + "' ";
			condition1 += " and tt.pk_seq = '" + obj.getTinh() + "' ";
		}

		//PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP
		condition += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "dh_sp", "ddkd_fk", obj.getLoainhanvien(), obj.getDoituongId() );

		if(obj.gettungay().trim().length() > 0)
		{
			//conditionCHUYENSALE += " and substring( a.NgayDonHang, 0, 8 ) = ( select substring( convert( varchar(10), DATEADD(m, -1, '" + obj.gettungay() + "'), 120 ), 0, 8) ) ";
			conditionCHUYENSALE += " and substring( a.NGAYXUATHD, 0, 8 ) = ( select substring( convert( varchar(10), DATEADD(m, -1, '" + obj.gettungay() + "'), 120 ), 0, 8) ) ";
			conditionCHUYENSALE_DLPP += " and substring( a.NGAYNHAP, 0, 8 ) = ( select substring( convert( varchar(10), DATEADD(m, -1, '" + obj.gettungay() + "'), 120 ), 0, 8) ) ";
		}

		String sql1="";
		if(obj.getDongiagoc().equals("1"))
		{
			sql1 += " round( data.dongiagoc * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)";
		}
		else
		{
			sql1 += " round( data.dongia * ( 1 + isnull(data.thuevat,0) / 100.0 ),0)";
		}

		String sqlsolo="b.solo";
		if(search.length()>0)
		{
			sqlsolo="'' as solo";
		}

		//LẤY TỪ SỔ XUẤT HÀNG ĐÃ CHỐT
		condition += " and a.pk_seq in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where TRANGTHAI != 0 ) ) ";
		conditionCHUYENSALE += " and a.pk_seq in ( select hoadon_fk from ERP_SOXUATHANGNPP_DDH where soxuathang_fk in ( select PK_SEQ from ERP_SOXUATHANGNPP where TRANGTHAI != 0 ) ) ";
		
		query = "\n select ROW_NUMBER() OVER (PARTITION BY data.ddkdten order by data.ddkdten desc)as stt,* ,"+
				"\n SUM(data.SOLUONG * "+sql1+") OVER (PARTITION BY data.ddkdten) AS TONGDOANHSO,"+sql1+" as dongiadoanhso,(data.SOLUONG * "+sql1+") as thanhtiensauvat "+
				"\n from ( select data1.tendiaban,donvi,hoadonId,SOHOADON,ngayxuatHD, ngaydonhang, tenkho,loaidonhang,makhachhangOLD,makhachhangNEW,tenkhachhang,ddkdTen,tinhthanhTen,quanhuyenTEN,khuvucTen,vungTen,masanphamOLD,masanphamNEW,tensanpham,solo,SUM(soluong) as soluong,SUM(SoLuong_Chuan) soluong_chuan,dongia,scheme,dongiaGOC,thueVAT,kbhTEN,daily,gsbhTEN,asmTEN from (  ";
		
		/*query +="\n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
				"\n 											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
				"\n 		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
				"\n 		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
				"\n 		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
				"\n 		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
				"\n 		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
				"\n 		 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
				"\n 		 dh_sp.DONGIA, b.scheme,   "+
				"\n 		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily, " + 
				"\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
				"from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
				"\n 	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
				"\n 	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk  and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '')  "+
				"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
				"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
				"\n 	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
				"\n 	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
				"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
				"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
				"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
				"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
				"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
				"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
				"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
				"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
				"\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 2 ) and dbo.trim( b.scheme ) = ''  " + condition ;*/
		
		query += "	 select dba.ten as tendiaban, dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.NGAYDONHANG, d.TEN as tenkho,     "+
				 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
				 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
				 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
				 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
				 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
				 "			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
				 "			 dh_sp.DONGIA, b.scheme,    "+
				 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, isnull(npp.ten, '') as daily,    "+
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
				 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and a.chuyenSALES = '0' and dbo.trim( dh_sp.scheme ) = ''    " + condition;
		
		//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
		//query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
		query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";

		if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM
		{
			/*query += "\n  UNION ALL " + 
					" \n select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
					"	\n 										and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
					"\n 		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
					"\n 		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
					"\n 		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
					"\n 		 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
					"\n 		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					"\n 		 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
					"\n 		 dh_sp.DONGIA, b.scheme,   "+
					"\n 		 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
					"\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					"\n  from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
					"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					"\n 	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '')  "+
					"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					"\n 	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
					"\n 	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
					"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
					"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					"\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 2 ) and dbo.trim( b.scheme ) != ''  " + condition ;*/

			query += "\n  UNION ALL " + 
					 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD,  a.NGAYDONHANG, d.TEN as tenkho,     "+
					 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
					 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
					 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
					 "			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
					 "			 dh_sp.DONGIA, b.scheme,    "+
					 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, isnull(npp.ten, '') as daily,    "+
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
					 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and a.chuyenSALES = '0' and dbo.trim( dh_sp.scheme ) != ''    " + condition;
			
			//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
			//query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";	
			query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";	
		}

		sql = query + condition;


		//ĐƠN HÀNG CTV
		sql += " \n  union ALL  "+
				"\n  select '' as tendiaban, dv.diengiai as donvi, a.pk_seq as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD,  a.NGAYNHAP as NGAYDONHANG, N'Hàng bán' as tenkho,    "+
				"\n 		 N'Cộng tác viên' as loaidonhang,   "+
				"\n 		 e.maFAST as makhachhangOLD,    "+
				"\n 		 e.ma as makhachhangNEW,    "+
				"\n 		 e.TEN as tenkhachhang,    "+
				"	\n 	 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, '' as khuvucTen, '' as vungTen,   "+
				"	\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, '' as SOLO, b.SOLUONG, b.SOLUONG,    "+
				"	\n 	 b.DONGIA, '' as scheme, b.dongia as dongiaGOC, 0 thueVAT, kenh.ten as kbhTEN, '' as congty, '' as daily,   "+
				"\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
				"\n  from DONHANGCTV a inner join DONHANGCTV_SP b on a.PK_SEQ = b.DONHANGCTV_FK   "+
				"\n 	inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ   "+
				"\n 	inner join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
				"\n 	left join CONGTACVIEN ctv on ctv.PK_SEQ = b.CTV_FK     "+
				"\n 	left join TINHTHANH tt on tt.PK_SEQ = e.TINHTHANH_FK   "+
				"\n 	left join QUANHUYEN qh on qh.PK_SEQ = e.QUANHUYEN_FK      "+
				"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
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
				"	\n 	 e.TEN as tenkhachhang,    "+
				"\n 		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
				"	\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham,"+sqlsolo+", b.SOLUONG, b.soluong as SoLuong_Chuan,    "+
				"	\n 	 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
				"	\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as congty, npp.TEN as daily,   "+
				"\n 	isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
				"\n  from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
				"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
				"\n 	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
				"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
				"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
				"\n 	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
				"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
				"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
				"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
				"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
				"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
				"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
				"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
				"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
				"\n  where ( a.CS_DUYET = '1' or a.SS_DUYET = '1' ) and a.chuyenSALES = '0'  and isnull(b.scheme, '') = ''  " + condition2+condition1;

		//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
		//sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
		sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";

		sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );	

		if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM
		{
			sql +=  "\n  UNION ALL " + 
					"\n  select dba.ten as tendiaban,dv.diengiai as donvi, a.PK_SEQ as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD,  a.NGAYNHAP as NGAYDONHANG d.TEN as tenkho,   "+
					"	\n 	 N'Đơn hàng đại lý' as loaidonhang,   "+
					"	\n 	 e.maFAST as makhachhangOLD,    "+
					"	\n 	 e.ma as makhachhangNEW,    "+
					"	\n 	 e.TEN as tenkhachhang,    "+
					"	\n 	 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					"	\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluong as SoLuong_Chuan,    "+
					"	\n 	 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
					"	\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as congty, npp.TEN as daily,   "+
					"\n 	 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					"\n from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
					"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					"\n 	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
					"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					"\n 	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
					"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
					"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					"\n where a.CS_DUYET = '1'  and isnull(b.scheme, '') != ''  " + condition2+condition1;
			
			//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
			//sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
			sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";

			sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );	
		}


		if( obj.getChuyenSale().equals("1") )
		{
			/*sql += "\n  UNION ALL " + 
					"\n  select dba.ten as tendiaban,dv.diengiai as donvi,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
					"\n 											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
					"\n 		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
					"\n 		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
					"\n 		 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
					"	\n 	 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
					"	\n 	 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					"	\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
					"	\n 	 dh_sp.DONGIA, b.scheme,   "+
					"	\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
					"\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					"\n  from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
					"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					"\n 	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '')  "+
					"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					"\n 	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
					"\n 	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
					"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
					"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					"\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 2 ) and dbo.trim( b.scheme ) = '' AND a.chuyenSALES = '1'  " + conditionCHUYENSALE;*/
			
			sql += "\n UNION ALL " + 
					 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD,  a.NGAYDONHANG,  d.TEN as tenkho,     "+
					 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
					 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
					 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
					 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
					 "			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
					 "			 dh_sp.DONGIA, b.scheme,    "+
					 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, isnull(npp.ten, '') as daily,    "+
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
					 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and  dbo.trim( dh_sp.scheme ) = ''  and a.chuyenSALES = '1'  " + conditionCHUYENSALE;
			
			//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
			//query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
			sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";

			
			//CHUYỂN SALES CỦA ĐƠN DLPP
			sql +=  "\n  UNION ALL " + 
					"\n  select  dba.ten as tendiaban,dv.diengiai as donvi, a.PK_SEQ as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD,  a.NGAYNHAP as NGAYDONHANG, d.TEN as tenkho,   "+
					"\n 		 N'Đơn hàng đại lý' as loaidonhang,   "+
					"\n 		 e.maFAST as makhachhangOLD,    "+
					"\n 		 e.ma as makhachhangNEW,    "+
					"	\n 	 e.TEN as tenkhachhang,    "+
					"\n 		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
					"	\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham,"+sqlsolo+", b.SOLUONG, b.soluong as SoLuong_Chuan,    "+
					"	\n 	 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
					"	\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as congty, npp.TEN as daily,   "+
					"\n 	isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
					"\n  from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
					"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
					"\n 	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
					"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
					"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
					"\n 	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
					"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
					"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
					"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
					"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
					"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
					"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
					"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
					"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
					"\n  where ( a.CS_DUYET = '1' or a.SS_DUYET = '1' ) and a.chuyenSALES = '1'  and isnull(b.scheme, '') = ''  " + conditionCHUYENSALE_DLPP;

			//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
			sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";
			sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
			
			
			if(obj.getKhuyenmai().equals("1") )  //LAY HANG KM CHUYEN SALES
			{
				/*sql += "\n  UNION ALL " + 
						"\n  select dba.ten as tendiaban,dv.diengiai,a.PK_SEQ as hoadonId, ISNULL( ( select top(1) sohoadon from ERP_HOADONNPP where trangthai not in ( 3, 5 )  "+
						"\n 											and pk_seq in ( select HOADONNPP_FK from ERP_HOADONNPP_DDH where DDH_FK = a.pk_seq ) ), '' ) as SOHOADON, a.NgayDonHang as ngayxuatHD, d.TEN as tenkho,    "+
						"\n 		 case a.LoaiDonHang when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,   "+
						"\n 		 case a.LoaiDonHang when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,    "+
						"	\n 	 case a.LoaiDonHang when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,    "+
						"	\n 	 case a.LoaiDonHang when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,    "+
						"	\n 	 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
						"	\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, "+sqlsolo+", b.SOLUONG, b.soluongCHUAN as SoLuong_Chuan,    "+
						"	\n 	 dh_sp.DONGIA, b.scheme,   "+
						"	\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as daily,   "+
						"\n 		 isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						"\n  from ERP_DONDATHANGNPP a inner join ERP_DONDATHANGNPP_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk   "+
						"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						"\n 	inner join ERP_DONDATHANGNPP_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.dondathang_fk = dh_sp.dondathang_fk and isnull(dh_sp.sohoadon_import, '') = isnull(b.sohoadon_import, '')  "+
						"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
						"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
						"\n 	left join NHAPHANPHOI npp on a.NPP_DAT_FK = npp.PK_SEQ   "+
						"\n 	left join ERP_NHANVIEN nv on a.nhanvien_fk = nv.PK_SEQ   "+
						"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = dh_sp.DDKD_FK     "+
						"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
						"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
						"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
						"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
						"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
						"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
						"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
						"\n  where a.CS_DUYET = '1'  and a.LoaiDonHang in ( 2 ) and dbo.trim( b.scheme ) != '' AND a.chuyenSALES = '1'  " + conditionCHUYENSALE;*/

				sql += "\n UNION ALL " + 
						 "	 select dba.ten as tendiaban,dv.diengiai as donvi, a.dondathangnpp_fk as hoadonId, a.SOHOADON as SOHOADON, a.NGAYXUATHD as ngayxuatHD, a.NGAYDONHANG, d.TEN as tenkho,     "+
						 "			 case a.LOAIXUATHD when 0 then N'Bán hàng NPP' when 1 then N'ETC' when 2 then N'OTC' else N'Nội bộ' end as loaidonhang,    "+
						 "			 case a.LOAIXUATHD when 0 then npp.maFAST when 1 then e.maFAST when 2 then e.maFAST else nv.MA end	 as makhachhangOLD,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.ma when 1 then e.ma when 2 then e.ma else nv.MA end	 as makhachhangNEW,     "+
						 "			 case a.LOAIXUATHD when 0 then npp.TEN when 1 then e.TEN when 2 then e.TEN else nv.TEN end	 as tenkhachhang,     "+
						 "			 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,    "+
						 "			 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, b.SOLO, b.SOLUONG, b.SoLuong_Chuan as SoLuong_Chuan,     "+
						 "			 dh_sp.DONGIA, b.scheme,    "+
						 "			 dh_sp.DonGia_Chuan as dongiaGOC, dh_sp.VAT as thueVAT, kenh.ten as kbhTEN, '' as congty, isnull(npp.ten, '') as daily,    "+
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
						 "	 where a.TRANGTHAI not in ( 0, 1, 3, 5 ) and a.LOAIXUATHD = '2' and  dbo.trim( dh_sp.scheme ) != ''  and a.chuyenSALES = '1'  " + conditionCHUYENSALE;
				
				//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
				//query += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq and tdv_fk = ddkd.pk_seq ) ";
				sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";
			
				
				//CHUYỂN SALES KM CỦA ĐƠN DLPP
				sql +=  "\n  UNION ALL " + 
						"\n  select  dba.ten as tendiaban,dv.diengiai as donvi, a.PK_SEQ as hoadonId, '' as SOHOADON, a.NGAYNHAP as ngayxuatHD, a.NGAYNHAP as ngaydonhang, d.TEN as tenkho,   "+
						"\n 		 N'Đơn hàng đại lý' as loaidonhang,   "+
						"\n 		 e.maFAST as makhachhangOLD,    "+
						"\n 		 e.ma as makhachhangNEW,    "+
						"	\n 	 e.TEN as tenkhachhang,    "+
						"\n 		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   "+
						"	\n 	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham,"+sqlsolo+", b.SOLUONG, b.soluong as SoLuong_Chuan,    "+
						"	\n 	 dh_sp.GIAMUA as DONGIA, isnull(b.scheme, '') as scheme,   "+
						"	\n 	 dh_sp.dongiaGOC as dongiaGOC, dh_sp.thueVAT as thueVAT, kenh.ten as kbhTEN, '' as congty, npp.TEN as daily,   "+
						"\n 	isnull( ( select top(1) ten from GIAMSATBANHANG where pk_seq = a.gsbh_fk ), '' ) as gsbhTEN, isnull( ( select top(1) ten from ASM where pk_seq in ( select asm_fk from GIAMSATBANHANG where pk_seq = a.gsbh_fk ) ), '' ) as asmTEN   "+
						"\n  from DONHANG a inner join DONHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.DONHANG_FK   "+
						"\n 	left join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ "+
						"\n 	inner join DONHANG_SANPHAM dh_sp on b.SANPHAM_FK = dh_sp.sanpham_fk and b.DONHANG_FK = dh_sp.DONHANG_FK   "+
						"\n 	left join KHO d on a.Kho_FK = d.PK_SEQ   "+
						"\n 	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ   "+
						"\n 	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ   "+
						"\n 	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK     "+
						"\n 	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)     "+
						"\n 	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)      "+
						"\n	left join DIABAN dba on e.diaban = dba.pk_seq 		   "+
						"\n	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK    "+
						"\n	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		   "+
						"\n 	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		   "+
						"\n left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	"+
						"\n  where ( a.CS_DUYET = '1' or a.SS_DUYET = '1' ) and a.chuyenSALES = '1'  and isnull(b.scheme, '') != ''  " + conditionCHUYENSALE_DLPP;
						//"\n  where ( a.CS_DUYET = '1' or a.SS_DUYET = '1' ) and a.chuyenSALES = '1'  and isnull(b.scheme, '') != '' and kenh.PK_SEQ in ( select kbh_fk from hethongbanhang_kenhbanhang where htbh_fk = '100001' ) " + conditionCHUYENSALE_DLPP;

				//Những sản phẩm theo TDV trong DLN này sẽ không được tính trong SALE OUT
				sql += " and c.pk_seq not in ( select sanpham_fk from KHACHHANG_SANPHAM_XQBENHVIEN where khachhang_fk = e.pk_seq  ) ";
				sql += util.getPhanQuyen_TheoNhanVien("ERP_DONDATHANGNPP", "ddkd", "PK_SEQ", obj.getLoainhanvien(), obj.getDoituongId() );
				
			}
		}

		//Đơn trả hàng
		sql += " union all "+
				" select  dba.ten as tendiaban,dv.diengiai as donvi, a.pk_seq as hoadonId, '' as SOHOADON, a.NGAYTRA as ngayxuatHD,  a.NGAYTRA as NGAYDONHANG, d.TEN as tenkho,    " + 
				"   		 N'Đơn trả hàng' as loaidonhang,    " + 
				"   		 e.maFAST as makhachhangOLD,     " + 
				"   		 e.ma as makhachhangNEW,    	 " + 
				"   	 e.TEN as tenkhachhang,     " + 
				"   		 isnull(ddkd.TEN, '') as ddkdTen, ISNULL( tt.TEN, '' ) as tinhthanhTen, ISNULL( qh.TEN, '' ) as quanhuyenTEN, ISNULL( kv.TEN, '' ) as khuvucTen, ISNULL( v.TEN, '' ) as vungTen,   	 " + 
				"   	 c.MA_FAST as masanphamOLD, c.MA as masanphamNEW, c.TEN as tensanpham, dh_sp.solo, -1 * dh_sp.SOLUONG as soluong, -1 * dh_sp.soluong as SoLuong_Chuan,    	 " + 
				"   	 dh_sp.DONGIA as DONGIA, '' as scheme,   	 " + 
				"   	 dh_sp.DONGIA as dongiaGOC, dh_sp.ptVat as thueVAT, kenh.ten as kbhTEN, npp.ten as congty, '' as daily,    " + 
				"   	'' as gsbhTEN, '' as asmTEN    " + 
				"  from DONTRAHANG a    " + 
				"   	inner join DONTRAHANG_SP dh_sp  on a.PK_SEQ = dh_sp.DONTRAHANG_FK " + 
				"   	left join SANPHAM c on c.PK_SEQ = dh_sp.sanpham_fk     " + 
				"   	left join KHO d on a.Kho_FK = d.PK_SEQ    " + 
				"   	left join KHACHHANG e on a.KHACHHANG_FK = e.PK_SEQ    " + 
				"   	left join NHAPHANPHOI npp on a.NPP_FK = npp.PK_SEQ    " + 
				"   	left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = a.DDKD_FK      " + 
				"   	left join TINHTHANH tt on tt.PK_SEQ = isnull(npp.TINHTHANH_FK, e.TINHTHANH_FK)      " + 
				"   	left join QUANHUYEN qh on qh.PK_SEQ = isnull(npp.QUANHUYEN_FK, e.QUANHUYEN_FK)       " + 
				"  	left join DIABAN dba on e.diaban = dba.pk_seq 		    " + 
				"  	left join KHUVUC kv on kv.PK_SEQ = dba.KHUVUC_FK     " + 
				"  	left join VUNG v on v.PK_SEQ = kv.VUNG_FK 		    " + 
				"   	left join KENHBANHANG kenh on a.KBH_FK = kenh.PK_SEQ 		    " + 
				"   left join DONVIDOLUONG dv on dv.PK_SEQ=c.DVDL_FK 	 " + 
				"    where a.TRANGTHAI in ( 1, 2, 5 ) " + conditionTRAHANG;
		
		sql += ") as data1  group by data1.tendiaban,donvi,hoadonId,SOHOADON,ngayxuatHD, ngaydonhang, tenkho,loaidonhang,makhachhangOLD,makhachhangNEW,tenkhachhang,ddkdTen,tinhthanhTen,quanhuyenTEN,khuvucTen,vungTen,masanphamOLD,masanphamNEW,tensanpham,solo,dongia,scheme,dongiaGOC,thueVAT,kbhTEN,daily,gsbhTEN,asmTEN ) as data where  data.soluong<>0  ";
		
		//LOẠI SALES OUT
		//Sales Out OTC: DLPP không thầu bán khách lẻ
		//Sales Out ETC: Báo cáo CTV của "MTV bán bệnh viện + DLPP thầu bán bệnh viện + DLPP không thầu bán bệnh viện"
		System.out.println(":::: TYPE: " + obj.gettype());
		if( obj.gettype().trim().length() > 0 )
		{
			if( obj.gettype().equals("1") ) //Sales out OTC
			{
				sql += " and ( data.loaidonhang in ( N'OTC', N'Đơn hàng đại lý', N'Đơn trả hàng' ) and data.kbhTEN in ( N'TPC', N'SPC', N'SI', N'Khác' ) ) ";
			}
			else if( obj.gettype().equals("2") ) //Sales out ETC
			{
				sql += " and ( data.loaidonhang in ( N'Đơn trả hàng', N'Cộng tác viên' ) and data.kbhTEN in ( N'TPC', N'SPC', N'SI', N'Khác' ) ) ";
				
				/*sql += " and (    ( data.loaidonhang in ( N'OTC', N'Cộng tác viên' ) and data.kbhTEN in ( N'CLC', N'INS', N'DLPP' ) ) " +
								"	or ( data.loaidonhang in ( N'Đơn trả hàng' ) and data.kbhTEN in ( N'CLC', N'INS', N'DLPP' ) )	" +
								"   or ( data.loaidonhang in ( N'Đơn hàng đại lý' ) and data.kbhTEN in ( N'CLC', N'INS', N'DLPP' ) ) ) ";*/
			}
		}
		
		//System.out.println("cau query la  "+sql);
		return sql;
	}	

	private boolean CreatePivotTable(OutputStream out, IStockintransit obj, String query, String level) throws Exception 
	{
		boolean isFillData = false;
		FileInputStream fstream = null;
		Workbook workbook = new Workbook();
		String chuoi = "";

		if( obj.getDongiagoc().equals("0") )
			chuoi = getServletContext().getInitParameter("path") + "\\DoanhSoTT.xlsm";
		else
			chuoi = getServletContext().getInitParameter("path") + "\\DoanhSoTT_GiaGoc.xlsm";

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

		String tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG";
		if(obj.getFromMonth().length() > 0)
			tieude = "BÁO CÁO DOANH SỐ BÁN HÀNG";
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

		if (rs != null) {
			try 
			{
				Cell cell = null;

				while (rs.next()) 
				{					
					double dongia = rs.getDouble("dongia");
					double dongiaGOC = rs.getDouble("dongiaGOC");
					//if( obj.getDongiagoc().equals("1") )
						//dongia = rs.getDouble("dongiaGOC") ;

					String scheme = rs.getString("scheme") == null ? "" : rs.getString("scheme");
					if( scheme.trim().length() > 0 )
					{
						dongia = 0;
						dongiaGOC = 0;
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

					double tienVAT = 0;
					if( obj.getDongiagoc().equals("0") )
						tienVAT = Math.round( ( dongia * rs.getDouble("soluong") ) * ( VAT / 100.0 ) );

					double thanhtienSAUVAT = 0;
					if( obj.getDongiagoc().equals("0") )
						thanhtienSAUVAT = Math.round( ( dongiaGOC * rs.getDouble("soluong_chuan") ) * ( 1 + VAT / 100.0 ) );
					else
						thanhtienSAUVAT = Math.round( dongiaGOC * rs.getDouble("soluong_chuan") );

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

					cell = cells.getCell("FP" + Integer.toString(i));	cell.setValue( rs.getDouble("soluong") );
					cell = cells.getCell("FQ" + Integer.toString(i));	cell.setValue( rs.getDouble("soluong_chuan") );
					cell = cells.getCell("FR" + Integer.toString(i));	cell.setValue( dongia );

					//double dongiaCHUAN = 0;
					//if( rs.getDouble("soluong_chuan") != 0 )
						//dongiaCHUAN = Math.round( dongia * rs.getDouble("soluong") ) / rs.getDouble("soluong_chuan");
					// don gia trong bang giá vốn là đơn giá chuẩn
					
					if( obj.getDongiagoc().equals("0") )
					{
						
						cell = cells.getCell("FS" + Integer.toString(i));	cell.setValue( Math.round( dongiaGOC * rs.getDouble("soluong_chuan") ) );
						
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
						cell = cells.getCell("GC" + Integer.toString(i));	cell.setValue( rs.getString("ngaydonhang") );
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
					}

					++i;					
				}

				if (rs != null) rs.close();

				if(db != null) db.shutDown();

				if(i==2){					
					throw new Exception("Xin loi,khong co bao cao voi dieu kien da chon....!!!");
				}

			}catch(Exception ex){
				ex.printStackTrace();
				throw new Exception(ex.getMessage());
			}
		}else{
			return false;
		}		
		return true;
	}	

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy : hh-mm-ss");
		Date date = new Date();
		return dateFormat.format(date);
	}

}

