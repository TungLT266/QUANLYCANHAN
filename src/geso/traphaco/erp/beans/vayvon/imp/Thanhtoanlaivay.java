package geso.traphaco.erp.beans.vayvon.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.vayvon.IThanhtoanlaivay;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Thanhtoanlaivay implements IThanhtoanlaivay{
	String Id;
	String ctyId;
	String soHD;
	String ntvId;
	String ngay;
	String hinhthuc;
	String tkctyId;
	String tienlai;
	String tiengoc;
	String tienphat;
	String phikhac;
	String tiente;
	String ttId;
	String ghichu;
	String msg;
	String userId;
	dbutils db;
	ResultSet HDRS;
	ResultSet ttRs;
	ResultSet NTVRS;
	ResultSet TKCTYRS;
	
	public Thanhtoanlaivay()
	{   
		this.ctyId = "";
		this.Id = "";
		this.soHD = "";
		this.ntvId = "";
		this.ttId = "";
		this.ngay = "";
		this.hinhthuc = "";
		this.tienlai = "0";
		this.tiengoc = "0";
		this.tienphat = "0";
		this.phikhac = "0";
		this.tiente = "";
		this.msg = "";
		this.ghichu = "";
		this.userId = "";
		this.db = new dbutils();
	}
	public Thanhtoanlaivay(String Id)
	{   
		this.ctyId = "";
		this.Id = Id;
		this.soHD = "";
		this.ntvId = "";
		this.ttId = "";
		this.ngay = "";
		this.hinhthuc = "";
		this.tienlai = "0";
		this.tiengoc = "0";
		this.tienphat = "0";
		this.phikhac = "0";
		this.tiente = "";
		this.msg = "";
		this.ghichu = "";
		this.userId = "";
		this.db = new dbutils();
	}

	public void setId(String Id) {
		this.Id = Id;
		
	}
	
	public String getId() {
		
		return this.Id;
	}

	public void setCtyId(String ctyId) {
		this.ctyId = ctyId;
		
	}

	public String getCtyId() {
		return this.ctyId;
	}
	
	public void setTtId(String ttId) {
		this.ttId = ttId;
		try{
			if(this.ttId.length() > 0){
				System.out.println("SELECT MA FROM ERP_TIENTE WHERE PK_SEQ = " + this.ttId + " ");
				
				ResultSet rs = this.db.get("SELECT MA FROM ERP_TIENTE WHERE PK_SEQ = " + this.ttId + " ");
				if (rs != null)
				{
					if (rs.next())			
						this.tiente = rs.getString("MA");
					rs.close();
				}
			}
		}catch(java.sql.SQLException e){}
	}

	public String getTtId() {
		return this.ttId;
	}

	public void setSoHD(String soHD) {
		this.soHD = soHD;
	}

	public String getSoHD() {
		return this.soHD;
	}
	
	public void setNtvId(String ntvId) {
		this.ntvId = ntvId;
		
	}

	public String getNtvId() {
		return this.ntvId;
	}

	public void setHinhthuc(String hinhthuc) {
		this.hinhthuc = hinhthuc;
		
	}

	public String getHinhthuc() {
		return this.hinhthuc;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}
	
	public void setTkCtyId(String tkctyId) {
		this.tkctyId = tkctyId;
		
	}

	public String getTkCtyId() {
		return this.tkctyId;
	}

	public void setTienlai(String tienlai) {
		this.tienlai = tienlai;
		
	}

	public String getTienlai() {
		return this.tienlai;
	}
	
	public void setTiengoc(String tiengoc) {
		this.tiengoc = tiengoc;
		
	}

	public String getTiengoc() {
		return this.tiengoc;
	}
	
	public void setTienphat(String tienphat) {
		this.tienphat = tienphat;
		
	}

	public String getTienphat() {
		return this.tienphat;
	}

	public void setPhikhac(String phikhac) {
		this.phikhac = phikhac;
		
	}

	public String getPhikhac() {
		return this.phikhac;
	}

	public void setTiente(String tiente) {
		this.tiente = tiente;
		
	}

	public String getTiente() {
		return this.tiente;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
		
	}

	public String getGhichu() {
		return this.ghichu;
	}

	public void setTtRs(ResultSet ttRs) {
		
		this.ttRs = ttRs;
	}
	
	public ResultSet getTtRs() {
		if(this.soHD.length() > 0){
			String query = 		"SELECT DISTINCT TT.PK_SEQ AS TTID, TT.MA AS TT " +
								"FROM ERP_TIENTE TT " +
								"INNER JOIN ERP_HOPDONGVAY HDV ON HDV.TIENTE_FK = TT.PK_SEQ " +
								"WHERE TT.TRANGTHAI = 1 " ;
			
		
			if(this.ntvId.length() > 0)
				query = query + " AND TT.PK_SEQ IN (SELECT TIENTE_FK FROM ERP_NHANTIENVAY WHERE PK_SEQ = " + this.ntvId + ")";
			
			return this.db.get(query);	
		}else{
			return null;
		}
	}
	
	public void setHDRS(ResultSet HDRS) {
		
		this.HDRS = HDRS;
	}
	
	public ResultSet getHDRS() {
		
		return this.HDRS;
	}

	public void setNTVRS(ResultSet NTVRS) {
		
		this.NTVRS = NTVRS;
	}
	
	public ResultSet getNTVRS() {
		
		return this.NTVRS;
	}

	public void setTKCTYRS(ResultSet TKCTYRS) {
		
		this.TKCTYRS = TKCTYRS;
	}
	
	public ResultSet getTKCTYRS() {
		
		return this.TKCTYRS;
	}

	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getUserId() {
		
		return this.userId;
	}

	public void init() {
		
		if(this.Id.length()>0)
		{
			try {
				String sql =	"SELECT	HDV.PK_SEQ AS SOHD, TTNV.NHANTIENVAY_FK AS NTVID, TTNV.NGAY, TTNV.HINHTHUC, TTNV.TKCONGTY_FK AS TKCTYID, " +
								"isnull(TTNV.TIENTE_FK, 100000) as TIENTE_FK, ISNULL(TTNV.TIENLAI, 0) AS TIENLAI, ISNULL(TTNV.TIENGOC, 0) AS TIENGOC, " +
								"ISNULL(TTNV.TIENPHAT, 0) AS TIENPHAT, ISNULL(TTNV.PHIKHAC, 0) AS PHIKHAC, TTNV.TRANGTHAI, TT.MA AS TT, TTNV.GHICHU " +
								"FROM ERP_HOPDONGVAY HDV " +
								"INNER JOIN ERP_THANHTOANLAIVAY TTNV ON TTNV.HOPDONG_FK = HDV.PK_SEQ " +
								"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = HDV.TIENTE_FK " +
								"WHERE TTNV.PK_SEQ = " + this.Id + " ";
								
				
				System.out.println(sql);
				ResultSet rs = db.get(sql);
			
				if (rs != null)
				{
					while(rs.next())
					{
						this.soHD = rs.getString("SOHD");
						this.ntvId = rs.getString("NTVID");
						this.ngay = rs.getString("NGAY");
						this.hinhthuc = rs.getString("HINHTHUC");
						this.tkctyId = rs.getString("TKCTYID");
						this.tienlai = rs.getString("TIENLAI");
						
						this.tienphat = rs.getString("TIENPHAT");
						this.phikhac = rs.getString("PHIKHAC");
						this.tiente = rs.getString("TT");
						this.ghichu = rs.getString("GHICHU");
						this.msg = "";
						this.setTtId(rs.getString("TIENTE_FK"));
					}
					rs.close();
				}				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		init_RS();
	}
   
	public void init_RS(){ 
	
		this.HDRS = this.db.get("SELECT PK_SEQ AS HDID, SOHD + ' - ' + DIENGIAI AS HD FROM ERP_HOPDONGVAY WHERE TRANGTHAI = 1");
		String query = "";
		
		if(this.soHD.length() > 0){
			query = "SELECT NTV.PK_SEQ AS NTVID,  " +
					"TKVAY + ' - [' + CONVERT(VARCHAR, CAST(NTV.SOTIEN AS MONEY), 1) + ' ' + TT.MA + '] - ' + NTV.GHICHU AS NTV " +
					"FROM ERP_HOPDONGVAY HDV " +
					"INNER JOIN ERP_NHANTIENVAY NTV ON NTV.HOPDONG_FK = HDV.PK_SEQ " +
					"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NTV.TIENTE_FK " +
					"WHERE HDV.PK_SEQ = " + this.soHD + " AND NTV.TRANGTHAI = '1' ";
			this.NTVRS = this.db.get("NTVRS: " + query);
		}
	
/*		this.TKCTYRS = 	this.db.get("SELECT NHCTY.PK_SEQ AS TKID, NH.MA + ' - ' + NH.TEN AS TK " +
									"FROM ERP_NGANHANG_CONGTY NHCTY " +
									"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK WHERE NHCTY.CONGTY_FK = " + this.ctyId + " "); */
		
		query = "SELECT NH_CTY.PK_SEQ AS TKID, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TK " +
				"FROM ERP_NGANHANG_CONGTY NH_CTY " +
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " + 
				"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
				"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
				"WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "' " +
				"AND TT.PK_SEQ IN (SELECT TIENTE_FK FROM ERP_NHANTIENVAY WHERE PK_SEQ = " + this.ntvId + " UNION SELECT 100000)";
		
		System.out.println("TKCTYRS: " + query);
		this.TKCTYRS = db.get(query);
		
//		this.Tinhlaivay();
	}
	
	private void Tinhlaivay(){
		String query;
		if(this.ngay.length() > 0 & this.ntvId.length() > 0){
			try{
				query = "SELECT NTV.LAISUAT, NTV.NGAYNHAN, (NTV.SOTIEN - ISNULL(SUM(TIENGOC), 0)) AS CONLAI " +
						"FROM ERP_NHANTIENVAY NTV " +
						"LEFT JOIN ERP_THANHTOANLAIVAY TT ON TT.NHANTIENVAY_FK = NTV.PK_SEQ AND TT.TRANGTHAI = 1 " +
						"WHERE  NTV.TRANGTHAI = 1 AND NTV.PK_SEQ = " + this.ntvId  + " " +
						"GROUP BY NTV.SOTIEN, NTV.LAISUAT, NTV.NGAYNHAN " ;
				
				System.out.println(query);
				ResultSet rs = this.db.get(query);
				String ngaynhan = "";
				double conlai = 0;
				String laisuat = "";
				if (rs != null)
				{
					if (rs.next())
					{
						laisuat = rs.getString("LAISUAT");
						ngaynhan = rs.getString("NGAYNHAN");
						conlai = Double.parseDouble(rs.getString("CONLAI"));
					}
					rs.close();
				}
				
				query = "SELECT NGAY FROM ERP_THANHTOANLAIVAY WHERE CONGTY_FK = " + this.ctyId + " ORDER BY NGAY DESC ";
				System.out.println(query);
				rs = this.db.get(query);
				if (rs != null)
				{
					if(rs.next()){
						ngaynhan = rs.getString("NGAY");
					}
					rs.close();
				}

				query = "SELECT DATEDIFF(day,'" + ngaynhan + "','" + this.ngay + "') AS DIFF";
				System.out.println(query);
				rs = this.db.get(query);
				double laivay = 0;
				if (rs != null)
				{
					if (rs.next())
						laivay = (conlai*Double.parseDouble(rs.getString("DIFF"))*Double.parseDouble(laisuat)/360)/100;
					rs.close();
				}
				System.out.println("laivay: " + laivay);
				if(laivay <= 0) laivay = 0;
				
				System.out.println("laivay: " + laivay);
				
				if(this.tienlai.equals("0")) this.tienlai += "" + laivay;
			}catch(java.sql.SQLException e){
				System.out.println(e.toString());
			}
		}
	}

	public void setmsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	
	public void Xoa()
	{
		String query = "DELETE ERP_THANHTOANLAIVAY WHERE PK_SEQ = " + this.Id + " AND TRANGTHAI = 0 ";
						
		this.db.update(query);
		
	}
	
	public String Hoantat()
	{
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE ERP_THANHTOANLAIVAY SET TRANGTHAI = '1' WHERE PK_SEQ = " + this.Id + "";		
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi chốt thanh toán nợ vay: " + query;
			}
			
			//GHI NHAN BOOK TOAN
			Utility util = new Utility();
			query =  "select  a.TIENTE_FK, isnull( ( select TIGIAQUYDOI from ERP_TIGIA where TIENTE_FK = a.TIENTE_FK and TuNgay <= a.NGAY and a.NGAY <= DenNgay ), 0 ) as TYGIA,  " + 
					 "		a.TIENGOC, a.TIENLAI, a.TIENPHAT, a.PHIKHAC, a.NGAY,  " + 
					 "		case a.hinhthuc when 2 then '111100'   " + 
					 "						else ( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq in ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where PK_SEQ = a.TKCONGTY_FK   ) )  " + 
					 "		end as sohieutaikhoanCO, " + 
					 "		case when b.loai = 1 then  (case b.nganhang_fk when '100039' then '311100' " +
					 "										               when '100003' then '311200' " +
					 "                                                     when '100002' then '311300' end) " + 
					 "			 when b.loai = 2 then '341000' " + 
					 "		end as sohieuTK_NO_TIENGOC, '635100' as sohieuTK_NO_TIENLAI, '811000' as sohieuTK_NO_TIENPHAT, '642020' as sohieuTK_NO_PHIKHAC " + 
					 "from ERP_THANHTOANLAIVAY a " +
					 "inner join ERP_HOPDONGVAY b on a.HOPDONG_FK = b.PK_SEQ " + 
					 "where a.PK_SEQ = '" + this.Id + "' ";
			
			System.out.println("__XAC DINH TAI KHOAN: " + query);
			String loaichungtu="Thanh toán lãi vay";
			ResultSet psktRs = db.get(query);
			 
				while(psktRs.next())
				{
					double tygia = psktRs.getDouble("TYGIA");
					
					double tiengoc = Math.round(psktRs.getDouble("TIENGOC"));
					double tiengocViet = Math.round( tygia * tiengoc );
					
					String nam = psktRs.getString("NGAY").substring(0, 4);
					String thang = psktRs.getString("NGAY").substring(5, 7);
					String tiente_fk = psktRs.getString("TIENTE_FK");
					
					System.out.println("---TIEN GOC: " + tiengoc + "  --- TIEN GOC VIET: " + tiengocViet);
					if(tiengoc > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENGOC") == null ? "" : psktRs.getString("sohieuTK_NO_TIENGOC") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "1.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							System.out.println("____MSG: " + msg);
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"),loaichungtu, this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tiengocViet), Double.toString(tiengocViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(tiengocViet), Double.toString(tiengoc), "Tiền gốc" );
						//System.out.println("____MSG: " + msg);
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
					
					double tienlai = Math.round(psktRs.getDouble("TIENLAI"));
					double tienlaiViet = Math.round( tygia * tienlai );
					
					if(tienlai > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENLAI") == null ? "" : psktRs.getString("sohieuTK_NO_TIENLAI") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "2.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), loaichungtu, this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tienlaiViet), Double.toString(tienlaiViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(tienlaiViet), Double.toString(tienlai), "Tiền lãi" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
					
					double tienphat = Math.round(psktRs.getDouble("TIENPHAT"));
					double tienphatViet = Math.round( tygia * tienphat );
					
					if(tienphat > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENPHAT") == null ? "" : psktRs.getString("sohieuTK_NO_TIENPHAT") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "3.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), loaichungtu, this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tienphatViet), Double.toString(tienphatViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(tienphatViet), Double.toString(tienphat), "Tiền phạt" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
					
					double phikhac = Math.round(psktRs.getDouble("PHIKHAC"));
					double phikhacViet = Math.round( tygia * phikhac );
					
					if(phikhac > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_PHIKHAC") == null ? "" : psktRs.getString("sohieuTK_NO_PHIKHAC") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "4.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu( db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(phikhacViet), Double.toString(phikhacViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(phikhacViet), Double.toString(phikhac), "Phí khác" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
				}
				psktRs.close();
			 
			db.getConnection().commit();
			db.shutDown();
		}
		catch(Exception e)
		{	
			e.printStackTrace();
			db.update("rollback");
			return "Lỗi chốt thanh toán nợ vay: " + e.getMessage();
		}
		
		return msg;
		
	}

	public boolean save() {
		if(this.tkctyId=="") this.tkctyId= "NULL";
		if(this.Id.length()>0 ){
		
			if(this.soHD.length() == 0 || this.ntvId.length() == 0 || this.ngay.length() == 0|| this.hinhthuc.length() == 0 ||  tkctyId.length()== 0 || this.ttId.length() == 0 )
			{  
				this.msg = "Ban phai nhap du thong tin";
				return false;
			}
		
			String sql;

			sql =	"UPDATE ERP_THANHTOANLAIVAY SET HOPDONG_FK = '" + this.soHD + "', NHANTIENVAY_FK = '" + this.ntvId + "', ngay = '"+ this.ngay +"', " +
					"HINHTHUC = '" + this.hinhthuc + "', TKCONGTY_FK = "+ this.tkctyId + ", " +
					"TIENLAI = '" + this.tienlai + "', TIENPHAT = '" + this.tienphat + "', " +
					"PHIKHAC = '" + this.phikhac + "', GHICHU = N'" + this.ghichu + "', " +
					"TIENTE_FK = '" + this.ttId + "', " +
					"NGAYSUA ='"+ this.getDateTime() +"', NGUOISUA  ='"+ this.userId+"' WHERE PK_SEQ ='"+ this.Id +"' ";
				
			System.out.println("lenh update:"+ sql);
			if(!db.update(sql))
			{	
				db.update("rollback");
				this.msg =sql;
				return false;
			}
			
		}
		else
		{      
			String sql;
			if(this.ngay.length() == 0) this.ngay = this.getDateTime();
			
			sql =	"INSERT INTO ERP_THANHTOANLAIVAY(HOPDONG_FK, NHANTIENVAY_FK, NGAY, HINHTHUC, TKCONGTY_FK, TIENTE_FK, TIENLAI, TIENPHAT, PHIKHAC, GHICHU, TRANGTHAI, NGAYSUA, NGUOISUA,  CONGTY_FK)  " +
					"VALUES('"+ this.soHD + "', '" + this.ntvId + "', '" + this.ngay + "', '" + this.hinhthuc + "'," + this.tkctyId + ", '" + this.ttId + "', " +
							"'"+ this.tienlai +"', '"+ this.tienphat + "','" + this.phikhac +"', N'" + this.ghichu + "', '0', " +
							"'" + this.getDateTime() + "', '" + this.userId + "', " + this.ctyId + " )";
			
			System.out.println(sql);
			try {
				db.getConnection().setAutoCommit(false);
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg =sql;
					return false;
				}
					 //System.out.println(sql);
				sql = "SELECT IDENT_CURRENT('ERP_THANHTOANLAIVAY') AS TTNVID";
					
				ResultSet rs = this.db.get(sql);
				if (rs != null)
				{
					if (rs.next())
						this.Id = rs.getString("TTNVID");
					rs.close();
				}									
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
	
	
	@Override
	public void DbClose() {
		// TODO Auto-generated method stub
		try{
			if(this.HDRS != null) this.HDRS.close();
			if (this.ttRs != null)
				this.ttRs.close();
			if (this.NTVRS != null)
				this.NTVRS.close();
			if (this.TKCTYRS != null)
				this.TKCTYRS.close();
			db.shutDown();
		}catch(Exception er){
			er.printStackTrace();
		}
	}
}
