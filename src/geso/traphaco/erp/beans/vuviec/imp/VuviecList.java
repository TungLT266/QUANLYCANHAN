package geso.traphaco.erp.beans.vuviec.imp;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.vuviec.IVuviec;
import geso.traphaco.erp.beans.vuviec.IVuviecList;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.distributor.db.sql.dbutils;

public class VuviecList implements IVuviecList
{
	private static final long serialVersionUID = -9217977546733610214L;
	
	// Tieu chi tim kiem
	String userId;
	String ma;
	String diengiai;
	String trangthai;
	String Msg;
	String congty;
	List<IVuviec> dmpllist; 
	
	dbutils db;
	
	public VuviecList(String[] param)
	{
		this.db = new dbutils();
		this.ma = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		this.congty = param[3];
		//init("");
	}
	
	public VuviecList()
	{
		this.db = new dbutils();
		this.ma = "";
		this.diengiai = "";
		this.trangthai = "";
		this.Msg ="";
		this.congty = "";
		init("");
	}
	
	public List<IVuviec> getDmplList() 
	{
		return this.dmpllist;
	}

	public void setDmplList(List<IVuviec> dmpllist) 
	{
		this.dmpllist = dmpllist ;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
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
		List<IVuviec> dmpllist = new ArrayList<IVuviec>();
		if (rs != null){		
			IVuviec dmplBean;
			String[] param = new String[10];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ma");
					param[2]= rs.getString("vvTen");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					
					dmplBean = new Vuviec(param);
					dmpllist.add(dmplBean);															
				}
			}catch(java.sql.SQLException e){
		
			}
		}
		
		this.dmpllist = dmpllist;
	}
	
	public void init(String search) 
	{
		String query;	
		if (search.length() == 0)
		{
			query = " select a.pk_seq as id, a.ten as vvTen, a.ma, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua "+
				    " from vuviec a, nhanvien b, nhanvien c "+
			        " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq "+
			        " order by a.ma"; 			

		}
		else
		{
			query = search;
		}
		
		System.out.println("Câu init "+query);
		
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

	
	public String getCongty() {
		
		return this.congty;
	}

	
	public void setCongty(String congty) {
		
		this.congty = congty;
	}



	
}
