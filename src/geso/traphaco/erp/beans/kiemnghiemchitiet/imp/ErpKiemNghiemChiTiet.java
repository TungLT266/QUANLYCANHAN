package geso.traphaco.erp.beans.kiemnghiemchitiet.imp;

import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhieuKiemDinh;
import geso.traphaco.erp.beans.kiemnghiemchitiet.IErpKiemNghiemChiTiet;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import com.sun.corba.se.spi.orbutil.fsm.Guard.Result;

public class ErpKiemNghiemChiTiet extends Phan_Trang implements IErpKiemNghiemChiTiet {
	private static final long serialVersionUID = 1L;
	String userId;
	String msg;
	dbutils db;
	private String ngayChungTu;
	private String soTT;
	private String phongBanId;
	private String dienGiai;
	private String thoiGianBD;
	private String thoiGianKT;
	private String hoSoId;
	private String tieuChuanId;
	String yeuCauId;
	private String tongThoiLuong;
	private String danhGia;
	private String id;
	private String ctyId;
	private String nppId; 
	private String sanphamId; 
	private String tieuChuan;
	private String sanphamTen;
	private String hoSo;
	
	/**
	 * Select
	 */
	
 
	private ResultSet rsPhongBan;
	private ResultSet rsHoSo;
	private ResultSet rsYeuCau;
	private List<PhuongPhap> dsPhieuKiemDinhs;
	private List<PhieuKiemDinh> dsHoaChatKiemNghiem;
	private ResultSet rsSanPham; 
	private ResultSet rsTieuChuan;
	private ResultSet rsTieuChuan1;
	private ResultSet rsDanhGia;
	private ResultSet rsKiemNghiemCT;
	private int __sizePhuongPhap;
	
	
	public ErpKiemNghiemChiTiet(){
		this.userId="";
		this.msg="";
		this.id = "";
		this.db = new dbutils();
		this.ngayChungTu = "";
		this.soTT = " ";
		this.dienGiai = "";
		this.phongBanId = "";
		this.thoiGianBD = "";
		this.thoiGianKT = "";
		this.tongThoiLuong = "";
		this.hoSoId = "";
		this.danhGia = "";
		this.yeuCauId = "";
		this.tieuChuanId = "";
		this.ctyId = "";
		this.nppId = "";
		this.tieuChuan = "";
		this.hoSo = "";
		this.sanphamTen = "";
	 
		this.dsPhieuKiemDinhs = new ArrayList<PhuongPhap>();
		this.dsHoaChatKiemNghiem = new ArrayList<PhieuKiemDinh>();
 
		this.sanphamId = "";
	 
		this.__sizePhuongPhap = 0;
 
	}
	
	public ErpKiemNghiemChiTiet(String id){
		this.userId="";
		this.msg="";
		this.id = id;
		this.db = new dbutils();
		this.ngayChungTu = "";
		this.soTT = " ";
		this.dienGiai = "";
		this.phongBanId = "";
		this.thoiGianBD = "";
		this.thoiGianKT = "";
		this.tongThoiLuong = "";
		this.hoSoId = "";
		this.danhGia = "";
		this.yeuCauId = "";
		this.tieuChuanId = "";
		this.ctyId = "";
		this.nppId = "";
		this.tieuChuan = "";
		this.hoSo = "";
		this.dsPhieuKiemDinhs = new ArrayList<PhuongPhap>();
		this.dsHoaChatKiemNghiem = new ArrayList<PhieuKiemDinh>();
		this.sanphamId = ""; 
		this.sanphamTen = "";
		this.__sizePhuongPhap = 0;
 
	}
	
	
	
	public int __sizePhuongPhap() {
		return __sizePhuongPhap;
	}

	public void set__sizePhuongPhap(int __sizePhuongPhap) {
		this.__sizePhuongPhap = __sizePhuongPhap;
	}

	public void createRs(){
		String query = "";
		query = "SELECT PK_SEQ , MA + ' - ' + TEN AS TEN FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI=1";
		this.rsPhongBan = db.get(query); 
		
		query = "SELECT PK_SEQ , MA + ' - ' + TEN AS TEN FROM ERP_SANPHAM WHERE TRANGTHAI=1 ";
		this.rsSanPham = db.get(query);
		
		query = "select PK_SEQ, MA as TEN from ERP_TIEUCHUANKIEMGNGHIEM WHERE TRANGTHAI=1";
		this.rsTieuChuan = db.get(query);
		
		query = "select pk_seq, ma + ' - ' + ten as ten from ERP_YEUCAUKYTHUAT";
		this.rsYeuCau = db.get(query);
		System.out.println("----0-----: " + this.yeuCauId);
		if(this.yeuCauId.trim().length() > 0){
			try {
				if(this.dsPhieuKiemDinhs.size() == 0)
					initPhuongPhapLayMau(this.yeuCauId);
				if(this.dsHoaChatKiemNghiem.size() == 0 ){
					initHoaChatKiemNghiem(this.yeuCauId);
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		query = "select a.PK_SEQ as hosoid, a.MASOKIEMNGHIEM as ma, b.pk_seq as tieuchuanid, b.MA + ' - ' + b.DIENGIAI as ten, "
				+ " SP.PK_SEQ as sanphamId, SP.MA + ' - ' + SP.TEN as tensp \r\n"
				+ " from ERP_HOSOKIEMNGHIEM a \r\n" + 
				"inner join ERP_TIEUCHUANKIEMGNGHIEM b on a.TIEUCHUANKIEMNGHIEM_FK=b.pk_seq \r\n"
				+ " inner join ERP_SANPHAM SP on a.SANPHAM_FK=SP.PK_SEQ \r\n";
		System.out.println("================1: " + query);
		this.rsHoSo = db.get(query);
		if(this.hoSoId.trim().length() > 0){
			query += "where a.pk_seq='"+this.hoSoId+"'";
			System.out.println("================2: " + query);
			this.rsTieuChuan1 = db.get(query);
			if(this.rsTieuChuan1 != null){
				try {
					while(this.rsTieuChuan1.next()){
						this.tieuChuanId = this.rsTieuChuan1.getString("tieuchuanid");
						this.tieuChuan = this.rsTieuChuan1.getString("ten");
						this.sanphamId = this.rsTieuChuan1.getString("sanphamId");
						this.sanphamTen = this.rsTieuChuan1.getString("tensp");
					}
					System.out.println("SAN PHAM FK: " + this.sanphamId);
					System.out.println("TIEU CHUAN FK: " + this.tieuChuanId);
				} catch (SQLException e) { 
					e.printStackTrace();
				}
			}
		}
		
		query = " select 'M1' as ma, N'M1 - Hoàn thành vượt định mức' as ten\r\n" + 
				" union\r\n" + 
				" select 'M2' as ma, N'M2 - Hoàn thành đúng định mức' as ten\r\n" + 
				" union\r\n" + 
				" select 'M3' as ma, N'M3 - Không đạt định mức' as ten ";
		this.rsDanhGia = db.get(query);
	}
	
	public boolean create(){
		 
		String ngaytao = this.getDateTime(); 
		try{
			db.getConnection().setAutoCommit(false);
 
			String query = "INSERT ERP_KIEMNGHIEMCHITIET(DVTH_FK, HOSOKIEMNGHIEM_FK, SANPHAM_FK, TIEUCHUANKIEMNGHIEM_FK, "
					+ " YEUCAUKYTHUAT_FK, THOIGIANBATDAUTN, THOIGIANKETTHUCTN, TONGTHOILUONG, DIENGIAI, NGAYCHUNGTU, "
					+ " NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, CONGTY_FK, TRANGTHAI, MADANHGIA)\r\n"
					+ " VALUES('"+this.phongBanId+"', '"+this.hoSoId+"', '"+this.sanphamId+"', '"+this.tieuChuanId+"', "
					+ " '"+this.yeuCauId+"', '"+this.thoiGianBD+"', '"+this.thoiGianKT+"', "+this.tongThoiLuong+", N'"+this.dienGiai+"', '"+this.ngayChungTu+"', "
					+ " '"+this.userId+"', '"+ngaytao+"', '"+this.userId+"',  '"+ngaytao+"', '"+this.ctyId+"', 0, '"+this.danhGia+"')";
			
			System.out.println("_____query_1____: " + query);
			
			
			if(!db.update(query))
			{
				this.msg = "Không thể tạo ERP_KIEMNGHIEMCHITIET lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 
			
			String currentId = "";//Lay PK_SEQ vua insert
			query = "select SCOPE_IDENTITY() as Id";
		
			ResultSet rsLmkn = db.get(query);						
			if(rsLmkn.next())
			{
				currentId = rsLmkn.getString("Id");
				rsLmkn.close();
			}
			
			for(PhieuKiemDinh pkd : getDsHoaChatKiemNghiem()){ 
				
				if(pkd.getStt()!=null && pkd.getStt().length() > 0){
					query = "INSERT ERP_KIEMNGHIEMCHITIET_HOACHAT(STT, KIEMNGHIEMCHITIET_FK, HOACHAT, SOLUONGTT, MASO,CACHPHA, SOLUONGDM)"
							+ " VALUES("+pkd.getStt()+", '"+currentId+"', '"+pkd.getHoaChat()+"', "
							+ "'"+pkd.getSoLuongThucTe()+"', '"+pkd.getMaSo()+"', N'"+pkd.getCachPha()+"', '"+pkd.getSoLuongDinhMuc()+"')";
					
					System.out.println("_____query_2____: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo ERP_KIEMNGHIEMCHITIET_HOACHAT lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			 } 
			
			for(PhuongPhap pp : getDsPhieuKiemDinhs()){
				query = "INSERT INTO ERP_KIEMNGHIEMCHITIET_PHUONGPHAP(KIEMNGHIEMCHITIET_FK, PHUONGPHAP_FK, KETQUA, STT)\r\n" + 
						"SELECT '"+currentId+"', '"+pp.getId()+"' , N'"+pp.getDienGiai()+"', '"+pp.getSott()+"'";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo ERP_KIEMNGHIEMCHITIET_PHUONGPHAP lỗi: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				// Tong
				
				for(PhieuKiemDinh pkd : pp.getDsPhieuKiemDinhs()){
					String query1 = "INSERT INTO ERP_PHUONGPHAP_CHITIET(PHUONGPHAP_FK, STT, KIEMNGHIEMCHITIET_FK, KYHIEU, THAMSO, DONVITINH, SOTT_MAU, GIATRIMAU)\r\n" + 
							"SELECT '"+pp.getId()+"', '"+pkd.getStt()+"', '"+currentId+"', '"+pkd.getKyHieu()+"', '"+pkd.getTenThamSo()+"', N'"+pkd.getDonViTinh()+"'";
					System.out.println("THAM SO MAU: " + pkd.getThamSoMau().size());
					for(int i = 0; i < pkd.getThamSoMau().size(); i++){
						String ts = pkd.getThamSoMau().get(i);
						query = query1 + ","+i+", N'"+ts+"' ";
						if(!db.update(query))
						{
							this.msg = "Không thể tạo ERP_PHUONGPHAP_CHITIET lỗi: " + query;
							db.getConnection().rollback();
							return false;
						} 
					} 
				}
 
			}
			 
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
 
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		 
	}
	
	public boolean update(){
		
		
		String ngay = this.getDateTime();
 
		try{
			db.getConnection().setAutoCommit(false);
  
			String query = "update ERP_KIEMNGHIEMCHITIET set ngaychungtu='"+this.ngayChungTu+"', dvth_fk='"+this.phongBanId+"',"
					+ " hosokiemnghiem_fk='"+this.hoSoId+"', sanpham_fk='"+this.sanphamId+"', tieuchuankiemnghiem_fk='"+this.tieuChuanId+"', "
					+ " yeucaukythuat_fk='"+this.yeuCauId+"', thoigianbatdautn='"+this.thoiGianBD+"', thoigianketthuctn='"+this.thoiGianKT+"',"
					+ " tongthoiluong="+this.tongThoiLuong+", diengiai='"+this.dienGiai+"', ngaytao='"+ngay+"', nguoitao='"+this.userId+"', ngaysua='"+ngay+"',"
					+ " nguoisua='"+this.userId+"', congty_fk='"+this.ctyId+"', madanhgia='"+this.danhGia+"' "
					+ " where pk_seq='"+this.id+"'";
			
			System.out.println("_____query_1____: " + query);
			
			
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_KIEMNGHIEMCHITIET lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 
			
			query = "DELETE FROM ERP_KIEMNGHIEMCHITIET_HOACHAT WHERE KIEMNGHIEMCHITIET_FK = " + this.id;
			System.out.println("DELETE HOA CHAT KIEM NGHIEM CHI TIET: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể xóa ERP_KIEMNGHIEMCHITIET_HOACHAT lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 
			
			query = "DELETE FROM ERP_KIEMNGHIEMCHITIET_PHUONGPHAP WHERE KIEMNGHIEMCHITIET_FK = " + this.id;
			System.out.println("DELETE PHUONG PHAP KIEM NGHIEM CHI TIET: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể xóa ERP_KIEMNGHIEMCHITIET_PHUONGPHAP lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 
			
			query = "DELETE FROM ERP_PHUONGPHAP_CHITIET WHERE KIEMNGHIEMCHITIET_FK = " + this.id;
			System.out.println("DELETE CHI TIET PHUONG PHAP: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể xóa ERP_PHUONGPHAP_CHITIET lỗi: " + query;
				db.getConnection().rollback();
				return false;
			} 
			
			
			for(PhieuKiemDinh pkd : getDsHoaChatKiemNghiem()){ 
				
				if(pkd.getStt()!=null && pkd.getStt().length() > 0){
					query = "INSERT ERP_KIEMNGHIEMCHITIET_HOACHAT(STT, KIEMNGHIEMCHITIET_FK, HOACHAT, SOLUONGTT, MASO,CACHPHA, SOLUONGDM)"
							+ " VALUES("+pkd.getStt()+", '"+this.id+"', '"+pkd.getHoaChat()+"', "
							+ "'"+pkd.getSoLuongThucTe()+"', '"+pkd.getMaSo()+"', N'"+pkd.getCachPha()+"', '"+pkd.getSoLuongDinhMuc()+"')";
					
					System.out.println("_____query_2____: " + query);
					if(!db.update(query))
					{
						this.msg = "Không thể tạo ERP_KIEMNGHIEMCHITIET_HOACHAT lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
			 } 
			
			for(PhuongPhap pp : getDsPhieuKiemDinhs()){
				query = "INSERT INTO ERP_KIEMNGHIEMCHITIET_PHUONGPHAP(KIEMNGHIEMCHITIET_FK, PHUONGPHAP_FK, KETQUA, STT)\r\n" + 
						"SELECT '"+this.id+"', '"+pp.getId()+"' , N'"+pp.getDienGiai()+"', '"+pp.getSott()+"'";
				if(!db.update(query))
				{
					this.msg = "Không thể tạo ERP_KIEMNGHIEMCHITIET_PHUONGPHAP lỗi: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				// Tong
				
				for(PhieuKiemDinh pkd : pp.getDsPhieuKiemDinhs()){
					String query1 = "INSERT INTO ERP_PHUONGPHAP_CHITIET(PHUONGPHAP_FK, STT, KIEMNGHIEMCHITIET_FK, KYHIEU, THAMSO, DONVITINH, SOTT_MAU, GIATRIMAU)\r\n" + 
							"SELECT '"+pp.getId()+"', '"+pkd.getStt()+"', '"+this.id+"', '"+pkd.getKyHieu()+"', '"+pkd.getTenThamSo()+"', N'"+pkd.getDonViTinh()+"'";
					System.out.println("INSERT PHUONG PHAP CHI TIET: " + query);
					for(int i = 0; i < pkd.getThamSoMau().size(); i++){
						String ts = pkd.getThamSoMau().get(i);
						query = query1 + ","+i+", N'"+ts+"' ";
						System.out.println("_____query_3____: " + query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo ERP_PHUONGPHAP_CHITIET lỗi: " + query;
							db.getConnection().rollback();
							return false;
						} 
					} 
				}
 
			}


			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
 
		}catch(Exception e){
			e.printStackTrace();
			return false;
		}
		 
	}
	
	public void initPhuongPhapLayMau(String id) throws SQLException{
		 
		String _id = null;
		if(this.id.trim().length() > 0)
			_id = this.id;
		
		String query = "select a.PK_SEQ, SOLUONGMAU, a.MA + ' - ' + a.TEN as TEN, isnull(c.KETQUA, '') KETQUA  from ERP_PHUONGPHAPTHUNGHIEM a\r\n" + 
				" inner join ERP_YEUCAUKYTHUAT b on a.YEUCAUKYTHUAT=b.PK_SEQ\r\n" + 
				" left join ERP_KIEMNGHIEMCHITIET_PHUONGPHAP c on c.PHUONGPHAP_FK = a.PK_SEQ AND c.KIEMNGHIEMCHITIET_FK = "+_id+" \r\n" + 
				"where a.YEUCAUKYTHUAT=" + id;
		System.out.println(query);
		ResultSet mau = db.get(query);
		while(mau.next()){
			PhuongPhap pp = new PhuongPhap();
			pp.set__sizePhuongPhap(mau.getInt("SOLUONGMAU"));
			pp.setTenPhuongPhap(mau.getString("TEN"));
			pp.setDienGiai(mau.getString("KETQUA"));
			pp.setId(mau.getString("PK_SEQ"));
			initPhuongPhapChiTiet(pp,id, _id);
			this.dsPhieuKiemDinhs.add(pp);
		}
		mau.close();
		
	}
	
	private void initPhuongPhapChiTiet(PhuongPhap pp, String id2, String _id) throws SQLException {
		// TODO Auto-generated method stub
		String query = "select TS.STT STT, POPUPTHAMSO,ISNULL(CONGTHUC, '')CONGTHUC , ISNULL(DVDL.DONVI,'') DONVI,KYHIEUTHAMSO\r\n" + 
				", LOAITICK, THAMSOGROUP , ISNULL(TS.MIN, 0)MIN, ISNULL(TS.MAX, 0)MAX,ISNULL(TS.AVG, 0)AVG,ISNULL(TS.SUM, 0)SUM\r\n" + 
				"from ERP_PHUONGPHAPTHUNGHIEM_THAMSO TS\r\n" + 
				"inner join ERP_PHUONGPHAPTHUNGHIEM PPTN on PPTN.PK_SEQ=TS.PHUONGPHAPTHUNGHIEM_FK\r\n" + 
				"inner JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ = TS.DVDL_FK inner join ERP_YEUCAUKYTHUAT YCKT on YCKT.PK_SEQ=PPTN.YEUCAUKYTHUAT\r\n" + 
				"left join ERP_KIEMNGHIEMCHITIET_PHUONGPHAP KNCT ON KNCT.PHUONGPHAP_FK = PPTN.PK_SEQ AND KIEMNGHIEMCHITIET_FK = "+_id+"\r\n" + 
				"where PPTN.PK_SEQ='"+pp.getId()+"'\r\n" + 
				"ORDER BY TS.STT";
		System.out.println(query);
		ResultSet rs = db.get(query);
		int i = 1;
		while(rs.next()){
			PhieuKiemDinh pkd = new PhieuKiemDinh();
			pkd.setSott(rs.getString("STT"));
			pkd.setTenThamSo(rs.getString("POPUPTHAMSO"));
			pkd.setDonViTinh(rs.getString("DONVI"));
			pkd.setKyHieu(rs.getString("KYHIEUTHAMSO"));
			pkd._CongThuc(rs.getString("CONGTHUC"));
			if(rs.getInt("MIN") == 1) pkd.setMin(true);
			if(rs.getInt("MAX") == 1) pkd.setMax(true);
			if(rs.getInt("AVG") == 1) pkd.setAvg(true);
			if(rs.getInt("SUM") == 1) pkd.setSum(true);
			if (pkd.getCongThuc().trim().length() > 0) pkd.setCongThuc(true);
			if(rs.getInt("THAMSOGROUP") == 1) pkd.setGroup(true);
			if(rs.getInt("LOAITICK") == 1) pkd.setDanhGia(true);
			layGiaTriMau(pkd, pp.getId());
			pp.getDsPhieuKiemDinhs().add(pkd);
		}
		
		rs.close();
	}

	private void layGiaTriMau(PhieuKiemDinh pkd, String id2) throws SQLException {
		if(this.id.trim().length() > 0){
			String query = "select GIATRIMAU from ERP_PHUONGPHAP_CHITIET where KIEMNGHIEMCHITIET_FK="+this.id+" and PHUONGPHAP_FK="+id2+"\r\n" + 
					"and THAMSO='"+pkd.getTenThamSo()+"' and DONVITINH = N'"+pkd.getDonViTinh()+"' and STT="+pkd.getSott()+" \r\n" + 
					"order by SOTT_MAU";
			System.out.println( " ::: LAY GIA TRI MAU ::: " + query);
			ResultSet rs = db.get(query);
			pkd.getThamSoMau().clear();
			while(rs.next()){
				pkd.getThamSoMau().add(rs.getString("GIATRIMAU"));
			}
		
		}
		
	}

	public void initHoaChatKiemNghiem(String id){
		try {
			String query = "select YCKT_CT.STT STT, SP.MA + ' - ' + SP.TEN TEN, YCKT_CT.SOLUONG SOLUONG, YCKT_CT.MASO MASO, YCKT_CT.CACHPHA CACHPHA\r\n" + 
					"from  ERP_YEUCAUKYTHUAT YCKT\r\n" + 
					"inner join ERP_YEUCAUKYTHUAT_CHITIET YCKT_CT on YCKT.PK_SEQ=YCKT_CT.YEUCAUKYTHUAT_FK\r\n" + 
					"inner join ERP_SANPHAM SP on YCKT_CT.SANPHAM_FK=SP.PK_SEQ\r\n" + 
					"where YCKT.PK_SEQ='"+this.yeuCauId+"'";
			System.out.println("DANH SACH HOA CHAT: " + query);
			ResultSet rs = db.get(query);
			if(rs != null){
				
					while(rs.next()){
						PhieuKiemDinh pkd = new PhieuKiemDinh();
						pkd.setStt(rs.getString("STT"));
						pkd.setHoaChat(rs.getString("TEN"));
						pkd.setSoLuongDinhMuc(rs.getString("SOLUONG"));
						pkd.setMaSo(rs.getString("MASO"));
						pkd.setCachPha(rs.getString("CACHPHA"));
						this.dsHoaChatKiemNghiem.add(pkd);
					}
			}
			rs.close();
		}catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	public String getSanphamId() {
		return this.sanphamId;
	}

	public void setSanphamId(String sanphamId) {
		this.sanphamId = sanphamId;
	}

	public ResultSet getRsSanPham() {
		return this.rsSanPham;
	}

	public void setRsSanPham(ResultSet rsSanPham) {
		this.rsSanPham = rsSanPham;
	}
	
	
	
	/*private void layPhieuKiemDinh(String soPhieuKiemDinh2, String sanphamId) {
		this.dsPhieuKiemDinhs.clear();
		String query =
		
				"SELECT SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,'') NGAYNHAPKHO, CT.NGAYHETHAN, \r\n" + 
				" CT.MATHUNG, CT.MAME, SUM( CT.SOLUONG ) SOLUONG  FROM ERP_YEUCAUKIEMDINH_THUNG_CHITIET CT\r\n" + 
				" INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ= CT.SANPHAM_FK\r\n" + 
				" INNER JOIN DONVIDOLUONG DVT ON DVT.PK_SEQ = SP.DVDL_FK\r\n" + 
				" WHERE CT.YEUCAUKIEMDINH_FK = '"+soPhieuKiemDinh2+"' \r\n" +
				" AND CT.SANPHAM_FK = " + sanphamId + " \r\n"+
				" GROUP BY SP.PK_SEQ, SP.TEN, SP.MA, DVT.DONVI, CT.SOLO, ISNULL(CT.NGAYNHAPKHO,''), CT.NGAYHETHAN, \r\n" + 
				" CT.MATHUNG, CT.MAME";
		System.out.println(query);
		ResultSet rs = db.get(query);
		try {
			PhieuKiemDinh phieuKiemDinh = null;
			while(rs.next()){
				phieuKiemDinh = new PhieuKiemDinh();
				phieuKiemDinh.setMaSp(rs.getString("MA"));
				phieuKiemDinh.setTenSp(rs.getString("TEN"));
				phieuKiemDinh.setDvt(rs.getString("DONVI"));
				phieuKiemDinh.setSanPhamId(rs.getString("PK_SEQ"));
				phieuKiemDinh.setLoHang(rs.getString("SOLO"));
				phieuKiemDinh.setNgaySX(rs.getString("NGAYNHAPKHO"));
				phieuKiemDinh.setNgayHetHan(rs.getString("NGAYHETHAN"));
				phieuKiemDinh.setMaThung(rs.getString("MATHUNG"));
				phieuKiemDinh.setMaMe(rs.getString("MAME"));
				phieuKiemDinh.setSoLuongPhieuNop(rs.getString("SOLUONG"));
				
				this.dsPhieuKiemDinhs.add(phieuKiemDinh);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}*/
	
 
	 
	public ResultSet getRsKiemNghiemCT() {
		return this.rsKiemNghiemCT;
	}

	public void setRsKiemNghiemCT(ResultSet rsKiemNghiemCT) {
		this.rsKiemNghiemCT = rsKiemNghiemCT;
	}

	public ResultSet getRsTieuChuan1() {
		return rsTieuChuan1;
	}

	public void setRsTieuChuan1(ResultSet rsTieuChuan1) {
		this.rsTieuChuan1 = rsTieuChuan1;
	}

	public String getSanphamTen() {
		return this.sanphamTen;
	}

	public void setSanphamTen(String sanphamTen) {
		this.sanphamTen = sanphamTen;
	}

	public List<PhieuKiemDinh> getDsHoaChatKiemNghiem() {
		return this.dsHoaChatKiemNghiem;
	}

	public void setDsHoaChatKiemNghiem(List<PhieuKiemDinh> dsHoaChatKiemNghiem) {
		this.dsHoaChatKiemNghiem = dsHoaChatKiemNghiem;
	}

	public String getHoSo() {
		return hoSo;
	}

	public void setHoSo(String hoSo) {
		this.hoSo = hoSo;
	}

	public String getTieuChuan() {
		return this.tieuChuan;
	}

	public void setTieuChuan(String tieuChuan) {
		this.tieuChuan = tieuChuan;
	}

	public ResultSet getRsTieuChuan() {
		return this.rsTieuChuan;
	}

	public void setRsTieuChuan(ResultSet rsTieuChuan) {
		this.rsTieuChuan = rsTieuChuan;
	}

	public String getNgayChungTu() {
		return ngayChungTu;
	}

	public void setNgayChungTu(String ngayChungTu) {
		this.ngayChungTu = ngayChungTu;
	}

	public List<PhuongPhap> getDsPhieuKiemDinhs() {
		return dsPhieuKiemDinhs;
	}

	public void setDsPhieuKiemDinhs(List<PhuongPhap> dsPhieuKiemDinhs) {
		this.dsPhieuKiemDinhs = dsPhieuKiemDinhs;
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

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public dbutils getDb() {
		return db;
	}

	public void setDb(dbutils db) {
		this.db = db;
	}

	 
	public String getDienGiai() {
		return dienGiai;
	}

	public void setDienGiai(String dienGiai) {
		this.dienGiai = dienGiai;
	}
 
	public ResultSet getRsPhongBan() {
		return rsPhongBan;
	}

	public void setRsPhongBan(ResultSet rsPhongBan) {
		this.rsPhongBan = rsPhongBan;
	}
 
	public void DBclose() {
 
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
 
	public String getSoTT() {
		return this.soTT;
	}

	public void setSoTT(String soTT) {
		this.soTT = soTT;
	}
	
	

	public void init() {
		String query = "select kn.NGAYCHUNGTU, kn.DVTH_FK, kn.HOSOKIEMNGHIEM_FK, kn.SANPHAM_FK, kn.TIEUCHUANKIEMNGHIEM_FK, \r\n" + 
				"kn.YEUCAUKYTHUAT_FK, kn.THOIGIANBATDAUTN, kn.THOIGIANKETTHUCTN, kn.TONGTHOILUONG, kn.madanhgia, kn.DIENGIAI\r\n" + 
				"from ERP_KIEMNGHIEMCHITIET kn where 1=1 ";
		
		if(this.id != null && this.id.trim().length() > 0){
			query += " AND kn.PK_SEQ = " + this.id;
		}
		
		System.out.println("________init sp 2 2 2 2______" + query);
		
		ResultSet rs = db.get(query);
		try {
			if(rs.next()){
				this.ngayChungTu = rs.getString("ngaychungtu");
				this.phongBanId = rs.getString("dvth_fk");
				this.hoSoId = rs.getString("hosokiemnghiem_fk");
				this.sanphamId = rs.getString("sanpham_fk");
				this.tieuChuanId = rs.getString("tieuchuankiemnghiem_fk");
				this.yeuCauId = rs.getString("yeucaukythuat_fk");
				this.thoiGianBD = rs.getString("thoigianbatdautn");
				this.thoiGianKT = rs.getString("THOIGIANKETTHUCTN");
				this.tongThoiLuong = rs.getString("TONGTHOILUONG");
				this.danhGia = rs.getString("madanhgia");
				this.dienGiai = rs.getString("diengiai");
				initHoaChat(this.id);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
 
	private void initHoaChat(String id2) throws SQLException {
		String query = "select knhc.stt, knhc.KIEMNGHIEMCHITIET_FK , knhc.HOACHAT, knhc.SOLUONGTT, knhc.MASO, knhc.CACHPHA, knhc.SOLUONGDM\r\n" + 
				"from ERP_KIEMNGHIEMCHITIET_HOACHAT knhc \r\n"
				+ " where 1=1";
		if(id2 !=null && id2.trim().length() > 0){
			query += " AND KIEMNGHIEMCHITIET_FK=" + id2 + " \r\n";
		}
		query += "order by STT asc";
		System.out.println("INIT HOA CHAT: " + query);
		ResultSet rs = db.get(query);
		PhieuKiemDinh pkd = null;
		
		while(rs.next()){
			pkd = new PhieuKiemDinh();
			pkd.setStt(rs.getString("stt"));
			pkd.setHoaChat(rs.getString("hoachat"));
			pkd.setSoLuongThucTe(rs.getString("SOLUONGTT"));
			pkd.setSoLuongDinhMuc(rs.getString("SOLUONGDM"));
			pkd.setMaSo(rs.getString("maso"));
			pkd.setCachPha(rs.getString("cachpha")); 
			this.dsHoaChatKiemNghiem.add(pkd);
		}
	}
 
	public ResultSet getRsDanhGia() {
		return rsDanhGia;
	}

	public void setRsDanhGia(ResultSet rsDanhGia) {
		this.rsDanhGia = rsDanhGia;
	}

	public ResultSet getRsYeuCau() {
		return rsYeuCau;
	}

	public void setRsYeuCau(ResultSet rsYeuCau) {
		this.rsYeuCau = rsYeuCau;
	}
 
	public String getPhongBanId() {
		 
		return this.phongBanId;
	}

 
	public void setPhongBanId(String phongBanId) {
		 this.phongBanId = phongBanId;
		
	}

	 
	public boolean chot(String Id){
		
		dbutils db = new dbutils();
		String msg = "";
		
		try
		{
			 
			
			String query = "update ERP_KIEMNGHIEMCHITIET set TRANGTHAI=1 where pk_seq='"+Id+"'";
			
			System.out.println("lllllllloooooollllllll: " + query);
			
			if(!db.update(query))
			{
				msg = "Khong the chot: " + query;
				 
				return false;
			}
			
		 
			
		}catch(Exception e){
		 
			e.printStackTrace();
			return false;
		}
		return true;
	}
	
	public boolean bochot(String Id){
		
		dbutils db = new dbutils();
		String msg = "";
		
		try
		{
			 
			
			String query = "update ERP_KIEMNGHIEMCHITIET set TRANGTHAI=0 where pk_seq='"+Id+"'";
			
			System.out.println("lllllllloooooollllllll: " + query);
			
			if(!db.update(query))
			{
				msg = "Khong the bo chot: " + query;
				 
				return false;
			}
			
		 
			
		}catch(Exception e){
		 
			e.printStackTrace();
			return false;
		}
		return true;
	}
 
	public boolean xoa(String Id){
		
		dbutils db = new dbutils();
		String msg = "";
		
		try
		{
			 
			
			String query = "update ERP_KIEMNGHIEMCHITIET set TRANGTHAI=2 where pk_seq='"+Id+"'";
			
			System.out.println("lllllllloooooollllllll: " + query);
			
			if(!db.update(query))
			{
				msg = "Khong the xoa: " + query;
				 
				return false;
			}
			
		 
			
		}catch(Exception e){
		 
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public ResultSet getRsHoSo() {
		return this.rsHoSo;
	}

	@Override
	public void setRsHoSo(ResultSet rsHoSo) {
		 this.rsHoSo = rsHoSo;
	}

	@Override
	public String getHoSoId() {
		return this.hoSoId;
	}

	@Override
	public void setHoSoId(String hoSoId) {
		this.hoSoId = hoSoId;
		
	}

	@Override
	public String getTieuChuanId() {
		return this.tieuChuanId;
	}

	@Override
	public void setTieuChuanId(String tieuChuanId) {
		this.tieuChuanId = tieuChuanId;
		
	}

	@Override
	public String getYeuCauId() {
		return this.yeuCauId;
	}

	@Override
	public void setYeuCauId(String yeuCauId) {
		this.yeuCauId = yeuCauId;
		
	}

	@Override
	public String getThoiGianBD() {
		return this.thoiGianBD;
	}

	@Override
	public void setThoiGianBD(String thoiGianBD) {
		this.thoiGianBD = thoiGianBD;
		
	}

	@Override
	public String getThoiGianKT() {
		return this.thoiGianKT;
	}

	@Override
	public void setThoiGianKT(String thoiGianKT) {
		this.thoiGianKT = thoiGianKT;
		
	}

	@Override
	public String getTongThoiLuong() {
		return this.tongThoiLuong;
	}

	@Override
	public void setTongThoiLuong(String tongThoiLuong) {
		this.tongThoiLuong = tongThoiLuong;
	}

	@Override
	public String getDanhGia() {
		return this.danhGia;
	}

	@Override
	public void setDanhGia(String danhGia) {
		this.danhGia = danhGia;
		
	}
}
