package geso.traphaco.erp.beans.erp_trungtamchiphi.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamdoanhthu;

public class Erp_trungtamdoanhthu implements IErp_trungtamdoanhthu
{

	private String Id;
	private String userId;
	private ResultSet TTDTlist;
	
	private String ctyId;
	private String ma;
	private String diengiai;
	private String trangthai;
	private String congansach;
	private String nhanphanbo;
	private String chophanbo;
	private String[] pbIds;
	private String[] pt;
	
	private String msg;
	private dbutils db;
	private Utility util;
	
	public Erp_trungtamdoanhthu()
	{
		this.Id = "";
		this.ctyId = "";
		this.ma = "";
		this.diengiai = "";
		this.trangthai = "";		
		this.congansach = "";
		this.nhanphanbo = "";
		this.chophanbo = "";
		
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


	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}

	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getMa(){
		return this.ma;
	}
	
	public void setMa(String ma){
		this.ma = ma;
	}

	public String getDiengiai(){
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai){
		this.diengiai = diengiai;
	}
	
	public String getTrangthai(){
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai){
		this.trangthai = trangthai;
	}
	

	public String getCongansach(){
		return this.congansach;
	}
	
	public void setCongansach(String congansach){
		this.congansach = congansach;
	}

	public String getNhanphanbo(){
		return this.nhanphanbo;
	}
	
	public void setNhanphanbo(String nhanphanbo){
		this.nhanphanbo = nhanphanbo;
	}


	public String getPhanbo(){
		return this.chophanbo;
	}
	
	public void setPhanbo(String phanbo){
		this.chophanbo = phanbo;
	}
	
	public ResultSet getTTDTList(){
		return this.TTDTlist;
	}

	public String[] getPbIds(){
		return this.pbIds;
	}
	
	public void setPbIds(String[] pbIds){
		this.pbIds = pbIds;
	}

	public String[] getPt(){
		return this.pt;
	}
	
	public void setPt(String[] pt){
		this.pt = pt;
	}

	public String getPbIdsList(){
		String tmp = "";
		if(this.pbIds != null){
			for(int i = 0; i < this.pbIds.length; i++){
				tmp = tmp +  this.pbIds[i] + ";" ;
			}
		}
		return tmp;
	}
	

	public void init(){
		String query = 	" SELECT 	TTDT.PK_SEQ AS TTDTID, TTDT.MA, TTDT.DIENGIAI, " + 
						" 			TTDT.TRANGTHAI,  ISNULL(NHANPHANBO, 0) AS NHANPHANBO, ISNULL(CHOPHANBO, 0) AS CHOPHANBO " +
						" FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						" INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = TTDT.CONGTY_FK " +
						" WHERE  TTDT.PK_SEQ = " + this.Id; // + " AND CONGTY.PK_SEQ = " + this.ctyId; 		
		System.out.println("init: " + query);
		
		ResultSet rs = this.db.get(query);
		try{
			while(rs.next()){
				this.ma = rs.getString("MA");
				this.diengiai = rs.getString("DIENGIAI");
				this.trangthai = rs.getString("TRANGTHAI");
				this.nhanphanbo = rs.getString("NHANPHANBO");
				this.chophanbo = rs.getString("CHOPHANBO");				
			}
			rs.close();
		}catch(java.sql.SQLException e){}
		
	}
	
	public boolean Update(){
		String query = "";

		query = "UPDATE ERP_TRUNGTAMDOANHTHU SET MA = N'" + this.ma + "', DIENGIAI = N'" + this.diengiai + "', " +
				"TRANGTHAI = '" + this.trangthai + "', CHOPHANBO = '" + this.chophanbo + "', " +
				"NGAYSUA = '" + this.getDateTime() + "', " +
				"NGUOISUA = '" + this.userId + "' " +
			    "WHERE PK_SEQ = '" + this.Id + "'";

		if(this.db.update(query)){
			query = "DELETE FROM ERP_TRUNGTAMDOANHTHU_PHANBO WHERE TTDTCHO_FK = '" + this.Id + "'";
			this.db.update(query);

			if(this.chophanbo.equals("1")){
				
				if(this.pbIds != null){
				
					for(int i = 0; i < this.pbIds.length; i++){
						String TTDTId = this.pbIds[i].split("-")[0];
						String pt = this.pbIds[i].split("-")[1];
						
						if(!pt.equals("0")){
							query = "INSERT INTO ERP_TRUNGTAMDOANHTHU_PHANBO(TTDTCHO_FK, TTDTNHAN_FK, PHANTRAM) " +
									"VALUES ('" + this.Id + "', '" + TTDTId + "', '" + pt + "')";
						
							this.db.update(query);
						
							query = "UPDATE ERP_TRUNGTAMDOANHTHU SET NHANPHANBO = '1' WHERE PK_SEQ = '" + TTDTId + "' ";
							this.db.update(query);
						}
					}
				}else{
					this.msg = "Vui lòng chọn Trung Tâm Chi Phí và nhập phần trăm để phân bổ" ;
					return false;
				}
				
			}

			// Cap nhat lai NHANPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMDOANHTHU SET NHANPHANBO = 0 WHERE PK_SEQ IN ( " +
					"SELECT PK_SEQ " +
					"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
					"WHERE NHANPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTDTNHAN_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) )" ;
			this.db.update(query);
			
			// Cap nhat lai NHANPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMDOANHTHU SET NHANPHANBO = 1 WHERE PK_SEQ IN  (SELECT TTDTNHAN_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO WHERE PHANTRAM > 0) " ;
			this.db.update(query);

			// Cap nhat lai CHOPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMDOANHTHU SET CHOPHANBO = 0 WHERE PK_SEQ IN ( " +
					"SELECT PK_SEQ " +
					"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
					"WHERE CHOPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTDTCHO_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO) )" ;
			this.db.update(query);
			
			// Cap nhat lai CHOPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMDOANHTHU SET CHOPHANBO = 1 WHERE PK_SEQ IN  (SELECT TTDTCHO_FK FROM ERP_TRUNGTAMDOANHTHU_PHANBO WHERE PHANTRAM > 0) " ;
			this.db.update(query);
			
		}
		
		
		return true;
	}
	
	public boolean New(){

		String query = "";
		
		query = "INSERT INTO ERP_TRUNGTAMDOANHTHU (MA, DIENGIAI, CONGTY_FK, NHANPHANBO, CHOPHANBO, " +
				"TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA )"  +
				"VALUES( N'" + this.ma + "', N'" + this.diengiai + "', '" + this.ctyId + "', '" + this.nhanphanbo + "', '" + this.chophanbo + "', " +
				 "'" + this.trangthai + "', '" + this.getDateTime() + "','"+ this.getDateTime() + "','" + this.userId + "','" + this.userId + "' )";		
			
		
		System.out.println(query);
		if(this.db.update(query)){
			query = "SELECT SCOPE_IDENTITY() AS ID";
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				this.Id = rs.getString("ID");

				if(this.chophanbo.equals("1")){
					
					if(this.pbIds != null){
					
						for(int i = 0; i < this.pbIds.length; i++){
							String TTDTId = this.pbIds[i].split("-")[0];
							String pt = this.pbIds[i].split("-")[1];
							
							if(!pt.equals("0")){
								query = "INSERT INTO ERP_TRUNGTAMDOANHTHU_PHANBO(TTDTCHO_FK, TTDTNHAN_FK, PHANTRAM) " +
										"VALUES ('" + this.Id + "', '" + TTDTId + "', '" + pt + "')";
							
								this.db.update(query);
							
								query = "UPDATE ERP_TRUNGTAMDOANHTHU SET NHANPHANBO = '1' WHERE PK_SEQ = '" + TTDTId + "' ";
								this.db.update(query);
							}
						}
					}else{
						this.msg = "Vui lòng chọn Trung Tâm Chi Phí và nhập phần trăm để phân bổ" ;
						return false;
					}
					
				}
				
				
			}catch(java.sql.SQLException e){}
		}
		
		return true;
	}

	public void setMsg(String msg) {
		this.msg =msg;
		
	}

	
	public String getMsg() {
		return this.msg;
	}

	
	public ResultSet getNhanphanbo(String TTDTId){
		String query = "";
		if(TTDTId.length() > 0){
			 query = 	"SELECT	TTDT.PK_SEQ AS TTDTID_NPB, PB.TTDTCHO_FK AS TTDTID_CPB, " + 
						"TTDT_CPB.MA AS MA_CPB, TTDT_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTNHAN_FK = TTDT.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_CPB ON TTDT_CPB.PK_SEQ = PB.TTDTCHO_FK " +
						"WHERE TTDT.PK_SEQ = " + TTDTId + " ORDER BY TTDT_CPB.MA ";
			 return this.db.get(query);
		}
		
		
		System.out.println(query);
		return null;
	}
	

	public ResultSet getChophanbo(String TTDTId){
		String query = "";
		if(TTDTId.length() > 0){
		
			query = 	"SELECT	TTDT.PK_SEQ AS TTDTID_CPB, PB.TTDTNHAN_FK AS TTDTID_NPB, " +
						"TTDT_NPB.MA AS MA_NPB, TTDT_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTCHO_FK = TTDT.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_NPB ON TTDT_NPB.PK_SEQ = PB.TTDTNHAN_FK " +		
						"WHERE TTDT.PK_SEQ = " + TTDTId + " ORDER BY TTDT_NPB.MA ";
			return this.db.get(query);

		}
		
		System.out.println(query);
		
		return null;
	}
	
	public ResultSet getChophanbothem(String TTDTId){
		String query;
		if(TTDTId.length() > 0){
		
			query = 	"SELECT PK_SEQ AS TTDTID, MA, DIENGIAI FROM " +
						"ERP_TRUNGTAMDOANHTHU WHERE PK_SEQ NOT IN (" +
						"	SELECT	PB.TTDTNHAN_FK  " +						
						"	FROM ERP_TRUNGTAMDOANHTHU TTDT " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU_PHANBO PB ON PB.TTDTCHO_FK = TTDT.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMDOANHTHU TTDT_NPB ON TTDT_NPB.PK_SEQ = PB.TTDTNHAN_FK " +
						"	WHERE TTDT.PK_SEQ = " + TTDTId + " ) AND PK_SEQ <> '"  + TTDTId + "' ORDER BY MA ";
			return this.db.get(query);
		}else{
			query = 	"SELECT PK_SEQ AS TTDTID, MA, DIENGIAI FROM " +
						"ERP_TRUNGTAMDOANHTHU ORDER BY MA ";
			
			return this.db.get(query);
		}
		
		
	}
	
	public void initNew(){
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBClose(){
		try{
			if(this.TTDTlist != null) this.TTDTlist.close();
			
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
	
}
