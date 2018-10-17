package geso.traphaco.erp.beans.chinhsachduyetmuahang.imp;

import geso.traphaco.erp.beans.chinhsachduyetmuahang.IChinhsachduyetmuahangList;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public class ChinhsachduyetmuahangList implements IChinhsachduyetmuahangList {

	private String ctyId;
	private String dvthId;
	private ResultSet cs;
	private ResultSet dvthlist;
	private String msg;
	private dbutils db;
	private String Userid;
	
	String chixem;
	
	public ChinhsachduyetmuahangList()
	{
		this.ctyId = "";
		this.dvthId = "";
		this.msg = "";
		this.db = new dbutils();
		this.chixem = "0";
		this.Userid = "";
	}

	public String getCtyId(){
		return this.ctyId;	
	}

	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	
	public String getDvthId(){
		return this.dvthId;	
	}

	public void setDvthId(String dvthId){
		this.dvthId = dvthId;
	}

	public void setDvthList(ResultSet dvthlist)
	{
		this.dvthlist = dvthlist;
	}

	
	public ResultSet getDvthList()
	{ 		
		return this.dvthlist;
		
	}

	public void setCS(ResultSet cs)
	{
		this.cs = cs;
	}

	public ResultSet getCS()
	{ 		
		return this.cs;
		
	}

	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	
	public void createDvthList()
	{
		String sql = "SELECT PK_SEQ AS DVTHID, TEN AS DVTHTEN FROM ERP_DONVITHUCHIEN WHERE CONGTY_FK = " + this.ctyId + " ";

		System.out.println(sql);
		
		this.dvthlist = this.db.get(sql);
	}
	
	public void init(){
		String query = 	"SELECT	DCP.PK_SEQ AS ID, DCP.DIENGIAI AS DCP, DVTH.TEN AS DVTH, ISNULL(DCP.TRANGTHAI, 0) AS TRANGTHAI, " + 
						"DCP.NGAYTAO, NV1.TEN AS NGUOITAO, DCP.NGAYSUA, NV1.TEN AS NGUOISUA " +
						"FROM ERP_CHINHSACHDUYET DCP " +
						"INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = DCP.DONVITHUCHIEN_FK " +
						"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = DCP.NGUOITAO " +
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = DCP.NGUOISUA " +
						"WHERE 1 = 1 "; // AND DCP.CONGTY_FK = " + this.ctyId + " ";
		if(this.dvthId.length() > 0) query = query + " AND DVTH.PK_SEQ = " + this.dvthId + " ";
		query = query + " ORDER BY DVTH.TEN, DCP.DIENGIAI ";
		
		this.cs = this.db.get(query);
		this.createDvthList();
	}

	
	public void DBClose(){
		try{
			if(this.dvthlist != null) this.dvthlist.close();
			if(this.cs != null) this.cs.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}

	@Override
	public void SetUserId(String userid) {
		// TODO Auto-generated method stub
		this.Userid=userid;
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
