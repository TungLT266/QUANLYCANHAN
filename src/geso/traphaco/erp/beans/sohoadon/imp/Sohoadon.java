package geso.traphaco.erp.beans.sohoadon.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.sohoadon.ISohoadon;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Sohoadon implements ISohoadon {

	private String Id;
	private String userId;
	private ResultSet kholist;
	
	private String ctyId;
	private String khoId;
	private String tuso;
	private String denso;
	private String kyhieu;
	private String loai;
	private String trangthai;		 
	private String msg;	
	private dbutils db;
	private Utility util;
	public Sohoadon()
	{
		this.Id = "";
		this.tuso = "";
		this.denso = "";
		this.trangthai = "";
		this.loai = "";
		this.ctyId = "";
		this.khoId = "";
		this.msg = "";
		this.kyhieu = "";
		this.db = new dbutils();
		this.util=new Utility();
	}


	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}


	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getKhoId(){
		return this.khoId;
	}
	
	public void setKhoId(String khoId){
		this.khoId = khoId;
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getTuso(){
		return this.tuso;
	}
	
	public void setTuso(String tuso){
		this.tuso = tuso;
	}

	public String getDenso(){
		return this.denso;
	}
	
	public void setDenso(String denso){
		this.denso = denso;
	}
	
	public String getLoai(){
		return this.loai;
	}
	

	public void setLoai(String loai){
		this.loai = loai;
	}

	public void setKyhieu(String kyhieu){
		this.kyhieu = kyhieu;
	}
	
	public String getKyhieu(){
		return this.kyhieu;
	}

	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	

	public ResultSet getKhoList(){
		return this.kholist;
	}
	
	public void init(){
		String query = 	"SELECT SHD.PK_SEQ AS SOHOADONID, SHD.TUSO, SHD.DENSO, SHD.KYHIEUHD, SHD.TRANGTHAI, \n " +
						"CASE WHEN SHD.LOAI = 1 THEN N'Hóa đơn' ELSE N'Xuất kho' END AS LOAI,  \n " +
						"KHO.PK_SEQ AS KHOID, \n " +
						"NV.TEN AS NGUOITAO, SHD.NGAYTAO \n " +
						"FROM ERP_SOHOADON SHD \n " +
						"INNER JOIN ERP_KHOVUNG KHO ON KHO.PK_SEQ = SHD.KHOVUNG_FK \n " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = SHD.NGUOITAO \n " +
						"WHERE SHD.CONGTY_FK = " + this.ctyId + " AND SHD.PK_SEQ = '" + this.Id + "' \n " ;
								
		System.out.println("1.Khoi tao so hoa don: " + query);
		
		ResultSet rs = this.db.get(query);
		try{
			if(rs!=null)
				if(rs.next()){
					this.Id = rs.getString("SOHOADONID");
					this.tuso = rs.getString("TUSO");
					this.denso = rs.getString("DENSO");
					this.kyhieu = rs.getString("KYHIEUHD");
					this.trangthai = rs.getString("TRANGTHAI");
					this.khoId = rs.getString("KHOID");
					this.loai = rs.getString("LOAI");
					
				}
			rs.close();
		
		}catch(java.sql.SQLException e){}

		this.createKhoList();
	}
	
	public boolean Update(){
		String query = 	"UPDATE ERP_SOHOADON SET TUSO = " + this.tuso + ", DENSO = " + this.denso + ", KYHIEUHD = N'" + this.kyhieu + "', KHOVUNG_FK = " + this.khoId + ",  " +
						"LOAI = " + this.loai + ", NGAYTAO = '" + this.getDateTime() + "', NGUOITAO = " + this.userId + ", TRANGTHAI = " + this.trangthai + "  " +
						"WHERE PK_SEQ = " + this.Id + "";
		System.out.println("1.Câu lệnh cập nhật: " + query);	
		if(!this.db.update(query)){
			
			this.msg = "Lưu số hóa đơn không thành công";
			db.update("rollback");
			return false;
		}
			
		return true;
	}
	
	public boolean New(){

		String query = 	"INSERT INTO ERP_SOHOADON(TUSO, DENSO, KYHIEUHD, KHOVUNG_FK, LOAI, CONGTY_FK, NGAYTAO, NGUOITAO, TRANGTHAI) \n " +
						"VALUES(" + this.tuso + ", " + this.denso + ", N'" + this.kyhieu + "', "+ this.khoId + ", " + this.loai + ", " + this.ctyId + ", " +
						"'" + this.getDateTime() + "', " + this.userId + ", " + this.trangthai + ") ";
		
		System.out.println("1.Câu lệnh tạo mới: " + query);	

		if(!this.db.update(query)){
			
			this.msg = "Lưu số hóa đơn không thành công";
			db.update("rollback");
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

	private void createKhoList(){		
		if(this.ctyId.length() > 0){

			String query = 	
							"SELECT PK_SEQ AS KHOID, MA + ' - ' + TEN AS TEN \n " +
							"FROM ERP_KHOVUNG WHERE CONGTY_FK = " + this.ctyId + "  \n " ;
				
			System.out.println(query);
			this.kholist = this.db.get(query );
			
		}
	}

	
	public void initNew(){
		createKhoList();
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBClose(){
		try{
			if(this.kholist != null) this.kholist.close();
			
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
	
}