package geso.traphaco.erp.beans.kiemdinhchatluong.kho.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.dinhtinheo.imp.DinhTinhEODetail;
import geso.traphaco.erp.beans.kiemdinhchatluong.kho.IErpKiemDinhChatLuongKho;
public class ErpKiemDinhChatLuongKho implements IErpKiemDinhChatLuongKho{
	String userId;
	String Id;
	
	String spId;
	String khoId;
	String soLo;
	
	String SoLuongKiemDinh, soluongDat, datCl;
	String Tongsoluongnhap;
	String Soluongchuakiem;
	String congtyId, trangthai, ngaykiem;
	String msg;
	ResultSet khoRs, rsSanPham;
	
	String denghixuly = "";
	String Soluongmau="0";
	dbutils db;
	// lấy các thùng để hiển thị ra kiểm định
	ResultSet rsSoThung;
	// dùng để lưu lại các đối tượng thùng
	String[] listSoThung;
	String[] listSoLuongThung;
	String[] listLayMauThung;
	String[] listDatThung;
	String[] listKhongDatThung;
	String[] listHamAm;
	String[] listHamLuong;
	String[] listKhoChiTiet;
	
	String loaimh;
	String maPhieu;
	String maMe;
	List<KiemDinhObj> listSpKiemDinh = new ArrayList<KiemDinhObj>();
	
	public ErpKiemDinhChatLuongKho() {
		super();
		this.userId = "";
		Id = "";
		this.spId = "";
		SoLuongKiemDinh = "0";
		this.soluongDat = "0";
		this.datCl = "";
		Tongsoluongnhap = "0";
		Soluongchuakiem = "0";
		this.congtyId = "";
		this.trangthai = "";
		this.ngaykiem = "";
		this.msg = "";
		this.denghixuly = "";
		Soluongmau = "0";
		this.loaimh = "2";
		this.maPhieu = "";
		this.maMe = "";
		this.khoId = "";
		this.soLo = "";
		db = new dbutils();
	}
	
	public ErpKiemDinhChatLuongKho(String userId, String id, String spId, String soLuongKiemDinh, String soluongDat, String datCl,
			String tongsoluongnhap, String soluongchuakiem, String congtyId, String trangthai, String ngaykiem, String msg, String denghixuly,
			String soluongmau, String[] listSoThung, String[] listSoLuongThung, String[] listLayMauThung, String[] listDatThung,
			String[] listKhongDatThung, String[] listHamAm, String[] listHamLuong, String[] listKhoChiTiet, String loaimh, String maPhieu,
			String maMe) {
		super();
		this.userId = userId;
		Id = id;
		this.spId = spId;
		SoLuongKiemDinh = soLuongKiemDinh;
		this.soluongDat = soluongDat;
		this.datCl = datCl;
		Tongsoluongnhap = tongsoluongnhap;
		Soluongchuakiem = soluongchuakiem;
		this.congtyId = congtyId;
		this.trangthai = trangthai;
		this.ngaykiem = ngaykiem;
		this.msg = msg;
		this.denghixuly = denghixuly;
		Soluongmau = soluongmau;
		this.listSoThung = listSoThung;
		this.listSoLuongThung = listSoLuongThung;
		this.listLayMauThung = listLayMauThung;
		this.listDatThung = listDatThung;
		this.listKhongDatThung = listKhongDatThung;
		this.listHamAm = listHamAm;
		this.listHamLuong = listHamLuong;
		this.listKhoChiTiet = listKhoChiTiet;
		this.loaimh = loaimh;
		this.maPhieu = maPhieu;
		this.maMe = maMe;
	}
	
	
	public List<KiemDinhObj> getListSpKiemDinh() {
		return listSpKiemDinh;
	}

	public void setListSpKiemDinh(List<KiemDinhObj> listSpKiemDinh) {
		this.listSpKiemDinh = listSpKiemDinh;
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getSpId() {
		return spId;
	}
	public void setSpId(String spId) {
		this.spId = spId;
	}
	public String getSoLuongKiemDinh() {
		return SoLuongKiemDinh;
	}
	public void setSoLuongKiemDinh(String soLuongKiemDinh) {
		SoLuongKiemDinh = soLuongKiemDinh;
	}
	public String getSoluongDat() {
		return soluongDat;
	}
	public void setSoluongDat(String soluongDat) {
		this.soluongDat = soluongDat;
	}
	public String getDatCl() {
		return datCl;
	}
	public void setDatCl(String datCl) {
		this.datCl = datCl;
	}
	public String getTongsoluongnhap() {
		return Tongsoluongnhap;
	}
	public void setTongsoluongnhap(String tongsoluongnhap) {
		Tongsoluongnhap = tongsoluongnhap;
	}
	public String getSoluongchuakiem() {
		return Soluongchuakiem;
	}
	public void setSoluongchuakiem(String soluongchuakiem) {
		Soluongchuakiem = soluongchuakiem;
	}
	public String getCongtyId() {
		return congtyId;
	}
	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}
	public String getTrangthai() {
		return trangthai;
	}
	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}
	public String getNgaykiem() {
		return ngaykiem;
	}
	public void setNgaykiem(String ngaykiem) {
		this.ngaykiem = ngaykiem;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public ResultSet getKhoRs() {
		return khoRs;
	}
	public void setKhoRs(ResultSet khoRs) {
		this.khoRs = khoRs;
	}
	public ResultSet getRsSanPham() {
		return rsSanPham;
	}
	public void setRsSanPham(ResultSet rsSanPham) {
		this.rsSanPham = rsSanPham;
	}
	public String getDenghixuly() {
		return denghixuly;
	}
	public void setDenghixuly(String denghixuly) {
		this.denghixuly = denghixuly;
	}
	public String getSoluongmau() {
		return Soluongmau;
	}
	public void setSoluongmau(String soluongmau) {
		Soluongmau = soluongmau;
	}
	public dbutils getDb() {
		return db;
	}
	public void setDb(dbutils db) {
		this.db = db;
	}
	public ResultSet getRsSoThung() {
		return rsSoThung;
	}
	public void setRsSoThung(ResultSet rsSoThung) {
		this.rsSoThung = rsSoThung;
	}
	public String[] getListSoThung() {
		return listSoThung;
	}
	public void setListSoThung(String[] listSoThung) {
		this.listSoThung = listSoThung;
	}
	public String[] getListSoLuongThung() {
		return listSoLuongThung;
	}
	public void setListSoLuongThung(String[] listSoLuongThung) {
		this.listSoLuongThung = listSoLuongThung;
	}
	public String[] getListLayMauThung() {
		return listLayMauThung;
	}
	public void setListLayMauThung(String[] listLayMauThung) {
		this.listLayMauThung = listLayMauThung;
	}
	public String[] getListDatThung() {
		return listDatThung;
	}
	public void setListDatThung(String[] listDatThung) {
		this.listDatThung = listDatThung;
	}
	public String[] getListKhongDatThung() {
		return listKhongDatThung;
	}
	public void setListKhongDatThung(String[] listKhongDatThung) {
		this.listKhongDatThung = listKhongDatThung;
	}
	public String[] getListHamAm() {
		return listHamAm;
	}
	public void setListHamAm(String[] listHamAm) {
		this.listHamAm = listHamAm;
	}
	public String[] getListHamLuong() {
		return listHamLuong;
	}
	public void setListHamLuong(String[] listHamLuong) {
		this.listHamLuong = listHamLuong;
	}
	public String[] getListKhoChiTiet() {
		return listKhoChiTiet;
	}
	public void setListKhoChiTiet(String[] listKhoChiTiet) {
		this.listKhoChiTiet = listKhoChiTiet;
	}
	public String getLoaimh() {
		return loaimh;
	}
	public void setLoaimh(String loaimh) {
		this.loaimh = loaimh;
	}
	public String getMaPhieu() {
		return maPhieu;
	}
	public void setMaPhieu(String maPhieu) {
		this.maPhieu = maPhieu;
	}
	public String getMaMe() {
		return maMe;
	}
	public void setMaMe(String maMe) {
		this.maMe = maMe;
	}
	public String getKhoId() {
		return khoId;
	}
	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}
	public String getSoLo() {
		return soLo;
	}
	public void setSoLo(String soLo) {
		this.soLo = soLo;
	}
	
	// lấy tất cả sản phẩm đang còn hoạt động, thuộc về 1 kho.
	private void createRsSanPham(String condition){
		String query = "select PK_SEQ, MA+'-'+TEN as TEN from ERP_SANPHAM where trangthai ='1'";
		if(condition.trim().length() >0){
			query += " \n and PK_SEQ in( select distinct SANPHAM_FK from ERP_KHOTT_SANPHAM where khott_fk = "+condition+")";
		
		}
		System.out.println("[init]khoRs: "+ query);
		this.rsSanPham = this.db.get(query);
	}
	
	// lấy tất cả sản phẩm đang còn hoạt động, thuộc về 1 kho.
	private void createRsKho(){
		String query = "select PK_SEQ, MA+'-'+TEN as TEN from ERP_KHOTT where trangthai ='1'";
		this.khoRs = this.db.get(query);
		System.out.println("[init]khoRs: "+ query);
	}
	
	public void init(){
		
		
	}
	
	
	// insert table details
	private boolean insertDetails(KiemDinhObj obj){
		try{
			
			String query = "insert into ERP_YEUCAUKIEMDINH_DETAILS(yeucaukiemdinh_fk, soluong, soluongmau, "
					+ " soluongdat, soluongkhongdat, hamam, hamluong, khochitiet_fk) values(?,?,?,?, ?,?,?,?)";
			
			double soluong = Double.parseDouble(obj.getSoLuong().replace(",", ""));
			double soluongmau = Double.parseDouble(obj.getSoLuongMau().replace(",", ""));
			double soluongdat = Double.parseDouble(obj.getSoLuongDat().replace(",", ""));
			double soluongkhongdat = Double.parseDouble(obj.getSoLuongKhongDat().replace(",", ""));
			String hamluong = String.valueOf(obj.getHamLuongNew());
			String hamam = String.valueOf(obj.getHamAmNew());
			String khochitiet_fk = obj.getKhoChiTiet_fk();
			
			PreparedStatement pre = this.db.getConnection().prepareStatement(query);
			pre.setInt(1, Integer.parseInt(this.Id));
			pre.setDouble(2,soluong);
			pre.setDouble(3, soluongmau);
			pre.setDouble(4, soluongdat);
			pre.setDouble(5, soluongkhongdat);
			pre.setString(6, hamam);
			pre.setString(7, hamluong);
			pre.setInt(8, Integer.parseInt(khochitiet_fk));
			
			int k = pre.executeUpdate();
			if(k !=1){
				this.db.getConnection().rollback();
				return false;
			}
			return true;
			
		} catch(Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
	}
	
	// function booked kho.
	private boolean BookedKho(double soluong, double booked, double available, String khochitiet_fk){
		
		PreparedStatement pre = null;
		try{
			String query = "update ERP_KHOTT_SP_CHITIET set soluong = soluong + ?,"
						   + "booked = booked + ?, available = available + ?  where PK_SEQ = ?";
			
			pre = this.db.getConnection().prepareStatement(query);
			pre.setDouble(1, soluong);
			pre.setDouble(2,booked);
			pre.setDouble(3, available);
			pre.setInt(4, Integer.parseInt(khochitiet_fk));
			
			int k = pre.executeUpdate();
			if(k !=1){
				this.db.getConnection().rollback();
				return false;
			}
			pre.close();
			
			query = " update ERP_KHOTT_SP_CHITIET set soluong = soluong + ? ,booked = booked + ?, available = available + ? "
					+ " \n where khott_fk = ( select khott_fk from ERP_KHOTT_SP_CHITIET where PK_SEQ = ?) "
					+ " \n and sanpham_fk = ( select khott_fk from ERP_KHOTT_SP_CHITIET where PK_SEQ = ?) ";
		
			pre = this.db.getConnection().prepareStatement(query);
			pre.setDouble(1, soluong);
			pre.setDouble(2,booked);
			pre.setDouble(3, available);
			pre.setInt(4, Integer.parseInt(khochitiet_fk));
			pre.setInt(5, Integer.parseInt(khochitiet_fk));
			
			k = pre.executeUpdate();
			if(k !=1){
				this.db.getConnection().rollback();
				return false;
			}
			
			pre.close();
		
			return true;
		} catch(Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		} 
	}
	public boolean create(){
		try{
			this.db.getConnection().setAutoCommit(false);
			// insert trang tong
			// tạo phiếu kiểm định
			Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			String s = timeFormat.format(today.getTime());
			
			String	query = "insert into ERP_YeuCauKiemDinh(nhanhang_fk,sanpham_fk,khuvuckho_fk,solo,trangthai,ngayhethan,nguoitao,"
						+ "ngaytao,nguoisua,ngaysua,soluong,ngaysanxuat,dinhluong,dinhtinh," + "ngaynhan,KHONHAN_FK,NGAYHETHONG,"
						+ "DonGiaViet,nhapkho_fk,loaimuahang,MAME, IDMARQUETTE, HAMAM, HAMLUONG, MAMARQUETTE)" 
						+ " values(null,"+ this.spId+ ",null,'"+ this.soLo+ "','0','',"+ userId+ ",'"+ s+ "',"+ ""+ userId+ ",'"+ s+ "',"
						+ this.SoLuongKiemDinh+ ",'','','','',"+ khoId+ ",'"+ s+ "',0,null,'"+ loaimh + "','',null,0,100,'')";

			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
				this.msg = " Tạo yêu cầu kiểm định thất bại.";
				return false;
			}
			
			// insert chi tiet
			for(int i=0; i < this.listSpKiemDinh.size(); i++){
				
				// insert tung dong bang chi tiet
				KiemDinhObj temp = new KiemDinhObj();
				boolean check = this.insertDetails(temp);
				if(check == false){
					this.db.getConnection().rollback();
					this.msg = " Tạo yêu cầu kiểm định chi tiết thất bại.";
					return false;
				}
				// booked tung dong
				double soluong = 0;
				double booked = Double.parseDouble(temp.getSoLuong().replaceAll(",", ""));
				double available = (-1.0)*Double.parseDouble(temp.getSoLuong().replaceAll(",", ""));
				
				check = BookedKho( soluong,  booked,  available, temp.getKhoChiTiet_fk());
				
				if(check == false){
					this.db.getConnection().rollback();
					this.msg = " Booked kho thất bại.";
					return false;
				}
				
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	public boolean update(){
		try{
			this.db.getConnection().setAutoCommit(false);
			// insert trang tong
			// tạo phiếu kiểm định
			Date today = new Date(System.currentTimeMillis());
			SimpleDateFormat timeFormat = new SimpleDateFormat("yyyy-MM-dd");
			String s = timeFormat.format(today.getTime());
			
			String	query = "insert into ERP_YeuCauKiemDinh(nhanhang_fk,sanpham_fk,khuvuckho_fk,solo,trangthai,ngayhethan,nguoitao,"
						+ "ngaytao,nguoisua,ngaysua,soluong,ngaysanxuat,dinhluong,dinhtinh," + "ngaynhan,KHONHAN_FK,NGAYHETHONG,"
						+ "DonGiaViet,nhapkho_fk,loaimuahang,MAME, IDMARQUETTE, HAMAM, HAMLUONG, MAMARQUETTE)" 
						+ " values(null,"+ this.spId+ ",null,'"+ this.soLo+ "','0','',"+ userId+ ",'"+ s+ "',"+ ""+ userId+ ",'"+ s+ "',"
						+ this.SoLuongKiemDinh+ ",'','','','',"+ khoId+ ",'"+ s+ "',0,null,'"+ loaimh + "','',null,0,100,'')";

			if (db.updateReturnInt(query) != 1) {
				db.getConnection().rollback();
				this.msg = " Tạo yêu cầu kiểm định thất bại.";
				return false;
			}
			
			// insert chi tiet
			for(int i=0; i < this.listSpKiemDinh.size(); i++){
				
				// insert tung dong bang chi tiet
				KiemDinhObj temp = new KiemDinhObj();
				boolean check = this.insertDetails(temp);
				if(check == false){
					this.db.getConnection().rollback();
					this.msg = " Tạo yêu cầu kiểm định chi tiết thất bại.";
					return false;
				}
				// booked tung dong
				double soluong = 0;
				double booked = Double.parseDouble(temp.getSoLuong().replaceAll(",", ""));
				double available = (-1.0)*Double.parseDouble(temp.getSoLuong().replaceAll(",", ""));
				
				check = BookedKho( soluong,  booked,  available, temp.getKhoChiTiet_fk());
				
				if(check == false){
					this.db.getConnection().rollback();
					this.msg = " Booked kho thất bại.";
					return false;
				}
				
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}catch(Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			return false;
		}
		
	}
	
	
	public void createRs(){
		// get Rs
		this.createRsKho();
		this.createRsSanPham("");
		if( this.khoId.trim().length() >0 && this.spId.trim().length() >0 ){
			// gọi hàm lấy số lô.
			createListChiTiet();
		}
	}
	private void createListChiTiet(){

		try{
			String query =  " select ct.PK_SEQ, ct.KHOTT_FK, SANPHAM_FK, sp.MA as MASP, sp.TEN as TENSP, NGAYHETHAN " +
			        " , SOLO, BIN_FK,NGAYSANXUAT, NGAYNHAPKHO, MARQ, HAMLUONG,HAMAM, MAME, MATHUNG, MAPHIEU, bin.MA as MABIN, " +
			        " isnull(PHIEUEO,'') as PHIEUEO, isnull(MAPHIEUDINHTINH,'') as MAPHIEUDINHTINH, AVAILABLE " +
			        " from ERP_KHOTT_SP_CHITIET ct inner join ERP_SANPHAM sp on ct.SANPHAM_FK = sp.PK_SEQ " +
			        " left join ERP_BIN bin on ct.BIN_FK = bin.PK_SEQ " +
			        " where ct.AVAILABLE >0  and ct.KHOTT_FK = "+this.khoId + " and SANPHAM_FK in ("+this.spId+")";
			
			ResultSet rs = this.db.get(query);
			List<KiemDinhObj> list = new ArrayList<KiemDinhObj>();
			System.out.println("[init Details]"+ query);
			if(rs!=null){
				while(rs.next()){
					KiemDinhObj detail = new KiemDinhObj();
					detail.setKhott_fk(rs.getString("KHOTT_FK"));
					detail.setSanPham_fk(rs.getString("SANPHAM_FK"));
					detail.setNgayHetHan(rs.getString("NGAYHETHAN"));
					detail.setSoLo(rs.getString("SOLO"));
					detail.setBin_fk(rs.getString("MABIN"));
					detail.setNgayNhapKho(rs.getString("NGAYNHAPKHO"));
					detail.setMaRQ(rs.getString("MARQ"));
					detail.setHamLuong(rs.getDouble("HAMLUONG"));
					detail.setHamAm(rs.getDouble("HAMAM"));
					detail.setMaMe(rs.getString("MAME"));
					detail.setMaThung(rs.getString("MATHUNG"));
					detail.setMaPhieu(rs.getString("MAPHIEU"));
					detail.setMaPhieuEO(rs.getString("PHIEUEO"));
					detail.setMaPhieuDinhTinh(rs.getString("MAPHIEUDINHTINH"));
					detail.setSoLuong(rs.getString("AVAILABLE"));
					detail.setKhoChiTiet_fk(rs.getString("PK_SEQ"));
					list.add(detail);
				}
			}
			
			this.listSpKiemDinh = list;
		} catch( Exception ex){
			ex.printStackTrace();
		}
	}
	
	
	public boolean duyetKiemDinh(){
		return  false;
	}
	public void DbClose(){
		this.db.shutDown();
	}
}
