package geso.traphaco.erp.beans.thuenhapkhau.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.thuenhapkhau.IErpHoadon;
import geso.traphaco.erp.beans.thuenhapkhau.IErpThuenhapkhau;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ErpThuenhapkhau implements IErpThuenhapkhau
{
	String userId;
	String congtyId;
	String id;
	String po;
	String poId;
	String soHD;
	String ptThueNK;
	String ThueNK;
	
	String cqt;
	String cqtId;
	
	String ptVAT;
	String VAT;
	String loaiMh;
	String ngaynhap;
	String ngaychungtu;
	String sochungtu;

	String diengiai;
	
	String pnkId;
	ResultSet pnkRs;
	
	String nccId;
	String ncc;

	ResultSet tnkRs;

	String msg;
	String[] spId;
	String[] soluong;
	String[] dongia;
	String[] thuesuat;
	String[] thue;
	
	Hashtable<String, String> tsht;
	Hashtable<String, String> tht;
	String tienhang;
	
	dbutils db;
	
	String loaihinh;
	
	String hoadonIds;
	
	List<IErpHoadon> hdList;
	
	HttpServletRequest req;
	
	String tigia = "";
	private String maTienTe = "";
	String tienteId = "";
	String nppdangnhap = "";
	
	private String maHS;
	private List<ErpSanPhamNhapKhau> sanPhamList;
	
	public ErpThuenhapkhau()
	{
		this.userId = "";
		this.id = "";

		this.cqt = "";
		this.cqtId = "";		
		this.po = "";
		this.poId = "";
		this.ptThueNK = "";
		this.ThueNK = "";
		this.soHD = "";
		this.ptVAT = "0";
		this.VAT = "0";
		
		this.diengiai = "";
		this.ngaynhap = "";
		this.ngaychungtu = "";
		this.sochungtu = "";

		this.pnkId = "";
		this.nccId = "";
		this.ncc = "";
		this.tsht = new Hashtable();
		this.tht = new Hashtable();
		this.tienhang = "0";
		this.msg = "";
		this.db = new dbutils();
		this.loaihinh = "";
		
		this.hoadonIds = "";
		this.tigia = "1.0";
		this.tienteId = "";
		this.setMaTienTe("");
		this.nppdangnhap = "";
		
		this.maHS = "";
		this.sanPhamList = new ArrayList<ErpSanPhamNhapKhau>();
	}
	
	public ErpThuenhapkhau(String id)
	{
		this.userId = "";
		this.id = id;

		this.cqt = "";
		this.cqtId = "";		
		this.ptVAT = "0";
		this.VAT = "0";

		this.diengiai = "";
		this.maHS = "";
		this.ngaynhap = "";
		this.ngaychungtu = "";
		this.sochungtu = "";

		this.pnkId = "";
		this.nccId = "";
		this.ncc = "";
		this.tsht = new Hashtable();
		this.tht = new Hashtable();
		this.tienhang = "0";
		
		this.msg = "";
		this.db = new dbutils();
		
		this.loaihinh = "";
		
		this.hoadonIds = "";
		this.tigia = "1.0";
		this.setMaTienTe("");
		this.tienteId = "";
		this.nppdangnhap = "";
		
		this.maHS = "";
		this.sanPhamList = new ArrayList<ErpSanPhamNhapKhau>();
	}
	
	public void init() 
	{
		this.getNppInfo();
		
		if (this.id.trim().length() > 0)
		{
			String query = 	"SELECT TNK.PK_SEQ AS TNKID, TNK.DIENGIAI, TNK.COQUANTHUE_FK AS CQTID, ISNULL(TNK.MAHS, '') as MAHS\n" +
							"		, CONVERT(VARCHAR, CQT.PK_SEQ) + ' - ' + CQT.MA + ', ' + CQT.TEN AS CQTTEN, \n" +
							"		TNK.PTVAT, TNK.VAT, TNK.NGAY, TNK.TRANGTHAI, \n" +
							"		NV1.TEN AS NGUOITAO, TNK.NGAYTAO, NV2.TEN AS NGUOISUA, TNK.NGAYSUA, \n" +
							"		NCC.PK_SEQ AS NCCID, CONVERT(VARCHAR, NCC.PK_SEQ) + ' - ' + NCC.MA + ', ' + NCC.TEN AS NCCTEN, \n" +
							"		ISNULL(TNK.NGAYCHUNGTU, '') AS NGAYCHUNGTU, TNK.SOCHUNGTU, \n" +		
							"		PTTHUENK, \n" +
							"		THUENK, ISNULL(TNK.TIENHANG, 0) AS TIENHANG , isnull(tnk.loaihinh, '') as loaihinh, \n" +
							" 		isnull(HOADONNCC, '') as HOADONNCC , ISNULL(TNK.TIENTE_FK,100000) as TIENTE_FK , ISNULL(TNK.TIGIA,1) AS TIGIA, isNull(tt.ma, '') as maTienTe \n" +
							"FROM ERP_THUENHAPKHAU TNK	\n" +				
							"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TNK.NCC_FK \n" +
							"INNER JOIN ERP_NHACUNGCAP CQT ON CQT.PK_SEQ = TNK.COQUANTHUE_FK \n" +
							"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = TNK.NGUOITAO \n" +
							"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = TNK.NGUOISUA \n" +
							"left join ERP_TIENTE tt on tt.pk_seq = tnk.tienTe_FK\n" +
							"WHERE TNK.PK_SEQ = '" + this.id + "'\n";
			System.out.println("Init: \n" + query + "\n-----------------------------------------------------------");
			
			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						this.ngaynhap = rs.getString("NGAY");
						this.cqtId = rs.getString("CQTID");
						this.cqt = rs.getString("CQTTEN");
						this.nccId =  rs.getString("NCCID");
						this.ncc = rs.getString("NCCTEN");
						this.VAT = rs.getString("VAT");
						this.ptVAT = rs.getString("PTVAT");
						this.diengiai = rs.getString("DIENGIAI");
						this.maHS = rs.getString("maHS");
						this.ngaychungtu = rs.getString("NGAYCHUNGTU");
						this.sochungtu = rs.getString("SOCHUNGTU") == null ? "" : rs.getString("SOCHUNGTU");
						//this.po = rs.getString("PO") ;
						//this.poId = rs.getString("POID") ;
						//this.soHD = rs.getString("SOHD") == null ? "" : rs.getString("SOHD");
						this.ptThueNK = rs.getString("PTTHUENK");
						this.ThueNK = rs.getString("THUENK");
						this.tienhang = rs.getString("TIENHANG");
						this.loaihinh = rs.getString("loaihinh");
						this.hoadonIds = rs.getString("HOADONNCC");
						this.tigia = rs.getString("TIGIA");
						this.tienteId = rs.getString("tienTe_FK");
						this.setMaTienTe(rs.getString("maTienTe"));
					}
					rs.close();
				}
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		}
		//this.createRs();
		initSanPham();
	}
	
	private void initSanPham() {
		if (this.sanPhamList == null)
			this.sanPhamList = new ArrayList<ErpSanPhamNhapKhau>();
		else this.sanPhamList.clear();
		
		String query = "";
		
		if (this.id.trim().length() == 0 && this.hoadonIds.trim().length() > 0)
			query = 
				/*"SELECT HOADONNCC_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK\n" +
				"	, ten, DVT, SOLO, SOLUONG\n" +
				"	, DONGIA, DONGIANT, THUESUAT, THANHTIEN\n" +
				"	, TIENTINHTHUENK, THUESUATNK, THUENHAPKHAU, PHANTRAMVATNK\n" +
				"	, VATNK\n" +
				"FROM \n" +
				"( \n" +
				"	SELECT HD_MH.HOADONNCC_FK, isNull(convert(nvarchar, HD_MH.SANPHAM_FK), '') as SANPHAM_FK, isNull(convert(nvarchar, HD_MH.TAISAN_FK), '') as TAISAN_FK, isNull(convert(nvarchar, HD_MH.CHIPHI_FK), '') as CHIPHI_FK\n" +
				"		, ISNULL(sp.ten, isnull(msc.TEN, '')) as ten, isNull(dv.DONVI, '') as DVT, HD_MH.SOLO, HD_MH.SOLUONG\n" +
				"		, HD_MH.DONGIAVIET as DONGIA, HD_MH.DONGIA DONGIANT, 0 as THUESUAT, HD_MH.THANHTIEN\n" +
				"		, 0 as TIENTINHTHUENK, 0 as THUESUATNK, 0 as THUENHAPKHAU, 0 as PHANTRAMVATNK\n" +
				"		, 0 as VATNK\n" +
				"	FROM ERP_HOADONNCC_DONMUAHANG HD_MH \n" +
				"		INNER JOIN ERP_HOADONNCC HD ON HD.pk_seq=HD_MH.HOADONNCC_FK \n" +
				"		left join erp_sanpham sp on sp.PK_SEQ = HD_MH.SANPHAM_FK\n" +
				"		left join donvidoluong dv on ("+ (this.loaiMh.equals("0")?" cast(dv.PK_SEQ  as varchar(18)) ":" dv.donvi " )+"  )= HD_MH.dvt\n" +
				"		left join ERP_MASCLON msc on msc.PK_SEQ = HD_MH.TAISAN_FK\n" +
				"		WHERE HD.CONGTY_FK = " + this.congtyId + "\n" +
				"	AND HOADONNCC_FK IN (" + this.hoadonIds + ") \n" +
				") DATA \n" +
				"GROUP BY HOADONNCC_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK\n" +
				"	, ten, DVT, SOLO, SOLUONG\n" +
				"	, DONGIA, DONGIANT, THUESUAT, THANHTIEN\n" +
				"	, TIENTINHTHUENK, THUESUATNK, THUENHAPKHAU, PHANTRAMVATNK\n" +
				"	, VATNK \n";*/
				
				"SELECT HOADONNCC_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK\r\n" + 
				"	, ten, DVT, SOLO, SOLUONG\r\n" + 
				"	, DONGIA, DONGIANT, THUESUAT, THANHTIEN\r\n" + 
				"	, TIENTINHTHUENK, THUESUATNK, THUENHAPKHAU, PHANTRAMVATNK\r\n" + 
				"	, VATNK\r\n" + 
				"FROM \r\n" + 
				"( \r\n" + 
				"	SELECT HD_MH.HOADONNCC_FK, isNull(convert(nvarchar, HD_MH.SANPHAM_FK), '') as SANPHAM_FK, isNull(convert(nvarchar, HD_MH.TAISAN_FK), '') as TAISAN_FK, isNull(convert(nvarchar, HD_MH.CHIPHI_FK), '') as CHIPHI_FK\r\n" + 
				"		, ISNULL(sp.ten, isnull(msc.TEN, '')) as ten, isNull(dv.DONVI, '') as DVT, HD_MH.SOLO, HD_MH.SOLUONG\r\n" + 
				"		, HD_MH.DONGIAVIET as DONGIA, HD_MH.DONGIA DONGIANT, 0 as THUESUAT, HD_MH.THANHTIEN\r\n" + 
				"		, 0 as TIENTINHTHUENK, 0 as THUESUATNK, 0 as THUENHAPKHAU, 0 as PHANTRAMVATNK\r\n" + 
				"		, 0 as VATNK, HD_MH.MUAHANG_FK, SOTT\r\n" + 
				"	FROM ERP_HOADONNCC_DONMUAHANG HD_MH \n" +
				"		INNER JOIN ERP_HOADONNCC HD ON HD.pk_seq=HD_MH.HOADONNCC_FK \n" +
				"		left join erp_sanpham sp on sp.PK_SEQ = HD_MH.SANPHAM_FK\n" +
				"		left join donvidoluong dv on ("+ (this.loaiMh.equals("0")?" cast(dv.PK_SEQ  as varchar(18)) ":" dv.donvi " )+"  )= HD_MH.dvt\n" +
				"		left join ERP_MASCLON msc on msc.PK_SEQ = HD_MH.TAISAN_FK\n" +
				"		WHERE HD.CONGTY_FK = " + this.congtyId + "\n" +
				"	AND HOADONNCC_FK IN (" + this.hoadonIds + ") \n" +
				") DATA \r\n" + 
				"GROUP BY HOADONNCC_FK, SANPHAM_FK, TAISAN_FK, CHIPHI_FK\r\n" + 
				"	, ten, DVT, SOLO, SOLUONG\r\n" + 
				"	, DONGIA, DONGIANT, THUESUAT, THANHTIEN\r\n" + 
				"	, TIENTINHTHUENK, THUESUATNK, THUENHAPKHAU, PHANTRAMVATNK\r\n" + 
				"	, VATNK, DATA.SOTT\r\n" + 
				"ORDER BY DATA.SOTT";
		else if (this.id.trim().length() > 0){
			query =
				" select ct.HOADONNCC_FK, isNull(convert(nvarchar, ct.SANPHAM_FK), '') as SANPHAM_FK \n" +
				" , isNull(convert(nvarchar, ct.TAISAN_FK), '') as TAISAN_FK \n" +
				" , isNull(convert(nvarchar, ct.CHIPHI_FK), '') as CHIPHI_FK \n" +
				" , ISNULL(sp.ten, isnull(msc.TEN,cp.DIENGIAI )) as ten, isNull(dv.DONVI, ct.DVT) as DVT, ct.SOLO, ct.SOLUONG \n" +
				" , ct.DONGIA, ct.DONGIANT, ct.THUESUAT, ct.THANHTIEN \n" +
				" , ct.TIENTINHTHUENK, ct.THUESUATNK, ct.THUENHAPKHAU, ct.PHANTRAMVATNK \n" +
				" , ct.VATNK \n" +
				" from ERP_THUENHAPKHAU_CHITIET ct \n" +
				" left join erp_sanpham sp on sp.PK_SEQ = ct.SANPHAM_FK \n" +
				" left join donvidoluong dv on CONVERT(NVARCHAR,dv.PK_SEQ) = CONVERT(NVARCHAR,ct.DVT) \n" +
				" left join ERP_NHOMCHIPHI cp on cp.PK_SEQ= ct.CHIPHI_FK \n" +
				" left join ERP_MASCLON msc on msc.PK_SEQ = ct.TAISAN_FK \n" +
				"where ct.THUENHAPKHAU_FK = " + this.id;
		}
		
		System.out.println("get san pham list:\n" + query + "\n----------------------------------------");
		if (query.trim().length() > 0)
		try {
			ResultSet rs = this.db.get(query);
			ErpSanPhamNhapKhau.getListFromResultset(rs, this.sanPhamList);
			System.out.println("this.tigia: " + this.tigia);
			if (this.id.trim().length() == 0 && this.hoadonIds.trim().length() > 0)
				ErpSanPhamNhapKhau.nhanTiGia(Double.parseDouble(this.tigia.replace(",", "")), this.sanPhamList);
			
			if(this.id.trim().length() == 0)
			{
			this.tienhang = Double.toString(ErpSanPhamNhapKhau.tinhTongThanhTien(this.sanPhamList));
			}
			this.VAT = Double.toString(ErpSanPhamNhapKhau.tinhTongVATNK(this.sanPhamList));
			this.ThueNK = Double.toString(ErpSanPhamNhapKhau.tinhTongThueNK(this.sanPhamList));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean Create()
	{	
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String result =  kiemTraTinhHopLeDuLieu();
			if (result.trim().length() > 0)
			{
				this.msg = "CR1.1 " + result;
				return false;
			}
			
			// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
			String ngaydenhantt = getNgayDenHanThanhToan();
			
			String query = "insert into ERP_THUENHAPKHAU(THUENK, PTTHUENK, SOHOADON, NGAYCHUNGTU\n" +
			 		", SOCHUNGTU, DIENGIAI, NCC_FK, NGAY\n" +
			 		", NGAYTAO, NGUOITAO,  NGAYSUA, NGUOISUA\n" +
			 		",  TRANGTHAI, CONGTY_FK, COQUANTHUE_FK, PTVAT\n" +
			 		", VAT, TIENHANG , LOAIHINH , HOADONNCC\n" +
			 		", NGAYDENHANTT, MAHS, TIENTE_FK, TIGIA) " +
					"values(" + this.ThueNK.replace(",", "") + ", " + this.ptThueNK.replace(",", "") + ", N'" + this.soHD +  "','" + this.ngaychungtu + "'\n" +
					", N'" + this.sochungtu + "', N'" + this.diengiai + "', '" + this.nccId + "', '" + this.ngaychungtu + "'\n" +
					", '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "'\n" +
					", '0', '" + this.congtyId + "', '" + this.cqtId + "', " + this.ptVAT.replace(",", "") + "\n" +
					", " + this.VAT.replace(",", "") + "," + this.tienhang.replace(",", "") + ", N'" + this.loaihinh  + "','" + this.hoadonIds + "'\n" +
					", '"+ ngaydenhantt +"', N'" + this.maHS + "', " + this.tienteId + ", " + this.tigia.replaceAll(",", "") + ")\n";
			
			System.out.println("___1.Insert: \n" + query + "\n--------------------------------------------------");
			if(!db.update(query))
			{
				this.msg = "CR1.2 Không thể tạo mới ERP_THUENHAPKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('ERP_THUENHAPKHAU') as thueId";

			ResultSet rsThue = db.get(query);						
			if(rsThue.next())
			{
				this.id = rsThue.getString("thueId");
				rsThue.close();
			}
			
			System.out.println("hdList" + this.hdList.size());
			
			String hoadonnccIds =  insertThueNK_HoaDonNCC();
			if (hoadonnccIds.trim().length() <= 0)
			{
				db.getConnection().rollback();
				this.msg = "CR1.1 Không thể thêm hóa đơn nhà cung cấp cho thuế nhập khẩu";
				return false;
			}
			
			if (!insertSanPham())
			{
				this.msg = "CR1.4 Không thể cập nhật tiền tệ ...: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "CR1.6 Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (java.sql.SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		
		return true;
	}
	
	private String insertThueNK_HoaDonNCC() {
		String hoadonnccIds = "";
		if(this.hdList.size() > 0)
		{
			String query = "";
			for(int i = 0; i < this.hdList.size(); i++)
			{	
				IErpHoadon hd = hdList.get(i);
				
				hoadonnccIds = hd.getID() + ",";
				
				query += "INSERT INTO ERP_THUENHAPKHAU_HOADONNCC(THUENHAPKHAU_FK , HOADONNCC_FK, SOLUONGTRA) \n" +
						"VALUES(" + this.id + ", " + hd.getID() + ", " + hd.getSOLUONGTRA().replaceAll(",", "") + "); \n";
			}
			if (query.trim().length() > 0)
				if(!db.update(query))//Luu vào bảng ERP_THUENHAPKHAU_HOADONNCC
				{
					return "";
				}
		}
		return hoadonnccIds;
	}

	private String getNgayDenHanThanhToan() {
		// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
		String query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '" + this.ngaychungtu + "')),120 ) as ngaydenhantt " +
				"FROM ERP_NHACUNGCAP " +
				"WHERE PK_SEQ = '"+ this.nccId +"'";
		String ngaydenhantt = "";
		
		try {
			ResultSet rsThoihanno = db.get(query);
			
			if(rsThoihanno!= null)
			{
				while(rsThoihanno.next())
				{
					ngaydenhantt = rsThoihanno.getString("ngaydenhantt") ;
				}
				rsThoihanno.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ngaydenhantt;
	}

	private String kiemTraTinhHopLeDuLieu() {
		if(this.ngaychungtu.trim().length() <= 0)
		{
			return "KTTHL1.1 Vui lòng chọn ngày tờ khai";
		}

		if(this.cqtId.trim().length() <= 0)
		{
			return "KTTHL1.2 Vui lòng chọn cơ quan thuế";
		}

		if(this.nccId.trim().length() <= 0)
		{
			return "KTTHL1.3 Vui lòng chọn nhà cung cấp";
		}

		if(this.hoadonIds.trim().length() <= 0)
		{
			return "KTTHL1.4 Vui lòng chọn hóa đơn";
		}
		
		
		if(this.diengiai.trim().length() <= 0)
		{
			return "VKTTHL1.5 ui lòng chọn diễn giải";
		}

		if(this.maHS.trim().length() <= 0)
		{
			return "KTTHL1.6 Vui lòng nhập 'Mã HS'";
		}
		
		if(this.tigia.trim().length() <= 0)
		{
			return "KTTHL1.7 Vui lòng nhập tỉ giá";
		}else
		{
			if(Double.parseDouble(this.tigia.replaceAll(",", "")) <= 0)
			{
				return "KTTHL1.8 Vui lòng nhập tỉ giá lớn hơn 0";
			}
		}
		return "";
	}

	private boolean insertSanPham() {
		if (this.id.trim().length() <= 0)
		{
			this.msg = "ISP1.1 Chưa có mã thuế nhập khẩu";
			return false;
		}
		String query = "";
		for (ErpSanPhamNhapKhau item : this.sanPhamList)
		{
			String sanPhamId = null;
			String taiSanId = null;
			String chiPhiId = null;
			
			if (item.getLoaiHangHoa() == 0)
				sanPhamId = item.getId();
			else if (item.getLoaiHangHoa() == 1)
				taiSanId = item.getId();
			else if (item.getLoaiHangHoa() == 2){
				chiPhiId = item.getId();
			}else {
				this.msg = "Không xác định loại sản phẩm";
				return false;
			}
			
			
			query += 
				"insert into ERP_THUENHAPKHAU_CHITIET\n" +
				"(THUENHAPKHAU_FK, HOADONNCC_FK, SANPHAM_FK, TAISAN_FK\n" +
				", CHIPHI_FK, SOLO, SOLUONG, DONGIA\n" +
				", DONGIANT, THUESUAT, THANHTIEN, TIENTINHTHUENK\n" +
				", THUESUATNK, THUENHAPKHAU, PHANTRAMVATNK, VATNK\n" +
				", DVT)\n" +
				"values(" + this.id + ", " + item.getHoaDonNCCId() + ", " + sanPhamId + ", " + taiSanId+ "\n" +
				", " + chiPhiId + ", '" + item.getSoLo() + "', " + Double.toString(item.getSoLuong()) + ", " + Double.toString(item.getDonGiaVND()) + "\n" +
				", " + Double.toString(item.getDonGiaNT()) + ", " + Double.toString(item.getThueSuat()) + ", " + Double.toString(item.getThanhTien()) + ", " +Double.toString(item.getTienTinhThueNhapKhau()) + "\n" +
				", " + Double.toString(item.getThueSuatNK()) + ", " + Double.toString(item.getThueNK()) + ", " + Double.toString(item.getPhanTramVATNK()) + ", " + Double.toString(item.getVATNK()) + ", N'" + item.getDonViTinh() + "' );\n";
			System.out.println("them san pham: \n" + query + "\n-----------------------------------------------------------");
			
		}
		
		try {
			if (!this.db.update(query))
			{
				this.msg = "ISP1.2 Không thể thêm hàng hóa cho thuế nhập khẩu";
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "ISP1.3 Không thể thêm hàng hóa cho thuế nhập khẩu";
			return false;
		}
		
		return true;
	}

	public boolean Update() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);

			String result =  kiemTraTinhHopLeDuLieu();
			if (result.trim().length() > 0)
			{
				this.msg = "UD1.1 " + result;
				return false;
			}

			// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
			String ngaydenhantt = getNgayDenHanThanhToan();
			
			String query = 	"update ERP_THUENHAPKHAU set NGAYCHUNGTU = '" + this.ngaychungtu + "', SOCHUNGTU = N'" + this.sochungtu + "', " +
							"DIENGIAI = N'" + this.diengiai + "', NCC_FK = '" + this.nccId + "', " +
							"COQUANTHUE_FK = '" + this.cqtId + "', PTVAT = " + this.ptVAT.replace(",", "") + ", VAT = " + this.VAT.replace(",", "") + ", " +
							"NGAY = '" + this.ngaychungtu + "', NGAYTAO = '" + this.getDateTime() + "', " +
							"NGUOITAO = '" + this.userId + "',  NGAYSUA = '" + this.getDateTime() + "', NGUOISUA = '" + this.userId + "', " +
							//"DONMUAHANG_FK = " + this.poId + ", " +
							"SOHOADON = N'" + this.soHD + "', " +
							"PTTHUENK = " + this.ptThueNK.replace(",", "") + ", " +
							"THUENK = " + this.ThueNK.replace(",", "") + ", " +
							"TIENHANG = " + this.tienhang.replace(",", "") + ", " +
							"LOAIHINH = N'" + this.loaihinh + "', " + 
							"HOADONNCC = '" + this.hoadonIds + "'," +
							"NGAYDENHANTT = '" + ngaydenhantt + "' \n" +
							", MAHS = N'" + this.maHS + "'\n" +
							", TIGIA = " + this.tigia.replaceAll(",", "") +
							", TIENTE_FK = " + this.tienteId +
							"WHERE PK_SEQ = '" + this.id + "' " ;
			
			System.out.println("___update: \n" + query + "\n---------------------------------------------------------");
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_THUENHAPKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			query = "delete ERP_THUENHAPKHAU_HOADONNCC where THUENHAPKHAU_FK = " + this.id ;
			
			if(!db.update(query))
			{
				this.msg = "Không thể xóa ERP_THUENHAPKHAU_HOADONNCC " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String hoadonnccIds =  insertThueNK_HoaDonNCC();
			if (hoadonnccIds.trim().length() <= 0)
			{
				db.getConnection().rollback();
				this.msg = "U1.1 Không thể thêm hóa đơn nhà cung cấp cho thuế nhập khẩu";
				return false;
			}
			
			// Phân bổ tiền thuế nhập khẩu vào từng sản phẩm trong hóa đơn nhà cung cấp dựa vào tỉ lệ đóng góp của số lượng
			
			query="delete ERP_THUENHAPKHAU_CHITIET  where THUENHAPKHAU_FK="+this.id;
			if(!db.update(query))//Luu vào bảng ERP_THUENHAPKHAU_HOADONNCC
			{
				this.msg = "Không thể tạo mới ...: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if (!insertSanPham())
			{
				this.msg = "CR1.4 Không thể cập nhật tiền tệ ...: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (java.sql.SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean UpdateVAT() 
	{
		try 
		{
			db.getConnection().setAutoCommit(false);

			String query = "update ERP_THUENHAPKHAU set PTVAT = " + this.ptVAT.replace(",", "") + ", VAT = " + this.VAT.replace(",", "") + ", " +
							"NGAYSUA = '" + this.getDateTime() + "', NGUOISUA = '" + this.userId + "' TIENHANG =  " + this.tienhang.replace(",", "") + " " +
							"WHERE PK_SEQ = '" + this.id + "' " ;
			
			System.out.println("___update: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật VAT ERP_THUENHAPKHAU " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// LƯU THÊM TIỀN TỆ VÀ TỈ GIÁ CHO TỜ KHAI
						
			// LẤY TIỀN TỆ TỪ INVOICE
			query = "select top 1 isnull(TIENTE_FK,100000) as tienteId from ERP_HOADONNCC where pk_seq in (select HOADONNCC_FK from ERP_THUENHAPKHAU_HOADONNCC where THUENHAPKHAU_FK = "+ this.id +")";
			ResultSet rsrs = db.get(query);
			if(rsrs!= null)
			{
				while(rsrs.next())
				{
					this.tienteId = rsrs.getString("tienteId");
				}
				rsrs.close();
			}
			
			query = "UPDATE ERP_THUENHAPKHAU set TIENTE_FK = '"+ this.tienteId +"', TIGIA = "+ this.tigia.replaceAll(",", "") +" where pk_seq = '"+ this.id +"' ";
			if(!db.update(query))//Luu vào bảng ERP_THUENHAPKHAU_HOADONNCC
			{
				this.msg = "Không thể cập nhật tiền tệ ...: " + query;
				db.getConnection().rollback();
				return false;
			}
						
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (java.sql.SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		
		return true;
	}
	
	public ResultSet getPoList(){
		String query = 	" SELECT MH.PK_SEQ AS MHID, '[' + MH.SOPO + '][ ' + NCC.TEN + ' ][ ' + CAST(MH.NGAYMUA AS VARCHAR(10)) + '][ ' + CAST(MH.TONGTIENAVAT AS VARCHAR(10)) + ' ]' AS PO " +
						" FROM 	ERP_MUAHANG MH " +
						"		INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MH.NHACUNGCAP_FK " +
						" WHERE MH.TRANGTHAI < 2 AND MH.TONGTIENAVAT > 0 AND NCC.PK_SEQ = " + nccId + " "+
//						"	   AND MH.NHAPHANPHOI_FK = "+this.nppdangnhap+" " +
						"	   AND MH.CONGTY_FK = "+this.congtyId;
		System.out.println(query);
		
		return this.db.get(query);
	}
	
	public ResultSet getNccList(){
		String query = 
				"select ncc.pk_seq as nccId, cast(ncc.pk_seq as nvarchar(10)) + ' - ' + ncc.ma + ', ' + ncc.ten as nccTen \n" +
				"from ERP_NHACUNGCAP ncc\n" +
				"inner join ERP_TAIKHOANKT tk on tk.PK_SEQ = ncc.TAIKHOAN_FK\n" +
				"where ncc.trangthai = '1' and ncc.CONGTY_FK = " + this.congtyId + "\n" +
				"and tk.congTy_FK = " + this.congtyId + " and tk.trangThai = 1 and tk.soHieuTaiKhoan = '33112000'\n";
		System.out.println("cau lenh lay danh sach ncc: \n" + query + "\n------------------------------------------------");
		return this.db.get(query);
	}
	
	public ResultSet getCoquanthue(){
		String query = "select PK_SEQ AS CQTID, cast(pk_seq as nvarchar(10)) + ' - ' + ma + ', ' + ten as CQT from ERP_NHACUNGCAP where trangthai = '1' and duyet = '1' AND LOAINCC = 100011 and CONGTY_FK = "+this.congtyId;
		return this.db.get(query);
	}
	
	public ResultSet getSoHoaDonNew(){
		String query =  " select tt.MA as maTienTe, p.TIENTE_FK, p.ncc_fk , hd.pk_seq , hd.sohoadon , hd.ngayhoadon ,  isnull(hd.tongsoluong,0) - isnull(soluongdatra.SOLUONGTRA, 0) as tongsoluong  \n" +
						" from ERP_HOADONNCC hd  \n" +
						" left join ERP_PARK p on p.pk_seq = hd.park_fk \n" +
						" left join ERP_TIENTE tt on tt.PK_SEQ = p.TIENTE_FK\n" +
						" left join ( select HOADONNCC_FK , SUM(SOLUONGTRA) as SOLUONGTRA  \n" +
						" 			  from ERP_THUENHAPKHAU_HOADONNCC  \n" +
						"             where THUENHAPKHAU_FK not in (select isnull(t.PK_SEQ, 0 )  \n" +
						"	                                        from ERP_THUENHAPKHAU t  		\n" +			   
						"	                                        where TRANGTHAI = 3 ) \n" +
						"             group by HOADONNCC_FK) soluongdatra on soluongdatra.HOADONNCC_FK = hd.pk_seq  \n" +
						" where HD.CONGTY_FK = " + this.congtyId + " \n" +
//								"and HD.NPP_FK = " + this.nppdangnhap+" \n" +
						" and p.ncc_fk = " + nccId + " \n" +
						" and (isnull(hd.tongsoluong,0) - isnull(soluongdatra.SOLUONGTRA, 0) > 0 or hd.tongsoluong is null)\n" +
						" and hd.pk_seq not in ( select isnull(th.HOADONNCC_FK, 0 )  \n" +
						"	from ERP_THUENHAPKHAU t  \n" +
						"	left join ERP_THUENHAPKHAU_HOADONNCC th on t.PK_SEQ = th.THUENhAPKHAU_fk   \n" +
						"	where TRANGTHAI != 3 \n" +
						" and t.CONGTY_FK = "+this.congtyId+") \n" +
						" and p.trangthai IN ( 1 , 2)  " +
						" and hd.pk_seq in (SELECT A.HOADONNCC_FK FROM ERP_HOADONNCC_DONMUAHANG  A " +
						" INNER JOIN ERP_MUAHANG B ON A.MUAHANG_FK=B.pk_seq AND B.LOAI='"+this.loaiMh+"' )  \n";
//						"and p.trangthai = 2 \n";
		
		System.out.println("--HOADONNEW: \n" + query + "\n-------------------------------------------------");
		return this.db.get(query);
	}
	
	public ResultSet getSoHoaDonUpdate(){
		String query =  "select tt.MA as maTienTe, p.TIENTE_FK, p.ncc_fk , hd.pk_seq , hd.sohoadon , hd.ngayhoadon , isnull(th.SOLUONGTRA, 0) as tongsoluong  , isnull(hd.tongsoluong,0) - isnull(soluongdatra.SOLUONGTRA, 0) as tongtra \n" +
						" from 	ERP_HOADONNCC hd \n" +
						" 		left join ERP_PARK p on p.pk_seq = hd.park_fk \n" +
						" 		left join ERP_TIENTE tt on tt.PK_SEQ = p.TIENTE_FK\n" +
						" 		left join ERP_THUENHAPKHAU_HOADONNCC th on  th.HOADONNCC_FK = hd.pk_seq  \n" +
						" 		left join (	select HOADONNCC_FK , SUM(SOLUONGTRA) as SOLUONGTRA  \n" +
						" 					from ERP_THUENHAPKHAU_HOADONNCC \n" +
						" 					where  THUENHAPKHAU_FK not in ( select 	isnull(t.PK_SEQ, 0 ) \n" +
						" 													from 	ERP_THUENHAPKHAU t  \n" +					   
						" 													where 	TRANGTHAI = 0 \n" +
//						" 														AND t.NPP_FK = "+this.nppdangnhap+" \n" +
						" 														AND t.CONGTY_FK = "+this.congtyId+" ) \n" +
						" 					group by HOADONNCC_FK) soluongdatra on soluongdatra.HOADONNCC_FK = hd.pk_seq  \n" +
						" where th.THUENHAPKHAU_FK = " + this.id  + " and hd.CONGTY_FK = "+this.congtyId+"\n" +
//						" and hd.NPP_FK = "+this.nppdangnhap+ "\n" +
						" union \n" +
						" select tt.MA as tienTe, p.TIENTE_FK, p.ncc_fk , hd.pk_seq , hd.sohoadon , hd.ngayhoadon , isnull(hd.tongsoluong,0) - isnull(soluongdatra.SOLUONGTRA, 0) as tongsoluong  , isnull(hd.tongsoluong,0) - isnull(soluongdatra.SOLUONGTRA, 0) as tongtra \n" + 
						" from 	 ERP_HOADONNCC hd  \n" +
						" 		 left join ERP_PARK p on p.pk_seq = hd.park_fk \n" +
						" 		 left join ERP_TIENTE tt on tt.PK_SEQ = p.TIENTE_FK\n" +
						" 		 left join ( select HOADONNCC_FK , SUM(SOLUONGTRA) as SOLUONGTRA  \n" +
						" 					 from 	ERP_THUENHAPKHAU_HOADONNCC  \n" +
						" 					 where 	THUENHAPKHAU_FK not in (select isnull(t.PK_SEQ, 0 )  \n" +
						" 													from ERP_THUENHAPKHAU t  		\n" +			   
						" 													where TRANGTHAI = 3 and t.CONGTY_FK = "+this.congtyId+"\n" +
//						" 														and t.NPP_FK = "+this.nppdangnhap+"" +
						") \n" +
						" 					 group by HOADONNCC_FK) soluongdatra on soluongdatra.HOADONNCC_FK = hd.pk_seq  \n" +
						" where   hd.pk_seq  in (SELECT A.HOADONNCC_FK FROM ERP_HOADONNCC_DONMUAHANG  A  " +
						"INNER JOIN ERP_MUAHANG B ON A.MUAHANG_FK=B.pk_seq AND B.LOAI='"+this.loaiMh+"') and  hd.CONGTY_FK = "+this.congtyId+" \n" +
//						" 		and hd.NPP_FK = "+this.nppdangnhap+" \n" +
						" 		and  p.ncc_fk = "+ nccId +"\n" +
						" 		and isnull(hd.tongsoluong,0) - isnull(soluongdatra.SOLUONGTRA, 0) > 0 \n" +
						" 		and hd.pk_seq not in ( select 	isnull(th.HOADONNCC_FK, 0 ) \n" +
						" 							   from 	ERP_THUENHAPKHAU t  \n" +
						" 										left join ERP_THUENHAPKHAU_HOADONNCC th on t.PK_SEQ = th.THUENhAPKHAU_fk   \n" +
						" 							    where   TRANGTHAI = 0 and t.CONGTY_FK = "+this.congtyId+"\n" +
//						"  							    	and t.NPP_FK = "+this.nppdangnhap+"\n" +
						" 							    	) and p.trangthai IN ( 1 , 2) \n";
		System.out.println("HOADONUPDATE: \n" + query + "\n-----------------------------------------------------");
		return this.db.get(query);
	}
	
	public ResultSet getSoHoaDonDisplay(){
		String query =  " select tt.MA as tienTe, tt.MA as maTienTe, p.TIENTE_FK, p.ncc_fk , hd.pk_seq , hd.sohoadon , hd.ngayhoadon , isnull(th.SOLUONGTRA, 0) as tongsoluong  \n" +
						" from ERP_HOADONNCC hd \n" +
						" left join ERP_PARK p on p.pk_seq = hd.park_fk \n" +
						" left join ERP_TIENTE tt on tt.PK_SEQ = p.TIENTE_FK\n" +
						" left join ERP_THUENHAPKHAU_HOADONNCC th on  th.HOADONNCC_FK = hd.pk_seq  \n" +
						" where hd.CONGTY_FK = "+this.congtyId+" \n" +
//						"and hd.NPP_FK = "+this.nppdangnhap+" \n" +
						"and  th.THUENHAPKHAU_FK = " + this.id ;
		System.out.println("HOADONDISPLAY: \n" + query + "\n-----------------------------------------------------");
		return this.db.get(query);
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void setRequest(HttpServletRequest request){
		this.req = request;
	}
	
	public List<IErpHoadon> getHdList() 
	{
		return this.hdList;
	}

	public void setHdList(List<IErpHoadon> hdList) 
	{
		this.hdList = hdList;
	}
	
	public String getHoadonIds() {
		return hoadonIds;
	}

	public void setHoadonIds(String hoadonIds) {
		this.hoadonIds = hoadonIds;
	}

	public String getLoaihinh() {
		return loaihinh;
	}

	public void setLoaihinh(String loaihinh) {
		this.loaihinh = loaihinh;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getCqt() 
	{
		return this.cqt;
	}

	public void setCqt(String cqt) 
	{
		this.cqt = cqt;
	}

	public String getCqtId() 
	{
		return this.cqtId;
	}

	public void setCqtId(String cqtId) 
	{
		this.cqtId = cqtId;
	}

	public String getPO() 
	{
		return this.po;
	}

	public void setPO(String po) 
	{
		this.po = po;
	}

	public String getPOId() 
	{
		return this.poId;
	}

	public void setPOId(String poId) 
	{
		this.poId = poId;
	}

	public String getPtThueNK() 
	{
		return this.ptThueNK;
	}

	public void setPtThueNK(String ptThueNK) 
	{
		this.ptThueNK = ptThueNK;
	}

	public String getThueNK() 
	{
		return this.ThueNK;
	}

	public void setThueNK(String ThueNK) 
	{
		this.ThueNK = ThueNK;
	}
	
	public String getSoHD() 
	{
		return this.soHD;
	}

	public void setSoHD(String soHD) 
	{
		this.soHD = soHD;
	}

	public String getPtVAT() 
	{
		return this.ptVAT;
	}

	public void setPtVAT(String ptVAT) 
	{
		this.ptVAT = ptVAT;
	}

	public String getVAT() 
	{
		return this.VAT;
	}

	public void setVAT(String VAT) 
	{
		this.VAT = VAT;
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getNcc() 
	{
		return this.ncc;
	}

	public void setNcc(String ncc) 
	{
		this.ncc = ncc;
	}

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccId) 
	{
		this.nccId = nccId;
	}

	public Hashtable getThuesuatHashtable() 
	{
		return this.tsht;
	}

	public void setThuesuatHashtable(Hashtable tsht) 
	{	
		this.tsht = tsht;
	}

	public Hashtable getThueHashtable() 
	{
		return this.tht;
	}

	public void setThueHashtable(Hashtable tht) 
	{
		this.tht = tht;
	}

	public String[] getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String[] spId) 
	{
		this.spId = spId;
	}

	public String[] getSoluong() 
	{
		return this.soluong;
	}

	public void setSoluong(String[] soluong) 
	{
		this.soluong = soluong;
	}

	public String[] getDongia() 
	{
		return this.dongia;
	}

	public void setDongia(String[] dongia) 
	{
		this.dongia = dongia;
	}

	public String[] getThuesuat() 
	{
		return this.thuesuat;
	}

	public void setThuesuat(String[] thuesuat) 
	{
		this.thuesuat = thuesuat;
	}

	public String[] getThue() 
	{
		return this.thue;
	}

	public void setThue(String[] thue) 
	{
		this.thue = thue;
	}

	public String getTienhang(){
		return this.tienhang;
		
	}
	
	public void setTienhang(String tienhang){
		this.tienhang = tienhang;
	}
	
	public void createRs() 
	{
//		String query = "";
	}
	

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getDiengiai() {
		
		return this.diengiai;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}

	
	public String getNgaynhap() {
		
		return this.ngaynhap;
	}

	
	public void setNgaynhap(String tungay) {
		
		this.ngaynhap = tungay;
	}

	public String getNgaychungtu() {
		
		return this.ngaychungtu;
	}

	
	public void setNgaychungtu(String ngaychungtu) {
		
		this.ngaychungtu = ngaychungtu;
	}
	
	public String getSochungtu() {
		
		return this.sochungtu;
	}

	
	public void setSochungtu(String sochungtu) {
		
		this.sochungtu = sochungtu;
	}
	
	public String getPnkId() {
		
		return this.pnkId;
	}

	
	public void setPnkId(String khId) {
		
		this.pnkId = khId;
	}

	
	public ResultSet getPhieunhapRs() {
		
		return this.pnkRs;
	}

	
	public void setPhieunhapRs(ResultSet pnRs) {
		
		this.pnkRs = pnRs;
	}

	public ResultSet getTnkRs() {
		
		return this.tnkRs;
	}

	
	public void setTnkRs(ResultSet tnkRs) {
		
		this.tnkRs = tnkRs;
	}

	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
	}


	public String getTienteId()
	{
		return this.tienteId;
	}


	public void setTienteId(String tienteId)
	{
		this.tienteId = tienteId;
	}

	public String getTigia()
	{
		return this.tigia;
	}

	public void setTigia(String tigia)
	{
		this.tigia = tigia;
	}

	public String getnppdangnhap() {
		return this.nppdangnhap;
	}

	public void setnppdangnhap(String nppdangnhap) {
	
		this.nppdangnhap = nppdangnhap;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}

	public void setMaHS(String maHS) {
		this.maHS = maHS;
	}

	public String getMaHS() {
		return maHS;
	}

	public void setSanPhamList(List<ErpSanPhamNhapKhau> sanPhamList) {
		this.sanPhamList = sanPhamList;
	}

	public List<ErpSanPhamNhapKhau> getSanPhamList() {
		return sanPhamList;
	}

	public void setMaTienTe(String maTienTe) {
		this.maTienTe = maTienTe;
	}

	public String getMaTienTe() {
		return maTienTe;
	}
	@Override
	public void setLoaiMh(String loaimh) {
		// TODO Auto-generated method stub
		this.loaiMh=loaimh;
		
	}
	@Override
	public String getLoaiMh() {
		// TODO Auto-generated method stub
		return this.loaiMh;
	}
	
}