package geso.traphaco.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.lapkehoach.IErpDanhMucVatTuThanhPhan;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpDanhMucVatTuThanhPhan implements IErpDanhMucVatTuThanhPhan {

	private String danhmucvattu_fk;
	private String sanpham_fk;
	private String soluong;
	private String dvdl_fk;
	private String tenVTTP;
	private String tenDonVi;
	private String msg;
	private dbutils db;

	List<IErpDanhMucVatTuThanhPhan> sptpList;

	public String getTenVTTP() {
		return tenVTTP;
	}

	public void setTenVTTP(String tenVTTP) {
		this.tenVTTP = tenVTTP;
	}

	public String getTenDonVi() {
		return tenDonVi;
	}

	public void setTenDonVi(String tenDonVi) {
		this.tenDonVi = tenDonVi;
	}

	public String getDanhmucvattu_fk() {
		return danhmucvattu_fk;
	}

	public void setDanhmucvattu_fk(String danhmucvattu_fk) {
		this.danhmucvattu_fk = danhmucvattu_fk;
	}

	public String getSanpham_fk() {
		return sanpham_fk;
	}

	public void setSanpham_fk(String sanpham_fk) {
		this.sanpham_fk = sanpham_fk;
	}

	public String getSoluong() {
		return soluong;
	}

	public void setSoluong(String soluong) {
		this.soluong = soluong;
	}

	public String getDvdl_fk() {
		return dvdl_fk;
	}

	public void setDvdl_fk(String dvdl_fk) {
		this.dvdl_fk = dvdl_fk;
	}

	public List<IErpDanhMucVatTuThanhPhan> getSptpList() {
		return sptpList;
	}

	public void setSptpList(List<IErpDanhMucVatTuThanhPhan> sptpList) {
		this.sptpList = sptpList;
	}

	public ErpDanhMucVatTuThanhPhan() {
		this.danhmucvattu_fk = "";
		this.sanpham_fk = "";
		this.soluong = "";
		this.dvdl_fk = "";
		this.sptpList = new ArrayList<IErpDanhMucVatTuThanhPhan>();
		this.db = new dbutils();
	}

	public void initDanhSachVatTu() {
		this.db = new dbutils();
		String query = "select vttp.danhmucvattu_fk, vttp.sanpham_fk,vttp.soluong,vttp.dvdl_fk,sp.TEN,dvdl.donvi"
				+ " from ERP_DANHMUCVATTU_THANHPHAN vttp " + "INNER join ERP_SANPHAM sp "
				+ " on sp.PK_SEQ = vttp.SANPHAM_FK" + " inner join DONVIDOLUONG dvdl on vttp.dvdl_fk = dvdl.PK_SEQ"
				+ " where vttp.danhmucvattu_fk = " + this.getDanhmucvattu_fk();
		ResultSet rs = this.db.get(query);
		String[] param = new String[7];
		try {
			while (rs.next()) {
				IErpDanhMucVatTuThanhPhan vt = new ErpDanhMucVatTuThanhPhan();
				vt.setTenVTTP(rs.getString("TEN"));
				vt.setTenDonVi(rs.getString("donvi"));
				vt.setSoluong(rs.getString("soluong"));
				vt.setSanpham_fk(rs.getString("sanpham_fk"));
				vt.setDvdl_fk(rs.getString("dvdl_fk"));			
				sptpList.add(vt);
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public Boolean updateDanhMucVatTuThanhPhan() {
		dbutils db = new dbutils();
		try {
			db.getConnection().setAutoCommit(false);
			String queryDelete = "delete from ERP_DANHMUCVATTU_THANHPHAN where DANHMUCVATTU_FK ="
					+ this.getDanhmucvattu_fk();
			if (!db.update(queryDelete)) {
				this.msg = "Không thể cập nhật BOM Thanh Phan: " + queryDelete;
				db.getConnection().rollback();
				return false;
			}
			for (int i = 0; i < this.sptpList.size(); i++) {

				IErpDanhMucVatTuThanhPhan sptp = this.sptpList.get(i);
				System.out.println("SPTP " + sptp.getSanpham_fk());
				if (Double.parseDouble(sptp.getSoluong()) > 0) {
					String query = "INSERT ERP_DANHMUCVATTU_THANHPHAN (danhmucvattu_fk, sanpham_fk, soluong, dvdl_fk) values("
							+ this.getDanhmucvattu_fk() + "," + sptp.getSanpham_fk() + "," + sptp.getSoluong() + ","
							+ sptp.getDvdl_fk() + ")";
					if (!db.update(query)) {
						this.msg = "Không thể tạo mới ERP_DANHMUCVATTU_VATTU_THAYTHE : " + query;
						db.getConnection().rollback();
						return false;
					}
				} else {
					this.msg = "Vui lòng kiểm tra số lượng của sản phẩm có mã " + sptp.getSanpham_fk();
					db.getConnection().rollback();
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);

		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}

	public Boolean createDanhMucVatTuThanhPhan() throws SQLException {
		dbutils db = new dbutils();

		System.out.println("BOMID Vao day");
		db.getConnection().setAutoCommit(false);
		this.setDanhmucvattu_fk(this.getNewBomId());
		for (int i = 0; i < this.sptpList.size(); i++) {
			IErpDanhMucVatTuThanhPhan sptp = this.sptpList.get(i);
			if (Double.parseDouble(sptp.getSoluong()) > 0) {
				String query = "INSERT ERP_DANHMUCVATTU_THANHPHAN (danhmucvattu_fk, sanpham_fk, soluong, dvdl_fk) values("
						+ this.getDanhmucvattu_fk() + "," + sptp.getSanpham_fk() + "," + sptp.getSoluong() + ","
						+ sptp.getDvdl_fk() + ")";
				if (!db.update(query)) {
					this.msg = "Không thể tạo mới ERP_DANHMUCVATTU_VATTU_THAYTHE : " + query;
					db.getConnection().rollback();
					return false;
				}
			} else {
				this.msg = "Vui lòng kiểm tra số lượng của sản phẩm có mã " + sptp.getSanpham_fk();
				db.getConnection().rollback();
				return false;
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);

		return true;
	}

	private String getNewBomId() {
		dbutils db = new dbutils();
		String result = "";
		try {
			String query = "select IDENT_CURRENT('ERP_DANHMUCVATTU') AS ID";
			ResultSet rs = db.get(query);
			rs.next();
			result = rs.getString("ID");
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;
	}

}
