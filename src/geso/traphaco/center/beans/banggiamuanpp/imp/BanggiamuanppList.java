package geso.traphaco.center.beans.banggiamuanpp.imp;

import geso.traphaco.center.beans.banggiamuanpp.IBanggiamuanppList;
import geso.traphaco.center.beans.banggiamuanpp.imp.BanggiamuanppList;
import geso.traphaco.center.beans.banggiamuanpp.IBanggiamuanpp;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.ArrayList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

public class BanggiamuanppList  extends Phan_Trang implements IBanggiamuanppList
{
	private static final long serialVersionUID = -927977546783610214L;
	
	String userId;
	String userTen;
	String ten;
	ResultSet dvkdIds;
	String dvkdId;
	ResultSet kenhIds;
	String kenhId;	
	String trangthai;
	String tungay,denngay,msg;
	

	List<IBanggiamuanpp> bgmuanpplist;
	ResultSet bglist;
	dbutils db;
	
	//Constructor
	public BanggiamuanppList(String[] param)
	{
		
		this.ten = param[1];		
		this.dvkdId = param[2];
		this.kenhId = param[3];
		this.trangthai = param[4];
	
		this.tungay="";
		this.denngay="";
		this.msg="";
		this.db = new dbutils();
	}
	
	public BanggiamuanppList()
	{
		this.ten = "";		
		this.dvkdId = "";
		this.kenhId = "";
		this.trangthai = "";
		this.tungay="";
		this.denngay="";
		this.msg="";
		this.db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
		
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
	
	public String getKenhId() 
	{
		return this.kenhId;
	}

	public void setKenhId(String kenhId) 
	{
		this.kenhId = kenhId;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public List<IBanggiamuanpp> getBgmuanppList() 
	{
		return this.bgmuanpplist;
	}

	public void setBgmuanppList(List<IBanggiamuanpp> bgmuanpplist) 
	{
		this.bgmuanpplist = bgmuanpplist;
	}


	public ResultSet getDvkd() 
	{
		return this.dvkdIds;
	}

	public void setDvkd(ResultSet dvkdIds) 
	{
		this.dvkdIds = dvkdIds;
	}

	public ResultSet getKenh() 
	{
		return this.kenhIds;
	}

	public void setKenh(ResultSet kenhIds) 
	{
		this.kenhIds = kenhIds;
	}

	public ResultSet getBglist() 
	{
		return this.bglist;
	}

	public void setBglist(ResultSet bglist) 
	{
		this.bglist = bglist;
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
		
		//this.dvkdIds  =  this.db.get("select donvikinhdoanh as dvkd, pk_seq as dvkdId from donvikinhdoanh where trangthai='1' ");;
		this.dvkdIds  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");
	}

	private void createKenhRS(){  				
		
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}

	public void createBanggiamuanppBeanList(String query){  	
		
		ResultSet rs =  this.db.get(query);
		List<IBanggiamuanpp> bgmuanpplist = new ArrayList<IBanggiamuanpp>();
		if (rs != null){		
			IBanggiamuanpp bgmuanppBean;
			String[] param = new String[15];
			try{
				while(rs.next()){
					param[0]= rs.getString("id");
					param[1]= rs.getString("ten");
					param[2]= rs.getString("dvkd");
					param[3]= rs.getString("kenh");
					param[4]= rs.getString("trangthai");
					param[5]= rs.getString("ngaytao");
					param[6]= rs.getString("nguoitao");
					param[7]= rs.getString("ngaysua");
					param[8]= rs.getString("nguoisua");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					bgmuanppBean = new Banggiamuanpp(param);
					bgmuanpplist.add(bgmuanppBean);															
				}
				rs.close();
			}catch(Exception e){
		
			}
			 
		}
		
		this.bgmuanpplist = bgmuanpplist;
	}

	public void init(String search){
		
		String query;
		
		if (search.length()>0){
			query = search;
			System.out.println("\nSecond Query: "+query);
		}else
		{
			query = "select a.pk_seq as id, a.ten as ten,b.donvikinhdoanh as dvkd, c.ten as kenh,a.tungay, a.trangthai as trangthai, d.ten as nguoitao, a.ngaytao as ngaytao, a.ngaysua as ngaysua,e.ten as nguoisua,   c.pk_seq as kenhId "
					+ "from banggiamuanpp a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e"
				+" where a.dvkd_fk=b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq";
			System.out.println("\nFirst Query: "+query);
		}		
		
		this.bglist = super.createSplittingData(super.getItems(), super.getSplittings(), " id desc ", query);
		
		createDvkdRS();
		System.out.println("Live 1");
		createKenhRS();
		System.out.println("Live 2");
	}
	
	public void closeDB(){
		try{
			Statement st;
			if(dvkdIds != null) {
				st = dvkdIds.getStatement();
				st.close();
				dvkdIds.close();
			}
			
			if (kenhIds != null){
				st = kenhIds.getStatement();
				st.close();				
				kenhIds.close();
			}
			
			if (bglist != null){
				st = bglist.getStatement();
				st.close();				
				bglist.close();
			}
			
/*			int size = this.bgmuanpplist.size();
			int m = 0;
			IBanggiamuanpp bgmuanpp;
			while (m < size){				
				bgmuanpp = (IBanggiamuanpp)bgmuanpplist.get(m);
				bgmuanpp.closeDB();
				m++;
			}*/
			
			this.db.shutDown();
		}catch(Exception e){}

	}
	
	public void setDenngay(String denngay) 
	{
		this.denngay = denngay;
	}
	
	public String getTungay() 
	{
		return tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay() 
	{
		return denngay;
	}
	
	public String getMsg() 
	{
		return msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}
	
}

