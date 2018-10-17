package geso.traphaco.center.beans.Router.imp;

import java.sql.ResultSet;

import geso.traphaco.center.beans.Router.IDRouter;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

public class Router implements IDRouter{

	String nppId;
	String ddkdId;
	String tuyenId;

	ResultSet tuyen;
	ResultSet npp;
	ResultSet ddkd;
	dbutils db;
	ResultSet danhsach;
	ResultSet kenh;
	String kenhId;
	private String khuvucId;
	private ResultSet khuvuc;
	private String status;
	public Router()
	{
		this.khuvucId="";
		this.status = "";
		this.nppId ="";
		this.ddkdId ="";
		this.tuyenId ="";
		this.kenhId ="";
		db = new dbutils();
	}
	
	public void setKenhId(String kenhId){
		this.kenhId = kenhId;
	}
	public void setnppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public String getnppId() {
		
		return this.nppId;
	}

	
	public void setddkdId(String ddkdId) {
		this.ddkdId = ddkdId;
		
	}

	
	public String getddkdId() {
		
		return this.ddkdId;
	}

	
	public ResultSet getnpp() {
		
		return this.npp;
	}

	
	public ResultSet getddkd() {
		
		return this.ddkd;
	}
	Utility Ult = new Utility();
	
	public void init() 
	{
		
		try
		{

			String			sql = "select phanloai,LOAI from nhanvien where pk_seq=" + this.userId;
			ResultSet rs = this.db.get(sql);
			if (rs != null)
			{
				if (rs.next())
				{
					this.phanloai = rs.getString("phanloai");
					loaiNv= rs.getString("LOAI")==null?"":rs.getString("LOAI");

					if (rs.getString("phanloai").equals("1")||( this.phanloai.equals("2")   && loaiNv.equals("3")   )  )
					{
						this.nppId = Ult.getIdNhapp(this.userId);
					}
					rs.close();
				}
			}
			//System.out.println("sql : " + sql);
		} catch (Exception er)
		{

		}
		String sql = "select * from nhaphanphoi where trangthai='"+this.status+"' and isKhachHang = 0 ";
		
		 if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
			{
				sql+= " and pk_Seq in " + Ult.quyen_npp(userId)+"";
			}
		
		System.out.println("npp init: "+sql);
		
		
		
		String kenh = "select* from kenhbanhang";
		String khuvuc = "select * from khuvuc order by ten";
		this.kenh = db.get(kenh);
		this.khuvuc = db.get(khuvuc);
      this.npp = db.get(sql);
        if(this.nppId.length()>0)
        sql ="select * from daidienkinhdoanh where pk_seq in (select ddkd_Fk from DaiDienKinhDoanh_Npp where npp_fk='"+nppId+"') ";
        else
        	sql ="select * from daidienkinhdoanh where 1=1 ";
      	if(this.phanloai.equals("2")&& !loaiNv.equals("3"))
  			{
      		sql+= " and pk_Seq in " + Ult.Quyen_Ddkd(userId)+"";
  			}
  			
        
        
        
        this.ddkd = db.get(sql);
        if(this.ddkdId.length()>0)
        	sql ="select distinct ngaylamviec,ngayid from tuyenbanhang where ddkd_fk ='"+ this.ddkdId +"' order by ngayid asc";
        else
        	sql ="select distinct ngaylamviec,ngayid from tuyenbanhang order by ngayid asc ";
        this.tuyen = db.get(sql);
       String st="";
       String tableKH = "";
       String tableDH = "";
        if(this.nppId.length()>0)
	    {
        	st = st + " tbh.npp_fk ='"+ this.nppId +"'";
        	tableKH = tableKH + "where npp_fk = '"+this.nppId+"'";
        	tableDH = tableDH + "where npp_fk = '"+this.nppId+"'";
	    	
	    }
       
        
	    if(this.tuyenId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ngaylamviec ='"+ this.tuyenId +"' ";
	    	else
	    		st ="tbh.ngaylamviec ='"+ this.tuyenId +"' ";
	    }
	    if(this.ddkdId.length()>0)
	    {
	    	if(st.length()>0)
	    		st = st + " and tbh.ddkd_fk ='"+ this.ddkdId +"' ";
	    	else
	    		st = st + " tbh.ddkd_fk ='"+ this.ddkdId +"' ";
	    	
	    	if (tableDH.length() > 0)
	    		tableDH = tableDH + " and ddkd_fk = '"+this.ddkdId+"' ";
	    	else 
	    		tableDH = tableDH + " where ddkd_fk = '"+this.ddkdId+"' ";
	    }
	    
	    if (this.kenhId.length() > 0)
	    {
	    	if (tableDH.length() > 0)
	    	{
	    		tableDH = tableDH + " and kbh_fk = '"+this.kenhId+"' ";
	    	}
	    	else
	    	{
	    		tableDH = tableDH + " where kbh_fk = '"+this.kenhId+"' ";
	    	}
	    	if (tableKH.length()>0)
	    	{
	    		tableKH = tableKH + " and kbh_fk = '"+this.kenhId+"'";
	    	}
	    	else
	    	{
	    		tableKH = tableKH + " where kbh_fk = '"+this.kenhId+"'";
	    	}
	    }
	    //loc bang khachhang
	    
	    
	    
	    if(st.length()>0)
	    {
	    	st = " where " + st;
	    //khoi tao ket noi csdl
	    	sql  = "select tbh.ngaylamviec,kh.pk_seq as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume,lch.diengiai as Outlet_Type,"+
			" vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency"+
			" from (select * from khachhang "+tableKH+") kh"+
			" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq"+
			" left join loaicuahang lch on lch.pk_seq = kh.lch_fk"+
			" left join vitricuahang vt on vt.pk_seq = kh.vtch_fk"+
			" left join hangcuahang hch on hch.pk_seq = kh.hch_fk"+
			" left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq"+
			" left join (select a.khachhang_fk,cast(sum(a.tonggiatri)/3 as int) as tonggiatri from (select * from donhang "+tableDH+") a where a.ngaynhap >'2011-08-01' and a.ngaynhap < '2011-12-15' group by khachhang_fk) as ds"+
			" on ds.khachhang_fk = kh.pk_seq"+
			" left join tuyenbanhang tbh on tbh.pk_seq = kh_tuyen.tbh_fk "+ st +
			" order by tbh.ngaylamviec desc";
	    	System.out.println("Lay Du Lieu :"+sql);
			//this.danhsach = db.get(sql);
			
	    }
	    else
	    {
	    	sql  = "select tbh.ngaylamviec,kh.pk_seq as Customer_Key,kh.ten as Customer_Name,kh.diachi as Address,qh.ten as province,case when ds.tonggiatri is null then 0 else ds.tonggiatri end as Average_Volume,lch.diengiai as Outlet_Type,"+
			" vt.vitri as Outlet_Location,hch.hang as Outlet_Class,kh_tuyen.tanso as Frequency"+
			" from (select * from khachhang "+tableKH+") kh"+
			" left join quanhuyen qh on kh.quanhuyen_fk = qh.pk_seq"+
			" left join loaicuahang lch on lch.pk_seq = kh.lch_fk"+
			" left join vitricuahang vt on vt.pk_seq = kh.vtch_fk"+
			" left join hangcuahang hch on hch.pk_seq = kh.hch_fk"+
			" left join KHACHHANG_TUYENBH kh_tuyen on kh_tuyen.khachhang_fk = kh.pk_seq"+
			" left join (select a.khachhang_fk,cast(sum(a.tonggiatri)/3 as int) as tonggiatri from (select * from donhang "+tableDH+") a where a.ngaynhap >'2011-08-01' and a.ngaynhap < '2011-12-15' group by khachhang_fk) as ds"+
			" on ds.khachhang_fk = kh.pk_seq"+
			" left join tuyenbanhang tbh on tbh.pk_seq = kh_tuyen.tbh_fk "+ st +
			" order by tbh.ngaylamviec desc";
	    	System.out.println("Lay Du Lieu :"+sql);
			//this.danhsach = db.get(sql);
	    }
	   


		
	}


	
	public void settuyenId(String tuyenId) {
		
		this.tuyenId = tuyenId;
	}


	
	public String gettuyenId() {
		
		return this.tuyenId;
	}


	
	public ResultSet getTuyen() {
		
		return this.tuyen;
	}


	
	public ResultSet getdanhsach() {
		
		return this.danhsach;
	}


	
	public ResultSet getKenh() {
		
		return this.kenh;
	}


	
	public String getkenhId() {
		
		return this.kenhId;
	}

	@Override
	public void DBclose() {
		// TODO Auto-generated method stub
		try {
			if(this.db != null)
				this.db.shutDown();
			if(this.danhsach != null)
				this.danhsach.close();
			if(this.ddkd != null)
				this.ddkd.close();
			if(this.kenh != null)
				this.kenh.close();
			if(this.npp != null)
				this.npp.close();
			if(this.tuyen != null)
				this.tuyen.close();
			
				
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	@Override
	public void setkhuvucId(String khuvucId) {
		// TODO Auto-generated method stub
		this.khuvucId = khuvucId;
		
	}

	@Override
	public String getkhuvucId() {
		// TODO Auto-generated method stub
		return this.khuvucId;
	}

	@Override
	public ResultSet getkhuvuc() {
		// TODO Auto-generated method stub
		return this.khuvuc;
	}

	@Override
	public void createNPP() {
		// TODO Auto-generated method stub
		this.khuvuc = db.get("select * from khuvuc order by ten");
		String sql = "select * from nhaphanphoi where trangthai='"+this.status+"' order by ten";
		if(this.khuvucId.trim().length() > 0)
			sql = "select * from nhaphanphoi where khuvuc_fk = '"+this.khuvucId+"' and trangthai='"+this.status+"' and isKhachhang = '0' order by ten";
		this.npp = db.get(sql);
		
		//System.out.println("nhapp: select * from nhaphanphoi where khuvuc_fk = '"+this.khuvucId+"' order by ten");
	}

	@Override
	public String getStatus() {
		// TODO Auto-generated method stub
		return this.status;
	}

	@Override
	public void setStatus(String status) {
		// TODO Auto-generated method stub
		this.status = status;
	}

	
	String loaiNv,phanloai,userId;
	public String getLoaiNv()
  {
  	return loaiNv;
  }

	public void setLoaiNv(String loaiNv)
  {
  	this.loaiNv = loaiNv;
  }

	public String getPhanloai()
  {
  	return phanloai;
  }

	public void setPhanloai(String phanloai)
  {
  	this.phanloai = phanloai;
  }

	public String getUserId()
  {
  	return userId;
  }

	public void setUserId(String userId)
  {
  	this.userId = userId;
  }
	
	
	
}
