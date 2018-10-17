package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoiquycach;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoiquycachList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoiquycach;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoiquycachList;

import java.io.IOException;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpDoiquycachSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDoiquycachSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDoiquycachList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	    

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpDoiquycachList();
	  
	    System.out.println("::: ACTION: " + action );
	    if (action.equals("delete"))
	    {	
	    	String msg = this.XoaChuyenKho( lsxId );
    		obj.setMsg(msg);
	    } 
	    else if(action.equals("chot"))
    	{
    		String msg = this.ChotChuyenKho( lsxId );
    		obj.setMsg(msg);
    	}
	    else if(action.equals("bochot"))
	    {
    		/*dbutils db = new dbutils();
	    	db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();*/
    	}
	    else if(action.equals("hoantat"))
		{
			dbutils db = new dbutils();
	    	db.update("update ERP_CHUYENKHO set trangthai = '3' where pk_seq = '" + lsxId + "'");
	    	db.shutDown();
		}
    	
	    String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCach.jsp";
		response.sendRedirect(nextJSP);
	}
 
	private String ChotChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DOIQUYCACH set trangthai = '1', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' ";
			System.out.println("::: CHOT CK: " + query);
			
			if( !db.update(query) )
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//KHO XUAT TRỪ KHO
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDOI, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_DOIQUYCACH_SANPHAM_CHITIET a inner join ERP_DOIQUYCACH b on a.doiquycach_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYDOI, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";

			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);

			while( rs.next() )
			{
				String khoId = rs.getString("KhoXuat_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");

				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");

				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");

				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");

				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = rs.getDouble("soluong");

				msg = util.Update_KhoTT(rs.getString("NGAYDOI"), "Chốt DQC - Giảm kho xuất: "+lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, -1 * soluong, 0);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi chốt: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}
			rs.close();
			
			//Tăng kho nhận
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDOI, b.KhoNHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_DOIQUYCACH_SANPHAM_CHITIET_NHANDOI a inner join ERP_DOIQUYCACH b on a.doiquycach_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYDOI, b.KhoNHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";

			System.out.println("::: CAP NHAT KHO - TANG KHO NHAN: " + query);
			rs = db.get(query);

			while( rs.next() )
			{
				String khoId = rs.getString("KhoNHAN_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");

				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");

				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");

				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");

				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = rs.getDouble("soluong");

				msg = util.Update_KhoTT(rs.getString("NGAYDOI"), "Chốt DQC - Tăng kho nhận: "+lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi chốt: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}
			rs.close();
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}
		
		return msg;
	}
	
	private String XoaChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DOIQUYCACH set trangthai = '2' where pk_seq = '" + lsxId + "' ";
			System.out.println("::: CHOT CK: " + query);
			
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			Utility util = new Utility();
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDOI, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_DOIQUYCACH_SANPHAM_CHITIET a inner join ERP_DOIQUYCACH b on a.doiquycach_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYDOI, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String khoId = rs.getString("KhoXuat_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = rs.getDouble("soluong");
				
				msg = util.Update_KhoTT(rs.getString("NGAYDOI"), "Xóa DQC - Tăng kho ngược lại trước khi xóa đổi quy cách " + lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + query;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}
			rs.close();
			
			db.getConnection().commit();
			db.shutDown();
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi xóa: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}
		
		return msg;
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

		IErpDoiquycachList obj = new ErpDoiquycachList();
		String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpDoiquycach lsxBean = new ErpDoiquycach();
			lsxBean.setUserId(userId);
			lsxBean.createRs();

			session.setAttribute("ckBean", lsxBean);
			session.setAttribute("khochuyenIds", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCachNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else 
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));
				obj.init(search);
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCach.jsp";

				response.sendRedirect(nextJSP);
			}
			else if(action.equals("chot") || action.equals("bochot"))
			{
				String Id = request.getParameter("chungtu");
				String msg="";
				if(action.equals("chot"))
				{
		    		msg = this.ChotChuyenKho( Id );
		    		obj.setMsg(msg);
				}
				else if(action.equals("bochot"))
				{
					/*dbutils db = new dbutils();
					db.update("update ERP_YEUCAUCHUYENKHO set trangthai = '0' where pk_seq = '" + Id + "'");
					db.shutDown();*/
				}

				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);	
				if(msg.length()>0){
					obj.setMsg(msg);

				}
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCach.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiQuyCach.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDoiquycachList obj)
	{
		String	query = " SELECT A.PK_SEQ, A.TRANGTHAI, A.ngaydoi, KHOTT.TEN as khochuyen, KHOTT2.TEN as khonhan, " +
						" 	A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA    " +
						" FROM ERP_DOIQUYCACH A   " +
						" INNER join ERP_KHOTT KHOTT on a.khoxuat_fk = KHOTT.PK_SEQ   " +
						" INNER join ERP_KHOTT KHOTT2 on a.khonhan_fk = KHOTT2.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						" WHERE 1=1 " ;

		String tungaySX = request.getParameter("tungaySX");
		if(tungaySX == null)
			tungaySX = "";
		obj.setTungayTao(tungaySX);
		
		String denngaySX = request.getParameter("denngaySX");
		if(denngaySX == null)
			denngaySX = "";
		obj.setDenngayTao(denngaySX);
		
		String trangthai = request.getParameter("trangthai");
		if(trangthai == null)
			trangthai = "";
		obj.setTrangthai(trangthai);
		
		String khonhanid = request.getParameter("khonhanId");
		if(khonhanid == null)
			khonhanid = "";
		obj.setkhonhanId(khonhanid);
		
		String sohoadon = request.getParameter("sohoadon");
		if(sohoadon == null)
			sohoadon = "";
		obj.setsohoadon(sohoadon);
		
		String khochuyenid = request.getParameter("khochuyenId");
		if(khochuyenid == null)
			khochuyenid = "";
		obj.setKhoChuyenId(khochuyenid);
		
		String sophieu = request.getParameter("sophieu");
		if(sophieu == null)
			sophieu = "";
		obj.setSophieu(sophieu);
		
		String lsxId = request.getParameter("solenhsx");
		if(lsxId == null)
			lsxId = "";
		obj.setlsxId(lsxId);
		
		String ndxuat = request.getParameter("ndxuat");
		if(ndxuat == null)
			ndxuat = "";
		obj.setNdxuat(ndxuat);
		
		String lydo = request.getParameter("lydo");
		if(lydo == null)
			lydo = "";
		obj.setLydo(lydo);
		
		String nguoitao = request.getParameter("nguoitao");
		if(nguoitao == null)
			nguoitao = "";
		obj.setNguoitao(nguoitao);
		
		String nguoisua = request.getParameter("nguoisua");
		if(nguoisua == null)
			nguoisua = "";
		obj.setNguoisua(nguoisua);
		
		String sochungtubn = request.getParameter("sochungtubn");
		if(sochungtubn == null)
			sochungtubn = "";
		obj.setsochungtubnId(sochungtubn);
		
		
		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setSolo(solo);
		
		if(tungaySX.length() > 0)
			query += "\n and a.ngaydieuchinh >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += "\n and a.ngaydieuchinh  <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += "\n and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += "\n and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += "\n and  cast( a.khott_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += "\n and  cast( a.khott_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(lydo.length() > 0){
			query += "\n and a.lydo like N'%" + lydo + "%'";
		}

		if(nguoitao.length() > 0){
			query += "\n and a.nguoitao = " +nguoitao+ " ";
		}
		if(nguoisua.length() > 0){
			query += "\n and a.nguoisua = " +nguoisua+ " ";
		}
		
		if(solo.length() > 0){
			query += "\n  and a.pk_seq in (select dctk_fk  from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET where solo like N'%" + solo +"%') ";
		}
		
		System.out.println(" seach query: "+ query);
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
}
