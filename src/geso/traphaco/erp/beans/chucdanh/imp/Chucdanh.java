package geso.traphaco.erp.beans.chucdanh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.chucdanh.IChucdanh;

public class Chucdanh implements IChucdanh {

	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String chucdanh;
	String trangthai;
	String isKTT;
	String isKTV;
	String isTP;
	
	String ctyId;
	ResultSet ctylist;
	
	String nvId;
	ResultSet nvlist;

	String nvquanly;
	ResultSet RsNvQuanly;
	
	String dvthId;
	ResultSet RsDvth;
	
	String msg;
	dbutils db;
	
	String userId;
	String loai;
	ResultSet rsCty;
	String duyetdtvt;
	
	public Chucdanh()
	{
		this.id = "";
		this.chucdanh = "";	
		this.trangthai = "2";	
		this.ctyId = "";
		this.nvId = "";
		this.msg = "";
		this.nvquanly="";
		this.dvthId = "";
		this.userId = "";
		this.db = new dbutils();
		this.loai = "";
		this.isKTT = "";
		this.isTP = "";
		this.isKTV = "";
		this.duyetdtvt = "";
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getChucdanh()
	{
		return this.chucdanh;
	}

	public void setChucdanh(String chucdanh)
	{
		this.chucdanh = chucdanh;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getNvId()
	{
		return this.nvId;
	}

	public void setNvId(String nvId)
	{
		this.nvId = nvId;
	}

	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void setCtyList(ResultSet ctylist)
	{
		this.ctylist = ctylist;
	}

	public ResultSet getCtyList()
	{ 		
		return this.ctylist;
		
	}

	public void setNvList(ResultSet nvlist)
	{
		this.nvlist = nvlist;
	}

	public ResultSet getNvList()
	{ 		
		return this.nvlist;
		
	}
	
	public void init(){
		try{
		if(this.id.length() > 0){
			String query = 	"SELECT	CONGTY.PK_SEQ AS CTYID, CONGTY.TEN AS CTYTEN, CHUCDANH.LOAI, " +
							"CHUCDANH.PK_SEQ AS CDID, CHUCDANH.DIENGIAI AS CHUCDANH, " +
							"NHANVIEN.PK_SEQ AS NVID, NHANVIEN.TEN, CHUCDANH.TRANGTHAI, CHUCDANH.DVTH_FK, isnull(isKTT,0) isKTT, duyetdtvt, isnull(isTP, 0) isTP, isnull(isKTV, 0) isKTV " +
							"FROM ERP_CHUCDANH CHUCDANH " +
							"INNER JOIN NHANVIEN NHANVIEN ON CHUCDANH.NHANVIEN_FK = NHANVIEN.PK_SEQ " +
							"INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = CHUCDANH.CONGTY_FK " +
							"WHERE CHUCDANH.PK_SEQ = '" + this.id + "'";
				System.out.println("[init] "+query); 
				ResultSet rs = this.db.get(query);
		
				if(rs.next()){
					this.chucdanh = rs.getString("CHUCDANH");
					this.ctyId = rs.getString("CTYID");
					this.nvId = rs.getString("NVID");
					this.trangthai = rs.getString("TRANGTHAI");
					this.dvthId = rs.getString("DVTH_FK")== null ?"":rs.getString("DVTH_FK");
					this.loai = rs.getString("LOAI")== null ?"":rs.getString("LOAI");
					this.isKTT = rs.getString("isKTT");
					this.isTP = rs.getString("isTP");
					this.isKTV = rs.getString("isKTV");
					this.duyetdtvt = rs.getString("duyetdtvt")== null ?"":rs.getString("duyetdtvt");
				}
				
				query="SELECT NHANVIEN_FK FROM ERP_CHUCDANH_NHANVIEN WHERE CHUCDANH_FK="+this.id;
				
				rs=db.get(query);
				this.nvquanly="0";
				while (rs.next()){
					this.nvquanly=this.nvquanly+","+rs.getString("NHANVIEN_FK");
				}
				rs.close();
		}
		
		}catch(Exception er){ 
			er.printStackTrace();
		}
	}
	
	public boolean saveNewChucdanh(){
		try{
			this.db.getConnection().setAutoCommit(false);
		
			if (!CheckUnique(""))
			{
				return false;
			}

			if(this.dvthId.trim().length() <= 0) this.dvthId = "NULL";
			if(this.duyetdtvt.trim().length() <= 0) this.duyetdtvt = "NULL";
			
			String query = 	"INSERT INTO ERP_CHUCDANH ( DIENGIAI, NHANVIEN_FK, CONGTY_FK, TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, DVTH_FK, LOAI, isKTT, duyetdtvt, isTP, isKTV ) " +
							"VALUES(N'" + this.chucdanh + "', '" + this.nvId + "','" + this.ctyId + "','"+ this.trangthai +"', '" + getDateTime() + "','" + getDateTime() + "','" + this.userId + "','" + this.userId + "', "+ this.dvthId +", '"+ this.loai +"', "+this.isKTT+", "+this.duyetdtvt+", "+this.isTP+", "+this.isKTV+" )";
			if(!this.db.update(query))
			{
				db.update("rollback");
				return false;
			}
			
			String hctbhCurrent = "";
			query = "select IDENT_CURRENT('ERP_CHUCDANH') as hctbhId";
			
			ResultSet rs = db.get(query);						
			if(rs.next())
			{
				hctbhCurrent = rs.getString("hctbhId");
				rs.close();
			}
			this.id=hctbhCurrent;
			
			if(this.nvquanly.trim().length() > 0)
			{
				query=  " INSERT INTO ERP_CHUCDANH_NHANVIEN ( CHUCDANH_FK, NHANVIEN_FK, CONGTY_FK ) "+ 
						" SELECT "+this.id+",PK_SEQ , "+this.ctyId+" FROM NHANVIEN WHERE PK_SEQ IN ("+this.nvquanly+")";
		
				if(!db.update(query)){
					db.update("rollback");
					this.msg = "Không thể thực hiện dòng lệnh : "+query;
					return false; 
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception err){ 
			
			db.update("rollback");
			err.printStackTrace();
			return false;
		}
		
		return true;
		
	}
	
	public boolean UpdateChucdanh(){
		try{
			this.db.getConnection().setAutoCommit(false);
			
			if(this.dvthId.trim().length() <= 0) this.dvthId = "NULL";
			if(this.duyetdtvt.trim().length() <= 0) this.duyetdtvt = "NULL";
			
			String query = 	" UPDATE ERP_CHUCDANH SET DIENGIAI = N'" + this.chucdanh + "', NHANVIEN_FK = '" + this.nvId + "', " +
							" CONGTY_FK = '" + this.ctyId + "', TRANGTHAI = '" + this.trangthai + "', NGAYSUA = '" + getDateTime() + "', NGUOISUA = '" + this.userId + "' , " +
							" DVTH_FK = " + this.dvthId + ", LOAI = '" + this.loai + "', isKTT = " +this.isKTT + ", duyetdtvt = "+this.duyetdtvt+", isTP = "+this.isTP +", isKTV = "+this.isKTV +
							" WHERE PK_SEQ = '" + this.id + "'";
			System.out.println("[update] "+query);
			if(!db.update(query)){
				db.update("rollback");
				this.msg = "Khong the cap nhat table 'CHUCDANH'";
				return false; 
			}
			query=" delete ERP_CHUCDANH_NHANVIEN where CHUCDANH_FK= "+this.id;
			if(!db.update(query)){
				db.update("rollback");
				this.msg = "Không thể thực hiện dòng lệnh : "+query;
				return false; 
			}
			
			if(this.nvquanly.trim().length() > 0)
			{
				query=  " INSERT INTO ERP_CHUCDANH_NHANVIEN (CHUCDANH_FK,NHANVIEN_FK, CONGTY_FK) "+ 
						" SELECT "+this.id+",PK_SEQ, "+this.ctyId+" FROM NHANVIEN WHERE PK_SEQ IN ("+this.nvquanly+")";
				
				if(!db.update(query)){
					db.update("rollback");
					this.msg = "Không thể thực hiện dòng lệnh : "+query;
					return false; 
				}
			}
		
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(java.sql.SQLException e){}
		return true;
	}

	
	public boolean AllowtoChangeCty(){
		ResultSet rs = db.get("SELECT COUNT(CHUCDANH_FK) AS NUM FROM ERP_DUYETMUAHANG WHERE CHUCDANH_FK = '" + this.id + "'");
		try{
			rs.next();
			if (rs.getString("NUM").equals("0"))
				return true;
			else 
				return false;
		}catch(java.sql.SQLException e){
			return false;
		}		
	
	}
 
	public void creaters()
	{		
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY WHERE pk_seq = '" + this.ctyId + "' ");
		this.nvlist =  this.db.get("SELECT PK_SEQ AS NVID, TEN FROM NHANVIEN" );
		this.RsDvth = this.db.get("SELECT PK_SEQ , MA + ' - '+ TEN AS TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' ");
		this.rsCty = this.db.get("SELECT PK_SEQ , MA + ' - '+ TEN AS TEN FROM ERP_CONGTY WHERE TRANGTHAI = '1' ");
		
		String query = "";
		
		
		if( this.nvId !=null && this.nvId.length() >0)
		{
			/*query=  " SELECT DISTINCT C.PK_SEQ , C.DANGNHAP, C.TEN \n"+
					" FROM PHAMVIHOATDONG A INNER JOIN NHAPHANPHOI B ON A.Npp_fk = B.PK_SEQ \n"+
					" INNER JOIN NHANVIEN C ON A.Nhanvien_fk = C.PK_SEQ \n"+
					" WHERE C.TRANGTHAI = 1 and c.pk_seq <> " + this.nvId + " ";*/
			
			query=  " SELECT DISTINCT C.PK_SEQ , C.DANGNHAP, C.TEN \n"+
					" FROM NHANVIEN C  \n"+
					" WHERE C.TRANGTHAI = 1 and c.pk_seq <> " + this.nvId + " ";
			
			if( this.dvthId.trim().length() > 0 )
				query += " and c.phongban_fk = '" + this.dvthId + "' ";
			
			System.out.println(":: NV THUOC PHONG BAN: " + query);
			this.RsNvQuanly=db.get(query);
		}
		
	}

	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public boolean CheckUnique(String command)
	{
		String query = "Select count(*) as count From Erp_Chucdanh Where Diengiai = N'" + this.chucdanh + "' and congty_fk = '" + this.ctyId + "' ";
		System.out.println(query);
		
		if (command.equals(""))
			command = query;
		else
			query += command;
		int count = 0;
		 
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0){
					
					this.msg = "Chức danh đã tồn tại. Vui lòng tạo chức danh mới";
					return false;
				}
			}
			catch (Exception e)
			{
				this.msg=e.getMessage();
				return false;
			}
		return true;
	}
	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.nvlist != null) this.nvlist.close();
			
			if(this.RsNvQuanly!=null){
				this.RsNvQuanly.close();
			}
			if(this.RsDvth!=null){
				this.RsDvth.close();
			}
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}

 
	public void setNvQuanly(String nvquanly) 
	{
		this.nvquanly=nvquanly;
	}

 
	public String getNvquanly() 
	{
		return this.nvquanly;
	}

 
	public void setRsNvquanly(ResultSet Nvquanly)
	{
		this.RsNvQuanly=Nvquanly;
	}

 
	public ResultSet getRsNvquanly() 
	{
		return this.RsNvQuanly;
	}
	
	public void setDvthId(String dvthId) 
	{
		this.dvthId = dvthId;
	}

	public String getDvthId() 
	{
		return this.dvthId ;
	}

	public void setRsDvth(ResultSet rsDvth) 
	{
		this.RsDvth = rsDvth ;
	}

	public ResultSet getRsDvth() 
	{
		return this.RsDvth;
	}

	
	public void setRsCty(ResultSet rsCty) {
		this.rsCty = rsCty;
	}

	
	public ResultSet getRsCty() {
		return this.rsCty;
	}

	
	public void setLoai(String loai) {
		this.loai = loai;
	}

	
	public String getLoai() {
		return this.loai;
	}

	
	public String getisKTT() {
	
		return this.isKTT;
	}

	
	public void setisKTT(String isKTT) {
	
		this.isKTT = isKTT;
	}

	
	public String getDuyetDtvt() {
		return this.duyetdtvt;
	}

	
	public void setDuyetDtvt(String duyetdtvt) {
		this.duyetdtvt = duyetdtvt;
	}

	
	public String getisTP() {
		
		return this.isTP;
	}

	
	public void setisTP(String isTP) {
		
		this.isTP = isTP;
	}

	
	public String getisKTV() {
		
		return this.isKTV;
	}

	
	public void setisKTV(String isKTV) {
		
		this.isKTV = isKTV;
	}
		
}


