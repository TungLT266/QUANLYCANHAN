package geso.traphaco.center.beans.khoasongay.imp;

import java.sql.ResultSet;
import java.util.Hashtable;

import geso.traphaco.center.beans.khoasongay.IKhoasotudong;
import geso.traphaco.center.db.sql.dbutils;

public class Khoasotudong implements IKhoasotudong 
{
	String userId;
	String msg;
	
	ResultSet vunglist;
	String[] vungIds;
	ResultSet kvlist;
	String[] kvIds;
	ResultSet npplist;
	ResultSet ngaykslist;
	String[] nppIds;
	
	int phut;
	int gio;
	int beforeday;
	String NgayKS;
	
	String[] DanhSachNpp;
	
	
	dbutils db;
	
	public Khoasotudong()
	{
		msg = "";
		NgayKS="";
		db = new dbutils();
		//this.init();
	}
		
	public String getUserId() 
	{	
		return this.userId;
	}
	
	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public String setMsg(String msg) 
	{
		return this.msg = msg;
	}
	
	public ResultSet getVungRs()
	{	
		return this.vunglist;
	}
	
	public void setVungRs(ResultSet vungRs) 
	{		
		this.vunglist = vungRs;
	}
	
	public Hashtable<Integer, String> getVungIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.vungIds != null){
			int size = (this.vungIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.vungIds[m]) ;
				m++;
			}
		}
		else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	
	public void setVungIds(String[] vungIds) 
	{
		this.vungIds = vungIds;	
	}

	
	public ResultSet getKhuvucRs() 
	{
		return this.kvlist;
	}
	
	public void setKhuvucRs(ResultSet kvRs) 
	{
		this.kvlist = kvRs;	
	}
	
	public Hashtable<Integer, String> getKvIds()
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.kvIds != null){
			int size = (this.kvIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.kvIds[m]) ;
				m++;
			}
		}
		else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	
	public void setKvIds(String[] kvIds) 
	{
		this.kvIds = kvIds;
	}

	public ResultSet getNppRs()
	{
		return this.npplist;
	}
	
	public void setNppRs(ResultSet nppRs)
	{
		this.npplist = nppRs;		
	}

	public Hashtable<Integer, String> getNppIds() 
	{	
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nppIds != null){
			int size = (this.nppIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.nppIds[m]) ;
				m++;
			}
		}
		else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setNppIds(String[] nppIds)
	{
		this.nppIds = nppIds;	
	}
	
	public void createVungRs()
	{
		this.vunglist = db.get("select pk_seq, ten, isnull(diengiai, 'Chua nhap dien giai') as diengiai from vung where trangthai = '1'");
	}
	
	public void createKvRs()
	{
		String query = "select pk_seq, ten, isnull(diengiai, 'Chua nhap dien giai') as diengiai from khuvuc where trangthai = '1'";
		if(this.vungIds != null)
		{
			String vungId = "";
			for(int i = 0; i < this.vungIds.length; i++)
				vungId += this.vungIds[i] + ",";
			if(vungId.length() > 0)
			{
				vungId = vungId.substring(0, vungId.length() - 1);
				query = query + " and vung_fk in(" + vungId+ ")";
			}	
		}
		this.kvlist = db.get(query);
	}
	
	public void createNppRs()
	{
		String query = "select pk_seq, ten, isnull(diachi, 'Chua nhap dia chi') as diachi from nhaphanphoi where trangthai = '1' and priandsecond='0'";
		if(this.kvIds != null)
		{
			String kvId = "";
			for(int i = 0; i < this.kvIds.length; i++)
				kvId += this.kvIds[i] + ",";
			if(kvId.length() > 0)
			{
				kvId = kvId.substring(0, kvId.length() - 1);
				query = query + " and khuvuc_fk in (" + kvId + ")";
			}
		}
		query=query + " order by nhaphanphoi.khuvuc_fk ";
		System.out.println("Cau Lenh Cap Nhat: "+query);
		this.npplist = db.get(query);
	}

	public void init()
	{
		this.createRs();
		this.createVungIds();
		this.createKhuvucIds();
		this.createNppIds();
		
		String sql="select gio,phut,daybefore from  THIETLAPKHOASO ";
		ResultSet rs=db.get(sql);
		try{
			if(rs!=null){
			if(rs.next()){
				this.phut=rs.getInt("phut");
				this.gio=rs.getInt("gio");
				this.beforeday=rs.getInt("daybefore");
				
			}
			rs.close();
		}
		}catch(Exception er){
			
		}
	}
	
	private void createVungIds()
	{
		ResultSet rs = db.get("select pk_seq from vung where trangthai = '1'");
		if(rs != null)
		{
			try 
			{
				String str = "";
				while(rs.next())
				{
					str += rs.getString("pk_seq") + ",";
				}
				if(str.length() > 0)
				{
					str = str.substring(0, str.length() - 1); //cat dau , cuoi cung
					this.vungIds = str.split(",");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
	}
	
	private void createKhuvucIds()
	{
		ResultSet rs = db.get("select pk_seq from khuvuc where trangthai = '1'");
		if(rs != null)
		{
			try 
			{
				String str = "";
				while(rs.next())
				{
					str += rs.getString("pk_seq") + ",";
				}
				if(str.length() > 0)
				{
					str = str.substring(0, str.length() - 1); //cat dau , cuoi cung
					this.kvIds = str.split(",");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
	}
	
	private void createNppIds()
	{
		ResultSet rs = db.get("select pk_seq from nhaphanphoi where khoatudong = '0'");
		if(rs != null)
		{
			try 
			{
				String str = "";
				while(rs.next())
				{
					str += rs.getString("pk_seq") + ",";
				}
				if(str.length() > 0)
				{
					str = str.substring(0, str.length() - 1); //cat dau , cuoi cung
					this.nppIds = str.split(",");
				}
				rs.close();
			} 
			catch(Exception e) {}
		}
	}
	
	public void createRs() 
	{
		this.createVungRs();			
		this.createKvRs();
		this.createNppRs();
	}

	public boolean updateKhoaso() 
	{
		
		
		
		Hashtable<String, String> htbnpp=this.getNhapPPDuocChon();
		
		dbutils db = new dbutils();
		
		if(this.DanhSachNpp != null)
		{
			try 
			{
				
				if(gio<0 || gio >24){
					this.msg="Vui lòng chọn lại giờ";
					return false;
				}
				if(phut<0 || phut >60){
					this.msg="Vui lòng chọn lại phut";
					return false;
				}
				if(beforeday<0|| beforeday >10){
					this.msg="Vui lòng chọn lại thời gian khóa sổ cách ngày hiện tại ";
					return false;
				}
				
				db.getConnection().setAutoCommit(false);
				
				
				String	sql="Update THIETLAPKHOASO set gio="+this.gio+",phut="+this.phut+", DAYBEFORE="+this.beforeday;
					if(!db.update(sql))
					{
						this.setMsg("Vui lòng kiểm tra lại .Lỗi: "+ sql );
						db.update("rollback");
						return false;
					}
				
				
				
				for(int i =0; i < this.DanhSachNpp.length; i++)
				{
					if(htbnpp.contains(DanhSachNpp[i].trim())){
						sql="update nhaphanphoi set khoatudong = 0 where pk_seq = '" + this.DanhSachNpp[i] + "'";
					}else{
					
						sql="update nhaphanphoi set khoatudong = 1 where pk_seq = '" + this.DanhSachNpp[i] + "'";
					}
					System.out.println("Cau Lenh Cap Nhat : "+sql);
					if(!db.update(sql))
					{
						this.setMsg("Khong the cap nhat trang thai khoa tu dong " + this.DanhSachNpp[i]);
						db.update("rollback");
						return false;
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} 
			catch(Exception e) {
				System.out.println("Lỗi xảy ra trong quá trình upload : "+e.toString());
				db.update("rollback");
				return false;
			}
		}
		return true;
	}

	private Hashtable<String, String> getNhapPPDuocChon() 
	{		
		Hashtable<String, String> htb=new Hashtable<String, String>();
		if(this.nppIds!=null){
			for(int i =0; i < this.nppIds.length; i++)
			{
			  htb.put(this.nppIds[i].trim(),this.nppIds[i].trim());
			}
		}
		return htb;
	}

	public void DBclose() {
		try{
		if(vunglist!=null)vunglist.close();
		if(kvlist!=null) kvlist.close();
		if(npplist!=null) npplist.close();		
		getVungIds().clear();
		vungIds = null;
		kvIds = null;
		if(db!=null) db.shutDown();
	}catch (Exception e) {System.out.println("Loi :"+e.toString());}
		}

	@Override
	public void setDanhSachNhaPP(String[] danhsach) 
	{		
		this.DanhSachNpp=danhsach;
	}

	@Override
	public String[] getDanhSachNhaPP() 
	{		
		return this.DanhSachNpp;
	}

	@Override
	public void SetPhut(int _phut)
	{		
		this.phut=_phut;
	}

	@Override
	public int getPhut() {
		
		return this.phut;
	}

	@Override
	public void SetGio(int _gio) {
		
		this.gio=_gio;
	}

	@Override
	public int getGio()
	{	
		return this.gio;
	}

	@Override
	public void SetBeforeDay(int BeforeDay) 
	{
		this.beforeday=BeforeDay;
	}

	@Override
	public int getBeforeDay() 
	{
		return this.beforeday;
	}

	@Override
	public void SetNgayKhoaSo(String _NgayKs) 
	{
		this.NgayKS=_NgayKs;
	}

	@Override
	public String GetNgayKhoaSo() 
	{
		return this.NgayKS;
	}

	@Override
	public ResultSet getRsListKs() 
	{
		this.ngaykslist=db.get(" select pk_seq,ngaykhongks,datename(dw,ngaykhongks)  as thu  from NGAYKHONGKS order by ngaykhongks");
		return this.ngaykslist;
	}
}
