package geso.traphaco.center.beans.hangcuahang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.traphaco.center.beans.hangcuahang.IHangcuahang;
import geso.traphaco.center.beans.hangcuahang.IHangcuahangList;
import geso.traphaco.center.db.sql.dbutils;

public class HangcuahangList implements IHangcuahangList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String hangcuahang;
	String diengiai;
	String trangthai;
	String Msg;
	List<IHangcuahang> hchlist; 
	
	dbutils db;
	
	public HangcuahangList(String[] param)
	{
		this.db = new dbutils();
		this.hangcuahang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
	}
	
	public HangcuahangList()
	{
		this.db = new dbutils();
		this.hangcuahang = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
	public List<IHangcuahang> getHchList() 
	{
		return this.hchlist;
	}

	public void setHchList(List<IHangcuahang> hchlist)
	{
		this.hchlist = hchlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getHangcuahang() 
	{
		return this.hangcuahang;
	}

	public void setHangcuahang(String hangcuahang) 
	{
		this.hangcuahang = hangcuahang;
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
	
	public void createHchBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IHangcuahang> hchlist = new ArrayList<IHangcuahang>();
		if (rs != null){		
			IHangcuahang hchBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("hang");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					hchBean = new Hangcuahang(param);
					hchlist.add(hchBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.hchlist = hchlist;
	}
	
	public void init(String search) 
	{
		String query;
		
		if (search.length() == 0)
		{
	    	query = "select a.pk_seq as id, a.hang, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"; 
			query = query + " from hangcuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq order by hang";
		}
		else
		{
			query = search;
		}
		
		createHchBeanList(query);  
	}
	
	public void closeDB(){
		if(this.db != null)
			this.db.shutDown();
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
	}

	
	public String getMsg() {
		
		return this.Msg;
	}

}

