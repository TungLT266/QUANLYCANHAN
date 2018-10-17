package geso.traphaco.erp.beans.thongtinsanphamgiay.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IBundle;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IErp_TaiSanCoDinh;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IHoaChat_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IMayMoc_SanPham;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IQuyCachSanPhamList;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IQuyDoi;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinNCC;
import geso.traphaco.erp.beans.thongtinsanphamgiay.IThongtinsanphamgiay;
import geso.traphaco.erp.beans.thongtinsanphamgiay.ITieuchikiemdinh;
import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.util.UtilitySyn;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Hashtable;

public class Thongtinsanphamgiay implements IThongtinsanphamgiay 
{
	String ctyId;
	String ctyTen;
	String is_khongthue;
	String userId;
	String id;
	String masp;
	String ten;
	String tennoibo;
	String ischietkhau;
	String tenthuongmai="";
	public String getTenthuongmai() {
		return tenthuongmai;
	}

	public void setTenthuongmai(String tenthuongmai) {
		this.tenthuongmai = tenthuongmai;
	}

	String hinhanh;
	String hancongboTP;
	String hinhcongboTP;
	String filenamecbTP;
	
	String dvdlId;
	String dvdl_qcIds;
	String dvdlTen;
	ResultSet dvdl;
	
	String dvkdId;
	String dvkdTen;
	ResultSet dvkd;
	
	String nhId;
	String nhTen;
	ResultSet nh;
	
	String clId;
	String clTen;
	ResultSet cl;
	
	String CongTy, GiaMua, HanSuDung, LoaiGiaTon;
	ResultSet CongTyRs;
	
	String trangthai;
	String giablc;
	String msg;
	
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	
	String nspIds;
	ResultSet nsp;
	
	ResultSet loaispRs;
	String loaispId;
	String loaispma;	
	String Songayhancanhbao;
	String thueSuat;

	String[] sl1;
	String[] dvdl1;
	String[] sl2;
	String[] dvdl2;
	String[] thoihangiaohang;
	String[] luongdattoithieu;
	String[] nhacungcap;
	String[] hinhcongbo;
	String[] filenamecb;
	String[] hancongbo;
	String[] khovung;
	String[] bantp;
	
	String type;
	
	String spIds;
	String[] spStt;
	ResultSet spList;
	
	//String luongdattoithieu;
	//String thoigianchogiaohang;
	String tonkhoantoan;
	
	ResultSet packsizeRs;
	String packsizeId;
	
	String cpnc, cpvc;
	
	String Check_Vothoihan;
	

	String kiemtradinhtinh;
	List<ITieuchikiemdinh> tckdDinhtinhList;
	String kiemtradinhluong;
	List<ITieuchikiemdinh> tckdDinhluongList;
	String kiemtraoe;
	
	List<IThongtinNCC> ThongtinNCCList;
	
	List<IHoaChat_SanPham> hoaChatKiemDinhList;
	
	List<IMayMoc_SanPham> mayMocKiemDinhList;
	
	dbutils db;
	private String kl;
	private String dvkl;
	private String dvtt;
	private String tt;
	Hashtable<String, Integer> bundle_soluong;
	NumberFormat formatter = new DecimalFormat("#,###,###.###");
	String[] filehinh;
	
	//newtoyo cu
	String ten1;
	String ten2;
	String maMetro;
	String nguongoc;

	String SoLuongTp;
	String SoLuongBanTp;
	
	private String daiday = ""; //Chiều dài đáy của khay tam giác
	private String dvdl_daiday = ""; //Chiều dài đáy của khay tam giác

	String songayantoan, soluongsanxuat, soluongbomchuan, lamtronguyenlieu;
	List<IDanhmucvattu_SP> listDanhMuc;

	List<IQuyCachSanPhamList> listquycach;
	
	IQuyCachSanPhamList qcgiay;

	private ResultSet rsMausac;
	
	private ResultSet rsMaKeToan;
	String maketoanId;
	
	private ResultSet rsNguonGoc;
	private ResultSet rsMauIn;
	private ResultSet rsDoNen;

	private ResultSet rsBTP;
	
	private ResultSet rsPhepham;
	
	
	ResultSet nhamayRs;
	String nhamayId;

	ResultSet nhomNLRs;
	String nhomNLId;
	
	String BTPId;
	String PPId;
	
	String pathHinhanhSp = "";
	String nameHinhanhSp = "";
	private String isHangbo = "0";
	String  Batbuockiemdinh="";
	
	String dangBaoChe;
	ResultSet dangBaoCheRs;
	// tiêu chuẩn kỹ thuật
	String tckt;
	// Loại hàng hóa
	String loaiHangHoa;
	String Ngayhoanthanh="";

	//Thiết bị cân
	private String ycnlsx;
	private String motaSp;
	private String yeucaudonggoi;
	private String thietBiCan;
	private String thietBiCanKhac;
	private String dactinhkythuat;
	private String congthuchoahoc;
	private String nhomluutru;
	private String mucdonguyhiem;
	private String khuvucbaoquan;
	private String thuocloaisp;
	private String khongqlsl;
	ResultSet tscdRs;

	public Thongtinsanphamgiay(String[] param) {
		
		this.db = new dbutils();
		this.id = param[0];
		this.masp = param[1];
		this.ten = param[2];
		this.dvdlTen = param[3];
		this.dvkdTen = param[4];
		this.dvkdId = param[5];
		this.nhTen = param[6];
		this.nhId = param[7];
		this.clId = param[8];
		this.clTen = param[9];
		this.giablc = param[10];
		this.trangthai = param[11];
		this.dvdlId = param[12];
		this.ngaysua = param[13];
		this.nguoisua = param[14];
		this.tennoibo = param[15];
		this.sl1 = new String[5];
		this.sl2 = new String[5];
		this.dvdl1 = new String[5];
		this.dvdl2 = new String[5];
		this.type = param[15]; // default
		this.CongTy = param[16];
		this.LoaiGiaTon = "0";
		this.GiaMua = "0";
		this.HanSuDung = "365";
		this.loaispId = "";
		this.nspIds = "";
		this.spIds = "";
		this.packsizeId = "";
		cpnc = ""; cpvc = "";
		this.loaispma = "";
		this.BTPId="";
		this.PPId="";
		this.Songayhancanhbao="";
		this.ischietkhau = "";
		
		this.dvdl_qcIds = "100018"; //Thung
		
		this.kl = this.dvkl = this.dvtt = this.tt = "";
		
		this.msg = "";
		this.hancongboTP = "";
		this.hinhcongboTP = "";
		this.filenamecbTP = "";
		
		this.ctyId = "";
		//this.luongdattoithieu = "";
		//this.thoigianchogiaohang = "";
		this.tonkhoantoan = "";
		
		this.nhacungcap = new String[10];
		this.hancongbo = new String[10];
		this.hinhcongbo = new String[10];
		this.filenamecb = new String[10];
		this.thoihangiaohang = new String[10];
		this.luongdattoithieu = new String[10];
		this.filehinh = new String[10];
		this.khovung = new String[40];
		this.bantp = new String[20];

		this.kiemtradinhtinh = "0";
		this.kiemtradinhluong = "0";
		
		//newtoyo cu
		this.ngaysua = param[13];
		this.nguoisua = param[14];
		this.type = param[15]; // default
		// this.CongTy = param[16];
		this.LoaiGiaTon = param[17];
		this.HanSuDung = param[18];
		this.GiaMua = param[19];
		this.songayantoan = "";
		this.soluongsanxuat = "";
		this.soluongbomchuan = "";
		this.lamtronguyenlieu = "";
		this.SoLuongBanTp="1";
		this.nguongoc="";
		this.maMetro="";
		
		this.loaispId = "";
		this.nspIds = "";

		this.dvdl_qcIds = "100018"; // Thung

		this.kl = this.dvkl = this.dvtt = this.tt = "";
		this.msg = "";

		this.listDanhMuc = new ArrayList<IDanhmucvattu_SP>();

		this.tckdDinhluongList = new ArrayList<ITieuchikiemdinh>();

		this.ThongtinNCCList=new  ArrayList<IThongtinNCC>();
		this.nhomNLId = "";
		this.nhamayId = "";
		this.maketoanId = "";
		this.Batbuockiemdinh="";
		this.tckdDinhtinhList = new ArrayList<ITieuchikiemdinh>();
		this.Check_Vothoihan="";
		this.hoaChatKiemDinhList = new ArrayList<IHoaChat_SanPham>();
		this.mayMocKiemDinhList = new ArrayList<IMayMoc_SanPham>();
		this.is_khongthue = "0";
		this.dangBaoChe = "";
		this.tckt = "";
		this.loaiHangHoa = "";
		this.kiemtraoe = "";
		
		this.ycnlsx = "";
		this.motaSp = "";
		this.yeucaudonggoi = "";
		this.thietBiCan = "";
		this.thietBiCanKhac = "";
		this.dactinhkythuat = "";
		this.congthuchoahoc = "";
		this.nhomluutru = "";
		this.mucdonguyhiem = "";
		this.khuvucbaoquan = "";
		this.thuocloaisp = "";
		this.khongqlsl = "0";
	}

	public Thongtinsanphamgiay() {
		this.db = new dbutils();
		this.SoLuongBanTp="1";
		this.id = "";
		this.masp = "";
		this.ten = "";
		this.ten1 = "";
		this.ten2 = "";
		this.dvdlTen = "";
		this.dvkdTen = "";
		this.dvkdId = "";
		this.nhTen = "";
		this.nhId = "";
		this.clId = "";
		this.nguongoc="";
		this.ischietkhau = "";
		this.clTen = "";
		this.giablc = "";
		this.trangthai = "1";
		this.dvdlId = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.type = "";
		this.maMetro="";
		this.BTPId="";
		this.PPId="";
		this.kl = this.dvkl = this.dvtt = this.tt = "";
		this.Check_Vothoihan="";
		this.loaispId = "";
		this.LoaiGiaTon = "0";
		this.GiaMua = "0";
		this.HanSuDung = "90";
		this.songayantoan = "";
		this.soluongsanxuat = "";
		this.soluongbomchuan = "";
		this.lamtronguyenlieu = "";
		this.nspIds = "";
		this.Songayhancanhbao="";
		this.dvdl_qcIds = "100018";
		this.is_khongthue = "0";
		this.sl1 = new String[5];
		this.sl2 = new String[5];
		this.dvdl1 = new String[5];
		this.dvdl2 = new String[5];

		this.listDanhMuc = new ArrayList<IDanhmucvattu_SP>();

		this.tckdDinhluongList = new ArrayList<ITieuchikiemdinh>();

		this.nhomNLId = "";
		this.nhamayId = "";
		this.maketoanId = "";
		this.Batbuockiemdinh="";
		this.tckdDinhtinhList = new ArrayList<ITieuchikiemdinh>();
		
		//newtoyo
		this.db = new dbutils();
		this.ctyId = "";
		this.id = "";
		this.masp = "";
		this.ten = "";
		this.tennoibo = "";
		this.dvdlTen = "";
		this.dvkdTen = "";
		this.dvkdId = "";
		this.nhTen = "";
		this.nhId = "";
		this.clId = "";
		this.clTen = "";
		this.giablc = "";
		this.trangthai = "1";
		this.dvdlId = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.type = "";
		
		this.kl = this.dvkl = this.dvtt = this.tt = "";
		
		this.loaispId = "";
		this.CongTy = "";
		this.LoaiGiaTon = "0";
		this.GiaMua = "0";
		this.HanSuDung = "365";
		this.nspIds = "";
		this.spIds = "";
		
		this.dvdl_qcIds = "100018";
		
		this.sl1 = new String[5];
		this.sl2 = new String[5];
		this.dvdl1 = new String[5];
		this.dvdl2 = new String[5];
		this.thueSuat = "0";
		
		//this.luongdattoithieu = "";
		//this.thoigianchogiaohang = "";
		this.tonkhoantoan = "";
		this.isHangbo = "0";
		this.nhacungcap = new String[10];
		this.hancongbo = new String[10];
		this.hinhcongbo = new String[10];
		this.filenamecb = new String[10];
		this.thoihangiaohang = new String[10];
		this.luongdattoithieu = new String[10];
		this.filehinh = new String[10];
		this.khovung = new String[40];
		this.bantp = new String[20];
		
		cpnc = ""; cpvc = "";
		
		this.packsizeId = "";
		
		this.kiemtradinhtinh = "0";
		this.kiemtradinhluong = "0";
		this.hancongboTP = "";
		this.hinhcongboTP = "";
		this.filenamecbTP = "";
		this.loaispma = "";
		
		this.ThongtinNCCList=new  ArrayList<IThongtinNCC>();

		this.hoaChatKiemDinhList = new ArrayList<IHoaChat_SanPham>();
		this.mayMocKiemDinhList = new ArrayList<IMayMoc_SanPham>();
		
		this.dangBaoChe = "";
		this.tckt = "";
		this.loaiHangHoa = "";
		this.kiemtraoe = "";
		
		this.ycnlsx = "";
		this.motaSp = "";
		this.yeucaudonggoi = "";
		this.thietBiCan = "";
		this.thietBiCanKhac = "";
		this.dactinhkythuat = "";
		this.congthuchoahoc = "";
		this.nhomluutru = "";
		this.mucdonguyhiem = "";
		this.khuvucbaoquan = "";
		this.thuocloaisp = "";
		this.khongqlsl = "0";
	}

	
	public String getIs_khongthue() {
		return is_khongthue;
	}

	public void setIs_khongthue(String is_khongthue) {
		this.is_khongthue = is_khongthue;
	}

	public ResultSet getDangBaoCheRs() {
		return dangBaoCheRs;
	}

	public void setDangBaoCheRs(ResultSet dangBaoCheRs) {
		this.dangBaoCheRs = dangBaoCheRs;
	}

	public String getKiemtraoe() {
		return kiemtraoe;
	}

	public void setKiemtraoe(String kiemtraoe) {
		this.kiemtraoe = kiemtraoe;
	}

	public String getDangBaoChe() {
		return dangBaoChe;
	}

	public void setDangBaoChe(String dangBaoChe) {
		this.dangBaoChe = dangBaoChe;
	}

	public String getTckt() {
		return tckt;
	}

	public void setTckt(String tckt) {
		this.tckt = tckt;
	}

	public String getLoaiHangHoa() {
		return loaiHangHoa;
	}

	public void setLoaiHangHoa(String loaiHangHoa) {
		this.loaiHangHoa = loaiHangHoa;
	}
	
	public String getTen1() {
		return this.ten1;
	}

	public void setTen1(String ten1) {
		this.ten1 = ten1;
	}

	public String getTen2() {
		return this.ten2;
	}

	public void setTen2(String ten2) {
		this.ten2 = ten2;
	}


	/*public ResultSet createDvdlRS() {

		String query = "select * from ERP_NguonGoc where trangthai = '1'";
		////System.out.println("[Thongtinsanphamgiay.createDvdlRs] query = " + query);
		this.rsNguonGoc = this.db.getScrol(query);
		
		query = " select distinct a.mau as ten " +
				" from MAUDVKD a "+
				" inner join MAUDVKD_DVKD c on c.Maudvkd_fk = a.pk_seq "+
				" inner join DONVIKINHDOANH d on d.PK_SEQ = c.dvkd_fk "+
				(this.dvkdId != null && this.dvkdId.trim().length() > 0 ? " where d.PK_SEQ = '"+ this.dvkdId +"'" : "");
		////System.out.println("[Thongtinsanphamgiay.createDvdlRs] query = " + query);
		this.rsMausac = this.db.getScrol(query);
	
		query = "select * from MAUIN";
		////System.out.println("[Thongtinsanphamgiay.createDvdlRs] query = " + query);
		this.rsMauIn = this.db.getScrol(query);
		
		query = "select * from CHUANNEN";
		////System.out.println("[Thongtinsanphamgiay.createDvdlRs] query = " + query);
		this.rsDoNen = this.db.getScrol(query);

		query = "select pk_seq as dvdlId, donvi as dvdlTen from donvidoluong where trangthai = '1' and congty_fk = " + this.ctyId + " order by donvi";
		////System.out.println("[Thongtinsanphamgiay.createDvdlRs] query = " + query);
		ResultSet dvdlRS = this.db.getScrol(query);
		
		return dvdlRS;
	}

	private ResultSet createDvkdRS() {
		ResultSet dvkdRS = this.db
				.get("select distinct(pk_seq) as dvkdId, donvikinhdoanh as dvkdTen from donvikinhdoanh  "
						+ "where trangthai ='1' and congty_fk = "
						+ this.ctyId
						+ " order by donvikinhdoanh");

	
		return dvkdRS;
	}

	private ResultSet createNhRS() {
		ResultSet nhRS;
		if (dvkdId.length() > 0) {
			nhRS = this.db
					.get("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai = '1' and dvkd_fk = '"
							+ this.dvkdId + "'");
			System.out
					.println("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai = '1' and dvkd_fk = '"
							+ this.dvkdId + "'");
		} else {
			nhRS = this.db.get("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1' and congty_fk = " + this.ctyId);
			//System.out.println("select pk_seq as nhId, ten as nhTen from nhanhang where trangthai='1' and congty_fk = " + this.ctyId);
		}
		return nhRS;
	}

	private ResultSet createClRS()
	{
		String query = "select pk_seq as clId, ten as clTen from chungloai where congty_fk = '" + this.ctyId + "' ";
		if(this.nhId.trim().length() > 0)
			query += " and pk_seq in ( select CL_FK from NHANHANG_CHUNGLOAI where NH_FK = '" + this.nhId + "' ) ";
		return this.db.get(query);
	}

	private void createNspRS() {
		String query = " select pk_seq as nspId, ten as nspTen, diengiai from nhomsanpham "
				+ "  where trangthai = '1' and loaithanhvien = '2' and type = '0' order by ten ";

		//System.out.println("Khoi tao nhom: " + query);
		this.nsp = this.db.get(query);
	}

	public void CreateRS() 
	{
		this.dvdl = createDvdlRS();
		this.dvkd = createDvkdRS();
		this.nh = createNhRS();
		this.cl = createClRS();

		this.loaispRs = db.get("select pk_seq as loaiId, ma + ' - ' + ten as loaiTen from erp_loaisanpham where trangthai = '1' ");
		this.createNspRS();

		this.rsMaKeToan = this.db.getScrol("SELECT PK_SEQ,MA,TEN FROM ERP_MAKETOAN where trangthai=1");

		if (this.type.equals("1") && this.spList.size() <= 0) {
			String query = "select pk_seq, ma as spMa, ten as spTen, '' as soluong, '' as dongia, '' as IsSpChinh "
					+ "from Erp_SanPham where  pk_seq > 0 ";

			if (this.loaispId.trim().length() > 0)
				query += " and loaisanpham_fk = '" + this.loaispId + "'";

			ResultSet rs = db.get(query);
			if (rs != null) {
				try {
					List<IBundle> spList = new ArrayList<IBundle>();
					while (rs.next()) {
						IBundle sp = new Bundle();

						sp.setSpId(rs.getString("pk_seq"));
						sp.setSpMa(rs.getString("spMa"));
						sp.setSpTen(rs.getString("spTen"));

						spList.add(sp);

					}
					rs.close();

					this.spList = spList;
				} catch (Exception e) {
					e.printStackTrace();
					
					//System.out.println("1.Exception: " + e.getMessage());
				}
			}
		}

		this.nhamayRs = db.get("select pk_seq, tennhamay from ERP_NHAMAY where CONGTY_FK = '"+ this.ctyId + "'");
		this.nhomNLRs = db.get("select pk_seq, diengiai  from ERP_NHOMNGUYENLIEU");

	}*/

	private boolean masanpham(IQuyCachSanPhamList qc) 
	{
		String sql = "";
		
		if( this.dvkdId.equals("100000") || this.dvkdId.equals("100005") || this.clId.trim().length() <= 0 )
		{
			if(this.dvkdId.equals("100005"))
			{
				sql = 	"select count(*) as num from erp_sanpham " +
				  		"where upper(MA)= upper('" + qc.getMA() + "') and dai = '" + qc.getDai() + "' and DVDL_DAI = '" + qc.getDVDL_Dai() +
				  		"' and rong = '" + qc.getRong() + "' and DVDL_RONG = '" + qc.getDVDL_Rong() +
				  		"' and dinhluong = '" + qc.getDinhLuong() + "' and DVDL_DINHLUONG = '" + qc.getDVDL_DinhLuong() +
				  		"' and QUYCACH_NGUONGOC = N'" + qc.getNguongoc() + "' and mau = N'" + qc.getMau() + "' " +
				  		" and congty_fk = '" + this.ctyId + "' and DUONGKINHTRONG = '" + qc.getDuongkinhtrong() + "' and DVDL_DKTRONG = '" + qc.getDVDL_Dktrong() + "' and ma='"+this.masp+"' ";
			}
			else
			{
				sql = "select count(*) as num from erp_sanpham " +
					  "where upper(MA)= upper('" + qc.getMA() + "') and dai = '" + qc.getDai() +  "' and DVDL_DAI = '" + qc.getDVDL_Dai() +
					  "' and rong = '" + qc.getRong() + "' and DVDL_RONG = '" + qc.getDVDL_Rong() +
					  "' and dinhluong = '" + qc.getDinhLuong() + "' and DVDL_DINHLUONG = '" + qc.getDVDL_DinhLuong() +
					  "' and QUYCACH_NGUONGOC = N'" + qc.getNguongoc() + "' and mau = N'" + qc.getMau() + "' and congty_fk = '" + this.ctyId + "' and ma='"+this.masp+"'  ";
			}
			
			if(qc.getId().trim().length() > 0)
				sql += " and pk_seq != '" + qc.getId() + "' ";
		}
		else
		{
			if(this.dvkdId.equals("100004"))
			{
				if(this.clId.equals("100031"))  //ONG CORE
				{
					sql = "select count(*) as num from erp_sanpham " +
						  "where daunho='" +qc.getDaunho()+  "' and DVDL_DAUNHO = '" + qc.getDVDL_Daunho() +
						  "' and    logo='"+qc.getLogo()+"' and  upper(MA)= upper('" + qc.getMA() + 
						  "') and doday = '" + qc.getDoday() +  "' and DVDL_DODAY = '" + qc.getDVDL_Doday() +
						  "' and duongkinhtrong = '" + qc.getDuongkinhtrong() + "' and DVDL_DKTRONG = '" + qc.getDVDL_Dktrong() +
						  "' and logo = N'" + qc.getLogo() + "' " +
						  "and Mau = N'" + qc.getMau() + "' and MauIn = N'" + qc.getMauin() + "' and congty_fk = '" + this.ctyId + "' and ma='"+this.masp+"'  ";
					if(qc.getId().trim().length() > 0)
						sql += " and pk_seq != '" + qc.getId() + "' ";
				}
				else
				{
					if(this.clId.equals("100040"))  //ONG CONE
					{
						sql = "select count(*) as num from erp_sanpham " +
							  "where  daunho='"+qc.getDaunho()+ "' and DVDL_DAUNHO = '" + qc.getDVDL_Daunho() +
							  "' and  logo='"+qc.getLogo()+"' and  upper(MA)= upper('" + qc.getMA() + 
							  "') and daulon = '" + qc.getDaulon() + "' and DVDL_DAULON = '" + qc.getDVDL_Daulon() +
							  "' and trongluong = N'" + qc.getTrongLuong() + "' and DVDL_TRONGLUONG = '" + qc.getDVDL_TrongLuong() + "' " +
							  " and dai='"+qc.getDai()+ "' and DVDL_DAI = '" + qc.getDVDL_Dai() +
							  "' and  chuannen=N'"+qc.getChuannen()+"'   and Mau = N'" + qc.getMau() + "' and MauIn = N'" + qc.getMauin() + "' and congty_fk = '" + this.ctyId + "' and ma='"+this.masp+"'  ";
						if(qc.getId().trim().length() > 0)
							sql += " and pk_seq != '" + qc.getId() + "' ";
					}
					else
					{
						if( this.clId.equals("100030") || this.clId.equals("100032") || this.clId.equals("100033") || this.clId.equals("100034") ) 
						{
							sql = "select count(*) as num from erp_sanpham " +
								  "where  daunho='"+qc.getDaunho()+  "' and DVDL_DAUNHO = '" + qc.getDVDL_Daunho() +
								  "' and  logo='"+qc.getLogo()+"' and  upper(MA)= upper('" + qc.getMA() + 
								  "') and doday = '" + qc.getDoday() +  "' and DVDL_DODAY = '" + qc.getDVDL_Doday() +
								  "' and duongkinhtrong = '" + qc.getDuongkinhtrong() + "' and DVDL_DKTRONG = '" + qc.getDVDL_Dktrong() +
								  "' and logo = N'" + qc.getLogo() + "' " +
								  		"and Mau = N'" + qc.getMau() + "' and MauIn = N'" + qc.getMauin() + "' and congty_fk = '" + this.ctyId + "' and ma='"+this.masp+"'  ";
							if(qc.getId().trim().length() > 0)
								sql += " and pk_seq != '" + qc.getId() + "' ";
						}
						else
						{
							sql = "select count(*) as num from erp_sanpham " +
								  "where upper(MA)= upper('" + qc.getMA() + "') and dai = '" + qc.getDai() +  "' and DVDL_DAI = '" + qc.getDVDL_Dai() +
								  "' and rong = '" + qc.getRong() + "' and DVDL_RONG = '" + qc.getDVDL_Rong() +
								  "' and dinhluong = '" + qc.getDinhLuong() + "' and DVDL_DINHLUONG = '" + qc.getDVDL_DinhLuong() +
								  "' and QUYCACH_NGUONGOC = N'" + qc.getNguongoc() + "' and mau = N'" + qc.getMau() + "' and congty_fk = '" + this.ctyId + "' and ma='"+this.masp+"'  ";
							if(qc.getId().trim().length() > 0)
								sql += " and pk_seq != '" + qc.getId() + "' ";
							
						}
					}
				}
			}
		}
		
		//System.out.println("Get San Pham Trung "+sql);
		ResultSet rs = db.get(sql);
		try 
		{
			if (rs != null) 
			{
				rs.next();
				if (rs.getString("num").equals("0")) 
				{
  
					rs.close();
					////System.out.println("Trung ma roi__" + sql);
					return false;
				}
				rs.close();
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg=e.toString();
			return false;
		}
		this.msg=sql;
		return true;
	}
 
  
 
	 
	public void init3() 
	{
		CreateRS();
		createNspIds();
		createTieuchikiemdinh();
		createListQuyCach();
		createHoaChatMayMocKiemDinhList();

	}

	private void createListQuyCach() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.###");
		try 
		{
	 
			listquycach = new ArrayList<IQuyCachSanPhamList>();
			
			String sql = "";
			if(this.dvkdId.equals("100005")) {
				sql = 	" select distinct isnull(a.trangthai,'0') as trangthai , dvdl.donvi as tendvdl , isnull(a.MA, a.ma) as MA, a.pk_seq, isnull(dai,0) as dai , isnull(rong,0) as rong, " +
						"   isnull(trongluong,0) as trongluong , isnull(dvdl_fk,0) as dvdl_fk, isnull(dinhluong,0) as dinhluong, isnull(mau, '') as mau, " +
						" 	isnull(DVDL_RONG, '') as DVDL_RONG, isnull(DVDL_DAI, '') as DVDL_DAI, isnull(DVDL_DINHLUONG, '') as DVDL_DINHLUONG, " +
						" 	isnull(DVDL_TRONGLUONG, '') as DVDL_TRONGLUONG ," + 
						" 	isnull(MAKETOAN_FK,0) as MAKETOAN_FK, thetich, quycach_nguongoc, " +
						"   isnull(DODAY,0) as Doday , isnull(DVDL_DODAY, '') as  DVDL_DODAY, isnull(DUONGKINHTRONG,0) as duongkinhtrong , isnull(DVDL_DKTRONG, '') as DVDL_DKTRONG, isnull(LOGO, '') as LOGO," +
						" 	isnull(MAUIN, '') as MAUIN, DAULON, ISNULL(DVDL_DAULON, '') as DVDL_DAULON, isnull(DAUNHO,0) as daunho , ISNULL(DVDL_DAUNHO, '') as DVDL_DAUNHO, isnull(a.CHUANNEN,'') as chuannen," +
						" 	isnull(daiday, 0) as daiday, isnull(dvdl_daiday, '') as dvdl_daiday, isnull(a.MAMETRO, '') as mametro  " + 
						" from erp_sanpham a " +
						" left join donvidoluong dvdl on dvdl.pk_seq=a.dvdl_fk  " + 
						" where a.ma = '" + this.masp + "' ";
			} else {
				sql = 	" select distinct isnull(a.trangthai,'0') as trangthai ,isnull(dvdlqc.donvi,'') as tendvqc2 , dvdl.donvi as tendvdl , isnull(a.MA, a.ma) as MA, a.pk_seq, isnull(dai,0) as dai , isnull(rong,0) as rong, " +
						"   isnull(trongluong,0) as trongluong , isnull(dvdl_fk,0) as dvdl_fk, isnull(dinhluong,0) as dinhluong, isnull(mau, '') as mau, " +
						" 	isnull(DVDL_RONG, '') as DVDL_RONG, isnull(DVDL_DAI, '') as DVDL_DAI, isnull(DVDL_DINHLUONG, '') as DVDL_DINHLUONG, " +
						" 	isnull(DVDL_TRONGLUONG, '') as DVDL_TRONGLUONG ," + 
						" 	isnull(MAKETOAN_FK,0) as MAKETOAN_FK, thetich, quycach_nguongoc, b.soluong1, b.soluong2, b.dvdl2_fk," +
						"   isnull(DODAY,0) as Doday , isnull(DVDL_DODAY, '') as  DVDL_DODAY, isnull(DUONGKINHTRONG,0) as duongkinhtrong , isnull(DVDL_DKTRONG, '') as DVDL_DKTRONG, isnull(LOGO, '') as LOGO," +
						" 	isnull(MAUIN, '') as MAUIN, DAULON, ISNULL(DVDL_DAULON, '') as DVDL_DAULON, isnull(DAUNHO,0) as daunho , ISNULL(DVDL_DAUNHO, '') as DVDL_DAUNHO, isnull(a.CHUANNEN,'') as chuannen," +
						" 	isnull(daiday, 0) as daiday, isnull(dvdl_daiday, '') as dvdl_daiday, isnull(a.MAMETRO, '') as mametro" + 
						" from erp_sanpham a " +
						" left join QUYCACH b on a.pk_seq = b.sanpham_fk and a.dvdl_fk = b.dvdl1_fk" +
						" left join donvidoluong dvdl on dvdl.pk_seq=a.dvdl_fk  " +
						" left join donvidoluong dvdlqc on dvdlqc.pk_seq= b.dvdl2_fk   "
						+ " where a.ma = '" + this.masp + "' ";
			}
			
			if(this.qcgiay!=null) {
				
				if(qcgiay.getDai().length() >0){
					sql=sql+ " and dai= '"+qcgiay.getDai()+"'";
				}
				if(qcgiay.getDVDL_Dai().length() >0){
					sql=sql+ " and DVDL_DAI= N'"+qcgiay.getDVDL_Dai()+"'";
				}
				if(qcgiay.getTrongLuong().length() >0){
					sql=sql+ " and trongluong= '"+qcgiay.getTrongLuong()+"'";
				}
				if(qcgiay.getDVDL_TrongLuong().length() >0){
					sql=sql+ " and DVDL_TRONGLUONG= N'"+qcgiay.getDVDL_TrongLuong()+"'";
				}
				//System.out.println("Mau Sac Nek : "+qcgiay.getMau());
				if(qcgiay.getMau().length() >0){
					
					sql=sql+ " and  mau = N'"+qcgiay.getMau()+"'";
				}
				
				if(qcgiay.getDinhLuong().length() >0){
					sql=sql+ " and dinhluong= '"+qcgiay.getDinhLuong()+"'";
				}
				if(qcgiay.getDVDL_DinhLuong().length() >0){
					sql=sql+ " and DVDL_DINHLUONG= N'"+qcgiay.getDVDL_DinhLuong()+"'";
				}
				
				if(qcgiay.getRong().length() >0){
					sql=sql+ " and rong= '"+qcgiay.getRong()+"'";
				}
				
				if(qcgiay.getDVDL_Rong().length() >0){
					sql=sql+ " and DVDL_RONG= N'"+qcgiay.getDVDL_Rong()+"'";
				}
				if(qcgiay.getDonvidoluong().length() >0){
					sql=sql+ " and DVDL_FK= '"+qcgiay.getDonvidoluong()+"'";
				}
				if(qcgiay.getNguongoc().length() >0){
					sql=sql+ " and quycach_nguongoc= N'"+qcgiay.getNguongoc()+"'";
				}
				if(qcgiay.getDoday().length() >0){
					sql=sql+ " and DODAY= '"+qcgiay.getDoday()+"'";
				}
				if(qcgiay.getDVDL_Doday().length() >0){
					sql=sql+ " and DVDL_DODAY= N'"+qcgiay.getDVDL_Doday()+"'";
				}
				
				if(qcgiay.getDuongkinhtrong().length() >0){
					sql=sql+ " and DUONGKINHTRONG= '"+qcgiay.getDuongkinhtrong()+"'";
				}
				
				if(qcgiay.getDVDL_Dktrong().length() >0){
					sql=sql+ " and DVDL_DKTRONG= N'"+qcgiay.getDVDL_Dktrong()+"'";
				}
				if(qcgiay.getLogo().length() >0){
					sql=sql+ " and LOGO= N'"+qcgiay.getLogo()+"'";
				}
				if(qcgiay.getMauin().length() >0){
					sql=sql+ " and MAUIN= N'"+qcgiay.getMauin()+"'";
				}
				if(qcgiay.getChuannen().length() >0){
					sql=sql+ " and CHUANNEN= '"+qcgiay.getChuannen()+"'";
				}
				if(qcgiay.getDaulon().length() >0){
					sql=sql+ " and DAULON= '"+qcgiay.getDaulon()+"'";
				}
				if(qcgiay.getDVDL_Daulon().length() >0){
					sql=sql+ " and DVDL_DAULON= N'"+qcgiay.getDaulon()+"'";
				}
				
				if(qcgiay.getDaunho().length() >0){
					sql=sql+ " and DAUNHO= '"+qcgiay.getDaunho()+"'";
				}
				if(qcgiay.getDVDL_Daunho().length() >0){
					sql=sql+ " and DVDL_DAUNHO= N'"+qcgiay.getDVDL_Daunho()+"'";
				}
				if(qcgiay.getSoluongchuan().length() >0){
					sql=sql+ " and b.soluong1 =  '"+qcgiay.getSoluongchuan()+"'";
				}
				if(qcgiay.getSoluongquidoi().length() >0){
					sql=sql+ " and b.soluong2 =  '"+qcgiay.getSoluongquidoi()+"'";
				}
				
				if(qcgiay.getDonvidoluong_quydoi().length() >0){
					sql=sql+ " and  b.dvdl2_fk =  '"+qcgiay.getDonvidoluong_quydoi()+"'";
				}
				
				if(qcgiay.getDaiDay().length() >0){
					sql=sql+ " and DAIDAY= '" + qcgiay.getDaiDay() + "'";
				}
				if(qcgiay.getDvdl_DaiDay().length() >0){
					sql=sql+ " and DVDL_DAIDAY= N'" + qcgiay.getDvdl_DaiDay() + "'";
				}
				
				if(qcgiay.getMA().length() >0){
					sql=sql+ " and  b.MA =  '"+qcgiay.getMA()+"'";
				}
 
			}
			
			
			System.out.println("__KHOI TAO QUY CACH: " + sql);
			
			ResultSet rs = db.get(sql), rs2;
		 
				while (rs.next()) 
				{
					IQuyCachSanPhamList qc = new QuyCachSanPhamList();
					qc.setTrangthai(rs.getString("trangthai").trim());
					qc.setMA(rs.getString("MA"));
					qc.setDai(rs.getString("dai") == null ? "" : formatter.format(rs.getDouble("dai"))  );
					qc.setRong(rs.getString("rong") == null ? "" : formatter.format(rs.getDouble("rong")) );
					qc.setTrongLuong(rs.getString("trongluong") == null ? "" : formatter.format(rs.getDouble("trongluong")) );
					qc.setId(rs.getString("pk_seq"));
					qc.setDinhLuong(rs.getString("dinhluong") == null ? "" : formatter.format(rs.getDouble("dinhluong")) );
					qc.setMau(rs.getString("mau"));
					qc.setDVDL_Dai( rs.getString("DVDL_DAI") == null ? "" : rs.getString("DVDL_DAI") );
					qc.setDVDL_Rong( rs.getString("DVDL_RONG") == null ? "" : rs.getString("DVDL_RONG") );
					qc.setDVDL_TrongLuong( rs.getString("DVDL_TRONGLUONG") == null ? "" : rs.getString("DVDL_TRONGLUONG") );
					qc.setDVDL_DinhLuong( rs.getString("DVDL_DINHLUONG") == null ? "" : rs.getString("DVDL_DINHLUONG") );
					qc.setMaKeToan(rs.getString("MAKETOAN_FK"));
					this.maketoanId = rs.getString("MAKETOAN_FK");
					qc.setDonvidoluong(rs.getString("dvdl_fk") == null ? "" : rs.getString("dvdl_fk") );
					qc.setTenDonvidoluong(rs.getString("tendvdl"));
					
					qc.setThetich(rs.getString("thetich") == null ? "" : rs.getString("thetich"));
					qc.setNguongoc(rs.getString("quycach_nguongoc") == null ? "" : rs.getString("quycach_nguongoc"));
					
					//Sản phẩm mới không xài quy đổi 1 dòng
					if(!this.dvkdId.equals("100005")) {
						qc.setSoluongchuan(rs.getString("soluong1") == null ? "" : rs.getString("soluong1"));
						qc.setSoluongquidoi(rs.getString("soluong2") == null ? "" : rs.getString("soluong2"));
						qc.setDonvidoluong_quydoi(rs.getString("dvdl2_fk") == null ? "" : rs.getString("dvdl2_fk"));
						qc.setTenDonvidoluong_quydoi(rs.getString("tendvqc2"));
					}
					
					qc.setDoday(rs.getString("DODAY") == null ? "" : formatter.format(rs.getDouble("DODAY")));
					qc.setDVDL_Doday(rs.getString("DVDL_DODAY"));
					
					qc.setDuongkinhtrong(rs.getString("DUONGKINHTRONG") == null ? "" : formatter.format(rs.getDouble("DUONGKINHTRONG")));
					qc.setDVDL_Dktrong(rs.getString("DVDL_DKTRONG"));
					qc.setLogo(rs.getString("LOGO"));
					qc.setMauin(rs.getString("MAUIN"));
					
					qc.setDaulon(rs.getString("DAULON") == null ? "" :formatter.format( rs.getDouble("DAULON")));
					qc.setDVDL_Daulon(rs.getString("DVDL_DAULON"));
					qc.setDaunho(rs.getString("DAUNHO") == null ? "" : formatter.format(rs.getDouble("DAUNHO")));
					qc.setDVDL_Daunho(rs.getString("DVDL_DAUNHO"));
					qc.setChuannen(rs.getString("CHUANNEN") == null ? "" : rs.getString("CHUANNEN"));

					qc.setDaiDay( rs.getString("DAIDAY") == null ? "" :formatter.format( rs.getDouble("DAIDAY")) );
					qc.setDvdl_DaiDay( rs.getString("DVDL_DAIDAY") == null ? "" : rs.getString("DVDL_DAIDAY") );
					qc.setMaMetro(rs.getString("mametro"));
					
					//Lấy danh sách quy đổi cho sản phẩm mới
					if(this.dvkdId.equals("100005")) {
						String query = " SELECT * FROM QUYCACH WHERE SANPHAM_FK = " + qc.getId() + " AND DVDL1_FK = " + this.dvdlId;
						rs2 = db.get(query);
						try {
							while(rs2.next()) {
								IQuyDoi qd = new QuyDoi();
								qd.setDvdl1(rs2.getString("DVDL1_FK"));
								qd.setDvdl2(rs2.getString("DVDL2_FK"));
								qd.setSl1(rs2.getDouble("SOLUONG1"));
								qd.setSl2(rs2.getDouble("SOLUONG2"));
								qc.getQuyDoiList().add(qd);
							}
							rs2.close();
						} catch(Exception e) { }
					}
					
					listquycach.add(qc);
				}
			 
		} 
		catch (Exception er) 
		{
			//System.out.println("Exception: " + er.toString());
			er.printStackTrace();
		}
	}

	 

	public void setSongaytonkhoantoan(String songayantoan) {
		this.songayantoan = songayantoan;
	}

	public String getSongaytonkhoantoan() {
		return this.songayantoan;
	}

	public void setSoluongsanxuat(String soluongsanxuat) {
		this.soluongsanxuat = soluongsanxuat;
	}

	public String getSoluongsanxuat() {
		return this.soluongsanxuat;
	}

	public void setSoluongbomchuan(String soluongbomchuan) {
		this.soluongbomchuan = soluongbomchuan;
	}

	public String getSoluongbomchuan() {
		return this.soluongbomchuan;
	}

	public void setLamtronnguyenlieu(String lamtronguyenlieu) {
		this.lamtronguyenlieu = lamtronguyenlieu;
	}

	public String getLamtronnguyenlieu() {
		return this.lamtronguyenlieu;
	}

	public void setListDanhMuc(List<IDanhmucvattu_SP> list) {
		this.listDanhMuc = list;
	}

	public List<IDanhmucvattu_SP> getListDanhMuc() {
		return this.listDanhMuc;
	}

	public void SetQuyCachGiayList(List<IQuyCachSanPhamList> _listquycach) {

		this.listquycach = _listquycach;
	}

	public List<IQuyCachSanPhamList> GetQuyCachGiayList() {

		return this.listquycach;
	}

	public ResultSet getRsMau() {

		return this.rsMausac;
	}

	public void SetRsMaKeToan(ResultSet RsMaKT) {

		this.rsMaKeToan = RsMaKT;
	}

	public ResultSet getRsMaKeToan() {

		return this.rsMaKeToan;
	}


	public ResultSet getRsNguonGoc() {

		return this.rsNguonGoc;
	}

	public void setNhamayId(String nhamayId) {

		this.nhamayId = nhamayId;
	}

	public String getNhamayId() {

		return this.nhamayId;
	}

	public void setNhamayRs(ResultSet nhamayRs) {

		this.nhamayRs = nhamayRs;
	}

	public ResultSet getNhamayRs() {

		return this.nhamayRs;
	}

	public void setNhomNLId(String nnlId) {

		this.nhomNLId = nnlId;
	}

	public String getNhomNLId() {

		return this.nhomNLId;
	}

	public void setNhomNguyenLieuRs(ResultSet nhomNLRs) {

		this.nhomNLRs = nhomNLRs;
	}

	public ResultSet getNhomNguyenLieuRs() {

		return this.nhomNLRs;
	}

	
	public void setSoLuongBanTp(String soluong) {
		
		this.SoLuongBanTp=soluong;
	}

	
	public String getSoLuongBanTp() {
		
		return this.SoLuongBanTp;
	}

	
	public void setSoLuongTp(String soluong) {
		
		this.SoLuongTp=soluong;
	}

	
	public String getSoLuongTp() {
		
		return this.SoLuongTp;
	}

	
	public void setMaKeToan(String maketoan) {
		
		this.maketoanId = maketoan;
	}

	
	public String getMaKeToan() {
		
		return this.maketoanId;
	}

	public ResultSet getRsMauIn() 
	{
		return this.rsMauIn;
	}


	public ResultSet getRsDoNen() {
	
		return this.rsDoNen;
	}

	
	public void SetQuyCachGiay(IQuyCachSanPhamList qc) {
		
		this.qcgiay=qc;
	}

	
	public IQuyCachSanPhamList GetQuyCachGiay() {
		
		return this.qcgiay;
	}
	boolean kiemtra(dbutils db, String sql,String id)
	{
		
		String query = "select count(*) as num from " + sql + " where sanpham_fk ='"+ id +"'";
		////System.out.println("Query : "+query);
    	ResultSet rs = db.get(query);
		try 
		{	
			//kiem tra ben san pham
			while(rs.next())
			{ 
				if(rs.getString("num").equals("0"))
					
					return false;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		this.msg="Dữ liệu phát sinh trong bảng "+sql;
		//System.out.println(this.msg);
		return true;
	}
	
		public static void main ( String args []  )   
		{
			//SYN LAI DMS CAC SP CHUA QUA
			geso.traphaco.erp.db.sql.dbutils db = new geso.traphaco.erp.db.sql.dbutils();
			
			/*String query = "select a.PK_SEQ, ( select COUNT(PK_SEQ) from  " + geso.traphaco.center.util.Utility.prefixDMS + "SANPHAM where PK_SEQ = a.PK_SEQ ) as daco " +
						   "from ERP_SANPHAM a " + 
						   "where ( select COUNT(PK_SEQ) from  " + geso.traphaco.center.util.Utility.prefixDMS + "SANPHAM where PK_SEQ = a.PK_SEQ ) = 0	" +
						   " order by PK_SEQ asc";*/
			
			String query = "select a.PK_SEQ, 0 as daco " +
						   "from ERP_SANPHAM a " + 
						   "where a.ma = '6BANL'	" +
						   " order by PK_SEQ asc";
			ResultSet rs = db.get(query);
			try 
			{
				while( rs.next() )
				{
					String id = rs.getString("PK_SEQ");
					int daco = rs.getInt("daco");
					
					System.out.println("::: DANG CHAY ID: " + id);
					if( daco > 0 ) //Cập nhật
					{
						/*UtilitySyn.SynData(db, "ERP_SANPHAM", "SANPHAM", "PK_SEQ", id, "1", false);
						UtilitySyn.SynData(db, "QUYCACH", "QUYCACH", "SANPHAM_FK", id, "2", true);
						UtilitySyn.SynData(db, "QUYCACH", "QUYCACH", "SANPHAM_FK", id, "0", false);*/
					}
					else //Tạo mới
					{
						UtilitySyn.SynData(db, "ERP_SANPHAM", "SANPHAM", "PK_SEQ", id, "0", false);
						UtilitySyn.SynData(db, "QUYCACH", "QUYCACH", "SANPHAM_FK", id, "0", false);
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
			}
			
			
			System.out.println("::: CHAY XONG..... ");
			
		
			 
			
	   }
	
	
	
	public boolean deleteQc(String idquycach) {
		
	 try{
		 String sql="";
		   sql=" select hd.mahopdong "+ 
			   " from erp_hopdong_Sanpham hdsp inner join erp_hopdong hd on hd.pk_seq=hdsp.hopdong_fk "+ 
			   " where sanpham_fk= "+idquycach;
	
			ResultSet rs=db.get(sql);
			String msg1="";
			while (rs.next()){
				
				msg1 = (msg1.length()>0? msg1+","+rs.getString("mahopdong"):rs.getString("mahopdong")); 
			}
				
			rs.close();
			if(msg1.length() > 0){
				this.msg="Sản phẩm đã phát sinh trong hợp đồng : "+ msg1;
				return false;
			}
			// mua hàng
			 sql=	" SELECT  MH.SOPO FROM ERP_MUAHANG MH "+ 
				   " INNER JOIN  ERP_MUAHANG_SP MHSP ON MH.PK_SEQ=MHSP.MUAHANG_FK "+ 
				   " WHERE MHSP.SANPHAM_FK= "+idquycach;
			 rs=db.get(sql);
			 msg1="";
			while (rs.next()){
				
				msg1 = (msg1.length()>0? msg1+","+rs.getString("SOPO"):rs.getString("SOPO")); 
			}
			
			rs.close();
			if(msg1.length() > 0){
				this.msg="Sản phẩm đã phát sinh trong mua hàng : [  "+ msg1+" ]";
				return false;
			}
	
	
	
		//lệnh sản xuất
		 sql=" SELECT LENHSANXUAT_FK FROM ERP_LENHSANXUAT_SANPHAM INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=SANPHAM_FK WHERE SANPHAM_FK= "+idquycach;
		 rs=db.get(sql);
		 msg1="";
			while (rs.next()){
				
				msg1 = (msg1.length()>0? msg1+","+rs.getString("LENHSANXUAT_FK"):rs.getString("LENHSANXUAT_FK")); 
			}
			
			rs.close();
			if(msg1.length() > 0){
				this.msg="Sản phẩm đã phát sinh trong lệnh sản xuất : [  "+ msg1+" ]";
				return false;
			}
		
		// nhập kho của lệnh sản xuát ERP_NHAPKHO_SANPHAM
			sql=" SELECT SONHAPKHO_FK  FROM ERP_NHAPKHO_SANPHAM INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=SANPHAM_FK WHERE SANPHAM_FK= "+idquycach;
		   rs=db.get(sql);
		   msg1="";
			while (rs.next()){
				
				msg1 = (msg1.length()>0? msg1+","+rs.getString("SONHAPKHO_FK"):rs.getString("SONHAPKHO_FK")); 
			}
			
			rs.close();
			if(msg1.length() > 0){
				this.msg="Sản phẩm đã phát sinh trong nhập kho của  lệnh sản xuất : [  "+ msg1+" ]";
				return false;
			}
	
			// tồn kho tháng
			 sql=" select thang,nam from erp_tonkhothang where sanpham_fk="+idquycach+" and soluong >0 ";
			 rs=db.get(sql);
			 msg1="";
				if (rs.next()){
					
					msg1 = rs.getString("thang") +"-" +  rs.getString("nam"); 
				}
				
				rs.close();
				if(msg1.length() > 0){
					this.msg="Sản phẩm đã phát sinh trong dữ liệu tồn đầu kỳ : [  "+ msg1+" ]";
					return false;
				}
				// CHUYỂN KHO 
			
		    sql="SELECT CHUYENKHO_FK  FROM ERP_CHUYENKHO_SANPHAM CK WHERE CK.SANPHAM_FK = "+idquycach;
		    rs=db.get(sql);
		    msg1="";
			while (rs.next()){
				msg1 = (msg1.length()>0? msg1+","+rs.getString("CHUYENKHO_FK"):rs.getString("CHUYENKHO_FK")); 
			}
			rs.close();
			if(msg1.length() > 0){
				this.msg="Sản phẩm đã phát sinh trong phiếu chuyển kho : [  "+ msg1+" ]";
				return false;
			}
		

	
			sql=" select * from erp_khott_sanpham where  ( soluong > 0 and soluong <>1000000 ) and sanpham_fk="+idquycach ;
			
			rs=db.get(sql);
			if(rs.next()){
				rs.close();
				
				this.msg="Quy cách này đã phát sinh dữ liệu tồn kho,bạn không thể xóa quy cách này. Trung sql :" +sql;
				return false;
			}
				rs.close();
				sql="SELECT A.dieuchinhtonkhoTT_fk  FROM ERP_DIEUCHINHTONKHOTT_SANPHAM  A INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=SANPHAM_FK WHERE SANPHAM_FK="+idquycach;
				rs=db.get(sql);
			    msg1="";
				while (rs.next()){
					msg1 = (msg1.length()>0? msg1+","+rs.getString("dieuchinhtonkhoTT_fk"):rs.getString("dieuchinhtonkhoTT_fk")); 
				}
				rs.close();
				if(msg1.length() > 0){
					this.msg="Sản phẩm đã phát sinh trong điều chỉnh tồn kho : [  "+ msg1+" ]";
					return false;
				}
				sql="SELECT A.PHIEUYEUCAU_FK  FROM ERP_PHIEUYEUCAU_SANPHAM  A INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=SANPHAM_FK WHERE SANPHAM_FK="+idquycach;
				rs=db.get(sql);
			    msg1="";
				while (rs.next()){
					msg1 = (msg1.length()>0? msg1+","+rs.getString("PHIEUYEUCAU_FK"):rs.getString("PHIEUYEUCAU_FK")); 
				}
				rs.close();
				if(msg1.length() > 0){
					this.msg="Sản phẩm đã phát sinh trong phiếu yêu cầu chuyển kho của lệnh sản xuất : [  "+ msg1+" ]";
					return false;
				}
				//yêu cầu chuyên rkho của quản lý tồn kho
				
				sql="SELECT A.YEUCAUCHUYENKHO_FK  FROM ERP_YEUCAUCHUYENKHO_SANPHAM  A INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=SANPHAM_FK WHERE SANPHAM_FK="+idquycach;
				rs=db.get(sql);
			    msg1="";
				while (rs.next()){
					msg1 = (msg1.length()>0? msg1+","+rs.getString("YEUCAUCHUYENKHO_FK"):rs.getString("YEUCAUCHUYENKHO_FK")); 
				}
				rs.close();
				if(msg1.length() > 0){
					this.msg="Sản phẩm đã phát sinh trong phiếu yêu cầu chuyển kho  của quản lý tồn kho : [  "+ msg1+" ]";
					return false;
				}
				
				
		if(kiemtra(db, "nhaphang_sp",id)||kiemtra(db, "donhangtrave_sanpham",idquycach) || kiemtra(db, "dieukienkm_sanpham",idquycach) || kiemtra(db, "phieuxuatkho_spkm",idquycach) 
				|| kiemtra(db, "bosanpham_sanpham",idquycach) || kiemtra(db, "trakhuyenmai_sanpham",idquycach)||kiemtra(db, "donhangthuhoi_sanpham",idquycach) 
				|| kiemtra(db, "phieuxuatkho_sanpham",idquycach) || kiemtra(db, "denghidathang_sp",idquycach) ||kiemtra(db, "phieuthuhoi_sanpham",idquycach) 
				|| kiemtra(db, "donhang_sanpham",idquycach) || kiemtra(db, "dieuchinhtonkho_sp",idquycach) || kiemtra(db, "dontrahang_sp",idquycach) 
				|| kiemtra(db, "bosanpham_sanpham",idquycach) ||kiemtra(db, "phieuxuatkho_spkm",idquycach)
				|| kiemtra(db, "ERP_NHAPKHO_SANPHAM",idquycach) || kiemtra(db, "ERP_NHANHANG_SANPHAM",idquycach)  
				|| kiemtra(db, "ERP_DIEUCHINHTONKHOTT_SANPHAM",idquycach) 
				|| kiemtra(db, "ERP_YEUCAUNGUYENLIEU_SANPHAM",idquycach) 
				 )
		{
			 
			return false;
			 
		}
		else
		{
 
			 db.getConnection().setAutoCommit(false);
 
			sql="delete from ERP_KHACHHANG_SANPHAM where sanpham_fk ='" + idquycach + "'";
			if(!db.update(sql)){
				
				db.update("rollback");
				this.msg=sql;
				return false;
			}
		 	sql="delete from nhomsanpham_sanpham where sp_fk='" + idquycach + "'";
			if(!db.update(sql)){
				
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			 
			sql="delete from ERP_SANPHAM_SANXUAT where sanpham_fk='" + idquycach + "'";
			if(!db.update(sql)){
				
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			 
			sql= "delete from quycach where sanpham_fk='" + idquycach + "'";
			if(!db.update(sql)){
				
				db.update("rollback");
				this.msg=sql;
				return false;
			}
				
			sql= "delete from bgmuanpp_sanpham where sanpham_fk='" + idquycach + "'";
			 
			if(!db.update(sql)){
				
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			
			sql= "delete from NHANVIEN_SANPHAM where sanpham_fk='" + idquycach + "'";
			 
			if(!db.update(sql)){
				
				db.update("rollback");
				this.msg=sql;
				return false;
			}
		 
			sql="delete erp_khott_sanpham where sanpham_fk= "+idquycach;
			if(!db.update(sql)){
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			
			sql="delete erp_khott_sp_chitiet where sanpham_fk= "+idquycach;
			if(!db.update(sql)){
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			
			sql="delete from SanPham_TieuChiKiemDinh where sanpham_fk ='" + idquycach + "'";
			 
			if(!db.update(sql)){
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			sql="delete from ERP_BGBAN_SANPHAM where sanpham_fk ='" + idquycach + "'";
			 
			if(!db.update(sql)){
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			
			sql="delete from ERP_KIEMKHO_SANPHAM where soluongdieuchinh=0 and  sanpham_fk ='" + idquycach + "'";
			 
			if(!db.update(sql)){
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			sql="delete from ERP_KIEMKHO_Sp_chitiet where soluongdieuchinh=0 and  sanpham_fk ='" + idquycach + "'";
			 
			if(!db.update(sql)){
				db.update("rollback");
				this.msg=sql;
				return false;
			}
			 
			
			sql="delete from erp_sanpham where pk_seq='" + idquycach + "'";

			if(!db.update(sql)){
				db.update("rollback");
				this.msg=sql;
				return false;
			}
		 
		}
		 db.getConnection().commit();
		 db.getConnection().setAutoCommit(true);
		
	 }catch(Exception er){
		 this.msg="Lỗi : "+er.getMessage();
		 db.update("rollback");
		 er.printStackTrace();
		return false; 
	 }
		return true;
	}

	
	public void setDaiDay(String daiday) {
		this.daiday = daiday;
	}

	
	public String getDaiDay() {
		return this.daiday;
	}

	
	public void setDvdlDaiDay(String dvdl_daiday) {
		this.dvdl_daiday = dvdl_daiday;
	}

	
	public String getDvdlDaiDay() {
		return this.daiday;
	}

	
	public boolean kichhoatquycach(String idquycach) {
		
		try{
			String sql="Update erp_sanpham set trangthai=1 where pk_seq="+idquycach;
			if(!db.update(sql)){
				this.msg="Cập nhật không thành công: "+sql;
				return false;
			}
			return true;
		}catch(Exception er){
			this.msg=er.getMessage();
			er.printStackTrace();
			return false;
		}
		
	}

	
	public boolean Ngunghoatdong_Qc(String idquycach) {
		
		try{
			db.getConnection().setAutoCommit(false);
			String sql="Update erp_sanpham set trangthai=0 where pk_seq="+idquycach;
			if(!db.update(sql)){
				db.update("rollback");
				this.msg="Cập nhật không thành công: "+sql;
				return false;
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		}catch(Exception er){
			this.msg=er.getMessage();
			er.printStackTrace();
			return false;
		}
	}
	
	public boolean  check_masp(){

		if(this.masp.contains("&")){
			return true;
		}
		return false;
	}

	
	public String getnguongoc() {
		
		return this.nguongoc;
	}

	
	public void setnguongoc(String _nguongoc) {
		
		this.nguongoc=_nguongoc;
	}


	public void setMaMetro(String maMetro) {
		this.maMetro = maMetro;
		
	}


	public String getMaMetro() {
	
		return this.maMetro;
	}
//newtoyo =========================================================
	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}

	public String getCtyTen() {
		return this.ctyTen;
	}

	public void setCtyTen(String ctyTen) {
		this.ctyTen = ctyTen;
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
	
	public String getMasp()
	{
		return this.masp;
	}
	
	public void setMasp(String masp)
	{
		this.masp = masp;
	}
	
	public String getTennoibo()
	{
		return this.tennoibo;
	}
	
	public void setTennoibo(String tennoibo)
	{
		this.tennoibo = tennoibo;
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
	
	public String getDvdlId()
	{
		return this.dvdlId;
	}
	
	public void setDvdlId(String dvdlId)
	{
		this.dvdlId = dvdlId;
	}
	
	public String getDvdlTen()
	{
		return this.dvdlTen;
	}
	
	public void setDvdlTen(String dvdlTen)
	{
		this.dvdlTen = dvdlTen;
	}
	
	public ResultSet getDvdl()
	{
		return this.dvdl;
	}
	
	public void setDvdl(ResultSet dvdl)
	{
		this.dvdl = dvdl;
	}
	
	public String getDvkdId()
	{
		return this.dvkdId;
	}
	
	public void setDvkdId(String dvkdId)
	{
		this.dvkdId = dvkdId;
	}
	
	public String getDvkdTen()
	{
		return this.dvkdTen;
	}
	
	public void setDvkdTen(String dvkdTen)
	{
		this.dvkdTen = dvkdTen;
	}
	
	public String getNhId()
	{
		return this.nhId;
	}
	
	public void setNhId(String nhId)
	{
		this.nhId = nhId;
	}
	
	public String getNhTen()
	{
		return this.nhTen;
	}
	
	public void setNhTen(String nhTen)
	{
		this.nhTen = nhTen;
	}
	
	public String getClId()
	{
		return this.clId;
	}
	
	public void setClId(String clId)
	{
		this.clId = clId;
	}
	
	public String getClTen()
	{
		return this.clTen;
	}
	
	public void setClTen(String clTen)
	{
		this.clTen = clTen;
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
	
	public void setHinhanh(String hinhanh) {
		this.hinhanh = hinhanh;
	}

	public String getHinhanh() {
		return this.hinhanh;
	}
	
	public void setHinhcongboTP(String hinhcongboTP) {
		this.hinhcongboTP = hinhcongboTP;
	}

	public String getHinhcongboTP() {
		return this.hinhcongboTP;
	}
	
	public void setFilenamecbTP(String filenamecbTP) {
		this.filenamecbTP = filenamecbTP;
	}

	public String getFilenamecbTP() {
		return this.filenamecbTP;
	}
	
	public void setHancongboTP(String hancongboTP) {
		this.hancongboTP = hancongboTP;
	}

	public String getHancongboTP() {
		return this.hancongboTP;
	}
	
	public void setKhovung(String[] khovung) {
		this.khovung = khovung;
	}

	public String[] getKhovung() {
		return this.khovung;
	}
	
	public void setBantp(String[] bantp) {
		this.bantp = bantp;
	}

	public String[] getBantp() {
		return this.bantp;
	}
	
	public void setFilehinhcb(String fhinh,int k) {
		this.filehinh[k] = fhinh;
	}

	public String getFilehinhcb(int k) {
		return this.filehinh[k];
	}
	
	public void setFilehinhcb(String[] fhinh) {
		this.filehinh = fhinh;
	}

	public String[] getFilehinhcb() {
		return this.filehinh;
	}
	
	public void setFilenamecb(String[] filenamecb) {
		this.filenamecb = filenamecb;
	}

	public String[] getFilenamecb() {
		return this.filenamecb;
	}
	
	public void setDvkd(ResultSet dvkd)
	{
		this.dvkd = dvkd;
	}
	
	public ResultSet getDvkd()
	{
		return this.dvkd;
	}
	
	public void setNh(ResultSet nh)
	{
		this.nh = nh;
	}
	
	public ResultSet getNh()
	{
		return this.nh;
	}
	
	public void setCl(ResultSet cl)
	{
		this.cl = cl;
	}
	
	public ResultSet getCl()
	{
		return this.cl;
	}
	
	public void setGiablc(String giablc)
	{
		this.giablc = giablc;
	}
	
	public String getGiablc()
	{
		return this.giablc;
	}
	
	public void setNsp(ResultSet nsp)
	{
		this.nsp = nsp;
	}
	
	public ResultSet getNsp()
	{
		return this.nsp;
	}
	
	public String[] getNhacungcap()
	{
		return this.nhacungcap;
	}
	
	public void setNhacungcap(String[] nhacungcap)
	{
		this.nhacungcap = nhacungcap;
	}
	
	public String[] getHinhcongbo()
	{
		return this.hinhcongbo;
	}
	
	public void setHinhcongbo(String[] hinhcongbo)
	{
		this.hinhcongbo = hinhcongbo;
	}
	
	public String[] getHancongbo()
	{
		return this.hancongbo;
	}
	
	public void setHancongbo(String[] hancongbo)
	{
		this.hancongbo = hancongbo;
	}
	
	public String[] getThoihangiaohang()
	{
		return this.thoihangiaohang;
	}
	
	public void setThoihangiaohang(String[] thoihangiaohang)
	{
		this.thoihangiaohang = thoihangiaohang;
	}
	
	public String[] getLuongdattoithieu()
	{
		return this.luongdattoithieu;
	}
	
	public void setLuongdattoithieu(String[] luongdattoithieu)
	{
		this.luongdattoithieu = luongdattoithieu;
	}
	
	public String[] getSl1()
	{
		return this.sl1;
	}
	
	public void setSl1(String[] sl1)
	{
		this.sl1 = sl1;
	}
	
	public String[] getDvdl1()
	{
		return this.dvdl1;
	}
	
	public void setDvdl1(String[] dvdl1)
	{
		this.dvdl1 = dvdl1;
	}
	
	public String[] getSl2()
	{
		return this.sl2;
	}
	
	public void setSl2(String[] sl2)
	{
		this.sl2 = sl2;
	}
	
	public String[] getDvdl2()
	{
		return this.dvdl2;
	}
	
	public void setDvdl2(String[] dvdl2)
	{
		this.dvdl2 = dvdl2;
	}
	
	public ResultSet createNccRs(String ma)
	{
		String query = " select ncc.pk_seq as nccId, ncc.ten as nccTen from ERP_nhacungcap ncc "+
					" left join ERP_LOAINHACUNGCAP lncc on ncc.loaincc = lncc.PK_SEQ where 1=1 ";
		//if (ma!=null && ma.length()>0 && !ma.equals("VLP")) query += " and lncc.ma like '%" + ma + "%' ";
		query += " order by ncc.ten";
		ResultSet nccRs = this.db.getScrol(query);
		return nccRs;
	}
	
	public ResultSet createKhoRs()
	{
		String query = " select distinct k.pk_seq as khoId, k.ten as khoTen, k.ma as khoMa "+
					   " from erp_khott k inner join ERP_KHOTT_LOAISANPHAM kl on kl.khott_fk = k.PK_SEQ "+
					   " where k.trangthai = '1' and kl.loaisanpham_fk = '"+this.loaispId+"'";		
		ResultSet khoRs = this.db.getScrol(query);
		return khoRs;
	}
	
	public ResultSet createDvdlRS()
	{
		ResultSet dvdlRS = this.db.getScrol("select pk_seq as dvdlId, donvi as dvdlTen from donvidoluong where congty_fk = " + this.ctyId + " and trangthai = '1' order by donvi");
		return dvdlRS;
	}
	
	private ResultSet createDvkdRS()
	{
		ResultSet dvkdRS = this.db.get("select distinct(a.pk_seq) as dvkdId, a.donvikinhdoanh as dvkdTen from donvikinhdoanh a where congty_fk = " + this.ctyId + " and a.trangthai ='1' order by a.donvikinhdoanh");
		return dvkdRS;
	}
	
	private ResultSet createNhRS()
	{
		ResultSet nhRS;
		if (dvkdId.length() > 0)
		{
			nhRS = this.db.get("select pk_seq as nhId, ten as nhTen from nhanhang where congty_fk = " + this.ctyId + " and trangthai = '1' and dvkd_fk = '" + this.dvkdId + "'");
		}
		else
		{
			nhRS = this.db.get("select pk_seq as nhId, ten as nhTen from nhanhang where congty_fk = " + this.ctyId + " and trangthai='1'");
		}
		
		System.out.println(" nhan hang qr: "+ "select pk_seq as nhId, ten as nhTen from nhanhang where congty_fk = " + this.ctyId + " and trangthai = '1' and dvkd_fk = '" + this.dvkdId + "'");
		return nhRS;
	}
	
	private ResultSet createClRS()
	{
		String query =
		"select distinct a.pk_seq as clId, a.ten as clTen from chungloai a, nhanhang_chungloai b where a.congty_fk = " + this.ctyId + " and trangthai='1' and a.pk_seq = b.cl_fk ";
		if (this.nhId.length() > 0)
		{
			query = query + "  and b.nh_fk = '" + this.nhId + "'";
		}
		
		System.out.println(" chung loai qr :"+ query);
		return this.db.get(query);
	}
	
	private ResultSet createKtDlClRS()
	{
		String query = "select distinct a.pk_seq as clId, a.ten as clTen from chungloai a where a.congty_fk = " + this.ctyId + " and trangthai='1' ";
		return this.db.getScrol(query);
	}
	
	private void createNspRS()
	{
		String query = "select pk_seq as nspId, ten as nspTen, diengiai from nhomsanpham where congty_fk = " + this.ctyId + " and trangthai = '1' and loaithanhvien = '2' and type = '0'   order by ten";
		this.nsp = this.db.get(query);
	}
	
	private void createPacksizeRs() 
	{
		String query = "SELECT PK_SEQ, TEN FROM PACKSIZE WHERE TRANGTHAI = '1' AND CONGTY_FK = '" + this.ctyId + "' ";
		this.packsizeRs = this.db.get(query);
	}
	
	public boolean checkPhongKeToan()
	{
		String query = "select distinct * from NHANVIEN nv "+
						"inner join NHANVIEN_DONVITHUCHIEN nvdv on nvdv.NHANVIEN_FK = nv.PK_SEQ "+
						"where nvdv.DONVITHUCHIEN_FK = '100004' and nv.PK_SEQ = '" + this.userId + "' ";
		ResultSet rs;
		rs = this.db.get(query);
		System.out.println("query ne " + query);
		try {
			while(rs.next())
			{
				System.out.println("dung roi ne ");
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return false;
	}
	
	public void CreateRS()
	{
		this.dvdl = createDvdlRS();
		this.dvkd = createDvkdRS();
		this.nh = createNhRS();
		this.cl = createClRS();
		this.loaispRs = db.get("select pk_seq as loaiId, ma + ', ' + ten as loaiTen, ma from erp_loaisanpham  where trangthai=1");
		this.dangBaoCheRs = db.get(" select PK_SEQ, TEN from DANGBAOCHE where trangthai =1");
		
		String query;
		ResultSet rs;
		if(this.loaispId.length() > 0){
			query = "select thuocloaisanpham from ERP_LOAISANPHAM where pk_seq = " + this.loaispId;
			rs = this.db.get(query);
			
			try {
				if(rs != null){
					rs.next();
					this.thuocloaisp = rs.getString("thuocloaisanpham");
					rs.close();
					
					if(!this.thuocloaisp.equals("2"))
						this.khongqlsl = "0";
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		//String query = "Select PK_SEQ,Ten From Erp_CongTy Where TrangThai=1 ";
		//this.CongTyRs = this.db.get(query);
		this.createNspRS();
		this.createPacksizeRs();
		
		this.createSpList();		
		query="select pk_seq, ma, ten from erp_sanpham where loaisanpham_fk in (100044,100045)";
		this.rsBTP=db.get(query);
		query="select pk_seq,ten from erp_sanpham where loaisanpham_fk in (100047) ";
		this.rsPhepham=db.get(query);
		
		query = "select ('TSCD' + cast(pk_seq as varchar)) as pk_seq, 'TSCĐ - ' + ma + case when diengiai is null then '' else ' - ' + diengiai end as ten"
				+ " from ERP_TAISANCODINH where trangthai = 1"
				+ " union all"
				+ " select ('CPTT' + cast(pk_seq as varchar)) as pk_seq, 'CPTT - ' + ma + case when diengiai is null then '' else ' - ' + diengiai end as ten"
				+ " from ERP_CONGCUDUNGCU where trangthai = 1";
		this.tscdRs = db.getScrol(query);
	}
	
	private void createSpList()
	{
		String query = "";
		
		if (this.type.equals("1"))
		{
			if(this.id.trim().length() > 0)
			{
				query = "select pk_seq, ma as spMa, ten as spTen, ten1 as spTennoibo " +
				   		"from erp_sanpham sp inner join bundle_sanpham bdsp on sp.pk_seq = bdsp.sanpham_fk  " +
				   		"where bdsp.bundle_fk = '" + this.id.trim() + "' and dvkd_fk = '" + this.dvkdId + "' ";
				
				if (this.nhId.length() > 0) 
					query = query + " and nhanhang_fk = '" + this.nhId + "' ";
				if (this.clId.length() > 0) 
					query = query + " and chungloai_fk = '" + this.clId + "' ";
				
				query += " union  select pk_seq, ma as spMa, ten as spTen, ten1 as spTennoibo " +
						 " from erp_SanPham where pk_seq not in ( select sanpham_fk from bundle_sanpham where bundle_fk = '" + this.id.trim() + "' ) ";
				
				if (this.nhId.length() > 0) 
					query = query + " and nhanhang_fk = '" + this.nhId + "' ";
				if (this.clId.length() > 0) 
					query = query + " and chungloai_fk = '" + this.clId + "' ";
			}
			else
			{
				query = " select pk_seq, ma as spMa, ten as spTen, ten1 as spTennoibo, '-1' as soluong " +
						 " from erp_SanPham where trangthai = '1' ";
				
				if (this.nhId.length() > 0) 
					query = query + " and nhanhang_fk = '" + this.nhId + "' ";
				if (this.clId.length() > 0) 
					query = query + " and chungloai_fk = '" + this.clId + "' ";
			}
			
			//System.out.print("1.khoi tao san pham_bundle: " + query);
			this.spList = db.get(query);
		}
			
	}
	
	private boolean masanpham()
	{
		String sql;
		if (this.id.length() == 0)
		{
			sql = "select count(*) as num from erp_sanpham where RTRIM(LTRIM(ma)) ='" + this.masp + "'";
		}
		else
		{
			sql = "select count(*) as num from erp_sanpham where pk_seq <> '" + this.id + "' and RTRIM(LTRIM(ma)) ='" + this.masp + "'";
		}
		
		ResultSet rs = db.get(sql);
		
		try
		{
			if (rs != null) 
			{
				rs.next();
				if (rs.getString("num").equals("0")) 
				{
					rs.close();
					return false;
				}
				rs.close();
			}
		}
		catch (Exception e) { return false; }
		
		return true;
	}
	
	public boolean CreateSp()
	{
		String command = "";
		try
		{
			if (this.masp.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập mã sản phẩm";
				return false;
			} 
			else if (masanpham())
			{
				this.msg = "Mã sản phẩm bị trùng";
				return false;
			}
			else if (this.tennoibo.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập tên nội bộ của sản phẩm";
				return false;
			}
			/*else if (this.tenthuongmai.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập tên thương mại của sản phẩm";
				return false;
			}*/
			else if (this.ten.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập tên sản phẩm";
				return false;
			} 
			/*else if (this.dvkdId.length()<=0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}*/
			/*else if (this.nhId.length()<=0)
			{
				this.msg = "Vui lòng chọn nhãn hàng";
				return false;
			}*/
			else if (this.loaispma.contains("TP")) 
			{
				/*if ( Double.parseDouble(this.GiaMua) <= 0 )
				{
					this.msg = "Vui lòng nhập giá mua sản phẩm";
					return false;
				}*/
			}
			
			//TAM THOI BO KHONG BAT BUOC
			/*if(this.Batbuockiemdinh.equals("1"))
			{
				if(this.tckdDinhluongList.size()==0 && this.tckdDinhtinhList.size()==0){
					this.msg = "Vui lòng chọn tiêu chí kiểm định hoặc định tính ";
					return false;
				}
			}*/
			
			
			/*if (this.kiemtradinhluong.equals("1")) 
			{
				if (this.tckdDinhluongList.size() <= 0 && this.CheckIsNot_TieuChiDL_NCC()) 
				{
					this.msg = "Vui lòng nhập tiêu chí kiểm tra định lượng";
					return false;
				}
				else 
				{
					
					for (int i = 0; i < this.tckdDinhluongList.size(); i++) 
					{
						ITieuchikiemdinh tckd = this.tckdDinhluongList.get(i);
						if (tckd.getTieuchi().trim().length() > 0) 
						{
							if (tckd.getToantu().trim().length() <= 0
									|| tckd.getGiatrichuan().trim().length() <= 0)
							{
								this.msg = "Vui lòng kiểm tra lại toán tử và các giá trị chuẩn của tiêu chí: " + tckd.getTieuchi();
								return false;
							}
						}
					}
				}
			}

			if (this.kiemtradinhtinh.equals("1")) 
			{
				if (this.tckdDinhtinhList.size() <= 0 && this.CheckIsNot_TieuChiDT_NCC())
				{
					this.msg = "Vui lòng nhập tiêu chí kiểm tra định tính";
					return false;
				}
			}*/
			
			if((this.loaispma.contains("VT")||this.loaispma.contains("BB")||this.loaispma.contains("NL")||this.loaispma.contains("VLP")) && this.nhacungcap==null )
			{
				this.msg = "Vui lòng chọn nhà cung cấp";
				return false;
			}
			if(!this.loaispId.contains("100005"))
				this.isHangbo = "NULL";
			
			this.db.getConnection().setAutoCommit(false);
			
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			String clId = this.clId == null || this.clId.trim().length() == 0 ? null : this.clId.trim();
			String nhId = this.nhId == null || this.nhId.trim().length() == 0 ? null : this.nhId.trim();
			String dvdlId = this.dvdlId == null || this.dvdlId.trim().length() == 0 ? null : this.dvdlId.trim();
			String loaispId = ( this.loaispId == null || this.loaispId.trim().length() == 0 ) ? "NULL" : this.loaispId.trim();
//			String loaispma = this.loaispma == null || this.loaispma.trim().length() == 0 ? null : this.loaispma.trim();
			String packsizeId = this.packsizeId == null || this.packsizeId.trim().length() == 0 ? null : this.packsizeId.trim();
			String dvkdId = this.dvkdId == null || this.dvkdId.trim().length() == 0 ? null : this.dvkdId.trim();
			String kl = this.kl == null || this.kl.trim().length() == 0 ? "0" : this.kl.trim();
			String thetich = this.tt == null || this.tt.trim().length()==0 ? "0" : this.tt.trim();
			
			if(this.Check_Vothoihan.equals("1")){
				this.HanSuDung="36500";
				
			}
			String hsd = this.HanSuDung == null || this.HanSuDung.trim().length() == 0 ? "0" : this.HanSuDung.trim();
			
			command = "insert into erp_SanPham(ma, ten, ten1, ngaytao, ngaysua, nguoitao, nguoisua, dvdl_fk, trangthai, dvkd_fk, nhanhang_fk, chungloai_fk, type, trongluong, thetich, loaisanpham_fk, congty_fk, tonkhoantoan, packsize_fk, chiphinhancong, chiphivanchuyen, kiemtradinhtinh, kiemtradinhluong, gianhaplandau, hansudung, HANGBO,FILEPATH,FILENAME,PHEPHAM_FK,BATBUOCKIEMDINH,IS_VOTHOIHAN,SONGAYHANCANHBAO,THUEXUAT,NGAYHOANTHANH, TENTHUONGMAI,ischietkhau,IS_KHONGTHUE,"
					+ "THIETBICAN, THIETBICANKHAC, YEUCAUNL, MOTACAMQUAN, YEUCAUDONGGOI, DACTINHKYTHUAT, CONGTHUCHOAHOC, NHOMLUUTRU, MUCDONGUYHIEM, KHUVUCBAOQUAN, KHONGQUANLYSL)  " +
					"values('" + this.masp + "', N'" + this.ten + "', N'" + this.tennoibo + "', '" + this.ngaysua + "','" + this.ngaysua + "','" + this.userId + "'," +
					"'" + this.userId + "', " + dvdlId + ",'" + this.trangthai + "', " + dvkdId + ", " + nhId + "," + clId + "," +
					"'" + this.type + "','" + kl + "','" + thetich + "', " + loaispId + ", '" + this.ctyId + "', '" + this.tonkhoantoan + "', " + packsizeId + ", '"+this.cpnc+"', '"+this.cpvc+"','" + this.kiemtradinhtinh + "', '" + this.kiemtradinhluong + "', '" + this.GiaMua + "', '" + hsd + "', "+this.isHangbo+", '"+ this.pathHinhanhSp + "', '" + this.nameHinhanhSp + "', " +
					""+(this.PPId.length() >0? this.PPId: "NULL")+",'"+this.Batbuockiemdinh+"','"+this.Check_Vothoihan+"' ,"+this.Songayhancanhbao+", "+this.thueSuat+",'"+this.Ngayhoanthanh+"',N'"+ this.tenthuongmai +"','"+this.ischietkhau+"','"+this.is_khongthue+"',"
					+ "'"+this.thietBiCan+"','"+this.thietBiCanKhac+"', N'"+this.ycnlsx+"', N'"+this.motaSp+"', N'"+this.yeucaudonggoi+"', N'"+this.dactinhkythuat+"', N'"+this.congthuchoahoc+"', N'"+this.nhomluutru+"', '"+this.mucdonguyhiem+"', N'"+this.khuvucbaoquan+"', "+this.khongqlsl+")";
			
			System.out.println("[Thongtinsanpham.CreateSp] command = " + command);
			if (!this.db.update(command))
			{
				this.msg = command;
				this.db.getConnection().rollback();
				return false;
			}
			
			command = "select IDENT_CURRENT('erp_sanpham') as spId";
			ResultSet rs = this.db.get(command);
			rs.next();
			this.id = rs.getString("spId");
			System.out.println("pk_seq ne!! " + this.id);
			
			command=  " UPDATE SP SET TIMKIEM = upper(dbo.ftBoDau(MA)) " +
			" +'-'+ upper(dbo.ftBoDau(isnull(SP.ten1,'')))   " +
			" +'-'+ upper(dbo.ftBoDau(isnull(SP.ten,'')))   " +  			
		    " +'-'+ upper(dbo.ftBoDau(isnull(DVDL.DONVI,'')))  " +  
		    " FROM erp_SANPHAM SP INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK  " +  
		    " where SP.PK_SEQ="+this.id;
			if(!db.update(command))
			{
				this.msg = command;
				this.db.update("rollback");
				return false;
			}
			
			// Cập nhật thêm tckt, dạng bào chế, loại hàng hóa
			command = " update ERP_SANPHAM set TCKT = N'"+ this.tckt +"',LOAIHANGHOA='"
						+ this.loaiHangHoa+"',DANGBAOCHE= '"+ this.dangBaoChe +"', KIEMTRAOE= "+ this.kiemtraoe +"  where PK_SEQ =" + this.id;
			
			if(!db.update(command))
			{
				this.msg = command;
				this.db.update("rollback");
				return false;
			}
			
			
			
			boolean error = false;
			for (int i = 0; i < 5; i++)
			{
				if (!(sl1[i] == null))
				{
					if ((sl1[i].length() > 0) && (sl2[i].length() > 0) && (dvdl1[i].length() > 0) && (dvdl2[i].length() > 0))
					{
						command = "insert into quycach(sanpham_fk, dvdl1_fk, soluong1, dvdl2_fk, soluong2) " +
								  "	values('" + this.id + "','" + this.dvdl1[i] + "','" + this.sl1[i] + "','" + this.dvdl2[i] + "','" + this.sl2[i] + "')";
						System.out.println("[Thongtinsanpham.CreateSp] command = " + command);
						
						if (!(this.db.update(command)))
						{
							this.msg = command;
							db.getConnection().rollback();
							return false;
						}
						
						//if (dvdl2[i].equals("100018")) error = false;
					}
				}
			}
			
			if (error)
			{
				this.db.getConnection().rollback();
				this.msg = "Vui long nhap thong tin quy cach san pham";
				return false;
			}
			
			if (this.nspIds.trim().length() > 0)
			{
				command = "insert nhomsanpham_sanpham(sp_fk, nsp_fk)  " +
						  "select '" + this.id + "', pk_seq from NhomSanPham where pk_seq in (" + this.nspIds + ")";
				 
				if (!this.db.update(command))
				{
					System.out.println("[Thongtinsanpham.CreateSp] --> That bai");
					/*System.out.print("2.Error: " + command);
					db.getConnection().rollback();
					return false;*/
				}
			 
			}
			
			if (this.type.equals("1"))
			{
				System.out.println("2.San pham Ids: " + this.spIds);
				String[] spArr = this.spIds.split(",");
				
				for(int j = 0; j < spArr.length; j++)
				{
					int soluong =  0;//this.bundle_soluong.get(spArr[j]) == null ? 0 : this.bundle_soluong.get(spArr[j]);
					
					command = "insert into Bundle_Sanpham(bundle_fk, sanpham_fk, soluong) " +
							  "values('" + this.id + "', '" + spArr[j] + "', '" + soluong + "')";					
					System.out.println("[Thongtinsanpham.CreateSp] insert command = " + command);
					if (!this.db.update(command))
					{
						System.out.println("[Thongtinsanpham.CreateSp] --> That bai");
					}
					System.out.println("[Thongtinsanpham.CreateSp] --> Thanh cong");
				}
			}
			
			if(this.dvkdId.trim().length() > 0) {
				command = "insert ERP_BGBAN_SANPHAM(sanpham_fk, bgban_fk, giaban, dvdl_fk) " +
						  "select '" + this.id + "', pk_seq, '" + this.giablc + "', " + dvdlId + " from ERP_BANGGIABAN where dvkd_fk = '" + this.dvkdId + "' ";
				System.out.println("[Thongtinsanpham.CreateSp] insert banggiablc = " + command);
				if (!this.db.update(command))
				{
					System.out.println("[Thongtinsanpham.CreateSp] --> That bai");
				}
				 
			}
			
			String query="";
			
			// Them vao kho TT Canfoco
			if(!this.khongqlsl.equals("1")){
			String kho = "(";
			for (int i=0;i<this.khovung.length;i++)
				if (this.khovung[i] != null && this.khovung[i].length()>0) kho+="'"+this.khovung[i]+"',";
			kho = kho.substring(0, kho.length()-1) + ")";
			
			if( kho.length() > 2 && !loaispId.equals("NULL") )
			{
				command = " insert erp_khott_sanpham(KHOTT_FK, SANPHAM_FK, GIATON, SOLUONG, BOOKED, AVAILABLE) " +
						  " select pk_seq, '" + this.id + "', '0', '0', '0', '0' " + 
						  " from ERP_KHOTT " + 
						  " where pk_seq in ( select khott_fk from ERP_KHOTT_LOAISANPHAM where loaisanpham_fk = '" + loaispId + "' ) and pk_seq in " + kho;
				//System.out.println("[Thongtinsanpham.CreateSp] command = " + command);
				if (!this.db.update(command))
				{
					this.msg = "Chưa chọn kho." + command;
					db.getConnection().rollback();
					return false;
				}
				
				/*query = "insert erp_tonkhothang(khott_fk, sanpham_fk, giaton, thanhtienTon, soluong, booked, available, thang, nam) " +
						"select pk_seq, '" + this.id +  "', '" + this.GiaMua + "', '0', '0', '0', '0', (select datepart(MM ,GETDATE())), (select datepart(yyyy ,GETDATE()))  "+
						"from erp_khott where pk_seq in " + kho + " group by pk_seq ";
				System.out.println("[Thongtinsanpham.CreateSp] insert erp_tonkhothang : " + query);
				if (!db.update(query))
				{
					this.msg = query;
					db.getConnection().rollback();
					return false;
				}*/
			}
			}
			
			
			if (this.loaispma.contains("TP")  ) //thanh pham
			{
				command = "insert erp_sanpham_nhacungcap(sanpham_fk,hancongbo,hinhcongbo,filename) " +
				  " values('"+ this.id +"','"+ this.hancongboTP +"','" + this.hinhcongboTP + "','" + this.filenamecbTP + "')";
				if (!this.db.update(command))
				{
					this.msg = command;
					db.getConnection().rollback();
					return false;
				}
				//System.out.println("[Thanhpham.CreateSp] --> Thanh cong");
				
				
				//xoa ban thanh pham cu
				command = "delete from erp_sanpham_btp where sp_fk='" + this.id +"'";
				if (!this.db.update(command))
				{
					db.getConnection().rollback();
				}
				//them ban thanh pham
				if (this.bantp!=null)
				{
					String btp = "(";
					for (int i=0;i<this.bantp.length;i++)
						if (this.bantp[i] != null && this.bantp[i].length()>0) btp+="'"+this.bantp[i]+"',";
					btp = btp.substring(0, btp.length()-1) + ")";
					
					if(btp.length()> 2){
						command = "insert erp_sanpham_btp(sp_fk,btp_fk) " +
						  "select '" + this.id + "', pk_seq from ERP_sanpham where pk_seq in "+btp;
						//System.out.println("[Thongtinsanpham.CreateSp] command = " + command);
						if (!this.db.update(command))
						{
							this.msg = "Chưa chọn bán thành phẩm." + command;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			//thong tin nha cung cap
			if((this.loaispma.contains("VT")||this.loaispma.contains("BB")||this.loaispma.contains("NL")||this.loaispma.contains("VLP")) && this.nhacungcap!=null )
			{ 

				for (int i=0;i<this.ThongtinNCCList.size();i++){
					
					IThongtinNCC ttncc=this.ThongtinNCCList.get(i);
						
							command = "insert erp_sanpham_nhacungcap(sanpham_fk,nhacungcap_fk,thoihangiaohang,luongdattoithieu,hancongbo,hinhcongbo,filename) " +
							  " values('"+ this.id +"','" + ttncc.getnhacungcap()+ "'," +ttncc.getthoihangiaohang()+ ","+ttncc.getluongdattoithieu()+",'"+ ttncc.gethancongbo() +"','" +ttncc.gethinhcongbo() + "','" +ttncc.getfilenamecb()+ "')";
							if (!this.db.update(command))
							{
								 
								this.msg = command;
								db.getConnection().rollback();
								return false;
							}
							
							List<ITieuchikiemdinh> tckdList_ncc =ttncc.getTieuchikiemdinhDinhluongList();
							for(int k=0;k<tckdList_ncc.size();k++){
								ITieuchikiemdinh tckd=tckdList_ncc.get(k);	
								if (tckd.getTieuchi().trim().length() > 0) {
									query = " insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai,NCC_FK) "
											+ " select pk_seq, N'" + tckd.getTieuchi() + "'"
											+ ", '" + tckd.getToantu()	+ "'"
											+ ", '"+ tckd.getGiatrichuan()	+ "' " 
											+ ", '0',"+ttncc.getnhacungcap()+" "
											+ " from erp_sanpham where pk_seq = '" + this.id + "' ";
								 
									if (!db.update(query)) {
										this.msg = "12.Không thể tạo mới SanPham_TieuChiKiemDinh: " + query;
										this.db.update("rollback");
										return false;
									}
								}
							}
							
							List<ITieuchikiemdinh>  tckdDinhtinhList_ncc =ttncc.getTieuchikiemdinhDinhtinhList();
							for ( int  k = 0; k <  tckdDinhtinhList_ncc.size(); k++) {
								ITieuchikiemdinh tckd =  tckdDinhtinhList_ncc.get(k);
					
								if (tckd.getTieuchi().trim().length() > 0) {
									query = "insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai,NCC_FK) "
											+ "select pk_seq, N'"
											+ tckd.getTieuchi()
											+ "', '"
											+ tckd.getToantu()
											+ "', '"
											+ tckd.getGiatrichuan()
											+ "', '1',"+ttncc.getnhacungcap()+" "
											+ "from erp_sanpham where pk_seq = '" + this.id + "' ";
									//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
									if (!db.update(query)) {
										this.msg = "13.Không thể tạo mới SanPham_TieuChiKiemDinh: " + query;
										this.db.update("rollback");
										return false;
									}
								}
							}
							//Insert vao bang HOACHAT_SANPHAM cua nha cung cap
							Iterator<IHoaChat_SanPham> iteratorHoaChat_NCC = ttncc.getHoaChatKiemDinhList().iterator();
							while (iteratorHoaChat_NCC.hasNext()) {
								IHoaChat_SanPham elementHoaChat_NCC = iteratorHoaChat_NCC.next();
								if (elementHoaChat_NCC.getPK_SEQHoaChatKiemDinh().trim().length() > 0) {
									ttncc.init();
									if (elementHoaChat_NCC.getSoLuongChatKiemDinh() == 0) {
										this.msg = "Vui lòng nhập số lượng của hóa chất "+elementHoaChat_NCC.getTenHoaChatKiemDinh()+" của nhà cung cấp "+ttncc.getTenNhaCungCap()+" trong mục \"Thông tin nhà cung cấp \"";
										return false;
									}
									if (elementHoaChat_NCC.getsoLuongChuan() == 0) {
										this.msg = "Vui lòng nhập số lượng chuẩn của nhà cung cấp "+ttncc.getTenNhaCungCap()+" trong mục \"Thông tin nhà cung cấp \"";
										return false;
									}	
									
									
									query = "insert into HOACHAT_SANPHAM(SANPHAM_FK, HOACHAT, SOCHATDUOCKIEMDINH, SOCHATKIEMDINH, NCC_FK) "
											+ "values ("
											+ this.id
											+ ", "
											+ elementHoaChat_NCC.getPK_SEQHoaChatKiemDinh()
											+ ", "
											+ elementHoaChat_NCC.getsoLuongChuan()
											+ ", "
											+ elementHoaChat_NCC.getSoLuongChatKiemDinh()
											+ ", "
											+ elementHoaChat_NCC.getNhaCungCap().getnhacungcap()
											+ ")";
									if (!db.update(query)) {
										this.msg = "13.Lỗi dòng lệnh  : " + query;
										this.db.update("rollback");
										return false;
									}
								}
							}
							
							//Insert may moc kiem dinh vao bang MAYMOC_SANPHAM cua nha cung cap khi TAO MOI
							Iterator<IMayMoc_SanPham> iteratorMayMoc_NCC = ttncc.getMayMocKiemDinhList().iterator();
							while (iteratorMayMoc_NCC.hasNext()) {
								IMayMoc_SanPham elementMayMoc_NCC = iteratorMayMoc_NCC.next();
								if (elementMayMoc_NCC.getMayMocKiemDinh().getId().trim().length() > 0) {
									query = "insert into [dbo].[MAYMOC_SANPHAM]\r\n" + 
											"           ([SANPHAM_FK]\r\n" + 
											"           ,[TAISANCODINH_FK]"
											+ "         ,[NCC_FK]) "
											+ "values ("
											+ this.id
											+ ", "
											+ elementMayMoc_NCC.getMayMocKiemDinh().getId()
											+ ", "
											+ elementMayMoc_NCC.getNhaCungCap().getnhacungcap()
											+ ")";
									//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
									System.out.println(query);
									if (!db.update(query)) {
										this.msg = "13.Lỗi dòng lệnh  : " + query;
										this.db.update("rollback");
										return false;
									}
								}
							}//Insert may moc kiem dinh vao bang MAYMOC_SANPHAM cua nha cung cap khi TAO MOI
							
				} 
			}
			
			query = " insert nhanvien_sanpham(nhanvien_fk, sanpham_fk) " +
					" select distinct nhanvien_fk, '" + this.id + "' as sanpham_fk from phanquyen " +
					" where nhanvien_fk not in (select nhanvien_fk from nhanvien_sanpham where sanpham_fk = '" + this.id + "')  " +
					" and dmq_fk in (select dmq_fk from nhomquyen where ungdung_fk = '8')" +
					" and nhanvien_fk in (select pk_seq from nhanvien where trangthai = '1')";
			//System.out.println("[Thongtinsanpham.CreateSp] command = " + command);
			if (!db.update(query))
			{
				this.msg = query;
				db.getConnection().rollback();
				return false;
			}
			
			for (int i = 0; i < this.tckdDinhluongList.size(); i++) 
			{
				ITieuchikiemdinh tckd = this.tckdDinhluongList.get(i);
	
				if (tckd.getTieuchi().trim().length() > 0) {
					query = " insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai) "
							+ " select pk_seq, N'" + tckd.getTieuchi() + "'"
							+ ", '" + tckd.getToantu()	+ "'"
							+ ", '"+ tckd.getGiatrichuan()	+ "' " 
							+ ", '0' "
							+ " from erp_SANPHAM where pk_seq = '" + this.id + "' ";
					//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
					if (!db.update(query)) {
						this.msg = "12.Lỗi dòng lệnh : " + query;
						this.db.update("rollback");
						return false;
					}
				}
			}
	
			for (int i = 0; i < this.tckdDinhtinhList.size(); i++) {
				ITieuchikiemdinh tckd = this.tckdDinhtinhList.get(i);
	
				if (tckd.getTieuchi().trim().length() > 0) {
					query = "insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai) "
							+ "select pk_seq, N'"
							+ tckd.getTieuchi()
							+ "', '"
							+ tckd.getToantu()
							+ "', '"
							+ tckd.getGiatrichuan()
							+ "', '1' "
							+ "from erp_SANPHAM where pk_seq = '" + this.id + "' ";
					//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
					if (!db.update(query)) {
						this.msg = "13.Lỗi dòng lệnh  : " + query;
						this.db.update("rollback");
						return false;
					}
				}
			}
			
			for (int i = 0; i < this.hoaChatKiemDinhList.size(); i++) {
				IHoaChat_SanPham hcsp = this.hoaChatKiemDinhList.get(i);

				if (hcsp.getPK_SEQHoaChatKiemDinh().trim().length() > 0) {
					System.out.println(hcsp.getPK_SEQHoaChatKiemDinh() + "||" +hcsp.getsoLuongChuan()+ "||" + hcsp.getSoLuongChatKiemDinh());
					query = "insert into HOACHAT_SANPHAM(SANPHAM_FK, HOACHAT, SOCHATDUOCKIEMDINH, SOCHATKIEMDINH) "
							+ "values ("
							+ this.id
							+ ", "
							+ hcsp.getPK_SEQHoaChatKiemDinh()
							+ ", "
							+ hcsp.getsoLuongChuan()
							+ ", "
							+ hcsp.getSoLuongChatKiemDinh()
							+ ")";
					System.out.println(query);
					//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
					if (!db.update(query)) {
						this.msg = "13.Lỗi dòng lệnh  : " + query;
						this.db.update("rollback");
						return false;
					}
				}
			}
			
			for (int i = 0; i < this.mayMocKiemDinhList.size(); i++) {
				IMayMoc_SanPham mmkd = this.mayMocKiemDinhList.get(i);

				if (mmkd.getMayMocKiemDinh().getId().trim().length() > 0) {
					query = "insert into [dbo].[MAYMOC_SANPHAM]\r\n" + 
							"           ([SANPHAM_FK]\r\n" + 
							"           ,[TAISANCODINH_FK]) "
							+ "values ("
							+ this.id
							+ ", "
							+ mmkd.getMayMocKiemDinh().getId()
							+ ")";
					//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
					System.out.println(query);
					if (!db.update(query)) {
						this.msg = "13.Lỗi dòng lệnh  : " + query;
						this.db.update("rollback");
						return false;
					}
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "ERP_SANPHAM", "SANPHAM", "PK_SEQ", id, "0", false);
			UtilitySyn.SynData(db, "QUYCACH", "QUYCACH", "SANPHAM_FK", id, "0", false);
			
			//Tam thời bên DMS dùng tên nội bộ
			/*query = " update a set a.ten = isnull(b.ten1, b.ten), a.TimKiem = dbo.ftBoDau( a.ma + ' ' + isnull(b.ten1, b.ten) + ' ' + isnull(c.DONVI, '') )  "+
					" from LINK_DMS_THAT.DataCenter.dbo.SANPHAM a inner join ERP_SANPHAM b on a.pk_seq = b.pk_seq "+
					" 	left join DONVIDOLUONG c on a.dvdl_fk = c.pk_seq " + 
					" where a.pk_seq = '" + this.id + "' ";
			db.update(query);*/
			
			//Đồng bộ khai báo qua bên DMS thật, qua đó sẽ vào cạp nhật lại thông tin
			/*query = " insert LINK_DMS_THAT.DataCenter.dbo.SANPHAM( PK_SEQ, MA, TEN, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, DVDL_FK, TRANGTHAI, DVKD_FK, NHANHANG_FK, CHUNGLOAI_FK, TYPE, TRONGLUONG, THETICH, LOAISANPHAM_FK, LOAIGIATON, GIANHAPLANDAU, QUANLYLO, HANSUDUNG, PACKSIZE_FK, NGANHHANG_FK, TimKiem ) "+
					" select PK_SEQ, MA, TEN, NGAYTAO, NGAYSUA, 100002 NGUOITAO, 100002 NGUOISUA, DVDL_FK, 0 as TRANGTHAI, DVKD_FK, NHANHANG_FK, CHUNGLOAI_FK, TYPE, TRONGLUONG, THETICH, LOAISANPHAM_FK, LOAIGIATON, GIANHAPLANDAU, QUANLYLO, HANSUDUNG, PACKSIZE_FK, NGANHHANG_FK, TimKiem "+
					" from ERP_SANPHAM  "+
					" where pk_seq = '" + this.id + "' ";
			db.update(query);*/
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.msg = e.getMessage();
			
			try 
			{
				db.getConnection().rollback();
			} 
			catch (Exception e1) {}
			
			return false;
		}
		
		return true;
	}
	// định lượng
	private boolean CheckIsNot_TieuChiDL_NCC() {

		for (int i=0;i<this.ThongtinNCCList.size();i++){
			IThongtinNCC ttncc=this.ThongtinNCCList.get(i);
					List<ITieuchikiemdinh> tckdList_ncc =ttncc.getTieuchikiemdinhDinhluongList();
					for(int k=0;k<tckdList_ncc.size();k++){
						ITieuchikiemdinh tckd=tckdList_ncc.get(k);
						if (tckd.getTieuchi().trim().length() > 0) {
							 return false;
						}
					}
					
					 
		} 
		return true;
	
	}
	// định tính
	private boolean CheckIsNot_TieuChiDT_NCC() {
		for (int i=0;i<this.ThongtinNCCList.size();i++){
			
			IThongtinNCC ttncc=this.ThongtinNCCList.get(i);
				  
					List<ITieuchikiemdinh>  tckdDinhtinhList_ncc =ttncc.getTieuchikiemdinhDinhtinhList();
					for ( int  k = 0; k <  tckdDinhtinhList_ncc.size(); k++) {
						ITieuchikiemdinh tckd =  tckdDinhtinhList_ncc.get(k);
			
						if (tckd.getTieuchi().trim().length() > 0) { 
							return false;
						}
					}
		} 
		return true;
	}

	private boolean KiemTraThayDoiDVDL(String dvdl1_fk,String soluong1,String dvdl2_fk,String soluong2) {
		try{
			
			String query=	" select SANPHAM_FK,DVDL1_FK,SOLUONG1,DVDL2_FK,SOLUONG2, dvdl.donvi from quycach qc " +
							" inner join donvidoluong dvdl on dvdl.pk_seq=dvdl2_fk  where " +
							" dvdl1_fk = "+ dvdl1_fk+ 
							" and  dvdl2_fk = "+dvdl2_fk  +" " +
							" and sanpham_fk ="+this.id ;
			 String donvi=""; 
			boolean Iscothaydoiqc=false;
			ResultSet rs=db.get(query);
			if(rs.next()){
				donvi=rs.getString("donvi");
				
				if(rs.getDouble("SOLUONG1")!= Double.parseDouble(soluong1) || rs.getDouble("soluong2")!=Double.parseDouble(soluong2)){
					Iscothaydoiqc=true;	 
				}
				 
			}
			rs.close();
				if(Iscothaydoiqc ){
					query=  " SELECT DVDL.PK_SEQ FROM ERP_MUAHANG_SP  MHSP "+
							" INNER JOIN DONVIDOLUONG DVDL ON DVDL.DONVI=MHSP.DONVI WHERE "+  
							" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdl2_fk+" "+
							" union all "+
							" SELECT DVDL.PK_SEQ FROM ERP_DONDATHANG_SP  dhsp "+ 
							" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=dhsp.DVDL_FK WHERE "+  
							" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdl2_fk+" "+
							" union all "+
							" SELECT DVDL.PK_SEQ FROM ERP_NHAPKHO_SANPHAM  nksp "+ 
							" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=nksp.DVDL_FK WHERE "+  
							" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdl2_fk+" "+
							" union all "+
							" SELECT DVDL.PK_SEQ FROM ERP_NHAPKHO_SANPHAM  nksp "+ 
							" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=nksp.DVDL_MAU_FK WHERE "+  
							" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdl2_fk+" "+
							" union  "+
							" SELECT DVDL.PK_SEQ FROM ERP_HOADON_SP  hdsp "+ 
							" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=hdsp.DVDL_FK WHERE "+  
							" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdl2_fk+"";
			
					ResultSet rs1=db.get(query);
					 if(rs1.next()){
						 // retun false;
						 this.msg="Đã có dữ liệu phát sinh của quy đổi ra :"+donvi+". Bạn không thể thay đổi quy cách này";
						 return false;
					 }
					 rs1.close();
			
					 
				 
				}
			
			
			
			}catch(Exception er){
				this.msg=er.getMessage();
				return false;
			}
		
		
		return true;
	}

	public boolean CheckDVDL() {
		 	
		try{
			if(this.id.length() > 0){
			String sql="select pk_seq from erp_sanpham where pk_seq="+this.id;
			
			ResultSet rs=db.get(sql);
			if(rs.next()){
				sql=" select top 1 SANPHAM_FK from ERP_TONKHOTHANG where soluong >0 and SANPHAM_FK= "+this.id +
					" union "+
					" select top 1 SANPHAM_FK from ERP_MUAHANG_SP where SANPHAM_FK= "+this.id + 
					" union "+
					" select top 1 SANPHAM_FK from ERP_NHANHANG_SANPHAM where SANPHAM_FK= "+this.id +
					" union " +
					"  select top 1 SANPHAM_FK from ERP_CHUYENKHO_SANPHAM   where SANPHAM_FK= "+this.id +
					" union "+
					" select top 1 SANPHAM_FK from ERP_DONDATHANG_SP where SANPHAM_FK= "+this.id ;
			System.out.println("SQL : "+sql);
				rs=db.get(sql);
				if(rs.next()){
					return true;
				}
				
				rs.close();
				return false;
			}else{
				return false;
			}
			}
			return true;
		}catch(Exception er){
			er.printStackTrace();
			return false;
		}
		
	}
	
	public boolean UpdateSp()
	{
		try
		{
			
			String query1="select distinct a.masanpham from ERP_DANHMUCVATTU a  where a.TRANGTHAI='1' and a.sanpham_fk='"+this.id+"'";
			ResultSet rsSanpham=db.get(query1);
			if(rsSanpham!=null){
				if(rsSanpham.next()){
					if(!this.masp.equals(rsSanpham.getString("masanpham"))){
						this.msg = "Mã sản phẩm này đã có trong danh mục vật tư không thể chỉnh sửa được!";
						return false;
					}
				}
			}
			
			if (this.masp.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập mã sản phẩm";
				return false;
			} 
			else if (masanpham())
			{
				this.msg = "Mã sản phẩm bị trùng";
				return false;
			}
			else if (this.tennoibo.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập tên nội bộ của sản phẩm";
				return false;
			}
			/*else if (this.tenthuongmai.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập tên thương mại của sản phẩm";
				return false;
			}*/
			else if (this.ten.trim().length() == 0)
			{
				this.msg = "Vui lòng nhập tên sản phẩm";
				return false;
			} 
			/*else if (this.dvkdId.length()<=0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}*/
			/*else if (this.nhId.length()<=0)
			{
				this.msg = "Vui lòng chọn nhãn hàng";
				return false;
			}*/
			else if (this.loaispma.contains("TP")) 
			{
				/*if ( Double.parseDouble(this.GiaMua) <= 0 )
				{
					this.msg = "Vui lòng nhập giá mua sản phẩm";
					return false;
				}*/
			}
			
			//TẠM BỎ KHÔNG CHECK
			/*if(this.Batbuockiemdinh.equals("1")){
				if(this.kiemtradinhluong.equals("0") && this.kiemtradinhtinh.equals("0")){
					this.msg = "Vui lòng chọn tiêu chí kiểm định hoặc định tính ";
					return false;
				}
			}
			
			if (this.kiemtradinhluong.equals("1")) 
			{
				if (this.tckdDinhluongList.size() <= 0 && this.CheckIsNot_TieuChiDL_NCC()) 
				{
					this.msg = "Vui lòng nhập kiểm tra định lượng";
					return false;
				}
				else 
				{
					if(!this.CheckIsNot_TieuChiDL_NCC()){
							for (int i = 0; i < this.tckdDinhluongList.size(); i++) 
							{
								ITieuchikiemdinh tckd = this.tckdDinhluongList.get(i);
								if (tckd.getTieuchi().trim().length() > 0) 
								{
									if (tckd.getToantu().trim().length() <= 0 || tckd.getGiatrichuan().trim().length() <= 0)
									{
										this.msg = "Vui lòng kiểm tra lại toán tử và giá trị chứa các tiêu chí: " + tckd.getTieuchi();
										return false;
									}
								}
							}
					}
				}
			}

			if (this.kiemtradinhtinh.equals("1") ) 
			{
				
					if (this.tckdDinhtinhList.size() <= 0 && this.CheckIsNot_TieuChiDT_NCC() )
					{
						this.msg = "Vui lòng nhập kiểm tra định tính.";
						return false;
					}
				
			}*/
			
			//check loai sp: sp da phat sinh nhap xuat thi khong duoc thay doi loai sp
			this.db.getConnection().setAutoCommit(false);
			
			ResultSet rs = this.db.get("select loaisanpham_fk from erp_sanpham where pk_seq=" + this.id);
			String loaisphientai = "";
			if (rs!=null)
				while (rs.next()) loaisphientai = rs.getString("loaisanpham_fk");
			
			if (!loaisphientai.equals(this.loaispId) && this.checkThayDoiLoaiSanpham()){
					this.msg = "Sản phẩm đã phát sinh nhập/xuất kho, không thể thay đổi loại sản phẩm";
					return false;
				}
			
			this.ngaysua = getDateTime();
			this.nguoisua = this.userId;
			String clId = this.clId == null || this.clId.trim().length() == 0 ? null : this.clId.trim();
			String nhId = this.nhId == null || this.nhId.trim().length() == 0 ? null : this.nhId.trim();
			String dvdlId = this.dvdlId == null || this.dvdlId.trim().length() == 0 ? null : this.dvdlId.trim();
			String loaispId = this.loaispId == null || this.loaispId.trim().length() == 0 ? null : this.loaispId.trim();
//			String loaispma = this.loaispma == null || this.loaispma.trim().length() == 0 ? null : this.loaispma.trim();
			String packsizeId = this.packsizeId == null || this.packsizeId.trim().length() == 0 ? null : this.packsizeId.trim();
			String dvkdId = this.dvkdId == null || this.dvkdId.trim().length() == 0 ? null : this.dvkdId.trim();
			String kl = this.kl == null || this.kl.trim().length() == 0 ? "0" : this.kl.trim();
			String tt = this.tt == null || this.tt.trim().length() == 0 ? "0" : this.tt.trim();
			
//			String giablc = this.giablc == null || this.giablc.trim().length() == 0 ? "0" : this.giablc.trim();
			
			if(this.Check_Vothoihan.equals("1")){
				this.HanSuDung="36500";
				
			}
			
			String hsd = this.HanSuDung == null || this.HanSuDung.trim().length() == 0 ? "0" : this.HanSuDung.trim();
			
			String command ="\n update ERP_SANPHAM set IS_KHONGTHUE = '"+this.is_khongthue+"',NGAYHOANTHANH='"+this.Ngayhoanthanh+"', SONGAYHANCANHBAO ="+this.Songayhancanhbao+",IS_VOTHOIHAN ='"+this.Check_Vothoihan+"', " +
							"\n batbuockiemdinh='"+this.Batbuockiemdinh+"',  ten = N'" + this.ten + "',ten1 = N'" + this.tennoibo + "', ngaysua = '" + this.ngaysua + "', nguoisua = '" + this.userId + "', " +
							"\n dvdl_fk = " + dvdlId + ", trangthai = '" + this.trangthai + "', dvkd_fk = " + dvkdId + ", nhanhang_fk = " + nhId + ", " +
							"\n chungloai_fk=" + clId + ", type = '" + this.type + "', trongluong='" + kl + "', thetich = '" + tt + "', loaisanpham_fk = " + loaispId + ", tonkhoantoan = '" + this.tonkhoantoan + "', packsize_fk = " + packsizeId + ", " +
							"\n chiphinhancong = '" + this.cpnc + "', chiphivanchuyen = '" + this.cpvc + "', kiemtradinhtinh = '" + this.kiemtradinhtinh + "', kiemtradinhluong = '" + this.kiemtradinhluong + "', hansudung = '" + hsd  + "', " +
							"\n HANGBO = " + this.isHangbo + ", FILEPATH = '" + this.pathHinhanhSp + "', FILENAME = '" + this.nameHinhanhSp + "', " +
							"\n GIAMUA="+this.GiaMua+" , PHEPHAM_FK="+(PPId.length() >0? PPId: "NULL")+", THUEXUAT="+this.thueSuat+", MA=N'"+this.masp+"' , TENTHUONGMAI=N'"+this.tenthuongmai+"',ischietkhau='"+this.ischietkhau+"',"
							+ "\n THIETBICAN='"+this.thietBiCan+"',THIETBICANKHAC='"+this.thietBiCanKhac+"',YEUCAUNL=N'"+this.ycnlsx+"', MOTACAMQUAN=N'"+this.motaSp+"', YEUCAUDONGGOI=N'"+this.yeucaudonggoi+"', DACTINHKYTHUAT=N'"+this.dactinhkythuat+"',"
							+ " CONGTHUCHOAHOC=N'"+this.congthuchoahoc+"', NHOMLUUTRU=N'"+this.nhomluutru+"', MUCDONGUYHIEM='"+this.mucdonguyhiem+"', KHUVUCBAOQUAN=N'"+this.khuvucbaoquan+"', KHONGQUANLYSL = "+this.khongqlsl+" where pk_seq = '" + this.id + "'";
			System.out.println(" update san pham: "+ command);
			if (!this.db.update(command))
			{
				this.msg="Không thể thực hện câu lệnh: "+command;
				this.db.update("rollback");
				return false;
			}
			
			// Cập nhật thêm tckt, dạng bào chế, loại hàng hóa
			command = " update ERP_SANPHAM set TCKT = N'"+ this.tckt +"',LOAIHANGHOA='"
			+ this.loaiHangHoa+"',DANGBAOCHE= '"+ this.dangBaoChe +"', KIEMTRAOE= "+ this.kiemtraoe +"  where PK_SEQ =" + this.id;
			
			if(!db.update(command))
			{
				this.msg = command;
				this.db.update("rollback");
				return false;
			}
			
			
			command = "delete from nhomsanpham_sanpham where sp_fk ='" + this.id + "'";
			 
			if (!this.db.update(command))
			{
				this.db.update("rollback");
				return false;
			}
			
			
			if (this.nspIds.trim().length() > 0)
			{
				command = "insert nhomsanpham_sanpham(sp_fk, nsp_fk)  " +
						  "select '" + this.id + "', pk_seq from NhomSanPham where pk_seq in (" + this.nspIds + ")";
				 
				if (!this.db.update(command))
				{
				}	
			}
			
			if (this.type.equals("1"))
			{
				command = "delete from Bundle_Sanpham where bundle_fk='" + this.id + "'";
				 
				if (!this.db.update(command))
				{
				}
				
				String[] spArr = this.spIds.split(",");
				
				for(int j = 0; j < spArr.length; j++)
				{
					int soluong =  0;//int soluong =this.bundle_soluong.get(spArr[j]) == null ? 0 : this.bundle_soluong.get(spArr[j]);
					
					command = "insert into Bundle_Sanpham(bundle_fk, sanpham_fk, soluong) " +
							  "values('" + this.id + "', '" + spArr[j] + "', '" + soluong + "')";
				 
					if (!this.db.update(command))
					{
					}
				}
			}
			
			if(this.dvkdId.trim().length() > 0) 
			{
				String query = "select pk_seq from erp_banggiaban where dvkd_fk = '" + this.dvkdId + "'";
				System.out.println("::: LAY BANG GIA: " + command);
				 
				rs = this.db.get(query);
				String bgblcId = "";
				if (rs != null)
				{
					if(rs.next()){
						bgblcId = rs.getString("pk_seq");
					}
				}
				if (bgblcId.length() > 0) {
				 
					command = "insert into ERP_BGBAN_SANPHAM(sanpham_fk,bgban_fk,giaban) values('" + this.id + "','" + bgblcId + "','" + this.giablc + "') ";
					System.out.println("::: CHEN BANG GIA: " + command);
					
					if (!(this.db.update(command)))
					{
						command = "update ERP_BGBAN_SANPHAM set giaban = '" + this.giablc + "' where sanpham_fk = '" + this.id + "' and bgban_fk = '" + bgblcId + "'";
				 
						if (!(this.db.update(command)))
						{
							this.msg = command;
							this.db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			command = "delete from quycach where sanpham_fk='" + this.id + "'";
			if (!(this.db.update(command)))
			{
				this.msg = command;
				this.db.getConnection().rollback();
				return false;
			}
			
			boolean error = true;
			boolean checkDoiQC = false;
			
			boolean flag= this.KiemTraThayDoiDVDL();
			
			if(!flag) {
				this.db.getConnection().rollback();
				return false;
			}
	 
			for (int i = 0; i < 5; i++)
			{
				if ((sl1[i].length() > 0) && (sl2[i].length() > 0) && (dvdl1[i].length() > 0) && (dvdl2[i].length() > 0))
				{
					command = "insert into quycach(sanpham_fk, dvdl1_fk, soluong1, dvdl2_fk, soluong2) " +
							"values('" + this.id + "','" + this.dvdl1[i] + "','" + sl1[i] + "','" + dvdl2[i] + "','" + sl2[i] + "')";
					checkDoiQC=true;
					if (!(this.db.update(command)))
					{
						this.msg = "Loi insert: " + command;
						this.db.getConnection().rollback();
						return false;
					}
					if (dvdl2[i].equals("100018")) 
						error = false;
				}
			}
			
			String query="";
			
			// xoa kho TT Canfoco
			
			/*query = "delete from erp_sanpham_nhacungcap where sanpham_fk = '"+ this.id +"'";
			if (!db.update(query))
			{
				this.msg = query;
				db.getConnection().rollback();
				return false;
			}*/
			
			// Them vao kho TT Canfoco
			if(!this.khongqlsl.equals("1")){
			String kho = "(";
			for (int i=0;i<this.khovung.length;i++)
				if (this.khovung[i] != null && this.khovung[i].length()>0) kho+="'"+this.khovung[i]+"',";
			kho = kho.substring(0, kho.length()-1) + ")";
			
			if( kho.length() > 2 && !loaispId.equals("NULL") )
			{
			
				// them khott_sanpham
				command = " insert erp_khott_sanpham(KHOTT_FK, SANPHAM_FK, GIATON, SOLUONG, BOOKED, AVAILABLE) " +
						  " select pk_seq, '" + this.id + "', '0', '0', '0', '0' " + 
						  " from ERP_KHOTT " + 
						  " where pk_seq in ( select khott_fk from ERP_KHOTT_LOAISANPHAM where loaisanpham_fk = '" + loaispId + "' ) and pk_seq in " + kho +
						  " and pk_seq not in (select distinct khott_fk from erp_khott_sanpham where sanpham_fk=" +this.id +")";
				
				System.out.println("[Thongtinsanpham.UpdateSp] command khott = " + command);
				
				if (!this.db.update(command))
				{
					this.msg = "Chưa chọn kho." + command;
					db.getConnection().rollback();
					return false;
				}
				command = " insert erp_khott_sanpham(KHOTT_FK, SANPHAM_FK, GIATON, SOLUONG, BOOKED, AVAILABLE) " +
				  		  " select pk_seq, '" + this.id + "', '0', '0', '0', '0' from ERP_KHOTT where pk_seq not in  (select khott_fk from erp_khott_sanpham where sanpham_fk="+this.id+")  and  pk_seq in " + kho ;
				  
				this.db.update(command) ;  
			}
			}
			
			if (this.loaispma.contains("TP")) //thanh pham
			{
				/*command = "insert erp_sanpham_nhacungcap(sanpham_fk,hancongbo,hinhcongbo,filename) " +
				  " values('"+ this.id +"','"+ this.hancongboTP +"','" + this.hinhcongboTP + "','" + this.filenamecbTP + "')";
				if (!this.db.update(command))
				{
					this.msg = command;
					db.getConnection().rollback();
					return false;
				}*/
				
				if (this.bantp!=null)
				{
					//them ban thanh pham
					String btp = "(";
					for (int i=0;i<this.bantp.length;i++)
						if (this.bantp[i] != null && this.bantp[i].length()>0) btp+="'"+this.bantp[i]+"',";
					btp = btp.substring(0, btp.length()-1) + ")";
					
					if(btp.length()> 2){
						command = "insert erp_sanpham_btp(sp_fk,btp_fk) " +
						  "select '" + this.id + "', pk_seq from ERP_sanpham where pk_seq in "+btp;
						//System.out.println("[Thongtinsanpham.CreateSp] command = " + command);
						if (!this.db.update(command))
						{
							this.msg = "Chưa chọn bán thành phẩm." + command;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			query = "delete SanPham_TieuChiKiemDinh where SANPHAM_FK = '" + this.id + "' ";
			if (!db.update(query)) {
				this.msg = "9.Lỗi dòng lệnh : " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			//Cap nhat thong tin hoa chat va may moc cua SANPHAM
			if (!this.updateHoaChatMayMocKiemDinhList()) {
				return false;
			}
			
			//cap nhap thong tin nha cung cap - undone
			if((this.loaispma.contains("VT")||this.loaispma.contains("BB")||this.loaispma.contains("NL")||this.loaispma.contains("VLP")) && this.nhacungcap!=null )
			{
				for (int i=0;i<this.ThongtinNCCList.size();i++){
					
					IThongtinNCC ttncc=this.ThongtinNCCList.get(i);
						
					/*command = "insert erp_sanpham_nhacungcap(sanpham_fk,nhacungcap_fk,thoihangiaohang,luongdattoithieu,hancongbo,hinhcongbo,filename) " +
					  " values('"+ this.id +"','" + ttncc.getnhacungcap()+ "'," +ttncc.getthoihangiaohang()+ ","+ttncc.getluongdattoithieu()+",'"+ ttncc.gethancongbo() +"','" +ttncc.gethinhcongbo() + "','" +ttncc.getfilenamecb()+ "')";
					if (!this.db.update(command))
					{
						 
						this.msg = command;
						db.getConnection().rollback();
						return false;
					}*/
					
					List<ITieuchikiemdinh> tckdList_ncc =ttncc.getTieuchikiemdinhDinhluongList();
					for(int k=0;k<tckdList_ncc.size();k++){
						ITieuchikiemdinh tckd=tckdList_ncc.get(k);
						if (tckd.getTieuchi().trim().length() > 0) {
							query = " insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai,NCC_FK) "
									+ " select pk_seq, N'" + tckd.getTieuchi() + "'"
									+ ", '" + tckd.getToantu()	+ "'"
									+ ", '"+ tckd.getGiatrichuan()	+ "' " 
									+ ", '0',"+ttncc.getnhacungcap()+" "
									+ " from erp_sanpham where pk_seq = '" + this.id + "' ";
						 
							if (!db.update(query)) {
								this.msg = "12.Không thể tạo mới SanPham_TieuChiKiemDinh: " + query;
								this.db.update("rollback");
								return false;
							}
						}
					}
					
					List<ITieuchikiemdinh>  tckdDinhtinhList_ncc =ttncc.getTieuchikiemdinhDinhtinhList();
					for ( int  k = 0; k <  tckdDinhtinhList_ncc.size(); k++) {
						ITieuchikiemdinh tckd =  tckdDinhtinhList_ncc.get(k);
			
						if (tckd.getTieuchi().trim().length() > 0) {
							query = "insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai,NCC_FK) "
									+ "select pk_seq, N'"
									+ tckd.getTieuchi()
									+ "', '"
									+ tckd.getToantu()
									+ "', '"
									+ tckd.getGiatrichuan()
									+ "', '1',"+ttncc.getnhacungcap()+" "
									+ "from erp_sanpham where pk_seq = '" + this.id + "' ";
							//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
							if (!db.update(query)) {
								this.msg = "13.Không thể tạo mới SanPham_TieuChiKiemDinh: " + query;
								this.db.update("rollback");
								return false;
							}
						}
					}
					
					
					//Insert vao bang HOACHAT_SANPHAM cua nha cung cap
					Iterator<IHoaChat_SanPham> iteratorHoaChat_NCC = ttncc.getHoaChatKiemDinhList().iterator();
					while (iteratorHoaChat_NCC.hasNext()) {
						IHoaChat_SanPham elementHoaChat_NCC = iteratorHoaChat_NCC.next();
						if (elementHoaChat_NCC.getPK_SEQHoaChatKiemDinh().trim().length() > 0) {
							ttncc.init();
							if (elementHoaChat_NCC.getSoLuongChatKiemDinh() == 0) {
								this.msg = "Vui lòng nhập số lượng của hóa chất "+elementHoaChat_NCC.getTenHoaChatKiemDinh()+" của nhà cung cấp "+ttncc.getTenNhaCungCap()+" trong mục \"Thông tin nhà cung cấp \"";
								return false;
							}
							if (elementHoaChat_NCC.getsoLuongChuan() == 0) {
								this.msg = "Vui lòng nhập số lượng chuẩn của nhà cung cấp "+ttncc.getTenNhaCungCap()+" trong mục \"Thông tin nhà cung cấp \"";
								return false;
							}	
							query = "insert into HOACHAT_SANPHAM(SANPHAM_FK, HOACHAT, SOCHATDUOCKIEMDINH, SOCHATKIEMDINH, NCC_FK) "
									+ "values ("
									+ this.id
									+ ", "
									+ elementHoaChat_NCC.getPK_SEQHoaChatKiemDinh()
									+ ", "
									+ elementHoaChat_NCC.getsoLuongChuan()
									+ ", "
									+ elementHoaChat_NCC.getSoLuongChatKiemDinh()
									+ ", "
									+ elementHoaChat_NCC.getNhaCungCap().getnhacungcap()
									+ ")";
							if (!db.update(query)) {
								this.msg = "13.Lỗi dòng lệnh  : " + query;
								this.db.update("rollback");
								return false;
							}
						}
					}
					
					
					//Insert may moc kiem dinh vao bang MAYMOC_SANPHAM cua nha cung cap khi CAP NHAT
					Iterator<IMayMoc_SanPham> iteratorMayMoc_NCC = ttncc.getMayMocKiemDinhList().iterator();
					while (iteratorMayMoc_NCC.hasNext()) {
						IMayMoc_SanPham elementMayMoc_NCC = iteratorMayMoc_NCC.next();
						if (elementMayMoc_NCC.getMayMocKiemDinh().getId().trim().length() > 0) {
							query = "insert into [dbo].[MAYMOC_SANPHAM]\r\n" + 
									"           ([SANPHAM_FK]\r\n" + 
									"           ,[TAISANCODINH_FK]"
									+ "         ,[NCC_FK]) "
									+ "values ("
									+ this.id
									+ ", "
									+ elementMayMoc_NCC.getMayMocKiemDinh().getId()
									+ ", "
									+ elementMayMoc_NCC.getNhaCungCap().getnhacungcap()
									+ ")";
							//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
							System.out.println(query);
							if (!db.update(query)) {
								this.msg = "13.Lỗi dòng lệnh  : " + query;
								this.db.update("rollback");
								return false;
							}
						}
					}// Ket thuc insert may moc kiem dinh vao bang MAYMOC_SANPHAM cua nha cung cap khi CAP NHAT
				} 
			}
 
			query  = "insert nhanvien_sanpham(nhanvien_fk, sanpham_fk) " +
					"select distinct nhanvien_fk, '" + this.id + "' as sanpham_fk from phanquyen " +
					" where nhanvien_fk not in (select nhanvien_fk from nhanvien_sanpham where sanpham_fk = '" + this.id + "')  " +
					" and dmq_fk in (select dmq_fk from nhomquyen where ungdung_fk = '8')" +
					" and nhanvien_fk in (select pk_seq from nhanvien where trangthai = '1')";
 
			if (!db.update(query))
			{
				this.msg = query;
				db.getConnection().rollback();
				return false;
			}
			command=    " UPDATE SP SET  TIMKIEM = upper(dbo.ftBoDau(MA)) " +  
						" +'-'+ upper(dbo.ftBoDau(isnull(SP.ten1,'')))   " +  
						" +'-'+ upper(dbo.ftBoDau(isnull(SP.ten,'')))   " +  
					    " +'-'+ upper(dbo.ftBoDau(isnull(DVDL.DONVI,'')))  " +  
					    " from ERP_SANPHAM SP INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK  " +  
					    " where SP.PK_SEQ="+this.id;
			if(!db.update(command))
			{
				this.msg = command;
				this.db.update("rollback");
				return false;
			}
			
			for (int i = 0; i < this.tckdDinhluongList.size(); i++) 
			{
				ITieuchikiemdinh tckd = this.tckdDinhluongList.get(i);
	
				if (tckd.getTieuchi().trim().length() > 0) {
					query = " insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai) "
							+ " select pk_seq, N'" + tckd.getTieuchi() + "'"
							+ ", '" + tckd.getToantu()	+ "'"
							+ ", '"+ tckd.getGiatrichuan()	+ "' " 
							+ ", '0' "
							+ " from erp_sanpham where pk_seq = '" + this.id + "' ";
					 
					if (!db.update(query)) {
						this.msg = "12.Không thể tạo mới SanPham_TieuChiKiemDinh: " + query;
						this.db.update("rollback");
						return false;
					}
				}
			}
	
			for (int i = 0; i < this.tckdDinhtinhList.size(); i++) {
				ITieuchikiemdinh tckd = this.tckdDinhtinhList.get(i);
	
				if (tckd.getTieuchi().trim().length() > 0) {
					query = "insert into SANPHAM_TIEUCHIKIEMDINH(SANPHAM_FK, TieuChi, pheptoan, giatrichuan, loai) "
							+ "select pk_seq, N'"
							+ tckd.getTieuchi()
							+ "', '"
							+ tckd.getToantu()
							+ "', '"
							+ tckd.getGiatrichuan()
							+ "', '1' "
							+ "from erp_sanpham where pk_seq = '" + this.id + "' ";
					//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
					if (!db.update(query)) {
						this.msg = "13.Không thể tạo mới SanPham_TieuChiKiemDinh: " + query;
						this.db.update("rollback");
						return false;
					}
				}
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			//SYN QUA DMS
			UtilitySyn.SynData(db, "ERP_SANPHAM", "SANPHAM", "PK_SEQ", id, "1", false);
			UtilitySyn.SynData(db, "QUYCACH", "QUYCACH", "SANPHAM_FK", id, "2", true);
			UtilitySyn.SynData(db, "QUYCACH", "QUYCACH", "SANPHAM_FK", id, "0", false);
			
			//Tam thời bên DMS dùng tên nội bộ
			/*query = " update a set a.ten = isnull(b.ten1, b.ten), a.TimKiem = dbo.ftBoDau( a.ma + ' ' + isnull(b.ten1, b.ten) + ' ' + isnull(c.DONVI, '') )  "+
					" from LINK_DMS_THAT.DataCenter.dbo.SANPHAM a inner join ERP_SANPHAM b on a.pk_seq = b.pk_seq "+
					" 	left join DONVIDOLUONG c on a.dvdl_fk = c.pk_seq " + 
					" where a.pk_seq = '" + this.id + "' ";
			db.update(query);*/
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "Vui Long Kiem Tra Lai Du Lieu .Xay Ra Loi Sau :" + e.toString();
			return false;
		}
		return true;
	}
	
	// Ham chua cau SQL cho hoa chat va may moc kiem dinh CUA SAN PHAM
	private boolean updateHoaChatMayMocKiemDinhList() throws SQLException {
		String query = "DELETE HOACHAT_SANPHAM WHERE SANPHAM_FK = " + this.id;
		if(!this.db.update(query)){
			this.db.getConnection().rollback();
			this.msg="Không thể thực hiện dòng lệnh ; "+query + "\n";
			return false;	
		}
		
		query = "DELETE MAYMOC_SANPHAM WHERE SANPHAM_FK = " + this.id;
		if(!this.db.update(query)){
			this.db.getConnection().rollback();
			this.msg="Không thể thực hiện dòng lệnh ; "+query + "\n";
			return false;	
		}
		
		for (int i = 0; i < this.hoaChatKiemDinhList.size(); i++) {
			IHoaChat_SanPham hcsp = this.hoaChatKiemDinhList.get(i);

			if (hcsp.getPK_SEQHoaChatKiemDinh().trim().length() > 0) {
				int soLuongChuan = hcsp.getsoLuongChuan();
				if (soLuongChuan == 0) {
					this. msg = "Vui lòng nhập số lượng chuẩn trong mục \"Hóa chất kiểm định\"";
					return false;
				} 
				if (hcsp.getSoLuongChatKiemDinh() == 0) {
					this. msg = "Vui lòng nhập số lượng của hóa chất "+hcsp.getTenHoaChatKiemDinh().trim()+" trong mục \"Hóa chất kiểm định\"";
					return false;
				}
				query = "insert into HOACHAT_SANPHAM(SANPHAM_FK, HOACHAT, SOCHATDUOCKIEMDINH, SOCHATKIEMDINH) "
						+ "values ("
						+ this.id
						+ ", "
						+ hcsp.getPK_SEQHoaChatKiemDinh()
						+ ", "
						+ soLuongChuan
						+ ", "
						+ hcsp.getSoLuongChatKiemDinh()
						+ ")";
				System.out.println(query);
				//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
				if (!db.update(query)) {
					this.msg = "13.Lỗi dòng lệnh  : " + query;
					this.db.update("rollback");
					return false;
				}
			}
		}
		
		for (int i = 0; i < this.mayMocKiemDinhList.size(); i++) {
			IMayMoc_SanPham mmkd = this.mayMocKiemDinhList.get(i);

			if (mmkd.getMayMocKiemDinh().getId().trim().length() > 0) {
				query = "insert into [dbo].[MAYMOC_SANPHAM]\r\n" + 
						"           ([SANPHAM_FK]\r\n" + 
						"           ,[TAISANCODINH_FK]) "
						+ "values ("
						+ this.id
						+ ", "
						+ mmkd.getMayMocKiemDinh().getId()
						+ ")";
				//System.out.println("[Thongtinsanpham.UpdateSp] query = " + query);
				System.out.println(query);
				if (!db.update(query)) {
					this.msg = "13.Lỗi dòng lệnh  : " + query;
					this.db.update("rollback");
					return false;
				}
			}
		}
		return true;
	}
	
	
	private boolean KiemTraThayDoiDVDL() {
		try{
			
			String iddvdlnew="";
			
			for (int i = 0; i < 5; i++)
			{
				if ((sl1[i].length() > 0) && (sl2[i].length() > 0) && (dvdl1[i].length() > 0) && (dvdl2[i].length() > 0))
				{
					
					if(i==0){
						iddvdlnew=  dvdl2[i];
					}else{
						iddvdlnew=iddvdlnew+","+ dvdl2[i];
					}
					System.out.println( " 323232 :  "+dvdl1[i] +"djdjajd : "+this.dvdlId );
					if(!dvdl1[i].trim().equals(this.dvdlId.trim()) ){
						this.msg="Vui lòng chỉ chọn đơn vị đo lường đổi là đơn vị chuẩn ";
						return false;
					}
				}
			}
			
			// có trong database nhưng ở ngoài đã không còn chọn dvdl này để quy đổi,thì kiểm tra dvdl2_fk cũ có phát sinh dữ liệu chưa?
			if(iddvdlnew.length() >0){
			String query=" select dvdl2_fk,dvdl.donvi from quycach qc inner join donvidoluong dvdl on dvdl.pk_seq=qc.dvdl2_fk" +
						 " where  sanpham_fk ="+this.id + " and dvdl2_fk not in ("+iddvdlnew+")";
			
			ResultSet rsdvdlcu=db.get(query);
			while(rsdvdlcu.next()){
				String dvdlid1=rsdvdlcu.getString("dvdl2_fk");
				String donvi=rsdvdlcu.getString("donvi");
				// kiểm tra xem dvdl2_fk cũ này có nằm trong 
				query=  " SELECT DVDL.PK_SEQ FROM ERP_MUAHANG_SP  MHSP "+
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.DONVI=MHSP.DONVI WHERE "+  
						" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdlid1+" "+
						" union all "+
						" SELECT DVDL.PK_SEQ FROM ERP_DONDATHANG_SP  dhsp "+ 
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=dhsp.DVDL_FK WHERE "+  
						" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdlid1+" "+
						" union all "+
						" SELECT DVDL.PK_SEQ FROM ERP_NHAPKHO_SANPHAM  nksp "+ 
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=nksp.DVDL_FK WHERE "+  
						" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdlid1+" "+
						" union all "+
						" SELECT DVDL.PK_SEQ FROM ERP_NHAPKHO_SANPHAM  nksp "+ 
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=nksp.DVDL_MAU_FK WHERE "+  
						" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdlid1+" "+
						" union  "+
						" SELECT DVDL.PK_SEQ FROM ERP_HOADON_SP  hdsp "+ 
						" INNER JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=hdsp.DVDL_FK WHERE "+  
						" SANPHAM_FK="+this.id + "  AND DVDL.PK_SEQ ="+dvdlid1+"";
				
				ResultSet rs=db.get(query);
				 if(rs.next()){
					 // retun false;
					 this.msg="Đã có dữ liệu phát sinh của quy đổi ra :"+donvi+". Bạn không thể bỏ quy cách này";
					 return false;
				 }
				 rs.close();
				  
				
			}
			rsdvdlcu.close();
			}
			
			for (int i = 0; i < 5; i++)
			{
				if ((sl1[i].length() > 0) && (sl2[i].length() > 0) && (dvdl1[i].length() > 0) && (dvdl2[i].length() > 0))
				{
 
					boolean flag= KiemTraThayDoiDVDL(this.dvdl1[i],  sl1[i], dvdl2[i], sl2[i]);
					
					if(!flag) {
						return false;
					}
		 
					 
				}
			}
			
			 
			
			return true;
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
		 
	}

	public boolean checkThayDoiLoaiSanpham() {
		
		try{
			String sql="select pk_seq from erp_sanpham where pk_seq="+this.id;
			
			ResultSet rs=db.get(sql);
			if(rs.next()){
				sql=" select top 1 SANPHAM_FK from ERP_TONKHOTHANG where soluong >0 and SANPHAM_FK= "+this.id +
					" union "+
					" select top 1 SANPHAM_FK from ERP_MUAHANG_SP where SANPHAM_FK= "+this.id + 
					" union "+
					" select top 1 SANPHAM_FK from ERP_NHANHANG_SANPHAM where SANPHAM_FK= "+this.id +
					" union " +
					"  select top 1 SANPHAM_FK from ERP_CHUYENKHO_SANPHAM   where SANPHAM_FK= "+this.id +
					" union "+
					" select top 1 SANPHAM_FK from ERP_DONDATHANG_SP where SANPHAM_FK= "+this.id ;
			System.out.println("SQL : "+sql);
				rs=db.get(sql);
				if(rs.next()){
					return true;
				}
				
				rs.close();
				return false;
			}else{
				return false;
			}
			
		}catch(Exception er){
			
		}
		return true;
	}

	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	
	public void init()
	{
		try
		{
			String query =
			"select  isnull(NGAYHOANTHANH,'') AS NGAYHOANTHANH ,ISNULL(SONGAYHANCANHBAO,0) AS SONGAYHANCANHBAO , isnull(IS_VOTHOIHAN,'') as IS_VOTHOIHAN,   ISNULL(batbuockiemdinh,'') as batbuockiemdinh,  a.pk_seq as id, a.ma as masp, a.ten as tensp,a.ten1 as tennoibo, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId, c.pk_seq as clId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, d.pk_seq as dvdlId, isnull(a.chiphinhancong, '0') as chiphinhancong, isnull(a.chiphivanchuyen, '0') as chiphivanchuyen,isnull(a.tenthuongmai,'') as tenthuongmai, " +
			"isnull(a.kiemtradinhtinh, 0) as kiemtradinhtinh, isnull(a.kiemtradinhluong, 0) as kiemtradinhluong, " +
			"a.trangthai, isnull(f.giaban,0) as giablc, a.type, isnull(a.trongluong,0) as TrongLuong, Isnull(a.thetich,0) as TheTich, a.loaisanpham_fk " +
			", h.ma as loaispma, isnull(a.HanSuDung ,0) as hansudung, a.LoaiGiaTon, " +
			" isnull(a.luongdattoithieu,'0') as luongdattoithieu, isnull(a.thoigianchogiaohang,'0') as thoigianchogiaohang, " +
			" isnull(a.tonkhoantoan, '0') as tonkhoantoan, isnull(a.kiemtradinhtinh, '0') as kiemtradinhtinh, " +
			" isnull(a.kiemtradinhluong, '0') as kiemtradinhluong, a.packsize_fk as packsize_fk, " +
			" isnull ( ( select top(1) GIATON from erp_tonkhothang where SANPHAM_FK = '" + this.id + "' order by NAM desc, THANG desc ), 0 ) as giamua," +
			" isnull(a.HANGBO,0) AS HANGBO, isnull(a.FILEPATH,'') as pathHinhanhSp, isnull(a.FILENAME,'') as nameHinhanhSp ," +
			" isnull(BANTHANHPHAM_FK,0) as BANTHANHPHAM_FK ,isnull(PHEPHAM_FK,0) as PHEPHAM_FK, THUEXUAT,isnull(a.tckt,'') as tckt, isnull(a.dangbaoche,0) as dangbaoche, isnull(a.loaihanghoa,'') as loaihanghoa, isnull(KIEMTRAOE, 0) as KIEMTRAOE ,ISNULL (a.ischietkhau,'0')  as ischietkhau,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE \n" +
			", a.THIETBICAN, a.THIETBICANKHAC, a.YEUCAUNL, a.MOTACAMQUAN, a.YEUCAUDONGGOI, a.DACTINHKYTHUAT, a.CONGTHUCHOAHOC, a.NHOMLUUTRU, a.MUCDONGUYHIEM, a.KHUVUCBAOQUAN, a.KHONGQUANLYSL"
			+ " from erp_sanpham a left join donvikinhdoanh b on a.dvkd_fk = b.pk_seq " +
			" left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq " +
			" left join nhanhang e on a.nhanhang_fk = e.pk_seq " +		
			" left join ERP_BGBAN_SANPHAM f on a.pk_seq = f.sanpham_fk left join erp_banggiaban g on f.bgban_fk = g.pk_seq " +
			" left join erp_loaisanpham h on h.pk_seq = a.loaisanpham_fk "+
			" where a.pk_seq = '" + this.id + "'";
			
			System.out.println(":::: INIT: " + query);
			
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				while (rs.next())
				{
					this.id = rs.getString("id");
					this.Batbuockiemdinh=rs.getString("batbuockiemdinh");
					this.masp = rs.getString("masp"); if(this.masp == null) this.masp = "";
					this.tennoibo = rs.getString ("tennoibo"); if(this.tennoibo == null) this.tennoibo = "";
					this.ten = rs.getString("tensp"); if(this.ten == null) this.ten = "";
					this.dvkdId = rs.getString("dvkdId"); if(this.dvkdId == null) this.dvkdId = "";
					this.dvdlTen = rs.getString("donvi"); if(this.dvdlTen == null) this.dvdlTen = "";
					this.nhId = rs.getString("nhId"); if(this.nhId == null) this.nhId = "";
					this.clId = rs.getString("clId") == null ? "" : rs.getString("clId");
					this.giablc = rs.getString("giablc") == null ? "" : rs.getString("giablc");
					this.trangthai = rs.getString("trangthai"); if(this.trangthai == null) this.trangthai = "";
					this.type = rs.getString("type"); if(this.type == null) this.type = "";
					this.dvdlId = rs.getString("dvdlId"); if(this.dvdlId == null) this.dvdlId = "";
					this.kl =formatter.format(rs.getDouble("trongluong"));
					this.tt =formatter.format(rs.getDouble("thetich"));
					this.loaispId = rs.getString("loaisanpham_fk"); if(this.loaispId == null) this.loaispId = "";
					this.loaispma = rs.getString("loaispma"); if(this.loaispma == null) this.loaispma = "";
					this.LoaiGiaTon = rs.getString("LoaiGiaTon") == null ? "0" : rs.getString("LoaiGiaTon");
					this.HanSuDung = rs.getString("HanSuDung") == null ? "0" : rs.getString("HanSuDung");
					/*this.luongdattoithieu = rs.getString("luongdattoithieu");
					this.thoigianchogiaohang = rs.getString("thoigianchogiaohang");*/
					this.tonkhoantoan = rs.getString("tonkhoantoan");
					this.packsizeId = rs.getString("packsize_fk") == null ? "" : rs.getString("packsize_fk");
					this.cpnc = rs.getString("chiphinhancong");
					this.cpvc = rs.getString("chiphivanchuyen");
					this.kiemtradinhtinh = rs.getString("kiemtradinhtinh");
					this.kiemtradinhluong = rs.getString("kiemtradinhluong");
					this.GiaMua = rs.getString("giamua") == null ? "0" : formatter.format( rs.getDouble("giamua") );
					this.isHangbo = rs.getString("HANGBO");
					this.pathHinhanhSp = rs.getString("pathHinhanhSp");
					this.nameHinhanhSp = rs.getString("nameHinhanhSp");
					this.BTPId=rs.getString("BANTHANHPHAM_FK");
					this.PPId=rs.getString("PHEPHAM_FK");
					this.Check_Vothoihan=rs.getString("IS_VOTHOIHAN");
					this.Songayhancanhbao=rs.getString("SONGAYHANCANHBAO");
					this.thueSuat = rs.getString("THUEXUAT");
					this.tckt =rs.getString("tckt");
					this.dangBaoChe = rs.getString("dangbaoche");
					this.loaiHangHoa = rs.getString("loaihanghoa");
					this.kiemtraoe =  rs.getString("KIEMTRAOE");
					this.ischietkhau =  rs.getString("ischietkhau");
					this.Ngayhoanthanh=rs.getString("NGAYHOANTHANH");
					this.tenthuongmai=rs.getString("tenthuongmai");
					this.is_khongthue=rs.getString("IS_KHONGTHUE");
					
					this.thietBiCan = rs.getString("THIETBICAN");
					this.thietBiCanKhac = rs.getString("THIETBICANKHAC");
					this.ycnlsx = rs.getString("YEUCAUNL");
					this.motaSp = rs.getString("MOTACAMQUAN");
					this.yeucaudonggoi = rs.getString("YEUCAUDONGGOI");
					this.dactinhkythuat = rs.getString("DACTINHKYTHUAT");
					this.congthuchoahoc = rs.getString("CONGTHUCHOAHOC");
					this.nhomluutru = rs.getString("NHOMLUUTRU");
					this.mucdonguyhiem = rs.getString("MUCDONGUYHIEM");
					this.khuvucbaoquan = rs.getString("KHUVUCBAOQUAN");
					this.khongqlsl = rs.getString("KHONGQUANLYSL");
				}
				
				rs.close();
				
			}
			
			query = " select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '" + this.id + "'";	
			System.out.println("So luong thuc te: "+query);
			
			rs = this.db.get(query);
			
			int i = 0;
		 
				while (rs.next())
				{
					
					System.out.println("So luong thuc te: "+rs.getString("sl1"));
					this.sl1[i] = rs.getString("sl1"); 
					this.dvdl1[i] =  rs.getString("dvdl1");
					this.sl2[i] = rs.getString("sl2");
					this.dvdl2[i] = rs.getString("dvdl2");
					
					 
					i++;
				}
	 
			if(i < 5)
			{
				for(int j = i; j < 5; j++)
				{
					this.sl1[j] = "";
					this.dvdl1[j] = this.dvdlId;
					this.sl2[j] = "";
					this.dvdl2[j] = "";
				}
			}
			rs.close();	
			
			// lay kho
			query = "select KHOTT_FK as kho from ERP_KHOTT_SANPHAM where sanpham_fk='" + this.id + "'";	
			System.out.println("---1313. lay kho: " + query);
			rs = this.db.get(query);
			i=0;
			try{
				while (rs.next())
				{
					this.khovung[i] = rs.getString("kho");
					i++;
				}
			}catch (Exception e) {
				e.printStackTrace();
			}
			
			// lay nha cung cap, hinhcongbo, hancongbo, ...
			query = "select sanpham_fk,nhacungcap_fk as nhacungcap,isnull(thoihangiaohang,0) as thoihangiaohang, "+
						" isnull(luongdattoithieu,0) as luongdattoithieu, isnull(hancongbo,'') as hancongbo, "+
						" isnull(hinhcongbo,'') as hinhcongbo, isnull(filename,'') as filenamecb " +
					" from erp_sanpham_nhacungcap where sanpham_fk='" + this.id + "'";	
			rs = this.db.get(query);
			
			if (this.loaispma.contains("TP")) //thanh pham
			{
				while (rs.next())
				{
					this.hancongboTP = rs.getString("hancongbo");
					this.hinhcongboTP = rs.getString("hinhcongbo");
					this.filenamecbTP = rs.getString("filenamecb");
				}
				rs.close();
				
				query = "select btp_fk from erp_sanpham_btp where sp_fk='" + this.id + "'";	
				rs = this.db.get(query);
				i=0;
				while (rs.next())
				{
					this.bantp[i] = rs.getString("btp_fk");
					i++;
				}
				
			}else  if (this.loaispma.contains("VT")||this.loaispma.contains("NL")||this.loaispma.contains("VLP")||this.loaispma.contains("BB")) //nguyen lieu, vat tu, bao bi
			{
				this.ThongtinNCCList.clear();
				i = 0;
				while (rs.next())
				{
					
					IThongtinNCC ttncc=new ThongtinNCC();
					
					
				/*	this.nhacungcap[i] = rs.getString("nhacungcap");
					this.thoihangiaohang[i] = rs.getString("thoihangiaohang");
					this.luongdattoithieu[i] = rs.getString("luongdattoithieu");
					this.hancongbo[i] = rs.getString("hancongbo");
					this.hinhcongbo[i] = rs.getString("hinhcongbo");
					this.filenamecb[i] = rs.getString("filenamecb");*/
					ttncc.setnhacungcap(rs.getString("nhacungcap"));
					ttncc.setthoihangiaohang(rs.getString("thoihangiaohang"));
					ttncc.setluongdattoithieu(rs.getString("luongdattoithieu"));
					ttncc.sethancongbo(rs.getString("hancongbo"));
					ttncc.sethinhcongbo(rs.getString("hinhcongbo"));
					ttncc.setfilenamecb(rs.getString("filenamecb"));
					
					List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
					//Lay tieu chi dinh luong nha cung cap
					String sql = " select distinct tieuchi, pheptoan, giatrichuan  from  SanPham_TieuChiKiemDinh " +
									" where loai = '0' and sanpham_fk = '" + this.id + "' AND NCC_FK = "+rs.getString("nhacungcap");
					//System.out.println("[Thongtinsanpham.createTieuchikiemdinh] sql = " + sql);
					 ResultSet rstc = db.get(sql);

					if (rstc != null) {
					 
							while (rstc.next()) {
								ITieuchikiemdinh tckd = new Tieuchikiemdinh();

								tckd.setTieuchi(rstc.getString("tieuchi"));
								tckd.setToantu(rstc.getString("pheptoan"));
								tckd.setGiatrichuan(rstc.getString("giatrichuan"));
								//tckd.setDungsai(rs.getString("dungsai"));

								tckdList.add(tckd);
							}
							rstc.close();
						 

						 
						 
					}
					ttncc.setTieuchikiemdinhDinhluongList(tckdList);

					//Lay tieu chi dinh tinh nha cung cap
					sql = 	" select distinct tieuchi, pheptoan, giatrichuan from SanPham_TieuChiKiemDinh  " +
							" where loai = '1' and sanpham_fk = '" + this.id + "' AND NCC_FK= "+rs.getString("nhacungcap");
					System.out.println("[Thongtinsanpham.createTieuchikiemdinh] sql = " + sql);
					tckdList = new ArrayList<ITieuchikiemdinh>();
					rstc = db.get(sql);

					if (rstc != null) {
						 
							while (rstc.next()) {
								ITieuchikiemdinh tckd = new Tieuchikiemdinh();

								tckd.setTieuchi(rstc.getString("tieuchi"));

								tckdList.add(tckd);
							}
							rstc.close();
						 
 
					}
					ttncc.setTieuchikiemdinhDinhtinhList(tckdList);
					
					//Lay hoa chat kiem dinh nha cung cap
					ttncc.loadHoaChatKiemDinhListCuaNCC(this);
					ttncc.loadMayMocKiemDinhListCuaNCC(this);
					//Lay may moc kiem dinh nha cung cap
					this.ThongtinNCCList.add(ttncc);
					
					i++;
				}
			}
			rs.close();
			
			CreateRS();
			createNspIds();
			
			createSpIds();
			
			createTieuchikiemdinh();
			createHoaChatMayMocKiemDinhList();
		}
		catch (Exception  er)
		{
			er.printStackTrace();
		}
	}
	
	
	private void createSpIds()
	{
		if(this.dvkdId.trim().length() > 0 ) {
			String query = "select pk_seq, ma as spMa, ten as spTen,ten1 as spTennoibo, bdsp.soluong " +
					   	   "from erp_sanpham sp inner join bundle_sanpham bdsp on sp.pk_seq = bdsp.sanpham_fk  " +
					   	   "where bdsp.bundle_fk = '" + this.id.trim() + "' and dvkd_fk = '" + this.dvkdId + "' ";
			System.out.println("[Thongtinsanpham.createSpIds] query = " + query);
			ResultSet rsSp = db.get(query);
			if(rsSp != null)
			{
				Hashtable<String, Integer> bunle_sp = new Hashtable<String, Integer>();
				try 
				{
					String spId = "";
					while(rsSp.next())
					{
						spId += rsSp.getString("pk_seq") + ",";
						bunle_sp.put(rsSp.getString("pk_seq"), rsSp.getInt("soluong"));
					}
					rsSp.close();
					
					//this.bundle_soluong = bunle_sp;
					
					if(spId.trim().length() > 0)
						this.spIds = spId.substring(0, spId.length() - 1);
				} 
				catch (Exception e) {}
				
			}
		}
	}

	private void createNspIds() 
	{
		String query = "select nsp_fk from nhomsanpham_sanpham where sp_fk = '" + this.id + "'";
		System.out.println("[Thongtinsanpham.createNspIds] query = " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			try
			{
				String str = "";
				while (rs.next())
				{
					str = str + rs.getString("nsp_fk") + ",";
				}
				if (str.length() > 0)
				{
					this.nspIds = str.substring(0, str.length() - 1);
				}
			}
			catch (Exception e){ }
		}
	}


	public void DBClose()
	{
		try
		{
			if (this.nh != null) this.nh.close();
			if (this.cl != null) this.cl.close();
			if (this.dvdl != null) this.dvdl.close();
			if (this.dvkd != null) this.dvkd.close();
			if (this.nsp != null) this.nsp.close();
			if (this.CongTyRs != null) this.CongTyRs.close();
			if(this.rsBTP!=null){
				this.rsBTP.close();
				
			}
			if(this.rsPhepham!=null){
				this.rsPhepham.close();
			}
			if(this.packsizeRs !=null){
				this.packsizeRs.close();
			}
			this.db.shutDown();
		}
		catch (java.sql.SQLException e)
		{
		}
	}
	
	
	public void setType(String type)
	{
		this.type = type;
	}
	
	public String getType()
	{
		return this.type;
	}
	
	public ResultSet getSanphamRs()
	{
		return this.spList;
	}
	
	public void setSanphamRs(ResultSet spRs)
	{
		this.spList = spRs;
	}
	
	public void init2() //display
	{
		try
		{
			String query =
			" select a.pk_seq as id, a.ma as masp, a.ten1 as tennoibo,a.ten as tensp, h.ma as loaispma, b.donvikinhdoanh as dvkd, b.pk_seq as dvkdId, c.pk_seq as clId, c.ten as chungloai, e.pk_seq as nhId, d.donvi, e.ten as nhanhang, d.pk_seq as dvdlId, a.trangthai,isnull(f.giaban,0) as giablc, a.type,  " +
			" isnull(a.luongdattoithieu,'0') as luongdattoithieu, isnull(a.thoigianchogiaohang,'0') as thoigianchogiaohang, isnull(a.tonkhoantoan, '0') as tonkhoantoan, a.packsize_fk as packsize_fk,  "
			+ " isnull(a.hangbo,0) as hangbo, isnull(a.filepath,'') as pathHinhanhSp, isnull(a.filename,'') as nameHinhanhSp, thuexuat,isnull(a.tckt,'') as tckt, " +
			" isnull(a.dangbaoche,'') as dangbaoche, isnull(a.loaihanghoa,'') as loaihanghoa, isnull(a.kiemtraoe,0) as KIEMTRAOE,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE \n "
			+ ", a.THIETBICAN, a.THIETBICANKHAC, a.YEUCAUNL, a.MOTACAMQUAN, a.YEUCAUDONGGOI, a.DACTINHKYTHUAT, a.CONGTHUCHOAHOC, a.NHOMLUUTRU, a.MUCDONGUYHIEM, a.KHUVUCBAOQUAN, a.KHONGQUANLYSL"
			+ " from erp_sanpham a inner join donvikinhdoanh b on a.dvkd_fk = b.pk_seq left join chungloai c on a.chungloai_fk = c.pk_seq left join donvidoluong d on a.dvdl_fk = d.pk_seq left join nhanhang e on a.nhanhang_fk = e.pk_seq "
			+ " inner join ERP_BGBAN_SANPHAM f on a.pk_seq = f.sanpham_fk inner join erp_banggiaban g on f.bgban_fk = g.pk_seq "
			+ " left join erp_loaisanpham h on h.pk_seq = a.loaisanpham_fk ";
			query = query + " where a.pk_seq = '" + this.id + "'";
			System.out.println("Init2---" + query);
			ResultSet rs = this.db.get(query);
			rs.next();
			this.id = rs.getString("id");
			this.masp = rs.getString("masp");
			this.tennoibo = rs.getString("tennoibo");
			this.ten = rs.getString("tensp");
			this.isHangbo = rs.getString("hangbo");
			this.pathHinhanhSp = rs.getString("pathHinhanhSp");
			this.nameHinhanhSp= rs.getString("nameHinhanhSp");
			/*this.luongdattoithieu = rs.getString("luongdattoithieu");
			this.thoigianchogiaohang = rs.getString("thoigianchogiaohang");*/
			this.tonkhoantoan = rs.getString("tonkhoantoan");
			this.packsizeId = rs.getString("packsize_fk") == null ? "" : rs.getString("packsize_fk");
			this.thueSuat = rs.getString("thuexuat");
			this.is_khongthue=rs.getString("IS_KHONGTHUE");
			this.tckt =rs.getString("tckt");
			this.dangBaoChe = rs.getString("dangbaoche");
			this.loaiHangHoa = rs.getString("loaihanghoa");
			this.kiemtraoe =  rs.getString("KIEMTRAOE");
			
			this.thietBiCan = rs.getString("THIETBICAN");
			this.thietBiCanKhac = rs.getString("THIETBICANKHAC");
			this.ycnlsx = rs.getString("YEUCAUNL");
			this.motaSp = rs.getString("MOTACAMQUAN");
			this.yeucaudonggoi = rs.getString("YEUCAUDONGGOI");
			this.dactinhkythuat = rs.getString("DACTINHKYTHUAT");
			this.congthuchoahoc = rs.getString("CONGTHUCHOAHOC");
			this.nhomluutru = rs.getString("NHOMLUUTRU");
			this.mucdonguyhiem = rs.getString("MUCDONGUYHIEM");
			this.khuvucbaoquan = rs.getString("KHUVUCBAOQUAN");
			this.khongqlsl = rs.getString("KHONGQUANLYSL");
			
			if (rs.getString("dvkdId") == null)
			{
				this.dvkdId = "";
			}
			else
			{
				this.dvkdId = rs.getString("dvkdId");
			}
			if (rs.getString("nhId") == null)
			{
				this.nhId = "";
			}
			else
			{
				this.nhId = rs.getString("nhId");
			}
			if (rs.getString("clId") == null)
			{
				this.clId = "";
			}
			else
			{
				this.clId = rs.getString("clId");
			}
			this.giablc = rs.getString("giablc");
			this.trangthai = rs.getString("trangthai");
			this.loaispma = rs.getString("loaispma");
			this.type = rs.getString("type");
			if (rs.getString("dvdlId") == null)
			{
				this.dvdlId = "";
			}
			else
			{
				this.dvdlId = rs.getString("dvdlId");
			}
			rs.close();
			query =
			"select soluong1 as sl1, dvdl1_fk as dvdl1, soluong2 as sl2, dvdl2_fk as dvdl2 from quycach where sanpham_fk = '" +
			id + "'";
			// this.msg =query;
			rs = this.db.get(query);
			int i = 0;
			while (rs.next())
			{
				this.sl1[i] = rs.getString("sl1");
				this.dvdl1[i] = rs.getString("dvdl1");
				this.sl2[i] = rs.getString("sl2");
				this.dvdl2[i] = rs.getString("dvdl2");
				i++;
			}
			rs.close();
			this.dvdl = createDvdlRS();
			this.dvkd = createDvkdRS();
			this.nh = createNhRS();
			this.cl = createClRS();
			
			createTieuchikiemdinh();

			/*createNspIds();
			createSpIds();*/
			
			// lay kho
			query = " select KHOTT_FK as kho from ERP_KHOTT_SANPHAM where sanpham_fk='" + this.id + "'";	
			rs = this.db.get(query);
			i=0;
			while (rs.next())
			{
				this.khovung[i] = rs.getString("kho");
				i++;
			}
			createHoaChatMayMocKiemDinhList();
		}
		catch (java.sql.SQLException e)
		{
		}
	}

	public void setKL(String kl)
	{
		this.kl = kl;
	}
	
	public void setDVKL(String dvkl)
	{
		this.dvkl = dvkl;
	}
	
	public void setDVTT(String dvtt)
	{
		this.dvtt = dvtt;
	}
	
	public void setTT(String tt)
	{
		this.tt = tt;
	}
	
	public String getKL()
	{
		return this.kl;
	}
	
	public String getDVKL()
	{
		return this.dvkl;
	}
	
	public String getDVTT()
	{
		return this.dvtt;
	}
	
	public String getTT()
	{
		return this.tt;
	}
	
	public ResultSet getLoaiSpRs()
	{
		return this.loaispRs;
	}
	
	public void setLoaiSpRs(ResultSet spRs)
	{
		this.loaispRs = spRs;
	}
	
	public String getLoaiSpId()
	{
		return this.loaispId;
	}
	
	public void setLoaiSpId(String loaisp)
	{
		this.loaispId = loaisp;
	}
	
	public String getLoaiSpMa()
	{
		return this.loaispma;
	}
	
	public void setLoaiSpMa(String loaispma)
	{
		this.loaispma = loaispma;
	}
	
	public String getLoaiSpMa(String loaispid)
	{
		ResultSet rs;
		System.out.println(" loai sp ma : "+ "select ma from erp_loaisanpham where pk_seq ='"+loaispid+"'");
		if(loaispid != null && loaispid.length() > 0)
			rs = this.db.get("select ma from erp_loaisanpham where pk_seq ='"+loaispid+"'");
		else
			return "";
		if (rs!=null)
			try {
				while (rs.next())
				{
					this.loaispma = rs.getString("ma");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		return this.loaispma;
	}
	
	public String getCongTy()
	{
		return this.CongTy;
	}
	
	public void setCongTy(String congty)
	{
		this.CongTy = congty;
	}
	
	public String getLoaiGiaTon()
	{
		return this.LoaiGiaTon;
	}
	
	public void setLoaiGiaTon(String loaigiaton)
	{
		this.LoaiGiaTon = loaigiaton;
	}
	
	public String getHanSuDung()
	{
		return this.HanSuDung;
	}
	
	public void setHanSuDung(String hansudung)
	{
		this.HanSuDung = hansudung;
	}
	
	public void setGiaMua(String giamua)
	{
		this.GiaMua = giamua;
	}
	
	public String getGiaMua()
	{
		return this.GiaMua;
	}
	
	public ResultSet getCongTyRs()
	{
		return this.CongTyRs;
	}
	
	public void setCongTyRs(ResultSet congty)
	{
		this.CongTyRs = congty;
	}

	public String getNspIds() 
	{
		return this.nspIds;
	}

	public void setNspIds(String nspIds) 
	{
		this.nspIds = nspIds;
	}

	public String getSpIds() 
	{
		return this.spIds;
	}

	public void setSpIds(String spIds) 
	{
		this.spIds = spIds;
	}

	public String getDvdl_qcIds()
	{
		return this.dvdl_qcIds;
	}

	public void setDvdl_dcIds(String dvdlIds) 
	{
		this.dvdl_qcIds = dvdlIds;
	}

	/*
	public String getLuongDatToiThieu() {
		return this.luongdattoithieu;
	}

	
	public void setLuongDatToiThieu(String value) {
		this.luongdattoithieu = value;
	}

	
	public String getThoiGianChoGiaoHang() {
		return this.thoigianchogiaohang;
	}

	
	public void setThoiGianChoGiaoHang(String value) {
		this.thoigianchogiaohang = value;
	}*/

	
	public String getTonKhoAnToan() {
		return this.tonkhoantoan;
	}

	
	public void setTonKhoAnToan(String value) {
		this.tonkhoantoan = value;
	}
	

	
	public ResultSet getPacksizeRs()
	{
		return this.packsizeRs;
	}
	
	public void setPacksizeRs(ResultSet packsizeRs)
	{
		this.packsizeRs = packsizeRs;
	}
	
	public String getPacksizeId()
	{
		return this.packsizeId;
	}
	
	public void setPacksizeId(String packsizeId)
	{
		this.packsizeId = packsizeId;
	}

	
	public void setCPNC(String cpnc) {
		this.cpnc = cpnc;
	}

	
	public String getCPNC() {
		return cpnc;
	}

	
	public void setCPVC(String cpvc) {
		this.cpvc = cpvc;
	}

	
	public String getCPVC() {
		return cpvc;
	}


	
	public String getKiemTraDinhTinh() {
		return this.kiemtradinhtinh;
	}

	
	public void setKiemTraDinhTinh(String value) {
		this.kiemtradinhtinh = value;
	}

	
	public String getKiemTraDinhLuong() {
		return this.kiemtradinhluong;
	}

	
	public void setKiemTraDinhLuong(String value) {
		this.kiemtradinhluong = value;
	}

	public void setTieuchikiemdinhDinhluongList(List<ITieuchikiemdinh> list) 
	{
		this.tckdDinhluongList = list;
	}

	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhluongList() 
	{
		return this.tckdDinhluongList;
	}

	public void setTieuchikiemdinhDinhtinhList(List<ITieuchikiemdinh> list) 
	{
		this.tckdDinhtinhList = list;
	}

	public List<ITieuchikiemdinh> getTieuchikiemdinhDinhtinhList() 
	{
		return this.tckdDinhtinhList;
	}


	public void createTieuchikiemdinh() {
		List<ITieuchikiemdinh> tckdList = new ArrayList<ITieuchikiemdinh>();
		
		String sql = " select distinct tieuchi, pheptoan, giatrichuan from SanPham_TieuChiKiemDinh where ncc_fk is null and  loai = '0' and sanpham_fk = '" + this.id + "' ";
		System.out.println("[Thongtinsanpham.createTieuchikiemdinh] sql = " + sql);
		ResultSet rs = db.get(sql);

		if (rs != null) {
			try {
				while (rs.next()) {
					ITieuchikiemdinh tckd = new Tieuchikiemdinh();

					tckd.setTieuchi(rs.getString("tieuchi"));
					tckd.setToantu(rs.getString("pheptoan"));
					tckd.setGiatrichuan(rs.getString("giatrichuan"));
					//tckd.setDungsai(rs.getString("dungsai"));

					tckdList.add(tckd);
				}
				rs.close();
			} catch (Exception e) {
				System.out.println("[Thongtinsanpham.createTieuchikiemdinh] Exception Message = " + e.toString());
				e.printStackTrace();
			}

			this.tckdDinhluongList = tckdList;
		}

		sql = " select distinct tieuchi, pheptoan, giatrichuan from SanPham_TieuChiKiemDinh where  ncc_fk is null and  loai = '1' and sanpham_fk = '" + this.id + "' ";
		System.out.println("[Thongtinsanpham.createTieuchikiemdinh] sql = " + sql);
		tckdList = new ArrayList<ITieuchikiemdinh>();
		rs = db.get(sql);

		if (rs != null) {
			try {
				while (rs.next()) {
					ITieuchikiemdinh tckd = new Tieuchikiemdinh();

					tckd.setTieuchi(rs.getString("tieuchi"));

					tckdList.add(tckd);
				}
				rs.close();
			} catch (Exception e) {
				System.out.println("[Thongtinsanpham.createTieuchikiemdinh] Exception Message = " + e.toString());
				e.printStackTrace();
			}

			this.tckdDinhtinhList = tckdList;
		}

	}
	
	public void createHoaChatMayMocKiemDinhList() {
		List<IHoaChat_SanPham> hoaChatList = new ArrayList<IHoaChat_SanPham>();
		String query = "";
		try{
			query = "SELECT SP.PK_SEQ, SP.TEN, DV.DIENGIAI, SP.MA, HC.SOCHATDUOCKIEMDINH, HC.SOCHATKIEMDINH\r\n" + 
					"FROM HOACHAT_SANPHAM HC\r\n" + 
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HC.HOACHAT\r\n" + 
					"INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = SP.DVDL_FK\r\n" + 
					"WHERE NCC_FK IS NULL AND SANPHAM_FK = "+this.id;
			ResultSet resultSetHoaChat = db.get(query);
			if (resultSetHoaChat != null){
				while(resultSetHoaChat.next()){
					IHoaChat_SanPham hoaChat_SanPham = new HoaChat_SanPham(this.id, this.dvdlTen, 
							resultSetHoaChat.getString("PK_SEQ"), resultSetHoaChat.getString("MA"), resultSetHoaChat.getString("TEN"), resultSetHoaChat.getString("DIENGIAI"),
							resultSetHoaChat.getInt("SOCHATDUOCKIEMDINH"), resultSetHoaChat.getInt("SOCHATKIEMDINH"));
					hoaChatList.add(hoaChat_SanPham);
				}
			}
			this.setHoaChatKiemDinhList(hoaChatList);
			
			
			query = "SELECT PK_SEQ, MA, TEN \r\n" + 
					"FROM MAYMOC_SANPHAM MM\r\n" + 
					"INNER JOIN ERP_TAISANCODINH TSCD ON MM.TAISANCODINH_FK = TSCD.pk_seq\r\n" + 
					"WHERE NCC_FK IS NULL AND SANPHAM_FK = "+this.id;
			resultSetHoaChat = db.get(query);
			while(resultSetHoaChat.next()){
				IErp_TaiSanCoDinh mayMoc = new Erp_TaiSanCoDinh();
				mayMoc.setId(resultSetHoaChat.getString("PK_SEQ"));
				mayMoc.setMa(resultSetHoaChat.getString("MA"));
				mayMoc.setDiengiai(resultSetHoaChat.getString("TEN"));
				IMayMoc_SanPham mayMocKiemDinh = new MayMoc_SanPham(this.id, mayMoc);
				this.mayMocKiemDinhList.add(mayMocKiemDinh);
			}
			
			resultSetHoaChat.close();
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	
	public List<IBundle> getSanphamList() {
		
		return null;
	}

	
	public void setSanphamList(List<IBundle> spList) {
		
		
	}

	
	public void setChogiaohangnguyenlieu(String chogiaonguyenlieu) {
		
		
	}

	
	public String getChogiaohangnguyenlieu() {
		
		return null;
	}

	
	public void setLuongdattoithieu(String luongdattoithieu) {
		
		
	}

	
	public void setKiemtradinhluong(String kiemtradinhluong) {
		
		
	}

	
	public String getKiemtradinhluong() {
		
		return null;
	}

	
	public void setKiemtradinhtinh(String kiemdinhchatluong) {
		
		
	}

	
	public String getKiemtradinhtinh() {
		
		return null;
	}

	
	public void setPathHinhanhSp(String filepath) {
		this.pathHinhanhSp = filepath;
	}

	
	public String getPathHinhanhSp() {
		return pathHinhanhSp;
	}

	
	public void setNameHinhanhSp(String filename) {
		this.nameHinhanhSp = filename;
	}

	
	public String getNameHinhanhSp() {
		return this.nameHinhanhSp;
	}

	
	public void setHangbo(String value) {
		this.isHangbo = value;
	}

	
	public String getHangbo() {
		return this.isHangbo;
	}

	
	public String getBTPId() {
		
		return this.BTPId;
	}

	
	public void setBTPId(String BTPId) {
		
		this.BTPId=BTPId;
	}

	
	public String getPhephamId() {
		
		return this.PPId;
	}

	
	public void setPhephamId(String PhephamId) {
		
		this.PPId=PhephamId;
	}

	
	public void setBTPRs(ResultSet BTPRs) {
		
		this.rsBTP=BTPRs;
	}

	
	public ResultSet getBTPRs() {
		
		return rsBTP;
	}

	
	public void setPhePhamRs(ResultSet PhePhamRs) {
		
		this.rsPhepham=PhePhamRs;
	}

	
	public ResultSet getPhePhamRs() {
		
		return this.rsPhepham;
	}

	
	public void SetThongtinNCClist(List<IThongtinNCC> list) {
		
		this.ThongtinNCCList=list;
	}

	
	public List<IThongtinNCC> GetThongtinNCClist() {
		
		return this.ThongtinNCCList;
	}

	
	public String getgetBatbuockiemdinh() {
		
		return this.Batbuockiemdinh;
	}

	
	public void setBatbuockiemdinh(String Batbuockiemdinh) {
		
		this.Batbuockiemdinh=Batbuockiemdinh;
	}

	
	public String getcheck_VoThoiHan() {
		
		return this.Check_Vothoihan;
	}

	
	public void setcheck_VoThoiHan(String check_vothoihan) {
		
		this.Check_Vothoihan=check_vothoihan;
		
	}

	
	public String getSongayhancanhbao() {
		
		return this.Songayhancanhbao;
	}

	
	public void setSongayhancanhbao(String songayhancanhbao) {
		
		this.Songayhancanhbao=songayhancanhbao;
	}
	
	public String getThueSuat() {
		return thueSuat;
	}
	
	public void setThueSuat(String thueSuat) {
		this.thueSuat = thueSuat;
	}

	
	public List<IHoaChat_SanPham> getHoaChatKiemDinhList() {
		return hoaChatKiemDinhList;
	}
	
	public void setHoaChatKiemDinhList(List<IHoaChat_SanPham> hoaChatKiemDinhList) {
		this.hoaChatKiemDinhList = hoaChatKiemDinhList;
	}
	
	public List<IMayMoc_SanPham> getMayMocKiemDinhList() {
		return mayMocKiemDinhList;
	}
	
	public void setMayMocKiemDinhList(List<IMayMoc_SanPham> mayMocKiemDinhList) {
		this.mayMocKiemDinhList = mayMocKiemDinhList;
	}
	
	public String getNgayhoanthanh() {
		
		return Ngayhoanthanh;
	}

	public void setNgayhoanthanh(String Ngayhoanthanh) {
		
		this.Ngayhoanthanh=Ngayhoanthanh;
	}

	public String getIschietkhau() {
		return ischietkhau;
	}

	public void setIschietkhau(String ischietkhau) {
		this.ischietkhau = ischietkhau;
	}

	public String getThietBiCan() {
		return thietBiCan;
	}

	public void setThietBiCan(String thietBiCan) {
		this.thietBiCan = thietBiCan;
	}

	public String getThietBiCanKhac() {
		return thietBiCanKhac;
	}

	public void setThietBiCanKhac(String thietBiCanKhac) {
		this.thietBiCanKhac = thietBiCanKhac;
	}

	public ResultSet getTscdRs() {
		return tscdRs;
	}

	public void setTscdRs(ResultSet tscdRs) {
		this.tscdRs = tscdRs;
	}

	public String getYcnlsx() {
		return ycnlsx;
	}

	public void setYcnlsx(String ycnlsx) {
		this.ycnlsx = ycnlsx;
	}

	public String getMotaSp() {
		return motaSp;
	}

	public void setMotaSp(String motaSp) {
		this.motaSp = motaSp;
	}

	public String getYeucaudonggoi() {
		return yeucaudonggoi;
	}

	public void setYeucaudonggoi(String yeucaudonggoi) {
		this.yeucaudonggoi = yeucaudonggoi;
	}

	public String getDactinhkythuat() {
		return dactinhkythuat;
	}

	public void setDactinhkythuat(String dactinhkythuat) {
		this.dactinhkythuat = dactinhkythuat;
	}

	public String getCongthuchoahoc() {
		return congthuchoahoc;
	}

	public void setCongthuchoahoc(String congthuchoahoc) {
		this.congthuchoahoc = congthuchoahoc;
	}

	public String getNhomluutru() {
		return nhomluutru;
	}

	public void setNhomluutru(String nhomluutru) {
		this.nhomluutru = nhomluutru;
	}

	public String getMucdonguyhiem() {
		return mucdonguyhiem;
	}

	public void setMucdonguyhiem(String mucdonguyhiem) {
		this.mucdonguyhiem = mucdonguyhiem;
	}

	public String getKhuvucbaoquan() {
		return khuvucbaoquan;
	}

	public void setKhuvucbaoquan(String khuvucbaoquan) {
		this.khuvucbaoquan = khuvucbaoquan;
	}

	public String getKhongqlsl() {
		return khongqlsl;
	}

	public void setKhongqlsl(String khongqlsl) {
		this.khongqlsl = khongqlsl;
	}

	public String getThuocloaisp() {
		return thuocloaisp;
	}

	public void setThuocloaisp(String thuocloaisp) {
		this.thuocloaisp = thuocloaisp;
	}
	


	
}