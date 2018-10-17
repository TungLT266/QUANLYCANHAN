package geso.traphaco.erp.beans.nhomchiphi.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;

import geso.traphaco.erp.beans.nhomchiphi.INhomchiphi;

public class Nhomchiphi implements INhomchiphi {

	private String Id;
	private String userId;
	private ResultSet tklist;
	private ResultSet ttcplist;
	private ResultSet ctylist;
	private ResultSet dvttlist;
	private ResultSet nhomlist;
	
	private String ctyId;
	private String dvttId;
	private String parentId;
	private String tkId;
	private String ttcpId;
	private String ten;
	private String diengiai;
	private String loai;
	private String trangthai;		 
	
	private String congty;
	private String donvi;	
	private String msg;
	private boolean search = false;
	private dbutils db;
	private Utility util;
	public Nhomchiphi()
	{
		this.Id = "";
		this.ten = "";
		this.diengiai = "";
		this.loai = "";
		this.trangthai = "";
		this.congty = "";
		this.donvi = "";
		this.tkId = "";
		this.ttcpId = "";
		this.ctyId = "";
		this.dvttId = "";
		this.parentId = "";
		this.msg = "";
		this.db = new dbutils();
		this.util=new Utility();
	}


	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}

	public String getParentId(){
		return this.parentId;
	}
	
	public void setParentId(String parentId){
		this.parentId = parentId;
	}

	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getDvttId(){
		return this.dvttId;
	}
	
	public void setDvttId(String dvttId){
		this.dvttId = dvttId;
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getTen(){
		return this.ten;
	}
	
	public void setTen(String ten){
		this.ten = ten;
	}

	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getLoai(){
		return this.loai;
	}
	
	public void setLoai(String loai){
		this.loai = loai;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	
	public String getCongty(){
		return this.congty;
	}

	public void setCongty(String congty){
		this.congty = congty;
	}

	public ResultSet getCtyList(){
		return this.ctylist;
	}
	
	public void setDonvi(String donvi){
		this.donvi = donvi;
	}

	public String getDonvi(){
		return this.donvi;
	}

	public ResultSet getDvttList(){
		return this.dvttlist;
	}
	
	public ResultSet getTkList(){
		return this.tklist;
	}

	public ResultSet getTtcpList(){
		return this.ttcplist;
	}

	public ResultSet getNhomList(){
		return this.nhomlist;
	}

	public String getTkId(){
		return this.tkId;
	}

	public void setTkId(String tkId){
		this.tkId = tkId;
	}
	
	public String getTtcpId(){
		return this.ttcpId;
	}

	public void setTtcpId(String ttcpId){
		this.ttcpId = ttcpId;
	}

	public void init(){
		String query = 	"SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPTEN, NHOMCHIPHI.DIENGIAI, " + 
						"NHOMCHIPHI.LOAI, NHOMCHIPHI.TRANGTHAI, NHOMCHIPHI.BOSS_FK, " +
						"CASE WHEN NHOMCHIPHI.LOAI = '1' THEN (CONVERT(VARCHAR(18),NHOMCHIPHI.PK_SEQ) + '-0') " +
						"ELSE (CONVERT(VARCHAR(18), NHOMCHIPHI.BOSS_FK) + '-' + CONVERT(VARCHAR(18), NHOMCHIPHI.PK_SEQ)) " +
						"END AS THUTU, " +
						"CONGTY.PK_SEQ AS CTYID, CONGTY.TEN AS CTYTEN, DVTH.PK_SEQ AS DVTHID, DVTH.TEN AS DVTHTEN, ISNULL(TAIKHOAN_FK, '0') AS TKID, " +
						"ISNULL(TTCHIPHI_FK, '0') AS TTCPID " +
						"FROM ERP_NHOMCHIPHI NHOMCHIPHI " +
						"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK " +
						"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK " +		
						"WHERE NHOMCHIPHI.PK_SEQ = '" + this.Id + "' and dvth.pk_seq in "+this.util.quyen_donvithuchien(this.userId) ;
		
		System.out.println("1.Khoi tao chi phi: " + query);
		
		ResultSet rs = this.db.get(query);
		try{
			if(rs!=null)
			while(rs.next()){
				this.ten = rs.getString("NCPTEN");
				this.diengiai = rs.getString("DIENGIAI");
				this.loai = rs.getString("LOAI");
				this.trangthai = rs.getString("TRANGTHAI");
				this.congty = rs.getString("CTYTEN");
				this.ctyId = rs.getString("CTYID");
				this.donvi = rs.getString("DVTHTEN");
				this.dvttId = rs.getString("DVTHID");
				this.parentId = rs.getString("BOSS_FK");
				this.tkId = rs.getString("TKID");
				this.ttcpId = rs.getString("TTCPID");
			}
			rs.close();
		}catch(java.sql.SQLException e){}
		
		createTkList();
		createTtcpList();
		createNhomList();
	}
	
	public boolean Update(){
		String query = "";
		if(this.parentId != null){
			if(this.parentId.length() == 0)
				this.parentId = "null";
		}else{
			this.parentId = "null";
		}
		
		if(this.tkId != null){
			if(this.tkId.length() == 0)
				this.tkId = "null";
		}else{
			this.tkId = "null";
		}

		if(this.ttcpId != null){
			if(this.ttcpId.length() == 0)
				this.ttcpId = "null";
		}else{
			this.ttcpId = "null";
		}

		if(this.loai.equals("2")){ // là tài khoản chi phí
			query = "UPDATE ERP_NHOMCHIPHI SET TEN = N'" + this.ten + "', DIENGIAI = N'" + this.diengiai + "', " +
					"TRANGTHAI = '" + this.trangthai + "', " +
					"TAIKHOAN_FK = " + tkId + ", " + "TTCHIPHI_FK = " + this.ttcpId + ", " +
					"BOSS_FK = " + this.parentId + " " +
				    "WHERE PK_SEQ = '" + this.Id + "'";
		}else{ 
 			query = "UPDATE ERP_NHOMCHIPHI SET TEN = N'" + this.ten + "', DIENGIAI = N'" + this.diengiai + "', " +
					"TRANGTHAI = '" + this.trangthai + "', " +
					"BOSS_FK = " + this.parentId + " " +
			   		"WHERE PK_SEQ = '" + this.Id + "'";			
		}
		System.out.println("1.Câu lệnh cập nhật: " + query);
		return this.db.update(query);
	}
	
	public boolean New(){

		String query1 = "";
		String query2 = "";
		
		if(this.loai.equals("1")){ // Là nhom chi phi
			if(this.parentId.length() == 0){
				query1 = "INSERT INTO ERP_NHOMCHIPHI (TEN, DIENGIAI, LOAI, CONGTY_FK, DONVITHUCHIEN_FK, " +
				 "TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA ) " +
				 "VALUES(N'" + this.ten + "', N'" + this.diengiai + "', '" + this.loai + "', " +
				 "'" + this.ctyId + "','" + this.dvttId + "','" + this.trangthai + "', " +
				 "'" + this.getDateTime() + "','"+ this.getDateTime() + "','" + this.userId + "','" + this.userId + "')";
				
				System.out.println(query1);
				this.db.update(query1);
				
			}else{
				query1 = "INSERT INTO ERP_NHOMCHIPHI (TEN, DIENGIAI, LOAI, BOSS_FK, CONGTY_FK, DONVITHUCHIEN_FK, " +
						 "TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA ) " +
						 "VALUES(N'" + this.ten + "', N'" + this.diengiai + "', '" + this.loai + "'," + this.parentId + ", " +
						 "'" + this.ctyId + "','" + this.dvttId + "','" + this.trangthai + "', " +
						 "'" + this.getDateTime() + "','"+ this.getDateTime() + "','" + this.userId + "','" + this.userId + "')";
				
				System.out.println(query1);
				this.db.update(query1);
			}
		}else{ // là chi phí
			
			query1 = "INSERT INTO ERP_NHOMCHIPHI (TEN, DIENGIAI, LOAI, CONGTY_FK, DONVITHUCHIEN_FK, " +
					 "TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA" ; 
	
			query2 = "VALUES(N'" + this.ten + "', N'" + this.diengiai + "', '" + this.loai + "', '" + this.ctyId + "', " +
					 "'" + this.dvttId + "','" + this.trangthai + "', '" + this.getDateTime() + "','"+ this.getDateTime() + "','" + this.userId + "','" + this.userId + "' ";

			if(this.tkId.length() > 0){
				query1 = query1 + " , TAIKHOAN_FK " ; 
				query2 = query2 + ", '" + this.tkId + "' " ; 
			}

			if(this.ttcpId.length() > 0){
				query1 = query1 + " , TTCHIPHI_FK " ; 
				query2 = query2 + ", '" + this.ttcpId + "' " ; 
				
			}
			
			
			if(this.parentId.length() > 0){
				query1 = query1 + " , BOSS_FK " ; 
				query2 = query2 + ", '" + this.parentId + "' " ; 				
			}

			query1 = query1 + ") " + query2 + " )";
			
			System.out.println(query1);
			this.db.update(query1);
			
/*			query1 = "SELECT PK_SEQ AS ID FROM ERP_LAPNGANSACH ";
			ResultSet rs = this.db.get(query1);
			try{
				while(rs.next()){
				
					query1 = 	"INSERT INTO ERP_LAPNGANSACH_CHIPHI(LAPNGANSACH_FK, DONVITHUCHIEN_FK, NHOMCHIPHI_FK, CHIPHI_FK, DUTOAN, THUCCHI) " +
								"(	SELECT " + rs.getString("ID") + " , DONVITHUCHIEN_FK, BOSS_FK, PK_SEQ, '0', '0' " +
								"	FROM ERP_NHOMCHIPHI WHERE LOAI = 2 AND TRANGTHAI = 1 AND PK_SEQ NOT IN " +
								"(SELECT DISTINCT CHIPHI_FK FROM ERP_LAPNGANSACH_CHIPHI WHERE LAPNGANSACH_FK = '" + rs.getString("ID") + "' )" +
								")";
					
					System.out.println(query1);
					this.db.update(query1);
				}
				rs.close();
			}catch(java.sql.SQLException e){return false;}*/
		}
				
		return true; 
	}

	public void setMsg(String msg) {
		this.msg =msg;
		
	}

	
	public String getMsg() {
		return this.msg;
	}

	public void createCtyList(){		
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY");
	}
	
	public void createDvttList(){
		if(this.ctyId.length() > 0){
			String sql="SELECT PK_SEQ AS DVTTID, TEN AS DVTTTEN FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = '" + this.ctyId + "' and pk_seq in "+this.util.quyen_donvithuchien(this.userId);
			this.dvttlist = this.db.get(sql);
		}
	}

	private void createTkList(){		
		this.tklist =  this.db.get( "SELECT TK.PK_SEQ AS TKID, TK.SOHIEUTAIKHOAN AS MA, TK.TENTAIKHOAN AS TEN " + 
									"FROM ERP_TAIKHOANKT TK " +
									"INNER JOIN ERP_LOAITAIKHOAN LTK ON TK.LOAITAIKHOAN_FK = LTK.PK_SEQ " +
									"WHERE LTK.MA = 'LOAI 6' AND TK.TRANGTHAI = '1' AND TK.CONGTY_FK = " + this.ctyId + " " +
									" ORDER BY MA" );
	}

	private void createTtcpList(){		

		this.ttcplist =  this.db.get("SELECT PK_SEQ AS TTCPID, MA, DIENGIAI FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI='1' AND CONGTY_FK = " + this.ctyId + " " );
		//this.ttcplist =  this.db.get("SELECT PK_SEQ AS TTCPID, MA, TEN FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI='1' AND CC_GROUP = 2 AND CONGTY_FK = " + this.ctyId + " " );
		this.ttcplist =  this.db.get("SELECT PK_SEQ AS TTCPID, MA, DIENGIAI as TEN FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI='1' AND CONGTY_FK = " + this.ctyId + " " );
	}

	private void createNhomList(){		
		String query = ""; 
		
		if(this.ctyId.length()>0 & this.dvttId.length() > 0){
			query = "SELECT PK_SEQ AS NHOMID, TEN + ' - ' + DIENGIAI AS TENNHOM FROM ERP_NHOMCHIPHI " +
					"WHERE LOAI = '1' AND CONGTY_FK = '" + this.ctyId + "' AND DONVITHUCHIEN_FK = '" + this.dvttId + "'" ;
		
			//System.out.println(query);
			if(this.Id.length() > 0){
				query = query + " AND PK_SEQ <> " + this.Id + "";
			}
			
			System.out.println(query);
			this.nhomlist =  this.db.get(query);
		}
	}
	
	public void initNew(){
		createCtyList();
		createDvttList();
		createNhomList();
		createTkList();
		createTtcpList();
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBClose(){
		try{
			if(this.tklist != null) this.tklist.close();
			if(this.ttcplist != null) this.ttcplist.close();
			if(this.ctylist != null) this.ctylist.close();
			if(this.dvttlist != null) this.dvttlist.close();
			if(this.nhomlist != null) nhomlist.close();
			
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
	
}