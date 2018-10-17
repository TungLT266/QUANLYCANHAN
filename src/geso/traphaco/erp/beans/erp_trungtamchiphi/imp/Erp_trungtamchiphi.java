package geso.traphaco.erp.beans.erp_trungtamchiphi.imp;

import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.erp_trungtamchiphi.IErp_trungtamchiphi;
import geso.traphaco.erp.util.UtilitySyn;

public class Erp_trungtamchiphi implements IErp_trungtamchiphi
{

	private String Id;
	private String userId;
	private ResultSet ttcplist;
	
	private String ctyId;
	private String ma;
	private String diengiai;
	private String trangthai;
	private String congansach;
	private String nhanphanbo;
	private String chophanbo;
	private String[] ptds;
	private String[] pbIds;
	private String[] pt;
	
	private String msg;
	private dbutils db;
//	private Utility util;
	private String timkiem;
	private String chon;
	
	public Erp_trungtamchiphi()
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
//		this.util=new Utility();
		this.timkiem = "";
		this.chon = "";
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

	public String getChon(){
		return this.chon;
	}
	
	public void setChon(String chon){
		this.chon = chon;
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
	
	public ResultSet getTtcpList(){
		return this.ttcplist;
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

	public String[] getPtds(){
		return this.ptds;
	}
	
	public void setPtds(String[] ptds){
		this.ptds = ptds;
	}

	public void setTimkiem(String timkiem){
		this.timkiem = timkiem;
	}
	
	public String getTimkiem(){
		return this.timkiem;
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
		String query = 	" SELECT 	TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, " + 
						" 			TTCP.TRANGTHAI, ISNULL(CONGANSACH, 0) AS CONGANSACH, ISNULL(NHANPHANBO, 0) AS NHANPHANBO, ISNULL(CHOPHANBO, 0) AS CHOPHANBO " +
						" FROM ERP_TRUNGTAMCHIPHI TTCP " +
						" INNER JOIN ERP_CONGTY CONGTY ON CONGTY.PK_SEQ = TTCP.CONGTY_FK " +
						" WHERE TTCP.PK_SEQ = " + this.Id; // + " AND CONGTY.PK_SEQ = " + this.ctyId; 		
		System.out.println("init: " + query);
		
		ResultSet rs = this.db.get(query);
		try{
			while(rs.next()){
				this.ma = rs.getString("MA");
				this.diengiai = rs.getString("DIENGIAI");
				this.trangthai = rs.getString("TRANGTHAI");
				this.congansach = rs.getString("CONGANSACH");
				this.nhanphanbo = rs.getString("NHANPHANBO");
				this.chophanbo = rs.getString("CHOPHANBO");				
			}
			rs.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public boolean Update(){
		String query = "";

		query = "UPDATE ERP_TRUNGTAMCHIPHI SET MA = N'" + this.ma + "', DIENGIAI = N'" + this.diengiai + "', " +
				"TRANGTHAI = '" + this.trangthai + "', CONGANSACH = '" + this.congansach + "', CHOPHANBO = '" + this.chophanbo + "', " +
				"NGAYSUA = '" + Utility.getCurrentDate() + "', " +
				"NGUOISUA = '" + this.userId + "' " +
			    "WHERE PK_SEQ = '" + this.Id + "'";

		if(this.db.update(query)){
			query = "DELETE FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE TTCPCHO_FK = '" + this.Id + "'";
			this.db.update(query);

			if(this.chophanbo.equals("1")){
				
				if(this.pbIds != null){
				
					for(int i = 0; i < this.pbIds.length; i++){
						String ttcpId = this.pbIds[i].split("-")[0];
						String pt = this.pbIds[i].split("-")[1];
						
						if(!pt.equals("0")){
							query = "INSERT INTO ERP_TRUNGTAMCHIPHI_PHANBO(TTCPCHO_FK, TTCPNHAN_FK, PHANTRAM) " +
									"VALUES ('" + this.Id + "', '" + ttcpId + "', '" + pt + "')";
						
							this.db.update(query);
						
							query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = '1' WHERE PK_SEQ = '" + ttcpId + "' ";
							this.db.update(query);
						}
					}
				}else{
					this.msg = "Vui lòng chọn Trung Tâm Chi Phí và nhập phần trăm để phân bổ" ;
					return false;
				}
				
			}

			// Cap nhat lai NHANPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 0 WHERE PK_SEQ IN ( " +
					"SELECT PK_SEQ " +
					"FROM ERP_TRUNGTAMCHIPHI TTCP " +
					"WHERE NHANPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO) )" ;
			this.db.update(query);
			
			// Cap nhat lai NHANPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 1 WHERE PK_SEQ IN  (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0) " ;
			this.db.update(query);

			// Cap nhat lai CHOPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 0 WHERE PK_SEQ IN ( " +
					"SELECT PK_SEQ " +
					"FROM ERP_TRUNGTAMCHIPHI TTCP " +
					"WHERE CHOPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO) )" ;
			this.db.update(query);
			
			// Cap nhat lai CHOPHANBO cho dung
			query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 1 WHERE PK_SEQ IN  (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0) " ;
			this.db.update(query);
			
			//SYN QUA DMS
			String str = UtilitySyn.SynData(db, "ERP_TRUNGTAMCHIPHI", "ERP_TRUNGTAMCHIPHI", "PK_SEQ", this.Id, "1", false);
			if (str.trim().length() > 0)
			{
				this.msg = "E1.3 Không thể đồng bộ xuống DMS " + str;
				return false;
			}
		}
		
		//SYN QUA DMS
		UtilitySyn.SynData(db, "Erp_NhaCungCap", "Erp_NhaCungCap", "PK_SEQ", this.Id, "1", false);
		UtilitySyn.SynData(db, "ERP_TRUNGTAMCHIPHI_PHANBO", "ERP_TRUNGTAMCHIPHI_PHANBO", "TTCPCHO_FK", this.Id, "2", true);
		UtilitySyn.SynData(db, "ERP_TRUNGTAMCHIPHI_PHANBO", "ERP_TRUNGTAMCHIPHI_PHANBO", "TTCPCHO_FK", this.Id, "0", false);
		
		return true;
	}
	
	public boolean New()
	{
		String query = "";
		
		query = "INSERT INTO ERP_TRUNGTAMCHIPHI (MA, DIENGIAI, CONGTY_FK, NHANPHANBO, CHOPHANBO, CONGANSACH, " +
				"TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA )"  +
				"VALUES( N'" + this.ma + "', N'" + this.diengiai + "', '" + this.ctyId + "', '0', '" + this.chophanbo + "', '" + this.congansach + "'," +
				 "'" + this.trangthai + "', '" + Utility.getCurrentDate() + "','"+ Utility.getCurrentDate() + "','" + this.userId + "','" + this.userId + "' )";		
		
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
							String ttcpId = this.pbIds[i].split("-")[0];
							String pt = this.pbIds[i].split("-")[1];
							
							if(!pt.equals("0")){
								query = "INSERT INTO ERP_TRUNGTAMCHIPHI_PHANBO(TTCPCHO_FK, TTCPNHAN_FK, PHANTRAM) " +
										"VALUES ('" + this.Id + "', '" + ttcpId + "', '" + pt + "')";
							
								this.db.update(query);
							
								query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = '1' WHERE PK_SEQ = '" + ttcpId + "' ";
								this.db.update(query);
							}
						}
					}else{
						this.msg = "Vui lòng chọn Trung Tâm Chi Phí và nhập phần trăm để phân bổ" ;
						return false;
					}
				}
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
		
		//SYN QUA DMS			
		UtilitySyn.SynData(db, "ERP_TRUNGTAMCHIPHI", "ERP_TRUNGTAMCHIPHI", "PK_SEQ", this.Id, "0", false);
		UtilitySyn.SynData(db, "ERP_TRUNGTAMCHIPHI_PHANBO", "ERP_TRUNGTAMCHIPHI_PHANBO", "TTCPCHO_FK", this.Id, "0", false);
		return true;
	}

	public void setMsg(String msg) {
		this.msg =msg;
	}
	
	public String getMsg() {
		return this.msg;
	}
	
	public ResultSet getNhanphanbo(String ttcpId){
		String query = "";
		if(ttcpId.length() > 0){
			 query = 	"SELECT	TTCP.PK_SEQ AS TTCPID_NPB, PB.TTCPCHO_FK AS TTCPID_CPB, " + 
						"TTCP_CPB.MA AS MA_CPB, TTCP_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPNHAN_FK = TTCP.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_CPB ON TTCP_CPB.PK_SEQ = PB.TTCPCHO_FK " +
						"WHERE TTCP.PK_SEQ = " + ttcpId + " ORDER BY TTCP_CPB.MA ";
			 System.out.println("Nhan Phan Bo : "+query);
			 return this.db.get(query);
		}else if(chon.length() > 0){
			 query = 	"SELECT	TTCP.PK_SEQ AS TTCPID_NPB, PB.TTCPCHO_FK AS TTCPID_CPB, " + 
						"TTCP_CPB.MA AS MA_CPB, TTCP_CPB.DIENGIAI AS DIENGIAI_CPB, PB.PHANTRAM AS PHANTRAM_CPB " +
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPNHAN_FK = TTCP.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_CPB ON TTCP_CPB.PK_SEQ = PB.TTCPCHO_FK " +
						"WHERE TTCP.PK_SEQ = " + ttcpId + " ORDER BY TTCP_CPB.MA ";
			 System.out.println("Nhan Phan Bo : "+query);
			 return this.db.get(query);
			
		}
	 
		return null;
	}
	

	public ResultSet getChophanbo(String ttcpId){
		String query = "";
		if(ttcpId.length() > 0){
		
			query = 	"SELECT	TTCP.PK_SEQ AS TTCPID_CPB, PB.TTCPNHAN_FK AS TTCPID_NPB, " +
						"TTCP_NPB.MA AS MA_NPB, TTCP_NPB.DIENGIAI AS DIENGIAI_NPB, PB.PHANTRAM AS PHANTRAM_NPB " +
						"FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPCHO_FK = TTCP.PK_SEQ " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_NPB ON TTCP_NPB.PK_SEQ = PB.TTCPNHAN_FK " +		
						"WHERE TTCP.PK_SEQ = " + ttcpId + " ORDER BY TTCP_NPB.MA ";
			System.out.println(query);
			return this.db.get(query);

		}else{
			if(this.chon.length() > 0){
				query = 	"SELECT	TTCP.PK_SEQ AS TTCPID_CPB, 0 AS TTCPID_NPB, " +
							"TTCP.MA AS MA_NPB, TTCP.DIENGIAI AS DIENGIAI_NPB, 0 AS PHANTRAM_NPB " +
							"FROM ERP_TRUNGTAMCHIPHI TTCP " +
							"WHERE TTCP.PK_SEQ IN (" + this.chon + ") ORDER BY TTCP.MA ";
				System.out.println(query);
				return this.db.get(query);
			}
			
		}
		
		System.out.println(query);
		
		return null;
	}
	
	public ResultSet getChophanbothem(String ttcpId){
		String query;
		if(ttcpId.length() > 0){
		
			query = 	"SELECT PK_SEQ AS TTCPID, MA, DIENGIAI FROM " +
						"ERP_TRUNGTAMCHIPHI WHERE PK_SEQ NOT IN (" +
						"	SELECT	PB.TTCPNHAN_FK  " +						
						"	FROM ERP_TRUNGTAMCHIPHI TTCP " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON PB.TTCPCHO_FK = TTCP.PK_SEQ " +
						"	INNER JOIN ERP_TRUNGTAMCHIPHI TTCP_NPB ON TTCP_NPB.PK_SEQ = PB.TTCPNHAN_FK " +
						"	WHERE TTCP.PK_SEQ = " + ttcpId + " ) AND PK_SEQ <> '"  + ttcpId + "' " ;
			if(this.timkiem.length() > 0){
				query = query + " AND DIENGIAI LIKE N'%" + this.timkiem + "%' ";
			}
			
			query = query +	" ORDER BY MA ";
			return this.db.get(query);
		}else{
			query = 	"SELECT PK_SEQ AS TTCPID, MA, DIENGIAI FROM " +
						"ERP_TRUNGTAMCHIPHI ";
			
			if(this.timkiem.length() > 0){
				query = query + " WHERE DIENGIAI LIKE N'%" + this.timkiem + "%' ";
			}
			
			if(this.chon.length() > 0 & query.indexOf("WHERE") > 0){
				query = query + " AND PK_SEQ NOT IN (" + this.chon + ")";
			
			}else if(this.chon.length() > 0){
				query = query + " WHERE PK_SEQ NOT IN (" + this.chon + ")";
			}
			
			query = query +	" ORDER BY MA ";

			System.out.println(query);
			return this.db.get(query);
		}
	}
	
	public void initNew(){
	}

	public void DBClose(){
		try{
			if(this.ttcplist != null) this.ttcplist.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		this.db.shutDown();
	}
}