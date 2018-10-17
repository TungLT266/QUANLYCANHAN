package geso.traphaco.center.beans.loaicuahang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.traphaco.center.beans.loaicuahang.ILoaicuahang;
import geso.traphaco.center.beans.loaicuahang.ILoaicuahangList;
import geso.traphaco.center.db.sql.dbutils;

public class LoaicuahangList implements ILoaicuahangList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String loaicuahang;
	String diengiai;
	String trangthai;
	String Msg;
	List<ILoaicuahang> lchlist; 
	
	dbutils db;
	
	public LoaicuahangList(String[] param)
	{
		this.db = new dbutils();
		this.loaicuahang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
	}
	
	public LoaicuahangList()
	{
		this.db = new dbutils();
		this.loaicuahang = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
	public List<ILoaicuahang> getLchList() 
	{
		return this.lchlist;
	}

	public void setLchList(List<ILoaicuahang> lchlist)
	{
		this.lchlist = lchlist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getLoaicuahang() 
	{
		return this.loaicuahang;
	}

	public void setLoaicuahang(String loaicuahang) 
	{
		this.loaicuahang = loaicuahang;
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
	
	public void createLchBeanList(String query)
	{  	  
		ResultSet rs =  this.db.get(query);
		List<ILoaicuahang> lchlist = new ArrayList<ILoaicuahang>();
		if (rs != null){		
			ILoaicuahang lchBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("loai");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					lchBean = new Loaicuahang(param);
					lchlist.add(lchBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.lchlist = lchlist;
	}
	
	public void init(String search) 
	{
		String query;
		
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.loai, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua"; 
			query = query + " from loaicuahang a, nhanvien b, nhanvien c where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq";
		}
		else
		{
			query = search;
		}
		
		createLchBeanList(query);  
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

