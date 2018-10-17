package geso.traphaco.distributor.beans.tdvdoanhso.imp;
import geso.traphaco.distributor.beans.tdvdoanhso.ITDVDoanhso;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Hashtable;

import javax.servlet.http.HttpServletRequest;

public class tdvdoanhso implements ITDVDoanhso, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;

	String userId;
	String id;
	String ten;
	String diachi;
	String tpId;
	String qhId;
	String smartid;
	
	String sodienthoai;
	String masothue;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg = "";
	String doanhso = "0";
	String khid = "";
	String nppId;
	String nppTen;
	String sitecode;
	String type="";
	String hch;
	String kbh;
	String bgst;
	String vtch;
	String lch;
	String nch;
	String mck;
	String ghcn;
	String tenkyhd;
	String thoihanno;
	String ghinhancongno = "";
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
	ResultSet Diabanrs;
	ResultSet nvgnRs;
	ResultSet KH_DSRs;
	String nvgnId;
	
	String maFAST;
	String kokyhd;
	String chucuahieu;
	String thang = "";
	String nam = "2015";
	dbutils db;
	geso.traphaco.center.util.Utility utilCenter;
	
	String thanhtoan;
	String thanhtoanQUY;
	
	String loaiNPP;
	String pt_chietkhau;
	String mauhd;
	String khoId;
	String cmnd;
	String chanhxe = "";
	String ptgiaohang = "";
	String cokhuyenmai;
	String doanhsobosung = "";
	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
	}


	ResultSet khoRs;
	
	public tdvdoanhso()
	{
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.ddkdId="";		
		this.doanhso = "0";
		this.utilCenter = new geso.traphaco.center.util.Utility();
	}
	
	public tdvdoanhso(String id)
	{
		this.id = id;
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.ddkdId="";
		this.doanhso = "0";
		this.utilCenter = new geso.traphaco.center.util.Utility();
		
	}
	
	
	public String getLoaiNPP() {
		return loaiNPP;
	}

	public void setLoaiNPP(String loaiNPP) {
		this.loaiNPP = loaiNPP;
	}

	public String getThanhtoan() {
		return thanhtoan;
	}

	public void setThanhtoan(String thanhtoan) {
		this.thanhtoan = thanhtoan;
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
		if(this.quocgiaId.length() == 0)
			this.tp = this.db.get("select ten as tpTen, pk_seq as tpId from tinhthanh order by ten");
		else
			this.tp = this.db.get("select ten as tpTen, pk_seq as tpId from tinhthanh where quocgia_fk = '"+this.quocgiaId+"' order by ten");
	}
	
	public void createQhRS()
	{  	
		
		if (this.tpId != null){
			
			this.qh = this.db.get("select ten as qhTen, pk_seq as qhId from quanhuyen where tinhthanh_fk='"+ this.tpId +"' order by ten");
		}
		
	}
	
	public void createHchRS()
	{
		this.hangcuahang =  this.db.get("select hang as hchTen, pk_seq as hchId from hangcuahang where trangthai='1' order by hang");
		
	}
	
	public void createKbhRS()
	{
		this.kenhbanhang =  this.db.get("select diengiai as kbhTen, pk_seq as kbhId from kenhbanhang where trangthai='1' order by diengiai");
		this.nvgnRs = this.db.get("select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where npp_fk = '" + this.nppId + "' and trangthai = '1'");
	}
	
	public void createBgstRS()
	{
		this.bgsieuthi =  this.db.get("select ten as bgstTen, pk_seq as bgstId from banggiasieuthi where npp_fk='" + this.nppId + "' order by ten");
	}
	
	public void createVtchRS()
	{
		this.vtcuahang =  this.db.get("select vitri as vtchTen, pk_seq as vtchId from vitricuahang where trangthai='1' order by vitri");
	}
	
	public void createLchRS()
	{
		this.loaicuahang =  this.db.get("select loai as Ten, pk_seq from loaicuahang where trangthai='1' order by loai");
	}
	
	public void createNchRS()
	{
		this.nhomcuahang =  this.db.get("select diengiai as nchTen, pk_seq as nchId from nhomkhachhang where trangthai='1' order by diengiai");
	}
	
	public void createMckRS()
	{
		this.mucchietkhau =  this.db.get("select chietkhau as mckTen, pk_seq as mckId from mucchietkhau where npp_fk='" + this.nppId + "' order by chietkhau");
	}
	
	public void createGhcnRS()
	{
		this.ghcongno =  this.db.get("select diengiai as ghcnTen, pk_seq as ghcnId from gioihancongno where npp_fk='" + this.nppId + "' order by sotienno");
	}
	
	public void createKhoRS()
	{
		this.khoRs =  this.db.get("select PK_SEQ, ten from kho where TRANGTHAI=1 and pk_seq in "+utilCenter.quyen_kho(this.userId));
	}
	
	public void createNkh_KhList()
	{  
		if(this.id.length()>0)
		{
		    String query="select b.diengiai as nkhTen, b.pk_seq as nkhId";
			query = query + " from khachhang_nkhachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq where a.khachhang_fk='" + this.id + "'";
			this.nkh_khSelected = db.get(query);
			
			String query2 = "select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang where pk_seq not in (select b.pk_seq as nkhId ";
			query2 = query2 + "from khachhang_nkhachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq where a.khachhang_fk='" + this.id + "')";
			this.nkh_khList = db.get(query2);
		}
		else
		{
			String query="select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang";
			this.nkh_khSelected = db.get(query);
			
			String query2 = "select diengiai as nkhTen, pk_seq as nkhId from nhomkhachhang ";
			this.nkh_khList = db.get(query2);
		}
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		System.out.println("ten npp "+nppTen);
		String sql="select loaiNPP from NHAPHANPHOI where pk_Seq = '"+this.nppId+"'";
		System.out.println("query loainpp "+sql);
		ResultSet rs = this.db.get(sql);		
		try {
			if(rs.next())
				this.loaiNPP = rs.getString("loaiNPP");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
				
	}

	public void createRS()
	{
		db = new dbutils();
		getNppInfo();
		
		String condition="";
		if(this.dtId!=null && this.dtId.length()>0)
		{
			condition+="and c.pk_seq='"+this.dtId+"' ";
		}
			
		String query = "	select distinct a.PK_SEQ as ddkdId,a.TEN as ddkdTen  "+
				"	from congtacvien a  "+
				"	where  a.trangthai = 1 ";
				query += " order by a.PK_SEQ,a.ten ";
		
		System.out.println("--DAI DIEN KINH DOANH: " + query);
		this.ddkdRs = this.db.get(query);
		
		
		
		
		if(this.ddkdId.length()>0 && this.thang.length() > 0 && this.nam.length() > 0)
		{
			query = "	select distinct d.pk_seq,d.Ma,d.TEN from "+ 
					" Congtacvien_khachhang c inner join KHACHHANG d on c.KH_FK = d.PK_SEQ  "+
					" where d.NPP_FK = "+this.nppId; 
			query+=" and c.CTV_FK in ("+this.ddkdId+") ";
			query+=" and c.CTV_FK not in (select CTV_FK from ctv_doanhso where ctv_fk = "+this.ddkdId+" and thang = "+this.thang+" and nam = "+this.nam+")";
			System.out.println("lay khach hang "+query);
			this.tbhRs = this.db.get(query);
		}
	
		
		 
		
		
	}
	public boolean CreateKh(HttpServletRequest request) 
	{		
		try
		{ 
			db = new dbutils();
			this.db.getConnection().setAutoCommit(false);
			this.ngaytao = getDateTime();
			this.nguoisua = this.userId;
			if(this.khid.length() > 0 && this.doanhso.length() > 0)
			{
				String makh[] = khid.split(",");
				String ds[] = doanhso.split(",");
				
				for(int i = 0; i < makh.length; i ++)
				{
					String query = "insert into CTV_doanhso(CTV_FK,KHACHHANG_FK,THANG,NAM,DOANHSO,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TRANGTHAI)"+
					" values("+this.ddkdId+","+makh[i]+","+this.thang+","+this.nam+","+ds[i]+",'"+this.ngaysua+"','"+this.ngaysua+"',"+this.nguoisua+","+this.nguoisua+",'0')";
					if(!db.update(query))
					{

						db.update("rollback");
						this.msg="Lỗi khi thêm mới "+query;
						return false;
					}
				}
			}
		
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			db.update("rollback");
			this.msg="Lỗi cập nhật "+e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
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
			if(khoRs!=null){
				khoRs.close();
			}
			if(this.db != null)
				this.db.shutDown();
			
		} catch (Exception e) {
			// TODO: handle exception
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

	
	public String gettype() 
	{
		return this.type;
	}

	
	public void settype(String _type) 
	{
		this.type=_type;
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

	
	public String getMaFAST() {

		return this.maFAST;
	}


	public void MaFAST(String maFAST) {
		
		this.maFAST = maFAST;
	}

	
	public String getKhongkyhd() {
		
		return this.kokyhd;
	}

	
	public void setKhongkyhd(String khongkyhd) {
		
		this.kokyhd = khongkyhd;
	}

	
	public String getChucuahieu() {

		return this.chucuahieu;
	}


	public void setChucuahieu(String chucuahieu) {
		
		this.chucuahieu = chucuahieu;
	}

	String ddkdId;
	
	public String getDdkdId()
	{
		return ddkdId;
	}
	public void setDdkdId(String ddkdId)
	{
		this.ddkdId=ddkdId;
	}
	ResultSet ddkdRs;
	
	public ResultSet getDdkdRs()
	{
		return this.ddkdRs;
	}
	public void setDdkdRs(ResultSet ddkdRs)
	{
		this.ddkdRs=ddkdRs;
	}
	
	String tbhId;
	public String getTbhId()
	{
		return this.tbhId;
	}
	public void setTbhId(String tbhId)
	{
		this.tbhId=tbhId;
	}
	
	ResultSet tbhRs;
	public ResultSet getTbhRs()
	{
		return this.tbhRs;
	}
	public void setTbhRs(ResultSet tbhRs)
	{
		this.tbhRs=tbhRs;

	}
	
	String hopdong;

	public String getHopdong()
  {
  	return hopdong;
  }

	public void setHopdong(String hopdong)
  {
  	this.hopdong = hopdong;
  }

	
	public String getThanhtoanQuy() {
		
		return this.thanhtoanQUY;
	}

	
	public void setThanhtoanQuy(String thanhtoanquy) {
		
		this.thanhtoanQUY = thanhtoanquy;
	}

	
	public String getPT_Chietkhau() {

		return this.pt_chietkhau;
	}


	public void setPT_Chietkhau(String ptCK) {
		
		this.pt_chietkhau = ptCK;
	}

	
	public String getmauhd() {
		
		return this.mauhd;
	}

	
	public void setmauhd(String mauhd) {
		this.mauhd=mauhd;
		
	}


	public String getkhoId() {
		
		return this.khoId;
	}


	public void setkhoId(String khoId) {
		
		this.khoId=khoId;
	}


	public ResultSet getKhoRs() {
		
		return this.khoRs;
	}


	public void setKhoRs(ResultSet khoRs) {
		
		this.khoRs=khoRs;
	}
	
	
	String dtId;
	ResultSet dtRs;

	public String getDtId()
  {
  	return dtId;
  }

	public void setDtId(String dtId)
  {
  	this.dtId = dtId;
  }

	public ResultSet getDtRs()
  {
  	return dtRs;
  }
	public void setDtRs(ResultSet dtRs)
  {
  	this.dtRs = dtRs;
  }

	
	public String getTenKyHd() {
		
		return this.tenkyhd;
	}

	
	public void setTenKyHd(String TenKyHd) {
		
		this.tenkyhd=TenKyHd;
	}
	
	String ngaysinh,mst;

	public String getNgaysinh()
  {
  	return ngaysinh;
  }

	public void setNgaysinh(String ngaysinh)
  {
  	this.ngaysinh = ngaysinh;
  }

	public String getMst()
  {
  	return mst;
  }

	public void setMst(String mst)
  {
  	this.mst = mst;
  }

	
	public String getThoihanno() {
		
		return this.thoihanno;
	}

	
	public void setThoihanno(String thoihanno) {
		
		this.thoihanno = thoihanno;
	}
	String ngaykyHd;

	private String quocgiaId;

	
	public String getNgaykyHd()
	{
		return ngaykyHd;
	}

	public void setNgaykyHd(String ngaykyHd)
	{
		this.ngaykyHd = ngaykyHd;
	}

	
	public String getCokhuyenmai() {
		
		return this.cokhuyenmai;
	}

	
	public void setCokhuyenmai(String cokhuyenmai) {
		
		this.cokhuyenmai = cokhuyenmai;
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

	
	public String getDoanhso() {
		
		return this.doanhso;
	}

	
	public void setDoanhso(String doanhso) {
		
		this.doanhso = doanhso;
	}

	
	public String getKhachhangId() {
		
	 return	this.khid;
	}

	
	public void setKhachhangId(String khId) {
		
		this.khid = khId;
	}

	
	public boolean UpdateKh(HttpServletRequest request) {
		try
		{ 
			db = new dbutils();
			this.db.getConnection().setAutoCommit(false);
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			String ngaytao = "";
			String nguoitao ="";
			String cmd = "select ngaytao,nguoitao from CTV_doanhso where CTV_FK = "+this.ddkdId+" and thang = "+this.thang+" and nam = "+this.nam;
			System.out.println("ngay tao "+cmd);
			ResultSet rs = db.get(cmd);
			while(rs.next())
			{
				ngaytao = rs.getString("ngaytao");
				nguoitao = rs.getString("nguoitao");
			}
			String query = "DELETE FROM CTV_doanhso WHERE CTV_FK ="+this.ddkdId+" and thang = "+this.thang+" and nam = "+this.nam;
			System.out.println("delete "+query);
			if(!db.update(query))
			{
			
				db.update("rollback");
				this.msg="Lỗi cập nhật "+query;
				return false;
			}
			System.out.println("doanhso "+doanhso);
			System.out.println("doanhsobosung "+doanhsobosung);
			System.out.println("khachhang "+khid);
			if(this.khid.length() > 0 && this.doanhso.length() > 0)
			{
				String makh[] = khid.split(",");
				String ds[] = doanhso.split(",");
				String dsbs[] = doanhsobosung.split(",");
				
				for(int i = 0; i < makh.length; i ++)
				{
					query = "insert into CTV_doanhso(CTV_FK,KHACHHANG_FK,THANG,NAM,DOANHSO,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TRANGTHAI)"+
					" values("+this.ddkdId+","+makh[i]+","+this.thang+","+this.nam+","+ds[i]+",'"+ngaytao+"','"+ngaysua+"',"+nguoitao+","+this.nguoisua+",'0')";
					if(!db.update(query))
					{

						db.update("rollback");
						this.msg="Lỗi khi thêm mới "+query;
						return false;
					}
				}
			}
			else
			{
				this.msg="Vui lòng điền dữ liệu doanh số và doanh số bổ sung ";
				return false;
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		}
		catch(Exception e)
		{
			db.update("rollback");
			this.msg="Lỗi cập nhật "+e.getMessage();
			e.printStackTrace();
			return false;
		}
		return true;
	}

	
	public ResultSet getKh_DsRs() {
		
		return this.KH_DSRs;
	}

	
	public void setKh_DSRs(ResultSet khrs) {
		
		this.KH_DSRs = khrs;
	}

	
	public void init() {
		
			db = new dbutils();
			getNppInfo();
			String query = "	select distinct a.khachhang_fk as makh,cast(a.doanhso as decimal) as doanhso,b.MA,b.ten  from CTV_doanhso a inner join KHACHHANG b on a.khachhang_fk = b.PK_SEQ ";
			query+=" and a.CTV_FK  in ("+this.ddkdId+") and thang = "+this.thang+" and nam = "+this.nam;
			query += "union select c.PK_SEQ,0 doanhso,c.MA,c.TEN from CONGTACVIEN a inner join CONGTACVIEN_KHACHHANG b "+
					 " on a.PK_SEQ = b.CTV_FK inner join KHACHHANG c on c.PK_SEQ = b.KH_FK where c.PK_SEQ not in "+
					 " (select khachhang_fk from CTV_doanhso where CTV_FK  in ("+this.ddkdId+") and thang = "+this.thang+" and nam = "+this.nam+")";
			System.out.println("CTV_doanhso "+query);
			this.KH_DSRs = this.db.get(query);
			 query = "	select distinct a.PK_SEQ as ddkdId,a.TEN as ddkdTen  "+
					"	from congtacvien a  "+
					"	where  a.trangthai = 1 ";
					query += " order by a.PK_SEQ,a.ten ";
			
			System.out.println("--Cong tac vien: " + query);
			this.ddkdRs = this.db.get(query);
			
	}

	
	public String getDoanhsobosung() {
		
		return this.doanhsobosung;
	}

	
	public void setDoanhsobosung(String doanhsobosung) {
		
		this.doanhsobosung = doanhsobosung;
	}

	
}

