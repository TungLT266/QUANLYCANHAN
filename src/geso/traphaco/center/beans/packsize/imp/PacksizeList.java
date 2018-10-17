package geso.traphaco.center.beans.packsize.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.traphaco.center.beans.kenhbanhang.IKenhbanhang;
import geso.traphaco.center.beans.kenhbanhang.IKenhbanhangList;
import geso.traphaco.center.beans.packsize.IPacksize;
import geso.traphaco.center.beans.packsize.IPacksizeList;
import geso.traphaco.center.db.sql.dbutils;

public class PacksizeList implements IPacksizeList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String kenhbanhang;
	String diengiai;
	String trangthai;
	String Msg;
	ResultSet rsPacksize; 
	
	dbutils db;
	
	public PacksizeList(String[] param)
	{
		this.db = new dbutils();
		this.kenhbanhang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		
	}
	
	public PacksizeList()
	{
		this.db = new dbutils();
		this.kenhbanhang = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.Msg ="";
		
	}
	
	
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(String kenhbanhang) 
	{
		this.kenhbanhang = kenhbanhang;
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
	
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq, a.ten , a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from packsize a " +
					"inner join  nhanvien b on b.pk_seq=a.nguoitao " +
					"inner join  nhanvien c on a.nguoisua = c.pk_seq";		

		}
		else
		{
			query = search;
		}
		
		this.rsPacksize=db.get(query);
		
	}

	public void DBClose(){
		if (this.db != null) 
			this.db.shutDown();
	}


	public void setMsg(String Msg) {
	     this.Msg = Msg;
		
	}
	public String getMsg() {
          return this.Msg;
	}

	@Override
	public ResultSet getKbhList() {
		// TODO Auto-generated method stub
		return rsPacksize;
	}

	@Override
	public void setKbhList(ResultSet kbhlist) {
		// TODO Auto-generated method stub
		rsPacksize=kbhlist;
	}

	
	
}
