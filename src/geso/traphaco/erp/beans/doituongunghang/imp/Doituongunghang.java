package geso.traphaco.erp.beans.doituongunghang.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.doituongunghang.IDoituongunghang;
import geso.traphaco.center.db.sql.dbutils;

public class Doituongunghang implements IDoituongunghang {

	String ctyId;
	String Ma;
	String Ten;
	String Taikhoan;
	String Diachi;
	String Dienthoai;
	String Email;
	String userId;
	String userTen;
	String trangthai;
	String Id;
	String msg;
	dbutils db;
	String chon;
	String sotaikhoan;
	String nhId;
	String cnId;
	String pbId;
	String tkktId;
	ResultSet nhRs; //Ngân hàng
	ResultSet cnRs; //
	ResultSet pbRs; //Phòng ban = Bộ phận 
	ResultSet tkktRs; //Tài khoản kế toán
	
	String diachi;
	String cmnd;
	String ngaycap;
	String noicap;
	
	public Doituongunghang()
	{   
		this.ctyId = "";
		this.Id ="";
		this.Ma = "";
		this.Ten="";
		//this.Taikhoan = "141000 - Tạm ứng";
		this.Dienthoai="";
		this.Email="";
		this.userId="";
		this.userTen="";
		this.trangthai="";
		this.chon ="1";
		this.msg="";
		this.sotaikhoan = "";
		this.nhId = "";
		this.cnId = "";
		this.pbId = "";
		this.tkktId="";
		this.diachi  = "";
		this.cmnd = "";
		this.ngaycap = "";
		this.noicap = "";
		db = new dbutils();
	}
	public Doituongunghang(String Id)
	{   
		
		this.Id = Id;
		this.Ma = "";
		this.Ten ="";
		//this.Taikhoan = "141000 - Tạm ứng";
		this.Dienthoai = "";
		this.Email = "";
		this.userId = "";
		this.userTen = "";
		this.trangthai = "";
		this.chon = "1";
		this.msg = "";
		this.sotaikhoan = "";
		this.nhId = "";
		this.cnId = "";
		this.pbId = "";
		this.tkktId="";
		this.diachi  = "";
		this.cmnd = "";
		this.ngaycap = "";
		this.noicap = "";

		db = new dbutils();
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public void setMa(String Ma) {
		this.Ma = Ma;
		
	}

	public String getMa() {
		return this.Ma;
	}
	
	public void setTaikhoan(String taikhoan) {
		this.Taikhoan = taikhoan;
		
	}

	public String getTaikhoan() {
		return this.Taikhoan;
	}

	public void setuserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getuserId() {
		
		return userId;
	}
	
	
	public void setTen(String Ten) {
		
		this.Ten = Ten;
	}
	
	public String getTen() {
		
		return this.Ten;
	}
	
	
	public void setEmail(String email) {
		
		this.Email = email;
	}
	
	public String getEmail() {
		
		return this.Email;
	}
	
	public void setdienthoai(String dienthoai) {
		
		this.Dienthoai = dienthoai;
	}
	
	public String getdienthoai() {
		
		return this.Dienthoai;
	}
	
	public void setSotaikhoan(String sotaikhoan) {
		
		this.sotaikhoan = sotaikhoan;
	}
	
	public String getSotaikhoan() {
		
		return this.sotaikhoan;
	}

	public void setNhId(String nhId) {
		
		this.nhId = nhId;
	}
	
	public String getNhId() {
		
		return this.nhId;
	}

	public void setTkktId(String tkktId) {
		
		this.tkktId = tkktId;
	}
	
	public String getTkktId() {
		
		return this.tkktId;
	}
	
	public void setNhRs(ResultSet nhRs) {
		
		this.nhRs = nhRs;
	}
	
	public ResultSet getNhRs() {
		
		return this.nhRs;
	}
	
	public void setCnId(String cnId) {
		
		this.cnId = cnId;
	}
	
	public String getCnId() {
		
		return this.cnId;
	}

	public void setCnRs(ResultSet cnRs) {
		
		this.cnRs = cnRs;
	}
	
	public ResultSet getCnRs() {
		
		return this.cnRs;
	}

	public void init() {
		
		if(this.Id.length()>0)
		{
			try {
				String sql = 	"SELECT	NVTU.PK_SEQ, NVTU.MA, NVTU.TEN, ISNULL(NVTU.EMAIL,'') AS EMAIL, " +
								"ISNULL(NVTU.DIACHI, '') AS DIACHI, ISNULL(NVTU.DIENTHOAI, '') AS DIENTHOAI, " +
								"TK.SOHIEUTAIKHOAN + ' - ' + TK.TENTAIKHOAN AS TAIKHOAN,NVTU.TAIKHOAN_FK AS TKKTID, NVTU.DVTH_FK AS DVTHID, " +
								"NVTU.TRANGTHAI, NV2.TEN AS NGUOISUA, NVTU.NGAYSUA, ISNULL(NVTU.SOTAIKHOAN, '') AS SOTAIKHOAN, " +
								"ISNULL(NVTU.NGANHANG_FK, '0') AS NHID, " +
								"ISNULL(NVTU.CHINHANH_FK, '0') AS CNID, " +
								"ISNULL(NVTU.CONGTY_FK, '0') AS CONGTYID, ISNULL(NVTU.DIACHI, '') DIACHI,  " +
								"ISNULL(NVTU.NOICAP, '') NOICAP, ISNULL(NVTU.NGAYCAP, '') NGAYCAP, ISNULL(NVTU.CMND, '') CMND "+
								"FROM ERP_DOITUONGUNGHANG NVTU " +
								"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = NVTU.NGUOITAO " + 
								"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = NVTU.NGUOISUA " +
								"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = NVTU.TAIKHOAN_FK " +
								//"INNER JOIN ERP_DONVITHUCHIEN DVTH ON NVTU.DVTH_FK = DVTH.PK_SEQ" +
								"WHERE NVTU.PK_SEQ = '"+ this.Id+"'";
				
				System.out.println("[Nhanvien.init] sql = " + sql);
				ResultSet rs = db.get(sql);
			
				while(rs.next())
				{
					this.Ma = rs.getString("ma");
					this.Ten = rs.getString("ten");
					this.Email = rs.getString("email");
					this.Dienthoai = rs.getString("dienthoai");
					this.trangthai = rs.getString("trangthai");
					this.Taikhoan = rs.getString("taikhoan");
					this.tkktId= rs.getString("tkktid");
					this.sotaikhoan = rs.getString("SOTAIKHOAN");
					this.ctyId = rs.getString("CONGTYID");
					this.nhId = rs.getString("NHID");
					this.cnId = rs.getString("CNID");
					this.pbId = rs.getString("DVTHID");
					this.cmnd = rs.getString("CMND");
					this.ngaycap = rs.getString("NGAYCAP");
					this.noicap = rs.getString("NOICAP");
					this.diachi = rs.getString("DIACHI");
					
					this.msg = "";
				}
				rs.close();
				
			
			} catch (SQLException e) {				
				e.printStackTrace();
			}
			
		}

		String sql;
		try {
			sql = "SELECT PK_SEQ AS NHID, MA + ' - ' + TEN AS NH FROM ERP_NGANHANG WHERE TRANGTHAI = 1";
			System.out.println("[Nhanvien.init] nganhang sql = " + sql);
			this.nhRs = this.db.get(sql);
		} catch(Exception e) {
			System.out.println("[Nhanvien.init] Exception Message = " + e.getMessage());
		}
		
		try {
			sql = "SELECT PK_SEQ AS CNID, MA + ' - ' + TEN AS CN FROM ERP_CHINHANH WHERE TRANGTHAI = 1";
			System.out.println("[Nhanvien.init] chinhanh sql = " + sql);
			this.cnRs = this.db.get(sql);
		} catch(Exception e) {
			System.out.println("[Nhanvien.init] Exception Message = " + e.getMessage());
		}
		
		try {
			sql = "SELECT PK_SEQ AS PBID, MA + ' - ' + TEN AS PB FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = 1";
			System.out.println("[Nhanvien.init] phongban sql = " + sql);
			this.pbRs = this.db.get(sql);
		} catch(Exception e) {
			System.out.println("[Nhanvien.init] Exception Message = " + e.getMessage());
		}
		try {
			sql= " SELECT PK_SEQ AS TKKTID, SOHIEUTAIKHOAN +' - '+ TENTAIKHOAN as TAIKHOANKT FROM ERP_TAIKHOANKT WHERE TRANGTHAI = 1  and CONGTY_FK="+this.ctyId;
			System.out.println("Tài khoản kế toán: " + sql);
			this.tkktRs= this.db.get(sql);
		} catch(Exception e){
			System.out.println("[Nhanvien.init] Exception TaikhoanKT = " + e.getMessage());
		}
	}
   
	
	public void settrangthai(String trangthai) {
		
		this.trangthai = trangthai;
	}
	
	public String gettrangthai() {
		
		return this.trangthai;
	}
	
	public void setmsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	boolean xoa()
	{
		String query = "DELETE ERP_DOITUONGUNGHANG WHERE PK_SEQ = " + this.Id + "";
		this.db.update(query);
		
		return true;
	}
	
	public boolean save() 
	{		
		
		if(this.Ma == "" || this.Ten == ""|| this.tkktId == "" ||this.pbId == "")
		{  
			this.msg = "Bạn phải nhập thông tin đầy đủ";
			return false;
		}
		//CHECK TRÙNG MÃ
		
		try 
		{
			String queryId ="SELECT * FROM ERP_DOITUONGUNGHANG WHERE MA=N'"+this.Ma+"' ";
			if(this.Id.length() >0){
				queryId +=" and PK_SEQ not in ("+this.Id+")";
			}
			ResultSet IdRs = db.get(queryId);
			if(IdRs.next())
				{
					this.msg = "Mã nhân viên này đã tồn tại. Vui lòng nhập lại Mã nhân viên.";
					return false;
				}
			IdRs.close();
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
		
		if(this.Id.length() >0)
		{	
			String sql;
			sql ="UPDATE ERP_DOITUONGUNGHANG SET MA = N'" + this.Ma + "', TEN = N'"+ this.Ten +"', email = '"+ this.Email +"', " +
				 "DIENTHOAI = '"+ this.Dienthoai+"', trangthai = '"+ this.trangthai+"', taikhoan_fk = '"+ this.tkktId +"' , " +
				 "SOTAIKHOAN = '" + this.sotaikhoan + "', NGANHANG_FK = " + this.nhId + ", CHINHANH_FK = " + this.cnId + ", " +
				 "DVTH_FK = '" + this.pbId + "', " +
				 "ngaysua ='"+ this.getDateTime() +"',nguoisua ='"+ this.userId+"',  cmnd ='"+this.cmnd+"', noicap =N'" +this.noicap+"', ngaycap = '"+this.ngaycap+"', diachi =N'"+this.diachi+"' "+
				 "where pk_seq ='"+ this.Id +"'";
			
			System.out.println("[Nhanvien] sql = " +this.tkktId + sql);
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg = "Co loi trong qua trinh update (" + sql + ")";
				return false;
			}
							
		}
		else
		{   
			
			String sql;
			sql =	"INSERT INTO ERP_DOITUONGUNGHANG(MA, TEN, EMAIL, DIENTHOAI, TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TAIKHOAN_FK, CONGTY_FK, SOTAIKHOAN, NGANHANG_FK, CHINHANH_FK, DVTH_FK, cmnd, ngaycap, noicap, diachi) VALUES("+
					"N'" + this.Ma + "', N'"+ this.Ten +"','"+ this.Email +"', '"+this.Dienthoai+"','"+this.trangthai+"','"+ this.getDateTime() +"'," +
					"'"+ this.getDateTime() +"','"+ this.userId+"','"+ this.userId+"', '"+ this.tkktId+ "', " + this.ctyId + ", '" + this.sotaikhoan + "', "  + this.nhId + ", " + this.cnId + ", '" + this.pbId + "', '"+this.ngaycap+"', '"+this.cmnd+"', '"+this.noicap+"', N'"+this.diachi+"')";
			
			System.out.println("[Nhanvien.save] sql = " + sql);
			try {
				db.getConnection().setAutoCommit(false);
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg =sql;
					return false;
				}
					 //System.out.println(sql);
				sql = "SELECT IDENT_CURRENT('ERP_DOITUONGUNGHANG') AS NVID";
					
				ResultSet rsDh = this.db.get(sql);						
				rsDh.next();
				this.Id = rsDh.getString("NVID");
				rsDh.close();
									
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				this.Id="";
				e.printStackTrace();
			}
		}	
		return true;
	}
	
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	public void setId(String Id) {
		this.Id = Id;
		
	}
	
	public String getId() {
		
		return this.Id;
	}
	
	
	
	
	public void DbClose() {
		try{
			db.shutDown();
		}catch(Exception er){
			
		}
	}
	
	public void setPbId(String pbId) {
		this.pbId = pbId;
	}
	
	public String getPbId() {
		return this.pbId;
	}
	
	public void setPbRs(ResultSet pbRs) {
		this.pbRs = pbRs;
	}
	
	public ResultSet getPbRs() {
		return this.pbRs;
	}
	
	public void setTkktRs(ResultSet tkktRs) {
			this.tkktRs= tkktRs;
		
	}
	
	public ResultSet getTkktRs() {
		
		return this.tkktRs;
	}
	
	public void setdiachi(String diachi) {
		
		this.diachi = diachi;
	}
	
	public String getdiachi() {
		
		return this.diachi;
	}
	
	public void setcmnd(String cmnd) {
		
		this.cmnd = cmnd;
	}
	
	public String getcmnd() {
		
		return this.cmnd;
	}
	
	public void setngaycap(String ngaycap) {
		
		this.ngaycap = ngaycap;
	}
	
	public String getngaycap() {
		
		return this.ngaycap;
	}
	
	public void setnoicap(String noicap) {
		
		this.noicap = noicap;
	}
	
	public String getnoicap() {
		
		return this.noicap;
	}
}
