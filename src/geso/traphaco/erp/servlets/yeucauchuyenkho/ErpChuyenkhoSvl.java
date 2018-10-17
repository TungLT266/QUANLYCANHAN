package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkhoList;
import geso.traphaco.erp.util.Library;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class ErpChuyenkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpChuyenkhoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpChuyenkhoList obj;
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
	    	    
	    HttpSession session = request.getSession();	 
	    String ServerletName = this.getServletName();

	    Utility util = new Utility();
	    	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    
	    if (userId.length()==0)
	    	userId = util.antiSQLInspection(request.getParameter("userId"));
	    
	    String action = util.getAction(querystring);
	    
	    String lsxId = util.getId(querystring);
	    obj = new ErpChuyenkhoList();
	  
	    System.out.println("::: ACTION: " + action );
	    if (action.equals("delete"))
	    {	
	    	String msg = this.XoaChuyenKho( lsxId, userId);
    		obj.setMsg(msg);
	    } 
	    else if(action.equals("khongnhanhang"))
    	{
    		String msg = this.KhongNhanHang( lsxId, userId );
    		obj.setMsg(msg);
    	}
	    else if(action.equals("isnhanHang"))
    	{
	    	String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
			geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
    	}
	    else if(action.equals("mochotnhanhang"))
    	{
    		String msg = this.mochotnhanhang( lsxId, userId );
    		obj.setMsg(msg);
    	}
    	
	    String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
	    obj.setUserId(userId);
	    obj.init("");
	    String querySearch = GiuDieuKienLoc.createParams(obj);
	    util.setSearchToHM(userId, session, ServerletName, querySearch);
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";
		response.sendRedirect(nextJSP);
	}
	
	private String mochotnhanhang(String lsxId, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		try
		{
			Utility util = new Utility();
			db.getConnection().setAutoCommit(false);
			 
			msg = util.CheckKhoaSoKho(db, "", "ERP_CHUYENKHO", "NGAYCHUYEN", lsxId);
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return msg;
			}
			
			String query="select pk_seq from erp_chuyenkho where trangthai = 3 and pk_seq= " + lsxId;
			ResultSet rsck=db.get(query);
			if(!rsck.next()){
				msg = "Trạng thái mở chốt nhập kho không hợp lệ, vui lòng thử lại hoặc trong trường hợp phiếu đã được mở chốt trong 1 phiên giao dịch khác ";
				db.getConnection().rollback();
				return msg;
			}
			
			//TĂNG KHO NHẬN
			query = "  select b.loaidoituongNhan, b.doituongnhan_fk as doituongNhanId, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.binNhan_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk   " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' and a.soluong > 0 " + 
					"  group by b.loaidoituongNhan, b.doituongnhan_fk, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, binNhan_fk, a.phieudt, a.phieueo, a.nsx_fk ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			 
			while( rs.next() )
			{
				String khoId = rs.getString("KHONHAN_FK");
				String spId = rs.getString("SanPham_fk");
				String solo = rs.getString("SoLo");
				String ngayhethan = rs.getString("NgayHetHan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				
				String loaidoituong = rs.getString("loaidoituongNhan") == null ? "" : rs.getString("loaidoituongNhan");
				String doituongId = rs.getString("doituongNhanId") == null ? "" : rs.getString("doituongNhanId");
				
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
				
				String nsx_fk = rs.getString("nsx_fk");
				
				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Mở chốt nhận hàng ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					db.getConnection().rollback();
					return msg;
				}
			}
			rs.close();

			query = "Update ERP_CHUYENKHO set ngaysua = '" + getDateTime() + "', nguoisua = '" + userId + "', trangthai = '1' " +
					"where pk_seq = '" + lsxId + "' ";
			
			System.out.println("::: CAP NHAT CK: " + query);	
			if(!db.update(query))
			{
				msg = "Không thể cập nhật ERP_CHUYENKHO " + query;
				db.getConnection().rollback();
				return msg;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			msg = "Exception: " + e.getMessage();
			return msg;
		}
		
		return msg;
	}
	
	private String KhongNhanHang(String lsxId, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();

		try
		{
			//Check lai trang thai, truong hop bam 2 lan
			String query =  "  select trangthai from ERP_CHUYENKHO  where PK_SEQ = '" + lsxId + "'  " ;
			ResultSet rsCheck = db.get(query);
			if( rsCheck != null )
			{
				if( rsCheck.next() )
				{
					if( !rsCheck.getString("trangthai").equals("1") )
					{
						rsCheck.close();
						return "Trạng thái phiếu chuyển không hợp lệ. Vui lòng kiểm tra lại";
					}
				}
				rsCheck.close();
			}

			db.getConnection().setAutoCommit(false);

			query = "update ERP_CHUYENKHO set trangthai = '0', ngaymochot = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai in (0,1,2)";
			System.out.println("::: CHOT CK: " + query);

			if( db.updateReturnInt(query)!=1)
			{
				msg = "Lỗi khi mở chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}

			//TĂNG KHO
			Utility util = new Utility();
			query = "  select  b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo, a.nsx_fk ";

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
				
				String nsx_fk = rs.getString("nsx_fk");

				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho khi không nhận hàng ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, +1 * soluong, 0, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}
			rs.close();

			//XOA NHAN NHAN HANG
			query = "  delete ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG where chuyenkho_fk = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi mở chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//NHỜ KẾ TOÁN VÀO GÀI LẠI CHỖ NÀY
			/*Library lib=new Library();
			String msg1=lib.CapNhatKeToanKho_Chuyenkho(userId, db, lsxId, false, "");
			if(msg1.length()>0){
				msg = "Lỗi khi xóa: " + msg;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}*/

			//Tạo nhận hàng cho chi nhánh nếu là xuất chuyển nội bộ
			/*query = " select NOIDUNGXUAT_FK, DOITUONGNHAN_FK from ERP_CHUYENKHO where pk_seq = '" + lsxId + "'  ";

			System.out.println("::: CHECK CK: " + query);
			rs = db.get(query);

			while( rs.next() )
			{
				String ndxId = rs.getString("NOIDUNGXUAT_FK");
				String doituongId = rs.getString("DOITUONGNHAN_FK") == null ? "" :  rs.getString("DOITUONGNHAN_FK");

				if( ndxId.equals("100073") ) //Xuất chuyển nội bộ, tạo nhận hàng cho bên DMS
				{
					query = " insert TraphacoDMS..NHAPHANG(NGAYNHAN, NGAYCHUNGTU, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, CHUYENKHO_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
							"  select distinct a.ngaychuyen, a.ngaychuyen, '" + doituongId + "' as NPP_FK,   " + 
							"   			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = '" + doituongId + "' ) ),  " + 
							"  			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = '" + doituongId + "' ),  " + 
							"  			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + doituongId + "' )),  " + 
							"  			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + doituongId + "' ) ) ),  " + 
							"   	   '100001' as DVKD_FK, ( select top(1) KBH_FK from NHAPP_KBH where NPP_FK = '" + doituongId + "' ) as KBH_FK, '" + lsxId + "', '0', '" + userId + "', '" + getDateTime() + "', '" + userId + "', '" + getDateTime() + "'  " + 
							"   from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on a.PK_SEQ = b.chuyenkho_fk  " + 
							"   where a.PK_SEQ = '" + lsxId + "' ";
					
					System.out.println("---INSERT NHAN HANG: " + query );
					if(!db.update(query))
					{
						msg = "Không tạo mới NHAPHANG " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					query = " insert TraphacoDMS..NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
							 " select ( select pk_seq from NHAPHANG where CHUYENKHO_FK = a.PK_SEQ  ),     "+
							 "    		b.sanpham_fk, d.soluong, NULL, isnull(b.dongia, 0), 0 as chietkhau, c.DVDL_FK, 0 asLOAI, '' SCHEME, isnull(d.solo, 'NA') solo, isnull(d.ngayhethan, '2019-01-01')  ngayhethan   "+
							 " from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on a.PK_SEQ = b.chuyenkho_fk   "+
							 "   		inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ      "+
							 "    		left join ERP_CHUYENKHO_SANPHAM_CHITIET d on b.sanpham_fk = d.SANPHAM_FK and b.chuyenkho_fk = d.chuyenkho_fk      "+
							 " where a.PK_SEQ = '" + lsxId + "' and d.soluong > 0  ";
					System.out.println("---INSERT NHAN HANG - SP: " + query );
					if(db.updateReturnInt(query) < 1 )
					{
						msg = "Không tạo mới NHAPHANG_SP " + query;
						db.getConnection().rollback();
						return msg;
					}
					
					query = "insert TraphacoDMS..NHAPHANG_DDH(nhaphang_fk, hoadon_fk, isHO )  " +
							"select ( select PK_SEQ from NHAPHANG where CHUYENKHO_FK = '" + lsxId + "' ) as nhID, '" + lsxId + "', 1  ";
					if(!db.update(query))
					{
						msg = "Không tạo mới NHAPHANG_DDH " + query;
						db.getConnection().rollback();
						return msg;
					}
				}
			}
			rs.close();*/
			
			db.getConnection().commit();
			
			//Cập nhật tooltip
			//db.execProceduce2("CapNhatTooltip_CK", new String[] { lsxId } );
		} 
		catch (Exception e)
		{
			e.printStackTrace();

			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}

		db.shutDown();
		return msg;
	}
 
	private String ChotChuyenKho(String lsxId, String userId, String ctyId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		Utility util = new Utility();
		
		try
		{
			//Check các loại chứng từ bắt buộc có kho nhận
			String query =  "  select COUNT(*) as soDong from ERP_CHUYENKHO  " + 
							"  where PK_SEQ = '" + lsxId + "' and NOIDUNGXUAT_FK  " +
							" in ( select PK_SEQ from ERP_NOIDUNGNHAP where MA like '%XK%' and LOAIKHO_NHAP != '' ) and KhoNhan_FK is null " ;
			ResultSet rsCheck = db.get(query);
			 
			if( rsCheck.next() )
			{
				if( rsCheck.getInt("soDong") > 0 )
				{
					rsCheck.close();
					return "Bạn phải chọn kho nhận";
				}
			}
			rsCheck.close();
			  
			db.getConnection().setAutoCommit(false);

			msg = util.CheckKhoaSoKho(db, "", "ERP_CHUYENKHO", "NGAYCHUYEN", lsxId);
			if( msg.trim().length() > 0 )
			{
				db.getConnection().rollback();
				return msg;
			}
			
			query = "update ERP_CHUYENKHO set trangthai = '1', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai=0  ";
			System.out.println("::: CHOT CK: " + query);

			if( db.updateReturnInt(query)!=1 )
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}

			//TRỪ KHO
			query = "  select  b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					"  a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, isnull(a.nsx_fk, 0) as nsx_fk, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, " +
					"  a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo, a.nsx_fk ";

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
				
				String nsx_fk = rs.getString("nsx_fk");

				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Chốt CK - Giam kho Id: "+lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, -1 * soluong, 0, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi chốt chuyển kho   " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}
			rs.close();

			//GHI NHAN NHAN HANG
			query = "  insert ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG( chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  	MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, soluong, nsx_fk		 )  " + 
					"  select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, " + 
					"  		  DT.soluong  as soluong, nsx_fk  " + 
					"  from " + 
					"  ( " + 
					"  	select chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  		MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, SUM(soluong) as soluong, nsx_fk " + 
					"  	from ERP_CHUYENKHO_SANPHAM_CHITIET  " + 
					"  	where chuyenkho_fk = '" + lsxId + "' and chuyenkho_fk in ( select pk_seq from ERP_CHUYENKHO where pk_seq = '" + lsxId + "' and khonhan_fk is not null )  " + 
					"  	group by chuyenkho_fk, SANPHAM_FK, solo, ngayhethan, LOAI, scheme, ngaynhapkho, MARQ, hamluong, hamam, " + 
					"  				MAME, MATHUNG, MAPHIEU, phieudt, phieueo, MAPHIEUDINHTINH, nsx_fk " + 
					"  ) " + 
					"  DT " +
					//", ( select 1 as col1 union select 2 union select 3 union select 4 union select 5 ) DT2 " + 
					"  order by DT.SANPHAM_FK asc, DT.soluong desc ";
			System.out.println("Insert nhan hang: " + query);
			if( !db.update(query) )
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			Library lib=new Library();
			String msg1=lib.CapNhatKeToanKho_Chuyenkho(userId, db, lsxId, false, ctyId);
			if(msg1.length()>0){
				msg = "Lỗi khi xóa: " + msg1;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}

			//Tạo nhận hàng cho chi nhánh nếu là xuất chuyển nội bộ ==> khi nào chạy thật thì mở lại
			query = " select NOIDUNGXUAT_FK, DOITUONGNHAN_FK from ERP_CHUYENKHO where pk_seq = '" + lsxId + "'  ";

			System.out.println("::: CHECK CK: " + query);
			rs = db.get(query);

			String ndxId = "";
			String doituongId = "";
			while( rs.next() )
			{
				ndxId = rs.getString("NOIDUNGXUAT_FK");
				doituongId = rs.getString("DOITUONGNHAN_FK") == null ? "" :  rs.getString("DOITUONGNHAN_FK");
			}
			rs.close();
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//Cập nhật tooltip
			db.execProceduce2("CapNhatTooltip_CK", new String[] { lsxId } );
			
			if( ndxId.equals("100073") )
			{
				//Mấy nữa phải thông qua webservice
				msg = this.TaoNhanHang_ChiNhanh( db, lsxId, doituongId );
				query = "INSERT LOG_SYN_TO_DMS( loaichungtu, sochungtu, tableNAME, msg, status ) " + 
						" VALUES( N'Chuyển kho chi nhánh', '" + lsxId + "', N'ERP_CHUYENKHO', N'" + msg + "', N'" + ( msg.trim().length() > 0 ? "Fail" : "Success" ) + "' )";
				db.update(query);
			}
		} 
		catch (Exception e)
		{
			e.printStackTrace();

			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
		}

		db.shutDown();
		return msg;
	}
	
	private String TaoNhanHang_ChiNhanh(dbutils db, String lsxId, String doituongId )
	{
		String msg = "";
		String query = "";
		
		try
		{
			query = " insert LINK_DMS.DataCenter.dbo.NHAPHANG(NGAYNHAN, NGAYCHUNGTU, SOCHUNGTUGOC, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, CHUYENKHO_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
					"  select distinct a.ngaychuyen, a.ngaychuyen, a.SoChungTu, '" + doituongId + "' as NPP_FK,   " + 
					"   			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = '" + doituongId + "' ) ),  " + 
					"  			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = '" + doituongId + "' ),  " + 
					"  			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + doituongId + "' )),  " + 
					"  			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = '" + doituongId + "' ) ) ),  " + 
					"   	   '100001' as DVKD_FK, ( select top(1) KBH_FK from NHAPP_KBH where NPP_FK = '" + doituongId + "' ) as KBH_FK, '" + lsxId + "', '0', '100002', '" + getDateTime() + "', '100002', '" + getDateTime() + "'  " + 
					"   from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on a.PK_SEQ = b.chuyenkho_fk  " + 
					"   where a.PK_SEQ = '" + lsxId + "' ";
			
			System.out.println("---INSERT NHAN HANG: " + query );
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG " + query;
				return msg;
			}
			
			query = " insert LINK_DMS.DataCenter.dbo.NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
					 " select ( select pk_seq from LINK_DMS.DataCenter.dbo.NHAPHANG where CHUYENKHO_FK = a.PK_SEQ  ),     "+
					 "    		b.sanpham_fk, d.soluong, NULL, isnull(b.dongia, 0), 0 as chietkhau, c.DVDL_FK, 0 asLOAI, '' SCHEME, isnull(d.solo, 'NA') solo, isnull(d.ngayhethan, '2019-01-01')  ngayhethan   "+
					 " from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on a.PK_SEQ = b.chuyenkho_fk   "+
					 "   		inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ      "+
					 "    		left join ERP_CHUYENKHO_SANPHAM_CHITIET d on b.sanpham_fk = d.SANPHAM_FK and b.chuyenkho_fk = d.chuyenkho_fk      "+
					 " where a.PK_SEQ = '" + lsxId + "' and d.soluong > 0  ";
			System.out.println("---INSERT NHAN HANG - SP: " + query );
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Không tạo mới NHAPHANG_SP " + query;
				return msg;
			}
			
			query = "insert LINK_DMS.DataCenter.dbo.NHAPHANG_DDH(nhaphang_fk, hoadon_fk, isHO )  " +
					"select ( select PK_SEQ from LINK_DMS.DataCenter.dbo.NHAPHANG where CHUYENKHO_FK = '" + lsxId + "' ) as nhID, '" + lsxId + "', 1  ";
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG_DDH " + query;
				return msg;
			}
			
			//WebService service = new WebService();
			
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		
		return msg;
	}

	private String MoChotChuyenKho(String lsxId, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();

		try
		{
			String query =  "  select NOIDUNGXUAT_FK, DOITUONGNHAN_FK, sochungtu, " +
							"	isnull( ( select count(*) from LINK_DMS.DataCenter.dbo.NHAPHANG where npp_fk = a.DOITUONGNHAN_FK and  trangthai = '1' and sochungtuGOC = a.sochungtu ) , 0 ) as chinhanh_danhanhang	" +
							"  from ERP_CHUYENKHO a " + 
							"  where PK_SEQ = '" + lsxId + "' " ;
			ResultSet rsCheck = db.get(query);
			
			String NOIDUNGXUAT_FK = "";
			String DOITUONGNHAN_FK = "";
			String sochungtu = "";
			int chinhanh_danhanhang = 0;
			if( rsCheck != null )
			{
				if( rsCheck.next() )
				{
					NOIDUNGXUAT_FK = rsCheck.getString("NOIDUNGXUAT_FK") == null ? "" : rsCheck.getString("NOIDUNGXUAT_FK");
					DOITUONGNHAN_FK = rsCheck.getString("DOITUONGNHAN_FK") == null ? "" : rsCheck.getString("DOITUONGNHAN_FK");
					sochungtu = rsCheck.getString("sochungtu") == null ? "" : rsCheck.getString("sochungtu");
					chinhanh_danhanhang = rsCheck.getInt("chinhanh_danhanhang");
				}
				rsCheck.close();
			}
			
			if( chinhanh_danhanhang > 0 )
			{
				return "Chi nhánh đã nhận hàng, bạn không thể mở chốt";
			}
			
			//Check nếu có kho nhận thì không được mở, qua bên nhận hàng mở
			if( !NOIDUNGXUAT_FK.equals("100073") )
			{
				//Check các loại chứng từ bắt buộc có kho nhận
				query = "  select COUNT(*) as soDong " + 
						"  from ERP_CHUYENKHO  " + 
						"  where PK_SEQ = '" + lsxId + "' and NOIDUNGXUAT_FK in ( select PK_SEQ from ERP_NOIDUNGNHAP where MA like '%XK%' and LOAIKHO_NHAP != '' ) and KhoNhan_FK is null " ;
				rsCheck = db.get(query);
				if( rsCheck != null )
				{
					if( rsCheck.next() )
					{
						if( rsCheck.getInt("soDong") > 0 )
						{
							rsCheck.close();
							return "Bạn phải chọn kho nhận";
						}
					}
					rsCheck.close();
				}
			}

			db.getConnection().setAutoCommit(false);

			query = "update ERP_CHUYENKHO set trangthai = '0', NGAYMOCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai = '1' ";
			System.out.println("::: CHOT CK: " + query);

			if( db.updateReturnInt(query) <= 0 )
			{
				msg = "Lỗi khi mở chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}

			//Tăng KHO ngược lại
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo, nsx_fk ";

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
				
				String nsx_fk = rs.getString("nsx_fk");

				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Hủy chuyển kho đã chốt " + lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, +1 * soluong, 0, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi mở xóa: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}
			rs.close();

			//GHI NHAN NHAN HANG
			query = "  delete ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG where chuyenkho_fk = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi mở chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//XOA KE TOAN
			query = "  delete ERP_PHATSINHKETOAN where loaichungtu = N'Chuyển kho' and sochungtu = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi mở chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}

			System.out.println("::: MO CHOT XONG........ ");
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//Xóa nhận hàng chi nhánh
			if( NOIDUNGXUAT_FK.equals("100073") )
			{
				query = "delete LINK_DMS.DataCenter.dbo.NHAPHANG_SP where nhaphang_fk in ( select pk_seq from LINK_DMS.DataCenter.dbo.NHAPHANG where npp_fk = '" + DOITUONGNHAN_FK + "' and  trangthai = '0' and sochungtuGOC = '" + sochungtu + "' ) ";
				System.out.println(":: 1.XOA NHAN HANG: " + query);
				if( !db.update(query))
				{
					System.out.println("::: LOI XOA NHAN HANG TRANG THAI KHONG DUNG..... ");
					msg = "1.Lỗi khi xóa nhận hàng DMS: " + query;
					return msg;
				}
				
				query = "delete LINK_DMS.DataCenter.dbo.NHAPHANG_DDH where nhaphang_fk in ( select pk_seq from LINK_DMS.DataCenter.dbo.NHAPHANG where npp_fk = '" + DOITUONGNHAN_FK + "' and  trangthai = '0' and sochungtuGOC = '" + sochungtu + "' ) ";
				System.out.println(":: 2.XOA NHAN HANG: " + query);
				if(!db.update(query))
				{
					System.out.println("::: LOI XOA NHAN HANG TRANG THAI KHONG DUNG..... ");
					msg = "2.Lỗi khi xóa nhận hàng DMS: " + query;
					return msg;
				}
				
				query = "delete LINK_DMS.DataCenter.dbo.NHAPHANG where npp_fk = '" + DOITUONGNHAN_FK + "' and  trangthai = '0' and sochungtuGOC = '" + sochungtu + "' ";
				System.out.println(":: 3. XOA NHAN HANG: " + query);
				if(!db.update(query))
				{
					System.out.println("::: LOI XOA NHAN HANG TRANG THAI KHONG DUNG..... ");
					msg = "3.Lỗi khi xóa nhận hàng DMS: " + query;
					return msg;
				}
			}
			
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
		
	private String TaoNhanHang_ChiNhanh_KhongSYN( String lsxId )
	{
		String msg = "";
		String query = "";
		
		dbutils db = new dbutils();
		
		try
		{
			query = " insert LINK_DMS.DataCenter.dbo.NHAPHANG(NGAYNHAN, NGAYCHUNGTU, SOCHUNGTUGOC, NPP_FK, NCC_FK, GSBH_FK, ASM_FK, BM_FK, DVKD_FK, KBH_FK, CHUYENKHO_FK, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA) " +
					"  select distinct a.ngaychuyen, a.ngaychuyen, a.SoChungTu, a.doituongnhan_fk as NPP_FK,   " + 
					"   			( select top(1) NCC_FK from NHACUNGCAP_DVKD where PK_SEQ in ( select NCC_DVKD_FK from NHAPP_NHACC_DONVIKD where NPP_FK = a.doituongnhan_fk  ) ),  " + 
					"  			( select top(1) GSBH_FK from NHAPP_GIAMSATBH where NPP_FK = a.doituongnhan_fk  ),  " + 
					"  			( select top(1) ASM_FK from ASM_KHUVUC where KHUVUC_FK in ( select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.doituongnhan_fk  )),  " + 
					"  			( select top(1) BM_FK from BM_CHINHANH where VUNG_FK in ( select VUNG_FK from KHUVUC where PK_SEQ in (  select KHUVUC_FK from NHAPHANPHOI where PK_SEQ = a.doituongnhan_fk  ) ) ),  " + 
					"   	   '100001' as DVKD_FK, ( select top(1) KBH_FK from NHAPP_KBH where NPP_FK = a.doituongnhan_fk  ) as KBH_FK, '" + lsxId + "', '0', '100002', '" + getDateTime() + "', '100002', '" + getDateTime() + "'  " + 
					"   from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on a.PK_SEQ = b.chuyenkho_fk  " + 
					"   where a.PK_SEQ = '" + lsxId + "' and a.doituongnhan_fk  is not null ";
			
			System.out.println("---INSERT NHAN HANG: " + query );
			if(db.updateReturnInt(query) < 1)
			{
				msg = "Không tạo mới NHAPHANG " + query;
				return msg;
			}
			
			query = " insert LINK_DMS.DataCenter.dbo.NHAPHANG_SP(NHAPHANG_FK, SANPHAM_FK, SOLUONG, soluongNHAN, DONGIA, CHIETKHAU, DVDL_FK, LOAI, SCHEME, SOLO, NGAYHETHAN) " +
					 " select ( select pk_seq from LINK_DMS.DataCenter.dbo.NHAPHANG where CHUYENKHO_FK = a.PK_SEQ  ),     "+
					 "    		b.sanpham_fk, d.soluong, NULL, isnull(b.dongia, 0), 0 as chietkhau, c.DVDL_FK, 0 asLOAI, '' SCHEME, isnull(d.solo, 'NA') solo, isnull(d.ngayhethan, '2019-01-01')  ngayhethan   "+
					 " from ERP_CHUYENKHO a inner join ERP_CHUYENKHO_SANPHAM b on a.PK_SEQ = b.chuyenkho_fk   "+
					 "   		inner join ERP_SANPHAM c on b.sanpham_fk = c.PK_SEQ      "+
					 "    		left join ERP_CHUYENKHO_SANPHAM_CHITIET d on b.sanpham_fk = d.SANPHAM_FK and b.chuyenkho_fk = d.chuyenkho_fk      "+
					 " where a.PK_SEQ = '" + lsxId + "' and d.soluong > 0  ";
			System.out.println("---INSERT NHAN HANG - SP: " + query );
			if(db.updateReturnInt(query) < 1 )
			{
				msg = "Không tạo mới NHAPHANG_SP " + query;
				return msg;
			}
			
			query = "insert LINK_DMS.DataCenter.dbo.NHAPHANG_DDH(nhaphang_fk, hoadon_fk, isHO )  " +
					"select ( select PK_SEQ from LINK_DMS.DataCenter.dbo.NHAPHANG where CHUYENKHO_FK = '" + lsxId + "' ) as nhID, '" + lsxId + "', 1  ";
			if(!db.update(query))
			{
				msg = "Không tạo mới NHAPHANG_DDH " + query;
				return msg;
			}
			
			db.shutDown();
		}
		catch(Exception ex)
		{
			return ex.getMessage();
		}
		
		return msg;
	}

	
	private String XoaChuyenKho(String lsxId, String userId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_CHUYENKHO set trangthai = '4', nguoisua = '"+userId +"' where pk_seq = '" + lsxId + "' and trangthai=0 ";
			if( db.updateReturnInt(query)!=1 )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//TRỪ KHO
			Utility util = new Utility();
			query = "  select  b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					"  a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, " +
					"  a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo, a.nsx_fk ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			/*if( rs != null )
			{*/
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
					
					String nsx_fk = rs.getString("nsx_fk");
					
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa " + lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
				}
				rs.close();
			//}
			
			//Mở lại trạng thái YCCK
			query = "update ERP_YEUCAUCHUYENKHO set trangthai = '0', nguoisua = '" + userId + "' where pk_seq = ( select yeucauchuyenkho_fk from ERP_CHUYENKHO where pk_seq = '" + lsxId + "' and yeucauchuyenkho_fk is not null ) ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			String msg1=Revert_SLYC_Lenhsanxuat(lsxId,db);
			if(msg1.length()>0){
				msg = "Lỗi khi xóa: " + msg1;
				db.getConnection().rollback();
				db.shutDown();
				return msg1;
			}

			  msg1=Revert_SLYC_muahang_giaCong(lsxId,db);
			if(msg1.length()>0){
				msg = "Lỗi khi xóa: " + msg1;
				db.getConnection().rollback();
				db.shutDown();
				return msg1;
			}
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
	private   String Revert_SLYC_muahang_giaCong(String ckid, dbutils db2) {
		// TODO Auto-generated method stub
		try{
			
			String query="SELECT MUAHANG_FK FROM ERP_CHUYENKHO WHERE MUAHANG_FK is not null and  PK_SEQ="+ckid;
			ResultSet rs=db2.get(query);
			String muahang_fk="";
			if(rs.next()){
				muahang_fk=rs.getString("MUAHANG_FK");
			}
			if(muahang_fk.length() >0){
				
			  query=" DELETE    ERP_MUAHANG_BOM_CHITIET   WHERE CHUYENKHO_FK ="+ckid;
			  String msg1;
			  
				if( !db2.update(query) )
				{
					msg1 = "Lỗi khi xóa: " + query;
					db2.getConnection().rollback();
					return msg1;
				}
			
				query=	" UPDATE BOM SET BOM.SOLUONGDAYEUCAU =ISNULL(DATA.SOLUONGCHUAN,0) FROM ERP_MUAHANG_BOM BOM "+ 
					" LEFT JOIN "+
					" (   "+
					" SELECT MUAHANG_FK,VATTU_FK,SUM(SOLUONGQUYCHUANBOM) AS SOLUONGCHUAN    "+
					" FROM ERP_MUAHANG_BOM_CHITIET   where MUAHANG_FK= "+muahang_fk+" and ISYCCK='1' "+   
					" GROUP BY MUAHANG_FK,VATTU_FK "+ 
					" ) DATA    "+
					" ON BOM.MUAHANG_FK=DATA.MUAHANG_FK AND BOM.VATTU_FK=DATA.VATTU_FK "+  
					" where BOM.MUAHANG_FK= "+muahang_fk;
				
				if(!db2.update(query)){
					msg1 = "Lỗi khi xóa: " + query;
					db2.getConnection().rollback();
					return msg1;
				}
			}

		}catch(Exception err){
			return "Lỗi : "+err.getMessage();
		}
		
		
		return "";
	}
	
	private   String Revert_SLYC_Lenhsanxuat(String ckid, dbutils db2) {
		// TODO Auto-generated method stub
		try{
			
			String query="SELECT LENHSANXUAT_FK FROM ERP_CHUYENKHO WHERE LENHSANXUAT_FK is not null and  PK_SEQ="+ckid;
			ResultSet rs=db2.get(query);
			String lsxId="";
			if(rs.next()){
				lsxId=rs.getString("LENHSANXUAT_FK");
			}
			if(lsxId.length() >0){
				
			  query=" DELETE    ERP_LENHSANXUAT_BOM_GIAY_CHITIET   WHERE CHUYENKHO_FK ="+ckid;
			  String msg1;
			  
				if( !db2.update(query) )
				{
					msg1 = "Lỗi khi xóa: " + query;
					db2.getConnection().rollback();
					return msg1;
				}
			
				query=	" UPDATE BOM SET BOM.SOLUONG=ISNULL(DATA.SOLUONGCHUAN,0)  FROM  " +
						" ERP_LENHSANXUAT_BOM_GIAY BOM  LEFT JOIN  ( "+
						" SELECT LENHSANXUAT_FK,VATTU_FK,SUM(SOLUONGCHUAN) AS SOLUONGCHUAN "+
						" FROM ERP_LENHSANXUAT_BOM_GIAY_CHITIET   where LENHSANXUAT_FK= "+lsxId+" and ISYCCK='1' "+
						" GROUP BY LENHSANXUAT_FK,VATTU_FK "+
						" ) DATA "+
						"   ON BOM.LENHSANXUAT_FK=DATA.LENHSANXUAT_FK AND BOM.VATTU_FK=DATA.VATTU_FK "+
						" where BOM.LENHSANXUAT_FK ="+lsxId;
				if(!db2.update(query)){
					msg1 = "Lỗi khi xóa: " + query;
					db2.getConnection().rollback();
					return msg1;
				}
			}

		}catch(Exception err){
			return "Lỗi : "+err.getMessage();
		}
		
		
		return "";
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		String ServerletName = this.getServletName();

		String action = request.getParameter("action");
		if (action == null)
		{
			action = "";
		}

		IErpChuyenkhoList obj = new ErpChuyenkhoList();
		String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpChuyenkho lsxBean = new ErpChuyenkho();
			lsxBean.setUserId(userId);
			lsxBean.createRs();
			lsxBean.settask(isnhanhang);
			session.setAttribute("lsxBean", lsxBean);
			session.setAttribute("khochuyenIds", "");
			session.setAttribute("vitriId", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKhoNew.jsp";
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
				
				String querySearch = GiuDieuKienLoc.createParams(obj);
		    	util.setSearchToHM(userId, session,ServerletName, querySearch);
				
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else if(action.equals("chot") || action.equals("bochot"))
			{
				System.out.println(" vao chot chuyen kho");
				String Id = request.getParameter("chungtu");
				String msg="";
				if(action.equals("chot"))
				{
		    		msg = this.ChotChuyenKho( Id,userId, session.getAttribute("congtyId").toString() );
		    		obj.setMsg(msg);
				}
				else if(action.equals("bochot"))
				{
					msg = this.MoChotChuyenKho( Id, userId);
		    		obj.setMsg(msg);
				}

				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);
				
				String querySearch = GiuDieuKienLoc.createParams(obj);
		    	util.setSearchToHM(userId, session,ServerletName, querySearch);
		    	
				if(msg.length()>0){
					obj.setMsg(msg);

				}
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);	
				
				String querySearch = GiuDieuKienLoc.createParams(obj);
		    	util.setSearchToHM(userId, session,ServerletName, querySearch);
				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpChuyenKho.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpChuyenkhoList obj)
	{
		String	query =   " SELECT A.PK_SEQ, A.TRANGTHAI, A.NGAYCHUYEN, A.NOIDUNGXUAT_FK AS NDXID, B.MA + ', ' + B.TEN AS NOIDUNGXUAT,    " + 
						  " 	ISNULL(KHOTT.TEN,'') AS KHOCHUYEN, ISNULL(KHOTT2.TEN,'') AS KHONHAN,  " + 
						  " 	A.LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA,    " + 
						  " 	ISNULL(A.SOCHUNGTU,'') AS SOCHUNGTU, ISNULL(A.TOOLTIP, '') AS TOOLTIP   " + 
						  " FROM ERP_CHUYENKHO A   " + 
						  "  LEFT JOIN ERP_NOIDUNGNHAP B ON A.NOIDUNGXUAT_FK = B.PK_SEQ   " + 
						 "  LEFT JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ= A.LENHSANXUAT_FK  " + 
						  " LEFT JOIN ERP_KHOTT KHOTT ON A.KHOXUAT_FK = KHOTT.PK_SEQ    " + 
						  " LEFT JOIN ERP_KHOTT KHOTT2 ON A.KHONHAN_FK = KHOTT2.PK_SEQ    " + 
						  " LEFT JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ    " + 
						  " LEFT JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ   " + 
						  " WHERE 1=1    "+
						 	" and (( A.LENHSANXUAT_FK IS NOT NULL AND ISNULL(LSX.TRANGTHAI,0) >= 2 ) OR A.LENHSANXUAT_FK IS NULL OR A.LENHSANXUAT_FK=0) " ; 

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
		
		
		String sanphamct = request.getParameter("sanphamct");
		if(sanphamct == null)
			sanphamct = "";
		obj.setSanphamct(sanphamct);
		
		String solo = request.getParameter("solo");
		if(solo == null)
			solo = "";
		obj.setSolo(solo);
		
		if(tungaySX.length() > 0)
			query += "\n and a.NgayChuyen >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += "\n and a.NgayChuyen  <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += "\n and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += "\n and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += "\n and  cast( a.khoxuat_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += " and  cast( a.khonhan_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(lsxId.length() > 0){
			query += " and (  cast( isnull( a.LENHSANXUAT_FK, 1 ) as nvarchar(10))  like '%" + lsxId + "%' or   "
					+ " isnull(yckd_fk,0) in ( select kd.pk_seq from erp_yeucaukiemdinh kd inner join erp_nhanhang nh "
					+ " on kd.nhanhang_fk=nh.pk_seq where cast(nh.lenhsanxuat_fk as nvarchar(20))  like   '%" + lsxId + "%'   ) ) "+
					"or a.MUAHANG_FK in (select PK_SEQ from ERP_MUAHANG where ISGIACONG='1' and SOPO like   '%" + lsxId + "%')";
		}
		
		if(lydo.length() > 0){
			query += " and a.lydo like N'%" + lydo + "%'";
		}
		
		if(ndxuat.length() > 0){
			query += " and a.noidungxuat_fk = " +ndxuat+ " ";
		}
		if(nguoitao.length() > 0){
			query += " and a.nguoitao = " +nguoitao+ " ";
		}
		if(nguoisua.length() > 0){
			query += " and a.nguoisua = " +nguoisua+ " ";
		}
		
		if(obj.getsochungtubnId().trim().length()>0){
			query+= " and A.LENHDIEUDONG LIKE '%"+sochungtubn+"%' ";
		}
		 
		if(sohoadon.length() >0){
			query+=" AND isnull(a.sochungtu,'') LIKE  '%"+sohoadon+"%'";
		}

		if(sanphamct.length() >0)
		{
			query += "\n  and a.PK_SEQ in (select chuyenkho_fk from ERP_CHUYENKHO_SANPHAM_CHITIET where sanpham_fk = '" + sanphamct + "' )";
		}
		
		if(solo.length() >0){
			query += "\n  and a.PK_SEQ in (select chuyenkho_fk from ERP_CHUYENKHO_SANPHAM_CHITIET where solo like N'%" + solo +  "%')";
		}
		
		System.out.println("\n seach qr:  \n "+query +"\n \n");
		return query;
	}
		
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public static void main(String[] arg)
	{  
		Library lib = new Library(); 
		dbutils db = new dbutils();
		//129472,2017-09-15
		//129557,2017-09-22
		//130354,2017-11-21
		//3 phiếu trên hk có lệnh sản xuất nên hk có chạy lại định khoản => đang yêu cầu khách hàng cập nhật lại lệnh sản xuất
		//String sql = " select pk_seq, nguoisua,ngaychuyen from erp_chuyenkho where trangthai in (1,2,3) and ngaychuyen = '2017-09-15' and pk_seq !=129472 order by ngaychuyen,pk_seq";
		String sql = "select * from ERP_CHUYENKHO a \n " +
				" left join erp_khott khonhan on khonhan.pk_seq=a.khonhan_fk  \n " +
				" left join erp_khott khoxuat on khoxuat.pk_seq=a.khoxuat_fk  \n " +
				" where a.pk_seq not in (129472,129557,130354) and  a.trangthai in (1,2,3) and a.noidungxuat_fk = 100066 \n " +
				" and khonhan.loai != 10 and khoxuat.loai = 10   \n ";
		ResultSet rs =  db.get(sql);
		try {
			if (rs != null){
				while (rs.next()){
					String msg1=lib.CapNhatKeToanKho_Chuyenkho(rs.getString("nguoisua"), db, rs.getString("pk_seq"), false, "100000");
					if (msg1.length() > 0 ){
						System.out.println("loi roi" + msg1 + "ID:"  + rs.getString("pk_Seq" ) + "," + rs.getString("ngaychuyen"));
						db.shutDown();
						return;
					}
				}
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}  
		
	}
	
	
}
