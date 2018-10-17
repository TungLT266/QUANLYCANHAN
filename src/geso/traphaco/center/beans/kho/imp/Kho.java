package geso.traphaco.center.beans.kho.imp;

import geso.traphaco.center.beans.kho.IKho;
import geso.traphaco.center.db.sql.*;
import java.sql.ResultSet;


public class Kho implements IKho
{
	private static final long serialVersionUID = -9217977546733610415L;
	String id;
	String ten;
	String diengiai;
	String ngaytao;
	String ngaysua;
	String nguoitao;
	String nguoisua;
	String trangthai;
	String msg;
	dbutils db;
	
	public Kho(String[] param)
	{
		this.id = param[0];
		this.ten = param[1];	
		this.diengiai  = param[2];
		this.ngaytao = param[3];
		this.ngaysua = param[4];
		this.nguoitao = param[5];
		this.nguoisua = param[6];
		this.trangthai = param[7];	
		this.msg = "";
		this.db = new dbutils();
	}
	
	public Kho()
	{
		this.id = "";
		this.ten = "";
		this.diengiai  = "";
		this.ngaytao = "";
		this.ngaysua = "";
		this.nguoitao = "";
		this.nguoisua = "";
		this.trangthai = "";	
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getTen()
	{
		return this.ten;
	}

	public void setTen(String ten)
	{
		this.ten = ten;
	}

	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
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
	
	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}


	public String getMessage()
	{
		return this.msg;
	}

	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	public boolean saveNewKho(){
		// Insert data set into table "kho"
		try{
			
			this.db.getConnection().setAutoCommit(false);
			
					String query ="insert into kho values(N'" + this.ten + "','"+ this.ngaytao + "','" + this.ngaysua + "','" + this.nguoitao + "','" + this.nguoisua + "','" + this.trangthai + "',N'" + this.diengiai + "')";
					
					if (!this.db.update(query)){
						this.db.update("rollback");
						this.msg = "Khong the luu vao table 'Kho'" ;
						return false;			
					}
	
				query = "select IDENT_CURRENT('kho')as khoId";
				ResultSet rs = this.db.get(query);
			
				rs.next();
				String khoId = rs.getString("khoId");
				if(khoId==null){
					this.db.update("rollback");
					this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau :" + query;
					return false;
				}
				query = "  insert into nhapp_kho  " +
						" select '"+khoId+"' as kho_fk,a.pk_seq as npp_fk, b.pk_seq as sanpham_fk,0,0,0,0, c.kbh_fk as kbh_fk from nhaphanphoi a," +
						"  sanpham b, nhapp_kbh c, nhapp_nhacc_donvikd d, nhacungcap_dvkd e where a.pk_seq = c.npp_fk " +
						"  and a.pk_seq= d.npp_fk and d.ncc_dvkd_fk = e.pk_seq and e.dvkd_fk=b.dvkd_fk " +
						"  and  a.trangthai=1 and a.priandsecond=0 and b.trangthai=1 ";
				if(!this.db.update(query)){
					this.db.update("rollback");
					this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau :" + query;
					return false;
					
				}
				
				/*rs = this.db.get(query);
			
				while(rs.next()){
					query = "insert into nhapp_kho values('"+ khoId +"', '" + rs.getString("npp_fk") + "','" + rs.getString("sanpham_fk") + "','0','0','0','0','" + rs.getString("kbh_fk") + "')";
					
					if(!this.db.update(query)){
						this.db.update("rollback");
						this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau :" + query;
						return false;
						
					}
					
				}
				if(rs!=null){
					rs.close();
				}*/
				if(rs!=null) rs.close();				
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
				if(db!=null) db.shutDown();
				return true;
				
	        	
		}catch(Exception e){
			
			this.db.update("rollback");
			this.msg="Ban Khong The Cap Nhat Kho ,Loi Dong Lenh Sau "+e.toString();
			return false;
		}
				
		
	}
	
	public boolean UpdateKho(){

		// Update table "Kho"
		String query = "update kho set ten =N'" + this.ten + "',  ngaysua = '" + this.ngaysua + "',  nguoisua = '" + this.nguoisua + "', trangthai = '" + this.trangthai + "', diengiai = N'" + this.diengiai + "' where pk_seq = '" + this.id + "'" ;

		if(!this.db.update(query)){
			this.db.update("rollback");
			this.msg = "Khong the cap nhat table 'Kho'";
			return false; 
		}
		return true;
	}

	@Override
	public void DBClose() 
	{		
		if(db!=null){
			db.shutDown();
		}
	}
		
		
}


