package geso.traphaco.erp.beans.lenhsanxuatgiay.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.lenhsanxuatgiay.ILenhSXCongDoan;
import geso.traphaco.erp.db.sql.dbutils;

public class LenhSXCongDoan implements ILenhSXCongDoan {

	private String LsxId;
	private String congDoanId;
	private String Phanxuong;
	private String Diengiai;
	private String trangthai;
	private String idsp;
	private String sanpham;
	private String KichBanSXId;
	private String KiemDinhCL;

	private String DaTieuHaoNL;
	private String Soluong;
	private String SoluongChuan;
	private String NhaMayId;
	private String maysudung = "";
	private String BomId;
	private String BomTen;
	private float thutu;
	private ResultSet RsHangDaNhan;
	private ResultSet RsBanTPDaNhan;
	private ResultSet RsHangDaKD;
	private ResultSet RsDaTieuHao;
	private String Active;
	private String Masp;
	private String Ngayycthem="";
	
	
	String  KhoSXId="";
	private ResultSet RsKho;
	
	// PhatNguyen thêm công đoạn nhập xuất
	ArrayList<String> soluongNhap = new ArrayList<String>();
	ArrayList<String> soluongXuat = new ArrayList<String>();
	ArrayList<String> soluongDuPham = new ArrayList<String>();
	ArrayList<String> soluongPhePham = new ArrayList<String>();
	
	dbutils db;
	
	//Lấy các Resultset Công đoạn nhập xuất dựa vào LsxId và congDoanId
	ResultSet congDoanNhapXuat;


	List<IDanhmucvattu_SP> dmvtList;
	
	public LenhSXCongDoan() {
		super();
		db = new dbutils();
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		
	}

	@Override
	public void setLenhSXId(String LSXid) {
		// TODO Auto-generated method stub
		this.LsxId = LSXid;
	}

	@Override
	public String getLenhSXId() {
		// TODO Auto-generated method stub
		return this.LsxId;
	}

	@Override
	public void setCongDoanId(String congdoanid) {
		// TODO Auto-generated method stub
		this.congDoanId = congdoanid;
	}

	@Override
	public String getCongDoanId() {
		// TODO Auto-generated method stub
		return this.congDoanId;
	}

	// nhà máy
	@Override
	public void setPhanXuong(String _phanxuongid) {
		// TODO Auto-generated method stub
		this.Phanxuong = _phanxuongid;
	}

	@Override
	public String getPhanXuong() {
		// TODO Auto-generated method stub
		return this.Phanxuong;
	}

	@Override
	public void setDiengiai(String _Diengiai) {
		// TODO Auto-generated method stub
		this.Diengiai = _Diengiai;
	}

	@Override
	public String getDiengiai() {
		// TODO Auto-generated method stub
		return this.Diengiai;
	}

	@Override
	public void setSanPham(String SanPham) {
		// TODO Auto-generated method stub
		this.sanpham = SanPham;
	}

	@Override
	public String getSanPham() {
		// TODO Auto-generated method stub
		return this.sanpham;
	}

	@Override
	public void setTrangthai(String Trangthai) {
		// TODO Auto-generated method stub
		this.trangthai = Trangthai;
	}

	@Override
	public String gettrangthai() {
		// TODO Auto-generated method stub
		return this.trangthai;
	}

	@Override
	public void Setkichbansanxuat(String kichbansx) {
		// TODO Auto-generated method stub
		this.KichBanSXId = kichbansx;
	}

	@Override
	public String getKichbansanxuat() {
		// TODO Auto-generated method stub
		return this.KichBanSXId;
	}

	@Override
	public void SetKiemDinhCL(String _KiemDinhCL) {
		// TODO Auto-generated method stub
		this.KiemDinhCL = _KiemDinhCL;
	}

	@Override
	public String getKiemDinhCL() {
		// TODO Auto-generated method stub
		return this.KiemDinhCL;
	}

	@Override
	public void setDaTieuHaoNL(String DaTieuHaoNL) {
		// TODO Auto-generated method stub
		this.DaTieuHaoNL = DaTieuHaoNL;
	}

	@Override
	public String GetDaTieuHaoNL() {
		// TODO Auto-generated method stub
		return this.DaTieuHaoNL;
	}

	@Override
	public void setSoLuong(String soluong) {
		// TODO Auto-generated method stub
		this.Soluong = soluong;
	}

	@Override
	public String getSoLuong() {
		// TODO Auto-generated method stub
		return this.Soluong;
	}

	@Override
	public void setSpId(String spid) {
		// TODO Auto-generated method stub
		this.idsp = spid;
	}

	@Override
	public String getSpid() {
		// TODO Auto-generated method stub
		return this.idsp;
	}

	@Override
	public void setNhaMayId(String _NhaMayId) {
		// TODO Auto-generated method stub
		this.NhaMayId = _NhaMayId;
	}

	@Override
	public String getNhaMayId() {
		// TODO Auto-generated method stub
		return this.NhaMayId;
	}

	@Override
	public void setBomId(String bomId) {
		// TODO Auto-generated method stub
		this.BomId = bomId;
	}

	@Override
	public String getBomId() {
		// TODO Auto-generated method stub
		return this.BomId;
	}

	@Override
	public void setSoLuongChuan(String soluong) {
		// TODO Auto-generated method stub
		this.SoluongChuan = soluong;
	}

	@Override
	public String getSoLuongChuan() {
		// TODO Auto-generated method stub
		return this.SoluongChuan;
	}

	@Override
	public void setThuTu(float tt) {
		// TODO Auto-generated method stub
		this.thutu = tt;
	}

	@Override
	public float getThutu() {
		// TODO Auto-generated method stub
		return this.thutu;
	}

	@Override
	public void SetRsHangDaNhan(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsHangDaNhan = rs;
	}

	@Override
	public ResultSet getRsHangDaNhan() {
		// TODO Auto-generated method stub
		return this.RsHangDaNhan;
	}

	@Override
	public void SetRsHangDaKD(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsHangDaKD = rs;
	}

	@Override
	public ResultSet getRsHangDaKD() {
		// TODO Auto-generated method stub
		return this.RsHangDaKD;
	}

	@Override
	public void SetRsDaTieuHao(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsDaTieuHao = rs;
	}

	@Override
	public ResultSet getRsDaTieuHao() {
		// TODO Auto-generated method stub
		return this.RsDaTieuHao;
	}

	@Override
	public void setActive(String active) {
		// TODO Auto-generated method stub
		this.Active = active;
	}

	@Override
	public String getActive() {
		// TODO Auto-generated method stub
		return this.Active;
	}

	@Override
	public void setMaSp(String _MaSp) {
		// TODO Auto-generated method stub
		this.Masp = _MaSp;
	}

	@Override
	public String getMaSp() {
		// TODO Auto-generated method stub
		return this.Masp;
	}

	@Override
	public void setMaySuDung(String MaySuDung) {
		// TODO Auto-generated method stub
		this.maysudung = MaySuDung;
	}

	@Override
	public String getMaySuDung() {
		// TODO Auto-generated method stub
		return this.maysudung;
	}

	@Override
	public void SetRsBanThanhPham(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsBanTPDaNhan = rs;
	}

	@Override
	public ResultSet getRsBanThanhPham() {
		// TODO Auto-generated method stub
		return this.RsBanTPDaNhan;
	}
	public ArrayList<String> getSoluongNhap() {
		return soluongNhap;
	}

	public void setSoluongNhap(ArrayList<String> soluongNhap) {
		this.soluongNhap = soluongNhap;
	}

	public ArrayList<String> getSoluongXuat() {
		return soluongXuat;
	}

	public void setSoluongXuat(ArrayList<String> soluongXuat) {
		this.soluongXuat = soluongXuat;
	}

	public ArrayList<String> getSoluongDuPham() {
		return soluongDuPham;
	}

	public void setSoluongDuPham(ArrayList<String> soluongDuPham) {
		this.soluongDuPham = soluongDuPham;
	}

	public ArrayList<String> getSoluongPhePham() {
		return soluongPhePham;
	}

	public void setSoluongPhePham(ArrayList<String> soluongPhePham) {
		this.soluongPhePham = soluongPhePham;
	}
	public ResultSet getCongDoanNhapXuat() {
		return congDoanNhapXuat;
	}

	public void setCongDoanNhapXuat(ResultSet congDoanNhapXuat) {
		this.congDoanNhapXuat = congDoanNhapXuat;
	}
	public void initCongDoanNhapXuat(){
		//Viết truy vấn lấy Resulset CongDoanNhapXuat
		String sql = "SELECT * FROM ERP_LENHSANXUAT_CONGDOAN_NHAPXUAT WHERE LENHSANXUAT_FK = " + this.LsxId ;
		sql += " AND CONGDOAN_FK = " + this.congDoanId ;
		sql += " ORDER BY LANNHAP ;" ;
		
		System.out.println("Cau lenh sql initCongDoanNhapXuat(lsxId): " + sql + " (lsx) " + this.LsxId);
		this.congDoanNhapXuat = db.get(sql);
		
		
	}
	public void initArrayNhapXuat() {
		try {
			boolean bien=false;
			if(this.congDoanNhapXuat!=null){
				while(this.congDoanNhapXuat.next()){
					///loop and set string array like : soluongNhap[],/..///
					String nhap = this.congDoanNhapXuat.getString("NHAP");
					String xuat = this.congDoanNhapXuat.getString("XUAT");
					String dupham = this.congDoanNhapXuat.getString("CHUYENDUPHAM");
					String phepham = this.congDoanNhapXuat.getString("CHUYENPHEPHAM");
					soluongNhap.add(nhap);
					soluongXuat.add(xuat);
					soluongDuPham.add(dupham);
					soluongPhePham.add(phepham);
					bien=true;
					
					System.out.println("soluong nhap la :" + nhap);
				}
			}
			if(!bien){
				System.out.println("soluong vào day nhe bpa"); 
				
				for(int i=0;i<5;i++){
					String nhap = "0";
					String xuat = "0";
					String dupham = "0";
					String phepham = "0";
					soluongNhap.add(nhap);
					soluongXuat.add(xuat);
					soluongDuPham.add(dupham);
					soluongPhePham.add(phepham);
				}
			}
			
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void setListDanhMuc(List<IDanhmucvattu_SP> list) {
		// TODO Auto-generated method stub
		this.dmvtList=list;
	}

	@Override
	public List<IDanhmucvattu_SP> getListDanhMuc() {
		// TODO Auto-generated method stub
		return this.dmvtList;
	}

	@Override
	public void setBomTen(String bomten) {
		// TODO Auto-generated method stub
		this.BomTen=bomten;
	}

	@Override
	public String getBomTen() {
		// TODO Auto-generated method stub
		return this.BomTen;
	}

	@Override
	public void setKhoSXId(String KhoSXId) {
		// TODO Auto-generated method stub
		this.KhoSXId = KhoSXId;
	}

	@Override
	public String getKhoSXId() {
		// TODO Auto-generated method stub
		if(this.KhoSXId==null){
			this.KhoSXId="";
		}
		return this.KhoSXId;
	}

	@Override
	public ResultSet getKhosxRs() {
		// TODO Auto-generated method stub
		return RsKho;
	}

	@Override
	public void setKhosxRs(ResultSet KhosxRs) {
		// TODO Auto-generated method stub
		RsKho=KhosxRs;
	}

	@Override
	public void setNGayYcThem(String ngayycthem) {
		// TODO Auto-generated method stub
		this.Ngayycthem=ngayycthem;
	}

	@Override
	public String getNGayYcThem() {
		// TODO Auto-generated method stub
		return this.Ngayycthem;
	}

}
