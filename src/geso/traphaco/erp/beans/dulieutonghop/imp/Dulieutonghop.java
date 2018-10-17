package geso.traphaco.erp.beans.dulieutonghop.imp;

import geso.traphaco.erp.beans.dulieutonghop.IDulieutonghop;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public class Dulieutonghop implements IDulieutonghop{

	private String ctyId;
	private String dlthId;
	private String ltkId;
	private ResultSet dlth;
	private ResultSet ctylist;
	private ResultSet loaitk;
	private ResultSet tk;
	private ResultSet tk_selected; 
	private String msg;
	private dbutils db;
	private String[] tkIds;

	private HttpServletRequest request;
	private String Userid;
	private   Utility util;
	
	public Dulieutonghop()
	{
		util = new Utility();
		this.ctyId = "";
		this.dlthId = "";
		this.ltkId = "";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getCtyId(){
		return this.ctyId;	
	}

	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public void setCtyList(ResultSet ctylist)
	{
		this.ctylist = ctylist;
	}

	public ResultSet getCtyList()
	{ 		
		return this.ctylist;
		
	}
	
	public String getDlthId(){
		return this.dlthId;	
	}

	public void setDlthId(String dlthId){
		this.dlthId = dlthId;
	}

	public void setDlthList(ResultSet dlth)
	{
		this.dlth = dlth;
	}

	
	public ResultSet getDlthList()
	{ 		
		return this.dlth;
		
	}

	public String getLtkId(){
		return this.ltkId;	
	}

	public void setLtkId(String ltkId){
		this.ltkId = ltkId;
	}
	
	public void setLoaiTK(ResultSet loaitk)
	{
		this.loaitk = loaitk;
	}

	public ResultSet getLoaiTK()
	{ 		
		return this.loaitk;
		
	}

	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}

	public void setTkIds(String[] tkIds){
		this.tkIds = tkIds;
	}
	
	public void setRequest(HttpServletRequest request){
		this.request = request;
	}
	
	public ResultSet getTaikhoan(){
		return this.tk;
		
	}
	
	public ResultSet getTaikhoan_Selected(){
		return this.tk_selected;
		
	}
	
	public void init(){
		String query = 	"SELECT PK_SEQ AS DLTHID, DIENGIAI FROM ERP_DULIEUTONGHOP WHERE TRANGTHAI = 1"; 
		this.dlth = this.db.get(query);
		
		query = "SELECT PK_SEQ AS LTKID, TEN AS LOAITK FROM ERP_LOAITAIKHOAN WHERE TRANGTHAI = 1";
		this.loaitk = this.db.get(query);
		
		if(this.dlthId.length() > 0){
			query = "SELECT DLTH_TK.DULIEUTONGHOP_FK AS DLTHID, TAIKHOAN_FK AS TKID, SOHIEUTAIKHOAN, TENTAIKHOAN " +
					"FROM ERP_DULIEUTONGHOP_TAIKHOAN DLTH_TK " +
					"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = DLTH_TK.TAIKHOAN_FK " +
					"WHERE DLTH_TK.DULIEUTONGHOP_FK =  " + this.dlthId;				
			tk_selected = this.db.get(query);
		
			query = "SELECT PK_SEQ AS TKID, SOHIEUTAIKHOAN, TENTAIKHOAN FROM ERP_TAIKHOANKT WHERE LOAITAIKHOAN_FK = " + this.ltkId  + " " +
					"AND PK_SEQ NOT IN (SELECT TAIKHOAN_FK FROM ERP_DULIEUTONGHOP_TAIKHOAN WHERE DULIEUTONGHOP_FK =  " + this.dlthId + ")";
			tk = this.db.get(query);
		}
	}

	
	public boolean Save()
	{
		String query;
		if(this.dlthId.length() > 0){
			try{
				this.db.getConnection().setAutoCommit(false);
				query = "DELETE ERP_DULIEUTONGHOP_TAIKHOAN WHERE DULIEUTONGHOP_FK = " + this.dlthId + "";
				System.out.println(query);
				if(!this.db.update(query)){
					this.msg = "Lưu lại không thành công!";
					db.getConnection().rollback();	
					return false;
				}
			
				if(this.tkIds.length > 0){
					for(int i = 0; i < this.tkIds.length; i++){
						query = "INSERT INTO ERP_DULIEUTONGHOP_TAIKHOAN(DULIEUTONGHOP_FK, TAIKHOAN_FK) VALUES(" + this.dlthId + ", " + tkIds[i] + ")";
						System.out.println(query);
						if(!this.db.update(query)){
							this.msg = "Lưu lại không thành công!" + query;
							db.getConnection().rollback();	
							return false;
						}
					}
				}
				
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
			}catch(java.sql.SQLException e){}
		}
		this.msg = "Lưu lại đã thành công!";
		return true;
	}
	
	public void DBClose(){
		try{
			if(this.ctylist != null) this.ctylist.close();
			if(this.dlth != null) this.dlth.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}

	@Override
	public void SetUserId(String userid) {
		// TODO Auto-generated method stub
		this.Userid=userid;
	}
	
}
