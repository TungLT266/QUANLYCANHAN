package geso.traphaco.erp.beans.tamung.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tamung.INhanvien;
import geso.traphaco.erp.util.UtilitySyn;

public class Nhanvien implements INhanvien {

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
	ResultSet pbRs; //Phòng ban = Bộ phận + Chi nhánh
	ResultSet tkktRs; //Tài khoản kế toán
	
	String diachi;
	String cmnd;
	String ngaycap;
	String noicap;
	
	String tkduId;
	String tkkqId;
	
	public Nhanvien()
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
		this.tkduId = "";
		this.tkkqId = ""; 
		db = new dbutils();
	}
	
	public Nhanvien(String Id)
	{   
		this();
		this.Id = Id;
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
				String sql = 	"SELECT	NVTU.PK_SEQ, NVTU.MA, NVTU.TEN, ISNULL(NVTU.EMAIL,'') AS EMAIL, \n" +
								"ISNULL(NVTU.DIACHI, '') AS DIACHI, ISNULL(NVTU.DIENTHOAI, '') AS DIENTHOAI, \n" +
								"TK.SOHIEUTAIKHOAN + ' - ' + TK.TENTAIKHOAN AS TAIKHOAN,TK.SOHIEUTAIKHOAN AS TKKTID, NVTU.DVTH_FK AS DVTHID, \n" +
								"NVTU.TRANGTHAI, NV2.TEN AS NGUOISUA, NVTU.NGAYSUA, ISNULL(NVTU.SOTAIKHOAN, '') AS SOTAIKHOAN, \n" +
								"ISNULL(NVTU.NGANHANG_FK, '0') AS NHID, \n" +
								"ISNULL(NVTU.CHINHANH_FK, '0') AS CNID, \n" +
								"ISNULL(NVTU.CONGTY_FK, '0') AS CONGTYID, ISNULL(NVTU.DIACHI, '') DIACHI,  \n" +
								"ISNULL(NVTU.NOICAP, '') NOICAP, ISNULL(NVTU.NGAYCAP, '') NGAYCAP, ISNULL(NVTU.CMND, '') CMND, NVTU.TAIKHOANDU_FK,  NVTU.TAIKHOANKQ_FK \n"+
								", isNull(convert(nvarchar, NVTU.npp_fk) , '') npp_fk\n" +
								"FROM ERP_NHANVIEN NVTU \n" +
								"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = NVTU.NGUOITAO \n" + 
								"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = NVTU.NGUOISUA \n" +
								"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = NVTU.TAIKHOAN_FK \n" +
								//"INNER JOIN ERP_DONVITHUCHIEN DVTH ON NVTU.DVTH_FK = DVTH.PK_SEQ\n" +
								"WHERE NVTU.PK_SEQ = '"+ this.Id+"'\n";
				
				System.out.println("[Nhanvien.init] sql = \n" + sql + "\n--------------------------------------------------------------");
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
					this.pbId = rs.getString("npp_fk");
					
					if (rs.getString("npp_fk").trim().equals("1"))
						this.pbId = "1," + rs.getString("DVTHID");
					else this.pbId = "0," + rs.getString("npp_fk");
					
					System.out.println("pbId: " + pbId);
					this.cmnd = rs.getString("CMND");
					this.ngaycap = rs.getString("NGAYCAP");
					this.noicap = rs.getString("NOICAP");
					this.diachi = rs.getString("DIACHI");
					this.tkduId = rs.getString("TAIKHOANDU_FK") == null ? "": rs.getString("TAIKHOANDU_FK"); ;
					this.tkkqId = rs.getString("TAIKHOANKQ_FK") == null ? "": rs.getString("TAIKHOANKQ_FK"); ;
					
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
			e.printStackTrace();
		}
		
		try {
			sql = "SELECT PK_SEQ AS CNID, MA + ' - ' + TEN AS CN FROM ERP_CHINHANH WHERE TRANGTHAI = 1";
			System.out.println("[Nhanvien.init] chinhanh sql = " + sql);
			this.cnRs = this.db.get(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
			sql = 
					"SELECT '0,' + convert(nvarchar, PK_SEQ) AS PBID, '[CN] ' +  isNull(mafast, '') + ' - ' + isNull(MA, '') + ' - ' + isNull(TEN, '') AS PB \n"
					+ "FROM NHAPHANPHOI WHERE TRANGTHAI = 1 and isKhachHang = 0\n"
					+ " union all\n"
					+ "SELECT '1,' + convert(nvarchar, PK_SEQ) AS PBID, '[PB] ' + isNull(MA, '') + ' - ' + isNull(TEN, '') AS PB \n" 
					+ "  FROM ERP_DONVITHUCHIEN  \n" 
					+ "  WHERE TRANGTHAI = 1\n";
			
			System.out.println("[Nhanvien.init] phongban sql = \n" + sql);
			this.pbRs = this.db.get(sql);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		try {
            sql= " SELECT SOHIEUTAIKHOAN AS TKKTID, SOHIEUTAIKHOAN +' - '+ TENTAIKHOAN as TAIKHOANKT FROM ERP_TAIKHOANKT WHERE TRANGTHAI = 1 AND CONGTY_FK = "+this.ctyId;
			System.out.println("Tài khoản kế toán: " + sql);
			this.tkktRs= this.db.getScrol(sql);
		} catch(Exception e){
			e.printStackTrace();
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
		String query = "DELETE ERP_NHANVIEN WHERE PK_SEQ = " + this.Id + "";
		this.db.update(query);
		
		UtilitySyn.SynData(db, "ERP_NHANVIEN", "ERP_NHANVIEN", "PK_SEQ", this.Id, "2", true);
		
		return true;
	}
	
	public boolean save() 
	{	
		if (this.tkduId != null && this.tkduId.trim().length() == 0)
			this.tkduId = null;
		if (this.tkkqId != null && this.tkkqId.trim().length() == 0)
			this.tkkqId = null;
		
		if(this.Ma == "" || this.Ten == ""|| this.tkktId == "" ||this.pbId == "")
		{  
			this.msg = "Bạn phải nhập thông tin đầy đủ";
			return false;
		}
		//CHECK TRÙNG MÃ
		
		try 
		{
			String queryId ="SELECT * FROM ERP_NHANVIEN WHERE MA=N'"+this.Ma+"' ";
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
		
		String dvthId = null;
		String nppId = "1";
		
		if (this.pbId != null && this.pbId.trim().length() > 0)
		{
			String[] arr = pbId.split(",");
			
			if (arr.length == 2)
			{
				if (arr[0].trim().equals("0"))
					nppId = arr[1];
				else
					dvthId = arr[1];
			}
		}
		
		String sql = "";
		String sqlSYN = "";
		
		if( this.Id.length() > 0 )
		{	
			sql = "UPDATE ERP_NHANVIEN SET MA = N'" + this.Ma + "', TEN = N'"+ this.Ten +"', email = '"+ this.Email +"', " +
				  "DIENTHOAI = '"+ this.Dienthoai+"', trangthai = '"+ this.trangthai+"', taikhoan_fk = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '"+this.tkktId+"' and npp_fk='"+nppId+"') , " +
				  "SOTAIKHOAN = '" + this.sotaikhoan + "', NGANHANG_FK = " + this.nhId + ", CHINHANH_FK = " + this.cnId + ", " +
				  "DVTH_FK = " + dvthId + ", npp_FK = " + nppId + ", ngaysua ='"+ Utility.getCurrentDate() +"',nguoisua ='"+ this.userId+"'\n" + 
				  ", cmnd ='"+this.cmnd+"', noicap =N'" +this.noicap+"', ngaycap = '"+this.ngaycap+"'\n" + 
				  ", diachi =N'"+this.diachi+"', TAIKHOANDU_FK = "+this.tkduId+", TAIKHOANKQ_FK = "+this.tkkqId +
				  " where pk_seq ='"+ this.Id +"'";
			
			System.out.println("[Nhanvien] sql = " +this.tkktId + sql);
			if(!db.update(sql))
			{	
				this.msg = "Co loi trong qua trinh update (" + sql + ")";
				return false;
			}
			
			sqlSYN = "UPDATE LINK_DMS.DataCenter.dbo.ERP_NHANVIEN SET MA = N'" + this.Ma + "', TEN = N'"+ this.Ten +"', email = '"+ this.Email +"', " +
					 "DIENTHOAI = '"+ this.Dienthoai+"', trangthai = '"+ this.trangthai+"', taikhoan_fk = (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '"+this.tkktId+"' and npp_fk='"+nppId+"') , " +
					 "SOTAIKHOAN = '" + this.sotaikhoan + "', NGANHANG_FK = " + this.nhId + ", CHINHANH_FK = " + this.cnId + ", " +
					 "DVTH_FK = " + dvthId + ", npp_FK = " + nppId + ", ngaysua ='"+ Utility.getCurrentDate() +"',nguoisua ='100002'\n" + 
					 ", cmnd ='"+this.cmnd+"', noicap =N'" +this.noicap+"', ngaycap = '"+this.ngaycap+"'\n" + 
					 ", diachi =N'"+this.diachi+"', TAIKHOANDU_FK = "+this.tkduId+", TAIKHOANKQ_FK = "+this.tkkqId +
					 " where pk_seq ='"+ this.Id +"'";
		}
		else
		{   
			sql =	"INSERT INTO ERP_NHANVIEN(MA, TEN, EMAIL, DIENTHOAI"
					+ ", TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO"
					+ ", NGUOISUA, TAIKHOAN_FK, CONGTY_FK, SOTAIKHOAN"
					+ ", NGANHANG_FK, CHINHANH_FK, DVTH_FK, npp_fk, cmnd"
					+ ", ngaycap, noicap, diachi, TAIKHOANDU_FK"
					+ ", TAIKHOANKQ_FK) "
					+ "VALUES("+
					"N'" + this.Ma + "', N'"+ this.Ten +"','"+ this.Email +"', '"+this.Dienthoai+"'"
					+ ",'"+this.trangthai+"','"+ Utility.getCurrentDate() +"', '"+ Utility.getCurrentDate() +"','"+ this.userId+"'"
					+ ",'"+ this.userId+"', (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '"+this.tkktId+"' and npp_fk='"+nppId+"') , " + this.ctyId + ", '" + this.sotaikhoan + "'"
					+ ", "  + this.nhId + ", " + this.cnId + ", " + dvthId + ", " + nppId + ", '"+this.ngaycap+"'\n"
					+ ", '"+this.cmnd+"', '"+this.noicap+"', N'"+this.diachi+"', "+this.tkduId+"\n"
					+ ", "+this.tkkqId+")";
			
			System.out.println("[Nhanvien.save] sql = " + sql);
			if(!db.update(sql))
			{	
				this.msg =sql;
				return false;
			}
			
			//Lấy ID
			sql = "select scope_identity() as nvId ";
			String nvId = "";
			ResultSet rs = db.get(sql);
			try
			{
				rs.next();
				nvId = rs.getString("nvId");
				rs.close();
			}
			catch(Exception ex)
			{
				ex.printStackTrace();
			}
			
			sqlSYN =	"INSERT LINK_DMS.DataCenter.dbo.ERP_NHANVIEN(PK_SEQ, MA, TEN, EMAIL, DIENTHOAI"
					+ ", TRANGTHAI, NGAYTAO, NGAYSUA, NGUOITAO"
					+ ", NGUOISUA, TAIKHOAN_FK, CONGTY_FK, SOTAIKHOAN"
					+ ", NGANHANG_FK, CHINHANH_FK, DVTH_FK, npp_fk, cmnd"
					+ ", ngaycap, noicap, diachi, TAIKHOANDU_FK"
					+ ", TAIKHOANKQ_FK) "
					+ "VALUES( '" + nvId + "', N'" + this.Ma + "', N'"+ this.Ten +"','"+ this.Email +"', '"+this.Dienthoai+"'"
					+ ",'"+this.trangthai+"','"+ Utility.getCurrentDate() +"', '"+ Utility.getCurrentDate() +"','100002'"
					+ ",'100002', (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '"+this.tkktId+"' and npp_fk='"+nppId+"'), " + this.ctyId + ", '" + this.sotaikhoan + "'"
					+ ", "  + this.nhId + ", " + this.cnId + ", " + dvthId + ", " + nppId + ", '"+this.ngaycap+"'\n"
					+ ", '"+this.cmnd+"', '"+this.noicap+"', N'"+this.diachi+"', "+this.tkduId+"\n"
					+ ", "+this.tkkqId+")";
			
		}	
		
		//ĐỒNG BỘ QUA DMS THẬT, SỦA LẠI GỌI SERVICE
		System.out.println(":: SYN DATA: " + sqlSYN);
		db.update(sqlSYN);
		
		return true;
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
			er.printStackTrace();
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
	
	public void setTkduId(String tkduId) {
		this.tkduId = tkduId;
	}
	
	public String getTkduId() {
		return this.tkduId;
	}
	
	public void setTkkqId(String tkkqId) {
		this.tkkqId = tkkqId;
	}
	
	public String getTkkqId() {
		return this.tkkqId;
	}
}