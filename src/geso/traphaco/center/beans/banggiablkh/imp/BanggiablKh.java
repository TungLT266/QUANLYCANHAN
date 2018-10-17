package geso.traphaco.center.beans.banggiablkh.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import geso.traphaco.center.beans.banggiablkh.IBanggiablKh;
import geso.traphaco.center.beans.banggiablkh.IGiaKh;
import geso.traphaco.center.db.sql.*;

public class BanggiablKh implements IBanggiablKh
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String userTen;
	String spid;
	String spten;

	String trangthai;

	String msg;
	dbutils db;
	private List<IGiaKh> khList;
	private String spMa;

	public BanggiablKh(String id)
	{
		this.db = new dbutils();
		this.spid = id;
		this.spten = "";
		this.spMa = "";
		this.msg = "";
		this.trangthai = "1";

	}
	
	public BanggiablKh()
	{	
		this.db = new dbutils();
		this.spMa = "";
		this.spid = "";
		this.spten = "";
		this.msg = "";
		this.trangthai = "1";
	}

	public String getUserId() 
	{
		return this.userId;
	}
	
	public String getUserTen() 
	{
		return this.userTen;
	}
	
	public void setUserTen(String userTen) 
	{
		this.userTen = userTen;
	}
	
	public String getSpTen() 
	{
		return this.spten;
	}

	public void setSpTen(String ten) 
	{
		this.spten = ten;
	}
	
	
	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}
	
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public void init()
	{
		String query = "select pk_seq, Ma, ten from SANPHAM where pk_seq ='" + this.spid + "' ";
		ResultSet rs = this.db.get(query);
		try
		{		
			rs.next();
			this.spMa= rs.getString("Ma");
			this.spten = rs.getString("ten");
			rs.close();
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
		
		createRS();
	}

	
	
	public void setUserId(String userId) {
		
		this.userId = userId;
	}

	
	public boolean CreateBg() 
	{
		return true;
	}

	
	public boolean UpdateBg() 
	{
		try {
			db.getConnection().setAutoCommit(false);
			String query = "DELETE from BANGGIABANLEKH_SANPHAM WHERE SANPHAM_FK = '"+this.spid+"'";
			if(!db.update(query)){
				db.getConnection().rollback();
				this.msg = "Lỗi cập nhật bảng giá (1)";
				return false;
			}
			for(int i=0; i< this.khList.size(); i++){
				IGiaKh kh = this.khList.get(i);
				query = "insert into BANGGIABANLEKH_SANPHAM(SANPHAM_FK, KHACHHANG_FK, DONGIA, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA) " +
						"values ('"+this.spid+"', '"+kh.getKhId()+"', '"+kh.getGiaban()+"', '"+getDateTime()+"', '"+getDateTime()+"', '"+this.userId+"', '"+this.userId+"')";
				if(db.updateReturnInt(query) == 0){
					db.getConnection().rollback();
					this.msg = "Lỗi cập nhật bảng giá (2): '" +kh.getKhId()+"', '"+kh.getGiaban()+"'";
					return false;
				}
				query = "insert into BANGGIABANLEKH_SANPHAM_LOG(SANPHAM_FK, KHACHHANG_FK, DONGIA, NGAYTAO, NGUOITAO) " +
						"values ('"+this.spid+"', '"+kh.getKhId()+"', '"+kh.getGiaban()+"', GETDATE(), '"+this.userId+"')";
				if(db.updateReturnInt(query) == 0){
					db.getConnection().rollback();
					this.msg = "Lỗi cập nhật bảng giá (log): '" +kh.getKhId()+"', '"+kh.getGiaban()+"'";
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} catch (SQLException e) {
			try {
				db.getConnection().rollback();
				db.getConnection().setAutoCommit(true);
				this.msg = e.getMessage();
				return false;
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			e.printStackTrace();
		}
		
		return true;
	}

	
	public void createRS() 
	{
		
		String query =  "SELECT k.PK_SEQ, k.MAFAST, k.TEN, bg.DONGIA "+
						"FROM BANGGIABANLEKH_SANPHAM bg inner join KHACHHANG k on bg.KHACHHANG_FK = k.PK_SEQ " +
						"where bg.SANPHAM_FK = "+this.spid+" order by TEN asc ";
		ResultSet rs = db.get(query);
		this.khList = new ArrayList<IGiaKh>();
		if(rs != null)
			try {
				while(rs.next()){
					GiaKh kh = new GiaKh();
					kh.setKhId(rs.getString("PK_SEQ"));
					kh.setKhTen(rs.getString("TEN"));
					kh.setKhMa(rs.getString("MAFAST"));
					kh.setGiaban(rs.getString("DONGIA"));
					this.khList.add(kh);
				}
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
	}

	
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	@Override
	public void closeDB() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSpId() {
		// TODO Auto-generated method stub
		return this.spid;
	}

	@Override
	public void setSpId(String id) {
		this.spid = id;
	}

	@Override
	public List<IGiaKh> getKhlist() {
		// TODO Auto-generated method stub
		return this.khList;
	}

	@Override
	public void setKhlist(List<IGiaKh> khlst) {
		this.khList = khlst;
	}

	@Override
	public String getSpMa() {
		// TODO Auto-generated method stub
		return this.spMa;
	}

	@Override
	public void setSpMa(String ma) {
		this.spMa = ma;
	}
	
}