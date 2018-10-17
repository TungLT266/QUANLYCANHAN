package geso.traphaco.erp.beans.nhomchiphi.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.hoadontaichinh.imp.ErpHoadontaichinh;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.nhomchiphi.IChiphi;
import geso.traphaco.erp.util.UtilitySyn;

public class Chiphi implements IChiphi 
{
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
	private String[] loai;
	private String trangthai;
	
	private String kmDacBiet;
	
	private String congty;
	private String donvi;	
	private String msg;
//	private boolean search = false;
	private dbutils db;
//	private Utility util;
	public Chiphi()
	{
		this.Id = "";
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "";
		this.congty = "";
		this.donvi = "";
		this.tkId = "";
		this.ttcpId = "";
		this.ctyId = "";
		this.dvttId = "";
		this.parentId = "";
		this.msg = "";
		this.kmDacBiet = "0";
		this.db = new dbutils();
//		this.util=new Utility();
	}

	public String getKmDacBiet() {
		return kmDacBiet;
	}

	public void setKmDacBiet(String kmDacBiet) {
		this.kmDacBiet = kmDacBiet;
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
	
	public String[] getLoai(){
		return this.loai;
	}
	
	public void setLoai(String[] loai){
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
		String query = 	"SELECT	NHOMCHIPHI.PK_SEQ AS NCPID, NHOMCHIPHI.TEN AS NCPTEN, NHOMCHIPHI.DIENGIAI, NHOMCHIPHI.KMCHIPHIDACBIET, " + 
						"NHOMCHIPHI.TRANGTHAI, " +
						"ISNULL(DVTH.PK_SEQ, 0) AS DVTHID, ISNULL(DVTH.TEN, '')  AS DVTHTEN, ISNULL(TAIKHOAN_FK, '0') AS TKID, " +
						"ISNULL(TTCHIPHI_FK, '0') AS TTCPID " +
						"FROM ERP_NHOMCHIPHI NHOMCHIPHI " +
						"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = NHOMCHIPHI.CONGTY_FK " +
						"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = NHOMCHIPHI.DONVITHUCHIEN_FK " +		
						"WHERE NHOMCHIPHI.PK_SEQ = '" + this.Id + "' " ;
		
		System.out.println("1.Khoi tao chi phi: " + query);
		
		ResultSet rs = this.db.get(query);
		try{
			if(rs!=null)
				if(rs.next()){
					this.ten = rs.getString("NCPTEN");
					this.diengiai = rs.getString("DIENGIAI");
					this.trangthai = rs.getString("TRANGTHAI");
					this.donvi = rs.getString("DVTHTEN");
					this.dvttId = rs.getString("DVTHID");
					this.tkId = rs.getString("TKID");
					this.ttcpId = rs.getString("TTCPID");
					this.kmDacBiet = rs.getString("KMCHIPHIDACBIET");
				}
			rs.close();
		
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}

		createTkList();
		createTtcpList();
		this.createDvttList();
	}
	
	public boolean Update(){
		String query = "";
		
		query = "UPDATE ERP_NHOMCHIPHI SET TEN = N'" + this.ten + "', DIENGIAI = N'" + this.diengiai + "',  " +
				"TRANGTHAI = '" + this.trangthai + "', DONVITHUCHIEN_FK = " + this.dvttId + ", NGUOISUA = '" + this.userId + "', NGAYSUA = '" + Utility.getCurrentDate() + "', " +
				"TAIKHOAN_FK = " + this.tkId + ", " + "TTCHIPHI_FK = " + this.ttcpId + ", KMCHIPHIDACBIET = " + this.kmDacBiet + 
				" WHERE PK_SEQ = '" + this.Id + "'";
		System.out.println("1.Câu lệnh cập nhật: " + query);	
		this.db.update(query);
			
		//SYN QUA DMS
		UtilitySyn.SynData(db, "ERP_NHOMCHIPHI", "ERP_NHOMCHIPHI", "PK_SEQ", this.Id, "1", false);
		
		return true;
	}
	
	public boolean New()
	{
		String query1 = "";
		String query2 = "";
		
		try{	
			query1 = "INSERT INTO ERP_NHOMCHIPHI (TEN, DIENGIAI, CONGTY_FK, " +
					"TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, KMCHIPHIDACBIET" ; 
	
			query2 = "VALUES(N'" + this.ten + "', N'" + this.diengiai + "', '" + this.ctyId + "', " +
					"'" + this.trangthai + "', '" + Utility.getCurrentDate() + "','"+ Utility.getCurrentDate() + "','" + this.userId + "','" + this.userId + "', " + this.kmDacBiet;

			if(this.dvttId.length() > 0){
				query1 = query1 + ", DONVITHUCHIEN_FK ";
				query2 = query2 + ", '" + this.dvttId + "' ";
			}
		
			if(this.tkId.length() > 0){
				query1 = query1 + " , TAIKHOAN_FK " ; 
				query2 = query2 + ", '" + this.tkId + "' " ; 
			}

			if(this.ttcpId.length() > 0){
				query1 = query1 + " , TTCHIPHI_FK " ; 
				query2 = query2 + ", '" + this.ttcpId + "' " ; 
			
			}
			
			query1 = query1 + ") " + query2 + " )";
			
			System.out.println(query1);
			this.db.update(query1);
			
			ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
			rs.next();
			
			this.Id = rs.getString("ID");
			
			//SYN QUA DMS			
			UtilitySyn.SynData(db, "ERP_NHOMCHIPHI", "ERP_NHOMCHIPHI", "PK_SEQ", this.Id, "0", false);
			
		}catch(java.sql.SQLException e){
			e.printStackTrace();
			return false;
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
			String sql	=	"SELECT PK_SEQ AS DVTTID, TEN AS DVTTTEN FROM ERP_DONVITHUCHIEN " ;
							//"WHERE CONGTY_FK = '" + this.ctyId + "' " ; 
			//and pk_seq in "+this.util.quyen_donvithuchien(this.userId);
			System.out.println(sql);
			this.dvttlist = this.db.get(sql);
	}

	public void createTkList(){		
		/*String sql = 	"SELECT TK.PK_SEQ AS TKID, TK.SOHIEUTAIKHOAN AS MA, TK.TENTAIKHOAN AS TEN " + 
						"FROM ERP_TAIKHOANKT TK " +
						"INNER JOIN ERP_LOAITAIKHOAN LTK ON TK.LOAITAIKHOAN_FK = LTK.PK_SEQ " +
						"WHERE (SOHIEUTAIKHOAN LIKE '6%' OR SOHIEUTAIKHOAN LIKE '8%' OR SOHIEUTAIKHOAN LIKE '3%')  AND TK.TRANGTHAI = '1' AND TK.CONGTY_FK = " + this.ctyId + " " +
						" ORDER BY MA";*/
		
		String sql = 	"SELECT DISTINCT TK.SOHIEUTAIKHOAN AS MA, TK.TENTAIKHOAN AS TEN \n" + 
						"FROM ERP_TAIKHOANKT TK \n" +
						"INNER JOIN ERP_LOAITAIKHOAN LTK ON TK.LOAITAIKHOAN_FK = LTK.PK_SEQ \n" +
						"WHERE TK.TRANGTHAI = '1' " +
						"ORDER BY MA \n";
		System.out.println(sql);
		this.tklist =  this.db.get(sql);
		System.out.println(sql);
	}

	public void createTtcpList(){	
		String sql = "SELECT PK_SEQ AS TTCPID, MA, DIENGIAI FROM ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI='1' " ;
		this.ttcplist =  this.db.get(sql);
		System.out.println(sql);
	}

	public void createNhomList(){		
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
		createDvttList();
		createTkList();
		createTtcpList();
	}

	public void DBClose(){
		try{
			if(this.tklist != null) this.tklist.close();
			if(this.ttcplist != null) this.ttcplist.close();
			if(this.ctylist != null) this.ctylist.close();
			if(this.dvttlist != null) this.dvttlist.close();
			if(this.nhomlist != null) nhomlist.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		this.db.shutDown();
	}
	
	
	
	public static void main(String[] args) 
	{
		try
		{ 
			geso.traphaco.erp.db.sql.dbutils db = new geso.traphaco.erp.db.sql.dbutils();
			UtilitySyn.SynData(db, "ERP_NHOMCHIPHI", "ERP_NHOMCHIPHI", "PK_SEQ", "101967", "0", false);
			
		}
		catch(Exception err)
		{
			err.printStackTrace();
		}
	}

	
	
}