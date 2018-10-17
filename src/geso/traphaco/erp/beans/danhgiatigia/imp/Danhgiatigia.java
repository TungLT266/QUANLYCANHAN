package geso.traphaco.erp.beans.danhgiatigia.imp;

import geso.traphaco.erp.beans.danhgiatigia.IDanhgiatigia;
import geso.traphaco.erp.db.sql.*;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Danhgiatigia implements IDanhgiatigia 
{
	String userId;
	String Id;
	String ctyId;
	ResultSet ctylist;
	
	String nam;
	ResultSet namlist;
	
	String thang;
	ResultSet thanglist;
		
	ResultSet kqlist;
	
	String ghidao;
	
	String msg;
	dbutils db;
	public Danhgiatigia(){
		this.userId = "";
		this.Id = "";
		this.ctyId = "";
		this.nam = "";
		this.thang = "";
		this.ghidao = "0";
		this.msg = "";
		this.db = new dbutils();
		
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}
	
	public String getUserId(){
		return this.userId;
	}

	public void setId(String Id){
		this.Id = Id;
	}
	
	public String getId(){
		return this.Id;
	}

	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getCtyId(){
		return this.ctyId;
	}

	
	public void setNam(String nam){
		this.nam = nam;
	}
	
	public String getNam(){
		return this.nam;
	}
	
	public void setNamRS(ResultSet namlist){
		this.namlist = namlist;
	}
	
	public ResultSet getNamRS(){
		return this.namlist;
	}

	public void setThangRS(ResultSet thang){
		this.thanglist = thang;
	}
	
	public ResultSet getThangRS(){
		return this.thanglist;
	}

	public void setKqRS(ResultSet kqlist){
		this.kqlist = kqlist;
	}
	
	public ResultSet getKqRS(){
		return this.kqlist;
	}

	public void setThang(String thang){
		this.thang = thang;
	}
	
	public String getThang(){
		return this.thang;
	}

	public void setGhidao(String ghidao){
		this.ghidao = ghidao;
	}
	
	public String getGhidao(){
		return this.ghidao;
	}
		
	public void setMsg(String msg){
		this.msg = msg;
	}
	
	public String getMsg(){
		return this.msg;
	}

	private void getNamThangList(){
		this.thang = this.getDateTime().substring(5, 7);
		this.nam = this.getDateTime().substring(0, 4);
		
		String thangks, namks;
		String query = "SELECT TOP 1 * FROM ERP_KHOASOKETOAN ORDER BY NAM, THANGKS DESC ";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			namks = rs.getString("NAM");
			thangks = rs.getString("THANGKS");
			rs.close();
			
			if(thangks.equals("12")){
				this.nam = "" + (Integer.parseInt(namks) + 1);
				this.thang = "1";
			}else{
				this.thang = "" + (Integer.parseInt(thangks) + 1);
			}
			rs.close();
			
			query = "DELETE ERP_DANHGIATIGIA_CHITIET WHERE DANHGIATIGIA_FK IN (SELECT PK_SEQ FROM ERP_DANHGIATIGIA WHERE NAM = " + this.nam + " AND THANG = " + this.thang + " AND TRANGTHAI = '0')";
			this.db.update(query);
			
			query = "DELETE ERP_DANHGIATIGIA WHERE NAM = " + this.nam + " AND THANG = " + this.thang + " AND TRANGTHAI = '0'";
			this.db.update(query);
			
			query = "SELECT COUNT(*) AS NUM FROM ERP_DANHGIATIGIA WHERE NAM = " + this.nam + " AND THANG = " + this.thang + " AND TRANGTHAI = '1' ";
			rs = this.db.get(query);
			try {
				rs.next();
				if(!rs.getString("NUM").equals("0")){
					this.msg = "Đánh giá tỉ giá cho tháng " + this.thang + ", năm " + this.nam + " không thể thực hiện, vì đã được thực hiện và chốt rồi ";
				}else{
					this.msg = "Hệ thống đã khóa sổ tháng " + thangks + ", năm " + namks + ". Thực hiện đánh giá tỉ giá cho tháng " + this.thang + ", năm " + this.nam + " ";					
				}
			} catch(Exception e) { }
		}catch(java.sql.SQLException e){}
				
	}
	private void createNamList(){
		int nam = Integer.parseInt(this.getDateTime().substring(0, 4));
		
		String query = "";
		if(nam == 2014){
			for(int i = nam; i <= nam + 1; i++)
			{
				query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
				if(i < nam + 1)
				{
					query += " union all ";
				}
			}
		}else{
			for(int i = nam - 1; i <= nam + 1; i++)
			{
				query += "select " + i + " as nam, N'Năm " + i + "' as namTen ";
				if(i < nam + 1)
				{
					query += " union all ";
				}
			}			
		}
		System.out.println(query);
		this.namlist = this.db.get(query);

	}

	private void createThangList(){
				
		String query = "";
		for(int i = 1; i <= 12; i++)
		{
			query += "select " + i + " as thang, N'Tháng " + i + "' as thangTen ";
			if(i < 12)
				query += " union all ";
		}
		System.out.println(query);
		
		this.thanglist = this.db.get(query);

	}

	public void init_Display(){
		
		String query = "";

		String ngaycuoithang = "";
		
		query = "SELECT PK_SEQ AS ID, GHIDAO, NAM, THANG FROM ERP_DANHGIATIGIA WHERE PK_SEQ = " + this.Id;
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		try{
			if(rs.next()){
				this.Id = rs.getString("ID");		
				this.ghidao = rs.getString("GHIDAO");	
				this.nam = rs.getString("NAM");
				this.thang = rs.getString("THANG");
				rs.close();
			}
		}catch(java.sql.SQLException e){}
			
		if(this.thang.length() == 1){
			ngaycuoithang = this.nam + "-0" + thang + "-31";
		}else{
			ngaycuoithang = this.nam + "-" + thang + "-31";
		}

		if(this.ctyId.length() > 0 & this.nam.length() > 0 & this.thang.length() > 0){			
			query = "SELECT '' AS ID, N'Hóa đơn mua hàng' AS LOAICT, DOITUONG, SOCHUNGTU, NGAYCHUNGTU, LOAITIEN, NGUYENTE, THANHTIENCU, THANHTIENMOI, CHENHLECH " +
					"FROM ERP_DANHGIATIGIA_CHITIET " +
					"WHERE DANHGIATIGIA_FK = " + this.Id + "";
			
			System.out.println(query);
			this.kqlist = this.db.get(query);
		}	
	}
	
	public void init_Update(){
		
		String query = "";
		
		// Truong hop cap nhat

		query = "SELECT NAM, THANG FROM ERP_DANHGIATIGIA WHERE PK_SEQ = " + this.Id + "";
		ResultSet rs = this.db.get(query);
		try{
			if(rs.next()){
				this.nam = rs.getString("NAM");
				this.thang =  rs.getString("THANG");
				rs.close();
			}
			
		}catch(java.sql.SQLException e){}
		
		if(this.Id.length() > 0){			
			query = "SELECT  DGTG_CT.LOAICHUNGTU AS LOAICT, DGTG_CT.DOITUONG, DGTG_CT.DOITUONG_FK, DGTG_CT.SOCHUNGTU, DGTG_CT.NGAYCHUNGTU, " +
					"DGTG_CT.LOAITIEN, DGTG_CT.NGUYENTE, DGTG_CT.THANHTIENCU, DGTG_CT.THANHTIENMOI, DGTG_CT.CHENHLECH " +
					"FROM ERP_DANHGIATIGIA_CHITIET DGTG_CT " +
					"WHERE DANHGIATIGIA_FK = " + this.Id + "";
			
			System.out.println(query);
			this.kqlist = this.db.get(query);
		}	
	}

	public void init_New(){
		
		String query = "";
		this.createNamList();
		this.createThangList();
		if(this.nam.length() == 0 & this.thang.length() == 0){
			this.getNamThangList();
		}
		String namthang = "";
		
		// Truong hop cap nhat
		if(this.Id.length() > 0){
			query = "SELECT GHIDAO FROM ERP_DANHGIATIGIA WHERE PK_SEQ = " + this.Id + "";
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				this.ghidao = rs.getString("GHIDAO");
				rs.close();
			}catch(java.sql.SQLException e){}
			
		}
			

		if(this.thang.length() == 1){
			namthang = this.nam + "-0" + thang ;
		}else{
			namthang = this.nam + "-" + thang ;
		}
		
		if(this.ctyId.length() > 0 & this.nam.length() > 0 & this.thang.length() > 0){	
// Tính Chênh lệch = Nguyên tệ chưa thanh toán * (tỉ giá dữ liệu nền - tỉ giá HĐ)			
			query =" select TK.SOHIEUTAIKHOAN, \n"+
				" CASE  \n"+
				"  WHEN SUM(PSKT.NO)> 0 THEN SUM(PSKT.NO) - SUM(TONGGIATRINT) * (SELECT ISNULL(TIGIAQUYDOI,0) FROM ERP_TIGIA WHERE TIENTE_FK=PSKT.TIENTEGOC_FK AND SUBSTRING(TuNgay,1,7)<='"+namthang+"' And SUBSTRING(DenNgay,1,7)>= '"+namthang+"')  \n"+
				"  WHEN SUM(PSKT.CO)> 0 THEN SUM(PSKT.CO) - SUM(TONGGIATRINT) * (SELECT ISNULL(TIGIAQUYDOI,0) FROM ERP_TIGIA WHERE TIENTE_FK=PSKT.TIENTEGOC_FK AND SUBSTRING(TuNgay,1,7)<='"+namthang+"' And SUBSTRING(DenNgay,1,7)>= '"+namthang+"')  \n"+
				"  ELSE 0 END AS CHENHLECH  \n"+
				"  ,SUM(TONGGIATRINT) GIATRINGUYENTE,DOITUONG,MADOITUONG,TIENTEGOC_FK   \n"+
				" from ERP_PHATSINHKETOAN PSKT inner join ERP_TAIKHOANKT TK ON TK.PK_SEQ=PSKT.TAIKHOAN_FK  \n"+
				" where TIENTEGOC_FK <> '100000' and TK.SOHIEUTAIKHOAN in (11221000,11222000,11223000,11224000,11225100,11225200,11226000,11227000,12812000,24421000,24422000,34112000,34114000,34122000,13112000,33112000)  \n"+
				" AND TK.CONGTY_FK ="+ this.ctyId +" \n"+
				" GROUP BY TK.SOHIEUTAIKHOAN,LOAICHUNGTU,SOCHUNGTU,DOITUONG,MADOITUONG,TIENTEGOC_FK  \n"+
				" HAVING  \n"+
				"  (CASE   \n"+
				"  WHEN SUM(PSKT.NO)> 0 THEN SUM(PSKT.NO) - SUM(TONGGIATRINT) * (SELECT ISNULL(TIGIAQUYDOI,0) FROM ERP_TIGIA WHERE TIENTE_FK=PSKT.TIENTEGOC_FK AND SUBSTRING(TuNgay,1,7)<='"+namthang+"' And SUBSTRING(DenNgay,1,7)>= '"+namthang+"')  \n"+
				"  WHEN SUM(PSKT.CO)> 0 THEN SUM(PSKT.CO) - SUM(TONGGIATRINT) * (SELECT ISNULL(TIGIAQUYDOI,0) FROM ERP_TIGIA WHERE TIENTE_FK=PSKT.TIENTEGOC_FK AND SUBSTRING(TuNgay,1,7)<='"+namthang+"' And SUBSTRING(DenNgay,1,7)>= '"+namthang+"')  \n"+
				"  ELSE 0 END) <>0		"	;
			System.out.println("query init_New " + query);
			this.kqlist = this.db.get(query);
		}	
	}

	public boolean Save(){
		String query = "";
		double tongcu = 0;
		double tongmoi = 0;
		String ghidao = this.ghidao;
		
		// goi ham nay de lay Id cua Danh gia ti gia da lam neu co
		this.init_New();
		
		this.ghidao = ghidao;
/*		if(this.ctyId.length() > 0 & this.nam.length() > 0 & this.thang.length() > 0){
			query = "DELETE ERP_DANHGIATIGIA_CHITIET WHERE DANHGIATIGIA_FK IN (SELECT PK_SEQ FROM ERP_DANHGIATIGIA WHERE NAM = " + this.nam + " AND THANG = " + this.thang + ")";
			System.out.println(query);
			this.db.update(query);
			
			query = "DELETE ERP_DANHGIATIGIA WHERE NAM = NAM = " + this.nam + " AND THANG = " + this.thang + "";
			System.out.println(query);
			this.db.update(query);
		}*/
		
		
		if(this.kqlist != null){
			try{
				this.db.getConnection().setAutoCommit(false);
				if(this.Id.length() == 0){	
					query = "INSERT INTO ERP_DANHGIATIGIA(NAM, THANG, NGUOITAO, NGUOISUA, NGAYTAO, NGAYSUA, TRANGTHAI, GHIDAO, CONGTY_FK) VALUES( " +
							"" + this.nam + ", " + this.thang + ", " + this.userId + ", " + this.userId + ", '" + this.getDateTime() + "', '" + this.getDateTime() + "', '0', '" + this.ghidao + "', " + this.ctyId + ") ";
					System.out.println(query);
		
					this.db.update(query);
		
					ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS ID");
					rs.next();
					this.Id = rs.getString("ID");
					rs.close();
				}else{
					query = "UPDATE ERP_DANHGIATIGIA SET GHIDAO = '" + this.ghidao + "', NGAYSUA = '" + this.getDateTime() + "' WHERE PK_SEQ = " + this.Id + "";
					System.out.println(query);
					this.db.update(query);
					
					query = "DELETE ERP_DANHGIATIGIA_CHITIET WHERE DANHGIATIGIA_FK = " + this.Id + "";
					System.out.println(query);
					this.db.update(query);
				}
				
				while(this.kqlist.next()){
					query = "INSERT INTO ERP_DANHGIATIGIA_CHITIET(DANHGIATIGIA_FK, LOAICHUNGTU, DOITUONG, DOITUONG_FK, SOCHUNGTU, NGAYCHUNGTU, LOAITIEN, NGUYENTE, THANHTIENCU, THANHTIENMOI, CHENHLECH) VALUES(" +
							"" + this.Id + ", N'" + this.kqlist.getString("LOAICT") + "', N'" + this.kqlist.getString("DOITUONG") + "', '" + this.kqlist.getString("DOITUONG_FK") + "', N'" + this.kqlist.getString("SOCHUNGTU") + "', '" + this.kqlist.getString("NGAYCHUNGTU") + "', '" + this.kqlist.getString("LOAITIEN") + "', " + this.kqlist.getString("NGUYENTE") + ", " + this.kqlist.getString("THANHTIENCU") + ", " + this.kqlist.getString("THANHTIENMOI") + ", " + this.kqlist.getString("CHENHLECH") + ")";
					
					System.out.println(query);
					this.db.update(query);
					tongcu = tongcu + Double.parseDouble(this.kqlist.getString("THANHTIENCU"));
					tongmoi = tongmoi + Double.parseDouble(this.kqlist.getString("THANHTIENMOI"));
				}
				
				query = "UPDATE ERP_DANHGIATIGIA SET TONGTIENCU = " + tongcu + ", TONGTIENMOI = " + tongmoi + ", TONGCHENHLECH = " + (tongmoi - tongcu) + " WHERE PK_SEQ = " + this.Id + "";
				System.out.println(query);
				this.db.update(query);
				
				this.db.getConnection().commit();
				this.db.getConnection().setAutoCommit(true);

			}catch(java.sql.SQLException e){
				this.msg = "Kết quả của đánh giá tỉ giá đã được lưu không thành công!";
			}
		}
		this.msg = "Kết quả của đánh giá tỉ giá đã được lưu thành công!";
		return true;
	}
		
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public void DBClose(){
		try{
			if(this.kqlist != null) this.kqlist.close();
			if(this.namlist != null) this.namlist.close();
			if(this.thanglist != null) this.thanglist.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){}
	}
}
