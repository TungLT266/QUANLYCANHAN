package geso.traphaco.center.beans.quanhuyen.imp;

import geso.traphaco.center.beans.quanhuyen.IQuanHuyen;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;


public class QuanHuyen implements IQuanHuyen {
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	
	String qhId, diengiai, trangthai, ttId, msg, nguoitao, nguoisua, ngaytao, ngaysua;
	ResultSet ttlist;
	
	dbutils db;
	
	public QuanHuyen(String[] param)
	{
		this.id = param[0];
		this.trangthai = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.diengiai = param[6];
		this.msg = "";
		this.db = new dbutils();
	}
	
	public QuanHuyen(String id)
	{
		this.id = id;

		this.ttId = "";
		this.qhId = "";
		
		this.diengiai = "";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		
		this.msg = "";
		this.db = new dbutils();
		if(id.length() > 0)
			this.init();
		else
			this.createRS();
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
	
	public String getDiengiai()
	{
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
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
	
	public String getNgaysua()
	{
		return this.ngaysua;
		
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
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
	
	private void init()
	{	
		String query = 
			" SELECT QH.pk_Seq AS QHID, QH.Ten AS QHTEN, QH.TRANGTHAI AS TRANGTHAI, QH.tinhthanh_fk AS TTID FROM QuanHuyen QH " +
			" WHERE QH.PK_SEQ = '"+ this.id +"' ";
		
		System.out.println("px init : "+query);
        ResultSet rs =  this.db.get(query);
        try
        {
            rs.next();        	
        	this.id = rs.getString("QHID");
        	this.diengiai = rs.getString("QHTEN");
        	this.ttId = rs.getString("TTID");
        	/*this.qhId = rs.getString("QHID");*/
        	this.trangthai = rs.getString("TRANGTHAI");
       	}
        catch(Exception e){}
       	createRS(); 
       	
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public ResultSet getTinhthanhRs() {
		
		return this.ttlist;
	}

	@Override
	public void setTinhthanhRs(ResultSet ttRs) {
		
		this.ttlist = ttRs;
	}

	@Override
	public String getTinhthanhId() {
		
		return this.ttId;
	}

	@Override
	public void setTinhthanhId(String ttId) {
		
		this.ttId = ttId;
	}

	/*@Override
	public ResultSet getQuanhuyenRs() {
		
		return this.qhlist;
	}

	@Override
	public void setQuanhuyenRs(ResultSet qhRs) {
		
		this.qhlist = qhRs;
	}

	@Override
	public String getQuanhuyenId() {
		
		return this.qhId;
	}

	@Override
	public void setQuanhuyenId(String qhId) {
		
		this.qhId = qhId;
	}*/

	@Override
	public boolean CreateQh() {
	
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		String command="";
			
		command = "INSERT INTO QUANHUYEN (TEN, TINHTHANH_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI) " +
		   "VALUES (N'" + this.diengiai + "','"+ this.ttId +"', '" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao + "','" + this.nguoitao + "','" + this.trangthai + "')"; 

		if (!db.update(command))
		{
			this.msg = command;
			db.update("rollback");
			return false;
		}
		
		String id = "";
		ResultSet rsId = db.get("select scope_identity() as ID ");
		if(rsId.next())
		{
			id = rsId.getString("ID");
		}
		rsId.close();
		
		command = "update QuanHuyen set diengiai = cast(TinhThanh_FK as varchar) + '-' + dbo.ftBoDau(Ten) where pk_seq = '"+ id +"'";
		if (!db.update(command))
		{
			this.msg = command;
			db.update("rollback");
			return false;
		}
		
		command = "SELECT diengiai AS DG FROM QUANHUYEN WHERE PK_SEQ = '"+ id +"' ";
		
		System.out.println("command : "+command);
		ResultSet rsspell = db.get(command);
		rsspell.next();

		Integer str = 0;
		String query =
		"select COUNT(*) as num from ( " +
		"select pk_seq from quanhuyen where diengiai = '"+ rsspell.getString("DG") +"' and pk_seq != '"+ id +"' ) as dt ";

		rsspell.close();
		System.out.println("CHECK : "+query);
		ResultSet rscheck = db.get(query);
		if(rscheck != null)
		{
			rscheck.next();
			str = rscheck.getInt("num");
			System.out.println("str : "+str);
			if(str > 0)
			{
				this.msg = "Đã có thông tin quận huyện này trong hệ thống. Vui lòng nhập lại !";
				db.update("rollback");
				return false;
			}
		}
		rscheck.close();
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.db.update("rollback");
			this.msg="Khong the cap nhat lai bang nay,xay ra loi trong dong lenh sau "+ er.toString();
			return false;
		}
		return true;
	}

	@Override
	public boolean UpdateQh() {
		
		try{
			this.db.getConnection().setAutoCommit(false);
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		String command="";
			
		command = "UPDATE QUANHUYEN SET TEN = N'"+ this.diengiai +"', TINHTHANH_FK = '"+ this.ttId +"', " +
				  "TRANGTHAI = '"+ this.trangthai +"', NGAYSUA = '"+ this.ngaysua +"', NGUOISUA = '"+ this.nguoisua +"' WHERE PK_SEQ = '"+ this.id +"' ";

		if (!db.update(command))
		{
			this.msg = command;
			db.update("rollback");
			return false;
		}
		
		command = "update QuanHuyen set diengiai = cast(TinhThanh_FK as varchar) + '-' + dbo.ftBoDau(Ten) where pk_seq = '"+ this.id +"'";
		if (!db.update(command))
		{
			this.msg = command;
			db.update("rollback");
			return false;
		}
		
		command = "SELECT diengiai AS DG FROM QUANHUYEN WHERE PK_SEQ = '"+ this.id +"' ";
		
		System.out.println("command : "+command);
		ResultSet rsspell = db.get(command);
		rsspell.next();

		Integer str = 0;
		String query =
		"select COUNT(*) as num from ( " +
		"select pk_seq from quanhuyen where diengiai = '"+ rsspell.getString("DG") +"' and pk_seq != '"+ this.id +"' ) haha ";

		rsspell.close();
		System.out.println("CHECK : "+query);
		ResultSet rscheck = db.get(query);
		if(rscheck != null)
		{
			rscheck.next();
			str = rscheck.getInt("num");
			System.out.println("str : "+str);
			if(str > 0)
			{
				this.msg = "Đã có thông tin quận huyện này trong hệ thống. Vui lòng nhập lại !";
				db.update("rollback");
				return false;
			}
		}
		rscheck.close();
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.db.update("rollback");
			this.msg="Khong the cap nhat lai bang nay,xay ra loi trong dong lenh sau "+ er.toString();
			return false;
		}
		return true;
	}

	@Override
	public void createRS() {
		if(this.db == null)
			db = new dbutils();
		this.ttlist = db.get("select * from tinhthanh");
		
		/*String sql = "select * from quanhuyen";
		if(!this.ttId.equals(""))
		{
			sql += " where TINHTHANH_FK = '"+ this.ttId +"' ";
		}
		this.qhlist = db.get(sql);*/
	}
}
