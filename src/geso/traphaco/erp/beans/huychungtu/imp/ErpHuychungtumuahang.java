package geso.traphaco.erp.beans.huychungtu.imp;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.huychungtu.IErpHuychungtumuahang;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility_Kho;

public class ErpHuychungtumuahang implements IErpHuychungtumuahang
{
	String congtyId;
	String userId;
	String id;
	String sochungtu;
	String ttnccId;
	String hdId;
	String tieuhaoId;
	String qcId;
	String pnkId;
	String pnhId;
	String dmhId;
	String dnttId;
	String msg;
	String Ngayghinhan;
	String tiente;
	String loaincc;
	
	String loaict;
	String Sochungtu_chonhuy;
	
	ResultSet sochungtuRequest;
	ResultSet sochungtuDetail;
	String Sothuenhapkhau;
	String soxuatkhotrahang="";
	String Sodotrahang=""; 
	String Sohoadontrahang="";
	String Sophieuchuyenkho="";
	String Sophieutieuhao ="";
	String Sohoadondieuchinhnnc="" ;
	String Sochiphinhanhang="";
	

	dbutils db;
	private Utility util;
	private Utility_Kho util_kho=new Utility_Kho();
	public ErpHuychungtumuahang()
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
		this.msg = "";
		this.loaict = "";
		this.Sochiphinhanhang="";
		this.dnttId ="";
		this.Sothuenhapkhau="";
		this.Sophieuchuyenkho="";
		this.util=new Utility();
		this.db = new dbutils();
	}
	
	public ErpHuychungtumuahang(String id)
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
		this.loaict = "";
		this.dnttId ="";
		this.Sothuenhapkhau="";
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
	public String getSochiphinhanhang() {
		return Sochiphinhanhang;
	}

	public void setSochiphinhanhang(String sochiphinhanhang) {
		Sochiphinhanhang = sochiphinhanhang;
	}

	public void createRs()
	{
		this.dmhId = "";
		this.pnhId = "";
		this.pnkId = "";
		this.hdId = "";
		this.ttnccId = "";
		
		String query = "";
		
		if( this.sochungtu.length() > 0 )
		{
							
			if(this.loaict.equals("1"))  //ĐƠN MUA HÀNG
			{

				query = " SELECT A.pk_seq, A.tiente_fk, B.LOAINCC" +
						" FROM   ERP_MUAHANG A INNER JOIN ERP_NHACUNGCAP B ON A.NHACUNGCAP_FK = B.PK_SEQ " +
						" WHERE  SOPO = '"+this.sochungtu+"'";
				
				ResultSet dmhRs = db.get(query);
				String soid = "";
				
				if(dmhRs!=null){
					try {
						if(dmhRs.next()){
							soid = dmhRs.getString("pk_seq");
							tiente = dmhRs.getString("tiente_fk");
							loaincc = dmhRs.getString("LOAINCC");
						}
					} catch (SQLException e) {
						
						e.printStackTrace();
					}
				}
				
				this.dmhId = soid;
				this.createDMH();
			
			}			
			else
			{
				if(this.loaict.equals("2")) //NHAN HANG
				{
					this.pnhId = this.sochungtu.substring(6, this.sochungtu.length());
					this.createNH();
				}
				else
				{
					if(this.loaict.equals("3"))  //KIỂM ĐỊNH CHẤT LƯỢNG
					{
						this.qcId = this.sochungtu.substring(3, this.sochungtu.length());
						this.createQC();
					}
					else
					{
						if(this.loaict.equals("10"))  //Tiêu hao
						{
							this.tieuhaoId = this.sochungtu.substring(3, this.sochungtu.length());
							this.createTieuHao();
						}
						else
						if(this.loaict.equals("4")) // so hoa don
						{
							this.hdId = this.sochungtu.substring(3, this.sochungtu.length());
							this.createHDNCC();
						}
						else 	if(this.loaict.equals("5")){ //ĐỀ NGHỊ THANH TOÁN
							query =
								"SELECT  MUAHANG.PK_SEQ \n"+
								"FROM	 ERP_MUAHANG MUAHANG \n"+  
								"WHERE	 MUAHANG.LOAIHANGHOA_FK = 2 and MUAHANG.TYPE = '1' \n"+
								"		 AND MUAHANG.SOPO = '"+this.sochungtu+"' \n";
							
							ResultSet dnttRs = db.get(query);
							String sodntt = "";
							
							if(dnttRs!=null){
								try {
									if(dnttRs.next()){
										sodntt = dnttRs.getString("pk_seq");
									}
								} catch (SQLException e) {
									
									e.printStackTrace();
								}
							}
							
							this.dnttId = sodntt;
								
							this.createDNTT();
						}
						else if(loaict.equals("6")){
							
							createThuenhapkhau(this.sochungtu);
							this.Sothuenhapkhau=this.sochungtu;
						}else if(loaict.equals("7")){
							create_Phieuxuatkhotrahang(this.sochungtu);
							this.soxuatkhotrahang=this.sochungtu;
							
						}else if(loaict.equals("9")){
							create_dontrahang(this.sochungtu);
							this.Sodotrahang=this.sochungtu;
							
						}else if(loaict.equals("8")){
							create_hoadontrahang(this.sochungtu);
							this.Sohoadontrahang=this.sochungtu;
							
						}else if(loaict.equals("12")){
							create_tieuhaogiacong(this.sochungtu);
							this.Sophieutieuhao =this.sochungtu;
							 
						}else if(loaict.equals("13")){
							create_hoadondieuchinhncc(this.sochungtu);
							this.Sohoadondieuchinhnnc=this.sochungtu;
							 
						}else if(loaict.equals("11")){
							create_chuyenKhoGiaCong(this.sochungtu);
							this.Sophieuchuyenkho=this.sochungtu;
							 
						}
						else if(loaict.equals("14")){
							create_chiphinhanhang(this.sochungtu);
							this.Sochiphinhanhang=this.sochungtu;
							 
						}
					}
					
					
				}
			}
			
		}
	}
	
	private void create_chuyenKhoGiaCong(String sochungtu2) {
		// TODO Auto-generated method stub
	String requestNh=	" SELECT CK.PK_SEQ, CAST(CK.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, CK.NGAYCHUYEN AS NGAYCHUNGTU,   "+
						" CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI, "+   
						" N'CHUYỂN KHO' AS LOAICHUNGTU, CK.NGAYTAO, 5 AS STT ,'CK' as type  "+
						" FROM 	 ERP_CHUYENKHO CK WHERE CK.pk_seq in ("+sochungtu2+" ) AND CK.TRANGTHAI IN (0,1,2,3)  and MUAHANG_FK IS NOT NULL " +
					    " order by stt desc ";
	
			System.out.println("1.Requset nhan hang la: " + requestNh);
			this.sochungtuRequest = db.get(requestNh);
	}

	private void create_hoadondieuchinhncc(String sochungtu2) {
		// TODO Auto-generated method stub
		String requestNh =  " select a.pk_seq,   cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.ngayghinhan as ngaychungtu,   "+
				" case a.TRANGTHAI when 0 then N'Chưa chốt' else N'Đã chốt' end as trangthai, N'Hóa đơn điều chỉnh NCC' as LOAICHUNGTU, "+ 
				" a.NGAYTAO, 1 as stt ,'HDDCNCC' as type "+   
				" from erp_hoadonkhacncc a  "+    
				" where a.trangthai in (1)  and a.PK_SEQ = '" +sochungtu2 + "' ";
					System.out.println("1.Requset nhan hang la: " + requestNh);
					this.sochungtuRequest = db.get(requestNh);
	}

	private void create_chiphinhanhang(String sochungtu2) {
		// TODO Auto-generated method stub
		String requestNh =" select a.pk_seq,   cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.ngay as ngaychungtu, "
				 +"\ncase a.TRANGTHAI when 0 then N'Chưa chốt' else N'Đã chốt' end as trangthai, N'Chi phí nhập khẩu' as LOAICHUNGTU,"
				 +"\na.NGAYTAO, 1 as stt ,'CPNHANHANG' as type  "
				 +"\nfrom ERP_CHIPHINHAPKHAU a      "
				 +"\nwhere a.trangthai in (1)  and a.PK_SEQ = '"+sochungtu2+"'";
					System.out.println("chi phi nhan hang " + requestNh);
					this.sochungtuRequest = db.get(requestNh);
	}
	
	private void create_tieuhaogiacong(String sochungtu2) {
		// TODO Auto-generated method stub
		String requestNh =  " select a.pk_seq,   cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.ngaytieuhao as ngaychungtu,   "+
				" case a.TRANGTHAI when 0 then N'Chưa chốt' else N'Đã chốt' end as trangthai, N'Tiêu hao gia công' as LOAICHUNGTU, "+ 
				" a.NGAYTAO, 1 as stt ,'THGC' as type  "+   
				" from erp_tieuhao a  "+    
				" where a.trangthai in (1,0)  and a.PK_SEQ = '" +sochungtu2 + "' ";
					System.out.println("1.Requset nhan hang la: " + requestNh);
					this.sochungtuRequest = db.get(requestNh);
	}

	private void create_hoadontrahang(String sochungtu2) {
		// TODO Auto-generated method stub
		String requestNh =  " select a.pk_seq,   cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYXUATHD as ngaychungtu,   "+
							" case a.TRANGTHAI when 0 then N'Chưa chốt' else N'Đã chốt' end as trangthai, N'Hóa đơn  hàng trả về' as LOAICHUNGTU, "+ 
							" a.NGAYTAO, 1 as stt ,'HDTRA' as type "+   
							" from erp_hoadon a "+    
							" where a.trangthai in (1)  and a.PK_SEQ = '" +sochungtu2 + "' ";
			System.out.println("1.Requset nhan hang la: " + requestNh);
			this.sochungtuRequest = db.get(requestNh);
	}

	private void create_dontrahang(String sochungtu2) {
		// TODO Auto-generated method stub
		String requestNh = " select a.pk_seq,   cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYMUA as ngaychungtu,  " +
		" case a.TRANGTHAI when 0 then N'Chưa chốt' else N'Đã chốt' end as trangthai, N'Đơn hàng trả về' as LOAICHUNGTU, a.NGAYTAO, 1 as stt ,'DONTH' as type  " +
	     " from erp_muahang a   " +
	   " where a.trangthai in (1)  and a.PK_SEQ = '" +sochungtu2 + "' ";

		System.out.println("1.Requset nhan hang la: " + requestNh);

			this.sochungtuRequest = db.get(requestNh);
	}

	private void create_Phieuxuatkhotrahang(String sochungtu2) {
		// TODO Auto-generated method stub
		String requestNh = " select a.pk_seq,   cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.ngayxuat as ngaychungtu,  " +
			" case a.TRANGTHAI when 0 then N'Chưa chốt' else N'Đã chốt' end as trangthai, N'Xuất kho trả về' as LOAICHUNGTU, a.NGAYTAO, 1 as stt ,'XKTRA' as type  " +
		   " from erp_xuatkho a   " +
		   " where a.trangthai in (1)  and a.PK_SEQ = '" +sochungtu2 + "' ";
 
			System.out.println("1.Requset nhan hang la: " + requestNh);

		this.sochungtuRequest = db.get(requestNh);
	}

	private void createTieuHao() 
	{
		String requestNh = " select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYTIEUHAO as ngaychungtu,  " +
								" case a.TRANGTHAI when 0 then N'Chưa tiêu hao' else N'Đã tiêu hao' end as trangthai, N'Tiêu hao gia công' as LOAICHUNGTU, a.NGAYTAO, 1 as stt ,'THGC' as type  " +
						   "from ERP_TIEUHAO a   " +
						   "where a.trangthai in (0, 1)  and a.PK_SEQ = '" + this.tieuhaoId + "' ";
		
		requestNh += " order by stt desc ";
		
		System.out.println("1.Requset nhan hang la: " + requestNh);
		
		this.sochungtuRequest = db.get(requestNh);
		
	}

	private void createQC() 
	{
		String requestNh = "";
		String requestTieuhao = "";
		String requestQC = "";
		String requestHDNCC = "";
		String requestTTHDNCC = "";
		
		String requestTNK = "";
		String query = "";
		
		//1. KIỂM TRA XEM KIỂM ĐỊNH CÓ THANH TOÁN HÓA ĐƠN CHƯA
		
		/*requestTTHDNCC ="SELECT a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu, a.ngayghinhan as ngaychungtu, \n "+   
					   	"		case a.trangthai when 0 then N'Chưa duyệt' when 1 then N'Đã duyệt' else N'NA' end as trangthai, N'Thanh toán Hóa đơn' as loaichungtu, a.ngaytao, 3 as stt \n "+    
					   	"FROM   ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_HOADON b on a.PK_SEQ = b.THANHTOANHD_FK \n "+   
					   	"		INNER JOIN ERP_HOADONNCC c on b.HOADON_FK = c.pk_seq \n "+ 
					   	"		INNER JOIN ERP_HOADONNCC_PHIEUNHAP d on c.pk_seq = d.hoadonncc_fk \n "+
					   	"WHERE  a.TRANGTHAI in ( 0, 1 ) AND d.phieunhan_fk IN (SELECT nhanhang_fk FROM ERP_YeuCauKiemDinh WHERE pk_seq ='"+this.qcId+"' and trangthai in (0,1)) \n";
	   
		System.out.println("query thanh toan hoa don:"+ requestTTHDNCC);
		//2. KIỂM TRA XEM KIỂM ĐỊNH CÓ HÓA ĐƠN NHÀ CUNG CẤP 
		
		requestHDNCC =  " SELECT a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu, a.ngayghinhan as ngaychungtu, \n " +
						"		 case a.trangthai when 0 then N'Chưa duyệt' when 1 then N'Đã duyệt' when 2 then N'Hoàn tất' else N'NA' end as trangthai, N'PARK Hóa đơn' as loaichungtu, a.ngaytao,2 as stt   \n" +
						" FROM 	 ERP_PARK a inner join ERP_HOADONNCC b on a.pk_seq = b.park_fk  \n" +
						"		 INNER JOIN ERP_HOADONNCC_PHIEUNHAP c on b.pk_seq = c.hoadonncc_fk  \n" +
						" WHERE  c.phieunhan_fk  IN (SELECT nhanhang_fk FROM ERP_YeuCauKiemDinh WHERE pk_seq ='"+qcId+"' and trangthai in (0,1)) \n"+
						"		 and a.TRANGTHAI != 3 \n";
		
		System.out.println("query hoa don ncc:"+ requestHDNCC);*/
		
		// 3. KIỂM ĐỊNH
		requestNh = 	" SELECT a.pk_seq, '200' + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, isnull(a.ngaykiem,a.ngaynhan) as ngaychungtu,  \n" +
						" 		 case a.TRANGTHAI when 0 then N'Chờ kiểm' else N'Đã kiểm' end as trangthai, N'Kiểm định chất lượng' as LOAICHUNGTU, a.NGAYTAO, 1 as stt ,'KDCL' as type  \n " +
						" FROM   ERP_YeuCauKiemDinh a   \n" +
						" WHERE  a.trangthai in (0, 1, 2 )  and a.PK_SEQ = '" + this.qcId + "' \n "+
						" union all " +
						" SELECT CK.PK_SEQ, CAST(CK.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, CK.NGAYCHUYEN AS NGAYCHUNGTU,   "+
						" CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI, "+   
						" N'CHUYỂN KHO' AS LOAICHUNGTU, CK.NGAYTAO, 5 AS STT ,'CK' as type  "+
						" FROM 	 ERP_CHUYENKHO CK WHERE CK.YCKD_FK in ("+this.qcId+" ) AND CK.TRANGTHAI IN (0,1,2,3) " +
					    " order by stt desc ";
 
						System.out.println("1.Requset nhan hang la 1: " + requestNh);
						
						this.sochungtuRequest = db.get(requestNh);
		
	}

	private void createTTHDNCC() 
	{
		String requestNh = " select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.ngayghinhan as ngaychungtu,  " +
								"case a.TRANGTHAI when 1 then N'Đã chốt' else N'Hoàn tất' end as trangthai, N'PARK Hóa đơn' as LOAICHUNGTU, a.NGAYTAO, 1 as stt   " +
						   "from ERP_THANHTOANHOADON a   " +
						   "where a.trangthai in (1, 2)  and a.PK_SEQ = '" + this.ttnccId + "' ";
		
		requestNh += " order by stt desc ";
		
		System.out.println("1.Requset nhan hang la: " + requestNh);
		
		this.sochungtuRequest = db.get(requestNh);
	}

	
	private void createHDNCC() 
	{
		try
		{
			String requestNh ="";
			String requestTTHDNCC = "";
			//lay hoa don
			requestTTHDNCC =  " select  a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu, a.ngayghinhan as ngaychungtu, a.NGAYTAO AS NGAYTAO, "+    
							 "\n	 case when a.trangthai =0  then  N'Chưa chốt' "+    
							 "\n	 when a.trangthai =1 then  N'Đã chốt' "+ 
							 "\n 	 when a.trangthai =2 and HOANTAT.SODEM <> 0 and THANHTOAN.PARK_FK IS not NULL then  N'Đã thanh toán'  "+    
							 "\n	 when a.trangthai =2 and HOANTAT.SODEM <> 0 and THANHTOAN.PARK_FK IS NULL then  N'Đã duyệt'  "+    
							 "\n	 when a.trangthai =2 and HOANTAT.SODEM = 0 or a.trangthai =3 then  N'Hoàn tất' "+
							 "\n	 else N'NA' "+    
							 "\n	 end as trangthai, N'Duyệt hóa đơn NCC' as LOAICHUNGTU , 1 as stt,'HD' as type  "+    
							 "\n	 from  "+    
							 "\n	 ERP_PARK a  inner join  ERP_HOADONNCC b on b.park_fk=a.pk_seq "+    
							 "\n 	 LEFT JOIN 	(   \n" +  
								"   				 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
								"					 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
								"							 FROM ERP_HOADONNCC   \n" +
								"							 WHERE CONGTY_FK = "+this.congtyId +
								"   				 		 GROUP BY PARK_FK) AS A  	\n" +
								"							 LEFT JOIN   	\n" +
								"							(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
								"							 FROM ERP_HOADONNCC  	 \n" +  
								"   				 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "+this.congtyId+
								"							 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
								"  					) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  
								"  	  LEFT JOIN 	(  \n" +  
								"   				SELECT 	DISTINCT HD.PARK_FK 		\n"+
								"   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
								"   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
								"   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+this.congtyId+"  )  \n"+
								"							AND HD.CONGTY_FK = "+this.congtyId +
								"  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +    
							 "\n	 where a.trangthai not in (4) and a.pk_seq= '"+ this.hdId +"' ";
			
			System.out.println("___Lay thanh toan HoaDon: " + requestTTHDNCC +"\n");
			
			
			//lay nhan hang
			 requestNh=" SELECT NH.pk_seq, b.PREFIX + nh.PREFIX + cast(nh.pk_seq as varchar(20)) as SOCHUNGTU, PARK.ngayghinhan as ngaychungtu,NH.NGAYTAO AS NGAYTAO, "+  
			 " 		case nh.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' else N'Hoàn tất' end as trangthai, N'Nhận hàng' as LOAICHUNGTU, 2 as stt, 'NH' as type  " +
			 "\n	 FROM 	  ERP_PARK PARK INNER JOIN ERP_HOADONNCC HD ON HD.PARK_FK= PARK.PK_SEQ   \n	INNER JOIN ERP_NHANHANG NH ON NH.HDNCC_FK = HD.PK_SEQ "+    
			 "\n	 inner join erp_donvithuchien b on nh.donvithuchien_fk = b.pk_seq  "+       
			 "\n	 WHERE PARK.trangthai NOT IN (4) AND NH.TRANGTHAI NOT IN (3,4) "+    
			 "\n	 AND PARK.pk_seq='"+ this.hdId + "'";
			 
			if(requestTTHDNCC.length() > 0)
				requestNh += "\n union all  \n" + requestTTHDNCC;
			
			requestNh += " order by stt desc ";
			
			System.out.println("1.Requset hoa don ncc: \n " + requestNh +" \n ");
			
			this.sochungtuRequest = db.get(requestNh);
			
		}
		catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		
	}


	private void createThuenhapkhau(String thuenhapkhauid) 
	{
		try
		{
			String requestNh ="";
		 
			
			//lay nhan hang
			 requestNh=  " 		 SELECT NH.pk_seq, b.PREFIX + nh.PREFIX + cast(nh.pk_seq as varchar(20)) as SOCHUNGTU, PARK.ngayghinhan as ngaychungtu,NH.NGAYTAO AS NGAYTAO, "+  
						 " 		 case nh.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' else N'Hoàn tất' end as trangthai, N'Nhận hàng' as LOAICHUNGTU, 0 as stt, 'NH' as type  " +
						 "\n	 FROM 	  ERP_PARK PARK INNER JOIN ERP_HOADONNCC HD ON HD.PARK_FK= PARK.PK_SEQ   \n	INNER JOIN ERP_NHANHANG NH ON NH.HDNCC_FK = HD.PK_SEQ "+    
						 "\n	 inner join erp_donvithuchien b on nh.donvithuchien_fk = b.pk_seq  "+       
						 "\n	 WHERE PARK.trangthai NOT IN (4) AND NH.TRANGTHAI NOT IN (3,4) "+    
						 "\n	 AND HD.PK_SEQ IN (select HOADONNCC_FK from ERP_THUENHAPKHAU_HOADONNCC WHERE THUENHAPKHAU_FK="+thuenhapkhauid+") " +
						 		"  union  " +
						 		"  SELECT a.pk_seq,   cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYCHUNGTU as ngaychungtu,a.NGAYTAO AS NGAYTAO,   "+
						 		" case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt thuế nhập khẩu' when 2 then N'Đã chốt thuế VAT' "+ 
						 		" else N'Đã hủy' end as trangthai, N'Thuế nhập khẩu' as LOAICHUNGTU, 1 as stt, 'TNK' as type "+
						 		" from ERP_THUENHAPKHAU a where a.PK_SEQ= "+thuenhapkhauid+" " +
						 				" order by stt "; 
			  
			
			System.out.println("1.Requset thue nhap khau: \n " + requestNh +" \n ");
			
			this.sochungtuRequest = db.get(requestNh);
			
		}
		catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
		
	}

	
 	private void createNH() 
	{
		try
		{
			 
			String requestQC = "";
		 
			String query = "";
			
			//1.KIỂM TRA XEM NHẬN HÀNG CHO ĐƠN MUA HÀNG NỘI ĐỊA HAY NGOẠI TỆ
			
			query = 
				" SELECT a.pk_seq, b.tiente_fk "+				
				" FROM   erp_nhanhang a inner join erp_muahang b on a.muahang_fk = b.pk_seq " +
				" WHERE  a.PK_SEQ = '" + this.pnhId + "' ";
			
			ResultSet nhRs = db.get(query);
			
			if(nhRs!=null){
				try {
					if(nhRs.next()){
						tiente = nhRs.getString("tiente_fk");
					}
				} catch (SQLException e) {
					
					e.printStackTrace();
				}
			}
			
		 
			
			String requestNh = " SELECT a.pk_seq, b.PREFIX + a.PREFIX + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, a.NGAYNHAN as ngaychungtu, " +
							   " 		case a.TRANGTHAI when 0 then N'Chưa chốt' when 1 then N'Đã chốt' else N'Hoàn tất' end as trangthai, N'Nhận hàng' as LOAICHUNGTU, a.NGAYTAO, 1 as stt ,'NH' as type " +
							   " FROM 	erp_nhanhang a inner join erp_donvithuchien b on a.donvithuchien_fk = b.pk_seq " +
							   " WHERE  a.congty_fk = '"+ this.congtyId +"' and a.trangthai in ( 0, 1, 2 ) " +
							   " 		and a.PK_SEQ = '" + this.pnhId + "' ";
			
			 
			requestQC = " 		 SELECT a.pk_seq, '200' + cast(a.pk_seq as varchar(20)) as SOCHUNGTU, isnull(a.ngaykiem,a.ngaynhan) as ngaychungtu,  " +
						" 		 case a.TRANGTHAI when 0 then N'Chờ kiểm' else N'Đã kiểm' end as trangthai,  " +
						"		 N'Kiểm định chất lượng' as LOAICHUNGTU, a.NGAYTAO, 4 as stt ,'KDCL' as type " +
						" 		 FROM 	 ERP_YeuCauKiemDinh a  " +
						" 		 WHERE  a.nhanhang_fk in ( " + this.pnhId + " )  and a.trangthai in (1,2)  ";

				System.out.println("___Phieu kiem dinh: " + requestQC);	

				
	String	requestCK = " SELECT CK.PK_SEQ, CAST(CK.PK_SEQ AS VARCHAR(20)) AS SOCHUNGTU, CK.NGAYCHUYEN AS NGAYCHUNGTU,   "+
						" CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI, "+   
						" N'CHUYỂN KHO' AS LOAICHUNGTU, CK.NGAYTAO, 5 AS STT  ,'CK' as type  "+
						" FROM 	 ERP_CHUYENKHO CK WHERE CK.YCKD_FK in (select pk_seq from ERP_YeuCauKiemDinh where nhanhang_fk in ( " + this.pnhId + " )   ) AND CK.TRANGTHAI IN (0,1,2,3)  ";

		System.out.println("___Phieu kiem dinh: " + requestQC);	
				
 
			requestNh += " union all " + requestQC;
			
		 
			requestNh += " union all " + requestCK;
						
			requestNh += " order by stt desc ";
			
			System.out.println("1.Requset nhan hang la: " + requestNh);
			
			this.sochungtuRequest = db.get(requestNh);
			
		}
		catch (Exception e) {
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	private void createDMH() 
	{
		try
		{
			//check hoa don --> huy don mua hang 
			String requestHDNCC = "";
			//3.HÓA ĐƠN NCC
			
			requestHDNCC =  " select distinct  a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu, a.ngayghinhan as ngaychungtu,  "+    
			 "\n	 case when a.trangthai =0  then  N'Chưa chốt' "+    
			 "\n	 when a.trangthai =1 then  N'Đã chốt' "+ 
			 "\n 	 when a.trangthai =2 and HOANTAT.SODEM <> 0 and THANHTOAN.PARK_FK IS not NULL then  N'Đã thanh toán'  "+    
			 "\n	 when a.trangthai =2 and HOANTAT.SODEM <> 0 and THANHTOAN.PARK_FK IS NULL then  N'Đã duyệt'  "+    
			 "\n	 when a.trangthai =2 and HOANTAT.SODEM = 0 or a.trangthai =3 then  N'Hoàn tất' "+
			 "\n	 else N'NA' "+    
			 "\n	 end as trangthai, N'Duyệt hóa đơn NCC' as LOAICHUNGTU ,a.NGAYTAO AS NGAYTAO, 1 as stt,'HD' as type  "+    
			 "\n	 from  "+    
			 "\n	 ERP_PARK a  inner join  ERP_HOADONNCC b on b.park_fk=a.pk_seq "+ 
			 "\n 	 INNER JOIN ERP_HOADONNCC_DONMUAHANG HDNCC ON HDNCC.HOADONNCC_FK=b.pk_seq "+
			 "\n 	 LEFT JOIN 	(   \n" +  
				"   				 SELECT A.PARK_FK , (DEMA - ISNULL (DEMB,0)) AS SODEM \n" +
				"					 FROM 	(SELECT PARK_FK , COUNT(PARK_FK) AS DEMA  	\n" +
				"							 FROM ERP_HOADONNCC   \n" +
				"							 WHERE CONGTY_FK = "+this.congtyId +
				"   				 		 GROUP BY PARK_FK) AS A  	\n" +
				"							 LEFT JOIN   	\n" +
				"							(SELECT PARK_FK , COUNT(PARK_FK) AS DEMB  	\n" +
				"							 FROM ERP_HOADONNCC  	 \n" +  
				"   				 		 WHERE TRANGTHAI = 2 AND CONGTY_FK = "+this.congtyId+
				"							 GROUP BY PARK_FK) AS B ON B.PARK_FK = A.PARK_FK  \n" +  
				"  					) HOANTAT ON HOANTAT.PARK_FK = A.PK_SEQ   	 \n" +  
				"  	  LEFT JOIN 	(  \n" +  
				"   				SELECT 	DISTINCT HD.PARK_FK 		\n"+
				"   				FROM 	ERP_THANHTOANHOADON_HOADON TT		\n"+   
				"   					 	INNER JOIN ERP_HOADONNCC HD ON HD.PK_SEQ = TT.HOADON_FK AND TT.LOAIHD = 0 \n"+
				"   				WHERE 	TT.THANHTOANHD_FK IN (SELECT PK_SEQ FROM ERP_THANHTOANHOADON WHERE TRANGTHAI != 2 AND CONGTY_FK = "+this.congtyId+"  )  \n"+
				"							AND HD.CONGTY_FK = "+this.congtyId +
				"  					) THANHTOAN ON THANHTOAN.PARK_FK = A.PK_SEQ " +    
			 "\n	 where a.trangthai not in (4) and HDNCC.MUAHANG_FK= '"+ this.dmhId +"' ";
			
				System.out.println("___Lay hoa don NCC: " + requestHDNCC);
			
			  	
				//Chi lay don mua hang da chot tro len
				String requestMh = 	"\n SELECT a.pk_seq, a.SOPO as SOCHUNGTU, a.NGAYMUA as ngaychungtu, " +
									"\n case a.TRANGTHAI when 1 then N'Đã chốt' else N'Hoàn tất' end as trangthai, N'Đơn mua hàng' as LOAICHUNGTU, a.NGAYTAO, 0 as stt ,'MH' as type" +
									"\n FROM   erp_muahang a inner join erp_donvithuchien b on a.donvithuchien_fk = b.pk_seq " +
									"\n WHERE  a.congty_fk = '"+this.congtyId+"' and a.trangthai in (1, 2) and a.PK_SEQ = '" + this.dmhId + "' ";
			
		
			if(requestHDNCC.length() > 0)
				requestMh += "\n union all \n" + requestHDNCC;
		
			requestMh += " order by stt desc ";
			
			System.out.println("1.Requset don mua hang: " + requestMh);
			
			this.sochungtuRequest = db.get(requestMh);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}

	private void createDNTT() 
	{
		try
		{
			String query =
				//NHỮNG ĐỀ NGHỊ THANH TOÁN ĐÃ CHỐT
				"SELECT  MUAHANG.PK_SEQ, MUAHANG.SOPO SOCHUNGTU, MUAHANG.NGAYMUA NGAYCHUNGTU, MUAHANG.TONGTIENAVAT, ISNULL( NCC.TEN,NV1.TEN) AS NCC, N'Đề nghị thanh toán' as LOAICHUNGTU, 1 STT," +
				"        CASE MUAHANG.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN -1 THEN N'CHƯA DUYỆT' WHEN 1 THEN N'ĐÃ DUYỆT' WHEN 2 THEN N'HOÀN TẤT' ELSE N'ĐÃ HỦY' END TRANGTHAI, MUAHANG.NGAYTAO, 'DNTT' type \n"+
				"FROM	ERP_MUAHANG MUAHANG \n"+ 
				"		LEFT JOIN NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK \n"+
				"		LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MUAHANG.NHANVIEN_FK \n"+ 
				"WHERE	 MUAHANG.LOAIHANGHOA_FK = 2 and MUAHANG.TYPE = '0' \n"+
				"		AND MUAHANG.SOPO = '"+this.sochungtu+"' \n"+
						
				"UNION ALL \n"+
				
				//PHIẾU CHI CÓ LIÊN QUAN ĐẾN ĐỀ NGHỊ THANH TOÁN 
				"SELECT TTHD.PK_SEQ, CAST (TTHD.PK_SEQ AS NVARCHAR(50)) SOCHUNGTU, TTHD.NGAYGHINHAN NGAYCHUNGTU, TTHD.SOTIENTT, ISNULL( NCC.TEN,NV1.TEN) AS NCC, N'Phiếu chi' as LOAICHUNGTU, 0 STT," +
				"       CASE	TTHD.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT'  END  TRANGTHAI, TTHD.NGAYTAO, 'DNTT' type \n"+
				"FROM   ERP_MUAHANG MUAHANG \n"+
				"	    INNER JOIN ERP_THANHTOANHOADON_HOADON TT ON MUAHANG.PK_SEQ = TT.HOADON_FK  AND TT.LOAIHD = 6 \n"+
				"	    INNER JOIN ERP_THANHTOANHOADON TTHD ON TT.THANHTOANHD_FK = TTHD.PK_SEQ \n"+
				"       LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK \n"+
				"       LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MUAHANG.NHANVIEN_FK \n"+
				"WHERE  TTHD.TRANGTHAI IN (0,1) \n"+
				"		AND MUAHANG.SOPO = '"+this.sochungtu+"' \n"+
				"ORDER BY STT ASC \n";
			
			System.out.println("1.search: " + query);
			
			this.sochungtuRequest = db.get(query);
		
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("Exception : " + e.getMessage());
		}
	}
	
	public void init() 
	{
		String query = "select   LOAICHUNGTU, ngayghinhan, SOCHUNGTU, trangthai,ngaytao, nguoitao, ngaysua, nguoisua, congty_fk  " +
				"  from erp_huychungtumuahang where pk_seq = '" + this.id + "'";
		System.out.println(" init: "+ query);
		ResultSet rs = db.get(query);
		try
		{
			while(rs.next())
			{
				 this.loaict=rs.getString("LOAICHUNGTU");
				 this.Ngayghinhan=rs.getString("ngayghinhan");
				 this.sochungtu=rs.getString("SOCHUNGTU");
				 
			}
			rs.close();
			
			query = "select  sochungtu as pk_Seq, ngaychungtu, trangthai, loaichungtu, ngaytao, thutu as stt,type from erp_huychungtumuahang_chungtu " +
					"where hctmh_fk = '" + this.id + "' order by thutu desc, sochungtu desc";
			
			System.out.println(" init danh sach: "+ query);
			this.sochungtuRequest = db.get(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		
	}


	public boolean createHct(String[] socthuy, String[] soct, String[] ngaychungtu, String[] trangthai, String[] loaichungtu, String[] ngaytao, String[] thutu,String type[],String chon[])
	{
		 
		if( this.Ngayghinhan.trim().length() <= 0 )
		{
			this.msg = "Bạn phải chọn ngày ghi nhận.";
			return false;
		}
		
		if(this.loaict.trim().length()<=0){
			this.msg = "Bạn phải chọn loại chứng từ ";
			return false;
		}
		
		if(this.sochungtu.trim().length() <= 0)
		{
			this.msg = "Không có chứng từ hủy nào được chọn";
			return false;
		}
		String msg1=util.checkNgayHopLe(db,this.Ngayghinhan);
		if(msg1.length() >0)
		{
			this.msg = msg1;
			return false;
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			//B1.KIỂM TRA XEM CHỨNG TỪ NÀY ĐÃ HỦY CHƯA
			String query =  
			" SELECT count(pk_seq) as sodong" +
	        " FROM 	 erp_huychungtumuahang " +
			" WHERE  trangthai != '2' and sochungtu = '" + this.sochungtu + "'" +
			" and loaichungtu = '"+ this.loaict +"' and loaichungtu not in (3) ";
			
			//System.out.println("CHECK: " + query+"\n"); 
			//System.out.println("LOAI CHUNG TU GOC: " + this.loaict +"\n"); 
			
			ResultSet rsCheck = db.get(query);
			
			int count = 0;
			if(rsCheck.next())
			{
				count = rsCheck.getInt("sodong");
				rsCheck.close();
			}
			
			if(count > 0)
			{
				this.msg = "Số chứng từ này đã hủy rồi, vui lòng nhập số chứng từ khác";
				System.out.println(this.msg);
				return false;
			}
			
			
			

			//B2.MẶC ĐỊNH TẠO MỚI XONG SẼ CHỐT LUÔN
			
			query =  " INSERT INTO ERP_HUYCHUNGTUMUAHANG ( LOAICHUNGTU, NGAYGHINHAN, SOCHUNGTU, TRANGTHAI,NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, CONGTY_FK) " +
					 " VALUES('"+this.loaict+"','" + this.Ngayghinhan + "', '" + this.sochungtu + "', '1','" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "',"+this.congtyId+")";
			
			
			System.out.println("INSERT REVERT: " + query);
			
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the tao moi ERP_HUYCHUNGTUMUAHANG, " + query;
				return false;
			}
			
			String hctbhCurrent = "";
			query = "select IDENT_CURRENT('ERP_HUYCHUNGTUMUAHANG') as hctbhId";
			
			ResultSet rsTthd = db.get(query);						
			if(rsTthd.next())
			{
				hctbhCurrent = rsTthd.getString("hctbhId");
				rsTthd.close();
			}
			
			this.id = hctbhCurrent;
			int dem=0;
			
			System.out.println("so chung tu huy "+socthuy.length);
			if(socthuy != null)
			{
				System.out.println("so luong so ct huy: "+soct.length);
				for(int i = 0; i < soct.length; i++)
				{
				  System.out.println(" chon: "+chon[i]);
				  
					if(chon[i].equals("1")){
						query = " insert into ERP_HUYCHUNGTUMUAHANG_CHUNGTU(hctmh_fk, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu,TYPE) " +
								" values('" + hctbhCurrent + "', '" + thutu[i] + "',  '" + soct[i] + "', '" + ngaychungtu[i] + "', N'" + trangthai[i] + "', N'" + loaichungtu[i] + "','"+type[i]+"')";
						
						if(!db.update(query))
						{
							db.getConnection().rollback();
							this.msg = "Không thể tạo mới ERP_HUYCHUNGTUMUAHANG_CHUNGTU, " + query;
							return false;
						}
						
						if(this.loaict.equals("4"))// huy hoa don
						{
							//check trang thai da chot moi cho huy ===> loai trang thai chua chot (0)
							query=" SELECT TRANGTHAI FROM ERP_PARK WHERE pk_seq= "+soct[i];
							ResultSet rs=db.get(query);
							String tt="";
							if(rs!=null){
								try {
									while(rs.next()){
										tt=rs.getString("TRANGTHAI");
									}
									rs.close();
								} catch (Exception e) {
								e.printStackTrace();
								}
							}
							if(tt.equals("0")){
								 this.msg = " Chứng từ này chưa chốt vui lòng kiểm tra lại ";
								 return false;
							}

							
							//check nhan hang
							String sms=Check_nhanhang_HDTC(db,soct[i]);
							if(sms.length()>0){
								this.msg = " Trước khi hủy hóa đơn này, vui lòng hủy các phiếu nhận hàng  với số chứng từ sau : "+sms;
								return false;
							}
							
							//check da thanh toan
							String sms1=Check_thanhtoan_HDTC(db,soct[i]);
							if(sms1.length()>0){
								this.msg = " Trước khi hủy hóa đơn này, vui lòng hủy các hóa đơn đã thanh toán với số chứng từ sau : "+sms1;
								return false;
							}
							
							//check da thanh toan
							  sms1=Check_ToKhai_HDTC(db,soct[i]);
							if(sms1.length()>0){
								this.msg = " Trước khi hủy hóa đơn này, vui lòng hủy hóa đơn đã có số tờ khai với số chứng từ sau : "+sms1;
								return false;
							}

							// revert ke toan khi duyệt hóa đơn 
							String  sms3= Revert_KeToan_loaichungtu(db,"Duyệt hóa đơn NCC",soct[i]);
							 if(sms3.length()>0){
								 db.getConnection().rollback();
								 this.msg=sms3;
								 return false;
							 }
							 
							 
							 //đưa hóa đơn về trạng thái hủy
							 query=" UPDATE ERP_PARK   SET TRANGTHAI=4  where pk_seq="+soct[i];
								if(!db.update(query)){
									db.getConnection().rollback();
									this.msg= "không thể cập nhật: "+query;
								}
								
							query=" UPDATE ERP_HOADONNCC   SET TRANGTHAI=4  where park_fk="+soct[i];
								if(!db.update(query)){
									db.getConnection().rollback();
									this.msg= "không thể cập nhật: "+query;
								}
							 
						}
						
						else{
					
								if(!this.RevertChungtu (db, type[i],soct[i], this.loaict)){
									db.getConnection().rollback();
									return false;
								}
						}
						dem=dem+1;
					} 
					
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
			 this.msg=e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}
	
	public boolean huychungtu_nhanhang(String nhid){
		
		try
		{
			db.getConnection().setAutoCommit(false);
			 	
			if(!this.RevertChungtu (db, "NH",nhid, "2")){
				db.getConnection().rollback();
				return false;
			}
					 
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			return true;
		}
		catch (Exception e)
		{
			 e.printStackTrace();
			 this.msg=e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
	}
	public static void main ( String args [  ]  )   {
		 ErpHuychungtumuahang hctmhBean =new ErpHuychungtumuahang();
		 
		/* String mang[]=new String[]{"106590","106587","106613","106604","106610","106607","106602","106596","106600","106598","106608","106601","106597","106609"};
		 
		 for(int i=0;i<mang.length;i++)
		 {
			 hctmhBean.huychungtu_nhanhang(mang[i]);
			 
		 }*/
		 hctmhBean.huychungtu_nhanhang("106590");
		
	}
	private String Check_ToKhai_HDTC(dbutils db2, String string) {
		// TODO Auto-generated method stub
		String s="";
	 
		
		String qr="	select a.pk_seq,  cast(a.pk_seq as varchar(20)) + a.SOCHUNGTU   as sochungtu  "
				+ "  from ERP_THUENHAPKHAU  a inner join ERP_THUENHAPKHAU_HOADONNCC b on a.PK_SEQ = b.THUENHAPKHAU_FK   "
				+ " inner join ERP_HOADONNCC c on b.HOADONNCC_FK = c.pk_seq  "
				+ " where c.park_fk = '" + this.hdId + "'  and a.TRANGTHAI in ( 0, 1,2 ) ";
		System.out.println(" check_thanhtoan: "+qr +"\n");
		int dem=0;
		ResultSet rs =db.get(qr);
		if(rs!=null){
			try {
				while(rs.next()){
					dem++;
					s += rs.getString("sochungtu") + " ; " ;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(dem >0)
		return s;
		else
			return "";
	}

	private String  Check_nhanhang_HDTC(dbutils db, String soct){
		String s="";
		String  qr=" SELECT  b.PREFIX + nh.PREFIX + cast(nh.pk_seq as varchar(20)) as SOCHUNGTU_NH  FROM "+    
			 "\n	 ERP_PARK PARK INNER JOIN ERP_HOADONNCC HD ON HD.PARK_FK= PARK.PK_SEQ " +
			 "  	INNER JOIN ERP_NHANHANG NH ON NH.HDNCC_FK = HD.PK_SEQ "+    
			 "\n	 inner join erp_donvithuchien b on nh.donvithuchien_fk = b.pk_seq  "+       
			 "\n	 WHERE PARK.trangthai NOT IN (4) AND NH.TRANGTHAI NOT IN (3,4) "+    
			 "\n	 AND PARK.pk_seq='"+ this.hdId + "'";
		System.out.println(" check_nhanhang: "+qr +"\n");
		int dem=0;
		ResultSet rs =db.get(qr);
		if(rs!=null){
			try {
				while(rs.next()){
					dem++;
					s += rs.getString("SOCHUNGTU_NH") + " ; " ;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(dem >0)
		return s;
		else
			return "";
		
	}
	
	private String  Check_HDTC_DonMH(dbutils db){
		String s="";
		String qr =    
			   "\n SELECT distinct  a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu   \n"+
			   "\n FROM   ERP_PARK a INNER JOIN ERP_HOADONNCC b on a.pk_seq = b.park_fk  \n" +
			   "\n INNER JOIN ERP_HOADONNCC_DONMUAHANG c on b.pk_seq = c.hoadonncc_fk  \n" +
			   "\n WHERE  c.muahang_fk = '" + this.dmhId + "' and a.TRANGTHAI != 4 \n";
		
		
		System.out.println(" Check_HDTC_DonMH: "+qr +"\n");
		int dem=0;
		ResultSet rs =db.get(qr);
		if(rs!=null){
			try {
				while(rs.next()){
					dem++;
					s += rs.getString("sochungtu") + " ; " ;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(dem >0)
		return s;
		else
			return "";
		
	}
	
	
	private String  Check_thanhtoan_HDTC(dbutils db, String soct){
		
		String s="";
		String  qr=  " select a.pk_seq, a.PREFIX + cast(a.pk_seq as varchar(20)) as sochungtu   " +
		  "\n from ERP_THANHTOANHOADON a inner join ERP_THANHTOANHOADON_HOADON b on a.PK_SEQ = b.THANHTOANHD_FK  " +
		  "\n inner join ERP_HOADONNCC c on b.HOADON_FK = c.pk_seq and B.loaihd = 0  " +
		  "\n where c.park_fk = '" + this.hdId + "' and a.TRANGTHAI in ( 0, 1 ) ";
		System.out.println(" check_thanhtoan: "+qr +"\n");
		int dem=0;
		ResultSet rs =db.get(qr);
		if(rs!=null){
			try {
				while(rs.next()){
					dem++;
					s += rs.getString("sochungtu") + " ; " ;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if(dem >0)
		return s;
		else
			return "";
	}
	
	
	private boolean RevertChungtu(dbutils db, String loaichungtu, String sochungtu, String loaict_goc) {
		// TODO Auto-generated method stub
		try{
			 
			
			
			//HUY NHAN HANG
			if(loaichungtu.equals("NH")){
				
				//check trang thai da chot moi cho huy ===> loai trang thai chua chot (0)
				 String	query=" SELECT TRANGTHAI FROM ERP_NHANHANG WHERE pk_seq= "+sochungtu;
				 System.out.println(" trang thai : " +query);
					ResultSet rs1=db.get(query);
					String tt="";
					if(rs1!=null){
						try {
							while(rs1.next()){
								tt=rs1.getString("TRANGTHAI");
							}
							rs1.close();
						} catch (Exception e) {
						e.printStackTrace();
						}
					}
					if(! tt.equals("1")){
						 String msg = "Chứng từ này chưa chốt/không được phép hủy vui lòng kiểm tra lại ";
						 return false;
					}

				
				
				String msg1=Revert_Nhanhang(db, sochungtu, loaict_goc);
				System.out.println(" huy nhan hang: "+msg1);
				if(msg1.length()>0){
					this.msg=msg1;
					return false;
				}
			
			
			//HUY PHIEU KIEM DINH
			}else if(loaichungtu.equals("KDCL")){
				String msg1=  Revert_yckD(db,sochungtu, loaict_goc);
				System.out.println(" huy kiem dinh: "+ msg1);
				if(msg1.length() > 0 ){
					this.msg=msg1;
					db.getConnection().rollback();
					return false;	
				}
			}
			//HUY DON MUA HANG
			else if(loaichungtu.equals("MH")){
				if(!this.revertMuahang( sochungtu)){
					return false;
				}
			}else if(loaichungtu.equals("TNK")){
				if(!this.reverttThuenhapkhau( sochungtu)){
					return false;
				}
			}
			else if(loaichungtu.equals("XKTRA")){
				if(!this.reverttPhieuxuatkhoTRahang( sochungtu)){
					return false;
				}
			}else if(loaichungtu.equals("DONTH")){
				if(!this.reverttPhieuDontrahang( sochungtu)){
					return false;
				}
				
				
			}
			else if(loaichungtu.equals("HDTRA")){
				if(!this.reverttHoaDontrahang( sochungtu)){
					return false;
				}
			}
			else if(loaichungtu.equals("HDDCNCC")){
				if(!this.RevertHoadonDieuchinhNCC( sochungtu)){
					return false;
				}
			}else if(loaichungtu.equals("THGC")){
				String msg1= this.Revert_TieuHaoGiaCong(db, sochungtu, "0");
				if(msg1.length() >0){
					this.msg=msg1;
					db.getConnection().rollback();
					return false;
				}
			}else if(loaichungtu.equals("CK")){
				
				String  query="SELECT * FROM ERP_CHUYENKHO where trangthai  <>4  and pk_seq ="+sochungtu;

				ResultSet rscheck=db.get(query);
				while  (rscheck.next()){
					 String msg1=Huychungtu_Chuyenkho(rscheck.getString("pk_seq"), rscheck.getString("trangthai"),db);
					 if(msg1.length() >0){
						 this.msg=msg1;
							db.getConnection().rollback();
							return false;
					 }
				}
				rscheck.close();
				 
				 
			}
			else if(loaichungtu.equals("CPNHANHANG")){
				if(!this.revertchiphinhanhang( sochungtu)){
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
	
	
	//----- huy nhan hang 

	private boolean RevertHoadonDieuchinhNCC(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
			String  sms3= Revert_KeToan_loaichungtu(db,"Hóa đơn điều chỉnh của NCC",sochungtu2);
			 if(sms3.length()>0){
				  
				 this.msg=sms3;
				 return false;
			 }
			  sms3= Revert_KeToan_loaichungtu(db,"Hóa đơn điều chỉnh giảm giá NCC",sochungtu2);
				 if(sms3.length()>0){
					  
					 this.msg=sms3;
					 return false;
				 }
				 
			 
			 
			 
			 
			 String query="UPDATE ERP_HOADONKHACNCC SET TRANGTHAI=2 WHERE PK_SEQ="+sochungtu2;
			 int k = this.db.updateReturnInt(query);
				
				if(k !=1){
					this.msg = "Cập nhật trạng thái hủy thất bại"+query;
					return false;
					
				}
			 
			 return true;
		}catch(Exception er){
			//
			this.msg=er.getMessage();
			return false;
		}
		 
	}

	private boolean reverttHoaDontrahang(String sochungtu2) {
		// TODO Auto-generated method stub
		 
		try{
			String  sms3= Revert_KeToan_loaichungtu(db,"Hóa đơn trả về NCC",sochungtu2);
			 if(sms3.length()>0){
				  
				 this.msg=sms3;
				 return false;
			 }
			 
			 String query="UPDATE ERP_HOADON SET TRANGTHAI=2 WHERE PK_SEQ="+sochungtu2;
			 int k = this.db.updateReturnInt(query);
				
				if(k !=1){
					this.msg = "Cập nhật trạng thái hủy thất bại"+query;
					return false;
					
				}
			 
			 return true;
		}catch(Exception er){
			//
			this.msg=er.getMessage();
			return false;
		}
		 
	}

	private boolean reverttPhieuDontrahang(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
				String query="select pk_seq from erp_xuatkho  where trangthai in (0,1) and  TRAHANGNCC_FK ="+sochungtu2;
				ResultSet rs=db.get(query);
				boolean is_xk=false;
				if(rs.next()){
					is_xk=true;
				}
				rs.close();
				if(is_xk){
					this.msg="Hủy  xuất kho trước khi hủy đơn trả hàng";
					return false;
				}
			 
			 query= " select * from ERP_HOADON_DDH a inner join erp_hoadon hd on hd.PK_SEQ =a.HOADON_FK " +
			 		" where a.DDH_FK="+sochungtu2+" and hd.TRANGTHAI in (0,1)";
			 
			 	rs=db.get(query);
				is_xk=false;
				if(rs.next()){
					is_xk=true;
				}
				rs.close();
				if(is_xk){
					this.msg="Hủy  hóa đơn trước khi hủy đơn trả hàng";
					return false;
				}
				String msg=revertBookDonTrahang(sochungtu2);
				if(msg.trim().length()>0)
				{
					this.msg=msg;
					return false;
				}
					
				 
				// thay đổi trạng thái phiếu
				query = "update ERP_MUAHANG set TRANGTHAI =4 , dachot=0 where PK_SEQ="+sochungtu2;
				int k = this.db.updateReturnInt(query);
				
				if(k !=1){
					this.msg = "Cập nhật trạng thái hủy thất bại"+query;
					 
					return false;
					
				}
				
			return true;
		}catch(Exception er){
			//
			this.msg=er.getMessage();
			return false;
		}
		
	}
	private boolean revertchiphinhanhang(String sochungtu2) {
		// TODO Auto-generated method stub
		 
		try{
			String  sms3= Revert_KeToan_loaichungtu(db,"Chi phí nhập khẩu",sochungtu2);
			 if(sms3.length()>0){
				  
				 this.msg=sms3;
				 return false;
			 }

			 String query="UPDATE ERP_CHIPHINHAPKHAU SET TRANGTHAI=2 WHERE PK_SEQ="+sochungtu2;
			 int k = this.db.updateReturnInt(query);
				
				if(k !=1){
					this.msg = "Cập nhật trạng thái hủy thất bại"+query;
					return false;
					
				}
			 
			 return true;
		}catch(Exception er){
			//
			this.msg=er.getMessage();
			return false;
		}
		 
	}
	

	private boolean revertBook(String sochungtu2){ 
		try{
			
			String query= " SELECT A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN  " +
						  ",A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH, " +
						  " ISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG " +
						  " ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK  "+
						  " FROM ERP_XUATKHO_SP_CHITIET A INNER JOIN ERP_XUATKHO B ON B.PK_SEQ=A.XUATKHO_FK "+
						  " WHERE b.trangthai='0' and B.PK_SEQ= "+sochungtu2;
			ResultSet rs=db.get(query);
			while(rs.next()){
				double booked =(-1) * rs.getDouble("SOLUONG");
				double soluong = 0;
				double available =   rs.getDouble("SOLUONG");
					
				Kho_Lib kholib=new Kho_Lib();
				kholib.setNgaychungtu("");
				kholib.setLoaichungtu("ErpPhieuxuatkho.java 392  ErpXuatkho :"+this.id);
				kholib.setKhottId(rs.getString("KHO_FK"));
				kholib.setBinId(rs.getString("BIN_FK"));
				kholib.setSolo(rs.getString("SOLO"));
				kholib.setSanphamId(rs.getString("SANPHAM_FK"));
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("MATHUNG"));
				
				kholib.setMaphieu(rs.getString("MAPHIEU"));
				kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
				kholib.setPhieuEo(rs.getString("PHIEUEO"));
				kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
			 
				kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
				kholib.setBooked( booked);
				kholib.setSoluong(soluong);
				kholib.setAvailable(available);
				
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				 
				kholib.setHamluong(rs.getString("HAMLUONG"));
				kholib.setHamam(rs.getString("HAMAM"));
				kholib.setNgaysanxuat("");
				
				String msg1= util_kho.Update_Kho_Sp_ChiTiet_Tra(db,kholib);
			    if( msg1.length() >0)
				{
			    	this.msg = msg1;
					db.getConnection().rollback();
					return  false;
				}
			}
			
			return true;
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
	}
	
	private String revertBookDonTrahang(String dmhId)
	{
	
		
		try 
		{
			
			
			String query = "select COUNT(*) as sodong from ERP_NHANHANG where MUAHANG_FK = '" + dmhId + "'";
			System.out.println("Query mua hang: " + query);
			int sodong = 0;
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					sodong = rs.getInt("sodong");
					rs.close();
				}
			}
			
		 
			if(sodong > 0)
			{

				return "Đơn trả hàng này đã có nhận hàng, bạn phải xóa nhận hàng trước khi xóa đơn trả hàng này";
			}

			//truoc khi delete
			
			String loaihanghoa="";
			
			query = " select isnull(MH.LOAIHANGHOA_FK,'1') as LOAIHANGHOA_FK , MHSP.sanpham_fk, MHSP.soluong, MHSP.khott_fk " +
					" from ERP_MUAHANG_SP MHSP " +
					" INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ=MHSP.MUAHANG_FK  where muahang_fk = '" + dmhId + "'";
			 
			rs = db.get(query);
		 
				while(rs.next())
				{

					loaihanghoa=rs.getString("LOAIHANGHOA_FK").trim();

						
					
				}
				rs.close();
				
				//số lượng giữ nguyên, tăng booked, giảm avai
				if(loaihanghoa.trim().equals("0"))
				{
					query="   SELECT isnull(A.NSX_FK,0) NSX_FK ,c.ngaymua,A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHOTT_FK as KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN,A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.PHIEUDT,'') AS MAPHIEUDINHTINH, "
							 +"\nISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK"
							 +"\nfrom ERP_MUAHANG_SP_CHITIET A inner join ERP_MUAHANG_SP B on A.MUAHANG_FK=B.MUAHANG_FK and A.SANPHAM_FK=B.SANPHAM_FK"
							 +"\ninner join ERP_MUAHANG C on C.PK_SEQ=B.MUAHANG_FK"
							 +"\nwhere C.PK_SEQ='"+dmhId+"' and C.TRANGTHAI=1";
					
							 rs=db.get(query);
							while(rs.next()){
								double booked = (-1)*DinhDang.dinhdangso(rs.getDouble("SOLUONG"));
								double avai =DinhDang.dinhdangso(rs.getDouble("SOLUONG"));
									
								Kho_Lib kholib=new Kho_Lib();
								kholib.setNgaychungtu(rs.getString("ngaymua"));
								kholib.setLoaichungtu("Xóa tăng kho nguoc lại ErpCongtytrahang  :"+dmhId);
								kholib.setKhottId(rs.getString("KHO_FK"));
								kholib.setBinId(rs.getString("BIN_FK"));
								kholib.setSolo(rs.getString("SOLO"));
								kholib.setSanphamId(rs.getString("SANPHAM_FK"));
								kholib.setNsxId(rs.getString("NSX_FK"));
								kholib.setMame(rs.getString("MAME"));
								kholib.setMathung(rs.getString("MATHUNG"));
								
								kholib.setMaphieu(rs.getString("MAPHIEU"));
								kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
								kholib.setPhieuEo(rs.getString("PHIEUEO"));
								kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
							 
								kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
								kholib.setBooked(booked);
								
								kholib.setSoluong(0);
								kholib.setAvailable(avai);
								
								kholib.setMARQ(rs.getString("MARQ"));
								kholib.setDoituongId("");
								kholib.setLoaidoituong("");
								 
								kholib.setHamluong(rs.getString("HAMLUONG"));
								kholib.setHamam(rs.getString("HAMAM"));
								kholib.setNgaysanxuat("");
								
								String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
							    if( msg1.length() >0)
								{
									return msg1;
								}
							 
							
						}
							
					   rs.close();
				}
			query = "update ERP_MUAHANG set trangthai = '4',dachot=0 where pk_seq = '" + dmhId + "' and trangthai=1";
			if(db.updateReturnInt(query)!=1)
			{

				return "Khong the cap nhat ERP_MUAHANG: " + query;
			}
			

			
			return "";
		} 
		catch (Exception e)
		{ 
			db.update("rollback");
			db.shutDown(); 
			return "Khong the xoa don mua hang"; 
		}
		
	}
	
	private boolean reverttPhieuxuatkhoTRahang(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
			
		 
			// revert ke toan khi duyệt hóa đơn 
			String  sms3= Revert_KeToan_loaichungtu(db,"Xuất kho trả hàng",sochungtu2);
			 if(sms3.length()>0){
				 db.getConnection().rollback();
				 this.msg=sms3;
				 return false;
			 }
			 
			 	boolean check =Capnhat_SoLuong_CT_Revert(sochungtu2);
				if(check == false){
					this.msg = "Cập nhật kho thất bại : "+this.msg;
					this.db.getConnection().rollback();
					return false;
					
				}
			 
			 //đưa hóa đơn về trạng thái hủy
			 	String  query=" UPDATE ERP_XUATKHO  SET TRANGTHAI=0  where pk_seq="+sochungtu2;
				if(!db.update(query)){
					db.getConnection().rollback();
					this.msg= "không thể cập nhật: "+query;
				}
			 	return true;
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return false;
		}
	 
	}
	 
	private boolean Capnhat_SoLuong_CT_Revert(String sochungtu2){
		try{
			
			String query= " SELECT a.nsx_fk,  A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN  " +
						  ",A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH, " +
						  " ISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG " +
						  " ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK  "+
						  " FROM ERP_XUATKHO_SP_CHITIET A INNER JOIN ERP_XUATKHO B ON B.PK_SEQ=A.XUATKHO_FK "+
						  " WHERE   B.PK_SEQ= "+sochungtu2;
			ResultSet rs=db.get(query);
			while(rs.next()){
				double booked =  rs.getDouble("SOLUONG");
				double soluong =   rs.getDouble("SOLUONG");;
					
				Kho_Lib kholib=new Kho_Lib();
				kholib.setNgaychungtu(this.Ngayghinhan);
				kholib.setLoaichungtu("ErpPhieuxuatkho.java 392  ErpXuatkho :"+this.id);
				kholib.setKhottId(rs.getString("KHO_FK"));
				kholib.setBinId(rs.getString("BIN_FK"));
				kholib.setSolo(rs.getString("SOLO"));
				kholib.setSanphamId(rs.getString("SANPHAM_FK"));
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("MATHUNG"));
				
				kholib.setMaphieu(rs.getString("MAPHIEU"));
				kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
				kholib.setPhieuEo(rs.getString("PHIEUEO"));
				kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
			 
				kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
				kholib.setBooked( booked);
				
				kholib.setSoluong(soluong);
				kholib.setAvailable(0);
				
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				 
				kholib.setHamluong(rs.getString("HAMLUONG"));
				kholib.setHamam(rs.getString("HAMAM"));
				kholib.setNgaysanxuat("");
				
				String nsxId=rs.getString("nsx_fk")==null?"":rs.getString("nsx_fk");
				kholib.setNsxId(nsxId);
				
				String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
			    if( msg1.length() >0)
				{
			    	this.msg = msg1;
					db.getConnection().rollback();
					return  false;
				}
			}
			
			return true;
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
	}
	// trả booked lại cho kho. xảy tra trong trường hợp update.
	
	private boolean reverttThuenhapkhau(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
			
			
			String  qr=" SELECT  b.PREFIX + nh.PREFIX + cast(nh.pk_seq as varchar(20)) as SOCHUNGTU_NH  FROM "+    
			 "\n	 ERP_PARK PARK INNER JOIN ERP_HOADONNCC HD ON HD.PARK_FK= PARK.PK_SEQ " +
			 "  	INNER JOIN ERP_NHANHANG NH ON NH.HDNCC_FK = HD.PK_SEQ "+    
			 "\n	 inner join erp_donvithuchien b on nh.donvithuchien_fk = b.pk_seq  "+       
			 "\n	 WHERE PARK.trangthai NOT IN (4) AND NH.TRANGTHAI NOT IN (3,4) "+    
			 "\n	 AND hd.pk_seq  in (SELECT  HOADONNCC_FK FROM ERP_THUENHAPKHAU_HOADONNCC WHERE THUENHAPKHAU_FK="+sochungtu2+")";
			ResultSet rs=db.get(qr);
			if(rs.next()){
				 db.getConnection().rollback();
				 this.msg="Vui lòng kiểm tra nhận hàng của tờ khai này";
				 return false;
			}
			rs.close();
			
			// revert ke toan khi duyệt hóa đơn 
			String  sms3= Revert_KeToan_loaichungtu(db,"Thuế VAT nhập khẩu",sochungtu2);
			 if(sms3.length()>0){
				 db.getConnection().rollback();
				 this.msg=sms3;
				 return false;
			 }
			    sms3= Revert_KeToan_loaichungtu(db,"Thuế nhập khẩu",sochungtu2);
			 if(sms3.length()>0){
				 db.getConnection().rollback();
				 this.msg=sms3;
				 return false;
			 }
			 
			 
			 //đưa hóa đơn về trạng thái hủy
			 	String  query=" UPDATE ERP_THUENHAPKHAU   SET TRANGTHAI=3  where pk_seq="+sochungtu2;
				if(!db.update(query)){
					db.getConnection().rollback();
					this.msg= "không thể cập nhật: "+query;
				}
			 	return true;
		}catch(Exception er){
			db.update("rollback");
			er.printStackTrace();
			return false;
		}
	 
	}
	
	private String Revert_TieuHaoGiaCong(dbutils db, String sochungtu,
			String loaict_goc) {
		// TODO Auto-generated method stub
	
		try{

		 //loaict_goc
		 //nếu loại chứng từ gốc =1 thì là trường hợp chứng từ gốc thì là hủy hẳn tiêu hao, còn trường hợp không phải thì đưa tiêu hao về trạng thái=0: tiêu hao lại
	 
			
			 String query= " select a.marq , th.ngaytieuhao ,TH.TRANGTHAI  ,ngaynhapkho ,ngayhethan,  "
			 		+ " isnull(cast(bin_fk as nvarchar(12)),'') as bin_fk  ,isnull(mathung,'') as mathung ,isnull(mame,'') as mame " +
						  "	, isnull(maphieu,'') as maphieu ,isnull(phieueo,'') as phieueo ,isnull(maphieudinhtinh,'') as maphieudinhtinh " +
						  " ,VATTU_FK,SOLO,A.SOLUONG,TH.KHOTT_FK,TH.NCC_FK  ,isnull(cast(A.IDMARQUETTE as varchar(12) ),'') as IDMARQUETTE " +
						  "   , isnull(cast(A.KHUVUCKHO_FK as varchar(12) ),'') as KHUVUCKHO_FK  ,a.hamam,a.hamluong  "+ 
						  " from  ERP_TIEUHAO_VATTU_CHITIET  a inner join ERP_TIEUHAO th on th.PK_SEQ=a.TIEUHAO_FK "+ 
						  " where tH.trangthai in (0,1 ) and th.PK_SEQ="+sochungtu;
			 
			int i=0;
			ResultSet rs=db.get(query);
			while (rs.next()) { 
				double soluonct=0 ;
				double available=rs.getDouble("soluong");
				double booked=(-1)*rs.getDouble("soluong") ;
				
				 if(rs.getString("trangthai").equals("1")){
						  soluonct=rs.getDouble("soluong") ;
						  available=rs.getDouble("soluong");
						  booked=0 ;
				 } 
				 
				String spid=rs.getString("vattu_fk");
				String ncc_fk=rs.getString("ncc_fk");
				String khott_fk=rs.getString("khott_fk");
				String solo=rs.getString("solo").trim();
			 
			 
				String maphieu=rs.getString("maphieu");
				String mathung=rs.getString("mathung");
				String mame=rs.getString("mame");
				String maphieudinhtinh=rs.getString("maphieudinhtinh");
				String phieueo=rs.getString("phieueo");
				String bin_fk=rs.getString("bin_fk");
				String ngaynhapkho= rs.getString("ngaynhapkho");
				String ngayhethan= rs.getString("ngayhethan");
			
				
				Kho_Lib kholib=new Kho_Lib();
				 
				kholib.setLoaichungtu("   :  ERP_TIEUHAO gia cong  :  ERP_TIEUHAO"+ sochungtu);
				kholib.setNgaychungtu(rs.getString("NGAYTIEUHAO"));
				
				kholib.setKhottId(khott_fk);
				kholib.setBinId(bin_fk);
				kholib.setSolo(solo);
				kholib.setSanphamId(spid);
				kholib.setMame(mame);
				kholib.setMathung(mathung);
				kholib.setMaphieu(maphieu);
				kholib.setMaphieudinhtinh(maphieudinhtinh);
				kholib.setPhieuEo(phieueo);
				kholib.setNgayhethan(ngayhethan);
				kholib.setNgaysanxuat("");
				kholib.setNgaynhapkho(ngaynhapkho);
			 
				kholib.setDoituongId(ncc_fk);
				kholib.setLoaidoituong("0");
			 
				kholib.setBooked(booked);
				kholib.setSoluong(soluonct);
				kholib.setAvailable(available);
				
				kholib.setHamam(rs.getString("hamam"));
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setMARQ(rs.getString("marq"));
				String msg1= util_kho.Update_Kho_Sp_Tra(db,kholib);
				if( msg1.length() >0)
				{
		  			this.msg = msg1;
					return "Không thể chốt tiêu hao .Lỗi: "+msg1;
				}
				 
			}
			
			query="delete ERP_TIEUHAO_VATTU_CHITIET where TIEUHAO_FK ="+sochungtu;
			if(!db.update(query)) {
				 
				 
				return "Không thể cập nhật trạng thái đã chốt cho phiếu " + sochungtu;
			}
			
			
			query = "update ERP_TIEUHAO set trangthai = '0',ngayhethong=getdate() where pk_seq = '" +sochungtu+ "'";
			
			if(loaict_goc.equals("1")){
				query = "update ERP_TIEUHAO set trangthai = '2',ngayhethong=getdate() where pk_seq = '" +sochungtu+ "'";
				
			}
			
			 
			if(!db.update(query)) {
				  
				return "Không thể cập nhật trạng thái đã chốt cho phiếu " + sochungtu;
			}
			 
			String loaichungtu="Xuất tiêu hao gia công";
			
			 msg= Revert_KeToan_loaichungtu(db,loaichungtu,sochungtu);
			 if(msg.length()>0){
				 return msg;
			 }
			
			 return "";
			
		}catch(Exception err){
			err.printStackTrace();
			return "Lỗi: "+err.getMessage();
		}
	}

	private   String Revert_Nhanhang(dbutils db, String nhId, String loaict_goc) {
		// TODO Auto-generated method stub
		try{
			String  query="SELECT pk_seq FROM ERP_YEUCAUKIEMDINH where trangthai in (1,2) and nhanhang_fk="+nhId;

			ResultSet rscheck=db.get(query);
			while(rscheck.next()){
				String msg1=  Revert_yckD(db,rscheck.getString("pk_seq"), loaict_goc);
				
				if(msg1.length() > 0 ){
					db.getConnection().rollback();
					return msg1;
					
				}
			}
			rscheck.close();
	 
			query="SELECT * FROM ERP_TIEUHAO WHERE  NHANHANG_FK="+nhId;
		    rscheck=db.get(query);
			while(rscheck.next()){
				String msg1=   Revert_TieuHaoGiaCong(db,rscheck.getString("pk_seq"), "1");
				
				if(msg1.length() > 0 ){
					db.getConnection().rollback();
					return msg1;
					
				}
			}

			rscheck.close();
				
			Utility_Kho util_kho=new Utility_Kho();
			
			
			  query=	" select b.nsx_fk, isnull(b.MARQ,'') marrquet, a.PK_SEQ ,isnull(bin.pk_seq,0)  AS BIN_FK ,a.KHOCHOXULY_FK as kho_fk, b.SANPHAM_FK, N'Nhận hàng ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYNHAN as ngaychungtu, a.NGAYCHOT,   " + 
					  " bin.ma as vitri, b.solo, b.ngayhethan, B.NGAYNHAPKHO as ngaynhapkho, b.mame, b.mathung, '' as maphieu, isnull( b.phieudt, '' ) as phieudt, isnull( b.phieueo, '' ) as phieueo,   " + 
					  " ISNULL(( select top 1 e.MA from ERP_NHANHANG_SANPHAM nhsp left join MARQUETTE e on nhsp.IDMARQUETTE = e.PK_SEQ where nhsp.NHANHANG_FK=a.pk_seq and nhsp.SANPHAM_FK=b.sanpham_fk  " + 
					  "   ),'') as MARQ, 0 as hamam, 100 as hamluong,	b.soluong as NHAP, 0 as XUAT    " + 
					  " from ERP_NHANHANG a inner join  ERP_NHANHANG_SP_CHITIET b on a.pk_seq = b.NHANHANG_FK   " + 
					  " left join ERP_BIN bin on b.bin_fk = bin.pk_seq   " + 
					  " inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " + 
					  " where a.trangthai = '1' and ( isnull(b.ISKIEMDINH,'0') =0 )  and A.PK_SEQ=  " +nhId+ 
					  "   " + 
					  " union all   " + 
					  " select b.nsx_fk, isnull(b.MARQ,'') marrquet, a.PK_SEQ ,isnull(bin.pk_seq,0) AS BIN_FK , a.KHONHAN_FK as kho_fk, b.SANPHAM_FK   " + 
					  " , N'Nhận hàng ' as loaichungtu, cast(a.PK_SEQ as varchar(10)) as machungtu, a.NGAYNHAN as ngaychungtu, a.NGAYCHOT,   " + 
					  " bin.ma as vitri, b.solo, b.ngayhethan, b.ngaynhapkho as ngaynhapkho, b.mame, b.mathung,   " + 
					  " '' as maphieu, isnull( b.phieudt, '' ) as phieudt, isnull( b.phieueo, '' ) as phieueo,   ISNULL(( select top 1 e.MA from ERP_NHANHANG_SANPHAM nhsp left join MARQUETTE e on nhsp.IDMARQUETTE = e.PK_SEQ where nhsp.NHANHANG_FK=a.pk_seq and nhsp.SANPHAM_FK=b.sanpham_fk  " + 
					  "   ),'') as MARQ , 0 as hamam, 100 as hamluong,  " + 
					  " 	b.soluong as NHAP, 0 as XUAT   " + 
					  " from ERP_NHANHANG a inner join  ERP_NHANHANG_SP_CHITIET b on a.pk_seq = b.NHANHANG_FK   " + 
					  " left join ERP_BIN bin on b.bin_fk = bin.pk_seq   " + 
					  " inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " + 
					  " where a.trangthai = '1' and ( isnull(b.ISKIEMDINH,'0') = 1)  and A.PK_SEQ= "+nhId;
			  
			
			System.out.println("du lieu  : "+query);
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				
				Kho_Lib kholib=new Kho_Lib();
				String ngaychungtu=rs.getString("ngaychungtu");
				kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 791 :revert ERP_NHANHANG"+nhId);
				
				kholib.setNgaychungtu(ngaychungtu);
				String khochuyen= rs.getString("kho_fk");
				kholib.setKhottId(khochuyen);
				
				kholib.setBinId("0");
				String solo= rs.getString("solo");
				
				kholib.setSolo(solo);
				String spid= rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maPHIEU"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan"));
				kholib.setNgaysanxuat("");
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(rs.getString("BIN_FK"));
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				 
		    	kholib.setBooked(0);
				kholib.setSoluong((-1)*rs.getDouble("NHAP"));
				kholib.setAvailable((-1)*rs.getDouble("NHAP"));
				System.out.println("-1)*rs.getDouble():"+(-1)*rs.getDouble("NHAP"));
				
				String nsxId=rs.getString("nsx_fk")==null?"":rs.getString("nsx_fk");
				kholib.setNsxId(nsxId);
				kholib.setMARQ(rs.getString("marrquet"));
				
				String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}

			}
			rs.close();
			
			query=" UPDATE ERP_YEUCAUKIEMDINH   SET TRANGTHAI=3  where nhanhang_fk="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			query=" UPDATE ERP_nhanhang SET TRANGTHAI= 3  where pk_seq="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			query="update ERP_PARK set HOANTAT_NHANHANG='0' where pk_seq=(select park_fk from erp_hoadonncc where pk_Seq= (select hdncc_fk from erp_nhanhang where pk_Seq="+nhId+")) ";
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			
			 
			String loaichungtu="Nhận hàng";
			
			String  msg1= Revert_KeToan_loaichungtu(db,loaichungtu,nhId);
			 if(msg1.length()>0){
				 db.getConnection().rollback();
				 return msg1;
			 }
			 
			 
			 
			return "";
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
	
	}
	public   String Revert_KeToan_loaichungtu(dbutils db,String loaichungtu,String sochungtu ) {

		try 
		{
			
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		String query =  " delete ERP_PHATSINHKETOAN " +
					    " where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "'  ";
		 
			 System.out.println("du lieu "+query);
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


	private   String Revert_yckD (dbutils db, String nhId, String loaict_goc) {
		// TODO Auto-generated method stub
		try{
			
			String  query="SELECT * FROM ERP_CHUYENKHO where trangthai  <>4  and YCKD_FK ="+nhId;

			ResultSet rscheck=db.get(query);
			while  (rscheck.next()){
				 String msg1=Huychungtu_Chuyenkho(rscheck.getString("pk_seq"), rscheck.getString("trangthai"),db);
				 if(msg1.length() >0){
					 return msg1;
				 }
			}
			rscheck.close();
		 
			
			Utility_Kho util_kho=new Utility_Kho();
			
	  
			  query=" select a.nsx_fk, isnull( bin.pk_seq,0)  AS BIN_FK ,a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, b.ngaynhapkho, a.mame, b.mathung, b.maphieu, isnull( b.phieudt, '' ) as phieudt, isnull( b.phieueo, '' ) as phieueo, isnull(a.MAMARQUETTE, '') as MARQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong,  " + 
				  "			case when b.soluongDAT > 0 then b.soluongDAT else 0 end as NHAP,  " + 
				  "			case when b.soluongDAT < 0 then -1 * b.soluongDAT else 0 end as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG_CHITIET b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2' AND  A.PK_SEQ= "+nhId+ 
				  " " + 
				  "	union all " + 
				  "	select a.nsx_fk, isnull( bin.pk_seq,0) AS BIN_FK , a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, a.ngaykiem, a.mame, b.mathung, isnull(a.MAPHIEU, '') as maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MERQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong, b.soluongMAU as NHAP, 0 as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2'  AND  A.PK_SEQ= "+nhId+ 
				  "	 " + 
				  "	union all " + 
				  "	select a.nsx_fk, isnull(bin.pk_seq,0) AS BIN_FK , a.KHONHAN_FK as kho_fk, a.SANPHAM_FK,  N'Yêu cầu kiểm định' as loaichungtu, isnull(a.MAPHIEU, '') as machungtu, a.ngaykiem as ngaychungtu, a.ngaykiem as ngaychot, " + 
				  "			bin.ma as vitri, a.solo, a.ngayhethan, a.ngaykiem, a.mame, b.mathung,'' as maphieu, '' as phieudt, '' as phieueo, isnull(a.MAMARQUETTE, '') as MERQ, isnull(b.hamam, 0) as hamam, isnull(b.hamluong, 100) as hamluong, 0 as NHAP, b.soluongMAU as XUAT   " + 
				  "	from ERP_YEUCAUKIEMDINH a inner join ERP_YEUCAUKIEMDINH_THUNG b on a.pk_seq = b.YEUCAUKIEMDINH_FK " + 
				  "			left join ERP_BIN bin on b.bin_fk = bin.pk_seq " + 
				  "	where a.trangthai = '2' AND  A.PK_SEQ= "+nhId ;
			
			
		 
			
			
			 System.out.println("du lieu  : "+query);
			ResultSet rs=db.get(query);
			while(rs.next()){
				
				
				Kho_Lib kholib=new Kho_Lib();
				String ngaychungtu=rs.getString("ngaychungtu");
				kholib.setLoaichungtu("erpnhanhangtrongnuoc_giaysvl 1160 :revert ERP_YEUCAUKIEMDINH"+nhId);
				
				kholib.setNgaychungtu(ngaychungtu);
				String khochuyen= rs.getString("kho_fk");
				kholib.setKhottId(khochuyen);
				
				 
				String solo= rs.getString("solo");
				
				kholib.setSolo(solo);
				String spid= rs.getString("SANPHAM_FK");
				kholib.setSanphamId(spid);
				
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("mathung"));
				kholib.setMaphieu(rs.getString("maPHIEU"));
				
				kholib.setMaphieudinhtinh(rs.getString("phieudt"));
				kholib.setPhieuEo(rs.getString("phieueo"));
				
				kholib.setNgayhethan(rs.getString("ngayhethan"));
				kholib.setNgaysanxuat("");
				
				kholib.setNgaynhapkho(rs.getString("ngaynhapkho"));
				 
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				kholib.setBinId(rs.getString("BIN_FK"));
				
				kholib.setHamluong(rs.getString("hamluong"));
				kholib.setHamam(rs.getString("hamam"));
				
				String nsxId=rs.getString("nsx_fk")==null?"":rs.getString("nsx_fk");
				kholib.setNsxId(nsxId);
				 
			     kholib.setBooked(0);
			    	
		    	if(rs.getFloat("NHAP") >0){
		    		kholib.setSoluong((-1)*rs.getDouble("NHAP"));
					kholib.setAvailable((-1)*rs.getDouble("NHAP"));
		    	}else{
		    		kholib.setSoluong( rs.getDouble("xuat"));
					kholib.setAvailable( rs.getDouble("xuat"));
					kholib.setHamluong("100");
					kholib.setHamam("0");
					
					
		    	}
					
		    	String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
			    if( msg1.length() >0)
				{
					// this.msg = msg1;
					db.getConnection().rollback();
					return msg1;
					
				}
				
			

			}
			rs.close();
			
			
			//neu loai chung tu goc huy la NHAN HANG (2) thi doi trang thai kiem dinh thanh "huy" 3
			//neu loai chung tu goc huy la PHIEU KIEM DINH (3) thi doi trang thai kiem dinh thanh "CHO KIEM DINH" 0
			// ĐÚNG RỒI ĐÓ 
			if(loaict_goc.equals("2"))
			{
				query=" UPDATE ERP_YEUCAUKIEMDINH SET TRANGTHAI=3 where pk_seq ="+nhId;
			}
			else
			{
				query=" UPDATE ERP_YEUCAUKIEMDINH SET TRANGTHAI=0 where pk_seq ="+nhId;
			}
			
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			query="delete ERP_KIEMDINHCHATLUONG_LANDUYET WHERE YEUCAUKIEMDINH_FK="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			 
			query="delete ERP_YEUCAUKIEMDINH_THUNG_CHITIET WHERE YEUCAUKIEMDINH_FK="+nhId;
			
			if(!db.update(query)){
				db.getConnection().rollback();
				return "không thể cập nhật "+query;
			}
			
		 
			return "";
		}catch(Exception err){
			err.printStackTrace();
			return err.getMessage();
		}
	 
	}

	private   String Huychungtu_Chuyenkho(String ckid, String trangthai, dbutils db) {
		// TODO Auto-generated method stub
		// trạng thái ,1,2,3 là đã xuất kho 
		String msg1="";
		try{
		
			if(trangthai.equals("0")){
				  msg1=	XoaChuyenKho(ckid,db);
				 
			}else{
				  msg1=	Revert_ChuyenKho(ckid,db);	
				 			
			}
			
			if(msg1.length()==0){
				msg1=Revert_SLYC_muahang_giaCong(ckid,db);
			}
			
			
			if(msg1.length()>0){
				this.msg=msg1;
				return this.msg;
				
			}
			
		}catch(Exception er){
			er.printStackTrace();
			msg1=er.getMessage();
		}
		return msg1;
		
	}
	
	
	private static String Revert_SLYC_muahang_giaCong(String ckid, dbutils db2) {
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
	
	

	private   String XoaChuyenKho(String lsxId, dbutils db) 
	{
		String msg = "";
		try
		{ 
			//TRỪ KHO
			Utility util = new Utility();
		String	query = "  select a.nsx_fk,  b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
					"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + lsxId + "' and b.trangthai=0 " + 
					"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.phieudt, a.phieueo,a.nsx_fk ";
			
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
					
					String nsxId=rs.getString("nsx_fk")==null?"":rs.getString("nsx_fk");
					
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, nsxId);
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
						return msg;
					}
				}
				rs.close();
			 

				query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
				if( !db.update(query) )
				{
					msg = "Lỗi khi xóa: " + query;
					db.getConnection().rollback();
				 
					return msg;
				}
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			//db.shutDown();
		}
		
		return msg;
	}


	private   String Revert_ChuyenKho(String lsxId ,dbutils db) 
	{
		String msg = "";
		try
		{
			//TRỪ KHO
			Utility util = new Utility();
			String query =  "  select a.nsx_fk,  b.loaidoituong, b.DOITUONG_FK as doituongId, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
							" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
							"  from ERP_CHUYENKHO_SANPHAM_CHITIET a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
							"  where b.PK_SEQ = '" + lsxId + "' and b.trangthai in (1,2,3)  " + 
							"  group by b.loaidoituong, b.DOITUONG_FK, b.NGAYCHUYEN, b.KhoXuat_FK, a.SanPham_fk, " +
							" a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, " +
							" a.bin_fk, a.phieudt, a.phieueo,a.nsx_fk  ";
			
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

					double soluong =   rs.getDouble("soluong");
					
					String nsxId=rs.getString("nsx_fk")==null?"":rs.getString("nsx_fk");
					
					msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Cập nhật CK - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, nsxId);
					System.out.println("Lõi 1 : " +msg);
					
					if( msg.trim().length() > 0 )
					{
						msg = "Lỗi khi xóa: " + msg;
						db.getConnection().rollback();
					 
						return msg;
					}
					 
				}
				rs.close();					
	// giam  kho nhan
	
				query = "  select a.nsx_fk, b.loaidoituongNhan, b.doituongnhan_fk as doituongNhanId, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
				" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.binNhan_fk, 0) as bin_fk, a.phieudt, a.phieueo, SUM( a.SoLuong ) as soluong  " + 
				"  from ERP_CHUYENKHO_SANPHAM_CHITIET_NHANHANG a inner join ERP_CHUYENKHO b on a.chuyenkho_FK = b.PK_SEQ " + 
				"  where  b.trangthai=3  and  b.PK_SEQ = '" +lsxId + "' and a.soluong > 0 " + 
				"  group by b.loaidoituongNhan, b.doituongnhan_fk, b.NGAYCHUYEN, b.KHONHAN_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, binNhan_fk, a.phieudt, a.phieueo,a.nsx_fk ";
		
		System.out.println("::: CAP NHAT KHO: " + query);
		  rs = db.get(query);
		 
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
				
				double soluong =(-1)*  rs.getDouble("soluong");
				
				String nsxId=rs.getString("nsx_fk")==null?"":rs.getString("nsx_fk");
				
				msg = util.Update_KhoTT_MOI(rs.getString("NGAYCHUYEN"), "Hủy chứng từ  chuyển kho "+lsxId, db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, soluong, 0, soluong, nsxId);
				if( msg.trim().length() > 0 )
				{
					msg = "Lỗi khi xóa: " + msg;
					db.getConnection().rollback();
					//db.shutDown();
					return msg;
				}
			}
			rs.close();
			
			
		 	query = "update ERP_CHUYENKHO set trangthai = '4' where pk_seq = '" + lsxId + "' ";
			if( !db.update(query) )
			{
				msg = "Lỗi khi xóa: " + query;
				db.getConnection().rollback();
				//db.shutDown();
				return msg;
			}
					
			 
		} 
		catch (Exception e)
		{
			e.printStackTrace();
			
			msg = "Lỗi khi chốt: " + e.getMessage();
			db.update("rollback");
			 
		}
		
		return msg;
	}


	//---------
	
	private boolean revertDenghithanhtoan(String sochungtu2) {
			// TODO Auto-generated method stub
			try{
				
				//KIỂM TRA TRẠNG THÁI CỦA ĐỀ NGHỊ THANH TOÁN
				String query =
				"SELECT  MUAHANG.TRANGTHAI," +
				"        CASE MUAHANG.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN -1 THEN N'CHƯA DUYỆT' WHEN 1 THEN N'ĐÃ DUYỆT' WHEN 2 THEN N'HOÀN TẤT' END TRANGTHAI2 \n"+
				"FROM	 ERP_MUAHANG MUAHANG \n"+ 
				"WHERE	 ISNULL(MUAHANG.DACHOT, 0) = '1' \n"+
				"		 AND MUAHANG.LOAIHANGHOA_FK = 2 and MUAHANG.TYPE = '0' \n"+
				"		 AND MUAHANG.SOPO = '"+this.sochungtu+"' \n";
				
				System.out.println(query);
				String _trangthai = "";
				ResultSet rsCheck = db.get(query);
				
				if(rsCheck!=null){
					if(rsCheck.next()){
						_trangthai = rsCheck.getString("trangthai") == null ? "" : rsCheck.getString("trangthai");
					}
				}
		
				// KIỂM TRA TRẠNG THÁI CỦA NHẬN HÀNG					

				if( _trangthai.equals("1") || _trangthai.equals("2")){
					
				}
				else{
					this.msg = "Trạng thái đề nghị thanh toán này không hợp lệ, bạn chỉ có thể hủy những đề nghị thanh toán đã duyệt hoặc hoàn tất!!!";
					return false;
				}
				
				//KIỂM TRA XEM CÓ PHIẾU CHI CỦA ĐỀ NGHỊ THANH TOÁN KHÔNG
				//KIỂM TRA XEM CÓ PHIẾU CHI CỦA ĐỀ NGHỊ THANH TOÁN KHÔNG
				query =			 
				"SELECT count(*) as sodong,TTHD.PK_SEQ, TTHD.TRANGTHAI " +
				"FROM   ERP_MUAHANG MUAHANG \n"+
				"	    INNER JOIN ERP_THANHTOANHOADON_HOADON TT ON MUAHANG.PK_SEQ = TT.HOADON_FK  AND TT.LOAIHD = 6 \n"+
				"	    INNER JOIN ERP_THANHTOANHOADON TTHD ON TT.THANHTOANHD_FK = TTHD.PK_SEQ \n"+
				"       LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK \n"+
				"       LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MUAHANG.NHANVIEN_FK \n"+
				"WHERE  TTHD.TRANGTHAI IN (0,1) \n"+
				"		AND MUAHANG.SOPO = '"+this.sochungtu+"' \n"+
				"GROUP BY TTHD.PK_SEQ, TTHD.TRANGTHAI";
				
				System.out.println(query);
								
				ResultSet rs = db.get(query);
				int sodong = 0;
				
				if(rs != null)
				{
					if(rs.next())
					{
						sodong = rs.getInt("sodong");					
						rs.close();
					}
				}
				
				if(sodong>0)
				{
					this.msg = "Đề nghị thanh toán " + this.sochungtu + ", đã có phiếu chi, bạn phải hủy phiếu chi trước.";
					return  false;
				}
				
				// chỉ đưa về trạng thái chưa duyệt
				query = "UPDATE ERP_MUAHANG SET TRANGTHAI = '4' WHERE SOPO = '" + this.sochungtu + "' ";
				System.out.println(query);
				if(!this.db.update(query)) 
				{
					db.getConnection().rollback();
					return false;
				}

				if( !Revert_KeToan("Chi phí khác", this.dnttId) )
				{
					db.getConnection().rollback();
					return false;
				}
				
				return true;
			
			}catch(Exception er){
				er.printStackTrace();
				this.msg=er.getMessage();
				return false;
			}
		}
	
	private boolean revertYeucaukiemdinh(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
			String query="select pk_seq,ngaychuyen ," +
					" CASE CK.TRANGTHAI WHEN 0 THEN N'CHƯA CHỐT' WHEN 1 THEN N'ĐÃ CHỐT' WHEN 2 THEN N'ĐÃ NHẬN HÀNG'  ELSE N'HOÀN TẤT' END AS TRANGTHAI " +
					" from ERP_CHUYENKHO CK where YCKD_FK="+sochungtu2+" and trangthai in (1,2,3)";
			ResultSet rs=db.get(query);
			while (rs.next()){
				if(!this.revertChuyenKho(rs.getString("pk_seq"))){
					 
					 return false;
				}
				
				query = " insert ERP_HUYCHUNGTUMUAHANG_CHUNGTU(hctmh_fk, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu,TYPE) " +
				" values('" + this.id + "', '4',  '" + rs.getString("pk_seq") + "', '" + rs.getString("ngaychuyen") + "', N'" + rs.getString("trangthai") + "', N'Chuyển kho','CK')";
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Không thể tạo mới ERP_HUYCHUNGTUMUAHANG_CHUNGTU, " + query;
					return false;
				}
		
			}
			
			// chỉ đưa về trạng thái chưa duyệt
			query="Update ERP_YeuCauKiemDinh set trangthai=0 where pk_Seq="+sochungtu2;
			if(!db.update(query))
			{
				 msg = "Khong the cap nhat erp_yecaukiemdinh: " + query;
				 return false;
			}
			query=" delete ERP_KIEMDINHCHATLUONG_LANDUYET where YEUCAUKIEMDINH_FK= "+sochungtu2;
			if(!db.update(query))
			{
				 msg = "Khong the cap nhat erp_yecaukiemdinh: " + query;
				 return false;
			}
			
			return true;
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
	}

	
	private boolean revertNhanhang(String nhanhangid) {
		try{
			//1.KIỂM TRA XEM NHẬN HÀNG CÓ THANH TOÁN HÓA ĐƠN CHƯA
			
			String query = 
				" SELECT a.trangthai, b.tiente_fk "+				
				" FROM   erp_nhanhang a inner join erp_muahang b on a.muahang_fk = b.pk_seq " +
				" WHERE  a.PK_SEQ = '" + nhanhangid + "' ";
			
			System.out.println(query);
			String _trangthai = "";
			ResultSet rsCheck = db.get(query);
			
			if(rsCheck!=null){
				if(rsCheck.next()){
					_trangthai = rsCheck.getString("trangthai") == null ? "" : rsCheck.getString("trangthai");
					tiente = rsCheck.getString("tiente_fk");
				}
			}
	
			// KIỂM TRA TRẠNG THÁI CỦA NHẬN HÀNG					

			if( _trangthai.equals("1") || _trangthai.equals("2")){
				
			}
			else{
				this.msg = "Trạng thái phiếu nhận hàng này không hợp lệ, bạn chỉ có thể hủy những phiếu nhận hàng đã chốt hoặc hoàn tất!!!";
				return false;
			}
			 query = " SELECT count(a.pk_seq) as sodong, case a.HTTT_FK  when 100000 then N'Phiếu chi' else N'Ủy nhiệm chi' end as httt \n"+
  				     " FROM   ERP_THANHTOANHOADON a on a." +
  				     "		  inner join ERP_THANHTOANHOADON_HOADON b on a.PK_SEQ = b.THANHTOANHD_FK  " +
  				     "		  INNER JOIN ERP_HOADONNCC c on b.HOADON_FK = c.pk_seq  " +
  				     "        INNER JOIN ERP_PARK e on c.park_fk = e.pk_seq "+
  				     "		  INNER JOIN ERP_HOADONNCC_PHIEUNHAP d on c.pk_seq = d.hoadonncc_fk   " +
  				     " WHERE  d.phieunhan_fk = '" + nhanhangid + "' and a.TRANGTHAI in ( 0, 1 ) and c.TRANGTHAI not in (4) "+
  				     " GROUP BY a.HTTT_FK ";

			System.out.println("___Thanh toan HoaDon: " + query);
			
			boolean daxuatphieuchi = false;
			String httt = "";
			
			 rsCheck = db.get(query);
			if(rsCheck != null)
			{
				if(rsCheck.next())
				{
					if(rsCheck.getInt("sodong") > 0){
						daxuatphieuchi = true;
						httt = rsCheck.getString("httt");
					}
				}
				rsCheck.close();
			}
			
			if(daxuatphieuchi)
			{
				this.msg = "Phiếu nhận hàng " + this.sochungtu + ", đã có "+httt+", bạn phải hủy "+httt+" trước.";
				return  false;
			}
			
			if(tiente.equals("100000")){//VIỆT NAM ĐỒNG -> QUY TRÌNH NHẬN HÀNG NỘI ĐỊA
				
				//2. KIỂM TRA XEM CÓ HÓA ĐƠN NCC CHƯA
				query =  " SELECT count(a.pk_seq) as sodong \n"+
						 " FROM   ERP_PARK a inner join ERP_HOADONNCC b on a.pk_seq = b.park_fk  " +
						 "		  INNER JOIN ERP_HOADONNCC_PHIEUNHAP c on b.pk_seq = c.hoadonncc_fk  " +
						 " WHERE  c.phieunhan_fk = '" + nhanhangid + "' and a.TRANGTHAI != 3 ";

				System.out.println("___Hoa don NCC: " + query);
				
				boolean daxuathoadon = false;
				
				rsCheck = db.get(query);
				if(rsCheck != null)
				{
					if(rsCheck.next())
					{
						if(rsCheck.getInt("sodong") > 0){
							daxuathoadon = true;
						}
					}
					rsCheck.close();
				}
				
				if(daxuathoadon)
				{
					this.msg = "Phiếu nhận hàng " + nhanhangid + ", đã xuất hóa đơn NCC, bạn phải hủy hóa đơn NCC trước.";
					return  false;
				}
				
				//3. KIỂM TRA XEM CÓ PHIẾU KIỂM ĐỊNH CHƯA (phiếu đã chốt,phiếu chưa chốt hủy nhận hàng thì hủy luôn)
				query = " SELECT count(*) as sodong "+
						" FROM 	 ERP_YeuCauKiemDinh a  " +
						" WHERE a.nhanhang_fk in ( " + nhanhangid + " )  and a.trangthai in (1,2)  ";
				
				System.out.println("___Phieu kiem dinh: " + query);
				
				boolean daxuatPKD = false;
				
				rsCheck = db.get(query);
				if(rsCheck != null)
				{
					if(rsCheck.next())
					{
						if(rsCheck.getInt("sodong") > 0){
							daxuatPKD = true;
						}
					}
					rsCheck.close();
				}
				
				if(daxuatPKD)
				{
					this.msg = "Phiếu nhận hàng " + nhanhangid + ", đã có phiếu kiểm định, bạn phải hủy phiếu kiểm định trước.";
					return  false;
				}
				
			}
			else {// ĐỒNG NGOẠI TỆ -> QUY TRÌNH NHẬN HÀNG NGOẠI
				
				//2. KIỂM TRA XEM CÓ PHIẾU KIỂM ĐỊNH KHÔNG
				query = " SELECT count(*) as sodong "+
						" FROM 	 ERP_YeuCauKiemDinh a  " +
						" WHERE  a.nhanhang_fk in ( " + nhanhangid+ " )  and a.trangthai in (1,2)  ";
				
				System.out.println("___Phieu kiem dinh: " + query);	
				
				boolean daxuatPKD = false;
				
				rsCheck = db.get(query);
				if(rsCheck != null)
				{
					if(rsCheck.next())
					{
						if(rsCheck.getInt("sodong") > 0){
							daxuatPKD = true;
						}
					}
					rsCheck.close();
				}
				
				if(daxuatPKD)
				{
					this.msg = "Phiếu nhận hàng " + nhanhangid + ", đã có phiếu kiểm định, bạn phải hủy phiếu kiểm định trước.";
					return  false;
				}
				
			}		
			
				// tạm thời hủy nhận hàng của thành phẩm 
					
				query = " insert ERP_HUYCHUNGTUMUAHANG_CHUNGTU(hctmh_fk, thutu, sochungtu, ngaychungtu, trangthai, loaichungtu,TYPE) " +
				" SELECT '" + this.id + "', '3',  PK_SEQ, NGAYNHAN ,N'Chưa chốt', N'Yêu cầu kiểm định','KDCL'  from ERP_YeuCauKiemDinh where trangthai=0 and nhanhang_fk="+nhanhangid;
				if(!db.update(query))
				{
					db.getConnection().rollback();
					this.msg = "Không thể tạo mới ERP_HUYCHUNGTUMUAHANG_CHUNGTU, " + query;
					return false;
				}
				
				
				
				query="update ERP_YeuCauKiemDinh set trangthai=3 where trangthai=0 and nhanhang_fk="+nhanhangid;
				if(!db.update(query))
				{
					 msg = "Khong the cap nhat erp_yecaukiemdinh: " + query;
					
					return false;
				}
					
				//NHẬN HÀNG
				
				query = "select NGAYNHAN,LOAIHANGHOA_FK, NGAYCHOT from ERP_NHANHANG where PK_SEQ = '" + nhanhangid + "'";
				ResultSet rsCheck2 = db.get(query);
				
				String loaihh = "0";
				String ngaychotNV = "";
				
				if(rsCheck2.next())
				{
					loaihh = rsCheck2.getString("LOAIHANGHOA_FK");
					ngaychotNV =  rsCheck2.getString("NGAYCHOT");
						
					String msg1=util.checkNgayHopLe(db, rsCheck2.getString("NGAYNHAN"));
					if(msg1.length() >0)
					{
						this.msg = msg1;
						return false;
					}
				}
				rsCheck2.close();
				
				if(!loaihh.equals("0"))
				{
					this.msg = "Bạn chỉ có thể hủy chứng từ nhận hàng của sản phẩm";
					return false;
				}
				
				Utility_Kho util_kho=new Utility_Kho();
				
				query =  " SELECT NHSP.KHONHAN as KHOTT_FK,SANPHAM_FK,  ISNULL(NHSP.SOLUONGNHAN, 0)  AS SOLUONGNHAN, isnull(DONGIAVIET,0) as DONGIA,ngaynhandukien "+  
					" FROM ERP_NHANHANG_SANPHAM NHSP "+
					" INNER JOIN  ERP_NHANHANG NH ON NH.PK_SEQ=NHSP.NHANHANG_FK "+ 		 
					" WHERE   NH.TRANGTHAI IN (1, 2) AND NHSP.DANHAN=1 and NHSP.SOLUONGNHAN >0 and NH.PK_SEQ= "+nhanhangid + 
					" union all  "+
					" SELECT NH.KHOCHOXULY_FK,SANPHAM_FK,  ISNULL(NHSP.SOLUONGNHAN, 0)  AS SOLUONGNHAN, isnull(DONGIAVIET,0) as DONGIA,ngaynhandukien  "+   
					" FROM ERP_NHANHANG_SANPHAM NHSP "+
					" INNER JOIN  ERP_NHANHANG NH ON NH.PK_SEQ=NHSP.NHANHANG_FK "+ 		 
					" WHERE   NH.TRANGTHAI IN (1, 2) AND NHSP.DANHAN=0 and NHSP.SOLUONGNHAN >0 and NH.PK_SEQ="+nhanhangid;
				
				  ResultSet rs = db.get(query);
								
				 
					while(rs.next()){
						String khott_fk=rs.getString("KHOTT_FK");
						String spId=rs.getString("SANPHAM_FK");
						double soluong=(-1)*rs.getDouble("soluongnhan");
						double available=(-1)*rs.getDouble("soluongnhan");
						double dongia=rs.getDouble("dongia");
						String ngaynhandukien=rs.getString("ngaynhandukien");
						double booked=0;
						//giảm kho tổng.
						String msg1=util_kho.Update_Kho_Sp(db, khott_fk, spId, soluong, booked, available, 0);
						if(msg1.length() >0){
							this.msg=msg1;
							return false;
							
						}
						query=" SELECT NHANHANG_FK,SANPHAM_FK,SOLO,SOLUONG,NGAYSANXUAT,NGAYHETHAN,NGAYNHANDUKIEN,GIATHEOLO,KHU_FK "+
							  " FROM ERP_NHANHANG_SP_CHITIET WHERE ngaynhandukien='"+ngaynhandukien+"'  and NHANHANG_FK="+nhanhangid+" AND SANPHAM_FK="+spId;
						ResultSet rsnhct=db.get(query);
						
						while(rsnhct.next()){
							String solo=rsnhct.getString("solo");
							String khuvucid=rsnhct.getString("khu_fk");
							String NgayBatDau=ngaychotNV;
							String vitri="100000";
							
							double soluong_ct=(-1)* rsnhct.getDouble("SOLUONG");
							double avai_ct=(-1)*rsnhct.getDouble("SOLUONG");
							if(!util_kho.getIsQuanLyKhuVuc(khott_fk, db).equals("1")){
								khuvucid="";
							}
							msg1=util_kho.Update_Kho_Sp_Chitiet(db, khott_fk, spId, soluong_ct, booked, avai_ct, dongia, solo, vitri, khuvucid, NgayBatDau); 
							  
							if(msg1.length() >0){
								this.msg=msg1;
								return false;
								
							}
							
							if(util_kho.IsKhoQuanLyTrangThai(khott_fk, db)==true){
								// trạng thái sản phâm kho chờ xử lý là trạng thái chưa đạt
								String trangthaisp="'-1'";
								msg1=util_kho.Update_Kho_Sp_Chitiet_TrangThai (db, khott_fk, spId, soluong_ct, booked, avai_ct, dongia, solo, vitri, khuvucid, NgayBatDau,trangthaisp); 
								  
								if(msg1.length() >0){
									this.msg=msg1;
									return false;
									
								}
							}
						}
						
					}	
			 
					rsCheck.close();				
				
				//HỦY NHẬN HÀNG
				query = "UPDATE ERP_NHANHANG set trangthai = '4' where pk_seq = '" +nhanhangid+ "'";
				
				System.out.println("1.Cap nhat: " + query);
				
				if(!db.update(query))
				{
					this.msg = "1.Không thể cập nhật " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//MỞ LẠI TRẠNG THÁI CỦA ERP_MUAHANG: CHƯA HOÀN TẤT
				
				query="SELECT pk_seq FROM ERP_NHANHANG where trangthai not in (3, 4) and muahang_fk=(select muahang_fk  from erp_nhanhang where pk_seq="+nhanhangid+") ";
				
				ResultSet rscheck=db.get(query);
				if(rscheck.next()) {
					
				}else{
					query = "Update ERP_MUAHANG set trangthai = 0 where pk_seq = ( select MUAHANG_FK from ERP_NHANHANG where PK_SEQ = '" + nhanhangid + "' )";
					System.out.println("2.Cap nhat: " + query);
					if(!db.update(query))
					{
						this.msg = "5.Không thể cập nhật " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				// CHẠY LẠI KẾ TOÁN
				if( !Revert_KeToan("Nhận hàng", nhanhangid) )
				{
					db.getConnection().rollback();
					return false;
				}
					
					
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
		return true;
	}

	
	
	private boolean revertMuahang(String sochungtu2) {
		// TODO Auto-generated method stub
		try{
			
			//check nhan hang
			String sms=Check_HDTC_DonMH(db);
			if(sms.length()>0){
				this.msg = " Trước khi hủy  đơn mua hàng này, vui lòng hủy các hóa đơn với số chứng từ sau : "+sms;
				return false;
			}
			
			//kiem tra khac chua duyet
			//check trang thai da chot moi cho huy ===> loai trang thai chua chot (0)
			String query=" SELECT TRANGTHAI FROM ERP_MUAHANG WHERE pk_seq= "+this.dmhId;
			ResultSet rs=db.get(query);
			String tt="";
			if(rs!=null){
				try {
					while(rs.next()){
						tt=rs.getString("TRANGTHAI");
					}
					rs.close();
				} catch (Exception e) {
				e.printStackTrace();
				}
			}
			if(tt.equals("0")){
				 this.msg = " Chứng từ này chưa chốt/chưa duyệt vui lòng kiểm tra lại ";
				 return false;
			}

			
			//cap nhat trang thai huy/xoa cho don mua hang
			query = "UPDATE ERP_MUAHANG SET TRANGTHAI = '3' WHERE PK_SEQ ="+sochungtu2+"";
			if(!db.update(query))
			{
				this.msg = "1.Không thể cập nhật " + query;
				db.getConnection().rollback();
				return false;
			}
		}catch(Exception er){
			return false;
		}
		return true;
	}

	private boolean revertChuyenKho(String chungtuid) {
		// TODO Auto-generated method stub
		try{
			 
			String query="";
			
	    	// Nếu là đã chuyển kho trạng thái '0','1','2',thì cập nhật kho CHUYỂN lại tăng avai,giam booked
			
			  query=
			  " SELECT isnull(CK.ISHANGDIDUONG,'0') as ISHANGDIDUONG , CK.NCC_CHUYEN_FK,CK.KHOXUAT_FK ,CHUYENKHO_FK,SANPHAM_FK,SOLO,VITRI,KHU,NGAYBATDAU,SOLUONG, ISNULL(CK.TRANGTHAISP,'0') AS TRANGTHAISP " +
			  " FROM ERP_CHUYENKHO CK "+ 
			  " INNER JOIN   ERP_CHUYENKHO_SP_XUATHANG  CKSP ON CK.PK_SEQ=CKSP.CHUYENKHO_FK "+
			  " WHERE ck.trangthai in ('1','2')  and   CK.PK_SEQ="+chungtuid;
			ResultSet rs=db.get(query);
			
			while(rs.next()){
				// giam booked_tang_avai
				// cập nhật kho tổng
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
				
				
				String msg1= util_kho.Update_Kho_Sp (this.db,khoXuatId,sanpham_fk,soluong,booked,available,0);
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
				  rs=db.get(query);
				
				rs=db.get(query);
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

	private boolean Revert_KeToan(String loaichungtu, String sochungtu)
	{
		//CHECK XEM NGAY CHUNG TU CUA PHIEU MUON HUY CO HOP LE KHONG????
		String colNAME = "";
		String tableNAME = "";
		if(loaichungtu.equals("Nhận hàng"))
		{
			colNAME = "ngaynhan";
			tableNAME = "ERP_NHANHANG";
		}
		
		if(loaichungtu.equals("Chi phí khác"))
		{
			colNAME = "NGAYMUA";
			tableNAME = "ERP_MUAHANG";
		}
		
		if(loaichungtu.equals("Duyệt PO nội bộ"))
		{
			colNAME = "NGAYMUA";
			tableNAME = "ERP_MUAHANG";
		}
		
		/*
		if(loaichungtu.equals("1")){
			colNAME = "NGAYMUA";
			tableNAME = "ERP_MUAHANG";
			
		}*/
		
		
		/*else
		{
			if(loaichungtu.equals("Duyệt hóa đơn"))
			{
				colNAME = "ngayghinhan";
				tableNAME = "ERP_PARK";
			}
			else
			{
				if(loaichungtu.equals("Tiêu hao gia công"))
				{
					colNAME = "ngaychot";
					tableNAME = "ERP_TIEUHAO";
				}
				else
				{
					if(loaichungtu.equals("Kiểm định chất lượng"))
					{
						colNAME = "ngaykiem";
						tableNAME = "ERP_YeuCauKiemDinh";
					}
				}
			}
		}*/
		
		String query = " select " + colNAME + " as ngaynghiepvu from " + tableNAME + " where pk_seq = '" + sochungtu + "' ";
		System.out.println("____LAY NGAY NGHIEP VU: " + query);
		
		ResultSet ngayRS = db.get(query);
		String ngaynghiepvu = "";
		
		try 
		{
			if(ngayRS.next())
			{
				ngaynghiepvu = ngayRS.getString("ngaynghiepvu");
			}
			ngayRS.close();
		} 
		catch (Exception e) { }
		
		String nam = ngaynghiepvu.substring(0, 4);
		String thang = ngaynghiepvu.substring(5, 7);
		
		//CHECK NGAY GHI NHAN REVERT CO HOP LE HAY KHONG (CHI DUOC THUC HIEN TRONG THANG KHOA SO CONG 1)
		query = "select THANGKS, NAM from ERP_KHOASOKETOAN order by NAM desc, THANGKS desc";
		String thangKS = "1";
		String namKS = "2013";
		ResultSet rsCheck = db.get(query);
		if(rsCheck != null)
		{
			try 
			{
				if(rsCheck.next())
				{
					thangKS = rsCheck.getString("THANGKS");
					namKS = rsCheck.getString("NAM");
				}
				rsCheck.close();
			} 
			catch (Exception e) {}
		}
		
		String thangHopLe = "";
		String namHopLe = "";
		if(Integer.parseInt(thangKS) == 12 )
		{
			thangHopLe =  "1";
			namHopLe = Integer.toString( Integer.parseInt(namKS)  + 1);
		}
		else
			
		{
			thangHopLe =  Integer.toString(Integer.parseInt(thangKS) + 1);
			namHopLe = namKS;
		}
		
	/*	if( ( Integer.parseInt(thangHopLe) != Integer.parseInt(thang) ) || ( Integer.parseInt(namHopLe) != Integer.parseInt(nam) ) )
		{
			this.msg = "Bạn chỉ có thể thực hiện nghiệp vụ sau tháng khóa sổ gần nhất ( " + thangKS + "-" + namKS + " ) 1 tháng";
			return false;
		}
		*/
		
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		query = "select SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NO, CO, TIENTEGOC_FK, TONGGIATRINT  " +
			    "from ERP_PHATSINHKETOAN " +
			    "where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu +"' ";
		
		System.out.println("1.CHECK NO-CO: " + query);
		
		ResultSet rsPSKT = db.get(query);
		try 
		{
			while(rsPSKT.next())
			{
				String taikhoan_fk = rsPSKT.getString("TAIKHOAN_FK");
				String tiente_fk = rsPSKT.getString("TIENTEGOC_FK");
				double NO = rsPSKT.getDouble("NO");
				double CO = rsPSKT.getDouble("CO");
				double TONGGIATRINT = rsPSKT.getDouble("TONGGIATRINT");
				
				//NEU LA CO THI BAY GIO GHI GIAM CO LAI
				if( NO > 0 )
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRINOVND = GIATRINOVND - " + NO + ", GIATRINONGUYENTE = GIATRINONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				else
				{
					query = " update ERP_TAIKHOAN_NOCO set GIATRICOVND = GIATRICOVND - " + CO + ", GIATRICONGUYENTE = GIATRICONGUYENTE - " + TONGGIATRINT + "  " +
							" where TAIKHOANKT_FK = '" + taikhoan_fk + "' and THANG = '" + Integer.parseInt(thang) + "' and NAM = '" + Integer.parseInt(nam) + "' and NGUYENTE_FK = '" + tiente_fk + "' ";
				}
				
				System.out.println("1.REVERT NO-CO: " + query);
				
				if(!db.update(query))
				{
					this.msg = "1.Lỗi REVERT: " + query;
					return false;
				}
				
			}
			rsPSKT.close();
		} 
		catch (Exception e) { }
		
		//GHI NHAN LOG NGUOC LAI - CHỊ VÂN CHỈNH SỬA NHÂN VỚI (-1)
		query = " INSERT ERP_PHATSINHKETOAN( NGAYCHUNGTU, NGAYGHINHAN, LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, NO, CO, DOITUONG, MADOITUONG, LOAIDOITUONG, " +
				"							SOLUONG, DONGIA, TIENTEGOC_FK, DONGIANT, TIGIA_FK, TONGGIATRI, TONGGIATRINT, KHOANMUC )  " +
				" 			SELECT NGAYCHUNGTU, '" + ngaynghiepvu + "', N'REVERT_' + LOAICHUNGTU as LOAICHUNGTU, SOCHUNGTU, TAIKHOAN_FK, TAIKHOANDOIUNG_FK, NOIDUNGNHAPXUAT_FK, CO, NO, DOITUONG, MADOITUONG, LOAIDOITUONG,  " +
				"			SOLUONG, (-1)*isnull(DONGIA,0) DONGIA, TIENTEGOC_FK, isnull(DONGIANT,0)*(-1), TIGIA_FK, ISNULL(TONGGIATRI,0)*(-1), ISNULL(TONGGIATRINT,0)*(-1), KHOANMUC  " +
				" FROM ERP_PHATSINHKETOAN  " +
				" WHERE LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "' ";
		
		System.out.println("2.REVERT LOG NO-CO: " + query);
		
		if(!db.update(query))
		{
			this.msg = "2.Lỗi REVERT: " + query;
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
			this.db.shutDown();
		}catch (Exception e) {
			
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

	
	public String getLoaictId() {
		
		return this.loaict;
	}

	
	public void setLoaictId(String loaictId) {
		
		this.loaict = loaictId;
	}

	
	public String getSoDeNghiThanhToan() {
		
		return this.dnttId;
	}

	
	public void setSoDeNghiThanhToan(String sodenghithanhtoan) {
		
		this.dnttId = sodenghithanhtoan;
	}

	@Override
	public void setSochungtu_chonhuy(String sct) {
		// TODO Auto-generated method stub
		this.Sochungtu_chonhuy=sct;
	}

	@Override
	public String getSochungtu_chonhuy() {
		// TODO Auto-generated method stub
		return this.Sochungtu_chonhuy;
	}

	@Override
	public String getSoThuenhapkhau() {
		// TODO Auto-generated method stub
		return this.Sothuenhapkhau ;
	}

	@Override
	public void setSoThuenhapkhau(String SoThuenhapkhau) {
		// TODO Auto-generated method stub
		this.Sothuenhapkhau=SoThuenhapkhau;
	}

	@Override
	public String getSoxuatkhotrave() {
		// TODO Auto-generated method stub
		return soxuatkhotrahang;
	}

	@Override
	public void setSoxuatkhotrave(String Soxuatkhotrave) {
		// TODO Auto-generated method stub
		soxuatkhotrahang=Soxuatkhotrave;
	}

	@Override
	public String getSoDontrahang() {
		// TODO Auto-generated method stub
		return Sodotrahang;
	}

	@Override
	public void setSoDontrahang(String SoDontrahang) {
		// TODO Auto-generated method stub
		Sodotrahang=SoDontrahang;
	}

	@Override
	public String getSohoadontrahang() {
		// TODO Auto-generated method stub
		return this.Sohoadontrahang;
	}

	@Override
	public void setSohoadontrahang(String Sohoadontrahang) {
		// TODO Auto-generated method stub
		this.Sohoadontrahang=Sohoadontrahang;
	}

	@Override
	public String getSophieuchuyenkho() {
		// TODO Auto-generated method stub
		return this.Sophieuchuyenkho;
	}

	@Override
	public void setSophieuchuyenkho(String sophieuchuyenkho) {
		// TODO Auto-generated method stub
		this.Sophieuchuyenkho= sophieuchuyenkho;
	}

	@Override
	public String getSotieuhaogiacong() {
		// TODO Auto-generated method stub
		return this.Sophieutieuhao;
	}

	@Override
	public void setSotieuhaogiacong(String Sotieuhaogiacong) {
		// TODO Auto-generated method stub
		this.Sophieutieuhao=Sotieuhaogiacong;
	}

	@Override
	public String getSohoadondieuchinhncc() {
		// TODO Auto-generated method stub
		return this.Sohoadondieuchinhnnc;
	}

	@Override
	public void setSohoadondieuchinhncc(String Sohoadondieuchinhncc) {
		// TODO Auto-generated method stub
		this.Sohoadondieuchinhnnc=Sohoadondieuchinhncc;
	}

	
}
