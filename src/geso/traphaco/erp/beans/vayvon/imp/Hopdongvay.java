package geso.traphaco.erp.beans.vayvon.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.vayvon.IHopdongvay;
import geso.traphaco.erp.db.sql.dbutils;
import javax.servlet.http.HttpSession;

public class Hopdongvay implements IHopdongvay {
	HttpSession session;
	String ctyId;
	String soHD;
	String diengiai;
	String ngay;
	String tongtien;
	String tongngoaite;
	String loai;
	String ngaybd;
	String thoihan;
	String trangthai;
	String Id;
	String msg;
	dbutils db;
	String nhId;
	String cnId;
	String ttId;
	String ntId;
	String hdtt;
	String userId;
	ResultSet nhRs;
	ResultSet cnRs;
	ResultSet ttRs;
	ResultSet ntvRs;
	
	public Hopdongvay()
	{   
		
		this.ctyId = "";
		this.Id ="";
		this.soHD = "";
		this.diengiai = "";
		this.ngay = "";
		this.tongtien = "0";
		this.tongngoaite = "0";
		this.loai = "";
		this.ngaybd = "";
		this.thoihan = "";
		this.msg="";
		this.nhId = "";
		this.cnId = "";
		this.ttId = "";
		this.ntId = "";
		this.hdtt = "";
		this.userId = "";
		this.trangthai = "";
		this.db = new dbutils();
	}
	public Hopdongvay(String Id)
	{   
		this.Id = Id;
		this.ctyId = "";
		this.soHD = "";
		this.diengiai = "";
		this.ngay = "";
		this.tongtien = "0";
		this.tongngoaite = "0";
		this.loai = "";
		this.ngaybd = "";
		this.thoihan = "";
		this.msg = "";
		this.nhId = "";
		this.cnId = "";
		this.ttId = "";
		this.ntId = "";
		this.hdtt = "";
		this.userId = "";
		this.trangthai = "";
		this.db = new dbutils();
	}

	public void setSession(HttpSession session){
		this.session = session;
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
	
	public void setSoHD(String soHD) {
		this.soHD = soHD;
		
	}

	public String getSoHD() {
		return this.soHD;
	}
	
	public void setDiengiai(String diengiai) {
		this.diengiai = diengiai;
		
	}

	public String getDiengiai() {
		return this.diengiai;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}
	
	public void setTongtien(String tongtien) {
		this.tongtien = tongtien;
		
	}

	public String getTongtien() {
		return this.tongtien;
	}
	
	public void setTongngoaite(String tongngoaite) {
		this.tongngoaite = tongngoaite;
		
	}

	public String getTongngoaite() {
		return this.tongngoaite;
	}

	public void setLoai(String loai) {
		this.loai = loai;
		
	}

	public String getLoai() {
		return this.loai;
	}

	public void setNgayBD(String ngaybd) {
		this.ngaybd = ngaybd;
		
	}

	public String getNgayBD() {
		return this.ngaybd;
	}

	public void setThoihan(String thoihan) {
		this.thoihan = thoihan;
		
	}

	public String getThoihan() {
		return this.thoihan;
	}
	
	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
		
	}


	public void setNhId(String nhId) {
		
		this.nhId = nhId;
	}
	
	public String getNhId() {
		
		return this.nhId;
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

	public void setTTId(String ttId) {
		
		this.ttId = ttId;
	}
	
	public String getTtId() {
		
		return this.ttId;
	}

	public void setNTId(String ntId) {
		
		this.ntId = ntId;
	}
	
	public String getNtId() {
		
		return this.ntId;
	}

	public void setHDTT(String hdtt) {
		
		this.hdtt = hdtt;
	}
	
	public String getHDTT() {
		
		return this.hdtt;
	}

	public void setTtRs(ResultSet ttRs) {
		
		this.ttRs = ttRs;
	}
	
	public ResultSet getTtRs1() {
		if(this.nhId.length() > 0 & this.cnId.length() > 0 ){
			String query = 	"SELECT DISTINCT TT.PK_SEQ AS TTID, TT.MA AS TT " + 
							"FROM ERP_TIENTE TT " +
							"INNER JOIN ERP_NGANHANG_CONGTY NH_CT ON NH_CT.TIENTE_FK = TT.PK_SEQ " +
							"WHERE TT.TRANGTHAI = 1 AND NH_CT.NGANHANG_FK = " + this.nhId + " AND NH_CT.CHINHANH_FK = " + this.cnId + "";
			
			System.out.println("TT 1: " + query);
			
			return this.db.get(query);
		}else{
			return null;
		}
		
	}

	public ResultSet getTtRs2() {
		if(this.nhId.length() > 0 & this.cnId.length() > 0 ){
			String query = 	"SELECT DISTINCT TT.PK_SEQ AS TTID, TT.MA AS TT " + 
							"FROM ERP_TIENTE TT " +
							"INNER JOIN ERP_NGANHANG_CONGTY NH_CT ON NH_CT.TIENTE_FK = TT.PK_SEQ " +
							"WHERE TT.TRANGTHAI = 1 AND NH_CT.NGANHANG_FK = " + this.nhId + " AND NH_CT.CHINHANH_FK = " + this.cnId + "";
		
			if(this.ttId.length() > 0){
				query = query + " AND TT.PK_SEQ <> " + this.ttId + " ";
			}
			
			System.out.println("TT 2: " + query);
			
			return this.db.get(query);
		}else{
			return null;
		}
	}
	
	public void setNtvRs(ResultSet ntvRs) {
		
		this.ntvRs = ntvRs;
	}
	
	public ResultSet getNtvRs() {
		return ntvRs;
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
				String sql =	"SELECT	HDV.SOHD, HDV.DIENGIAI, HDV.NGAY, NH.PK_SEQ AS NHID, CN.PK_SEQ AS CNID, " +
								"HDV.TONGTIEN, TT.PK_SEQ AS TTID, ISNULL(HDV.TONGNGOAITE, 0) AS TONGNGOAITE, ISNULL(TT2.PK_SEQ, 0) AS NTID,  HDV.NGAYBATDAU, HDV.THOIHAN, " + 
								"HDV.LOAI, NV2.TEN AS NGUOISUA, HDV.NGAYSUA, HDV.TRANGTHAI, ISNULL(THAYTHECHO,'') AS THAYTHECHO " +
								"FROM ERP_HOPDONGVAY HDV " +
								"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = HDV.NGANHANG_FK " +
								"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = HDV.CHINHANH_FK " +
								"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = HDV.TIENTE_FK " +
								"LEFT JOIN ERP_TIENTE TT2 ON TT2.PK_SEQ = HDV.NGOAITE_FK " +
								"INNER JOIN NHANVIEN NV1 ON NV1.PK_SEQ = HDV.NGUOITAO " + 
								"INNER JOIN NHANVIEN NV2 ON NV2.PK_SEQ = HDV.NGUOISUA " +
								"WHERE HDV.PK_SEQ = " + this.Id + " ";
				
				System.out.println(sql);
				ResultSet rs = db.get(sql);
			
				while(rs.next())
				{
					this.soHD = rs.getString("SOHD");
					this.diengiai = rs.getString("DIENGIAI");
					this.ngay = rs.getString("NGAY");
					
					this.nhId = rs.getString("NHID");
					this.session.setAttribute("nhId", this.nhId);
					
					this.cnId = rs.getString("CNID");
					this.ttId = rs.getString("TTID");
					this.tongtien = rs.getString("TONGTIEN");
					this.ntId = rs.getString("NTID");
					this.tongngoaite = rs.getString("TONGNGOAITE");
					this.loai = rs.getString("LOAI");
					this.ngaybd = rs.getString("NGAYBATDAU");
					this.thoihan = rs.getString("THOIHAN");
					this.trangthai = rs.getString("TRANGTHAI");
					
					this.hdtt = rs.getString("THAYTHECHO");
					this.msg = "";
				}
				rs.close();
				
			
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		CreateRs();
	}
   
	
	
	public void setmsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	
	public void Xoa()
	{
		String query = "DELETE ERP_HOPDONGVAY WHERE PK_SEQ = " + this.Id + " AND TRANGTHAI = 0 AND PK_SEQ NOT IN (SELECT DISTINCT HOPDONG_FK FROM ERP_NHANTIENVAY WHERE CONGTY_FK = " + this.ctyId + ")";
		this.db.update(query);
		
	}
	
	public void Hoantat()
	{
		String query = "UPDATE ERP_HOPDONGVAY SET TRANGTHAI = '2' WHERE PK_SEQ = " + this.Id + "";
		this.db.update(query);
		
	}

	public boolean save() {
		if(this.Id.length()>0 )
		
			if(this.soHD.length() == 0 || this.ngaybd.length() == 0|| this.thoihan.length() == 0 || this.loai.length() == 0 || this.nhId.length() == 0 || this.cnId.length() == 0 || this.ttId.length() == 0)
			{  
				this.msg = "Ban phai nhap du thong tin";
				return false;
			}
		
			if(this.Id.length() >0)
			{	
				String sql;
				if(this.ntId.length() > 0){
					sql =	"UPDATE ERP_HOPDONGVAY SET SOHD = N'" + this.soHD + "', DIENGIAI = N'"+ this.diengiai +"', ngay = '"+ this.ngay +"', " +
							"NGANHANG_FK = '" + this.nhId + "', CHINHANH_FK = '"+ this.cnId + "'," +
							"TIENTE_FK = '" + this.ttId + "', TONGTIEN = '" + this.tongtien + "', " +
							"NGOAITE_FK = " + this.ntId + ", TONGNGOAITE = " + this.tongngoaite + ", " +
							"LOAI = '" + this.loai + "', " +
							"NGAYBATDAU = '" + this.ngaybd + "', THOIHAN = '" + this.thoihan + "', " +
							"THAYTHECHO = '" + this.hdtt + "',  "+
							"NGAYSUA ='"+ this.getDateTime() +"', NGUOISUA  ='"+ this.userId+"' WHERE PK_SEQ ='"+ this.Id +"'";					
				}else{
					sql =	"UPDATE ERP_HOPDONGVAY SET SOHD = N'" + this.soHD + "', DIENGIAI = N'"+ this.diengiai +"', ngay = '"+ this.ngay +"', " +
							"NGANHANG_FK = '" + this.nhId + "', CHINHANH_FK = '"+ this.cnId + "'," +
							"TIENTE_FK = '" + this.ttId + "', TONGTIEN = '" + this.tongtien + "', " +
							"NGOAITE_FK = NULL , TONGNGOAITE = 0 , " +
							"LOAI = '" + this.loai + "', " +
							"NGAYBATDAU = '" + this.ngaybd + "', THOIHAN = '" + this.thoihan + "', " +
							"THAYTHECHO = '" + this.hdtt + "',  "+
							"NGAYSUA ='"+ this.getDateTime() +"', NGUOISUA  ='"+ this.userId+"' WHERE PK_SEQ ='"+ this.Id +"'";									
				}
				
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
			if(this.soHD.length() == 0 || this.ngaybd.length() == 0|| this.thoihan.length() == 0 || this.loai.length() == 0 || this.nhId.length() == 0 || this.cnId.length() == 0 || this.ttId.length() == 0)
			{  
				this.msg = "Ban phai nhap du thong tin";
				return false;
			}

			String sql;
			if(this.ntId.length() == 0) this.ntId = null;
			sql =	"INSERT INTO ERP_HOPDONGVAY(SOHD, DIENGIAI, NGAY, NGANHANG_FK, CHINHANH_FK, TIENTE_FK, TONGTIEN, NGOAITE_FK, TONGNGOAITE, LOAI, " +
					"NGAYBATDAU,THAYTHECHO, THOIHAN, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, CONGTY_FK, TRANGTHAI) VALUES("+
					"N'" + this.soHD + "', N'" + this.diengiai + "','" + this.ngay + "', '" + this.nhId + "','" + this.cnId + "','"+ this.ttId +"'," +
					"'"+ this.tongtien + "', " + this.ntId + ", " + this.tongngoaite + ", " +
					"'" + this.loai + "','" + this.ngaybd +"','" + this.hdtt +"', '" + this.thoihan + "', '" + this.getDateTime() + "', " +
					"'" + this.getDateTime() + "', '" + this.userId + "','" + this.userId + "', " + this.ctyId + ", '0' )";
			
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
				sql = "SELECT IDENT_CURRENT('ERP_HOPDONGVAY') AS HDID";
					
				ResultSet rs = this.db.get(sql);						
				rs.next();
				this.Id = rs.getString("HDID");

				rs.close();
									
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				this.Id="";
				e.printStackTrace();
			}
		}
			
//		String tmp = "";
//		int index = 0;
//		tmp = this.hdtt;
//
//		if(tmp.length() > 0 & tmp.indexOf("### N/A") < 0 & this.Id.length() > 0){
//			index = tmp.indexOf("[");				
//			tmp = tmp.substring(index + 1, tmp.length());
//				
//			index = tmp.indexOf("[");				
//			tmp = tmp.substring(index + 1, tmp.length());
//
//			index = tmp.indexOf("[");				
//			tmp = tmp.substring(index + 1, tmp.length());
//
//			index = tmp.indexOf("[");				
//			String ttId = tmp.substring(index + 1, tmp.length() - 1);
//			String tt="";
//			if( this.hdtt.length() >3){
//				tt = this.hdtt.substring(3, this.hdtt.indexOf("-"));
//			}
//			
//			String sql =  	"UPDATE ERP_HOPDONGVAY " +
//							"SET LICHSU = (SELECT '" + ttId + "' + ',' + ISNULL(LICHSU, '0') FROM ERP_HOPDONGVAY WHERE PK_SEQ = " + ttId + "), " +
//							"THAYTHECHO = N'" + this.hdtt + "' " +
//							"WHERE PK_SEQ = " + this.Id + " ";
//			System.out.println(sql);
//			
//			this.db.update(sql);
//		}
		
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
			if(this.nhRs != null) this.nhRs.close();
			if(this.cnRs != null) this.cnRs.close();
			if(this.ttRs != null) this.ttRs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
		db.shutDown();
	}
	@Override
	public void CreateRs() {
		// TODO Auto-generated method stub
		if(!this.trangthai.equals("0") &&  this.Id.trim().length() > 0){
			
			String sql = "   SELECT ntv.TKVAY, NGAYNHAN, SOTIEN, ntv.HINHTHUC, LAISUAT, isnull(sum(tt.TIENGOC),0) as SOTIENDATRA \n" +
					" FROM ERP_NHANTIENVAY ntv left join ERP_THANHTOANNOVAY tt on tt.NHANTIENVAY_FK= ntv.PK_SEQ where ntv.HOPDONG_FK = " + this.Id + "\n" +
					" group by ntv.PK_SEQ, ntv.TKVAY, ntv.NGAYNHAN, ntv.HINHTHUC, ntv.LAISUAT, ntv.SOTIEN \n";
			System.out.println("lay tai khoan vay: \n" + sql + "\n====================================");
			this.ntvRs = this.db.get(sql);
		}
		
		this.nhRs = this.db.get("SELECT NH.PK_SEQ AS NHID, NH.MA + ' - ' + NH.TEN AS NH " + 
								"FROM ERP_NGANHANG NH " +
								"INNER JOIN ERP_NGANHANG_CONGTY NH_CT ON NH.PK_SEQ = NH_CT.NGANHANG_FK " +
								"WHERE NH.TRANGTHAI = 1 AND NH_CT.TIENTE_FK IN (SELECT PK_SEQ FROM ERP_TIENTE WHERE TRANGTHAI = '1' ) ");
		
		
		if(this.nhId.length() > 0){
			String query = 	"SELECT DISTINCT CN.PK_SEQ AS CNID, CN.MA + ' - ' + CN.TEN AS CN \n" + 
							"FROM ERP_CHINHANH CN \n" +
							"INNER JOIN ERP_NGANHANG_CONGTY NH_CT ON CN.PK_SEQ = NH_CT.CHINHANH_FK \n" +
							"WHERE CN.TRANGTHAI = 1 AND NH_CT.NGANHANG_FK = " + this.nhId + " \n";
			
			System.out.println("lay chi nhanh ngan hang:\n" + query + "\n----------------------------------------------------");
			this.cnRs = this.db.get(query);
		}
	}
}
