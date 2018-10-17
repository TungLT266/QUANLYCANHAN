package geso.traphaco.erp.beans.phongbansx.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.phongbansx.IErpPhongbanSX;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpPhongbanSX implements IErpPhongbanSX {
	String userId;
	String congtyId;
	String id;
	String ma;
	String isChangeMa;
	String diengiai;
	String trangthai;
	String msg;
	String[] spDiengiai;
	String[] spDinhmuctu;
	String[] spDinhmucden;
	String[] spDonvi;
	ResultSet dvtRs;
	dbutils db;

	public ErpPhongbanSX() {
		this.userId = "";
		this.id = "";
		this.ma = "";
		this.isChangeMa = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.msg = "";
		this.db = new dbutils();
	}

	public ErpPhongbanSX(String id) {
		this.id = id;
		this.ma = "";
		this.diengiai = "";
		this.trangthai = "1";
		this.msg = "";
		this.db = new dbutils();
	}

	public String getId() {
		return this.id;
	}

	public void setId(String Id) {
		this.id = Id;
	}

	public String getUserId() {
		return this.userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getMsg() {
		return this.msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void init() {
		checkInCongDoanSX();
		
		String query = "select ma, ten, trangthai from Erp_phongbansx where PK_SEQ = '"
				+ this.id + "'";
		ResultSet rs = db.get(query);
		try {
			while (rs.next()) {
				this.ma = rs.getString("ma");
				this.diengiai = rs.getString("ten");
				this.trangthai = rs.getString("trangthai");
			}
			rs.close();

			String _SPDIENGIAI = "";
			String _SPDINHMUCTU = "";
			String _SPDINHMUCDEN = "";
			String _SPDVDL = "";
			query = "select DIENGIAI, DINHMUCTU, DINHMUCDEN, DVDL_FK from ERP_PHONGBANSX_CHITIET where PHONGBAN_fk = "
					+ this.id;
			System.out.println("query: " + query);
			rs = db.get(query);
			while (rs.next()) {
				_SPDIENGIAI += rs.getString("DIENGIAI") + "__";
				_SPDINHMUCTU += rs.getString("DINHMUCTU").trim().length() <= 0 ? " __"
						: rs.getString("DINHMUCTU") + "__";
				_SPDINHMUCDEN += rs.getString("DINHMUCDEN").trim().length() <= 0 ? " __"
						: rs.getString("DINHMUCDEN") + "__";
				_SPDVDL += rs.getString("DVDL_FK") == null ? " __" : rs
						.getString("DVDL_FK") + "__";
			}
			rs.close();
			_SPDIENGIAI = _SPDIENGIAI.substring(0, _SPDIENGIAI.length() - 2);
			this.spDiengiai = _SPDIENGIAI.split("__");

			_SPDINHMUCTU = _SPDINHMUCTU.substring(0, _SPDINHMUCTU.length() - 2);
			this.spDinhmuctu = _SPDINHMUCTU.split("__");

			_SPDINHMUCDEN = _SPDINHMUCDEN.substring(0,
					_SPDINHMUCDEN.length() - 2);
			this.spDinhmucden = _SPDINHMUCDEN.split("__");

			_SPDVDL = _SPDVDL.substring(0, _SPDVDL.length() - 2);
			this.spDonvi = _SPDVDL.split("__");
		} catch (Exception e) {
			System.out.println("__Exception: " + e.getMessage());
		}

		creaters();
	}

	public boolean CheckUnique() {
		String query = "";
		if (this.id.length() > 0)
			query = "Select count(*) as count From Erp_phongbansx Where MA=N'"
					+ this.ma + "' AND PK_SEQ !='" + this.id + "'";
		else
			query = "Select count(*) as count From Erp_phongbansx Where MA=N'"
					+ this.ma + "' ";
		System.out.println("____Kiem tra rang buoc_____ " + query);
		int count = 0;
		ResultSet rs = this.db.get(query);
		if (rs != null)
			try {
				while (rs.next()) {
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e) {
				return false;
			}
		return true;
	}

	public boolean create() {
		try {
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}

			if (this.ma.trim().length() <= 0) {
				this.msg = "Vui lòng nhập mã";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			if (this.isExist()) {
				this.msg = "Mã nhà máy đã tồn tại. Vui lòng nhập mã nhà máy khác";
				return false;
			} else {
				String query = "insert Erp_phongbansx(ma, ten, trangthai, ngaytao, nguoitao, ngaysua, nguoisua, congty_fk) "
						+ "values('"
						+ this.ma
						+ "', N'"
						+ this.diengiai
						+ "', "
						+ "'"
						+ this.trangthai
						+ "', '"
						+ this.getDateTime()
						+ "', '"
						+ this.userId
						+ "', '"
						+ this.getDateTime()
						+ "', '"
						+ this.userId
						+ "', '"
						+ this.congtyId + "')";

				System.out.println("create: " + query);
				if (!db.update(query)) {
					this.msg = "Không thể tạo mới Erp_phongbansx " + query;
					db.getConnection().rollback();
					return false;
				}

				ResultSet rsDDH = db.get("select SCOPE_IDENTITY() as ID ");
				String id = "";
				if (rsDDH.next()) {
					id = rsDDH.getString("ID");
				}
				rsDDH.close();

				for (int i = 0; i < spDiengiai.length; i++) {
					if (spDiengiai[i].trim().length() > 0) {
						query = "insert ERP_phongbansx_chitiet( phongban_fk, diengiai, dinhmuctu, dinhmucden, dvdl_fk ) "
								+ "select "
								+ id
								+ ", N'"
								+ spDiengiai[i]
								+ "', N'"
								+ spDinhmuctu[i]
								+ "', N'"
								+ spDinhmucden[i] + "', " + spDonvi[i];

						System.out.println("1.Insert: " + query);
						if (!db.update(query)) {
							this.msg = "Không thể chèn ERP_phongbansx_chitiet: "
									+ query;
							db.getConnection().rollback();
							return false;
						}
					}
				}

				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}

		return true;
	}
	
	public void checkInCongDoanSX(){
		try{
			String query = "select count(*) as count from ERP_CONGDOANSANXUAT_GIAIDOAN_GIAY where PhongBan_FK = " + this.id;
			
			ResultSet rs = this.db.get(query);
			rs.next();
			int count = rs.getInt("count");
			rs.close();
			if(count > 0){
				this.isChangeMa = "1";
			} else {
				this.isChangeMa = "0";
			}
		} catch(Exception e){
			this.msg = e.getMessage();
		}
	}

	public boolean update() {
		try {
//			if(!checkInCongDoanSX())
//				return false;
			
			if (!CheckUnique()) {
				this.msg = "Mã này đã được sử dụng, vui lòng nhập mã khác";
				return false;
			}

			if (this.ma.trim().length() <= 0) {
				this.msg = "Vui lòng nhập mã cụm sản xuất";
				return false;
			}

			if (this.isExist()) {
				this.msg = "Mã đã bị trùng với mã nhà máy khác. Vui lòng nhập lại";
				return false;
			}

			db.getConnection().setAutoCommit(false);

			String query = "update Erp_phongbansx set ma = '" + this.ma
					+ "', ten = N'" + this.diengiai + "', trangthai = '"
					+ this.trangthai + "', " + "ngaysua = '"
					+ this.getDateTime() + "', nguoisua = '" + this.userId
					+ "' where pk_seq = '" + this.id + "' ";

			if (db.updateReturnInt(query) != 1) {
				this.msg = "Không thể cập nhật Erp_phongbansx " + query;
				db.getConnection().rollback();
				return false;
			}

			query = "delete erp_phongbansx_chitiet where phongban_fk = "
					+ this.id;
			if (!db.update(query)) {
				this.msg = "Không thể xóacập nhật erp_phongbansx_chitiet "
						+ query;
				db.getConnection().rollback();
				return false;
			}

			for (int i = 0; i < spDiengiai.length; i++) {
				if (spDiengiai[i].trim().length() > 0) {
					query = "insert ERP_phongbansx_chitiet( phongban_fk, diengiai, dinhmuctu, dinhmucden, dvdl_fk ) "
							+ "select "
							+ this.id
							+ ", N'"
							+ spDiengiai[i]
							+ "', N'"
							+ spDinhmuctu[i]
							+ "', N'"
							+ spDinhmucden[i] + "', " + spDonvi[i];

					System.out.println("1.Insert: " + query);
					if (!db.update(query)) {
						this.msg = "Không thể chèn ERP_phongbansx_chitiet: "
								+ query;
						db.getConnection().rollback();
						return false;
					}
				}
			}

			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (Exception e) {
			this.msg = "Loi: " + e.getMessage();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
			}
			return false;
		}

		return true;
	}

	public boolean isExist() {
		String query = "select ma from Erp_phongbansx";
		if (this.id.trim().length() > 0) {
			query += " where pk_seq <> " + this.id;
		}
		ResultSet rs = db.get(query);
		if (rs != null) {
			try {
				while (rs.next()) {
					if (rs.getString("ma").equals(this.ma)) {
						rs.close();
						return true;
					}
				}
				rs.close();
			} catch (Exception e) {
				System.out.println("__Exception: " + e.getMessage());
			}
		}
		return false;
	}

	public String getDiengiai() {
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
	}

	public void DbClose() {
		try {
			this.db.shutDown();
		} catch (Exception e) {
		}
	}

	public String getMa() {
		return this.ma;
	}

	public void setMa(String ma) {
		this.ma = ma;
	}

	private String getDateTime() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
	}

	public String getCongtyId() {
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) {
		this.congtyId = congtyId;
	}

	@Override
	public void creaters() {
		try {

			String query = "select pk_seq, donvi from donvidoluong";
			this.dvtRs = db.getScrol(query);

		} catch (Exception er) {
		}
	}

	@Override
	public String[] getSpDiengiai() {
		return spDiengiai;
	}

	@Override
	public void setSpDiengiai(String[] spDiengiai) {
		this.spDiengiai = spDiengiai;
	}

	@Override
	public String[] getSpDinhmuctu() {
		return this.spDinhmuctu;
	}

	@Override
	public void setSpDinhmuctu(String[] spDinhmuctu) {
		this.spDinhmuctu = spDinhmuctu;
	}

	@Override
	public String[] getSpDinhmucden() {
		return this.spDinhmucden;
	}

	@Override
	public void setSpDinhmucden(String[] spDinhmucden) {
		this.spDinhmucden = spDinhmucden;
	}

	@Override
	public String[] getSpDonvi() {
		return this.spDonvi;
	}

	@Override
	public void setSpDonvi(String[] spDonvi) {
		this.spDonvi = spDonvi;
	}

	@Override
	public ResultSet getDvtRs() {
		return this.dvtRs;
	}

	@Override
	public void setDvtRs(ResultSet dvtRs) {
		this.dvtRs = dvtRs;
	}

	public String getIsChangeMa() {
		return isChangeMa;
	}
	public void setIsChangeMa(String isChangeMa) {
		this.isChangeMa = isChangeMa;
	}
}
