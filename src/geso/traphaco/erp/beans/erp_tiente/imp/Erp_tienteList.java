package geso.traphaco.erp.beans.erp_tiente.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.erp_tiente.IErp_tienteList;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Erp_tienteList implements IErp_tienteList
{
	dbutils db;
	String ID;
	String MA;
	String TEN;
	String Msg;
	ResultSet Rstt;
	String userten;
	String userid;
	String chixem;

	public Erp_tienteList()
	{
		db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.Msg = "";
		userid = "";
		this.chixem = "0";
	}

	
	public String getID()
	{
		return ID;
	}

	
	public String getMA()
	{
		return MA;
	}

	
	public String getTEN()
	{
		return TEN;
	}

	
	public void setID(String ID)
	{
		this.ID = ID;
	}

	
	public void setMA(String MA)
	{
		this.MA = MA;
	}

	
	public void setTEN(String TEN)
	{
		this.TEN = TEN;
	}

	
	public String getMsg()
	{

		return Msg;
	}

	
	public ResultSet getRstt()
	{

		return Rstt;
	}

	
	public void setRstt(ResultSet Rstt)
	{

		this.Rstt = Rstt;
	}

	
	public void setMsg(String Msg)
	{

		this.Msg = Msg;
	}

	

	
	public void init()
	{
		String query = " SELECT pk_seq, ma, ten FROM erp_tiente WHERE 1=1";

		if (this.MA.length() > 0)
			query += " AND ma LIKE '%" + this.MA + "%' ";
		if (this.TEN.length() > 0)
			query += " AND ten LIKE '%" + this.TEN + "%' ";
		this.Rstt = db.get(query);

	}

	
	public void DBClose()
	{
		try
		{
			if (Rstt != null)
			{
				Rstt.close();
			}
			if (db != null)
			{
				db.shutDown();
			}

		} catch (Exception er)
		{
		}

	}

	
	public void setUserid(String userid)
	{

		this.userid = userid;
	}

	
	public String getUserid()
	{

		return userid;
	}

	
	public void setUserTen(String userten)
	{

		this.userten = userten;
	}

	
	public String getUserTen()
	{

		return this.userten;
	}
	public boolean CheckReferences(String column, String table, String trangthai, boolean check_trangthai)
	{
		String query = "SELECT count(" + column + ") AS NUM  FROM " + table + " WHERE " + column + " =" + this.ID + "";
		if(check_trangthai){
			query += " and trangthai = " + trangthai + "";
		}
		System.out.println("CheckReferences " + query);
		ResultSet rs = db.get(query);
		try
		{// kiem tra ben san pham
			while (rs.next())
			{
				if (rs.getString("num").equals("0"))
					return true;
			}
		} catch (SQLException e)
		{

			e.printStackTrace();
		}
		return false;
	}

	public boolean DeleteTienTe()
	{

		if (!CheckReferences("TienTe_FK", "ERP_TIGIA", "1", true))
		{
			this.Msg = "Tiền tệ này đã có tỷ giá bạn phải xóa tỷ giá  này trước khi xóa Tiền tệ";
			return false;
		}
		
		if (!CheckReferences("TienTe_FK", "ERP_MUAHANG", "", false))
		{
			this.Msg = "Tiền tệ này đã có mua hàng bạn phải xóa mua hàng  này trước khi xóa Tiền tệ";
			return false;
		}
		if (!CheckReferences("TienTe_FK", "Erp_NganHang_CongTy", "", false))
		{
			this.Msg = "Tiền tệ này đã có trong ngân hàng công ty bạn phải xóa ngân hàng công ty này trước khi xóa Tiền tệ";
			return false;
		}
		
		String query = "DELETE Erp_TienTe WHERE PK_SEQ='" + this.ID + "'";
		if (!this.db.update(query))
		{
			this.Msg = "Không thể xóa tỷ giá " + query;
			return false;
		}
		return true;
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
