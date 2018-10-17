package geso.traphaco.center.beans.khachhang.imp;

import geso.traphaco.center.beans.khachhang.IKhachhangList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Phan_Trang;

import java.io.Serializable;
import java.sql.ResultSet;

public class KhachhangList extends Phan_Trang implements IKhachhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String ten;
	String trangthai;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String tungay;
	String denngay;
	String loaiKH;
	String hopdong;
	String query;
	
	
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
	String hangkh;
	
	ResultSet rshangkh;
	

	public KhachhangList()
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
		this.createHchRS();
		this.createKbhRS();
		this.createLchRS();
		this.createVtchRS();
		
		String query="select * from DiaDiem ";
		this.diadiemRs = this.db.get(query);
		
		query="select ten as ddkdTen,pk_Seq as ddkdId from DaiDienKinhDoanh where trangthai=1 and pk_seq in (  select ddkd_fk from DAIDIENKINHDOANH_NPP ) ";
		
		this.ddkdRs = this.db.get(query);
		
		query="select diengiai as tbhTen,pk_Seq as tbhId from TuyenBanHang ";
		
		if(ddkdId.length()>0)
		{
			query+=" and ddkd_Fk='"+this.ddkdId+"'" ;
		}
		
		this.tbhRs = this.db.get(query);
		this.rshangkh=db.get("select PK_SEQ,DIENGIAI from HANGCUAHANG where TRANGTHAI=1");
		
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
				"	select f.pk_seq as hangkh, ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', isnull(a.mafast,'') as mafast , a.pk_seq as khId, a.smartid, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId,  \n"+ 
				"		e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,   \n"+
				"		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen,  \n"+
				"		m.pk_seq as vtchId, a.dienthoai, a.diachi,  \n"+
				"	STUFF      \n"+
				"	(    \n"+
				"		(   \n"+
				"			select DISTINCT TOP 100 PERCENT ' , ' + tbh.DIENGIAI   \n"+
				"			from KHACHHANG_TUYENBH khtbh inner join TUYENBANHANG tbh on tbh.PK_SEQ=khtbh.TBH_FK   \n"+ 
				"			where khtbh.KHACHHANG_FK=a.PK_SEQ and tbh.NPP_FK=a.NPP_FK  \n"+ 
				"			ORDER BY ' , ' + tbh.DIENGIAI      \n"+
				"			FOR XML PATH('')      \n"+
				"		 ), 1, 2, ''   \n"+
				"	) + ' '  AS tbhTen,a.CHUCUAHIEU,a.MaHD,n.ten as LoaiCH ,a.NgayKyHD  \n"+ 
				"  from    \n"+
				"  khachhang a left join nhanvien b on a.nguoitao = b.pk_seq   \n"+ 
				"  left join nhanvien c on a.nguoisua = c.pk_seq    \n"+
				"  left join mucchietkhau d on a.chietkhau_fk = d.pk_seq   \n"+
				"  left join kenhbanhang e on a.kbh_fk = e.pk_seq    \n"+
				"  left join hangcuahang f on a.hch_fk = f.pk_seq    \n"+
				"  left join loaicuahang g on a.lch_fk = g.pk_seq    \n"+
				"  inner join nhaphanphoi h on a.npp_fk = h.pk_seq    \n"+
				"  left join gioihancongno k on a.ghcn_fk = k.pk_seq    \n"+
				"  left join banggiasieuthi l on a.bgst_fk = l.pk_seq    \n"+
				"  left join vitricuahang m on a.vtch_fk = m.pk_seq \n" +
				"  left join LOAIKHACHHANG n on n.pk_seq=a.XuatKhau \n";
		}
		else
		{
			query = search;
		}
		
		System.out.println("chuoi:"+query );
	
		this.khlist =  super.createSplittingData(super.getItems(), super.getSplittings(), "khId desc", query);
		this.createRS();
	}
	
	public void khChuaPhanTuyen(String dk)
	{
		this.getNppInfo();
		String query;
		
		query = 
			  " select  a.smartid as khId,a.maFAST as mafast, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId," 
			+ " e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,"
			+ " k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi"
			+ " from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq"
			+ " left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq"
			+ " inner join nhaphanphoi h on a.npp_fk = h.pk_seq left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq left join vitricuahang m on a.vtch_fk = m.pk_seq where a.npp_fk='"+ this.nppId +"' "
			+ " and a.trangthai = 1 and isnull(a.XuatKhau,0)=0 and a.PK_SEQ NOT IN (SELECT KHACHHANG_FK FROM KHACHHANG_TUYENBH) "+dk;			
		 
		this.khlist = super.createSplittingData(super.getItems(), super.getSplittings(), "khId desc", query);
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

	@Override
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

	@Override
	public void setKhList(ResultSet khlist) {
		this.khlist = khlist;
		
	}

	@Override
	public ResultSet getKhList() {		
		return khlist;
	}

	@Override
	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	@Override
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


		@Override
		public void setQuery(String query) {
			this.query= query;
			
		}
	
		public String getHangkh() {
			return hangkh;
		}


		public void setHangkh(String hangkh) {
			this.hangkh = hangkh;
		}
		public ResultSet getRshangkh() {
			return rshangkh;
		}


		public void setRshangkh(ResultSet rshangkh) {
			this.rshangkh = rshangkh;
		}

}

