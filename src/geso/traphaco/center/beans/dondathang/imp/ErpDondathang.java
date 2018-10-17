package geso.traphaco.center.beans.dondathang.imp;

import geso.traphaco.center.beans.dondathang.IErpDondathang;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.WebService;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

public class ErpDondathang implements IErpDondathang
{
	String userId;
	String id;
	
	String mahopdong;
	String ngayyeucau;
	String ngaydenghi;
	String ghichu;

	String msg;
	String trangthai;
	
	String loaidonhang;  //0 đơn đặt hàng, 1 đơn hàng khuyến mại ứng hàng, 3 đơn hàng khuyến mại tích lũy, 
						//4 đơn hàng trưng bày, 4 đơn hàng khác, 5 đơn nội bộ
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
	
	String schemeId;
	ResultSet schemeRs;
	
	Hashtable<String, String> scheme_soluong;
	Hashtable<String, String> sanpham_soluong;
	Hashtable<String, String> sanpham_soluongDAXUAT;

	String[] spId;
	String[] spMa;
	String[] spTen;
	String[] spDonvi;
	String[] spSoluong;
	String[] spGianhap;
	String[] spChietkhau;
	String[] spVAT;
	String[] spSCheme;
	String[] spTrongluong;
	String[] spThetich;
	
	String[] dhCk_diengiai;
	String[] dhCk_giatri;
	String[] dhCk_loai;
	String[] spchietkhaugia;
	String[] spIskhongthue;

	ResultSet congnoRs;
	
	dbutils db;
	String isdhk;
	String iskm;
	String tungay;
	String denngay;
	String ETC;
	String danhmucKH;
	
	Hashtable<String, String> schemeNoibo;
	Hashtable<String, String> schemeNoibo_vat;
	Hashtable<String, String> schemeNoibo_bvat;
	
	String ishm;
	String phanloai;

	String[] sptienvat;
	
	public String getIshm() {
		return ishm;
	}

	public void setIshm(String ishm) {
		this.ishm = ishm;
	}
	
	public String[] getSptienvat() {
		return sptienvat;
	}

	public void setSptienvat(String[] sptienvat) {
		this.sptienvat = sptienvat;
	}

	public ErpDondathang()
	{
		this.id = "";
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime_CongMot();
		this.tungay = "";
		this.denngay = "";
		this.ngaydenghi = "";
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
		this.schemeId = "";
		
		this.dhCk_diengiai = new String[]{"Chiết khấu ngay", "Chiết khấu tháng", "Chiết khấu quý", "Khác"};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.scheme_soluong = new Hashtable<String, String>();
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		
		this.iskm="0";
		this.db = new dbutils();
		this.isdhk="0";
		this.isgia="0";
		this.ETC = "";
		
		this.mahopdong = "";
		this.gsbhId = "";
		this.ddkdId = "";
		this.danhmucKH = "0";
		
		this.schemeNoibo = new Hashtable<String, String>();
		this.schemeNoibo_bvat = new Hashtable<String, String>();
		this.schemeNoibo_vat = new Hashtable<String, String>();
		this.phanloai = "";
		this.ishm="";
	}
	
	public ErpDondathang(String id)
	{
		this.id = id;
		this.ngayyeucau = getDateTime();
		this.ngaydenghi = getDateTime_CongMot();
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
		this.schemeId = "";

		this.dhCk_diengiai = new String[]{"Chiết khấu ngay", "Chiết khấu tháng", "Chiết khấu quý", "Khác"};
		this.dhCk_giatri = new String[]{"0", "0", "0", "0"};
		this.dhCk_loai = new String[]{"0", "0", "0", "0"};
		
		this.scheme_soluong = new Hashtable<String, String>();
		this.sanpham_soluong = new Hashtable<String, String>();
		this.sanpham_soluongDAXUAT = new Hashtable<String, String>();
		this.iskm="0";
		this.db = new dbutils();
		this.isdhk="0";
		this.isgia="0";
		this.ETC = "";
		
		this.mahopdong = "";
		this.gsbhId = "";
		this.ddkdId = "";
		this.danhmucKH = "0";
		
		this.schemeNoibo = new Hashtable<String, String>();
		this.schemeNoibo_bvat = new Hashtable<String, String>();
		this.schemeNoibo_vat = new Hashtable<String, String>();
		this.phanloai = "";
		this.ishm="";
	}
	public String[] getSpIskhongthue() {
		return spIskhongthue;
	}

	public void setSpIskhongthue(String[] spIskhongthue) {
		this.spIskhongthue = spIskhongthue;
	}
	public Hashtable<String, String> getSchemeNoibo_bvat() {
		return schemeNoibo_bvat;
	}

	public void setSchemeNoibo_bvat(Hashtable<String, String> schemeNoibo_bvat) {
		this.schemeNoibo_bvat = schemeNoibo_bvat;
	}

	public Hashtable<String, String> getSchemeNoibo_vat() {
		return schemeNoibo_vat;
	}

	public void setSchemeNoibo_vat(Hashtable<String, String> schemeNoibo_vat) {
		this.schemeNoibo_vat = schemeNoibo_vat;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getNgayyeucau() 
	{
		return this.ngayyeucau;
	}

	public void setNgayyeucau(String ngayyeucau) 
	{
		this.ngayyeucau = ngayyeucau;
	}

	public String getKhoNhapId()
	{
		return this.khoNhanId;
	}

	public void setKhoNhapId(String khonhaptt) 
	{
		this.khoNhanId = khonhaptt;
	}

	public ResultSet getKhoNhapRs() 
	{
		return this.khoNhanRs;
	}

	public void setKhoNHapRs(ResultSet khonhapRs) 
	{
		this.khoNhanRs = khonhapRs;
	}
	
	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean createNK() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.ngaydenghi = this.ngayyeucau;
			//this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			//return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho";
			return false;
		}
		
		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 && ( this.loaidonhang.equals("0") || this.loaidonhang.equals("5") ) )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					System.out.println("spSCheme[i]: " + spSCheme[i]);
					if(spGianhap[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(!this.loaidonhang.equals("4"))
					{
						if(spDonvi[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
						{
							this.msg = "Bạn phải nhập đơn vị của sản phẩm ( " + spTen[i] + " ) hoặc SCheme của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					/*if(this.loaidonhang.equals("4"))
					{
						if(spSCheme[i].trim().length() <= 0 )
						{
							this.msg = "Bạn phải nhập SCheme của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}*/
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0  )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[j].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String kbh_fk = this.kbhId.trim().length() <= 0 ? "NULL" : this.kbhId.trim();
			String gsbh_fk = this.gsbhId.trim().length() <= 0 ? "NULL" : this.gsbhId.trim();
			String ddkd_fk = this.ddkdId.trim().length() <= 0 ? "NULL" : this.ddkdId.trim();
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			String danhmucKH = this.danhmucKH.trim().length() <= 0 ? "NULL" : this.danhmucKH.trim();
			
			String query = " insert ERP_Dondathang(ishm,ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua,tructhuoc_fk,isKM,isdhkhac,isingia, isETC, gsbh_fk, ddkd_fk, hopdong_fk, danhmucKH, phanloai ) " +
						   " select "+this.ishm+",'" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', " + kbh_fk + ", '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "',1,'"+this.iskm+"',"+this.isdhk+","+this.isgia+", '" + this.ETC + "', " + gsbh_fk + ", " + ddkd_fk + ", " + 
						   "	( select pk_seq from ERP_HOPDONG where MaHopDong = '" + this.mahopdong + "' ), " + danhmucKH + ", '" + this.phanloai + "'	 ";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_Dondathang') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			System.out.println("DDH ID: " + this.id);
			
			//TRANSACTION LOG ERP
			Utility util = new Utility();
			
			String loaichungtu = "Đơn đặt hàng"; 
			String chungtuId = this.id; 
			String transactionId = util.createTransactionId();
			
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					String ck = "0";
					String ckg = "0";
					if(spChietkhau != null && spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					if(spchietkhaugia != null && spchietkhaugia[i].trim().length() > 0)
						ckg = spchietkhaugia[i].trim().replaceAll(",", "");
					
					String thueVAT = "0";
					System.out.println("this.spVAT[i]: ");
					if(this.spVAT !=null && this.spVAT[i] !=null && this.spVAT[i].trim().trim().length() > 0)
						thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					
					if( !this.loaidonhang.equals("4") && spSCheme[i].trim().length() <= 0 )
					{
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, soluongCHUAN,ptchietkhaugia,IS_KHONGTHUE, kho_fk ) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), " + 
								" 	'" + spSoluong[i].replaceAll(",", "") + "' * dbo.LayQuyCach( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) " +
								","+ckg+",'"+this.spIskhongthue[i]+"', '" + this.khoNhanId + "' from ERP_SANPHAM a where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					else if( this.loaidonhang.equals("4") ) //ĐƠn hàng KM
					{
						/*spGianhap[i] = "0";
						ck = "0";
						thueVAT = "0";*/
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, soluongCHUAN, dongia, chietkhau, thueVAT, dvdl_fk, scheme ,IS_KHONGTHUE, kho_fk) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), N'" + spSCheme[i] + "','"+this.spIskhongthue[i]+"', '" + this.khoNhanId + "' " +
								"from ERP_SANPHAM where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					
					//INSERT CHI TIET
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i] + "__" + spSCheme[i] ) )
							{
								String[] _sp = this.mySplit( key, "__" );
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								if(!_soluongCT.equals("0"))
								{
									//QUY VỀ SỐ LƯỢNG CHUẨN LƯU KHO
									query = " select '" + _soluongCT.replaceAll(",", "") + "' * dbo.LayQuyCach( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as soluongCHUAN, " + 
											"	dbo.LayQuyCach_DVBan( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as quyveDVBAN	" +
											" from ERP_SANPHAM a where ma = '" + spMa[i] + "' ";
									
									double soluongCHUAN = Double.parseDouble(_soluongCT.replaceAll(",", ""));
									double quyveDVBAN = 0;
									ResultSet rs = db.get(query);
									if(rs != null )
									{
										if( rs.next() )
										{
											soluongCHUAN = rs.getDouble("soluongCHUAN");
											quyveDVBAN = rs.getDouble("quyveDVBAN");
										}
										rs.close();
									}
									
									//Bên đầu bán, lúc nhập chỉ có số lô, ngày hết hạn, vị trí, các thông số khác sẽ đề xuất chỗ này
									//List<ErpThongtinkho> chitiet = util.DeXuatKho(db, this.ngayyeucau, this.nppId, this.khoNhanId, spMa[i], _sp[2],  _sp[3], _sp[4], Double.parseDouble(_soluongCT.replaceAll(",", "")) );
									List<ErpThongtinkho> chitiet = util.DeXuatKho(db, this.ngayyeucau, this.nppId, this.khoNhanId, spMa[i], _sp[2],  _sp[3], _sp[4], _sp[5], _sp[6], _sp[7], _sp[8], _sp[9], soluongCHUAN );
									if( chitiet == null )
									{
										this.msg = "Lỗi đề xuất sản phẩm, vui lòng liên hệ admin để xử lý.";
										db.getConnection().rollback();
										return false;
									}
									
									totalCT += Double.parseDouble(_soluongCT);
									
									for( int j = 0; j < chitiet.size(); j++  )
									{
										ErpThongtinkho tt = chitiet.get(j);
										
										/*query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, mame, mathung, soluong ) " +
												"select '" + this.id + "', pk_seq, N'" + _sp[1] + "', N'" + _sp[2] + "', '" + _sp[3] + "', '" + _sp[4] + "', '" + _sp[5] + "', '" + _sp[6] + "', '" + _soluongCT.replaceAll(",", "") + "' " +
												"from SANPHAM where MA = '" + spMa[i] + "' ";*/
										String scheme = "";
										if(this.loaidonhang.equals("4"))
										{
											scheme = spSCheme[i];
										}
										query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, nsx_fk, soluong, soluongCHUAN,IS_KHONGTHUE,TIENHANG,TIENVAT,TIENHANGSAUVAT, inchung, kho_fk ) " +
												"select '" + this.id + "', '" + tt.spId + "', N'"+scheme+"', N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', '" + tt.phieudt + "', '" + tt.phieueo + "', " + tt.nsxId + ", ( " + quyveDVBAN + " * " + tt.soluong + " ), '" + tt.soluong + "','"+this.spIskhongthue[i]+"' "+
												" ,ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0) AS TIENHANG, ROUND(((ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0))*("+thueVAT+")/100),0) AS TIENVAT"+
												", (ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0) +ROUND(((ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0))*("+thueVAT+")/100),0) )AS TIENHANGSAUVAT, null, '" + this.khoNhanId + "' ";
										
										System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										//this.msg = util.Update_KhoTT(this.ngaydenghi, "HO / Bán đối tác", db, this.khoNhanId, "( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' )", _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
										this.msg = util.Update_KhoTT_NEW(this.ngaydenghi, "HO / Bán đối tác", db, tt.khoId, tt.spId, tt.solo, tt.ngayhethan, tt.ngaynhapkho, 
														tt.mame, tt.mathung, tt.vitriId, tt.maphieu, tt.phieudt, tt.phieueo, tt.MARQ, tt.hamluong, tt.hamam, "", "", 0, tt.soluong, -1 * tt.soluong, 
														Double.parseDouble(spGianhap[i].replaceAll(",", "")), "", tt.nsxId	);
										
										if( this.msg.trim().length() > 0 )
										{
											
											db.getConnection().rollback();
											return false;
										}
										String querycheck = "SELECT ISNULL(BOOKED,0) as BOOKED FROM ERP_KHOTT_SP_CHITIET WHERE KHOTT_FK = '"+ tt.khoId+"' and SANPHAM_FK ='"+ tt.spId+"' and isnull(SOLO,'') = '"+ tt.solo+"' \n"+
										"   and NGAYNHAPKHO = '"+ tt.ngaynhapkho+"' and NGAYHETHAN = '"+ tt.ngayhethan+"' and isnull(MAME,'') = '"+ tt.mame+"'\n"+
										"   and isnull(MAPHIEU,'') = '"+ tt.maphieu+"' and isnull(MATHUNG,'') = '"+ tt.mathung+"' and isnull(MAPHIEUDINHTINH,'') = '"+ tt.phieudt+"' \n"+
										"   and isnull(PHIEUEO,'') = '"+ tt.phieueo+"' and isnull(HAMAM,0) ='"+ tt.hamam+"' and isnull(HAMLUONG,100)  ='"+ tt.hamluong+"' and isnull(BIN_FK,0) = '"+ tt.vitriId+"' \n"+
										" \n";
										ResultSet rscheck = db.get(querycheck);
										if(rscheck.next())
										{
											if(rscheck.getDouble("BOOKED") <tt.soluong)
											{
												this.msg ="Lỗi trong quá trình booked kho. Vui lòng kiểm tra lại hoặc liên hệ admin để xửa lý";
												db.getConnection().rollback();
												return false;
											}
										}
										rscheck.close();
									}
								}
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
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
						query = "insert ERP_DONDATHANG_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI) " +
								"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "' ) ";
						
						System.out.println("1.Insert DH - CK: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			//CHECK PHÂN BỔ TỔNG
			/*query = "  select MA, TEN, soluong, dasudung from " + 
					"  ( " + 
					"  	select PB.*, ISNULL( (  " + 
					"  					select SUM( b.soluong ) as soluong " + 
					"  					from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " + 
					"  					where a.TRANGTHAI != 3 and a.NPP_FK = '" + this.nppId + "' and b.sanpham_fk = PB.sanpham_fk and a.NgayDonHang >= PB.tungay and a.NgayDonHang <= PB.denngay " + 
					"  				 ), 0 ) as dasudung  " + 
					"  	from " + 
					"  	( " + 
					"  		select a.tungay, a.denngay, b.sanpham_fk, c.MA, c.TEN, SUM(b.soluong) as soluong  " + 
					"  		from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk " + 
					"  				inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " + 
					"  		where a.TRANGTHAI = '1' and a.doituongId = '" + this.nppId + "' " + 
					"  		group by a.tungay, a.denngay, b.sanpham_fk, c.MA, c.TEN  " + 
					"  	) " + 
					"  	PB " + 
					"  ) " + 
					"  DT where DT.soluong - DT.dasudung <= 0 ";
			System.out.println("::: CHECK PHÂN BỔ TỔNG:  " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				String spVUOT = "";
				while( rs.next() )
				{
					spVUOT += rs.getString("TEN") + ", ";
				}
				rs.close();
				
				if( spVUOT.trim().length() > 0 )
				{
					this.msg = "Các mặt hàng sau: " + spVUOT + " đã vượt quá số lượng được phân bổ";
					db.getConnection().rollback();
					return false;
				}
			}*/
			
			//CHECK PHÂN BỔ CT
			/*query = "  select MA, TEN, SOLO, soluong, dasudung from " + 
					"  ( " + 
					"  	select PB.*, ISNULL( (  " + 
					"  					select SUM( b.soluong ) as soluong " + 
					"  					from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk " + 
					"  					where a.TRANGTHAI != 3 and dbo.trim(scheme) = '' and a.NPP_FK = '" + this.nppId + "' and b.sanpham_fk = PB.sanpham_fk and b.solo = PB.solo and a.NgayDonHang >= PB.tungay and a.NgayDonHang <= PB.denngay " + 
					"  				 ), 0 ) as dasudung  " + 
					"  	from " + 
					"  	( " + 
					"  		select a.tungay, a.denngay, b.sanpham_fk, b.solo, c.MA, c.TEN, SUM(b.soluong) as soluong  " + 
					"  		from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk " + 
					"  				inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " + 
					"  		where a.TRANGTHAI = '1' and a.doituongId = '" + this.nppId + "' " + 
					"  		group by a.tungay, a.denngay, b.sanpham_fk,  b.solo, c.MA, c.TEN  " + 
					"  	) " + 
					"  	PB " + 
					"  ) " + 
					"  DT where DT.soluong - DT.dasudung <= 0 ";
			System.out.println("::: CHECK PHÂN BỔ CHI TIET:  " + query);
			rs = db.get(query);
			if( rs != null )
			{
				String spVUOT = "";
				while( rs.next() )
				{
					spVUOT += rs.getString("TEN") + " - " + rs.getString("SOLO") + ", ";
				}
				rs.close();
				
				if( spVUOT.trim().length() > 0 )
				{
					this.msg = "Các mặt hàng sau: " + spVUOT + " đã vượt quá số lượng được phân bổ";
					db.getConnection().rollback();
					return false;
				}
			}*/
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	private String[] mySplit(String key, String operator) 
	{
		boolean exit = false;
		if( key.endsWith(operator) )
		{
			key = key + " ";
			exit = true;
		}
		
		String[] arr = key.split(operator);
		if( arr.length > 0 && exit )
			arr[ arr.length - 1 ] = arr[ arr.length - 1 ].trim();
		
		return arr;
	}

	/*public static void main(String[] arg)
	{
		ErpDondathang dh = new ErpDondathang();
		String str = "1ACE1__ __4033__2016-05-31__2016-06-01____";
		
		String[] arr = dh.mySplit(str);
		for( int i = 0; i < arr.length; i++ )
			System.out.println("::: " + i + " : " + arr[i]);
		
	}*/
	
	public boolean updateNK(String checkKM) 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày nhập kho";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.ngaydenghi = this.ngayyeucau;
			//this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			//return false;
		}

		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho";
			return false;
		}
		
		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 && ( this.loaidonhang.equals("0") || this.loaidonhang.equals("5") ) )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối đặt hàng";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho đặt hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(spGianhap[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
					{
						this.msg = "Bạn phải nhập đơn giá của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					if(!this.loaidonhang.equals("4"))
					{
						if(spDonvi[i].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
						{
							this.msg = "Bạn phải nhập đơn vị của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}
					/*if(this.loaidonhang.equals("4"))
					{
						if(spSCheme[i].trim().length() <= 0 )
						{
							this.msg = "Bạn phải nhập SCheme của sản phẩm ( " + spTen[i] + " )";
							return false;
						}
					}*/
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0  )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[j].trim().length() <= 0 && spSCheme[i].trim().length() <= 0 )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}
			
			//NEU LA CAP NHAT, MA THAY DOI SP HOAC SO LUONG THI PHAI AP LAI KM
			if(checkKM.equals("1"))
			{
				boolean coKM = false;
				String sql = "select count(*) as soDONG from ERP_DONDATHANG_CTKM_TRAKM where DONDATHANGID = '" + this.id + "' ";
				ResultSet rs = db.get(sql);
				if(rs != null)
				{
					try 
					{
						if(rs.next())
						{
							if(rs.getInt("soDONG") > 0 )
								coKM = true;
						}
						rs.close();
					}
					catch (Exception e) {}
				}
				
				if(coKM)
				{
					for(int i = 0; i < spMa.length; i++)
					{
						if( spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 && spDonvi[i].trim().length() > 0 && spSCheme[i].trim().length() <= 0 )
						{
							sql = "select count(*) as soDONG from ERP_DONDATHANG_SANPHAM " +
								 "where dondathang_fk = '" + this.id + "' and SOLUONG = '" + spSoluong[i].trim().replaceAll(",", "") + "' and sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa[i].trim() + "' ) " +
								 		" and dvdl_fk = ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ";
							//System.out.println("CHECK SP: " + sql);
							int soDONG = 0;
							rs = db.get(sql);
							try 
							{
								if(rs.next())
								{
									soDONG = rs.getInt("soDONG");
								}
								rs.close();
							} 
							catch (Exception e) {}
							
							if(soDONG <= 0)
							{
								this.msg = "Khi thay đổi thông tin sản phẩm, số lượng, đơn vị trong đơn hàng, bạn phải bấm áp lại khuyến mại.";
								return false;
							}
							
						}
					}
				}
			}
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String kbh_fk = this.kbhId.trim().length() <= 0 ? "NULL" : this.kbhId.trim();
			String gsbh_fk = this.gsbhId.trim().length() <= 0 ? "NULL" : this.gsbhId.trim();
			String ddkd_fk = this.ddkdId.trim().length() <= 0 ? "NULL" : this.ddkdId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();	
			String danhmucKH = this.danhmucKH.trim().length() <= 0 ? "NULL" : this.danhmucKH.trim();
			
			String query =	" Update ERP_Dondathang set ishm="+this.ishm+",IsKM='"+this.iskm+"',ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
							" 	 danhmucKH = " + danhmucKH + ",gsbh_fk = " + gsbh_fk + ", ddkd_fk = " + ddkd_fk + ", dvkd_fk = '" + this.dvkdId + "', kbh_fk = " + kbh_fk + ", npp_fk = '" + this.nppId + "', kho_fk = '" + this.khoNhanId + "', ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "', phanloai = '" + this.phanloai + "', isdhkhac= "+this.isdhk+",isingia="+this.isgia + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//TRANSACTION LOG ERP
			Utility util = new Utility();
			
			String loaichungtu = "Đơn đặt hàng"; 
			String chungtuId = this.id; 
			String transactionId = util.createTransactionId();
			
			//Tăng kho ngược lại trước khi xóa
			query = "  select b.NgayDonHang, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, " + 
					" 		a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, isnull(a.bin_fk, 0) as bin_fk, a.phieudt, a.phieueo, isnull(a.nsx_fk, 0) as nsx_fk, SUM( a.SoLuongCHUAN ) as soluong  " + 
					"  from ERP_DONDATHANG_SANPHAM_CHITIET a inner join ERP_DONDATHANG b on a.DonDatHang_FK = b.PK_SEQ " + 
					"  where b.PK_SEQ = '" + this.id + "' " + 
					"  group by b.NgayDonHang, a.Kho_FK, a.SanPham_fk, a.SoLo, a.NgayHetHan, a.ngaynhapkho, a.mame, a.mathung, a.maphieu, a.marq, a.hamluong, a.hamam, a.bin_fk, a.nsx_fk, a.phieudt, a.phieueo ";
			
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			if( rs != null )
			{
				while( rs.next() )
				{
					String khoId = rs.getString("Kho_FK");
					String spId = rs.getString("SanPham_fk");
					String solo = rs.getString("SoLo");
					String ngayhethan = rs.getString("NgayHetHan");
					String ngaynhapkho = rs.getString("ngaynhapkho");
					
					String loaidoituong = "";
					String doituongId = "";
					
					String mame = rs.getString("mame");
					String mathung = rs.getString("mathung");
					String bin_fk = rs.getString("bin_fk");
					
					String maphieu = rs.getString("maphieu");
					String phieudt = rs.getString("phieudt");
					String phieueo = rs.getString("phieueo");
					
					String marq = rs.getString("marq");
					String hamluong = rs.getString("hamluong");
					String hamam = rs.getString("hamam");
					String nsx = rs.getString("nsx_fk");
					double soluong = rs.getDouble("soluong");
					
					msg = util.Update_KhoTT_NEW(rs.getString("NgayDonHang"), "Cập nhật DH - Tăng kho ngược lại trước khi xóa ", db, khoId, spId, solo, ngayhethan, ngaynhapkho, 
							mame, mathung, bin_fk, maphieu, phieudt, phieueo, marq, hamluong, hamam, loaidoituong, doituongId, 0, -1 * soluong, soluong, 0, "", nsx	);
					
					if( msg.trim().length() > 0 )
					{
						db.getConnection().rollback();
						return false;
					}
				}
				rs.close();
			}
			
			query = "delete ERP_DONDATHANG_SANPHAM_CHITIET where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_SANPHAM_CHITIET " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANG_CHIETKHAU where Dondathang_fk = '" + this.id + "' ";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_CHIETKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					String ck = "0";
					if(spChietkhau != null && spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					String ckg = "0";
					if(spchietkhaugia != null && spchietkhaugia[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");

					String kho = this.khoNhanId, inchung = "null";
					if(spSCheme[i].trim().length() > 0)
					{
						query = "select inchung, kho_fk from ctkhuyenmai where scheme = '" + spSCheme[i] + "'";
						System.out.println("aaa = " + query);
						ResultSet rskm = db.get(query);
						if(rskm != null && rskm.next())
						{
							kho = rskm.getString("kho_fk");
							inchung = rskm.getString("inchung");
							rskm.close();
						}
					}
					
					String thueVAT = "0";
					if(this.spVAT !=null && this.spVAT[i]!=null && this.spVAT[i].trim().trim().length() > 0)
						thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					
					if( !this.loaidonhang.equals("4") && spSCheme[i].trim().length() <= 0 )
					{
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, chietkhau, thueVAT, dvdl_fk, soluongCHUAN,ptchietkhaugia ,IS_KHONGTHUE, kho_fk) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), " + 
								" 	'" + spSoluong[i].replaceAll(",", "") + "' * dbo.LayQuyCach( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) " +
								","+ckg+",'"+this.spIskhongthue[i]+"', '" + kho + "' from ERP_SANPHAM a where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					else if( this.loaidonhang.equals("4") ) //ĐƠn hàng KM
					{
						/*spGianhap[i] = "0";
						ck = "0";
						thueVAT = "0";*/
						
						query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, soluongCHUAN, dongia, chietkhau, thueVAT, dvdl_fk, scheme,IS_KHONGTHUE, kho_fk ) " +
								"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), N'" + spSCheme[i] + "','"+this.spIskhongthue[i]+"', '" + kho + "' " +
								"from ERP_SANPHAM where MA = '" + spMa[i].trim() + "' ";
						
						System.out.println("1.Insert NK - SP: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
					System.out.println("vo day");
					//INSERT CHI TIET
					if(this.sanpham_soluong != null)
					{
						Enumeration<String> keys = this.sanpham_soluong.keys();
						double totalCT = 0;
						
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							
							if(key.startsWith( spMa[i] + "__" + spSCheme[i] ) )
							{
								String[] _sp = this.mySplit( key, "__" );
								
								String _soluongCT = "0"; 
								if(this.sanpham_soluong.get(key) != null)
								{
									_soluongCT = this.sanpham_soluong.get(key).replaceAll(",", "");
								}
								
								if(!_soluongCT.equals("0"))
								{
									//QUY VỀ SỐ LƯỢNG CHUẨN LƯU KHO
									query = " select '" + _soluongCT.replaceAll(",", "") + "' * dbo.LayQuyCach( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as soluongCHUAN, " + 
											"	dbo.LayQuyCach_DVBan( a.pk_seq, null, ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ) ) as quyveDVBAN	" +
											" from ERP_SANPHAM a where ma = '" + spMa[i] + "' ";
									System.out.println("Quyve: "+ query);
									double soluongCHUAN = Double.parseDouble(_soluongCT.replaceAll(",", ""));
									double quyveDVBAN = 0;
									rs = db.get(query);
									if(rs != null )
									{
										if( rs.next() )
										{
											soluongCHUAN = rs.getDouble("soluongCHUAN");
											quyveDVBAN = rs.getDouble("quyveDVBAN");
										}
										rs.close();
									}
									
									//Bên đầu bán, lúc nhập chỉ có số lô, ngày hết hạn, vị trí, các thông số khác sẽ đề xuất chỗ này
									//List<ErpThongtinkho> chitiet = util.DeXuatKho(db, this.ngayyeucau, this.nppId, this.khoNhanId, spMa[i], _sp[2],  _sp[3], _sp[4], Double.parseDouble(_soluongCT.replaceAll(",", "")) );
									List<ErpThongtinkho> chitiet = util.DeXuatKho(db, this.ngayyeucau, this.nppId, kho, spMa[i], _sp[2],  _sp[3], _sp[4], _sp[5], _sp[6], _sp[7], _sp[8], _sp[9], soluongCHUAN );
									if( chitiet == null )
									{
										this.msg = "Lỗi đề xuất sản phẩm, vui lòng liên hệ admin để xử lý.";
										db.getConnection().rollback();
										return false;
									}
									
									totalCT += Double.parseDouble(_soluongCT);
									
									for( int j = 0; j < chitiet.size(); j++  )
									{
										ErpThongtinkho tt = chitiet.get(j);
										
										/*query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, mame, mathung, soluong ) " +
												"select '" + this.id + "', pk_seq, N'" + _sp[1] + "', N'" + _sp[2] + "', '" + _sp[3] + "', '" + _sp[4] + "', '" + _sp[5] + "', '" + _sp[6] + "', '" + _soluongCT.replaceAll(",", "") + "' " +
												"from SANPHAM where MA = '" + spMa[i] + "' ";*/
										String scheme = "";
										if(this.loaidonhang.equals("4") || this.spSCheme[i].trim().length() > 0)
										{
											scheme = spSCheme[i];
										}
										query = "insert ERP_DONDATHANG_SANPHAM_CHITIET( dondathang_fk, SANPHAM_FK, scheme, solo, ngayhethan, ngaynhapkho, MAME, MATHUNG, MAPHIEU, MARQ, HAMLUONG, HAMAM, bin_fk, phieudt, phieueo, nsx_fk, soluong, soluongCHUAN,IS_KHONGTHUE ,TIENHANG,TIENVAT,TIENHANGSAUVAT, inchung, kho_fk) " +
												"select '" + this.id + "', '" + tt.spId + "', N'"+scheme+"', N'" + tt.solo + "', '" + tt.ngayhethan + "', '" + tt.ngaynhapkho + "', '" + tt.mame + "', '" + tt.mathung + "', N'" + tt.maphieu + "', '" + tt.MARQ + "', '" + tt.hamluong + "', '" + tt.hamam + "', '" + tt.vitriId + "', '" + tt.phieudt + "', '" + tt.phieueo + "', " + tt.nsxId + ", ( " + quyveDVBAN + " * " + tt.soluong + " ), '" + tt.soluong + "','"+this.spIskhongthue[i]+"' "+
												" ,ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0) AS TIENHANG, ROUND(((ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0))*("+thueVAT+")/100),0) AS TIENVAT"+
												", (ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0) +ROUND(((ROUND(("+ tt.soluong+" * "+spGianhap[i].replaceAll(",", "")+"),0))*("+thueVAT+")/100),0) )AS TIENHANGSAUVAT, " + inchung + ", '" + kho + "' ";
								
										System.out.println("1.2.Insert ERP_DONDATHANG_SANPHAM_CHITIET: " + query);
										if(!db.update(query))
										{
											this.msg = "Khong the tao moi ERP_DONDATHANG_SANPHAM_CHITIET: " + query;
											db.getConnection().rollback();
											return false;
										}
										
										//CẬP NHẬT KHO
										//this.msg = util.Update_KhoTT(this.ngaydenghi, "HO / Bán đối tác", db, this.khoNhanId, "( select pk_seq from SANPHAM where ma = '" + spMa[i] + "' )", _sp[2], _sp[3], _sp[4], _sp[5], _sp[6], 0, Double.parseDouble(_soluongCT.replaceAll(",", "")), -1 * Double.parseDouble(_soluongCT.replaceAll(",", "")));
										this.msg = util.Update_KhoTT_NEW(this.ngaydenghi, "HO / Bán đối tác", db, tt.khoId, tt.spId, tt.solo, tt.ngayhethan, tt.ngaynhapkho, 
														tt.mame, tt.mathung, tt.vitriId, tt.maphieu, tt.phieudt, tt.phieueo, tt.MARQ, tt.hamluong, tt.hamam, "", "", 0, tt.soluong, -1 * tt.soluong, 
														Double.parseDouble(spGianhap[i].replaceAll(",", "")), "", tt.nsxId	);
										if( this.msg.trim().length() > 0 )
										{
											db.getConnection().rollback();
											return false;
										}
									}
								}
							}
						}
						
						//NEU TONG SO LUONG CT MA KHONG BANG TONG LUONG XUAT THI KO CHO LUU
						if(totalCT != Double.parseDouble(spSoluong[i].replaceAll(",", "").trim()) )
						{
							this.msg = "Tổng xuất theo lô của sản phẩm ( " + spTen[i] + " ) ( " + totalCT + " ) phải bằng tổng số lượng xuất ( " + spSoluong[i] + " ) ";
							db.getConnection().rollback();
							return false;
						}
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
						query = "insert ERP_DONDATHANG_CHIETKHAU(DONDATHANG_FK, DIENGIAI, GIATRI, LOAI) " +
								"values( '" + this.id + "', N'" + this.dhCk_diengiai[i].trim() + "', '" + this.dhCk_giatri[i].replaceAll(",", "") + "', '" + this.dhCk_loai[i] + "' ) ";
						
						System.out.println("1.Insert DH - CK: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: " + query;
							db.getConnection().rollback();
							return false;
						}
					}
				}
			}
			
			//CHECK PHÂN BỔ TỔNG
			/*query = "  select MA, TEN, soluong, dasudung from " + 
					"  ( " + 
					"  	select PB.*, ISNULL( (  " + 
					"  					select SUM( b.soluong ) as soluong " + 
					"  					from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " + 
					"  					where a.TRANGTHAI != 3 and a.NPP_FK = '" + this.nppId + "' and b.sanpham_fk = PB.sanpham_fk and a.NgayDonHang >= PB.tungay and a.NgayDonHang <= PB.denngay " + 
					"  				 ), 0 ) as dasudung  " + 
					"  	from " + 
					"  	( " + 
					"  		select a.tungay, a.denngay, b.sanpham_fk, c.MA, c.TEN, SUM(b.soluong) as soluong  " + 
					"  		from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk " + 
					"  				inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " + 
					"  		where a.TRANGTHAI = '1' and a.doituongId = '" + this.nppId + "' " + 
					"  		group by a.tungay, a.denngay, b.sanpham_fk, c.MA, c.TEN  " + 
					"  	) " + 
					"  	PB " + 
					"  ) " + 
					"  DT where DT.soluong - DT.dasudung <= 0 ";
			System.out.println("::: CHECK PHÂN BỔ TỔNG:  " + query);
			rs = db.get(query);
			if( rs != null )
			{
				String spVUOT = "";
				while( rs.next() )
				{
					spVUOT += rs.getString("TEN") + ", ";
				}
				rs.close();
				
				if( spVUOT.trim().length() > 0 )
				{
					this.msg = "Các mặt hàng sau: " + spVUOT + " đã vượt quá số lượng được phân bổ";
					db.getConnection().rollback();
					return false;
				}
			}*/
			
			//CHECK PHÂN BỔ CT
			/*query = "  select MA, TEN, SOLO, soluong, dasudung from " + 
					"  ( " + 
					"  	select PB.*, ISNULL( (  " + 
					"  					select SUM( b.soluong ) as soluong " + 
					"  					from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM_CHITIET b on a.PK_SEQ = b.dondathang_fk " + 
					"  					where a.TRANGTHAI != 3 and dbo.trim(scheme) = '' and a.NPP_FK = '" + this.nppId + "' and b.sanpham_fk = PB.sanpham_fk and b.solo = PB.solo and a.NgayDonHang >= PB.tungay and a.NgayDonHang <= PB.denngay " + 
					"  				 ), 0 ) as dasudung  " + 
					"  	from " + 
					"  	( " + 
					"  		select a.tungay, a.denngay, b.sanpham_fk, b.solo, c.MA, c.TEN, SUM(b.soluong) as soluong  " + 
					"  		from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk " + 
					"  				inner join SANPHAM c on b.sanpham_fk = c.PK_SEQ " + 
					"  		where a.TRANGTHAI = '1' and a.doituongId = '" + this.nppId + "' " + 
					"  		group by a.tungay, a.denngay, b.sanpham_fk,  b.solo, c.MA, c.TEN  " + 
					"  	) " + 
					"  	PB " + 
					"  ) " + 
					"  DT where DT.soluong - DT.dasudung <= 0 ";
			System.out.println("::: CHECK PHÂN BỔ CHI TIET:  " + query);
			rs = db.get(query);
			if( rs != null )
			{
				String spVUOT = "";
				while( rs.next() )
				{
					spVUOT += rs.getString("TEN") + " - " + rs.getString("SOLO") + ", ";
				}
				rs.close();
				
				if( spVUOT.trim().length() > 0 )
				{
					this.msg = "Các mặt hàng sau: " + spVUOT + " đã vượt quá số lượng được phân bổ";
					db.getConnection().rollback();
					return false;
				}
			}*/
				
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		if( this.dvkdId == null )
			this.dvkdId = "";
		if( this.kbhId == null )
			this.kbhId = "";
		
		Utility util = new Utility(); 

		String query = "select PK_SEQ, TEN from ERP_KHOTT where trangthai = '1' AND PK_sEQ IN " + util.quyen_khoTT(userId); 
		if( this.loaidonhang.equals("0") || this.loaidonhang.equals("4") )
		{	//query += " and pk_seq = 100023 " ;  //Kho thành phẩm Hoàng Liệt
		
		}
		else if( this.loaidonhang.equals("3") ) 
			query += " and pk_seq in ( 100014, 100016, 100018 ) ";  //2. Bán NVL: Load từ KHO10 và KHO25, KHO33
		else //ĐƠN HÀNG KHÁC, KHÔNG PHÂN BIỆT KHO
		{
			//if( this.danhmucKH.equals("0") )
				//query += " and pk_seq in ( 100015, 100023 ) " ; //1. Bán hàng xuât khẩu: Load từ KHO30
			//else
				//query += " and pk_seq in ( 100014, 100016, 100023 ) ";  //2. Bán NVL, bán hàng khác: Load từ KHO10 và KHO25
		}
		
		System.out.println("::: LAY KHO: " + query);
		this.khoNhanRs = db.get(query);
		
		this.dvtRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where trangthai = '1' ");
		
		this.dvkdId = "100001";
		this.dvkdRs = db.get("select PK_SEQ, DONVIKINHDOANH as TEN from DONVIKINHDOANH where TRANGTHAI = '1'");
		
		if( this.ETC.equals("0") )
			this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1'");
		else
			this.kbhRs = db.get("select PK_SEQ, DIENGIAI as TEN from KENHBANHANG where TRANGTHAI = '1' and pk_seq = '100052' ");
		
		this.gsbhRs = db.get("select PK_SEQ, TEN from GIAMSATBANHANG where trangthai = '1' ");
		query = "select pk_seq, TEN from DAIDIENKINHDOANH where 1=1 ";
		if(this.gsbhId.trim().length() > 0)
			query += " and pk_seq in ( select ddkd_fk from DDKD_GSBH where GSBH_FK = '" + this.gsbhId + "' ) ";
		this.ddkdRs = db.get(query);
		
		if( this.ETC.equals("0") && !( this.loaidonhang.equals("2") || this.loaidonhang.equals("3") ) )
		{
			query = "select PK_SEQ, MAFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1'  ";
			if(this.loaidonhang.equals("0"))
				query += " and loaiNPP = '4' "; //BÁN CHO ĐỐI TÁC
			else if(this.loaidonhang.equals("5"))
				query += " and loaiNPP != '4' ";
			
			//if(this.dvkdId.trim().length() > 0)
			//	query += " and pk_seq in ( select NPP_FK from NHAPP_NHACC_DONVIKD where NCC_DVKD_FK in ( select PK_SEQ from NHACUNGCAP_DVKD where DVKD_FK = '" + this.dvkdId + "' ) ) ";
			if(this.kbhId.trim().length() > 0)
				query += " and pk_seq in ( select NPP_FK from NHAPP_KBH where KBH_FK = '" + this.kbhId + "' ) ";
		}
		else
		{
			query = "select PK_SEQ, MAFAST + ' - ' + TEN as TEN from NHAPHANPHOI where TRANGTHAI = '1' and isKHACHHANG = '1' ";
			if( this.loaidonhang.equals("2") ) //ĐƠN HÀNG XNK
			{/*
				if( this.danhmucKH.equals("0") )
					query += " and xuatkhau = '1' ";
				else if( this.danhmucKH.equals("1") )
					query += " and kehoach = '1' ";
				else if( this.danhmucKH.equals("2") )
					query += " and catlo = '1' ";
				
				if( this.danhmucKH.trim().length() > 0 )
					query += " and isnull( loaikhachhang, 10 ) = '" + this.danhmucKH + "' ";
				
			*/}
		}
		if( this.loaidonhang.equals("4") ) //ĐƠN HÀNG XNK
		{
			query += "   and isKHACHHANG = '1'";
		}
		System.out.println("----LAY NPP: " + query );
		this.nppRs = db.get(query);
		
		if(this.nppId.trim().length() > 0)
		{
			if( this.ETC.equals("0") && !( this.loaidonhang.equals("2") || this.loaidonhang.equals("3") ) )
			{
				query = "select a.PK_SEQ as nppId, d.DVKD_FK, b.KBH_FK  " +
					    "From NhaPhanPhoi a left join nhapp_kbh b on b.NPP_FK = a.PK_SEQ left join NHAPP_NHACC_DONVIKD c on c.NPP_FK = b.NPP_FK  " +
					    "	left join NHACUNGCAP_DVKD d on d.PK_SEQ = c.NCC_DVKD_FK   " +
					    "where a.pk_Seq = '" + this.nppId + "' ";
				ResultSet rsInfo = db.get(query);
				if(rsInfo != null)
				{
					try 
					{
						if(rsInfo.next())
						{
							if(this.dvkdId.trim().length() <= 0)
								this.dvkdId = rsInfo.getString("DVKD_FK");
							if(this.kbhId.trim().length() <= 0 )
								this.kbhId = rsInfo.getString("KBH_FK");
						}
						rsInfo.close();
					} 
					catch (Exception e) {}
				}
			}
			else //Tự load GSBH + DDKD
			{
				this.kbhId = "100052";
				this.dvkdId = "100001";
				
				query = "select DDKD_FK, ( select top(1) gsbh_fk from DDKD_GSBH where ddkd_fk = a.ddkd_fk ) as gsbh_fk " +
					    "From NhaPhanPhoi a  " +
					    "where pk_Seq = '" + this.nppId + "' ";
				System.out.println("ddkd: "+query);
				ResultSet rsInfo = db.get(query);
				if(rsInfo != null)
				{
					try 
					{
						if(rsInfo.next())
						{
							this.ddkdId = rsInfo.getString("DDKD_FK");
							this.gsbhId = rsInfo.getString("gsbh_fk");
						}
						rsInfo.close();
					} 
					catch (Exception e) {}
				}
			}
						
			query = "  SELECT sum( HD.TONGTIENAVAT - ISNULL( TT.DATHANHTOAN, 0 ) ) as tongno, 0 as notronghan, 0 as noquahan, 0 as noxau,  " + 
					"  		 DATEDIFF(DD, MIN( HD.NGAYXUATHD ), GETDATE() ) as songaynoxanhat, MIN( HD.NGAYXUATHD ) as ngaynoxanhat	  " + 
					"  FROM ERP_HOADON HD 	left join  " + 
					"  	( " + 
					"  		SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN    	  " + 
					"  		FROM   	  " + 
					"  		( 	 					  " + 
					"  			SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN     		  " + 
					"  			FROM ERP_THUTIEN_HOADON TTHD    " + 
					"  				INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ    		  " + 
					"  			WHERE  TT.TRANGTHAI NOT IN (2)	   " + 
					"  	  " + 
					"  			GROUP BY HOADON_FK    	  " + 
					"  		)   " + 
					"  		HOADONDATT  GROUP BY HOADON_FK    " + 
					"  	 )	 " + 
					"  	 TT on HD.PK_SEQ = TT.HOADON_FK  " + 
					"  WHERE  HD.TRANGTHAI in ( 2, 4 )  AND HD.NPP_FK = '" + this.nppId + "' and  HD.TONGTIENAVAT - ISNULL( TT.DATHANHTOAN, 0 ) > 0 ";
			
			System.out.println("CONG NO: " + query);
			this.congnoRs = db.get(query);
			
		}
		
		if(this.loaidonhang.equals("5")) //ĐƠn hàng nội bộ
		{
			if( this.tungay.trim().length() <= 0 )
				this.tungay = "";
			
			if( this.nppId.trim().length() > 0 && ( this.spMa == null || this.spMa.length <= 0 ) && this.phanloai.trim().length() > 0 )
			{
				this.createRSSANPHAM_NoiBo();
			}
		}
	}


	private void createRSSANPHAM_NoiBo() 
	{
		String condition = " ";
		String condition_hdkhac = " ";
		if( this.nppId.trim().length() > 0 )
		{
			condition += " and HD.NPP_FK = '" + this.nppId + "'  ";
			condition_hdkhac += " and a.NPP_FK = '" + this.nppId + "'  ";
		
		}
		if( this.tungay.trim().length() > 0 )
		{
			condition += " and HD.NGAYXUATHD >= '" + this.tungay + "'  ";
			condition_hdkhac += " and a.NGAYGHINHAN >= '" + this.tungay + "'  ";
		}
		if( this.denngay.trim().length() > 0 )
		{
			condition += " and HD.NGAYXUATHD <= '" + this.denngay + "'  ";
			condition_hdkhac += " and   a.NGAYGHINHAN <= '" + this.denngay + "'  ";
		}
		//ETC
		String condition1 = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = '"+this.phanloai+"' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
		
		//OTC
		String condition2 = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTC' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
		
		//KM
		String condition3 = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'KM' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
		
		String condition4 = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'HM' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
		
		//mauhoadon = 1 chi nhánh -- 2 công ty
	
		String query="";
		if(!this.phanloai.equals("KM") &&  !this.phanloai.equals("HM"))
		{
			if( this.phanloai.equals("OTC") )
			{
				 query ="\n  select HD.MA, HD.TEN, HD.DONVI, round( SUM(HD.soluong), 4 ) as soluong, " +      
								"\n SUM( HD.doanhthu ) / SUM ( soluong )  as dongia, SUM(chietkhau) as chietkhau,    	AVG( trongluong ) as trongluong, AVG( thetich ) as thetich, AVG( spQuyDoi ) as spQuyDoi, scheme,  " +      
								"\n  SUM( HD.VAT ) / SUM ( HD.doanhthu )*100.0 as thueVAT ,sum(HD.vat) as tienvat " +      
								"\n  from  " +      
								"\n  (  " +      
								"\n  	select  'OTC'  as phanloai,  " +      
								"\n 		 b.MA, b.TEN, DV.donvi,sum(spct.soluong) as soluong,   " +      
								"\n  		  sum(round(spct.soluong * a.DonGia,0))/sum((spct.soluong))  as dongia, " +      
								"\n  		   isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, " +      
								"\n  		    ISNULL(b.thetich, 0) as thetich, 1 as spQuyDoi,   " +      
								"\n  		isnull(a.scheme, ' ') as scheme, isnull(a.VAT, 0) as thueVAT , " +      
								"\n  		sum(round(spct.soluong * a.DonGia,0)) as doanhthu , " +      
								"\n  		 sum( round( round(spct.soluong * a.DonGia,0) *(a.vat/100),0) ) as VAT " +      
								"\n  	from HOADON_SP a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ      " +      
								"\n	inner join HOADON HD on a.HOADON_FK = HD.PK_SEQ     " +      
								"\n 	inner join  (  " +      
								"\n  select MA, DONGIA, SOLO, NGAYHETHAN, THUEVAT as vat, sum(SOLUONG) as soluong, CHIETKHAU,hoadon_fk   " +      
								"\n  from HOADON_SP_CHITIET group by MA, DONGIA, SOLO, NGAYHETHAN, THUEVAT ,CHIETKHAU,hoadon_fk  ) spct on    " +      
								"\n    spct.hoadon_fk=HD.PK_SEQ and a.SANPHAM_FK=(select PK_SEQ from ERP_SANPHAM where spct.MA=MA)     " +      
								"\n  			INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    " +      
								"\n  	where HD.TRANGTHAI in ( 2, 4 ) and HD.mauhoadon = '1' and spct.soluong>0 and isnull(HD.loaihoadon, '0') = '0'    " +condition +condition2     ;
					
				query +=	"\n group by  b.MA, b.TEN, DV.donvi,b.thetich,a.scheme,a.chietkhau,b.trongluong,a.VAT " +   
				
							"\n union all"+
							"\n 	select  'OTC' as phanloai, "+
							"\n 		d.MA, d.TEN, e.DONVI,  "+
							"\n 		0    as soluong,  "+
							"\n 		c.THANHTIEN   as dongia,0 chiekhau,0 trongluong,0 thetich,1 spquydoi,'' scheme, a.VAT as thuexuat,round(c.THANHTIEN,0)  as doanhthu ,  round(round(c.THANHTIEN,0)*a.vat/100,0) as VAT  "+   
							"\n 		from ERP_HOADONKHAC a       "+
							"\n 		inner join ERP_HOADONKHAC_SANPHAM c on a.pk_seq = c.HOADONKHAC_FK    "+    
							"\n 		inner join ERP_SANPHAM d on d.pk_seq= c.sanpham_fk 		"+
							"\n 	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     "+
							"\n  	inner join khachhang kh on kh.pk_seq=a.khachhang_fk  "+
							"\n  	where 1=1  and c.SOLUONG > 0   and a.trangthai  in ( 1 )     and  a.DOANHTHU_FK in (400002,400003)  and a.khachhang_fk is not null "+condition_hdkhac+ 
							"\n   	and a.KBH_FK =100025"+
							
							
							"\n  " +      
							"\n  )  " +      
							"\n  HD where 1 = 1 and HD.PHANLOAI = 'OTC'   " +      
							"\n  group by HD.MA, HD.TEN, HD.DONVI, scheme  "       ;
			}
			
			if( this.phanloai.equals("ETCCN") )
			{
			
				 
				 	query=	"\n select HD.MA, HD.TEN, HD.DONVI, round( SUM(HD.soluong), 4 ) as soluong,  " +      
								 "\n SUM( HD.doanhthu ) / SUM ( soluong )  as dongia, SUM(chietkhau) as chietkhau,    	AVG( trongluong ) as trongluong, AVG( thetich ) as thetich, AVG( spQuyDoi ) as spQuyDoi, scheme,   " +      
								 "\n  SUM( HD.VAT ) / SUM ( HD.doanhthu )*100.0 as thueVAT ,  SUM( HD.VAT)  as tienVAT,SUM(HD.doanhthu) as doanhthu " +      
								 "\n  from   " +      
								 "\n  (   " +      
								 "\n  	select  'ETCCN'  as phanloai,   " +      
								 "\n 		 b.MA, b.TEN, DV.donvi,sum(spct.soluong*dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) as soluong,    " +      
								 "\n  		   isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong,  " +      
								 "\n  		    ISNULL(b.thetich, 0) as thetich, dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) as spQuyDoi,    " +      
								 "\n  		isnull(a.scheme, ' ') as scheme, isnull(a.VAT, 0) as thueVAT ,  " +      
								 "\n  		sum(round((spct.soluong*dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) * (a.DonGia /dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) ,0)) as doanhthu ,  " +      
								 "\n  		sum(isnull(spct.TIENVAT,0))as VAT   " +      
								 "\n	from ERP_HOADONNPP_SP a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ       " +      
								 "\n	inner join ERP_HOADONNPP HD on a.HOADON_FK = HD.PK_SEQ      " + 
								 "\n	 inner join  (      " + 
								 "\n				select sum(tienthue) as tienvat,b.hoadon_fk,b.MA,solo,ngayhethan,SUM(soluong) as soluong,kho_fk      " + 
								 "\n				from ERP_HOADONNPP_SP_CHITIET b        " + 
								 "\n				group by hoadon_fk,b.MA,solo,ngayhethan,kho_fk) spct on hd.pk_seq = spct.hoadon_fk	 and spct.MA=b.MA  " +  

								 "\n  	INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK     " +      
								 "\n  	where HD.TRANGTHAI in ( 2, 4 ) and HD.mauhoadon = '1' and isnull(HD.LoaiHoaDon,0)=0 and spct.soluong>0 and HD.khachhang_fk is not null  "+ condition + condition1 ;      
								
					query += "\n group by  b.MA, b.TEN, DV.donvi,b.thetich,a.scheme,a.chietkhau,b.trongluong,a.VAT,spct.TIENVAT ,a.dvdl_fk,a.SANPHAM_FK " +      
							 "\n   " +    
							
								"\n union all"+
								"\n 	select  'ETCCN' as phanloai, "+
								"\n 		d.MA, d.TEN, e.DONVI,  "+
								"\n 		0    as soluong,  "+
								"\n 		0 chiekhau,0 trongluong,0 thetich,1 spquydoi,'' scheme, a.VAT as thuexuat,round(c.THANHTIEN,0)  as doanhthu ,  round(round(c.THANHTIEN,0)*a.vat/100,0) as VAT  "+   
								"\n 		from ERP_HOADONKHAC a       "+
								"\n 		inner join ERP_HOADONKHAC_SANPHAM c on a.pk_seq = c.HOADONKHAC_FK    "+    
								"\n 		inner join ERP_SANPHAM d on d.pk_seq= c.sanpham_fk 		"+
								"\n 	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     "+
								"\n  	inner join khachhang kh on kh.pk_seq=a.khachhang_fk  "+
								"\n  	where 1=1  and c.SOLUONG > 0   and a.trangthai  in ( 1 )     and  a.DOANHTHU_FK in (400002,400003)  and a.khachhang_fk is not null "+condition_hdkhac+ 
								"\n   	and a.KBH_FK =100052"+
							 
							 "\n  )   " +      
							 "\n  HD where 1 = 1 and HD.PHANLOAI = 'ETCCN'    " +      
							 "\n  group by HD.MA, HD.TEN, HD.DONVI, scheme  " ;      

		
			}
			if( this.phanloai.equals("ETCDT") )
			{
			
				query=	"\n select HD.MA, HD.TEN, HD.DONVI, round( SUM(HD.soluong), 4 ) as soluong,  " +      
						 "\n SUM( HD.doanhthu ) / SUM ( soluong )  as dongia, SUM(chietkhau) as chietkhau,    	AVG( trongluong ) as trongluong, AVG( thetich ) as thetich, AVG( spQuyDoi ) as spQuyDoi, scheme,   " +      
						 "\n  SUM( HD.VAT ) / SUM ( HD.doanhthu )*100.0 as thueVAT ,  SUM( HD.VAT)  as tienVAT,SUM(HD.doanhthu) as doanhthu " +      
						 "\n  from   " +      
						 "\n  (   " +      
						 "\n  	select  'ETCDT'  as phanloai,   " +      
						 "\n 		 b.MA, b.TEN, DV.donvi,sum(spct.soluong*dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) as soluong,    " +      
						 "\n  		   isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong,  " +      
						 "\n  		    ISNULL(b.thetich, 0) as thetich, dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) as spQuyDoi,    " +      
						 "\n  		isnull(a.scheme, ' ') as scheme, isnull(a.VAT, 0) as thueVAT ,  " +      
						 "\n  		sum(round((spct.soluong*dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) * (a.DonGia /dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) ,0)) as doanhthu ,  " +      
						 "\n  		sum(isnull(spct.TIENVAT,0))as VAT   " +      
						 "\n	from ERP_HOADONNPP_SP a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ       " +      
						 "\n	inner join ERP_HOADONNPP HD on a.HOADON_FK = HD.PK_SEQ      " + 
						 "\n	 inner join  (      " + 
						 "\n				select sum(tienthue) as tienvat,b.hoadon_fk,b.MA,solo,ngayhethan,SUM(soluong) as soluong,kho_fk      " + 
						 "\n				from ERP_HOADONNPP_SP_CHITIET b        " + 
						 "\n				group by hoadon_fk,b.MA,solo,ngayhethan,kho_fk) spct on hd.pk_seq = spct.hoadon_fk	 and spct.MA=b.MA  " +  

						 "\n  	INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK     " +      
						 "\n  	where HD.TRANGTHAI in ( 2, 4 ) and spct.soluong>0 and HD.mauhoadon = '1'  and isnull(HD.LoaiHoaDon,0)=0  and HD.kbh_fk=100052 and HD.NPP_DAT_FK is not null  "+ condition + condition1 ;      
						
				query += "\n group by  b.MA, b.TEN, DV.donvi,b.thetich,a.scheme,a.chietkhau,b.trongluong,a.VAT,spct.TIENVAT ,a.dvdl_fk,a.SANPHAM_FK " +      
						 "\n   " +  
						
						"\n union all"+
						"\n 	select  'ETCDT' as phanloai, "+
						"\n 		d.MA, d.TEN, e.DONVI,  "+
						"\n 		0    as soluong,  "+
						"\n 		0 chiekhau,0 trongluong,0 thetich,1 spquydoi,'' scheme, a.VAT as thuexuat,round(c.THANHTIEN,0)  as doanhthu ,  round(round(c.THANHTIEN,0)*a.vat/100,0) as VAT  "+   
						"\n 		from ERP_HOADONKHAC a       "+
						"\n 		inner join ERP_HOADONKHAC_SANPHAM c on a.pk_seq = c.HOADONKHAC_FK    "+    
						"\n 		inner join ERP_SANPHAM d on d.pk_seq= c.sanpham_fk 		"+
						"\n 	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     "+
						"\n  	inner join khachhang kh on kh.pk_seq=a.khachhang_fk  "+
						"\n  	where 1=1  and c.SOLUONG > 0   and a.trangthai  in ( 1 )     and  a.DOANHTHU_FK in (400002,400003)  and a.npp_dat_fk is not null   "+condition_hdkhac+ 
						"\n   	and a.KBH_FK =100052"+
						 "\n  )   " +      
						 "\n  HD where 1 = 1 and HD.PHANLOAI = 'ETCDT'    " +      
						 "\n  group by HD.MA, HD.TEN, HD.DONVI, scheme  " ;      

		
			}
			
			if( this.phanloai.equals("OTCDT") )
			{
			
				
				query=	"\n select HD.MA, HD.TEN, HD.DONVI, round( SUM(HD.soluong), 4 ) as soluong,  " +      
						 "\n SUM( HD.doanhthu ) / SUM ( soluong )  as dongia, SUM(chietkhau) as chietkhau,    	AVG( trongluong ) as trongluong, AVG( thetich ) as thetich, AVG( spQuyDoi ) as spQuyDoi, scheme,   " +      
						 "\n  SUM( HD.VAT ) / SUM ( HD.doanhthu )*100.0 as thueVAT ,  SUM( HD.VAT)  as tienVAT,SUM(HD.doanhthu) as doanhthu " +      
						 "\n  from   " +      
						 "\n  (   " +      
						 "\n  	select  'OTCDT'  as phanloai,   " +      
						 "\n 		 b.MA, b.TEN, DV.donvi,sum(spct.soluong*dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) as soluong,    " +      
						 "\n  		   isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong,  " +      
						 "\n  		    ISNULL(b.thetich, 0) as thetich, dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk) as spQuyDoi,    " +      
						 "\n  		isnull(a.scheme, ' ') as scheme, isnull(a.VAT, 0) as thueVAT ,  " +      
						 "\n  		sum(round((spct.soluong*dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) * (a.DonGia /dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) ,0)) as doanhthu ,  " +      
						 "\n  		sum(isnull(spct.TIENVAT,0))as VAT   " +      
						 "\n	from ERP_HOADONNPP_SP a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ       " +      
						 "\n	inner join ERP_HOADONNPP HD on a.HOADON_FK = HD.PK_SEQ      " + 
						 "\n	 inner join  (      " + 
						 "\n				select sum(tienthue) as tienvat,b.hoadon_fk,b.MA,solo,ngayhethan,SUM(soluong) as soluong,kho_fk      " + 
						 "\n				from ERP_HOADONNPP_SP_CHITIET b        " + 
						 "\n				group by hoadon_fk,b.MA,solo,ngayhethan,kho_fk) spct on hd.pk_seq = spct.hoadon_fk	 and spct.MA=b.MA  " +  

						 "\n  	INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK     " +      
						 "\n  	where HD.TRANGTHAI in ( 2, 4 ) and HD.mauhoadon = '1' and spct.soluong>0  and isnull(HD.LoaiHoaDon,0)=0  and HD.kbh_fk=100025 and HD.NPP_DAT_FK is not null  "+ condition + condition1 ;      
						
				query += "\n group by  b.MA, b.TEN, DV.donvi,b.thetich,a.scheme,a.chietkhau,b.trongluong,a.VAT,spct.TIENVAT ,a.dvdl_fk,a.SANPHAM_FK " +      
						 "\n   " +      
						
						"\n union all"+
						"\n 	select  'ETCDT' as phanloai, "+
						"\n 		d.MA, d.TEN, e.DONVI,  "+
						"\n 		0    as soluong,  "+
						"\n 		0 chiekhau,0 trongluong,0 thetich,1 spquydoi,'' scheme, a.VAT as thuexuat,round(c.THANHTIEN,0)  as doanhthu ,  round(round(c.THANHTIEN,0)*a.vat/100,0) as VAT  "+   
						"\n 		from ERP_HOADONKHAC a       "+
						"\n 		inner join ERP_HOADONKHAC_SANPHAM c on a.pk_seq = c.HOADONKHAC_FK    "+    
						"\n 		inner join ERP_SANPHAM d on d.pk_seq= c.sanpham_fk 		"+
						"\n 	inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq     "+
						"\n  	inner join khachhang kh on kh.pk_seq=a.khachhang_fk  "+
						"\n  	where 1=1  and c.SOLUONG > 0   and a.trangthai  in ( 1 )     and  a.DOANHTHU_FK in (400002,400003)  and a.npp_dat_fk is not null   "+condition_hdkhac+ 
						"\n   	and a.KBH_FK =100025"+
						 
						 "\n  )   " +      
						 "\n  HD where 1 = 1 and HD.PHANLOAI = 'OTCDT'    " +      
						 "\n  group by HD.MA, HD.TEN, HD.DONVI, scheme  " ;      
				
				 
			}
			
			
			
			System.out.println("---INIT SP NOI BO: " + query);
			ResultSet spRs = db.get(query);
			
			NumberFormat formater = new DecimalFormat("##,###,###.####");
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
					String spTHUEVAT = "";
					String spSCHEME = "";
					
					String spTRONGLUONG = "";
					String spTHETICH = "";
					
					String spQuyDoi ="";
					String sptienvat ="";
					
					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP +=spRs.getDouble("DONGIA") + "__";
						spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
						spTHUEVAT += spRs.getDouble("thueVAT") + "__";
						spSCHEME += ( spRs.getString("scheme").trim().length() <= 0 ? " " : spRs.getString("scheme") ) + "__";
						spTRONGLUONG += spRs.getString("trongluong") + "__";
						spTHETICH += spRs.getString("thetich") + "__";
						spQuyDoi += spRs.getString("spQuyDoi") + "__";
						sptienvat += spRs.getString("tienvat") + "__";
					}
					spRs.close();
					
					
					//INIT CHIẾT KHẤU QUÝ ==> KM ĐƯA VÀO OTC
					if( this.phanloai.equals("OTC") )
					{
						query =  " select hd_ck.diengiai, SUM( hd_ck.tienBVAT ) as chietkhau, SUM(hd_ck.tienvat ) as thueVAT,  SUM ( hd_ck.tienAVAT ) as thanhtien"+
								 "\n from HOADON_CHIETKHAU hd_ck inner join HOADON hd on hd_ck.hoadon_fk = hd.pk_seq "+
								 "\n  where hd.trangthai in ( 2, 4 ) and hd_ck.hienthi=1 and hd_ck.diengiai in ( 'CQX5', 'CT5' )  " + condition + condition2 +
								 "\n  group by hd_ck.diengiai ";
						System.out.println("::: INIT CHIET KHAU: " + query );
						ResultSet rs = db.get(query);
						if( rs != null )
						{
							Hashtable<String, String> schemeNOIBO = new Hashtable<String, String>();
							Hashtable<String, String> schemeNOIBO_vat = new Hashtable<String, String>();
							Hashtable<String, String> schemeNOIBO_bvat = new Hashtable<String, String>();
							while( rs.next() )
							{
								schemeNOIBO.put(rs.getString("diengiai"), rs.getString("thanhtien"));
								schemeNOIBO_vat.put(rs.getString("diengiai"), rs.getString("thueVAT"));
								schemeNOIBO_bvat.put(rs.getString("diengiai"), rs.getString("chietkhau"));
							}
							rs.close();
							
							this.schemeNoibo = schemeNOIBO;
							this.schemeNoibo_bvat= schemeNOIBO_bvat;
							this.schemeNoibo_vat=schemeNOIBO_vat;
						}
					}
					
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
						
						spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
						this.spVAT = spTHUEVAT.split("__");
						
						spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
						this.spSCheme = spSCHEME.split("__");
						
						spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
						this.spTrongluong = spTRONGLUONG.split("__");
						
						spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
						this.spThetich = spTHETICH.split("__");
						
						spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
						this.spQuyDoi = spQuyDoi.split("__");
						
						sptienvat = sptienvat.substring(0, sptienvat.length() - 2);
						this.sptienvat = sptienvat.split("__");
					}
				} 
				catch (Exception e) 
				{
					e.printStackTrace();
					System.out.println("115.Exception: " + e.getMessage());
				}
			}
		}
		else
		{
			//INIT SP KHUYEN MAI
			NumberFormat formater = new DecimalFormat("##,###,###.####");
			String spMA = "";
			String spTEN = "";
			String spDONVI = "";
			String spSOLUONG = "";
			String spGIANHAP = "";
			String spCHIETKHAU = "";
			String spTHUEVAT = "";
			String spSCHEME = "";
			
			String spTRONGLUONG = "";
			String spTHETICH = "";
			
			String spQuyDoi ="";
			String sptienvat ="";
			if( this.phanloai.equals("KM") )
			{
				query = "  select HD.MA, HD.TEN, HD.DONVI, SUM(HD.soluong) as soluong, AVG(HD.dongia) as dongia, SUM(chietkhau) as chietkhau,  " + 
						"\n  	AVG( trongluong ) as trongluong, AVG( thetich ) as thetich, AVG( spQuyDoi ) as spQuyDoi, 'NA' scheme, AVG( thueVAT ) as thueVAT,0 tienvat " + 
						"\n  from " + 
						"\n  ( " + 
						"\n  	select b.MA, b.TEN, DV.donvi, a.soluong,  " + 
						"\n  		 0 as dongia, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, 1 as spQuyDoi,  " + 
						"\n  		a.CTKM as scheme, isnull(a.VAT, 0) as thueVAT  " + 
						"\n  	from HOADON_CTKM_TRAKM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ     " + 
						"\n   			INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK  " + 
						"\n   			inner join HOADON HD on a.HOADON_FK = HD.PK_SEQ       " + 
						"\n   			inner join HOADON_DDH HDD on HDD.HOADON_FK = HD.PK_SEQ       " + 
						"\n   			inner join DONHANG DH on HDD.DDH_FK = DH.PK_SEQ       " + 
						"\n  	where HD.TRANGTHAI in ( 2, 4 )  and isnull(dh.donquatang,0)<> 1  " + condition + condition3 +
						"\n union all "+
						"\n	select    "+ 
						"\n	 b.MA, b.TEN, DV.donvi,sum(spct.soluong*dbo.LayQuyCach(a.sanpham_fk, null, a.dvdl_fk)) as soluong,  "+   
						"\n	   0 dongia,0 chietkhau, ISNULL(b.trongluong, 0) as trongluong,   "+
						"\n	    ISNULL(b.thetich, 0) as thetich, 1 as spQuyDoi,     "+
						"\n	isnull(a.scheme, ' ') as scheme, isnull(a.VAT, 0) as thueVAT  "+
						 "\n	from ERP_HOADONNPP_SP a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ       " +      
						 "\n	inner join ERP_HOADONNPP HD on a.HOADON_FK = HD.PK_SEQ      " + 
						 "\n	 inner join  (      " + 
						 "\n				select sum(tienthue) as tienvat,b.hoadon_fk,b.MA,solo,ngayhethan,SUM(soluong) as soluong,kho_fk      " + 
						 "\n				from ERP_HOADONNPP_SP_CHITIET b        " + 
						 "\n				group by hoadon_fk,b.MA,solo,ngayhethan,kho_fk) spct on hd.pk_seq = spct.hoadon_fk	 and spct.MA=b.MA  " +  

						 "\n  	INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK     " +      
						 "\n  	where HD.TRANGTHAI in ( 2, 4 ) and spct.soluong>0 and HD.mauhoadon = '1'  and isnull(HD.LoaiHoaDon,0)=1    "+ condition + condition3 +
						"\n   group by b.MA, b.TEN, DV.donvi,a.scheme,b.TRONGLUONG,b.THETICH,a.VAT ) " + 
						"\n  HD group by HD.MA, HD.TEN, HD.DONVI ";
				
				System.out.println("___INIT KM NOI BO: " + query);
				ResultSet spRs = db.get(query);
				try {
					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += spRs.getDouble("DONGIA") + "__";
						spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
						spTHUEVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
						spSCHEME += spRs.getString("scheme") + "__";
						spTRONGLUONG += spRs.getString("trongluong") + "__";
						spTHETICH += spRs.getString("thetich") + "__";
						spQuyDoi +=spRs.getString("spQuyDoi") + "__";
						sptienvat += spRs.getString("tienvat") + "__";
					}
					spRs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
				
			}
			
			if( this.phanloai.equals("HM") )
			{
/*				query = "  select HD.MA, HD.TEN, HD.DONVI, SUM(HD.soluong) as soluong, AVG(HD.dongia) as dongia, SUM(chietkhau) as chietkhau,  " + 
						"\n  	AVG( trongluong ) as trongluong, AVG( thetich ) as thetich, AVG( spQuyDoi ) as spQuyDoi, 'NA' scheme, AVG( thueVAT ) as thueVAT,0 tienvat " + 
						"\n  from " + 
						"\n  ( " + 
						"\n  	select b.MA, b.TEN, DV.donvi, a.soluong,  " + 
						"\n  		 a.dongia as dongia, 0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich, 1 as spQuyDoi,  " + 
						"\n  		a.CTKM as scheme, isnull(a.VAT, 0) as thueVAT  " + 
						"\n  	from HOADON_CTKM_TRAKM a inner Join SanPham b on a.SANPHAM_FK = b.PK_SEQ     " + 
						"\n   			INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK  " + 
						"\n   			inner join HOADON HD on a.HOADON_FK = HD.PK_SEQ       " + 
						"\n   			inner join HOADON_DDH HDD on HDD.HOADON_FK = HD.PK_SEQ       " + 
						"\n   			inner join DONHANG DH on HDD.DDH_FK = DH.PK_SEQ       " + 
						"\n  	where HD.TRANGTHAI in ( 2, 4 ) and isnull(dh.donquatang,0)= 1  " + condition + condition4 +
						"\n  ) " + 
						"\n  HD group by HD.MA, HD.TEN, HD.DONVI ";*/
				
				
				
				 query ="\n  select HD.MA, HD.TEN, HD.DONVI, round( SUM(HD.soluong), 4 ) as soluong, " +      
							"\n SUM( HD.doanhthu ) / SUM ( soluong )  as dongia, SUM(chietkhau) as chietkhau,    	AVG( trongluong ) as trongluong, AVG( thetich ) as thetich, AVG( spQuyDoi ) as spQuyDoi, scheme,  " +      
							"\n  case when SUM ( HD.doanhthu ) =0 then 0 else SUM( HD.VAT ) / (SUM ( HD.doanhthu ))*100.0 end  as thueVAT ,sum(HD.vat) as tienvat " +      
							"\n  from  " +      
							"\n  (  " +      
							"\n  	select  'HM'  as phanloai,  " +      
							"\n 		 b.MA, b.TEN, DV.donvi,sum(spct.soluong) as soluong,   " +      
							"\n  		  sum(round(spct.soluong * a.DonGia,0))/sum((spct.soluong))  as dongia, " +      
							"\n  		   0 as chietkhau, ISNULL(b.trongluong, 0) as trongluong, " +      
							"\n  		    ISNULL(b.thetich, 0) as thetich, 1 as spQuyDoi,   " +      
							"\n  		 ' ' as scheme, isnull(a.VAT, 0) as thueVAT , " +      
							"\n  		sum(round(spct.soluong * a.DonGia,0)) as doanhthu , " +      
							"\n  		 sum( round( round(spct.soluong * a.DonGia,0) *(a.vat/100),0) ) as VAT " +      
							"\n  	from HOADON_CTKM_TRAKM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ      " +      
							"\n	inner join HOADON HD on a.HOADON_FK = HD.PK_SEQ     " + 
							"\n inner join HOADON_DDH HDD on HDD.HOADON_FK = HD.PK_SEQ    " +     
							"\n inner join DONHANG DH on HDD.DDH_FK = DH.PK_SEQ         " +
							"\n 	inner join  (  " +      
							"\n  select sanpham_fk, DONGIA, SOLO, NGAYHETHAN,  sum(SOLUONG) as soluong, 0 CHIETKHAU,hoadon_fk  " +      
							"\n   from HOADON_CTKM_TRAKM_CHITIET group by sanpham_fk, DONGIA, SOLO, NGAYHETHAN,hoadon_fk  ) spct on    " +      
							"\n    spct.hoadon_fk=HD.PK_SEQ and  a.SANPHAM_FK=spct.sanpham_fk     " +      
							"\n  			INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = b.DVDL_FK    " +      
							"\n  	where HD.TRANGTHAI in ( 2, 4 ) and isnull(dh.donquatang,0)=1   and spct.soluong>0 and isnull(HD.loaihoadon, '0') <> '0'    " +condition +condition4     ;
				
			query +=	"\n group by  b.MA, b.TEN, DV.donvi,b.thetich,b.trongluong,a.VAT " +      
						"\n  " +      
						"\n  )  " +      
						"\n  HD where 1 = 1  and HD.PHANLOAI = 'HM'   " +      
						"\n  group by HD.MA, HD.TEN, HD.DONVI, scheme  "       ;
				
				
				System.out.println("___INIT KM NOI BO: " + query);
				ResultSet spRs = db.get(query);
				try {
					while(spRs.next())
					{
						spMA += spRs.getString("MA") + "__";
						spTEN += spRs.getString("TEN") + "__";
						spDONVI += spRs.getString("DONVI") + "__";
						spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
						spGIANHAP += spRs.getDouble("DONGIA") + "__";
						spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
						spTHUEVAT +=spRs.getDouble("thueVAT") + "__";
						spSCHEME += spRs.getString("scheme") + "__";
						spTRONGLUONG += spRs.getString("trongluong") + "__";
						spTHETICH += spRs.getString("thetich") + "__";
						spQuyDoi +=spRs.getString("spQuyDoi") + "__";
						sptienvat += spRs.getString("tienvat") + "__";
					}
					spRs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			
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
				
				spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
				this.spVAT = spTHUEVAT.split("__");
				
				spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
				this.spSCheme = spSCHEME.split("__");
				
				spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
				this.spTrongluong = spTRONGLUONG.split("__");
				
				spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
				this.spThetich = spTHETICH.split("__");
				
				spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
				this.spQuyDoi = spQuyDoi.split("__");
				
				sptienvat = sptienvat.substring(0, sptienvat.length() - 2);
				this.sptienvat = sptienvat.split("__");
			}
			
		}
		
		

	}

	
	private void initSANPHAM_NoiBo() 
	{
		String query =  "select isnull(a.tienvat,0) tienvat,b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich    " +	
						"	, ISNULL ( (select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018), 0 )   as spQuyDoi, isnull(a.scheme, ' ') as scheme, isnull(a.thueVAT, 0) as thueVAT "+
						" from ERP_Dondathang_SANPHAM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
						" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
						"where a.Dondathang_FK = '" + this.id + "' ";
		
		System.out.println("---INIT SP NOI BO: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###.####");
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
				String spTHUEVAT = "";
				String spSCHEME = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				
				String spQuyDoi ="";
				String sptienvat ="";
				
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += spRs.getDouble("DONGIA") + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spTHUEVAT += spRs.getDouble("thueVAT") + "__";
					spSCHEME += ( spRs.getString("scheme").trim().length() <= 0 ? " " : spRs.getString("scheme") ) + "__";
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi += spRs.getString("spQuyDoi") + "__";
					sptienvat+=spRs.getString("tienvat") + "__";
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
					
					spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
					this.spVAT = spTHUEVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					sptienvat = sptienvat.substring(0, sptienvat.length() - 2);
					this.sptienvat = sptienvat.split("__");
				}
				
				//INIT CHIẾT KHẤU QUÝ
				query =  "select DIENGIAI, GIATRI, THANHTIEN,tienbvat,tienvat from ERP_DONDATHANG_CHIETKHAU where DONDATHANG_FK = '" + this.id + "' ";
				System.out.println("::: INIT CHIET KHAU: " + query );
				ResultSet rs = db.get(query);
				if( rs != null )
				{
					Hashtable<String, String> schemeNOIBO = new Hashtable<String, String>();
					Hashtable<String, String> schemeNOIBO_vat = new Hashtable<String, String>();
					Hashtable<String, String> schemeNOIBO_bvat = new Hashtable<String, String>();
					while( rs.next() )
					{
						schemeNOIBO.put(rs.getString("diengiai"), rs.getString("thanhtien"));
						schemeNOIBO_vat.put(rs.getString("diengiai"), rs.getString("tienvat"));
						schemeNOIBO_bvat.put(rs.getString("diengiai"), rs.getString("tienbvat"));
					}
					rs.close();
					
					this.schemeNoibo = schemeNOIBO;
					this.schemeNoibo_vat = schemeNOIBO_vat;
					this.schemeNoibo_bvat = schemeNOIBO_bvat;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
	}

	private void initSANPHAM() 
	{
		String query =  
					"select isnull(a.ptchietkhaugia,-1) ptchietkhaugia,b.MA, b.TEN, DV.donvi, a.soluong, a.dongia, isnull(a.chietkhau, 0) as chietkhau, ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich    " +	
					"	, ISNULL ( (select soluong1/ soluong2 from QUYCACH where sanpham_fk=a.sanpham_fk and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018), 0 )   as spQuyDoi, isnull(a.scheme, ' ') as scheme, isnull(a.thueVAT, 0) as thueVAT,isnull(a.IS_KHONGTHUE,0) as IS_KHONGTHUE "+
					" from ERP_Dondathang_SANPHAM a inner Join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ    " +
					" 		INNER JOIN DONVIDOLUONG DV ON DV.PK_SEQ = a.DVDL_FK       " +
					"where a.Dondathang_FK = '" + this.id + "' ";
		
		System.out.println("---INIT SP: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###.####");
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
				String spTHUEVAT = "";
				String spSCHEME = "";
				
				String spTRONGLUONG = "";
				String spTHETICH = "";
				String iskht ="";
				String spQuyDoi ="";
				String spchietkhaugia = "";
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("DONGIA")) + "__";
					spCHIETKHAU += formater.format(spRs.getDouble("chietkhau")) + "__";
					spTHUEVAT += formater.format(spRs.getDouble("thueVAT")) + "__";
					spSCHEME += ( spRs.getString("scheme").trim().length() <= 0 ? " " : spRs.getString("scheme") ) + "__";
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					spQuyDoi +=spRs.getString("spQuyDoi") + "__";
					spchietkhaugia +=spRs.getString("ptchietkhaugia") + "__";
					iskht += spRs.getString("IS_KHONGTHUE") + "__";
				}
				spRs.close();
				
				//INIT SP KHUYEN MAI
				query = "select isnull(b.MA, ' ') as MA, isnull(b.TEN, ' ') as TEN, isnull(c.DONVI, ' ') as donvi, d.SCHEME, isnull(a.soluong, 0) as soluong, a.tonggiatri, " +
						"		ISNULL(b.trongluong, 0) as trongluong, ISNULL(b.thetich, 0) as thetich " +
						"	, ISNULL( (select soluong1 / soluong2 from QUYCACH where sanpham_fk=b.pk_Seq and DVDL1_FK=b.DVDL_FK and DVDL2_FK=100018), 0)   as spQuyDoi "+
						"from ERP_DONDATHANG_CTKM_TRAKM a left join ERP_SANPHAM b on a.SPMA = b.MA  " +
						"	left join DONVIDOLUONG c on b.DVDL_FK = c.PK_SEQ  " +
						"	inner join CTKHUYENMAI d on a.ctkmID = d.PK_SEQ " +
						"where a.dondathangID = '" + this.id + "' ";
				
				System.out.println("___INIT KM: " + query);
				spRs = db.get(query);
				while(spRs.next())
				{
					spMA += spRs.getString("MA") + "__";
					spTEN += spRs.getString("TEN") + "__";
					spDONVI += spRs.getString("DONVI") + "__";
					spSOLUONG += formater.format(spRs.getDouble("SOLUONG")) + "__";
					spGIANHAP += formater.format(spRs.getDouble("tonggiatri")) + "__";
					spCHIETKHAU += "0__";
					spTHUEVAT += "0__";
					spSCHEME += spRs.getString("SCHEME") + "__";
					
					spTRONGLUONG += spRs.getString("trongluong") + "__";
					spTHETICH += spRs.getString("thetich") + "__";
					
					spQuyDoi += spRs.getString("spQuyDoi") + "__";
					spchietkhaugia += "0__";
					iskht += "0__";
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
					
					spTHUEVAT = spTHUEVAT.substring(0, spTHUEVAT.length() - 2);
					this.spVAT = spTHUEVAT.split("__");
					
					spSCHEME = spSCHEME.substring(0, spSCHEME.length() - 2);
					this.spSCheme = spSCHEME.split("__");
					
					spTRONGLUONG = spTRONGLUONG.substring(0, spTRONGLUONG.length() - 2);
					this.spTrongluong = spTRONGLUONG.split("__");
					
					spTHETICH = spTHETICH.substring(0, spTHETICH.length() - 2);
					this.spThetich = spTHETICH.split("__");
					
					spQuyDoi = spQuyDoi.substring(0, spQuyDoi.length() - 2);
					this.spQuyDoi = spQuyDoi.split("__");
					
					spchietkhaugia = spchietkhaugia.substring(0, spchietkhaugia.length() - 2);
					this.spchietkhaugia = spchietkhaugia.split("__");
					
					iskht = iskht.substring(0, iskht.length() - 2);
					this.spIskhongthue = iskht.split("__");
					
				}
				
				//INIT CHIET KHAU
				if(this.loaidonhang.equals("0"))
				{
					query = "select DIENGIAI, GIATRI, LOAI from ERP_DONDATHANG_CHIETKHAU where DONDATHANG_FK = '" + this.id + "' order by pk_seq asc";
					System.out.println("[INIT_CK]"+query);
					ResultSet rsCK = db.get(query);
					if(rsCK != null)
					{
						String dkCK_diengiai = "";
						String dkCK_giatri = "";
						String dkCK_loai = "";
						
						while(rsCK.next())
						{
							dkCK_diengiai += rsCK.getString("DIENGIAI") + "__";
							dkCK_giatri += formater.format(rsCK.getDouble("GIATRI")) + "__";
							dkCK_loai += rsCK.getString("LOAI") + "__";
						}
						rsCK.close();
						
						if(dkCK_diengiai.trim().length() > 0)
						{
							dkCK_diengiai = dkCK_diengiai.substring(0, dkCK_diengiai.length() - 2);
							this.dhCk_diengiai = dkCK_diengiai.split("__");
							
							dkCK_giatri = dkCK_giatri.substring(0, dkCK_giatri.length() - 2);
							this.dhCk_giatri = dkCK_giatri.split("__");
							
							dkCK_loai = dkCK_loai.substring(0, dkCK_loai.length() - 2);
							this.dhCk_loai = dkCK_loai.split("__");
						}
						
					}
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
		String query = "select isnull(ishm,0) ishm,isnull(isdhkhac,0) isdhkhac, isnull(isingia,0) isingia,ngaydonhang, ngaydenghi, ISNULL(ghichu, '') as ghichu, dvkd_fk, kbh_fk, npp_fk, kho_fk, isnull(chietkhau, 0) as chietkhau, vat, loaidonhang,iskm, " +
						" isnull( tungay, '' ) as tungay, isnull( denngay, '' ) as denngay, isnull(isETC, 0) as isETC, gsbh_fk, ddkd_fk, "+
						" 	( select MaHopDong from ERP_HOPDONG where PK_SEQ = a.hopdong_fk ) as mahopdong, danhmucKH, phanloai	" +
						"from ERP_Dondathang a where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					this.ngayyeucau = rs.getString("ngaydonhang");
					this.ngaydenghi = rs.getString("ngaydenghi");
					this.ghichu = rs.getString("ghichu");
					this.dvkdId = rs.getString("dvkd_fk");
					this.kbhId = rs.getString("kbh_fk");
					this.nppId = rs.getString("npp_fk");
					this.khoNhanId = rs.getString("kho_fk");
					this.loaidonhang = rs.getString("loaidonhang");
					this.chietkhau = rs.getString("chietkhau");
					this.vat = rs.getString("vat");
					this.iskm = rs.getString("iskm") == null ? "0" : rs.getString("iskm");
					this.isdhk=rs.getString("isdhkhac");
					this.isgia=rs.getString("isingia");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.ETC = rs.getString("isETC");
					
					this.mahopdong = rs.getString("mahopdong") == null ? "" : rs.getString("mahopdong");
					this.gsbhId = rs.getString("gsbh_fk") == null ? "" : rs.getString("gsbh_fk");
					this.ddkdId = rs.getString("ddkd_fk") == null ? "" : rs.getString("ddkd_fk");
					
					this.danhmucKH = rs.getString("danhmucKH") == null ? "" : rs.getString("danhmucKH");
					this.phanloai = rs.getString("phanloai") == null ? "" : rs.getString("phanloai");
					this.ishm=rs.getString("ishm");
				}
				rs.close();
				
				//INIT SO LUONG CHI TIET
				//query = "select (select MA from SANPHAM where pk_seq = a.sanpham_fk ) as spMA,  solo, ngayhethan, ngaynhapkho, isnull( mame, '' ) as mame, isnull( mathung, '' ) as mathung, soluong, isnull(scheme, ' ') as scheme " +
						//"from ERP_DONDATHANG_SANPHAM_CHITIET a where dondathang_fk = '" + this.id + "'";
				
				query = "select (select MA from ERP_SANPHAM where pk_seq = a.sanpham_fk ) as spMA, solo, ngayhethan, ngaynhapkho, mathung, mame, maphieu, isnull( bin.ma, '' ) as vitri, marq, isnull( nsx.ma, '' ) as nsx, isnull(scheme, ' ') as scheme, sum(soluong)  as soluong  " +
						"from ERP_DONDATHANG_SANPHAM_CHITIET a left join ERP_BIN bin on a.bin_fk = bin.pk_seq left join ERP_NHASANXUAT nsx on a.nsx_fk = nsx.pk_seq " + 
						"where dondathang_fk = '" + this.id + "' " + 
						"group by a.sanpham_fk, solo, ngayhethan, ngaynhapkho, mathung, mame, maphieu, bin.ma, marq, nsx.ma, scheme  ";
				System.out.println("---INIT SP CHI TIET: " + query);
				rs = db.get(query);
				if(rs != null)
				{
					Hashtable<String, String> sp_soluong = new Hashtable<String, String>();
					while(rs.next())
					{
						//System.out.println("---KEY BEAN: " + ( rs.getString("spMA") + "__" + rs.getString("scheme") + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("vitri") + "__" + rs.getString("mathung") + "__" + rs.getString("mame") ) );
						//sp_soluong.put( rs.getString("spMA") + "__" + rs.getString("scheme") + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("vitri") + "__" + rs.getString("mathung") + "__" + rs.getString("mame"), rs.getString("soluong") );
						String scheme = ( rs.getString("scheme").trim().length() <= 0 ? " " : rs.getString("scheme") );
						String key = rs.getString("spMA") + "__" + scheme + "__" + rs.getString("solo") + "__" + rs.getString("ngayhethan") + "__" + rs.getString("vitri") + "__" + rs.getString("mathung") + "__" + rs.getString("mame") + "__" + rs.getString("maphieu") + "__" + rs.getString("marq") + "__" + rs.getString("nsx")+ "__" + rs.getString("ngaynhapkho"); 
						
						System.out.println( "::: KEY BEAN:  " + key + " -- SLG: " + rs.getString("soluong") );
						sp_soluong.put( key , rs.getString("soluong") );
					}
					rs.close();
					
					this.sanpham_soluong = sp_soluong;
					this.sanpham_soluongDAXUAT = this.sanpham_soluong;
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}
		
		if( this.loaidonhang.equals("5") )
			this.initSANPHAM_NoiBo();
		else
			this.initSANPHAM();
		
		this.createRs();
		
	}

	public void DBclose() {
		
		try{
			
			if(khoNhanRs!=null){
				khoNhanRs.close();
			}
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	private String getDateTime_CongMot() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        
        String[] arr = dateFormat.format(date).split("-");	
        
		Calendar cal = Calendar.getInstance();    
		cal.set(Calendar.DAY_OF_MONTH, Integer.parseInt(arr[2]));
		cal.set(Calendar.MONTH, Integer.parseInt(arr[1]) - 1);
		cal.set(Calendar.YEAR, Integer.parseInt(arr[0]) );
		
		cal.add(Calendar.DAY_OF_MONTH, 1);
		
        String nam = Integer.toString(cal.get(Calendar.YEAR));
        String thang = Integer.toString( cal.get(Calendar.MONTH) + 1);
        if(thang.trim().length() < 2)
        	thang = "0" + thang;
        
        String ngay = Integer.toString(cal.get(Calendar.DAY_OF_MONTH));
        if(ngay.trim().length() < 2)
        	ngay = "0" + ngay;
		
        return nam + "-" + thang + "-" + ngay;	
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
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

	public String getNgaydenghi() {
		
		return this.ngaydenghi;
	}

	public void setNgaydenghi(String ngaydenghi) {
		
		this.ngaydenghi = ngaydenghi;
	}

	public ResultSet getDvtRs() {

		return this.dvtRs;
	}


	public void setDvtRs(ResultSet dvtRs) {
		
		this.dvtRs = dvtRs;
	}

	
	public String getSchemeId() {
		
		return this.schemeId;
	}

	
	public void setSchemeId(String kbhId) {
		
		this.schemeId = kbhId;
	}

	
	public ResultSet getSchemeRs() {
		
		return this.schemeRs;
	}

	
	public void setSchemeRs(ResultSet schemeRs) {
		
		this.schemeRs = schemeRs;
	}

	public ResultSet getSpTheoScheme(String scheme, String tongluong) 
	{
		String query =  "select c.MA, c.TEN, d.donvi, '' as soluong " +
						"from TIEUCHITHUONGTL_SPTRA a inner join TIEUCHITHUONGTL b on a.thuongtl_fk = b.PK_SEQ " +
						"	inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ " +
						"	inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ " +
						"where b.PK_SEQ = N'" + scheme+ "'";
		return db.get(query);
	}

	
	public Hashtable<String, String> getScheme_Soluong() {
	
		return this.scheme_soluong;
	}


	public void setScheme_Soluong(Hashtable<String, String> scheme_soluong) {
		
		this.scheme_soluong = scheme_soluong;
	}


	public boolean createKMTichLuy() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}
		
		if( this.schemeId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn SCHEME";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query = " insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert ERP_DONDATHANG_KMTICHLUY( Dondathang_fk, CTKM_FK, tongluong ) " +
							"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
							"from TIEUCHITHUONGTL where SCHEME = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMTICHLUY: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if(key.startsWith(spId[i]))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
										"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
										"from ERP_SANPHAM where MA = '" + _spTRA[1] + "' ";
								
								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public boolean updateKMTichLuy() 
	{

		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query =	" Update ERP_Dondathang set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
							" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANG_KMTICHLUY where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_KMTICHLUY " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert ERP_DONDATHANG_KMTICHLUY( Dondathang_fk, CTKM_FK, tongluong ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
							"from TIEUCHITHUONGTL where SCHEME = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMTICHLUY: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if(key.startsWith(spId[i]))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
										"select '" + this.id + "', pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
										"from ERP_SANPHAM where MA = '" + _spTRA[1] + "' ";
								
								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public ResultSet getSpTheoScheme_UngHang(String scheme, String tongluong)
	{
		/*String query = " select b.MA, b.TEN, d.donvi, sum(a.SOLUONG) as soluong   " +
					   "from DONHANG_CTKM_TRAKM a inner join SANPHAM b on a.SPMA = b.MA 	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
					   "	inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ " +
					   "where a.CTKMID in (" + scheme + ") and SPMA is not null and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' )   " +
					   "group by b.MA, b.TEN, d.donvi ";*/
		
		String query =  " select UNGHANG.PK_SEQ, UNGHANG.MA, UNGHANG.TEN, UNGHANG.DONVI, " +
						" 	UNGHANG.soluong - ISNULL(DUYET.SOLUONG, 0) as soluong " +
						" from " +
						" ( " +
						" 	select b.PK_SEQ, b.MA, b.TEN, d.donvi, sum(a.SOLUONG) as soluong   " +
						" 	from DONHANG_CTKM_TRAKM a inner join ERP_SANPHAM b on a.SPMA = b.MA 	inner join CTKHUYENMAI c on a.CTKMID = c.PK_SEQ " +
						" 			inner join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ  " +
						" 	where a.CTKMID = '" + scheme + "' and SPMA is not null and a.DONHANGID in ( select pk_seq from DONHANG where trangthai = '1' and NPP_FK = '" + this.nppId + "' )  " +
						" 	group by b.PK_SEQ, b.MA, b.TEN, d.donvi " +
						" ) " +
						" UNGHANG left join " +
						" ( " +
						" 	select b.sanpham_fk, SUM(b.soluong) as soluong  " +
						" 	from ERP_DONDATHANG a inner join ERP_DONDATHANG_SANPHAM b on a.PK_SEQ = b.dondathang_fk " +
						" 	where a.LoaiDonHang = '1' and a.TRANGTHAI in (0, 1) and b.ctkm_fk = '" + scheme + "' and a.NPP_FK = '" + this.nppId + "'  " + ( this.id.trim().length() > 0 ? " and a.PK_SEQ != '" + this.id + "' " : "" ) +
						" 	group by b.sanpham_fk " +
						" ) " +
						" DUYET on UNGHANG.PK_SEQ = DUYET.sanpham_fk ";
		
		return db.get(query);
	}

	
	public boolean createKMUngHang() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query = " insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert ERP_DONDATHANG_KMUNGHANG( Dondathang_fk, CTKM_FK, tongluong ) " +
							"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
							"from CTKHUYENMAI where SCHEME = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMUNGHANG: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if(key.startsWith(spId[i]))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
										"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
										"from ERP_SANPHAM where MA = '" + _spTRA[1] + "' ";
								
								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public boolean updateKMUngHang() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query =	" Update ERP_Dondathang set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
							" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANG_KMUNGHANG where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_KMUNGHANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert ERP_DONDATHANG_KMUNGHANG( Dondathang_fk, CTKM_FK, tongluong ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
							"from CTKHUYENMAI where SCHEME = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_KMTICHLUY: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							System.out.println("----KEY LA: " + key);
							if(key.startsWith(spId[i]))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, ctkm_fk ) " +
										"select '" + this.id + "', pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
										"from ERP_SANPHAM where MA = '" + _spTRA[1] + "' ";
								
								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
		
	}


	public ResultSet getSpTheoScheme_TrungBay(String scheme, String tongluong) 
	{
		String query =  "select distinct c.MA, c.TEN, d.donvi, '' as soluong  " +
						"from TRATRUNGBAY_SANPHAM a inner join TRATRUNGBAY b on a.TRATRUNGBAY_FK = b.PK_SEQ  " +
						"	inner join ERP_SANPHAM c on a.sanpham_fk = c.PK_SEQ  " +
						"	inner join DONVIDOLUONG d on c.DVDL_FK = d.PK_SEQ   " +
						"where b.PK_SEQ in ( select TRATRUNGBAY_FK from CTTB_TRATB where CTTRUNGBAY_FK = '" + scheme + "' )";
		
		return db.get(query);

	}

	public boolean createTrungBay() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query = " insert ERP_Dondathang(ngaydonhang, ngaydenghi, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua) " +
						   " values('" + this.ngayyeucau + "', '" + this.ngaydenghi + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', '" + dvkdId + "', '" + kbhId + "', '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert ERP_DONDATHANG_TRUNGBAY( Dondathang_fk, CTTB_FK, tongluong ) " +
							"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
							"from CTTRUNGBAY where SCHEME = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_TRUNGBAY: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							if(key.startsWith(spId[i]))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, cttb_fk ) " +
										"select IDENT_CURRENT('ERP_Dondathang'), pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
										"from ERP_SANPHAM where MA = '" + _spTRA[1] + "' ";
								
								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	
	public boolean updateTrungBay() 
	{
		
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.ngaydenghi.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đề nghị giao hàng";
			return false;
		}

		if( this.dvkdId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn đơn vị kinh doanh";
			return false;
		}
		
		if( this.kbhId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kênh bán hàng";
			return false;
		}
		
		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}
		
		if( this.khoNhanId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn kho hàng";
			return false;
		}
		
		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					//CHECK TOTAL KHONG DUOC VUOT QUA TONG LUONG CO THE NHAN
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách SCHEME trả cho NPP";
				return false;
			}
			
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "null" : this.khoNhanId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.trim();
			
			String query =	" Update ERP_Dondathang set ngaydonhang = '" + this.ngayyeucau + "', ngaydenghi = '" + this.ngaydenghi + "', ghichu = N'" + this.ghichu + "', " +
							" 	dvkd_fk = '" + this.dvkdId + "', kbh_fk = '" + this.kbhId + "', npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "' " + 
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANG_TRUNGBAY where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_TRUNGBAY " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0)
				{
					query = "insert ERP_DONDATHANG_TRUNGBAY( Dondathang_fk, CTTB_FK, tongluong ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "' " + 
							"from CTTRUNGBAY where SCHEME = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_TRUNGBAY: " + query;
						db.getConnection().rollback();
						return false;
					}
			
					if(this.scheme_soluong != null)
					{
						Enumeration<String> keys = this.scheme_soluong.keys();
						while(keys.hasMoreElements())
						{
							String key = keys.nextElement();
							System.out.println("----KEY LA: " + key);
							if(key.startsWith(spId[i]))
							{
								String[] _spTRA = key.split("__");
								String _soluongTRA = "0"; 
								if(this.scheme_soluong.get(key) != null)
									_soluongTRA = this.scheme_soluong.get(key);
								
								query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, dongia, dvdl_fk, cttb_fk ) " +
										"select '" + this.id + "', pk_seq, '" + _soluongTRA + "', '0', dvdl_fk, '" + _spTRA[0] + "' " +
										"from ERP_SANPHAM where MA = '" + _spTRA[1] + "' ";
								
								System.out.println("1.2.Insert NK - SP: " + query);
								if(!db.update(query))
								{
									this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	
	public String[] getSpScheme() {

		return this.spSCheme;
	}


	public void setSpScheme(String[] spScheme) {
		
		this.spSCheme = spScheme;
	}

	public ResultSet getCongnoRs() {

		return this.congnoRs;
	}


	public void setCongnoRs(ResultSet congnoRs) {
		
		this.congnoRs = congnoRs;
	}

	public String[] getSpChietkhau() {
		
		return this.spChietkhau;
	}

	
	public void setSpChietkhau(String[] spChietkhau) {
		
		this.spChietkhau = spChietkhau;
	}

	public String[] getSpVat() {
		
		return this.spVAT;
	}

	
	public void setSpVat(String[] spVat) {
		
		this.spVAT = spVat;
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

	String[] spQuyDoi;
	
  public String[] getSpQuyDoi()
  {
	  return spQuyDoi;
  }

  public void setSpQuyDoi(String[] spQuyDoi)
  {
		this.spQuyDoi =spQuyDoi;
  }
  
	public String getIsKm()
  {
  	return iskm;
  }

	public void setIsKm(String iskm)
  {
  	this.iskm = iskm;
  }
	
	public String getIsdhk() {
		return isdhk;
	}

	public void setIsdhk(String isdhk) {
		this.isdhk = isdhk;
	}

	String isgia;
	public String getIsgia() {
		return isgia;
	}

	public void setIsgia(String isgia) {
		this.isgia = isgia;
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

	public boolean createNoiBo() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.tungay.trim().length() < 10)
		{
			this.msg = "Vui lòng chọn từ ngày";
			return false;
		}
		
		if(this.denngay.trim().length() < 10)
		{
			this.msg = "Vui lòng chọn đến ngày";
			return false;
		}

		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if(spMa[i].trim().length() > 0 && spMa[j].trim().length() > 0 )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[i].trim().equals(spSCheme[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "100023" : this.khoNhanId.trim();
			String dvkd_fk = this.dvkdId.trim().length() <= 0 ? "NULL" : this.dvkdId.trim();
			String kbh_fk = this.kbhId.trim().length() <= 0 ? "NULL" : this.kbhId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			
			String query = " insert ERP_Dondathang(ngaydonhang, tungay, denngay, loaidonhang, ghichu, trangthai, dvkd_fk, kbh_fk, npp_fk, kho_fk, chietkhau, vat, ngaytao, nguoitao, ngaysua, nguoisua,tructhuoc_fk,isKM,isdhkhac,isingia,phanloai) " +
						   " values('" + this.ngayyeucau + "', '" + this.tungay + "', '" + this.denngay + "', '" + this.loaidonhang + "', N'" + this.ghichu + "', '0', " + dvkd_fk + ", " + kbh_fk + ", '" + nppId + "', " + khonhan_fk + ", '" + chietkhau + "', '" + vat + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "',1, 0, 0, 0, '" + this.phanloai + "' )";
			
			System.out.println("1.Insert DDH: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_Dondathang') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();
			
			System.out.println("DDH ID: " + this.id);
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";
					
					String tienvat = this.sptienvat[i].trim().replaceAll(",", "");
					if(tienvat.trim().length() < 0)
						tienvat = "0";
					
					query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, soluongCHUAN, dongia, chietkhau, thueVAT, dvdl_fk, scheme,tienvat ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), N'" + spSCheme[i] + "',"+ tienvat +" " +
							"from ERP_SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//Lưu chiết khấu quý
			if( this.schemeNoibo != null )
			{
				Enumeration<String> keys = schemeNoibo.keys();
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					
					query = "insert ERP_DONDATHANG_CHIETKHAU( DONDATHANG_FK, DIENGIAI, GIATRI, LOAI, THANHTIEN,tienbvat,tienvat ) " + 
							" values( '" + this.id + "', N'" + key + "', '" + this.schemeNoibo.get(key).replaceAll(",", "") + "', '0', '" + this.schemeNoibo.get(key).replaceAll(",", "") + "','" + this.schemeNoibo_bvat.get(key).replaceAll(",", "") + "','" + this.schemeNoibo_vat.get(key).replaceAll(",", "") + "' )";
					System.out.println("1.Insert DH - CK: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//CHÈN DANH SÁCH HÓA ĐƠN SỬ DỤNG
			String condition="";
			String condition_hdkhac = " ";
			if( this.nppId.trim().length() > 0 )
			{
				condition += " and HD.NPP_FK = '" + this.nppId + "'  ";
				condition_hdkhac += " and HD.NPP_FK = '" + this.nppId + "'  ";
			
			}
			if( this.tungay.trim().length() > 0 )
			{
				condition += " and HD.NGAYXUATHD >= '" + this.tungay + "'  ";
				condition_hdkhac += " and HD.NGAYGHINHAN >= '" + this.tungay + "'  ";
			}
			if( this.denngay.trim().length() > 0 )
			{
				condition += " and HD.NGAYXUATHD <= '" + this.denngay + "'  ";
				condition_hdkhac += " and   HD.NGAYGHINHAN <= '" + this.denngay + "'  ";
			}
			
			//ETC
			String condition_ETCDT = "\n  and HD.TRANGTHAI in ( 2, 4 )  and HD.mauhoadon = '1'  and isnull(HD.LoaiHoaDon,0)=0  and HD.kbh_fk=100052 and HD.NPP_DAT_FK is not null and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_ETCDT_HK = "\n  and HD.TRANGTHAI in ( 1 )   and HD.kbh_fk=100052 and HD.NPP_DAT_FK is not null and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			
			String condition_ETCCN = "\n and HD.TRANGTHAI in ( 2, 4 ) and HD.mauhoadon = '1' and isnull(HD.LoaiHoaDon,0)=0 and HD.khachhang_fk is not null   and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCCN' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_ETCCN_HK = "\n and HD.TRANGTHAI in ( 1)  and HD.khachhang_fk is not null and HD.kbh_fk=100052 and     hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCCN' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			
			String condition_OTCDT = "\n HD.TRANGTHAI in ( 2, 4 ) and HD.mauhoadon = '1'  and isnull(HD.LoaiHoaDon,0)=0  and HD.kbh_fk=100025 and HD.NPP_DAT_FK is not null  and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_OTCDT_HK = "\n and  HD.TRANGTHAI in ( 1 )   and HD.kbh_fk=100025 and HD.NPP_DAT_FK is not null  and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			//OTC
			String condition_OTC = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTC' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_OTC_HK = "\n  and HD.TRANGTHAI in ( 1 )   and HD.kbh_fk=100025 and HD.khachhang_fk is not null and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTC' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			
			//KM
			String condition_KM = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'KM' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			//KM
			String condition_HM = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'HM' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
		
			
			
		
			
			String query1="";
			String query2="";
			
			if( this.phanloai.equals("ETCDT") || this.phanloai.equals("ETCCN") || this.phanloai.equals("OTCDT") )
			{
				
				query1 = "\n  insert ERP_DONDATHANG_HOADON( dondathang_fk, hoadon_fk, phanloai )  " + 
						"\n  	select '" + this.id + "', HD.PK_SEQ, 0 as phanloai " + 
						"\n  	from ERP_HOADONNPP HD      " + 
						"\n  	where HD.TRANGTHAI in ( 2, 4 )  " + condition ;
				query2=
						"\n  	select '" + this.id + "', HD.PK_SEQ, 0 as phanloai " + 
						"\n  	from ERP_HOADONKHAC HD      " + 
						"\n  	where HD.TRANGTHAI in ( 1 )   " + condition_hdkhac ;
				if(this.phanloai.equals("ETCDT"))
				{
					query1+=condition_ETCDT;
					query2+=condition_ETCDT_HK;
					
					query=query1+"  union all " +query2;
				}
				if(this.phanloai.equals("ETCCN"))
				{
					query+=condition_ETCCN;
					query2+=condition_ETCCN_HK;
					
					query=query1+"  union all " +query2;
				}
				if(this.phanloai.equals("OTCDT"))
				{
					query+=condition_OTCDT;
					query2+=condition_OTCDT_HK;
					query=query1+"  union all " +query2;
				}
			}
			else if( this.phanloai.equals("OTC")  )
			{
				query = "  insert ERP_DONDATHANG_HOADON( dondathang_fk, hoadon_fk, phanloai )  " + 
						"  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
						"  	from HOADON HD      " + 
						"  	where HD.TRANGTHAI in ( 2, 4 )  " + condition + condition_OTC+
						" union all"+
						"  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
						"  	from ERP_HOADONKHAC HD      " + 
						"  	where HD.TRANGTHAI in (1 )   " + condition_hdkhac +condition_OTC_HK ;
						
				
			}
			else if( this.phanloai.equals("KM")  || this.phanloai.equals("HM") )
			{
				query = "  insert ERP_DONDATHANG_HOADON( dondathang_fk, hoadon_fk, phanloai )  " + 
						"  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
						"  	from HOADON HD      " + 
						"\n   			inner join HOADON_DDH HDD on HDD.HOADON_FK = HD.PK_SEQ       " + 
						"\n   			inner join DONHANG DH on HDD.DDH_FK = DH.PK_SEQ       " + 
						"  	where HD.TRANGTHAI in ( 2, 4 ) and  isnull(hd.loaihoadon,0)<>0  " + condition ;
				if(this.phanloai.equals("KM"))
				{
					query+=condition_KM +" and isnull(DH.donquatang,0)<>1";
					query+="\n union all "+
							"\n  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
							"\n  	from ERP_HOADONNPP HD      " + 
							"\n  	where HD.TRANGTHAI in ( 2, 4 )  " + condition + condition_KM +" and isnull(hd.loaihoadon,0)=1" ;
				}
				
				if(this.phanloai.equals("HM"))
				{
					query+=condition_HM + " and isnull(DH.donquatang,0)=1 and  isnull(hd.loaihoadon,0)<>0 ";
				}
			}	
				System.out.println("query insert vao la "+query);	
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_DONDATHANG_HOADON: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean updateNoiBo() 
	{
		if(this.ngayyeucau.trim().length() < 10)
		{
			this.msg = "Vui lòng nhập ngày đơn hàng";
			return false;
		}
		
		if(this.tungay.trim().length() < 10)
		{
			this.msg = "Vui lòng chọn từ ngày";
			return false;
		}
		
		if(this.denngay.trim().length() < 10)
		{
			this.msg = "Vui lòng chọn đến ngày";
			return false;
		}

		if( this.nppId.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn nhà phân phối";
			return false;
		}

		if(spMa == null)
		{
			this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm đặt hàng";
			return false;
		}
		else
		{
			boolean coSP = false;
			for(int i = 0; i < spMa.length; i++)
			{
				if( spMa[i].trim().length() > 0 )
				{
					if(spSoluong[i].trim().length() <= 0)
					{
						this.msg = "Bạn phải nhập số lượng của sản phẩm ( " + spTen[i] + " )";
						return false;
					}
					
					coSP = true;
				}
			}
			
			if(!coSP)
			{
				this.msg = "Vui lòng kiểm tra lại danh sách sản phẩm";
				return false;
			}
			
			//CHECK TRUNG MA 
			for(int i = 0; i < spMa.length - 1; i++)
			{
				for(int j = i + 1; j < spMa.length; j++)
				{
					if( spMa[i].trim().equals(spMa[j].trim()) && spSCheme[i].trim().equals(spSCheme[j].trim()) )
					{
						if( spMa[i].trim().equals(spMa[j].trim()) )
						{
							this.msg = "Sản phẩm ( " + spTen[i] + " )  đã bị trùng.";
							return false;
						}
					}
				}
			}	
		}
		
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String khonhan_fk = this.khoNhanId.trim().length() <= 0 ? "100023" : this.khoNhanId.trim();
			String dvkd_fk = this.dvkdId.trim().length() <= 0 ? "NULL" : this.dvkdId.trim();
			String kbh_fk = this.kbhId.trim().length() <= 0 ? "NULL" : this.kbhId.trim();
			String chietkhau = this.chietkhau.trim().length() <= 0 ? "0" : this.chietkhau.replaceAll(",", "").trim();
			String vat = this.vat.trim().length() <= 0 ? "0" : this.vat.replaceAll(",", "").trim();
			
			String query =	" Update ERP_Dondathang set ngaydonhang = '" + this.ngayyeucau + "', tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', ghichu = N'" + this.ghichu + "', " +
							" 	dvkd_fk = " + dvkd_fk + ", kbh_fk = " + kbh_fk + ", npp_fk = '" + this.nppId + "', kho_fk = " + khonhan_fk + ", ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "', chietkhau = '" + chietkhau + "', vat = '" + vat + "', phanloai = '" + this.phanloai + "' " +
							" where pk_seq = '" + this.id + "' ";
		
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang " + query;
				db.getConnection().rollback();
				return false;
			}
						
			query = "delete ERP_Dondathang_SANPHAM where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_Dondathang_SANPHAM " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANG_CHIETKHAU where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_CHIETKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DONDATHANG_HOADON where Dondathang_fk = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_DONDATHANG_HOADON " + query;
				db.getConnection().rollback();
				return false;
			}
			
			for(int i = 0; i < spMa.length; i++)
			{
				if(spMa[i].trim().length() > 0 && spSoluong[i].trim().length() > 0 )
				{
					String ck = "0";
					if(spChietkhau[i].trim().length() > 0)
						ck = spChietkhau[i].trim().replaceAll(",", "");
					
					String thueVAT = this.spVAT[i].trim().replaceAll(",", "");
					if(thueVAT.trim().length() < 0)
						thueVAT = "0";
					
					String tienvat = this.sptienvat[i].trim().replaceAll(",", "");
					if(tienvat.trim().length() < 0)
						tienvat = "0";
					
					query = "insert ERP_Dondathang_SANPHAM( Dondathang_fk, SANPHAM_FK, soluong, soluongCHUAN, dongia, chietkhau, thueVAT, dvdl_fk, scheme,tienvat ) " +
							"select '" + this.id + "', pk_seq, '" + spSoluong[i].replaceAll(",", "") + "', '" + spSoluong[i].replaceAll(",", "") + "', '" + spGianhap[i].replaceAll(",", "") + "', '" + ck + "', '" + thueVAT + "', ISNULL( ( select pk_Seq from DONVIDOLUONG where donvi = N'" + spDonvi[i].trim() + "' ), DVDL_FK ), N'" + spSCheme[i] + "',"+tienvat+" " +
							"from ERP_SANPHAM where MA = '" + spMa[i].trim() + "' ";
					
					System.out.println("1.Insert NK - SP: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_Dondathang_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//Lưu chiết khấu quý
			if( this.schemeNoibo != null )
			{
				Enumeration<String> keys = schemeNoibo.keys();
				while( keys.hasMoreElements() )
				{
					String key = keys.nextElement();
					
					query = "insert ERP_DONDATHANG_CHIETKHAU( DONDATHANG_FK, DIENGIAI, GIATRI, LOAI, THANHTIEN ,tienbvat,tienvat) " + 
							" values( '" + this.id + "', N'" + key + "', '" + this.schemeNoibo.get(key).replaceAll(",", "") + "', '0', '" + this.schemeNoibo.get(key).replaceAll(",", "") + "','" + this.schemeNoibo_bvat.get(key).replaceAll(",", "") + "','" + this.schemeNoibo_vat.get(key).replaceAll(",", "") + "' )";
					System.out.println("1.Insert DH - CK: " + query);
					if(!db.update(query))
					{
						this.msg = "Khong the tao moi ERP_DONDATHANG_CHIETKHAU: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			//CHÈN DANH SÁCH HÓA ĐƠN SỬ DỤNG
			String condition="";
			String condition_hdkhac = " ";
			if( this.nppId.trim().length() > 0 )
			{
				condition += " and HD.NPP_FK = '" + this.nppId + "'  ";
				condition_hdkhac += " and HD.NPP_FK = '" + this.nppId + "'  ";
			
			}
			if( this.tungay.trim().length() > 0 )
			{
				condition += " and HD.NGAYXUATHD >= '" + this.tungay + "'  ";
				condition_hdkhac += " and HD.NGAYGHINHAN >= '" + this.tungay + "'  ";
			}
			if( this.denngay.trim().length() > 0 )
			{
				condition += " and HD.NGAYXUATHD <= '" + this.denngay + "'  ";
				condition_hdkhac += " and   HD.NGAYGHINHAN <= '" + this.denngay + "'  ";
			}
			
			//ETC
			String condition_ETCDT = "\n  and HD.TRANGTHAI in ( 2, 4 )  and HD.mauhoadon = '1'  and isnull(HD.LoaiHoaDon,0)=0  and HD.kbh_fk=100052 and HD.NPP_DAT_FK is not null and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_ETCDT_HK = "\n  and HD.TRANGTHAI in ( 1 )   and HD.kbh_fk=100052 and HD.NPP_DAT_FK is not null and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			
			String condition_ETCCN = "\n and HD.TRANGTHAI in ( 2, 4 ) and HD.mauhoadon = '1' and isnull(HD.LoaiHoaDon,0)=0 and HD.khachhang_fk is not null   and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCCN' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_ETCCN_HK = "\n and HD.TRANGTHAI in ( 1)  and HD.khachhang_fk is not null and HD.kbh_fk=100052 and     hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'ETCCN' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			
			String condition_OTCDT = "\n HD.TRANGTHAI in ( 2, 4 ) and HD.mauhoadon = '1'  and isnull(HD.LoaiHoaDon,0)=0  and HD.kbh_fk=100025 and HD.NPP_DAT_FK is not null  and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_OTCDT_HK = "\n HD.TRANGTHAI in ( 1 )   and HD.kbh_fk=100025 and HD.NPP_DAT_FK is not null  and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 0 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTCDT' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			//OTC
			String condition_OTC = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTC' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			String condition_OTC_HK = "\n  and HD.TRANGTHAI in ( 1 )   and HD.kbh_fk=100025 and HD.khachhang_fk is not null and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'OTC' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			
			//KM
			String condition_KM = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'KM' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
			
			//KM
			String condition_HM = "\n and hd.pk_seq not in ( select hoadon_fk from ERP_DONDATHANG_HOADON where phanloai = 1 and dondathang_fk in ( select PK_SEQ from ERP_DONDATHANG where LoaiDonHang = '5' and phanloai = 'HM' and NPP_FK = '" + this.nppId + "' and TRANGTHAI != 3 and pk_seq != " + ( this.id.trim().length() <= 0 ? "1" : this.id ) + " ) ) ";
		
			
			
		
			
			String query1="";
			String query2="";
			
			if( this.phanloai.equals("ETCDT") || this.phanloai.equals("ETCCN") || this.phanloai.equals("OTCDT") )
			{
				
				query1 = "\n  insert ERP_DONDATHANG_HOADON( dondathang_fk, hoadon_fk, phanloai )  " + 
						"\n  	select '" + this.id + "', HD.PK_SEQ, 0 as phanloai " + 
						"\n  	from ERP_HOADONNPP HD      " + 
						"\n  	where HD.TRANGTHAI in ( 2, 4 )  " + condition ;
				query2=
						"\n  	select '" + this.id + "', HD.PK_SEQ, 0 as phanloai " + 
						"\n  	from ERP_HOADONKHAC HD      " + 
						"\n  	where HD.TRANGTHAI in ( 1 )  " + condition_hdkhac ;
				if(this.phanloai.equals("ETCDT"))
				{
					query1+=condition_ETCDT;
					query2+=condition_ETCDT_HK;
					
					query=query1+"  union all " +query2;
				}
				if(this.phanloai.equals("ETCCN"))
				{
					query+=condition_ETCCN;
					query2+=condition_ETCCN_HK;
					
					query=query1+"  union all " +query2;
				}
				if(this.phanloai.equals("OTCDT"))
				{
					query+=condition_OTCDT;
					query2+=condition_OTCDT_HK;
					query=query1+"  union all " +query2;
				}
			}
			else if( this.phanloai.equals("OTC")  )
			{
				query = "  insert ERP_DONDATHANG_HOADON( dondathang_fk, hoadon_fk, phanloai )  " + 
						"  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
						"  	from HOADON HD      " + 
						"  	where HD.TRANGTHAI in ( 2, 4 )  " + condition + condition_OTC+
						" union all"+
						"  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
						"  	from ERP_HOADONKHAC HD      " + 
						"  	where HD.TRANGTHAI in (1 )  " + condition_hdkhac +condition_OTC_HK ;
						
				
			}
			else if( this.phanloai.equals("KM")  || this.phanloai.equals("HM") )
			{
				query = "  insert ERP_DONDATHANG_HOADON( dondathang_fk, hoadon_fk, phanloai )  " + 
						"  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
						"  	from HOADON HD      " + 
						"\n   			inner join HOADON_DDH HDD on HDD.HOADON_FK = HD.PK_SEQ       " + 
						"\n   			inner join DONHANG DH on HDD.DDH_FK = DH.PK_SEQ       " + 
						"  	where HD.TRANGTHAI in ( 2, 4 )  " + condition ;
				if(this.phanloai.equals("KM"))
				{
					query+=condition_KM +" and isnull(DH.donquatang,0)<>1";
					query+="\n union all "+
							"\n  	select '" + this.id + "', HD.PK_SEQ, 1 as phanloai " + 
							"\n  	from ERP_HOADONNPP HD      " + 
							"\n  	where HD.TRANGTHAI in ( 2, 4 )  " + condition + condition_KM +" and isnull(hd.loaihoadon,0)=1" ;
				}
				
				if(this.phanloai.equals("HM"))
				{
					query+=condition_HM + " and isnull(DH.donquatang,0)<>1";
				}
			}	
				System.out.println("query insert vao la "+query);	
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi ERP_DONDATHANG_HOADON: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	
	public ResultSet getSoloTheoSp(String spMa, String donvi, String tongluong)
	{
		tongluong = tongluong.replaceAll(",", "");
		double sum = Double.parseDouble(tongluong);
		//System.out.println("---TONG LUONG: " + tongluong );
		String kho = this.khoNhanId, scheme = "";
		String soloDACHON = "";
		if( this.sanpham_soluongDAXUAT != null )
		{
			//KEY: MA - SOLO; 
			Enumeration<String> keys = this.sanpham_soluongDAXUAT.keys();
			while( keys.hasMoreElements() )
			{
				String key = keys.nextElement();
				System.out.println("kEY======: " + key );
				if(key.startsWith(spMa))
				{
					String[] arr = this.mySplit(key, "__" );
					scheme = arr[1];
					String solo = arr[2];
					String ngayhethan = arr[3];
					String vitri = arr[4];
					String mathung = arr[5];
					String mame = arr[6];
					String maphieu = arr[7];
					String marq = arr[8];
					String nsx = arr[9];
					String ngaynhapkho= arr[10];
					
					String daxuat =  this.sanpham_soluongDAXUAT.get(key).replaceAll(",", "");
					sum = sum - Double.parseDouble(daxuat);
					if( daxuat.trim().length() > 0 )
						soloDACHON += "select '" + solo + "' as soloCHON, '" + ngayhethan + "' as ngayhethanCHON, '" + ngaynhapkho + "' as ngaynhapkhoCHON, N'" + vitri + "' as vitriCHON, '" + mathung + "' as mathungCHON, '" + mame + "' as mameCHON,'" + maphieu + "' as maphieuCHON, '" + marq + "' as marqCHON,'" + nsx + "' as nsxCHON, " + daxuat + " as soluongDACHON union ";
					
				}
			}
		}

		String query = "";
		
		if(scheme.trim().length() > 0)
		{
			kho = "(select kho_fk from erp_dondathang_ctkm_trakm where dondathangid = " + this.id + " and spma = '" + spMa + "' and ctkmid = (select pk_seq from ctkhuyenmai where scheme = '" + scheme + "'))";
		}
		tongluong = sum + "";
		if( soloDACHON.trim().length() > 0 )
		{
			soloDACHON = soloDACHON.substring(0, soloDACHON.length() - 7 );
			soloDACHON = " select soloCHON, ngayhethanCHON,ngaynhapkhoCHON, vitriCHON, mathungCHON, mameCHON, maphieuCHON, marqCHON, nsxCHON, SUM( soluongDACHON ) as soluongDACHON " +
						 " from ( " + soloDACHON + " ) DT group by soloCHON, ngayhethanCHON, ngaynhapkhoCHON, vitriCHON, mathungCHON, mameCHON, maphieuCHON, marqCHON, nsxCHON ";
		}
		else
			soloDACHON = " select '1' as soloCHON, '' as ngayhethanCHON,'' as ngaynhapkhoCHON,  '' as vitriCHON, '' mathungCHON, '' mameCHON, '' maphieuCHON, '' marqCHON, '' nsxCHON, 0 as soluongDACHON ";
		
		
		String sqlDONHANG = "";
		if( this.id.trim().length() > 0 )
			sqlDONHANG = " select SUM(soluong) from ERP_DONDATHANG_SANPHAM_CHITIET where dondathang_fk = '" + this.id + "' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu and marq = DT.marq and isnull(nsx_fk, 0) = DT.nsx_fk ";
		else
			sqlDONHANG = " select SUM(soluong) from ERP_DONDATHANG_SANPHAM_CHITIET where dondathang_fk = '1' and SANPHAM_FK = DT.sanpham_fk and solo = DT.solo and ngayhethan = DT.ngayhethan and isnull( bin_fk, 0 ) = isnull( DT.bin_fk, 0 ) and mathung = DT.mathung and mame = DT.mame and maphieu = DT.maphieu and marq = DT.marq and isnull(nsx_fk, 0) = DT.nsx_fk ";
		
		String conditionKHONGDUOCPB = " select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
							  		  " where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngayyeucau + "' and '" + this.ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk != '" + this.nppId + "' ) ";
		
		String conditionDUOCPB = "  select b.solo from ERP_PHANBODONHANG a inner join ERP_PHANBODONHANG_SANPHAM b on a.PK_SEQ = b.phanbo_fk and b.sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) " +
				  				 " 	where a.TRANGTHAI = '1' and a.tungay <= '" + this.ngayyeucau + "' and '" + this.ngayyeucau + "' <= a.denngay and a.PK_SEQ in ( select phanbo_fk from ERP_PHANBODONHANG_DOITUONG where doituong_fk = '" + this.nppId + "' ) ";
		
		query =  "\n select  distinct isnull(pb.vt,0) vt, DT.SOLO, DT.NGAYHETHAN, DT.NGAYNHAPKHO, DT.MATHUNG, DT.MAME, DT.MAPHIEU, isnull(bin.MA, '') as vitri, DT.MARQ, isnull(nsx.MA, '') as nsx, " + 
				 //"\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) as AVAILABLE, '' as tudexuat  "+
				 "\n DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) as AVAILABLE, '' as tudexuat  "+
				 "\n from "+
				 "\n ( "+
				 "\n 	select ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN,ct.NGAYNHAPKHO,  ct.mathung, ct.mame, ct.maphieu, isnull( ct.bin_fk, 0 ) as bin_fk, ct.marq, isnull( ct.nsx_fk, 0 ) as nsx_fk, sum(ct.AVAILABLE) as AVAILABLE  "+
				 "\n 	from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.sanpham_fk = sp.pk_seq  "+
				 "\n 	where KHOTT_FK = " + kho + " and SANPHAM_FK = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )   " +
				 		"  and ngaynhapkho <= '" + this.ngayyeucau + "' "+
				 		"  and ( isnull(sp.batbuockiemdinh, 0) = 0 or ct.KHOTT_FK in (100074, 100078) or ( sp.batbuockiemdinh = 1 and isnull( maphieu, '' ) != ''  ) )		" +
				 "\n	group by ct.sanpham_fk, ct.SOLO, ct.NGAYHETHAN,ct.NGAYNHAPKHO,  ct.mathung, ct.mame, ct.maphieu, isnull( ct.bin_fk, 0 ), ct.marq, isnull( ct.nsx_fk, 0 ) " +
				 "\n ) "+
				 "\n DT left join ERP_BIN bin on DT.bin_fk = bin.pk_seq "+
				 "\n left join ERP_NHASANXUAT nsx on DT.nsx_fk = nsx.pk_seq left join  "+
				 "\n ( "+
				 	soloDACHON +
				 "\n ) "+
				 "\n DAXUAT on DT.SOLO = DAXUAT.soloCHON  and DT.NGAYHETHAN = DAXUAT.ngayhethanCHON and DT.NGAYNHAPKHO = DAXUAT.ngaynhapkhoCHON and isnull( bin.ma, '' ) = DAXUAT.vitriCHON  "+
				 "\n		and DT.MATHUNG = DAXUAT.mathungCHON and DT.MAME = DAXUAT.mameCHON and DT.MAPHIEU = DAXUAT.maphieuCHON and DT.MArq = DAXUAT.marqCHON and DT.nsx_fk = DAXUAT.nsxCHON " +
				 "\n left join ( "+
				 "\n		 select '1' as vt,solo,ngayhethan,NGAYNHAPKHO, doituong_fk,sanpham_fk from ERP_PHANBODONHANG_DOITUONG a "+
				 "\n		  inner join ERP_PHANBODONHANG_SANPHAM b "+
				 "\n		on a.phanbo_fk=b.phanbo_fk where "+
				 "\n		a.doituong_fk='" + this.nppId + "' and b.sanpham_fk=( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' )  "+
				 "\n		) pb on pb.solo=DT.SOLO  and pb.sanpham_fk=DT.SANPHAM_FK  "+
				 //"\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) - isnull(DAXUAT.soluongDACHON, 0) > 0 "+
				 "\n where DT.AVAILABLE + isnull( ( " + sqlDONHANG + " ), 0) > 0 "+
				 "\n			and DT.SOLO not in ( select solo from ERP_HANGCHOPHANBO where sanpham_fk = ( select pk_seq from ERP_SANPHAM where ma = '" + spMa + "' ) )	" +
				 "\n 			and ( DT.SOLO not in ( " + conditionKHONGDUOCPB + " ) or DT.SOLO in ( " + conditionDUOCPB + " ) )	" +
				 "\n order by isnull(vt,0) DESC ,NGAYHETHAN asc ,NGAYNHAPKHO ASC ";
		
		System.out.println("----LAY SO LO ( " + spMa + " ): " + query );
		String query2 = "";
		ResultSet rs = db.get(query);
		try 
		{
			double total = 0;
			while(rs.next())
			{
				double slg = 0;
				double avai = rs.getDouble("AVAILABLE");
				
				total += avai;
				
				if(total < Double.parseDouble(tongluong))
				{
					slg = avai;
				}
				else
				{
					slg =  Double.parseDouble(tongluong) - ( total - avai );
				}
					
				if(slg >= 0)
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '" + slg + "' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu,'" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("nsx") + "' as nsx ,'" + rs.getString("ngaynhapkho") + "' as ngaynhapkho  union ALL ";
					
				}
				else
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + rs.getString("SOLO") + "' as SOLO, '" + rs.getString("NGAYHETHAN") + "' as NGAYHETHAN, '" + rs.getString("VITRI") + "' as VITRI, '' as tuDEXUAT,'" + rs.getString("MATHUNG") + "' as MATHUNG,'" + rs.getString("MAME") + "' as MAME, '" + rs.getString("MAPHIEU") + "' as maphieu,'" + rs.getString("MARQ") + "' as MARQ, '" + rs.getString("nsx") + "' as nsx ,'" + rs.getString("ngaynhapkho") + "' as ngaynhapkho union ALL ";
				}
			}
			rs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		
		/*String query2 = "";
		NodeList rs = WebService.ExecQueryFromERP(query, "geso@@tra@@90123");
		try 
		{
			double total = 0;
			for( int i = 0; i < rs.getLength(); i++ ) 
			{
				Element element = (Element) rs.item(i);
				
				double slg = 0;
				double avai = Double.parseDouble( WebService.getValues( element, "AVAILABLE" ) );
				String solo = WebService.getValues( element, "SOLO" );
				String ngayhethan = WebService.getValues( element, "NGAYHETHAN" );
				String vitri = WebService.getValues( element, "vitri" );
				String mathung = WebService.getValues( element, "MATHUNG" );
				String mame = WebService.getValues( element, "MAME" );
				//String tudexuat =  WebService.getValues( element, "tudexuat" );
				
				if(this.id.trim().length() <= 0 )
				{
					total += avai;
					
					if(total < Double.parseDouble(tongluong))
					{
						slg = avai;
					}
					else
					{
						slg =  Double.parseDouble(tongluong) - ( total - avai );
					}
						
					if(slg >= 0)
					{
						query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '" + slg + "' as tuDEXUAT union ALL ";
					}
					else
					{
						query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '' as tuDEXUAT union ALL ";
					}
				}
				else //Khi vào cập nhật thì không đề xuất lại nữa
				{
					query2 += "select '" + avai + "' as AVAILABLE, '" + solo + "' as SOLO, '" + ngayhethan + "' as NGAYHETHAN, '" + vitri + "' as VITRI, '" + mathung + "' as mathung, '" + mame + "' as mame, '' as tuDEXUAT union ALL ";
				}
			}
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("EXCEPTION INIT SOLO: " + e.getMessage());
		}
		*/
		if(query2.trim().length() > 0)
		{
			query2 = query2.substring(0, query2.length() - 10);
			System.out.println("---TU DONG DE XUAT BIN - LO: " + query2 );
			return db.get(query2);
		}
		
		return null;
	}
	
	public Hashtable<String, String> getSanpham_Soluong() {
		
		return this.sanpham_soluong;
	}

	
	public void setSanpham_Soluong(Hashtable<String, String> sp_soluong) {
		
		this.sanpham_soluong = sp_soluong;
	}
	
	public Hashtable<String, String> getSanpham_SoluongDAXUAT_THEODN() 
	{
		return this.sanpham_soluongDAXUAT;
	}


	public void setSanpham_SoluongDAXUAT_THEODN(Hashtable<String, String> sp_soluong) 
	{
		this.sanpham_soluongDAXUAT = sp_soluong;
	}
	
	public String getETC()
	{
		return this.ETC;
	}

	public void setETC(String ETC) 
	{
		this.ETC = ETC;
	}
	
	public String getMahopdong() {
		
		return this.mahopdong;
	}

	public void setMahopdong(String ma) {
		
		this.mahopdong = ma;
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

	
	public String getDanhmucKH() {
	
		return this.danhmucKH;
	}


	public void setDanhmucKH(String danhmucKH) {
		
		this.danhmucKH = danhmucKH;
	}

	
	public Hashtable<String, String> getSchemeNoibo() {

		return this.schemeNoibo;
	}


	public void setSchemeNoibo(Hashtable<String, String> schemeNoibo) {
		
		this.schemeNoibo = schemeNoibo;
	}

	public String getPhanloai() {

		return this.phanloai;
	}

	public void setPhanloai(String phanloai) {
		
		this.phanloai = phanloai;
	}
	
	public String[] getSpchietkhaugia() {
		return spchietkhaugia;
	}

	public void setSpchietkhaugia(String[] spchietkhaugia) {
		this.spchietkhaugia = spchietkhaugia;
	}
	
}
