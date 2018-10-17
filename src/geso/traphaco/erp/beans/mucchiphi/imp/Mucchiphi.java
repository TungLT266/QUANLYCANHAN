package geso.traphaco.erp.beans.mucchiphi.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.mucchiphi.IMucchiphi;

public class Mucchiphi implements IMucchiphi {

	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String sotientu;
	String sotienden;	
	String trangthai;
	
	String ctyId;
	ResultSet ctylist;
	
	String[] dvthIds;
	ResultSet dvthlist;

	String msg;
	dbutils db;
	
	String userId;
	
	private Utility util;
	public Mucchiphi()
	{
		this.id = "";
		this.sotientu = "";	
		this.sotienden = "";
		this.trangthai = "";	
		this.ctyId = "";
		this.msg = "";
		this.userId = "";
		this.db = new dbutils();
		this.util=new Utility();
//		createCtyList();
//		createDvthList();
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

	public String getSotientu()
	{
		return this.sotientu;
	}

	public void setSotientu(String sotientu)
	{
		this.sotientu = sotientu;
	}

	public String getSotienden()
	{
		return this.sotienden;
	}

	public void setSotienden(String sotienden)
	{
		this.sotienden = sotienden;
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
	
	public String[] getDvthIds()
	{
		return this.dvthIds;
	}

	public void setDvthIds(String[] dvthIds)
	{
		this.dvthIds = dvthIds;
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

	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthlist = dvthlist;
	}

	public ResultSet getDvthList()
	{ 		
		return this.dvthlist;
		
	}
	
	public void init(){
		
		NumberFormat format = new DecimalFormat("#,###,###");
		if(this.id.length() >0){
			String query = 	"SELECT SOTIENTU, SOTIENDEN, TRANGTHAI FROM ERP_KHOANGCHIPHI WHERE PK_SEQ = '" + this.id + "' "; // AND CONGTY_FK = " + this.ctyId + " ";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				this.sotientu = format.format(rs.getDouble("SOTIENTU"));
				this.sotienden = format.format(rs.getDouble("SOTIENDEN"));
				this.trangthai = rs.getString("TRANGTHAI");
				rs.close();
			}catch(java.sql.SQLException e){}
		}
		createDvthList();
		createCtyList();
	}
	
	
	public boolean saveNewMucchiphi(){
		try{
			this.db.getConnection().setAutoCommit(false);
			
			if(this.sotientu.length() == 0) this.sotientu = "0";
			if(this.sotienden.length() == 0) this.sotienden = "0";
			
			String query = 	"INSERT INTO ERP_KHOANGCHIPHI (CONGTY_FK, SOTIENTU, SOTIENDEN, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI) " +
							"VALUES (" + this.ctyId + ", " + this.sotientu.replaceAll(",", "") + ", " + this.sotienden.replaceAll(",", "") + ", " + this.userId + ", " + this.userId + ", '" + this.getDateTime() + "', '" + this.getDateTime() + "', '1')";
			
			System.out.println("Update - step 1: " + query);
		
			if(!this.db.update(query)){
				this.db.update("ROLLBACK");
				return false;
			}
		
			query = "select id = SCOPE_IDENTITY()" ;
			ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("id");
			System.out.println("ID Mới: " + this.id);
			rs.close();
			
			query =		"SELECT PK_SEQ AS DVTHID, TEN AS DVTH, '0' AS  CHON " +
						"FROM ERP_DONVITHUCHIEN " +
						"WHERE CONGTY_FK ='"+ this.ctyId + "' "; //and pk_seq in   " + this.util.quyen_donvithuchien(this.userId) ;

			rs = this.db.get(query);
			
			while(rs.next()){
				query = "INSERT INTO ERP_KHOANGCHIPHI_DONVITHUCHIEN (DONVITHUCHIEN_FK, KHOANGCHIPHI_FK, CHON) " +
						"VALUES( " + rs.getString("DVTHID") + ", " + this.id + ", '0' )";
				System.out.println("Update - step 2: " + query);

				if(!this.db.update(query)){
					this.db.update("ROLLBACK");
					return false;
				}
				
				
			}
			
			rs.close();
			
			for(int i = 0; i < this.dvthIds.length; i++){
				query = "UPDATE ERP_KHOANGCHIPHI_DONVITHUCHIEN SET CHON = '1' " + 
						"WHERE DONVITHUCHIEN_FK = '" + this.dvthIds[i] + "' " +
						"AND KHOANGCHIPHI_FK =  '" + this.id + "' " ;
			
				System.out.println("Update - step 3: " + query);			
				
				if(!this.db.update(query)){
					this.db.update("ROLLBACK");
					return false;
				}
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(java.sql.SQLException e){}
		
		return true;
	}

	public boolean UpdateMucchiphi(){
		try{
			this.db.getConnection().setAutoCommit(false);
			
			if(this.sotientu.length() == 0) this.sotientu = "0";
			if(this.sotienden.length() == 0) this.sotienden = "0";
			
			String query = 	"UPDATE ERP_KHOANGCHIPHI SET SOTIENTU = " + this.sotientu.replaceAll(",", "") + ", " +
							"SOTIENDEN = " + this.sotienden.replaceAll(",", "") + ", " +
							"NGUOISUA = '" + this.userId + "', " +
							"NGAYSUA = '" + this.getDateTime() + "', " +
							"TRANGTHAI = '" + this.trangthai + "' " +
							"WHERE PK_SEQ = '" + this.id + "' ";
			System.out.println("Update - step 1: " + query);
		
			if(!this.db.update(query)){
				this.db.update("ROLLBACK");
				return false;
			}
		
			query = "UPDATE ERP_KHOANGCHIPHI_DONVITHUCHIEN SET CHON = '0' " +
					"WHERE KHOANGCHIPHI_FK = '" + this.id + "' " +
					"AND DONVITHUCHIEN_FK IN (SELECT PK_SEQ FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = '" + this.ctyId + "' )";

			System.out.println("Update - step 2: " + query);
			
			if(!this.db.update(query)){
				this.db.update("ROLLBACK");
				return false;
			}
		
			for(int i = 0; i < this.dvthIds.length; i++){
				query = "INSERT INTO ERP_KHOANGCHIPHI_DONVITHUCHIEN (DONVITHUCHIEN_FK, KHOANGCHIPHI_FK, CHON) VALUES" +
						"( '" + this.dvthIds[i] + "', ' " + this.id + "', '1')";
				
				System.out.println("Insert - step 3: " + query);
				
				if(!this.db.update(query)){
					query = "UPDATE ERP_KHOANGCHIPHI_DONVITHUCHIEN SET CHON = '1' " + 
							"WHERE DONVITHUCHIEN_FK = '" + this.dvthIds[i] + "' " +
							"AND KHOANGCHIPHI_FK =  '" + this.id + "' " ;
			
					System.out.println("Update - step 3: " + query);			
				
					if(!this.db.update(query)){
						this.db.update("ROLLBACK");
						return false;
					}
				}
			}

			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(java.sql.SQLException e){}
		return true;
	}
	
	private void createCtyList(){		
		this.ctylist =  this.db.get("SELECT PK_SEQ AS CTYID, TEN FROM ERP_CONGTY");
	}
	
	public void createDvthList(){
		if(this.ctyId.length() > 0){
			String query;
			if(this.id.length() > 0){
				query = 	"SELECT	DVTH.PK_SEQ AS DVTHID, DVTH.TEN AS DVTH,	" +
								" KCP_DVTH.CHON AS CHON	" +
								" FROM ERP_KHOANGCHIPHI_DONVITHUCHIEN KCP_DVTH " + 
								" INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = KCP_DVTH.DONVITHUCHIEN_FK " +
								" INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = DVTH.CONGTY_FK " +
								" WHERE KCP_DVTH.KHOANGCHIPHI_FK = '"+ this.id +"' " + //"  AND CTY.PK_SEQ ='"+ this.ctyId + "' " +
								" AND KCP_DVTH.CHON ='1'  " + //and dvth.pk_seq in " + this.util.quyen_donvithuchien(this.userId)+
							
								" UNION " +
								" SELECT PK_SEQ AS DVTHID, TEN AS DVTH, '0' AS  CHON " +
								" FROM ERP_DONVITHUCHIEN " +
								" WHERE PK_SEQ NOT IN " +
								" ( " +
								" SELECT	DVTH.PK_SEQ " + 		
								" FROM ERP_KHOANGCHIPHI_DONVITHUCHIEN KCP_DVTH " + 
								" INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = KCP_DVTH.DONVITHUCHIEN_FK " +
								" INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = DVTH.CONGTY_FK " +
								" WHERE KCP_DVTH.KHOANGCHIPHI_FK = '"+ this.id +"' " + //" AND CTY.PK_SEQ ='"+ this.ctyId + "' " +
								" AND KCP_DVTH.CHON ='1'  " + //and dvth.pk_seq in " + this.util.quyen_donvithuchien(this.userId)+
								" ) " + //" AND CONGTY_FK = " + this.ctyId + " " +
								" ORDER BY CHON DESC";
			}else{
				query =		"SELECT PK_SEQ AS DVTHID, TEN AS DVTH, '0' AS  CHON " +
							"FROM ERP_DONVITHUCHIEN " +
							"WHERE TRANGTHAI = 1 "; // AND CONGTY_FK ='"+ this.ctyId + "' "; //and pk_seq in  "+  this.util.quyen_donvithuchien(this.userId); ;
							
 			}

			System.out.println(query);
			this.dvthlist = this.db.get(query);
			
			System.out.println(query);
		}
	}


	private String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.dvthlist != null) this.dvthlist.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
		
}


