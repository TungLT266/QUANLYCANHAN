package geso.traphaco.center.beans.tieuchithuong.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.beans.tieuchithuong.IDuyetsuunghoList;
import geso.traphaco.center.db.sql.dbutils;

public class DuyetsuunghoList implements IDuyetsuunghoList 
{
	String userId;
	
	String tuquy;
	String denquy;
	String nam;
	String trangthai;
	String msg;
	String diengiai;
	int sl;
	public int getSl() {
		return sl;
	}

	public void setSl(int sl) {
		this.sl = sl;
	}

	ResultSet rsTieuchi;
	
	dbutils db;
	
	public DuyetsuunghoList()
	{
		this.tuquy="";
		this.denquy="";
		this.nam = "";
		this.trangthai = "";
		this.msg = "";
		this.diengiai = "";
		this.db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	
	public String getNam() 
	{
		return this.nam;
	}
	
	public void setNam(String nam)
	{
		this.nam = nam;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}


	public void init(String query)
	{
		String sql = "";
		String sql1="select count(*) as sl,a.pk_seq from nhaphanphoi a,nhanvien b where  a.SITECODE=b.CONVSITECODE and b.PK_SEQ="+this.userId +" group by a.pk_seq";
		System.out.println("--------------------"+sql1);
		ResultSet rs1=this.db.get(sql1);
		
		try {
			if (rs1.next())
			{
				this.sl=rs1.getInt("sl");
				if(rs1.getInt("sl")>0)
				{
					if(query.length() > 0)
						sql = query;
					else
					{
						sql = "select a.pk_seq, a.thang, a.quy, a.nam, a.landuyet, diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
								"from DUYETSUUNGHO a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq inner join NHANVIEN c on a.NGUOISUA = c.pk_seq " +
								" inner join DUYETSUUNGHO_NPP d on d.duyet_fk=a.PK_SEQ "+
								" where d.npp_fk="+rs1.getString("pk_seq")+
								"order by nam desc, thang desc";
					}
					
					System.out.println("1.Khoi tao chi tieu: " + sql);
					System.out.println("user ten"+this.userId);
					this.rsTieuchi = db.get(sql);
				}
				
			}
			else
			{
				this.sl=0;
				if(query.length() > 0)
					sql = query;
				else
				{
					sql = "select a.pk_seq, a.thang, a.quy, a.nam, a.landuyet, diengiai, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua  " +
							"from DUYETSUUNGHO a inner join NHANVIEN b on a.NGUOITAO = b.pk_seq inner join NHANVIEN c on a.NGUOISUA = c.pk_seq " +
							"order by nam desc, thang desc";
				}
				
				System.out.println("1.Khoi tao chi tieu: " + sql);
				System.out.println("user ten"+this.userId);
				this.rsTieuchi = db.get(sql);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public ResultSet getTieuchiSKUInRs()
	{
		return this.rsTieuchi;
	}

	public void setTieuchiSKUInRs(ResultSet tieuchiSKU) 
	{
		this.rsTieuchi = tieuchiSKU;
	}

	@Override
	public String getTuquy() {
		return this.tuquy;
	}

	@Override
	public void setTuquy(String tuquy) {
		this.tuquy=tuquy;
	}

	@Override
	public String getDenquy() {
		return this.denquy;
	}

	@Override
	public void setDenquy(String denquy) {
		this.denquy=denquy;
	}

	
	
}
