package geso.traphaco.center.beans.kenhbanhang.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.traphaco.center.beans.kenhbanhang.IKenhbanhang;
import geso.traphaco.center.beans.kenhbanhang.IKenhbanhangList;
import geso.traphaco.center.db.sql.dbutils;

public class KenhbanhangList implements IKenhbanhangList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String kenhbanhang;
	String diengiai;
	String trangthai;
	String Msg;
	List<IKenhbanhang> kbhlist; 
	
	dbutils db;
	
	public KenhbanhangList(String[] param)
	{
		this.db = new dbutils();
		this.kenhbanhang = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		//init("");
	}
	
	public KenhbanhangList()
	{
		this.db = new dbutils();
		this.kenhbanhang = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
	public List<IKenhbanhang> getKbhList() 
	{
		return this.kbhlist;
	}

	public void setKbhList(List<IKenhbanhang> kbhlist)
	{
		this.kbhlist = kbhlist;
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
	
	public void createKbhBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<IKenhbanhang> kbhlist = new ArrayList<IKenhbanhang>();
		if (rs != null){		
			IKenhbanhang kbhBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("kbhTen");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					kbhBean = new Kenhbanhang(param);
					kbhlist.add(kbhBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.kbhlist = kbhlist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.ten as kbhTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from kenhbanhang a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			

		}
		else
		{
			query = search;
		}
		
		createKbhBeanList(query);  
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
