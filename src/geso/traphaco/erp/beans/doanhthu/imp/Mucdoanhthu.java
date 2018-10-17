package geso.traphaco.erp.beans.doanhthu.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.doanhthu.IMucdoanhthu;

public class Mucdoanhthu implements IMucdoanhthu {

	private String Id;
	private String userId;
	private ResultSet tklist;
	private ResultSet ttdtlist;
	private ResultSet dvttlist;
	private ResultSet nhomlist;
	
	private String ctyId;
	private String dvttId;
	private String parentId;
	private String tkId;
	private String ttdtId;
	private String ten;
	private String diengiai;
	private String[] loai;
	private String trangthai;		 
	
	private String congty;
	private String donvi;	
	private String msg;
	private boolean search = false;
	private dbutils db;
	private Utility util;
	public Mucdoanhthu()
	{
		this.Id = "";
		this.ten = "";
		this.diengiai = "";
		this.trangthai = "";
		this.congty = "";
		this.donvi = "";
		this.tkId = "";
		this.ttdtId = "";
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

	public ResultSet getTtdtList(){
		return this.ttdtlist;
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
	
	public String getTtdtId(){
		return this.ttdtId;
	}

	public void setTtdtId(String ttdtId){
		this.ttdtId = ttdtId;
	}

	public void init(){
		String query = 	"SELECT	DOANHTHU.PK_SEQ AS NCPID, DOANHTHU.TEN AS NCPTEN, DOANHTHU.DIENGIAI, " + 
						"DOANHTHU.TRANGTHAI, " +
						"ISNULL(DVTH.PK_SEQ, 0) AS DVTHID, ISNULL(DVTH.TEN, '')  AS DVTHTEN, ISNULL(TAIKHOAN_FK, '0') AS TKID, " +
						"ISNULL(TTDOANHTHU_FK, '0') AS TTDTID " +
						"FROM ERP_DOANHTHU DOANHTHU " +
						"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = DOANHTHU.CONGTY_FK " +
						"LEFT JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DOANHTHU.DONVITHUCHIEN_FK " +		
						"WHERE DOANHTHU.PK_SEQ = '" + this.Id + "' " ;
		
		System.out.println("1.Khoi tao doanh thu: " + query);
		
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
					this.ttdtId = rs.getString("TTDTID");
				}
			rs.close();
			
		}catch(java.sql.SQLException e){}
		
		createTkList();
		createTtdtList();
		this.createDvttList();
	}
	
	public boolean Update(){
		String query = "";
		String tmp = "";
		String checktrungten = "";
		
		checktrungten = CheckTenKMDG();
		
		if(checktrungten.trim().length()>0)
		{
			this.msg = checktrungten;
			return false;
		}
		
		query = "UPDATE ERP_DOANHTHU SET TEN = N'" + this.ten + "', DIENGIAI = N'" + this.diengiai + "',  " +
				"TRANGTHAI = '" + this.trangthai + "', DONVITHUCHIEN_FK = " + this.dvttId + ", NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', " +
				"TAIKHOAN_FK = " + tkId + ", " + "TTDOANHTHU_FK = " + this.ttdtId + " " +
				"WHERE PK_SEQ = '" + this.Id + "'";
			
		System.out.println("1.Câu lệnh cập nhật: " + query);	
		this.db.update(query);
		
		return true;
	}
	
	
	
	public boolean New(){

		String query1 = "";
		String query2 = "";
		String checktrungten = "";
		try{
			
			checktrungten = CheckTenKMDG();
			
			if(checktrungten.trim().length()>0)
			{
				this.msg = checktrungten;
				return false;
			}
			
			query1 = " INSERT INTO ERP_DOANHTHU (TEN, DIENGIAI, LOAI, CONGTY_FK, " +
					 " TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA " ; 
	
			query2 = "VALUES(N'" + this.ten + "', N'" + this.diengiai + "', '1', '" + this.ctyId + "', " +
					"'" + this.trangthai + "', '" + this.getDateTime() + "','"+ this.getDateTime() + "','" + this.userId + "','" + this.userId + "' ";

			if(this.dvttId.length() > 0){
				query1 = query1 + ", DONVITHUCHIEN_FK ";
				query2 = query2 + ", '" + this.dvttId + "' ";
			}
		
			if(this.tkId.length() > 0){
				query1 = query1 + " , TAIKHOAN_FK " ; 
				query2 = query2 + ", '" + this.tkId + "' " ; 
			}

			if(this.ttdtId.length() > 0){
				query1 = query1 + " , TTDOANHTHU_FK " ; 
				query2 = query2 + ", '" + this.ttdtId + "' " ; 		
			}
			
			query1 = query1 + ") " + query2 + " )";
			
			System.out.println(query1);
			this.db.update(query1);

			ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
			rs.next();
		
			this.Id = rs.getString("ID");
	}
	catch(Exception e)
	{
		this.msg = "Không thể tạo mới khoản mục doanh thu: " + e.getMessage();
		System.out.println(this.msg);
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

	public String CheckTenKMDG() {
		
		String checktrung = "";
		
		try
		{			
			String query = " SELECT count(*) dem FROM ERP_DOANHTHU WHERE TEN = N'"+this.ten+"' AND TRANGTHAI = 1 ";
			
			if(this.Id.trim().length() > 0)
				query += " AND PK_SEQ != "+this.Id;
			
			ResultSet Rscheck = db.get(query);
			int count = 0;
			if(Rscheck!=null)
			{
				while(Rscheck.next())
				{
					count = Rscheck.getInt("dem");
				}
				
			}
			Rscheck.close();
			
			if(count>0)
				checktrung = "ĐÃ CÓ TÊN KHOẢN MỤC DOANH THU NÀY TRONG HỆ THỐNG. VUI LÒNG XEM LẠI !";
		}
		catch(Exception ex)
		{
			ex.printStackTrace();
		}
		
		return checktrung;
	}
	
	
	public void createDvttList(){
		if(this.ctyId.length() > 0){
			String sql="SELECT PK_SEQ AS DVTTID, TEN AS DVTTTEN FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = '" + this.ctyId + "' "; // and pk_seq in "+this.util.quyen_donvithuchien(this.userId);
			this.dvttlist = this.db.get(sql);
		}
	}

	public void createTkList(){		
		
		this.tklist =  this.db.get( "SELECT TK.PK_SEQ AS TKID, TK.SOHIEUTAIKHOAN AS MA, TK.TENTAIKHOAN AS TEN " + 
									"FROM ERP_TAIKHOANKT TK " +
									"INNER JOIN ERP_LOAITAIKHOAN LTK ON TK.LOAITAIKHOAN_FK = LTK.PK_SEQ " +
									"WHERE TK.TRANGTHAI = '1' AND TK.CONGTY_FK = " + this.ctyId + " " +
									" ORDER BY MA" );
	}

	public void createTtdtList(){		
		this.ttdtlist =  this.db.get("SELECT PK_SEQ AS TTDTID, MA, DIENGIAI FROM ERP_TRUNGTAMDOANHTHU WHERE TRANGTHAI='1' " );
	}

	private void createNhomList(){		
		String query = ""; 
		
		if(this.ctyId.length()>0 & this.dvttId.length() > 0){
			query = "SELECT PK_SEQ AS NHOMID, TEN + ' - ' + DIENGIAI AS TENNHOM FROM ERP_DOANHTHU " +
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
		createTtdtList();
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
			if(this.ttdtlist != null) this.ttdtlist.close();
			if(this.dvttlist != null) this.dvttlist.close();
			if(this.nhomlist != null) nhomlist.close();
			
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
	
}