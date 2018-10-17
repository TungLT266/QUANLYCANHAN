package geso.traphaco.erp.beans.phieuxuatkhoTH.imp;
  
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility_Kho;
import geso.traphaco.erp.beans.phieuxuatkhoTH.IErpPhieuxuatkho;
import geso.traphaco.erp.beans.phieuxuatkhoTH.ISanpham;
import geso.traphaco.erp.beans.phieuxuatkhoTH.ISpDetail;
import geso.traphaco.erp.util.CapnhatKT;
import geso.traphaco.erp.util.DinhDang;
import geso.traphaco.erp.util.Kho_Lib;
import geso.traphaco.erp.util.Library;
 




import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpPhieuxuatkho implements IErpPhieuxuatkho 
{
	String userId;
	String id;
	String ngayxuatkho;
	String ngaychotNV;
	
	String nppId,nppTen;
	ResultSet nppRs;
	
	ResultSet nppRs2;
	//String nppIds;
	
	String nccId;
	ResultSet nccRs;
	String NhomkenhId="100000";
	//String ddhId;
	ResultSet ddhRs, ddhRs2;
	String trahangNccId;
	ResultSet trahangNccRs;
	String inddhId="";
	String CongtyId;
	//String inddhIds="";
	String khoId;
	ResultSet khoRs;
	
	String ndxId;
	ResultSet ndxRs;
	
	String lydoxuat;
	String ghichu;
	String trangthai;
	boolean quanlybean;
	boolean quanlylo;
	float soluongxuat;
	List<ISanpham> spList;
	
	//pdf
	String nguoinhanhang;
	String diachinhan;
	String xuattaikho;
	String Loaixuatkho;
	String DdhId;
	ResultSet RsDondathang;
	String NhaphanphoiId;
	String ngayhoadon="";
	
	public String getNgayhoadon() {
		return ngayhoadon;
	}


	public void setNgayhoadon(String ngayhoadon) {
		this.ngayhoadon = ngayhoadon;
	}


	String msg;

	dbutils db;
	
	String maphieu ="";
	//String ddhIds;
	private ResultSet hdtcList;
	private String hdtcId;
	 String IsKhoXuatQL_Khuvuc="";
	 Utility_Kho util_kho=new Utility_Kho();
	 Utility util=new Utility();
	 NumberFormat formatter_6le = new DecimalFormat("#,###,###.######");  
	 NumberFormat formatter_3le = new DecimalFormat("#######.###");  
	public String getMaphieu() {
		return maphieu;
	}
	
	
	public ErpPhieuxuatkho()
	{
		this.userId = "";
		this.id = "";
		this.ngayxuatkho = "";
		this.ngaychotNV = "";
		this.nppId = "";
		this.nppTen="";
		this.CongtyId="";
		this.hdtcId = "";
		this.khoId = "";
		this.ndxId = "";
		this.lydoxuat = "";
		this.ghichu = "";
		this.trangthai = "";
		this.NhomkenhId="100000";
		this.msg = "";
		this.nccId = "";
		this.DdhId="";
		this.trahangNccId = "";
		this.quanlybean = false;
		this.quanlylo = false;
		this.Loaixuatkho="";
		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.xuattaikho = "";
		//this.nppIds = "";
		//this.ddhIds = "";
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
	}
	
	public ErpPhieuxuatkho(String id)
	{
		this.userId = "";
		this.id = id;
		this.ngayxuatkho = "";
		this.ngaychotNV = "";
		this.nppId = "";
		this.nppTen="";
		this.CongtyId="";
		this.khoId = "";
		this.ndxId = "";
		this.lydoxuat = "";
		this.ghichu = "";
		this.trangthai = "";
		this.DdhId="";
		this.msg = "";
		this.quanlybean = false;
		this.quanlylo = false;
		this.Loaixuatkho="";
		this.nguoinhanhang = "";
		this.diachinhan = "";
		this.xuattaikho = "";
		this.nccId = "";
		this.trahangNccId = "";
		//this.nppIds = "";
		this.hdtcId = "";
		this.spList = new ArrayList<ISanpham>();
		this.db = new dbutils();
	}
	public void setInddhId(String inddhId) {
		this.inddhId = inddhId;
	}
	public String getInddhId() {
		return inddhId;
	}
	public void setInddhIds(String inddhIds) {
		//this.inddhIds = inddhIds;
	}
	public String getInddhIds() {
		return "";
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
		this.NhaphanphoiId=util.getIdNhapp(userId);
		
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String id) 
	{
		this.id = id;
	}
	
	public String getngayxuatkho()
	{
		return this.ngayxuatkho;
	}

	public void setngayxuatkho(String ngayxuatkho) 
	{
		this.ngayxuatkho = ngayxuatkho;
	}
	
	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public String getNgayxuatkho() 
	{
		return this.ngayxuatkho;
	}

	public void setNgayxuatkho(String ngayxuatkho) 
	{
		this.ngayxuatkho = ngayxuatkho;
	}

	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppid)
	{
		this.nppId = nppid;
	}

	public ResultSet getNppList() 
	{
		return this.nppRs;
	}

	public void setNppList(ResultSet nppList)
	{
		this.nppRs = nppList;
	}

	public String getNppIds() 
	{
		return "";
	}

	public void setNppIds(String nppids)
	{
		//this.nppIds = nppids;
	}

	public ResultSet getNppList2() 
	{
		return this.nppRs2;
	}

	public void setNppList2(ResultSet nppList2)
	{
		this.nppRs2 = nppList2;
	}

	public String getKhoId() 
	{
		return this.khoId;
	}

	public void setKhoId(String khoId) 
	{
		this.khoId = khoId;
	}

	public ResultSet getKhoList()
	{
		return this.khoRs;
	}

	public void setKhoList(ResultSet kholist) 
	{
		this.khoRs = kholist;
	}

	public String getNdxId()
	{
		return this.ndxId;
	}

	public void setNdxId(String ndxId) 
	{
		this.ndxId = ndxId;
	}

	public ResultSet getNdxList()
	{
		return this.ndxRs;
	}

	public void setNdxList(ResultSet ndxList) 
	{
		this.ndxRs = ndxList;	
	}
 
	private boolean UpdateKhoChiTiet(String khoChiTietId, double soluong, double booked,double available) {
		String sql = " update ERP_KHOTT_SP_CHITIET set SOLUONG= SOLUONG + (" + soluong 
					+ ") ,BOOKED = BOOKED + (" + booked+ "),available = available + (" + available+ ") where PK_SEQ = " + khoChiTietId;
		System.out.println("sql" + sql);

		int k = this.db.updateReturnInt(sql);

		if (k != 1) {
			try {
				this.db.getConnection().rollback();
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}

		return true;
	}
	public boolean Delete(){

		try{
			this.db.getConnection().setAutoCommit(false);
			

			// thay đổi trạng thái phiếu
			String sql = "update ERP_XUATKHO set TRANGTHAI =2  where PK_SEQ="+ this.id +" and TRANGTHAI=0";
			int k = this.db.updateReturnInt(sql);
			
			if(k !=1){
				this.msg = "Cập nhật kho thất bại";
				this.db.getConnection().rollback();
				return false;
				
			}
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}catch( Exception ex){
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
			return false;
		}
	
	}
	public boolean Chot(){
		try{
			this.db.getConnection().setAutoCommit(false);
			
			// cập nhật kho
			String ketqua =Capnhat_SoLuong_CT();
			if(ketqua.trim().length()>0){
				this.msg =ketqua;
				this.db.getConnection().rollback();
				return false;
				
			}
			// thay đổi trạng thái phiếu
			String sql = "update ERP_XUATKHO set TRANGTHAI =1  where PK_SEQ="+ this.id + " and TRANGTHAI=0";
			int k = this.db.updateReturnInt(sql);
			
			if(k !=1){
				this.msg = "Cập nhật lỗi: "+sql;
				this.db.getConnection().rollback();
				return false;
				
			}
			
			
			// Đối với hóa đơn chỉ định khoản tiền thuế, xuất kho thì định khoản tiền hàng -- GHI NHAN: TIEN HANG,
			 String query =  " select NGAYXUAT from ERP_XUATKHO where PK_SEQ="+this.id;
			 ResultSet checkNpp = db.get(query);
			 String ngayghinhan = "";
			 if(checkNpp !=null){
				 if(checkNpp.next()){
					 ngayghinhan = checkNpp.getString("NGAYXUAT");
				 }
				 checkNpp.close();
			 }
			CapnhatKT	KT;
			geso.traphaco.distributor.util.Utility	Util = new geso.traphaco.distributor.util.Utility();
			String nam =ngayghinhan.substring(0, 4);
			String thang = ngayghinhan.substring(5, 7);
			String loaichungtu="Xuất kho trả hàng";
			
			
			
			
			query="delete erp_phatsinhketoan where loaichungtu=N'"+loaichungtu+"' and SOCHUNGTU="+this.id+" ";
			if(!db.update(query)){
				db.getConnection().rollback();
				System.out.println("___LOI CHOT XUAT KHO TRA VE NCC: " + query);
				this.msg = "Khong The Chot Xuat Kho ,Loi Dong Lenh : "+ query;
				return false;
			}
			query="delete erp_phatsinhketoan where loaichungtu=N'Xuất kho trả hàng' and SOCHUNGTU="+this.id+" ";
			if(!db.update(query)){
				db.getConnection().rollback();
				System.out.println("___LOI CHOT XUAT KHO TRA VE NCC:" + query);
				this.msg = "Khong The Chot Xuat Kho ,Loi Dong Lenh : "+ query;
				return false;
			}
			
			
		
			query ="  SELECT  MH.TIENTE_FK,TT.MA AS MATIENTE, N'Nhà cung cấp' as doituongNCC, a.NCC_FK as madoituongNCC, \n " +
				   " (SELECT TAIKHOAN_FK FROM ERP_NHACUNGCAP  WHERE PK_SEQ = a.NCC_FK) as taiKhoanNCC, \n " +
				   " (SELECT pk_seq FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = lsp.taikhoankt_fk and congty_fk=a.congty_fk ) as taiKhoanCO_TIENHANG, \n " +
				   " hd_SP.TIENBVAT  as TienHang " +
				   " FROM   ERP_HOADON a INNER JOIN ERP_NHACUNGCAP b on a.NCC_FK = b.PK_SEQ \n " +
				   " INNER JOIN ERP_HOADON_SP hd_SP ON hd_SP.HOADON_FK = A.PK_SEQ \n" +
				   " inner join erp_sanpham sp on sp.pk_seq = hd_SP.sanpham_fk \n" +
				   " inner join erp_loaisanpham lsp on lsp.pk_seq = sp.loaisanpham_fk \n" +
				   "  INNER JOIN ERP_HOADON_DDH HDDH ON HDDH.HOADON_FK= A.PK_SEQ \n" + 
				   "  inner join ERP_MUAHANG MH ON MH.PK_SEQ=HDDH.HOADON_FK \n" + 
				   "  LEFT JOIN ERP_TIENTE TT ON TT.PK_SEQ=MH.TIENTE_FK \n" + 
				   " WHERE  a.TRANGTHAI<>2 and  a.PK_SEQ in (select HOADON_FK from ERP_HOADON_DDH where DDH_FK= (select TRAHANGNCC_FK from ERP_XUATKHO where PK_SEQ='"+ this.id+"'))";
			System.out.println("lay tien hang dinh khoan hoa don: "+ query );	
			ResultSet rsDoanhSo = db.get(query);
			while(rsDoanhSo.next())
			{	
				String taikhoanktCo = "";
				String taikhoanktNo = "";
				
				String doituong_no = "";
				String madoituong_no = "";
				String doituong_co = "";
				String madoituong_co = ""; 
				
				String tiente_fk=rsDoanhSo.getString("tiente_fk");
				 
				taikhoanktNo = rsDoanhSo.getString("taiKhoanNCC");
				double tienhang =rsDoanhSo.getDouble("TIENHANG");
				if(tienhang > 0)
				{
					doituong_no = rsDoanhSo.getString("doituongNCC");
					madoituong_no = rsDoanhSo.getString("madoituongNCC");
					taikhoanktCo = rsDoanhSo.getString("taiKhoanCO_TIENHANG");
				
					if(taikhoanktCo.trim().length() <= 0 || taikhoanktNo.trim().length() <= 0)
					{
						rsDoanhSo.close();
						this.msg = "Nhà cung cấp và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						this.db.getConnection().rollback();
						return false;
					}
					
					KT=new CapnhatKT();
					KT.setSochungtu(this.id);
				 	KT.setNOIDUNGNHAPXUAT_FK(""); 
					String spid = "";
					KT.setSpId(spid);
					 
					double soluong = 0;
					// lấy quy đổi ra chuẩn của sản phẩm nhập kho, về chuẩn mới lấy được số lượng 
					 
					KT.setSOLUONG("0");;
					
					double dongia=0;
					
					KT.setDONGIA("0");
					
					String thanhtien =""+ Math.round(tienhang);
					
					
				 
					KT.setNam(nam);
					KT.setThang(thang);
				 
					 
					KT.setTaikhoanCO_fk(taikhoanktCo);
					KT.setTaikhoanNO_fk(taikhoanktNo);
			 
				 
					KT.setDOITUONG_CO(doituong_co);
					KT.setDOITUONG_NO(doituong_no);
					KT.setMADOITUONG_CO(madoituong_co);
					KT.setMADOITUONG_NO(madoituong_no);
					KT.setTIGIA_FKl("1");
					KT.setDONGIANT("0");
				  
					KT.setTIENTEGOC_FK(tiente_fk);
					
					double  dongiaViet = dongia;
					
					KT.setDONGIA(dongiaViet+"");
					  
					KT.setNO(thanhtien+"");
					KT.setCO(thanhtien+"");
					KT.setTONGGIATRI(thanhtien+"");
					
					KT.setChiPhiId("NULL");
					KT.setKhoNhanID("NULL");
					KT.setMasp("");
					KT.setTensp("");
					KT.setDonvi("");
					KT.setLoaichungtu(loaichungtu);
					
					KT.setNgaychotnv(ngayghinhan);
					KT.setNgaychungtu(ngayghinhan);
					KT.setNgayghinhan(ngayghinhan);
				 
					KT.setKhoanmuc("Xuất trả NCC");
					//System.out.println("THANHTIEN : "+thanhtien);

					String msg1=KT.CapNhatKeToan_Kho(Util, db);
					//System.out.println("thông bao loi : "+msg1);
					if(msg1.length()> 0){
						this.msg = msg1;
						this.db.getConnection().rollback();
						return false;
						
					}
					 
				}
				
			 
				
			}
			rsDoanhSo.close();
			//------------------------------------------
			
			
			
			Library lib =new Library();
			String msg1=lib.CapNhatKeToanKho_XuatKho(userId, db, this.id, false, this.CongtyId);
			if(msg1.length()>0){
				this.msg =msg1;
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}catch( Exception ex){
			try {
				this.db.getConnection().rollback();
			} catch (SQLException e) {
				e.printStackTrace();
			}
			ex.printStackTrace();
			return false;
		}
	}
	
	private boolean checkDuLieuDauVao(){
		try{
			String msg1 = util.checkNgayHopLe(this.db, this.ngayxuatkho);
			if (msg1.length() > 0) {
				this.msg = msg1;
				return false;
			}
			
			if( this.ngayxuatkho.compareTo(this.ngayhoadon)<0){
				this.msg = "Vui lòng chọn ngày xuất kho lớn hơn hoặc bằng ngày hóa đơn !";
				return false;
			}

			if (this.spList.size() > 0) {
				for (int i = 0; i < this.spList.size(); i++) {
					ISanpham sp = this.spList.get(i);
					List<ISpDetail> spDetail = sp.getSpDetailList();

					double sum = 0;
					for (int j = 0; j < spDetail.size(); j++) {
						sum += DinhDang.dinhdangso(Double.parseDouble(spDetail.get(j).getSoluong()));
					}
					if (DinhDang.dinhdangso(sum) > DinhDang.dinhdangso(Double.parseDouble(sp.getSoluongTotal())) - DinhDang.dinhdangso(Double.parseDouble(sp.getSoluongDaXuat())) ) {
						this.msg += "+ Sản phẩm " + sp.getMa() + " - " + sp.getDiengiai()
								+ ", không  được vượt số lượng của đơn hàng/ trả hàng, vui lòng kiểm tra lại \n";
					}
				}
			}

			if (this.msg.length() > 0) {
				this.msg = "Vui lòng kiểm tra lại các thông tin sau trước khi xuất kho: \n" + this.msg;
				System.out.println(this.msg);
				return false;
			} else {
				if (this.spList.size() <= 0) {
					this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong đơn mua hàng trước khi xuất kho: \n" + this.msg;
					System.out.println(this.msg);
					return false;
				}
			}
			if (this.Loaixuatkho.equals("DH") && this.DdhId.length() > 0) {
				msg1 = this.check_daxuatkho();
				if (msg1.length() > 0) {
					this.msg = msg1;
					return false;
				}
			}
			return true;
		}catch( Exception ex){
			ex.printStackTrace();
			return false;
		}
	}
	public boolean createPxk(){
		
		// lay ngay hoa don 
		String qrngay="select B.NGAYXUATHD from ERP_HOADON_DDH C INNER JOIN ERP_HOADON B ON C.HOADON_FK= B.PK_SEQ WHERE DDH_FK="+ this.trahangNccId;
		ResultSet rsngay=db.get(qrngay);
		try {
			if(rsngay.next()){
				this.ngayhoadon= rsngay.getString("NGAYXUATHD");
			}
			rsngay.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		// check dữ liệu đầu vào
		boolean b = checkDuLieuDauVao();
		if(b == false)
			return false;

		// bắt đầu insert dữ liệu
		try {
			db.getConnection().setAutoCommit(false);

			if (this.ndxId.trim().length() > 0 && this.ndxId.equals("100062")) {// XUẤT
																				// KHO
																				// CHO
																				// ĐƠN
																				// TRẢ
																				// HÀNG
																				// NCC
				String query = "";
				if (this.trahangNccId != null && this.trahangNccId.length() > 0) {
					query = " SELECT a.KHOTT_FK,KHO.TEN \n" + " FROM 	 ERP_MUAHANG_SP a  \n"
							+ " 		 INNER JOIN ERP_KHOTT KHO ON  a.KHOTT_FK = KHO.PK_SEQ   \n" + " WHERE  a.MUAHANG_FK =  " + this.trahangNccId
							+ " AND a.KHOTT_FK= " + this.khoId;

					ResultSet rsCheck = db.get(query);

					if (!rsCheck.next()) {
						db.getConnection().rollback();
						this.msg = "Kho xuất phải là kho trả của nhà phân phối khi làm đơn trả hàng.  ";
						return false;
					}

				} else {
					this.msg = "Không có đơn trả hàng NCC nào được chọn";
					return false;
				}
				String ngaytao = this.getDateTime();

				query = " insert ERP_XUATKHO(NHOMKENH_FK,NPP_ID,CONGTY_FK,NGAYXUAT, NGAYCHOT, TRAHANGNCC_FK, NOIDUNGXUAT, KHO_FK, LYDOXUAT," +
						" GHICHU, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, NPP_FK ) "
						+ " values(" + this.NhomkenhId
						+ ","
						+ this.NhaphanphoiId
						+ ","
						+ this.CongtyId
						+ ",'"
						+ this.ngayxuatkho
						+ "', '"
						+ this.ngayxuatkho
						+ "', "
						+ this.trahangNccId
						+ ", '"
						+ this.ndxId
						+ "', '"
						+ this.khoId
						+ "', N'"
						+ this.lydoxuat
						+ "', N'"
						+ this.ghichu
						+ "', " + "'" + this.userId + "', '" + this.userId + "', '" + ngaytao + "', '" + ngaytao + "', '0', " + this.nccId + ")";

				if (!db.update(query)) {
					this.msg = "Khong the tao moi phieu xuat kho: " + query;
					System.out.println("Loi: " + query);
					db.getConnection().rollback();
					return false;
				}

				String xkCurrent = "";
				query = "select SCOPE_IDENTITY() as xkId";
				ResultSet rsPxk = db.get(query);
				if (rsPxk.next()) {
					xkCurrent = rsPxk.getString("xkId");
					rsPxk.close();
				}

				boolean QlKhuvucKho = false;
				query = "select * from ERP_KHOTT WHERE QUANLYBIN = 1 AND PK_SEQ = '" + this.khoId + "'";

				ResultSet rs = db.get(query);
				try {
					if (rs.next()) {
						QlKhuvucKho = true;
					}
				} catch (Exception err) {
					this.msg = err.getMessage();
					err.printStackTrace();
				}

				query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '" + this.khoId + "' and TRANGTHAI = 1";
				if (QlKhuvucKho) {
					boolean tmp = false;
					rs = db.get(query);
					try {
						if (rs.next()) {
							tmp = true;
						}
					} catch (Exception err) {
						this.msg = err.getMessage();
						err.printStackTrace();
					}
					if (!tmp) {
						this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
						QlKhuvucKho = false;
					}
				}

				int i = 0;
				if (this.spList.size() > 0) {
					for (i = 0; i < this.spList.size(); i++) {
						ISanpham sp = this.spList.get(i);
						query = "insert ERP_XUATKHO_SANPHAM(XUATKHO_FK, SANPHAM_FK, SOLUONG) " + "values('" + xkCurrent + "', '" + sp.getId()
								+ "', '" + sp.getSoluong() + "')";

						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_XUATKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}

						List<ISpDetail> spCon = sp.getSpDetailList();
						for (int j = 0; j < spCon.size(); j++) {
							ISpDetail detail = spCon.get(j);
							if (!QlKhuvucKho) {
								detail.setKhuId("NULL");
							}
							double soluongct = 0;
							try {
								soluongct = Double.parseDouble(detail.getSoluong());
							} catch (Exception er) {
								er.printStackTrace();
								this.msg = "Số lượng trong lô chi tiết của sản phẩm : " + sp.getTen() + " không hợp lệ";
								db.getConnection().rollback();
								return false;
							}
							if (soluongct > 0) {

								if(detail.getIdmarquette().trim().length() == 0){
									detail.setIdmarquette("NULL");
								}
								query = " insert ERP_XUATKHO_SP_CHITIET(XUATKHO_FK, SANPHAM_FK, BIN_FK, SOLO, SOLUONG," +
										"  NGAYHETHAN, MAME, MATHUNG,MARQ, KHOCHITIET_FK,PHIEUEO,MAPHIEUDINHTINH,MAPHIEU,NGAYNHAPKHO,HAMLUONG,HAMAM,NSX_FK) "
										+ " values('"
										+ xkCurrent+ "', '"+ detail.getId()+ "', "+ (detail.getKhuId().length() > 0 ? detail.getKhuId() : "0")
										+ ", N'"+ detail.getSolo()+ "', '"+ detail.getSoluong()+ "'  ,'"+ detail.getNgayhethan() + "','"+detail.getMame()+"','"+detail.getMathung()
										+ "', '"+detail.getMamarquette()+"',"+detail.getKhoChiTietId()+",'"+detail.getPhieuEO()+"','"+detail.getMaphieudinhtinh()+"','"+detail.getMaphieu()+"','"+detail.getNgaynhapkho()+"','"+detail.getHamluong()+"','"+detail.getHamam()+"',"+(detail.getIdnsx().trim().length()==0?"NULL":detail.getIdnsx())+")";

								if (!db.update(query)) {
									this.msg = "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}

								
								 
							}
						}
					}
				}

			}
			if (this.checkXuatkhovuotHd()) {
				db.getConnection().rollback();
				return false;

			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} catch (Exception e) {
			db.update("rollback");

			e.printStackTrace();
			this.msg = "Loi khi luu phieu XK";
			return false;
		}

	}
	private boolean BookedKho(String khoChiTietId, double booked, double available ){
		String sql =  " update ERP_KHOTT_SP_CHITIET set BOOKED= BOOKED + ("+ booked 
					+ ") , AVAILABLE = AVAILABLE + ("+available + ") where PK_SEQ = "+ khoChiTietId;
		System.out.println("sql"+ sql);
		
		int k = this.db.updateReturnInt(sql);
		System.out.println("k:"+k);
		
		if(k != 1){
			try {
				this.db.getConnection().rollback();
				return false;
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		return true;
	}

	private String check_daxuatkho() {
		// TODO Auto-generated method stub
		String msg1="";
		try{
			
		String	query=" SELECT PK_SEQ FROM ERP_XUATKHO WHERE  trangthai <> 2  and DONDATHANG_FK="+this.DdhId+" " +
					  "  AND NOIDUNGXUAT="+this.ndxId+" AND KHO_FK ="+this.khoId+" AND PK_SEQ <> "+(this.id.length()>0?this.id:"0" )+"      AND TRANGTHAI <> 2";
			ResultSet rs=db.get(query);
			
			if(rs.next()){
				msg1=" Đã tồn tại xuất kho số : "+rs.getString("PK_SEQ") +". Cho đơn đặt hàng "+this.DdhId+" theo kho và nội dung xuất bạn đang thực hiện";
			}
			
			rs.close();
			
		 
		}catch(Exception er){
			msg1= er.getMessage();
		}
		
		return msg1;
	}


	private boolean checkXuatkhovuotHd() {
		try{
		// TODO Auto-generated method stub
			if(this.trahangNccId!=null && this.trahangNccId.length()>0){
				// trường hợp trả hàng nhà cung cấp
				
			}
			
			boolean bien=false;
			if(hdtcId!=null && hdtcId.length() > 0){
				
		String sql= "  SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, " +  
					"  ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT       " +  
					"  FROM     " +  
					"  (     " +  
					"  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					"  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					"  FROM ERP_HOADON_SP HDSP      " +  
					"  INNER JOIN ERP_HOADON HD ON HD.PK_SEQ =HDSP.HOADON_FK       " +  
					"  INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					"  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					"  WHERE HDSP.HOADON_FK =   " +this.hdtcId+   
					"  AND HDSP.SOLUONG > 0     " +  
					"  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					"   " +  
					"  )     " +  
					"  HOA_DON LEFT JOIN     " +  
					"  (     " +  
					"  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					"  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					"  WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.HOADON_FK IN ( "+this.hdtcId+")      " +  
					"  GROUP BY A.SANPHAM_FK     " +  
					"  ) XUATKHO on XUATKHO.SANPHAM_FK=HOA_DON.SPID " +  
					"  where XUATKHO.TOTALXUAT >HOA_DON.SOLUONG ";
			ResultSet rs=db.get(sql);
			
			if(rs.next()){
				bien=true;
				this.msg="Tổng số lượng xuất trong các phiếu xuất kho đã vượt số lượng trong hóa đơn, bạn không thể lưu phiếu này. ";
			}
			rs.close();
			
			}else if(this.DdhId!=null && this.DdhId.length() >0){
				String sql="";
				if(!this.ndxId.equals("100008")){
					// xuất hàng bán
					sql=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD "+
					   "  FROM     " +  
					   "  (     " +  
					   "  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					   "  FROM ERP_DONDATHANG_SP HDSP      " +  
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANG_FK       " +  
					   "  INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					   "  WHERE HDSP.DONDATHANG_FK =  "+this.DdhId+"   " +  
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = "+this.khoId+"  " +  
					   "  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					   "   " +  
					   "  )     " +  
					   "  HOA_DON LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.DONDATHANG_FK IN ("+DdhId+" ) AND B.NOIDUNGXUAT="+this.ndxId+"   " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  )     " +  
					   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK     " +  
					   
					   "  where  XUATKHO.TOTALXUAT <> ISNULL( HOA_DON.SOLUONG,0) ";
					
				}else if(this.ndxId.equals("100008")){
					// Hàng khuyên mãi
					sql=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD "+
					   "  FROM     " +  
					   "  (     " +  
					 
					   "  SELECT SP.PK_SEQ AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG " +    
					   "  FROM ERP_DONDATHANG_CTKM_TRAKM HDSP  " +    
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANGID " +      
					   "  INNER JOIN SANPHAM SP ON HDSP.SPMA = SP.MA  " +    
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK= SP.PK_SEQ  " +   
					   "  WHERE HDSP.DONDATHANGID =  " +this.DdhId+ 
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = " +this.khoId +
					   "  GROUP BY SP.PK_SEQ, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK       " +  
					   " union all "+
					   "  SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,     " +  
					   "  CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG1/QC.SOLUONG2)  END AS SOLUONG     " +  
					   "  FROM ERP_DONDATHANG_SP HDSP      " +  
					   "  INNER JOIN ERP_DONDATHANG HD ON HD.PK_SEQ =HDSP.DONDATHANG_FK       " +  
					   "  INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ       " +  
					   "  LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK    " +  
					   "  WHERE HDSP.DONDATHANG_FK =  "+this.DdhId+"   " +  
					   "  AND HDSP.SOLUONG > 0 AND HDSP.KHOTT_FK = "+this.khoId+"  AND HD.LOAIDONHANG='6' " +  
					   "  GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK    " +  
					   
					   "   " +  
					   "  )     " +  
					   "  HOA_DON LEFT JOIN     " +  
					   "  (     " +  
					   "  SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT     " +  
					   "  FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ     " +  
					   "  WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.DONDATHANG_FK IN ("+DdhId+" ) AND B.NOIDUNGXUAT="+this.ndxId+"  " +  
					   "  GROUP BY A.SANPHAM_FK     " +  
					   "  )     " +  
					   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK     " +  
					   
					   "  where XUATKHO.TOTALXUAT <> ISNULL(HOA_DON.SOLUONG,0) ";
					
				}
				ResultSet rs=db.get(sql);
				
				if(rs.next()){
					bien=true;
					 if(this.DdhId!=null && this.DdhId.length() >0){
						 this.msg="Số lượng xuất trong phiếu xuất kho phải bằng số lượng bên đơn bán hàng.";
					 }else{
						 this.msg="Tổng số lượng xuất trong các phiếu xuất kho đã vượt số lượng trong đơn hàng, bạn không thể lưu phiếu này. ";
					 }
				}
			}
			return bien;
			
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return true;
		}
	}
 
	private String Capnhat_SoLuong_CT(){
		try{
			
			String query= " SELECT A.NSX_FK,A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN  " +
						  ",A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH, " +
						  " ISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG " +
						  " ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK  "+
						  " FROM ERP_XUATKHO_SP_CHITIET A INNER JOIN ERP_XUATKHO B ON B.PK_SEQ=A.XUATKHO_FK "+
						  " WHERE b.trangthai='0' and B.PK_SEQ= "+this.id;
			ResultSet rs=db.get(query);
			while(rs.next()){
				double booked =(-1) * rs.getDouble("SOLUONG");
				double soluong =(-1) *  rs.getDouble("SOLUONG");;
					
				Kho_Lib kholib=new Kho_Lib();
				kholib.setNgaychungtu(this.ngayxuatkho);
				kholib.setLoaichungtu("ErpPhieuxuatkho.java 392  ErpXuatkho :"+this.id);
				kholib.setKhottId(rs.getString("KHO_FK"));
				kholib.setBinId(rs.getString("BIN_FK"));
				kholib.setSolo(rs.getString("SOLO"));
				kholib.setSanphamId(rs.getString("SANPHAM_FK"));
				kholib.setNsxId(rs.getString("NSX_FK")==null?"":rs.getString("NSX_FK"));
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("MATHUNG"));
				
				kholib.setMaphieu(rs.getString("MAPHIEU"));
				kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
				kholib.setPhieuEo(rs.getString("PHIEUEO"));
				kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
			 
				kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
				kholib.setBooked( booked);
				
				kholib.setSoluong(soluong);
				kholib.setAvailable(0);
				
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				 
				kholib.setHamluong(rs.getString("HAMLUONG"));
				kholib.setHamam(rs.getString("HAMAM"));
				kholib.setNgaysanxuat("");
				
				String msg1= util_kho.Update_Kho_Sp_Tra_NEW(db,kholib);
			    if( msg1.length() >0)
				{
			    	this.msg = msg1;
			    	System.out.println(" cau loi kho : "+ this.msg);
					db.getConnection().rollback();
					return  this.msg;
				}
			}
			
			return "";
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return this.msg;
		}
	}
	// trả booked lại cho kho. xảy tra trong trường hợp update.
	
	private boolean revertBook(){ 
		try{
			
			String query= " SELECT A.MARQ,A.SANPHAM_FK, A.SOLO,B.KHO_FK,A.SOLUONG,A.MARQ,A.MATHUNG,A.MAME,A.NGAYHETHAN  " +
						  ",A.NGAYNHAPKHO ,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH, " +
						  " ISNULL(A.PHIEUEO,'') AS PHIEUEO , ISNULL(A.HAMLUONG,100) AS HAMLUONG " +
						  " ,ISNULL(A.HAMAM,0) AS HAMAM ,ISNULL(A.BIN_FK,0) AS BIN_FK  "+
						  " FROM ERP_XUATKHO_SP_CHITIET A INNER JOIN ERP_XUATKHO B ON B.PK_SEQ=A.XUATKHO_FK "+
						  " WHERE b.trangthai='0' and B.PK_SEQ= "+this.id;
			ResultSet rs=db.get(query);
			while(rs.next()){
				double booked =(-1) * rs.getDouble("SOLUONG");
				double soluong = 0;
				double available =   rs.getDouble("SOLUONG");
					
				Kho_Lib kholib=new Kho_Lib();
				kholib.setNgaychungtu(this.ngayxuatkho);
				kholib.setLoaichungtu("ErpPhieuxuatkho.java 392  ErpXuatkho :"+this.id);
				kholib.setKhottId(rs.getString("KHO_FK"));
				kholib.setBinId(rs.getString("BIN_FK"));
				kholib.setSolo(rs.getString("SOLO"));
				kholib.setSanphamId(rs.getString("SANPHAM_FK"));
				
				kholib.setMame(rs.getString("MAME"));
				kholib.setMathung(rs.getString("MATHUNG"));
				
				kholib.setMaphieu(rs.getString("MAPHIEU"));
				kholib.setMaphieudinhtinh(rs.getString("MAPHIEUDINHTINH"));
				kholib.setPhieuEo(rs.getString("PHIEUEO"));
				kholib.setNgayhethan(rs.getString("NGAYHETHAN"));
			 
				kholib.setNgaynhapkho(rs.getString("NGAYNHAPKHO"));
				kholib.setBooked( booked);
				kholib.setSoluong(soluong);
				kholib.setAvailable(available);
				
				kholib.setMARQ(rs.getString("MARQ"));
				kholib.setDoituongId("");
				kholib.setLoaidoituong("");
				 
				kholib.setHamluong(rs.getString("HAMLUONG"));
				kholib.setHamam(rs.getString("HAMAM"));
				kholib.setNgaysanxuat("");
				
				String msg1= util_kho.Update_Kho_Sp_ChiTiet_Tra(db,kholib);
				
				
			    if( msg1.length() >0)
				{
			    	this.msg = msg1;
					db.getConnection().rollback();
					return  false;
				}
			}
			
			return true;
		}catch(Exception er){
			er.printStackTrace();
			this.msg=er.getMessage();
			return false;
		}
	}
	
	
	public static void main(String args[]){  
		   String s1="2017-12-09";  
		   String s2="2017-12-08";  
		   System.out.println(s1.compareTo(s2));//0  
		   
		 }  
	
	public boolean updatePxk() 
	{
			String msg1=util.checkNgayHopLe(this.db, this.ngayxuatkho);
			if(msg1.length() > 0)
			{
				this.msg = msg1;
				return false;
			}
			
			
			System.out.println(" ngay xuat kho :"+ this.ngayxuatkho);
			System.out.println(" ngay hoa don :"+ this.ngayhoadon);
			
			if( this.ngayxuatkho.compareTo(this.ngayhoadon)<0){
				this.msg = "Vui lòng chọn ngày xuất kho lớn hơn hoặc bằng ngày hóa đơn !";
				return false;
			}
			
			
		
			if(this.spList.size() > 0)
			{
				for(int i = 0; i < this.spList.size(); i++)
				{
					ISanpham sp = this.spList.get(i);
					List<ISpDetail> spDetail = sp.getSpDetailList();
					
					double sum = 0;
					for(int j = 0; j < spDetail.size(); j++)
					{
						sum += DinhDang.dinhdangso(Double.parseDouble(spDetail.get(j).getSoluong()));
					}
					
					if(DinhDang.dinhdangso(sum) != DinhDang.dinhdangso(Double.parseDouble(sp.getSoluong())))
					{
						this.msg += " + Sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() + ", không đủ số lượng để xuất kho, vui lòng kiểm tra lại \n";
					}
				}
			}
			
			if(this.msg.length() > 0)
			{
				this.msg = "Vui lòng kiểm tra lại các thông tin sau trước khi xuất kho: \n" + this.msg;
				System.out.println(this.msg);
				return false;
			}
	 
		else
		{
			if(this.spList.size() <= 0)
			{
				this.msg = "Vui lòng kiểm tra lại thông tin sản phẩm trong đơn mua hàng trước khi xuất kho: \n" + this.msg;
				return false;
			}
		}
			if(this.Loaixuatkho.equals("DH") && this.DdhId.length() >0){
				  msg1=this.check_daxuatkho();
					if(msg1.length() >0){
						this.msg=msg1;
						return false;
					}
			}
		try 
		{
			db.getConnection().setAutoCommit(false);
			if(this.ndxId.trim().length()>0&& this.ndxId.equals("100062")){//XUẤT KHO CHO ĐƠN TRẢ HÀNG NCC
				String query = 	"";
				if(this.trahangNccId != null && this.trahangNccId.length()>0){
					query = " SELECT a.KHOTT_FK,KHO.TEN \n" +
							" FROM 	 ERP_MUAHANG_SP a  \n"+  
			  				" INNER JOIN ERP_KHOTT KHO ON  a.KHOTT_FK = KHO.PK_SEQ   \n"+
			  				" WHERE  a.MUAHANG_FK =  "+this.trahangNccId+" AND a.KHOTT_FK= "+this.khoId;
 
					ResultSet rsCheck = db.get(query);
					if(!rsCheck.next())
					{
						db.getConnection().rollback();
						this.msg = "Kho xuất phải là kho trả của nhà phân phối khi làm đơn trả hàng.  ";
						return false;
					}
					
				}
				else
				{	 
					this.msg = "Không có đơn trả hàng NCC nào được chọn";
					return false;
				}
				
//				String ngaytao = this.getDateTime();
				
				boolean QlKhuvucKho = false;
				QlKhuvucKho=util_kho.getIsQuanLyKhuVuc(this.khoId, db).equals("1");
				
				  query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
				
				if(QlKhuvucKho){
					boolean tmp = false;
					ResultSet	rs = db.get(query);
					try{
						if(rs.next()){
							tmp = true;
						}
					}catch(Exception err){
						this.msg=err.getMessage();
						err.printStackTrace();
					}
					if(!tmp){
						this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
						QlKhuvucKho = false;
					}
				}
				
				
				//TRẢ LẠI BOOK
				/*boolean check = this.revertBook();
				if(check == false){
					 
					this.db.getConnection().rollback();
					return false;
					
				}*/
				 
				//CẬP NHẬT LẠI THÔNG TIN
				query = 
				" UPDATE ERP_XUATKHO set NHOMKENH_FK="+this.NhomkenhId+",CONGTY_FK="+this.CongtyId+",NPP_ID="+this.NhaphanphoiId+", NGAYXUAT = '" + this.ngayxuatkho + "', " +
				" NGAYCHOT = '" + this.ngaychotNV +"', " +
				" TRAHANGNCC_FK = " + this.trahangNccId + ", NOIDUNGXUAT = '" + this.ndxId + "', " +
				" LYDOXUAT = N'" + this.lydoxuat + "', GHICHU = N'" + this.ghichu + "', NGUOISUA = '" + this.userId + "', " +
				" NGAYSUA = '" + this.getDateTime() + "', TRANGTHAI = '0', " +
				" NPP_FK = '"+this.nccId+"'"+
				" WHERE pk_seq = '" + this.id + "'";
	 				 
				if(!db.update(query))
				{
					this.msg = "Không thể cập nhật phiếu xuất kho : " + query;
					db.getConnection().rollback();
					return false;
				}
				
				//XÓA 
				query="delete ERP_XUATKHO_SANPHAM where xuatkho_fk = '" + this.id + "'";
				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_XUATKHO_SANPHAM: " + query;
					db.getConnection().rollback();
					return false;
				}
				query="delete ERP_XUATKHO_SP_CHITIET where xuatkho_fk = '" + this.id + "'";
				if (!db.update(query)) {
					this.msg = "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
					db.getConnection().rollback();
					return false;
				}
			 
 
				int i = 0;
				if (this.spList.size() > 0) {
					for (i = 0; i < this.spList.size(); i++) {
						ISanpham sp = this.spList.get(i);
						query = "insert ERP_XUATKHO_SANPHAM(XUATKHO_FK, SANPHAM_FK, SOLUONG) " + "values('" + this.id + "', '" + sp.getId()
								+ "', '" + sp.getSoluong() + "')";
						
						if (!db.update(query)) {
							this.msg = "Khong the tao moi ERP_XUATKHO_SANPHAM: " + query;
							db.getConnection().rollback();
							return false;
						}
						
						List<ISpDetail> spCon = sp.getSpDetailList();
						for (int j = 0; j < spCon.size(); j++) { 
							ISpDetail detail = spCon.get(j);
							if (!QlKhuvucKho) {
								detail.setKhuId("NULL");
							}
							double soluongct = 0;
							try {
								soluongct = Double.parseDouble(detail.getSoluong());
							} catch (Exception er) {
								er.printStackTrace();
								this.msg = "Số lượng trong lô chi tiết của sản phẩm : " + sp.getTen() + " không hợp lệ";
								db.getConnection().rollback();
								return false;
							}
							if (soluongct > 0) {

								if(detail.getIdmarquette().trim().length() == 0){
									detail.setIdmarquette("NULL");
								}
								query = " insert ERP_XUATKHO_SP_CHITIET(XUATKHO_FK, SANPHAM_FK, BIN_FK, SOLO, SOLUONG," +
										"  NGAYHETHAN, MAME, MATHUNG,MARQ, KHOCHITIET_FK,PHIEUEO,MAPHIEUDINHTINH,MAPHIEU,NGAYNHAPKHO,HAMLUONG,HAMAM,NSX_FK) "
										+ " values('"
										+ this.id+ "', '"+ detail.getId()+ "', "+ (detail.getKhuId().length() > 0 ? detail.getKhuId() : "0")
										+ ", N'"+ detail.getSolo()+ "', '"+ detail.getSoluong()+ "'  ,'"+ detail.getNgayhethan() + "','"+detail.getMame()+"','"+detail.getMathung()
										+ "', '"+detail.getMamarquette()+"',"+detail.getKhoChiTietId()+",'"+detail.getPhieuEO()+"','"+detail.getMaphieudinhtinh()+"','"+detail.getMaphieu()+"','"+detail.getNgaynhapkho()+"','"+detail.getHamluong()+"','"+detail.getHamam()+"',"+(detail.getIdnsx().trim().length()==0?"NULL":detail.getIdnsx())+")";

								if (!db.update(query)) {
									this.msg = "Khong the tao moi ERP_XUATKHO_SP_CHITIET: " + query;
									db.getConnection().rollback();
									return false;
								}

								
								
								 
							}
						}
					}
				}

			}
			 
			if(this.checkXuatkhovuotHd()){
				db.getConnection().rollback();
				return false;
				
			}	
			
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				return true;
		} 
		catch (Exception  e)
		{ 
			e.printStackTrace();
			db.update("rollback");
			this.msg = e.getMessage();
			return false;
		}
	}
	
	public void createRs()
	{
		String query ;
		

		// lấy thông tin NCC
		query = "select PK_SEQ as nccId, ma + ', ' + ten as nccTen from ERP_NHACUNGCAP where trangthai = '1' and duyet = '1' order by ma asc";
		System.out.println("Khoi tao NCC: " + query);
		this.nccRs = this.db.get(query);
		
		// lấy thông tin NDX
		this.ndxRs = db.get(" select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP " +
							" where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('XK') ");
 
		if(this.nccId!=null&& this.nccId.length()>0)
		{
			
			// Lấy thông tin Rs của trả hàng
			query = " select PK_SEQ as dmhId, cast(PK_SEQ as nvarchar(10)) + ' _ ngay tra ' + NGAYMUA as dmhTen " +
					" from ERP_MUAHANG A where TYPE = '2' and TRANGTHAI  in ('1','2','3') and NHACUNGCAP_FK = '" + this.nccId + "' "
				  + " and exists ( SELECT PK_SEQ FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HD_DDH ON  HD.PK_SEQ=HD_DDH.HOADON_FK "
				  + " WHERE HD.TRANGTHAI=1 AND HD_DDH.DDH_FK=A.PK_SEQ ) ";
	
			if(this.id!=null&& this.id.length()>0)
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1) and pk_seq != '" + this.id + "' )";
			else
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1))";
						
			this.trahangNccRs = db.get(query);
			
			// Lấy thông tin Kho của trả hàng
			if(this.trahangNccId.length()>0){
				query = "select distinct a.PK_SEQ, a.TEN from ERP_KHOTT a inner join erp_muahang_sp b on a.PK_SEQ = b.KHOTT_FK where b.muahang_fk ="+this.trahangNccId+"";
				this.khoRs = db.get(query);
				
				if(this.khoId.equals("")){
					ResultSet rskho=db.get(query);
					try{
						if(rskho.next()){
							this.khoId=rskho.getString("PK_SEQ");
						}
						rskho.close();
					}catch(Exception er){
						er.printStackTrace();
					}
				}
				
			}
			
				
		}
		this.IsKhoXuatQL_Khuvuc=util_kho.getIsQuanLyKhuVuc(this.khoId, this.db);
		
		
		// Lấy danh sách sản phẩm
		if( ((this.trahangNccId!=null&&this.trahangNccId.length()>0)) && (this.ndxId!=null&&this.ndxId.length()>0) 
					&& (this.khoId!=null&&this.khoId.length()>0) && this.spList.size() <= 0)
		{
			this.createSanpham();
		}
	}
 
	public void createRs_Display(){

		String query ;
		

		// lấy thông tin NCC
		query = "select PK_SEQ as nccId, ma + ', ' + ten as nccTen from ERP_NHACUNGCAP where trangthai = '1' and duyet = '1' order by ma asc";
		System.out.println("Khoi tao NCC: " + query);
		this.nccRs = this.db.get(query);
		
		// lấy thông tin NDX
		this.ndxRs = db.get(" select pk_seq, MA + ' - ' + TEN as TEN from ERP_NOIDUNGNHAP " +
							" where trangthai = '1' and upper(substring(ma, 0, 3)) = upper('XK') ");
 
		if(this.nccId!=null&& this.nccId.length()>0)
		{
			
			// Lấy thông tin Rs của trả hàng
			/*query = "select PK_SEQ as dmhId, cast(PK_SEQ as nvarchar(10)) + ' _ ngay tra ' + NGAYMUA as dmhTen " +
					"from ERP_MUAHANG where TYPE = '2' and TRANGTHAI = '1' and NHACUNGCAP_FK = '" + this.nccId + "'";*/
			
			query = " select PK_SEQ as dmhId, cast(PK_SEQ as nvarchar(10)) + ' _ ngay tra ' + NGAYMUA as dmhTen " +
					" from ERP_MUAHANG A where TYPE = '2' and TRANGTHAI  in ('1','2','3') and NHACUNGCAP_FK = '" + this.nccId + "' "
				  + " and exists ( SELECT PK_SEQ FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HD_DDH ON  HD.PK_SEQ=HD_DDH.HOADON_FK "
				  + " WHERE HD.TRANGTHAI=1 AND HD_DDH.DDH_FK=A.PK_SEQ ) ";
	
			if(this.id!=null&& this.id.length()>0)
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1) and pk_seq != '" + this.id + "' )";
			else
				query += " and PK_SEQ not in (select trahangncc_fk from ERP_XUATKHO where trahangncc_fk is not null and TRANGTHAI in (0, 1))";
						
			this.trahangNccRs = db.get(query);
			
			// Lấy thông tin Kho của trả hàng
			if(this.trahangNccId.length()>0){
				query = "select distinct a.PK_SEQ, a.TEN from ERP_KHOTT a inner join erp_muahang_sp b on a.PK_SEQ = b.KHOTT_FK where b.muahang_fk ="+this.trahangNccId+"";
				this.khoRs = db.get(query);
				
				if(this.khoId.equals("")){
					ResultSet rskho=db.get(query);
					try{
						if(rskho.next()){
							this.khoId=rskho.getString("PK_SEQ");
						}
						rskho.close();
					}catch(Exception er){
						er.printStackTrace();
					}
				}
				
			}
			
				
		}
		this.IsKhoXuatQL_Khuvuc=util_kho.getIsQuanLyKhuVuc(this.khoId, this.db);
		
		
		// Lấy danh sách sản phẩm
		if( ((this.trahangNccId!=null&&this.trahangNccId.length()>0)) && (this.ndxId!=null&&this.ndxId.length()>0) 
					&& (this.khoId!=null&&this.khoId.length()>0) && this.spList.size() <= 0)
		{
			this.createSanphamDisplay();
		}
	}
	
	// hàm lấy sản phẩm theo phiếu xuất kho dùng cho hiển thị
	private void getSanPhamFromNCCDisplay(boolean dasualo, boolean QlKhuvucKho){
		String query =
		" SELECT  isnull(b.pk_seq,0) as spid, isnull(b.ma, '') as spMa, isnull(b.dvkd_fk,0) as spDvkd, \n"+  
        " 		  isnull(b.ten1, b.ten)  as spTen, \n"+ 
        " 		  isnull(tscd.pk_seq,0) as tscdid ,isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh, \n"+  
        " 		  isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh, \n"+
		" 		  isnull(a.donvi, '') as donvi, a.soluong SOLUONGHD, isnull(a.dongia, '0') as dongia, \n"+ 
 		" 		  isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, isnull(a.thuenhapkhau, '0') as thuenhapkhau, ngaynhan, a.khott_fk, dungsai, \n"+  
 	    "  		  isnull(muanguyenlieudukien_fk, 0) as mnlId , '1' as inraHd \n"+ 
 	    " 		  ,isnull(a.tenhd, '') as tenhd, ISNULL(THUCTEXUAT.TOTALXUAT,0) THUCTEXUAT, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT  \n"+  
 	    "		FROM	 erp_muahang_sp a left join  ERP_SANPHAM b on a.sanpham_fk = b.pk_seq \n"+   
 	    "	   	LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq \n"+  
 	    "	   	LEFT JOIN erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq \n"+   
 	    "	   	LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq \n"+ 
 	    "	   	LEFT JOIN erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq \n"+ 
 	    "	   	LEFT JOIN  " +  
	    "  		(  " +  
	    "  			SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  \n" +  
	    "  			FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  \n" +  
	    "  			WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.TRAHANGNCC_FK IN (   "+this.trahangNccId+") AND A.XUATKHO_FK != "+(this.id.length() >0 ?this.id:"0")+"  " +  
	    "  			GROUP BY A.SANPHAM_FK  " +  
	    "  		) \n " +  
	    "  		XUATKHO ON a.SANPHAM_FK = XUATKHO.SANPHAM_FK  \n " +  
 	   	" 		LEFT JOIN  \n" +  
 	   	"  		(  " +  
 	   	"  			SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  \n" +  
 	   	"  			FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  \n" +  
 	   	"  			WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.TRAHANGNCC_FK IN (  "+this.trahangNccId+") AND A.XUATKHO_FK  = "+(this.id.length() >0 ?this.id:"0")+"  \n" +  
 	   	"  			GROUP BY A.SANPHAM_FK  \n" +  
 	   	"  		) THUCTEXUAT ON THUCTEXUAT.SANPHAM_FK = a.SANPHAM_FK \n" +
 	    " WHERE muahang_fk = '"+this.trahangNccId+"' \n";
	 
		
		System.out.println("query:"+query);
		
		ResultSet spRs = db.get(query);

		List<ISanpham> spList = new ArrayList<ISanpham>();
			try 
			{
				ISanpham sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					String soluong="0";
					if(this.id.length() >0){
						soluong=""+spRs.getDouble("THUCTEXUAT");
						
					}else{
					  soluong = ""+(spRs.getDouble("SOLUONGHD")-spRs.getDouble("soluongdaxuat"));
					  soluong = "0";
					}
					
					sp = new Sanpham(spId, spMa, spTen, "", soluong);
					sp.setIsBean(QlKhuvucKho);
					List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
					sp.setSoluongDaXuat(spRs.getString("soluongdaxuat"));		
					sp.setSoluongTotal(spRs.getString("SOLUONGHD"));
					
					if(this.id.trim().length() == 0){
						this.id = "0";
					}
					
					query = "  select  ISNULL(a.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH, ISNULL(a.PHIEUEO,'') AS PHIEUEO,  ISNULL(a.MAPHIEU,'') AS MAPHIEU,ISNULL(a.MAPHIEU,'') AS MAPHIEU,a.NGAYNHAPKHO,a.NGAYSANXUAT, A.NGAYHETHAN , A.SANPHAM_FK, 0 AS AVAILABLE,   "+
						" A.SOLO,ISNULL(A.MATHUNG,'') AS MATHUNG, ISNULL(A.MAME,'') AS MAME, A.IDMARQUETTE, "
						+ " ISNULL(a.HAMAM,'0') AS HAMAM, ISNULL(a.HAMLUONG,'100') AS HAMLUONG,	ISNULL(A.MARQ,'') AS MARQ, A.BIN_FK ,ISNULL(KV.TEN,'') AS KVKHO, "+  
						"  a.nsx_fk, isnull(nsx.ma + ' - ' + nsx.ten,'') as NSXTEN,isnull(A.SOLUONG,0) as sl  "+
						" from  ERP_XUATKHO_SP_CHITIET  A "+
						" left join ERP_BIN kv on kv.PK_SEQ = A.BIN_FK \n"
						+ "	left join erp_nhasanxuat nsx on nsx.pk_Seq = a.nsx_fk "+  
						" where A.XUATKHO_FK = "+this.id ;
					System.out.println("Kho:"+ query +" \n");
					
					// gan lai = rong
					if(this.id.equals("0")){
						this.id = "";
					}		
												
				ResultSet rsSpDetail = db.get(query);
				if(rsSpDetail != null)
				{		 
					while(rsSpDetail.next())
					{
						ISpDetail  splo = new SpDetail();
						splo.setSolo(rsSpDetail.getString("solo"));
						splo.setKhuId(rsSpDetail.getString("BIN_FK"));
						splo.setKhu(rsSpDetail.getString("KVKHO"));
						splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
						splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
						splo.setNgaynhapkho(rsSpDetail.getString("ngaynhapkho"));
						
						splo.setMame(rsSpDetail.getString("MAME"));
						splo.setMathung(rsSpDetail.getString("MATHUNG"));
						splo.setMamarquette(rsSpDetail.getString("MARQ"));
						splo.setTennsx(rsSpDetail.getString("NSXTEN"));
						splo.setIdnsx(rsSpDetail.getString("NSX_FK")==null?"":rsSpDetail.getString("NSX_FK"));
						splo.setMaphieu(rsSpDetail.getString("MAPHIEU"));
						splo.setMaphieudinhtinh(rsSpDetail.getString("MAPHIEUDINHTINH"));
						splo.setPhieuEO(rsSpDetail.getString("PHIEUEO"));
						 
						splo.setHamam(rsSpDetail.getString("HAMAM"));
						splo.setHamluong(rsSpDetail.getString("HAMLUONG"));
				 		double sl = rsSpDetail.getDouble("sl");
						splo.setSoluong(""+ sl);
						splo.setSoluongton("");
						
						spDetail.add(splo);
				}
							
				rsSpDetail.close();
			}
						
			sp.setSpDetailList(spDetail);
				 					
			spList.add(sp);
		}
		spRs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		this.spList = spList;
	}
	
	// hàm này lấy sản phẩm theo nhà cung cấp
	private void getSanPhamFromNCC(boolean dasualo ,boolean QlKhuvucKho ){

		String query =
		" SELECT  isnull(b.pk_seq,0) as spid, isnull(b.ma, '') as spMa, isnull(b.dvkd_fk,0) as spDvkd, \n"+  
        " 		  isnull(b.ten1, b.ten)  as spTen, \n"+ 
        " 		  isnull(tscd.pk_seq,0) as tscdid ,isnull(tscd.ma, '') as tscdMa, isnull(a.diengiai, tscd.ten) as tscdTen, isnull(nts.ma, 'NA') as nstNh, \n"+  
        " 		  isnull(ncp.pk_seq,0) as ncpid,isnull(ncp.ten, '') as ncpMa, isnull(a.diengiai, ncp.diengiai) as ncpTen, isnull(ttcp.diengiai, 'NA') as ncpNh, \n"+
		" 		  isnull(a.donvi, '') as donvi, a.soluong SOLUONGHD, isnull(a.dongia, '0') as dongia, \n"+ 
 		" 		  isnull(a.thanhtien, '0') as thanhtien, isnull(a.phantramthue, '0') as phantramthue, isnull(a.thuenhapkhau, '0') as thuenhapkhau, ngaynhan, a.khott_fk, dungsai, \n"+  
 	    "  		  isnull(muanguyenlieudukien_fk, 0) as mnlId , '1' as inraHd \n"+ 
 	    " 		  ,isnull(a.tenhd, '') as tenhd, ISNULL(THUCTEXUAT.TOTALXUAT,0) THUCTEXUAT, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT  \n"+  
 	    "		FROM	 erp_muahang_sp a left join  ERP_SANPHAM b on a.sanpham_fk = b.pk_seq \n"+   
 	    "	   	LEFT JOIN erp_taisancodinh tscd on a.taisan_fk = tscd.pk_seq \n"+  
 	    "	   	LEFT JOIN erp_nhomtaisan nts on tscd.NhomTaiSan_fk = nts.pk_seq \n"+   
 	    "	   	LEFT JOIN erp_nhomchiphi ncp on a.chiphi_fk = ncp.pk_seq \n"+ 
 	    "	   	LEFT JOIN erp_trungtamchiphi ttcp on ncp.ttchiphi_fk = ttcp.pk_seq \n"+ 
 	    "	   	LEFT JOIN  " +  
	    "  		(  " +  
	    "  			SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  \n" +  
	    "  			FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  \n" +  
	    "  			WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.TRAHANGNCC_FK IN (   "+this.trahangNccId+") AND A.XUATKHO_FK != "+(this.id.length() >0 ?this.id:"0")+"  " +  
	    "  			GROUP BY A.SANPHAM_FK  " +  
	    "  		) \n " +  
	    "  		XUATKHO ON a.SANPHAM_FK = XUATKHO.SANPHAM_FK  \n " +  
 	   	" 		LEFT JOIN  \n" +  
 	   	"  		(  " +  
 	   	"  			SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  \n" +  
 	   	"  			FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  \n" +  
 	   	"  			WHERE B.TRANGTHAI IN (0, 1,4,5) AND B.TRAHANGNCC_FK IN (  "+this.trahangNccId+") AND A.XUATKHO_FK  = "+(this.id.length() >0 ?this.id:"0")+"  \n" +  
 	   	"  			GROUP BY A.SANPHAM_FK  \n" +  
 	   	"  		) THUCTEXUAT ON THUCTEXUAT.SANPHAM_FK = a.SANPHAM_FK \n" +
 	    " WHERE muahang_fk = '"+this.trahangNccId+"' \n";
	 
		
		System.out.println("query:"+query);
		
		ResultSet spRs = db.get(query);

		List<ISanpham> spList = new ArrayList<ISanpham>();
			try 
			{
				ISanpham sp = null;
				while(spRs.next())
				{
					String spId = spRs.getString("spId");
					String spMa = spRs.getString("spMa");
					String spTen = spRs.getString("spTen");
					double soluong=0;
					if(this.id.length() >0){
						soluong=spRs.getDouble("THUCTEXUAT");
						
					}else{
					  soluong = (spRs.getDouble("SOLUONGHD")-spRs.getDouble("soluongdaxuat"));
					  
					}
					
					sp = new Sanpham(spId, spMa, spTen, "", ""+soluong);
					sp.setIsBean(QlKhuvucKho);
					List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
					sp.setSoluongDaXuat(spRs.getString("soluongdaxuat"));		
					sp.setSoluongTotal(spRs.getString("SOLUONGHD"));
					
					if(this.id.trim().length() == 0){
						this.id = "0";
					}
					
					query="SELECT * FROM ERP_XUATKHO_SP_CHITIET WHERE XUATKHO_FK="+this.id;
					ResultSet rsck=db.get(query);
					boolean daxk=false;
					if(rsck.next()){
						daxk=true;
					}
					
				
					
					query="SELECT   ISNULL(NSX.MA +''+NSX.TEN,'')AS NSXTEN,A.NSX_FK,ISNULL(A.HAMLUONG,'100') AS HAMLUONG ,ISNULL(A.HAMAM,'0') AS HAMAM   , "
							 +"\nB.NGAYNHAPKHO,ISNULL(A.MAPHIEU,'') AS MAPHIEU,ISNULL(A.MAPHIEUDINHTINH,'') AS MAPHIEUDINHTINH , "
							 +"\nISNULL(A.PHIEUEO,'') AS PHIEUEO   ,A.PK_SEQ AS KHOCHITIETID, B.NGAYHETHAN , NGAYSANXUAT, B.SANPHAM_FK,"
							 +"\nISNULL(AVAILABLE, 0) AS AVAILABLE,  B.SOLO,ISNULL(B.MATHUNG,'') AS MATHUNG, ISNULL(B.MAME,'') AS MAME,ISNULL(B.MARQ,'') AS MARQ,"
							 +"\nisnull(B.BIN_FK,0) as BIN_FK  ,isnull(KV.MA,'') AS KVKHO,B.SOLUONG as SL"
							 +"\nFROM ERP_KHOTT_SP_CHITIET A   "
							 +"\ninner join ("
							 +"\nSELECT   * FROM ERP_MUAHANG_SP_CHITIET B WHERE B.MUAHANG_FK="+this.trahangNccId+"  "
							 +"\nand KHOTT_SP_CT_FK='"+this.khoId+"'"
							 +"\n)B"
							 +"\non  ISNULL(A.SOLO,'')=ISNULL(B.SOLO,'') and ISNULL(A.NGAYHETHAN,'')=ISNULL(B.NGAYHETHAN,'')"
							 +"\nand ISNULL(A.NGAYNHAPKHO,'')=ISNULL(B.NGAYNHAPKHO,'')"
							 +"\nand ISNULL(A.MAME,'')=ISNULL(b.MAME,'')"
							 +"\nand ISNULL(A.MATHUNG,'')=ISNULL(B.MATHUNG,'')"
							 +"\nand ISNULL(A.MARQ,'')=ISNULL(B.MARQ,'')"
							 +"\nand ISNULL(A.HAMAM,'0')=ISNULL(B.HAMAM,'0')"
							 +"\nand ISNULL(A.HAMLUONG,'100')=ISNULL(B.HAMLUONG,'100')"
							 +"\nand ISNULL(A.MAPHIEU,'')=ISNULL(B.MAPHIEU,'')"
							 +"\nand ISNULL(A.BIN_FK,0)=ISNULL(B.BIN_FK,0)  "
							 +"\nand ISNULL(A.MAPHIEUDINHTINH,'')=ISNULL(B.PHIEUDT,'')"
							 +"\nand ISNULL(A.PHIEUEO,'')=ISNULL(B.PHIEUEO,'')"
							 +"\nand ISNULL(A.NSX_FK,0)=ISNULL(B.NSX_FK,0)"
							 +"\nand B.SANPHAM_FK=A.SANPHAM_FK"
							 +"\nLEFT JOIN ERP_BIN KV ON KV.PK_SEQ = A.BIN_FK    "
							 +"\nLEFT JOIN ERP_NHASANXUAT NSX ON NSX.PK_SEQ = A.NSX_FK  "
							 +"\nWHERE A.SANPHAM_FK = "+spId+"   AND A.KHOTT_FK ="+this.khoId+"   "
							 +"\nand  B.SOLUONG+ A.AVAILABLE>0 "
							 +"\norder by A.NGAYHETHAN   ";
					
					System.out.println("lay chi tiet san pham :"+ query +" \n");
					
					// gan lai = rong
					if(this.id.equals("0")){
						this.id = "";
					}		
					
					double soluongtongct=soluong;						
				ResultSet rsSpDetail = db.get(query);
				if(rsSpDetail != null)
				{		 
					while(rsSpDetail.next())
					{
						ISpDetail  splo = new SpDetail();
						splo.setKhoChiTietId(rsSpDetail.getString("KHOCHITIETID"));
						splo.setSolo(rsSpDetail.getString("solo"));
						splo.setKhuId(rsSpDetail.getString("BIN_FK"));
						splo.setKhu(rsSpDetail.getString("KVKHO"));
						splo.setNgaysanxuat(rsSpDetail.getString("ngaysanxuat"));
						splo.setNgayhethan(rsSpDetail.getString("ngayhethan"));
						splo.setNgaynhapkho(rsSpDetail.getString("ngaynhapkho"));
						
						splo.setMame(rsSpDetail.getString("MAME"));
						splo.setMathung(rsSpDetail.getString("MATHUNG"));
						splo.setMamarquette(rsSpDetail.getString("MARQ"));
						splo.setTennsx(rsSpDetail.getString("NSXTEN"));
						splo.setIdnsx(rsSpDetail.getString("NSX_FK")==null?"":rsSpDetail.getString("NSX_FK"));
						splo.setMaphieu(rsSpDetail.getString("MAPHIEU"));
						splo.setMaphieudinhtinh(rsSpDetail.getString("MAPHIEUDINHTINH"));
						splo.setPhieuEO(rsSpDetail.getString("PHIEUEO"));
						 
						splo.setHamam(rsSpDetail.getString("HAMAM"));
						splo.setHamluong(rsSpDetail.getString("HAMLUONG"));
						
						double avai = rsSpDetail.getDouble("AVAILABLE");
				 		double sl = rsSpDetail.getDouble("sl");
						splo.setSoluong(""+ sl);
						if(rsSpDetail.getDouble("sl") >0){
							avai = avai+ sl;
							splo.setSoluongton(""+avai);
						} else{
							splo.setSoluongton(""+avai);
						}
						
						/*if(!daxk){
							// phân bổ vào những lô 
							double soluongcapnhat=0;
							if(soluongtongct > avai){
								soluongcapnhat=avai;
								soluongtongct=soluongtongct-avai;
							}else{
								soluongcapnhat=soluongtongct;
								soluongtongct=0;
							}
							System.out.println("soluongcapnhat: "+soluongcapnhat);
							splo.setSoluong(formatter_3le.format(soluongcapnhat));
							
						}else{
							splo.setSoluong(sl+"");
						}*/
						splo.setSoluong(sl+"");
						// duyet
						if((avai > 0)  ||(avai ==0 && sl >0)){
							spDetail.add(splo);
						}
				}
							
				rsSpDetail.close();
			}
						
			sp.setSpDetailList(spDetail);
				 					
			spList.add(sp);
		}
		spRs.close();
		} 
		catch (Exception e) {
			e.printStackTrace();
		}
		this.spList = spList;
	
	}
	
	private void createSanphamDisplay(){
		 
		String query = "";
		boolean dasualo = false;
		boolean QlKhuvucKho = false;
		
		
		query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
		if(QlKhuvucKho){
			boolean tmp = false;
			ResultSet rs = db.get(query);
			try{
			if(rs.next()){
				tmp = true;
			}
			}catch(Exception err){
				this.msg=err.getMessage();
				err.printStackTrace();
			}
			if(!tmp){
				this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
				QlKhuvucKho = false;
			}
		}
		
		if(this.trahangNccId != null && this.trahangNccId.length()>0) // Lấy theo trả hàng NCC
		{ 
			this.getSanPhamFromNCCDisplay(dasualo,QlKhuvucKho);
		}
	}
	private void createSanpham()
	{	 
		String query = "";
		boolean dasualo = false;
		boolean QlKhuvucKho = false;
		query= "SELECT * FROM ERP_XUATKHO_SP_CHITIET WHERE XUATKHO_FK="+(this.id.length() >0?this.id:"0") ;
		ResultSet rs=db.get(query);
		try{
		if(rs.next()){
			dasualo=true;
		}
		}catch(Exception err){
			this.msg=err.getMessage();
			err.printStackTrace();
		}
	
		QlKhuvucKho= util_kho.getIsQuanLyKhuVuc(this.khoId, db).equals("1");
		
		
		query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
		if(QlKhuvucKho){
			boolean tmp = false;
			rs = db.get(query);
			try{
			if(rs.next()){
				tmp = true;
			}
			}catch(Exception err){
				this.msg=err.getMessage();
				err.printStackTrace();
			}
			if(!tmp){
				this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
				QlKhuvucKho = false;
			}
		}
		
		if(this.trahangNccId != null && this.trahangNccId.length()>0) // Lấy theo trả hàng NCC
		{ 
			this.getSanPhamFromNCC(dasualo,QlKhuvucKho);
		}
			
	//	check San pham xuat kho
		 
		if(this.spList.size() > 0)
		{
			for(int i = 0; i < this.spList.size(); i++)
			{
				ISanpham sp = this.spList.get(i);
				List<ISpDetail> spDetail = sp.getSpDetailList();
				
				double sum = 0;
				for(int j = 0; j < spDetail.size(); j++)
				{
					sum += DinhDang.dinhdangso(Double.parseDouble(spDetail.get(j).getSoluongton()));
				}
					
				if(DinhDang.dinhdangso(sum) < DinhDang.dinhdangso(Double.parseDouble(sp.getSoluong())))
				{
					this.msg += "+ Sản phẩm " + sp.getMa() + " - " + sp.getDiengiai() + ", không đủ số lượng để xuất kho, vui lòng kiểm tra lại \n";
				}
			}
		}
		 
	}
	
	public void changeDdh() 
	{
		try{
			if(this.DdhId.length() > 0){
				String query="select NGAYDAT from ERP_DONDATHANG where PK_SEQ="+this.DdhId;
				ResultSet rs=db.get(query);
				if(rs.next()){
					this.ngayxuatkho=rs.getString("NGAYDAT");
				}
				rs.close();
			}
			
	 
		
		//if( ((this.ddhIds != null && this.ddhIds.length()>0) || (this.trahangNccId!=null&&this.trahangNccId.length()>0)) && (this.ndxId!=null&&this.ndxId.length()>0) && (this.khoId!=null&&this.khoId.length()>0) && this.spList.size() <= 0)
		if(this.hdtcId != null && this.hdtcId.length() > 0 && this.khoId.length() > 0)
		{
			this.createSanpham();
		}
		else{
			this.spList = new ArrayList<ISanpham>();
		}
		
		}catch(Exception er){
			this.msg=er.getMessage();
			er.printStackTrace();
		}
	}

	public void init() 
	{
		String query = 
			" select 	a.PK_SEQ as pxkId,  a.PREFIX + CAST(a.PK_SEQ as varchar(20)) as maphieu , a.NGAYXUAT, a.NGAYCHOT, \n" +
			" 			a.HOADON_FK, A.DONDATHANG_FK , a.NPP_FK as nppId, \n" +
			" 			a.TRAHANGNCC_FK, a.KHO_FK, a.GHICHU, a.NOIDUNGXUAT, \n" +
			" 			a.LYDOXUAT,   \n" +
			"			a.TRANGTHAI ,\n" +
			" 			ISNULL ((select B.NGAYXUATHD from ERP_HOADON_DDH C INNER JOIN ERP_HOADON B ON C.HOADON_FK= B.PK_SEQ WHERE DDH_FK=a.TRAHANGNCC_FK and B.TRANGTHAI<>2),'') as  NGAYXUATHD \n"+
			" from ERP_XUATKHO a \n" +
			" where a.pk_seq = '" + this.id + "'";
		
		System.out.println("PXK111: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayxuatkho = rs.getString("ngayxuat");
					this.ngaychotNV = rs.getString("NGAYCHOT");
					
					this.ngayhoadon=rs.getString("NGAYXUATHD");
					System.out.println( " ngay hoa don :"+ this.ngayhoadon);
					
					this.khoId = rs.getString("kho_fk");
					
					this.hdtcId = "";
					if(rs.getString("HOADON_FK") != null){
						this.hdtcId = rs.getString("HOADON_FK");
						this.Loaixuatkho="HD";
					}else if(rs.getString("dondathang_fk")!=null) {
						this.DdhId=rs.getString("dondathang_fk");
						this.Loaixuatkho="DH";
					}
					
					this.trahangNccId = "";
					if(rs.getString("TRAHANGNCC_FK") != null)
						{
							this.trahangNccId = rs.getString("TRAHANGNCC_FK");
							this.nccId = rs.getString("nppId");
						}
					
					this.ndxId = rs.getString("NOIDUNGXUAT");
					this.lydoxuat = rs.getString("LYDOXUAT");
					this.ghichu = rs.getString("GHICHU");					
					this.trangthai = rs.getString("trangthai");
					
					this.maphieu = rs.getString("maphieu");
					
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
				this.msg=e.getMessage();
				
			}
		}
	
		if(Double.parseDouble(this.trangthai) >= 1){
			this.createRs_Display();
		}else{
			if(this.Loaixuatkho.equals("DH")){
				this.createSanpham();
			}
			this.createRs();
		}
	}

	public void initPdf()
	{
		String query =  "select a.PK_SEQ as pxkId, a.NGAYXUAT, a.KHO_FK, a.GHICHU, e.MA + '; ' + e.TEN as NOIDUNGXUAT, " +
						"a.LYDOXUAT, b.KHACHHANG_FK as nppId, g.pk_seq as nccId, a.TRANGTHAI, " +
						"a.HOADON_FK, a.TRAHANGNCC_FK, " +
						"c.ma + ', ' + c.ten as nppTen, isnull(c.DIACHI, 'na') as diachiNpp, " +
						"g.ma + ', ' + g.ten as nccTen, isnull(g.DIACHI, 'na') as diachiNcc, " +
						"d.TEN + '; ' + d.DIACHI as diachiKho  " +
						"from ERP_XUATKHO a " +
						"left join ERP_HOADON b on a.HOADON_FK = b.PK_SEQ " +
						"left join ERP_KHACHHANG c on b.KHACHHANG_FK = c.PK_SEQ " +
						"left join ERP_MUAHANG f on a.trahangncc_fk = f.pk_seq " +
						"left join ERP_NHACUNGCAP g on f.nhacungcap_fk = g.pk_seq " +
						"inner join ERP_KHOTT d on a.KHO_FK = d.PK_SEQ " +
						"inner join ERP_NOIDUNGNHAP e on a.NOIDUNGXUAT = e.PK_SEQ " +
						"where a.pk_seq = '" + this.id + "'";
		
		//if(this.inddhIds!=null && this.inddhIds.trim().length()>0)
		//	query+=" and b.pk_seq in ("+this.inddhIds+")";
		
		System.out.println("PXK PDF: " + query);
		
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				while(rs.next())
				{
					this.ngayxuatkho = rs.getString("ngayxuat");
					this.khoId = rs.getString("kho_fk");
					
					if(rs.getString("trahangncc_fk") != null)
					{
						this.nppId = rs.getString("nccId");
						//this.ddhId = " - Số trả hàng: " + rs.getString("TRAHANGNCC_FK");
						this.diachinhan = rs.getString("diachiNcc");
						this.nguoinhanhang = rs.getString("nccTen");
					}
					else
					{
						this.nppId = rs.getString("nppId");
						//this.ddhId =  " - Số đơn đặt hàng: " + rs.getString("dondathang_fk");
						this.diachinhan = rs.getString("diachiNpp");
						this.nguoinhanhang = rs.getString("nppTen");
					}
					//this.nppIds = rs.getString("nppIds");
					this.hdtcId = rs.getString("HOADON_FK");
					this.ndxId = rs.getString("NOIDUNGXUAT");
					this.lydoxuat = rs.getString("LYDOXUAT");
					this.ghichu = rs.getString("GHICHU");
					this.trangthai = rs.getString("trangthai");
					this.xuattaikho = rs.getString("diachiKho");
				}
				rs.close();
			} 
			catch (SQLException e) {}
		}
		
		this.initSanPhamPdf();
	}
	
	private void initSanphamDisplay() {
		System.out.println("DISPLAY");
		String query = " select a.SANPHAM_FK, b.MA, b.TEN, a.SOLUONG from ERP_XUATKHO_SANPHAM a " +
					   " inner join  ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ where XUATKHO_FK = '" + this.id + "'";
 
			ResultSet rs = db.get(query);
			
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			try 
			{
				ISanpham sanpham;
				String[] param = new String[6];
				
				while(rs.next())
				{
					param[0] = rs.getString("SANPHAM_FK");
					param[1] = rs.getString("MA");
					param[2] = rs.getString("TEN");
					param[3] = rs.getString("SOLUONG");
					param[4] = "";
					
					sanpham = new Sanpham(param);
 
					List<ISpDetail> spDetail = new ArrayList<ISpDetail>();	
					query = " select sp.*, ISNULL(kv.TEN,'') AS KVKHO from ERP_XUATKHO_SP_CHITIET sp " +
							" left join ERP_KHUVUCKHO kv on sp.KHUVUCKHO_FK = kv.PK_SEQ " +
							" WHERE sanpham_fk="+rs.getString("SANPHAM_FK")+" and XUATKHO_FK="+this.id;
					ResultSet rsSpDetail = db.get(query);
				 
						while(rsSpDetail.next())
						{
							ISpDetail  splo =new SpDetail();
							splo.setSolo(rsSpDetail.getString("solo"));
							splo.setKhu(rsSpDetail.getString("KVKHO"));
					 		splo.setSoluong(""+rsSpDetail.getDouble("SOLUONG"));
					 		
					 		splo.setNgaybatdau(rsSpDetail.getString("NGAYBATDAU"));
					 		splo.setNgayhethan(rsSpDetail.getString("NGAYHETHAN"));
					 		splo.setNgaysanxuat(rsSpDetail.getString("Ngaysanxuat"));
					 		if(splo.getKhu() == null || splo.getKhu().length() == 0)
					 			sanpham.setIsBean(false);
					 		else
					 			sanpham.setIsBean(true);
					 		spDetail.add(splo);
						}
						rsSpDetail.close();
				  
					sanpham.setSpDetailList(spDetail);	
					this.soluongxuat=Float.parseFloat(sanpham.getSoluong());
					spList.add(sanpham);
				}
				rs.close();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
			System.out.println("SỐ LƯỢNG XUẤT: "+this.soluongxuat);
			this.spList = spList;
	}


	public void initLayHang() 
	{
		
		
		String query = 
			" select a.NPP_FK, a.PK_SEQ as pxkId, a.NGAYXUAT, a.HOADON_FK, a.KHO_FK, c.ten as khoten, a.GHICHU, a.NOIDUNGXUAT, " +
			" a.LYDOXUAT, b.MA as ndxId , a.TRANGTHAI " +
			" from ERP_XUATKHO a " +
			" inner join ERP_NOIDUNGNHAP b on a.NOIDUNGXUAT = b.PK_SEQ " +
			" inner join ERP_KHOTT c on a.kho_fk = c.pk_seq " +			
			" where a.pk_seq = '"  + this.id + "'";
		
		//System.out.println("Lay hang: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
		try 
		{
			while(rs.next())
			{
				this.ngayxuatkho = rs.getString("ngayxuat");
				this.khoId = rs.getString("khoten");
				this.hdtcId = rs.getString("HOADON_FK");
				this.ndxId = rs.getString("ndxId");
				this.lydoxuat = rs.getString("LYDOXUAT");
				this.ghichu = rs.getString("GHICHU");
				this.trangthai = rs.getString("trangthai");
				this.nppId=rs.getString("NPP_FK");
				//this.nppIds=rs.getString("NPPIDS");
			}
			rs.close();
		} 
		catch (SQLException e) {}
		}
		
		this.initSanPhamLayHang();
 
	}
	
 
	private void initSanPhamPdf()
	{
		String query =  " SELECT A.SANPHAM_FK, B.MA, B.TEN,  C.DONVI AS DVT, ISNULL(D.SOLUONG1,0) AS QUYCACH,  A.SOLUONG, ISNULL(D.SOLUONG2,0) AS SOLUONG2 , "+ 
						" CAST(ROUND(B.TRONGLUONG, 5) AS NUMERIC(10, 5)) AS TRONGLUONG, CAST(ROUND(B.THETICH, 5) AS NUMERIC(10, 5)) AS THETICH  "+
						" FROM ERP_XUATKHO_SANPHAM A INNER JOIN SANPHAM B ON A.SANPHAM_FK = B.PK_SEQ  "+
						" INNER JOIN DONVIDOLUONG C ON B.DVDL_FK = C.PK_SEQ  "+
						" LEFT JOIN QUYCACH D ON B.PK_SEQ = D.SANPHAM_FK AND  D.DVDL2_FK=100004 "+
						" WHERE XUATKHO_FK = "+this.id;
	
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			List<ISanpham> spList = new ArrayList<ISanpham>();
			
			try 
			{				
				while(rs.next())
				{
					ISanpham sanpham = new Sanpham();
					 
					int soluong = rs.getInt("SOLUONG");
					float quycach = rs.getFloat("quycach");
					float soluong2 = rs.getFloat("SOLUONG2");
					
					float thung = 0;
					float le = 0;
					if(quycach >0){
						thung = soluong * soluong2 / quycach;
						
						le = (soluong * soluong2) % quycach;
					}
					
					sanpham.setId(rs.getString("SANPHAM_FK"));
					sanpham.setMa(rs.getString("MA"));
					sanpham.setDiengiai(rs.getString("TEN"));
					sanpham.setDVT(rs.getString("DVT"));
					
					sanpham.setSoluong(Integer.toString(soluong));
					sanpham.setQuycach(Float.toString(quycach));
						String sole_1=formatter_6le.format(thung);
						 
						 int idex=sole_1.indexOf(".");
						  
						 if(idex >0){
							 thung=Float.parseFloat(sole_1.substring(0,idex) );
							 
						 }
						 
						 sanpham.setThung(""+thung);
						  
						 
					sanpham.setLe(Float.toString(le));
					 
					spList.add(sanpham);
				}
			
				rs.close();
			} 
			catch (Exception e) {e.printStackTrace();}
			
			this.spList = spList;
		}
	}

	private void initSanPhamLayHang()
	{
		List<ISanpham> spList = new ArrayList<ISanpham>();
		ISanpham sanpham = new Sanpham();
		spList.add(sanpham);
		List<ISpDetail> spctList = sanpham.getSpDetailList();
		
		String query =  " SELECT SP.MA as SPMA, SP.TEN AS SPTEN, A.SOLO AS SOLO, ISNULL(kv.TEN ,'')AS KVKHO, "+  
						" ISNULL(A.NGAYSANXUAT, '') AS NGAYSANXUAT, "+  	 
						" DVDL.DONVI AS DVDL,   "+
						" ISNULL(QC.SOLUONG1,0) AS SL1, ISNULL(QC.SOLUONG2,0) AS SL2, A.SOLUONG AS SL, "+   
						" CAST(ROUND(SP.TRONGLUONG, 5) as numeric(10, 5)) AS TRONGLUONG, "+  
						" CAST(ROUND(SP.THETICH, 5) AS numeric(10, 5)) AS THETICH,a.SOLUONG "+ 
						" FROM ERP_XUATKHO_SP_CHITIET  A   "+ 
						" INNER JOIN ERP_XUATKHO XK ON A.XUATKHO_FK = XK.PK_SEQ  "+  
						" LEFT JOIN ERP_KHUVUCKHO kv on kv.PK_SEQ = A.KHUVUCKHO_FK   "+ 
						" INNER JOIN SANPHAM SP ON A.SANPHAM_FK = SP.PK_SEQ    "+
						" INNER JOIN DONVIDOLUONG DVDL ON SP.DVDL_FK = DVDL.PK_SEQ    "+
						" LEFT JOIN QUYCACH QC ON QC.SANPHAM_FK = A.SANPHAM_FK  AND QC.DVDL2_FK=100012 "+  
						" WHERE XUATKHO_FK ="+this.id;
		
		System.out.println("initSanPhamLayHang: " + query);
		ResultSet rs = db.get(query);
		
		if(rs != null)
		{
			try 
			{	
				while(rs.next())
				{
 
					int soluong = rs.getInt("SOLUONG");
					float SL1 = rs.getFloat("SL1");
					float SL2 = rs.getFloat("SL2");
					
					float thung = 0;
					float le = 0;
					if(SL2 >0){
						thung = soluong * SL1 / SL2;
						le = (soluong * SL1) % SL2;
					}
					
					double trongluong = rs.getDouble("trongluong");
					double thetich = rs.getDouble("thetich");
					
					ISpDetail sp = new SpDetail();
					sp.setTen(rs.getString("SPMA") + " - " + rs.getString("SPTEN"));
					sp.setDvt(rs.getString("DVDL"));
					sp.setKhu(rs.getString("KVKHO"));
					sp.setSolo(rs.getString("SOLO"));
					sp.setSoluong(rs.getString("SL"));
					sp.setSoluongton(rs.getString("NGAYSANXUAT"));
					sp.setQuycach(SL1+"");
					sp.setVitri(thung+"");
					sp.setVitriId(le+"" );
					sp.setTrongLuong(trongluong);
					sp.setTheTich(thetich);
					
					spctList.add(sp);
				}
			
				rs.close();
			} 
			catch (Exception e) { e.printStackTrace();}
			
			this.spList = spList;
		}
	}

	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public List<ISanpham> getSpList()
	{
		return this.spList;
	}

	public void setSpList(List<ISanpham> spList)
	{
		this.spList = spList;
	}

	public String getLydoxuat() 
	{
		return this.lydoxuat;
	}

	public void setLydoxuat(String lydoxuat) 
	{
		this.lydoxuat = lydoxuat;
	}

	public String getGhichu() 
	{
		return this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public ResultSet getDdhList() 
	{
		return this.ddhRs;
	}

	public void setDdhList(ResultSet ddhList)
	{
		this.ddhRs = ddhList;
	}

	public ResultSet getDdhList2() 
	{
		return this.ddhRs2;
	}

	public void setDdhList2(ResultSet ddhList2)
	{
		this.ddhRs2 = ddhList2;
	}

	public String getNguoinhanhang()
	{
		String tmp = "";
		String query = "select TEN from ERP_KHACHHANG WHERE PK_SEQ IN (" + this.nppId + ") ";
		System.out.println("Nguoi nhan hang " + query);
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				while(rs.next()){
					tmp = tmp + rs.getString("TEN") + ", ";
				}
				rs.close();
				
			}catch(java.sql.SQLException e){}
		}
		if(tmp.length() > 0) tmp = tmp.substring(0, tmp.length() - 2);
		
		this.nguoinhanhang = tmp;
		return this.nguoinhanhang ;
	}

	public void setNguoinhanhang(String nguoinhanhang) 
	{
		this.nguoinhanhang = nguoinhanhang;
	}

	public String getDiachi()
	{
		return this.diachinhan;
	}

	public void setDiachi(String diachi)
	{
		this.diachinhan = diachi;
	}

	public String getXuattaikho() 
	{
		return this.xuattaikho;
	}

	public void setXuattaikho(String xuattaikho) 
	{
		this.xuattaikho = xuattaikho;
	}

	public boolean chotXuatKho(String userId) 
	{
		String query = "";
		try 
		{
			this.db.getConnection().setAutoCommit(false);
			Utility util = new Utility();
			query = " SELECT NGAYXUAT, HOADON_FK, DONDATHANG_FK, TRAHANGNCC_FK FROM ERP_XUATKHO WHERE TRANGTHAI = 0 AND PK_SEQ = " + this.id;
			ResultSet rs = db.get(query);
			String ngaychotnv = "";
			String idkhott="";
			if(rs.next()){
				  ngaychotnv = rs.getString("NGAYXUAT");
				  this.trahangNccId = rs.getString("TRAHANGNCC_FK");
				  this.hdtcId = rs.getString("HOADON_FK");
				  this.DdhId = rs.getString("DONDATHANG_FK");
			}
			rs.close();
			int thangtruoc = Integer.parseInt(ngaychotnv.substring(5, 7));
			int namtruoc = Integer.parseInt(ngaychotnv.substring(0, 4));
			
			if(thangtruoc == 1){
				namtruoc = namtruoc - 1;
				thangtruoc = 12;
				
			}else{
				thangtruoc = thangtruoc - 1;
			}
 
			  
			String kbhId = "";
			// Lấy kênh bán hàng
			if(this.hdtcId !=null &&  this.hdtcId.trim().length() > 0)
			{								
				query = "SELECT KBH_FK FROM ERP_HOADON WHERE PK_SEQ = '" + this.hdtcId + "'";
			 
				rs = db.get(query);
				while(rs.next())
				{
					kbhId = rs.getString("KBH_FK");
				}
				
				rs.close();
			}
			
			boolean QlKhuvucKho = util_kho.getIsQuanLyKhuVuc(this.khoId, db).equals("1");
			 
		 
			query = "select * from ERP_KHUVUCKHO where KHOTT_FK = '"+this.khoId+"' and TRANGTHAI = 1";
			if(QlKhuvucKho){
				boolean tmp = false;
				rs = db.get(query);
				try{
				if(rs.next()){
					tmp = true;
				}
				}catch(Exception err){
					this.msg=err.getMessage();
					err.printStackTrace();
				}
				if(!tmp){
					this.msg += "Kho sản phẩm được quản lý theo khu vực, nhưng chưa có khu vực nào hoạt động.\n";
					QlKhuvucKho = false;
				}
			}
			
			query	=	" SELECT xk.nhomkenh_fk ,xk.npp_id ,xk.ngayxuat, SP.LOAISANPHAM_FK,  " +
						" XK.KHO_FK, A.SANPHAM_FK, A.SOLO, A.SOLUONG, A.KHUVUCKHO_FK ,A.NGAYBATDAU, XK.MACHUNGTU, XK.GHICHU  "+
						" FROM ERP_XUATKHO_SP_CHITIET A " +
						" INNER JOIN ERP_XUATKHO XK ON XK.PK_SEQ = A.XUATKHO_FK  " +
						" INNER JOIN SANPHAM SP ON SP.PK_SEQ = A.SANPHAM_FK  " +
						" WHERE XK.PK_SEQ = " + this.id;
			
			System.out.println(query);
			rs = this.db.get(query);
			while (rs.next()){
					String loaisanpham = rs.getString("LOAISANPHAM_FK");
					//String loaidh = rs.getString("LOAIDH");
					String spid = rs.getString("SANPHAM_FK");
					idkhott = rs.getString("KHO_FK");
					//String nhanvien_fk = rs.getString("NHANVIEN_FK");
					//String npp_fk = rs.getString("NPP_FK");
//					String ngaybatdau= rs.getString("ngaybatdau");
//					String khuvuckhoId = rs.getString("KHUVUCKHO_FK");
					String solo = rs.getString("SOLO");
					double soluongct = rs.getDouble("SOLUONG");
					String nhomkenhid=rs.getString("nhomkenh_fk");
					String nppid=rs.getString("npp_id");
					
					String machungtu = rs.getString("MACHUNGTU");
					String diengiai = rs.getString("GHICHU");
					
					double giavon = 0;
					
					String[] str=util_kho.getGiaChayKT(ngaychotnv, db, this.CongtyId, nppid, spid, solo);
					
					if(str[1].length() >0){
						this.msg = str[1];
						db.getConnection().rollback();
						return false;
					}else{
						giavon=Double.parseDouble(str[0]);
					}
					
					query = " SELECT SANPHAM_FK, AVG(ISNULL(GIATON, 0)) AS GIAVON  " +
							" FROM ERP_TONKHOTHANG " +
							" WHERE SANPHAM_FK = " + spid + " AND  THANG = '" + thangtruoc + "' " +
							" AND NAM = '" + namtruoc + "' AND CONGTY_FK = " + this.CongtyId + "" +
							" GROUP BY SANPHAM_FK " ;
					
					System.out.println(query);
					ResultSet rsgia = db.get(query); 
					if(rsgia.next()){
						giavon = rsgia.getDouble("GIAVON");
					}
					rsgia.close();								
					
					double thanhtien = giavon*soluongct;
				
					query = " UPDATE ERP_XUATKHO_SANPHAM " +
							" SET DONGIA = " + giavon + ", THANHTIEN = " + giavon + "*SOLUONG   " +
							" WHERE SANPHAM_FK = " + spid + " AND XUATKHO_FK = " + this.id;
 
					if(!this.db.update(query))
					{
						this.msg = "2.Khong the cap nhat ERP_XUATKHO_SANPHAM: " + query;
						db.getConnection().rollback();
						return false;
					}
					 
					//soluong GIẢM ,booked giảm lại
					double soluong=(-1)*soluongct;
					double booked= (-1)*soluongct;
					double available=  0;
					
				 
					String msg1= util_kho.Update_NPP_Kho_Sp(this.ngayxuatkho, "Chốt xuất kho", db, idkhott, spid, nppid, nhomkenhid, soluong, booked, available, 0);
					if(msg1.length() >0){
							db.update("rollback");
							this.msg= msg1;
							return false;
						}
					 
					
					
					  msg1=util_kho.Update_NPP_Kho_Sp_Chitiet(this.ngayxuatkho,"Chốt xuất kho", db, idkhott, spid, nppid, nhomkenhid, solo, "", "","", soluong, booked, available, 0);
					if(msg1.length() >0){
						db.update("rollback");
						this.msg= msg1;
						return false;
					}
					
					
								
					 
				//**********
			
					String masanpham = spid;
				
					String nam = ngaychotnv.substring(0, 4);
					String thang = ngaychotnv.substring(5, 7);
				
					//System.out.println("NOI DUNG XUAT: " + this.ndxId);
					String queryTK = "";
					if( this.ndxId.equals("100007")) //Xuất trả NCC
					{
						String taikhoanktCo = "";
						String taikhoanktNo = "";
					
						String doituong_no = "";
						String madoituong_no = "";
						String doituong_co = "";
						String madoituong_co = "";
					
						String queryncc = " SELECT NHACUNGCAP_FK from ERP_MUAHANG where pk_seq in (select TRAHANGNCC_FK from ERP_XUATKHO where pk_seq = "+ this.id +")  \n ";
						System.out.println(queryncc);
							
						ResultSet ncc = db.get(queryncc);
							
						String ncc_fk = "";
						while (ncc.next()){
								ncc_fk = ncc.getString("NHACUNGCAP_FK");								
						}
						ncc.close();		
							
						queryTK = 	"SELECT TK.PK_SEQ as TAIKHOANKTCO, \n " +
									"(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '63212000' AND CONGTY_FK = "+this.CongtyId+" ) as TAIKHOANKTNO \n " + 								
									"FROM ERP_TAIKHOANKT TK \n " +
									"INNER JOIN ERP_LOAISANPHAM LSP ON LSP.TAIKHOANKT_FK = TK.SOHIEUTAIKHOAN \n " +  
									"WHERE LSP.PK_SEQ = '" + loaisanpham + "' AND TK.CONGTY_FK = " + this.CongtyId + " \n " ;
							
						
						System.out.println("Quá đúng luôn : "+queryTK);
						
						doituong_no = "Nhà cung cấp";
						madoituong_no = ncc_fk;
						doituong_co = "Sản phẩm";
						madoituong_co = masanpham;

						//System.out.println("5.Query lay tai khoan: " + queryTK);
						if(queryTK.trim().length()>0)
						{
							//System.out.println("chay vao day ");
							ResultSet tkRs = db.get(queryTK);
							if(tkRs != null)
							{
								if(tkRs.next())
								{
									taikhoanktCo = tkRs.getString("TAIKHOANKTCO");
									taikhoanktNo = tkRs.getString("TAIKHOANKTNO");
									tkRs.close();
								}
							
								if(taikhoanktCo == null || taikhoanktCo.trim().length() <= 0 || taikhoanktNo == null || taikhoanktNo.trim().length() <= 0)
								{
									this.msg = "Loại sản phẩm và nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rs.close();
									db.getConnection().rollback();
									return false;
								}
								
								query=" update ERP_XUATKHO_SP_CHITIET SET GIACHAYKT="+giavon+" , taikhoanktCO= "+taikhoanktCo+" ,taikhoanktNo ="+taikhoanktNo+" where SANPHAM_FK="+masanpham+" AND XUATKHO_FK="+this.id+" AND SOLO='"+solo+"'";
								if(!db.update(query)){
									db.getConnection().rollback();
									this.msg= "Lỗi dòng lệnh : "+query;
								}
								
						
							//	Nguyen te khi xuat kho chinh la VND
								String tiente_fk = "100000";
								double  dongiaViet = giavon;
							
							
							//UPDATE NO-CO NEW
								thanhtien = giavon *  soluongct;
							  
								msg = util.Update_TaiKhoan_DienGiai( db, thang, nam, this.ngayxuatkho, this.ngayxuatkho, "XK08", this.id, taikhoanktNo, taikhoanktCo, this.ndxId, 
															Double.toString(thanhtien), Double.toString(thanhtien), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", (""+soluongct)
															, Double.toString(dongiaViet), tiente_fk, Double.toString(giavon), "1", dongiaViet + "*" + soluongct, giavon + "*" + soluongct, "Xuất trả NCC (hàng không đạt)", diengiai, machungtu );
								if(msg.trim().length()>0)
								{
									rs.close();
									tkRs.close();
									this.msg = "Loi update: " + msg;
									System.out.println("Loi khi chot: " + this.msg);
									db.getConnection().rollback();
									return false;
								}
							}else{
								this.msg = "Không xác định được tài khoản kế toán của nghiệp vụ này .Lỗi dòng lệnh : " +queryTK;
								rs.close();
								db.getConnection().rollback();
								return false;
							}
						}
					
					} 
					
				//*********
			}
			
			query = "update ERP_XUATKHO set trangthai = '1', ngaysua = '" + this.getDateTime() + "', nguoisua = '" + userId + "' where pk_seq = '" + this.id + "'";
			//System.out.println(query);
			if(!db.update(query))
			{
				this.msg = "8.Khong the cap nhat NHAPKHO: " + query;
				db.getConnection().rollback();
				
				return false;
			}
			
			//System.out.println("HOADON: "+this.hdtcId);
			
			//PHIẾU XUẤT KHO CHO HÓA ĐƠN TÀI CHÍNH || HÓA ĐƠN KHUYẾN MÃI
			if(this.hdtcId != null && this.hdtcId.length()>0)
			{
				
		     query=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT     " +  
				   "  FROM  " +  
				   "  (  " +  
				   "  	SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,  " +  
				   "  	CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG2/QC.SOLUONG1)  END AS SOLUONG  " +  
				   "  	FROM ERP_HOADON_SP HDSP   " +  
				   "  	INNER JOIN ERP_HOADON HD ON HD.PK_SEQ =HDSP.HOADON_FK    " +  
				   "  	INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ    " +  
				   "  	LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK " +  
				   "  	WHERE HDSP.HOADON_FK =   " + this.hdtcId+  
				   "  	AND HDSP.SOLUONG > 0 " +  
				   "  	GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK " +  
				   "  )  " +  
				   "  HOA_DON LEFT JOIN  " +  
				   "  (  " +  
				   "  	SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  " +  
				   "  	FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  " +  
				   "  	WHERE B.TRANGTHAI IN ( 1 ) AND B.HOADON_FK IN (  "+this.hdtcId+")   " +  
				   "  	GROUP BY A.SANPHAM_FK  " +  
				   "  )  " +  
				   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK  " +  
				   " where  HOA_DON.SOLUONG - ISNULL(XUATKHO.TOTALXUAT,0)  > 0 ";
		     
		     	//System.out.println("query:"+query);
		     	
				     ResultSet rscheck=db.get(query);
				     if(!rscheck.next()){
						query = "update ERP_HOADON set trangthai = '5' where pk_seq = '" + this.hdtcId + "'";
						if(!this.db.update(query))
						{
							this.msg = "1.Khong the cap nhat trang thai Hoa don: " + query;
							db.getConnection().rollback();
							return false;
						}
				     }
				     rscheck.close();
				
			}
			
			//NẾU LÀ PHIẾU XUẤT TRẢ HÀNG NCC
			if(this.trahangNccId != null && this.trahangNccId.trim().length()>0  )
			{
				//TRẠNG THÁI ĐÃ XUẤT KHO
				query = "update ERP_MUAHANG set trangthai = '2' where pk_seq = '" + this.trahangNccId + "'";
				if(!this.db.update(query))
				{
					this.msg = "1.Khong the cap nhat trang thai ERP_MUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				
			}else{
				if(this.hdtcId!=null&& this.hdtcId.length() >0){
						query=  " update ERP_DONDATHANG set TRANGTHAI='6' where PK_SEQ in  " +
								" ( select hd_dh.DDH_FK from ERP_XUATKHO xk inner join ERP_HOADON_DDH hd_dh on xk.HOADON_FK=hd_dh.HOADON_FK where xk.PK_SEQ= "+this.id+")";
						
						if(!this.db.update(query))
						{
							this.msg = "1.Khong the cap nhat trang thai ERP_MUAHANG: " + query;
							db.getConnection().rollback();
							return false;
						}
				}else{
						// phân biệt trường hợp xuất hàng bán và hàng khuyến mãi,xuât hàng bán thì xem đơn hàng có khuyến mãi không,và nếu có khuyến mãi thì xuất hóa đơn chưa?
					
					if(this.checkHoanTatPXK(this.id)){
							//nếu có thì hoàn tất xuất kho cho đơn đặt hàng
							query=  " update ERP_DONDATHANG set TRANGTHAI='6', IS_HOANTATXK='1' where PK_SEQ in  " +
									" ( select xk.dondathang_fk from ERP_XUATKHO xk   where xk.PK_SEQ= "+this.id+")";
						}else{
							query=  " update ERP_DONDATHANG set TRANGTHAI='6', IS_HOANTATXK='0' where PK_SEQ in  " +
									" ( select xk.dondathang_fk from ERP_XUATKHO xk   where xk.PK_SEQ= "+this.id+")";
						}
						 
						if(!this.db.update(query))
						{
							this.msg = "1.Không thể cập nhật" + query;
							db.getConnection().rollback();
							return false;
						}
					}
					
			}
			
			 
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		 
			return true;
		} 
		catch (Exception e)
		{ 	
			e.printStackTrace();
			db.update("rollback");
			this.msg = e.getMessage(); 
			return false;
		}
	}
	
	 

	private boolean checkHoanTatPXK(String id2) {
		// TODO Auto-generated method stub
		try{
			// kiểm tra hàng khuyến mãi đã xuất kho hết chưa
			String query=" SELECT DH.DONDATHANG_FK,DH.COUNT -ISNULL(XK.COUNT,0) FROM ( "+
				" SELECT   DONDATHANGID AS DONDATHANG_FK,COUNT( DISTINCT KHOTT_FK)  AS COUNT "+ 
				" FROM ERP_DONDATHANG_CTKM_TRAKM  "+
				" WHERE DONDATHANGID =(SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+ 
				" AND SPMA IS NOT NULL "+
				" GROUP BY DONDATHANGID "+
				" ) DH LEFT JOIN "+ 
				" ( "+
				" SELECT DONDATHANG_FK,COUNT(XK.KHO_FK)  AS COUNT "+
				" FROM ERP_XUATKHO XK WHERE XK.DONDATHANG_FK= (SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+
				" AND XK.TRANGTHAI NOT IN ('2','0') and XK.NOIDUNGXUAT=100008 "+
				" GROUP BY XK.DONDATHANG_FK "+
				" )XK ON DH.DONDATHANG_FK=XK.DONDATHANG_FK "+
				" WHERE DH.COUNT -  ISNULL(XK.COUNT,0)> 0"; 
			ResultSet rs=db.get(query);
			if(rs.next()){
				return false;
			}
			
			// kiểm tra hàng bán đã xuất kho hết chưa
			
			query=" SELECT DH.DONDATHANG_FK,DH.COUNT -ISNULL(XK.COUNT,0) FROM ( "+
				" SELECT   DONDATHANG_FK,COUNT( DISTINCT KHOTT_FK)  AS COUNT FROM ERP_DONDATHANG_SP  "+
				" WHERE DONDATHANG_FK =(SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+
				" GROUP BY DONDATHANG_FK "+
				" ) DH LEFT JOIN "+ 
				" ( "+
				" SELECT DONDATHANG_FK,COUNT(XK.KHO_FK)  AS COUNT "+
				" FROM ERP_XUATKHO XK WHERE XK.DONDATHANG_FK= (SELECT DONDATHANG_FK FROM ERP_XUATKHO XK WHERE XK.PK_SEQ="+this.id+" ) "+
				" AND XK.TRANGTHAI NOT IN ('2','0') and XK.NOIDUNGXUAT in (100008,100002,100027,100028,100029) "+
				" GROUP BY XK.DONDATHANG_FK "+
				" )XK ON DH.DONDATHANG_FK=XK.DONDATHANG_FK "+
				" WHERE DH.COUNT -  ISNULL(XK.COUNT,0)> 0 ";
			
			rs=db.get(query);
			if(rs.next()){
				return false;
			}
			rs.close();
			
			return true;
			 
		}catch(Exception er){
			
			er.printStackTrace();
			return false;
		}
	}


	public String getNPP(String soId){
		String npp = "";
		String query = 	"SELECT NPP.TEN " +
						"FROM NHAPHANPHOI NPP " +
						"INNER JOIN DONDATHANG DDH ON DDH.NPP_FK = NPP.PK_SEQ " +
						"WHERE DDH.PK_SEQ = " + soId + " ";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				npp = rs.getString("TEN");
				return npp;
			}catch(java.sql.SQLException e){}
		}
		return npp;
	}
	
	public boolean isQuanlylo() 
	{
		return this.quanlylo;
	}

	public void setQuanlylo(boolean quanlylo)
	{
		this.quanlylo = quanlylo;
	}

	public boolean isQuanlybean() 
	{
		return this.quanlybean;
	}

	public void setQuanlybean(boolean quanlybean) 
	{
		this.quanlybean = quanlybean;
	}

	
	public String getNccId() 
	{
		return this.nccId;
	}

	
	public void setNccId(String nccid) 
	{
		this.nccId = nccid;	
	}

	public ResultSet getNccList() 
	{
		return this.nccRs;
	}

	public void setNccList(ResultSet nccList) 
	{
		this.nccRs = nccList;
	}

	public String getTrahangNccId() 
	{
		return this.trahangNccId;
	}
	
	public void setTrahangNccId(String trahangid)
	{
		this.trahangNccId = trahangid;
	}

	public ResultSet getTrahangList()
	{
		return this.trahangNccRs;
	}

	public void setTrahangList(ResultSet trahangList) 
	{
		this.trahangNccRs = trahangList;
	}
	
	public String getNgaychotNV() 
	{
		return this.ngaychotNV;
	}

	public void setNgaychotNV(String ngaychotNV) 
	{
		this.ngaychotNV = ngaychotNV;
	}

	
	public void setNppTen(String nppTen) {
		this.nppTen=nppTen;
		
	}

	
	public String getNppTen() {
	
		return this.nppTen;
	}

	public void DBClose(){
		try{
			if(this.ddhRs != null) this.ddhRs.close();
			if(this.ddhRs2 != null) this.ddhRs2.close();
			if(this.khoRs != null) this.khoRs.close();
			if(this.nccRs != null) this.nccRs.close();
			if(this.ndxRs != null) this.ndxRs.close();
			if(this.nppRs != null) this.nppRs.close();
			if(this.hdtcList != null) this.hdtcList.close();
			db.shutDown();
		}catch(java.sql.SQLException e){}
	}



	public ResultSet getHDTCList() {
		return this.hdtcList;
	}



	public String getHDTCId() {
		return this.hdtcId;
	}



	public void setHDTCId(String hdtcId) {
		this.hdtcId = hdtcId;
	}



	public void setNppIdKhoId(String hdId) {
		String query = 	"select KHACHHANG_FK, KHO_FK from ERP_HOADON Where PK_SEQ = '" + hdId + "' ";
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				this.nppId = rs.getString("KHACHHANG_FK");
				this.khoId = rs.getString("KHO_FK");
			}catch(java.sql.SQLException e){}
		}
	}



	public boolean isDataoPXK() {
		
		
		if(this.hdtcId != null && this.hdtcId.length()>0)
		{
			
 String  query=" SELECT HOA_DON.SPID, HOA_DON.SPMA, HOA_DON.SPTEN,  HOA_DON.SOLUONG AS SOLUONGHD, ISNULL(XUATKHO.TOTALXUAT,0) AS SOLUONGDAXUAT     " +  
			   "  FROM  " +  
			   "  (  " +  
			   "  	SELECT HDSP.SANPHAM_FK AS SPID, SP.MA AS SPMA, SP.TEN AS SPTEN,  " +  
			   "  	CASE WHEN SP.DVDL_FK=HDSP.DVDL_FK THEN SUM(HDSP.SOLUONG) ELSE SUM(HDSP.SOLUONG*QC.SOLUONG2/QC.SOLUONG1)  END AS SOLUONG  " +  
			   "  	FROM ERP_HOADON_SP HDSP   " +  
			   "  	INNER JOIN ERP_HOADON HD ON HD.PK_SEQ =HDSP.HOADON_FK    " +  
			   "  	INNER JOIN SANPHAM SP ON HDSP.SANPHAM_FK = SP.PK_SEQ    " +  
			   "  	LEFT JOIN QUYCACH QC ON QC.DVDL2_FK =HDSP.DVDL_FK AND QC.SANPHAM_FK=HDSP.SANPHAM_FK " +  
			   "  	WHERE HDSP.HOADON_FK =   " + this.hdtcId+  
			   "  	AND HDSP.SOLUONG > 0 " +  
			   "  	GROUP BY HDSP.SANPHAM_FK, SP.MA, SP.TEN,HDSP.DVDL_FK,SP.DVDL_FK " +  
			   "  )  " +  
			   "  HOA_DON LEFT JOIN  " +  
			   "  (  " +  
			   "  	SELECT A.SANPHAM_FK, SUM(A.SOLUONG) AS TOTALXUAT  " +  
			   "  	FROM ERP_XUATKHO_SANPHAM A INNER JOIN ERP_XUATKHO B ON A.XUATKHO_FK = B.PK_SEQ  " +  
			   "  	WHERE B.TRANGTHAI IN ( 0,1 ) AND B.HOADON_FK IN (  "+this.hdtcId+")   " +  
			   "  	GROUP BY A.SANPHAM_FK  " +  
			   "  )  " +  
			   "  XUATKHO ON HOA_DON.SPID = XUATKHO.SANPHAM_FK  " +  
			   " where  HOA_DON.SOLUONG - ISNULL(XUATKHO.TOTALXUAT,0)  > 0 ";
			
			ResultSet rsCheck = db.get(query);
			 
			
			try {
				if(!rsCheck.next())
				{
					rsCheck.close();
					return true;
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return false;
	}



	public String getIsKhoXuatQuanLyKV() {
		
		return this.IsKhoXuatQL_Khuvuc;
	}



	public float getSoLuongXuat() {
		
		return this.soluongxuat;
	}



	public void setSoLuongXuat(float soluongxuat) {
		
		this.soluongxuat=soluongxuat;
	}


	@Override
	public String getLoaixuatkho() {
		// TODO Auto-generated method stub
		return this.Loaixuatkho;
	}


	@Override
	public void setLoaixuatkho(String loaixuatkho) {
		// TODO Auto-generated method stub
		this.Loaixuatkho=loaixuatkho;
	}


	@Override
	public ResultSet getRsDonhang() {
		// TODO Auto-generated method stub
		return this.RsDondathang;
	}


	@Override
	public void setRsRsDonhang(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsDondathang=rs;
	}


	@Override
	public String getDDHId() {
		// TODO Auto-generated method stub
		return this.DdhId;
	}


	@Override
	public void setDDHId(String dhid) {
		// TODO Auto-generated method stub
		this.DdhId=dhid;
	}


	@Override
	public void InitXuatKho_From_DDH() {
		// TODO Auto-generated method stub
		try{
			if(this.ndxId.equals("100062")){
				String query="select pk_seq,nhacungcap_fk from erp_muahang where pk_seq="+this.trahangNccId;
				this.Loaixuatkho="DH";
				ResultSet rs=db.get(query);
				if(rs.next()){
					this.nccId=rs.getString("nhacungcap_fk");
				}
				rs.close();
			}else{
				String query="select pk_seq,khachhang_fk from erp_dondathang where pk_seq="+this.DdhId;
				this.Loaixuatkho="DH";
				ResultSet rs=db.get(query);
				if(rs.next()){
					this.nppId=rs.getString("khachhang_fk");
				}
				rs.close();
			}
			
			
			/*// lay thong tin ngay hoa don
			String query="select B.NGAYXUATHD from ERP_HOADON_DDH A INNER JOIN ERP_HOADON B ON A.HOADON_FK= B.PK_SEQ WHERE DDH_FK="+this.DdhId;
			ResultSet rs=db.get(query);
			if(rs.next()){
				this.ngayhoadon=rs.getString("NGAYXUATHD");
			}
			rs.close();*/
			
		}catch(Exception er){
			er.printStackTrace();
		}
	}


	@Override
	public String getNhaphanphoiId() {
		// TODO Auto-generated method stub
		return NhaphanphoiId;
	}


	@Override
	public void setNhaphanphoiId(String _NhaphanphoiId) {
		// TODO Auto-generated method stub
		NhaphanphoiId=_NhaphanphoiId;
	}


	@Override
	public String getCongtyId() {
		// TODO Auto-generated method stub
		return this.CongtyId;
	}


	@Override
	public void setCongtyId(String CongtyId) {
		// TODO Auto-generated method stub
		this.CongtyId=CongtyId;
	}
}
