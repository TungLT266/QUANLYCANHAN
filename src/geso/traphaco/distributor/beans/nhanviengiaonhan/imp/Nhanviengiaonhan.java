package geso.traphaco.distributor.beans.nhanviengiaonhan.imp;

import geso.traphaco.distributor.beans.nhanviengiaonhan.INhanviengiaonhan;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Nhanviengiaonhan implements INhanviengiaonhan, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String tennv;
	String trangthai;
	String diachi;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String dienthoai;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	ResultSet ddkdlist;
	String ddkdId;
	ResultSet tbhlist;
	String tbhId;
	String[] tbhIds;
	String[] ddkdIds;
	
	ResultSet khlist;
	ResultSet khSelectedList; //khach hang cua nhan vien giao nhan
	String[] khIds;
	
	dbutils db;
	
	public Nhanviengiaonhan(String[] param)
	{
		this.id = param[0];
		this.tennv = param[1];
		this.trangthai = param[2];
		this.diachi = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.dienthoai = param[8];
		this.ddkdId = "";
		this.tbhId = "";
		this.msg = "";
		this.ttId="";
		this.qhId="";
		db = new dbutils();
	}
	
	public Nhanviengiaonhan(String id)
	{
		this.id = id;
		this.tennv = "";
		this.trangthai = "2";
		this.diachi = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.dienthoai = "";
		this.ddkdId = "";
		this.tbhId = "";
		this.msg = "";
		this.ttId="";
		this.qhId="";
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
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
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
	
	public String getDiachi()
	{
		return this.diachi;
	}
	
	public void setDiachi(String diachi)
	{
		this.diachi = diachi;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getDienthoai()
	{
		return this.dienthoai;
	}
	
	public void setDienthoai(String dienthoai)
	{
		this.dienthoai = dienthoai;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
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

	public ResultSet getDdkdList() 
	{
		return this.ddkdlist;
	}
	
	public void setDdkdList(ResultSet ddkdlist) 
	{
		this.ddkdlist = ddkdlist;
	}
	
	public String getDdkdId() 
	{		
		return this.ddkdId;
	}
	
	public void setDdkdId(String ddkdId) 
	{
		this.ddkdId = ddkdId;		
	}
	
	public ResultSet getTuyenbanhang() 
	{		
		return this.tbhlist;
	}
	
	public void setTuyenhang(ResultSet tuyenbanhang) 
	{
		this.tbhlist = tuyenbanhang;		
	}
	
	public Hashtable<Integer, String> getTbhIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.tbhIds != null){
			int size = (this.tbhIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.tbhIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public void setTbhIds(String[] tbhIds)
	{
		this.tbhIds = tbhIds;
	}

	public void setDdkdIds(String[] ddkdIds)
	{
		this.ddkdIds = ddkdIds;
	}

	public Hashtable<Integer, String> getDdkdIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.ddkdIds != null){
			int size = (this.ddkdIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.ddkdIds[m]) ;
				//System.out.println("Danh Sach DDKD : "+ this.ddkdIds[m] );
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}

	public ResultSet getKhList() 
	{		
		return this.khlist;
	}
	
	public void setKhList(ResultSet khlist) 
	{
		this.khlist = khlist;		
	}

	public Hashtable<Integer, String> getKhIds() 
	{		
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.khIds != null){
			int size = (this.khIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.khIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setKhIds(String[] khIds) 
	{
		this.khIds = khIds;
	}
	
	private void getNppInfo()
	{	
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try
		{
			if (!(rs == null)){
				rs.next();
				this.nppId = rs.getString("pk_seq");
				this.nppTen = rs.getString("ten");
				this.sitecode = rs.getString("sitecode");
				
			}else
			{
				this.nppId = "";
				this.nppTen = "";
				this.sitecode = "";	
			}	
		}
		catch(Exception e){}
		*/
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	
	public boolean CreateNvgn(String[] khIds)
	{	
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		ResultSet rsNvgn = null;
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			String query = "insert into nhanviengiaonhan(ten, trangthai, diachi, ngaytao, ngaysua, nguoitao, nguoisua, dienthoai, npp_fk) " ;
			query = query + "values(N'" + this.tennv + "','1',N'" + this.diachi + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.dienthoai + "','" + this.nppId + "')";
			System.out.print(query);
			if(!db.update(query)){
				db.update("rollback");
				this.msg = "Khong the tao moi 'NhanVienGiaoNhan' , " + query;
				return false; 
			}
			
			if(khIds != null)
			{
				query = "select IDENT_CURRENT('nhanviengiaonhan') as nvgnId";
				
				rsNvgn = this.db.get(query);					
				rsNvgn.next();
				this.id = rsNvgn.getString("nvgnId");
				rsNvgn.close();
				
				for(int m = 0; m < khIds.length; m++)
				{
					
					String sql = "insert into nvgn_kh(nvgn_fk, khachhang_fk) values('" + this.id + "', '" + khIds[m] + "')";
					System.out.print(sql);
					if(!db.update(sql))
					{
						db.update("rollback");
						this.msg = "Khong the cap nhat 'nvgn_kh' cua NhanVienGiaoHang, " + sql;
						return false; 
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		finally{try {
			if(rsNvgn != null)
				rsNvgn.close();
		} catch (Exception e2) {
			// TODO: handle exception
		}}
		return true;	
	}
	
	public boolean UpdateNvgn(String[] khIds)
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "update NhanVienGiaoNhan set ten=N'" + this.tennv + "', trangthai='" + this.trangthai + "', diachi=N'" + this.diachi + "', dienthoai='";
			query = query + this.dienthoai + "', nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "' where pk_seq='" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'NhanVienGiaoNhan' , " + query;
				return false; 
			}	
			
			query = "delete nvgn_kh where nvgn_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = query;
				return false;
			}
			
			if(khIds != null)
			{
				for(int m = 0; m < khIds.length; m++)
				{
					String sql = "insert into nvgn_kh(nvgn_fk, khachhang_fk) values('" + this.id + "','" + khIds[m] + "')";
					System.out.println(sql + "\n");
					if(!db.update(sql))
					{
						db.getConnection().rollback();
						this.msg = "Khong the cap nhat 'nvgn_kh', " + sql;
						return false; 
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		return true;
	}
	
	private void createDdkdRs() 
	{
		this.ddkdlist = db.get("select pk_seq as ddkdId, ten as ddkdTen, dienthoai, diachi from daidienkinhdoanh where pk_seq in (select ddkd_fk from daidienkinhdoanh_npp where npp_fk='"+this.nppId+"') and trangthai = '1'");
	}
	
	private void createTbhRs()
	{
		String sql = "";

		if(this.ddkdId.length() > 0)
		{
			//sql = "select tuyenbanhang.pk_seq as tbhId, diengiai as tbhTen,ddkd.ten, ngaylamviec from tuyenbanhang inner join daidienkinhdoanh ddkd on dkkd.pk_seq=tuyenbanhang.ddkd_fk  where npp_fk = '" + this.nppId + "'";	
			sql="select tuyenbanhang.pk_seq as tbhId,tuyenbanhang.diengiai as tbhTen, ddkd.ten, ngaylamviec "+
			" from tuyenbanhang inner join daidienkinhdoanh ddkd on ddkd.pk_seq =tuyenbanhang.ddkd_fk  "+
			" where tuyenbanhang.npp_fk = '" + this.nppId + "' ";
			
			sql = sql + " and tuyenbanhang.ddkd_fk in ("+ this.ddkdId +") ";
				
			this.tbhlist = db.get(sql);			
		}
		else
		{
			this.tbhlist = null;
		}			
		
		System.out.println("Lay tuyen ban hang la: " + sql + "\n");
	}
	
	private void createKhRs()
	{
		String sql = "";
		if(this.tbhId.length() > 0)
		{

			sql = "select distinct a.smartid,a.pk_seq as khId, a.ten as khTen, a.diachi, a.dienthoai from khachhang a, khachhang_tuyenbh b, tuyenbanhang c where a.pk_seq = b.khachhang_fk and b.tbh_fk = c.pk_seq and c.npp_fk='" + this.nppId + "'";			
			sql += " and c.pk_seq in (" + this.tbhId + ")";
			sql = sql + " and a.pk_seq not in (select khachhang_fk from NVGN_KH where nvgn_fk in (select pk_seq from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai = '1'))";
			
			this.khlist = db.get(sql);
	
		}
		else{
			this.khlist = null;
		}
		System.out.println("Khach hang chua phan tuyen: " + sql + "\n");
	}
	
	public void init() 
	{
		this.getNppInfo();
		String query = "select a.pk_seq as nvgnId, a.ten as nvgnTen, a.trangthai, a.diachi, a.npp_fk as nppId, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, a.dienthoai "+
		"	,	STUFF  "+   
		"		(     "+
		"			(    "+
		"	   select DISTINCT TOP 100 PERCENT ' , ' +cast( kh.QUANHUYEN_FK as nvarchar(18) ) "+   		
		"				from KHACHHANG kh inner join NVGN_KH b on b.KHACHHANG_FK=kh.PK_SEQ "+
		"	     where b.NVGN_FK=a.PK_SEQ             "+
		"	  ORDER BY ' , ' +cast( kh.QUANHUYEN_FK as nvarchar(18) ) "+   	
		"			FOR XML PATH('')    "+   
		"			 ), 1, 2, ''     "+
		"		) + ' '  AS qhId,  "+
		"		STUFF      "+ 
		"		(      "+
		"			(   "+
		"	   select DISTINCT TOP 100 PERCENT ' , ' +cast( kh.TINHTHANH_FK as nvarchar(18) )  "+   		
		"				from KHACHHANG kh inner join NVGN_KH b on b.KHACHHANG_FK=kh.PK_SEQ "+
		"	     where b.NVGN_FK=a.PK_SEQ              "+
		"	  ORDER BY ' , ' +cast( kh.TINHTHANH_FK as nvarchar(18) )  "+   	
		"			FOR XML PATH('')     "+   
		"			 ), 1, 2, ''     "+
		"		) + ' '  AS ttId  ";

		query = query + "from nhanviengiaonhan a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where a.npp_fk='" + this.nppId + "'";
		ResultSet rs =  db.get(query + " and a.pk_seq='" + this.id + "'");
		System.out.println(query + " and a.pk_seq='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("nvgnId");
			this.tennv = rs.getString("nvgnTen");
			this.trangthai = rs.getString("trangthai");
			this.diachi = rs.getString("diachi");
			this.dienthoai = rs.getString("dienthoai");			
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			
			
			this.ttId = rs.getString("ttId")==null?"":rs.getString("ttId");
			this.qhId = rs.getString("qhId")==null?"":rs.getString("qhId");
			
			rs.close();
			
			if(this.tbhId == "")
			{
				query = "select distinct c.TBH_FK as tbhId from NHANVIENGIAONHAN a inner join NVGN_KH b on a.PK_SEQ = b.NVGN_FK inner join KHACHHANG_TUYENBH c on b.KHACHHANG_FK = c.KHACHHANG_FK where a.PK_SEQ = '" + this.id + "'";
				
				rs = db.get(query);
				int i = 0;
				while(rs.next())
				{
					this.tbhId += rs.getString("tbhId") + ",";
					i++;
				}
				rs.close();
				System.out.println(query+"[tbhId]"+tbhId);
				if(this.tbhId.length() > 0)
					this.tbhId = this.tbhId.substring(0, this.tbhId.length() - 1);
			}
			
			if(this.tbhId.length() > 0)
			{				
				query = "select DDKD_FK as ddkdId from TUYENBANHANG where PK_SEQ in (" + this.tbhId + ")";
				System.out.println("Query lay ddkd: " + query + "\n");
				rs = this.db.get(query);
				int i = 0;
				while(rs.next())
				{
					//this.ddkdIds[i] = rs.getString("ddkdId");
					this.ddkdId += rs.getString("ddkdId") + ",";
					i++;
				}
				rs.close();
				if(this.ddkdId.length() > 0)
					this.ddkdId = this.ddkdId.substring(0, this.ddkdId.length() - 1);
			}
			
			//khach hang thuoc nhan vien giao nhan nay
			query = "select kh.smartid,kh.pk_seq as khId, kh.ten as khTen, kh.diachi, kh.dienthoai from NVGN_KH a inner join khachhang kh on a.khachhang_fk = kh.pk_seq ";
			query = query + "where a.nvgn_fk = '" + this.id + "'";
			//System.out.println(query);
			this.khSelectedList = this.db.get(query);
			
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
		
		}}
        
		createRS();
		
		
		
	}

	public void createRS() 
	{
		this.getNppInfo();
		this.createDdkdRs();
		this.createTbhRs();
		this.createKhRs();
	String	 query="select * from tinhthanh where vung_fk in (select vung_fk from khuvuc where pk_Seq in (select khuvuc_fk from nhaphanphoi where pk_Seq='"+this.nppId+"')) order by ten ";
			this.ttRs=db.get(query);
			 
		 query="select * from quanhuyen where 1=1 ";
		 if(this.ttId.length()>0)
		 {
			 query+=" and tinhthanh_fk='"+this.ttId+"'";
		 }
		 query+=" order by TEN ";
		 
			this.qhRs=this.db.get(query);
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
		{
			if(this.ddkdlist != null)
				this.ddkdlist.close();
			if(this.khlist != null)
				this.khlist.close();
			if(this.tbhlist != null)
				this.tbhlist.close();
			
			if(this.khSelectedList != null)
				this.khSelectedList.close();
			if(this.db != null)
				this.db.shutDown();
			
		} 
		catch(Exception e) {}
	}
	public ResultSet getKhSelectedList() 
	{
		return this.khSelectedList;
	}

	public void setKhSelectedList(ResultSet khSelectlist)
	{
		this.khSelectedList = khSelectlist;
	}

	public String getTbhId() 
	{
		return this.tbhId;
	}

	public void setTbhId(String ddkdId) 
	{
		this.tbhId = ddkdId;
	}
	
	String ttId,qhId;
	ResultSet ttRs,qhRs;

	public String getTtId()
  {
  	return ttId;
  }

	public void setTtId(String ttId)
  {
  	this.ttId = ttId;
  }

	public String getQhId()
  {
  	return qhId;
  }

	public void setQhId(String qhId)
  {
  	this.qhId = qhId;
  }

	public ResultSet getTtRs()
  {
  	return ttRs;
  }

	public void setTtRs(ResultSet ttRs)
  {
  	this.ttRs = ttRs;
  }

	public ResultSet getQhRs()
  {
  	return qhRs;
  }

	public void setQhRs(ResultSet qhRs)
  {
  	this.qhRs = qhRs;
  }

		
}

