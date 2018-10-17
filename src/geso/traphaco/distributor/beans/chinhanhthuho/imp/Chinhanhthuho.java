package geso.traphaco.distributor.beans.chinhanhthuho.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.beans.chinhanhthuho.IChinhanhthuho;
import geso.traphaco.erp.beans.nganhang.IErpNganHang;
import geso.traphaco.erp.util.UtilitySyn;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Chinhanhthuho implements IChinhanhthuho {
	dbutils db; 
	String ID;
	String MA;
	String TEN;
	String ngaytao;
	String ngaysua;
	String userId;
	String Msg;
	String Trangthai;
	String sotien;
	String chinhanh;

	public Chinhanhthuho(String id) {
		db = new dbutils();
		this.ID=id;
		this.MA = "";
		this.TEN = "";
		this.ngaytao="";
		this.ngaysua="";
		this.Trangthai = "";
		this.Msg = "";
		this.userId="";
		this.sotien="";
		this.chinhanh="";
		if(id.length() > 0)
		this.init();

	}

	public Chinhanhthuho() {
		db = new dbutils();
		this.ID = "";
		this.MA = "";
		this.TEN = "";
		this.Trangthai = "1";
		this.Msg = "";
		this.userId="";
		this.sotien="";
		this.chinhanh="";
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

	
	
	
	public String getMsg() {
		return Msg;
	}

	
	public String gettrangthai() {
		return Trangthai;
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

	
	

	
	public void setTrangthai(String trangthai) {
		this.Trangthai = trangthai;
	}

	public void setMsg(String Msg) {
		this.Msg = Msg;
	}
	
	
	public void init() {
		String query = "select CN.PK_SEQ AS PK_SEQ,CN.KHACHHANG_FK,KH.TEN AS TENKH,CN.SOTIEN," +
				"NPP.TEN AS TEN_NPP,npp.ten as NPP_FK,CN.SOTIEN,CN.NGAYTAO,CN.NGAYSUA,NV.TEN AS NGUOITAO,NV.TEN AS NGUOISUA,CN.TRANGTHAI" +
				" FROM ERP_CHINHANHTHUHO CN"+
				" LEFT JOIN KHACHHANG KH ON CN.KHACHHANG_FK=KH.PK_SEQ"+
				" LEFT JOIN NHAPHANPHOI NPP ON CN.NPP_FK=NPP.PK_SEQ"+
				" LEFT JOIN NHANVIEN NV ON CN.NGUOITAO=NV.PK_SEQ"+
				" where CN.pk_seq='" + this.ID + "'";
		ResultSet nh = db.get(query);
		try {
			if (nh.next()) {
				this.ID = nh.getString("pk_seq");
				this.MA = nh.getString("khachhang_fk");
				this.TEN = nh.getString("TENKH");
				this.sotien=nh.getString("sotien");
				this.chinhanh=nh.getString("NPP_FK");
				this.ngaytao= nh.getString("NGAYTAO");
				this.ngaysua = nh.getString("NGAYSUA");
				this.Trangthai = nh.getString("trangthai");
			}
		} catch (Exception er) {
			er.printStackTrace();
			this.Msg = "Loi" + er.toString();
		}
		System.out.println("asdasdasdasdas"+query);
	}

	public void DBClose() 
	{
		if(db != null)
		{
			db.shutDown();
		}
	}


	

	


	public String getNgaytao() {
		return ngaytao;
	}

	public void setNgaytao(String ngaytao) {
		this.ngaytao = ngaytao;
	}

	public String getNgaysua() {
		return ngaysua;
	}

	public void setNgaysua(String ngaysua) {
		this.ngaysua = ngaysua;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getSotien() {
		return sotien;
	}

	public void setSotien(String sotien) {
		this.sotien = sotien;
	}

	public String getChinhanh() {
		return chinhanh;
	}

	public void setChinhanh(String chinhanh) {
		this.chinhanh = chinhanh;
	}
}
