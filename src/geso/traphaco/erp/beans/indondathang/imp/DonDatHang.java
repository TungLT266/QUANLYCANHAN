package geso.traphaco.erp.beans.indondathang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.indondathang.IDonDatHang;

public class DonDatHang implements IDonDatHang {
	String congTyId;
	String userId;
	String id;
	String congTyDatHang;
	String donViDatHang;
	String diaChi;
	String dienThoai;
	String fax;
	String maSoThue;
	String loai;
	Long tongCong;
	String hinhThucThanhToan;
	String tongCongBangChu;
	ResultSet thongTinDatHang;
	String tienTe;
	dbutils db;
	
	String diachincc="";
	String dienthoaincc="";
	String masothuencc="";
	String faxncc="";
	String sotaikhoanncc="";
	String nganhangncc="";
	
	String sotaikhoan="";
	String nganhang="";

	public String getDiachincc() {
		return diachincc;
	}

	public String getSotaikhoanncc() {
		return sotaikhoanncc;
	}

	public void setSotaikhoanncc(String sotaikhoanncc) {
		this.sotaikhoanncc = sotaikhoanncc;
	}

	public String getNganhangncc() {
		return nganhangncc;
	}

	public void setNganhangncc(String nganhangncc) {
		this.nganhangncc = nganhangncc;
	}

	public String getSotaikhoan() {
		return sotaikhoan;
	}

	public void setSotaikhoan(String sotaikhoan) {
		this.sotaikhoan = sotaikhoan;
	}

	public String getNganhang() {
		return nganhang;
	}

	public void setNganhang(String nganhang) {
		this.nganhang = nganhang;
	}

	public void setDiachincc(String diachincc) {
		this.diachincc = diachincc;
	}

	public String getDienthoaincc() {
		return dienthoaincc;
	}

	public void setDienthoaincc(String dienthoaincc) {
		this.dienthoaincc = dienthoaincc;
	}

	public String getMasothuencc() {
		return masothuencc;
	}

	public void setMasothuencc(String masothuencc) {
		this.masothuencc = masothuencc;
	}

	public String getFaxncc() {
		return faxncc;
	}

	public void setFaxncc(String faxncc) {
		this.faxncc = faxncc;
	}

	public DonDatHang() {
		this.congTyId = "";
		this.userId = "";
		this.id = "";
		this.congTyDatHang = "";
		this.donViDatHang = "";
		this.diaChi = "";
		this.dienThoai = "";
		this.fax = "";
		this.maSoThue = "";
		this.tongCong = (long) 0;
		this.loai ="";
		this.db = new dbutils();
	}

	public DonDatHang(String id) {
		this.congTyId = "";
		this.userId = "";
		this.id = id;
		this.congTyDatHang = "";
		this.donViDatHang = "";
		this.diaChi = "";
		this.dienThoai = "";
		this.fax = "";
		this.maSoThue = "";
		this.tongCong = (long) 0;
		this.loai="";
		this.db = new dbutils();
	}

	@Override
	public String getCongTyId() {
		// TODO Auto-generated method stub
		return this.congTyId;
	}

	@Override
	public void setCongTyId(String congTyId) {
		// TODO Auto-generated method stub
		this.congTyId = congTyId;
	}

	@Override
	public String getUserId() {
		// TODO Auto-generated method stub
		return this.userId;
	}

	@Override
	public void setUserId(String userId) {
		// TODO Auto-generated method stub
		this.userId = userId;
	}

	@Override
	public String getId() {
		// TODO Auto-generated method stub
		return this.id;
	}

	@Override
	public void setId(String id) {
		// TODO Auto-generated method stub
		this.id = id;
	}

	@Override
	public String getCongTyDatHang() {
		// TODO Auto-generated method stub
		return this.congTyDatHang;
	}

	@Override
	public void setCongTyDatHang(String congTyDatHang) {
		// TODO Auto-generated method stub
		this.congTyDatHang = congTyDatHang;
	}

	@Override
	public String getDonViDatHang() {
		// TODO Auto-generated method stub
		return this.donViDatHang;
	}

	@Override
	public void setDonViDatHang(String donViDatHang) {
		// TODO Auto-generated method stub
		this.donViDatHang = donViDatHang;
	}

	@Override
	public String getDiaChi() {
		// TODO Auto-generated method stub
		return this.diaChi;
	}

	@Override
	public void setDiaChi(String diaChi) {
		// TODO Auto-generated method stub
		this.diaChi = diaChi;
	}

	@Override
	public String getDienThoai() {
		// TODO Auto-generated method stub
		return this.dienThoai;
	}

	@Override
	public void setDienThoai(String dienThoai) {
		// TODO Auto-generated method stub
		this.dienThoai = dienThoai;
	}

	@Override
	public String getFax() {
		// TODO Auto-generated method stub
		return this.fax;
	}

	@Override
	public void setFax(String fax) {
		// TODO Auto-generated method stub
		this.fax = fax;
	}

	@Override
	public String getMaSoThue() {
		// TODO Auto-generated method stub
		return this.maSoThue;
	}

	@Override
	public void setMaSoThue(String maSoThue) {
		// TODO Auto-generated method stub
		this.maSoThue = maSoThue;
	}

	@Override
	public long getTongCong() {
		// TODO Auto-generated method stub
		return this.tongCong;
	}

	@Override
	public void setTongCong(long tongCong) {
		// TODO Auto-generated method stub
		this.tongCong = tongCong;
	}

	@Override
	public String getHinhThucThanhToan() {
		// TODO Auto-generated method stub
		return this.hinhThucThanhToan;
	}

	@Override
	public void setHinhThucThanhToan(String hinhThucThanhToan) {
		// TODO Auto-generated method stub
		this.hinhThucThanhToan = hinhThucThanhToan;
	}

	@Override
	public ResultSet getThongTinDatHang() {
		// TODO Auto-generated method stub
		return thongTinDatHang;
	}

	@Override
	public void createThongTinDatHang() {
		// TODO Auto-generated method stub
		String sql = "SELECT NULL AS TCKT,ISNULL(B.PK_SEQ, 0) AS SPID, isnull(b.ma,'') as SPMA,   ISNULL(B.TEN, '') AS SPTEN,"
				+ " ISNULL(B.QUYCACH, '') AS QUYCACH,ISNULL((SELECT DONVI FROM DONVIDOLUONG WHERE PK_SEQ=A.DONVI), '') AS DONVI,A.SOLUONG,ISNULL(A.DONGIA, '0') AS DONGIA,"
				+ " ISNULL(A.THUEXUAT, '0') AS VAT,"
				+ " ISNULL(A.THANHTIEN, '0') + ISNULL(A.THANHTIEN, '0') * ISNULL(A.THUEXUAT, '0') / 100 AS THANHTIEN,"
				+ " CASE"
				+ " WHEN A.NGAYNHAN = NULL OR A.NGAYNHAN = ''"
				+ " THEN '' "
				+ " ELSE CONVERT(VARCHAR(10),RIGHT(A.NGAYNHAN,2)+ '/' +SUBSTRING(A.NGAYNHAN,6,2) +'/'+LEFT(A.NGAYNHAN,4))"
				+ " END AS THOIGIANGIAOHANG,"
				+ " ISNULL(MH.DIADIEMGIAOHANG,'') AS DIADIEMGIAOHANG"
				/*--'----------' AS PHANCHIA
				--ISNULL(TSCD.PK_SEQ, 0) AS TSCDID,
				--ISNULL(TSCD.MA, '') AS TSCDMA,
				--ISNULL(A.DIENGIAI, TSCD.DIENGIAI) AS TSCDTEN,
				--ISNULL(NTS.MA, 'NA') AS NSTNH,
				--ISNULL(CCDC.PK_SEQ, 0) AS CCDCID,
				--ISNULL(CCDC.MA, '') AS CCDCMA,
				--ISNULL(A.DIENGIAI, CCDC.DIENGIAI) AS CCDCTEN,
				--ISNULL(NCP.PK_SEQ, 0) AS NCPID,
				--ISNULL(NCP.TEN, '') AS NCPMA,
				--ISNULL(A.DIENGIAI, NCP.DIENGIAI) AS NCPTEN,
				--ISNULL(TTCP.DIENGIAI, 'NA') AS NCPNH,
				--ISNULL(A.SOLUONG_NEW, '0') AS SOLUONG_NEW,
				--ISNULL(A.DONGIA_NEW, '0') AS DONGIA_NEW,
				--A.VAT,
				--ISNULL(A.PHANTRAMTHUE, '0') AS PHANTRAMTHUE,
				--ISNULL(A.THUENHAPKHAU, '0') AS THUENHAPKHAU,
				--A.KHOTT_FK,
				--DUNGSAI,
				--ISNULL(MUANGUYENLIEUDUKIEN_FK, 0) AS MNLID,
				--ISNULL(A.TENHD, '') AS TENHD*/
				+ " FROM ( SELECT AVG (PK_SEQ) AS PK_SEQ,MUAHANG_FK,SANPHAM_FK,CHIPHI_FK,TAISAN_FK,DIENGIAI,"
				+ " SUM (SOLUONG) AS SOLUONG,DONGIA,SUM (SOLUONG_NEW) AS SOLUONG_NEW,DONGIA_NEW,TIENTE_FK,"
				+ " SUM (THANHTIEN) AS THANHTIEN,PHANTRAMTHUE,THUENHAPKHAU,DONGIAVIET,SUM (THANHTIENVIET) AS THANHTIENVIET,"
				+ " DONVI,KHOTT_FK,DUNGSAI,MUANGUYENLIEUDUKIEN_FK,CCDC_FK,TENHD,HOADONNCC_FK,THUEXUAT,VAT,IDMARQUETTE,"
				+ " NGAYNHAN FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id + "' GROUP BY MUAHANG_FK,SANPHAM_FK,"
				+ " CHIPHI_FK,TAISAN_FK,DIENGIAI,DONGIA,DONGIA_NEW,TIENTE_FK,PHANTRAMTHUE,THUENHAPKHAU,DONGIAVIET,"
				+ " DONVI,KHOTT_FK,DUNGSAI,MUANGUYENLIEUDUKIEN_FK,CCDC_FK,TENHD,HOADONNCC_FK,THUEXUAT,VAT,"
				+ " IDMARQUETTE,NGAYNHAN) A" + " LEFT JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ"
				+ " LEFT JOIN ERP_TAISANCODINH TSCD ON A.TAISAN_FK = TSCD.PK_SEQ"
				+ " LEFT JOIN ERP_NHOMTAISAN NTS ON TSCD.NHOMTAISAN_FK = NTS.PK_SEQ"
				+ " LEFT JOIN ERP_CONGCUDUNGCU CCDC ON A.CCDC_FK = CCDC.PK_SEQ"
				+ " LEFT JOIN MARQUETTE M ON M.SANPHAM_FK = B.PK_SEQ" + " AND M.PK_SEQ = A.IDMARQUETTE"
				+ " LEFT JOIN ERP_NHOMCHIPHI NCP ON A.CHIPHI_FK = NCP.PK_SEQ"
				+ " LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON NCP.TTCHIPHI_FK = TTCP.PK_SEQ"
				+ " LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ = A.MUAHANG_FK" + " WHERE MUAHANG_FK = '" + this.id
				+ "' ORDER BY A.PK_SEQ ASC";
		if (this.loai.equals("VATTU")) {
			sql = "SELECT NULL AS TCKT,ISNULL(B.PK_SEQ, 0) AS SPID,ISNULL(A.DIENGIAI, B.TEN ) AS SPTEN,"
					+ " ISNULL(B.QUYCACH, '') AS QUYCACH,ISNULL(A.DONVI, '') AS DONVI,A.SOLUONG,ISNULL(A.DONGIA, '0') AS DONGIA,"
					+ " ISNULL(A.THUEXUAT, '0') AS VAT,"
					+ " ISNULL(A.THANHTIEN, '0') + ISNULL(A.THANHTIEN, '0') * ISNULL(A.THUEXUAT, '0') / 100 AS THANHTIEN,"
					+ " CASE"
					+ " WHEN A.NGAYNHAN = NULL OR A.NGAYNHAN = ''"
					+ " THEN '' "
					+ " ELSE CONVERT(VARCHAR(10),RIGHT(A.NGAYNHAN,2)+ '/' +SUBSTRING(A.NGAYNHAN,6,2) +'/'+LEFT(A.NGAYNHAN,4))"
					+ " END AS THOIGIANGIAOHANG,"
					+ " ISNULL(MH.DIADIEMGIAOHANG,'') AS DIADIEMGIAOHANG"
					/*--'----------' AS PHANCHIA
					--ISNULL(TSCD.PK_SEQ, 0) AS TSCDID,
					--ISNULL(TSCD.MA, '') AS TSCDMA,
					--ISNULL(A.DIENGIAI, TSCD.DIENGIAI) AS TSCDTEN,
					--ISNULL(NTS.MA, 'NA') AS NSTNH,
					--ISNULL(CCDC.PK_SEQ, 0) AS CCDCID,
					--ISNULL(CCDC.MA, '') AS CCDCMA,
					--ISNULL(A.DIENGIAI, CCDC.DIENGIAI) AS CCDCTEN,
					--ISNULL(NCP.PK_SEQ, 0) AS NCPID,
					--ISNULL(NCP.TEN, '') AS NCPMA,
					--ISNULL(A.DIENGIAI, NCP.DIENGIAI) AS NCPTEN,
					--ISNULL(TTCP.DIENGIAI, 'NA') AS NCPNH,
					--ISNULL(A.SOLUONG_NEW, '0') AS SOLUONG_NEW,
					--ISNULL(A.DONGIA_NEW, '0') AS DONGIA_NEW,
					--A.VAT,
					--ISNULL(A.PHANTRAMTHUE, '0') AS PHANTRAMTHUE,
					--ISNULL(A.THUENHAPKHAU, '0') AS THUENHAPKHAU,
					--A.KHOTT_FK,
					--DUNGSAI,
					--ISNULL(MUANGUYENLIEUDUKIEN_FK, 0) AS MNLID,
					--ISNULL(A.TENHD, '') AS TENHD*/
					+ " FROM ( SELECT AVG (PK_SEQ) AS PK_SEQ,MUAHANG_FK,SANPHAM_FK,CHIPHI_FK,TAISAN_FK,DIENGIAI,"
					+ " SUM (SOLUONG) AS SOLUONG,DONGIA,SUM (SOLUONG_NEW) AS SOLUONG_NEW,DONGIA_NEW,TIENTE_FK,"
					+ " SUM (THANHTIEN) AS THANHTIEN,PHANTRAMTHUE,THUENHAPKHAU,DONGIAVIET,SUM (THANHTIENVIET) AS THANHTIENVIET,"
					+ " DONVI,KHOTT_FK,DUNGSAI,MUANGUYENLIEUDUKIEN_FK,CCDC_FK,TENHD,HOADONNCC_FK,THUEXUAT,VAT,IDMARQUETTE,"
					+ " NGAYNHAN FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id
					+ "' GROUP BY MUAHANG_FK,SANPHAM_FK,"
					+ " CHIPHI_FK,TAISAN_FK,DIENGIAI,DONGIA,DONGIA_NEW,TIENTE_FK,PHANTRAMTHUE,THUENHAPKHAU,DONGIAVIET,"
					+ " DONVI,KHOTT_FK,DUNGSAI,MUANGUYENLIEUDUKIEN_FK,CCDC_FK,TENHD,HOADONNCC_FK,THUEXUAT,VAT,"
					+ " IDMARQUETTE,NGAYNHAN) A" + " LEFT JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ"
					+ " LEFT JOIN ERP_TAISANCODINH TSCD ON A.TAISAN_FK = TSCD.PK_SEQ"
					+ " LEFT JOIN ERP_NHOMTAISAN NTS ON TSCD.NHOMTAISAN_FK = NTS.PK_SEQ"
					+ " LEFT JOIN ERP_CONGCUDUNGCU CCDC ON A.CCDC_FK = CCDC.PK_SEQ"
					+ " LEFT JOIN MARQUETTE M ON M.SANPHAM_FK = B.PK_SEQ" + " AND M.PK_SEQ = A.IDMARQUETTE"
					+ " LEFT JOIN ERP_NHOMCHIPHI NCP ON A.CHIPHI_FK = NCP.PK_SEQ"
					+ " LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON NCP.TTCHIPHI_FK = TTCP.PK_SEQ"
					+ " LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ = A.MUAHANG_FK" + " WHERE MUAHANG_FK = '" + this.id
					+ "' ORDER BY A.PK_SEQ ASC";
		}
		System.out.println("Câu lấy thông tin sản phẩm đặt hàng : " + sql);
		setThongTinDatHang(this.db.get(sql));
	}

	@Override
	public void init() {
		// TODO Auto-generated method stub
		String sqlLayCongTyDatHang = "SELECT NCC.TEN , isnull( ncc.DIACHI,'' ) as diachi , isnull (ncc.DIENTHOAI,'') as dienthoai , "+
		"\n isnull(ncc.sotaikhoan,'') as sotaikhoan,isnull ( (select ten from ERP_NGANHANG where pk_seq=ncc.nganhang_fk ), '') as nganhang," +
		"\n isnull( ncc.MASOTHUE,'') as masothue , isnull ( ncc.FAX ,'') as fax  FROM ERP_MUAHANG MH"
				+ " INNER JOIN ERP_NHACUNGCAP NCC ON MH.NHACUNGCAP_FK = NCC.PK_SEQ" + " WHERE MH.PK_SEQ = " + this.id;
		ResultSet rsCongTyDatHang = db.get(sqlLayCongTyDatHang);
		try {
			if (rsCongTyDatHang.next()) {
				this.congTyDatHang = rsCongTyDatHang.getString("TEN");
				
				this.diachincc = rsCongTyDatHang.getString("diachi");
				this.dienthoaincc = rsCongTyDatHang.getString("dienthoai");
				this.masothuencc = rsCongTyDatHang.getString("masothue");
				this.faxncc = rsCongTyDatHang.getString("fax");
				this.nganhangncc=rsCongTyDatHang.getString("nganhang");
				this.sotaikhoanncc=rsCongTyDatHang.getString("sotaikhoan");
				
			}
			rsCongTyDatHang.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String sqlLayThongTinDonViDatHang = "SELECT TEN,DIACHI,MASOTHUE,DIENTHOAI,FAX, isnull(sotaikhoan,'') as sotaikhoan,isnull ( (select ten from ERP_NGANHANG where pk_seq=nganhang_fk ), '') as nganhang "
				+ " FROM ERP_CONGTY WHERE PK_SEQ = " + this.congTyId;
		ResultSet rsThongTinDonViDatHang = db.get(sqlLayThongTinDonViDatHang);
		try {
			if (rsThongTinDonViDatHang.next()) {
				this.donViDatHang = rsThongTinDonViDatHang.getString("TEN");
				this.diaChi = rsThongTinDonViDatHang.getString("DIACHI");
				this.dienThoai = rsThongTinDonViDatHang.getString("DIENTHOAI");
				this.maSoThue = rsThongTinDonViDatHang.getString("MASOTHUE");
				this.fax = rsThongTinDonViDatHang.getString("FAX");
				this.nganhang=rsThongTinDonViDatHang.getString("nganhang");
				this.sotaikhoan=rsThongTinDonViDatHang.getString("sotaikhoan");
				
				
			}
			rsThongTinDonViDatHang.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String sqlTongCong = "SELECT SUM(SOLUONG* DONGIA + SOLUONG* DONGIA* THUEXUAT/100) AS TONGCONG FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = "
				+ this.id;
		ResultSet rsTongCong = db.get(sqlTongCong);
		try {
			if (rsTongCong.next()) {
				this.tongCong = rsTongCong.getLong("TONGCONG");
			}
			rsTongCong.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		String sqlHinhThucThanhToan = "SELECT HTTT FROM ERP_MUAHANG WHERE PK_SEQ = " + this.id;
		ResultSet rsHinhThucThanhToan = db.get(sqlHinhThucThanhToan);
		try {
			if (rsHinhThucThanhToan.next()) {
				this.hinhThucThanhToan = rsHinhThucThanhToan.getString("HTTT");
			}
			rsHinhThucThanhToan.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		String sqlTienTe = "select TT.MA FROM ERP_MUAHANG MH" + " INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = MH.TIENTE_FK "
				+ "WHERE MH.PK_SEQ = " + this.id;
		ResultSet rsTienTe = db.get(sqlTienTe);
		try {
			if (rsTienTe.next()) {
				this.tienTe = rsTienTe.getString("MA");
			}
			rsTienTe.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		createThongTinDatHang();

		doctienrachu doctien = new doctienrachu();
		String docTienRaChu = doctien.docSo(this.tongCong);
		docTienRaChu = docTienRaChu + " (" + this.tienTe + ")";
		this.tongCongBangChu = docTienRaChu;

	}

	@Override
	public String getTongCongBangChu() {
		// TODO Auto-generated method stub
		return this.tongCongBangChu;
	}

	@Override
	public void setTongCongBangChu(String tongCongBangChu) {
		// TODO Auto-generated method stub
		this.tongCongBangChu = tongCongBangChu;
	}

	@Override
	public void setThongTinDatHang(ResultSet thongTinDatHang) {
		// TODO Auto-generated method stub
		this.thongTinDatHang = thongTinDatHang;
	}

	@Override
	public String getTienTe() {
		// TODO Auto-generated method stub
		return this.tienTe;
	}

	@Override
	public void setTienTe(String tienTe) {
		// TODO Auto-generated method stub
		this.tienTe = tienTe;
	}

	@Override
	public String getLoai() {
		// TODO Auto-generated method stub
		return this.loai;
	}

	@Override
	public void setLoai(String loai) {
		// TODO Auto-generated method stub
		this.loai = loai;
	}
}

class doctienrachu {
	// Trả về tên gọi của một số ở hàng đơn vị, hàng trăm.
	private String numberToTextA(int number) {
		String sR = "";
		switch (number) {
		case 0:
			sR = "không";
			break;
		case 1:
			sR = "một";
			break;
		case 2:
			sR = "hai";
			break;
		case 3:
			sR = "ba";
			break;
		case 4:
			sR = "bốn";
			break;
		case 5:
			sR = "năm";
			break;
		case 6:
			sR = "sáu";
			break;
		case 7:
			sR = "bảy";
			break;
		case 8:
			sR = "tám";
			break;
		case 9:
			sR = "chín";
			break;
		default:
			sR = "";
		}
		return sR;
	}

	private String ChuyenDV(String Number) {
		String sNumber = "";
		int len = Number.length();
		if (len == 1) {
			int iNu = Integer.parseInt("" + Number.charAt(0));
			sNumber += numberToTextA(iNu);
		} else if (len == 2) {
			int iChuc = Integer.parseInt("" + Number.charAt(0));
			int iDV = Integer.parseInt("" + Number.charAt(1));
			if (iChuc == 1) {
				if (iDV > 0) {
					sNumber += "Mười " + numberToTextA(iDV);
				} else {
					sNumber += "Mười ";
				}
			} else {
				sNumber += numberToTextA(iChuc) + " mươi " + numberToTextA(iDV);
			}
		} else {
			int iTram = Integer.parseInt("" + Number.charAt(0));
			int iChuc = Integer.parseInt("" + Number.charAt(1));
			int iDV = Integer.parseInt("" + Number.charAt(2));

			if (iChuc == 0) {
				if (iDV > 0) {
					sNumber += numberToTextA(iTram) + " trăm linh " + numberToTextA(iDV);
				} else {
					sNumber += numberToTextA(iTram) + " trăm";
				}
			} else if (iChuc == 1) {
				if (iDV > 0) {
					sNumber += numberToTextA(iTram) + " trăm mười " + numberToTextA(iDV);
				} else {
					sNumber += numberToTextA(iTram) + " trăm mười ";
				}
			} else {
				if (iDV > 0) {
					sNumber += numberToTextA(iTram) + " trăm " + numberToTextA(iChuc) + " mươi " + numberToTextA(iDV);
				} else {
					sNumber += numberToTextA(iTram) + " trăm " + numberToTextA(iChuc) + " mươi ";
				}
			}
		}
		return sNumber;
	}

	// Phương thức chuyển đổi
	public String tranlate(String sNumber) {

		String sR = "";
		String sR1 = "";
		String sR2 = "";
		String sR3 = "";
		String sR4 = "";
		// sR = ChuyenDV(sNumber);

		int seq = 0;
		int k = 1;
		for (int i = sNumber.length(); i >= 0; i--) {
			if (seq == 3) {
				String subStr = sNumber.substring(i, i + seq);
				if (k == 1) {
					sR = ChuyenDV(subStr) + " đồng";
				} else if (k == 2) {
					sR1 = ChuyenDV(subStr) + " nghìn ";
				} else if (k == 3) {
					sR2 = ChuyenDV(subStr) + " triệu ";
				} else {
					sR3 = ChuyenDV(subStr) + " tỷ ";
				}
				seq = 0;
				k++;
			}
			seq++;
		}
		if (seq > 1) {
			String subStr = sNumber.substring(0, seq - 1);
			if (k == 1) {
				sR = ChuyenDV(subStr) + " đồng";
			} else if (k == 2) {
				sR1 = ChuyenDV(subStr) + " nghìn ";
			} else if (k == 3) {
				sR2 = ChuyenDV(subStr) + " triệu ";
			} else {
				sR3 = ChuyenDV(subStr) + " tỷ ";
			}
		}
		// seq
		sR4 = sR3 + sR2 + sR1 + sR;

		return sR4;
	}

	// Tên gọi của các chữ số
	static private String[] chuSo = { "không", "một", "hai", "ba", "bốn", "năm", "sáu", "bảy", "tám", "chín" };
	// Tên gọi đơn vị của các nhóm số (tính từ phải sang trái)
	static private String[] donViNhom = { "", "nghìn", "triệu ", "tỉ" };

	static public String docTien(long soTienCanDoc) {
		String bangChu = ""; // chứa kết quả đọc số

		// duyệt từng nhóm số (mỗi nhóm 3 chữ số)
		for (int i = 0; soTienCanDoc > 0; i++) {
			// tách lấy 3 chữ số cuối
			int hangDonVi = (int) (soTienCanDoc % 10);
			int hangChuc = (int) ((soTienCanDoc / 10) % 10);
			int hangTram = (int) ((soTienCanDoc / 100) % 10);

			// đọc hàng đơn vị
			String nhomDocLa = chuSo[hangDonVi]; // đọc số 1 chữ số

			// đọc hàng chục nếu có
			if (soTienCanDoc > 9) {
				nhomDocLa = chuSo[hangChuc] + " mươi " + nhomDocLa; // đọc số 2
																	// chữ số
				// hiệu chỉnh kết quả đọc số có 2 chữ số
				nhomDocLa = nhomDocLa.replace("không mươi không", "").replace("không mươi", "lẻ")
						.replace("mươi không", "mươi").replace("mươi một", "mươi mốt").replace("mươi năm", "mươi lăm")
						.replace("một mươi", "mười").replace("mười mốt", "mười một").replace("lẻ mốt", "lẻ một")
						.replace("lẻ lăm", "lẻ năm");
			}

			// đọc hàng trăm nếu có
			if (soTienCanDoc > 99) {
				nhomDocLa = chuSo[hangTram] + " trăm " + nhomDocLa; // đọc số 3
																	// chữ số

				// hiệu chỉnh kết quả đọc số có 3 chữ số
				if (nhomDocLa.trim().equals("không trăm")) {
					nhomDocLa = "";
				}
			}

			// hiệu chỉnh và bổ sung đơn vị nhóm
			i = (i == 4) ? 1 : i;
			nhomDocLa = nhomDocLa + " " + donViNhom[i];

			// bổ sung đọc nhóm vào kết quả
			bangChu = nhomDocLa + " " + bangChu;

			// phần còn lại (loại 3 số cuối) của số cần đọc
			soTienCanDoc = soTienCanDoc / 1000;
		}

		// hiệu chỉnh kết quả đọc được lần cuối
		bangChu = bangChu.replaceAll("\\s+", " ").replaceAll("tỉ triệu nghìn", "tỉ").replaceAll("tỉ triệu", "tỉ")
				.replaceAll("triệu nghìn", "triệu");

		if (bangChu.trim().length() == 0) {
			bangChu = "không ";
		}
		bangChu = bangChu.substring(0, 1).toUpperCase() + bangChu.substring(1, bangChu.length());

		return bangChu + "đồng";
	}

	static public String docSo(long soTienCanDoc) {
		String bangChu = ""; // chứa kết quả đọc số

		// duyệt từng nhóm số (mỗi nhóm 3 chữ số)
		for (int i = 0; soTienCanDoc > 0; i++) {
			// tách lấy 3 chữ số cuối
			int hangDonVi = (int) (soTienCanDoc % 10);
			int hangChuc = (int) ((soTienCanDoc / 10) % 10);
			int hangTram = (int) ((soTienCanDoc / 100) % 10);

			// đọc hàng đơn vị
			String nhomDocLa = chuSo[hangDonVi]; // đọc số 1 chữ số

			// đọc hàng chục nếu có
			if (soTienCanDoc > 9) {
				nhomDocLa = chuSo[hangChuc] + " mươi " + nhomDocLa; // đọc số 2
																	// chữ số
				// hiệu chỉnh kết quả đọc số có 2 chữ số
				nhomDocLa = nhomDocLa.replace("không mươi không", "").replace("không mươi", "lẻ")
						.replace("mươi không", "mươi").replace("mươi một", "mươi mốt").replace("mươi năm", "mươi lăm")
						.replace("một mươi", "mười").replace("mười mốt", "mười một").replace("lẻ mốt", "lẻ một")
						.replace("lẻ lăm", "lẻ năm");
			}

			// đọc hàng trăm nếu có
			if (soTienCanDoc > 99) {
				nhomDocLa = chuSo[hangTram] + " trăm " + nhomDocLa; // đọc số 3
																	// chữ số

				// hiệu chỉnh kết quả đọc số có 3 chữ số
				if (nhomDocLa.trim().equals("không trăm")) {
					nhomDocLa = "";
				}
			}

			// hiệu chỉnh và bổ sung đơn vị nhóm
			i = (i == 4) ? 1 : i;
			nhomDocLa = nhomDocLa + " " + donViNhom[i];

			// bổ sung đọc nhóm vào kết quả
			bangChu = nhomDocLa + " " + bangChu;

			// phần còn lại (loại 3 số cuối) của số cần đọc
			soTienCanDoc = soTienCanDoc / 1000;
		}

		// hiệu chỉnh kết quả đọc được lần cuối
		bangChu = bangChu.replaceAll("\\s+", " ").replaceAll("tỉ triệu nghìn", "tỉ").replaceAll("tỉ triệu", "tỉ")
				.replaceAll("triệu nghìn", "triệu");

		if (bangChu.trim().length() == 0) {
			bangChu = "không ";
		}
		bangChu = bangChu.substring(0, 1).toUpperCase() + bangChu.substring(1, bangChu.length());

		return bangChu;
	}

}
