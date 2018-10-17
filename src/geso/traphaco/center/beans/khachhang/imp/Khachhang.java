package geso.traphaco.center.beans.khachhang.imp;

import geso.traphaco.center.beans.khachhang.IKhachhang;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class Khachhang implements IKhachhang, Serializable
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
	String msg;
	
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
	
	ResultSet nvgnRs;
	String nvgnId;
	
	String maFAST;
	String kokyhd;
	String chucuahieu;
	
	dbutils db;
	geso.traphaco.center.util.Utility utilCenter;
	
	String thanhtoan;
	String thanhtoanQUY;
	
	String loaiNPP;
	String pt_chietkhau;
	String mauhd;
	String khoId;
	String cmnd;
	String dungmau;

	String cokhuyenmai;
	
	ResultSet khoRs;
	private String soHieuTaiKhoan;
	private List<Erp_Item> taiKhoanIdList;
	
	public Khachhang()
	{
		this.id = "";
		this.ten = "";
		this.diachi = "";
		this.tpId = "";
		this.qhId = "";
		this.type="0";

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
		this.thanhtoan = "";
		this.thanhtoanQUY = "0";
				
		this.msg = "";
		this.diadiemId="";
		this.xuatkhau="0";
		this.maFAST = "";
		this.kokyhd = "0";
		this.chucuahieu = "";
		this.ddkdId="";
		this.tbhId="";
		this.hopdong="";
		this.pt_chietkhau = "";
		this.mauhd="";
		this.khoId="";
		this.dtId="";
		this.tenkyhd="";
		this.ngaysinh="";
		this.mst="";
		this.cmnd="";
		this.thoihanno= "0";
		this.ngaykyHd="";
		this.cokhuyenmai = "1";
		this.dungmau="0";
		
		this.soHieuTaiKhoan = "" ;
		this.taiKhoanIdList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
		this.utilCenter = new geso.traphaco.center.util.Utility();
	}
	
	public Khachhang(String id)
	{
		this.id = id;
		this.ten = "";
		this.diachi = "";
		this.tpId = "";
		this.qhId = "";
		this.type="0";
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
		this.kbhId = "100025";
		this.bgstId = "";
		this.vtchId = "";
		this.lchId = "";
		this.nchId = "";
		this.mckId = "";
		this.ghcnId = "";
		this.nvgnId = "";
		this.thanhtoan = "";
		this.thanhtoanQUY = "0";
				
		this.msg = "";
		this.diadiemId="";
		this.xuatkhau="0";
		this.maFAST = "";
		this.kokyhd = "0";
		this.chucuahieu = "";
		
		this.ddkdId="";
		this.tbhId="";
		this.hopdong="";
		this.pt_chietkhau = "";
		this.mauhd="";
		this.khoId="";
		this.dtId="";
		this.tenkyhd="";
		this.ngaysinh="";
		this.mst="";
		this.cmnd="";
		this.thoihanno = "";
		this.ngaykyHd="";
		this.cokhuyenmai = "1";
		this.dungmau="0";
		
		this.soHieuTaiKhoan = "" ;
		this.taiKhoanIdList = new ArrayList<Erp_Item>();
		
		this.db = new dbutils();
		this.utilCenter = new geso.traphaco.center.util.Utility();
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
		this.nvgnRs = this.db.get("select pk_seq as nvgnId, ten as nvgnTen from nhanviengiaonhan where trangthai = '1'");
	}
	
	public void createBgstRS()
	{
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
		this.mucchietkhau =  this.db.get("select chietkhau as mckTen, pk_seq as mckId from mucchietkhau  order by chietkhau");
	}
	
	public void createGhcnRS()
	{
		this.ghcongno =  this.db.get("select diengiai as ghcnTen, pk_seq as ghcnId from gioihancongno order by sotienno");
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
			//query = query + " from khachhang_nkhachhang a inner join nhomkhachhang b on a.nkh_fk = b.pk_seq ";
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
		this.sitecode=util.getSitecode();
		
		
		// check ChiNhanh hoac DoiTac
		String sql="select loaiNPP from NHAPHANPHOI where pk_Seq = '"+this.nppId+"'";
		ResultSet rs = this.db.get(sql);		
		try {
			rs.next();
			this.loaiNPP = rs.getString("loaiNPP");
			rs.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void createRS()
	{
		this.createTaiKhoanList();
		//this.getNppInfo();
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
		this.createKhoRS();
		createBangGiaSieuthi();
		
		String query="select * from DiaDiem ";
		this.diadiemRs = this.db.get(query);
		
		String condition="";
		if(this.dtId!=null && this.dtId.length()>0)
		{
			condition+="and c.pk_seq='"+this.dtId+"' ";
		}
		
		query = "	select distinct a.MANHANVIEN ,a.PK_SEQ as ddkdId,a.TEN as ddkdTen  "+
				"	from DAIDIENKINHDOANH a inner join DAIDIENKINHDOANH_NPP b on b.ddkd_fk=a.PK_SEQ inner join NHAPHANPHOI c on c.PK_SEQ=b.npp_fk  "+
				"	where  a.trangthai=1 "+condition+" "+
				"	union    "+
				"	select distinct a.MANHANVIEN ,a.PK_SEQ as ddkdId,a.TEN as ddkdTen  "+
				"	from DAIDIENKINHDOANH a inner join DAIDIENKINHDOANH_NPP b on b.ddkd_fk=a.PK_SEQ inner join NHAPHANPHOI c on c.PK_SEQ=b.npp_fk  "+
				"	where  a.trangthai=1 "+condition+"  ";
		//query += " order by c.TEN,a.ten ";
		
		System.out.println("--DAI DIEN KINH DOANH: " + query);
		this.ddkdRs = this.db.get(query);
		
		
		query = "	select tbh.PK_SEQ as tbhId,'T'+cast(tbh.NGAYID as varchar(2))+'_'+ ddkd.TEN as tbhTen,   "+
				"		isnull((select max(sott) from KHACHHANG_TUYENBH where TBH_FK=tbh.PK_SEQ ),0)+1 as SOTT,''as TanSo,ddkd.TEN as tdvTen,tbh.ngayId  "+ 
				"	from TUYENBANHANG tbh inner join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=tbh.DDKD_FK  ";
		
		if(this.ddkdId.length()>0)
		{
			query+=" and tbh.ddkd_Fk in ("+this.ddkdId+") ";
		}
		
/*		if(this.dtId!=null && this.dtId.length()>0)
		{
			query=
			"		select tbh.PK_SEQ as tbhId,'T'+cast(tbh.NGAYID as varchar(2))+'_'+ ddkd.TEN as tbhTen,   "+
			"			isnull((select max(sott) from KHACHHANG_TUYENBH where TBH_FK=tbh.PK_SEQ ),0)+1 as SOTT,''as TanSo,ddkd.TEN as tdvTen,tbh.ngayId  "+  
			"		where tbh.NPP_FK in (select PK_SEQ from NHAPHANPHOI where tructhuoc_fk='"+this.nppId+"' ) " ;  
			if(this.ddkdId.length()>0)
			{
				query+=" and tbh.ddkd_Fk in ("+this.ddkdId+") ";
			}
		}*/
		
		if(this.id.length()>0)
		{
			query+=
			" and  tbh.PK_SEQ not in   "+
			"	(  "+ 
			"		select TBH_FK from KHACHHANG_TUYENBH   "+
			"		where KHACHHANG_FK='"+this.id+"'   "+
			"	)   "+
		"union all	" +
			"select TBH_FK,'T'+cast(b.NGAYID as varchar(2))+'_'+ c.TEN as tbhTen,SOTT,TANSO ,c.TEN as tdvTen,b.ngayId   "+ 
			" from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on b.PK_SEQ=a.TBH_FK  "+
			" inner join DAIDIENKINHDOANH c on c.PK_SEQ=b.DDKD_FK  "+
			"	where a.KHACHHANG_FK='"+this.id+"' ";
			
			if(this.ddkdId.length()>0)
			{
				query+=" and b.ddkd_Fk in ("+this.ddkdId+") ";
			}
		}		


		query+=" order by tdvTen, ngayId ";
		
		System.out.println("[tbhRs] 1: "+query);
		this.tbhRs = this.db.get(query);
		
		query="select pk_seq,ten,diachi,ma from nhaphanphoi ";
		this.dtRs=this.db.get(query);
		
	}
	private void createTaiKhoanList() {
		String query = "select soHieuTaiKhoan as pk_seq, soHieuTaiKhoan + ' - ' + tenTaiKhoan as ten from erp_taiKhoanKT where trangThai = 1 and npp_fk = 1 and soHieuTaiKhoan like '131%'\n";
		
		Erp_Item.getListFromQuery(db, query, this.taiKhoanIdList);
	}

	public void createBangGiaSieuthi(){
		String sql="select banggiasieuthi_fk as ma,bgst.ten,donvikinhdoanh as dvkdten from BGST_KHACHHANG kh "+
			" inner join banggiasieuthi bgst on bgst.pk_Seq=kh.banggiasieuthi_fk "+
			" inner join donvikinhdoanh dvkd on dvkd.pk_seq=bgst.dvkd_fk "+
			" where khachhang_fk='"+this.id+"'";
		this.Rsbanggiasieuthi=db.get(sql);
	}
	public boolean CreateKh(HttpServletRequest request) 
	{		
		try
		{
			this.db.getConnection().setAutoCommit(false);
		this.ngaytao = getDateTime();
		this.nguoisua = this.userId;
		
		String query = "";
		
		if(  this.mckId == ""){
			this.mckId=null;
		}
		if(  this.ghcnId == "")
		{
			this.ghcnId = null;
		}
		
		/*if( this.KiemTraKhachHangTrunng() && this.type.trim().equals("0") )
		{
			this.msg="Địa chỉ này đã trùng,vui lòng kiểm tra lại";
			this.type="1";
			return false;
		}*/
		
		if(KiemTraKhachHangTrunngMa_Fast())
		{
			this.msg="Mã FAST này đã trùng,vui lòng kiểm tra lại";
			this.type="1";
			return false;
		}
		
		if(this.hopdong.trim().length()<=0)
				this.kokyhd="1";
		else 
			this.kokyhd="0";
		
		String _ck = "NULL";
		if(this.pt_chietkhau.trim().length() > 0)
			_ck = this.pt_chietkhau.replaceAll(",", "");
		
		if(this.khoId=="")
			this.khoId = null;
		
		if(this.mauhd =="")
			this.mauhd = null;
		
		if(this.dtId=="")
		this.dtId=null;;
		
		// KH OTC: KH CỦA CN HÀ NỘI DÙNG MẪU HÓA ĐƠN 2(HO), CÒN LẠI : MẪU HÓA ĐƠN 1(CN) 
		// KH ETC: KH CỦA CN HÀ NỘI DÙNG MẪU HÓA ĐƠN 2(HO), CN HCM : TÙY CHỌN TRONG DLN , CÒN LẠI: DÙNG MẪU 1
		if( this.kbhId.equals("100025") && this.nppId.equals("100002")) this.mauhd = "2" ;
		if( this.kbhId.equals("100025") && !this.nppId.equals("100002")) this.mauhd = "1" ;
		if( this.kbhId.equals("100052") && this.nppId.equals("100002")) this.mauhd = "2" ;
		if( this.kbhId.equals("100052") && !this.nppId.equals("100002") && !this.nppId.equals("106210")) this.mauhd = "1" ;
		
		if(this.bgstId.length() > 0)
		{			
			query = "insert into khachhang(TrucThuoc_fk,ten, maFAST, chucuahieu, KhongKyHopDong, trangthai, ngaytao, ngaysua, nguoitao, nguoisua, chietkhau_fk, kbh_fk, hch_fk, npp_fk, ghcn_fk, bgst_fk,  dienthoai, diachi,TINHTHANH_FK,QUANHUYEN_FK, masothue,XuatKhau, thanhtoan, THANHTOANQUY, MaHD, PT_CHIETKHAU, MAUHOADON, KHO_FK, TenKyHd,NgaySinh,MST_CaNhan,cmnd, thoihanno,NgayKyHD, cokhuyenmai,mauho, taiKhoan_FK)";
			query = query + " values("+dtId+",N'" + this.ten + "', N'"+ this.maFAST + "', N'" + this.chucuahieu + "',  '" + this.kokyhd + "', '" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoisua + "','" + this.nguoisua + "'," + this.mckId + ",'" + this.kbhId + "'," + (this.hchId.trim().length()<=0?"NULL":this.hchId)+ ",'" + this.nppId + "'," + this.ghcnId + ",'" + this.bgstId + "','" + this.sodienthoai + "',N'" + this.diachi + "','"+ this.tpId + "','"+ this.qhId + "', N'" + this.masothue + "',"+this.xuatkhau+  "," +this.thanhtoan+", '" + this.thanhtoanQUY + "', '"+this.hopdong+"', " + _ck + ","+this.mauhd+","+this.khoId+",N'"+this.tenkyhd +"','"+this.ngaysinh+"','"+this.mst+"','"+this.cmnd+"', "+(this.thoihanno.trim().length()<=0?"NULL":this.thoihanno)+" ,'"+this.ngaykyHd+"', '" + this.cokhuyenmai + "',"+this.dungmau+", " + this.soHieuTaiKhoan + ") ";
			
			System.out.println("query: "+ query);
		}
		else
		{
			query = "insert into khachhang(TrucThuoc_fk,ten, maFAST, chucuahieu\n" +
					", KhongKyHopDong, trangthai, ngaytao, ngaysua\n" +
					", nguoitao, nguoisua, chietkhau_fk, kbh_fk\n" +
					", hch_fk, npp_fk, ghcn_fk, dienthoai\n" +
					", diachi,TINHTHANH_FK,QUANHUYEN_FK, masothue\n" +
					",XuatKhau, thanhtoan, THANHTOANQUY, MaHD\n" +
					", PT_CHIETKHAU, MAUHOADON, KHO_FK, TenKyHd\n" +
					",NgaySinh,MST_CaNhan,cmnd, thoihanno\n" +
					",NgayKyHd, cokhuyenmai,mauho, taiKhoan_FK)";
			query = query + " values("+this.dtId+",N'" + this.ten + "', N'" + this.maFAST + "', N'" + this.chucuahieu + "'\n" +
					", '" + this.kokyhd + "', '" + this.trangthai + "','" + this.ngaytao + "','" + this.ngaytao + "'\n" +
					",'" + this.nguoisua + "','" + this.nguoisua + "'," + this.mckId + ",'" + this.kbhId + "'\n" +
					"," + (this.hchId.trim().length()<=0?"NULL":this.hchId)+ ",'" + this.nppId + "'," + this.ghcnId + ", '" + this.sodienthoai + "'\n" +
					",N'" + this.diachi + "','"+ this.tpId + "','"+ this.qhId + "', N'" + masothue + "'\n" +
					","+this.xuatkhau+ ","+ this.thanhtoan + ", '" + this.thanhtoanQUY + "', '"+this.hopdong+"'\n" +
					", " + _ck + ","+this.mauhd+","+this.khoId+",N'"+this.tenkyhd +"'\n" +
					",'"+this.ngaysinh+"','"+this.mst+"','"+this.cmnd+"', "+(this.thoihanno.trim().length()<=0?"NULL":this.thoihanno)+"\n" +
					",'"+this.ngaykyHd+"', '" + this.cokhuyenmai + "',"+this.dungmau+", " + this.soHieuTaiKhoan + ") ";
			
			System.out.println("query: "+ query);
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
			
			if(this.nvgnId!=null && this.nvgnId.length()>0)
			{
				query = "insert nvgn_kh(nvgn_fk, khachhang_fk) select pk_Seq as nvgnId,'"+this.id+"' as khId from nhanviengiaonhan where pk_Seq in ("+this.nvgnId+") ";
				if(!db.update(query))
				{
					this.msg="Khong the cap nhat nhanviengiaonhan cho khach hang nay";
					db.getConnection().rollback();
					return false;
				}
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
			//Update khách hàng vưa insert vào cot smartid giá trị tự động tăng theo nhà phân phối
			
			//query ="select  isnull(max(smartid),0) as mamax from khachhang kh where kh.npp_fk="+this.nppId;
			query = "select isnull(max(cast (smartid as numeric(18,0))),0) as mamax from khachhang kh where kh.npp_fk ="+this.nppId;
			 rs=this.db.get(query);
			 if(rs!=null){
				 if(rs.next()){
					 String sql_update_smartid="update khachhang set smartid='"+(rs.getLong("mamax") +1)+"' where pk_seq=" + this.id;
						if(!db.update(sql_update_smartid))
						{
							db.getConnection().rollback();
							this.msg = "Khong The Thuc hien Cap Nhat SmartId ,Vui Long Lien He Voi Admin De Sua Loi Nay";
							return false;
						}

				 }
				 rs.close();
			 }
			 
				/*
	    	 * 0 :Chi Nhanh cap 1 
	    	 * 1 :Chi Nhanh cap 2
	    	 * 2 : Quầy bán buôn
	    	 * 3 : Quầy TraphacoDMS
	   	   * 4 : Doi tac
	   	   * 5: Chi nhanh doi tac
	    	 */
			 Utility util = new Utility();
			 if(util.getLoaiNpp().equals("2")||util.getLoaiNpp().equals("3"))
			 {
					 /*********************Tạo mới KH tự cập nhật vào tuyến**************************/
					 query=
							"  insert into KHACHHANG_TUYENBH(KHACHHANG_FK,SOTT,TANSO,TBH_FK)  "+
							" select kh.PK_SEQ as khId,   "+
							" 	isnull   "+
							" 	(   "+
							" 		(   "+
							" 			select MAX(sott) from KHACHHANG_TUYENBH a  where a.KHACHHANG_FK=kh.PK_SEQ   "+ 
							" 			and a.TBH_FK =tbh.PK_SEQ   "+
							" 		),0    "+
							" 	) + 1 as SoTT, 'F4' as TanSo, tbh.PK_SEQ as TbhId  "+
							" from KHACHHANG kh, TUYENBANHANG tbh   "+
							" where tbh.NPP_FK = '" + this.nppId + "' and kh.pk_seq = '" + this.id + "'  "+
							"   and kh.NPP_FK in ( select pk_seq from NHAPHANPHOI where loaiNPP in ( 2, 3 ) ) ";
					 if(!db.update(query))
					 {
							this.msg="Lỗi cập nhật "+query ;
							db.getConnection().rollback();
							return false;
						}
			 }
			 
			 if(!util.getLoaiNpp().equals("2")||!util.getLoaiNpp().equals("3"))
			 {
				 String[] tanso = request.getParameterValues("tanso");
				 String[] sott = request.getParameterValues("sott");
				 if(tanso!=null)
				 {
						 for(int i=0;i<tanso.length;i++)
						 {
							String tbhId_ = request.getParameter("tbhId_"+i);
							 if(tbhId_!=null )
							 {
								 query=
											"  insert into KHACHHANG_TUYENBH(KHACHHANG_FK,SOTT,TANSO,TBH_FK)  "+
											" select  "+this.id+",'"+sott[i]+"','"+tanso[i]+"','"+tbhId_+"' "+
											"  where NOT EXISTS "+
											"  (  "+
											"		select 1 from KHACHHANG_TUYENBH where KHACHHANG_FK='"+this.id+"'  "+
											"		and TBH_FK='"+tbhId_+"' and TANSO='"+tanso[i]+"' AND SOTT= '"+sott[i]+"'  "+
											 "  )  ";
								 
								 		System.out.println("___"+query);
								 		
									 if(!db.update(query))
									 {
											this.msg="Lỗi cập nhật "+query ;
											db.getConnection().rollback();
											return false;
										}
							 }
						 }
				 	}
			 }
			 query= 
			"		 select KHACHHANG_FK,a.tbh_fk,  "+
			"			(   "+
			"				select count (*) from KHACHHANG_TUYENBH  inner join TUYENBANHANG on TUYENBANHANG.PK_SEQ=KHACHHANG_TUYENBH.TBH_FK  "+
			"				where KHACHHANG_TUYENBH.KHACHHANG_FK=a.KHACHHANG_FK  AND len(ltrim(rtrim(KHACHHANG_TUYENBH.TANSO)))=0  and  TUYENBANHANG.DDKD_FK=b.DDKD_FK  "+
			"				group by KHACHHANG_FK,TUYENBANHANG.DDKD_FK	  "+ 
			"			) as SoDong,b.DDKD_FK,a.TANSO as TanSoOld, "+
			"			sum(b.NgayId) OVER( PARTITION BY DDKD_FK,KHACHHANG_FK) as TongNgay,b.NGAYID   "+
			"		from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on b.PK_SEQ=a.TBH_FK    "+
			"		where a.KHACHHANG_FK='"+this.id+"' and len(ltrim(rtrim(a.TANSO)))=0 ";
			 
			 System.out.println("_____"+query);
			 
						rs =db.get(query);
						
						while(rs!=null&&rs.next())
						{
							int SoDong =rs.getInt("SoDong");
							String TanSoNew = "";
							if(SoDong==1)
							{
								TanSoNew="F4";
							}else if(SoDong==2)
							{
								TanSoNew="F8";
							}if(SoDong==3)
							{
								if( ( rs.getInt("TongNgay")%2)==0 && (rs.getInt("NGAYID")%2)==0 )
								TanSoNew="F12-2";
								else if( ( rs.getInt("TongNgay")%2)!=0 && (rs.getInt("NGAYID")%2)!=0 )
								{
									TanSoNew="F12-1";
								}
								else 
								{
									this.msg="Vui lòng chọn ngày viếng thăm hợp lệ!";
									db.getConnection().rollback();
									return false;
								}
							}
							String TanSoOld = rs.getString("TanSoOld");
							String tbhId = rs.getString("tbh_fk");
							query="update KHACHHANG_TUYENBH set tanso='"+TanSoNew+"' where khachhang_fk='"+this.id+"' and tanso='"+TanSoOld+"' and tbh_fk='"+tbhId+"' ";
							 if(!db.update(query))
							 {
									this.msg="Lỗi cập nhật "+query ;
									db.getConnection().rollback();
									return false;
								}
						}	
						
			query = "update KHACHHANG set timkiem = dbo.ftBoDau(maFAST + '-' + isnull(ten, '') + '-' + isnull(diachi, ''))  where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg="Lỗi cập nhật "+query ;
				db.getConnection().rollback();
				return false;
			}			
			
			
			String check=" select d.KHACHHANG_FK, COUNT(d.DDKD_FK) as num from  \n"+
					 "	 (  \n"+ 
					 "	  select distinct k.KHACHHANG_FK, t.DDKD_FK  \n"+
					 "	  from KHACHHANG_TUYENBH k inner join TUYENBANHANG t on k.TBH_FK = t.PK_SEQ  \n"+
					 "	  where exists (select 1 from DAIDIENKINHDOANH where PK_SEQ = t.DDKD_FK and TRANGTHAI = 1)  \n"+
					 "	 ) d   \n"+
					 "	 where exists (select 1 from KHACHHANG where PK_SEQ = d.KHACHHANG_FK and TRANGTHAI = 1)   \n"+
					 "	 group by d.KHACHHANG_FK having COUNT(d.DDKD_FK) > 1";
		ResultSet rscheck=db.get(check);
			if(rscheck.next())
			{
				db.update("rollback");
				this.msg = "tồn tại một khách hàng thuộc 2 trình dược viên";
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
	
	private boolean KiemTraKhachHangTrunng() {
		try{
			String sql="select pk_seq from khachhang where diachi =N'"+this.diachi+"'";
			
			if(this.id.length() > 0){
				sql=sql+ " and pk_seq <> "+this.id;
			}
		
			ResultSet rs=db.get(sql);
			int bien=0;
			if(rs.next()){
				bien=1;
			}
			rs.close();
			if(bien>0){
				return true;
			}
		}catch(Exception er){
			er.printStackTrace();
			return true;
		}
		
		return false;
	}
	
	
	private boolean KiemTraKhachHangTrunngMa_Fast() {
		try{
			// ---- KIỂM TRA MÃ FAST + NPP_FK LÀ DUY NHẤT ---- //
			String sql="select pk_seq from khachhang where 	 MaFast=N'"+this.maFAST.trim()+"' and  NPP_FK='"+this.nppId+"'";   
			
			if(this.id.length() > 0){
				sql=sql+ " and pk_seq <> "+this.id;
			}
			System.out.println("2. nhapp:  "+ this.nppId);
			System.out.println(" Query kiem tra trung ma fast: "+ sql);
			ResultSet rs=db.get(sql);
			int bien=0;
			if(rs.next()){
				bien=1;
			}
			rs.close();
			if(bien>0){
				return true;
			}
		}catch(Exception er){
			er.printStackTrace();
			return true;
		}
		
		return false;
	}

	public boolean UpdateKh(HttpServletRequest request) 
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
		
		
		/*if( this.KiemTraKhachHangTrunng() && this.type.trim().equals("0") )
		{
			this.msg="Địa chỉ này đã trùng,vui lòng kiểm tra lại";
			this.type="1";
			return false;
		}*/
		
		if(KiemTraKhachHangTrunngMa_Fast())
		{
			this.msg="Mã FAST này đã trùng,vui lòng kiểm tra lại";
			this.type="1";
			return false;
		}
		
		if(this.hopdong.trim().length()<=0)
			this.kokyhd="1";
	else 
		this.kokyhd="0";
		
		
		if(this.dtId=="")
		this.dtId=null;;
		
		try
		{
			this.db.getConnection().setAutoCommit(false);
			
			String _ck = "NULL";
			if(this.pt_chietkhau.trim().length() > 0)
				_ck = this.pt_chietkhau.replaceAll(",", "");
			
			if(this.khoId=="") this.khoId = null;
			if(this.mauhd=="") this.mauhd=null;
			
			// KH OTC: KH CỦA CN HÀ NỘI DÙNG MẪU HÓA ĐƠN 2(HO), CÒN LẠI : MẪU HÓA ĐƠN 1(CN) 
			// KH ETC: KH CỦA CN HÀ NỘI DÙNG MẪU HÓA ĐƠN 2(HO), CN HCM : TÙY CHỌN TRONG DLN , CÒN LẠI: DÙNG MẪU 1
			if( this.kbhId.equals("100025") && this.nppId.equals("100002")) this.mauhd = "2" ;
			if( this.kbhId.equals("100025") && !this.nppId.equals("100002")) this.mauhd = "1" ;
			if( this.kbhId.equals("100052") && this.nppId.equals("100002")) this.mauhd = "2" ;
			if( this.kbhId.equals("100052") && !this.nppId.equals("100002") && !this.nppId.equals("106210")) this.mauhd = "1" ;
			
			System.out.println("Hang cua hang: '" + this.hchId + "' ");
			
			if(this.bgstId.length() > 0)
				query = "update khachhang set NgayKyHD='"+this.ngaykyHd+"',NgaySinh='"+this.ngaysinh+"',MST_CaNhan='"+this.mst+"',TrucThuoc_fk="+this.dtId+"\n" +
						",MaHD='"+this.hopdong+"',maFAST = N'" + this.maFAST + "', chucuahieu = N'" + this.chucuahieu +  "', KhongKyHopDong = '" + this.kokyhd + "'\n" +
						", DiaDiem_FK="+(this.diadiemId.trim().length()<=0?"NULL":this.diadiemId)+",XuatKhau="+this.xuatkhau+",ten=N'" + this.ten + "', diachi=N'" + this.diachi + "'\n" +
						", ghcn_fk=" + this.ghcnId + ", chietkhau_fk=" + this.mckId + ", dienthoai='" + this.sodienthoai + "', kbh_fk='" + this.kbhId + "'\n" +
						", hch_fk=" +(this.hchId.trim().length()<=0?"NULL":this.hchId) + ", bgst_fk='" + this.bgstId + "', ngaysua = '" + this.ngaysua + "', trangthai = '" + this.trangthai + "'\n" +
						", nguoisua='" + this.nguoisua + "', tinhthanh_fk='" + this.tpId + "', quanhuyen_fk='" + this.qhId + "'\n" +
						", masothue = N'" + this.masothue +  "', thanhtoan = " + this.thanhtoan + ", THANHTOANQUY = '" + this.thanhtoanQUY + "', PT_CHIETKHAU = " + _ck + "\n" +
						", MAUHOADON ="+this.mauhd+", KHO_FK ="+this.khoId+", TenKyHd =N'"+this.tenkyhd+"', cmnd='"+this.cmnd+"'\n" +
						", thoihanno ="+(this.thoihanno.trim().length()<=0?"NULL":this.thoihanno)+", cokhuyenmai = '" + this.cokhuyenmai + "',mauho="+this.dungmau+"  \n" +
						", taiKhoan_FK = " + this.soHieuTaiKhoan + "\n" +
						"where pk_seq = '" + this.id + "'" ;
			else
				query = "update khachhang set NgayKyHd='"+this.ngaykyHd+"',NgaySinh='"+this.ngaysinh+"',MST_CaNhan='"+this.mst+"',TrucThuoc_fk="+this.dtId+",MaHD='"+this.hopdong+"',maFAST = N'" + this.maFAST + "', chucuahieu = N'" + this.chucuahieu + "', KhongKyHopDong = '" + this.kokyhd + "', DiaDiem_FK="+(this.diadiemId.trim().length()<=0?"NULL":this.diadiemId)+",XuatKhau="+this.xuatkhau+",ten=N'" + this.ten + "', diachi=N'" + this.diachi + "', ghcn_fk=" + this.ghcnId + ", chietkhau_fk=" + this.mckId + ", dienthoai='" + this.sodienthoai + "', kbh_fk='" + this.kbhId + "', hch_fk="+ (this.hchId.trim().length()<=0?"NULL":this.hchId)+ ",  ngaysua = '" + this.ngaysua + "', trangthai = '" + this.trangthai + "', nguoisua='" + this.nguoisua + "', tinhthanh_fk='" + this.tpId + "', quanhuyen_fk='" + this.qhId + "', masothue = '" + this.masothue +  "', thanhtoan = " + this.thanhtoan + ", THANHTOANQUY = '" + this.thanhtoanQUY + "', PT_CHIETKHAU = " + _ck + ", MAUHOADON ="+this.mauhd+", KHO_FK ="+this.khoId+ ", TenKyHd =N'"+this.tenkyhd+"',cmnd='"+this.cmnd+"', thoihanno = "+(this.thoihanno.trim().length()<=0?"NULL":this.thoihanno)+", cokhuyenmai = '" + this.cokhuyenmai + "' ,mauho="+this.dungmau+"  \n" +
						", taiKhoan_FK = " + this.soHieuTaiKhoan + "\n" +
						"where pk_seq = '" + this.id + "'" ;
			
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat table 'Khach Hang' , " + query;
				return false; 
			}
			
			query = "delete bgst_khachhang where khachhang_fk='"+this.id+"'";
			if(!this.db.update(query))
			{
				this.db.getConnection().rollback();
				this.msg = "Khong the cap nhat table 'Khach Hang' , " + query;
				return false;
			}
			
			query = "delete from khachhang_nkhachhang where khachhang_fk= '" + this.id + "'" ;
			if(!this.db.update(query))
			{
				db.update("rollback");
				this.msg = "Loi khi cap nhat bang "+query;
				return false; 
			}
					
			// Update table "khachhang_nkhachhang"
			if (this.nkh_khIds != null)
			{
				int size = (this.nkh_khIds).length;
				int m = 0;
				while(m < size)
				{
					query = "insert into khachhang_nkhachhang values('" + this.nkh_khIds[m] + "','" + this.id + "')"; 				
					if(!this.db.update(query))
					{
						this.msg="Loi trong qua trinh insert,vui long thuc hien lai,neu khong duoc vui lien he voi admin GESO de duoc huong dan .Loi dong lenh :" +query;
						this.db.getConnection().rollback();
						return false;
					}
					
					m++;
				}
			}
			
			query = "delete nvgn_kh where khachhang_fk = '" + this.id + "'";
			if(!this.db.update(query))
			{
				db.update("rollback");
				this.msg = "Loi khi cap nhat bang "+query;
				return false; 
			}
			
	
			if(this.nvgnId!=null && this.nvgnId.length()>0)
			{
				query = "insert nvgn_kh(nvgn_fk, khachhang_fk) select pk_Seq as nvgnId,'"+this.id+"' as khId from nhanviengiaonhan where pk_Seq in ("+this.nvgnId+") ";
				if(!db.update(query))
				{
					this.msg="Khong the cap nhat nhanviengiaonhan cho khach hang nay";
					db.getConnection().rollback();
					return false;
				}
			}
			
			
			query="delete from KHACHHANG_TUYENBH where KHACHHANG_FK='"+this.id+"' ";
			 if(!db.update(query))
			 {
					this.msg="Lỗi cập nhật "+query ;
					db.getConnection().rollback();
					return false;
				}

				 String[] tanso = request.getParameterValues("tanso");
				 String[] sott = request.getParameterValues("sott");
				 if(tanso!=null)
				 {
						 for(int i=0;i<tanso.length;i++)
						 {
							String tbhId_ = request.getParameter("tbhId_"+i);
							 if(tbhId_!=null )
							 {
								 query=
										 "  insert into KHACHHANG_TUYENBH(KHACHHANG_FK,SOTT,TANSO,TBH_FK)  "+
													" select  "+this.id+",'"+sott[i]+"','"+tanso[i]+"','"+tbhId_+"' "+
													"  where NOT EXISTS "+
													"  (  "+
													"		select 1 from KHACHHANG_TUYENBH where KHACHHANG_FK='"+this.id+"'  "+
													"		and TBH_FK='"+tbhId_+"' and TANSO='"+tanso[i]+"' AND SOTT= '"+sott[i]+"'  "+
													 "  )  ";
									 if(!db.update(query))
									 {
											this.msg="Lỗi cập nhật "+query ;
											db.getConnection().rollback();
											return false;
										}
							 }
						 }
				 	}
			 query= 
						"		 select KHACHHANG_FK,a.tbh_fk,  "+
								"			(   "+
								"				select count (*) from KHACHHANG_TUYENBH  inner join TUYENBANHANG on TUYENBANHANG.PK_SEQ=KHACHHANG_TUYENBH.TBH_FK  "+
								"				where KHACHHANG_TUYENBH.KHACHHANG_FK=a.KHACHHANG_FK  AND len(ltrim(rtrim(KHACHHANG_TUYENBH.TANSO)))=0  and  TUYENBANHANG.DDKD_FK=b.DDKD_FK  "+
								"				group by KHACHHANG_FK,TUYENBANHANG.DDKD_FK	  "+ 
								"			) as SoDong,b.DDKD_FK,a.TANSO as TanSoOld, "+
								"			sum(b.NgayId) OVER( PARTITION BY DDKD_FK,KHACHHANG_FK) as TongNgay,b.NGAYID   "+
								"		from KHACHHANG_TUYENBH a inner join TUYENBANHANG b on b.PK_SEQ=a.TBH_FK    "+
								"		where a.KHACHHANG_FK='"+this.id+"' and len(ltrim(rtrim(a.TANSO)))=0 ";
								 
			 
				ResultSet		rs =db.get(query);
						while(rs.next())
						{
							int SoDong =rs.getInt("SoDong");
							String TanSoNew = "";
							if(SoDong==1)
							{
								TanSoNew="F4";
							}else if(SoDong==2)
							{
								TanSoNew="F8";
							}if(SoDong==3)
							{
								if( ( rs.getInt("TongNgay")%2)==0 && (rs.getInt("NGAYID")%2)==0 )
								TanSoNew="F12-2";
								else if( ( rs.getInt("TongNgay")%2)!=0 && (rs.getInt("NGAYID")%2)!=0 )
								{
									TanSoNew="F12-1";
								}
								else 
								{
									this.msg="Vui lòng chọn ngày viếng thăm hợp lệ!";
									db.getConnection().rollback();
									return false;
								}
							}
							String TanSoOld = rs.getString("TanSoOld");
							String tbhId = rs.getString("tbh_fk");
							query="update KHACHHANG_TUYENBH set tanso='"+TanSoNew+"' where khachhang_fk='"+this.id+"' and tanso='"+TanSoOld+"' and tbh_fk='"+tbhId+"' ";
							 if(!db.update(query))
							 {
									this.msg="Lỗi cập nhật "+query ;
									db.getConnection().rollback();
									return false;
								}
						}		
			
						
			//CHECK XEM DA CO DON HANG CHUA, NEU DA CO THI KHONG DUOC PHEP DOI LOAI KHACH HANG TRONG THANG HIEN TAI
			String dauthangHT = this.getDateTime().split("-")[0] + "-" + this.getDateTime().split("-")[1] + "-01";
			String cuoithangHT = this.getDateTime().split("-")[0] + "-" + this.getDateTime().split("-")[1] + "-" + this.getngayCUOITHANG(this.getDateTime());
						
			query = "select distinct loaikhachhang from DONHANG where khachhang_fk = '" + this.id +  "' " +
					"and trangthai != 2 and ngaynhap >= '" + dauthangHT + "' and ngaynhap <= '" + cuoithangHT + "' ";	
			
			System.out.println("__"+query);
			
			String loaiKHOLD = "";
			rs = db.get(query);
			if(rs != null)
			{
				if(rs.next())
				{
					loaiKHOLD = rs.getString("loaikhachhang");
				}
				rs.close();
			}
			
			if(loaiKHOLD.trim().length() > 0)
			{
				if(!loaiKHOLD.equals(this.xuatkhau))
				{
					this.msg = "Khách hàng đã phát sinh đơn hàng trong tháng hiện tại. Bạn không thể đổi loại của khách hàng này";
					db.getConnection().rollback();
					return false;
				}
			}
					
			query = "update KHACHHANG set timkiem = dbo.ftBoDau(maFAST + '-' + isnull(ten, '') + '-' + isnull(diachi, ''))  where pk_seq = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg="Lỗi cập nhật "+query ;
				db.getConnection().rollback();
				return false;
			}	
			
			String check=" select d.KHACHHANG_FK, COUNT(d.DDKD_FK) as num from  \n"+
					 "	 (  \n"+ 
					 "	  select distinct k.KHACHHANG_FK, t.DDKD_FK  \n"+
					 "	  from KHACHHANG_TUYENBH k inner join TUYENBANHANG t on k.TBH_FK = t.PK_SEQ  \n"+
					 "	  where exists (select 1 from DAIDIENKINHDOANH where PK_SEQ = t.DDKD_FK and TRANGTHAI = 1)  \n"+
					 "	 ) d   \n"+
					 "	 where exists (select 1 from KHACHHANG where PK_SEQ = d.KHACHHANG_FK and TRANGTHAI = 1)   \n"+
					 "	 group by d.KHACHHANG_FK having COUNT(d.DDKD_FK) > 1";
		ResultSet rscheck=db.get(check);
			if(rscheck.next())
			{
				db.update("rollback");
				this.msg = "tồn tại một khách hàng thuộc 2 trình dược viên";
				return false;
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			
		}
		catch(Exception er)
		{
			this.msg="Loi trong qua trinh update,vui long thuc hien lai,neu khong duoc vui lien he voi admin GESO de duoc huong dan .Loi dong lenh :" +er.toString();
			this.db.update("rollback");
			er.printStackTrace();
			return false;
		}
		return true;
	}
	
	private String getngayCUOITHANG(String ngaygiaodich) 
	{
        String[] arr = ngaygiaodich.split("-");
        String ngaycuoithang = "";
        
        switch ( Integer.parseInt(arr[1]) ) {
	        case 1:
	        case 3:
	        case 5:
	        case 7:
	        case 8:
	        case 10:
	        case 12:
	        	ngaycuoithang    = "31";
	            break;
	        case 4:
	        case 6:
	        case 9:
	        case 11:
	        	ngaycuoithang    = "30";
	            break;
	        case 2:
	            if( Integer.parseInt(arr[0]) % 100 != 0 && Integer.parseInt(arr[0]) % 4 == 0 ) {
	            	ngaycuoithang    = "29";
	            } else {
	            	ngaycuoithang    = "28";
	            }
	            break;
	        default: ngaycuoithang    = "28";
	    } 

        return ngaycuoithang;
  
	}
	
	
	public void init() 
	{
		String query = 
				"select a.maHD as HopDong,a.cmnd,isnull(a.thanhtoan,'0') as thanhtoan ,  a.pk_seq as khId, a.smartid,a.ten as khTen, a.trangthai, a.ngaytao, a.ngaysua, isnull(a.masothue, '') as masothue, b.ten as nguoitao, c.ten as nguoisua, d.chietkhau, d.pk_seq as mckId, isnull(a.tenkyhd,'') tenkyhd, \n"+
		    	" 	e.diengiai as kbhTen, e.pk_seq as kbhId, f.hang as hchTen, f.pk_seq as hchId, g.loai as lchTen, g.pk_seq as lchId, h.ten as nppTen, h.pk_seq as nppId, a.mauhoadon, \n"+
		    	"		k.sotienno as ghcnTen, k.pk_seq as ghcnId, l.ten as bgstTen, l.pk_seq as bgstId, m.vitri as vtchTen, m.pk_seq as vtchId, a.dienthoai, a.diachi, a.tinhthanh_fk as tpId, a.quanhuyen_fk as qhId,a.DiaDiem_FK as DiaDiemId,isnull(a.XuatKhau,0) as XuatKhau, KhongKyHopDong,isnull(a.MaFast,'')  as MaFast, isnull(chucuahieu, '') as chucuahieu, THANHTOANQUY, a.PT_CHIETKHAU,a.TrucTHUOC_FK, a.kho_fk, \n"+
			  	"	STUFF      \n"+
					"	(    \n"+
					"		(   \n"+
					"    select DISTINCT TOP 100 PERCENT ' , ' +cast( tbh.nvgn_fk as nvarchar(18) )   		\n"+
					"			from nvgn_kh tbh \n"+
					"     where khachhang_fk=a.pk_seq                  \n"+ 
					"  ORDER BY ' , ' +cast( tbh.nvgn_fk as nvarchar(18) )   	\n"+
					"			FOR XML PATH('')      \n"+
					"		 ), 1, 2, ''   \n"+
					"	) + ' '  AS nvgnId,a.Mst_CaNHAN,A.NgaySinh, a.thoihanno,a.NgayKyHD, a.cokhuyenmai,isnull(a.mauho,0) mauho \n" +
				", isNull(convert(nvarchar, a.taiKhoan_FK), '') taiKhoan_FK\n"+
			    "from khachhang a LEFT join nhanvien b on a.nguoitao = b.pk_seq LEFT join nhanvien c on a.nguoisua = c.pk_seq left join mucchietkhau d on a.chietkhau_fk = d.pk_seq \n"+
		    	"left join kenhbanhang e on a.kbh_fk = e.pk_seq left join hangcuahang f on a.hch_fk = f.pk_seq left join loaicuahang g on a.lch_fk = g.pk_seq \n"+
		    	"inner join nhaphanphoi h on a.npp_fk = h.pk_seq left join gioihancongno k on a.ghcn_fk = k.pk_seq left join banggiasieuthi l on a.bgst_fk = l.pk_seq \n"+
		    	" left join vitricuahang m on a.vtch_fk = m.pk_seq where a.pk_seq='" + this.id + "' ";
		
	    System.out.println("---INIT KHACH HANG: " + query);
	    
        ResultSet rs =  this.db.get(query);
        try{
            rs.next();
            this.soHieuTaiKhoan = rs.getString("taiKhoan_FK");
        	this.id = rs.getString("khId");
        	this.ten = rs.getString("khTen");
        	if(rs.getString("dienthoai")!=null)
        	{	
        		this.sodienthoai = rs.getString("dienthoai");
        	}
        	else
        	{
        		this.sodienthoai = "";
        	}
        	this.masothue = rs.getString("masothue");
        	this.diachi = rs.getString("diachi");
        	this.dungmau=rs.getString("mauho");
        	this.mauhd = rs.getString("mauhoadon")==null?"": rs.getString("mauhoadon");
        	this.ngaykyHd = rs.getString("ngaykyHd")==null?"": rs.getString("ngaykyHd");
        	this.tpId = rs.getString("tpId");
        	this.qhId = rs.getString("qhId");
        	
        	this.trangthai = rs.getString("trangthai");
        	this.ngaytao = rs.getString("ngaytao");
        	this.nguoitao = rs.getString("nguoitao");
        	this.ngaysua = rs.getString("ngaysua");
        	this.nguoisua = rs.getString("nguoisua");
        	this.mck = rs.getString("chietkhau");
        	this.mckId = rs.getString("mckId");
        	this.tenkyhd = rs.getString("tenkyhd");
        	this.kbh = rs.getString("kbhTen");
        	this.kbhId = rs.getString("kbhId");
        
        	this.hch = rs.getString("hchTen");
        	 this.hchId = rs.getString("hchId")==null?"":rs.getString("hchId");
        
        	this.lch = rs.getString("lchTen");
        	this.lchId = rs.getString("lchId");
        	this.ghcn = rs.getString("ghcnTen");
        
        	this.ghcnId = rs.getString("ghcnId");
        	this.bgst = rs.getString("bgstTen");
        	this.bgstId = rs.getString("bgstId");
        
        	this.vtch = rs.getString("vtchTen");
        	this.vtchId = rs.getString("vtchId");
        
        	this.nvgnId = rs.getString("nvgnId")==null?"":rs.getString("nvgnId");
        	
        	
        	this.diadiemId = rs.getString("diadiemId")==null?"":rs.getString("diadiemId");
        	this.hopdong = rs.getString("hopdong")==null?"":rs.getString("hopdong");
        	
        	this.xuatkhau = rs.getString("xuatkhau")==null?"0":rs.getString("xuatkhau");
        	this.pt_chietkhau = rs.getString("PT_CHIETKHAU")==null?"":rs.getString("PT_CHIETKHAU");
        	
        	this.dtId = rs.getString("tructhuoc_fk")==null?"":rs.getString("tructhuoc_fk");
        	
        	this.ngaysinh = rs.getString("ngaysinh")==null?"":rs.getString("ngaysinh");
        	this.mst = rs.getString("Mst_CaNHAN")==null?"":rs.getString("Mst_CaNHAN");
        	
        	this.kokyhd = rs.getString("KhongKyHopDong");
        	this.maFAST = rs.getString("maFAST");
        	this.thanhtoan = rs.getString("thanhtoan")==null?"":rs.getString("thanhtoan");
        	this.thanhtoanQUY = rs.getString("THANHTOANQUY")==null?"":rs.getString("THANHTOANQUY");
        	this.chucuahieu = rs.getString("chucuahieu");
        	this.khoId = rs.getString("kho_fk") == null ? "" : rs.getString("kho_fk");
        	this.cmnd= rs.getString("cmnd") == null ? "" : rs.getString("cmnd");
        	this.thoihanno =  rs.getString("thoihanno") == null ? "0" : rs.getString("thoihanno");
        	
        	this.cokhuyenmai = rs.getString("cokhuyenmai");
        	
        	query=
              "select b.TBH_FK,b.KHACHHANG_FK,a.DDKD_FK from TUYENBANHANG a inner join KHACHHANG_TUYENBH b on b.TBH_FK=a.PK_SEQ "+
              "where b.KHACHHANG_FK='"+this.id+"' ";
        	
        	System.out.println("__________"+query);
        	
        	this.ddkdId="";
        	this.tbhId="";
        	
               rs=db.get(query);
               int i=0;
               while(rs.next())
               {
              	 if(i==0)
              	 {
              		 this.ddkdId +=rs.getString("DDKD_FK");
              		 this.tbhId +=rs.getString("TBH_FK");
              	 }
              	 else
              	 {
              		 this.ddkdId +=  "," +rs.getString("DDKD_FK");
              		 this.tbhId +="," + rs.getString("TBH_FK")+",";
              	 }
              	 i++;
               }
       	}
        
        
        
        catch(Exception e)
        {
        	e.printStackTrace();
        	this.msg="Loi Trong Qua Trinh Lay Du Lieu ."+ e.toString();
        }
        finally
        {
        	try 
        	{
        		if(rs != null)
        			rs.close();
        	} catch (Exception e2) 
        	{
        		e2.printStackTrace();
        	}
        }
        
         
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
			e.printStackTrace();
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
	
	public String getDungmau() {
		return dungmau;
	}

	public void setDungmau(String dungmau) {
		this.dungmau = dungmau;
	}
	
	public String getCmnd() {
		return cmnd;
	}

	public void setCmnd(String cmnd) {
		this.cmnd = cmnd;
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

	public void setSoHieuTaiKhoan(String SoHieuTaiKhoan) {
		this.soHieuTaiKhoan = SoHieuTaiKhoan;
	}

	public String getSoHieuTaiKhoan() {
		return soHieuTaiKhoan;
	}

	public void setTaiKhoanIdList(List<Erp_Item> taiKhoanIdList) {
		this.taiKhoanIdList = taiKhoanIdList;
	}

	public List<Erp_Item> getTaiKhoanIdList() {
		return taiKhoanIdList;
	}
}