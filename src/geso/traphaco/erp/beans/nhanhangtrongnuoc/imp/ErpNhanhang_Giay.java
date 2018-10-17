package geso.traphaco.erp.beans.nhanhangtrongnuoc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Donvi;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.ISanpham;
import geso.traphaco.erp.beans.nhanhangtrongnuoc.ISpDetail;
import geso.traphaco.erp.util.DinhDang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpNhanhang_Giay implements IErpNhanhang_Giay {
	String congtyId;
	String userId;
	String id;
	String ngaynhanhang;
	String ngaychot;
	String sohoadon;
	String loaispId = "";
	String dvthId;
	String lydo;
	String ngayhoadon;
	int is_saungayKS;
	String ghichu;

	ResultSet dvthRs;

	String poId;
	ResultSet poRs;

	String ndnId;
	ResultSet ndnRs;

	String ldnId;
	ResultSet ldnRs;

	String nccId;
	ResultSet nccRs;

	String mahangmuaId;
	int ngayhethan;
	ResultSet mahangmuaRs;

	String diengiai;
	String trangthai;
	String soluongPO;
	String soluongDaNhan;
	String loaihanghoa;
	String tigia;
	String sopoId;
	String muahang_fk1;
	String is_createRs = "";

	
	
	
	List<ISanpham> spList;

	String isPONK;

	String isNCCNK;

	String hdNccId;
	ResultSet hdNccRs;

	String loaimh;
	String isTudong; // Phiếu nhận hàng tự động : 0 , ngược lại : 1

	String msg;
	String pb;
	boolean IsKhoNhanQL_khuvuc = false;
	dbutils db;
	private Utility util;
	private Utility_Kho util_kho = new Utility_Kho();
	String KhoNhanId;
	ResultSet RsKhoNhan;
	// Kho nhận chờ xử lý
	String khoChoXuLy;
	ResultSet RsKhoChoXuLy;
	
	ResultSet hoadonNCCList;

	ResultSet khachhangRs;
	String khachhangId;

	String loaikho = "";
	int tilequydoi_dvdl ; // dành cho phần gia công.
	
	List<KhuVucKho> listKhuVucKho = new ArrayList<KhuVucKho>();
	List<KhuVucKho> listKhuVucKhoCXL = new ArrayList<KhuVucKho>();
	List<IDonvi> listDonvi = new ArrayList<IDonvi>();

	public ErpNhanhang_Giay() {
		this.userId = "";
		this.id = "";
		this.ngaynhanhang = this.getDateTime();
		this.KhoNhanId = "";
		this.ngaychot = "";
		this.sohoadon = "";
		this.diengiai = "";
		this.dvthId = "";
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.loaihanghoa = "0";
		this.loaimh = "";
		this.ngayhoadon = "";
		this.msg = "";
		this.ngayhethan = 0;
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";
		this.lydo = "";

		this.isPONK = "0";
		this.hdNccId = "";

		this.isNCCNK = "0";
		this.pb = "";
		this.tigia = "1";
		this.muahang_fk1 = "";
		this.sopoId = "";
		this.is_createRs = "";

		this.is_saungayKS = 0;
		this.ghichu = "";
		this.isTudong = "0";

		this.khachhangId = "";
		this.loaikho = "0";

		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util = new Utility();
		this.khoChoXuLy = "";
		this.tilequydoi_dvdl = 1;

	}

	public ErpNhanhang_Giay(String id) {
		this.userId = "";
		this.id = id;
		this.ngaynhanhang = this.getDateTime();
		this.ngaychot = "";
		this.sohoadon = "";
		this.dvthId = "";
		this.poId = "";
		this.mahangmuaId = "";
		this.diengiai = "";
		this.trangthai = "";
		this.soluongPO = "";
		this.soluongDaNhan = "0";
		this.ngayhethan = 0;
		this.loaihanghoa = "0";
		this.loaimh = "";
		this.ngayhoadon = "";
		this.msg = "";
		this.ndnId = "";
		this.ldnId = "";
		this.nccId = "";

		this.isPONK = "0";
		this.hdNccId = "";

		this.isNCCNK = "0";
		this.pb = "";
		this.tigia = "1";
		this.muahang_fk1 = "";
		this.sopoId = "";
		this.is_createRs = "";

		this.is_saungayKS = 0;
		this.ghichu = "";
		this.isTudong = "0";

		this.khachhangId = "";
		this.loaikho = "0";
		this.KhoNhanId = "";

		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
		this.util = new Utility();
		this.khoChoXuLy = "";
		this.tilequydoi_dvdl = 1;
	}
	
	public String getNgayhoadon(){
		return this.ngayhoadon;
	}
	public void setNgayhoadon(String ngayhoadon){
		this.ngayhoadon = ngayhoadon;
	}
	
	
	public List<IDonvi> getListDonvi() {
		return listDonvi;
	}

	public void setListDonvi(List<IDonvi> listDonvi) {
		this.listDonvi = listDonvi;
	}

	public int getTilequydoi_dvdl() {
		return tilequydoi_dvdl;
	}

	public void setTilequydoi_dvdl(int tilequydoi_dvdl) {
		this.tilequydoi_dvdl = tilequydoi_dvdl;
	}

	public List<KhuVucKho> getListKhuVucKhoCXL() {
		return listKhuVucKhoCXL;
	}

	public void setListKhuVucKhoCXL(List<KhuVucKho> listKhuVucKhoCXL) {
		this.listKhuVucKhoCXL = listKhuVucKhoCXL;
	}

	public String getKhoChoXuLy() {
		return khoChoXuLy;
	}

	public void setKhoChoXuLy(String khoChoXuLy) {
		this.khoChoXuLy = khoChoXuLy;
	}

	public ResultSet getRsKhoChoXuLy() {
		return RsKhoChoXuLy;
	}

	public void setRsKhoChoXuLy(ResultSet rsKhoChoXuLy) {
		RsKhoChoXuLy = rsKhoChoXuLy;
	}
	public List<KhuVucKho> getListKhuVucKho() {
		return listKhuVucKho;
	}

	public void setListKhuVucKho(List<KhuVucKho> listKhuVucKho) {
		this.listKhuVucKho = listKhuVucKho;
	}

	public String getSopo_Id() {
		return this.sopoId;
	}

	public void setSopo_Id(String sopo_Id) {
		this.sopoId = sopo_Id;
	}

	public String getmuahang_fk() {
		return this.muahang_fk1;
	}

	public void setmuahang_fk(String muahang_fk) {
		this.muahang_fk1 = muahang_fk;
	}

	public String getTigia() {
		return this.tigia;
	}

	public void setTiia(String tigia) {
		this.tigia = tigia;
	}

	public ResultSet getHoadonNCCList() {
		return this.hoadonNCCList;
	}

	public void setHoadonNCCList(ResultSet hoadonnccList) {
		this.hoadonNCCList = hoadonnccList;
	}

	public void setLoaispId(String loaispId) {
		this.loaispId = loaispId;
	}

	public String getLoaispId() {
		return loaispId;
	}

	public String getIsNCCNK() {
		return isNCCNK;
	}

	public void setIsNCCNK(String isNCCNK) {
		this.isNCCNK = isNCCNK;
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

	public String getNgaynhanhang() {
		return this.ngaynhanhang;
	}

	public void setNgaynhanhang(String ngaynhanhang) {
		this.ngaynhanhang = ngaynhanhang;
	}

	public String getDvthId() {
		return this.dvthId;
	}

	public void setDvthId(String dvthid) {
		this.dvthId = dvthid;
	}

	public ResultSet getDvthList() {
		return this.dvthRs;
	}

	public void setDvthList(ResultSet dvthlist) {
		this.dvthRs = dvthlist;
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getSohoadon() {
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
	}

	public String getDonmuahangId() {
		return this.poId;
	}

	public void setDonmuahangId(String dmhid) {
		this.poId = dmhid;
	}

	public ResultSet getDmhList() {
		return this.poRs;
	}

	public void setDmhList(ResultSet dmhlist) {
		this.poRs = dmhlist;
	}

	public String getMahangmuaId() {
		return this.mahangmuaId;
	}

	public void setMahangmuaId(String mhmId) {
		this.mahangmuaId = mhmId;
	}

	public ResultSet getMahangmuaList() {
		return this.mahangmuaRs;
	}

	public void setMahangmuaList(ResultSet mhmlist) {
		this.mahangmuaRs = mhmlist;
	}

	public String getDiengiai() {
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public String getTongSoluongPO() {
		return this.soluongPO;
	}

	public void setTongSoluongPO(String tongslgPO) {
		this.soluongPO = tongslgPO;
	}

	public String getTongSoluongDN() {
		return this.soluongDaNhan;
	}

	public void setTongSoluongDN(String tongslgDN) {
		this.soluongDaNhan = tongslgDN;
	}

	public List<ISanpham> getSpList() {
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList) {
		this.spList = spList;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	// dành cho trường hợp chưa có hề có số mẻ và số lô người dùng nhập
	public void createRs() {

		String sql = "select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'  ";
		// " and congty_fk = '" + this.congtyId + "' and pk_seq in " +
		// this.util.quyen_donvithuchien(this.userId);;
		
		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1' ");
		this.listDonvi.clear();
		if (donvi != null) {
			try {
				while (donvi.next()) {
					this.listDonvi.add((IDonvi) new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		this.dvthRs = db.get(sql);

		this.ndnRs = db.get("select pk_seq, noidung from ERP_NOIDUNGNHANHANG where trangthai = 1 ");

		this.ldnRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') and pk_seq not in (100004,100005) ");

		String cd ="";
		System.out.println("");
		cd = "select PK_SEQ, ma + ', ' + TEN as ten  from ERP_NHACUNGCAP where trangthai = 1 and pk_seq = '"+ this.nccId + "' ";
		this.nccRs = db	.getScrol(cd);
		System.out.println("NPP:"+cd);
		

		// Kiểm tra kho nhận có phải Kho ký gửi KH/Dự trữ KH không
		if (this.KhoNhanId.trim().length() > 0) {
			String command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN where KHOTT_FK = "+ this.KhoNhanId;
			ResultSet rss = this.db.get(command);
			List<KhuVucKho> listK = new ArrayList<KhuVucKho>();
			try{
				if(rss !=null){
					while (rss.next()){
						KhuVucKho temp = new KhuVucKho();
						temp.setId(rss.getString("PK_SEQ"));
						temp.setTen(rss.getString("TEN"));
						listK.add(temp);
					}
					rss.close();
				}
				this.listKhuVucKho = listK;
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		// lấy khu vực kho chờ xử lý
		if (this.khoChoXuLy.trim().length() > 0) {
			String command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN where KHOTT_FK = "+ this.khoChoXuLy;
			ResultSet rss = this.db.get(command);
			List<KhuVucKho> listK = new ArrayList<KhuVucKho>();
			try{
				if(rss !=null){
					while (rss.next()){
						KhuVucKho temp = new KhuVucKho();
						temp.setId(rss.getString("PK_SEQ"));
						temp.setTen(rss.getString("TEN"));
						listK.add(temp);
					}
					rss.close();
				}
				this.listKhuVucKhoCXL = listK;
			} catch (Exception ex){
				ex.printStackTrace();
			}
		}
		

		this.khachhangRs = db
				.get("select PK_SEQ, MA + ', ' +TEN AS TEN from KHACHHANG where TRANGTHAI = '1' AND CONGTY_FK = "
						+ this.congtyId + " ");

		if (this.nccId.trim().length() > 0) {
			String query1 = "";

			// ---lấy danh sách số hoá đơn ncc
			
			if(this.loaimh.equals("0")){
				query1 =  " select distinct hd.pk_seq, (hd.sohoadon) sohoadon   \n"
						+ " from ERP_PARK p       \n" 
						+ " inner join ERP_HOADONNCC hd on p.pk_seq=hd.park_fk     \n"
						+ " inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK  \n"
						+ " where hd.LOAIHD = "
						+ this.loaimh + " AND p.trangthai in ( '1', '2' )   and p.ncc_fk=" + this.nccId + " ";
			}else{
				query1 =  " select distinct hd.pk_seq, (hd.sohoadon) + N' -- SỐ MUA HÀNG NỘI ĐỊA: ' + cast(mh.PK_SEQ as nvarchar(50)) sohoadon   \n"
						+ " from ERP_PARK p       \n" 
						+ " inner join ERP_HOADONNCC hd on p.pk_seq=hd.park_fk     \n"
						+ " inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK  \n"
						+ " inner join ERP_MUAHANG mh on hd_dmh.MUAHANG_FK= mh.PK_SEQ  \n" 
						+ " where hd.LOAIHD = " + this.loaimh + " AND p.trangthai in ( '1', '2' ) \n"
						+ " and p.ncc_fk=" + this.nccId + " "; //and mh.loaihanghoa_fk = " + this.loaihanghoa;
				
			}
			System.out.println(" ds hoa don :" + query1);

			this.hdNccRs = db.get(query1);

			// tạo ra các sản phẩm resultSet
			if (this.id.trim().length() <= 0) {
				CreateRsSanPham();
				// xét xem có phải là đơn gia công không, để quy đổi thành đơn vị chuẩn.
				ConvertDHG();
			}
			
			// Cho chọn kho nhận: cố gắng được kho có chứa sản phẩm đó. 15/06/2016
			query1  = " select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1' and PK_SEQ in " +
					  " (select KHOTT_FK from ERP_KHOTT_SANPHAM where SANPHAM_FK in (select distinct b.SANPHAM_FK from ERP_PARK p   " +
					  " inner join ERP_HOADONNCC a on p.pk_seq=a.park_fk " +
					  " inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK  " +
					  " where a.pk_seq= "+this.hdNccId.trim()+" ))";
			System.out.println("Du lieu : "+query1);
			this.RsKhoNhan = db.get(query1);
			this.RsKhoChoXuLy = db.get(query1);
		}

	}
	
	//nếu 2 đơn vị trùng nhau = true
	//nếu 2 đơn vị khác nhau = false
	private List<Double> CheckDonViDoLuong(List<String> sanpham_fk,List<String> dvdl_chuan ,List<String> dvdl ){
		List<Double> tile = new ArrayList<Double>();
		try{
			// lấy đơn vị chuẩn
			String sql = " select SANPHAM_FK, DVT, b.DVDL_FK from ERP_HOADONNCC_DONMUAHANG  a inner join ERP_SANPHAM b on "
					     + " a.sanpham_fk = b.PK_SEQ  where HOADONNCC_FK ="+ this.hdNccId;
			
			
			ResultSet rs = this.db.get(sql);
			if(rs!=null){
				while(rs.next()){
					dvdl_chuan.add(rs.getString("DVDL_FK"));
					sanpham_fk.add(rs.getString("SANPHAM_FK"));
					dvdl.add(rs.getString("DVT"));
				}
				rs.close();
			}
					
			
			for(int i=0; i< sanpham_fk.size(); i++){
				
				if(dvdl.get(i).equals(dvdl_chuan.get(i))){
					double soluongtl=1;
					tile.add(soluongtl);
				} else{
					
					sql =   " select isnull( CAST(SOLUONG2 AS float) / CAST(SOLUONG1 AS float),1) as tile from QUYCACH " +
							" where DVDL1_FK = "+dvdl_chuan.get(i)+" and DVDL2_FK ="+dvdl.get(i) 
							+" and SANPHAM_FK = "+sanpham_fk.get(i);
					System.out.println("Quy doi chuan : "+ sql);
					rs = this.db.get(sql);
					// check truong hop, khong dc khai quy doi.
					boolean check = false;
					
					if(rs!=null){
						if(rs.next()){
							tile.add(rs.getDouble("tile"));
							check = true;
						}
						rs.close();
					}
					if(check == false){
						double soluongtl=1;
						tile.add(soluongtl);
					}
				}
			}
			
			return tile;
		} catch (Exception ex){
			ex.printStackTrace();
			return tile;
		}
	}
	
	// thay đổi số lượng và đơn vị.
	private void ConvertDHG() {
		
		List<String> sanpham_fk = new ArrayList<String>();
		List<String> dvdl_chuan = new ArrayList<String>();
		List<String> dvdl = new ArrayList<String>();
		
		List<Double> tile = CheckDonViDoLuong(sanpham_fk, dvdl_chuan, dvdl);
		
		for (int i = 0; i < this.spList.size(); i++) {
			ISanpham sp = this.spList.get(i);
			
			for(int j=0; j< sanpham_fk.size(); j++){
				
				if(sanpham_fk.get(j).equals(sp.getId())){
					if(tile.get(j) != 1){
						xuLySoluongTungSp(sp, tile.get(j), dvdl_chuan.get(j),i);
						break;
					}
				}
			}
		}

	}
	
	public static void main(String[] agrs){
		double a = 25.000/0.001;
		System.out.println("a"+a);
	}
	// chỗ này xem xét từng sản phẩm và quy đổi qua đơn vị chuẩn theo tỉ lệ đã tính được
	private void xuLySoluongTungSp(ISanpham sp,double tile,String dvdl_chuan, int index){
		DecimalFormat formatter = new DecimalFormat("###,###.####");
		DecimalFormat formatterVND = new DecimalFormat("###,###,###.####");
		
		DecimalFormat formatter_9sole = new DecimalFormat("######.##########");
		
		
		//tile=Double.parseDouble(formatter_9sole.format(tile));
		sp.setTiLeQuyDoiDv(tile);
		//tile = 1/tile;
		 
		System.out.println("ti le new : "+tile);
	
		double soluong = Double.parseDouble(sp.getSoluongMaxNhan().replaceAll(",", ""))  / tile;
		sp.setSoluongMaxNhan(formatter.format(soluong));

		soluong = Double.parseDouble(sp.getSoluongDaNhan().replaceAll(",", ""))/ tile; 
		sp.setSoluongDaNhan(formatter.format(soluong));

		soluong = Double.parseDouble(sp.getSoluongdat().replaceAll(",", ""))/ tile;
		sp.setSoluongdat(formatter.format(soluong));

		soluong =  Double.parseDouble(sp.getSoluongnhan().replaceAll(",", "")) / tile;
		System.out.println("so luong nhan : "+sp.getSoluongnhan());
		sp.setSoluongnhan(formatter.format(soluong));

		double dongia = Double.parseDouble(sp.getDongia().replaceAll(",", "")) *tile;
		
		dongia = Math.round (dongia * 1000.0) / 1000.0; 
		
		sp.setDongia(formatterVND.format(dongia));
		sp.setDongiaViet(formatterVND.format(dongia));
		// set dvdl
		sp.setDvdl(dvdl_chuan);

		List<ISpDetail> listct = sp.getSpDetail();

		for (int j = 0; j < listct.size(); j++) {
			ISpDetail spCon = sp.getSpDetail().get(j);
			soluong = Double.parseDouble(spCon.getSoluong().replaceAll(",", "")) / tile;
			spCon.setSoluong(formatter.format(soluong));

			soluong = Double.parseDouble(spCon.getSoluong().replaceAll(",", "")) / tile;
			spCon.setSoluongmax(formatter.format(soluong));
			
			spCon.setDongiaLo(formatterVND.format(dongia));
			listct.set(j, spCon);
		}
		sp.setSpDetail(listct);
		this.spList.set(index, sp);
	}
	// Dành cho trường hợp tạo ra số mẻ và số lô
	public void createRs1() {

		String sql = "select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'  ";
		// " and congty_fk = '" + this.congtyId + "' and pk_seq in " +
		// this.util.quyen_donvithuchien(this.userId);;
		this.dvthRs = db.get(sql);
		
		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1' ");
		this.listDonvi.clear();
		if (donvi != null) {
			try {
				while (donvi.next()) {
					this.listDonvi.add((IDonvi) new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		

		this.ndnRs = db.get("select pk_seq, noidung from ERP_NOIDUNGNHANHANG where trangthai = 1 ");

		this.ldnRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') and pk_seq not in (100004,100005) ");

		String cd ="";
		cd = "select PK_SEQ, ma + ', ' + TEN as ten  from ERP_NHACUNGCAP where trangthai = 1 and pk_seq = '"+ this.nccId + "' ";
		this.nccRs = db	.getScrol(cd);

		System.out.println("NPP:"+cd);
		// Cho chọn kho nhận: không lấy Kho NCC & Kho ký gửi NCC & Kho TDV
		this.RsKhoNhan = db.get("select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1'");

		// Kiểm tra kho nhận có phải Kho ký gửi KH/Dự trữ KH không
		if (this.KhoNhanId.trim().length() > 0) {
			String command = "SELECT ISNULL(LOAI,0) LOAI FROM ERP_KHOTT WHERE PK_SEQ = " + this.KhoNhanId + " ";
			ResultSet rss = db.get(command);
			try {
				if (rss.next()) {
					this.loaikho = rss.getString("LOAI");
				}
				rss.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
			command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN where KHOTT_FK = "+ this.KhoNhanId;
			rss = this.db.get(command);
			List<KhuVucKho> listK = new ArrayList<KhuVucKho>();
			try{
				if(rss !=null){
					while (rss.next()){
						KhuVucKho temp = new KhuVucKho();
						temp.setId(rss.getString("PK_SEQ"));
						temp.setTen(rss.getString("TEN"));
						listK.add(temp);
					}
					rss.close();
				}
				this.listKhuVucKho = listK;
			} catch (Exception ex){
				ex.printStackTrace();
			}
			// lấy khu vực kho cho phần kho chờ xử lý
			command = "select PK_SEQ, MA+'-'+ TEN as TEN from ERP_BIN where KHOTT_FK = "+ this.khoChoXuLy;
			rss = this.db.get(command);
			List<KhuVucKho> listKK = new ArrayList<KhuVucKho>();
			try{
				if(rss !=null){
					while (rss.next()){
						KhuVucKho temp = new KhuVucKho();
						temp.setId(rss.getString("PK_SEQ"));
						temp.setTen(rss.getString("TEN"));
						listKK.add(temp);
					}
					rss.close();
				}
				this.listKhuVucKhoCXL = listKK;
			} catch (Exception ex){
				ex.printStackTrace();
			}
			

		}

		this.khachhangRs = db
				.get("select PK_SEQ, MA + ', ' +TEN AS TEN from KHACHHANG where TRANGTHAI = '1' AND CONGTY_FK = "
						+ this.congtyId + " ");

		if (this.nccId.trim().length() > 0) {
			String query1 = "";

			// ---lấy danh sách số hoá đơn ncc
			
			if(this.loaimh.equals("0")){
				query1 =  " select distinct hd.pk_seq, (hd.sohoadon) sohoadon   \n"
						+ " from ERP_PARK p       \n" 
						+ " inner join ERP_HOADONNCC hd on p.pk_seq=hd.park_fk     \n"
						+ " inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK  \n"
						+ " where hd.LOAIHD = "
						+ this.loaimh + " AND p.trangthai in ( '1', '2' )   and p.ncc_fk=" + this.nccId + " ";
			}else{
				query1 =  " select distinct hd.pk_seq, (hd.sohoadon) + N' -- SỐ MUA HÀNG NỘI ĐỊA: ' + cast(mh.PK_SEQ as nvarchar(50)) sohoadon   \n"
						+ " from ERP_PARK p       \n" 
						+ " inner join ERP_HOADONNCC hd on p.pk_seq=hd.park_fk     \n"
						+ " inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK  \n"
						+ " inner join ERP_MUAHANG mh on hd_dmh.MUAHANG_FK= mh.PK_SEQ  \n" 
						+ " where hd.LOAIHD = " + this.loaimh + " AND p.trangthai in ( '1', '2' ) \n"
						+ " and p.ncc_fk=" + this.nccId + " and mh.loaihanghoa_fk = " + this.loaihanghoa;
				
			}
			System.out.println(" ds hoa don :" + query1);

			this.hdNccRs = db.get(query1);
			
			// Cho chọn kho nhận: cố gắng được kho có chứa sản phẩm đó. 15/06/2016
			query1  = " select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1' and PK_SEQ in " +
					  " (select KHOTT_FK from ERP_KHOTT_SANPHAM where SANPHAM_FK in (select distinct b.SANPHAM_FK from ERP_PARK p   " +
					  " inner join ERP_HOADONNCC a on p.pk_seq=a.park_fk " +
					  " inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK  " +
					  " where a.pk_seq= "+this.hdNccId.trim()+" ))";
			
			this.RsKhoNhan = db.get(query1);
			this.RsKhoChoXuLy = db.get(query1);
		}
		

	}
	public void CreateRsSanPham(){
		String query1 = "";
		
		// ----- chọn hoá đơn thì load thông tin dơn hàng
		if (this.hdNccId.length() > 0) {
			//Lấy ngày hóa đơn để so sánh với ngày nhận hàng . Ngày nhận hàng phải >= ngày hóa đơn
			query1 = "SELECT ISNULL(NGAYGHINHAN,'') AS ngayhoadon FROM ERP_PARK WHERE PK_SEQ  = (SELECT PARK_FK FROM ERP_HOADONNCC WHERE PK_SEQ = "+this.hdNccId+")" ;
			ResultSet rsGetNgayHoaDon = this.db.get(query1);
			if(rsGetNgayHoaDon != null){
				try {
					while (rsGetNgayHoaDon.next()){
						this.ngayhoadon = rsGetNgayHoaDon.getString("ngayhoadon");
					}
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			if(this.loaimh.equals("0")){ // NHẬP KHẨU
				query1 =  " select isnull(p.tigia, 1) as tigia,  mh.PK_SEQ as mhId, " +
						  " mh.PK_SEQ as sopo, a.pk_seq as hdncc  \n"
						+ " from ERP_PARK p   \n" 
						+ " inner join ERP_HOADONNCC a on p.pk_seq=a.park_fk  \n"
						+ " inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK  \n"
						+ " inner join ERP_MUAHANG mh on b.MUAHANG_FK= mh.PK_SEQ \n" 
						+ " where a.pk_seq='"	+ this.hdNccId.trim() + "'   ";
			}else{
				query1 =  " select isnull(p.tigia, 1) as tigia,  mh.PK_SEQ as mhId, mh.PK_SEQ as sopo, a.pk_seq as hdncc  \n"
					+ " from ERP_PARK p   \n" 
					+ " inner join ERP_HOADONNCC a on p.pk_seq=a.park_fk  \n"
					+ " inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK  \n"
					+ " inner join ERP_MUAHANG mh on b.MUAHANG_FK= mh.PK_SEQ \n" + " where a.pk_seq='"
					+ this.hdNccId.trim() + "'   ";						
			}

			ResultSet tmp = db.get(query1);
			if (tmp != null) {
				try {
					while (tmp.next()) {
						this.tigia = tmp.getString("tigia");
						this.muahang_fk1 = tmp.getString("mhId");
						this.sopoId = tmp.getString("sopo");
						this.hdNccId = tmp.getString("hdncc");
						this.poId = tmp.getString("mhId");
					}
					tmp.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			// nhận hàng nhập khẩu cột muahang_fk trong erp_nhanhang, erp_nhanhang_sanpham, erp_nhanhang_sp_chitiet link với pk_seq bảng ERP_NHAPKHONHAPKHAU
			if(this.loaimh.equals("1")){ // TRONG NƯỚC
				query1 = " SELECT distinct max(hdsp.sott) as sott  ,mh.PK_SEQ, hdsp.MUAHANG_FK MUAHANG_FK, mh.SOPO SOPO,  \n"
						+ "   sp.PK_SEQ as SANPHAM_FK, \n"
						+ "   sp.MA as SPMA , \n"
						+ "   sp.TEN + '-' + isnull(' marquette: ' + isnull(m.MA,''),'') as spten,   \n"
						// thêm marquette
						+ "   m.PK_SEQ as idmarquette,   \n"
						// phân biệt kiểm định vs không kiểm định trên từng sản phẩm
						+ "  isnull(sp.BATBUOCKIEMDINH,'0')  as kiemdinh, \n"
						+ "   isnull(hdsp.DVT,'NA') as dvdlTen, 0 as dungsai , isnull(p.TIGIA,1)as tigiaquydoi, isnull(p.tiente_fk, 100000) as tienteId,  isnull(sp.hansudung,0) as hansudung , \n"
						+ "   (mhsp.DONGIA)*isnull(p.TIGIA,1) as dongia,               \n"
						+ "   isnull(hdsp.NGAYNHANDK,'') ngaynhan, sum(hdsp.SOLUONG) as soluonghd,      \n"
						+ "   isnull((select SUM(nhsp.SOLUONGNHAN *nhsp.TILEQUYDOI_DOLUONG)       \n"
						+ "   from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK \n"
						+ "   where nh.TRANGTHAI not in (3,4) and nhsp.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
						+ "        and nhsp.SANPHAM_FK=hdsp.sanpham_fk and nhsp.DONGIA = hdsp.DONGIA  \n"
						+ "   ),0) as soluongdanhan, '" + this.KhoNhanId
						+ "' khonhan,  (select k.ten from KHO k where pk_seq = '" + this.KhoNhanId
						+ "'  ) khoten \n" + " FROM ERP_HOADONNCC_DONMUAHANG hdsp   \n"
						+ " inner join ERP_HOADONNCC hd on hd.pk_seq= hdsp.HOADONNCC_FK \n"
						+ " inner join ERP_MUAHANG mh on hdsp.MUAHANG_FK= mh.pk_seq     \n"
						+ " inner join ERP_MUAHANG_SP mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK and hdsp.SANPHAM_FK = mhsp.SANPHAM_FK and mhsp.dongia = hdsp.dongia \n"
						+ " inner join ERP_PARK p on hd.park_fk= p.pk_seq      	\n"
						+ " left join ERP_SANPHAM  sp on hdsp.SANPHAM_FK= sp.PK_SEQ    \n"
						// thêm marquette
						+ " left join MARQUETTE  m on m.SANPHAM_FK= sp.PK_SEQ and m.PK_SEQ = mhsp.IDMARQUETTE \n"
						
						+ " WHERE hd.pk_seq = " + this.hdNccId + " \n"
						+ " group by hdsp.MUAHANG_FK, hdsp.MUAHANG_FK, mh.PK_SEQ, mh.LOAIHANGHOA_FK, isnull(hdsp.DVT,'NA'), isnull(p.TIGIA,1), isnull(p.tiente_fk, 100000), \n"
						+ " isnull(sp.hansudung,0), isnull(hdsp.NGAYNHANDK,''), hd.pk_seq, hdsp.SANPHAM_FK, sp.PK_SEQ, \n"
						+ " sp.ma, sp.ten, mhsp.DONGIA, mh.SOPO,  m.PK_SEQ, m.MA, sp.kiemtradinhtinh, sp.kiemtradinhluong, sp.BATBUOCKIEMDINH, hdsp.DONGIA \n"
						+ " having \n" 
						+ "  sum(hdsp.SOLUONG)  - \n"
						+ "  isnull((select SUM(nhsp.SOLUONGNHAN*nhsp.TILEQUYDOI_DOLUONG)   \n"
						+ "     from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK  \n"
						+ "     where nh.TRANGTHAI not in (3,4) and nhsp.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
						+ "          and nhsp.SANPHAM_FK=hdsp.sanpham_fk and nhsp.DONGIA = hdsp.DONGIA \n"
						+ "   ),0) > 0  order by sott";
			}
			System.out.println("ds sanpham :" + query1);

			ResultSet rs = db.get(query1);
			List<ISanpham> spList = new ArrayList<ISanpham>();
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			//NumberFormat formatter2 = new DecimalFormat("#,###,###.###");
			
			NumberFormat formatter_4le = new DecimalFormat("#,###,###.####");
			
			if (rs != null) {
				ISanpham sp = null;
				try {
					while (rs.next()) {
						sp = new Sanpham();

						sp.setMuahang_fk(rs.getString("MUAHANG_FK"));
						sp.setSoPO(rs.getString("SOPO"));
						sp.setMa(rs.getString("spma"));
						sp.setId(rs.getString("SANPHAM_FK"));
						sp.setDiengiai(rs.getString("spten"));
						//thêm idmarquette
						sp.setIdmarquette(rs.getString("idmarquette"));
						
						sp.setSoluongdat(DinhDang.dinhdangkho(rs.getDouble("soluonghd")));
						sp.setKhonhanId(rs.getString("khonhan"));
						System.out.println("khonhan ID " + sp.getKhonhanId());
						sp.setDvdl(rs.getString("dvdlTen"));
						sp.setSoluongMaxNhan(
								DinhDang.dinhdangkho(rs.getDouble("soluonghd") - rs.getDouble("soluongdanhan")));
						sp.setSoluongDaNhan(DinhDang.dinhdangkho(rs.getDouble("soluongdanhan")));
						sp.setHansudung(rs.getString("hansudung"));
						sp.setNgaynhandukien(rs.getString("ngaynhan")==null?"":rs.getString("ngaynhan"));
						sp.setKhonhanTen(rs.getString("khoten"));
						sp.setDongia(formatter_4le.format(rs.getDouble("dongia")));
						sp.setDongiaViet(formatter_4le.format(rs.getDouble("dongia")));
						sp.setTigiaquydoi(rs.getString("tigiaquydoi"));
						sp.setTiente(rs.getString("tienteId"));
						sp.setSoluongnhan("");
						sp.setthanhtien("");
						// thêm trường kiểm định
						sp.setIsKiemDinh(rs.getString("kiemdinh"));
						
						if(this.id.trim().length() <= 0)
						{
							if(this.loaimh.equals("0") || this.loaimh.equals("1")){
								sp.setSoluongnhan(
										DinhDang.dinhdangkho(rs.getDouble("soluonghd") - rs.getDouble("soluongdanhan")));
								String command = "";
							 
									command = " select d.sott,SANPHAM_FK, SOLUONG,SOLO, NGAYSANXUAT, NGAYHETHAN, DONGIA, " +
									  " d.soluong - isnull((select SUM(ct.soluong* nhsp.TILEQUYDOI_DOLUONG) from ERP_NHANHANG_SP_CHITIET ct " +
									  " inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ " +
									  " inner join ERP_NHANHANG_SANPHAM nhsp on nhsp.NHANHANG_FK = nh.PK_SEQ "+
									  " where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO  " +
									  " and nh.hdNCC_fk = d.HOADONNCC_FK and d.DONGIA = nhsp.DONGIA group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, " +
									  " d.NGAYSANXUAT as ngaysanxuat, d.NGAYHETHAN, " +
									  " isnull(d.nsx_fk,0) nsx_fk,isnull((select isnull(ten,'') from erp_nhasanxuat q where d.nsx_fk=q.pk_seq),'') nsxten, isnull(d.marrquet,'') marrquet  " +
									  " from  ERP_HOADONNCC_DONMUAHANG d  " +
									  " where  d.hoadonncc_fk = "+this.hdNccId+" and d.sanpham_fk = "+sp.getId()+ "and round(d.DONGIA,4) =" + sp.getDongia().replaceAll(",", "")+
									  "and d.soluong <> 0 and d.MUAHANG_FK = "+ sp.getMuahang_fk()  +" "
									  		+ " order by d.sott ";
								 

								System.out.println("Khoi tao san pham con 2 : " + command);
								ResultSet spConRs = db.get(command);

								List<ISpDetail> spConList = new ArrayList<ISpDetail>();
								ISpDetail spCon = null;
								if (spConRs != null) {
									while (spConRs.next()) {
										String spConMa = spConRs.getString("sanpham_fk");
										String solo = spConRs.getString("solo");
										String soluong = DinhDang.dinhdangkho(spConRs.getDouble("soluongmax"));
										
										String ngaysanxuat = spConRs.getString("ngaysanxuat");
										String ngayhethan = spConRs.getString("ngayhethan");
									
										spCon = new SpDetail(spConMa, solo,"", soluong, ngaysanxuat, ngayhethan);
										spCon.setSoluongmax(DinhDang.dinhdangkho(spConRs.getDouble("soluongmax")));
										spCon.setDongiaLo(formatter.format(spConRs.getDouble("dongia")));
										
										spCon.setNsxId(spConRs.getString("nsx_fk"));
										spCon.setNsxTen(spConRs.getString("nsxten"));
										spCon.setMarrquet(spConRs.getString("marrquet"));
										
										//-- khoi tao cho so thung=1  va cho sua : YEU CAU 28-10-2017
										spCon.setSothung("1");
										
										spConList.add(spCon);
									}
									spConRs.close();
								}
								sp.setSpDetail(spConList);
							}
						}
						spList.add(sp);
					}
					rs.close();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			this.spList = spList;
			LocLoaiSanPham();
			//l

		}
	
	}

	public void init() {
		
		String query = "";
		
				query = "SELECT isnull(A.ISTUDONG,0) as  ISTUDONG   ,a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYCHOT, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen,   \n"
				+ " 	   c.PK_SEQ as PoId, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK, c.LOAI as loaimh, a.KHONHAN_FK,a.khochoxuly_fk, a.KHACHHANGKYGUI_FK, \n"
				+ "        isnull( ( select isnull(NGUONGOCHH, 'TN') from ERP_MUAHANG where PK_SEQ = c.pk_seq ), 'TN') as NGUONGOCHH, hdNCC_fk,  a.muahang_fk as muahang_fk, isnull(a.tigia, '1') as tigia, c.sopo as sopo,isnull(p.ngayghinhan,'') as ngayhoadon \n"
				+ " FROM erp_nhanhang a " 
				 + "  left join erp_hoadonncc hd on a.hdncc_fk = hd.pk_seq \n"
				 + " left join erp_park p on hd.park_fk = p.pk_seq \n" 
				 + " inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"
				+ " left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ \n"
				+ " left join DonTraHang d on a.TRAHANG_FK = d.PK_SEQ \n" + " WHERE a.pk_seq = '" + this.id + "' ";
		
		System.out.println("Init : " + query);

		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.ngaychot = rs.getString("ngaychot");
					this.sohoadon = rs.getString("sohoadon");
					this.dvthId = rs.getString("dvthId");
					this.poId = rs.getString("PoId");
					this.diengiai = rs.getString("diengiai");
					this.ndnId = rs.getString("noidungnhan_fk");
					this.loaihanghoa = rs.getString("loaihanghoa_fk");
					this.ldnId = rs.getString("NoiDungNhap_fk");
					this.trangthai = rs.getString("trangthai");
					this.nccId = rs.getString("NCC_KH_FK") == null ? "" : rs.getString("NCC_KH_FK");
					this.loaimh = rs.getString("loaimh");
					if(this.loaimh.trim().equals("2"))
					{
						this.ldnId = "100000";
					}
					this.isPONK = rs.getString("NGUONGOCHH").equals("TN") ? "0" : "1";
					this.hdNccId = rs.getString("hdNCC_fk") == null ? "" : rs.getString("hdNCC_fk");
					this.ngayhoadon = rs.getString("ngayhoadon");
					this.isTudong = rs.getString("ISTUDONG");

					this.muahang_fk1 = rs.getString("muahang_fk") == null ? "" : rs.getString("muahang_fk");
					this.tigia = rs.getString("tigia");
					this.sopoId = rs.getString("sopo");

					if (this.KhoNhanId.trim().length() <= 0) {
						this.KhoNhanId = rs.getString("KHONHAN_FK") == null ? "" : rs.getString("KHONHAN_FK");
						this.khachhangId = rs.getString("KHACHHANGKYGUI_FK") == null ? ""
								: rs.getString("KHACHHANGKYGUI_FK");
						this.khoChoXuLy = rs.getString("KHOCHOXULY_FK") == null ? "" : rs.getString("KHOCHOXULY_FK");
					}
				}
				rs.close();
			} catch (Exception e) {
				System.out.println("1.Exception: " + e.getMessage());
			}
		}

		this.initSanPham();

		this.is_createRs = "1";

		this.createRs();
	}

 
	private void initSanPham() {

		NumberFormat formatter = new DecimalFormat("#,###,###.###");

		String query = " ";
		if(!this.loaimh.equals("0"))
		{
			query = "SELECT distinct a.sott ,  case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as spId, \n"
				+ " case nh.loaihanghoa_fk when 0 then isnull(sp.MA, isnull(sp.ma, ''))  when 1 then tscd.Ma when 3 then ccdc.MA else ncp.Ten end AS spMa,  \n"
				+ " CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten1 +isnull(m.MA,''), sp.Ten +isnull(m.MA,''))        \n"
				+ " ELSE a.DienGiai END AS spTen, mh.PK_SEQ as MUAHANG_FK, mh.SOPO , m.PK_SEQ as idmarquette,  \n"
				+ " a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, \n"
				+ " isnull(a.DonVi, 0) as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, a.TILEQUYDOI_DOLUONG, \n"
				+ " khott.pk_seq as khottId,   khott.ten as khottTen,a.SPKIEMDINH      \n"
				+ " FROM ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   \n"
				+ " INNER JOIN ERP_MUAHANG mh on a.MUAHANG_FK = mh.PK_SEQ \n"
				+ " LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   \n"
				+ " LEFT join marquette m on m.PK_SEQ = a. idmarquette and m.SANPHAM_FK = sp.PK_SEQ  "
				+ " LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq        \n"
				+ " LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  \n"
				+ " LEFT JOIN erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  \n"
				+ " LEFT JOIN KHO khott on a.khonhan = khott.pk_seq  " 
				+ " WHERE a.NHANHANG_FK = '" + this.id + "' order by a.sott  ";					
		}
		else
		{
			query = "SELECT  distinct a.sott, case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as spId, \n"
				+ " case nh.loaihanghoa_fk when 0 then isnull(sp.MA, isnull(sp.ma, ''))  when 1 then tscd.Ma when 3 then ccdc.MA else ncp.Ten end AS spMa,  \n"
				+ " CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten1 +isnull(m.MA,''), sp.Ten +isnull('marquette: '+ m.MA,''))        \n"
				+ " ELSE a.DienGiai END AS spTen, nk.MUAHANG_FK as MUAHANG_FK, mh.SOPO , m.PK_SEQ as idmarquette,  \n"
				+ " a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, \n"
				+ " isnull(a.DonVi, 0) as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, a.TILEQUYDOI_DOLUONG , \n"
				+ " khott.pk_seq as khottId,   khott.ten as khottTen ,a.SPKIEMDINH       \n"
				+ " FROM ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   \n"
				+ " inner join ERP_HOADONNCC_DONMUAHANG nk on a.MUAHANG_FK = nk.MUAHANG_FK \n"
				+ " INNER JOIN ERP_MUAHANG mh on nk.MUAHANG_FK = mh.PK_SEQ \n"
				+ " LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   \n"
				+ " LEFT join marquette m on m.PK_SEQ = a. idmarquette and m.SANPHAM_FK = sp.PK_SEQ  "
				+ " LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq        \n"
				+ " LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  \n"
				+ " LEFT JOIN erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  \n"
				+ " LEFT JOIN KHO khott on a.khonhan = khott.pk_seq  " 
				+ " WHERE a.NHANHANG_FK = '" + this.id + "' order by a.sott";			
		}
		System.out.println("[ErpNhanhang_Giay.initSanPham] query = 2 " + query);
		ResultSet rsSp = db.get(query);

		if (rsSp != null) {
			NumberFormat formater = new DecimalFormat("#,###,###.###");
			NumberFormat formater_4le = new DecimalFormat("#,###,###.####");
			try {
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next()) {
					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");

					String soluongdat = formater_4le.format(rsSp.getDouble("SOLUONGDAT"));
					String soluongnhan = formater_4le.format(rsSp.getDouble("SOLUONGNHAN"));

					String hansudung = rsSp.getString("HANSUDUNG");
					String dvdl = rsSp.getString("DonVi");
					String dongia = formater_4le.format(rsSp.getDouble("DONGIA"));

					String muahang_fk = rsSp.getString("MUAHANG_FK");
					String soPO = rsSp.getString("SOPO");
					double tilequydoiDV = rsSp.getDouble("TILEQUYDOI_DOLUONG");

					String thanhtien = formatter.format(rsSp.getDouble("SOLUONGNHAN") * rsSp.getDouble("DONGIA"));

					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					if (soluongdat != "" && soluongnhan != "")
						sanpham.setCOnlai(Float.toString(Float.parseFloat(soluongdat.replaceAll(",", ""))
								- Float.parseFloat(soluongnhan.replaceAll(",", ""))));
					
					sanpham.setTiLeQuyDoiDv(tilequydoiDV);
					
					if (this.loaihanghoa.equals("0")) {}

					sanpham.setMuahang_fk(muahang_fk);
					sanpham.setSoPO(soPO);
					sanpham.setDongia(dongia);
					sanpham.setTiente(rsSp.getString("TienTe_Fk"));
					sanpham.setTigiaquydoi(rsSp.getString("TyGiaQuyDoi"));
					sanpham.setDongiaViet(rsSp.getString("DonGiaViet"));
					String khottId = rsSp.getString("khottId") == null ? "" : rsSp.getString("khottId");
					sanpham.setKhonhanId(khottId);
					sanpham.setKhonhanTen(rsSp.getString("khottTen") == null ? "" : rsSp.getString("khottTen"));

					sanpham.setIdmarquette(rsSp.getString("idmarquette"));
					sanpham.setIsKiemDinh(rsSp.getString("SPKIEMDINH"));
					
					double soluongDat = rsSp.getDouble("SOLUONGDAT");

					// Tinh so luong MAX + dung sai co the nhan
					double soluongPONhan = 0;
					double soluongMax = soluongDat;

					if (this.poId.trim().length() > 0) {
						String sql = "select sum(b.SOLUONGNHAN)  as soluongDaNhan  "
								+ "from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  "
								+ "where b.muahang_fk = '" + muahang_fk + "' and NGAYNHANDUKIEN = '" + ngaynhandk
								+ "' and SANPHAM_FK = '" + spId + "' AND DONGIA="+dongia.replaceAll(",", "")+" and a.TRANGTHAI not in (3, 4) and a.hdncc_fk = "+this.hdNccId+" ";
						query = sql;
						if (this.id.trim().length() > 0) {
							query += " and a.pk_seq != '" + this.id + "' ";
						}
						System.out.println("lay so luong dat: " + query);
						ResultSet rsNhanTD = db.get(query);
						if (rsNhanTD != null) {
							if (rsNhanTD.next()) {
								double soluongPODat = soluongDat;

								soluongPONhan = rsNhanTD.getDouble("soluongDaNhan");
								soluongMax = (soluongPODat) - soluongPONhan;
							}
							rsNhanTD.close();
						}
						
						sql += " and a.pk_seq < " + this.id;
						rsNhanTD = db.get(sql);
						if (rsNhanTD != null) {
							if (rsNhanTD.next()) {
								soluongPONhan = rsNhanTD.getDouble("soluongDaNhan");
							}
							rsNhanTD.close();
						}
					}
					
					// Lấy số lô bên này qua
					List<ISpDetail> spConList = getListChiTietLo(muahang_fk,spId, dongia.replaceAll(",", ""),tilequydoiDV );
					sanpham.setSpDetail(spConList);
					
					// Lấy số mẻ từ bảng NHẬN HÀNG ra
					List<DetailMeSp> listMeSp = getListChiTietLoMe(muahang_fk,spId,dongia.replaceAll(",", ""));
					sanpham.setListDetailMeSp(listMeSp);
					
					sanpham.setSoluongDaNhan(formater_4le.format(soluongPONhan));
					sanpham.setSoluongMaxNhan(Double.toString(soluongMax));
					sanpham.setthanhtien(thanhtien);

					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
				
				// xử lý hiện theo đúng loại, kiểm định thì hiện ở kho biệt trữ, không kiểm định thì hiện ở kho tồn trữ.
				LocLoaiSanPham();

			} catch (Exception e) {
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();

			}
		}
	}
	
	// xét xem kho nào có trong list danh sách kho khác không?
	private boolean ExistKho(List<String> dsKho, String khoId){
		for(int i=0; i< dsKho.size(); i++){
			if(dsKho.get(i).equals(khoId)){
				return true;
			}
		}
		return false;
	}
	
	
	// xét xem người quản lý kho được quản lý :
	// 0: tồn trữ
	// 1: biệt trữ
	// 2: cả hai
	private int QuyenKho(String khoBietTruId, String khoTonTruId ){
		try{
			int quyenkho =0;
			// lấy kho mà người dùng được quyền xem
			String sql = " select khott_fk from NHANVIEN_KHOTT where nhanvien_fk ="+this.userId+" " +
				  " union select kho_fk from NHANVIEN_KHO where nhanvien_fk =" + this.userId;
			
			ResultSet rs = this.db.get(sql);
			List<String> dsKho = new ArrayList<String>();
			if(rs!=null){
				while(rs.next()){
					dsKho.add(rs.getString("khott_fk"));
				}
			}
			boolean check = false;
			// nếu kho biệt trữ và kho tồn trữ là 1.
			if( khoBietTruId.equals(khoTonTruId)){
				check = ExistKho(dsKho, khoTonTruId);
				// nếu ds kho người quản lý đều thỏa biệt trữ và tồn trữ. 2 kho là 1 thì hiện hết ds sp
				if(check == true){
					return 2;
				}
				
			} else { // nếu chúng khác nhau.
				
				if(ExistKho(dsKho,khoBietTruId )== true &&  ExistKho(dsKho,khoBietTruId )== true){
					return 2;
				}
				// TH1: kho biệt trữ.
				if(ExistKho(dsKho,khoBietTruId )== true){
					return 0;
				} 
				// TH2: kho tồn trữ.
				if(ExistKho(dsKho,khoTonTruId )== true){
					return 1;
				}
			}
		
			return quyenkho;
		} catch (Exception ex){
			ex.printStackTrace();
			return -1;
		}
	}
	
	// xử lý loại bỏ list 
	private void LocLoaiSanPham(){
		try{
			String sql = "select KHOBIETTRU_FK, KHOTONTRU_FK from Erp_HOADONNCC where PK_Seq = "+ this.hdNccId;
			ResultSet rs = this.db.get(sql);
			String khoBietTruId = "";
			String khoTonTruId = "";
			if(rs!=null){
				if(rs.next()){
					khoBietTruId = rs.getString("KHOBIETTRU_FK");
					khoTonTruId = rs.getString("KHOTONTRU_FK");
				}
				rs.close();
			}
			
			// trường hợp 1: khoBietTru và tồn trữ đều được người này quản lý.
			int quyenKho = QuyenKho(khoBietTruId, khoTonTruId);
			
			if( quyenKho == 2){
				// giữ nguyên danh sách, kho này được nhận cả 2 loại.
			} else if( quyenKho == 1){
				// loại bỏ sản phẩm không kiểm định
				
				for(int i =0; i< this.spList.size(); i++){
					if(this.spList.get(i).getIsKiemDinh().equals("1")){
						this.spList.remove(i);
						// do xóa 1 phần tử ra khỏi list, thì size của list sẽ bị giảm lại -1, nên phải xét tiếp phần tử bị dồn lại
						i= i-1;
					}
				}
				
			} else if(quyenKho == 0){
				// loại bỏ sản phẩm có kiểm định
				for(int i =0; i< this.spList.size(); i++){
					if(this.spList.get(i).getIsKiemDinh().equals("0")){
						this.spList.remove(i);
						// do xóa 1 phần tử ra khỏi list, thì size của list sẽ bị giảm lại -1, nên phải xét tiếp phần tử bị dồn lại
						i=i-1;
					}
				}
			}
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	// cái này dùng để lấy số lô.
	// 05/06/2016
	private List<ISpDetail> getListChiTietLo( String muahang_fk, String SanPhamId, String dongia,double TILEQUYDOI_DOLUONG) throws SQLException{
		
		DecimalFormat formatter = new DecimalFormat("###,###,###.###");
		DecimalFormat formatter_4le = new DecimalFormat("###,###,###.####");
		DecimalFormat formatter_VND = new DecimalFormat("#########");
		
		double giachuaquydoi= Double.parseDouble(formatter_VND.format((Double.parseDouble(dongia.replaceAll(",", ""))/ TILEQUYDOI_DOLUONG))) ;
		 
		
		
		String  command = " select d.sott ,d.SANPHAM_FK, d.SOLUONG,d.SOLO, d.NGAYSANXUAT, d.NGAYHETHAN, d.DONGIA, " +
						  " d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct " +
						  " inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ " +
						  " where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO  " +
						  " and nh.hdNCC_fk = d.HOADONNCC_FK and round(ct.GIATHEOLO,4) = "+dongia+" group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, " +
						  " isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct " +
						  " inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ " +
						  " where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO  " +
						  " and nh.hdNCC_fk = d.HOADONNCC_FK " +
						  "  and ct.NHANHANG_FK = "+ this.id +"and round(ct.GIATHEOLO,4)  = "+dongia+ " " +
						  " group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongdanhan, " +
						  " d.NGAYSANXUAT as ngaysanxuat, d.NGAYHETHAN, " +
						  " isnull((select COUNT(*) from ERP_NHANHANG_SP_CHITIET a " +
						  " where SANPHAM_FK = "+SanPhamId+" and NHANHANG_FK = "+this.id+" and MUAHANG_FK = "+muahang_fk+
						  " and a.SOLO = d.SOLO and round( a.GIATHEOLO,4) = "+dongia+"),0) as soluongthung, " +
						  " d.nsx_fk nsxid, isnull((select isnull(ten,'') from erp_nhasanxuat q where d.nsx_fk=q.pk_seq),'') nsxten, isnull(d.marrquet,'') marrquet "+
						  " from  ERP_HOADONNCC_DONMUAHANG d  " +
						  " where  d.hoadonncc_fk = "+this.hdNccId+" and d.sanpham_fk = "+SanPhamId +
						  "and d.soluong <> 0 and d.MUAHANG_FK = "+ muahang_fk + " and ROUND (d.DONGIA,0) ="+ giachuaquydoi +" "
						  		+ " order by d.sott ";

		System.out.println("Khoi tao san pham con 4: " + command);
		ResultSet spConRs = db.get(command);

		List<ISpDetail> spConList = new ArrayList<ISpDetail>();
		ISpDetail spCon = null;
		if (spConRs != null) {
			while (spConRs.next()) {
				String spConMa = spConRs.getString("sanpham_fk");
				String solo = spConRs.getString("solo");
				String soluong = formatter_4le.format(spConRs.getDouble("soluongmax"));
				
				if( this.id.trim().length() >0){
					soluong = formatter_4le.format(spConRs.getDouble("soluongdanhan"));
				}
				String ngaysanxuat = spConRs.getString("ngaysanxuat");
				String ngayhethan = spConRs.getString("ngayhethan");
				String sothung = spConRs.getString("soluongthung");
				
				spCon = new SpDetail(spConMa, solo,sothung, soluong, ngaysanxuat, ngayhethan);
				spCon.setSoluongmax(formatter_4le.format(spConRs.getDouble("soluongmax")));
				spCon.setDongiaLo(formatter_4le.format(spConRs.getDouble("dongia")));
				
				spCon.setNsxId(spConRs.getString("nsxid"));
				spCon.setNsxTen(spConRs.getString("nsxten"));
				spCon.setMarrquet(spConRs.getString("marrquet"));
				
				spConList.add(spCon);
			}
			spConRs.close();
		}
		return spConList;
	}

	// Tính lại số lượng nhân thực sự
	
	// Hàm dùng để lấy chi tiết số lô theo mẻ
	private List<DetailMeSp> getListChiTietLoMe(String muahang_fk, String SanPhamId, String dongia ) {

		DecimalFormat formatter = new DecimalFormat("###,###,###.###");
		List<DetailMeSp> listDetailMeSp = new ArrayList<DetailMeSp>();
		try {

			String comand = " select a.MATHUNG, a.MAME, a.BIN_FK, a.SOLUONG, a.SOLO,a.NGAYHETHAN, a.NGAYSANXUAT, " +
					" a.nsx_fk nsxid, isnull((select isnull(ten,'') from erp_nhasanxuat q where a.nsx_fk=q.pk_seq),'') nsxten, isnull(a.MARQ,'') marrquet "+
					        " from ERP_NHANHANG_SP_CHITIET a where SANPHAM_FK = "+SanPhamId +
					        " and NHANHANG_FK = "+this.id+" and MUAHANG_FK = "+ muahang_fk + "and round(giatheolo,4) ="+ dongia+
					        "  order by SOLO, cast(MAME as float), case when ISNUMERIC(MATHUNG)=1 then CAST(MATHUNG as float)  else 100000    end  ";

			System.out.println("Khoi tao san pham con 1:   " + comand);
			ResultSet rs = db.get(comand);
			
			if (rs != null) {
				while(rs.next()){
					DetailMeSp spCon = new DetailMeSp();
					spCon.setMaThung(rs.getString("MATHUNG"));
					spCon.setMe(rs.getString("MAME"));
					spCon.setKhuVuc(rs.getString("BIN_FK"));
					spCon.setSoLuong(rs.getString("SOLUONG"));
					spCon.setSoLo(rs.getString("SOLO"));
					spCon.setNgayHetHan(rs.getString("NGAYHETHAN"));
					spCon.setNgaySanXuat(rs.getString("NGAYSANXUAT"));
					spCon.setNsxId(rs.getString("nsxid"));
					spCon.setNsxTen(rs.getString("nsxten"));
					spCon.setMarrquet(rs.getString("marrquet"));
					listDetailMeSp.add(spCon);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return listDetailMeSp;
	}

	public void initPdf(String spId) {
		String query = " select isnull(c.LOAISANPHAM_FK,0) as LOAISANPHAM_FK, a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYTAO, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK, "
				+ " 	case when a.noidungnhan_fk = 100000 then  SOPO else d.Prefix + cast(d.PK_SEQ as varchar(10)) end as sochungtu,  "
				+ "   case when a.noidungnhan_fk = 100000 then ncc.TEN else kh.TEN end as donviban "
				+ " from erp_nhanhang a inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ "
				+ " left join ERP_MUAHANG c on a.MUAHANG_FK = c.PK_SEQ "
				+ " left join DonTraHang d on a.TRAHANG_FK = d.PK_SEQ "
				+ " left join Erp_Nhacungcap ncc on ncc.PK_SEQ=c.NHACUNGCAP_FK "
				+ " left join ERP_KHACHHANG kh on kh.PK_SEQ=d.KHACHHANG_FK " + " where a.pk_seq = '" + this.id
				+ "' and b.pk_seq in " + this.util.quyen_donvithuchien(this.userId);

		System.out.println("[ErpNhanhang_Giay.initPdf] Khoi tao nhan hang: " + query);
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					this.loaispId = rs.getString("LOAISANPHAM_FK");
					this.ngaychot = rs.getString("ngaytao"); // NGAY TAO
					this.ngaynhanhang = rs.getString("ngaynhan");
					this.dvthId = rs.getString("donviban");
					this.poId = rs.getString("sochungtu");

					this.diengiai = rs.getString("diengiai");
					this.ndnId = rs.getString("noidungnhan_fk");
					this.sohoadon = rs.getString("sohoadon");

					/*
					 * System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] ngaynhanhang = " +
					 * this.ngaynhanhang); System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] dvthId = " + this.dvthId);
					 * System.out.println("[ErpNhanhang_Giay.initPdf] poId = " +
					 * this.poId); System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] diengiai = " +
					 * this.diengiai); System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] ndnId = " + this.ndnId);
					 * System.out.println(
					 * "[ErpNhanhang_Giay.initPdf] sohoadon = " +
					 * this.sohoadon);
					 */

				}
				rs.close();
			} catch (SQLException e) {
			}
		}

		this.initSanPhamPdf(spId);
	}

	private void initSanPhamPdf(String _spId) {

		String query = " SELECT isnull(sp.loaisanpham_fk,0) as  loaisanpham_fk, CASE NH.LOAIHANGHOA_FK WHEN 0 THEN A.SANPHAM_FK WHEN 1 THEN TSCD.PK_SEQ ELSE NCP.PK_SEQ END AS SPID,  "
				+ "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL( CASE WHEN ( LEN(LTRIM(RTRIM(SP.MA))) <= 0 OR ( SP.MA IS NULL ) ) THEN SP.MA ELSE SP.MA END, '' )  WHEN 1 THEN TSCD.MA ELSE NCP.TEN END AS SPMA,   "
				+ "  CASE NH.LOAIHANGHOA_FK WHEN 0 THEN ISNULL(SP.TEN1, ISNULL(SP.TEN, ''))  ELSE ISNULL(A.DIENGIAI, '') END AS SPTEN,  "
				+ "  A.NGAYNHANDUKIEN, A.DUNGSAI, A.DONGIA, A.SOLUONGDAT, A.SOLUONGNHAN, ISNULL(SP.HANSUDUNG, '0') AS HANSUDUNG,  "
				+ "  ISNULL(A.DONVI, 'NA') AS DONVI, A.TIENTE_FK, A.TYGIAQUYDOI, A.DONGIAVIET,  "
				+ "  KHOTT.PK_SEQ AS KHOTTID, KHOTT.MA + ', ' + KHOTT.TEN AS KHOTEN, "
				+ "  CASE WHEN ISNULL(QC.SOLUONG1,'0') = '0' THEN   "
				+ "  A.SOLUONGNHAN ELSE A.SOLUONGNHAN *  ISNULL(QC.SOLUONG2,'0') /QC.SOLUONG1 END AS TRONGLUONG , "
				+ "  CAST(ROUND(ISNULL(SP.THETICH, 0), 5) AS NUMERIC(10, 5)) AS THETICH  "
				+ "  FROM ERP_NHANHANG_SANPHAM A INNER JOIN ERP_NHANHANG NH ON A.NHANHANG_FK = NH.PK_SEQ    "
				+ "  LEFT JOIN ERP_SANPHAM SP ON A.SANPHAM_FK = SP.PK_SEQ    "
				+ "  LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ AND SP.DVDL_FK=QC.DVDL1_FK AND QC.DVDL2_FK=100003   "
				+ "  LEFT JOIN ERP_TAISANCODINH TSCD ON A.TAISAN_FK = TSCD.PK_SEQ         "
				+ "  LEFT JOIN ERP_NHOMCHIPHI NCP ON A.CHIPHI_FK = NCP.PK_SEQ   "
				+ "  LEFT JOIN ERP_KHOTT KHOTT ON A.KHONHAN = KHOTT.PK_SEQ   " + "  WHERE A.NHANHANG_FK = " + this.id
				+ " AND A.SOLUONGNHAN >0 ";

		if (_spId.length() > 0) {
			query += " and a.SANPHAM_FK in (" + _spId + ")";
		}

		ResultSet rsSp = db.get(query);

		if (rsSp != null) {
			try {
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next()) {
					String spId = rsSp.getString("spId");
					// System.out.println("[ErpNhanhang_Giay.initSanPhamPdf]
					// spId = " + spId);
					String spMa = rsSp.getString("spMa");

					// --- nếu sản phẩm là GLUE, ko in quy cách -----//
					String loaisp = rsSp.getString("loaisanpham_fk");

					String spTen = "";
					if (loaisp.equals("100015") || loaisp.equals("100016")) {
						spTen = rsSp.getString("spten1");
					} else {
						spTen = rsSp.getString("spTen");
					}

					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");
					String soluongdat = rsSp.getString("SOLUONGDAT");
					String soluongnhan = rsSp.getString("SOLUONGNHAN");
					String hansudung = rsSp.getString("khoTen"); // luu ten kho
					String dvdl = rsSp.getString("DonVi");

					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					sanpham.setTrongluong(rsSp.getString("trongluong"));
					sanpham.setThetich(rsSp.getString("THETICH") == null ? "0" : rsSp.getString("THETICH"));
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			} catch (Exception e) {
				e.printStackTrace();

			}
		}
	}

	// trả về số lần nhận hàng hiện tại,
	// chỉ cập nhật lúc tạo mới mà thôi
	private int tinhSoLanNhanHang() {
		try {
			int stt = 0;
			String sql = "  select COUNT(*) as sl from ERP_NHANHANG where hdNCC_fk = " + this.hdNccId;
			ResultSet rs = this.db.get(sql);
			if (rs != null) {
				if (rs.next()) {
					stt = rs.getInt("sl");
				}
				rs.close();
			}
			return (stt);

		} catch (Exception ex) {
			ex.printStackTrace();
			return -1;
		}
	}
	
	// kiểm tra đã hoàn tất nhận hàng của hoá đơn này chưa?
	/*private boolean checkHoanTatNhanHang(){
		try {
			double sldat =0;
			double slnhan = 0;
			String sql = " select isnull( (select SUM(SOLUONG) as sl from ERP_HOADONNCC_DONMUAHANG " +
			 " where HOADONNCC_FK = "+this.hdNccId+"),0) as sldat," +
			 " isnull((select SUM(SOLUONGNHAN* TILEQUYDOI_DOLUONG) from ERP_NHANHANG_SANPHAM where NHANHANG_FK in (" +
			 " select PK_SEQ from ERP_NHANHANG where hdNCC_fk ="+this.hdNccId +")),0) as slnhan";
			
			ResultSet rs = this.db.get(sql);
			if (rs != null) {
				if (rs.next()) {
					sldat = rs.getInt("sldat");
					slnhan = rs.getInt("slnhan");
				}
				rs.close();
			}
			
			if(this.tilequydoi_dvdl >1){
				if(sldat == (slnhan*this.tilequydoi_dvdl))
					return true;
				return false;
					
			} else {
				if(sldat == slnhan)
					return true;
				return false;
			}

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}*/
	
	private boolean checkHoanTatNhanHang(){
		try {
			
			String sql = " SELECT  SOLUONG1 AS SOLUONG1,ISNULL(SOLUONG2,0)   AS SOLUONG2   \n   "+    
			 "FROM   \n   "+  
			 
				 "  (SELECT  P.pk_seq AS PARK, HDMH.SANPHAM_FK AS SANPHAM,SUM( HDMH.SOLUONG ) AS SOLUONG1    \n   "+    
				 "  FROM    \n   "+    
				 "   ERP_PARK p inner join ERP_HOADONNCC hd on p.PK_SEQ=hd.PARK_FK   \n   "+    
				 "   INNER JOIN ERP_HOADONNCC_DONMUAHANG HDMH ON HD.pk_seq=HDMH.HOADONNCC_FK   \n   "+    
				 "  WHERE P.pk_seq  in (select park_fk from erp_hoadonncc where pk_Seq= "+ this.hdNccId +" )   \n   "+    
				 "  GROUP BY P.pk_seq,HDMH.SANPHAM_FK) kq1   \n   "+    
				 "  LEFT JOIN   \n   "+   
				 "  (    \n   "+  
				 
				 "  SELECT  P.pk_seq AS PARK, NHSP.SANPHAM_FK AS SANPHAM,    \n   "+    
				 "   SUM( NHSP.SOLUONGNHAN * TILEQUYDOI_DOLUONG ) AS SOLUONG2   \n   "+    
				 "  FROM    \n   "+    
				 "  ERP_NHANHANG NH INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK    \n   "+    
				 "  INNER JOIN ERP_HOADONNCC hd on HD.PK_SEQ=NH.hdNCC_fk   \n   "+    
				 "  INNER JOIN ERP_PARK P ON p.PK_SEQ=hd.PARK_FK   \n   "+    
				 "  WHERE P.pk_seq  in (select park_fk from erp_hoadonncc where pk_Seq= "+ this.hdNccId +" )  and NH.TRANGTHAI IN (0,1,2)   \n   "+
				 "  GROUP BY   P.pk_seq,NHSP.SANPHAM_FK    \n   "+    
				 "  ) kq2   \n   "+    
				 "	ON kq1.SANPHAM=kq2.SANPHAM   \n   "+    
				 "	WHERE  ROUND (kq1.SOLUONG1,0) > ROUND(ISNULL( kq2.SOLUONG2,0),0)";
			 
			ResultSet rs = this.db.get(sql);
			boolean bien=true;
			if (rs.next()) {
				bien= false;
			}
			rs.close();
			 return bien;
			
			

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	 
	
	private boolean checkNhanHangVuotHoaDon(){
		try {
			
			String sql = " SELECT  SOLUONG1 AS SOLUONG1,ISNULL(SOLUONG2,0)   AS SOLUONG2   \n   "+    
			 "FROM   \n   "+  
			 
				 "  (SELECT  P.pk_seq AS PARK, HDMH.SANPHAM_FK AS SANPHAM,SUM( HDMH.SOLUONG ) AS SOLUONG1    \n   "+    
				 "  FROM    \n   "+    
				 "   ERP_PARK p inner join ERP_HOADONNCC hd on p.PK_SEQ=hd.PARK_FK   \n   "+    
				 "   INNER JOIN ERP_HOADONNCC_DONMUAHANG HDMH ON HD.pk_seq=HDMH.HOADONNCC_FK   \n   "+    
				 "  WHERE P.pk_seq  in (select park_fk from erp_hoadonncc where pk_Seq= "+ this.hdNccId +" )   \n   "+    
				 "  GROUP BY P.pk_seq,HDMH.SANPHAM_FK) kq1   \n   "+    
				 "  LEFT JOIN   \n   "+   
				 "  (    \n   "+  
				 
				 "  SELECT  P.pk_seq AS PARK, NHSP.SANPHAM_FK AS SANPHAM,    \n   "+    
				 "   SUM( NHSP.SOLUONGNHAN * TILEQUYDOI_DOLUONG ) AS SOLUONG2   \n   "+    
				 "  FROM    \n   "+    
				 "  ERP_NHANHANG NH INNER JOIN ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK    \n   "+    
				 "  INNER JOIN ERP_HOADONNCC hd on HD.PK_SEQ=NH.hdNCC_fk   \n   "+    
				 "  INNER JOIN ERP_PARK P ON p.PK_SEQ=hd.PARK_FK   \n   "+    
				 "  WHERE P.pk_seq  in (select park_fk from erp_hoadonncc where pk_Seq= "+ this.hdNccId +" )  and NH.TRANGTHAI IN (0,1,2)   \n   "+
				 "  GROUP BY   P.pk_seq,NHSP.SANPHAM_FK    \n   "+    
				 "  ) kq2   \n   "+    
				 "	ON kq1.SANPHAM=kq2.SANPHAM   \n   "+    
				 "	WHERE  ROUND (kq1.SOLUONG1,0) < ROUND(ISNULL( kq2.SOLUONG2,0),0)";
			 
			ResultSet rs = this.db.get(sql);
			boolean bien=false;
			if (rs.next()) {
				bien= true;
			}
			rs.close();
			 return bien;
			
			

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}
	 
	public boolean createNhanHang() {
		String ngaytao = this.getDateTime();

		if (this.spList.size() <= 0) {
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		} else {
			for (int i = 0; i < spList.size(); i++) {
				ISanpham sp = spList.get(i);

				if(sp.getSoluongnhan() == null || sp.getSoluongnhan() == "")
				{
						this.msg = "Vui lòng nhập đầy đủ thông tin : Số lô, Số lượng , Ngày sản xuất";
						return false;
					
				}
				
				if (Double.parseDouble(sp.getSoluongnhan()) > 0) {
					if (this.loaihanghoa.equals("0")) {
						if (sp.getListDetailMeSp().size() <= 0) {
							this.msg = "Vui lòng nhập đầy đủ thông tin : Số lô, Số lượng , Ngày sản xuất";
							return false;
						}
					}
				}

			}
		}

		if(this.dvthId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn vị thực hiện cho phiếu nhận hàng này";
			return false;
		}
		
		if (this.KhoNhanId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn kho nhận cho phiếu nhận hàng này";
			return false;
		} else {
			if (this.loaikho.equals("5") || this.loaikho.equals("8")) {
				if (this.khachhangId.trim().length() <= 0) {
					this.msg = "Vui lòng chọn khách hàng cho kho nhận này";
					return false;
				}
			} else {
				this.khachhangId = "NULL";
			}
		}

		if (this.ndnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}

		if (this.loaihanghoa.equals("0") && this.ldnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lý do nhận hàng";
			return false;
		}

		String query = "";

		if (this.nccId.trim().length() > 0) {
			String query1 = "";
			query1 = "select SOHIEUTAIKHOAN from ERP_NHACUNGCAP ncc inner join ERP_TAIKHOANKT tk on ncc.TAIKHOAN_FK = tk.PK_SEQ where ncc.PK_SEQ = "
					+ this.nccId;

			ResultSet rsNG = db.get(query1);
			try {
				if (rsNG.next()) {
					if (rsNG.getString("SOHIEUTAIKHOAN").indexOf("331120") > 0) { // nha
																					// cung
																					// cap
																					// co
																					// so
																					// hieu
																					// tai
																					// khoan
																					// 331120
						this.isNCCNK = "1";
					} else {
						this.isNCCNK = "0";
					}

				}
				rsNG.close();
			} catch (Exception e1) {
			}
		}

		if (this.hdNccId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn Số hóa đơn NCC ";
			return false;
		}

		try {
			db.getConnection().setAutoCommit(false);

			String muahang_fk = "null";
			String trahang_fk = "null";

			if (this.ndnId.equals("100000")) {
				if (this.poId.trim().length() > 0)
					muahang_fk = this.poId;
			} else {
				if (this.poId.trim().length() > 0)
					trahang_fk = this.poId;
			}

			String ldn_fk = "null";
			if (this.ldnId.trim().length() > 0)
				ldn_fk = this.ldnId;

			String NCC_KH_FK = "null";
			if (this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;

			String nppId = util.getIdNhapp(this.userId);
			/*
			 // gan kho cho xu ly=100047 la kho cho xu ly nguyen lieu trong bang erp_nhanhang
			 //
			 //
			 */

			query = "insert ERP_NHANHANG(KHACHHANGKYGUI_FK, KHONHAN_FK, NGAYNHAN, MUAHANG_FK, NGAYCHOT, LOAIHANGHOA_FK, NOIDUNGNHAN_FK, DIENGIAI, " +
					"DONVITHUCHIEN_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NoiDungNhap_fk, NCC_KH_FK, hdNCC_fk, tigia ,khochoxuly_fk, " +
					"GHICHU , NHAPHANPHOI_FK) "
					+ "values( " + this.khachhangId + ", " + this.KhoNhanId + ",'" + this.ngaynhanhang + "', "
					+ this.muahang_fk1 + ", '" + this.ngaynhanhang + "', '" + this.loaihanghoa + "', '" + this.ndnId
					+ "', N'" + this.diengiai + "', '" + this.dvthId + "',  " + " '" + ngaytao + "', '" + ngaytao
					+ "', '" + this.userId + "', '" + this.userId + "', '0', '" + this.congtyId + "', " + ldn_fk + ", "
					+ NCC_KH_FK + ", " + this.hdNccId + ", '" + this.tigia + "',"+this.khoChoXuLy+", " + " N'" + this.ghichu + "', " + nppId
					+ " )";
			System.out.println(" vao khu che xuat ne :" + query);

			if (!db.update(query)) {
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}

			String nhCurrent = "";
			query = "select IDENT_CURRENT('Erp_NHANHANG') as nhId";

			ResultSet rsNh = db.get(query);
			if (rsNh.next()) {
				nhCurrent = rsNh.getString("nhId");
				rsNh.close();
			}

			
			// Tính ra lần nhận hàng thứ mấy của đơn hàng.
			int soLanNhanHang = tinhSoLanNhanHang();
			if(soLanNhanHang == -1){
				this.msg = "Không tính được số lần nhận hàng của hoá đơn";
				db.getConnection().rollback();
				return false;
			}
			
			// Cập nhật số lần nhận hàng
			query = "update ERP_NHANHANG set LANNHAN ="+ soLanNhanHang +" where PK_SEQ = "+ nhCurrent;
			int k = this.db.updateReturnInt(query);
			if( k !=1){
				this.msg = "Cập nhật lần nhận hàng thất bại.";
				db.getConnection().rollback();
				return false;
			}
			
			
			if (this.spList.size() > 0) {
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);

					if (sp.getSoluongnhan() != "") // chi luu nhung san pham
													// nguoi dung nhap so luong
					{
						String SanPham_FK = "NULL";
						String ChiPhi_FK = "NULL";
						String TaiSan_FK = "NULL";
						String CCDC_FK = "NULL";

						if (this.loaihanghoa.equals("0")) {
							SanPham_FK = sp.getId();
							ChiPhi_FK = "NULL";
							TaiSan_FK = "NULL";
							CCDC_FK = "NULL";
						} else {
							if (this.loaihanghoa.equals("1")) {
								SanPham_FK = "NULL";
								ChiPhi_FK = "NULL";
								TaiSan_FK = sp.getId();
								CCDC_FK = "NULL";
							} else {
								if (loaihanghoa.equals("3")) {
									SanPham_FK = "NULL";
									ChiPhi_FK = "NULL";
									TaiSan_FK = "NULL";
									CCDC_FK = sp.getId();
								} else {
									SanPham_FK = "NULL";
									ChiPhi_FK = sp.getId();
									TaiSan_FK = "NULL";
									CCDC_FK = "NULL";
								}
							}
						}

						double soluongnhan = 0;
						if (sp.getSoluongnhan().trim().length() <= 0) {
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: " + sp.getMa();
							db.getConnection().rollback();
							return false;
						} else {
							soluongnhan = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
							System.out.println("so luong nhan " + soluongnhan);
							if (sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");

							double slgMax = Double.parseDouble(sp.getSoluongMaxNhan().replaceAll(",", ""));
							System.out.println("so luong max nhan " + slgMax);
							if (soluongnhan > slgMax) {
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa()
										+ " không được phép vượt quá tổng đặt (" + sp.getSoluongdat()
										+ ") và dung sai cho phép ( " + sp.getDungsai()
										+ "%). Vì thế bạn chỉ có thể nhận tối đa là ( " + slgMax + " )  ";
								db.getConnection().rollback();
								return false;
							}
						}

						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null" : sp.getKhonhanId().trim();
						if (this.loaihanghoa.equals("0") && sp.getKhonhanId().trim().length() <= 0) {
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( " + sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}

						// Với Mua hàng nhập khẩu : lưu thêm số ngày vượt mức
						// miễn phí , số lượng >> sau này tính chi phí
						query = "SELECT case when DATEDIFF(day,nksp.NGAYNHAP,'" + this.ngaynhanhang
								+ "') <= nksp.SONGAYLUUKHO then 0 " + "       else DATEDIFF(day,nksp.NGAYNHAP,'"
								+ this.ngaynhanhang + "') - nksp.SONGAYLUUKHO end SONGAYVUOTMUC "
								+ "FROM ERP_NHAPKHONHAPKHAU_SANPHAM nksp inner join ERP_NHAPKHONHAPKHAU nk on nksp.NHAPKHO_FK = nk.PK_SEQ "
								+ "WHERE nk.MUAHANG_FK = " + this.muahang_fk1 + " AND nksp.SANPHAM_FK = " + SanPham_FK
								+ " ";
						ResultSet rsTT = db.get(query);
						int songayvuotmuc = 0;
						double soluongvuotmuc = 0;
						if (rsTT.next()) {

							songayvuotmuc = rsTT.getInt("SONGAYVUOTMUC");

						}
						rsTT.close();

						if (songayvuotmuc > 0)
							soluongvuotmuc = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
						// Kiểm định =1 , không kiểm định = 0;
						String kho = "";
						if(sp.getIsKiemDinh().equals("0")){
							kho = this.KhoNhanId;
						} else {
							kho = this.khoChoXuLy;
						}
						
						// Kho nhận đây là kho nhận thực sự: có thể kho chờ xử lý hoặc kho nhân
						query = " insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, MUAHANG_FK ,SANPHAM_FK, TAISAN_FK, CCDC_FK, " +
								" CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI," +
								" DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC, IDMARQUETTE,SPKIEMDINH,TILEQUYDOI_DOLUONG) "
								+ "values('" + nhCurrent + "', " + sp.getMuahang_fk() + ", " + SanPham_FK + ", "
								+ TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', N'"
								+ sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + kho + ", " + "'"
								+ sp.getSoluongdat().replaceAll(",", "") + "',  '"
								+ sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', "
								+ Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + sp.getTiente()
								+ "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "")
								+ "' , " + songayvuotmuc + ", " + soluongvuotmuc + ","+sp.getIdmarquette()+",'"
								+sp.getIsKiemDinh()+"',"+sp.getTiLeQuyDoiDv()+" )";
						System.out.println("insert nhanhang_sanpham: "+query);
						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}
						
						double tongchitiet = 0;

						List<DetailMeSp> detailList = sp.getListDetailMeSp();
						for (int j = 0; j < detailList.size(); j++) {
							DetailMeSp detail = detailList.get(j);
								/*
								 * set co dinh cho khu_fk =100000 (khu biet tru) trong bang ERP_NHANHANG_SP_CHITIET
								 * Số thùng không dùng nhé
								 * 03/06/2016
								 */
							    // set mặc định kho nào không có khu thì là 100000.
								if(detail.getKhuVuc() == null ){
									detail.setKhuVuc("0");
								}
								
								String nsx_fk = (detail.getNsxId()==null || detail.getNsxId().trim().length()<1)?null:detail.getNsxId();
								
								query = " insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, MUAHANG_FK, SANPHAM_FK, LANNHAN, SOLO,MATHUNG, " +
										" SOLUONG, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,BIN_FK, MAME, GIATHEOLO,NGAYNHAPKHO,ISKIEMDINH,nsx_fk,MARQ) "
										+ "values('" + nhCurrent + "', " + sp.getMuahang_fk() + ", '" + sp.getId() + "', '"
										+ Integer.toString(j + 1) + "', '" + detail.getSoLo() + "','"+detail.getMaThung()+"', " + "'"
										+ detail.getSoLuong().replaceAll(",", "") + "', '"
										+ detail.getNgaySanXuat() + "', '" + detail.getNgayHetHan() + "','"
										+ sp.getNgaynhandukien() + "',"
										+ detail.getKhuVuc() +",'" + detail.getMe() + "',"+sp.getDongia()+",'"+this.ngaynhanhang+"',  +'"+  sp.getIsKiemDinh() + "',"+nsx_fk+",N'"+detail.getMarrquet()+"')";

								tongchitiet = tongchitiet + Double.parseDouble(detail.getSoLuong().replaceAll(",", ""));

								System.out.println("11.Insert Nhan hang ERP_NHANHANG_SP_CHITIET: " + query);

								if (!db.update(query)) {
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
									db.getConnection().rollback();
									return false;
								}
						}
						
						if (this.loaihanghoa.equals("0")) {
						 
							NumberFormat formatter_3le = new DecimalFormat("#######.###");
							NumberFormat formatter_4le = new DecimalFormat("#######.####");
							
							tongchitiet = Double.parseDouble( formatter_4le.format(tongchitiet));
							 
							double tong_=   Double.parseDouble( formatter_4le.format(Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""))));
							
							if ( tong_  -  tongchitiet != 0) {
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :" + sp.getMa();
								db.getConnection().rollback();
								return false;
							}
						}

					}
				}
			}

			//CẬP NHẬT TOOLTIP
			String ab = db.execProceduce2("CapNhatTooltip_NhanHang", new String[] { nhCurrent } );
			
			
			
			// Cập nhật lại xem đã hoàn tất nhận hàng hay chưa?
			boolean check = checkHoanTatNhanHang();
			check = this.UpdateHoanTatNhanHang(check, nhCurrent);
			if( check == false){
				this.msg = "Cập nhật trạng thái hoàn tất nhận hàng thất bại";
				db.getConnection().rollback();
				return false;
			}
			
			boolean  checkvuothd_nh=checkNhanHangVuotHoaDon();
			if( checkvuothd_nh == true){
				this.msg = "Số lượng nhận hàng đã vượt số  lượng hóa đơn";
				db.getConnection().rollback();
				return false;
			}
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			this.msg = "Lỗi tạo nhận hàng: " + e.getMessage();
			e.printStackTrace();
			db.update("rollback");

			return false;
		}

	}

	private boolean UpdateHoanTatNhanHang(boolean flag, String id) {
		try {
			int index = 0;
			if (flag == true) {
				index = 1;
			}
			String query = "";
			query =   " update ERP_PARK set HOANTAT_NHANHANG = '" + index + "' where PK_SEQ in ("
					+ "	select park_fk from ERP_HOADONNCC where pk_seq in (" 
					+ "	select hdNCC_fk from ERP_NHANHANG where PK_SEQ = " + id + "))";

			int k = this.db.updateReturnInt(query);
			if (k != 1) {
				this.msg = " Không cập nhật được trạng thái hoàn tất nhận hàng hoá đơn.";
				this.db.getConnection().rollback();
				return false;
			}
			
			 	query="SELECT distinct MUAHANG_FK FROM ERP_NHANHANG_SANPHAM WHERE NHANHANG_FK= "+id;
			 	ResultSet rs=db.get(query);
			 	while(rs.next()){
			 		String mhid=rs.getString("MUAHANG_FK");
			 		boolean hoantat=check_hoatatnhanhang_mua(mhid);
			 		query="UPDATE ERP_MUAHANG SET HOANTAT_NHANHANG='"+(hoantat?"1":"0")+"' where pk_seq="+mhid;
			 		if(!db.update(query)){
			 			this.msg = " Không cập nhật được trạng thái hoàn tất mua hàng."+query;
						this.db.getConnection().rollback();
						return false;
			 		}
			 	}
			 	rs.close();
	 
			
			
			return true;
			
			
			
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	private boolean check_hoatatnhanhang_mua(String mhid) {
		// TODO Auto-generated method stub
		try{
			String  query=" SELECT * FROM  " + 
			  " (  " + 
			  "	SELECT B.MUAHANG_FK,SANPHAM_FK,SUM(B.SOLUONG) AS SOLUONG  " + 
			  "	FROM ERP_MUAHANG  A INNER JOIN ERP_MUAHANG_SP  B ON A.PK_SEQ=B.MUAHANG_FK  " + 
			  "	WHERE B.MUAHANG_FK= "+mhid+"   " + 
			  "	GROUP BY B.MUAHANG_FK, SANPHAM_FK  " + 
			  " ) MH LEFT JOIN   " + 
			  "(   " + 
			  "	SELECT A.MUAHANG_FK,SANPHAM_FK,SUM(SOLUONGNHAN * TILEQUYDOI_DOLUONG) AS SOLUONG  " + 
			  "	FROM ERP_NHANHANG  A INNER JOIN ERP_NHANHANG_SANPHAM B ON A.PK_SEQ=B.NHANHANG_FK  " + 
			  "	WHERE B.MUAHANG_FK= "+mhid+" AND A.TRANGTHAI IN (0,1,2)  " + 
			  "	GROUP BY A.MUAHANG_FK, SANPHAM_FK  " + 
			  ") NH ON MH.SANPHAM_FK= NH.SANPHAM_FK AND MH.MUAHANG_FK =NH.MUAHANG_FK  " + 
			  "WHERE MH.SOLUONG > NH.SOLUONG";
			ResultSet rs=db.get(query);
			boolean bien=true;
			if(rs.next()){
				bien=false;
			}
			rs.close();
			return bien;
			 
			
		}catch(Exception er){
			er.printStackTrace();
			return false;
		}
	 
	}

	public boolean updateNhanHang() {
		String ngaytao = this.getDateTime();

		if (this.spList.size() <= 0) {
			this.msg = "Không có sản phẩm nào để nhận hàng, vui lòng kiểm tra lại";
			return false;
		} else {
			for (int i = 0; i < spList.size(); i++) {
				ISanpham sp = spList.get(i);

				if(sp.getSoluongnhan() == null || sp.getSoluongnhan() == "")
				{
						this.msg = "Vui lòng nhập đầy đủ thông tin : Số lô, Số lượng , Ngày sản xuất";
						return false;
					
				}
				
				if (Double.parseDouble(sp.getSoluongnhan()) > 0) {
					if (this.loaihanghoa.equals("0")) {
						if (sp.getListDetailMeSp().size() <= 0) {
							this.msg = "Vui lòng nhập đầy đủ thông tin : Số lô, Số lượng , Ngày sản xuất";
							return false;
						}
					}
				}

			}
		}

		if(this.dvthId.trim().length() <= 0)
		{
			this.msg = "Vui lòng chọn đơn vị thực hiện cho phiếu nhận hàng này";
			return false;
		}
		
		if (this.KhoNhanId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn kho nhận cho phiếu nhận hàng này";
			return false;
		} else {
			if (this.loaikho.equals("5") || this.loaikho.equals("8")) {
				if (this.khachhangId.trim().length() <= 0) {
					this.msg = "Vui lòng chọn khách hàng cho kho nhận này";
					return false;
				}
			} else {
				this.khachhangId = "NULL";
			}
		}

		if (this.ndnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}

		if (this.loaihanghoa.equals("0") && this.ldnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lý do nhận hàng";
			return false;
		}

		String query = "";

		if (this.nccId.trim().length() > 0) {
			String query1 = "";
			query1 = "select SOHIEUTAIKHOAN from ERP_NHACUNGCAP ncc inner join ERP_TAIKHOANKT tk on ncc.TAIKHOAN_FK = tk.PK_SEQ where ncc.PK_SEQ = "
					+ this.nccId;

			ResultSet rsNG = db.get(query1);
			try {
				if (rsNG.next()) {
					if (rsNG.getString("SOHIEUTAIKHOAN").indexOf("331120") > 0) { // nha
																					// cung
																					// cap
																					// co
																					// so
																					// hieu
																					// tai
																					// khoan
																					// 331120
						this.isNCCNK = "1";
					} else {
						this.isNCCNK = "0";
					}

				}
				rsNG.close();
			} catch (Exception e1) {
			}
		}

		if (this.hdNccId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn Số hóa đơn NCC ";
			return false;
		}

		try {
			db.getConnection().setAutoCommit(false);

			String muahang_fk = "null";
			String trahang_fk = "null";

			if (this.ndnId.equals("100000")) {
				if (this.poId.trim().length() > 0)
					muahang_fk = this.poId;
			} else {
				if (this.poId.trim().length() > 0)
					trahang_fk = this.poId;
			}

			String ldn_fk = "null";
			if (this.ldnId.trim().length() > 0)
				ldn_fk = this.ldnId;

			String NCC_KH_FK = "null";
			if (this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;

			String nppId = util.getIdNhapp(this.userId);
			/*
			 // gan kho cho xu ly=100047 la kho cho xu ly nguyen lieu trong bang erp_nhanhang
			 //
			 //
			 */

			System.out.println("loaimh " + this.loaimh);
			query = "update ERP_NHANHANG set KHACHHANGKYGUI_FK = " + this.khachhangId + " , KHONHAN_FK = " + this.KhoNhanId + ", MUAHANG_FK = "
					+ this.muahang_fk1 + ", NGAYNHAN = '" + this.ngaynhanhang + "', NOIDUNGNHAN_FK = '" + this.ndnId + "', " + "DIENGIAI = N'"
					+ this.diengiai + "', " + "DONVITHUCHIEN_FK = '" + this.dvthId + "'," + " NGAYSUA = '" + this.getDateTime() + "', "
					+ "NGUOISUA = '" + this.userId + "', NoiDungNhap_fk = " + ldn_fk + "," + " NCC_KH_FK = " + NCC_KH_FK + ", hdNCC_fk = "
					+ this.hdNccId + ", tigia = '" + this.tigia + "', GHICHU = N'" + this.ghichu + "', NHAPHANPHOI_FK = " + nppId + " "
					+ "where pk_seq = '" + this.id + "'";

			System.out.println("Query update: " + query);

			if (!db.update(query)) {
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_NHANHANG_SANPHAM where nhanhang_fk = '" + this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_NHANHANG_SANPHAM: " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete ERP_NHANHANG_SP_CHITIET where nhanhang_fk = '" + this.id + "'";
			if (!db.update(query)) {
				this.msg = "Khong the cap nhat ERP_NHANHANG_SP_CHITIET: " + query;
				db.getConnection().rollback();
				return false;
			}

			if (this.spList.size() > 0) {
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);

					if (sp.getSoluongnhan() != "") // chi luu nhung san pham
													// nguoi dung nhap so luong
					{
						String SanPham_FK = "NULL";
						String ChiPhi_FK = "NULL";
						String TaiSan_FK = "NULL";
						String CCDC_FK = "NULL";

						if (this.loaihanghoa.equals("0")) {
							SanPham_FK = sp.getId();
							ChiPhi_FK = "NULL";
							TaiSan_FK = "NULL";
							CCDC_FK = "NULL";
						} else {
							if (this.loaihanghoa.equals("1")) {
								SanPham_FK = "NULL";
								ChiPhi_FK = "NULL";
								TaiSan_FK = sp.getId();
								CCDC_FK = "NULL";
							} else {
								if (loaihanghoa.equals("3")) {
									SanPham_FK = "NULL";
									ChiPhi_FK = "NULL";
									TaiSan_FK = "NULL";
									CCDC_FK = sp.getId();
								} else {
									SanPham_FK = "NULL";
									ChiPhi_FK = sp.getId();
									TaiSan_FK = "NULL";
									CCDC_FK = "NULL";
								}
							}
						}

						double soluongnhan = 0;
						if (sp.getSoluongnhan().trim().length() <= 0) {
							this.msg = "Bạn phải nhập số lượng nhận cho sản phẩm: " + sp.getMa();
							db.getConnection().rollback();
							return false;
						} else {
							soluongnhan = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
							System.out.println("so luong nhan " + soluongnhan);
							if (sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");

							double slgMax = Double.parseDouble(sp.getSoluongMaxNhan().replaceAll(",", ""));
							System.out.println("so luong max nhan " + slgMax);
							if (soluongnhan > slgMax) {
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa()
										+ " không được phép vượt quá tổng đặt (" + sp.getSoluongdat()
										+ ") và dung sai cho phép ( " + sp.getDungsai()
										+ "%). Vì thế bạn chỉ có thể nhận tối đa là ( " + slgMax + " )  ";
								db.getConnection().rollback();
								return false;
							}
						}

						String khonhan = sp.getKhonhanId().trim().length() <= 0 ? "null" : sp.getKhonhanId().trim();
						if (this.loaihanghoa.equals("0") && sp.getKhonhanId().trim().length() <= 0) {
							this.msg = "Vui lòng kiểm tra lại kho nhận của sản phẩm ( " + sp.getMa() + " ) ";
							db.getConnection().rollback();
							return false;
						}

						// Với Mua hàng nhập khẩu : lưu thêm số ngày vượt mức
						// miễn phí , số lượng >> sau này tính chi phí
						query = "SELECT case when DATEDIFF(day,nksp.NGAYNHAP,'" + this.ngaynhanhang
								+ "') <= nksp.SONGAYLUUKHO then 0 " + "       else DATEDIFF(day,nksp.NGAYNHAP,'"
								+ this.ngaynhanhang + "') - nksp.SONGAYLUUKHO end SONGAYVUOTMUC "
								+ "FROM ERP_NHAPKHONHAPKHAU_SANPHAM nksp inner join ERP_NHAPKHONHAPKHAU nk on nksp.NHAPKHO_FK = nk.PK_SEQ "
								+ "WHERE nk.MUAHANG_FK = " + this.muahang_fk1 + " AND nksp.SANPHAM_FK = " + SanPham_FK
								+ " ";
						ResultSet rsTT = db.get(query);
						int songayvuotmuc = 0;
						double soluongvuotmuc = 0;
						if (rsTT.next()) {

							songayvuotmuc = rsTT.getInt("SONGAYVUOTMUC");

						}
						rsTT.close();

						if (songayvuotmuc > 0)
							soluongvuotmuc = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
						
						// Kiểm định =1 , không kiểm định = 0;
						String kho = "";
						if(sp.getIsKiemDinh().equals("0")){
							kho = this.KhoNhanId;
						} else {
							kho = this.khoChoXuLy;
						}
						
						query = " insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, MUAHANG_FK ,SANPHAM_FK, TAISAN_FK, CCDC_FK, " +
								" CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI," +
								" DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC, IDMARQUETTE,SPKIEMDINH,TILEQUYDOI_DOLUONG) "
								+ "values('" + this.id + "', " + sp.getMuahang_fk() + ", " + SanPham_FK + ", "
								+ TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', N'"
								+ sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + kho + ", " + "'"
								+ sp.getSoluongdat().replaceAll(",", "") + "',  '"
								+ sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', "
								+ Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + sp.getTiente()
								+ "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "")
								+ "' , " + songayvuotmuc + ", " + soluongvuotmuc + ","+sp.getIdmarquette()+",'"
								+sp.getIsKiemDinh()+"',"+sp.getTiLeQuyDoiDv()+" )";
						System.out.println("insert nhanhang_sanpham: "+query);
						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}

						double tongchitiet = 0;
						
						List<DetailMeSp> detailList = sp.getListDetailMeSp();
						for (int j = 0; j < detailList.size(); j++) {
							DetailMeSp detail = detailList.get(j);
							
								/*
								 * set co dinh cho khu_fk =100000 (khu biet tru) trong bang ERP_NHANHANG_SP_CHITIET
								 * Số thùng không dùng nhé
								 * 03/06/2016
								 */
							
								String nsx_fk = (detail.getNsxId()==null || detail.getNsxId().trim().length()<1)?null:detail.getNsxId();
							
								query = " insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, MUAHANG_FK, SANPHAM_FK, LANNHAN, SOLO,MATHUNG, " +
										" SOLUONG, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,BIN_FK, MAME,GIATHEOLO,NGAYNHAPKHO,ISKIEMDINH,nsx_fk,MARQ) "
										+ "values('" + this.id + "', " + sp.getMuahang_fk() + ", '" + sp.getId() + "', '"
										+ Integer.toString(j + 1) + "', '" + detail.getSoLo() + "','"+detail.getMaThung()+"', " + "'"
										+ detail.getSoLuong().replaceAll(",", "") + "', '"
										+ detail.getNgaySanXuat() + "', '" + detail.getNgayHetHan() + "','"
										+ sp.getNgaynhandukien() + "','"
										+ detail.getKhuVuc() +"','" + detail.getMe() + "',"+sp.getDongia()+",'"+this.ngaynhanhang+"','"+sp.getIsKiemDinh()+"',"+nsx_fk+",N'"+detail.getMarrquet()+"')";
								
								tongchitiet = tongchitiet + Double.parseDouble(detail.getSoLuong().replaceAll(",", ""));
								//System.out.println("11.Insert Nhan hang ERP_NHANHANG_SP_CHITIET: " + query);

								if (!db.update(query)) {
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
									db.getConnection().rollback();
									return false;
								}
						}
						
						if (this.loaihanghoa.equals("0")) {
							//System.out.println("[soluongnhan] "+sp.getSoluongnhan()+", [soluongct] "+tongchitiet);
							NumberFormat formatter_3le = new DecimalFormat("#######.###"); 
							NumberFormat formatter_4le = new DecimalFormat("#######.####"); 
							tongchitiet = Double.parseDouble( formatter_4le.format(tongchitiet));
							 
							double tong_=   Double.parseDouble( formatter_4le.format(Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""))));
							
							if ( tong_  -  tongchitiet != 0) {
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :" + sp.getMa();
								db.getConnection().rollback();
								return false;
							}

						}

					}
				}
			}

			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_NhanHang", new String[] { this.id } );
			
			
			// Cập nhật lại xem đã hoàn tất nhận hàng hay chưa?
			boolean check = checkHoanTatNhanHang();
			check = this.UpdateHoanTatNhanHang(check, this.id);
			if( check == false){
				this.msg = "Cập nhật trạng thái hoàn tất nhận hàng thất bại";
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			this.msg = "Lỗi tạo nhận hàng: " + e.getMessage();
			e.printStackTrace();
			db.update("rollback");

			return false;
		}

	}

	private boolean IsCoKiemdinh() {

		try {

			if (this.loaihanghoa.equals("0")) {
				String str_sp = "";
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);

					if (sp.getSoluongnhan() != "") // chi luu nhung san pham
													// nguoi dung nhap so luong
					{

						if (Double.parseDouble(sp.getSoluongnhan()) > 0) {

							str_sp = (str_sp.length() > 0 ? str_sp + "," : "") + sp.getId();

						}

					}
				}
				if (str_sp.length() > 0) {
					String query = "SELECT PK_SEQ,KIEMTRADINHLUONG, KIEMTRADINHTINH FROM SANPHAM SP WHERE (SP.KIEMTRADINHLUONG=1 OR SP.KIEMTRADINHTINH='1') AND  PK_SEQ IN ("
							+ str_sp + ")";
					ResultSet rs = db.get(query);
					if (rs.next()) {
						rs.close();
						return true;
					}
					rs.close();

				}
			} else {
				return false;
			}

		} catch (Exception err) {
			err.printStackTrace();
		}
		return false;
	}

	public void updateDonmuahang(String poId) {
		this.poId = poId;

		// Cap nhat trang thai PO la hoan tat neu tong so luong nhan >= tong so
		// luong dat
		if (this.id.length() > 0) {
			String query = "select noidungnhan_fk from ERP_NhanHang where pk_seq = '" + this.id + "'";
			ResultSet rsNoiDungNhan = db.get(query);
			try {
				if (rsNoiDungNhan.next()) {
					this.ndnId = rsNoiDungNhan.getString("noidungnhan_fk");
				}
				rsNoiDungNhan.close();
			} catch (Exception e1) {
			}
		}

		if (this.ndnId.equals("100000")) {
			/*
			 * query = "select count(muahang.SANPHAM_FK) as sodong   " +
			 * "from   " + "(    " +
			 * "select case a.loaihanghoa_fk when 0 then b.SANPHAM_FK when 1 then b.TaiSan_fk else b.ChiPhi_fk end as SANPHAM_FK, "
			 * +
			 * "( isnull(b.DUNGSAI, 0) * sum(SOLUONG) / 100 ) + sum(SOLUONG)  as SOLUONG        "
			 * +
			 * "from erp_muahang a inner join erp_muahang_sp b on b.MuaHang_FK = a.pk_seq    "
			 * + "where MUAHANG_FK = '" + this.poId + "'    " +
			 * "group by a.loaihanghoa_fk, b.SANPHAM_FK, b.TaiSan_fk, b.ChiPhi_fk, b.DUNGSAI  "
			 * + ") " + "muahang   left join    " + "( " +
			 * "select case a.loaihanghoa_fk when 0 then b.SANPHAM_FK when 1 then b.TaiSan_fk else b.ChiPhi_fk end as SANPHAM_FK,  "
			 * + "isnull(SUM(b.soluongnhan), '0') as soluong   " +
			 * "from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  "
			 * + "where a.MUAHANG_FK = '" + this.poId +
			 * "' and a.TRANGTHAI != '3'   " +
			 * "group by a.loaihanghoa_fk, b.SANPHAM_FK, b.TaiSan_fk, b.ChiPhi_fk  "
			 * + ") nhanhang   " +
			 * "on muahang.SANPHAM_FK = nhanhang.SANPHAM_FK  " +
			 * "where muahang.SOLUONG - isnull(nhanhang.soluong, '0') > 0";
			 * 
			 * System.out.println("1.Check don mua hang: " + query);
			 * 
			 * ResultSet nhanhangRs = db.get(query); int sodong = 0; if
			 * (nhanhangRs != null) { try { if (nhanhangRs.next()) { sodong =
			 * nhanhangRs.getInt("sodong"); nhanhangRs.close(); } } catch
			 * (Exception e) { }
			 * 
			 * if (sodong <= 0) // het sp co the nhan { query =
			 * "update ERP_MUAHANG set trangthai = '2' where pk_seq = '" +
			 * this.poId + "'"; } else { //Neu da xoa nhan hang, ma chua co nhan
			 * hang phat sinh thi trang thai = 0 String trangthai = "0"; query =
			 * "select COUNT(*) as soDong    " +
			 * "from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK    "
			 * + "where a.MUAHANG_FK = '" + this.poId +
			 * "' and a.TRANGTHAI != '3'  ";
			 * 
			 * ResultSet rsCheck = db.get(query); try { if(rsCheck.next()) {
			 * if(rsCheck.getInt("soDong") > 0) trangthai = "1"; }
			 * rsCheck.close(); } catch (SQLException e) {}
			 * 
			 * 
			 * query = "update ERP_MUAHANG set trangthai = '" + trangthai +
			 * "' where pk_seq = '" + this.poId + "'"; }
			 * 
			 * db.update(query); System.out.println("2.Cap nhat mua hang: " +
			 * query); }
			 */
		} else {
			/*
			 * query = "select count(muahang.SANPHAM_FK) as sodong " +
			 * "from  (  " + "select SANPHAM_FK, sum(SOLUONG) as SOLUONG   " +
			 * "from erp_trahang_sp where TRAHANG_FK = '" + this.poId + "'  " +
			 * "group by SANPHAM_FK ) muahang  " + "left join  " +
			 * "(select b.SANPHAM_FK, isnull(SUM(b.soluongnhan), '0') as soluong  "
			 * +
			 * "from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK "
			 * + "where a.TRAHANG_FK = '" + this.poId +
			 * "' and a.TRANGTHAI != '3'  group by b.SANPHAM_FK) nhanhang  " +
			 * "on muahang.SANPHAM_FK = nhanhang.SANPHAM_FK " +
			 * "where muahang.SOLUONG - isnull(nhanhang.soluong, '0') > 0";
			 */

			String query = " SELECT COUNT(*) as sodong " + "FROM   " + "(    "
					+ "SELECT  A.SANPHAM_FK, A.SOLUONG, A.DONGIA, DTH.NGAYTRA as ngayNhan, 0 as DungSai, B.MA AS SPMA,  B.TEN1 AS SPTEN, B.HANSUDUNG, C.DONVI     "
					+ "FROM DONTRAHANG DTH inner join DONTRAHANG_SP A on DTH.PK_SEQ = A.DONTRAHANG_FK   "
					+ "left join SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ   "
					+ "LEFT JOIN 	DONVIDOLUONG C ON C.PK_SEQ=B.DVDL_FK     " + "WHERE DTH.PK_SEQ = '" + this.poId
					+ "'  AND DTH.TRANGTHAI in (3, 4)    " + ")   " + "MUAHANG   LEFT JOIN    " + "(   "
					+ "SELECT B.SANPHAM_FK, B.NGAYNHANDUKIEN, ISNULL(SUM(B.SOLUONGNHAN), '0' ) AS SOLUONG 	  "
					+ "FROM ERP_NHANHANG A  INNER JOIN ERP_NHANHANG_SANPHAM B ON A.PK_SEQ = B.NHANHANG_FK    "
					+ "WHERE A.TRAHANG_FK = '" + this.poId + "' AND A.TRANGTHAI != '3'    "
					+ "GROUP BY B.SANPHAM_FK, NGAYNHANDUKIEN   " + ")   "
					+ "NHANHANG  ON MUAHANG.SANPHAM_FK = NHANHANG.SANPHAM_FK  AND MUAHANG.NGAYNHAN = NHANHANG.NGAYNHANDUKIEN    "
					+ "WHERE MUAHANG.SOLUONG - ISNULL(NHANHANG.SOLUONG, '0') > 0 ";

			System.out.println("___Check don tra hang: " + query);

			ResultSet nhanhangRs = db.get(query);
			int sodong = 0;
			if (nhanhangRs != null) {
				try {
					if (nhanhangRs.next()) {
						sodong = nhanhangRs.getInt("sodong");
						nhanhangRs.close();
					}
				} catch (SQLException e) {
				}

				if (sodong <= 0) // het sp cp the nhan
				{
					query = "update DONTRAHANG set trangthai = '4' where pk_seq = '" + this.poId + "'";
				} else {
					String trangthai = "0";
					query = "select COUNT(*) as soDong    "
							+ "from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK    "
							+ "where a.TRAHANG_FK = '" + this.poId + "' and a.TRANGTHAI != '3'  ";

					ResultSet rsCheck = db.get(query);
					try {
						if (rsCheck.next()) {
							if (rsCheck.getInt("soDong") > 0)
								trangthai = "4";
						}
						rsCheck.close();
					} catch (SQLException e) {
					}

					query = "update DONTRAHANG set trangthai = '" + trangthai + "' where pk_seq = '" + this.poId + "'";
				}

				db.update(query);
				System.out.println("Cap nhat tra hang: " + query);
			}
		}
	}

	// 0: CK từ kho ký gửi NCC > Kho NM/NK ; 1 ngược lại
	private String TaoChuyenKhoTuDong(dbutils db, String userId, String ctyId, String nppId, String khoxuat,
			String khonhan, String nhId, String nccXuatId, String nccNhanId, String khXuatId, String khNhanId,
			String loaiChuyen) {
		String msg = "";

		// Nội dung xuất: Chuyển kho bên ngoài 100024
		// Đã trừ cập nhật kho ở phía trên

		try {

			String query = " insert ERP_CHUYENKHO(CONGTY_FK, NGUOIDENGHI,NHOMKENH_FK,NPP_FK,NGUOINHAN ,noidungxuat_fk, ngaychuyen, ngaynhan, ngaychot, lydo, ghichu, trangthai,"
					+ " khoxuat_fk, khonhan_fk, trangthaisp, ngaytao, nguoitao, ngaysua, nguoisua, NCC_CHUYEN_FK, NCC_NHAN_FK ,KYHIEU, SOCHUNGTU, LENHDIEUDONG, NGAYDIEUDONG,"
					+ " NGUOIDIEUDONG, VEVIEC, NGUOIVANCHUYEN, PHUONGTIEN, HOPDONG,KH_XUAT_FK,KH_NHAN_FK , NHANHANG_FK) "
					+ " values(" + ctyId + ",N'','100000'," + nppId + ", N'','100024', '" + this.getDateTime() + "', '"
					+ this.getDateTime() + "', '" + this.getDateTime()
					+ "', N'', N'Chuyển kho tự động từ nhận hàng' + '" + nhId + "' , '3',  " + "        " + khoxuat
					+ ", " + khonhan + ", '1', '" + this.getDateTime() + "', '" + userId + "', '" + this.getDateTime()
					+ "', '" + userId + "'," + nccXuatId + ", " + nccNhanId + ", "
					+ "        '','','','',N'',N'',N'',N'',N''," + khXuatId + "," + khNhanId + ", " + nhId + ")";
			System.out.println("---1 insert erp_chuyenkho " + query);
			if (!db.update(query)) {
				msg = "Không thể tạo mới ERP_CHUYENKHO " + query;
				return msg;
			}

			String ycnlCurrent = "";
			query = " select IDENT_CURRENT('ERP_CHUYENKHO') as ckId";

			ResultSet rsPxk = db.get(query);
			if (rsPxk.next()) {
				ycnlCurrent = rsPxk.getString("ckId");
				rsPxk.close();
			}

			query = " SELECT DISTINCT c.ma as masp , SANPHAM_FK, DONGIA, b.ngaynhandukien, b.SOLUONGDAT, b.SOLUONGNHAN, nh.MUAHANG_FK, "
					+ "        b.SOLOKYGUI, b.NGAYSANXUAT, b.NGAYHETHAN "
					+ " FROM ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM b on nh.PK_SEQ = b.NHANHANG_FK  "
					+ "      inner join ERP_SANPHAM c on b.SANPHAM_FK = c.PK_SEQ " + " WHERE b.NHANHANG_FK = '" + nhId
					+ "'  ";
			System.out.println("---2 select erp_nhanhang " + query);
			ResultSet rs = db.get(query);
			String muahang_fk = "";
			if (rs != null) {
				while (rs.next()) {
					muahang_fk = rs.getString("MUAHANG_FK");

					query = " INSERT ERP_CHUYENKHO_SANPHAM( CHUYENKHO_FK, SANPHAM_FK ,SOLUONGYEUCAU , SOLUONGXUAT , SOLUONGNHAN,DONGIA ) "
							+ " values( '" + ycnlCurrent + "', '" + rs.getString("SANPHAM_FK") + "',"
							+ rs.getString("SOLUONGNHAN") + " ,  " + rs.getString("SOLUONGNHAN") + " ,  "
							+ rs.getString("SOLUONGNHAN") + ", " + rs.getString("DONGIA") + "  ) ";
					System.out.println("---3 insert erp_chuyenkho_sanpham " + query);
					if (!db.update(query)) {
						msg = "Không thể tạo mới ERP_CHUYENKHO_SANPHAM : " + query;
						return msg;
					}

					// Lay lo NM/NK

					String query1 = "";
					String solo_NK = "";
					String ngaynhap_NK = "";
					System.out.println("loai mh " + this.loaimh);
					if (this.loaimh.equals("0")) {
						query1 = "SELECT ct.SOLO, ct.NGAYNHAP "
								+ "FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET ct on nk.PK_SEQ = ct.NHAPKHO_FK "
								+ "WHERE nk.MUAHANG_FK = " + muahang_fk + " and ct.SANPHAM_FK =  "
								+ rs.getString("SANPHAM_FK") + " ";
					} else if (this.loaimh.equals("1")) {
						query1 = "SELECT ct.SOLO, nk.NGAYNHAP "
								+ "FROM ERP_NHAPKHONHAMAY nk inner join ERP_NHAPKHONHAMAY_SP_CHITIET ct on nk.PK_SEQ = ct.NHAMAY_FK "
								+ "WHERE nk.MUAHANG_FK in (select distinct b.MUAHANG_FK "
								+ "                        from ERP_PHANBOMUAHANG_PO a inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ "
								+ "                        where a.PODUOCPB =  " + muahang_fk
								+ ")  and ct.SANPHAM_FK =  " + rs.getString("SANPHAM_FK") + " ";
					}
					System.out.println("---4 Cau lay so lo " + query1);
					ResultSet rsSL = db.get(query1);

					if (rsSL != null) {
						while (rsSL.next()) {
							solo_NK = rsSL.getString("SOLO");
							ngaynhap_NK = rsSL.getString("NGAYNHAP");
						}
						rsSL.next();
					}

					// Nếu CK : từ kho ký gửi NCC >> Kho NK/NM
					if (loaiChuyen.equals("0")) {
						query = " INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) "
								+ " VALUES (" + ycnlCurrent + "," + rs.getString("SANPHAM_FK") + ",'"
								+ rs.getString("SOLOKYGUI") + "' , NULL " + " ,'" + rs.getString("NGAYSANXUAT") + "',"
								+ rs.getString("SOLUONGNHAN") + "," + rs.getString("DONGIA") + ",'"
								+ rs.getString("NGAYHETHAN") + "')";

						if (!db.update(query)) {
							msg = "Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
							return msg;
						}

						if (khonhan != "NULL") {
							query = " INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK, CK_SP_XH_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) "
									+ " VALUES (" + ycnlCurrent + ", NULL, " + rs.getString("SANPHAM_FK") + ",'"
									+ solo_NK + "' , NULL " + " ,'" + ngaynhap_NK + "'," + rs.getString("SOLUONGNHAN")
									+ "," + rs.getString("DONGIA") + ",'')";

							if (!db.update(query)) {
								msg = "Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
								return msg;
							}
						}

					} else // CK : Kho NM/NK >> Kho ký gửi
					{
						if (khoxuat != "NULL") {
							query = " INSERT INTO ERP_CHUYENKHO_SP_XUATHANG (CHUYENKHO_FK, SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) "
									+ " VALUES (" + ycnlCurrent + ", " + rs.getString("SANPHAM_FK") + ",'" + solo_NK
									+ "' , NULL " + " ,'" + ngaynhap_NK + "'," + rs.getString("SOLUONGNHAN") + ","
									+ rs.getString("DONGIA") + ",'')";

							if (!db.update(query)) {
								msg = "Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
								return msg;
							}
						}

						query = " INSERT INTO ERP_CHUYENKHO_SP_NHANHANG (CHUYENKHO_FK,SANPHAM_FK,SOLO,KHU,NGAYBATDAU,SOLUONG,DONGIA,NGAYHETHAN ) "
								+ " VALUES (" + ycnlCurrent + "," + rs.getString("SANPHAM_FK") + ",'"
								+ rs.getString("SOLOKYGUI") + "' , NULL " + " ,'" + rs.getString("NGAYSANXUAT") + "',"
								+ rs.getString("SOLUONGNHAN") + "," + rs.getString("DONGIA") + ",'"
								+ rs.getString("NGAYHETHAN") + "')";

						if (!db.update(query)) {
							msg = "Không thể tạo mới ERP_CHUYENKHO_SP_XUATHANG : " + query;
							return msg;
						}

					}

				}
				rs.close();
			}

		} catch (Exception e) {
			e.printStackTrace();
			return "Lỗi trong quá trình tạo phiếu Chuyển kho tự động";

		}

		return msg;
	}

	public void close() {
		try {

			if (spList != null) {
				spList.clear();
			}

			if (ndnRs != null) {
				ndnRs.close();
			}

			if (mahangmuaRs != null) {
				mahangmuaRs.close();
			}
			if (dvthRs != null) {
				dvthRs.close();
			}
			if (poRs != null) {
				poRs.close();
			}
			db.shutDown();

		} catch (Exception er) {

		}
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
		
		
		
	}

	public int getNgayhethan() {
		return this.ngayhethan;
	}

	public void setNgayhethan(int ngayhethan) {
		this.ngayhethan = ngayhethan;
	}

	public String getNdnId() {

		return this.ndnId;
	}

	public void setNdnId(String mhmId) {

		this.ndnId = mhmId;
	}

	public ResultSet getNdnList() {

		return this.ndnRs;
	}

	public void setNdnList(ResultSet ndnlist) {

		this.ndnRs = ndnlist;
	}

	public String getLoaihanghoa() {
		return this.loaihanghoa;
	}

	public void setLoaihanghoa(String loaihh) {
		this.loaihanghoa = loaihh;
	}

	public String getNgaychot() {
		return this.ngaychot;
	}

	public void setNgaychot(String ngaychot) {
		this.ngaychot = ngaychot;
	}

	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public void setLdnId(String ndnId) {

		this.ldnId = ndnId;
	}

	public String getLdnId() {

		return this.ldnId;
	}

	public ResultSet getLdnList() {

		return this.ldnRs;
	}

	public void setLdnList(ResultSet ldnList) {

		this.ldnRs = ldnList;
	}

	public void setNccId(String ndnId) {

		this.nccId = ndnId;
	}

	public String getNccId() {

		return this.nccId;
	}

	public ResultSet getNccList() {

		return this.nccRs;
	}

	public void setNccList(ResultSet nccList) {

		this.nccRs = nccList;
	}

	public String getIsPONK() {

		return this.isPONK;
	}

	public void setIsPONK(String poNK) {

		this.isPONK = poNK;
	}

	public void setHdNccId(String hdNccId) {

		this.hdNccId = hdNccId;
	}

	public String getHdNccId() {

		return this.hdNccId;
	}

	public ResultSet getHdNccList() {

		return this.hdNccRs;
	}

	public void setHdNccList(ResultSet hdnccList) {

		this.hdNccRs = hdnccList;
	}

	public boolean kt_nhanhang_theoHDNCC_bivuot(String hoadonncc, String ncc_fk) {
		String query = "";
		query = " select top(1) hd.pk_seq as hdId, hd_dmh.SANPHAM_FK,   \n"
				+ " isnull((select SUM(nhsp.SOLUONGNHAN)      \n" + "from ERP_NHANHANG nh      \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK      \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  and nh.trangthai not in (3,4)     \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0) - SUM(hd_dmh.SOLUONG)  as soluongconlai      \n"
				+ "from ERP_HOADONNCC hd      \n" + "inner join ERP_PARK p on hd.park_fk = p.pk_seq      \n"
				+ "inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK      \n"
				+ "where p.ncc_fk=" + ncc_fk + " and hd.sohoadon='" + hoadonncc.trim()
				+ "'  and hd_dmh.SOLUONG >0      \n" + "group by hd.pk_seq, hd_dmh.SANPHAM_FK    \n"
				+ "having  (   isnull((select SUM(nhsp.SOLUONGNHAN) \n" + "from ERP_NHANHANG nh  \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK  \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0)  - SUM(hd_dmh.SOLUONG)  ) > 0   ";

		System.out.println(" Kt nhan hang vuot :" + query);
		ResultSet rs = db.get(query);

		try {
			String hd = "";
			double soluongconlai;
			while (rs.next()) {
				hd = rs.getString("hdId");
				soluongconlai = rs.getDouble("soluongconlai");
			}
			rs.close();
			if (hd.length() > 0) {
				this.msg = "Nhận hàng của hoá đơn " + hd + " đang có sản phẩm bị quá số lượng so với hoá đơn  ";
				return true;
			} else {
				return false;
			}

		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
	}

	public void kt_capnhattrangthai_hoadonNCC(String hoadonncc, String ncc_fk, String nhanhang_ID) {
		String query = "";
		query = " select top(1) hd.pk_seq as hdId, hd_dmh.SANPHAM_FK,    \n"
				+ " isnull((select SUM(nhsp.SOLUONGNHAN)      \n" + "from ERP_NHANHANG nh      \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK      \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK   and nh.trangthai not in (3,4)     \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0) -  SUM(hd_dmh.SOLUONG) as soluongconlai      \n"
				+ "from ERP_HOADONNCC hd      \n" + "inner join ERP_PARK p on hd.park_fk = p.pk_seq      \n"
				+ "inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK      \n"
				+ "where p.ncc_fk=" + ncc_fk + " and hd.sohoadon='" + hoadonncc.trim()
				+ "'  and hd_dmh.SOLUONG >0      \n" + "group by hd.pk_seq, hd_dmh.SANPHAM_FK    \n"
				+ "having  (  isnull((select SUM(nhsp.SOLUONGNHAN) \n" + "from ERP_NHANHANG nh  \n"
				+ "inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ= nhsp.NHANHANG_FK  \n"
				+ "where nh.hdNCC_fk=hd.pk_seq and nhsp.SANPHAM_FK = hd_dmh.SANPHAM_FK  \n"
				+ "group by nh.hdNCC_fk, nhsp.SANPHAM_FK),0)  -  SUM(hd_dmh.SOLUONG) ) <>  0   ";

		System.out.println(" Kt nhan hang du HD chua :" + query);
		ResultSet rs = db.get(query);

		try {
			if (!rs.next()) {
				query = " update erp_hoadonncc_donmuahang set is_nhanhang=1 where hoadonncc_fk=(select hdNCC_fk from erp_nhanhang where pk_seq ="
						+ nhanhang_ID + ") ";
				db.update(query);
			}

		} catch (Exception e) {
			e.printStackTrace();

		}
	}

	public boolean suaSoHoaDon() {

		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "UpDate ERP_NHANHANG set SoHoaDon='" + this.sohoadon + "',DienGiai=N'" + this.diengiai
					+ "' where pk_Seq='" + this.id + "'";
			if (!this.db.update(query)) {
				this.msg = "Lỗi khi cập nhật số hóa đơn " + query;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Lỗi khi cập nhật số hóa đơn(Exception) " + e.getMessage();
			e.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}

	public String getLydonhan() {

		return this.lydo;
	}

	public void setLydo(String lydo) {
		this.lydo = lydo;
	}

	public String getPhongBan() {

		return this.pb;
	}

	public void setPhongBan(String phongban) {

		this.pb = phongban;
	}

	public boolean getIsKhoNhanQL_Khuvuc() {

		return this.IsKhoNhanQL_khuvuc;
	}

	public void setIsKhoNhanQL_Khuvuc(boolean bien) {

		this.IsKhoNhanQL_khuvuc = bien;
	}

	public ResultSet getrskhoNhan() {

		return this.RsKhoNhan;
	}

	public void setrskhoNhan(ResultSet rskhoNhan) {

		this.RsKhoNhan = rskhoNhan;
	}

	public String getKhoNhanId() {

		return this.KhoNhanId;
	}

	public void setKhoNhanId(String KHoNhanId) {

		this.KhoNhanId = KHoNhanId;
	}

	public Integer getIs_saungayKS() {
		return this.is_saungayKS;
	}

	public void setIs_saungayKS(Integer is_saungayKS) {
		this.is_saungayKS = is_saungayKS;
	}

	public String getGhichu() {

		return this.ghichu;
	}

	public void setGhichu(String ghichu) {

		this.ghichu = ghichu;
	}

	public String getLoaimh() {
		return this.loaimh;
	}

	public void setLoaimh(String loaimh) {
		this.loaimh = loaimh;
	}

	public String getIsTudong() {
		return this.isTudong;
	}

	public void setIsTudong(String isTudong) {
		this.isTudong = isTudong;
	}

	public String getKhachhangId() {
		return this.khachhangId;
	}

	public void setKhachhangId(String khachhangId) {
		this.khachhangId = khachhangId;
	}

	public ResultSet getKhachhangRs() {
		return this.khachhangRs;
	}

	public void setKhachhangRs(ResultSet khachhangRs) {
		this.khachhangRs = khachhangRs;
	}

	public String getLoaikho() {
		return this.loaikho;
	}

	public void setLoaikho(String loaikho) {
		this.loaikho = loaikho;
	}

	public void init_convert(String hoadonnccId, String loaihd) {

		String query = "";
		if(loaihd.equals("0"))
		{
			query = " select distinct DONVITHUCHIEN_FK, LOAIHANGHOA_FK "
				+ " from ERP_MUAHANG "
				+ "WHERE PK_SEQ IN (SELECT DISTINCT A.MUAHANG_FK "
				+ "					FROM ERP_HOADONNCC_DONMUAHANG A "
				+ "					WHERE HOADONNCC_FK = "	+ hoadonnccId + ") ";
		}
		else
		{
			query = " select distinct DONVITHUCHIEN_FK, LOAIHANGHOA_FK "
				+ " from ERP_MUAHANG "
				+ "WHERE PK_SEQ IN (SELECT DISTINCT A.MUAHANG_FK "
				+ "					FROM ERP_HOADONNCC_DONMUAHANG A WHERE HOADONNCC_FK = "	+ hoadonnccId + ") ";
		}

		System.out.println("[init convert] "+query);
		ResultSet RsKtr = db.get(query);

		try {
			while (RsKtr.next()) {
				this.loaihanghoa = RsKtr.getString("LOAIHANGHOA_FK");
				this.dvthId = RsKtr.getString("DONVITHUCHIEN_FK");
			}
			RsKtr.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		// lay Kho Nhan va Kho Cho Xu Ly
		query = " select KHOBIETTRU_FK, KHOTONTRU_FK, (select ngayghinhan from erp_park where pk_seq = park_fk)  as ngayghinhan   from ERP_HOADONNCC where pk_seq = "+ hoadonnccId;
		ResultSet rs = this.db.get(query);
		System.out.println("lay kho "+query);
		String khoBietTru= "";
		String khoTonTru = "";
			try{
			if(rs!=null){
				if(rs.next()){
					this.ngaynhanhang=rs.getString("ngayghinhan");
					khoBietTru= rs.getString("KHOBIETTRU_FK");
					khoTonTru = rs.getString("KHOTONTRU_FK");
				}
				rs.close();
			} 
			
			// lấy kho của User đăng nhập để định vị kho. Biệt trữ hay tồn trữ.
			
			// lấy kho mà người dùng được quyền xem
			String sql = " select khott_fk from NHANVIEN_KHOTT where nhanvien_fk ="+this.userId+" " +
				  " union select kho_fk from NHANVIEN_KHO where nhanvien_fk =" + this.userId;
			System.out.println("lay kho  1 "+sql);
			rs = this.db.get(sql);
			List<String> dsKho = new ArrayList<String>();
			if(rs!=null){
				while(rs.next()){
					dsKho.add(rs.getString("khott_fk"));
				}
				rs.close();
			}
			
			// check kho Biệt trữ.
			boolean check1 = this.ExistKho(dsKho, khoBietTru);
			// check kho Tồn trữ.
			boolean check2 = this.ExistKho(dsKho, khoTonTru);
			
			if(check1== true && check2 == true){
				this.khoChoXuLy = khoBietTru;
				this.KhoNhanId = khoTonTru;
				
			} else if( check1 == true){
				this.khoChoXuLy = khoBietTru;
				this.KhoNhanId = khoBietTru;
				
			} else if( check2 == true){
				this.khoChoXuLy = khoTonTru;
				this.KhoNhanId = khoTonTru;
			}
		
		}catch (Exception ex){
			ex.printStackTrace();
		}
		
	}

}
