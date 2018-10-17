package geso.traphaco.distributor.beans.phanbogiaonhan.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhan;
import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhanList;
import geso.traphaco.distributor.db.sql.dbutils;
import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class PhanbogiaonhanList implements IPhanbogiaonhanList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; 
	
	String tennv;
	String diengiai;
	String trangthai;	
	ResultSet nvgnRs;
	String nvgnId;
	String ngay;
		
	String nppId;
	String nppTen;
	String sitecode;
	String congtyId;
	List<IPhanbogiaonhan> pbgnList;
	ResultSet pbgnRs;
	
	dbutils db;
	
	public PhanbogiaonhanList(String[] param)
	{
		this.tennv = param[0];
		this.trangthai = param[1];
		this.nvgnId = param[2];
		this.ngay = param[3];
		db = new dbutils();
	}
	
	public PhanbogiaonhanList()
	{
		this.tennv = "";
		this.trangthai = "";
		this.nvgnId = "";
		this.diengiai="";
		this.ngay = "";
		db = new dbutils();
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}
	
	public String getNvgnId() 
	{
		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;
	}
	
	public String getNgay() 
	{
		return this.ngay;
	}

	public void setNgay(String ngay) 
	{
		this.ngay = ngay;
	}
	
	public String getTennv() 
	{	
		return this.tennv;
	}
	
	public void setTennv(String tennv) 
	{
		this.tennv = tennv;		
	}

	public String getTrangthai()
	{	
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai) 
	{		
		this.trangthai = trangthai;
	}
	
	public ResultSet getNvgnRs() 
	{		
		return this.nvgnRs;
	}
	
	public void setNvgnRs(ResultSet nvgnRs) 
	{		
		this.nvgnRs = nvgnRs;
	}
	
	public String getDiengiai() 
	{	
		return this.diengiai;
	}
	
	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;		
	}
	
	public List<IPhanbogiaonhan> getPbgnList() 
	{	
		return this.pbgnList;
	}
	
	public void setPbgnList(List<IPhanbogiaonhan> pbgnList)
	{
		this.pbgnList = pbgnList;		
	}
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	public void init(String search) 
	{		
		this.getNppInfo();
		String query = "";	
		if (search.length() == 0)
		{
			query =  "select a.pk_seq, a.diengiai, a.ngay, a.trangthai, a.nhanviengn, a.nhanviengn_moi, d.ten as tennv, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua ";
			query += "from phanbogiaonhan a inner join nhanviengiaonhan d on a.nhanviengn = d.pk_seq inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where 1=1 "
					+ " and a.npp_fk = '" + this.nppId + "' ";
		}
		else
		{
			query = search;
		}		
		
		Utility util = new Utility();
		
		int[] quyen = util.GetquyenNew("PhanbogiaonhanSvl", "", this.userId );
		
		//PHAN QUYEN THEO NHAN VIEN
		if( quyen[ Utility.HIENTHIALL ] == 0 )
			query += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "d", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		System.out.println("INT PBGN: " + query);
		this.pbgnRs=db.get(query);
		this.createNvgnRs();
	}

	private void createNvgnRs() 
	{
		Utility util = new Utility();
		
		String query = "select pk_seq, ten from nhanviengiaonhan a where trangthai=1";
		
		int[] quyen = util.GetquyenNew("PhanbogiaonhanSvl", "", this.userId );
		
		//PHAN QUYEN THEO NHAN VIEN
		if( quyen[ Utility.HIENTHIALL ] == 0 )
			query += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.nvgnRs = db.get(query);
	}

	private void createPbgnBeanList(String query) 
	{
		ResultSet rs =  db.get(query);
		List<IPhanbogiaonhan> pbgnList = new ArrayList<IPhanbogiaonhan>();
			
		if(rs != null)
		{
			String[] param = new String[11];
			IPhanbogiaonhan pbgnBean = null;			
			try 
			{
				while(rs.next())
				{
					param[0]= rs.getString("pk_seq");
					param[1]= rs.getString("diengiai");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("nhanviengn");
					param[4]= rs.getString("ngaytao");
					param[5]= rs.getString("nguoitao");
					param[6]= rs.getString("ngaysua");
					param[7]= rs.getString("nguoisua");
					param[8]= rs.getString("ngay");
					param[9]= rs.getString("nhanviengn_moi");
					param[10]= rs.getString("tennv");
					pbgnBean = new Phanbogiaonhan(param);
					pbgnList.add(pbgnBean);
				}
				rs.close();
			}
			catch(Exception e) {}
			finally{try {
				if(rs != null)
					rs.close();
			} catch (Exception e2) {
				
			}}
		}
		this.pbgnList = pbgnList;
	}

	public void DBclose() 
	{
			try 
			{
				if(this.db != null)
					this.db.shutDown();
				
				if(!(nvgnRs == null))
					nvgnRs.close();
			} 
			catch(Exception e) {}
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}

	public ResultSet getPbgnRs() {
		return pbgnRs;
	}

	public void setPbgnRs(ResultSet pbgnRs) {
		this.pbgnRs = pbgnRs;
	}
	
}
