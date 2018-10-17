package geso.traphaco.center.beans.danhmuckhachhang.imp;


import geso.traphaco.center.beans.danhmuckhachhang.IDanhmuckhachhang;
import geso.traphaco.center.db.sql.dbutils;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

public class Danhmuckhachhang implements IDanhmuckhachhang, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String diachi;
	String tpId;
	String tpTen;
	
	String qhId;
	String qhTen;
	
	String sodienthoai;
	String masothue;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	String hch;
	String kbh;
	String bgst;
	String vtch;
	String lch;
	String nch;
	String mck;
	String ghcn;

	ResultSet tp;
	ResultSet qh = null;

	ResultSet hangcuahang;
	String hchId;
	ResultSet kenhbanhang;
	String kbhId;
	ResultSet bgsieuthi;
	String bgstId;
	ResultSet vtcuahang;
	String vtchId;
	ResultSet loaicuahang;
	String lchId;
	ResultSet nhomcuahang;
	String nchId;
	ResultSet mucchietkhau;
	String mckId;
	ResultSet ghcongno;
	String ghcnId;
	ResultSet Rsbanggiasieuthi;	
	ResultSet nkh_khList;
	ResultSet nkh_khSelected;
	String[] nkh_khIds;
	
	ResultSet nhaphanphoi;
	
	ResultSet nvgnRs;
	String nvgnId;
	
	dbutils db;
	
	public Danhmuckhachhang(String[] param){
		this.id = param[0];
		this.ten = param[1];
		this.trangthai = param[2];		
		this.nppTen = param[3];		
		this.tpTen = param[4];
		this.qhTen = param[5];
		this.mck = param[6];
		this.mckId = param[7];
		this.kbh = param[8];
		this.kbhId = param[9];
		this.hch = param[10];
		this.hchId = param[11];
		this.lch = param[12];
		this.lchId = param[13];
		this.ghcn = param[14];
		this.ghcnId = param[15];
		this.vtch = param[16];
		this.vtchId = param[17];	
		/*this.diachi = param[18];
		this.sodienthoai = param[19];*/
		this.sodienthoai = param[18];
		this.diachi = param[19];
		this.masothue = param[20];
				
		this.bgstId = "";
		this.msg = "";
		this.tpId = "";
		this.qhId = "";
		this.nvgnId = "";
		this.nppId = "";

		this.db = new dbutils();
	}
	
	public Danhmuckhachhang(String id)
	{
		this.id = id;
		this.ten = "";
		this.diachi = "";
		this.tpId = "";
		this.qhId = "";

		this.sodienthoai = "";
		this.masothue = "";
		this.trangthai = "1";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		
		this.hch = "";
		this.kbh = "";
		this.bgst = "";
		this.vtch = "";
		this.lch = "";
		this.nch = "";
		this.mck = "";
		this.ghcn = "";
		
		this.hchId = "";
		this.kbhId = "";
		this.bgstId = "";
		this.vtchId = "";
		this.lchId = "";
		this.nchId = "";
		this.mckId = "";
		this.ghcnId = "";
		this.nvgnId = "";
		this.nppId = "";
				
		this.msg = "";
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

	public String getTen() 
	{
		return this.ten;
	}

	public void setTen(String ten) 
	{
		this.ten = ten;
	}
	
	public String getDiachi() 
	{
		return this.diachi;
	}

	public void setDiachi(String diachi) 
	{
		this.diachi = diachi;
	}
	
	public String getTpId() 
	{
		return this.tpId;
	}

	public void setTpId(String tpId) 
	{
		this.tpId = tpId;
	}

	public String getQhId() 
	{
		return this.qhId;
	}

	public void setQhId(String qhId) 
	{
		this.qhId = qhId;
	}

	public String getSodienthoai() 
	{
		return this.sodienthoai;
	}

	public void setSodienthoai(String sodienthoai) 
	{
		this.sodienthoai = sodienthoai;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getNgaytao()
	{
		return this.ngaytao;	
	}

	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;	
	}

	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}

	public String getNguoisua()
	{
		return this.nguoisua;
	}

	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
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
	
	public String getHch() 
	{
		return this.hch;
	}
	
	public void setHch(String hch)
	{
		this.hch = hch;		
	}
	
	public String getKbh() 
	{
		return this.kbh;
	}
	
	public void setKbh(String kbh) 
	{
		this.kbh = kbh;
	}
	
	public String getBgst() 
	{	
		return this.bgst;
	}
	
	public void setBgst(String bgst) 
	{
		this.bgst = bgst;
	}
	
	public String getVtch() 
	{
		return this.vtch;
	}
	
	public void setVtch(String vtch) 
	{
		this.vtch = vtch;	
	}
	
	public String getLch() 
	{	
		return this.lch;
	}
	
	public void setLch(String lch) 
	{
		this.lch = lch;	
	}
	
	public String getNch() 
	{	
		return this.nch;
	}
	
	public void setNch(String nch) 
	{		
		this.nch = nch;
	}
	
	public String getMck() 
	{	
		return this.mck;
	}
	
	public void setMck(String mck) 
	{
		this.mck = mck;	
	}
	
	public String getGhcn() 
	{
		return this.ghcn;
	}
	
	public void setGhcn(String ghcn) 
	{
		this.ghcn = ghcn;
	}

	public ResultSet getTp() 
	{
		return this.tp;
	}

	public void setTp(ResultSet tp) 
	{
		this.tp = tp;
	}

	public ResultSet getQh() 
	{
		return this.qh;
	}

	public void setQh(ResultSet qh) 
	{
		this.qh = qh;
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
	
	public ResultSet getBgsieuthi() 
	{
		return this.bgsieuthi;
	}
	
	public void setBgsieuthi(ResultSet bgsieuthi) 
	{
		this.bgsieuthi = bgsieuthi;
	}
	
	public ResultSet getVtcuahang() 
	{
		return this.vtcuahang;
	}
	
	public void setVtcuahang(ResultSet vtcuahang) 
	{
		this.vtcuahang = vtcuahang;
	}
	
	public ResultSet getLoaicuahang() 
	{
		return this.loaicuahang;
	}
	
	public void setLoaicuahang(ResultSet loaicuahang) 
	{
		this.loaicuahang = loaicuahang;
	}
	
	public ResultSet getNhomcuahang() 
	{
		return this.nhomcuahang;
	}
	
	public void setNhomcuahang(ResultSet nhomcuahang) 
	{
		this.nhomcuahang = nhomcuahang;
	}
	
	public ResultSet getMucchietkhau() 
	{	
		return this.mucchietkhau;
	}
	
	public void setMucchietkhau(ResultSet mucchietkhau)
	{
		this.mucchietkhau = mucchietkhau;	
	}
	
	public ResultSet getGhcongno() 
	{
		return this.ghcongno;
	}
	
	public void setGhcongno(ResultSet ghcongno) 
	{
		this.ghcongno = ghcongno;	
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
	
	public String getBgstId() 
	{
		return this.bgstId;
	}
	
	public void setBgstId(String bgstId) 
	{
		this.bgstId = bgstId;		
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
	
	public String getMckId() 
	{	
		return this.mckId;
	}
	
	public void setMckId(String mckId_) 
	{
		this.mckId = mckId_;	
	}
	
	public String getGhcnId() 
	{	
		return this.ghcnId;
	}
	
	public void setGhcnId(String ghcnId)
	{
		this.ghcnId = ghcnId;	
	}
	
	public ResultSet getNkh_khList() 
	{
		return this.nkh_khList;
	}
	
	public void setNkh_khList(ResultSet nkh_khlist) 
	{
		this.nkh_khList = nkh_khlist;		
	}
	
	public ResultSet getNkh_KhSelected() 
	{
		return this.nkh_khSelected;
	}
	
	public void setNkh_KhSelected(ResultSet nkh_khselected) 
	{
		this.nkh_khSelected = nkh_khselected;		
	}
	
	public Hashtable<Integer, String> getNkh_KhIds() 
	{
		Hashtable<Integer, String> selected = new Hashtable<Integer, String>();
		if(this.nkh_khIds != null){
			int size = (this.nkh_khIds).length;
			int m = 0;
			while(m < size){
				selected.put(new Integer(m), this.nkh_khIds[m]) ;
				m++;
			}
		}else{
			selected.put(new Integer(0), "null");
		}
		return selected;
	}
	
	public void setNkh_KhIds(String[] nkh_khIds) 
	{		
		this.nkh_khIds = nkh_khIds;
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
	
	public void createHchRS()
	{
		this.hangcuahang =  this.db.get("select hang as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
		//System.out.println("select hang as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
	}
	
	public void createKbhRS()
	{
		String query = "select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' order by diengiai";
		this.kenhbanhang =  this.db.get(query);
		if(this.nppId.length() > 0)
		{query = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai = '1'";
		this.nvgnRs = this.db.get(query);
		System.out.println("Lay NVGN : "+query);}
		else{
		query = "select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where trangthai = '1'";
		this.nvgnRs = this.db.get(query);}
			
	}
	
	public void createBgstRS()
	{
		if(this.nppId.length() > 0)
		this.bgsieuthi =  this.db.get("select ten as bgstTen, pk_seq as bgstId from banggiasieuthi where npp_fk='" + this.nppId + "' order by ten");
		else
			this.bgsieuthi =  this.db.get("select ten as bgstTen, pk_seq as bgstId from banggiasieuthi order by ten");
	}
	
	public void createVtchRS()
	{
		this.vtcuahang =  this.db.get("select vitri as vtchTen, pk_seq as vtchId from vitricuahang where trangthai='1' order by vitri");
	}
	
	public void createLchRS()
	{
		this.loaicuahang =  this.db.get("select loai as lchTen, pk_seq as lchId from loaicuahang where trangthai='1' order by loai");
	}
	
	public void createNchRS()
	{
		this.nhomcuahang =  this.db.get("select diengiai as nchTen, pk_seq as nchId from nhomkhachhang where trangthai='1' order by diengiai");
	}
	
	public void createMckRS()
	{
		if(this.nppId.length() > 0)
		this.mucchietkhau =  this.db.get("select chietkhau as mckTen, pk_seq as mckId from mucchietkhau where npp_fk='" + this.nppId + "' order by chietkhau");
		else
			this.mucchietkhau =  this.db.get("select chietkhau as mckTen, pk_seq as mckId from mucchietkhau order by chietkhau");
			
	}
	
	public void createGhcnRS()
	{
		if(this.nppId.length() > 0)
		this.ghcongno =  this.db.get("select diengiai as ghcnTen, pk_seq as ghcnId from gioihancongno where npp_fk='" + this.nppId + "' order by sotienno");
		else
			this.ghcongno =  this.db.get("select diengiai as ghcnTen, pk_seq as ghcnId from gioihancongno order by sotienno");
	}
	
	public void createNkh_KhList()
	{  
		if(this.id.length() > 0)
		{
		    String query="select b.diengiai as nkhTen, b.pk_seq as nkhId";
			query = query + " from khachhang_nkhachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq where a.khachhang_fk='" + this.id + "'";
			this.nkh_khSelected = db.get(query);
			
			String query2 = "select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang where pk_seq not in (select b.pk_seq as nkhId ";
			query2 = query2 + "from khachhang_nkhachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq where a.khachhang_fk='" + this.id + "')";
			System.out.println("nhom"+query2);
			this.nkh_khList = db.get(query2);
		}
		else
		{
			String query="select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang";
			//query = query + " from khachhang_nkhachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq ";
			this.nkh_khSelected = db.get(query);
			
			String query2 = "select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang ";
			System.out.println("nhom"+query2);
			this.nkh_khList = db.get(query2);
		}
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.center.util.Utility util=new geso.traphaco.center.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
	}

	public void createRS()
	{
		//this.getNppInfo();
		this.createNppRS();		
		this.createTpRS();
		this.createQhRS();
		this.createHchRS();
		this.createKbhRS();
		this.createBgstRS();
		this.createLchRS();
		this.createNchRS();
		this.createVtchRS();		
		this.createMckRS();
		this.createGhcnRS();
		this.createNkh_KhList();
		
		this.createBangGiaSieuthi();
	}
	public void createBangGiaSieuthi(){
		if(this.id.length()>0){
		String sql="select banggiasieuthi_fk as ma,bgst.ten,donvikinhdoanh as dvkdten from BGST_KHACHHANG kh "+
			" inner join banggiasieuthi bgst on bgst.pk_Seq=kh.banggiasieuthi_fk "+
			" inner join donvikinhdoanh dvkd on dvkd.pk_seq=bgst.dvkd_fk "+	
			" where khachhang_fk='"+this.id+"'";
		this.Rsbanggiasieuthi=db.get(sql);
		System.out.println("bang gia sieu thi id>0 : "+sql);
		}
		else{
			String sql="select banggiasieuthi_fk as ma,bgst.ten,donvikinhdoanh as dvkdten from BGST_KHACHHANG kh "+
			" inner join banggiasieuthi bgst on bgst.pk_Seq=kh.banggiasieuthi_fk "+
			" inner join donvikinhdoanh dvkd on dvkd.pk_seq=bgst.dvkd_fk ";
			
			this.Rsbanggiasieuthi=db.get(sql);
			System.out.println("bang gia sieu thi: "+sql);
		}
		
		
	}
	public boolean CreateKh() 
	{		
		try
		{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoisua = this.userId;
		
		String query = "";
		//Khoa sua them cho nay,khi khach hang them moi khong co chiet khau,va  khong co muc tin dung thi isert vao gia tri null,database cho phep null,nhung ko duoc phep mang gia tri khac voi bang chiet khau va muc tin dung
		//System.out.println("Muc Chiet khau :" + mucchietkhau);
		if(  this.mckId == ""){
			this.mckId=null;
		}
		if(  this.ghcnId == ""){
			this.ghcnId = null;
		}
		if(  this.hchId == ""){
			this.hchId = null;
		}
		if( this.lchId == ""){
			this.lchId = null;
		}
		if( this.vtchId == ""){
			this.vtchId=null;
		}
		if(this.bgstId.length() > 0)
		{
			query = "insert into khachhang(ten, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, chietkhau_fk, kbh_fk, hch_fk, lch_fk, npp_fk, ghcn_fk, bgst_fk, vtch_fk, dienthoai, diachi,TINHTHANH_FK,QUANHUYEN_FK, masothue)";
			query = query + " values(N'" + this.ten + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoisua + "','" + this.nguoisua + "'," + this.mckId + ",'" + this.kbhId + "'," + this.hchId + "," + this.lchId + ",'" + this.nppId + "'," + this.ghcnId + ",'" + this.bgstId + "'," + this.vtchId + ",'" + this.sodienthoai + "',N'" + this.diachi + "','"+ this.tpId + "','"+ this.qhId + "', N'" + this.masothue + "')";
		}
		else
		{
			query = "insert into khachhang(ten, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, chietkhau_fk, kbh_fk, hch_fk, lch_fk, npp_fk, ghcn_fk, vtch_fk, dienthoai, diachi,TINHTHANH_FK,QUANHUYEN_FK, masothue)";
			query = query + " values(N'" + this.ten + "','" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoisua + "','" + this.nguoisua + "'," + this.mckId + ",'" + this.kbhId + "'," + this.hchId + "," + this.lchId + ",'" + this.nppId + "'," + this.ghcnId + ", " + this.vtchId + ",'" + this.sodienthoai + "',N'" + this.diachi + "','"+ this.tpId + "','"+ this.qhId + "', N'" + masothue + "')";
		}
				
		if (!db.update(query)){
			db.getConnection().rollback();
			this.msg = "Khong the luu vao table 'Khach Hang' , " + query;
			return false;			
		}
		
		query = "select IDENT_CURRENT('khachhang') as khId";
		ResultSet rs = this.db.get(query);
	
			rs.next();
			this.id = rs.getString("khId");
			rs.close();
			
			String sql_update_smartid="update khachhang set smartid='"+this.id+"' where pk_seq=" + this.id;
			if(!db.update(sql_update_smartid))
			{
				db.getConnection().rollback();
				this.msg = "Khong The Thuc hien Cap Nhat SmartId ,Vui Long Lien He Voi Admin De Sua Loi Nay";
				return false;
			}
			
			query = "insert nvgn_kh(nvgn_fk, khachhang_fk) values('" + this.nvgnId + "', '" + this.id + "')";
			System.out.println("Cau lenh cap nhat: " + query + "\n");
			if(!db.update(query))
			{
				this.msg="Khong the cap nhat nhanviengiaonhan cho khach hang nay";
				db.getConnection().rollback();
				return false;
			}
			
		// Insert data set into table "Khachhang_nhomkhachhang"
			if(this.nkh_khIds != null)
			{
				int size = this.nkh_khIds.length;
				int m = 0;
			
				while (m < size )
				{
					query = "insert into khachhang_nkhachhang values('" + this.nkh_khIds[m] + "','" + this.id + "')"; 	
					if(!db.update(query)){
						this.msg="Khong the cap nhat khachang_nkhachhang .vui long thuc hien lai,hoac lien he voi admin de sua loi nay . Loi insert: "+query;
						db.getConnection().rollback();
						return false;
					}
					m++;	
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}catch(Exception e)
		{
			db.update("rollback");
			this.msg="Khong the them khach hang nay,xay ra loi trong qua trinh tao moi,loi dong lenh sau :" +e.toString();
			return false;
		}
		return true;
	}
	
	public boolean UpdateKh() 
	{	
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		// Update table "Khach Hang"
		String query="";
		if( this.mckId == ""){
			this.mckId=null;
		}
		if( this.ghcnId == ""){
			this.ghcnId=null;
		}
		if( this.hchId == ""){
			this.hchId=null;
		}
		if( this.lchId == ""){
			this.lchId=null;
		}	
		if( this.vtchId == ""){
			this.vtchId=null;
		}
		if(this.bgstId.length() > 0)
			query = "update khachhang set ten=N'" + this.ten + "', diachi=N'" + this.diachi + "', ghcn_fk=" + this.ghcnId + ", chietkhau_fk=" + this.mckId + ", dienthoai='" + this.sodienthoai + "', kbh_fk='" + this.kbhId + "', lch_fk=" + this.lchId + ", hch_fk=" + this.hchId + ", vtch_fk=" + this.vtchId + ", bgst_fk='" + this.bgstId + "',npp_fk = '"+ this.nppId +"', ngaysua = '" + this.ngaysua + "', trangthai = '" + this.trangthai + "', nguoisua='" + this.nguoisua + "', tinhthanh_fk='" + this.tpId + "', quanhuyen_fk='" + this.qhId + "', masothue = N'" + this.masothue + "' where pk_seq = '" + this.id + "'" ;
		else
			query = "update khachhang set ten=N'" + this.ten + "', diachi=N'" + this.diachi + "', ghcn_fk=" + this.ghcnId + ", chietkhau_fk=" + this.mckId + ", dienthoai='" + this.sodienthoai + "', kbh_fk='" + this.kbhId + "', lch_fk=" + this.lchId + ", hch_fk=" + this.hchId + ", vtch_fk=" + this.vtchId + ", npp_fk = '"+ this.nppId +"', ngaysua = '" + this.ngaysua + "', trangthai = '" + this.trangthai + "', nguoisua='" + this.nguoisua + "', tinhthanh_fk='" + this.tpId + "', quanhuyen_fk='" + this.qhId + "', masothue = '" + this.masothue + "' where pk_seq = '" + this.id + "'" ;
		
		try
		{
			this.db.getConnection().setAutoCommit(false);

			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat table 'Khach Hang' , " + query;
				return false; 
			}
		//neuw kenh ban hang 100002 thi xoa het bang gia cua khachhang nay di
		if(!this.kbhId.equals("100002")){
			query="delete bgst_khachhang where khachhang_fk='"+this.id+"'";
			if(!this.db.update(query)){
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat table 'Khach Hang' , " + query;
				return false;
			}
		}
		query = "delete from khachhang_nkhachhang where khachhang_fk= '" + this.id + "'" ;
		if(!this.db.update(query)){
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+query;
			return false; 
		}
				
		// Update table "khachhang_nkhachhang"
		if (this.nkh_khIds != null){
			int size = (this.nkh_khIds).length;
			int m = 0;
			while(m < size)
			{
				query = "insert into khachhang_nkhachhang values('" + this.nkh_khIds[m] + "','" + this.id + "')"; 				
				//System.out.println(query);
				if(!this.db.update(query)){
					this.msg="Loi trong qua trinh insert,vui long thuc hien lai,neu khong duoc vui lien he voi admin GESO de duoc huong dan .Loi dong lenh :" +query;
					this.db.getConnection().rollback();
					return false;
				}
				
				m++;
			}
		}
		
		db.update("delete nvgn_kh where nvgn_fk = '" + this.nvgnId + "' and khachhang_fk = '" + this.id + "'");
		query = "insert nvgn_kh(nvgn_fk, khachhang_fk) values('" + this.nvgnId + "', '" + this.id + "')";
		if(!db.update(query))
		{
			this.msg="Khong the cap nhat nhanviengiaonhan cho khach hang nay. Vui long thu lai.";
			db.getConnection().rollback();
			return false;
		}
		
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		}catch(Exception er){
			this.msg="Loi trong qua trinh update,vui long thuc hien lai,neu khong duoc vui lien he voi admin GESO de duoc huong dan .Loi dong lenh :" +er.toString();
			this.db.update("rollback");
			return false;
		}
		return true;
	}
	
	public void init() 
	{
		String query = "select a.pk_seq as khId, a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, isnull(a.masothue, '') as masothue, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId, ";
	    query = query + "e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId, "; 
	    query = query + "k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, a.tinhthanh_fk as tpId, a.quanhuyen_fk as qhId, nvgn_kh.nvgn_fk as nvgnId, ";
	    query = query + "n.ten as tinhthanh, o.ten as quanhuyen from khachhang a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq ";
	    query = query + "left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq ";
	    query = query + "inner join nhaphanphoi h on a.npp_fk = h.pk_seq left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq ";
	    query = query + "left join vitricuahang m on a.vtch_fk = m.pk_seq inner join nvgn_kh on a.pk_seq = nvgn_kh.khachhang_fk " 
	    			  + "left join tinhthanh n on a.tinhthanh_fk = n.pk_seq "
	    			  + "left join quanhuyen o on a.quanhuyen_fk = o.pk_seq "
	    			  + "where a.pk_seq='" + this.id + "'";
	    System.out.println("Query :"+query);
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();        	
        	this.id = rs.getString("khId");
        	this.nppId = rs.getString("nppId");
        	this.nppTen = rs.getString("nppTen");
        	this.ten = rs.getString("khTen");
        	this.sodienthoai = rs.getString("dienthoai");
        	this.masothue = rs.getString("masothue");
        	this.diachi = rs.getString("diachi");
        	if(rs.getString("qhId")!= null)
        	{
        		this.tpId = rs.getString("tpId");
        		this.qhId = rs.getString("qhId");
        	}
        	else
        	{
        		this.tpId = "";
        		this.qhId = "";        		
        	}
        	this.tpTen = rs.getString("tinhthanh");
        	this.qhTen = rs.getString("quanhuyen");
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getDate("ngaytao").toString();
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getDate("ngaysua").toString();
        	this.nguoisua = rs.getString("nguoisua");
        	this.mck = rs.getString("chietkhau");
        	this.mckId = rs.getString("mckId");
        	this.kbh = rs.getString("kbhTen");
        	this.kbhId = rs.getString("kbhId");
        	this.hch = rs.getString("hchTen");
        	this.hchId = rs.getString("hchId");
        	this.lch = rs.getString("lchTen");
        	this.lchId = rs.getString("lchId");
        	this.ghcn = rs.getString("ghcnTen");
        	this.ghcnId = rs.getString("ghcnId");
        	this.bgst = rs.getString("bgstTen");
        	this.bgstId = rs.getString("bgstId");
        	this.vtch = rs.getString("vtchTen");
        	this.vtchId = rs.getString("vtchId");
        	this.nvgnId = rs.getString("nvgnId");
        	this.msg = "";
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {			
		}}
       	createRS(); 
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public String getMasothue() 
	{
		return this.masothue;
	}

	public void setMasothue(String masothue) 
	{
		this.masothue = masothue;
	}

	
	public ResultSet getBangGiaST() {
		
		return this.Rsbanggiasieuthi;
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
	
	public void DBclose() {
		
		try {
			
			if(this.bgsieuthi != null)
				this.bgsieuthi.close();
			if(this.ghcongno != null)
				this.ghcongno.close();
			if(this.hangcuahang != null)
				this.hangcuahang.close();
			if(this.loaicuahang != null)
				this.loaicuahang.close();
			if(this.kenhbanhang != null)
				this.kenhbanhang.close();
			if(this.mucchietkhau != null)
				this.mucchietkhau.close();
			if(this.nhomcuahang != null)
				this.nhomcuahang.close();
			if(this.nkh_khSelected != null)
				this.nkh_khSelected.close();
			if(this.nkh_khList != null)
				this.nkh_khList.close();
			if(this.qh != null)
				this.qh.close();
			if(this.Rsbanggiasieuthi != null)
				this.Rsbanggiasieuthi.close();
			if(this.tp != null)
				this.tp.close();
			if(this.vtcuahang != null)
				this.vtcuahang.close();
			if(nvgnRs!=null){
				nvgnRs.close();
			}
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {			
		}
		
	}


	public ResultSet getNvgnRs() 
	{
		return this.nvgnRs;
	}

	public void setNvgnRs(ResultSet nvgnRs) 
	{
		this.nvgnRs = nvgnRs;
	}

	public String getNvgnId() 
	{
		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId) 
	{
		this.nvgnId = nvgnId;
	}

	@Override
	public String getTpTen() {
		
		return tpTen;
	}

	@Override
	public void setTpTen(String tpTen) {
		
		this.tpTen = tpTen;
	}

	@Override
	public String getQhTen() {
		
		return qhTen;
	}

	@Override
	public void setQhTen(String qhTen) {
		
		this.qhTen = qhTen;
	}
	
}

