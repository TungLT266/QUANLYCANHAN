package geso.traphaco.erp.beans.stockintransit.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;
import geso.traphaco.erp.beans.baocao.BaoCaoVayTienPoJo;
import geso.traphaco.erp.beans.stockintransit.IDataChart;
import geso.traphaco.erp.beans.stockintransit.IStockintransit;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Stockintransit implements IStockintransit {
	String userId;
	String nppTen;
	String kenhId;
	String dvkdId;
	String nhanhangId;
	String chungloaiId;
	String tungay;
	String denngay;
	String userTen;
	String khoId;
	String book;
	String unghang;
	ResultSet kho;
	String msg;
	ResultSet kenh;
	ResultSet dvkd;
	ResultSet nhanhang;
	ResultSet chungloai;
	String vungId;
	String khuvucId;
	ResultSet khuvuc;
	ResultSet vung;
	ResultSet npp;
	String view;
	String vat;
	String gsbhId;
	ResultSet gsbh;
	String sanphamId;
	ResultSet sanpham;
	String dvdlId;
	String[] FieldShow;
	String[] FieldHidden;
	String ngayton;
	String promotion;
	String discount;
	ResultSet dvdl;
	String lessday;
	String moreday;
	dbutils db;
	String year;
	String month;
	String ttcpid;
	String loaitaisanid;
	String nhomtaisanid;
	ResultSet loaitaisanrs;
	ResultSet nhomtaisanrs;
	ResultSet ttcprs;
	String khachhangIds;
	ResultSet rskhachhang;
	ResultSet rsNCC;
	String Pivot;
	String SanphamId;
	String nppdangnhap;
	String isNPP;

	String ddkd;
	ResultSet rsddkd;

	ResultSet nhanvienRs;
	ResultSet congtyRs;
	String[] ctyIds;

	String unit;
	String groupCus;
	//
	String programsId;
	ResultSet rsPrograms;

	String donviTinh;
	String tenxuathd;

	String fromMonth;
	String toMonth;
	String type;
	String ToYear = "";
	String FromYear = "";
	String Nhospid = "";
	ResultSet RsNhomSp;
	ResultSet RsKhErp;
	String KhId;

	ResultSet RsLoaiSp;
	String LoaiSpid;

	ResultSet RsErpKho;
	String ErpKhoId;

	ResultSet RsErpCongty;
	String ErpCongTyId;
	String congTy;

	ResultSet RsErpTiente;
	String ErpTienteId;

	String ErpTaiKhoanKTId;

	String ErpLoaiTk;
	ResultSet rsErpLoaiTK;
	ResultSet rsErpNCC;
	ResultSet rsErpDonViThucHien;
	ResultSet rsNhamay;

	String nhomKhid;

	ResultSet nhomKhRs;
	ResultSet sanphamrs;

	String taisanid;
	ResultSet taisanRs;

	String taisanids;
	String nhanvienId;

	String Nhamayid;
	String ErpKhachhangId;
	String ErpNCCId;
	String ErpDonViTHid;
	String[][] mang;
	String hangtrave;
	String loainccIds;
	String[] lnccIds;
	String[] nccIds;
	String[] lspIds;

	String loaiBC;

	// Nhà phân phối
	String nppId;
	ResultSet nppRs;
	
	//Chi nhánh
	ResultSet chiNhanhRs;

	// địa bàn
	String diabanId;
	ResultSet diabanRs;

	List<IDataChart> chartRs;

	// Bao cao vay tien
	List<BaoCaoVayTienPoJo> listBaoCaoVayTien = new ArrayList<BaoCaoVayTienPoJo>();
	String chiNhanh;
	String chiNhanhTen;

	public Stockintransit() {
		this.userId = "";
		this.ErpNCCId = "";
		this.nppId = "";
		this.nppTen = "";
		this.kenhId = "";
		this.dvkdId = "";
		this.nhanhangId = "";
		this.chungloaiId = "";
		this.tungay = "";
		this.denngay = "";
		this.hangtrave = "";
		this.userTen = "";
		this.khoId = "";
		this.book = "";
		this.msg = "";
		this.vungId = "";
		this.khuvucId = "";
		this.vat = "";
		this.gsbhId = "";
		this.sanphamId = "";
		this.dvdlId = "";
		this.ngayton = "1";
		this.promotion = "0";
		this.discount = "0";
		this.lessday = "0";
		this.moreday = "0";
		this.ddkd = "";
		this.unit = "";
		this.groupCus = "";
		this.programsId = "";
		this.donviTinh = "";
		this.month = Integer.toString(Integer.parseInt(getDate().substring(5, 7)));
		this.year = "";
		this.fromMonth = "";
		this.toMonth = "";
		this.unghang = "1";
		this.Nhamayid = "";
		this.type = "1";
		this.KhId = "";
		this.LoaiSpid = "";
		this.ErpKhoId = "";
		this.ErpTaiKhoanKTId = "";
		this.ErpKhachhangId = "";
		this.ErpDonViTHid = "";
		this.loainccIds = "";
		this.khachhangIds = "";
		this.Pivot = "";
		this.nhomKhid = "";
		this.loaitaisanid = "";
		this.nhomtaisanid = "";
		this.view = "0";
		this.sanphamId = "";
		this.taisanid = "";
		this.taisanids = "";

		this.ttcpid = "";
		this.loaiBC = "0";
		this.nppdangnhap = "";
		this.nhanvienId = "";
		this.tenxuathd = "0";
		this.diabanId = "";
		this.db = new dbutils();
		this.chiNhanh = "";
		this.chiNhanhTen = "";
		this.congTy = "";
	}

	public void setuserId(String userId) {

		this.userId = userId;
	}

	public String getuserId() {

		return this.userId;
	}

	public void setIsNPP(String isNPP) {

		this.isNPP = isNPP;
	}

	public String getIsNPP() {

		return this.isNPP;
	}

	public void setnppId(String nppId) {

		this.nppId = nppId;
	}

	public String getnppId() {

		return this.nppId;
	}

	public void setnppTen(String nppTen) {

		this.nppTen = nppTen;
	}

	public String getnppTen() {

		return this.nppTen;
	}

	public void setkenhId(String kenhId) {

		this.kenhId = kenhId;
	}

	public String getkenhId() {

		return this.kenhId;
	}

	public void setdvkdId(String dvkdId) {

		this.dvkdId = dvkdId;
	}

	public String getdvkdId() {

		return this.dvkdId;
	}

	public void setnhanhangId(String nhanhangId) {

		this.nhanhangId = nhanhangId;
	}

	public String getnhanhangId() {

		return this.nhanhangId;
	}

	public void setchungloaiId(String chungloaiId) {

		this.chungloaiId = chungloaiId;
	}

	public String getchungloaiId() {

		return this.chungloaiId;
	}

	public void setkenh(ResultSet kenh) {

		this.kenh = kenh;
	}

	public ResultSet getkenh() {

		return this.kenh;
	}

	public void setdvkd(ResultSet dvkd) {

		this.dvkd = dvkd;
	}

	public ResultSet getdvkd() {

		return this.dvkd;
	}

	public void setnhanhang(ResultSet nhanhang) {

		this.nhanhang = nhanhang;
	}

	public ResultSet getnhanhang() {

		return this.nhanhang;
	}

	public void setchungloai(ResultSet chungloai) {

		this.chungloai = chungloai;
	}

	public ResultSet getchungloai() {

		return this.chungloai;
	}

	public void setMonth(String month) {

		this.month = month;
	}

	public String getMonth() {
		if (this.month != null && this.month.length() > 0) {
			return this.month;
		} else {
			return this.getDate().substring(5, 7);
		}
	}

	public void setUnghang(String unghang) {

		this.unghang = unghang;
	}

	public String getUnghang() {

		return this.unghang;
	}

	public void setLNccIds(String[] lnccIds) {

		this.lnccIds = lnccIds;
	}

	public String[] getLNccIds() {

		return this.lnccIds;
	}

	public void setNccIds(String[] nccIds) {

		this.nccIds = nccIds;
	}

	public String[] getNccIds() {

		return this.nccIds;
	}

	public void setLSPIds(String[] lspIds) {

		this.lspIds = lspIds;
	}

	public String[] getLSPIds() {

		return this.lspIds;
	}

	public void setYear(String year) {

		this.year = year;
	}

	public String getYear() {
		if (this.year.length() > 0) {
			return this.year;
		} else {
			return this.getDate().substring(0, 4);
		}
	}
	@Override
	public String getChiNhanh() {
		return chiNhanh;
	}

	@Override
	public void setChiNhanh(String chiNhanh) {
		this.chiNhanh = chiNhanh;
	}
	

	@Override
	public String getChiNhanhTen() {
		return chiNhanhTen;
	}

	@Override
	public void setChiNhanhTen(String chiNhanhTen) {
		this.chiNhanhTen = chiNhanhTen;
	}

	public void init() {

		// Utility Ult = new Utility();
		// this.nppId = Ult.getIdNhapp(this.userId);
		// this.nppTen = Ult.getTenNhaPP();
		this.kenh = db.get(" select pk_seq,ten,diengiai from kenhbanhang ");

		this.vung = db.get("select pk_seq,ten,diengiai from vung ");

		this.dvkd = db.get("select pk_seq,diengiai from donvikinhdoanh ");

		this.rsNhamay = db.get("select pk_seq,tennhamay as ten from ERP_NHAMAY ");
		if (this.vungId.length() > 2) {
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk ='" + this.vungId + "'");
		} else
			this.khuvuc = db.get("select pk_seq,ten from khuvuc ");

		String sql = "select pk_seq,ten from ERP_KHACHHANG where trangthai ='1' ";
		if (this.khuvucId.length() > 2) {
			sql = sql + " and khuvuc_fk ='" + this.khuvucId + "'";
		}
		if (this.vungId.length() > 2) {
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		// System.out.println("Get NPP :"+sql);
		if (this.kenhId.length() > 2)
			sql = sql + " and KBH_FK =" + this.kenhId;

		this.npp = db.get(sql);

		sql = " SELECT KH.PK_SEQ,KH.MA,KH.Ten FROM ERP_KHACHHANG KH  where 1=1 ";

		if (this.khuvucId.length() > 2) {
			sql = sql + " and khuvuc_fk ='" + this.khuvucId + "'";
		}

		if (this.vungId.length() > 2) {
			sql = sql + " and khuvuc_fk in (select pk_seq from khuvuc where vung_fk ='" + this.vungId + "')";
		}
		if (this.kenhId.length() > 2)
			sql = sql + " and KBH_FK =" + this.kenhId;

		this.npp = db.get(sql);

		this.rskhachhang = db.get(sql);

		if (this.dvkdId.length() > 2)
			this.nhanhang = db.get("select * from nhanhang where dvkd_fk ='" + this.dvkdId + "'");
		else
			this.nhanhang = db.get("select * from nhanhang ");

		this.chungloai = db.get("select pk_Seq,ten  from chungloai");

		sql = "select pk_seq,ten  from giamsatbanhang where trangthai ='1'";
		if (this.kenhId.length() > 2) {
			sql = sql + " and kbh_fk ='" + this.kenhId + "'";
		} else if (this.dvkdId.length() > 2) {
			sql = sql + " and dvkd_fk ='" + this.dvkdId + "'";
		}

		this.gsbh = db.get(sql);

		String st = "select pk_seq,ma,ten from ERP_sanpham where trangthai ='1'  ";
		if (this.nhanhangId.length() > 2)
			st = st + " and nhanhang_fk ='" + this.nhanhangId + "'";
		if (this.chungloaiId.length() > 2)
			st = st + " and chungloai_fk ='" + this.chungloaiId + "'";
		if (this.dvkdId.length() > 2)
			st = st + " and dvkd_fk ='" + this.dvkdId + "'";
		if (this.dvdlId.length() > 2)
			st = st + " and dvdl_fk ='" + this.dvdlId + "'";

		if (this.Nhospid.length() > 2)
			st = st + " and pk_seq in  (select sp_fk from nhomsanpham_sanpham where nsp_fk='" + this.Nhospid + "')";

		if (this.LoaiSpid.length() > 2) {
			st = st + "and loaisanpham_fk =" + this.LoaiSpid;
		}

		this.sanpham = db.get(st);

		sql = "select pk_seq,donvi from donvidoluong ";

		this.dvdl = db.get(sql);
		this.kho = db.get("select * from kho ");

		sql = " select pk_seq,ten from erp_khott ";
		this.RsErpKho = db.get(sql);

		sql = "select pk_seq,ten from erp_loaisanpham ";
		this.RsLoaiSp = db.get(sql);

		// Dai dien kinh doanh
		if (this.nppId.length() > 0) {
			this.setRsddkd(db.get(
					"SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where NPP_FK='" + this.nppId + "' and d.trangthai=1"));
		} else {
			this.setRsddkd(db.get("SELECT pk_seq,ten FROM DAIDIENKINHDOANH d where trangthai=1 "));
		}

		// Lay chuong trinh khuyen mai
		this.rsPrograms = db.get("SELECT pk_seq,diengiai,scheme FROM CTKHUYENMAI c ");

		this.RsNhomSp = db.get("select pk_seq,diengiai from nhomsanpham where trangthai=1");

		String query = "select substring(ngaynhap, 0, 5) as nam, substring(ngaynhap, 6, 2) as thang, sum(tonggiatri) as doanhsothang "
				+ "from donhang " + "where substring(ngaynhap, 0, 5) = '2011' "
				+ "group by  substring(ngaynhap, 0, 5),  substring(ngaynhap, 6, 2) " + "union all "
				+ "select substring(ngaynhap, 0, 5) as nam, substring(ngaynhap, 6, 2) as thang, sum(tonggiatri) as doanhsothang "
				+ "from donhang " + "where substring(ngaynhap, 0, 5) = '2012' "
				+ "group by  substring(ngaynhap, 0, 5),  substring(ngaynhap, 6, 2) "
				+ "order by substring(ngaynhap, 0, 5) asc, substring(ngaynhap, 6, 2) asc";

		ResultSet rs = db.get(query);
		List<IDataChart> dataChart = new ArrayList<IDataChart>();
		if (rs != null) {
			try {
				IDataChart chart = null;
				while (rs.next()) {
					chart = new DataChart();

					chart.setNam(rs.getString("nam"));
					chart.setThang(rs.getString("thang"));
					chart.setData(Long.toString(Math.round(rs.getDouble("doanhsothang"))));

					dataChart.add(chart);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			this.chartRs = dataChart;
		}
		// this.chartRs = db.get(query);
	}

	public void settungay(String tungay) {

		this.tungay = tungay;
	}

	public String gettungay() {
		return tungay;
	}

	public void setdenngay(String denngay) {

		this.denngay = denngay;
	}

	public String getdenngay() {

		return this.denngay;
	}

	public void setMsg(String msg) {

		this.msg = msg;
	}

	public String getMsg() {

		return this.msg;
	}

	public void setuserTen(String userTen) {

		this.userTen = userTen;
	}

	public String getuserTen() {

		return this.userTen;
	}

	public void setkhoId(String khoId) {

		this.khoId = khoId;
	}

	public String getkhoId() {

		return this.khoId;
	}

	public void setkho(ResultSet kho) {

		this.kho = kho;
	}

	public ResultSet getkho() {

		return this.kho;
	}

	public void setCtyRs(ResultSet ctyRs) {

		this.congtyRs = ctyRs;
	}

	public ResultSet getCtyRs() {

		return this.congtyRs;
	}

	public void setCtyIds(String[] ctyIds) {

		this.ctyIds = ctyIds;
	}

	public String[] getCtyIds() {

		return this.ctyIds;
	}

	public void setbook(String book) {

		this.book = book;
	}

	public String getbook() {

		return this.book;
	}

	public void setvat(String vat) {

		this.vat = vat;
	}

	public void DBclose() {
		try {
			if (chungloai != null)
				chungloai.close();
			if (loaitaisanrs != null)
				loaitaisanrs.close();
			if (nhomtaisanrs != null)
				nhomtaisanrs.close();
			if (dvdl != null)
				dvdl.close();
			if (dvkd != null)
				dvkd.close();
			if (gsbh != null)
				gsbh.close();
			if (kenh != null)
				kenh.close();
			if (kho != null)
				kho.close();
			if (khuvuc != null)
				khuvuc.close();
			if (nhanhang != null)
				nhanhang.close();
			if (npp != null)
				npp.close();
			if (rsddkd != null)
				rsddkd.close();
			if (rsPrograms != null)
				rsPrograms.close();
			if (sanpham != null)
				sanpham.close();
			if (vung != null)
				vung.close();

			if (RsErpCongty != null) {
				RsErpCongty.close();
			}

			if (this.congtyRs != null)
				this.congtyRs.close();

			if (RsKhErp != null) {
				RsKhErp.close();
			}

			if (RsErpKho != null) {
				RsErpKho.close();
			}

			if (RsErpTiente != null) {
				RsErpTiente.close();
			}

			if (this.RsLoaiSp != null)
				this.RsLoaiSp.close();

			if (this.rsErpNCC != null)
				this.rsErpNCC.close(); // Bien nay luu loai ncc

			if (this.rsNCC != null)
				this.rsNCC.close(); // Bien nay luu NCC

			if (db != null)
				db.shutDown();

		} catch (Exception e) {
			e.printStackTrace();
			// System.out.println("khong the dong ket noi");
		}
	}

	public void setvungId(String vungId) {

		this.vungId = vungId;
	}

	public String getvungId() {

		return this.vungId;
	}

	public void setvung(ResultSet vung) {

		this.vung = vung;
	}

	public ResultSet getvung() {

		return this.vung;
	}

	public void setkhuvucId(String khuvucId) {

		this.khuvucId = khuvucId;
	}

	public String getkhuvucId() {

		return this.khuvucId;
	}

	public void setkhuvuc(ResultSet khuvuc) {

		this.khuvuc = khuvuc;
	}

	public ResultSet getkhuvuc() {

		return this.khuvuc;
	}

	public void setnpp(ResultSet npp) {

		this.npp = npp;
	}

	public ResultSet getnpp() {

		return this.npp;
	}

	public void setgsbhId(String gsbhId) {

		this.gsbhId = gsbhId;
	}

	public String getgsbhId() {

		return this.gsbhId;
	}

	public void setgsbh(ResultSet gsbh) {

		this.gsbh = gsbh;
	}

	public ResultSet getgsbh() {

		return this.gsbh;
	}

	public void setsanphamId(String sanphamId) {

		this.sanphamId = sanphamId;
	}

	public String getsanphamId() {

		return this.sanphamId;
	}

	public void setsanpham(ResultSet sanpham) {

		this.sanpham = sanpham;
	}

	public ResultSet getsanpham() {

		return this.sanpham;
	}

	public void setdvdlId(String dvdlId) {

		this.dvdlId = dvdlId;
	}

	public String getdvdlId() {

		return this.dvdlId;
	}

	public void setdvdl(ResultSet dvdl) {

		this.dvdl = dvdl;
	}

	public ResultSet getdvdl() {

		return this.dvdl;
	}

	public void setFieldShow(String[] fieldShow) {

		this.FieldShow = fieldShow;
	}

	public String[] getFieldShow() {
		return this.FieldShow;

	}

	public void setFieldHidden(String[] fieldHidden) {

		this.FieldHidden = fieldHidden;
	}

	public String[] getFieldHidden() {

		return this.FieldHidden;
	}

	public void setngayton(String ngayton) {

		this.ngayton = ngayton;
	}

	public String getngayton() {

		return this.ngayton;
	}

	public String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getdiscount() {

		return this.discount;
	}

	public String getpromotion() {

		return this.promotion;
	}

	public void setdiscount(String discount) {

		this.discount = discount;
	}

	public void setpromotion(String promotion) {

		this.promotion = promotion;
	}

	public String getvat() {

		return this.vat;
	}

	public String getlessday() {

		return this.lessday;
	}

	public String getmoreday() {

		return this.moreday;
	}

	public void setlessday(String lessday) {

		this.lessday = lessday;
	}

	public void setmoreday(String moreday) {

		this.moreday = moreday;
	}

	public void setDdkd(String ddkd) {
		this.ddkd = ddkd;
	}

	public String getDdkd() {

		return this.ddkd;
	}

	public void setRsddkd(ResultSet rs) {
		this.rsddkd = rs;
	}

	public ResultSet getRsddkd() {
		return this.rsddkd;
	}

	public void setUnit(String unit) {
		this.unit = unit;
	}

	public String getUnit() {

		return this.unit;
	}

	public void setGroupCus(String groupCus) {
		this.groupCus = groupCus;
	}

	public String getGroupCus() {

		return this.groupCus;
	}

	public void setPrograms(String programs) {
		this.programsId = programs;
	}

	public String getPrograms() {

		return this.programsId;
	}

	public void setRsPrograms(ResultSet RsPrograms) {
		this.rsPrograms = RsPrograms;
	}

	public ResultSet getRsPrograms() {

		return this.rsPrograms;
	}

	public void setDonViTinh(String donviTinh) {
		this.donviTinh = donviTinh;

	}

	public String getDonViTinh() {
		return this.donviTinh;
	}

	public void setFromMonth(String month) {
		this.fromMonth = month;
	}

	public String getFromMonth() {
		return this.fromMonth;
	}

	public void setToMonth(String month) {
		this.toMonth = month;
	}

	public String getToMonth() {
		return this.toMonth;
	}

	public void settype(String _type) {

		this.type = _type;
	}

	public String gettype() {

		return this.type;
	}

	public void setFromYear(String fromyear) {

		this.FromYear = fromyear;
	}

	public String getFromYear() {

		return this.FromYear;
	}

	public void setToYear(String toyear) {

		this.ToYear = toyear;
	}

	public String getToYear() {

		return this.ToYear;
	}

	public void SetNhoSPId(String nhomspid) {

		this.Nhospid = nhomspid;
	}

	public String GetNhoSPId() {

		return this.Nhospid;
	}

	public void setRsNhomSP(ResultSet rs) {

		this.RsNhomSp = rs;
	}

	public ResultSet GetRsNhomSP() {

		return this.RsNhomSp;
	}

	public void setChartRs(List<IDataChart> chartRs) {
		this.chartRs = chartRs;
	}

	public List<IDataChart> getChartRs() {
		return this.chartRs;
	}

	public void SetKHid(String KhId) {

		this.KhId = KhId;
	}

	public String GetKhId() {

		return this.KhId;
	}

	public void setRsKhErp(ResultSet rs) {

		if (rs == null) {
			String sql = "select pk_seq,ten,ma from erp_khachhang";
			// System.out.println(sql);
			this.RsKhErp = this.db.get(sql);

		}
		this.RsKhErp = rs;
	}

	public ResultSet GetRsKhErp() {

		return this.RsKhErp;
	}

	public void SetLoaiSPId(String loaispid) {

		this.LoaiSpid = loaispid;
	}

	public String GetLoaiSPId() {

		return this.LoaiSpid;
	}

	public void setRsLoaiSP(ResultSet rs) {

		this.RsLoaiSp = rs;
	}

	public ResultSet GetRsLoaiSP() {

		return this.RsLoaiSp;
	}

	public void SetErpKhottId(String id) {

		this.ErpKhoId = id;
	}

	public String GetErpKhottId() {

		return this.ErpKhoId;
	}

	public void setRsErpKho(ResultSet rs) {

		this.RsErpKho = rs;
	}

	public ResultSet GetRsErpKho() {

		return this.RsErpKho;
	}

	public ResultSet getRsErpCongty() {

		return this.RsErpCongty;
	}

	public void setRsErpCongty(ResultSet rs) {

		this.RsErpCongty = rs;
	}

	public String getErpCongtyId() {

		return this.ErpCongTyId;
	}

	public void setErpCongtyId(String id) {

		this.ErpCongTyId = id;
	}

	public ResultSet getRsErpTiente() {

		return this.RsErpTiente;
	}

	public void setRsErpTiente(ResultSet rs) {

		this.RsErpTiente = rs;
	}

	public String getErpTienteId() {

		return this.ErpTienteId;
	}

	public void setErpTienteId(String id) {

		this.ErpTienteId = id;
	}

	public ResultSet getCongtyRS(String ctyId) {
		return db.get("SELECT NPP.DIACHI, CTY.PK_SEQ, CTY.TEN, CTY.MA, CTY.MASOTHUE, CTY.DIENTHOAI, CTY.FAX \n "
				+ "FROM NHAPHANPHOI NPP  \n " + "INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ = NPP.CONGTY_FK \n "
				+ "WHERE NPP.CONGTY_FK =  " + ctyId + "");
	}

	public ResultSet getKhachhangRS(String khId) {
		String[] tmp = this.KhId.split("-");
		String query = "";

		if (tmp[1].equals("0")) {
			query = "select TEN  from KHACHHANG where PK_SEQ = " + tmp[0] + "";
		} else {
			query = "select TEN  from NHAPHANPHOI where PK_SEQ = " + tmp[0] + "";
		}

		return db.get(query);
	}

	public ResultSet getKhachhangRs() {

		Utility util = new Utility();
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "KH", "pk_seq", this.getLoainhanvien(),
				this.getDoituongId());

		String query = " SELECT CONVERT(VARCHAR, PK_SEQ) + '-0' AS PK_SEQ, '[KH]'+ MAFAST MA, TEN \n"
				+ " FROM KHACHHANG KH WHERE  TRANGTHAI = 1 AND CONGTY_FK = " + this.ErpCongTyId + " \n" + strQUYEN +

				" UNION ALL \n" +

				" SELECT CONVERT(VARCHAR, PK_SEQ) + '-1' AS PK_SEQ, '[NPP]'+ MAFAST MA, TEN  \n"
				+ " FROM NHAPHANPHOI WHERE  TRANGTHAI = 1 AND TRUCTHUOC_FK = (SELECT PK_SEQ FROM NHAPHANPHOI WHERE CONGTY_FK = "
				+ this.ErpCongTyId + ") \n" + " AND PK_SEQ in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '"
				+ this.userId + "' ) \n" +

				" ORDER BY TEN ";
		System.out.println("Khach hang: " + query);

		// PHÂN QUYỀN THEO LOẠI NHÂN VIÊN ĐĂNG NHẬP

		// query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or (
		// a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from
		// PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) ";

		return this.db.getScrol(query);
	}

	public void InitErp() {

		this.vung = db.get("select pk_seq,ten,diengiai from vung ");

		if (this.vungId.length() > 1) {
			this.khuvuc = db.get("select pk_seq,ten from khuvuc where vung_fk in (" + this.vungId + " ) ");
		} else
			this.khuvuc = db.get("select pk_seq,ten from khuvuc ");

//		Utility util = new Utility();

		if (this.ctyIds != null) {
			if (this.ErpCongTyId.length() == 0) {
				String tmp = "";
				for (int i = 0; i < this.ctyIds.length; i++) {
					tmp += this.ctyIds[i] + ",";
				}
				this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
			}
		} else {
			if (this.ErpCongTyId == null || this.ErpCongTyId.length() == 0) {
				String tmp = "";
				ResultSet rs = this.db.get(
						"SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE isKHACHHANG = 0 AND TRANGTHAI = 1 and pk_seq = 1");
				try {
					while (rs.next()) {
						tmp += rs.getString("PK_SEQ") + ",";
					}
					if (tmp.length() > 0)
						this.ErpCongTyId = tmp.substring(0, tmp.length() - 1);
				} catch (java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
		}

		String sql = " select pk_seq,ten,ma, diachi, masothue  from erp_congty " + " where  TRANGTHAI = 1 ";

		this.RsErpCongty = db.get(sql);

		this.rsNhamay = db.get("select pk_seq,tennhamay as ten from ERP_NHAMAY ");

		sql = "select pk_seq,ten,ma from erp_tiente";
		this.RsErpTiente = db.get(sql);

		sql = "select pk_seq,ma,ten from erp_loaitaikhoan";
		this.rsErpLoaiTK = db.get(sql);

		sql = "SELECT PK_SEQ,MA,TEN  FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI=1 and congty_fk in (" + this.ErpCongTyId
				+ ") " + " and pk_seq in (select donvithuchien_fk from nhanvien_donvithuchien where nhanvien_fk="
				+ this.userId + ")";
		this.rsErpDonViThucHien = db.get(sql);

		sql = "SELECT PK_SEQ, DIENGIAI FROM KENHBANHANG WHERE TRANGTHAI = 1 ";
		this.kenh = db.get(sql);

		sql = "SELECT PK_SEQ, MA, TEN FROM ERP_LOAINHACUNGCAP WHERE TRANGTHAI = 1 ";
		this.rsErpNCC = db.get(sql);

		sql = "select pk_seq,ten,ma from erp_nhacungcap WHERE CONGTY_FK in (" + this.ErpCongTyId + ") ";

		String lncclist = "";
		if (lnccIds != null) {
			if (lnccIds.length > 0) {
				for (int i = 0; i < lnccIds.length; i++) {
					if (lncclist.length() == 0) {
						lncclist = lncclist + lnccIds[i];
					} else {
						lncclist = lncclist + ", " + lnccIds[i];
					}
				}

			}
		}
		if (lncclist != null && lncclist.length() > 0) {
			sql = sql + " and loainhacungcap_fk in (" + lncclist + ")";
		}

		this.rsNCC = db.get(sql);

		this.nhomKhRs = db.get("select PK_SEQ, DIENGIAI from NHOMKHACHHANGNPP where TRANGTHAI = 1");

		sql = "\n SELECT PK_SEQ, MA, TEN FROM NHAPHANPHOI " + "\n WHERE TRANGTHAI = 1 AND PK_SEQ != 1 \n";
		System.out.println("Cau lay KH " + sql);
		this.RsKhErp = this.db.get(sql);

		sql = "select pk_seq,ten from erp_loaisanpham ";
		this.RsLoaiSp = db.get(sql);
		sql = "select pk_Seq,donvikinhdoanh  as ten from  donvikinhdoanh";
		this.dvkd = db.get(sql);

		sql = "select pk_seq, diengiai from erp_loaitaisan where trangthai = 1 ";
		this.loaitaisanrs = db.get(sql);

		sql = "select pk_seq, diengiai from erp_nhomtaisan where trangthai = 1 ";
		this.nhomtaisanrs = db.get(sql);

		sql = "select pk_seq, diengiai from erp_trungtamchiphi where trangthai = 1 ";
		this.ttcprs = db.get(sql);

		sql = "select pk_seq, ma, ten from ERP_TAISANCODINH where trangthai = 1 ";
		this.taisanRs = db.get(sql);

		sql = "select pk_seq, ma, ten from ERP_NHANVIEN where trangthai = 1 ";
		this.nhanvienRs = db.get(sql);

		sql = "SELECT PK_SEQ, MA,TEN FROM NHAPHANPHOI WHERE isKHACHHANG = 0 AND TRANGTHAI = 1 ";
		this.congtyRs = db.get(sql);
	}

	public void getCongTyBaoCao() {

		try {
			String sql = "SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE isKHACHHANG = 0 AND TRANGTHAI = 1";
			this.congtyRs = db.get(sql);
			sql = "select pk_seq,ten,ma from erp_tiente";
			this.RsErpTiente = db.get(sql);
			sql = "SELECT PK_SEQ,TEN FROM NHAPHANPHOI WHERE PK_SEQ =1 AND TRANGTHAI = 1";
			ResultSet rs = db.get(sql);
			if (this.ctyIds != null) {
				if (this.ctyIds.length > 0) {
					this.ErpCongTyId = "";
					for (int i = 0; i < this.ctyIds.length; i++) {
						this.ErpCongTyId = this.ErpCongTyId + this.ctyIds[i] + ",";
					}
				}
			} else {
				if (rs != null) {
					while (rs.next()) {
						this.ErpCongTyId = rs.getString("PK_SEQ");
					}
				}
			}
			System.out.println("this.erpcongtyid" + this.ErpCongTyId);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	public String getErpTaiKhoanKTId() {

		return this.ErpTaiKhoanKTId;
	}

	public void setErpTaiKhoanKTId(String rs) {

		this.ErpTaiKhoanKTId = rs;
	}

	public String getErpLoaiTaiKhoanKTId() {

		return this.ErpLoaiTk;
	}

	public void setErpLoaiKhoanKTId(String id) {

		this.ErpLoaiTk = id;
	}

	public ResultSet getRsErpLoaiTK() {

		return this.rsErpLoaiTK;
	}

	public void setRsErpLoaiTK(ResultSet rs) {

		this.rsErpLoaiTK = rs;
	}

	public String getErpKhachHangId() {

		return this.ErpKhachhangId;
	}

	public void setErpKhachHangId(String id) {

		this.ErpKhachhangId = id;
	}

	public String[][] getMang2chieu() {

		return this.mang;
	}

	public void setMang2Chieu(String[][] mang) {

		this.mang = mang;
	}

	public ResultSet getRsErpDonViTH() {

		return this.rsErpDonViThucHien;
	}

	public void setRsErpDonViTH(ResultSet rs) {

		this.rsErpDonViThucHien = rs;
	}

	public String getErpDonViTHId() {

		return this.ErpDonViTHid;
	}

	public void setErpDonViTHid(String id) {

		this.ErpDonViTHid = id;
	}

	public String getErpNCCId() {

		return this.ErpNCCId;
	}

	public void setErpNCCId(String id) {

		ErpNCCId = id;
	}

	public ResultSet getRsErpNCCId() {

		return this.rsErpNCC; // Tra ve Loai NCC
	}

	public void setRsErpNCCId(ResultSet rs) {

		this.rsErpNCC = rs;
	}

	public ResultSet getRsLoaiNCC() {

		return this.rsErpNCC; // Tra ve Loai NCC
	}

	public void setRsLoaiNCC(ResultSet rs) {

		this.rsErpNCC = rs;
	}

	public ResultSet getRsNCC() {

		return this.rsNCC; // Tra ve NCC
	}

	public void setRsNCC(ResultSet rs) {

		this.rsNCC = rs;
	}

	public String getthangtrave() {

		return this.hangtrave;
	}

	public void sethangtrave(String hangtrave) {

		this.hangtrave = hangtrave;
	}

	public void setrskhachhang(ResultSet rs) {

		this.rskhachhang = rs;
	}

	public ResultSet getrskhachhang() {

		return this.rskhachhang;
	}

	public String getLoaiNCCIds() {

		return this.loainccIds;
	}

	public String getLoaincc() {
		String loaincc = "";

		if (this.loainccIds.length() > 0) {
			String query = "SELECT MA + '-' + TEN AS LOAINCC FROM ERP_LOAINHACUNGCAP WHERE PK_SEQ IN( "
					+ this.loainccIds + ")";
			ResultSet rs = this.db.get(query);
			if (rs != null) {
				try {
					while (rs.next()) {
						loaincc = loaincc + rs.getString("LOAINCC") + " ; ";
					}
					rs.close();
				} catch (java.sql.SQLException e) {
					e.printStackTrace();
				}
			}
			if (loaincc.length() > 2) {
				loaincc = loaincc.substring(0, loaincc.length() - 2);
			}
		}
		return loaincc;
	}

	public ResultSet getRsErpLoaiNCC() {
		String query = "SELECT MA + '-' + TEN AS LOAINCC FROM ERP_LOAINHACUNGCAP WHERE TRANGTHAI = 1";
		return this.db.get(query);
	}

	public void setLoaiNCCIds(String loainccIds) {

		this.loainccIds = loainccIds;
	}

	public String getKenhBH() {
		String query = "SELECT DIENGIAI FROM KENHBANHANG WHERE PK_SEQ = " + this.kenhId + " ";
		// System.out.println(query);

		ResultSet rs = this.db.get(query);
		String kbh = "";
		if (rs != null) {
			try {
				rs.next();
				kbh = rs.getString("DIENGIAI");
				rs.close();
				return kbh;

			} catch (java.sql.SQLException e) {
				e.printStackTrace();
				return kbh;
			}
		} else {
			return kbh;
		}

	}

	public String getErpTiente() {

		String tiente = "";
		ResultSet rs = this.db.get("SELECT TEN FROM ERP_TIENTE WHERE PK_SEQ = " + this.ErpTienteId);
		try {
			if (rs != null) {
				rs.next();
				tiente = rs.getString("TEN");
				rs.close();
			}
			return tiente;

		} catch (java.sql.SQLException e) {
			e.printStackTrace();
		}

		return this.ErpTienteId;
	}

	public void setKhachhangIds(String khachhangIds) {
		this.khachhangIds = khachhangIds;

	}

	public String getKhachhangIds() {
		return this.khachhangIds;
	}

	public void setPivot(String Pivot) {

		this.Pivot = Pivot;
	}

	public String getPivot() {

		return this.Pivot;
	}

	public ResultSet getRsNhamay() {

		return this.rsNhamay;
	}

	public void setRsNhamay(ResultSet rs) {

		this.rsNhamay = rs;
	}

	public String getNhamayId() {

		return this.Nhamayid;
	}

	public void setNhamayId(String NhamayId) {

		this.Nhamayid = NhamayId;
	}

	public String getNhomKhId() {

		return this.nhomKhid;
	}

	public void setNhomKhId(String nhomkhid) {

		this.nhomKhid = nhomkhid;
	}

	public ResultSet getRsNhomKh() {

		return this.nhomKhRs;
	}

	public void setRsNhomKh(ResultSet nhomkhrs) {

		this.nhomKhRs = nhomkhrs;
	}

	public String getNhomTaiSanId() {

		return this.nhomtaisanid;
	}

	public void setNhomTaiSanId(String nhomtaisanid) {

		this.nhomtaisanid = nhomtaisanid;
	}

	public String getLoaiTaiSanId() {

		return this.loaitaisanid;
	}

	public void setLoaiTaiSanId(String loaitaisanid) {

		this.loaitaisanid = loaitaisanid;
	}

	public ResultSet getNhomTaiSanRs() {

		return this.nhomtaisanrs;
	}

	public void setNhomTaiSanRs(ResultSet nhomtaisanrs) {
		this.nhomtaisanrs = nhomtaisanrs;

	}

	public ResultSet getLoaiTaiSanRs() {

		return this.loaitaisanrs;
	}

	public void setLoaiTaiSanRs(ResultSet loaitaisanrs) {

		this.loaitaisanrs = loaitaisanrs;
	}

	public void setView(String view) {
		this.view = view;
	}

	public String getView() {
		return this.view;
	}

	public void settaisanIds(String taisanIds) {

		this.taisanids = taisanIds;
	}

	public String gettaisanIds() {

		return taisanids;
	}

	public void settaisanRs(ResultSet taisanRs) {

		this.taisanRs = taisanRs;
	}

	public ResultSet gettaisanRs() {

		return this.taisanRs;
	}

	public String gettaisanId() {

		return this.taisanid;
	}

	public void settaisanId(String taisanId) {

		this.taisanid = taisanId;
	}

	public void setLoaiBC(String loaiBC) {
		this.loaiBC = loaiBC;

	}

	public String getLoaiBC() {
		return this.loaiBC;
	}

	public String getTTCPId() {
		return this.ttcpid;
	}

	public void setTTCPId(String ttcpid) {
		this.ttcpid = ttcpid;
	}

	public ResultSet getTTCPRs() {
		return this.ttcprs;
	}

	public void setTTCPRs(ResultSet ttcprs) {
		this.ttcprs = ttcprs;
	}

	public String getnppdangnhap() {

		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) {

		this.nppdangnhap = nppdangnhap;
	}

	public String getNhanvienId() {

		return this.nhanvienId;
	}

	public void setNhanvienId(String nhanvienid) {

		this.nhanvienId = nhanvienid;
	}

	public ResultSet getRsNhanvien() {

		return this.nhanvienRs;
	}

	public void setRsNhanvien(ResultSet nhanvienrs) {

		this.nhanvienRs = nhanvienrs;
	}

	public ResultSet getNppRs() {
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) {
		this.nppRs = nppRs;
	}

	Object loainhanvien;
	Object doituongId;

	public String getLoainhanvien() {
		if (this.loainhanvien == null)
			return "";

		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) {
		this.loainhanvien = loainhanvien;
	}

	public String getDoituongId() {
		if (this.doituongId == null)
			return "";

		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) {
		this.doituongId = doituongId;
	}

	public String getTenxuathd() {

		return this.tenxuathd;
	}

	public void setTenxuathd(String tenxuathd) {

		this.tenxuathd = tenxuathd;
	}

	public ResultSet getDiabanRs() {

		String query = "SELECT CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, TEN AS TEN FROM diaban WHERE  TRANGTHAI = 1 ";
		System.out.println(query);

		return this.db.getScrol(query);
	}

	public String getDiabanId() {

		return this.diabanId;
	}

	public void setDiabanId(String diabanId) {

		this.diabanId = diabanId;
	}

	public List<BaoCaoVayTienPoJo> getListBaoCaoVayTien() {
		return listBaoCaoVayTien;
	}

	public void setListBaoCaoVayTien(List<BaoCaoVayTienPoJo> listBaoCaoVayTien) {
		this.listBaoCaoVayTien = listBaoCaoVayTien;
	}

	@Override
	public void init_Khoaso() {
		// TODO Auto-generated method stub
		String sql = " select pk_seq,ten,isnull(loai,'') as loai from erp_khott ";
		this.RsErpKho = db.get(sql);

	}
	@Override
	public void  initKhachHangRs() {
		//Lay thong tin khach hang
		String sql = "SELECT PK_SEQ, ISNULL(MA, '') AS MA, TEN FROM (\r\n" + 
				"			SELECT 'KH'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MAFAST AS MA, TEN FROM  " + geso.traphaco.center.util.Utility.prefixDMS + "KHACHHANG WHERE TRANGTHAI = 1 AND NPP_FK IS NOT NULL \r\n" + 
				"			UNION ALL \r\n" + 
				"			SELECT 'NPP'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM NHAPHANPHOI WHERE TRANGTHAI = 1 \r\n" + 
				"			AND PK_SEQ != 1"
				+ "			UNION ALL \r\n" + 
				"			SELECT 'NV'+CONVERT(VARCHAR, PK_SEQ) AS PK_SEQ, MA, TEN FROM ERP_NHANVIEN WHERE TRANGTHAI = 1 \r\n" + 
				"		) DANHMUC"; 
		this.RsKhErp = this.db.get(sql);
	}
	
	@Override
	public void initChiNhanhRs() {
		//Lay thong tin khach hang
		String query = "SELECT PK_SEQ, TEN\r\n" + 
				"FROM NHAPHANPHOI \r\n" + 
				"WHERE ISKHACHHANG = '0'\r\n" + 
				"  AND LOAINPP IN (0, 1, 2, 3)";
		System.out.println(query);
		this.chiNhanhRs = this.db.get(query);
	}
	@Override
	public ResultSet getChiNhanhRs() {
		return chiNhanhRs;
	}

	@Override
	public void setChiNhanhRs(ResultSet chiNhanhRs) {
		this.chiNhanhRs = chiNhanhRs;
	}

	@Override
	public String getCongTy() {
		return congTy;
	}

	@Override
	public void setCongTy(String congTy) {
		this.congTy = congTy;
	}
	
	
}
