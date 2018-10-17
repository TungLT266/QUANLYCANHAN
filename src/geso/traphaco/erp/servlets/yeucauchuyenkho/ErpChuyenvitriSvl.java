package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenvitri;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenvitriList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenvitri;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenvitriList;

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

public class ErpChuyenvitriSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpChuyenvitriSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpChuyenvitriList obj;
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
	    obj = new ErpChuyenvitriList();
	  
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
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenViTri.jsp";
		response.sendRedirect(nextJSP);
	}
 
	private String ChotChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CHUYENVITRI set trangthai = '1', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai=0 ";
			System.out.println("::: CHOT CK: " + query);
			
			if( db.updateReturnInt(query)!=1 )
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//TRỪ KHO
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.Khochuyen_FK, isnull(a.binchuyen_fk, 0) as binchuyen_fk,  isnull(a.binnhan_fk, 0) as binnhan_fk,  a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk   " + 
					"  from ERP_CHUYENVITRI_SANPHAM_CHITIET a inner join ERP_CHUYENVITRI b on a.chuyenvitri_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.Khochuyen_FK, a.binchuyen_fk, a.binnhan_fk, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo, a.nsx_fk ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String khoId = rs.getString("Khochuyen_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String binChuyen_fk = rs.getString("binchuyen_fk");
				String binNhan_fk = rs.getString("binnhan_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = rs.getDouble("soluong");
				
				String nsx_fk = rs.getString("nsx_fk");
				
				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Chốt chuyển vị trí - giảm kho chuyển: " + lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binChuyen_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, -1 * soluong, 0, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi chốt: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				
				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Chốt chuyển vị trí - tăng kho nhận: " + lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binNhan_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, nsx_fk);
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
			
			String query = "update ERP_CHUYENVITRI set trangthai = '4' where pk_seq = '" + lsxId + "' and trangthai=0 ";
			System.out.println("::: CHOT CK: " + query);
			
			if( db.updateReturnInt(query)!=1 )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//TĂNG KHO NGƯỢC LẠI KHI XÓA
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.Khochuyen_FK, isnull(a.binchuyen_fk, 0) as binchuyen_fk,  isnull(a.binnhan_fk, 0) as binnhan_fk,  a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk  " + 
					"  from ERP_CHUYENVITRI_SANPHAM_CHITIET a inner join ERP_CHUYENVITRI b on a.chuyenvitri_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.Khochuyen_FK, a.binchuyen_fk, a.binnhan_fk, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo, a.nsx_fk ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String khoId = rs.getString("Khochuyen_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String binChuyen_fk = rs.getString("binchuyen_fk");
				String binNhan_fk = rs.getString("binnhan_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = rs.getDouble("soluong");
				
				String nsx_fk = rs.getString("nsx_fk");
				
				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Xóa chuyển vị trí - tăng kho chuyển ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binChuyen_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + msg;
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
	

	private String MoChotChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CHUYENVITRI set trangthai = '0', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' ";
			System.out.println("::: CHOT CK: " + query);
			
			if( !db.update(query) )
			{
				msg = "Lỗi khi mở chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//Tăng KHO lại
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.Khochuyen_FK, isnull(a.binchuyen_fk, 0) as binchuyen_fk,  isnull(a.binnhan_fk, 0) as binnhan_fk,  a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_CHUYENVITRI_SANPHAM_CHITIET a inner join ERP_CHUYENVITRI b on a.chuyenvitri_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.Khochuyen_FK, a.binchuyen_fk, a.binnhan_fk, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			while( rs.next() )
			{
				String khoId = rs.getString("Khochuyen_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId") == null ? "" :  rs.getString("doituongId");
				
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String binChuyen_fk = rs.getString("binchuyen_fk");
				String binNhan_fk = rs.getString("binnhan_fk");
				
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");

				double soluong = rs.getDouble("soluong");
				
				msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Chốt chuyển vị trí - tăng kho chuyển ( mở lại phiếu ) ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binChuyen_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, +1 * soluong, 0);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi mở chốt: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
				
				msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Chốt chuyển vị trí - giảm kho nhận ( mở lại phiếu )  ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binNhan_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi mở chốt: " + msg;
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
			
			msg = "Lỗi khi mở chốt: " + e.getMessage();
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

		IErpChuyenvitriList obj = new ErpChuyenvitriList();
		String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpChuyenvitri lsxBean = new ErpChuyenvitri();
			lsxBean.setUserId(userId);
			lsxBean.createRs();

			session.setAttribute("ckBean", lsxBean);
			session.setAttribute("khochuyenIds", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenViTriNew.jsp";
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
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenViTri.jsp";

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

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenViTri.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenViTri.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpChuyenvitriList obj)
	{
		String	query = " SELECT A.PK_SEQ, A.TRANGTHAI, A.NgayChuyen, KHOTT.TEN as khochuyen, case when bin.pk_seq is null then N'Không có vị trí' else BIN.MA + ' - ' + BIN.TEN end as vitrichuyen, " +
						"\n 	A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA    " +
						"\n  FROM ERP_CHUYENVITRI A   " +
						"\n  INNER join ERP_KHOTT KHOTT on a.khochuyen_fk = KHOTT.PK_SEQ   " +
						"\n  left join ERP_BIN BIN on a.binchuyen_fk = BIN.PK_SEQ   " +
						"\n  INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						"\n  INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						"\n  WHERE 1=1  " ;

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
			query += "\n  and a.NgayChuyen >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += "\n  and a.NgayChuyen  <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += "\n  and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += "\n  and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += "\n  and  cast( a.khoxuat_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += "\n  and  cast( a.khonhan_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(lydo.length() > 0){
			query += "\n  and a.lydo like N'%" + lydo + "%'";
		}

		if(nguoitao.length() > 0){
			query += "\n  and a.nguoitao = " +nguoitao+ " ";
		}
		if(nguoisua.length() > 0){
			query += "\n  and a.nguoisua = " +nguoisua+ " ";
		}
		

		if(sohoadon.length() >0){
			query+="\n  AND A.sochungtu LIKE  '%"+sohoadon+"%'";
		}
		
		if(solo.length() >0){
			query+="\n and a.pk_seq in (select chuyenvitri_fk  from ERP_CHUYENVITRI_SANPHAM_CHITIET where solo like N'%"+ solo+"%') ";
		}
		
		System.out.println(" \n seach query: "+ query);
		
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] arg )
	{
		ErpChuyenvitriSvl cvt = new ErpChuyenvitriSvl();
		
		cvt.MoChotChuyenKho("100607");
		
		System.out.println("::: MO CHOT THANH CONG.... ");
	}
	
	
}
