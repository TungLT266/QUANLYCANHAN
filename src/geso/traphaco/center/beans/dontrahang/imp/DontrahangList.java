package geso.traphaco.center.beans.dontrahang.imp;
import java.io.Serializable;
import java.sql.ResultSet;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.dontrahang.IDontrahangList;

public class DontrahangList implements IDontrahangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	private String userId;
	private String nppTen;
	private String nppId;
	private String sku;
	private String tungay;
	private String denngay;
	private String trangthai;
	private ResultSet dthList;
	private String malist;
	private dbutils db;
	private String msg="";
	public DontrahangList(String[] param)
	{
		this.db = new dbutils();
		this.sku = param[0];
		this.tungay = param[1];
		this.denngay = param[2];
		this.trangthai = param[3];
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		createDthlist("");
	}
	
	public DontrahangList()
	{
		this.db = new dbutils();
		this.userId = "";
		this.nppTen = "";
		this.nppId = "";
		this.sku = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		
	}
	
	public ResultSet getDthList()
	{
		return this.dthList;
	}
	
	public void setDthList(ResultSet dthList)
	{
		this.dthList = dthList;
	}

	public String getUserId()
	{
		return this.userId;
	}
	
	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getNppId()
	{
		return this.nppId;
	}
	
	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getNppTen()
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen)
	{
		this.nppTen = nppTen;
	}
	
	public String getSKU()
	{
		return this.sku;
	}
	
	public void setSKU(String sku)
	{
		this.sku = sku;
	}
	
	public String getTungay()
	{
		return this.tungay;
	}
	
	public void setTungay(String tungay)
	{
		this.tungay = tungay;
	}
	
	public String getDenngay()
	{
		return this.denngay;
	}
	
	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMalist()
	{
		return this.malist;
	}
	
	public void setMalist(String malist)
	{
		this.malist = malist;
	}

	public void createDthlist(String querystr){
		String query;
		Utility Ult = new Utility(); 
		if (querystr.length()>0){
			query = querystr;
			 query = query + " and a.npp_fk in" + Ult.quyen_npp(this.userId);
		     query = query + " and a.kbh_fk in" + Ult.quyen_kenh(this.userId);
	         query = query+	"order by a.trangthai, a.pk_seq";
		}else{
			 query = "select distinct a.ngaytra, a.pk_seq as chungtu,e.donvikinhdoanh, a.sotienavat, b.ten as nguoitao, c.ten as nguoisua, a.trangthai from dontrahang a, nhanvien b, nhanvien c, dontrahang_sp d, donvikinhdoanh e where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = d.dontrahang_fk and a.dvkd_fk = e.pk_seq " ;
			 query = query + " and a.npp_fk in" + Ult.quyen_npp(this.userId);
		     query = query + " and a.kbh_fk in" + Ult.quyen_kenh(this.userId);
	         query = query+	"order by a.trangthai, a.pk_seq";
		}
		
		System.out.println("Query ben ko Svl: " + query + "\n");
		this.dthList =  this.db.get(query);
	
	}
	
	private void getNPPInfo(){
		/*
		String query = "select a.pk_seq, a.ten from nhaphanphoi a, nhanvien b where a.ma = b.dangnhap and b.pk_seq = '" + this.userId + "'";
		
		ResultSet rs = this.db.get(query);
		try{
			if (rs != null){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				rs.close();
			}else{
				this.nppId = "";
				this.nppTen = "";
			}			
		}catch(Exception e){}
	*/
	}
	
	public void DBclose(){
		if(!(this.db == null)){
			this.db.shutDown();
		}
	}

	@Override
	public String getMessage() {
		// TODO Auto-generated method stub
		return this.msg;
	}

	@Override
	public void setMesage(String _msg) {
		// TODO Auto-generated method stub
		this.msg=_msg;
	}
	
}
