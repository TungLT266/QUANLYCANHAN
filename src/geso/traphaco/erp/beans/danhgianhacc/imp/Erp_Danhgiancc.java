package geso.traphaco.erp.beans.danhgianhacc.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.danhgianhacc.IErp_Danhgiancc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Erp_Danhgiancc implements IErp_Danhgiancc{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String congtyId;
	String nppId;
	String userId;
	String id;
	String nccId;
	ResultSet nccRs;
	String trangthai;
	String spId;
	ResultSet spRs;
	String dvthId;
	ResultSet dvthRs;
	String ngaytao;
	String nguoitao;
	String ngaysua;
	String nguoisua;
	String tucach;
	String uytin;
	String chatluongsp;
	String thoigiangiaohang;
	String giaca;
	String phuongthuctt;
	String phuongthucvc;
	String haumai;
	String chinhsachkhac;
	String sopo;
	ResultSet pors;
	dbutils db;
	Utility util;
	String active;
	String msg;
	
	public Erp_Danhgiancc(String[] param)
	{
		this.db = new dbutils();
		this.id = param[0];
		this.spId = param[1];
		this.ngaytao = param[2];
		this.nguoitao = param[3];
		this.ngaysua = param[4];
		this.nguoisua = param[5];
		this.trangthai = param[6];
		this.msg = "";
		this.active = "0";
		this.util=new Utility();
	}
	public Erp_Danhgiancc(String id) {
		this.congtyId = "";
		this.nppId = "";
		this.userId = "";
		this.id = id;
		this.nccId = "";
		this.spId = "";
		this.trangthai = "1";
		this.dvthId = "";
		this.ngaytao = "";
		this.nguoitao = "";
		this.ngaysua = "";
		this.nguoisua = "";
		this.tucach = "0";
		this.uytin = "0";
		this.chatluongsp = "0";
		this.thoigiangiaohang = "0";
		this.giaca = "";
		this.phuongthuctt = "";
		this.phuongthucvc = "";
		this.haumai = "";
		this.chinhsachkhac = "";
		this.sopo = "null";
		this.msg = "";
		this.active = "0";
		this.util=new Utility();
		this.db = new dbutils();
		if(id.length() > 0)
			this.init();
	}
	
	@Override
	public String getCongtyId() {
		
		return this.congtyId;
	}
	@Override
	public void setCongtyId(String congtyId) {
		
		this.congtyId = congtyId;
	}
	@Override
	public String getUserId() {
		
		return this.userId;
	}
	@Override
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	@Override
	public String getId() {
		
		return this.id;
	}
	@Override
	public void setId(String id) {
		
		this.id = id;
	}
	
	@Override
	public String getTrangthai() {
		
		return this.trangthai;
	}
	@Override
	public void setTrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}
	@Override
	public void createRs() {
		
		String query = " select pk_seq, ten from ERP_DONVITHUCHIEN " +
				   		" where trangthai = '1'";// and congty_fk = '" + this.congtyId + "' and pk_seq in " + util.quyen_donvithuchien(this.userId);
		
		System.out.println("Get Dvth : "+query);
		System.out.println("dvth "+query);
		this.dvthRs = db.get(query);
		
		/*query = "select pk_seq, ma + '-' + diengiai as ten from erp_congcudungcu where trangthai = 0";
		System.out.println("sp "+query);
		this.spRs = db.get(query);*/
		
		query = "select pk_seq, ten from erp_nhacungcap where trangthai = 1 and duyet = '1' and congty_fk = "+ this.congtyId +" ";
		System.out.println("danh sach ncc " + query);
		this.nccRs = db.get(query);	
		
		/*if(this.spId.length() > 0)
		{*/
		
		query = "SELECT DISTINCT A.PK_SEQ, A.SOPO FROM ERP_MUAHANG A INNER JOIN ERP_MUAHANG_SP B ON A.PK_SEQ = B.MUAHANG_FK "
				+ "where A.trangthai = 1 and A.congty_fk = '"+this.congtyId+"' and A.loai = 2 " ;
		
		/*"AND (b.ccdc_fk = '"+this.spId+"') ";*/
		System.out.println("po rs " + query);
		this.pors = db.get(query);
		/*}*/
	}
	
	@Override
	public void init() {
		
		try
        {
			String query = "select a.PK_SEQ, a.NCC_FK, a.TRANGTHAI, a.NGAYSUA, a.NGAYTAO, b.TEN as nguoitao, c.TEN as nguoisua, a.SP_FK, a.dvth_fk, e.pk_seq as tieuchi, d.diem, d.dat, a.muahang_fk  ";
					query = query + "from ERP_DANHGIANCC a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ ";
					query = query + "inner join ERP_DGNCC_TIEUCHIDG d on a.PK_SEQ = d.DG_FK right join ERP_TIEUCHIDANHGIA e on d.TIEUCHI_FK = e.pk_seq where a.PK_SEQ = '"+ this.id + "'";
			System.out.println("lay thong tin danh gia: " + query);
			ResultSet rs =  this.db.get(query);
			
            while(rs.next())
			{
	        	this.id = rs.getString("pk_seq");
	        	this.trangthai = rs.getString("trangthai")==null?"0":rs.getString("trangthai");
	        	this.ngaytao = rs.getString("ngaytao");
	        	this.nguoitao = rs.getString("nguoitao");
	        	this.ngaysua = rs.getString("ngaysua");
	        	this.nguoisua = rs.getString("nguoisua");
	        	this.dvthId = rs.getString("dvth_fk");
	        	this.spId = rs.getString("sp_fk");
	        	this.nccId = rs.getString("ncc_fk");
	        	this.sopo = rs.getString("muahang_fk");
	        	String tieuchi = rs.getString("tieuchi");
	        	if(tieuchi.equals("100000"))
	        		this.tucach = rs.getString("dat");
	        	if(tieuchi.equals("100001"))
	        		this.uytin = rs.getString("dat");
	        	if(tieuchi.equals("100002"))
	        		this.chatluongsp = rs.getString("dat");
	        	if(tieuchi.equals("100003"))
	        		this.thoigiangiaohang = rs.getString("dat");
	        	if(tieuchi.equals("100004"))
	        		this.giaca = rs.getString("diem");
	        	if(tieuchi.equals("100005"))
	        		this.phuongthuctt = rs.getString("diem");
	        	if(tieuchi.equals("100006"))
	        		this.phuongthucvc = rs.getString("diem");
	        	if(tieuchi.equals("100007"))
	        		this.haumai = rs.getString("diem");
	        	if(tieuchi.equals("100008"))
	        		this.chinhsachkhac = rs.getString("diem");
	        	
			}
			rs.close();
			
       	}
        catch (java.sql.SQLException e){
        	e.printStackTrace();
        }
		
		this.createRs();
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public boolean createDGNcc()
	{
		try
		{
			this.nppId = util.getIdNhapp(userId);
			
			if(this.dvthId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị thực hiện";
				return false;
			}
			if(this.nccId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp";
				return false;
			}
			if(Double.parseDouble(this.giaca.trim()) > 5)
			{
				this.msg = "Điểm giá cả không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.phuongthuctt.trim()) > 5)
			{
				this.msg = "Điểm phương thức thanh toán không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.phuongthucvc.trim()) > 5)
			{
				this.msg = "Điểm phương thức vận chuyển không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.haumai.trim()) > 5)
			{
				this.msg = "Điểm chế độ hậu mãi không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.chinhsachkhac.trim()) > 5)
			{
				this.msg = "Điểm chính sách khác không được lớn hơn 5";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "insert into erp_Danhgiancc(trangthai, ngaytao, nguoitao, ngaysua, nguoisua, dvth_FK, " +
					"npp_fk, sp_fk, ncc_fk, congty_fk, muahang_fk) " +
					"values(0, '" + this.getDateTime() + "', '" + this.userId + "', '" + this.getDateTime() 
					+ "', '" + this.userId + "', '" + this.dvthId + "', null,null,'"
					+this.nccId+"', '"+this.congtyId+"', "+this.sopo+" )";
			
			System.out.println("1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the tao moi erp_Danhgiancc: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('erp_Danhgiancc') as Id";
			ResultSet rs = db.get(query);
			rs.next();
			this.id = rs.getString("Id");
			rs.close();
			
			query = "select pk_seq from ERP_TIEUCHIDANHGIA where trangthai = 1";
			rs = db.get(query);
			while (rs.next())
			{
				query = "insert into ERP_DGNCC_TIEUCHIDG(dg_FK, tieuchi_FK, dat, diem) " +
						"values('"+ this.id + "', '" + rs.getString("pk_seq") + "', '0', '0' )";
				
				System.out.println("1.Insert: " + query);
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			rs.close();
			if(this.tucach.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100000 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.uytin.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100001 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.chatluongsp.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100002 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.thoigiangiaohang.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100003 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.giaca.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.giaca+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100004 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.phuongthuctt.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.phuongthuctt+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100005 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.phuongthucvc.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.phuongthucvc+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100006 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.haumai.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.haumai+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100007 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.chinhsachkhac.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.chinhsachkhac+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100008 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				e.printStackTrace();
				
				this.msg = "Lỗi khi tạo mới tìm nhà cung cấp: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		return true;
	}
	
	public boolean updateDGNcc()
	{
		try
		{
			this.nppId = util.getIdNhapp(userId);
			
			if(this.dvthId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị thực hiện";
				return false;
			}
			if(this.nccId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn nhà cung cấp";
				return false;
			}
			if(Double.parseDouble(this.giaca.trim()) > 5)
			{
				this.msg = "Điểm giá cả không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.phuongthuctt.trim()) > 5)
			{
				this.msg = "Điểm phương thức thanh toán không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.phuongthucvc.trim()) > 5)
			{
				this.msg = "Điểm phương thức vận chuyển không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.haumai.trim()) > 5)
			{
				this.msg = "Điểm chế độ hậu mãi không được lớn hơn 5";
				return false;
			}
			if(Double.parseDouble(this.chinhsachkhac.trim()) > 5)
			{
				this.msg = "Điểm chính sách khác không được lớn hơn 5";
				return false;
			}
			
			db.getConnection().setAutoCommit(false);
			
			String query = "update erp_Danhgiancc set  ngaysua = '"
			+this.getDateTime()+"', nguoisua = '"+this.userId 
			+"', dvth_fk = '"+this.dvthId +"', muahang_fk = "+this.sopo+" where pk_Seq = '"+this.id+"'";
			
			System.out.println("2.Update: " + query);
			if(!db.update(query))
			{
				this.msg = "Khong the cập nhật erp_Danhgiancc: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			if(this.tucach.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100000 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.uytin.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100001 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.chatluongsp.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100002 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.thoigiangiaohang.equals("1"))
			{
						query = "update ERP_DGNCC_TIEUCHIDG set dat = '1' where dg_fk = '"+this.id+"' and tieuchi_fk = 100003 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.giaca.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.giaca+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100004 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.phuongthuctt.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.phuongthuctt+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100005 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.phuongthucvc.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.phuongthucvc+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100006 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.haumai.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.haumai+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100007 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
			if(this.chinhsachkhac.trim().length() > 0)
			{
						query = "update ERP_DGNCC_TIEUCHIDG set diem = '"+this.chinhsachkhac+"' where dg_fk = '"+this.id+"' and tieuchi_fk = 100008 ";
						
						System.out.println("1.Insert: " + query);
						if(!db.update(query))
						{
							this.msg = "Khong the tao moi ERP_DGNCC_TIEUCHIDG: " + query;
							db.getConnection().rollback();
							return false;
						}
			}
					
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e)
		{
			try 
			{
				db.getConnection().rollback();
				e.printStackTrace();
				
				this.msg = "Lỗi khi cập nhật tìm nhà cung cấp: " + e.getMessage();
			} 
			catch (SQLException e1) {}
			
			return false;
		}
		return true;
	}
	
	@Override
	public void close() {
		
		try{
			
			if(dvthRs!=null){
				dvthRs.close();
			}
			if(spRs!=null){
				spRs.close();
			}
			if(nccRs!=null){
				nccRs.close();
			}
			if(pors!=null){
				pors.close();
			}
		}catch(Exception er){
			er.printStackTrace();
		}
		db.shutDown();
	}

	@Override
	public String getDvthId() {
		
		return this.dvthId;
	}

	@Override
	public void setDvthId(String dvthId) {
		
		this.dvthId = dvthId;
	}

	@Override
	public ResultSet getDvthRs() {
		
		return this.dvthRs;
	}

	@Override
	public void setDvthRs(ResultSet dvthRs) {
		
		this.dvthRs = dvthRs;
	}
	
	@Override
	public String getSpId() {
		
		return this.spId;
	}

	@Override
	public void setSpId(String spId) {
		
		this.spId = spId;
	}

	@Override
	public ResultSet getSpRs() {
		
		return this.spRs;
	}

	@Override
	public void setSpRs(ResultSet spRs) {
		
		this.spRs = spRs;
	}

	@Override
	public String getNgaytao() {
		
		return this.ngaytao;
	}

	@Override
	public void setNgaytao(String ngaytao) {
		
		this.ngaytao = ngaytao;
	}

	@Override
	public String getNgaysua() {
		
		return this.ngaysua;
	}

	@Override
	public void setNgaysua(String ngaysua) {
		
		this.ngaysua = ngaysua;
	}

	@Override
	public String getNguoitao() {
		
		return this.nguoitao;
	}

	@Override
	public void setNguoitao(String nguoitao) {
		
		this.nguoitao = nguoitao;
	}

	@Override
	public String getNguoisua() {
		
		return this.nguoisua;
	}

	@Override
	public void setNguoisua(String nguoisua) {
		
		this.nguoisua = nguoisua;
	}

	@Override
	public String getMessage() {
		
		return this.msg;
	}

	@Override
	public void setMessage(String msg) {
		
		this.msg = msg;
	}
	
	public void setActive(String active) 
	{
		this.active = active;
	}

	public String getActive()
	{
		return this.active;
	}
	@Override
	public String getTucach() {
		
		return this.tucach;
	}
	@Override
	public void setTucach(String tucach) {
		
		this.tucach = tucach;
	}
	@Override
	public String getUytin() {
		
		return this.uytin;
	}
	@Override
	public void setUytin(String uytin) {
		
		this.uytin = uytin;
	}
	@Override
	public String getChatluong() {
		
		return this.chatluongsp;
	}
	@Override
	public void setChatluong(String chatluong) {
		
		this.chatluongsp = chatluong;
	}
//	@Override
//	public String getKhanangcn() {
//		
//		return this.khanangcn;
//	}
//	@Override
//	public void setKhanangcn(String khanangcn) {
//		
//		this.khanangcn = khanangcn;
//	}
	@Override
	public String getGiaca() {
		
		return this.giaca;
	}
	@Override
	public void setGiaca(String giaca) {
		
		this.giaca = giaca;
	}
	@Override
	public String getPtthanhtoan() {
		
		return this.phuongthuctt;
	}
	@Override
	public void setPtthanhtoan(String ptthanhtoan) {
		
		this.phuongthuctt = ptthanhtoan;
	}
	@Override
	public String getPtvanchuyen() {
		
		return this.phuongthucvc;
	}
	@Override
	public void setPtvanchuyen(String ptvanchuyen) {
		
		this.phuongthucvc = ptvanchuyen;
	}
	@Override
	public String getTggiaohang() {
		
		return this.thoigiangiaohang;
	}
	@Override
	public void setTggiaohang(String tggiaohang) {
		
		this.thoigiangiaohang = tggiaohang;
	}
	@Override
	public String getHaumai() {
		
		return this.haumai;
	}
	@Override
	public void setHaumai(String haumai) {
		
		this.haumai = haumai;
	}
	@Override
	public String getChinhsachkhac() {
		
		return this.chinhsachkhac;
	}
	@Override
	public void setChinhsachkhac(String chinhsachkhac) {
		
		this.chinhsachkhac = chinhsachkhac;
	}
	@Override
	public String getNccId() {
		
		return this.nccId;
	}
	@Override
	public void setNccId(String nccId) {
		
		this.nccId = nccId;
	}
	@Override
	public ResultSet getNccRs() {
		
		return this.nccRs;
	}
	@Override
	public void setNccRs(ResultSet nccRs) {
		
		this.nccRs = nccRs;
	}
	@Override
	public String getSoPO() {
		return this.sopo;
	}
	@Override
	public void setSoPO(String sopo) {
		this.sopo = sopo;
	}
	@Override
	public ResultSet getPORs() {
		return this.pors;
	}
	@Override
	public void setPORs(ResultSet pors) {
		this.pors = pors;
	}
}
