package geso.traphaco.erp.beans.nhasanxuat.imp;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.nhasanxuat.IErpNhaSanXuat;
import geso.traphaco.erp.util.UtilitySyn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class ErpNhaSanXuat implements IErpNhaSanXuat {
	dbutils db; 
	String ID;
	String MA;
	String TEN;
	String NGAYTAO;
	String NGAYSUA;
	String NGUOITAO;
	String NGUOISUA;
	String userTen;
	String userId;
	String Msg;
	String TRANGTHAI;

	public ErpNhaSanXuat(String id) {
		db = new dbutils();
		this.ID=id;
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.TRANGTHAI = "1";
		this.userId = "";
		this.Msg = "";
	}

	public ErpNhaSanXuat() {
		db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.NGAYTAO = "";
		this.NGAYSUA = "";
		this.NGUOITAO = "";
		this.NGUOISUA = "";
		this.TRANGTHAI = "1";
		this.userId = "";
		this.Msg = "";
	}
	
	public String getID() {
		return ID;
	}

	
	public String getMA() {
		return MA;
	}

	
	public String getTEN() {
		return TEN;
	}

	
	public String getNGAYTAO() {
		return NGAYTAO;
	}

	
	public String getNGAYSUA() {
		return NGAYSUA;
	}

	
	public String getNGUOITAO() {
		return NGUOITAO;
	}

	
	public String getNGUOISUA() {
		return NGUOISUA;
	}

	public String getMsg() {
		return Msg;
	}

	
	public String getTRANGTHAI() {
		return TRANGTHAI;
	}

	
	public void setID(String ID) {
		this.ID = ID;
	}

	
	public void setMA(String MA) {
		this.MA = MA;
	}

	
	public void setTEN(String TEN) {
		this.TEN = TEN;
	}

	
	public void setNGAYTAO(String NGAYTAO) {
		this.NGAYTAO = NGAYTAO;
	}

	
	public void setNGAYSUA(String NGAYSUA) {
		this.NGAYSUA = NGAYSUA;
	}

	
	public void setNGUOITAO(String NGUOITAO) {
		this.NGUOITAO = NGUOITAO;
	}

	
	public void setNGUOISUA(String NGUOISUA) {
		this.NGUOISUA = NGUOISUA;
	}

	
	public void setTRANGTHAI(String TRANGTHAI) {
		this.TRANGTHAI = TRANGTHAI;
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
	}
	
	public boolean update() {
		try
			{
				dbutils db = new dbutils();
				db.getConnection().setAutoCommit(false);
				
				String query = "";

				if (this.ID == null || this.ID.trim().length() <= 0) {
					//them moi
					query = "insert erp_nhasanxuat(ten, trangthai, ngaytao, ngaysua, nguoitao, nguoisua) " +
							   " values(N'"+ this.TEN+ "','"+ this.TRANGTHAI +"','"+this.NGAYTAO+"','"
							   +this.NGAYTAO+"','"+this.NGUOITAO+"','"+this.NGUOITAO+"')";
				} else {
					//cap nhat
					query = "UPDATE erp_nhasanxuat " + "SET TEN = N'" + this.TEN + "',TRANGTHAI = '"
							+ this.TRANGTHAI + "', NGUOISUA = '"+this.NGUOISUA+"'," +
							" NGAYSUA = '"+this.NGAYSUA + "' "
							+ "WHERE PK_SEQ = '" + this.ID + "' ";
				}
				
				System.out.println("Query update/insert : " + query);
				if (!db.update(query)) {
					this.Msg = "Lỗi khi cập nhật/thêm mới nhà sản xuất "+query;
					db.getConnection().rollback();
					return false;
				}
				
				if (this.ID == null || this.ID.trim().length() <= 0) {
					query = "select scope_identity() as Id";
					ResultSet rs = db.get(query);
					rs.next();
					this.ID = rs.getString("Id");
					System.out.println("id = " + this.ID + "; query = " + query);
					rs.close();

					this.MA = "HY" + this.ID;
					query = "update erp_nhasanxuat set ma = '" + this.MA + "' where pk_seq = " + this.ID;
					if (db.updateReturnInt(query) != 1) {
						this.Msg = "Lỗi khi cập nhật/thêm mới nhà sản xuất "+query;
						db.getConnection().rollback();
						return false;
					}

					if (!CheckUnique(db))
					{
						this.Msg = "Mã này đã được sử dụng, vui lòng chọn mã khác";
						db.getConnection().rollback();
						return false;
					}
				}	
				
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
				db.shutDown();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
				return false;
			}
			return true;
		}

	public void init() {
		String query = "select * from erp_nhasanxuat where pk_seq='" + this.ID + "'";
		ResultSet nh = db.get(query);
		try {
			if (nh.next()) {
				this.ID = nh.getString("PK_SEQ");
				this.MA = nh.getString("MA");
				this.TEN = nh.getString("TEN");
				this.NGAYTAO = nh.getString("NGAYTAO");
				this.NGAYSUA = nh.getString("NGAYSUA");
				this.NGUOITAO = nh.getString("NGUOITAO");
				this.NGUOISUA = nh.getString("NGUOISUA");
				this.TRANGTHAI = nh.getString("TRANGTHAI");
			}
		} catch (Exception er) {
			er.printStackTrace();
			this.Msg = "Loi" + er.toString();
		}
	}

	public void DBClose() 
	{
		if(db != null)
		{
			db.shutDown();
		}
	}

	public void setUserid(String user) {
		
		this.userId = user;
	}


	public String getUserid() {
		
		return userId;
	}


	public void setUserTen(String userten) {
		
		this.userTen = userten;
	}


	public String getUserTen() {
		
		return userTen;
	}

	public boolean CheckUnique(Idbutils db)
	{
		String query = "";
		if (this.ID.length() > 0)
			query = "Select count(*) as count From Erp_Nhasanxuat Where MA=N'" + this.MA + "' AND PK_SEQ !='" + this.ID + "'";
		else
			query = "Select count(*) as count From Erp_Nhasanxuat Where MA=N'" + this.MA + "' ";
		System.out.println("____Kiem tra rang buoc_____ " + query);
		int count = 0;
		ResultSet rs = db.get(query);
		if (rs != null)
			try
			{
				while (rs.next())
				{
					count = rs.getInt("count");
				}
				rs.close();
				if (count > 0)
					return false;
			} catch (SQLException e)
			{
				return false;
			}
		return true;
	}

}
