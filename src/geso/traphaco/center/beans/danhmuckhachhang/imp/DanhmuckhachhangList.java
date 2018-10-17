package geso.traphaco.center.beans.danhmuckhachhang.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.beans.danhmuckhachhang.IDanhmuckhachhang;
import geso.traphaco.center.beans.danhmuckhachhang.IDanhmuckhachhangList;

import geso.traphaco.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class DanhmuckhachhangList extends Phan_Trang implements IDanhmuckhachhangList, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId; //nppId
	String ten;
	String trangthai;
	
	String tpId;
	String qhId;
	String ttTen;
	String qhTen;
	
	
	ResultSet tp;
	ResultSet qh = null;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	List<IDanhmuckhachhang> khlist;
	
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
	
	ResultSet nhaphanphoi;
	
	dbutils db;
	
	int currentPages;
	int[] listPages;
		
	//Constructor
	public DanhmuckhachhangList(String[] param)
	{
		this.ten = param[0];
		//this.trangthai = param[1];
		this.hchId = param[1];
		this.kbhId = param[2];
		this.vtchId = param[3];
		this.lchId = param[4];
		this.nchId = param[5];
		this.nppId = param[6];
	
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10};
		
		this.db = new dbutils();
	}
	
	public DanhmuckhachhangList()
	{
		this.ten = "";
		this.trangthai = "";
		this.hchId = "";
		this.kbhId = "";
		this.vtchId = "";
		this.lchId = "";
		this.nchId = "";
		this.nppId = "";
		this.tpId = "";
		this.qhId = "";
		
		currentPages = 1;
		listPages = new int[]{1, 2 , 3 , 4, 5, 6, 7, 8, 9, 10};
		
		this.db = new dbutils();
		//init("");
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

	public List<IDanhmuckhachhang> getKhList() 
	{
		return this.khlist;
	}

	public void setKhList(List<IDanhmuckhachhang> khlist) 
	{
		this.khlist = khlist;
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
		//this.getNppInfo();
		this.createHchRS();
		this.createKbhRS();
		this.createLchRS();
		this.createVtchRS();
		this.createNppRS();
		this.createTpRS();
		this.createQhRS();
	}
	
	public void createKhBeanList(String query)
	{
		ResultSet rs =  super.createSplittingData(super.getItems(), super.getSplittings(), "khId desc", query);//this.db.get(query);
		List<IDanhmuckhachhang> khlist = new ArrayList<IDanhmuckhachhang>();
			
		if(rs != null)
		{
			String[] param = new String[25];
			IDanhmuckhachhang khBean = null;			
			try {
				while(rs.next())
				{	
					param[0]= rs.getString("khId");
					param[1]= rs.getString("khTen");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("nppTen");					
					param[4]= rs.getString("tinhthanh");
					param[5]= rs.getString("quanhuyen");
					param[6]= rs.getString("chietkhau");
					param[7]= rs.getString("mckId");
					param[8]= rs.getString("kbhTen");
					param[9]= rs.getString("kbhId");					
					param[10]= rs.getString("hchTen");
					param[11]= rs.getString("hchId");
					param[12]= rs.getString("lchTen");
					param[13]= rs.getString("lchId");
					param[14]= rs.getString("ghcnTen");
					param[15]= rs.getString("ghcnId");
					param[16]= rs.getString("vtchTen");
					param[17]= rs.getString("vtchId");
					/*param[18]= rs.getString("diachi");
					param[19]= rs.getString("dienthoai");*/
					param[18]= rs.getString("dienthoai");
					param[19]= rs.getString("diachi");
					param[20]= rs.getString("bgstTen");																					
					param[21]= rs.getString("bgstId");
					
					khBean = (IDanhmuckhachhang) new Danhmuckhachhang(param);
					khlist.add(khBean);
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
		this.khlist = khlist;
	}
	private void getNppInfo(){
		
		/*ResultSet rs = this.db.get("select a.pk_seq, a.ten, a.sitecode from nhaphanphoi a, nhanvien b where b.dangnhap = a.ma and b.pk_seq ='" + this.userId + "'");
		try{
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
			
		}catch(Exception e){}
		*/
		//Phien ban moi
		geso.traphaco.center.util.Utility util=new geso.traphaco.center.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}
	public void init(String search) 
	{
		//this.getNppInfo();
		String query;	
		if (search.length() == 0)
		{
			query = "select ROW_NUMBER() OVER(ORDER BY a.pk_seq DESC) AS 'stt', a.pk_seq as khId, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId," 
			+ " e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,"
			+ " k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, n.ten as tinhthanh, o.ten as quanhuyen"
			+ " from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq"
			+ " left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq"
			+ " inner join nhaphanphoi h on a.npp_fk = h.pk_seq left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq left join vitricuahang m on a.vtch_fk = m.pk_seq "
			+ " left join tinhthanh n on a.tinhthanh_fk = n.pk_seq"
			+ " left join quanhuyen o on a.quanhuyen_fk = o.pk_seq "
			+ " where 1=1 ";
			
			//where a.npp_fk='"+ this.nppId +"'
			//query = "select top(10) * from vwKhachHang where nppId = '" + this.nppId + "'";
			//query = "select top(10) * from (" + query + ") as khList";
		}
		else
		{
			query = search;
		}
		
		//System.out.println("chuoi:"+query );
		this.createKhBeanList(query);  
		this.createRS();
		
	}
	
	public void khChuaPhanTuyen(String dk)
	{
		this.getNppInfo();
		String query;
		
		query = "select  a.pk_seq as khId, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId," 
			+ " e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId,"
			+ " k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, n.ten as tinhthanh, o.ten as quanhuyen"
			+ " from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq"
			+ " left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq"
			+ " inner join nhaphanphoi h on a.npp_fk = h.pk_seq left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq left join vitricuahang m on a.vtch_fk = m.pk_seq "
			+ " left join tinhthanh n on a.tinhthanh_fk = n.pk_seq"
			+ " left join quanhuyen o on a.quanhuyen_fk = o.pk_seq"
			+ " where a.npp_fk='"+ this.nppId +"' "
			+ " and a.trangthai = 1 and a.PK_SEQ NOT IN (SELECT KHACHHANG_FK FROM KHACHHANG_TUYENBH) "+dk;
		
		//System.out.println("chuoi:"+query );
		this.createKhCPTBeanList(query);  
		this.createRS();
	}
	
	public void createTpRS()
	{  	
		this.tp = this.db.get("select ten as tpTen, pk_seq as tpId from tinhthanh order by ten");
	}
	
	public void createQhRS()
	{  	
		
		if (this.tpId.length() > 0){
			
			this.qh = this.db.get("select ten as qhTen, pk_seq as qhId from quanhuyen where tinhthanh_fk='"+ this.tpId +"' order by ten");
			System.out.print("select ten as qhTen, pk_seq as qhId from quanhuyen where tinhthanh_fk='"+ this.tpId +"' order by ten");
		}
		else{
			String query = "select ten as qhTen, pk_seq as qhId from quanhuyen order by ten";
			this.qh = this.db.get(query);
		}
		
	}
	
	public void createKhCPTBeanList(String query)
	{
		ResultSet rs =  super.createSplittingData(super.getItems(), super.getSplittings(), "khId desc", query);//this.db.get(query);
		List<IDanhmuckhachhang> khlist = new ArrayList<IDanhmuckhachhang>();
			
		if(rs != null)
		{
			String[] param = new String[25];
			IDanhmuckhachhang khBean = null;			
			try {
				while(rs.next())  //chua  co nppTen
				{	
					param[0]= rs.getString("khId");
					param[1]= rs.getString("khTen");
					param[2]= rs.getString("trangthai");
					param[3]= rs.getString("nppTen");
					param[4]= rs.getString("tinhthanh");
					param[5]= rs.getString("quanhuyen");
					param[6]= rs.getString("nguoisua");
					param[7]= rs.getString("chietkhau");
					param[8]= rs.getString("mckId");
					param[9]= rs.getString("kbhTen");
					param[10]= rs.getString("kbhId");					
					param[11]= rs.getString("hchTen");
					param[12]= rs.getString("hchId");
					param[13]= rs.getString("lchTen");
					param[14]= rs.getString("lchId");
					param[15]= rs.getString("ghcnTen");
					param[16]= rs.getString("ghcnId");
					param[17]= rs.getString("vtchTen");
					param[18]= rs.getString("vtchId");
					param[19]= rs.getString("diachi");
					param[20]= rs.getString("dienthoai");
					param[21]= rs.getString("bgstTen");																					
					param[22]= rs.getString("bgstId");
					
					khBean = (IDanhmuckhachhang) new Danhmuckhachhang(param);
					khlist.add(khBean);
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
		this.khlist = khlist;
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
	
	public ResultSet getNhaphanphoi() 
	{
		return this.nhaphanphoi;
	}

	public void setNhaphanphoi(ResultSet nhaphanphoi)
	{
		this.nhaphanphoi = nhaphanphoi;
	}
	
	private void createNppRS()
	{  			
		String query = "select a.pk_seq as nppId, a.ten as nppTen, 'Khu vuc ' + b.ten as kvTen ";
		query += "from nhaphanphoi a inner join khuvuc b on a.khuvuc_fk = b.pk_seq where a.trangthai = '1' and a.sitecode = a.convsitecode order by b.pk_seq asc";
		System.out.println("Query NPP: " + query);
		this.nhaphanphoi = this.db.get(query);
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
			if(this.tp != null)
				this.tp.close();
			if(this.qh != null)
				this.qh.close();
			if(this.db != null)
				db.shutDown();
			if(this.nhomcuahang!=null){
				nhomcuahang.close();
			}
			if(khlist!=null){
				khlist.clear();
			}
			
		} catch (Exception e) {		
		}
		
	}

	@Override
	public String getTpId() {
		
		return tpId;
	}

	@Override
	public void setTpId(String tpId) {
		
		this.tpId = tpId;
	}

	@Override
	public String getQhId() {
		
		return qhId;
	}

	@Override
	public void setQhId(String qhId) {
		
		this.qhId = qhId;
	}

	@Override
	public ResultSet getTp() {
		
		return tp;
	}

	@Override
	public void setTp(ResultSet tp) {
		
		this.tp = tp;
	}

	@Override
	public ResultSet getQh() {
		
		return this.qh;
	}

	@Override
	public void setQh(ResultSet qh) {
		
		this.qh = qh;
	}

}

