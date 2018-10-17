package geso.traphaco.erp.beans.hosokiemnghiem.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.hosokiemnghiem.IHoSoKiemNghiem;
import geso.traphaco.erp.beans.hosokiemnghiemchitiet.IHoSoKiemNghiemChiTiet;
import geso.traphaco.erp.beans.hosokiemnghiemchitiet.imp.HoSoKiemNghiemChiTiet;
import geso.traphaco.erp.beans.hosokiemnghiemthietbi.IHoSoKiemNghiemThietBi;
import geso.traphaco.erp.beans.hosokiemnghiemthietbi.imp.HoSoKiemNghiemThietBi;
import geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.IPhuongPhapThuNghiemThamSo;



public class HoSoKiemNghiem implements IHoSoKiemNghiem {

	private String pk_seq;
	private String congtyId;
	private String nppId; 
	String NgayChungTu;
	String TenYeuCauKyThuat;
	private String MaSoKN;
	private String MaPhongBan;
	private String SoPhieuKN;
	private String ThoiGianGiaoMau;
	private String NguoiGuiMau;
	private String MaPhieuYeuCauLayMau;
	private String MaSanPham;
	private String TenSanPham;
	private String MaLoaiMauKN;
	private String TenLoaiKN;
	private String MaTieuChuanKiemNghiem;
	private String LoaiKiemTra;
	private String ThoiDiemLayMau;
	private String DienGiai;
	private String TrangThai;
	private String UserId;
	private String UserName;
	private String msg;
	private String ngaygiaohang;
	
	ResultSet RsPhongBan;
	ResultSet RsYCLayMau;
	ResultSet RsTCKiemNghiem;
	ResultSet RsNhanVien;
	ResultSet RsSanPham;
	ResultSet RsLoaiMauKN;
	
	List<IHoSoKiemNghiemChiTiet> listCT;
	List<IHoSoKiemNghiemThietBi> listTB;
	dbutils db;

	public HoSoKiemNghiem(){
		this.pk_seq="";
		this.NgayChungTu="";
		this.MaSoKN="";
		this.MaPhongBan="";
		this.SoPhieuKN="";
		this.ThoiGianGiaoMau="";
		this.NguoiGuiMau="";
		this.MaPhieuYeuCauLayMau="";
		this.MaSanPham="";
		this.TenSanPham="";
		this.MaLoaiMauKN="";
		this.TenLoaiKN="";
		this.MaTieuChuanKiemNghiem="";
		this.LoaiKiemTra="";
		this.ThoiDiemLayMau="";
		this.DienGiai="";
		this.TrangThai="";
		this.UserId="";
		this.UserName="";
		this.msg="";
		this.congtyId="";
		this.TenYeuCauKyThuat="";
		this.nppId="";
		this.ngaygiaohang="";
		this.listCT=new ArrayList<IHoSoKiemNghiemChiTiet>();
		this.listTB=new ArrayList<IHoSoKiemNghiemThietBi>();
		db=new dbutils();
	}
	
	
	public String getNgaygiaohang() {
		return ngaygiaohang;
	}
	public void setNgaygiaohang(String ngaygiaohang) {
		this.ngaygiaohang = ngaygiaohang;
	}
	public ResultSet getRsSanPham() {
		return RsSanPham;
	}

	public void setRsSanPham(ResultSet rsSanPham) {
		RsSanPham = rsSanPham;
	}

	public ResultSet getRsLoaiMauKN() {
		return RsLoaiMauKN;
	}

	public void setRsLoaiMauKN(ResultSet rsLoaiMauKN) {
		RsLoaiMauKN = rsLoaiMauKN;
	}

	public String getTenYeuCauKyThuat() {
		return TenYeuCauKyThuat;
	}

	public void setTenYeuCauKyThuat(String tenYeuCauKyThuat) {
		TenYeuCauKyThuat = tenYeuCauKyThuat;
	}

	public String getCongtyId() {
		return congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public String getTenLoaiKN() {
		return TenLoaiKN;
	}

	public void setTenLoaiKN(String tenLoaiKN) {
		TenLoaiKN = tenLoaiKN;
	}

	public String getTenSanPham() {
		return TenSanPham;
	}

	public void setTenSanPham(String tenSanPham) {
		TenSanPham = tenSanPham;
	}

	public String getPk_seq() {
		return pk_seq;
	}

	public void setPk_seq(String pk_seq) {
		this.pk_seq = pk_seq;
	}

	public String getNgayChungTu() {
		return NgayChungTu;
	}

	public void setNgayChungTu(String ngayChungTu) {
		NgayChungTu = ngayChungTu;
	}

	public String getMaSoKN() {
		return MaSoKN;
	}

	public void setMaSoKN(String maSoKN) {
		MaSoKN = maSoKN;
	}

	public String getMaPhongBan() {
		return MaPhongBan;
	}

	public void setMaPhongBan(String maPhongBan) {
		MaPhongBan = maPhongBan;
	}

	public String getSoPhieuKN() {
		return SoPhieuKN;
	}

	public void setSoPhieuKN(String soPhieuKN) {
		SoPhieuKN = soPhieuKN;
	}

	public String getThoiGianGiaoMau() {
		return ThoiGianGiaoMau;
	}

	public void setThoiGianGiaoMau(String thoiGianGiaoMau) {
		ThoiGianGiaoMau = thoiGianGiaoMau;
	}

	public String getNguoiGuiMau() {
		return NguoiGuiMau;
	}

	public void setNguoiGuiMau(String nguoiGuiMau) {
		NguoiGuiMau = nguoiGuiMau;
	}

	public String getMaPhieuYeuCauLayMau() {
		return MaPhieuYeuCauLayMau;
	}

	public void setMaPhieuYeuCauLayMau(String maPhieuYeuCauLayMau) {
		MaPhieuYeuCauLayMau = maPhieuYeuCauLayMau;
	}
	
	public String getMaSanPham() {
		return MaSanPham;
	}

	public void setMaSanPham(String maSanPham) {
		MaSanPham = maSanPham;
	}

	public String getMaLoaiMauKN() {
		return MaLoaiMauKN;
	}

	public void setMaLoaiMauKN(String maLoaiMauKN) {
		MaLoaiMauKN = maLoaiMauKN;
	}

	public String getMaTieuChuanKiemNghiem() {
		return MaTieuChuanKiemNghiem;
	}

	public void setMaTieuChuanKiemNghiem(String maTieuChuanKiemNghiem) {
		MaTieuChuanKiemNghiem = maTieuChuanKiemNghiem;
	}

	public String getLoaiKiemTra() {
		return LoaiKiemTra;
	}

	public void setLoaiKiemTra(String loaiKiemTra) {
		LoaiKiemTra = loaiKiemTra;
	}

	public String getThoiDiemLayMau() {
		return ThoiDiemLayMau;
	}

	public void setThoiDiemLayMau(String thoiDiemLayMau) {
		ThoiDiemLayMau = thoiDiemLayMau;
	}

	public String getDienGiai() {
		return DienGiai;
	}

	public void setDienGiai(String dienGiai) {
		DienGiai = dienGiai;
	}

	public String getTrangThai() {
		return TrangThai;
	}

	public void setTrangThai(String trangThai) {
		TrangThai = trangThai;
	}

	public String getUserId() {
		return UserId;
	}

	public void setUserId(String userId) {
		UserId = userId;
	}

	public String getUserName() {
		return UserName;
	}

	public void setUserName(String userName) {
		UserName = userName;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public ResultSet getRsPhongBan() {
		return RsPhongBan;
	}

	public void setRsPhongBan(ResultSet rsPhongBan) {
		RsPhongBan = rsPhongBan;
	}

	public ResultSet getRsYCLayMau() {
		return RsYCLayMau;
	}

	public void setRsYCLayMau(ResultSet rsYCLayMau) {
		RsYCLayMau = rsYCLayMau;
	}

	public ResultSet getRsTCKiemNghiem() {
		return RsTCKiemNghiem;
	}

	public void setRsTCKiemNghiem(ResultSet rsTCKiemNghiem) {
		RsTCKiemNghiem = rsTCKiemNghiem;
	}
	
	public List<IHoSoKiemNghiemChiTiet> getListCT() {
		return listCT;
	}

	public void setListCT(List<IHoSoKiemNghiemChiTiet> listCT) {
		this.listCT = listCT;
	}

	public List<IHoSoKiemNghiemThietBi> getListTB() {
		return listTB;
	}

	public void setListTB(List<IHoSoKiemNghiemThietBi> listTB) {
		this.listTB = listTB;
	}
	
	public ResultSet getRsNhanVien() {
		String queryKMV="select PK_SEQ,MA,TEN from ERP_NHANVIEN where TRANGTHAI=1";
		return this.db.getScrol(queryKMV);

	}

	public void setRsNhanVien(ResultSet rsNhanVien) {
		RsNhanVien = rsNhanVien;
	}

	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void creates(){
		/*String queryKMV="select PK_SEQ,MA,TEN from ERP_NHANVIEN where TRANGTHAI=1";
		this.RsNhanVien=this.db.get(queryKMV);*/
		
		String querySP="select PK_SEQ,MA,TEN from ERP_SANPHAM where trangthai=1";
		this.RsSanPham=this.db.get(querySP);
		String queryLMKN="select PK_SEQ,MA,TEN from ERP_LOAIMAUKIEMNGHIEM where trangthai=1";
		this.RsLoaiMauKN=this.db.get(queryLMKN);
		String queryPB="select pk_seq,ma,ten from ERP_DONVITHUCHIEN where trangthai=1";
		this.RsPhongBan=this.db.get(queryPB);
		String queryYCLM="select b.PK_SEQ as PK_SEQ_YCLM,b.NGAYCHUNGTU as NGAYCHUNGTUYCLM,b.DIENGIAI as DIENGIAIYCLM, a.MA as MALOAIKIEMNGHIEM, a.TEN as TENLOAIKIEMNGHIEM,"
					 +"\n       a.PK_SEQ as PK_SEQ_LOAIKIEMNGHIEM,c.MA as MASANPHAM,c.TEN as TENSANPHAM,C.PK_SEQ as PK_SEQ_SANPHAM" 
					 +"\n from ERP_LOAIMAUKIEMNGHIEM a inner join ERP_YCLAYMAUKIEMNGHIEM b on a.PK_SEQ=b.MAUKIEMNGHIEM_FK"
					 +"\n inner join ERP_SANPHAM c on b.SANPHAM_FK=C.PK_SEQ where b.trangthai=1" ;
		System.out.println("lay yeu cau lay mau rs 1: " + queryYCLM);
		this.RsYCLayMau=this.db.get(queryYCLM);
		if(this.MaPhieuYeuCauLayMau.trim().length() > 0){
			queryYCLM+=" and b.PK_SEQ='"+this.MaPhieuYeuCauLayMau+"'";
			System.out.println("lay yeu cau lay mau rs 2: " + queryYCLM);
			ResultSet RsSP=this.db.get(queryYCLM);
			
			if(RsSP!=null){
				try {
					while (RsSP.next()) {
						this.setMaSanPham(RsSP.getString("PK_SEQ_SANPHAM"));
						this.TenSanPham=RsSP.getString("MASANPHAM")+"-"+RsSP.getString("TENSANPHAM");
						this.MaLoaiMauKN= RsSP.getString("PK_SEQ_LOAIKIEMNGHIEM");
						this.TenLoaiKN=RsSP.getString("MALOAIKIEMNGHIEM")+"-"+RsSP.getString("TENLOAIKIEMNGHIEM");
					}
					
				} catch (SQLException e) {
					this.msg="Không Thể Thực Hiện Câu Lệnh "+queryYCLM;
					e.printStackTrace();
				}
			}
			String queryTCKM="select pk_seq,ma,ten from ERP_TIEUCHUANKIEMGNGHIEM where trangthai=1 and sanpham_fk in("+ this.getMaSanPham()+") and loaimauKN_fk in ("+ this.getMaLoaiMauKN()+")";
			System.out.println("queryTCKM 1: "+ queryTCKM);
			this.RsTCKiemNghiem=this.db.get(queryTCKM);
			}
//		}
		String queryTCKM="select pk_seq,ma,ten from ERP_TIEUCHUANKIEMGNGHIEM where trangthai=1 ";
		if(this.getMaSanPham().trim().length()>0){
			queryTCKM+=" and sanpham_fk in("+ this.getMaSanPham()+") and loaimauKN_fk in ("+ this.getMaLoaiMauKN()+")";
		}
		System.out.println("queryTCKM 2:"+ queryTCKM);
		this.RsTCKiemNghiem=this.db.get(queryTCKM);
		if(this.MaTieuChuanKiemNghiem.trim().length() > 0){
			String queryCT="select ROW_NUMBER() OVER(ORDER BY a.PK_SEQ ASC) as STT,a.TEN as TENYCKT,a.PK_SEQ as MAYCKT, a.THONGSOYEUCAUTU , a.THONGSOYEUCAUDEN,c.pk_seq as pk_seq,"
					+ "\n			 c.MA as MA,c.TEN as TEN,SANPHAM_FK "
					+ "\n    from ERP_YEUCAUKYTHUAT a inner join ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT b on a.PK_SEQ=b.YEUCAUKT_FK"
					+ "\n    inner join ERP_TIEUCHUANKIEMGNGHIEM c on b.TIEUCHUANKN_FK=c.PK_SEQ "
					+ "\n    where a.trangthai=1 and c.pk_seq='"+this.MaTieuChuanKiemNghiem+"'";
			
			System.out.println("lay rs Ma Tieu Chuan KN: "+ queryCT);
			this.listCT=new ArrayList<IHoSoKiemNghiemChiTiet>();
			ResultSet rsCT=this.db.get(queryCT);
			System.out.println("===================="+queryCT);
			if(rsCT!=null){
				try {
					while (rsCT.next()) {
						IHoSoKiemNghiemChiTiet ct=new HoSoKiemNghiemChiTiet();
						ct.setSoTT(rsCT.getString("STT"));
						ct.setThongSoYeuCau(rsCT.getString("THONGSOYEUCAUTU"));
						ct.setThongSoYeuCauDen(rsCT.getString("THONGSOYEUCAUDEN"));
						ct.setMaYCKT(rsCT.getString("MAYCKT"));
						ct.setTenYCKT(rsCT.getString("TENYCKT"));
						this.listCT.add(ct);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					this.msg="Không Thể Thực Hiện Câu Lệnh "+queryCT;
					e.printStackTrace();
				}
			}
			String queryTB="select ROW_NUMBER() OVER(ORDER BY a.PK_SEQ ASC) as STT,a.TEN as TENTHIETBI,a.MA as MATHIETBI,a.PK_SEQ as PK_SEQ_TB,c.pk_seq as pk_seq,"+
						"\n        c.MA as MA,c.TEN as TEN,SANPHAM_FK "+
						"\n   from ERP_THIETBISX a inner join ERP_TIEUCHUANKIEMGNGHIEM_THIETBI b on a.PK_SEQ=b.THIETBI_FK"+
					    "\n   inner join ERP_TIEUCHUANKIEMGNGHIEM c on b.TIEUCHUAN_FK=c.PK_SEQ"+
					    "\n   where a.trangthai=1 and c.pk_seq='"+this.MaTieuChuanKiemNghiem+"'";
			ResultSet RsTB=this.db.get(queryTB);
			this.listTB=new ArrayList<IHoSoKiemNghiemThietBi>();
			if (RsTB!=null) {
				try {
					while(RsTB.next()){
						IHoSoKiemNghiemThietBi tb=new HoSoKiemNghiemThietBi();
						tb.setSoTT(RsTB.getString("STT"));
						tb.setMaThietBi(RsTB.getString("MATHIETBI"));
						tb.setTenThietBi(RsTB.getString("TENTHIETBI"));
						this.listTB.add(tb);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					this.msg="Không Thể Thực Hiện Câu Lệnh "+queryTB;
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	public void createsloaimau(){
		String querySP="select PK_SEQ,MA,TEN from ERP_SANPHAM where trangthai=1";
		this.RsSanPham=this.db.get(querySP);
		String queryLMKN="select PK_SEQ,MA,TEN from ERP_LOAIMAUKIEMNGHIEM where trangthai=1";
		this.RsLoaiMauKN=this.db.get(queryLMKN);
		String queryPB="select pk_seq,ma,ten from ERP_DONVITHUCHIEN where trangthai=1";
		this.RsPhongBan=this.db.get(queryPB);
		String queryYCLM="select b.PK_SEQ as PK_SEQ_YCLM,b.NGAYCHUNGTU as NGAYCHUNGTUYCLM,b.DIENGIAI as DIENGIAIYCLM, a.MA as MALOAIKIEMNGHIEM, a.TEN as TENLOAIKIEMNGHIEM,"
					 +"\n       a.PK_SEQ as PK_SEQ_LOAIKIEMNGHIEM,c.MA as MASANPHAM,c.TEN as TENSANPHAM,C.PK_SEQ as PK_SEQ_SANPHAM" 
					 +"\n from ERP_LOAIMAUKIEMNGHIEM a inner join ERP_YCLAYMAUKIEMNGHIEM b on a.PK_SEQ=b.MAUKIEMNGHIEM_FK"
					 +"\n inner join ERP_SANPHAM c on b.SANPHAM_FK=C.PK_SEQ where b.trangthai=1" ;
		System.out.println("lay yeu cau lay mau rs 1: " + queryYCLM);
		this.RsYCLayMau=this.db.get(queryYCLM);
//		if(this.MaPhieuYeuCauLayMau.trim().length() > 0){
//			queryYCLM+=" and b.PK_SEQ='"+this.MaPhieuYeuCauLayMau+"'";
//			System.out.println("lay yeu cau lay mau rs 2: " + queryYCLM);
//			ResultSet RsSP=this.db.get(queryYCLM);
//			
//			if(RsSP!=null){
//				try {
//					while (RsSP.next()) {
//						this.setMaSanPham(RsSP.getString("PK_SEQ_SANPHAM"));
//						this.TenSanPham=RsSP.getString("MASANPHAM")+"-"+RsSP.getString("TENSANPHAM");
//						this.MaLoaiMauKN= RsSP.getString("PK_SEQ_LOAIKIEMNGHIEM");
//						this.TenLoaiKN=RsSP.getString("MALOAIKIEMNGHIEM")+"-"+RsSP.getString("TENLOAIKIEMNGHIEM");
//					}
//					
//				} catch (SQLException e) {
//					this.msg="Không Thể Thực Hiện Câu Lệnh "+queryYCLM;
//					e.printStackTrace();
//				}
//			}
//			String queryTCKM="select pk_seq,ma,ten from ERP_TIEUCHUANKIEMGNGHIEM where trangthai=1 and sanpham_fk in("+ this.getMaSanPham()+") and loaimauKN_fk in ("+ this.getMaLoaiMauKN()+")";
//			System.out.println("queryTCKM 1: "+ queryTCKM);
//			this.RsTCKiemNghiem=this.db.get(queryTCKM);
//			}
//		}
		String queryTCKM="select pk_seq,ma,ten from ERP_TIEUCHUANKIEMGNGHIEM where trangthai=1 ";
		if(this.getMaSanPham().trim().length()>0){
			queryTCKM+=" and sanpham_fk in("+ this.getMaSanPham()+") and loaimauKN_fk in ("+ this.getMaLoaiMauKN()+")";
		}
		System.out.println("queryTCKM 2:"+ queryTCKM);
		this.RsTCKiemNghiem=this.db.get(queryTCKM);
		if(this.MaTieuChuanKiemNghiem.trim().length() > 0){
			String queryCT="select ROW_NUMBER() OVER(ORDER BY a.PK_SEQ ASC) as STT,a.TEN as TENYCKT,a.PK_SEQ as MAYCKT, a.THONGSOYEUCAUTU , a.THONGSOYEUCAUDEN,c.pk_seq as pk_seq,"
					+ "\n			 c.MA as MA,c.TEN as TEN,SANPHAM_FK "
					+ "\n    from ERP_YEUCAUKYTHUAT a inner join ERP_TIEUCHUANKIEMGNGHIEM_YCKYTHUAT b on a.PK_SEQ=b.YEUCAUKT_FK"
					+ "\n    inner join ERP_TIEUCHUANKIEMGNGHIEM c on b.TIEUCHUANKN_FK=c.PK_SEQ "
					+ "\n    where a.trangthai=1 and c.pk_seq='"+this.MaTieuChuanKiemNghiem+"'";
			
			System.out.println("lay rs Ma Tieu Chuan KN: "+ queryCT);
			this.listCT=new ArrayList<IHoSoKiemNghiemChiTiet>();
			ResultSet rsCT=this.db.get(queryCT);
			System.out.println("===================="+queryCT);
			if(rsCT!=null){
				try {
					while (rsCT.next()) {
						IHoSoKiemNghiemChiTiet ct=new HoSoKiemNghiemChiTiet();
						ct.setSoTT(rsCT.getString("STT"));
						ct.setThongSoYeuCau(rsCT.getString("THONGSOYEUCAUTU"));
						ct.setThongSoYeuCauDen(rsCT.getString("THONGSOYEUCAUDEN"));
						ct.setMaYCKT(rsCT.getString("MAYCKT"));
						ct.setTenYCKT(rsCT.getString("TENYCKT"));
						this.listCT.add(ct);
					}
					
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					this.msg="Không Thể Thực Hiện Câu Lệnh "+queryCT;
					e.printStackTrace();
				}
			}
			String queryTB="select ROW_NUMBER() OVER(ORDER BY a.PK_SEQ ASC) as STT,a.TEN as TENTHIETBI,a.MA as MATHIETBI,a.PK_SEQ as PK_SEQ_TB,c.pk_seq as pk_seq,"+
						"\n        c.MA as MA,c.TEN as TEN,SANPHAM_FK "+
						"\n   from ERP_THIETBISX a inner join ERP_TIEUCHUANKIEMGNGHIEM_THIETBI b on a.PK_SEQ=b.THIETBI_FK"+
					    "\n   inner join ERP_TIEUCHUANKIEMGNGHIEM c on b.TIEUCHUAN_FK=c.PK_SEQ"+
					    "\n   where a.trangthai=1 and c.pk_seq='"+this.MaTieuChuanKiemNghiem+"'";
			ResultSet RsTB=this.db.get(queryTB);
			this.listTB=new ArrayList<IHoSoKiemNghiemThietBi>();
			if (RsTB!=null) {
				try {
					while(RsTB.next()){
						IHoSoKiemNghiemThietBi tb=new HoSoKiemNghiemThietBi();
						tb.setSoTT(RsTB.getString("STT"));
						tb.setMaThietBi(RsTB.getString("MATHIETBI"));
						tb.setTenThietBi(RsTB.getString("TENTHIETBI"));
						this.listTB.add(tb);
					}
					
				} catch (SQLException e) {
					this.msg="Không Thể Thực Hiện Câu Lệnh "+queryTB;
					e.printStackTrace();
				}
			}
		}
		
		
		
	}
	public void init(){
		String query="select isnull(a.ngaygiaohang,'') as ngaygiaohang, a.*,a.DIENGIAI as TENDIENGIAI,(select TEN from ERP_SANPHAM where PK_SEQ=a.SANPHAM_FK) as TENSANPHAM,c.TEN as TENLOAIKIEMNGHIEM"
				+ "\n from  ERP_LOAIMAUKIEMNGHIEM c inner join ERP_HOSOKIEMNGHIEM a on c.PK_SEQ=a.LOAIMAUKIEMNGHIEM_FK"
				+ "\n where a.PK_SEQ='"+this.pk_seq+"'";
		System.out.println("=========================HSKN"+query);
		ResultSet rsHSKN=this.db.get(query);
		try {
			if (rsHSKN!=null) {
				while (rsHSKN.next()) {
					this.ngaygiaohang=rsHSKN.getString("ngaygiaohang");
					this.MaSoKN=rsHSKN.getString("MASOKIEMNGHIEM");
					this.NgayChungTu=rsHSKN.getString("NGAYCHUNGTU");
					this.SoPhieuKN=rsHSKN.getString("SOPHIEUKIEMNGHIEM");
					this.ThoiGianGiaoMau=rsHSKN.getString("THOIGIANGIAOMAU");
					this.NguoiGuiMau=rsHSKN.getString("NGUOIGUIMAU");
					this.MaPhieuYeuCauLayMau=rsHSKN.getString("YEUCAULAYMAU_FK");
					//this.TenYeuCauKyThuat=rsHSKN.getString("TENYEUCAUKYTHUAT");
					this.MaSanPham=rsHSKN.getString("SANPHAM_FK");
					this.TenSanPham=rsHSKN.getString("TENSANPHAM");
					this.MaTieuChuanKiemNghiem=rsHSKN.getString("TIEUCHUANKIEMNGHIEM_FK");
					this.MaLoaiMauKN=rsHSKN.getString("LOAIMAUKIEMNGHIEM_FK");
					this.TenLoaiKN=rsHSKN.getString("TENLOAIKIEMNGHIEM");
					this.LoaiKiemTra=rsHSKN.getString("LOAIKIEMTRA");
					this.ThoiDiemLayMau=rsHSKN.getString("THOIDIEMLAYMAU");
					this.DienGiai=rsHSKN.getString("TENDIENGIAI");
					this.MaPhongBan=rsHSKN.getString("PHONGBAN_FK");
				}
				rsHSKN.close();
				System.out.println("DIENGIAI: " + this.DienGiai);
			}
		} catch (Exception e) {
			this.msg="Không Thể Thực Hiện Câu Lệnh "+query;
			e.printStackTrace();
			
		}
		this.listCT=new ArrayList<IHoSoKiemNghiemChiTiet>();
		String queryCT="select a.SOTT, a.HOSOKIEMNGHIEM_FK,a.KIEMNGHIEMVIEN_FK,a.THONGSOYEUCAU,a.THONGSOYEUCAUDEN,a.YEUCAUKYTHUAT_FK,isnull(a.DANHGIA,'') as DANHGIA,isnull(a.THOIGIANBD,'') as THOIGIANBD,"
				+ "\n	   isnull(a.THOIGIANKT,'') as THOIGIANKT,isnull(a.THOILUONG,'') as THOILUONG ,(select TEN from ERP_YEUCAUKYTHUAT where PK_SEQ=a.YEUCAUKYTHUAT_FK) as TENYCKT" 
				+ "\n  from  ERP_HOSOKIEMNGHIEM_CHITIET a inner join ERP_HOSOKIEMNGHIEM b on a.HOSOKIEMNGHIEM_FK=b.PK_SEQ "
				+ "\n  where b.PK_SEQ='"+this.pk_seq+"'";
		System.out.println("============================CT"+queryCT);
		ResultSet rsCT=this.db.get(queryCT);
		if (rsCT!=null) {
			try {
				;
				while (rsCT.next()) {
					IHoSoKiemNghiemChiTiet ct=new HoSoKiemNghiemChiTiet();
					ct.setSoTT(rsCT.getString("SOTT"));
					ct.setMaHSTN(rsCT.getString("HOSOKIEMNGHIEM_FK"));
					ct.setMaKiemNghiemVien(rsCT.getString("KIEMNGHIEMVIEN_FK"));
					ct.setThongSoYeuCau(rsCT.getString("THONGSOYEUCAU"));
					ct.setThongSoYeuCauDen(rsCT.getString("THONGSOYEUCAUDEN"));
//					ct.setMaTCKN(rsCT.getString("YEUCAUKYTHUAT_FK"));
					ct.setMaYCKT(rsCT.getString("YEUCAUKYTHUAT_FK"));
					ct.setTenYCKT(rsCT.getString("TENYCKT"));
					ct.setDanhGia(rsCT.getString("DANHGIA"));
					ct.setThoiGianBD(rsCT.getString("THOIGIANBD"));
					ct.setThoiGianKT(rsCT.getString("THOIGIANKT"));
					ct.setThoiLuong(rsCT.getString("THOILUONG"));
					this.listCT.add(ct);
				}
				rsCT.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				this.msg="Không Thể Thực Hiện Câu Lệnh "+queryCT;
				e.printStackTrace();
				
			}
		}
		this.listTB=new ArrayList<IHoSoKiemNghiemThietBi>();
	/*	String queryTB="select  a.SOTT,isnull(a.HOSOKIEMNGHIEM_FK,'') as HOSOKIEMNGHIEM_FK, isnull(a.THIETBI_FK,0) as THIETBI_FK ,isnull(a.NGAYDANHGIA,'') as NGAYDANHGIA,"
				+ "\n isnull(a.NGAYDANHGIAKETIEP,'') as NGAYDANHGIAKETIEP,a.GHICHU,"
				+ "\n isnull((select TEN from ERP_THIETBISX where PK_SEQ=a.thietbi_fk),'') as TENTHIETBI "
				+ "\n from ERP_HOSOKIEMNGHIEM_THIETBI a inner join ERP_HOSOKIEMNGHIEM b on a.HOSOKIEMNGHIEM_FK=b.PK_SEQ "
				+ "\n where b.PK_SEQ='"+this.pk_seq+"'";*/
		String queryTB = "select  c.SOTT,HOSOKIEMNGHIEM_FK as HOSOKIEMNGHIEM_FK, isnull(a.THIETBI_FK,0) as THIETBI_FK ,isnull(c.NGAYDANHGIA,'') as NGAYDANHGIA,\r\n" + 
				" isnull(c.NGAYDANHGIAKETIEP,'') as NGAYDANHGIAKETIEP,c.GHICHU,\r\n" + 
				" isnull((select TEN from ERP_THIETBISX where PK_SEQ=a.thietbi_fk),'') as TENTHIETBI \r\n" + 
				" from ERP_TIEUCHUANKIEMGNGHIEM_THIETBI a \r\n" + 
				" inner join ERP_HOSOKIEMNGHIEM b on b.TIEUCHUANKIEMNGHIEM_FK=a.TIEUCHUAN_FK \r\n" + 
				" left join ERP_HOSOKIEMNGHIEM_THIETBI c on c.HOSOKIEMNGHIEM_FK = b.PK_SEQ\r\n" + 
				" where b.PK_SEQ='"+this.pk_seq+"'";
		ResultSet rsTB=this.db.get(queryTB);
		System.out.println("=========================TB"+queryTB);
		if (rsTB!=null) {
			try {
				while(rsTB.next()){
					IHoSoKiemNghiemThietBi tb=new HoSoKiemNghiemThietBi();
					tb.setSoTT(rsTB.getString("SOTT"));
					tb.setMaHoSoKiemNghiem(rsTB.getString("HOSOKIEMNGHIEM_FK"));
					tb.setMaThietBi(rsTB.getString("THIETBI_FK"));
					tb.setTenThietBi(rsTB.getString("TENTHIETBI"));
					tb.setGhiChu(rsTB.getString("GHICHU"));
					tb.setNgayDanhGia(rsTB.getString("NGAYDANHGIA"));
					tb.setNgayDanhGiaKeTiep(rsTB.getString("NGAYDANHGIAKETIEP"));
					this.listTB.add(tb);
				}
				rsTB.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				this.msg="Không Thể Thực Hiện Câu Lệnh "+queryTB;
				e.printStackTrace();
			}
		}
		
	}
	public boolean save(){
		try {
			//String idhskn;
			db.getConnection().setAutoCommit(false);
			String query="insert into ERP_HOSOKIEMNGHIEM(NGAYCHUNGTU, ngaygiaohang, MASOKIEMNGHIEM,SOPHIEUKIEMNGHIEM,THOIGIANGIAOMAU,NGUOIGUIMAU,YEUCAULAYMAU_FK,SANPHAM_FK,"
					+ "TIEUCHUANKIEMNGHIEM_FK,LOAIMAUKIEMNGHIEM_FK,LOAIKIEMTRA,THOIDIEMLAYMAU,DIENGIAI,PHONGBAN_FK,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,TRANGTHAI) VALUES('"
					+this.NgayChungTu+"', '" +this.ngaygiaohang +"',N'"+this.MaSoKN+"',N'"+this.SoPhieuKN+"','"+this.ThoiGianGiaoMau+"',N'"+this.NguoiGuiMau+"','"+this.MaPhieuYeuCauLayMau+"','"+this.MaSanPham
					+"','"+this.MaTieuChuanKiemNghiem+"','"+this.MaLoaiMauKN+"','"+this.LoaiKiemTra+"','"+this.ThoiDiemLayMau+"',N'"+this.DienGiai+"','"+this.MaPhongBan+"','"+this.UserId
					+"','"+this.getDateTime()+"','"+this.UserId+"','"+this.getDateTime()+"','"+this.TrangThai+"')";
			System.out.println("====================="+query);
			if (!db.update(query)) {
				this.msg="Không thực hiện được câu lệnh: "+query;
				db.getConnection().rollback();
				return false;
			}
			String idhskn="";
			query="select SCOPE_IDENTITY() as idhskn";
			ResultSet rsHSKN=db.get(query);
			if (rsHSKN.next()) {
				idhskn=rsHSKN.getString("idhskn");
			}
			rsHSKN.close();
			for (int j = 0; j < this.listCT.size(); j++) {
				IHoSoKiemNghiemChiTiet ts=this.listCT.get(j);
				if(ts.getMaKiemNghiemVien().equals("")){
					db.getConnection().rollback();
					this.msg="Mời Bạn Chọn Đầy Đủ Kiểm Nghiệm Viên Trong Tab Kiểm Nghiệm";
					return false;
				}
				
			
				query="insert into ERP_HOSOKIEMNGHIEM_CHITIET(HOSOKIEMNGHIEM_FK,KIEMNGHIEMVIEN_FK,YEUCAUKYTHUAT_FK,THONGSOYEUCAU,THONGSOYEUCAUDEN,SOTT)  " +
						" values('"+idhskn+ "', '"+ ts.getMaKiemNghiemVien() + "','" + ts.getMaYCKT()+ "','" +ts.getThongSoYeuCau()+"','"+ts.getThongSoYeuCauDen()+"','"+ts.getSoTT()+"')";
				System.out.println("====================="+query);
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			for (int h = 0; h < this.listTB.size(); h++) {
				IHoSoKiemNghiemThietBi tb=listTB.get(h);
				query="insert into ERP_HOSOKIEMNGHIEM_THIETBI(HOSOKIEMNGHIEM_FK,THIETBI_FK,GHICHU,SOTT,NGAYDANHGIA,NGAYDANHGIAKETIEP)  " +
						" values('"+idhskn+ "',(select PK_SEQ from ERP_THIETBISX where MA='"+ tb.getMaThietBi() + "'),N'" + tb.getGhiChu()+ "','" +tb.getSoTT()+"','"+tb.getNgayDanhGia()+"','"+tb.getNgayDanhGiaKeTiep()+"')";
				System.out.println("====================="+query);
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean update(){
		try {
			//String idhskn;
			db.getConnection().setAutoCommit(false);
			String query="delete from erp_hosokiemnghiem where pk_seq= '" + this.pk_seq + "'";
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_HOSOKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			query="delete from ERP_HOSOKIEMNGHIEM_CHITIET where HOSOKIEMNGHIEM_FK= '" + this.pk_seq + "'";
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_HOSOKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="delete from ERP_HOSOKIEMNGHIEM_THIETBI where HOSOKIEMNGHIEM_FK= '" + this.pk_seq + "'";
			if(!db.update(query)) {
				this.msg = "Không thể cập nhật ERP_HOSOKIEMNGHIEM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query="insert into ERP_HOSOKIEMNGHIEM(NGAYCHUNGTU, ngaygiaohang, MASOKIEMNGHIEM,SOPHIEUKIEMNGHIEM,THOIGIANGIAOMAU,NGUOIGUIMAU,YEUCAULAYMAU_FK,SANPHAM_FK,"
					+ "TIEUCHUANKIEMNGHIEM_FK,LOAIMAUKIEMNGHIEM_FK,LOAIKIEMTRA,THOIDIEMLAYMAU,DIENGIAI,PHONGBAN_FK,NGUOITAO,NGAYTAO,NGUOISUA,NGAYSUA,TRANGTHAI) VALUES('"
					+this.NgayChungTu+"', '" +this.ngaygiaohang +"',N'"+this.MaSoKN+"',N'"+this.SoPhieuKN+"','"+this.ThoiGianGiaoMau+"',N'"+this.NguoiGuiMau+"','"+this.MaPhieuYeuCauLayMau+"','"+this.MaSanPham
					+"','"+this.MaTieuChuanKiemNghiem+"','"+this.MaLoaiMauKN+"','"+this.LoaiKiemTra+"','"+this.ThoiDiemLayMau+"',N'"+this.DienGiai+"','"+this.MaPhongBan+"','"+this.UserId
					+"','"+this.getDateTime()+"','"+this.UserId+"','"+this.getDateTime()+"','"+this.TrangThai+"')";
			System.out.println("====================="+query);
			if (!db.update(query)) {
				this.msg="Không thực hiện được câu lệnh: "+query;
				db.getConnection().rollback();
				return false;
			}
			String idhskn="";
			query="select SCOPE_IDENTITY() as idhskn";
			ResultSet rsHSKN=db.get(query);
			if (rsHSKN.next()) {
				idhskn=rsHSKN.getString("idhskn");
			}
			rsHSKN.close();
			for (int j = 0; j < this.listCT.size(); j++) {
				IHoSoKiemNghiemChiTiet ts=this.listCT.get(j);
				if(ts.getMaKiemNghiemVien().equals("")){
					db.getConnection().rollback();
					this.msg="Mời Bạn Chọn Đầy Đủ Kiểm Nghiệm Viên Trong Tab Kiểm Nghiệm";
					return false;
				}
				
			
				query="insert into ERP_HOSOKIEMNGHIEM_CHITIET(HOSOKIEMNGHIEM_FK,KIEMNGHIEMVIEN_FK,YEUCAUKYTHUAT_FK,THONGSOYEUCAU,THONGSOYEUCAUDEN,SOTT)  " +
						" values('"+idhskn+ "', '"+ ts.getMaKiemNghiemVien() + "','" + ts.getMaYCKT()+ "','" +ts.getThongSoYeuCau()+"','"+ts.getThongSoYeuCauDen()+"','"+ts.getSoTT()+"')";
				System.out.println("====================="+query);
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			for (int h = 0; h < this.listTB.size(); h++) {
				IHoSoKiemNghiemThietBi tb=listTB.get(h);
				query="insert into ERP_HOSOKIEMNGHIEM_THIETBI(HOSOKIEMNGHIEM_FK,THIETBI_FK,GHICHU,SOTT)  " +
						" values('"+idhskn+ "',(select PK_SEQ from ERP_THIETBISX where MA='"+ tb.getMaThietBi() + "'),N'" + tb.getGhiChu()+ "','" +tb.getSoTT()+"')";
				System.out.println("====================="+query);
				if (!db.update(query)) {
					db.getConnection().rollback();
					this.msg="Không thể cập nhật dòng lệnh:"+query;
					return false;
				}
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public void DBClose(){
		try {
			if(this.RsPhongBan==null)this.RsPhongBan.close();
			if(this.RsTCKiemNghiem==null)this.RsPhongBan.close();
			if(this.RsYCLayMau==null)this.RsYCLayMau.close();
			if(this.db==null)this.db.shutDown();
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
	/*String congtyId;
	String userId;
	String userTen;
	String id;
	String ma;
	String ten;
	String msg;
	dbutils db;
	String nppId;
	
	String ngaychungtu;
	String phongbanTH;
	String maSoKiemNghiem;
	String soPhieuKiemNghiem;
	String thoiGianMau;
	String nguoiGuiMau;
	
	String phieuYeuCauId;
	ResultSet phieuYeuCauRs;
	
	String sanPhamYcID;
	
	String tieuchuanKNId;
	ResultSet tieuchuenKNRs;
	
	String loaiKNId;
	ResultSet loaiKNRs;
	
	String thoidiemlaymau;
	
	String trangthai;
	String diengiai;
	
	public ErpHoSoKiemNghiem (){
		 this.id="";
		 this.nppId="";
		 this.congtyId ="";
		 this.userId="";
		 this.userTen="";
		 this. ma="";
		 this.ten="";
		 this.msg="";
		 
		 this.ngaychungtu="";
		 this.phongbanTH="";
		 this.maSoKiemNghiem="";
		 this.soPhieuKiemNghiem="";
		 this.thoiGianMau="";
		 this.nguoiGuiMau="";
			
		 this.phieuYeuCauId="";
		 this.sanPhamYcID="";
			
		 this.tieuchuanKNId="";
		 this.loaiKNId="";
		 this.thoidiemlaymau="";
			
		 this.trangthai="";
		 this.diengiai="";
		 
		 this.db = new dbutils();
	}
	
	
	public void init() {
		
		
	}
	
	public void createRs() {
		
		
	}
	
	public void DBclose() 
	{
		try
		{
			
		}
		catch(Exception er){
			er.printStackTrace();
		}
		finally
		{
			if(this.db!=null)
				this.db.shutDown();
		}
	}
	
	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public boolean createHSKN() {

		try {
			
			db.getConnection().setAutoCommit(false);
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean UpdateHSKN() {
		
		try {
			
			db.getConnection().setAutoCommit(false);
			
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}


	public String getCongtyId() {
		return congtyId;
	}


	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}


	public String getUserId() {
		return userId;
	}


	public void setUserId(String userId) {
		this.userId = userId;
	}


	public String getUserTen() {
		return userTen;
	}


	public void setUserTen(String userTen) {
		this.userTen = userTen;
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getMa() {
		return ma;
	}


	public void setMa(String ma) {
		this.ma = ma;
	}


	public String getTen() {
		return ten;
	}


	public void setTen(String ten) {
		this.ten = ten;
	}


	public String getMsg() {
		return msg;
	}


	public void setMsg(String msg) {
		this.msg = msg;
	}


	public String getNppId() {
		return nppId;
	}


	public void setNppId(String nppId) {
		this.nppId = nppId;
	}*/
	
	
}
