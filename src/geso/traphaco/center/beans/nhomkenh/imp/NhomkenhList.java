package geso.traphaco.center.beans.nhomkenh.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.center.beans.nhomkenh.INhomkenh;
import geso.traphaco.center.beans.nhomkenh.INhomkenhList;
import geso.traphaco.center.db.sql.dbutils;

public class NhomkenhList implements INhomkenhList 
{
private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String nhomkenh;
	String diengiai;
	String trangthai;
	String Msg;
	List<INhomkenh> nklist; 
	
	dbutils db;
	
	public NhomkenhList(String[] param)
	{
		this.db = new dbutils();
		this.nhomkenh = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		//init("");
	}
	
	public NhomkenhList()
	{
		this.db = new dbutils();
		this.nhomkenh = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.Msg ="";
		init("");
	}
	
	public List<INhomkenh> getNkList() 
	{
		return this.nklist;
	}

	public void setNkList(List<INhomkenh> nklist)
	{
		this.nklist = nklist;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getNhomkenh() 
	{
		return this.nhomkenh;
	}

	public void setNhomkenh(String nhomkenh) 
	{
		this.nhomkenh = nhomkenh;
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
	
	public void createNkBeanList(String query)
	{  	  
		ResultSet rs =  db.get(query);
		List<INhomkenh> nklist = new ArrayList<INhomkenh>();
		if (rs != null){		
			INhomkenh nkBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("nkTen");
					param[2]= rs.getString("diengiai");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					nkBean = new Nhomkenh(param);
					nklist.add(nkBean);															
				}
			}catch(Exception e){
		
			}
		}
		
		this.nklist = nklist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = "select a.pk_seq as id, a.ten as nkTen, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua from Nhomkenh a, nhanvien b, nhanvien c ";
			query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq"; 			

		}
		else
		{
			query = search;
		}
		
		createNkBeanList(query);  
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
