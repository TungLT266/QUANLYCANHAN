package geso.traphaco.erp.beans.congty.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.congty.IErpCongTy;
import geso.traphaco.erp.beans.congty.IErpNganHang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ErpCongTy implements IErpCongTy
{
	String ID, MA, TEN,TENTIENGANH, DIACHI,DIACHITIENGANH, MASOTHUE, NGANHNGHEKINHDOANH, DIENTHOAI, FAX, GIAMDOC, KETOANTRUONG, USERID, TRANGTHAI,
	SoTaiKhoan, NganHang_FK, congtyId, ngaybatdautc, ngayketthuctc;
	IErpNganHang nganhang;
	List<IErpNganHang> nganhangList;
	dbutils db;
	String msg;
	ResultSet ctyRs;
	String tkctyId;
	String isTongCty;
	String vonDieuLe;
	public String getVonDieuLe() {
		if(vonDieuLe==null){
			vonDieuLe = "";
		}
		return vonDieuLe;
	}

	public void setVonDieuLe(String vonDieuLe) {
		this.vonDieuLe = vonDieuLe;
	}

	public String getGiayPhepKinhDoanh() {
		if(giayPhepKinhDoanh==null){
			giayPhepKinhDoanh = "";
		}
		return giayPhepKinhDoanh;
	}

	public void setGiayPhepKinhDoanh(String giayPhepKinhDoanh) {
		this.giayPhepKinhDoanh = giayPhepKinhDoanh;
	}

	String giayPhepKinhDoanh;
	
	public String getID()
	{
		return this.ID;
	}
	
	public void setID(String pk_seo)
	{
		this.ID = pk_seo;
	}
	
	public String getTkCtyId()
	{
		return this.tkctyId;
	}
	
	public void setTkCtyId(String id)
	{
		this.tkctyId = id;
	}

	public ErpCongTy()
	{
		db = new dbutils();
		this.ID = "";
		this.DIACHI = "";
		this.DIACHITIENGANH="";
		this.DIENTHOAI = "";
		this.FAX = "";
		this.GIAMDOC = "";
		this.KETOANTRUONG = "";
		this.MA = "";
		this.MASOTHUE = "";
		this.NGANHNGHEKINHDOANH = "";
		this.USERID = "";
		this.TEN = "";
		this.TENTIENGANH="";
		this.SoTaiKhoan = "";
		this.NganHang_FK = "";
		this.TRANGTHAI = "1";
		this.tkctyId = "";
		this.msg = "";
		this.isTongCty = "0";
		this.vonDieuLe = "";
		this.giayPhepKinhDoanh = "";
		this.ngaybatdautc = "";
		this.ngayketthuctc = "";
		this.nganhangList = new ArrayList<IErpNganHang>();
	}
	
	public ErpCongTy(String id)
	{
		this.db = new dbutils();
		this.ID = id;
		this.DIACHI = "";
		this.DIACHITIENGANH="";
		this.DIENTHOAI = "";
		this.FAX = "";
		this.GIAMDOC = "";
		this.KETOANTRUONG = "";
		this.MA = "";
		this.MASOTHUE = "";
		this.NGANHNGHEKINHDOANH = "";
		this.USERID = "";
		this.TEN = "";
		this.TENTIENGANH="";
		this.SoTaiKhoan = "";
		this.NganHang_FK = "";
		this.TRANGTHAI = "1";
		this.msg = "";
		this.tkctyId = "";
		this.isTongCty = "0";
		this.vonDieuLe = "";
		this.giayPhepKinhDoanh = "";
		this.ngaybatdautc = "";
		this.ngayketthuctc = "";
		this.nganhangList = new ArrayList<IErpNganHang>();
	}
	
	public boolean CheckUnique(String command)
	{
		String query = "Select count(*) as count From Erp_CongTy Where MA='" + this.MA + "'";
		if (command.equals(""))
			command = query;
		else
			query += command;
		int count = 0;
		System.out.print("CheckUnique" + query);
		ResultSet rs = this.db.get(query);
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
			}
			catch (SQLException e)
			{
				return false;
			}
		return true;
	}
	
	public String getMA()
	{
		return this.MA;
	}
	
	public void setMA(String ma)
	{
		this.MA = ma;
	}
	
	public String getTEN()
	{
		return this.TEN;
	}
	
	public void setTEN(String ten)
	{
		this.TEN = ten;
	}
	
	public String getTENTIENGANH()
	{
		return this.TENTIENGANH;
	}
	
	public void setTENTIENGANH(String tentienganh)
	{
		this.TENTIENGANH = tentienganh ;
	}
		
	
	public String getDIACHI()
	{
		return this.DIACHI;
	}
	
	public void setDIACHI(String diachi)
	{
		this.DIACHI = diachi;
	}
	
	public String getDIACHITIENGANH()
	{
		return this.DIACHITIENGANH;
	}
	
	public void setDIACHITIENGANH(String diachitienganh)
	{
		this.DIACHITIENGANH= diachitienganh;
	}
	
	public String getMASOTHUE()
	{
		return this.MASOTHUE;
	}
	
	public void setMASOTHUE(String masothue)
	{
		this.MASOTHUE = masothue;
	}
	
	public String getNGANHNGHEKINHDOANH()
	{
		return this.NGANHNGHEKINHDOANH;
	}
	
	public void setNGANHNGHEKINHDOANH(String nganhnghekinhdoanh)
	{
		this.NGANHNGHEKINHDOANH = nganhnghekinhdoanh;
	}
	
	public String getDIENTHOAI()
	{
		return this.DIENTHOAI;
	}
	
	public void setDIENTHOAI(String dienthoai)
	{
		this.DIENTHOAI = dienthoai;
	}
	
	public String getFAX()
	{
		return this.FAX;
	}
	
	public void setFAX(String fax)
	{
		this.FAX = fax;
	}
	
	public String getGIAMDOC()
	{
		return this.GIAMDOC;
	}
	
	public void setGIAMDOC(String giamdoc)
	{
		this.GIAMDOC = giamdoc;
	}
	
	public String getKETOANTRUONG()
	{
		return this.KETOANTRUONG;
	}
	
	public void setKETOANTRUONG(String ketoantruong)
	{
		this.KETOANTRUONG = ketoantruong;
	}
	
	public String getTRANGTHAI()
	{
		return this.TRANGTHAI;
	}
	
	public void setTRANGTHAI(String trangthai)
	{
		this.TRANGTHAI = trangthai;
	}
	
	public ResultSet getCtyRs()
	{
		String query = "SELECT PK_SEQ AS CTYID, TEN AS CTY FROM ERP_CONGTY WHERE 1 = 1 ";
		if(this.ID.length() > 0) query = query + " AND PK_SEQ <> '" + this.ID + "' ";
		
		System.out.println(query);
		
		return this.db.get(query);
	}
	
	public void setCtyRs(ResultSet ctyRs)
	{

		this.ctyRs = ctyRs;
	}

	public void init()
	{
		String query =
		"select isnull(Ma,'')as Ma,isnull(DiaChi,'')as DiaChi,isnull(DienThoai,'') as DienThoai,isnull(Ten,'')as Ten, " +
		" isnull(Tentienganh,'')as Tentienganh , isnull(Diachitienganh,'') as Diachitienganh, " +
		" isnull(Fax,'')as Fax,isnull(GiamDoc,'')as GiamDoc,isnull(KeToanTruong,'')as KeToanTruong,isnull(MaSoThue,'')as MaSoThue," +
		" isnull(NganhNgheKinhDoanh,'')as NganhNgheKinhDoanh,isnull(TrangThai,'')as TrangThai,isnull(NganHang_FK,null)as NganHang_FK," +
		" isnull(SoTaiKhoan,'')as SoTaiKhoan, isnull(TKCTYID, 0) as TKCTYID , isnull(isTongCongTy, '0') as isTongCongTy " +
		" ,VONDIEULE,GIAYPHEPKINHDOANH, isnull(NGAYBDTAICHINH, '') NGAYBDTAICHINH, ISNULL(NGAYKTTAICHINH, '') NGAYKTTAICHINH "+
		" from ERP_CONGTY where PK_SEQ = '" + this.ID + "'";
		System.out.println("CongTy Init: " + query);
		ResultSet rs = db.get(query);
		if (rs != null)
		{
			System.out.println("ss");
			try
			{
				while (rs.next())
				{
					this.DIACHI = rs.getString("diachi");
					this.DIACHITIENGANH = rs.getString("diachitienganh");
					this.DIENTHOAI = rs.getString("dienthoai");
					this.TEN = rs.getString("ten");
					this.TENTIENGANH= rs.getString("tentienganh");
					this.FAX = rs.getString("fax");
					this.GIAMDOC = rs.getString("giamdoc");
					this.KETOANTRUONG = rs.getString("ketoantruong");
					this.MA = rs.getString("ma");
					this.MASOTHUE = rs.getString("masothue");
					this.NGANHNGHEKINHDOANH = rs.getString("NGANHNGHEKINHDOANH");
					this.TRANGTHAI = rs.getString("trangthai");
					this.NganHang_FK = rs.getString("NganHang_FK");
					this.SoTaiKhoan = rs.getString("SoTaiKhoan");
					this.tkctyId = rs.getString("TKCTYID");					
					this.isTongCty = rs.getString("isTongCongTy");
					this.vonDieuLe = rs.getString("VONDIEULE");
					this.giayPhepKinhDoanh = rs.getString("GIAYPHEPKINHDOANH");
					this.ngaybatdautc = rs.getString("NGAYBDTAICHINH");
					this.ngayketthuctc = rs.getString("NGAYKTTAICHINH");
				}
				rs.close();
			}
			catch (SQLException e)
			{
				e.printStackTrace();
			}
		}
	}
	
	public boolean CreateCongty()
	{
		try
		{
			this.db.getConnection().setAutoCommit(false);
			if (!CheckUnique(""))
			{
				this.msg="Mã công ty này đã có ";
				return false;
			}
			String ngaytao = getDateTime();
			String command =
			"INSERT INTO ERP_CONGTY([MA],[TEN],[TENTIENGANH],[DIACHI],[DIACHITIENGANH],[MASOTHUE],[NGANHNGHEKINHDOANH],[DIENTHOAI],[FAX],[GIAMDOC],[KETOANTRUONG],[NGUOITAO],"
			+ "[NGUOISUA],[NGAYTAO],[NGAYSUA],[TRANGTHAI],[SoTaiKhoan],[NganHang_FK], [TKCTYID] , isTongCongTy , VONDIEULE, GIAYPHEPKINHDOANH, NGAYBDTAICHINH, NGAYKTTAICHINH) ";
			command +=
			" values(N'" + this.MA + "', N'" + this.TEN + "',N'" + this.TENTIENGANH + "',N'" + this.DIACHI + "',N'" + this.DIACHITIENGANH + "','" + this.MASOTHUE + "', N'" +
			this.NGANHNGHEKINHDOANH + "', '" + this.DIENTHOAI + "', '" + this.FAX + "', N'" + this.GIAMDOC + "', N'" +
			this.KETOANTRUONG + "', N'" + this.USERID + "', '" + this.USERID + "', '" + ngaytao + "', '" + ngaytao +
			"', '" + this.TRANGTHAI + "','" + this.SoTaiKhoan + "'," + this.NganHang_FK + ", " + this.tkctyId + ", '" + this.isTongCty + "' ,'" +this.vonDieuLe+"','"+this.giayPhepKinhDoanh+"', '"+this.ngaybatdautc+"', '"+this.ngayketthuctc+"')";
			System.out.println("SQL ...."+command);
			if (!this.db.update(command))
			{
				this.msg = "Không thể tạo mới công ty !";
				this.db.update("rollback");
				return false;
			}
			
			String query = "SELECT SCOPE_IDENTITY() AS ID";
			ResultSet rs = this.db.get(query);
			if (rs != null)
			{
				if (rs.next())
					this.ID = rs.getString("ID");
				rs.close();
			}
			if( this.isTongCty.equals("0") && this.tkctyId.length() > 0)
			{
				if(!this.CreateCOA())
				{
					this.msg = "Vui lòng chọn hệ thống tài khoản thừa hưởng";
					return false;
				}
				
				//Loai SP khong phan biet cong ty ( cac chuc nang phai dua vao loai SP de ve giao dien tuong ung )
				//CreateLoaiSP();
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			return true;
		}
		catch (Exception er)
		{
			this.db.update("rollback");
			this.msg = "khong the cap nhat cong ty nay,loi tai dong lenh sau :" + er.toString();
			return false;
		}
	}
	
	private boolean CreateCOA(){
		String query = "INSERT INTO ERP_TAIKHOANKT (CONGTY_FK, SOHIEUTAIKHOAN, TENTAIKHOAN, LOAITAIKHOAN_FK, TAIKHOANCOCHITIET, " +
						"NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG, " +
						"DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG) " +
						"SELECT '" + this.ID + "', SOHIEUTAIKHOAN, TENTAIKHOAN, LOAITAIKHOAN_FK, TAIKHOANCOCHITIET, NGUOITAO, " +
						"NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, COTTCHIPHI, DUNGCHOKHO, DUNGCHONGANHANG, DUNGCHONCC, DUNGCHOTAISAN, DUNGCHOKHACHHANG " +
						"FROM ERP_TAIKHOANKT WHERE CONGTY_FK = '" + this.tkctyId + "' ";
		System.out.println(query);
		return this.db.update(query);
	}
	
	private void CreateLoaiSP(){
		String query = "SELECT COUNT(*) AS NUM FROM ERP_LOAISANPHAM WHERE CONGTY_FK = '" + this.ID + "' ";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try{
				rs.next();
				if(rs.getString("NUM").equals("0")){
					query = "INSERT INTO ERP_LOAISANPHAM(CONGTY_FK, MA, TEN, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI) " +
							"SELECT '" + this.ID + "', MA, TEN, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI " +
							"FROM ERP_LOAISANPHAM WHERE CONGTY_FK =  '" + this.tkctyId + "'  ";
	
					System.out.println(query);
					this.db.update(query);				
				}
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
	}
	
	public boolean UpdateCongty()
	{
		try
		{
			if (!CheckUnique("  And PK_SEQ!='" + this.ID + "'"))
			{
				return false;
			}
			this.db.getConnection().setAutoCommit(false);
			String ngaytao = getDateTime();
			String command =
			"UPDATE [ERP_CONGTY] SET [MA] = '" + this.MA + "',[TEN] = N'" + this.TEN + "',[TENTIENGANH] = N'" + this.TENTIENGANH +
			"',[DIACHI] = N'" + this.DIACHI +"',[DIACHITIENGANH] = N'" + this.DIACHITIENGANH +
			"',[MASOTHUE] = '" + this.MASOTHUE + "',[NGANHNGHEKINHDOANH] = N'" + this.NGANHNGHEKINHDOANH +
			"',[DIENTHOAI] = '" + this.DIENTHOAI + "', [FAX] = '" + this.FAX + "',GIAMDOC=N'" + this.GIAMDOC +
			"',KETOANTRUONG=N'" + this.KETOANTRUONG + "', NGUOISUA='" + this.USERID + "', NGAYSUA='" + ngaytao +
			"', TRANGTHAI='" + this.TRANGTHAI + "', NganHang_FK=" + this.NganHang_FK + ",SoTaiKhoan='" +
			this.SoTaiKhoan + "', isTongCongTy = '" + this.isTongCty + "', [TKCTYID] = null,VONDIEULE = '" +this.vonDieuLe+
			"', GIAYPHEPKINHDOANH = '" +this.giayPhepKinhDoanh+"', NGAYBDTAICHINH = '"+this.ngaybatdautc+"', NGAYKTTAICHINH = '"+this.ngayketthuctc+"' "+"Where PK_SEQ='" + this.ID + "'";
			if (!this.db.update(command))
			{
				this.msg = "Không thể tạo mới công ty do lỗi sau " + command;
				this.db.update("rollback");
				return false;
			}
			
			if(this.isTongCty.equals("0"))
			{
				command = "SELECT ISNULL(TKCTYID, 0) AS TKCTYID FROM ERP_CONGTY WHERE PK_SEQ = '" + this.ID +"' ";
				ResultSet rs = this.db.get(command);
				if (rs != null)
				{
					rs.next();
					if(!rs.getString("TKCTYID").equals(this.tkctyId))
					{
						if(checkCOA())
						{
							this.DeleteCOA();
		
							if(!this.CreateCOA())
							{
								this.msg = "Vui lòng chọn hệ thống tài khoản thừa hưởng";
								return false;
							}
							
							//CreateLoaiSP();
							
							this.db.update("UPDATE ERP_CONGTY SET TKCTYID = '" + this.tkctyId + "' WHERE PK_SEQ = '" + this.ID + "' ");
						}
						else
						{
							this.msg = "Không thể thay đổi hệ thống tài khoản kế toán";
							return false;
						}
					}
					else if(this.tkctyId.length() == 0)
					{
						this.msg = "Vui lòng chọn hệ thống tài khoản kế thừa";
						return false;
					}
					rs.close();
				}
			}
			
			
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);

			return true;
		}
		catch (Exception er)
		{
			this.db.update("rollback");
			this.msg = "khong the cap nhat cong ty nay,loi tai dong lenh sau :" + er.toString();
			return false;
		}
	}
	
	private boolean checkCOA(){
		String query = 	"SELECT ISNULL(SUM(GIATRICOVND) + SUM(GIATRINOVND) + SUM(GIATRINGUYENTE),0) AS SUM " +
						"FROM ERP_TAIKHOAN_NOCO " +
						"WHERE TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK = '" + this.ID + "' )";
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		try{
			float n = 0;
			if (rs != null)
			{
				if (rs.next())
					n = Float.parseFloat(rs.getString("SUM"));
				rs.close();
			}			
			if( n == 0){
				return true;
			}else{
				return false;
			}
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	private void DeleteCOA(){
		String query = "DELETE FROM ERP_TAIKHOAN_NOCO WHERE TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE CONGTY_FK = '" + this.ID + "') ";
		System.out.println(query);
		this.db.update(query);
		
		query = "DELETE FROM ERP_TAIKHOANKT WHERE CONGTY_FK = '" + this.ID + "' ";
		System.out.println(query);
		this.db.update(query);
	}
	
	public void closeDB()
	{
		try{
			if (this.nganhangList != null)
				this.nganhangList.clear();
			if (this.db != null)
				this.db.shutDown();
			if(ctyRs!=null){
				ctyRs.close();
			}
		}catch(Exception er){
			er.printStackTrace();
		}
	}
	
	public String getMessage()
	{
		return this.msg;
	}
	
	public void setMessage(String msg)
	{
		this.msg = msg;
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public String getUserId()
	{
		return this.USERID;
	}
	
	public void setUserId(String userId)
	{
		this.USERID = userId;
	}
	
	public String getNganHang_FK()
	{
		return this.NganHang_FK;
	}
	
	public void setNganHang_FK(String nganhangid)
	{
		this.NganHang_FK = nganhangid;
	}
	
	public String getSoTaiKhoan()
	{
		return this.SoTaiKhoan;
	}
	
	public void setSoTaiKhoan(String sotaikhoan)
	{
		this.SoTaiKhoan = sotaikhoan;
	}
	
	public IErpNganHang getNganHang()
	{
		if (NganHang_FK != null)
			nganhang = new ErpNganHang(this.NganHang_FK);
		else
			nganhang = new ErpNganHang();
		return this.nganhang;
	}
	
	public void setNganHang(IErpNganHang nganhang)
	{
		this.nganhang = nganhang;
	}
	
	public List<IErpNganHang> getNganHangList()
	{
		String query = "Select PK_SEQ,Ma,Ten From Erp_NganHang Where TrangThai=1";
		IErpNganHang o = null;
		ResultSet rsNganHang = this.db.get(query);
		if (rsNganHang != null)
		{
			try
			{
				while (rsNganHang.next())
				{
					o = new ErpNganHang();
					o.setId(rsNganHang.getString("PK_SEQ"));
					o.setMa(rsNganHang.getString("Ma"));
					o.setTen(rsNganHang.getString("Ten"));
					nganhangList.add(o);
				}
				rsNganHang.close();
			}
			catch (SQLException e)
			{
				System.out.println(e);
			}
		}
		return this.nganhangList;
	}
	
	public void setNganHangList(List<IErpNganHang> nganhanglist)
	{
		this.nganhangList = nganhanglist;
	}
	
	public boolean Delete()
	{

		String query = "SELECT COUNT(PK_SEQ) AS NUM FROM ERP_TAIKHOANKT WHERE CONGTY_FK = '" + this.ID + "'";
		ResultSet rs = this.db.get(query);
		if (rs != null)
		{
			try{
				rs.next();
				if(rs.getString("NUM").equals("0")){
					this.db.update("DELETE FROM ERP_CONGTY WHERE PK_SEQ='" + this.ID + "'");
				}else{
					this.msg = "Bạn không thể xóa vì công ty đã chứa dữ liệu!";
				}
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}		 
			
		return true;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getIsTongCty() {
		
		return this.isTongCty;
	}

	
	public void setIsTongCty(String tongcty) {
		
		this.isTongCty = tongcty;
	}

	
	public String getngaybatdautc() {
		
		return this.ngaybatdautc;
	}

	
	public void setngaybatdautc(String ngaybatdautc) {
		
		this.ngaybatdautc = ngaybatdautc;
	}

	
	public String getngayketthuctc() {
		
		return this.ngayketthuctc;
	}

	
	public void setngayketthuctc(String ngayketthuctc) {
		this.ngayketthuctc = ngayketthuctc;
		
	}

	
	
}
