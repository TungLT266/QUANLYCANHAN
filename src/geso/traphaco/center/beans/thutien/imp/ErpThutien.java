package geso.traphaco.center.beans.thutien.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.beans.thutien.IErpThutien;
import geso.traphaco.center.beans.thutien.IHoadon;
import geso.traphaco.center.beans.thutien.imp.Hoadon;

public class ErpThutien implements IErpThutien 
{
	String userId;
	String id;
	String ngaychungtu;
	String ngayghiso;
	String ctyId;
	String lydonop;
	String nppId;
	ResultSet nppRs;
	
	String hdId;
	ResultSet hdTCRs;
	
	String Soin;
	
	String htttId;
	ResultSet htttRs;
	
	String ndId;
	ResultSet ndRs;
	
	String DoiTuongTamUng;
	
	String nccId="";
	ResultSet nccRs;
	
	String nvtuId="";
	ResultSet nvtuRs;
	
	String nganhangId;
	String nganhangTen;
	ResultSet nganhangRs;
	
	String chinhanhId;
	String chinhanhTen;
	ResultSet chinhanhRs;
	
	String Sotaikhoan;
	
	String nguoinoptien;
	String noidungtt;
	String sotientt;
	String sotienttNT;
	
	String sochungtu;
	
	String tungay;
	String denngay;
	
	String hoadonId;
	List<IHoadon> hoadonList;
	
	String msg;
	String thuduoc;
	String thuduocNT;
	String bpkinhdoanh;
	String cpnganhang;
	String chenhlech;
	String chenhlechNT;
	String tongVND;
	String tongNT;
	String tigia;
	String Tigia_hoadon;
	
	String nppIds;
	String hdIds;
	
	String chietkhau;
	String chietkhauNT;
	
	String DoiTuongDinhKhoan;
	String DoituongdinhkhoanId;
	String MaTenDoiTuongDinhKhoan;
	String dinhkhoanco;
	String dinhkhoancoId;
	
	
	ResultSet sotkRs;
	dbutils db;
	Utility util;
	
	public ErpThutien()
	{
		this.id = "";
		this.ctyId = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "100000";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.nguoinoptien = "";
		this.noidungtt = "";
		this.sotientt = "0";
		this.sotienttNT = "0";
		this.hoadonId = "";
		this.msg = "";
		this.sochungtu = "";
		this.tungay = "";
		this.denngay = "";
		this.thuduoc = "0";
		this.thuduocNT = "0";
		this.bpkinhdoanh = "";
		this.cpnganhang = "0";
		this.chenhlech = "0";
		this.chenhlechNT = "0";
		this.tongNT = "0";
		this.tongVND = "0";
		this.lydonop= "";
		this.chietkhauNT= "0";
		Tigia_hoadon="";
		this.tigia = "1";
		this.nppIds="";
		this.hdIds="";
		
		this.nganhangTen="";
		this.chinhanhTen="";
		this.Sotaikhoan="";
		
		this.Soin ="";
		
		this.dinhkhoanco="";
		this.dinhkhoancoId= "";
		this.DoiTuongDinhKhoan= "";
		
		this.DoiTuongTamUng="";
		this.nccId="";
		this.nvtuId="";
		
		this.hdId= "";
		
		this.hoadonList = new ArrayList<IHoadon>();
		
		this.db = new dbutils();
		this.util=new Utility();
	}
	
	public ErpThutien(String id)
	{
		this.id = id;
		this.ctyId = "";
		this.ngaychungtu = "";
		this.ngayghiso = "";
		this.nppId = "";
		this.htttId = "";
		this.ndId = "100000";
		this.nganhangId = "";
		this.chinhanhId = "";
		this.nguoinoptien = "";
		this.noidungtt = "";
		this.sotientt = "0";
		this.sotienttNT = "0";
		this.hoadonId = "";
		this.msg = "";
		this.sochungtu = "";
		this.tungay = "";
		this.denngay = "";
		this.thuduoc = "0";
		this.thuduocNT = "0";
		this.bpkinhdoanh = "";
		this.cpnganhang = "0";
		this.chenhlech = "0";
		this.chenhlechNT = "0";
		this.tongNT = "0";
		this.tongVND = "0";
		this.lydonop= "";
		this.chietkhauNT = "0";
		this.nppIds="";
		this.hdIds="";
		this.dinhkhoanco="";
		this.dinhkhoancoId= "";
		this.DoiTuongDinhKhoan= "";
		
		this.DoiTuongTamUng="";
		this.nccId="";
		this.nvtuId="";
		
		this.nganhangTen="";
		this.chinhanhTen="";
		this.Sotaikhoan="";
		
		this.hdId= "";
		this.Soin ="";
		
		this.tigia = "1";
		this.hoadonList = new ArrayList<IHoadon>();
		this.util=new Utility();
		this.db = new dbutils();
	}
	
	public String getDoiTuongTamUng() {
		return DoiTuongTamUng;
	}

	public void setDoiTuongTamUng(String DoiTuongTamUng) {
		this.DoiTuongTamUng= DoiTuongTamUng;
		
	}
	
public String getNccId() {
		
		return this.nccId;
	}

	
	public void setNccId(String nccId) {
		
		this.nccId=nccId;
	}
	
	public void setNccRs(ResultSet nccRs)
	{
		this.nccRs = nccRs;
	}
	public ResultSet getNccRs() 
	{
		return nccRs;
	}
	
    public String getNvtuId() {
		
		return this.nvtuId;
	}

	
	public void setNvtuId(String nvtuId) {
		
		this.nvtuId=nvtuId;
	}
	
	public ResultSet getNvtuRs() 
	{
		return nvtuRs;
	}
	public void setNvtuRs(ResultSet nvtuRs) 
	{
		this.nvtuRs = nvtuRs;
	}
	
	public void setChietkhau(String chietkhau) 
	{
		this.chietkhau = chietkhau;
	}
	public String getChietkhau()
	{
		return chietkhau;
	}
	
	public void setChietkhauNT(String chietkhauNT) 
	{
		this.chietkhauNT = chietkhauNT;
	}
	public String getChietkhauNT()
	{
		return chietkhauNT;
	}
	
	public String getUserId()
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;
	}

	public String getId()
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}

	public String getNgaychungtu() 
	{
		return this.ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) 
	{
		this.ngaychungtu = ngaychungtu;
	}

	public String getNppId()
	{
		return this.nppId;
	}

	public void setNppId(String nppId)
	{
		this.nppId = nppId;	
	}

	public ResultSet getNppRs()
	{
		return this.nppRs;
	}

	public void setNppRs(ResultSet nppRs) 
	{
		this.nppRs = nppRs;
	}

	public ResultSet getHdTCRs()
	{
		return this.hdTCRs;
	}

	public void setHdTCRs(ResultSet hdTCRs) 
	{
		this.hdTCRs = hdTCRs;
	}
	
	public ResultSet getSotkRs()
	{
		return this.sotkRs;
	}

	public void setSotkRs(ResultSet sotkRs) 
	{
		this.sotkRs = sotkRs;
	}

	public String getHtttId() 
	{
		return this.htttId;
	}

	public void setHtttId(String htttId)
	{
		this.htttId = htttId;
	}

	public ResultSet getHtttRs() 
	{
		return this.htttRs;
	}

	public void setHtttRs(ResultSet htttRs)
	{
		this.htttRs = htttRs;	
	}

	public String getNganhangId()
	{
		return this.nganhangId;
	}

	public void setNganhangId(String nganhangId)
	{
		this.nganhangId = nganhangId;
	}

	public ResultSet getNganhangRs() 
	{
		return this.nganhangRs;
	}

	public void setNganhangRs(ResultSet nganhangRs) 
	{
		this.nganhangRs = nganhangRs;
	}

	public String getChinhanhId() 
	{
		return this.chinhanhId;
	}

	public void setChinhanhId(String cnId)
	{
		this.chinhanhId = cnId;
	}

	public ResultSet getChinhanhRs()
	{
		return this.chinhanhRs;
	}

	public void setChinhanhRs(ResultSet chinhanhRs) 
	{
		this.chinhanhRs = chinhanhRs;
	}

	public String getNguoinoptien() 
	{
		return this.nguoinoptien;
	}

	public void setNguoinoptien(String nguoinoptien)
	{
		this.nguoinoptien = nguoinoptien;
	}

	public String getNoidungtt()
	{
		return this.noidungtt;
	}

	public void setNoidungtt(String ndtt) 
	{
		this.noidungtt = ndtt;
	}

	public String getSotientt() 
	{
		return this.sotientt;
	}

	public void setSotientt(String sotientt) 
	{
		this.sotientt = sotientt;
	}

	public String getSotienttNT() 
	{
		return this.sotienttNT;
	}

	public void setSotienttNT(String sotienttNT) 
	{
		this.sotienttNT = sotienttNT;
	}

	public String getHoadonIds() 
	{
		return this.hoadonId;
	}

	public void setHoadonIds(String hdIds) 
	{
		this.hoadonId = hdIds;
	}

	public List<IHoadon> getHoadonRs() 
	{
		return this.hoadonList;
	}

	public void setHoadonRs(List<IHoadon> hoadonRs)
	{
		this.hoadonList = hoadonRs;
	}

	public String getThuduoc() 
	{
		return this.thuduoc;
	}

	public void setThuduoc(String thuduoc)
	{
		this.thuduoc = thuduoc;
	}

	public String getThuduocNT() 
	{
		return this.thuduocNT;
	}

	public void setThuduocNT(String thuduocNT)
	{
		this.thuduocNT = thuduocNT;
	}

	public String getBpkinhdoanh() 
	{
		return this.bpkinhdoanh;
	}

	public void setBpkinhdoanh(String Bpkinhdoanh)
	{
		this.bpkinhdoanh = Bpkinhdoanh;
	}

	public String getChiphinganhang() 
	{
		return this.cpnganhang;
	}

	public void setChiphinganhang(String cpnganhang)
	{
		this.cpnganhang = cpnganhang;
	}

	public String getChenhlech() 
	{
		return this.chenhlech;
	}

	public void setChenhlech(String chenhlech)
	{
		this.chenhlech = chenhlech;
	}

	public String getChenhlechNT() 
	{
		return this.chenhlechNT;
	}

	public void setChenhlechNT(String chenhlechNT)
	{
		this.chenhlechNT = chenhlechNT;
	}

	public String getTigia() 
	{
		return this.tigia;
	}

	public void setTigia(String tigia)
	{
		this.tigia= tigia;
	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getTongNT() 
	{
		return this.tongNT;
	}

	public void setTongNT(String tongNT)
	{
		this.tongNT = tongNT;
	}

	public String getTongVND() 
	{
		return this.tongVND;
	}

	public void setTongVND(String tongVND)
	{
		this.tongVND = tongVND;
	}

	public boolean createTTHD() 
	{

		if (this.ndId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nội dung thu tiền";
			return false;
		}
		
		//	100000	-	Thu tiền bán hàng
		//	100001	-	Khách hàng trả trước
		//	100002	-	Thu khác
		//	100003	-	Thu hồi tạm ứng
		//	100004	-	Thu theo bảng kê		
		
		if (this.ndId.equals("100003")) // THU HỒI TẠM ỨNG
		{		
			if(this.thuduoc.equals("0") )
			{
				this.msg = "Vui lòng chọn hóa đơn thu tiền";
				return false;
			}
		}

		if (this.ndId.equals("100002")) { // THU KHÁC
			
			if (this.dinhkhoancoList.size() <= 0) {
				this.msg = "Vui lòng chọn định khoản có";
				return false;
			}

			for (int j = 0; j < this.dinhkhoancoList.size(); j++) {
				IDinhkhoanco dkco = this.dinhkhoancoList.get(j);

				if (Integer.parseInt(dkco.getDoituongdinhkhoan()) >= 1) {
					if (dkco.getDoituongId().trim().length() <= 0) {
						this.msg = "Vui lòng chọn mã và tên đối tượng ";
						return false;
					}
				}
			}

		}
		

		if (this.DoiTuongTamUng.equals("1")) // NEU LA NCC
			this.nvtuId = "NULL";		
		
		if (this.DoiTuongTamUng.equals("0")) // NEU LA NV
			this.nccId = "NULL";		

		if (this.sotientt.trim().length() <= 0) {
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}

		try 
		{
			String ngaytao = getDateTime();

			db.getConnection().setAutoCommit(false);

			String khachhang_fk = "NULL";

			if (!this.ndId.equals("100002")) {
				khachhang_fk = this.nppId;
			}

			if (this.nhomkhttId.trim().length() <= 0)
				this.nhomkhttId = "NULL";

			if (this.nhomkhtt.equals("1")) // THU THEO NHÓM KHÁCH HÀNG
				khachhang_fk = "NULL";
			
			if (this.ndId.equals("100003"))
				khachhang_fk = "NULL";
			
			if (this.ndId.equals("100002"))
				khachhang_fk = "NULL";
			
			if (!this.ndId.equals("100003")) {
				this.nvtuId = "NULL";
				this.nccId = "NULL";
			}
			
			this.kbhId = "NULL";

			String query = "";

			if (this.ndId.equals("100004")) { // THU TIỀN THEO BẢNG KÊ
				khachhang_fk = null;
				this.isNPP = null;
			} else
				this.bangkeId = null;

			query = "Insert ERP_THUTIEN(KBH_FK, NGUOINOPTIEN, DIACHI, NHOMKHTT_FK, CHIETKHAUNT, CHIETKHAU, TIGIA, SOTIENTTNT, THUDUOCNT, PHINGANHANGNT, THUDUOC, PHINGANHANG, CHENHLECH, NGAYCHUNGTU, NGAYGHISO, TRANGTHAI, "+
					"KHACHHANG_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT_FK, GHICHU, SOTIENTT, TIENTE_FK, SOCHUNGTU, NGAYTAO, "+
					"NGUOITAO, NGAYSUA, NGUOISUA, NCC_FK, NHANVIEN_FK, CTKEMTHEO, ISKTTDUYET,CONGTY_FK, BANGKE_FK, PREFIX, isNPP) "+
					"values( " + this.kbhId + ", N'" + this.nguoinoptien + "', N'" + this.diachi + "' , "+
					this.nhomkhttId + ", " + this.chietkhauNT.replace(",", "") + "," + this.chietkhau.replace(",", "")+
					" ," + this.tigia.replaceAll(",", "") + ", " + this.sotienttNT.replaceAll(",", "") + ", "+
					this.thuduocNT.replaceAll(",", "") + ", " + this.cpnganhangNT.replaceAll(",", "") + ", "+
					this.thuduoc + ", " + this.cpnganhang + ", " + this.chenhlech + ", '" + this.ngaychungtu + "', '"+
					this.ngayghiso + "', '0', " + khachhang_fk + ", '" + this.htttId + "', NULL, NULL, '', " + " '"+
					this.ndId + "', N'" + this.noidungtt + "', '" + this.sotientt.replaceAll(",", "") + "', '"+
					this.tienteId + "', '" + this.sochungtu + "', '" + ngaytao + "', '" + this.userId + "', '"+
					ngaytao + "', '" + this.userId + "', " + this.nccId + ", " + this.nvtuId + ",N'"+
					this.chungtukemtheo + "', 0, " + this.ctyId + ", " + this.bangkeId + ", 'PT', '" + this.isNPP	+ "')";

			System.out.println(query);

			if (!db.update(query)) {
				this.msg = "Không thể tạo mới phiếu thu tiền. Vui lòng liên hệ admin để được xử lý! ";
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
			
			
			if (this.ndId.equals("100004") || this.ndId.equals("100000")) // THU TIỀN BÁN HÀNG || THU HỒI THEO BẢNG KÊ THÌ LƯU CHI TIẾT THEO SẢN PHẨM
			{
				// LƯU CHI TIẾT THEO HÓA ĐƠN
				
				for (int i = 0; i < this.hoadonList.size(); i++) 
				{
					IHoadon hoadon = this.hoadonList.get(i);
					
					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");	
					String chenhlech = hoadon.getChenhlech().replaceAll(",", "");	
					String xoachenhlech = hoadon.getxoacl()== ""? "0":hoadon.getxoacl();
					
					String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
					String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
					String conlai = hoadon.getConlai().replaceAll(",", "");
					
					if (this.ndId.equals("100000")) // THU TIỀN TRỰC TIẾP - BÁN HÀNG
					{
						 if (thanhtoan.trim().length() != 0) 
						 { 
							 if (Float.parseFloat(thanhtoan) > 0 || (Float.parseFloat(chenhlech)!=0 && Integer.parseInt(xoachenhlech)== 1 ) )  // NẾU THANH TOÁN > 0 HOẶC THANH TOÁN = 0 NHƯNG CÓ XÓA CHÊNH LỆCH 
							 { 																						
									query = "Insert ERP_THUTIEN_HOADON ( THUTIEN_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, LOAIHOADON, KHACHHANG_FK, PTCHIETKHAU, TIENCK, THUCTHU, KBH_FK, TIENCHENHLECH, XOACHENHLECH, isNPP ) "+
											"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim()+
											"', '" + hoadon.getTiengocHD().replaceAll(",", "") + "', " + hoadon.getLoaihd() + ",'"+
											hoadon.getKhId() + "', '" + hoadon.getptck().replaceAll(",", "") + "', "+
											hoadon.gettienck().replaceAll(",", "") + ", "+
											hoadon.getthucthu().replaceAll(",", "") + ", " + ( hoadon.getkbhId() == "" ? "null":hoadon.getkbhId() ) + ","+
										   (hoadon.getChenhlech().replaceAll(",", "") == "" ? "0": hoadon.getChenhlech().replaceAll(",", ""))+
											", " + (hoadon.getxoacl()== ""? "0":hoadon.getxoacl()) + ", " + hoadon.getisNPP() + " )";
									
									System.out.println(query);
				
									if (!db.update(query)) {
										this.msg = "KHONG THE TAO MOI PHIEU THU TIEN. VUI LONG LIEN HE ADMIN!";
										System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
										db.getConnection().rollback();
										return false;
									} 
									
									// POPUP HÓA ĐƠN - SẢN PHẨM
									for (int j = 0; j < hoadon.getHoadonList().size(); j++) {								
										 
										IHoadonct hoadonct = hoadon.getHoadonList().get(j);
				
										query = " INSERT ERP_THUTIEN_HOADON_SP (thutien_fk, hoadon_fk, sanpham_fk, thanhtoan, ptchietkhau, tienck, thucthu, loaihoadon, tienavat)"+
												" values ('" + tthdCurrent + "', '" + hoadonct.gethdId() + "', "+
												hoadonct.getspId() + ", " + hoadonct.getThanhtoan().replaceAll(",", "") + ", "+
												hoadonct.getptchietkhau().replaceAll(",", "") + ","+
												hoadonct.getsotienck().replaceAll(",", "") + ", "+
												hoadonct.getThucthu().replaceAll(",", "") + ", " + hoadon.getLoaihd() + ", "+hoadonct.getThanhtienhd().replaceAll(",", "")+")";
				
										System.out.println(query);
				
										if (!db.update(query)) {
											this.msg = "KHONG THE TAO MOI PHIEU THU TIEN. VUI LONG LIEN HE ADMIN!";
											System.out.println(this.msg);
											db.getConnection().rollback();
											return false;
										}
									}
									
									//POPUP TIỀN CHÊNH LỆCH
									for (int j = 0; j < hoadon.getChenhlechctList().size(); j++) {								
										 
										IChenhlechct chenhlechct = hoadon.getChenhlechctList().get(j);
				
										if(chenhlechct.gettienchenhlech().replaceAll(",", "").trim().length()>0&& hoadon.getxoacl().equals("1"))
										{
											query = " INSERT ERP_THUTIEN_HOADON_TIENCHENHLECH (thutien_fk, hoadon_fk, loaihoadon, khachhang_fk, tienchenhlech, macp, isNPP )"+
													" values ('" + tthdCurrent + "', '" + chenhlechct.gethdId() + "', "+ chenhlechct.getloaihd() + ", " + hoadon.getKhId() + ", "+
													chenhlechct.gettienchenhlech().replaceAll(",", "") + ", "+(chenhlechct.getmakhoanmuccp() == "" ? null: chenhlechct.getmakhoanmuccp())+
													", "+ hoadon.getisNPP()+")";
					
											System.out.println(query);
					
											if (!db.update(query)) {
												this.msg = "KHONG THE TAO MOI PHIEU THU TIEN. VUI LONG LIEN HE ADMIN!";
												System.out.println(this.msg);
												db.getConnection().rollback();
												return false;
											}
										}
									}
											
							 }
						 }
						
					}
					else // THU TIỀN GIÁN TIẾP QUA BẢNG KÊ THU TIỀN
					{							
						query = "Insert ERP_THUTIEN_HOADON ( THUTIEN_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, LOAIHOADON, KHACHHANG_FK, PTCHIETKHAU, TIENCK, THUCTHU, KBH_FK, TIENCHENHLECH, XOACHENHLECH, isNPP ) "+
								"values('" + tthdCurrent + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() +
								"', '" + hoadon.getTiengocHD().replaceAll(",", "") + "', " + hoadon.getLoaihd() + ",'"+
								hoadon.getKhId() + "', '" + hoadon.getptck().replaceAll(",", "") + "', "+
								hoadon.gettienck().replaceAll(",", "") + ", "+
								hoadon.getthucthu().replaceAll(",", "") + ", " + ( hoadon.getkbhId() == "" ? "null":hoadon.getkbhId() ) + ","+
								(hoadon.getChenhlech().replaceAll(",", "") == "" ? "0": hoadon.getChenhlech().replaceAll(",", ""))+
								", " + hoadon.getxoacl() + ", " + hoadon.getisNPP() + " )";
												
						System.out.println(query);
	
						if (!db.update(query)) {
							this.msg = "KHONG THE TAO MOI PHIEU THU TIEN. VUI LONG LIEN HE ADMIN!";
							System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
							db.getConnection().rollback();
							return false;
						} 
						
						// POPUP HÓA ĐƠN - SẢN PHẨM
						
						for (int j = 0; j < hoadon.getHoadonList().size(); j++) {								
							 
							IHoadonct hoadonct = hoadon.getHoadonList().get(j);
	
							query = " INSERT ERP_THUTIEN_HOADON_SP (thutien_fk, hoadon_fk, sanpham_fk, thanhtoan, ptchietkhau, tienck, thucthu, loaihoadon, tienavat)"+
									" values ('" + tthdCurrent + "', '" + hoadonct.gethdId() + "', "+hoadonct.getspId() + ", " + hoadonct.getThanhtoan().replaceAll(",", "") + ", "+
									hoadonct.getptchietkhau().replaceAll(",", "") + ","+hoadonct.getsotienck().replaceAll(",", "") + ", "+
									hoadonct.getThucthu().replaceAll(",", "") + ", " + hoadon.getLoaihd() + ", "+hoadonct.getThanhtienhd().replaceAll(",", "")+")";
	
							System.out.println(query);
	
							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_XOAKHTRATRUOC_HOADON_SP: " + query;
								System.out.println(this.msg);
								db.getConnection().rollback();
								return false;
							}
						}		
						
						//POPUP TIỀN CHÊNH LỆCH
						for (int j = 0; j < hoadon.getChenhlechctList().size(); j++) {								
							 
							IChenhlechct chenhlechct = hoadon.getChenhlechctList().get(j);
	
							if(chenhlechct.gettienchenhlech().replaceAll(",", "").trim().length()>0 && hoadon.getxoacl().equals("1"))
							{
								query = " INSERT ERP_THUTIEN_HOADON_TIENCHENHLECH (thutien_fk, hoadon_fk, loaihoadon, khachhang_fk, tienchenhlech, macp, isNPP )"+
										" values ('" + tthdCurrent + "', '" + chenhlechct.gethdId() + "', "+ chenhlechct.getloaihd() + ", " + hoadon.getKhId() + ", "+
										chenhlechct.gettienchenhlech().replaceAll(",", "") + ", "+(chenhlechct.getmakhoanmuccp() == "" ? null: chenhlechct.getmakhoanmuccp())+
										", "+ hoadon.getisNPP()+")";

								System.out.println(query);
		
								if (!db.update(query)) {
									this.msg = "KHONG THE TAO MOI PHIEU THU TIEN. VUI LONG LIEN HE ADMIN!";
									System.out.println(this.msg);
									db.getConnection().rollback();
									return false;
								}
							}
						 }
							 	
					}

				}		
				

			} else if( this.ndId.equals("100003") ) // THU HỒI TẠM ỨNG
			{				
				for (int i = 0; i < this.hoadonList.size(); i++) {
					IHoadon hoadon = this.hoadonList.get(i);

					String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
					String avat = hoadon.getTongtiencoVAT().replaceAll(",","");
					String sotienNT = hoadon.getSotienNT().replaceAll(",","");
					String conlai = hoadon.getConlai().replaceAll(",", "");

					if (thanhtoan.length() > 0 ) {
						if (Float.parseFloat(thanhtoan) != 0) {

							if (this.ndId.equals("100003"))
								hoadon.setLoaihd("");

							if (hoadon.getLoaihd().equals("7"))
								{
									thanhtoan = Float.parseFloat(thanhtoan)*(-1)+"";
									avat = Float.parseFloat(avat)*(-1)+"";
									conlai = Float.parseFloat(conlai)*(-1)+"";
								}
								
							query = " Insert ERP_THUTIEN_HOADON(THUTIEN_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI,LOAIHOADON) "+
									" values('"+ tthdCurrent+ "', '"+ hoadon.getId()+ "', '"+ thanhtoan.trim()	+ "', '"+ avat+ "', '"+ conlai.trim()+ "', '"+ hoadon.getLoaihd() + "')";

							if (!db.update(query)) {
								this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: "	+ query;
								System.out.println("[ErpThutien.createTTHD] error message: "+ this.msg);
								db.getConnection().rollback();
								return false;
							}
							
						/*	// NẾU LÀ HÓA ĐƠN TÀI CHÍNH || HOÁ ĐƠN PHẾ LIỆU  || HÓA ĐƠN HÀNG TRẢ VỀ || BÚT TOÁN TỔNG HỢP THÌ PHÂN BỔ THEO SẢN PHẨM
							// RIÊNG BÚT TOÁN TỔNG HỢP LÚC NÀO CŨNG ĐỂ SỐ LƯỢNG THANH TOÁN LÀ 1
							if( ( hoadon.getLoaihd().equals("0") || hoadon.getLoaihd().equals("1") ||  hoadon.getLoaihd().equals("7") || hoadon.getLoaihd().equals("2") )  && this.nppId.trim().length()>0 ) // CHỈ DÙNG PHÂN BỔ CHO KHÁCH HÀNG - NHÀ CUNG CẤP VÀ NHÂN VIÊN KHÔNG PHÂN BỔ
							{
								if(hoadon.getLoaihd().equals("0")) // HÓA ĐƠN TÀI CHÍNH
								{
									query = 
										" SELECT HDSP.SANPHAM_FK, HDSP.DONGIA, HDSP.SOLUONG, HDSP.VAT, HDSP.CHIETKHAU ,  \n"+
										" ( ROUND( HDSP.TIENAVAT  - HDSP.tientichluyCOVAT, 0) - ISNULL(BKTT.THANHTOAN, 0) - ISNULL (TT.THANHTOAN, 0)  " +
										"   -  ISNULL (KHTT.THANHTOAN, 0) - ISNULL (BTKH.THANHTOAN, 0) - ISNULL (CTCN.THANHTOAN, 0)) thanhtien, \n"+
										" ISNULL(BKTT.THANHTOAN, 0) isBKTT , ISNULL (KHTT.THANHTOAN, 0) isTT, ISNULL (BTKH.THANHTOAN, 0) isKHTT," +
										" ISNULL (CTCN.THANHTOAN, 0) isCTCN, ROUND( (HDSP.TIENAVAT  - HDSP.tientichluyCOVAT), 0)  TIENAVAT "+
										
										" FROM ERP_HOADONNPP_SP HDSP INNER JOIN ERP_HOADONNPP HD ON HDSP.HOADON_FK = HD.PK_SEQ  \n"+
										
										//THU TIỀN BẢNG KÊ
										
										" LEFT JOIN \n"+
										" ( SELECT A.hoadon_fk, A.sanpham_fk, SUM (ISNULL(A.THANHTOAN,0)) THANHTOAN  \n"+
										"   FROM ERP_THUTIEN_HOADON_SP A INNER JOIN ERP_THUTIEN B ON A.THUTIEN_FK = B.PK_SEQ \n"+
										"   WHERE B.TRANGTHAI != 2 AND B.BANGKE_FK IS NOT NULL \n"+
										"   GROUP BY  A.hoadon_fk, A.sanpham_fk \n"+
										" ) BKTT ON BKTT.HOADON_FK = HD.PK_SEQ AND HDSP.SANPHAM_FK = BKTT.SANPHAM_FK \n"+
										
										//THU TIỀN HÓA ĐƠN
										
										" LEFT JOIN \n"+
										" ( SELECT A.hoadon_fk, A.sanpham_fk, SUM (ISNULL(A.THANHTOAN,0)) THANHTOAN  \n"+
										"   FROM ERP_THUTIEN_HOADON_SP A INNER JOIN ERP_THUTIEN B ON A.THUTIEN_FK = B.PK_SEQ \n"+
										"   WHERE B.TRANGTHAI != 2 AND B.BANGKE_FK IS NULL AND B.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" \n"+
										"   GROUP BY  A.hoadon_fk, A.sanpham_fk \n"+
										" ) TT ON TT.HOADON_FK = HD.PK_SEQ AND HDSP.SANPHAM_FK = TT.SANPHAM_FK \n"+
										
										//XÓA KHÁCH HÀNG TRẢ TRƯỚC
										
										" LEFT JOIN \n"+
										" ( SELECT A.hoadon_fk, A.sanpham_fk, SUM (ISNULL(A.THANHTOAN,0)) THANHTOAN  \n"+
										"   FROM ERP_XOAKHTRATRUOC_PHANBO_CHITIET A INNER JOIN ERP_XOAKHTRATRUOC B ON A.XKHTT_FK = B.PK_SEQ \n"+
										"   WHERE B.TRANGTHAI != 2  \n"+
										"   GROUP BY  A.hoadon_fk, A.sanpham_fk \n"+
										" ) KHTT ON KHTT.HOADON_FK = HD.PK_SEQ AND HDSP.SANPHAM_FK = KHTT.SANPHAM_FK \n"+
										
										// BÙ TRỪ KHÁCH HÀNG
										
										" LEFT JOIN \n"+
										" ( SELECT A.hoadon_fk, A.sanpham_fk, SUM (ISNULL(A.THANHTOAN,0)) THANHTOAN  \n"+
										"   FROM ERP_BUTRUKHACHHANG_PHANBO_CHITIET A INNER JOIN ERP_BUTRUKHACHHANG B ON A.BTKH_FK = B.PK_SEQ \n"+
										"   WHERE B.TRANGTHAI != 2 and b.KH_CHUYENNO = "+this.nppId+" \n"+
										"   GROUP BY  A.hoadon_fk, A.sanpham_fk \n"+
										" ) BTKH ON BTKH.HOADON_FK = HD.PK_SEQ AND HDSP.SANPHAM_FK = BTKH.SANPHAM_FK \n"+
										
										// CẤN TRỪ CÔNG NỢ
										
										" LEFT JOIN \n"+
										" ( SELECT A.hoadon_fk, A.sanpham_fk, SUM (ISNULL(A.THANHTOAN,0)) THANHTOAN  \n"+
										"   FROM ERP_CANTRUCONGNO_PHANBO_CHITIET A INNER JOIN ERP_CANTRUCONGNO B ON A.CTCN_FK = B.PK_SEQ \n"+
										"   WHERE B.TRANGTHAI != 2 \n"+
										"   GROUP BY  A.hoadon_fk, A.sanpham_fk \n"+
										" ) CTCN ON CTCN.HOADON_FK = HD.PK_SEQ AND HDSP.SANPHAM_FK = CTCN.SANPHAM_FK \n"+
										
										" WHERE HDSP.HOADON_FK = '" +hoadon.getId()+ "' AND HDSP.SCHEME = ' ' ";
								}
								else if(hoadon.getLoaihd().equals("1")) // HÓA ĐƠN PHẾ LIỆU = HÓA ĐƠN KHÁC
								{
									query = 
										" SELECT HDSP.SANPHAM_FK, HDSP.DONGIACK DONGIA, 0 CHIETKHAU , \n"+
										"	 	 (ISNULL(HDSP.tienavat,0) - ISNULL(DATHANHTOAN.TONGTHU,0) )  THANHTIEN, \n"+
										"	 	 HDSP.SOLUONG SOLUONG, \n"+
										"	 	 0 ptchietkhau, HDSP.DONGIA, 0 SOLUONGTT, 0 TIENCK, HDSP.TIENAVAT TIENAVAT \n"+
									
										" FROM ERP_HOADONPHELIEU HD INNER JOIN ERP_HOADONPHELIEU_SANPHAM HDSP ON HD.PK_SEQ = HDSP.HOADONPHELIEU_FK \n"+
										" LEFT JOIN ( \n"+
										" SELECT  THU.sanpham_fk,THU.HOADON_FK, SUM(ISNULL(THU.TONGTHU,0)) TONGTHU \n"+
										" FROM ( \n"+
										// THU TIỀN HÓA ĐƠN
										"			SELECT TTHD.HOADON_FK, TTHD.sanpham_fk, SUM(TTHD.THANHTOAN) AS TONGTHU \n"+
										"			FROM ERP_THUTIEN_HOADON_SP TTHD \n"+
										"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI != 2 AND TT.BANGKE_FK IS NULL AND TTHD.LOAIHOADON = 1 AND TT.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" \n"+
										"			GROUP BY TTHD.sanpham_fk,TTHD.HOADON_FK \n"+

										"			UNION ALL \n"+	

										//TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
										"			SELECT TTHD.HOADON_FK,TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
										"			FROM ERP_XOAKHTRATRUOC_PHANBO_CHITIET TTHD \n"+
										"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XKHTT_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '1'  \n"+
										"           AND TT.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" \n"+
										"			GROUP BY TTHD.HOADON_FK, TTHD.SANPHAM_FK \n"+
															    
										"   		UNION ALL \n"+

										// BÙ TRỪ KHÁCH HÀNG
										"			SELECT BT_KH.HOADON_FK,BT_KH.SANPHAM_FK, SUM(BT_KH.THANHTOAN) AS THANHTOAN  \n"+
										"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_PHANBO_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK \n "+
										"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1 AND BT.KH_CHUYENNO = "+this.nppId+" \n"+
										"			GROUP BY BT_KH.HOADON_FK, BT_KH.SANPHAM_FK \n"+
								
										"	 ) THU \n"+
										" GROUP BY THU.sanpham_fk, THU.HOADON_FK \n"+
										" ) DATHANHTOAN ON HD.PK_SEQ =  DATHANHTOAN.hoadon_fk AND HDSP.SANPHAM_FK = DATHANHTOAN.sanpham_fk \n"+
										" WHERE HD.PK_SEQ = " + hoadon.getId() ;
								}
								else if( hoadon.getLoaihd().equals("7") ) // HÓA ĐƠN HÀNG TRẢ VỀ
								{
									query = 
										" SELECT HDSP.SANPHAM_FK, HDSP.DONGIA DONGIA, 0 CHIETKHAU , \n"+
										"	 	 ((ISNULL(HDSP.tienavat,0) - ISNULL(DATHANHTOAN.TONGTHU,0)))  THANHTIEN, \n"+
										"	 	 HDSP.SOLUONG SOLUONG, \n"+
										"	 	 0 ptchietkhau, 0 SOLUONGTT, 0 TIENCK, HDSP.TIENAVAT TIENAVAT \n"+
										
										" FROM ERP_HOADON_SP HDSP INNER JOIN ERP_HOADON HD ON HDSP.HOADON_FK = HD.PK_SEQ  \n"+
										" LEFT JOIN ( \n"+
										" SELECT  THU.sanpham_fk,THU.HOADON_FK, SUM(ISNULL(THU.TONGTHU,0)) TONGTHU \n"+
										" FROM ( \n"+
										// THU TIỀN HÓA ĐƠN
										"			SELECT TTHD.HOADON_FK, TTHD.sanpham_fk, SUM(TTHD.THANHTOAN) AS TONGTHU \n"+
										"			FROM ERP_THUTIEN_HOADON_SP TTHD \n"+
										"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI != 2 AND TT.BANGKE_FK IS NULL AND TTHD.LOAIHOADON = 7 AND TT.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" \n"+
										"			GROUP BY TTHD.sanpham_fk,TTHD.HOADON_FK \n"+

										"			UNION ALL \n"+	

										//TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
										"			SELECT TTHD.HOADON_FK,TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
										"			FROM ERP_XOAKHTRATRUOC_PHANBO_CHITIET TTHD \n"+
										"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XKHTT_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '7'  \n"+
										"           AND TT.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" \n"+
										"			GROUP BY TTHD.HOADON_FK, TTHD.SANPHAM_FK \n"+
															    
										"   		UNION ALL \n"+

										// BÙ TRỪ KHÁCH HÀNG
										"			SELECT BT_KH.HOADON_FK,BT_KH.SANPHAM_FK, SUM(BT_KH.THANHTOAN) AS THANHTOAN  \n"+
										"			FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_PHANBO_CHITIET BT_KH ON BT.PK_SEQ = BT_KH.BTKH_FK \n "+
										"			WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 7 AND BT.KH_CHUYENNO = "+this.nppId+" \n"+
										"			GROUP BY BT_KH.HOADON_FK, BT_KH.SANPHAM_FK \n"+
								
										"   		UNION ALL \n"+
										
										// THANH TOÁN HÓA ĐƠN
										"			SELECT TT_HD.HOADON_FK,TT_HD.SANPHAM_FK, SUM(TT_HD.THANHTOAN) AS THANHTOAN  \n"+
										"			FROM   ERP_THANHTOANHOADON TT INNER JOIN ERP_THANHTOANHOADON_HOADON_PHANBO TT_HD ON TT.PK_SEQ = TT_HD.TTHD_FK \n "+
										"			WHERE  TT.TRANGTHAI NOT IN (2) AND TT_HD.LOAIHD = 8 AND TT_HD.KHACHHANG_FK = "+this.nppId+" \n"+
										"			GROUP BY TT_HD.HOADON_FK, TT_HD.SANPHAM_FK \n"+
										
										"	 ) THU \n"+
										" GROUP BY THU.sanpham_fk, THU.HOADON_FK \n"+
										" ) DATHANHTOAN ON HD.PK_SEQ =  DATHANHTOAN.hoadon_fk AND HDSP.SANPHAM_FK = DATHANHTOAN.sanpham_fk \n"+
										" WHERE HD.PK_SEQ = " + hoadon.getId() ;
								}								
								else if( hoadon.getLoaihd().equals("2") ) // BÚT TOÁN TỔNG HỢP
								{									 
									query = 
										 " SELECT HD.SANPHAM_FK, 0 DONGIA, 0 CHIETKHAU , \n"+
										 " 	 ((ISNULL(HD.sotienAVAT,0) - ISNULL(DATHANHTOAN.TONGTHU,0)))  THANHTIEN, 1 SOLUONG, \n"+
										 " 	 0 ptchietkhau, 0 SOLUONGTT, 0 TIENCK, HD.sotienAVAT TIENAVAT \n"+
										
										 "	FROM ( \n" +
										 "			SELECT 	a.pk_seq, '' as kyhieu, cast(a.pk_seq as nvarchar(50)) sohoadon, ngaybuttoan as ngayhoadon, b.SP_FK SANPHAM_FK , SUM(isnull(NO,0)) as sotienAVAT, 1 as TYGIA \n" +
										 "			FROM 	ERP_BUTTOANTONGHOP a inner join ERP_BUTTOANTONGHOP_CHITIET b on a.PK_SEQ = b.BUTTOANTONGHOP_FK  \n" +
										 "			WHERE 	b.khachhang_fk = '" + this.nppId + "' and trangthai in (1) and b.NO > 0 and b.TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13100000' and CONGTY_FK = "+this.ctyId+") "+
										 " 			AND 	b.isNPP = "+this.isNPP+" AND a.CONGTY_FK = "+this.ctyId+ "  "+					
				                		 "			GROUP BY a.pk_seq, ngaybuttoan, b.SP_FK \n"+
										 " 	)HD \n" +
										" LEFT JOIN ( \n"+
										" SELECT  THU.sanpham_fk,THU.HOADON_FK, SUM(ISNULL(THU.TONGTHU,0)) TONGTHU \n"+
										" FROM ( \n"+
										// THU TIỀN HÓA ĐƠN
										"			SELECT TTHD.HOADON_FK, TTHD.sanpham_fk, SUM(TTHD.THANHTOAN) AS TONGTHU \n"+
										"			FROM ERP_THUTIEN_HOADON_SP TTHD \n"+
										"			INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI != 2 AND TT.BANGKE_FK IS NULL AND TTHD.LOAIHOADON = 2 AND TT.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" and isnull(TT.isNPP,0) = "+this.isNPP+" \n"+
										"			GROUP BY TTHD.sanpham_fk,TTHD.HOADON_FK \n"+

										"			UNION ALL \n"+	

										//TỔNG TIỀN XÓA KHÁCH HÀNG TRẢ TRƯỚC
										"			SELECT TTHD.HOADON_FK,TTHD.SANPHAM_FK , SUM(TTHD.THANHTOAN) AS THANHTOAN \n"+
										"			FROM ERP_XOAKHTRATRUOC_PHANBO_CHITIET TTHD \n"+
										"			INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XKHTT_FK = TT.PK_SEQ \n"+
										"			WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' AND TTHD.LOAIHD = '7'  \n"+
										"           AND TT.PK_SEQ != "+(this.id.trim().length() <=0 ? "0": this.id )+" and isnull(TT.isNPP,0) = "+this.isNPP+ " \n"+
										"			GROUP BY TTHD.HOADON_FK, TTHD.SANPHAM_FK \n"+
																				
										"	 ) THU \n"+
										" GROUP BY THU.sanpham_fk, THU.HOADON_FK \n"+
										" ) DATHANHTOAN ON HD.PK_SEQ =  DATHANHTOAN.hoadon_fk AND HD.SANPHAM_FK = DATHANHTOAN.sanpham_fk  \n"+
										" WHERE HD.PK_SEQ = " + hoadon.getId() ;
								}
								
								
								System.out.println("::: LAY HOA DON CHI TIET: " + query);
								ResultSet rsHD = db.get(query);
								double totalTIEN = 0;
								boolean exit = false;
								
								while(rsHD.next())
								{
									String sanpham_fk = rsHD.getString("SANPHAM_FK");
									double thanhTIEN = rsHD.getDouble("thanhtien");
									double dongia = rsHD.getDouble("DONGIA");
									double soluong = rsHD.getDouble("soluong");
									double tienAvat = rsHD.getDouble("TIENAVAT");
									int isHDG = 0;
									
									double tienPHANBO = 0;
									double soluongPHANBO = 0;									
									
									if(thanhTIEN >= 0) 
									{
										totalTIEN += thanhTIEN;
										if(totalTIEN <= Float.parseFloat(thanhtoan))
										{
											tienPHANBO = thanhTIEN;
										}
										else
										{
											tienPHANBO = Float.parseFloat(thanhtoan) - ( totalTIEN - thanhTIEN ) ;
											exit = true;
										}
									}
									else // PHÂN BỔ HÓA ĐƠN GIẢM
									{
										totalTIEN += thanhTIEN * (-1);
										if(totalTIEN <= ( Float.parseFloat(thanhtoan) * (-1)))
										{
											tienPHANBO = thanhTIEN;
										}
										else
										{
											tienPHANBO = ( Float.parseFloat(thanhtoan) * (-1) - ( totalTIEN - thanhTIEN*(-1) )) * (-1) ;
											exit = true;
										}
									}
									
									// PHÂN BỔ SỐ LƯỢNG
									// HÓA ĐƠN MỚI:
									System.out.println("tienPHANBO:"+tienPHANBO);
									System.out.println("thanhTIEN:"+thanhTIEN);
									
									soluongPHANBO = Math.round(tienPHANBO * soluong / tienAvat ) ;
									
									if(hoadon.getLoaihd().equals("2")) // BUT TOAN TONG HOP MAC DINH SO LUONG = 1
										soluongPHANBO = 1;
										
									query = " insert ERP_THUTIEN_HOADON_SP ( THUTIEN_FK, LOAIHOADON, HOADON_FK , thanhtoan, sanpham_fk, ptchietkhau)  \n" + 
										 	" values ("+tthdCurrent+",  '"+hoadon.getLoaihd()+"',"+hoadon.getId()+" , "+tienPHANBO+", "+ sanpham_fk + ", 0) ";
										 
									
									System.out.println("::: CHEN PHAN BO: " + query);
									if(!db.update(query))
									{
										this.msg = "Lỗi khi phân bổ: " + query;
										db.getConnection().rollback();
										return false;
									}
									
									if(exit)
										break;
								}
								rsHD.close();
								
							}	*/
							

						}
					}
				}
			}
			else if (this.ndId.equals("100002")) // THU KHÁC
			{

				for (int j = 0; j < this.dinhkhoancoList.size(); j++) {
					IDinhkhoanco dkc = this.dinhkhoancoList.get(j);

					if (Double.parseDouble(dkc.getSotien().replaceAll(",", "")) > 0) {
						String doituongdk = dkc.getDoituongdinhkhoan();
						String doituong_fk = dkc.getDoituongId();

						double sotienco = Double.parseDouble(dkc.getSotien().replaceAll(",", ""));
						double sotiencoNT = 0;

						if (!this.tienteId.equals("100000")) {
							sotiencoNT = Double.parseDouble(dkc.getSotien().replaceAll(",", ""));
							sotienco = Math.round(sotiencoNT * Double.parseDouble(this.tigia.replaceAll(",", "")));
						}

						if (doituong_fk.trim().length() <= 0) {
							doituongdk = "0";
							doituong_fk = "NULL";
						}
						
						String is_NPP = "0";
						if(doituongdk.equals("5")) 
						{
							//CHỖ NÀY ĐỂ PHÂN BIỆT LÀ NPP HAY KHÁCH HÀNG HAY NHÂN VIÊN
							
							if( doituong_fk.contains("-") )
							{
								String kh[] = doituong_fk.split("-");
								doituong_fk = kh[0];
								is_NPP = kh[1];
							}
						}
						
						query = "Insert into ERP_THUTIEN_DINHKHOANCO(THUTIEN_FK, TAIKHOAN_FK, DOITUONGDINHKHOAN, DOITUONG_FK, SOTIENNT, SOTIEN, ISNPP) "
								+ "values('" + tthdCurrent + "', '" + dkc.getTaikhoanId() + "', '" + doituongdk + "', "
								+ doituong_fk + ", '" + sotiencoNT + "' , '" + sotienco + "', "+is_NPP+")";

						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}

			// cập nhật mã chứng từ
			query = " update ERP_THUTIEN set machungtu = 'PT' + SUBSTRING(NGAYGHISO, 6, 2) + SUBSTRING(NGAYGHISO, 0, 5) + '-' + dbo.LaySoChungTu( " + tthdCurrent + " ) " + 
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
		} catch (SQLException e) {
			try {
				e.printStackTrace();
				db.getConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}

		return true;
	}

	public boolean updateTTHD() 
	{
		if(this.htttId.equals("100002"))
		{
			if(this.Sotaikhoan.length() <=0)
			{
				this.msg = "Vui lòng nhập số tài khoản";
				return false;
			}
			if(this.nganhangTen.length() <=0)
			{
				this.msg = "Vui lòng nhập tên ngân hàng";
				return false;
			}
			if(this.chinhanhTen.length() <=0)
			{
				this.msg = "Vui lòng nhập tên chi nhánh";
				return false;
			}
		}
		
			if(this.hoadonList.size() < 0)
			{
				this.msg = "Vui lòng chọn hóa đơn thu tiền";
				return false;
			}

		
		if(this.sotientt.trim().length() <= 0)
		{
			this.msg = "Vui lòng nhập số tiền thanh toán";
			return false;
		}
		
		
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
						

			String query = "";
			double Conlai= Double.parseDouble(this.sotientt.replace(",", "")) - Double.parseDouble(this.thuduoc.replace(",", ""));

				query = "update ERP_THUTIEN set SOTIENTHU= "+ this.thuduoc.replace(",", "") +" ,SOTIENTT= "+ this.thuduoc.replace(",", "") +",  "+
						" NGAYCHUNGTU = '" + this.ngaychungtu + "', GHICHU = N'" + this.noidungtt + "', " +
						" LYDONOP = N'" + this.lydonop + "', NGUOINOPTIEN = N'" + this.nguoinoptien + "' , BPKINHDOANH= N'"+ this.bpkinhdoanh +"' ,"+
						" NGAYSUA = '" +  getDateTime() + "', NGUOISUA = '" + this.userId + "',  NGANHANGTEN =N'"+ this.nganhangTen +"', HINHTHUCTT = '"+ this.htttId +"', " +
						" CHINHANHTEN =N'"+ this.chinhanhTen +"', SOTAIKHOAN='"+ this.Sotaikhoan +"', PHINGANHANG = "+ this.cpnganhang.replace(",", "") +" where PK_SEQ = '"  + this.id + "'";
				
			
			
			
			System.out.println(query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_THUTIEN: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			// cập nhật mã chứng từ
			query = " update ERP_THUTIEN set machungtu = 'PT' + SUBSTRING(NGAYGHISO, 6, 2) + SUBSTRING(NGAYGHISO, 0, 5) + '-' + dbo.LaySoChungTu( " + this.id + " ) " + 
					" where pk_seq = '" + this.id + "' ";
			
			System.out.println("[ErpThutien.createTTHD] error message:" + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_THUTIEN: " + query;
				System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_THUTIEN_HOADON where THUTIEN_FK = '" + this.id + "'";
			db.update(query);

			for(int i = 0; i < this.hoadonList.size(); i++)
			{
				IHoadon hoadon = this.hoadonList.get(i);

				String thanhtoan = hoadon.getThanhtoan().replaceAll(",", "");
				String avat = hoadon.getTongtiencoVAT().replaceAll(",", "");
				String sotienNT = hoadon.getSotienNT().replaceAll(",", "");
				String conlai = hoadon.getConlai().replaceAll(",", "");
				
				if(thanhtoan.length() > 0)
				{
					if(Float.parseFloat(thanhtoan) > 0)
					{							
							
							
							query = "Insert ERP_THUTIEN_HOADON(THUTIEN_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, CONLAI,NPP_FK) " +
									"values('" + this.id + "', '" + hoadon.getId() + "', '" + thanhtoan.trim() + "', '" + avat + "', '" + conlai.trim() + "', '" + hoadon.getNppId() + "')";
						
							System.out.println(query);
							
							if(!db.update(query))
							{
								this.msg = "Khong the tao moi ERP_THUTIEN_HOADON: " + query;
								System.out.println("[ErpThutien.createTTHD] error message: " + this.msg);
								db.getConnection().rollback();
								return false;
							}
						
					
					}
				}
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (SQLException e) 
		{
			try 
			{
				System.out.println(e.toString());
				db.getConnection().rollback();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public boolean chotTTHD(String userId)
	{
		try 
		{
			String ngaysua = getDateTime();
			
			db.getConnection().setAutoCommit(false);
			
			
			String query = "update ERP_THUTIEN set TRANGTHAI = '1', NGUOISUA = '" + userId + "', NGAYSUA = '" + ngaysua + "' where PK_SEQ = '"  + this.id + "'";
			System.out.println("1.Cap nhat ERP_THUTIEN: " + query);
			
			if(!db.update(query))
			{
				this.msg = "Khong the chot ERP_THUTIEN: " + query;
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			
			String ngayghiso="";
			query = "Select ngayghiso from ERP_THUTIEN where pk_seq = '"+ this.id +"' ";
			ResultSet rs = db.get(query);
			try {
				if(rs!= null){
					while(rs.next()){
						ngayghiso = rs.getString("ngayghiso");
					}
				rs.close();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

			
			//cap nhat trang thai hoa don la hoan tat neu da thanh toan het hoa don
			query = "select thanhtoan.hoadon_fk, hoadon.tongtienAVat as sotienAVAT, thanhtoan.SOTIENTT " +
					"from ( " +
						"select hoadon_fk, sum(SOTIENTT) as SOTIENTT " +
						"from ERP_THUTIEN_HOADON " +
						"where hoadon_fk in (select hoadon_fk from ERP_THUTIEN_HOADON where thutien_fk = '" + this.id + "') " +
						"group by hoadon_fk " +
					") thanhtoan " +
					"inner join ERP_HOADON hoadon on thanhtoan.hoadon_fk = hoadon.pk_seq " +
					"where hoadon.tongtienAVat - thanhtoan.SOTIENTT <= 0";
			
			System.out.println("__Check trang thai hoa don: " + query);
			ResultSet hoadonRs = db.get(query);
			if(hoadonRs != null)
			{
				while(hoadonRs.next())
				{
					String hoadon_fk = hoadonRs.getString("hoadon_fk");
					
					query = "update ERP_HOADON set trangthai = '4' where pk_seq = '" + hoadon_fk + "'";
					System.out.println("Cap nhat ERP_HOADON " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_HOADON: " + query;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
				}
				hoadonRs.close();
			}
															
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (java.sql.SQLException e) 
		{
			try 
			{
				db.getConnection().rollback();
				this.msg = "Lỗi khi chốt thu tiền: " + e.getMessage();
			}
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}

	public void init()
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " select tt.pk_seq, tt.ngaychungtu, tt.trangthai, tt.hinhthuctt,tt.phinganhang, " +
				       "  isnull(tt.sotaikhoan,'') as sotaikhoan, isnull(tt.nganhangten,'')as nganhangten, isnull(tt.chinhanhten,'') as chinhanhten, "+
					   "  isnull(tt.ghichu, '') as ghichu, tt.bpkinhdoanh,tt.lydonop,tt.nguoinoptien, "+
					   "  ISNULL(tt.sotientt, 0) AS SOTIENTT,  isnull(tt.sotienthu, 0) as thuduoc "+
						" from ERP_THUTIEN tt  "+
						" where tt.pk_seq = '" + this.id + "'";
		
		System.out.println("115.Khoi tao thu tien: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.bpkinhdoanh =rs.getString("bpkinhdoanh");
					this.nguoinoptien =rs.getString("nguoinoptien");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc = "" + rs.getString("thuduoc");
					this.lydonop =rs.getString("lydonop");
					this.htttId = rs.getString("hinhthuctt");
					
					// Chuyển khoản
					if(this.htttId.equals("100002"))
					{ 
						this.nganhangTen = rs.getString("nganhangten");
						this.chinhanhTen = rs.getString("chinhanhten");
						this.Sotaikhoan = rs.getString("sotaikhoan");
						this.cpnganhang =rs.getString("phinganhang");
					}
					
				


					
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		this.createRs();
	}

	
	public void initDisplay()
	{
		String query = 	"SELECT HD.PK_SEQ, HD.NGAYXUATHD ,cast(HD.PK_SEQ as nvarchar(20)) + '-' +HD.NGAYXUATHD as TEN  " +
			"FROM ERP_HOADON HD " +
			"WHERE HD.TRANGTHAI=2 ";
		this.hdTCRs = db.get(query);
		
		query = "select PK_SEQ, ten from ERP_HINHTHUCTHANHTOAN ";
		this.htttRs = db.get(query);

		String sql = "select pk_seq, ma + ', ' + ten as nppTen from NHAPHANPHOI where trangthai = '1' and pk_seq in " +util.quyen_npp(this.userId);
		this.nppRs = db.get(sql);
		
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		 query = " select tt.pk_seq, tt.ngaychungtu, tt.trangthai, tt.hinhthuctt,phinganhang,isnull(tt.sotaikhoan,'') as sotaikhoan,  "+
					   " isnull(tt.ghichu, '') as ghichu, tt.bpkinhdoanh,tt.lydonop,tt.nguoinoptien,isnull(tt.nganhangten,'')as nganhangten, isnull(tt.chinhanhten,'') as chinhanhten, "+
					   "  ISNULL(tt.sotientt, 0) AS SOTIENTT,  isnull(tt.sotienthu, 0) as thuduoc "+
						" from ERP_THUTIEN tt  "+
						" where tt.pk_seq = '" + this.id + "'";
		
		System.out.println("115.Khoi tao thu tien: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.bpkinhdoanh =rs.getString("bpkinhdoanh");
					this.nguoinoptien =rs.getString("nguoinoptien");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc = "" + rs.getString("thuduoc");
					this.lydonop =rs.getString("lydonop");
					this.htttId = rs.getString("hinhthuctt");
					
					// Chuyển khoản
					if(this.htttId.equals("100002")){ 
						this.nganhangTen = rs.getString("nganhangten");
						this.chinhanhTen = rs.getString("chinhanhten");
						this.Sotaikhoan = rs.getString("sotaikhoan");
						this.cpnganhang =rs.getString("phinganhang");
					}
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
		// INIT HOA DON
			query = 	
			" SELECT NPP.PK_SEQ AS NPPID, NPP.MA AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
			"	HD.NGAYXUATHD AS NGAYHOADON, \n" +
				"	(ISNULL(HD.TONGTIENAVAT,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n" +
				"	TT_HD.SOTIENTT\n" +
				" FROM ERP_THUTIEN_HOADON TT_HD \n" +
				" INNER JOIN ERP_HOADON HD ON TT_HD.HOADON_FK = HD.PK_SEQ  \n" +
			    " INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_FK  \n" +
			" LEFT JOIN	\n" +
			" ( 	\n" +
			"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
			"	FROM ERP_THUTIEN_HOADON TTHD \n" +
			"	INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" + 		
			"	WHERE TT.TRANGTHAI != '2' AND TTHD.THUTIEN_FK != '" + this.id + "' \n" + 		
			"	AND TTHD.HOADON_FK IN \n" +
			"		(SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + this.id + "' \n" + 						
			" ) 	\n" +	
			"	GROUP BY HOADON_FK \n" +
			" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n" +
			"WHERE TT_HD.THUTIEN_FK = '" + this.id + "'  \n" ;  
			
			  System.out.println("1.Query khoi tao hoa don Display: " + query);
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadon hd = null;
						while(rsHoadon.next())
						{
							
							String id = rsHoadon.getString("PK_SEQ");							
							String kyhieu = rsHoadon.getString("KYHIEU");
							String sohoadon = rsHoadon.getString("SOHOADON");
							String ngayhd = rsHoadon.getString("NGAYHOADON");
							String avat = "" +rsHoadon.getDouble("SOTIENVND");
							String nppid= rsHoadon.getString("NPPID");
							String manpp= rsHoadon.getString("MANPP");
							
							String dathanhtoan = "0";
							if(this.id.length() > 0)
							{								
								if(Math.abs(rsHoadon.getDouble("SOTIENTT")) > 0){
									dathanhtoan = "" + rsHoadon.getDouble("SOTIENTT");
								}
							}
							hd = new Hoadon(id, "", kyhieu, sohoadon, ngayhd, avat, "", dathanhtoan, "","","");
							hd.setNppId(nppid);
							hd.setNppMa(manpp);
							hdList.add(hd);
							
						}
						rsHoadon.close();
					} 
					catch (SQLException e) {}
				}
				this.hoadonList = hdList;
	}
	
	public void initPdf() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = " select a.pk_seq as tthdId, a.nguoinoptien, a.trangthai, a.ngaychungtu, a.soin, " +
					   " c.pk_seq as httt,  a.ghichu as ghichu, isnull(sotienthu, 0) as thuduoc, isnull(phinganhang, 0) as phinganhang " +
					   " from ERP_THUTIEN a inner join ERP_HINHTHUCTHANHTOAN c on a.HINHTHUCTT = c.pk_seq " +
					   " where a.pk_seq = '" + this.id + "' ";
		
		System.out.println("[ErpThutien.initPdf] query = " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.htttId = rs.getString("httt");
					this.noidungtt = rs.getString("ghichu");
					this.thuduoc =  rs.getString("thuduoc");
					this.nguoinoptien = rs.getString("nguoinoptien");
					this.Soin = rs.getString("soin");
					

				}
			} 
			catch (SQLException e) 
			{
				System.out.println("115..Exception: " + e.getMessage());
			}
		}
		
		
		
	}

	public void initUnc() 
	{
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
		String query = "select a.pk_seq, a.ngaychungtu, b.ten as nppTen, b.diachi, a.httt_fk, c.ten as nganhang_fk, a.chinhanh_fk, a.sotaikhoan, a.noidungtt, a.sotientt " +
						"from ERP_THANHTOANHOADON a inner join ERP_NHACUNGCAP b on a.npp_fk = b.pk_seq left join erp_nganhang c on a.nganhang_fk = c.pk_seq" +
						" where a.pk_seq = '" + this.id + "'";
		System.out.println("Khoi tao Unc: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngaychungtu = rs.getString("ngaychungtu");
					this.nppId = rs.getString("nppTen") + " --- " + rs.getString("diachi");
					this.htttId = rs.getString("httt_fk");

					this.noidungtt = rs.getString("noidungtt");
					this.sotientt = formatter.format(rs.getDouble("sotientt"));

				}
			} 
			catch (SQLException e) 
			{
				System.out.println("Exception: " + e.getMessage());
			}
		}
		
	}
	
	public void createRs() 
	{		
		
		String query = 	"";
		
		 query = 	"SELECT HD.PK_SEQ, HD.NGAYXUATHD ,cast(HD.PK_SEQ as nvarchar(20)) + '-' +HD.NGAYXUATHD as TEN  \n" +
					"FROM 	ERP_HOADONNPP HD \n" +
					"WHERE 	HD.TRANGTHAI in (2,4) AND HD.NPP_FK='"+ this.nppId +"' \n" +
					"UNION ALL \n"+
			 		"SELECT HD1.PK_SEQ, HD1.NGAYXUATHD ,cast(HD1.PK_SEQ as nvarchar(20)) + '-' +HD1.NGAYXUATHD as TEN  \n" +
					"FROM 	HOADON HD1 \n" +
					"WHERE 	HD1.TRANGTHAI in (2,4) AND HD1.NPP_FK='"+ this.nppId +"' AND ISNULL(HD1.LOAIHOADON,0) = 0 \n" ; // CHỈ LẤY HÓA ĐƠN TC , KO LAY HOA DON KM

		 this.hdTCRs = db.get(query);

		query = "select PK_SEQ, ten from ERP_HINHTHUCTHANHTOAN ";
		this.htttRs = db.get(query);
		
		String sql = "select pk_seq, isnull(maFAST,'') + '- ' + ten as nppTen from NHAPHANPHOI where trangthai = '1' and pk_seq in " +util.quyen_npp(this.userId);

		this.nppRs = db.get(sql);

		query = "";
		
		/*if( this.hoadonList.size() <= 0)
		{
//			NumberFormat formatter = new DecimalFormat("#,###,###");
				if(this.id.length() > 0)
				{
					query += 	"SELECT NPP.PK_SEQ AS NPPID, NPP.MA AS MANPP, HD.PK_SEQ, HD.KYHIEU AS KYHIEU, HD.SOHOADON, \n" + 
								"	HD.NGAYXUATHD AS NGAYHOADON, \n" +
			 	   				"	(ISNULL(HD.TONGTIENAVAT,0) - ISNULL(DATHU.TONGTHU, '0')) AS SOTIENVND, \n" +
			 	   				"	TT_HD.SOTIENTT\n" +
			 	   				" FROM ERP_THUTIEN_HOADON TT_HD \n" +
			 	   				" INNER JOIN ERP_HOADON HD ON TT_HD.HOADON_FK = HD.PK_SEQ  \n" +
			 	   			    " INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ = HD.NPP_FK  \n" +
								" LEFT JOIN	\n" +
								" ( 	\n" +
								"	SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS TONGTHU \n" + 		
								"	FROM ERP_THUTIEN_HOADON TTHD \n" +
								"	INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" + 		
								"	WHERE TT.TRANGTHAI != '2' AND TTHD.THUTIEN_FK != '" + this.id + "' \n" + 		
								"	AND TTHD.HOADON_FK IN \n" +
								"		(SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + this.id + "' \n" + 						
								" ) 	\n" +	
								"	GROUP BY HOADON_FK \n" +
								" )DATHU ON TT_HD.HOADON_FK = DATHU.HOADON_FK \n" +
								"WHERE TT_HD.THUTIEN_FK = '" + this.id + "'  \n" ;                      								
				query += " UNION ALL ";		
								
				}
				query += 	"(SELECT  HOADON.NPPID ,HOADON.MANPP , HOADON.PK_SEQ, HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n" +	
							" (ISNULL(HOADON.TONGTIENAVAT, 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) AS SOTIENVND, \n" +
							" 0 AS DATHANHTOAN \n" +
	
							"FROM ( \n" +		
							"	SELECT NPP.PK_SEQ as NPPID, NPP.MA as MANPP, HD.PK_SEQ, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, \n" + 
							"	HD.TONGTIENAVAT \n" +		
							"	FROM ERP_HOADON HD 	inner join NHAPHANPHOI NPP on HD.NPP_FK= NPP.PK_SEQ \n" +			
							"	WHERE  HD.TRANGTHAI = '2'  \n";
				if(this.nppIds.length() > 0)
				{
					query += "AND HD.NPP_FK in (" + this.nppIds + ") ";
				}
							
				if(this.id.length() > 0)
				{
					query += "AND HD.PK_SEQ NOT IN (SELECT HOADON_FK FROM ERP_THUTIEN_HOADON WHERE THUTIEN_FK = '" + this.id + "') \n";
					
				}
			
				query += 	") HOADON \n" + 
							"LEFT JOIN ( \n" +	
							"	SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n" +  	
							"	FROM  \n" +	
							"	( 	\n" +					
							"		SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n" +   		
							"		FROM ERP_THUTIEN_HOADON TTHD \n" +
							"		INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n" +   		
							"		WHERE  TT.TRANGTHAI NOT IN (2)\n"	; 
				
							
				if(this.id.trim().length() > 0){
					query += " 		AND TT.PK_SEQ  != '" + this.id + "' \n";
				}
			
				query +=	" 		GROUP BY HOADON_FK \n" +  	
							"	) HOADONDATT  \n" +
							"	GROUP BY HOADON_FK " +   
							")DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADON_FK \n" +
							" WHERE HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0  \n" ;
					if(this.hdIds.length() > 0)		
					{
						query += " AND HOADON.PK_SEQ in ( "+ this.hdIds +" )" ;
					}
					if(this.tungay.length() > 0)		
					{
						query += " AND HOADON.NGAYHOADON >= '"+ this.tungay +"' " ;
					}
					if(this.denngay.length() > 0)		
					{
						query += " AND HOADON.NGAYHOADON <= '"+ this.denngay +"' " ;
					}
				query += " )\n"; 
							 			
			
				query += " ORDER BY NPPID ASC,NGAYHOADON ASC ";
				
				
               System.out.println("1.Query khoi tao hoa don: " + query);
				ResultSet rsHoadon = db.get(query);
				List<IHoadon> hdList = new ArrayList<IHoadon>();
				if(rsHoadon != null)
				{
					try 
					{
						IHoadon hd = null;
						while(rsHoadon.next())
						{
							
							String id = rsHoadon.getString("PK_SEQ");							
							String kyhieu = rsHoadon.getString("KYHIEU");
							String sohoadon = rsHoadon.getString("SOHOADON");
							String ngayhd = rsHoadon.getString("NGAYHOADON");
							String avat = "" +rsHoadon.getDouble("SOTIENVND");
							String nppid= rsHoadon.getString("NPPID");
							String manpp= rsHoadon.getString("MANPP");
							
							String dathanhtoan = "0";
							if(this.id.length() > 0)
							{								
								if(Math.abs(rsHoadon.getDouble("SOTIENTT")) > 0){
									dathanhtoan = "" + rsHoadon.getDouble("SOTIENTT");
								}
							}
							hd = new Hoadon(id, "", kyhieu, sohoadon, ngayhd, avat, "", dathanhtoan, "","","");
							hd.setNppId(nppid);
							hd.setNppMa(manpp);
							hdList.add(hd);
							
						}
						rsHoadon.close();
					} 
					catch (SQLException e) {}
				}
				this.hoadonList = hdList;
				
			
		}*/
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void DBclose() 
	{
		
	}

	
	public String getNgayghiso() 
	{
		return this.ngayghiso;
	}

	public void setNgayghiso(String ngayghiso) 
	{
		this.ngayghiso = ngayghiso;
	}

	public String getNoidungId()
	{
		return this.ndId;
	}

	public void setNoidungId(String ndId) 
	{
		this.ndId = ndId;
	}

	public ResultSet getNoidungRs()
	{
		return this.ndRs;
	}

	public void setNoidungRs(ResultSet ndRs) 
	{
		this.ndRs = ndRs;	
	}

	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String soct) {
		
		this.sochungtu = soct;
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

	@Override
	public String getTigia_hoadon() {
		// TODO Auto-generated method stub
		return Tigia_hoadon;
	}

	@Override
	public void setTigia_hoadon(String _Tigia_hoadon) {
		// TODO Auto-generated method stub
		this.Tigia_hoadon=_Tigia_hoadon;
	}

	public String getDinhkhoanco() {
		return this.dinhkhoanco;
	}

	public void setDinhkhoanco(String dinhkhoanco) {
		this.dinhkhoanco= dinhkhoanco;
		
	}

	public String getDinhkhoancoId() {
		return this.dinhkhoancoId;
	}

	public void setDinhkhoancoId(String dinhkhoancoId) {
		this.dinhkhoancoId = dinhkhoancoId;
		
	}

	public String getDoiTuongDinhKhoan() {
		return this.DoiTuongDinhKhoan;
	}

	public void setDoiTuongDinhKhoan(String DoiTuongDinhKhoan) {
		this.DoiTuongDinhKhoan= DoiTuongDinhKhoan;
		
	}

	public String getDoituongdinhkhoanId() {
		return this.DoituongdinhkhoanId;
	}

	public void setDoituongdinhkhoanId(String DoituongdinhkhoanId) {
		this.DoituongdinhkhoanId = DoituongdinhkhoanId;
		
	}

	public String getMaTenDoiTuongDinhKhoan() {
		return this.MaTenDoiTuongDinhKhoan;
	}

	public void setMaTenDoiTuongDinhKhoan(String MaTenDoiTuongDinhKhoan) {
		this.MaTenDoiTuongDinhKhoan= MaTenDoiTuongDinhKhoan;
		
	}

	@Override
	public String getHdId() {
		// TODO Auto-generated method stub
		return this.hdId;
	}

	@Override
	public void setHdId(String hdId) {
		this.hdId =hdId;
		
	}

	@Override
	public String getLydonop() {
		return this.lydonop;
	}

	@Override
	public void setLydonop(String lydonop) {
		this.lydonop = lydonop;
		
	}

	public String getNppIds() {
		
		return this.nppIds;
	}

	
	public void setNppIds(String nppIds ) {
		
		this.nppIds = nppIds ;
	}
	
	public String getHdIds() {
		
		return this.hdIds;
	}

	
	public void setHdIds(String hdIds ) {
		
		this.hdIds = hdIds ;
	}

	@Override
	public String getNganhangTen() {
		return this.nganhangTen;
	}

	@Override
	public void setNganhangTen(String nganhangTen) {
		this.nganhangTen= nganhangTen;
		
	}

	@Override
	public String getChinhanhTen() {
		return this.chinhanhTen;
	}

	@Override
	public void setChinhanhTen(String cnTen) {
		this.chinhanhTen= cnTen;		
		
	}

	@Override
	public String getSotaikhoan() {
		// TODO Auto-generated method stub
		return this.Sotaikhoan;
	}

	@Override
	public void setSotaikhoan(String sotaikhoan) {
		this.Sotaikhoan= sotaikhoan;
		
	}

	public String getSoin() {
		// TODO Auto-generated method stub
		return this.Soin;
	}

	@Override
	public void setSoin(String soin) {
		this.Soin= soin;
		
	}
	
	
}
