package geso.traphaco.erp.beans.nhanhangsanxuat.imp;

//
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Donvi;
import geso.traphaco.erp.beans.nhanhangsanxuat.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangsanxuat.ISanpham;
import geso.traphaco.erp.beans.nhanhangsanxuat.ISpDetail;
import geso.traphaco.erp.beans.nhanhangsanxuat.imp.DetailMeSp;
import geso.traphaco.erp.util.Library;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpNhanhang_Giay implements IErpNhanhang_Giay {
	String congtyId;
	String nppId;
	String userId;
	String id;
	String ngaynhanhang;
	String ngaychot;
	String sohoadon;
	String loaispId = "";
	String dvthId;
	String lydo;

	int is_saungayKS;
	String ghichu;

	ResultSet dvthRs;

	String poId;
	ResultSet poRs;

	String KhonhanTpdat;
	ResultSet RsKhoNhanTpDat;

	String ndnId;
	ResultSet ndnRs;

	String ldnId;
	ResultSet ldnRs;

	String nccId;
	ResultSet nccRs;

	String mahangmuaId;
	int ngayhethan;
	ResultSet mahangmuaRs;

	String diengiai;
	String trangthai;
	String soluongPO;
	String soluongDaNhan;
	String loaihanghoa;
	String tigia;
	String sopoId;
	String muahang_fk1;
	String is_createRs = "";

	List<ISanpham> spList;

	String isPONK;

	String isNCCNK;

	String hdNccId;
	ResultSet hdNccRs;

	String loaimh;
	String isTudong; // Phiếu nhận hàng tự động : 0 , ngược lại : 1

	String msg;
	String pb;
	boolean IsKhoNhanQL_khuvuc = false;
	dbutils db;
	private Utility util;
	private Utility_Kho util_kho = new Utility_Kho();
	String KhoNhanId;
	ResultSet RsKhoNhan;
	// Kho nhận chờ xử lý
	String khoChoXuLy;
	ResultSet RsKhoChoXuLy;

	ResultSet hoadonNCCList;

	ResultSet khachhangRs;
	String khachhangId;

	String loaikho = "";
	int tilequydoi_dvdl; // dành cho phần gia công.

	List<KhuVucKho> listKhuVucKho = new ArrayList<KhuVucKho>();
	List<KhuVucKho> listKhuVucKhoCXL = new ArrayList<KhuVucKho>();
	List<IDonvi> listDonvi = new ArrayList<IDonvi>();

	ResultSet congdoanRs;
	String idcongdoan;
	String loaisanpham;
	ResultSet phepham_duphamRs;
	String idphepham_dupham;

	String filename = "";

	String KhoNhanCoDoituong = "";
	String IdDoituongkhonhan = "";
	ResultSet DoituongnhanRs;

	public String getFilename() {
		return filename;
	}

	public void setFilename(String filename) {
		this.filename = filename;
	}

	public ErpNhanhang_Giay() {
		this.userId = "";
		this.id = "";
		this.ngaynhanhang = this.getDateTime();
		this.KhoNhanId = "";
		this.ngaychot = "";
		this.sohoadon = "";
		this.diengiai = "";
		this.dvthId = "";
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.loaihanghoa = "0";
		this.loaimh = "";
		this.KhonhanTpdat = "";
		this.msg = "";
		this.ngayhethan = 0;
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";
		this.lydo = "";

		this.isPONK = "0";
		this.hdNccId = "";

		this.isNCCNK = "0";
		this.pb = "";
		this.tigia = "1";
		this.muahang_fk1 = "";
		this.sopoId = "";
		this.is_createRs = "";

		this.is_saungayKS = 0;
		this.ghichu = "";
		this.isTudong = "0";

		this.khachhangId = "";
		this.loaikho = "0";

		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util = new Utility();
		this.khoChoXuLy = "";
		this.tilequydoi_dvdl = 1;
		this.loaisanpham = "";
		this.idphepham_dupham = "";
	}

	public ErpNhanhang_Giay(String id) {
		this.userId = "";
		this.KhonhanTpdat = "";

		this.id = id;
		this.ngaynhanhang = this.getDateTime();
		this.ngaychot = "";
		this.sohoadon = "";
		this.dvthId = "";
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.ngayhethan = 0;
		this.loaihanghoa = "0";
		this.loaimh = "";

		this.msg = "";
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";

		this.isPONK = "0";
		this.hdNccId = "";

		this.isNCCNK = "0";
		this.pb = "";
		this.tigia = "1";
		this.muahang_fk1 = "";
		this.sopoId = "";
		this.is_createRs = "";

		this.is_saungayKS = 0;
		this.ghichu = "";
		this.isTudong = "0";

		this.khachhangId = "";
		this.loaikho = "0";
		this.KhoNhanId = "";

		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util = new Utility();
		this.khoChoXuLy = "";
		this.tilequydoi_dvdl = 1;
		this.loaisanpham = "";
		this.idphepham_dupham = "";
	}

	public List<IDonvi> getListDonvi() {
		return listDonvi;
	}

	public void setListDonvi(List<IDonvi> listDonvi) {
		this.listDonvi = listDonvi;
	}

	public int getTilequydoi_dvdl() {
		return tilequydoi_dvdl;
	}

	public void setTilequydoi_dvdl(int tilequydoi_dvdl) {
		this.tilequydoi_dvdl = tilequydoi_dvdl;
	}

	public List<KhuVucKho> getListKhuVucKhoCXL() {
		return listKhuVucKhoCXL;
	}

	public void setListKhuVucKhoCXL(List<KhuVucKho> listKhuVucKhoCXL) {
		this.listKhuVucKhoCXL = listKhuVucKhoCXL;
	}

	public String getKhoChoXuLy() {
		return khoChoXuLy;
	}

	public void setKhoChoXuLy(String khoChoXuLy) {
		this.khoChoXuLy = khoChoXuLy;
	}

	public ResultSet getRsKhoChoXuLy() {
		return RsKhoChoXuLy;
	}

	public void setRsKhoChoXuLy(ResultSet rsKhoChoXuLy) {
		RsKhoChoXuLy = rsKhoChoXuLy;
	}

	public List<KhuVucKho> getListKhuVucKho() {
		return listKhuVucKho;
	}

	public void setListKhuVucKho(List<KhuVucKho> listKhuVucKho) {
		this.listKhuVucKho = listKhuVucKho;
	}

	public String getSopo_Id() {
		return this.sopoId;
	}

	public void setSopo_Id(String sopo_Id) {
		this.sopoId = sopo_Id;
	}

	public String getmuahang_fk() {
		return this.muahang_fk1;
	}

	public void setmuahang_fk(String muahang_fk) {
		this.muahang_fk1 = muahang_fk;
	}

	public String getTigia() {
		return this.tigia;
	}

	public void setTiia(String tigia) {
		this.tigia = tigia;
	}

	public ResultSet getHoadonNCCList() {
		return this.hoadonNCCList;
	}

	public void setHoadonNCCList(ResultSet hoadonnccList) {
		this.hoadonNCCList = hoadonnccList;
	}

	public void setLoaispId(String loaispId) {
		this.loaispId = loaispId;
	}

	public String getLoaispId() {
		return loaispId;
	}

	public String getIsNCCNK() {
		return isNCCNK;
	}

	public void setIsNCCNK(String isNCCNK) {
		this.isNCCNK = isNCCNK;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNgaynhanhang() {
		return this.ngaynhanhang;
	}

	public void setNgaynhanhang(String ngaynhanhang) {
		this.ngaynhanhang = ngaynhanhang;
	}

	public String getDvthId() {
		return this.dvthId;
	}

	public void setDvthId(String dvthid) {
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList() {
		return this.dvthRs;
	}

	public void setDvthList(ResultSet dvthlist) {
		this.dvthRs = dvthlist;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getSohoadon() {
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
	}

	public String getDonmuahangId() {
		return this.poId;
	}

	public void setDonmuahangId(String dmhid) {
		this.poId = dmhid;
	}

	public ResultSet getDmhList() {
		return this.poRs;
	}

	public void setDmhList(ResultSet dmhlist) {
		this.poRs = dmhlist;
	}

	public String getMahangmuaId() {
		return this.mahangmuaId;
	}

	public void setMahangmuaId(String mhmId) {
		this.mahangmuaId = mhmId;
	}

	public ResultSet getMahangmuaList() {
		return this.mahangmuaRs;
	}

	public void setMahangmuaList(ResultSet mhmlist) {
		this.mahangmuaRs = mhmlist;
	}

	public String getDiengiai() {
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getTongSoluongPO() {
		return this.soluongPO;
	}

	public void setTongSoluongPO(String tongslgPO) {
		this.soluongPO = tongslgPO;
	}

	public String getTongSoluongDN() {
		return this.soluongDaNhan;
	}

	public void setTongSoluongDN(String tongslgDN) {
		this.soluongDaNhan = tongslgDN;
	}

	public List<ISanpham> getSpList() {
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList) {
		this.spList = spList;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	// dành cho trường hợp chưa có hề có số mẻ và số lô người dùng nhập
	public void createRs() {

		init_Rs();

		// tạo ra các sản phẩm resultSet
		if (this.id.trim().length() <= 0) {
			CreateRsSanPham();
			// xét xem có phải là đơn gia công không, để quy đổi thành đơn vị
			// chuẩn.
			ConvertDHG();
		}

	}

	// nếu 2 đơn vị trùng nhau = true
	// nếu 2 đơn vị khác nhau = false
	private List<Double> CheckDonViDoLuong(List<String> sanpham_fk,
			List<String> dvdl_chuan, List<String> dvdl) {
		List<Double> tile = new ArrayList<Double>();
		try {
			String sql = "";
			// lấy đơn vị chuẩn
			if (this.idcongdoan.trim().length() > 0
					&& this.hdNccId.trim().length() > 0) {

				sql = " select distinct A.SANPHAM_FK, DM.DVDL_FK AS DVT, b.DVDL_FK "
						+ "\n from ERP_LENHSANXUAT_CONGDOAN_GIAY  a inner join ERP_SANPHAM b on  a.sanpham_fk = b.PK_SEQ  "
						+ "\n INNER JOIN ERP_DANHMUCVATTU DM ON DM.PK_SEQ=A.DANHMUCVATTU_FK where A.LENHSANXUAT_FK  ="
						+ this.hdNccId
						+ " and a.CONGDOAN_FK='"
						+ this.idcongdoan + "'";
				System.out.println("du lieu lay dvdl :" + sql);
				ResultSet rs = this.db.get(sql);
				if (rs != null) {
					while (rs.next()) {
						dvdl_chuan.add(rs.getString("DVDL_FK"));
						sanpham_fk.add(rs.getString("SANPHAM_FK"));
						dvdl.add(rs.getString("DVT"));
					}
					rs.close();
				}

				for (int i = 0; i < sanpham_fk.size(); i++) {

					if (dvdl.get(i).equals(dvdl_chuan.get(i))) {
						tile.add((double) 1.0f);
					} else {

						sql = " select isnull( CAST(SOLUONG2 AS float) / CAST(SOLUONG1 AS float),1) as tile from QUYCACH "
								+ " where DVDL1_FK = "
								+ dvdl_chuan.get(i)
								+ " and DVDL2_FK ="
								+ dvdl.get(i)
								+ " and SANPHAM_FK = " + sanpham_fk.get(i);
						System.out.println("Quy doi chuan : " + sql);
						rs = this.db.get(sql);
						// check truong hop, khong dc khai quy doi.
						boolean check = false;

						if (rs != null) {
							if (rs.next()) {
								tile.add(rs.getDouble("tile"));
								check = true;
							}
							rs.close();
						}
						if (check == false) {
							tile.add((double) 1.0f);
						}
					}
				}
			}

			return tile;
		} catch (Exception ex) {
			ex.printStackTrace();
			return tile;
		}
	}

	// thay đổi số lượng và đơn vị.
	private void ConvertDHG() {

		List<String> sanpham_fk = new ArrayList<String>();
		List<String> dvdl_chuan = new ArrayList<String>();
		List<String> dvdl = new ArrayList<String>();

		List<Double> tile = CheckDonViDoLuong(sanpham_fk, dvdl_chuan, dvdl);

		for (int i = 0; i < this.spList.size(); i++) {
			ISanpham sp = this.spList.get(i);

			for (int j = 0; j < sanpham_fk.size(); j++) {

				if (sanpham_fk.get(j).equals(sp.getId())) {
					if (tile.get(j) != 1) {
						xuLySoluongTungSp(sp, tile.get(j), dvdl_chuan.get(j), i);
						break;
					} else {
						sp.setDvdl(dvdl_chuan.get(j));
					}
				}
			}
		}

	}

	public static void main(String[] agrs) {
		double a = 25.000 / 0.001;
		System.out.println("a" + a);
	}

	// chỗ này xem xét từng sản phẩm và quy đổi qua đơn vị chuẩn theo tỉ lệ đã
	// tính được
	private void xuLySoluongTungSp(ISanpham sp, double tile, String dvdl_chuan,
			int index) {
		DecimalFormat formatter = new DecimalFormat("###,###.###");
		DecimalFormat formatterVND = new DecimalFormat("###,###,###.####");

		DecimalFormat formatter_9sole = new DecimalFormat("######.##########");

		tile = Double.parseDouble(formatter_9sole.format(tile));
		sp.setTiLeQuyDoiDv(tile);
		// tile = 1/tile;

		System.out.println("ti le new : " + tile);

		double soluong = Double.parseDouble(sp.getSoluongMaxNhan().replaceAll(
				",", ""))
				/ tile;
		sp.setSoluongMaxNhan(formatter.format(soluong));

		soluong = Double.parseDouble(sp.getSoluongDaNhan().replaceAll(",", ""))
				/ tile;
		sp.setSoluongDaNhan(formatter.format(soluong));

		soluong = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""))
				/ tile;
		sp.setSoluongdat(formatter.format(soluong));

		soluong = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""))
				/ tile;
		System.out.println("so luong nhan : " + sp.getSoluongnhan());
		sp.setSoluongnhan(formatter.format(soluong));

		double dongia = Double.parseDouble(sp.getDongia().replaceAll(",", ""))
				* tile;

		dongia = Math.round(dongia * 1000.0) / 1000.0;

		sp.setDongia(formatterVND.format(dongia));
		sp.setDongiaViet(formatterVND.format(dongia));
		// set dvdl
		sp.setDvdl(dvdl_chuan);

		List<ISpDetail> listct = sp.getSpDetail();

		for (int j = 0; j < listct.size(); j++) {
			ISpDetail spCon = sp.getSpDetail().get(j);
			soluong = Double
					.parseDouble(spCon.getSoluong().replaceAll(",", "")) / tile;
			spCon.setSoluong(formatter.format(soluong));

			soluong = Double
					.parseDouble(spCon.getSoluong().replaceAll(",", "")) / tile;
			spCon.setSoluongmax(formatter.format(soluong));

			spCon.setDongiaLo(formatterVND.format(dongia));
			listct.set(j, spCon);
		}
		sp.setSpDetail(listct);
		this.spList.set(index, sp);
	}

	// Dành cho trường hợp tạo ra số mẻ và số lô
	public void createRs1() {

		this.init_Rs();

	}

	private void init_Rs() {
		// TODO Auto-generated method stub
		String sql = "select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1' and congty_fk = "
				+ this.congtyId;
		// " and congty_fk = '" + this.congtyId + "' and pk_seq in " +
		// this.util.quyen_donvithuchien(this.userId);;

		ResultSet donvi = db
				.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1'");
		this.listDonvi.clear();
		if (donvi != null) {
			try {
				while (donvi.next()) {
					this.listDonvi.add((IDonvi) new Donvi(donvi
							.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		this.dvthRs = db.get(sql);

		this.ndnRs = db
				.get("select pk_seq, noidung from ERP_NOIDUNGNHANHANG where trangthai = 1 and loai = 2  ");

		this.ldnRs = db
				.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP "
						+ " where trangthai = '1' and loaixuat = '2'");

		String cd = "";

		if ((this.KhoNhanId == null || this.KhoNhanId.equals(""))
				&& this.hdNccId.length() > 0 && this.idcongdoan != null
				&& this.idcongdoan.length() > 0) {
			String query = " select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1' and PK_SEQ in "
					+ " (select khosanxuat_fk from ERP_LENHSANXUAT_congdoan_GIAY where congdoan_fk="
					+ this.idcongdoan
					+ "  and   lenhsanxuat_fk ="
					+ this.hdNccId + ")";
			try {
				ResultSet rskho = db.get(query);
				if (rskho.next()) {
					this.KhoNhanId = rskho.getString("PK_SEQ");
				}
				rskho.close();
			} catch (Exception er) {
			}
		}

		// Kiểm tra kho nhận có phải Kho ký gửi KH/Dự trữ KH không
		if (this.KhoNhanId.trim().length() > 0) {
			String command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN  where KHOTT_FK = "
					+ this.KhoNhanId;
			ResultSet rss = this.db.get(command);
			List<KhuVucKho> listK = new ArrayList<KhuVucKho>();
			try {
				if (rss != null) {
					while (rss.next()) {
						KhuVucKho temp = new KhuVucKho();
						temp.setId(rss.getString("PK_SEQ"));
						temp.setTen(rss.getString("TEN"));
						listK.add(temp);
					}
					rss.close();
				}
				this.listKhuVucKho = listK;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
		// lấy khu vực kho chờ xử lý
		if (this.khoChoXuLy.trim().length() > 0) {
			String command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN where KHOTT_FK = "
					+ this.khoChoXuLy;
			ResultSet rss = this.db.get(command);
			List<KhuVucKho> listK = new ArrayList<KhuVucKho>();
			try {
				if (rss != null) {
					while (rss.next()) {
						KhuVucKho temp = new KhuVucKho();
						temp.setId(rss.getString("PK_SEQ"));
						temp.setTen(rss.getString("TEN"));
						listK.add(temp);
					}
					rss.close();
				}
				this.listKhuVucKhoCXL = listK;
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}

		this.khachhangRs = db
				.get("select PK_SEQ, MA + ', ' +TEN AS TEN from erp_KHACHHANG where TRANGTHAI = '1'");

		// ---lấy danh sách lệnh sản xuất

		String query = " select   lsx.pk_seq,ISNULL(SOLENHSANXUAT,'') +' - '+ CAST(pk_seq AS NVARCHAR(18))  +', ' + ISNULL(DIENGIAI,'') AS  sohoadon   \n"
				+ " from ERP_LENHSANXUAT_GIAY   LSX    \n"

				+ " where 1=1 ";
		if (this.id.length() == 0) {
			query += " and  LSX.trangthai  in ('2','5') ";
		}
		/*
		 * + " and LSX.PK_SEQ NOT IN (" +
		 * " select nh.LENHSANXUAT_FK from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ = nhsp.NHANHANG_FK  "
		 * +
		 * " where nh.TRANGTHAI = 1 and nhsp.SOLUONGDAT = nhsp.SOLUONGNHAN and nh.LENHSANXUAT_FK is not null "
		 * + "		)";
		 */

		System.out.println(" ds lenh san xuat 1:" + query);

		this.hdNccRs = db.get(query);
		if (this.hdNccId == null) {
			this.hdNccId = "";
		}
		if (this.idcongdoan == null) {
			this.idcongdoan = "";
		}
		if (this.hdNccId.length() > 0) {
			// Cho chọn kho nhận: cố gắng được kho có chứa sản phẩm đó.
			// 15/06/2016
			/*
			 * query =
			 * " select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1' and PK_SEQ in "
			 * +
			 * " (select khosanxuat_fk from ERP_LENHSANXUAT_GIAY where pk_seq ="
			 * +this.hdNccId+") ";
			 */

			query = " select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1' and PK_SEQ in "
					+ " (select khosanxuat_fk from ERP_LENHSANXUAT_congdoan_GIAY where congdoan_fk="
					+ this.idcongdoan
					+ "  and   lenhsanxuat_fk ="
					+ this.hdNccId + ")";
			this.RsKhoNhan = db.get(query);

			this.RsKhoChoXuLy = db.get(query);
			query = "select PK_SEQ,DIENGIAI from ERP_CONGDOANSANXUAT_GIAY where PK_SEQ in "
					+ " (select CONGDOAN_FK from ERP_LENHSANXUAT_CONGDOAN_GIAY where lenhsanxuat_fk = "
					+ this.hdNccId + ")";
			// System.out.println("ds cong doan san xuat:"+query);
			this.congdoanRs = db.get(query);
		}

		query = " select PK_SEQ,TEN from ERP_KHOTT where   ISKHONHANTP_DAT =1  AND   congty_fk="
				+ this.congtyId;
		this.RsKhoNhanTpDat = db.get(query);
		if (KhonhanTpdat != null && KhonhanTpdat.length() > 0) {
			//

			if (util_kho.getLoaiKho(db, this.KhonhanTpdat).equals("10")) {
				this.KhoNhanCoDoituong = "1";
				query = "  select PK_SEQ,TEN_DT as TEN from ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK="
						+ this.hdNccId;
				this.DoituongnhanRs = db.get(query);

			} else {
				this.KhoNhanCoDoituong = "0";
			}
		} else {
			this.KhoNhanCoDoituong = "0";
		}

		if ((this.loaihanghoa != null && !this.loaihanghoa.equals(""))) {
			query = "select pk_seq, ma + ' - ' + ten as tensp from erp_sanpham where congty_fk = "
					+ this.congtyId;
			query += " and loaisanpham_fk in (select pk_seq from erp_loaisanpham where   loai ="
					+ this.loaisanpham + ")";

			System.out.println("ds phe pham / du pham:" + query);
			this.phepham_duphamRs = db.get(query);
		}
	}

	public void CreateRsSanPham() {
		String query1 = "";

		// ----- chọn hoá đơn thì load thông tin dơn hàng
		if (this.hdNccId != null && this.idcongdoan != null
				&& this.hdNccId.length() > 0 && this.idcongdoan.length() > 0) {

			if ((this.loaisanpham != null && !this.loaisanpham.equals(""))) {
				if (this.loaisanpham.equals("0")) {
					query1 = " SELECT  sp.loaisanpham_fk ,hdsp.LENHSANXUAT_FK as pk_Seq,  ''  MUAHANG_FK,hdsp.LENHSANXUAT_FK SOPO,    "
							+ " sp.PK_SEQ as SANPHAM_FK,   "
							+ " sp.MA as SPMA ,   "
							+ " sp.TEN    as spten,     "
							+ " '' as idmarquette,     "
							+ " isnull(sp.BATBUOCKIEMDINH,'0')  as kiemdinh,   "
							+ " isnull(dv.pk_seq,'0') as dvdlTen, 0 as dungsai ,  1 as tigiaquydoi,   100000 as tienteId,  isnull(sp.hansudung,0) as hansudung ,   "
							+ " 0  as dongia,                 "
							+ " ''  ngaynhan,  (hdsp.SOLUONG) as soluonghd,        "
							+ " isnull((select  sum(nhsp.SOLUONGNHAN *nhsp.TILEQUYDOI_DOLUONG)         "
							+ " from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK   "
							+ " where nh.TRANGTHAI not in (3,4) and    nh.lenhsanxuat_fk=hdsp.LENHSANXUAT_FK    "
							+ " and nhsp.SANPHAM_FK=hdsp.sanpham_fk    "
							+ " ),0) as soluongdanhan, '"
							+ this.KhoNhanId
							+ "' as  khonhan,  ''  khoten   "
							+ " FROM (select LENHSANXUAT_FK,SANPHAM_FK,SOLUONG from  ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK='"
							+ this.hdNccId
							+ "' and CONGDOAN_FK='"
							+ this.idcongdoan
							+ "' ) hdsp    "
							+ " inner join ERP_SANPHAM  sp on hdsp.SANPHAM_FK= sp.PK_SEQ    "
							+ " left join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK    "
							+ " WHERE hdsp.LENHSANXUAT_FK  =  " + hdNccId;
				} else if ((this.loaisanpham.equals("1")
						|| this.loaisanpham.equals("2")
						|| this.loaisanpham.equals("3") || this.loaisanpham
							.equals("4")) && this.idphepham_dupham.length() > 0) {
					query1 = " SELECT  sp.loaisanpham_fk ,hdsp.LENHSANXUAT_FK as pk_Seq,  ''  MUAHANG_FK,hdsp.LENHSANXUAT_FK SOPO,    "
							+ " sp.PK_SEQ as SANPHAM_FK,   "
							+ " sp.MA as SPMA ,   "
							+ " sp.TEN    as spten,     "
							+ " '' as idmarquette,     "
							+ " isnull(sp.BATBUOCKIEMDINH,'0')  as kiemdinh,   "
							+ " isnull(dv.pk_seq,'0') as dvdlTen, 0 as dungsai ,  1 as tigiaquydoi,   100000 as tienteId,  isnull(sp.hansudung,0) as hansudung ,   "
							+ " 0  as dongia,                 "
							+ " ''  ngaynhan,  (hdsp.SOLUONG) as soluonghd,        "
							+ " isnull((select  sum(nhsp.SOLUONGNHAN *nhsp.TILEQUYDOI_DOLUONG)         "
							+ " from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK   "
							+ " where nh.TRANGTHAI not in (3,4) and    nh.lenhsanxuat_fk=hdsp.LENHSANXUAT_FK    "
							+ " and nhsp.SANPHAM_FK=hdsp.sanpham_fk    "
							+ " ),0) as soluongdanhan, '"
							+ this.KhoNhanId
							+ "' as  khonhan,  ''  khoten   "
							+ " FROM ( select LENHSANXUAT_FK,"
							+ this.idphepham_dupham
							+ " as SANPHAM_FK,SOLUONG from  ERP_LENHSANXUAT_CONGDOAN_GIAY where LENHSANXUAT_FK='"
							+ this.hdNccId
							+ "' and CONGDOAN_FK='"
							+ this.idcongdoan
							+ "' )  hdsp    "
							+ " inner join ERP_SANPHAM  sp on hdsp.SANPHAM_FK= sp.PK_SEQ    "
							+ " left join DONVIDOLUONG dv on dv.PK_SEQ=sp.DVDL_FK    "
							+ " WHERE hdsp.LENHSANXUAT_FK  =  " + hdNccId;
				}

				System.out.println("ds sanpham :" + query1);
				if (query1.length() > 0) {
					ResultSet rs = db.get(query1);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					NumberFormat formatter = new DecimalFormat("#,###,###.###");
					NumberFormat formatter2 = new DecimalFormat("#,###,###.###");
					if (rs != null) {
						ISanpham sp = null;
						try {
							while (rs.next()) {
								sp = new Sanpham();

								sp.setMuahang_fk(rs.getString("MUAHANG_FK"));
								sp.setSoPO(rs.getString("SOPO"));
								sp.setMa(rs.getString("spma"));
								sp.setId(rs.getString("SANPHAM_FK"));
								sp.setDiengiai(rs.getString("spten"));
								// thêm idmarquette
								sp.setIdmarquette(rs.getString("idmarquette"));

								sp.setSoluongdat(formatter.format(rs
										.getDouble("soluonghd")));
								sp.setKhonhanId(rs.getString("khonhan"));
								System.out.println("khonhan ID "
										+ sp.getKhonhanId());
								sp.setDvdl(rs.getString("dvdlTen"));
								double soluongmaxnhan = rs
										.getDouble("soluonghd")
										- rs.getDouble("soluongdanhan");// <= 0
																		// ? 0 :
																		// rs.getDouble("soluonghd")
																		// -
																		// rs.getDouble("soluongdanhan");
								sp.setSoluongMaxNhan(formatter2
										.format(soluongmaxnhan));
								sp.setSoluongDaNhan(formatter2.format(rs
										.getDouble("soluongdanhan")));
								sp.setHansudung(rs.getString("hansudung"));
								sp.setNgaynhandukien(rs.getString("ngaynhan") == null ? ""
										: rs.getString("ngaynhan"));
								sp.setKhonhanTen(rs.getString("khoten"));
								sp.setDongia(formatter.format(rs
										.getDouble("dongia")));
								sp.setDongiaViet(formatter.format(rs
										.getDouble("dongia")));
								sp.setTigiaquydoi(rs.getString("tigiaquydoi"));
								sp.setTiente(rs.getString("tienteId"));
								sp.setSoluongnhan("");
								sp.setthanhtien("");
								sp.setloaisp(rs.getString("loaisanpham_fk"));
								// thêm trường kiểm định
								sp.setIsKiemDinh(rs.getString("kiemdinh"));

								if (this.id.trim().length() <= 0) {
									double soluongnhan = rs
											.getDouble("soluonghd")
											- rs.getDouble("soluongdanhan");// <=
																			// 0
																			// ?
																			// 0
																			// :
																			// rs.getDouble("soluonghd")
																			// -
																			// rs.getDouble("soluongdanhan");
									sp.setSoluongnhan(formatter2
											.format(soluongnhan));
									String command = "";
									if (this.loaisanpham.equals("0"))
										command = " SELECT SANPHAM_FK, SOLUONG,'' AS SOLO, '' NGAYSANXUAT, '' NGAYHETHAN, 0 AS DONGIA, "
												+ " A.SOLUONG - ISNULL((SELECT SUM(CT.SOLUONG) FROM ERP_NHANHANG_SP_CHITIET CT "
												+ " INNER JOIN ERP_NHANHANG NH ON CT.NHANHANG_FK = NH.PK_SEQ  "
												+ " INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NHSP.NHANHANG_FK = NH.PK_SEQ "
												+ " WHERE NH.TRANGTHAI <> 3 AND CT.SANPHAM_FK = A.SANPHAM_FK "
												+ " AND NH.lenhsanxuat_fk = A.LENHSANXUAT_FK   "
												+ " GROUP BY CT.SANPHAM_FK ), 0) AS SOLUONGMAX, "
												+ " '' AS NGAYSANXUAT, ''   "
												+ " FROM  ERP_LENHSANXUAT_CONGDOAN_GIAY  A "
												+ " WHERE  A.LENHSANXUAT_FK= "
												+ hdNccId
												+ " and A.CONGDOAN_FK='"
												+ this.idcongdoan + "'";
									else
										command = " SELECT SANPHAM_FK, SOLUONG,'' AS SOLO, '' NGAYSANXUAT, '' NGAYHETHAN, 0 AS DONGIA, "
												+ " 0 AS SOLUONGMAX "
												+ " FROM  ERP_LENHSANXUAT_CONGDOAN_GIAY  A "
												+ " WHERE  A.LENHSANXUAT_FK= "
												+ hdNccId
												+ " and A.CONGDOAN_FK='"
												+ this.idcongdoan + "'";
									;
									System.out
											.println("Khoi tao san pham con: "
													+ command);
									ResultSet spConRs = db.get(command);

									List<ISpDetail> spConList = new ArrayList<ISpDetail>();
									ISpDetail spCon = null;
									if (spConRs != null) {
										while (spConRs.next()) {
											String spConMa = spConRs
													.getString("sanpham_fk");
											String solo = spConRs
													.getString("solo");
											String soluong = formatter
													.format(spConRs
															.getDouble("soluongmax"));

											String ngaysanxuat = spConRs
													.getString("ngaysanxuat");
											String ngayhethan = spConRs
													.getString("ngayhethan");

											spCon = new SpDetail(spConMa, solo,
													"", soluong, ngaysanxuat,
													ngayhethan);
											spCon.setSoluongmax(formatter.format(spConRs
													.getDouble("soluongmax")));
											spCon.setDongiaLo(formatter.format(spConRs
													.getDouble("dongia")));
											spConList.add(spCon);
										}
										spConRs.close();
									}
									sp.setSpDetail(spConList);

								}
								spList.add(sp);
							}
							rs.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					this.spList = spList;
					// LocLoaiSanPham();
					// l
				}
			}
		}

	}

	public void init() {

		String query = "";

		query = " SELECT isnull(DOITUONGNHAN_FK,0) as  DOITUONGNHAN_FK ,KHONHANTP_DAT_FK , ISNULL(A.ISTUDONG,0) AS  ISTUDONG   ,A.PK_SEQ AS NHID, A.TRANGTHAI, A.LOAIHANGHOA_FK, A.NOIDUNGNHAN_FK, A.SOHOADON, A.NGAYNHAN, A.NGAYCHOT, A.DIENGIAI, B.PK_SEQ AS DVTHID, B.TEN AS DVTHTEN, "
				+ "\n A.LENHSANXUAT_FK AS POID, A.TRANGTHAI, A.NOIDUNGNHAP_FK , A.NCC_KH_FK, '' AS LOAIMH, A.KHONHAN_FK,A.KHOCHOXULY_FK, A.KHACHHANGKYGUI_FK, "
				+ "\n 'TN'  AS NGUONGOCHH, LENHSANXUAT_FK  AS HDNCC_FK  "
				+ "\n ,  A.MUAHANG_FK AS MUAHANG_FK, ISNULL(A.TIGIA, '1') AS TIGIA, '' AS SOPO,A.CONGDOAN_FK, A.FILENAME "
				+ "\n FROM ERP_NHANHANG A   "
				+ "\n INNER JOIN ERP_DONVITHUCHIEN B ON A.DONVITHUCHIEN_FK = B.PK_SEQ "
				+ " WHERE A.PK_SEQ = '" + this.id + "' ";

		System.out.println("Init : " + query);

		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.ngaychot = rs.getString("ngaychot");
					this.sohoadon = rs.getString("sohoadon");
					this.dvthId = rs.getString("dvthId");
					this.poId = rs.getString("PoId");
					this.diengiai = rs.getString("diengiai");
					this.ndnId = rs.getString("noidungnhan_fk");
					this.loaihanghoa = rs.getString("loaihanghoa_fk");
					System.out
							.println(" loai hang hoa   : " + this.loaihanghoa);
					this.ldnId = rs.getString("NoiDungNhap_fk");
					this.trangthai = rs.getString("trangthai");
					this.nccId = rs.getString("NCC_KH_FK") == null ? "" : rs
							.getString("NCC_KH_FK");
					this.loaimh = rs.getString("loaimh");
					this.KhonhanTpdat = rs.getString("KHONHANTP_DAT_FK");
					this.idcongdoan = rs.getString("CONGDOAN_FK");
					if (this.loaimh.trim().equals("2")) {
						this.ldnId = "100000";
					}
					this.isPONK = rs.getString("NGUONGOCHH").equals("TN") ? "0"
							: "1";
					this.hdNccId = rs.getString("hdNCC_fk") == null ? "" : rs
							.getString("hdNCC_fk");

					this.isTudong = rs.getString("ISTUDONG");

					this.muahang_fk1 = rs.getString("muahang_fk") == null ? ""
							: rs.getString("muahang_fk");
					this.tigia = rs.getString("tigia");
					this.sopoId = rs.getString("sopo");

					if (this.KhoNhanId.trim().length() <= 0) {
						this.KhoNhanId = rs.getString("KHONHAN_FK") == null ? ""
								: rs.getString("KHONHAN_FK");
						this.khachhangId = rs.getString("KHACHHANGKYGUI_FK") == null ? ""
								: rs.getString("KHACHHANGKYGUI_FK");
						this.khoChoXuLy = rs.getString("KHOCHOXULY_FK") == null ? ""
								: rs.getString("KHOCHOXULY_FK");
					}
					this.filename = rs.getString("FILENAME") == null ? "" : rs
							.getString("FILENAME");
					this.IdDoituongkhonhan = rs.getString("DOITUONGNHAN_FK");

				}
				rs.close();
			} catch (Exception e) {
				System.out.println("1.Exception: " + e.getMessage());
			}
		}

		query = "select lsp.loai,sp.pk_seq from erp_nhanhang nh inner join erp_nhanhang_sanpham nhsp on nh.pk_seq = nhsp.nhanhang_fk"
				+ " inner join erp_sanpham sp on nhsp.sanpham_fk =  sp.pk_seq inner join erp_loaisanpham lsp on sp.loaisanpham_fk = lsp.pk_seq "
				+ "where nh.pk_seq =" + this.id;
		ResultSet sp = db.get(query);
		if (sp != null) {
			try {
				while (sp.next()) {
					this.loaisanpham = sp.getString("loai");
					// if(this.loaisanpham == null)
					// this.loaisanpham = "3";
					this.idphepham_dupham = sp.getString("pk_seq");
				}
			} catch (Exception e) {
				System.out.println("1.Exception: " + e.getMessage());
			}
		}
		this.initSanPham();
		this.is_createRs = "1";

		this.createRs1();
	}

	private void initSanPhamXK() {
		NumberFormat formatter = new DecimalFormat("#,###,###.##");

		String query = "select isnull(mh.PK_SEQ, 0) as pk_seq , isnull(mh.SOPO, '') as SOPO , case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as spId, "
				+ "case nh.loaihanghoa_fk when 0 then isnull(sp.MA, isnull(sp.ma, ''))  when 1 then tscd.Ma when 3 then ccdc.MA else ncp.Ten end AS spMa,  "
				+ " CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten1, sp.Ten)   "
				+ "     ELSE a.DienGiai END AS spTen, "
				+ " a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, "
				+ "isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, "
				+ "khott.pk_seq as khottId, khott.ma + ', ' + khott.ten as khottTen, a.SPKIEMDINH     "
				+ "from ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   "
				+ "LEFT join SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   "
				+ "LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq        "
				+ "LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  "
				+ "LEFT JOIN erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  "
				+ "LEFT JOIN ERP_KHOTT khott on a.khonhan = khott.pk_seq "
				+ "LEFT JOIN ERP_MUAHANG mh on mh.PK_SEQ = a.muahang_fk " +
				// "inner join ERP_THUENHAPKHAU t on nh.SoToKhai_fk = t.PK_SEQ "
				// +
				// "inner join ERP_THUENHAPKHAU_HOADONNCC th on t.PK_SEQ =
				// th.THUENHAPKHAU_FK " +
				// "inner join ERP_HOADONNCC_DONMUAHANG hd on th.HOADONNCC_FK =
				// hd.HOADONNCC_FK and a.SANPHAM_FK = hd.SANPHAM_FK " +
				"where a.NHANHANG_FK = '" + this.id + "'";

		System.out.println("[ErpNhanhang_Giay.initSanPham] query = " + query);
		ResultSet rsSp = db.get(query);

		if (rsSp != null) {
			NumberFormat formater = new DecimalFormat("#,###,###.###");

			try {
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next()) {
					String muahangpkseq = rsSp.getString("PK_SEQ");
					String soPo = rsSp.getString("SOPO");

					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");

					String soluongdat = formatter.format(rsSp
							.getDouble("SOLUONGDAT"));
					String soluongnhan = formatter.format(rsSp
							.getDouble("SOLUONGNHAN"));

					String hansudung = rsSp.getString("HANSUDUNG");
					String dungsai = rsSp.getString("DUNGSAI");
					String dvdl = rsSp.getString("DonVi");
					String dongia = rsSp.getString("DONGIA");

					String thanhtien = formatter.format(rsSp
							.getDouble("SOLUONGNHAN")
							* rsSp.getDouble("DONGIA"));

					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan,
							hansudung, ngaynhandk, soluongdat, dvdl);
					/*
					 * if (soluongdat != "" && soluongnhan != "")
					 * sanpham.setCOnlai
					 * (Float.toString(Float.parseFloat(soluongdat
					 * .replaceAll(",", "")) -
					 * Float.parseFloat(soluongnhan.replaceAll(",", ""))));
					 */

					if (this.loaihanghoa.equals("0")) {
						String comand = "";
						if (this.isNCCNK.equals("0")) {
							// them sothung trong cau select
							comand = " select sanpham_fk, solo,sothung, soluong, ngaysanxuat, ngayhethan from ERP_NHANHANG_SP_CHITIET "
									+ " where NGAYNHANDUKIEN='"
									+ ngaynhandk
									+ "' AND nhanhang_fk = '"
									+ this.id
									+ "' and sanpham_fk = '"
									+ spId
									+ "'"
									+ " order by lannhan asc ";
						} else {
							// them sothung trong cau select
							comand = " select sanpham_fk, solo,sothung, soluong, ngaysanxuat, ngayhethan from ERP_NHANHANG_SP_CHITIET "
									+ " where MUAHANG_FK = '"
									+ muahangpkseq
									+ "' and   NGAYNHANDUKIEN='"
									+ ngaynhandk
									+ "' AND nhanhang_fk = '"
									+ this.id
									+ "' and sanpham_fk = '"
									+ spId
									+ "'"
									+ " order by lannhan asc ";
						}

						System.out.println("Khoi tao san pham con: " + comand);
						ResultSet spConRs = db.get(comand);

						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						ISpDetail spCon = null;
						if (spConRs != null) {
							while (spConRs.next()) {
								String spConMa = spConRs
										.getString("sanpham_fk");
								String solo = spConRs.getString("solo");
								// them sothung trong table
								// ERP_NHANHANG_SP_CHITIET
								String sothung = formatter.format(spConRs
										.getInt("sothung"));

								String soluong = formatter.format(spConRs
										.getDouble("soluong"));
								String ngaysanxuat = spConRs
										.getString("ngaysanxuat");
								String ngayhethan = spConRs
										.getString("ngayhethan");
								// them so thung trong constructor
								spCon = new SpDetail(spConMa, solo, sothung,
										soluong, ngaysanxuat, ngayhethan);
								spConList.add(spCon);
							}
							spConRs.close();
						}

						sanpham.setSpDetail(spConList);
					}

					sanpham.setMuahang_fk(muahangpkseq);
					sanpham.setSoPO(soPo);

					sanpham.setDungsai(dungsai);
					sanpham.setDongia(dongia);
					sanpham.setTiente(rsSp.getString("TienTe_Fk"));
					sanpham.setTigiaquydoi(rsSp.getString("TyGiaQuyDoi"));
					sanpham.setDongiaViet(rsSp.getString("DonGiaViet"));

					sanpham.setKhonhanId(rsSp.getString("khottId") == null ? ""
							: rsSp.getString("khottId"));
					sanpham.setKhonhanTen(rsSp.getString("khottTen") == null ? ""
							: rsSp.getString("khottTen"));

					double soluongDat = rsSp.getDouble("SOLUONGDAT");

					// Tinh so luong MAX + dung sai co the nhan
					double soluongPONhan = 0;
					double soluongMax = soluongDat;

					if (muahangpkseq.trim().length() > 1) {
						query = "select sum(b.SOLUONGNHAN)  as soluongDaNhan  "
								+ "from ERP_NHANHANG a "
								+ "inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  "
								+ "inner join ERP_THUENHAPKHAU t on a.SoToKhai_fk = t.PK_SEQ "
								+ "inner join ERP_THUENHAPKHAU_HOADONNCC th on t.PK_SEQ = th.THUENHAPKHAU_FK "
								+ "inner join ERP_HOADONNCC_DONMUAHANG hd on th.HOADONNCC_FK = hd.HOADONNCC_FK and b.SANPHAM_FK = hd.SANPHAM_FK "
								+ "where hd.MUAHANG_FK = '" + muahangpkseq
								+ "' and NGAYNHANDUKIEN = '" + ngaynhandk
								+ "and b.DONGIA = " + dongia
								+ "' and b.SANPHAM_FK = '" + spId
								+ "' and a.TRANGTHAI not in (3, 4) ";

						if (this.id.trim().length() > 0) {
							query += " and a.pk_seq != '" + this.id + "' ";
						}

						ResultSet rsNhanTD = db.get(query);
						if (rsNhanTD != null) {
							if (rsNhanTD.next()) {
								// double soluongPODat =
								// rsNhanTD.getDouble("soluongDat");
								double soluongPODat = soluongDat;

								soluongPONhan = rsNhanTD
										.getDouble("soluongDaNhan");
								soluongMax = (soluongPODat + (soluongPODat
										* Double.parseDouble(dungsai
												.replaceAll(",", "")) / 100))
										- soluongPONhan;
							}
							rsNhanTD.close();
						}
					}

					sanpham.setSoluongDaNhan(formater.format(soluongPONhan));
					sanpham.setSoluongMaxNhan(Double.toString(soluongMax));

					sanpham.setthanhtien(thanhtien);
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			} catch (Exception e) {
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();

			}
		}
	}

	private void initSanPham() {

		NumberFormat formatter = new DecimalFormat("#,###,###.###");

		String query = " ";

		query = "SELECT   sp.loaisanpham_fk ,nh.lenhsanxuat_fk, a.sott ,  case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as spId,   "
				+ " case nh.loaihanghoa_fk when 0 then isnull(sp.MA, isnull(sp.ma, ''))  when 1 then tscd.Ma when 3 then ccdc.MA else ncp.Ten end AS spMa,    "
				+ " CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten1 +isnull(m.MA,''), sp.Ten +isnull(m.MA,''))          "
				+ " ELSE a.DienGiai END AS spTen, '' as MUAHANG_FK, '' as SOPO , m.PK_SEQ as idmarquette,    "
				+ " a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung,   "
				+ " isnull(a.DonVi, 0) as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, a.TILEQUYDOI_DOLUONG,   "
				+ " khott.pk_seq as khottId,   khott.ten as khottTen,a.SPKIEMDINH        "
				+ " FROM ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq     "
				+ " LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ     "
				+ " LEFT join marquette m on m.PK_SEQ = a. idmarquette and m.SANPHAM_FK = sp.PK_SEQ     "
				+ " LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq          "
				+ " LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq    "
				+ " LEFT JOIN erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq    "
				+ " LEFT JOIN KHO khott on a.khonhan = khott.pk_seq      "
				+ " WHERE a.NHANHANG_FK = '" + this.id + "' order by a.sott  ";

		System.out.println("get du lieu ; " + query);
		ResultSet rsSp = db.get(query);

		if (rsSp != null) {
			NumberFormat formater = new DecimalFormat("#,###,###.###");

			try {
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next()) {
					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");

					String soluongdat = formatter.format(rsSp
							.getDouble("SOLUONGDAT"));
					String soluongnhan = formatter.format(rsSp
							.getDouble("SOLUONGNHAN"));

					String hansudung = rsSp.getString("HANSUDUNG");
					String dvdl = rsSp.getString("DonVi");
					String dongia = formatter.format(rsSp.getDouble("DONGIA"));

					String muahang_fk = rsSp.getString("MUAHANG_FK");
					String soPO = rsSp.getString("SOPO");
					double tilequydoiDV = rsSp.getDouble("TILEQUYDOI_DOLUONG");

					String thanhtien = formatter.format(rsSp
							.getDouble("SOLUONGNHAN")
							* rsSp.getDouble("DONGIA"));

					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan,
							hansudung, ngaynhandk, soluongdat, dvdl);
					if (soluongdat != "" && soluongnhan != "")
						sanpham.setCOnlai(Double.toString(Double
								.parseDouble(soluongdat.replaceAll(",", ""))
								- Double.parseDouble(soluongnhan.replaceAll(
										",", ""))));

					sanpham.setTiLeQuyDoiDv(tilequydoiDV);

					if (this.loaihanghoa.equals("0")) {
					}

					sanpham.setMuahang_fk(muahang_fk);
					sanpham.setSoPO(soPO);
					sanpham.setDongia(dongia);
					sanpham.setTiente(rsSp.getString("TienTe_Fk"));
					sanpham.setloaisp(rsSp.getString("loaisanpham_fk"));

					sanpham.setTigiaquydoi(rsSp.getString("TyGiaQuyDoi"));
					sanpham.setDongiaViet(rsSp.getString("DonGiaViet"));
					String khottId = rsSp.getString("khottId") == null ? ""
							: rsSp.getString("khottId");
					sanpham.setKhonhanId(khottId);
					sanpham.setKhonhanTen(rsSp.getString("khottTen") == null ? ""
							: rsSp.getString("khottTen"));

					sanpham.setIdmarquette(rsSp.getString("idmarquette"));
					sanpham.setIsKiemDinh(rsSp.getString("SPKIEMDINH"));

					double soluongDat = rsSp.getDouble("SOLUONGDAT");

					// Tinh so luong MAX + dung sai co the nhan
					double soluongPONhan = 0;
					double soluongMax = soluongDat;
					String lenhsanxuat_fk = rsSp.getString("lenhsanxuat_fk");

					if (this.poId.trim().length() > 0) {
						String sql = " select sum(b.SOLUONGNHAN)  as soluongDaNhan  "
								+ " from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  "
								+ " where a.lenhsanxuat_fk = '"
								+ lenhsanxuat_fk
								+ "' and NGAYNHANDUKIEN = '"
								+ ngaynhandk
								+ "' and SANPHAM_FK = '"
								+ spId
								+ "' AND DONGIA="
								+ dongia.replaceAll(",", "")
								+ " "
								+ " and a.TRANGTHAI not in (3, 4) and a.hdncc_fk = "
								+ this.hdNccId + " ";
						query = sql;
						if (this.id.trim().length() > 0) {
							query += " and a.pk_seq != '" + this.id + "' ";
						}
						System.out.println("lay so luong dat: " + query);
						ResultSet rsNhanTD = db.get(query);
						if (rsNhanTD != null) {
							if (rsNhanTD.next()) {
								double soluongPODat = soluongDat;

								soluongPONhan = rsNhanTD
										.getDouble("soluongDaNhan");
								soluongMax = (soluongPODat) - soluongPONhan;
							}
							rsNhanTD.close();
						}

						sql += " and a.pk_seq < " + this.id;
						rsNhanTD = db.get(sql);
						if (rsNhanTD != null) {
							if (rsNhanTD.next()) {
								soluongPONhan = rsNhanTD
										.getDouble("soluongDaNhan");
							}
							rsNhanTD.close();
						}
					}

					// Lấy số lô bên này qua
					List<ISpDetail> spConList = getListChiTietLo(muahang_fk,
							spId, dongia.replaceAll(",", ""), tilequydoiDV);
					sanpham.setSpDetail(spConList);

					// Lấy số mẻ từ bảng NHẬN HÀNG ra
					List<DetailMeSp> listMeSp = getListChiTietLoMe(muahang_fk,
							spId, dongia.replaceAll(",", ""));
					sanpham.setListDetailMeSp(listMeSp);

					sanpham.setSoluongDaNhan(formater.format(soluongPONhan));
					sanpham.setSoluongMaxNhan(Double.toString(soluongMax));
					sanpham.setthanhtien(thanhtien);

					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;

				// xử lý hiện theo đúng loại, kiểm định thì hiện ở kho biệt trữ,
				// không kiểm định thì hiện ở kho tồn trữ.
				// LocLoaiSanPham();

			} catch (Exception e) {
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();

			}
		}
	}

	// xét xem kho nào có trong list danh sách kho khác không?
	private boolean ExistKho(List<String> dsKho, String khoId) {
		for (int i = 0; i < dsKho.size(); i++) {
			if (dsKho.get(i).equals(khoId)) {
				return true;
			}
		}
		return false;
	}

	// xét xem người quản lý kho được quản lý :
	// 0: tồn trữ
	// 1: biệt trữ
	// 2: cả hai
	private int QuyenKho(String khoBietTruId, String khoTonTruId) {
		try {
			int quyenkho = 0;
			// lấy kho mà người dùng được quyền xem
			String sql = " select khott_fk from NHANVIEN_KHOTT where nhanvien_fk ="
					+ this.userId
					+ " "
					+ " union select kho_fk from NHANVIEN_KHO where nhanvien_fk ="
					+ this.userId;

			ResultSet rs = this.db.get(sql);
			List<String> dsKho = new ArrayList<String>();
			if (rs != null) {
				while (rs.next()) {
					dsKho.add(rs.getString("khott_fk"));
				}
			}
			boolean check = false;
			// nếu kho biệt trữ và kho tồn trữ là 1.
			if (khoBietTruId.equals(khoTonTruId)) {
				check = ExistKho(dsKho, khoTonTruId);
				// nếu ds kho người quản lý đều thỏa biệt trữ và tồn trữ. 2 kho
				// là 1 thì hiện hết ds sp
				if (check == true) {
					return 2;
				}

			} else { // nếu chúng khác nhau.

				if (ExistKho(dsKho, khoBietTruId) == true
						&& ExistKho(dsKho, khoBietTruId) == true) {
					return 2;
				}
				// TH1: kho biệt trữ.
				if (ExistKho(dsKho, khoBietTruId) == true) {
					return 0;
				}
				// TH2: kho tồn trữ.
				if (ExistKho(dsKho, khoTonTruId) == true) {
					return 1;
				}
			}

			return quyenkho;
		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	// xử lý loại bỏ list
	private void LocLoaiSanPham() {
		try {
			String sql = "select A.khosanxuat_fk from  ERP_LENHSANXUAT_GIAY A where PK_Seq ="
					+ this.hdNccId;
			ResultSet rs = this.db.get(sql);

			String khosanxuat_fk = "";
			if (rs != null) {
				if (rs.next()) {
					khosanxuat_fk = rs.getString("khosanxuat_fk");

				}
				rs.close();
			}

			// trường hợp 1: khoBietTru và tồn trữ đều được người này quản lý.
			int quyenKho = QuyenKho(khosanxuat_fk, khosanxuat_fk);

			if (quyenKho == 2) {
				// giữ nguyên danh sách, kho này được nhận cả 2 loại.
			} else if (quyenKho == 1) {
				// loại bỏ sản phẩm không kiểm định

				for (int i = 0; i < this.spList.size(); i++) {
					if (this.spList.get(i).getIsKiemDinh().equals("1")) {
						this.spList.remove(i);
						// do xóa 1 phần tử ra khỏi list, thì size của list sẽ
						// bị giảm lại -1, nên phải xét tiếp phần tử bị dồn lại
						i = i - 1;
					}
				}

			} else if (quyenKho == 0) {
				// loại bỏ sản phẩm có kiểm định
				for (int i = 0; i < this.spList.size(); i++) {
					if (this.spList.get(i).getIsKiemDinh().equals("0")) {
						this.spList.remove(i);
						// do xóa 1 phần tử ra khỏi list, thì size của list sẽ
						// bị giảm lại -1, nên phải xét tiếp phần tử bị dồn lại
						i = i - 1;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	// cái này dùng để lấy số lô.
	// 05/06/2016
	private List<ISpDetail> getListChiTietLo(String muahang_fk,
			String SanPhamId, String dongia, double TILEQUYDOI_DOLUONG)
			throws SQLException {

		DecimalFormat formatter = new DecimalFormat("###,###,###.###");

		DecimalFormat formatter_VND = new DecimalFormat("#########");

		double giachuaquydoi = Double
				.parseDouble(formatter_VND.format((Double.parseDouble(dongia
						.replaceAll(",", "")) / TILEQUYDOI_DOLUONG)));

		/*
		 * String command =
		 * " select d.SANPHAM_FK, d.SOLUONG,d.SOLO, d.NGAYSANXUAT, d.NGAYHETHAN, d.DONGIA, "
		 * +
		 * " d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct "
		 * + " inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ " +
		 * " where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO  "
		 * +
		 * " and nh.hdNCC_fk = d.HOADONNCC_FK and round(ct.GIATHEOLO,4) = "+dongia
		 * +" group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, " +
		 * " isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct " +
		 * " inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ " +
		 * " where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO  "
		 * + " and nh.hdNCC_fk = d.HOADONNCC_FK " + "  and ct.NHANHANG_FK = "+
		 * this.id +"and round(ct.GIATHEOLO,4)  = "+dongia+ " " +
		 * " group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongdanhan, " +
		 * " d.NGAYSANXUAT as ngaysanxuat, d.NGAYHETHAN, " +
		 * " isnull((select COUNT(*) from ERP_NHANHANG_SP_CHITIET a " +
		 * " where SANPHAM_FK = "
		 * +SanPhamId+" and NHANHANG_FK = "+this.id+" and MUAHANG_FK = "
		 * +muahang_fk+
		 * " and a.SOLO = d.SOLO and round( a.GIATHEOLO,4) = "+dongia
		 * +"),0) as soluongthung" + " from  ERP_HOADONNCC_DONMUAHANG d  " +
		 * " where  d.hoadonncc_fk = "
		 * +this.hdNccId+" and d.sanpham_fk = "+SanPhamId +
		 * "and d.soluong <> 0 and d.MUAHANG_FK = "+ muahang_fk +
		 * " and ROUND (d.DONGIA,0) ="+ giachuaquydoi;
		 */
		String command = "SELECT D.SANPHAM_FK, D.SOLUONG,D.SOLO, D.NGAYSANXUAT, D.NGAYHETHAN, D.GIATHEOLO  as DONGIA,  D.SOLUONG -   "
				+ " ISNULL((SELECT SUM(CT.SOLUONG) FROM ERP_NHANHANG_SP_CHITIET CT   "
				+ " INNER JOIN ERP_NHANHANG NH ON CT.NHANHANG_FK = NH.PK_SEQ    "
				+ " WHERE NH.TRANGTHAI <> 3 AND CT.SANPHAM_FK = D.SANPHAM_FK   AND NH.LENHSANXUAT_FK=  D.LENHSANXUAT_FK  "
				+ " ), 0) AS SOLUONGMAX,    "
				+ " ISNULL((SELECT SUM(CT.SOLUONG)  "
				+ " FROM ERP_NHANHANG_SP_CHITIET CT  INNER JOIN ERP_NHANHANG NH ON CT.NHANHANG_FK = NH.PK_SEQ    "
				+ " WHERE NH.TRANGTHAI <> 3  "
				+ " AND CT.SANPHAM_FK = D.SANPHAM_FK  AND NH.PK_SEQ=  D.NHANHANG_FK  "
				+ " ), 0) AS SOLUONGDANHAN,    "
				+ " D.NGAYSANXUAT AS NGAYSANXUAT, D.NGAYHETHAN,    "
				+ " D.SOTHUNG FROM     "
				+ " (		  "
				+ "   "
				+ " 	SELECT NH.LENHSANXUAT_FK, NHANHANG_FK,SANPHAM_FK,SOLO ,NHSP.NGAYHETHAN,NHSP.NGAYNHANDUKIEN,NHSP.NGAYSANXUAT   "
				+ " 	,NHSP.GIATHEOLO ,SUM(SOLUONG) AS SOLUONG,COUNT(NHSP.SANPHAM_FK) AS SOTHUNG   "
				+ " 	FROM ERP_NHANHANG_SP_CHITIET  NHSP INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ= NHSP.NHANHANG_FK  "
				+ " 	 WHERE NHSP.NHANHANG_FK ="
				+ this.id
				+ "    "
				+ " 	GROUP BY   NH.LENHSANXUAT_FK, NHANHANG_FK,SANPHAM_FK,SOLO ,NHSP.NGAYHETHAN,NHSP.NGAYNHANDUKIEN,NHSP.NGAYSANXUAT ,NHSP.GIATHEOLO  "
				+ " 	  "
				+ " )  D   WHERE  D.NHANHANG_FK = "
				+ this.id
				+ "   "
				+ " AND D.SANPHAM_FK = " + SanPhamId + " AND D.SOLUONG <> 0 ";

		System.out.println("Khoi tao san pham con: " + command);
		ResultSet spConRs = db.get(command);

		List<ISpDetail> spConList = new ArrayList<ISpDetail>();
		ISpDetail spCon = null;
		if (spConRs != null) {
			while (spConRs.next()) {
				String spConMa = spConRs.getString("sanpham_fk");
				String solo = spConRs.getString("solo");
				String soluong = formatter.format(spConRs
						.getDouble("soluongmax"));

				if (this.id.trim().length() > 0) {
					soluong = formatter.format(spConRs
							.getDouble("soluongdanhan"));
				}
				String ngaysanxuat = spConRs.getString("ngaysanxuat");
				String ngayhethan = spConRs.getString("ngayhethan");
				String sothung = spConRs.getString("sothung");

				spCon = new SpDetail(spConMa, solo, sothung, soluong,
						ngaysanxuat, ngayhethan);
				spCon.setSoluongmax(formatter.format(spConRs
						.getDouble("soluongmax")));
				spCon.setDongiaLo(formatter.format(spConRs.getDouble("dongia")));
				spConList.add(spCon);
			}
			spConRs.close();
		}
		return spConList;
	}

	// Tính lại số lượng nhân thực sự

	// Hàm dùng để lấy chi tiết số lô theo mẻ
	private List<DetailMeSp> getListChiTietLoMe(String muahang_fk,
			String SanPhamId, String dongia) {

		DecimalFormat formatter = new DecimalFormat("###,###,###.###");
		List<DetailMeSp> listDetailMeSp = new ArrayList<DetailMeSp>();
		try {

			String comand = " select a.MATHUNG, a.MAME, a.BIN_FK, a.SOLUONG, a.SOLO,a.NGAYHETHAN, a.NGAYSANXUAT "
					+ " from ERP_NHANHANG_SP_CHITIET a where SANPHAM_FK = "
					+ SanPhamId
					+ " and NHANHANG_FK = "
					+ this.id
					+ "  order by SOLO, cast(MAME as float), cast(MATHUNG as float) ";

			System.out.println("Khoi tao san pham con: " + comand);
			ResultSet rs = db.get(comand);

			if (rs != null) {
				while (rs.next()) {
					DetailMeSp spCon = new DetailMeSp();
					spCon.setMaThung(rs.getString("MATHUNG"));
					spCon.setMe(rs.getString("MAME"));
					spCon.setKhuVuc(rs.getString("BIN_FK"));
					spCon.setSoLuong(rs.getString("SOLUONG"));
					spCon.setSoLo(rs.getString("SOLO"));
					spCon.setNgayHetHan(rs.getString("NGAYHETHAN"));
					spCon.setNgaySanXuat(rs.getString("NGAYSANXUAT"));
					listDetailMeSp.add(spCon);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listDetailMeSp;
	}

	public void initPdf(String spId) {
		String query = " select isnull(c.LOAISANPHAM_FK,0) as LOAISANPHAM_FK, a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYTAO, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK, "
				+ " 	case when a.noidungnhan_fk = 100000 then  SOPO else d.Prefix + cast(d.PK_SEQ as varchar(10)) end as sochungtu,  "
				+ "   case when a.noidungnhan_fk = 100000 then ncc.TEN else kh.TEN end as donviban "
				+ " from erp_nhanhang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ "
				+ " left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ "
				+ " left join DonTraHang d on a.TRAHANG_FK = d.PK_SEQ "
				+ " left join Erp_Nhacungcap ncc on ncc.PK_SEQ=c.NHACUNGCAP_FK "
				+ " left join ERP_KHACHHANG kh on kh.PK_SEQ=d.KHACHHANG_FK "
				+ " where a.pk_seq = '"
				+ this.id
				+ "' and b.pk_seq in "
				+ this.util.quyen_donvithuchien(this.userId);

		System.out.println("[ErpNhanhang_Giay.initPdf] Khoi tao nhan hang: "
				+ query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.loaispId = rs.getString("LOAISANPHAM_FK");
					this.ngaychot = rs.getString("ngaytao"); // NGAY TAO
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.dvthId = rs.getString("donviban");
					this.poId = rs.getString("sochungtu");

					this.diengiai = rs.getString("diengiai");
					this.ndnId = rs.getString("noidungnhan_fk");
					this.sohoadon = rs.getString("sohoadon");

					/*
					 * System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] ngaynhanhang = " +
					 * this.ngaynhanhang); System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] dvthId = " + this.dvthId);
					 * System.out.println("[ErpNhanhang_Giay.initPdf] poId = " +
					 * this.poId); System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] diengiai = " +
					 * this.diengiai); System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] ndnId = " + this.ndnId);
					 * System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] sohoadon = " +
					 * this.sohoadon);
					 */

				}
				rs.close();
			} catch (SQLException e) {
			}
		}

		this.initSanPhamPdf(spId);
	}

	private void initSanPhamPdf(String _spId) {

		String query = " SELECT isnull(sp.loaisanpham_fk,0) as  loaisanpham_fk, CASE NH.LOAIHANGHOA_FK WHEN 0 THEN A.SANPHAM_FK WHEN 1 THEN TSCD.PK_SEQ ELSE NCP.PK_SEQ END AS SPID,  "
				+ "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL( CASE WHEN ( LEN(LTRIM(RTRIM(SP.MA))) <= 0 OR ( SP.MA IS NULL ) ) THEN SP.MA ELSE SP.MA END, '' )  WHEN 1 THEN TSCD.MA ELSE NCP.TEN END AS SPMA,   "
				+ "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL(SP.TEN1, ISNULL(SP.TEN, ''))  ELSE ISNULL(A.DIENGIAI, '') END AS SPTEN,  "
				+ "  A.NGAYNHANDUKIEN, A.DUNGSAI, A.DONGIA, A.SOLUONGDAT, A.SOLUONGNHAN, ISNULL(SP.HANSUDUNG, '0') AS HANSUDUNG,  "
				+ "  ISNULL(A.DONVI, 'NA') AS DONVI, A.TIENTE_FK, A.TYGIAQUYDOI, A.DONGIAVIET,  "
				+ "  KHOTT.PK_SEQ AS KHOTTID, KHOTT.MA + ', ' + KHOTT.TEN AS KHOTEN, "
				+ "  CASE WHEN ISNULL(QC.SOLUONG1,'0') = '0' THEN   "
				+ "  A.SOLUONGNHAN ELSE A.SOLUONGNHAN *  ISNULL(QC.SOLUONG2,'0') /QC.SOLUONG1 END AS TRONGLUONG , "
				+ "  CAST(ROUND(ISNULL(SP.THETICH, 0), 5) AS NUMERIC(10, 5)) AS THETICH  "
				+ "  FROM ERP_NHANHANG_SANPHAM A INNER JOIN ERP_NHANHANG NH ON A.NHANHANG_FK = NH.PK_SEQ    "
				+ "  LEFT JOIN ERP_SANPHAM SP ON A.SANPHAM_FK = SP.PK_SEQ    "
				+ "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND SP.DVDL_FK=QC.DVDL1_FK AND QC.DVDL2_FK=100003   "
				+ "  LEFT JOIN ERP_TAISANCODINH TSCD ON A.TAISAN_FK = TSCD.PK_SEQ         "
				+ "  LEFT JOIN ERP_NHOMCHIPHI NCP ON A.CHIPHI_FK = NCP.PK_SEQ   "
				+ "  LEFT JOIN ERP_KHOTT KHOTT ON A.KHONHAN = KHOTT.PK_SEQ   "
				+ "  WHERE A.NHANHANG_FK = "
				+ this.id
				+ " AND A.SOLUONGNHAN >0 ";

		if (_spId.length() > 0) {
			query += " and a.SANPHAM_FK in (" + _spId + ")";
		}

		ResultSet rsSp = db.get(query);

		if (rsSp != null) {
			try {
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next()) {
					String spId = rsSp.getString("spId");
					// System.out.println("[ErpNhanhang_Giay.initSanPhamPdf]
					// spId = " + spId);
					String spMa = rsSp.getString("spMa");

					// --- nếu sản phẩm là GLUE, ko in quy cách -----//
					String loaisp = rsSp.getString("loaisanpham_fk");

					String spTen = "";
					if (loaisp.equals("100015") || loaisp.equals("100016")) {
						spTen = rsSp.getString("spten1");
					} else {
						spTen = rsSp.getString("spTen");
					}

					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					String soluongdat = rsSp.getString("SOLUONGDAT");
					String soluongnhan = rsSp.getString("SOLUONGNHAN");
					String hansudung = rsSp.getString("khoTen"); // luu ten kho
					String dvdl = rsSp.getString("DonVi");

					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan,
							hansudung, ngaynhandk, soluongdat, dvdl);
					sanpham.setTrongluong(rsSp.getString("trongluong"));
					sanpham.setThetich(rsSp.getString("THETICH") == null ? "0"
							: rsSp.getString("THETICH"));
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	// trả về số lần nhận hàng hiện tại,
	// chỉ cập nhật lúc tạo mới mà thôi
	private int tinhSoLanNhanHang() {
		try {
			int stt = 0;
			String sql = "  select COUNT(*) as sl from ERP_NHANHANG where hdNCC_fk = "
					+ this.hdNccId;
			ResultSet rs = this.db.get(sql);
			if (rs != null) {
				if (rs.next()) {
					stt = rs.getInt("sl");
				}
				rs.close();
			}
			return (stt);

		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}

	// kiểm tra đã hoàn tất nhận hàng của hoá đơn này chưa?
	private boolean checkHoanTatNhanHang() {
		try {
			double sldat = 0;
			double slnhan = 0;
			String sql = " select isnull( (select SUM(SOLUONG) as sl from ERP_HOADONNCC_DONMUAHANG "
					+ " where HOADONNCC_FK = "
					+ this.hdNccId
					+ "),0) as sldat,"
					+ " isnull((select SUM(SOLUONGNHAN* TILEQUYDOI_DOLUONG) from ERP_NHANHANG_SANPHAM where NHANHANG_FK in ("
					+ " select PK_SEQ from ERP_NHANHANG where hdNCC_fk ="
					+ this.hdNccId + ")),0) as slnhan";

			ResultSet rs = this.db.get(sql);
			if (rs != null) {
				if (rs.next()) {
					sldat = rs.getInt("sldat");
					slnhan = rs.getInt("slnhan");
				}
				rs.close();
			}

			if (this.tilequydoi_dvdl > 1) {
				if (sldat == (slnhan * this.tilequydoi_dvdl))
					return true;
				return false;

			} else {
				if (sldat == slnhan)
					return true;
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	private boolean checkSoLo_Trung(DetailMeSp detail, String SPID) {
		// TODO Auto-generated method stub
		try {
			String query = " SELECT NGAYSANXUAT,NGAYHETHAN FROM ERP_NHANHANG_SP_CHITIET NHSP "
					+ " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=NHSP.NHANHANG_FK  "
					+ " WHERE NHSP.SANPHAM_FK="
					+ SPID
					+ " and  SOLO='"
					+ detail.getSoLo()
					+ "' AND NH.NPP_FK="
					+ this.nppId
					+ " and nh.trangthai not in ('3','4') "
					+ " and nh.pk_seq<> "
					+ (this.id.length() > 0 ? this.id : "0")
					+ " AND NH.LENHSANXUAT_FK IS NOT NULL ";

			System.out.println(query);
			// ĐÃ TỪNG NHẬP KHO RỒI THÌ NGÀY SẢN XUẤT NGÀY HẾT HẠN GIỐNG NHAU
			ResultSet rs = db.get(query);
			String ngayhethan = "";
			String ngaysanxuat = "";
			boolean bien = false;
			if (rs.next()) {
				ngaysanxuat = rs.getString("NGAYSANXUAT");
				ngayhethan = rs.getString("NGAYHETHAN");
				bien = true;
			}
			rs.close();

			if (bien) {
				System.out.println(ngayhethan);
				System.out.println(detail.getNgayHetHan());
				System.out.println(ngaysanxuat);

				System.out.println(detail.getNgaySanXuat());
				if (!ngayhethan.equals(detail.getNgayHetHan())
						|| !ngaysanxuat.equals(detail.getNgaySanXuat())) {
					detail.setNgayHetHan(ngayhethan);
					detail.setNgaySanXuat(ngaysanxuat);

					this.msg = "Không thể nhận cùng lô " + detail.getSoLo()
							+ " khác ngày hết hạn, lô có ngày hết hạn : "
							+ ngayhethan + " ngày sản xuất: " + ngaysanxuat;

					return true;
				}
			}

		} catch (Exception er) {
			er.printStackTrace();
		}
		return false;
	}

	public boolean createNhanHang() {
		String ngaytao = this.getDateTime();

		if (this.spList.size() <= 0) {
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		} else {
			for (int i = 0; i < spList.size(); i++) {
				ISanpham sp = spList.get(i);

				List<DetailMeSp> detailList = sp.getListDetailMeSp();
				for (int j = 0; j < detailList.size(); j++) {
					DetailMeSp detail = detailList.get(j);
					if (detail.getNgayHetHan().equals("")) {
						this.msg = "Vui lòng nhập đầy đủ thông tin ngày hết hạn";
						return false;
					}
				}

				if (sp.getSoluongnhan() == null || sp.getSoluongnhan() == "") {
					this.msg = "Vui lòng nhập đầy đủ thông tin số lượng cần nhập";
					return false;

				}

				if (Double.parseDouble(sp.getSoluongnhan()) > 0) {
					if (this.loaihanghoa.equals("0")) {
						if (sp.getListDetailMeSp().size() <= 0) {
							this.msg = "Vui lòng nhập đầy đủ thông tin : Số lô, Số lượng , Ngày sản xuất";
							return false;
						}
					}
				}

			}
		}

		if (this.dvthId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn đơn vị thực hiện cho phiếu nhận hàng này";
			return false;
		}

		if (this.KhoNhanId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn kho nhận cho phiếu nhận hàng này";
			return false;
		} else {
			if (this.loaikho.equals("5") || this.loaikho.equals("8")) {
				if (this.khachhangId.trim().length() <= 0) {
					this.msg = "Vui lòng chọn khách hàng cho kho nhận này";
					return false;
				}
			} else {
				this.khachhangId = "NULL";
			}
		}

		if (this.ndnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}

		if (this.loaihanghoa.equals("0") && this.ldnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lý do nhận hàng";
			return false;
		}

		String query = "";

		if (this.hdNccId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lệnh sản xuất nhập kho  ";
			return false;
		}
		if (this.KhonhanTpdat == null || this.KhonhanTpdat.equals("")) {
			this.msg = "Vui lòng chọn kho thành phẩm hàng đạt  ";
			return false;
		}

		try {
			db.getConnection().setAutoCommit(false);

			String muahang_fk = "null";
			String trahang_fk = "null";

			if (this.ndnId.equals("100000")) {
				if (this.poId.trim().length() > 0)
					muahang_fk = this.poId;
			} else {
				if (this.poId.trim().length() > 0)
					trahang_fk = this.poId;
			}

			String ldn_fk = "null";
			if (this.ldnId.trim().length() > 0)
				ldn_fk = this.ldnId;

			String NCC_KH_FK = "null";
			if (this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;

			/*
			 * // gan kho cho xu ly=100047 la kho cho xu ly nguyen lieu trong
			 * bang erp_nhanhang // //
			 */
			String LOAIDOITUONGNHAN = "";
			String DOITUONGNHAN_FK = "NULL";
			if (util_kho.getLoaiKho(db, this.KhonhanTpdat).equals("10")) {

				LOAIDOITUONGNHAN = "5";
				DOITUONGNHAN_FK = this.IdDoituongkhonhan;

			}

			query = "insert ERP_NHANHANG(DOITUONGNHAN_FK,LOAIDOITUONGNHAN,  KHONHANTP_DAT_FK,KHACHHANGKYGUI_FK, KHONHAN_FK, NGAYNHAN, NGAYCHOT, LOAIHANGHOA_FK, NOIDUNGNHAN_FK, DIENGIAI, "
					+ "DONVITHUCHIEN_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NoiDungNhap_fk, NCC_KH_FK, LENHSANXUAT_FK, tigia ,khochoxuly_fk, "
					+ "GHICHU , NPP_FK,CONGDOAN_FK, FILENAME,LOAIDOITUONG,DOITUONG_FK) "
					+ "values( "
					+ DOITUONGNHAN_FK
					+ ",'"
					+ LOAIDOITUONGNHAN
					+ "', "
					+ this.KhonhanTpdat
					+ ","
					+ this.khachhangId
					+ ", "
					+ this.KhoNhanId
					+ ",'"
					+ this.ngaynhanhang
					+ "', "
					+ "'"
					+ this.ngaynhanhang
					+ "', '"
					+ this.loaihanghoa
					+ "', '"
					+ this.ndnId
					+ "', N'"
					+ this.diengiai
					+ "', '"
					+ this.dvthId
					+ "',  "
					+ " '"
					+ ngaytao
					+ "', '"
					+ ngaytao
					+ "', '"
					+ this.userId
					+ "', '"
					+ this.userId
					+ "', '0', '"
					+ this.congtyId
					+ "', "
					+ ldn_fk
					+ ", "
					+ NCC_KH_FK
					+ ", "
					+ this.hdNccId
					+ ", '"
					+ this.tigia
					+ "',"
					+ this.khoChoXuLy
					+ ", "
					+ " N'"
					+ this.ghichu
					+ "', NULL ,'"
					+ this.idcongdoan
					+ "' ,N'"
					+ this.filename
					+ "','5',(select top 1 PK_SEQ from ERP_LENHSANXUAT_CONGDOAN_GIAY where CONGDOAN_FK='"
					+ this.idcongdoan
					+ "' and LENHSANXUAT_FK='"
					+ this.hdNccId
					+ "'))";
			System.out.println(" vao khu che xuat ne :" + query);

			if (!db.update(query)) {
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}

			String nhCurrent = "";
			query = "select IDENT_CURRENT('Erp_NHANHANG') as nhId";

			ResultSet rsNh = db.get(query);
			if (rsNh.next()) {
				nhCurrent = rsNh.getString("nhId");
				rsNh.close();
			}

			// Tính ra lần nhận hàng thứ mấy của đơn hàng.
			int soLanNhanHang = tinhSoLanNhanHang();
			if (soLanNhanHang == -1) {
				this.msg = "Không tính được số lần nhận hàng của hoá đơn";
				db.getConnection().rollback();
				return false;
			}

			// Cập nhật số lần nhận hàng
			query = "update ERP_NHANHANG set LANNHAN =" + soLanNhanHang
					+ " where PK_SEQ = " + nhCurrent;
			int k = this.db.updateReturnInt(query);
			if (k != 1) {
				this.msg = "Cập nhật lần nhận hàng thất bại.";
				db.getConnection().rollback();
				return false;
			}

			if (this.spList.size() > 0) {
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);

					if (sp.getSoluongnhan() != "") // chi luu nhung san pham
													// nguoi dung nhap so luong
					{
						String SanPham_FK = "NULL";
						String ChiPhi_FK = "NULL";
						String TaiSan_FK = "NULL";
						String CCDC_FK = "NULL";

						if (this.loaihanghoa.equals("0")) {
							SanPham_FK = sp.getId();
							ChiPhi_FK = "NULL";
							TaiSan_FK = "NULL";
							CCDC_FK = "NULL";
						} else {
							if (this.loaihanghoa.equals("1")) {
								SanPham_FK = "NULL";
								ChiPhi_FK = "NULL";
								TaiSan_FK = sp.getId();
								CCDC_FK = "NULL";
							} else {
								if (loaihanghoa.equals("3")) {
									SanPham_FK = "NULL";
									ChiPhi_FK = "NULL";
									TaiSan_FK = "NULL";
									CCDC_FK = sp.getId();
								} else {
									SanPham_FK = "NULL";
									ChiPhi_FK = sp.getId();
									TaiSan_FK = "NULL";
									CCDC_FK = "NULL";
								}
							}
						}

						double soluongnhan = 0;
						if (sp.getSoluongnhan().trim().length() <= 0) {
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: "
									+ sp.getMa();
							db.getConnection().rollback();
							return false;
						} else {
							soluongnhan = Double.parseDouble(sp
									.getSoluongnhan().replaceAll(",", ""));
							System.out.println("so luong nhan " + soluongnhan);
							/*
							 * if (sp.getDungsai().trim().length() <= 0)
							 * sp.setDungsai("0");
							 * 
							 * double slgMax =
							 * Double.parseDouble(sp.getSoluongMaxNhan
							 * ().replaceAll(",", ""));
							 * System.out.println("so luong max nhan " +
							 * slgMax); if (soluongnhan > slgMax ) { this.msg =
							 * "Tổng số lượng nhận của sản phẩm: " + sp.getMa()
							 * + " không được phép vượt quá tổng đặt (" +
							 * sp.getSoluongdat() + ") và dung sai cho phép ( "
							 * + sp.getDungsai() +
							 * "%). Vì thế bạn chỉ có thể nhận tối đa là ( " +
							 * slgMax + " )  "; db.getConnection().rollback();
							 * return false; }
							 */
						}

						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null"
								: sp.getKhonhanId().trim();
						if (this.loaihanghoa.equals("0")
								&& sp.getKhonhanId().trim().length() <= 0) {
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( "
									+ sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}

						// Kiểm định =1 , không kiểm định = 0;
						String kho = "";
						if (sp.getIsKiemDinh().equals("0")) {
							kho = this.KhoNhanId;
						} else {
							kho = this.khoChoXuLy;
						}

						// Kho nhận đây là kho nhận thực sự: có thể kho chờ xử
						// lý hoặc kho nhân
						query = " insert ERP_NHANHANG_SANPHAM(NHANHANG_FK ,SANPHAM_FK, TAISAN_FK, CCDC_FK, "
								+ " CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI,"
								+ " DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC,SPKIEMDINH,TILEQUYDOI_DOLUONG) "
								+ "values('"
								+ nhCurrent
								+ "',   "
								+ SanPham_FK
								+ ", "
								+ TaiSan_FK
								+ ", "
								+ CCDC_FK
								+ ", "
								+ ChiPhi_FK
								+ ", N'"
								+ sp.getDiengiai()
								+ "', N'"
								+ sp.getDvdl()
								+ "', '"
								+ sp.getNgaynhandukien()
								+ "', "
								+ kho
								+ ", "
								+ "'"
								+ sp.getSoluongdat().replaceAll(",", "")
								+ "',  '"
								+ sp.getSoluongnhan().replaceAll(",", "")
								+ "', '"
								+ sp.getDungsai()
								+ "', "
								+ Double.parseDouble(sp.getDongia().replaceAll(
										",", ""))
								+ ", '"
								+ sp.getTiente()
								+ "', '"
								+ sp.getTigiaquydoi()
								+ "', '"
								+ sp.getDongiaViet().replaceAll(",", "")
								+ "' , "
								+ 0
								+ ", "
								+ 0
								+ ", '"
								+ sp.getIsKiemDinh()
								+ "',"
								+ sp.getTiLeQuyDoiDv() + " )";
						System.out.println("insert nhanhang_sanpham: " + query);
						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: "
									+ query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}

						double tongchitiet = 0;

						List<DetailMeSp> detailList = sp.getListDetailMeSp();
						for (int j = 0; j < detailList.size(); j++) {
							DetailMeSp detail = detailList.get(j);
							/*
							 * set co dinh cho khu_fk =100000 (khu biet tru)
							 * trong bang ERP_NHANHANG_SP_CHITIET Số thùng không
							 * dùng nhé 03/06/2016
							 */
							// set mặc định kho nào không có khu thì là 100000.
							if (detail.getKhuVuc() == null) {
								detail.setKhuVuc("0");
							}

							/*
							 * String msg1=
							 * Library.checkSoLo_Trung(db,detail.getSoLo
							 * (),detail
							 * .getNgayHetHan(),detail.getNgaySanXuat(),
							 * sp.getId(),this.nppId); if(msg1.length()>0){
							 * this.msg=msg1; db.getConnection().rollback();
							 * return false; }
							 */

							query = " insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO,MATHUNG, "
									+ " SOLUONG, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,BIN_FK, MAME, GIATHEOLO) "
									+ "values('"
									+ nhCurrent
									+ "',   '"
									+ sp.getId()
									+ "', '"
									+ Integer.toString(j + 1)
									+ "', '"
									+ detail.getSoLo()
									+ "','"
									+ detail.getMaThung()
									+ "', "
									+ "'"
									+ detail.getSoLuong().replaceAll(",", "")
									+ "', '"
									+ detail.getNgaySanXuat()
									+ "', '"
									+ detail.getNgayHetHan()
									+ "','"
									+ sp.getNgaynhandukien()
									+ "',"
									+ detail.getKhuVuc()
									+ ",'"
									+ detail.getMe()
									+ "',"
									+ sp.getDongia()
									+ ")";

							tongchitiet = tongchitiet
									+ Double.parseDouble(detail.getSoLuong()
											.replaceAll(",", ""));

							System.out
									.println("11.Insert Nhan hang ERP_NHANHANG_SP_CHITIET: "
											+ query);

							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: "
										+ query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}

						if (this.loaihanghoa.equals("0")) {

							NumberFormat formatter_3le = new DecimalFormat(
									"#######.###");

							tongchitiet = Double.parseDouble(formatter_3le
									.format(tongchitiet));

							double tong_ = Double.parseDouble(formatter_3le
									.format(Double.parseDouble(sp
											.getSoluongnhan().replaceAll(",",
													""))));

							if (tong_ - tongchitiet != 0) {
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :"
										+ sp.getMa();
								db.getConnection().rollback();
								return false;
							}
						}

					}
				}
			}

			// CẬP NHẬT TOOLTIP
			String ab = db.execProceduce2("CapNhatTooltip_NhanHang",
					new String[] { nhCurrent });

			/*
			 * // Cập nhật lại xem đã hoàn tất nhận hàng hay chưa? boolean check
			 * = checkHoanTatNhanHang(); check =
			 * this.UpdateHoanTatNhanHang(check, nhCurrent); if( check ==
			 * false){ this.msg =
			 * "Cập nhật trạng thái hoàn tất nhận hàng thất bại";
			 * db.getConnection().rollback(); return false; }
			 */

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			this.msg = "Lỗi tạo nhận hàng: " + e.getMessage();
			e.printStackTrace();
			db.update("rollback");

			return false;
		}

	}

	private boolean UpdateHoanTatNhanHang(boolean flag, String id) {
		try {
			int index = 0;
			if (flag == true) {
				index = 1;
			}
			String query = "";
			query = "update ERP_PARK set HOANTAT_NHANHANG = '" + index
					+ "' where PK_SEQ in ("
					+ "		select park_fk from ERP_HOADONNCC where pk_seq in ("
					+ "			 select hdNCC_fk from ERP_NHANHANG where PK_SEQ = "
					+ id + "))";

			int k = this.db.updateReturnInt(query);
			if (k != 1) {
				this.msg = " Không cập nhật được trạng thái hoàn tất nhận hàng hoá đơn.";
				this.db.getConnection().rollback();
				return false;
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	public boolean updateNhanHang() {
		String ngaytao = this.getDateTime();

		if (this.spList.size() <= 0) {
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		} else {
			for (int i = 0; i < spList.size(); i++) {
				ISanpham sp = spList.get(i);
				List<DetailMeSp> detailList = sp.getListDetailMeSp();
				for (int j = 0; j < detailList.size(); j++) {
					DetailMeSp detail = detailList.get(j);
					if (detail.getNgayHetHan().equals("")) {
						this.msg = "Vui lòng nhập đầy đủ thông tin ngày hết hạn";
						return false;
					}
				}
				if (sp.getSoluongnhan() == null || sp.getSoluongnhan() == "") {
					this.msg = "Vui lòng nhập đầy đủ thông tin : Số lô, Số lượng , Ngày sản xuất";
					return false;

				}

				if (Double.parseDouble(sp.getSoluongnhan()) > 0) {
					if (this.loaihanghoa.equals("0")) {
						if (sp.getListDetailMeSp().size() <= 0) {
							this.msg = "Vui lòng nhập đầy đủ thông tin : Số lô, Số lượng , Ngày sản xuất";
							return false;
						}
					}
				}

			}
		}

		if (this.dvthId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn đơn vị thực hiện cho phiếu nhận hàng này";
			return false;
		}

		if (this.KhonhanTpdat == null || this.KhonhanTpdat.equals("")) {
			this.msg = "Vui lòng chọn kho thành phẩm hàng đạt  ";
			return false;
		}

		if (this.KhoNhanId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn kho nhận cho phiếu nhận hàng này";
			return false;
		} else {
			if (this.loaikho.equals("5") || this.loaikho.equals("8")) {
				if (this.khachhangId.trim().length() <= 0) {
					this.msg = "Vui lòng chọn khách hàng cho kho nhận này";
					return false;
				}
			} else {
				this.khachhangId = "NULL";
			}
		}

		if (this.ndnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}

		if (this.loaihanghoa.equals("0") && this.ldnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lý do nhận hàng";
			return false;
		}

		String query = "";

		if (this.hdNccId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lệnh sản xuất";
			return false;
		}

		try {
			db.getConnection().setAutoCommit(false);

			String muahang_fk = "null";
			String trahang_fk = "null";

			if (this.ndnId.equals("100000")) {
				if (this.poId.trim().length() > 0)
					muahang_fk = this.poId;
			} else {
				if (this.poId.trim().length() > 0)
					trahang_fk = this.poId;
			}

			String ldn_fk = "null";
			if (this.ldnId.trim().length() > 0)
				ldn_fk = this.ldnId;

			String NCC_KH_FK = "null";
			if (this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;

			String nppId = util.getIdNhapp(this.userId);
			/*
			 * // gan kho cho xu ly=100047 la kho cho xu ly nguyen lieu trong
			 * bang erp_nhanhang // //
			 */

			String LOAIDOITUONGNHAN = "";
			String DOITUONGNHAN_FK = "NULL";
			if (util_kho.getLoaiKho(db, this.KhonhanTpdat).equals("10")) {

				LOAIDOITUONGNHAN = "5";
				DOITUONGNHAN_FK = this.IdDoituongkhonhan;

			}

			query = " update ERP_NHANHANG set  DOITUONGNHAN_FK="
					+ DOITUONGNHAN_FK
					+ ",LOAIDOITUONGNHAN='"
					+ LOAIDOITUONGNHAN
					+ "',  KHONHANTP_DAT_FK="
					+ this.KhonhanTpdat
					+ ",  KHACHHANGKYGUI_FK = "
					+ this.khachhangId
					+ " ,khochoxuly_fk="
					+ this.khoChoXuLy
					+ ", KHONHAN_FK = "
					+ this.KhoNhanId
					+ ",  "
					+ "  NGAYNHAN = '"
					+ this.ngaynhanhang
					+ "', NOIDUNGNHAN_FK = '"
					+ this.ndnId
					+ "', "
					+ "DIENGIAI = N'"
					+ this.diengiai
					+ "', "
					+ "DONVITHUCHIEN_FK = '"
					+ this.dvthId
					+ "',"
					+ " NGAYSUA = '"
					+ this.getDateTime()
					+ "', "
					+ "NGUOISUA = '"
					+ this.userId
					+ "', NoiDungNhap_fk = "
					+ ldn_fk
					+ ","
					+ " NCC_KH_FK = "
					+ NCC_KH_FK
					+ ", lenhsanxuat_fk = "
					+ this.hdNccId
					+ ", tigia = '"
					+ this.tigia
					+ "', GHICHU = N'"
					+ this.ghichu
					+ "',CONGDOAN_FK = "
					+ this.idcongdoan
					+ ",FILENAME=N'"
					+ this.filename
					+ "',LOAIDOITUONG=5, DOITUONG_FK=(select top 1 PK_SEQ from ERP_LENHSANXUAT_CONGDOAN_GIAY where CONGDOAN_FK='"
					+ this.idcongdoan + "' and LENHSANXUAT_FK='" + this.hdNccId
					+ "')" + " where pk_seq = '" + this.id
					+ "' and trangthai=0 ";

			System.out.println("Query update: " + query);

			if (db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật phiếu nhận hàng , vui lòng reload lại phiếu để kiểm tra trạng thái, lỗi dòng lệnh : "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_NHANHANG_SANPHAM where nhanhang_fk = '"
					+ this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_NHANHANG_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_NHANHANG_SP_CHITIET where nhanhang_fk = '"
					+ this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_NHANHANG_SP_CHITIET: "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			if (this.spList.size() > 0) {
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);

					if (sp.getSoluongnhan() != "") // chi luu nhung san pham
													// nguoi dung nhap so luong
					{
						String SanPham_FK = "NULL";
						String ChiPhi_FK = "NULL";
						String TaiSan_FK = "NULL";
						String CCDC_FK = "NULL";

						if (this.loaihanghoa.equals("0")) {
							SanPham_FK = sp.getId();
							ChiPhi_FK = "NULL";
							TaiSan_FK = "NULL";
							CCDC_FK = "NULL";
						} else {
							if (this.loaihanghoa.equals("1")) {
								SanPham_FK = "NULL";
								ChiPhi_FK = "NULL";
								TaiSan_FK = sp.getId();
								CCDC_FK = "NULL";
							} else {
								if (loaihanghoa.equals("3")) {
									SanPham_FK = "NULL";
									ChiPhi_FK = "NULL";
									TaiSan_FK = "NULL";
									CCDC_FK = sp.getId();
								} else {
									SanPham_FK = "NULL";
									ChiPhi_FK = sp.getId();
									TaiSan_FK = "NULL";
									CCDC_FK = "NULL";
								}
							}
						}

						double soluongnhan = 0;
						if (sp.getSoluongnhan().trim().length() <= 0) {
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: "
									+ sp.getMa();
							db.getConnection().rollback();
							return false;
						} else {
							soluongnhan = Double.parseDouble(sp
									.getSoluongnhan().replaceAll(",", ""));
							System.out.println("so luong nhan " + soluongnhan);
							if (sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");

							double slgMax = Double.parseDouble(sp
									.getSoluongMaxNhan().replaceAll(",", ""));
							System.out.println("so luong max nhan " + slgMax);
							/*
							 * if (soluongnhan > slgMax &&
							 * loaihanghoa.equals("0") ) { this.msg =
							 * "Tổng số lượng nhận của sản phẩm: " + sp.getMa()
							 * + " không được phép vượt quá tổng đặt (" +
							 * sp.getSoluongdat() + ") và dung sai cho phép ( "
							 * + sp.getDungsai() +
							 * "%). Vì thế bạn chỉ có thể nhận tối đa là ( " +
							 * slgMax + " )  "; db.getConnection().rollback();
							 * return false; }
							 */
						}

						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null"
								: sp.getKhonhanId().trim();
						if (this.loaihanghoa.equals("0")
								&& sp.getKhonhanId().trim().length() <= 0) {
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( "
									+ sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}

						// Kiểm định =1 , không kiểm định = 0;
						String kho = "";
						if (sp.getIsKiemDinh().equals("0")) {
							kho = this.KhoNhanId;
						} else {
							kho = this.khoChoXuLy;
						}

						// Kho nhận đây là kho nhận thực sự: có thể kho chờ xử
						// lý hoặc kho nhân
						query = " insert ERP_NHANHANG_SANPHAM(NHANHANG_FK ,SANPHAM_FK, TAISAN_FK, CCDC_FK, "
								+ " CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI,"
								+ " DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC,SPKIEMDINH,TILEQUYDOI_DOLUONG) "
								+ "values('"
								+ this.id
								+ "',   "
								+ SanPham_FK
								+ ", "
								+ TaiSan_FK
								+ ", "
								+ CCDC_FK
								+ ", "
								+ ChiPhi_FK
								+ ", N'"
								+ sp.getDiengiai()
								+ "', N'"
								+ sp.getDvdl()
								+ "', '"
								+ sp.getNgaynhandukien()
								+ "', "
								+ kho
								+ ", "
								+ "'"
								+ sp.getSoluongdat().replaceAll(",", "")
								+ "',  '"
								+ sp.getSoluongnhan().replaceAll(",", "")
								+ "', '"
								+ sp.getDungsai()
								+ "', "
								+ Double.parseDouble(sp.getDongia().replaceAll(
										",", ""))
								+ ", '"
								+ sp.getTiente()
								+ "', '"
								+ sp.getTigiaquydoi()
								+ "', '"
								+ sp.getDongiaViet().replaceAll(",", "")
								+ "' , "
								+ 0
								+ ", "
								+ 0
								+ ", '"
								+ sp.getIsKiemDinh()
								+ "',"
								+ sp.getTiLeQuyDoiDv() + " )";
						System.out.println("insert nhanhang_sanpham: " + query);
						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: "
									+ query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}

						double tongchitiet = 0;

						List<DetailMeSp> detailList = sp.getListDetailMeSp();
						for (int j = 0; j < detailList.size(); j++) {
							DetailMeSp detail = detailList.get(j);
							/*
							 * set co dinh cho khu_fk =100000 (khu biet tru)
							 * trong bang ERP_NHANHANG_SP_CHITIET Số thùng không
							 * dùng nhé 03/06/2016
							 */
							// set mặc định kho nào không có khu thì là 100000.
							if (detail.getKhuVuc() == null) {
								detail.setKhuVuc("0");
							}

							/*
							 * String msg1=
							 * Library.checkSoLo_Trung(db,detail.getSoLo
							 * (),detail
							 * .getNgayHetHan(),detail.getNgaySanXuat(),
							 * sp.getId(),this.nppId); if(msg1.length()>0){
							 * this.msg=msg1; db.getConnection().rollback();
							 * return false; }
							 */

							query = " insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, SANPHAM_FK, LANNHAN, SOLO,MATHUNG, "
									+ " SOLUONG, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,BIN_FK, MAME, GIATHEOLO) "
									+ "values('"
									+ this.id
									+ "',   '"
									+ sp.getId()
									+ "', '"
									+ Integer.toString(j + 1)
									+ "', '"
									+ detail.getSoLo()
									+ "','"
									+ detail.getMaThung()
									+ "', "
									+ "'"
									+ detail.getSoLuong().replaceAll(",", "")
									+ "', '"
									+ detail.getNgaySanXuat()
									+ "', '"
									+ detail.getNgayHetHan()
									+ "','"
									+ sp.getNgaynhandukien()
									+ "',"
									+ detail.getKhuVuc()
									+ ",'"
									+ detail.getMe()
									+ "',"
									+ sp.getDongia()
									+ ")";

							tongchitiet = tongchitiet
									+ Double.parseDouble(detail.getSoLuong()
											.replaceAll(",", ""));

							// System.out.println("11.Insert Nhan hang ERP_NHANHANG_SP_CHITIET: "
							// + query);

							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: "
										+ query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}

						if (this.loaihanghoa.equals("0")) {

							NumberFormat formatter_3le = new DecimalFormat(
									"#######.###");

							tongchitiet = Double.parseDouble(formatter_3le
									.format(tongchitiet));

							double tong_ = Double.parseDouble(formatter_3le
									.format(Double.parseDouble(sp
											.getSoluongnhan().replaceAll(",",
													""))));

							if (tong_ - tongchitiet != 0) {
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :"
										+ sp.getMa();
								db.getConnection().rollback();
								return false;
							}
						}

					}
				}
			}

			// CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_NhanHang",
					new String[] { this.id });

			// Cập nhật lại xem đã hoàn tất nhận hàng hay chưa?
			/*
			 * boolean check = checkHoanTatNhanHang(); check =
			 * this.UpdateHoanTatNhanHang(check, this.id); if( check == false){
			 * this.msg = "Cập nhật trạng thái hoàn tất nhận hàng thất bại";
			 * db.getConnection().rollback(); return false; }
			 */

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			this.msg = "Lỗi tạo nhận hàng: " + e.getMessage();
			e.printStackTrace();
			db.update("rollback");

			return false;
		}

	}

	private boolean IsCoKiemdinh() {

		try {

			if (this.loaihanghoa.equals("0")) {
				String str_sp = "";
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);

					if (sp.getSoluongnhan() != "") // chi luu nhung san pham
													// nguoi dung nhap so luong
					{

						if (Double.parseDouble(sp.getSoluongnhan()) > 0) {

							str_sp = (str_sp.length() > 0 ? str_sp + "," : "")
									+ sp.getId();

						}

					}
				}
				if (str_sp.length() > 0) {
					String query = "SELECT PK_SEQ,KIEMTRADINHLUONG, KIEMTRADINHTINH FROM SANPHAM SP WHERE (SP.KIEMTRADINHLUONG=1 OR SP.KIEMTRADINHTINH='1') AND  PK_SEQ IN ("
							+ str_sp + ")";
					ResultSet rs = db.get(query);
					if (rs.next()) {
						rs.close();
						return true;
					}
					rs.close();

				}
			} else {
				return false;
			}

		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}

	public void updateDonmuahang(String poId) {
	}

	public void close() {
		try {

			if (spList != null) {
				spList.clear();
			}

			if (ndnRs != null) {
				ndnRs.close();
			}

			if (mahangmuaRs != null) {
				mahangmuaRs.close();
			}
			if (dvthRs != null) {
				dvthRs.close();
			}
			if (poRs != null) {
				poRs.close();
			}

			if (RsKhoNhanTpDat != null)
				RsKhoNhanTpDat.close();

			if (ldnRs != null)
				ldnRs.close();
			if (nccRs != null)
				nccRs.close();

			if (hdNccRs != null)
				hdNccRs.close();
			if (RsKhoNhan != null)
				RsKhoNhan.close();
			if (RsKhoChoXuLy != null)
				RsKhoChoXuLy.close();
			if (hoadonNCCList != null)
				hoadonNCCList.close();
			if (khachhangRs != null)
				khachhangRs.close();
			if (listKhuVucKho != null)
				listKhuVucKho.clear();
			if (listKhuVucKhoCXL != null)
				listKhuVucKhoCXL.clear();
			if (listDonvi != null)
				listDonvi.clear();
			if (congdoanRs != null)
				congdoanRs.close();
			if (phepham_duphamRs != null)
				phepham_duphamRs.close();
			db.shutDown();

		} catch (Exception er) {

		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public int getNgayhethan() {
		return this.ngayhethan;
	}

	public void setNgayhethan(int ngayhethan) {
		this.ngayhethan = ngayhethan;
	}

	public String getNdnId() {

		return this.ndnId;
	}

	public void setNdnId(String mhmId) {

		this.ndnId = mhmId;
	}

	public ResultSet getNdnList() {

		return this.ndnRs;
	}

	public void setNdnList(ResultSet ndnlist) {

		this.ndnRs = ndnlist;
	}

	public String getLoaihanghoa() {
		return this.loaihanghoa;
	}

	public void setLoaihanghoa(String loaihh) {
		this.loaihanghoa = loaihh;
	}

	public String getNgaychot() {
		return this.ngaychot;
	}

	public void setNgaychot(String ngaychot) {
		this.ngaychot = ngaychot;
	}

	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public void setLdnId(String ndnId) {

		this.ldnId = ndnId;
	}

	public String getLdnId() {

		return this.ldnId;
	}

	public ResultSet getLdnList() {

		return this.ldnRs;
	}

	public void setLdnList(ResultSet ldnList) {

		this.ldnRs = ldnList;
	}

	public void setNccId(String ndnId) {

		this.nccId = ndnId;
	}

	public String getNccId() {

		return this.nccId;
	}

	public ResultSet getNccList() {

		return this.nccRs;
	}

	public void setNccList(ResultSet nccList) {

		this.nccRs = nccList;
	}

	public String getIsPONK() {

		return this.isPONK;
	}

	public void setIsPONK(String poNK) {

		this.isPONK = poNK;
	}

	public void setHdNccId(String hdNccId) {

		this.hdNccId = hdNccId;
	}

	public String getHdNccId() {

		return this.hdNccId;
	}

	public ResultSet getHdNccList() {

		return this.hdNccRs;
	}

	public void setHdNccList(ResultSet hdnccList) {

		this.hdNccRs = hdnccList;
	}

	public boolean kt_nhanhang_theoHDNCC_bivuot(String hoadonncc, String ncc_fk) {
		String query = "";
		query = " select top(1) hd.pk_seq as hdId, hd_dmh.SANPHAM_FK,   \n"
				+ " isnull((select SUM(nhsp.SOLUONGNHAN)      \n"
				+ "from ERP_NHANHANG nh      \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK      \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  and nh.trangthai not in (3,4)     \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0) - SUM(hd_dmh.SOLUONG)  as soluongconlai      \n"
				+ "from ERP_HOADONNCC hd      \n"
				+ "inner join ERP_PARK p on hd.park_fk = p.pk_seq      \n"
				+ "inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK      \n"
				+ "where p.ncc_fk="
				+ ncc_fk
				+ " and hd.sohoadon='"
				+ hoadonncc.trim()
				+ "'  and hd_dmh.SOLUONG >0      \n"
				+ "group by hd.pk_seq, hd_dmh.SANPHAM_FK    \n"
				+ "having  (   isnull((select SUM(nhsp.SOLUONGNHAN) \n"
				+ "from ERP_NHANHANG nh  \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK  \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0)  - SUM(hd_dmh.SOLUONG)  ) > 0   ";

		System.out.println(" Kt nhan hang vuot :" + query);
		ResultSet rs = db.get(query);

		try {
			String hd = "";
			double soluongconlai;
			while (rs.next()) {
				hd = rs.getString("hdId");
				soluongconlai = rs.getDouble("soluongconlai");
			}
			rs.close();
			if (hd.length() > 0) {
				this.msg = "Nhận hàng của hoá đơn " + hd
						+ " đang có sản phẩm bị quá số lượng so với hoá đơn  ";
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void kt_capnhattrangthai_hoadonNCC(String hoadonncc, String ncc_fk,
			String nhanhang_ID) {
		String query = "";
		query = " select top(1) hd.pk_seq as hdId, hd_dmh.SANPHAM_FK,    \n"
				+ " isnull((select SUM(nhsp.SOLUONGNHAN)      \n"
				+ "from ERP_NHANHANG nh      \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK      \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK   and nh.trangthai not in (3,4)     \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0) -  SUM(hd_dmh.SOLUONG) as soluongconlai      \n"
				+ "from ERP_HOADONNCC hd      \n"
				+ "inner join ERP_PARK p on hd.park_fk = p.pk_seq      \n"
				+ "inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK      \n"
				+ "where p.ncc_fk="
				+ ncc_fk
				+ " and hd.sohoadon='"
				+ hoadonncc.trim()
				+ "'  and hd_dmh.SOLUONG >0      \n"
				+ "group by hd.pk_seq, hd_dmh.SANPHAM_FK    \n"
				+ "having  (  isnull((select SUM(nhsp.SOLUONGNHAN) \n"
				+ "from ERP_NHANHANG nh  \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK  \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0)  -  SUM(hd_dmh.SOLUONG) ) <>  0   ";

		System.out.println(" Kt nhan hang du HD chua :" + query);
		ResultSet rs = db.get(query);

		try {
			if (!rs.next()) {
				query = " update erp_hoadonncc_donmuahang set is_nhanhang=1 where hoadonncc_fk=(select hdNCC_fk from erp_nhanhang where pk_seq ="
						+ nhanhang_ID + ") ";
				db.update(query);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public boolean suaSoHoaDon() {

		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "UpDate ERP_NHANHANG set SoHoaDon='" + this.sohoadon
					+ "',DienGiai=N'" + this.diengiai + "' where pk_Seq='"
					+ this.id + "'";
			if (!this.db.update(query)) {
				this.msg = "Lỗi khi cập nhật số hóa đơn " + query;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Lỗi khi cập nhật số hóa đơn(Exception) "
					+ e.getMessage();
			e.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}

	public String getLydonhan() {

		return this.lydo;
	}

	public void setLydo(String lydo) {
		this.lydo = lydo;
	}

	public String getPhongBan() {

		return this.pb;
	}

	public void setPhongBan(String phongban) {

		this.pb = phongban;
	}

	public boolean getIsKhoNhanQL_Khuvuc() {

		return this.IsKhoNhanQL_khuvuc;
	}

	public void setIsKhoNhanQL_Khuvuc(boolean bien) {

		this.IsKhoNhanQL_khuvuc = bien;
	}

	public ResultSet getrskhoNhan() {

		return this.RsKhoNhan;
	}

	public void setrskhoNhan(ResultSet rskhoNhan) {

		this.RsKhoNhan = rskhoNhan;
	}

	public String getKhoNhanId() {

		return this.KhoNhanId;
	}

	public void setKhoNhanId(String KHoNhanId) {

		this.KhoNhanId = KHoNhanId;
	}

	public Integer getIs_saungayKS() {
		return this.is_saungayKS;
	}

	public void setIs_saungayKS(Integer is_saungayKS) {
		this.is_saungayKS = is_saungayKS;
	}

	public String getGhichu() {

		return this.ghichu;
	}

	public void setGhichu(String ghichu) {

		this.ghichu = ghichu;
	}

	public String getLoaimh() {
		return this.loaimh;
	}

	public void setLoaimh(String loaimh) {
		this.loaimh = loaimh;
	}

	public String getIsTudong() {
		return this.isTudong;
	}

	public void setIsTudong(String isTudong) {
		this.isTudong = isTudong;
	}

	public String getKhachhangId() {
		return this.khachhangId;
	}

	public void setKhachhangId(String khachhangId) {
		this.khachhangId = khachhangId;
	}

	public ResultSet getKhachhangRs() {
		return this.khachhangRs;
	}

	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;
	}

	public String getLoaikho() {
		return this.loaikho;
	}

	public void setLoaikho(String loaikho) {
		this.loaikho = loaikho;
	}

	public void init_convert(String hoadonnccId, String loaihd) {

		String query = "";
		if (loaihd.equals("0")) {
			query = " select distinct DONVITHUCHIEN_FK, LOAIHANGHOA_FK "
					+ " from ERP_MUAHANG "
					+ "WHERE PK_SEQ IN (SELECT DISTINCT A.MUAHANG_FK "
					+ "					FROM ERP_HOADONNCC_DONMUAHANG A "
					+ "					WHERE HOADONNCC_FK = " + hoadonnccId + ") ";
		} else {
			query = " select distinct DONVITHUCHIEN_FK, LOAIHANGHOA_FK "
					+ " from ERP_MUAHANG "
					+ "WHERE PK_SEQ IN (SELECT DISTINCT A.MUAHANG_FK "
					+ "					FROM ERP_HOADONNCC_DONMUAHANG A WHERE HOADONNCC_FK = "
					+ hoadonnccId + ") ";
		}

		System.out.println("[init convert] " + query);
		ResultSet RsKtr = db.get(query);

		try {
			while (RsKtr.next()) {
				this.loaihanghoa = RsKtr.getString("LOAIHANGHOA_FK");
				this.dvthId = RsKtr.getString("DONVITHUCHIEN_FK");
			}
			RsKtr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// lay Kho Nhan va Kho Cho Xu Ly
		query = " select KHOBIETTRU_FK, KHOTONTRU_FK,ngayhoadon  from ERP_HOADONNCC where pk_seq = "
				+ hoadonnccId;
		ResultSet rs = this.db.get(query);

		String khoBietTru = "";
		String khoTonTru = "";
		try {
			if (rs != null) {
				if (rs.next()) {
					this.ngaynhanhang = rs.getString("ngayhoadon");
					khoBietTru = rs.getString("KHOBIETTRU_FK");
					khoTonTru = rs.getString("KHOTONTRU_FK");
				}
				rs.close();
			}

			// lấy kho của User đăng nhập để định vị kho. Biệt trữ hay tồn trữ.

			// lấy kho mà người dùng được quyền xem
			String sql = " select khott_fk from NHANVIEN_KHOTT where nhanvien_fk ="
					+ this.userId
					+ " "
					+ " union select kho_fk from NHANVIEN_KHO where nhanvien_fk ="
					+ this.userId;

			rs = this.db.get(sql);
			List<String> dsKho = new ArrayList<String>();
			if (rs != null) {
				while (rs.next()) {
					dsKho.add(rs.getString("khott_fk"));
				}
				rs.close();
			}

			// check kho Biệt trữ.
			boolean check1 = this.ExistKho(dsKho, khoBietTru);
			// check kho Tồn trữ.
			boolean check2 = this.ExistKho(dsKho, khoTonTru);

			if (check1 == true && check2 == true) {
				this.khoChoXuLy = khoBietTru;
				this.KhoNhanId = khoTonTru;

			} else if (check1 == true) {
				this.khoChoXuLy = khoBietTru;
				this.KhoNhanId = khoBietTru;

			} else if (check2 == true) {
				this.khoChoXuLy = khoTonTru;
				this.KhoNhanId = khoTonTru;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
		}

	}

	@Override
	public String getKhonhanhangdat() {
		// TODO Auto-generated method stub
		return this.KhonhanTpdat;
	}

	@Override
	public void setKhonhanhangdat(String Khonhanhangdat) {
		// TODO Auto-generated method stub
		this.KhonhanTpdat = Khonhanhangdat;
	}

	@Override
	public ResultSet getRsKhonhanhangdat() {
		// TODO Auto-generated method stub
		return this.RsKhoNhanTpDat;
	}

	@Override
	public void setRsKhonhanhangdat(ResultSet rsKhoChoXuLy) {
		// TODO Auto-generated method stub
		this.RsKhoNhanTpDat = rsKhoChoXuLy;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getIdcongdoan() {
		return idcongdoan;
	}

	public void setIdcongdoan(String idcongdoan) {
		this.idcongdoan = idcongdoan;
	}

	public ResultSet getCongdoanRs() {
		return congdoanRs;
	}

	public String getLoaisanpham() {
		return loaisanpham;
	}

	public void setLoaisanpham(String loaisanpham) {
		this.loaisanpham = loaisanpham;
	}

	public ResultSet getPhepham_duphamRs() {
		return phepham_duphamRs;
	}

	public String getIdphepham_dupham() {
		return idphepham_dupham;
	}

	public void setIdphepham_dupham(String idphepham_dupham) {
		this.idphepham_dupham = idphepham_dupham;
	}

	@Override
	public String getIdCongdoannhan() {
		// TODO Auto-generated method stub
		return this.IdDoituongkhonhan;
	}

	@Override
	public void setIdCongdoannhan(String IdCongdoannhan) {
		// TODO Auto-generated method stub
		this.IdDoituongkhonhan = IdCongdoannhan;
	}

	@Override
	public String getIsKhoNhanCoDoiTuong() {
		// TODO Auto-generated method stub
		return this.KhoNhanCoDoituong;
	}

	@Override
	public void setIsKhoNhanCoDoiTuong(String isdoituong) {
		// TODO Auto-generated method stub
		this.KhoNhanCoDoituong = isdoituong;
	}

	@Override
	public ResultSet getDoituongNhanRs() {
		// TODO Auto-generated method stub
		return DoituongnhanRs;
	}

	@Override
	public void setDoituongNhanRs(ResultSet rs) {
		// TODO Auto-generated method stub
		DoituongnhanRs = rs;
	}

}
