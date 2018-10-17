package geso.traphaco.erp.servlets.yeucauchuyenkho;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoisolo;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoisoloList;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoisolo;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpDoisoloList;

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

public class ErpDoisoloSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
       
    public ErpDoisoloSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		IErpDoisoloList obj;
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
	    obj = new ErpDoisoloList();
	  
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
    	
	    String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
	    obj.setUserId(userId);
	    obj.init("");
	    
		session.setAttribute("obj", obj);
			
		String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLo.jsp";
		response.sendRedirect(nextJSP);
	}
 
	private String ChotChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DOISOLO set trangthai = '1', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' and trangthai=0 ";
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
			query = "  select a.nsxNEW_fk, a.nsx_fk, a.pk_seq, b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk, ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, a.SoLo, a.NgayHetHan, a.ngaynhapkho,   " + 
					"  	a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong, " + 
					"  			c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE,  " +
					"			b.loaidieuchinh, a.soloMoi, a.ngayhethanMoi, a.marquetteMoi,a.maphieuMoi, a.thungMoi, a.meMoi, a.phieudtMoi, a.hamamMoi, a.hamluongMoi		" +
					"  from ERP_DOISOLO_SANPHAM_CHITIET a inner join ERP_DOISOLO b on a.doisolo_FK = b.PK_SEQ  " + 
					"  	 inner join ERP_KHOTT_SP_CHITIET c on b.khott_fk = c.KHOTT_FK and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and a.sanpham_fk = c.SANPHAM_FK  " + 
					"  		and ISNULL(	b.loaidoituong, 0 ) = 	ISNULL(	c.loaidoituong, 0 ) and ISNULL( b.DOITUONG_FK, 0 ) = ISNULL(	c.DOITUONGID, 0 ) " + 
					"  		and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO " + 
					"  		and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '') " + 
					"  		and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0) " + 
					"  		and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '') " + 
					"  		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '') and isnull(a.nsx_fk, 0) = isnull(c.nsx_fk, 0) " + 
					"  where b.PK_SEQ = '" + lsxId + "' and a.soluong > 0 ";
			
			System.out.println("::: CAP NHAT KHO1: " + query);
			ResultSet rs = db.get(query);
			 
				while( rs.next() )
				{
					String khoId = rs.getString("Khott_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					String ngaydieuchinh = rs.getString("NGAYDIEUCHINH");
					
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
					
					String nsx_fk = rs.getString("nsx_fk")==null?"":rs.getString("nsx_fk");

					double soluong = rs.getDouble("soluong");
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh cột số lượng tỏng kho
					if( soluong > avai )
					{
						msg = "Sản phẩm ( " + rs.getString("tensp") + " ) với số lượng điều chỉnh ( " + soluong + " ), không đủ tồn kho ( " + avai + " ) để đổi thông tin ";
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
					//Giảm kho chuyển
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
					//Tăng kho nhận
					String soloMoi = solo; 
					String ngayhethanMoi = ""; 
					String marquetteMoi = "";
					String maphieuMoi = "";
					String thungMoi = "";
					String meMoi = "";
					String phieudtMoi = "";
					String hamamMoi = "";
					String hamluongMoi = "";
					String ngaynhapkhoTANG = ngaydieuchinh;  //Không thay đổi ngaynhapkho bên này
					String nsxNew = "";
					
					query = "update ERP_DOISOLO_SANPHAM_CHITIET set ngaynhapkhoTANG = '" + ngaynhapkhoTANG + "' where pk_seq = '" + rs.getString("pk_seq") + "' ";
					if( db.updateReturnInt(query) != 1 )
					{
						msg = "Lỗi khi chốt: " + query;
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}

					//String ngaynhapkhoTANG = ngaydieuchinh;
					if( rs.getString("loaidieuchinh").equals("0") ) //Điều chỉnh LÔ
					{
						soloMoi = rs.getString("soloMoi");
						ngayhethanMoi = rs.getString("ngayhethanMoi");
					
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethanMoi, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("1") ) //Điều chỉnh marquette
					{
						marquetteMoi = rs.getString("marquetteMoi");
						
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marquetteMoi, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("2") ) //Điều chỉnh mã phiếu
					{
						maphieuMoi = rs.getString("maphieuMoi");
						
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieuMoi, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("3") ) //Điều chỉnh mã thùng
					{
						thungMoi = rs.getString("thungMoi");
						
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, thungMoi, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("4") ) //Điều chỉnh mã mẻ
					{
						meMoi = rs.getString("meMoi");
						
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
								meMoi, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("5") ) //Điều chỉnh mã phiếu định tính
					{
						phieudtMoi = rs.getString("phieudtMoi");
						
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
								mame, mathung, bin_fk, maphieu, phieudtMoi, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("6") ) //Điều chỉnh hàm ẩm / hàm lượng
					{
						hamamMoi = rs.getString("hamamMoi");
						hamluongMoi = rs.getString("hamluongMoi");
					
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluongMoi, hamamMoi, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("7") ) //Điều chỉnh hàm ẩm / hàm lượng
					{
						nsxNew = rs.getString("nsxNEW_fk");
						System.out.println("HRER: " + soluong);
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Chốt đổi số lô: " + lsxId, db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
								mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, +1 * soluong, 0, +1 * soluong, nsxNew);
					}
					
					if( msg.trim().length() > 0 )
					{
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
	
	private String MoChotChuyenKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DOISOLO set trangthai = '0', NGAYCHOT = '" + this.getDateTime() + "' where pk_seq = '" + lsxId + "' ";
			System.out.println("::: CHOT CK: " + query);
			
			if( !db.update(query) )
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//CẬP NHẬT KHO ==> giam kho nhan
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk, ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, a.SoLo, a.NgayHetHan, a.ngaynhapkho,   " + 
					"  	a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong, " + 
					"  			c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE,  " +
					"			b.loaidieuchinh, a.soloMoi, a.ngayhethanMoi, a.marquetteMoi, a.maphieuMoi	" +
					"  from ERP_DOISOLO_SANPHAM_CHITIET a inner join ERP_DOISOLO b on a.doisolo_FK = b.PK_SEQ  " + 
					"  	 inner join ERP_KHOTT_SP_CHITIET c on b.khott_fk = c.KHOTT_FK and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and a.sanpham_fk = c.SANPHAM_FK  " + 
					"  		and ISNULL(	b.loaidoituong, 0 ) = 	ISNULL(	c.loaidoituong, 0 ) and ISNULL( b.DOITUONG_FK, 0 ) = ISNULL(	c.DOITUONGID, 0 ) " + 
					"  		and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO " + 
					"  		and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '') " + 
					"  		and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0) " + 
					"  		and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '') " + 
					"  		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '') " + 
					"  where b.PK_SEQ = '" + lsxId + "' and a.soluong > 0 ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Khott_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					String ngaydieuchinh = rs.getString("NGAYDIEUCHINH");
					
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
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh cột số lượng tỏng kho
					/*if( soluong > avai )
					{
						msg = "Sản phẩm ( " + rs.getString("tensp") + " ) với số lượng điều chỉnh ( " + soluong + " ), không đủ tồn kho ( " + avai + " ) để đổi thông tin ";
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}*/

					//Tang kho chuyển
					msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Mo Chốt đổi số lô", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 1 * soluong, 0, 1 * soluong);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
					//Giam kho nhận
					String soloMoi = solo; 
					String ngayhethanMoi = ""; 
					String marquetteMoi = "";
					String maphieuMoi = "";
					ngaynhapkho = ngaydieuchinh;
					if( rs.getString("loaidieuchinh").equals("0") ) //Điều chỉnh LÔ
					{
						soloMoi = rs.getString("soloMoi");
						ngayhethanMoi = rs.getString("ngayhethanMoi");
					
						msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Mo Chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethanMoi, ngaynhapkho, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else if( rs.getString("loaidieuchinh").equals("0") ) //Điều chỉnh marquette
					{
						marquetteMoi = rs.getString("marquetteMoi");
						
						msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Mo Chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkho, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marquetteMoi, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else //Điều chỉnh mã phiếu
					{
						maphieuMoi = rs.getString("maphieuMoi");
						
						msg = util.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Mo Chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkho, 
										mame, mathung, bin_fk, maphieuMoi, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
						
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}
					
				}
				rs.close();
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
			
			String query = "update ERP_DOISOLO set trangthai = '2' where pk_seq = '" + lsxId + "'  and trangthai=0";
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

		IErpDoisoloList obj = new ErpDoisoloList();
		String isnhanhang = request.getParameter("isnhanHang");
		if(isnhanhang == null)
			isnhanhang = "0";
		obj.setIsnhanHang(isnhanhang);
		
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userId = util.antiSQLInspection(request.getParameter("userId")); 
		if(action.equals("Tao moi"))
		{
			IErpDoisolo lsxBean = new ErpDoisolo();
			lsxBean.setUserId(userId);
			lsxBean.createRs();

			session.setAttribute("ckBean", lsxBean);
			session.setAttribute("khochuyenIds", "");
			obj.DBclose();
			String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLoNew.jsp";
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
				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLo.jsp";

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

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLo.jsp";

				response.sendRedirect(nextJSP);
			}
			else
			{
				obj.setUserId(userId);
				String search = getSearchQuery(request, obj);		    	
				obj.init(search);				
				session.setAttribute("obj", obj);  	
				session.setAttribute("userId", userId);

				String nextJSP = "/TraphacoHYERP/pages/Erp/ErpDoiSoLo.jsp";

				response.sendRedirect(nextJSP);
			}
		}
	}
	
	private String getSearchQuery(HttpServletRequest request, IErpDoisoloList obj)
	{
		String	query = " SELECT A.PK_SEQ, A.TRANGTHAI, A.ngaydieuchinh, KHOTT.TEN as khochuyen, " + 
						" case a.loaidieuchinh when 0 then N'Số lô' when 1 then N'Marquette' when 2 then N'Phiếu kiểm nghiệm' when 3 then N'Mã thùng' when 4 then N'Mã mẻ' when 5 then N'Phiếu định tính' when 7 then N'Nhà sản xuất' else N'Hàm ẩm & hàm lượng' end as loaidieuchinh, " +
						" 	A.LYDODIEUCHINH as LYDO, NV.TEN AS NGUOITAO, A.NGAYSUA, A.NGAYTAO, NV2.TEN AS NGUOISUA    " +
						" FROM ERP_DOISOLO A   " +
						" INNER join ERP_KHOTT KHOTT on a.khott_fk = KHOTT.PK_SEQ   " +
						" LEFT join ERP_BIN BIN on a.bin_fk = BIN.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV ON A.NGUOITAO = NV.PK_SEQ   " +
						" INNER JOIN NHANVIEN NV2 ON A.NGUOISUA = NV2.PK_SEQ  " +
						" WHERE 1=1  " ;

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
			query += " and a.ngaydieuchinh >= '" + tungaySX + "'";
		
		if(denngaySX.length() > 0)
			query += " and a.ngaydieuchinh  <= '" + denngaySX + "'";
	
		if(trangthai.length() > 0)
			query += " and a.TrangThai = '" + trangthai + "'";
		
		if(sophieu.length() > 0){
			query += " and  cast( a.pk_seq as nvarchar(10))  like '%" + sophieu + "%'";
		}
		
		if(khochuyenid.length() > 0){
			query += " and  cast( a.khott_fk as nvarchar(10))  like '%" + khochuyenid + "%'";
		}
		
		if(khonhanid.length() > 0){
			query += " and  cast( a.khott_fk as nvarchar(10))  like '%" + khonhanid + "%'";
		}
		
		if(lydo.length() > 0){
			query += " and a.lydo like N'%" + lydo + "%'";
		}

		if(nguoitao.length() > 0){
			query += " and a.nguoitao = " +nguoitao+ " ";
		}
		if(nguoisua.length() > 0){
			query += " and a.nguoisua = " +nguoisua+ " ";
		}
		
		if(solo.length() > 0){
			query += "\n  and a.pk_seq in (select doisolo_fk  from ERP_DOISOLO_SANPHAM_CHITIET where solo like N'%" + solo +"%') ";
			
		}
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
		ErpDoisoloSvl dsl = new ErpDoisoloSvl();
		
		String lsxId = "100013";
		String msg = dsl.MoChotChuyenKho(lsxId);
		
		System.out.println("::: CHAY XONG -- KQ LA: " + msg);
		
	}
	
	
}
