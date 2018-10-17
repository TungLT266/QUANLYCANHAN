package geso.traphaco.erp.beans.phanbomuahang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phanbomuahang.imp.*;
import geso.traphaco.erp.beans.phanbomuahang.*;

public class ErpPhanbomuahang implements IErpPhanbomuahang {

	String congtyId;
	String nppId;
	String userId;
	String id;
	List<ISanpham> lstSp;
	ResultSet ctRs;
	String trangthai;
	String diengiai;
	String dmhId;
	ResultSet dmhRs;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String dungsai;
	String vat;
	String ngaymua;
	String dvth;
	String ncc;
	dbutils db;
	Utility util;
	String active;
	String msg;

	public ErpPhanbomuahang(String[] param) {
		this.db = new dbutils();
		this.id = param[0];
		this.dmhId = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.diengiai = param[6];
		this.trangthai = param[7];
		this.msg = "";
		this.active = "0";
		this.dungsai = "";
		this.vat = "";
		this.ngaymua = "";
		this.dvth = "";
		this.ncc = "";
		this.util = new Utility();
	}

	public ErpPhanbomuahang(String id) {
		this.congtyId = "";
		this.nppId = "";
		this.userId = "";
		this.id = id;
		this.dmhId = "";
		this.trangthai = "1";
		this.diengiai = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.msg = "";
		this.active = "0";
		this.dungsai = "";
		this.vat = "";
		this.ngaymua = "";
		this.dvth = "";
		this.ncc = "";
		this.util = new Utility();
		this.db = new dbutils();
		if (id.length() > 0)
			this.init();
	}

	@Override
	public String getCongtyId() {

		return this.congtyId;
	}

	@Override
	public void setCongtyId(String congtyId) {

		this.congtyId = congtyId;
	}

	@Override
	public String getUserId() {

		return this.userId;
	}

	@Override
	public void setUserId(String userId) {

		this.userId = userId;
	}

	@Override
	public String getId() {

		return this.id;
	}

	@Override
	public void setId(String id) {

		this.id = id;
	}

	@Override
	public String getTrangthai() {

		return this.trangthai;
	}

	@Override
	public void setTrangthai(String trangthai) {

		this.trangthai = trangthai;
	}

	@Override
	public String getDiengiai() {

		return this.diengiai;
	}

	@Override
	public void setDiengiai(String diengiai) {

		this.diengiai = diengiai;
	}

	public void createRs() {

		// erp_muahang isduocphanbo 1: don mua hang con, 2: don mua hang cha
		String query = ""; 
		/*query = "select pk_seq, sopo, vat, dungsai, ngaymua, donvithuchien_fk, nhacungcap_fk from erp_muahang "
				+ "where loai = 1 and PK_SEQ not in (select PK_SEQ from ERP_MUAHANG where ISDUOCPHANBO = '1' or ISDUOCPHANBO = '2')";*/
		
		query = "	select pk_seq, sopo, vat, dungsai, ngaymua, donvithuchien_fk, nhacungcap_fk from erp_muahang \n"+
				"	where loai = 1 and PK_SEQ in (select distinct c.MUAHANG_FK \n"+
				"	from erp_nhapkhonhamay_sp_chitiet a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ \n"+
				"	INNER JOIN erp_nhapkhonhamay c on a.nhamay_fk = c.pk_seq where c.MUAHANG_FK = erp_muahang.pk_seq and c.trangthai = 1 \n"+
				"	and a.SOLUONG - ISNULL((select SUM(SOLUONG) from ERP_PHANBOMUAHANG_SP_CHITIET ct inner join ERP_PHANBOMUAHANG t on ct.PHANBO_FK = t.PK_SEQ \n"+
				"	where t.muahang_fk = c.MUAHANG_FK and SANPHAM_FK = a.SANPHAM_FK and SOLO = a.SOLO), 0) > 0) and (ISDUOCPHANBO <> 1 or ISDUOCPHANBO is null)";
				
		if (this.id.trim().length() > 0)
			query = "select pk_seq, sopo, vat, dungsai, ngaymua, donvithuchien_fk, nhacungcap_fk from erp_muahang where loai = 1 and pk_seq = '" + this.dmhId + "'";
		System.out.println("don hang " + query);
		this.dmhRs = db.get(query);

		query = "select PK_SEQ, MA, TEN from ERP_CONGTY a ";
		// query += " where isTongCongTy = 0";
		this.ctRs = db.get(query);

		if (dmhId.trim().length() > 0) {
			query = "select pk_seq, sopo, vat, dungsai, ngaymua, donvithuchien_fk, nhacungcap_fk from erp_muahang where loai = 1 and pk_seq = '"+ this.dmhId + "'";
			System.out.println("don mua hang " + query);
			ResultSet rs = db.get(query);
			if (rs != null) {
				try {
					while (rs.next()) {
						this.ngaymua = rs.getString("ngaymua") == null ? "" : rs.getString("ngaymua");
						this.vat = rs.getString("vat") == null ? "0" : rs.getString("vat");
						this.dungsai = rs.getString("dungsai") == null ? "" : rs.getString("dungsai");
						this.dvth = rs.getString("donvithuchien_fk") == null ? "" : rs.getString("donvithuchien_fk");
						this.ncc = rs.getString("nhacungcap_fk") == null ? "" : rs.getString("nhacungcap_fk");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			query = "select b.pk_seq, a.solo, a.SOLUONG - ISNULL((select SUM(SOLUONG) from ERP_PHANBOMUAHANG_SP_CHITIET ct "
					+ "inner join ERP_PHANBOMUAHANG t on ct.PHANBO_FK = t.PK_SEQ "
					+ "where t.muahang_fk = c.MUAHANG_FK and ct.SANPHAM_FK = a.SANPHAM_FK and ct.SOLO = a.SOLO ";
			if(this.id.trim().length() > 0)
				query += "and phanbo_fk < "+this.id;
			query += "), 0) as soluong"
					+ ", a.DONVI, a.gia AS DONGIA, a.nhomkenh_fk, b.TEN, b.MA, isnull(b.thuexuat, '0') as thuexuat "
					+ "from erp_nhapkhonhamay_sp_chitiet a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ "
					+ "INNER JOIN erp_nhapkhonhamay c on a.nhamay_fk = c.pk_seq " + "where c.MUAHANG_FK = '" + this.dmhId + "' and c.trangthai = 1";
			System.out.println("danh sach san pham " + query);

			rs = db.get(query);
			try {
				List<ISanpham> lstSP = new ArrayList<ISanpham>();
				if (rs != null) {
					while (rs.next()) {

						ISanpham sp = new Sanpham();
						sp.setId(rs.getString("pk_seq"));
						sp.setMasanpham(rs.getString("ma"));
						sp.setTensanpham(rs.getString("ten"));
						sp.setSolo(rs.getString("solo"));
						sp.setSoluong(rs.getString("soluong"));
						sp.setDonvi(rs.getString("donvi"));
						sp.setDongia(rs.getString("dongia"));
						sp.setNhomkenh(rs.getString("nhomkenh_fk"));
						sp.setThuexuat(rs.getString("thuexuat"));
						query = "select PK_SEQ, MA, TEN from ERP_CONGTY a ";
						// query += " where isTongCongTy = 0";
						System.out.println("danh sach congty " + query);
						ResultSet rsct = db.get(query);
						if (rsct != null) {
							List<ICongty> lstCt = new ArrayList<ICongty>();
							while (rsct.next()) {
								ICongty ct = new Congty();
								ct.setId(rsct.getString("pk_seq"));
								ct.setMacongty(rsct.getString("ma") == null ? "" : rsct.getString("ma"));
								ct.setTencongty(rsct.getString("ten") == null ? "" : rsct.getString("ten"));
								if (this.id.trim().length() > 0) {
									query = "select sum(a.SOLUONG) as soluong "
											+ "from ERP_PHANBOMUAHANG_SP_Chitiet a inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ "
											+ "where a.CONGTY_FK = '"+ ct.getId() + "' and b.PK_SEQ = '"+ this.id+ "' "
											+ "and a.SANPHAM_FK = '"+ sp.getId() + "' and a.solo = '"+ sp.getSolo() + "'"
											+ "group by a.sanpham_fk, a.congty_fk, a.phanbo_fk, a.solo";
									System.out.println("congty " + ct.getId() + " soluongpb " + query);
									ResultSet rsslsp = db.get(query);
									if (rsslsp != null) {
										while (rsslsp.next()) {
											ct.setSoluongpb(rsslsp.getString("soluong"));
										}
									}
								}
								lstCt.add(ct);
							}
							sp.setCongty(lstCt);
						}
						System.out.println("pk_seq cua san pham " + sp.getId());
						lstSP.add(sp);
					}
					this.lstSp = lstSP;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public void init() {

		String query = "select a.pk_seq, a.diengiai, a.trangthai, a.ngaytao, b.ten as nguoitao, a.ngaysua, c.ten as nguoisua, a.muahang_fk from erp_phanbomuahang a, nhanvien b, nhanvien c ";
		query = query + " where a.nguoitao = b.pk_seq and a.nguoisua = c.pk_seq and a.pk_seq = '" + this.id + "'";
		System.out.println("Cau init " + query);
		ResultSet rs = this.db.get(query);
		try {
			while (rs.next()) {
				this.id = rs.getString("pk_seq");
				this.diengiai = rs.getString("diengiai") == null ? "" : rs.getString("diengiai");
				this.trangthai = rs.getString("trangthai") == null ? "0" : rs.getString("trangthai");
				this.ngaytao = rs.getString("ngaytao");
				this.nguoitao = rs.getString("nguoitao");
				this.ngaysua = rs.getString("ngaysua");
				this.nguoisua = rs.getString("nguoisua");
				this.dmhId = rs.getString("muahang_fk");
			}
			rs.close();
		} catch (java.sql.SQLException e) {
		}

		this.createRs();
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public boolean createTimNcc() {
		try {
			this.nppId = util.getIdNhapp(userId);
			if (this.dmhId.trim().length() <= 0) {
				this.msg = "Vui lòng chọn đề nghị đặt hàng";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			String query = "insert into erp_phanbomuahang(trangthai, ngaytao, nguoitao, ngaysua, nguoisua, diengiai, MUAHang_FK, congty_fk, NHAPHANPHOI_FK) "
					+ "values(0, '"+ this.getDateTime()+ "', '"+ this.userId+ "', '"+ this.getDateTime()+ "', '"+ this.userId+ "', N'"+ this.diengiai+ "', '"
					+ this.dmhId+ "', '"+ this.congtyId + "', '" + this.nppId + "' )";

			System.out.println("1.Insert: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the tao moi erp_phanbomuahang: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "update erp_muahang set isduocphanbo = '2' where pk_seq = '"+ this.dmhId + "' ";

			System.out.println("1.Insert: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the tao moi erp_phanbomuahang: " + query;
				db.getConnection().rollback();
				return false;
			}
			NumberFormat formatter = new DecimalFormat("#,###,###");
			query = "select IDENT_CURRENT('erp_phanbomuahang') as Id";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("Id");
			rs.close();
			List<String> idmuahangs = new ArrayList<String>();

			query = "select PK_SEQ, MA, TEN from ERP_CONGTY a ";
			// query += " where isTongCongTy = 0";
			System.out.println("danh sach congty " + query);
			ResultSet rsct = db.get(query);
			String idCt = "";
			if (rsct != null) {
				while (rsct.next()) {
					idCt = rsct.getString("pk_seq");
					double tongtien = 0;
					String querysp = "";
					if (this.lstSp != null) {
						for (int i = 0; i < this.lstSp.size(); i++) {
							ISanpham sp = this.lstSp.get(i);

							double soluongpb = 0;
							double dongia = Double.parseDouble(sp.getDongia());
							List<ICongty> lstct = sp.getCongty();
							for (int j = 0; j < lstct.size(); j++) {
								ICongty ct = lstct.get(j);
								if (ct.getId().trim().equals(idCt.trim())) {
									soluongpb = Double.parseDouble(ct.getSoluongpb());
									if (soluongpb > 0) {
										double thanhtien = dongia * soluongpb;
										tongtien += thanhtien;
										querysp += "select '"+ formatter.format(dongia).replace(",", "")+ "' as dongia, "
												+ formatter.format(soluongpb).replace(",", "")+ " as soluong, "
												+ formatter.format(thanhtien).replace(",", "")+ " as thanhtien, N'"
												+ sp.getDonvi()+ "' as donvi, '"+ sp.getId().trim()+ "' as sp_fk, '"
												+ sp.getTensanpham()+ "' as diengiai, '"+ sp.getNhomkenh()
												+ "' as nhomkenh, '"+ sp.getThuexuat()+ "' as thuexuat, '"+ sp.getSolo()+ "' as solo union ";

										query = "insert into erp_phanbomuahang_sp_chitiet(phanbo_fk, congty_fk, sanpham_fk, solo, soluong, donvi, dongia) "
												+ "select "+ this.id+ ", "+ idCt+ ", "+ sp.getId().trim()+ ", '"+ sp.getSolo()+ "', "+ formatter.format(soluongpb).replace(",", "")
												+ ", N'"+ sp.getDonvi()+ "', '"+ formatter.format(dongia).replace(",", "") + "'";
										System.out.println("2.Insert: " + query);
										if (!db.update(query)) {
											this.msg = "Khong the tao moi erp_phanbomuahang: "
													+ query;
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
						}

						if (tongtien > 0) {
							double vat = Double.parseDouble(this.vat);
							double tongtienbvat = tongtien;
							tongtien = tongtien * (1 + vat / 100);

							/*
							 * String nam = this.ngaymua.substring(0, 4); String
							 * thang = this.ngaymua.substring(5, 7);
							 * 
							 * query=
							 * " SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "
							 * + " WHERE PK_SEQ ="+this.dvth+" ) AS PREFIX   "+
							 * " FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = "
							 * +nam+ " and     DMH.DONVITHUCHIEN_FK="+this.dvth;
							 * System.out.println("Du lieu po sai  :"+query);
							 * String soPO = ""; int sotutang_theonam = 0;
							 * ResultSet rsPO = db.get(query); if(rsPO.next()) {
							 * sotutang_theonam = (rsPO.getInt("maxSTT") + 1 );
							 * String prefix = rsPO.getString("PREFIX"); String
							 * namPO = this.ngaymua.substring(2, 4); String
							 * chuoiso= ("0000"+
							 * Integer.toString(sotutang_theonam
							 * )).substring(("0000"+
							 * Integer.toString(sotutang_theonam
							 * )).length()-4,("0000"+
							 * Integer.toString(sotutang_theonam)).length());
							 * 
							 * soPO = prefix + "-" + chuoiso+ "/" + thang + "/"
							 * + namPO;
							 * 
							 * } rsPO.close();
							 */

							query = "insert into erp_muahang(ngaymua, donvithuchien_fk, nhacungcap_fk, loaihanghoa_fk, ngaytao, nguoitao, ngaysua, nguoisua, tiente_fk, "
									+ "congty_fk, dachot, isduocphanbo, loai, tongtienbvat, vat, tongtienavat, trangthai, dungsai )"
									+ " values ('"+ this.ngaymua+ "', '"+ this.dvth+ "', '"+ this.ncc+ "', '0', '"+ this.getDateTime()+ "', '"+ this.userId+ "', '"
									+ this.getDateTime()+ "', '"+ this.userId+ "', "+ "'100000', '"+ idCt+ "', '1', '1', '1', '"
									+ formatter.format(tongtienbvat).replace(",", "")+ "', '"+ this.vat+ "', '"+ formatter.format(tongtien).replace(",","")+ "', '1', '"
									+ this.dungsai+ "')";
							System.out.println("1.Insert erp_muahang: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: "
										+ query;
								db.getConnection().rollback();
								return false;
							}

							query = "select IDENT_CURRENT('erp_muahang') as Id";
							rs = db.get(query);
							rs.next();
							String idmuahang = rs.getString("Id");
							rs.close();

							query = "update erp_phanbomuahang_sp_chitiet set muahang_fk = '" + idmuahang + "' where phanbo_fk = '" + this.id + "' "+
									"and congty_fk = "+idCt+" and muahang_fk is null";
							System.out.println("1.Insert erp_muahang: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: "
										+ query;
								db.getConnection().rollback();
								return false;
							}
							
							query = "update erp_muahang set sopo = '" + idmuahang + "' where pk_seq = '" + idmuahang + "'";
							System.out.println("1.Insert erp_muahang: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: " + query;
								db.getConnection().rollback();
								return false;
							}

							System.out.println("chi tiet don hang " + querysp);
							query = "insert into erp_muahang_sp(diengiai, MUAHang_FK, sanpham_fk, soluong, dongia, soluong_new, dongia_new, thanhtien, dongiaviet, "
									+ "thanhtienviet, donvi, nhomkenh_fk, thuexuat) "
									+ "select diengiai, '"+ idmuahang+ "', sp_fk, sum(soluong), dongia, sum(soluong), dongia,sum(thanhtien), dongia, sum(thanhtien), "
									+ "donvi, nhomkenh, thuexuat from ("+ querysp.substring(0, querysp.length() - 6)+ ")as sp group by diengiai, sp_fk, dongia, donvi, nhomkenh, thuexuat";

							System.out.println("1.Insert erp_muahang_sp: "+ query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: "+ query;
								db.getConnection().rollback();
								return false;
							}

							query = "\n SELECT	NCC.CHUCDANH_FK, NCC.QUYETDINH FROM ERP_MUAHANG MUAHANG  "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n	WHERE MUAHANG.PK_SEQ = '"+ idmuahang+ "' "
									+ "\n	UNION ALL "
									// NẾU KO CÓ CHÍNH SÁCH DUYỆT DÀNH CHO SẢN
									// PHẨM CỦA ĐƠN MUA HÀNG VÀ KO CÓ CHÍNH SÁCH
									// DUYỆT CHO NCC CỦA ĐƠN MUA HÀNG
									+ "\n	SELECT SP.CHUCDANH_FK, SP.QUYETDINH "
									+ "\n	FROM ERP_MUAHANG MUAHANG  "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
									+ "\n	AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '"+ idmuahang+ "') "
									+ "\n LEFT JOIN "
									+ "\n (  "
									+ "\n		SELECT	COUNT(*) AS NUM "
									+ "\n		FROM ERP_MUAHANG MUAHANG   "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n		AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '"+ idmuahang+ "')  "
									+ "\n		WHERE MUAHANG.PK_SEQ = '"+ idmuahang+ "'   "
									+ "\n )DUYET_NCC ON 1 = 1 "
									+ "\n LEFT JOIN "
									+ "\n ( "
									+ "\n		SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM "
									+ "\n		FROM ERP_MUAHANG_SP MH_SP  "
									+ "\n		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
									+ "\n		WHERE MH.PK_SEQ = '"+ idmuahang+ "'  AND MH_SP.SANPHAM_FK NOT IN  "
									+ "\n	 	(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
									+ "\n )KTRA_SP ON 1 = 1 "
									+ "\n WHERE MUAHANG.PK_SEQ = '"+ idmuahang+ "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 "
									+ "\n UNION ALL "
									+ "\n SELECT CP.CHUCDANH_FK, CP.QUYETDINH "
									+ "\n FROM ERP_MUAHANG MUAHANG   "
									+ "\n INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n INNER JOIN ERP_CHINHSACHDUYET_CHIPHI CP ON CP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
									+ "\n INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CP.KHOANGCHIPHI_FK  "
									+ "\n LEFT JOIN( "
									+ "\n		SELECT	COUNT(SP.CHUCDANH_FK) AS NUM "
									+ "\n		FROM ERP_MUAHANG MUAHANG   "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
									+ "\n		AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '"+ idmuahang+ "') "
									+ "\n		LEFT JOIN "
									+ "\n		( "
									+ "\n			SELECT	COUNT(*) AS NUM "
									+ "\n			FROM ERP_MUAHANG MUAHANG   "
									+ "\n			INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n			INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n			AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '"+ idmuahang+ "')  "
									+ "\n			WHERE MUAHANG.PK_SEQ = '"+ idmuahang+ "'   "
									+ "\n		)DUYET_NCC ON 1 = 1 "
									+ "\n		LEFT JOIN "
									+ "\n		( "
									+ "\n			SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM "
									+ "\n			FROM ERP_MUAHANG_SP MH_SP  "
									+ "\n			INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
									+ "\n			INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
									+ "\n			WHERE MH.PK_SEQ = '" + idmuahang + "'  AND MH_SP.SANPHAM_FK NOT IN  "
									+ "\n			(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) " 
									+ "\n		)KTRA_SP ON 1 = 1 "
									+ "\n		WHERE MUAHANG.PK_SEQ = '" + idmuahang + "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 "
									+ "\n )DUYET_SP ON 1 = 1 "
									+ "\n LEFT JOIN( "
									+ "\n		SELECT	COUNT(NCC.CHUCDANH_FK) AS NUM "
									+ "\n		FROM ERP_MUAHANG MUAHANG   "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n		WHERE MUAHANG.PK_SEQ = '" + idmuahang + "'"
									+ "\n )DUYET_NCC ON 1 = 1 "
									+ "\n WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT*MUAHANG.TYGIAQUYDOI "
									+ "\n	AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT*MUAHANG.TYGIAQUYDOI OR KHOANGCHIPHI.SOTIENDEN IS NULL) "
									+ "\n	AND MUAHANG.PK_SEQ = '" + idmuahang + "' AND DUYET_SP.NUM = 0 AND DUYET_NCC.NUM = 0 ";

							System.out.println("3.Duyet PO....:" + query);

							rs = db.get(query);

							if (rs.next()) {

								query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = "+ rs.getString("CHUCDANH_FK") + " AND MUAHANG_FK = "+ idmuahang+ ")"
										+ "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) "
										+ "VALUES('"+ idmuahang+ "', '" + rs.getString("CHUCDANH_FK") + "', '0','"+ rs.getString("QUYETDINH") + "') ";

								System.out.println("4. Insert Duyet PO:"+ query);
								if (!db.update(query)) {
									this.msg = "Khong the them nguoi duyet cho PO: "+ query;
									db.getConnection().rollback();
									return false;
								}
							} 

							query = "insert into erp_phanbomuahang_po(PHANBO_FK, PODUOCPB) "
									+ "values('"+ this.id+ "', '"+ idmuahang+ "' )";

							System.out.println("1.Insert: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_timncc_ncc: "+ query;
								db.getConnection().rollback();
								return false;
							}
							
							//CẬP NHẬT TOOLTIP
							db.execProceduce2("CapNhatTooltip_DMH", new String[] { idmuahang } );
							
							idmuahangs.add(idmuahang);
						}
					}
				}
			}
			
			query = "select b.pk_seq, a.solo, a.SOLUONG - ISNULL((select SUM(SOLUONG) from ERP_PHANBOMUAHANG_SP_CHITIET ct \n"+ 
					"inner join ERP_PHANBOMUAHANG t on ct.PHANBO_FK = t.PK_SEQ where t.muahang_fk = c.MUAHANG_FK and ct.SANPHAM_FK = a.SANPHAM_FK \n"+ 
					"and ct.SOLO = a.SOLO and phanbo_fk < "+this.id+"), 0) as soluongdat,  \n"+
					"ISNULL((select SUM(SOLUONG) from ERP_PHANBOMUAHANG_SP_CHITIET ct  \n"+
					"inner join ERP_PHANBOMUAHANG t on ct.PHANBO_FK = t.PK_SEQ where t.muahang_fk = c.MUAHANG_FK and ct.SANPHAM_FK = a.SANPHAM_FK \n"+ 
					"and ct.SOLO = a.SOLO and phanbo_fk = "+this.id+"), 0) as soluongpb \n"+
					"from erp_nhapkhonhamay_sp_chitiet a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ INNER JOIN erp_nhapkhonhamay c on a.nhamay_fk = c.pk_seq \n"+ 
					"where c.MUAHANG_FK = "+this.dmhId+" and c.trangthai = 1";
			ResultSet rskt = db.get(query);
			double soluongdat = 0, soluongpb = 0;
			if(rskt != null)
			{
				while(rskt.next())
				{
					soluongdat = rskt.getDouble("soluongdat");
					soluongpb = rskt.getDouble("soluongpb");
					if(soluongdat < soluongpb)
					{
						this.msg = "Khong the phan bo: So luong phan bo lon hon so luong nhap kho.";
						db.getConnection().rollback();
						return false;
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			try {
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();

				this.msg = "Lỗi khi tạo mới phân bổ đơn hàng MTV: "
						+ e.getMessage();
			} catch (SQLException e1) {
			}

			return false;
		}
		return true;
	}

	public boolean updateTimNcc() {
		try {
			this.nppId = util.getIdNhapp(userId);
			if (this.dmhId.trim().length() <= 0) {
				this.msg = "Vui lòng chọn đề nghị đặt hàng";
				return false;
			}
			
			String query = "";
			query = "select COUNT(*) as num from ERP_PHANBOMUAHANG_PO where PHANBO_FK = "+this.id+" \n"+
					"and PODUOCPB in (select b.MUAHANG_FK from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK \n"+
					"inner join ERP_PARK c on a.park_fk = c.pk_seq where c.trangthai <> 4)";
			ResultSet rs = db.get(query);
			int kt = 0;
			if(rs != null)
			{
				if(rs.next())
				{
					kt = rs.getInt("num");
				}
			}
			
			db.getConnection().setAutoCommit(false);

			query = "update erp_phanbomuahang set ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', " 
					+ "diengiai = '" + this.diengiai + "' ";
			if(kt <= 0)
				query += ", MUAHANG_FK = '" + this.dmhId + "' ";
			query += "where pk_seq = '" + this.id + "' ";

			System.out.println("2.Update erp_phanbomuahang: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat erp_phanbomuahang: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_MUAHANG_SP where MUAHANG_FK in (select PODUOCPB from ERP_PHANBOMUAHANG_PO where PHANBO_FK = '" + this.id + "' "+
					"and PODUOCPB not in (select b.MUAHANG_FK from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK \n"+
					"inner join ERP_PARK c on a.park_fk = c.pk_seq where c.trangthai <> 4))";
			System.out.println("2.delete ERP_MUAHANG_SP: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat erp_phanbomuahang: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete ERP_DUYETMUAHANG where muahang_fk in (select PODUOCPB from ERP_PHANBOMUAHANG_PO where PHANBO_FK = '" + this.id + "' "+
					"and PODUOCPB not in (select b.MUAHANG_FK from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK \n"+
					"inner join ERP_PARK c on a.park_fk = c.pk_seq where c.trangthai <> 4))";
			System.out.println("2.delete ERP_MUAHANG: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat erp_phanbomuahang: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete ERP_MUAHANG where PK_SEQ in (select PODUOCPB from ERP_PHANBOMUAHANG_PO where PHANBO_FK = '" + this.id + "' "+
					"and PODUOCPB not in (select b.MUAHANG_FK from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK \n"+
					"inner join ERP_PARK c on a.park_fk = c.pk_seq where c.trangthai <> 4))";
			System.out.println("2.delete ERP_MUAHANG: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat erp_phanbomuahang: PO duoc phan bo da xuat hoa don" + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete erp_phanbomuahang_sp_chitiet where phanbo_fk = '" + this.id + "' and muahang_fk "+
					"not in (select b.MUAHANG_FK from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK \n"+
					"inner join ERP_PARK c on a.park_fk = c.pk_seq where c.trangthai <> 4)";

			System.out.println("2.delete erp_phanbomuahang_sp_chitiet: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat erp_phanbomuahang: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete erp_phanbomuahang_po where phanbo_fk = '" + this.id + "' and poduocpb "+
					"not in (select b.MUAHANG_FK from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK \n"+
					"inner join ERP_PARK c on a.park_fk = c.pk_seq where c.trangthai <> 4)";

			System.out.println("2.delete erp_phanbomuahang_po: " + query);
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat erp_phanbomuahang: " + query;
				db.getConnection().rollback();
				return false;
			}
			NumberFormat formatter = new DecimalFormat("#,###,###");
			List<String> idmuahangs = new ArrayList<String>();
			query = "select PK_SEQ, MA, TEN from ERP_CONGTY a ";
			// query += " where isTongCongTy = 0";
			System.out.println("danh sach congty " + query);
			ResultSet rsct = db.get(query);
			String idCt = "";
			if (rsct != null) {
				while (rsct.next()) {
					idCt = rsct.getString("pk_seq");
					double tongtien = 0;
					String querysp = "";
					if (this.lstSp != null) {
						for (int i = 0; i < this.lstSp.size(); i++) {
							ISanpham sp = this.lstSp.get(i);

							double soluongpb = 0, soluongxhd = 0;
							double dongia = Double.parseDouble(sp.getDongia());
							
							List<ICongty> lstct = sp.getCongty();
							for (int j = 0; j < lstct.size(); j++) {
								ICongty ct = lstct.get(j);
								if (ct.getId().trim().equals(idCt.trim())) {
									soluongpb = Double.parseDouble(ct.getSoluongpb());
									query = "select isnull(SUM(soluong), 0) as soluongxhd from ERP_HOADONNCC a inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK \n"+ 
											"inner join ERP_PARK c on a.park_fk = c.pk_seq where c.trangthai <> 4 and b.MUAHANG_FK in (select poduocpb from ERP_PHANBOMUAHANG_PO \n"+
											"where PHANBO_FK = "+this.id+") and b.SANPHAM_FK = "+sp.getId()+" and b.SOLO = '"+sp.getSolo()+"' and a.congty_fk = "+idCt;
									
									ResultSet rssoluong = db.get(query);
									if(rssoluong != null && rssoluong.next())
									{
										soluongxhd = rssoluong.getDouble("soluongxhd");
									}
									System.out.println("[soluongxhd] "+query+", [soluong] "+soluongxhd);
									double a = soluongpb - soluongxhd;
									if (a > 0) {
										double thanhtien = dongia * soluongpb;
										tongtien += thanhtien;
										querysp += "select '" + formatter.format(dongia).replace(",", "") + "' as dongia, "
												+ formatter.format(a).replace(",", "") + " as soluong, " 
												+ formatter.format(thanhtien).replace(",", "") + " as thanhtien, N'"
												+ sp.getDonvi() + "' as donvi, '" + sp.getId().trim() + "' as sp_fk, '"
												+ sp.getTensanpham() + "' as diengiai, '" + sp.getNhomkenh() + "' as nhomkenh, '"
												+ sp.getThuexuat() + "' as thuexuat, '" + sp.getSolo() + "' as solo union ";

										query = "insert into erp_phanbomuahang_sp_chitiet(phanbo_fk, congty_fk, sanpham_fk, solo, soluong, donvi, dongia) "
												+ "select " + this.id + ", " + idCt + ", " + sp.getId().trim() + ", '" + sp.getSolo() + "', "
												+ formatter.format(a).replace(",", "") + ", N'" + sp.getDonvi() + "', '"
												+ formatter.format(dongia).replace(",", "") + "'";
										System.out.println("2.Insert: " + query);
										if (!db.update(query)) {
											this.msg = "Khong the tao moi erp_phanbomuahang: " + query;
											db.getConnection().rollback();
											return false;
										}
									}
									else if (a < 0) 
									{
										this.msg = "Khong the phan bo: So luong phan bo khong duoc nho hon so luong da xuat hoa don.";
										db.getConnection().rollback();
										return false;
									}
								}
							}
						}
						System.out.println("tong tien " + tongtien);
						if (tongtien > 0) {
							double vat = Double.parseDouble(this.vat);
							double tongtienbvat = tongtien;
							tongtien = tongtien * (1 + vat / 100);

							/*
							 * String nam = this.ngaymua.substring(0, 4); String
							 * thang = this.ngaymua.substring(5, 7);
							 * 
							 * query=
							 * " SELECT ISNULL( MAX(SOTUTANG_THEONAM), 0) AS MAXSTT, (SELECT PREFIX FROM ERP_DONVITHUCHIEN  "
							 * + " WHERE PK_SEQ ="+this.dvth+" ) AS PREFIX   "+
							 * " FROM ERP_MUAHANG  DMH WHERE SUBSTRING(NGAYMUA, 0, 5) = "
							 * +nam+ " and     DMH.DONVITHUCHIEN_FK="+this.dvth;
							 * System.out.println("Du lieu po sai  :"+query);
							 * String soPO = ""; int sotutang_theonam = 0;
							 * ResultSet rsPO = db.get(query); if(rsPO.next()) {
							 * sotutang_theonam = (rsPO.getInt("maxSTT") + 1 );
							 * String prefix = rsPO.getString("PREFIX"); String
							 * namPO = this.ngaymua.substring(2, 4); String
							 * chuoiso= ("0000"+
							 * Integer.toString(sotutang_theonam
							 * )).substring(("0000"+
							 * Integer.toString(sotutang_theonam
							 * )).length()-4,("0000"+
							 * Integer.toString(sotutang_theonam)).length());
							 * 
							 * soPO = prefix + "-" + chuoiso+ "/" + thang + "/"
							 * + namPO;
							 * 
							 * } rsPO.close();
							 */

							query = "insert into erp_muahang(ngaymua, donvithuchien_fk, nhacungcap_fk, loaihanghoa_fk,ngaytao, nguoitao, ngaysua, nguoisua, tiente_fk, "
									+ "congty_fk, dachot, isduocphanbo, loai, tongtienbvat, vat, tongtienavat, trangthai, dungsai )"
									+ " values ('" + this.ngaymua + "', '" + this.dvth + "', '" + this.ncc + "', '0', '" + this.getDateTime() + "', '"
									+ this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', " + "'100000', '" + idCt + "', '1', '1', '1', "
									+ "'" + formatter.format(tongtienbvat).replace(",", "") + "', '" + this.vat + "', '" 
									+ formatter.format(tongtien).replace(",", "") + "', '1', '" + this.dungsai + "')";
							System.out.println("1.Insert erp_muahang: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: " + query;
								db.getConnection().rollback();
								return false;
							}

							query = "select IDENT_CURRENT('erp_muahang') as Id";
							rs = db.get(query);
							rs.next();
							String idmuahang = rs.getString("Id");
							rs.close();

							query = "update erp_phanbomuahang_sp_chitiet set muahang_fk = '" + idmuahang + "' where phanbo_fk = '" + this.id + "' "+
									"and congty_fk = "+idCt+" and muahang_fk is null";
							System.out.println("1.Insert erp_muahang: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: "
										+ query;
								db.getConnection().rollback();
								return false;
							}
							
							query = "update erp_muahang set sopo = '" + idmuahang + "' where pk_seq = '" + idmuahang + "'";
							System.out.println("1.Insert erp_muahang: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: "
										+ query;
								db.getConnection().rollback();
								return false;
							}

							System.out.println("chi tiet don hang " + querysp);
							query = "insert into erp_muahang_sp(diengiai, MUAHang_FK, sanpham_fk, soluong, dongia, soluong_new, dongia_new, thanhtien, dongiaviet, "
									+ "thanhtienviet, donvi, nhomkenh_fk, thuexuat) "
									+ "select diengiai, '" + idmuahang + "', sp_fk, sum(soluong), dongia, sum(soluong), dongia, sum(thanhtien), dongia, sum(thanhtien), "
									+ "donvi, nhomkenh, thuexuat from (" + querysp.substring(0, querysp.length() - 6) + ") as sp "
									+ "group by diengiai, sp_fk, dongia, donvi, nhomkenh, thuexuat";

							System.out.println("1.Insert erp_muahang_sp: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_phanbomuahang: " + query;
								db.getConnection().rollback();
								return false;
							}

							query = "\n	SELECT	NCC.CHUCDANH_FK, NCC.QUYETDINH "
									+ "\n	FROM ERP_MUAHANG MUAHANG  "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n	WHERE MUAHANG.PK_SEQ = '" + idmuahang + "' "
									+ "\n	UNION ALL "
									// NẾU KO CÓ CHÍNH SÁCH DUYỆT DÀNH CHO SẢN
									// PHẨM CỦA ĐƠN MUA HÀNG VÀ KO CÓ CHÍNH SÁCH
									// DUYỆT CHO NCC CỦA ĐƠN MUA HÀNG
									+ "\n	SELECT	SP.CHUCDANH_FK, SP.QUYETDINH "
									+ "\n	FROM ERP_MUAHANG MUAHANG  "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
									+ "\n	AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + idmuahang + "') "
									+ "\n	LEFT JOIN "
									+ "\n 	(  "
									+ "\n		SELECT	COUNT(*) AS NUM FROM ERP_MUAHANG MUAHANG   "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n		AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + idmuahang + "')  "
									+ "\n		WHERE MUAHANG.PK_SEQ = '" + idmuahang + "'   "
									+ "\n	)DUYET_NCC ON 1 = 1 "
									+ "\n	LEFT JOIN "
									+ "\n	( "
									+ "\n		SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM "
									+ "\n		FROM ERP_MUAHANG_SP MH_SP  "
									+ "\n		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
									+ "\n		WHERE MH.PK_SEQ = '" + idmuahang + "'  AND MH_SP.SANPHAM_FK NOT IN  "
									+ "\n		(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
									+ "\n 	)KTRA_SP ON 1 = 1 "
									+ "\n	WHERE MUAHANG.PK_SEQ = '" + idmuahang + "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 "
									+ "\n	UNION ALL "
									+ "\n	SELECT	CP.CHUCDANH_FK, CP.QUYETDINH FROM ERP_MUAHANG MUAHANG   "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n	INNER JOIN ERP_CHINHSACHDUYET_CHIPHI CP ON CP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
									+ "\n	INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CP.KHOANGCHIPHI_FK  "
									+ "\n	LEFT JOIN( "
									+ "\n		SELECT	COUNT(SP.CHUCDANH_FK) AS NUM "
									+ "\n		FROM ERP_MUAHANG MUAHANG   "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
									+ "\n		AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + idmuahang + "') "
									+ "\n		LEFT JOIN "
									+ "\n		( "
									+ "\n			SELECT	COUNT(*) AS NUM "
									+ "\n			FROM ERP_MUAHANG MUAHANG   "
									+ "\n			INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n			INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n			AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + idmuahang + "')  "
									+ "\n			WHERE MUAHANG.PK_SEQ = '" + idmuahang + "'   "
									+ "\n		)DUYET_NCC ON 1 = 1 "
									+ "\n		LEFT JOIN "
									+ "\n		( "
									+ "\n			SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM "
									+ "\n			FROM ERP_MUAHANG_SP MH_SP  "
									+ "\n			INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
									+ "\n			INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
									+ "\n			WHERE MH.PK_SEQ = '" + idmuahang + "'  AND MH_SP.SANPHAM_FK NOT IN  "
									+ "\n			(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ)"
									+ "\n		)KTRA_SP ON 1 = 1 "
									+ "\n		WHERE MUAHANG.PK_SEQ = '" + idmuahang + "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 "
									+ "\n	)DUYET_SP ON 1 = 1 "
									+ "\n	LEFT JOIN( "
									+ "\n		SELECT	COUNT(NCC.CHUCDANH_FK) AS NUM "
									+ "\n		FROM ERP_MUAHANG MUAHANG   "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
									+ "\n		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
									+ "\n		WHERE MUAHANG.PK_SEQ = '" + idmuahang + "'"
									+ "\n	)DUYET_NCC ON 1 = 1 "
									+ "\n	WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT*MUAHANG.TYGIAQUYDOI "
									+ "\n	AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT*MUAHANG.TYGIAQUYDOI OR KHOANGCHIPHI.SOTIENDEN IS NULL) "
									+ "\n	AND MUAHANG.PK_SEQ = '" + idmuahang + "' AND DUYET_SP.NUM = 0 AND DUYET_NCC.NUM = 0 ";

							System.out.println("3.Duyet PO....:" + query);

							rs = db.get(query);

							if (rs.next()) {

								query = "if not exists (select * from ERP_DUYETMUAHANG where CHUCDANH_FK = " + rs.getString("CHUCDANH_FK") + " AND MUAHANG_FK = " + idmuahang + ")"
										+ "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH) "
										+ "VALUES('" + idmuahang + "', '" + rs.getString("CHUCDANH_FK") + "', '0','" + rs.getString("QUYETDINH") + "') ";

								System.out.println("4. Insert Duyet PO:" + query);
								if (!db.update(query)) {
									this.msg = "Khong the them nguoi duyet cho PO: " + query;
									db.getConnection().rollback();
									return false;
								}
							}

							query = "insert into erp_phanbomuahang_po(PHANBO_FK, PODUOCPB) "
									+ "values('" + this.id + "', '" + idmuahang + "' )";

							System.out.println("1.Insert: " + query);
							if (!db.update(query)) {
								this.msg = "Khong the tao moi erp_timncc_ncc: " + query;
								db.getConnection().rollback();
								return false;
							}
							
							//CẬP NHẬT TOOLTIP
							db.execProceduce2("CapNhatTooltip_DMH", new String[] { idmuahang } );
							
							idmuahangs.add(idmuahang);
						}
					}
				}
			}

			query = "select b.pk_seq, a.solo, a.SOLUONG - ISNULL((select SUM(SOLUONG) from ERP_PHANBOMUAHANG_SP_CHITIET ct \n"+ 
					"inner join ERP_PHANBOMUAHANG t on ct.PHANBO_FK = t.PK_SEQ where t.muahang_fk = c.MUAHANG_FK and ct.SANPHAM_FK = a.SANPHAM_FK \n"+ 
					"and ct.SOLO = a.SOLO and phanbo_fk < "+this.id+"), 0) as soluongdat,  \n"+
					"ISNULL((select SUM(SOLUONG) from ERP_PHANBOMUAHANG_SP_CHITIET ct  \n"+
					"inner join ERP_PHANBOMUAHANG t on ct.PHANBO_FK = t.PK_SEQ where t.muahang_fk = c.MUAHANG_FK and ct.SANPHAM_FK = a.SANPHAM_FK \n"+ 
					"and ct.SOLO = a.SOLO and phanbo_fk = "+this.id+"), 0) as soluongpb \n"+
					"from erp_nhapkhonhamay_sp_chitiet a inner join SANPHAM b on a.SANPHAM_FK = b.PK_SEQ INNER JOIN erp_nhapkhonhamay c on a.nhamay_fk = c.pk_seq \n"+ 
					"where c.MUAHANG_FK = "+this.dmhId+" and c.trangthai = 1";
			ResultSet rskt = db.get(query);
			double soluongdat = 0, soluongpb = 0;
			if(rskt != null)
			{
				while(rskt.next())
				{
					soluongdat = rskt.getDouble("soluongdat");
					soluongpb = rskt.getDouble("soluongpb");
					if(soluongdat < soluongpb)
					{
						this.msg = "Khong the phan bo: So luong phan bo lon hon so luong nhap kho.";
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			try {
				db.getConnection().rollback();
				System.out.println("__EXCEPTION UPDATE: " + e.getMessage());
				e.printStackTrace();

				this.msg = "Lỗi khi cập nhật phân bổ đơn hàng MTV: "
						+ e.getMessage();
			} catch (SQLException e1) {
			}

			return false;
		}
		return true;
	}

	@Override
	public void close() {

		try {

			if (dmhRs != null) {
				dmhRs.close();
			}
			db.shutDown();

		} catch (Exception er) {

		}
	}

	@Override
	public String getDnmhId() {

		return this.dmhId;
	}

	@Override
	public void setDnmhId(String dmhId) {

		this.dmhId = dmhId;
	}

	@Override
	public ResultSet getDnmhRs() {

		return this.dmhRs;
	}

	@Override
	public void setDnmhRs(ResultSet dmhRs) {

		this.dmhRs = dmhRs;
	}

	@Override
	public String getNgaytao() {

		return this.ngaytao;
	}

	@Override
	public void setNgaytao(String ngaytao) {

		this.ngaytao = ngaytao;
	}

	@Override
	public String getNgaysua() {

		return this.ngaysua;
	}

	@Override
	public void setNgaysua(String ngaysua) {

		this.ngaysua = ngaysua;
	}

	@Override
	public String getNguoitao() {

		return this.nguoitao;
	}

	@Override
	public void setNguoitao(String nguoitao) {

		this.nguoitao = nguoitao;
	}

	@Override
	public String getNguoisua() {

		return this.nguoisua;
	}

	@Override
	public void setNguoisua(String nguoisua) {

		this.nguoisua = nguoisua;
	}

	@Override
	public String getMessage() {

		return this.msg;
	}

	@Override
	public void setMessage(String msg) {

		this.msg = msg;
	}

	public void setActive(String active) {
		this.active = active;
	}

	public String getActive() {
		return this.active;
	}

	@Override
	public void setVat(String vat) {

		this.vat = vat;
	}

	@Override
	public String getVat() {

		return this.vat;
	}

	@Override
	public void setDungsai(String dungsai) {

		this.dungsai = dungsai;
	}

	@Override
	public String getDungsai() {

		return this.dungsai;
	}

	@Override
	public void setNgaymua(String ngaymua) {

		this.ngaymua = ngaymua;
	}

	@Override
	public String getNgaymua() {

		return this.ngaymua;
	}

	@Override
	public void setDvth(String dvth) {

		this.dvth = dvth;
	}

	@Override
	public String getDvth() {

		return this.dvth;
	}

	@Override
	public void setNcc(String ncc) {

		this.ncc = ncc;
	}

	@Override
	public String getNcc() {

		return this.ncc;
	}

	@Override
	public List<ISanpham> getSpList() {

		return this.lstSp;
	}

	@Override
	public void setSpList(List<ISanpham> spList) {

		this.lstSp = spList;
	}

	@Override
	public ResultSet getCtRs() {

		return this.ctRs;
	}

	@Override
	public void setCtRs(ResultSet ctRs) {

		this.ctRs = ctRs;
	}
}
