package geso.traphaco.erp.beans.khovung.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.khovung.IKhoVung;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KhoVung implements IKhoVung
{
	private static final long serialVersionUID = -9217977546733610214L;
	String ctyId;
	String userId;
	String id;
	String ma;
	String ten;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	dbutils db;
	
	public KhoVung(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.ma = param[1];
		this.ten = param[2];
		this.trangthai = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.msg = "";
		
	}
	
	public KhoVung(String id)
	{
		this.db = new dbutils();
		this.ctyId = "";
		this.id = id;
		this.ma = "";
		this.ten = "";
		this.trangthai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}
	
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	
	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;
	}

	public void setNgaytao(String ngaytao) 
	{
		this.ngaytao = ngaytao;
	}

	public String getNguoitao() 
	{
		return this.nguoitao;
	}

	public void setNguoitao(String nguoitao) 
	{
		this.nguoitao = nguoitao;
	}

	public String getNgaysua() 
	{
		return this.ngaysua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.ngaysua = ngaysua;
	}

	public String getNguoisua() 
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.nguoisua = nguoisua;
	}

	public String getMessage() 
	{
		return this.msg;
	}

	public void setMessage(String msg) 
	{
		this.msg = msg;
	}

	public boolean CreateKhoVung()
	{
		try{
			this.db.getConnection().setAutoCommit(false);
			
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String command="";
		command = "insert into ERP_KHOVUNG(congty_fk, ma, ten, trangthai, ngaytao, ngaysua, nguoitao, nguoisua) " +
  		 		  "values(" + this.ctyId + ", N'" + this.ma + "', N'" + this.ten + "'," + this.trangthai + ",'" + this.ngaytao + "','" + this.ngaytao + "','" + this.userId + "','" + this.userId + "')"; 
		
		if (!this.db.update(command)){
			this.msg = command;		
			this.db.update("rollback");
			return false;
		}
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.db.update("rollback");
			this.msg="Không thể thêm kho vùng,lỗi tại dòng lệnh: " + er.toString();
			return false;
		}
		return true;
	}

	public boolean UpdateKhoVung() 
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		String command;
		 command ="update ERP_KHOVUNG set ma = N'" + this.ma + "', ten = N'" + this.ten + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";

		 if (!this.db.update(command)){
			this.msg = "update vung set ma = N'" + this.ma + "', ten = N'" + this.ten + "', trangthai='" + this.trangthai + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "' where pk_seq = '" + this.id + "'";			
			this.db.update("rollback");
			return false;
		}

		return true; 
	}

	public void init()
	{	
		
		String query = 	"SELECT KV.PK_SEQ as ID, KV.MA, KV.TEN, KV.TRANGTHAI, KV.NGAYTAO, NV1.TEN as NGUOITAO, KV.NGAYSUA, NV2.TEN as NGUOISUA \n " +
						"FROM ERP_KHOVUNG KV \n " +
						"INNER JOIN NHANVIEN NV1 on nv1.pk_seq = KV.NGUOITAO \n " +
						"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = KV.NGUOISUA \n " +
						"WHERE KV.CONGTY_FK = " + this.ctyId + " AND KV.PK_SEQ = " + this.id ;
		
        ResultSet rs =  this.db.get(query);
        if (rs != null)
        {
	        try{
	            if (rs.next())
	            {
		        	
		        	this.ma = rs.getString("ma");
		        	this.ten = rs.getString("ten");
		        	this.trangthai = rs.getString("trangthai");
		        	this.ngaytao = rs.getString("ngaytao");
		        	this.nguoitao = rs.getString("nguoitao");
		        	this.ngaysua = rs.getString("ngaysua");
		        	this.nguoisua = rs.getString("nguoisua");

	            }
	       	}catch (java.sql.SQLException e){
	       		e.printStackTrace();
	       	}finally{
	       		try {
					rs.close();
				} catch (Exception e2) {
					// TODO: handle exception
					e2.printStackTrace();
				}
	       	}
        }	       	
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}	
	
	public void closeDB(){
		if (this.db != null) this.db.shutDown();
			
	}

	
}

