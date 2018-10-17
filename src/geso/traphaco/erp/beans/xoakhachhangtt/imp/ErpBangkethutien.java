package geso.traphaco.erp.beans.xoakhachhangtt.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.xoakhachhangtt.IHoadon;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.Hoadon;
import geso.traphaco.erp.beans.xoakhachhangtt.IHoadonct;
import geso.traphaco.erp.beans.xoakhachhangtt.imp.Hoadonct;
import geso.traphaco.erp.beans.xoakhachhangtt.IErpBangkethutien;
import geso.traphaco.center.util.Utility;

public class ErpBangkethutien implements IErpBangkethutien {
	String userId;
	String id;
	String ngaychungtu;
	String ngayghiso;

	String nppId;

	String htttId;
	ResultSet htttRs;
	String nganhangId;
	ResultSet nganhangRs;
	String chinhanhId;
	ResultSet chinhanhRs;

	String msg;
	dbutils db;

	String congtyId;

	String isNPP;

	ResultSet nvgnRs;
	String nvgnId;

	ResultSet ddkdRs;
	String ddkdId;

	ResultSet khuvucRs;
	String khuvucId;

	ResultSet quanhuyenRs;
	String quanhuyenId;

	String makhachhang;
	String nppdangnhap;
	
	String tungay;
	String denngay;

	ResultSet hoadonRs;
	Hashtable<String, Double> hoadon_tienAVAT;
	Hashtable<String, Double> hoadon_thanhtoan;

	String ghichu;
	String tongtientt;

	List<IHoadon> hoadonList;

	public ErpBangkethutien() {
		this.id = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.nganhangId = "";
		this.chinhanhId = "";

		this.msg = "";
		this.congtyId = "";

		this.db = new dbutils();

		this.isNPP = "";
		this.ghichu = "";

		this.nvgnId = "";
		this.ddkdId = "";
		this.khuvucId = "";
		this.hoadon_thanhtoan = new Hashtable<String, Double>();
		this.hoadon_tienAVAT = new Hashtable<String, Double>();
		this.hoadonList = new ArrayList<IHoadon>();
		this.makhachhang = "";
		this.tongtientt = "0";
		this.nppdangnhap = "";
		this.quanhuyenId = "";
		this.tungay = "";
		this.denngay = "";
	}

	public ErpBangkethutien(String id) {
		this.id = id;
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.msg = "";

		this.db = new dbutils();

		this.congtyId = "";

		this.isNPP = "";
		this.ghichu = "";

		this.nvgnId = "";
		this.ddkdId = "";
		this.khuvucId = "";
		this.hoadon_thanhtoan = new Hashtable<String, Double>();
		this.hoadon_tienAVAT = new Hashtable<String, Double>();
		this.makhachhang = "";

		this.tongtientt = "0";
		this.nppdangnhap = "";
		this.hoadonList = new ArrayList<IHoadon>();
		this.quanhuyenId = "";
		
		this.tungay = "";
		this.denngay = "";
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

	public String getNgaychungtu() {
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getNppId() {
		return this.nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getHtttId() {
		return this.htttId;
	}

	public void setHtttId(String htttId) {
		this.htttId = htttId;
	}

	public ResultSet getHtttRs() {
		return this.htttRs;
	}

	public void setHtttRs(ResultSet htttRs) {
		this.htttRs = htttRs;
	}

	public String getNganhangId() {
		return this.nganhangId;
	}

	public void setNganhangId(String nganhangId) {
		this.nganhangId = nganhangId;
	}

	public ResultSet getNganhangRs() {
		return this.nganhangRs;
	}

	public void setNganhangRs(ResultSet nganhangRs) {
		this.nganhangRs = nganhangRs;
	}

	public String getChinhanhId() {
		return this.chinhanhId;
	}

	public void setChinhanhId(String cnId) {
		this.chinhanhId = cnId;
	}

	public ResultSet getChinhanhRs() {
		return this.chinhanhRs;
	}

	public void setChinhanhRs(ResultSet chinhanhRs) {
		this.chinhanhRs = chinhanhRs;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public boolean createTTHD() {
		try {
			if (this.ngaychungtu.trim().length() <= 0) {
				this.msg = "Vui lòng chọn ngày chứng từ";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			String ddkd_fk = this.ddkdId.trim().length() <= 0 ? "NULL": this.ddkdId;
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL": this.nvgnId;
			String khuvuc_fk = this.khuvucId.trim().length() <= 0 ? "NULL": this.khuvucId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL": this.quanhuyenId;

			String query = "insert ERP_BANGKETHUTIEN(ngaychungtu, ngayghiso, trangthai, nvgn_fk, ddkd_fk, tinhthanh_fk, ghichu, npp_fk, congty_fk, makhachhang, ngaytao, nguoitao, ngaysua, nguoisua, tongtientt, quanhuyen_fk, HTTT_FK, tungay, denngay ) "
						+  "values(  '"+ this.ngaychungtu+ "', '"+ this.ngayghiso+ "', '0', "+ nvgn_fk+ ", "	+ ddkd_fk+ ", "	+ khuvuc_fk
						+  ", N'"+ this.ghichu+ "', '"+ this.nppId+ "', '"+ this.congtyId+ "', N'"+ this.makhachhang	+ "', '"+ this.getDateTime()
						+  "', '"+ this.userId+ "', '"+ this.getDateTime()+ "', '"+ this.userId+ "', "+ this.tongtientt.replaceAll(",", "")
						+  ", "	+ quanhuyen_fk+ ", "+this.htttId+", '"+this.tungay+"', '"+this.denngay+"') ";
			System.out.println("::: INSERT BANG KE: " + query);

			if (!db.update(query)) {
				this.msg = "Lỗi khi tạo mới: " + query;
				db.getConnection().rollback();
				return false;
			}

			String bangkeId = "";
			query = "select SCOPE_IDENTITY() as bangkeId";
			ResultSet rs1 = db.get(query);
			rs1.next();
			bangkeId = rs1.getString("bangkeId");
			rs1.close();

			double sum_tienck = 0;
			
			System.out.println("hoadonlist:" + this.hoadonList.size());
			for (int i = 0; i < this.hoadonList.size(); i++) {
				IHoadon hoadon = this.hoadonList.get(i);

				String avat = hoadon.getTiengocHD().replaceAll(",", "");
				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String ptck = hoadon.getptck().replaceAll(",", "");
				String tienck = hoadon.gettienck().replaceAll(",", "");
				String thucthu = hoadon.getthucthu().replaceAll(",", "");

				if (thanhtoan.length() > 0) {
					if (Double.parseDouble(thanhtoan) != 0) {
						
						sum_tienck += Double.parseDouble(tienck);
						
						double chenhlech = Double.parseDouble(avat)	- Double.parseDouble(thanhtoan);

						query = "Insert ERP_BANGKETHUTIEN_HOADON(BANGKE_FK, HOADON_FK, TONGTIENAVAT, THANHTOAN, PTCHIETKHAU,TIENCK, THUCTHU, KBH_FK, THOIHANTT, CHENHLECH, GHICHU, KHACHHANG_FK, ISNPP ) "
								+ "values('"+ bangkeId	+ "', '"+ hoadon.getId()+ "', '"+ avat	+ "', '"+ thanhtoan	+ "', '"
								+ ptck+ "', '"+ tienck+ "', "+ thucthu	+ ", "	+ hoadon.getKenhId()
								+ ", 0, "	+ chenhlech	+ ", N'"+ hoadon.getghichu() + "', "+hoadon.getKhId()+", "+hoadon.getisNPP()+")";
						System.out.println(query);

						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: "	+ query;
							db.getConnection().rollback();
							return false;
						}

						for (int j = 0; j < hoadon.getHoadonList().size(); j++) {
							IHoadonct hoadonct = hoadon.getHoadonList().get(j);

							double sotienthanhtoan = Double.parseDouble(hoadonct.getThanhtoan().replaceAll(",", ""));
							double thanhtienhd = Double.parseDouble(hoadonct.getThanhtienhd().replaceAll(",", ""));
							double sotienckhd = Double.parseDouble(hoadonct.getsotienckhd().replaceAll(",", ""));
							double isdathanhtoan = Double.parseDouble(hoadonct.getIsdathanhtoan().replaceAll(",", ""));
							double vathd = Double.parseDouble(hoadonct.getvat().replaceAll(",", ""));
							double dongiahd = Double.parseDouble(hoadonct.getDongiahd().replaceAll(",", ""));

							double sotienPHANBO = sotienthanhtoan;
							double soluongPHANBO = 0;

							if (sotienthanhtoan > thanhtienhd)
								sotienPHANBO = thanhtienhd;

							// PHÂN BỔ SỐ LƯỢNG

							if (isdathanhtoan != 0) {
								soluongPHANBO = (sotienPHANBO + sotienckhd)	/ (dongiahd * (1 + vathd / 100));
							} else
								soluongPHANBO = sotienPHANBO / (dongiahd * (1 + vathd / 100));

							query = " INSERT ERP_BANGKETHUTIEN_PHANBO (bangke_fk, hoadon_fk, sanpham_fk, thanhtoan, sotien, ptchietkhau, tienck, thucthu, dongia, soluongtt)"
									+ " values ('"+ bangkeId+ "', '"+ hoadonct.gethdId()+ "', "+ hoadonct.getspId()	+ ", "
									+ hoadonct.getThanhtoan().replaceAll(",","")+ ", "+ hoadonct.getThanhtienhd().replaceAll(",","")
									+ ", "+ hoadonct.getptchietkhau().replaceAll(",","")+ ""+ ", "+ hoadonct.getsotienck().replaceAll(",", "")
									+ ", "+ hoadonct.getThucthu().replaceAll(",", "")+ ", "	+ hoadonct.getDongiahd().replaceAll(",", "")
									+ ", "+ soluongPHANBO + ")";

							System.out.println(query);

							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_BANGKETHUTIEN_PHANBO: "+ query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}

						}

					}
				}

			}
			
			query = "UPDATE ERP_BANGKETHUTIEN SET tienck = "+sum_tienck +" where PK_SEQ = "+bangkeId;
			
			System.out.println(query);

			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_BANGKETHUTIEN: "+ query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			

			db.getConnection().commit();
		} catch (Exception e) {
			db.update("rollback");
			this.msg = "Lỗi tạo mới: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public boolean updateTTHD() {
		try {
			if (this.ngaychungtu.trim().length() <= 0) {
				this.msg = "Vui lòng chọn ngày chứng từ";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			String ddkd_fk = this.ddkdId.trim().length() <= 0 ? "NULL": this.ddkdId;
			String nvgn_fk = this.nvgnId.trim().length() <= 0 ? "NULL": this.nvgnId;
			String khuvuc_fk = this.khuvucId.trim().length() <= 0 ? "NULL": this.khuvucId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL": this.quanhuyenId;

			String query = 	" update ERP_BANGKETHUTIEN set ngaychungtu = '"	+ this.ngaychungtu + "', ngayghiso = '" + this.ngayghiso+ "', " +
							" nvgn_fk = " + nvgn_fk + ", ddkd_fk = " + ddkd_fk	+ ", tinhthanh_fk = " + khuvuc_fk + ", quanhuyen_fk = " + quanhuyen_fk + ", ghichu = N'" + this.ghichu	+ "', " +
							" makhachhang = N'" + this.makhachhang	+ "', tongtientt = " + this.tongtientt.replaceAll(",", "")+", HTTT_FK = "+this.htttId+ ", tungay = '"+this.tungay+"', denngay = '"+this.denngay+"' "+
							" where pk_seq = '" + this.id + "' ";
			
			System.out.println("::: UPDATE BANG KE: " + query);
			
			if (!db.update(query)) {
				this.msg = "Lỗi khi cập nhật: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_BANGKETHUTIEN_HOADON where bangke_fk = '"+ this.id + "' ";
			
			System.out.println("::: DELETE BANG KE: " + query);
			if (!db.update(query)) {
				this.msg = "Lỗi khi cập nhật: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_BANGKETHUTIEN_PHANBO where bangke_fk = '"+ this.id + "' ";
			
			System.out.println("::: DELETE BANG KE: " + query);
			if (!db.update(query)) {
				this.msg = "Lỗi khi cập nhật: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_BANGKETHUTIEN_PHANBO  where bangke_fk = '"+ this.id + "'";
			
			System.out.println("::: CAP NHAT TRANG THAI: " + query);
			if (!db.update(query)) {
				this.msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			double sum_tienck = 0;
			
			for (int i = 0; i < this.hoadonList.size(); i++) {
				IHoadon hoadon = this.hoadonList.get(i);

				String avat = hoadon.getTiengocHD().replaceAll(",", "");
				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String ptck = hoadon.getptck().replaceAll(",", "");
				String tienck = hoadon.gettienck().replaceAll(",", "");
				String thucthu = hoadon.getthucthu().replaceAll(",", "");

				if (thanhtoan.length() > 0) {
					if (Double.parseDouble(thanhtoan) != 0) {
						
						sum_tienck  += Double.parseDouble(tienck);
						
						double chenhlech = Double.parseDouble(avat)	- Double.parseDouble(thanhtoan);
						
						query = "Insert ERP_BANGKETHUTIEN_HOADON(BANGKE_FK, HOADON_FK, TONGTIENAVAT, THANHTOAN, PTCHIETKHAU,TIENCK, THUCTHU, KBH_FK, THOIHANTT, CHENHLECH, GHICHU, KHACHHANG_FK, ISNPP) "
								+ "values('"+ this.id+ "', '"+ hoadon.getId()+ "', '"+ avat	+ "', '"+ thanhtoan	+ "', '"+ ptck+ "', '"
								+ tienck+ "', "+ thucthu+ ", "+ hoadon.getKenhId()+ ", 0, "+ chenhlech+ ", N'"+ hoadon.getghichu() + "', "+hoadon.getKhId()+", "+hoadon.getisNPP()+")";
						System.out.println(query);

						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: "	+ query;
							db.getConnection().rollback();
							return false;
						}

						System.out.println("this.hoadonList.size(): "+ hoadon.getHoadonList().size());

						for (int j = 0; j < hoadon.getHoadonList().size(); j++) {
							IHoadonct hoadonct = hoadon.getHoadonList().get(j);

							double sotienthanhtoan = Double.parseDouble(hoadonct.getThanhtoan().replaceAll(",", ""));
							double thanhtienhd = Double.parseDouble(hoadonct.getThanhtienhd().replaceAll(",", ""));
							double sotienckhd = Double.parseDouble(hoadonct.getsotienckhd().replaceAll(",", ""));
							double isdathanhtoan = Double.parseDouble(hoadonct.getIsdathanhtoan().replaceAll(",", ""));
							double vathd = Double.parseDouble(hoadonct.getvat().replaceAll(",", ""));
							double dongiahd = Double.parseDouble(hoadonct.getDongiahd().replaceAll(",", ""));

							double sotienPHANBO = sotienthanhtoan;
							double soluongPHANBO = 0;

							if (sotienthanhtoan > thanhtienhd)
								sotienPHANBO = thanhtienhd;

							// PHÂN BỔ SỐ LƯỢNG

							if (isdathanhtoan != 0) {
								soluongPHANBO = (sotienPHANBO + sotienckhd)	/ (dongiahd * (1 + vathd / 100));
							} else
								soluongPHANBO = sotienPHANBO / (dongiahd * (1 + vathd / 100));

							query = " INSERT ERP_BANGKETHUTIEN_PHANBO (bangke_fk, hoadon_fk, sanpham_fk, thanhtoan, sotien, ptchietkhau, tienck, thucthu, dongia, soluongtt)"
									+ " values ('"+ this.id+ "', '"+ hoadonct.gethdId()+ "', "+ hoadonct.getspId()	+ ", "
									+ hoadonct.getThanhtoan().replaceAll(",","")+ ", "+ hoadonct.getThanhtienhd().replaceAll(",","")
									+ ", "+ hoadonct.getptchietkhau().replaceAll(",","")+ ""+ ", "+ hoadonct.getsotienck().replaceAll(",", "")
									+ ", "+ hoadonct.getThucthu().replaceAll(",", "")+ ", "	+ hoadonct.getDongiahd().replaceAll(",", "")
									+ ", "+ soluongPHANBO + ")";

							System.out.println(query);

							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_BANGKETHUTIEN_PHANBO: "+ query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}

						}

					}
				}

			}

			query = " UPDATE ERP_BANGKETHUTIEN SET TIENCK = "+sum_tienck+ " WHERE PK_SEQ = "+this.id;
			System.out.println(query);

			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_BANGKETHUTIEN_PHANBO: "+ query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
		} catch (Exception e) {
			db.update("rollback");
			this.msg = "Lỗi tạo mới: " + e.getMessage();
			e.printStackTrace();
			return false;
		}

		return true;
	}

	public void init() {
		this.getNppInfo();

		String query = "select ngaychungtu, ngayghiso, nvgn_fk, ddkd_fk, tinhthanh_fk, ghichu, makhachhang, isnull(tongtientt,0) tongtientt, quanhuyen_fk, httt_fk, isnull(tungay,'') tungay , isnull(denngay,'') denngay "
					+  " from ERP_BANGKETHUTIEN where pk_seq = '" + this.id + "'  ";
		ResultSet rs = db.get(query);
		System.out.println("::: INIT" + query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.nvgnId = rs.getString("nvgn_fk") == null ? "" : rs.getString("nvgn_fk").toString();
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk").toString();
					this.khuvucId = rs.getString("tinhthanh_fk") == null ? "": rs.getString("tinhthanh_fk").toString();
					this.ghichu = rs.getString("ghichu");
					this.makhachhang = rs.getString("makhachhang") == null ? "": rs.getString("makhachhang").toString();
					this.tongtientt = rs.getString("tongtientt");
					this.quanhuyenId = rs.getString("quanhuyen_fk") == null ? "": rs.getString("quanhuyen_fk").toString();
					this.htttId = rs.getString("httt_fk");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}

		}

		this.createRs();
	}

	public void initDisplay() {
		this.getNppInfo();

		String query = "select ngaychungtu, ngayghiso, nvgn_fk, ddkd_fk, khuvuc_fk, ghichu, makhachhang, isnull(tongtientt, 0) tongtientt, quanhuyen_fk, httt_fk "
					 + " from ERP_BANGKETHUTIEN where pk_seq = '" + this.id + "'  ";
		ResultSet rs = db.get(query);
		System.out.println("::: INIT" + query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.ngayghiso = rs.getString("ngayghiso");
					this.nvgnId = rs.getString("nvgn_fk") == null ? "" : rs.getString("nvgn_fk").toString();
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk").toString();
					this.khuvucId = rs.getString("khuvuc_fk") == null ? "" : rs.getString("khuvuc_fk").toString();
					this.ghichu = rs.getString("ghichu");
					this.makhachhang = rs.getString("makhachhang") == null ? ""	: rs.getString("makhachhang").toString();
					this.tongtientt = rs.getString("tongtientt");
					this.quanhuyenId = rs.getString("quanhuyen_fk") == null ? "" : rs.getString("quanhuyen_fk").toString();
					this.htttId = rs.getString("httt_fk");
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}

		}

		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' AND PK_SEQ IN ( 100000, 100001 ) ");
		
		Utility util = new Utility();
		
		query = "select pk_seq, ten from NHANVIENGIAONHAN a where trangthai = 1 and npp_fk = '"+ this.nppId + "' ";
		query += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.nvgnRs = db.get(query);

		query = "select PK_SEQ, TEN from DAIDIENKINHDOANH a where trangthai = 1 ";
				//"and PK_SEQ in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '"+ this.nppId + "' )";
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.ddkdRs = db.get(query);


		query = "select pk_seq, ten from tinhthanh where trangthai = 1  ";
		this.khuvucRs = db.get(query);

		if (this.khuvucId.trim().length() > 0) {

			this.quanhuyenRs = this.db.get("select ten , pk_seq  from quanhuyen where tinhthanh_fk='"+ this.khuvucId + "' order by ten");
			
			System.out.println("select ten , pk_seq  from quanhuyen where tinhthanh_fk='"+ this.khuvucId + "' order by ten");
		}

		query = " select a.PK_SEQ hdId, a.KBH_FK kbhId, isnull(isnull(b.TEN, c.TEN), d.TEN) as khTen, isnull(isnull(b.MA, c.MA), d.MA) as MAKH,  \n"+
				"   a.SOHOADON, a.NGAYXUATHD, \n"+
				"   case when a.KHACHHANG_FK is not null then isnull(b.thoihanno,0) \n"+
				"   when a.NPP_DAT_FK is not null then ISNULL(c.THOIHANNO,0) \n"+
				"   when a.nhanvien_fk is not null then ISNULL(d.thoihanno, 0) \n"+
				"   end as thoihanno, \n"+
				"   (a.tongtienavat - isnull(bk.dathanhtoan,0)) tongtienavat, \n"+
				"   f.ptchietkhau PTCKTHANHTOAN, \n"+
				"   f.thucthu, f.tienck, f.thanhtoan tienthanhtoan, e.TEN KBHTen, isnull(f.GHICHU, '') ghichuhd \n"+
				" from ERP_HOADONNPP a \n"+
				" inner join KENHBANHANG e on a.KBH_FK = e.PK_SEQ \n"+
				" left join KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ \n"+
				" left join NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ \n"+
				" left join ERP_NHANVIEN d on a.nhanvien_fk = d.PK_SEQ \n"+
				" inner join ERP_BANGKETHUTIEN_HOADON f on a.pk_seq = f.hoadon_fk \n"+
				" left join \n"+
				" ( \n"+
				"	SELECT  THU.HOADON_FK, SUM(ISNULL(THU.dathanhtoan,0)) dathanhtoan \n"+
				"	FROM ( \n"+
				
				// BẢNG KÊ THU TIỀN
				"			SELECT hoadon_fk, sum( THANHTOAN ) as dathanhtoan \n"+
				"			FROM ERP_BANGKETHUTIEN_HOADON A INNER JOIN ERP_BANGKETHUTIEN B ON A.BANGKE_FK = B.PK_SEQ \n"+
				"			WHERE bangke_fk != ("+ (this.id.length() <= 0 ? "0" : this.id)+ ") and B.TRANGTHAI NOT IN (2) \n"+
				"			GROUP BY hoadon_fk \n"+
				
				"   		UNION ALL \n"+

				// THU TIỀN KHÔNG THEO BẢNG KÊ
				"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
				"			FROM ERP_THUTIEN_HOADON TTHD \n"+
				"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
				"			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL \n"+
				"			GROUP BY HOADON_FK \n"+

				"   		UNION ALL \n"+
				
				// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
				"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
				"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
				"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
				"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0'  \n"+
				"			GROUP BY HOADON_FK \n"+
				
				"   		UNION ALL \n"+
				
				// BÙ TRỪ KHÁCH HÀNG
				"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN  \n"+
				"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
				"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 \n"+
				"			GROUP BY BT_KH.HOADON_FK \n"+
				"		) THU \n"+
				"	 GROUP BY  THU.HOADON_FK \n"+
				" ) bk on a.PK_SEQ = bk.hoadon_fk \n"+
				" where a.trangthai in ( 2, 4 ) and f.bangke_fk = "+ this.id	+ "";

		/*if (this.nvgnId.trim().length() > 0)
			query += " and b.pk_seq in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '"+ this.nvgnId + "' ) ";

		if (this.ddkdId.trim().length() > 0)
			query += " and b.pk_seq in ( select khachhang_fk from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '"+ this.ddkdId + "' ) ) ";

		if (this.khuvucId.trim().length() > 0)
			query += " and b.tinhthanh_fk = '" + this.khuvucId + "' ";

		if (this.quanhuyenId.trim().length() > 0)
			query += " and b.quanhuyen_fk = '" + this.quanhuyenId + "' ";

		if (this.makhachhang.trim().length() > 0)
			query += " and ( ( b.ten like N'%" + this.makhachhang + "%' or b.ma like N'%" + this.makhachhang+ "%' ) or ( c.ma like N'%" + this.makhachhang + "%' or c.ten like N'%" + this.makhachhang + "%' ) ) ";*/

		System.out.println(query);
		ResultSet rsHoadon = db.get(query);

		double sotientt = 0;
		NumberFormat formatter = new DecimalFormat("#,###,###.##");

		List<IHoadon> hdList = new ArrayList<IHoadon>();

		if (rsHoadon != null) {
			try {
				IHoadon hd = null;
				while (rsHoadon.next()) {
					String hdid = rsHoadon.getString("hdId");
					String kbhid = rsHoadon.getString("kbhId");
					String kbhTen = rsHoadon.getString("KBHTen");
					String khTen = rsHoadon.getString("khTen");
					String khMa = rsHoadon.getString("MAKH");
					String sohoadon = rsHoadon.getString("SOHOADON");
					String ngayhd = rsHoadon.getString("NGAYXUATHD");
					String thoihanno = rsHoadon.getString("thoihanno");
					String avat = "" + rsHoadon.getDouble("tongtienavat");
					String ptckthanhtoan = ""+ rsHoadon.getDouble("PTCKTHANHTOAN");
					String thucthu = "" + rsHoadon.getDouble("thucthu");
					String tienck = "" + rsHoadon.getDouble("tienck");
					String tienthanhtoan = ""	+ rsHoadon.getDouble("tienthanhtoan");

					String ghichuhd = rsHoadon.getString("ghichuhd");

					hd = new Hoadon();
					hd.setId(hdid);
					hd.setLoaihd("");
					hd.setKenhId(kbhid);
					hd.setKenhTen(kbhTen);
					hd.setTenkh(khTen);
					hd.setMaKH(khMa);
					hd.setSo(sohoadon);
					hd.setNgay(ngayhd);
					hd.setNgaydenhanTT(thoihanno);
					hd.setTiengocHD(avat);
					hd.setptck(ptckthanhtoan);
					hd.setThanhtoan(tienthanhtoan);
					hd.setthucthu(thucthu);
					hd.settienck(tienck);
					hd.setghichu(ghichuhd);

					// POPUP CHI TIẾT HÓA ĐƠN

					if (hdid.trim().length() > 0) {
						query = " SELECT HD.PK_SEQ hdId, HD.KBH_FK kbhId, \n"+
						"		 HDSP.DONGIA, ( ROUND( HDSP.TIENAVAT - HDSP.tientichluyCOVAT, 0) - isnull(BK_HD_DATHANHTOAN.THANHTOAN,0)) THANHTIENHD, \n"+
						"		 ISNULL(BK_HD.THANHTOAN,0) THANHTOAN, \n"+
						"		 ISNULL(BK_HD.thucthu,0) THUCTHU, SP.TEN AS MASP, SP.PK_SEQ SPID, \n"+
						"		 BK_HD.ptchietkhau, isnull(BK_HD.TIENCK,0) TIENCK, HDSP.VAT, HDSP.CHIETKHAU, isnull(BK_HD.THANHTOAN,0) isDATHANHTOAN  \n"+
						
						" FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK \n"+
						" INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ \n"+
						" LEFT JOIN KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
						" LEFT JOIN NHAPHANPHOI NPP ON HD.NPP_DAT_FK = NPP.PK_SEQ \n"+
						" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.NHANVIEN_FK \n"+
						" LEFT JOIN KHACHHANG_SANPHAMCK KH_CK ON HD.KHACHHANG_FK = KH_CK.KHACHHANG_FK AND HDSP.SANPHAM_FK = KH_CK.SANPHAM_FK and HD.KBH_FK = KH_CK.KENHBANHANG_FK \n"+
						" INNER JOIN \n"+
						" ( \n"+
						"	SELECT A.hoadon_fk, A.sanpham_fk, SUM(A.thanhtoan) THANHTOAN, SUM(A.THUCTHU) thucthu, \n"+
						"		   A.PTCHIETKHAU, SUM(A.TIENCK) TIENCK \n"+
						"	FROM ERP_BANGKETHUTIEN_PHANBO A INNER JOIN ERP_BANGKETHUTIEN B ON A.bangke_fk = B.pk_seq \n"+
						"	WHERE A.HOADON_FK = "+ hdid +" 	AND B.PK_SEQ = "+ this.id +	" \n"+
						"	GROUP BY A.hoadon_fk, A.sanpham_fk, A.PTCHIETKHAU \n"+
						"  ) BK_HD ON HDSP.HOADON_FK = BK_HD.hoadon_fk AND HDSP.SANPHAM_FK = BK_HD.sanpham_fk "+
						"  LEFT JOIN \n"+
						"  ( \n"+
						"	 SELECT  THU.HOADON_FK,THU.SANPHAM_FK, SUM(ISNULL(THU.THANHTOAN,0)) THANHTOAN \n"+
						"	 FROM ( \n"+
						
						// THU TIỀN THEO BẢNG KÊ
						"			SELECT A.hoadon_fk, A.sanpham_fk, SUM(A.thanhtoan) THANHTOAN \n"+
						"			FROM ERP_THUTIEN_HOADON_SP A INNER JOIN ERP_THUTIEN B ON A.THUTIEN_FK = B.PK_SEQ \n"+
						"			WHERE A.HOADON_FK = "+ hdid + " and B.BANGKE_FK != " + (this.id.length() <= 0 ? "0": this.id) +
						" 			AND B.TRANGTHAI NOT IN (2) AND B.BANGKE_FK IS NOT NULL \n"+
						"			GROUP BY A.hoadon_fk, A.sanpham_fk \n"+
						
						"   		UNION ALL \n"+
						
						// THU TIỀN KHÔNG THEO BẢNG KÊ
						"			SELECT TTHD.HOADON_FK,TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
						"			FROM ERP_THUTIEN_HOADON_SP TTHD \n"+
						"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
						"			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = 0 AND TT.BANGKE_FK IS NULL \n"+
						"			GROUP BY HOADON_FK, TTHD.SANPHAM_FK \n"+
						
						"   		UNION ALL \n"+
						
						// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
						"			SELECT TTHD.HOADON_FK,TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
						"			FROM ERP_XOAKHTRATRUOC_PHANBO_CHITIET TTHD \n"+
						"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XKHTT_FK = TT.PK_SEQ \n"+
						"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0'  \n"+
						"			GROUP BY TTHD.HOADON_FK, TTHD.SANPHAM_FK \n"+
						
						"   		UNION ALL \n"+
						
						// BÙ TRỪ KHÁCH HÀNG
						"			SELECT BT_KH.HOADON_FK,BT_KH.SANPHAM_FK, SUM(BT_KH.THANHTOAN) AS THANHTOAN  \n"+
						"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_PHANBO_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK \n "+
						"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 \n"+
						"			GROUP BY BT_KH.HOADON_FK, BT_KH.SANPHAM_FK \n"+										

						"		) THU \n"+
						"	 	GROUP BY  THU.HOADON_FK , THU.SANPHAM_FK \n"+
						" ) BK_HD_DATHANHTOAN ON HDSP.HOADON_FK = BK_HD_DATHANHTOAN.hoadon_fk AND HDSP.SANPHAM_FK = BK_HD_DATHANHTOAN.sanpham_fk "+
						
						" WHERE HD.PK_SEQ = " + hdid + " AND HDSP.SCHEME = ' ' ";

			}

					System.out.println(query);
					ResultSet rshdct = db.get(query);

					List<IHoadonct> HdctList = new ArrayList<IHoadonct>();
					while (rshdct.next()) {
						IHoadonct hdct = new Hoadonct();

						hdct.sethdId(rshdct.getString("hdId"));
						hdct.setspId(rshdct.getString("SPID"));
						hdct.setkbhId(rshdct.getString("kbhId"));
						hdct.setTensp(rshdct.getString("MASP"));
						// hdct.setSoluonghd(formatter.format(rshdct.getDouble("SOLUONGHD")));
						hdct.setDongiahd(formatter.format(rshdct.getDouble("DONGIA")));
						hdct.setThanhtienhd(formatter.format(rshdct.getDouble("THANHTIENHD")));
						// hdct.setSoluongtt(formatter.format(rshdct.getDouble("SOLUONGTT")));
						hdct.setThanhtoan(formatter.format(rshdct.getDouble("THANHTOAN")));
						hdct.setThucthu(formatter.format(rshdct.getDouble("THUCTHU")));
						hdct.setptchietkhau(formatter.format(rshdct.getDouble("ptchietkhau")));
						hdct.setsotienck(formatter.format(rshdct.getDouble("TIENCK")));

						HdctList.add(hdct);
					}

					hd.setHoadonList(HdctList);

					hdList.add(hd);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.hoadonList = hdList;
	}

	public void createRs() 
	{
		Utility util = new Utility();
		this.getNppInfo();

		String query = "select pk_seq, ten from NHANVIENGIAONHAN a where trangthai = 1 and npp_fk = '"+ this.nppId + "' ";
		query += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.nvgnRs = db.get(query);

		query = "select PK_SEQ, TEN from DAIDIENKINHDOANH a where trangthai = 1 ";
				//"and PK_SEQ in ( select ddkd_fk from DAIDIENKINHDOANH_NPP where npp_fk = '"+ this.nppId + "' )";
		query += util.getPhanQuyen_TheoNhanVien("DAIDIENKINHDOANH", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		this.ddkdRs = db.get(query);

		query = "select pk_seq, ten from tinhthanh where trangthai = 1  ";
		this.khuvucRs = db.get(query);
		
		this.htttRs = db.get("select pk_seq, ma, ten from ERP_HINHTHUCTHANHTOAN where trangthai = '1' AND PK_SEQ IN ( 100000, 100001 ) ");

		if (this.khuvucId != null || this.khuvucId != "") {
			this.quanhuyenRs = this.db.get("select ten , pk_seq  from quanhuyen where tinhthanh_fk='"+ this.khuvucId + "' order by ten");
		}
		
		System.out.println("LOAINHANVIEN:"+this.getLoainhanvien());
		String strQUYEN = util.getPhanQuyen_TheoNhanVien("KHACHHANG", "b", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		query = "";
		
		if (this.tungay.trim().length() > 0 || this.denngay.trim().length() > 0 || this.nvgnId.trim().length() > 0 || this.ddkdId.trim().length() > 0 || this.khuvucId.trim().length() > 0	|| this.makhachhang.trim().length() > 0) {
			
			if (this.id.trim().length() > 0) 
			{
				query = " SELECT a.PK_SEQ hdId, a.KBH_FK kbhId, isnull(isnull(b.TEN, c.TEN), d.TEN) as khTen, isnull(isnull(isnull(b.maFAST, c.maFAST), d.MA),'') as MAKH,  \n"+
						"   	 a.SOHOADON, a.NGAYXUATHD, \n"+
						"   	 case when a.KHACHHANG_FK is not null then isnull(b.thoihanno,0) \n"+
						"   	 when a.NPP_DAT_FK is not null then ISNULL(c.THOIHANNO,0) \n"+
						"   	 when a.nhanvien_fk is not null then ISNULL(d.thoihanno, 0) \n"+
						"   	 end as thoihanno, \n"+
						"  		 (a.tongtienavat - isnull(bk.dathanhtoan,0)) tongtienavat, \n"+
						"   	 f.ptchietkhau PTCKTHANHTOAN, \n"+
						"   	 f.thucthu, f.tienck, f.thanhtoan tienthanhtoan, e.TEN KBHTen, isnull(f.GHICHU, '') ghichuhd, \n"+
						"		 CASE WHEN a.KHACHHANG_FK IS NOT NULL THEN a.KHACHHANG_FK  \n"+
						"		 WHEN a.nhanvien_fk IS NOT NULL THEN a.nhanvien_fk \n"+
						"		 WHEN a.NPP_DAT_FK IS NOT NULL THEN a.NPP_DAT_FK END KHACHHANG_FK, \n"+
						"		 CASE WHEN a.KHACHHANG_FK IS NOT NULL THEN 0  \n"+
						"		 WHEN a.nhanvien_fk IS NOT NULL THEN 2 \n"+
						"		 WHEN a.NPP_DAT_FK IS NOT NULL THEN 1 END isNPP \n"+
						
						" FROM ERP_HOADONNPP a \n"+
						" INNER JOIN KENHBANHANG e on a.KBH_FK = e.PK_SEQ \n"+
						" LEFT JOIN KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ \n"+
						" LEFT JOIN NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ \n"+
						" LEFT JOIN ERP_NHANVIEN d on a.nhanvien_fk = d.PK_SEQ \n"+
						" INNER JOIN ERP_BANGKETHUTIEN_HOADON f on a.pk_seq = f.hoadon_fk \n"+
						" LEFT JOIN \n"+
						" ( \n"+
						"	SELECT  THU.HOADON_FK, SUM(ISNULL(THU.dathanhtoan,0)) dathanhtoan \n"+
						"	FROM ( \n"+
						
						// THU TIỀN THEO BẢNG KÊ
						"			SELECT hoadon_fk, sum( A.SOTIENTT ) as dathanhtoan \n"+
						"			FROM ERP_THUTIEN_HOADON A INNER JOIN ERP_THUTIEN B ON A.THUTIEN_FK = B.PK_SEQ \n"+
						"			WHERE B.bangke_fk != ("+ this.id+ ") and B.TRANGTHAI NOT IN (2) AND B.BANGKE_FK IS NOT NULL \n"+
						"			GROUP BY hoadon_fk \n"+
						
						"   		UNION ALL \n"+
												
						// THU TIỀN KHÔNG THEO BẢNG KÊ
						"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
						"			FROM ERP_THUTIEN_HOADON TTHD \n"+
						"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
						"			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL \n"+
						"			GROUP BY HOADON_FK \n"+
						
						"   		UNION ALL \n"+					

						// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
						"			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
						"			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
						"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
						"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0'  \n"+
						"			GROUP BY HOADON_FK \n"+
						
						"   		UNION ALL \n"+					

						// BÙ TRỪ KHÁCH HÀNG
						"			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN  \n"+
						"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
						"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 \n"+
						"			GROUP BY BT_KH.HOADON_FK \n"+
						"		) THU \n"+
						"	 GROUP BY  THU.HOADON_FK \n"+
						" ) bk on a.PK_SEQ = bk.hoadon_fk \n"+
						" WHERE a.trangthai in ( 2, 4 ) AND a.LOAIHOADON = '0' and f.bangke_fk = "+ this.id + " AND a.TONGTIENAVAT > 0 and a.CONGTY_FK = "+this.congtyId + " ";
				if(this.tungay.trim().length()>0)
					query+= " and A.NGAYXUATHD >= '"+this.tungay+"'";
				
				if(this.denngay.trim().length()>0)
					query+= " and A.NGAYXUATHD <= '"+this.denngay+"'";
				
				if (this.nvgnId.trim().length() > 0)
					query += " and b.pk_seq in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '"+ this.nvgnId + "' ) ";

				if (this.ddkdId.trim().length() > 0)
					query += " and b.pk_seq in ( select khachhang_fk from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '" + this.ddkdId + "' ) ) ";

				if (this.khuvucId.trim().length() > 0)
					query += " and b.tinhthanh_fk = '" + this.khuvucId + "' ";

				if (this.quanhuyenId.trim().length() > 0)
					query += " and b.quanhuyen_fk = '" + this.quanhuyenId + "' ";

				if (this.makhachhang.trim().length() > 0)
					query += " and ( ( b.ten like N'%" + this.makhachhang+ "%' or b.ma like N'%" + this.makhachhang+ "%' or b.mafast = N'%"+this.makhachhang+"%' ) or ( c.ma like N'%" + this.makhachhang
							+ "%' or c.ten like N'%" + this.makhachhang+ "%' or c.mafast like N'%"+this.makhachhang+"%' ) ) ";

				query += " \n UNION ALL \n";
			}
			
			query +=  " SELECT a.PK_SEQ hdId, a.KBH_FK kbhId, isnull(isnull(b.TEN, c.TEN), d.TEN) as khTen, isnull(isnull(isnull(b.maFAST, c.maFAST), d.MA),'') as MAKH,  \n"+
					  "   	 a.SOHOADON, a.NGAYXUATHD, \n"+
					  "   	 case when a.KHACHHANG_FK is not null then isnull(b.thoihanno,0) \n"+
					  "   	 when a.NPP_DAT_FK is not null then ISNULL(c.THOIHANNO,0) \n"+
					  "   	 when a.nhanvien_fk is not null then ISNULL(d.thoihanno, 0) \n"+
					  "   	 end as thoihanno, \n"+
					  "   	 a.tongtienavat - isnull(bk.dathanhtoan,0) tongtienavat, \n"+
					  "   	 case when a.KBH_FK != '100052' and ((datediff(day,a.NGAYXUATHD, '"+ this.ngayghiso + "') - 1) <= isnull(isnull(isnull(b.THOIHANNO, c.THOIHANNO), d.THOIHANNO),0)) \n"+
					  "		 then isnull(isnull(isnull(b.CKTHANHTOAN, c.CKTHANHTOAN), d.CKTHANHTOAN),0) \n"+
					  "   	 else 0 end PTCKTHANHTOAN, \n"+
					  "   	 0 thucthu, 0 tienck, 0 tienthanhtoan, e.TEN KBHTen, '' ghichuhd, \n"+
					  "		 CASE WHEN a.KHACHHANG_FK IS NOT NULL THEN a.KHACHHANG_FK  \n"+
					  "		 WHEN a.nhanvien_fk IS NOT NULL THEN a.nhanvien_fk \n"+
					  "		 WHEN a.NPP_DAT_FK IS NOT NULL THEN a.NPP_DAT_FK END KHACHHANG_FK, \n"+
					  "		 CASE WHEN a.KHACHHANG_FK IS NOT NULL THEN 0  \n"+
					  "		 WHEN a.nhanvien_fk IS NOT NULL THEN 2 \n"+
					  "		 WHEN a.NPP_DAT_FK IS NOT NULL THEN 1 END isNPP \n"+
					
					  " FROM ERP_HOADONNPP a \n"+
					  " INNER JOIN KENHBANHANG e on a.KBH_FK = e.PK_SEQ  \n"+
							//"AND a.KBH_FK in " + util.quyen_kenh( this.userId ) + " \n "
					  " LEFT JOIN KHACHHANG b on a.KHACHHANG_FK = b.PK_SEQ \n"+					
					  " LEFT JOIN NHAPHANPHOI c on a.NPP_DAT_FK = c.PK_SEQ \n"+
					  " LEFT JOIN ERP_NHANVIEN d on a.nhanvien_fk = d.PK_SEQ \n"+
					  " LEFT JOIN \n"+
					  " ( \n"+
					  "	SELECT  THU.HOADON_FK, SUM(ISNULL(THU.dathanhtoan,0)) dathanhtoan \n"+
					  "	FROM ( \n"+
					
					// THU TIỀN THEO BẢNG KÊ
					  "			SELECT hoadon_fk, sum( A.SOTIENTT) as dathanhtoan \n"+
					  "			FROM ERP_THUTIEN_HOADON A INNER JOIN ERP_THUTIEN B ON A.THUTIEN_FK = B.PK_SEQ \n"+
					  "			WHERE B.bangke_fk != ("+ (this.id.length() <= 0 ? "0" : this.id)+ ") and B.TRANGTHAI NOT IN (2) AND B.BANGKE_FK IS NOT NULL \n"+
					  "			GROUP BY hoadon_fk \n"+
					
					  "   		UNION ALL \n"+
					
					// THU TIỀN KHÔNG THEO BẢNG KÊ
					  "			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS dathanhtoan \n"+
					  "			FROM ERP_THUTIEN_HOADON TTHD \n"+
					  "			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
					  "			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = 0 AND isnull(TTHD.XOACHENHLECH,0) = 0 AND TT.BANGKE_FK IS NULL \n"+
					  "			GROUP BY HOADON_FK \n"+
					
					  "   		UNION ALL \n"+					

					// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
					  "			SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+
					  "			FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+
					  "			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+
					  "			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0'  \n"+
					  "			GROUP BY HOADON_FK \n"+
					
					  "   		UNION ALL \n"+
					
					// BÙ TRỪ KHÁCH HÀNG
					  "			SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS DATHANHTOAN  \n"+
					  "			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n "+
					  "			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 \n"+
					  "			GROUP BY BT_KH.HOADON_FK \n"+
					  "		) THU \n"+
					  "	 GROUP BY  THU.HOADON_FK \n"+
					
					  " ) bk on a.PK_SEQ = bk.hoadon_fk \n"+
					  " WHERE a.TRANGTHAI in ( 2, 4 ) and (a.tongtienavat - ISNULL(bk.dathanhtoan, 0)) >0  AND a.LOAIHOADON = '0' AND A.TONGTIENAVAT > 0 and a.CONGTY_FK = "+this.congtyId + " \n"+					
					  " AND a.PK_SEQ NOT IN ( SELECT tthd.HOADON_FK FROM ERP_THUTIEN tt INNER JOIN ERP_THUTIEN_HOADON tthd on tt.pk_seq = tthd.THUTIEN_FK  where tt.trangthai = 0 ) \n"+ // KHÔNG LẤY HÓA ĐƠN MÀ THU TIỀN LÀ TRẠNG THÁI CHƯA CHỐT					
					  " AND a.PK_SEQ NOT IN ( SELECT tthd.HOADON_FK FROM ERP_THUTIEN tt INNER JOIN ERP_THUTIEN_HOADON tthd on tt.pk_seq = tthd.THUTIEN_FK  where tt.trangthai != 2 and tthd.xoachenhlech = 1 ) \n"+ // KHÔNG LẤY HÓA ĐƠN ĐÃ BỊ XÓA CHÊNH LỆCH
					  " AND a.PK_SEQ NOT IN ( SELECT BKHD.HOADON_FK FROM ERP_BANGKETHUTIEN BK INNER JOIN ERP_BANGKETHUTIEN_HOADON BKHD ON BK.PK_SEQ = BKHD.BANGKE_FK WHERE BK.TRANGTHAI = 0 ) \n"; // KHÔNG LẤY HÓA ĐƠN TRONG BẢNG KÊ MÀ TRẠNG THÁI CHƯA CHỐT

			//query += " and ( ( a.khachhang_fk is not null " + strQUYEN + " ) or ( a.npp_dat_fk is not null and a.npp_dat_fk in ( select Npp_fk from PHAMVIHOATDONG where Nhanvien_fk = '" + this.userId + "' ) ) ) \n";
			
			//PHAN QUYEN THEO KENH
			//query += " and a.kbh_fk in " + util.quyen_kenh( this.userId ) + " ";
			
			if(this.tungay.trim().length()>0)
				query+= " and A.NGAYXUATHD >= '"+this.tungay+"'";
			
			if(this.denngay.trim().length()>0)
				query+= " and A.NGAYXUATHD <= '"+this.denngay+"'";

			if (this.id.trim().length() > 0)
				query += " and a.pk_seq not in (SELECT hoadon_fk FROM ERP_BANGKETHUTIEN_HOADON where bangke_fk = "+ this.id + " ) ";

			if (this.nvgnId.trim().length() > 0)
				query += " and b.pk_seq in ( select KHACHHANG_FK from NVGN_KH where NVGN_FK = '"+ this.nvgnId + "' ) ";

			if (this.ddkdId.trim().length() > 0)
				query += " and b.pk_seq in ( select khachhang_fk from KHACHHANG_TUYENBH where TBH_FK in ( select PK_SEQ from TUYENBANHANG where DDKD_FK = '"+ this.ddkdId + "' ) ) ";

			if (this.khuvucId.trim().length() > 0)
				query += " and b.tinhthanh_fk = '" + this.khuvucId + "' ";

			if (this.quanhuyenId.trim().length() > 0)
				query += " and b.quanhuyen_fk = '" + this.quanhuyenId + "' ";

			if (this.makhachhang.trim().length() > 0)
			{
				query += " and ( ( b.ten like N'%" + this.makhachhang+ "%' or b.ma like N'%" + this.makhachhang+ "%' or b.mafast = N'%"+this.makhachhang+"%' ) or ( c.ma like N'%" + this.makhachhang
					  + "%' or c.ten like N'%" + this.makhachhang+ "%' or c.mafast like N'%"+this.makhachhang+"%' ) ) ";				
			}

			query += "order by a.NGAYXUATHD asc ";

			System.out.println("INIT____  : " + query);
			ResultSet rsHoadon = db.get(query);

			double sotientt = 0;
			NumberFormat formatter = new DecimalFormat("#,###,###.##");

			List<IHoadon> hdList = new ArrayList<IHoadon>();

			if (rsHoadon != null) {
				try {
					IHoadon hd = null;
					while (rsHoadon.next()) {
						String hdid = rsHoadon.getString("hdId");
						String kbhid = rsHoadon.getString("kbhId");
						String kbhTen = rsHoadon.getString("KBHTen");
						String khTen = rsHoadon.getString("khTen");
						String khMa = rsHoadon.getString("MAKH");
						String sohoadon = rsHoadon.getString("SOHOADON");
						String ngayhd = rsHoadon.getString("NGAYXUATHD");
						String thoihanno = rsHoadon.getString("thoihanno");
						String avat = "" + rsHoadon.getDouble("tongtienavat");
						String ptckthanhtoan = ""+ rsHoadon.getDouble("PTCKTHANHTOAN");
						String thucthu = "" + rsHoadon.getDouble("thucthu");
						String tienck = "" + rsHoadon.getDouble("tienck");
						String tienthanhtoan = ""+ rsHoadon.getDouble("tienthanhtoan");
						String ghichuhd = rsHoadon.getString("ghichuhd");
						String khId = rsHoadon.getString("khachhang_fk");
						String isNPP = rsHoadon.getString("isNPP");

						hd = new Hoadon();
						hd.setId(hdid);
						hd.setLoaihd("0");
						hd.setKenhId(kbhid);
						hd.setKenhTen(kbhTen);
						hd.setTenkh(khTen);
						hd.setMaKH(khMa);
						hd.setSo(sohoadon);
						hd.setNgay(ngayhd);
						hd.setNgaydenhanTT(thoihanno);
						hd.setTiengocHD(avat);
						hd.setptck(ptckthanhtoan);
						hd.setThanhtoan(tienthanhtoan);
						hd.setthucthu(thucthu);
						hd.settienck(tienck);
						hd.setghichu(ghichuhd);
						hd.setKhId(khId);
						hd.setisNPP(isNPP);

						// POPUP CHI TIẾT HÓA ĐƠN

						if (hdid.trim().length() > 0) {
							query = "";
							if (this.id.trim().length() > 0) {
								query = " SELECT HD.PK_SEQ hdId, HD.KBH_FK kbhId, \n"+
										"		 HDSP.DONGIA, ( ROUND( HDSP.TIENAVAT - HDSP.tientichluyCOVAT, 0) - isnull(BK_HD_DATHANHTOAN.THANHTOAN,0)) THANHTIENHD, \n"+
										"		 ISNULL(BK_HD.THANHTOAN,0) THANHTOAN, \n"+
										"		 ISNULL(BK_HD.thucthu,0) THUCTHU, SP.TEN AS MASP, SP.PK_SEQ SPID, \n"+
										"		 BK_HD.ptchietkhau, isnull(BK_HD.TIENCK,0) TIENCK, HDSP.VAT, HDSP.CHIETKHAU, isnull(BK_HD.THANHTOAN,0) isDATHANHTOAN  \n"+
										
										" FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK \n"+
										" INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ \n"+
										" LEFT JOIN KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
										" LEFT JOIN NHAPHANPHOI NPP ON HD.NPP_DAT_FK = NPP.PK_SEQ \n"+
										" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.NHANVIEN_FK \n"+
										" LEFT JOIN KHACHHANG_SANPHAMCK KH_CK ON HD.KHACHHANG_FK = KH_CK.KHACHHANG_FK AND HDSP.SANPHAM_FK = KH_CK.SANPHAM_FK and HD.KBH_FK = KH_CK.KENHBANHANG_FK \n"+
										" INNER JOIN \n"+
										" ( \n"+
										"	SELECT A.hoadon_fk, A.sanpham_fk, SUM(A.thanhtoan) THANHTOAN, SUM(A.THUCTHU) thucthu, \n"+
										"		   A.PTCHIETKHAU, SUM(A.TIENCK) TIENCK \n"+
										"	FROM ERP_BANGKETHUTIEN_PHANBO A INNER JOIN ERP_BANGKETHUTIEN B ON A.bangke_fk = B.pk_seq \n"+
										"	WHERE A.HOADON_FK = "+ hdid +" 	AND B.PK_SEQ = "+ this.id +	" \n"+
										"	GROUP BY A.hoadon_fk, A.sanpham_fk, A.PTCHIETKHAU \n"+
										"  ) BK_HD ON HDSP.HOADON_FK = BK_HD.hoadon_fk AND HDSP.SANPHAM_FK = BK_HD.sanpham_fk "+
										"  LEFT JOIN \n"+
										"  ( \n"+
										"	 SELECT  THU.HOADON_FK,THU.SANPHAM_FK, SUM(ISNULL(THU.THANHTOAN,0)) THANHTOAN \n"+
										"	 FROM ( \n"+
										
										// THU TIỀN THEO BẢNG KÊ
										"			SELECT A.hoadon_fk, A.sanpham_fk, SUM(A.thanhtoan) THANHTOAN \n"+
										"			FROM ERP_THUTIEN_HOADON_SP A INNER JOIN ERP_THUTIEN B ON A.THUTIEN_FK = B.PK_SEQ \n"+
										"			WHERE A.HOADON_FK = "+ hdid + " and B.BANGKE_FK != " + (this.id.length() <= 0 ? "0": this.id) +
										" 			AND B.TRANGTHAI NOT IN (2) AND B.BANGKE_FK IS NOT NULL \n"+
										"			GROUP BY A.hoadon_fk, A.sanpham_fk \n"+
										
										"   		UNION ALL \n"+
										
										// THU TIỀN KHÔNG THEO BẢNG KÊ
										"			SELECT TTHD.HOADON_FK,TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
										"			FROM ERP_THUTIEN_HOADON_SP TTHD \n"+
										"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = 0 AND TT.BANGKE_FK IS NULL \n"+
										"			GROUP BY HOADON_FK, TTHD.SANPHAM_FK \n"+
										
										"   		UNION ALL \n"+
										
										// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
										"			SELECT TTHD.HOADON_FK,TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
										"			FROM ERP_XOAKHTRATRUOC_PHANBO_CHITIET TTHD \n"+
										"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XKHTT_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0'  \n"+
										"			GROUP BY TTHD.HOADON_FK, TTHD.SANPHAM_FK \n"+
										
										"   		UNION ALL \n"+
										
										// BÙ TRỪ KHÁCH HÀNG
										"			SELECT BT_KH.HOADON_FK,BT_KH.SANPHAM_FK, SUM(BT_KH.THANHTOAN) AS THANHTOAN  \n"+
										"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_PHANBO_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK \n "+
										"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 \n"+
										"			GROUP BY BT_KH.HOADON_FK, BT_KH.SANPHAM_FK \n"+										

										"		) THU \n"+
										"	 	GROUP BY  THU.HOADON_FK , THU.SANPHAM_FK \n"+
										" ) BK_HD_DATHANHTOAN ON HDSP.HOADON_FK = BK_HD_DATHANHTOAN.hoadon_fk AND HDSP.SANPHAM_FK = BK_HD_DATHANHTOAN.sanpham_fk "+
										
										" WHERE HD.PK_SEQ = " + hdid + " AND HDSP.SCHEME = ' ' ";

								query += " UNION ALL \n";
							}

							query += " select HD.PK_SEQ hdId, HD.KBH_FK kbhId, \n"+
									 "		  HDSP.DONGIA, ( ROUND( HDSP.TIENAVAT - HDSP.tientichluyCOVAT, 0) - isnull(BK_HD.THANHTOAN,0)) THANHTIENHD, \n"+
									 "		  0 THANHTOAN, \n"+									 
									 "		  0 THUCTHU, SP.TEN AS MASP, SP.PK_SEQ SPID, \n"+
									 "		  case when HD.KBH_FK != '100052'  and ( ( datediff(day,HD.NGAYXUATHD, '" + this.ngayghiso+ "') - 1 ) <= isnull(isnull(isnull(KH.THOIHANNO, NPP.THOIHANNO), NV.THOIHANNO),0)) \n"+
									 "		  then isnull(isnull(isnull(KH.CKTHANHTOAN, NPP.CKTHANHTOAN), NV.CKTHANHTOAN),0)  "+
									 "        else isnull(KH_CK.ptchietkhau,0) end ptchietkhau, 0 TIENCK, HDSP.VAT, HDSP.CHIETKHAU, isnull(BK_HD.THANHTOAN,0) isDATHANHTOAN    "+
									
									 " FROM ERP_HOADONNPP HD INNER JOIN ERP_HOADONNPP_SP HDSP ON HD.PK_SEQ = HDSP.HOADON_FK \n"+
									 " INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ \n"+
									 " LEFT JOIN KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
									 " LEFT JOIN NHAPHANPHOI NPP ON HD.NPP_DAT_FK = NPP.PK_SEQ \n"+
									 " LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = HD.NHANVIEN_FK \n"+
									 " LEFT JOIN KHACHHANG_SANPHAMCK KH_CK ON HD.KHACHHANG_FK = KH_CK.KHACHHANG_FK AND HDSP.SANPHAM_FK = KH_CK.SANPHAM_FK and HD.KBH_FK = KH_CK.KENHBANHANG_FK \n"+
									 " LEFT JOIN \n"+
									 " ( \n"+
									 "	 	SELECT  THU.HOADON_FK,THU.SANPHAM_FK ,SUM(ISNULL(THU.THANHTOAN,0)) THANHTOAN \n"+
									 "		FROM ( \n"+
									
									// THU TIỀN THEO BẢNG KÊ
									 "				SELECT A.hoadon_fk, A.sanpham_fk, SUM(A.thanhtoan) THANHTOAN \n"+
									 "				FROM ERP_THUTIEN_HOADON_SP A INNER JOIN ERP_THUTIEN B ON A.THUTIEN_FK = B.pk_seq \n"+
									 "				WHERE A.HOADON_FK = "+ hdid	+ " and B.BANGKE_FK != "+ (this.id.length() <= 0 ? "0" : this.id) +
									 " 				AND B.TRANGTHAI NOT IN (2) \n"+
									 "				GROUP BY A.hoadon_fk, A.sanpham_fk \n"+
									
									 "   			UNION ALL \n"+
									

									// THU TIỀN KHÔNG THEO BẢNG KÊ
									 "				SELECT TTHD.HOADON_FK, TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
									 "				FROM ERP_THUTIEN_HOADON_SP TTHD \n"+
									 "				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
									 "				WHERE TT.TRANGTHAI != 2 AND TTHD.LOAIHOADON = 0 AND TT.BANGKE_FK IS NULL \n"+
									 "				GROUP BY HOADON_FK, TTHD.SANPHAM_FK \n"+
									
									 "   			UNION ALL \n"+
									
									// TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
									 "				SELECT TTHD.HOADON_FK, TTHD.SANPHAM_FK ,SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
									 "				FROM ERP_XOAKHTRATRUOC_PHANBO_CHITIET TTHD \n"+
									 "				INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XKHTT_FK = TT.PK_SEQ \n"+
									 "				WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '0'  \n"+
									 "				GROUP BY HOADON_FK, TTHD.SANPHAM_FK \n"+
									
									 "   			UNION ALL \n"+									

									// BÙ TRỪ KHÁCH HÀNG
									 "				SELECT BT_KH.HOADON_FK,BT_KH.SANPHAM_FK, SUM(BT_KH.THANHTOAN) AS THANHTOAN  \n"+
									 "				FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_PHANBO_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK \n "+
									 "				WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0 \n"+
									 "				GROUP BY BT_KH.HOADON_FK, BT_KH.SANPHAM_FK \n"+
									
									 "			) THU \n"+
									 "	 	GROUP BY  THU.HOADON_FK, THU.SANPHAM_FK \n"+
									 "	 ) BK_HD ON HDSP.HOADON_FK = BK_HD.hoadon_fk AND HDSP.SANPHAM_FK = BK_HD.sanpham_fk \n"+
									 " WHERE HD.PK_SEQ = "	+ hdid +
									 " AND HDSP.SCHEME = ' ' AND HD.PK_SEQ NOT IN (SELECT hoadon_fk FROM ERP_BANGKETHUTIEN_PHANBO WHERE BANGKE_FK = "+ (this.id.length() <= 0 ? "0" : this.id)+
									 ") \n"+
									 " AND ( ROUND( HDSP.TIENAVAT - HDSP.tientichluyCOVAT, 0) - isnull(BK_HD.THANHTOAN,0)) > 0 \n";

							query += " ORDER BY SP.TEN ASC";
						}

						System.out.println(query);
						ResultSet rshdct = db.get(query);

						List<IHoadonct> HdctList = new ArrayList<IHoadonct>();
						while (rshdct.next()) {
							IHoadonct hdct = new Hoadonct();

							hdct.sethdId(rshdct.getString("hdId"));
							hdct.setspId(rshdct.getString("SPID"));
							hdct.setkbhId(rshdct.getString("kbhId"));
							hdct.setTensp(rshdct.getString("MASP"));
							// hdct.setSoluonghd(formatter.format(rshdct.getDouble("SOLUONGHD")));

							hdct.setThanhtienhd(formatter.format(rshdct.getDouble("THANHTIENHD")));
							// hdct.setSoluongtt(formatter.format(rshdct.getDouble("SOLUONGTT")));
							hdct.setThanhtoan(formatter.format(rshdct.getDouble("THANHTOAN")));
							hdct.setThucthu(formatter.format(rshdct.getDouble("THUCTHU")));
							hdct.setptchietkhau(formatter.format(rshdct.getDouble("ptchietkhau")));
							hdct.setsotienck(formatter.format(rshdct.getDouble("TIENCK")));

							// THÔNG TIN ĐỂ PHÂN BỔ
							hdct.setDongiahd(formatter.format(rshdct.getDouble("DONGIA")));
							hdct.setsotienckhd(formatter.format(rshdct.getDouble("CHIETKHAU")));
							hdct.setvat(formatter.format(rshdct.getDouble("VAT")));
							hdct.setIsdathanhtoan(rshdct.getString("isDATHANHTOAN"));

							HdctList.add(hdct);
						}
						rshdct.close();

						hd.setHoadonList(HdctList);

						hdList.add(hd);
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			this.hoadonList = hdList;

		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void DBclose() {

	}

	public String getNgayghiso() {
		return this.ngayghiso;
	}

	public void setNgayghiso(String ngayghiso) {
		this.ngayghiso = ngayghiso;
	}

	public boolean chotTTHD(String userId, String id) {
		try {
			db.getConnection().setAutoCommit(false);

			String query = " update ERP_BANGKETHUTIEN set trangthai = '1' where pk_seq = '"+ id + "'";
			 
			if (!db.update(query)) {
				this.msg = "Lỗi khi chốt: " + query;
				db.getConnection().rollback();
				return false;
			}

			// TỰ ĐỘNG TẠO PHIẾU THU TIỀN
			
			query = " Insert ERP_THUTIEN(KBH_FK, NGUOINOPTIEN, DIACHI, NHOMKHTT_FK, CHIETKHAUNT, CHIETKHAU, TIGIA, SOTIENTTNT, THUDUOCNT, PHINGANHANGNT, " +
					" THUDUOC, PHINGANHANG, CHENHLECH, NGAYCHUNGTU, NGAYGHISO, TRANGTHAI, "+
				 	" KHACHHANG_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT_FK, GHICHU, SOTIENTT, TIENTE_FK, SOCHUNGTU, NGAYTAO, "+
					" NGUOITAO, NGAYSUA, NGUOISUA, NCC_FK, NHANVIEN_FK, CTKEMTHEO, ISKTTDUYET,CONGTY_FK, BANGKE_FK, PREFIX, isNPP) "+
					
					" SELECT NULL, '', '', NULL, 0, TIENCK, 1, 0, 0, 0, tongtientt, 0, 0, '" +getDateTime()+"', '"+getDateTime()+"', 0, NULL, "+
					" HTTT_FK, NULL, NULL, '', 100004, '', (SELECT SUM(THANHTOAN) FROM ERP_BANGKETHUTIEN_HOADON WHERE bangke_fk = "+id+" ) , 100000, '', '"+getDateTime()+"', "+
					" NGUOITAO, '"+getDateTime()+"', NGUOISUA, NULL, NULL, '', 1, "+this.congtyId+", "+id+", "+
					" case HTTT_FK when 100000 then 'PT' else 'BC' end PREFIX, NULL "+
					" FROM ERP_BANGKETHUTIEN WHERE PK_SEQ = "+id;
				
			System.out.println(query);

			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_THUTIEN: " + query;
				System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			String tthdCurrent = "";
			query = "select IDENT_CURRENT('ERP_THUTIEN') as tthdId";

			ResultSet rsTthd = db.get(query);
			if (rsTthd.next()) {
				tthdCurrent = rsTthd.getString("tthdId");
				rsTthd.close();
			}
			
			query =  " Insert ERP_THUTIEN_HOADON ( THUTIEN_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, LOAIHOADON, KHACHHANG_FK, PTCHIETKHAU, TIENCK, THUCTHU, KBH_FK, TIENCHENHLECH, XOACHENHLECH, macp, isNPP ) \n"+
					 " SELECT " +tthdCurrent+", HOADON_FK, THANHTOAN, TONGTIENAVAT, 0, KHACHHANG_FK, PTCHIETKHAU, TIENCK, THUCTHU, KBH_FK, CHENHLECH, 0, NULL, isNPP \n"+
					 " FROM ERP_BANGKETHUTIEN_HOADON WHERE BANGKE_FK = "+id;
			
			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: " + query;
				System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
				db.getConnection().rollback();
				return false;
			} 
			
			query = " INSERT ERP_THUTIEN_HOADON_SP (thutien_fk, hoadon_fk, sanpham_fk, thanhtoan, ptchietkhau, tienck, thucthu, loaihoadon, tienavat) \n"+
					" SELECT " +tthdCurrent+", hoadon_fk, sanpham_fk, thanhtoan, ptchietkhau, tienck, thucthu, 0, sotien "+
					" FROM ERP_BANGKETHUTIEN_PHANBO WHERE BANGKE_FK = "+id;
			
			if (!db.update(query)) {
				this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: " + query;
				System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
				db.getConnection().rollback();
				return false;
			} 
			
			
			// cập nhật mã chứng từ
			query = " update ERP_THUTIEN set machungtu = PREFIX + SUBSTRING(NGAYGHISO, 6, 2) + SUBSTRING(NGAYGHISO, 0, 5) + '-' + dbo.LaySoChungTu( " + tthdCurrent + " ) " + 
					" where pk_seq = '" + tthdCurrent + "' ";
			
			System.out.println("[ErpThutien.createTTHD] error message:" + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THUTIEN: " + query;
				System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

			return true;
		} catch (Exception e) {
			e.printStackTrace();

			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			db.shutDown();
			return false;
		}

	}

	public String getCongtyId() {

		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {

		this.congtyId = congtyId;
	}

	private void getNppInfo() {
		// Phien ban moi
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.userId);
	}

	public String getIsNPP() {

		return this.isNPP;
	}

	public void setIsNPP(String isNPP) {

		this.isNPP = isNPP;
	}

	public String getNvgnId() {

		return this.nvgnId;
	}

	public void setNvgnId(String nvgnId) {

		this.nvgnId = nvgnId;
	}

	public ResultSet getNvgnRs() {

		return this.nvgnRs;
	}

	public void setNvgnRs(ResultSet nvgnRs) {

		this.nvgnRs = nvgnRs;
	}

	public String getDdkdId() {

		return this.ddkdId;
	}

	public void setDdkdId(String ddkdId) {

		this.ddkdId = ddkdId;
	}

	public ResultSet getDdkdRs() {

		return this.ddkdRs;
	}

	public void setDdkdRs(ResultSet ddkdRs) {

		this.ddkdRs = ddkdRs;
	}

	public String getKhuvucId() {

		return this.khuvucId;
	}

	public void setKhuvucId(String kvId) {

		this.khuvucId = kvId;
	}

	public ResultSet getKhuvucRs() {

		return this.khuvucRs;
	}

	public void setKhuvucRs(ResultSet kvRs) {

		this.khuvucRs = kvRs;
	}

	public String getMakhachhang() {

		return this.makhachhang;
	}

	public void setMakhachhang(String makhachhang) {

		this.makhachhang = makhachhang;
	}

	public Hashtable<String, Double> getThanhtoan() {

		return this.hoadon_thanhtoan;
	}

	public void setThanhtoan(Hashtable<String, Double> thanhtoan) {

		this.hoadon_thanhtoan = thanhtoan;
	}

	public String getGhichu() {

		return this.ghichu;
	}

	public void setGhichu(String ghichu) {

		this.ghichu = ghichu;
	}

	public Hashtable<String, Double> getTienAVAT() {

		return this.hoadon_tienAVAT;
	}

	public void setTienAVAT(Hashtable<String, Double> tienAVAT) {

		this.hoadon_tienAVAT = tienAVAT;
	}

	public List<IHoadon> getHoadonRs() {
		return this.hoadonList;
	}

	public void setHoadonRs(List<IHoadon> hoadonRs) {
		this.hoadonList = hoadonRs;

	}

	public String gettongtientt() {

		return this.tongtientt;
	}

	public void settongtientt(String tongtientt) {

		this.tongtientt = tongtientt;
	}

	public String getnppdangnhap() {

		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) {

		this.nppdangnhap = nppdangnhap;
	}

	public String getQuanhuyenId() {

		return this.quanhuyenId;
	}

	public void setQuanhuyenId(String quanhuyenId) {

		this.quanhuyenId = quanhuyenId;
	}

	public ResultSet getQuanhuyenRs() {

		return quanhuyenRs;
	}

	public void setQuanhuyenRs(ResultSet QuanhuyenRs) {

		this.quanhuyenRs = QuanhuyenRs;
	}

	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
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


	
}
