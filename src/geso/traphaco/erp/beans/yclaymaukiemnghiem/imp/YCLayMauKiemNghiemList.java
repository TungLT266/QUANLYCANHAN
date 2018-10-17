package geso.traphaco.erp.beans.yclaymaukiemnghiem.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import geso.traphaco.erp.beans.yclaymaukiemnghiem.IYCLayMauKiemNghiem;
import geso.traphaco.erp.beans.yclaymaukiemnghiem.IYCLayMauKiemNghiemList;
import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.util.CapnhatKT;
import geso.traphaco.erp.util.Library;
import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;

public class YCLayMauKiemNghiemList extends Phan_Trang implements IYCLayMauKiemNghiemList{

	private static final long serialVersionUID = 1L;
	String userId;
	String msg;
	dbutils db;

	private String ctyId;
	private String nppId;

	private List<PhieuKiemDinh> dsPhieuKiemDinhs;
	private String mauKiemNghiemId;
	private String khoBietTruId;
	private String phongBanId;
	private String phieuNhanHangId;
	private String khoTonTruId;

	private String khoanMucId;
	private String khoXuatMauId;


	private ResultSet rsMauKiemNghiem;
	private ResultSet rsPhongBan;
	private ResultSet rsPhieuNhanHang;
	private ResultSet rsKhoBietTru;
	private ResultSet rsKhoTonTru;
	private ResultSet rsKhoXuatMau;
	private ResultSet rsPhieuKiemDinh;
	private ResultSet rsLoaiKiemDinh;
	private ResultSet rsKhoanMuc;

	private String sanphamId;
	private ResultSet rsSanPham;

	ResultSet rsYCKN;


	private int num;
	private int[] listPages;
	private int currentPages;

	String tungay,denngay;
	String sophieu,sophieukiemdinh,quytrinhmauso;

	private String loaiKiemDinh;

	public YCLayMauKiemNghiemList(){

		currentPages = 1;
		num = 1;

		this.userId="";
		this.msg="";
		this.db = new dbutils();
		this.ctyId = "";
		this.nppId = "";
		this.mauKiemNghiemId = "";
		this.khoBietTruId = "";
		this.phongBanId = "";
		this.phieuNhanHangId = "";
		this.khoTonTruId = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
		this.khoanMucId = "";
		this.khoXuatMauId = "";

		this.sophieu="";
		this.sophieukiemdinh="";
		this.quytrinhmauso="";
		this.loaiKiemDinh="";
		this.tungay="";
		this.denngay="";

		this.sanphamId="";
		this.dsPhieuKiemDinhs = new ArrayList<PhieuKiemDinh>();
	}


	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}


	public ResultSet getRsYCKN() {
		return rsYCKN;
	}

	public void setRsYCKN(ResultSet rsYCKN) {
		this.rsYCKN = rsYCKN;
	}

	public String getCtyId() {
		return ctyId;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
	}

	public String getNppId() {
		return nppId;
	}

	public void setNppId(String nppId) {
		this.nppId = nppId;
	}

	public List<PhieuKiemDinh> getDsPhieuKiemDinhs() {
		return dsPhieuKiemDinhs;
	}

	public void setDsPhieuKiemDinhs(List<PhieuKiemDinh> dsPhieuKiemDinhs) {
		this.dsPhieuKiemDinhs = dsPhieuKiemDinhs;
	}
	@Override
	public void DBclose() {
		// TODO Auto-generated method stub

	}

	////Chua lam---
	public boolean Chotphieu(String id){
	
		try 
		{

			String query="update  ERP_YCLAYMAUKIEMNGHIEM set trangthai=1 where pk_seq="+id;
			System.out.println("query chot "+query);
			if(db.updateReturnInt(query)!=1)
			{
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}

			//TRỪ KHO
			Utility util = new Utility();

			query = "  select b.ngaychungtu,b.khoxuatmau_fk,a.sanpham_fk,a.lohang,a.ngayhh, a.ngaysx,a.mame,a.mathung,bin.pk_seq as vitri,a.maphieu, "
					+ " a.phieudt,a.phieueo,a.marq,a.hamluong,a.hamam,'' as loaidoituong, '' as doituongid,a.soluong,a.soluonglaymau " + 
					"  from ERP_YCLAYMAUKIEMNGHIEM_chitiet a "+
					"  inner join ERP_YCLAYMAUKIEMNGHIEM b on a.YCLAYMAUKIEMNGHIEM_FK = b.PK_SEQ " + 
					"  left join erp_bin bin on a.vitri=bin.ma "+
					"  where b.PK_SEQ = '" + id + "' ";
					
			System.out.println("::: CAP NHAT KHO: " + query);
			ResultSet rs = db.get(query);
			/*if( rs != null )
		{*/
			while( rs.next() )
			{

				String khoId = rs.getString("khoxuatmau_fk");
				String spId = rs.getString("sanpham_fk");
				String solo = rs.getString("lohang");
				String ngayhethan = rs.getString("ngayhh");
				String ngaynhapkho = rs.getString("ngaysx");

				String mame=rs.getString("mame");
				String mathung=rs.getString("mathung");
				String vitri = rs.getString("vitri");
				String maphieu = rs.getString("maphieu");

				double soluong = rs.getDouble("soluong");

				String phieudt=rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				String MARQ = rs.getString("MARQ");
				String HAMLUONG=rs.getString("HAMLUONG");
				String HAMAM = rs.getString("HAMAM");
				String loaidoituong = rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId");

				double booked = rs.getDouble("soluonglaymau");

				msg = util.Update_KhoTT(rs.getString("ngaychungtu"), "Xuất kho" + id, db,  khoId, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, vitri, maphieu, phieudt, phieueo, MARQ, HAMLUONG,HAMAM,loaidoituong,doituongId,(-1)*soluong,(-1)*booked,(-1)*(soluong-booked));
				if( msg.trim().length() > 0 )
				{
					db.getConnection().rollback();
					db.shutDown();
					return false;
				}
			}
			rs.close();
			///-------------------------
			//Tăng kho nhận

			query = "  select b.ngaychungtu,b.kholuumau_fk,b.khoondinh_fk,a.sanpham_fk,a.lohang,a.ngayhh, a.ngaysx,a.mame,a.mathung,bin.pk_seq as vitri,rtrim(a.maphieu) as maphieu, "
					+ " a.phieudt,a.phieueo,a.marq,a.hamluong,a.hamam,'' as loaidoituong, '' as doituongid,"
					+ " a.soluong,isnull(a.soluongmauluu,0) as soluongmauluu, isnull(a.sloluongondinh,0) as soluongondinh " + 
					"  from ERP_YCLAYMAUKIEMNGHIEM_chitiet a "+
					"  inner join ERP_YCLAYMAUKIEMNGHIEM b on a.YCLAYMAUKIEMNGHIEM_FK = b.PK_SEQ " + 
					"  left join erp_bin bin on a.vitri=bin.ma "+
					"  where b.PK_SEQ = '" + id + "' " ;

			rs = db.get(query);
			/*if( rs != null )
			{*/
			while( rs.next() )
			{				
				String kholuumau_fk = rs.getString("kholuumau_fk");
				String khoondinh_fk = rs.getString("khoondinh_fk");

				String spId = rs.getString("sanpham_fk");
				String solo = rs.getString("lohang");
				String ngayhethan = rs.getString("ngayhh");
				String ngaynhapkho = rs.getString("ngaysx");

				String mame=rs.getString("mame");
				String mathung=rs.getString("mathung");
				String vitri = rs.getString("vitri");
				String maphieu = rs.getString("maphieu");

				double soluong = rs.getDouble("soluong");

				String phieudt=rs.getString("phieudt");
				String phieueo = rs.getString("phieueo");
				String MARQ = rs.getString("MARQ");
				String HAMLUONG=rs.getString("HAMLUONG");
				String HAMAM = rs.getString("HAMAM");
				String loaidoituong = rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongId");

				double soluongondinh = rs.getDouble("soluongondinh");
				double soluongmauluu = rs.getDouble("soluongmauluu");

				//soluongondinh
				msg = util.Update_KhoTT(rs.getString("ngaychungtu"), "Nhập kho " + id, db,  khoondinh_fk, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, vitri, maphieu.trim(), phieudt, phieueo, MARQ, HAMLUONG,HAMAM,loaidoituong,doituongId,soluong,0,soluong+soluongondinh);


				//soluongondinh
				msg = util.Update_KhoTT(rs.getString("ngaychungtu"), "Nhập kho " + id, db,  kholuumau_fk, spId, solo, ngayhethan, ngaynhapkho, 
						mame, mathung, vitri, maphieu.trim(), phieudt, phieueo, MARQ, HAMLUONG,HAMAM,loaidoituong,doituongId,soluong,0,soluong+soluongmauluu);

				if( msg.trim().length() > 0 )
				{
					db.getConnection().rollback();
					db.shutDown();
					return false;
				}
			}
			rs.close();


			///---------------------------------Dinhkhoankt
			NumberFormat formater = new DecimalFormat("#######");
			Library lib=new Library();
			query="select a.ngaychungtu,'Xuất lấy mẫu kiểm nghiệm' as loaichungtu,a.PK_SEQ,sp.ten as tenhang,\n "+ 
					"dbo.GET_TAIKHOANID(ncp.TAIKHOAN_FK) as taikhoanNo_km,dbo.GET_TAIKHOANID(lsp.taikhoankt_fk) as taikhoanCo_sp,b.soluonglaymau,\n "+ 
					" a.KHOANMUC_FK,a.SANPHAM_FK,b.lohang as solo ,b.ngayhh,a.KHOXUATMAU_FK,isnull(soctnhapmau,'') as soctnhapmau,diengiai,\n "+ 
					"from ERP_YCLAYMAUKIEMNGHIEM a\n "+ 
					"left join ERP_YCLAYMAUKIEMNGHIEM_chitiet b on a.pk_seq=b.yclaymaukiemnghiem_fk\n "+ 
					"left join ERP_NHOMCHIPHI ncp on a.khoanmuc_fk = ncp.pk_seq\n "+ 
					"left join erp_sanpham sp on a.sanpham_fk =sp.pk_seq\n "+ 
					"left join erp_loaisanpham lsp on sp.loaisanpham_fk=lsp.pk_Seq\n "+ 
					"where a.pk_seq="+id+" and congty_fk="+this.ctyId;
			
			System.out.println("dinhkhoan kt [ERP_YCLAYMAUKIEMNGHIEM] \n"+ query);
			rs=db.get(query);
			while(rs.next()){
				
			String ngaychungtu=rs.getString("ngaychungtu");
			String loaichungtu=rs.getString("loaichungtu");
			String sochungtu=rs.getString("pk_seq");
			String taikhoan_fk_no=rs.getString("taikhoanNo_km");
			String taikhoan_fk_co=rs.getString("taikhoanCo_sp");
			
			String sanpham_fk=rs.getString("SANPHAM_FK");
			String tensanpham_fk=rs.getString("tenhang");
			
			String[] thangnam = lib.getThangNam(db,false,ngaychungtu);
			String thangtruoc =  thangnam[0];
			String namtruoc =  thangnam[1];
			
			double soluonglaymau=rs.getDouble("soluonglaymau");		
			double dongia=lib.GetGia_ChayKT_Dauky(sanpham_fk, false, thangtruoc, namtruoc,ngaychungtu);
			System.out.println("dongi kt : "+dongia);
			
			String thanhtien_soluonglaymau = formater.format(dongia*soluonglaymau);
					
			String doituong_no="Khoản mục chi phí";
			String madoituong_no=rs.getString("KHOANMUC_FK");
			
			String doituong_co="Sản phẩm";
			String madoituong_co=rs.getString("SANPHAM_FK");
			
			String diengiai=rs.getString("diengiai");
			String machungtu=rs.getString("soctnhapmau");
			String solo=rs.getString("solo");
			String KHOXUATMAU_FK=rs.getString("KHOXUATMAU_FK");
			
			String thang=ngaychungtu.split("-")[1];
			String nam=ngaychungtu.split("-")[0];
			
			UtilityKeToan ukt = new UtilityKeToan("Xuất lấy mẫu kiểm nghiệm",sochungtu , ngaychungtu, ngaychungtu
					, taikhoan_fk_no, taikhoan_fk_co, thanhtien_soluonglaymau, thanhtien_soluonglaymau
					, "", "", thang, nam, doituong_no, madoituong_no, doituong_co, madoituong_co);

			ukt.setMaChungTuNoCo(machungtu);
			ukt.setDienGiaiNoCo(diengiai);

			ukt.setTongGiaTri(thanhtien_soluonglaymau);
			ukt.setTongGiaTriNT(thanhtien_soluonglaymau);


			this.msg = ukt.Update_TaiKhoanByTaiKhoan_FK(db);
			if(msg.length()>0){
				System.out.println("msg:[Update_TaiKhoanByTaiKhoan_FK] "+msg);
				db.getConnection().rollback();
				db.shutDown();
				return false;
			}
			
			}rs.close();
			
			db.getConnection().setAutoCommit(false);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}

			this.msg = "Khong the cap nhat ERP_HOADONNCC ABC: " + e.getMessage();
			return false;
		}
		return true;
	}

	public boolean Bochotphieu(String id){
		dbutils db =new dbutils();
		try 
		{

			String query="update  ERP_YCLAYMAUKIEMNGHIEM set trangthai=0 where pk_seq="+id;
			System.out.println("query bo chot "+query);
			if(db.updateReturnInt(query)!=1)
			{
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().setAutoCommit(false);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}

			this.msg = "Khong the cap nhat ERP_HOADONNCC ABC: " + e.getMessage();
			return false;
		}
		return true;
	}

	////Chua lam---
	public boolean Xoaphieu(String id){
		dbutils db =new dbutils();
		try 
		{

			String query="update  ERP_YCLAYMAUKIEMNGHIEM set trangthai=2 where pk_seq="+id;
			System.out.println("query delete "+query);
			if(db.updateReturnInt(query)!=1)
			{
				System.out.println(this.msg);
				db.getConnection().rollback();
				return false;
			}
			db.getConnection().setAutoCommit(false);
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}

			this.msg = "Khong the cap nhat ERP_HOADONNCC ABC: " + e.getMessage();
			return false;
		}
		return true;
	}

	public void init(String search ) {
		createRs();
		String query = "select a.PK_SEQ soId, a.YCKIEMDINH_FK sophieukiemdinh, a.NGAYCHUNGTU, a.LOAIKIEMDINH, loai.ten as tenloaikiemdinh,b.TENSP, isnull(a.QUYTRINHLAYMAU,'') as QUYTRINHLAYMAU, a.TRANGTHAI  \n "+ 
				" from ERP_YCLAYMAUKIEMNGHIEM a inner join ERP_YCLAYMAUKIEMNGHIEM_CHITIET b on a.PK_SEQ=b.YCLAYMAUKIEMNGHIEM_FK  \n "+ 
				" inner join ( \n "+ 
				" 	SELECT 1 AS PK_SEQ , N'TỰ TẠO (MẶC ĐỊNH)' AS TEN \n "+ 
				" 	UNION ALL \n "+ 
				" 	SELECT 2 AS PK_SEQ , N'KIỂM ĐỊNH MUA HÀNG' AS TEN \n "+ 
				" 	UNION ALL \n "+ 
				" 	SELECT 3 AS PK_SEQ , N'KIỂM ĐỊNH SẢN XUẤT' AS TEN \n "+ 
				" ) LOAI ON LOAI.PK_SEQ = a.LOAIKIEMDINH"
				+ " where 1=1 \n";


		if(this.tungay.length()>0)
			query+=" and  a.ngaychungtu>='"+this.tungay+"'";
		if(this.denngay.length()>0)
			query+=" and  a.ngaychungtu<='"+this.denngay+"'";

		if(this.loaiKiemDinh.length()>0)
			query+=" and   a.LOAIKIEMDINH='"+this.loaiKiemDinh+"'";
		if(this.mauKiemNghiemId.length()>0)
			query+=" and  a.maukiemnghiem_fk='"+this.mauKiemNghiemId+"'";

		if(this.sophieu.length()>0)
			query+=" and   a.pk_seq='"+this.sophieu.trim()+"'";
		if(this.sophieukiemdinh.length()>0)
			query+=" and  a.YCKIEMDINH_FK='"+this.sophieukiemdinh.trim()+"'";

		if(this.sanphamId.length()>0)
			query+=" and   b.sanpham_fk='"+this.sanphamId+"'";
		if(this.quytrinhmauso.length()>0)
			query+=" and  a.QUYTRINHLAYMAU like N'%"+this.quytrinhmauso.trim()+"%'";
		query+="  group by a.PK_SEQ, a.YCKIEMDINH_FK , a.NGAYCHUNGTU, a.LOAIKIEMDINH, loai.ten,b.TENSP, a.QUYTRINHLAYMAU, a.TRANGTHAI "
				+ " order by  a.TRANGTHAI asc,a.ngaychungtu desc ,a.PK_SEQ desc ";

		//query = createSplittingData_ListNew(this.db, 30, 10, "soId asc ", query);
		System.out.println("init()list  sp______" + query);
		ResultSet rs = db.get(query);
		PhieuKiemDinh phieuKiemDinh = null;
		try {
			while(rs.next()){
				phieuKiemDinh = new PhieuKiemDinh();
				phieuKiemDinh.setId(rs.getString("soId"));
				phieuKiemDinh.setTenSp(rs.getString("TENSP"));
				phieuKiemDinh.setSophieukiemdinh(rs.getString("sophieukiemdinh"));
				phieuKiemDinh.setNgaychungtu(rs.getString("NGAYCHUNGTU"));
				phieuKiemDinh.setQuytrinhlaymau(rs.getString("QUYTRINHLAYMAU"));
				phieuKiemDinh.setLoaikiemdinh(rs.getString("tenloaikiemdinh"));
				phieuKiemDinh.setTrangthai(rs.getString("TRANGTHAI"));

				getDsPhieuKiemDinhs().add(phieuKiemDinh);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public String getKhoanMucId() {
		return khoanMucId;
	}

	public void setKhoanMucId(String khoanMucId) {
		this.khoanMucId = khoanMucId;
	}

	public ResultSet getRsKhoanMuc() {
		return rsKhoanMuc;
	}

	public void setRsKhoanMuc(ResultSet rsKhoanMuc) {
		this.rsKhoanMuc = rsKhoanMuc;
	}



	public ResultSet getRsLoaiKiemDinh() {
		return rsLoaiKiemDinh;
	}

	public void setRsLoaiKiemDinh(ResultSet rsLoaiKiemDinh) {
		this.rsLoaiKiemDinh = rsLoaiKiemDinh;
	}

	public ResultSet getRsPhieuKiemDinh() {
		return rsPhieuKiemDinh;
	}

	public void setRsPhieuKiemDinh(ResultSet rsPhieuKiemDinh) {
		this.rsPhieuKiemDinh = rsPhieuKiemDinh;
	}



	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}



	public String getMauKiemNghiemId() {
		return mauKiemNghiemId;
	}

	public void setMauKiemNghiemId(String mauKiemNghiemId) {
		this.mauKiemNghiemId = mauKiemNghiemId;
	}



	public String getKhoBietTruId() {
		return khoBietTruId;
	}

	public void setKhoBietTruId(String khoBietTruId) {
		this.khoBietTruId = khoBietTruId;
	}



	public String getPhongBanId() {
		return phongBanId;
	}

	public void setPhongBanId(String phongBanId) {
		this.phongBanId = phongBanId;
	}



	public String getPhieuNhanHangId() {
		return phieuNhanHangId;
	}

	public void setPhieuNhanHangId(String phieuNhanHangId) {
		this.phieuNhanHangId = phieuNhanHangId;
	}




	public String getKhoTonTruId() {
		return khoTonTruId;
	}

	public void setKhoTonTruId(String khoTonTruId) {
		this.khoTonTruId = khoTonTruId;
	}

	public ResultSet getRsMauKiemNghiem() {
		return rsMauKiemNghiem;
	}

	public void setRsMauKiemNghiem(ResultSet rsMauKiemNghiem) {
		this.rsMauKiemNghiem = rsMauKiemNghiem;
	}

	public ResultSet getRsPhongBan() {
		return rsPhongBan;
	}

	public void setRsPhongBan(ResultSet rsPhongBan) {
		this.rsPhongBan = rsPhongBan;
	}

	public ResultSet getRsPhieuNhanHang() {
		return rsPhieuNhanHang;
	}

	public void setRsPhieuNhanHang(ResultSet rsPhieuNhanHang) {
		this.rsPhieuNhanHang = rsPhieuNhanHang;
	}

	public ResultSet getRsKhoBietTru() {
		return rsKhoBietTru;
	}

	public void setRsKhoBietTru(ResultSet rsKhoBietTru) {
		this.rsKhoBietTru = rsKhoBietTru;
	}

	public ResultSet getRsKhoTonTru() {
		return rsKhoTonTru;
	}

	public void setRsKhoTonTru(ResultSet rsKhoTonTru) {
		this.rsKhoTonTru = rsKhoTonTru;
	}

	public String getKhoXuatMauId() {
		return khoXuatMauId;
	}

	public void setKhoXuatMauId(String khoXuatMauId) {
		this.khoXuatMauId = khoXuatMauId;
	}

	public ResultSet getRsKhoXuatMau() {
		return rsKhoXuatMau;
	}

	public void setRsKhoXuatMau(ResultSet rsKhoXuatMau) {
		this.rsKhoXuatMau = rsKhoXuatMau;
	}

	public String getSanphamId() {
		return sanphamId;
	}


	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}


	public ResultSet getRsSanPham() {
		return rsSanPham;
	}


	public void setRsSanPham(ResultSet rsSanPham) {
		this.rsSanPham = rsSanPham;
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
		ResultSet rs = db.get("select count(*) as c from ERP_THANHTOANHOADON");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public String getTungay() {
		return tungay;
	}


	public void setTungay(String tungay) {
		this.tungay = tungay;
	}


	public String getDenngay() {
		return denngay;
	}


	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	public String getLoaiKiemDinh() {
		return loaiKiemDinh;
	}

	public void setLoaiKiemDinh(String loaiKiemDinh) {
		this.loaiKiemDinh = loaiKiemDinh;
	}
	public String getSophieu() {
		return sophieu;
	}


	public void setSophieu(String sophieu) {
		this.sophieu = sophieu;
	}


	public String getSophieukiemdinh() {
		return sophieukiemdinh;
	}


	public void setSophieukiemdinh(String sophieukiemdinh) {
		this.sophieukiemdinh = sophieukiemdinh;
	}


	public String getQuytrinhmauso() {
		return quytrinhmauso;
	}


	public void setQuytrinhmauso(String quytrinhmauso) {
		this.quytrinhmauso = quytrinhmauso;
	}
	public void createRs(){
		String query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_LOAIMAUKIEMNGHIEM WHERE TRANGTHAI=1";
		System.out.println("rsMauKiemNghiem "+query);
		this.rsMauKiemNghiem = db.get(query);

		query = " SELECT 1 AS PK_SEQ , N'TỰ TẠO (MẶC ĐỊNH)' AS TEN\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT 2 AS PK_SEQ , N'KIỂM ĐỊNH MUA HÀNG' AS TEN\r\n" + 
				"UNION ALL\r\n" + 
				"SELECT 3 AS PK_SEQ , N'KIỂM ĐỊNH SẢN XUẤT' AS TEN\r\n" + 
				" ";
		System.out.println("rsLoaiKiemDinh "+query);
		this.rsLoaiKiemDinh = db.get(query);

		query = "select PK_SEQ, MA + ' - ' + TEN AS TEN from ERP_SANPHAM SP WHERE TRANGTHAI=1";	
		System.out.println("rsSanPham "+query);
		this.rsSanPham = db.get(query);



	}



}
