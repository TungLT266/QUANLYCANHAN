package geso.traphaco.erp.beans.huychungtu.imp;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huychungtu.IErpHuyChuyenKho;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Utility_Kho;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpHuyChuyenKho implements IErpHuyChuyenKho
{
	String congtyId;
	String userId;
	String id;
	String sochungtu;
	String Sochungtu_chonhuy;
	String ttnccId;
	String hdId;
	String tieuhaoId;
	String qcId;
	String pnkId;
	String pnhId;
	String dmhId;
	String msg;
	String Ngayghinhan;


	String pdt_eoId="";
	String chuyenVitriId="";

	public String getPdt_eoId() {
		return pdt_eoId;
	}

	public void setPdt_eoId(String pdt_eoId) {
		this.pdt_eoId = pdt_eoId;
	}

	public String getChuyenVitriId() {
		return chuyenVitriId;
	}

	public void setChuyenVitriId(String chuyenVitriId) {
		this.chuyenVitriId = chuyenVitriId;
	}

	ResultSet sochungtuRequest;
	ResultSet sochungtuDetail;

	String loaichungtu;

	dbutils db;
	private Utility util;
	private Utility_Kho util_kho=new Utility_Kho();

	public ErpHuyChuyenKho()
	{
		this.id = "";
		this.sochungtu = "";
		this.ttnccId = "";
		this.hdId = "";
		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.dmhId = "";
		this.Ngayghinhan="";
		this.loaichungtu="";
		this.msg = "";
		this.util=new Utility();
		this.db = new dbutils();
	}

	public ErpHuyChuyenKho(String id)
	{
		this.id = id;
		this.sochungtu = "";
		this.ttnccId = "";
		this.hdId = "";
		this.tieuhaoId = "";
		this.qcId = "";
		this.pnkId = "";
		this.pnhId = "";
		this.Ngayghinhan="";
		this.dmhId = "";
		this.msg = "";
		this.util=new Utility();
		this.db = new dbutils();
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getSoThanhtoan() 
	{
		return this.ttnccId;
	}

	public void setSoThanhtoan(String sothanhtoan) 
	{
		this.ttnccId = sothanhtoan;
	}

	public String getSoHoadon() 
	{
		return this.hdId;
	}

	public void setSoHoadon(String sohoadon) 
	{
		this.hdId = sohoadon;
	}

	public String getSophieunhapkho() 
	{
		return this.pnkId;
	}

	public void setSoPhieunhapkho(String sonhapkho)
	{
		this.pnkId = sonhapkho;
	}

	public String getSoPhieunhanhang() 
	{
		return this.pnhId;
	}

	public void setSoPhieunhanhang(String sonhanhang)
	{
		this.pnhId = sonhanhang;
	}

	public String getSoDonMuahang() 
	{
		return this.dmhId;
	}

	public void setSoDonmuahang(String somuahang) 
	{
		this.dmhId = somuahang;
	}

	public void createRs()
	{
		this.dmhId = "";
		this.pnhId = "";
		this.pnkId = "";
		this.hdId = "";
		this.ttnccId = "";


		this.chuyenVitriId="";
		this.pdt_eoId="";

		if( this.sochungtu.length() > 0 )
		{ 
			this.createRsLsx();
		}
	}


	private void createRsLsx() 
	{
		try
		{
			String requestMh="";
			/*if(this.loaichungtu.equals("CK")){

			  requestMh =      		 " SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYCHUYEN AS NGAYCHUNGTU,      " + 
									 " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'CHỜ NHẬN HÀNG/CHỜ XUẤT KHO'  " + 
									 " WHEN 2 THEN N'ĐÃ NHẬN HÀNG/ĐÃ XUẤT KHO' ELSE N'HOÀN TẤT' END AS TRANGTHAI,     " + 
									 " N'CHUYỂN KHO' AS LOAICHUNGTU, A.NGAYTAO,'CHUYENKHO' as type , 1 AS STT  " + 
									 " FROM ERP_CHUYENKHO A   " + 
									 " WHERE A.PK_SEQ ="+sochungtu+" AND A.TRANGTHAI <>4  AND YCKD_FK IS NULL " ;

			}else if(this.loaichungtu.equals("DQC")) {
						requestMh=   " SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAY AS NGAYCHUNGTU, "+        
									 " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT'  END AS TRANGTHAI,  "+    
									 " N'ĐỔI QUY CÁCH' AS LOAICHUNGTU, A.NGAY AS NGAYTAO,'DQC' as type , 1 AS STT    "+
									 " FROM ERP_XUATDOIQUYCACH A     "+
									 " WHERE A.PK_SEQ="+sochungtu+" AND A.TRANGTHAI IN (1)";
			}else if(this.loaichungtu.equals("YCCK")){
				  requestMh =        " SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYCHUYEN AS NGAYCHUNGTU,      " + 
									 " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'CHỜ NHẬN HÀNG/CHỜ XUẤT KHO'  " + 
									 " WHEN 2 THEN N'ĐÃ NHẬN HÀNG/ĐÃ XUẤT KHO' ELSE N'HOÀN TẤT' END AS TRANGTHAI,     " + 
									 " N'CHUYỂN KHO' AS LOAICHUNGTU, A.NGAYTAO,'CHUYENKHO' as type , 1 AS STT  " + 
									 " FROM ERP_CHUYENKHO A   " + 
									 " WHERE A.YEUCAUCHUYENKHO_FK="+sochungtu+" AND A.TRANGTHAI <>4  	 " + 
									 " UNION ALL " + 
									 " SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYYEUCAU AS NGAYCHUNGTU,      " + 
									 " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ CHUYỂN KHO' ELSE N'HOÀN TẤT' END AS TRANGTHAI,     " + 
									 " N'YÊU CẦU CHUYỂN KHO' AS LOAICHUNGTU, A.NGAYTAO,'YEUCAUCHUYENKHO' as type , 2 AS STT  " + 
									 " FROM ERP_YEUCAUCHUYENKHO A   " + 
									 " WHERE A.PK_SEQ="+sochungtu+" AND A.TRANGTHAI <>4  "+			
									 " ORDER BY STT ";
			}else if(this.loaichungtu.equals("DCTK")){
				 requestMh =         " SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.ngaydieuchinh AS NGAYCHUNGTU, "+          
					 				 " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT'  END AS TRANGTHAI, "+    
					 				 " N'ĐIỀU CHỈNH TỒN KHO' AS LOAICHUNGTU, A.ngaytao AS NGAYTAO,'DCTK' as type , 1 AS STT "+     
					 				 " FROM ERP_DIEUCHINHTONKHOTT A "+     
					 				 " WHERE A.PK_SEQ="+sochungtu+"   AND A.TRANGTHAI IN (1) ";

			}
			else if(this.loaichungtu.equals("KK")){
				 requestMh =         " SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYKIEM AS NGAYCHUNGTU, "+           
									 " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT'  END AS TRANGTHAI,     "+
									 " N'KIỂM KHO' AS LOAICHUNGTU, A.ngaytao AS NGAYTAO,'KK' as type , 1 AS STT      "+
									 " FROM ERP_KIEMKHO A       "+
									 " WHERE A.PK_SEQ="+sochungtu+"  AND A.TRANGTHAI IN (1) ";

			}
			//System.out.println("1.Requset la: " + requestMh);
			 */			


			//LAY DANH SACH PHIEU DINH TINH/ EO
			if(this.loaichungtu.equals("PDT_EO"))
			{

				requestMh =	  " SELECT  A.PK_SEQ,  CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NGAYCHUNGTU  AS NGAYCHUNGTU,      \n" + 
							  " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' END AS TRANGTHAI,     \n" + 
							  " N'PHIẾU KIỂM ĐỊNH EO/ĐỊNH TÍNH' AS LOAICHUNGTU, A.NGAYTAO,'PDT_EO' as type , 1 AS STT   \n" + 
							  " FROM ERP_DINHTINH_EO A   \n" + 
							  " WHERE A.PK_SEQ ='"+sochungtu+"' AND A.TRANGTHAI <>2   " ;

			}
			else if(this.loaichungtu.equals("CVT")) {
				requestMh=    " SELECT  A.PK_SEQ,  CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.NgayChuyen  AS NGAYCHUNGTU,       \n" + 
							  " CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' END AS TRANGTHAI,    \n" + 
							  " N'PHIẾU CHUYỂN VỊ TRÍ' AS LOAICHUNGTU, A.NGAYTAO,'CVT' as type , 1 AS STT    \n" + 
							  " FROM ERP_CHUYENVITRI A   \n" + 
							  " WHERE A.PK_SEQ ='"+sochungtu+"' AND A.TRANGTHAI NOT IN (4,0)";
			}
			else if(this.loaichungtu.equals("DCTTK")) {
				requestMh="SELECT  A.PK_SEQ,  CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.ngaydieuchinh  AS NGAYCHUNGTU,       "
						 +"\nCASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT'  WHEN 2 THEN N'ĐÃ HỦY' END AS TRANGTHAI,    "
						 +"\n N'ĐIỀU CHỈNH THÔNG TIN KHO' AS LOAICHUNGTU, A.NGAYTAO,'DCTTK' as type , 1 AS STT   "
						 +"\nFROM ERP_DOISOLO A  "
						 +"\nWHERE A.PK_SEQ ='"+sochungtu+"' AND A.TRANGTHAI='1'";
				
			}
			else if(this.loaichungtu.equals("DCTK")) {
				requestMh =  " SELECT  A.PK_SEQ,   CAST(A.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, A.ngaydieuchinh AS NGAYCHUNGTU, \n"+          
						" CASE A.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT'  END AS TRANGTHAI, \n"+    
						" N'ĐIỀU CHỈNH TỒN KHO' AS LOAICHUNGTU, A.ngaytao AS NGAYTAO,'DCTK' as type , 1 AS STT \n"+     
						" FROM ERP_DIEUCHINHTONKHOTT A \n"+     
						" WHERE A.PK_SEQ="+sochungtu+"   AND A.TRANGTHAI IN (1) \n";
			}
			
	
			System.out.println("Lay danh sach :"+ requestMh);
			this.sochungtuRequest = db.get(requestMh);

		}
		catch (Exception e) 
		{
			e.printStackTrace();
			//System.out.println("Exception : " + e.getMessage());
		}
	}

	public void init() 
	{
		String query = "SELECT  LOAICHUNGTU,NGAYGHINHAN, PHIEUYEUCAU_FK, TRANGTHAI,NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK FROM ERP_HUYCHUYENKHO WHERE PK_SEQ = '" + this.id + "'";

		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				this.sochungtu = rs.getString("PHIEUYEUCAU_FK");
				this.Ngayghinhan=rs.getString("ngayghinhan");
				this.loaichungtu=rs.getString("loaichungtu");
			}

			rs.close();

			query = " SELECT  HUYCHUYENKHO_FK, THUTU AS STT, SOCHUNGTU, NGAYCHUNGTU, TRANGTHAI, LOAICHUNGTU FROM ERP_HUYCHUYENKHO_CHUNGTU " +
					" WHERE HUYCHUYENKHO_FK = '" + this.id + "' ORDER BY STT  , SOCHUNGTU DESC";

			this.sochungtuRequest = db.get(query);
		}
		catch (Exception e) {
			e.printStackTrace();
		}

	}


	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai, String[] loaichungtu, String[] ngaytao, String[] thutu,String type[])
	{

		if( this.Ngayghinhan.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn ngày ghi nhận.";
			return false;
		}
		String msg1=util.checkNgayHopLe(db,this.Ngayghinhan);
		if(msg1.length() >0)
		{
			this.msg = msg1;
			return false;
		}
		
		if(  this.Ngayghinhan.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn ngày ghi nhận.";
			return false;
		}
		
		
		

		try
		{
			db.getConnection().setAutoCommit(false);

			//khong cam ham tao moi, mac dinh tao moi xong se chot luon

			String query =  " INSERT INTO ERP_HUYCHUYENKHO ( LOAICHUNGTU, ngayghinhan, PHIEUYEUCAU_FK, trangthai,ngaytao, nguoitao, ngaysua, nguoisua, congty_fk ) " +
					" VALUES('"+this.loaichungtu+"','" + this.Ngayghinhan + "', '" + this.sochungtu + "', '1','" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "',"+this.congtyId+")";
			//System.out.println("4.___INSERT REVERT: " + query);

			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi ERP_HUYCHUYENKHO, " + query;

				return false;
			}

			String hctbhCurrent = "";
			query = "select IDENT_CURRENT('ERP_HUYCHUYENKHO') as hctbhId";

			ResultSet rsTthd = db.get(query);	
			if (rsTthd != null)
			{
				if(rsTthd.next())
				{
					hctbhCurrent = rsTthd.getString("hctbhId");
				}
				rsTthd.close();
			}
			this.id = hctbhCurrent;
			int dem=0;
			if(socthuy != null)
			{
				for(int i = 0; i < socthuy.length; i++)
				{
					
					// kiem tra ngay chung tu so vs ngay ghi nhan
					
					if(this.Ngayghinhan.compareTo( ngaychungtu[i] )<0){
						db.getConnection().rollback();
						this.msg = "Vui lòng chọn ngày ghi nhận sau ngày chứng từ!";
						return false;
					}
					
					
					
					query = " insert ERP_HUYCHUYENKHO_CHUNGTU(HUYCHUYENKHO_FK, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu,TYPE) " +
							" values('" + hctbhCurrent + "', '" + thutu[i] + "',  '" + soct[i] + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'" + loaichungtu[i] + "','"+type[i]+"')";
					if(!db.update(query))
					{
						db.getConnection().rollback();
						this.msg = "Không thể tạo mới ERP_HUYCHUYENKHO_CHUNGTU, " + query;
						return false;
					}
					if(!this.RevertChungtu(type[i],soct[i])){
						db.getConnection().rollback();
						return false;
					}
					dem=dem+1;
				}
			} 

			if(dem==0){
				db.getConnection().rollback();
				this.msg = "Không có chứng từ hủy,vui lòng kiểm tra lại trạng thái số chứng từ đang chọn" ;
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return true;
		}
		catch (Exception e)
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}

	private boolean RevertChungtu(String loaichungtu, String sochungtu) {
		// TODO Auto-generated method stub
		try{
			/*if(loaichungtu.equals("CHUYENKHO")){
				//HỦY CHUYỂN KHO CỦA YÊU CẦU

				if(!this.revertChuyenKho( sochungtu)){
					return false;
				}
			}else if(loaichungtu.equals("YEUCAUCHUYENKHO")){
				if(!this.revertYeuCauChuyenKho( sochungtu)){
					return false;
				}
			}else if(loaichungtu.equals("DQC")){
				if(!this.revertDoiQuyCach( sochungtu)){
					return false;
				}

			}else if(loaichungtu.equals("DCTK")){
				if(!this.revertDieuChinhTonKho(sochungtu)){
					return false;
				}

			}else if(loaichungtu.equals("KK")){
				if(!this.revertKiemkho(sochungtu)){
					return false;
				}
			}*/

			
			System.out.println(" loai chung tu: "+ loaichungtu);
			if(loaichungtu.equals("PDT_EO")){
				//HUY PHIEU EO/ ĐỊNH TÍNH
				if(!this.revertPhieuEO_Dinhtinh(sochungtu)){
					return false;
				}
			}
			else
				if(loaichungtu.equals("CVT")){
					//HỦY CHUYỂN VỊ TRI
					if(!this.revertPhieuChuyenViTri( sochungtu)){
						return false;
					}
				}else if(loaichungtu.equals("DCTTK"))
				{
					//Hủy DCTTkho
					if(!this.revertDieuChinhThongTinKho( sochungtu)){
						return false;
					}
				}else if(loaichungtu.equals("DCTK")){
					if(!this.revertDieuChinhTonKho(sochungtu)){
						System.out.println(" loi revert dieu chinh");
						return false;
					}
				}
			

			return true;
		}catch(Exception er){
			this.msg=er.getMessage();
			er.printStackTrace();
			return false;
		}
	}
	private boolean revertDieuChinhThongTinKho(String sochungtu)
	{
		
		try
		{

			
			String query = "update ERP_DOISOLO set trangthai = '2' where pk_seq = '" + sochungtu + "' ";
			System.out.println("::: CHOT CK: " + query);
			
			if( !db.update(query) )
			{
				this.msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				db.shutDown();
				return false;
				
			}
			
			//CẬP NHẬT KHO ==> mấy nữa cải tiến booked tính động, không lấy trong kho
			geso.traphaco.center.util.Utility utilkho = new geso.traphaco.center.util.Utility();
			query = "  select a.pk_seq, b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk, ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, a.SoLo, a.NgayHetHan, a.ngaynhapkho,   " + 
					"  	a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuong, " + 
					"  			c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE,  " +
					"			b.loaidieuchinh, a.soloMoi, a.ngayhethanMoi, a.marquetteMoi,a.maphieuMoi, a.thungMoi, a.meMoi, a.phieudtMoi, a.hamamMoi, a.hamluongMoi, isnull(a.nsx_fk, 0) as nsx_fk		" +
					"  from ERP_DOISOLO_SANPHAM_CHITIET a inner join ERP_DOISOLO b on a.doisolo_FK = b.PK_SEQ  " + 
					"  	 inner join ERP_KHOTT_SP_CHITIET c on b.khott_fk = c.KHOTT_FK and  ISNULL(a.nsx_fk, 0) = ISNULL( c.nsx_fk, 0 ) and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and a.sanpham_fk = c.SANPHAM_FK  " + 
					"  		and ISNULL(	b.loaidoituong, 0 ) = 	ISNULL(	c.loaidoituong, 0 ) and ISNULL( b.DOITUONG_FK, 0 ) = ISNULL(	c.DOITUONGID, 0 ) " + 
					"  		and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO " + 
					"  		and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '') " + 
					"  		and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0) " + 
					"  		and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '') " + 
					"  		and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '') " + 
					"  where b.PK_SEQ = '" + sochungtu + "' and a.soluong > 0 ";
			
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
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");

					double soluong = rs.getDouble("soluong");
					
					String nsx_fk = rs.getString("nsx_fk");

					//Tang kho chuyển
					this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 1 * soluong, 0, 1 * soluong, nsx_fk);
					if( this.msg.trim().length() > 0 )
					{
						
						db.getConnection().rollback();
						db.shutDown();
						return false;
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
					
						this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethanMoi, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("1") ) //Điều chỉnh marquette
					{
						marquetteMoi = rs.getString("marquetteMoi");
						
						this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marquetteMoi, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("2") ) //Điều chỉnh mã phiếu
					{
						maphieuMoi = rs.getString("maphieuMoi");
						
						this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieuMoi, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("3") ) //Điều chỉnh mã thùng
					{
						thungMoi = rs.getString("thungMoi");
						
						this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, thungMoi, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("4") ) //Điều chỉnh mã mẻ
					{
						meMoi = rs.getString("meMoi");
						
						this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
								meMoi, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("5") ) //Điều chỉnh mã phiếu định tính
					{
						phieudtMoi = rs.getString("phieudtMoi");
						
						this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
								mame, mathung, bin_fk, maphieu, phieudtMoi, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					}
					else if( rs.getString("loaidieuchinh").equals("6") ) //Điều chỉnh hàm ẩm / hàm lượng
					{
						hamamMoi = rs.getString("hamamMoi");
						hamluongMoi = rs.getString("hamluongMoi");
					
						this.msg = utilkho.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chốt đổi số lô", db, khoId, spId, soloMoi, ngayhethan, ngaynhapkhoTANG, 
										mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluongMoi, hamamMoi, loaidoituong, doituongId, -1 * soluong, 0, -1 * soluong, nsx_fk);
					}
					
					if( this.msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}
					
				}
				rs.close();
			 
			
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			this.msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			db.shutDown();
			return false;
		}
		
		return true;
	}
	
	
	private boolean revertPhieuEO_Dinhtinh(String sochungtu) {
		// TODO Auto-generated method stub
		try
		{
			//this.db.getConnection().setAutoCommit(false);
			String sql = "";
			
			System.out.println(" da vao huy chung tu dinh tinh eo");
			
			//Trừ kho
			//CẬP NHẬT KHO ==> mấy nữa cải tiến booked tính động, không lấy trong kho
			Utility util = new Utility();
		 
			sql=" select a.kho_fk as kho_fk, a.SANPHAM_FK, N'Xuất định tính EO' as loaichungtu, "+
						" cast(a.PK_SEQ as varchar(10)), a.ngaychungtu, a.ngaychungtu as NGAYCHOT,  "+
					" isnull(bin.ma, '') as vitri, b.solo, b.ngayhethan, b.ngaynhapkho, b.mame, b.mathung, "+
					" b.maphieu, b.phieudt, b.phieueo,  b.MARQ, isnull(b.hamam, '0') as hamam, isnull(b.hamluong, '100') as hamluong"
					+ ", 0 as NHAP, "+
					" b.soluong as XUAT,b.bin_fk, isnull(b.nsx_fk, 0) as nsx_fk "+
					" from ERP_DINHTINH_EO a inner join ERP_DINHTINH_EO_DETAILS b on a.PK_SEQ = b.dinhtinh_fk "+
					" left join ERP_BIN bin on b.bin_fk = bin.pk_seq "+
					" where a.TRANGTHAI = '1' and ( maphieudinhtinhNew != '' or phieueonew != '' ) "+ 
					" and a.PK_SEQ= '" + sochungtu+ "' ";
			System.out.println("::: CAP NHAT KHO: " + sql);
			ResultSet rs = db.get(sql);
		 
				while( rs.next() )
				{
					String khoId = rs.getString("kho_fk");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngaydieuchinh = rs.getString("NGAYCHOT");
					
					String ngayhethan = rs.getString("NgayHetHan");
					
					String ngaynhapkho  = rs.getString("ngaynhapkho");
			 
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");
					
					double soluong = rs.getDouble("XUAT");
				 
					double booked = 0; 
					double avai = rs.getDouble("XUAT");
					
					String nsx_fk = rs.getString("nsx_fk");
					
		 
					//tang lai kho xuat 
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHOT"), "huy chung tu Chốt định tính EO  ID:"+sochungtu, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}
					 
				}
				rs.close();
		 
				sql="  select a.kho_fk as kho_fk, a.SANPHAM_FK, N'Nhập định tính EO' as loaichungtu, cast(a.PK_SEQ as varchar(10)), a.ngaychungtu, a.ngaychungtu as NGAYCHOT,  "+  
						" isnull(bin.ma, '') as vitri, b.solo, b.ngayhethan, b.ngaynhapkhoTANG as ngaynhapkho, b.mame, b.mathung, b.maphieu,  "+  
						" case a.loai when 0 then b.maphieudinhtinhNEW else b.phieudt end as phieudt,  "+  
						" case a.loai when 0 then b.phieueo else b.phieuEONEW end as phieueo,   "+  
						" b.MARQ, isnull(b.hamam, '0') as hamam, isnull(b.hamluong, '100') as hamluong , b.soluong as NHAP,	0 as XUAT,b.bin_fk, isnull(b.nsx_fk, 0) as nsx_fk "+  
						" from ERP_DINHTINH_EO a inner join ERP_DINHTINH_EO_DETAILS b on a.PK_SEQ = b.dinhtinh_fk "+  
						" left join ERP_BIN bin on b.bin_fk = bin.pk_seq "+  
						" where a.TRANGTHAI = '1' and ( maphieudinhtinhNew != '' or phieueonew != '' )  "+  
							" and a.PK_SEQ= '" + sochungtu+ "' ";
		System.out.println("::: CAP NHAT KHO: " + sql);
			rs = db.get(sql);
		 
				while( rs.next() )
				{
					String khoId = rs.getString("kho_fk");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngaydieuchinh = rs.getString("NGAYCHOT");
					
					String ngayhethan = rs.getString("NgayHetHan");
					
					String ngaynhapkho  = rs.getString("ngaynhapkho");
			 
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");
					
					double soluong = (-1)* rs.getDouble("NHAP");
				 
					double booked = 0; 
					double avai = (-1)*  rs.getDouble("NHAP");
					
					String nsx_fk = rs.getString("nsx_fk");
		 
					//tang lai kho xuat 
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHOT"), "Huy chung tu Chốt định tính EO  ID:"+sochungtu, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, nsx_fk);
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						db.shutDown();
						return false;
					}
					 
				}
				rs.close();
			
			
			// cập nhật trang thai huy
			sql = "update ERP_DINHTINH_EO set TRANGTHAI = 2 where PK_SEQ = '" +sochungtu + "' ";
			if(!db.update(sql))
			{
				this.msg = "Cập nhật thất bại Error 1.";
				this.db.getConnection().rollback();
				return false;
			}
			
			/*this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);*/
			
			return true;
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			this.msg = ex.getMessage();
			return false;
		}
		
	}
	
	
	

	private boolean revertPhieuChuyenViTri(String sochungtu) {
		// TODO Auto-generated method stub
		try
		{
			String query="";
			String sql="";
			//TRỪ KHO
			Utility util = new Utility();
			query = "  select b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.Khochuyen_FK, isnull(a.binchuyen_fk, 0) as binchuyen_fk,  isnull(a.binnhan_fk, 0) as binnhan_fk,  a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong, isnull(a.nsx_fk, 0) as nsx_fk  " + 
					"  from ERP_CHUYENVITRI_SANPHAM_CHITIET a inner join ERP_CHUYENVITRI b on a.chuyenvitri_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + sochungtu + "' " + 
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
				
				// tang lai vi tri da chuyen
				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Hủy chuyển vị trí - tăng kho chuyển ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binChuyen_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId,  soluong,  0,  soluong, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi chốt: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return false;
				}
				
				// giam tại vị trí nhận
				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Hủy chuyển vị trí - giảm kho nhận ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, binNhan_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluong, 0,  -1 *soluong, nsx_fk);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi chốt: " + msg;
					db.getConnection().rollback();
					db.shutDown();
					return false;
				}
				
			}
			rs.close();
			
			
			
			// cập nhật trang thai huy
			sql = "update ERP_CHUYENVITRI set trangthai = '4' where pk_seq ='" +sochungtu + "' ";
			if(!db.update(sql))
			{
				this.msg = "Cập nhật thất bại Error 1.";
				this.db.getConnection().rollback();
				return false;
			}

			
			return true;
		
		} 
		catch(Exception ex)
		{
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			this.msg = ex.getMessage();
			return false;
		}
		
	}
	
	
	
	

	private boolean revertKiemkho(String sochungtu2) {
		// TODO Auto-generated method stub
		try{

			Utility_Kho util_kho=new Utility_Kho();

			db.getConnection().setAutoCommit(false);
			String query =  " SELECT DC.NGAYKIEM , SANPHAM_FK, SOLO, SOLUONGDIEUCHINH AS SOLUONG,DC.KHOTT_FK,DCSP.NGAYBATDAU,DCSP.KHUVUCKHO_FK  "+
					" FROM ERP_KIEMKHO_SP_CHITIET DCSP INNER JOIN ERP_KIEMKHO  DC ON DC.PK_SEQ=DCSP.KIEMKHO_FK    "+
					" WHERE DC.trangthai='1' and DC.PK_SEQ="+sochungtu2+"  AND SOLUONGDIEUCHINH IS NOT NULL";

			ResultSet rsSp = db.get(query);
			if (rsSp != null)
			{
				while(rsSp.next())
				{
					String ngaychungtu= rsSp.getString("NGAYKIEM");
					String spId = rsSp.getString("sanpham_fk");
					//					String bin = "100000";
					String solo = rsSp.getString("solo");
					double soluong= (-1)* rsSp.getDouble("soluong");
					double available=(-1)* rsSp.getDouble("soluong");
					double booked=0;
					String ngaybatdau=rsSp.getString("ngaybatdau");
					String khuvuckho_fk=rsSp.getString("khuvuckho_fk");
					String khott_fk=rsSp.getString("khott_fk");

					if(util_kho.getIsQuanLyKhuVuc(rsSp.getString("khott_fk"),db).equals("1")){
						if(khuvuckho_fk==null|| khuvuckho_fk.equals("")){
							this.msg="Không thể xác định khu vực kho của các sản phẩm điều chỉnh.vui lòng thử lại";
							db.getConnection().rollback();
							return false;
						}
					}else{
						khuvuckho_fk="";
					}
					String msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(db, khott_fk, spId, soluong, booked, available, 0,  ngaychungtu) ;
					if(msg1.length() >0){
						this.msg=msg1;						
						return false;
					}
					msg1= util_kho.Update_Kho_Sp_Chitiet(db, khott_fk, spId, soluong, booked, available, 0, solo, "100000", khuvuckho_fk, ngaybatdau);
					if(msg1.length() >0){
						this.msg=msg1;
						return false;
					}

				}
				rsSp.close();
			}			 
			query="update ERP_KIEMKHO set trangthai = '2' where pk_seq = '" + sochungtu2 + "'";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_DIEUCHINHTONKHOTT: " + query;

				return false;
			}

		}catch(Exception err){
			err.printStackTrace();
			this.msg=err.getMessage();
			return false;

		}
		return true;
	}

	private boolean revertDieuChinhTonKho(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
			Utility_Kho util_kho=new Utility_Kho();
			db.getConnection().setAutoCommit(false);
			String	query ="";
			
			query = "select NGAYDIEUCHINH, KHOTT_FK,isnull( DOITUONG_FK,'0') as DOITUONG_FK  from ERP_DIEUCHINHTONKHOTT where pk_seq = '" + sochungtu2 + "' ";
			ResultSet rsData = db.get(query);
			String NGAYDIEUCHINH = "";
			String KHOTT_FK = "";
			String doituongIdDTTK="";
			if( rsData.next() )
			{
				NGAYDIEUCHINH = rsData.getString("NGAYDIEUCHINH");
				KHOTT_FK = rsData.getString("KHOTT_FK");
				doituongIdDTTK= rsData.getString("DOITUONG_FK");
				rsData.close();
			}
			
			// DIEU CHINH GIAM  NGAYNHAPKHO=NGAYNHAPKHO
			query =	  " select b.loaidoituong, b.npp_fk, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk,  \n" + 
						  " ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, a.SoLo, a.NgayHetHan, a.ngaynhapkho,   \n" + 
						  " a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuongdieuchinh, \n" + 
						  " c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE, isnull(a.nsx_fk, 0) as nsx_fk \n" + 
					  " from ERP_DIEUCHINHTONKHOTT b inner join ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a on b.PK_SEQ = a.dctk_fk \n" + 
					  " inner join ERP_KHOTT_SP_CHITIET c on b.khott_fk = c.KHOTT_FK and ISNULL(a.nsx_fk, 0) = ISNULL( c.nsx_fk, 0 ) and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and a.sanpham_fk = c.SANPHAM_FK  \n" + 
						  " and ISNULL(	b.loaidoituong, 0 ) = 	ISNULL(	c.loaidoituong, 0 ) and ISNULL( b.DOITUONG_FK, 0 ) = ISNULL(	c.DOITUONGID, 0 )  \n" + 
						  " and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkho = c.NGAYNHAPKHO  \n" + 
						  " and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '')  \n" + 
						  " and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0)  \n" + 
						  " and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '')  \n" + 
						  " and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '') \n" + 
					  " where b.TRANGTHAI = '1' and a.soluongDIEUCHINH <= 0	 and b.pk_seq='" + sochungtu2 + "' \n" + 
					  
					  " union all \n" + 
					// DIEU CHINH TANG + TON > O: NGAYNHAPKHO=NGAYCHUNGTU
					  " select b.loaidoituong, b.npp_fk, b.DOITUONG_FK as doituongId, b.NGAYDIEUCHINH, b.Khott_FK, a.SanPham_fk,  \n" + 
						  " ISNULL( ( select ten from ERP_SANPHAM where pk_seq = a.sanpham_fk ), '') as tensp, a.SoLo, a.NgayHetHan, a.ngaynhapkhoTANG as ngaynhapkho ,   \n" + 
						  " a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, a.SoLuongdieuchinh, \n" + 
						  " c.SOLUONG as tonkho, c.BOOKED, c.AVAILABLE, isnull(a.nsx_fk, 0) as nsx_fk \n" + 
						  " from ERP_DIEUCHINHTONKHOTT b inner join ERP_DIEUCHINHTONKHOTT_SANPHAM_CHITIET a on b.PK_SEQ = a.dctk_fk \n" + 
					  "  \n" + 
					  " inner join ERP_KHOTT_SP_CHITIET c on b.khott_fk = c.KHOTT_FK and ISNULL(a.nsx_fk, 0) = ISNULL( c.nsx_fk, 0 ) and  ISNULL(a.bin_fk, 0) = ISNULL( c.BIN_FK, 0 ) and a.sanpham_fk = c.SANPHAM_FK  \n" + 
						  " and ISNULL(	b.loaidoituong, 0 ) = 	ISNULL(	c.loaidoituong, 0 ) and ISNULL( b.DOITUONG_FK, 0 ) = ISNULL(	c.DOITUONGID, 0 )  \n" + 
						  " and a.solo = c.SOLO and a.ngayhethan = c.NGAYHETHAN and a.ngaynhapkhoTANG = c.NGAYNHAPKHO  \n" + 
						  " and isnull(a.mame, '') = isnull(c.MAME, '') and isnull(a.mathung, '') = isnull(c.MATHUNG, '')  \n" + 
						  " and isnull(a.hamluong, 100) = isnull(c.hamluong, 100) and isnull(a.hamam, 0) = isnull(c.hamam, 0)  \n" + 
						  " and isnull(a.maphieu, '') = isnull(c.maphieu, '') and isnull(a.marq, '') = isnull(c.marq, '')  \n" + 
						  " and isnull(a.phieudt, '') = isnull(c.maphieudinhtinh, '') and isnull(a.phieueo, '') = isnull(c.phieueo, '') \n" + 
					  " where b.TRANGTHAI = '1' and a.soluongDIEUCHINH > 0	 and b.pk_seq='" + sochungtu2 + "' ";

	
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

					double soluong = rs.getDouble("soluongDIEUCHINH");
					double tonkho = rs.getDouble("tonkho");
					double booked = rs.getDouble("BOOKED");
					double avai = rs.getDouble("AVAILABLE");
					
					String nsx_fk = rs.getString("nsx_fk");

					//Tính ra số lượng cần điều chỉnh, ở đây là điều chỉnh cột số lượng tỏng kho
					double soluongDIEUCHINH = 0;
					soluongDIEUCHINH =soluong; // DAY LA LUONG TRU NGUOC TRONG KHO
					
					//Lưu ý, nếu điều chỉnh tăng thì ngày nhập kho phải = ngày điều chỉnh : PHAN NAY DC PHAN RA TREN RUI
					String ngaynhapkhoTANG = rs.getString("ngaynhapkho");
					if( soluongDIEUCHINH != 0 )
					{
						msg = util.Update_KhoTT_MOI(rs.getString("NGAYDIEUCHINH"), "Hủy chứng từ điều chỉnh tồn kho", db, khoId,  spId, solo, ngayhethan, ngaynhapkhoTANG, 
								mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, -1 * soluongDIEUCHINH, 0, -1 * soluongDIEUCHINH, nsx_fk);

						if( msg.trim().length() > 0 )
						{
							System.err.println("ERR: "+ msg);
							db.getConnection().rollback();
							return false;
						}
					}
				}
				rs.close();
			}


			// XOA CAC DINH KHOAN KE TOAN KHO
			query = "delete erp_phatsinhketoan where loaichungtu = N'Nhập điều chỉnh tồn kho' and sochungtu = " + sochungtu2;
			System.out.println("::: XOA DINH KHOAN: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return false;
			}

			query = "delete erp_phatsinhketoan where loaichungtu = N'Xuất điều chỉnh tồn kho' and sochungtu = " + sochungtu2;
			System.out.println("::: XOA DINH KHOAN: " + query);
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return false;
			}


			// CAP NHAT LẠI TRANG THAI 
			query="update ERP_DIEUCHINHTONKHOTT set trangthai = '2' where pk_seq = '" + sochungtu2 + "'";
			System.out.println("::: UPDATE TRANG THAI: " + query);
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_DIEUCHINHTONKHOTT: " + query;
				return false;
			}

			return true;

		}catch(Exception err){
			err.printStackTrace();
			this.msg=err.getMessage();
			return false;
		}
	}

	private boolean revertDoiQuyCach(String Id) {
		// TODO Auto-generated method stub
		try{

			String query="";

			query = " SELECT  DQC_CT.NGAYHETHAN , DQC_CT.NGAYBATDAU ,DQC_CT.KHUVUC_FK,DQC_CT.SANPHAM_FK, DQC_CT.SOLUONG, DQC_CT.SOLO, DQC.KHO_FK " +
					" FROM ERP_XUATDOIQUYCACH_CHITIET DQC_CT " +
					" INNER JOIN ERP_XUATDOIQUYCACH DQC ON DQC.PK_SEQ = DQC_CT.DOIQUYCACH_FK " +
					" WHERE DOIQUYCACH_FK = " + Id + " AND  DQC.TRANGTHAI='1' ";

			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				while(rs.next())
				{								  
					String khott_fk=rs.getString("KHO_FK");
					double soluongct=rs.getDouble("SOLUONG");
					double booked=0 ;
					double available=rs.getDouble("SOLUONG");

					String sanpham_fk=rs.getString("SANPHAM_FK");
					String solo1=rs.getString("SOLO");
					String khuid=(rs.getString("KHUVUC_FK")==null?"":rs.getString("KHUVUC_FK"));
					if(util_kho.getIsQuanLyKhuVuc(khott_fk, db).equals("1")){
						if(khuid.equals("")){
							this.msg = "Không thể thực hiện nghiệp vụ này,không thể xác định khu vực của các sản phẩm đã đem rã trong phiếu đem sửa";
							this.db.getConnection().rollback();

						}
					}else{
						khuid="";
					}
					//						String ngayhethan=rs.getString("NGAYHETHAN");
					String ngaybatdau_=rs.getString("NGAYBATDAU");

					// cập nhật kho chi tiết
					String msg1= util_kho.Update_Kho_Sp (this.db,khott_fk,sanpham_fk,soluongct, booked,available,0);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					String vitri="100000";
					msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khott_fk,sanpham_fk,soluongct, booked,available,0,solo1,vitri,khuid,ngaybatdau_);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					if(util_kho.IsKhoQuanLyTrangThai(khott_fk, db)){
						this.msg = "Vui lòng báo admin để được xử lý trong trường hợp này,kho xử lý không được rã đổi quy cách";
						this.db.getConnection().rollback();
						return false;
					}

				}
				rs.close();
			}

			query = " SELECT NGAY, NQC.KHUVUC_FK , NQC.TONGGIATRI/XQC.SOLUONG AS DONGIAMUA,NQC.TONGGIATRI  , XQC.KHO_FK,NQC.SANPHAM_FK, " +
					" NQC.SOLUONG, '0', NQC.SOLUONG, NQC.SOLO, '100000', XQC.NGAY as NGAYBATDAU, '" + this.getDateTime() + "' " +
					" FROM ERP_NHANDOIQUYCACH NQC " +
					" INNER JOIN ERP_XUATDOIQUYCACH XQC ON XQC.PK_SEQ = NQC.DOIQUYCACH_FK " +
					" WHERE NQC.DOIQUYCACH_FK = " + Id + "  AND  XQC.TRANGTHAI=1 ";
			rs=this.db.get(query);
			//				int i=0;

			//tinh giá bình quân của các sản phẩm nhập vào

			if (rs != null)
			{
				while (rs.next()){
					//************************************
					String ngaychungtu=rs.getString("NGAY");

					double soluongct= (-1)* rs.getDouble("SOLUONG");
					String sanpham_fk=rs.getString("sanpham_fk");
					String solo=rs.getString("SOLO");
					String KHU=rs.getString("KHUVUC_FK");
					String ngaybatdau=rs.getString("NGAYBATDAU");
					String KHONHAN_FK=rs.getString("KHO_FK");

					if(KHU==null){
						KHU="";
					}


					double dongiamua =rs.getDouble("DONGIAMUA");

					if(util_kho.getIsQuanLyKhuVuc(KHONHAN_FK,db).equals("1")){
						if(KHU.equals("")){
							this.msg="kho nhận có quản lý theo khu.vui lòng xác nhận khu của những sản phẩm rã quy cách";
						}
					}

					String msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(db, KHONHAN_FK,sanpham_fk,soluongct,0,soluongct,0,ngaychungtu);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					msg1= util_kho.Update_Kho_Sp_Chitiet( db,KHONHAN_FK,sanpham_fk,soluongct,0,soluongct,dongiamua,solo,"",KHU,ngaybatdau);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					// số lượng đạt
					//trangthai=0 là hàng đạt
					if(util_kho.IsKhoQuanLyTrangThai(KHONHAN_FK, db)) {
						this.msg = "Không xử lý kho quản lý trạng thái trong trường hợp này, vui lòng báo Admin để được xử lý";
						this.db.getConnection().rollback();
						return false;
					}
					///**************

				}
				rs.close();
			}

			query="update ERP_XUATDOIQUYCACH set trangthai = '2' where pk_seq = '" + Id + "'";
			if(!db.update(query))
			{
				msg = "Không thể cập nhật dòng lệnh: " + query;

				return false;
			}
			//KIỂM TRA CÒN CHUYỂN KHO NÀO KHÔNG?



		}catch(Exception err){
			err.printStackTrace();
			this.msg=err.getMessage();
			return false;
		}
		return true;
	}

	private boolean revertYeuCauChuyenKho(String SOCHUNGTU) {
		try{

			String query="update ERP_YEUCAUCHUYENKHO set trangthai = '4' where pk_seq = '" +  SOCHUNGTU + "'";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat erp_khott_sanpham: " + query;

				return false;
			}

		}catch(Exception err){
			err.printStackTrace();
			this.msg=err.getMessage();
			return false;
		}
		return true;
	}

	private boolean revertChuyenKho(String chungtuid) {
		// TODO Auto-generated method stub
		try{

			
			String query="";

			// Nếu là đã chuyển kho trạng thái '0','1','2',thì cập nhật kho CHUYỂN lại tăng avai,giam booked


			query=  " SELECT CK.NGAYCHUYEN ,isnull(CK.ISHANGDIDUONG,'0') as ISHANGDIDUONG , CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
					" FROM ERP_CHUYENKHO CK "+ 
					" INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
					" WHERE ck.trangthai in ('0','1','2')  and   CK.PK_SEQ="+chungtuid;
			ResultSet rs=db.get(query);

			while(rs.next()){
				// giam booked_tang_avai
				// cập nhật kho tổng
				String ngaychungtu=rs.getString("NGAYCHUYEN");
				double soluongct=rs.getDouble("SOLUONG");
				String sanpham_fk=rs.getString("sanpham_fk");
				String solo=rs.getString("SOLO");
				String KHU=rs.getString("KHU");
				String trangthaisp=rs.getString("TRANGTHAISP");

				if(KHU==null){
					KHU="";
				}
				String NGAYBATDAU=rs.getString("NGAYBATDAU");
				String VITRI=rs.getString("VITRI");
				String khoXuatId =rs.getString("KHOXUAT_FK");
				double soluong=0;
				double booked= (-1)*soluongct;
				double available=soluongct; 


				if(rs.getString("ISHANGDIDUONG").equals("1")){
					//nếu là hàng đi đường thì kho xuất đã bị trừ,không phải cập nhật booked với avai,lúc chốt đơn hàng thì mới chuyển trạng thái ishangdiduong =1
					soluong=soluongct;
					booked=0;
					available=soluongct;
				}


				String msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,ngaychungtu);
				if(msg1.length() >0){
					this.msg = msg1;
					this.db.getConnection().rollback();
					return false;
				}

				msg1=util_kho.Update_Kho_Sp_Chitiet(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU);
				if(msg1.length() >0){
					this.msg = msg1;
					this.db.getConnection().rollback();
					return false;
				}

				if(util_kho.IsKhoQuanLyTrangThai(khoXuatId,this.db)){
					//neu la kho quan ly trang thai thi them kho trang thai
					msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU, trangthaisp);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

				}


				if(rs.getString("NCC_CHUYEN_FK")!=null){

					msg1=util_kho.Update_Kho_Sp_Chitiet_NCC(this.db,khoXuatId,sanpham_fk,soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

				}

			}
			//nếu là đã hoàn tất thì giảm 2 kho

			//tăng kho xuất
			query=" SELECT CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
					" FROM ERP_CHUYENKHO CK "+ 
					" INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
					" WHERE CK.trangthai in ('3') and CK.PK_SEQ="+chungtuid;
			rs=db.get(query);

			while(rs.next()){
				// tăng  available
				// tăng số lượng kho xuất
				double soluongct=rs.getDouble("SOLUONG");
				String sanpham_fk=rs.getString("sanpham_fk");
				String solo=rs.getString("SOLO");
				String KHU=rs.getString("KHU");
				String khoXuatId=rs.getString("KHOXUAT_FK");
				String trangthaisp=rs.getString("TRANGTHAISP");
				double soluong= soluongct;
				double booked= 0;
				double available= soluongct; 

				if(KHU==null){
					KHU="";
				}
				String NGAYBATDAU=rs.getString("NGAYBATDAU");
				String VITRI=rs.getString("VITRI");
				// giảm số lượng ,giảm bookd,avai giữ nguyên

				String msg1= util_kho.Update_Kho_Sp(db,khoXuatId,sanpham_fk, soluong,booked,available,0);
				if(msg1.length() >0){
					this.msg = msg1;
					this.db.getConnection().rollback();
					return false;
				}

				msg1=util_kho.Update_Kho_Sp_Chitiet(db,khoXuatId,sanpham_fk,  soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU);
				if(msg1.length() >0){
					this.msg = msg1;
					this.db.getConnection().rollback();
					return false;
				}

				if(util_kho.IsKhoQuanLyTrangThai( khoXuatId, db)){
					//neu la kho quan ly trang thai thi them kho trang thai
					msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai(db,khoXuatId,sanpham_fk, soluong,booked,available ,0,solo,VITRI,KHU,NGAYBATDAU, trangthaisp);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

				}

				if(rs.getString("NCC_CHUYEN_FK")!=null){

					msg1=util_kho.Update_Kho_Sp_Chitiet_NCC(db,khoXuatId,sanpham_fk,  soluong,booked,available,0,solo,VITRI,KHU,NGAYBATDAU,rs.getString("NCC_CHUYEN_FK"));
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

				}

			}
			//GIẢM KHO NHẬN
			//tăng kho nhận ,cập nhật giá kho
			query=" SELECT CK.NCC_NHAN_FK,CK.KHONHAN_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
					" FROM ERP_CHUYENKHO CK "+ 
					" INNER JOIN   ERP_CHUYENKHO_SP_NHANHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
					" WHERE CK.trangthai in ('3') and CK.PK_SEQ="+chungtuid;
			//				  rs=db.get(query);

			rs=db.get(query);
			if (rs != null)
			{
				while (rs.next()){
					double soluongct=rs.getDouble("SOLUONG");
					String sanpham_fk=rs.getString("sanpham_fk");
					String solo=rs.getString("SOLO");

					String khukhonhan=rs.getString("KHU");
					if(khukhonhan==null){
						khukhonhan="";
					}

					String KHONHAN_FK=rs.getString("KHONHAN_FK");
					String trangthaisp=rs.getString("TRANGTHAISP");

					String NGAYBATDAU=rs.getString("NGAYBATDAU");
					double soluong=(-1)* soluongct;
					double booked= 0;
					double available=(-1)* soluongct; 
					double dongiamua=0;

					// cập nhật vào kho nhận

					String msg1= util_kho.Update_Kho_Sp( db, KHONHAN_FK,sanpham_fk,soluong,booked,available,0);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					msg1= util_kho.Update_Kho_Sp_Chitiet( db,KHONHAN_FK,sanpham_fk,soluong,booked,available,dongiamua,solo,"",khukhonhan,NGAYBATDAU);
					if(msg1.length() >0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
					}

					// số lượng đạt
					//trangthai=0 là hàng đạt
					if(util_kho.IsKhoQuanLyTrangThai(KHONHAN_FK, db)) {
						msg1= util_kho.Update_Kho_Sp_Chitiet_TrangThai( db,KHONHAN_FK,sanpham_fk,soluong,booked,available,dongiamua,solo,"",khukhonhan,NGAYBATDAU,trangthaisp);
						if(msg1.length() >0){
							this.msg = msg1;
							this.db.getConnection().rollback();
							return false;
						}
					}
					// trường hợp kho gia công 

				}
				rs.close();
			}

			query=" update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + chungtuid + "'";
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_CHUYENKHO: " + query;

				return false;
			}
			//KIỂM TRA CÒN CHUYỂN KHO NÀO KHÔNG?
			//NẾU YÊU CẦU CHƯA CHUYỂN KHO THÌ ĐƯA YÊU CẦU CHUYỂN KHO VỀ CHƯA CHUYỂN NGUYÊN LIỆU
			query= " SELECT PK_SEQ FROM ERP_CHUYENKHO WHERE  " +
					" YCKD_FK=(SELECT yeucauchuyenkho_fk FROM ERP_CHUYENKHO WHERE PK_SEQ="+chungtuid+") AND TRANGTHAI NOT IN ('4') AND PK_SEQ <> "+chungtuid+"";
			rs=db.get(query);
			if(rs.next()){
				// đang có yêu cầu thì để về trạng thái đã chuyển kho
				query="UPDATE ERP_YEUCAUCHUYENKHO SET TRANGTHAI='2' WHERE PK_SEQ =(SELECT yeucauchuyenkho_fk FROM ERP_CHUYENKHO WHERE PK_SEQ="+chungtuid+")  ";

			}else{
				/// đưa về trạng thái đã chốt

				query="UPDATE ERP_YEUCAUCHUYENKHO SET TRANGTHAI='1' WHERE PK_SEQ =(SELECT yeucauchuyenkho_fk FROM ERP_CHUYENKHO WHERE PK_SEQ="+chungtuid+")  ";
			}
			rs.close();
			if(!db.update(query))
			{
				msg = "Khong the cap nhat ERP_YEUCAUCHUYENKHO: " + query;

				return false;
			}


		}catch(Exception err){
			err.printStackTrace();
			this.msg=err.getMessage();
			return false;
		}
		return true;
	}


	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getSochungtu() 
	{
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) 
	{
		this.sochungtu = sochungtu;
	}

	public ResultSet getSochungtuDetail() 
	{
		return this.sochungtuDetail;
	}

	public void setSochugntuDetail(ResultSet soctdetail) 
	{
		this.sochungtuDetail = soctdetail;
	}

	public ResultSet getSochungtuRequest() 
	{
		return this.sochungtuRequest;
	}

	public void setSochungtuRequest(ResultSet soctRequest) 
	{
		this.sochungtuRequest = soctRequest;
	}

	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);	
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}


	public void DbClose() {
		try{
			if (sochungtuRequest != null)
				sochungtuRequest.close();
			if (sochungtuDetail != null)
				sochungtuDetail.close();
			this.db.shutDown();
		}catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getNgayghinhan() {

		return this.Ngayghinhan;
	}


	public void setNgayghinhan(String Ngayghinhan) {

		this.Ngayghinhan=Ngayghinhan;
	}


	public String getSophieutieuhao() {

		return this.tieuhaoId;
	}


	public void setSoPhieutieuhao(String sotieuhao) {

		this.tieuhaoId = sotieuhao;
	}


	public String getSophieukiemdinh() {

		return this.qcId;
	}


	public void setSoPhieukiemdinh(String sokiemdinh) {

		this.qcId = sokiemdinh;
	}

	@Override
	public String getSochungtu_chonhuy() {
		// TODO Auto-generated method stub
		return Sochungtu_chonhuy;
	}

	@Override
	public void setSochungtu_chonhuy(String _Sochungtu_chonhuy) {
		// TODO Auto-generated method stub
		Sochungtu_chonhuy=_Sochungtu_chonhuy;
	}

	@Override
	public String getloaichungtu() {
		// TODO Auto-generated method stub
		return this.loaichungtu;
	}

	@Override
	public void setloaichungtu(String _loaichungtu) {
		// TODO Auto-generated method stub
		this.loaichungtu=_loaichungtu;
	}



}
