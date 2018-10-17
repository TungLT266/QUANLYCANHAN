package geso.traphaco.center.beans.chitieunhanvienetc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.center.beans.chitieunhanvienetc.IChiTieuNhanvienETC;
import geso.traphaco.distributor.db.sql.dbutils;

public class ChiTieuNhanvienETC implements IChiTieuNhanvienETC 
{
	String UserID;
	String Id;
	String quy;
	String Nam;
	String thang;
	String diengiai;
	String loai;
	String isDisplay;

	String msg;
	String TrangThai;
	
	String kbhId;
	ResultSet kbhRs;
	
	ResultSet chitieuRs;
	ResultSet chitieuListRs;
	
	String[] codeTDV;
	String[] tenTDV;
	String[] hangchienluoc;
	String[] hangdactri;
	String[] dskhoan;
	String[] kpichienluoc;
	String[] kpidactri;
	
	String nppId;
	String nppTen;
	String sitecode;
	
	//REPORT
	String loainhanvien;
	
	String nhanvienId;
	ResultSet nahnvienRs;
	
	dbutils db;

	public ChiTieuNhanvienETC()
	{
		this.Id = "";
		this.thang = "";
		this.Nam = "2015";
		this.diengiai = "";
		this.loai = "0";
		this.isDisplay = "0";
		this.kbhId = "";
		
		this.msg = "";
		this.loainhanvien = "0";
		this.nhanvienId = "";
		db = new dbutils();
	}
	
	public ChiTieuNhanvienETC(String id)
	{
		this.Id = id;
		this.thang = "";
		this.Nam = "2015";
		this.diengiai = "";
		this.loai = "0";
		this.isDisplay = "0";
		this.kbhId = "";
		
		this.msg = "";
		this.loainhanvien = "0";
		this.nhanvienId = "";
		db = new dbutils();
	}

	
	public void setID(String id) {
		
		this.Id=id;	
	}

	
	public String getID() {
		
		return Id;
	}

	
	public void setQuy(String thang) {
		
		this.quy=thang;
	}

	
	public String getQuy() {
		
		return this.quy;
	}

	
	public void setNam(String nam) {
		
		this.Nam=nam;
	}

	
	public String getNam() {
		
		return this.Nam;
	}

	public void setUserId(String userid) {
		
		this.UserID = userid;
	}


	public String getUserId() {
		
		return this.UserID;
	}
	
	public void setmsg(String msg) {
		
		this.msg = msg;
	}

	
	public String getmsg() {
		
		return this.msg;
	}


	public boolean Create( ) 
	{
		if( this.thang.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn tháng.";
			return false;
		}
		
		if( this.Nam.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn năm.";
			return false;
		}
		
		try
		{
			String sql = " select count(pk_seq) as sodong from CHITIEUNHANVIEN " + 
						 " where thang = " + this.thang + " and nam = " + this.Nam + " and npp_fk = " + this.nppId + " and loai = '" + this.loai + "'  and trangthai != 2 ";
			ResultSet rs_check = db.get(sql);
			int sodong = 0;
			if(rs_check!=null)
			{
				if(rs_check.next())
				{
					sodong = rs_check.getInt("sodong");
				}
				rs_check.close();
			}	
			
			if( sodong > 0 )
			{
				this.msg = "Tháng " + this.thang + ", năm " + this.Nam + " đã có khai báo chỉ tiêu.";
				return false;
			}

			db.getConnection().setAutoCommit(false);
			
			sql = "insert into ChiTieuNhanVien( npp_fk, loai, diengiai, thang, NAM, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA ) " +
				  "values (" + this.nppId + ", '" + this.loai + "', N'" + this.diengiai + "', " + this.thang + ", " + this.Nam + ", '" + this.getDateTime() + "','" + this.UserID + "', '" + this.getDateTime() + "','" + this.UserID + "' )";
			System.out.println("::: CHEN CHI TIEU: " + sql );
			if(!db.update(sql))
			{
				this.msg = "Loi :" + sql;		
				db.getConnection().rollback();
				return false;
			}		

			String query = "select SCOPE_IDENTITY() as dhId";
			ResultSet rsDh = db.get(query);	
			rsDh.next();
			this.Id = rsDh.getString("dhId");
			
			if( this.codeTDV != null )
			{
				String sqlInsert = "";
				for( int i = 0; i < this.codeTDV.length; i++ )
				{
					if( this.loai.equals("0") )
					{
						/*sqlInsert += "select pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, '" + this.kpidactri[i].replaceAll(",", "") + "' as kpidactri " + 
									 " from DAIDIENKINHDOANH where maFAST = '" + this.codeTDV[i] + "' ";*/
						sqlInsert += "select top(1) pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, '" + this.kpidactri[i].replaceAll(",", "") + "' as kpidactri " + 
								 	 " from DAIDIENKINHDOANH where MANHANSU = '" + this.codeTDV[i] + "' ";
					}
					else
					{
						/*sqlInsert += "select pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, NULL as kpidactri " + 
								 	 " from DAIDIENKINHDOANH where maFAST = '" + this.codeTDV[i] + "' ";*/
						sqlInsert += "select top(1) pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, NULL as kpidactri " + 
							 	 	 " from DAIDIENKINHDOANH where MANHANSU = '" + this.codeTDV[i] + "' ";
					}
					
					if( i < this.codeTDV.length - 1 )
						sqlInsert += " union ";
				}
				
				if( sqlInsert.trim().length() > 0 )
				{
					sqlInsert = "insert ChiTieuNhanVien_ChiTiet( chitieu_fk, ddkd_fk, hangdactri, hangchienluoc, tongdskhoan, kpichienluoc, kpidactri ) " + 
								"select '" + this.Id + "', DATA.* from ( " + sqlInsert + " ) DATA ";
					System.out.println("::: CHEN CHI TIET: " + sqlInsert );
					if(!db.update(sqlInsert))
					{
						this.msg = "Loi :" + sqlInsert;		
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{	
			er.printStackTrace();
			db.update("rollback");
			this.msg = "Lỗi tạo mới: " + er.getMessage();
			return false;			
		}
		
		return true;
	}

	public boolean Update( ) 
	{
		if( this.thang.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn tháng.";
			return false;
		}
		
		if( this.Nam.trim().length() <= 0 )
		{
			this.msg = "Vui lòng chọn năm.";
			return false;
		}
		
		try
		{
			String sql = " select count(pk_seq) as sodong from CHITIEUNHANVIEN " + 
						 " where thang = " + this.thang + " and nam = " + this.Nam + " and npp_fk = " + this.nppId + " and loai = '" + this.loai + "' and trangthai != 2 and pk_seq != '" + this.Id + "' ";
			ResultSet rs_check = db.get(sql);
			int sodong = 0;
			if(rs_check!=null)
			{
				if(rs_check.next())
				{
					sodong = rs_check.getInt("sodong");
				}
				rs_check.close();
			}	
			
			if( sodong > 0 )
			{
				this.msg = "Tháng " + this.thang + ", năm " + this.Nam + " đã có khai báo chỉ tiêu.";
				return false;
			}

			db.getConnection().setAutoCommit(false);
			
			sql = "update ChiTieuNhanVien set loai = '" + this.loai + "', diengiai = N'" + this.diengiai + "', thang = '" + this.thang + "', NAM = '" + this.Nam + "', NGAYSUA = '" + this.getDateTime() + "', NGUOISUA = '" + this.UserID + "' " + 
				  " where pk_seq = '" + this.Id + "' ";
			System.out.println("::: CAP NHAT CHI TIEU: " + sql );
			if(!db.update(sql))
			{
				this.msg = "Loi :" + sql;		
				db.getConnection().rollback();
				return false;
			}		

			sql = "delete ChiTieuNhanVien_ChiTiet where chitieu_fk = '" + this.Id + "' ";
			if(!db.update(sql))
			{
				this.msg = "Loi :" + sql;		
				db.getConnection().rollback();
				return false;
			}
			
			if( this.codeTDV != null )
			{
				String sqlInsert = "";
				for( int i = 0; i < this.codeTDV.length; i++ )
				{
					if( this.loai.equals("0") )
					{
						/*sqlInsert += "select pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, '" + this.kpidactri[i].replaceAll(",", "") + "' as kpidactri " + 
									 " from DAIDIENKINHDOANH where maFAST = '" + this.codeTDV[i] + "' ";*/
						sqlInsert += "select top(1) pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, '" + this.kpidactri[i].replaceAll(",", "") + "' as kpidactri " + 
								 	 " from DAIDIENKINHDOANH where MANHANSU = '" + this.codeTDV[i] + "' ";
					}
					else
					{
						/*sqlInsert += "select pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, NULL as kpidactri " + 
								 	 " from DAIDIENKINHDOANH where maFAST = '" + this.codeTDV[i] + "' ";*/
						sqlInsert += "select top(1) pk_seq, '" + this.hangdactri[i].replaceAll(",", "") + "' as hangdactri, '" + this.hangchienluoc[i].replaceAll(",", "") + "' as hangchienluoc, '" + this.dskhoan[i].replaceAll(",", "") + "' as tongdskhoan, '" + this.kpichienluoc[i].replaceAll(",", "") + "' as kpichienluoc, NULL as kpidactri " + 
							 	 	 " from DAIDIENKINHDOANH where MANHANSU = '" + this.codeTDV[i] + "' ";
					}
					
					if( i < this.codeTDV.length - 1 )
						sqlInsert += " union ";
				}
				
				if( sqlInsert.trim().length() > 0 )
				{
					sqlInsert = "insert ChiTieuNhanVien_ChiTiet( chitieu_fk, ddkd_fk, hangdactri, hangchienluoc, tongdskhoan, kpichienluoc, kpidactri ) " + 
								" select '" + this.Id + "', DATA.* from ( " + sqlInsert + " ) DATA ";
					System.out.println("::: CHEN CHI TIET: " + sqlInsert );
					if(!db.update(sqlInsert))
					{
						this.msg = "Loi :" + sqlInsert;		
						db.getConnection().rollback();
						return false;
					}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch(Exception er)
		{	
			er.printStackTrace();
			db.update("rollback");
			this.msg = "Lỗi tạo mới: " + er.getMessage();
			return false;			
		}
		
		return true;

	}
	
	public void setTrangThai(String trangthai) {
		
		this.TrangThai=trangthai;
	}
	
	public String getTrangThai() {
		
		return this.TrangThai;
	}
	
	public void closeDB() 
	{
		try
		{
			if(this.db!=null){
				db.shutDown();
			}
		}
		catch(Exception er)
		{

		}
	}


	public void createRs()
	{
		this.getNppInfo();
		
		this.kbhRs = db.get("select PK_SEQ, DIENGIAI from KENHBANHANG order by PK_SEQ asc");
	}

	
	public String getThang() {
		
		return this.thang;
	}

	
	public void setThang(String thang) {
		
		this.thang = thang;
	}

	
	public void setDienGiai(String diengiai) {
		
		this.diengiai = diengiai;
	}

	
	public String getDienGiai() {
		
		return this.diengiai;
	}

	
	public String getDisplay() {
		
		return this.isDisplay;
	}

	
	public void setDisplay(String loai) {
		
		this.isDisplay = loai;
	}

	
	public String getLoai() {
		
		return this.loai;
	}

	
	public void setLoai(String loai) {
		
		this.loai = loai;
	}

	
	public String getKbhId() {
		
		return this.kbhId;
	}

	
	public void setKbhId(String kbhId) {
		
		this.kbhId = kbhId;
	}

	
	public ResultSet getKbhRs() {
		
		return this.kbhRs;
	}

	
	public void setKbhRs(ResultSet kbhRs) {
		
		this.kbhRs = kbhRs;
	}

	
	public void setMessage(String msg) {
		
		this.msg = msg;
	}

	
	public String getMessage() {
		
		return this.msg;
	}

	
	public void init() {
		
		String query =  " select a.pk_seq, a.thang, a.NAM, a.trangthai, a.diengiai, a.loai "+
						 " from ChiTieuNhanVien a where a.pk_seq = '" + this.Id + "' ";

		System.out.println("::: INIT: " + query);
		ResultSet rs = db.get(query);
		if( rs != null )
		{
			try 
			{
				if( rs.next() )
				{
					this.thang = rs.getString("thang");
					this.Nam = rs.getString("nam");
					this.diengiai = rs.getString("diengiai");
					this.loai = rs.getString("loai");
				}
				rs.close();
				
				//INIT CHI TIEU
				this.initTieuChi();
			} 
			catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	
	private void initTieuChi() 
	{
		String query = "";
		
		query =  " select b.maFAST, isnull(b.MANHANSU, '') as MANHANSU, b.TEN, a.hangchienluoc, a.hangdactri, a.tongdskhoan, a.kpichienluoc, a.kpidactri "+
				 " from ChiTieuNhanVien_ChiTiet a inner join DAIDIENKINHDOANH b on a.ddkd_fk = b.PK_SEQ"+
				 " where a.chitieu_fk = '" + this.Id + "'" ;
		
		System.out.println("---INIT SP DB: " + query);
		ResultSet spRs = db.get(query);
		
		NumberFormat formater = new DecimalFormat("##,###,###");
		if(spRs != null)
		{
			try 
			{
				String codeTDV = "";
				String tenTDV = "";
				String hangchienluoc = "";
				String hangdactri = "";
				String tongdskhoan = "";
				String kpichienluoc = "";
				String kpidactri = "";
				
				while(spRs.next())
				{
					if( spRs.getString("MANHANSU").trim().length() > 0 )
						codeTDV += spRs.getString("MANHANSU") + "__";
					else
						codeTDV += " __";
					tenTDV += spRs.getString("TEN") + "__";
					
					hangchienluoc += formater.format(spRs.getDouble("hangchienluoc")) + "__";
					hangdactri += formater.format(spRs.getDouble("hangdactri")) + "__";
					tongdskhoan += formater.format(spRs.getDouble("tongdskhoan")) + "__";
					kpichienluoc += formater.format(spRs.getDouble("kpichienluoc")) + "__";
					kpidactri += formater.format(spRs.getDouble("kpidactri")) + "__";
				}
				spRs.close();

				if(codeTDV.trim().length() > 0)
				{
					codeTDV = codeTDV.substring(0, codeTDV.length() - 2);
					this.codeTDV = codeTDV.split("__");
					
					tenTDV = tenTDV.substring(0, tenTDV.length() - 2);
					this.tenTDV = tenTDV.split("__");
					
					hangchienluoc = hangchienluoc.substring(0, hangchienluoc.length() - 2);
					this.hangchienluoc = hangchienluoc.split("__");
					
					hangdactri = hangdactri.substring(0, hangdactri.length() - 2);
					this.hangdactri = hangdactri.split("__");
					
					tongdskhoan = tongdskhoan.substring(0, tongdskhoan.length() - 2);
					this.dskhoan = tongdskhoan.split("__");

					kpichienluoc = kpichienluoc.substring(0, kpichienluoc.length() - 2);
					this.kpichienluoc = kpichienluoc.split("__");
					
					kpidactri = kpidactri.substring(0, kpidactri.length() - 2);
					this.kpidactri = kpidactri.split("__");
				}
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				System.out.println("115.Exception: " + e.getMessage());
			}
		}
		
	}

	public String getNppId() {
		
		return this.nppId;
	}

	
	public void setNppId(String nppId) {
		
		this.nppId = nppId;
	}

	
	public ResultSet getChitieuRs() {
		
		return this.chitieuRs;
	}

	
	public ResultSet getChitieuListRs() {
		
		return this.chitieuListRs;
	}

	
	public void initCtLict(String search) 
	{
		this.getNppInfo();
		
		String query =  " select a.pk_seq, a.thang, a.NAM, a.trangthai, a.diengiai, b.TEN as nguoitao, a.NGAYTAO, c.TEN as nguoisua, c.NGAYSUA "+
						 " from ChiTieuNhanVien a inner join NHANVIEN b on a.NGUOITAO = b.PK_SEQ "+
						 " 	inner join NHANVIEN c on a.NGUOISUA = c.PK_SEQ"+
						 " where a.npp_fk = '" + this.nppId + "' "+
						 " order by a.NAM desc, a.thang desc";
		
		System.out.println("::: DANH SACH CHI TIEU: " + query);
		this.chitieuListRs = db.get(query);
	}

	
	public boolean Delete() {
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Update ChiTieuNhanVien set trangthai = '2' where pk_seq = '" + this.Id + "' ";
			if( !db.update(query) )
			{
				this.msg = "Lỗi chốt: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) { }
			
			e.printStackTrace();
			this.msg = "Lỗi: " + e.getMessage();
		}

		return true;
	}

	
	public boolean Chot() {
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Update ChiTieuNhanVien set trangthai = '1' where pk_seq = '" + this.Id + "' ";
			if( !db.update(query) )
			{
				this.msg = "Lỗi chốt: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) { }
			
			e.printStackTrace();
			this.msg = "Lỗi: " + e.getMessage();
		}

		return true;
	}

	
	public boolean UnChot() {

		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "Update ChiTieuNhanVien set trangthai = '0' where pk_seq = '" + this.Id + "' ";
			if( !db.update(query) )
			{
				this.msg = "Lỗi chốt: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) { }
			
			e.printStackTrace();
			this.msg = "Lỗi: " + e.getMessage();
		}

		return true;
	}

	
	public String[] getCodeTDV() {
		
		return this.codeTDV;
	}

	
	public void setCodeTDV(String[] codeTDV) {
		
		this.codeTDV = codeTDV;
	}

	
	public String[] getTenTDV() {
		
		return this.tenTDV;
	}

	
	public void setTenTDV(String[] tenTDV) {
		
		this.tenTDV = tenTDV;
	}

	
	public String[] getHangchienluoc() {
		
		return this.hangchienluoc;
	}

	
	public void setHangchienluoc(String[] hangchienluoc) {
		
		this.hangchienluoc = hangchienluoc;
	}

	
	public String[] getHangdactri() {
		
		return this.hangdactri;
	}

	
	public void setHangdactri(String[] hangdactri) {
		
		this.hangdactri = hangdactri;
	}

	
	public String[] getTongdskhoan() {
		
		return this.dskhoan;
	}

	
	public void setTongdskhoan(String[] tongdskhoan) {
		
		this.dskhoan = tongdskhoan;
	}

	
	public String[] getKPIChienluoc() {
		
		return this.kpichienluoc;
	}

	
	public void setKPIChienluoc(String[] kpiChienluoc) {
		
		this.kpichienluoc = kpiChienluoc;
	}

	
	public String[] getKPIDactri() {
		
		return this.kpidactri;
	}

	
	public void setKPIDactri(String[] kpiDactri) {
		
		this.kpidactri = kpiDactri;
	}
	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();

		return dateFormat.format(date);
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
		geso.traphaco.distributor.util.Utility util = new geso.traphaco.distributor.util.Utility();
		this.nppId = util.getIdNhapp(this.UserID);
		this.nppTen = util.getTenNhaPP();
		this.sitecode = util.getSitecode();
		
	}

	
	public String getLoainhanvien() {
		
		return this.loainhanvien;
	}

	
	public void setLoainhanvien(String loainhanvien) {
		
		this.loainhanvien = loainhanvien;
	}

	
	public String getNhanvienId() {
		
		return this.nhanvienId;
	}

	
	public void setNhanvienId(String nhanvienId) {
		
		this.nhanvienId = nhanvienId;
	}

	
	public ResultSet getNhanvienRs() {
		
		return this.nahnvienRs;
	}

	
	public void setNhanvienRs(ResultSet nhanvienRs) {
		
		this.nahnvienRs = nhanvienRs;
	}

	
	public void DBclose()
	{

		
	}
	
}
