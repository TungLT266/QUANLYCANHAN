package geso.traphaco.erp.beans.chiphinhapkhau.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Erp_Item;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.chiphinhapkhau.IErpChiphinhapkhau;
import geso.traphaco.erp.beans.chiphinhapkhau.ISanPhamPhanBo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpChiphinhapkhau implements IErpChiphinhapkhau {
	String userId;
	String congtyId;
	String id;
	String ncc;
	String nccId;

	String ncc_cn;
	String nccId_cn;

	String ngaynhap;
	String ghichu;

	String pnkId;
	ResultSet pnkRs;

	ResultSet nccRs;

	ResultSet tienteRs;
	String tienteId;

	String tigia;
	String trangthai;

	String sotokhaiId;
	ResultSet RsSoTokhai;
	String pnkIds;

	String[] diengiai;
	String[] maHD;
	String[] mausoHD;
	String[] kyhieu;
	String[] sochungtu;
	String[] ngaychungtu;
	String[] nhacungcap;
	String[] diaChiNCC;
	String[] mst;
	String[] tienhang;
	String[] thuesuat;
	String[] tienthue;
	String[] tongtien;

	private String soChungTu_Chu;
	private String soChungTu_So;

	String msg;

	dbutils db;
	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter1 = new DecimalFormat("#,###,###,###.###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
	String Tongtienhang, Tongtienthue, Tongtien_AVAT;
	List<ISanPhamPhanBo> spList;
	private List<Erp_Item> sanPhamKhoList;

	public List<ISanPhamPhanBo> getSpList() {
		return spList;
	}

	public void setSpList(List<ISanPhamPhanBo> spList) {
		this.spList = spList;
	}

	public ErpChiphinhapkhau() {
		this.userId = "";
		this.id = "";
		this.ghichu = "";
		this.ngaynhap = "";
		this.ncc = "";
		this.nccId = "";
		this.ncc_cn = "";
		this.nccId_cn = "";
		this.sotokhaiId = "";
		this.pnkId = "";
		this.tienteId = "100000";
		this.tigia = "1";

		this.Tongtienhang = "0";
		this.Tongtienthue = "0";
		this.Tongtien_AVAT = "0";

		this.trangthai = "";
		this.pnkIds = "";
		this.msg = "";
		this.spList = new ArrayList<ISanPhamPhanBo>();
		this.sanPhamKhoList = new ArrayList<Erp_Item>();
		this.db = new dbutils();

		this.soChungTu_Chu = "CPNK";
		this.soChungTu_So = geso.traphaco.center.util.Utility.getSoChungTuMax(
				db, "erp_chiphinhapkhau", null);
	}

	public ErpChiphinhapkhau(String id) {
		this.userId = "";
		this.id = id;
		this.ghichu = "";
		this.ngaynhap = "";
		this.ncc = "";
		this.nccId = "";

		this.pnkId = "";
		this.ncc_cn = "";
		this.sotokhaiId = "";
		this.nccId_cn = "";
		this.tienteId = "100000";
		this.tigia = "1";
		this.spList = new ArrayList<ISanPhamPhanBo>();
		this.Tongtienhang = "0";
		this.Tongtienthue = "0";
		this.Tongtien_AVAT = "0";

		this.trangthai = "";
		this.pnkIds = "";
		this.msg = "";
		this.db = new dbutils();

		this.sanPhamKhoList = new ArrayList<Erp_Item>();

		this.soChungTu_Chu = "CPNK";
		this.soChungTu_So = geso.traphaco.center.util.Utility.getSoChungTuMax(
				db, "erp_chiphinhapkhau", null);
	}

	public void init() {
		String query = "select isnull(nhacungcapnhan, '') as nhacungcapnhan, ISNULL(nccId_cn, 0) AS nccId_cn, ISNULL(ncc_cn, '') AS ncc_cn,\n"
				+ "       isnull(tongtienbvat,'0') as tongtienbvat, isnull(tongtienvat,'0') as tongtienvat,\n"
				+ "       isnull(tongtienavat,'0') as tongtienavat, trangthai,  \n"
				+ "       isnull(ghichu, '') as ghichu, ngay ,isnull(tiente_fk,'100000') as tienteId, isnull(tigia,'1') as tigia \n"
				+ "       , isNull(soChungTu_Chu, '') as soChungTu_Chu, isNull(soChungTu_So, '') as soChungTu_So\n"
				+ "from erp_chiphinhapkhau where PK_SEQ = '" + this.id + "'\n";
		System.out.println("Câu init" + query);
		ResultSet rs = db.get(query);
		NumberFormat formatterVND = new DecimalFormat("#,###,###");
		if (rs != null) {
			try {
				while (rs.next()) {
					this.soChungTu_Chu = rs.getString("soChungTu_Chu");
					this.soChungTu_So = rs.getString("soChungTu_So");
					this.nccId = rs.getString("nhacungcapnhan");
					this.nccId_cn = rs.getString("nccId_cn");

					this.ghichu = rs.getString("ghichu");
					this.ngaynhap = rs.getString("ngay");
					this.tienteId = rs.getString("tienteId");
					this.tigia = rs.getString("tigia");
					this.trangthai = rs.getString("trangthai");

					this.Tongtienhang = formatterVND.format(rs
							.getDouble("tongtienbvat"));
					this.Tongtienthue = formatterVND.format(rs
							.getDouble("tongtienvat"));
					this.Tongtien_AVAT = formatterVND.format(rs
							.getDouble("tongtienavat"));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		this.initDetail();
		this.createRs();
	}

	private void initDetail() {
		try {
			String query = "select ISNULL(NHANHANG_FK, 0) AS NHID  from ERP_CHIPHINHAPKHAU_NHANHANG where CHIPHINHAPKHAU_FK="
					+ this.id;

			ResultSet rs = db.get(query);

			String pnkIdstr = "";
			while (rs.next()) {
				if (pnkIdstr.length() > 0) {
					pnkIdstr = pnkIdstr
							+ ","
							+ (rs.getString("NHID").equals("0") ? "" : rs
									.getString("NHID"));
				} else {
					pnkIdstr = ""
							+ (rs.getString("NHID").equals("0") ? "" : rs
									.getString("NHID"));
				}
			}
			rs.close();
			if (this.pnkIds.length() == 0)
				this.pnkIds = pnkIdstr;

			query = " select isnull(diengiai, ' ') as diengiai, isnull(MAUHOADON, '') as MAUHOADON, \n "
					+ "        isnull(MAUSOHOADON, '') as MAUSOHOADON, isnull(kyhieuchungtu, ' ') as kyhieuchungtu,  \n "
					+ "        isnull(sochungtu, ' ') as sochungtu, ngaychungtu, isnull(nhacungcap, ' ') as nhacungcap,isnull(diachincc,'') as diachincc , \n "
					+ "        isnull(masothue, ' ') as masothue, tienhang, isnull(tienthue,0) as tienthue,  \n "
					+ "		isnull(thuesuat,0) as  thuesuat,  (tienhang + isnull(tienthue,0)) as tongtien    \n "
					+ " from erp_chiphinhapkhau_chitiet "
					+ " where chiphinhapkhau_fk = '" + this.id + "'";
			System.out.println("\n \n  cau  query \n \n  " + query);
			// NumberFormat formatterVND = new DecimalFormat("#,###,###");
			rs = db.get(query);

			String diengiai = "";
			String mauhoadon = "";
			String mausohoadon = "";
			String kyhieu = "";
			String soCT = "";
			String ngaychungtu = "";
			String nhacungcap = "";
			String diaChiNCC = "";
			String masothue = "";
			String tienhang = "";
			String thuesuat = "";
			String tienthue = "";
			String tongtien = "";

			while (rs.next()) {
				String ts = "" + rs.getDouble("thuesuat");
				String tt = "" + rs.getDouble("tienthue");

				if (Double.parseDouble(ts) == 0 && Double.parseDouble(tt) > 0) {
					ts = "";
				}

				diengiai += rs.getString("diengiai") + " ----";
				mauhoadon += rs.getString("MAUHOADON") + " ----";
				mausohoadon += rs.getString("MAUSOHOADON") + " ----";
				kyhieu += rs.getString("kyhieuchungtu") + " ----";
				soCT += rs.getString("sochungtu") + " ----";
				ngaychungtu += rs.getString("ngaychungtu") + " ----";
				nhacungcap += rs.getString("nhacungcap") + " ----";
				diaChiNCC += rs.getString("diachincc") + " ----";
				masothue += rs.getString("masothue") + " ----";
				tienhang += "" + rs.getDouble("tienhang") + "----";
				thuesuat += ts + "----";
				tienthue += "" + rs.getDouble("tienthue") + "----";
				tongtien += "" + rs.getDouble("tongtien") + "----";
			}
			rs.close();

			if (diengiai.trim().length() > 0) {
				System.out.println("Dien giai : " + diengiai);
				diengiai = diengiai.substring(0, diengiai.length() - 4);
				this.diengiai = diengiai.split("----");
				System.out.println(this.diengiai.length);
			}

			if (mauhoadon.trim().length() > 0) {
				mauhoadon = mauhoadon.substring(0, mauhoadon.length() - 4);
				this.maHD = mauhoadon.split("----");
			}

			if (mausohoadon.trim().length() > 0) {
				mausohoadon = mausohoadon
						.substring(0, mausohoadon.length() - 4);
				this.mausoHD = mausohoadon.split("----");
			}

			if (kyhieu.trim().length() > 0) {
				kyhieu = kyhieu.substring(0, kyhieu.length() - 4);
				this.kyhieu = kyhieu.split("----");
			}

			if (soCT.trim().length() > 0) {
				soCT = soCT.substring(0, soCT.length() - 4);
				this.sochungtu = soCT.split("----");
			}

			if (ngaychungtu.trim().length() > 0) {
				ngaychungtu = ngaychungtu
						.substring(0, ngaychungtu.length() - 4);
				this.ngaychungtu = ngaychungtu.split("----");
			}

			if (nhacungcap.trim().length() > 0) {
				nhacungcap = nhacungcap.substring(0, nhacungcap.length() - 4);
				this.nhacungcap = nhacungcap.split("----");
			}
			if (diaChiNCC.trim().length() > 0) {
				diaChiNCC = diaChiNCC.substring(0, diaChiNCC.length() - 4);
				this.diaChiNCC = diaChiNCC.split("----");
			}

			if (masothue.trim().length() > 0) {
				masothue = masothue.substring(0, masothue.length() - 4);
				this.mst = masothue.split("----");
			}

			if (tienhang.trim().length() > 0) {
				tienhang = tienhang.substring(0, tienhang.length() - 4);
				this.tienhang = tienhang.split("----");
			}

			if (thuesuat.trim().length() > 0) {
				thuesuat = thuesuat.substring(0, thuesuat.length() - 4);
				this.thuesuat = thuesuat.split("----");
			}

			if (tienthue.trim().length() > 0) {
				tienthue = tienthue.substring(0, tienthue.length() - 4);
				this.tienthue = tienthue.split("----");
			}

			if (tongtien.trim().length() > 0) {
				tongtien = tongtien.substring(0, tongtien.length() - 4);
				this.tongtien = tongtien.split("----");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void createRs() {
		// khi khóa sổ tất cả các tờ khai trong tháng sẽ chuyển sang trạng thái
		// hoàn tất

		/*
		 * String query =
		 * "  select a.pk_seq, TNK.SOCHUNGTU as sotokhai , TNK.NGAYCHUNGTU  as ngaytokhai "
		 * +
		 * ", b.prefix + a.prefix + cast(a.pk_seq as varchar(10))  as sochungtu ,  a.ngaynhan "
		 * + " from erp_nhanhang a " +
		 * " inner join erp_donvithuchien b on a.donvithuchien_fk = b.pk_seq " +
		 * " inner join erp_muahang c on a.muahang_fk = c.pk_seq  " +
		 * "  INNER JOIN ERP_THUENHAPKHAU TNK ON A.SoToKhai_fk=TNK.PK_SEQ  " +
		 * " where a.trangthai in (1, 2) and ( c.nguongocHH = 'NN' or a.LOAIHANGHOA_FK = 1 ) "
		 * ; " and a.pk_seq not in ( " +
		 * " select cp_nh.NHANHANG_FK from ERP_CHIPHINHAPKHAU_NHANHANG cp_nh " +
		 * " inner join ERP_CHIPHINHAPKHAU cp on cp.pk_seq=cp_nh.CHIPHINHAPKHAU_FK  "
		 * + " where cp.trangthai in (3) and cp.pk_seq != '" + (
		 * this.id.trim().length() <= 0 ? "-1" : this.id.trim() ) + "' ) " ;
		 */
		String query = "";

		// KIỂM TRA NẾU TSCD CÓ KHẤU HAO THÁNG RỒI THÌ K CHO HIỆN PHIẾU NÀY RA
		// NỮA

		if (this.id.length() == 0) {
			// NHẬN HÀNG TSCD
			query = "	SELECT a.pk_seq as pnkId, \n"
					+ "	'[ ' +b.prefix + a.prefix + cast(a.pk_seq as varchar(10)) + ' ][ ' + a.ngaynhan + ' ][ ' + ncc.ten + ' ][ ' + isnull(c.nguongocHH, 'TN') + ' ][ ' + isnull(th.SOCHUNGTU, '') + ']' as pnk  \n"
					+ "   FROM erp_nhanhang a  \n"
					+ "	inner join erp_donvithuchien b on a.donvithuchien_fk = b.pk_seq \n "
					+ "   inner join ERP_MUAHANG c on  a.MUAHANG_FK = c.PK_SEQ  \n "
					+ "   inner join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = c.NHACUNGCAP_FK \n "
					+ "  left join ERP_HOADONNCC hdncc on a.hdNCC_fk = hdncc.pk_seq"
					+ "  left join ERP_THUENHAPKHAU_HOADONNCC thhd on thhd.HOADONNCC_FK = hdncc.pk_seq"
					+ "  left join ERP_THUENHAPKHAU th on th.PK_SEQ = thhd.THUENHAPKHAU_FK  "
					+ "   WHERE a.trangthai in (1, 2) \n"
					+ "   AND a.PK_SEQ NOT IN ( \n"
					+ "   	SELECT CPNH.NHANHANG_FK \n"
					+ "   	FROM ERP_CHIPHINHAPKHAU_NHANHANG CPNH \n"
					+ "   	INNER JOIN ERP_CHIPHINHAPKHAU NH ON CPNH.NHANHANG_FK = NH.PK_SEQ \n"
					+ "   	WHERE NH.TRANGTHAI NOT IN (2)) \n";
			// "   and a.PK_SEQ IN ( SELECT nhanhang_fk \n" +
			// "					  FROM ERP_NHANHANG_SANPHAM A \n"+
			// "					  WHERE TAISAN_FK IS NOT NULL AND A.TAISAN_FK NOT IN ( SELECT TAISAN_FK FROM ERP_KHAUHAOTAISAN WHERE TRANGTHAI = 1 ) )  \n";
			// // LẤY NHẬN HÀNG TSCĐ

			if (this.nccId.length() > 0) {
				query = query + " AND c.NHACUNGCAP_FK = " + this.nccId + " \n";
			}

			System.out.println("Lay nhan hang : \n" + query
					+ "\n------------------------------------");
			this.pnkRs = db.get(query);
		} else {
			query = "SELECT * FROM(	\n"
					+ "	SELECT 1 AS CHON, a.pk_seq as pnkId, a.NGAYNHAN, \n"
					+ "	'[ ' +b.prefix + a.prefix + cast(a.pk_seq as varchar(10)) + ' ][ ' + a.ngaynhan + ' ][ ' + ncc.ten + ' ][ ' + isnull(c.nguongocHH, 'TN') + ' ][ ' + isnull(th.SOCHUNGTU, '') + ']' as pnk \n "
					+ "   FROM erp_nhanhang a  "
					+ "	inner join erp_donvithuchien b on a.donvithuchien_fk = b.pk_seq \n "
					+ "   inner join ERP_MUAHANG c on  a.MUAHANG_FK = c.PK_SEQ  \n "
					+ "   inner join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = c.NHACUNGCAP_FK \n "
					+ "  left join ERP_HOADONNCC hdncc on a.hdNCC_fk = hdncc.pk_seq"
					+ "  left join ERP_THUENHAPKHAU_HOADONNCC thhd on thhd.HOADONNCC_FK = hdncc.pk_seq"
					+ "  left join ERP_THUENHAPKHAU th on th.PK_SEQ = thhd.THUENHAPKHAU_FK  "
					+ "   WHERE a.trangthai in (1, 2) and a.NGAYNHAN > '" + UtilityKeToan.GetNgayKhoaSoMax(this.db) + "' \n";

			if (this.nccId.length() > 0) {
				query = query + " AND c.NHACUNGCAP_FK = " + this.nccId + " \n";
			}
			query = query + ") a";

			System.out.println("Lay nhan hang co id: \n" + query
					+ "\n--------------------------------------------------");
			this.pnkRs = db.get(query);
		}

		if (this.tienteId.length() > 0) {
			query = "select TIGIAQUYDOI from ERP_TIGIA where TIENTE_FK= "
					+ this.tienteId + " and '" + this.ngaynhap
					+ "' >= TUNGAY and '" + this.ngaynhap
					+ "' <= DENNGAY and TRANGTHAI='1' ";
			System.out.println("Lấy tỉ giá :" + query);
			ResultSet rs = db.get(query);

			if (rs != null) {
				try {
					while (rs.next()) {
						this.tigia = rs.getString("TIGIAQUYDOI");
					}
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		query = "select pk_seq, ma + ', ' + ten as nccTen from ERP_nhaCungCap where congty_fk = "
				+ this.congtyId + " and trangthai = '1' and duyet = '1'";
		this.nccRs = db.getScrol(query);
		System.out.println("Truoc Load phan bo sp");
		this.loadSanPhamPhanBo();
		System.out.println("Sau Load phan bo sp");
		//initSanPhamKhoList();
	}

	// thể hiện các sản phẩm theo phiếu nhập
	public void loadSanPhamPhanBo() {
		this.spList = new ArrayList<ISanPhamPhanBo>();
		String query;
		if (this.pnkIds.length() > 0) 
		{
			System.out.println("pnkIds" + this.pnkIds);
			
			if (this.id.length() > 0) 
			{
				query = "SELECT COUNT(*) OVER() AS SIZE, SP.PK_SEQ,SP.MA, SP.TEN, ISNULL(CPNK_PB.SOLO, '') AS SOLO, TIENHANG_GOC, \n "
						+ "0 AS PHANTRAM, TIENHANG AS PHANBO, ISNULL(CPNK_PB.NHANHANG_FK, 0) AS NHID, \n "
						+ "(SELECT SUM(TIENHANG) FROM ERP_CHIPHINHAPKHAU_PHANBO WHERE CHIPHINHAPKHAU_FK = CPNK_PB.CHIPHINHAPKHAU_FK ) AS TONGTIENPHANTRAM, \n "
						+ "CPNK_PB.TIENHANG_GOC AS TONGTIEN \n" 
						+ "FROM ERP_CHIPHINHAPKHAU_PHANBO CPNK_PB \n "
						+ "INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = CPNK_PB.SANPHAM_FK \n "
						+ "WHERE CHIPHINHAPKHAU_FK = " + this.id + " and CPNK_PB.NHANHANG_FK in (" + this.pnkIds + ")\n" 
						+ "ORDER BY CPNK_PB.STT \n";
				System.out.println("Load sp phan bo2:\n" + query + "\n-----------------------------------------");
				ResultSet rs = this.db.get(query);
				DecimalFormat df = new DecimalFormat("#.##");    
				if (rs != null) {
					try {
						int i = 0 ;
						double tongPhanTram = 0 ;
						while (rs.next()) {
							ISanPhamPhanBo sp = new SanPhamPhanBo();
							i++;
							sp.setLoai("1");
							sp.setMaLon(rs.getString("MA"));
							sp.setTenSp(rs.getString("TEN"));
							sp.setManhanHang(rs.getString("NHID").equals(
									"0") ? "" : rs.getString("NHID"));
							sp.setIdSP(rs.getString("PK_SEQ"));
							sp.setPhanBo(rs.getDouble("PHANBO"));
							sp.setPhanTram(Double.valueOf(df.format(100 * rs.getDouble("PHANBO")/ rs.getDouble("TONGTIENPHANTRAM"))));
							int size = rs.getInt("SIZE");
							if(size == i) {
								sp.setPhanTram(100 - tongPhanTram);
							}else{
								tongPhanTram += Double.valueOf(df.format(100 * rs.getDouble("PHANBO")/ rs.getDouble("TONGTIENPHANTRAM")));
							}
							
							sp.setSoLo(rs.getString("SOLO"));
							sp.setTien(rs.getDouble("TONGTIEN"));
							this.spList.add(sp);
						}
					} catch (java.sql.SQLException e) {
						e.printStackTrace();
					}
				}
			}else{
				//Không có ID : Load sản phẩm theo phiếu nhận hàng
				boolean isExit = getSanPhamPhieuNhap();
				if (isExit == true)
					tinhPhanTramPhanBo();
			}
			
			
		} else {
			if (this.id.length() > 0) {
				query = "SELECT  SP.MA, SP.TEN, ISNULL(CPNK_PB.SOLO, '') AS SOLO, TIENHANG_GOC, \n "
						+ "0 AS PHANTRAM, TIENHANG AS PHANBO, ISNULL(CPNK_PB.NHANHANG_FK, 0) AS NHID, \n "
						+ "(SELECT SUM(TIENHANG) FROM ERP_CHIPHINHAPKHAU_PHANBO WHERE CHIPHINHAPKHAU_FK = CPNK_PB.CHIPHINHAPKHAU_FK ) AS TONGTIEN \n "
						+ "FROM ERP_CHIPHINHAPKHAU_PHANBO CPNK_PB \n "
						+ "INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = CPNK_PB.SANPHAM_FK \n "
						+ "WHERE CHIPHINHAPKHAU_FK = " + this.id + " and CPNK_PB.NHANHANG_FK is null\n" 
						+ "ORDER BY CPNK_PB.STT \n";
				System.out.println("Load sp phan bo3:\n" + query + "\n--------------------------------------------------");
				ResultSet rs = this.db.get(query);
				if (rs != null) {
					try {
						
						while (rs.next()) {
							ISanPhamPhanBo sp = new SanPhamPhanBo();
							
							
							sp.setLoai("1");
							sp.setMaLon(rs.getString("MA"));
							sp.setTenSp(rs.getString("TEN"));
							sp.setManhanHang(rs.getString("NHID").equals("0") ? ""
									: rs.getString("NHID"));
							sp.setMaSp(rs.getString("MA"));
							sp.setPhanBo(rs.getDouble("PHANBO"));
							sp.setPhanTram(100 * rs.getDouble("PHANBO")/ rs.getDouble("TONGTIEN"));
							
							
							sp.setSoLo(rs.getString("SOLO"));
							sp.setTien(rs.getDouble("TONGTIEN"));
							this.spList.add(sp);
						}
					} catch (java.sql.SQLException e) {
						e.printStackTrace();
					}
				}
			}
		}
	}

	//Lấy sản phẩm từ phiếu nhập kho chưa nằm trong phiếu nhận hàng (ngoại trừ phiếu đã được xóa)
	private boolean getSanPhamPhieuNhap() 
	{
		boolean isExit = false;
		/*String subQuery1 = 
			"(select convert(nvarchar, pb.NHANHANG_FK) \n" +
			"from ERP_CHIPHINHAPKHAU_PHANBO pb\n" +
			"inner join ERP_CHIPHINHAPKHAU cp on cp.pk_seq = pb.CHIPHINHAPKHAU_FK and cp.trangthai != 2\n" +
			"where pb.NHANHANG_FK is not null)\n";*/
		
		String query = 
			"select nhsp.NHANHANG_FK,sp.MA , sp.PK_SEQ, sp.TEN, nhsp.SOLO, isnull(sum (nhsp.GIACHAYKT* nhsp.SOLUONG),0) as tien\n" +
			", 1 as loai, isnull(sum (nhsp.GIATHEOLO * nhsp.SOLUONG),0) as GIATHEOLO\n"
			+ " from  ERP_NHANHANG nh\n"
			+ " left join ERP_NHANHANG_SP_CHITIET nhsp on nhsp.NHANHANG_FK = nh.PK_SEQ\n"
			+ " left join ERP_SANPHAM sp on sp.PK_SEQ = nhsp.SANPHAM_FK\n"
			+ "\n   where nh.PK_SEQ in ( \n"
			+ this.pnkIds 
			+ ") \n" 
			/*+ ") and nh.PK_SEQ not in " + subQuery1 + "\n"*/
			+ " group by sp.PK_SEQ, sp.TEN , nhsp.SOLO, nhsp.NHANHANG_FK,sp.MA\n"
			+ " union all\n"
			+ " select nhsp.NHANHANG_FK, scl.MA, scl.PK_SEQ, scl.TEN,'' as SOLO, isnull(sum (nhsp.DONGIA* nhsp.SOLUONGNHAN),0) as tien\n" +
			", 0 as loai, isnull(sum (nhsp.donGiaViet * nhsp.SOLUONGNhan),0) as GIATHEOLO\n"
			+ " from  \n"
			+ " ERP_NHANHANG_SANPHAM nhsp \n"
			+ " inner join ERP_MASCLON scl on scl.PK_SEQ = nhsp.TAISAN_FK\n"
			+ "\n   where nhsp.NHANHANG_FK in ( \n"
			+ this.pnkIds
			+ ") \n" 
			/*+ ") and nhsp.NHANHANG_FK not in " + subQuery1 + "\n"*/
			+ " group by scl.PK_SEQ, scl.TEN, nhsp.NHANHANG_FK, scl.MA\n"
			+ " order by NHANHANG_FK,TEN asc \n";

		Double tongtien = 0.0;
		System.out.println("Load sp phan bo1:\n" + query + "\n-----------------------------------");
		ResultSet rssp = db.get(query);
		if (rssp != null) 
		{
			try 
			{
				while (rssp.next()) 
				{
					isExit = true;
					String idSP = rssp.getString("PK_SEQ");
					String tenSP = rssp.getString("TEN");
					Double tien = Double.parseDouble(rssp.getString("tien").replaceAll(",", ""));
					tien = Double.parseDouble(rssp.getString("GIATHEOLO").replaceAll(",", ""));
					String solo = rssp.getString("SOLO");
					String loai = rssp.getString("loai");
					String ma = rssp.getString("MA");
					String nhanhang = rssp.getString("NHANHANG_FK");
					ISanPhamPhanBo sp = new SanPhamPhanBo();
					sp.setIdSP(idSP);
					sp.setTenSp(tenSP);
					sp.setTien(tien);
					sp.setSoLo(solo);
					sp.setLoai(loai);
					sp.setManhanHang(nhanhang);
					sp.setMaLon(ma);
					tongtien = tongtien + tien;
		
					spList.add(sp);
				}
				rssp.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		
		return isExit;
	}

	private void tinhPhanTramPhanBo() 
	{
		System.out.println("tinhPhanTramPhanBo");
		try {
			if (this.pnkIds.length() > 0) 
			{
				double tongtruoc = 0;
				double tongTien = SanPhamPhanBo.getTongTien(this.spList);
				for (int i = 0; i < spList.size(); i++) 
				{
					ISanPhamPhanBo sp = spList.get(i);
					double phanTram = sp.getTien() / tongTien * 100;
	
					sp.setPhanTram(phanTram);
					double tienphanbo = 0;
					if (i == spList.size() - 1) {
						tienphanbo = Double.parseDouble(this.Tongtienhang.replaceAll(",", ""))
								- tongtruoc;
					} else {
						tienphanbo = Math.round(Double.parseDouble(this.Tongtienhang.replaceAll(",", ""))
								* phanTram / 100);
						tongtruoc = tongtruoc + tienphanbo;
					}
	
					sp.setPhanBo(tienphanbo);
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private void Tinhphanbo_1(String cpnkId){
		String query =  "SELECT CPNK.PK_SEQ AS CPNKID, NH.SANPHAM_FK,(SELECT TEN FROM ERP_SANPHAM WHERE PK_SEQ =NH.SANPHAM_FK) AS TEN, \n " +
						"ROUND((SELECT SUM(TIENHANG) FROM ERP_CHIPHINHAPKHAU_CHITIET \n " + 
						"WHERE CHIPHINHAPKHAU_FK = CPNK.PK_SEQ)*NH.THANHTIEN/TONGTIEN.THANHTIEN, 0) AS TIENHANG, \n " +
						
						"ROUND((SELECT SUM(TIENTHUE) FROM ERP_CHIPHINHAPKHAU_CHITIET  \n " +
						"WHERE CHIPHINHAPKHAU_FK = CPNK.PK_SEQ)*NH.THANHTIEN/TONGTIEN.THANHTIEN, 0)  AS TIENTHUE, \n " +
						
						//Tiền hàng gốc hóa đơn (dùng để tính tiền hàng của sản phẩm cuối cùng)
						"(SELECT SUM(TIENHANG) FROM ERP_CHIPHINHAPKHAU_CHITIET  \n " +
						"WHERE CHIPHINHAPKHAU_FK = CPNK.PK_SEQ) AS TIENHANG_GOC, \n " +
					
						"(SELECT SUM(TIENTHUE) FROM ERP_CHIPHINHAPKHAU_CHITIET  \n " +
						"WHERE CHIPHINHAPKHAU_FK = CPNK.PK_SEQ) AS TIENTHUE_GOC, \n " +
						
						//Tiền hàng gốc phí nhận hàng , dùng để lưu
						"(NH.THANHTIEN) AS TIENHANG_GOCSAVE, \n" +
						"(NH.THANHTIEN*CPNK.TONGTIENVAT/CPNK.TONGTIENBVAT) AS TIENTHUE_GOCSAVE, \n"+
					
						"NH.NHANHANG_FK, NH.SOLO, COUNT.NUM \n " +
						 
						"FROM  ERP_CHIPHINHAPKHAU CPNK \n " +
						"INNER JOIN  \n " +
						"( \n " +
						"	SELECT CPNK_NH.CHIPHINHAPKHAU_FK, NH_SP.SANPHAM_FK, NH_SP.SOLUONGNHAN, NH_SP.DONGIA, NH_SP.NHANHANG_FK, '' SOLO, \n " +
						"	NH_SP.DONGIA*NH_SP.SOLUONGNHAN as THANHTIEN \n " +
						"	FROM ERP_CHIPHINHAPKHAU_NHANHANG CPNK_NH \n " +
						"	INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = CPNK_NH.NHANHANG_FK \n " +
						"	WHERE NH_SP.SANPHAM_FK IS NOT NULL \n " +
						")NH ON NH.CHIPHINHAPKHAU_FK = CPNK.PK_SEQ \n " +
						"INNER JOIN  \n " +
						"( \n " +
						"	SELECT CPNK_NH.CHIPHINHAPKHAU_FK, \n " + 
						"	SUM(NH_SP.DONGIA*NH_SP.SOLUONGNHAN) as THANHTIEN \n " +
						"	FROM ERP_CHIPHINHAPKHAU_NHANHANG CPNK_NH \n " +
						"	INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = CPNK_NH.NHANHANG_FK \n " +
						"	WHERE NH_SP.SANPHAM_FK IS NOT NULL \n " +
						"	GROUP BY CPNK_NH.CHIPHINHAPKHAU_FK \n " +
						")TONGTIEN ON TONGTIEN.CHIPHINHAPKHAU_FK = CPNK.PK_SEQ  \n " +
						"INNER JOIN  \n " +
						"( \n " +
						"	SELECT CPNK_NH.CHIPHINHAPKHAU_FK, \n " + 
						"	COUNT(NH_SP.SANPHAM_FK) as NUM \n " +
						"	FROM ERP_CHIPHINHAPKHAU_NHANHANG CPNK_NH \n " +
						"	INNER JOIN ERP_NHANHANG_SANPHAM NH_SP ON NH_SP.NHANHANG_FK = CPNK_NH.NHANHANG_FK \n " +
						"	WHERE NH_SP.SANPHAM_FK IS NOT NULL AND NH_SP.DONGIAVIET > 0 \n " +
						"	GROUP BY CPNK_NH.CHIPHINHAPKHAU_FK \n " +
						")COUNT ON COUNT.CHIPHINHAPKHAU_FK = CPNK.PK_SEQ  \n " +
						" AND ROUND((SELECT SUM(TIENHANG) FROM ERP_CHIPHINHAPKHAU_CHITIET \n " + 
						" 			 WHERE CHIPHINHAPKHAU_FK = CPNK.PK_SEQ)*NH.THANHTIEN/TONGTIEN.THANHTIEN, 0) > 0 " +

						" WHERE CPNK.PK_SEQ = " + cpnkId + ""+
						" ORDER BY NHANHANG_FK,TEN ";
		
		System.out.println(query);
		ResultSet rs = db.get(query);
		int num = 0;
		int i = 0;
		double subtotal_th = 0;
		double subtotal_vat = 0;
		try{
			while(rs.next()){
				num = rs.getInt("NUM");
				
				i++;
				System.out.println("Sản phẩm " + i +":" + rs.getString("SANPHAM_FK"));
				if(i == num){
					double tienhang = rs.getDouble("TIENHANG_GOC") - subtotal_th;
					double tienthue = rs.getDouble("TIENTHUE_GOC") - subtotal_vat;
					System.out.println("tính tiền còn lại");

					if(tienhang > 0){
						query = "INSERT INTO ERP_CHIPHINHAPKHAU_PHANBO (CHIPHINHAPKHAU_FK, SANPHAM_FK, TIENHANG, TIENTHUE, TIENHANG_GOC, NHANHANG_FK, SOLO, TIENTHUE_GOC,STT) VALUES( " +
								"" + cpnkId + ", " + rs.getString("SANPHAM_FK") + ", " + tienhang + ", " + tienthue + ", " +
								"" + rs.getString("TIENHANG_GOCSAVE") + ", " + rs.getString("NHANHANG_FK") + ", '"+rs.getString("SOLO")+"', "  + rs.getString("TIENTHUE_GOCSAVE") + ","+i + ")";
					
					}
				}else{
					subtotal_th = subtotal_th + rs.getDouble("TIENHANG") ;
					subtotal_vat = subtotal_vat + rs.getDouble("TIENTHUE") ;
					
					if(rs.getDouble("TIENHANG") > 0){
						query = "INSERT INTO ERP_CHIPHINHAPKHAU_PHANBO (CHIPHINHAPKHAU_FK, SANPHAM_FK, TIENHANG, TIENTHUE, TIENHANG_GOC, NHANHANG_FK, SOLO, TIENTHUE_GOC,STT) VALUES( " +
								"" + cpnkId + ", " + rs.getString("SANPHAM_FK") + ", " + rs.getString("TIENHANG") + ", " + rs.getString("TIENTHUE") + ", " +
								"" + rs.getString("TIENHANG_GOCSAVE") + ", " + rs.getString("NHANHANG_FK") + ", '"+rs.getString("SOLO")+"', "  + rs.getString("TIENTHUE_GOCSAVE") + ","+i + ")";
					}
				}
				System.out.println(query);
				db.update(query);
			}
		}catch(java.sql.SQLException e){}

	}
	
	public boolean Create() {
		try {
			db.getConnection().setAutoCommit(false);

			if (this.ngaynhap.trim().length() <= 0) {
				this.msg = "Vui long chon ngay nhap";
				return false;
			}

			// Check so chung tu
			boolean cthople = false;
			if (this.sochungtu != null) {
				for (int i = 0; i < this.sochungtu.length; i++) {
					if (this.sochungtu[i].trim().length() > 0) {
						cthople = true;
					}
				}
			}

			if (!cthople) {
				this.msg = "C1.1 Vui long kiem tra lai so chung tu trong hoa don chi tiet";
				return false;
			}

			// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa
			// đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
			String query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '"
					+ this.ngaynhap
					+ "')),120 ) as ngaydenhantt "
					+ "FROM ERP_NHACUNGCAP "
					+ "WHERE PK_SEQ = '"
					+ this.nccId_cn + "'";
			System.out.println("Câu lấy ngày đến hạn tt " + query);
			ResultSet rsThoihanno = db.get(query);
			String ngaydenhantt = "";
			if (rsThoihanno != null) {
				while (rsThoihanno.next()) {
					ngaydenhantt = rsThoihanno.getString("ngaydenhantt");
				}
				rsThoihanno.close();
			}

			query = " insert ERP_CHIPHINHAPKHAU(ngay , ghichu, congty_fk, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, nhacungcapnhan, nccId_cn, ncc_cn, tiente_fk, tigia,"
					+ "                           tongtienbvat, tongtienvat, tongtienavat, ngaydenhantt\n"
					+ ", soChungTu_Chu, soChungTu_So)\n" + " values('"
					+ this.ngaynhap
					+ "',   N'"
					+ this.ghichu
					+ "', '"
					+ this.congtyId
					+ "', '0', "
					+ " '"
					+ this.getDateTime()
					+ "', '"
					+ this.userId
					+ "', '"
					+ this.getDateTime()
					+ "', '"
					+ this.userId
					+ "',"
					+ " N'"
					+ this.nccId
					+ "', '"
					+ this.nccId_cn
					+ "', N'"
					+ this.ncc_cn
					+ "', "
					+ this.tienteId
					+ ", "
					+ this.tigia
					+ ", "
					+ " '"
					+ this.Tongtienhang.replaceAll(",", "")
					+ "', '"
					+ this.Tongtienthue.replaceAll(",", "")
					+ "',"
					+ " '"
					+ this.Tongtien_AVAT.replaceAll(",", "")
					+ "' , '"
					+ ngaydenhantt
					+ "' \n"
					+ ", '"
					+ this.soChungTu_Chu
					+ "', '" + this.soChungTu_So + "')\n";

			System.out.println("___1.Insert: " + query);
			if (!db.update(query)) {
				this.msg = "C1.2 Không thể tạo mới ERP_CHIPHINHAPKHAU ";
				db.getConnection().rollback();
				return false;
			}

			String cpnkCurrent = "";
			query = "select SCOPE_IDENTITY() as cpnkId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next()) {
				cpnkCurrent = rsDmh.getString("cpnkId");
				this.id = cpnkCurrent;
				rsDmh.close();
			}

			if (this.getPnkIds().trim().length() > 0) {
				query = " insert into ERP_CHIPHINHAPKHAU_NHANHANG (CHIPHINHAPKHAU_FK, NHANHANG_FK ) "
						+ " select "
						+ this.id
						+ ", pk_seq from erp_nhanhang where pk_Seq in ("
						+ this.getPnkIds() + ")";

				if (db.updateReturnInt(query) < 1) {
					this.msg = "C1.3 Không thể thực hiện lưu chứng từ nhận hàng của chi phí này,vui lòng chọn số chứng từ nhận hàng";
					db.getConnection().rollback();
					return false;
				}

			}

			for (int i = 0; i < this.sochungtu.length; i++) {
				if (this.sochungtu[i].trim().length() > 0) {
					if (this.nhacungcap[i].trim().length() <= 0) {
						this.msg = "C1.4 Vui long kiem tra lai thong tin nha cung cap o chi tiet hoa don";
						db.getConnection().rollback();
						return false;
					}

					String mauhoadon = "";
					if (this.mausoHD[i].trim().length() > 6)
						mauhoadon = this.mausoHD[i].substring(0, 6);
					else
						mauhoadon = this.mausoHD[i];

					if (this.thuesuat[i].equals(""))
						this.thuesuat[i] = "0";

					query = " insert ERP_CHIPHINHAPKHAU_CHITIET\n"
							+ "(chiphinhapkhau_fk, diengiai, MAUHOADON, MAUSOHOADON\n"
							+ ", kyhieuchungtu, sochungtu, ngaychungtu, nhacungcap,diachincc \n"
							+ ", masothue, tienhang, thuesuat, tienthue) \n"
							+ " values('"
							+ this.id
							+ "', N'"
							+ this.diengiai[i]
							+ "', '"
							+ mauhoadon.toUpperCase()
							+ "', '"
							+ this.mausoHD[i].toUpperCase()
							+ "'\n"
							+ ", '"
							+ this.kyhieu[i].toUpperCase()
							+ "', '"
							+ this.sochungtu[i]
							+ "', '"
							+ this.ngaychungtu[i]
							+ "', N'"
							+ this.nhacungcap[i]
							+ "'\n"
							+ ", N'"
							+ this.diaChiNCC[i]
							+ "'\n"
							+ ", '"
							+ this.mst[i]
							+ "', '"
							+ this.tienhang[i].replaceAll(",", "")
							+ "', '"
							+ this.thuesuat[i].replaceAll(",", "")
							+ "' , '"
							+ this.tienthue[i].replaceAll(",", "") + "')";

					System.out
							.println("2.___Lay chi tiet: \n"
									+ query
									+ "\n-------------------------------------------------");
					if (!db.update(query)) {
						this.msg = "C1.5 Không thể tạo mới ERP_CHIPHINHAPKHAU_CHITIET ";
						db.getConnection().rollback();
						return false;
					}
					
					if(this.mst[i].trim().length() > 0){
						query = "select count(*) as sodong from MST_NCC where mst ='"+this.mst[i]+"'";
						
						ResultSet check = db.get(query);
						
						int count = 0;
						if(check!=null){
							if(check.next()) count= check.getInt("sodong"); 
						}
						check.close();
						
						if(count==0){
							query = "INSERT INTO MST_NCC (MST, NCC, DIACHI) VALUES (N'"+this.mst[i].trim()+"', N'"+(this.nhacungcap[i]==null?"":this.nhacungcap[i].replaceAll("'", "''").trim())+"', N'" + (this.diaChiNCC[i]==null?"":this.diaChiNCC[i].replaceAll("'", "''").trim()) + "')";
							
							if (!db.update(query))
							{
								this.msg = "Khong the tao moi MST_NCC: " + query;
								 
								db.getConnection().rollback();
								return false;
							}
						}else{
						query = "DELETE FROM MST_NCC WHERE MST ='" + this.mst[i] + "'";
						if(!db.update(query)){
							this.msg = "Không thể cập nhật MST_NCC" + query;
							db.getConnection().rollback();
							return false;
						}
						query = "INSERT INTO MST_NCC (MST, NCC, DIACHI) VALUES (N'" + this.mst[i].trim()+ "', N'"+ (this.nhacungcap[i] == null ? "" : this.nhacungcap[i].replaceAll("'", "''").trim())+ "', N'" + (this.diaChiNCC[i] == null ? "" : this.diaChiNCC[i].replaceAll("'", "''").trim())+ "')";

						if (!db.update(query)) {
							this.msg = "Khong the tao moi MST_NCC: " + query;

							db.getConnection().rollback();
							return false;
						}
						}
					}
				}
			}

			if(this.pnkIds.trim().length() > 0){
				Tinhphanbo_1(this.id);

			}else{
			// -- phần phân bổ sản phẩm---//
				double tongThue = 0;
				double tongTruocThue = 0;
				double tongSauThue = 0;
				double total = 0;
				for (int i = 0; i < spList.size(); i++) {
	
						ISanPhamPhanBo sp = spList.get(i);
						String masp = "NULL";
						String taisan_fk = "NULL";
	
						if (spList.get(i).getLoai().equals("1")) {
							masp = spList.get(i).getMaSp();
						} else
							taisan_fk = sp.getMaSp();
	
						if (sp.getPhanBo() > 0) {
							// tính phân bổ thuế
							double ptthue = 0;
							double tienthue = 0;
							double tienhang = 0;
							double phanbo = sp.getPhanBo();
	
							if (i == (spList.size() - 1)) {
	
								if (total + sp.getPhanBo() != (Double.parseDouble(this.Tongtienhang))) {
									phanbo = Double.parseDouble(this.Tongtienhang) - total;
								}
	
							} 
	
							total += phanbo;
							tienhang = phanbo;
							String queryInsert = "INSERT INTO ERP_CHIPHINHAPKHAU_PHANBO (CHIPHINHAPKHAU_FK, SANPHAM_FK, TIENHANG, TIENTHUE, TAISAN_FK, TIENHANG_GOC, NHANHANG_FK, SOLO, THUESUAT,STT) "
									+ " VALUES ("
									+ this.id
									+ ", (SELECT PK_SEQ FROM ERP_SANPHAM WHERE MA = '"
									+ masp
									+ "'), "
									+ tienhang
									+ ","
									+ tienthue
									+ ", "
									+ taisan_fk
									+ ", "
									+ (tienhang)
									+ ", "
									+ ((sp.getManhanHang() != null && sp.getManhanHang().trim().length() == 0) ? null : sp.getManhanHang()) + ", N'"+sp.getSoLo()+"', " + ptthue + "," + (i+1) + ")";
	
							System.out.println("2. phan bo san pham: \n"
											+ queryInsert
											+ "\n-------------------------------------------------");
							if (!db.update(queryInsert)) {
								this.msg = "C1.5 KHông thể phân bổ sản phẩm ";
								db.getConnection().rollback();
								return false;
							}
						
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}

		return true;
	}

	public double tinhphanbo(double phantram, double giatritinh,
			int dorongcuamang, int chiso, double tongtruoc) {
		double phanbo = 0;
		double pt = Double.parseDouble(formatter2.format(phantram).replaceAll(
				",", ""));

		if (chiso == dorongcuamang - 1) {
			phanbo = giatritinh - tongtruoc;

		} else {
			phanbo = pt * giatritinh / 100;
		}
		// double tam =
		// Double.parseDouble(formatter.format(phanbo).replaceAll(",", ""));
		return Double.parseDouble(formatter.format(phanbo).replaceAll(",", ""));
	}

	public boolean Update() {
		try {
			db.getConnection().setAutoCommit(false);

			if (this.ngaynhap.trim().length() <= 0) {
				this.msg = "Vui long chon ngay nhap";
				return false;
			}

			// Check so chung tu
			boolean cthople = false;
			if (this.sochungtu != null) {
				for (int i = 0; i < this.sochungtu.length; i++) {
					if (this.sochungtu[i].trim().length() > 0) {
						cthople = true;
					}
				}
			}

			if (!cthople) {
				this.msg = "Vui long kiem tra lai so chung tu trong hoa don chi tiet";
				return false;
			}

			// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa
			// đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
			String query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '"
					+ this.ngaynhap
					+ "')),120 ) as ngaydenhantt "
					+ "FROM ERP_NHACUNGCAP "
					+ "WHERE PK_SEQ = '"
					+ this.nccId_cn + "'";
			System.out.println("Câu lấy ngày đến hạn tt " + query);
			ResultSet rsThoihanno = db.get(query);
			String ngaydenhantt = "";
			if (rsThoihanno != null) {
				while (rsThoihanno.next()) {
					ngaydenhantt = rsThoihanno.getString("ngaydenhantt");
				}
				rsThoihanno.close();
			}

			query = "update ERP_CHIPHINHAPKHAU set ngay = '" + this.ngaynhap
					+ "',   " + " ghichu = N'" + this.ghichu
					+ "', congty_fk = '" + this.congtyId + "', ngaysua = '"
					+ this.getDateTime() + "', " + " nguoisua = '"
					+ this.userId + "', nhacungcapnhan = N'" + this.nccId
					+ "', nccId_cn = '" + this.nccId_cn + "', ncc_cn = N'"
					+ this.ncc_cn + "', tiente_fk= " + this.tienteId
					+ ", tigia= " + this.tigia + " ," + " tongtienbvat = '"
					+ this.Tongtienhang.replaceAll(",", "")
					+ "', tongtienvat = '"
					+ this.Tongtienthue.replaceAll(",", "")
					+ "', tongtienavat =  '"
					+ this.Tongtien_AVAT.replaceAll(",", "")
					+ "', ngaydenhantt = '" + ngaydenhantt + "' \n"
					+ ", soChungTu_Chu = '" + this.soChungTu_Chu
					+ "', soChungTu_So = '" + this.soChungTu_So + "'\n"
					+ " where pk_seq = '" + this.id + "' ";

			System.out.println("___1.Insert: " + query);
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_CHIPHINHAPKHAU " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_CHIPHINHAPKHAU_NHANHANG where chiphinhapkhau_fk = '"
					+ this.id + "'";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_CHIPHINHAPKHAU_NHANHANG "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			if (this.getPnkIds().trim().length() > 0) {
				query = " insert into ERP_CHIPHINHAPKHAU_NHANHANG (CHIPHINHAPKHAU_FK, NHANHANG_FK ) "
						+ " select "
						+ this.id
						+ ", pk_seq from erp_nhanhang where pk_Seq in ("
						+ this.getPnkIds() + ")";

				if (db.updateReturnInt(query) < 1) {
					this.msg = "không thể thực hiện lưu chứng từ nhận hàng của chi phí này,vui lòng chọn số chứng từ nhận hàng";
					db.getConnection().rollback();
					return false;
				}

			}

			query = "delete ERP_CHIPHINHAPKHAU_CHITIET where chiphinhapkhau_fk = '"
					+ this.id + "'";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_CHIPHINHAPKHAU_CHITIET "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			for (int i = 0; i < this.sochungtu.length; i++) {
				if (this.sochungtu[i].trim().length() > 0) {
					if (this.nhacungcap[i].trim().length() <= 0) {
						this.msg = "Vui long kiem tra lai thong tin nha cung cap o chi tiet hoa don";
						db.getConnection().rollback();
						return false;
					}

					String mauhoadon = "";
					if (this.mausoHD[i].trim().length() > 6)
						mauhoadon = this.mausoHD[i].substring(0, 6);
					else
						mauhoadon = this.mausoHD[i];

					if (this.thuesuat[i].equals(""))
						this.thuesuat[i] = "0";

					query = "insert ERP_CHIPHINHAPKHAU_CHITIET(chiphinhapkhau_fk, diengiai, MAUHOADON, MAUSOHOADON, kyhieuchungtu, sochungtu, ngaychungtu, "
							+ "nhacungcap,diachincc, masothue, tienhang, thuesuat, tienthue)  "
							+ "values('"
							+ this.id
							+ "', N'"
							+ this.diengiai[i]
							+ "', '"
							+ mauhoadon.toUpperCase()
							+ "', '"
							+ this.mausoHD[i].toUpperCase()
							+ "', '"
							+ this.kyhieu[i].toUpperCase()
							+ "', '"
							+ this.sochungtu[i]
							+ "', "
							+ "'"
							+ this.ngaychungtu[i]
							+ "', N'"
							+ this.nhacungcap[i]
							+ "', N'"
							+ this.diaChiNCC[i]
							+ "', '"
							+ this.mst[i]
							+ "',"
							+ "'"
							+ this.tienhang[i].replaceAll(",", "")
							+ "', '"
							+ this.thuesuat[i].replaceAll(",", "")
							+ "', '"
							+ this.tienthue[i].replaceAll(",", "")
							+ "')";

					System.out.println("2.___Lay chi tiet: " + query);
					if (!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_CHIPHINHAPKHAU_CHITIET "
								+ query;
						db.getConnection().rollback();
						return false;
					}
					
					if(this.mst[i].trim().length() > 0){
						query = "select count(*) as sodong from MST_NCC where mst ='"+this.mst[i]+"'";
						
						ResultSet check = db.get(query);
						
						int count = 0;
						if(check!=null){
							if(check.next()) count= check.getInt("sodong"); 
						}
						check.close();
						
						if(count==0){
							query = "INSERT INTO MST_NCC (MST, NCC, DIACHI) VALUES (N'"+this.mst[i].trim()+"', N'"+(this.nhacungcap[i]==null?"":this.nhacungcap[i].replaceAll("'", "''").trim())+"', N'" + (this.diaChiNCC[i]==null?"":this.diaChiNCC[i].replaceAll("'", "''").trim()) + "')";
							
							if (!db.update(query))
							{
								this.msg = "Khong the tao moi MST_NCC: " + query;
								 
								db.getConnection().rollback();
								return false;
							}
						}else{
						query = "DELETE FROM MST_NCC WHERE MST ='" + this.mst[i] + "'";
						if(!db.update(query)){
							this.msg = "Không thể cập nhật MST_NCC" + query;
							db.getConnection().rollback();
							return false;
						}
						query = "INSERT INTO MST_NCC (MST, NCC, DIACHI) VALUES (N'" + this.mst[i].trim()+ "', N'"+ (this.nhacungcap[i] == null ? "" : this.nhacungcap[i].replaceAll("'", "''").trim())+ "', N'" + (this.diaChiNCC[i] == null ? "" : this.diaChiNCC[i].replaceAll("'", "''").trim())+ "')";

						if (!db.update(query)) {
							this.msg = "Khong the tao moi MST_NCC: " + query;

							db.getConnection().rollback();
							return false;
						}
						}
					}

				}
			}

			query = "delete ERP_CHIPHINHAPKHAU_PHANBO where chiphinhapkhau_fk = '"
					+ this.id + "'";
			System.out.println(query);
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_CHIPHINHAPKHAU_PHANBO "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			if(this.pnkIds.trim().length() > 0){
				Tinhphanbo_1(this.id);

			}else{

				// -- phần phân bổ sản phẩm---//
				double tongThue = 0;
				double tongTruocThue = 0;
				double tongSauThue = 0;
				double total = 0;
				for (int i = 0; i < spList.size(); i++) {
					ISanPhamPhanBo sp = spList.get(i);
					String masp = "NULL";
					String taisan_fk = "NULL";

					if (spList.get(i).getLoai().equals("1")) {
						masp = spList.get(i).getMaSp();
					} else
						taisan_fk = sp.getMaSp();

					if (sp.getPhanBo() > 0) {
						// tính phân bổ thuế
						double ptthue = 0;
						double tienthue = 0;
						double tienhang = 0;
						double phanbo = sp.getPhanBo();

						if (i == (spList.size() - 1)) {

							if (total + sp.getPhanBo() != (Double.parseDouble(this.Tongtienhang))) {
								phanbo = Double.parseDouble(this.Tongtienhang) - total;
							}

						} 

						total += phanbo;
						tienhang = phanbo;

						String queryInsert = "INSERT INTO ERP_CHIPHINHAPKHAU_PHANBO (CHIPHINHAPKHAU_FK, SANPHAM_FK, TIENHANG, TIENTHUE, TAISAN_FK, TIENHANG_GOC, NHANHANG_FK, SOLO, THUESUAT,STT) "
								+ " VALUES ("
								+ this.id
								+ ", (SELECT PK_SEQ FROM ERP_SANPHAM WHERE MA = '"
								+ masp
								+ "'), "
								+ tienhang
								+ ","
								+ tienthue
								+ ", "
								+ taisan_fk
								+ ", "
								+ (tienhang + tienthue)
								+ ", "
								+ ((sp.getManhanHang() != null && sp.getManhanHang().trim().length() == 0) ? null : sp.getManhanHang()) + ", N'"+sp.getSoLo()+"', " + ptthue +","+(i+1)+ ")";

						System.out
								.println("2. phan bo san pham: \n"
										+ queryInsert
										+ "\n-------------------------------------------------");
						if (!db.update(queryInsert)) {
							this.msg = "C1.5 KHông thể phân bổ sản phẩm ";
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}

		return true;
	}

	public void DbClose() {
		try {
			if (pnkRs != null)
				pnkRs.close();

			if (nccRs != null)
				nccRs.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		this.db.shutDown();
	}

	public String[] getMaHD() {
		return this.maHD;
	}

	public void setMaHD(String[] maHD) {
		this.maHD = maHD;
	}

	public String[] getMausoHD() {

		return this.mausoHD;
	}

	public void setMausoHD(String[] masoHD) {

		this.mausoHD = masoHD;
	}

	public ResultSet getTienteRs() {
		if (this.tienteRs == null) {
			this.tienteRs = db
					.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		}
		return this.tienteRs;
	}

	public void setTienteRs(ResultSet tienteRs) {

		this.tienteRs = tienteRs;
	}

	public String getTienteId() {
		return this.tienteId;
	}

	public void setTienteId(String tienteId) {
		this.tienteId = tienteId;

	}

	public String getTigia() {
		return this.tigia;
	}

	public void setTigia(String tigia) {
		this.tigia = tigia;

	}

	@Override
	public String getSoToKhaiId() {
		// TODO Auto-generated method stub
		return this.sotokhaiId;
	}

	@Override
	public void setSoToKhaiId(String SoToKhaiId) {
		// TODO Auto-generated method stub
		this.sotokhaiId = SoToKhaiId;
	}

	@Override
	public ResultSet getSoToKhaiRs() {
		// TODO Auto-generated method stub
		return this.RsSoTokhai;
	}

	@Override
	public void setSoToKhaiRs(ResultSet SoToKhairs) {
		// TODO Auto-generated method stub
		this.RsSoTokhai = SoToKhairs;
	}

	public String getTongtienhang() {
		return this.Tongtienhang;
	}

	public void setTongtienhang(String Tongtienhang) {
		this.Tongtienhang = Tongtienhang;
	}

	public String getTongtienthue() {
		return this.Tongtienthue;
	}

	public void setTongtienthue(String Tongtienthue) {
		this.Tongtienthue = Tongtienthue;
	}

	public String getTongtien_AVAT() {
		return this.Tongtien_AVAT;
	}

	public void setTongtien_AVAT(String Tongtien_AVAT) {
		this.Tongtien_AVAT = Tongtien_AVAT;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public void setSanPhamKhoList(List<Erp_Item> sanPhamKhoList) {
		this.sanPhamKhoList = sanPhamKhoList;
	}

	public List<Erp_Item> getSanPhamKhoList() {
		return sanPhamKhoList;
	}

	private void initSanPhamKhoList() {
		String query = "";
		if (this.id.length() > 0) {
			query = "SELECT SP.PK_SEQ AS PK_SEQ,SP.MA AS MA, SP.TEN, \n "
					+ " TIENHANG_GOC AS TONGTIEN \n"
					+
					/*
					 * "(SELECT SUM(TIENHANG + TIENTHUE) FROM ERP_CHIPHINHAPKHAU_PHANBO WHERE CHIPHINHAPKHAU_FK = CPNK_PB.CHIPHINHAPKHAU_FK ) AS TONGTIEN \n "
					 * +
					 
					"FROM ERP_CHIPHINHAPKHAU_PHANBO CPNK_PB \n "
					+ "INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = CPNK_PB.SANPHAM_FK \n "
					+ "WHERE CHIPHINHAPKHAU_FK = " + this.id + " \n ";
			/* "UNION ALL \n "; */

			Erp_Item.getListFromQuery_2(db, query, this.sanPhamKhoList);
		}

		/*
		 * query +=
		 * "SELECT SP.MA AS PK_SEQ, SP.MA + ' - ' + SP.TEN as TEN, 0 AS TONGTIEN \n"
		 * + "FROM ERP_SANPHAM SP \n" +
		 * "INNER JOIN ERP_LOAISANPHAM l on l.PK_SEQ = SP.LOAISANPHAM_FK\n" +
		 * "WHERE SP.TRANGTHAI = 1 AND l.isHangBan = 1 ";
		 */

		// if(this.id.length() > 0){
		// query +=
		// " AND SP.PK_SEQ IN (SELECT SANPHAM_FK FROM ERP_CHIPHINHAPKHAU_PHANBO WHERE CHIPHINHAPKHAU_FK = "
		// + this.id + ") ";
		// }

		System.out.println("San pham kho list: " + query);

	}

	public void setSoChungTu_Chu(String soChungTu_Chu) {
		this.soChungTu_Chu = soChungTu_Chu;
	}

	public String getSoChungTu_Chu() {
		return soChungTu_Chu;
	}

	public void setSoChungTu_So(String soChungTu_So) {
		this.soChungTu_So = soChungTu_So;
	}

	public String getSoChungTu_So() {
		return soChungTu_So;
	}

	public String getId() {
		return this.id;
	}

	public void setId(String Id) {
		this.id = Id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public void setNcc(String ncc) {

		this.ncc = ncc;
	}

	public String getNcc() {

		return this.ncc;
	}

	public void setNccId(String nccId) {

		this.nccId = nccId;
	}

	public String getNccId() {

		return this.nccId;
	}

	public void setNcc_cn(String ncc_cn) {

		this.ncc_cn = ncc_cn;
	}

	public String getPnkIds() {

		return this.pnkIds;
	}

	public void setPnkIds(String pnkIds) {

		this.pnkIds = pnkIds;
	}

	public String getNcc_cn() {

		return this.ncc_cn;
	}

	public void setNccId_cn(String nccId_cn) {

		this.nccId_cn = nccId_cn;
	}

	public String getNccId_cn() {

		return this.nccId_cn;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getGhichu() {

		return this.ghichu;
	}

	public void setGhichu(String ghichu) {

		this.ghichu = ghichu;
	}

	public String getNgaynhap() {

		return this.ngaynhap;
	}

	public void setNgaynhap(String tungay) {

		this.ngaynhap = tungay;
	}

	public String getPnkId() {

		return this.pnkId;
	}

	public void setPnkId(String khId) {

		this.pnkId = khId;
	}

	public ResultSet getPhieunhapRs() {

		return this.pnkRs;
	}

	public void setPhieunhapRs(ResultSet pnRs) {

		this.pnkRs = pnRs;
	}

	public String[] getDiengiai() {

		return this.diengiai;
	}

	public void setDiengiai(String[] diengiai) {

		this.diengiai = diengiai;
	}

	public String[] getKyhieu() {

		return this.kyhieu;
	}

	public void setKyhieu(String[] kyhieu) {

		this.kyhieu = kyhieu;
	}

	public String[] getSochungtu() {

		return this.sochungtu;
	}

	public void setSochungtu(String[] sochungtu) {

		this.sochungtu = sochungtu;
	}

	public String[] getNgaychungtu() {

		return this.ngaychungtu;
	}

	public void setNgaychungtu(String[] ngayct) {

		this.ngaychungtu = ngayct;
	}

	public String[] getNhacungcap() {

		return this.nhacungcap;
	}

	public void setNhacungcap(String[] nhacc) {

		this.nhacungcap = nhacc;
	}
	
	public String[] getDiaChiNCC(){
		return this.diaChiNCC;
	}
	
	public void setDiaChiNCC(String[] diaChiNCC){
		this.diaChiNCC = diaChiNCC;
	}

	public String[] getMst() {

		return this.mst;
	}

	public void setMst(String[] mst) {

		this.mst = mst;
	}

	public String[] getTienhang() {

		return this.tienhang;
	}

	public void setTienhang(String[] tienhang) {

		this.tienhang = tienhang;
	}

	public String[] getThuesuat() {

		return this.thuesuat;
	}

	public void setThuesuat(String[] thuesuat) {

		this.thuesuat = thuesuat;
	}

	public String[] getTienthue() {

		return this.tienthue;
	}

	public void setTienthue(String[] tienthue) {

		this.tienthue = tienthue;
	}

	public String[] getTongtien() {

		return this.tongtien;
	}

	public void setTongtien(String[] tongtien) {

		this.tongtien = tongtien;
	}

	public ResultSet getNccRs() {

		return this.nccRs;
	}

	public void setNccRs(ResultSet pnRs) {

		this.nccRs = pnRs;
	}
}