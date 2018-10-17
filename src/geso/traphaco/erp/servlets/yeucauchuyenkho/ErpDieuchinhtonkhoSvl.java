package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.GiuDieuKienLoc;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDieuchinhtonkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDieuchinhtonkhoList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDieuchinhtonkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDieuchinhtonkhoList;
import geso.traphaco.erp.util.Library;

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

public class ErpDieuchinhtonkhoSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDieuchinhtonkhoSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDieuchinhtonkhoList obj;
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
	    obj = new ErpDieuchinhtonkhoList();
	  
	    System.out.println("::: ACTION: " + action );
	    if (action.equals("delete"))
	    {	
	    	String msg = this.XoaChuyenKho( lsxId );
    		obj.setMsg(msg);
	    } 
	    else if(action.equals("chot"))
    	{
    		String msg = this.ChotChuyenKho( lsxId ,userId);
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
	    if(action.equals("goBack")){
			String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
	    	geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
	    }
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDieuChinhTonKho.jsp";
		response.sendRedirect(nextJSP);
	}
 
	private String ChotChuyenKho(String lsxId,String userid) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DIEUCHINHTONKHOTT set trangthai = '1', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai=0 ";
			System.out.println("::: CHOT CK: " + query);
			
			if( db.updateReturnInt(query)!=1 )
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//CẬP NHẬT KHO ==> mấy nữa cải tiến booked tính động, không lấy trong kho
			Utility util = new Utility();
		  String ctyId="";
				  
			query = "  select 	b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk, ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, a.SoLo, a.NgayHetHan, a.ngaynhapkho,   " + 
					"  	a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong, " + 
					"  			c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE,ISNULL(a.NSX_FK,0) AS NSX_FK  " + 
					"  from ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a inner join ERP_DIEUCHINHTONKHOTT b on a.dctk_FK = b.PK_SEQ  " + 
					"  	 inner join ERP_KHOTT_SP_CHITIET c on b.khott_fk = c.KHOTT_FK and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and a.sanpham_fk = c.SANPHAM_FK  " + 
					"  		and ISNULL(	b.loaidoituong, 0 ) = 	ISNULL(	c.loaidoituong, 0 ) and ISNULL( b.DOITUONG_FK, 0 ) = ISNULL(	c.DOITUONGID, 0 ) " + 
					"  		and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO " + 
					"  		and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '') " + 
					"  		and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0) " + 
					"  		and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '') " + 
					"  		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '') AND ISNULL(a.NSX_FK,0) = ISNULL(c.NSX_FK,0) " + 
					"  where b.PK_SEQ = '" + lsxId + "' ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
		 
				while( rs.next() )
				{
					 
					String khoId = rs.getString("Khott_FK");
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
					String NSX_FK = rs.getString("NSX_FK");
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh cột số lượng tỏng kho
					double soluongDIEUCHINH = 0;
					if( soluong < booked )
					{
						msg = "Tồn kho của sản phẩm ( " + rs.getString("tensp") + " ) sau điều chỉnh ( " + soluong + " ) đang nhỏ hơn số lượng hàng đang booked ( " + booked + " ) ";
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
					soluongDIEUCHINH = soluong - tonkho;
					
					//Lưu ý, nếu điều chỉnh tăng thì ngày nhập kho phải = ngày điều chỉnh
					String ngaynhapkhoTANG = rs.getString("ngaynhapkho");
					if( soluongDIEUCHINH > 0 )
						ngaynhapkhoTANG = rs.getString("NGAYDIEUCHINH");
					
					query = "update ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET set soluongHT = '" + tonkho + "', bookedHT = '" + booked + "', avaiHT = '" + avai + "', soluongDIEUCHINH = '" + soluongDIEUCHINH + "', ngaynhapkhoTANG = '" + ngaynhapkhoTANG + "' " +
							"where dctk_fk = '" + lsxId + "' and sanpham_fk = '" + spId + "' and solo = '" + solo + "' and ngayhethan = '" + ngayhethan + "' and ngaynhapkho = '" + ngaynhapkho + "'  " +
							"	  and isnull(mame, '') = '" + mame + "' and isnull(mathung, '') = '" + mathung + "' " + 
							" 	  and maphieu = '" + maphieu + "' and phieudt = '" + phieudt + "' and phieueo = '" + phieueo + "' " + 
							" 	 AND ISNULL(NSX_FK,0) = "+NSX_FK+" AND  ISNULL(bin_fk,0) = '" + bin_fk + "' and hamam = '" + hamam + "' and hamluong = '" + hamluong + "'  ";
					System.out.println("::: CAP NHAT DCTK: " + query);
					if( db.updateReturnInt(query) < 1 )
					{
						msg = "Lỗi khi cập nhật ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET: " + query;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
					if( soluongDIEUCHINH != 0 )
					{
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt dctk: "+lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkhoTANG, 
								mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluongDIEUCHINH, 0, soluongDIEUCHINH,NSX_FK);
						if( msg.trim().length() > 0 )
						{
							db.getConnection().rollback();
							db.shutDown();
							return msg;
						}
					}
				}
				rs.close();
				Library lib=new Library();
			String msg1= lib.capnhatketoan_Xuat_DieuChinhTonkho(userid, db, lsxId, false, ctyId);
			if(msg1.length()>0){
				db.getConnection().rollback();
				db.shutDown();
				return msg1;
			}
			msg1= lib.capnhatketoan_Nhap_DieuChinhTonkho(userid, db, lsxId, false, ctyId);
				if(msg1.length()>0){
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
	
	private String XoaChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DIEUCHINHTONKHOTT set trangthai = '2' where pk_seq = '" + lsxId + "' and trangthai=0 ";
			System.out.println("::: CHOT CK: " + query);
			
			if( db.updateReturnInt(query)!=1 )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//TĂNG KHO NGƯỢC LẠI KHI XÓA ==> KHÔNG BOOKED KHO, KHI CHỐT SẼ KIỂM TRA TỒN KHO
			/*Utility util = new Utility();
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
				
				msg = util.Update_KhoTT(rs.getString("NGAYCHUYEN"), "Xóa chuyển vị trí - tăng kho chuyển ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binChuyen_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return msg;
				}
			}
			rs.close();*/
			
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

		IErpDieuchinhtonkhoList obj = new ErpDieuchinhtonkhoList();
		String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpDieuchinhtonkho lsxBean = new ErpDieuchinhtonkho();
			lsxBean.setUserId(userId);
			lsxBean.createRs();

			session.setAttribute("ckBean", lsxBean);
			session.setAttribute("khochuyenIds", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDieuChinhTonKhoNew.jsp";
			response.sendRedirect(nextJSP);
		}
		else 
		{
			if(action.equals("view") || action.equals("next") || action.equals("prev"))
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);
				obj.setNxtApprSplitting(Integer.parseInt(request.getParameter("nxtApprSplitting")));

				String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				obj.init("");

				String querySearch = GiuDieuKienLoc.createParams(obj);
				util.setSearchToHM(userId, session,this.getServletName(), querySearch);
				
				obj.setAttribute(request, action, "list", "crrApprSplitting", "nxtApprSplitting");
				session.setAttribute("obj", obj);
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDieuChinhTonKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else if(action.equals("chot") || action.equals("bochot"))
			{
				String Id = request.getParameter("chungtu");
				String msg="";
				if(action.equals("chot"))
				{
		    		msg = this.ChotChuyenKho( Id ,userId);
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

				String searchQuery=util.getSearchFromHM(userId,this.getServletName(), session);
 	    		geso.traphaco.center.util.GiuDieuKienLoc.setParamsToOject(obj, searchQuery);
				obj.init("");	
				if(msg.length()>0){
					obj.setMsg(msg);

				}
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDieuChinhTonKho.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDieuChinhTonKho.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDieuchinhtonkhoList obj)
	{
		String	query = "\n SELECT A.PK_SEQ, A.TRANGTHAI, A.ngaydieuchinh, KHOTT.TEN as khochuyen, isnull(BIN.MA + ' - ' + BIN.TEN, '') as vitrichuyen, " +
						"\n 	A.LYDODIEUCHINH as LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA    " +
						"\n FROM ERP_DIEUCHINHTONKHOTT A   " +
						"\n INNER join ERP_KHOTT KHOTT on a.khott_fk = KHOTT.PK_SEQ   " +
						"\n LEFT join ERP_BIN BIN on a.bin_fk = BIN.PK_SEQ   " +
						"\n INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						"\n INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						"\n WHERE 1=1  " ;

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
