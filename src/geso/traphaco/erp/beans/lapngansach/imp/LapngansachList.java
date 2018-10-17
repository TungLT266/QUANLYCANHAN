package geso.traphaco.erp.beans.lapngansach.imp;

import geso.traphaco.erp.beans.lapngansach.ILapngansachList;
import geso.traphaco.erp.beans.lapngansach.IDubaokinhdoanhNam_Giay;
import geso.traphaco.erp.beans.lapngansach.imp.DubaokinhdoanhNam_Giay;
import geso.traphaco.erp.db.sql.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class LapngansachList implements ILapngansachList {
	String Id;
	String userId;
	String ngay;
	String ctyId;
	String hieuluc;
	ResultSet lns;
	
	ResultSet ctylist;
	ResultSet ngansachcopy;
	ResultSet nslist;
	String diengiai;
	
	String ttcp, thang;
	ResultSet ttcplist;
	
	String nam;
	String msg;
	String copyId;
	ResultSet rs;
	ResultSet namlist;
	
	dbutils db;
	
	String[] CP_DuocChapNhan;
	String[] CP_BiLoaiBo;
	String[] CP_ThueThuNhap;

	public LapngansachList(){
		this.Id = "";
		this.ctyId = "";
		this.diengiai = "";
		this.nam = "";
		this.hieuluc = "";
		this.nam = "";
		this.thang = "";
		this.msg = "";
		this.copyId = "";
		this.ttcp = "";
		
		this.db = new dbutils();
		this.ngay = this.getDateTime();
		
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}

	public String getNgay(){
		return this.ngay;
	}
	
	public void setNgay(String ngay){
		this.ngay = ngay;
	}
	
	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getCopyId(){
		return this.copyId;
	}
	
	public void setCopyId(String copyId){
		this.copyId = copyId;
	}

	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}

	public String getNamNew(){
		return "" + (Integer.parseInt(this.getDateTime().substring(0, 4)) + 1);
	}

	public String getNamUpdate(){
		return this.nam;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public String getHieuluc(){
		return this.hieuluc;
	}
	
	public void setHieuluc(String hieuluc){
		this.hieuluc = hieuluc;
	}

	public ResultSet getCtylist(){
		return this.ctylist;
	}
	
	public void setCtylist(ResultSet ctylist){
		this.ctylist = ctylist;
	}

	public ResultSet getNgansach(){
		return this.nslist;
	}
	
	public void setNgansach(ResultSet nslist){
		this.nslist = nslist;
	}

	public ResultSet getNgansachCopy(){
		return this.ngansachcopy;
	}
	public ResultSet getNamlist(){
		return this.namlist;
	}
	
	public void setNamlist(ResultSet namlist){
		this.namlist = namlist;
	}
	
	public String getNam(){
		return this.nam;
	}
	
	public void setNam(String nam){
		this.nam = nam;
	}

	public void Duyet(String Id){
		String query = "UPDATE ERP_LAPNGANSACH SET TRANGTHAI = '1' WHERE PK_SEQ = " + Id + " ";
		this.db.update(query);
	}
		
	
	public String getThang() {
		// TODO Auto-generated method stub
		return this.thang;
	}

	
	public void setThang(String thang) {
		// TODO Auto-generated method stub
		this.thang = thang;
	}
	
	public ResultSet getTtcplist() {
		// TODO Auto-generated method stub
		return this.ttcplist;
	}

	public void setTtcplist(ResultSet ttcplist) {
		// TODO Auto-generated method stub
		this.ttcplist = ttcplist;
	}
	
	public String getTtcpId() {
		// TODO Auto-generated method stub
		return this.ttcp;
	}

	public void setTtcpId(String ttcpId) {
		// TODO Auto-generated method stub
		this.ttcp = ttcpId;
	}
	
	public String[] getCP_DuocChapNhan() {
		
		return this.CP_DuocChapNhan;
	}

	
	public void setCP_DuocChapNhan(String[] CP_DuocChapNhan) {
		
		this.CP_DuocChapNhan = CP_DuocChapNhan;
	}

	
	public String[] getCP_BiLoai() {
		
		return this.CP_BiLoaiBo;
	}

	
	public void setCP_BiLoai(String[] CP_BiLoai) {
		
		this.CP_BiLoaiBo = CP_BiLoai;
	}

	
	public String[] getCP_ThueThuNhap() {
		
		return this.CP_ThueThuNhap;
	}

	
	public void setCP_ThueThuNhap(String[] CP_ThueThuNhap) {
		
		this.CP_ThueThuNhap = CP_ThueThuNhap;
	}
	
	public boolean Hieuluc(String Id){
		String query = 	"UPDATE ERP_LAPNGANSACH SET HIEULUC = '" + this.hieuluc + "' " +
						"WHERE PK_SEQ = " + Id + " ";
		
		System.out.println(query);
		if(!this.db.update(query)){
			if(this.hieuluc.equals("1")){
				query = "UPDATE ERP_LAPNGANSACH SET HIEULUC = '0' WHERE PK_SEQ <> " + Id + "";
				System.out.println(query);
				this.db.update(query);
			}
			return true;
			
		}else{
			return false;
		}
		
	}

	private void createNamList(){
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		String query = "";
		for(int i = nam - 5; i <= nam + 1; i++)
		{
			query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
			if(i < nam + 1)
			{
				query += " union all ";
			}
		}
		
		this.namlist = this.db.get(query);

	}
	
	public void createBudget(){
		try{
		if(!this.isExist() && this.Id.trim().length() <= 0 ){
			String query = 	"INSERT INTO ERP_LAPNGANSACH(DIENGIAI, CONGTY_FK, NAM, NGAYTAO, NGUOITAO, HIEULUC) " +
							"VALUES('" + this.diengiai + "', '" + this.ctyId + "', '" + this.getNamNew() + "','" + this.getDateTime() + "','" + this.userId + "','0')";
		
			this.db.update(query);
		
			ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
		
			
			rs.next();
			String Id = rs.getString("ID");
			rs.close();
		}else{	
			String query = "";
			boolean checked = false;
			query = "SELECT COUNT(*) AS NUM FROM ERP_LAPNGANSACH WHERE HIEULUC = '0' AND PK_SEQ = " + this.Id + " AND 1 = " + this.hieuluc;
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			rs.next();

			if(rs.getInt("NUM") > 0){
				checked = true;
			}
			rs.close();

			query = 	"UPDATE ERP_LAPNGANSACH SET DIENGIAI = N'" + this.diengiai + "', HieuLuc = '" + ( this.hieuluc.trim().length() <= 0 ? "0" : this.hieuluc ) + "' " +
						"WHERE PK_SEQ = " + this.Id + " ";
			System.out.println(query);
			this.db.update(query);
			
			
			if(checked){
				query = "INSERT INTO ERP_LAPNGANSACH_CHIPHI(DONVITHUCHIEN_FK, THTC_FK, CHIPHI_FK, DUTOAN, THUCCHI, LAPNGANSACH_FK) " +
						"( " +
						"	SELECT DISTINCT NCP.DONVITHUCHIEN_FK, null, NCP.PK_SEQ, 0, 0, " + Id + " " + 
						"	FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"	INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.TTCHIPHI_FK = TTCP.PK_SEQ " + 
						"	INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NCP.DONVITHUCHIEN_FK " +
						"	WHERE TTCP.CONGANSACH=1 AND TTCP.CONGTY_FK = " + this.ctyId + " AND NCP.PK_SEQ NOT IN " +
						"	(" +
						"		SELECT CHIPHI_FK FROM ERP_LAPNGANSACH_CHIPHI WHERE LAPNGANSACH_FK = '" + this.Id + "' " +
						"	) " +
						") ";
				
//				System.out.println("1.INsert: " + query);
				this.db.update(query);
					
					// Tao truoc 1 ban copy cac trung tam chi phi cho tung thang cua nam moi
				for(int i = 1; i <= 12; i++){
					query = "INSERT INTO ERP_TRUNGTAMCHIPHI_NGANSACH (TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK)" +
							"SELECT PK_SEQ, " + i + ", " + this.getNamNew() + ", 0, " + Id + " FROM ERP_TRUNGTAMCHIPHI " +
							"WHERE CONGTY_FK = " + this.ctyId + " AND TRANGTHAI = 1 AND PK_SEQ NOT IN "  +
							"	(SELECT TTCP_FK FROM ERP_TRUNGTAMCHIPHI_NGANSACH WHERE THANG = " + i + " AND NAM = " + this.getNamNew() + " AND " +
							"LAPNGANSACH_FK  = '" + this.Id + "') " ;
						
//					System.out.println("2.Insert: " + query);
						
					this.db.update(query);
				}
					
				// Ke hoach khau hao cho nam toi cua nhung tai san da mua
				this.KHKhauhaoTaisanHienco(Id);
				this.KHKhauhaoCCDCHienco(Id);
				
				query = "INSERT INTO ERP_LAPNGANSACH_DOANHTHU(DONVITHUCHIEN_FK, THTC_FK, DOANHTHU_FK, DUTOAN, THUCCHI, LAPNGANSACH_FK) " +
						"( " +
						"	SELECT DISTINCT DT.DONVITHUCHIEN_FK, null, DT.PK_SEQ, 0, 0, " + Id + " " + 
						"	FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"	INNER JOIN ERP_DOANHTHU DT ON DT.TTDOANHTHU_FK = TTDT.PK_SEQ " + 
						"	INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DT.DONVITHUCHIEN_FK " +
						"	WHERE TTDT.CONGANSACH=1 AND TTDT.CONGTY_FK = " + this.ctyId + " AND DT.PK_SEQ NOT IN (" +
						" 		SELECT DOANHTHU_FK FROM ERP_LAPNGANSACH_DOANHTHU WHERE LAPNGANSACH_FK = " + this.Id + " ) " +	
						") ";
				
//				System.out.println("3.Insert: " + query);
				this.db.update(query);
					
				// Tao truoc 1 ban copy cac trung tam chi phi cho tung thang cua nam moi
				for(int i = 1; i <= 12; i++){
					query = "INSERT INTO ERP_TRUNGTAMDOANHTHU_NGANSACH (TTDT_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK)" +
							"SELECT PK_SEQ, " + i + ", " + this.getNamNew() + ", 0, " + Id + " FROM ERP_TRUNGTAMDOANHTHU " +
							"WHERE CONGTY_FK = " + this.ctyId + " AND TRANGTHAI = 1 AND PK_SEQ NOT IN "  +
							"	(SELECT TTDT_FK FROM ERP_TRUNGTAMDOANHTHU_NGANSACH WHERE THANG = " + i + " AND NAM = " + this.getNamNew() + " AND " +
							"LAPNGANSACH_FK  = '" + this.Id + "') " ;
						
//					System.out.println("4.Insert: " + query);
						
					this.db.update(query);
				}
							
			}
		}
		}
		catch(Exception e)
		{
			System.out.println("____Exception: " + e.getMessage() );
		}
	}
	
	private void KHKhauhaoTaisanHienco(String Id){
		String query = 	"SELECT DISTINCT A.TSID, A.DIENGIAI, A.TTCP_FK AS TTCPID, B.KHAUHAODUKIEN AS KHAUHAO, A.SOTHANGKHAUHAONAMTOI "  +
						"FROM  " +
						"(  " +
						"	SELECT	TSCD.PK_SEQ AS TSID, TSCD.TTCP_FK, TSCD.DIENGIAI, TSCD.SOTHANGKH, ISNULL(KH.THANGDAKH, 0) AS THANGDAKH, " +  
						"	TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) AS SOTHANGKHCONLAI,  " +
						"	CASE WHEN TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) > (12-ISNULL(KH.THANG,0)) " +  			
						"	THEN   " +
						"	CASE WHEN (TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-ISNULL(KH.THANG,0))) > 12 " +  
						"	THEN 12  " +
						"	ELSE (TSCD.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-ISNULL(KH.THANG,0))) " +  
						"	END	 " +				
						"	ELSE 0 END AS SOTHANGKHAUHAONAMTOI " +  										
						"	FROM ERP_TAISANCODINH TSCD  " +
						"	INNER JOIN ERP_TAISANCODINH_CONGDUNG TSCD_CD ON TSCD.PK_SEQ = TSCD_CD.TAISAN_FK " +
						"	LEFT JOIN  " +
						"	(  " +
						"		SELECT TAISAN_FK, MAX(THANGTHU) AS THANGDAKH, THANG, NAM " +  
						"		FROM ERP_KHAUHAOTAISAN WHERE NAM =   '" + Integer.parseInt(this.getDateTime().substring(0, 4)) + "'  " +  
						"		GROUP BY TAISAN_FK, THANG, NAM  " +
						"	)KH ON KH.TAISAN_FK = TSCD.PK_SEQ  " +					
						")A  " +
						"INNER JOIN " +  
						"(  " +
						"	SELECT TAISAN_FK AS TSID , SUM(KHAUHAODUKIEN)/COUNT(TAISAN_FK) AS KHAUHAODUKIEN " +  
						"	FROM ERP_TAISANCODINH_CHITIET  " +
						"	GROUP BY TAISAN_FK  " +
						")B ON B.TSID = A.TSID  " +
						"WHERE A.SOTHANGKHAUHAONAMTOI > 0 "; 
		
		System.out.println("22.Khau hao TS: " + query);
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try{
				
				while(rs.next()){
					
					int sothang = Integer.parseInt(rs.getString("SOTHANGKHAUHAONAMTOI"));
					
					for (int i = 1; i <= sothang; i++)
					{
						long ngansach = Math.round(rs.getDouble("KHAUHAO"));
						query = "INSERT INTO ERP_TRUNGTAMCHIPHI_LNS_TAISAN(TTCP_FK, THANG, NAM, NGANSACH, LNS_TAISAN_FK, LAPNGANSACH_FK) VALUES( " +
								"" + rs.getString("TTCPID") + ", '" + i + "', '" + this.getNamNew() + "', '" + Long.toString(ngansach) + "', '1_" + rs.getString("TSID") + "', " + Id + ")";
						
						System.out.println("2.3.Khau hao TS: " + query);
						this.db.update(query);
					}
				}
			}catch(java.sql.SQLException e){}
		}
	}
	
	private void KHKhauhaoCCDCHienco(String Id){
		String query = 	"SELECT DISTINCT A.CCDCID, A.DIENGIAI, A.TTCP_FK AS TTCPID, B.KHAUHAODUKIEN AS KHAUHAO, A.SOTHANGKHAUHAONAMTOI "  +
						"FROM  " +
						"(  " +
						"	SELECT	CCDC.PK_SEQ AS CCDCID, CCDC.TTCP_FK, CCDC.DIENGIAI, CCDC.SOTHANGKH, ISNULL(KH.THANGDAKH, 0) AS THANGDAKH, " +  
						"	CCDC.SOTHANGKH-ISNULL(KH.THANGDAKH,0) AS SOTHANGKHCONLAI,  " +
						"	CASE WHEN CCDC.SOTHANGKH-ISNULL(KH.THANGDAKH,0) > (12-ISNULL(KH.THANG,0)) " +  			
						"	THEN   " +
						"	CASE WHEN (CCDC.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-ISNULL(KH.THANG,0))) > 12 " +  
						"	THEN 12  " +
						"	ELSE (CCDC.SOTHANGKH-ISNULL(KH.THANGDAKH,0) - (12-ISNULL(KH.THANG,0))) " +  
						"	END	 " +				
						"	ELSE 0 END AS SOTHANGKHAUHAONAMTOI " +  										
						"	FROM ERP_CONGCUDUNGCU CCDC  " +
						"	INNER JOIN ERP_CONGCUDUNGCU_CONGDUNG CCDC_CD ON CCDC.PK_SEQ = CCDC_CD.CCDC_FK " +
						"	LEFT JOIN  " +
						"	(  " +
						"		SELECT CCDC_FK, MAX(THANGTHU) AS THANGDAKH, THANG, NAM " +  
						"		FROM ERP_KHAUHAOCCDC WHERE NAM =   '" + Integer.parseInt(this.getDateTime().substring(0, 4)) + "'  " +  
						"		GROUP BY CCDC_FK, THANG, NAM  " +
						"	)KH ON KH.CCDC_FK = CCDC.PK_SEQ  " +					
						")A  " +
						"INNER JOIN " +  
						"(  " +
						"	SELECT CCDC_FK AS CCDCID , SUM(KHAUHAODUKIEN)/COUNT(CCDC_FK) AS KHAUHAODUKIEN " +  
						"	FROM ERP_CONGCUDUNGCU_CHITIET  " +
						"	GROUP BY CCDC_FK  " +
						")B ON B.CCDCID = A.CCDCID  " +
						"WHERE A.SOTHANGKHAUHAONAMTOI > 0 "; 
		
		System.out.println("22.Khau hao TS: " + query);
		ResultSet rs = this.db.get(query);
		
		if(rs != null){
			try{
				
				while(rs.next()){
					
					int sothang = Integer.parseInt(rs.getString("SOTHANGKHAUHAONAMTOI"));
					
					for (int i = 1; i <= sothang; i++)
					{
						long ngansach = Math.round(rs.getDouble("KHAUHAO"));
						query = "INSERT INTO ERP_TRUNGTAMCHIPHI_LNS_CCDC(TTCP_FK, THANG, NAM, NGANSACH, LNS_CCDC_FK, LAPNGANSACH_FK) VALUES( " +
								"" + rs.getString("TTCPID") + ", '" + i + "', '" + this.getNamNew() + "', '" + Long.toString(ngansach) + "', '1_" + rs.getString("CCDCID") + "', " + Id + ")";
						
						System.out.println("2.3.Khau hao TS: " + query);
						this.db.update(query);
					}
				}
			}catch(java.sql.SQLException e){}
		}
	}

	public void init(){
		String query = 	"SELECT LNS.PK_SEQ AS NSID, CTY.TEN AS CTY, LNS.DIENGIAI, LNS.NAM, LNS.HIEULUC, NV.TEN AS NGUOITAO, LNS.NGAYTAO " +
						"FROM ERP_LAPNGANSACH LNS " +
						"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = LNS.NGUOITAO " +
						"WHERE CTY.PK_SEQ = '" + this.ctyId + "' ";
				
		if(this.diengiai.length() > 0){
			query = query + " AND LNS.DIENGIAI LIKE '%" + this.diengiai + "%'" ;
		}
		
		query = query + "ORDER BY LNS.NAM DESC";
		
		System.out.println(query);
		this.nslist = this.db.get(query);
		
		this.ngansachcopy = this.db.get("SELECT LNS.PK_SEQ AS LNSID, LNS.DIENGIAI " +
				   						"FROM ERP_LAPNGANSACH LNS " +
				   						"WHERE LNS.CONGTY_FK = " + this.ctyId + " ");

	}

	public void init_duyetngansach(){
		String query = "SELECT * FROM ERP_LAPNGANSACH WHERE CONGTY_FK = " + this.ctyId + " ";
		
		if(this.diengiai.length() > 0){
			query = query + " AND DIENGIAI LIKE '%" + this.diengiai +"%' ";
		}
		
		if(this.nam.length() > 0){
			query = query + " AND NAM = '" + this.nam + "' ";
		}
		
		query = query + " ORDER BY NAM, TRANGTHAI, HIEULUC DESC ";
		
		System.out.println(query);
		
		this.nslist = this.db.get(query);
		
		this.createNamList();
	}
	
	public void initUpdate(){
		String query = 	"SELECT LNS.PK_SEQ AS NSID, CTY.PK_SEQ AS CTYID, CTY.TEN AS CTY, LNS.DIENGIAI, LNS.NAM, LNS.HIEULUC, NV.TEN AS NGUOITAO, LNS.NGAYTAO " +
						"FROM ERP_LAPNGANSACH LNS " +
						"INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = LNS.CONGTY_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = LNS.NGUOITAO " +
						"WHERE LNS.PK_SEQ = '" + this.Id + "' ";
		
		System.out.println(query);
		this.nslist = this.db.get(query);
		try{
			this.nslist.next();
			this.diengiai = this.nslist.getString("DIENGIAI");
			this.nam = this.nslist.getString("NAM");
			this.hieuluc =  this.nslist.getString("HIEULUC");
			this.nslist.close();
		}catch(java.sql.SQLException e){}
		
		
		
	}

	public void Delete(String Id){
		String query = "SELECT HIEULUC FROM ERP_LAPNGANSACH WHERE PK_SEQ = '" + Id + "'";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String trangthai = rs.getString("HIEULUC");
			rs.close();
			if(trangthai.equals("0")){
				this.db.update("DELETE FROM ERP_LAPNGANSACH_CHIPHI WHERE LAPNGANSACH_FK = '" + Id + "' ");
				this.db.update("DELETE FROM ERP_LAPNGANSACH WHERE PK_SEQ = '" + Id + "' ");
			}
		}catch(java.sql.SQLException e){}
	}
	
	private boolean isExist()
	{
		String query = "SELECT COUNT(*) AS NUM FROM ERP_LAPNGANSACH WHERE NAM = '" + this.getNamNew() + "' AND CONGTY_FK = '" + this.ctyId + "' ";
		System.out.println("__Check Nam: " + query);
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String tmp = rs.getString("NUM");
			rs.close();
			
			if(!tmp.equals("0")){
				return true;
			}else{
				return false;
			}
		}catch(java.sql.SQLException e){return false;}
		
	}
	
	public void CopyNgansach(){
		// Tạo ngân sách mới
		String query = 	"INSERT INTO ERP_LAPNGANSACH (DIENGIAI, CONGTY_FK, NAM, NGUOITAO, NGAYTAO, HIEULUC, TRANGTHAI) " +
						"SELECT N'Vui lòng cập nhật diễn giải', CONGTY_FK,  NAM, '" + this.userId + "', '" + this.getDateTime() + "', '0', '0' " + 
						"FROM ERP_LAPNGANSACH WHERE PK_SEQ = '" + this.copyId + "' ";
		
		System.out.println(query);
		this.db.update(query);
			
		query = "SELECT SCOPE_IDENTITY() AS ID ";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String Id = rs.getString("ID");
			rs.close();
					
			// Copy dự báo
			query = "INSERT INTO ERP_LAPNGANSACH_DUBAO (DVKD_FK, KHO_FK, DIENGIAI, TRANGTHAI, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, NGAYTONKHOANTOAN, CONGTY_FK, NAM, LAPNGANSACH_FK) " +
					"SELECT  DVKD_FK, KHO_FK, DIENGIAI, TRANGTHAI, " + this.userId + ", '" + this.getDateTime() + "', " + this.userId + ", '" + this.getDateTime() + "', NGAYTONKHOANTOAN, CONGTY_FK, NAM, " + Id + " " +
					"FROM ERP_LAPNGANSACH_DUBAO WHERE LAPNGANSACH_FK = " + this.copyId + " AND TRANGTHAI = 1 ";
			
			System.out.println(query);
			this.db.update(query);
			
			query = "INSERT INTO ERP_LAPNGANSACH_DUBAOSANPHAM (DUBAO_FK, SANPHAM_FK, KHACHHANG_FK, THANG, LOAISOSANH, NAM, TONDAU, TANGTRUONG, DUKIENBAN, TONKHOANTOAN, SANXUAT, TONCUOI, SONGAYBANHANG, AVG3M, AVG6M, LASTYEAR, DONGIA, THANHTIEN ) " +
					"SELECT	DISTINCT DB.PK_SEQ AS DUBAO_FK, DBSP.SANPHAM_FK, DBSP.KHACHHANG_FK, DBSP.THANG, DBSP.LOAISOSANH, DBSP.NAM, DBSP.TONDAU, DBSP.TANGTRUONG, " + 
					"		DBSP.DUKIENBAN, DBSP.TONKHOANTOAN, DBSP.SANXUAT, DBSP.TONCUOI, DBSP.SONGAYBANHANG, DBSP.AVG3M, DBSP.AVG6M, DBSP.LASTYEAR, DBSP.DONGIA, DBSP.THANHTIEN  " +
					"FROM ERP_LAPNGANSACH_DUBAO DB " +
					"LEFT JOIN ERP_LAPNGANSACH_DUBAOSANPHAM DBSP ON DBSP.DUBAO_FK IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_DUBAO WHERE LAPNGANSACH_FK = " + this.copyId + " AND TRANGTHAI = 1) " +
					"LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DBSP.SANPHAM_FK AND SP.DVKD_FK = DB.DVKD_FK " +
					"WHERE DB.PK_SEQ IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_DUBAO WHERE LAPNGANSACH_FK = " + Id + ") ";
			
			System.out.println(query);
			this.db.update(query);
			
			query = "INSERT INTO ERP_LAPNGANSACH_CCDC(LAPNGANSACH_FK, DONVITHUCHIEN_FK, DIENGIAI, NHOMCCDC_FK, SOLUONG, DONGIA, THANHTIEN, NGUOITAO, NGAYTAO, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, SOTHANGKH, THUCCHI) " +
					"SELECT " + Id + " , DONVITHUCHIEN_FK, DIENGIAI, NHOMCCDC_FK, SOLUONG, DONGIA, THANHTIEN, " + this.userId + ", '" + this.getDateTime() + "', T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, SOTHANGKH, THUCCHI " +
					"FROM ERP_LAPNGANSACH_CCDC WHERE LAPNGANSACH_FK = " + this.copyId + " ";
			
			System.out.println(query);
			this.db.update(query);

			query = "SELECT SCOPE_IDENTITY() AS LNSCCDCID ";
			rs = this.db.get(query);
			rs.next();
			String lnsCCDC_Id = rs.getString("LNSCCDCID");
			rs.close();

			// CCDC nam trong ngan sach
			query = "INSERT INTO ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN (LAPNGANSACH_CCDC_FK, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, THANG, NAM) " +
					"SELECT " + lnsCCDC_Id + ", KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, THANG, NAM " +
					"FROM ERP_LAPNGANSACH_CCDC_KHAUHAODUKIEN WHERE LAPNGANSACH_CCDC_FK IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_CCDC WHERE LAPNGANSACH_FK = "  + this.copyId +  ")" ;
			
			System.out.println(query);
			this.db.update(query);
			
			query = "INSERT INTO ERP_LAPNGANSACH_CCDC_PHANBO (LNSCCDC_FK, TTCP_FK, PHANTRAM) " +
					"SELECT " + lnsCCDC_Id + ", TTCP_FK, PHANTRAM FROM ERP_LAPNGANSACH_CCDC_PHANBO WHERE LNSCCDC_FK IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_CCDC WHERE LAPNGANSACH_FK = "  + this.copyId +  ")" ;
			
			System.out.println(query);
			this.db.update(query);
			
			// CCDC da mua, nen da co TTCP roi
			query = "INSERT INTO ERP_TRUNGTAMCHIPHI_LNS_CCDC(TTCP_FK, THANG, NAM, NGANSACH, LNS_CCDC_FK, LAPNGANSACH_FK) " +
					"SELECT TTCP_FK, THANG, NAM, NGANSACH, LNS_CCDC_FK, " + Id + " " +
					"FROM ERP_TRUNGTAMCHIPHI_LNS_CCDC WHERE LAPNGANSACH_FK = " + this.copyId + " ";
				
			System.out.println(query);
			this.db.update(query);
			
			query = "INSERT INTO ERP_LAPNGANSACH_TAISAN(LAPNGANSACH_FK, DONVITHUCHIEN_FK, DIENGIAI, NHOMTAISAN_FK, SOLUONG, DONGIA, THANHTIEN, NGUOITAO, NGAYTAO, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, SOTHANGKH, THUCCHI) " +
					"SELECT " + Id + " , DONVITHUCHIEN_FK, DIENGIAI, NHOMTAISAN_FK, SOLUONG, DONGIA, THANHTIEN, " + this.userId + ", '" + this.getDateTime() + "', T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12, SOTHANGKH, THUCCHI " +
					"FROM ERP_LAPNGANSACH_TAISAN WHERE LAPNGANSACH_FK = " + this.copyId + " ";
			
			System.out.println(query);
			this.db.update(query);

			query = "SELECT SCOPE_IDENTITY() AS LNSTAISANID ";
			rs = this.db.get(query);
			rs.next();
			String lnsTaisan_Id = rs.getString("LNSTAISANID");
			rs.close();
						
			query = "INSERT INTO ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN (LAPNGANSACH_TAISAN_FK, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, THANG, NAM) " +
					"SELECT " + lnsTaisan_Id + ", KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, THANG, NAM " +
					"FROM ERP_LAPNGANSACH_TAISAN_KHAUHAODUKIEN WHERE LAPNGANSACH_TAISAN_FK IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_TAISAN WHERE LAPNGANSACH_FK = "  + this.copyId +  ")" ;
			
			System.out.println(query);
			this.db.update(query);
	
			query = "INSERT INTO ERP_LAPNGANSACH_TAISAN_PHANBO (LNSTAISAN_FK, TTCP_FK, PHANTRAM) " +
					"SELECT " + lnsTaisan_Id + ", TTCP_FK, PHANTRAM FROM ERP_LAPNGANSACH_TAISAN_PHANBO WHERE LNSTAISAN_FK IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_TAISAN WHERE LAPNGANSACH_FK = "  + this.copyId +  ")" ;
			
			System.out.println(query);
			this.db.update(query);

			query = "INSERT INTO ERP_TRUNGTAMCHIPHI_LNS_TAISAN(TTCP_FK, THANG, NAM, NGANSACH, LNS_TAISAN_FK, LAPNGANSACH_FK) " +
					"SELECT TTCP_FK, THANG, NAM, NGANSACH, LNS_TAISAN_FK, " + Id + " " +
					"FROM ERP_TRUNGTAMCHIPHI_LNS_TAISAN WHERE LAPNGANSACH_FK = " + this.copyId + " ";

			System.out.println(query);
			this.db.update(query);
			
			query = "INSERT INTO ERP_LAPNGANSACH_CHIPHI(LAPNGANSACH_FK, DONVITHUCHIEN_FK, THTC_FK, CHIPHI_FK, DUTOAN, THUCCHI, NGAYSUA, NGUOISUA, PHANBO, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) " +
					"SELECT " + Id + ", DONVITHUCHIEN_FK, THTC_FK, CHIPHI_FK, DUTOAN, THUCCHI, '" + this.getDateTime()  +"', " + this.userId +  ", PHANBO, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 " + 
					"FROM ERP_LAPNGANSACH_CHIPHI WHERE LAPNGANSACH_FK = " + this.copyId + " ";
			
			System.out.println(query);
			this.db.update(query);

			rs = this.db.get("SELECT NAM FROM ERP_LAPNGANSACH WHERE PK_SEQ = " + Id + " ");
			
			rs.next();
			this.nam = rs.getString("NAM");
			rs.close();
			
			// Tao truoc 1 ban copy cac trung tam chi phi cho tung thang cua nam moi
			for(int i = 1; i <= 12; i++){
				query = "INSERT INTO ERP_TRUNGTAMCHIPHI_NGANSACH (TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK)" +
						"SELECT PK_SEQ, " + i + ", " + this.nam + ", 0, " + Id + " FROM ERP_TRUNGTAMCHIPHI " +
						"WHERE CONGTY_FK = " + this.ctyId + " AND TRANGTHAI = 1 AND PK_SEQ NOT IN "  +
						"	(SELECT TTCP_FK FROM ERP_TRUNGTAMCHIPHI_NGANSACH WHERE THANG = " + i + " AND NAM = " + this.nam + " AND " +
							"LAPNGANSACH_FK  = '" + this.Id + "') " ;
				
				System.out.println("2.Insert: " + query);
				
				this.db.update(query);
			}
			
			
			//INSERT BANG TEMP
			query = "insert ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP(TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK)  " +
					"select TTCP_FK, THANG, NAM, NGANSACH, '" + this.copyId + "' " +
					"from ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP where LAPNGANSACH_FK = '" + this.Id + "'";
			this.db.update(query);
			
			
			query = "insert ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP_CHIPHI(TTCP_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK)  " +
					"select TTCP_FK, THANG, NAM, NGANSACH, '" + this.copyId + "' " +
					"from ERP_TRUNGTAMCHIPHI_NGANSACH_TEMP_CHIPHI where LAPNGANSACH_FK = '" + this.Id + "'";
			this.db.update(query);
			
			
			query = "INSERT INTO ERP_LAPNGANSACH_DOANHTHU(DONVITHUCHIEN_FK, THTC_FK, DOANHTHU_FK, DUTOAN, THUCCHI, LAPNGANSACH_FK) " +
					"( " +
					"	SELECT DISTINCT DT.DONVITHUCHIEN_FK, null, DT.PK_SEQ, 0, 0, " + Id + " " + 
					"	FROM ERP_TRUNGTAMDOANHTHU TTDT " +
					"	INNER JOIN ERP_DOANHTHU DT ON DT.TTDOANHTHU_FK = TTDT.PK_SEQ " + 
					"	INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DT.DONVITHUCHIEN_FK " +
					"	WHERE TTDT.CONGANSACH=1 AND TTDT.CONGTY_FK = " + this.ctyId + " AND DT.PK_SEQ NOT IN (" +
					" 		SELECT DOANHTHU_FK FROM ERP_LAPNGANSACH_DOANHTHU WHERE LAPNGANSACH_FK = " + this.Id + " ) " +	
					") ";
		
			System.out.println("3.Insert: " + query);
			this.db.update(query);
			

			query = "INSERT INTO ERP_LAPNGANSACH_DOANHTHU(LAPNGANSACH_FK, DONVITHUCHIEN_FK, THTC_FK, DOANHTHU_FK, DUTOAN, THUCCHI, NGAYSUA, NGUOISUA, PHANBO, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12) " +
					"SELECT " + Id + ", DONVITHUCHIEN_FK, THTC_FK, DOANHTHU_FK, DUTOAN, THUCCHI, '" + this.getDateTime()  +"', " + this.userId +  ", PHANBO, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12 " + 
					"FROM ERP_LAPNGANSACH_DOANHTHU WHERE LAPNGANSACH_FK = " + this.copyId + " ";
			
			System.out.println(query);
			this.db.update(query);

			// Tao truoc 1 ban copy cac trung tam chi phi cho tung thang cua nam moi
			for(int i = 1; i <= 12; i++){
				query = "INSERT INTO ERP_TRUNGTAMDOANHTHU_NGANSACH (TTDT_FK, THANG, NAM, NGANSACH, LAPNGANSACH_FK)" +
						"SELECT PK_SEQ, " + i + ", " + this.nam + ", 0, " + Id + " FROM ERP_TRUNGTAMDOANHTHU " +
						"WHERE CONGTY_FK = " + this.ctyId + " AND TRANGTHAI = 1 AND PK_SEQ NOT IN "  +
						"	(SELECT TTDT_FK FROM ERP_TRUNGTAMDOANHTHU_NGANSACH WHERE THANG = " + i + " AND NAM = " + this.nam + " AND " +
							"LAPNGANSACH_FK  = '" + this.Id + "') " ;
				
				System.out.println("4.Insert: " + query);
				
				this.db.update(query);
			}
			
			IDubaokinhdoanhNam_Giay dbBean = new DubaokinhdoanhNam_Giay();
			dbBean.setCtyId(this.ctyId);
			
			rs = this.db.get("SELECT COUNT(DISTINCT SANPHAM_FK) AS NUM FROM ERP_LAPNGANSACH_DUBAOSANPHAM WHERE DUBAO_FK IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_DUBAO WHERE LAPNGANSACH_FK = " + Id + ") ");
			rs.next();
			int n = rs.getInt("NUM");
			rs.close();
			String[] spIds = new String[n];
			
			rs = this.db.get("SELECT DISTINCT SANPHAM_FK FROM ERP_LAPNGANSACH_DUBAOSANPHAM WHERE DUBAO_FK IN (SELECT PK_SEQ FROM ERP_LAPNGANSACH_DUBAO WHERE LAPNGANSACH_FK = " + Id + ") ");
			int i = 0;
			while(rs.next()){
				spIds[i] = rs.getString("SANPHAM_FK");
			}
			rs.close();
			
			dbBean.setSanPhamIds(spIds);
			dbBean.setNsId(Id);
			dbBean.setUserId(Id);
			
			rs = this.db.get("SELECT PK_SEQ FROM ERP_LAPNGANSACH_DUBAO WHERE LAPNGANSACH_FK = " + Id + "");
			while(rs.next()){				
				dbBean.setId(rs.getString("PK_SEQ"));
				dbBean.UpdateCopyLNS();
			}
			rs.close();	
			dbBean.close();
			
		}catch(java.sql.SQLException e){}
		
	}
	
	private void createTtcpList()
	{
		String query = "";		
		query = "SELECT PK_SEQ, diengiai FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = '1'";
		System.out.println(query);
		this.ttcplist = db.get(query);		
	}
	
	public void initTTCP(){
		this.createTtcpList();
	}

	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	
	public void DBClose(){
		try{
			if(this.nslist != null) this.nslist.close();
			if(this.rs != null) this.rs.close();
			if(this.ngansachcopy != null) this.ngansachcopy.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
}
