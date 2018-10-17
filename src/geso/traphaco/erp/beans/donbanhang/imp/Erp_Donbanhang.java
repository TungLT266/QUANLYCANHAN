package geso.traphaco.erp.beans.donbanhang.imp;

import geso.traphaco.erp.beans.donbanhang.IErp_Donbanhang;
import geso.traphaco.erp.beans.donbanhang.IErp_Donbanhang_SP;
import geso.traphaco.erp.beans.donbanhang.ISanpham;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.Utility_Kho;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class Erp_Donbanhang implements IErp_Donbanhang
{
	String congtyId;
	String Id,NgayGiaoDich;
	String TrangThai;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	double ChietKhau;
	double VAT;
	double CKTrucTiep;
	double UngCK;
	double CKThuongMai;
	String Msg;
	String NppId;
	String NppTen;
	String khoId;
	String KhoTen;
	String so;
	String DvkdId;
	String Diachi;
	String DiaChiXhd;
	String Masothue;
	double TongTienTruocVAT;
	double TongTienSauVAT;
	double TongTienKM;
	double TongTienSauKM;
	String IDNhaCungCap;
	String TenNhaCungCap;
	String KenhBanHangId;
	String ISKM;
	String maketoStock;
	String UserId;
	String Sopo;
	String TTten;
	String hopdongten;
	
	String hanmucno;
	String tongsono;
	String noquahan;
	String notronghan;
	String sonovuothanmuc;
	String ycgdduyet;
	String dathanhtoan;
	
	String thoihanno;
	String ngayvuotno;
	
	String tungaytk;
	String denngaytk;
	String khachhangtk;
	String loaidhtk;
	String trangthaitk;
	String donvikinhdoanhtk;
	String sochungtutk;
	String nguoitaotk;
	String khodattk;
	String kbhtk;
	
	
	double trongluong=0;
	
	String duyetdh;
	/*
	 * loai chietkhau =0 la chiet khau tien,1 la chiet khau%
	 */
	String loaichietkhau = "0";
	String SoSO = "";
	ResultSet rskenh;
	ResultSet chitietCongnoRs;
	ResultSet rskho;
	ResultSet rsnhapp;
	ResultSet rsnhacc;
	ResultSet rsdvkd;
	String userten;
	String ghichu;
	String noidungchietkhau;
	String bgId;
	String[] Scheme;
	String[] Sotien;
	List<IErp_Donbanhang_SP> listsanpham = new ArrayList<IErp_Donbanhang_SP>();
	Hashtable<String, Integer> spThieuList;
	
	///trakhuyen mai
	Hashtable<String, Float> scheme_tongtien = new Hashtable<String, Float>();
	Hashtable<String, Float> scheme_chietkhau = new Hashtable<String, Float>();
	List<ISanpham> scheme_sanpham = new ArrayList<ISanpham>();
	
	ResultSet hopdongRs;
	String hopdongId;
	String loaihopdong;
	
	ResultSet tienteRs;
	String tienteId;
	String tygia;
	String chophepsuagia;
	String chophepdoiKH;
	
	String ngaydukiengiao;
	dbutils db;

	double dungsai = 0;
	String hinhthuctt = "";
	String etd = "", paymentTerms = "", deliveryTerms = "", remarks = "";
	double freightCost = 0;
	
	String customerspo;
	
	String[] tichluy_scheme;
	String[] tichluy_tongtien;
	
	ResultSet dvdlRs;
	String loaidonhang;
	String diachigiaohang;
	String bantructiep = "1";	
	
	ResultSet schemeRs;
	String schemeIds;
	Utility_Kho util_kho = new Utility_Kho();
	
	
	public Erp_Donbanhang( String id )
	{
		db = new dbutils();
		this.Id = id;
		this.ISKM = "0";
		NgayGiaoDich = "";
		TrangThai = "";
		NgayTao = "";
		NguoiTao = "";
		NgaySua = "";
		NguoiSua = "";
		ChietKhau = 0;
		VAT = 10;
		UngCK=0;
		CKTrucTiep=0;
		CKThuongMai=0;
		Msg = "";
		NppId = "";
		NppTen = "";
		khoId = "";
		KhoTen = "";
		so = "";
		DvkdId = "";
		TongTienTruocVAT = 0;
		TongTienSauVAT = 0;
		this.TongTienKM = 0;
		this.TongTienSauKM = 0;
		IDNhaCungCap = "";
		TenNhaCungCap = "";
		KenhBanHangId = "";
		SoSO = "";
		Diachi = "";
		DiaChiXhd = "";
		Masothue = "";
		this.ghichu = "";
		this.noidungchietkhau = "";
		this.maketoStock = "1";
		this.ngaydukiengiao = "";
		this.hopdongId = "";
		this.tienteId = "100000";
		this.tygia = "1";
		this.chophepsuagia = "0";
		this.chophepdoiKH = "0";
		this.loaihopdong = "";
		this.hopdongten="";
		this.SoTienBaoHiem="0";
		this.TTten="";
		this.customerspo = "";
		this.trongluong=0;
		this.hanmucno = "0";
		this.tongsono = "0";
		this.noquahan = "0";
		this.notronghan = "0";
		this.thoihanno = "0";
		this.ngayvuotno = "0";
		this.sonovuothanmuc = "0";
		this.ycgdduyet="";
		this.dathanhtoan="";
		this.duyetdh="";
		this.loaidonhang = "1";
		this.diachigiaohang ="";
		this.bgId = "";
		this.schemeIds = "";
		this.bantructiep = "";
	}
	
	public Erp_Donbanhang( )
	{
		db = new dbutils();
		this.Id = "";
		this.ISKM = "0";
		NgayGiaoDich = "";
		TrangThai = "";
		NgayTao = "";
		NguoiTao = "";
		NgaySua = "";
		NguoiSua = "";
		ChietKhau = 0;
		VAT = 10;
		UngCK=0;
		CKTrucTiep=0;
		CKThuongMai=0;
		Msg = "";
		NppId = "";
		NppTen = "";
		khoId = "";
		KhoTen = "";
		so = "";
		DvkdId = "";
		TongTienTruocVAT = 0;
		TongTienSauVAT = 0;
		this.TongTienKM = 0;
		this.TongTienSauKM = 0;
		IDNhaCungCap = "";
		TenNhaCungCap = "";
		KenhBanHangId = "";
		SoSO = "";
		Diachi = "";
		DiaChiXhd = "";
		Masothue = "";
		this.ghichu = "";
		this.noidungchietkhau = "";
		this.maketoStock = "1";
		this.ngaydukiengiao = "";
		this.hopdongId = "";
		this.tienteId = "100000";
		this.tygia = "1";
		this.chophepsuagia = "0";
		this.TTten="";
		this.chophepdoiKH = "0";
		this.loaihopdong = "";
		this.hopdongten="";
		this.SoTienBaoHiem="0";
		
		this.customerspo = "";
		this.Sopo="";
		this.trongluong=0;
		
		this.hanmucno = "0";
		this.tongsono = "0";
		this.noquahan = "0";
		this.notronghan = "0";
		this.thoihanno = "0";
		this.ngayvuotno = "0";
		this.sonovuothanmuc = "0";
		this.ycgdduyet="0";
		this.dathanhtoan="0";
		this.duyetdh="";
		this.loaidonhang = "1";
		this.diachigiaohang ="";
		this.bgId = "";
		this.schemeIds = "";
		this.bantructiep = "";
	}
	
	public String formatTienVND( String tienVND){
		
		/*String chuyendoi= tienVND.replaceAll(",", "@");
		chuyendoi= chuyendoi.replaceAll("[.]", ",");
		chuyendoi= chuyendoi.replaceAll("@", ".");*/
		return tienVND;	
	}
	
	public String formatTienVND_2( String tienVND_2){
		
		/*String chuyendoi= tienVND_2.replaceAll("[.]", "#");
		chuyendoi= chuyendoi.replaceAll(",", ".");
		chuyendoi= chuyendoi.replaceAll("#", ",");
		return chuyendoi;	*/
		return tienVND_2;
	}
	
	
	
	public String getCustomerspo() {
		return customerspo;
	}

	public void setCustomerspo(String customerspo) {
		this.customerspo = customerspo;
	}

	public String getId()
	{
		return this.Id;
	}
	
	public void setId(String id)
	{
		this.Id = id;
	}
	
	public String getNgaygiaodich()
	{
		return this.NgayGiaoDich;
	}
	
	public void setNgaygiaodich(String ngaygiaodich)
	{
		this.NgayGiaoDich = ngaygiaodich;
	}
	
	public String getNppTen()
	{
		return this.NppTen;
	}
	
	public void setNppTen(String _nppTen)
	{
		this.NppTen = _nppTen;
	}
	
	public String getTrangthai()
	{
		return this.TrangThai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.TrangThai = trangthai;
	}
	
	public String getNgaytao()
	{
		return this.NgayTao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.NgayTao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.NguoiTao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.NguoiTao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.NgaySua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.NgaySua = ngaysua;
	}
	
	public String getNguoisua()
	{
		return this.NguoiSua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.NguoiSua = nguoisua;
	}
	
	public double getChietkhau()
	{
		return this.ChietKhau;
	}
	
	public void setChietkhau(double chietkhau)
	{
		this.ChietKhau = chietkhau;
	}
	
	public double getVAT()
	{
		return this.VAT;
	}
	
	public void setVAT(double vat)
	{
		this.VAT = vat;
	}
	
	public String getMessage()
	{
		return this.Msg;
	}
	
	public void setMessage(String msg)
	{
		this.Msg = msg;
	}
	
	public void setrs_nhacc(ResultSet rsncc)
	{
		this.rsnhacc = rsncc;
	}
	
	public ResultSet GetRsnhacc()
	{
		return this.rsnhacc;
	}
	
	public String getIdNhaCungCap()
	{
		return this.IDNhaCungCap;
	}
	
	public void setIdNhaCungCap(String idnhacc)
	{
		this.IDNhaCungCap = idnhacc;
	}
	
	public String getTenNhaCungCap()
	{
		return this.TenNhaCungCap;
	}
	
	public void setTenNhaCungCap(String tennhacc)
	{
		this.TenNhaCungCap = tennhacc;
	}
	
	public void setListSanPham(List<IErp_Donbanhang_SP> list)
	{
		this.listsanpham = list;
	}
	
	public String getIDKenhBanHang()
	{
		return this.KenhBanHangId;
	}
	
	public void setIDKenhBanHang(String kenhbanhangid)
	{
		this.KenhBanHangId = kenhbanhangid;
	}
	
	public void setrs_kbh(ResultSet _rskenh)
	{
		this.rskenh = _rskenh;
	}
	
	public ResultSet GetRsKbh()
	{
		return this.rskenh;
	}
	
	public List<IErp_Donbanhang_SP> getListSanPham()
	{
		return this.listsanpham;
	}
	
	public String getNppId()
	{
		return this.NppId;
	}
	
	public void setNppId(String nppId)
	{
		this.NppId = nppId;
	}
	
	public void setrs_nhapp(ResultSet rsnpp)
	{
		this.rsnhapp = rsnpp;
	}
	
	public ResultSet GetRsnhapp()
	{
		return this.rsnhapp;
	}
	
	public String getKhottId()
	{
		return this.khoId;
	}
	
	public void setKhottId(String khottid)
	{
		this.khoId = khottid;
	}
	
	public void setRskhott(ResultSet rs_kho)
	{
		this.rskho = rs_kho;
	}
	
	public ResultSet GetRskhott()
	{
		return this.rskho;
	}
	
	public String getKhottTen()
	{
		return this.KhoTen;
	}
	
	public void setKhottTen(String KhottTen)
	{
		this.KhoTen = KhottTen;
	}
	
	public double getTongtientruocVAT()
	{
		return this.TongTienTruocVAT;
	}
	
	public void setTongtientruocVAT(double tttvat)
	{
		this.TongTienTruocVAT = tttvat;
	}
	
	public double getTongtiensauVAT()
	{
		return this.TongTienSauVAT;
	}
	
	public void setTongtiensauVAT(double ttsvat)
	{
		this.TongTienSauVAT = ttsvat;
	}
	
	NumberFormat formatter = new DecimalFormat("#######.###");
	private boolean coKhuyenmai;
	public void Init()
	{
		System.out.println("id: "+this.Id);
		if(this.Id!=null && this.Id.trim().length() > 0)
		{
			db = new dbutils();
			String sql ="   select isnull(ddh.sopo,'') as sopo  , isnull(ddh.customerspo, '') as customerspo, ddh.ghichu, ddh.noidungchietkhau, ddh.pk_seq, ngaydat, NGAYDUKIENGIAO, iskm, isnull (sotienbvat, 0) as sotienbvat, ddh.nguoitao, ddh.nguoisua, ddh.trangthai, ddh.makeToStock, " +
						" 	khachhang_fk, npp.congty_fk, isnull (vat,0) as vat, isnull (sotienavat,0) as sotienavat, ddh.dvkd_fk, ddh.kbh_fk, " +
						" 	isnull(chietkhau,0) as chietkhau, isnull(NPP.ungck,0) as ungck,isnull(NPP.CKTrucTiep,0) as CKTrucTiep,isnull(ckthuongmai,0) as ckthuongmai,isnull(loaidonhang,'0') as loaidonhang, isnull(loaichietkhau,'0') as loaichietkhau, ddh.khott_fk,  " +
						" 	npp.ma + ', ' + npp.ten as tennpp, npp.diachigiaohang as diachixhd, npp.diachi, npp.mst as masothue, isnull(khuyenmai.tongtienKM, 0) as tongtienKM, ddh.tiente_fk, ddh.CHOPHEPSUAGIA, ddh.FROM_HOPDONG, isnull(ddh.SoTienBaoHiem, 0) as SoTienBaoHiem, " +
						" 	isnull(ddh.dungsai, 0) as dungsai, isnull(ddh.hinhthuctt, '') as hinhthuctt, isnull(ddh.paymentterms, '') as paymentterms, " +
						"	isnull(ddh.deliveryterms, '') as deliveryterms, isnull(ddh.etd, '') as etd, isnull(ddh.remarks, '') as remarks, " +
						"	isnull(ddh.FreightCost, 0) as FreightCost, ISNULL(ddh.BGID, 0) AS BGID " +
						"   from ERP_DonDatHang ddh " +
						"   inner join ERP_KhachHang npp on npp.pk_seq = ddh.KhachHang_FK " +
						"   left join " +
						"   ( " +
						" 	select '" + this.Id + "' as ddhId, isnull(sum(tonggiatri), 0) as tongtienKM from erp_dondathang_ctkm_trakm " +
						" 	where dondathangId = '" + this.Id + "' and spMa is  null " +
						"   ) khuyenmai on ddh.pk_seq = khuyenmai.ddhId " +
						"   where  ddh.pk_Seq = " + this.Id;
			
			System.out.println("vao day : "+sql);
			ResultSet rs = db.get(sql);
			try
			{
				if (rs != null)
				{
					if (rs.next())
					{
						this.NgayGiaoDich = rs.getString("ngaydat");
						this.ngaydukiengiao = rs.getString("NGAYDUKIENGIAO");
						this.IDNhaCungCap = rs.getString("congty_fk");
						this.khoId = rs.getString("khott_fk");
						this.TrangThai=rs.getString("trangthai");
						this.setISKM(rs.getString("iskm"));
						this.setNgaygiaodich(rs.getString("ngaydat"));
						this.setIDKenhBanHang(rs.getString("kbh_fk"));
						this.setdvkdid(rs.getString("dvkd_fk"));
						
						this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
						this.VAT = rs.getDouble("VAT");
						//CK = UNGCK + CKTrucTiep
						this.CKTrucTiep=rs.getDouble("CKTrucTiep");
						this.CKThuongMai=rs.getDouble("ckthuongmai");
						this.UngCK=rs.getDouble("ungck");
						this.ChietKhau = rs.getDouble("chietkhau");
						
						this.TongTienKM = rs.getDouble("tongtienKM");
						this.freightCost = rs.getDouble("FreightCost");
						this.Sopo=rs.getString("sopo");
						this.setMakeToStock(rs.getString("makeToStock"));
						this.setdiachi(rs.getString("diachi"));
						this.setdiachixhd(rs.getString("diachixhd"));
						this.setmasothue(rs.getString("masothue"));
						this.setGhichu(rs.getString("ghichu"));
						this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
						//this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk");
						this.tienteId = rs.getString("tiente_fk") == null ? "100000" : rs.getString("tiente_fk");
						this.chophepsuagia = rs.getString("CHOPHEPSUAGIA") == null ? "0" : rs.getString("CHOPHEPSUAGIA");
						this.chophepdoiKH = rs.getString("FROM_HOPDONG") == null ? "0" : rs.getString("FROM_HOPDONG");
						this.loaidonhang = rs.getString("loaidonhang");
						
						if(this.chophepdoiKH.equals("1") && this.NppId.trim().length() <= 0 )
						{
							this.NppId = rs.getString("khachhang_fk");
							this.setNppTen(rs.getString("tennpp"));
						}
						this.SoTienBaoHiem=rs.getString("SoTienBaoHiem")==null?"0":formatter.format(rs.getDouble("SoTienBaoHiem"));
						
						this.dungsai = rs.getFloat("dungsai");
						this.hinhthuctt = rs.getString("hinhthuctt");
						this.paymentTerms = rs.getString("PAYMENTTERMS");
						this.deliveryTerms = rs.getString("DELIVERYTERMS");
						this.etd = rs.getString("ETD");
						this.remarks = rs.getString("REMARKS");
						this.customerspo = rs.getString("customerspo");
					}
					rs.close();
				}
				
				// Thuc hien lay thong tin don hang
				if(this.listsanpham.size() <= 0)
				{
					String strkho=this.khoId;
					if(this.khoId.equals("100003")|| this.khoId.equals("100004")){
						strkho="100003,100004";
					}
					String sql_getdetail = 
						"   select ddh_sp.khott_fk ,dondathang_fk, ddh_sp.sanpham_fk, ma, ten, " +
						"   a.trongluong, a.thetich, donvi, soluong, scheme, " +
						" 	isnull(a.loaisanpham_fk, 0) as loaisanpham_fk, isnull(a.CHUNGLOAI_FK, 0) as CHUNGLOAI_FK,  " +
						"	 dongia,   isnull(ddh_sp.ngaydukiengiao, '') as ngaydukiengiao,  " +
						"  ddh_sp.ghichu , isnull(ton.ton, 0) as ton " +
						"   from ERP_DonDatHang_sp ddh_sp  inner join erp_sanpham a on a.pk_seq = sanpham_fk  " +
					    "   left join  ( select SANPHAM_FK , AVAILABLE  as Ton, KHOTT_FK " +
					    "   from ERP_KHOTT_SANPHAM  " +
					    "	where KHOTT_FK  in ( " + strkho +")"+
					    "	 ) TON on TON.SANPHAM_FK = ddh_sp.SANPHAM_FK AND  TON.KHOTT_FK = ddh_sp.KHOTT_FK	  " +
						"   inner join ERP_DonDatHang ddh on ddh.pk_Seq =ddh_sp.dondathang_fk " +
						"   where  ddh_sp.dondathang_fk = '" + this.Id + "' order by ddh_sp.pk_seq" ;
					
					System.out.println("___Khoi tao don dat hang: " + sql_getdetail);
					this.listsanpham.clear();
					rs = db.get(sql_getdetail);
					if (rs != null)
					{
						while (rs.next())
						{
							IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setDonViTinh(rs.getString("donvi"));
							sanpham.setId(rs.getString("dondathang_fk"));
							sanpham.setIdSanPham(rs.getString("sanpham_fk"));
							sanpham.setGhichusp(rs.getString("ghichu"));
							sanpham.setTenSanPham(rs.getString("ten"));
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setKhoid(rs.getString("khott_fk"));
							double soluong = rs.getDouble("soluong");
							if(soluong > 0)
							{
								sanpham.setSoLuong(rs.getDouble("soluong"));
							}
							else
							{
								sanpham.setSoLuong(0);
							}
							
							sanpham.setSHEME(rs.getString("scheme"));
							sanpham.setTrongluong(rs.getString("trongluong"));
							sanpham.setThetich(rs.getString("thetich"));
							
							 
								if(maketoStock.equals("1"))
									sanpham.setSoluongton(rs.getDouble("soluong") + rs.getDouble("ton"));
								else
									sanpham.setSoluongton(rs.getDouble("ton"));
							 
							
							//sanpham.setTenXuatHoaDon(rs.getString("tenXHD"));
							sanpham.setNgaydukiengiao(rs.getString("ngaydukiengiao"));
							
							this.listsanpham.add(sanpham);
						}
					}
					rs.close();
					//Khoi tao tra khuyen mai
				}
				this.getTrakhuyenmai();
			}
			catch (Exception er)
			{
				er.printStackTrace();
				//System.out.println("1.Loi khoi tao:" + er.toString());
			}		
		
		} else {
			
			//Lấy thông tin hợp đồng nếu cần thiết
			if(hopdongId != null && hopdongId.trim().length() > 0) {
				String query = 
					" select ISNULL(PAYMENTTERMS, '') AS PAYMENTTERMS, ISNULL(DELIVERYTERMS, '') AS DELIVERYTERMS, ISNULL(ETD, '') AS ETD, ISNULL(REMARKS, '') AS REMARKS, ISNULL(FreightCost, 0) AS FreightCost " +
				    " from Erp_HopDong where PK_SEQ = " + hopdongId + " ";
				
				ResultSet rs = db.get(query);
				if (rs != null)
				{
					try {
						if (rs.next())
						{
							this.paymentTerms = rs.getString("PAYMENTTERMS");
							this.deliveryTerms = rs.getString("DELIVERYTERMS");
							this.etd = rs.getString("ETD");
							this.remarks = rs.getString("REMARKS");
							this.freightCost = rs.getDouble("FreightCost");
						}						
						rs.close();
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
		}
		
		CreateRs();
	}
	
	public void CreateRs()
	{
		this.dvdlRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where CONGTY_FK = '" + this.congtyId + "'");
		
		String sql = "select pk_seq, ten from erp_congty where trangthai = '1' and pk_seq = '" + this.congtyId + "' ";
		this.rsnhacc = db.get(sql);
 
		if(this.NppId.length() == 0){
			sql = "select pk_seq, ten " +
			  	  "from kenhbanhang where trangthai != '2' ";
		}else{
			sql = "SELECT KBH.PK_SEQ, KBH.TEN " +
				  "FROM ERP_KHACHHANG_KENHBANHANG KH_KENH " +
				  "INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = KH_KENH.KENHBANHANG_FK " +
				  "WHERE KH_KENH.KHACHHANG_FK = " + this.NppId + "";
		}
		
		this.rskenh = db.get(sql);
		
		sql = "select pk_seq, ma + ', ' + ten as ten from erp_tiente ";
		this.tienteRs = db.get(sql);
		
		/*sql=	" select pk_seq, case when pk_seq=100003 then N'Thành phẩm HP' else  ten end as ten  from erp_khott where trangthai=1 AND PK_SEQ IN (SELECT KHOTT_FK FROM NHANVIEN_KHOTT WHERE NHANVIEN_FK="+this.UserId+" ) " +
				" and pk_seq in (100003 ,100012,100013,100014,100000,100001,100002)  ";*/
		
		sql= " SELECT PK_SEQ, CASE WHEN PK_SEQ=100003 THEN N'Kho thành phẩm - Hải Phòng' ELSE "+ 
			 " TEN END AS TEN  FROM ERP_KHOTT WHERE TRANGTHAI=1 AND PK_SEQ IN (SELECT KHOTT_FK FROM NHANVIEN_KHOTT WHERE NHANVIEN_FK="+this.UserId+" ) "+ 
			 " AND PK_SEQ IN (100003 ,100012,100013,100014,100000,100001,100002,100023,100024) ";
		
		this.rskho= db.get(sql);
		
		if(this.tienteId.trim().length() > 0)
		{
			sql = "select tigiaquydoi from ERP_TiGia where pk_seq = '" + this.tienteId + "' and trangthai = '1' ";
			ResultSet rs = db.get(sql);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						this.tygia = Double.toString(rs.getDouble("tigiaquydoi"));
					}
					rs.close();
				} 
				catch (Exception e) {
					this.tygia = "1";
				}
			}
		}
		
		sql = "select pk_seq, donvikinhdoanh as ten from donvikinhdoanh where trangthai = '1' ";
		if(this.IDNhaCungCap.trim().length() > 0)
			sql += " and pk_seq in ( select dvkd_fk from erp_congty_dvkd where congty_fk = '" + this.IDNhaCungCap + "' ) ";
		
		this.rsdvkd = db.get(sql);
		
		if(this.listsanpham.size() >0){
			for(int i=0;i <this.listsanpham.size();i++){
				IErp_Donbanhang_SP sp=this.listsanpham.get(i);
				this.GetGia(sp);
			}
		}
		
		//if(this.KenhBanHangId.equals("100001") && this.NppId.trim().length() > 0 )
		if( this.NppId.trim().length() > 0 )
		{
			String spList = "";
			if(this.listsanpham.size() >0){
				for(int i=0;i <this.listsanpham.size();i++){
					spList += this.listsanpham.get(i).getIdSanPham() + ",";
				}
			}
			//Lấy những ctkm còn hiệu lực
			sql = "\n select pk_seq, scheme, diengiai  " +
				  "\n  from CTKHUYENMAI " + 
				  "\n  where pk_seq in ( select ctkm_fk from CTKM_NPP where khachhang_fk = '" + this.NppId + "' ) and tungay <= '" + this.NgayGiaoDich + "' and '" + this.NgayGiaoDich + "' <= denngay " + 
				  "\n        and pk_seq in ( select ctkm_fk from PHANBOKHUYENMAI where KHACHHANG_FK = '" + this.NppId + "' )  ";
				  
			if(spList.length() > 0){
				spList = spList.substring(0, spList.length() - 1);
				sql += "\n  and pk_seq in (select dk.CTKHUYENMAI_FK "+
						"\n from CTKM_DKKM dk inner join DIEUKIENKM_SANPHAM sp on sp.DIEUKIENKHUYENMAI_FK = dk.DKKHUYENMAI_FK " +
						"\n where sp.SANPHAM_FK in ("+spList+")) ";
			}
			sql += "order by scheme asc";			
			System.out.println("get scheme " + sql);
			this.schemeRs = db.get(sql);
		}
		
	}
	public Double  GetGia(IErp_Donbanhang_SP sp ){
			 String query="";
				// lấy hết tất cả những sản phẩm có trong điều kiện ngày giao hợp lý,nếu còn ngày có hiệu lực,theo từ ngày đến ngày trong hợp đồng.
				//nếu sản phẩm nằm trong 2 hợp đồng và có điều kiện ngày giao hợp lý thì sẽ lấy sản phẩm  trong cái số hợp đồng lớn hơn,có nghia là lấy cái có phụ lục,phụ lục làm sau chắc chắn sẽ có Id lớn hơn.
			
			 String spid=sp.getIdSanPham();
				query = "     select top 30  1 AS Row_Num, b.pk_seq, b.MA, b.ten" +
						" , c.diengiai  as donvi, a.GIABAN as dongia, isnull(tonhientai.Ton,0) as Ton " +
				  "	    from ERP_BGBAN_SANPHAM a " +
				  "     inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ " +
				  "     left join  ( select SANPHAM_FK , AVAILABLE as Ton from ERP_KHOTT_SANPHAM  " +
				  "					 where KHOTT_FK = "+ this.khoId +  
				  "				   ) tonhientai on tonhientai.SANPHAM_FK = a.SANPHAM_FK   " +
				  "		left join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ " +
				  "	    where  b.trangthai='1'  and   b.pk_seq="+spid+" and a.giaban > 0 and BGBAN_FK in" +
				  		" ( select BANGGIABAN_FK from ERP_BANGGIABAN_KH where KH_FK = '" + this.NppId + "' " +
				  				" and BANGGIABAN_FK in ( select PK_SEQ from ERP_BANGGIABAN " +
				  				"where TRANGTHAI in('1','2') and dvkd_fk = '" + this.DvkdId + "' and kenh_fk = '" + this.KenhBanHangId + "' " +
				  				" and tungay <= '" + this.NgayGiaoDich + "' and denngay >= '" + this.NgayGiaoDich + "')  ) ";	
					 //LAY THEM SAN PHAM TRONG HOP DONG PHU LUC LOAI: "BAN THEM SP"
				 System.out.println("Get Gia : "+query);
				 
				 ResultSet rs=db.get(query);
				 if (rs != null)
				 {
					 try
					 {  
						 while(rs.next()){
							 if(rs.getString("Row_Num").equals("1")){
								 sp.setDonGia(rs.getDouble("dongia"));
								 sp.setDonViTinh(rs.getString("donvi"));
							 }
						 }
						 rs.close();
					 }catch(Exception er){
						 er.printStackTrace();
					 }
				 }				 
				 return 0.0; 
				 
				 
	}
	
	public void InitPdf()
	{
		if(this.Id.trim().length() > 0)
		{
			db = new dbutils();
			String sql ="   select ddh.loaidonhang, isnull(ddh.sopo,'') as sopo  , isnull(ddh.customerspo, '') as customerspo, ddh.ghichu, ddh.noidungchietkhau, ddh.pk_seq, ngaydat, NGAYDUKIENGIAO, iskm, isnull (sotienbvat, 0) as sotienbvat, ddh.nguoitao, ddh.nguoisua, ddh.trangthai, ddh.makeToStock, " +
			" 	khachhang_fk, npp.congty_fk, isnull (vat,0) as vat, isnull (sotienavat,0) as sotienavat, ddh.dvkd_fk, ddh.kbh_fk, " +
			" 	isnull(chietkhau,0) as chietkhau,isnull(ddh.ungck,0) as ungck,isnull(ddh.CKTrucTiep,0) as CKTrucTiep,isnull(ddh.ckthuongmai,0) as ckthuongmai, isnull(loaidonhang,'0') as loaidonhang, isnull(loaichietkhau,'0') as loaichietkhau, ddh.khott_fk,  " +
			" 	npp.ma + ', ' + npp.ten as tennpp, npp.diachigiaohang as diachixhd, npp.diachi, npp.mst as masothue, isnull(khuyenmai.tongtienKM, 0) as tongtienKM,  ddh.tiente_fk, ddh.CHOPHEPSUAGIA, ddh.FROM_HOPDONG, isnull(ddh.SoTienBaoHiem, 0) as SoTienBaoHiem, " +
			" 	isnull(ddh.dungsai, 0) as dungsai, isnull(ddh.hinhthuctt, '') as hinhthuctt, isnull(ddh.paymentterms, '') as paymentterms, isnull(ddh.deliveryterms, '') as deliveryterms, isnull(ddh.etd, '') as etd, isnull(ddh.remarks, '') as remarks, isnull(ddh.FreightCost, 0) as FreightCost, " + 
			" 	isnull(ddh.yeucauGDduyet,0) yeucauGDduyet, isnull(ddh.DIACHIGIAOHANG, '') DIACHIGIAOHANG, " +
			"	isnull ( ( select schemeIds from ERP_DONDATHANG_SCHEME_CHON where dondathang_fk = ddh.pk_seq ) , '' ) as schemeIds, isnull(ddh.bantructiep,0) bantructiep " +
			"   from ERP_DonDatHang ddh " +
			"   inner join ERP_KhachHang npp on npp.pk_seq = ddh.KhachHang_FK " +
			"   left join " +
			"   ( " +
			" 	select '" + this.Id + "' as ddhId, isnull(sum(tonggiatri), 0) as tongtienKM from erp_dondathang_ctkm_trakm " +
			" 	where dondathangId = '" + this.Id + "' and spMa is  null " +
			"   ) khuyenmai on ddh.pk_seq = khuyenmai.ddhId " +
			"   where  ddh.pk_Seq = " + this.Id;

//System.out.println("Câu init display : "+sql);
			ResultSet rs = db.get(sql);
			try
			{
				if (rs != null)
				{
					if (rs.next())
					{
						this.NgayGiaoDich = rs.getString("ngaydat");
						this.ngaydukiengiao = rs.getString("NGAYDUKIENGIAO");
						this.IDNhaCungCap = rs.getString("congty_fk");
						this.khoId = rs.getString("khott_fk");
						this.TrangThai=rs.getString("trangthai");
						this.setISKM(rs.getString("iskm"));
						this.setNgaygiaodich(rs.getString("ngaydat"));
						this.setIDKenhBanHang(rs.getString("kbh_fk"));
						this.setdvkdid(rs.getString("dvkd_fk"));
						this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
							
						this.VAT = rs.getDouble("VAT");
						this.UngCK = rs.getDouble("UngCK");
						this.CKThuongMai = rs.getDouble("CKThuongMai");
						this.CKTrucTiep = rs.getDouble("CKTrucTiep");
						this.ChietKhau = rs.getDouble("chietkhau");
						this.TongTienKM = rs.getDouble("tongtienKM");
						this.Sopo=rs.getString("sopo");
						this.setMakeToStock(rs.getString("makeToStock"));
						this.setdiachi(rs.getString("diachi"));
						this.setdiachixhd(rs.getString("diachixhd"));
						this.setmasothue(rs.getString("masothue"));
						this.setGhichu(rs.getString("ghichu"));
						this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
						//this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk");
						this.tienteId = rs.getString("tiente_fk") == null ? "100000" : rs.getString("tiente_fk");
						this.chophepsuagia = rs.getString("CHOPHEPSUAGIA") == null ? "0" : rs.getString("CHOPHEPSUAGIA");
						this.chophepdoiKH = rs.getString("FROM_HOPDONG") == null ? "0" : rs.getString("FROM_HOPDONG");
						this.loaidonhang = rs.getString("loaidonhang");
						
						if(this.chophepdoiKH.equals("1") && this.NppId.trim().length() <= 0 )
						{
							this.NppId = rs.getString("khachhang_fk");
							this.setNppTen(rs.getString("tennpp"));
						}
						this.SoTienBaoHiem=rs.getString("SoTienBaoHiem")==null?"0":formatter.format(rs.getDouble("SoTienBaoHiem"));
						
						this.dungsai = rs.getFloat("dungsai");
						this.hinhthuctt = rs.getString("hinhthuctt");
						this.paymentTerms = rs.getString("PAYMENTTERMS");
						this.deliveryTerms = rs.getString("DELIVERYTERMS");
						this.etd = rs.getString("ETD");
						this.remarks = rs.getString("REMARKS");
						this.customerspo = rs.getString("customerspo");
						this.freightCost = rs.getDouble("FreightCost");
						this.ycgdduyet = rs.getString("yeucauGDduyet");
						this.diachigiaohang = rs.getString("DIACHIGIAOHANG");
						this.schemeIds = rs.getString("schemeIds");
						this.bantructiep = rs.getString("bantructiep");
				
					}
					rs.close();
				}
				
				String strkhoId=this.khoId;
				if(this.khoId.equals("100003")|| this.khoId.equals("100004")){
					strkhoId="100003,100004";
				}
				String sql_getdetail = 
					"\n   select ddh_sp.KHOTT_FK , dondathang_fk, ddh_sp.sanpham_fk, ma, ten, " +
					"\n   donvi, soluong, isnull(ddh_sp.scheme,'') as scheme, " +
					"\n 	isnull(a.loaisanpham_fk, 0) as loaisanpham_fk, isnull(a.CHUNGLOAI_FK, 0) as CHUNGLOAI_FK," +
					"\n	isnull(ddh_sp.trongluong, 0) as trongluong, isnull(a.DVDL_TRONGLUONG, '') as DVDL_TRONGLUONG, " +
					"\n	isnull(a.thetich, '0') as thetich,  dongia, 0 as available, isnull(ddh_sp.ngaydukiengiao, '') as ngaydukiengiao,  " +
					"\n   ddh_sp.ghichu , isnull(tonhientai.ton, 0) as ton, isnull(sopallet,0) as sopallet, isnull(grossweight,0) as grossweight, isnull(netweight,0) as netweight,  isnull(ddh_sp.dongiaCK, 0) as dongiaCK, " +
					"\n	isnull(qc.SOLUONG1,0) as QUYCACH " +
				    "\n   from ERP_DonDatHang_sp ddh_sp  inner join erp_sanpham a on a.pk_seq = sanpham_fk  " +
				    "\n	left join (select * from QUYCACH where DVDL2_FK = 100004) qc on qc.SANPHAM_FK = a.PK_SEQ " +
				    "\n   left join  ( select KHOTT_FK,SANPHAM_FK , AVAILABLE as Ton " +
				    "\n   from ERP_KHOTT_SANPHAM  " +
				    "\n	where KHOTT_FK in ( " + strkhoId +")"+
				    "\n	) tonhientai on tonhientai.SANPHAM_FK = ddh_sp.SANPHAM_FK and tonhientai.KHOTT_FK = ddh_sp.KHOTT_FK	  " +
					"\n   inner join ERP_DonDatHang ddh on ddh.pk_Seq =ddh_sp.dondathang_fk " +
					"\n   where  ddh_sp.dondathang_fk = '" + this.Id + "' order by ddh_sp.pk_seq";
				
				//System.out.println("___Khoi tao don dat hang Update: " + sql_getdetail);
				this.listsanpham.clear();
				rs = db.get(sql_getdetail);
				if (rs != null)
				{
					while (rs.next())
					{
						 
						IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
						sanpham.setDonGia(rs.getDouble("dongia"));
						sanpham.setDonViTinh(rs.getString("donvi"));
						sanpham.setId(rs.getString("dondathang_fk"));
						sanpham.setIdSanPham(rs.getString("sanpham_fk"));
						sanpham.setGhichusp(rs.getString("ghichu"));
						sanpham.setGrossWeight(rs.getString("grossweight"));
						sanpham.setSoPallet(rs.getString("sopallet"));
						sanpham.set_DongiaCK(formatterThapphan.format(rs.getDouble("dongiaCK")));
						
						sanpham.setKhoid(rs.getString("KHOTT_FK"));
						sanpham.setQuycach(rs.getString("QUYCACH"));
						sanpham.setNetWeight(rs.getString("netweight"));
						sanpham.setSHEME(rs.getString("scheme"));
						
						sanpham.setTenSanPham(rs.getString("ten"));
						sanpham.setMaSanPham(rs.getString("ma"));
						
						double soluong = rs.getDouble("soluong");
						if(soluong > 0)
						{
							sanpham.setSoLuong(rs.getDouble("soluong"));
						}
						else
						{
							sanpham.setSoLuong(0);
						}
						
						sanpham.setSHEME(rs.getString("scheme"));
						
						if(rs.getString("donvi").toUpperCase().equals("KG")){//--- neu donvi = kg , thi bang = soluong   ----> trongluong=1
							sanpham.setTrongluong("1");
						}else{
							sanpham.setTrongluong(rs.getString("trongluong"));
						}
						
						sanpham.setThetich(rs.getString("thetich"));
						
						if(soluong > 0)
						{
							/*if(maketoStock.equals("1"))
								sanpham.setSoluongton(rs.getDouble("soluong") + rs.getDouble("ton"));
							else*/
								sanpham.setSoluongton(rs.getDouble("ton"));
						}
						else
						{
							sanpham.setSoluongton(0);
						}
					 
						sanpham.setNgaydukiengiao(rs.getString("ngaydukiengiao"));
						
						this.listsanpham.add(sanpham);
					}
				}
				rs.close();
				//Khoi tao tra khuyen mai
				this.getTrakhuyenmai();
			}
			catch (Exception er)
			{
				er.printStackTrace();
				//System.out.println("1.Loi khoi tao:" + er.toString());
			}		
		
		}		
	}
	
	public Hashtable<String, Integer> getSpThieuList()
	{
		return this.spThieuList;
	}
	
	public void setSpThieuList(Hashtable<String, Integer> spThieuList)
	{
		this.spThieuList = spThieuList;
	}
	
	public boolean Save()
	{
		 
		try
		{
			if (this.NguoiTao == null || this.NguoiTao.equals(""))
			{
				this.Msg = "User Dang Nhap Tam Thoi Bi Lock Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.DvkdId.trim().length() <= 0)
			{
				this.Msg = "Bạn phải chọn đơn vị kinh doanh";
				return false;
			}
			
			if (this.NppId == null || this.NppId.equals(""))
			{
				this.Msg = "Bạn phải chọn khách hàng";
				return false;
			}
			
			if(this.ISKM.equals("1")){
				this.maketoStock = "1";
			}
			
			if(khoId.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn kho.";
				return false;
			}
			if(hinhthuctt.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn hình thức thanh toán";
				return false;
			}
			db.getConnection().setAutoCommit(false);
			
			String ngayks = this.checkNgayKs();
			if(ngayks.length() > 0){
				this.Msg = "Bạn không thể làm đơn hàng trước ngày " + ngayks + " của tháng hiện tại";
				return false;
			}
			
			if(this.tienteId.trim().length() <= 0)
			{
				this.tienteId = "100000";
			}
			//KHONG RO CANFOCO CO HOP DONG HAY KHONG?
			//String hopdong_fk = this.hopdongId.trim().length() <= 0 ? "null" : this.hopdongId.trim();
			
			String query = " insert into ERP_DonDatHang(SOPO,SoTienBaoHiem,NGAYDAT, NGAYDUKIENGIAO,HINHTHUCTT, TRANGTHAI, NGUOITAO, NGUOISUA, " +
						   " KHACHHANG_FK, DVKD_FK, CONGTY_FK, kbh_FK, khott_fk, loaidonhang, iskm, tinhtrang, maketoStock,  TienTe_FK, " +
						   " CHOPHEPSUAGIA, PAYMENTTERMS, DELIVERYTERMS, ETD, " +
						   " REMARKS, FreightCost ,customerspo ,ngaytao,ngaysua, dungsai,ungck,CKTrucTiep,ckthuongmai, DIACHIGIAOHANG, BGID, bantructiep) " +
						   " values (N'"+this.Sopo+"','"+this.SoTienBaoHiem+"','" + this.NgayGiaoDich + "', '" + this.ngaydukiengiao + "',N'"+ this.hinhthuctt +"',  '1', " + this.NguoiTao + ", " + this.NguoiSua + ", " + this.NppId + ", " + this.DvkdId + ", " + this.congtyId + ", " + this.KenhBanHangId + ", '" + khoId + "', '" + this.loaidonhang + "', '" + this.ISKM + "', '0', '" + this.maketoStock + "', " +
						   " '" + this.tienteId + "', '" + this.chophepsuagia + "', N'"+this.paymentTerms+"', N'"+this.deliveryTerms+"', N'"+this.etd+"', " +
						   "N'"+this.remarks+"', " + this.freightCost + ", N'"+this.customerspo +  "','"+this.getDateTime()+"','"+this.getDateTime()+"', " +
						   "'"+ this.dungsai +"',"+this.UngCK  +","+this.CKTrucTiep  +","+this.CKThuongMai+", N'"+this.diachigiaohang+"', " + this.bgId + ", "+this.bantructiep+" )  ";
			
			//System.out.println("Insert Don dat hang: " + query);
			if (!db.update(query))
			{
				db.getConnection().rollback();
				this.Msg = "1.Không thể tạo mới,lỗi dòng lệnh  :" + query;
				return false;
			}
			
			// Save chi tiet don hang
			query = "select SCOPE_IDENTITY() as dhId";
			ResultSet rsDh = db.get(query);
			if (rsDh != null)
			{
				if(rsDh.next())
				{
					this.Id = rsDh.getString("dhId");
				}
				rsDh.close();
			}
			int count = 0;
			double tongtienTruocVat = 0;
			System.out.println("Action ABC : ");
			if(this.listsanpham != null){
				while (count < this.listsanpham.size())
				{
					IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
					sanpham = listsanpham.get(count);
				
					if(!this.ISKM.equals("1"))
					{
						if (sanpham.getSoLuong() <= 0 || sanpham.getDonGia() <= 0)
						{
							this.Msg = "3.Khong the luu ma san pham : " + sanpham.getMaSanPham() + ", Cap Nhat So Luong Va Gia Lon hon 0.";
							db.update("rollback");
							return false;
						}
					}
					else
					{
						if (sanpham.getSoLuong() <= 0)
						{
							this.Msg = "3.Khong the luu ma san pham : " + sanpham.getMaSanPham() + ", Cap Nhat So Luong Va Gia Lon hon 0.";
							db.update("rollback");
							return false;
						}
					}
				
					tongtienTruocVat += sanpham.getSoLuong() * sanpham.getDonGia();
					count = count + 1;
				}
			}
			double tienchietkhau = 0;			
		 
			tienchietkhau = tongtienTruocVat * this.ChietKhau / 100+ this.CKThuongMai;
			 
			count = 0;
			
			if(this.listsanpham != null){
				while (count < this.listsanpham.size())
				{
					IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
					sanpham = listsanpham.get(count);
 
					double thanhtien = sanpham.getSoLuong() * sanpham.getDonGia();
					double chietkhausp = 0;
					double vatsp = 0;
					double tienavat = (thanhtien - chietkhausp) + vatsp;
				
				//KIỂM TRA QUY CÁCH TỒN TẠI HAY KHÔNG
				/*String checkQuyCach = "select cast(SOLUONG2 as float) / cast(SOLUONG1 as float) from QUYCACH where SANPHAM_FK = '" + sanpham.getIdSanPham() + "' and DVDL2_FK = '100003'";
				ResultSet rsQuyCach = db.get(checkQuyCach);
				if(!rsQuyCach.next())
				{
					this.Msg = "Sản phẩm: " + sanpham.getMaSanPham()+". Chưa được khai báo quy cách sản phẩm";
					db.getConnection().rollback();
					rsQuyCach.close();
					return false;
				}
				rsQuyCach.close();*/
				
					if(!this.ISKM.equals("1"))
					{
						if(this.maketoStock.equals("1"))
						{
							if(sanpham.getNgaydukiengiao().trim().length() <= 0)
							{
								this.Msg = "Vui lòng chọn ngày giao hàng của sản phẩm: " + sanpham.getMaSanPham();
								db.getConnection().rollback();
								return false;
							}
						}
												
						if(sanpham.getKhoid().equals("null")|| sanpham.getKhoid().trim().length()<=0){
							this.Msg = "Sản phẩm : " + sanpham.getMaSanPham() +" chưa có trong kho đã chọn. Yêu cầu thiết lập lại dữ liệu nền cho sản phẩm này.";
							db.getConnection().rollback();
							return false;
						}
											
						query = " insert into ERP_DonDatHang_sp (sanpham_fk, dondathang_fk, soluong , dongia, DONGIAVIET, dvdl_fk, donvi, sotienbvat, chietkhau, vat, sotienavat, ngaydukiengiao, ghichu, grossweight, sopallet, netweight,KHOTT_FK ,SCHEME, dongiaCK) " +
								" select " + sanpham.getIdSanPham() + ", " + this.Id +", " + sanpham.getSoLuong() +", " +
								"  " + sanpham.getDonGia() +", " + ( sanpham.getDonGia() + " * " + this.getTyGiaQuyDoi() ) + ", PK_SEQ , " +
								" N'" + sanpham.getDonViTinh() +"','" + thanhtien + "','" + chietkhausp + "', '" + vatsp + "', '" + tienavat + "', '" + sanpham.getNgaydukiengiao() + "', N'" + sanpham.getGhichusp() + "', "+sanpham.getGrossWeight()+", "+sanpham.getSoPallet()+", "+sanpham.getNetWeight()+","+sanpham.getKhoid()+" ,'"+sanpham.getSHEME()+"', "+sanpham.get_DongiaCK()+"  " +
								" from DONVIDOLUONG where DIENGIAI =N'" + sanpham.getDonViTinh()+ "' ";
					}
					else
					{
					
						if(sanpham.getKhoid().equals("null")|| sanpham.getKhoid().trim().length()<=0){
							this.Msg = "Sản phẩm : " + sanpham.getMaSanPham() +" chưa có trong kho đã chọn. Yêu cầu thiết lập lại dữ liệu nền cho sản phẩm này.";
							db.getConnection().rollback();
							return false;
						}
							
						query =   " insert into ERP_DonDatHang_sp (sanpham_fk, dondathang_fk, soluong, dongia, DONGIAVIET, dvdl_fk, donvi, sotienbvat, chietkhau, vat, sotienavat, ngaydukiengiao, ghichu ,khott_fk,SCHEME, dongiaCK) " +
							  		" select " + sanpham.getIdSanPham() + ", " + this.Id +", " + sanpham.getSoLuong() +", " +
							  		" '0', '0', PK_SEQ , " +
							  		" N'" + sanpham.getDonViTinh() + "', '0' , '0', '0', '0', '" + sanpham.getNgaydukiengiao() + "', N'" + sanpham.getGhichusp() + "','"+sanpham.getKhoid()+
							  		"' ,'"+sanpham.getSHEME()+"', "+sanpham.get_DongiaCK()+" from DONVIDOLUONG where DIENGIAI =N'" + sanpham.getDonViTinh()+ "' ";
 
					}
					
				//System.out.println("2.Insert ddh - SP: " + sql);
					
					if (db.updateReturnInt(query) != 1)
					{
						this.Msg = "4.Khong the luu ma san pham : " + sanpham.getMaSanPham() + " ,Loi trong dong lenh sau : " + query;
						db.getConnection().rollback();
						return false;
					}
				
				//DDH MTO CHECK TON KHO
					if(this.ISKM.equals("0") && this.maketoStock.equals("0") )
					{
						this.KiemtraThieuhang(sanpham.getMaSanPham());
					}
				
					count++;
				}
			}
			double tienvat =  this.TongTienSauVAT - this.TongTienSauKM;
			
			if(this.TongTienSauVAT <=0 ){
				this.Msg = " Không lấy được thành tiền sau thuế.Vui lòng thử lại";
				db.update("rollback");
				return false;
			}
			
			query = " update ERP_DonDatHang set  sotienavat = " + this.TongTienSauVAT + ", vat = '" + this.VAT + "', " +
					" sotienbvat = " + Math.round(tongtienTruocVat) + ", " +
					" chietkhau='" + this.ChietKhau + "', ghichu = N'" + this.ghichu + "', noidungchietkhau = N'" + this.noidungchietkhau + "', " +
					" ngaydat = '" + this.NgayGiaoDich + "', tienvat="+tienvat+", BGID = " + this.bgId + "  where pk_Seq = " + this.Id;
			
		 
			if (!db.update(query))
			{
				this.Msg = "5.Loi Nhap Lieu,Vui Long Xem Lai.Error: " + query;
				db.update("rollback");
				return false;
			}
			
			
			//LUU SCHEME DA CHON
			if(this.schemeIds.trim().length() > 0)
			{
				query = "Insert ERP_DONDATHANG_SCHEME_CHON ( dondathang_fk, schemeIds ) values ( '" + this.Id + "', '" + this.schemeIds + "' ) ";
				if (!db.update(query))
				{
					this.Msg = "5.Loi Nhap Lieu,Vui Long Xem Lai.Error: " + query;
					db.update("rollback");
					return false;
				}	
			}
			
			//MAKE TO STOCK - KM MOI CHECK TON KHO KHI DAT HANG
			if(this.maketoStock.trim().equals("1"))  
			{
					query =     " 	SELECT b.MA + '-' +b.ten  as ten , A.KHOTT_FK,A.SANPHAM_FK  , "+
							    "	CASE WHEN A.DVDL_FK != B.DVDL_FK AND CAST(D.SOLUONG2 AS FLOAT) >0 "+ 
								"	THEN A.SOLUONG * ISNULL(D.SOLUONG1, 1) / ISNULL(D.SOLUONG2, 1)    ELSE A.SOLUONG END AS SOLUONG "+ 
								"	FROM ERP_DONDATHANG_SP A  "+
								"	INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ "+   
								"	INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ   "+
								"	INNER JOIN ERP_DONDATHANG C ON C.PK_SEQ = A.DONDATHANG_FK "+  
								"	LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK  AND A.DVDL_FK = D.DVDL2_FK  AND D.DVDL1_FK=B.DVDL_FK "+
								"	WHERE A.DONDATHANG_FK ="+this.Id+"    ";
				 
				    rskho=db.get(query);
				    while (rskho.next()){
					if(  rskho.getDouble("soluong")<=0 ){
						db.getConnection().rollback();
						this.Msg = "Quy cách của sản phẩm "+rskho.getString("ten")+" chưa xác định,vui lòng thiết lập trước vì đây là đơn hàng không chọn  Sản xuất theo đơn đặt hàng  "   ;
						return false;
					}
					 
					// tăng booked,giảm avai
					
					double booked= rskho.getDouble("soluong");
					double available=(-1) * rskho.getDouble("soluong");
					
					String	msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(db, rskho.getString("khott_fk"), rskho.getString("sanpham_fk"), 0, booked, available,0,this.NgayGiaoDich);
					if(msg1.length() >0)
					{
						db.getConnection().rollback();
						this.Msg = msg1  ;
						return false;
					}
					 
				}
				rskho.close();
				
			}
			
			
			//Check thieu hang ( MAKE TO ORDER )
			try
			{
				query = "SELECT count(kiemtra.SPID) as soDong " +
						"from " +
						"( " +
							"SELECT SP.PK_SEQ AS SPID, SUM(KHO_SP.SOLUONG) AS SoLuong       " +
								"FROM ERP_KHOTT_SANPHAM KHO_SP      " +
								"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ      " +
								"WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( '100005', '100006', '100007') )   " +
								"GROUP BY SP.PK_SEQ     " +
							"union all  " +
								"select c.PK_SEQ as spId, (-1) * sum(b.SOLUONG) as soluong    " +
								"from ERP_DONDATHANG a inner join ERP_DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK    " +
									"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
								"where a.TRANGTHAI <= 3 and b.ngaydukiengiao >= '" + this.getDateTime() + "'  " +
								"group by c.PK_SEQ " +
							" union all  " +
								"select c.PK_SEQ as spId, sum(b.SOLUONG) as SoLuong   " +
								"from ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_SANPHAM b on a.pk_seq = b.lenhsanxuat_fk  " +
								"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ   " +
								"where a.NGAYDUKIENHT >= '" + this.getDateTime() + "'  " +
								"group by c.PK_SEQ " +
						") " +
						"kiemtra group by kiemtra.SPID " +
						"having SUM(soluong) < 0 ";
				
				ResultSet rsCheck = db.get(query);
				int sodong = 0;
				if (rsCheck != null)
				{
					if(rsCheck.next())
					{
						sodong = rsCheck.getInt("soDong");
					}
					rsCheck.close();
				}				
				
				if(sodong > 0)
				{
					//Lay LIST DIEN THOAI
					query = "select SMS, PHONELIST from ERP_CAUHINHSMS where MUCDICHSUDUNG = 'SO' and TRANGTHAI = '1'";
					ResultSet rsPhone = db.get(query);
					if (rsPhone != null)
					{
						if(rsPhone.next())
						{
							String sms = rsPhone.getString("SMS");
							sms = sms.replaceAll("__", this.Id);
							
							String phone = rsPhone.getString("PHONELIST");
							
							if(phone.length() > 0)
							{
								String[] pl = phone.split(";");
								
								for(int i = 0; i < pl.length; i++ )
								{
									if(pl[i].trim().length() > 0)
									{
										query = "insert OutBox(ID, PhoneNumber, Content, Contenttype, SendDate, Status) " +
												"values ('1', '" + pl[i] + "', N'" + sms + "', '0', '" + getDateTime() + "', '0')";
										db.update(query);
									}
								}
							}
						}
						rsPhone.close();
					}
				}
			}
			catch (Exception e) {
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception er)
		{
			db.update("rollback");
			this.setMessage( " Error : "+er.toString());
			return false;
		}
		return true;
	}
	 

	private String checkNgayKs() {
		String ngay = this.getDateTime();
		System.out.println("Ngay " + ngay);
		String ngayks = "";
		String query = "select top(1)* from NGAYKHOASO WHERE TRANGTHAI = 1";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try {
				if(rs.next()){
					ngayks = rs.getString("NGAY");
					if(ngayks.length() > 0){
						ngay = ngay.substring(0, ngay.length()-2) + ngayks;
						System.out.println("Ngay ks " + ngay);
						String[] ngays = ngay.split("-");
						String[] ngaydh = this.NgayGiaoDich.split("-");
						
						Calendar c1 = Calendar.getInstance(); //new GregorianCalendar();
						Calendar c2 = Calendar.getInstance(); //new GregorianCalendar();
	
						c1.set(Integer.parseInt(ngays[0]), Integer.parseInt(ngays[1]) - 1, Integer.parseInt(ngays[2]));
						c2.set(Integer.parseInt(ngaydh[0]), Integer.parseInt(ngaydh[1]) - 1, Integer.parseInt(ngaydh[2]));
	
						long songay = (c2.getTime().getTime() - c1.getTime().getTime()) / (24 * 3600 * 1000);
	
						System.out.println("songay " + songay);
						if(songay < 0)
							return ngayks;
						return "";
					}
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				return e.getMessage();
			}
		}
		return "";
	}

	private boolean checkDungSai_HopDong() 
	{ 
		return true;
	}

	public boolean Edit(String ischot)
	{
		try
		{
			if (this.NguoiTao == null || this.NguoiTao.equals(""))
			{
				this.Msg = "User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.DvkdId.trim().length() <= 0)
			{
				this.Msg = "Bạn phải chọn đơn vị kinh doanh";
				return false;
			}
			
			if (this.NppId == null || this.NppId.equals(""))
			{
				this.Msg = "Bạn phải chọn khách hàng";
				return false;
			}
			
			if(this.ISKM.equals("1"))
				this.maketoStock = "1";
			 
			
			if(khoId.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn kho.";
				return false;
			}
			
			if(hinhthuctt.trim().length() <= 0)
			{
				this.Msg = "Vui lòng chọn hình thức thanh toán.";
				return false;
			}
			String query="";
			//Neu la MAKE TO STOCK thi phai tinh so luong quy doi trong truong hop khong dat theo DON VI CHUAN
			if(this.maketoStock.equals("1"))
			{
				
			}
			
			db.getConnection().setAutoCommit(false);// chu y
			
			String ngayks = this.checkNgayKs();
			if(ngayks.length() > 0){
				this.Msg = "Bạn không thể cập nhật đơn hàng trước ngày " + ngayks + " của tháng hiện tại";
				return false;
			}
			
			
			//Truoc khi xoa
			String queryUpdate =    "\n	SELECT A.KHOTT_FK,A.SANPHAM_FK  , "+
								    "\n	CASE WHEN A.DVDL_FK != B.DVDL_FK AND CAST(D.SOLUONG2 AS FLOAT) >0 "+ 
									"\n	THEN A.SOLUONG * ISNULL(D.SOLUONG1, 1) / ISNULL(D.SOLUONG2, 1)    ELSE A.SOLUONG END AS SOLUONG "+ 
									"\n	FROM ERP_DONDATHANG_SP A  "+
									"\n	INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ "+   
									"\n	INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ   "+
									"\n	INNER JOIN ERP_DONDATHANG C ON C.PK_SEQ = A.DONDATHANG_FK "+  
									"\n	LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK  AND A.DVDL_FK = D.DVDL2_FK  AND D.DVDL1_FK=B.DVDL_FK "+
									"\n	WHERE A.DONDATHANG_FK ="+this.Id+"     AND C.MAKETOSTOCK   ='1'";
			System.out.println(queryUpdate);
			ResultSet rskho=db.get(queryUpdate);
			if (rskho != null)
			{
				while (rskho.next()){
					// giảm booked,tăng avai lại khi sửa đơn hàng
					double booked= (-1) *rskho.getDouble("soluong");
					double available= rskho.getDouble("soluong");
					
					String	msg1= util_kho.Update_Kho_Sp(db, rskho.getString("khott_fk"), rskho.getString("sanpham_fk"), 0, booked, available,0);
					if(msg1.length() >0)
					{
						db.getConnection().rollback();
						this.Msg = msg1;
						return false;
					}
				}
				rskho.close();
			}			
			// xoa het chi tiet cu
			query = "delete from ERP_DonDatHang_sp where dondathang_fk = " + this.Id;
			if (!db.update(query))
			{
				db.getConnection().rollback();
				this.Msg = "1.Khong The Cap Nhat ERP_DonDatHang_sp, Loi Tren Dong Lenh Sau :" + query;
				return false;
			}
			
			// Save chi tiet don hang
			int count = 0;
			double tongtienTruocVat = 0;
			
			while (count < this.listsanpham.size())
			{
				IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
				sanpham = listsanpham.get(count);
				if(!this.ISKM.equals("1"))
				{
					if (sanpham.getSoLuong() <= 0 || sanpham.getDonGia() <= 0)
					{
						this.Msg = "3.Khong the luu ma san pham : " + sanpham.getMaSanPham() + ", Cap Nhat So Luong Va Gia Lon hon 0.";
						db.update("rollback");
						return false;
					}
				}
				else
				{
					if (sanpham.getSoLuong() <= 0)
					{
						this.Msg = "3.Khong the luu ma san pham : " + sanpham.getMaSanPham() + ", Cap Nhat So Luong Va Gia Lon hon 0.";
						db.update("rollback");
						return false;
					}
				}
				tongtienTruocVat += sanpham.getSoLuong() * sanpham.getDonGia();
				count = count + 1;
			}
			
			double tienchietkhau = 0;
			
			//if(!this.ISKM.equals("1"))
			//{
				tienchietkhau = tongtienTruocVat * this.ChietKhau / 100+this.CKThuongMai;
			//}
			count = 0;
			while (count < this.listsanpham.size())
			{
				IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
				sanpham = listsanpham.get(count);
  
				double thanhtien = sanpham.getSoLuong() * sanpham.getDonGia();
				double chietkhausp = 0;
				double vatsp = 0;
				double tienavat = (thanhtien - chietkhausp) + vatsp;
				
			/*	//KIỂM TRA QUY CÁCH TỒN TẠI HAY KHÔNG
				String checkQuyCach = "select cast(SOLUONG2 as float) / cast(SOLUONG1 as float) from QUYCACH where SANPHAM_FK = '" + sanpham.getIdSanPham() + "' and DVDL2_FK = '100003'";
				ResultSet rsQuyCach = db.get(checkQuyCach);
				if(!rsQuyCach.next())
				{
					this.Msg = "Sản phẩm: " + sanpham.getMaSanPham()+". Chưa được khai báo quy cách sản phẩm";
					db.getConnection().rollback();
					rsQuyCach.close();
					return false;
				}
				rsQuyCach.close();*/
				

				if(!this.ISKM.equals("1"))
				{
					if(this.maketoStock.equals("1"))
					{
						if(sanpham.getNgaydukiengiao().trim().length() <= 0)
						{
							this.Msg = "Vui lòng chọn ngày giao hàng của sản phẩm: " + sanpham.getMaSanPham();
							db.getConnection().rollback();
							return false;
						}
					}
					
					if(sanpham.getKhoid().equals("null")|| sanpham.getKhoid().trim().length()<=0){
						this.Msg = "Sản phẩm : " + sanpham.getMaSanPham() +" chưa có trong kho đã chọn. Yêu cầu thiết lập lại dữ liệu nền cho sản phẩm này.";
						db.getConnection().rollback();
						return false;
					}
					
					query = " insert into ERP_DonDatHang_sp (sanpham_fk, dondathang_fk, soluong , dongia, DONGIAVIET, dvdl_fk, donvi, sotienbvat, chietkhau, vat, sotienavat, ngaydukiengiao, ghichu, grossweight, sopallet, netweight,KHOTT_FK ,SCHEME, dongiaCK) " +
							" select " + sanpham.getIdSanPham() + ", " + this.Id +", " + sanpham.getSoLuong() +", " +
						    "  " + sanpham.getDonGia() +", " + ( sanpham.getDonGia() + " * " + this.getTyGiaQuyDoi() ) + ", PK_SEQ , " +
							" N'" + sanpham.getDonViTinh() +"','" + thanhtien + "','" + chietkhausp + "', '" + vatsp + "', '" + tienavat + "', '" + sanpham.getNgaydukiengiao() + "', N'" + sanpham.getGhichusp() + "', "+sanpham.getGrossWeight()+", "+sanpham.getSoPallet()+", "+sanpham.getNetWeight()+","+sanpham.getKhoid()+" ,'"+sanpham.getSHEME()+"', "+sanpham.get_DongiaCK()+"    " +
							" from DONVIDOLUONG where DIENGIAI =N'" + sanpham.getDonViTinh()+ "' ";
				}
				else
				{
					
					if(sanpham.getKhoid().equals("null")|| sanpham.getKhoid().trim().length()<=0){
						this.Msg = "Sản phẩm : " + sanpham.getMaSanPham() +" chưa có trong kho đã chọn. Yêu cầu thiết lập lại dữ liệu nền cho sản phẩm này.";
						db.getConnection().rollback();
						return false;
					}
 
					query =   " insert into ERP_DonDatHang_sp (sanpham_fk, dondathang_fk, soluong, dongia, DONGIAVIET, dvdl_fk, donvi, sotienbvat, chietkhau, vat, sotienavat, ngaydukiengiao, ghichu ,khott_fk,SCHEME, dongiaCK) " +
							  " select " + sanpham.getIdSanPham() + ", " + this.Id +", " + sanpham.getSoLuong() +", " +
				  			  " '0', '0', PK_SEQ , " +
							  " N'" + sanpham.getDonViTinh() + "', '0' , '0', '0', '0', '" + sanpham.getNgaydukiengiao() + "', N'" + sanpham.getGhichusp() + "',"+sanpham.getKhoid()+
							  " ,'"+sanpham.getSHEME()+"', "+sanpham.get_DongiaCK()+ " from DONVIDOLUONG where DIENGIAI =N'" + sanpham.getDonViTinh()+ "' ";

					

				}
 				//System.out.println("2.Insert ddh - SP: " + sql);
				
				if (db.updateReturnInt(query) != 1)
				{
					this.Msg = "4.Khong the luu ma san pham : " + sanpham.getMaSanPham() + " ,Loi trong dong lenh sau : " + query;
					db.getConnection().rollback();
					return false;
				}

				
				if(this.ISKM.equals("0") && this.maketoStock.equals("0") )
				{
					this.KiemtraThieuhang(sanpham.getMaSanPham());
				}
				
				count++;
			}
			
			
			 
			

			
		 	/*
		 	 * double vat1 = ( tongtienTruocVat - tienchietkhau - this.TongTienKM+ this.freightCost ) * this.VAT / 100;
			  double tongtienSauvat1 = Math.round(tongtienTruocVat - tienchietkhau  - this.TongTienKM + this.freightCost  + vat1);
			 */
			
			
			double  tienvat = this.TongTienSauVAT - this.TongTienSauKM;
	
			 
			if(this.ISKM.equals("1")){
				this.maketoStock = "1";
			}
			 

			if(this.tienteId.trim().length() <= 0)
			{
				this.tienteId = "100000";
			}
		 
			if(this.TongTienSauVAT <=0 ){
				this.Msg = " Không lấy được thành tiền sau thuế.Vui lòng thử lại";
				db.update("rollback");
				return false;
			}
			
			
			query =   "  update ERP_DonDatHang set sopo=N'"+this.Sopo+"', loaidonhang = '" + this.loaidonhang + "', NGAYSUA='"+this.getDateTime()+"' ,SoTienBaoHiem='"+SoTienBaoHiem+"'," +(ischot.equals("1")? " trangthai='2', ":"" ) + " NGUOISUA=" + this.NguoiSua + ",VAT='" + this.VAT + "', SOTIENBVAT=" + tongtienTruocVat + " , TIENKHUYENMAI = '" + this.TongTienKM + "', " +
					  "  SOTIENAVAT=" + this.TongTienSauVAT + ", khachhang_fk='" + this.NppId + "',congty_fk=" + this.congtyId + ",kbh_fk='" + this.KenhBanHangId + "' ,chietkhau='" + this.ChietKhau + "', " +
					  "  ghichu = N'" + this.ghichu + "', NGAYDAT='" + this.NgayGiaoDich + "', NGAYDUKIENGIAO = '" + this.ngaydukiengiao + "', makeToStock = '" + this.maketoStock + "', TIENTE_FK = '" + this.tienteId + "', CHOPHEPSUAGIA = '" +  this.chophepsuagia + "', khott_fk = " + this.khoId + ", " +
					  "  PAYMENTTERMS = N'" + this.getPaymentTerms() + "', DELIVERYTERMS = N'" + this.getDeliveryTerms() + "', ETD = N'" + this.getETD() + "', REMARKS = N'" + this.getRemarks() + "', FreightCost = "+this.freightCost + ", customerspo = N'"+this.customerspo+"' , dungsai = '"+ this.dungsai + "', " +
					  "  ungck ="+this.UngCK+", ckthuongmai ="+this.CKThuongMai+" ,CKTrucTiep= "+this.CKTrucTiep+
					  "  , hinhthuctt=N'"+this.hinhthuctt+"', DIACHIGIAOHANG = N'"+this.diachigiaohang+"', tienvat="+tienvat+", BGID = " + this.bgId + ", bantructiep = "+this.bantructiep+""+
					  "  where pk_seq = " + this.Id;
			//System.out.println("QUERY UPDATE: "+query);
			if (!db.update(query))
			{
				db.update("rollback");
				this.Msg = "6.Khong The Duyet Hoa Don ,Loi Tren Dong Lenh Sau :" + query;
				return false;
			}
			
			if(this.maketoStock.trim().equals("1"))  //MAKE TO STOCK MOI CHECK TON KHO KHI DAT HANG
			{
					query =     " 	SELECT b.MA + '-' +b.ten+ '-'+b.quycach as ten , A.KHOTT_FK,A.SANPHAM_FK  , "+
							    "	CASE WHEN A.DVDL_FK != B.DVDL_FK AND CAST(D.SOLUONG2 AS FLOAT) >0 "+ 
								"	THEN A.SOLUONG * ISNULL(D.SOLUONG1, 1) / ISNULL(D.SOLUONG2, 1)    ELSE A.SOLUONG END AS SOLUONG "+ 
								"	FROM ERP_DONDATHANG_SP A  "+
								"	INNER JOIN ERP_SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ "+   
								"	INNER JOIN DONVIDOLUONG E ON B.DVDL_FK = E.PK_SEQ   "+
								"	INNER JOIN ERP_DONDATHANG C ON C.PK_SEQ = A.DONDATHANG_FK "+  
								"	LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK  AND A.DVDL_FK = D.DVDL2_FK  AND D.DVDL1_FK=B.DVDL_FK "+
								"	WHERE A.DONDATHANG_FK ="+this.Id+"  ";
				   rskho=db.get(query);
				   while (rskho.next()){
					   	// tăng booked ,giảm avai
						    double booked= rskho.getDouble("soluong");
							double available=(-1) * rskho.getDouble("soluong");
							
							String	msg1= util_kho.Update_Kho_Sp_Check_TonKhoNgay(db, rskho.getString("khott_fk"), rskho.getString("sanpham_fk"), 0, booked, available,0,this.NgayGiaoDich);
							if(msg1.length() >0)
							{
								db.getConnection().rollback();
								this.Msg = msg1;
								return false;
							}
						
					 }
				rskho.close();
			}
			
			
			query = "delete ERP_DONDATHANG_SCHEME_CHON where dondathang_fk = '" + this.Id + "' ";
			if (!db.update(query))
			{
				this.Msg = "5.Loi Nhap Lieu,Vui Long Xem Lai.Error: " + query;
				db.update("rollback");
				return false;
			}
			
			//LUU SCHEME DA CHON
			if(this.schemeIds.trim().length() > 0)
			{
				query = "Insert ERP_DONDATHANG_SCHEME_CHON ( dondathang_fk, schemeIds ) values ( '" + this.Id + "', '" + this.schemeIds + "' ) ";
				if (!db.update(query))
				{
					this.Msg = "5.Loi Nhap Lieu,Vui Long Xem Lai.Error: " + query;
					db.update("rollback");
					return false;
				}	
			}
			
			
			//Check thieu hang
			  query = "";
			try
			{
				query = " SELECT count(kiemtra.SPID) as soDong " +
						" from " +
						" ( " +
							"SELECT SP.PK_SEQ AS SPID, SUM(KHO_SP.SOLUONG) AS SoLuong       " +
								"FROM ERP_KHOTT_SANPHAM KHO_SP      " +
								"INNER JOIN ERP_SANPHAM SP on KHO_SP.SANPHAM_FK = SP.PK_SEQ      " +
								"WHERE KHO_SP.SANPHAM_FK in ( select pk_seq from ERP_SANPHAM where LOAISANPHAM_FK in ( '100005', '100006', '100007') )   " +
								"GROUP BY SP.PK_SEQ     " +
							"union all  " +
								"select c.PK_SEQ as spId, (-1) * sum(b.SOLUONG) as soluong    " +
								"from ERP_DONDATHANG a inner join ERP_DONDATHANG_SP b on a.PK_SEQ = b.DONDATHANG_FK    " +
									"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ    " +
								"where a.TRANGTHAI <= 3 and b.ngaydukiengiao >= '" + this.getDateTime() + "'  " +
								"group by c.PK_SEQ " +
							" union all  " +
								"select c.PK_SEQ as spId, sum(b.SOLUONG) as SoLuong   " +
								"from ERP_LENHSANXUAT_GIAY a inner join ERP_LENHSANXUAT_SANPHAM b on a.pk_seq = b.lenhsanxuat_fk  " +
								"inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ   " +
								"where a.NGAYDUKIENHT >= '" + this.getDateTime() + "'  " +
								"group by c.PK_SEQ " +
						") " +
						"kiemtra group by kiemtra.SPID " +
						"having SUM(soluong) < 0 ";
				
				//System.out.println("___Cau lenh check: " + query);
				ResultSet rsCheck = db.get(query);
				int sodong = 0;
				if (rsCheck != null)
				{
					if(rsCheck.next())
					{
						sodong = rsCheck.getInt("soDong");
					}
					rsCheck.close();
				}
				
				if(sodong > 0)
				{
					if(sodong > 0)
					{
						//Lay LIST DIEN THOAI
						query = "select SMS, PHONELIST from ERP_CAUHINHSMS where MUCDICHSUDUNG = 'SO' and TRANGTHAI = '1'";
						ResultSet rsPhone = db.get(query);
						if (rsPhone != null)
						{
							if(rsPhone.next())
							{
								String sms = rsPhone.getString("SMS");
								sms = sms.replaceAll("__", this.Id);
								
								String phone = rsPhone.getString("PHONELIST");
								
								if(phone.length() > 0)
								{
									String[] pl = phone.split(";");
									
									for(int i = 0; i < pl.length; i++ )
									{
										if(pl[i].trim().length() > 0)
										{
											query = "insert OutBox(ID, PhoneNumber, Content, Contenttype, SendDate, Status) " +
													"values ('1', '" + pl[i] + "', N'" + sms + "', '0', '" + getDateTime() + "', '0')";
											db.update(query);
										}
									}
								}
							}
							rsPhone.close();	
						}
					}
				}
			}
			catch (Exception e) {
				e.printStackTrace();
				//System.out.println("____Exception: " + e.getMessage());
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception er)
		{
			db.update("rollback");
			this.setMessage(er.toString());
			//System.out.println("7.Exception: " + er.toString());
			return false;
		}
		return true;
	}
	
	private void KiemtraThieuhang(String masp)
	{
		try
		{
			// Tao query, loc ra ton kho, nhung PrO va SO theo ngay o tren
			String ngay;
			float tonkho;
			ResultSet rs2;
			String query;
			
			query = "DELETE CBTH FROM ERP_CANHBAOTHIEUHANG CBTH " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = CBTH.SANPHAM_FK " + 
					"WHERE SP.MA = '" + masp + "'";
			this.db.update(query);
			
			query = "SELECT	'1INV' AS ID,  SANXUAT_SP.SANPHAM_FK AS SPID, " + 
					"SUM(KHO_SP.SOLUONG) AS QTY, NULL AS KHO, " +	 
					"'" + this.getDateTime() + "' AS NGAY " +   	
					"FROM ERP_KHOTT_SANPHAM KHO_SP  " +	
					"INNER JOIN ERP_SANPHAM_SANXUAT SANXUAT_SP ON SANXUAT_SP.SANPHAM_FK = KHO_SP.SANPHAM_FK " +
					"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = KHO_SP.SANPHAM_FK " +
					"WHERE SP.MA = '" + masp + "'" + 	
					"GROUP BY SANXUAT_SP.SANPHAM_FK ";
			
			//System.out.println("___1_Check thieu hang: " + query);
			rs2 = this.db.get(query);
			tonkho = 0;
			if (rs2 != null)
			{
				if(rs2.next())
					tonkho = Float.parseFloat(rs2.getString("QTY"));
				else 
					tonkho = 0;
				rs2.close();
			}
			// Chon ngay du kien giao hang cua cac SO
			query = 	"SELECT DISTINCT NGAYDUKIENGIAO FROM ERP_DONDATHANG " +
						"WHERE TRANGTHAI < 3 AND MAKETOSTOCK = 0 AND NGAYDUKIENGIAO >= '" + this.getDateTime() + "' " +
						"ORDER BY NGAYDUKIENGIAO";
			
			ResultSet rs = this.db.get(query);
			
			ngay = this.getDateTime();
			if (rs != null)
			{
				// Kiem tra xem co xay ra thieu hang tai cac ngay du kien giao hang cua SO khong? Neu co, them vao bang ERP_CANHBAOTHIEUHANG
				while(rs.next()){			
					query = 	"SELECT * FROM	" +
							"( " + 
						  	"( " +
							"	SELECT	'2PRO' AS ID, " + 
							"	LSX_SP.SANPHAM_FK AS SPID, " +
							"	SUM(LSX.SOLUONG) AS QTY, NULL AS KHO, '" +  rs.getString("NGAYDUKIENGIAO") + "' AS NGAY " + 	
							"	FROM ERP_LENHSANXUAT_GIAY LSX INNER JOIN ERP_LENHSANXUAT_SANPHAM LSX_SP on LSX.PK_SEQ = LSX_SP.LENHSANXUAT_FK " +
							"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = LSX_SP.SANPHAM_FK  "	+ 
							"	WHERE	SP.MA = '" + masp + "' AND LSX.NGAYDUKIENHT >= '" + ngay + "' AND  LSX.NGAYDUKIENHT < '" + rs.getString("NGAYDUKIENGIAO") + "'" +
							" 	AND LSX.TRANGTHAI <> '3' " +
							"	GROUP BY LSX_SP.SANPHAM_FK " +
							")" +		
							"UNION	" +
							"( " +
							"	SELECT	'3DEM' AS ID,	" + 
							"	DDH_SP.SANPHAM_FK AS SPID, " +
							"	SUM(DDH_SP.SOLUONG) AS QTY, " +			
							"	'0' AS KHO, DDH.NGAYDUKIENGIAO AS NGAY " + 	
							"	FROM ERP_DONDATHANG DDH " +												
							"	INNER JOIN ERP_DONDATHANG_SP DDH_SP ON DDH_SP.DONDATHANG_FK = DDH.PK_SEQ " +
							"	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK "	+						
							"	WHERE SP.MA = '" + masp + "' AND DDH.MAKETOSTOCK = '0' AND DDH.TRANGTHAI < 3 AND DDH.NGAYDUKIENGIAO = '" + rs.getString("NGAYDUKIENGIAO") + "'" +
							"	GROUP BY DDH_SP.SANPHAM_FK, DDH.NGAYDUKIENGIAO " +
							"))A	" +
							"ORDER BY ID, NGAY";
						
					rs2 = this.db.get(query);
					if (rs2 != null)
					{
						while(rs2.next()){
							if(rs2.getString("ID").equals("2PRO")){
								tonkho = tonkho + Float.parseFloat(rs2.getString("QTY"));
							}
						
							if(rs2.getString("ID").equals("3DEM")){
								if(tonkho - Float.parseFloat(rs2.getString("QTY")) < 0){
									query = "INSERT INTO ERP_CANHBAOTHIEUHANG(SANPHAM_FK, NGAY, TONGYEUCAU, TONKHO, THIEU )" +
									"SELECT PK_SEQ, '" + rs.getString("NGAYDUKIENGIAO") + "', '" + rs2.getString("QTY") + "', " +
									"'" + tonkho + "', '" + (Float.parseFloat(rs2.getString("QTY")) - tonkho) + "' " +
									"FROM ERP_SANPHAM  " +
									"WHERE MA = '" + masp + "'";
									
									//System.out.println("cau lenh insert " + query);
									this.db.update(query);
								}
							
								tonkho = tonkho - Float.parseFloat(rs2.getString("QTY"));
							}
						}
						
						rs2.close();
					}
					ngay = rs.getString("NGAYDUKIENGIAO");
				}
				rs.close();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public boolean Delete()
	{
		return false;
	}
	
	public ResultSet getrsdvkd()
	{
		return this.rsdvkd;
	}
	
	public void setrsdvkd(ResultSet rsdvkd)
	{
		this.rsdvkd = rsdvkd;
	}
	
	public void setdvkdid(String dvkdid)
	{
		this.DvkdId = dvkdid;
	}
	
	public String getdvkdid()
	{
		return DvkdId;
	}
	
	public void setUserTen(String _Userten)
	{
		this.userten = _Userten;
	}
	
	public String getUserten()
	{
		return this.userten;
	}
	
	public void DBClose()
	{
		try
		{
			if (this.spThieuList != null)
				this.spThieuList.clear();
			if (this.chitietCongnoRs != null)
				this.chitietCongnoRs.close();
			if (this.listsanpham != null)
				this.listsanpham.clear();
			if (this.scheme_tongtien != null)
				this.scheme_tongtien.clear();
			if (this.scheme_chietkhau != null)
				this.scheme_chietkhau.clear();
			if (this.scheme_sanpham != null)
				this.scheme_sanpham.clear();
			if (this.hopdongRs != null)
				this.hopdongRs.close();
			if (this.tienteRs != null)
				this.tienteRs.close();
			if (this.dvdlRs != null)
				this.dvdlRs.close();
			if (this.schemeRs != null)
				this.schemeRs.close();
			if (this.chitietCongnoRs !=  null)
				this.chitietCongnoRs.close();
			if (this.scheme_tongtien != null)
				this.scheme_tongtien.clear();
			if (this.hopdongRs != null)
				this.hopdongRs.close();
			if (this.tienteRs != null)
				this.tienteRs.close();
			if (this.dvdlRs != null)
				this.dvdlRs.close();
			if (this.schemeRs != null)
				this.schemeRs.close();
			if (rskenh != null)
			{
				rskenh.close();
			}
			if (rskho != null)
			{
				rskho.close();
			}
			if (rsnhapp != null)
			{
				rsnhapp.close();
			}
			if (rsnhacc != null)
			{
				rsnhacc.close();
			}
			if (rsdvkd != null)
			{
				rsdvkd.close();
			}
			if (this.chitietCongnoRs != null)
			{
				this.chitietCongnoRs.close();
			}
			if(listsanpham!=null){
				listsanpham.clear();
			}
			if(scheme_chietkhau!=null){
				scheme_chietkhau.clear();
			}
			if(scheme_sanpham!=null){
				scheme_sanpham.clear();
			}
			if(spThieuList!=null){
				spThieuList.clear();
			}
			
		}
		catch (Exception err)
		{
			err.printStackTrace();
		}
		finally{
			if (db != null)
			{
				db.shutDown();
			}
		}
	}
	
	public void setloaichietkhau(String _loaichietkhau)
	{
		loaichietkhau = _loaichietkhau;
	}
	
	public String getloaichietkhau()
	{
		return this.loaichietkhau;
	}
	
	public String getdiachi()
	{
		return this.Diachi;
	}
	
	public String getdiachixhd()
	{
		return this.DiaChiXhd;
	}
	
	public String getmasothue()
	{
		return this.Masothue;
	}
	
	public void setdiachi(String diachi)
	{
		this.Diachi = diachi;
	}
	
	public void setdiachixhd(String diachixhd)
	{
		this.DiaChiXhd = diachixhd;
	}
	
	public void setmasothue(String mst)
	{
		this.Masothue = mst;
	}
	
	public String getISKM()
	{
		return this.ISKM;
	}
	
	public void setISKM(String iskm)
	{
		this.ISKM = iskm;
	}
	
	public String[] getSotien()
	{
		return this.Sotien;
	}
	
	public void setSotien(String[] sotien)
	{
		this.Sotien = sotien;
	}
	
	public String[] getScheme()
	{
		return this.Scheme;
	}
	
	public void setScheme(String[] scheme)
	{
		this.Scheme = scheme;
	}
	
	public String getGhichu()
	{
		return this.ghichu;
	}
	
	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}
	
	public String getNoidungchietkhau()
	{
		return this.noidungchietkhau;
	}
	
	public void setNoidungchietkhau(String noidungchietkhau)
	{
		this.noidungchietkhau = noidungchietkhau;
	}

	public String getMakeToStock() 
	{
		return this.maketoStock;
	}

	public void setMakeToStock(String maketoStock) 
	{
		this.maketoStock = maketoStock;
	}
	
	//tra km
	public Hashtable<String, Float> getScheme_Tongtien() 
	{
		return this.scheme_tongtien;
	}

	public void setScheme_Tongtien(Hashtable<String, Float> scheme_tongtien) 
	{
		this.scheme_tongtien = scheme_tongtien;
	}

	public Hashtable<String, Float> getScheme_Chietkhau() 
	{
		return this.scheme_chietkhau;
	}

	public void setScheme_Chietkhau(Hashtable<String, Float> scheme_chietkhau) 
	{
		this.scheme_chietkhau = scheme_chietkhau;
	}
	
	public List<ISanpham> getScheme_SpList() 
	{
		return this.scheme_sanpham;
	}

	public void setScheme_SpList(List<ISanpham> splist) 
	{
		this.scheme_sanpham = splist;
	}
	
	private void getTrakhuyenmai()
	{
		List<ISanpham> scheme_sp = new ArrayList<ISanpham>();
	
		String query = "select a.ctkmId, a.trakmId, a.soxuat, a.soluong,dv.diengiai as donvi, a.spMa, a.tonggiatri, b.scheme, c.loai, c.hinhthuc, c.chietkhau,  c.tongluong, c.tongtien, d.ten, d.pk_seq as spId " +
						"from erp_dondathang_ctkm_trakm a inner join ctkhuyenmai b on a.ctkmid = b.pk_seq inner join trakhuyenmai c on a.trakmid = c.pk_seq left join erp_sanpham d on a.spMa = d.ma  " +
							"left join donvidoluong dv on dv.pk_seq=a.dvdl_fk " +
						"where a.dondathangId = '" + this.Id+ "'";
		System.out.println("__Khoi tao tra KM: " + query);
		ResultSet rs = db.get(query);
		if( rs != null)
		{
			try 
			{
				while(rs.next())
				{
					String schemeName = rs.getString("scheme");				
					String soxuat = rs.getString("soxuat");	
					String soluong = rs.getString("soluong");
					String loai = rs.getString("loai");
					String tongiatri = rs.getString("tonggiatri");
					String donvi=rs.getString("donvi");
					float tongtien = 0;

					if (loai == null)
					{
						if(rs.getString("spMa") == null)
						{
							if(rs.getString("tongtien") != null)
								tongtien = Float.parseFloat(rs.getString("tongtien"));
							this.scheme_tongtien.put(schemeName, tongtien * Integer.parseInt(soxuat));
						}
					}
					else
					{
						if(loai.equals("1")) //tra tien
						{
							if(rs.getString("tongtien") != null)
								tongtien = Float.parseFloat(rs.getString("tongtien"));
							this.scheme_tongtien.put(schemeName, tongtien * Integer.parseInt(soxuat));
						}
						else
						{
							if(loai.equals("2")) //tra chiet khau
							{
								this.scheme_chietkhau.put(schemeName, Float.parseFloat(tongiatri));
							}
							else //tra sp
							{			
								String[] param = new String[8];
								ISanpham sp = null;	

								param[0] = rs.getString("spId");
								param[1] = rs.getString("spMa");
								param[2] = rs.getString("ten");
								param[3] = soluong;
								param[4] = donvi;
								param[5] = "0";
								param[6] = schemeName;
								param[7] = "0";
				
								sp = new Sanpham(param);
								scheme_sp.add(sp);
							}
						}
					}
					this.coKhuyenmai = true;
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.scheme_sanpham = scheme_sp;
		
	}

	public String getNgaydukiengiao()
	{
		return this.ngaydukiengiao;
	}

	public void setNgaydukiengiao(String ngaygiaodich) 
	{
		this.ngaydukiengiao = ngaygiaodich;
	}

	public double getTongtienKM() 
	{
		return this.TongTienKM;
	}

	public void setTongtienKM(double ttKM) 
	{
		this.TongTienKM = ttKM;
	}

	public double getTongtiensauKM()
	{
		return this.TongTienSauKM;
	}

	public void setTongtiensauKM(double ttSauKm) 
	{
		this.TongTienSauKM = ttSauKm;
	}

	public String getCongtyId()
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public void setUserId(String UserId) {
		
		this.UserId=UserId;
	}

	
	public String getUserId() {
		
		return this.UserId;
	}

	
	public ResultSet getHopDongRs() {
		
		return this.hopdongRs;
	}

	
	public void setHopDongRs(ResultSet hdRs) {
		
		this.hopdongRs = hdRs;
	}

	
	public String getHopdongId() {
		
		return this.hopdongId;
	}

	
	public void setHopdongId(String hdId) {
		
		this.hopdongId = hdId;
	}

	
	public ResultSet getTienteRs() {
		
		return this.tienteRs;
	}

	
	public void setTienteRs(ResultSet ttRs) {
		
		this.tienteRs = ttRs;
	}


	
	public String getTienteId() {
		
		return this.tienteId;
	}

	
	public void setTienteId(String ttId) {
		
		this.tienteId = ttId;
	}

	
	public String getTyGiaQuyDoi() {
		
		return this.tygia;
	}

	
	public void setTyGiaQuyDoi(String tygia) {
		
		this.tygia = tygia;
	}

	public String getBgId() {
		
		if(this.KenhBanHangId.length() > 0 & this.NppId.length() > 0){
			String query = 	"SELECT ISNULL(BG.PK_SEQ, 0) AS BGID " +
							"FROM ERP_KHACHHANG_KENHBANHANG KH_KBH " +
							"INNER JOIN ERP_BANGGIABAN_KH BG_KH ON BG_KH.KH_FK = KH_KBH.KHACHHANG_FK " +
							"INNER JOIN ERP_BANGGIABAN BG ON BG.PK_SEQ = BG_KH.BANGGIABAN_FK " +
							"WHERE KH_KBH.KENHBANHANG_FK = " + this.KenhBanHangId + " AND KH_KBH.KHACHHANG_FK = " + this.NppId + " " +
							"and BG.TUNGAY <= '" + this.NgayGiaoDich + "' AND BG.DENNGAY >= '" + this.NgayGiaoDich + "' ";
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			try{
				if(rs != null){
					if(rs.next()){
						this.bgId = rs.getString("BGID");
					}else{
						this.bgId = "0";
					}
					rs.close();
				}else{
					bgId = "0";
				}
			}catch(java.sql.SQLException e){
				this.bgId = "0";
			}
		}else{
			this.bgId = "0";
		}
		return this.bgId;
	}

	
	public void setBgId(String bgId) {
		
		this.bgId = bgId;
	}
	
	public String getChophepsuagia() {
		
		return this.chophepsuagia;
	}

	
	public void setChophepsuagia(String cpSuaGiaId) {
		
		this.chophepsuagia = cpSuaGiaId;
	}
	
	
	public static void main(String[] arg)
	{
		dbutils db = new dbutils();
		String query = "select pk_seq, QUYCACH from ERP_TEM_TONDAU";
		//System.out.println("___LAY TON DAU: " + query);
		
		ResultSet rsTest = db.get(query);
		
		if(rsTest != null)
		{
			try 
			{
				while(rsTest.next())
				{
					try
					{
						String pk_seq = rsTest.getString("pk_seq");
						String quycach = rsTest.getString("QUYCACH");
						
						String rong = quycach.substring(0, quycach.indexOf("MM"));
						
						quycach = quycach.substring(quycach.indexOf("MM") + 4, quycach.length());
						String dai = quycach.trim().substring(0, quycach.lastIndexOf(" M"));
						
						query = "Update ERP_TEM_TONDAU set rong = N'" + rong.trim() + "', dai = N'" + dai.trim() + "' where pk_seq = '" + pk_seq + "'";
						//System.out.println("__Update: " + query);
						db.update(query);
					}
					catch (Exception e) {
						e.printStackTrace();
					}
				}
				rsTest.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	public String getChophepdoiKH() {
		
		return this.chophepdoiKH;
	}

	
	public void setChophepdoiKH(String cpDoiKh) {
		
		this.chophepdoiKH = cpDoiKh;
	}


	public String getLoaihopdong() {

		return this.loaihopdong;
	}


	public void setLoaihopdong(String lhdId) {
		
		this.loaihopdong = lhdId;
	}
	

	String SoTienBaoHiem;
	public String getSoTienBaoHiem()
	{
		return SoTienBaoHiem;
	}

	public void setSoTienBaoHiem(String SoTienBaoHiem)
	{
		this.SoTienBaoHiem = SoTienBaoHiem;
	}
	
	public double getDungsai() {
		return this.dungsai;
	}

	
	public void setDungsai(String dungsai) {
		try {
			this.dungsai = Double.parseDouble(dungsai);
		} catch(Exception e) { }
	}
	
	public void setDungsai(double dungsai) {
		this.dungsai = dungsai;
	}
	

	
	public String getHinhthucTT() {
		
		return this.hinhthuctt;
	}

	
	public void setHinhthucTT(String httt) {
		
		this.hinhthuctt=httt;
	}
	

	
	public String getPaymentTerms() {
		return this.paymentTerms;
	}

	
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	
	public String getDeliveryTerms() {
		return this.deliveryTerms;
	}

	
	public void setDeliveryTerms(String deliveryTerms) {
		this.deliveryTerms = deliveryTerms;
	}

	
	public String getETD() {
		return this.etd;
	}

	
	public void setETD(String ETD) {
		this.etd = ETD;
	}

	
	public String getRemarks() {
		return this.remarks;
	}

	
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	
	public double getFreightCost() {
		return this.freightCost;
	}

	
	public void setFreightCost(String freightCost) {
		try { this.freightCost = Double.parseDouble(freightCost); } catch(Exception e) { } 
	}

	
	public void setFreightCost(double freightCost) {
		this.freightCost = freightCost;
	}
	
	public ResultSet getDvdlRs() {
		
		return this.dvdlRs;
	}

	
	public void setDvdlRs(ResultSet dvdlRs) {
		
		this.dvdlRs = dvdlRs;
	}

	
	public String getSoPo() {
	
		return this.Sopo;
	}

	
	public void setSoPo(String SoPo) {
	
		this.Sopo=SoPo;
		
	}

	
	
	NumberFormat formatterThapphan = new DecimalFormat("#,###,###.###");
	
	
	
	public void InitDisplay() 
	{
		if(this.Id!=null && this.Id.trim().length() > 0)
		{
			db = new dbutils();
			String sql ="   select ddh.loaidonhang, isnull(ddh.sopo,'') as sopo  , isnull(ddh.customerspo, '') as customerspo, ddh.ghichu, ddh.noidungchietkhau, ddh.pk_seq, ngaydat, NGAYDUKIENGIAO, iskm, isnull (sotienbvat, 0) as sotienbvat, ddh.nguoitao, ddh.nguoisua, ddh.trangthai, ddh.makeToStock, " +
						" 	khachhang_fk, npp.congty_fk, isnull (vat,0) as vat, isnull (sotienavat,0) as sotienavat, ddh.dvkd_fk, ddh.kbh_fk, " +
						" 	isnull(chietkhau,0) as chietkhau,isnull(ddh.ungck,0) as ungck,isnull(ddh.CKTrucTiep,0) as CKTrucTiep,isnull(ddh.ckthuongmai,0) as ckthuongmai, isnull(loaidonhang,'0') as loaidonhang, isnull(loaichietkhau,'0') as loaichietkhau, ddh.khott_fk,  " +
						" 	npp.ma + ', ' + npp.ten as tennpp, npp.diachigiaohang as diachixhd, npp.diachi, npp.mst as masothue, isnull(khuyenmai.tongtienKM, 0) as tongtienKM,  ddh.tiente_fk, ddh.CHOPHEPSUAGIA, ddh.FROM_HOPDONG, isnull(ddh.SoTienBaoHiem, 0) as SoTienBaoHiem, " +
						" 	isnull(ddh.dungsai, 0) as dungsai, isnull(ddh.hinhthuctt, '') as hinhthuctt, isnull(ddh.paymentterms, '') as paymentterms, isnull(ddh.deliveryterms, '') as deliveryterms, isnull(ddh.etd, '') as etd, isnull(ddh.remarks, '') as remarks, isnull(ddh.FreightCost, 0) as FreightCost, " + 
						" 	isnull(ddh.yeucauGDduyet,0) yeucauGDduyet, isnull(ddh.DIACHIGIAOHANG, '') DIACHIGIAOHANG, " +
						"	isnull ( ( select schemeIds from ERP_DONDATHANG_SCHEME_CHON where dondathang_fk = ddh.pk_seq ) , '' ) as schemeIds, isnull(ddh.bantructiep,0) bantructiep " +
						"   from ERP_DonDatHang ddh " +
						"   inner join ERP_KhachHang npp on npp.pk_seq = ddh.KhachHang_FK " +
						"   left join " +
						"   ( " +
						" 	select '" + this.Id + "' as ddhId, isnull(sum(tonggiatri), 0) as tongtienKM from erp_dondathang_ctkm_trakm " +
						" 	where dondathangId = '" + this.Id + "' and spMa is  null " +
						"   ) khuyenmai on ddh.pk_seq = khuyenmai.ddhId " +
						"   where  ddh.pk_Seq = " + this.Id;
			
			//System.out.println("Câu init display : "+sql);
			ResultSet rs = db.get(sql);
			try
			{
				if (rs != null)
				{
					if (rs.next())
					{
						this.NgayGiaoDich = rs.getString("ngaydat");
						this.ngaydukiengiao = rs.getString("NGAYDUKIENGIAO");
						this.IDNhaCungCap = rs.getString("congty_fk");
						this.khoId = rs.getString("khott_fk");
						this.TrangThai=rs.getString("trangthai");
						this.setISKM(rs.getString("iskm"));
						this.setNgaygiaodich(rs.getString("ngaydat"));
						this.setIDKenhBanHang(rs.getString("kbh_fk"));
						this.setdvkdid(rs.getString("dvkd_fk"));
						this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
							
						this.VAT = rs.getDouble("VAT");
						this.UngCK = rs.getDouble("UngCK");
						this.CKThuongMai = rs.getDouble("CKThuongMai");
						this.CKTrucTiep = rs.getDouble("CKTrucTiep");
						this.ChietKhau = rs.getDouble("chietkhau");
						this.TongTienKM = rs.getDouble("tongtienKM");
						this.Sopo=rs.getString("sopo");
						this.setMakeToStock(rs.getString("makeToStock"));
						this.setdiachi(rs.getString("diachi"));
						this.setdiachixhd(rs.getString("diachixhd"));
						this.setmasothue(rs.getString("masothue"));
						this.setGhichu(rs.getString("ghichu"));
						this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
						//this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk");
						this.tienteId = rs.getString("tiente_fk") == null ? "100000" : rs.getString("tiente_fk");
						this.chophepsuagia = rs.getString("CHOPHEPSUAGIA") == null ? "0" : rs.getString("CHOPHEPSUAGIA");
						this.chophepdoiKH = rs.getString("FROM_HOPDONG") == null ? "0" : rs.getString("FROM_HOPDONG");
						this.loaidonhang = rs.getString("loaidonhang");
						
						if(this.chophepdoiKH.equals("1") && this.NppId.trim().length() <= 0 )
						{
							this.NppId = rs.getString("khachhang_fk");
							this.setNppTen(rs.getString("tennpp"));
						}
						this.SoTienBaoHiem=rs.getString("SoTienBaoHiem")==null?"0":formatter.format(rs.getDouble("SoTienBaoHiem"));
						
						this.dungsai = rs.getFloat("dungsai");
						this.hinhthuctt = rs.getString("hinhthuctt");
						this.paymentTerms = rs.getString("PAYMENTTERMS");
						this.deliveryTerms = rs.getString("DELIVERYTERMS");
						this.etd = rs.getString("ETD");
						this.remarks = rs.getString("REMARKS");
						this.customerspo = rs.getString("customerspo");
						this.freightCost = rs.getDouble("FreightCost");
						this.ycgdduyet = rs.getString("yeucauGDduyet");
						this.diachigiaohang = rs.getString("DIACHIGIAOHANG");
						this.schemeIds = rs.getString("schemeIds");
						this.bantructiep = rs.getString("bantructiep");
	
					}
				}
				rs.close();
				
			// Thuc hien lay thong tin sản phẩm
				if(this.listsanpham.size() <= 0 || this.Msg.length() == 0)
				{
					
					String strkhoId=this.khoId;
					if(this.khoId.equals("100003")|| this.khoId.equals("100004")){
						strkhoId="100003,100004";
					}
					String sql_getdetail = 
						"\n   select ddh_sp.KHOTT_FK , dondathang_fk, ddh_sp.sanpham_fk, ma, ten, " +
						"\n   donvi, soluong, isnull(ddh_sp.scheme,'') as scheme, " +
						"\n 	isnull(a.loaisanpham_fk, 0) as loaisanpham_fk, isnull(a.CHUNGLOAI_FK, 0) as CHUNGLOAI_FK," +
						"\n	isnull(ddh_sp.trongluong, 0) as trongluong, isnull(a.DVDL_TRONGLUONG, '') as DVDL_TRONGLUONG, " +
						"\n	isnull(a.thetich, '0') as thetich,  dongia, 0 as available, isnull(ddh_sp.ngaydukiengiao, '') as ngaydukiengiao,  " +
						"\n   ddh_sp.ghichu , isnull(tonhientai.ton, 0) as ton, isnull(sopallet,0) as sopallet, isnull(grossweight,0) as grossweight, isnull(netweight,0) as netweight,  isnull(ddh_sp.dongiaCK, 0) as dongiaCK, " +
						"\n	isnull(qc.SOLUONG1,0) as QUYCACH " +
					    "\n   from ERP_DonDatHang_sp ddh_sp  inner join erp_sanpham a on a.pk_seq = sanpham_fk  " +
					    "\n	left join (select * from QUYCACH where DVDL2_FK = 100004) qc on qc.SANPHAM_FK = a.PK_SEQ " +
					    "\n   left join  ( select KHOTT_FK,SANPHAM_FK , AVAILABLE as Ton " +
					    "\n   from ERP_KHOTT_SANPHAM  " +
					    "\n	where KHOTT_FK in ( " + strkhoId +")"+
					    "\n	) tonhientai on tonhientai.SANPHAM_FK = ddh_sp.SANPHAM_FK and tonhientai.KHOTT_FK = ddh_sp.KHOTT_FK	  " +
						"\n   inner join ERP_DonDatHang ddh on ddh.pk_Seq =ddh_sp.dondathang_fk " +
						"\n   where  ddh_sp.dondathang_fk = '" + this.Id + "' order by ddh_sp.pk_seq";
					
					//System.out.println("___Khoi tao don dat hang Update: " + sql_getdetail);
					this.listsanpham.clear();
					rs = db.get(sql_getdetail);
					if (rs != null)
					{
						while (rs.next())
						{
//							String quycach_sp="";
							IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setDonViTinh(rs.getString("donvi"));
							sanpham.setId(rs.getString("dondathang_fk"));
							sanpham.setIdSanPham(rs.getString("sanpham_fk"));
							sanpham.setGhichusp(rs.getString("ghichu"));
							sanpham.setGrossWeight(rs.getString("grossweight"));
							sanpham.setSoPallet(rs.getString("sopallet"));
							sanpham.set_DongiaCK(formatterThapphan.format(rs.getDouble("dongiaCK")));
							
							sanpham.setKhoid(rs.getString("KHOTT_FK"));
							sanpham.setQuycach(rs.getString("QUYCACH"));
							sanpham.setNetWeight(rs.getString("netweight"));
							sanpham.setSHEME(rs.getString("scheme"));
							
							sanpham.setTenSanPham(rs.getString("ten"));
							sanpham.setMaSanPham(rs.getString("ma"));
							
							double soluong = rs.getDouble("soluong");
							if(soluong > 0)
							{
								sanpham.setSoLuong(rs.getDouble("soluong"));
							}
							else
							{
								sanpham.setSoLuong(0);
							}
							
							sanpham.setSHEME(rs.getString("scheme"));
							
							if(rs.getString("donvi").toUpperCase().equals("KG")){//--- neu donvi = kg , thi bang = soluong   ----> trongluong=1
								sanpham.setTrongluong("1");
							}else{
								sanpham.setTrongluong(rs.getString("trongluong"));
							}
							
							sanpham.setThetich(rs.getString("thetich"));
							
							if(soluong > 0)
							{
								/*if(maketoStock.equals("1"))
									sanpham.setSoluongton(rs.getDouble("soluong") + rs.getDouble("ton"));
								else*/
									sanpham.setSoluongton(rs.getDouble("ton"));
							}
							else
							{
								sanpham.setSoluongton(0);
							}
						 
							sanpham.setNgaydukiengiao(rs.getString("ngaydukiengiao"));
							
							this.listsanpham.add(sanpham);
						}
					}
					rs.close();
					
					//HIỆN HẠN MỨC ĐƠN HÀNG, TỔNG SỐ NỢ, NỢ QUÁ HẠN, NỢ TRONG HẠN, 
					
					/*thông tin hạn mức lúc duyệt đơn hàng của CANFOCO như sau:

						1.	Hạn mức nợ: Lấy từ ô “Hạn mức nợ” trong dữ liệu nền Khách hàng.

						2.	Tổng số nợ: Lấy giống công thức tính số dư cuối trong báo cáo công nợ chi tiết khách hàng tính đến “Ngày giao dịch” của đơn hàng.

						3.	Số nợ vượt hạn mức = (2) – (1)

						4.	Thời hạn nợ: Lấy từ ô “Hạn mức nợ” trong dữ liệu nền Khách hàng.

						5.	Số nợ quá hạn: Là số tiền còn lại của các hoá đơn tài chính, và hoá đơn khác (còn có thể xoá nợ) mà ngày giao dịch của đơn hàng – ngày xuất hoá đơn (ngày ghi sổ) > thời hạn nơ. Thêm popup kế bên liệt kê chi tiết các hoá đơn này (Gồm STT, ngày hoá đơn, số hoá đơn, số tiền, số ngày quá hạn).

						6.	Tiền đơn hàng: là tổng tiền sau thuế của đơn hàng đang xem.

						7.	Tổng nợ sau đơn hàng = (2) + (6) */

						
					// 1.4 HẠN MỨC NỢ, THỜI HẠN NỢ
					String query=
						"	 SELECT KH.PK_SEQ, ISNULL(KH.HANMUCNO,0) HANMUCNO , ISNULL(KH.THOIHANNO,0)THOIHANNO \n"+   
						"	 FROM   ERP_KHACHHANG KH \n"+
						"	 WHERE KH.PK_SEQ in (SELECT KHACHHANG_FK FROM ERP_DONDATHANG WHERE PK_SEQ ='"+this.Id+"')";
					
					System.out.println("HAN MUC NO, THOI HAN NO: "+query);
					ResultSet congno = db.get(query);
					if(congno!=null){
						try{
							if(congno.next()){
								this.hanmucno = congno.getString("HANMUCNO");
								this.thoihanno = congno.getString("THOIHANNO");								
							}
							congno.close();
						}
						catch(Exception e){
							e.printStackTrace();
						}
					}
					
						// 2.	Tổng số nợ:
						query =     
						"	SELECT ISNULL(SUM(ISNULL(a.TANGTRONGKY,0)) - SUM (ISNULL(a.GIAMTRONGKY, 0)),0) AS DAUKY \n" +
						"   FROM  ( \n" ;
							
						query += 	
						
						//HÓA ĐƠN
						"	SELECT 	1 AS LOAI,CAST(HD.SOHOADON as NVARCHAR(50)) as SOHOADON, HD.PK_SEQ AS ID, NGAYXUATHD AS NGAY, PS.NO AS TANGTRONGKY, 0 AS GIAMTRONGKY, \n"+
						"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+   
						" 	FROM  	ERP_HOADON HD INNER JOIN ERP_PHATSINHKETOAN PS ON HD.PK_SEQ = PS.SOCHUNGTU \n"+  
						"       	INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK =  TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" 	WHERE 	HD.TRANGTHAI in (1,4,5)  AND LOAIHOADON = 0 \n"+
						"	    	AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"' \n"+ 
						"	    	AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"	    	AND (PS.LOAICHUNGTU = N'Hóa đơn' OR PS.LOAICHUNGTU = N'Hóa đơn đầu kỳ')  AND PS.NO > 0 \n "+
						" 			AND HD.KHACHHANG_FK IN (" + this.NppId + ") \n"+								
					
						" 	UNION ALL \n"+
						
						//BÚT TOÁN TỔNG HỢP
						"	SELECT 	2 AS LOAI,'' SOHOADON,BTTH_CT.BUTTOANTONGHOP_FK AS ID, BTTH.NGAYBUTTOAN AS NGAY, PS.NO AS TANGTRONGKY, 0 AS GIAMTRONGKY , \n"+
						"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						"	FROM   	ERP_BUTTOANTONGHOP_CHITIET BTTH_CT \n"+
						"	   		INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH.PK_SEQ = BTTH_CT.BUTTOANTONGHOP_FK \n"+
						"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH_CT.BUTTOANTONGHOP_FK = PS.SOCHUNGTU AND PS.MADOITUONG =  CONVERT(VARCHAR, BTTH_CT.KHACHHANG_FK) \n"+
						"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						"	WHERE   BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"' \n"+ 
						"	   		AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' \n"+ 
						"	   		AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"	   		AND PS.NO > 0 AND BTTH_CT.KHACHHANG_FK IS NOT NULL \n"+
						" 			AND BTTH_CT.KHACHHANG_FK IN  (" + this.NppId + ") \n"+
						
						" UNION ALL  \n"+

						//Hóa đơn phế liệu
						" SELECT 	3 AS LOAI,CAST(HD.SOHOADON as NVARCHAR(50)) as SOHOADON ,HD.pk_seq AS ID, HD.NGAYHOADON AS NGAY, PS.NO AS TANGTRONGKY, 0 AS GIAMTRONGKY , \n"+ 
						"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						" FROM   	ERP_HOADONPHELIEU HD \n"+ 
						"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON HD.pk_seq = PS.SOCHUNGTU \n"+
						"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE  	HD.TRANGTHAI = 1 AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"' \n"+ 
						"	   		AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"	   		AND PS.LOAICHUNGTU = N'Hóa đơn phế liệu' \n"+
						"	   		AND PS.NO > 0 \n "+
						" AND HD.KHACHHANG_FK IN   (" + this.NppId + ") \n"+
						
						" UNION ALL \n"+  

						//Hóa đơn phế liệu
						" SELECT 	4 AS LOAI,CAST(HD.SOHOADON as NVARCHAR(50)) as SOHOADON ,HD.pk_seq AS ID, HD.NGAYHOADON AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY , \n"+ 
						"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						" FROM   	ERP_HOADONPHELIEU HD \n"+ 
						"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON HD.pk_seq = PS.SOCHUNGTU \n"+
						"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE  	HD.TRANGTHAI = 1 AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"' \n"+ 
						"	   		AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"	   		AND PS.LOAICHUNGTU = N'Hóa đơn phế liệu'	\n"+
						"	   		AND PS.CO > 0 \n"+
						" AND HD.KHACHHANG_FK IN   (" + this.NppId + ") \n"+
						
						" UNION ALL \n"+	

						//THU TIỀN HÓA ĐƠN
						" SELECT  	5 as LOAI,'' SOHOADON ,TT.PK_SEQ AS ID, TT.NGAYCHUNGTU AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN , TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						" FROM    	ERP_THUTIEN TT LEFT JOIN  ERP_THUTIEN_HOADON TTHD ON TT.PK_SEQ = TTHD.THUTIEN_FK \n"+
						"			INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n"+
						"			INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE    	PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"'  \n"+ 
						"			AND TT.TRANGTHAI = 1 \n"+
						"			AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"			AND (PS.LOAICHUNGTU = N'Thu tiền hóa đơn' OR PS.LOAICHUNGTU = N'Thu tiền theo hóa đơn' OR PS.LOAICHUNGTU = N'Thu tiền KH trả trước' \n"+
						"			OR PS.LOAICHUNGTU = N'Thu khác' ) \n"+ 
						"			AND PS.CO > 0 \n"+
						" 			AND TT.KHACHHANG_FK IN   (" + this.NppId + ") \n"+
					
						" UNION ALL \n"+ 
						
						//BÚT TOÁN TỔNG HỢP
						" SELECT 	6 AS LOAI,'' SOHOADON,BTTH_CT.BUTTOANTONGHOP_FK AS ID, BTTH.NGAYBUTTOAN AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY , \n"+
						"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN , TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						" FROM   	ERP_BUTTOANTONGHOP_CHITIET BTTH_CT \n"+ 
						"	   		INNER JOIN ERP_BUTTOANTONGHOP BTTH ON BTTH.PK_SEQ = BTTH_CT.BUTTOANTONGHOP_FK \n"+
						"	   		INNER JOIN ERP_PHATSINHKETOAN PS ON BTTH_CT.BUTTOANTONGHOP_FK = PS.SOCHUNGTU AND PS.MADOITUONG =  CONVERT(VARCHAR, BTTH_CT.KHACHHANG_FK) \n"+
						"	   		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE  	BTTH.TRANGTHAI = '1' AND PS.NGAYCHUNGTU <= '" + this.NgayGiaoDich+"' \n"+ 
						"	   		AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG) \n"+
						"	   		AND PS.LOAICHUNGTU = N'Bút toán tổng hợp' AND PS.CO > 0 \n"+

						"	   		AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG) AND BTTH_CT.KHACHHANG_FK IS NOT NULL \n"+
						" 			AND BTTH_CT.KHACHHANG_FK IN   (" + this.NppId + ") \n"+
						
						" UNION ALL \n"+  
						 
						//HÓA ĐƠN BÁN HÀNG
						" SELECT 	7 AS LOAI,CAST(SOCHUNGTU as NVARCHAR(50)) as SOHOADON, CONVERT(VARCHAR, SOCHUNGTU) AS ID, NGAY, TANGTRONGKY AS  TANGTRONGKY, 0 AS GIAMTRONGKY, DATA.KHOANMUC, DATA.SOHIEUTAIKHOAN, DATA.SOHIEUTAIKHOAN_DU  \n"+
						" FROM 		( \n"+ 
						" 				SELECT  A.PK_SEQ,A.SOCHUNGTU, CAST (A.NAM AS CHAR(4))+'-'+   CASE WHEN  LEN(A.THANG) > 1  THEN CAST (THANG AS VARCHAR(2)) ELSE '0' + CAST (THANG AS VARCHAR(2)) END + CASE WHEN GHIDAO='0' THEN '-28' ELSE '01' END  AS NGAY, \n"+ 
						" 						B.CHENHLECH AS TANGTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						" 				FROM	ERP_DANHGIATIGIA A INNER JOIN ERP_DANHGIATIGIA_CHITIET B ON A.PK_SEQ = B.DANHGIATIGIA_FK \n"+
						" 						INNER JOIN ERP_PHATSINHKETOAN PS ON A.PK_SEQ = PS.SOCHUNGTU \n"+
						" 						INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"+
						"           			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" 				WHERE   A.TONGCHENHLECH > 0 AND A.TRANGTHAI = 1   \n"+
						" 				AND PS.NO > 0 \n"+ 
						" 				AND PS.LOAICHUNGTU = N'ĐGTG: Hóa đơn bán hàng' \n"+
						" 				AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						" 				AND A.KHACHHANG_FK IN   (" + this.NppId + ") \n"+	
						") AS DATA WHERE DATA.NGAY <='"+this.NgayGiaoDich+"' "+
						
						" UNION ALL \n"+ 
						
						//HÓA ĐƠN BÁN HÀNG
						" SELECT 	8 AS LOAI,CAST(SOCHUNGTU AS NVARCHAR(50)) AS SOHOADON,PK_SEQ AS ID, NGAY, 0 AS TANGTRONGKY , GIAMTRONGKY AS  GIAMTRONGKY, DATA.KHOANMUC, DATA.SOHIEUTAIKHOAN, DATA.SOHIEUTAIKHOAN_DU  \n"+
						" FROM 		( \n"+  
						" 				SELECT  A.PK_SEQ,A.SOCHUNGTU, CAST (A.NAM AS CHAR(4))+'-'+   CASE WHEN  LEN(A.THANG) > 1 \n"+  
						" 						THEN CAST (A.THANG AS VARCHAR(2)) ELSE '0' + CAST (A.THANG AS VARCHAR(2)) END + CASE WHEN GHIDAO='0' THEN '-28' ELSE '01' END  AS NGAY, \n"+ 
						" 						PS.CO AS GIAMTRONGKY, PS.KHOANMUC, TK.SOHIEUTAIKHOAN , TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						" 				FROM	ERP_DANHGIATIGIA  A INNER JOIN ERP_DANHGIATIGIA_CHITIET B ON A.PK_SEQ = B.DANHGIATIGIA_FK \n"+
						" 						INNER JOIN ERP_PHATSINHKETOAN PS ON A.PK_SEQ = PS.SOCHUNGTU \n"+
						" 						INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PS.TAIKHOAN_FK \n"+
						"           			INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" 				WHERE	A.TONGCHENHLECH < 0 AND A.TRANGTHAI = 1  \n"+ 
						" 						AND PS.CO > 0 \n"+ 
						" 						AND PS.LOAICHUNGTU = N'ĐGTG: Hóa đơn bán hàng' \n"+
						" 						AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						" 						AND A.KHACHHANG_FK IN   (" + this.NppId + ") \n"+	
						"	) AS DATA WHERE DATA.NGAY <='"+this.NgayGiaoDich+"'  \n"+ 
							
						" UNION ALL \n"+
						
						//HÓA ĐƠN TRẢ HÀNG KHÁCH HÀNG
						" SELECT 	9 AS LOAI,CAST(HD.SOHOADON as NVARCHAR(50)) as SOHOADON, HD.PK_SEQ AS ID, NGAYXUATHD AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY, \n"+
						"		   	PS.KHOANMUC, TK.SOHIEUTAIKHOAN , TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+ 
						" FROM   	ERP_HOADON HD INNER JOIN ERP_PHATSINHKETOAN PS ON HD.PK_SEQ = PS.SOCHUNGTU \n"+  
						"		    INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK =  TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE  	HD.TRANGTHAI in (1)  AND LOAIHOADON = 2 \n"+
						"		   	AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"'  \n"+ 
						"		    AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"		   AND PS.LOAICHUNGTU = N'Hóa đơn trả hàng khách hàng' AND PS.CO > 0 \n"+
						" 			AND HD.KHACHHANG_FK IN   (" + this.NppId + ") \n"+
						
						" UNION ALL \n"+
						
						//BÙ TRỪ CÔNG NỢ
						" SELECT 	10 AS LOAI,'' as SOHOADON, HD.PK_SEQ AS ID, NGAYBUTRU AS NGAY, PS.NO AS TANGTRONGKY, 0 AS GIAMTRONGKY, \n"+
						"		   	PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+   
						" FROM   	ERP_BUTRUKHACHHANG HD INNER JOIN ERP_PHATSINHKETOAN PS ON HD.PK_SEQ = PS.SOCHUNGTU \n"+  
						"		    INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK =  TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE  	HD.TRANGTHAI in (1)  \n"+
						"		   	AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"'  \n"+ 
						"		    AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"		   AND PS.LOAICHUNGTU = N'Bù trừ công nợ' AND PS.NO > 0 AND HD.KH_NHANNO IS NOT NULL \n"+
						" 			AND HD.KH_NHANNO IN   (" + this.NppId + ") \n"+
						" UNION ALL \n"+
						
						//BÙ TRỪ CÔNG NỢ
						" SELECT 	11 AS LOAI,'' as SOHOADON, HD.PK_SEQ AS ID, NGAYBUTRU AS NGAY, 0 AS TANGTRONGKY, PS.CO AS GIAMTRONGKY, \n"+
						"		   	PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+    
						" FROM   	ERP_BUTRUKHACHHANG HD INNER JOIN ERP_PHATSINHKETOAN PS ON HD.PK_SEQ = PS.SOCHUNGTU \n"+  
						"		    INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK =  TK.PK_SEQ \n"+
						"           INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n"+
						" WHERE  	HD.TRANGTHAI in (1)  \n"+
						"		   	AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"' \n"+ 
						"		    AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"		   	AND PS.LOAICHUNGTU = N'Bù trừ công nợ' AND PS.CO > 0 AND HD.KH_CHUYENNO IS NOT NULL \n"+
						" 			AND HD.KH_CHUYENNO IN   (" + this.NppId + ") \n"+
							
						// CẤN TRỪ CÔNG NỢ
						"		UNION ALL \n"+ 

						"		SELECT  DISTINCT 12 as LOAI, '' AS SOHOADON, CTCN.PK_SEQ AS ID, CTCN.NGAYCANTRU AS NGAY, 0 AS TANGTRONGKY, ISNULL(PS.CO, 0) AS GIAMTRONGKY, \n"+
						"	   		   PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						"		FROM    ERP_CANTRUCONGNO CTCN \n"+
						"		INNER JOIN ERP_PHATSINHKETOAN PS ON CTCN.PK_SEQ = PS.SOCHUNGTU \n"+
						"		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK = TK.PK_SEQ \n"+
						"       INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n " +
						"		WHERE   PS.NGAYCHUNGTU <= '" + this.NgayGiaoDich + "' \n "+
						"				AND CTCN.TRANGTHAI = 1  \n"+
						"				AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n "+
						"				AND (PS.LOAICHUNGTU = N'Cấn trừ công nợ' ) \n " +
						"				AND PS.CO > 0 \n" +
						" 				AND CTCN.KH_FK IN   (" + this.NppId + ") \n"+
													
						//NHẬN HÀNG TRẢ VỀ KHÔNG CÓ HÓA ĐƠN
						"		UNION ALL \n"+
						"		SELECT 13 AS LOAI, '' AS SOHOADON, NH.PK_SEQ AS ID, NH.NGAYNHAN AS NGAY,0 AS TANGTRONGKY, ISNULL(PS.CO,0) AS GIAMTRONGKY, \n " +
						"	   		   PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+
						"		FROM   ERP_NHANHANG NH INNER JOIN ERP_PHATSINHKETOAN PS ON NH.PK_SEQ = PS.SOCHUNGTU \n " +  
						"		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK =  TK.PK_SEQ \n " +
						"       INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n " +
						"       INNER JOIN DONTRAHANG TRAHANG ON TRAHANG.PK_SEQ = NH.TRAHANG_FK AND TRAHANG.KHACHHANG_FK = NH.NCC_KH_FK \n " +
						"		WHERE  NH.TRANGTHAI in (1)  \n " +
						"			   AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"' \n " + 
						"			   AND PS.TAIKHOAN_FK IN (SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG) \n"+
						"			   AND PS.LOAICHUNGTU = N'Nhận hàng' AND PS.CO > 0 AND NH.TRAHANG_FK IS NOT NULL \n "+
						" 			   AND TRAHANG.KHACHHANG_FK IN   (" + this.NppId + ") \n"+
						
						//CHI TRẢ TIỀN KH TẠM ỨNG
						"UNION ALL " +
						"		SELECT 14 AS LOAI, '' AS SOHOADON, TT.PK_SEQ AS ID, TT.NGAYGHINHAN AS NGAY, ISNULL(PS.NO,0) AS TANGTRONGKY, 0 AS GIAMTRONGKY,  \n "+
						"	   		PS.KHOANMUC, TK.SOHIEUTAIKHOAN, TK_DU.SOHIEUTAIKHOAN SOHIEUTAIKHOAN_DU \n"+ 
						"		FROM   ERP_THANHTOANHOADON TT " +
						"		INNER JOIN ERP_PHATSINHKETOAN PS ON TT.PK_SEQ = PS.SOCHUNGTU \n "+  
						"		INNER JOIN ERP_TAIKHOANKT TK ON PS.TAIKHOAN_FK =  TK.PK_SEQ \n "+
						"       INNER JOIN ERP_TAIKHOANKT TK_DU ON PS.TAIKHOANDOIUNG_FK = TK_DU.PK_SEQ \n " +
						"		WHERE  TT.TRANGTHAI in (1)  \n"+
						"			   AND PS.NGAYCHUNGTU <= '"+this.NgayGiaoDich+"' \n " + 
						"			   AND PS.TAIKHOAN_FK IN ( SELECT DISTINCT TAIKHOAN_FK FROM ERP_KHACHHANG ) \n"+
						"			   AND PS.LOAICHUNGTU = N'Thanh toán hóa đơn' AND PS.DOITUONG = N'Khách hàng' AND PS.NO > 0 AND TT.KHACHHANG_FK IS NOT NULL \n" +
						" 			   AND TT.KHACHHANG_FK IN   (" + this.NppId + ") \n"+
						
						" ) a \n";
					
						ResultSet congno1 = db.get(query);
						if(congno1!=null){
							try{
								if(congno1.next()){
									this.tongsono = congno1.getString("DAUKY");							
								}
								congno1.close();
							}
							catch(Exception e){
								e.printStackTrace();
							}
						}
					
						// 5.	Chi tiết Số nợ quá hạn: 
						query = 								
								"  (SELECT '0' AS LOAIHD, ISNULL(HOADON.TYGIA, 1) as TIGIA , HOADON.PK_SEQ, HOADON.MAHOPDONG AS MAHOPDONG, \n"+ 
								"        HOADON.KYHIEU, HOADON.SOHOADON, HOADON.NGAYHOADON, \n"+ 
								"        HOADON.TONGTIENAVAT AS SOTIENGOC, \n"+ 
								"        CAST((ISNULL(HOADON.TYGIA, 1)*(ISNULL(HOADON.TONGTIENAVAT, 0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0'))) AS numeric(18,0) ) AS SOTIENVND, \n"+ 
								"       (HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0')) AS SOTIENNT, \n"+ 
								"        0 AS DATHANHTOAN, HOADON.TTID, \n"+ 
								"        HOADON.NGAYDENHANTT \n"+ 
								"  FROM ( \n"+ 
								"		SELECT 	HD.PK_SEQ, cast(XK.DONDATHANG_FK as nvarchar(50)) AS MAHOPDONG, HD.KYHIEU, HD.SOHOADON, HD.NGAYXUATHD AS NGAYHOADON, \n"+ 
								"				HD.TONGTIENAVAT, ISNULL(HD.TYGIA,1) AS TYGIA, ISNULL(HD.TIENTE_FK,100000) AS TTID, \n"+
								"   			(DATEDIFF(DD,HD.NGAYXUATHD, '"+this.NgayGiaoDich+"')) as NGAYDENHANTT \n"+ 
								"		FROM 	ERP_HOADON HD 	\n"+
								"        		INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+ 
								"	     		LEFT JOIN ERP_HOADON_XUATKHO DDH ON DDH.HOADON_FK = HD.PK_SEQ \n"+ 
								"				LEFT JOIN ERP_XUATKHO XK ON DDH.XUATKHO_FK = XK.PK_SEQ \n"+  	
								"		WHERE 	HD.KHACHHANG_FK = "+this.NppId +" \n"+ 
								"				AND HD.TRANGTHAI in (1,3,4,5) AND HD.LOAIHOADON = '0' \n"+ 
								"   			AND (DATEDIFF(DD,HD.NGAYXUATHD, '"+this.NgayGiaoDich+"') > "+this.thoihanno+" ) "+
								" ) HOADON \n"+ 
								" LEFT JOIN ( \n"+ 
								"	SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
								"	FROM \n"+  
								"	( \n"+									
								"		SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+ 
								"		FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+ 
								"		INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+ 
								"		WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n"+ 
								"		AND TTHD.LOAIHD = '0' \n"+  
								"		GROUP BY HOADON_FK \n"+

								"		UNION ALL \n"+

								"		SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+ 
								"		FROM ERP_THUTIEN_HOADON TTHD \n"+ 
								"		INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+ 
								"		INNER JOIN ERP_HOADON HD ON HD.PK_SEQ = TTHD.HOADON_FK \n"+
								"		WHERE HD.LOAIHOADON= '0' AND TT.TRANGTHAI NOT IN (2) \n"+ 
								"		GROUP BY HOADON_FK \n"+ 

								"		UNION ALL \n"+ 

								"		SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU \n"+  
								"		FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+ 
								"		WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 0  AND BT.KH_CHUYENNO  = "+this.NppId +" \n"+ 
								"		GROUP BY BT_KH.HOADON_FK \n"+ 

								"	) HOADONDATT \n"+  
								"	GROUP BY HOADON_FK \n"+
								" )DATHANHTOAN ON HOADON.PK_SEQ = DATHANHTOAN.HOADON_FK \n"+ 
								" WHERE HOADON.TONGTIENAVAT - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') > 0 AND HOADON.TTID = "+this.tienteId +"  ) \n"+

								" UNION ALL \n"+

								" (SELECT '1' AS LOAIHD , HDPHELIEU.TYGIA AS TIGIA,HDPHELIEU.PK_SEQ ,HDPHELIEU.MAHOPDONG, HDPHELIEU.KYHIEU, \n"+ 
								"  HDPHELIEU.SOHOADON, HDPHELIEU.NGAYHOADON, \n"+ 
								"   ISNULL(HDPHELIEU.SOTIENVND,0) AS SOTIENGOC, \n"+
								"   ISNULL(HDPHELIEU.SOTIENVND,0) - ISNULL(DATHANHTOAN.DATHANHTOAN,0) AS SOTIENVND, \n"+ 
								"  0 AS SOTIENNT, 0 AS DATHANHTOAN, '100000' AS TTID, HDPHELIEU.NGAYDENHANTT \n"+ 
								" FROM \n"+ 
								" ( \n"+
								" SELECT '1' AS TYGIA,PL.PK_SEQ,'' AS MAHOPDONG, PL.KYHIEUHOADON AS KYHIEU, PL.SOHOADON, PL.NGAYHOADON, CAST( (PLSP.SOTIENVND + PLSP.SOTIENVND*PL.VAT/100) AS NUMERIC(18,0) ) AS SOTIENVND, \n"+
								"        0 AS SOTIENNT,0 AS DATHANHTOAN ,'100000' AS TTID , (DATEDIFF(DD,PL.NGAYGHINHAN, '"+this.NgayGiaoDich+"')) as NGAYDENHANTT \n"+ 
								" FROM ERP_HOADONPHELIEU PL \n"+ 
								"    INNER JOIN ERP_KHACHHANG KH ON PL.KHACHHANG_FK = KH.PK_SEQ \n"+ 
								"    INNER JOIN \n"+ 
								"    (SELECT HOADONPHELIEU_FK, SUM(THANHTIEN)AS SOTIENVND \n"+
								"     FROM ERP_HOADONPHELIEU_SANPHAM \n"+ 
								"     GROUP BY HOADONPHELIEU_FK)AS PLSP  ON PL.PK_SEQ= PLSP.HOADONPHELIEU_FK \n"+ 
								"	 WHERE PL.KHACHHANG_FK = "+this.NppId+" and PL.TRANGTHAI = '1' AND (DATEDIFF(DD, PL.NGAYGHINHAN, '"+this.NgayGiaoDich+"') > "+this.thoihanno+" ) "+ 
								" 	) HDPHELIEU \n"+ 
								" LEFT JOIN ( \n"+ 
								"	SELECT HOADON_FK, SUM(ISNULL(DATHANHTOAN, 0)) AS DATHANHTOAN \n"+ 
								"	FROM \n"+  
								"	( \n"+ 	
								"		SELECT TTHD.HOADON_FK, SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+ 
								"		FROM ERP_XOAKHTRATRUOC_HOADON TTHD \n"+ 
								"		INNER JOIN ERP_XOAKHTRATRUOC TT ON TTHD.XOAKHTRATRUOC_FK = TT.PK_SEQ \n"+ 
								"		WHERE TT.TRANGTHAI NOT IN (2) AND ISNULL(TT.LOAIXOATRATRUOC,0) = '0' \n"+ 
								"		AND TTHD.LOAIHD = '1' \n"+ 
								"		GROUP BY HOADON_FK \n"+ 
								                             
								"       UNION ALL \n"+
								
								"		SELECT TTHD.HOADON_FK , SUM(TTHD.SOTIENTT) AS DATHANHTOAN \n"+ 
								"		FROM ERP_THUTIEN_HOADON TTHD \n"+ 
								"		INNER JOIN ERP_THUTIEN TT ON TTHD.THUTIEN_FK = TT.PK_SEQ \n"+ 
								"		WHERE TTHD.LOAIHOADON= '1' AND TT.TRANGTHAI NOT IN (2) \n"+

								"		GROUP BY HOADON_FK \n"+ 

								"		UNION ALL \n"+

								"		SELECT BT_KH.HOADON_FK, SUM(BT_KH.XOANO) AS SOTIENBUTRU \n"+  
								"		FROM   ERP_BUTRUKHACHHANG BT INNER JOIN ERP_BUTRUKHACHHANG_CHITIET BT_KH ON BT.PK_SEQ =BT_KH.BTKH_FK \n"+ 
								"		WHERE  BT.TRANGTHAI NOT IN (2) AND BT_KH.LOAIHD = 1  AND BT.KH_CHUYENNO = "+this.NppId+

								"		GROUP BY BT_KH.HOADON_FK \n"+ 

								"	) HOADONDATT \n"+  
								"	GROUP BY HOADON_FK \n"+ 
								" )DATHANHTOAN ON HDPHELIEU.PK_SEQ = DATHANHTOAN.HOADON_FK \n"+ 
								" WHERE ISNULL(HDPHELIEU.SOTIENVND,0) - ISNULL(DATHANHTOAN.DATHANHTOAN, '0') != 0 \n"+ 
								")  ORDER BY NGAYHOADON ASC \n";		
				
						System.out.println(query);
						this.chitietCongnoRs = db.get(query);
					
					//Khoi tao tra khuyen mai
				}
				this.getTrakhuyenmai();
			}
			catch (Exception er)
			{
				er.printStackTrace();
				//System.out.println("1.Loi khoi tao:" + er.toString());
			}		
		
		}
  
		this.dvdlRs = db.getScrol("select PK_SEQ, DONVI from DONVIDOLUONG where CONGTY_FK = '" + this.congtyId + "'");
		
		String sql = "select pk_seq, ten from erp_congty where trangthai = '1' and pk_seq = '" + this.congtyId + "' ";
		this.rsnhacc = db.get(sql);				
		
//		sql = "select pk_seq, ten from kenhbanhang where trangthai != '2'";
		if(this.NppId.length() == 0){
			sql = "select pk_seq, ten " +
			  	  "from kenhbanhang where trangthai != '2' ";
		}else{
			sql = "SELECT KBH.PK_SEQ, KBH.TEN " +
				  "FROM ERP_KHACHHANG_KENHBANHANG KH_KENH " +
				  "INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ = KH_KENH.KENHBANHANG_FK " +
				  "WHERE KH_KENH.KHACHHANG_FK = " + this.NppId + "";
		}
		System.out.println("lấy kênh bán hàng: " + sql);
		
		this.rskenh = db.get(sql);
		
		sql = "select pk_seq, ma + ', ' + ten as ten from erp_tiente";
		this.tienteRs = db.get(sql);
		
		sql= " SELECT PK_SEQ, CASE WHEN PK_SEQ = 100003 THEN N'Kho thành phẩm - Hải Phòng' ELSE TEN END AS TEN " +
				"  FROM ERP_KHOTT WHERE    PK_SEQ IN (100003 ,100012,100013,100014,100000,100001,100002,100023,100024) ";
		this.rskho= db.get(sql);
		
		if(this.tienteId.trim().length() > 0)
		{
			sql = "select tigiaquydoi from ERP_TiGia where pk_seq = '" + this.tienteId + "' and trangthai = '1' ";
			ResultSet rs = db.get(sql);
			if(rs != null)
			{
				try 
				{
					if(rs.next())
					{
						this.tygia = Double.toString(rs.getDouble("tigiaquydoi"));
					}
					rs.close();
				} 
				catch (Exception e) {
					this.tygia = "1";
					e.printStackTrace();
				}
			}
		}
		
		sql = "select pk_seq, donvikinhdoanh as ten from donvikinhdoanh where trangthai = '1' ";
		if(this.IDNhaCungCap.trim().length() > 0)
			sql += " and pk_seq in ( select dvkd_fk from erp_congty_dvkd where congty_fk = '" + this.IDNhaCungCap + "' ) ";
		
		this.rsdvkd = db.get(sql);
			
		System.out.println("LOAI DON HANG  : "+this.loaidonhang);
 
		if(this.TrangThai.equals("1") && !this.loaidonhang.equals("7")){
			if(this.listsanpham.size() >0){
				for(int i=0;i <this.listsanpham.size();i++){
					IErp_Donbanhang_SP sp=this.listsanpham.get(i);
					this.GetGia(sp);
				}
			}
		}
		
		//if(this.KenhBanHangId.equals("100001") && this.NppId.trim().length() > 0 )
		if(this.NppId.trim().length() > 0 )
		{
			String spList = "";
			if(this.listsanpham.size() >0){
				for(int i=0;i <this.listsanpham.size();i++){
					spList += this.listsanpham.get(i).getIdSanPham() + ",";
				}
			}
			//Lấy những ctkm còn hiệu lực
			sql = "select pk_seq, scheme, diengiai  " +
				  "from CTKHUYENMAI where pk_seq in ( select ctkm_fk from CTKM_NPP where khachhang_fk = '" + this.NppId + "' ) " +
				  		"and pk_seq in ( select ctkm_fk from PHANBOKHUYENMAI where KHACHHANG_FK = '" + this.NppId + "') " +
				  		"and tungay <= '" + this.NgayGiaoDich + "' and '" + this.NgayGiaoDich + "' <= denngay ";
				  
			if(spList.length() > 0){
				spList = spList.substring(0, spList.length() - 1);
				sql += " and pk_seq in (select dk.CTKHUYENMAI_FK "+
						"from CTKM_DKKM dk inner join DIEUKIENKM_SANPHAM sp on sp.DIEUKIENKHUYENMAI_FK = dk.DKKHUYENMAI_FK " +
						"where sp.SANPHAM_FK in ("+spList+")) ";
			}
			sql += "order by scheme asc";
			this.schemeRs = db.get(sql);
		}
		
		//System.out.println("get scheme: " + sql);
	}

	
	public boolean SaveDungSai() {
	
		try{
			String query="UPDATE ERP_DONDATHANG set dungsai="+this.dungsai+" WHERE PK_SEQ="+this.Id;
			this.db.update(query);
			
		}catch(Exception er){
			this.Msg=er.getMessage();
			return false;
		}
		return true;
		
	}


	//*******  DÙNG ĐỂ IN  ĐƠN HÀNG XUẤT KHẨU  ***********//
	public void Donbanhang_XK(String donhangId) {
		
		this.Id= donhangId;

		if(this.Id.trim().length() > 0)
		{
			db = new dbutils();
			String sql = 	" select ddh.ghichu, ddh.noidungchietkhau, ddh.pk_seq, ngaydat, NGAYDUKIENGIAO, iskm, isnull (sotienbvat, 0) as sotienbvat, ddh.nguoitao, ddh.nguoisua, ddh.trangthai, ddh.makeToStock, " +
							" khachhang_fk, npp.congty_fk, isnull (vat,0) as vat, isnull (sotienavat,0) as sotienavat, ddh.dvkd_fk, ddh.kbh_fk, " +
							" isnull(chietkhau,0) as chietkhau, isnull(loaidonhang,'0') as loaidonhang, isnull(loaichietkhau,'0') as loaichietkhau, " +
							" npp.ten as tennpp, npp.diachigiaohang as diachixhd, npp.diachi, npp.mst as masothue, isnull(khuyenmai.tongtienKM, 0) as tongtienKM, ddh.tiente_fk, ddh.CHOPHEPSUAGIA, npp.ThoiHanNo as phuongthucthanhtoan, npp.NguoiLienHe + ' - ' + npp.DT_Nguoilienhe as nguoilienhe,SoTienBaoHiem " +
							" , tt.ma as tienteten" +
							" from ERP_DonDatHang ddh " +
							" inner join ERP_KhachHang npp on npp.pk_seq = ddh.KhachHang_FK " +
							" left join ERP_TIENTE tt on ddh.tiente_fk=tt.pk_seq" +
							" left join " +
							"( " +
								"select '" + this.Id + "' as ddhId, isnull(sum(tonggiatri), 0) as tongtienKM from erp_dondathang_ctkm_trakm " +
								"where dondathangId = '" + this.Id + "' and spMa is  null " +
							" ) khuyenmai on ddh.pk_seq = khuyenmai.ddhId " +
							" where  ddh.pk_Seq = " + this.Id;
			
			ResultSet rs = db.get(sql);
			try
			{
				if (rs != null)
				{
					if (rs.next())
					{
						this.NgayGiaoDich = rs.getString("ngaydat");
						this.ngaydukiengiao = rs.getString("NGAYDUKIENGIAO");
						this.NppId = rs.getString("khachhang_fk");
						this.IDNhaCungCap = rs.getString("congty_fk");
						//this.congtyId= rs.getString("congty_fk");
						this.setISKM(rs.getString("iskm"));
						this.setNgaygiaodich(rs.getString("ngaydat"));
						//this.setIDKenhBanHang(rs.getString("kbh_fk"));
						this.setdvkdid(rs.getString("dvkd_fk"));
					
						this.setTongtientruocVAT(rs.getDouble("sotienbvat"));
						this.VAT = rs.getDouble("VAT");
						this.ChietKhau = rs.getDouble("chietkhau");
						this.TongTienKM = rs.getDouble("tongtienKM");
						
						this.setNppTen(rs.getString("tennpp"));
						//this.setMakeToStock(rs.getString("makeToStock"));
						this.setdiachi(rs.getString("diachi"));
						this.setdiachixhd(rs.getString("diachixhd"));
						this.setmasothue(rs.getString("masothue"));
						this.setGhichu(rs.getString("ghichu"));
						this.setNoidungchietkhau(rs.getString("noidungchietkhau"));
						//this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk");
						this.tienteId = rs.getString("tiente_fk") == null ? "100000" : rs.getString("tiente_fk");
						this.chophepsuagia = rs.getString("CHOPHEPSUAGIA") == null ? "0" : rs.getString("CHOPHEPSUAGIA");
						
						this.setMakeToStock(rs.getString("nguoilienhe")); //Nguoi lien he
						this.setIDKenhBanHang(rs.getString("phuongthucthanhtoan")); //Phuong thuc thanh toan
						
						this.SoTienBaoHiem=rs.getString("SoTienBaoHiem")==null?"0":formatter.format(rs.getDouble("SoTienBaoHiem"));
						
						this.TTten= rs.getString("tienteten");
						
					}
					rs.close();
				}
				sql= "SELECT MAHOPDONG FROM ERP_HOPDONG WHERE PK_SEQ='"+this.hopdongId+"'  ";
				ResultSet hd = db.get(sql);
				if(hd!=null){
					try{
						while(hd.next()){
							this.hopdongten= hd.getString("MAHOPDONG");
						}
						hd.close();
					}catch(Exception e)
					{
						e.printStackTrace();
					}
				}
				
				
				// Thuc hien lay thong tin don hang
				String sql_getdetail =  "  SELECT DONDATHANG_FK, SANPHAM_FK, ma, TEN, QUYCACH, DDH_SP.DONVI, SOLUONG, SCHEME, 	ISNULL(DDH_SP.TRONGLUONG, '0') AS TRONGLUONG, " +  
										"  ISNULL(SP.THETICH, '0') AS THETICH,  DONGIA, 0 AS AVAILABLE, ISNULL(DDH_SP.NGAYDUKIENGIAO, '') AS NGAYDUKIENGIAO  , " +  
										"  ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG,  " +  
										"  ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG,  " +  
										"  ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO,  " +  
										"  ISNULL(SP.DAIDAY, 0) AS DAIDAY,      ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG,  " +  
										"  ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG,  " +  
										"  ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY,  " +  
										"  ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DVDL_DAUNHO, '') AS DVDL_DAUNHO,  " +  
										"  ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY,  ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU   ," +
										"  SP.TEN2 AS tenXHD ,SP.DVKD_FK AS dvkdid, ISNULL(SP.CHUNGLOAI_FK,0) AS CHUNGLOAI_FK, isnull(DDH_SP.ghichu,'') as ghichu, isnull(grossweight,0) as grossweight, isnull(sopallet,0) as sopallet, isnull(netweight,0) as netweight, isnull(DVDL.DIENGIAIANH,'') as dvEng,   " +  
										"  isnull(SP.TRONGLUONG,0) as trongluong2, DDH_SP.DVDL_FK as dh_dvdl_fk , SP.DVDL_FK as sp_dvdl, SP.DVKD_FK as sp_dvkd " +
					
										"  FROM ERP_DONDATHANG_SP DDH_SP   " +  
										"  INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = SANPHAM_FK   " +  
										"  INNER JOIN ERP_DONDATHANG DDH ON DDH.PK_SEQ =DDH_SP.DONDATHANG_FK 	 " + 
										" left join DONVIDOLUONG DVDL ON SP.DVDL_FK= DVDL.PK_SEQ " +
										"  WHERE  DDH_SP.DONDATHANG_FK =  " +this.Id;
				
				System.out.println("___Khoi tao don dat hang: " + sql_getdetail);
				this.listsanpham.clear();
				rs = db.get(sql_getdetail);
				if (rs != null)
				{
					while (rs.next())
					{
						IErp_Donbanhang_SP sanpham = new Erp_Donbanhang_SP();
						sanpham.setDonGia(rs.getDouble("dongia"));
						sanpham.setDonViTinh(rs.getString("donvi"));
						sanpham.setId(rs.getString("dondathang_fk"));
						sanpham.setIdSanPham(rs.getString("sanpham_fk"));
						
						sanpham.setMaSanPham(rs.getString("ma"));
						sanpham.setGhichusp(rs.getString("ghichu"));
						
						sanpham.setGrossWeight(rs.getString("grossweight"));
						sanpham.setSoPallet(rs.getString("sopallet"));
						sanpham.setNetWeight(rs.getString("netweight"));
						
						sanpham.setDonviEng(rs.getString("dvEng"));	
						
						System.out.println("don vi trong dh : " + rs.getString("donvi"));
						System.out.println("sanpham_fk : " + rs.getString("sanpham_fk"));

						
						
						//----- nếu dvdl trong đơn hàng là Kg, khác với dvdl chuẩn của sp thì qui đổi --
				
					/*	
						if(rs.getString("sp_dvkd").equals("100000")){  //-------- NHÔM
*/						String query1= " SELECT SOLUONG2 FROM QUYCACH WHERE SANPHAM_FK="+sanpham.getIdSanPham()+" AND DVDL2_FK=100003 ";
						ResultSet kt = db.get(query1);
		
						if(kt!=null){
							while(kt.next()){
								sanpham.setWeight(kt.getString("SOLUONG2"));
							}
							kt.close();
						}
						
						double soluong = rs.getDouble("soluong");
						if(soluong > 0)
						{
							sanpham.setSoLuong(rs.getDouble("soluong"));
						}
						else
						{
							sanpham.setSoLuong(0);
						}
						
						sanpham.setSHEME(rs.getString("scheme"));
						
						
						// --- quy doi ra trong luong neu donvi tinh khac KG---//
						if(rs.getString("donvi").toUpperCase().equals("KG")){
							sanpham.setTrongluong(rs.getString("soluong"));
							sanpham.setSoLuong(rs.getDouble("soluong")/rs.getDouble("trongluong2"));
						}
						else{
							if(Double.parseDouble(sanpham.getWeight()) >0){
								sanpham.setTrongluong(String.valueOf(Double.parseDouble(sanpham.getWeight())*rs.getDouble("soluong")));
							}else{
								sanpham.setTrongluong("0");
							}
						}
						if(rs.getDouble("TRONGLUONG") >0){
							
						}
					
						
						
						
						
						sanpham.setThetich(rs.getString("thetich"));
						
						if(soluong > 0)
						{
							sanpham.setSoluongton(rs.getDouble("soluong") + rs.getDouble("available"));
						}
						else
						{
							sanpham.setSoluongton(0);
						}
						
						sanpham.setTenXuatHoaDon(rs.getString("tenXHD"));
						sanpham.setNgaydukiengiao(rs.getString("ngaydukiengiao"));
//						String quycach_="";
						
						NumberFormat format = new DecimalFormat("#,###,###.###");
//						NumberFormat format2 = new DecimalFormat("#,###,###");
						String qc="";
						String dvkd_fk=rs.getString("dvkdid");
						String chungloai_fk=rs.getString("chungloai_fk");
					 
						double fDai = rs.getDouble("Dai"), fRong = rs.getDouble("RONG"),  fDinhLuong = rs.getDouble("DinhLuong"), fTrongluong = rs.getDouble("TrongLuong"), fDuongKinhTrong = rs.getDouble("DuongKinhTrong"), fDoDay = rs.getDouble("DoDay"), fDauLon = rs.getDouble("DauLon"), fDauNho = rs.getDouble("DauNho"), fDaiDay = rs.getDouble("DaiDay");
						String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong), sDuongKinhTrong = format.format(fDuongKinhTrong), sDoDay = format.format(fDoDay), sDauLon = format.format(fDauLon), sDauNho = format.format(fDauNho), sDaiDay = format.format(fDaiDay);
						String mau ="";
					 	if(!dvkd_fk.trim().equals("100005") || chungloai_fk.trim().equals("100042")){
					 		//đối với sp mới không cần in màu
					 		mau = rs.getString("MAU").trim(); if(mau.toUpperCase().indexOf("NO") >= 0 || mau.toUpperCase().indexOf("KHONG") >= 0 || mau.toUpperCase().indexOf("KHÔNG") >= 0) mau = "";
					 	}
					 	 
						String mauin = rs.getString("MAUIN").trim(); if(mauin.toUpperCase().indexOf("NO") >= 0 || mauin.toUpperCase().indexOf("KHONG") >= 0 || mauin.toUpperCase().indexOf("KHÔNG") >= 0) mauin = "";
						
						if(dvkd_fk.equals("100004")) {
							
							qc=rs.getString("quycach");
							System.out.println("Qc :  -----------------------"+qc);
							
						 
						} else  if(dvkd_fk.equals("100000")) {
							//nhom
						    if(fRong > 0) { qc += " " +  sRong.replaceAll(",", "") + rs.getString("DVDL_Rong"); }
						    if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai.replaceAll(",", "")  + rs.getString("DVDL_DAI"); }
						    if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong.replaceAll(",", "")  + rs.getString("DVDL_DINHLUONG"); }
						}else{
						 
									if(fDuongKinhTrong > 0) { 
							  		qc  = " " + format.format(fDuongKinhTrong) + rs.getString("DVDL_DKTRONG"); }
							  
								    if(fDai > 0) { 
								    	if(qc.length() > 0) { qc += " x"; } qc += " " +  format.format(fDai).replaceAll(",", "")  + rs.getString("DVDL_DAI");
								    }
								 
								    if(fRong> 0) { 
								    	if(qc.length() > 0) { qc += " x"; } qc += " " + format.format(fRong).replaceAll(",", "") + rs.getString("DVDL_RONG");
								    }
								    
								    if(fDaiDay> 0) { 
								    	if(qc.length() > 0) { qc += " x"; } qc += " " + format.format(fDaiDay).replaceAll(",", "") + rs.getString("DVDL_daiday");
								    }
								    
								    if(mau.length()> 0) { 
								    	if(qc.length() > 0) { qc += " x"; } qc += " " + mau ;
								    }
							  //  if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDinhluong + rs.getString("DVDL_DINHLUONG"); }

						}
								
						sanpham.setQuycach(qc);	 
						sanpham.setTenSanPham(rs.getString("tenXHD") );
						this.listsanpham.add(sanpham);
					}
				}
				rs.close();
				//Khoi tao tra khuyen mai
				this.getTrakhuyenmai();
			}
			catch (Exception er)
			{
				er.printStackTrace();
				//System.out.println("1.Loi khoi tao:" + er.toString());
			}		
		}		
	}


	public String getTienteTen() {
		return this.TTten;
	}
	
	public void setTienteTen(String TTten) {
		this.TTten=  TTten;
	}

	public String getHopdongTen() {
		return this.hopdongten;
	}

	public void setHopdongTen(String hopdongten) {
		this.hopdongten=hopdongten;
	}

	public void setUngck(double ungck) {
		this.UngCK=ungck;
	}

	public double getUngck() {
		return this.UngCK;
	}
	
	public void setCkthuongmai(double ckthuongmai) {
		this.CKThuongMai=ckthuongmai;
	}

	public double getCkthuongmai() {
		return this.CKThuongMai;
	}
	
	public void setCKTrucTiep(double CKTrucTiep) {
		this.CKTrucTiep=CKTrucTiep;
	}
	
	public double getCKTrucTiep() {
		return this.CKTrucTiep;
	}

	public String getHanmucno() {
		return this.hanmucno;
	}
	
	public String getTongsono() {
		return this.tongsono;
	}
	
	public String getNoquahan() {
		return this.noquahan;
	}
	
	public String getNotronghan() {
		return this.notronghan;
	}
	
	public String getYCGDDuyet() {
		return this.ycgdduyet;
	}
	
	public void setYCGDDuyet(String yeucauGDDuyet) {
		this.ycgdduyet = yeucauGDDuyet;
	}
	
	public String getDathanhtoan() {
		return this.dathanhtoan;
	}
	
	public void setDathanhtoan(String dathanhtoan) {
		this.dathanhtoan = dathanhtoan;
	}
	
	public String getduyetDH() {
		return this.duyetdh;
	}
	
	public void setduyetDH(String duyetdh) {
		this.duyetdh = duyetdh;
	}
	
	public String[] getTichLuy_Scheme() {
		return this.tichluy_scheme;
	}
	
	public void setTichLuy_Scheme(String[] tichluy_scheme) {
		this.tichluy_scheme = tichluy_scheme;
	}
	
	public String[] getTichLuy_Tongtien() {
		return this.tichluy_tongtien;
	}
	
	public void setTichLuy_Tongtien(String[] tichluy_tongtien) {
		this.tichluy_tongtien = tichluy_tongtien;
	}
	
	public void ApTichLuy() 
	{
		/*try
		{
			String query = "delete DUYETTRAKHUYENMAI_DONHANG where donhang_fk = '" + this.Id + "' ";
			db.update(query);
			
			//Tinh tong gia tri don hang
			query = "select sum( (soluong * giamua) - chietkhau )   " +
						"- isnull( ( select sum(tonggiatri) as giatriKM from donhang_ctkm_trakm where donhangId = '" + this.Id + "' and SPMA is null ), 0) as tongGiatri    " +
					"from donhang_sanpham         " +
					"where donhang_fk = '" + this.Id + "'       " +
					"group by donhang_fk ";
			
			System.out.println("__1.Tong gia tri f=don hnag: " + query);
			ResultSet rsDh = db.get(query);
			double tongGiaTriDH = 0;
			if(rsDh.next())
			{
				tongGiaTriDH = rsDh.getDouble("tongGiatri");
			}
			rsDh.close();
			
			
			query = " select a.PK_SEQ, c.SCHEME, khId,  " +
						"thuong - isnull(( select SUM(thanhtoan) from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = '" + this.NppId + "' and duyetkm_fk = b.duyetkm_fk  ), 0) as conLai  " +
					"from DUYETTRAKHUYENMAI a inner join DUYETTRAKHUYENMAI_KHACHHANG b on a.pk_seq = b.duyetkm_fk  " +
					"inner join TIEUCHITHUONGTL c on a.ctkm_fk = c.PK_SEQ  " +
					"where a.trangthai = '1' and b.khId = '" + this.NppId + "' " +
						"and thuong - isnull(( select SUM(thanhtoan) from DUYETTRAKHUYENMAI_DONHANG where khachhang_fk = '" + this.NppId + "' and duyetkm_fk = b.duyetkm_fk  ), 0) > 0  " +
					"order by c.NAM desc, c.THANG desc ";
			
			System.out.println("__2.LAY SCHEME TICH LUY: " + query);
			ResultSet rsTL = db.get(query);
			if(rsTL != null)
			{
				NumberFormat format = new DecimalFormat("#,###,###");
				String schemeMa = "";
				String schemeGiaTri = "";
				
				double total = 0;
				while(rsTL.next())
				{
					double thuongCL = rsTL.getDouble("conLai");
					String tichluyId = rsTL.getString("PK_SEQ");
					String scheme = rsTL.getString("SCHEME");
					
					total += thuongCL;
					if(total <= tongGiaTriDH)
					{
						//Luu lai
						double thuong = thuongCL;
						schemeMa += scheme + "__";
						schemeGiaTri += format.format(thuong) + "__";
						
						//Insert DONHANG TICH LUY
						query = "insert DUYETTRAKHUYENMAI_DONHANG(duyetkm_fk, khachhang_fk, donhang_fk, thanhtoan) " +
								"values('" + tichluyId + "', '" + this.NppId + "', '" + this.Id + "', '" + thuong + "')";
						
						db.update(query);
						System.out.println("__3.Chen data: " + query);
						
					}
					else
					{
						double thuong = tongGiaTriDH - (total - thuongCL);
						schemeMa += scheme + "__";
						schemeGiaTri += format.format(thuong) + "__";
						
						query = "insert DUYETTRAKHUYENMAI_DONHANG(duyetkm_fk, khachhang_fk, donhang_fk, thanhtoan) " +
								"values('" + tichluyId + "', '" + this.NppId + "', '" + this.Id + "', '" + thuong + "')";
						
						db.update(query);
						System.out.println("__3.2.Chen data: " + query);
				
						break;
					}
					
				}
				rsTL.close();
				
				if(schemeMa.trim().length() > 0)
				{
					schemeMa = schemeMa.substring(0, schemeMa.length() - 2 );
					this.tichluy_scheme = schemeMa.split("__");
					
					schemeGiaTri = schemeGiaTri.substring(0, schemeGiaTri.length() - 2);
					this.tichluy_tongtien = schemeGiaTri.split("__");
				}
			}
		}
		catch (Exception e) 
		{
			System.out.println("__Exception ap KM TL: " + e.getMessage());
		}*/
	}

	
	public String getLoaidonhang() {

		return this.loaidonhang;
	}

	public void setLoaidonhang(String loaidonhang) {
	
		this.loaidonhang = loaidonhang;
	}

	
	public String getDiachigiaohang() {
		
		return this.diachigiaohang;
	}

	
	public void setDiachigiaohang(String diachigiaohang) {
		
		this.diachigiaohang = diachigiaohang;
	}

	
	public boolean getCoKhuyenmai() {
		return this.coKhuyenmai;
	}

	
	public String getSchemeIds() {
		
		return this.schemeIds;
	}

	
	public void setSchemeIds(String schemeIds) {
		
		this.schemeIds = schemeIds;
	}

	
	public ResultSet getSchemeRs() {
		
		return this.schemeRs;
	}

	
	public void setSchemeRs(ResultSet schemeRs) {
		
		this.schemeRs = schemeRs;
	}

	
	public String getTungaytk() {
		
		return this.tungaytk;
	}

	
	public void setTungaytk(String tungaytk) {
		
		this.tungaytk = tungaytk;
	}

	
	public String getDenngaytk() {
		
		return this.denngaytk;
	}

	
	public void setDenngaytk(String denngaytk) {
		this.denngaytk = denngaytk;
		
	}

	
	public String getKhachhangtk() {
		
		return this.khachhangtk;
	}

	
	public void setKhachhangtk(String khachhangtk) {
		this.khachhangtk = khachhangtk;
		
	}

	
	public String getLoaidhtk() {
		
		return this.loaidhtk;
	}

	
	public void setLoaidhtk(String loaidhtk) {
		this.loaidhtk = loaidhtk;
		
	}

	
	public String getTrangthaitk() {
		
		return this.trangthaitk;
	}

	
	public void setTrangthaitk(String Trangthaitk) {
		
		this.trangthaitk = Trangthaitk;
	}

	
	public String getDonvikinhdoanhtk() {
		
		return this.donvikinhdoanhtk;
	}

	
	public void setDonvikinhdoanhtk(String Donvikinhdoanhtk) {
		this.donvikinhdoanhtk = Donvikinhdoanhtk;
		
	}

	
	public String getSochungtutk() {
		
		return this.sochungtutk;
	}

	
	public void setSochungtutk(String Sochungtutk) {
		
		this.sochungtutk = Sochungtutk;
	}

	
	public String getNguoitaotk() {
		
		return this.nguoitaotk;
	}

	
	public void setNguoitaotk(String Nguoitaotk) {
		
		this.nguoitaotk = Nguoitaotk;
	}

	
	public String getKhodattk() {
		
		return this.khodattk;
	}

	
	public void setKhodattk(String Khodattk) {
		this.khodattk = Khodattk;
		
	}

	
	public String getKbhtk() {
	
		return this.kbhtk;
	}

	
	public void setKbhtk(String Kbhtk) {
	
		this.kbhtk = Kbhtk;
	}

	
	public String getBantructiep() {
		
		return this.bantructiep;
	}

	
	public void setBantructiep(String bantructiep) {
		
		this.bantructiep = bantructiep;
	}

	public String getThoihanno() {
		
		return this.thoihanno;
	}

	
	public String getNgayvuothanno() {
		
		return this.ngayvuotno;
	}

	
	public String getSonovuothanmuc() {
		
		return this.sonovuothanmuc;
	}

	
	public void setCoKhuyenmai(boolean value) {
		this.coKhuyenmai = value;
	}

	
	public ResultSet getChitietCongnoRs() {
	
		return this.chitietCongnoRs;
	}

	
	public void setChitietCongnoRs(ResultSet ChitietCongnoRs) {
	
		this.chitietCongnoRs = ChitietCongnoRs;
	}
	
	
}
