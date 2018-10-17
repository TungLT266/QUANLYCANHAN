package geso.traphaco.center.beans.hopdong.imp;

import geso.traphaco.center.beans.hopdong.IErpHopdong;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpHopdong implements IErpHopdong {
	String userId;
	String id;

	String ma;
	String tungay;
	String denngay;
	String ghichu;

	String msg;
	String trangthai;

	String loaidonhang;
	String chietkhau;
	String vat;

	String khoNhanId;
	ResultSet khoNhanRs;

	String nppId;
	ResultSet nppRs;

	String dvkdId;
	ResultSet dvkdRs;

	String kbhId;
	ResultSet kbhRs;

	String gsbhId;
	ResultSet gsbhRs;

	String ddkdId;
	ResultSet ddkdRs;

	ResultSet dvtRs;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spVAT;
	String[] spTungay;
	String[] spDenngay;
	String[] spTrongluong;
	String[] spThetich;
	String[] spQuyDoi;

	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;

	dbutils db;

	public ErpHopdong() {
		this.id = "";
		this.ma = "";
		this.tungay = "";
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";

		this.dhCk_diengiai = new String[] { "", "", "", "" };
		this.dhCk_giatri = new String[] { "0", "0", "0", "0" };
		this.dhCk_loai = new String[] { "0", "0", "0", "0" };

		this.db = new dbutils();
	}

	public ErpHopdong(String id) {
		this.id = id;
		this.ma = "";
		this.tungay = "";
		this.denngay = "";
		this.ghichu = "";
		this.khoNhanId = "";
		this.nppId = "";
		this.msg = "";
		this.loaidonhang = "0";
		this.trangthai = "0";
		this.chietkhau = "0";
		this.vat = "0";
		this.dvkdId = "";
		this.kbhId = "";
		this.ddkdId = "";
		this.gsbhId = "";

		this.dhCk_diengiai = new String[] { "", "", "", "" };
		this.dhCk_giatri = new String[] { "0", "0", "0", "0" };
		this.dhCk_loai = new String[] { "0", "0", "0", "0" };

		this.db = new dbutils();
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

	public boolean createNK() {
		if(this.ma.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập mã hợp đồng";
			return false;
		}
		
		if(this.tungay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày bắt đầu hợp đồng";
			return false;
		}
		
		if(this.denngay.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày kết thúc hợp đồng";
			return false;
		}

		if( this.gsbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn Giám sát";
			return false;
		}
		
		if( this.ddkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 && !this.loaidonhang.equals("3") )
		{
			this.msg = "Vui lòng chọn khách hàng ETC";
			return false;
		}
		else
		{
			/*if(this.loaidonhang.equals("3") && this.khApdungId.trim().length() <= 0 )
			{
				this.msg = "Vui lòng chọn khách hàng ETC áp dụng";
				return false;
			}*/
		}
		
		//NEU PHU LUC CUA HOP DONG NGUYEN TAC THI KHONG CAN NHAP SO LUONG
		String loaihopdongPL = "";
		if(this.loaidonhang.equals("1"))
		{
			if(this.hopdongId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn phụ lục của hợp đồng";
				return false;
			}
			else
			{
				String query = "select loaidonhang from ERP_HOPDONGNPP where pk_seq = '" + this.hopdongId + "'";
				ResultSet rsLOAI = db.get(query);
				if(rsLOAI != null)
				{
					try 
					{
						if(rsLOAI.next())
						{
							loaihopdongPL = rsLOAI.getString("loaidonhang");
						}
						rsLOAI.close();
					} 
					catch (Exception e) {}
				}
			}
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm hợp đồng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(this.loaidonhang.equals("1"))  //HỢP ĐỒNG PHỤ LỤC CỦA HỢP ĐỒNG NGUYÊN TẮC THÌ KHÔNG CẦN NHẬP SỐ LƯỢNG
					{
						if(spSoluong[i].trim().length() <= 0 && !loaihopdongPL.equals("2") )
						{
							this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					else
					{
						if(spSoluong[i].trim().length() <= 0 && !( this.loaidonhang.equals("2") || this.loaidonhang.equals("3") ) )  //Hợp đồng nguyên tắc thì không cần nhập số lượng
						{
							this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					
					if(spGianhap[i].trim().length() <= 0 || spGianhap[i].trim().equals("0") )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					/*if(spTungay[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập từ ngày giao của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spDenngay[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập đến ngày giao của sản phẩm ( " + spTen[i] + " )";
						return false;
					}*/
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			

			// CHECK TRUNG MA
			for (int i = 0; i < spMa.length - 1; i++) {
				for (int j = i + 1; j < spMa.length; j++) {
					if (spMa[i].trim().length() > 0
							&& spMa[j].trim().length() > 0) {
						if (spMa[i].trim().equals(spMa[j].trim())) {
							this.msg = "Sản phẩm ( " + spTen[i]
									+ " )  đã bị trùng.";
							return false;
						}
					}
				}
			}
		}
		try 
		{
			
			db.getConnection().setAutoCommit(false);
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			String hopdong_fk = this.hopdongId.trim().length() <= 0 ? "null" : this.hopdongId.trim();
			
			String query = 
			" insert ERP_HopDong(mahopdong, tungay, denngay, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua,HOPDONG_FK) "+ 
			" values(N'"+ this.ma+ "', '"+ this.tungay+ "', '"+ this.denngay+ "', '"+ this.loaidonhang+ "', N'"+ this.ghichu+ "', '0', '"+ dvkdId+ "', '"+ kbhId+ "', '"+ this.gsbhId+ "', '"+ this.ddkdId+ "', '"+ nppId+ "', "+ khonhan_fk+ ", '"+ chietkhau+ "', '"+ vat+ "', '"+ getDateTime()+ "', '"+ this.userId+ "', '"+ getDateTime() + "', '" + this.userId + "',"+hopdong_fk+" )";
			System.out.println("1.Insert HOPDONG: " + query);
			if (!db.update(query)) {
				this.msg = "Không thể tạo mới ERP_HopDong " + query;
				db.getConnection().rollback();
				return false;
			}

			// LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_HopDong') as ID ");
			if (rsDDH.next()) {
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();

			System.out.println("HOPDONG ID: " + this.id);

			for (int i = 0; i < spMa.length; i++) 
			{
				if (spMa[i].trim().length() > 0&& spSoluong[i].trim().length() > 0&& spGianhap[i].trim().length() > 0) 
				{
					String ck = "0";
					if (spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = spVAT[i].trim().replaceAll(",", "");
					if (thueVAT.trim().length() < 0)
						thueVAT = "0";

					query = "insert ERP_HOPDONG_SANPHAM( hopdong_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) "+ 
							"select '"+ this.id+ "', pk_seq, '"+ spSoluong[i].replaceAll(",", "")+ "', '"+ spGianhap[i].replaceAll(",", "")+ "', '"+ ck+ "', '"+ thueVAT+ "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'"+ spDonvi[i].trim()+ "' ), DVDL_FK ), '"+ spTungay[i].trim()+ "', '"+ spDenngay[i].trim()+ "' "+ "from SANPHAM where MA = '"+ spMa[i].trim() + "' ";
					System.out.println("1.Insert HD - SP: " + query);
					if (!db.update(query)) 
					{
						this.msg = "Khong the tao moi ERP_HOPDONG_SANPHAM: "
								+ query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			// INSERT CHIET KHAU BO SUNG
			if (this.dhCk_diengiai != null) 
			{
				for (int i = 0; i < this.dhCk_diengiai.length; i++) 
				{
					if (this.dhCk_giatri[i].trim().length() > 0) 
					{
						query = "insert ERP_HOPDONG_CHIETKHAU(HOPDONG_FK, DIENGIAI, GIATRI, LOAI) "+ 
								"values( '"+ this.id+ "', N'"+ this.dhCk_diengiai[i].trim()+ "', '"+ this.dhCk_giatri[i].replaceAll(",", "")+ "', '" + this.dhCk_loai[i] + "' ) ";
						System.out.println("1.Insert HD - CK: " + query);
						if (!db.update(query)) 
						{
							this.msg = "Khong the tao moi ERP_HOPDONG_CHIETKHAU: "+ query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			//HOP DONG CHUNG AP DUNG CHO NHUGN ETC NAO
			if(loaidonhang.equals("3") && this.khApdungId.trim().length() > 0)
			{
				query = "Insert ERP_HOPDONGNPP_APDUNG(hopdong_fk, khachhang_fk) " +
						"select '" + this.id + "', pk_seq from KHACHHANG where NPP_FK = '" + this.nppId + "' and pk_seq in ( " + this.khApdungId + " ) ";
				System.out.println("1.Insert HD - AP: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_HOPDONGNPP_APDUNG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
	}

	public boolean updateNK(String checkKM) 
	{
		if (this.ma.trim().length() <= 0) {
			this.msg = "Vui lòng nhập mã hợp đồng";
			return false;
		}

		if (this.tungay.trim().length() < 10) {
			this.msg = "Vui lòng nhập ngày bắt đầu hợp đồng";
			return false;
		}

		if (this.denngay.trim().length() < 10) {
			this.msg = "Vui lòng nhập ngày kết thúc hợp đồng";
			return false;
		}

		if (this.gsbhId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn Giám sát";
			return false;
		}

		if (this.ddkdId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn trình dược viên";
			return false;
		}

		if (this.nppId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn khách hàng ETC";
			return false;
		}

		if (this.khoNhanId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}

		if (spMa == null) {
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		} else {
			boolean coSP = false;
			for (int i = 0; i < spMa.length; i++) 
			{
				if (spMa[i].trim().length() > 0) 
				{
					if (spSoluong[i].trim().length() <= 0) 
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( "
								+ spTen[i] + " )";
						return false;
					}

					if (spGianhap[i].trim().length() <= 0) 
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( "+ spTen[i] + " )";
						return false;
					}

					if (!this.loaidonhang.equals("4")) 
					{
						if (spDonvi[i].trim().length() <= 0) 
						{
							this.msg = "Bạn phải nhập đơn vị của sản phẩm ( "+ spTen[i] + " )";
							return false;
						}
					}

					coSP = true;
				}
			}

			if (!coSP) 
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}

			// CHECK TRUNG MA
			for (int i = 0; i < spMa.length - 1; i++) {
				for (int j = i + 1; j < spMa.length; j++) {
					if (spMa[i].trim().length() > 0
							&& spMa[j].trim().length() > 0) {
						if (spMa[i].trim().equals(spMa[j].trim())) {
							this.msg = "Sản phẩm ( " + spTen[i]
									+ " )  đã bị trùng.";
							return false;
						}
					}
				}
			}
		}

		try {
			db.getConnection().setAutoCommit(false);

			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			String hopdong_fk = this.hopdongId.trim().length() <= 0 ? "null" : this.hopdongId.trim();
			

			String query = " Update ERP_HopDong set HopDong_fk="+hopdong_fk+",LoaiDonHang='"+loaidonhang+"',mahopdong = '" + this.ma+ "', tungay = '" + this.tungay + "', denngay = '"+ this.denngay + "', ghichu = N'" + this.ghichu + "', "+ " 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '"+ this.kbhId + "', gsbh_fk = '" + this.gsbhId+ "', ddkd_fk = '" + this.ddkdId + "', npp_fk = '"+ this.nppId + "', kho_fk = " + khonhan_fk+ ", ngaysua = '" + getDateTime() + "', nguoisua = '"+ this.userId + "', chietkhau = '" + chietkhau+ "', vat = '" + vat + "' " + " where pk_seq = '" + this.id+ "' ";

			if (!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_HopDong " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_HOPDONG_SANPHAM where hopdong_fk = '" + this.id+ "'";
			if (!db.update(query)) 
			{
				this.msg = "Không thể cập nhật ERP_HOPDONG_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_HOPDONG_APDUNG where hopdong_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_HOPDONG_CHIETKHAU " + query;
				db.getConnection().rollback();
				return false;
			}

			for (int i = 0; i < spMa.length; i++) 
			{
				if (spMa[i].trim().length() > 0&& spSoluong[i].trim().length() > 0&& spGianhap[i].trim().length() > 0) 
				{
					String ck = "0";
					if (spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");

					String thueVAT = spVAT[i].trim().replaceAll(",", "");
					if (thueVAT.trim().length() < 0)
						thueVAT = "0";

					query = 
						"insert ERP_HOPDONG_SANPHAM( hopdong_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, tungay, denngay ) "+ 
						"select '"+ this.id+ "', pk_seq, '"+ spSoluong[i].replaceAll(",", "")+ "', '"+ spGianhap[i].replaceAll(",", "")+ "', '"+ ck+ "', '"+ thueVAT+ "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'"+ spDonvi[i].trim()+ "' ), DVDL_FK ), '"+ spTungay[i].trim()+ "', '"+ spDenngay[i].trim()+ "' "+ "from SANPHAM where MA = '"+ spMa[i].trim() + "' ";
					System.out.println("1.Insert HD - SP: " + query);
					if (!db.update(query)) {
						this.msg = "Khong the tao moi ERP_HOPDONG_SANPHAM: "
								+ query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			//INSERT CHIET KHAU BO SUNG
			if(this.dhCk_diengiai != null)
			{
				for(int i = 0; i < this.dhCk_diengiai.length; i++)
				{
					if(this.dhCk_giatri[i].trim().length() > 0)
					{
						query = "insert ERP_HOPDONG_CHIETKHAU(HOPDONG_FK, DIENGIAI, GIATRI, LOAI) " +
								"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "' ) ";
						
						System.out.println("1.Insert HD - CK: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_HOPDONG_CHIETKHAU: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			//HOP DONG CHUNG AP DUNG CHO NHUGN ETC NAO
			if(loaidonhang.equals("3") && this.khApdungId.trim().length() > 0)
			{
				query = 
						"Insert ERP_HOPDONG_APDUNG(hopdong_fk, npp_fk) " +
						"select '" + this.id + "', pk_seq from NhaPhanPhoi where pk_seq in ( " + this.khApdungId + " ) ";
				System.out.println("1.Insert HD - AP: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_HOPDONG_APDUNG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}

		return true;
	}

	public void createRs() {
		Utility util = new Utility();

		this.khoNhanRs = db
				.get("select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' AND PK_sEQ IN "
						+ util.quyen_khoTT(userId) + " ");

		this.dvtRs = db
				.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdRs = db
				.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		this.kbhRs = db
				.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_seq = '100052' ");

		this.gsbhRs = db
				.get("select PK_SEQ, TEN from GIAMSATBANHANG where trangthai = '1' ");
		
		String query = "select pk_seq, TEN from DAIDIENKINHDOANH where 1=1 ";
		if (this.gsbhId.trim().length() > 0)						
			query += " and pk_seq in ( select ddkd_fk from DDKD_GSBH where GSBH_FK = '"
					+ this.gsbhId + "' ) ";
			this.ddkdRs = db.get(query);
		System.out.println("lay ddkv theo gsbh" + query);

		query = "select PK_SEQ, MA + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  AND isKHACHHANG = '1' ";
		if (this.ddkdId.trim().length() > 0)
			query += " and DDKD_FK = '" + this.ddkdId + "' ";
		this.nppRs = db.get(query);

		if (this.nppId.trim().length() > 0) 
		{
			query = "select DDKD_FK, ( select top(1) gsbh_fk from DDKD_GSBH where ddkd_fk = a.ddkd_fk ) as gsbh_fk "+ "From NhaPhanPhoi a  "+ "where pk_Seq = '"+ this.nppId+ "' ";
			ResultSet rsInfo = db.get(query);
/*			if (rsInfo != null) {*/
				try 
				{
					if (rsInfo.next()) 
					{
						this.ddkdId = rsInfo.getString("DDKD_FK");
						this.gsbhId = rsInfo.getString("gsbh_fk");
					}
					rsInfo.close();
				} catch (Exception e) 
				{
				}			
			System.out.println("nppId : " +query+"__Info__"+ddkdId);
		}
		
		if(this.loaidonhang.equals("1") && this.nppId.trim().length() > 0 )
		{
			query = "select PK_SEQ, cast(pk_seq as varchar(10)) + ' [' + mahopdong + ']' as diengiai " +
					"from ERP_HOPDONG where npp_fk = '" + this.nppId + "' order by pk_seq desc ";
			
			System.out.println("ohuj"+ query);
			this.hopdongRs = db.get(query);
			
			if(this.spMa == null && this.hopdongId.trim().length() > 0 )
			{
				//INIT SAN PHAM LUC DAU SE GIONG SP TRONG HOP DONG CU, NHUNG DUOC PHEP SUA LAI SO LUONG
				
				String loaihopdongPL = "";
				if(this.loaidonhang.equals("1") && this.hopdongId.trim().length() > 0 )
				{
					query = "select loaidonhang from ERP_HOPDONG where pk_seq = '" + this.hopdongId + "'";
					ResultSet rsLOAI = db.get(query);
					if(rsLOAI != null)
					{
						try 
						{
							if(rsLOAI.next())
							{
								loaihopdongPL = rsLOAI.getString("loaidonhang");
							}
							rsLOAI.close();
						} 
						catch (Exception e) {}
					}
				}
				
				if( !(this.loaidonhang.equals("1") && loaihopdongPL.equals("2")) )
					initSANPHAM_THEOHD();
			}
			
		}
		

	}

	private void initSANPHAM() {
		String query = "select b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, a.tungay, a.denngay, a.thueVAT    "
				+ "	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi "
				+ " from ERP_HopDong_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    "
				+ " 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       "
				+ "where a.HOPDONG_FK = '" + this.id + "' ";

		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);

		NumberFormat formater = new DecimalFormat("##,###,###");
		if (spRs != null) {
			try {
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";

				String spTRONGLUONG = "";
				String spTHETICH = "";

				String spQuyDoi = "";

				while (spRs.next()) {
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG"))
							+ "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA"))
							+ "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau"))
							+ "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
				
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi += spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();

				if (spMA.trim().length() > 0) {
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");

					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");

					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");

					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");

					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");

					spCHIETKHAU = spCHIETKHAU.substring(0,
							spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");

					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");

					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");

					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");

					spTRONGLUONG = spTRONGLUONG.substring(0,
							spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");

					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");

					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");

				}

				// INIT CHIET KHAU
				query = "select DIENGIAI, GIATRI, LOAI from ERP_HOPDONG_CHIETKHAU where HOPDONG_FK = '"
						+ this.id + "'";
				System.out.println("[INIT_CK]" + query);
				ResultSet rsCK = db.get(query);
				if (rsCK != null) {
					String dkCK_diengiai = "";
					String dkCK_giatri = "";
					String dkCK_loai = "";

					while (rsCK.next()) {
						dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
						dkCK_giatri += formater
								.format(rsCK.getDouble("GIATRI")) + "__";
						dkCK_loai += rsCK.getString("LOAI") + "__";
					}
					rsCK.close();

					if (dkCK_diengiai.trim().length() > 0) {
						dkCK_diengiai = dkCK_diengiai.substring(0,
								dkCK_diengiai.length() - 2);
						this.dhCk_diengiai = dkCK_diengiai.split("__");

						dkCK_giatri = dkCK_giatri.substring(0,
								dkCK_giatri.length() - 2);
						this.dhCk_giatri = dkCK_giatri.split("__");

						dkCK_loai = dkCK_loai.substring(0,
								dkCK_loai.length() - 2);
						this.dhCk_loai = dkCK_loai.split("__");
					}

				}
			} catch (Exception e) {
				System.out.println("115.Exception: " + e.getMessage());
			}
		}

	}
	
	private void initSANPHAM_THEOHD() 
	{
		String query =  
					"select b.MA, b.TEN, DV.donvi, isnull(a.soluong, 0) as soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, a.tungay, a.denngay, a.thueVAT    " +	
					"	,(select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018)   as spQuyDoi "+
					" from ERP_HopDong_SANPHAM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.HOPDONG_FK = '" + this.hopdongId + "' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String spMA = "";
				String spTEN = "";
				String spDONVI = "";
				String spSOLUONG = "";
				String spGIANHAP = "";
				String spCHIETKHAU = "";
				String spVAT = "";
				String spTUNGAY = "";
				String spDENNGAY = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					
					if(spRs.getString("tungay").trim().length() > 0)
						spTUNGAY += spRs.getString("tungay") + "__";
					else
						spTUNGAY += " __";
					
					if(spRs.getString("denngay").trim().length() > 0)
						spDENNGAY += spRs.getString("denngay") + "__";
					else
						spDENNGAY += " __";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
				}
				spRs.close();
				
				if(spMA.trim().length() > 0)
				{
					spMA = spMA.substring(0, spMA.length() - 2);
					this.spMa = spMA.split("__");
					
					spTEN = spTEN.substring(0, spTEN.length() - 2);
					this.spTen = spTEN.split("__");
					
					spDONVI = spDONVI.substring(0, spDONVI.length() - 2);
					this.spDonvi = spDONVI.split("__");
					
					spSOLUONG = spSOLUONG.substring(0, spSOLUONG.length() - 2);
					this.spSoluong = spSOLUONG.split("__");
					
					spGIANHAP = spGIANHAP.substring(0, spGIANHAP.length() - 2);
					this.spGianhap = spGIANHAP.split("__");
					
					spCHIETKHAU = spCHIETKHAU.substring(0, spCHIETKHAU.length() - 2);
					this.spChietkhau = spCHIETKHAU.split("__");
					
					spVAT = spVAT.substring(0, spVAT.length() - 2);
					this.spVAT = spVAT.split("__");
					
					spTUNGAY = spTUNGAY.substring(0, spTUNGAY.length() - 2);
					this.spTungay = spTUNGAY.split("__");
					
					spDENNGAY = spDENNGAY.substring(0, spDENNGAY.length() - 2);
					this.spDenngay = spDENNGAY.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
				}
				
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}

	public void init() 
	{	
		String query = "select mahopdong, trangthai, tungay, denngay, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, gsbh_fk, ddkd_fk, npp_fk, kho_fk, isnull(chietkhau, 0) as chietkhau, vat, loaidonhang, hopdong_fk " +
				"from ERP_HopDong where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ma = rs.getString("mahopdong");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.gsbhId = rs.getString("gsbh_fk");
					this.ddkdId = rs.getString("ddkd_fk");
					this.trangthai = rs.getString("trangthai");
					
					if(rs.getString("hopdong_fk") != null)
						this.hopdongId = rs.getString("hopdong_fk");
		
				}
				rs.close();
				//
			/*	query = "select khachhang_fk from ERP_HOPDONG_APDUNG where hopdong_fk = '" + this.id + "'";
				System.out.println("=================== AP DUNG: " + query );
				rs = db.get(query);
				String khApdungId = "";
				while(rs.next())
				{
					khApdungId += rs.getString("khachhang_fk") + ",";
				}
				rs.close();
				
				if(khApdungId.trim().length() > 0)
				{
					khApdungId = khApdungId.substring(0, khApdungId.length() - 1);
					this.khApdungId = khApdungId;
				}*/
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.initSANPHAM();
		
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

	public void setNppId(String nppId) {

		this.nppId = nppId;
	}

	public ResultSet getNppRs() {

		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) {

		this.nppRs = nppRs;
	}

	public String getLoaidonhang() {

		return this.loaidonhang;
	}

	public void setLoaidonhang(String loaidonhang) {

		this.loaidonhang = loaidonhang;
	}

	public String getChietkhau() {

		return this.chietkhau;
	}

	public void setChietkhau(String chietkhau) {

		this.chietkhau = chietkhau;
	}

	public String getVat() {

		System.out.println("---VAT LA: " + this.vat);
		return this.vat;
	}

	public void setVat(String vat) {

		this.vat = vat;
	}

	public String getDvkdId() {

		return this.dvkdId;
	}

	public void setDvkdId(String dvkdId) {

		this.dvkdId = dvkdId;
	}

	public ResultSet getDvkdRs() {

		return this.dvkdRs;
	}

	public void setDvkdRs(ResultSet dvkdRs) {

		this.dvkdRs = dvkdRs;
	}

	public String getKbhId() {

		return this.kbhId;
	}

	public void setKbhId(String kbhId) {

		this.kbhId = kbhId;
	}

	public ResultSet getKbhRs() {

		return this.kbhRs;
	}

	public void setKbhRs(ResultSet kbhRs) {

		this.kbhRs = kbhRs;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}

	public void setDvtRs(ResultSet dvtRs) {

		this.dvtRs = dvtRs;
	}

	public String[] getDhck_diengiai() {

		return this.dhCk_diengiai;
	}

	public void setDhck_Diengiai(String[] obj) {

		this.dhCk_diengiai = obj;
	}

	public String[] getDhck_giatri() {

		return this.dhCk_giatri;
	}

	public void setDhck_giatri(String[] obj) {

		this.dhCk_giatri = obj;
	}

	public String[] getDhck_loai() {

		return this.dhCk_loai;
	}

	public void setDhck_loai(String[] obj) {

		this.dhCk_loai = obj;
	}

	public String[] getSpTrongluong() {

		return this.spTrongluong;
	}

	public void setSpTrongluong(String[] spTrongluong) {

		this.spTrongluong = spTrongluong;
	}

	public String[] getSpThetich() {

		return this.spThetich;
	}

	public void setSpThetich(String[] spThetich) {

		this.spThetich = spThetich;
	}

	public String[] getSpQuyDoi() {
		return spQuyDoi;
	}

	public void setSpQuyDoi(String[] spQuyDoi) {
		this.spQuyDoi = spQuyDoi;
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

	public String[] getSpChietkhau() {

		return this.spChietkhau;
	}

	public void setSpChietkhau(String[] spChietkhau) {

		this.spChietkhau = spChietkhau;
	}

	public String[] getSpTungay() {

		return this.spTungay;
	}

	public void setSpTungay(String[] spTungay) {

		this.spTungay = spTungay;
	}

	public String[] getSpDenngay() {

		return this.spDenngay;
	}

	public void setSpDenngay(String[] spDenngay) {

		this.spDenngay = spDenngay;
	}

	public String getMahopdong() {

		return this.ma;
	}

	public void setMahopdong(String ma) {

		this.ma = ma;
	}

	public String getGsbhId() {

		return this.gsbhId;
	}

	public void setGsbhId(String gsbhId) {

		this.gsbhId = gsbhId;
	}

	public ResultSet getGsbhRs() {

		return this.gsbhRs;
	}

	public void setGsbhRs(ResultSet gsbhRs) {

		this.gsbhRs = gsbhRs;
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

	public void setDddkdRs(ResultSet ddkdRs) {

		this.ddkdRs = ddkdRs;
	}

	public String[] getSpVat() {

		return this.spVAT;
	}

	public void setSpVat(String[] spVat) {

		this.spVAT = spVat;
	}

	String hopdongId;
	ResultSet hopdongRs;
	
	String khApdungId;
	ResultSet khApdungRs;
	
	public String getHopdongId() {
		
		return this.hopdongId;
	}

	
	public void setHopdongId(String hopdongId) {
		
		this.hopdongId = hopdongId;
	}

	
	public ResultSet getHopdongRs() {
		
		return this.hopdongRs;
	}

	
	public void setHopdongRs(ResultSet hopdongRs) {
		
		this.hopdongRs = hopdongRs;
	}

	String hopdongchung;
	public String getHopdongchung() {

		return this.hopdongchung;
	}


	public void setHopdongchung(String hopdongchung) {
		
		this.hopdongchung = hopdongchung;
	}

	
	public String getKhApdungId() {
		
		return this.khApdungId;
	}

	
	public void setKhApdungId(String khApdungId) {
		
		this.khApdungId = khApdungId;
	}

	
	public ResultSet getKhApdungRs() {
		
		return this.khApdungRs;
	}

	@Override
  public void setKhApdungRs(ResultSet khApdungRs)
  {
	 this.khApdungRs=khApdungRs;
  }
	
	
	
	
}
