package geso.traphaco.distributor.beans.tdvdoanhso.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.distributor.beans.khachhang.IKhachhangList;
import geso.traphaco.distributor.beans.tdvdoanhso.ITdvdoanhsoList;
import geso.traphaco.distributor.db.sql.*;

import java.io.Serializable;
import java.sql.ResultSet;

public class TdvdoanhsoList extends Phan_Trang implements ITdvdoanhsoList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String ten;
	String trangthai ="";
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String tungay;
	String denngay;
	String loaiKH;
	String hopdong;
	String query;
	String thang = "";
	String nam = "2015";
	
	//List<IKhachhang> khlist;
	ResultSet khlist;
	
	ResultSet hangcuahang;
	String hchId;
	
	ResultSet kenhbanhang;
	String kbhId;
	
	ResultSet vitricuahang;
	String vtchId;
	
	ResultSet loaicuahang;
	String lchId;
	
	ResultSet nhomcuahang;
	String nchId;
	
	dbutils db;
	
	int currentPages;
	int[] listPages;
	
	String diachi, maFAST;
	
	public TdvdoanhsoList()
	{
		this.ten = "";
		this.trangthai = "";
		this.hchId = "";
		this.kbhId = "";
		this.vtchId = "";
		this.lchId = "";
		this.nchId = "";
		this.msg = "";
		this.diadiemId="";
		this.xuatkhau ="";
		
		this.diachi="";
		this.maFAST = "";
		
		this.tungay="";
		this.denngay="";
		this.loaiKH="";
		this.hopdong="";
		
		currentPages = 1;
		
		this.ddkdId="";
		this.tbhId="";
		
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10};
		
		this.db = new dbutils();
		
	}
	
		
	public String getDiachi() {
		return diachi;
	}

	public void setDiachi(String diachi) {
		this.diachi = diachi;
	}


	public String getMaFAST() {
		return maFAST;
	}


	public void setMaFAST(String maFAST) {
		this.maFAST = maFAST;
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

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public ResultSet getHangcuahang() 
	{
		return this.hangcuahang;
	}

	public void setHangcuahang(ResultSet hangcuahang)
	{
		this.hangcuahang = hangcuahang;
	}

	public ResultSet getKenhbanhang() 
	{
		return this.kenhbanhang;
	}

	public void setKenhbanhang(ResultSet kenhbanhang)
	{
		this.kenhbanhang = kenhbanhang;
	}

	public ResultSet getVitricuahang() 
	{
		return this.vitricuahang;
	}

	public void setVitricuahang(ResultSet vitricuahang) 
	{
		this.vitricuahang = vitricuahang;
	}

	public ResultSet getLoaicuahang() 
	{
		return this.loaicuahang;
	}

	public void setLoaicuahang(ResultSet loaicuahang) 
	{
		this.loaicuahang =  loaicuahang;
	}

	public ResultSet getNhomcuahang() 
	{
		return this.nhomcuahang;
	}

	public void setNhomcuahang(ResultSet nhomcuahang) 
	{
		this.nhomcuahang = nhomcuahang;
	}

	public String getHchId() 
	{
		return this.hchId;
	}

	public void setHchId(String hchId)
	{
		this.hchId = hchId;
	}

	public String getKbhId() 
	{
		return this.kbhId;
	}

	public void setKbhId(String kbhId) 
	{
		this.kbhId = kbhId;
	}

	public String getVtchId() 
	{
		return this.vtchId;
	}

	public void setVtId(String vtchId) 
	{
		this.vtchId = vtchId;
	}

	public String getLchId()
	{
		return this.lchId;
	}

	public void setLchId(String lchId) 
	{
		this.lchId = lchId;
	}

	public String getNchId() 
	{
		return this.nchId;
	}

	public void setNchId(String nchId) 
	{
		this.nchId = nchId;
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
	
	public void createHchRS()
	{
		this.hangcuahang =  this.db.get("select hang as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
	}
	
	public void createKbhRS()
	{
		this.kenhbanhang =  this.db.get("select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' order by diengiai");
	}
	
	public void createVtchRS()
	{
		this.vitricuahang =  this.db.get("select vitri as vtchTen, pk_seq as vtchId from vitricuahang where trangthai='1' order by vitri");
	}
	
	public void createLchRS()
	{
		this.loaicuahang =  this.db.get("select loai as lchTen, pk_seq as lchId from loaicuahang where trangthai='1' order by loai");
	}
	
	public void createNchRS()
	{
		this.nhomcuahang =  this.db.get("select diengiai as nchTen, pk_seq as nchId from nhomcuahang order by diengiai");
	}
	
	public void createRS()
	{
		this.getNppInfo();
		
		query="select ten as ddkdTen,pk_Seq as ddkdId from Congtacvien where trangthai=1 ";
		System.out.println("CTV init "+query);
		this.ddkdRs = this.db.get(query);
		
	}
	
	
	
	private void getNppInfo()
	{
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	public void init(String search) 
	{
		this.getNppInfo();
		String query;	
		if (search.length() == 0)
		{		
			query = 
					"select distinct CTV_FK AS MATDV,B.TEN AS TenTDV,isnull(A.TRANGTHAI,'') as trangthai,A.THANG,A.NAM,A.NGAYTAO,c.ten as nguoitao,d.ten as nguoisua,A.NGAYSUA "+
					" from CTV_DOANHSO A INNER JOIN Congtacvien B"+
					" ON A.CTV_FK = B.PK_SEQ inner join nhanvien c on c.pk_seq = a.nguoitao inner join nhanvien d on d.pk_seq = a.nguoisua ";			
				 
				this.khlist = super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "ngaysua desc", query);
		}
		else
		{
			query = search;
		}
		
		System.out.println("chuoi:"+query );
		this.khlist =  super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "ngaysua desc", query);
		this.createRS();
	}
	
	public void khChuaPhanTuyen(String dk)
	{
		this.getNppInfo();
		String query;
		
		query = 
			"select CTV_FK AS MATDV,B.TEN AS TenTDV,A.TRANGTHAI,A.THANG,A.NAM,A.NGAYTAO,A.NGUOITAO,A.NGUOISUA,A.NGAYSUA "+
			" from CTV_DOANHSO A INNER JOIN Congtacvien B"+
			" ON A.CTV_FK = B.PK_SEQ ";			
		 
		this.khlist = super.createSplittingDataNew(this.db, super.getItems(), super.getSplittings(), "MATDV desc", query);
		this.createRS();
	}

	public int getCurrentPage() 
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages()
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as skh from khachhang");
		try 
		{
			rs.next();
			int count = Integer.parseInt(rs.getString("skh"));
			rs.close();
			return count;
		}
		catch(Exception e) {}
		finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
			
		}}
		
		return 0;
	}


	public void DBclose() {
		
		try {
			if(this.hangcuahang != null)
				this.hangcuahang.close();
			if(this.kenhbanhang != null)
				this.kenhbanhang.close();
			if(this.loaicuahang != null)
				this.loaicuahang.close();
			if(this.nhomcuahang != null)
				this.nhomcuahang.close();
			if(this.vitricuahang != null)
				this.vitricuahang.close();
			if(ddkdRs!=null)ddkdRs.close();
			if(tbhRs!=null)tbhRs.close();
			if(this.db != null)
				db.shutDown();
			if(this.nhomcuahang!=null){
				nhomcuahang.close();
			}
		
			
		} catch (Exception e) {
			
		}
		
	}


	public void setKhList(ResultSet khlist) {
		this.khlist = khlist;
		
	}


	public ResultSet getKhList() {		
		return khlist;
	}


	public void setMsg(String msg) 
	{
		this.msg = msg;
	}


	public String getMsg() 
	{	
		return msg;
	}
	String diadiemId,xuatkhau;
	public String getXuatkhau()
  {
  	return xuatkhau;
  }

	public void setXuatkhau(String xuatkhau)
  {
  	this.xuatkhau = xuatkhau;
  }

	public String getDiadiemId()
  {
  	return diadiemId;
  }

	public void setDiadiemId(String diadiemId)
  {
  	this.diadiemId = diadiemId;
  }

	public ResultSet getDiadiemRs()
  {
  	return diadiemRs;
  }

	public void setDiadiemRs(ResultSet diadiemRs)
  {
  	this.diadiemRs = diadiemRs;
  }
	ResultSet diadiemRs;
	
	String ddkdId,tbhId;
	
	
	
	
	public String getDdkdId()
  {
  	return ddkdId;
  }


	public void setDdkdId(String ddkdId)
  {
  	this.ddkdId = ddkdId;
  }


	public String getTbhId()
  {
  	return tbhId;
  }


	public void setTbhId(String tbhId)
  {
  	this.tbhId = tbhId;
  }

		ResultSet ddkdRs,tbhRs;
		public ResultSet getDdkdRs()
		{
			return ddkdRs;
		}
		
		public void setDdkdRs(ResultSet ddkdRs)
		{
			this.ddkdRs = ddkdRs;
		}
		
		public ResultSet getTbhRs()
		{
			return tbhRs;
		}
		
		public void setTbhRs(ResultSet tbhRs)
		{
			this.tbhRs = tbhRs;
		}



		public String getTungay() {
		
			return this.tungay;
		}


	
		public void setTungay(String tungay) {
			this.tungay=tungay;
			
		}



		public String getDenngay() {
		
			return this.denngay;
		}



		public void setDenngay(String denngay) {
			this.denngay= denngay;
			
		}



		public String getloaiKH() {
	
			return this.loaiKH;
		}


	
		public void setloaiKH(String loaikh) {
			this.loaiKH= loaikh;
			
		}



		public String getHopdong() {
		
			return this.hopdong;
		}


	
		public void setHopdong(String hopdong) {
			this.hopdong= hopdong;
			
		}


	
		public void setQuery(String query) {
			this.query= query;
			
		}


	
		public String getThang() {
			
			return this.thang;
		}


	
		public void setThang(String thang) {
			
			this.thang = thang;
		}


	
		public String getNam() {
			
			return this.nam;
		}


	
		public void setNam(String nam) {
			
			this.nam = nam;
		}
	

	
}

