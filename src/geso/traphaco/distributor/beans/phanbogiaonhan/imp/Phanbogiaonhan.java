package geso.traphaco.distributor.beans.phanbogiaonhan.imp;

import geso.traphaco.distributor.beans.phanbogiaonhan.IPhanbogiaonhan;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.Serializable;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Phanbogiaonhan implements IPhanbogiaonhan, Serializable
{
	private static final long serialVersionUID = -9217977546733610214L;
	String userId;
	String id;
	String tennv;
	String diengiai;
	String ngay;
	String trangthai;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String msg;
	
	String congtyId;
	String nppId;
	String nppTen;
	String sitecode;
	String khId;
	String nvgnId;
	String nvgn_newId;
	String tinhthanhId;
	String quanhuyenId;
	String ddhId;
	ResultSet ddhRs;
	ResultSet khRs;
	ResultSet nvgnRs;
	ResultSet nvgn_newRs;
	ResultSet tinhthanhRs;
	ResultSet quanhuyenRs;
	
	String denngay;
	dbutils db;
	
	public Phanbogiaonhan(String[] param)
	{
		this.id = param[0];
		this.diengiai = param[1];
		this.trangthai = param[2];
		this.nvgnId = param[3];
		this.ngaytao = param[4];
		this.nguoitao = param[5];
		this.ngaysua = param[6];
		this.nguoisua = param[7];
		this.ngay = param[8];
		this.nvgn_newId = param[9];
		this.tennv = param[10];
		this.tinhthanhId = "";
		this.quanhuyenId = "";
		this.msg = "";
		this.khId = "";
		this.ddhId = "";
		this.denngay = "";
		db = new dbutils();
	}
	
	public Phanbogiaonhan(String id)
	{
		this.id = id;
		this.ngay = "";
		this.diengiai = "";
		this.trangthai = "2";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.nvgnId = "";
		this.nvgn_newId = "";
		this.tinhthanhId = "";
		this.msg = "";
		this.quanhuyenId="";
		this.khId = "";
		this.ddhId = "";
		this.denngay = "";
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
	
	public String getId()
	{
		return this.id;
	}
	
	public void setId(String id)
	{
		this.id = id;
	}
	
	public String getTrangthai()
	{
		return this.trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}
	
	public String getNgaytao()
	{
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.ngaytao = ngaytao;
	}
	
	public String getNguoitao()
	{
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.nguoitao = nguoitao;
	}
	
	public String getNgaysua()
	{
		return this.ngaysua;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.ngaysua = ngaysua;
	}
	
	public String getNguoisua()
	{
		return this.nguoisua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.nguoisua = nguoisua;
	}
	
	public String getMessage() 
	{
		return this.msg;
	}
	
	public void setMessage(String msg) 
	{
		this.msg = msg;
	}
	
	public boolean CreatePbgn()
	{	
		this.ngaytao = getDateTime();
		this.nguoitao = this.userId;
		ResultSet rsNvgn = null;
		if(this.nvgnId.trim().length() <= 0)
		{
			this.msg = "Bạn chưa chọn nhân viên giao nhận";
			return false;
		}
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Bạn chưa chọn khách hàng";
			return false;
		}

		if(this.nvgn_newId.trim().length() <= 0)
		{
			this.msg = "Bạn chưa chọn nhân viên giao hàng thay thế";
			return false;
		}
		try 
		{
			db.getConnection().setAutoCommit(false);
		
			String tinhthanh_fk = this.tinhthanhId.trim().length() <= 0 ? "NULL" : this.tinhthanhId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL" : this.quanhuyenId;
			
			String query = "insert into phanbogiaonhan(ngay, denngay, trangthai, diengiai, ngaytao, ngaysua, nguoitao, nguoisua, nhanviengn, nhanviengn_moi, tinhthanh_fk, quanhuyen_fk, congty_fk, npp_fk, kh_fk) " ;
			query = query + "values(N'" + this.ngay + "', '" + this.denngay + "', '1','" + this.diengiai + "','" + this.ngaytao + "','" + this.ngaytao + "','" + this.nguoitao +"','" + this.nguoitao +"','" + this.nvgnId + "','" + this.nvgn_newId + "', " + tinhthanh_fk + ", " + quanhuyen_fk +",'"+this.congtyId+"', '"+this.nppId+"', '" + this.khId + "')";
			System.out.print(query);
			if(!db.update(query))
			{
				db.update("rollback");
				this.msg = "Khong the tao moi 'phanbogiaonhan' , " + query;
				return false; 
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		finally{try {
			if(rsNvgn != null)
				rsNvgn.close();
		} catch (Exception e2) {
			
		}}
		return true;	
	}
	
	public boolean UpdatePbgn()
	{
		this.ngaysua = getDateTime();
		this.nguoisua = this.userId;
		if(this.nvgnId.trim().length() <= 0)
		{
			this.msg = "Bạn chưa chọn nhân viên giao nhận";
			return false;
		}
		if(this.khId.trim().length() <= 0)
		{
			this.msg = "Bạn chưa chọn khách hàng";
			return false;
		}
		if(this.nvgn_newId.trim().length() <= 0)
		{
			this.msg = "Bạn chưa chọn nhân viên giao hàng thay thế";
			return false;
		}
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String tinhthanh_fk = this.tinhthanhId.trim().length() <= 0 ? "NULL" : this.tinhthanhId;
			String quanhuyen_fk = this.quanhuyenId.trim().length() <= 0 ? "NULL" : this.quanhuyenId;
			
			String query = "update phanbogiaonhan set ngay='" + this.ngay + "', denngay = '" + this.denngay + "', trangthai='" + this.trangthai + "', diengiai=N'" + this.diengiai + "', nhanviengn='";
			query = query + this.nvgnId + "', nhanviengn_moi = '" + this.nvgn_newId + "', tinhthanh_fk = " + tinhthanh_fk + ", quanhuyen_fk = " + quanhuyen_fk + ", nguoisua='" + this.nguoisua + "',  ngaysua='" + this.ngaysua + "', kh_fk = '" + this.khId + "' "; 
			query = query +	" where pk_seq='" + this.id + "'";
			if(!db.update(query))
			{
				db.getConnection().rollback();
				this.msg = "Khong the cap nhat 'phanbogiaonhan' , " + query;
				return false; 
			}	
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch(Exception e) {
			db.update("rollback");
			this.msg = "Loi khi cap nhat bang "+e.toString();
			return false; 
		}
		return true;
	}
	private void getNppInfo()
	{	
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppId=util.getIdNhapp(this.userId);
		this.nppTen=util.getTenNhaPP();
		this.sitecode=util.getSitecode();
		System.out.println("NPP ID " + this.nppId);
	}
	public void init() 
	{
		this.getNppInfo();
		String query = "select a.pk_seq, a.diengiai, a.ngay, a.denngay, a.trangthai, a.nhanviengn, a.nhanviengn_moi, a.ngaytao, a.ngaysua, b.ten as nguoitao, c.ten as nguoisua, a.tinhthanh_fk, a.quanhuyen_fk, a.KH_FK, a.DDH_FK ";

		query = query + "from phanbogiaonhan a inner join nhanvien b on a.nguoitao = b.pk_seq inner join nhanvien c on a.nguoisua = c.pk_seq where 1=1";
		ResultSet rs =  db.get(query + " and a.pk_seq='" + this.id + "'");
		System.out.println(query + " and a.pk_seq='" + this.id + "'");
		try
        {
            rs.next();        	
            this.id = rs.getString("pk_seq");
			this.diengiai = rs.getString("diengiai");
			this.trangthai = rs.getString("trangthai");
			this.ngay = rs.getString("ngay");
			this.denngay = rs.getString("denngay");
			this.nvgnId = rs.getString("nhanviengn");
			this.nvgn_newId = rs.getString("nhanviengn_moi");
			this.ngaytao = rs.getString("ngaytao");
			this.nguoitao = rs.getString("nguoitao");
			this.ngaysua = rs.getString("ngaysua");
			this.nguoisua = rs.getString("nguoisua");
			this.khId = rs.getString("kh_fk")==null?"":rs.getString("kh_fk");
			this.ddhId = rs.getString("ddh_fk")==null?"":rs.getString("ddh_fk");
			
			this.tinhthanhId = rs.getString("tinhthanh_fk")==null?"":rs.getString("tinhthanh_fk");
			this.quanhuyenId = rs.getString("quanhuyen_fk")==null?"":rs.getString("quanhuyen_fk");
			
			rs.close();	
       	}
        catch(Exception e){}
        finally{try {
			if(rs != null)
				rs.close();
		} catch (Exception e2) {
		
		}}
        
		createRS();
	}

	public void createRS() 
	{
		Utility util = new Utility();
		int[] quyen = util.GetquyenNew("PhanbogiaonhanSvl", "", this.userId );
		
		this.getNppInfo();
		
		String	 query="select * from tinhthanh where trangthai = 1 order by TEN ";
		this.tinhthanhRs = db.get(query);
		System.out.println("tinh thanh " + this.tinhthanhId);
		if(this.tinhthanhId.length()>0)
		{
			query="select * from quanhuyen where trangthai=1 ";
			query+=" and tinhthanh_fk='"+this.tinhthanhId+"'";
			query+=" order by TEN ";
			 
			this.quanhuyenRs = this.db.get(query);
		}
		System.out.println("quan huyen " + this.quanhuyenId);
		
		query = "select pk_seq, ten from nhanviengiaonhan a where trangthai = 1 and npp_fk='" + this.nppId + "'";
		
		//PHAN QUYEN THEO NHAN VIEN
		if( quyen[ Utility.HIENTHIALL ] == 0 )
			query += util.getPhanQuyen_TheoNhanVien("NHANVIENGIAONHAN", "a", "pk_seq", this.getLoainhanvien(), this.getDoituongId() );
		
		this.nvgnRs = db.get(query);
		
		if(this.nvgnId.trim().length() > 0)
		{
			query += " and PK_SEQ not in ('"+this.nvgnId+"') ";
			this.nvgn_newRs = db.get(query);
		}

		if(this.nppId.trim().length() > 0 && this.nvgnId.trim().length() > 0)
		{
			query = " select a.pk_seq, a.TEN from KHACHHANG a, nvgn_kh b where a.trangthai = '1' and a.NPP_FK = '" + this.nppId + "' and "
					+ "a.PK_SEQ = b.KHACHHANG_FK and b.NVGN_FK = '" + this.nvgnId + "' ";
			this.khRs = db.get(query);
		}

		/*if(this.khId.trim().length() > 0 )
		{
			query = "select PK_SEQ, CAST(pk_seq as varchar(10)) + ' / ' + NgayDonHang as ten " +
					"from ERP_DONDATHANGNPP where NgayDonHang = '" + this.ngay + "' and TRANGTHAI in ( 2, 4 ) and NPP_FK = '" + this.nppId + "'  ";
			
<<<<<<< .mine
			query += " and KHACHHANG_FK in (" + this.khId + ") ";
			//query += " and pk_seq not in (  ) ";
			
=======
			query += " and KHACHHANG_FK in (" + this.khId + ") ";
>>>>>>> .r26512
			System.out.println("don dat hang rs " + query);
			this.ddhRs = db.get(query);
		}*/
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBclose()
	{
		try 
		{
			if(this.nvgnRs != null)
				this.nvgnRs.close();
			if(this.tinhthanhRs != null)
				this.tinhthanhRs.close();
			if(this.quanhuyenRs != null)
				this.quanhuyenRs.close();
			
			if(this.db != null)
				this.db.shutDown();
			
		} 
		catch(Exception e) {}
	}

	
	public String getDiengiai() {
		
		return this.diengiai;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}

	
	public String getNgay() {
		
		return this.ngay;
	}

	
	public void setNgay(String ngay) {
		
		this.ngay = ngay;
	}

	
	public String getNvgnId() {
		
		return this.nvgnId;
	}

	
	public void setNvgnId(String nvgnId) {
		
		this.nvgnId = nvgnId;
	}

	
	public String getNvgn_newId() {
		
		return this.nvgn_newId;
	}

	
	public void setNvgn_newId(String nvgn_newId) {
		
		this.nvgn_newId = nvgn_newId;
	}

	
	public String getTinhthanhId() {
		
		return this.tinhthanhId;
	}

	
	public void setTinhthanhId(String tinhthanhId) {
		
		this.tinhthanhId = tinhthanhId;
	}

	
	public String getQuanhuyenId() {
		
		return this.quanhuyenId;
	}

	
	public void setQuanhuyenId(String quanhuyenId) {
		
		this.quanhuyenId = quanhuyenId;
	}

	
	public ResultSet getNvgnRs() {
		
		return this.nvgnRs;
	}

	
	public void setNvgnRs(ResultSet nvgnRs) {
		
		this.nvgnRs = nvgnRs;
	}
	
	
	public ResultSet getNvgn_newRs() {
		
		return this.nvgn_newRs;
	}

	
	public void setNvgn_newRs(ResultSet nvgn_newRs) {
		
		this.nvgn_newRs = nvgn_newRs;
	}

	
	public ResultSet getTinhthanhRs() {
		
		return this.tinhthanhRs;
	}

	
	public void setTinhthanhRs(ResultSet tinhthanhRs) {
		
		this.tinhthanhRs = tinhthanhRs;
	}

	
	public ResultSet getQuanhuyenRs() {
		
		return this.quanhuyenRs;
	}

	
	public void setQuanhuyenRs(ResultSet quanhuyenRs) {
		
		this.quanhuyenRs = quanhuyenRs;
	}

	
	public String getTennv() {
		
		return this.tennv;
	}

	
	public void setTennv(String tennv) {
		
		this.tennv = tennv;
	}

	
	public String getKhId() {
		
		return this.khId;
	}

	
	public void setKhId(String khId) {
		
		this.khId = khId;
	}

	
	public ResultSet getKhRs() {
		
		return this.khRs;
	}

	
	public void setKhRs(ResultSet khRs) {
		
		this.khRs = khRs;
	}

	
	public String getDdhId() {
		
		return this.ddhId;
	}

	
	public void setDdhId(String ddhId) {
		
		this.ddhId = ddhId;
	}

	
	public ResultSet getDdhRs() {
		
		return this.ddhRs;
	}

	
	public void setDdhRs(ResultSet ddhRs) {
		
		this.ddhRs = ddhRs;
	}

	
	public String getCongtyId() {
		
		return this.congtyId;
	}

	
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}
	
	Object loainhanvien;
	Object doituongId;
	public String getLoainhanvien() 
	{
		if( this.loainhanvien == null )
			return "";
		
		return this.loainhanvien.toString();
	}

	public void setLoainhanvien(Object loainhanvien) 
	{
		this.loainhanvien = loainhanvien;
	}
	
	public String getDoituongId() 
	{
		if( this.doituongId == null )
			return "";
		
		return this.doituongId.toString();
	}

	public void setDoituongId(Object doituongId) 
	{
		this.doituongId = doituongId;
	}

	public String getDenngay() {
	
		return this.denngay;
	}

	public void setDenngay(String denngay) {
		
		this.denngay = denngay;
	}
	
}

