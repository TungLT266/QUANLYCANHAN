package geso.traphaco.erp.beans.lapngansach.imp;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.lapngansach.ILNSBanggiaban;
import geso.traphaco.erp.beans.lapngansach.ILNSBanggiabanList;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class LNSBanggiabanList implements ILNSBanggiabanList
{
	private static final long serialVersionUID = -927977546783610214L;
	private String ctyId;
	private String ctyTen;
	
	private String userId;
	private String userTen;
	private String ten;
	private ResultSet dvkdIds;
	private String dvkdId;
	private ResultSet kenhIds;
	private String kenhId;	
	private String trangthai;
	private List<ILNSBanggiaban> bgbanlist;
	private ResultSet bglist;
	private dbutils db;
	
	//Constructor
	public LNSBanggiabanList(String[] param)
	{
		this.ten = param[0];		
		this.dvkdId = param[1];
		this.kenhId = param[2];
		this.trangthai = param[3];
		this.db = new dbutils();
	}
	
	public LNSBanggiabanList()
	{
		this.ten = "";		
		this.dvkdId = "";
		this.kenhId = "";
		this.trangthai = "";
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
		this.userTen = "Nobody";
		ResultSet rs = this.db.get("select ten from nhanvien where pk_seq = "+ this.userId);
		System.out.println("select ten from nhanvien where pk_seq ='" + this.userId + "'");
		try{
			rs.next();
			this.userTen = rs.getString("ten");
			rs.close();
		}catch(Exception er){}
	}

	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
		
	}

	public String getCtyId()
	{
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public String getCtyTen()
	{
		return this.ctyTen;
	}
	
	public void setCtyTen(String ctyTen)
	{
		this.ctyTen = ctyTen;
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

	public List<ILNSBanggiaban> getBgbanList() 
	{
		return this.bgbanlist;
	}

	public void setBgbanList(List<ILNSBanggiaban> bgbanlist) 
	{
		this.bgbanlist = bgbanlist;
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
		
		/*this.dvkdIds  =  this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId from donvikinhdoanh a,nhacungcap_dvkd b " +
									 "where a.pk_seq = b.DVKD_fk and b.checked ='1' and a.trangthai ='1' order by a.donvikinhdoanh");*/
		this.dvkdIds = this.db.get("select distinct a.donvikinhdoanh as dvkd, a.pk_seq as dvkdId "+
									"	from donvikinhdoanh a "+
									"	where a.trangthai ='1' "+
									"	order by a.donvikinhdoanh");
	}

	private void createKenhRS(){  				
		
		this.kenhIds  =  this.db.get("select diengiai as kenh, pk_seq as kenhId from kenhbanhang where trangthai='1'");
	}

	public void createBanggiabanBeanList(String query){  	
		
		ResultSet rs =  this.db.get(query);
		List<ILNSBanggiaban> bgbanlist = new ArrayList<ILNSBanggiaban>();
		if (rs != null){		
			ILNSBanggiaban bgbanBean;
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
					
					bgbanBean = new LNSBanggiaban(param);
					bgbanlist.add(bgbanBean);															
				}
				rs.close();
			}catch(java.sql.SQLException e){
		
			}
			 
		}
		
		this.bgbanlist = bgbanlist;
	}

	public void init(String search){
		String query;
		
		if (search.length()>0){
			query = search;
		}else{
			query = "select a.pk_seq as id, a.ten as ten, a.trangthai as trangthai, a.tinhtrang, d.ten as nguoitao, a.ngaytao as ngaytao, " +
					"e.ten as nguoisua, a.ngaysua as ngaysua, b.donvikinhdoanh as dvkd, c.ten as kenh, c.pk_seq as kenhId " +
					"from ERP_LNSBANGGIABAN a, donvikinhdoanh b, kenhbanhang c, nhanvien d, nhanvien e " +
					"where a.dvkd_fk=b.pk_seq and a.kenh_fk = c.pk_seq and a.nguoitao = d.pk_seq and a.nguoisua = e.pk_seq " +
					"and a.congty_fk = " + this.ctyId + " and c.trangthai = 1 and a.trangthai = 1 ";
		}

		System.out.println("1.Khoi tao bang gia: " + query);
		this.bglist = this.db.get(query);
		
		createDvkdRS();
		createKenhRS();
	}
	
	public void closeDB(){
		try{
			if (this.bgbanlist != null)
				this.bgbanlist.clear(); 
			if(dvkdIds != null) {
				dvkdIds.close();
			}
			
			if (kenhIds != null){
				kenhIds.close();
			}
			
			if (bglist != null){
				bglist.close();
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}finally{
			this.db.shutDown();
		}

	}
	
}

