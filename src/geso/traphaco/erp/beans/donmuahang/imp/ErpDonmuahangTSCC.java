package geso.traphaco.erp.beans.donmuahang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.donmuahang.IDonvi;
import geso.traphaco.erp.beans.donmuahang.IErpDonmuahangTSCC;
import geso.traphaco.erp.beans.donmuahang.IKho;
import geso.traphaco.erp.beans.donmuahang.INgaynhan;
import geso.traphaco.erp.beans.donmuahang.ISanPhamPhanBo;
import geso.traphaco.erp.beans.donmuahang.ISanpham;
import geso.traphaco.erp.beans.donmuahang.ITiente;
import geso.traphaco.erp.beans.donmuahang.ITrungTamChiPhi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpDonmuahangTSCC implements IErpDonmuahangTSCC {
	String congtyId;
	String userId;
	String ctyId;
	String cty;
	String id;
	Long tiGiaNguyenTe;

	public Long getTiGiaNguyenTe() {
		return this.tiGiaNguyenTe;
	}

	public void SetTiGiaNguyenTe(Long tiGiaNguyenTe) {
		this.tiGiaNguyenTe = tiGiaNguyenTe;
	}

	String hinhThucTT = "", diadiemgiaohang = "";

	public String getHinhThucTT() {
		return hinhThucTT;
	}

	public void setHinhThucTT(String hinhThucTT) {
		this.hinhThucTT = hinhThucTT;
	}

	// Them cot Nguon Goc Hang Hoa,TienTe_FK,mot don mua hang chi thuoc 1 loai
	// tien te
	String NguonGocHH;
	String MaLoaiHH;
	String TienTe_FK;
	String GhiChu;
	String ThueNhapKhau;
	String PhanTramThue;
	String TrungTamChiPhi_FK;
	float TyGiaQuyDoi;
	// Them cot Nguon Goc Hang Hoa,TienTe_FK
	String ngaymuahang, ETD = "", ETA = "";
	String dvthId;
	ResultSet dvthRs;
	String nccId;
	String nccTen;
	String nccLoai;
	String trangthai;
	String BVAT;
	String VAT;
	String AVAT;
	String lhhId;
	String sochungtu;
	String maDMH;

	String[] duyetIds;
	ResultSet lhhRs;
	List<ISanpham> spList;
	List<IDonvi> dvList;
	List<ITiente> tienteList;
	List<IKho> khoList;
	List<ITrungTamChiPhi> ListTTCP;

	String msg;
	String dungsai;
	String lspId;
	String isdontrahang;
	String maketoStock;
	String khoId;
	String canduyet;
	String quanlyCN;

	String sothamchieu;
	String[] ghichuArr;

	String[] cpkDiengiai;
	String[] cpkSotien;
	// Begin Thời Hạn Thanh Toán
	String[] ngayThanhToanArr;
	String[] ptThanhToanArr;
	String[] soTienThanhToanArr;
	
	// Thêm NCC
	ResultSet nccRs;
	String nhacungcapNK;
	
	
	public String getNhacungcapNK() {
		return nhacungcapNK;
	}

	public void setNhacungcapNK(String nhacungcapNK) {
		this.nhacungcapNK = nhacungcapNK;
	}

	public ResultSet getNccRs() {
		return nccRs;
	}

	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}

	public String[] getSoTienThanhToanArr() {
		return this.soTienThanhToanArr;
	}

	public void setSoTienThanhToanArr(String[] soTienThanhToanArr) {
		this.soTienThanhToanArr = soTienThanhToanArr;
	}
	
	public String[] getPtThanhToanArr() {
		return this.ptThanhToanArr;
	}

	public void setPtThanhToanArr(String[] ptThanhToanArr) {
		this.ptThanhToanArr = ptThanhToanArr;
	}

	public String[] getNgayThanhToanArr() {
		return this.ngayThanhToanArr;
	}

	public void setNgayThanhToanArr(String[] ngayThanhToanArr) {
		this.ngayThanhToanArr = ngayThanhToanArr;
	}

	// End Thời Hạn Thanh Toán
	String checkedNoiBo;
	String dutoanId;

	String loai;
	String isDuocPhanBo; // PO trong nước
	String loaiDMH_NK; // PO nhập khẩu
	String thoihanno;

	ResultSet kenhRs;
	String kenhId;

	String sohopdong;
	String soluong;
	String tennhank;
	String tennhasx;
	String ngayship;
	String ngaynhapkho;
	String dvchiutrachnhiem;
	ResultSet thttRs;// Thời hạn thanh toán RS

	public String getCheckedNoiBo() {
		return checkedNoiBo;
	}

	public void setCheckedNoiBo(String checkedNoiBo) {
		this.checkedNoiBo = checkedNoiBo;
	}

	dbutils db;

	private Utility util;

	public ErpDonmuahangTSCC() {
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.VAT = "10";
		this.sochungtu = "";
		this.AVAT = "";
		this.lhhId = "0";
		this.msg = "";
		this.dungsai = "10";
		this.NguonGocHH = "";
		this.MaLoaiHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.maDMH = "";

		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();

		this.checkedNoiBo = "0";
		this.lspId = "";
		// 0 Phiếu thanh toán - 1 ĐƠN MUA HÀNG
		this.isdontrahang = "1";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.quanlyCN = "1";
		this.sothamchieu = "";
		this.dutoanId = "";

		this.loai = "2";
		this.isDuocPhanBo = "0";
		this.loaiDMH_NK = "";
		this.thoihanno = "";

		this.kenhId = "";

		this.sohopdong = "";
		this.soluong = "";
		this.tennhank = "";
		this.tennhasx = "";
		this.ngayship = "";
		this.ngaynhapkho = "";
		this.dvchiutrachnhiem = "";
		this.db = new dbutils();
		this.util = new Utility();
	}

	public ErpDonmuahangTSCC(String id) {
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.sochungtu = "";
		this.VAT = "10";
		this.AVAT = "";
		this.lhhId = "0";
		this.msg = "";
		this.dungsai = "10";
		this.MaLoaiHH = "";
		this.NguonGocHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.maDMH = "";

		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();

		this.checkedNoiBo = "0";
		this.lspId = "";
		this.isdontrahang = "0";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.quanlyCN = "1";
		this.sothamchieu = "";
		this.dutoanId = "";

		this.loai = "2";
		this.isDuocPhanBo = "0";
		this.loaiDMH_NK = "";
		this.thoihanno = "";

		this.kenhId = "";

		this.sohopdong = "";
		this.soluong = "";
		this.tennhank = "";
		this.tennhasx = "";
		this.ngayship = "";
		this.ngaynhapkho = "";
		this.dvchiutrachnhiem = "";
		this.db = new dbutils();
		this.util = new Utility();
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getCty() {
		return this.cty;
	}

	public void setCty(String cty) {
		this.cty = cty;
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

	public String getNgaymuahang() {
		return this.ngaymuahang;
	}

	public void setNgaymuahang(String ngaymuahang) {
		this.ngaymuahang = ngaymuahang;
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

	public String getNCC() {
		return this.nccId;
	}

	public void setNCC(String ncc) {
		this.nccId = ncc;
	}

	public String getTongtienchuaVat() {
		return this.BVAT;
	}

	public void setTongtienchuaVat(String ttchuavat) {
		this.BVAT = ttchuavat;
	}

	public String getVat() {
		/*
		 * if (this.VAT.length() == 0) this.VAT = "10";
		 */
		return this.VAT;
	}

	public void setVat(String vat) {
		this.VAT = vat;
	}

	public String getTongtiensauVat() {
		return this.AVAT;
	}

	public void setTongtiensauVat(String ttsauvat) {
		this.AVAT = ttsauvat;
	}

	public String getLoaispId() {
		return this.lspId;
	}

	public void setLoaispId(String loaispid) {
		this.lspId = loaispid;
	}

	public ResultSet getLoaiList() {
		return this.lhhRs;
	}

	public void setLoaiList(ResultSet loaihhlist) {
		this.lhhRs = loaihhlist;
	}

	public List<ISanpham> getSpList() {
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList) {
		this.spList = spList;
	}

	public String[] getDuyetIds() {
		return this.duyetIds;
	}

	public void setDuyetIds(String[] duyetIds) {
		this.duyetIds = duyetIds;
	}

	public void createRs() {
		
		// bổ sung phần NCC. 2016-05-01
		this.nccRs = db.get(" select PK_SEQ, MA +'-'+ TEN as TEN from ERP_NHACUNGCAP " +
	            " where PK_SEQ in ( select distinct NHACUNGCAP_FK from " +
	            " ERP_DUYETNHACUNGCAP where TRANGTHAI = 1) and loaincc='100002'");
		
		
		// set tạm thời là usd
		if(this.TienTe_FK.trim().length() == 0){
			this.TienTe_FK = "100002";
		}
		String tgnt = "select isnull(tigiaquydoi,1) as tigianguyente from erp_tiente tt" + " left join("
				+ " 		select pk_seq,tiente_fk,tigiaquydoi from erp_tigia where pk_seq in "
				+ " 		(select max(pk_seq) from erp_tigia where trangthai='1'" + " 		group by tiente_fk)"
				+ " ) tg on tg.tiente_fk = tt.pk_seq" + " where TIENTE_FK = "+ this.TienTe_FK;
		System.out.println("Select TiGiaNguyenTe:" + tgnt);

		ResultSet rsTGNT = db.get(tgnt);
		long tiGiaNguyenTe = 0;
		try {
			if (rsTGNT.next()) {
				try {
					tiGiaNguyenTe = rsTGNT.getLong("tigianguyente");
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			rsTGNT.close();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		this.tiGiaNguyenTe = tiGiaNguyenTe;

		String sql = " select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'  ";
		// " and congty_fk = '" + this.congtyId + "' and pk_seq in " +
		// util.quyen_donvithuchien(this.userId);
		this.dvthRs = db.get(sql);

		this.lhhRs = db.get("Select pk_seq, ten, ma From Erp_LoaiSanPham where TRANGTHAI = '1' and congty_fk = "
				+ this.congtyId + " ");

		this.kenhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1'");

		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1' ");
		this.dvList.clear();
		if (donvi != null) {
			try {
				while (donvi.next()) {
					this.dvList.add(new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			} catch (SQLException e) {
			}
		}

		String query = "select distinct PK_SEQ, TEN from ERP_KHOTT a inner join ERP_KHOTT_LOAISANPHAM b on a.PK_SEQ = b.khott_fk "+ 
						"where b.loaisanpham_fk in (select PK_SEQ from ERP_LOAISANPHAM where PK_SEQ in (100001, 100010)) and a.trangthai = 1 ";

		System.out.println("___KHOI TAO KHO: " + query);
		ResultSet khoTT = db.get(query);
		this.khoList.clear();
		if (khoTT != null) {
			try {
				while (khoTT.next()) {
					this.khoList.add(new Kho(khoTT.getString("pk_seq"), khoTT.getString("ten")));
				}
				khoTT.close();
			} catch (SQLException e) {
			}
		}

		query = " select distinct ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI "
				+ " from ERP_TIENTE inner join ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK "
				+ " where ERP_TIENTE.Trangthai = '1' and ERP_TIGIA.Trangthai = '1' "
				+ " and ERP_TIGIA.CONGTY_FK = "+ this.congtyId 
				+ " order by ERP_TIENTE.PK_SEQ ASC";

		ResultSet tiente = db.get(query);
		this.tienteList.clear();
		if (tiente != null) {
			try {
				while (tiente.next()) {
					this.tienteList.add(new Tiente(tiente.getString("pk_seq"), tiente.getString("ma"),
							tiente.getString("TIGIAQUYDOI")));
				}
				tiente.close();
			} catch (SQLException e) {
			}
		}

		// ----- kiểm tra có trong khu chế xuất để mặc định tiền tệ USD -----//
		if (this.nccId != null && this.nccId.length() > 0) {
			String query1 = " select isnull(is_khuchexuat,0) as is_khuchexuat from erp_nhacungcap where pk_seq="
					+ this.nccId + " ";
			ResultSet rs1 = db.get(query1);
			if (rs1 != null) {
				try {
					String tmp = "";
					while (rs1.next()) {
						tmp = rs1.getString("is_khuchexuat");
					}

					if (tmp.equals("1")) {
						this.TienTe_FK = "100001";
					}

				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		}

	}

	public void init() {

		NumberFormat formatter = new DecimalFormat("#,###,###.#####");

		String query = " select a.PK_SEQ as dmhId, isnull(a.HTTT,'') HTTT, isnull(a.NguonGocHH ,'') as NguonGocHH, isnull(a.TienTe_FK, '100000') as TienTe_FK, \n"
				+ " c.PREFIX + a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as SOCHUNGTU, a.NGAYMUA, isnull(a.GhiChu,'') as GhiChu, a.sohopdong, a.soluong, a.tennhanhapkhau, a.tennhasanxuat, a.ngayship, a.ngaynhapkho, a.dvchiutrachnhiem, \n"
				+ " a.DONVITHUCHIEN_FK as dvthId, a.LOAIHANGHOA_FK, a.LOAISANPHAM_FK, b.loainhacungcap_fk as nccLoai, b.pk_seq as nccId, b.ma + ', ' + b.TEN as nccTen, isnull(a.ETD, '') as ETD, isnull(a.ETA, '') as ETA, \n"
				+ " isnull(a.TONGTIENAVAT, '0') as TONGTIENAVAT, isnull(a.VAT, '0') as VAT, isnull(a.TONGTIENBVAT, 0) as TONGTIENBVAT, isnull(a.Dungsai, '0') as dungsai, a.TRANGTHAI, b.loainhacungcap_fk, b.khoNL_Nhan_GC, a.quanlycongno, isnull(sothamchieu, '') as sothamchieu, \n"
				+ " isnull(b.noibo,0) as noibo, a.DTVT_FK, a.LOAI, ISNULL(a.ISHOPDONG,0) as ishopdong, a.KENH_FK \n"
				+ " , isnull(b.noibo,0) as noibo, isnull(a.diadiemgiaohang, '') as diadiemgiaohang, a.sopo, isnull(a.thoihanno,0) thoihanno, isduocphanbo \n"
				+ "  from ERP_MUAHANG a \n" + "  left join ERP_NHACUNGCAP b on a.NHACUNGCAP_FK = b.PK_SEQ \n"
				+ "  inner join ERP_DONVITHUCHIEN c on c.PK_SEQ = a.DONVITHUCHIEN_FK  \n" + " where a.pk_seq = '"
				+ this.id + "' ";

		System.out.println("Don Mua Hang : " + query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.maDMH = rs.getString("sopo");
					this.id = rs.getString("dmhId");
					this.ngaymuahang = rs.getString("ngaymua");
					this.dvthId = rs.getString("dvthId");
					this.nccId = rs.getString("nccId");
					this.nccTen = rs.getString("nccTen");
					this.nccLoai = rs.getString("nccLoai");
					this.lhhId = rs.getString("LOAIHANGHOA_FK");
					this.lspId = rs.getString("LOAISANPHAM_FK") == null ? "" : rs.getString("LOAISANPHAM_FK");
					this.BVAT = formatter.format(rs.getDouble("TONGTIENBVAT"));
					this.VAT = formatter.format(rs.getDouble("VAT"));
					this.AVAT = formatter.format(rs.getDouble("TONGTIENAVAT"));
					this.trangthai = rs.getString("trangthai");
					this.dungsai = rs.getString("dungsai");
					this.sochungtu = rs.getString("SOCHUNGTU");
					this.NguonGocHH = rs.getString("NguonGocHH");
					this.TienTe_FK = rs.getString("TienTe_FK");
					this.GhiChu = rs.getString("GhiChu");
					this.quanlyCN = rs.getString("quanlycongno");
					this.sothamchieu = rs.getString("SOTHAMCHIEU");
					this.hinhThucTT = rs.getString("HTTT");
					this.checkedNoiBo = rs.getString("noibo");
					this.diadiemgiaohang = rs.getString("diadiemgiaohang").trim();
					this.dutoanId = rs.getString("DTVT_FK") == null ? "" : rs.getString("DTVT_FK");
					this.thoihanno = rs.getString("thoihanno");
					this.isDuocPhanBo = rs.getString("isduocphanbo") == null ? "0" : rs.getString("isduocphanbo");
					this.kenhId = rs.getString("KENH_FK") == null ? "" : rs.getString("KENH_FK");

					this.sohopdong = rs.getString("sohopdong");
					this.soluong = rs.getString("soluong");
					this.tennhank = rs.getString("tennhanhapkhau");
					this.tennhasx = rs.getString("tennhasanxuat");
					this.ngayship = rs.getString("ngayship");
					this.ngaynhapkho = rs.getString("ngaynhapkho");
					this.dvchiutrachnhiem = rs.getString("dvchiutrachnhiem");
					if (this.GhiChu.trim().length() > 0) {
						this.ghichuArr = this.GhiChu.split("__");
					}

					/*
					 * if(this.NguonGocHH.equals("NN")) { this.VAT = "0";
					 * this.AVAT = this.BVAT; }
					 */

					this.loai = rs.getString("loai");
					
					// nếu là nhập khẩu thì cần id nhà cung cấp NK
					if(this.loai.equals("0")){
						this.nhacungcapNK = this.nccId;
					}
					
					if (rs.getString("ishopdong").equals("1")) {
						this.loaiDMH_NK = "0";
					} else {
						this.loaiDMH_NK = "1";
					}

					this.ETD = rs.getString("ETD");
					this.ETA = rs.getString("ETA");
				}
				rs.close();
			} catch (Exception e) {
				System.out.println("__Exception: " + e.getMessage());
			}
		}

		// INIT CHI PHI KHAC
		query = "select diengiai, sotien from ERP_MUAHANG_CPKHAC where muahang_fk = '" + this.id + "'  ";

		ResultSet rsCPK = db.get(query);

		String diengiaiCPK = "";
		String sotienCPK = "";
		if (rsCPK != null) {
			try {
				while (rsCPK.next()) {
					diengiaiCPK += rsCPK.getString("diengiai") + "__";
					sotienCPK += rsCPK.getString("sotien") + "__";
				}
				rsCPK.close();

				if (diengiaiCPK.trim().length() > 0) {

					diengiaiCPK = diengiaiCPK.substring(0, diengiaiCPK.length() - 2);
					this.cpkDiengiai = diengiaiCPK.split("__");

					sotienCPK = sotienCPK.substring(0, sotienCPK.length() - 2);
					this.cpkSotien = sotienCPK.split("__");

				} else {

					diengiaiCPK = " __";
					diengiaiCPK = diengiaiCPK.substring(0, diengiaiCPK.length() - 2);
					this.cpkDiengiai = diengiaiCPK.split("__");

					sotienCPK = " __";
					sotienCPK = sotienCPK.substring(0, sotienCPK.length() - 2);
					this.cpkSotien = sotienCPK.split("__");
				}

			} catch (Exception e) {
				System.out.println("EXCEPTION CPK: " + e.getMessage());
			}

		}
		String thtt = "select isnull(ngaythanhtoan,'') as ngaythanhtoan , isnull(sotien,0) as sotien, isnull(phantramthanhtoan,0) as ptthanhtoan "
				+ " from erp_thoihanthanhtoan where muahang_fk =" + this.id;
		System.out.println("Thoi Han Thanh Toan:" + thtt);
		this.thttRs = db.get(thtt);
		
		
		
		this.createRs();
		this.createSanpham();
	}

	private void createSanpham() {

		String query = " select  isnull(b.pk_seq,0) as spid, \n" +
		// " isnull( case when ( len(ltrim(rtrim(b.machitiet))) <= 0 or (
		// b.machitiet is null ) ) then b.ma else b.machitiet end, '' ) as spMa,
		// " +
				" isnull(b.ma, '' ) as spMa, \n" + " isnull(b.quycach,'NA') as quycach , \n" +

				" isnull(a.diengiai, b.ten )   as spTen, \n" + " b.ten    as spTen2, 'NA' as spNh, \n"
				+ " isnull(tscd.pk_seq,0) as tscdid, isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, 'NA' as nstNh,  \n"
				+ " isnull(ccdc.pk_seq,0) as ccdcid, isnull(ccdc.ma, '') as ccdcMa, isnull(a.diengiai, ccdc.DIENGIAI) as ccdcTen,  \n"
				+ " isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh, \n"
				+ " isnull(a.donvi, '') as donvi, a.soluong, isnull(a.dongia, '0') as dongia, isnull(a.SOLUONG_NEW, '0') as soluong_new, isnull(a.dongia_NEW, '0') as dongia_NEW, isnull(a.thuexuat, '0') as thuexuat, a.vat, \n"
				+ " isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, isnull(a.thuenhapkhau, '0') as thuenhapkhau, a.khott_fk, dungsai,  \n"
				+ " isnull(muanguyenlieudukien_fk, 0) as mnlId, \n" + " isnull(a.tenhd, '') as tenhd ,isnull(a.IS_KHONGTHUE,'0') IS_KHONGTHUE \n" + " from ( \n"
				+ "	select AVG(PK_SEQ) AS PK_SEQ, MUAHANG_FK, SANPHAM_FK, CHIPHI_FK, TAISAN_FK, DIENGIAI, SUM(SOLUONG) AS SOLUONG, DONGIA, SUM(SOLUONG_NEW) AS SOLUONG_NEW, DONGIA_NEW, TIENTE_FK, SUM(THANHTIEN) AS THANHTIEN, \n"
				+ "	PhanTramThue, ThueNhapKhau, DONGIAVIET, SUM(THANHTIENVIET) AS THANHTIENVIET, DONVI, KHOTT_FK, DUNGSAI, MUANGUYENLIEUDUKIEN_FK, CCDC_FK, TENHD, HOADONNCC_FK, thuexuat, vat,IS_KHONGTHUE \n"
				+ "	from ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id + "' \n"
				+ "	GROUP BY MUAHANG_FK, SANPHAM_FK, CHIPHI_FK, TAISAN_FK, DIENGIAI, DONGIA, DONGIA_NEW, TIENTE_FK, \n"
				+ "	PhanTramThue, ThueNhapKhau, DONGIAVIET, DONVI, KHOTT_FK, DUNGSAI, MUANGUYENLIEUDUKIEN_FK, CCDC_FK, TENHD, HOADONNCC_FK, thuexuat, vat,IS_KHONGTHUE \n"
				+ ") a left join \n" + " ERP_SANPHAM b on a.sanpham_fk = b.pk_seq   \n"
				+ " left join	ERP_MASCLON tscd on a.taisan_fk = tscd.pk_seq  \n"
				+ " left join	erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  \n"
				+ " left join erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq \n"
				+ " left join  erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq  \n" + " where muahang_fk = '"
				+ this.id + "' order by a.pk_seq asc ";

		System.out.println(" San pham init: " + query);
		ResultSet spRs = db.get(query);
		List<ISanpham> spList = new ArrayList<ISanpham>();

		NumberFormat formatter = new DecimalFormat("#,###,###.#####");
		if (spRs != null) {
			try {
				ISanpham sp = null;
				while (spRs.next()) {
					sp = new Sanpham();
					System.out.println("[lhh] " + this.lhhId);
					if (this.lhhId.equals("0")) {
						sp.setPK_SEQ(spRs.getString("spid"));
						sp.setMasanpham(spRs.getString("spMa"));
						sp.setTensanpham(spRs.getString("spTen2"));
						sp.setNhomhang(spRs.getString("spNh"));
						sp.setTenXHD(spRs.getString("spTen2"));
						sp.setMNLId(spRs.getString("mnlId"));

						// Ngày nhận
						String subquery = "select SOLUONG, ISNULL(NGAYNHAN,'') NGAYNHAN from ERP_MUAHANG_SP WHERE MUAHANG_FK = '"
								+ this.id + "' and SANPHAM_FK = '" + sp.getPK_SEQ() + "' ORDER BY PK_SEQ ASC";
						ResultSet subRs = db.get(subquery);
						List<INgaynhan> nnList = new ArrayList<INgaynhan>();
						if (nnList != null) {
							while (subRs.next()) {
								nnList.add(new Ngaynhan(subRs.getString("NGAYNHAN"), subRs.getString("SOLUONG")));
							}
							subRs.close();
						}
						sp.setNgaynhan(nnList);
					} else {
						if (this.lhhId.equals("1")) // Tai san co dinh
						{
							sp.setPK_SEQ(spRs.getString("tscdid"));
							sp.setMasanpham(spRs.getString("tscdMa"));
							sp.setTensanpham(spRs.getString("tscdTen"));
							sp.setNhomhang(spRs.getString("nstNh"));
							sp.setTenXHD(spRs.getString("tscdTen"));

							String subquery = "select SOLUONG, ISNULL(NGAYNHAN,'') NGAYNHAN from ERP_MUAHANG_SP WHERE MUAHANG_FK = '"
									+ this.id + "' and TAISAN_FK = '" + sp.getPK_SEQ() + "' ORDER BY PK_SEQ ASC";
							ResultSet subRs = db.get(subquery);
							List<INgaynhan> nnList = new ArrayList<INgaynhan>();
							if (nnList != null) {
								while (subRs.next()) {
									nnList.add(new Ngaynhan(subRs.getString("NGAYNHAN"), subRs.getString("SOLUONG")));
								}
								subRs.close();
							}
							sp.setNgaynhan(nnList);
						} else {
							if (this.lhhId.equals("3")) // Cong cu dung cu
							{
								sp.setPK_SEQ(spRs.getString("ccdcId"));
								sp.setMasanpham(spRs.getString("ccdcMa"));
								sp.setTensanpham(spRs.getString("ccdcTen"));
								sp.setNhomhang("");
								sp.setTenXHD(spRs.getString("ccdcTen"));

								String subquery = "select SOLUONG, ISNULL(NGAYNHAN,'') NGAYNHAN from ERP_MUAHANG_SP WHERE MUAHANG_FK = '"
										+ this.id + "' and CCDC_FK = '" + sp.getPK_SEQ() + "' ORDER BY PK_SEQ ASC";
								ResultSet subRs = db.get(subquery);
								List<INgaynhan> nnList = new ArrayList<INgaynhan>();
								if (nnList != null) {
									while (subRs.next()) {
										nnList.add(
												new Ngaynhan(subRs.getString("NGAYNHAN"), subRs.getString("SOLUONG")));
									}
									subRs.close();
								}
								sp.setNgaynhan(nnList);
							} else {
								sp.setPK_SEQ(spRs.getString("ncpid"));
								sp.setMasanpham(spRs.getString("ncpMa"));
								sp.setTensanpham(spRs.getString("ncpTen"));
								sp.setNhomhang(spRs.getString("ncpNh"));
								sp.setTenXHD(spRs.getString("ncpTen"));

								String subquery = "select SOLUONG, ISNULL(NGAYNHAN,'') NGAYNHAN from ERP_MUAHANG_SP WHERE MUAHANG_FK = '"
										+ this.id + "' and CHIPHI_FK = '" + sp.getPK_SEQ() + "' ORDER BY PK_SEQ ASC";
								ResultSet subRs = db.get(subquery);
								List<INgaynhan> nnList = new ArrayList<INgaynhan>();
								if (nnList != null) {
									while (subRs.next()) {
										nnList.add(
												new Ngaynhan(subRs.getString("NGAYNHAN"), subRs.getString("SOLUONG")));
									}
									subRs.close();
								}
								sp.setNgaynhan(nnList);

								// Sản phẩm phân bổ
								String sppb = " select distinct sp.PK_SEQ, sp.MA, sp.TEN , cast(sp.PK_SEQ as nvarchar(50)) as CHON "
										+ " from ERP_MUAHANG_SP_PHANBO mh inner join ERP_SANPHAM sp on mh.SANPHAMPB_FK = sp.PK_SEQ "
										+ " where MUAHANG_FK = '" + this.id + "' and CHIPHI_FK = '" + sp.getPK_SEQ()
										+ "' " + " UNION ALL " + " select PK_SEQ, MA, TEN, '' as CHON "
										+ " from ERP_SANPHAM "
										+ " where TRANGTHAI = 1 AND PK_SEQ not in (select SANPHAMPB_FK from ERP_MUAHANG_SP_PHANBO where MUAHANG_FK = "
										+ id + " AND CHIPHI_FK = " + sp.getPK_SEQ() + " ) "
										+ " order by CHON desc, PK_SEQ asc";
								System.out.println("Cau lay SP phan bo " + sppb);
								ResultSet sppbRs = db.get(sppb);
								List<ISanPhamPhanBo> sppbList = new ArrayList<ISanPhamPhanBo>();
								if (sppbList != null) {
									while (sppbRs.next()) {
										sppbList.add(new SanPhamPhanBo(sppbRs.getString("PK_SEQ"),
												sppbRs.getString("MA"), sppbRs.getString("TEN"),
												sppbRs.getString("CHON") == null ? "" : sppbRs.getString("CHON")));
									}
									sppbRs.close();
								}

								sp.setSanphamPB(sppbList);
							}
						}
					}

					sp.setSoluongOLD(formatter.format(spRs.getDouble("soluong")));
					sp.setSoluong_bk(formatter.format(spRs.getDouble("soluong")));
					sp.setSoluong(formatter.format(spRs.getDouble("soluong_new")));
					sp.setDonvitinh(spRs.getString("donvi"));
					sp.setDongiaOLD(formatter.format(spRs.getDouble("dongia")));
					sp.setDongia(formatter.format(spRs.getDouble("dongia_new")));
					sp.setThuexuat(formatter.format(spRs.getDouble("thuexuat")));
					if (this.loai.equals("2"))
						sp.setThuexuat(formatter.format(spRs.getDouble("vat")));
					sp.setThanhtien(formatter.format(spRs.getDouble("thanhtien")));

					// sp.setNgaynhan(spRs.getString("ngaynhan"));

					if (spRs.getString("khott_fk") != null)
						sp.setKhonhan(spRs.getString("khott_fk"));
					sp.setThueNhapKhau(formatter.format(spRs.getDouble("thuenhapkhau")));
					sp.setPhanTramThue(formatter.format(spRs.getDouble("phantramthue")));

					sp.setTenHD(spRs.getString("tenhd"));
					sp.setIsmienthue(spRs.getString("IS_KHONGTHUE"));
					spList.add(sp);
				}
				spRs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Khong the tao san Pham" + e.getMessage());
			}
		}

		this.spList = spList;
		System.out.println("size : " + this.spList.size());
	}

	public void CreatePOfromPR(ResultSet rs, String mnlId) {
		try {
			if (rs.next()) {
				this.db.getConnection().setAutoCommit(false);

				String query = "Insert into Erp_MuaHang(NgayMua, DonViThucHien_FK, NhaCungCap_FK, "
						+ "LoaiHangHoa_FK, LoaiSanPham_FK, TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, "
						+ "NgaySua, NguoiTao, NguoiSua, congty_fk, HTTT) " + "Values('" + this.getDateTime()
						+ "','100003', null, '0', '100002', '0', '0' , '0' , '0', '0', '" + this.getDateTime() + "', "
						+ "'" + this.getDateTime() + "'," + this.userId + "," + this.userId + ", '" + this.congtyId
						+ "',N'" + this.hinhThucTT + "')";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang: " + query;
					this.db.getConnection().rollback();
				}

				query = "select SCOPE_IDENTITY() as dmhId";
				ResultSet rsDmh = db.get(query);
				if (rsDmh.next()) {
					this.id = rsDmh.getString("dmhId");
					rsDmh.close();
				}

				query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, diengiai, donvi, soluong, muanguyenlieudukien_fk) "
						+ " values(" + this.id + ", " + rs.getString("SANPHAM_FK") + ", N'" + rs.getString("TEN")
						+ "', N'" + rs.getString("DONVI") + "', " + "'" + rs.getString("SOLUONG") + "', '" + mnlId
						+ "')";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang: " + query;
					this.db.getConnection().rollback();
				}

				// query = "update ERP_MUANGUYENLIEUDUKIEN SET DADATHANG =
				// DADATHANG + " + rs.getString("SOLUONG") + " WHERE PK_SEQ = "
				// + mnlId + " ";

				/*
				 * query = "UPDATE ERP_MUANGUYENLIEUDUKIEN  " +
				 * "SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) "
				 * + "FROM " + "( " +
				 * "	SELECT SUM(SOLUONG) AS SOLUONG, MUANGUYENLIEUDUKIEN_FK "
				 * + "	FROM ERP_MUAHANG_SP WHERE MUANGUYENLIEUDUKIEN_FK = " +
				 * mnlId + " " + "	GROUP BY MUANGUYENLIEUDUKIEN_FK " +
				 * ")A  WHERE ERP_MUANGUYENLIEUDUKIEN.PK_SEQ = " + mnlId + " " ;
				 */

				// Cap nhat DADATHANG trong ERP_MUANGUYENLIEUDUKIEN
				query = "UPDATE ERP_MUANGUYENLIEUDUKIEN	"
						+ "SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " + "FROM " + "( "
						+ "	SELECT SUM(SOLUONG) AS SOLUONG, SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4) AS NAM,	"
						+ "	CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2)) AS THANG	" + "	FROM ERP_MUAHANG_SP "
						+ "	WHERE SANPHAM_FK IS NOT NULL	"
						+ "	GROUP BY SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4),CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2))	"
						+ ")A  " + "WHERE ERP_MUANGUYENLIEUDUKIEN.NAM = A.NAM	"
						+ "AND ERP_MUANGUYENLIEUDUKIEN.THANG = A.THANG	"
						+ "AND ERP_MUANGUYENLIEUDUKIEN.SANPHAM_FK = A.SANPHAM_FK ";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang: " + query;
					this.db.getConnection().rollback();
				}

				/*
				 * boolean vuotNganSach = false; //Chen co che duyet // insert
				 * nguoi duyet PO query =
				 * "SELECT	DUYETCHIPHI.CHUCDANH_FK, DUYETCHIPHI.QUYETDINH, DUYETCHIPHI.THUTU "
				 * + "FROM ERP_MUAHANG MUAHANG " +
				 * "INNER JOIN ERP_CHINHSACHDUYETCHIPHI DUYETCHIPHI ON DUYETCHIPHI.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				 * +
				 * "INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = DUYETCHIPHI.KHOANGCHIPHI_FK "
				 * +
				 * "INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETCHIPHI.CHUCDANH_FK "
				 * +
				 * "WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) "
				 * + "AND MUAHANG.PK_SEQ = '" + this.id +
				 * "' ORDER BY DUYETCHIPHI.THUTU" ;
				 * 
				 * System.out.println("3.Duyet PO:" + query);
				 * 
				 * rs = db.get(query);
				 * 
				 * boolean dacoTongGiamDoc = false; int thutu = 0;
				 * 
				 * while (rs.next()) {
				 * if(rs.getString("CHUCDANH_FK").equals("100003"))
				 * dacoTongGiamDoc = true;
				 * 
				 * thutu = Integer.parseInt(rs.getString("THUTU"));
				 * 
				 * query =
				 * "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) "
				 * + "VALUES('"+ this.id +"', '" + rs.getString("CHUCDANH_FK") +
				 * "', '0','" + rs.getString("QUYETDINH")+ "','" +
				 * rs.getString("THUTU") + "') ";
				 * 
				 * System.out.println("4. Insert Duyet PO:" + query); if
				 * (!db.update(query)) { this.msg =
				 * "Khong the them nguoi duyet cho PO: " + query;
				 * db.getConnection().rollback(); } }
				 * 
				 * if (rs != null) rs.close();
				 * 
				 * query = "Update ERP_MUAHANG set VUOTNGANSACH = '" +
				 * (vuotNganSach == true ? "1" : "0") + "' where pk_seq = '" +
				 * this.id + "' "; if (!db.update(query)) { this.msg =
				 * "Khong the cap nhat ERP_MUAHANG: " + query;
				 * db.getConnection().rollback(); }
				 */

				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
				this.init();

			}
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
		}
	}

	public boolean createDmh() {
		// Kiem tra moi them vao
		String query = "";

		if (this.loai.equals("0")) // Mua hàng nhập khẩu
		{
			if (this.loaiDMH_NK.trim().length() <= 0) {
				this.msg = "Vui lòng loại đơn Hợp đồng/ Đơn mua hàng";
				return false;
			}
			if (this.loaiDMH_NK.equals("0") && this.thoihanno.trim().length() <= 0) {
				this.msg = "Vui lòng thời hạn nợ cho hợp đồng này";
				return false;
			}
			this.nccId = "NULL";
		} else {
			if (this.nccId.trim().length() <= 0) {
				this.msg = "Vui lòng chọn nhà cung cấp.";
				return false;
			}
		}

		// Check kho
//		String loainccId = "";
		/*
		 * if(this.nccTen.trim().length() > 0 && this.lspId.trim().length() > 0)
		 * { if(this.lspId.equals("100009")) { query =
		 * " select LOAINHACUNGCAP_FK, KhoNL_Nhan_GC from ERP_NHACUNGCAP where PK_SEQ = '"
		 * + this.nccId + "'"; ResultSet rs = db.get(query); if(rs != null) {
		 * try { if(rs.next()) { loainccId = rs.getString("LOAINHACUNGCAP_FK");
		 * if(loainccId.equals("100003")) { this.khoId =
		 * rs.getString("KhoNL_Nhan_GC") == null ? "" :
		 * rs.getString("KhoNL_Nhan_GC"); } } rs.close(); } catch (Exception e)
		 * { } } }
		 * 
		 * }
		 */

		// Neu NCC o du lieu nen khong thiet lap QL Cong no, ma o don mua hang
		// chon thiet lap cong no thi bao loi
		if (!this.loai.equals("0") && this.quanlyCN.equals("1") && this.nccId.trim().length() > 0) {
			query = " select quanlycongno from Erp_NhaCungCap where pk_seq = '" + this.nccId + "' ";
			ResultSet rsCongNo = db.get(query);
			try {
				if (rsCongNo.next()) {
					if (rsCongNo.getString("quanlycongno").equals("0")) {
						rsCongNo.close();
						this.msg = "Nhà cung cấp trong dữ liệu nền không thiết lập quản lý công nợ. Vui lòng kiểm tra lại dữ liệu nền";
						return false;
					}
				}
				rsCongNo.close();
			} catch (SQLException e) {
			}
		}

		if (this.spList.size() <= 0) {
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		} else {
			if (this.loai.equals("2")) {
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);

					System.out.println("Kho Nhan: " + sp.getKhonhan());

					if (sp.getNgaynhan().size() == 0) {
						this.msg = "Vui lòng nhập thông tin ngày nhận trong danh sách sản phẩm.";
						return false;
					} else {
						if (sp.getSoluong() == null || sp.getSoluong().length() == 0
								|| Double.parseDouble(sp.getSoluong()) == 0) {
							this.msg = "Vui lòng nhập số lượng sản phẩm.";
							return false;
						}
						List<INgaynhan> nn = sp.getNgaynhan();
						double sl = Double.parseDouble(sp.getSoluong());
						double tmpSL = 0;
						for (int j = 0; j < nn.size(); j++) {
							if (nn.get(j).getSoluong() == null || nn.get(j).getSoluong().length() == 0) {
								this.msg = "Vui lòng nhập số lượng sản phẩm, ngày nhận " + nn.get(j).getNgay();
								return false;
							}
							tmpSL += Double.parseDouble(nn.get(j).getSoluong());
						}
						if (sl != tmpSL) {
							this.msg = "Tổng số lượng trong ngày nhận không khớp với số lượng nhập.";
							return false;
						}
					}

					
					//cho nay la luc moi sp nhan 1 kho
					/*if (this.lhhId.equals("0")) {
						if (sp.getKhonhan().trim().length() <= 0) {
							this.msg = "Vui lòng nhập kho nhận hàng trong danh sách sản phẩm.";
							return false;
						}

						// Check kho nhan va sanpham : chỗ này nếu không có thì lên dữ liệu nền khai báo dùm rồi làm 
						int count = 0;
						query = "select count(*) as sodong from ERP_KHOTT_SANPHAM where sanpham_fk = '" + sp.getPK_SEQ()
								+ "' and khott_fk = '" + sp.getKhonhan().trim() + "' ";
						ResultSet rsCheckKho = db.get(query);
						try {
							if (rsCheckKho.next()) {
								count = rsCheckKho.getInt("sodong");
							}
							rsCheckKho.close();
						} catch (Exception e) {
							e.printStackTrace();
						}

						if (count <= 0) {
							this.msg = "Kho nhận của sản phẩm ( " + sp.getMasanpham()
									+ " ) không đúng trong dữ liệu nền KHO - SANPHAM. Vui lòng kiểm tra lại.";
							return false;
						}
					}*/

				}
			}
		}

		if (this.TienTe_FK.trim().length() <= 0) {
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}

		if (!this.loai.equals("0") && this.nccTen.trim().length() <= 0) {
			this.msg = "Vui lòng nhập tên nhà cung cấp mua hàng";
			return false;
		}

		try {
			String ngaytao = getDateTime();
			db.getConnection().setAutoCommit(false);

			String loaisanpham = "NULL";

			// Dùng trong cơ chế duyệt
			boolean isNK = false;
			boolean isKhangSinh = false;

			String ghichu = "";
			if (this.ghichuArr != null) {
				for (int i = 0; i < this.ghichuArr.length; i++) {
					ghichu += this.ghichuArr[i] + "__";
				}
				if (ghichu.trim().length() > 0) {
					this.GhiChu = ghichu;
				}
			}

			// CAP NHAT SO PO, MA TU DONG TANG
			String nam = this.ngaymuahang.substring(0, 4);
			String thang = this.ngaymuahang.substring(5, 7);

			query = " SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "
					+ " WHERE PK_SEQ =" + this.dvthId + " ) AS PREFIX   "
					+ " FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = " + nam
					+ " and     DMH.DONVITHUCHIEN_FK=" + this.dvthId;
			System.out.println("Du lieu po sai  :" + query);
			String soPO = "";
			int sotutang_theonam = 0;
			ResultSet rsPO = db.get(query);
			if (rsPO.next()) {
				sotutang_theonam = (rsPO.getInt("maxSTT") + 1);
				String prefix = rsPO.getString("PREFIX");
				String namPO = this.ngaymuahang.substring(2, 4);
				String chuoiso = ("0000" + Integer.toString(sotutang_theonam)).substring(
						("0000" + Integer.toString(sotutang_theonam)).length() - 4,
						("0000" + Integer.toString(sotutang_theonam)).length());

				soPO = prefix + "-" + chuoiso + "/" + thang + "/" + namPO;

			}
			rsPO.close();

			// INSERT NGÀY ĐẾN HẠN TT NẾU PO LÀ PO NỘI BỘ (DÙNG TRONG PHIẾU CHI)
			String ngaydenhantt = "";
			if (this.checkedNoiBo.equals("1")) {
				// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày
				// hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
				query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '" + this.ngaymuahang
						+ "')),120 ) as ngaydenhantt " + "FROM ERP_NHACUNGCAP " + "WHERE PK_SEQ = '" + this.nccId + "'";
				ResultSet rsThoihanno = db.get(query);
				if (rsThoihanno != null) {
					while (rsThoihanno.next()) {
						ngaydenhantt = rsThoihanno.getString("ngaydenhantt");
					}
					rsThoihanno.close();
				}
			}

			int ishopdong = 0;
			if (this.loaiDMH_NK.equals("0"))
				ishopdong = 1;

			// nếu là nhập khẩu ( loại =0 ) thì NCC chính là NhacungcapNK
			if(this.loai.equals("0")){
				this.nccId = this.nhacungcapNK;
			}
			
			query = " Insert into Erp_MuaHang(THOIHANNO, LOAI, ISHOPDONG, NgayMua, DonViThucHien_FK, NhaCungCap_FK, " +
					"LoaiHangHoa_FK, LoaiSanPham_FK, TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, " +
					"NgaySua, NguoiTao, NguoiSua, NguonGocHH, TienTe_FK, GhiChu, TyGiaQuyDoi, type, congty_fk, " +
					"quanlycongno, SOTHAMCHIEU, ETD, ETA,HTTT, SOPO, SOTUTANG_THEONAM, DIADIEMGIAOHANG, NGAYDENHANTT, " +
					"SOHOPDONG, SOLUONG, TENNHANHAPKHAU, TENNHASANXUAT, NGAYSHIP, NGAYNHAPKHO, dvchiutrachnhiem)"
					+ " Values( " + this.thoihanno + "," + this.loai + ", " + ishopdong + " , '" + this.ngaymuahang
					+ "','" + this.dvthId + "'," + this.nccId + "," + this.lhhId + ", " + loaisanpham + ", " + this.AVAT
					+ "," + this.VAT + ", " + this.BVAT + ", " + this.dungsai + ", '0', '" + ngaytao + "', '" + ngaytao
					+ "'," + this.userId + "," + this.userId + ",'" + this.NguonGocHH + "'," + this.TienTe_FK + ",N'"
					+ this.GhiChu.replaceAll("'", "''") + "'," + "" + this.TyGiaQuyDoi + ", '" + this.isdontrahang
					+ "', '" + this.congtyId + "', '" + this.quanlyCN + "', N'" + this.sothamchieu + "', '" + this.ETD
					+ "', '" + this.ETA + "',N'" + this.hinhThucTT + "', '" + soPO + "', '" + sotutang_theonam + "', N'"
					+ this.diadiemgiaohang + "', '" + ngaydenhantt + "'," + "'" + this.sohopdong + "', '" + this.soluong
					+ "', N'" + this.tennhank + "', N'" + this.tennhasx + "', '" + this.ngayship + "', '"
					+ this.ngaynhapkho + "', '" + this.dvchiutrachnhiem + "')";
			System.out.println("luu erp_muahang " + query);
			// System.out.println("Insert into Erp_MuaHang " + query);
			if (!db.update(query)) {
				this.msg = "Khong the tao moi Mua hang: " + query;
				System.out.println("2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}

			String dmhCurrent = "";
			query = "select SCOPE_IDENTITY() as dmhId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next()) {
				dmhCurrent = rsDmh.getString("dmhId");
				this.id = dmhCurrent;
				rsDmh.close();
			}
			//Begin - Thời Hạn Thanh Toán - Dương
			if (this.ngayThanhToanArr != null) {
				for (int i = 0; i < this.ngayThanhToanArr.length; i++) {
					if (this.ngayThanhToanArr[i] != null && this.ngayThanhToanArr[i] != "") {
						String insTHTT = "insert into erp_thoihanthanhtoan values (" + this.id + ",'"
								+ this.ngayThanhToanArr[i] + "'," + this.soTienThanhToanArr[i].replaceAll(",", "") + ", "+this.ptThanhToanArr[i]+")";
						if (!db.update(insTHTT)) {
							this.msg = "Khong the tao moi ThoiHanThanhToan: " + insTHTT;
							System.out.println("2.Exception tai day: " + insTHTT);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			//End - Thời Hạn Thanh Toán - Dương
			// Neu la chi phi, xem xem co vuot ngan sach khong
			for (int i = 0; i < this.spList.size(); i++) {
				ISanpham sp = this.spList.get(i);

				String SanPham_FK = "NULL";
				String ChiPhi_FK = "NULL";
				String TaiSan_FK = "NULL";
				String CCDC_FK = "NULL";

				if (this.lhhId.equals("0")) {
					SanPham_FK = sp.getPK_SEQ();
				} else {
					if (this.lhhId.equals("1")) // Tai san co dinh
					{

						TaiSan_FK = sp.getPK_SEQ();
					} else {
						if (this.lhhId.equals("3")) // CONG CU DUNG CU
						{

							CCDC_FK = sp.getPK_SEQ();
						} else // Chi phi dich vu
						{

							ChiPhi_FK = sp.getPK_SEQ();
							if (ChiPhi_FK == null || ChiPhi_FK.trim().length() == 0) {
								ChiPhi_FK = "NULL";
							}
						}
					}

				}

				if (!this.lhhId.equals("2") && SanPham_FK.equals("NULL") && TaiSan_FK.equals("NULL")
						&& CCDC_FK.equals("NULL")) // &&
													// ChiPhi_FK.equals("NULL")
				{
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã công cụ dụng cụ / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}

				long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi));

				String ptThue = "0";
				if (sp.getPhanTramThue().trim().length() > 0)
					ptThue = sp.getPhanTramThue().trim();

				String thueNK = "0";
				if (sp.getThueNhapKhau().trim().length() > 0)
					thueNK = sp.getThueNhapKhau();
				if (!this.lhhId.equals("2")) {
					if (SanPham_FK == "NULL" && TaiSan_FK == "NULL" && CCDC_FK == "NULL" && ChiPhi_FK == "NULL") {
						this.msg = "Vui lòng nhập mã sản phẩm hoặc chi phí của P0";
						db.getConnection().rollback();
						return false;
					}
				}

				List<INgaynhan> nn = sp.getNgaynhan();

				for (int j = 0; j < nn.size(); j++) {

					double soluong = Double.parseDouble(sp.getSoluong());
					if (this.loai.equals("2"))
						soluong = Double.parseDouble(nn.get(j).getSoluong().trim());

					if (soluong > 0) {
						String sql = "SELECT CHUNGLOAI_FK, NGUONGOC FROM SANPHAM WHERE PK_SEQ = " + SanPham_FK + " ";
						ResultSet rsKT = db.get(sql);
						if (rsKT.next()) {
							String chungloai = rsKT.getString("CHUNGLOAI_FK") == null ? ""
									: rsKT.getString("CHUNGLOAI_FK");
							String nguongoc = rsKT.getString("NGUONGOC") == null ? "" : rsKT.getString("NGUONGOC");

							if (chungloai.equals("100003") || chungloai.equals("100004"))
								isKhangSinh = true;
							if (nguongoc.equals("Nhập khẩu"))
								isNK = true;

						}
						rsKT.close();
						// Kiem tra quota
						String queryHMNK = " select isnull(soluong,0) as soluong from erp_hanmucnhapkhau "
								+ " where sanpham_fk = '" + SanPham_FK + "' and tungay<='" + this.ngaymuahang
								+ "' and '" + this.ngaymuahang + "'<=denngay and pk_seq "
								+ " =(select max(pk_seq) from erp_hanmucnhapkhau where sanpham_fk = '" + SanPham_FK
								+ "' and trangthai =1)";
						ResultSet rsHMNK = db.get(queryHMNK);

						long soluongHMNK = 0;
						if (rsHMNK.next()) {
							soluongHMNK = rsHMNK.getLong("soluong");
						}
						rsHMNK.close();

						String querySLDaMua = "select isnull(sum(muasp.soluong),0) as soluong from erp_muahang_sp muasp"
								+ " left join erp_muahang mua on mua.pk_seq = muasp.muahang_fk" + " left join "
								+ " (select pk_seq,sanpham_fk,soluong,tungay,denngay" + " from erp_hanmucnhapkhau"
								+ " where pk_seq in(" + " select max(pk_seq)" + " from erp_hanmucnhapkhau"
								+ " where trangthai =1 "
								+ " group by (sanpham_fk))) hmnk on hmnk.sanpham_fk = muasp.sanpham_fk"
								+ " where muasp.sanpham_fk = '" + SanPham_FK
								+ "'and (mua.trangthai=2 or mua.trangthai=1 or mua.trangthai = 0) and hmnk.tungay <=mua.ngaymua and hmnk.denngay >=mua.ngaymua";
						// chỉ lấy những đơn mua hàng có trạng thái đã duyệt và hoàn tất ????
						ResultSet rsSLDaMua = db.get(querySLDaMua);
						long slDaMua = 0;
						if (rsSLDaMua.next()) {
							slDaMua = rsSLDaMua.getLong("soluong");
						}
						rsSLDaMua.close();
						double test = slDaMua + soluong - soluongHMNK;
						if (soluongHMNK > 0 && test > 0) {
							this.msg = "Sản phẩm " + sp.getTensanpham() + " chỉ còn số lượng đặt tối đa:"
									+ (soluongHMNK - slDaMua);
							this.db.getConnection().rollback();
							
							return false;
						}
						// End kiem tra quota
						double thanhtien = Double.parseDouble(sp.getDongia()) * soluong;
						long thanhtienviet = Math.round(dongiaviet * soluong);
						query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, taisan_fk, ccdc_fk, chiphi_fk, diengiai, donvi, soluong, dongia, soluong_new, dongia_new, thuexuat, vat, thanhtien, dongiaviet, thanhtienviet, ngaynhan, khott_fk, nhomkenh_fk, dungsai, PhanTramThue, ThueNhapKhau, TENHD) "
								+ " values(" + dmhCurrent + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + CCDC_FK + ", "
								+ ChiPhi_FK + ", N'" + sp.getTensanpham() + "', N'" + sp.getDonvitinh() + "', '"
								+ soluong + "'," + sp.getDongia() + ", " + soluong + "," + sp.getDongia() + ","
								+ sp.getThuexuat() + ", "+ sp.getThuexuat() + "," + thanhtien + " ," + dongiaviet + ", " + "" + thanhtienviet
								+ ", '" + nn.get(j).getNgay() + "', "
								+ (sp.getKhonhan().length() > 0 ? sp.getKhonhan() : null) + ", '100000', "
								+ this.dungsai + "," + ptThue + ", " + thueNK + ", N'" + sp.getTenHD() + "')";

						System.out.println("(Thêm mới)Insert Into Erp_MuaHang_SP :" + query);

						if (!db.update(query)) {
							this.msg = "Khong the tao moi Mua hang - San pham: " + query;
							db.getConnection().rollback();
							return false;
						}
					}

				}

			}
			// End Insert For tung dong
			System.out.println("Loại hh " + this.lhhId);

			// CAP NHAT CHIPHI KHAC NEU CO
			if (this.lhhId.equals("0")) {
				double cpKHAC = 0;
				if (this.cpkDiengiai != null && this.cpkSotien != null) {
					for (int i = 0; i < this.cpkDiengiai.length; i++) {
						if (this.cpkSotien[i].trim().length() > 0) {
							cpKHAC += Double.parseDouble(this.cpkSotien[i].replaceAll(",", "").trim());

							query = "Insert ERP_MUAHANG_CPKHAC(muahang_fk, diengiai, sotien) " + "values('" + dmhCurrent
									+ "', N'" + this.cpkDiengiai[i] + "', '" + this.cpkSotien[i].replaceAll(",", "")
									+ "') ";
							System.out.println("Câu chèn CP KHAC: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi Mua hang - San pham: " + query;

								db.getConnection().rollback();
								return false;
							}

						}
					}
				}

				query = "UPDATE ERP_MUAHANG set CHIPHIKHAC = '" + cpKHAC + "' where pk_seq = '" + dmhCurrent + "' ";
				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang - San pham: " + query;

					// System.out.println("5.2.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}
			}

			//
			boolean vuotNganSach = false;

			if (this.lhhId.equals("2")) // chi phi, dich vu
			{

				query = " INSERT INTO ERP_MUAHANG_SP_HOADON   (	MUAHANG_FK ,MUAHANG_SP_FK  ,MAHOADON  ,	MAUSOHOADON  ,	KYHIEU  ,SOHOADON  ,NGAYHOADON , "
						+ " TENNHACUNGCAP  ,MASOTHUE  ,TIENHANG  ,	THUESUAT  ,	TIENTHUE  ,	TONGCONG   ,GHICHU   )    "
						+ " select mhsp.MUAHANG_FK,mhsp.PK_SEQ,'','','','','','','',mhsp.THANHTIEN,mh.VAT,mhsp.THANHTIEN*mh.VAT/100,mhsp.THANHTIEN+mhsp.THANHTIEN*mh.VAT/100,'' "
						+ " from ERP_MUAHANG_SP mhsp " + " inner join ERP_MUAHANG mh on mh.PK_SEQ=mhsp.MUAHANG_FK "
						+ " where MUAHANG_FK not in (select MUAHANG_FK from ERP_MUAHANG_SP_HOADON) "
						+ " and mh.LOAIHANGHOA_FK='2' and mh.pk_seq=" + dmhCurrent;
				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang - San pham: " + query;
					db.getConnection().rollback();
					return false;
				}

				query = " select isnull(ngansachConLai.conLai, 0) - muahang.tongGiaTri as cotheSuDung  " + "from "
						+ "( " + "select CHIPHI_FK, SUM(SOLUONG * DONGIA) as tongGiaTri  "
						+ "from ERP_MUAHANG_SP where MUAHANG_FK = '" + this.id + "' group by CHIPHI_FK  " + ")  "
						+ "muahang left join " + "( "
						+ "select nganSach.CHIPHI_FK, isnull(nganSach.DUTOAN, 0) - ISNULL( dukienChi.tongduKien, 0) as conLai  "
						+ "from " + "( " + "select CHIPHI_FK, DUTOAN, THUCCHI   " + "from ERP_LAPNGANSACH_CHIPHI   "
						+ "where LAPNGANSACH_FK in ( select pk_seq from ERP_LAPNGANSACH where NAM = '" + nam
						+ "' and TRANGTHAI = '1' )   " + "and DONVITHUCHIEN_FK = '" + this.dvthId + "'   " + ")  "
						+ "nganSach left join " + "(  "
						+ "select CHIPHI_FK, SUM(b.SOLUONG * b.DONGIAVIET) as tongduKien  "
						+ "from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK  "
						+ "where a.LOAIHANGHOA_FK = '2' and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'   "
						+ "and SUBSTRING(ngaymua, 0, 5) = '" + nam + "' and a.pk_seq != '" + this.id + "'  "
						+ "group by CHIPHI_FK  " + ") " + "dukienChi on nganSach.CHIPHI_FK = dukienChi.CHIPHI_FK   "
						+ ")  " + "ngansachConLai on muahang.CHIPHI_FK = ngansachConLai.CHIPHI_FK";

				System.out.println("___Check ngan sach chi phi: " + query);

				ResultSet rsCheckNS = db.get(query);
				while (rsCheckNS.next()) {
					if (rsCheckNS.getDouble("cotheSuDung") < 0) {
						vuotNganSach = true;
					}
				}
				rsCheckNS.close();
			} else // Tai san co dinh
			{
				if (this.lhhId.equals("1")) {
					/*query = "select b.pk_seq as taisan_fk, a.SoLuong, a.DonGia, a.THANHTIEN as tongNganSach, a.SOTHANGKH, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12   "
							+ "from ERP_LAPNGANSACH_TAISAN a inner join ERP_TAISANCODINH b on a.PK_SEQ = b.LAPNGANSACH_TAISAN_FK  "
							+ "where DONVITHUCHIEN_FK = '" + this.dvthId
							+ "' and b.pk_seq in ( select TAISAN_FK from ERP_MUAHANG_SP where MUAHANG_FK = '" + this.id
							+ "' )";

					System.out.println("1.Check ngan sach tai san: " + query);

					ResultSet rsNgansach = db.get(query);
					while (rsNgansach.next()) {
						String taisanId = rsNgansach.getString("taisan_fk");
						double ngansachTong = rsNgansach.getDouble("tongNganSach");
						double tongKhauhao = 0;

						int sothangKH = rsNgansach.getInt("SOTHANGKH");
						double dongia = rsNgansach.getDouble("DonGia");

						int thangthu = 0;
						for (int i = 1; i <= 12; i++) {
							int T1 = rsNgansach.getInt("T" + Integer.toString(i));
							if (T1 > 0) {
								double khaukhao_thang = ((T1 * dongia) / sothangKH) * (12 - thangthu);
								thangthu++;

								tongKhauhao += khaukhao_thang;
							}
						}

						// Lay tat cac cac mua hang cua tai san nay, tinh tong
						// Ngansach da su dung va tong khau hao (du kien)
						query = "select a.NGAYMUA, b.SOLUONG, b.DONGIAVIET   "
								+ "from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK "
								+ "where b.TAISAN_FK = '" + taisanId + "' and a.DONVITHUCHIEN_FK = '" + this.dvthId
								+ "'";

						System.out.println("2.Check ngan sach tai san da su dung: " + query);
						ResultSet rsTaisan = db.get(query);

						double tongNganSach_Mua = 0;
						double tongKhauHao_Mua = 0;

						if (rsTaisan != null) {
							while (rsTaisan.next()) {
								String thangbdKhauHao_DuKien = rsTaisan.getString("NGAYMUA").split("-")[1];

								int soluongMua = rsTaisan.getInt("SOLUONG");
								double dongiaMua = rsTaisan.getDouble("DONGIAVIET");

								tongNganSach_Mua += soluongMua * dongiaMua;

								tongKhauHao_Mua += (soluongMua * dongiaMua / sothangKH)
										* (12 - Integer.parseInt(thangbdKhauHao_DuKien));
							}
							rsTaisan.close();
						}

						if ((tongNganSach_Mua > ngansachTong) || (tongKhauHao_Mua > tongKhauhao)) {
							vuotNganSach = true;
							rsNgansach.close();
							break;
						}

					}
					rsNgansach.close();

					System.out.println("3.Check tai san vuot ngan sach: " + vuotNganSach);*/
				}
			}

			query = "Update ERP_MUAHANG set VUOTNGANSACH = '" + (vuotNganSach == true ? "1" : "0")
					+ "' where pk_seq = '" + this.id + "' ";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			System.out.println("loai don " + this.loai + "; duoc phan bo " + this.isDuocPhanBo);
			// PO nhập khẩu && PO trong nước (PO tổng dùng để phân bổ)
			if (this.loai.equals("0") || (this.loai.equals("1") )) {
				// NẾU CÓ CHÍNH SÁCH DUYỆT DÀNH CHO NCC CỦA ĐƠN MUA HÀNG, THÌ LẤY CHÍNH SÁCH DUYỆT ĐÓ
				query = CreateChinhSachDuyet();
			}
			// insert nguoi duyet PO : Mua chi phí/TSCD/CCDC lấy trong cơ chế duyệt
			else {
				// chỗ này giống hàm CreateChinhSachDuyet
				query = CreateChinhSachDuyet();
			}

			System.out.println("3.Duyet PO....:" + query);

			ResultSet rs = db.get(query);
			int kt = 0;
			while (rs.next()) {

				query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = "
						+ rs.getString("CHUCDANH_FK") + " AND MUAHANG_FK = " + this.id + ")"
						+ "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) " + "VALUES('"
						+ this.id + "', '" + rs.getString("CHUCDANH_FK") + "', '0','" + rs.getString("QUYETDINH")
						+ "') ";

				System.out.println("4. Insert Duyet PO:" + query);
				if (!db.update(query)) {
					this.msg = "Khong the them nguoi duyet cho PO: " + query;
					db.getConnection().rollback();
					return false;
				}
				kt++;
			} 
			if(kt <= 0){
				query = "update ERP_MUAHANG set trangthai = 1 where pk_seq = '" + this.id + "'";
				if (!db.update(query)) {
					this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
				rs.close();
			}

			// CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_DMH", new String[] { this.id });

			/*
			 * //Tạm thời nếu có TGD duyệt thì bỏ if(vuotNganSach) { query =
			 * "delete ERP_DUYETMUAHANG where chucdanh_fk = '100005' and muahang_fk = '"
			 * + this.id +"'"; if (!db.update(query)) { this.msg =
			 * "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
			 * db.getConnection().rollback(); return false; }
			 * 
			 * }
			 */
			// Vuot ngan sach, trong co che duyet ma chua co tong giam doc
			/*
			 * if(vuotNganSach == true && dacoTongGiamDoc == false) { //chi tong
			 * giam doc moi co quyen quyet dinh query =
			 * "update ERP_DUYETMUAHANG set QUYETDINH = '0' where MUAHANG_FK = '"
			 * + this.id + "'"; if (!db.update(query)) { this.msg =
			 * "Khong the them nguoi duyet cho PO: " + query;
			 * db.getConnection().rollback(); return false; }
			 * 
			 * query =
			 * "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) "
			 * + "VALUES('"+ this.id +"', '100005', '0', '1', '" + ( thutu + 1 )
			 * + "') ";
			 * 
			 * System.out.println("4.Vuot ngan sach. Tong giam doc duyet: " +
			 * query); if (!db.update(query)) { this.msg =
			 * "Khong the them nguoi duyet cho PO: " + query;
			 * db.getConnection().rollback(); return false; } }
			 */

			// Check thieu hang
			/*
			 * try { query = "select count(kiemtra.SPID) as soDong  " + "from  "
			 * + "(  " +
			 * "SELECT SP.PK_SEQ AS SPID, SUM(KHO_SP.SOLUONG) AS SoLuong       "
			 * + "FROM ERP_KHOTT_SANPHAM KHO_SP      " +
			 * "INNER JOIN SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ      " +
			 * "WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from SANPHAM where LOAISANPHAM_FK in ( '100000', '100002', '100003', '100009', '100013', '100014', '100015', '100016', '100017' ) )     "
			 * + "GROUP BY SP.PK_SEQ  " + "union all  " +
			 * "select c.PK_SEQ as spId, sum(b.SOLUONG - ISNULL(nhanhang.SOLUONGNHAN, 0 ) ) as SoLuong    "
			 * +
			 * "from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK     "
			 * + "inner join SANPHAM c on b.SANPHAM_FK = c.PK_SEQ     " +
			 * "left join  " + "(  " +
			 * "select a.MUAHANG_FK, b.NGAYNHANDUKIEN, b.SANPHAM_FK, b.SOLUONGNHAN    "
			 * +
			 * "from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK    "
			 * +
			 * "where TRANGTHAI in (1, 2) and b.SANPHAM_FK is not null and b.NGAYNHANDUKIEN >= '"
			 * + getDateTime() + "'   " + ")  " +
			 * "nhanhang on a.PK_SEQ = nhanhang.MUAHANG_FK and c.PK_SEQ = nhanhang.SANPHAM_FK and b.NGAYNHAN = nhanhang.NGAYNHANDUKIEN   "
			 * +
			 * "where a.LOAISANPHAM_FK in ('100000', '100002', '100003', '100009', '100013', '100014', '100015', '100016', '100017') and b.NGAYNHAN >= '"
			 * + getDateTime() + "'    " + "group by c.PK_SEQ " + "union all  "
			 * + "select VT.PK_SEQ as spId,   " +
			 * "(-1) * SUM ( ( LSX.SOLUONG * isnull(SP.SOLUONGBANTP, 1) / isnull(SP.SOLUONGTP, 1) ) * BOM.TiLeBOM ) as SoLuong    "
			 * +
			 * "from ERP_LENHSANXUAT_GIAY LSX inner join ERP_LENHSANXUAT_SANPHAM LSX_SP on LSX.PK_SEQ = LSX_SP.Lenhsanxuat_fk    "
			 * + "inner join SANPHAM SP on LSX_SP.SANPHAM_FK = SP.PK_SEQ  " +
			 * "inner join   " + "(  " +
			 * "select c.pk_seq as DMID, a.pk_seq as spId,     " +
			 * "case d.vattu_fk when - 1 then   " +
			 * "( case ( select distinct loaisanpham_fk from SANPHAM where ma = d.mavattu )     "
			 * +
			 * "when 100013 then ( select top(1) pk_seq from SANPHAM where rong = ( select max(NO_OF_UP) from erp_no_of_slitting where FG_WIDTH = a.rong ) and ma = d.mavattu )    "
			 * +
			 * "else ( select top(1) pk_seq from SANPHAM where rong = ( select max(NO_OF_UP) - 2 from erp_no_of_slitting where FG_WIDTH = a.rong ) and ma = d.mavattu ) end )     "
			 * +
			 * "else d.VATTU_FK	end  as vattu_fk,  ( d.SOLUONG / c.SOLUONGCHUAN ) as TiLeBOM   "
			 * +
			 * "from SANPHAM a inner join ERP_MAKETOAN b on a.maketoan_fk = b.pk_seq      "
			 * + "inner join ERP_DANHMUCVATTU c on 'S' + b.ma = c.MaVatTu    " +
			 * "inner join ERP_DANHMUCVATTU_VATTU d on c.pk_seq = d.danhmucvt_fk      "
			 * + "where c.hieuluctu <= '" + getDateTime() + "' and '" +
			 * getDateTime() +
			 * "' <= c.hieulucden and c.trangthai = '1' and c.sudung = 1    " +
			 * " )  " +
			 * " BOM on SP.PK_SEQ = BOM.spId inner join SANPHAM VT on BOM.vattu_fk = VT.PK_SEQ  "
			 * + "where LSX.NGAYDUKIENHT >= '" + getDateTime() + "'   " +
			 * " group by VT.PK_SEQ " + ") " + "kiemtra  " +
			 * "group by kiemtra.SPID  " + "having SUM(soluong) < 0  ";
			 * 
			 * ResultSet rsCheck = db.get(query); int sodong = 0;
			 * if(rsCheck.next()) { sodong = rsCheck.getInt("soDong"); }
			 * 
			 * 
			 * if(sodong > 0) { //Lay LIST DIEN THOAI query =
			 * "select SMS, PHONELIST from ERP_CAUHINHSMS where MUCDICHSUDUNG = 'PO' and TRANGTHAI = '1'"
			 * ; ResultSet rsPhone = db.get(query); if(rsPhone.next()) { String
			 * sms = rsPhone.getString("SMS"); sms = sms.replaceAll("__",
			 * this.id);
			 * 
			 * String phone = rsPhone.getString("PHONELIST");
			 * 
			 * if(phone.length() > 0) { String[] pl = phone.split(";");
			 * 
			 * for(int i = 0; i < pl.length; i++ ) { if(pl[i].trim().length() >
			 * 0) { query =
			 * "insert OutBox(ID, PhoneNumber, Content, Contenttype, SendDate, Status) "
			 * + "values ('1', '" + pl[i] + "', N'" + sms + "', '0', '" +
			 * getDateTime() + "', '0')"; db.update(query); } } } }
			 * rsPhone.close(); } } catch (Exception e) {
			 * 
			 * }
			 */

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}

	}

	private String CreateChinhSachDuyet(){
		String query = "SELECT	NCC.CHUCDANH_FK, NCC.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG  "
		+ "INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "WHERE MUAHANG.PK_SEQ = '" + this.id + "' " +

		"UNION ALL " +
		// NẾU KO CÓ CHÍNH SÁCH DUYỆT DÀNH CHO SẢN PHẨM CỦA ĐƠN
		// MUA HÀNG VÀ KO CÓ CHÍNH SÁCH DUYỆT CHO NCC CỦA ĐƠN
		// MUA HÀNG
		"SELECT	SP.CHUCDANH_FK, SP.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG  "
		+ "INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
		+ "AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id
		+ "') " + "LEFT JOIN " + "(  " + "	SELECT	COUNT(*) AS NUM " + "	FROM ERP_MUAHANG MUAHANG   "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "	AND NCC.NCC_FK IN (SELECT NHACUNGCAP_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id + "')  "
		+ "	WHERE MUAHANG.PK_SEQ = '" + this.id + "'   " + ")DUYET_NCC ON 1 = 1 " + "LEFT JOIN " + "( "
		+ "	SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + "	FROM ERP_MUAHANG_SP MH_SP  "
		+ "	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
		+ "	WHERE MH.PK_SEQ = '" + this.id + "'  AND MH_SP.SANPHAM_FK NOT IN  "
		+ "	(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
		+ ")KTRA_SP ON 1 = 1 " + "WHERE MUAHANG.PK_SEQ = '" + this.id
		+ "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " +

		"UNION ALL " + "SELECT	CP.CHUCDANH_FK, CP.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG   "
		+ "INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "INNER JOIN ERP_CHINHSACHDUYET_CHIPHI CP ON CP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
		+ "INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CP.KHOANGCHIPHI_FK  "
		+ "LEFT JOIN( " + "	SELECT	COUNT(SP.CHUCDANH_FK) AS NUM " + "	FROM ERP_MUAHANG MUAHANG   "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
		+ "	AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id
		+ "') " + "	LEFT JOIN " + "	( " + "		SELECT	COUNT(*) AS NUM "
		+ "		FROM ERP_MUAHANG MUAHANG   "
		+ "		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "		AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id + "')  "
		+ "		WHERE MUAHANG.PK_SEQ = '" + this.id + "'   " + "	)DUYET_NCC ON 1 = 1 "
		+ "	LEFT JOIN " + "	( " + "		SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM "
		+ "		FROM ERP_MUAHANG_SP MH_SP  "
		+ "		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
		+ "		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
		+ "		WHERE MH.PK_SEQ = '" + this.id + "'  AND MH_SP.SANPHAM_FK NOT IN  "
		+ "		(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
		+ "	)KTRA_SP ON 1 = 1 " + "	WHERE MUAHANG.PK_SEQ = '" + this.id
		+ "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " +

		")DUYET_SP ON 1 = 1 " + "LEFT JOIN( " + "	SELECT	COUNT(NCC.CHUCDANH_FK) AS NUM "
		+ "	FROM ERP_MUAHANG MUAHANG   "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "	WHERE MUAHANG.PK_SEQ = '" + this.id + "'" + ")DUYET_NCC ON 1 = 1 "
		+ "WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT "
		+ "AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) "
		+ "AND MUAHANG.PK_SEQ = '" + this.id + "' AND DUYET_SP.NUM = 0 AND DUYET_NCC.NUM = 0 ";
		// 01/06/2016: Ngày quốc tế thiếu nhi, ^^
		// Lưu ý: không tính tỉ giá quy đổi tại đây vì đã quy đổi rồi.
		// Khi tạo tính lại tiền thì vui lòng chia ngược lại với tỉ giá để biết được đúng số nguyên tệ
		// MUAHANG.TONGTIENBVAT hiện tại đang lưu tiền VND đã được quy đổi sang USD
		
		return query;
	}
	public boolean updateDmh() {

		// Kiem tra moi them vao
		String query = "";

		if (this.loai.equals("0")) // Mua hàng nhập khẩu
		{
			if (this.loaiDMH_NK.trim().length() <= 0) {
				this.msg = "Vui lòng loại đơn Hợp đồng/ Đơn mua hàng";
				return false;
			}
			if (this.loaiDMH_NK.equals("0") && this.thoihanno.trim().length() <= 0) {
				this.msg = "Vui lòng thời hạn nợ cho hợp đồng này";
				return false;
			}
			this.nccId = "NULL";
		} else {
			if (this.nccId.trim().length() <= 0) {
				this.msg = "Vui lòng chọn nhà cung cấp.";
				return false;
			}
		}

		this.kenhId = "NULL";

		// LẤY KHOID CHO TRƯỜNG HỢP GIA CÔNG
		String loainccId = "";

		// Neu NCC o du lieu nen khong thiet lap QL Cong no, ma o don mua hang
		// chon thiet lap cong no thi bao loi
		if (!this.loai.equals("0") && this.quanlyCN.equals("1") && this.quanlyCN.equals("1")) {
			query = " select isnull(quanlycongno,'0') as quanlycongno " +
					" from Erp_NhaCungCap where pk_seq = '" + this.nccId + "' ";
			ResultSet rsCongNo = db.get(query);
			try {
				if (rsCongNo.next()) {
					if (rsCongNo.getString("quanlycongno").equals("0")) {
						rsCongNo.close();
						this.msg = "Nhà cung cấp trong dữ liệu nền không thiết lập quản lý công nợ. Vui lòng kiểm tra lại dữ liệu nền";
						return false;
					}
				}
				rsCongNo.close();
			} catch (SQLException e) {
			}
		}

		if (this.TienTe_FK.trim().length() <= 0) {
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}

		if (this.spList.size() <= 0) {
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		} else {
			if (this.loai.equals("2")) {
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);

					System.out.println("Kho Nhan: " + sp.getKhonhan());

					if (sp.getNgaynhan().size() == 0) {
						this.msg = "Vui lòng nhập thông tin ngày nhận trong danh sách sản phẩm.";
						return false;
					} else {
						if (sp.getSoluong() == null || sp.getSoluong().length() == 0
								|| Double.parseDouble(sp.getSoluong()) == 0) {
							this.msg = "Vui lòng nhập số lượng sản phẩm.";
							return false;
						}
						List<INgaynhan> nn = sp.getNgaynhan();
						double sl = Double.parseDouble(sp.getSoluong());
						double tmpSL = 0;
						for (int j = 0; j < nn.size(); j++) {
							if (nn.get(j).getSoluong() == null || nn.get(j).getSoluong().length() == 0) {
								this.msg = "Vui lòng nhập số lượng sản phẩm, ngày nhận " + nn.get(j).getNgay();
								return false;
							}
							tmpSL += Double.parseDouble(nn.get(j).getSoluong());
						}
						if (sl != tmpSL) {
							this.msg = "Tổng số lượng trong ngày nhận không khớp với số lượng nhập.";
							return false;
						}
					}

					/*if (this.lhhId.equals("0")) {
						if (sp.getKhonhan().trim().length() <= 0) {
							this.msg = "Vui lòng nhập kho nhận hàng trong danh sách sản phẩm.";
							return false;
						}

						// Check kho nhan va sanpham
						int count = 0;
						query = "select count(*) as sodong from ERP_KHOTT_SANPHAM where sanpham_fk = '" + sp.getPK_SEQ()
								+ "' and khott_fk = '" + sp.getKhonhan().trim() + "' ";
						ResultSet rsCheckKho = db.get(query);
						try {
							if (rsCheckKho.next()) {
								count = rsCheckKho.getInt("sodong");
							}
							rsCheckKho.close();
						} catch (Exception e) {
						}

						if (count <= 0) {
							this.msg = "Kho nhận của sản phẩm ( " + sp.getMasanpham()
									+ " ) không đúng trong dữ liệu nền KHO - SANPHAM. Vui lòng kiểm tra lại.";
							return false;
						}
					}*/

				}
			}
		}

		if (this.TienTe_FK.trim().length() <= 0) {
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}

		if (!this.loai.equals("0") && this.nccTen.trim().length() <= 0) {
			this.msg = "Vui lòng nhập tên nhà cung cấp mua hàng";
			return false;
		}

		try {
			String ngaysua = getDateTime();
			// String[] ncc = this.nccTen.split(" - ");
			db.getConnection().setAutoCommit(false);

			// Dùng trong cơ chế duyệt
			boolean isNK = false;
			boolean isKhangSinh = false;

			String loaisanpham = "NULL";

			System.out.println("Loai hàng hóa ở đây : " + this.lhhId);

			if (this.lhhId.trim().equals("0"))
				loaisanpham = this.lspId;

			query = "SELECT DONVITHUCHIEN_FK, TONGTIENBVAT, NHACUNGCAP_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id
					+ "' ";
			boolean approve = false; // Vượt tiền so với giá trị tiền đơn cũ
			boolean thaydoidvth = false;
			boolean thaydoincc = false;
			ResultSet rs = this.db.get(query);

			if (rs.next()) {

				if (Double.parseDouble(rs.getString("TONGTIENBVAT")) != Double.parseDouble(this.BVAT))
					approve = true;

				if (!rs.getString("DONVITHUCHIEN_FK").equals(this.dvthId))
					thaydoidvth = true;

				if (!this.loai.equals("0")) {
					if (!rs.getString("NHACUNGCAP_FK").equals(this.nccId))
						thaydoincc = true;
				}

			}
			rs.close();

			String ghichu = "";
			if (this.ghichuArr != null) {
				for (int i = 0; i < this.ghichuArr.length; i++) {
					ghichu += this.ghichuArr[i] + "__";
				}
				if (ghichu.trim().length() > 0) {
					this.GhiChu = ghichu;
				}
			}

			// CAP NHAT SO PO, MA TU DONG TANG
			String nam = this.ngaymuahang.substring(0, 4);
			String thang = this.ngaymuahang.substring(5, 7);
			String soPO = "";
			int sotutang_theonam = 0;

			boolean cothaydoi_dvth = false;

			query = " select  donvithuchien_fk from erp_muahang mh " + " where mh.pk_seq=" + this.id
					+ " and ( donvithuchien_fk <>  " + this.dvthId + " or  SUBSTRING(NGAYMUA, 0, 5) <> " + nam + " ) ";
			ResultSet rscheckdv = db.get(query);
			if (rscheckdv.next()) {
				cothaydoi_dvth = true;
				// Có thay đổi đơn vị thực hiện.phải thay đổi lại số PO và số
				// thứ tự,hoặc năm bị thay đổi
				query = " SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "
						+ " WHERE PK_SEQ =" + this.dvthId + " ) AS PREFIX   "
						+ " FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = " + nam
						+ "  and   DMH.DONVITHUCHIEN_FK=" + this.dvthId;
				// System.out.println("Du lieu po sai :"+query);

				ResultSet rsPO = db.get(query); // MẤY NỮA BỔ SUNG THÊM, QUA NĂM
												// MỚI SỐ TỰ TĂNG RESET VỀ BẰNG
												// 1
				if (rsPO.next()) {
					sotutang_theonam = (rsPO.getInt("maxSTT") + 1);
					String prefix = rsPO.getString("PREFIX");
					String namPO = this.ngaymuahang.substring(2, 4);
					String chuoiso = ("0000" + Integer.toString(sotutang_theonam)).substring(
							("0000" + Integer.toString(sotutang_theonam)).length() - 4,
							("0000" + Integer.toString(sotutang_theonam)).length());

					soPO = prefix + "-" + chuoiso + "/" + thang + "/" + namPO;

				}
				rsPO.close();
				// System.out.println("---SO PO: " + soPO);

			}
			rscheckdv.close();

			// CẬP NHẬT NGÀY ĐẾN HẠN TT NẾU PO LÀ PO NỘI BỘ (DÙNG TRONG PHIẾU
			// CHI)
			String ngaydenhantt = "";
			if (this.checkedNoiBo.equals("1")) {
				// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày
				// hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
				query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '" + this.ngaymuahang
						+ "')),120 ) as ngaydenhantt " + "FROM ERP_NHACUNGCAP " + "WHERE PK_SEQ = '" + this.nccId + "'";
				ResultSet rsThoihanno = db.get(query);
				if (rsThoihanno != null) {
					while (rsThoihanno.next()) {
						ngaydenhantt = rsThoihanno.getString("ngaydenhantt");
					}
					rsThoihanno.close();
				}
			}

			if (loaisanpham == null || loaisanpham.length() <= 0) {
				loaisanpham = "NULL";
			}

			int ishopdong = 0;
			if (this.loaiDMH_NK.equals("0"))
				ishopdong = 1;
			
			// nếu là nhập khẩu ( loại =0 ) thì NCC chính là NhacungcapNK
			if(this.loai.equals("0")){
				this.nccId = this.nhacungcapNK;
			}
			
			System.out.println("vat " + this.VAT);
			query = " update erp_muahang set KENH_FK = " + this.kenhId + ", THOIHANNO = " + this.thoihanno + " , LOAI ="
					+ this.loai + ", ISHOPDONG = " + ishopdong + ", ngaymua = '" + this.ngaymuahang
					+ "', donvithuchien_fk = '" + this.dvthId + "', type = '" + this.isdontrahang + "', "
					+ " nhacungcap_fk = " + this.nccId + ", loaisanpham_fk = " + loaisanpham + ", loaihanghoa_fk = '"
					+ this.lhhId + "', tiente_fk = '" + this.TienTe_FK + "', tygiaquydoi = '" + this.TyGiaQuyDoi
					+ "', tongtienBVat = " + this.BVAT + ", " + " vat = " + this.VAT + ", tongtienAVat = " + this.AVAT
					+ ", dungsai = '" + this.dungsai + "', ngaysua = '" + ngaysua + "', nguoisua = '" + this.userId
					+ "'," + " GhiChu=N'" + this.GhiChu.replaceAll("'", "''") + "', SOTHAMCHIEU = N'" + this.sothamchieu
					+ "', NguonGocHH = '" + this.NguonGocHH + "', quanlycongno = '" + this.quanlyCN + "'," + " ETD = '"
					+ this.ETD + "', ETA = '" + this.ETA + "',HTTT = N'" + this.hinhThucTT + "', DIADIEMGIAOHANG = N'"
					+ this.diadiemgiaohang + "', NGAYDENHANTT = '" + ngaydenhantt + "', dvchiutrachnhiem = '"
					+ this.dvchiutrachnhiem + "', " + " sohopdong = '" + this.sohopdong + "', soluong = '"
					+ this.soluong + "', TENNHANHAPKHAU = N'" + this.tennhank + "', TENNHASANXUAT = N'" + this.tennhasx
					+ "', NGAYSHIP = '" + this.ngayship + "', NGAYNHAPKHO = '" + this.ngaynhapkho + "' ";

			if (cothaydoi_dvth) {
				query = query + " ,SOTUTANG_THEONAM ='" + sotutang_theonam + "',SOPO='" + soPO + "' ";
			}

			query = query + "  where pk_seq = '" + this.id + "'";
			System.out.println("luu erp_muahang " + query);
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat Mua hang: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_MUAHANG_SP where muahang_fk = '" + this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_MUAHANG_SP: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			//Begin - Thời Hạn Thanh Toán - Dương
			query = "delete erp_thoihanthanhtoan where muahang_fk = '" + this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat erp_thoihanthanhtoan: " + query;
				db.getConnection().rollback();
				return false;
			}
			if (this.ngayThanhToanArr != null) {
				for (int i = 0; i < this.ngayThanhToanArr.length; i++) {
					if (this.ngayThanhToanArr[i] != null && this.ngayThanhToanArr[i] != "") {
						String insTHTT = "insert into erp_thoihanthanhtoan values (" + this.id + ",'"
								+ this.ngayThanhToanArr[i] + "'," + this.soTienThanhToanArr[i].replaceAll(",", "") + ", "+this.ptThanhToanArr[i]+")";
						if (!db.update(insTHTT)) {
							this.msg = "Khong the tao moi ThoiHanThanhToan: " + insTHTT;
							System.out.println("2.Exception tai day: " + insTHTT);
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			//End - Thời Hạn Thanh Toán - Dương
			
			
			// CAP NHAT CHIPHI KHAC NEU CO
			query = "update ERP_MUAHANG set CHIPHIKHAC = '0' where pk_seq = '" + this.id + "' ";
			System.out.println("cap nhat chi phi khac " + query);
			if (!db.update(query)) {
				this.msg = "Khong the tao moi Mua hang - San pham: " + query;

				System.out.println("5.2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_MUAHANG_CPKHAC where muahang_fk = '" + this.id + "' ";
			if (!db.update(query)) {
				this.msg = "Khong the tao moi Mua hang - San pham: " + query;

				System.out.println("5.2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_MUAHANG_SP_PHANBO where muahang_fk = '" + this.id + "' ";
			if (!db.update(query)) {
				this.msg = "Khong the tao moi Mua hang - San pham - Phan bo: " + query;

				System.out.println("5.2.Exception tai day: " + query);
				db.getConnection().rollback();
				return false;
			}

			if (this.lhhId.equals("0")) {
				double cpKHAC = 0;
				if (this.cpkDiengiai != null && this.cpkSotien != null) {
					for (int i = 0; i < this.cpkDiengiai.length; i++) {
						if (this.cpkSotien[i].trim().length() > 0) {
							cpKHAC += Double.parseDouble(this.cpkSotien[i].replaceAll(",", "").trim());

							query = "Insert ERP_MUAHANG_CPKHAC(muahang_fk, diengiai, sotien) " + "values('" + this.id
									+ "', N'" + this.cpkDiengiai[i] + "', '" + this.cpkSotien[i].replaceAll(",", "")
									+ "') ";
							System.out.println("Câu chèn CP khác :" + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi Mua hang - San pham: " + query;

								System.out.println("5.1.Exception tai day: " + query);
								db.getConnection().rollback();
								return false;
							}

						}
					}
				}

				query = "UPDATE ERP_MUAHANG set CHIPHIKHAC = '" + cpKHAC + "' where pk_seq = '" + this.id + "' ";
				System.out.println("cap nhat cpk " + query);
				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang - San pham: " + query;

					System.out.println("5.2.Exception tai day: " + query);
					db.getConnection().rollback();
					return false;
				}
			}

			for (int i = 0; i < this.spList.size(); i++) {
				ISanpham sp = this.spList.get(i);

				String SanPham_FK = "NULL";
				String ChiPhi_FK = "NULL";
				String TaiSan_FK = "NULL";
				String CCDC_FK = "NULL";

				if (this.lhhId.equals("0")) {
					SanPham_FK = sp.getPK_SEQ();

				} else {
					if (this.lhhId.equals("1")) // Tai san co dinh
					{
						TaiSan_FK = sp.getPK_SEQ();
					} else {
						if (this.lhhId.equals("3")) // Cong cu dung cu
						{
							CCDC_FK = sp.getPK_SEQ();
						} else // Chi phi dich vu
						{
							ChiPhi_FK = sp.getPK_SEQ();
							if (ChiPhi_FK == null || ChiPhi_FK.trim().length() == 0) {
								ChiPhi_FK = "NULL";
							}
						}
					}
				}

				if (!this.lhhId.equals("2") && SanPham_FK.equals("NULL") && TaiSan_FK.equals("NULL")
						&& CCDC_FK.equals("NULL")) {
					this.msg = "Vui lòng kiểm tra lại mã sản phẩm / mã tài sản / mã công cụ dụng cụ / mã chi phí trong danh sách sản phẩm bạn nhập.";
					this.db.getConnection().rollback();
					return false;
				}

				long dongiaviet = Math.round((Double.parseDouble(sp.getDongia()) * this.TyGiaQuyDoi));
				// long thanhtienviet = Math.round( dongiaviet *
				// Double.parseDouble(sp.getSoluong()) );

				String ptThue = "0";
				if (sp.getPhanTramThue().trim().length() > 0)
					ptThue = sp.getPhanTramThue().trim();

				String thueNK = "0";
				if (sp.getThueNhapKhau().trim().length() > 0)
					thueNK = sp.getThueNhapKhau();

				if (SanPham_FK == "NULL" && TaiSan_FK == "NULL" && CCDC_FK == "NULL" && ChiPhi_FK == "NULL") {
					this.msg = "Vui lòng nhập mã sản phẩm hoặc chi phí của P0";
					db.getConnection().rollback();
					return false;
				}

				String muaNLdukien_fk = ((sp.getMNLId().trim().length() <= 0 || sp.getMNLId() == null) ? "null"
						: sp.getMNLId());

				List<INgaynhan> nn = sp.getNgaynhan();

				for (int j = 0; j < nn.size(); j++) {
					double soluong = Double.parseDouble(sp.getSoluong());
					if (this.loai.equals("2"))
						soluong = Double.parseDouble(nn.get(j).getSoluong().trim());
					double soluongold = Double.parseDouble(sp.getSoluongOLD());
					/*
					 * if(soluong < soluongold) { this.msg =
					 * "Khong the cap nhat Mua hang - San pham: so luong moi khong duoc nho hon so luong cu"
					 * ; db.getConnection().rollback(); return false; }
					 */

					if(!SanPham_FK.equals("NULL"))
					{
						double soluongship = 0;
						query = "select b.soluong "
								+ "from ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK "
								+ "where a.trangthai = '1' and a.MUAHANG_FK = " + this.id + " and sanpham_fk = "
								+ SanPham_FK;
						ResultSet rsship = db.get(query);
						if (rsship != null) {
							while (rsship.next()) {
								soluongship += rs.getDouble("soluong");
							}
						}
						if (soluong < soluongship) {
							this.msg = "Khong the cap nhat Mua hang - San pham: So luong khong duoc nho hon so luong da ship";
							db.getConnection().rollback();
							return false;
						}
	
						if (soluong > 0) {
							String sql = "SELECT CHUNGLOAI_FK, NGUONGOC FROM ERP_SANPHAM WHERE PK_SEQ = " + SanPham_FK
									+ " ";
							ResultSet rsKT = db.get(sql);
							if (rsKT.next()) {
								String chungloai = rsKT.getString("CHUNGLOAI_FK") == null ? ""
										: rsKT.getString("CHUNGLOAI_FK");
								String nguongoc = rsKT.getString("NGUONGOC") == null ? "" : rsKT.getString("NGUONGOC");
	
								if (chungloai.equals("100003") || chungloai.equals("100004"))
									isKhangSinh = true;
								if (nguongoc.equals("Nhập khẩu"))
									isNK = true;
	
							}
							rsKT.close();
	
							// Kiem tra quota
							String queryHMNK = " select isnull(soluong,0) as soluong from erp_hanmucnhapkhau "
									+ " where sanpham_fk = '" + SanPham_FK + "' and tungay<='" + this.ngaymuahang
									+ "' and '" + this.ngaymuahang + "'<=denngay and pk_seq "
									+ " =(select max(pk_seq) from erp_hanmucnhapkhau where sanpham_fk = '" + SanPham_FK
									+ "' and trangthai =1)";
							ResultSet rsHMNK = db.get(queryHMNK);
							long soluongHMNK = 0;
							if (rsHMNK.next()) {
								soluongHMNK = rsHMNK.getLong("soluong");
							}
							rsHMNK.close();
							System.out.println("soLuongHMNK" + soluongHMNK);
	
							String querySLDaMua = "select isnull(sum(muasp.soluong),0) as soluong from erp_muahang_sp muasp"
									+ " left join erp_muahang mua on mua.pk_seq = muasp.muahang_fk" + " left join "
									+ " (select pk_seq,sanpham_fk,soluong,tungay,denngay" + " from erp_hanmucnhapkhau"
									+ " where pk_seq in(" + " select max(pk_seq)" + " from erp_hanmucnhapkhau"
									+ " where trangthai =1 "
									+ " group by (sanpham_fk))) hmnk on hmnk.sanpham_fk = muasp.sanpham_fk"
									+ " where muasp.sanpham_fk = '" + SanPham_FK
									+ "' and (mua.trangthai=2 or mua.trangthai=1 or mua.trangthai = 0) and hmnk.tungay <=mua.ngaymua and hmnk.denngay >=mua.ngaymua";
							ResultSet rsSLDaMua = db.get(querySLDaMua);
							long slDaMua = 0;
							if (rsSLDaMua.next()) {
								slDaMua = rsSLDaMua.getLong("soluong");
							}
							rsSLDaMua.close();
							double test = slDaMua + soluong - soluongHMNK;
							
							if (soluongHMNK > 0 && test > 0) {
								this.msg = "Sản phẩm " + sp.getTensanpham() + " chỉ còn số lượng đặt tối đa:"
										+ (soluongHMNK - slDaMua);
								db.getConnection().rollback();
								return false;
							}
							System.out.println("slDaMua" + slDaMua);
						}
						// End kiem tra quota
					}
					double thanhtien = Double.parseDouble(sp.getDongia()) * soluong;
					long thanhtienviet = Math.round(dongiaviet * soluong);
					query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, taisan_fk, ccdc_fk, chiphi_fk, diengiai, donvi, soluong, dongia, soluong_new, dongia_new, thuexuat, vat, thanhtien, dongiaviet, thanhtienviet, ngaynhan, khott_fk, nhomkenh_fk, dungsai, PhanTramThue, ThueNhapKhau,Muanguyenlieudukien_fk, TENHD,is_khongthue) "
							+ " values(" + this.id + ", " + SanPham_FK + ", " + TaiSan_FK + ", " + CCDC_FK + ", "
							+ ChiPhi_FK + ", N'" + sp.getTensanpham() + "', N'" + sp.getDonvitinh() + "', '"
							+ soluong + "'," + sp.getDongia() + ", " + soluong + "," + sp.getDongia() + ","
							+ sp.getThuexuat() + ", "+ sp.getThuexuat() + "," + thanhtien + " ," + dongiaviet + ", " + "" + thanhtienviet
							+ ", '" + nn.get(j).getNgay() + "', "
							+ (sp.getKhonhan().length() > 0 ? sp.getKhonhan() : null) + ", '100000', "
							+ this.dungsai + "," + ptThue + ", " + thueNK + ", " + muaNLdukien_fk + ", N'"
							+ sp.getTenHD() + "',"+sp.getIsmienthue()+")";

					System.out.println("2.Insert Into Erp_MuaHang_SP :" + query);

					if (!db.update(query)) {
						this.msg = "Khong the tao moi Mua hang - San pham: " + query;
						db.getConnection().rollback();
						return false;
					}

					if (this.lhhId.equals("2")) // Chi phí mới lưu
					{
						List<ISanPhamPhanBo> ss = sp.getSanphamPB();
						for (int k = 0; k < ss.size(); k++) {
							if (ss.get(k).getChon().trim().equals(ss.get(k).getSpId())) {
								query = " insert into ERP_MUAHANG_SP_PHANBO(muahang_fk, chiphi_fk, sanphampb_fk) "
										+ " values(" + this.id + "," + ChiPhi_FK + ", '" + ss.get(k).getSpId()
										+ "' )";

								System.out.println("2.Insert Into ERP_MUAHANG_SP_PHANBO :" + query);

								if (!db.update(query)) {
									this.msg = "Khong the tao moi Mua hang - San pham - Phan bo: " + query;
									db.getConnection().rollback();
									return false;
								}
							}

						}
					}
				}

				// Cap nhat DADATHANG trong ERP_MUANGUYENLIEUDUKIEN
				query = "UPDATE ERP_MUANGUYENLIEUDUKIEN	"
						+ "SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " + "FROM " + "( "
						+ "	SELECT SUM(SOLUONG) AS SOLUONG, SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4) AS NAM,	"
						+ "	CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2)) AS THANG	" + "	FROM ERP_MUAHANG_SP "
						+ "	WHERE SANPHAM_FK IS NOT NULL	"
						+ "	GROUP BY SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4),CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2))	"
						+ ")A  " + "WHERE ERP_MUANGUYENLIEUDUKIEN.NAM = A.NAM	"
						+ "AND ERP_MUANGUYENLIEUDUKIEN.THANG = A.THANG	"
						+ "AND ERP_MUANGUYENLIEUDUKIEN.SANPHAM_FK = A.SANPHAM_FK ";

				System.out.println("Cap nhat da dat hang: " + query);

				this.db.update(query);

			} // End Insert For tung dong

			// KTRA NGUOI QUYET DINH
			query = " select count(*) isQuyetDinh \n"
					+ " from ERP_DUYETMUAHANG D inner join ERP_CHUCDANH C on D.CHUCDANH_FK = C.PK_SEQ \n"
					+ " where D.MUAHANG_FK ='" + this.id + "' and D.QUYETDINH = '1' and C.NHANVIEN_FK = " + this.userId
					+ "   \n";
			System.out.println("quyet dinh " + query);
			ResultSet rsTT = db.get(query);
			boolean isQD = false;

			if (rsTT != null) {
				while (rsTT.next()) {
					if (rsTT.getInt("isQuyetDinh") > 0) {
						isQD = true;
					}
				}
				rsTT.close();
			}

			// KIEM TRA PO CO VUOT NO: CO > CAP TREN DUYET MOI DC
			query = " SELECT isnull(HANMUCNO.TONGTIEN, 0) - ISNULL(MUAHANG.TONGGIATRI,0) - ISNULL(DATHANHTOAN.SOTIEN,0) as COTHESUDUNG, \n"
					+ "       (select TONGTIENAVAT*ISNULL(TYGIAQUYDOI,1) from ERP_MUAHANG where PK_SEQ = '" + this.id
					+ "') TGTPO \n" + " FROM  \n" + " ( select PK_SEQ, ISNULL(HANMUCNO,0) AS TONGTIEN  \n"
					+ "  from ERP_NHACUNGCAP \n"
					+ "  where PK_SEQ = (select NHACUNGCAP_FK from ERP_MUAHANG where PK_SEQ = '" + this.id + "') \n"
					+ " )HANMUCNO LEFT JOIN \n"
					+ " ( select NHACUNGCAP_FK, SUM(TONGTIENAVAT*ISNULL(TYGIAQUYDOI,1)) as TONGGIATRI   \n"
					+ "  from ERP_MUAHANG \n" + "  where PK_SEQ != '" + this.id + "' AND TRANGTHAI not in (3,4) \n"
					+ "  group by NHACUNGCAP_FK \n"
					+ " )MUAHANG ON MUAHANG.NHACUNGCAP_FK = HANMUCNO.PK_SEQ  LEFT JOIN \n"
					+ " (select TT.NCC_FK, SUM(TT.SOTIENTT) AS SOTIEN \n" + "  from ERP_THANHTOANHOADON TT \n"
					+ "  where TT.TRANGTHAI = 1 \n" + "  group by TT.NCC_FK \n"
					+ " ) DATHANHTOAN ON HANMUCNO.PK_SEQ =DATHANHTOAN.NCC_FK  ";

			ResultSet rsKT = db.get(query);
			boolean isVuotNo = false;

			if (rsKT != null) {
				while (rsKT.next()) {
					if (rsKT.getDouble("COTHESUDUNG") < rsKT.getDouble("TGTPO"))
						isVuotNo = true;

				}
				rsKT.close();
			}

			if (!approve && !thaydoidvth && !thaydoincc) // không vượt tiền và không thay đổi đơn vị thực hiện
			{
				query = "select count(*) as sodong from erp_duyetmuahang where muahang_fk = '" + this.id + "'";

				ResultSet rsCheck = db.get(query);
				int sodong = 0;
				if (rsCheck.next()) {
					sodong = rsCheck.getInt("sodong");
				}

				// NẾU CHƯA CÓ DANH SÁCH DUYỆT THÌ THÊM DANH SÁCH DUYỆT (PO
				// trong nuoc sau khi phan bo OR PO mua CP/TS/CCDC)
				if (((this.loai.equals("1") && this.isDuocPhanBo.equals("1")) || this.loai.equals(""))
						&& sodong <= 0) {
					query =  this.CreateChinhSachDuyet();

					System.out.println("3.Duyet PO:" + query);

					rs = db.get(query);

					while (rs.next()) {
						query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = "
								+ rs.getString("CHUCDANH_FK") + " AND MUAHANG_FK = " + this.id + ")"
								+ "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) "
								+ "VALUES('" + this.id + "', '" + rs.getString("CHUCDANH_FK") + "', '0','"
								+ rs.getString("QUYETDINH") + "') ";

						System.out.println("4. Insert Duyet PO:" + query);
						if (!db.update(query)) {
							this.msg = "Khong the them nguoi duyet cho PO: " + query;
							db.getConnection().rollback();
						}
					}
					rs.close();
					
					
				} else {
					System.out.println("duyetID " + this.duyetIds);
					if (this.duyetIds != null) {
						int n = this.duyetIds.length;
						if (isVuotNo) {
							if (!isQD) // Khong phai nguoi Quyet Dinh >> Bao loi
							{
								this.msg = "Đơn mua hàng này đã vượt hạn mức nợ, bạn không được phép duyệt. Vui lòng thông báo cấp trên duyệt.";
								db.getConnection().rollback();
								return false;
							} else {
								for (int i = 0; i < n; i++) {
									query = "UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = '1' " + "WHERE CHUCDANH_FK = '"
											+ this.duyetIds[i] + "' AND MUAHANG_FK = '" + this.id + "'";
									db.update(query);
								}
							}
						} else {
							for (int i = 0; i < n; i++) {
								query = "UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = '1' " + "WHERE CHUCDANH_FK = '"
										+ this.duyetIds[i] + "' AND MUAHANG_FK = '" + this.id + "'";
								db.update(query);
							}
						}

					} else {
						query = "UPDATE ERP_DUYETMUAHANG " + "SET TRANGTHAI = '0' "
								+ "FROM ERP_DUYETMUAHANG DUYETMUAHANG "
								+ "INNER JOIN ERP_CHUCDANH CHUCDANH ON DUYETMUAHANG.CHUCDANH_FK = CHUCDANH.PK_SEQ "
								+ "WHERE DUYETMUAHANG.MUAHANG_FK = '" + this.id + "' AND CHUCDANH.NHANVIEN_FK = '"
								+ this.userId + "' ";
						db.update(query);
					}

					// Nếu là nguoi QD: cập nhật trang thai = 1
					/*if (isQD) {
						query = " UPDATE ERP_MUAHANG SET TRANGTHAI = '1' WHERE PK_SEQ = '" + this.id + "' ";

						if (!this.db.update(query)) {
							db.getConnection().rollback();
							return false;
						}

						query = "UPDATE ERP_DUYETMUAHANG " + "SET TRANGTHAI = '1' "
								+ "FROM ERP_DUYETMUAHANG DUYETMUAHANG "
								+ "INNER JOIN ERP_CHUCDANH CHUCDANH ON DUYETMUAHANG.CHUCDANH_FK = CHUCDANH.PK_SEQ "
								+ "WHERE DUYETMUAHANG.MUAHANG_FK = '" + this.id + "' AND CHUCDANH.NHANVIEN_FK = '"
								+ this.userId + "' ";
						System.out.println("duyet " + query);
						if (!this.db.update(query)) {
							db.getConnection().rollback();
							return false;
						}
					}*/
				}
			}
			// Thay đổi tiền hoặc ĐVTH thì mới bắt duyệt lại, nêu thay đổi thông
			// tin khác thì không cần
			else // Thay đổi tiền hoặc ĐVTH thì mới bắt duyệt lại, nêu thay đổi
					// thông tin khác thì không cần
			{

				boolean vuotNganSach = false;
				if (this.lhhId.equals("2")) // chi phi, dich vu
				{
					query = " select isnull(ngansachConLai.conLai, 0) - muahang.tongGiaTri as cotheSuDung  " + "from "
							+ "( " + "select CHIPHI_FK, SUM(SOLUONG * DONGIA) as tongGiaTri  "
							+ "from ERP_MUAHANG_SP where MUAHANG_FK = '" + this.id + "' group by CHIPHI_FK  " + ")  "
							+ "muahang left join " + "( "
							+ "select nganSach.CHIPHI_FK, isnull(nganSach.DUTOAN, 0) - ISNULL( dukienChi.tongduKien, 0) as conLai  "
							+ "from " + "( " + "select CHIPHI_FK, DUTOAN, THUCCHI   " + "from ERP_LAPNGANSACH_CHIPHI   "
							+ "where LAPNGANSACH_FK in ( select pk_seq from ERP_LAPNGANSACH where NAM = '" + nam
							+ "' and TRANGTHAI = '1' )   " + "and DONVITHUCHIEN_FK = '" + this.dvthId + "'   " + ")  "
							+ "nganSach left join " + "(  "
							+ "select CHIPHI_FK, SUM(b.SOLUONG * b.DONGIAVIET) as tongduKien  "
							+ "from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK  "
							+ "where a.LOAIHANGHOA_FK = '2' and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'   "
							+ "and SUBSTRING(ngaymua, 0, 5) = '" + nam + "' and a.pk_seq != '" + this.id + "'  "
							+ "group by CHIPHI_FK  " + ") " + "dukienChi on nganSach.CHIPHI_FK = dukienChi.CHIPHI_FK   "
							+ ")  " + "ngansachConLai on muahang.CHIPHI_FK = ngansachConLai.CHIPHI_FK";

					System.out.println("___Check ngan sach chi phi: " + query);

					ResultSet rsCheckNS = db.get(query);
					while (rsCheckNS.next()) {
						if (rsCheckNS.getDouble("cotheSuDung") < 0) {
							vuotNganSach = true;
						}
					}
					rsCheckNS.close();

				} else {
					if (this.lhhId.equals("1")) // Tai san co dinh
					{
						/*query = "select b.pk_seq as taisan_fk, a.SoLuong, a.DonGia, a.THANHTIEN as tongNganSach, a.SOTHANGKH, T1, T2, T3, T4, T5, T6, T7, T8, T9, T10, T11, T12   "
								+ "from ERP_LAPNGANSACH_TAISAN a inner join ERP_TAISANCODINH b on a.PK_SEQ = b.LAPNGANSACH_TAISAN_FK  "
								+ "where DONVITHUCHIEN_FK = '" + this.dvthId
								+ "' and b.pk_seq in ( select TAISAN_FK from ERP_MUAHANG_SP where MUAHANG_FK = '"
								+ this.id + "' )";

						System.out.println("1.Check ngan sach tai san: " + query);

						ResultSet rsNgansach = db.get(query);
						while (rsNgansach.next()) {
							String taisanId = rsNgansach.getString("taisan_fk");
							double ngansachTong = rsNgansach.getDouble("tongNganSach");
							double tongKhauhao = 0;

							int sothangKH = rsNgansach.getInt("SOTHANGKH");
							double dongia = rsNgansach.getDouble("DonGia");

							int thangthu = 0;
							for (int i = 1; i <= 12; i++) {
								int T1 = rsNgansach.getInt("T" + Integer.toString(i));
								if (T1 > 0) {
									double khaukhao_thang = ((T1 * dongia) / sothangKH) * (12 - thangthu);
									thangthu++;

									tongKhauhao += khaukhao_thang;
								}
							}

							// Lay tat cac cac mua hang cua tai san nay, tinh
							// tong Ngansach da su dung va tong khau hao (du
							// kien)
							query = "select a.NGAYMUA, b.SOLUONG, b.DONGIAVIET   "
									+ "from ERP_MUAHANG a inner join ERP_MUAHANG_SP b on a.PK_SEQ = b.MUAHANG_FK where b.TAISAN_FK = '"
									+ taisanId + "' and a.DONVITHUCHIEN_FK = '" + this.dvthId + "'";

							System.out.println("2.Check ngan sach tai san da su dung: " + query);
							ResultSet rsTaisan = db.get(query);

							double tongNganSach_Mua = 0;
							double tongKhauHao_Mua = 0;

							if (rsTaisan != null) {
								while (rsTaisan.next()) {
									String thangbdKhauHao_DuKien = rsTaisan.getString("NGAYMUA").split("-")[1];

									int soluongMua = rsTaisan.getInt("SOLUONG");
									double dongiaMua = rsTaisan.getDouble("DONGIAVIET");

									tongNganSach_Mua += soluongMua * dongiaMua;

									tongKhauHao_Mua += (soluongMua * dongiaMua / sothangKH)
											* (12 - Integer.parseInt(thangbdKhauHao_DuKien));
								}
								rsTaisan.close();
							}

							System.out.println(
									"____Tong ngan sach: " + ngansachTong + "   -- Ngan sach mua: " + tongNganSach_Mua);
							System.out.println(
									"____Tong khau hao: " + tongKhauhao + "   -- Ngan sach mua: " + tongKhauHao_Mua);

							if ((tongNganSach_Mua > ngansachTong) || (tongKhauHao_Mua > tongKhauhao)) {
								vuotNganSach = true;
								rsNgansach.close();
								break;
							}

						}
						rsNgansach.close();

						System.out.println("3.Check tai san vuot ngan sach: " + vuotNganSach);*/
					}
				}

				query = "Update ERP_MUAHANG  set VUOTNGANSACH = '" + (vuotNganSach == true ? "1" : "0")
						+ "' where pk_seq = '" + this.id + "' ";
				if (!db.update(query)) {
					this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}

				// PO trong nước sau khi phân bổ OR PO mua CP/TS/CCDC
				if ((this.loai.equals("1") && this.isDuocPhanBo.equals("1")) || this.loai.equals("0") || this.loai.equals("2")) {
					query = "DELETE FROM ERP_DUYETMUAHANG WHERE MUAHANG_FK = '" + this.id + "'";
					this.db.update(query);

					query = this.CreateChinhSachDuyet();

					System.out.println("3.Duyet PO:" + query);

					rs = db.get(query);

					while (rs.next()) {
						query = "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) "
								+ "VALUES('" + this.id + "', '" + rs.getString("CHUCDANH_FK") + "', '0','"
								+ rs.getString("QUYETDINH") + "') ";

						// System.out.println("4. Insert Duyet PO:" + query);
						if (!db.update(query)) {
							this.msg = "Khong the them nguoi duyet cho PO: " + query;
							db.getConnection().rollback();
							return false;
						}

					} /*else {
						query = "update ERP_MUAHANG set trangthai = 1 where pk_seq = '" + this.id + "'";
						if (!db.update(query)) {
							this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
							db.getConnection().rollback();
							return false;
						}
						rs.close();
					}*/

				} else {

					query = this.CreateChinhSachDuyet();

					System.out.println("3.Duyet PO:" + query);

					rs = db.get(query);

					while (rs.next()) {

						query = "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) "
								+ "VALUES('" + this.id + "', '" + rs.getString("CHUCDANH_FK") + "', '0','"
								+ rs.getString("QUYETDINH") + "') ";

						// System.out.println("4. Insert Duyet PO:" + query);
						if (!db.update(query)) {
							this.msg = "Khong the them nguoi duyet cho PO: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}

				// Duyệt trong đơn
				if (this.duyetIds != null) {
					int n = this.duyetIds.length;
					if (isVuotNo || vuotNganSach == true) {
						if (!isQD) // Khong phai nguoi Quyet Dinh >> Bao loi
						{
							this.msg = "Đơn mua hàng này đã vượt hạn mức nợ, bạn không được phép duyệt. Vui lòng thông báo cấp trên duyệt.";
							db.getConnection().rollback();
							return false;
						} else {
							for (int i = 0; i < n; i++) {
								query = "UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = '1' " + "WHERE CHUCDANH_FK = '"
										+ this.duyetIds[i] + "' AND MUAHANG_FK = '" + this.id + "'";
								db.update(query);
							}
						}
					} else {
						for (int i = 0; i < n; i++) {
							query = "UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = '1' " + "WHERE CHUCDANH_FK = '"
									+ this.duyetIds[i] + "' AND MUAHANG_FK = '" + this.id + "'";
							db.update(query);
						}
					}
				}

			}

			query = "delete ERP_MUAHANG_SP_HOADON where muahang_fk=" + this.id;
			if (!db.update(query)) {
				this.msg = "Khong the tao moi Mua hang - San pham: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = " INSERT INTO   ERP_MUAHANG_SP_HOADON   (	MUAHANG_FK ,MUAHANG_SP_FK  ,MAHOADON  ,	MAUSOHOADON  ,	KYHIEU  ,SOHOADON  ,NGAYHOADON , "
					+ " TENNHACUNGCAP  ,MASOTHUE  ,TIENHANG  ,	THUESUAT  ,	TIENTHUE  ,	TONGCONG   ,GHICHU   )    "
					+ " select mhsp.MUAHANG_FK,mhsp.PK_SEQ,'','','','','','','',mhsp.THANHTIEN,mh.VAT,mhsp.THANHTIEN*mh.VAT/100,mhsp.THANHTIEN+mhsp.THANHTIEN*mh.VAT/100,'' "
					+ " from ERP_MUAHANG_SP mhsp inner join ERP_MUAHANG mh on mh.PK_SEQ=mhsp.MUAHANG_FK "
					+ " where MUAHANG_FK not in (select MUAHANG_FK from ERP_MUAHANG_SP_HOADON) "
					+ " and mh.LOAIHANGHOA_FK='2' and mh.pk_seq=" + this.id;
			if (!db.update(query)) {
				this.msg = "Khong the tao moi Mua hang - San pham: " + query;
				db.getConnection().rollback();
				return false;
			}

			// CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_DMH", new String[] { this.id });

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Khong the cap nhat don hang " + query;
			return false;
		}
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<IDonvi> getDvList() {
		return this.dvList;
	}

	public void setDvList(List<IDonvi> dvList) {
		this.dvList = dvList;
	}

	public List<IKho> getKhoList() {
		return this.khoList;
	}

	public void setKhoList(List<IKho> khoList) {
		this.khoList = khoList;
	}

	public List<ITiente> getTienteList() {
		return this.tienteList;
	}

	public void setTienteList(List<ITiente> ttList) {
		this.tienteList = ttList;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void close() {
		try {

			if (this.dvthRs != null) {
				this.dvthRs.close();
			}
			if (this.lhhRs != null) {
				this.lhhRs.close();
			}
			if (spList != null) {
				spList.clear();
			}
			if (dvList != null) {
				dvList.clear();
			}

			if (tienteList != null) {
				tienteList.clear();
			}
			if (khoList != null) {
				khoList.clear();
			}
			if (ListTTCP != null) {
				ListTTCP.clear();
			}
			this.db.shutDown();
		} catch (SQLException e) {

		}

	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getDungsai() {
		return this.dungsai;
	}

	public void setDungsai(String dungsai) {
		this.dungsai = dungsai;
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	public void setNguonGocHH(String nguongoc) {
		this.NguonGocHH = nguongoc;
	}

	public String getNguonGocHH() {
		return this.NguonGocHH;
	}

	public void setMaLoaiHH(String maloaihh) {
		this.MaLoaiHH = maloaihh;

	}

	public String getMaLoaiHH() {

		return this.MaLoaiHH;
	}

	public void setTienTe_FK(String tiente_fk) {
		this.TienTe_FK = tiente_fk;

	}

	public String getTienTe_FK() {

		return this.TienTe_FK;
	}

	public String getGhiChu() {

		return this.GhiChu;
	}

	public void setGhiChu(String ghichu) {

		this.GhiChu = ghichu;
	}

	public void setTrungTamChiPhi_FK(String trungtamchiphi_fk) {
		this.TrungTamChiPhi_FK = trungtamchiphi_fk;
	}

	public String getTrungTamChiPhi_FK() {

		return this.TrungTamChiPhi_FK;
	}

	public void CreateListTrungTamChiPhi() {
		String query = "Select PK_SEQ,Ma,Ten From Erp_TrungTamChiPhi Where TrangThai=1";
		ResultSet rsTTCP = this.db.get(query);
		try {
			while (rsTTCP.next()) {
				ITrungTamChiPhi o = new TrungTamChiPhi();
				o.setId(rsTTCP.getString("PK_SEQ"));
				o.setMaChiPhi(rsTTCP.getString("Ma"));
				o.setTen(rsTTCP.getString("Ten"));
				this.ListTTCP.add(o);
			}
			rsTTCP.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<ITrungTamChiPhi> getTrungTamCpList() {

		return this.ListTTCP;
	}

	public void setTrungTamCpList(List<ITrungTamChiPhi> ttcp) {
		this.ListTTCP = ttcp;
	}

	public void setTyGiaQuyDoi(float tygiaquydoi) {
		this.TyGiaQuyDoi = tygiaquydoi;
	}

	public Float GetTyGiaQuyDoi() {

		return this.TyGiaQuyDoi;
	}

	public ResultSet getDuyet() {
		ResultSet rs;
		String query = "SELECT DUYETMUAHANG.CHUCDANH_FK, CHUCDANH.DIENGIAI, DUYETMUAHANG.TRANGTHAI, "
				+ "CASE WHEN (SELECT ISNULL(DACHOT,0) FROM ERP_MUAHANG WHERE PK_SEQ = DUYETMUAHANG.MUAHANG_FK) = 1 AND CHUCDANH.NHANVIEN_FK = '"
				+ this.userId + "' THEN '1' " + "ELSE '0' END AS QUYEN " + "FROM ERP_DUYETMUAHANG DUYETMUAHANG "
				+ "INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK "
				+ "WHERE DUYETMUAHANG.MUAHANG_FK = '" + this.id + "'";
		System.out.println(query);
		rs = this.db.get(query);
		return rs;
	}

	public String getTrangthaiDuyet() {
		String result = "Chờ duyệt";

		String query = "SELECT " + "	CASE WHEN SUM(QUYETDINH) > 0 THEN  " + "("
				+ "	SELECT COUNT(TRANGTHAI) - SUM(TRANGTHAI) " + "	FROM ERP_DUYETMUAHANG " + "	WHERE MUAHANG_FK = '"
				+ this.id + "' AND QUYETDINH = 1 " + ") " + "ELSE " + "	COUNT(TRANGTHAI) - SUM(TRANGTHAI) "
				+ "END AS RESULT " + "FROM ERP_DUYETMUAHANG " + "WHERE MUAHANG_FK = '" + this.id + "'";

		System.out.println("Trang thai duyet" + query);

		ResultSet rs = this.db.get(query);
		try {
			if (rs != null) {
				if (rs.next()) {
					String tmp = rs.getString("RESULT");
					if (tmp != null) {
						if (tmp.equals("0"))
							result = "Đã duyệt";
					} else {
						result = "Không cần duyệt";
					}
					rs.close();
				} else {
					result = "Không cần duyệt";
				}
			} else {
				result = "Không cần duyệt";
			}

		} catch (SQLException e) {
		}

		return result;

	}

	public String getLoaihanghoa() {
		return this.lhhId;
	}

	public void setLoaihanghoa(String loaihh) {
		this.lhhId = loaihh;
	}

	public String getIsdontrahang() {
		return this.isdontrahang;
	}

	public void setIsdontrahang(String dontrahang) {
		this.isdontrahang = dontrahang;
	}

	public String getMakeToStock() {
		return this.maketoStock;
	}

	public void setMakeToStock(String maketoStock) {
		this.maketoStock = maketoStock;
	}

	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getKhoId() {
		return this.khoId;
	}

	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}

	public String getNccTen() {

		return this.nccTen;
	}

	public void setNccTen(String nccTen) {

		this.nccTen = nccTen;
	}

	public String getNccLoai() {

		return this.nccLoai;
	}

	public void setNccLOai(String nccLoai) {

		this.nccLoai = nccLoai;
	}

	public String getCanDuyet() {
		if (this.id != null && this.id.length() > 0) {
			String sql = "select DACHOT from  ERP_MUAHANG  where pk_seq =" + this.id;
			ResultSet rs = db.get(sql);
			try {
				if (rs.next()) {
					this.canduyet = rs.getString("DACHOT");
				}
				rs.close();
			} catch (Exception er) {
				er.printStackTrace();
			}

		}
		return this.canduyet;
	}

	public void setCanDuyet(String canduyet) {

		this.canduyet = canduyet;
	}

	public void setQuanlycongno(String quanlyCN) {

		this.quanlyCN = quanlyCN;
	}

	public String getQuanlycongno() {

		return this.quanlyCN;
	}

	public String getSoThamChieu() {

		return this.sothamchieu;
	}

	public void setSoThamChieu(String sothamchieu) {

		this.sothamchieu = sothamchieu;
	}

	public String[] getGhiChuArr() {
		return this.ghichuArr;
	}

	public void setGhiChuArr(String[] ghichuArr) {
		this.ghichuArr = ghichuArr;
	}

	public String getETD() {
		return this.ETD;
	}

	public void setETD(String ETD) {
		this.ETD = ETD;
	}

	public String getETA() {
		return this.ETA;
	}

	public void setETA(String ETA) {
		this.ETA = ETA;
	}

	public String[] getCpkDienGiai() {

		return this.cpkDiengiai;
	}

	public void setCpkDiengiai(String[] cpkDD) {

		this.cpkDiengiai = cpkDD;
	}

	public String[] getCpkSoTien() {

		return this.cpkSotien;
	}

	public void setCpkSoTien(String[] cpkST) {

		this.cpkSotien = cpkST;
	}

	public String getDiaDiemGiaoHang() {
		return this.diadiemgiaohang;
	}

	public void setDiaDiemGiaoHang(String ddgh) {
		this.diadiemgiaohang = ddgh;
	}

	public String getmaDMH() {

		return this.maDMH;
	}

	public void setmaDMH(String maDMH) {
		this.maDMH = maDMH;

	}

	public String getDutoanId() {
		return this.dutoanId;
	}

	public void setDutoanId(String dutoanId) {
		this.dutoanId = dutoanId;
	}

	public String getLoai() {
		return this.loai;
	}

	public void setLoai(String Loai) {
		this.loai = Loai;
	}

	public String getLoaiDMH_NK() {
		return this.loaiDMH_NK;
	}

	public void setLoaiDMH_NK(String loaiDMH_NK) {
		this.loaiDMH_NK = loaiDMH_NK;
	}

	public String getThoihanno() {
		return this.thoihanno;
	}

	public void setThoihanno(String thoihanno) {
		this.thoihanno = thoihanno;
	}

	public String getIsDuocPhanBo() {
		return this.isDuocPhanBo;
	}

	public void setIsDuocPhanBo(String isDuocPhanBo) {
		this.isDuocPhanBo = isDuocPhanBo;
	}

	public String getKenhId() {
		return this.kenhId;
	}

	public void setKenhId(String kenhId) {
		this.kenhId = kenhId;
	}

	public void setKenhRs(ResultSet kenhRs) {
		this.kenhRs = kenhRs;
	}

	public ResultSet getKenhRs() {
		return this.kenhRs;
	}

	@Override
	public String getSohopdong() {
		return this.sohopdong;
	}

	@Override
	public void setSohopdong(String sohopdong) {
		this.sohopdong = sohopdong;
	}

	@Override
	public String getSoluong() {
		return this.soluong;
	}

	@Override
	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	@Override
	public String getTennhanhapkhau() {
		return this.tennhank;
	}

	@Override
	public void setTennhanhapkhau(String tennhank) {
		this.tennhank = tennhank;
	}

	@Override
	public String getTennhasanxuat() {
		return this.tennhasx;
	}

	@Override
	public void setTennhasanxuat(String tennhasx) {
		this.tennhasx = tennhasx;
	}

	@Override
	public String getNgayship() {
		return this.ngayship;
	}

	@Override
	public void setNgayship(String ngayship) {
		this.ngayship = ngayship;
	}

	@Override
	public String getNgaynhapkho() {
		return this.ngaynhapkho;
	}

	@Override
	public void setNgaynhapkho(String ngaynhapkho) {
		this.ngaynhapkho = ngaynhapkho;
	}

	@Override
	public String getDvChiuTrachNhiem() {
		return this.dvchiutrachnhiem;
	}

	@Override
	public void setDvChiuTrachNhiem(String dvchiutrachnhiem) {
		this.dvchiutrachnhiem = dvchiutrachnhiem;
	}

	@Override
	public ResultSet getThttRs() {
		return this.thttRs;
	}

	@Override
	public void setThttRs(ResultSet thttRs) {
		this.thttRs = thttRs;
	}
}
