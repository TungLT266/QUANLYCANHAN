package geso.traphaco.erp.beans.hoadonkhacncc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.hoadonkhacncc.IDonvi;
 
import geso.traphaco.erp.beans.hoadonkhacncc.IErpHoadonkhacNcc;
import geso.traphaco.erp.beans.hoadonkhacncc.IKho;
import geso.traphaco.erp.beans.hoadonkhacncc.ISanpham;
import geso.traphaco.erp.beans.hoadonkhacncc.ITiente;
import geso.traphaco.erp.beans.hoadonkhacncc.ITrungTamChiPhi;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
 
public class ErpHoadonkhacNcc implements IErpHoadonkhacNcc {
	String congtyId;
	String userId;
	String ctyId;
	String cty;
	String id;
	double tiGiaNguyenTe;
	String Mauhoadon;
	String Kyhieu;
	String Sohoadon;
	String Ngayhoadon;
	String Nhaptaikho;
	String Diachi;
	String Masothue;
	String npp_duocchon_id="";
	
	DecimalFormat formatter_4le = new DecimalFormat("#########.####");
	public double getTiGiaNguyenTe() {
		return this.tiGiaNguyenTe;
	}

	public void SetTiGiaNguyenTe(double tiGiaNguyenTe) {
		this.tiGiaNguyenTe = tiGiaNguyenTe;
	}

	String hinhThucTT = "", diadiemgiaohang = "";

	public String getHinhThucTT() {
		return hinhThucTT;
	}

	public void setHinhThucTT(String hinhThucTT) {
		this.hinhThucTT = hinhThucTT;
	}

	// Them cot Nguon Goc Hang Hoa,TienTe_FK,mot don mua hang chi thuoc 1 loai
	// tien te
	String NguonGocHH;
	String MaLoaiHH;
	String TienTe_FK;
	String GhiChu;
	String ThueNhapKhau;
	String PhanTramThue;
	String TrungTamChiPhi_FK;
	float TyGiaQuyDoi;
	// Them cot Nguon Goc Hang Hoa,TienTe_FK
	String ngaymuahang, ETD = "", ETA = "";
	String dvthId;
	ResultSet dvthRs;
	String nccId;
	String nccTen;
	String nccLoai;
	String trangthai;
	String BVAT;
	String VAT;
	String AVAT;
	String lhhId;
	String sochungtu;
	String maDMH;

	String[] duyetIds;
	ResultSet lhhRs;
	List<ISanpham> spList;
	List<IDonvi> dvList;
	List<ITiente> tienteList;
	List<IKho> khoList;
	List<ITrungTamChiPhi> ListTTCP;

	String msg;
	String dungsai;
	String lspId;
	String isdontrahang;
	String maketoStock;
	String khoId;
	String canduyet;
	String quanlyCN;

	String sothamchieu;
	String[] ghichuArr;

	String[] cpkDiengiai;
	String[] cpkSotien;
	// Begin Thời Hạn Thanh Toán
	String[] ngayThanhToanArr;
	String[] phanTramThanhToanArr;
	String[] soTienThanhToanArr;
	
	// Thêm NCC
	ResultSet nccRs;
	String nhacungcapNK;
	ResultSet nguoiDuyetRs;
	
	public ResultSet getNguoiDuyetRs() {
		return nguoiDuyetRs;
	}

	public void setNguoiDuyetRs(ResultSet nguoiDuyetRs) {
		this.nguoiDuyetRs = nguoiDuyetRs;
	}

	public String getNhacungcapNK() {
		return nhacungcapNK;
	}

	public void setNhacungcapNK(String nhacungcapNK) {
		this.nhacungcapNK = nhacungcapNK;
	}

	public ResultSet getNccRs() {
		return nccRs;
	}

	public void setNccRs(ResultSet nccRs) {
		this.nccRs = nccRs;
	}

	public String[] getSoTienThanhToanArr() {
		return this.soTienThanhToanArr;
	}

	public void setSoTienThanhToanArr(String[] soTienThanhToanArr) {
		this.soTienThanhToanArr = soTienThanhToanArr;
	}

	public String[] getNgayThanhToanArr() {
		return this.ngayThanhToanArr;
	}

	public void setNgayThanhToanArr(String[] ngayThanhToanArr) {
		this.ngayThanhToanArr = ngayThanhToanArr;
	}
	
	public String[] getPhanTramThanhToanArr(){
		return this.phanTramThanhToanArr;
	}
	
	public void setPhanTramThanhToanArr(String[] phanTramThanhToan){
		this.phanTramThanhToanArr = phanTramThanhToan;
	}

	// End Thời Hạn Thanh Toán
	String checkedNoiBo;
	String dutoanId;

	String loai;
	String isDuocPhanBo; // PO trong nước
	String loaiDMH_NK; // PO nhập khẩu
	String thoihanno;

	ResultSet kenhRs;
	String kenhId;

	String sohopdong;
	String soluong;
	String tennhank;
	String tennhasx;
	String ngayship;
	String ngaynhapkho;
	String dvchiutrachnhiem;
	ResultSet thttRs;// Thời hạn thanh toán RS

	public String getCheckedNoiBo() {
		return checkedNoiBo;
	}

	public void setCheckedNoiBo(String checkedNoiBo) {
		this.checkedNoiBo = checkedNoiBo;
	}

	dbutils db;

	private Utility util;

	public ErpHoadonkhacNcc() {
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = "";
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.VAT = "10";
		this.sochungtu = "";
		this.AVAT = "";
		this.lhhId = "0";
		this.msg = "";
		this.dungsai = "10";
		this.NguonGocHH = "";
		this.MaLoaiHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.maDMH = "";
		this.Nhaptaikho="";
		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();

		this.checkedNoiBo = "0";
		this.lspId = "";
		// 0 Phiếu thanh toán - 1 ĐƠN MUA HÀNG
		this.isdontrahang = "1";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.quanlyCN = "1";
		this.sothamchieu = "";
		this.dutoanId = "";

		this.loai = "";
		this.isDuocPhanBo = "0";
		this.loaiDMH_NK = "";
		this.thoihanno = "";

		this.kenhId = "";

		this.sohopdong = "";
		this.soluong = "";
		this.tennhank = "";
		this.tennhasx = "";
		this.ngayship = "";
		this.ngaynhapkho = "";
		this.dvchiutrachnhiem = "";
		this.db = new dbutils();
		this.util = new Utility();
		this.Kyhieu="";
		this.Mauhoadon="";
		this.Ngayhoadon="";
		this.Sohoadon="";
		this.Diachi="";
		this.Masothue="";
		this.Nhaptaikho="";
		
	}

	public ErpHoadonkhacNcc(String id) {
		this.userId = "";
		this.ctyId = "";
		this.cty = "";
		this.id = id;
		this.ngaymuahang = "";
		this.dvthId = "";
		this.nccId = "";
		this.nccTen = "";
		this.nccLoai = "";
		this.trangthai = "";
		this.BVAT = "";
		this.sochungtu = "";
		this.VAT = "10";
		this.AVAT = "";
		this.lhhId = "0";
		this.msg = "";
		this.dungsai = "10";
		this.MaLoaiHH = "";
		this.NguonGocHH = "";
		this.TienTe_FK = "";
		this.GhiChu = "";
		this.maDMH = "";

		this.TyGiaQuyDoi = 1;
		this.spList = new ArrayList<ISanpham>();
		this.dvList = new ArrayList<IDonvi>();
		this.tienteList = new ArrayList<ITiente>();
		this.khoList = new ArrayList<IKho>();
		this.ListTTCP = new ArrayList<ITrungTamChiPhi>();

		this.checkedNoiBo = "0";
		this.lspId = "";
		this.isdontrahang = "0";
		this.maketoStock = "0";
		this.khoId = "";
		this.canduyet = "1";
		this.quanlyCN = "1";
		this.sothamchieu = "";
		this.dutoanId = "";

		this.loai = "";
		this.isDuocPhanBo = "0";
		this.loaiDMH_NK = "";
		this.thoihanno = "";

		this.kenhId = "";

		this.sohopdong = "";
		this.soluong = "";
		this.tennhank = "";
		this.tennhasx = "";
		this.ngayship = "";
		this.ngaynhapkho = "";
		this.dvchiutrachnhiem = "";
		this.db = new dbutils();
		this.util = new Utility();
		
		this.Kyhieu="";
		this.Mauhoadon="";
		this.Ngayhoadon="";
		this.Sohoadon="";
		this.Diachi="";
		this.Masothue="";
	}

	public String getCtyId() {
		return this.ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getCty() {
		return this.cty;
	}

	public void setCty(String cty) {
		this.cty = cty;
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

	public String getNgaymuahang() {
		return this.ngaymuahang;
	}

	public void setNgaymuahang(String ngaymuahang) {
		this.ngaymuahang = ngaymuahang;
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

	public String getNCC() {
		return this.nccId;
	}

	public void setNCC(String ncc) {
		this.nccId = ncc;
	}

	public String getTongtienchuaVat() {
		return this.BVAT;
	}

	public void setTongtienchuaVat(String ttchuavat) {
		this.BVAT = ttchuavat;
	}

	public String getVat() {
		/*
		 * if (this.VAT.length() == 0) this.VAT = "10";
		 */
		return this.VAT;
	}

	public void setVat(String vat) {
		this.VAT = vat;
	}

	public String getTongtiensauVat() {
		return this.AVAT;
	}

	public void setTongtiensauVat(String ttsauvat) {
		this.AVAT = ttsauvat;
	}

	public String getLoaispId() {
		return this.lspId;
	}

	public void setLoaispId(String loaispid) {
		this.lspId = loaispid;
	}

	public ResultSet getLoaiList() {
		return this.lhhRs;
	}

	public void setLoaiList(ResultSet loaihhlist) {
		this.lhhRs = loaihhlist;
	}

	public List<ISanpham> getSpList() {
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList) {
		this.spList = spList;
	}

	public String[] getDuyetIds() {
		return this.duyetIds;
	}

	public void setDuyetIds(String[] duyetIds) {
		this.duyetIds = duyetIds;
	}

	public void createRs() {
		
		// bổ sung phần NCC. 2016-05-01
		this.nccRs = db.get(" SELECT * FROM ERP_NHACUNGCAP WHERE TRANGTHAI = 1 AND CONGTY_FK = "+this.congtyId);
		 
		String sql = " select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'  ";
		this.dvthRs = db.get(sql);
		
		
		// lấy đơn vị thực hiện từ userId đăng nhập
		
		sql = " select PHONGBAN_FK from NHANVIEN where PK_SEQ= "+ this.userId;
		try{
			ResultSet rs = this.db.get(sql);
			if(rs!=null){
				if(rs.next()){
					this.dvthId = rs.getString("PHONGBAN_FK");
				}
			}
			
		} catch (Exception ex){
			
		}
		

	 
		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1' ");
		this.dvList.clear();
		if (donvi != null) {
			try {
				while (donvi.next()) {
					this.dvList.add(new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			} catch (SQLException e) {
			}
		}
 

		 
		
		
	String	query = " select ERP_TIENTE.PK_SEQ, ERP_TIENTE.MA, ERP_TIGIA.TIGIAQUYDOI " +
		" from ERP_TIENTE inner join ERP_TIGIA on ERP_TIENTE.PK_SEQ = ERP_TIGIA.TIENTE_FK " +
		" where ERP_TIENTE.Trangthai = '1' and ERP_TIGIA.Trangthai = '1' " +
		" order by ERP_TIENTE.PK_SEQ ASC";
		System.out.println("du lieu : "+query);

		ResultSet tiente = db.get(query);
		this.tienteList.clear();
		 
			try {
				while (tiente.next()) {
					
					if(this.TienTe_FK== null ||  this.TienTe_FK.equals("")){
						this.TienTe_FK= tiente.getString("pk_seq");
					}
					
					if(tiente.getString("pk_seq").equals(this.TienTe_FK)){
						if(	this.tiGiaNguyenTe==0){
							this.tiGiaNguyenTe =tiente.getDouble("TIGIAQUYDOI");
						}
						 
					}
					this.tienteList.add(new Tiente(tiente.getString("pk_seq"), tiente.getString("ma"),
							tiente.getString("TIGIAQUYDOI")));
				}
				tiente.close();
			} catch ( Exception e) {
				e.printStackTrace();
			}
		 
		System.out.println("ti gia nguyen te : "+this.tiGiaNguyenTe);
 

	}
	
	
	public List<String> createSanPham(){
		try{
			String sql = " select PK_SEQ from ERP_SANPHAM where TRANGTHAI = 1";
			ResultSet rs = this.db.get(sql);
			
			List<String> list = new ArrayList<String>();
			if(rs!= null){
				while(rs.next()){
					list.add(rs.getString("PK_SEQ"));
				}
			}
			return list;
		} catch ( Exception ex){
			ex.printStackTrace();
			return null;
		}
		
	}
	
	public List<String> createNhaCungCap(){
		try{
			String sql = " select PK_SEQ from ERP_SANPHAM where TRANGTHAI = 1";
			ResultSet rs = this.db.get(sql);
			
			List<String> list = new ArrayList<String>();
			if(rs!= null){
				while(rs.next()){
					list.add(rs.getString("PK_SEQ"));
				}
			}
			return list;
		} catch ( Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	public void createDuyetNCC(List<String> listsp, String ncc_fk){
		
		try{
			
			String listxuly = "";
			
			for(int i=0; i< listsp.size(); i++){
				listxuly = listxuly +","+ listsp.get(i);
				if(i == (listsp.size()-1)){
					listxuly = listxuly.substring(0,listxuly.length()-1);
				}
			}
			
			System.out.println("list xu lys"+ listxuly);

			this.db.getConnection().setAutoCommit(false);
			
		String query="insert into ERP_DUYETNHACUNGCAP(NHACUNGCAP_FK,SANPHAM_FK,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,TUNGAY,DENNGAY,TRANGTHAI) "
			+ "values("+ncc_fk+",'"+listxuly+"',"+100002+","+100002+",'2016-07-20','2016-07-20','2016-07-20','2016-07-20','1')";
		
		int k = this.db.updateReturnInt(query);
		if(k !=1){
			this.msg ="cập nhật bảng chi tiết thất bại";
			this.db.getConnection().rollback();
		}
		
		query = "SELECT IDENT_CURRENT('ERP_DUYETNHACUNGCAP') as Id";
		String id= "";
		ResultSet rs = this.db.get(query);
		if(rs !=null){
			if(rs.next()){
				id =rs.getString("Id");
			}
			rs.close();
		}
		
		
		// insert bảng ERP_DUYETNHACUNGCAP_SANPHAM
		for(int i=0; i< listsp.size(); i++){
			if(listsp.get(i).trim().length() >0){
				query =" insert into ERP_DUYETNHACUNGCAP_SANPHAM(DUYETNHACUNGCAP_FK, SANPHAM_FK)" +
					   " values("+ id+","+ listsp.get(i)+")";
				
				k = this.db.updateReturnInt(query);
				if(k !=1){
					this.msg ="cập nhật bảng chi tiết thất bại";
					this.db.getConnection().rollback();
				}
				
			}
		} 
		System.out.println("insert thanhf coong");
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		
		
		} catch( Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
	
	// tao bg
	public List<String> createBg(List<String> listTen){
		try{
			String sql = " select distinct b.PK_SEQ, b.TEN from temp_bgmua a " +
					     " left join ERP_NHACUNGCAP b on a.NCC_FK = b.PK_SEQ ";
			
			ResultSet rs = this.db.get(sql);
			List<String> list = new ArrayList<String>();
			if(rs!= null){
				while(rs.next()){
					list.add(rs.getString("PK_SEQ"));
					listTen.add(rs.getString("TEN"));
				}
			}
			return list;
		} catch ( Exception ex){
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public void createNewBg(String ncc_fk, String ten){
		try{
			this.db.getConnection().setAutoCommit(false);
			
			String query = "insert into ERP_BANGGIAMUANCC(ncc_fk,ten, ngaytao, ngaysua, nguoitao, nguoisua, " +
					" dvkd_fk, NHOMKENH_FK, trangthai, TuNgay, DADUYET) " +
			"values("+ncc_fk+ ",N'Bảng giá mua " +ten + "','" + getDateTime() + "','" + getDateTime() + "','100002', " +
				"100002,100001,100001,'1', '2016-07-01', 1)";

		int k = this.db.updateReturnInt(query);
		if(k !=1){
			this.msg ="cập nhật bảng chi tiết thất bại";
			this.db.getConnection().rollback();
		}
		
		System.out.println("insert thanhf coong");
		this.db.getConnection().commit();
		this.db.getConnection().setAutoCommit(true);
		
		
		} catch( Exception ex){
			ex.printStackTrace();
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	
	}
	
	
	public static void main(String arg[]){
//		ErpDonmuahang_Giay obj = new ErpDonmuahang_Giay();;
		/*List<String> listsp = obj.createSanPham();
		List<String> listncc = obj.createNhaCungCap();
		
		//obj.createDuyetNCC(listsp, "100234");
		
		for(int i=0; i< listncc.size(); i++){
			obj.createDuyetNCC(listsp, listncc.get(i));
		}*/
		
		// BAng GIá Mua
		/*List<String> listTen  = new ArrayList<String>();
		
		List<String> listncc = obj.createBg(listTen);
		
		for(int i=0; i< listncc.size(); i++){
			obj.createNewBg(listncc.get(i),listTen.get(i));
		}
		
		System.out.println("Thành công rồi nhe");*/
	}
	
	public void init() {

	 System.out.println("id : "+this.id);
		DecimalFormat formatter_0 = new DecimalFormat("###,###,###");
		String query =  " SELECT isnull(HD.KYHIEUHD,'') as KYHIEUHD,ISNULL(HD.NHAPTAIKHO,'') AS NHAPTAIKHO  ,NCC.TEN ,HD.NGAYGHINHAN,HD.SOCHUNGTU,HD.MAUHOADON,HD.NGAYHOADON,HD.SOHOADON,HD.NHACUNGCAP_FK,HD.DIACHI,HD.MASOTHUE,HD.CONGTY_FK, " +
						" HD.NPP_FK,HD.VAT_VND,HD.TONGTIENAVAT_VND,HD.TONGTIENBVAT_VND,HD.NGAYTAO,HD.NGAYSUA,HD.NGUOITAO,HD.NGUOISUA,HD.TIENTE_FK,HD.TRANGTHAI,HD.TYGIA ,HD.DIENGIAI,ISNULL(LOAIHANGHOA,'0') AS LOAIHANGHOA " +
						" FROM ERP_HOADONKHACNCC HD LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ=HD.NHACUNGCAP_FK  WHERE HD.PK_SEQ="	+ this.id + "";
		 
		System.out.println("Don Mua Hang : " + query);
		ResultSet rs = db.get(query);
	 
			try {
				while (rs.next()) {
					 this.ngaymuahang=rs.getString("NGAYGHINHAN");
					 this.sochungtu=rs.getString("SOCHUNGTU");
					 this.Mauhoadon=rs.getString("MAUHOADON");
					 this.Kyhieu=rs.getString("MAUHOADON");
					 this.Ngayhoadon=rs.getString("Ngayhoadon");
					 this.Sohoadon=rs.getString("Sohoadon");
					 this.Diachi=rs.getString("DIACHI");
					 this.Masothue=rs.getString("MASOTHUE");
					 this.Nhaptaikho=rs.getString("Nhaptaikho");
					 this.lhhId=rs.getString("LOAIHANGHOA");

					 this.VAT =formatter_0.format(rs.getDouble("VAT_VND"));
					 this.AVAT=  formatter_0.format(rs.getDouble("TONGTIENAVAT_VND"));
					 this.BVAT=formatter_0.format(rs.getDouble("TONGTIENBVAT_VND"));
					 
					 this.TienTe_FK=rs.getString("TIENTE_FK");
					 this.tiGiaNguyenTe=rs.getDouble("TYGIA");
					 this.nccId=rs.getString("NHACUNGCAP_FK");
					 this.nccTen=rs.getString("TEN");
					 this.GhiChu=rs.getString("DIENGIAI");
					 this.Kyhieu=rs.getString("KYHIEUHD");
					 
				}
				rs.close();
			} catch (Exception e) {
				System.out.println("__Exception: " + e.getMessage());
			}
	  
		this.createRs();
		this.createSanpham();
	 
	}
 
	private void createSanpham() {

		String query =  "  SELECT " +
				 " case \n" + 
				 " when A.SANPHAM_FK is not null then ISNULL(SP.TEN,'') \n" + 
				 " when A.MASCLON_FK is not null then ISNULL(SCL.TEN,'') \n" + 
				 " when A.CPTT_FK is not null then ISNULL(cptt.DIENGIAI,'') \n" + 
				 " when A.KHOANMUCCHIPHI_FK is not null then ISNULL(NCP.DIENGIAI,'') \n" + 
				 " else '' end as Ten \n" + 
				 ", case \n" + 
				 " when A.SANPHAM_FK is not null then ISNULL(SP.MA,'') \n" + 
				 " when A.MASCLON_FK is not null then ISNULL(SCL.MA,'') \n" + 
				 " when A.CPTT_FK is not null then ISNULL(cptt.MA,'') \n" + 
				 " when A.KHOANMUCCHIPHI_FK is not null then ISNULL(NCP.TEN,'') \n" + 
				 " else '' end as MA, \n"
				+ "TK.SOHIEUTAIKHOAN+'['+ TK.TENTAIKHOAN +']' aS tentk , \n"
				+ " A.TAIKHOANKT_FK,CAST( A.SANPHAM_FK AS VARCHAR(18)) AS SANPHAM_FK \n" +
				" ,isnull( A.TENHANG,'') as TENHANG ,A.DVT,A.SOLUONG,A.DONGIA,A.THANHTIEN,A.THANHTIENVND,A.PHANTRAMVAT,A.TIENVATVND \n" + 
						" FROM ERP_HOADONKHACNCC_SANPHAM A \n"
						+ "left JOIN  ERP_SANPHAM SP ON SP.PK_SEQ =A.SANPHAM_FK  \n" +
						" LEFT JOIN ERP_MASCLON SCL ON SCL.PK_SEQ = A.MASCLON_FK \n" +
						" LEFT JOIN erp_congcudungcu cptt ON cptt.PK_SEQ = A.CPTT_FK \n" +
						" LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = A.KHOANMUCCHIPHI_FK \n" +
						" INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ= A.TAIKHOANKT_FK  " +
						" WHERE HOADONKHACNCC_FK="+this.id;
		

		System.out.println(" San pham init: " + query);
		ResultSet spRs = db.get(query);
		List<ISanpham> spList = new ArrayList<ISanpham>();

		NumberFormat formatter = new DecimalFormat("#,###,###.##########");
		if (spRs != null) {
			try {
				ISanpham sp = null;
				while (spRs.next()) {
					sp = new Sanpham();
				 
					sp.setPK_SEQ(spRs.getString("SANPHAM_FK")== null ? "" : spRs.getString("SANPHAM_FK"));
					sp.setMasanpham(spRs.getString("MA"));
					sp.setTensanpham(spRs.getString("TENHANG"));
					 //						THANHTIENVND	PHANTRAMVAT	TIENVATVND
					  
					sp.setSoluong(formatter.format(spRs.getDouble("SOLUONG")));
					sp.setDonvitinh(spRs.getString("DVT"));
				 
					sp.setDongia(formatter.format(spRs.getDouble("dongia")));
					sp.setPhantramthue(spRs.getDouble("PHANTRAMVAT"));
					sp.setTienthue(spRs.getDouble("TIENVATVND"));
					sp.setThanhtien(spRs.getDouble("THANHTIENVND"));
					sp.setThanhtienNguyenTe(spRs.getDouble("THANHTIEN")); 
					sp.setTaikhoanKTId(spRs.getString("TAIKHOANKT_FK"));
					sp.setSohieutaikhoan(spRs.getString("tentk"));
				  
					spList.add(sp);
				}
				spRs.close();
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Khong the tao san Pham" + e.getMessage());
			}
		}

		this.spList = spList;
		System.out.println("size : " + this.spList.size());
	}

	public void CreatePOfromPR(ResultSet rs, String mnlId) {
		try {
			if (rs.next()) {
				this.db.getConnection().setAutoCommit(false);

				String query = "Insert into Erp_MuaHang(NgayMua, DonViThucHien_FK, NhaCungCap_FK, "
						+ "LoaiHangHoa_FK, LoaiSanPham_FK, TongTienAVAT, VAT, TongTienBVAT, DungSai, TrangThai, NgayTao, "
						+ "NgaySua, NguoiTao, NguoiSua, congty_fk, HTTT) " + "Values('" + this.getDateTime()
						+ "','100003', null, '0', '100002', '0', '0' , '0' , '0', '0', '" + this.getDateTime() + "', "
						+ "'" + this.getDateTime() + "'," + this.userId + "," + this.userId + ", '" + this.congtyId
						+ "',N'" + this.hinhThucTT + "')";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang: " + query;
					this.db.getConnection().rollback();
				}

				query = "select SCOPE_IDENTITY() as dmhId";
				ResultSet rsDmh = db.get(query);
				if (rsDmh.next()) {
					this.id = rsDmh.getString("dmhId");
					rsDmh.close();
				}

				query = " insert into ERP_MUAHANG_SP(muahang_fk, sanpham_fk, diengiai, donvi, soluong, muanguyenlieudukien_fk) "
						+ " values(" + this.id + ", " + rs.getString("SANPHAM_FK") + ", N'" + rs.getString("TEN")
						+ "', N'" + rs.getString("DONVI") + "', " + "'" + rs.getString("SOLUONG") + "', '" + mnlId
						+ "')";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang: " + query;
					this.db.getConnection().rollback();
				}

				 

				// Cap nhat DADATHANG trong ERP_MUANGUYENLIEUDUKIEN
				query = "UPDATE ERP_MUANGUYENLIEUDUKIEN	"
						+ "SET ERP_MUANGUYENLIEUDUKIEN.DADATHANG = ISNULL(A.SOLUONG, 0) " + "FROM " + "( "
						+ "	SELECT SUM(SOLUONG) AS SOLUONG, SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4) AS NAM,	"
						+ "	CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2)) AS THANG	" + "	FROM ERP_MUAHANG_SP "
						+ "	WHERE SANPHAM_FK IS NOT NULL	"
						+ "	GROUP BY SANPHAM_FK, SUBSTRING(NGAYNHAN, 1, 4),CONVERT(INT, SUBSTRING(NGAYNHAN, 6,2))	"
						+ ")A  " + "WHERE ERP_MUANGUYENLIEUDUKIEN.NAM = A.NAM	"
						+ "AND ERP_MUANGUYENLIEUDUKIEN.THANG = A.THANG	"
						+ "AND ERP_MUANGUYENLIEUDUKIEN.SANPHAM_FK = A.SANPHAM_FK ";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi Mua hang: " + query;
					this.db.getConnection().rollback();
				}

				/*
				 * boolean vuotNganSach = false; //Chen co che duyet // insert
				 * nguoi duyet PO query =
				 * "SELECT	DUYETCHIPHI.CHUCDANH_FK, DUYETCHIPHI.QUYETDINH, DUYETCHIPHI.THUTU "
				 * + "FROM ERP_MUAHANG MUAHANG " +
				 * "INNER JOIN ERP_CHINHSACHDUYETCHIPHI DUYETCHIPHI ON DUYETCHIPHI.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
				 * +
				 * "INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = DUYETCHIPHI.KHOANGCHIPHI_FK "
				 * +
				 * "INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETCHIPHI.CHUCDANH_FK "
				 * +
				 * "WHERE KHOANGCHIPHI.SOTIENTU < MUAHANG.TONGTIENBVAT AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) "
				 * + "AND MUAHANG.PK_SEQ = '" + this.id +
				 * "' ORDER BY DUYETCHIPHI.THUTU" ;
				 * 
				 * System.out.println("3.Duyet PO:" + query);
				 * 
				 * rs = db.get(query);
				 * 
				 * boolean dacoTongGiamDoc = false; int thutu = 0;
				 * 
				 * while (rs.next()) {
				 * if(rs.getString("CHUCDANH_FK").equals("100003"))
				 * dacoTongGiamDoc = true;
				 * 
				 * thutu = Integer.parseInt(rs.getString("THUTU"));
				 * 
				 * query =
				 * "INSERT INTO ERP_DUYETMUAHANG(MUAHANG_FK, CHUCDANH_FK, TRANGTHAI, QUYETDINH, THUTU) "
				 * + "VALUES('"+ this.id +"', '" + rs.getString("CHUCDANH_FK") +
				 * "', '0','" + rs.getString("QUYETDINH")+ "','" +
				 * rs.getString("THUTU") + "') ";
				 * 
				 * System.out.println("4. Insert Duyet PO:" + query); if
				 * (!db.update(query)) { this.msg =
				 * "Khong the them nguoi duyet cho PO: " + query;
				 * db.getConnection().rollback(); } }
				 * 
				 * if (rs != null) rs.close();
				 * 
				 * query = "Update ERP_MUAHANG set VUOTNGANSACH = '" +
				 * (vuotNganSach == true ? "1" : "0") + "' where pk_seq = '" +
				 * this.id + "' "; if (!db.update(query)) { this.msg =
				 * "Khong the cap nhat ERP_MUAHANG: " + query;
				 * db.getConnection().rollback(); }
				 */

				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);
				this.init();

			}
		} catch (Exception e) {
			db.update("rollback");
			e.printStackTrace();
		}
	}

	public boolean createDmh() {
		// Kiem tra moi them vao
		String query = "";
		getNppInfo();
		if(!check_valid())
		{
			return false;
		}
  		 
		try {
			 
			db.getConnection().setAutoCommit(false);
 
			
			query = " insert into ERP_HOADONKHACNCC(NGAYGHINHAN,SOCHUNGTU,MAUHOADON,NGAYHOADON,SOHOADON,NHACUNGCAP_FK,DIACHI,MASOTHUE,CONGTY_FK,VAT_VND,TONGTIENAVAT_VND,TONGTIENBVAT_VND,NGAYTAO,NGAYSUA,NGUOITAO,NGUOISUA,TIENTE_FK,TRANGTHAI,TYGIA,DIENGIAI,KYHIEUHD,NHAPTAIKHO,LOAIHANGHOA) " +
					" VALUES ('"+this.ngaymuahang+"','"+this.sochungtu+"','"+this.Mauhoadon+"','"+this.Ngayhoadon+"','"+this.Sohoadon+"',"+this.nccId+",N'"+this.Diachi+"','"+this.Masothue+"',"+this.congtyId+"," +
							" "+this.VAT+","+this.AVAT+","+this.BVAT+",'"+this.getDateTime()+"','"+this.getDateTime()+"',"+this.userId+","+this.userId+","+this.TienTe_FK+",'0',"+this.tiGiaNguyenTe+",N'"+this.GhiChu+"',N'"+this.Kyhieu+"',N'"+this.Nhaptaikho+"',"+this.lhhId+" ) 	 ";
			
			
			System.out.println("luu erp_muahang " + query);
			// System.out.println("Insert into Erp_MuaHang " + query);
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật dòng lệnh: " + query;
				db.getConnection().rollback();
				return false;
			}

			String dmhCurrent = "";
			query = "select SCOPE_IDENTITY() as dmhId";
			ResultSet rsDmh = db.get(query);
			if (rsDmh.next()) {
				dmhCurrent = rsDmh.getString("dmhId");
				//this.id = dmhCurrent;
				rsDmh.close();
			}
			
			 
			//End - Thời Hạn Thanh Toán - Dương
			// Neu la chi phi, xem xem co vuot ngan sach khong
			for (int i = 0; i < this.spList.size(); i++) {
				ISanpham sp = this.spList.get(i);
				
				String  SanPham_FK = sp.getPK_SEQ();
				  
			 
				 
				
				if (  SanPham_FK=="") {
					SanPham_FK="NULL";
					
				}
				String dvt=sp.getDonvitinh();
				if(dvt==null || dvt.equals("")){
					dvt="NULL";
				}
				 
				
				if(this.lhhId.equals("0")) // lOẠI BẰNG 0 INSET VO SANPHAM
				{
					query=	" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,SANPHAM_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
							" VALUES("+dmhCurrent+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";
					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}  
				else if(this.lhhId.equals("1")) // lOẠI BẰNG 1 INSERT  VO MASCLON
				{
					query=	" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,MASCLON_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
							" VALUES("+dmhCurrent+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";
					
					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				else if(this.lhhId.equals("2")) // lOẠI BẰNG2 INSERT  VO CPTT_FK
				{
					query=	" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,CPTT_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
							" VALUES("+dmhCurrent+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";
								    
					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				
				else if(this.lhhId.equals("3")) // lOẠI BẰNG 3 INSERT  VO KHOANMUCCHIPHI_FK
				{
					if(SanPham_FK==null||SanPham_FK.length()<=0)
					{
						this.db.getConnection().rollback();
						this.msg = "S1.2 Vui lòng khoản mục chi phí. ";
						return false;
					}
					query=	" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,KHOANMUCCHIPHI_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
							" VALUES("+dmhCurrent+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";
								    
					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				 
				 
			}
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
	}

	private boolean check_valid() {
		// TODO Auto-generated method stub
		 
		if (this.nccId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nhà cung cấp.";
			return false;
		}
 
 
		if (this.spList.size() <= 0) {
			this.msg = "Vui lòng chọn sản phẩm";
			return false;
		} else {
			if (this.loai.equals("2")) {
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);
 
					 
						if (sp.getSoluong() == null || sp.getSoluong().length() == 0
								|| Double.parseDouble(sp.getSoluong()) == 0) {
							this.msg = "Vui lòng nhập số lượng sản phẩm.";
							return false;
						}
					 if(sp.getTaikhoanKTId().trim().equals("")){
							this.msg = "Vui lòng nhập số tài khoản kế toán cho dòng sản phẩm: "+sp.getTensanpham();
							return false;
					 }
				}
			}
		}
		
		
		if (this.TienTe_FK.trim().length() <= 0) {
			this.msg = "Vui lòng chọn tiền tệ của đơn mua hàng";
			return false;
		}

		if (!this.loai.equals("0") && this.nccTen.trim().length() <= 0) {
			this.msg = "Vui lòng nhập tên nhà cung cấp mua hàng";
			return false;
		}
		if(this.Sohoadon.equals("")){
			this.msg = "Vui lòng nhập số hóa đơn";
			return false;
		}
		if(this.Ngayhoadon.equals("")){
			this.msg = "Vui lòng nhập ngày hóa đơn";
			return false;
		}
		if(this.sochungtu.equals("")){
			this.msg = "Vui lòng nhập số chứng từ hóa đơn";
			return false;
		}
		
		return true;
		
	}

	private String CreateChinhSachDuyet(String phongbanId, String loai){
		String query ="  select ";
		return query;
	}
	
	private String CreateChinhSachDuyet(){
		String query = "SELECT	NCC.CHUCDANH_FK, NCC.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG  "
		+ "INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "WHERE MUAHANG.PK_SEQ = '" + this.id + "' " +

		"UNION ALL " +
		// NẾU KO CÓ CHÍNH SÁCH DUYỆT DÀNH CHO SẢN PHẨM CỦA ĐƠN
		// MUA HÀNG VÀ KO CÓ CHÍNH SÁCH DUYỆT CHO NCC CỦA ĐƠN
		// MUA HÀNG
		"SELECT	SP.CHUCDANH_FK, SP.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG  "
		+ "INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
		+ "AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id
		+ "') " + "LEFT JOIN " + "(  " + "	SELECT	COUNT(*) AS NUM " + "	FROM ERP_MUAHANG MUAHANG   "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "	AND NCC.NCC_FK IN (SELECT NHACUNGCAP_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id + "')  "
		+ "	WHERE MUAHANG.PK_SEQ = '" + this.id + "'   " + ")DUYET_NCC ON 1 = 1 " + "LEFT JOIN " + "( "
		+ "	SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM " + "	FROM ERP_MUAHANG_SP MH_SP  "
		+ "	INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
		+ "	WHERE MH.PK_SEQ = '" + this.id + "'  AND MH_SP.SANPHAM_FK NOT IN  "
		+ "	(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
		+ ")KTRA_SP ON 1 = 1 " + "WHERE MUAHANG.PK_SEQ = '" + this.id
		+ "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " +

		"UNION ALL " + "SELECT	CP.CHUCDANH_FK, CP.QUYETDINH " + "FROM ERP_MUAHANG MUAHANG   "
		+ "INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "INNER JOIN ERP_CHINHSACHDUYET_CHIPHI CP ON CP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
		+ "INNER JOIN ERP_KHOANGCHIPHI KHOANGCHIPHI ON KHOANGCHIPHI.PK_SEQ = CP.KHOANGCHIPHI_FK  "
		
		+" INNER JOIN ERP_CHUCDANH CD ON CD.PK_SEQ=CP.CHUCDANH_FK AND CD.TRANGTHAI=1 " 
		
		+ "LEFT JOIN( " + "	SELECT	COUNT(SP.CHUCDANH_FK) AS NUM " + "	FROM ERP_MUAHANG MUAHANG   "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET_SANPHAM SP ON SP.CHINHSACHDUYET_FK = DUYET.PK_SEQ   "
		+ "	AND SP.SANPHAM_FK IN (SELECT SANPHAM_FK FROM ERP_MUAHANG_SP WHERE MUAHANG_FK = '" + this.id
		+ "') " + "	LEFT JOIN " + "	( " + "		SELECT	COUNT(*) AS NUM "
		+ "		FROM ERP_MUAHANG MUAHANG   "
		+ "		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "		INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "		AND NCC.NCC_FK IN (SELECT NCC_FK FROM ERP_MUAHANG WHERE PK_SEQ = '" + this.id + "')  "
		+ "		WHERE MUAHANG.PK_SEQ = '" + this.id + "'   " + "	)DUYET_NCC ON 1 = 1 "
		+ "	LEFT JOIN " + "	( " + "		SELECT COUNT(MH_SP.SANPHAM_FK) AS NUM "
		+ "		FROM ERP_MUAHANG_SP MH_SP  "
		+ "		INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = MH_SP.MUAHANG_FK "
		+ "		INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MH.DONVITHUCHIEN_FK AND DUYET.TRANGTHAI = 1 "
		+ "		WHERE MH.PK_SEQ = '" + this.id + "'  AND MH_SP.SANPHAM_FK NOT IN  "
		+ "		(SELECT SANPHAM_FK FROM ERP_CHINHSACHDUYET_SANPHAM WHERE CHINHSACHDUYET_FK = DUYET.PK_SEQ) "
		+ "	)KTRA_SP ON 1 = 1 " + "	WHERE MUAHANG.PK_SEQ = '" + this.id
		+ "'  AND DUYET_NCC.NUM = 0 AND KTRA_SP.NUM = 0 " +

		")DUYET_SP ON 1 = 1 " + "LEFT JOIN( " + "	SELECT	COUNT(NCC.CHUCDANH_FK) AS NUM "
		+ "	FROM ERP_MUAHANG MUAHANG   "
		+ "	INNER JOIN ERP_CHINHSACHDUYET DUYET ON DUYET.DONVITHUCHIEN_FK = MUAHANG.DONVITHUCHIEN_FK "
		+ "	INNER JOIN ERP_CHINHSACHDUYET_NCC NCC ON NCC.CHINHSACHDUYET_FK = DUYET.PK_SEQ AND NCC.NCC_FK = MUAHANG.NHACUNGCAP_FK "
		+ "	WHERE MUAHANG.PK_SEQ = '" + this.id + "'" + ")DUYET_NCC ON 1 = 1 "
		+ "WHERE  (case when KHOANGCHIPHI.SOTIENTU =0  then  KHOANGCHIPHI.SOTIENTU-1 else  KHOANGCHIPHI.SOTIENTU end ) < MUAHANG.TONGTIENBVAT "
		+ "AND (KHOANGCHIPHI.SOTIENDEN >= MUAHANG.TONGTIENBVAT OR KHOANGCHIPHI.SOTIENDEN IS NULL) "
		+ "AND MUAHANG.PK_SEQ = '" + this.id + "' AND DUYET_SP.NUM = 0 AND DUYET_NCC.NUM = 0 ";
		// 01/06/2016: Ngày quốc tế thiếu nhi, ^^
		// Lưu ý: không tính tỉ giá quy đổi tại đây vì đã quy đổi rồi.
		// Khi tạo tính lại tiền thì vui lòng chia ngược lại với tỉ giá để biết được đúng số nguyên tệ
		// MUAHANG.TONGTIENBVAT hiện tại đang lưu tiền VND đã được quy đổi sang USD
		
		return query;
	}
	public boolean updateDmh() {
		// Kiem tra moi them vao
		String query = "";
		getNppInfo();
		if(!check_valid())
		{
			return false;
		}
  		 
		try {
			 
			db.getConnection().setAutoCommit(false);
 
			
			query = " update  ERP_HOADONKHACNCC set NGAYGHINHAN ='"+this.ngaymuahang+"' , " +
					" SOCHUNGTU='"+this.sochungtu+"',MAUHOADON=N'"+this.Mauhoadon+"',NGAYHOADON='"+this.Ngayhoadon+"',SOHOADON='"+this.Sohoadon+"' " +
					",NHACUNGCAP_FK="+this.nccId+",DIACHI=N'"+this.Diachi+"',MASOTHUE=N'"+this.Masothue+"', " +
					" VAT_VND ="+this.VAT+",TONGTIENAVAT_VND="+this.AVAT+",TONGTIENBVAT_VND="+this.BVAT+", " +
					"  NGAYSUA='"+this.getDateTime()+"', NGUOISUA="+this.userId+", " +
					" TIENTE_FK="+this.TienTe_FK+",TYGIA="+this.tiGiaNguyenTe+", " +
					" DIENGIAI=N'"+this.GhiChu+"',KYHIEUHD =N'"+this.Kyhieu+"',NHAPTAIKHO=N'"+this.Nhaptaikho+"' " +
					"   where pk_seq="+this.id;
			
			
			System.out.println("luu erp_muahang " + query);
			// System.out.println("Insert into Erp_MuaHang " + query);
			if (!db.update(query)) {
				this.msg = "Không thể cập nhật dòng lệnh: " + query;
				db.getConnection().rollback();
				return false;
			}

		 
			
			 query="delete ERP_HOADONKHACNCC_SANPHAM where HOADONKHACNCC_FK="+this.id;
			 if (!db.update(query)) {
					this.msg = "Không thể cập nhật dòng lệnh: " + query;
					db.getConnection().rollback();
					return false;
				}

			 
		 
			for (int i = 0; i < this.spList.size(); i++) {
				ISanpham sp = this.spList.get(i);
				
				String  SanPham_FK = sp.getPK_SEQ();
				  
			 
				if (  SanPham_FK=="") {
					SanPham_FK="NULL";
					
				}
				String dvt=sp.getDonvitinh();
				if(dvt==null || dvt.equals("")){
					dvt="NULL";
				}

				
				if(this.lhhId.equals("0")) // lOẠI BẰNG 0 INSET VO SANPHAM
				{
					query=" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,SANPHAM_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
					" VALUES("+this.id+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";

					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}  
				else if(this.lhhId.equals("1")) // lOẠI BẰNG 1 INSERT  VO MASCLON
				{
					query=" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,MASCLON_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
							" VALUES("+this.id+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";
					
				    
					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				else if(this.lhhId.equals("2")) // lOẠI BẰNG2 INSERT  VO CPTT_FK
				{
					query=" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,CPTT_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
							" VALUES("+this.id+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";
					
					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				else if(this.lhhId.equals("3")) // lOẠI BẰNG 3 INSERT  VO KHOANMUCCHIPHI_FK
				{
					if(SanPham_FK==null||SanPham_FK.length()<=0)
					{
						this.db.getConnection().rollback();
						this.msg = "S1.2 Vui lòng khoản mục chi phí. ";
						return false;
					}
					query=	" insert into ERP_HOADONKHACNCC_SANPHAM(HOADONKHACNCC_FK,TAIKHOANKT_FK,KHOANMUCCHIPHI_FK,TENHANG,DVT,SOLUONG,DONGIA,THANHTIEN,THANHTIENVND,PHANTRAMVAT,TIENVATVND) " +
							" VALUES("+this.id+","+sp.getTaikhoanKTId()+","+SanPham_FK+",N'"+sp.getTensanpham()+"',"+dvt+","+sp.getSoluong()+","+sp.getDongia()+","+sp.getThanhtienNguyenTe()+","+sp.getThanhtien()+","+sp.getPhantramthue()+","+sp.getTienthue()+")";
								    
					if (!db.update(query)) {
						this.msg = "Không thể cập nhật dòng lệnh: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				 
			}
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			this.db.update("rollback");
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
	}
	

	
	
	

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public List<IDonvi> getDvList() {
		return this.dvList;
	}

	public void setDvList(List<IDonvi> dvList) {
		this.dvList = dvList;
	}

	public List<IKho> getKhoList() {
		return this.khoList;
	}

	public void setKhoList(List<IKho> khoList) {
		this.khoList = khoList;
	}

	public List<ITiente> getTienteList() {
		return this.tienteList;
	}

	public void setTienteList(List<ITiente> ttList) {
		this.tienteList = ttList;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public void close() {
		try {

			if (this.dvthRs != null) {
				this.dvthRs.close();
			}
			if (this.lhhRs != null) {
				this.lhhRs.close();
			}
			if (spList != null) {
				spList.clear();
			}
			if (dvList != null) {
				dvList.clear();
			}

			if (tienteList != null) {
				tienteList.clear();
			}
			if (khoList != null) {
				khoList.clear();
			}
			if (ListTTCP != null) {
				ListTTCP.clear();
			}
			this.db.shutDown();
		} catch (SQLException e) {

		}

	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getDungsai() {
		return this.dungsai;
	}

	public void setDungsai(String dungsai) {
		this.dungsai = dungsai;
	}

	public String getSochungtu() {
		return this.sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	public void setNguonGocHH(String nguongoc) {
		this.NguonGocHH = nguongoc;
	}

	public String getNguonGocHH() {
		return this.NguonGocHH;
	}

	public void setMaLoaiHH(String maloaihh) {
		this.MaLoaiHH = maloaihh;

	}

	public String getMaLoaiHH() {

		return this.MaLoaiHH;
	}

	public void setTienTe_FK(String tiente_fk) {
		this.TienTe_FK = tiente_fk;

	}

	public String getTienTe_FK() {

		return this.TienTe_FK;
	}

	public String getGhiChu() {

		return this.GhiChu;
	}

	public void setGhiChu(String ghichu) {

		this.GhiChu = ghichu;
	}

	public void setTrungTamChiPhi_FK(String trungtamchiphi_fk) {
		this.TrungTamChiPhi_FK = trungtamchiphi_fk;
	}

	public String getTrungTamChiPhi_FK() {

		return this.TrungTamChiPhi_FK;
	}

	public void CreateListTrungTamChiPhi() {
		String query = "Select PK_SEQ,Ma,Ten From Erp_TrungTamChiPhi Where TrangThai=1";
		ResultSet rsTTCP = this.db.get(query);
		try {
			while (rsTTCP.next()) {
				ITrungTamChiPhi o = new TrungTamChiPhi();
				o.setId(rsTTCP.getString("PK_SEQ"));
				o.setMaChiPhi(rsTTCP.getString("Ma"));
				o.setTen(rsTTCP.getString("Ten"));
				this.ListTTCP.add(o);
			}
			rsTTCP.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

	public List<ITrungTamChiPhi> getTrungTamCpList() {

		return this.ListTTCP;
	}

	public void setTrungTamCpList(List<ITrungTamChiPhi> ttcp) {
		this.ListTTCP = ttcp;
	}

	public void setTyGiaQuyDoi(float tygiaquydoi) {
		this.TyGiaQuyDoi = tygiaquydoi;
	}

	public Float GetTyGiaQuyDoi() {

		return this.TyGiaQuyDoi;
	}

	public ResultSet getDuyet() {
		ResultSet rs;
		String query = "SELECT DUYETMUAHANG.CHUCDANH_FK, CHUCDANH.DIENGIAI, DUYETMUAHANG.TRANGTHAI, "
				+ "CASE WHEN (SELECT ISNULL(DACHOT,0) FROM ERP_MUAHANG WHERE PK_SEQ = DUYETMUAHANG.MUAHANG_FK) = 1 AND CHUCDANH.NHANVIEN_FK = '"
				+ this.userId + "' THEN '1' " + "ELSE '0' END AS QUYEN " + "FROM ERP_DUYETMUAHANG DUYETMUAHANG "
				+ "INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK "
				+ "WHERE DUYETMUAHANG.MUAHANG_FK = '" + this.id + "'";
		System.out.println(query);
		rs = this.db.get(query);
		return rs;
	}

	public String getTrangthaiDuyet() {
		String result = "Chờ duyệt";

		String query = "SELECT " + "	CASE WHEN SUM(QUYETDINH) > 0 THEN  " + "("
				+ "	SELECT COUNT(TRANGTHAI) - SUM(TRANGTHAI) " + "	FROM ERP_DUYETMUAHANG " + "	WHERE MUAHANG_FK = '"
				+ this.id + "' AND QUYETDINH = 1 " + ") " + "ELSE " + "	COUNT(TRANGTHAI) - SUM(TRANGTHAI) "
				+ "END AS RESULT " + "FROM ERP_DUYETMUAHANG " + "WHERE MUAHANG_FK = '" + this.id + "'";

		System.out.println("Trang thai duyet" + query);

		ResultSet rs = this.db.get(query);
		try {
			if (rs != null) {
				if (rs.next()) {
					String tmp = rs.getString("RESULT");
					if (tmp != null) {
						if (tmp.equals("0"))
							result = "Đã duyệt";
					} else {
						result = "Không cần duyệt";
					}
					rs.close();
				} else {
					result = "Không cần duyệt";
				}
			} else {
				result = "Không cần duyệt";
			}

		} catch (SQLException e) {
		}

		return result;

	}

	public String getLoaihanghoa() {
		return this.lhhId;
	}

	public void setLoaihanghoa(String loaihh) {
		this.lhhId = loaihh;
	}

	public String getIsdontrahang() {
		return this.isdontrahang;
	}

	public void setIsdontrahang(String dontrahang) {
		this.isdontrahang = dontrahang;
	}

	public String getMakeToStock() {
		return this.maketoStock;
	}

	public void setMakeToStock(String maketoStock) {
		this.maketoStock = maketoStock;
	}

	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	public String getKhoId() {
		return this.khoId;
	}

	public void setKhoId(String khoId) {
		this.khoId = khoId;
	}

	public String getNccTen() {

		return this.nccTen;
	}

	public void setNccTen(String nccTen) {

		this.nccTen = nccTen;
	}

	public String getNccLoai() {

		return this.nccLoai;
	}

	public void setNccLOai(String nccLoai) {

		this.nccLoai = nccLoai;
	}

	public String getCanDuyet() {
		if (this.id != null && this.id.length() > 0) {
			String sql = "select DACHOT from  ERP_MUAHANG  where pk_seq =" + this.id;
			ResultSet rs = db.get(sql);
			try {
				if (rs.next()) {
					this.canduyet = rs.getString("DACHOT");
				}
				rs.close();
			} catch (Exception er) {
				er.printStackTrace();
			}

		}
		return this.canduyet;
	}

	public void setCanDuyet(String canduyet) {

		this.canduyet = canduyet;
	}

	public void setQuanlycongno(String quanlyCN) {

		this.quanlyCN = quanlyCN;
	}

	public String getQuanlycongno() {

		return this.quanlyCN;
	}

	public String getSoThamChieu() {

		return this.sothamchieu;
	}

	public void setSoThamChieu(String sothamchieu) {

		this.sothamchieu = sothamchieu;
	}

	public String[] getGhiChuArr() {
		return this.ghichuArr;
	}

	public void setGhiChuArr(String[] ghichuArr) {
		this.ghichuArr = ghichuArr;
	}

	public String getETD() {
		return this.ETD;
	}

	public void setETD(String ETD) {
		this.ETD = ETD;
	}

	public String getETA() {
		return this.ETA;
	}

	public void setETA(String ETA) {
		this.ETA = ETA;
	}

	public String[] getCpkDienGiai() {

		return this.cpkDiengiai;
	}

	public void setCpkDiengiai(String[] cpkDD) {

		this.cpkDiengiai = cpkDD;
	}

	public String[] getCpkSoTien() {

		return this.cpkSotien;
	}

	public void setCpkSoTien(String[] cpkST) {

		this.cpkSotien = cpkST;
	}

	public String getDiaDiemGiaoHang() {
		return this.diadiemgiaohang;
	}

	public void setDiaDiemGiaoHang(String ddgh) {
		this.diadiemgiaohang = ddgh;
	}

	public String getmaDMH() {

		return this.maDMH;
	}

	public void setmaDMH(String maDMH) {
		this.maDMH = maDMH;

	}

	public String getDutoanId() {
		return this.dutoanId;
	}

	public void setDutoanId(String dutoanId) {
		this.dutoanId = dutoanId;
	}

	public String getLoai() {
		return this.loai;
	}

	public void setLoai(String Loai) {
		this.loai = Loai;
	}

	public String getLoaiDMH_NK() {
		return this.loaiDMH_NK;
	}

	public void setLoaiDMH_NK(String loaiDMH_NK) {
		this.loaiDMH_NK = loaiDMH_NK;
	}

	public String getThoihanno() {
		return this.thoihanno;
	}

	public void setThoihanno(String thoihanno) {
		this.thoihanno = thoihanno;
	}

	public String getIsDuocPhanBo() {
		return this.isDuocPhanBo;
	}

	public void setIsDuocPhanBo(String isDuocPhanBo) {
		this.isDuocPhanBo = isDuocPhanBo;
	}

	public String getKenhId() {
		return this.kenhId;
	}

	public void setKenhId(String kenhId) {
		this.kenhId = kenhId;
	}

	public void setKenhRs(ResultSet kenhRs) {
		this.kenhRs = kenhRs;
	}

	public ResultSet getKenhRs() {
		return this.kenhRs;
	}

	@Override
	public String getSohopdong() {
		return this.sohopdong;
	}

	@Override
	public void setSohopdong(String sohopdong) {
		this.sohopdong = sohopdong;
	}

	@Override
	public String getSoluong() {
		return this.soluong;
	}

	@Override
	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	@Override
	public String getTennhanhapkhau() {
		return this.tennhank;
	}

	@Override
	public void setTennhanhapkhau(String tennhank) {
		this.tennhank = tennhank;
	}

	@Override
	public String getTennhasanxuat() {
		return this.tennhasx;
	}

	@Override
	public void setTennhasanxuat(String tennhasx) {
		this.tennhasx = tennhasx;
	}

	@Override
	public String getNgayship() {
		return this.ngayship;
	}

	@Override
	public void setNgayship(String ngayship) {
		this.ngayship = ngayship;
	}

	@Override
	public String getNgaynhapkho() {
		return this.ngaynhapkho;
	}

	@Override
	public void setNgaynhapkho(String ngaynhapkho) {
		this.ngaynhapkho = ngaynhapkho;
	}

	@Override
	public String getDvChiuTrachNhiem() {
		return this.dvchiutrachnhiem;
	}

	@Override
	public void setDvChiuTrachNhiem(String dvchiutrachnhiem) {
		this.dvchiutrachnhiem = dvchiutrachnhiem;
	}

	@Override
	public ResultSet getThttRs() {
		return this.thttRs;
	}

	@Override
	public void setThttRs(ResultSet thttRs) {
		this.thttRs = thttRs;
	}

	@Override
	public String getSohoadon() {
		// TODO Auto-generated method stub
		return this.Sohoadon;
	}

	@Override
	public void setSohoadon(String Sohoadon) {
		// TODO Auto-generated method stub
		 this.Sohoadon=Sohoadon;
	}

	@Override
	public String getNgayhoadon() {
		// TODO Auto-generated method stub
		return this.Ngayhoadon;
	}

	@Override
	public void setNgayhoadon(String Ngayhoadon) {
		// TODO Auto-generated method stub
		this.Ngayhoadon=Ngayhoadon;
	}

	@Override
	public String getMauhoadon() {
		// TODO Auto-generated method stub
		return this.Mauhoadon;
	}

	@Override
	public void setMauhoadon(String Mauhoadon) {
		// TODO Auto-generated method stub
		 this.Mauhoadon=Mauhoadon;
	}

	@Override
	public String getKyhieu() {
		// TODO Auto-generated method stub
		return this.Kyhieu;
	}

	@Override
	public void setKyhieu(String Kyhieu) {
		// TODO Auto-generated method stub
		this.Kyhieu=Kyhieu;
	}

	@Override
	public String getMasothue() {
		// TODO Auto-generated method stub
		return this.Masothue;
	}

	@Override
	public void setMasothue(String Masothue) {
		// TODO Auto-generated method stub
		this.Masothue=Masothue; 
	}

	@Override
	public String getDiachiNcc() {
		// TODO Auto-generated method stub
		return this.Diachi;
	}

	@Override
	public void setDiachiNcc(String DiachiNcc) {
		// TODO Auto-generated method stub
		 this.Diachi=DiachiNcc;
	}

	@Override
	public void setNpp_duocchon_id(String npp_duocchon_id) {
		// TODO Auto-generated method stub
		this.npp_duocchon_id= npp_duocchon_id;
	}
	
	private void getNppInfo()
	{		
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		
		if(this.npp_duocchon_id.trim().length() <= 0)
		{
			this.npp_duocchon_id = util.getIdNhapp(this.userId);
		}
		 
	}

	@Override
	public void ChangeTienTe() {
		// TODO Auto-generated method stub
		try{
			
		}catch(Exception er){
			
		}
	}

	public String getNhaptaikho() {
		return Nhaptaikho;
	}

	public void setNhaptaikho(String nhaptaikho) {
		Nhaptaikho = nhaptaikho;
	}
	
	
}