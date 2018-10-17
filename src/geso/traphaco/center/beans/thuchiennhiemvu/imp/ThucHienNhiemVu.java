package geso.traphaco.center.beans.thuchiennhiemvu.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.thuchiennhiemvu.IThucHienNhiemVu;
import geso.traphaco.center.db.sql.dbutils;

public class ThucHienNhiemVu implements IThucHienNhiemVu
{

	String id, thang, nam, dvkdId, KbhId, msg, doituong,userId,nvId;
	dbutils db;
	ResultSet rsKbh, rsDvkd, rsNv;

	public ThucHienNhiemVu()
	{
		this.id = "";
		this.thang =getMonth();
		this.nam = getYear();
		this.dvkdId = "";
		this.KbhId = "";
		this.doituong = "";
		this.userId="";
		this.msg="";
		this.nvId="";
		db = new dbutils();
	}

	public String getId()
	{
		return id;
	}

	public void setId(String id)
	{
		this.id = id;
	}

	public String getThang()
	{
		return thang;
	}

	public void setThang(String thang)
	{
		this.thang = thang;
	}

	public String getNam()
	{
		return nam;
	}

	public void setNam(String nam)
	{
		this.nam = nam;
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
		return KbhId;
	}

	public void setKbhId(String kbhId)
	{
		KbhId = kbhId;
	}

	public String getMsg()
	{
		return msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public String getDoituong()
	{
		return doituong;
	}

	public void setDoituong(String doituong)
	{
		this.doituong = doituong;
	}

	@Override
	public void DBClose()
	{
		
			try
			{
				if(rsDvkd!=null)
					this.rsDvkd.close();
				if(rsKbh!=null)
					this.rsKbh.close();
				if(this.rsNv!=null)
					this.rsNv.close();
				if(db!=null)
					db.shutDown();
			} catch (SQLException e)
			{
				
				e.printStackTrace();
			}
		
	}

	public void createRs()
	{
		String query="select pk_Seq,ten,diengiai from kenhbanhang ";
		this.rsKbh=this.db.get(query);
		
		query="select diengiai as ten,pk_seq from donvikinhdoanh";
		this.rsDvkd=this.db.get(query);
		
		
		if(this.doituong.trim().length()>0)
		{
			query=" SELECT PK_SEQ,DienGiai AS TEN FROM NHIEMVUNHANVIEN where DoiTuong='"+this.doituong+"' ";
			if(this.thang!=null && this.thang.length()>0)
				query+=" and Thang='"+this.thang+"'";
			if(this.nam!=null&&this.nam.length()>0)
				query+=" and Nam='"+this.nam+"'";
			
			System.out.println("Get sql : "+query);
			
			this.rsNv=this.db.get(query);
			System.out.println("Init___"+query);
		}		
	}

	public ResultSet getRsKbh()
	{
		return rsKbh;
	}

	public void setRsKbh(ResultSet rsKbh)
	{
		this.rsKbh = rsKbh;
	}

	public ResultSet getRsDvkd()
	{
		return rsDvkd;
	}

	public void setRsDvkd(ResultSet rsDvkd)
	{
		this.rsDvkd = rsDvkd;
	}

	public ResultSet getRsNv()
	{
		return rsNv;
	}

	public void setRsNv(ResultSet rsNv)
	{
		this.rsNv = rsNv;
	}

	@Override
	public String getUserId()
	{
		return this.userId;
	}

	@Override
	public void setUserId(String userId)
	{
		this.userId=userId;
	}

	@Override
	public void init()
	{
		createRs();
	}

	public String getNvId()
	{
		return nvId;
	}

	public void setNvId(String nvId)
	{
		this.nvId = nvId;
	}
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private String getMonth() 
	{
        DateFormat dateFormat = new SimpleDateFormat("MM");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getYear() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
}
