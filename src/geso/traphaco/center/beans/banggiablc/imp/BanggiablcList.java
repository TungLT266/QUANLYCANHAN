package geso.traphaco.center.beans.banggiablc.imp;

import geso.traphaco.center.beans.banggiablc.IBanggiablcList;
import geso.traphaco.center.beans.banggiablc.imp.BanggiablcList;
import geso.traphaco.center.beans.banggiablc.IBanggiablc;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;
import geso.traphaco.center.db.sql.dbutils;

public class BanggiablcList implements IBanggiablcList
{
	private static final long serialVersionUID = -927977546783610214L;
	
	String userId;
	String ten;
	ResultSet dvkd;
	String dvkdId;
	String trangthai;
	List<IBanggiablc> bgblclist;
	dbutils db;
	
	//Constructor
	public BanggiablcList(String[] param)
	{
		this.ten = param[0];		
		this.dvkdId = param[1];
		this.trangthai = param[3];
		this.db = new dbutils();
	}
	
	public BanggiablcList()
	{
		this.ten= "";		
		this.dvkdId= "";
		this.trangthai = "2";
		this.db = new dbutils();
		init("");
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}

	
	public String getDvkdId() 
	{
		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) 
	{
		this.dvkdId = dvkdId;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public List<IBanggiablc> getBgblcList() 
	{
		return this.bgblclist;
	}

	public void setBgblcList(List<IBanggiablc> bgblclist) 
	{
		this.bgblclist = bgblclist;
	}


	public ResultSet getDvkd() 
	{
		return this.dvkd;
	}

	public void setDvkd(ResultSet dvkd) 
	{
		this.dvkd = dvkd;
	}

	public boolean saveNewBgblc() 
	{
		return false;
	}

	public boolean UpdateBgblc() 
	{
		return false;
	}

	
	private void createDvkdRS(){  				
				
		//this.dvkd  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' ");;
		this.dvkd  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai = '1' order by a.donvikinhdoanh");
		
	}

	public void createBanggiablcBeanList(String query){  	
	    
		ResultSet rs =  db.get(query);
		List<IBanggiablc> bgblclist = new ArrayList<IBanggiablc>();
		if (rs != null){		
			IBanggiablc bgblcBean;
			String[] param = new String[15];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("dvkd");
					param[3]= rs.getString("trangthai");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					
					bgblcBean = new Banggiablc(param);
					bgblclist.add(bgblcBean);															
				}
				rs.close();
			}catch(Exception e){
		
			}
		}
		
		this.bgblclist = bgblclist;
	}

	public void init(String search){

		String query;
		
		if (search.length()>0){
			query = search;
		}else{
			//query = "select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd from banggiabanlechuan a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
			//query = "select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd from banggiabanlechuan a, donvikinhdoanh b, nhanvien c, nhanvien d,nhacungcap_dvkd e where b.pk_seq = e.dvkd_fk and e.checked ='1'and a.dvkd_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
			query ="select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, c.ten as nguoitao, a.ngaytao as ngaytao, d.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId from banggiabanlechuan a, donvikinhdoanh b, nhanvien c, nhanvien d where a.dvkd_fk=b.pk_seq and a.nguoitao = c.pk_seq and a.nguoisua = d.pk_seq";
		}
		
		System.out.println("::: INIT BG: " + query);
		createBanggiablcBeanList(query);
	    
		createDvkdRS();
	}

	@Override
	public void DbClose() {
		try{
		// TODO Auto-generated method stub
		if(this.dvkd!=null){
			this.dvkd.close();
		}
		
		if(this.db!=null){
			db.shutDown();
		}
		}catch(Exception er){
			
		}
	}
	
}

