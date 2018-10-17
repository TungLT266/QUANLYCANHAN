package geso.traphaco.center.beans.xuatnhaptonStore.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.beans.xuatnhaptonStore.IXuatNhapTonStore;
import geso.traphaco.center.db.sql.dbutils;

public class XuatNhapTonStore implements IXuatNhapTonStore
{

	String msg, vungId, khuvucId, spId, userId, nppId, gsbhId, dvkdId, kbhId, nhanhangId, chungloaiId, fromMonth, toMonth, fromYear, toYear;

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getVungId()
	{
		return vungId;
	}

	public void setVungId(String vungId)
	{
		this.vungId = vungId;
	}

	public String getKhuvucId()
	{
		return khuvucId;
	}

	public void setKhuvucId(String khuvucId)
	{
		this.khuvucId = khuvucId;
	}

	public ResultSet getVungRs()
	{
		return vungRs;
	}

	public void setVungRs(ResultSet vungRs)
	{
		this.vungRs = vungRs;
	}

	public ResultSet getKhuvucRs()
	{
		return khuvucRs;
	}

	public void setKhuvucRs(ResultSet khuvucRs)
	{
		this.khuvucRs = khuvucRs;
	}

	ResultSet spRs, nppRs, gsbhRs, kbhRs, dvkdRs, chungloaiRs, nhanhangRs, vungRs, khuvucRs;

	dbutils db;

	public XuatNhapTonStore()
	{
		this.spId = "";
		this.userId = "";
		this.vungId = "";
		this.khuvucId = "";
		this.nppId = "";
		this.gsbhId = "";
		this.dvkdId = "";
		this.chungloaiId = "";
		this.nhanhangId="";
		this.fromMonth = "";
		this.fromYear = "";
		this.toMonth = "";
		this.toYear = "";
		this.msg="";
		this.kbhId = "";

		db = new dbutils();

	}

	public String getSpId()
	{
		return spId;
	}

	public void setSpId(String spId)
	{
		this.spId = spId;
	}

	public ResultSet getNppRs()
	{
		return nppRs;
	}

	public void setNppRs(ResultSet nppRs)
	{
		this.nppRs = nppRs;
	}

	public ResultSet getGsbhRs()
	{
		return gsbhRs;
	}

	public void setGsbhRs(ResultSet gsbhRs)
	{
		this.gsbhRs = gsbhRs;
	}

	public ResultSet getKbhRs()
	{
		return kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs)
	{
		this.kbhRs = kbhRs;
	}

	public ResultSet getDvkdRs()
	{
		return dvkdRs;
	}

	public void setDvkdRs(ResultSet dvkdRs)
	{
		this.dvkdRs = dvkdRs;
	}

	public ResultSet getChungloaiRs()
	{
		return chungloaiRs;
	}

	public void setChungloaiRs(ResultSet chungloaiRs)
	{
		this.chungloaiRs = chungloaiRs;
	}

	public ResultSet getNhanhangRs()
	{
		return nhanhangRs;
	}

	public void setNhanhangRs(ResultSet nhanhangRs)
	{
		this.nhanhangRs = nhanhangRs;
	}

	public ResultSet getSpRs()
	{
		return spRs;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;
	}

	public String getUserId()
	{
		return userId;
	}

	public void setUserId(String userId)
	{
		this.userId = userId;
	}

	public String getNppId()
	{
		return nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;
	}

	public String getGsbhId()
	{
		return gsbhId;
	}

	public void setGsbhId(String gsbhId)
	{
		this.gsbhId = gsbhId;
	}

	public String getDvkdId()
	{
		return dvkdId;
	}

	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}

	public String getKbhId()
	{
		return kbhId;
	}

	public void setKbhId(String kbhId)
	{
		this.kbhId = kbhId;
	}

	public String getNhanhangId()
	{
		return nhanhangId;
	}

	public void setNhanhangId(String nhanhangId)
	{
		this.nhanhangId = nhanhangId;
	}

	public String getChungloaiId()
	{
		return chungloaiId;
	}

	public void setChungloaiId(String chungloaiId)
	{
		this.chungloaiId = chungloaiId;
	}

	public String getFromMonth()
	{
		return fromMonth;
	}

	public void setFromMonth(String fromMonth)
	{
		this.fromMonth = fromMonth;
	}

	public String getToMonth()
	{
		return toMonth;
	}

	public void setToMonth(String toMonth)
	{
		this.toMonth = toMonth;
	}

	public String getFromYear()
	{
		return fromYear;
	}

	public void setFromYear(String fromYear)
	{
		this.fromYear = fromYear;
	}

	public String getToYear()
	{
		return toYear;
	}

	public void setToYear(String toYear)
	{
		this.toYear = toYear;
	}

	@Override
	public void createRs()
	{
		try{
		String sql = "";
		String query = "select PK_SEQ,TEN,DienGiai from KENHBANHANG ";
		this.kbhRs = this.db.get(query);
		query="select * from vung ";
		this.vungRs=this.db.get(query);
		query="select pk_Seq,ten from khuvuc ";
		this.khuvucRs=this.db.get(query);
		
		if (this.vungId.length() > 0)
		{
			this.khuvucRs = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} else
			this.khuvucRs = db.get("select pk_seq,ten from khuvuc ");

				
		query = "select pk_Seq,ten from GIAMSATBANHANG ";
		this.gsbhRs = this.db.get(query);			
		
		this.dvkdRs = db.get("select pk_seq,diengiai from donvikinhdoanh ");
		
		if (this.dvkdId.length() > 0)
			this.nhanhangRs = db.get("select PK_SEQ,TEN,ma from nhanhang where dvkd_fk ='" + this.dvkdId + "'");
		else
			this.nhanhangRs = db.get("select PK_SEQ,TEN,ma from nhanhang ");
		
		if(this.nhanhangId.length() > 0)
			sql = "select cl.pk_Seq,ten from chungloai cl inner join nhanhang_chungloai nhcl on nhcl.cl_fk = cl.pk_seq where nh_fk = '"+ this.nhanhangId +"' ";
		else
			sql = "select pk_Seq,ten  from chungloai";
		
		this.chungloaiRs = db.get(sql);
		
		query = "select pk_Seq,ma,ten from sanpham where 1=1 ";
		if (chungloaiId.length() > 0)
			query += " and chungloai_fk='" + this.chungloaiId + "'";
		if (nhanhangId.length() > 0)
			query += " and nhanhang_fk ='" + this.nhanhangId + "'";
		System.out.println("san pham : "+query);
		this.spRs = this.db.get(query);
		
		sql = "select pk_seq,ten from nhaphanphoi where trangthai ='1' ";
		
		if (this.khuvucId.length() > 0)
		{
			sql = sql + " and khuvuc_fk ='" + this.khuvucId + "'";
		}
		if (this.vungId.length() > 0)
		{
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		// System.out.println("Get NPP :"+sql);
		if (this.kbhId.length() > 0)
			sql = sql + " and pk_seq in (select npp_fk from NHAPP_KBH where kbh_fk ='" + this.kbhId + "')";		
				
		System.out.println("Get NPP :" + sql);
		
		sql += " order by ten ";
		//this.nppRs = db.getScrol(sql);
		this.nppRs = db.get(sql);
		}catch(Exception ex){
			System.out.println("Error : "+ex.toString());
			ex.printStackTrace();
		}

	}

	@Override
	public void closeDB()
	{

		try
		{
			if (nhanhangRs != null)
				this.nhanhangRs.close();

			if (chungloaiRs != null)
				this.chungloaiRs.close();

			if (nppRs != null)
				this.nppRs.close();

			if (gsbhRs != null)
				this.gsbhRs.close();

			if (dvkdRs != null)
				this.dvkdRs.close();

			if (kbhRs != null)
				this.kbhRs.close();

			if (spRs != null)
				this.spRs.close();

		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
