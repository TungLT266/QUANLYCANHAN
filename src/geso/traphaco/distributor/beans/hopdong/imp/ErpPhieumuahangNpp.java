package geso.traphaco.distributor.beans.hopdong.imp;

import geso.traphaco.distributor.beans.hopdong.IErpPhieumuahangNpp;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpPhieumuahangNpp implements IErpPhieumuahangNpp
{
	String userId;
	String id;
	
	String ma;
	String tungay;
	String denngay;
	
	String soluong;
	String giatri;
	
	String ghichu;
	String msg;
	
	String nppId;
	String nppTen;
	String sitecode;

	dbutils db;
	
	public ErpPhieumuahangNpp()
	{
		this.id = "";
		this.ma = "";
		this.tungay = "";
		this.denngay = "";
		this.soluong = "";
		this.giatri = "";
		this.ghichu = "";
		this.msg = "";

		this.db = new dbutils();
	}
	
	public ErpPhieumuahangNpp(String id)
	{
		this.id = id;
		this.ma = "";
		this.tungay = "";
		this.denngay = "";
		this.soluong = "";
		this.giatri = "";
		this.ghichu = "";
		this.msg = "";

		this.db = new dbutils();
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;
	}

	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	
	public boolean create() 
	{
		if(this.ma.trim().length() < 0  )
		{
			this.msg = "Vui lòng nhập mã phiếu mua hàng";
			return false;
		}
		
		if(this.soluong.trim().length() <= 0  )
		{
			this.msg = "Vui lòng nhập số lượng phiếu mua hàng";
			return false;
		}
		
		if(this.giatri.trim().length() <= 0  )
		{
			this.msg = "Vui lòng nhập giá trị phiếu mua hàng";
			return false;
		}

		try
		{
			db.getConnection().setAutoCommit(false);
			
			//CHECK TRUNG MA HOP DONG
			String query = "select count(*) as soDONG from ERP_PhieumuahangNpp where maphieu = '" + this.ma + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					this.msg = "Mã phiếu đã bị trùng. Vui lòng kiểm tra lại";
					rs.close();
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = " insert ERP_PhieuMuahangNpp(maphieu, tungay, denngay, soluong, giatri, ghichu, trangthai, npp_fk, ngaytao, nguoitao, ngaysua, nguoisua ) " +
					" values(N'" + this.ma + "', '" + this.tungay + "', '" + this.denngay + "', '" + this.soluong.replaceAll(",", "") + "', '" + this.giatri.replaceAll(",", "") + "', N'" + this.ghichu + "', '0', '" + this.nppId + "', '" + getDateTime() + "', '" + this.userId + "', '" + getDateTime() + "', '" + this.userId + "' )";
			
			System.out.println("1.Insert PHIEU MUA HANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới PHIEU MUA HANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//LAY ID
			/*ResultSet rsDDH = db.get("select IDENT_CURRENT('ERP_HopDongNpp') as ID ");
			if(rsDDH.next())
			{
				this.id = rsDDH.getString("ID");
			}
			rsDDH.close();*/
			
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}

	public boolean update() 
	{
		if(this.ma.trim().length() < 0  )
		{
			this.msg = "Vui lòng nhập mã phiếu mua hàng";
			return false;
		}
		
		if(this.soluong.trim().length() <= 0  )
		{
			this.msg = "Vui lòng nhập số lượng phiếu mua hàng";
			return false;
		}
		
		if(this.giatri.trim().length() <= 0  )
		{
			this.msg = "Vui lòng nhập giá trị phiếu mua hàng";
			return false;
		}

		try
		{
			db.getConnection().setAutoCommit(false);
			
			//CHECK TRUNG MA HOP DONG
			String query = "select count(*) as soDONG from ERP_PhieumuahangNpp where maphieu = '" + this.ma + "' and pk_seq != '" + this.id + "' ";
			ResultSet rs = db.get(query);
			if(rs.next())
			{
				if(rs.getInt("soDONG") > 0 )
				{
					this.msg = "Mã phiếu đã bị trùng. Vui lòng kiểm tra lại";
					rs.close();
					db.getConnection().rollback();
					return false;
				}
			}
			
			query = " Update ERP_PhieuMuahangNpp set maphieu = '" + this.ma + "', tungay = '" + this.tungay + "', denngay = '" + this.denngay + "', soluong = '" + this.soluong.replaceAll(",", "") + "', giatri = '" + this.giatri.replaceAll(",", "") + "', ghichu = N'" + this.ghichu + "', " + 
						" ngaysua = '" + getDateTime() + "', nguoisua = '" + this.userId + "' " + 
					" where pk_seq = '" + this.id + "' ";
			
			System.out.println("1.Insert PHIEU MUA HANG: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể tạo mới PHIEU MUA HANG " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		
		return true;
	}


	public void createRs() 
	{
		this.getNppInfo();
				
	}

	public void init() 
	{
		String query = "select maphieu, tungay, denngay, soluong, giatri, ghichu " +
						"from ERP_PhieuMuaHangNPP where pk_seq = '" + this.id + "'";
		System.out.println("____INIT NHAP KHO: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				NumberFormat formater = new DecimalFormat("#,###,###");
				if(rs.next())
				{
					this.ma = rs.getString("maphieu");
					this.tungay = rs.getString("tungay");
					this.denngay = rs.getString("denngay");
					this.soluong = formater.format( rs.getDouble("soluong") );
					this.giatri = formater.format( rs.getDouble("giatri") );
					this.ghichu = rs.getString("ghichu");
				}
				rs.close();
				
				
			} 
			catch (Exception e) 
			{
				System.out.println("---LOI INIT: " + e.getMessage());
			}
		}

		this.createRs();
		
	}

	public void DBclose() {
		
		try{
			
			this.db.shutDown();
			
		}catch(Exception er){
			
		}
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public String getGhichu() {
		
		return this.ghichu;
	}

	public void setGhichu(String ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String getMaphieu() {
		
		return this.ma;
	}

	
	public void setMaphieu(String maphieu) {
		
		this.ma = maphieu;
	}

	
	public String getTungay() {
		
		return this.tungay;
	}

	
	public void setTungay(String tungay) {
		
		this.tungay = tungay;
	}

	
	public String getDenngay() {
		
		return this.denngay;
	}

	
	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}

	
	public String getSoluong() {
		
		return this.soluong;
	}

	
	public void setSoluong(String soluong) {
		
		this.soluong = soluong;
	}

	
	public String getGiatri() {
		
		return this.giatri;
	}

	
	public void setGiatri(String giatri) {
		
		this.giatri = giatri;
	}

	
	public String getNppId() 
	{
		return this.nppId;
	}

	public void setNppId(String nppId) 
	{
		this.nppId = nppId;
	}
	
	public String getNppTen() 
	{
		return this.nppTen;
	}
	
	public void setNppTen(String nppTen) 
	{
		this.nppTen = nppTen;
	}
	
	public String getSitecode() 
	{
		return this.sitecode;
	}

	public void setSitecode(String sitecode) 
	{
		this.sitecode = sitecode;
	}
	
	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		//this.dangnhap = util.getDangNhap();
		this.sitecode=util.getSitecode();
	}

	
}
