package geso.traphaco.center.beans.hethongbanhang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.beans.hethongbanhang.IHethongbanhang;
import geso.traphaco.center.beans.hethongbanhang.IHethongbanhangList;
import geso.traphaco.center.db.sql.dbutils;

public class HethongbanhangList implements IHethongbanhangList
{
private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String hethongbanhang;
	String diengiai;
	String trangthai;
	String Msg;
	List<IHethongbanhang> htbhlist; 
	
	dbutils db;
	
	public HethongbanhangList(String[] param)
	{
		this.db = new dbutils();
		this.hethongbanhang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		//init("");
	}
	
	public HethongbanhangList()
	{
		this.db = new dbutils();
		this.hethongbanhang = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
	public List<IHethongbanhang> getHtbhList() 
	{
		return this.htbhlist;
	}

	public void setHtbhList(List<IHethongbanhang> htbhlist)
	{
		this.htbhlist = htbhlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getHethongbanhang() 
	{
		return this.hethongbanhang;
	}

	public void setHethongbanhang(String hethongbanhang) 
	{
		this.hethongbanhang = hethongbanhang;
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
	
	public void createHtbhBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IHethongbanhang> htbhlist = new ArrayList<IHethongbanhang>();
		if (rs != null){		
			IHethongbanhang htbhBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("htbhTen");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					htbhBean = new Hethongbanhang(param);
					htbhlist.add(htbhBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.htbhlist = htbhlist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.ten as htbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from hethongbanhang a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			

		}
		else
		{
			query = search;
		}
		
		createHtbhBeanList(query);  
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
}
