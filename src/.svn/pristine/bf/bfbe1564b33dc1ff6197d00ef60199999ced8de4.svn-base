
package geso.traphaco.erp.util;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import B.LI;
import Z.DB;
import geso.traphaco.erp.util.CapnhatKT;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils_syn;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.hoadontravencc.imp.ErpHoaDon;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon;
import geso.traphaco.erp.beans.park.IErpPark;
import geso.traphaco.erp.beans.park.imp.ErpPark;
import geso.traphaco.erp.beans.phieuxuatkhoTH.imp.ErpPhieuxuatkho;
import geso.traphaco.erp.beans.tieuhao.IErpTieuHao;
import geso.traphaco.erp.beans.tieuhao.imp.ErpTieuHao;
import geso.traphaco.erp.db.sql.dbutils;
 
public class Library 
{ 
	NumberFormat formater = new DecimalFormat("#######");
	NumberFormat formater_3sole = new DecimalFormat("#######.###");
	
  	public static void main ( String args [  ]  )   {
  		dbutils db=new dbutils();
  
  		try{
  			
  			Library lib=new Library();
  			System.out.println("here ");
  			// lib.Capnhat_Tieuhao();
  		 
			 
		/*	String msg1= lib.capnhatketoan_Xuat_Tieuhao_Giacong("100002", db, "100695", false, "100000");
			System.out.println("msg1: "+msg1);	*/
  			
  		/*	System.out.println("HERE ARE YOU New ");
  			//TinhSoDuCuoiThangKT(7,2016,"","");
  			Library lib=new Library();
  			String msg1= lib.DuaSoDuDaukyKeToanKho("100000", "12","2016","100002");
  			System.out.println("Thông báo lỗi: "+msg1);
  		 */
  		
  			
  			
  			
  		/*	String  query=	"   SELECT A.PK_SEQ , A.CONGTY_FK ,A.NGUOITAO,A.TRANGTHAI   FROM ERP_NHANHANG A where A.TRANGTHAI=1 AND PK_SEQ= 105496";
	  		System.out.println("du lieu : "+query);
			ResultSet rs=db.get(query);
			while(rs.next()){
			
			
				String  msg1= lib.CapNhatKeToanKho_NhanhangMua_SPHH(rs.getString("NGUOITAO"), db, rs.getString("PK_SEQ"), false, rs.getString("CONGTY_FK"));
			
				System.out.println("msg1: "+msg1);
			}
			rs.close();
  			
  			
  			/*String query="select PK_SEQ,CONGTY_FK from ERP_XUATKHO WHERE TRANGTHAI=1 and pk_seq=100026";
  			ResultSet rs=db.get(query);
  			while(rs.next()){
  				String nkId=rs.getString("PK_SEQ");
	  			ErpPhieuxuatkho temp = new ErpPhieuxuatkho(nkId);
	  			String congty_fk=rs.getString("CONGTY_FK");
	  			
	    		temp.setCongtyId(congty_fk);
	    		boolean check = temp.Chot();
		    	if(check == false){
		    		System.out.println("Chot khong thanh cong");
		    	}
  			}
  			*/
  			/*String query="SELECT PK_SEQ,NGUOITAO FROM ERP_HOADON WHERE LOAIHOADON='6' AND TRANGTHAI=1";
  			ResultSet rs=db.get(query);
  			while(rs.next()){
  			
	  			ErpHoaDon obj = new ErpHoaDon();
	  		    String ddhId=rs.getString("PK_SEQ");
	  		    String userId=rs.getString("NGUOITAO");
  		    	obj.setId(ddhId);
  		    	obj.setUserId(userId);
  		    	
  		    	obj.setMessage(obj.ChotHoaDon());
  		     
  			}*/
  			
  			
	  		/*String query="  SELECT A.PK_SEQ , lsx.CONGTY_FK,A.NGUOITAO,A.TRANGTHAI FROM ERP_TIEUHAONGUYENLIEU A  "
	  				+ " inner join ERP_LENHSANXUAT_GIAY lsx on lsx.PK_SEQ=A.LENHSANXUAT_FK WHERE    A.TRANGTHAI =1 and A.PK_SEQ=100191  ";
	  		
	  		ResultSet rs=db.get(query);
	  		while(rs.next()){
	  			
	  			String msg1= lib.capnhatketoan_Xuat_Tieuhaolsx(rs.getString("NGUOITAO"), db, rs.getString("PK_SEQ"), false, rs.getString("CONGTY_FK"));
	  			System.out.println("msg1: "+msg1);
	  		}
	  		rs.close();*/
  			
  	  		/*String  query=" SELECT   CK.PK_SEQ ,CK.NGUOITAO,CK.TRANGTHAI ,kho.CONGTY_FK   "
  	  				+ "from ERP_DIEUCHINHTONKHOTT ck inner join ERP_KHOTT kho on kho.PK_SEQ=ck.khott_fk "
  	  				+ "  where ck.trangthai=1 AND ck.ngaydieuchinh>='2017-01-01'  ";
  	  		
  	  		System.out.println(query);
  	  		ResultSet rs=db.get(query);
  	  		while(rs.next()){
 	  			
 	  			String msg1= lib.capnhatketoan_Xuat_DieuChinhTonkho(rs.getString("NGUOITAO"), db, rs.getString("PK_SEQ"), false, rs.getString("CONGTY_FK"));
  	  			
   	  			System.out.println("msg1: "+msg1);
  	  			
  	  			String msg= lib.capnhatketoan_Nhap_DieuChinhTonkho(rs.getString("NGUOITAO"),db,rs.getString("PK_SEQ"),false,rs.getString("CONGTY_FK"));
  	  		 
  	  			System.out.println("msg2: "+msg);
  	  		}
  	  		rs.close();*/
  			
  			/* 
  			
  				String  query=" SELECT A.NGUOITAO,A.PK_SEQ,kho.CONGTY_FK FROM ERP_CHUYENKHO A inner join ERP_KHOTT kho on kho.PK_SEQ=a.KhoXuat_FK "
  					+ " WHERE  NOIDUNGXUAT_FK IN (100059,100060,100063,100061) and   a.TRANGTHAI IN (1,2,3) and A.NGAYCHUYEN >='2017-01-01'";
 
			System.out.println(query);
			ResultSet rs=db.get(query);
			while(rs.next()){
				
			 
				  String msg1= lib.CapNhatKeToanKho_Chuyenkho(rs.getString("NGUOITAO"), db, rs.getString("PK_SEQ"), false, rs.getString("CONGTY_FK"));
				
				System.out.println("msg1: "+msg1);
				if(msg1.length()>0){
					query="insert into ErrorMsg(pxkId)values("+rs.getString("PK_SEQ")+")";
					db.update(query);
				}
			}
			rs.close();*/
  			
  		/**/
  			
  			String  query=	"  SELECT DISTINCT  A.PK_SEQ , A.CONGTY_FK ,A.NGUOITAO,A.TRANGTHAI   "
  					+ " FROM ERP_PARK A  WHERE A.PK_SEQ=106809";
  			 
  			ResultSet rs=db.get(query);
  			while(rs.next()){
  				String id=rs.getString("PK_SEQ");
  			 
	  			IErpPark pBean = new ErpPark(id);
				pBean.setCtyId(rs.getString("CONGTY_FK"));
			 
				 String userId=rs.getString("NGUOITAO");
				pBean.setUserId(userId);
			  
				String msg1="";
				
	    		if(!pBean.Duyet())
	    		{
	    				msg1= pBean.getMsg();
	    		}
	    		System.out.println("msg1: "+msg1);
  			}
  			
  			//Capnhat_Tieuhao();
	  	 
	  		// createTieuHaoGiaCong(db,"103764","100002");
  			
  			//
  			
  		  		/*String query="  SELECT A.PK_SEQ , a.CONGTY_FK ,A.NGUOITAO,A.TRANGTHAI FROM ERP_TIEUHAO  A  "
				+ "   WHERE    A.TRANGTHAI =1  and A.NGAYTIEUHAO >='2017-01-01'  ";
		
		ResultSet rs=db.get(query);
		while(rs.next()){
			
			String msg1= lib.capnhatketoan_Xuat_Tieuhao_Giacong(rs.getString("NGUOITAO"), db, rs.getString("PK_SEQ"), false, rs.getString("CONGTY_FK"));
			System.out.println("msg1: "+msg1);
		}
		rs.close();
  			*/
  			/*db.getConnection().setAutoCommit(false);
  			
  			String query = "select A.PK_SEQ, 100000 as CONGTY_FK, A.NGUOITAO, A.TRANGTHAI from ERP_YCXUATKHO a where TRANGTHAI = 2 and pk_seq in (101753)";
	  		System.out.println("query = " + query);
	  		ResultSet rs = db.get(query);
	  		while(rs.next())
	  		{
	  			//Xuất kho bán hàng
	  			String msg1 = lib.CapNhatKeToanKho_XuatKho_Donhang_HO(rs.getString("NGUOITAO"), db, rs.getString("PK_SEQ"), false, rs.getString("CONGTY_FK"));
	  			System.out.println("msg1: " + msg1);
	  			if(msg1.trim().length() > 0)
	  			{
	  				db.getConnection().rollback();
	  				break;
	  			}
	  		}
	  		rs.close();	
	  		
	  		db.getConnection().commit();
	  		db.getConnection().setAutoCommit(true);
  			db.shutDown();*/
  			
  		}catch(Exception er){
  			er.printStackTrace();
  		}
  		
  		 
    }
	public boolean check_trangthaihople(String sochungtu ,String table_name,String trangthai,Idbutils db){
  		try{
  			boolean bien=false;
  			String query="select trangthai from "+table_name+" where trangthai in ("+trangthai+") and pk_seq="+sochungtu;
  			ResultSet rs=db.get(query);
  			
  			if(rs.next()){
  				bien=true;
  			}
  			rs.close();
  			return bien;
  			
  			
  		}catch(Exception er){
  			er.printStackTrace();
  			return false;
  		}
  	}
  	public String HuyDieuChinhTTKho(String lsxId) 
	{
		String msg = "";
		dbutils db = new dbutils();
		lsxId="101069";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_DOISOLO set trangthai = '2' where pk_seq = '" + lsxId + "' ";
			System.out.println("::: CHOT CK: " + query);
			
			if( !db.update(query) )
			{
				msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return msg;
			}
			
			//CẬP NHẬT KHO ==> mấy nữa cải tiến booked tính động, không lấy trong kho
			geso.traphaco.center.util.Utility utilkho = new geso.traphaco.center.util.Utility();
			query = "  select a.pk_seq, b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk, ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, a.SoLo, a.NgayHetHan, a.ngaynhapkho,   " + 
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
					"  		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '') " + 
					"  where b.PK_SEQ = '" + lsxId + "' and a.soluong > 0 ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
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

					double soluong = rs.getDouble("soluong");
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					/*//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh cột số lượng tỏng kho
					if( soluong > avai )
					{
						msg = "Sản phẩm ( " + rs.getString("tensp") + " ) với số lượng điều chỉnh ( " + soluong + " ), không đủ tồn kho ( " + avai + " ) để đổi thông tin ";
						db.getConnection().rollback();
						db.shutDown();
						return msg;
					}*/
					
					//Tang kho chuyển
					msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 1 * soluong, 0, 1 * soluong);
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
					String ngaynhapkhoTANG = ngaynhapkho;  //Không thay đổi ngaynhapkho bên này
					

					//String ngaynhapkhoTANG = ngaydieuchinh;
					if( rs.getString("loaidieuchinh").equals("0") ) //Điều chỉnh LÔ
					{
						soloMoi = rs.getString("soloMoi");
						ngayhethanMoi = rs.getString("ngayhethanMoi");
					
						msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethanMoi, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else if( rs.getString("loaidieuchinh").equals("1") ) //Điều chỉnh marquette
					{
						marquetteMoi = rs.getString("marquetteMoi");
						
						msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marquetteMoi, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else if( rs.getString("loaidieuchinh").equals("2") ) //Điều chỉnh mã phiếu
					{
						maphieuMoi = rs.getString("maphieuMoi");
						
						msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieuMoi, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else if( rs.getString("loaidieuchinh").equals("3") ) //Điều chỉnh mã thùng
					{
						thungMoi = rs.getString("thungMoi");
						
						msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, thungMoi, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else if( rs.getString("loaidieuchinh").equals("4") ) //Điều chỉnh mã mẻ
					{
						meMoi = rs.getString("meMoi");
						
						msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
								meMoi, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else if( rs.getString("loaidieuchinh").equals("5") ) //Điều chỉnh mã phiếu định tính
					{
						phieudtMoi = rs.getString("phieudtMoi");
						
						msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
								mame, mathung, bin_fk, maphieu, phieudtMoi, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
					}
					else if( rs.getString("loaidieuchinh").equals("6") ) //Điều chỉnh hàm ẩm / hàm lượng
					{
						hamamMoi = rs.getString("hamamMoi");
						hamluongMoi = rs.getString("hamluongMoi");
					
						msg = utilkho.Update_KhoTT(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluongMoi, hamamMoi, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong);
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
  	public String Capnhat_Ngaynhapkho_Nhanhang(String userId, Idbutils db, String nhId) {
		// TODO Auto-generated method st
		try
		{
 			String query =  " UPDATE NHSP SET NHSP.NGAYNHAPKHO= NH.NGAYNHAN \n"+
							" FROM ERP_NHANHANG_SP_CHITIET NHSP INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ= NHSP.NHANHANG_FK \n"+
							" WHERE NH.PK_SEQ="+nhId;
						if (!db.update(query))
						{
							return "Không thể cập nhật ngày nhập kho";
						}
			
			 query= " update nhsp set nhsp.ISKIEMDINH= (case  when isnull(sp.BATBUOCKIEMDINH ,'0') = '1' then '1' else '0' end ) "+ 
					" ,nhsp.KHO_FK= (case when isnull(sp.BATBUOCKIEMDINH ,'0') = '1' then nh.KHOCHOXULY_FK else  nh.khonhan_fk end )   "+ 
					" from  ERP_NHANHANG_SP_CHITIET nhsp  "+ 
					" inner join erp_nhanhang nh on nh.pk_seq=nhsp.nhanhang_fk "+ 
					" inner join ERP_SANPHAM sp  on sp.PK_SEQ= nhsp.SANPHAM_FK "+ 
					" where  nhsp.NHANHANG_FK= "+nhId;
			 if (!db.update(query))
			 {
				return "Không thể cập nhật kiểm định .Lỗi dòng lệnh: "+query;
				
			 }
			 
		}
		catch(Exception e)
		{
			e.printStackTrace();
			return "Lỗi :"+ e.getMessage();
		}
		return "";
	}
	private static String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
  	 private static void Capnhat_Tieuhao() {
		// TODO Auto-generated method stub
		try{
			dbutils db=new dbutils();
			String ngaytieuhao="2017-01-01";
			String userId="100002";
			String query= 	" SELECT A.KHO,A.NHID,NH.LENHSANXUAT_FK FROM "+ 
							" ( "+
							/*" SELECT  100051 AS KHO , 103460 AS NHID UNION   "+
							" SELECT  100060 AS KHO , 103281 AS NHID UNION   "+
							" SELECT  100061 AS KHO , 103283 AS NHID UNION   "+
							" SELECT  100062 AS KHO , 103148 AS NHID UNION   "+
							" SELECT  100063 AS KHO , 103330 AS NHID UNION "+  */
							" SELECT  100064 AS KHO , 103252 AS NHID    "+
							" ) A INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ= A.NHID  ";
			
			ResultSet rs=db.get(query);
			Library lib=new Library();
			
			while(rs.next()){
				String lsx=rs.getString("LENHSANXUAT_FK");
				String NHID=rs.getString("NHID");
				String KHO=rs.getString("KHO");
				
				
				 query=" INSERT INTO ERP_TIEUHAONGUYENLIEU (LENHSANXUAT_FK,TRANGTHAI,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,NGAYTIEUHAO,NGAYHETHONG) "+
							" VALUES ("+lsx+", 1,"+userId+",'"+getDateTime()+"',"+userId+",'"+ getDateTime()+"','"+ngaytieuhao+"',GETDATE())"; 
					  	//System.out.println("insert tieu hao "+query);
						if(!db.update(query))
						{
							 
						}
						query = "select IDENT_CURRENT('ERP_TIEUHAONGUYENLIEU') as clId";
						 ResultSet rs1 = db.get(query);
						String chungtuid = "";
						 
							if (rs1.next())
								chungtuid = rs1.getString("clId");
							rs1.close();
						 
						query="insert into ERP_LSXTIEUHAO_NHAPKHO (TIEUHAO_FK,NHAPKHO_FK) values ("+chungtuid+","+NHID+")";
						if(!db.update(query))
						{
							 
						}
						
						query="	SELECT KHO.PK_SEQ AS KHO_FK  	,SP.PK_SEQ AS SANPHAM_FK , sum( A.soluong) as soluong FROM tbl_tieuhao A "
								+ " 	LEFT JOIN ERP_SANPHAM SP ON SP.MA=A.MAHANG  "
								+ "	LEFT JOIN ERP_KHOTT KHO ON KHO.TEN=A.KHO 	where kho.PK_SEQ="+KHO  
								+" group by  KHO.PK_SEQ,SP.PK_SEQ ";
						
						ResultSet rssp=db.get(query);
						while(rssp.next()){
							String spid=rssp.getString("SANPHAM_FK");
							double soluong=rssp.getDouble("SOLUONG");
							
								query = " Insert ERP_LENHSANXUAT_TIEUHAO ( TIEUHAONGUYENLIEU_FK,KHOTT_FK, sanpham_fk, soluong, dongia, thanhtien ) " +
										" select  "+chungtuid+","+KHO+", " + spid + " ,"+soluong+", " + 0 + ", 0 ";
								 
								if(!db.update(query))
								{
									System.out.println("Khonog thanh cong"+query);
								}
								
							/*	query=" SELECT ISNULL(A.MAPHIEU,'') AS MAPHIEU , ISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL (A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH  ,      " +  
									  " A.HAMLUONG,A.HAMAM ,isnull(BIN.MA,'')  AS BIN ,ISNULL(CAST(BIN.PK_SEQ AS NVARCHAR(12)),'0')  AS BINID ,  A.NGAYBATDAU ,       " + 
									  " ISNULL(CAST(KV.PK_SEQ AS VARCHAR(12)),'')   AS KHUID, ISNULL(KV.TEN,'') AS KHUTEN, DV.DONVI,A.SANPHAM_FK,  " + 
									  " ISNULL(AVAILABLE, 0) AS AVAILABLE ,    " + 
									  " A.SOLO ,isnull(A.MATHUNG,'') as MATHUNG,ISNULL(A.MAME,'') AS MAME ,A.NGAYHETHAN ,A.NGAYNHAPKHO,ISNULL(A.MARQ,'') AS MARQ         " + 
									  " FROM ERP_KHOTT_SP_CHITIET A      " + 
									  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK      " + 
									  " LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK   LEFT JOIN ERP_KHUVUCKHO KV ON KV.PK_SEQ= A.KHUVUCKHO_FK        " + 
									  " LEFT JOIN ERP_BIN BIN ON BIN.PK_SEQ=A.BIN_FK         " + 
									  " where a.khott_fk =  "+KHO+"  and a.sanpham_fk = "+spid+"  and AVAILABLE > 0         " + 
									  " AND NGAYNHAPKHO<='2017-01-31' order by a.ngayhethan asc, a.AVAILABLE asc ";*/
								
								query=	"	SELECT '' as ngaynhapkho ,KHO.PK_SEQ AS KHO_FK 	,SP.PK_SEQ AS SANPHAM_FK ,  "
										+ " Kho,MAHANG,DONVI,SOLO,NGAYHETHAN,ME,THUNG,VITRI,MAPHIEU,PHIEUDINHTINH,PHIEUEO,Marquette ,HAMAM,HAMLUONG,SOLUONG "
										+ " FROM tbl_tieuhao A 	LEFT JOIN ERP_SANPHAM SP ON SP.MA=A.MAHANG  "
										+ "	LEFT JOIN ERP_KHOTT KHO ON KHO.TEN=A.KHO "
										+ " where kho.PK_SEQ="+KHO +" and sp.pk_seq="+spid;
								
								
								ResultSet rsct=db.get(query);
								
								double soluongct=soluong;
								
								while(rsct.next()  ){
									
									
									 double soluongcn=rsct.getDouble("SOLUONG");
										query = " INSERT ERP_LENHSANXUAT_TIEUHAO_CHITIET ( TIEUHAONGUYENLIEU_FK, KHOTT_FK, SANPHAM_FK, SOLO, NGAYHETHAN, SOLUONG "
												+ ",GIATHEOLO,DONGIA   ,MATHUNG,MAME,NGAYNHAPKHO,MARQ,HAMAM,HAMLUONG,MAPHIEU,PHIEUEO,MAPHIEUDINHTINH ) " +
												" values ( "+chungtuid+","+KHO+","+spid+", '" + rsct.getString("solo")+ "', '"+rsct.getString("ngayhethan")+"', " +soluongcn + " " +
												" , "+0+","+0+",'"+rsct.getString("thung")+"' "
												+ ",'"+rsct.getString("me")+"' " +
												",'"+rsct.getString("ngaynhapkho")+"','"+rsct.getString("Marquette")+"' "
												+ ", '"+rsct.getString("hamam")+"' "
												+ ",'"+rsct.getString("hamluong")+"','"+rsct.getString("maphieu")+"','"+rsct.getString("phieueo")+"' "
												+ ",'"+rsct.getString("PHIEUDINHTINH")+"')";
			 
										if(db.updateReturnInt(query)!=1)
										{
										  System.out.println("Khonog thanh cong"+query);
										}
								
								}
								
							 
								/*String msg1= lib.capnhatketoan_Xuat_Tieuhaolsx("100002", db,chungtuid, false, "100000");
					  			System.out.println("msg1: "+msg1);*/
					  			
					  			
						}
						 
						 	
			}
			
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}
 	
 	private static String createTieuHaoGiaCong(dbutils db, String nhId, String userId) {
 		// TODO Auto-generated method stub]
 		try{
 			String query="SELECT NPP_FK,PK_SEQ, ISNULL(ISGIACONG,'0') AS ISGIACONG ,congty_fk "
 					+ " FROM ERP_MUAHANG WHERE PK_SEQ= (SELECT MUAHANG_FK FROM ERP_NHANHANG WHERE PK_SEQ="+nhId+")";
 			//System.out.println("query:"+query);
 			
 			ResultSet rs=db.get(query);
 			String isgiacong="0";
 			String congtyid="";
 			String muahang_fk="";
 			String NPP_FK="";
 			if(rs.next()){
 				isgiacong=rs.getString("ISGIACONG");
 				congtyid=rs.getString("congty_fk");
 				muahang_fk=rs.getString("PK_SEQ");
 				NPP_FK=rs.getString("NPP_FK");
 			}
 			rs.close();
 			if(isgiacong.equals("1")){
 				
 				query=  " select  SANPHAM_FK,  sum(SOLUONG)   as SOLUONG     " +
 						" from ERP_NHANHANG_SP_CHITIET  " +
 						" where    NHANHANG_FK = '" + nhId + "' and SOLUONG> 0  " +
 						" group by SANPHAM_FK ";
 				System.out.println("query:"+query);
 				
 				ResultSet rssp=db.get(query);
 				
 				 while(rssp.next()){
 					 	String masanpham= rssp.getString("sanpham_fk");
 					 	double soluong=rssp.getDouble("SOLUONG");
 					 
 						
 						  query = 
 								" INSERT INTO ERP_TIEUHAO(CONGTY_FK, NHANHANG_FK, SANPHAM_FK, SOLUONG, TRANGTHAI, NGAYSUA, NGAYTAO, NGUOITAO, NGUOISUA,NGAYTIEUHAO ) " +
 								" SELECT '"+congtyid+"', '"+nhId+"', '"+masanpham+"', '"+soluong+"', '"+1+"', '2017-05-01', '2017-05-01', '"+userId+"', '"+userId+"','2017-01-01'";
 							
 							if(!db.update(query)) {
 								 
 							 
 							}
 							
 							query = "SELECT SCOPE_IDENTITY() AS ID";
 							  rs =  db.get(query);
 							rs.next();
 						String	 id = rs.getString("ID");
 							
 						
 						query="	SELECT KHO.PK_SEQ AS KHO_FK  	,SP.PK_SEQ AS SANPHAM_FK , sum( A.soluong) as soluong FROM tbl_tieuhao A "
								+ " 	LEFT JOIN ERP_SANPHAM SP ON SP.MA=A.MAHANG  "
								+ "	LEFT JOIN ERP_KHOTT KHO ON KHO.TEN=A.KHO 	where kho.PK_SEQ=100055 "  
								+" group by  KHO.PK_SEQ,SP.PK_SEQ ";
 						System.out.println("query:"+query);
 						ResultSet rsBom = db.get(query);
 							while(rsBom.next())
 							{
 								
 								String spid=rsBom.getString("SANPHAM_FK");
 								double soluongt=rsBom.getDouble("soluong");
 								
 								query = " INSERT ERP_TIEUHAO_VATTU(TIEUHAO_FK, VATTU_FK, SOLUONGCHUAN, SOLUONGTHUCTE) " +
 										" SELECT '" + id + "', '" + spid + "', " + soluongt+ ", " +soluongt+ " ";

 								if(!db.update(query)) {
 								 
 								}
 								
 							}
 							
 							query=	"	SELECT '' as ngaynhapkho ,KHO.PK_SEQ AS KHO_FK 	,SP.PK_SEQ AS SANPHAM_FK ,  "
									+ " Kho,MAHANG,DONVI,SOLO,NGAYHETHAN,ME,THUNG,VITRI,MAPHIEU,PHIEUDINHTINH,PHIEUEO,Marquette ,HAMAM,HAMLUONG,SOLUONG "
									+ " FROM tbl_tieuhao A 	LEFT JOIN ERP_SANPHAM SP ON SP.MA=A.MAHANG  "
									+ "	LEFT JOIN ERP_KHOTT KHO ON KHO.TEN=A.KHO "
									+ " where kho.PK_SEQ= 100055 ";
							
							
							ResultSet rsct=db.get(query);
							
							double soluongct=soluong;
							
							while(rsct.next()  ){
								
								 String spid=rsct.getString("SANPHAM_FK");
 								 
								 double soluongcn=rsct.getDouble("SOLUONG");
									query = " INSERT ERP_TIEUHAO_VATTU_CHITIET ( tieuhao_fk , KHOTT_FK, VATTU_FK, SOLO, NGAYHETHAN, SOLUONG "
											+ ",DONGIA   ,MATHUNG,MAME,NGAYNHAPKHO,MARQ,HAMAM,HAMLUONG,MAPHIEU,PHIEUEO,MAPHIEUDINHTINH ) " +
											" values ( "+id+","+100055+","+spid+", '" + rsct.getString("solo")+ "', '"+rsct.getString("ngayhethan")+"', " +soluongcn + " " +
											" , "+0+",'"+rsct.getString("thung")+"' "
											+ ",'"+rsct.getString("me")+"' " +
											",'"+rsct.getString("ngaynhapkho")+"','"+rsct.getString("Marquette")+"' "
											+ ", '"+rsct.getString("hamam")+"' "
											+ ",'"+rsct.getString("hamluong")+"','"+rsct.getString("maphieu")+"','"+rsct.getString("phieueo")+"' "
											+ ",'"+rsct.getString("PHIEUDINHTINH")+"')";
		 
									if(db.updateReturnInt(query)!=1)
									{
									  System.out.println("Khonog thanh cong"+query);
									}
							}
							
 							 
						
 					 }
 			
 				
 				
 				
 			}
 			
 		}catch(Exception err){
 			err.printStackTrace();
 			return err.getMessage();
 		}
 		return "";
 	}

 	
	public   String DuaSoDuDaukyKeToanKho(String congtyid,String thang,String nam,String userid ){
  		 try{
  			 dbutils db=new dbutils();
  			 
  		//	 String chuoitaikhoan="'15310000','15320000','15230000','15500000','15610000','15210000'";
  			 
  		/*	 String  query=	" select distinct SOHIEUTAIKHOAN "+
							" FROM ( "+
							" SELECT   (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK "+   
							" AND CONGTY_FK="+congtyid+" ) AS TAIKHOAN_FK,KHOTT_FK,SANPHAM_FK,sp.ma,sp.ten ,DV.DONVI ,SUM(SOLUONG) AS SOLUONG "+   
							" ,SUM(SOLUONG * ISNULL(DONGIAMUA,0) )  AS THANHTIEN  "+
							" FROM ERP_TONKHOTHANG_CHITIET A INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK "+ 
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ= SP.LOAISANPHAM_FK   "+
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK   "+
							" WHERE THANG="+thang+" AND NAM= "+nam+" "+
							" GROUP BY  KHOTT_FK,SANPHAM_FK ,LSP.TAIKHOANKT_FK ,sp.ma,sp.ten ,DV.DONVI "+
							" ) A "+
							" inner join ERP_TAIKHOANKT  tk on tk.PK_SEQ "+ 
							" =TAIKHOAN_FK "; 
						 
  			 ResultSet rs=db.get(query);
  			 System.out.println("query  : "+query);
  			 if(rs.next()){
  				 return  "Vui lòng cấu hình lại các tài khoản sẽ cập nhật kho ";
  				 
  			 }*/
  			 
  			 db.getConnection().setAutoCommit(false);
  			 
  			Utility util = new Utility();
  			
  			 String loaichungtu="Đăng ký số dư";
  			 
  			 String tungay  =nam+"-"+(thang.length()>1?thang:"0"+thang)+"-01";
			 String denngay ="";
		 	
			String  query="SELECT  CONVERT (char(10),DATEADD(DAY,-1,  DATEADD (MONTH, 1, '"+tungay+"')) ,126) AS NGAY ";
			 
			 ResultSet rsngay=db.get(query);
		     if(rsngay.next()){
				denngay =rsngay.getString("NGAY");
			 }
		     
		     query="select * from erp_buttoantonghop where congty_fk="+congtyid+" AND  DIENGIAI =N'"+loaichungtu+"'  and NGAYBUTTOAN= '"+denngay+"'";
		     ResultSet rscheck=db.get(query);
		     String Sochungtu ="";
		     if(rscheck.next()){
		    	 Sochungtu =rscheck.getString("PK_SEQ");
		    	 // revert lại kế toán
		    	 String msg= Revert_KeToan_loaichungtu(db,loaichungtu,Sochungtu);
				 if(msg.length()>0){
					 return msg;
				 }
				 query="DELETE ERP_BUTTOANTONGHOP_CHITIET WHERE BUTTOANTONGHOP_FK= "+Sochungtu;
				 if(!db.update(query)){
					 return "Không thể cập nhật dòng lệnh : "+ query; 
				 }
				 
		     }else{
		    	 query="insert into ERP_BUTTOANTONGHOP (NGAYBUTTOAN,DIENGIAI,NGAYTAO,NGUOITAO,NGAYSUA,NGUOISUA,TRANGTHAI  " +
		    	 		" ,CONGTY_FK,TIENTE_FK,TIGIA)values ('"+denngay+"',N'"+loaichungtu+"','"+getDate()+"',"+userid+",'"+getDate()+"',"+userid+",1,"+congtyid+",100000,1)";
		    	 if(!db.update(query)){
					 return "Không thể cập nhật dòng lệnh : "+ query; 
				 }
		    	 
	    	     query = "select SCOPE_IDENTITY() as dhId";
				 ResultSet rsDh = db.get(query);
				 
				 if(rsDh.next())
				 {
				 	Sochungtu= rsDh.getString("dhId");
				 }
				 rsDh.close();
					 
		     }
		     String taikhoanco_fk=getIdTaiKhoan(db, "41180000", congtyid);
		     
		     
		/*     query=	 " SELECT  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  "+
			    	 " AND CONGTY_FK= "+congtyid+" ) as  TAIKHOANKTNO,KHOTT_FK,SANPHAM_FK,sp.ma,sp.ten ,DV.DONVI ,SUM(SOLUONG) AS SOLUONG  " +
			    	 ",SUM(SOLUONG * ISNULL(DONGIAMUA,0) )  AS THANHTIEN "+ 
			    	 " FROM ERP_TONKHOTHANG_CHITIET A INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK "+
			    	 " INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ= SP.LOAISANPHAM_FK " +
			    	 "  LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK "+
			    	 " WHERE      THANG="+thang+" AND NAM= "+nam+
			    	 " GROUP BY  KHOTT_FK,SANPHAM_FK ,LSP.TAIKHOANKT_FK ,sp.ma,sp.ten ,DV.DONVI ";*/
		     
		     query=" SELECT TAIKHOANKTNO	,KHOTT_FK,	SANPHAM_FK	,ma	,ten,	DONVI	, 	SUM(THANHTIEN) AS THANHTIEN,SUM(SOLUONG) AS SOLUONG "+
		    	 " FROM "+
		    	 " ( "+
		    	 " SELECT  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK   "+
		    	 " AND CONGTY_FK= "+congtyid+" ) as  TAIKHOANKTNO,KHOTT_FK,SANPHAM_FK,sp.ma,sp.ten ,DV.DONVI,A.SOLO ,SUM(SOLUONG) AS SOLUONG "+ 
		    	 " ,    SUM(ROUND ( ( SOLUONG * ISNULL(DONGIAMUA,0) +0.000001)  ,0) )     AS THANHTIEN   "+
		    	 " FROM ERP_TONKHOTHANG_CHITIET A INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=A.SANPHAM_FK "+ 
		    	 " INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ= SP.LOAISANPHAM_FK "+
		    	 " LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=SP.DVDL_FK " +
		    	 " INNER JOIN ERP_KHOTT KHO ON KHO.PK_SEQ= A.KHOTT_FK  " + 
		    	 " WHERE     KHO.LOAI NOT IN (10,7) AND   THANG="+thang+" AND NAM= "+nam+
		    	 " GROUP BY  KHOTT_FK,SANPHAM_FK ,LSP.TAIKHOANKT_FK ,sp.ma,sp.ten ,DV.DONVI,A.SOLO "+
		    	 " ) A GROUP BY TAIKHOANKTNO	,KHOTT_FK,	SANPHAM_FK	,ma	,ten,	DONVI ";
		    	 
		     ResultSet rssp=db.get(query);
		     CapnhatKT KT;
		     	 
		     while(rssp.next()){
		    	 
		    	 String spid=rssp.getString("SANPHAM_FK");
		    	 String khoid=rssp.getString("KHOTT_FK");
		    	 double soluong=rssp.getDouble("SOLUONG");
		    	 double thanhtien = rssp.getDouble("THANHTIEN");
		    	 String taikhoanno_fk=rssp.getString("TAIKHOANKTNO");
		    	 
		    	 query=" insert into ERP_BUTTOANTONGHOP_CHITIET( BUTTOANTONGHOP_FK,TAIKHOANKT_FK,NO,CO,SANPHAM_FK,KHO_FK,STT) VALUES " +
		    	 		" ("+Sochungtu+","+taikhoanno_fk+","+thanhtien+",0,"+spid+","+khoid+",1)";
		    	 if(!db.update(query)){
		    		 db.getConnection().rollback();
					 return "Không thể cập nhật dòng lệnh : "+ query; 
				 }
		    	 
		    /*	 query=" insert into ERP_BUTTOANTONGHOP_CHITIET( BUTTOANTONGHOP_FK,TAIKHOANKT_FK,NO,CO,KHO_FK,SANPHAM_FK,STT) VALUES " +
	    	 		" ("+Sochungtu+","+taikhoanco_fk+",0,"+thanhtien+",NULL,NULL,2)";
		    	 if(!db.update(query)){
		    		 db.getConnection().rollback();
					 return "Không thể cập nhật dòng lệnh : "+ query; 
				 }*/
		    	 
		    	 	KT=new CapnhatKT();
					KT.setSochungtu(Sochungtu);
				 	KT.setNOIDUNGNHAPXUAT_FK(""); 
					
					KT.setSpId(spid);
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					 
					  
					KT.setNam(nam);
					KT.setThang(thang);
				
					
					String taikhoanktCo = taikhoanco_fk;
					String taikhoanktNo =taikhoanno_fk;
				 
					
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "Sản phẩm";
					String madoituong_no = spid;
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = 0;
					if(soluong>0){
						dongiaViet= thanhtien/soluong;
					}
					
					KT.setDONGIA(formater.format(dongiaViet));
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(khoid);
					KT.setMasp(rssp.getString("MA"));
					KT.setTensp(rssp.getString("TEN"));
					KT.setDonvi(rssp.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(denngay);
					KT.setNgaychungtu(denngay);
					KT.setNgayghinhan(denngay);
					
					KT.setKhoanmuc("Số dư đầu kỳ kho");
					System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						db.getConnection().rollback();
						return msg1;
						
					}
					// đưa vào bảng đầu kỳ 
 
		     }
		     // xoa cái phát sinh của tk :41180000
		     	query="delete  ERP_PHATSINHKETOAN  WHERE     TAIKHOAN_FK= "+taikhoanco_fk+"   AND LOAICHUNGTU=N'Đăng ký số dư' AND KHOANMUC=N'Số dư đầu kỳ kho'";
		     
		     if(!db.update(query)){
	    		 db.getConnection().rollback();
				 return "Không thể cập nhật dòng lệnh : "+ query; 
			 }
		     
		     
		     
  			 // SAU KHI ĐƯA VÀO PHÁT SINH THÌ CẬP NHẬT LẠI ĐẦU KỲ KẾ TOÁN CỦA CÁC TÀI KHOẢN KHO : TK KHO LÀ TÀI KHOẢN NÀO?
		     // xóa các tài khoản đầu kỳ của kho này :
		     
		    /* query=" delete erp_taikhoan_noco_khoaso  where thang="+thang+" and nam ="+nam+" " +
		     	   " and  TAIKHOANKT_FK in (select pk_Seq from erp_taikhoankt where sohieutaikhoan in ("+chuoitaikhoan+") and congty_fk= "+congtyid+") ";
		     
		     if(!db.update(query)){
	    		 db.getConnection().rollback();
				 return "Không thể cập nhật dòng lệnh : "+ query; 
			 }
		     String msg1=LibraryKS.Tinhcuoiky(db, "1", nam, thang);
		     if(msg1.length() > 0){
		    	 db.getConnection().rollback();
				 return "Lỗi : "+ msg1; 
		     }*/
		     db.getConnection().commit();
		     db.getConnection().setAutoCommit(true);
		     
  			 
  		 }catch(Exception er){
  			 er.printStackTrace();
  			 return er.getMessage();
  		 }
  		 return "";
  	 }
  	 
 	
  	 
  	 public String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
 	
 	
  	 public static String createMaPhieuTuDong(String sanphamId, String idKiemDinh, dbutils db){
  	   /*001-16 (năm)-TP/NL/BTP (loại sản phẩm)*/
  		 try{
		  	  // truy vấn thông tin loại sản phẩm
		  	  String query = "  select l.MA from ERP_SANPHAM sp left join ERP_LOAISANPHAM l " +
		  	        "  on sp.LOAISANPHAM_FK = l.PK_SEQ where sp.PK_SEQ ="+ sanphamId;
		  	  
		  	  ResultSet rs = db.get(query);
		  	  String loaiSanPham = "";
		  	  if(rs !=null){
		  	   if(rs.next()){
		  	    loaiSanPham = rs.getString("MA");
		  	   }
		  	   rs.close();
		  	  }
		  	  Date d = new Date();
		  	  SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		  	  String date = sdf.format(d);
		  	  String year = date.substring(2, 4);
		  	  
		  	  return idKiemDinh +"-"+year+ "-"+loaiSanPham;
	   } catch (Exception ex){
	  	   ex.printStackTrace();
	  	   return "";
  	  }
  	  
  	 }
  	 
  	public static String TinhSoDuCuoiThangKT(int thang,int nam, String nppid,String ctyid){
  		try{
  			System.out.println("TinhSoDuCuoiThangKT");
  			// viet chuong trih
  		}catch(Exception er){
  			er.printStackTrace();
  			return er.getMessage();
  		}
  		return "";
  	}
	public   String CapNhatKeToanKho_ERP_YCXUATKHO(String userId,Idbutils db,String id,boolean Ischaycuoiky,String ctyId) 
	{
		
		String msg="";
		String query = "";
		try 
		{
			dbutils_syn db_dms=new dbutils_syn();
			
			Utility util = new Utility();
			String loaichungtu="Xuất kho trả NCC";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT XK.NgayYeuCau AS NGAYCHOT  ,  XK.GHICHU AS DIENGIAI " +
					" FROM ERP_YCXUATKHO XK " +
					" LEFT JOIN NHAPHANPHOI KH ON KH.PK_SEQ=XK.NPP_FK " +
					" WHERE  XK.PK_SEQ= " +  id;

			ResultSet rs = db_dms.get(query);
			System.out.println("du lieu : "+query);
			String ngaychotnv = "";
			 
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="100057";//XK01
			
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				   
			}
			rs.close();
			 
			
			query=	" SELECT distinct  isnull( dh.LoaiDonHang,'') as loaidonhang  FROM ERP_YCXUATKHO_DDH a   " +
					" inner join ERP_HOADON hd on hd.PK_SEQ= a.hoadon_fk " +
					" inner join ERP_HOADON_DDH hd_dh on hd.PK_SEQ=hd_dh.HOADON_FK " +
					" inner join ERP_DONDATHANG dh on dh.PK_SEQ=hd_dh.DDH_FK  where ycxk_fk="+id;
			System.out.println("du lieu : "+query);
			ResultSet rsloai=db_dms.get(query);
			String loaidonhang="";
			
			if(rsloai.next()){
				loaidonhang=rsloai.getString("loaidonhang");
			}
			if(loaidonhang.equals("")){
				return "Không xác định được loại đơn hàng";
			}
			
			rsloai.close();
			String Isnoibo="0";
			if(loaidonhang.equals("5")){
				Isnoibo="1";
			}
			
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			
			
			//XK06-- TÀI KHOẢN ĐỐI ỨNG XUẤT TRẢ NCC

			 		
			query	=	" SELECT KM.KHOANMUCCHIPHI_FK , ISNULL(KM.codangky,'') AS codangky,ISNULL(KM.codieukien,'')  AS  codieukien \n" +
					    " , LTRIM(RTRIM(ISNULL(NKSP.SCHEME,''))) as scheme ,100057 AS  NOIDUNGNHAP   , ''  AS KHONHAP \n"+
						" , ISNULL(CAST(A.KHO_FK AS NVARCHAR(18)),'')  AS KHOXUAT  \n"+
						" , SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE \n"+
						" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+ctyId+" ) AS TAIKHOANCO_FK , \n"+
						" ( SELECT PK_SEQ FROM ERP_TAIKHOANKT TK   \n"+
						" INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN  \n"+   
						" WHERE    \n"+
						" CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT= (case when LTRIM(RTRIM(ISNULL(NKSP.SCHEME,''))) ='' then  'XK01' else 'XK02' end  )  \n"+
						" AND TK.CONGTY_FK="+ctyId+" AND CH.ISNOIBO ='"+Isnoibo+"' )  AS TAIKHOANNO_FK , \n"+ 
						" NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI \n"+ 
						" ,    NKSP.soluong   AS SOLUONG  \n"+
						" FROM ERP_YCXUATKHO A        \n"+
						" INNER JOIN ERP_YCXUATKHO_SANPHAM_CHITIET NKSP ON A.PK_SEQ=NKSP.ycxk_fk \n"+
						" INNER JOIN SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK   \n"+
						" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK \n"+ 
						" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK \n" +
						" LEFT JOIN CTKHUYENMAI KM ON KM.SCHEME=NKSP.SCHEME \n"+ 
						" AND LTRIM(RTRIM(ISNULL(NKSP.SCHEME,''))) =''   \n"+
						" WHERE A.PK_SEQ = " +id;
				
			System.out.println("du lieu : "+query);  
			rs =  db_dms.get(query);
			CapnhatKT KT;
			while (rs.next()){
			 	
			 	
				KT=new CapnhatKT();
				KT.setSochungtu(id);
			 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
				String spid = rs.getString("SANPHAM_FK");
				KT.setSpId(spid);
				 
				double soluong = rs.getDouble("SOLUONG");
				// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
				
				 
				KT.setSOLUONG(formater.format(soluong));
				
				double dongia=GetGia_ChayKT(spid,db,Ischaycuoiky,thangtruoc,namtruoc);
				
				KT.setDONGIA(formater.format(dongia));
				
				String thanhtien = formater.format(dongia*soluong);
				 
				String nam = ngaychotnv.substring(0, 4);
				String thang = ngaychotnv.substring(5, 7);
				KT.setNam(nam);
				KT.setThang(thang);
			
				
				String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
				String taikhoanktNo ="";
				
				
				 
				String scheme= rs.getString("scheme");
				if(scheme.length() >0){
					// kiểm tra ko có điều kiện hoặc không có đăng ký
					
					if(!rs.getString("codangky").equals("1") || !rs.getString("codieukien").equals("1")){
						  query=" SELECT TK.PK_SEQ AS TAIKHOANNO_FK FROM CTKHUYENMAI CT INNER JOIN ERP_NHOMCHIPHI " +
						  		" NCP ON NCP.PK_SEQ=KHOANMUCCHIPHI_FK  " +
						  		" INNER JOIN ERP_TAIKHOANKT  TK ON TK.SOHIEUTAIKHOAN=NCP.TAIKHOAN_FK  " +
						  		" WHERE TK.CONGTY_FK="+ctyId+" AND CT.SCHEME='"+scheme+"'";
						  ResultSet rstknocp=db_dms.get(query);
						  if(rstknocp.next()){
							  taikhoanktNo=rs.getString("TAIKHOANNO_FK"); 
						  }else{
							  return  "Chưa xác định khoản mục phí cho scheme không có điều kiện hoặc không có đăng ký";
						  }
						  
					}else{
						// lấy từ trên câu tổng cấu hình
						taikhoanktNo=rs.getString("TAIKHOANNO_FK"); 
					}
					
				}else{
					// 	lấy từ trên câu tổng cấu hình
					taikhoanktNo=rs.getString("TAIKHOANNO_FK"); 
				}
				
				KT.setTaikhoanCO_fk(taikhoanktCo);
				KT.setTaikhoanNO_fk(taikhoanktNo);
				
				String doituong_no = "";
				
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co ="";
				KT.setDOITUONG_CO(doituong_co);
				KT.setDOITUONG_NO(doituong_no);
				KT.setMADOITUONG_CO(madoituong_co);
				KT.setMADOITUONG_NO(madoituong_no);
				KT.setTIGIA_FKl("1");
				KT.setDONGIANT("0");
			 
				String tiente_fk = "100000";
				KT.setTIENTEGOC_FK(tiente_fk);
				
				double  dongiaViet = dongia;
				
				KT.setDONGIA(dongiaViet+"");
				  
				KT.setNO(thanhtien+"");
				KT.setCO(thanhtien+"");
				KT.setTONGGIATRI(thanhtien+"");
				
				KT.setChiPhiId("NULL");
				KT.setKhoNhanID(rs.getString("KHONHAP"));
				KT.setMasp(rs.getString("MA"));
				KT.setTensp(rs.getString("TEN"));
				KT.setDonvi(rs.getString("DONVI"));
				KT.setLoaichungtu(loaichungtu);
				
				KT.setNgaychotnv(ngaychotnv);
				KT.setNgaychungtu(ngaychotnv);
				KT.setNgayghinhan(ngaychotnv);
				
				KT.setKhoanmuc(diengiai_ketoan);
				System.out.println("THANHTIEN : "+thanhtien);
				String msg1=KT.CapNhatKeToan_Kho(util, db);
				if(msg1.length()> 0){
					return msg1;
				}
				
				/*query=" UPDATE ERP_YCXUATKHO_SANPHAM_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" WHERE   YCXK_FK="+id+" and SANPHAM_FK="+spid;
				if(!db.update(query)){
					return "Lỗi trong quá trình cập nhật giá kế toán";
				}*/
				
			}  
			rs.close();
							
			 
					
			 
		 
			return "";
		 }

		catch (Exception e)
		{ 	
			e.printStackTrace();
			 msg = e.getMessage(); 
			return msg;
		}
	}

	
	public   String Revert_KeToan_loaichungtu(Idbutils db,String loaichungtu,
			String sochungtu ) {

		try 
		{
			
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		String query =  "  delete ERP_PHATSINHKETOAN " +
					    "  where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "'  ";
	 
			 
			if(!db.update(query))
			{
				return "Không thể hủy ERP_PHATSINHKETOAN " + query;
				 
			}			
			return "";
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
			
		}
	 
	
		 
	
	}
	
	 

	public String CapNhatKeToanKho_Chuyenkho(String userId, Idbutils db, String id,
			boolean Ischaycuoiky,String ctyId) { 
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Chuyển kho";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT CK.GHICHU as DIENGIAI,CK.NGAYCHUYEN  AS NGAYCHOT ,CK.NOIDUNGXUAT_FK AS NOIDUNGXUAT "+ 
					" , ND.MA + ' - ' + ND.TEN AS NOIDUNG "+
					" FROM ERP_CHUYENKHO  CK "+
					" INNER JOIN ERP_NOIDUNGNHAP ND ON ND.PK_SEQ = CK.NOIDUNGXUAT_FK "+ 
					" WHERE CK.PK_SEQ=" +  id;
			System.out.println("du lieu  : "+query);
			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = rs.getString("NOIDUNGXUAT");
				  noidung=rs.getString("NOIDUNG");
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			 
			
			if(ndxId.equals("100059") || ndxId.equals("100060")|| ndxId.equals("100063") || ndxId.equals("100061") || ndxId.equals("100066") || ndxId.equals("100068") || ndxId.equals("100065")      ) {
				
			 
				
				query	=	" SELECT  a.muahang_fk ,  isnull(khonhan.loai,'') as loaikhonhan  ,  isnull(khoxuat.loai,'') as loaikhoxuat , a.lenhsanxuat_fk ,isnull(convert(varchar,NDN.cochiphi),'') as cochiphi,  ISNULL(A.loaidoituongNHAN,'') AS loaidoituongNHAN ,	A.DOITUONGNHAN_FK  " +
							" ,A.NOIDUNGXUAT_FK as NOIDUNGNHAP, ISNULL(CAST(A.KhoNhan_FK AS NVARCHAR(18)),'') AS KHONHAP "+
							" , ISNULL(CAST(A.KhoXuat_FK AS NVARCHAR(18)),'')  AS KHOXUAT "+
							" , SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+ctyId+" ) AS TAIKHOANCO_FK   , "+
							"  (   SELECT PK_SEQ FROM ERP_TAIKHOANKT TK   INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH " +
							"  ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN "+ 
							"  WHERE     CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT=NDN.MA  AND TK.CONGTY_FK="+ctyId+" )AS TAIKHOANCO_DOIUNG_FK   , "+
							" (	SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+
							" TK.SOHIEUTAIKHOAN= NCP.TAIKHOAN_FK  AND TK.CONGTY_FK="+ctyId+" ) AS TAIKHOANNO_FK,NCP.PK_SEQ AS NHOMCHIPHI_FK, "+
							" NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+ 
							" ,    NKSP.soluong   AS SOLUONG , "
							+ " (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  TK.SOHIEUTAIKHOAN= '15419000' AND TK.CONGTY_FK="+ctyId+"  ) as  taikhoan_15419000 , "
							+ " (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  SOHIEUTAIKHOAN='62110000'   AND TK.CONGTY_FK="+ctyId+"   )  AS TAIKHOANNO_SX_FK, "+
							" (  select tk.PK_SEQ  from ERP_NHAMAY nm  "
							+ " inner join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN=nm.TAIKHOANKT_FK    where LSX.CONGTY_FK= TK.CONGTY_FK  and nm.pk_seq = (SELECT NHAMAY_FK FROM ERP_CONGDOANSANXUAT_GIAY WHERE PK_SEQ=A.CONGDOAN_FK)  )  AS TAIKHOAN_15410000 "+
							" FROM ERP_CHUYENKHO A   "+     
							" INNER JOIN ERP_CHUYENKHO_SANPHAM_CHITIET NKSP ON A.PK_SEQ=NKSP.chuyenkho_fk "+
							" LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ=A.chiphi_fk "+ 
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK "+     
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+ 
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  " +
							" INNER JOIN ERP_NOIDUNGNHAP NDN ON NDN.PK_SEQ= A.NOIDUNGXUAT_FK "
							+ " left join erp_lenhsanxuat_giay lsx on lsx.pk_seq=a.lenhsanxuat_fk  "
							+ " left join erp_khott khonhan on khonhan.pk_seq=a.khonhan_fk     "+  
							" left join erp_khott khoxuat on khoxuat.pk_seq=a.khoxuat_fk     "+  
							" WHERE A.PK_SEQ = " +id; 
 
				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
					 	 
					
					 
						KT=new CapnhatKT();
						KT.setSochungtu(id);
					 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
						String spid = rs.getString("SANPHAM_FK");
						KT.setSpId(spid);
						 
						double soluong = rs.getDouble("SOLUONG");
						// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
						
						 
						KT.setSOLUONG(formater_3sole.format(soluong));
						
						double dongia=GetGia_ChayKT_Dauky(spid, db, Ischaycuoiky, thangtruoc, namtruoc,ngaychotnv);
						
						KT.setDONGIA(formater.format(dongia));
						
						String thanhtien = formater.format(dongia*soluong);
						 System.out.println("thanh tien : "+thanhtien);
						String nam = ngaychotnv.substring(0, 4);
						String thang = ngaychotnv.substring(5, 7);
						KT.setNam(nam);
						KT.setThang(thang);
						String taikhoanktNo = "";
						String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
						
						KT.setChiPhiId(rs.getString("NHOMCHIPHI_FK")==null?"":rs.getString("NHOMCHIPHI_FK"));
						if(rs.getString("cochiphi").equals("1")){
							taikhoanktNo = rs.getString("TAIKHOANNO_FK");
						  
						}else  {
							 
							taikhoanktNo=rs.getString("TAIKHOANCO_DOIUNG_FK");
							 
						}  
						 
						
						 
						KT.setTaikhoanCO_fk(taikhoanktCo);
						KT.setTaikhoanNO_fk(taikhoanktNo);
						
						String doituong_no = "";
						
						String madoituong_no = "";
						String doituong_co = "";
						String madoituong_co ="";
						
						if( ndxId.equals("100063")){
							// đối với ký gửi thì có thêm đối tượng
							doituong_no="Khách hàng";
							madoituong_no=rs.getString("DOITUONGNHAN_FK");
						}
						
						KT.setDOITUONG_CO(doituong_co);
						KT.setDOITUONG_NO(doituong_no);
						KT.setMADOITUONG_CO(madoituong_co);
						KT.setMADOITUONG_NO(madoituong_no);
						KT.setTIGIA_FKl("1");
						KT.setDONGIANT("0");
					 
						String tiente_fk = "100000";
						KT.setTIENTEGOC_FK(tiente_fk);
						
						double  dongiaViet = dongia;
						
						KT.setDONGIA(dongiaViet+"");
						  
						KT.setNO(thanhtien+"");
						KT.setCO(thanhtien+"");
						KT.setTONGGIATRI(thanhtien+"");
						
//						KT.setChiPhiId("NULL");
						KT.setKhoNhanID(rs.getString("KHONHAP"));
						KT.setMasp(rs.getString("MA"));
						KT.setTensp(rs.getString("TEN"));
						KT.setDonvi(rs.getString("DONVI"));
						KT.setLoaichungtu(loaichungtu);
						
						KT.setNgaychotnv(ngaychotnv);
						KT.setNgaychungtu(ngaychotnv);
						KT.setNgayghinhan(ngaychotnv);
						
						KT.setKhoanmuc("Chuyển kho");
						KT.setDiengiai(diengiai_ketoan);
						System.out.println("THANHTIEN : "+thanhtien);
						
						if(ndxId.equals("100066")){
							// nội dung chuyển của của sản xuất 
							
							
							if(rs.getString("lenhsanxuat_fk")==null){
								return "không xác định được lệnh sản xuất của nội dung chuyển kho này, vui lòng kiểm tra lại chứng từ";
							}
							
							if(rs.getString("loaikhonhan").equals("10")) { 
								// nếu kho nhận là kho nguyên liệu sản xuất
								
									 
									KT.setKhoanmuc("Chuyển kho nguyên liệu sản xuất");
									taikhoanktNo =rs.getString("TAIKHOAN_15410000");
									KT.setTaikhoanNO_fk(taikhoanktNo);
									 
									String msg1=KT.CapNhatKeToan_Kho(util, db);
									if(msg1.length()> 0){
										return msg1;
									}
							}
							if (rs.getString("loaikhoxuat").equals("10")){
								//nếu kho xuất là kho nguyên liệu sản xuất thì cho nó định khoản ngược lại
								
								KT.setKhoanmuc("Xuất trả kho sản xuất");
								taikhoanktCo =rs.getString("TAIKHOAN_15410000");
								taikhoanktNo = rs.getString("TAIKHOANCO_FK");
								KT.setTaikhoanNO_fk(taikhoanktNo);
								KT.setTaikhoanCO_fk(taikhoanktCo);
								 
								String msg1=KT.CapNhatKeToan_Kho(util, db);
								if(msg1.length()> 0){
									return msg1;
								}
							}
									
							
							
						} else if ( ndxId.equals("100068") || ndxId.equals("100065") ){
								// gia công 
							
							if(rs.getString("muahang_fk")==null){
								return "không xác định được đơn mua hàng gia công của nội dung chuyển kho này, vui lòng kiểm tra lại chứng từ";
							}
							
							if( ndxId.equals("100065")  ) { 
								// nếu kho nhận là kho nguyên liệu sản xuất
								
									KT.setKhoanmuc("Chuyển kho nguyên liệu gia công");
									taikhoanktNo =rs.getString("taikhoan_15419000");
									KT.setTaikhoanNO_fk(taikhoanktNo);
									 
									String msg1=KT.CapNhatKeToan_Kho(util, db);
									if(msg1.length()> 0){
										return msg1;
									}
							}else{
								
								// trả về gia công
									KT.setKhoanmuc("Xuất trả kho sản xuất từ gia công");
									taikhoanktNo =rs.getString("taikhoan_15419000");
									KT.setTaikhoanNO_fk(taikhoanktNo);
									 
									String msg1=KT.CapNhatKeToan_Kho(util, db);
									if(msg1.length()> 0){
										return msg1;
									}
							}
						}	
							
						 else{
							String msg1=KT.CapNhatKeToan_Kho(util, db);
							if(msg1.length()> 0){
								return msg1;
							}
						}
						
						
					 
						
						
						
						query="UPDATE ERP_CHUYENKHO_SANPHAM_CHITIET   SET GIACHAYKT="+dongia+" WHERE  SANPHAM_FK="+spid +" AND CHUYENKHO_FK ="+id;
						if(!db.update(query)){
							return "Không thể cập nhật giá chạy kế toán ,vui lòng kiểm tra lại";
						}
						
				} 
					 
				 
				rs.close();
							
					
			}
		 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 msg = e.getMessage(); 
			return msg;
		}
	}
	 
	public   String CapNhatKeToanKho_XuatKho(String userId,Idbutils db,String id,boolean Ischaycuoiky,String ctyId) 
	{
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Xuất kho trả hàng";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
		query = " SELECT   isnull(XK.NOIDUNGXUAT,0) as NOIDUNGXUAT, XK.NGAYXUAT AS NGAYCHOT, XK.HOADON_FK,  " +
				" XK.DONDATHANG_FK, XK.TRAHANGNCC_FK, ND.MA + ' - ' + ND.TEN AS NOIDUNG,  "+
				" CASE WHEN XK.DONDATHANG_FK IS NOT NULL THEN ND.TEN +': ' +KH.TEN ELSE  ND.TEN +': ' + NCC.TEN END AS DIENGIAI "+ 
				" FROM ERP_XUATKHO XK  " +
				"  INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ= XK.TRAHANGNCC_FK  "+
				" INNER JOIN ERP_NOIDUNGNHAP ND ON ND.PK_SEQ = XK.NOIDUNGXUAT "+  
				" LEFT JOIN ERP_KHACHHANG KH ON KH.PK_SEQ=XK.NPP_FK  "+
				" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=XK.NPP_FK "+  
				" WHERE xk.trangthai<>2 and XK.PK_SEQ= " +  id;

		ResultSet rs = db.get(query);
		String ngaychotnv = "";
		 
		String diengiai_ketoan="";
		String noidung="";
		String ndxId="";
		String DVKD_FK="";
	 	if(rs.next()){

			  diengiai_ketoan= rs.getString("DIENGIAI");
			  ngaychotnv = rs.getString("ngaychot");
			  ndxId = rs.getString("NOIDUNGXUAT");
			  noidung=rs.getString("NOIDUNG");
			 
		}
		rs.close();
		 
			 
			  
			
			//XK06-- TÀI KHOẢN ĐỐI ỨNG XUẤT TRẢ NCC
			//Quản lý mua hàng > Nghiệp vụ khác > Xuất kho trả hàng (về NCC)
			//Chốt	Phiếu xuất	Nợ <TK 331 bên dưới Mã NCC>/ Có <TK15X bên dưới loại SP>

			 		
		query	=	" SELECT   mh.NHACUNGCAP_FK , ( select DONGIAVIET  from ERP_MUAHANG_SP mhsp where mhsp.SANPHAM_FK= NKSP.SANPHAM_FK  " +
				" and A.TRAHANGNCC_FK= mhsp.MUAHANG_FK ) AS DONGIAVIET , ( select DONGIA   from ERP_MUAHANG_SP mhsp where mhsp.SANPHAM_FK= NKSP.SANPHAM_FK  " +
				" and A.TRAHANGNCC_FK= mhsp.MUAHANG_FK ) AS DONGIA ,A.NOIDUNGXUAT as NOIDUNGNHAP, ''  AS KHONHAP "+
				" , ISNULL(CAST(A.KHO_FK AS NVARCHAR(18)),'')  AS KHOXUAT "+
				" , SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+
				" TK.SOHIEUTAIKHOAN = LSP.TAIKHOANKT_FK AND TK.CONGTY_FK=A.CONGTY_FK  ) AS TAIKHOANCO_FK ," +
				" ( select TAIKHOAN_FK from erp_nhacungcap where pk_seq=  mh.NHACUNGCAP_FK  )  AS TAIKHOANNO_TRANCC_FK , "+
				" NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+ 
				" ,    NKSP.soluong   AS SOLUONG "+  
				" FROM ERP_XUATKHO A  " +
				"  INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ= a.TRAHANGNCC_FK   "+     
				" INNER JOIN ERP_XUATKHO_SP_CHITIET NKSP ON A.PK_SEQ=NKSP.XUATKHO_FK "+
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK  "+    
				" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+ 
				" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK "+  
				" WHERE A.PK_SEQ ="+id;
		
			System.out.println("lay cau lenh : "+query);
			
			rs =  db.get(query);
			CapnhatKT KT;
			while (rs.next()){
			 	
				KT=new CapnhatKT();
				KT.setSochungtu(id);
			 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
			  
				String spid = rs.getString("SANPHAM_FK");
				KT.setSpId(spid);
				 
				double soluong = rs.getDouble("SOLUONG");
				// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
				 
				KT.setSOLUONG(formater_3sole.format(soluong));;
				
				//double dongia=GetGia_ChayKT(spid,db,Ischaycuoiky,thangtruoc,namtruoc);
				double dongia = rs.getDouble("DONGIAVIET");
				
				KT.setDONGIA(formater.format(dongia));
				 
				String nam = ngaychotnv.substring(0, 4);
				String thang = ngaychotnv.substring(5, 7);
				KT.setNam(nam);
				KT.setThang(thang);
			
				
				String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
				String  taikhoanktNo=rs.getString("TAIKHOANNO_TRANCC_FK"); 
					//đối về hàng trả về lấy giá nhập bước 2
				  
				String thanhtien = formater.format(dongia*soluong);
				
				KT.setTaikhoanCO_fk(taikhoanktCo);
				KT.setTaikhoanNO_fk(taikhoanktNo);
				
				String doituong_no =  "Nhà cung cấp";
				String madoituong_no =rs.getString("NHACUNGCAP_FK");
				String doituong_co = "";
				String madoituong_co ="";
				
				KT.setDOITUONG_CO(doituong_co);
				KT.setDOITUONG_NO(doituong_no);
				KT.setMADOITUONG_CO(madoituong_co);
				KT.setMADOITUONG_NO(madoituong_no);
				KT.setTIGIA_FKl("1");
				KT.setDONGIANT("0");
				
				String tiente_fk = "100000";
				KT.setTIENTEGOC_FK(tiente_fk);
			 
				KT.setNO(thanhtien+"");
				KT.setCO(thanhtien+"");
				KT.setTONGGIATRI(thanhtien+"");
				
				KT.setChiPhiId("NULL");
				KT.setKhoNhanID(rs.getString("KHONHAP"));
				KT.setMasp(rs.getString("MA"));
				KT.setTensp(rs.getString("TEN"));
				KT.setDonvi(rs.getString("DONVI"));
				
				
				KT.setLoaichungtu(loaichungtu);
				
				KT.setNgaychotnv(ngaychotnv);
				KT.setNgaychungtu(ngaychotnv);
				KT.setNgayghinhan(ngaychotnv);
				
				KT.setKhoanmuc(diengiai_ketoan);
				System.out.println("THANHTIEN : "+thanhtien);
				String msg1=KT.CapNhatKeToan_Kho(util, db);
				if(msg1.length()> 0){
					return msg1;
				}
				
				 query=" UPDATE ERP_XUATKHO_SP_CHITIET SET   GIACHAYKT_3="+dongia+",GIACHAYKT= "  + dongia +" WHERE   XUATKHO_FK="+id+" and SANPHAM_FK="+spid;
				if(!db.update(query)){
					return "Lỗi trong quá trình cập nhật giá kế toán";
				}
			}  
			rs.close();
			
			return "";
		 }

		catch (Exception e)
		{ 	
			 e.printStackTrace();
			 msg = "Loiox : "+e.getMessage(); 
			return msg;
		}
	}
	
	
  	public String  updateNguyenGia(String Id, Idbutils db) {
		// TODO Auto-generated method stub
	 
			try {
				String query = " Select mh.PK_SEQ AS SOCHUNGTU, mh.ngaymua,sp.THANHTIENVIET AS NGUYENGIA,"
						+ " SCLON_FK as MASCLON,CPTRATRUOC_FK AS CPTRATRUOC,"
						+ " CASE WHEN sp.SCLON_FK IS not null then 1 "
						+ " when sp.CPTRATRUOC_FK IS not null then 2 "
						+ " else '' end as loai From erp_muahang_sp sp "
						+ " inner join ERP_MUAHANG mh on mh.PK_SEQ=sp.MUAHANG_FK  WHERE MH.PK_SEQ="
						+ Id;

				ResultSet rs = db.get(query);
				System.out.println("Cau query"+query);
				while (rs.next()) {
					String loai = rs.getString("loai");
					String ngaytang = rs.getString("ngaymua");
					String maSCLon_fk = rs.getString("MASCLON");
					String cpTraTruoc = rs.getString("CPTRATRUOC");
					double thanhtien = rs.getDouble("NGUYENGIA");
					String sochungtu = rs.getString("SOCHUNGTU");
					if (loai.equals("1")) {
					 String msg = Erp_MaSCLon.InsertDieuChinhMSCL(db, maSCLon_fk,
								ngaytang, Double.toString(thanhtien), sochungtu,
								"Đề nghị thanh toán", "ERP_MUAHANG");
						if (msg.length() > 0) {
							return msg;
						}
					} else if (loai.equals("2"))
					{
						query = " select * from erp_congcudungcu where pk_seq = "
								+ cpTraTruoc + " and trangthai = 0";
						ResultSet rsCP = db.get(query);
						if (rsCP.next()) 
						{
							System.out.println("da vao day");
							query = "Update ERP_CONGCUDUNGCU set trangthai=1"
									+ " where pk_seq = " + cpTraTruoc;
							int result = db.updateReturnInt(query);
							if (result <= 0)
							{
								return "Không thể cập nhập nguyên giá chi phí phân bổ";
								 
							}
						}
							query = "insert into ERP_CONGCUDUNGCU_DIEUCHINH(CCDC_FK,GIATRI,LOAICHUNGTU,BANGTHAMCHIEU,SOCHUNGTU,NGAYDIEUCHINH) values( "
									+ cpTraTruoc
									+ " ,"
									+ Double.toString(thanhtien)
									+ ",0, 'ERP_MUAHANG', "
									+ sochungtu + " ,'" + ngaytang + "') ";
							int result = db.updateReturnInt(query);
							if (result <= 0) {
								return "Không thể cập nhập nguyên giá chi phí phân bổ";
								  
							}
							System.out.println("aaaaa"+query);
						
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
				return  "Lỗi khi chốt. Vui lòng liên hệ admin để xử lý"+e.getMessage();
				 
			}
			return "";
		 

	}
  	

	private   double GetGia_ChayKT(String spid, Idbutils db,
			boolean ischaycuoiky,String thangtruoc,String namtruoc) {
		// TODO Auto-generated method stub
		double dongia = 0;
		 
		
		try{
			if(!ischaycuoiky){
				
				String query = " SELECT SANPHAM_FK, round(ISNULL(DONGIA, 0), 0) GIATON  from ERP_BANGGIA_TON_CUOIKY " +
								" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
								" AND NAM = '" + namtruoc + "'  ";
				ResultSet rsgia = db.get(query);
				if(rsgia.next()){
					dongia = rsgia.getDouble("GIATON");
				}
				rsgia.close();
				
			}else{
				
				String query = " SELECT SANPHAM_FK,  DONGIA_3  from ERP_BANGGIA_TON_CUOIKY " +
				" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
				" AND NAM = '" + namtruoc + "'  ";
				ResultSet rsgia = db.get(query);
				if(rsgia.next()){
					dongia = rsgia.getDouble("DONGIA_3");
				}
				rsgia.close();
			}
				 
		}catch(Exception er){
			
		}
		return dongia;
	}

	public  String[] getThangNam(Idbutils db, boolean Ischaycuoiky, String ngaychotnv) {
		String[] mang =new String[3];
		try{
			 
			if(Ischaycuoiky){
				// là tháng khóa sổ để lấy giá trong tháng
				 
				    	mang[0]= ngaychotnv.substring(5,7); 
				    	mang[1]=ngaychotnv.substring(0,4);
				    	mang[2]="";
					 
					 
			}else{
				
				 String query="select top 1 THANGKS,NAM from ERP_KHOASOTHANG order by NAM desc,THANGKS desc";
				 ResultSet rsks=db.get(query);
				 if(rsks.next()){
					 mang[0]=rsks.getString("THANGKS");
					 mang[1]=rsks.getString("NAM");
					 mang[2]="";
					 
					 
				 } else{
					 mang[2]="Không xác định được tháng khóa sổ gần nhất";
				 }
			}
			
			
		
		}catch(Exception er){
			mang[2]=er.getMessage();
			
		}
		return mang;
	
	}

	public   String CapNhatKeToanKho_NhanhangMua_SPHH(String userId, Idbutils db, String id,
			boolean Ischaycuoiky,String ctyId) {
		// nhận hàng hóa sản phẩm.
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhận hàng";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT CK.DIENGIAI  as DIENGIAI,CK.NGAYCHOT  AS NGAYCHOT ,CK.NOIDUNGNHAP_FK AS NOIDUNGXUAT "+ 
					" , ND.MA + ' - ' + ND.TEN AS NOIDUNG "+
					" FROM ERP_NHANHANG  CK "+
					" INNER JOIN ERP_NOIDUNGNHAP ND ON ND.PK_SEQ = CK.NOIDUNGNHAP_FK "+ 
					" WHERE CK.PK_SEQ=" +  id;

					ResultSet rs = db.get(query);
					String ngaychotnv = "";
					String diengiai_ketoan="";
					String noidung="";
					String ndxId="";
				 	if(rs.next()){
		
						  diengiai_ketoan= rs.getString("DIENGIAI");
						  ngaychotnv = rs.getString("ngaychot");
						  ndxId = rs.getString("NOIDUNGXUAT");
						  noidung=rs.getString("NOIDUNG");
					}
					rs.close();
		   
					query="SELECT NH.NOIDUNGNHAN_FK ,ISNULL(MH.ISGIACONG,'') AS  ISGIACONG, NH.PK_SEQ, MH.PK_SEQ MUAHANG_FK ,MH.NGUONGOCHH, NH.NGAYCHOT ,  " + 
						  "(SELECT DISTINCT C.PK_SEQ FROM ERP_SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.PK_SEQ  " + 
						  "INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOANKT_FK = C.SOHIEUTAIKHOAN  " + 
						  "WHERE A.PK_SEQ = NHSP.SANPHAM_FK AND C.CONGTY_FK = "+ctyId+") TAIKHOANNO_SANPHAM,  " + 
						  "(SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15120000' AND CONGTY_FK = "+ctyId+") TAIKHOAN_15120000,  " + 
						  "(SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15110000' AND CONGTY_FK = "+ctyId+") TAIKHOAN_15110000,  " + 
						  "(SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15419000' AND CONGTY_FK ="+ctyId+") TAIKHOAN_15419000,  " + 
//						  "NHSP.TYGIAQUYDOI TIGIA, " +
						  "isnull( HDNCC.TIGIA, 1) AS TIGIA, " +
						  "NHSP.TIENTE_FK, MH.LOAI , NHSP.DONGIA,   " + 
						  "NHSP.TAISAN_FK, NHSP.CCDC_FK, NHSP.CHIPHI_FK, NHSP.SANPHAM_FK, NH.NCC_KH_FK NCC_FK,  " + 
						  "NCC.TAIKHOAN_FK TAIKHOAN_NCC, NH.HDNCC_FK ,SP.MA,SP.TEN,DV.DONVI   " + 
						  ",NHCT.SOLO,NHCT.NGAYHETHAN , SUM(NHCT.SOLUONG) AS   SOLUONG  " + 
						  "FROM ERP_NHANHANG NH  " + 
						  "INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK   " + 
						  "INNER JOIN ERP_NHANHANG_SP_CHITIET NHCT ON NHSP.NHANHANG_FK=NHCT.NHANHANG_FK AND NHSP.SANPHAM_FK=NHCT.SANPHAM_FK  " + 
						  "AND NHSP.NGAYNHANDUKIEN= NHCT.NGAYNHANDUKIEN and NHSP.MUAHANG_FK= NHCT.MUAHANG_FK " + 
						  "INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=NHSP.SANPHAM_FK   " + 
						  "LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  " + 
						  "INNER JOIN ERP_MUAHANG MH ON  NH.MUAHANG_FK= MH.PK_SEQ   " + 
						  "INNER JOIN ERP_NHACUNGCAP NCC ON NH.NCC_KH_FK = NCC.PK_SEQ   " + 
						  "INNER JOIN ERP_HOADONNCC HDNCC ON NH.HDNCC_FK = HDNCC.PK_SEQ " + 
						  "WHERE 1 = 1 AND NH.PK_SEQ = " +id+ 
						  "GROUP BY NH.NOIDUNGNHAN_FK ,ISNULL(MH.ISGIACONG,'') , NH.PK_SEQ, MH.PK_SEQ   ,MH.NGUONGOCHH, NH.NGAYCHOT ,  " + 
						  "NHSP.TYGIAQUYDOI  , NHSP.TIENTE_FK, MH.LOAI , NHSP.DONGIA,   " + 
						  "NHSP.TAISAN_FK, NHSP.CCDC_FK, NHSP.CHIPHI_FK, NHSP.SANPHAM_FK, NH.NCC_KH_FK  ,  " + 
						  "NCC.TAIKHOAN_FK  , NH.HDNCC_FK ,SP.MA,SP.TEN,DV.DONVI  ,NHCT.SOLO,NHCT.NGAYHETHAN, HDNCC.TIGIA ";
 
			 	System.out.println(" query tai khoan dinh khoan : "+query);	
				rs =  db.get(query);
				CapnhatKT KT;
				
					while(rs.next())
					{				
						KT=new CapnhatKT();
					 
						String	loaimh = rs.getString("LOAI");
						String TAIKHOAN_NO = "";
						String TAIKHOAN_CO = "";
						String tiente_fk = rs.getString("tiente_fk");
						String hdNCC_fk = rs.getString("hdNCC_FK") == null?"":rs.getString("hdNCC_FK") ;
						
						double tygia = rs.getDouble("tigia");
						double sotienBVAT_VND = 0;
	        		    double sotienBVAT_NT = 0;
	        		    
	        		    double soluong  =  rs.getDouble("soluong");
	        		    double dongia = rs.getDouble("DONGIA");
	        		    
	        		    double dongia_VND = 0;
	        		    double dongia_NT = 0;
	        		    
	        		    String doituongno = "";
	        		    String madoituongno = "";
	        		    
	        		    String doituongco = "";
	        		    String madoituongco = "";
	        		    
	        		    String isgiacong= rs.getString("isgiacong") == null?"":rs.getString("isgiacong") ;
	        		    
	        		    String TAIKHOAN_15419000=rs.getString("TAIKHOAN_15419000") == null?"":rs.getString("TAIKHOAN_15419000") ;
	        		     
						String TAIKHOANNO_SANPHAM = rs.getString("TAIKHOANNO_SANPHAM") == null?"":rs.getString("TAIKHOANNO_SANPHAM") ;
						String TAIKHOAN_15120000 = rs.getString("TAIKHOAN_15120000") == null?"":rs.getString("TAIKHOAN_15120000") ;
						String TAIKHOAN_15110000 = rs.getString("TAIKHOAN_15110000") == null?"":rs.getString("TAIKHOAN_15110000") ;
						  
						String sanpham_fk =  rs.getString("SANPHAM_FK") == null ? "":rs.getString("sanpham_fk")  ;
	        			 
	        			
	        			String ncc_fk = rs.getString("ncc_fk") == null?"":rs.getString("ncc_fk") ;
						
	        			KT.setSochungtu(id);
	    			 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAN_FK"));
						KT.setSpId(sanpham_fk);
						KT.setSOLUONG(""+soluong );
						KT.setDONGIA(formater.format(dongia));
						String nam = ngaychotnv.substring(0, 4);
						String thang = ngaychotnv.substring(5, 7);
						
						KT.setNam(nam);
						KT.setThang(thang);
						 
	    				KT.setChiPhiId("NULL");
	    				KT.setKhoNhanID("NULL");
	    				KT.setMasp(rs.getString("MA"));
	    				KT.setTensp(rs.getString("TEN"));
	    				KT.setDonvi(rs.getString("DONVI"));
	    				String solo=rs.getString("SOLO");
	    				KT.setSolo(solo);
	    		
	    				KT.setLoaichungtu(loaichungtu);
	    				
	    				KT.setNgaychotnv(ngaychotnv);
	    				KT.setNgaychungtu(ngaychotnv);
	    				KT.setNgayghinhan(ngaychotnv);
	    				
	    				KT.setKhoanmuc(diengiai_ketoan);
	    			 

	        			
						if(tiente_fk.equals("100000")) // TIỀN VIỆT
	        			{
	        				tygia = 1;
	        				dongia_VND = dongia;
	        				dongia_NT  = 0;
	        				
	        				sotienBVAT_VND = soluong*dongia_VND;
	        				sotienBVAT_NT = soluong*dongia_VND;
	        				
	        			}
	        			else
	        			{
	        				dongia_VND = dongia*tygia;
	        				System.out.println("dongia_VND : "+dongia_VND);
	        				dongia_NT = dongia;
	        				
	        				sotienBVAT_VND = Math.round(soluong*dongia_VND);
	        				sotienBVAT_NT = soluong*dongia_NT;
	        				
	        			}
						 
						if(loaimh.equals("1")) // TRONG NUOC
						{	
							 
								KT.setTIGIA_FKl(""+tygia);
								KT.setDONGIANT(""+dongia_NT);
								KT.setTIENTEGOC_FK(tiente_fk);
								KT.setDONGIA(dongia_VND+"");
			    				  
			    				KT.setNO(formater.format(sotienBVAT_VND));
			    				KT.setCO(formater.format(sotienBVAT_VND));
			    				KT.setTONGGIATRI(formater.format(sotienBVAT_VND));
			    				KT.setTONGGIATRINT(formater.format(sotienBVAT_NT));
			    				 
							/*	doituongno = "Sản phẩm";
		                		madoituongno = sanpham_fk;
		                		
		                		doituongco = "Nhà cung cấp";
		                		madoituongco = ncc_fk;*/
		                		
		                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
		                		if(isgiacong.equals("1")){
		                			//nếu gia công thì lấy 15419000
		                			TAIKHOAN_CO=TAIKHOAN_15419000;
		                		}else{
		                			TAIKHOAN_CO = TAIKHOAN_15110000;
		                		}
		                		
		                		KT.setTaikhoanCO_fk(TAIKHOAN_CO);
								KT.setTaikhoanNO_fk(TAIKHOAN_NO);
								KT.setDOITUONG_CO(doituongco);
								KT.setDOITUONG_NO(doituongno);
								KT.setMADOITUONG_CO(madoituongco);
								KT.setMADOITUONG_NO(madoituongno);
								KT.setKhoanmuc("Nhận hàng - Tiền hàng");
								String msg1=KT.CapNhatKeToan_Kho(util, db);
								if(msg1.length()> 0){
									return msg1;
								}
 
						}
						else if(loaimh.equals("0")) // NHẬP KHẨU
						{
							 	
								double sotienBVAT_VND_TOKHAI = 0;
		                		double sotienBVAT_VND_HOADON = 0;
								// CHÊNH LÊCH TỈ GIÁ
		                		 query =  " SELECT distinct a.PK_SEQ , A.TIGIA " +
			            				" FROM ERP_THUENHAPKHAU A INNER JOIN ERP_THUENHAPKHAU_HOADONNCC B ON A.PK_SEQ = B.THUENHAPKHAU_FK \n"+
			            				" INNER JOIN ERP_HOADONNCC C ON B.HOADONNCC_FK = C.PK_SEQ \n"+
			            				" WHERE C.PARK_FK IN (SELECT PK_SEQ FROM ERP_PARK WHERE TRANGTHAI IN (1, 2) ) AND C.PK_SEQ = "+hdNCC_fk+" AND A.TRANGTHAI IN (1,2) ";
		                		
		                		ResultSet rschenhlech = db.get(query);
		                		
		                		int tokhai_fk = 0;
		                		
		                		double tygia_tokhai = 0;
		                		
		                	 
	                			while(rschenhlech.next())
	                			{
	                				tokhai_fk = rschenhlech.getInt("PK_SEQ");
	                				tygia_tokhai = rschenhlech.getInt("TIGIA");
	                			}
	                			rschenhlech.close();
		                		 
		                		if(tokhai_fk<=0)
		                		{
		                			rs.close();
									return "Vui lòng khai báo thuế nhập khẩu cho nhận hàng này! số chứng từ: "+id;
		                		}
		                	 	            
//	                			sotienBVAT_VND_TOKHAI = Math.round(soluong*dongia*tygia_tokhai);
	                			sotienBVAT_VND_TOKHAI = Math.round(soluong*dongia*tygia); // LẤY TỈ GIÁ BẰNG VỚI TỈ GIÁ TRONG HÓA ĐƠN NCC -> KO CÓ CHÊNH LỆCH
	            				sotienBVAT_VND_HOADON = Math.round(soluong*dongia*tygia);
	            						            	
	            				sotienBVAT_NT = Math.round(soluong*dongia);
	            				
	            				
	            				double chenhlech_VND =  Math.abs( sotienBVAT_VND_TOKHAI - sotienBVAT_VND_HOADON); 
	            					if(chenhlech_VND >0){
	            							
			            					KT.setTIGIA_FKl(""+tygia_tokhai);
											KT.setDONGIANT(""+dongia_NT);
											KT.setTIENTEGOC_FK(tiente_fk);
											KT.setDONGIA(dongia_VND+"");
						    				  
						    				KT.setNO(formater.format(chenhlech_VND));
						    				KT.setCO(formater.format(chenhlech_VND));
						    				KT.setTONGGIATRI(formater.format(chenhlech_VND));
						    				KT.setTONGGIATRINT(formater.format(chenhlech_VND/tygia_tokhai)); 
						    				
				                			if(tygia > tygia_tokhai)
				                			{
				                			/*	doituongno = "Sản phẩm";
						                		madoituongno = sanpham_fk;
						                		
						                		doituongco = "Sản phẩm";
						                		madoituongco = sanpham_fk;*/
						                		
						                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
						                		TAIKHOAN_CO = TAIKHOAN_15120000;
						                		
						            		    
				                			}		                			
				                			else if(tygia < tygia_tokhai)
				                			{
				                				/*doituongno = "Sản phẩm";
						                		madoituongno = sanpham_fk;
						                		
						                		doituongco = "Sản phẩm";
						                		madoituongco = sanpham_fk;*/
						                		
						                		TAIKHOAN_NO = TAIKHOAN_15120000 ;
						                		TAIKHOAN_CO = TAIKHOANNO_SANPHAM;
						                						            		    
				                			}
				                			KT.setTaikhoanCO_fk(TAIKHOAN_CO);
											KT.setTaikhoanNO_fk(TAIKHOAN_NO);
											KT.setDOITUONG_CO(doituongco);
											KT.setDOITUONG_NO(doituongno);
											KT.setMADOITUONG_CO(madoituongco);
											KT.setMADOITUONG_NO(madoituongno);
											KT.setKhoanmuc("Nhận hàng - Chênh lệch");
											
											String msg1=KT.CapNhatKeToan_Kho(util, db);
											if(msg1.length()> 0){
												return msg1;
											}
		            		    			 
			            		    }
			            		    
			            		     
			            		    	
			            		    	//KT.setTIGIA_FKl(""+tygia_tokhai);
			            		    	KT.setTIGIA_FKl(""+tygia); // LẤY TỈ GIÁ BẰNG VỚI TỈ GIÁ TRONG HÓA ĐƠN NCC
										KT.setDONGIANT(""+dongia_NT);
										KT.setTIENTEGOC_FK(tiente_fk);
										dongia_VND =dongia*tygia;
										
										KT.setDONGIA(formater.format(dongia_VND));
					    				  
					    				KT.setNO(formater.format(sotienBVAT_VND_TOKHAI));
					    				KT.setCO(formater.format(sotienBVAT_VND_TOKHAI));
					    				KT.setTONGGIATRI(formater.format(sotienBVAT_VND_TOKHAI));
					    				
					    				KT.setTONGGIATRINT(formater.format(dongia_NT*soluong)); 
			                		/*	doituongno = "Sản phẩm";
				                		madoituongno = sanpham_fk;
				                		
				                		doituongco = "Sản phẩm";
				                		madoituongco = sanpham_fk;*/
				                		
				                		TAIKHOAN_NO = TAIKHOANNO_SANPHAM;
				                		TAIKHOAN_CO = TAIKHOAN_15120000;
				                		KT.setTaikhoanCO_fk(TAIKHOAN_CO);
										KT.setTaikhoanNO_fk(TAIKHOAN_NO);
										KT.setDOITUONG_CO(doituongco);
										KT.setDOITUONG_NO(doituongno);
										KT.setMADOITUONG_CO(madoituongco);
										KT.setMADOITUONG_NO(madoituongno);
										KT.setKhoanmuc("Nhận hàng - Tiền hàng");
										String msg1=KT.CapNhatKeToan_Kho(util, db);
										
										if(msg1.length()> 0){
											return msg1;
										}
									 
			            	 
						}else{
							return "Vui lòng báo admin để được trợ giúp, loại nhận hàng này chưa được cấu hình .Chứng từ: "+id ;
						}
						
						  query="UPDATE ERP_NHANHANG_SP_CHITIET SET  GIACHAYKT="+KT.getDONGIA()+",GIATRIKT="+KT.getTONGGIATRI()+" where NHANHANG_FK="+id+" AND SANPHAM_FK="+sanpham_fk+" AND SOLO='"+solo+"' and SANPHAM_FK="+sanpham_fk;
						  if(!db.update(query)){
							  return  "Không thể cập nhật nhật giá trị kế toán :" +query;
						  }
					 
					}
					rs.close();
					
					
				 /*// kiểm tra số lượng khi  chạy có khớp không
					query=" SELECT * FROM "+
						" 	(   "+
						" 	SELECT SANPHAM_FK,SUM( NHSP.SOLUONGNHAN) AS SOLUONG "+
						" 	FROM ERP_NHANHANG NH  "+
						" 	INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK "+  
						" 	WHERE 1 = 1 AND NH.PK_SEQ = "+id+
						" 	GROUP BY SANPHAM_FK "+
						" 	) A  "+
						" 	LEFT JOIN "+ 
						" 	( "+
						" 	 "+
						" 	 SELECT A.SANPHAM_FK,SUM(A.SOLUONG) AS SOLUONG "+
						" 	FROM ERP_PHATSINHKETOAN A WHERE SOCHUNGTU= "+id+" AND LOAICHUNGTU=N'NHẬN HÀNG' "+ 
						" 	AND KHOANMUC=N'NHẬN HÀNG - TIỀN HÀNG' AND NO>0 "+
						" 	GROUP BY A.SANPHAM_FK  "+
						" 	)  B ON A.SANPHAM_FK=B.SANPHAM_FK "+
						" 	WHERE ISNULL(B.SOLUONG,0)<> ISNULL(A.SOLUONG,0)";
							
					ResultSet rscheck=db.get(query);
					if(rscheck.next())
					{
						return "Không thể cập nhật kế toán nhận hàng. Vui lòng báo Admin để xử lý . chứng từ: "+id +"  :  " + query;
					}
					rscheck.close();*/
		 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 msg = e.getMessage(); 
			return msg;
		}
	}
	 
	
	
	private String getIdTaiKhoan(Idbutils db, String SOHIEUTK,String ctyid) {
		// TODO Auto-generated method stub
		String TkId="";
		try{
			String query="SELECT PK_SEQ FROM  ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN='"+SOHIEUTK+"' and congty_fk="+ctyid;
			ResultSet rs=db.get(query);
			if(rs.next()){
				TkId=rs.getString("pk_seq");
			}
			rs.close();
			
		}catch(Exception er){
			er.printStackTrace();
			
		}
		return TkId;
	}
	// đã test xong kiểu mới
	public   String capnhatketoan_Nhap_DieuChinhTonkho(String userId,
		Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
	
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhập điều chỉnh tồn kho";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
				
				query = " SELECT kho.congty_fk , NGAYDIEUCHINH as ngaychot,LYDODIEUCHINH  as DIENGIAI FROM ERP_DIEUCHINHTONKHOTT a  "
						+ " inner join erp_khott kho on kho.pk_seq=a.khott_fk  WHERE a.PK_SEQ=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  Ctyid=rs.getString("congty_fk");
				  ndxId = "";
				  noidung="Nhập điều chỉnh tồn kho";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			
		    // 100052	NK07	Nhập điều chỉnh kiểm kê
			query	=		" SELECT   100052   AS NOIDUNGNHAP  ,  "+
							" DC.khott_fk  ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+ 
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANNO_FK   , "+ 
							" ( " +
							"  SELECT PK_SEQ FROM ERP_TAIKHOANKT TK " +
							"  INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN  " +
							"  WHERE   "+
							"  CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT='NK07'  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANCO_FK, "+ 
							"  DC.khott_fk AS KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+  
							" ,   NKSP.soluongDIEUCHINH   AS SOLUONG "+   
							" FROM ERP_DIEUCHINHTONKHOTT DC "+
							" INNER JOIN ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET NKSP  on NKSP.dctk_FK = DC.PK_SEQ "+      
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK "+      
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+  
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  "+ 
							" WHERE DC.PK_SEQ = "+id+" AND NKSP.soluongDIEUCHINH >0 "; 

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
				 	
				 	
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_Dauky(spid, db, false, thangtruoc, namtruoc, ngaychotnv);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
					
				
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						return msg1;
					}
					query=" UPDATE ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" " +
							" WHERE   SoLuongDieuChinh > 0 AND dctk_FK="+id+" and SANPHAM_FK="+spid;
					if(!db.update(query)){
						return "Lỗi trong quá trình cập nhật giá kế toán";
					}
					 
				} 
				 
				
				rs.close();
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	
	// đã test xong kiểu mới
	public   String capnhatketoan_Nhap_KH_Trave(String userId,
		Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
	
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhập kho hàng trả về";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT NgayNhapKho as ngaychot,ghichu  as DIENGIAI FROM ERP_DONTRAHANG  WHERE PK_SEQ=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Nhập kho hàng trả về";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			
		    // 100052	NK07	Nhập điều chỉnh kiểm kê
			query	=		" SELECT   100050   AS NOIDUNGNHAP  ,  "+
							" DC.khott_fk  ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  "+
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANNO_FK   , "+ 
							" (   "+
							" SELECT PK_SEQ FROM ERP_TAIKHOANKT TK   "+
							" INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN "+   
							" WHERE   "+
							" CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT='NK05'  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANCO_FK, "+ 
							" DC.khott_fk AS KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+  
							" ,   NKSP.soluong   AS SOLUONG "+   
							" FROM ERP_DONTRAHANG DC "+
							" INNER JOIN ERP_DONTRAHANG_SANPHAM NKSP  on NKSP.dontrahang_fk = DC.PK_SEQ "+      
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK       "+
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+  
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK "+  
							" WHERE DC.PK_SEQ ="+id; 

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
				 	
				 	
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_NhapB2(spid,db,Ischaycuoiky,thangtruoc,namtruoc);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
					
				
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						return msg1;
					}
					query=" UPDATE ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" " +
							" WHERE   SoLuongDieuChinh > 0 AND dctk_FK="+id+" and SANPHAM_FK="+spid;
					if(!db.update(query)){
						return "Lỗi trong quá trình cập nhật giá kế toán";
					}
					 
				} 
				 
				
				rs.close();
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	
	public   String capnhatketoan_Xuat_KiemKho(String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Xuất kiểm kho";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT NGAYKIEM as ngaychot, DIENGIAI FROM ERP_KIEMKHO  WHERE PK_SEQ=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Xuất kiểm kho";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			  
			query	=		" SELECT   100052   AS NOIDUNGNHAP  ,  "+
							" DC.khott_fk  ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+ 
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANNO_FK   , "+ 
							" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  "+
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_DOIUNG_FK  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANCO_FK, "+ 
							" DC.khott_fk AS KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+  
							" ,    NKSP.soluongDIEUCHINH   AS SOLUONG "+   
							" FROM  ERP_KIEMKHO DC "+
							" INNER JOIN ERP_KIEMKHO_SP_CHITIET NKSP  on NKSP.KIEMKHO_FK = DC.PK_SEQ "+      
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK "+      
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+  
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  "+ 
							" WHERE DC.PK_SEQ = "+id+" AND NKSP.soluongDIEUCHINH < 0 "; 

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
				 	
				 	
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_NhapB2(spid,db,Ischaycuoiky,thangtruoc,namtruoc);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktCo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktNo = rs.getString("TAIKHOANCO_FK");
					
				
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						return msg1;
					}
					query=" UPDATE ERP_KIEMKHO_SP_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" " +
							" WHERE   SoLuongDieuChinh < 0 AND kiemkho_fk ="+id+" and SANPHAM_FK="+spid;
					if(!db.update(query)){
						return "Lỗi trong quá trình cập nhật giá kế toán";
					}
					 
				} 
				 
				
				rs.close();
		 	 
				 
			 
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}

	public   String capnhatketoan_Xuat_Tieuhaolsx( String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Xuất tiêu hao lệnh sản xuất";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = "SELECT NGAYTIEUHAO AS ngaychot,'' AS DIENGIAI FROM ERP_TIEUHAONGUYENLIEU WHERE PK_SEQ= " +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="Tiêu hao nguyên liệu";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				 // diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="c";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			
			
			query	=		"  SELECT  SP.LOAISANPHAM_FK ,SP.DVKD_FK , '100074' as NOIDUNGNHAP, ''  AS KHONHAP  , ISNULL(CAST(NKSP.KHOTT_FK AS NVARCHAR(18)),'')  AS KHOXUAT "+ 
							" , SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  and LSX.CONGTY_FK= TK.CONGTY_FK   ) AS TAIKHOANCO_FK , "+   
							" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  SOHIEUTAIKHOAN='62110000' and LSX.CONGTY_FK= TK.CONGTY_FK   )  AS TAIKHOANNO_FK, "+
							" (  select tk.PK_SEQ  from ERP_NHAMAY nm  "
							+ " inner join ERP_TAIKHOANKT tk on tk.SOHIEUTAIKHOAN=nm.TAIKHOANKT_FK    where LSX.CONGTY_FK= TK.CONGTY_FK  and lsx.nhamay_fk=nm.pk_seq  )  AS TAIKHOAN_15410000 , "+
							" NKSP.SANPHAM_FK ,  SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI  ,    sum(NKSP.soluong)   AS SOLUONG  FROM ERP_TIEUHAONGUYENLIEU A     "+
							" INNER JOIN ERP_LENHSANXUAT_TIEUHAO_CHITIET NKSP ON A.PK_SEQ=NKSP.TIEUHAONGUYENLIEU_FK  "+
							" INNER JOIN ERP_LENHSANXUAT_GIAY LSX ON LSX.PK_SEQ= A.LENHSANXUAT_FK "+
							" INNER JOIN ERP_NHAMAY NM ON NM.pk_seq=LSX.NHAMAY_FK "+
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK "+ 
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+ 
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  WHERE A.PK_SEQ ="+id +" "
						  + " group by NKSP.SANPHAM_FK , SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI ,SP.LOAISANPHAM_FK ,SP.DVKD_FK ,NKSP.KHOTT_FK,LSP.TAIKHOANKT_FK,LSX.CONGTY_FK,LSX.NHAMAY_FK ";
			

			System.out.println(query);
			rs =  db.get(query);
			CapnhatKT KT;
			while (rs.next()){
			 	
			 	
				KT=new CapnhatKT();
				KT.setSochungtu(id);
			 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
				String spid = rs.getString("SANPHAM_FK");
				KT.setSpId(spid);
				 
				double soluong = rs.getDouble("SOLUONG");
				// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
				 
				KT.setSOLUONG(formater_3sole.format(soluong));;
				
				double dongia=GetGia_ChayKT_Dauky(spid, db, false, thangtruoc, namtruoc, ngaychotnv);
				
				KT.setDONGIA(formater.format(dongia));
				
				if(rs.getString("LOAISANPHAM_FK").equals("100013")){
					dongia=0;
				}
				
				String thanhtien = formater.format(dongia*soluong);
				 
				String nam = ngaychotnv.substring(0, 4);
				String thang = ngaychotnv.substring(5, 7);
				KT.setNam(nam);
				KT.setThang(thang);
				
				String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
				String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
				 
				KT.setTaikhoanCO_fk(taikhoanktCo);
				KT.setTaikhoanNO_fk(taikhoanktNo);
				
				String doituong_no = "";
				
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co ="";
				KT.setDOITUONG_CO(doituong_co);
				KT.setDOITUONG_NO(doituong_no);
				KT.setMADOITUONG_CO(madoituong_co);
				KT.setMADOITUONG_NO(madoituong_no);
				KT.setTIGIA_FKl("1");
				KT.setDONGIANT("0");
			 
				String tiente_fk = "100000";
				KT.setTIENTEGOC_FK(tiente_fk);
				 
			 
				  
				KT.setNO(thanhtien+"");
				KT.setCO(thanhtien+"");
				KT.setTONGGIATRI(thanhtien+"");
				
				KT.setChiPhiId("NULL");
				KT.setKhoNhanID(rs.getString("KHONHAP"));
				KT.setMasp(rs.getString("MA"));
				KT.setTensp(rs.getString("TEN"));
				KT.setDonvi(rs.getString("DONVI"));
				KT.setLoaichungtu(loaichungtu);
				
				KT.setNgaychotnv(ngaychotnv);
				KT.setNgaychungtu(ngaychotnv);
				KT.setNgayghinhan(ngaychotnv);
				
				KT.setKhoanmuc(diengiai_ketoan);
				 
				//System.out.println("THANHTIEN : "+thanhtien);
				String msg1=KT.CapNhatKeToan_Kho(util, db);
				//System.out.println("thông bao loi : "+msg1);
				if(msg1.length()> 0){
					return msg1;
				}
					// bút toán thứ 2
					// Có 632 : chi phí nguyên vật liệu
					// nợ 15410000 
				
				String TAIKHOAN_15410000 =rs.getString("TAIKHOAN_15410000");
				KT.setTaikhoanNO_fk(TAIKHOAN_15410000);
				KT.setTaikhoanCO_fk(taikhoanktNo);
				KT.setKhoanmuc("Kết chuyển chi phí NVL");
				 msg1=KT.CapNhatKeToan_Kho(util, db);
					//System.out.println("thông bao loi : "+msg1);
					if(msg1.length()> 0){
						return msg1;
					}
					
				
				query=" UPDATE ERP_LENHSANXUAT_TIEUHAO_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" WHERE   TIEUHAONGUYENLIEU_FK="+id+" and SANPHAM_FK="+spid;
				if(!db.update(query)){
					return "Lỗi trong quá trình cập nhật giá kế toán";
				}
				 
			} 
			 
			
			rs.close();
			   
		 
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	

	public   String capnhatketoan_Xuat_Tieuhao_Giacong( String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Xuất tiêu hao gia công";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = "SELECT NGAYTIEUHAO AS ngaychot,'' AS DIENGIAI FROM ERP_TIEUHAO WHERE PK_SEQ= " +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Xuất tiêu hao gia công";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			query	=		"  SELECT  '100074' as NOIDUNGNHAP, ''  AS KHONHAP  , ISNULL(CAST(NKSP.KHOTT_FK AS NVARCHAR(18)),'')  AS KHOXUAT   "+
							" , SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+Ctyid+" ) "+
							" AS TAIKHOANCO_FK ,     "+
							" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE  TK.SOHIEUTAIKHOAN= '15419000' AND TK.CONGTY_FK="+Ctyid+"  )  AS TAIKHOANNO_FK,  "+
							" NKSP.VATTU_FK as SANPHAM_FK  ,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI  ,   sum(NKSP.soluong)    AS SOLUONG  FROM ERP_TIEUHAO A "+     
							" INNER JOIN ERP_TIEUHAO_VATTU_CHITIET NKSP ON A.PK_SEQ=NKSP.TIEUHAO_FK "+  
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.VATTU_FK   "+
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+ 
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  WHERE A.PK_SEQ  ="+id +" "
									+ " group by NKSP.VATTU_FK  , SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI ,NKSP.KHOTT_FK,SP.LOAISANPHAM_FK,LSP.TAIKHOANKT_FK";
			

			System.out.println(query);
			rs =  db.get(query);
			CapnhatKT KT;
			while (rs.next()){
			 	
			 	
				KT=new CapnhatKT();
				KT.setSochungtu(id);
			 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
				String spid = rs.getString("SANPHAM_FK");
				KT.setSpId(spid);
				 
				double soluong = rs.getDouble("SOLUONG");
				// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
				 
				KT.setSOLUONG(formater_3sole.format(soluong));;
				
				double dongia=GetGia_ChayKT_Dauky(spid, db, false, thangtruoc, namtruoc, ngaychotnv);
				
				KT.setDONGIA(formater.format(dongia));
				
				String thanhtien = formater.format(dongia*soluong);
				 
				String nam = ngaychotnv.substring(0, 4);
				String thang = ngaychotnv.substring(5, 7);
				KT.setNam(nam);
				KT.setThang(thang);
			
				String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
				String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
				 
				KT.setTaikhoanCO_fk(taikhoanktCo);
				KT.setTaikhoanNO_fk(taikhoanktNo);
				
				String doituong_no = "";
				
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co ="";
				KT.setDOITUONG_CO(doituong_co);
				KT.setDOITUONG_NO(doituong_no);
				KT.setMADOITUONG_CO(madoituong_co);
				KT.setMADOITUONG_NO(madoituong_no);
				KT.setTIGIA_FKl("1");
				KT.setDONGIANT("0");
			 
				String tiente_fk = "100000";
				KT.setTIENTEGOC_FK(tiente_fk);
				
				double  dongiaViet = dongia;
				
				KT.setDONGIA(dongiaViet+"");
				  
				KT.setNO(thanhtien+"");
				KT.setCO(thanhtien+"");
				KT.setTONGGIATRI(thanhtien+"");
				
				KT.setChiPhiId("NULL");
				KT.setKhoNhanID(rs.getString("KHONHAP"));
				KT.setMasp(rs.getString("MA"));
				KT.setTensp(rs.getString("TEN"));
				KT.setDonvi(rs.getString("DONVI"));
				KT.setLoaichungtu(loaichungtu);
				
				KT.setNgaychotnv(ngaychotnv);
				KT.setNgaychungtu(ngaychotnv);
				KT.setNgayghinhan(ngaychotnv);
				
				KT.setKhoanmuc(diengiai_ketoan);
				//System.out.println("THANHTIEN : "+thanhtien);
				String msg1=KT.CapNhatKeToan_Kho(util, db);
				//System.out.println("thông bao loi : "+msg1);
				if(msg1.length()> 0){
					return msg1;
				}
				
				query=" UPDATE ERP_TIEUHAO_VATTU_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" WHERE   TIEUHAO_FK="+id+" and VATTU_FK="+spid;
				if(!db.update(query)){
					return "Lỗi trong quá trình cập nhật giá kế toán";
				}
				 
			} 
			 
			
			rs.close();
			   
		 
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	
	public   String capnhatketoan_Nhap_KiemKho(String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhập kiểm kho";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT NGAYKIEM as ngaychot, DIENGIAI FROM ERP_KIEMKHO  WHERE PK_SEQ=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Nhập  kiểm kho";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			  
			query	=		" SELECT   100052   AS NOIDUNGNHAP  ,  "+
							" DC.khott_fk  ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+ 
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANNO_FK   , "+ 
							" ( " +
							"  SELECT PK_SEQ FROM ERP_TAIKHOANKT TK " +
							"  INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN  " +
							"  WHERE   "+
							"  CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT='NK07'  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANCO_FK, "+ 
							" DC.khott_fk AS KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+  
							" ,    NKSP.soluongDIEUCHINH   AS SOLUONG "+   
							" FROM  ERP_KIEMKHO DC "+
							" INNER JOIN ERP_KIEMKHO_SP_CHITIET NKSP  on NKSP.KIEMKHO_FK = DC.PK_SEQ "+      
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK "+      
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+  
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  "+ 
							" WHERE DC.PK_SEQ = "+id+" AND NKSP.soluongDIEUCHINH >0 "; 

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
				 	
				 	
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_NhapB2(spid,db,Ischaycuoiky,thangtruoc,namtruoc);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
					
				
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						return msg1;
					}
					query=" UPDATE ERP_KIEMKHO_SP_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" " +
							" WHERE   SoLuongDieuChinh > 0 AND kiemkho_fk ="+id+" and SANPHAM_FK="+spid;
					if(!db.update(query)){
						return "Lỗi trong quá trình cập nhật giá kế toán";
					}
					 
				} 
				 
				
				rs.close();
		 	 
				 
			 
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	
	public   String capnhatketoan_Xuat_DieuChinhTonkho(String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Xuất điều chỉnh tồn kho";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT kho.congty_fk , NGAYDIEUCHINH as ngaychot,LYDODIEUCHINH  as DIENGIAI FROM ERP_DIEUCHINHTONKHOTT a  "
					+ " inner join erp_khott kho on kho.pk_seq=a.khott_fk  WHERE a.PK_SEQ=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  Ctyid=rs.getString("congty_fk");
				  
				  ndxId = "";
				  noidung="Xuất điều chỉnh tồn kho";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
		  
			query	=		" SELECT   100052   AS NOIDUNGNHAP  ,  "+
							" DC.khott_fk  ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+ 
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANNO_FK   , "+ 
							" ( " +
							"  SELECT PK_SEQ FROM ERP_TAIKHOANKT TK " +
							"  INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN  " +
							"  WHERE   "+
							"  CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT='XK13'  AND TK.CONGTY_FK="+Ctyid+" ) AS TAIKHOANCO_FK, "+ 
							
							" DC.khott_fk AS KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+  
							" ,   (-1)* NKSP.soluongDIEUCHINH   AS SOLUONG "+   
							" FROM ERP_DIEUCHINHTONKHOTT DC "+
							" INNER JOIN ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET NKSP  on NKSP.dctk_FK = DC.PK_SEQ "+      
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK "+      
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+  
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK  "+ 
							" WHERE DC.PK_SEQ = "+id+" AND NKSP.soluongDIEUCHINH < 0 "; 

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
				 	
				 	
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_Dauky(spid, db, false, thangtruoc, namtruoc, ngaychotnv);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktCo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktNo = rs.getString("TAIKHOANCO_FK");
					 
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					//System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					//System.out.println("thông bao loi : "+msg1);
					if(msg1.length()> 0){
						return msg1;
					}
					query=" UPDATE ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" " +
							" WHERE   SoLuongDieuChinh < 0 AND dctk_FK="+id+" and SANPHAM_FK="+spid;
					if(!db.update(query)){
						return "Lỗi trong quá trình cập nhật giá kế toán";
					}
					 
				} 
				 
				
				rs.close();
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}

	public   String capnhatketoan_Xuat_DoiSolo(String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Xuất đổi lô";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT ngaydoi as ngaychot,ghichu  as DIENGIAI FROM ERP_DOISOLO where pk_seq=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Xuất đổi lô";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
		  
		  
			query=	" SELECT   (SELECT PK_SEQ FROM ERP_NOIDUNGNHAP WHERE MA='XK14')   AS NOIDUNGNHAP  , "+   
					" DC.kho_fk  ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE   "+
					" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK= "+Ctyid+") AS TAIKHOANNO_FK   , "+  
					" (  "+
					" SELECT PK_SEQ FROM ERP_TAIKHOANKT TK  "+
					" INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN "+  
					" WHERE    "+
					" CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT='XK14'  AND TK.CONGTY_FK= "+Ctyid+" ) AS TAIKHOANCO_FK, "+  
					"  "+
					" DC.kho_fk AS KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+   
					" ,     NKSP.soluongdoi   AS SOLUONG "+    
					" FROM ERP_DOISOLO DC  "+
					" INNER JOIN ERP_DOISOLO_SANPHAM_CHITIET NKSP  on NKSP.DOISOLO_FK = DC.PK_SEQ "+       
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK        "+
					" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+   
					" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK "+   
					" WHERE DC.PK_SEQ ="+id+"  ";

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
				 	
				 	
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_NhapB2(spid,db,Ischaycuoiky,thangtruoc,namtruoc);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktCo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktNo = rs.getString("TAIKHOANCO_FK");
					 
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					//System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					//System.out.println("thông bao loi : "+msg1);
					if(msg1.length()> 0){
						return msg1;
					}
					query=	" UPDATE ERP_DOISOLO_SANPHAM_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" " +
							" WHERE    DOISOLO_FK="+id+" and SANPHAM_FK="+spid;
					if(!db.update(query)){
						return "Lỗi trong quá trình cập nhật giá kế toán";
					}
					 
				} 
				 
				
				rs.close();
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	
	
	public   String capnhatketoan_Nhap_DoiSolo(String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String Ctyid) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhập đổi lô";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT ngaydoi as ngaychot,ghichu  as DIENGIAI FROM ERP_DOISOLO where pk_seq=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Nhập đổi lô";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
		  
		  
			query=	" SELECT   (SELECT PK_SEQ FROM ERP_NOIDUNGNHAP WHERE MA='NK08')   AS NOIDUNGNHAP  , "+   
					" DC.kho_fk  ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE   "+
					" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK= "+Ctyid+") AS TAIKHOANNO_FK   , "+  
					" (  "+
					" SELECT PK_SEQ FROM ERP_TAIKHOANKT TK  "+
					" INNER JOIN ERP_CAUHINH_KETOAN_NDNHAPXUAT CH ON CH.SOHIEUTAIKHOAN_DOIUNG=TK.SOHIEUTAIKHOAN "+  
					" WHERE    "+
					" CH.LOAISANPHAM_FK=LSP.PK_SEQ AND CH.MANOIDUNGNHAPXUAT='NK08'  AND TK.CONGTY_FK= "+Ctyid+" ) AS TAIKHOANCO_FK, "+  
					"  "+
					" DC.kho_fk AS KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI "+   
					" ,     NKSP.soluongdoi   AS SOLUONG "+    
					" FROM ERP_DOISOLO DC  "+
					" INNER JOIN ERP_DOISOLO_SANPHAM_CHITIET NKSP  on NKSP.DOISOLO_FK = DC.PK_SEQ "+       
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK        "+
					" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+   
					" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK "+   
					" WHERE DC.PK_SEQ ="+id+"  ";

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){ 
					
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_NhapB2(spid,db,Ischaycuoiky,thangtruoc,namtruoc);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
					String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
					 
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					//System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					//System.out.println("thông bao loi : "+msg1);
					if(msg1.length()> 0){
						return msg1;
					}
					//NHẬP VÀ XUẤT CỦA ĐỔI LÔ LÀ CÙNG GIÁ
					
					query=	" UPDATE ERP_DOISOLO_SANPHAM_CHITIET SET "+(Ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongia +" " +
							" WHERE    DOISOLO_FK="+id+" and SANPHAM_FK="+spid;
					if(!db.update(query)){
						return "Lỗi trong quá trình cập nhật giá kế toán";
					}
					 
				} 
				 
				
				rs.close();
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}

	public   String capnhatketoan_Nhap_Kho_LSX(String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String ctyId) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhập kho sản xuất";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = "SELECT   isnull(XK.GHICHU,'')  as   DIENGIAI,XK.NGAYNHAPKHO AS NGAYCHOT FROM ERP_NHAPKHO  XK WHERE PK_SEQ =" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Nhập kho sản xuất";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			String Nhamayid="";
			
   
			query	=		" SELECT   ISNULL(CAST(A.NOIDUNGNHAP AS VARCHAR(18)),'')    AS NOIDUNGNHAP  , " +
							" A.KHONHAP ,LSX.NHAMAY_FK,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+
							" TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK="+ctyId+" ) AS TAIKHOANNO_FK   , "+
							" (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE "+
							" TK.SOHIEUTAIKHOAN= NM.TAIKHOANKT_FK  AND TK.CONGTY_FK="+ctyId+" ) AS TAIKHOANCO_FK, "+
							" A.KHONHAP  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI,NKSP.DVDL_FK "+ 
							" ,    NKSP.SOLUONGNHAP   AS SOLUONG "+  
							" FROM ERP_NHAPKHO A        "+
							" inner join ERP_LENHSANXUAT_GIAY lsx on lsx.PK_SEQ=A.SOLENHSANXUAT "+ 
							" INNER JOIN ERP_NHAMAY NM ON NM.pk_seq=lsx.NHAMAY_FK "+
							" INNER JOIN ERP_NHAPKHO_SANPHAM NKSP ON A.PK_SEQ=NKSP.SONHAPKHO_FK "+ 
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK      "+
							" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+ 
							" LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK "+  
							" WHERE A.PK_SEQ ="+id;
			 
			//System.out.println(query);
			rs =  db.get(query);
			CapnhatKT KT;
			while (rs.next()){
				 	
					Nhamayid=rs.getString("NHAMAY_FK");
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					
					query="SELECT SOLUONG1,SOLUONG2 FROM QUYCACH WHERE SANPHAM_FK= "+spid+" AND DVDL2_FK="+rs.getString("DVDL_FK");
					ResultSet rsqc =db.get(query);
					int bien=0;
					if(rsqc.next()){
						if(rsqc.getDouble("SOLUONG2")>0){
							soluong=soluong * rsqc.getDouble("soluong1")/ rsqc.getDouble("SOLUONG2");
						}else{
							return "Vui lòng kiểm tra quy đổi của sản phẩm :"+rs.getString("MA")+"Số lượng quy đổi bằng 0";
						}
						bien++;
					}
					rsqc.close();
					
					if(bien> 1){
						return "Vui lòng kiểm tra quy đổi của sản phẩm :"+rs.getString("MA")+" Đang có 2 quy đổi cùng 1 đơn vị";
					}
					
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_GiaThanhCuoiky(spid,db,Ischaycuoiky,thangtruoc,namtruoc,Nhamayid);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
	  
					String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
					
				
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO( thanhtien );
					KT.setCO( thanhtien );
					KT.setTONGGIATRI( thanhtien );
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setSolo(rs.getString("SOLO"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
			 
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						return msg1;
					}
					
					query="UPDATE ERP_NHAPKHO_SANPHAM   SET GIACHAYKT_3="+dongia+" WHERE  SANPHAM_FK="+spid +" AND SONHAPKHO_FK ="+id;
					if(!db.update(query)){
						return "Không thể cập nhật giá chạy kế toán ,vui lòng kiểm tra lại";
					}
					
			}  
			rs.close();
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}

	private   double GetGia_GiaThanhCuoiky(String spid, Idbutils db,
			boolean ischaycuoiky, String thang, String nam,String nhamayId) {
		// TODO Auto-generated method stub
		 
						
			double dongia = 0;
			 
			
			try{
				if(!ischaycuoiky){
						String query = " SELECT SANPHAM_FK, round(ISNULL(GIATON, 0), 0) GIATON  from ERP_TONKHOTHANG " +
										" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thang + "' " +
										" AND NAM = '" + nam + "'  ";
							
						ResultSet rsgia = db.get(query);
						if(rsgia.next()){
							dongia = rsgia.getDouble("GIATON");
						}
						rsgia.close();
				}else{
					 
						 
						String  query="SELECT DONGIA FROM ERP_GIATHANH_THANHPHAM WHERE   NHAMAY_FK="+nhamayId+" and  THANG="+thang+" And NAM="+nam+" And SANPHAM_FK= "+spid;
						System.out.println(query);
						ResultSet rsgia = db.get(query);
						if(rsgia.next()){
							dongia = rsgia.getDouble("DONGIA");
						}
						rsgia.close();
				}
					 
			}catch(Exception er){
				
			}
			return dongia;
	}

	public   String capnhatketoan_NhanhangTrave(String userId, Idbutils db,
			String id, boolean ischaycuoiky,String congtyId) {
		// TODO Auto-generated method stub
		 try{
			 	String loaichungtu="Nhận hàng trả về";
				String msg="";
				Utility util = new Utility();
				
				
				//NoiDungNhap_fk
				String query=  "  SELECT   ISNULL(NDN.TEN,'')+ ':'+ ISNULL(A.DIENGIAI,'') AS DIENGIAI   , " +
				 			   "  NDN.PK_SEQ AS NDNID,  A.NGAYNHAN AS NGAYCHUNGTU, A.NGAYCHOT, C.LOAINHACUNGCAP_FK, KHONL_NHO_GC, B.NHACUNGCAP_FK , D.KHACHHANG_FK,  " +  
							   "  ISNULL(B.NGUONGOCHH, 'TN') AS NGUONGOC		 " +  
							   "  FROM ERP_NHANHANG A   " +  
							   "  LEFT JOIN ERP_NOIDUNGNHAP NDN ON NDN.PK_SEQ=A.NoiDungNhap_fk  "+
							   "  LEFT JOIN  " +  
							   "  (  " +  
							   "  	SELECT DISTINCT TNK.PK_SEQ,HD_MH.MUAHANG_FK FROM ERP_THUENHAPKHAU TNK  	 " +  
							   "  	INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNKHD ON TNK.PK_SEQ=TNKHD.THUENHAPKHAU_FK   " +  
							   "  	INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_MH ON TNKHD.HOADONNCC_FK=HD_MH.HOADONNCC_FK   " +  
							   "  ) " +  
							   "  TNK_MH ON TNK_MH.PK_SEQ=A.SOTOKHAI_FK  " +  
							   "  LEFT JOIN ERP_MUAHANG B ON ISNULL(A.MUAHANG_FK,TNK_MH.MUAHANG_FK) = B.PK_SEQ   " +  
							   "  LEFT JOIN ERP_NHACUNGCAP C ON B.NHACUNGCAP_FK = C.PK_SEQ  " +  
							   "  LEFT JOIN DONTRAHANG D ON A.TRAHANG_FK = D.PK_SEQ " +  
							   "  WHERE A.PK_SEQ ="+id;
			 
				String ngaychotNV = "";
				String ngaychungtu = "";
			 
				String nccId = "";
				String khId = "";
				String nguongocHH = "";
				String diengiai_ketoan="";
				String noidungnhap="";
				ResultSet rs = db.get(query);
				 
				if(rs.next())
				{
					ngaychotNV = rs.getString("ngaychot");
					ngaychungtu = rs.getString("ngaychungtu");
					 
					nccId = rs.getString("nhacungcap_fk") == null ? "" : rs.getString("nhacungcap_fk");
					khId = rs.getString("khachhang_fk") == null ? "" : rs.getString("khachhang_fk");
					nguongocHH = rs.getString("NguonGoc");
					diengiai_ketoan=rs.getString("DIENGIAI");
					noidungnhap=rs.getString("NDNID");
					
				}
				rs.close();
				
				String nam = ngaychotNV.substring(0, 4);
				String thang = ngaychotNV.substring(5, 7);
				String[] thangnam = getThangNam(db,ischaycuoiky,ngaychotNV);
				String thangtruoc =  thangnam[0];
				String namtruoc =  thangnam[1];
				
				
				String taikhoanktCo = "";
				String taikhoanktNo = "";
				
				String doituong_co = "";
				String madoituong_co = "";
				String doituong_no = "";
				String madoituong_no = "";
				double tygia = 1;
			
			  query=" SELECT   SP.LOAISANPHAM_FK, LSP.TAIKHOANKT_FK  AS TAIKHOANNO, "+    
					" NKSP.SANPHAM_FK , NKSP.SOLUONGNHAN  AS SOLUONG, DONGIA, DONGIAVIET ,NKSP.TIENTE_FK"+   
					" FROM ERP_NHANHANG A      "+
					" INNER JOIN ERP_NHANHANG_SANPHAM NKSP ON A.PK_SEQ=NKSP.NHANHANG_FK "+  
					" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK     "+
					" INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK "+    
					" WHERE A.PK_SEQ ="+id;
			rs=db.get(query);
			
			String queryTK = "";
			while(rs.next()){
				
				String loaisanpham=rs.getString("LOAISANPHAM_FK");
				String masanpham=rs.getString("SANPHAM_FK");
				
				double dongia = rs.getDouble("DONGIA");
				double dongiaViet = rs.getDouble("DONGIAVIET");
				String tiente_fk = rs.getString("TienTe_FK");
				float soluong =  rs.getFloat("SOLUONG");
				
				if(noidungnhap.equals("100000"))
				{
					if(nguongocHH.equals("TN"))
					{
						queryTK = 	" select a.TaiKhoanKt_fk as TAIKHOANKTNO, b.TaiKhoan_fk as TAIKHOANKTCO " +
									" from erp_loaisanpham a, erp_nhacungcap b  " +
									" where a.pk_seq = '" + loaisanpham + "' and b.pk_seq = '" + nccId + "' ";
						
						doituong_no = "Sản phẩm";
						madoituong_no = masanpham;
						doituong_co = "Nhà cung cấp";
						madoituong_co = nccId;
						
					}
					else
					{
						queryTK =   "select a.pk_seq as TAIKHOANKTNO, a.TAIKHOANKT_FK  as TAIKHOANKTCO  "+
									"from erp_loaisanpham a, ERP_TAIKHOANKT b  " +
									"where a.pk_seq = '" + loaisanpham + "' and b.SOHIEUTAIKHOAN = '15100000'";
						
						doituong_no = "Sản phẩm";
						madoituong_no = masanpham;
						doituong_co = "Nhà cung cấp";
						madoituong_co = nccId;
							
						
					}
				}
				else if(noidungnhap.equals("100004"))
				{
					
					//100004	NK03	Nhập trả hàng từ KH / NPP
					queryTK =   "select a.pk_seq as TAIKHOANKTNO, a.TAIKHOANKT_FK  as TAIKHOANKTCO  "+
								"from erp_loaisanpham a, ERP_TAIKHOANKT b  " +
								"where a.pk_seq = '" + loaisanpham + "' and b.SOHIEUTAIKHOAN = '63211000'";
							
							doituong_no = "Sản phẩm";
							madoituong_no = masanpham;
							doituong_co = "Khách hàng";
							madoituong_co = khId;
					//hàng trả về lấy giá tháng trước,NẾU GIÁ NHẬP TÍNH BƯỚC 2 THÌ LẤY GIÁ BƯỚC 2
							
							dongiaViet=GetGia_ChayKT_NhapB2(masanpham, db, ischaycuoiky, thangtruoc, namtruoc);
					
			    }
					
				 
				
						//System.out.println("__QUERY TAI KHOAN: " + queryTK );
				
						if(queryTK.trim().length() > 0)
						{
								ResultSet tkRs = db.get(queryTK);
						 
								if(tkRs.next())
								{
									taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
									taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
									tkRs.close();
								}
								if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
								{
									msg = "222.Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rs.close();
									tkRs.close();
									return msg;
								}
								 
								//System.out.println("Đối tượng nợ: " + doituong_no + " (Mã: " + madoituong_no + "), đối tượng có: " + doituong_co + " (Mã: " + madoituong_co + ") ");
								double thanhtien = dongiaViet * soluong;
								
									
								/*msg = util.Update_TaiKhoan_Diengiai( db, thang, nam, ngaychungtu, ngaychotNV, loaichungtu, id, taikhoanktNo, taikhoanktCo, noidungnhap, 
															Double.toString(thanhtien), Double.toString(thanhtien), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", Float.toString(soluong), Double.toString(dongiaViet), tiente_fk, Double.toString(dongia), Double.toString(tygia), dongiaViet + "*" + soluong, dongia + "*" + soluong, "" ,diengiai_ketoan);
								if(msg.trim().length() > 0)
								{
									rs.close();
									tkRs.close();
									return msg;
								}*/
							 
						}else{
							return  "Chưa xác định cấu hình định khoản cho nội dung xuất này";
						}
						
						query="UPDATE ERP_NHANHANG_SANPHAM   SET "+(ischaycuoiky?" GIACHAYKT_3=":" GIACHAYKT= ") + dongiaViet +" WHERE  SANPHAM_FK="+masanpham +" AND NHANHANG_FK ="+id;
						System.out.println(query);
						if(!db.update(query)){
							return "Không thể cập nhật giá chạy kế toán ,vui lòng kiểm tra lại";
						}
						
						
				}
			
		 }catch(Exception er){
			 er.printStackTrace();
			 return er.getMessage();
		 }
		 return "";
	}
	private   double GetGia_ChayKT_Dauky(String spid, Idbutils db,
			boolean ischaycuoiky, String thangtruoc, String namtruoc,String ngaychungtu) {
		// TODO Auto-generated method stub
		double dongia = 0;
		 
		
		try{
			if(!ischaycuoiky){
					String query = " SELECT SANPHAM_FK, round(ISNULL(DONGIA, 0), 0) GIATON  from ERP_BANGGIA_THANHPHAM_CUOIKY    " +
									" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
									" AND NAM = '" + namtruoc + "'  ";
						System.out.println(query);
					ResultSet rsgia = db.get(query);
					int bien=0;
					if(rsgia.next())
					{
						dongia = rsgia.getDouble("GIATON");
						bien++;
					}
					rsgia.close();
					
					if(bien==0)
					{
						String query3 =	"SELECT TOP 1 DONGIA * isnull(MH.TyGiaQuyDoi,1) AS DONGIA , NH.NGAYNHAN " +
										"FROM ERP_NHANHANG NH " +
										"LEFT JOIN  ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "
										+ " INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ= NH.MUAHANG_FK  " +
										"WHERE     nh.trangthai=1  and NHSP.SANPHAM_FK ="+ spid+" and NH.NGAYNHAN <= '"+ngaychungtu+"'  " +
										"ORDER BY NH.NGAYNHAN DESC " ;
						ResultSet rs3 = db.get(query3);
						System.out.println("query 3: " + query3);
						 
						if(rs3.next())
						{
							dongia=rs3.getDouble("DONGIA");
							rs3.close();
						}
						
						 
					}
			}else{
					String query = " SELECT SANPHAM_FK,  DONGIA_2  from ERP_BANGGIA_TON_CUOIKY " +
					" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
					" AND NAM = '" + namtruoc + "'  ";
							ResultSet rsgia = db.get(query);
							if(rsgia.next()){
								dongia = rsgia.getDouble("DONGIA_2");
							}
							rsgia.close();
			}
				 
		}catch(Exception er){
			er.printStackTrace();
		}
		return dongia;
	}

	
	private   double GetGia_ChayKT_NhapB2(String spid, Idbutils db,
			boolean ischaycuoiky, String thangtruoc, String namtruoc) {
		// TODO Auto-generated method stub
		double dongia = 0;
		 
		
		try{
			if(!ischaycuoiky){
					String query = " SELECT SANPHAM_FK, round(ISNULL(DONGIA, 0), 0) GIATON  from ERP_BANGGIA_THANHPHAM_CUOIKY    " +
									" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
									" AND NAM = '" + namtruoc + "'  ";
						
					ResultSet rsgia = db.get(query);
					int bien=0;
					if(rsgia.next())
					{
						dongia = rsgia.getDouble("DONGIA");
						bien++;
					}
					rsgia.close();
					 
			}else{
					String query = " SELECT SANPHAM_FK,  DONGIA_2  from ERP_BANGGIA_THANHPHAM_CUOIKY " +
					" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
					" AND NAM = '" + namtruoc + "'  ";
					ResultSet rsgia = db.get(query);
					if(rsgia.next()){
						dongia = rsgia.getDouble("DONGIA_2");
					}
					rsgia.close();
			}
				 
		}catch(Exception er){
			
		}
		return dongia;
	}

	 
	public   String CapNhatKeToanKho_NhanhangSanXuat(String userId,
			Idbutils db, String id, boolean Ischaycuoiky,String ctyId) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhập kho sản xuất";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = "SELECT   isnull(XK.GHICHU,'')  as   DIENGIAI,XK.NGAYNHAN AS NGAYCHOT FROM ERP_NHANHANG  XK WHERE PK_SEQ =" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Nhập kho sản xuất";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			String Nhamayid="";
			
   
			query	=		  " SELECT   ISNULL(CAST(A.NoiDungNhap_fk AS VARCHAR(18)),'')    AS NOIDUNGNHAP  ,    " + 
							  " A.KHONHAN_FK as KHONHAP ,LSX.NHAMAY_FK,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE   " + 
							  " TK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK=100000) AS TAIKHOANNO_FK   ,   " + 
							  " (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE   " + 
							  " TK.SOHIEUTAIKHOAN= NM.TAIKHOANKT_FK  AND TK.CONGTY_FK=100000) AS TAIKHOANCO_FK,   " + 
							  " A.KHONHAN_FK  , NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI,SP.DVDL_FK    " + 
							  " ,    NKSP.SOLUONG   AS SOLUONG     " + 
							  " FROM ERP_NHANHANG A          " + 
							  " inner join ERP_LENHSANXUAT_GIAY lsx on lsx.PK_SEQ=A.LENHSANXUAT_FK    " + 
							  " INNER JOIN ERP_NHAMAY NM ON NM.pk_seq=lsx.NHAMAY_FK   " + 
							  " INNER JOIN ERP_NHANHANG_SP_CHITIET NKSP ON A.PK_SEQ=NKSP.NHANHANG_FK    " + 
							  " INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK        " + 
							  " INNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK    " + 
							  " LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK     " + 
							  " WHERE A.PK_SEQ = "+id;
			 
			//System.out.println(query);
			rs =  db.get(query);
			CapnhatKT KT;
			while (rs.next()){
				 	
					Nhamayid=rs.getString("NHAMAY_FK");
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
				 
					KT.setSOLUONG(formater_3sole.format(soluong));;
					
					double dongia=GetGia_ChayKT_Dauky(spid,db,Ischaycuoiky,thangtruoc,namtruoc,ngaychotnv);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
	  
					String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
					
				
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO( thanhtien );
					KT.setCO( thanhtien );
					KT.setTONGGIATRI( thanhtien );
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setSolo(rs.getString("SOLO"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
			 
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						return msg1;
					}
					
					query="UPDATE ERP_NHANHANG_SANPHAM   SET GIACHAYKT="+dongia+" WHERE  SANPHAM_FK="+spid +" AND NHANHANG_FK ="+id;
					if(!db.update(query)){
						return "Không thể cập nhật giá chạy kế toán ,vui lòng kiểm tra lại";
					}
					
			}  
			rs.close();
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	
	
	public   String capnhatketoan_Nhap_trahang(String userId,
			Idbutils db, String id, boolean Ischaycuoiky) {
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhận hàng trả lại";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
			 if(msg.length()>0){
				 return msg;
			 }
			
			query = " SELECT NGAYNHAPKHO as ngaychot, ghichu FROM ERP_DONTRAHANG  WHERE PK_SEQ=" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("ghichu");
				  ngaychotnv = rs.getString("ngaychot");
				  ndxId = "";
				  noidung="Nhận hàng trả lại";
			}
			rs.close();
				
			String[] thangnam = getThangNam(db,Ischaycuoiky,ngaychotnv);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			  
			query	= "		select 100050 AS NOIDUNGNHAP,DTH.KHOTT_FK,SP.LOAISANPHAM_FK,(select pk_seq from erp_taikhoankt \n " +
				 "		where SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK and npp_fk=1) AS TAIKHOANNO_FK,   \n " +
				 "			(select pk_seq from erp_taikhoankt   \n " +
				 "			where SOHIEUTAIKHOAN= LSP.TaikhoanGiavon_sh_fk and npp_fk=1) AS TAIKHOANCO_FK,  \n " +
				 "			DTH.KHOTT_FK,DTH_SP.SANPHAM_FK,DTH_SP.SoLo,SP.PK_SEQ,SP.MA,SP.TEN,DV.DONVI,  \n " +
				 "			DTH_SP.SOLUONG  \n " +
				 "			from ERP_DONTRAHANG_SANPHAM DTH_SP INNER JOIN ERP_SANPHAM sp on sp.PK_SEQ=DTH_SP.SANPHAM_FK  \n " +
				 "			inner join ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK  \n " +
				 "			INNER JOIN ERP_DONTRAHANG DTH ON DTH.PK_SEQ=DTH_SP.dontrahang_fk  \n " +
				 "			LEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK    \n " +
				 "			WHERE DTH.PK_SEQ ="+id+" AND DTH_SP.SOLUONG>0  \n " ;

				System.out.println(query);
				rs =  db.get(query);
				CapnhatKT KT;
				while (rs.next()){
				 	
				 	
					KT=new CapnhatKT();
					KT.setSochungtu(id);
				 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
					String spid = rs.getString("SANPHAM_FK");
					KT.setSpId(spid);
					 
					double soluong = rs.getDouble("SOLUONG");
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG(formater.format(soluong));
					
					double dongia=GetGia_ChayKT_Dauky(spid,db,Ischaycuoiky,thangtruoc,namtruoc,ngaychotnv);
					
					KT.setDONGIA(formater.format(dongia));
					
					String thanhtien = formater.format(dongia*soluong);
					 
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
					KT.setNam(nam);
					KT.setThang(thang);
				
					String taikhoanktNo = rs.getString("TAIKHOANNO_FK");
					String taikhoanktCo = rs.getString("TAIKHOANCO_FK");
					
				
					
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
					
					String doituong_no = "";
					
					String madoituong_no = "";
					String doituong_co = "";
					String madoituong_co ="";
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				 
					String tiente_fk = "100000";
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
//					KT.setKhoNhanID(rs.getString("KHONHAP"));
					KT.setMasp(rs.getString("MA"));
					KT.setTensp(rs.getString("TEN"));
					KT.setDonvi(rs.getString("DONVI"));
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngaychotnv);
					KT.setNgaychungtu(ngaychotnv);
					KT.setNgayghinhan(ngaychotnv);
					
					KT.setKhoanmuc(diengiai_ketoan);
					System.out.println("THANHTIEN : "+thanhtien);
					String msg1=KT.CapNhatKeToan_Kho(util, db);
					if(msg1.length()> 0){
						return msg1;
					}
				
					 
				} 
				 
				
				rs.close();
		 	 
				 
			 
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}

	public String CapNhatKeToanKho_XuatKho_Donhang_HO(String userId, Idbutils db, String id, boolean Ischaycuoiky, String ctyId) 
	{
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu = "Xuất kho bán hàng";
			
			msg = Revert_KeToan_loaichungtu(db, loaichungtu, id);
			if(msg.length()>0){
				System.out.println("msg = " + msg);
				return msg;
			}
			
			query = " SELECT XK.NGAYYEUCAU AS NGAYCHOT, N'Phiếu xuất kho '+ CAST(XK.PK_SEQ AS NVARCHAR) + a.TEN AS DIENGIAI, a.pk_seq as NPP_FK "+ 
					" FROM ERP_YCXUATKHO XK left join nhaphanphoi a on xk.npp_fk = a.pk_seq  "+
					" WHERE xk.trangthai in (2,3) and XK.PK_SEQ = " + id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			 
			String diengiai_ketoan="";
			String noidung="";
			String ndxId="";

		 	if(rs.next()){

				  diengiai_ketoan = rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  //ndxId = rs.getString("NOIDUNGXUAT");
				  //noidung = rs.getString("NOIDUNG");

			}
			rs.close();
				
			String[] thangnam = getThangNam(db, Ischaycuoiky, ngaychotnv);
			String thangtruoc = thangnam[0];
			String namtruoc = thangnam[1];
			String solo = "";
						
		;
			
			//XK01-- XUẤT KHO BÁN HÀNG 
			query =	"SELECT DH.loaidonhang, isnull(XK_SP.SCHEME,'') as SCHEME, km.codieukien, km.codangki, km.inchung, LOAI_SP.PK_SEQ LOAIHH,  \n" + 
				"XK.NPP_FK KHACHHANG_FK, isnull(dh.ishm, 0) ishm, \n" + 
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TaikhoanGiavon_sh_fk  AND NPP_FK = 1 ) TAIKHOANHO_GIAVON,  \n" + 
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = LOAI_SP.TAIKHOANKT_FK   AND NPP_FK = 1) TAIKHOANKT, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64163001') AND NPP_FK = 1) taikhoankt_64163001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64163001') kmcp_64163001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64144001') AND NPP_FK = 1) taikhoankt_64144001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64144001') kmcp_64144001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-35320001') AND NPP_FK = 1) taikhoankt_35320001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-35320001') kmcp_35320001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64180001') AND NPP_FK = 1) taikhoankt_64180001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64180001') kmcp_64180001, \n" +
				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = (select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64173001') AND NPP_FK = 1) taikhoankt_64173001, \n" +
				"(select taikhoan_fk from erp_nhomchiphi where ten = 'PBC-64173001') kmcp_64173001, \n" +
//				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '15700000'   AND NPP_FK = 1) TAIKHOANKT_KYGUI,  \n" + 
//				"(SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE SOHIEUTAIKHOAN = '64185110'  AND NPP_FK = 1 ) TAIKHOANKT_64185110,  "
//				"(select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN='63230000'  AND NPP_FK = 1 )  as  TAIKHOANKT_63230000 , \n" + 
				"XK_SP.solo AS SOLO, XK_SP.soluong SOLUONG, SP.PK_SEQ SANPHAM_FK, XK.NgayYeuCau, 100000 as TIENTE_FK, 1 as TIGIA, \n" +   
				"XK_SP.SANPHAM_FK MASP, SP.TEN TENSP, DV.DONVI AS DONVI, XK.KHO_FK \n" +   
				"FROM ERP_YCXUATKHO XK \n" +  
				"INNER JOIN ERP_YCXUATKHO_SANPHAM_CHITIET XK_SP ON XK.PK_SEQ = XK_SP.ycxk_fk \n" +   
				"INNER JOIN ERP_YCXUATKHO_DDH YCXK_DH ON XK.PK_SEQ = YCXK_DH.ycxk_fk \n" +   
				"INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = YCXK_DH.HOADON_FK \n" +  
				"INNER JOIN ERP_DONDATHANG DH ON DH.PK_SEQ = YCXK_DH.DDH_FK \n" +  
				"INNER JOIN ERP_SANPHAM SP ON XK_SP.SANPHAM_FK = SP.PK_SEQ \n" +  
				"INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.dvdl_fk \n" +  
				"LEFT JOIN ERP_LOAISANPHAM LOAI_SP ON SP.LOAISANPHAM_FK = LOAI_SP.PK_SEQ \n" +   
				"LEFT JOIN NHAPHANPHOI KH ON XK.NPP_FK = KH.PK_SEQ \n" +  
				"left join ctkhuyenmai km on isnull(xk_sp.scheme,'') = km.scheme \n" +
				"INNER JOIN ERP_KHOTT KHO  ON XK.Kho_FK = KHO.PK_SEQ \n" +  
				"WHERE XK.PK_SEQ ="+id+"  ";
				
			System.out.println("lay cau lenh : "+query);
			
			rs =  db.get(query);
			CapnhatKT KT;
			while (rs.next())
			{
			 	String loaidonhang = rs.getString("loaidonhang");
			 	String scheme = rs.getString("SCHEME");
			 	int codieukien = rs.getInt("codieukien");
			 	int codangki = rs.getInt("codangki");
			 	int inchung = rs.getInt("inchung");
			 	int ishm = rs.getInt("ishm");
				KT = new CapnhatKT();
				KT.setSochungtu(id);

			 	KT.setNOIDUNGNHAPXUAT_FK(""); 
				String spid = rs.getString("SANPHAM_FK");
				KT.setSpId(spid);
				 
				double soluong = rs.getDouble("SOLUONG");
				// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
								 
				KT.setSOLUONG(formater_3sole.format(soluong));	
				solo = rs.getString("SOLO");
				KT.setSolo(solo);
				double dongia = GetGia_ChayKT(spid, db, Ischaycuoiky, thangtruoc, namtruoc);				
				KT.setDONGIA(formater.format(dongia));				
								 
				String nam = ngaychotnv.substring(0, 4);
				String thang = ngaychotnv.substring(5, 7);
				KT.setNam(nam);
				KT.setThang(thang);
							
				String taikhoanktCo = "";
				String taikhoanktNo = "", taikhoankt_64163001 = "", taikhoankt_64144001 = "", taikhoankt_35320001 = "", taikhoankt_64180001 = "", taikhoankt_64173001 = "";
				String kmcp = "NULL", kmcp_64163001 = "", kmcp_64144001 = "", kmcp_35320001 = "", kmcp_64180001 = "", kmcp_64173001 = "";
				taikhoanktCo = rs.getString("TAIKHOANKT");
				taikhoanktNo = rs.getString("TAIKHOANHO_GIAVON");
				taikhoankt_64163001 = rs.getString("taikhoankt_64163001");
				kmcp_64163001 = rs.getString("kmcp_64163001");
				taikhoankt_64144001 = rs.getString("taikhoankt_64144001");
				kmcp_64144001 = rs.getString("kmcp_64144001");
				taikhoankt_35320001 = rs.getString("taikhoankt_35320001");
				kmcp_35320001 = rs.getString("kmcp_35320001");
				taikhoankt_64180001 = rs.getString("taikhoankt_64180001");
				kmcp_64180001 = rs.getString("kmcp_64180001");
				taikhoankt_64173001 = rs.getString("taikhoankt_64173001");
				kmcp_64173001 = rs.getString("kmcp_64173001");
				
				if(loaidonhang.equals("2"))
				{
					if(scheme.trim().length() > 0)
					{
						if(codieukien == 0)
						{
							taikhoanktNo = taikhoankt_64163001;
							kmcp = kmcp_64163001;
						}
					}
				}
				if(loaidonhang.equals("4"))
				{
					if(ishm == 1)
					{
						taikhoanktNo = taikhoankt_64144001;
						kmcp = kmcp_64144001;
					}
					else if(ishm == 2)
					{
						taikhoanktNo = taikhoankt_35320001;
						kmcp = kmcp_35320001;
					}
					else if(ishm == 3)
					{
						taikhoanktNo = taikhoankt_64180001;
						kmcp = kmcp_64180001;
					}
					else if(ishm == 4)
					{
						taikhoanktNo = taikhoankt_64173001;
						kmcp = kmcp_64173001;
					}
				}
				
				String thanhtien = formater.format(Math.round(dongia*soluong));
				
				KT.setTaikhoanCO_fk(taikhoanktCo);
				KT.setTaikhoanNO_fk(taikhoanktNo);
				
				String doituong_no = "";
				
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co ="";
				KT.setDOITUONG_CO(doituong_co);
				KT.setDOITUONG_NO(doituong_no);
				KT.setMADOITUONG_CO(madoituong_co);
				KT.setMADOITUONG_NO(madoituong_no);
				KT.setTIGIA_FKl("1");
				KT.setDONGIANT("0");
				String tiente_fk = "100000";
				KT.setTIENTEGOC_FK(tiente_fk);
				
				double  dongiaViet = dongia;
				
				KT.setDONGIA(dongiaViet+"");
				  
				KT.setNO(thanhtien+"");
				KT.setCO(thanhtien+"");
				KT.setTONGGIATRI(thanhtien+"");
				
				KT.setChiPhiId(kmcp);
			
				KT.setKhoNhanID(rs.getString("KHO_FK"));
				KT.setMasp(rs.getString("MASP"));
				KT.setTensp(rs.getString("TENSP"));
				KT.setDonvi(rs.getString("DONVI"));
				KT.setLoaichungtu(loaichungtu);
				
				KT.setNgaychotnv(ngaychotnv);
				KT.setNgaychungtu(ngaychotnv);
				KT.setNgayghinhan(ngaychotnv);
				
				KT.setKhoanmuc(diengiai_ketoan);
				System.out.println("THANHTIEN : "+thanhtien);
				String msg1=KT.CapNhatKeToan_Kho(util, db);
				if(msg1.length()> 0){
					return msg1;
				}
				
				query = " UPDATE ERP_YCXUATKHONPP_SANPHAM_CHITIET SET GIACHAYKT_3 = " + dongia + ", GIACHAYKT = " + dongia + " WHERE ycxk_fk = " + id + " and SANPHAM_FK = " + spid;
				if(!db.update(query)){
					return "Lỗi trong quá trình cập nhật giá kế toán";
				}				
			}  
			rs.close();		 
		 
			return "";
		 }

		catch (Exception e)
		{ 	
			e.printStackTrace();
			msg = e.getMessage(); 
			return msg;
		}
	}
	
	public String capnhatketoan_nhanhangkhac(
			 Idbutils db, String id,String ctyId) { 
		
		String msg="";
		String query = "";
		try 
		{
			
			Utility util = new Utility();
			String loaichungtu="Nhập hàng khác";
			
			query = "select isnull(NoiDungNhap_fk,'0')  noidungnhap,isnull(GHICHU,'') DIENGIAI,NGAYNHAN as ngaychot from ERP_NHANHANG  WHERE PK_SEQ =" +  id;

			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String diengiai_ketoan="";
			String ndnhapId="";
		 	if(rs.next()){

				  diengiai_ketoan= rs.getString("DIENGIAI");
				  ngaychotnv = rs.getString("ngaychot");
				  ndnhapId=rs.getString("noidungnhap");

			}
			rs.close();
			
			 
			
		
				
		 
			if(ndnhapId.equals("100051"))
			{
				msg= Revert_KeToan_loaichungtu(db,loaichungtu,id);
				 if(msg.length()>0){
					 return msg;
				 }
				 
				query	=" SELECT  ISNULL(CAST(A.NoiDungNhap_fk AS VARCHAR(18)),'')    AS NOIDUNGNHAP  ,         "
						 +"\n(select top 1 KHONHAN from ERP_NHANHANG_SANPHAM where nhanhang_fk=a.pk_seq and sanpham_fk=sp.pk_seq) as KHONHAP ,SP.LOAISANPHAM_FK, (SELECT PK_SEQ FROM ERP_TAIKHOANKT TK WHERE        "
						 +"\nTK.SOHIEUTAIKHOAN= LSP.TAIKHOANKT_FK  AND TK.CONGTY_FK='"+ctyId+"') AS TAIKHOANNO_FK  "
						 +"\n, NKSP.SANPHAM_FK ,NKSP.SOLO,SP.PK_SEQ ,SP.MA,SP.TEN,DV.DONVI,SP.DVDL_FK         "
						 +"\n,    NKSP.SOLUONG   AS SOLUONG,"
						 +"\n(select top 1 dongia from ERP_NHANHANG_SANPHAM where nhanhang_fk=a.pk_seq and sanpham_fk=sp.pk_seq) as DONGIA,a.nhomchiphi_fk ,"
						 +"\n(select top 1 PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN in "
						 +"\n (select taikhoan_fk from erp_nhomchiphi ncp where ncp.pk_Seq=a.nhomchiphi_fk) ) as taikhoanco_chiphi            "
						 +"\nFROM ERP_NHANHANG A                      "
						 +"\nINNER JOIN ERP_NHANHANG_SP_CHITIET NKSP ON A.PK_SEQ=NKSP.NHANHANG_FK         "
						 +"\nINNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = NKSP.SANPHAM_FK             "
						 +"\nINNER JOIN ERP_LOAISANPHAM LSP ON LSP.PK_SEQ=SP.LOAISANPHAM_FK         "
						 +"\nLEFT JOIN DONVIDOLUONG DV ON DV.PK_SEQ=  SP.DVDL_FK          "
						 +"\nWHERE A.PK_SEQ ='"+id+"'";
		 
		System.out.println("cahy dinh khoan "+query);
			rs =  db.get(query);
			CapnhatKT KT;
			while (rs.next()){
			 	
				 
				KT=new CapnhatKT();
				KT.setSochungtu(id);
			 	KT.setNOIDUNGNHAPXUAT_FK(rs.getString("NOIDUNGNHAP")); 
				String spid = rs.getString("SANPHAM_FK");
				KT.setSpId(spid);
				 
				double soluong = rs.getDouble("SOLUONG");
				// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
			 
				KT.setSOLUONG(DinhDang.dinhdangso(soluong)+"");
				
				double dongia= rs.getDouble("dongia");
				
				KT.setDONGIA(DinhDang.dinhdangso(dongia)+"");
				
				String thanhtien = formater.format(dongia*soluong);
				 
				String nam = ngaychotnv.substring(0, 4);
				String thang = ngaychotnv.substring(5, 7);
				KT.setNam(nam);
				KT.setThang(thang);
				// tai
				String taikhoanktNo = rs.getString("TAIKHOANNO_FK"); 
				String taikhoanktCo = rs.getString("taikhoanco_chiphi");
				KT.setTaikhoanCO_fk(taikhoanktCo);
				KT.setTaikhoanNO_fk(taikhoanktNo);		
				String doituong_no = "";	
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co ="";
				KT.setDOITUONG_CO(doituong_co);
				KT.setDOITUONG_NO(doituong_no);
				KT.setMADOITUONG_CO(madoituong_co);
				KT.setMADOITUONG_NO(madoituong_no);
				KT.setTIGIA_FKl("1");
				KT.setDONGIANT("0");
				String tiente_fk = "100000";
				KT.setTIENTEGOC_FK(tiente_fk);
				double  dongiaViet = dongia;
				
				KT.setDONGIA(dongiaViet+"");
				  
				KT.setNO( thanhtien );
				KT.setCO( thanhtien );
				KT.setTONGGIATRI( thanhtien );
				
				KT.setChiPhiId(rs.getString("nhomchiphi_fk"));
				
				
				KT.setKhoNhanID(rs.getString("KHONHAP"));
				KT.setMasp(rs.getString("MA"));
				KT.setTensp(rs.getString("TEN"));
				KT.setDonvi(rs.getString("DONVI"));
				KT.setSolo(rs.getString("SOLO"));
				KT.setLoaichungtu(loaichungtu);
				
				KT.setNgaychotnv(ngaychotnv);
				KT.setNgaychungtu(ngaychotnv);
				KT.setNgayghinhan(ngaychotnv);
				KT.setDiengiai(diengiai_ketoan);
				KT.setKhoanmuc("Nhập hàng khác");
		 
				String msg1=KT.CapNhatKeToan_Kho(util, db);
				if(msg1.length()> 0){
					return msg1;
				}
				
				query="UPDATE ERP_NHANHANG_SANPHAM   SET GIACHAYKT="+dongia+" WHERE  SANPHAM_FK="+spid +" AND NHANHANG_FK ="+id;
				if(!db.update(query)){
					return "Không thể cập nhật giá chạy kế toán ,vui lòng kiểm tra lại";
				}
				
		}  
		rs.close();
				
			}
  
			
			 
			return "";
		} 
		
		catch (Exception e)
		{ 	
			e.printStackTrace();
			 
			 msg = e.getMessage(); 
			return msg;
		}
	}
	public static Object getLoaiKho(String khoid,
			Idbutils db) {

		try {
			String query = "select  ISNULL(LOAIKHO,'') AS LOAI  from ERP_KHOTT where PK_SEQ  IN ("
					+ khoid + ") ";
			ResultSet rs = db.get(query);
			String loaikho = "";
			if (rs.next()) {
				loaikho = rs.getString("LOAI");
			}
			rs.close();
			return loaikho;

		} catch (Exception er) {
			return "";
		}
	}

	public  double GetGia_ChayKT_Dauky(String spid,boolean ischaycuoiky, String thangtruoc, String namtruoc,String ngaychungtu) {
		// TODO Auto-generated method stub
		double dongia = 0;
		 
		dbutils db=new dbutils();
		try{
			if(!ischaycuoiky){
					String query = " SELECT SANPHAM_FK, round(ISNULL(DONGIA, 0), 0) GIATON  from ERP_BANGGIA_THANHPHAM_CUOIKY    " +
									" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
									" AND NAM = '" + namtruoc + "'  ";
						System.out.println(query);
					ResultSet rsgia = db.get(query);
					int bien=0;
					if(rsgia.next())
					{
						dongia = rsgia.getDouble("GIATON");
						bien++;
					}
					rsgia.close();
					
					if(bien==0)
					{
						String query3 =	"SELECT TOP 1 DONGIA * isnull(MH.TyGiaQuyDoi,1) AS DONGIA , NH.NGAYNHAN " +
										"FROM ERP_NHANHANG NH " +
										"LEFT JOIN  ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ=NHSP.NHANHANG_FK "
										+ " INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ= NH.MUAHANG_FK  " +
										"WHERE     nh.trangthai=1  and NHSP.SANPHAM_FK ="+ spid+" and NH.NGAYNHAN <= '"+ngaychungtu+"'  " +
										"ORDER BY NH.NGAYNHAN DESC " ;
						ResultSet rs3 = db.get(query3);
						System.out.println("query 3: " + query3);
						 
						if(rs3.next())
						{
							dongia=rs3.getDouble("DONGIA");
							rs3.close();
						}
						
						 
					}
			}else{
					String query = " SELECT SANPHAM_FK,  DONGIA_2  from ERP_BANGGIA_TON_CUOIKY " +
					" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
					" AND NAM = '" + namtruoc + "'  ";
							ResultSet rsgia = db.get(query);
							if(rsgia.next()){
								dongia = rsgia.getDouble("DONGIA_2");
							}
							rsgia.close();
			}
				 
		}catch(Exception er){
			er.printStackTrace();
		}
		db.shutDown();
		return dongia;
	}
	
}

