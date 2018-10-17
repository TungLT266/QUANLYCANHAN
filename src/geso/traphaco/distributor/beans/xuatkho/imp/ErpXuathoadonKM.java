package geso.traphaco.distributor.beans.xuatkho.imp;

import geso.traphaco.distributor.beans.xuatkho.IErpXuathoadonKM;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;

public class ErpXuathoadonKM implements IErpXuathoadonKM {
	String userId;
	String id;

	String ngayyeucau;
	String ngaygiaohangGui;
	String ghichu;

	String msg;
	String trangthai;
	String khoNhanId;
	ResultSet khoNhanRs;
	String tdv_dangnhap_id;
	String npp_duocchon_id;

	String tinhthanhId;
	ResultSet tinhthanhRs;
	String quanhuyenId;
	ResultSet quanhuyenRs;
	String nvgnId;
	ResultSet nvgnRs;
	String nvbhId;
	ResultSet nvbhRs;

	String khId;
	ResultSet khRs;

	String ddhId;
	ResultSet ddhRs;

	Hashtable<String, String> sanpham_soluong;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluongDat;
	String[] spTonkho;
	String[] spDaxuat;
	String[] spSoluong;
	String[] spGianhap;
	String[] spLoai;
	String[] spSCheme;

	String nppId;
	String nppTen;
	String sitecode;
	String xuatcho;

	ResultSet soloOLD;

	String phanloai;

	String tungay;
	String denngay;

	String kyhieuhd;
	String sohoadon;

	dbutils db;
	Utility util;

	public ErpXuathoadonKM() {
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "100000";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "";
		this.ngaygiaohangGui = "";

		this.sanpham_soluong = new Hashtable<String, String>();

		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.nvbhId = "";
		this.nvgnId = "";

		this.phanloai = "";
		this.db = new dbutils();
		this.util = new Utility();

		this.tungay = "";
		this.denngay = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";

		this.kyhieuhd = "";
		this.sohoadon = "";
	}

	public ErpXuathoadonKM(String id) {
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ghichu = "";
		this.khoNhanId = "100000";
		this.khId = "";
		this.msg = "";
		this.trangthai = "0";
		this.ddhId = "";
		this.xuatcho = "";
		this.ngaygiaohangGui = "";

		this.sanpham_soluong = new Hashtable<String, String>();

		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.nvbhId = "";
		this.nvgnId = "";

		this.phanloai = "";
		this.db = new dbutils();
		this.util = new Utility();

		this.tungay = "";
		this.denngay = "";

		this.kyhieuhd = "";
		this.sohoadon = "";
		this.tdv_dangnhap_id = "";
		this.npp_duocchon_id = "";
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

	public void setId(String Id) {
		this.id = Id;
	}

	public String getNgayyeucau() {
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) {
		this.ngayyeucau = ngayyeucau;
	}

	public String getKhoNhapId() {
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) {
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() {
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) {
		this.khoNhanRs = khonhapRs;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void createRs() {
		Utility util = new Utility();
		this.getNppInfo();

		String query = " select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI "
				+ " where TRANGTHAI = '1'  AND tructhuoc_fk = '"
				+ this.nppId
				+ "'  ";
		query += " and pk_seq in " + util.quyen_npp(this.userId);
		this.khRs = db.get(query);

		if (this.khId.trim().length() > 0 && this.tungay.trim().length() > 0
				&& this.denngay.trim().length() > 0) {
			/*
			 * query = "select PK_SEQ, SCHEME, DIENGIAI, TUNGAY, DENNGAY  "+
			 * "from CTKHUYENMAI  "+
			 * "where PK_SEQ in ( select CTKHUYENMAI_FK from CTKM_TRAKM where TRAKHUYENMAI_FK in ( select pk_seq from TRAKHUYENMAI where loai = 3 ) ) "
			 * +
			 * "  and pk_seq in ( select b.CTKMID from DONHANG a inner join DONHANG_CTKM_TRAKM b on a.PK_SEQ = b.DONHANGID where a.NPP_FK = '"
			 * + this.khId + "' ) " ;
			 * 
			 * if( this.tungay.trim().length() > 0 ) query += " and tungay >= '"
			 * + this.tungay + "' "; if ( this.denngay.trim().length() > 0 )
			 * query += " and denngay <= '" + this.denngay + "' ";
			 */

			query = "select PK_SEQ, SCHEME, DIENGIAI, TUNGAY, DENNGAY  "
					+ "from CTKHUYENMAI  "
					+ "where PK_SEQ in ( select CTKHUYENMAI_FK from CTKM_TRAKM where TRAKHUYENMAI_FK in ( select pk_seq from TRAKHUYENMAI where loai = 3 ) ) "
					+ "  and pk_seq in ( select b.CTKMID from DONHANG a inner join DONHANG_CTKM_TRAKM b on a.PK_SEQ = b.DONHANGID where a.NPP_FK = '"
					+ this.khId + "' and a.ngaynhap >= '" + this.tungay
					+ "' and a.ngaynhap <= '" + this.denngay + "' ) ";

			query += "order by DENNGAY desc ";

			System.out.println("----SCHEME: " + query);
			this.ddhRs = db.get(query);
		}

		System.out.println(":::: DON DAT HANG ID: " + this.ddhId);

		if (this.ddhId.trim().length() > 0 && this.khId.trim().length() > 0) {
			// INIT SP
			String _id = (this.id.trim().length() <= 0) ? "-1" : this.id;
			query = "select ddh.*, ISNULL(xuat.soluongXUAT, 0) as xuat,  ISNULL( daxuat.soluongDAXUAT, 0) as daxuat    "
					+ "from    "
					+ "(    "
					+ "	select sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI, SUM(hd.SOLUONG) as soluongDAT  "
					+ "	from DONHANG_CTKM_TRAKM hd inner join SANPHAM sp on hd.SPMA = sp.MA  "
					+ "			inner join DONVIDOLUONG dv on sp.DVDL_FK = dv.PK_SEQ  "
					+ "			inner join DONHANG dh on hd.DONHANGID = dh.PK_SEQ "
					+ "	where hd.CTKMID in (  "
					+ this.ddhId
					+ " ) and dh.npp_fk = '"
					+ this.khId
					+ "'  "
					+ "	group by sp.PK_SEQ, sp.MA, sp.TEN, dv.DONVI "
					+ ")    "
					+ "ddh left join     "
					+ "(    "
					+ "	select b.sanpham_fk, b.soluongXUAT as soluongXUAT    "
					+ "	from ERP_XUATHOADONKM a inner join ERP_XUATHOADONKM_SANPHAM b on a.PK_SEQ = b.xuathoadonkm_fk   "
					+ "	where a.PK_SEQ = '"
					+ _id
					+ "'   "
					+ ")   "
					+ "xuat on ddh.PK_SEQ = xuat.sanpham_fk left join     "
					+ "(    "
					+ "	select b.sanpham_fk, SUM( b.soluongXUAT ) as soluongDAXUAT    "
					+ "	from ERP_XUATHOADONKM a inner join ERP_XUATHOADONKM_SANPHAM b on a.PK_SEQ = b.xuathoadonkm_fk   "
					+ "	where a.PK_SEQ != '"
					+ _id
					+ "' and a.TRANGTHAI != 3    "
					+ "		and a.PK_SEQ in ( select xuathoadonkm_fk from  ERP_XUATHOADONKM_CTKM where ctkm_fk in (  "
					+ this.ddhId
					+ "  ) )   "
					+ "	group by b.sanpham_fk, b.LOAI, b.SCHEME   "
					+ ")   "
					+ "daxuat on ddh.PK_SEQ = daxuat.sanpham_fk ";

			System.out.println("---INIT SAN PHAM: " + query);
			ResultSet spRs = db.get(query);
			NumberFormat formater = new DecimalFormat("##,###,###");

			if (spRs != null) {
				try {
					String spID = "";
					String spMA = "";
					String spTEN = "";
					String spDONVI = "";
					String spSOLUONGDAT = "";
					String spTONKHO = "";
					String spDAXUAT = "";
					String spSOLUONGXUAT = "";

					while (spRs.next()) {
						double conLAI = 1000000;
						if (this.id.trim().length() <= 0)
							conLAI = spRs.getDouble("soluongDAT")
									- spRs.getDouble("daxuat");
						else
							conLAI = spRs.getDouble("xuat");

						if (conLAI > 0) {
							spID += spRs.getString("PK_SEQ") + "__";
							spMA += spRs.getString("MA") + "__";
							spTEN += spRs.getString("TEN") + "__";
							spDONVI += spRs.getString("DONVI") + "__";
							spSOLUONGDAT += formater.format(spRs
									.getDouble("soluongDAT")) + "__";
							spTONKHO += "0__";
							spDAXUAT += formater.format(spRs
									.getDouble("daxuat")) + "__";

							// SO LUONG XUAT LUC NAO CUNG BANG SO LUONG DAT
							if (spRs.getDouble("xuat") > 0)
								spSOLUONGXUAT += formater.format(spRs
										.getDouble("xuat")) + "__";
							else
								spSOLUONGXUAT += formater.format(conLAI) + "__";
						}
					}
					spRs.close();

					System.out.println("::: SO LUONG: " + spSOLUONGXUAT);
					if (spMA.trim().length() > 0) {
						spID = spID.substring(0, spID.length() - 2);
						this.spId = spID.split("__");

						spMA = spMA.substring(0, spMA.length() - 2);
						this.spMa = spMA.split("__");

						spTEN = spTEN.substring(0, spTEN.length() - 2);
						this.spTen = spTEN.split("__");

						spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
						this.spDonvi = spDONVI.split("__");

						spSOLUONGDAT = spSOLUONGDAT.substring(0,
								spSOLUONGDAT.length() - 2);
						this.spSoluongDat = spSOLUONGDAT.split("__");

						spTONKHO = spTONKHO.substring(0, spTONKHO.length() - 2);
						this.spTonkho = spTONKHO.split("__");

						spDAXUAT = spDAXUAT.substring(0, spDAXUAT.length() - 2);
						this.spDaxuat = spDAXUAT.split("__");

						spSOLUONGXUAT = spSOLUONGXUAT.substring(0,
								spSOLUONGXUAT.length() - 2);
						this.spSoluong = spSOLUONGXUAT.split("__");
					}
				} catch (Exception e) {
					e.printStackTrace();
					System.out.println("EXCEPTION SP: " + e.getMessage());
				}
			}
		} else {
			this.spId = null;
			this.spMa = null;
			this.spTen = null;
			this.spDonvi = null;
			this.spSoluongDat = null;
			this.spTonkho = null;
			this.spDaxuat = null;
			this.spSoluong = null;
		}

		// LẤY SỐ HÓA ĐƠN
		if (this.id.length() <= 0) {
			// TỰ TẠO SỐ HÓA ĐƠN CỦA USER

			String kyhieuhoadon = "";
			String sohoadon = "";
			String ngayhoadon = "";

			sohoadon = "NA";
			kyhieuhoadon = "NA";
			String mau = "1";

			String chuoi = "";
			long sohoadontu = 0;
			long sohoadonden = 0;

			try {
				{
					String query_kyhieu = " NV.KYHIEU ";
					String query_sohdTU = " NV.SOHOADONTU ";
					String query_sohdDEN = " NV.SOHOADONDEN ";
					String query_mauhd = "1";
					String query_ngayhd = " NV.NGAYHOADON  ";

					// LAY TT KHAI BAO SO HD TRONG DLN
					query = " SELECT ISNULL("
							+ query_ngayhd
							+ ", '') as NGAYHOADON, (CASE WHEN ISNULL("
							+ query_kyhieu
							+ ",'-1') = '' THEN '-1' ELSE ISNULL("
							+ query_kyhieu
							+ ",'-1') END)  as KYHIEU, \n"
							+ "        ISNULL("
							+ query_sohdTU
							+ ", -1) AS SOHOADONTU, ISNULL("
							+ query_sohdDEN
							+ ", -1) AS SOHOADONDEN,  \n"
							+ "        (select count(hd.pk_seq) as dem  "
							+ "         from ERP_XUATHOADONKM hd               "
							+ "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "
							+ query_mauhd
							+ " and hd.kyhieu = ISNULL("
							+ query_kyhieu
							+ ",'-1')  "
							+ "               and cast(hd.sohoadon as int) >= cast(ISNULL("
							+ query_sohdTU
							+ ", -1) as int) "
							+ "               and cast(hd.sohoadon as int) <= cast(ISNULL("
							+ query_sohdDEN
							+ ", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_OTC, \n"
							+ "        (select count(hd.pk_seq) as dem  "
							+ "         from ERP_HOADONNPP hd               "
							+ "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "
							+ query_mauhd
							+ " and hd.kyhieu = ISNULL("
							+ query_kyhieu
							+ ",'-1')  "
							+ "               and cast(hd.sohoadon as int) >= cast(ISNULL("
							+ query_sohdTU
							+ ", -1) as int) "
							+ "               and cast(hd.sohoadon as int) <= cast(ISNULL("
							+ query_sohdDEN
							+ ", -1) as int) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC \n"
							+ " FROM NHANVIEN NV  \n" + " WHERE NV.pk_seq = '"
							+ userId + "' \n";
					System.out.println("Câu check khai báo SHD " + query);
					ResultSet rsLayDL = db.get(query);

					int check_OTC = 0;
					int check_ETC = 0;

					while (rsLayDL.next()) {
						kyhieuhoadon = rsLayDL.getString("kyhieu");
						sohoadontu = rsLayDL.getString("sohoadontu").trim()
								.length() <= 0 ? -1 : rsLayDL
								.getLong("sohoadontu");
						sohoadonden = rsLayDL.getString("sohoadonden").trim()
								.length() <= 0 ? -1 : rsLayDL
								.getLong("sohoadonden");
						;
						ngayhoadon = rsLayDL.getString("ngayhoadon");

						check_OTC = rsLayDL.getInt("isSd_OTC");
						check_ETC = rsLayDL.getInt("isSd_ETC");
					}
					rsLayDL.close();

					if (kyhieuhoadon.equals("-1") || sohoadontu == -1
							|| sohoadonden == -1) {
						this.msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
					}

					if (check_OTC <= 0 && check_ETC <= 0) {
						chuoi = ("000000" + sohoadontu);
						chuoi = chuoi.substring(chuoi.length() - 7,
								chuoi.length());
					} else {
						// LAY SOIN MAX TRONG OTC && ETC
						query = " SELECT  \n"
								+ "       isnull((select max(cast(sohoadon as float)) as soin_max  "
								+ "        from ERP_XUATHOADONKM hd               "
								+ "        where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "
								+ query_mauhd
								+ " and hd.kyhieu = ISNULL("
								+ query_kyhieu
								+ ",'-1')  "
								+ "              and cast(hd.sohoadon as int) >= cast(ISNULL("
								+ query_sohdTU
								+ ", -1) as int) "
								+ "              and cast(hd.sohoadon as int) <= cast(ISNULL("
								+ query_sohdDEN
								+ ", -1) as int) and hd.nguoisua = NV.PK_SEQ ),0) soinMAX_OTC, \n"
								+ "       isnull((select max(cast(sohoadon as float)) as soin_max "
								+ "        from ERP_HOADONNPP hd               "
								+ "        where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "
								+ query_mauhd
								+ " and hd.kyhieu = ISNULL("
								+ query_kyhieu
								+ ",'-1')  "
								+ "              and cast(hd.sohoadon as int) >= cast(ISNULL("
								+ query_sohdTU
								+ ", -1) as int) "
								+ "              and cast(hd.sohoadon as int) <= cast(ISNULL("
								+ query_sohdDEN
								+ ", -1) as int) and hd.nguoisua = NV.PK_SEQ),0) soinMAX_ETC  \n"
								+ " FROM NHANVIEN NV  \n"
								+ " WHERE NV.pk_seq = '" + userId + "' \n";

						System.out.println("Câu lấy SHD Max: " + query);
						long soinMAX_OTC = 0;
						long soinMAX_ETC = 0;

						ResultSet laySOIN = db.get(query);
						while (laySOIN.next()) {
							soinMAX_OTC = laySOIN.getLong("soinMAX_OTC");
							soinMAX_ETC = laySOIN.getLong("soinMAX_ETC");
						}
						laySOIN.close();

						if (soinMAX_OTC > soinMAX_ETC) {
							chuoi = ("000000" + (soinMAX_OTC > 0 ? (soinMAX_OTC + 1)
									: "1"));
						} else {
							chuoi = ("000000" + (soinMAX_ETC > 0 ? (soinMAX_ETC + 1)
									: "1"));
						}

						chuoi = chuoi.substring(chuoi.length() - 7,
								chuoi.length()).trim();
					}

					if (Integer.parseInt(chuoi) > sohoadonden) {
						// CHECK THEM NEU TRONG KHOANG HOA DON CUA USER DO VAN
						// CON SHD THI TU DONG LAY SO DO
						query = " SELECT  \n"
								+ "      (select  max(cast(hd.sohoadon as float)) as soin_max   \n"
								+ "       from ERP_HOADONNPP hd                                     \n"
								+ "       where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "
								+ query_mauhd
								+ " and hd.kyhieu = ISNULL("
								+ query_kyhieu
								+ ",'-1')  \n"
								+ "             and cast(hd.sohoadon as float) >= cast(ISNULL("
								+ query_sohdTU
								+ ", -1) as float)                                 \n"
								+ "             and cast(hd.sohoadon as float) <= cast(ISNULL("
								+ query_sohdDEN
								+ ", -1) as float)  and hd.nguoisua = NV.PK_SEQ                               \n"
								+ "       having max(cast(hd.sohoadon as float)) != ( select  MAX(cast(SOHOADON as float)) as SOIN_MAX  from ERP_HOADONNPP where trangthai!= 3 and  kyhieu = ISNULL("
								+ query_kyhieu
								+ ",'-1')  and nguoisua = NV.PK_SEQ) \n"
								+ "       ) soinMAX_OTC 										  \n"
								+ " FROM NHANVIEN NV   \n"
								+ " WHERE NV.pk_seq = '" + userId + "' \n";

						System.out.println("Câu lấy HD không dùng " + query);
						ResultSet SoMAX_HD = db.get(query);
						String soinmax = "";
						while (SoMAX_HD.next()) {
							soinmax = SoMAX_HD.getString("soinMAX_OTC") == null ? ""
									: SoMAX_HD.getString("soinMAX_OTC");
							chuoi = ("000000" + (SoMAX_HD
									.getLong("soinMAX_OTC")));

						}
						SoMAX_HD.close();

						chuoi = chuoi.substring(chuoi.length() - 7,
								chuoi.length());

						if (soinmax.trim().length() <= 0) {
							this.msg = "Số hóa đơn tiếp theo  đã vượt quá Số hóa đơn đến ("
									+ sohoadonden
									+ ")  trong dữ liệu nền. Vui lòng vào dữ liệu nền khai báo lại ! ";

						}
					}

					this.sohoadon = chuoi;

					// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON
					// NAO HIEN TAI TRONG HD O & E
					query = " select (select count(*) from ERP_XUATHOADONKM where (SOHOADON = '"
							+ sohoadon
							+ "' ) and kyhieu = '"
							+ kyhieuhoadon
							+ "' and trangthai != '2' and npp_fk = '"
							+ nppId
							+ "' and sohoadon != 'NA' and mauhoadon = "
							+ query_mauhd
							+ ") as KtraOTC, "
							+ "        (select count(*) from ERP_HOADONNPP where (SOHOADON = '"
							+ sohoadon
							+ "' ) and kyhieu = '"
							+ kyhieuhoadon
							+ "' and trangthai != '3' and npp_fk = '"
							+ nppId
							+ "' and sohoadon != 'NA' and mauhoadon = "
							+ query_mauhd
							+ ") as KtraETC "
							+ " from NHANVIEN where pk_seq = '" + userId + "' ";

					System.out.println("Câu kiểm tra lại SHD: " + query);
					ResultSet RsRs = db.get(query);
					int KT_OTC = 0;
					int KT_ETC = 0;
					while (RsRs.next()) {
						KT_OTC = RsRs.getInt("KtraOTC");
						KT_ETC = RsRs.getInt("KtraETC");
					}

					if (KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC)
													// CÓ SỐ HD TRÙNG VS SỐ HÓA
													// ĐƠN MỚI TẠO
					{
						// msg =
						// "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
						this.msg = "Không thể duyệt đơn hàng: Số hoá đơn tiếp theo '"
								+ sohoadon + "' đã có trên hệ thống! ";
					}
				}
			} catch (Exception e) {
				this.msg = "Lỗi xảy ra trong quá trình lấy Số hóa đơn";
				e.printStackTrace();
			}
		}

	}

	public void init() {
		String query = "select ngayhoadon, ISNULL(ghichu, '') as ghichu, npp_dat_fk as khId, trangthai, tungay, denngay, isnull(kyhieu,'') kyhieu , isnull(sohoadon,'') sohoadon "
				+ "from ERP_XUATHOADONKM where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				if (rs.next()) {
					this.ngayyeucau = rs.getString("ngayhoadon");
					this.ghichu = rs.getString("ghichu");
					this.khId = rs.getString("khId") == null ? "" : rs
							.getString("khId");
					this.trangthai = rs.getString("trangthai");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.kyhieuhd = rs.getString("kyhieu");
					this.sohoadon = rs.getString("sohoadon");
				}
				rs.close();

				// INIT DDH
				query = "select ctkm_fk from ERP_XUATHOADONKM_CTKM where xuathoadonkm_fk = '"
						+ this.id + "' ";
				rs = db.get(query);
				String ddhID = "";
				while (rs.next()) {
					ddhID += rs.getString("ctkm_fk") + ",";
				}
				rs.close();

				if (ddhID.trim().length() > 0) {
					this.ddhId = ddhID.substring(0, ddhID.length() - 1);
				}
			} catch (Exception e) {
				System.out.println("---LOI INIT: " + e.getMessage());
				e.printStackTrace();
			}
		}

		this.createRs();
	}

	public void DBclose() {

		try {

			if (khoNhanRs != null) {
				khoNhanRs.close();
			}

			this.db.shutDown();

		} catch (Exception er) {

		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getGhichu() {

		return this.ghichu;
	}

	public void setGhichu(String ghichu) {

		this.ghichu = ghichu;
	}

	public String[] getSpId() {

		return this.spId;
	}

	public void setSpId(String[] spId) {

		this.spId = spId;
	}

	public String[] getSpMa() {

		return this.spMa;
	}

	public void setSpMa(String[] spMa) {

		this.spMa = spMa;
	}

	public String[] getSpTen() {

		return this.spTen;
	}

	public void setSpTen(String[] spTen) {

		this.spTen = spTen;
	}

	public String[] getSpDonvi() {

		return this.spDonvi;
	}

	public void setSpDonvi(String[] spDonvi) {

		this.spDonvi = spDonvi;
	}

	public String[] getSpSoluong() {

		return this.spSoluong;
	}

	public void setSpSoluong(String[] spSoluong) {

		this.spSoluong = spSoluong;
	}

	public String[] getSpGianhap() {

		return this.spGianhap;
	}

	public void setSpGianhap(String[] spGianhap) {

		this.spGianhap = spGianhap;
	}

	public String getNppId() {

		return this.nppId;
	}

	public void setNppId(String khId) {

		this.nppId = khId;
	}

	public ResultSet getKhRs() {

		return this.khRs;
	}

	public void setKhRs(ResultSet khRs) {

		this.khRs = khRs;
	}

	public String getDondathangId() {

		return this.ddhId;
	}

	public void setDondathangId(String kbhId) {

		this.ddhId = kbhId;
	}

	public ResultSet getDondathangRs() {

		return this.ddhRs;
	}

	public void setDondathangRs(ResultSet ddhRs) {

		this.ddhRs = ddhRs;
	}

	public Hashtable<String, String> getSanpham_Soluong() {

		return this.sanpham_soluong;
	}

	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {

		this.sanpham_soluong = sp_soluong;
	}

	public ResultSet getSoloTheoSp(String spIds, String tongluong, String scheme) {
		/*
		 * String query =
		 * "select sum(soluong) as tuDEXUAT, SOLO, NGAYHETHAN, 0 as AVAILABLE "
		 * + "from ERP_DONDATHANGNPP_SANPHAM_CHITIET " + " where sanpham_fk = '"
		 * + spIds + "' and dondathang_fk in ( " + this.ddhId +
		 * " ) and ltrim(rtrim(scheme)) = '" + scheme +
		 * "' group by SOLO, NGAYHETHAN ";
		 */

		String query = "select sum(soluong_chuan) as tuDEXUAT, SOLO, NGAYHETHAN, 0 as AVAILABLE "
				+ "from ERP_HOADONNPP_SP_CHITIET "
				+ " where MA = ( select ma from SANPHAM where pk_seq = '"
				+ spIds
				+ "' ) and hoadon_fk in ( "
				+ this.ddhId
				+ " )"
				+ " and ltrim(rtrim(scheme)) = '"
				+ scheme
				+ "' group by SOLO, NGAYHETHAN ";
		System.out.println("---LAY SO LO OLD:: " + query);
		return db.get(query);
	}

	public ResultSet getSoloTheoSpOLD(String spIds, String tongluong) {
		/*
		 * String query = "select soluong, SOLO, NGAYHETHAN " +
		 * "from ERP_DONDATHANGNPP_SANPHAM_CHITIET where sanpham_fk = '" + spIds
		 * +
		 * "' and dondathang_fk = ( select ddh_fk from ERP_SOXUATHANGNPP where PK_SEQ = '"
		 * + this.id + "' ) ";
		 */

		String query = "select sum(soluong_chuan) as soluong, SOLO, NGAYHETHAN, 0 as AVAILABLE "
				+ "from ERP_HOADONNPP_SP_CHITIET "
				+ " where MA = ( select ma from SANPHAM where pk_seq = '"
				+ spIds
				+ "' ) and hoadon_fk in ( "
				+ this.ddhId
				+ " )"
				+ " group by SOLO, NGAYHETHAN ";
		System.out.println("---LAY SO LO OLD:: " + query);
		return db.get(query);
	}

	public String[] getSpSoluongDat() {

		return this.spSoluongDat;
	}

	public void setSpSoluongDat(String[] spSoluong) {

		this.spSoluongDat = spSoluong;
	}

	public String[] getSpTonKho() {

		return this.spTonkho;
	}

	public void setSpTonKho(String[] spTonkho) {

		this.spTonkho = spTonkho;
	}

	public String[] getSpDaXuat() {

		return this.spDaxuat;
	}

	public void setSpDaXuat(String[] spDaXuat) {

		this.spDaxuat = spDaXuat;
	}

	public boolean create() {
		if (this.ngayyeucau.trim().length() < 10) {
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}

		if (this.khId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if (this.ddhId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn SCHEME";
			return false;
		}

		if (spMa == null) {
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		} else {
			boolean coSP = false;
			for (int i = 0; i < spId.length; i++) {
				if (spSoluong[i].trim().length() > 0) {
					if (Double.parseDouble(spSoluong[i].trim().replaceAll(",",
							"")) > 0)
						coSP = true;
				}
			}

			if (!coSP) {
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
		}

		try {
			db.getConnection().setAutoCommit(false);

			String loaiNPP = "1";
			String query = "";

			// LAY KY HIEU HOA DON ,SOHDTU TRONG DLN
			long sohoadontu = 0;
			long sohoadonden = 0;

			String chuoi = "";
			String query_kyhieu = " NV.KYHIEU ";
			String query_sohdTU = " NV.SOHOADONTU ";
			String query_sohdDEN = " NV.SOHOADONDEN ";
			String query_mauhd = "1";
			String query_ngayhd = " NV.NGAYHOADON  ";
			int kt_kyhieu = 0;
			String kyhieuhoadon = "";

			// LAY TT KHAI BAO SO HD TRONG DLN
			query = " SELECT ISNULL("
					+ query_ngayhd
					+ ", '') as NGAYHOADON, (CASE WHEN ISNULL("
					+ query_kyhieu
					+ ",'-1') = '' THEN '-1' ELSE ISNULL("
					+ query_kyhieu
					+ ",'-1') END)  as KYHIEU, \n"
					+ "        ISNULL("
					+ query_sohdTU
					+ ", -1) AS SOHOADONTU, ISNULL("
					+ query_sohdDEN
					+ ", -1) AS SOHOADONDEN,  \n"
					+ "        (select count(hd.pk_seq) as dem  "
					+ "         from ERP_XUATHOADONKM hd               "
					+ "         where hd.trangthai != 2 and hd.sohoadon != 'NA' and hd.mauhoadon = "
					+ query_mauhd
					+ " and hd.kyhieu = ISNULL("
					+ query_kyhieu
					+ ",'-1')  "
					+ "               and cast(hd.sohoadon as int) >= cast(ISNULL("
					+ query_sohdTU
					+ ", -1) as int) "
					+ "               and cast(hd.sohoadon as int) <= cast(ISNULL("
					+ query_sohdDEN
					+ ", -1) as int) and hd.NGUOISUA = NV.PK_SEQ ) isSd_OTC, \n"
					+ "        (select count(hd.pk_seq) as dem  "
					+ "         from ERP_HOADONNPP hd               "
					+ "         where hd.trangthai != 3 and hd.sohoadon != 'NA' and hd.mauhoadon = "
					+ query_mauhd
					+ " and hd.kyhieu = ISNULL( "
					+ query_kyhieu
					+ ",'-1')  "
					+ "               and cast(hd.sohoadon as int) >= cast( ISNULL("
					+ query_sohdTU
					+ ", -1) as int ) "
					+ "               and cast(hd.sohoadon as int) <= cast( ISNULL("
					+ query_sohdDEN
					+ ", -1) as int ) and hd.NGUOISUA = NV.PK_SEQ) isSd_ETC, \n"
					+ "        ISNULL((SELECT SOHOADONTU FROM NHANVIEN NV1 WHERE NV1.PK_SEQ = NV.PK_SEQ and isnull(kyhieu,'') = '"
					+ this.kyhieuhd + "' ), 0) KTKYHIEU \n"
					+ " FROM NHANVIEN NV  \n" + " WHERE NV.pk_seq = '" + userId
					+ "' and NV.KYHIEU = '" + this.kyhieuhd + "' \n";
			System.out.println("Câu check khai báo SHD " + query);
			ResultSet rsLayDL = db.get(query);

			int check_OTC = 0;
			int check_ETC = 0;

			while (rsLayDL.next()) {
				kyhieuhoadon = rsLayDL.getString("kyhieu");
				sohoadontu = rsLayDL.getString("sohoadontu").trim().length() <= 0 ? -1
						: rsLayDL.getLong("sohoadontu");
				sohoadonden = rsLayDL.getString("sohoadonden").trim().length() <= 0 ? -1
						: rsLayDL.getLong("sohoadonden");
				check_OTC = rsLayDL.getInt("isSd_OTC");
				check_ETC = rsLayDL.getInt("isSd_ETC");
				kt_kyhieu = rsLayDL.getInt("KTKYHIEU");
			}
			rsLayDL.close();

			if (kyhieuhoadon.equals("-1") || sohoadontu == -1
					|| sohoadonden == -1) {
				msg = "Vui lòng thiết lập khoảng Số hóa đơn cho USER ";
				return false;
			}

			if (kt_kyhieu == 0) {
				this.msg = " Ký hiệu "
						+ this.kyhieuhd
						+ " không giống với ký hiệu khai báo trong dữ liệu nền/ Chưa khai báo số hóa đơn trong dữ liệu nền (Số hóa đơn) ";
				return false;
			}

			if (check_OTC <= 0 && check_ETC <= 0) {
				chuoi = ("000000" + sohoadontu);
				chuoi = chuoi.substring(chuoi.length() - 7, chuoi.length());

				sohoadon = chuoi;
			} else {

				// KIEM TRA LAI SO HOA DON MOI TAO CO TRUNG VS SO HOA DON NAO
				// HIEN TAI TRONG HD O & E
				query = " select ( select count(*) from ERP_XUATHOADONKM where ( select dbo.ftXuLySo( (RTRIM(LTRIM(sohoadon) ) ) ) ) = ( select dbo.ftXuLySo( (RTRIM(LTRIM("
						+ sohoadon
						+ ") ) ) ) ) and kyhieu = '"
						+ kyhieuhoadon
						+ "' and trangthai != '2' and npp_fk = '"
						+ this.nppId
						+ "' and sohoadon != 'NA' and mauhoadon = "
						+ query_mauhd
						+ ") as KtraOTC, \n"
						+ "        ( select count(*) from ERP_HOADONNPP where ( select dbo.ftXuLySo( (RTRIM(LTRIM(sohoadon) ) ) ) ) = ( select dbo.ftXuLySo( (RTRIM(LTRIM("
						+ sohoadon
						+ ") ) ) ) ) and kyhieu = '"
						+ kyhieuhoadon
						+ "' and trangthai != '3' and pk_seq != "
						+ this.id
						+ " and npp_fk = '"
						+ this.nppId
						+ "' and sohoadon != 'NA' and mauhoadon = "
						+ query_mauhd
						+ ") as KtraETC, \n"
						+ "        ( select count(*) from ERP_HOADONNPP where  ( select dbo.ftXuLySo( (RTRIM(LTRIM(sohoadon) ) ) ) ) = ( select dbo.ftXuLySo( (RTRIM(LTRIM("
						+ sohoadon
						+ ") ) ) ) ) and NGAYXUATHD > '"
						+ this.ngayyeucau
						+ "' and kyhieu = '"
						+ kyhieuhoadon
						+ "' and trangthai != '3' and npp_fk = '"
						+ this.nppId
						+ "' and sohoadon != 'NA' and mauhoadon = "
						+ query_mauhd
						+ ") as is_KtraOTC_dk, \n"
						+ "        ( select count(*) from ERP_XUATHOADONKM where ( select dbo.ftXuLySo( (RTRIM(LTRIM(sohoadon) ) ) ) ) = ( select dbo.ftXuLySo( (RTRIM(LTRIM("
						+ sohoadon
						+ ") ) ) ) ) and NGAYHOADON > '"
						+ this.ngayyeucau
						+ "' and kyhieu = '"
						+ kyhieuhoadon
						+ "' and trangthai != '3' and npp_fk = '"
						+ this.nppId
						+ "' and sohoadon != 'NA' and mauhoadon = "
						+ query_mauhd + " ) as is_KtraETC_dk \n" +

						" from NHANVIEN where pk_seq = '" + userId + "' ";

				System.out.println("Câu kiểm tra lại SHD: " + query);
				ResultSet RsRs = db.get(query);
				int KT_OTC = 0;
				int KT_ETC = 0;

				int is_KT_OTC = 0;
				int is_KT_ETC = 0;

				while (RsRs.next()) {
					KT_OTC = RsRs.getInt("KtraOTC");
					KT_ETC = RsRs.getInt("KtraETC");

					is_KT_OTC = RsRs.getInt("is_KtraOTC_dk");
					is_KT_ETC = RsRs.getInt("is_KtraETC_dk");

				}

				if (Integer.parseInt(sohoadonden + "") < Integer
						.parseInt(this.sohoadon.trim())) {
					this.msg = "Số hóa đơn này đã vượt quá số hóa đơn đến trong dữ liệu nền. Vui lòng thiết lập lại số hóa đơn ";
					db.getConnection().rollback();
					return false;
				}

				if (KT_OTC > 0 || KT_ETC > 0) // CÓ HÓA ĐƠN (CỦA USER KHÁC) CÓ
												// SỐ HD TRÙNG VS SỐ HÓA ĐƠN MỚI
												// TẠO
				{
					// msg =
					// "Số hóa đơn tiếp theo đã trùng với số hóa đơn trong Hóa Đơn OTC/ETC ! ";
					msg = "Số hoá đơn '" + sohoadon + "' đã có trên hệ thống! ";
					db.getConnection().rollback();
					return false;
				}

				if (is_KT_OTC > 0 || is_KT_ETC > 0) // NẾU CÓ SỐ HÓA ĐƠN LỚN HƠN
													// MÀ NGÀY XUẤT NHỎ HƠN CỦA
													// HÓA ĐƠN TIẾP THEO THÌ K
													// CHO
				{
					msg = "Yêu cầu check lại ngày thiết lập số hóa đơn!";
					db.getConnection().rollback();
					return false;
				}
			}

			query = " insert ERP_XUATHOADONKM(NgayHoaDon, ghichu, trangthai, npp_fk, NPP_DAT_FK, ngaytao, nguoitao, ngaysua, nguoisua, tungay, denngay, kyhieu, sohoadon, mauhoadon) "
					+ " select top(1) '"
					+ this.ngayyeucau
					+ "', N'"
					+ this.ghichu
					+ "', '0', '"
					+ this.nppId
					+ "', '"
					+ this.khId
					+ "', "
					+ " '"
					+ getDateTime()
					+ "', '"
					+ this.userId
					+ "', '"
					+ getDateTime()
					+ "', '"
					+ this.userId
					+ "', '"
					+ this.tungay
					+ "', '"
					+ this.denngay
					+ "', '"
					+ this.kyhieuhd + "', '" + this.sohoadon + "', '1' ";

			System.out.println("1.Insert ERP_SOXUATHANGNPP: " + query);

			if (!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_XUATHOADONKM " + query;
				db.getConnection().rollback();
				return false;
			}

			// LAY ID
			ResultSet rsDDH = db.get("select scope_identity() as ID ");
			if (rsDDH.next()) {
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();

			query = "Insert ERP_XUATHOADONKM_CTKM(xuathoadonkm_fk, ctkm_fk) "
					+ "select '" + this.id
					+ "', pk_seq from CTKHUYENMAI where pk_seq in ( "
					+ this.ddhId + " )  ";
			System.out.println("2.chen ERP_SOXUATHANGNPP: " + query);
			if (!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_XUATHOADONKM_CTKM " + query;
				db.getConnection().rollback();
				return false;
			}

			for (int i = 0; i < spId.length; i++) {
				if (spId[i].trim().length() > 0
						&& Double.parseDouble(spSoluong[i].trim().replaceAll(
								",", "")) > 0) {
					query = "insert ERP_XUATHOADONKM_SANPHAM( xuathoadonkm_fk, sanpham_fk, soluongDAT, daxuat, soluongXUAT ) "
							+ "select '"
							+ this.id
							+ "', '"
							+ spId[i]
							+ "', '"
							+ spSoluongDat[i].replaceAll(",", "")
							+ "', '"
							+ spDaxuat[i].replaceAll(",", "")
							+ "', '"
							+ spSoluong[i].replaceAll(",", "") + "' ";

					System.out.println("1.1.Insert YCXK - SP: " + query);
					if (!db.update(query)) {
						this.msg = "Khong the tao moi ERP_XUATHOADONKM_SANPHAM: "
								+ query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			// CHEN SAN PHAM CHI TIET
			query = "insert ERP_XUATHOADONKM_DONHANG( xuathoadonkm_fk, donhang_fk ) "
					+ "	select '"
					+ this.id
					+ "', dh.PK_SEQ  "
					+ "	from DONHANG_CTKM_TRAKM hd inner join DONHANG dh on hd.DONHANGID = dh.PK_SEQ   "
					+ "	where hd.CTKMID in ( "
					+ this.ddhId
					+ " )   and dh.NPP_FK = '" + this.khId + "' ";
			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_XUATHOADONKM_DONHANG: "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			query = "insert ERP_XUATHOADONKM_SANPHAM_CHITIET( xuathoadonkm_fk, sanpham_fk, soluongXUAT, SOLO, NGAYHETHAN )  "
					+ "select '"
					+ this.id
					+ "', SANPHAM_FK, SUM( SOLUONG ) as soluongXUAT, SOLO, NGAYHETHAN "
					+ "from DONHANG_SANPHAM_CHITIET "
					+ "where DONHANG_FK in ( select donhang_fk from ERP_XUATHOADONKM_DONHANG where xuathoadonkm_fk = '"
					+ this.id
					+ "' ) "
					+ "group by SANPHAM_FK, SOLO, NGAYHETHAN ";
			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_XUATHOADONKM_DONHANG: "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
	}

	public boolean update() {
		if (this.ngayyeucau.trim().length() < 10) {
			this.msg = "Vui lòng nhập ngày yêu cầu";
			return false;
		}

		if (this.khId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if (this.ddhId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn SCheme";
			return false;
		}

		/*
		 * if( this.khoNhanId.trim().length() <= 0 ) { this.msg =
		 * "Vui lòng chọn kho xuất hàng"; return false; }
		 */

		if (spMa == null) {
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
			return false;
		} else {
			boolean coSP = false;
			for (int i = 0; i < spId.length; i++) {
				if (spSoluong[i].trim().length() > 0) {
					if (Double.parseDouble(spSoluong[i].trim().replaceAll(",",
							"")) > 0)
						coSP = true;
				}
			}

			if (!coSP) {
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
		}

		try {
			db.getConnection().setAutoCommit(false);

			String npp_dat_fk = this.khId.trim().length() <= 0 ? "NULL"
					: this.khId;

			String query = " Update ERP_SOXUATHANGNPP set NgayYeuCau = '"
					+ this.ngayyeucau + "', ghichu = N'" + this.ghichu
					+ "', npp_fk = '" + this.nppId + "',  " + "	npp_dat_fk = "
					+ npp_dat_fk + ", " + "	ngaysua = '" + this.getDateTime()
					+ "', nguoisua = '" + this.userId + "', tungay = '"
					+ this.tungay + "', denngay = '" + this.denngay
					+ "' where pk_seq = '" + this.id + "' ";

			System.out.println("1.Update ERP_XUATHOADONKM: " + query);
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_XUATHOADONKM " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_XUATHOADONKM_CTKM where xuathoadonkm_fk = '"
					+ this.id + "' ";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_XUATHOADONKM_CTKM " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_XUATHOADONKM_SANPHAM where xuathoadonkm_fk = '"
					+ this.id + "' ";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_XUATHOADONKM_SANPHAM "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_XUATHOADONKM_DONHANG where xuathoadonkm_fk = '"
					+ this.id + "' ";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_XUATHOADONKM_DONHANG "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_XUATHOADONKM_SANPHAM_CHITIET where xuathoadonkm_fk = '"
					+ this.id + "' ";
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_XUATHOADONKM_SANPHAM_CHITIET "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			query = "Insert ERP_XUATHOADONKM_CTKM(xuathoadonkm_fk, ctkm_fk) "
					+ "select '" + this.id
					+ "', pk_seq from CTKHUYENMAI where pk_seq in ( "
					+ this.ddhId + " )  ";
			System.out.println("2.chen ERP_SOXUATHANGNPP: " + query);
			if (!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_XUATHOADONKM_CTKM " + query;
				db.getConnection().rollback();
				return false;
			}

			for (int i = 0; i < spId.length; i++) {
				if (spId[i].trim().length() > 0
						&& Double.parseDouble(spSoluong[i].trim().replaceAll(
								",", "")) > 0) {
					query = "insert ERP_XUATHOADONKM_SANPHAM( xuathoadonkm_fk, sanpham_fk, soluongDAT, daxuat, soluongXUAT ) "
							+ "select '"
							+ this.id
							+ "', '"
							+ spId[i]
							+ "', '"
							+ spSoluongDat[i].replaceAll(",", "")
							+ "', '"
							+ spDaxuat[i].replaceAll(",", "")
							+ "', '"
							+ spSoluong[i].replaceAll(",", "") + "' ";

					System.out.println("1.1.Insert YCXK - SP: " + query);
					if (!db.update(query)) {
						this.msg = "Khong the tao moi ERP_XUATHOADONKM_SANPHAM: "
								+ query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			// CHEN SAN PHAM CHI TIET
			query = "insert ERP_XUATHOADONKM_DONHANG( xuathoadonkm_fk, donhang_fk ) "
					+ "	select '"
					+ this.id
					+ "', dh.PK_SEQ  "
					+ "	from DONHANG_CTKM_TRAKM hd inner join DONHANG dh on hd.DONHANGID = dh.PK_SEQ   "
					+ "	where hd.CTKMID in ( "
					+ this.ddhId
					+ " )   and dh.NPP_FK = '" + this.khId + "' ";
			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_XUATHOADONKM_DONHANG: "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			query = "insert ERP_XUATHOADONKM_SANPHAM_CHITIET( xuathoadonkm_fk, sanpham_fk, soluongXUAT, SOLO, NGAYHETHAN )  "
					+ "select '"
					+ this.id
					+ "', SANPHAM_FK, SUM( SOLUONG ) as soluongXUAT, SOLO, NGAYHETHAN "
					+ "from DONHANG_SANPHAM_CHITIET "
					+ "where DONHANG_FK in ( select donhang_fk from ERP_XUATHOADONKM_DONHANG where xuathoadonkm_fk = '"
					+ this.id
					+ "' ) "
					+ "group by SANPHAM_FK, SOLO, NGAYHETHAN ";
			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_XUATHOADONKM_DONHANG: "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public String[] getSpLoai() {

		return this.spLoai;
	}

	public void setSpLoai(String[] spLoai) {

		this.spLoai = spLoai;
	}

	public String[] getSpScheme() {

		return this.spSCheme;
	}

	public void setSpScheme(String[] spScheme) {

		this.spSCheme = spScheme;
	}

	public String getKhId() {
		return this.khId;
	}

	public void setKhId(String khId) {
		this.khId = khId;
	}

	public String getNppTen() {
		return this.nppTen;
	}

	public void setNppTen(String nppTen) {
		this.nppTen = nppTen;
	}

	public String getSitecode() {
		return this.sitecode;
	}

	public void setSitecode(String sitecode) {
		this.sitecode = sitecode;
	}

	private void getNppInfo() {
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.nppId = util.getIdNhapp(this.userId);
			this.nppTen = util.getTenNhaPP();
			this.sitecode = util.getSitecode();
		}
		else
		{
			this.nppId = this.npp_duocchon_id;
			this.nppTen = "";
			this.sitecode = "";
		}
	}

	public String getXuatcho() {

		return this.xuatcho;
	}

	public void setXuatcho(String xuatcho) {

		this.xuatcho = xuatcho;
	}

	public ResultSet getSoloOLD() {

		return this.soloOLD;
	}

	public void setSoloOLD(ResultSet soloOLD) {

		this.soloOLD = soloOLD;
	}

	public String getNgaygiaohanggui() {

		return this.ngaygiaohangGui;
	}

	public void setNgaygiaohanggui(String ngaygiaohanggui) {

		this.ngaygiaohangGui = ngaygiaohanggui;
	}

	public String getPhanloai() {
		return this.phanloai;
	}

	public void setPhanloai(String phanloai) {
		this.phanloai = phanloai;
	}

	public String getTinhthanhId() {

		return this.tinhthanhId;
	}

	public void setTinhthanhId(String tinhthanhId) {

		this.tinhthanhId = tinhthanhId;
	}

	public ResultSet getTinhthanhRs() {

		return this.tinhthanhRs;
	}

	public void setTinhthanhRs(ResultSet tinhthanhRs) {

		this.tinhthanhRs = tinhthanhRs;
	}

	public String getQuanhuyenId() {

		return this.quanhuyenId;
	}

	public void setQuanhuyenId(String quanhuyenId) {

		this.quanhuyenId = quanhuyenId;
	}

	public ResultSet getQuanhuyenRs() {

		return this.quanhuyenRs;
	}

	public void setQuanhuyenRs(ResultSet qunahuyenRs) {

		this.quanhuyenRs = qunahuyenRs;
	}

	public String getNVGNId() {

		return this.nvgnId;
	}

	public void setNVGNId(String nvgnId) {

		this.nvgnId = nvgnId;
	}

	public ResultSet getNVGNRs() {

		return this.nvgnRs;
	}

	public void setNVGNRs(ResultSet nvgnRs) {

		this.nvgnRs = nvgnRs;
	}

	public String getNVBHId() {

		return this.nvbhId;
	}

	public void setNVBHId(String nvbhId) {

		this.nvbhId = nvbhId;
	}

	public ResultSet getNVBHRs() {

		return this.nvbhRs;
	}

	public void setNVBHRs(ResultSet nvbhRs) {

		this.nvbhRs = nvbhRs;
	}

	public String getTungay() {

		return this.tungay;
	}

	public void setTungay(String tungay) {

		this.tungay = tungay;
	}

	public String getDenngay() {

		return this.denngay;
	}

	public void setDenngay(String denngay) {

		this.denngay = denngay;
	}

	public String getKyhieu() {

		return this.kyhieuhd;
	}

	public void setKyhieu(String kyhieu) {

		this.kyhieuhd = kyhieu;
	}

	public String getSohoadon() {

		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) {

		this.sohoadon = sohoadon;
	}
	
	public String getTdv_dangnhap_id() {
		
		return this.tdv_dangnhap_id;
	}

	
	public void setTdv_dangnhap_id(String tdv_dangnhap_id) {
		
		this.tdv_dangnhap_id = tdv_dangnhap_id;
	}
	
	public String getNpp_duocchon_id() {
		
		return this.npp_duocchon_id;
	}

	
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		
		this.npp_duocchon_id = npp_duocchon_id;
	}

}
