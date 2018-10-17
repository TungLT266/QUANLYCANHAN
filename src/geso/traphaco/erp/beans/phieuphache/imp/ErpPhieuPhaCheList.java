package geso.traphaco.erp.beans.phieuphache.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.phieuphache.IErpPhieuPhaCheList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpPhieuPhaCheList implements IErpPhieuPhaCheList {
	private String userId;
	private String congtyId;
	private String ngaychungtu;
	private String sanpham;
	private String nguoiphache;
	private String trangthai;
	private String msg;
	
	private ResultSet SanphamRs;
	private ResultSet PhieuphacheRs;
	dbutils db;
	
	public ErpPhieuPhaCheList() {
		this.ngaychungtu = "";
		this.sanpham = "";
		this.nguoiphache = "";
		this.trangthai = "";
		this.msg = "";
		
		this.db = new dbutils();
	}
	
	public void init(){
		String query = "select ppc.pk_seq, ppc.ngaychungtu, sp.ma + ' - ' + sp.ten as sanpham, ppc.nguoiphache, ppc.trangthai, nvt.TEN as nguoitao, nvs.TEN as nguoisua, ppc.ngaytao, ppc.ngaysua"
				+ " from ERP_PHIEUPHACHE ppc inner join nhanvien nvt on nvt.PK_SEQ = ppc.nguoitao inner join nhanvien nvs on nvs.PK_SEQ = ppc.nguoisua"
				+ " inner join erp_sanpham sp on sp.pk_seq = ppc.sanpham_fk"
				+ " where ppc.congty_fk = " + this.congtyId;
		
		if(this.ngaychungtu.length() > 0){
			query += " and ppc.ngaychungtu like '%" + this.ngaychungtu + "%'";
		}
		
		if(this.sanpham.length() > 0){
			query += " and ppc.sanpham_fk = '" + this.sanpham + "'";
		}
		
		if(this.nguoiphache.length() > 0){
			Utility util = new Utility();
			query += " and dbo.ftBoDau(ppc.nguoiphache) like N'%" + util.replaceAEIOU(this.nguoiphache) + "%'";
		}
		
		if(this.trangthai.length() > 0){
			query += " and ppc.trangthai = '" + this.trangthai + "'";
		}
		
		query += " order by ppc.pk_seq desc";
		
		this.PhieuphacheRs = this.db.get(query);
		
		createRS();
	}
	
	public void createRS(){
		String query = "select PK_SEQ, MA + ' - ' + TEN as ten from ERP_SANPHAM";
		this.SanphamRs = this.db.get(query);
	}
	
	public void delete(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			//Cộng lại kho
			String query = "select ppc_sp.KHOTT_FK,tlpc_tt.SANPHAM_FK,ppc_spct.soluongchitiet,ppc_spct.solo,ppc_spct.ngayhethan,"
					+ "ppc_spct.bin_fk,ppc_spct.ngaynhapkho,ppc_spct.marq,ppc_spct.hamluong,ppc_spct.hamam,ppc_spct.loaidoituong,"
					+ "ppc_spct.doituongid,ppc_spct.mame,ppc_spct.mathung,ppc_spct.maphieu,ppc_spct.maphieudinhtinh,ppc_spct.phieueo,ppc_spct.nsx_fk"
					+ " from ERP_PHIEUPHACHE_SP_CHITIET ppc_spct inner join ERP_PHIEUPHACHE_SANPHAM ppc_sp on ppc_sp.PK_SEQ=ppc_spct.PHIEUPHACHE_SP_FK"
					+ " inner join ERP_TAILIEUPHACHETHUOC_THONGTIN tlpc_tt on tlpc_tt.PK_SEQ=ppc_sp.TLPC_TT_FK where ppc_spct.PHIEUPHACHE_FK="+id;
			System.out.println(query);
			ResultSet rs = this.db.get(query);
			while(rs.next()){
				String khoId = rs.getString("khott_fk");
				String spId = rs.getString("sanpham_fk");
				String solo = rs.getString("solo");
				String ngayhethan = rs.getString("ngayhethan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongid") == null ? "" : rs.getString("doituongid");
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk");
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("maphieudinhtinh");
				String phieueo = rs.getString("phieueo");
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");
				String nsx_fk = rs.getString("nsx_fk") == null ? "" : rs.getString("nsx_fk");
				double soluong = rs.getDouble("soluongchitiet");
				
				Utility util = new Utility();
				
				this.msg = util.Update_KhoTT_MOI("","",db,khoId,spId,solo,ngayhethan,ngaynhapkho,mame,mathung,bin_fk,maphieu,
						phieudt,phieueo,marq,hamluong,hamam,loaidoituong,doituongId,0,-1 * soluong,soluong, nsx_fk);
				if(this.msg.trim().length() > 0){
					db.getConnection().rollback();
				}
			}
			rs.close();
			
			query = "update ERP_PHIEUPHACHE set trangthai = '2' where pk_seq = " + id;
			if(!db.update(query)) {
				this.msg = "Không thể cập nhât ERP_PHIEUPHACHE: " + query;
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Xóa không thành công";
		}
	}
	
	public void chot(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			Utility util = new Utility();
			
			String khottid;
			String sanphamid;
			String query = "select khott_fk,sanpham_fk from ERP_PHIEUPHACHE where pk_seq=" + id;
			
			ResultSet rs = this.db.get(query);
			rs.next();
			khottid = rs.getString("khott_fk");
			sanphamid = rs.getString("sanpham_fk");
			rs.close();
			
			query = "select solo, isnull(cast(bin_fk as varchar),'') as vitri, mathung, mame, soluongchitiet"
					+ " from ERP_PHIEUPHACHE_NHAPKHO where phieuphache_fk = " + id;
			System.out.println(query);
			rs = this.db.get(query);
			Double soluong;
			while(rs.next()){
				soluong = rs.getDouble("soluongchitiet");
				msg = util.Update_KhoTT_MOI("","",db,khottid,sanphamid,rs.getString("solo"),"","",rs.getString("mame"),
						rs.getString("mathung"),rs.getString("vitri"),"","","","","","","","",soluong,0,soluong,"");
			}
			rs.close();
			
			//Trừ kho
			query = "select ppc_sp.KHOTT_FK,tlpc_tt.SANPHAM_FK,ppc_spct.soluongchitiet,ppc_spct.solo,ppc_spct.ngayhethan,"
					+ "ppc_spct.bin_fk,ppc_spct.ngaynhapkho,ppc_spct.marq,ppc_spct.hamluong,ppc_spct.hamam,ppc_spct.loaidoituong,"
					+ "ppc_spct.doituongid,ppc_spct.mame,ppc_spct.mathung,ppc_spct.maphieu,ppc_spct.maphieudinhtinh,ppc_spct.phieueo,ppc_spct.nsx_fk"
					+ " from ERP_PHIEUPHACHE_SP_CHITIET ppc_spct inner join ERP_PHIEUPHACHE_SANPHAM ppc_sp on ppc_sp.PK_SEQ=ppc_spct.PHIEUPHACHE_SP_FK"
					+ " inner join ERP_TAILIEUPHACHETHUOC_THONGTIN tlpc_tt on tlpc_tt.PK_SEQ=ppc_sp.TLPC_TT_FK where ppc_spct.PHIEUPHACHE_FK="+id;
			System.out.println(query);
			rs = this.db.get(query);
			soluong = (double) 0;
			while(rs.next()){
				String khoId = rs.getString("khott_fk");
				String spId = rs.getString("sanpham_fk");
				String solo = rs.getString("solo");
				String ngayhethan = rs.getString("ngayhethan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongid") == null ? "" : rs.getString("doituongid");
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk") == null ? "" : rs.getString("bin_fk");
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("maphieudinhtinh");
				String phieueo = rs.getString("phieueo");
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");
				String nsx_fk = rs.getString("nsx_fk") == null ? "" : rs.getString("nsx_fk");
				soluong = rs.getDouble("soluongchitiet");
				
				this.msg = util.Update_KhoTT_MOI("","",db,khoId,spId,solo,ngayhethan,ngaynhapkho,mame,mathung,bin_fk,maphieu,
						phieudt,phieueo,marq,hamluong,hamam,loaidoituong,doituongId,-1 * soluong,-1 * soluong,0, nsx_fk);
				if(this.msg.trim().length() > 0){
					db.getConnection().rollback();
				}
			}
			rs.close();
			
			query = "update ERP_PHIEUPHACHE set trangthai = '1' where pk_seq = " + id;
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Loi";
		}
	}
	
	public void bochot(String id){
		try {
			db.getConnection().setAutoCommit(false);
			
			Utility util = new Utility();
			
			String khottid;
			String sanphamid;
			String query = "select khott_fk,sanpham_fk from ERP_PHIEUPHACHE where pk_seq=" + id;
			
			ResultSet rs = this.db.get(query);
			rs.next();
			khottid = rs.getString("khott_fk");
			sanphamid = rs.getString("sanpham_fk");
			rs.close();
			
			query = "select solo, isnull(cast(bin_fk as varchar),'') as vitri, mathung, mame, soluongchitiet"
					+ " from ERP_PHIEUPHACHE_NHAPKHO where phieuphache_fk = " + id;
			System.out.println(query);
			rs = this.db.get(query);
			Double soluong;
			while(rs.next()){
				soluong = rs.getDouble("soluongchitiet");
				msg = util.Update_KhoTT_MOI("","",db,khottid,sanphamid,rs.getString("solo"),"","",rs.getString("mame"),
						rs.getString("mathung"),rs.getString("vitri"),"","","","","","","","",-1 * soluong,0,-1 * soluong,"");
			}
			rs.close();
			
			//Trừ kho
			query = "select ppc_sp.KHOTT_FK,tlpc_tt.SANPHAM_FK,ppc_spct.soluongchitiet,ppc_spct.solo,ppc_spct.ngayhethan,"
					+ "ppc_spct.bin_fk,ppc_spct.ngaynhapkho,ppc_spct.marq,ppc_spct.hamluong,ppc_spct.hamam,ppc_spct.loaidoituong,"
					+ "ppc_spct.doituongid,ppc_spct.mame,ppc_spct.mathung,ppc_spct.maphieu,ppc_spct.maphieudinhtinh,ppc_spct.phieueo,ppc_spct.nsx_fk"
					+ " from ERP_PHIEUPHACHE_SP_CHITIET ppc_spct inner join ERP_PHIEUPHACHE_SANPHAM ppc_sp on ppc_sp.PK_SEQ=ppc_spct.PHIEUPHACHE_SP_FK"
					+ " inner join ERP_TAILIEUPHACHETHUOC_THONGTIN tlpc_tt on tlpc_tt.PK_SEQ=ppc_sp.TLPC_TT_FK where ppc_spct.PHIEUPHACHE_FK="+id;
			System.out.println(query);
			rs = this.db.get(query);
			soluong = (double) 0;
			while(rs.next()){
				String khoId = rs.getString("khott_fk");
				String spId = rs.getString("sanpham_fk");
				String solo = rs.getString("solo");
				String ngayhethan = rs.getString("ngayhethan");
				String ngaynhapkho = rs.getString("ngaynhapkho");
				String loaidoituong = rs.getString("loaidoituong") == null ? "" : rs.getString("loaidoituong");
				String doituongId = rs.getString("doituongid") == null ? "" : rs.getString("doituongid");
				String mame = rs.getString("mame");
				String mathung = rs.getString("mathung");
				String bin_fk = rs.getString("bin_fk") == null ? "" : rs.getString("bin_fk");
				String maphieu = rs.getString("maphieu");
				String phieudt = rs.getString("maphieudinhtinh");
				String phieueo = rs.getString("phieueo");
				String marq = rs.getString("marq");
				String hamluong = rs.getString("hamluong");
				String hamam = rs.getString("hamam");
				String nsx_fk = rs.getString("nsx_fk") == null ? "" : rs.getString("nsx_fk");
				soluong = rs.getDouble("soluongchitiet");
				
				this.msg = util.Update_KhoTT_MOI("","",db,khoId,spId,solo,ngayhethan,ngaynhapkho,mame,mathung,bin_fk,maphieu,
						phieudt,phieueo,marq,hamluong,hamam,loaidoituong,doituongId,soluong,soluong,0, nsx_fk);
				if(this.msg.trim().length() > 0){
					db.getConnection().rollback();
				}
			}
			rs.close();
			
			query = "update ERP_PHIEUPHACHE set trangthai = '0' where pk_seq = " + id;
			if(!db.update(query)) {
				db.getConnection().rollback();
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			this.msg = "Loi";
		}
	}
	
	public void DBClose(){
		try {
			if(this.SanphamRs != null)
				this.SanphamRs.close();
			if(this.PhieuphacheRs != null)
				this.PhieuphacheRs.close();
			this.db.shutDown();
		} catch (SQLException e) {}
	}

	public String getUserId() {
		return userId;
	}
	public void setUserId(String userId) {
		this.userId = userId;
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

	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}

	public String getNgaychungtu() {
		return ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getSanpham() {
		return sanpham;
	}

	public void setSanpham(String sanpham) {
		this.sanpham = sanpham;
	}

	public String getNguoiphache() {
		return nguoiphache;
	}

	public void setNguoiphache(String nguoiphache) {
		this.nguoiphache = nguoiphache;
	}

	public ResultSet getSanphamRs() {
		return SanphamRs;
	}

	public void setSanphamRs(ResultSet sanphamRs) {
		SanphamRs = sanphamRs;
	}

	public ResultSet getPhieuphacheRs() {
		return PhieuphacheRs;
	}

	public void setPhieuphacheRs(ResultSet phieuphacheRs) {
		PhieuphacheRs = phieuphacheRs;
	}
}
