package geso.traphaco.erp.beans.nhanhang.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.nhanhang.IErpNhanhang_Giay;
import geso.traphaco.erp.beans.nhanhang.ISanpham;
import geso.traphaco.erp.beans.nhanhang.ISpDetail;

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
	ResultSet hoadonNCCList;

	ResultSet khachhangRs;
	String khachhangId;

	String loaikho = "";

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
	public String getNgayhoadon(){
		return this.ngayhoadon;
	}
	public void setNgayhoadon(String ngayhoadon){
		this.ngayhoadon = ngayhoadon;
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

	public void createRs() {

		//String sql = "select pk_seq, ten from ERP_DONVITHUCHIEN where trangthai = '1'  ";
		// " and congty_fk = '" + this.congtyId + "' and pk_seq in " +
		// this.util.quyen_donvithuchien(this.userId);;
		
		
		String sql=" select distinct A.pk_seq AS PK_SEQ, A.ten AS TEN   "+    
		 "\n  from ERP_DONVITHUCHIEN  A inner join NHANVIEN NV ON NV.PHONGBAN_FK=A.PK_SEQ  "+    
		 "\n  where A.trangthai = '1' and "+
		 "\n  NV.PHONGBAN_FK in ( select  distinct PHONGBAN_FK from NHANVIEN where PK_SEQ ="+this.userId+" )";
		System.out.println(" don vi thuc hien: "+ sql);
		this.dvthRs = db.get(sql);

		this.ndnRs = db.get("select pk_seq, noidung from ERP_NOIDUNGNHANHANG where trangthai = 1 ");

		this.ldnRs = db.get("select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('NK') and pk_seq not in (100004,100005) ");

		String cd ="";
		if (this.trangthai.equals("1") || this.trangthai.equals("2")) {
			
			cd = "select PK_SEQ, ma + ', ' + TEN as ten  from ERP_NHACUNGCAP where trangthai = 1 and pk_seq = '"+ this.nccId + "'  and duyet = '1'";
			System.out.println("NCCRS "+cd);
			this.nccRs = db	.getScrol(cd);
		} else {
			if (this.loaimh.equals("0")) {
				cd = "select PK_SEQ, ma + ', ' + TEN as ten  from ERP_NHACUNGCAP where trangthai = 1 and duyet = '1' and loaincc = '100002' and congty_fk = "+ this.congtyId + " ";
				System.out.println("NCCRS "+cd);
				this.nccRs = db	.getScrol(cd);
			} else {
				
				cd = "select PK_SEQ, ma + ', ' + TEN as ten  from ERP_NHACUNGCAP where trangthai = 1 and duyet = '1' and congty_fk = "+ this.congtyId + " ";
				System.out.println("NCCRS "+cd);
				this.nccRs = db	.getScrol(cd); // pk_seq in " +
														// this.util.quyen_nhacungcap(this.userId)
														// + "
			}
		}

		System.out.println("NPP:"+cd);
		// Cho chọn kho nhận: không lấy Kho NCC & Kho ký gửi NCC & Kho TDV
		
		
		this.RsKhoNhan = db.get("select PK_SEQ, TEN from ERP_KHOTT where TRANGTHAI = '1' and PK_SEQ in " +util.quyen_kho(this.userId));

		// Kiểm tra kho nhận có phải Kho ký gửi KH/Dự trữ KH không
		if (this.KhoNhanId.trim().length() > 0) {
			String command = "SELECT ISNULL(LOAI,0) LOAI FROM KHO WHERE PK_SEQ = " + this.KhoNhanId + " ";
			ResultSet rss = db.get(command);
			try {
				if (rss.next()) {
					this.loaikho = rss.getString("LOAI");
				}
				rss.close();
			} catch (Exception e) {
			}

		}

		this.khachhangRs = db.get("select PK_SEQ, MA + ', ' +TEN AS TEN from KHACHHANG where TRANGTHAI = '1' AND CONGTY_FK = " + this.congtyId + " ");

		/*// Check ngày nhận hàng
		if (this.ngaynhanhang.trim().length() > 0) {
			String nppId = util.getIdNhapp(this.userId);
			String query = "select top(1) NAM , THANGKS  from KHOASOTHANG where NPP_FK = '"+nppId+"' order by NAM desc, THANGKS desc ";
			String thangKS = "";
			String namKS = "";
			System.out.println("Câu check ngay nhan " + query);
			ResultSet rsCheck = db.get(query);
			if (rsCheck != null) {
				try {
					if (rsCheck.next()) {
						thangKS = rsCheck.getString("THANGKS");
						namKS = rsCheck.getString("NAM");
					}
					rsCheck.close();
				} catch (Exception e) {
				}
			}

			if (thangKS.equals("12")) {
				if (Integer.parseInt(this.ngaynhanhang.substring(0, 4)) > Integer.parseInt(namKS))
					this.is_saungayKS = 1;
				else
					this.is_saungayKS = 0;
			} else {
				if (this.ngaynhanhang.substring(0, 4).equals(namKS)
						&& Integer.parseInt(this.ngaynhanhang.substring(5, 7)) > Integer.parseInt(thangKS))
					this.is_saungayKS = 1;
				else
					this.is_saungayKS = 0;
			}
		}*/
		if (this.nccId.trim().length() > 0) {
//			String query = "";

			String query1 = "";

			// ---lấy danh sách số hoá đơn ncc
			
			if(this.loaimh.equals("0")){
				query1 =  " select distinct hd.pk_seq, (hd.sohoadon) sohoadon \n"
						+ " from ERP_PARK p       \n" 
						+ " inner join ERP_HOADONNCC hd on p.pk_seq=hd.park_fk     \n"
						+ " inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK  \n"
						+ " where hd.LOAIHD = "
						+ this.loaimh + " AND p.trangthai in ( '1', '2' )   and p.ncc_fk=" + this.nccId + " ";
			}else{
				query1 =  " select distinct hd.pk_seq, (hd.sohoadon) + N' -- SỐ MUA HÀNG NỘI ĐỊA: ' + cast(mh.PK_SEQ as nvarchar(50)) sohoadon \n"
						
						+ " from ERP_PARK p       \n" 
						+ " inner join ERP_HOADONNCC hd on p.pk_seq=hd.park_fk     \n"
						+ " inner join ERP_HOADONNCC_DONMUAHANG hd_dmh on hd.pk_seq= hd_dmh.HOADONNCC_FK  \n"
						+ " inner join ERP_MUAHANG mh on hd_dmh.MUAHANG_FK= mh.PK_SEQ  \n" 
						+ " where hd.LOAIHD = " + this.loaimh + " AND p.trangthai in ( '1', '2' ) \n"
						+ " and p.ncc_fk=" + this.nccId + " and mh.loaihanghoa_fk = " + this.loaihanghoa;
			}
			System.out.println(" ds hoa don :" + query1);

			this.hdNccRs = db.get(query1);

			if (this.id.trim().length() <= 0) {
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
						query1 =  " select isnull(p.tigia, 1) as tigia,  mh.PK_SEQ as mhId, " 
								+ " mh.PK_SEQ as sopo, a.pk_seq as hdncc  \n"
								+ " from ERP_PARK p   \n" 
								+ " inner join ERP_HOADONNCC a on p.pk_seq=a.park_fk  \n"
								+ " inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK  \n"
								+ " inner join ERP_MUAHANG mh on b.MUAHANG_FK= mh.PK_SEQ \n" 
								+ " where a.pk_seq='"	+ this.hdNccId.trim() + "'   ";
					}else{
						query1 =  " select isnull(p.tigia, 1) as tigia,  mh.PK_SEQ as mhId, mh.PK_SEQ as sopo, a.pk_seq as hdncc \n"
							+ " from ERP_PARK p   \n" 
							+ " inner join ERP_HOADONNCC a on p.pk_seq=a.park_fk  \n"
							+ " inner join ERP_HOADONNCC_DONMUAHANG b on a.pk_seq = b.HOADONNCC_FK  \n"
							+ " inner join ERP_MUAHANG mh on b.MUAHANG_FK= mh.PK_SEQ \n" + " where a.pk_seq='"
							//+ " inner join ERP_MUAHANG mh on b.MUAHANG_FK= mh.PK_SEQ \n" + " where a.pk_seq='"
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
					if(this.loaimh.equals("0")){ // NHẬP KHẨU
						query1 = " SELECT distinct mh.PK_SEQ, hdsp.MUAHANG_FK MUAHANG_FK, mh.SOPO SOPO,  \n"
								+ "   sp.PK_SEQ as SANPHAM_FK, \n"
								+ "   sp.MA as SPMA , \n"
								+ "   sp.TEN as spten,   \n"
								+ "   isnull(hdsp.DVT,'NA') as dvdlTen, 0 as dungsai , isnull(p.TIGIA,1)as tigiaquydoi, isnull(p.tiente_fk, 100000) as tienteId,  isnull(sp.hansudung,0) as hansudung , \n"
								+ "   mhsp.DONGIA, (mhsp.DONGIA)*isnull(p.TIGIA,1) as dongiaviet,               \n"
								+ "   isnull(hdsp.NGAYNHANDK,'') ngaynhan, sum(hdsp.SOLUONG) as soluonghd,      \n"
								+ "   isnull((select SUM(nhsp.SOLUONGNHAN)       \n"
								+ "   from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK \n"
								+ "   where nh.TRANGTHAI not in (3,4) and nh.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
								+ "        and nhsp.SANPHAM_FK=hdsp.sanpham_fk \n"
								+ "   ),0) as soluongdanhan, '" + this.KhoNhanId
								+ "' khonhan,  (select k.ten from KHO k where pk_seq = '" + this.KhoNhanId
								+ "'  ) khoten \n" + " FROM ERP_HOADONNCC_DONMUAHANG hdsp   \n"
								+ " inner join ERP_HOADONNCC hd on hd.pk_seq= hdsp.HOADONNCC_FK \n"
								/*+ " inner join ERP_NHAPKHONHAPKHAU nk on hdsp.MUAHANG_FK= nk.PK_SEQ     \n"*/
								+ " inner join ERP_MUAHANG mh on hdsp.MUAHANG_FK= mh.pk_seq     \n"
								+ " inner join ERP_MUAHANG_SP mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK and hdsp.SANPHAM_FK = mhsp.SANPHAM_FK \n"
								+ " inner join ERP_PARK p on hd.park_fk= p.pk_seq      	\n"
								+ " left join ERP_SANPHAM  sp on hdsp.SANPHAM_FK= sp.PK_SEQ    \n"
								+ " WHERE hd.pk_seq = " + this.hdNccId + " \n"
								+ " group by hdsp.MUAHANG_FK, hdsp.MUAHANG_FK, mh.PK_SEQ, mh.LOAIHANGHOA_FK, isnull(hdsp.DVT,'NA'), isnull(p.TIGIA,1), isnull(p.tiente_fk, 100000), \n"
								+ " isnull(sp.hansudung,0), isnull(hdsp.NGAYNHANDK,''), hd.pk_seq, hdsp.SANPHAM_FK, sp.PK_SEQ, \n"
								+ " sp.ma, sp.ten, mhsp.DONGIA, mh.SOPO \n"
								+ " having \n" 
								+ "  sum(hdsp.SOLUONG)  - \n"
								+ "  isnull((select SUM(nhsp.SOLUONGNHAN)   \n"
								+ "     from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK  \n"
								+ "     where nh.TRANGTHAI not in (3,4) and nh.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
								+ "          and nhsp.SANPHAM_FK=hdsp.sanpham_fk \n"
								+ "   ),0) >= 0 ";
					}else if(this.loaimh.equals("1")){
						query1 = " SELECT distinct hdsp.MUAHANG_FK, mh.PK_SEQ SOPO,  \n"
							+ "   case mh.LOAIHANGHOA_FK  when 0 then sp.PK_SEQ when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as SANPHAM_FK, \n"
						    + "   case mh.LOAIHANGHOA_FK  when 0 then sp.MA when 1 then tscd.ma when 3 then ccdc.MA else ncp.TEN end  as SPMA , \n"
							+ "   case mh.LOAIHANGHOA_FK  when 0 then sp.TEN when 1 then tscd.ten when 3 then ccdc.DIENGIAI else ncp.DIENGIAI end as spten,   \n"
							+ "   isnull(hdsp.DVT,'NA') as dvdlTen, 0 as dungsai , isnull(p.TIGIA,1)as tigiaquydoi, isnull(p.tiente_fk, 100000) as tienteId,  isnull(sp.hansudung,0) as hansudung , \n"
							+ "   mhsp.DONGIA, (mhsp.DONGIA)*isnull(p.TIGIA,1) as dongiaviet,               \n"
							+ "   isnull(hdsp.NGAYNHANDK,'') ngaynhan, sum(hdsp.SOLUONG) as soluonghd,      \n"
							+ "   isnull((select SUM(nhsp.SOLUONGNHAN)       \n"
							+ "   from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK \n"
							+ "   where nh.TRANGTHAI not in (3,4) and nh.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
							+ "        and nhsp.SANPHAM_FK=hdsp.sanpham_fk \n"
							+ "   ),0) as soluongdanhan, '" + this.KhoNhanId
							+ "' khonhan,  (select k.ten from KHO k where pk_seq = '" + this.KhoNhanId
							+ "'  ) khoten \n" + " FROM ERP_HOADONNCC_DONMUAHANG hdsp   \n"
							+ " inner join ERP_HOADONNCC hd on hd.pk_seq= hdsp.HOADONNCC_FK \n"
							//+ " inner join ERP_NHAPKHONHAPKHAU mh on hdsp.MUAHANG_FK= mh.pk_seq     \n"
							+ " inner join ERP_MUAHANG mh on hdsp.MUAHANG_FK= mh.pk_seq     \n"
							+ " inner join ERP_MUAHANG_SP mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK and hdsp.SANPHAM_FK = mhsp.SANPHAM_FK"
							+ " inner join ERP_PARK p on hd.park_fk= p.pk_seq      	\n"
							+ " left join ERP_SANPHAM  sp on hdsp.SANPHAM_FK= sp.PK_SEQ    \n"
							+ " left join ERP_MASCLON tscd on hdsp.taisan_fk = tscd.pk_seq    \n"
						    + " left join erp_congcudungcu ccdc on hdsp.ccdc_fk = ccdc.pk_seq    \n"
						    + " left join erp_nhomchiphi ncp on hdsp.chiphi_fk = ncp.pk_seq   	\n"
							+ " WHERE hd.pk_seq = " + this.hdNccId + " \n"
							+ " group by hdsp.MUAHANG_FK, mh.PK_SEQ, mh.LOAIHANGHOA_FK, isnull(hdsp.DVT,'NA'), isnull(p.TIGIA,1), isnull(p.tiente_fk, 100000), \n"
							+ " isnull(sp.hansudung,0), isnull(hdsp.NGAYNHANDK,''), hd.pk_seq, hdsp.SANPHAM_FK, sp.PK_SEQ, ccdc.pk_seq, tscd.PK_SEQ, ncp.pk_seq, \n"
							+ " sp.ma, ccdc.ma, tscd.ma, ncp.ten, sp.ten, ccdc.DIENGIAI, tscd.ten, ncp.DIENGIAI, mhsp.DONGIA \n"
							+ " having \n"
							+ "  sum(hdsp.SOLUONG) - \n"
							+ "  isnull((select SUM(nhsp.SOLUONGNHAN)   \n"
							+ "     from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK  \n"
							+ "     where nh.TRANGTHAI not in (3,4) and nh.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
							+ "          and nhsp.SANPHAM_FK=hdsp.sanpham_fk \n"
							+ "   ),0)  >= 0 ";					
					}
					else {
						query1 = " SELECT distinct hdsp.MUAHANG_FK, mh.PK_SEQ SOPO,  \n"
							+ "   case mh.LOAIHANGHOA_FK  when 0 then sp.PK_SEQ when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as SANPHAM_FK, \n"
						    + "   case mh.LOAIHANGHOA_FK  when 0 then sp.MA when 1 then tscd.ma when 3 then ccdc.MA else ncp.TEN end  as SPMA , \n"
							+ "   case mh.LOAIHANGHOA_FK  when 0 then sp.TEN when 1 then tscd.ten when 3 then ccdc.DIENGIAI else ncp.DIENGIAI end as spten,   \n"
							+ "   isnull(hdsp.DVT,'NA') as dvdlTen, 0 as dungsai , isnull(p.TIGIA,1)as tigiaquydoi, isnull(p.tiente_fk, 100000) as tienteId,  isnull(sp.hansudung,0) as hansudung , \n"
							+ "   hdsp.DONGIA, (hdsp.DONGIA)*isnull(p.TIGIA,1) as dongiaviet,               \n"
							+ "   isnull(hdsp.NGAYNHANDK,'') ngaynhan, sum(hdsp.SOLUONG) as soluonghd,      \n"
							+ "   isnull((select SUM(nhsp.SOLUONGNHAN)       \n"
							+ "   from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK \n"
							+ "   where nh.TRANGTHAI not in (3,4) and nh.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
							+ "        and nhsp.SANPHAM_FK=hdsp.sanpham_fk \n"
							+ "   ),0) as soluongdanhan, '" + this.KhoNhanId
							+ "' khonhan " ;
						
							if(this.KhoNhanId.length()>0){
								query1 +=" \n , (select k.ten from ERP_KHOTT k where pk_seq = '" + this.KhoNhanId
								+ "'  ) khoten \n" ; 
							}
							else
							{
								query1 +=" \n , ''  as  khoten \n" ; 
							}
							
							query1 +=	" FROM ERP_HOADONNCC_DONMUAHANG hdsp   \n"
							+ " inner join ERP_HOADONNCC hd on hd.pk_seq= hdsp.HOADONNCC_FK \n"
							//+ " inner join ERP_NHAPKHONHAPKHAU mh on hdsp.MUAHANG_FK= mh.pk_seq     \n"
							+ " inner join ERP_MUAHANG mh on hdsp.MUAHANG_FK= mh.pk_seq     \n"
							+ " inner join ERP_PARK p on hd.park_fk= p.pk_seq      	\n"
							+ " left join ERP_SANPHAM  sp on hdsp.SANPHAM_FK= sp.PK_SEQ    \n"
							+ " left join erp_masclon tscd on hdsp.taisan_fk = tscd.pk_seq    \n"
						    + " left join erp_congcudungcu ccdc on hdsp.ccdc_fk = ccdc.pk_seq    \n"
						    + " left join erp_nhomchiphi ncp on hdsp.chiphi_fk = ncp.pk_seq   	\n"
							+ " WHERE hd.pk_seq = " + this.hdNccId + " \n"
							+ " group by hdsp.MUAHANG_FK, mh.PK_SEQ, mh.LOAIHANGHOA_FK, isnull(hdsp.DVT,'NA'), isnull(p.TIGIA,1), isnull(p.tiente_fk, 100000), \n"
							+ " isnull(sp.hansudung,0), isnull(hdsp.NGAYNHANDK,''), hd.pk_seq, hdsp.SANPHAM_FK, sp.PK_SEQ, ccdc.pk_seq, tscd.PK_SEQ, ncp.pk_seq, \n"
							+ " sp.ma, ccdc.ma, tscd.ma, ncp.ten, sp.ten, ccdc.DIENGIAI, tscd.ten, ncp.DIENGIAI, hdsp.DONGIA \n"
							+ " having \n"
							+ "  sum(hdsp.SOLUONG) - \n"
							+ "  isnull((select SUM(nhsp.SOLUONGNHAN)   \n"
							+ "     from ERP_NHANHANG nh inner join ERP_NHANHANG_SANPHAM nhsp on nh.PK_SEQ=nhsp.NHANHANG_FK  \n"
							+ "     where nh.TRANGTHAI not in (3,4) and nh.MUAHANG_FK= mh.pk_seq and nh.hdncc_fk=hd.pk_seq  \n"
							+ "          and nhsp.SANPHAM_FK=hdsp.sanpham_fk \n"
							+ "   ),0)  >= 0 ";					
					}
					System.out.println("ds sanpham :" + query1);

					ResultSet rs = db.get(query1);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					NumberFormat formatter = new DecimalFormat("#,###,###.###");
					NumberFormat formatter2 = new DecimalFormat("#,###,###");
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
								sp.setSoluongdat(formatter.format(rs.getDouble("soluonghd")));
								sp.setKhonhanId(rs.getString("khonhan"));
								System.out.println(" \n \n khonhan ID " + sp.getKhonhanId());
								sp.setDvdl(rs.getString("dvdlTen"));
								sp.setSoluongMaxNhan(formatter2.format(rs.getDouble("soluonghd") - rs.getDouble("soluongdanhan")));
								sp.setSoluongDaNhan(formatter2.format(rs.getDouble("soluongdanhan")));
								sp.setHansudung(rs.getString("hansudung"));
								sp.setNgaynhandukien(rs.getString("ngaynhan")==null?"":rs.getString("ngaynhan"));
								sp.setKhonhanTen(rs.getString("khoten"));
								sp.setDongia(formatter.format(rs.getDouble("dongia")));
								sp.setDongiaViet(formatter.format(rs.getDouble("dongiaviet")));
								sp.setTigiaquydoi(rs.getString("tigiaquydoi"));
								sp.setTiente(rs.getString("tienteId"));
								sp.setSoluongnhan("");
								sp.setthanhtien("");
								if(this.id.trim().length() <= 0)
								{
									if(this.loaimh.equals("0") || this.loaimh.equals("1")){
										sp.setSoluongnhan(
												formatter2.format(rs.getDouble("soluonghd") - rs.getDouble("soluongdanhan")));
										String command = "";
										if(this.loaimh.equals("1"))
										{
											
											command = "select d.*, d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ "
												+ "where nh.trangthai <> 3 and nh.hdNCC_fk = d.HOADONNCC_FK and ct.SANPHAM_FK = a.SANPHAM_FK and ct.SOLO = a.SOLO "
												+ "group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, g.NGAYSANXUAT, g.NGAYHETHAN, d.dongia "
												+ "from erp_phanbomuahang_sp_chitiet a inner join erp_phanbomuahang_po b on a.phanbo_fk = b.phanbo_fk "
												+ "inner join ERP_MUAHANG c on b.poduocpb = c.PK_SEQ and c.congty_fk = a.congty_fk "
												+ "inner join ERP_HOADONNCC_DONMUAHANG d on c.PK_SEQ = d.muahang_fk  and a.SANPHAM_FK = d.SANPHAM_FK and d.SOLO = a.SOLO and d.MUAHANG_FK = b.PODUOCPB "
												+ "inner join ERP_PHANBOMUAHANG e on b.PHANBO_FK = e.pk_seq "
												+ "inner join ERP_NHAPKHONHAMAY f on f.muahang_fk = e.MUAHANG_FK inner join ERP_NHAPKHONHAMAY_SP_CHITIET g on g.nhamay_fk = f.PK_SEQ "
												+ "and g.SOLO = a.SOLO and g.SANPHAM_FK = a.SANPHAM_FK "
												+ "where d.hoadonncc_fk = "+this.hdNccId + " and a.sanpham_fk = "+sp.getId()+" and d.soluong <> 0 and d.MUAHANG_FK = "+sp.getMuahang_fk()+"";
											
											command = " select SANPHAM_FK, SOLUONG,SOLO, NGAYSANXUAT, NGAYHETHAN, DONGIA, " +
											  " d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct " +
											  " inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ " +
											  " where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO  " +
											  " and nh.hdNCC_fk = d.HOADONNCC_FK group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, " +
											  " d.NGAYSANXUAT as ngaysanxuat, d.NGAYHETHAN " +
											  " from  ERP_HOADONNCC_DONMUAHANG d  " +
											  " where  d.hoadonncc_fk = "+this.hdNccId+" and d.sanpham_fk = "+sp.getId()+
											  "and d.soluong <> 0 and d.MUAHANG_FK = "+ sp.getMuahang_fk() ;
										}
										else 
										{
											
											command = "select d.*, d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ "
												+ "where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO and nh.hdNCC_fk = d.HOADONNCC_FK "
												+ "group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, a.NGAYSX as ngaysanxuat, a.NGAYHETHAN, d.dongia "
												+ "from ERP_NHAPKHONHAPKHAU_SP_CHITIET a "
												+ "inner join ERP_HOADONNCC_DONMUAHANG d on a.NHAPKHO_FK = d.muahang_fk and a.SANPHAM_FK = d.SANPHAM_FK and d.SOLO = a.SOLO "
												+ "and d.hoadonncc_fk = "+this.hdNccId + " and a.sanpham_fk = "+sp.getId()+" and d.soluong <> 0 and d.MUAHANG_FK = "+sp.getMuahang_fk()+"";
											
											command = " select SANPHAM_FK, SOLUONG,SOLO, NGAYSANXUAT, NGAYHETHAN, DONGIA, " +
													  " d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct " +
													  " inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ " +
													  " where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO  " +
													  " and nh.hdNCC_fk = d.HOADONNCC_FK group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, " +
													  " d.NGAYSANXUAT as ngaysanxuat, d.NGAYHETHAN " +
													  " from  ERP_HOADONNCC_DONMUAHANG d  " +
													  " where  d.hoadonncc_fk = "+this.hdNccId+" and d.sanpham_fk = "+sp.getId()+
													  "and d.soluong <> 0 and d.MUAHANG_FK = "+ sp.getMuahang_fk() ;
										}
	
										System.out.println("Khoi tao san pham con: " + command);
										ResultSet spConRs = db.get(command);
	
										List<ISpDetail> spConList = new ArrayList<ISpDetail>();
										ISpDetail spCon = null;
										if (spConRs != null) {
											while (spConRs.next()) {
												String spConMa = spConRs.getString("sanpham_fk");
												String solo = spConRs.getString("solo");
												String soluong = formatter.format(spConRs.getDouble("soluong"));
												
												String ngaysanxuat = spConRs.getString("ngaysanxuat");
												String ngayhethan = spConRs.getString("ngayhethan");
											
												spCon = new SpDetail(spConMa, solo,"", soluong, ngaysanxuat, ngayhethan);
												spCon.setSoluongmax(formatter.format(spConRs.getDouble("soluongmax")));
												spCon.setDongiaLo(formatter.format(spConRs.getDouble("dongia")));
												spConList.add(spCon);
											}
											spConRs.close();
										}
										sp.setSpDetail(spConList);
									}
									/*else
									{
										sp.setSoluongnhan(
												formatter2.format(rs.getDouble("soluonghd") - rs.getDouble("soluongdanhan")));
										
										String comand = "select g.sanpham_Fk, g.solo, g.soluong, g.ngaysx as NGAYSANXUAT, g.NGAYHETHAN from "
												+ "ERP_HOADONNCC_DONMUAHANG d "
												+ "inner join ERP_NHAPKHONHAPKHAU f on d.muahang_fk = f.pk_seq "
												+ "inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET g on g.nhapkho_fk = f.PK_SEQ and g.SANPHAM_FK = d.SANPHAM_FK "
												+ "where d.hoadonncc_fk = "+this.hdNccId + " and g.sanpham_fk = "+sp.getId();
	
										System.out.println("Khoi tao san pham con: " + comand);
										ResultSet spConRs = db.get(comand);
	
										List<ISpDetail> spConList = new ArrayList<ISpDetail>();
										ISpDetail spCon = null;
										if (spConRs != null) {
											while (spConRs.next()) {
												String spConMa = spConRs.getString("sanpham_fk");
												String solo = spConRs.getString("solo");
												String soluong = formatter.format(spConRs.getDouble("soluong"));
												String ngaysanxuat = spConRs.getString("ngaysanxuat");
												String ngayhethan = spConRs.getString("ngayhethan");
												spCon = new SpDetail(spConMa, solo, soluong, ngaysanxuat, ngayhethan);
												spCon.setSoluongmax(soluong);
												spConList.add(spCon);
											}
											spConRs.close();
										}
										sp.setSpDetail(spConList);
									}*/
								}
								spList.add(sp);
							}
							rs.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					this.spList = spList;
				}
			}
		}

	}

	public void init() {
		
		String query = "";
		
				query = "SELECT isnull(A.ISTUDONG,0) as  ISTUDONG   ,a.PK_SEQ as nhId, a.trangthai, a.loaihanghoa_fk, a.noidungnhan_fk, a.SOHOADON, a.NGAYNHAN, a.NGAYCHOT, a.diengiai, b.pk_seq as dvthId, b.TEN as dvthTen,   \n"
				+ " 	     case when a.trahang_fk is null then c.PK_SEQ else d.PK_SEQ end as PoId, a.TRANGTHAI, a.NoiDungNhap_fk , a.NCC_KH_FK, c.LOAI as loaimh, a.KHONHAN_FK, a.KHACHHANGKYGUI_FK, \n"
				+ "        isnull( ( select isnull(NGUONGOCHH, 'TN') from ERP_MUAHANG where PK_SEQ = c.pk_seq ), 'TN') as NGUONGOCHH, hdNCC_fk,  a.muahang_fk as muahang_fk, isnull(a.tigia, '1') as tigia, c.sopo as sopo,isnull(p.ngayghinhan,'') as ngayhoadon  \n"
				+ " FROM erp_nhanhang a " 
				+ " inner join ERP_DONVITHUCHIEN b on a.DONVITHUCHIEN_FK = b.PK_SEQ \n"
				+ "  left join erp_hoadonncc hd on a.hdncc_fk = hd.pk_seq \n"
				 + " left join erp_park p on hd.park_fk = p.pk_seq \n" 
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
					/*if(this.loaimh.trim().equals("2"))
					{
						this.ldnId = "100000";
					}*/
					this.ngayhoadon = rs.getString("NGAYHOADON");
					this.isPONK = rs.getString("NGUONGOCHH").equals("TN") ? "0" : "1";
					this.hdNccId = rs.getString("hdNCC_fk") == null ? "" : rs.getString("hdNCC_fk");

					this.isTudong = rs.getString("ISTUDONG");

					this.muahang_fk1 = rs.getString("muahang_fk") == null ? "" : rs.getString("muahang_fk");
					this.tigia = rs.getString("tigia");
					this.sopoId = rs.getString("sopo");

					if (this.KhoNhanId.trim().length() <= 0) {
						this.KhoNhanId = rs.getString("KHONHAN_FK") == null ? "" : rs.getString("KHONHAN_FK");
						this.khachhangId = rs.getString("KHACHHANGKYGUI_FK") == null ? ""
								: rs.getString("KHACHHANGKYGUI_FK");
					}
					System.out.println("khonhan "+KhoNhanId);
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

	private void initSanPhamXK() {
		NumberFormat formatter = new DecimalFormat("#,###,###.##");

		String query = "select isnull(mh.PK_SEQ, 0) as pk_seq , isnull(mh.SOPO, '') as SOPO , case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as spId, "
				+ "case nh.loaihanghoa_fk when 0 then isnull(sp.MA, isnull(sp.ma, ''))  when 1 then tscd.Ma when 3 then ccdc.MA else ncp.Ten end AS spMa,  "
				+ " CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten1, sp.Ten)   "
				+ "     ELSE a.DienGiai END AS spTen, "
				+ " a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, "
				+ "isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, "
				+ "khott.pk_seq as khottId, khott.ma + ', ' + khott.ten as khottTen     "
				+ "from ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   "
				+ "LEFT join SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   "
				+ "LEFT JOIN ERP_MASCLON tscd on a.taisan_fk = tscd.pk_seq        "
				+ "LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  "
				+ "LEFT JOIN erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  "
				+ "LEFT JOIN ERP_KHOTT khott on a.khonhan = khott.pk_seq "
				+ "LEFT JOIN ERP_MUAHANG mh on mh.PK_SEQ = a.muahang_fk " +
				// "inner join ERP_THUENHAPKHAU t on nh.SoToKhai_fk = t.PK_SEQ "
				// +
				// "inner join ERP_THUENHAPKHAU_HOADONNCC th on t.PK_SEQ =
				// th.THUENHAPKHAU_FK " +
				// "inner join ERP_HOADONNCC_DONMUAHANG hd on th.HOADONNCC_FK =
				// hd.HOADONNCC_FK and a.SANPHAM_FK = hd.SANPHAM_FK " +
		"where a.NHANHANG_FK = '" + this.id + "'";

		System.out.println("[ErpNhanhang_Giay.initSanPham] query = " + query);
		ResultSet rsSp = db.get(query);

		if (rsSp != null) {
			NumberFormat formater = new DecimalFormat("#,###,###.##");

			try {
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next()) {
					String muahangpkseq = rsSp.getString("PK_SEQ");
					String soPo = rsSp.getString("SOPO");

					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");

					String soluongdat = formatter.format(rsSp.getDouble("SOLUONGDAT"));
					String soluongnhan = formatter.format(rsSp.getDouble("SOLUONGNHAN"));

					String hansudung = rsSp.getString("HANSUDUNG");
					String dungsai = rsSp.getString("DUNGSAI");
					String dvdl = rsSp.getString("DonVi");
					String dongia = rsSp.getString("DONGIA");

					String thanhtien = formatter.format(rsSp.getDouble("SOLUONGNHAN") * rsSp.getDouble("DONGIA"));

					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					/*if (soluongdat != "" && soluongnhan != "")
						sanpham.setCOnlai(Float.toString(Float.parseFloat(soluongdat.replaceAll(",", ""))
								- Float.parseFloat(soluongnhan.replaceAll(",", ""))));*/

					if (this.loaihanghoa.equals("0")) {
						String comand = "";
						if (this.isNCCNK.equals("0")) {
							// them sothung trong cau select
							comand = " select sanpham_fk, solo,sothung, soluong, ngaysanxuat, ngayhethan from ERP_NHANHANG_SP_CHITIET "
									+ " where NGAYNHANDUKIEN='" + ngaynhandk + "' AND nhanhang_fk = '" + this.id
									+ "' and sanpham_fk = '" + spId + "'" + " order by lannhan asc ";
						} else {
							// them sothung trong cau select
							comand = " select sanpham_fk, solo,sothung, soluong, ngaysanxuat, ngayhethan from ERP_NHANHANG_SP_CHITIET "
									+ " where MUAHANG_FK = '" + muahangpkseq + "' and   NGAYNHANDUKIEN='" + ngaynhandk
									+ "' AND nhanhang_fk = '" + this.id + "' and sanpham_fk = '" + spId + "'"
									+ " order by lannhan asc ";
						}

						System.out.println("Khoi tao san pham con: " + comand);
						ResultSet spConRs = db.get(comand);

						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						ISpDetail spCon = null;
						if (spConRs != null) {
							while (spConRs.next()) {
								String spConMa = spConRs.getString("sanpham_fk");
								String solo = spConRs.getString("solo");
								// them sothung trong table ERP_NHANHANG_SP_CHITIET
								String sothung=formatter.format(spConRs.getInt("sothung"));
								
								String soluong = formatter.format(spConRs.getDouble("soluong"));
								String ngaysanxuat = spConRs.getString("ngaysanxuat");
								String ngayhethan = spConRs.getString("ngayhethan");
									// them so thung trong constructor
								spCon = new SpDetail(spConMa, solo,sothung, soluong, ngaysanxuat, ngayhethan);
								spConList.add(spCon);
							}
							spConRs.close();
						}

						sanpham.setSpDetail(spConList);
					}

					sanpham.setMuahang_fk(muahangpkseq);
					sanpham.setSoPO(soPo);

					sanpham.setDungsai(dungsai);
					sanpham.setDongia(dongia);
					sanpham.setTiente(rsSp.getString("TienTe_Fk"));
					sanpham.setTigiaquydoi(rsSp.getString("TyGiaQuyDoi"));
					sanpham.setDongiaViet(rsSp.getString("DonGiaViet"));

					sanpham.setKhonhanId(rsSp.getString("khottId") == null ? "" : rsSp.getString("khottId"));
					sanpham.setKhonhanTen(rsSp.getString("khottTen") == null ? "" : rsSp.getString("khottTen"));

					double soluongDat = rsSp.getDouble("SOLUONGDAT");

					// Tinh so luong MAX + dung sai co the nhan
					double soluongPONhan = 0;
					double soluongMax = soluongDat;

					if (muahangpkseq.trim().length() > 1) {
						query = "select sum(b.SOLUONGNHAN)  as soluongDaNhan  " + "from ERP_NHANHANG a "
								+ "inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  "
								+ "inner join ERP_THUENHAPKHAU t on a.SoToKhai_fk = t.PK_SEQ "
								+ "inner join ERP_THUENHAPKHAU_HOADONNCC th on t.PK_SEQ = th.THUENHAPKHAU_FK "
								+ "inner join ERP_HOADONNCC_DONMUAHANG hd on th.HOADONNCC_FK = hd.HOADONNCC_FK and b.SANPHAM_FK = hd.SANPHAM_FK "
								+ "where hd.MUAHANG_FK = '" + muahangpkseq + "' and NGAYNHANDUKIEN = '" + ngaynhandk
								+ "' and b.SANPHAM_FK = '" + spId + "' and a.TRANGTHAI not in (3, 4) ";

						if (this.id.trim().length() > 0) {
							query += " and a.pk_seq != '" + this.id + "' ";
						}

						ResultSet rsNhanTD = db.get(query);
						if (rsNhanTD != null) {
							if (rsNhanTD.next()) {
								// double soluongPODat =
								// rsNhanTD.getDouble("soluongDat");
								double soluongPODat = soluongDat;

								soluongPONhan = rsNhanTD.getDouble("soluongDaNhan");
								soluongMax = (soluongPODat
										+ (soluongPODat * Double.parseDouble(dungsai.replaceAll(",", "")) / 100))
										- soluongPONhan;
							}
							rsNhanTD.close();
						}
					}

					sanpham.setSoluongDaNhan(formater.format(soluongPONhan));
					sanpham.setSoluongMaxNhan(Double.toString(soluongMax));

					sanpham.setthanhtien(thanhtien);
					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;
			} catch (Exception e) {
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();

			}
		}
	}

	private void initSanPham() {

		NumberFormat formatter = new DecimalFormat("#,###,###.##");

		String query = " ";
		if(!this.loaimh.equals("0"))
		{
			query = "SELECT  distinct case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as spId, \n"
				+ " case nh.loaihanghoa_fk when 0 then isnull(sp.MA, isnull(sp.ma, ''))  when 1 then tscd.Ma when 3 then ccdc.MA else ncp.Ten end AS spMa,  \n"
				+ " CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten1, sp.Ten)        \n"
				+ " ELSE a.DienGiai END AS spTen, mh.PK_SEQ as MUAHANG_FK, mh.SOPO ,   \n"
				+ " a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, \n"
				+ " isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, \n"
				+ " khott.pk_seq as khottId,   khott.ten as khottTen     \n"
				+ " FROM ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   \n"
				+ " INNER JOIN ERP_MUAHANG mh on a.MUAHANG_FK = mh.PK_SEQ \n"
				+ " LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   \n"
				+ " LEFT JOIN ERP_MASCLON tscd on a.taisan_fk = tscd.pk_seq        \n"
				+ " LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  \n"
				+ " LEFT JOIN erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  \n"
				+ " LEFT JOIN KHO khott on a.khonhan = khott.pk_seq  " 
				+ " WHERE a.NHANHANG_FK = '" + this.id + "'";					
		}
		else
		{
			query = "SELECT  distinct case nh.loaihanghoa_fk when 0 then A.SANPHAM_FK when 1 then tscd.PK_SEQ when 3 then ccdc.pk_seq else ncp.pk_seq end as spId, \n"
				+ " case nh.loaihanghoa_fk when 0 then isnull(sp.MA, isnull(sp.ma, ''))  when 1 then tscd.Ma when 3 then ccdc.MA else ncp.Ten end AS spMa,  \n"
				+ " CASE nh.loaihanghoa_fk WHEN 0 THEN isnull(sp.Ten1, sp.Ten)        \n"
				+ " ELSE a.DienGiai END AS spTen, nk.MUAHANG_FK as MUAHANG_FK, mh.SOPO ,   \n"
				+ " a.NGAYNHANDUKIEN, a.DUNGSAI, a.DONGIA, a.SOLUONGDAT, a.SOLUONGNHAN, isnull(sp.HANSUDUNG, '0') as HanSuDung, \n"
				+ " isnull(a.DonVi, 'NA') as donvi, a.TienTe_Fk, a.TyGiaQuyDoi, a.DonGiaViet, \n"
				+ " khott.pk_seq as khottId,   khott.ten as khottTen     \n"
				+ " FROM ERP_NHANHANG_SANPHAM a inner join ERP_NhanHang nh on a.nhanhang_fk = nh.pk_seq   \n"
				+ " inner join ERP_HOADONNCC_DONMUAHANG nk on a.MUAHANG_FK = nk.MUAHANG_FK \n"
				+ " INNER JOIN ERP_MUAHANG mh on nk.MUAHANG_FK = mh.PK_SEQ \n"
				+ " LEFT join ERP_SANPHAM sp on a.SANPHAM_FK = sp.PK_SEQ   \n"
				+ " LEFT JOIN ERP_MASCLON tscd on a.taisan_fk = tscd.pk_seq        \n"
				+ " LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq  \n"
				+ " LEFT JOIN erp_congcudungcu ccdc on a.ccdc_fk = ccdc.pk_seq  \n"
				+ " LEFT JOIN KHO khott on a.khonhan = khott.pk_seq  " 
				+ " WHERE a.NHANHANG_FK = '" + this.id + "'";			
		}
		System.out.println("[ErpNhanhang_Giay.initSanPham] query = " + query);
		ResultSet rsSp = db.get(query);

		if (rsSp != null) {
			NumberFormat formater = new DecimalFormat("#,###,###.##");

			try {
				ISanpham sanpham;
				List<ISanpham> spList = new ArrayList<ISanpham>();
				while (rsSp.next()) {
					String spId = rsSp.getString("spId");
					String spMa = rsSp.getString("spMa");
					String spTen = rsSp.getString("spTen");
					String ngaynhandk = rsSp.getString("NGAYNHANDUKIEN");

					String soluongdat = formatter.format(rsSp.getDouble("SOLUONGDAT"));
					String soluongnhan = formatter.format(rsSp.getDouble("SOLUONGNHAN"));

					String hansudung = rsSp.getString("HANSUDUNG");
					String dvdl = rsSp.getString("DonVi");
					String dongia = formatter.format(rsSp.getDouble("DONGIA"));

					String muahang_fk = rsSp.getString("MUAHANG_FK");
					String soPO = rsSp.getString("SOPO");

					String thanhtien = formatter.format(rsSp.getDouble("SOLUONGNHAN") * rsSp.getDouble("DONGIA"));

					sanpham = new Sanpham(spId, spMa, spTen, soluongnhan, hansudung, ngaynhandk, soluongdat, dvdl);
					if (soluongdat != "" && soluongnhan != "")
						sanpham.setCOnlai(Float.toString(Float.parseFloat(soluongdat.replaceAll(",", ""))
								- Float.parseFloat(soluongnhan.replaceAll(",", ""))));

					if (this.loaihanghoa.equals("0")) {
						//them sothung
						String comand = " select sanpham_fk, solo,sothung, soluong, giatheolo, ngaysanxuat, ngayhethan, khu_fk from ERP_NHANHANG_SP_CHITIET "
								+ " where NGAYNHANDUKIEN='" + ngaynhandk + "' AND nhanhang_fk = '" + this.id
								+ "' and sanpham_fk = '" + spId + "' and muahang_fk = '"+muahang_fk+"' order by lannhan asc ";

						System.out.println("Khoi tao san pham con: " + comand);
						ResultSet spConRs = db.get(comand);

						List<ISpDetail> spConList = new ArrayList<ISpDetail>();
						ISpDetail spCon = null;
						if (spConRs != null) {
							while (spConRs.next()) {
								String spConMa = spConRs.getString("sanpham_fk");
								String solo = spConRs.getString("solo");
								// them sothung trong table ERP_NHANHANG_SP_CHITIET
								String sothung=formatter.format(spConRs.getInt("sothung"));
								
								String soluong = formatter.format(spConRs.getDouble("soluong"));
								String ngaysanxuat = spConRs.getString("ngaysanxuat");
								String ngayhethan = spConRs.getString("ngayhethan");
								double dongialo = spConRs.getDouble("giatheolo");
								// them sothung
								spCon = new SpDetail(spConMa, solo,sothung, soluong, ngaysanxuat, ngayhethan);
								spCon.setDongiaLo(formatter.format(dongialo));
								if(this.loaimh.trim().equals("1") || this.loaimh.trim().equals("0"))
								{
									/*comand = 
										"select a.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ "
										+ "inner join ERP_HOADONNCC_DONMUAHANG hd_ddh on nh.hdNCC_fk = hd_ddh.HOADONNCC_FK and ct.SANPHAM_FK = hd_ddh.SANPHAM_FK "
										+ "where nh.trangthai <> 3 and hd_ddh.MUAHANG_FK = c.pk_seq and ct.SANPHAM_FK = a.SANPHAM_FK and ct.SOLO = a.SOLO and nh.PK_SEQ <> "+this.id+" "
										+ "group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax "
										+ "from erp_phanbomuahang_sp_chitiet a inner join erp_phanbomuahang_po b on a.phanbo_fk = b.phanbo_fk "
										+ "inner join ERP_MUAHANG c on b.poduocpb = c.PK_SEQ and c.congty_fk = a.congty_fk "
										+ "inner join ERP_HOADONNCC_DONMUAHANG d on c.PK_SEQ = d.muahang_fk  and a.SANPHAM_FK = d.SANPHAM_FK "
										+ "inner join ERP_PHANBOMUAHANG e on b.PHANBO_FK = e.pk_seq "
										+ "inner join ERP_NHAPKHONHAMAY f on f.muahang_fk = e.MUAHANG_FK inner join ERP_NHAPKHONHAMAY_SP_CHITIET g on g.nhamay_fk = f.PK_SEQ "
										+ "and g.SOLO = a.SOLO and g.SANPHAM_FK = a.SANPHAM_FK "
										+ "where d.hoadonncc_fk = "+this.hdNccId + " and a.sanpham_fk = "+spConMa+" and a.solo = '"+solo+"'";*/
									if(this.loaimh.equals("1"))
									{
										comand = "select d.*, d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ "
											+ "where nh.trangthai <> 3 and nh.hdNCC_fk = d.HOADONNCC_FK and ct.SANPHAM_FK = a.SANPHAM_FK and ct.SOLO = a.SOLO and nh.PK_SEQ <> "+this.id+" "
											+ "group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, g.NGAYSANXUAT, g.NGAYHETHAN "
											+ "from erp_phanbomuahang_sp_chitiet a inner join erp_phanbomuahang_po b on a.phanbo_fk = b.phanbo_fk "
											+ "inner join ERP_MUAHANG c on b.poduocpb = c.PK_SEQ and c.congty_fk = a.congty_fk "
											+ "inner join ERP_HOADONNCC_DONMUAHANG d on c.PK_SEQ = d.muahang_fk  and a.SANPHAM_FK = d.SANPHAM_FK and d.SOLO = a.SOLO and d.MUAHANG_FK = b.PODUOCPB  "
											+ "inner join ERP_PHANBOMUAHANG e on b.PHANBO_FK = e.pk_seq "
											+ "inner join ERP_NHAPKHONHAMAY f on f.muahang_fk = e.MUAHANG_FK inner join ERP_NHAPKHONHAMAY_SP_CHITIET g on g.nhamay_fk = f.PK_SEQ "
											+ "and g.SOLO = a.SOLO and g.SANPHAM_FK = a.SANPHAM_FK "
											+ "where d.hoadonncc_fk = "+this.hdNccId + " and a.sanpham_fk = "+spConMa+" and a.solo = '"+solo+"' and d.muahang_fk = "+muahang_fk+"";
									}
									else
									{
										comand = "select d.*, d.soluong - isnull((select SUM(ct.soluong) from ERP_NHANHANG_SP_CHITIET ct inner join ERP_NHANHANG nh on ct.NHANHANG_FK = nh.PK_SEQ "
											+ "where nh.trangthai <> 3 and ct.SANPHAM_FK = d.SANPHAM_FK and ct.SOLO = d.SOLO and nh.hdNCC_fk = d.HOADONNCC_FK and nh.PK_SEQ <> "+this.id+" "
											+ "group by ct.SANPHAM_FK, ct.SOLO), 0) as soluongmax, a.NGAYSX, a.NGAYHETHAN "
											+ "from ERP_NHAPKHONHAPKHAU_SP_CHITIET a "
											+ "inner join ERP_HOADONNCC_DONMUAHANG d on a.NHAPKHO_FK = d.muahang_fk  and a.SANPHAM_FK = d.SANPHAM_FK and d.SOLO = a.SOLO "
											+ " and d.hoadonncc_fk = "+this.hdNccId + " and a.sanpham_fk = "+spConMa+" and a.solo = '"+solo+"' and d.muahang_fk = "+muahang_fk+"";
									}
									ResultSet rsslmax = db.get(comand);
									if(rsslmax!=null && rsslmax.next())
										spCon.setSoluongmax(formatter.format(rsslmax.getDouble("soluongmax")));
									else
										spCon.setSoluongmax(soluong);
								}
								else
								{
									spCon.setSoluongmax(soluong);
								}
								spCon.setkhuid(spConRs.getString("khu_fk"));
								spConList.add(spCon);
							}
							spConRs.close();
						}

						sanpham.setSpDetail(spConList);
					}

					sanpham.setMuahang_fk(muahang_fk);
					sanpham.setSoPO(soPO);
					sanpham.setDongia(dongia);
					sanpham.setTiente(rsSp.getString("TienTe_Fk"));
					sanpham.setTigiaquydoi(rsSp.getString("TyGiaQuyDoi"));
					sanpham.setDongiaViet(rsSp.getString("DonGiaViet"));
					String khottId = rsSp.getString("khottId") == null ? "" : rsSp.getString("khottId");
					sanpham.setKhonhanId(khottId);
					sanpham.setKhonhanTen(rsSp.getString("khottTen") == null ? "" : rsSp.getString("khottTen"));

					double soluongDat = rsSp.getDouble("SOLUONGDAT");

					// Tinh so luong MAX + dung sai co the nhan
					double soluongPONhan = 0;
					double soluongMax = soluongDat;

					if (this.poId.trim().length() > 0) {
						String sql = "select sum(b.SOLUONGNHAN)  as soluongDaNhan  "
								+ "from ERP_NHANHANG a inner join ERP_NHANHANG_SANPHAM b on a.PK_SEQ = b.NHANHANG_FK  "
								+ "where b.muahang_fk = '" + muahang_fk + "' and NGAYNHANDUKIEN = '" + ngaynhandk
								+ "' and SANPHAM_FK = '" + spId + "' and a.TRANGTHAI not in (3, 4) and a.hdncc_fk = "+this.hdNccId+" ";
						query = sql;
						if (this.id.trim().length() > 0) {
							query += " and a.pk_seq != '" + this.id + "' ";
						}
						System.out.println("lay so luong dat: " + query);
						ResultSet rsNhanTD = db.get(query);
						if (rsNhanTD != null) {
							if (rsNhanTD.next()) {
								// double soluongPODat =
								// rsNhanTD.getDouble("soluongDat");
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

					sanpham.setSoluongDaNhan(formater.format(soluongPONhan));
					sanpham.setSoluongMaxNhan(Double.toString(soluongMax));
					sanpham.setthanhtien(thanhtien);

					spList.add(sanpham);
				}
				rsSp.close();
				this.spList = spList;

			} catch (Exception e) {
				System.out.println("115.Exception: " + e.getMessage());
				e.printStackTrace();

			}
		}
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
				+ "  LEFT JOIN ERP_MASCLON TSCD ON A.TAISAN_FK = TSCD.PK_SEQ         "
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
						if (sp.getSpDetail().size() <= 0) {
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
		
		if (this.loaihanghoa.equals("0") && this.KhoNhanId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn kho nhận cho phiếu nhận hàng này";
			return false;
		} else if(this.KhoNhanId.trim().length() > 0){
			if (this.loaikho.equals("5") || this.loaikho.equals("8")) {
				if (this.khachhangId.trim().length() <= 0) {
					this.msg = "Vui lòng chọn khách hàng cho kho nhận này";
					return false;
				}
			} else {
				this.khachhangId = "NULL";
			}
		}
		else
		{
			this.khachhangId = "NULL";
			this.KhoNhanId = "NULL";
		}

		if (this.ndnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}

		if (this.loaihanghoa.equals("0") && this.ldnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lý do nhận hàng";
			return false;
		}

//		String cmd = "";
//
//		if (this.ndnId.equals("100000")) {
//			cmd = "select ngaymua from erp_muahang where pk_seq = '" + this.poId + "' and ngaymua > '"	+ this.ngaynhanhang + "'";
//		} else {
//			cmd = "select ngaymua from erp_trahang where pk_seq = '" + this.poId + "' and ngaymua > '"	+ this.ngaynhanhang + "'";
//		}
//		ResultSet rsCheck = db.get(cmd);

//		String ngaymua = "";
//		if (rsCheck != null) {
//			try {
//				if (rsCheck.next()) {
//					ngaymua = rsCheck.getString("ngaymua");
//				}
//				rsCheck.close();
//			} catch (SQLException e) {
//			}
//		}

		String query = "";

		if (this.nccId.trim().length() > 0) {
			String query1 = "";
			query1 = "select SOHIEUTAIKHOAN from ERP_NHACUNGCAP ncc inner join ERP_TAIKHOANKT tk on ncc.TAIKHOAN_FK = tk.PK_SEQ where ncc.PK_SEQ = "
					+ this.nccId;

			ResultSet rsNG = db.get(query1);
			try {
				if (rsNG.next()) {
					if (rsNG.getString("SOHIEUTAIKHOAN").indexOf("331120") > 0) { // nha cung cap co so hieu tai khoan 331120
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
		 		gan kho cho xu ly=100047 la kho cho xu ly nguyen lieu trong bang erp_nhanhang
			*/

			query = "insert ERP_NHANHANG(KHACHHANGKYGUI_FK, KHONHAN_FK, NGAYNHAN, MUAHANG_FK, NGAYCHOT, LOAIHANGHOA_FK, NOIDUNGNHAN_FK, DIENGIAI, " +
					"DONVITHUCHIEN_FK,  NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, NoiDungNhap_fk, NCC_KH_FK, hdNCC_fk, tigia ,khochoxuly_fk, " +
					"GHICHU , NHAPHANPHOI_FK) "
					+ "values( " + this.khachhangId + ", " + this.KhoNhanId + ",'" + this.ngaynhanhang + "', "
					+ this.muahang_fk1 + ", '" + this.ngaynhanhang + "', '" + this.loaihanghoa + "', '" + this.ndnId
					+ "', N'" + this.diengiai + "', '" + this.dvthId + "',  " + " '" + ngaytao + "', '" + ngaytao
					+ "', '" + this.userId + "', '" + this.userId + "', '0', '" + this.congtyId + "', " + ldn_fk + ", "
					+ NCC_KH_FK + ", " + this.hdNccId + ", '" + this.tigia + "','"+this.KhoNhanId+"', " + " N'" + this.ghichu + "', " + nppId
					+ " )";
			System.out.println(" vao khu che xuat ne :" + query);

			if (!db.update(query)) {
				this.msg = "Khong the tao moi Nhan hang: " + query;
				db.getConnection().rollback();
				return false;
			}

			String nhCurrent = "";
			query = "select IDENT_CURRENT('Erp_NHANHANG') as nhId";

			ResultSet rs1 = db.get(query);

			if (rs1 != null) {
				System.out.println(" vao dc day");
				try {
					while (rs1.next()) {
						this.id = rs1.getString("nhId");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			ResultSet rsNh = db.get(query);
			if (rsNh.next()) {
				nhCurrent = rsNh.getString("nhId");
				rsNh.close();
			}

			if (this.spList.size() > 0) {
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);

					if (sp.getSoluongnhan() != "") // chi luu nhung san pham nguoi dung nhap so luong
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

						query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, MUAHANG_FK ,SANPHAM_FK, TAISAN_FK, CCDC_FK, CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC) "
								+ "values('" + nhCurrent + "', " + sp.getMuahang_fk() + ", " + SanPham_FK + ", "
								+ TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', N'"
								+ sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + khonhan + ", " + "'"
								+ sp.getSoluongdat().replaceAll(",", "") + "',  '"
								+ sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', "
								+ Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + sp.getTiente()
								+ "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "")
								+ "' , " + songayvuotmuc + ", " + soluongvuotmuc + " )";
						System.out.println("insert nhanhang_sanpham: "+query);
						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}

						double tongchitiet = 0;

						List<ISpDetail> detailList = sp.getSpDetail();
						for (int j = 0; j < detailList.size(); j++) {
							ISpDetail detail = detailList.get(j);

							if (detail.getSoluong().trim().length() > 0 //&& !detail.getSoluong().equals("0")
									&& detail.getSolo() != "" && detail.getNgaySx() != "") {
									// sua: them so thung
								/*
								 * set co dinh cho khu_fk =100000 (khu biet tru) trong bang ERP_NHANHANG_SP_CHITIET
								 */
								query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, MUAHANG_FK, SANPHAM_FK, LANNHAN, SOLO,SOTHUNG, SOLUONG, GIATHEOLO, NGAYSANXUAT, NGAYHETHAN, NGAYNHANDUKIEN ,KHU_FK, MAME, MATHUNG) "
										+ "values('" + nhCurrent + "', " + sp.getMuahang_fk() + ", '" + sp.getId() + "', '"
										+ Integer.toString(j + 1) + "', '" + detail.getSolo() + "','"+detail.getSothung()+"', " + "'"
										+ detail.getSoluong().replaceAll(",", "") + "', "
										+ Double.parseDouble(detail.getDongiaLo().replaceAll(",", "")) + ", '"
										+ detail.getNgaySx() + "', '" + detail.getNgayHethan() + "','"
										+ sp.getNgaynhandukien() + "',"
										+ (detail.getkhuid().length() > 0 ? detail.getkhuid() : 100000) + " ,'','')";

								tongchitiet = tongchitiet + Double.parseDouble(detail.getSoluong().replaceAll(",", ""));

								// System.out.println("11.Insert Nhan hang
								// ERP_NHANHANG_SP_CHITIET: " + query);

								if (!db.update(query)) {
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
									db.getConnection().rollback();
									return false;
								}
								
								
								if(this.loaimh.trim().equals("1"))
									if (Double.parseDouble(detail.getSoluong().replaceAll(",", "")) > Double.parseDouble(detail.getSoluongmax().replaceAll(",", ""))) {
										this.msg = "Số lượng không được lớn hơn số lượng được phân bổ: " + detail.getSolo();
										db.getConnection().rollback();
										return false;
									}
							}
						}

						if (this.loaihanghoa.equals("0")) {
							System.out.println("[soluongnhan] "+sp.getSoluongnhan()+", [soluongct] "+tongchitiet);
							if (Double.parseDouble(sp.getSoluongnhan().replaceAll(",", "")) - tongchitiet != 0) {
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :" + sp.getMa();
								db.getConnection().rollback();
								return false;
							}

						}

					}
				}
			}

			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_NhanHang", new String[] { nhCurrent } );

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

	private boolean UpdateHoanTatNhanHang(boolean flag, String id) {
		try {
			int index = 0;
			if (flag == true) {
				index = 1;
			}
			String query = "";
			query = "update ERP_PARK set HOANTAT_NHANHANG = '" + index + "' where PK_SEQ in ("
					+ "		select park_fk from ERP_HOADONNCC where pk_seq in (" 
					+ "			 select hdNCC_fk from ERP_NHANHANG where PK_SEQ = " + id + "))";

			int k = this.db.updateReturnInt(query);
			if (k != 1) {
				this.msg = " Không cập nhật được trạng thái hoàn tất nhận hàng hoá đơn.";
				this.db.getConnection().rollback();
				return false;
			}
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	// kiểm tra đã hoàn tất nhận hàng của hoá đơn này chưa?
	private boolean checkHoanTatNhanHang(){
		try {
			double sldat =0;
			double slnhan = 0;
			String sql = " select isnull( (select SUM(SOLUONG) as sl from ERP_HOADONNCC_DONMUAHANG " +
						 " where HOADONNCC_FK = "+this.hdNccId+"),0) as sldat," +
						 " isnull((select SUM(SOLUONGNHAN * TILEQUYDOI_DOLUONG) from ERP_NHANHANG_SANPHAM where NHANHANG_FK in (" +
						 " select PK_SEQ from ERP_NHANHANG where hdNCC_fk ="+this.hdNccId +")),0) as slnhan";
			ResultSet rs = this.db.get(sql);
			if (rs != null) {
				if (rs.next()) {
					sldat = rs.getInt("sldat");
					slnhan = rs.getInt("slnhan");
				}
				rs.close();
			}
			if(sldat == slnhan)
				return true;
			return false;

		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean updateNhanHang() {
//		Utility_Kho util_kho = new Utility_Kho();
		System.out.println("vao nhan hang, loaimh " +  this.loaimh + ", sopo " + this.poId);
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
						if (sp.getSpDetail().size() <= 0) {
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
		
		if (this.loaihanghoa.equals("0") && this.KhoNhanId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn kho nhận cho phiếu nhận hàng này";
			return false;
		} else if(this.KhoNhanId.trim().length() > 0){
			if (this.loaikho.equals("5") || this.loaikho.equals("8")) {
				if (this.khachhangId.trim().length() <= 0) {
					this.msg = "Vui lòng chọn khách hàng cho kho nhận này";
					return false;
				}
			} else {
				this.khachhangId = "NULL";
			}
		}
		else
		{
			this.khachhangId = "NULL";
			this.KhoNhanId = "NULL";
		}

		if (this.ndnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn nội dung nhận hàng";
			return false;
		}

		if (this.loaihanghoa.equals("0") && this.ldnId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn lý do nhận hàng";
			return false;
		}

		String cmd = "";
		if (this.ndnId.equals("100000"))
			cmd = "select ngaymua from erp_muahang where pk_seq = '" + this.poId + "' and ngaymua > '"
					+ this.ngaynhanhang + "'";
		else
			cmd = "select ngaymua as NGUONGOCHH from erp_trahang where pk_seq = '" + this.poId + "' and ngaymua > '"
					+ this.ngaynhanhang + "'";

		System.out.println("----CHECK: " + cmd);
//		ResultSet rsCheck = db.get(cmd);
//
//		String ngaymua = "";
//		if (rsCheck != null) {
//			try {
//				if (rsCheck.next()) {
//					ngaymua = rsCheck.getString("ngaymua");
//				}
//				rsCheck.close();
//			} catch (SQLException e) {
//			}
//		}

		if (this.hdNccId.trim().length() <= 0) {
			this.msg = "Vui lòng chọn Số hóa đơn NCC ";
			return false;
		}

		try {
			db.getConnection().setAutoCommit(false);

			String query = "";
//			String muahang_fk = "null";
//			String trahang_fk = "null";
			
//			if (this.ndnId.equals("100000"))
//				muahang_fk = this.poId;
//			else
//				trahang_fk = this.poId;

			String ldn_fk = "null";
			if (this.ldnId.trim().length() > 0)
				ldn_fk = this.ldnId;

			String NCC_KH_FK = "null";
			if (this.nccId.trim().length() > 0)
				NCC_KH_FK = this.nccId;

			String nppId = util.getIdNhapp(this.userId);

			// CAP NHAT KHO (nhan hang tu dong): ngaychot(solo) của nhận hàng tự
			// động, khokygui, khoncc
//			String query1 = "";
			String soloKygui = "";
			String ngaysx_Kygui = "";
			String ngayhh_Kygui = "";
//			String khokyguiNCC = "";
//			String khoNCC = "";

//			String khoxuat = "NULL";
//			String khonhanhang = "NULL";
//			String nccXuatId = "NULL";
//			String nccNhanId = "NULL";
//			String khXuatId = "NULL";
//			String khNhanId = "NULL";

//			String solo_NK = "";
//			String ngaynhap_NK = "";
			// Tạo CK từ kho ký gửi >> Kho NM/NK
			/*if (this.isTudong.equals("1") && this.loaihanghoa.equals("0")) {
				query1 = " SELECT DISTINCT NH.NGAYCHOT, (select PK_SEQ from KHO where LOAI = 2) khokyguiNCC, (select PK_SEQ from KHO where LOAI = 7) khoNCC, "
						+ "        MH.LOAI, NH.MUAHANG_FK ,NHSP.SANPHAM_FK, NHSP.SOLUONGNHAN, NHSP.DONGIA, NHSP.SOLOKYGUI, NHSP.NGAYSANXUAT, NHSP.NGAYHETHAN "
						+ " FROM ERP_NHANHANG NH inner join ERP_NHANHANG_SANPHAM NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK "
						+ "      INNER JOIN ERP_MUAHANG mh ON NH.MUAHANG_FK = mh.PK_SEQ   " + " WHERE NH.PK_SEQ = "
						+ this.id;
				System.out.println("Cau lay TT " + query1);
				ResultSet rsNG = db.get(query1);
				if (rsNG != null) {
					while (rsNG.next()) {
						this.ngaychot = rsNG.getString("NGAYCHOT");
						khokyguiNCC = rsNG.getString("khokyguiNCC");
						khoNCC = rsNG.getString("khoNCC");
						this.loaimh = rsNG.getString("LOAI");
						System.out.println("loaimh " +  this.loaimh);

						soloKygui = rsNG.getString("SOLOKYGUI") == null ? "" : rsNG.getString("SOLOKYGUI");
						ngaysx_Kygui = rsNG.getString("NGAYSANXUAT") == null ? "" : rsNG.getString("NGAYSANXUAT");
						ngayhh_Kygui = rsNG.getString("NGAYHETHAN") == null ? "" : rsNG.getString("NGAYHETHAN");

						double sl = rsNG.getDouble("SOLUONGNHAN");
						double booked = 0;
						double avai = rsNG.getDouble("SOLUONGNHAN");

						if (!this.loaimh.equals("2")) { // Lấy số lô luc nhap
														// kho NCC
							if (this.loaimh.equals("0")) {
								query1 = "SELECT ct.SOLO, ct.NGAYNHAP "
										+ "FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET ct on nk.PK_SEQ = ct.NHAPKHO_FK "
										+ "WHERE nk.MUAHANG_FK = " + rsNG.getString("MUAHANG_FK")
										+ " and ct.SANPHAM_FK =  " + rsNG.getString("SANPHAM_FK") + " ";
							} else if (this.loaimh.equals("1")) {
								query1 = "SELECT ct.SOLO, nk.NGAYNHAP "
										+ "FROM ERP_NHAPKHONHAMAY nk inner join ERP_NHAPKHONHAMAY_SP_CHITIET ct on nk.PK_SEQ = ct.NHAMAY_FK "
										+ "WHERE nk.MUAHANG_FK in (select distinct b.MUAHANG_FK from ERP_PHANBOMUAHANG_PO a inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ where a.PODUOCPB =  "
										+ rsNG.getString("MUAHANG_FK") + ")  and ct.SANPHAM_FK =  "
										+ rsNG.getString("SANPHAM_FK") + " and solo = '"+soloKygui+"'";
							}
							System.out.println("Câu lấy Số lô" + query1);
							ResultSet rsSL = db.get(query1);

							if (rsSL != null) {
								while (rsSL.next()) {
									solo_NK = rsSL.getString("SOLO");
									ngaynhap_NK = rsSL.getString("NGAYNHAP");
								}
								rsSL.next();
							}
						}

						// Cap nhat kho

						// Giam kho ký gửi
						khoxuat = khokyguiNCC;
						nccXuatId = this.nccId;

						String msg1 = util_kho.Update_NPP_Kho_Sp(this.ngaynhanhang, "Nhận hàng(tự động) - Cập nhật", db,
								khokyguiNCC, rsNG.getString("SANPHAM_FK"), nppId, "100000", sl * (-1), booked,
								avai * (-1), rsNG.getDouble("DONGIA"));
						if (msg1.length() > 0) {
							this.msg = msg1;
							db.getConnection().rollback();
							return false;
						}

						msg1 = util_kho.Update_NPP_Kho_Sp_NCC(this.ngaynhanhang, "Nhận hàng(tự động) - Cập nhật", db,
								khokyguiNCC, rsNG.getString("SANPHAM_FK"), nppId, "100000", sl * (-1), booked,
								avai * (-1), rsNG.getDouble("DONGIA"), this.nccId);
						if (msg1.length() > 0) {

							this.msg = msg1;
							db.getConnection().rollback();
							return false;
						}
						System.out.println("ngayhh "+ngayhh_Kygui);
						if (this.loaimh.equals("0"))
						{
							msg1 = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
									"Nhận hàng(tự động) - Cập nhật", db, khokyguiNCC, rsNG.getString("SANPHAM_FK"), nppId,
									"100000", this.nccId, soloKygui, ngaysx_Kygui, ngayhh_Kygui, ngaysx_Kygui, sl * (-1), booked,
									avai * (-1), rsNG.getDouble("DONGIA"));
							if (msg1.length() > 0) {
	
								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}
						}
						else if (this.loaimh.equals("1"))
						{
							query1 = " SELECT NHSP.SANPHAM_FK, NHSP.giatheolo, NHSP.SOLO, NHSP.SOLUONG,NHSP.NGAYSANXUAT, NHSP.NGAYHETHAN "
									+ " FROM ERP_NHANHANG NH inner join ERP_NHANHANG_SP_CHITIET NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK "
									+ " WHERE NH.PK_SEQ = "+ this.id +" and nhsp.sanpham_fk = "+rsNG.getString("SANPHAM_FK");
							ResultSet rsSL = db.get(query1);

							if (rsSL != null) {
								while (rsSL.next()) {
									sl = rsSL.getDouble("SOLUONG");
									avai = sl;
									msg1 = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
											"Nhận hàng(tự động) - Cập nhật", db, khokyguiNCC, rsNG.getString("SANPHAM_FK"), nppId,
											"100000", this.nccId, rsSL.getString("SOLO"), rsSL.getString("NGAYSANXUAT"), rsSL.getString("NGAYHETHAN"), ngaysx_Kygui, sl * (-1), booked,
											avai * (-1), rsSL.getDouble("giatheolo"));
									if (msg1.length() > 0) {
			
										this.msg = msg1;
										db.getConnection().rollback();
										return false;
									}
								}
								rsSL.next();
							}
						}
						
						if (!this.loaimh.equals("2")) // Loai: mua hang nhap
														// khau / trong nuoc
						{
							khonhanhang = khoNCC;

							// Tang kho nhà cung cấp
							msg1 = util_kho.Update_NPP_Kho_Sp(this.ngaynhanhang, "Nhận hàng(tự động) - Cập nhật", db,
									khoNCC, rsNG.getString("SANPHAM_FK"), nppId, "100000", sl, booked, avai,
									rsNG.getDouble("DONGIA"));
							if (msg1.length() > 0) {

								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}

							msg1 = util_kho.Update_NPP_Kho_Sp_NCC(this.ngaynhanhang, "Nhận hàng(tự động) - Cập nhật",
									db, khoNCC, rsNG.getString("SANPHAM_FK"), nppId, "100000", sl, booked, avai,
									rsNG.getDouble("DONGIA"), this.nccId);
							if (msg1.length() > 0) {

								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}

							if (this.loaimh.equals("0"))
							{
								msg1 = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
										"Nhận hàng(tự động) - Cập nhật", db, khoNCC, rsNG.getString("SANPHAM_FK"), nppId,
										"100000", this.nccId, solo_NK, ngaynhap_NK, ngaynhap_NK, ngaynhap_NK, sl, booked, avai,
										rsNG.getDouble("DONGIA"));
								if (msg1.length() > 0) {
	
									this.msg = msg1;
									db.getConnection().rollback();
									return false;
								}
							}
							else if (this.loaimh.equals("1"))
							{
								query1 = " SELECT NHSP.SANPHAM_FK, NHSP.giatheolo, NHSP.SOLO, NHSP.SOLUONG,NHSP.NGAYSANXUAT, NHSP.NGAYHETHAN "
										+ " FROM ERP_NHANHANG NH inner join ERP_NHANHANG_SP_CHITIET NHSP ON NH.PK_SEQ = NHSP.NHANHANG_FK "
										+ " WHERE NH.PK_SEQ = "+ this.id +" and nhsp.sanpham_fk = "+rsNG.getString("SANPHAM_FK");
								ResultSet rsSL = db.get(query1);

								if (rsSL != null) {
									while (rsSL.next()) {
										sl = rsSL.getDouble("SOLUONG");
										avai = sl;
										msg1 = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
												"Nhận hàng(tự động) - Cập nhật", db, khoNCC, rsNG.getString("SANPHAM_FK"), nppId,
												"100000", this.nccId, rsSL.getString("SOLO"), rsSL.getString("NGAYSANXUAT"), rsSL.getString("NGAYHETHAN"), ngaynhap_NK, sl, booked, avai,
												rsSL.getDouble("giatheolo"));
										if (msg1.length() > 0) {
				
											this.msg = msg1;
											db.getConnection().rollback();
											return false;
										}
									}
									rsSL.next();
								}
							}
						}

					}
					rsNG.close();
				}

				String msg1 = TaoChuyenKhoTuDong(db, userId, this.congtyId, nppId, khoxuat, khonhanhang, this.id,
						nccXuatId, nccNhanId, khXuatId, khNhanId, "0");
				if (msg1.trim().length() > 0) {
					this.msg = msg1;
					db.getConnection().rollback();
					return false;
				}

			}*/

			// END
			System.out.println("loaimh " +  this.loaimh);
			query = "update ERP_NHANHANG set KHACHHANGKYGUI_FK = " + this.khachhangId + " , KHONHAN_FK = "
					+ this.KhoNhanId + ", MUAHANG_FK = " + this.muahang_fk1 + ", NGAYNHAN = '" + this.ngaynhanhang
					+ "', NOIDUNGNHAN_FK = '" + this.ndnId + "', " + "DIENGIAI = N'" + this.diengiai + "', "
					+ "DONVITHUCHIEN_FK = '" + this.dvthId + "'," + " NGAYSUA = '" + this.getDateTime() + "', "
					+ "NGUOISUA = '" + this.userId + "', NoiDungNhap_fk = " + ldn_fk + "," + " NCC_KH_FK = " + NCC_KH_FK
					+ ", hdNCC_fk = " + this.hdNccId + ", tigia = '" + this.tigia + "', GHICHU = N'" + this.ghichu
					+ "', NHAPHANPHOI_FK = " + nppId + " " + "where pk_seq = '" + this.id + "'";

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

							if (sp.getDungsai().trim().length() <= 0)
								sp.setDungsai("0");

							// double dungsai =
							// Double.parseDouble(sp.getDungsai().replaceAll(",",
							// ""));
							// double soluongdat =
							// Double.parseDouble(sp.getSoluongdat().replaceAll(",",
							// ""));

							// double slgMax = soluongdat + Math.abs(dungsai) *
							// soluongdat / 100;
							double slgMax = Double.parseDouble(sp.getSoluongMaxNhan().replaceAll(",", ""));

							if (soluongnhan > slgMax) {
								this.msg = "Tổng số lượng nhận của sản phẩm: " + sp.getMa()
										+ " không được phép vượt quá tổng đặt (" + sp.getSoluongdat()
										+ ") và dung sai cho phép ( " + sp.getDungsai()
										+ " ). Bạn chỉ có thể nhận tối đa là ( " + slgMax + " )  ";
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
								+ "') <= nksp.SONGAYLUUKHO then 0 " + " else DATEDIFF(day,nksp.NGAYNHAP,'"
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

						query = "insert ERP_NHANHANG_SANPHAM(NHANHANG_FK, MUAHANG_FK, SANPHAM_FK, TAISAN_FK, CCDC_FK, CHIPHI_FK, DIENGIAI, DONVI, NGAYNHANDUKIEN, KHONHAN, SOLUONGDAT, SOLUONGNHAN, DUNGSAI, DONGIA, TIENTE_FK, TYGIAQUYDOI, DONGIAVIET, SONGAYVUOTMUC, SOLUONGVUOTMUC, solokygui, ngayhethan, ngaysanxuat) "
								+ "values('" + this.id + "', " + sp.getMuahang_fk() + ", " + SanPham_FK + ", "
								+ TaiSan_FK + ", " + CCDC_FK + ", " + ChiPhi_FK + ", N'" + sp.getDiengiai() + "', N'"
								+ sp.getDvdl() + "', '" + sp.getNgaynhandukien() + "', " + khonhan + ", " + "'"
								+ sp.getSoluongdat().replaceAll(",", "") + "',  '"
								+ sp.getSoluongnhan().replaceAll(",", "") + "', '" + sp.getDungsai() + "', "
								+ Double.parseDouble(sp.getDongia().replaceAll(",", "")) + ", '" + sp.getTiente()
								+ "', '" + sp.getTigiaquydoi() + "', '" + sp.getDongiaViet().replaceAll(",", "") + "', "
								+ songayvuotmuc + ", " + soluongvuotmuc + ", '"+soloKygui+"', '"+ngayhh_Kygui+"', '"+ngaysx_Kygui+"' )";
						System.out.println("insert nhanhang_sanpham: "+query);
						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_NHANHANG_SANPHAM: " + query;
							System.out.println(this.msg);
							db.getConnection().rollback();
							return false;
						}

						// CAP NHAT KHO : với phiếu nhận hàng tự động
						// Tạo CK : Kho NM/NK >> Kho ký gửi
						/*if (this.isTudong.equals("1") && this.loaihanghoa.equals("0")) {
							solo_NK = "";
							ngaynhap_NK = "";

							khoxuat = "NULL";
							khonhanhang = "NULL";
							nccXuatId = "NULL";
							nccNhanId = "NULL";
							khXuatId = "NULL";
							khNhanId = "NULL";
							System.out.println("toiday1.....................");
							// Lấy số lô luc nhap kho NCC
							if (!this.loaimh.equals("2")) {
								if (this.loaimh.equals("0")) {
									query1 = "SELECT ct.SOLO, ct.NGAYNHAP "
											+ "FROM ERP_NHAPKHONHAPKHAU nk inner join ERP_NHAPKHONHAPKHAU_SP_CHITIET ct on nk.PK_SEQ = ct.NHAPKHO_FK "
											+ "WHERE nk.MUAHANG_FK = " + this.muahang_fk1 + " and ct.SANPHAM_FK =  "
											+ SanPham_FK + " ";
								} else if (this.loaimh.equals("1")) {
									query1 = "SELECT ct.SOLO, nk.NGAYNHAP "
											+ "FROM ERP_NHAPKHONHAMAY nk inner join ERP_NHAPKHONHAMAY_SP_CHITIET ct on nk.PK_SEQ = ct.NHAMAY_FK "
											+ "WHERE nk.MUAHANG_FK in (select distinct b.MUAHANG_FK from ERP_PHANBOMUAHANG_PO a inner join ERP_PHANBOMUAHANG b on a.PHANBO_FK = b.PK_SEQ where a.PODUOCPB =  "
											+ this.muahang_fk1 + ")  and ct.SANPHAM_FK =  " + SanPham_FK + " ";
								}
								System.out.println("Câu lấy Số lô" + query1);
								ResultSet rsSL = db.get(query1);

								if (rsSL != null) {
									while (rsSL.next()) {
										solo_NK = rsSL.getString("SOLO");
										ngaynhap_NK = rsSL.getString("NGAYNHAP");
									}
									rsSL.next();
								}
							}

							double soluong = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));
							double booked = 0;
							double avai = Double.parseDouble(sp.getSoluongnhan().replaceAll(",", ""));

							// Tang kho ký gửi
							nccNhanId = this.nccId;
							khonhanhang = khokyguiNCC;

							String msg1 = util_kho.Update_NPP_Kho_Sp(this.ngaynhanhang, "Nhận hàng(tự động) - Cập nhật",
									db, khokyguiNCC, SanPham_FK, nppId, "100000", soluong, booked, avai,
									Double.parseDouble(sp.getDongia().replaceAll(",", "")));
							if (msg1.length() > 0) {
								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}

							msg1 = util_kho.Update_NPP_Kho_Sp_NCC(this.ngaynhanhang, "Nhận hàng(tự động) - Cập nhật",
									db, khokyguiNCC, SanPham_FK, nppId, "100000", soluong, booked, avai,
									Double.parseDouble(sp.getDongia().replaceAll(",", "")), this.nccId);
							if (msg1.length() > 0) {

								this.msg = msg1;
								db.getConnection().rollback();
								return false;
							}
							if (this.loaimh.equals("0")) {
								msg1 = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
										"Nhận hàng(tự động) - Cập nhật", db, khokyguiNCC, SanPham_FK, nppId, "100000",
										this.nccId, soloKygui, ngaysx_Kygui, ngaysx_Kygui, ngaysx_Kygui, soluong, booked, avai,
										Double.parseDouble(sp.getDongia().replaceAll(",", "")));
								if (msg1.length() > 0) {
	
									this.msg = msg1;
									db.getConnection().rollback();
									return false;
								}
							}
							System.out.println("toiday.....................");
							if (!this.loaimh.equals("2")) {
								khoxuat = khoNCC;
								// Giam kho nhà cung cấp
								msg1 = util_kho.Update_NPP_Kho_Sp(this.ngaynhanhang, "Nhận hàng(tự động) - Cập nhật",
										db, khoNCC, SanPham_FK, nppId, "100000", soluong * (-1), booked, avai * (-1),
										Double.parseDouble(sp.getDongia().replaceAll(",", "")));
								if (msg1.length() > 0) {
									this.msg = msg1;
									db.getConnection().rollback();
									return false;
								}

								msg1 = util_kho.Update_NPP_Kho_Sp_NCC(this.ngaynhanhang,
										"Nhận hàng(tự động) - Cập nhật", db, khoNCC, SanPham_FK, nppId, "100000",
										soluong * (-1), booked, avai * (-1),
										Double.parseDouble(sp.getDongia().replaceAll(",", "")), this.nccId);
								if (msg1.length() > 0) {

									this.msg = msg1;
									db.getConnection().rollback();
									return false;
								}
								if (this.loaimh.equals("0")) {
									msg1 = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
											"Nhận hàng(tự động) - Cập nhật", db, khoNCC, SanPham_FK, nppId, "100000", nccId,
											solo_NK, ngaynhap_NK, ngaynhap_NK, ngaynhap_NK, soluong * (-1), booked, avai * (-1),
											Double.parseDouble(sp.getDongia().replaceAll(",", "")));
									if (msg1.length() > 0) {
	
										this.msg = msg1;
										db.getConnection().rollback();
										return false;
									}
								}
							}
							

						}*/

						// END

						double tongchitiet = 0;
						List<ISpDetail> detailList = sp.getSpDetail();
						for (int j = 0; j < detailList.size(); j++) {
							ISpDetail detail = detailList.get(j);

							if (detail.getSoluong() != "" //&& !detail.getSoluong().equals("0") 
									&& detail.getSolo() != "" && detail.getNgaySx() != "") {
								// them so thung
								query = "insert ERP_NHANHANG_SP_CHITIET(NHANHANG_FK, MUAHANG_FK, SANPHAM_FK, LANNHAN, SOLO,SOTHUNG, SOLUONG, GIATHEOLO,  NGAYSANXUAT, NGAYHETHAN,NGAYNHANDUKIEN ,KHU_FK, MAME, MATHUNG) "
										+ "values('" + this.id + "', " + sp.getMuahang_fk() + ", '" + sp.getId() + "', '" + Integer.toString(j + 1)
										+ "', '" + detail.getSolo() + "','"+detail.getSothung()+"' ," + "'"
										+ detail.getSoluong().replaceAll(",", "") + "', "
										+ Double.parseDouble(detail.getDongiaLo().replaceAll(",", "")) + " ,'"
										+ detail.getNgaySx() + "', '" + detail.getNgayHethan() + "','"
										+ sp.getNgaynhandukien() + "' ,"
										+ (detail.getkhuid().length() > 0 ? detail.getkhuid() : "NULL") + ", '', '')";
								tongchitiet = tongchitiet + Double.parseDouble(detail.getSoluong().replaceAll(",", ""));

								System.out.println("ERP	_NHANHANG_SP_CHITIET: " + query);
								if (!db.update(query)) {
									this.msg = "Khong the tao moi ERP_NHANHANG_SP_CHITIET: " + query;
									System.out.println(this.msg);
									db.getConnection().rollback();
									return false;
								}
								if(this.loaimh.trim().equals("1"))
									if (Double.parseDouble(detail.getSoluong().replaceAll(",", "")) > Double.parseDouble(detail.getSoluongmax().replaceAll(",", ""))) {
										this.msg = "Số lượng không được lớn hơn số lượng được phân bổ: " + detail.getSolo();
										db.getConnection().rollback();
										return false;
									}
								
								/*if (this.loaimh.equals("1")) {
									if (this.isTudong.equals("1") && this.loaihanghoa.equals("0")) {
										this.msg = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
												"Nhận hàng(tự động) - Cập nhật", db, khokyguiNCC, SanPham_FK, nppId, "100000",
												this.nccId, detail.getSolo(), detail.getNgaySx(), detail.getNgayHethan(), ngaysx_Kygui, Double.parseDouble(detail.getSoluong().replaceAll(",", "")), 0, Double.parseDouble(detail.getSoluong().replaceAll(",", "")),
												Double.parseDouble(sp.getDongia().replaceAll(",", "")));
										if (msg.length() > 0) {
											db.getConnection().rollback();
											return false;
										}
										
										this.msg = util_kho.Update_NPP_Kho_Sp_Chitiet_NCC(this.ngaynhanhang,
												"Nhận hàng(tự động) - Cập nhật", db, khoNCC, SanPham_FK, nppId, "100000", nccId,
												detail.getSolo(),detail.getNgaySx(), detail.getNgayHethan(), ngaynhap_NK, Double.parseDouble(detail.getSoluong().replaceAll(",", "")) * (-1), 0, Double.parseDouble(detail.getSoluong().replaceAll(",", "")) * (-1),
												Double.parseDouble(sp.getDongia().replaceAll(",", "")));
										if (this.msg.length() > 0) {
		
											db.getConnection().rollback();
											return false;
										}
									}
								}*/
							}
						}

						if (this.loaihanghoa.equals("0")) {

							if (Double.parseDouble(sp.getSoluongnhan().replaceAll(",", "")) - tongchitiet != 0) {
								this.msg = "Vui lòng kiểm tra số lo chi tiết của sản phẩm :" + sp.getMa();
								db.getConnection().rollback();
								return false;
							}

						}
					}
				}
				
				/*if (this.isTudong.equals("1") && this.loaihanghoa.equals("0")) {
					String msg1 = TaoChuyenKhoTuDong(db, userId, this.congtyId, nppId, khoxuat, khonhanhang, this.id,
							nccXuatId, nccNhanId, khXuatId, khNhanId, "1");
					if (msg1.trim().length() > 0) {
						this.msg = msg1;
						db.getConnection().rollback();
						return false;
					}
				}*/
			}

			//CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_NhanHang", new String[] { this.id } );
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			this.msg = "Lỗi không thể cập nhật nhận hàng: " + e.getMessage();
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
//			double soluongconlai;
			while (rs.next()) {
				hd = rs.getString("hdId");
//				soluongconlai = rs.getDouble("soluongconlai");
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

		try {
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
		
			while (RsKtr.next()) {
				this.loaihanghoa = RsKtr.getString("LOAIHANGHOA_FK");
				this.dvthId = RsKtr.getString("DONVITHUCHIEN_FK");
			}
			RsKtr.close();
			
			/*query = "select top 1 KHOTT_FK from ERP_HOADONNCC_DONMUAHANG a inner join ERP_MUAHANG_SP b on a.MUAHANG_FK = b.MUAHANG_FK \n"+ 
					"and isnull(a.SANPHAM_FK, 0) = isnull(b.SANPHAM_FK, 0) and isnull(a.CHIPHI_FK, 0) = isnull(b.CHIPHI_FK, 0) \n"+ 
					"and isnull(a.TAISAN_FK, 0) = isnull(b.TAISAN_FK, 0) and isnull(a.CCDC_FK, 0) = isnull(b.CCDC_FK, 0) \n"+
					"where a.hoadonncc_fk = "+hoadonnccId;
			System.out.println("[init kho] "+query);
			ResultSet RsKho = db.get(query);
		
			while (RsKho.next()) {
				this.KhoNhanId = RsKho.getString("KHOTT_FK")==null?"0":RsKho.getString("KHOTT_FK");
			}
			RsKho.close();*/
			
			
			// lay Kho Nhan va Kho Cho Xu Ly
			query = " select KHOTONTRU_FK , (select ngayghinhan from erp_park where pk_seq in (select park_fk from erp_hoadonncc where pk_seq = "+hoadonnccId+") )  as ngayghinhan   from ERP_HOADONNCC where pk_seq = "+ hoadonnccId;
			ResultSet rs = this.db.get(query);
			String khoTonTru = "";
			try{
				if(rs!=null){
					if(rs.next()){
						this.ngaynhanhang=rs.getString("ngayghinhan");
						khoTonTru = rs.getString("KHOTONTRU_FK");
					}
					rs.close();
				} 
				
				// lấy kho của User đăng nhập để định vị kho. Biệt trữ hay tồn trữ.
				
				// lấy kho mà người dùng được quyền xem
				String sql = " select khott_fk from NHANVIEN_KHOTT where nhanvien_fk ="+this.userId+" " +
					  " union select kho_fk from NHANVIEN_KHO where nhanvien_fk =" + this.userId;
				
				rs = this.db.get(sql);
				List<String> dsKho = new ArrayList<String>();
				if(rs!=null){
					while(rs.next()){
						dsKho.add(rs.getString("khott_fk"));
					}
					rs.close();
				}
				// check kho Tồn trữ.
				boolean check2 = this.ExistKho(dsKho, khoTonTru);
				if( check2 == true){
					this.KhoNhanId = khoTonTru;
				}
				
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
			
			
			
		} catch (SQLException e) {
			e.printStackTrace();
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

}
