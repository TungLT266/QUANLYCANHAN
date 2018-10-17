package geso.traphaco.erp.beans.hoadon.imp;

import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon;
import geso.traphaco.erp.beans.hoadon.IErpHoaDonList;
import geso.traphaco.erp.beans.hoadon.IErpHoaDon_SP;
import geso.traphaco.erp.beans.hoadon.IHdDetail;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

public class ErpHoaDon extends Phan_Trang implements IErpHoaDon 
{
	private static final long serialVersionUID = 1L;
	String congtyId;
	String Id;
	String NgayXuaHd;
	String TrangThai;
	String NgayTao;
	String NguoiTao;
	String NgaySua;
	String NguoiSua;
	String HoanTat;
	String SoXuatkho="";
	String ChoPhepSuaGiaHD="";
	String DiachiNPP="";
	String invoice;
	
	ResultSet lichsuinRs;
	String lichsuinId;
	
	ResultSet tienteRs;
	String tienteId;
	String tienteTen;
	String tygia;
	
	double tienchuaVat;
	double chietKhau;
	double tienChietKhau = 0;
	double tienKhuyenMai;
	String KM_ghichu;
	double tienSauCK_Km;
	double VAT;
	double tienVAT = 0;
	double TienSauVat;
	String SoPo;
	String Msg;
	String Kyhieu;
	String SoHoaDon;
	String IdDonHangDat;
	String HinhThucTT;
	String NppId;
	String NccId;
	String NppTen;
	String khoId;
	String KhoTen;
	String LoaiDonHang;
	String DeNghiDatHangId;
	String POMT;
	String sohopdong;
	String dienthoai;
	String fax;

	ResultSet listHoaDon;
	ResultSet rsddhChuaXuatHD;
	List<IErpHoaDon_SP> listsanpham = new ArrayList<IErpHoaDon_SP>();
	
	/**
	 * Chỉ dành cho sản phẩm CONE để in hóa đơn tài chính
	 * Danh sách sản phẩm group theo key = 'mã lớn + duongkinhtrong x daulon x dai x daunho x doday' | đơn giá
	 * 	+ IdSanPham = key.
	 *  + Đơn giá
	 *  + Số lượng : tổng số lượng các sản phẩm chi tiết cùng key và cùng đơn giá
	 */
	List<IErpHoaDon_SP> listsanphamCone = new ArrayList<IErpHoaDon_SP>();
	
	String IDNhaCungCap;
	String TenNhaCungCap;
	String KenhBanHangId;
	String SoSO="";
	double NoCu=0;
	double SoTienTraTrungBay;
	String NguoiMuaHang;
	private int num;
	private int[] listPages;
	private int currentPages;
	dbutils db;
	
	String tungay;
	String denngay;
	String UserId;
	
	String DonDatHang;
	List<String> ddhIdList;
	
	String poInfo;
	String noidungCK;
	String GhiChu;
	String quydoi; //danh cho xuat hoa don
	Utility util=new Utility();
	
	String htXuat;
	
	String ngayghinhanCN;
	String diengiai;
	ResultSet hopdongRs;
	
	String hopdongId;
	Hashtable<String, String> sanpham_ghichu;
	Hashtable<String, Double> sanpham_gia;
	Hashtable<String, Double> htb_soluongmoi;
	String etd = "", paymentTerms = "", deliveryTerms = "", remarks = "";
	double SoTienBaoHiem = 0;
	double freightCost = 0;
	
	String customersPo = "";
	
	String dvkdId = "";
	ResultSet dvkdRs;
	
	//Hóa đơn có bao nhiêu dòng sản phẩm (để in)
	//int sodongsanpham = 0;
	//Mỗi dòng sản phẩm in được bao nhiêu ký tự (dùng để tính số dòng)
	//int sokytu1dongsp = 40;
	List<IErpHoaDonList> HoadonList;
	
	public ErpHoaDon()
	{
		db = new dbutils();
		this.SoXuatkho="";
		this.TrangThai="";
		this.tungay="";
		this.denngay="";
		this.SoHoaDon="";
		this.Msg="";
		this.NgayXuaHd="";
		this.SoPo="";
		
		this.HinhThucTT="";
		this.createKyHieu();
		this.NccId="";
		this.NppId="";
		this.NppTen="";
		this.NguoiMuaHang="";
		this.POMT="";
		this.poInfo = "";
		this.noidungCK = "";
		currentPages = 1;
		num = 1;
		this.Id="";
		this.GhiChu="";
		this.noidungCK="";
		this.dienthoai="";
		this.fax="";
		
		this.tienchuaVat = 0;
		this.chietKhau = 0;
		this.tienKhuyenMai = 0;
		this.KM_ghichu = "";
		this.tienSauCK_Km = 0;
		this.VAT = 0;
		this.TienSauVat = 0;
		this.KenhBanHangId="";
		this.tienteId = "";
		this.tienteTen="";
		this.tygia = "";
		this.htXuat = "";
		this.DonDatHang = "";
		this.quydoi = "";
		this.invoice="";
		this.lichsuinId = "";
		this.sohopdong="";
		this.ngayghinhanCN = "";
		this.diengiai = "";
		this.hopdongId = "";
		this.sanpham_ghichu = new Hashtable<String, String>();	
		this.SoTienBaoHiem = 0;
		this.freightCost = 0;
		this.KenhBanHangId="";
		ddhIdList = new ArrayList<String>();
		
		this.HoadonList = new ArrayList<IErpHoaDonList>();
		
	}
	
	private void createKyHieu() {
		// TODO Auto-generated method stub
		String query=" select top 1 kyhieu from erp_hoadon order by ngayxuathd desc";
		ResultSet rs=db.get(query);
		try{
			if(rs!=null && rs.next()){
					this.Kyhieu=rs.getString("kyhieu");
			}
			rs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
	}

	public ErpHoaDon(String id)
	{
		init(id, false);
	}
	 
	public void init(String id, boolean in)
	{
		try
		{
			String query =  " SELECT isnull(NPP.diachi,'') as  diachi,  ISNULL(HD.SOPO,'')  AS SOPO ,isnull(HD.trangthai,'') as trangthaihd  ,  isnull(NPP.CHOPPHEPSUAHD,'0') as CHOPPHEPSUAHD,HD.PK_SEQ,HD.NGAYXUATHD, " +
							" HD.GHICHU, HD.NOIDUNGCHIETKHAU, ISNULL(HD.PO_MT, 0) AS PO_MT, isnull(HD.NGUOIMUA,'') as nguoimua,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU, " +
							" HD.SOHOADON,HD.HINHTHUCTT, NCC.PK_SEQ as NCCID,  "+
							" NPP.TEN AS NPP,HD.KHACHHANG_FK, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA, TONGTIENBVAT, CHIETKHAU, ISNULL(TIENBAOHIEM, 0) AS TIENBAOHIEM, " +
							" ISNULL(TIENVANCHUYEN, 0) AS TIENVANCHUYEN, " +
							" ISNULL(TIENKHUYENMAI, 0) AS TIENKHUYENMAI, ISNULL(VAT, 0) AS VAT, ISNULL(TONGTIENAVAT, 0) AS TONGTIENAVAT, " +
							" ISNULL(HD.HOANTAT, 0) AS HOANTAT, ISNULL(NPP.KENHBANHANG_FK,100000) AS KENHBANHANG_FK , ISNULL(HD.TIENTE_FK,100000) as TIENTE_FK ,TT.MA AS MATT, HD.XUATTHEO, " +
							" isnull(ngayghinhanCN, HD.NGAYXUATHD ) as ngayghinhanCN, isnull(HD.diengiai, '') as diengiai, " +
							" ISNULL(HD.hopdong_fk, 0) AS HOPDONG_FK, ISNULL(HD.TIENCHIETKHAU, 0) AS TIENCHIETKHAU,ISNULL(HD.TIENKHUYENMAI, 0) AS TIENKHUYENMAI, " +
							" ISNULL(HD.TYGIA,1) AS TYGIA, " +
							" ISNULL(HD.KM_GHICHU, '') AS KM_GHICHU, isnull(HD.INVOICE,'') AS INVOICE," +
							"  ( SELECT COUNT(*) " +
							"    FROM ERP_HOADON " +
							"    WHERE PK_SEQ="+ id+" AND KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM nhomkhachhangtt_khachhangtt WHERE NKHTT_FK = '100001' )" +
							"  )AS ISKH_METRO  "+
							" FROM ERP_HOADON HD " +
							" LEFT JOIN ERP_KhachHang NPP ON NPP.PK_SEQ = HD.KHACHHANG_FK" +
							" LEFT JOIN ERP_Nhacungcap NCC ON NCC.PK_SEQ = HD.NCC_FK" +
							" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
							" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA " +
							" LEFT JOIN ERP_TIENTE TT ON HD.TIENTE_FK=TT.PK_SEQ " +
							" WHERE HD.PK_SEQ="+ id;
			
			System.out.println("1.init: " + query);
			String trangthai_hd="";
			int iskh_METRO = 0;
			db = new dbutils();
			ResultSet rs = db.get(query);
			if(rs != null)
			{	
				while (rs.next())
				{
					
					trangthai_hd=rs.getString("trangthaihd").trim();
					this.Id = id;
					this.SoPo=rs.getString("SOPO");
					this.ChoPhepSuaGiaHD=rs.getString("CHOPPHEPSUAHD");
					this.NgaySua = rs.getString("ngaysua");
					this.NgayTao = rs.getString("ngaytao");
					this.NgayXuaHd = rs.getString("NGAYXUATHD");
					this.NppId = rs.getString("KHACHHANG_FK")== null ? "":rs.getString("KHACHHANG_FK") ;
					this.NccId = rs.getString("NCCID")== null ? "":rs.getString("NCCID");
					this.NppTen = rs.getString("npp");
					this.SoHoaDon = rs.getString("sohoadon");
					this.TrangThai = rs.getString("trangthai");
					this.Kyhieu = rs.getString("kyhieu");
					this.DiachiNPP=rs.getString("diachi");
					this.HinhThucTT = rs.getString("HINHTHUCTT");
					this.NguoiMuaHang = rs.getString("NGUOIMUA");
					this.POMT = rs.getString("PO_MT");
					this.tygia = rs.getString("TYGIA");
					this.invoice = rs.getString("INVOICE");
					
					
					this.tienteId = rs.getString("TIENTE_FK");
					this.tienteTen = rs.getString("MATT");
					this.chietKhau = rs.getDouble("CHIETKHAU");
					this.tienChietKhau = rs.getDouble("TIENCHIETKHAU");
					this.tienKhuyenMai = rs.getDouble("TIENKHUYENMAI");
					this.KM_ghichu = rs.getString("KM_GHICHU");;
					this.tienchuaVat = rs.getDouble("TONGTIENBVAT");
					this.SoTienBaoHiem = rs.getDouble("TIENBAOHIEM");
					this.freightCost = rs.getDouble("TIENVANCHUYEN");
					this.tienSauCK_Km = rs.getDouble("TONGTIENBVAT") - (this.tienChietKhau > 0 ? this.tienChietKhau : this.chietKhau * rs.getDouble("TONGTIENBVAT") / 100) - this.tienKhuyenMai + this.SoTienBaoHiem + this.freightCost;
					System.out.println("tien sau ck_KM,...."+this.tienSauCK_Km );
					this.VAT = rs.getDouble("VAT") / this.tienSauCK_Km * 100;
					System.out.println("tien VAT,...."+this.VAT);
					//this.VAT = rs.getDouble("VAT") ;
					this.tienVAT = rs.getDouble("VAT");
					this.TienSauVat = rs.getDouble("TONGTIENAVAT");
					
					iskh_METRO = rs.getInt("ISKH_METRO");
					
					this.Msg = "";
					this.noidungCK = rs.getString("noidungchietkhau");
					this.GhiChu = rs.getString("ghichu");
					this.HoanTat = rs.getString("HoanTat");
					this.KenhBanHangId=rs.getString("KENHBANHANG_FK");
					this.htXuat = rs.getString("XuatTheo");
					
					this.ngayghinhanCN = rs.getString("ngayghinhanCN");
					this.diengiai = rs.getString("diengiai");
					this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk") ;
					this.sanpham_ghichu = new Hashtable<String, String>();
					
				}
				boolean kenhXuatKhau = this.KenhBanHangId.equals("100001");
				
				this.ddhIdList = new ArrayList<String>();
				String dvkd_fk = "";
				String kbh_fk = "";
				
			if(this.NppId.trim().length() > 0)
			{
				if(this.htXuat.equals("0"))
				{
					query = " SELECT HOADON_FK, DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " + this.Id + " ";
				}
				else
				{
					query = " SELECT HOADON_FK, XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = " + this.Id;
				}
				
				//System.out.println("2.Khoi tao DDH: " + query);
				
				rs = this.db.get(query);
				String ddhIds = "";
				if(rs != null)
				{
					//this.DonDatHang = new String[20];
					int i= 0;
					while(rs.next())
					{
						if(this.htXuat.equals("0"))
						{
							//this.DonDatHang[i] = rs.getString("DDH_FK");
							ddhIds += rs.getString("DDH_FK") + ",";
							this.ddhIdList.add(rs.getString("DDH_FK"));
						}
						else
						{
							//this.DonDatHang[i] = rs.getString("XUATKHO_FK");
							ddhIds += rs.getString("XUATKHO_FK") + ",";
							this.ddhIdList.add(rs.getString("XUATKHO_FK"));
						}
						i++;
					}
					rs.close();
					
					if(ddhIds.trim().length() > 0)
					{
						ddhIds = ddhIds.substring(0, ddhIds.length() - 1);
						this.DonDatHang = ddhIds;
					}
					else
					{
						this.DonDatHang = "";
					}
				}
				else
				{
					this.setMessage("Khong Lay Duoc Gia tri Don hang");
					return;
				}
				
				
				//Lấy một số thông tin đơn đặt hàng
				query = " SELECT isnull(ddh.paymentterms, '') as paymentterms, isnull(ddh.deliveryterms, '') as deliveryterms, " +
						" isnull(ddh.etd, '') as etd, isnull(ddh.remarks, '') as remarks, isnull(ddh.FreightCost, 0) as FreightCost, " +
						" isnull(ddh.CUSTOMERSPO, '') AS CUSTOMERSPO " +
						" FROM ERP_DONDATHANG ddh " +
						" WHERE ddh.PK_SEQ IN ( " +
						(this.htXuat.equals("0") 
							? " SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " + this.Id + " "
							: " SELECT TOP (1) DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ( SELECT TOP(1) XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = " + this.Id + " ) "
						) +
						" ) ";
				//System.out.println(query);
				
				rs = this.db.get(query);
				try {
					rs.next();

					this.paymentTerms = rs.getString("PAYMENTTERMS");
					this.deliveryTerms = rs.getString("DELIVERYTERMS");
					this.etd = rs.getString("ETD");
					this.remarks = rs.getString("REMARKS").trim();
					this.customersPo = rs.getString("CUSTOMERSPO");
					
					rs.close();
				} catch(Exception e) {
					
				}
				
				//System.out.println(" htXuat " + this.htXuat);			 
				if(this.htXuat.equals("0"))
				{
					
					String sql = " select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
								 " from ERP_dondathang ddh "+
								 " where khachhang_fk = '" + this.NppId + "' and ddh.trangthai >= 3  " +
								 " and ddh.pk_seq not in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk in ( select pk_seq from ERP_HOADON where trangthai != 2 ) ) " ;
		
					if(this.Id.trim().length() > 0)
					{
						sql += " union  " +
								"select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
								 "from ERP_dondathang ddh "+
								 "where ddh.pk_seq in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk = '" + this.Id + "' ) " ;
						if(!trangthai_hd.equals("0")){
							
							sql=	" select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
									 "from ERP_dondathang ddh "+
									 "where ddh.pk_seq in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk = '" + this.Id + "' ) " ;
						}
					}
					
					
					
					//System.out.println("DDH/Xuat kho -- cap nhat :" + sql);
					
					
					
					this.rsddhChuaXuatHD = db.get(sql);
					
					query = " select b.dvkd_fk, b.kbh_fk from erp_hoadon_ddh a inner join erp_dondathang b on a.ddh_fk = b.pk_seq " +
							" where a.hoadon_fk = '" + this.Id + "'";
					ResultSet rsDdh = db.get(query);
					if (rsDdh != null)
					{
						if(rsDdh.next())
						{
							dvkd_fk = rsDdh.getString("dvkd_fk");
							kbh_fk = rsDdh.getString("kbh_fk");
						}
						rsDdh.close();
					}
				}
				else
				{
					String sql = " select distinct xk.prefix + cast(xk.pk_seq as varchar(10) ) as soCT, xk.pk_seq as id, xk.ngayxuat as ngaydat, 0 as STT " +
								 " from ERP_XUATKHO xk inner join ERP_dondathang ddh on xk.dondathang_fk = ddh.pk_seq " +
								 " where ddh.khachhang_fk = '" + this.NppId + "' and xk.trangthai  in ( 1,4)  " +
								 " and xk.pk_seq not in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk in "
								 + " ( select pk_seq from ERP_HOADON where trangthai != 2 )  ) and xk.ngayxuat >= '2014-01-01'  " ;
					
					if(this.Id.trim().length() > 0)
					{
						sql += " union " +
							   " select distinct prefix + cast(pk_seq as varchar(10) ) as soCT, pk_seq as id, ngayxuat as ngaydat, 0 as STT " +
							   " from ERP_XUATKHO xk where pk_seq in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk = '" + this.Id + "' ) ";
						
						
						
						
							if(!trangthai_hd.equals("0")){
							
								sql=	 " select distinct prefix + cast(pk_seq as varchar(10) ) as soCT, pk_seq as id, ngayxuat as ngaydat, 0 as STT " +
									     " from ERP_XUATKHO xk where pk_seq in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk = '" + this.Id + "' )   ";
							}
					}
					
					sql += " order by xk.ngayxuat desc ";
					
					
					//System.out.println("____KHOI TAO XK -- da huy : " + sql);
					
					
					this.rsddhChuaXuatHD = db.get(sql);
					
					query = " select c.dvkd_fk, c.kbh_fk " +
							" from erp_hoadon_xuatkho a inner join erp_xuatkho b on a.xuatkho_fk = b.pk_seq " +
							" inner join erp_dondathang c on b.dondathang_fk = c.pk_seq " +
							" where a.hoadon_fk = '" + this.Id + "'";
					ResultSet rsDdh = db.get(query);
					if (rsDdh != null)
					{
						if(rsDdh.next())
						{
							dvkd_fk = rsDdh.getString("dvkd_fk");
							kbh_fk = rsDdh.getString("kbh_fk");
						}
						rsDdh.close();
					}
				}
			 }
				
				 				
				query=	" SELECT DISTINCT ISNULL(SP.MAMETRO,'') AS MAMETRO  , DDH_SP.SANPHAM_FK,SP.MA, SP.TEN + ' ' +  "+
						" case "+
						"	when substring(LTRIM(isnull(SP.QUYCACH,'')), 1, 1 ) = 'x' then STUFF(LTRIM(isnull(SP.QUYCACH,'')),1,1,'')"+
						"	else isnull(SP.QUYCACH,'') end  as tensp,  "+
						" isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, "+
						" isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, isnull(SP.QUYCACH, '') as QUYCACH , DDH_SP.DONGIA, DDH_SP.DONGIAVIET, "+
						" DDH_SP.SOLUONG AS SOLUONG, SP.loaisanpham_fk, isnull(DDH_SP.dvdl_fk,0) as dvdl_fk ,  "+
						" ISNULL(round(QUYCACH.SOLUONG1,0),'1') AS QUYDOI, DONVITINH AS DONVI , '' as scheme,      "+
						" ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG, "+
						" ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG, "+
						" ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO, "+
						" ISNULL(SP.DAIDAY, 0) AS DAIDAY,      ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG, "+
						" ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG, "+
						" ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY, "+
						" ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DVDL_DAUNHO, '') AS DVDL_DAUNHO, "+
						" ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY,  ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU,      "+
						" SP.ten1 as tenXHD1, SP.ten2 as tenXHD2, dvdl.DIENGIAIANH as DONVI_ENG, "+
						" isnull(DVDL.TENINAN, ISNULL(DVDL.DONVI, '')) as DVINAN,        "+
						" isnull(dvdl1.pk_seq, 0) as dvdl1_fk, isnull(dvdl1.DONVI,'') AS DVDL1TEN, "+
						" isnull(dvdl1.DIENGIAI,'') as DVDL1DG, isnull(QUYCACH.SOLUONG1,1) as DVDL1SL, "+
						" isnull(DVDL1.TENINAN, ISNULL(DVDL1.DONVI, '')) as DVINAN1,      "+
						" isnull(dvdl2.pk_seq, 0) as dvdl2_fk, isnull(dvdl2.DONVI,'') AS DVDL2TEN, "+
						" isnull(dvdl2.DIENGIAI,'') as DVDL2DG, isnull(QUYCACH.SOLUONG2,1) as DVDL2SL, "+
						" isnull(DVDL2.TENINAN, ISNULL(DVDL2.DONVI, '')) as DVINAN2,      "+
						" isnull(DDH_SP.ghichu, '') as ghichu, CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN ISNULL(DDH_SP.inrahd, '1') ELSE '1' END  as inrahd," +
						" SP.DVDL_FK as dvdlCHUAN,      "+
						" sp.dvdl_fk as dvdl_chuan ,  "+
						" isnull(sp.dvkd_fk, 0) as dvkd_fk, "+
						" isnull(sp.nhanhang_fk, 0) as nhanhang_fk, isnull(sp.chungloai_fk, 0) as chungloai_fk, "+
						" isnull(sp.loaisanpham_fk, 0) as loaisanpham_fk, A.SOLUONG as SOLUONGCHUAN  "+
						" FROM ERP_HOADON_SP DDH_SP    "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK "+
						" INNER JOIN ERP_HOADON HD ON HD.PK_SEQ= DDH_SP.HOADON_FK "+
						" left JOIN ERP_HOADON_DDH DDH ON HD.PK_SEQ= DDH.HOADON_FK "+
						" left JOIN ERP_DONDATHANG_SP A ON A.DONDATHANG_FK=DDH.DDH_FK AND A.SANPHAM_FK = DDH_SP.SANPHAM_FK "+
						" LEFT JOIN QUYCACH ON SP.PK_SEQ = QUYCACH.SANPHAM_FK  AND DDH_SP.DVDL_FK = QUYCACH.DVDL2_FK    "+
						" LEFT JOIN DONVIDOLUONG dvdl ON DDH_SP.dvdl_fk = dvdl.pk_seq  "+
						" LEFT JOIN DONVIDOLUONG dvdl1 ON QUYCACH.DVDL1_FK = DVDL1.PK_SEQ "+
						" LEFT JOIN DONVIDOLUONG dvdl2 ON QUYCACH.DVDL2_FK = DVDL2.PK_SEQ "+
						" WHERE DDH_SP.SOLUONG > 0 AND DDH_SP.HOADON_FK = '" + this.Id + "'     "+
						" order by (CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN ISNULL(DDH_SP.inrahd, '1') ELSE '1' END)  DESC, SP.MA, ISNULL(SP.DUONGKINHTRONG,0), "+
						" ISNULL(SP.DVDL_DKTRONG,''), ISNULL(SP.DAULON,0), ISNULL(SP.DVDL_DAULON,''), ISNULL(SP.DAI,0), "+
						" ISNULL(SP.DVDL_DAI,''), ISNULL(SP.DAUNHO,0), ISNULL(SP.DVDL_DAUNHO,''), ISNULL(SP.DODAY,0), "+
						" ISNULL(SP.DVDL_DODAY,''), DDH_SP.DONGIA ASC, SCHEME ASC  ";

				
			    String nhanhang_fk, chungloai_fk, loaisanpham_fk, dvdl_fk, dvdl1_fk, dvdl2_fk;
			    System.out.println("reload--: "+query);
				rs = this.db.get(query);
				if(rs != null)
				{
					try
					{
					 	NumberFormat format = new DecimalFormat("#,###,###.###");
					 	NumberFormat format2 = new DecimalFormat("#,###,###");
					 
						while(rs.next())
						{
							dvkd_fk = rs.getString("dvkd_fk");
							nhanhang_fk = rs.getString("nhanhang_fk");
							chungloai_fk = rs.getString("chungloai_fk");
							loaisanpham_fk = rs.getString("loaisanpham_fk");
							
							
							double fDai = rs.getDouble("Dai"), fRong = rs.getDouble("RONG"),  fDinhLuong = rs.getDouble("DinhLuong"), fTrongluong = rs.getDouble("TrongLuong"), fDuongKinhTrong = rs.getDouble("DuongKinhTrong"), fDoDay = rs.getDouble("DoDay"), fDauLon = rs.getDouble("DauLon"), fDauNho = rs.getDouble("DauNho"), fDaiDay = rs.getDouble("DaiDay");
							
							String sDai = formatVN(format.format(fDai)), sRong = formatVN(format.format(fRong)), sDinhluong = formatVN(format.format(fDinhLuong)),
							sTrongluong =formatVN( format.format(fTrongluong)), sDuongKinhTrong = formatVN(format.format(fDuongKinhTrong)), sDoDay = formatVN(format.format(fDoDay)), sDauLon = formatVN(format.format(fDauLon))
							, sDauNho = formatVN(format.format(fDauNho)), sDaiDay = formatVN(format.format(fDaiDay));
							String mau ="";
			/*				if (!this.KenhBanHangId.equals("100000")) {
								 sDai =  format.format( fDai); sRong = format.format(fRong) ; sDinhluong =  format.format(fDinhLuong) ;
									sTrongluong =  format.format(fTrongluong) ; sDuongKinhTrong =  format.format(fDuongKinhTrong) ; 
									sDoDay = format.format(fDoDay) ; sDauLon = format.format(fDauLon) 
									; sDauNho =  format.format(fDauNho); sDaiDay =  format.format(fDaiDay) ;

							}*/
						 
						 	if(!dvkd_fk.trim().equals("100005") || chungloai_fk.trim().equals("100042")){
						 		//đối với sp mới không cần in màu
						 		mau = rs.getString("MAU").trim(); if(mau.toUpperCase().indexOf("NO") >= 0 || mau.toUpperCase().indexOf("KHONG") >= 0 || mau.toUpperCase().indexOf("KHÔNG") >= 0) mau = "";
						 	}
						 	 
							String mauin = rs.getString("MAUIN").trim(); if(mauin.toUpperCase().indexOf("NO") >= 0 || mauin.toUpperCase().indexOf("KHONG") >= 0 || mauin.toUpperCase().indexOf("KHÔNG") >= 0) mauin = "";
							
							//System.out.println("ErpHoaDon : mau = " + mau + ", mauin = " + mauin + ", dvdl_doday = " + rs.getString("DVDL_DODAY"));
							
						
							
							IErpHoaDon_SP sanpham = new ErpHoanDon_SP();
							sanpham.setDvkdId(dvkd_fk);
							sanpham.setId(this.Id);
							sanpham.setIdSanPham(rs.getString("SANPHAM_FK"));
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setTenSanPham(rs.getString("tensp"));
							sanpham.setTen1(rs.getString("TEN1"));
							sanpham.setTen2(rs.getString("TEN2"));
							sanpham.setTen3(rs.getString("TEN3"));
							sanpham.setQuycach(rs.getString("QUYCACH"));
							sanpham.setIn(rs.getString("inrahd"));
							
							sanpham.setDonViTinh(rs.getString("DONVI"));
							sanpham.setDonViTinhEng(rs.getString("DONVI_ENG"));
							sanpham.setDonViInAn(rs.getString("DVINAN"));
							sanpham.setSoluongchuan(rs.getString("SOLUONGCHUAN"));

							sanpham.setQuyDoi(rs.getInt("QUYDOI"));
							
							if(sanpham.getDonViTinh().toUpperCase().equals("M2"))
								sanpham.setSoLuong(Double.parseDouble(format2.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							else
								sanpham.setSoLuong(Double.parseDouble(format.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setDonGiaViet(rs.getDouble("dongiaViet"));
							sanpham.setCTKMID(rs.getString("scheme"));
							
							sanpham.setLoaisp(rs.getString("loaisanpham_fk"));  //Luu Loai San Pham
							String kichthuoc = sDai + rs.getString("DVDL_DAI")+"x"+sRong+rs.getString("DVDL_Rong");
							sanpham.setKichthuoc(kichthuoc);
							String dinhluong = sDinhluong + rs.getString("DVDL_DINHLUONG");
							sanpham.setDinhluong(dinhluong);
							String trongluong = sTrongluong + "kg";
							sanpham.setTrongluong(trongluong);
							//sanpham.setTenXuatHoaDon(rs.getString("tenXHD"));
							
							//Quy cách in hóa đơn
							boolean isDvkdLoi = dvkd_fk.equals("100004");
							boolean isCone = isDvkdLoi && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0|| sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0  ||  sanpham.getMaSanPham().trim().equals("OE"));
							String qc = "";
							String qcG = "";
							if(isDvkdLoi) {
								
								//Lõi: Đường kính trong x Dài x Độ Dày
								if(fDuongKinhTrong > 0) { qc += " " +  sDuongKinhTrong  + rs.getString("DVDL_DKTRONG"); }
								if(fDauLon > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDauLon  + rs.getString("DVDL_DAULON"); }
								
								if(fRong > 0) { qc += " " +  sRong + rs.getString("DVDL_Rong"); }
								if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI"); }
								if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong  + rs.getString("DVDL_DINHLUONG"); }							
								
								if(fDauNho > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauNho + rs.getString("DVDL_DAUNHO"); }
								if(fDoDay > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDoDay + rs.getString("DVDL_DODAY"); }
								
								if(kenhXuatKhau) {
									//qc += " (" +  sTrongluong + rs.getString("DVDL_TRONGLUONG") + ")";
									if(isCone) { qcG = qc; qc = ""; }
								} else {
									if(isCone) {
										qcG = qc; qc = "";
									}
									
									if(chungloai_fk.equals("100040")){
										
										if(mauin.length() > 0) {
											qc += " " + mauin; 
										}
										qc += " " + (mau.trim().length() >0?mau:"Không màu");
										
									}else if(chungloai_fk.equals("100030") || chungloai_fk.equals("100032")||chungloai_fk.equals("100033")){
										
										qc += " " + (mau.trim().length() >0?mau:"Không màu");
										
										if(mauin.length() > 0) {
											qc += " " + mauin; 
										}
										
										
									}else{
										
										if(mauin.length() > 0) {
											qc += " " + mauin; 
										}
										
									    if(!chungloai_fk.equals("100031")){
										  qc += " " + (mau.trim().length() >0?mau:"Không màu");
									    }
									}
								}
								//Cộng tổng lượng vào listsanphamCone phục vụ cho lúc in hóa đơn tài chính
								if(isCone) { capNhatSoluongChoListSanPhamCone(sanpham, qcG); }
								
							} else  if(dvkd_fk.equals("100000")) {
								//nhom
							    if(fRong > 0) { qc += " " +  sRong + rs.getString("DVDL_Rong"); }
							    if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI"); }
							    if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong  + rs.getString("DVDL_DINHLUONG"); }
							}else{
							 
										if(fDuongKinhTrong > 0) { 
								  		qc  = " " + sDuongKinhTrong + rs.getString("DVDL_DKTRONG"); }
								  
									    if(fDai > 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI");
									    }
									 
									    if(fRong> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sRong + rs.getString("DVDL_RONG");
									    }
									    
									    if(fDaiDay> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sDaiDay+ rs.getString("DVDL_daiday");
									    }
									    
									    if(mau.length()> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + mau ;
									    }
									    if(rs.getString("MAMETRO").length() >0){
									    	if(qc.length() > 0) { qc += " x"; } 
									    	qc += " (" + rs.getString("MAMETRO") +")" ;
									    }
								  //  if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDinhluong + rs.getString("DVDL_DINHLUONG"); }

							}
							System.out.println("[ErpHoaDon] qc = " + qc);
							
							sanpham.setQuyCachGroup(qcG);
						       
						    //Quy doi dvdl
						    dvdl1_fk = rs.getString("dvdl1_fk");
						    String dvdl1ten = rs.getString("DVINAN1").trim();
						    String dvdl1dg = rs.getString("DVDL1DG").trim();
						    double dvdl1sl = rs.getDouble("DVDL1SL");

						    dvdl2_fk = rs.getString("dvdl2_fk");
//						    String dvdl2ten = rs.getString("DVINAN2").trim();
//						    String dvdl2dg = rs.getString("DVDL2DG").trim();
						    double dvdl2sl = rs.getDouble("DVDL2SL");
						    
						    double soluong = rs.getDouble("soluong");
						    double quydoi = 0; 
						    String dvdltenquydoi = "";
						    String dvdldgquydoi = "";
						    //System.out.println("rsDonvi = '" + rs.getString("DONVI").trim() + "', dvdl1ten = " + dvdl1ten + ", dvdl2ten = " + dvdl2ten + ", dvdl1sl = " + dvdl1sl + ", dvdl2sl = " + dvdl2sl );
						    
						    // chỉ làm cho nhôm
						    if(dvkd_fk.equals("100000")){
						    
						    	if(rs.getString("DVDL_FK").trim().equals(rs.getString("dvdl_chuan"))) {
						    		// LẤY TRONG DATABASE RA 1 ĐƠN VỊ QUY ĐỔI DUY NHẤT,NẾU CÓ NHIỀU QUY ĐỔI
						    		query=" select soluong1,soluong2,dvdl.DONVI from quycach qc "+ 
						    			  "	inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=DVDL2_FK "+
						    			  " where sanpham_fk="+rs.getString("sanpham_fk");
						    		//System.out.println("Get Du Lieu : "+query);
						    		ResultSet rsqc=db.get(query);
						    		if (rsqc != null)
						    		{
							    		if(rsqc.next()){
							    			quydoi = soluong * rsqc.getDouble("soluong2")/rsqc.getDouble("soluong1");
							    			dvdltenquydoi=rsqc.getString("DONVI");
							    		}
							    		rsqc.close();
						    		}						    		
							    	/*quydoi = soluong / dvdl1sl * dvdl2sl;
							    	dvdltenquydoi = dvdl2ten;
							    	dvdldgquydoi = dvdl2dg;*/
							    	//System.out.println("quydoi1 = '" + quydoi );
							    } else {
							    	quydoi = soluong / dvdl2sl * dvdl1sl;
							    	dvdltenquydoi = dvdl1ten;
							    	dvdldgquydoi = dvdl1dg;
							    	//System.out.println("quydoi2 = '" + quydoi );
							    }
						    }
						     
//						    NumberFormat formatter = new DecimalFormat("#,###,###.###");
						    if (this.KenhBanHangId.equals("100000")) {
						    	if(!dvkd_fk.equals("100005")){
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + "}" + qc);
						    	}else{
						    		// Nếu DVKD: SP MỚI && KH THUỘC NHÓM KH METRO >> LẤY TEN1
						    		if(iskh_METRO <= 0 )
						    		   sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") +" " + qc + "} ");
						    		else 
						    			sanpham.setTenXuatHoaDon(rs.getString("TEN1") +" " + qc + "} ");				    		
						    		
						    	}
						    	if(dvdltenquydoi.length() >0){
						    		sanpham.setQuyDoiStr(formatVN(format.format(quydoi)) + " " + dvdltenquydoi);
						    	}
						    	System.out.println(sanpham.getTenXuatHoaDon());
						    	
						    	
						    } else {
						    	if(!dvkd_fk.equals("100005")){
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + "}" + qc);
						    	}else{
						    		
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1")  +" "+ qc + "}");
						    	}
						    	if(dvdldgquydoi.length() >0){
						    		sanpham.setQuyDoiStr(format.format(quydoi) + "" + dvdldgquydoi);
						    	}
						    }
						    
						    String ghichu = rs.getString("ghichu");
						    if(ghichu.trim().length() > 0)
						    {
						    	this.sanpham_ghichu.put(sanpham.getIdSanPham(), ghichu);
						    	//System.out.println("__GHI CHU: " + ghichu);
						    	if(ghichu.contains("__"))
						    	{
						    		this.sanpham_ghichu.put(sanpham.getIdSanPham(), ghichu);
						    		
						    		
						    	}
						    }
						  
							this.listsanpham.add(sanpham);
							
						}
						rs.close();
					}
					catch(Exception er)
					{
						er.printStackTrace();
						//System.out.println("[ErpHoaDon.constructor] SP Exception Message = " + er.getMessage());
					}
				}
			}
		}
		catch(Exception er)
		{
			System.out.println("[ErpHoaDon.constructor] 4.Exception: " + er.getMessage());
		}
		
		String sql = "select pk_seq, ma + ', ' + ten as ten from erp_tiente";
		this.tienteRs = db.get(sql);
		
		System.out.println("___TIEN TE: " + sql);
		
		/*		if(this.tienteId.trim().length() > 0)
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
		}*/
		
		if(this.NppId.trim().length() > 0 && this.NgayXuaHd.trim().length() > 0)
		{
			String query = "select pk_seq, mahopdong from ERP_HOPDONG " +
						   "where khachhang_fk = '" + this.NppId + "' and tungay <= '" + this.NgayXuaHd + "' and denngay >= '"  + this.NgayXuaHd + "' ";
			//System.out.println("Lay hop dong: " + query);
			this.hopdongRs = db.get(query);
		}
	}

	private void capNhatSoluongChoListSanPhamCone(IErpHoaDon_SP sanpham, String qcG) {
		String key = sanpham.getMaSanPham() + qcG;
		//Tìm để thêm vào sanpham.listsanphamCone
		IErpHoaDon_SP _spCone = null, _spTemp; 
		for(int _z = 0; _z < listsanphamCone.size(); _z++) {
			_spTemp = listsanphamCone.get(_z);
			if(_spTemp != null && _spTemp.getIdSanPham().equals(key) && sanpham.getDonGia() == _spTemp.getDonGia()) {
				_spCone = _spTemp;
				break;
			}
		}
		if(_spCone == null) {
			_spCone = new ErpHoanDon_SP();
			_spCone.setIdSanPham(key);
			_spCone.setDonGia(sanpham.getDonGia());
			_spCone.setSoLuong(0);
			listsanphamCone.add(_spCone);
		}
		_spCone.setSoLuong(_spCone.getSoLuong() + sanpham.getSoLuong());
	}

	public String getId() 
	{
		return this.Id;
	}

	public void setId(String id) 
	{
		this.Id = id;
	}

	public String getNgayxuathd()
	{
		return this.NgayXuaHd;
	}

	public void setNgayxuathd(String ngayxuathd)
	{
		this.NgayXuaHd = ngayxuathd;
	}

	public String getNppTen()
	{
		return this.NppTen;
	}

	public void setNppTen(String nppTen)
	{
		this.NppTen = nppTen;
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
		this.NguoiTao=nguoitao;	
	}

	public String getNgaysua() 
	{
		return this.NgaySua;
	}

	public void setNgaysua(String ngaysua) 
	{
		this.NgaySua=ngaysua;
	}

	public String getNguoisua() 
	{
		return this.NguoiSua;
	}

	public void setNguoisua(String nguoisua) 
	{
		this.NguoiSua=nguoisua;
	}

	public double getVAT()
	{
		return this.VAT;
	}

	public void setVAT(double vat) 
	{
		this.VAT=vat;
	}

	public String getMessage() 
	{
		return this.Msg;
	}

	public void setMessage(String msg) 
	{
		this.Msg=msg;
	}

	public void SetKyHieu(String kyhieu) 
	{
		this.Kyhieu=kyhieu;
	}

	public String getKyHieu()
	{
		return this.Kyhieu;
	}

	public void SetSoHoaDon(String sohoadon) 
	{
		this.SoHoaDon=sohoadon;
	}

	public String getSoHoaDon() 
	{
		return this.SoHoaDon;
	}

	public String gethinhthuctt() 
	{
		return this.HinhThucTT;
	}

	public void sethinhthuctt(String hinhthuctt) 
	{
		this.HinhThucTT=hinhthuctt;
	}

	public double getTongtienchuaCK() 
	{
		return 0;
	}

	public void setTongtienchuaCK(double ttcck)
	{
		
	}
	
	public ResultSet getListHoaDon() 
	{
		return this.listHoaDon;
	}

	public void setListHoaDon(String sql)
	{
		String query="";
		this.lichsuinId ="";
		if(!sql.equals(""))
		{
			query = sql;
			System.out.println("câu search nè: " + query);
		}
		else
		{
			query = "SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, \n"+
				 	"       NPP.TEN AS NPP, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA ,HD.HOANTAT , \n"+
				 	"       ISNULL(HD.TONGTIENAVAT,0) AS TONGTIENAVAT, \n"+
				 	"       ISNULL((SELECT B.PREFIX + cast(B.PK_SEQ as varchar(20)) + ', ' AS [text()] \n"+
				 	"               FROM ERP_HOADON_XUATKHO A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ \n"+
				 	"               WHERE A.HOADON_FK = HD.PK_SEQ AND B.TRANGTHAI not in (2,3) " +
				 	"               For XML PATH ('') ) \n"+
				 	"              , '') AS SOXUATKHO \n"+
				 	"FROM ERP_HOADON HD INNER JOIN ERP_KHACHHANG NPP ON NPP.PK_SEQ = HD.KhachHang_FK \n"+
					"     INNER JOIN NHANVIEN NT ON NT.PK_SEQ = HD.NGUOITAO \n"+
					"     INNER JOIN NHANVIEN NS ON NS.PK_SEQ = HD.NGUOISUA \n"+
					" WHERE LOAIHD <> 2 and HD.congty_fk = '" + this.congtyId + "' ";
								//"and npp.pk_seq  in "+util.quyen_npp(this.UserId) ;
			
		}
	
		String sql1 = createSplittingData_ListNew(this.db, 50, 10, " NGAYXUATHD DESC, cast(SOHOADON as numeric(18,0)) desc", query) ;

		ResultSet rs = db.get(sql1);
		List<IErpHoaDonList> hdList = new ArrayList<IErpHoaDonList>();
		if(rs!= null)
		{
			try
			{
				IErpHoaDonList hd = null;
				while(rs.next())
				{
					String id = rs.getString("PK_SEQ") ;
					String ngayxuathd = rs.getString("NGAYXUATHD");
					String trangthai = rs.getString("TRANGTHAI");
					String hoantat = rs.getString("HOANTAT");
					String soxuatkho = rs.getString("SOXUATKHO");
					String sohoadon = rs.getString("SOHOADON");
					String khachhang = rs.getString("NPP");
					String ngaytao = rs.getString("NGAYTAO");
					String ngaysua = rs.getString("NGAYSUA");
					String nguoitao = rs.getString("NGUOITAO");
					String nguoisua = rs.getString("NGUOISUA");
					String tongtienavat = rs.getString("TONGTIENAVAT");
					
					// Lấy lịch sử in của từng HD 
					query = " SELECT T.PK_SEQ, T.NGAYIN, NV.TEN AS NGUOIIN, T.SOLANIN," +
							"        CASE T.TRANGTHAIHD WHEN '0' THEN N'Chưa chốt' " +
							"                           WHEN '1' THEN N'Đã chốt' " +
							"                           WHEN '2' THEN N'Đã hủy' " +
							"                           ELSE  N'Hoàn tất' " +
							"        END AS TRANGTHAIHD " +
							" FROM LICHSUIN T INNER JOIN NHANVIEN NV ON T.NGUOIIN = NV.PK_SEQ " +
							" WHERE T.SOCHUNGTU = "+ rs.getString("PK_SEQ") +" ";
					
					ResultSet rsLayLS = db.get(query);
					List<IHdDetail> hdDetail = new ArrayList<IHdDetail>();
					if(rsLayLS!= null)
					{
						IHdDetail hdD = null;
						while(rsLayLS.next())
						{
							String pk_seq = rsLayLS.getString("PK_SEQ");
							String ngayinHd = rsLayLS.getString("NGAYIN");
							String nguoiin = rsLayLS.getString("NGUOIIN");
							String solanin = rsLayLS.getString("SOLANIN");
							String trangthaiHd = rsLayLS.getString("TRANGTHAIHD");
							System.out.println("Trang thai :"+trangthaiHd);
							
							hdD = new HdDetail(pk_seq, "", "",trangthaiHd, ngayinHd, solanin, nguoiin, "" );
							hdDetail.add(hdD);
							
						}
						rsLayLS.close();
					}
					
					
					hd = new ErpHoaDonList(id,soxuatkho, ngayxuathd, sohoadon, tongtienavat, "", khachhang, trangthai, hoantat,
							               ngaytao, ngaysua, nguoitao,nguoisua );
					hd.setHdDetailList(hdDetail);
					hdList.add(hd);
					
					
				}
				rs.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
		
		this.HoadonList = hdList;
	}

	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}

	public int getCurrentPage() 
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages)
	{
		this.listPages = listPages;
	}

	public int getLastPage() 
	{
		ResultSet rs = db.get("select count(*) as c from ERP_HOADON ");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) 
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public void setTungay(String _tungay) 
	{
		this.tungay=_tungay;
	}

	public String getTungay() 
	{
		 return this.tungay;
	}

	public void setDenNgay(String _denngay) 
	{
		this.denngay=_denngay;
	}

	public String getDenNgay()
	{
		return this.denngay;
	}

	public String getUserid()
	{
		return this.UserId;
	}

	public void setUserId(String user) 
	{
		this.UserId=user;
	}
	
	public String getDonDatHang() 
	{
		return this.DonDatHang;
	}
	
	public void setDonDatHang(String dondathang)
	{
		this.DonDatHang = dondathang;
	}
	
	public void CreateRs() 
	{
		String sql = "select pk_seq, ma + ', ' + ten as ten from erp_tiente";
		this.tienteRs = db.get(sql);
		
		
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
		
		if(this.NppId.trim().length() > 0 && this.NgayXuaHd.trim().length() > 0)
		{
			String query =  " select pk_seq, mahopdong from ERP_HOPDONG " +
							" where khachhang_fk = '" + this.NppId + "' order by pk_seq desc ";
									//"and tungay <= '" + this.NgayXuaHd + "' " +
									//"and denngay >= '"  + this.NgayXuaHd + "' ";
			//System.out.println("Lay hop dong: " + query);
			this.hopdongRs = db.get(query);
			
			query = "select kenhbanhang_fk,isnull(CHOPPHEPSUAHD,'0') as CHOPPHEPSUAHD from erp_khachhang where pk_seq= "+ this.NppId +" ";
			ResultSet rsKT = db.get(query) ;
			if(rsKT!= null)
			{
				try {
					while(rsKT.next())
					{
						this.KenhBanHangId= rsKT.getString("kenhbanhang_fk");
						this.ChoPhepSuaGiaHD=rsKT.getString("CHOPPHEPSUAHD");
					}
					rsKT.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		if(!this.NppId.equals("") && this.htXuat.trim().length() > 0 )
		{
			String query = "";
			
			if(this.htXuat.equals("0"))
			{
				query = //--Lấy ra những đơn đặt hàng chưa có hóa đơn
						" select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
						" from ERP_DonDatHang ddh "+
						" where khachhang_fk = " + this.NppId +
						" 	and ddh.trangthai IN (3,4,5)  " ;
				
					//	" 	and ddh.pk_seq not in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk in (  select pk_seq from Erp_HoaDon where trangthai != '2' )  ) " ;
				
					/*	" UNION " +
						//--Lấy ra những đơn đặt hàng có hóa đơn (tất cả đã chốt) và có sản phẩm chưa in (sản phẩm trong đơn đặt hàng nhưng ko có trong hóa đơn)
						" SELECT distinct DDH.PREFIX + CAST(DDH.PK_SEQ AS VARCHAR(10)) AS SOCT, DDH.PK_SEQ AS ID, DDH.NGAYDAT " +
						" FROM ERP_DONDATHANG DDH " +
						" inner join ERP_HOADON_DDH hdddh on hdddh.DDH_FK = DDH.PK_SEQ " +
						" WHERE DDH.KHACHHANG_FK = " + this.NppId + " AND DDH.TRANGTHAI >= 3 " +
						" 	and DDH.PK_SEQ not in ( " +
						" 		select DDH_FK from ERP_HOADON_DDH a inner join erp_HOADON b on a.HOADON_FK = b.PK_SEQ where b.TRANGTHAI = '0' " +
						" 	) " +
						"	and DDH.PK_SEQ in ( " +
						" 		select a.DONDATHANG_FK " +
						"		from ERP_DONDATHANG_SP a " +
						" 		left join ( " +
						//			Những sản phẩm đã có trong hóa đơn
						"			select distinct b.DDH_FK, d.SANPHAM_FK " +
						"			from ERP_HOADON_DDH b " +
						"			inner join ERP_HOADON_SP d on d.HOADON_FK = b.HOADON_FK " +
						" 		) z on z.DDH_FK = a.DONDATHANG_FK and a.SANPHAM_FK = z.SANPHAM_FK " +
						" 		where z.SANPHAM_FK is null " +
						" 	) ";*/
				if(this.Id.trim().length() > 0)
				{
					query += " union " +
							" select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
							" from ERP_DonDatHang ddh "+
							" where  ddh.pk_seq  in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk = '" + this.Id + "'  )  " ;
				}
				
				query = query + " order by  ddh.ngaydat desc "; 
				
			}
			else
			{
				query = //--Lấy ra những phiếu xuất kho chưa có hóa đơn
						"   select distinct a.prefix + cast(a.pk_seq as varchar(10)) as soCT, a.pk_seq as id, a.ngayxuat as ngaydat " +
						"   from ERP_XUATKHO a inner join ERP_DONDATHANG b on a.DONDATHANG_FK = b.PK_SEQ " +
						"   where a.TRANGTHAI not in ('2','3','5','0')  and b.KHACHHANG_FK = '" + this.NppId + "' " +
						"	and a.pk_seq not in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk in (  select pk_seq from Erp_HoaDon where trangthai not in ('2','1') )  ) " +
						"   and a.ngayxuat >= '2014-01-01' " ;
				 
				
				if(this.Id.trim().length() > 0)
				{
					query += " union " +
							" select distinct prefix + cast(pk_seq as varchar(10) ) as soCT, pk_seq as id, ngayxuat as ngaydat " +
							" from ERP_XUATKHO " +
							" where pk_seq in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk = '" + this.Id + "' )   ";
				}
				
				query += " order by ngayxuat desc "; 
				
			}
			
		System.out.println("Get Sql DDDH / PXK :" + query);
			this.rsddhChuaXuatHD = db.get(query);
	
		
			if(this.DonDatHang.trim().length() > 0)
			{
				if(this.htXuat.equals("0"))
				{
					query = " select isnull ( cast(sotienbvat as float), 0) as sotienbvat, isnull(cast(vat as float), 10) as vat, " +
							"     isnull(chietkhau, 0) as chietkhau, isnull(tienkhuyenmai, 0) as tienkhuyenmai,  "+
							"     isnull(cast (sotienavat as float), 0)  as tienavat ,   "+
							"    	isnull(sopo,'') as sopo,	  isnull(ghichu,'') as ghichu, isnull(noidungchietkhau,'') as noidungchietkhau, ddh.tiente_fk, tt.MA as matt, " +
							"     isnull(( select top(1) tigiaquydoi from ERP_TiGia where pk_seq = ddh.tiente_fk and trangthai = '1' ), 1) as quydoi," +
							"     isnull(ddh.kbh_fk, 0) as kbh_fk, ddh.hopdong_fk, " +
							"     isnull(ddh.hinhthuctt, '') as hinhthuctt, isnull(ddh.paymentterms, '') as paymentterms, isnull(ddh.deliveryterms, '') as deliveryterms, isnull(ddh.etd, '') as etd, isnull(ddh.remarks, '') as remarks, isnull(ddh.FreightCost, 0) as FreightCost, isnull(ddh.SoTienBaoHiem, 0) as SoTienBaoHiem " +
							" from ERP_DonDatHang ddh " +
							" left join ERP_TIENTE tt on ddh.TIENTE_FK = tt.PK_SEQ " +
							" where ddh.pk_seq in (" + this.DonDatHang + ") and khachhang_fk = " + this.NppId;
				}
				else
				{
					this.SoPo="";
					this.GhiChu="";
					query="select isnull(sopo,'') as sopo,isnull(ghichu,'') as ghichu  from erp_xuatkho where pk_seq in ("+this.DonDatHang+")";
					ResultSet rsgetpo=db.get(query);
					if (rsgetpo != null)
					{
						try{
							while (rsgetpo.next()){
								this.SoPo= (this.SoPo.length() >0?this.SoPo+",":"")+rsgetpo.getString("sopo");
								this.GhiChu= (this.GhiChu.length() >0?this.GhiChu+",":"")+rsgetpo.getString("GhiChu");
							}
							rsgetpo.close();
						}catch(Exception er){
							er.printStackTrace();
						}
					}					
					
					query = "   select SOPO,xuat.TIENTE_FK, tt.MA as matt, isnull(( select top(1) tigiaquydoi from ERP_TiGia where pk_seq = xuat.TIENTE_FK and trangthai = '1' ), 1) as quydoi,  " +
							"   SUM(	case when xk_sp.DVDL_FK = xuat.DVDL_FK then xk_sp.SOLUONG * xuat.DONGIA  " +
							"   else xk_sp.SOLUONG * ISNULL(qc.SOLUONG2, 1) / ISNULL(qc.SOLUONG1, 1) * xuat.DONGIA end ) as sotienbvat," +
							"   isnull(xuat.vat, 0) as vat, '' as noidungchietkhau, isnull(xuat.ghichu, '') as ghichu , isnull(xuat.chietkhau, '') as chietkhau , 0 as tienkhuyenmai , isnull(xuat.kbh_fk, 0) as kbh_fk, xuat.hopdong_fk," +
							"   isnull(xuat.hinhthuctt, '') as hinhthuctt, isnull(xuat.paymentterms, '') as paymentterms, isnull(xuat.deliveryterms, '') as deliveryterms, isnull(xuat.etd, '') as etd, isnull(xuat.remarks, '') as remarks, isnull(xuat.FreightCost, 0) as FreightCost, isnull(xuat.SoTienBaoHiem, 0) as SoTienBaoHiem " +
							"   from " +
							"   ( " +
							"   select xk.PK_SEQ as xkId, dh_sp.SANPHAM_FK, dh_sp.DONGIA, dh.TIENTE_FK, dh_sp.dongiaviet / dh_sp.DONGIA as quydoi, " +
							"   dh_sp.DVDL_FK, dh.VAT, isnull(xk.ghichu, '') as ghichu ,ISNULL(xk.SOPO,'') AS SOPO , isnull(dh.kbh_fk, 0) as kbh_fk, dh.hopdong_fk, " +
							"   isnull(dh.hinhthuctt, '') as hinhthuctt, isnull(dh.paymentterms, '') as paymentterms, isnull(dh.deliveryterms, '') as deliveryterms, isnull(dh.etd, '') as etd, isnull(dh.remarks, '') as remarks, isnull(dh.FreightCost, 0) as FreightCost, isnull(dh.SoTienBaoHiem, 0) as SoTienBaoHiem,  isnull(dh.chietkhau, 0) as chietkhau  " +
							"   from ERP_XUATKHO xk inner join ERP_DONDATHANG dh on xk.DONDATHANG_FK = dh.PK_SEQ  " +
							"   inner join ERP_DONDATHANG_SP dh_sp on dh.PK_SEQ = dh_sp.DONDATHANG_FK  " +
							"   where xk.PK_SEQ in ( " + this.DonDatHang + " ) " +
							"   )  " +
							"   xuat inner join ERP_XUATKHO_SANPHAM xk_sp on xuat.xkId = xk_sp.XUATKHO_FK and xuat.SANPHAM_FK = xk_sp.SANPHAM_FK  " +
							"   inner join ERP_SANPHAM sp on xk_sp.SANPHAM_FK = sp.PK_SEQ  " +
							"   left join QUYCACH qc on xk_sp.SANPHAM_FK = qc.SANPHAM_FK AND xuat.DVDL_FK = qc.DVDL2_FK  " +
							"   left join ERP_TIENTE tt on tt.PK_SEQ = xuat.TIENTE_FK " +
							"   where xk_sp.soluong > 0 " +
							"   group by xuat.sopo ,xuat.chietkhau, xuat.VAT, xuat.TIENTE_FK, tt.MA , xuat.ghichu, isnull(xuat.kbh_fk, 0), xuat.hinhthuctt, xuat.paymentterms, xuat.deliveryterms, xuat.etd, isnull(xuat.FreightCost, 0), isnull(xuat.SoTienBaoHiem, 0), xuat.remarks, xuat.hopdong_fk ";
					
				}
				
				System.out.println("1.Get Vat: " + query);
				
				ResultSet rs = db.get(query);
				if (rs != null)
				{
					try
					{
						if(	rs.next())
						{
							this.noidungCK = rs.getString("noidungchietkhau");
							
							this.tienteId = rs.getString("tiente_fk");
							this.tienteTen = rs.getString("matt");
							this.tygia = rs.getString("quydoi");
							if(this.htXuat.equals("0"))
							{
								//truong hơp chi 1 đơn đặt hàng
								this.SoPo=rs.getString("sopo");
								this.GhiChu = rs.getString("ghichu");
								
							}
							this.tienchuaVat = rs.getDouble("sotienbvat") * rs.getDouble("quydoi");
							this.chietKhau = rs.getDouble("chietkhau");
							this.tienKhuyenMai = rs.getDouble("tienkhuyenmai");
							
							this.SoTienBaoHiem = rs.getDouble("SoTienBaoHiem");
							this.freightCost = rs.getDouble("FreightCost");
							
							this.tienSauCK_Km = this.tienchuaVat - (this.tienChietKhau > 0 ? this.tienChietKhau : this.tienchuaVat*this.chietKhau/100) - this.tienKhuyenMai + this.SoTienBaoHiem + this.freightCost;
							this.VAT = rs.getDouble("vat");
							this.tienVAT = this.tienSauCK_Km * this.VAT / 100;
							this.TienSauVat = this.tienSauCK_Km + this.tienVAT;
							
							this.KenhBanHangId = rs.getString("kbh_fk");
							
							this.hopdongId = rs.getString("hopdong_fk");
							
							this.paymentTerms = rs.getString("PAYMENTTERMS");
							this.deliveryTerms = rs.getString("DELIVERYTERMS");
							this.etd = rs.getString("ETD");
							this.remarks = rs.getString("REMARKS");
							
							System.out.println("VAT = " + this.VAT);
						}
						rs.close();
					}
					catch(Exception er)
					{
						er.printStackTrace();
					}
				}
				
				boolean kenhXuatKhau = this.KenhBanHangId.equals("100001");
				
				//1 hoa don dat hang, xuat kho chi xuat hoa don 1 lan, qua so dong tu dong tach
				if(htXuat.equals("0"))
				{
						query = "   select a.sanpham_fk, a.mametro ,a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN, a.ghichusp, a.quycach, " +
								"	a.DAI, a.DVDL_DAI, a.RONG, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG, a.TRONGLUONG, a.DVDL_TRONGLUONG, a.DUONGKINHTRONG, a.DVDL_DKTRONG, a.DODAY, a.DVDL_DODAY, a.DAULON, a.DVDL_DAULON, a.DAUNHO, a.DVDL_DAUNHO, a.DAIDAY, a.DVDL_DAIDAY, a.MAUIN, a.MAU, " +
								"	a.tenXHD1, a.tenXHD2, a.DONVI_ENG, a.DVINAN,  " +
								"	a.dvkd_fk, a.nhanhang_fk, a.chungloai_fk, a.loaisanpham_fk, " +
								"	sum(a.soluong) as soluong, a.soluongchuan " +
								"	from (" +
								" 	  select   DDH_SP.SANPHAM_FK,SP.MA,ISNULL(SP.MAMETRO,'') AS MAMETRO,   " +
								" 	  SP.TEN + ' ' + case when substring(LTRIM(isnull(SP.QUYCACH,'')), 1, 1 ) = 'x' then STUFF(LTRIM(isnull(SP.QUYCACH,'')),1,1,'') else isnull(SP.QUYCACH,'') end  as tensp," +
								" 	  isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, DONGIA, isnull(scheme, '') as Scheme,  SOLUONG,  " +
								"     ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG, ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG, ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO, ISNULL(SP.DAIDAY, 0) AS DAIDAY, " +
								"     ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG, ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG, ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY, ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DVDL_DAUNHO, '') AS DVDL_DAUNHO, ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY, " +
								"     ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU, " +
								"     SP.ten1 as tenXHD1, SP.ten2 as tenXHD2, dvdl.DIENGIAIANH as DONVI_ENG, isnull(DVDL.TENINAN, ISNULL(DVDL.DONVI, '')) as DVINAN,   " +
								"     isnull(sp.dvkd_fk, 0) as dvkd_fk, isnull(sp.nhanhang_fk, 0) as nhanhang_fk, isnull(sp.chungloai_fk, 0) as chungloai_fk, isnull(sp.loaisanpham_fk, 0) as loaisanpham_fk, " +
								"     ISNULL(round(QUYCACH.SOLUONG1,0),'1') AS QUYDOI   ,ISNULL(DVDL.DONVI, '') AS DONVI, N'' as ghichusp, isnull(SP.QUYCACH, '') as QUYCACH, sp.DVDL_FK as dvdlCHUAN, DDH_SP.SOLUONG AS SOLUONGCHUAN     " +
								" 	  FROM ERP_DONDATHANG_SP DDH_SP  " +
								"     INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK    " +
								"     INNER JOIN ERP_DonDatHang DDH   ON DDH.PK_SEQ = DDH_SP.DONDATHANG_FK " +
								"     LEFT JOIN QUYCACH ON SP.PK_SEQ = QUYCACH.SANPHAM_FK  and DDH_SP.DVDL_FK = QUYCACH.DVDL2_FK   " +
								"     LEFT JOIN DONVIDOLUONG DVDL ON DDH_SP.DVDL_FK = DVDL.PK_SEQ     " +
								"     WHERE  DDH.KhachHang_FK = '" + this.NppId + "' AND  DDH_SP.SOLUONG > 0 AND DONDATHANG_FK IN (" + this.DonDatHang + ") " +
					/*			" 	and DDH_SP.SANPHAM_FK NOT IN (" +
								"		select distinct SANPHAM_FK from ERP_HOADON_DDH hdddh " +
								"		inner join ERP_HOADON hd on hd.pk_seq = hdddh.HOADON_FK " +
								"		inner join ERP_HOADON_SP hdsp on hd.PK_SEQ = hdsp.HOADON_FK " +
								"		where hd.TRANGTHAI != '2' and hdddh.ddh_fk = DDH_SP.DONDATHANG_FK " + 
										(this.Id != null && this.Id.trim().length() > 0 ? " AND hd.pk_seq != " + this.Id : " ") + 
								" 	) " +*/
							" ) a" +
							" group by  a.MAMETRO, a.sanpham_fk, a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN,  a.ghichusp, a.quycach, " +
							"			a.DAI, a.DVDL_DAI, a.RONG, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG, a.TRONGLUONG, a.DVDL_TRONGLUONG, a.DUONGKINHTRONG, a.DVDL_DKTRONG, a.DODAY, a.DVDL_DODAY, a.DAULON, a.DVDL_DAULON, a.DAUNHO, a.DVDL_DAUNHO, a.DAIDAY, a.DVDL_DAIDAY, a.MAUIN, a.MAU, " +
							"			a.tenXHD1, a.tenXHD2, a.DONVI_ENG, a.DVINAN,  " +
							"			a.dvkd_fk, a.nhanhang_fk, a.chungloai_fk, a.loaisanpham_fk, a.soluongchuan " +
							" order by a.MA, ISNULL(a.DUONGKINHTRONG,0), ISNULL(a.DVDL_DKTRONG,''), ISNULL(a.DAULON,0), ISNULL(a.DVDL_DAULON,''), ISNULL(a.DAI,0), ISNULL(a.DVDL_DAI,''), ISNULL(a.DAUNHO,0), ISNULL(a.DVDL_DAUNHO,''), ISNULL(a.DODAY,0), ISNULL(a.DVDL_DODAY,''), SCHEME ASC ";
					 					
				}
				else
				{


					query = " select  max(ngayxuat) as ngayxuat,max(xkId) as xuatkho_fk , a.sanpham_fk, a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN,  a.quycach, " +
							"	a.DAI, a.DVDL_DAI, a.RONG, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG,  a.TRONGLUONG, a.DVDL_TRONGLUONG, a.DUONGKINHTRONG, a.DVDL_DKTRONG, a.DODAY, a.DVDL_DODAY, a.DAULON, " +
							"   a.DVDL_DAULON, a.DAUNHO, a.DVDL_DAUNHO, a.DAIDAY, a.DVDL_DAIDAY, a.MAUIN, a.MAU,a.mametro , " +

							"	a.tenXHD1, a.tenXHD2, a.DONVI_ENG, a.DVINAN,  " +
							"	a.dvkd_fk, a.nhanhang_fk, a.chungloai_fk, a.loaisanpham_fk, " +
							"	sum(a.soluong) as soluong " +
							" from ( " +
								"     select  xuat.ngayxuat,xuat.xkId, xuat.sanpham_fk, sp.MA," +
								"     sp.TEN + ' ' + case when substring(LTRIM(isnull(SP.QUYCACH,'')), 1, 1 ) = 'x' then STUFF(LTRIM(isnull(SP.QUYCACH,'')),1,1,'') else isnull(SP.QUYCACH,'') end  as tensp, " +
								"     isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, xuat.DONGIA, " +
								"     ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG, ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG, ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO, ISNULL(SP.DAIDAY, 0) AS DAIDAY, " +
								"     ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG, ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG, ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY, ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DVDL_DAUNHO, '') AS DVDL_DAUNHO, ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY, " +
								"     ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU, " +
								"     SP.ten1 as tenXHD1, SP.ten2 as tenXHD2, dv.DIENGIAIANH as DONVI_ENG, isnull(DV.TENINAN, ISNULL(DV.DONVI, '')) as DVINAN,   " +
								"     isnull(sp.dvkd_fk, 0) as dvkd_fk, isnull(sp.nhanhang_fk, 0) as nhanhang_fk, isnull(sp.chungloai_fk, 0) as chungloai_fk, isnull(sp.loaisanpham_fk, 0) as loaisanpham_fk, " +
								"	  case when xk_sp.DVDL_FK = xuat.DVDL_FK then xk_sp.SOLUONG  " +
								"	  else xk_sp.SOLUONG * ISNULL(qc.SOLUONG2, 1) / ISNULL(qc.SOLUONG1, 1) end as SOLUONG, " +

								"	  ISNULL(dv.DonVi, '') as DonVi, '' as scheme, '1' as quydoi, N'' as ghichusp, isnull(SP.QUYCACH, '') as QUYCACH, sp.DVDL_FK as dvdlCHUAN , ISNULL(mametro,'') AS mametro   " +


								" 	from " +
								" 	( " +
								" 	select distinct    xk.PK_SEQ as xkId, dh_sp.SANPHAM_FK, dh_sp.DONGIA, dh.TIENTE_FK, " +
								"   case when dh_sp.DONGIA >0 then dh_sp.dongiaviet / dh_sp.DONGIA else 0 end  as quydoi , dh_sp.DVDL_FK,  xk.NGAYXUAT     " +
								" 	from ERP_XUATKHO xk inner join ERP_DONDATHANG dh on xk.DONDATHANG_FK = dh.PK_SEQ   " +
								" 	inner join ERP_DONDATHANG_SP dh_sp on dh.PK_SEQ = dh_sp.DONDATHANG_FK  " +
								" 	where xk.PK_SEQ in ( " + this.DonDatHang + " )  " +
								" 	)  " +
								" 	xuat inner join ERP_XUATKHO_SANPHAM xk_sp on xuat.xkId = xk_sp.XUATKHO_FK and xuat.SANPHAM_FK = xk_sp.SANPHAM_FK  " +
								" 	inner join ERP_SANPHAM sp on xk_sp.SANPHAM_FK = sp.PK_SEQ " +
								" 	left join QUYCACH qc on xk_sp.SANPHAM_FK = qc.SANPHAM_FK and xuat.DVDL_FK = qc.DVDL2_FK  " +
								" 	inner join DONVIDOLUONG dv on xuat.DVDL_FK = dv.PK_SEQ " +
								" 	WHERE xuat.SANPHAM_FK NOT IN (" +
								"	select distinct SANPHAM_FK from ERP_HOADON_XUATKHO hdddh " +
								"	inner join ERP_HOADON hd on hd.pk_seq = hdddh.HOADON_FK " +
								"	inner join ERP_HOADON_SP hdsp on hd.PK_SEQ = hdsp.HOADON_FK " +
								"	where hd.TRANGTHAI != '2' and isnull(hdsp.inrahd, '1') = 1  AND HDDDH.XUATKHO_FK= XK_SP.XUATKHO_FK " + 
									(this.Id != null && this.Id.trim().length() > 0 ? " AND hd.pk_seq != " + this.Id : " ") + 
								" )" +
							" ) a where a.soluong>0 " +
							" group by  a.mametro,a.sanpham_fk, a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN,  a.ghichusp, a.quycach, " +
							"			a.DAI, a.DVDL_DAI, a.RONG, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG, a.TRONGLUONG, a.DVDL_TRONGLUONG, a.DUONGKINHTRONG, a.DVDL_DKTRONG, a.DODAY, a.DVDL_DODAY, a.DAULON, a.DVDL_DAULON, a.DAUNHO, a.DVDL_DAUNHO, a.DAIDAY, a.DVDL_DAIDAY, a.MAUIN, a.MAU, " +
							"			a.tenXHD1, a.tenXHD2, a.DONVI_ENG, a.DVINAN,  " +


							"			a.dvkd_fk, a.nhanhang_fk, a.chungloai_fk, a.loaisanpham_fk " +
							" order by ngayxuat desc,xuatkho_fk desc  , a.MA, ISNULL(a.DUONGKINHTRONG,0), ISNULL(a.DVDL_DKTRONG,''), ISNULL(a.DAULON,0), ISNULL(a.DVDL_DAULON,''), ISNULL(a.DAI,0), ISNULL(a.DVDL_DAI,''), ISNULL(a.DAUNHO,0), ISNULL(a.DVDL_DAUNHO,''), ISNULL(a.DODAY,0), ISNULL(a.DVDL_DODAY,''), SCHEME ASC ";

				}
				
			 	System.out.println("12345 .Get San Pham: " + query);
				
				rs = this.db.get(query);
				this.listsanpham.clear();


			    String dvkd_fk, nhanhang_fk, chungloai_fk, loaisanpham_fk, dvdl_fk, dvdl1_fk, dvdl2_fk;
				if(rs != null)
				{
					try
					{
						NumberFormat format = new DecimalFormat("#,###,###.###");
						NumberFormat format2 = new DecimalFormat("#,###,###");
						
						NumberFormat format_KG = new DecimalFormat("#,###,###.##");
						while(rs.next())
						{
							double fDai = rs.getFloat("Dai"), fRong = rs.getFloat("RONG"), fDinhLuong = rs.getFloat("DinhLuong"), fTrongluong = rs.getFloat("TrongLuong"), fDuongKinhTrong = rs.getFloat("DuongKinhTrong"), fDoDay = rs.getFloat("DoDay"), fDauLon = rs.getFloat("DauLon"), fDauNho = rs.getFloat("DauNho"), fDaiDay = rs.getFloat("DaiDay");
							String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong), sDuongKinhTrong = format.format(fDuongKinhTrong), sDoDay = format.format(fDoDay), sDauLon = format.format(fDauLon), sDauNho = format.format(fDauNho), sDaiDay = format.format(fDaiDay);
							String mau = rs.getString("MAU").trim(); if(mau.toUpperCase().indexOf("NO") >= 0 || mau.toUpperCase().indexOf("KHONG") >= 0 || mau.toUpperCase().indexOf("KHÔNG") >= 0) mau = "";
							String mauin = rs.getString("MAUIN").trim(); if(mauin.toUpperCase().indexOf("NO") >= 0 || mauin.toUpperCase().indexOf("KHONG") >= 0 || mauin.toUpperCase().indexOf("KHÔNG") >= 0) mauin = "";
							
							dvkd_fk = rs.getString("dvkd_fk");
							nhanhang_fk = rs.getString("nhanhang_fk");
							chungloai_fk = rs.getString("chungloai_fk");
							loaisanpham_fk = rs.getString("loaisanpham_fk");
							
							IErpHoaDon_SP sanpham= new ErpHoanDon_SP();
							sanpham.setDvkdId(dvkd_fk);
							sanpham.setId(this.Id);
							sanpham.setIdSanPham(rs.getString("SANPHAM_FK"));
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setTenSanPham(rs.getString("tensp"));
							sanpham.setTen1(rs.getString("TEN1"));
							sanpham.setTen2(rs.getString("TEN2"));
							sanpham.setTen3(rs.getString("TEN3"));
							sanpham.setQuycach(rs.getString("QUYCACH"));
							sanpham.setDonGia(rs.getDouble("dongia"));
							
							sanpham.setDonViTinh(rs.getString("DONVI"));
							sanpham.setDonViTinhEng(rs.getString("DONVI_ENG"));
							sanpham.setDonViInAn(rs.getString("DVINAN"));
							try{
							sanpham.setSoluongchuan(rs.getString("soluongchuan"));
							}catch(Exception er){
								
							}
							
							sanpham.setCTKMID(rs.getString("SCHEME"));
							sanpham.setQuyDoi(rs.getInt("QUYDOI"));
							
							sanpham.setLoaisp(rs.getString("loaisanpham_fk"));  //Luu Loai San Pham
							
							String ghichusp="";
							if(htXuat.equals("0"))
							{
								ghichusp=rs.getString("ghichusp");
							}else{
								query="select  ghichu  from ERP_XUATKHO_SANPHAM where sanpham_fk="+rs.getString("SANPHAM_FK")+" and xuatkho_fk in ( " + this.DonDatHang + " )   and isnull(ghichu,'') <> '' ";
								ResultSet rsghchu=db.get(query);
								if (rsghchu != null)
								{
									while (rsghchu.next()){
										if(ghichusp.length()>0){
										ghichusp=ghichusp + "," +rsghchu.getString("ghichu") ;
										}else{
											ghichusp= rsghchu.getString("ghichu") ;
										}
									}
									rsghchu.close();
								}
							}
							sanpham.setGhiChu1(ghichusp);
							//System.out.println("__Gia tri GOC: " + rs.getDouble("soluong") + "  -- Thuc Te: " + Double.parseDouble( fomart.format(rs.getDouble("soluong")).replaceAll(",", "")) );
							
							//sanpham.setSoLuong( Double.parseDouble( fomart.format(rs.getDouble("soluong")).replaceAll(",", "")) ) ;
							if( sanpham.getDonViTinh().toUpperCase().equals("M2") )
								sanpham.setSoLuong(Double.parseDouble(format2.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							else if( sanpham.getDonViTinh().toUpperCase().equals("KG")){
								sanpham.setSoLuong(Double.parseDouble(format_KG.format(rs.getFloat("soluong")).replaceAll(",", ""))) ;
							}else{
								sanpham.setSoLuong(Double.parseDouble(format.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							}
							 
							
							//Quy cách in hóa đơn
							boolean isDvkdLoi = dvkd_fk.equals("100004");
							boolean isCone = isDvkdLoi && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 ||  sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0  ||  sanpham.getMaSanPham().trim().equals("OE"));
							String qc = "";
							String qcG = "";
							if(isDvkdLoi) {
								
								//Lõi: Đường kính trong x Dài x Độ Dày
								if(fDuongKinhTrong > 0) { qc += " " + sDuongKinhTrong + rs.getString("DVDL_DKTRONG"); }
								if(fDauLon > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauLon + rs.getString("DVDL_DAULON"); }
								if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai + rs.getString("DVDL_DAI"); }
								if(fDauNho > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauNho + rs.getString("DVDL_DAUNHO"); }
								if(fDoDay > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDoDay + rs.getString("DVDL_DODAY"); }
								
								if(kenhXuatKhau) {
									qc += " (" + sTrongluong + rs.getString("DVDL_TRONGLUONG") + ")";
									if(isCone) { qcG = qc; qc = ""; }
								} else {
									if(isCone) { qcG = qc; qc = ""; }
									if(mauin.length() > 0) { qc += " " + mauin; }
									if(mau.length() > 0) { qc += " " + mau; }
								}
								//Cộng tổng lượng vào listsanphamCone phục vụ cho lúc in hóa đơn tài chính
								if(isCone) { capNhatSoluongChoListSanPhamCone(sanpham, qcG); }
								
							} else {
							    if(fRong > 0) { qc += " " + sRong + rs.getString("DVDL_Rong"); }
							    if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai + rs.getString("DVDL_DAI"); }
							    if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDinhluong + rs.getString("DVDL_DINHLUONG"); }
							}
							
							sanpham.setQuyCachGroup(qcG);
							
							if (this.KenhBanHangId.equals("100000")) {
								sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + "}" + qc);
						    } else {
						    	sanpham.setTenXuatHoaDon(rs.getString("tenXHD2") + "}" + qc);
						    }
							System.out.println("[ErpHoaDon.createRs] tenxuathoadon = " + sanpham.getTenXuatHoaDon());
							
							/*double fDai = rs.getFloat("Dai"), fRong = rs.getFloat("RONG"), fDinhLuong = rs.getFloat("DinhLuong"), fTrongluong = rs.getFloat("TrongLuong");
							String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong);
							//Quy cach
							String qc = "";
						    if(fRong > 0 ) {
						        qc += sRong + rs.getString("DVDL_Rong");
						    }
						    if(fDai > 0 ) {
						    	if(qc.length() > 0) { qc += " x "; }
						    	qc += sDai + rs.getString("DVDL_DAI");
						    }
						    if(fDinhLuong > 0 ) {
						        if(qc.length() > 0) { qc += " x "; }
						        qc += sDinhluong + rs.getString("DVDL_DINHLUONG");
						    }
						    sanpham.setTenXuatHoaDon(this.KenhBanHangId.equals("100000") ? sanpham.getTen2() + "}" + qc : sanpham.getTen3() + "}" + qc);*/
							
							this.listsanpham.add(sanpham);
							
						}
						rs.close();
					}
					catch(Exception er)
					{
						er.printStackTrace();
						//System.out.println("Loi khoi tao SP : " + er.getMessage());
					}
				}
			}
		}
	}
	
	public ResultSet getrsddhChuaXuathd() {
		
		return this.rsddhChuaXuatHD;
	}
	
	public void SetNppId(String nppid) {
		
		this.NppId=nppid;
	}
	
	public String getNppId() {
		
		return this.NppId;
	}
	
	public List<IErpHoaDon_SP> GetListSanPham() {
		
		return this.listsanpham;
	}
	
	public List<IErpHoaDon_SP> GetListSanPhamCone() {
		
		return this.listsanphamCone;
	}
	
	public void SetNguoiMuaHang(String nguoimuahang) {
		
		this.NguoiMuaHang=nguoimuahang;
	}
	
	public String getNguoiMuaHang() {
		
		return this.NguoiMuaHang;
	}
	
	public boolean Save() 
	{			
				String sql = "", newId = "";	
				try
				{
					
					if(!(ktSoHoadon())){
						return false;
					}
				
					if(this.listsanpham == null)
					{
						this.setMessage("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
						return false;
					}
					
					if(this.listsanpham.size()==0)
					{
						this.setMessage("Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
						return false;
					}
					
					if(this.NguoiTao == null || this.NguoiTao.equals(""))
					{
						this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
						return false;
					}
					
					if(this.NppId==null || this.NppId.equals(""))
					{
						this.Msg = "Khách hàng chưa được chọn,vui lòng kiểm tra lại thông tin khách hàng";
					}
					
					 	//CreateRs();	 
				 	if(this.TienSauVat < 0 )
				 	{
				 		this.Msg = "Vui lòng kiểm tra lại, tổng giá trị của hóa đơn không được nhỏ hơn 0";
				 		return false;
				 	}
				 	
				 	if(this.listsanpham.size()<= 0)
				 	{
				 		this.Msg="Không thể tạo hóa đơn này, không có sản phẩm này được chọn trong hóa đơn";
				 		return false;
				 	}
				 	 
				 	try{
				 		Double.parseDouble(this.congtyId);
				 	}catch(Exception er){
				 		this.Msg="Vui lòng đăng nhập lại,vì lý do bảo mật,lỗi không thể lấy được thông tin công ty của user đang đăng nhập";
				 		return false;
				 	}
				 	
					db.getConnection().setAutoCommit(false);
		//		 	String hopdongId = this.hopdongId.trim() ? "null" : this.hopdongId.trim();
				 	sql = "INSERT INTO ERP_HOADON (SOPO,KM_GHICHU, NGAYXUATHD, TRANGTHAI, NGAYTAO, NGAYSUA, KYHIEU, SOHOADON, HINHTHUCTT, NGUOITAO, NGUOISUA, KHACHHANG_FK, NGUOIMUA, PO_MT, chietkhau, tienkhuyenmai, tongtienbvat, vat, tongtienavat, ghichu, noidungchietkhau, congty_fk, tiente_fk, xuattheo, ngayghinhanCN, diengiai, hopdong_fk, TIENCHIETKHAU,TYGIA, TIENBAOHIEM, TIENVANCHUYEN, INVOICE ) "+
					 	  " VALUES (N'"+this.SoPo+"',N'" + this.KM_ghichu + "', '" + this.NgayXuaHd + "', '0', '" + this.NgayTao + "', '" + this.NgaySua + "', '" + this.Kyhieu + "', '" + this.SoHoaDon + "', N'" + this.HinhThucTT + "', '" + this.NguoiTao + "', " +
					 	  		"'" + this.NguoiSua + "', '" + this.NppId + "', N'" + this.NguoiMuaHang + "', '" + this.POMT + "', " + this.chietKhau + ", " + this.tienKhuyenMai + ", " + this.tienchuaVat + ", " + this.tienVAT + ", " + this.TienSauVat + ", " +
					 	  		"N'" + this.GhiChu + "', N'" + this.noidungCK + "', " + this.congtyId + ", '" + this.tienteId + "', '" + this.htXuat + "', '" + this.ngayghinhanCN + "', N'" + this.diengiai + "', " + this.hopdongId + ", " + this.tienChietKhau + ", " + this.tygia + ", " + this.SoTienBaoHiem + ", " + this.freightCost + ", N'"+this.invoice+"' )";
				 
				 	System.out.println("1.Insert: " + sql);
				
					if(!db.update(sql))
					{
						db.update("rollback");
						this.Msg="Khong The Cap Nhat Hoa Don, Loi Tren Dong Lenh Sau :" + sql;
						return false;
					}
				 
					String query = "select SCOPE_IDENTITY() as dhId";
					ResultSet rsDh = db.get(query);	
					if (rsDh != null)
					{
						if (rsDh.next())
							newId = rsDh.getString("dhId");
					    rsDh.close();
					}
					
					if(this.htXuat.equals("0"))
					{
						int i=0;
						if(this.DonDatHang.trim().length() > 0 )
						{
							sql = "insert into ERP_HOADON_DDH (HOADON_FK, DDH_FK) select '" + newId + "', pk_seq from ERP_DONDATHANG where pk_seq in (" + this.DonDatHang + ") ";
							System.out.println("2.chen don dat hang: " + sql);
							if(!db.update(sql))
							{
								db.update("rollback");
								this.Msg = "Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
								return false;
							}	
							i++;
						}
					}
					else
					{
						int i=0;
						if(this.DonDatHang.trim().length() > 0 )
						{
							sql =   " insert into ERP_HOADON_XUATKHO (HOADON_FK, XUATKHO_FK) " +
									" select '" + newId + "', pk_seq from erp_xuatkho where pk_seq in (" + this.DonDatHang + ") ";
							System.out.println("2.chen xuat kho: " + sql);
							if(db.updateReturnInt(sql)<1)
							{
								db.update("rollback");
								this.Msg = "Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
								return false;
							}	
							i++;
							
							sql=" update ERP_XUATKHO set TRANGTHAI=4 where PK_SEQ in  (" + this.DonDatHang + ")  ";
							if(!db.update(sql))
							{
								db.update("rollback");
								this.Msg = "Không thể cập nhật phiếu xuất kho :" + sql;
								return false;
							}	
							
						}
					}
			
					System.out.println("VAT = " + this.VAT);
					int count = 0;IErpHoaDon_SP sanpham;
					while(count < this.listsanpham.size())
					{
						sanpham = listsanpham.get(count);
						
						if(sanpham.getSoLuong() > 0) {
						
							sanpham.setId(newId);
							double thanhtien = sanpham.getSoLuong() * sanpham.getDonGia();
							double thanhtiensauck = thanhtien + thanhtien * sanpham.getChietKhau() / 100;
							double tienVAT = thanhtiensauck * this.VAT/100;
							
							String sp_ghichu = this.sanpham_ghichu.get(sanpham.getIdSanPham());
							if(sp_ghichu == null)
								sp_ghichu = "";
							
							if(sanpham.getCTKMId().trim().length() <= 0)
							{
								
								if(this.sanpham_gia.containsKey(sanpham.getIdSanPham())){
									sanpham.setDonGia(this.sanpham_gia.get(sanpham.getIdSanPham()));
								}
								
								if(this.htb_soluongmoi.containsKey(sanpham.getIdSanPham())){
									sanpham.setSoLuong(this.htb_soluongmoi.get(sanpham.getIdSanPham()));
								}
								
								
								if(this.checkgiaVuot20Phantram( sanpham.getIdSanPham(),sanpham.getDonGia() )){
									 
									this.Msg = "Không thể sửa giá vượt quá 20% trong đơn đặt hàng của  mã sản phẩm : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
									db.update("rollback");
									return false;
								}
								
								sql = " insert into erp_hoadon_sp (SANPHAM_FK, HOADON_FK, SOLUONG, DONGIA, DONGIAVIET, CHIETKHAU, TIENBVAT, VAT, TIENAVAT, DONVITINH, SCHEME, ghichu, INRAHD, DVDL_FK) " +
									  " select " + sanpham.getIdSanPham() + ", " + sanpham.getId() + ", " + sanpham.getSoLuong() + ", " + sanpham.getDonGia() + ", " + sanpham.getDonGia() + " * " + this.tygia + ", " +
									  " " + sanpham.getChietKhau() + ", " + thanhtien + ", " + (tienVAT ) + ", " + (thanhtiensauck + tienVAT ) + ", N'" + sanpham.getDonViTinh()+ "', N'" + sanpham.getCTKMId() + "', N'" + sp_ghichu + "', '"+sanpham.getIn() + "', pk_seq from DONVIDOLUONG where DONVI = N'" + sanpham.getDonViTinh() + "' ";
								
								
							}
							else
							{
								sql = "insert into erp_hoadon_spkm (SANPHAM_FK, HOADON_FK, SOLUONG, DONGIA, DONGIAVIET, CHIETKHAU, TIENBVAT, VAT, TIENAVAT, DONVITINH, SCHEME, ghichu, INRAHD, DVDL_FK) " +
								  	  " select " + sanpham.getIdSanPham() + ", " + sanpham.getId() + ", " + sanpham.getSoLuong() + ", " + sanpham.getDonGia() + ", " + sanpham.getDonGia() + " * " + this.tygia + ", " +
								  			"" + sanpham.getChietKhau() + ", " + thanhtien + ", " + (tienVAT ) + ", " + (thanhtiensauck + tienVAT ) + ", N'" + sanpham.getDonViTinh()+ "', N'" + sanpham.getCTKMId() + "', N'" + sp_ghichu + "', '"+sanpham.getIn() + "', pk_seq from DONVIDOLUONG where DONVI = N'" + sanpham.getDonViTinh() + "' ";
							}
				
							System.out.println("3.Insert hoa don - san pham: " + sql);
							
							if(!db.update(sql))
							{
								System.out.println("Kho :"+sql);
								this.Msg = "Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
								db.update("rollback");
								return false;
							}
						}
						
						count++;
					}
					
					
		
					db.getConnection().commit();
					db.getConnection().setAutoCommit(true);
				}
				catch(Exception er)
				{
					db.update("rollback");
					this.setMessage( er.toString() + " Error : " +  sql);
					return false;
				}
	
		return true;
		
		
		
	}
	
	private boolean checkgiaVuot20Phantram(String idSanPham, double donGia) {
		// TODO Auto-generated method stub
		try{
			String query="";
			if(htXuat.equals("0"))
			{
				 					query = " select a.sanpham_fk, a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN, a.ghichusp, a.quycach, a.DAI, a.RONG, a.DVDL_DAI, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG, a.TRONGLUONG, sum(a.soluong) as soluong " +
						"from (" +
							" select DDH_SP.SANPHAM_FK,SP.MA,SP.TEN + ' ' + SP.QUYCACH as tensp, isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, DONGIA, isnull(scheme, '') as Scheme,  SOLUONG,  " +
							"	SP.DAI,SP.RONG,SP.DVDL_DAI,SP.DVDL_RONG,SP.DINHLUONG,SP.DVDL_DINHLUONG,SP.TRONGLUONG, " +
							"   ISNULL(round(QUYCACH.SOLUONG1,0),'1') AS QUYDOI   ,ISNULL(DVDL.DONVI, '') AS DONVI, N'' as ghichusp, isnull(SP.QUYCACH, '') as QUYCACH, sp.DVDL_FK as dvdlCHUAN   " +
							" FROM ERP_DONDATHANG_SP DDH_SP  " +
							" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = DDH_SP.SANPHAM_FK    " +
							" INNER JOIN ERP_DonDatHang DDH   ON DDH.PK_SEQ = DDH_SP.DONDATHANG_FK " +
							" LEFT JOIN QUYCACH ON SP.PK_SEQ = QUYCACH.SANPHAM_FK  and DDH_SP.DVDL_FK = QUYCACH.DVDL2_FK   " +
							" LEFT JOIN DONVIDOLUONG DVDL ON DDH_SP.DVDL_FK = DVDL.PK_SEQ     " +
							" WHERE  DDH.KhachHang_FK = '" + this.NppId + "' AND  DDH_SP.SOLUONG > 0 AND DONDATHANG_FK IN (" + this.DonDatHang + ") " +
							" 	and DDH_SP.SANPHAM_FK NOT IN (" +
							"		select distinct SANPHAM_FK from ERP_HOADON_DDH hdddh " +
							"		inner join ERP_HOADON hd on hd.pk_seq = hdddh.HOADON_FK " +
							"		inner join ERP_HOADON_SP hdsp on hd.PK_SEQ = hdsp.HOADON_FK " +
							"		where hd.TRANGTHAI != '2' and hdddh.ddh_fk = DDH_SP.DONDATHANG_FK " + 
									(this.Id != null && this.Id.trim().length() > 0 ? " AND hd.pk_seq != " + this.Id : " ") + 
							" 	) " +
						" ) a where a.sanpham_fk="+idSanPham+" " +
						" group by a.sanpham_fk, a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN,  a.ghichusp, a.quycach, a.DAI, a.RONG, a.DVDL_DAI, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG, a.TRONGLUONG ";
			}
			else
			{
				query = " select a.sanpham_fk, a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN,  a.quycach, a.DAI, a.RONG, a.DVDL_DAI, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG, a.TRONGLUONG, sum(a.soluong) as soluong " +
						"from ( " +
							" select xuat.sanpham_fk, sp.MA, sp.TEN + ' ' + sp.QUYCACH as tensp, isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, xuat.DONGIA, " +
							" 	SP.DAI,SP.RONG,SP.DVDL_DAI,SP.DVDL_RONG,SP.DINHLUONG,SP.DVDL_DINHLUONG,SP.TRONGLUONG,  " +
							"	case when xk_sp.DVDL_FK = xuat.DVDL_FK then xk_sp.SOLUONG  " +
							"	else xk_sp.SOLUONG * ISNULL(qc.SOLUONG2, 1) / ISNULL(qc.SOLUONG1, 1) end as SOLUONG, " +
							"	ISNULL(dv.DonVi, '') as DonVi, '' as scheme, '1' as quydoi, isnull(SP.QUYCACH, '') as QUYCACH, sp.DVDL_FK as dvdlCHUAN  " +
							" 	from " +
							" 	( " +
							" 	select distinct xk.PK_SEQ as xkId, dh_sp.SANPHAM_FK, dh_sp.DONGIA, dh.TIENTE_FK, " +
							"   case when dh_sp.DONGIA >0 then dh_sp.dongiaviet / dh_sp.DONGIA else 0 end  as quydoi , dh_sp.DVDL_FK   " +
							" 	from ERP_XUATKHO xk inner join ERP_DONDATHANG dh on xk.DONDATHANG_FK = dh.PK_SEQ   " +
							" 	inner join ERP_DONDATHANG_SP dh_sp on dh.PK_SEQ = dh_sp.DONDATHANG_FK  " +
							" 	where xk.PK_SEQ in ( " + this.DonDatHang + " )  " +
							" 	)  " +
							" 	xuat inner join ERP_XUATKHO_SANPHAM xk_sp on xuat.xkId = xk_sp.XUATKHO_FK and xuat.SANPHAM_FK = xk_sp.SANPHAM_FK  " +
							" 	inner join ERP_SANPHAM sp on xk_sp.SANPHAM_FK = sp.PK_SEQ " +
							" 	left join QUYCACH qc on xk_sp.SANPHAM_FK = qc.SANPHAM_FK and xuat.DVDL_FK = qc.DVDL2_FK  " +
							" 	inner join DONVIDOLUONG dv on xuat.DVDL_FK = dv.PK_SEQ " +
							" 	WHERE xuat.SANPHAM_FK NOT IN (" +
							"	select distinct SANPHAM_FK from ERP_HOADON_XUATKHO hdddh " +
							"	inner join ERP_HOADON hd on hd.pk_seq = hdddh.HOADON_FK " +
							"	inner join ERP_HOADON_SP hdsp on hd.PK_SEQ = hdsp.HOADON_FK " +
							"	where hd.TRANGTHAI != '2' and isnull(hdsp.inrahd, '1') = 1  AND HDDDH.XUATKHO_FK= XK_SP.XUATKHO_FK " + 
								(this.Id != null && this.Id.trim().length() > 0 ? " AND hd.pk_seq != " + this.Id : " ") + 
							" )" +
						" ) a where a.soluong>0 and a.sanpham_fk="+idSanPham+" " +
						" group by a.sanpham_fk, a.ma, a.tensp, a.ten1, a.ten2, a.ten3, a.dongia, a.scheme, a.quydoi, a.donvi, dvdlCHUAN, a.quycach, a.DAI, a.RONG, a.DVDL_DAI, a.DVDL_RONG, a.DINHLUONG, a.DVDL_DINHLUONG, a.TRONGLUONG ";
			}
			
			System.out.println("12345 .Get San Pham: " + query);
			
			ResultSet rs=db.get(query);
			double dongiacu=0;
			if (rs != null)
			{
				if(rs.next()){
					dongiacu=rs.getDouble("dongia");
				}
				rs.close();
			}
			
			if(donGia > (dongiacu*1.2 ))
			{
				return true;
			}
			
		}catch(Exception er){
			er.printStackTrace();
			return true;
		}
		return false;
	}
		

	public boolean Edit() 
	{
		String sql="";
		try
		{
			if(!(ktSoHoadon())){
				return false;
			}
			
			if(this.listsanpham == null)
			{
				this.setMessage("1.Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
				return false;
			}
			
			if(this.listsanpham.size() == 0 )
			{
				this.setMessage("2.Sản phẩm xuất hóa đơn chưa có ,vui lòng chọn lại");
				return false;
			}
			
			if(this.NguoiTao==null || this.NguoiTao.equals(""))
			{
				this.Msg="User Dang Nhap Tam Thoi Bi Log Vi Che Do Bao Mat, Vui Long Dang Nhap Lai De Thuc Hien Chuc Nang Nay";
				return false;
			}
			
			if(this.NppId==null || this.NppId.equals(""))
			{
				this.Msg="Nha Phan Phoi Khong Duoc Rong";
				return false;
			}
			
			try
			{
				Double.parseDouble(this.SoHoaDon);
			}
			catch(Exception er)
			{
				this.Msg="So Hoa Don Khong Hop Le";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			  
			// CreateRs();	 
			if(this.listsanpham.size()<=0)
			{
		 		this.Msg="Khong The Tao Hoa Don Nay,Khong Co San Pham Trong Hoa Don";
		 		return false;
		 	}		  
			 
//			String hopdongId = this.hopdongId.trim().length() <= 0 ? "null" : this.hopdongId.trim();
			sql = " UPDATE ERP_HOADON  SET SOPO=N'"+this.SoPo+"', KM_GHICHU = N'" + this.KM_ghichu + "', ghichu=N'"+this.GhiChu+"',noidungchietkhau=N'"+this.noidungCK+"' ,PO_MT='"+this.POMT+"',NGUOIMUA=N'"+this.NguoiMuaHang+"', NGAYXUATHD ='"+this.NgayXuaHd+"',NGAYSUA='"+this.NgaySua+"',KYHIEU ='"+this.Kyhieu+"'," +
		 		  " SOHOADON='"+this.SoHoaDon+"',HINHTHUCTT=N'"+this.HinhThucTT+"',NGUOISUA='"+this.NguoiSua+"',KHACHHANG_FK="+this.NppId+",chietkhau="+this.chietKhau+", tienkhuyenmai = " + this.tienKhuyenMai + ", tongtienbvat="+this.tienchuaVat+",vat="+this.tienVAT+",tongtienavat="+this.TienSauVat+", " +
		 		  " xuattheo = '" + this.htXuat + "', ngayghinhanCN = '" + this.ngayghinhanCN + "', diengiai = N'" + this.diengiai + "', hopdong_fk = " + hopdongId + ", TIENCHIETKHAU = " + this.tienChietKhau + ", TIENBAOHIEM = " + this.SoTienBaoHiem + ", TIENVANCHUYEN = " + this.freightCost + " , TYGIA = "+ this.tygia +
		 		  " , invoice='"+this.invoice+"'  where pk_seq="+this.Id;
		 
			//System.out.println("1.Update: " + sql);
		
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
		 
			sql = "delete ERP_HOADON_DDH where hoadon_fk="+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
			
			// Những xuất kho đã làm hóa đơn,nhưng lại bỏ ra khỏi phiếu này,nếu ko còn nằm trong hóa đơn tài chính nào khác,thì cập nhật trạng thái là chưa xuất hóa đơn tài chính
		
			sql=    " UPDATE ERP_XUATKHO SET TRANGTHAI=1 WHERE PK_SEQ IN ( "+
					" SELECT XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE XUATKHO_FK  NOT IN ("+ this.DonDatHang+")  AND HOADON_FK="+this.Id +
					" AND XUATKHO_FK  NOT IN  ( SELECT XUATKHO_FK FROM ERP_HOADON_XUATKHO HDXK  "+
					" INNER JOIN ERP_HOADON HD ON HD.PK_SEQ=HDXK.HOADON_FK  WHERE HOADON_FK <> "+this.Id+"  AND HD.TRANGTHAI <>2))";
			
			
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Không thể cập nhật bảng  ERP_XUATKHO:" + sql;
				return false;
			}
			
			
			sql = "delete ERP_HOADON_XUATKHO where hoadon_fk = " + this.Id;
			 
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}
				
			if(this.htXuat.equals("0"))
			{
				int i=0;	
				if(this.DonDatHang.trim().length() > 0)
				{
					sql = "insert into ERP_HOADON_DDH (HOADON_FK, DDH_FK) select '" + this.Id + "', pk_seq from ERP_DONDATHANG where pk_seq in (" + this.DonDatHang + ") ";
					///System.out.println("2.chen don dat hang: " + sql);
					if(!db.update(sql))
					{
						db.update("rollback");
						this.Msg = "Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
						return false;
					}	
					i++;
				}
			}
			else
			{
				int i=0;
				if(this.DonDatHang.trim().length() > 0)
				{
					sql = "insert into ERP_HOADON_XUATKHO (HOADON_FK, XUATKHO_FK) select '" + this.Id + "', pk_seq from erp_xuatkho where pk_seq in (" + this.DonDatHang + ") ";
					//System.out.println("2.chen xuat kho: " + sql);
					if(!db.update(sql))
					{
						db.update("rollback");
						this.Msg = "Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
						return false;
					}	
					i++;
					// cập nhật trạng thái đã xuất hóa đơn của phiếu xuất kho
					sql=" update ERP_XUATKHO set TRANGTHAI=4 where PK_SEQ in  (" + this.DonDatHang + ")  ";
					if(!db.update(sql))
					{
						db.update("rollback");
						this.Msg = "Không thể cập nhật phiếu xuất kho :" + sql;
						return false;
					}	
					
				}
			}
			
			
			sql="delete ERP_HOADON_SP where hoadon_fk="+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
			
			sql="delete ERP_HOADON_SPKM where hoadon_fk="+this.Id;
			if(!db.update(sql))
			{
				db.update("rollback");
				this.Msg="Khong The Cap Nhat Hoa Don ,Loi Tren Dong Lenh Sau :" + sql;
				return false;
			}	
      		
			int count = 0;
			while(count < this.listsanpham.size())
			{
				IErpHoaDon_SP sanpham = listsanpham.get(count);
				
		
				
				if(sanpham.getSoLuong() > 0) {
				
					sanpham.setId(this.Id);
					double thanhtien = sanpham.getSoLuong() * sanpham.getDonGia();
					double thanhtiensauck = sanpham.getSoLuong() * sanpham.getDonGia() - sanpham.getChietKhau();
					double tienVAT = thanhtiensauck * this.VAT/100;
					
					String sp_ghichu = this.sanpham_ghichu.get(sanpham.getIdSanPham());
					if(sp_ghichu == null)
						sp_ghichu = "";
					
					
					if(this.sanpham_gia.containsKey(sanpham.getIdSanPham())){
						
						sanpham.setDonGia(this.sanpham_gia.get(sanpham.getIdSanPham()));
						
					}
					if(this.htb_soluongmoi.containsKey(sanpham.getIdSanPham())){
						sanpham.setSoLuong(this.htb_soluongmoi.get(sanpham.getIdSanPham()));
					}
					 
					if(this.checkgiaVuot20Phantram( sanpham.getIdSanPham(),sanpham.getDonGia() )){
						 
						this.Msg = "Không thể sửa giá vượt quá 20% trong đơn đặt hàng của  mã sản phẩm : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
						db.update("rollback");
						return false;
					}
					if(sanpham.getCTKMId().trim().length() <= 0)
					{
						//System.out.println("so luong cap nhat: "+ sanpham.getSoLuong());
						sql = " insert into erp_hoadon_sp (SANPHAM_FK, HOADON_FK, SOLUONG, DONGIA, DONGIAVIET, CHIETKHAU, TIENBVAT, VAT, TIENAVAT, DONVITINH, SCHEME, ghichu, INRAHD, DVDL_FK) " +
							  " select " + sanpham.getIdSanPham() + ", " + sanpham.getId() + ", " + sanpham.getSoLuong() + ", " + sanpham.getDonGia() + ", " + sanpham.getDonGia() + " * " + this.tygia + ", " +
							  " " + sanpham.getChietKhau() + ", " + thanhtien + ", " + (tienVAT ) + ", " + (thanhtiensauck + tienVAT ) + ", N'" + sanpham.getDonViTinh()+ "', N'" + sanpham.getCTKMId() + "', N'" + sp_ghichu + "', '"+sanpham.getIn() + "', pk_seq from DONVIDOLUONG where DONVI = N'" + sanpham.getDonViTinh() + "' ";
					
						/*sql=" UPDATE ERP_HOADON_SP SET SOLUONG='"+sanpham.getSoLuong()+"' WHERE HOADON_FK='"+sanpham.getId()+"' AND SANPHAM_FK='"+sanpham.getIdSanPham()+"' ";*/
					}
					else
					{
						sql = "insert into erp_hoadon_spkm (SANPHAM_FK, HOADON_FK, SOLUONG, DONGIA, DONGIAVIET, CHIETKHAU, TIENBVAT, VAT, TIENAVAT, DONVITINH, SCHEME, ghichu, INRAHD, DVDL_FK) " +
						  	  " select " + sanpham.getIdSanPham() + ", " + sanpham.getId() + ", " + sanpham.getSoLuong() + ", " + sanpham.getDonGia() + ", " + sanpham.getDonGia() + " * " + this.tygia + ", " +
						  			"" + sanpham.getChietKhau() + ", " + thanhtien + ", " + (tienVAT ) + ", " + (thanhtiensauck + tienVAT ) + ", N'" + sanpham.getDonViTinh()+ "', N'" + sanpham.getCTKMId() + "', N'" + sp_ghichu + "', '"+sanpham.getIn() + "', pk_seq from DONVIDOLUONG where DONVI = N'" + sanpham.getDonViTinh() + "' ";
					}
					
					//System.out.println("___HOA DON - SP - capnhat: " + sql);
					
					if(!db.update(sql)){
						//System.out.println("Kho :"+sql);
						this.Msg="Khong the luu ma san pham : "+ sanpham.getMaSanPham()+" ,Loi trong dong lenh sau : " + sql;
						db.update("rollback");
						return false;
					}
				
				}
				
				count++;
			}	
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{
			db.update("rollback");
			this.setMessage( er.toString() + " Error : " +  sql);
			return false;
		}
		
		return true;
	}
	
	public void initdisplay(String id)
	{
		try
		{
			String query =  "SELECT HD.PK_SEQ,HD.NGAYXUATHD,HD.GHICHU,HD.NOIDUNGCHIETKHAU,HD.PO_MT,HD.NGUOIMUA,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU,HD.SOHOADON,HD.HINHTHUCTT, "+
							" NPP.TEN AS NPP,HD.KHACHHANG_FK, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA, TONGTIENBVAT, CHIETKHAU, TIENKHUYENMAI, VAT, TONGTIENAVAT, HD.HOANTAT,NPP.KENHBANHANG_FK, HD.TIENTE_FK,TT.MA as MATT, HD.XUATTHEO, " +
							" isnull(ngayghinhanCN, HD.NGAYXUATHD ) as ngayghinhanCN, isnull(HD.diengiai, '') as diengiai, HD.hopdong_fk, HD.TYGIA, ISNULL(HD.INVOICE,'') as INVOICE "+
							" FROM ERP_HOADON HD INNER JOIN ERP_KhachHang NPP ON NPP.PK_SEQ = HD.KHACHHANG_FK "+
							" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
							" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA " +
							" LEFT JOIN ERP_TIENTE TT ON HD.TIENTE_FK=TT.PK_SEQ " +
							" WHERE HD.PK_SEQ="+ id;
			
			System.out.println("1.init: " + query);
			
			db = new dbutils();
			ResultSet rs = db.get(query);
			if(rs != null)
			{	
				while (rs.next())
				{
					this.Id = id;
					this.NgaySua = rs.getString("ngaysua");
					this.NgayTao = rs.getString("ngaytao");
					this.NgayXuaHd = rs.getString("NGAYXUATHD");
					this.NppId = rs.getString("KHACHHANG_FK");
					this.NppTen = rs.getString("npp");
					this.SoHoaDon = rs.getString("sohoadon");
					this.TrangThai = rs.getString("trangthai");
					this.Kyhieu = rs.getString("kyhieu");
					this.HinhThucTT = rs.getString("HINHTHUCTT");
					this.NguoiMuaHang = rs.getString("NGUOIMUA");
					this.POMT = rs.getString("PO_MT");
					this.tygia= rs.getString("TYGIA");
					this.invoice= rs.getString("INVOICE");
					
					
					this.tienteId = rs.getString("TIENTE_FK");
					this.tienteTen = rs.getString("MATT");
					this.chietKhau = rs.getDouble("CHIETKHAU");
					this.tienKhuyenMai = rs.getDouble("TIENKHUYENMAI");
					this.tienchuaVat = rs.getDouble("TONGTIENBVAT") + this.chietKhau + this.tienKhuyenMai;
					this.tienSauCK_Km = rs.getDouble("TONGTIENBVAT");
					this.VAT = rs.getDouble("VAT") / this.tienchuaVat * 100;
					this.tienVAT = rs.getDouble("VAT");
					this.TienSauVat = rs.getDouble("TONGTIENAVAT");
					
					this.Msg = "";
					this.noidungCK = rs.getString("noidungchietkhau");
					this.GhiChu = rs.getString("ghichu");
					this.HoanTat = rs.getString("HoanTat");
					this.KenhBanHangId=rs.getString("KENHBANHANG_FK");
					this.htXuat = rs.getString("XuatTheo");
					
					this.ngayghinhanCN = rs.getString("ngayghinhanCN");
					this.diengiai = rs.getString("diengiai");
					this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk") ;
					this.sanpham_ghichu = new Hashtable<String, String>();
				}
				rs.close();
				boolean kenhXuatKhau = this.KenhBanHangId.equals("100001");
				
				if(this.htXuat.equals("0"))
				{
					query = "SELECT HOADON_FK, DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " + this.Id;
				}
				else
				{
					query = "SELECT HOADON_FK, XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = " + this.Id;
				}
				
				System.out.println("2.Khoi tao DDH: " + query);
				
				rs = this.db.get(query);
				String ddhIds = "";
				this.ddhIdList = new ArrayList<String>();
				if(rs != null)
				{
					//this.DonDatHang = new String[20];
					int i= 0;
					while(rs.next())
					{
						if(this.htXuat.equals("0"))
						{
							//this.DonDatHang[i] = rs.getString("DDH_FK");
							ddhIds += rs.getString("DDH_FK") + ",";
							this.ddhIdList.add(rs.getString("DDH_FK"));
						}
						else
						{
							//this.DonDatHang[i] = rs.getString("XUATKHO_FK");
							ddhIds += rs.getString("XUATKHO_FK") + ",";
							this.ddhIdList.add(rs.getString("XUATKHO_FK"));
						}
						i++;
					}
					rs.close();
					
					if(ddhIds.trim().length() > 0)
					{
						ddhIds = ddhIds.substring(0, ddhIds.length() - 1);
						this.DonDatHang = ddhIds;
					}
					else
					{
						this.DonDatHang = "";
					}
				}
				else
				{
					this.setMessage("Khong Lay Duoc Gia tri Don hang");
					return;
				}
				
				if(this.htXuat.equals("0"))
				{
					String sql = "";
					if(this.Id.trim().length() > 0)
					{
						sql = "select ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
							  "from ERP_dondathang ddh "+
							  "where ddh.pk_seq in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk = '" + this.Id + "' ) order by ddh.ngaydat desc " ;
					}
					System.out.println("Xuat theo don hang");
					this.rsddhChuaXuatHD = db.get(sql);
				}
				else
				{
					String sql = "" ;
					if(this.Id.trim().length() > 0)
					{
						sql += 
							   " select prefix + cast(pk_seq as varchar(10) ) as soCT, pk_seq as id, ngayxuat as ngaydat, 1 as STT " +
							   " from ERP_XUATKHO where pk_seq in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk = '" + this.Id + "' ) order by prefix + cast(pk_seq as varchar(10) ) desc  ";
						System.out.println("Xuat theo xuat kho ");
					}
					
					System.out.println("____KHOI TAO XK 1: " + sql);
					System.out.println("an file" );
					this.rsddhChuaXuatHD = db.get(sql);
				}
				
				query = " SELECT DDH_SP.SANPHAM_FK,SP.MA,SP.TEN + ' ' + SP.QUYCACH as tensp, isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, isnull(SP.QUYCACH, '') as QUYCACH , DONGIA,SOLUONG AS SOLUONG, SP.loaisanpham_fk,   " +
						"     ISNULL(round(QUYCACH.SOLUONG1,0),'1') AS QUYDOI, DDH_SP.DONVITINH AS DONVI , '' as scheme, DDH_SP.DVDL_FK AS DVDL_FK, " +
						"     ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG, ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG, ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO, ISNULL(SP.DAIDAY, 0) AS DAIDAY, " +
						"     ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG, ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG, ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY, ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DAUNHO, '') AS DVDL_DAUNHO, ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY, " +
						"     ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU, " +
						"     SP.ten1 as tenXHD1, SP.ten2 as tenXHD2, dvdl.DIENGIAI as DONVI_ENG,  " +
						"     isnull(dvdl1.pk_seq, 0) as dvdl1_fk, isnull(dvdl1.DONVI,'') AS DVDL1TEN, isnull(dvdl1.DIENGIAI,'') as DVDL1DG, isnull(QUYCACH.SOLUONG1,1) as DVDL1SL, " +
						"     isnull(dvdl2.pk_seq, 0) as dvdl2_fk, isnull(dvdl2.DONVI,'') AS DVDL2TEN, isnull(dvdl2.DIENGIAI,'') as DVDL2DG, isnull(QUYCACH.SOLUONG2,1) as DVDL2SL, " +
						"     isnull(DDH_SP.ghichu, '') as ghichu, ISNULL(DDH_SP.inrahd, '1') as inrahd, " +
						"     isnull(sp.dvkd_fk, 0) as dvkd_fk, isnull(sp.nhanhang_fk, 0) as nhanhang_fk, isnull(sp.chungloai_fk, 0) as chungloai_fk, isnull(sp.loaisanpham_fk, 0) as loaisanpham_fk " +
						" FROM ERP_HOADON_SP DDH_SP   INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK     " +
						" LEFT JOIN QUYCACH ON SP.PK_SEQ = QUYCACH.SANPHAM_FK AND SP.DVDL_FK = QUYCACH.DVDL1_FK  " +
						" LEFT JOIN DONVIDOLUONG dvdl ON DDH_SP.DONVITINH = dvdl.DONVI " +
						" LEFT JOIN DONVIDOLUONG dvdl1 ON QUYCACH.DVDL1_FK = DVDL1.PK_SEQ" + 
						" LEFT JOIN DONVIDOLUONG dvdl2 ON QUYCACH.DVDL2_FK = DVDL2.PK_SEQ" + 
						" WHERE DDH_SP.SOLUONG > 0 AND HOADON_FK = '" + this.Id + "'  AND ISNULL(DDH_SP.INRAHD, '1') = '1'  " +
						" order by scheme asc, isnull(DDH_SP.inrahd, '1') desc";
				
				

				
				String dvkd_fk, nhanhang_fk, chungloai_fk, loaisanpham_fk, dvdl_fk, dvdl1_fk, dvdl2_fk;
				
			    System.out.println("[ErpHoaDon.constructor] sp query = " + query);
				rs = this.db.get(query);
				if(rs != null)
				{
					try
					{
						NumberFormat format = new DecimalFormat("#,###,###.###");
						NumberFormat format2 = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							double fDai = rs.getFloat("Dai"), fRong = rs.getFloat("RONG"), fDinhLuong = rs.getFloat("DinhLuong"), fTrongluong = rs.getFloat("TrongLuong"), fDuongKinhTrong = rs.getFloat("DuongKinhTrong"), fDoDay = rs.getFloat("DoDay"), fDauLon = rs.getFloat("DauLon"), fDauNho = rs.getFloat("DauNho"), fDaiDay = rs.getFloat("DaiDay");
							String sDai = format.format(fDai), sRong = format.format(fRong), sDinhluong = format.format(fDinhLuong), sTrongluong = format.format(fTrongluong), sDuongKinhTrong = format.format(fDuongKinhTrong), sDoDay = format.format(fDoDay), sDauLon = format.format(fDauLon), sDauNho = format.format(fDauNho), sDaiDay = format.format(fDaiDay);
							String mau = rs.getString("MAU").trim(); if(mau.toUpperCase().indexOf("NO") > 0 || mau.toUpperCase().indexOf("Khong") > 0 || mau.toUpperCase().indexOf("Không") > 0) mau = "";
							String mauin = rs.getString("MAUIN").trim(); if(mauin.toUpperCase().indexOf("NO") > 0 || mauin.toUpperCase().indexOf("Khong") > 0 || mauin.toUpperCase().indexOf("Không") > 0) mauin = "";
							
							dvkd_fk = rs.getString("dvkd_fk");
							nhanhang_fk = rs.getString("nhanhang_fk");
							chungloai_fk = rs.getString("chungloai_fk");
							loaisanpham_fk = rs.getString("loaisanpham_fk");
							
							IErpHoaDon_SP sanpham = new ErpHoanDon_SP();
							sanpham.setId(this.Id);
							sanpham.setIdSanPham(rs.getString("SANPHAM_FK"));
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setTenSanPham(rs.getString("tensp"));
							sanpham.setTen1(rs.getString("TEN1"));
							sanpham.setTen2(rs.getString("TEN2"));
							sanpham.setTen3(rs.getString("TEN3"));
							sanpham.setQuycach(rs.getString("QUYCACH"));
							sanpham.setIn(rs.getString("inrahd"));
							
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setCTKMID(rs.getString("scheme"));
							
							sanpham.setDonViTinh(rs.getString("DONVI"));
							sanpham.setDonViTinhEng(rs.getString("DONVI_ENG"));
						
							
							if(sanpham.getDonViTinh().toUpperCase().equals("M2"))
								sanpham.setSoLuong(Double.parseDouble(format2.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							else
								sanpham.setSoLuong(Double.parseDouble(format.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							
							sanpham.setQuyDoi(rs.getInt("QUYDOI"));
							sanpham.setLoaisp(rs.getString("loaisanpham_fk"));  //Luu Loai San Pham
							String kichthuoc = sDai + rs.getString("DVDL_DAI")+"x" + sRong + rs.getString("DVDL_Rong");
							sanpham.setKichthuoc(kichthuoc);
							String dinhluong = sDinhluong + rs.getString("DVDL_DINHLUONG");
							sanpham.setDinhluong(dinhluong);
							String trongluong = sTrongluong + "kg";
							sanpham.setTrongluong(trongluong);
							//sanpham.setTenXuatHoaDon(rs.getString("tenXHD"));
							
							//Quy cách in hóa đơn
							boolean isCone = dvkd_fk.equals("100004") && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 || sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0  ||  sanpham.getMaSanPham().trim().equals("OE") );
							String qc = "";
							if(isCone) {
								//Lõi: Đường kính trong x Dài x Độ Dày
								if(fDuongKinhTrong > 0) { qc += " " + sDuongKinhTrong + rs.getString("DVDL_DKTRONG"); }
								if(fDauLon > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauLon + rs.getString("DVDL_DAULON"); }
								if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai + rs.getString("DVDL_DAI"); }
								if(fDauNho > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauNho + rs.getString("DVDL_DAUNHO"); }
								if(fDoDay > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDoDay + rs.getString("DVDL_DODAY"); }
								
								if(kenhXuatKhau) {
									qc += " (" + sTrongluong + rs.getString("DVDL_TRONGLUONG") + ")";
								} else {
									if(mauin.length() > 0) { qc += " " + mauin; }
									if(mau.length() > 0) { qc += " " + mau; }
								}
							} else {
							    if(fRong > 0) { qc += " " + sRong + rs.getString("DVDL_Rong"); }
							    if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai + rs.getString("DVDL_DAI"); }
							    if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDinhluong + rs.getString("DVDL_DINHLUONG"); }
							    qc = "}" + qc;
							}
						       
						    //Quy doi dvdl
							dvdl_fk = rs.getString("DVDL_FK");
							
							dvdl1_fk = rs.getString("DVDL1_FK");
						    String dvdl1ten = rs.getString("DVDL1TEN").trim();
						    String dvdl1dg = rs.getString("DVDL1DG").trim();
						    double dvdl1sl = rs.getDouble("DVDL1SL");

							dvdl2_fk = rs.getString("DVDL2_FK");
						    String dvdl2ten = rs.getString("DVDL2TEN").trim();
						    String dvdl2dg = rs.getString("DVDL2DG").trim();
						    double dvdl2sl = rs.getDouble("DVDL2SL");
						    
						    double soluong = rs.getDouble("soluong");
						    double quydoi = 0; 
						    String dvdltenquydoi = "";
						    String dvdldgquydoi = "";
						    if(dvdl_fk.equals(dvdl1_fk)) {
						    	quydoi = soluong * dvdl2sl / dvdl1sl;
						    	dvdltenquydoi = dvdl2ten;
						    	dvdldgquydoi = dvdl2dg;
						    } else {
						    	quydoi = soluong * dvdl1sl / dvdl2sl;
						    	dvdltenquydoi = dvdl1ten;
						    	dvdldgquydoi = dvdl1dg;
						    }
						    
//						    NumberFormat formatter = new DecimalFormat("###,###,###.###");
						    if (this.KenhBanHangId.equals("100000")) {
						        sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + qc);
						        sanpham.setQuyDoiStr( FormatNumber(quydoi, 0) + " " + dvdltenquydoi);
						    } else {
						        sanpham.setTenXuatHoaDon(rs.getString("tenXHD2") + qc);
						        sanpham.setQuyDoiStr(format.format(quydoi) + " " + dvdldgquydoi);
						    }
						    
						    String ghichu = rs.getString("ghichu");
						    if(ghichu.trim().length() > 0)
						    {
						    	System.out.println("__GHI CHU: " + ghichu);
						    	if(ghichu.contains("__"))
						    	{
						    		this.sanpham_ghichu.put(sanpham.getIdSanPham(), ghichu);
						    	}
						    }
						    
							this.listsanpham.add(sanpham);
							
						}
						rs.close();
					}
					catch(Exception er)
					{
						System.out.println("[ErpHoaDon.constructor] SP Exception Message = " + er.getMessage());
					}
				}
			}
		}
		catch(Exception er)
		{
			System.out.println("[ErpHoaDon.constructor] 4.Exception: " + er.getMessage());
		}
		
		String sql = "select pk_seq, ma + ', ' + ten as ten from erp_tiente";
		this.tienteRs = db.get(sql);
		
		System.out.println("___TIEN TE: " + sql);
		
/*		if(this.tienteId.trim().length() > 0)
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
		}*/
		
		if(this.NppId.trim().length() > 0 && this.NgayXuaHd.trim().length() > 0)
		{
			String query = "select pk_seq, mahopdong from ERP_HOPDONG " +
						   "where khachhang_fk = '" + this.NppId + "' and tungay <= '" + this.NgayXuaHd + "' and denngay >= '"  + this.NgayXuaHd + "' ";
			//System.out.println("Lay hop dong: " + query);
			this.hopdongRs = db.get(query);
		}
			
	}
	
	public void SetPOMT(String PoMt) {
		
		this.POMT=PoMt;
	}
	
	public String GetPOMT() {
		
		return this.POMT;
	}
	
	public String getPOInfo() 
	{
		return this.poInfo;
	}
	
	public void setPOInfor(String poInfo) 
	{
		this.poInfo = poInfo;
	}
	
	public String getNoidungCK()
	{	
		return this.noidungCK;
	}
	
	public void setNoidungCK(String noidungCK)
	{
		this.noidungCK = noidungCK;
	}
	
	public void SetGhiChu(String ghichu) {
		
		this.GhiChu=ghichu;
	}
	
	public String getGhiChu() {
		
		return this.GhiChu;
	}
	
	public String getHoanTat() {
		return this.HoanTat;
	}
	
	public void setHoanTat(String HoanTat) {
	this.HoanTat=HoanTat;
		
	}
	
	public boolean EditHT() 
	{
		String query="Update Erp_HoaDon Set SoHoaDon = N'" + this.SoHoaDon + "', KyHieu = '" + this.Kyhieu + "' Where PK_SEQ = '" + this.Id + "' And HoanTat=0";
		System.out.println("__Update HD: " +  query);
		if(!this.db.update(query))
		{
			this.setMessage("Không thể cập nhật hóa đơn");
			return false;
		}
		
		return true;
	}

	
	public double getTongtienchuaVat() {
		
		return this.tienchuaVat;
	}

	
	public void setTongtienchuaVat(double ttchuaVAT) {
		
		this.tienchuaVat = ttchuaVAT;
	}

	
	public double getChietkhau() {
		
		return this.chietKhau;
	}

	
	public void setChietkhau(double chietkhau) {
		
		this.chietKhau = chietkhau;
	}

	
	public double getTienkhuyenmai() {
		
		return this.tienKhuyenMai;
	}

	
	public void setTienkhuyenmai(double tienKM) {
		
		this.tienKhuyenMai = tienKM;
	}

	public String getKM_ghichu() {
		
		return this.KM_ghichu;
	}

	
	public void setKM_ghichu(String KM_ghichu) {
		
		this.KM_ghichu = KM_ghichu;
	}

	
	public double getTienSauCK_KM() {
		
		return this.tienSauCK_Km;
	}

	
	public void setTienSauCK_KM(double tiensauCK) {
		
		this.tienSauCK_Km = tiensauCK;
	}

	
	public double getTongtiensauVAT() {
		
		return this.TienSauVat;
	}

	
	public void setTongtiensauVAT(double ttsvat) {
		
		this.TienSauVat = ttsvat;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public void DbClose() 
	{
		try
		{
			if (this.lichsuinRs != null)
				this.lichsuinRs.close();
			if (this.listsanphamCone != null)
				this.listsanphamCone.clear();
			if (this.ddhIdList != null)
				this.ddhIdList.clear();
			if (this.hopdongRs != null)
				this.hopdongRs.close();
			if (this.sanpham_ghichu != null)
				this.sanpham_ghichu.clear();
			if (this.sanpham_gia != null)
				this.sanpham_gia.clear();
			if (this.htb_soluongmoi != null)
				this.htb_soluongmoi.clear();
			if(listHoaDon!=null){
				listHoaDon.close();
			}
			if(rsddhChuaXuatHD!=null){
				rsddhChuaXuatHD.close();
			}
			if(this.tienteRs != null)
				this.tienteRs.close();
			if(this.dvkdRs != null)
				this.dvkdRs.close();
			
			listsanpham.clear();
			HoadonList.clear() ;
		
		}
		catch(Exception err){
			err.printStackTrace();
		}
		finally
		{
			if (this.db != null)
				db.shutDown();
		}
	}

	
	public String getKenhbanhangId()
	{
		if(this.KenhBanHangId==null){
			this.KenhBanHangId="";
		}
			return this.KenhBanHangId;
	}

	
	public void setKenhbanhangId(String kenhbanhangId)
	{
		this.KenhBanHangId=kenhbanhangId;
		
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

	public String getTienteTen() {
		
		return this.tienteTen;
	}

	
	public void setTienteTen(String ttTen) {
		
		this.tienteTen= ttTen;
	}
	
	public String getTyGiaQuyDoi() {
		
		return this.tygia;
	}

	
	public void setTyGiaQuyDoi(String tygia) {
		
		this.tygia = tygia;
	}

	public String getHinhthucxuat() 
	{
		return this.htXuat;
	}

	public void setHinhthucxuat(String htxuat) {
		
		this.htXuat = htxuat;
	}

	private static String FormatNumber(double number, int round)
	{
		// System.out.println("Truoc kho Format: " + number);
		String format = "#,###,###";
		if (round >= 1)
			format += ".";

		for (int i = 0; i < round; i++)
			format += "0";

		// System.out.println("Chuoi Format: " + format);

		DecimalFormat df = new DecimalFormat(format);
		String result = df.format(number);

		if (number > 999)
		{
			// result = result.replaceAll(".", "+");
			result = result.replaceAll(",", ".");
			if (round > 0)
				result = result.substring(0, result.length() - (round + 1)) + ","
						+ result.substring(result.length() - round);
			// result = result.replaceFirst("-", ",");
		} else
			result = result.replaceAll(",", ".");

		// System.out.println("ket qua: " + result);
		return result;
	}

	
	public ResultSet getHopdongRs() {
		
		return this.hopdongRs;
	}

	
	public void setHopdongRs(ResultSet hdRs) {
		
		this.hopdongRs = hdRs;
	}

	
	public String getHopdongId() {
		
		return this.hopdongId;
	}

	
	public void setHopdongId(String hdId) {
		
		this.hopdongId = hdId;
	}

	
	public void setNgayghinhanCN(String ngayghinhanCN) {
		
		this.ngayghinhanCN = ngayghinhanCN;
	}

	
	public String getNgayghinhanCN() {
		
		return this.ngayghinhanCN;
	}

	
	public void setGhichu(String ghichu) {
		
		this.GhiChu = ghichu;
	}

	
	public String getGhichu() {
		
		return this.GhiChu;
	}

	
	public void setSanphamGhiChu(Hashtable<String, String> sanpham_ghichu) {
		
		this.sanpham_ghichu = sanpham_ghichu;
	}

	
	public Hashtable<String, String> getSanphamGhiChu() {
		
		return this.sanpham_ghichu;
	}
	
	/**
	 * Lấy số ký tự 1 dòng hóa đơn từ kbhId
	 * @param kbhId
	 * @return
	 */
	public static int getSoKyTu1DongSanPham(String kbhId) {
		return kbhId.equals("100000") ? 48 : 45;
	}

	@Override
	public int getSoKyTu1DongSanPham() {
		return getSoKyTu1DongSanPham(this.getKenhbanhangId());
	}
	
	/**
	 * Lấy số dòng hóa đơn từ kbhId
	 * @param kbhId
	 * @return
	 */
	public static int getSoDongSanPham(String kbhId) {
		return kbhId.equals("100000") ? 27 : 18;
	}

	@Override
	public int getSoDongSanPham() {
		return getSoDongSanPham(this.getKenhbanhangId());
	}

	@Override
	public List<String> getDonDatHangIdList() {
		return this.ddhIdList;
	}

	@Override
	public void setDonDatHangIdList(List<String> list) {
		this.ddhIdList = list;
	}

	@Override
	public double getTienVAT() {
		return this.tienVAT;
	}

	@Override
	public void setTienVAT(double vat) {
		this.tienVAT = vat;
	}

	@Override
	public void setTienChietKhau(double tienck) {
		this.tienChietKhau = tienck;
	}

	@Override
	public double getTienChietKhau() {
		return this.tienChietKhau;
	}
	

	@Override
	public String getPaymentTerms() {
		return this.paymentTerms;
	}

	@Override
	public void setPaymentTerms(String paymentTerms) {
		this.paymentTerms = paymentTerms;
	}

	@Override
	public String getDeliveryTerms() {
		return this.deliveryTerms;
	}

	@Override
	public void setDeliveryTerms(String deliveryTerms) {
		this.deliveryTerms = deliveryTerms;
	}

	@Override
	public String getETD() {
		return this.etd;
	}

	@Override
	public void setETD(String ETD) {
		this.etd = ETD;
	}

	@Override
	public String getRemarks() {
		return this.remarks;
	}

	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	@Override
	public double getTienBaoHiem()
	{
		return SoTienBaoHiem;
	}

	@Override
	public void setTienBaoHiem(String SoTienBaoHiem)
	{
		try {
			this.SoTienBaoHiem = Double.parseDouble(SoTienBaoHiem);
		} catch(Exception e) { }
	}

	@Override
	public void setTienBaoHiem(double SoTienBaoHiem) {
		this.SoTienBaoHiem = SoTienBaoHiem;
	}

	@Override
	public double getTienVanChuyen() {
		return this.freightCost;
	}


	@Override
	public void setTienVanChuyen(String freightCost) {
		try { this.freightCost = Double.parseDouble(freightCost); } catch(Exception e) { } 
	}

	@Override
	public void setTienVanChuyen(double freightCost) {
		this.freightCost = freightCost;
	}

	@Override
	public String getCustomersPO() {
		return this.customersPo;
	}

	@Override
	public void setCustomersPO(String cpo) {
		this.customersPo = cpo;
	}
	
	public static String formatVN(String so) {
		
		String result = so.replaceAll(",", "@");
		result = result.replaceAll("[.]", ",");
		result = result.replaceAll("@", ".");
		
		return result;
		
	}

	@Override
	public String getDvkdId() {
		return this.dvkdId;
	}

	@Override
	public void setDvkdId(String dvkdId) {
		this.dvkdId = dvkdId;
	}

	@Override
	public ResultSet getDvkdRs() {
		return this.dvkdRs;
	}

	@Override
	public void setDvkdRs(ResultSet dvkdRs) {
		this.dvkdRs = dvkdRs;
	}

	@Override
	public void createDvkdRs() {
		String query = " select pk_seq, donvikinhdoanh from donvikinhdoanh ";
		this.dvkdRs = db.get(query);
	}

	@Override
	public String getChoPhepSuaGiaHD() {
		// TODO Auto-generated method stub
		return ChoPhepSuaGiaHD;
	}

	@Override
	public void setChoPhepSuaGiaHD(String ChoPhepSuaGiaHD) {
		// TODO Auto-generated method stub
		this.ChoPhepSuaGiaHD=ChoPhepSuaGiaHD;
	}

	@Override
	public Hashtable<String, Double> getSanphamGia() {
		// TODO Auto-generated method stub
		return sanpham_gia;
	}

	@Override
	public void setSanphamGia(Hashtable<String, Double> SanphamGia) {
		// TODO Auto-generated method stub
		sanpham_gia=SanphamGia;
	}

	@Override
	public String getSoXuatKho() {
		// TODO Auto-generated method stub
		return this.SoXuatkho;
	}

	@Override
	public void setSoXuatKho(String SoXuatKho) {
		// TODO Auto-generated method stub
		this.SoXuatkho=SoXuatKho;
	}
	

	public String getNccId() {
		return this.NccId;
	}


	public void setNccId(String nccId) {
		this.NccId = nccId;
	}
	
	

	public String getDienthoai() {

		return this.dienthoai;
	}


	public void setDienthoai(String dienthoai) {
		this.dienthoai=dienthoai;
		
	}

	public String getFax() {
	
		return this.fax;
	}


	public void setFax(String fax) {
		this.fax= fax;
		
	}

	
	
	
	public boolean ktSoHoadon() {
		
		 
		if(this.SoHoaDon.trim().equals("")){
			this.Msg=" Vui lòng nhập số hóa đơn ";
			return false;
		}
		try{
		String tmp="0000000"+this.SoHoaDon;
		this.SoHoaDon = ( tmp).substring(tmp.length()-7 ,tmp.length());

		ResultSet  kt;
		String  query=" select SOHOADON, NGAYXUATHD from ERP_HOADON where  "
					+ " TRANGTHAI!=2 and SOHOADON='"+this.SoHoaDon+"'  and NGAYXUATHD='"+this.NgayXuaHd+"' ";
		
		if(this.Id.length() >0){
			query=query+" and pk_Seq <> "+this.Id;
		}
	
		kt = db.get(query);
		
		if(kt.next()){
			this.Msg=" Số hóa đơn đã bị trùng ";
			kt.close();
			return false;
		}
		if (kt != null)
			kt.close();
		return true;
		} catch(Exception e){
			e.printStackTrace();
			this.Msg =" "; 
			return false;
		}
	}

	@Override
	public void sethtb_SoluongMoi(Hashtable<String, Double> htb_SoluongMoi) {
		// TODO Auto-generated method stub
		this.htb_soluongmoi=htb_SoluongMoi;
	}

	@Override
	public Hashtable<String, Double> gethtb_SoluongMoi() {
		// TODO Auto-generated method stub
		return this.htb_soluongmoi;
	}


	public String getSoPO() {
	
		return this.SoPo;
	}

	
	public void setSoPO(String SoPO) {
		
		this.SoPo=SoPO;
	}

	@Override
	public String getNppDiachi() {
		// TODO Auto-generated method stub
		return this.DiachiNPP;
	}

	@Override
	public void setNppDiachi(String nppDiachi) {
		// TODO Auto-generated method stub
		this.DiachiNPP=nppDiachi;
	}


	public String getinvoice() {
		
		return this.invoice;
	}


	public void setinvoice(String invoice) {
		this.invoice= invoice;
		
	}
 
	public List<IErpHoaDonList> getHoadonList()
	{
		return this.HoadonList;
	}

	public void setHoadonList(List<IErpHoaDonList> hoadonList) 
	{
		this.HoadonList = hoadonList ;
		
	}

	public String CreateLSIN(String hdId, String loaihd) 
	{

		String msg= "" ;
		String query= "" ;
		
		try
		{
			// 0.Tính số lần in
			query = " SELECT count(*) as dem FROM LICHSUIN WHERE SOCHUNGTU = '"+ hdId +"' AND ISNULL(LOAIHD,0) = 0 ";
			int solanin = 0;
			ResultSet rs = db.get(query);
			if(rs!= null)
			{
				while(rs.next())
				{
					solanin = rs.getInt("dem");
				}
				rs.close();
			}
			
			solanin = solanin + 1;
			
			int ishdNuocngoai= 0;   // 0: Hóa đơn trong nước; 1: Hóa đơn nước ngoài
			if(this.KenhBanHangId.equals("100001"))
			{
				ishdNuocngoai= 1;
			}
			
			db.getConnection().setAutoCommit(false);
			// 1.Lưu vào bảng tổng LICHSUIN
			query = " INSERT INTO LICHSUIN " +
					" SELECT PK_SEQ, SOHOADON, TRANGTHAI, '"+ this.getDate() +"', '"+ this.UserId +"', "+ solanin +" , "+ ishdNuocngoai +", "+ loaihd +" " +
					" FROM ERP_HOADON" +
					" WHERE PK_SEQ = '"+ hdId +"' ";
			System.out.println("Câu insert LICHSUIN "+query);
			if(!db.update(query))
			{
				msg= "Không thể lưu vào bảng LICHSUIN " + query ;
				db.getConnection().rollback();
			}
			
			String lsId = "";
			query = "select IDENT_CURRENT('LICHSUIN') as lsId";
			
			ResultSet rsLs = db.get(query);		
			if (rsLs != null)
			{
				if(rsLs.next())
				{
					lsId = rsLs.getString("lsId");
				}
				rsLs.close();
			}
			
			// 2.Lưu vào bảng LICHSUIN_HOADON
			query = "INSERT INTO LICHSUIN_HOADON \n"+
					" SELECT "+ lsId +", HD.NGAYXUATHD,  isnull(NPP.CHOPPHEPSUAHD,'0') as CHOPPHEPSUAHD, HD.TRANGTHAI, HD.NOIDUNGCHIETKHAU, \n"+
					"       ISNULL(HD.PO_MT, 0) AS PO_MT, isnull(HD.NGUOIMUA,'') as nguoimua, HD.GHICHU, HD.KYHIEU, HD.SOHOADON, HD.HINHTHUCTT, NPP.TEN AS NPP, HD.KHACHHANG_FK,  \n"+
					" 		TONGTIENBVAT, CHIETKHAU, ISNULL(HD.TIENCHIETKHAU, 0) AS TIENCHIETKHAU, ISNULL(TIENBAOHIEM, 0) AS TIENBAOHIEM, ISNULL(TIENVANCHUYEN, 0) AS TIENVANCHUYEN, \n"+
					" 		ISNULL(TIENKHUYENMAI, 0) AS TIENKHUYENMAI, ISNULL(VAT, 0) AS VAT, ISNULL(TONGTIENAVAT, 0) AS TONGTIENAVAT, ISNULL(HD.TYGIA,1) AS TYGIA, " +
					"       HD.NGAYTAO ,HD.NGAYSUA, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA, isnull(ngayghinhanCN, HD.NGAYXUATHD ) as ngayghinhanCN, \n"+
					" 	    CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN NPP.KENHBANHANG_FK ELSE '100000' END AS KENHBANHANG_FK , ISNULL(HD.TIENTE_FK,100000) AS TIENTE_FK, TT.MA AS MATT, HD.XUATTHEO, ISNULL(HD.HOANTAT, 0) AS HOANTAT, \n"+
					" 		 isnull(HD.diengiai, '') as diengiai, ISNULL(HD.hopdong_fk, 0) AS HOPDONG_FK, ISNULL(HD.KM_GHICHU, '') AS KM_GHICHU, ISNULL(HD.SOPO,'')  AS SOPO , HD.NCC_FK   \n"+
					" FROM ERP_HOADON HD " +
					" 		LEFT JOIN ERP_KhachHang NPP ON NPP.PK_SEQ = HD.KHACHHANG_FK \n"+
					" 		LEFT JOIN ERP_Nhacungcap NCC ON NCC.PK_SEQ = HD.NCC_FK \n"+
					" 		INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO \n"+
					" 		INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA \n"+
					" 		LEFT JOIN ERP_TIENTE TT ON HD.TIENTE_FK=TT.PK_SEQ \n"+
					" WHERE HD.PK_SEQ="+ hdId;
			System.out.println("Câu insert LICHSUIN_HOADON "+query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg= "Không thể lưu vào bảng LICHSUIN_HOADON " + query ;
				db.getConnection().rollback();
			}
			
			// 3.Lưu vào bảng LICHSUIN_TTDONHANG	
			if(loaihd.equals("0"))
			{
				query = "INSERT INTO LICHSUIN_TTDONHANG \n"+
						" SELECT "+ lsId +", isnull(ddh.paymentterms, '') as paymentterms, isnull(ddh.deliveryterms, '') as deliveryterms, \n"+
						"        isnull(ddh.etd, '') as etd, isnull(ddh.remarks, '') as remarks, isnull(ddh.FreightCost, 0) as FreightCost, \n"+
						" 		 isnull(ddh.CUSTOMERSPO, '') AS CUSTOMERSPO \n"+
						" FROM ERP_DONDATHANG ddh \n"+
						" WHERE ddh.PK_SEQ IN ( \n"+
						(this.htXuat.equals("0") 
							? " SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " + this.Id + " "
							: " SELECT TOP (1) DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ( SELECT TOP(1) XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = " + this.Id + " ) "
						) +
						" ) ";
				System.out.println("Câu insert LICHSUIN_TTDONHANG "+query);
				if(db.updateReturnInt(query) <= 0)
				{
					msg= "Không thể lưu vào bảng LICHSUIN_TTDONHANG " + query ;
					db.getConnection().rollback();
				}
			}
			
			// 4.Lưu vào bảng LICHSUIN_SANPHAM
			query = "INSERT INTO LICHSUIN_SANPHAM  \n"+
					" SELECT DISTINCT "+ lsId +", DDH_SP.SANPHAM_FK, SP.MA, SP.TEN + ' ' +  \n"+
					"       case \n"+
					"	    when substring(LTRIM(isnull(SP.QUYCACH,'')), 1, 1 ) = 'x' then STUFF(LTRIM(isnull(SP.QUYCACH,'')),1,1,'') \n"+
					"	    else isnull(SP.QUYCACH,'') end  as tensp,  \n"+
					" 		isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, \n"+
					" 		isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, isnull(SP.QUYCACH, '') as QUYCACH , DDH_SP.DONGIA, DDH_SP.DONGIAVIET, \n"+
					" 		DDH_SP.SOLUONG AS SOLUONG, SP.loaisanpham_fk, isnull(DDH_SP.dvdl_fk,0) as dvdl_fk ,  \n"+
					" 		ISNULL(round(QUYCACH.SOLUONG1,0),'1') AS QUYDOI, DONVITINH AS DONVI , '' as scheme,     \n"+
					" 		ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG, \n"+
					" 		ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG, \n"+
					" 		ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO, \n"+
					" 		ISNULL(SP.DAIDAY, 0) AS DAIDAY, ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG, \n"+
					" 		ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG, \n"+
					" 		ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY, \n"+
					" 		ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DVDL_DAUNHO, '') AS DVDL_DAUNHO, \n"+
					" 		ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY,  ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU,      \n"+
					" 		SP.ten1 as tenXHD1, SP.ten2 as tenXHD2, dvdl.DIENGIAIANH as DONVI_ENG, \n"+
					" 		isnull(DVDL.TENINAN, ISNULL(DVDL.DONVI, '')) as DVINAN,        \n"+
					" 		isnull(dvdl1.pk_seq, 0) as dvdl1_fk, isnull(dvdl1.DONVI,'') AS DVDL1TEN, \n"+
					" 		isnull(dvdl1.DIENGIAI,'') as DVDL1DG, isnull(QUYCACH.SOLUONG1,1) as DVDL1SL, \n"+
					" 		isnull(DVDL1.TENINAN, ISNULL(DVDL1.DONVI, '')) as DVINAN1,      \n"+
					" 		isnull(dvdl2.pk_seq, 0) as dvdl2_fk, isnull(dvdl2.DONVI,'') AS DVDL2TEN, \n"+
					" 		isnull(dvdl2.DIENGIAI,'') as DVDL2DG, isnull(QUYCACH.SOLUONG2,1) as DVDL2SL, \n"+
					" 		isnull(DVDL2.TENINAN, ISNULL(DVDL2.DONVI, '')) as DVINAN2,      \n"+
					" 		isnull(DDH_SP.ghichu, '') as ghichu, (CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN ISNULL(DDH_SP.inrahd, '1') ELSE '1' END) as inrahd, sp.dvdl_fk as dvdl_chuan ,  \n"+
					" 		isnull(sp.dvkd_fk, 0) as dvkd_fk, \n"+
					" 		isnull(sp.nhanhang_fk, 0) as nhanhang_fk, isnull(sp.chungloai_fk, 0) as chungloai_fk, \n"+
					" 		A.SOLUONG as SOLUONGCHUAN  "+
					" FROM ERP_HOADON_SP DDH_SP    "+
					" 	   INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK \n"+
					" 	   INNER JOIN ERP_HOADON HD ON HD.PK_SEQ= DDH_SP.HOADON_FK \n"+
					"      left JOIN ERP_HOADON_DDH DDH ON HD.PK_SEQ= DDH.HOADON_FK \n"+
					"      left JOIN ERP_DONDATHANG_SP A ON A.DONDATHANG_FK=DDH.DDH_FK AND A.SANPHAM_FK = DDH_SP.SANPHAM_FK \n"+
					"      LEFT JOIN QUYCACH ON SP.PK_SEQ = QUYCACH.SANPHAM_FK  AND DDH_SP.DVDL_FK = QUYCACH.DVDL2_FK    \n"+
					"      LEFT JOIN DONVIDOLUONG dvdl ON DDH_SP.dvdl_fk = dvdl.pk_seq  \n"+
					"      LEFT JOIN DONVIDOLUONG dvdl1 ON QUYCACH.DVDL1_FK = DVDL1.PK_SEQ \n"+
					"      LEFT JOIN DONVIDOLUONG dvdl2 ON QUYCACH.DVDL2_FK = DVDL2.PK_SEQ "+
					" WHERE DDH_SP.SOLUONG > 0 AND DDH_SP.HOADON_FK = '" + hdId + "'     \n"+
					" ORDER BY  (CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN ISNULL(DDH_SP.inrahd, '1') ELSE '1' END) DESC, SP.MA, ISNULL(SP.DUONGKINHTRONG,0), \n"+
					"          ISNULL(SP.DVDL_DKTRONG,''), ISNULL(SP.DAULON,0), ISNULL(SP.DVDL_DAULON,''), ISNULL(SP.DAI,0), \n"+
					" 		   ISNULL(SP.DVDL_DAI,''), ISNULL(SP.DAUNHO,0), ISNULL(SP.DVDL_DAUNHO,''), ISNULL(SP.DODAY,0), \n"+
					"          ISNULL(SP.DVDL_DODAY,''), DDH_SP.DONGIA ASC, SCHEME ASC ";

			
			System.out.println("Câu insert LICHSUIN_SANPHAM "+query);
			if(db.updateReturnInt(query) <= 0)
			{
				msg= "Không thể lưu vào bảng LICHSUIN_SANPHAM " + query ;
				db.getConnection().rollback();
			}
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);			
			
		}catch(Exception e)
		{
			msg = " Lỗi phát sinh trong quá trình lưu. ";
			e.printStackTrace();
		}
		
		System.out.println("Lưu vào LỊCH SỬ IN thành công");
		return msg;
	
	}

	public ResultSet getLichSuInRs() 
	{
		return this.lichsuinRs;
	}

	public void setLichSuInRs(ResultSet lichSuInRs) 
	{
		this.lichsuinRs = lichSuInRs;
	}

	public String getLichSuInId()
	{
		return this.lichsuinId;
	}

	public void setLichSuInId(String lichSuInId) 
	{
		this.lichsuinId = lichSuInId;
	}

	public void initLSIN(String id) 
	{


		try 
		{
			String query =  " SELECT ct.*, t.SOCHUNGTU," +
							"  ( SELECT COUNT(*) " +
							"    FROM LICHSUIN_HOADON " +
							"    WHERE PK_SEQ ="+ id+" AND KHACHHANG_FK IN (SELECT KHACHHANG_FK FROM nhomkhachhangtt_khachhangtt WHERE NKHTT_FK = '100001' )" +
							"  )AS ISKH_METRO  "+
							" FROM LICHSUIN_HOADON ct inner join LICHSUIN t on ct.LICHSUIN_FK = t.PK_SEQ " +
							" WHERE PK_SEQ = "+ id;
			
			System.out.println("1.init: " + query);
			String trangthai_hd="";
			int iskh_METRO = 0;
			db = new dbutils();
			ResultSet rs = db.get(query);
			if(rs != null)
			{	
				while (rs.next())
				{
					
					trangthai_hd=rs.getString("trangthai").trim();
					this.Id = rs.getString("SOCHUNGTU");
					this.SoPo=rs.getString("SOPO");
					this.ChoPhepSuaGiaHD=rs.getString("CHOPHEPSUAHD");
					this.NgaySua = rs.getString("ngaysua");
					this.NgayTao = rs.getString("ngaytao");
					this.NgayXuaHd = rs.getString("NGAYXUATHD");
					this.NppId = rs.getString("KHACHHANG_FK")== null ? "" :rs.getString("KHACHHANG_FK") ;
					this.NppTen = rs.getString("npp");
					this.NccId = rs.getString("NCC_FK")== null ? "" :rs.getString("NCC_FK") ;
					this.SoHoaDon = rs.getString("sohoadon");
					this.TrangThai = rs.getString("trangthai");
					this.Kyhieu = rs.getString("kyhieu");
					this.HinhThucTT = rs.getString("HINHTHUCTT");
					this.NguoiMuaHang = rs.getString("NGUOIMUA");
					this.POMT = rs.getString("PO_MT");
					this.tygia = rs.getString("TYGIA");
					
					iskh_METRO = rs.getInt("ISKH_METRO");
					
					this.tienteId = rs.getString("TIENTE_FK");
					this.tienteTen = rs.getString("MATT");
					this.chietKhau = rs.getDouble("CHIETKHAU");
					this.tienChietKhau = rs.getDouble("TIENCHIETKHAU");
					this.tienKhuyenMai = rs.getDouble("TIENKHUYENMAI");
					this.KM_ghichu = rs.getString("KM_GHICHU");;
					this.tienchuaVat = rs.getDouble("TONGTIENBVAT");
					this.SoTienBaoHiem = rs.getDouble("TIENBAOHIEM");
					this.freightCost = rs.getDouble("TIENVANCHUYEN");
					this.tienSauCK_Km = rs.getDouble("TONGTIENBVAT") - (this.tienChietKhau > 0 ? this.tienChietKhau : this.chietKhau * rs.getDouble("TONGTIENBVAT") / 100) - this.tienKhuyenMai + this.SoTienBaoHiem + this.freightCost;
					System.out.println("tien sau ck_KM,...."+this.tienSauCK_Km );
					this.VAT = rs.getDouble("VAT") / this.tienSauCK_Km * 100;
					System.out.println("tien VAT,...."+this.VAT);
					//this.VAT = rs.getDouble("VAT") ;
					this.tienVAT = rs.getDouble("VAT");
					this.TienSauVat = rs.getDouble("TONGTIENAVAT");
					
					this.Msg = "";
					this.noidungCK = rs.getString("noidungchietkhau");
					this.GhiChu = rs.getString("ghichu");
					this.HoanTat = rs.getString("HoanTat");
					this.KenhBanHangId=rs.getString("KENHBANHANG_FK");
					this.htXuat = rs.getString("XuatTheo");
					
					this.ngayghinhanCN = rs.getString("ngayghinhanCN");
					this.diengiai = rs.getString("diengiai");
					this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk") ;
					this.sanpham_ghichu = new Hashtable<String, String>();
					
				}
				rs.close();
				boolean kenhXuatKhau = this.KenhBanHangId.equals("100001");
								
				String dvkd_fk = "";
//				String kbh_fk = "";
				
				//Lấy một số thông tin đơn đặt hàng
				query = " SELECT * "+
						" FROM LICHSUIN_TTDONHANG " +
						" WHERE LICHSUIN_FK = "+ id +" ";
				//System.out.println(query);
				
				rs = this.db.get(query);
				if (rs != null)
				{
					try {
						if (rs.next())
						{
							this.paymentTerms = rs.getString("PAYMENTTERMS");
							this.deliveryTerms = rs.getString("DELIVERYTERMS");
							this.etd = rs.getString("ETD");
							this.remarks = rs.getString("REMARKS");
							this.customersPo = rs.getString("CUSTOMERSPO");
						}
						rs.close();
					} catch(Exception e) {
						e.printStackTrace();
					}
				}
				
				query=	" SELECT * "+
						" FROM LICHSUIN_SANPHAM   " +
						" WHERE LICHSUIN_FK = "+ id +"  ";
				
				System.out.println("reload 11 --: "+query);
			    String nhanhang_fk, chungloai_fk, loaisanpham_fk, dvdl_fk, dvdl1_fk, dvdl2_fk;
				rs = this.db.get(query);
				if(rs != null)
				{
					try
					{
						 NumberFormat format = new DecimalFormat("#,###,###.###");
						NumberFormat format2 = new DecimalFormat("#,###,###");
						while(rs.next())
						{
							dvkd_fk = rs.getString("dvkd_fk");
							nhanhang_fk = rs.getString("nhanhang_fk");
							chungloai_fk = rs.getString("chungloai_fk");
							loaisanpham_fk = rs.getString("loaisanpham_fk");
							
							
							double fDai = rs.getDouble("Dai"), fRong = rs.getDouble("RONG"),  fDinhLuong = rs.getDouble("DinhLuong"), fTrongluong = rs.getDouble("TrongLuong"), fDuongKinhTrong = rs.getDouble("DuongKinhTrong"), fDoDay = rs.getDouble("DoDay"), fDauLon = rs.getDouble("DauLon"), fDauNho = rs.getDouble("DauNho"), fDaiDay = rs.getDouble("DaiDay");
							String sDai = formatVN(format.format(fDai)), sRong = formatVN(format.format(fRong)), sDinhluong = formatVN(format.format(fDinhLuong)),
							sTrongluong =formatVN( format.format(fTrongluong)), sDuongKinhTrong = formatVN(format.format(fDuongKinhTrong)), sDoDay = formatVN(format.format(fDoDay)), sDauLon = formatVN(format.format(fDauLon))
							, sDauNho = formatVN(format.format(fDauNho)), sDaiDay = formatVN(format.format(fDaiDay));
							String mau ="";
							/*if (!this.KenhBanHangId.equals("100000")) {
								 sDai =  format.format( fDai); sRong = format.format(fRong) ; sDinhluong =  format.format(fDinhLuong) ;
									sTrongluong =  format.format(fTrongluong) ; sDuongKinhTrong =  format.format(fDuongKinhTrong) ; 
									sDoDay = format.format(fDoDay) ; sDauLon = format.format(fDauLon) 
									; sDauNho =  format.format(fDauNho); sDaiDay =  format.format(fDaiDay) ;

							}*/
						
							
						 	if(!dvkd_fk.trim().equals("100005") || chungloai_fk.trim().equals("100042")){
						 		//đối với sp mới không cần in màu
						 		mau = rs.getString("MAU").trim(); if(mau.toUpperCase().indexOf("NO") >= 0 || mau.toUpperCase().indexOf("KHONG") >= 0 || mau.toUpperCase().indexOf("KHÔNG") >= 0) mau = "";
						 	}
						 	 
							String mauin = rs.getString("MAUIN").trim(); if(mauin.toUpperCase().indexOf("NO") >= 0 || mauin.toUpperCase().indexOf("KHONG") >= 0 || mauin.toUpperCase().indexOf("KHÔNG") >= 0) mauin = "";
							
							//System.out.println("ErpHoaDon : mau = " + mau + ", mauin = " + mauin + ", dvdl_doday = " + rs.getString("DVDL_DODAY"));
							
						
							
							IErpHoaDon_SP sanpham = new ErpHoanDon_SP();
							sanpham.setDvkdId(dvkd_fk);
							sanpham.setId(this.Id);
							sanpham.setIdSanPham(rs.getString("SANPHAM_FK"));
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setTenSanPham(rs.getString("tensp"));
							sanpham.setTen1(rs.getString("TEN1"));
							sanpham.setTen2(rs.getString("TEN2"));
							sanpham.setTen3(rs.getString("TEN3"));
							sanpham.setQuycach(rs.getString("QUYCACH"));
							sanpham.setIn(rs.getString("inrahd"));
							
							sanpham.setDonViTinh(rs.getString("DONVI"));
							sanpham.setDonViTinhEng(rs.getString("DONVI_ENG"));
							sanpham.setDonViInAn(rs.getString("DVINAN"));
							sanpham.setSoluongchuan(rs.getString("SOLUONGCHUAN"));

							sanpham.setQuyDoi(rs.getInt("QUYDOI"));
							
							if(sanpham.getDonViTinh().toUpperCase().equals("M2"))
								sanpham.setSoLuong(Double.parseDouble(format2.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							else
								sanpham.setSoLuong(Double.parseDouble(format.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setDonGiaViet(rs.getDouble("dongiaViet"));
							sanpham.setCTKMID(rs.getString("scheme"));
							
							sanpham.setLoaisp(rs.getString("loaisanpham_fk"));  //Luu Loai San Pham
							String kichthuoc = sDai + rs.getString("DVDL_DAI")+"x"+sRong+rs.getString("DVDL_Rong");
							sanpham.setKichthuoc(kichthuoc);
							String dinhluong = sDinhluong + rs.getString("DVDL_DINHLUONG");
							sanpham.setDinhluong(dinhluong);
							String trongluong = sTrongluong + "kg";
							sanpham.setTrongluong(trongluong);
							//sanpham.setTenXuatHoaDon(rs.getString("tenXHD"));
							
							//Quy cách in hóa đơn
							boolean isDvkdLoi = dvkd_fk.equals("100004");
							boolean isCone = isDvkdLoi && ( sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0 || sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0);
							String qc = "";
							String qcG = "";
							if(isDvkdLoi) {
								
								//Lõi: Đường kính trong x Dài x Độ Dày
								if(fDuongKinhTrong > 0) { qc += " " +  sDuongKinhTrong  + rs.getString("DVDL_DKTRONG"); }
								if(fDauLon > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDauLon  + rs.getString("DVDL_DAULON"); }
								
								if(fRong > 0) { qc += " " +  sRong + rs.getString("DVDL_Rong"); }
								if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI"); }
								if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong  + rs.getString("DVDL_DINHLUONG"); }							
								
								if(fDauNho > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauNho + rs.getString("DVDL_DAUNHO"); }
								if(fDoDay > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDoDay + rs.getString("DVDL_DODAY"); }
								
								if(kenhXuatKhau) {
									//qc += " (" +  sTrongluong + rs.getString("DVDL_TRONGLUONG") + ")";
									if(isCone) { qcG = qc; qc = ""; }
								} else {
									if(isCone) {
										qcG = qc; qc = "";
									}
									
									if(mauin.length() > 0) {
										qc += " " + mauin; 
										}
										
									  if(!chungloai_fk.equals("100031")){
										  qc += " " + (mau.trim().length() >0?mau:"Không màu");
									  }
									  
									  
									  
								}
								//Cộng tổng lượng vào listsanphamCone phục vụ cho lúc in hóa đơn tài chính
								if(isCone) { capNhatSoluongChoListSanPhamCone(sanpham, qcG); }
								
							} else  if(dvkd_fk.equals("100000")) {
								//nhom
							    if(fRong > 0) { qc += " " +  sRong + rs.getString("DVDL_Rong"); }
							    if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI"); }
							    if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong  + rs.getString("DVDL_DINHLUONG"); }
							}else{
							 
										if(fDuongKinhTrong > 0) { 
								  		qc  = " " + sDuongKinhTrong + rs.getString("DVDL_DKTRONG"); }
								  
									    if(fDai > 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " +  sDai  + rs.getString("DVDL_DAI");
									    }
									 
									    if(fRong> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sRong + rs.getString("DVDL_RONG");
									    }
									    
									    if(fDaiDay> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sDaiDay + rs.getString("DVDL_daiday");
									    }
									    
									    if(mau.length()> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + mau ;
									    }
								  //  if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDinhluong + rs.getString("DVDL_DINHLUONG"); }

							}
							System.out.println("[ErpHoaDon] qc = " + qc);
							
							sanpham.setQuyCachGroup(qcG);
						       
						    //Quy doi dvdl
						    dvdl1_fk = rs.getString("dvdl1_fk");
						    String dvdl1ten = rs.getString("DVINAN1").trim();
						    String dvdl1dg = rs.getString("DVDL1DG").trim();
						    double dvdl1sl = rs.getDouble("DVDL1SL");

						    dvdl2_fk = rs.getString("dvdl2_fk");
//						    String dvdl2ten = rs.getString("DVINAN2").trim();
//						    String dvdl2dg = rs.getString("DVDL2DG").trim();
						    double dvdl2sl = rs.getDouble("DVDL2SL");
						    
						    double soluong = rs.getDouble("soluong");
						    double quydoi = 0; 
						    String dvdltenquydoi = "";
						    String dvdldgquydoi = "";
						    //System.out.println("rsDonvi = '" + rs.getString("DONVI").trim() + "', dvdl1ten = " + dvdl1ten + ", dvdl2ten = " + dvdl2ten + ", dvdl1sl = " + dvdl1sl + ", dvdl2sl = " + dvdl2sl );
						    
						    // chỉ làm cho nhôm
						    if(dvkd_fk.equals("100000")){
						    
						    	if(rs.getString("DVDL_FK").trim().equals(rs.getString("dvdlchuan_fk"))) {
						    		// LẤY TRONG DATABASE RA 1 ĐƠN VỊ QUY ĐỔI DUY NHẤT,NẾU CÓ NHIỀU QUY ĐỔI
						    		query=" select soluong1,soluong2,dvdl.DONVI from quycach qc "+ 
						    			  "	 inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=DVDL2_FK "+
						    			  " where sanpham_fk="+rs.getString("sanpham_fk");
						    		//System.out.println("Get Du Lieu : "+query);
						    		ResultSet rsqc=db.get(query);
						    		if (rsqc != null)
						    		{
							    		if(rsqc.next()){
							    			quydoi = soluong * rsqc.getDouble("soluong2")/rsqc.getDouble("soluong1");
							    			dvdltenquydoi=rsqc.getString("DONVI");
							    		}
							    		rsqc.close();
						    		}
						    		
							    	/*quydoi = soluong / dvdl1sl * dvdl2sl;
							    	dvdltenquydoi = dvdl2ten;
							    	dvdldgquydoi = dvdl2dg;*/
							    	//System.out.println("quydoi1 = '" + quydoi );
							    } else {
							    	quydoi = soluong / dvdl2sl * dvdl1sl;
							    	dvdltenquydoi = dvdl1ten;
							    	dvdldgquydoi = dvdl1dg;
							    	//System.out.println("quydoi2 = '" + quydoi );
							    }
						    }
						    
						    
						    
//						    NumberFormat formatter = new DecimalFormat("#,###,###.###");
						    if (this.KenhBanHangId.equals("100000")) {
						    	if(!dvkd_fk.equals("100005")){
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + "}" + qc);
						    	}else{
						    		
						    		// Nếu DVKD: SP MỚI && KH THUỘC NHÓM KH METRO >> LẤY TEN1
						    		if(iskh_METRO <= 0 )
						    		   sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") +" " + qc + "} ");
						    		else 
						    			sanpham.setTenXuatHoaDon(rs.getString("TEN1") +" " + qc + "} ");
						    								    		
						    	}
						    	if(dvdltenquydoi.length() >0){
						    		sanpham.setQuyDoiStr(formatVN(format.format(quydoi)) + " " + dvdltenquydoi);
						    	}
						    	System.out.println(sanpham.getTenXuatHoaDon());
						    	
						    	
						    } else {
						    	if(!dvkd_fk.equals("100005")){
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + "}" + qc);
						    	}else{
						    		
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1")  +" "+ qc + "}");
						    	}
						    	if(dvdldgquydoi.length() >0){
						    		sanpham.setQuyDoiStr(format.format(quydoi) + "" + dvdldgquydoi);
						    	}
						    }
						    
						    String ghichu = rs.getString("ghichu");
						    if(ghichu.trim().length() > 0)
						    {
						    	//System.out.println("__GHI CHU: " + ghichu);
						    	if(ghichu.contains("__"))
						    	{
						    		this.sanpham_ghichu.put(sanpham.getIdSanPham(), ghichu);
						    	}
						    }
						  
							this.listsanpham.add(sanpham);
							
						}
						rs.close();
					}
					catch(Exception er)
					{
						er.printStackTrace();
						//System.out.println("[ErpHoaDon.constructor] SP Exception Message = " + er.getMessage());
					}
				}
			}
		}
		catch(Exception er)
		{
			System.out.println("[ErpHoaDon.constructor] 4.Exception: " + er.getMessage());
		}
		
		String sql = "select pk_seq, ma + ', ' + ten as ten from erp_tiente";
		this.tienteRs = db.get(sql);
		
		System.out.println("___TIEN TE: " + sql);
		
		if(this.NppId.trim().length() > 0 && this.NgayXuaHd.trim().length() > 0)
		{
			String query = " select pk_seq, mahopdong" +
					       " from ERP_HOPDONG " +
						   " where khachhang_fk = '" + this.NppId + "' and tungay <= '" + this.NgayXuaHd + "'" +
						   "       and denngay >= '"  + this.NgayXuaHd + "' ";
			//System.out.println("Lay hop dong: " + query);
			this.hopdongRs = db.get(query);
		}
	
	
	}
	
	private String getDate() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		return dateFormat.format(date);

	}

	
	public String getSohopdong() {
		
		return this.sohopdong;
	}


	public void setSohopdong(String sohopdong) {
		this.sohopdong=sohopdong;	
	}

	//  **** DÙNG CHO HÓA ĐƠN = TIẾNG ANH ****//
	public void hoadon_pdf_XK(String sohoadon) {
		
		String query="";
		try
		{
			 query =  " SELECT isnull(NPP.diachi,'') as  diachi,  ISNULL(HD.SOPO,'')  AS SOPO ,isnull(HD.trangthai,'') as trangthaihd  ,  isnull(NPP.CHOPPHEPSUAHD,'0') as CHOPPHEPSUAHD,HD.PK_SEQ,HD.NGAYXUATHD, " +
							" HD.GHICHU, HD.NOIDUNGCHIETKHAU, ISNULL(HD.PO_MT, 0) AS PO_MT, isnull(HD.NGUOIMUA,'') as nguoimua,HD.TRANGTHAI,HD.NGAYTAO ,HD.NGAYSUA,HD.KYHIEU, " +
							" HD.SOHOADON,HD.HINHTHUCTT, NCC.PK_SEQ as NCCID,  "+
							" NPP.TEN AS NPP,HD.KHACHHANG_FK, NT.TEN AS NGUOITAO,NS.TEN AS NGUOISUA, TONGTIENBVAT, CHIETKHAU, ISNULL(TIENBAOHIEM, 0) AS TIENBAOHIEM, " +
							" ISNULL(TIENVANCHUYEN, 0) AS TIENVANCHUYEN, " +
							" ISNULL(TIENKHUYENMAI, 0) AS TIENKHUYENMAI, ISNULL(VAT, 0) AS VAT, ISNULL(TONGTIENAVAT, 0) AS TONGTIENAVAT, " +
							" ISNULL(HD.HOANTAT, 0) AS HOANTAT, ISNULL(NPP.KENHBANHANG_FK,100000) AS KENHBANHANG_FK , ISNULL(HD.TIENTE_FK,100000) as TIENTE_FK ,TT.MA AS MATT, HD.XUATTHEO, " +
							" isnull(ngayghinhanCN, HD.NGAYXUATHD ) as ngayghinhanCN, isnull(HD.diengiai, '') as diengiai, " +
							" ISNULL(HD.hopdong_fk, 0) AS HOPDONG_FK, ISNULL(HD.TIENCHIETKHAU, 0) AS TIENCHIETKHAU,ISNULL(HD.TIENKHUYENMAI, 0) AS TIENKHUYENMAI, " +
							" ISNULL(HD.TYGIA,1) AS TYGIA, " +
							" ISNULL(HD.KM_GHICHU, '') AS KM_GHICHU, isnull(HD.INVOICE,'') AS INVOICE, isnull(NPP.DienThoai,'') as dienthoai, isnull(NPP.fax,'') as fax  "+
							" FROM ERP_HOADON HD " +
							" LEFT JOIN ERP_KhachHang NPP ON NPP.PK_SEQ = HD.KHACHHANG_FK" +
							" LEFT JOIN ERP_Nhacungcap NCC ON NCC.PK_SEQ = HD.NCC_FK" +
							" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=HD.NGUOITAO "+
							" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=HD.NGUOISUA " +
							" LEFT JOIN ERP_TIENTE TT ON HD.TIENTE_FK=TT.PK_SEQ " +
							" WHERE HD.PK_SEQ="+ sohoadon;
			
			System.out.println("1.init pdf: " + query);
			String trangthai_hd="";
			db = new dbutils();
			ResultSet rs = db.get(query);
			if(rs != null)
			{	
				while (rs.next())
				{
					trangthai_hd=rs.getString("trangthaihd").trim();
					this.Id = sohoadon;
					this.SoPo=rs.getString("SOPO");
					this.ChoPhepSuaGiaHD=rs.getString("CHOPPHEPSUAHD");
					this.NgaySua = rs.getString("ngaysua");
					this.NgayTao = rs.getString("ngaytao");
					this.NgayXuaHd = rs.getString("NGAYXUATHD");
					this.NppId = rs.getString("KHACHHANG_FK")== null ? "":rs.getString("KHACHHANG_FK") ;
					this.NccId = rs.getString("NCCID")== null ? "":rs.getString("NCCID");
					this.NppTen = rs.getString("npp");
					this.SoHoaDon = rs.getString("sohoadon");
					this.TrangThai = rs.getString("trangthai");
					this.Kyhieu = rs.getString("kyhieu");
					this.DiachiNPP=rs.getString("diachi");
					this.HinhThucTT = rs.getString("HINHTHUCTT");
					this.NguoiMuaHang = rs.getString("NGUOIMUA");
					this.POMT = rs.getString("PO_MT");
					this.tygia = rs.getString("TYGIA");
					this.invoice = rs.getString("INVOICE");
					
					this.dienthoai= rs.getString("dienthoai");
					this.fax= rs.getString("fax");
					this.tienteId = rs.getString("TIENTE_FK");
					this.tienteTen = rs.getString("MATT");
					this.chietKhau = rs.getDouble("CHIETKHAU");
					this.tienChietKhau = rs.getDouble("TIENCHIETKHAU");
					this.tienKhuyenMai = rs.getDouble("TIENKHUYENMAI");
					this.KM_ghichu = rs.getString("KM_GHICHU");;
					this.tienchuaVat = rs.getDouble("TONGTIENBVAT");
					this.SoTienBaoHiem = rs.getDouble("TIENBAOHIEM");
					this.freightCost = rs.getDouble("TIENVANCHUYEN");
					this.tienSauCK_Km = rs.getDouble("TONGTIENBVAT") - (this.tienChietKhau > 0 ? this.tienChietKhau : this.chietKhau * rs.getDouble("TONGTIENBVAT") / 100) - this.tienKhuyenMai + this.SoTienBaoHiem + this.freightCost;
					System.out.println("tien sau ck_KM,...."+this.tienSauCK_Km );
					this.VAT = rs.getDouble("VAT") / this.tienSauCK_Km * 100;
					System.out.println("tien VAT,...."+this.VAT);
					//this.VAT = rs.getDouble("VAT") ;
					this.tienVAT = rs.getDouble("VAT");
					this.TienSauVat = rs.getDouble("TONGTIENAVAT");
					
					this.Msg = "";
					this.noidungCK = rs.getString("noidungchietkhau");
					this.GhiChu = rs.getString("ghichu");
					this.HoanTat = rs.getString("HoanTat");
					this.KenhBanHangId=rs.getString("KENHBANHANG_FK");
					this.htXuat = rs.getString("XuatTheo");
					
					this.ngayghinhanCN = rs.getString("ngayghinhanCN");
					this.diengiai = rs.getString("diengiai");
					this.hopdongId = rs.getString("hopdong_fk") == null ? "" : rs.getString("hopdong_fk") ;
					this.sanpham_ghichu = new Hashtable<String, String>();
					
				}
//				boolean kenhXuatKhau = this.KenhBanHangId.equals("100001");
				
				this.ddhIdList = new ArrayList<String>();
				String dvkd_fk = "";
				String kbh_fk = "";
				
			if(this.NppId.trim().length() > 0)
			{
				if(this.htXuat.equals("0"))
				{
					query = " SELECT HOADON_FK, DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " +sohoadon+ " ";
				}
				else
				{
					query = " SELECT HOADON_FK, XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = " + sohoadon;
				}
				
				//System.out.println("2.Khoi tao DDH: " + query);
				
				rs = this.db.get(query);
				String ddhIds = "";
				if(rs != null)
				{
					//this.DonDatHang = new String[20];
					int i= 0;
					while(rs.next())
					{
						if(this.htXuat.equals("0"))
						{
							//this.DonDatHang[i] = rs.getString("DDH_FK");
							ddhIds += rs.getString("DDH_FK") + ",";
							this.ddhIdList.add(rs.getString("DDH_FK"));
						}
						else
						{
							//this.DonDatHang[i] = rs.getString("XUATKHO_FK");
							ddhIds += rs.getString("XUATKHO_FK") + ",";
							this.ddhIdList.add(rs.getString("XUATKHO_FK"));
						}
						i++;
					}
					rs.close();
					
					if(ddhIds.trim().length() > 0)
					{
						ddhIds = ddhIds.substring(0, ddhIds.length() - 1);
						this.DonDatHang = ddhIds;
					}
					else
					{
						this.DonDatHang = "";
					}
				}
				else
				{
					this.setMessage("Khong Lay Duoc Gia tri Don hang");
					return;
				}
				
				
				//Lấy một số thông tin đơn đặt hàng
				query = " SELECT isnull(ddh.paymentterms, '') as paymentterms, isnull(ddh.deliveryterms, '') as deliveryterms, " +
						" isnull(ddh.etd, '') as etd, isnull(ddh.remarks, '') as remarks, isnull(ddh.FreightCost, 0) as FreightCost, " +
						" isnull(ddh.CUSTOMERSPO, '') AS CUSTOMERSPO " +
						" FROM ERP_DONDATHANG ddh " +
						" WHERE ddh.PK_SEQ IN ( " +
						(this.htXuat.equals("0") 
							? " SELECT DDH_FK FROM ERP_HOADON_DDH WHERE HOADON_FK = " + sohoadon + " "
							: " SELECT TOP (1) DONDATHANG_FK FROM ERP_XUATKHO WHERE PK_SEQ IN ( SELECT TOP(1) XUATKHO_FK FROM ERP_HOADON_XUATKHO WHERE HOADON_FK = " +sohoadon + " ) "
						) +
						" ) ";
				//System.out.println(query);
				
				rs = this.db.get(query);
				try {
					rs.next();

					this.paymentTerms = rs.getString("PAYMENTTERMS");
					this.deliveryTerms = rs.getString("DELIVERYTERMS");
					this.etd = rs.getString("ETD");
					this.remarks = rs.getString("REMARKS").trim();
					this.customersPo = rs.getString("CUSTOMERSPO");
					
					rs.close();
				} catch(Exception e) {
					
				}
				
				//System.out.println(" htXuat " + this.htXuat);			 
				if(this.htXuat.equals("0"))
				{
					
					String sql = " select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
								 " from ERP_dondathang ddh "+
								 " where khachhang_fk = '" + this.NppId + "' and ddh.trangthai >= 3  " +
								 " and ddh.pk_seq not in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk in ( select pk_seq from ERP_HOADON where trangthai != 2 ) ) " ;
		
					if(this.Id.trim().length() > 0)
					{
						sql += " union  " +
								"select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
								 "from ERP_dondathang ddh "+
								 "where ddh.pk_seq in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk = '" +sohoadon + "' ) " ;
						if(!trangthai_hd.equals("0")){
							
							sql=	" select distinct ddh.prefix + cast(ddh.pk_seq as varchar(10)) as soCT, ddh.pk_seq as id, ddh.ngaydat " +
									 "from ERP_dondathang ddh "+
									 "where ddh.pk_seq in ( select ddh_fk from ERP_HOADON_DDH where hoadon_fk = '" + sohoadon + "' ) " ;
						}
					}
					
					
					
					//System.out.println("DDH/Xuat kho -- cap nhat :" + sql);
					
					
					
					this.rsddhChuaXuatHD = db.get(sql);
					
					query = " select b.dvkd_fk, b.kbh_fk from erp_hoadon_ddh a inner join erp_dondathang b on a.ddh_fk = b.pk_seq " +
							" where a.hoadon_fk = '" + sohoadon + "'";
					ResultSet rsDdh = db.get(query);
					if (rsDdh != null)
					{
						if(rsDdh.next())
						{
							dvkd_fk = rsDdh.getString("dvkd_fk");
							kbh_fk = rsDdh.getString("kbh_fk");
						}
						rsDdh.close();
					}
				}
				else
				{
					String sql = " select distinct xk.prefix + cast(xk.pk_seq as varchar(10) ) as soCT, xk.pk_seq as id, xk.ngayxuat as ngaydat, 0 as STT " +
								 " from ERP_XUATKHO xk inner join ERP_dondathang ddh on xk.dondathang_fk = ddh.pk_seq " +
								 " where ddh.khachhang_fk = '" + this.NppId + "' and xk.trangthai  in ( 1,4)  " +
								 " and xk.pk_seq not in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk in "
								 + " ( select pk_seq from ERP_HOADON where trangthai != 2 )  ) and xk.ngayxuat >= '2014-01-01'  " ;
					
					if(this.Id.trim().length() > 0)
					{
						sql += " union " +
							   " select distinct prefix + cast(pk_seq as varchar(10) ) as soCT, pk_seq as id, ngayxuat as ngaydat, 0 as STT " +
							   " from ERP_XUATKHO xk where pk_seq in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk = '" + sohoadon + "' ) ";
						
						
						
						
							if(!trangthai_hd.equals("0")){
							
								sql=	 " select distinct prefix + cast(pk_seq as varchar(10) ) as soCT, pk_seq as id, ngayxuat as ngaydat, 0 as STT " +
									     " from ERP_XUATKHO xk where pk_seq in ( select xuatkho_fk from ERP_HOADON_XUATKHO where hoadon_fk = '" + sohoadon + "' )   ";
							}
					}
					
					sql += " order by xk.ngayxuat desc ";
					
					
					//System.out.println("____KHOI TAO XK -- da huy : " + sql);
					
					
					this.rsddhChuaXuatHD = db.get(sql);
					
					query = " select c.dvkd_fk, c.kbh_fk " +
							" from erp_hoadon_xuatkho a inner join erp_xuatkho b on a.xuatkho_fk = b.pk_seq " +
							" inner join erp_dondathang c on b.dondathang_fk = c.pk_seq " +
							" where a.hoadon_fk = '" + sohoadon + "'";
					ResultSet rsDdh = db.get(query);
					if (rsDdh != null)
					{
						if(rsDdh.next())
						{
							dvkd_fk = rsDdh.getString("dvkd_fk");
							kbh_fk = rsDdh.getString("kbh_fk");
						}
						rsDdh.close();
					}
				}
			 }
				
				 				
				query=	" SELECT DISTINCT DDH_SP.SANPHAM_FK,SP.MA, SP.TEN + ' ' +  "+
						" case "+
						"	when substring(LTRIM(isnull(SP.QUYCACH,'')), 1, 1 ) = 'x' then STUFF(LTRIM(isnull(SP.QUYCACH,'')),1,1,'')"+
						"	else isnull(SP.QUYCACH,'') end  as tensp,  "+
						" isnull(SP.TEN, '') as TEN1, isnull(SP.TEN1, isnull(SP.TEN, '')) as TEN2, "+
						" isnull(SP.TEN2, isnull(SP.TEN, '')) as TEN3, isnull(SP.QUYCACH, '') as QUYCACH , DDH_SP.DONGIA, DDH_SP.DONGIAVIET, "+
						" DDH_SP.SOLUONG AS SOLUONG, SP.loaisanpham_fk, isnull(DDH_SP.dvdl_fk,0) as dvdl_fk ,  "+
						" ISNULL(round(QUYCACH.SOLUONG1,0),'1') AS QUYDOI, DONVITINH AS DONVI , '' as scheme,      "+
						" ISNULL(SP.DAI, 0) AS DAI, ISNULL(SP.RONG, 0) AS RONG, ISNULL(SP.DINHLUONG, 0) AS DINHLUONG, "+
						" ISNULL(SP.TRONGLUONG, 0) AS TRONGLUONG, ISNULL(SP.DUONGKINHTRONG, 0) AS DUONGKINHTRONG, "+
						" ISNULL(SP.DODAY, 0) AS DODAY, ISNULL(SP.DAULON, 0) AS DAULON, ISNULL(SP.DAUNHO, 0) AS DAUNHO, "+
						" ISNULL(SP.DAIDAY, 0) AS DAIDAY,      ISNULL(SP.DVDL_DAI, '') AS DVDL_DAI, ISNULL(SP.DVDL_RONG, '') AS DVDL_RONG, "+
						" ISNULL(SP.DVDL_DINHLUONG, '') AS DVDL_DINHLUONG, ISNULL(SP.DVDL_TRONGLUONG, '') AS DVDL_TRONGLUONG, "+
						" ISNULL(SP.DVDL_DKTRONG, '') AS DVDL_DKTRONG, ISNULL(SP.DVDL_DODAY, '') AS DVDL_DODAY, "+
						" ISNULL(SP.DVDL_DAULON, '') AS DVDL_DAULON, ISNULL(SP.DVDL_DAUNHO, '') AS DVDL_DAUNHO, "+
						" ISNULL(SP.DVDL_DAIDAY, '') AS DVDL_DAIDAY,  ISNULL(SP.MAUIN, '') AS MAUIN, ISNULL(SP.MAU, '') AS MAU,      "+
						" SP.ten1 as tenXHD1, SP.ten2 as tenXHD2, isnull(dvdl.DIENGIAIANH,'') as DONVI_ENG, "+
						
						
						" isnull(DVDL.TENINAN, ISNULL(DVDL.DONVI, '')) as DVINAN,        "+
						" isnull(dvdl1.pk_seq, 0) as dvdl1_fk, isnull(dvdl1.DONVI,'') AS DVDL1TEN, "+
						" isnull(dvdl1.DIENGIAI,'') as DVDL1DG, isnull(QUYCACH.SOLUONG1,1) as DVDL1SL, "+
						" isnull(DVDL1.TENINAN, ISNULL(DVDL1.DONVI, '')) as DVINAN1,      "+
						" isnull(dvdl2.pk_seq, 0) as dvdl2_fk, isnull(dvdl2.DONVI,'') AS DVDL2TEN, "+
						" isnull(dvdl2.DIENGIAI,'') as DVDL2DG, isnull(QUYCACH.SOLUONG2,1) as DVDL2SL, "+
						" isnull(DVDL2.TENINAN, ISNULL(DVDL2.DONVI, '')) as DVINAN2,      "+
						
						
						" isnull(DDH_SP.ghichu, '') as ghichu, CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN ISNULL(DDH_SP.inrahd, '1') ELSE '1' END  as inrahd," +
						" SP.DVDL_FK as dvdlCHUAN,      "+
						" sp.dvdl_fk as dvdl_chuan ,  "+
						" isnull(sp.dvkd_fk, 0) as dvkd_fk, "+
						" isnull(sp.nhanhang_fk, 0) as nhanhang_fk, isnull(sp.chungloai_fk, 0) as chungloai_fk, "+
						" isnull(sp.loaisanpham_fk, 0) as loaisanpham_fk, A.SOLUONG as SOLUONGCHUAN, isnull(A.netweight,0) as netweight, isnull(SP.TRONGLUONG,0) as trongluong2 ,  "+
						" DDH_SP.DVDL_FK as hd_dvdl, SP.DVDL_Fk as sp_dvdl  "+
						" FROM ERP_HOADON_SP DDH_SP    "+
						" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=DDH_SP.SANPHAM_FK "+
						" INNER JOIN ERP_HOADON HD ON HD.PK_SEQ= DDH_SP.HOADON_FK "+
						" left JOIN ERP_HOADON_DDH DDH ON HD.PK_SEQ= DDH.HOADON_FK "+
						" left JOIN ERP_DONDATHANG_SP A ON A.DONDATHANG_FK=DDH.DDH_FK AND A.SANPHAM_FK = DDH_SP.SANPHAM_FK "+
						
						
						" LEFT JOIN QUYCACH ON SP.PK_SEQ = QUYCACH.SANPHAM_FK  AND DDH_SP.DVDL_FK = QUYCACH.DVDL2_FK    "+
						" LEFT JOIN DONVIDOLUONG dvdl ON DDH_SP.dvdl_fk = dvdl.pk_seq  "+
						" LEFT JOIN DONVIDOLUONG dvdl1 ON QUYCACH.DVDL1_FK = DVDL1.PK_SEQ "+
						" LEFT JOIN DONVIDOLUONG dvdl2 ON QUYCACH.DVDL2_FK = DVDL2.PK_SEQ "+
						

						" WHERE DDH_SP.SOLUONG > 0 AND DDH_SP.HOADON_FK = '" +sohoadon + "'     "+
						" order by (CASE WHEN HD.KHACHHANG_FK IS NOT NULL THEN ISNULL(DDH_SP.inrahd, '1') ELSE '1' END)  DESC, SP.MA, ISNULL(SP.DUONGKINHTRONG,0), "+
						" ISNULL(SP.DVDL_DKTRONG,''), ISNULL(SP.DAULON,0), ISNULL(SP.DVDL_DAULON,''), ISNULL(SP.DAI,0), "+
						" ISNULL(SP.DVDL_DAI,''), ISNULL(SP.DAUNHO,0), ISNULL(SP.DVDL_DAUNHO,''), ISNULL(SP.DODAY,0), "+
						" ISNULL(SP.DVDL_DODAY,''), DDH_SP.DONGIA ASC, SCHEME ASC  ";

				
			    String nhanhang_fk, chungloai_fk, loaisanpham_fk, dvdl_fk, dvdl1_fk, dvdl2_fk;
			    System.out.println("in HDXK pdf--: "+query);
				rs = this.db.get(query);
				
				
				
				if(rs != null)
				{
					try
					{
					 	NumberFormat format = new DecimalFormat("#,###,###.###");
					 	NumberFormat format2 = new DecimalFormat("#,###,###");
					 
						while(rs.next())
						{
							dvkd_fk = rs.getString("dvkd_fk");
							nhanhang_fk = rs.getString("nhanhang_fk");
							chungloai_fk = rs.getString("chungloai_fk");
							loaisanpham_fk = rs.getString("loaisanpham_fk");
							
							
							double fDai = rs.getDouble("Dai"), fRong = rs.getDouble("RONG"),  fDinhLuong = rs.getDouble("DinhLuong"), fTrongluong = rs.getDouble("TrongLuong"), fDuongKinhTrong = rs.getDouble("DuongKinhTrong"), fDoDay = rs.getDouble("DoDay"), fDauLon = rs.getDouble("DauLon"), fDauNho = rs.getDouble("DauNho"), fDaiDay = rs.getDouble("DaiDay");
							
							String sDai = formatVN(format.format(fDai)), sRong = formatVN(format.format(fRong)), sDinhluong = formatVN(format.format(fDinhLuong)),
							sTrongluong =formatVN( format.format(fTrongluong)), sDuongKinhTrong = formatVN(format.format(fDuongKinhTrong)), sDoDay = formatVN(format.format(fDoDay)), sDauLon = formatVN(format.format(fDauLon))
							, sDauNho = formatVN(format.format(fDauNho)), sDaiDay = formatVN(format.format(fDaiDay));
							String mau ="";
							
							
							
							if (!this.KenhBanHangId.equals("100000")) {
								 sDai =  format.format( fDai); sRong = format.format(fRong) ; sDinhluong =  format.format(fDinhLuong) ;
									sTrongluong =  format.format(fTrongluong) ; sDuongKinhTrong =  format.format(fDuongKinhTrong) ; 
									sDoDay = format.format(fDoDay) ; sDauLon = format.format(fDauLon) 
									; sDauNho =  format.format(fDauNho); sDaiDay =  format.format(fDaiDay) ;

							}
						 
						 	if(!dvkd_fk.trim().equals("100005") || chungloai_fk.trim().equals("100042")){
						 		//đối với sp mới không cần in màu
						 		mau = rs.getString("MAU").trim(); if(mau.toUpperCase().indexOf("NO") >= 0 || mau.toUpperCase().indexOf("KHONG") >= 0 || mau.toUpperCase().indexOf("KHÔNG") >= 0) mau = "";
						 	}
						 	 
							String mauin = rs.getString("MAUIN").trim(); if(mauin.toUpperCase().indexOf("NO") >= 0 || mauin.toUpperCase().indexOf("KHONG") >= 0 || mauin.toUpperCase().indexOf("KHÔNG") >= 0) mauin = "";
							
							
						
							
							IErpHoaDon_SP sanpham = new ErpHoanDon_SP();
							sanpham.setDvkdId(dvkd_fk);
							sanpham.setId(this.Id);
							sanpham.setIdSanPham(rs.getString("SANPHAM_FK"));
							sanpham.setMaSanPham(rs.getString("ma"));
							sanpham.setTenSanPham(rs.getString("tensp"));
							sanpham.setTen1(rs.getString("TEN1"));
							sanpham.setTen2(rs.getString("TEN2"));
							sanpham.setTen3(rs.getString("TEN3"));
							sanpham.setQuycach(rs.getString("QUYCACH"));
							sanpham.setIn(rs.getString("inrahd"));
							
							sanpham.setDonViTinh(rs.getString("DONVI"));
							sanpham.setDonViTinhEng(rs.getString("DONVI_ENG")==null ? "": rs.getString("DONVI_ENG"));
							sanpham.setDonViInAn(rs.getString("DVINAN"));
							sanpham.setSoluongchuan(rs.getString("SOLUONGCHUAN"));

							sanpham.setQuyDoi(rs.getInt("QUYDOI"));
							
							System.out.println(" sp pk_seq :" + rs.getString("SANPHAM_FK"));
					

							String query1 = " SELECT A.DVDL_FK, B.DIENGIAIANH "+
											" FROM ERP_SANPHAM A "+
											" INNER JOIN DONVIDOLUONG B ON A.DVDL_FK= B.PK_SEQ "+
											" WHERE A.PK_SEQ= "+sanpham.getIdSanPham()+" ";
							System.out.println(query1);
							ResultSet kt = db.get(query1);
							if(kt!=null){
								while(kt.next()){
									sanpham.setDonvitinhSP(kt.getString("DIENGIAIANH"));
								}
							}
							
							
							String query2= " SELECT SOLUONG2 FROM QUYCACH WHERE SANPHAM_FK="+sanpham.getIdSanPham()+" AND DVDL2_FK=100003 ";
							ResultSet kt1 = db.get(query2);
							if(kt!=null){
								while(kt1.next()){
									System.out.println(" weight :" + kt1.getString("SOLUONG2"));
									sanpham.setWeight(kt1.getString("SOLUONG2"));
								}
								kt1.close();
							}
							
							
							
							if(!rs.getString("hd_dvdl").equals("100003")){
								sanpham.setWeightQuydoi(String.valueOf(Double.parseDouble(sanpham.getWeight())*rs.getDouble("soluong")));
								
							}
							


							sanpham.setSoLuong(Double.parseDouble(format.format(rs.getDouble("soluong")).replaceAll(",", ""))) ;
							sanpham.setDonGia(rs.getDouble("dongia"));
							sanpham.setDonGiaViet(rs.getDouble("dongiaViet"));
							sanpham.setCTKMID(rs.getString("scheme"));
							
							sanpham.setLoaisp(rs.getString("loaisanpham_fk"));  //Luu Loai San Pham
							String kichthuoc = sDai + rs.getString("DVDL_DAI")+"x"+sRong+rs.getString("DVDL_Rong");
							sanpham.setKichthuoc(kichthuoc);
							String dinhluong = sDinhluong + rs.getString("DVDL_DINHLUONG");
							sanpham.setDinhluong(dinhluong);
							String trongluong = sTrongluong + "kg";
							
							
							sanpham.setTrongluong(trongluong);
							//sanpham.setTenXuatHoaDon(rs.getString("tenXHD"));
							
									
							
							//Quy cách in hóa đơn
							String qc = "";
							String qcG = "";
							
							
							//***** nếu DVKD = NHÔM *****//
							if(dvkd_fk.equals("100000")){
								if(fRong > 0) {qc += " " +  sRong.replaceAll(",", "") + rs.getString("DVDL_Rong"); }
							    if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai.replaceAll(",", "")  + rs.getString("DVDL_DAI"); }
							    if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong.replaceAll(",", "")  + rs.getString("DVDL_DINHLUONG");}
							}
							
							
							//***** nếu DVKD = LÕI *****//
							else if(dvkd_fk.equals("100004")){
								
								//---- nếu là ống  CORE & DTY  (dktrong, dài, đáy)
								if(rs.getString("chungloai_fk").equals("100030") || rs.getString("chungloai_fk").equals("100031")  ){
									
									if(rs.getDouble("DUONGKINHTRONG") > 0){
										qc += format.format(rs.getDouble("DUONGKINHTRONG")).replaceAll(",", "") + rs.getString("DVDL_DKTRONG")+" x ";
									}
									if(rs.getDouble("DAI") > 0){
										qc += format.format(rs.getDouble("DAI")).replaceAll(",", "") + rs.getString("DVDL_DAI") +" x ";
									}
									if(rs.getDouble("DAIDAY") > 0){
										qc += format.format(rs.getDouble("DAIDAY")).replaceAll(",", "") + rs.getString("DVDL_DAIDAY");
									}							
								}	
								
								
								//---- nếu là ống  CONE  (daulon, dài, daunho)
								if(rs.getString("chungloai_fk").equals("100040")  ){
									if(rs.getDouble("DAULON") > 0){
										qc += format.format(rs.getDouble("DAULON")).replaceAll(",", "") + rs.getString("DVDL_DAULON")+" x ";
									}
									if(rs.getDouble("DAI") > 0){
										qc += format.format(rs.getDouble("DAI")).replaceAll(",", "") + rs.getString("DVDL_DAI") +" x ";
									}
									if(rs.getDouble("DAUNHO") > 0){
										qc += format.format(rs.getDouble("DAUNHO")).replaceAll(",", "") + rs.getString("DVDL_DAUNHO");
									}							
								}
								
							}
							
					/*		
							boolean isDvkdLoi = dvkd_fk.equals("100004");
							boolean isCone = isDvkdLoi && (sanpham.getMaSanPham().trim().toUpperCase().indexOf("CONE") >= 0|| sanpham.getMaSanPham().trim().toUpperCase().indexOf("DTY") >= 0);
							
							if(isDvkdLoi) {
								
								//Lõi: Đường kính trong x Dài x Độ Dày
								if(fDuongKinhTrong > 0) { qc += " " +  sDuongKinhTrong  + rs.getString("DVDL_DKTRONG"); }
								if(fDauLon > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDauLon  + rs.getString("DVDL_DAULON"); }
								
								if(fRong > 0) { qc += " " +  sRong + rs.getString("DVDL_Rong"); }
								if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI"); }
								if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong  + rs.getString("DVDL_DINHLUONG"); }							
								
								if(fDauNho > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDauNho + rs.getString("DVDL_DAUNHO"); }
								if(fDoDay > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDoDay + rs.getString("DVDL_DODAY"); }
								
								if(kenhXuatKhau) {
									//qc += " (" +  sTrongluong + rs.getString("DVDL_TRONGLUONG") + ")";
									if(isCone) { qcG = qc; qc = ""; }
								} else {
									if(isCone) {
										qcG = qc; qc = "";
									}
									
									if(mauin.length() > 0) {
										qc += " " + mauin; 
										}
										
									  if(!chungloai_fk.equals("100031")){
										  qc += " " + (mau.trim().length() >0?mau:"Không màu");
									  }
									  
									  
									  
								}
								//Cộng tổng lượng vào listsanphamCone phục vụ cho lúc in hóa đơn tài chính
								if(isCone) { capNhatSoluongChoListSanPhamCone(sanpham, qcG); }
								
							} else  if(dvkd_fk.equals("100000")) {
								//nhom
							    if(fRong > 0) { qc += " " +  sRong + rs.getString("DVDL_Rong"); }
							    if(fDai > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI"); }
							    if(fDinhLuong > 0) { if(qc.length() > 0) { qc += " x"; } qc += " " +  sDinhluong  + rs.getString("DVDL_DINHLUONG"); }
							}else{
							 
										if(fDuongKinhTrong > 0) { 
								  		qc  = " " + sDuongKinhTrong + rs.getString("DVDL_DKTRONG"); }
								  
									    if(fDai > 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sDai  + rs.getString("DVDL_DAI");
									    }
									 
									    if(fRong> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sRong + rs.getString("DVDL_RONG");
									    }
									    
									    if(fDaiDay> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + sDaiDay+ rs.getString("DVDL_daiday");
									    }
									    
									    if(mau.length()> 0) { 
									    	if(qc.length() > 0) { qc += " x"; } qc += " " + mau ;
									    }

							}*/
							System.out.println("[ErpHoaDon] qc = " + qc);
							sanpham.setQuycach(qc);
							sanpham.setQuyCachGroup(qcG);
						       
						    //Quy doi dvdl
						    dvdl1_fk = rs.getString("dvdl1_fk");
						    String dvdl1ten = rs.getString("DVINAN1").trim();
						    String dvdl1dg = rs.getString("DVDL1DG").trim();
						    double dvdl1sl = rs.getDouble("DVDL1SL");

						    dvdl2_fk = rs.getString("dvdl2_fk");
//						    String dvdl2ten = rs.getString("DVINAN2").trim();
//						    String dvdl2dg = rs.getString("DVDL2DG").trim();
						    double dvdl2sl = rs.getDouble("DVDL2SL");
						    
						    double soluong = rs.getDouble("soluong");
						    double quydoi = 0; 
						    String dvdltenquydoi = "";
						    String dvdldgquydoi = "";
						    
						    // chỉ làm cho nhôm
						    if(dvkd_fk.equals("100000")){
						    
						    	if(rs.getString("DVDL_FK").trim().equals(rs.getString("dvdl_chuan"))) {
						    		// LẤY TRONG DATABASE RA 1 ĐƠN VỊ QUY ĐỔI DUY NHẤT,NẾU CÓ NHIỀU QUY ĐỔI
						    		query=" select soluong1,soluong2,dvdl.DONVI from quycach qc "+ 
						    			  "	 inner join DONVIDOLUONG dvdl on dvdl.PK_SEQ=DVDL2_FK "+
						    			  " where sanpham_fk="+rs.getString("sanpham_fk");
						    		
						    		ResultSet rsqc=db.get(query);
						    		if (rsqc != null)
						    		{
							    		if(rsqc.next()){
							    			quydoi = soluong * rsqc.getDouble("soluong2")/rsqc.getDouble("soluong1");
							    			dvdltenquydoi=rsqc.getString("DONVI");
							    		}
							    		rsqc.close();
						    		}						    		
							    	
							    } else {
							    	quydoi = soluong / dvdl2sl * dvdl1sl;
							    	dvdltenquydoi = dvdl1ten;
							    	dvdldgquydoi = dvdl1dg;
							    	
							    }
						    }
						    
						    
						    
						    NumberFormat formatter = new DecimalFormat("#,###,###.###");
						    if (this.KenhBanHangId.equals("100000")) {
						    	if(!dvkd_fk.equals("100005")){
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + "}" + qc);
						    	}else{
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") +" " + qc + "} ");
						    		
						    		
						    	}
						    	if(dvdltenquydoi.length() >0){
						    		sanpham.setQuyDoiStr(formatVN(format.format(quydoi)) + " " + dvdltenquydoi);
						    	}
						    	System.out.println(sanpham.getTenXuatHoaDon());
						    	
						    	
						    } else {
						    	if(!dvkd_fk.equals("100005")){
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1") + "}" + qc);
						    	}else{
						    		
						    		sanpham.setTenXuatHoaDon(rs.getString("tenXHD1")  +" "+ qc + "}");
						    	}
						    	if(dvdldgquydoi.length() >0){
						    		sanpham.setQuyDoiStr(format.format(quydoi) + "" + dvdldgquydoi);
						    	}
						    }
						    
						    String ghichu = rs.getString("ghichu");
						    if(ghichu.trim().length() > 0)
						    {
						    	//System.out.println("__GHI CHU: " + ghichu);
						    	if(ghichu.contains("__"))
						    	{
						    		this.sanpham_ghichu.put(sanpham.getIdSanPham(), ghichu);
						    	}
						    }
						  
							this.listsanpham.add(sanpham);
							
							
							
						}
						rs.close();
					}
					catch(Exception er){er.printStackTrace();}
				}
			}
		}
		catch(Exception er)
		{
			System.out.println("[ErpHoaDon.constructor] 4.Exception: " + er.getMessage());
		}
		
		String sql = "select pk_seq, ma + ', ' + ten as ten from erp_tiente";
		this.tienteRs = db.get(sql);
		
		System.out.println("___TIEN TE: " + sql);
		

		
		// --- lấy mã của hợp đồng ---//
		query="SELECT MAHOPDONG FROM ERP_HOPDONG WHERE PK_SEQ='"+this.hopdongId+"'   ";
		ResultSet kt = db.get(query);
		if(kt!=null){
			try
			{
				while(kt.next()){
					this.sohopdong=kt.getString("MAHOPDONG");
				}
				kt.close();
			}catch(Exception e){
				e.printStackTrace();
			}
		}
	}
}
