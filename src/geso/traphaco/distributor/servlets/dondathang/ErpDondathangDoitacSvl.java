package geso.traphaco.distributor.servlets.dondathang;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangDoitac;
import geso.traphaco.distributor.beans.dondathang.IErpDondathangDoitacList;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangDoitac;
import geso.traphaco.distributor.beans.dondathang.imp.ErpDondathangDoitacList;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLDecoder;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDondathangDoitacSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDondathangDoitacSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDondathangDoitacList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    PrintWriter out = response.getWriter();
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String locgiaQUYDOI = request.getParameter("locgiaQUYDOI");
	    if(locgiaQUYDOI == null)
	    	locgiaQUYDOI = "0";
	    
	    
	    System.out.println("ACTION LA: " + action );
	    if(locgiaQUYDOI.equals("1"))
    	{
	    	String spMa = request.getParameter("spMa");
	    	if(spMa == null)
	    		spMa = "";
	    	
	    	String dvt = request.getParameter("dvt");
	    	if(dvt == null)
	    		dvt = "";
	    	
	    	String dvkdId = "";
	    	if(session.getAttribute("dvkdId") != null )
	    		dvkdId = (String) session.getAttribute("dvkdId");
	    	
	    	String kbhId = "";
	    	if(session.getAttribute("kbhId") != null )
	    		kbhId = (String) session.getAttribute("kbhId");
	    	
	    	String nppId = "";
	    	if(session.getAttribute("nppId") != null )
	    		nppId = (String) session.getAttribute("nppId");
	    	
	    	String doitacId = "";
	    	if(session.getAttribute("doitacId") != null )
	    		doitacId = (String) session.getAttribute("doitacId");
	    	
	    	String query = (String)request.getQueryString();
			spMa = new String(query.substring(query.indexOf("&spMa=") + 6, query.indexOf("&dvt=")).getBytes("UTF-8"), "UTF-8");
			spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
			
			dvt = new String(query.substring(query.indexOf("&dvt=") + 5, query.indexOf("&locgiaQUYDOI")).getBytes("UTF-8"), "UTF-8");
			dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");
			
			//System.out.println(" -- MA SP: " + spMa + " -- DVT: " + dvt );
	    	//spMa = URLDecoder.decode(spMa, "UTF-8").replace("+", " ");
	    	//dvt = URLDecoder.decode(dvt, "UTF-8").replace("+", " ");
	    	
	    	if(spMa.trim().length() > 0 && dvt.trim().length() > 0 )
	    	{
	    		dbutils db = new dbutils();
	    		
	    		/*String command = " select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) as dvNEW, " + 
				 				 "    isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = '100018' ), 0 ) as quydoi, " +
								 " 	  isnull( ( select GIAMUA_SAUCK from BGMUANPP_SANPHAM " +
								 "			    where SANPHAM_FK = a.pk_seq " +
								 "					and BGMUANPP_FK in ( select top(1) PK_SEQ from BANGGIAMUANPP bg inner join BANGGIAMUANPP_NPP bg_npp on bg.PK_SEQ = bg_npp.BANGGIAMUANPP_FK where bg.TRANGTHAI = '1' and bg_npp.NPP_FK = '" + nppId + "' and bg.DVKD_FK = '" + dvkdId + "' and bg.KENH_FK = '" + kbhId + "' order by bg.TUNGAY desc ) ), 0) as giamua " + 
								 " from SANPHAM a where a.MA = '" + spMa + "'  ";*/
	    		
	    		String command = "select a.DVDL_FK as dvCHUAN, ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) as dvNEW,      " +
					    		"	isnull( ( select SOLUONG1 / SOLUONG2 from QUYCACH where SANPHAM_FK = a.pk_seq and DVDL1_FK = a.DVDL_FK and DVDL2_FK = ( select PK_SEQ from DONVIDOLUONG where DONVI = N'" + dvt + "' ) ), 0 ) as quydoi,  	  " +
					    		"	isnull( ( select dongia from BANGGIABANDOITAC_SANPHAM where sanpham_fk = a.pk_seq  " +
					    		"					and BGBANDOITAC_FK in ( select top(1) BANGGIABANDOITAC_FK from BANGGIABANDOITAC_DOITAC where  NPP_FK = '" + doitacId + "' and BANGGIABANDOITAC_FK in ( select pk_seq from BANGGIABANDOITAC where  NPP_FK = '" + nppId + "' and trangthai = '1' and KENH_FK = '" + kbhId + "' ) ) ), 0) as giamua   " +
					    		"from SANPHAM a where a.MA = '" + spMa + "'  ";
	    		
	    		System.out.println("Lay don gia san pham: " + command);
	    		String kq  = "0";
				String quycach = "0";
	    		ResultSet rs = db.get(command);
	    		try
	    		{
					if(rs.next())
					{
						String dvCHUAN = rs.getString("dvCHUAN");
						String dvNEW = rs.getString("dvNEW");
						double quydoi = rs.getDouble("quydoi");
						double dongia = rs.getDouble("giamua");
						quycach = Double.toString(quydoi);
						//System.out.println("DON VI NEW: " + dvNEW);
						if(dvCHUAN.equals(dvNEW))
							kq = Double.toString(dongia);
						else
							kq = Double.toString(dongia * quydoi );
						

					}
					rs.close();
				} 
	    		catch (Exception e)
				{
					kq = "0_0";
				}
	    		
	    		db.shutDown();
				String res = kq + "_" + quycach;
				System.out.println("GIA: " + res);
				out.write(res);
	    	}
	    	else
	    	{
				out.write("0_0");
	    	}
	    	
			return;
    	}
	    else
	    {
	    	String lsxId = util.getId(querystring);
		    obj = new ErpDondathangDoitacList();
		    
		    String loaidonhang = request.getParameter("loaidonhang");
		    if(loaidonhang == null)
		    	loaidonhang = "0";
		    obj.setLoaidonhang(loaidonhang);
		    System.out.println("---LOAI DON HANG: " + loaidonhang);
		    

		    obj.setUserId(userId);
		    
		    if (action.equals("delete") )
		    {	
		    	String msg = this.DeleteChuyenKho(lsxId);
				obj.setMsg(msg);
		    }
		    else if(action.equals("chot"))
	    	{
	    		String msg = this.Chot(lsxId, userId);
    			obj.setMsg(msg);
	    	}
		    else if(action.equals("unchot"))
	    	{
	    		String msg = this.UnChot(lsxId, userId);
    			obj.setMsg(msg);
	    	}
		    
		    obj.setUserId(userId);
		    obj.init("");
		    
			session.setAttribute("obj", obj);
				
			String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangDoiTac.jsp";
			response.sendRedirect(nextJSP);
	    }
	}

	private String UnChot(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_DondathangNPP set trangthai = '0', NPP_DACHOT = '0' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}
	
	private String Chot(String lsxId, String userId) 
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "update ERP_DondathangNPP set trangthai = '1', NPP_DACHOT = '1' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				db.getConnection().rollback();
				return msg;
			}

			//TU DONG TAO XUAT CHUYEN NOI BO TREN TRUNG TAM
			// --> NEU LA CHI NHANH CAC CAP THI LA XUAT CHUYEN NOI BO TREN TRUNG TAM
			// --> NEU LA DOI TAC THI CHAY THEO QUY TRINH BINH THUONG --> KO CAN CHINH
			/*
			 * query =
			 * "select b.loaiNPP, a.npp_fk, a.ngaydenghi, a.kbh_fk, a.kho_fk, a.dvkd_fk  "
			 * +
			 * "from ERP_Dondathang a inner join NHAPHANPHOI b on a.npp_fk = b.pk_seq "
			 * + "where a.pk_seq = '" + lsxId + "' "; ResultSet rs =
			 * db.get(query); String loaiNPP = ""; if(rs.next()) { loaiNPP =
			 * rs.getString("loaiNPP");
			 * 
			 * if(!loaiNPP.equals("4") && !loaiNPP.equals("5") ) { query =
			 * " insert ERP_CHUYENKHO(ngaychuyen, ghichu, trangthai, khoxuat_fk, kbh_fk, npp_fk, ddh_fk, ngaytao, nguoitao, ngaysua, nguoisua) "
			 * + " values('" + rs.getString("ngaydenghi") +
			 * "', N'Chi nhánh đặt hàng', '0', '" + rs.getString("kho_fk") +
			 * "', '" + rs.getString("kbh_fk") + "', " + rs.getString("npp_fk")
			 * + ", '" + lsxId + "', '" + getDateTime() + "', '" + userId +
			 * "', '" + getDateTime() + "', '" + userId + "' )";
			 * 
			 * System.out.println("1.Insert CK: " + query);
			 * if(!db.update(query)) { msg = "Lỗi khi chốt: " + query;
			 * db.getConnection().rollback(); rs.close(); return msg; }
			 * 
			 * query =
			 * "insert ERP_CHUYENKHO_SANPHAM( chuyenkho_fk, SANPHAM_FK, soluongchuyen, dongia, dvdl_fk ) "
			 * +
			 * "select ident_current('ERP_CHUYENKHO'), sanpham_fk, soluong, dongia, DVDL_FK "
			 * + "from ERP_DONDATHANG_SANPHAM where dondathang_fk = '" + lsxId +
			 * "' ";
			 * 
			 * System.out.println("3.Insert CK - SP: " + query);
			 * if(!db.update(query)) { msg =
			 * "Khong the tao moi ERP_CHUYENKHO_SANPHAM: " + query;
			 * db.getConnection().rollback(); rs.close(); return msg; } } }
			 * rs.close();
			 */
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
	}

	private String DeleteChuyenKho(String lsxId)
	{
		dbutils db = new dbutils();
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_DondathangNPP set trangthai = '3' where pk_seq = '" + lsxId + "'";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			//GIAM BOOK, TANG AVAI
			query = "update kho   " +
					"set kho.available = kho.available + BOOK_KHO.soluong,  " +
					"	kho.booked = kho.booked - BOOK_KHO.soluong  " +
					"from " +
					"( " +
					"	select khoxuat_fk, npp_fk, kbh_fk, sanpham_fk, sum(soluong) as soluong  " +
					"	from " +
					"	( " +
					"		select c.kho_fk as khoxuat_fk, c.npp_fk, case when d.dungchungkenh=1 then '100025' else c.kbh_fk end  as kbh_fk, a.sanpham_fk,       " +
					"				case when a.dvdl_fk IS null then a.soluong       " +
					"					 when a.dvdl_fk = b.DVDL_FK then a.soluong      " +
					"					 else  a.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = a.sanpham_fk and DVDL2_FK = a.dvdl_fk and dvdl1_fk = b.dvdl_fk )  end as soluong    " +
					"		from ERP_DONDATHANGNPP_SANPHAM a inner join SANPHAM b on a.sanpham_fk = b.PK_SEQ   " +
					"				inner join ERP_DONDATHANGNPP c on a.dondathang_fk = c.pk_seq  inner join NhaPhanPhoi d on d.pk_seq=c.npp_fk   " +
					"		where a.dondathang_fk in (  " + lsxId + "  ) and a.soluong > 0 " +
					"	) " +
					"	DATHANG  " +
					"	group by khoxuat_fk, npp_fk, kbh_fk, sanpham_fk " +
					") " +
					"BOOK_KHO inner join NHAPP_KHO kho on BOOK_KHO.khoxuat_fk = kho.kho_fk and BOOK_KHO.npp_fk = kho.npp_fk and BOOK_KHO.kbh_fk = kho.kbh_fk and BOOK_KHO.sanpham_fk = kho.sanpham_fk ";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật NHAPP_KHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			// PHAI HUY DON DUOI Đối tác trực thuộc đặt lên (trường hợp không
			// phải tự tao mới)
			query = "update ERP_Dondathang set trangthai = '3', GHICHU = N'Cấp trên không duyệt' where pk_seq = ( select from_dondathang from ERP_DondathangNPP where pk_seq = '" + lsxId + "' ) ";
			if(!db.update(query))
			{
				msg = "1.Khong the xoa: " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch (Exception e) 
		{
			db.update("rollback");
			db.shutDown();
			return "Exception: " + e.getMessage();
		}
		
		return "";
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    
	    String action = request.getParameter("action");
	    if (action == null)
	    {
	    	action = "";
	    }
	    
	    String loaidonhang = request.getParameter("loaidonhang");
	    if(loaidonhang == null)
	    	loaidonhang = "0";
	    
	    
		IErpDondathangDoitacList obj = new ErpDondathangDoitacList();
		obj.setLoaidonhang(loaidonhang);
		
	    Utility util = new Utility();
	    
		HttpSession session = request.getSession();
	    String userId = util.antiSQLInspection(request.getParameter("userId")); 
	    
	    obj.setUserId(userId);

	    if(action.equals("Tao moi"))
	    {
	    	IErpDondathangDoitac lsxBean = new ErpDondathangDoitac();
	    	lsxBean.setLoaidonhang(loaidonhang);
	    	lsxBean.setUserId(userId);
	    	
	    	lsxBean.createRs();
	    	session.setAttribute("dvkdId", lsxBean.getDvkdId());
			session.setAttribute("kbhId", lsxBean.getKbhId());
			session.setAttribute("nppId", lsxBean.getNppId());
			session.setAttribute("doitacId", "");
    		
	    	session.setAttribute("lsxBean", lsxBean);
	    	
    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangDoiTacNew.jsp";
    		response.sendRedirect(nextJSP);
	    }
	    else
	    {
	    	if(action.equals("view") || action.equals("next") || action.equals("prev"))
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
		    	obj.setUserId(userId);
		    	obj.init(search);
		    	obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
		    	session.setAttribute("obj", obj);
		    	
		    	String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangDoiTac.jsp";
				response.sendRedirect(nextJSP);
		    }
	    	else
	    	{
		    	String search = getSearchQuery(request, obj);
		    	obj.setUserId(userId);
		    	obj.init(search);
				
		    	session.setAttribute("obj", obj);  	
	    		session.setAttribute("userId", userId);
		
	    		String nextJSP = "/TraphacoHYERP/pages/Distributor/ErpDonDatHangDoiTac.jsp";
	    		response.sendRedirect(nextJSP);
	    	}
	    }
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDondathangDoitacList obj)
	{
		Utility util = new Utility();
		String query = "select a.PK_SEQ, a.trangthai, a.ngaydonhang, c.ten as nppTEN, b.ten as khoTEN, NV.TEN as nguoitao, ISNULL(cast(a.from_dondathang as nvarchar),'')as maddh, b.ten as khonhan, a.NGAYSUA, a.NGAYTAO, NV2.TEN as nguoisua, a.NPP_DACHOT,   " +
					    " nk.diengiai  as KenhBanHang,isnull(a.iskm,0) as iskm "+
						"from ERP_DondathangNPP a inner join KHO b on a.kho_fk = b.pk_seq inner join NHAPHANPHOI c on a.NPP_DAT_FK = c.pk_seq inner join NHOMKENH nk on a.nhomkenh_fk = nk.pk_seq  " +
						"inner join NHANVIEN nv on a.NGUOITAO = nv.PK_SEQ   " +
						"inner join NHANVIEN nv2 on a.NGUOISUA = nv2.PK_SEQ where a.pk_seq > 0 and a.kho_fk in "+util.quyen_kho(obj.getUserId());
		
		String tungay = request.getParameter("tungay");
		if(tungay == null)
			tungay = "";
		obj.setTungay(tungay);
		
		String denngay = request.getParameter("denngay");
		if(denngay == null)
			denngay = "";
		obj.setDenngay(denngay);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String nppId = request.getParameter("nppId");
		if(nppId == null)
			nppId = "";
		obj.setNppTen(nppId);
		
		String sodonhang = request.getParameter("sodonhang");
		if(sodonhang == null)
			sodonhang = "";
		obj.setSodonhang(sodonhang);
		
		String khId = request.getParameter("khId");
		if(khId == null)
			khId = "";
		obj.setKhTen(khId);
		
		String madhdt = request.getParameter("madhdt");
		if(madhdt == null)
			madhdt = "";
		obj.setMaddh(madhdt);
		
		
		String iskm = util.antiSQLInspection(request.getParameter("iskm")==null?"0":request.getParameter("iskm"));
		obj.setIsKm(iskm);
		
		if(iskm.length() > 0)
			query += " and a.iskm = '" + iskm + "' ";
		
		
		if(tungay.length() > 0)
			query += " and a.ngaydonhang >= '" + tungay + "'";
		
		if(denngay.length() > 0)
			query += " and a.ngaydonhang <= '" + denngay + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(nppId.length() > 0){
			query += " and a.NPP_FK= '" + nppId + "'";
		}
		
		if(sodonhang.length() > 0){
			query += " and cast( a.PK_SEQ as varchar(10) ) like '%" + sodonhang + "%'";
		}
		
		if(khId.length() > 0){
			query += " and a.npp_dat_FK = '" + khId + "'";
		}
		if(madhdt.length() > 0){
			query += " and a.from_dondathang = '" + madhdt + "'";
		}
		System.out.print(query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
