package geso.traphaco.erp.beans.dieuchuyentien.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.dieuchuyentien.IErpDieuchuyentien;
import geso.traphaco.center.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ErpDieuchuyentien implements IErpDieuchuyentien 
{
	String congtyId;
	String userId;
	String Id;
	String sochungtu;
	String ngaychungtu;
	String ttId;
	String tigia;
	ResultSet ttRs;
	
	String loaidc;
	
	String nhchuyenId;
	ResultSet nhchuyenRs;
	
	String nhnhanId;
	ResultSet nhnhanRs;
	
	String sotienNT;
	String sotienVND;
	String trichphi;
	
	String nhtrichphiId;
	ResultSet nhtrichphiRs;
	
	String phiNT;
	String phiVND;
	String thueNT;
	String thueVND;
	String ghichu;
	String msg;
	
	String mahoadon;
	String mauhoadon;
	String kyhieu;
	String sohoadon;
	String ngayhoadon;
	String mst;
	String tienhang;
	String thuesuat;
	String tienthue;
	String tenNH_VAT;

	String nccId;
	ResultSet nccRs;
	String POId;
	ResultSet PORs;
	
	String nhkyquyId;
	ResultSet nhkyquyRs;
	
	dbutils db;
	Utility util;
	
	public ErpDieuchuyentien()
	{
		this.congtyId = "";
		this.userId = "";
		this.Id = "";
		this.sochungtu = "";
		this.ngaychungtu = "";
		this.ttId = "";
		this.tigia = "1";
		this.nhchuyenId = "";
		this.nhnhanId = "";
		this.sotienNT = "0";
		this.sotienVND = "0";
		this.trichphi = "";
		
		this.loaidc = "";
		
		this.nhtrichphiId = "";
		this.phiNT = "0";
		this.phiVND = "0";
		this.thueNT = "0";
		this.thueVND = "0";
		this.ghichu = "";
		this.msg = "";
		
		this.mahoadon = "";
		this.mauhoadon = "";
		this.kyhieu = "";
		this.sohoadon = "";
		this.ngayhoadon = "";
		this.mst = "";
		this.tienhang = "0";
		this.thuesuat = "0";
		this.tienthue = "0";
		this.tenNH_VAT = "";
		
		this.nccId = "";
		this.POId = "";
		this.nhkyquyId = "";
		
		this.db = new dbutils();
		this.util=new Utility();
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
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
		return this.Id;
	}

	public void setId(String Id) 
	{
		this.Id = Id;
	}
	
	public String getSochungtu(){
		return this.sochungtu;
	}
	
	public void setSochungtu(String sochungtu){
		this.sochungtu = sochungtu;
	}

	public String getNgaychungtu(){
		return this.ngaychungtu;
	}
	
	public void setNgaychungtu(String ngaychungtu){
		this.ngaychungtu = ngaychungtu;
	}

	public String getTtId(){
		return this.ttId;
	}
	
	public void setTtId(String ttId){
		this.ttId = ttId;
	}

	public String getTigia(){
		return this.tigia;
	}
	
	public void setTigia(String tigia){
		this.tigia = tigia;
	}
	
	public String getNhchuyenId(){
		return this.nhchuyenId;
	}

	public void setNhchuyenId(String nhchuyenId){
		this.nhchuyenId = nhchuyenId;
	}

	public String getNhnhanId(){
		return this.nhnhanId;
	}
	
	public void setNhnhanId(String nhnhanId){
		this.nhnhanId = nhnhanId;
	}

	public String getSotienNT(){
		return this.sotienNT;
	}
	
	public void setSotienNT(String sotienNT){
		this.sotienNT = sotienNT;
	}

	public String getSotienVND(){
		return this.sotienVND;
	}
	
	public void setSotienVND(String sotienVND){
		this.sotienVND = sotienVND;
	}

	public String getTrichphi()
	{
		return this.trichphi;
	}

	public void setTrichphi(String trichphi)
	{
		this.trichphi = trichphi;
	}


	public String getNHTrichphiId()
	{
		return this.nhtrichphiId;
	}

	public void setNHTrichphiId(String NHtrichphiId)
	{
		this.nhtrichphiId = NHtrichphiId;
		if(this.trichphi.equals("1")){
			if(this.nhtrichphiId != null && this.nhtrichphiId.trim() .length() > 0){
				String query = 	"SELECT ISNULL(NH.MA, '') + ' - ' + ISNULL(CN.MA, '') + ' [' + ISNULL(TT.MA, '') + ']' AS TENNH_VAT, ISNULL(NH_CTY.MASOTHUE, '') AS MASOTHUE " + 
								"FROM ERP_NGANHANG_CONGTY NH_CTY " +
								"LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
								"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
								"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " +
								"WHERE NH_CTY.PK_SEQ = " + this.nhtrichphiId + "";
				
				System.out.println("query: " + query);
				ResultSet rs = this.db.get(query);
				if(rs != null){
					try{
						rs.next();
						this.tenNH_VAT = rs.getString("TENNH_VAT");
						this.mst = rs.getString("MASOTHUE");
						rs.close();
					}catch(java.sql.SQLException e){
						e.printStackTrace();
					}
				}
			}else{
				this.tenNH_VAT = "";
			}
		}
	}


	public String getPhiNT()
	{
		return 	this.phiNT;
	}

	public void setPhiNT(String phiNT)
	{
		this.phiNT = phiNT;
	}

	public String getPhiVND()
	{
		return 	this.phiVND;
	}

	public void setPhiVND(String phiVND)
	{
		this.phiVND = phiVND;
	}

	public String getThueVND()
	{
		return 	this.thueVND;
	}

	public void setThueVND(String thueVND)
	{
		this.thueVND = thueVND;
	}

	public String getThueNT()
	{
		return 	this.thueNT;
	}

	public void setThueNT(String thueNT)
	{
		this.thueNT = thueNT;
	}

	public String getGhichu()
	{
		return 	this.ghichu;
	}

	public void setGhichu(String ghichu)
	{
		this.ghichu = ghichu;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	public ResultSet getTienteRs(){
		return this.ttRs;
	}

	public void setTienteRs(ResultSet ttRs){
		this.ttRs = ttRs;
	}

	public ResultSet getNHChuyenRs(){
		return this.nhchuyenRs;
	}

	public void setNHChuyenRs(ResultSet nhchuyenRs){
		this.nhchuyenRs = nhchuyenRs;
	}

	public ResultSet getNHNhanRs(){
		return this.nhnhanRs;
	}

	public void setNHNhanRs(ResultSet nhnhanRs){
		this.nhnhanRs = nhnhanRs;
	}

	public ResultSet getNHTrichphiRs(){
		return this.nhtrichphiRs;
	}

	public void setNHTrichphiRs(ResultSet nhtrichphiRs){
		this.nhtrichphiRs = nhtrichphiRs;
	}

	public String getMahoadon() 
	{
		return this.mahoadon;
	}

	public void setMahoadon(String mahoadon)
	{
		this.mahoadon = mahoadon;
	}

	public String getMauhoadon() 
	{
		return this.mauhoadon;
	}

	public void setMauhoadon(String mauhoadon)
	{
		this.mauhoadon = mauhoadon;
	}

	public String getKyhieu() 
	{
		return this.kyhieu;
	}

	public void setKyhieu(String kyhieu)
	{
		this.kyhieu = kyhieu;
	}

	public String getSohoadon() 
	{
		return this.sohoadon;
	}

	public void setSohoadon(String sohoadon)
	{
		this.sohoadon = sohoadon;
	}

	public String getNgayhoadon() 
	{
		return this.ngayhoadon;
	}

	public void setNgayhoadon(String ngayhoadon)
	{
		this.ngayhoadon = ngayhoadon;
	}

	public String getTenNH_VAT() 
	{
		return this.tenNH_VAT;
	}

	public void setTenNH_VAT(String tenNH_VAT)
	{
		this.tenNH_VAT = tenNH_VAT;
	}

	public String getMST() 
	{
		return this.mst;
	}

	public void setMST(String mst)
	{
		this.mst = mst;
	}

	public void init(){
		
		String query; 
		query = 	"SELECT DC.PK_SEQ AS DCID, DC.SOCHUNGTU, DC.NGAYCHUNGTU, TT.PK_SEQ AS TTID, TT.MA AS TIENTE, ISNULL(DC.TIGIA, 1) AS TIGIA, \n" + 
					"		DC.NGANHANGCHUYEN_FK, DC.NGANHANGNHAN_FK, ISNULL(DC.GHICHU,'') AS GHICHU,  \n" +
					"		ISNULL(DC.SOTIENNT, 0) AS SOTIENNT, ISNULL(DC.SOTIENVND, 0) AS SOTIENVND, DC.TRICHPHI, \n" +
					"		NHTRICHPHI_FK, ISNULL(PHINT, 0) AS PHINT, ISNULL(PHIVND, 0) AS PHIVND, ISNULL(VATNT, 0) AS VATNT, ISNULL(VATVND, 0) AS VATVND, \n" +
					"		ISNULL(DC.MAHOADON, '') AS MAHOADON, ISNULL(DC.MAUHOADON, '') MAUHOADON, ISNULL(DC.KYHIEU, '') KYHIEU, ISNULL(SOHOADON, '') AS SOHOADON, \n" +
					"		ISNULL(DC.NGAYHOADON, '') AS NGAYHOADON, ISNULL(TENNGANHANG, '') AS TENNGANHANG, ISNULL(MST, '') AS MST, \n"
					+ "     DC.NCC_FK, DC.NHKYQUY_FK, DC.MUAHANG_FK, DC.LOAI \n" + 
					"FROM ERP_DIEUCHUYENTIEN DC \n" +
					"	  INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = DC.TIENTE_FK \n" +
					"	  LEFT JOIN ERP_NGANHANG_CONGTY NH_CTY1 ON NH_CTY1.PK_SEQ = DC.NGANHANGCHUYEN_FK \n" +
					"	  LEFT JOIN ERP_NGANHANG NH1 ON NH1.PK_SEQ = NH_CTY1.NGANHANG_FK \n" +
					"     LEFT JOIN ERP_CHINHANH CN1 ON CN1.PK_SEQ = NH_CTY1.CHINHANH_FK \n" +
					"     LEFT JOIN ERP_NGANHANG_CONGTY NH_CTY2 ON NH_CTY2.PK_SEQ = DC.NGANHANGNHAN_FK \n" +
					"     LEFT JOIN ERP_NGANHANG NH2 ON NH2.PK_SEQ = NH_CTY2.NGANHANG_FK \n" +
					"     LEFT JOIN ERP_CHINHANH CN2 ON CN2.PK_SEQ = NH_CTY2.CHINHANH_FK \n" +
					"     LEFT JOIN NHANVIEN NV1 ON NV1.PK_SEQ = DC.NGUOISUA \n" +
					"WHERE DC.PK_SEQ = " + this.Id + " \n";
		System.out.println("cau lenh init dieu chuyen tien:\n" + query + "\n=============================================");
		
		ResultSet rs = this.db.get(query);
		if(rs != null){
			try{
				rs.next();
				this.sochungtu = rs.getString("SOCHUNGTU");
				this.ngaychungtu = rs.getString("NGAYCHUNGTU");
				this.ttId = rs.getString("TTID");
				this.tigia = rs.getString("TIGIA");
				this.nhchuyenId = rs.getString("NGANHANGCHUYEN_FK");
				this.nhnhanId = rs.getString("NGANHANGNHAN_FK");
				this.sotienNT = rs.getString("SOTIENNT");
				this.sotienVND = rs.getString("SOTIENVND");
				this.trichphi = rs.getString("TRICHPHI");
				this.nhtrichphiId = rs.getString("NHTRICHPHI_FK");
				this.phiNT = rs.getString("PHINT");
				this.phiVND = rs.getString("PHIVND");
				this.thueNT = rs.getString("VATNT");
				this.thueVND = rs.getString("VATVND");
				this.mahoadon = rs.getString("MAHOADON");
				this.mauhoadon = rs.getString("MAUHOADON");
				this.kyhieu = rs.getString("KYHIEU");
				this.sohoadon = rs.getString("SOHOADON");
				this.ngayhoadon = rs.getString("NGAYHOADON");
				this.tenNH_VAT = rs.getString("TENNGANHANG");
				this.mst = rs.getString("MST");
				this.ghichu = rs.getString("GHICHU");
				
				this.loaidc = rs.getString("LOAI");
				this.nccId = rs.getString("NCC_FK")== null ? "":rs.getString("NCC_FK");
				this.nhkyquyId = rs.getString("NHKYQUY_FK")== null ? "":rs.getString("NHKYQUY_FK");
				this.POId = rs.getString("MUAHANG_FK")== null ? "":rs.getString("MUAHANG_FK");
				
				rs.close();
				
				if(this.trichphi.equals("1")){
					if(this.nhtrichphiId != null){
						query = 	"SELECT NH.MA + ' - ' + CN.MA + ' [' + TT.MA + ']' AS TENNH_VAT, \n" +
								    "       ISNULL(NH_CTY.MASOTHUE, '') AS MASOTHUE \n" + 
									"FROM ERP_NGANHANG_CONGTY NH_CTY \n" +
									"     LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK \n" +
									"     LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK \n" +
									"     INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK \n" +
									"WHERE NH_CTY.PK_SEQ = " + this.nhtrichphiId + "\n";
						
						System.out.println("query: " + query);
						rs = this.db.get(query);
						if(rs != null){
							try{
								rs.next();
								this.tenNH_VAT = rs.getString("TENNH_VAT");
								this.mst = rs.getString("MASOTHUE");
								rs.close();
							}catch(java.sql.SQLException e){
								e.printStackTrace();
							}
						}
					}else{
						this.tenNH_VAT = "";
					}
				}

				if(this.tenNH_VAT.length() == 0){
					
				}
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
		}
		
		createRs();
	}
	
	public void createRs(){
		String query;
		
		query = "SELECT PK_SEQ AS TTID, MA + ' - ' + TEN AS TIENTE " + 
				"FROM ERP_TIENTE ";	
		this.ttRs = this.db.get(query);
		
		
		query = "SELECT PK_SEQ , MA + ' - ' + TEN AS TEN " + 
				"FROM ERP_DOITUONGKYQUY "+
				"WHERE TRANGTHAI = 1 ";	
		this.nhkyquyRs = this.db.get(query);

		query = "SELECT PK_SEQ , MA + ' - ' + TEN AS TEN " + 
				"FROM ERP_NHACUNGCAP "+
				"WHERE TRANGTHAI = 1 and duyet = '1'\n";	
		this.nccRs = this.db.get(query);
		
		if(this.nccId.trim().length() > 0 && this.loaidc.equals("1"))
		{
			query = " select a.PK_SEQ , cast(a.PK_SEQ  as nvarchar(50) ) + ' - ' + a.ngaymua as dmhId , ISNULL(DUYET.DUYET,0) AS DUYET,ISNULL(tt.ma, 'NA') as tiente \n"+
					" from erp_muahang a left join ERP_TIENTE tt on a.tiente_fk = tt.pk_seq left join \n "+ 
					"	(SELECT 	MUAHANG_FK AS DMHID, CASE WHEN SUM(QUYETDINH) > 0 THEN \n"+ 
					"	(CASE WHEN ( SELECT SUM(TRANGTHAI) FROM ERP_DUYETMUAHANG WHERE MUAHANG_FK = DUYETMUAHANG.MUAHANG_FK AND QUYETDINH = 1) > 0 THEN 0 ELSE 1 END) \n"+
					"	 ELSE COUNT(TRANGTHAI) - SUM(TRANGTHAI) 	END AS DUYET FROM ERP_DUYETMUAHANG DUYETMUAHANG GROUP BY MUAHANG_FK \n"+
					"	)DUYET ON DUYET.DMHID = A.PK_SEQ \n"+ 
					" WHERE DUYET.DUYET = 0 and a.TRANGTHAI in (1,2)  and a.nhacungcap_fk='"+this.nccId+"'    \n";
			
			if(this.ttId.trim().length() > 0)
			{
				query += " and a.TIENTE_FK = '"+ this.ttId +"'";
			}
			System.out.println("Lấy PO :"+query);
			this.PORs = this.db.get(query);	
		}
		

		query = "SELECT NH_CTY.PK_SEQ AS NHCTYID, ISNULL(NH_CTY.SOTAIKHOAN + ' - ' + NH.MA + ' - ' + CN.TEN + ' [' + TT.MA + ']', N'Tiền mặt') AS NGANHANG  \n " +
				"FROM ERP_NGANHANG_CONGTY NH_CTY  \n " +
				"LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK  \n " +
				"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK  \n " +
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK  \n " +
				"WHERE NH_CTY.TRANGTHAI = 1   \n " +
				"AND NH_CTY.SOTAIKHOAN + ' - ' + NH.MA + ' - ' + CN.TEN + ' [' + TT.MA + ']' IS NOT NULL \n " ;
		
		if(this.ttId.length() > 0){
			query += " AND TT.PK_SEQ = " + this.ttId + " ";
		}
		this.nhchuyenRs = this.db.get(query);
		
		query = "SELECT NH_CTY.PK_SEQ AS NHCTYID,NH_CTY.SOTAIKHOAN + ' - ' + NH.MA + ' - ' + CN.TEN + ' [' + TT.MA + ']' AS NGANHANG \n " +
				"FROM ERP_NGANHANG_CONGTY NH_CTY  \n " +
				"LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK \n " +
				"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK \n " +
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK \n " +
				"WHERE NH_CTY.TRANGTHAI = 1 \n " +
				"AND NH_CTY.SOTAIKHOAN + ' - ' + NH.MA + ' - ' + CN.TEN + ' [' + TT.MA + ']' IS NOT NULL \n " ;
		
		if(this.nhchuyenId.length() > 0){
			query += "AND NH_CTY.PK_SEQ <> " + this.nhchuyenId + "";
		}

		if(this.ttId.length() > 0){
			query += " AND TT.PK_SEQ = " + this.ttId + " ";
		}
		System.out.println("NH Nhận: " + query);
		this.nhnhanRs = this.db.get(query);
			
		query = "SELECT NH_CTY.PK_SEQ AS NHCTYID, NH_CTY.SOTAIKHOAN + ' - ' +  NH.MA + ' - ' + CN.TEN + ' [' + TT.MA + ']' AS NGANHANG " +
				"FROM ERP_NGANHANG_CONGTY NH_CTY " +
				"LEFT JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
				"LEFT JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " +
				"WHERE NH_CTY.TRANGTHAI = 1 ";
		
//Trich phi VND		
	/**
		if(this.trichphi.equals("1")){ 
			
			query += " AND TT.PK_SEQ = '100000' ";
			
		}else{
			if(this.ttId.length() > 0){
			query += " AND TT.PK_SEQ = " + this.ttId + " ";
			}
		}
		
		System.out.println("Trích phí: " + query);
		this.nhtrichphiRs = this.db.get(query);

	}
	*/
	}
	
	public boolean Taodieutien(){
		
		if(this.nccId.trim().length() <= 0) this.nccId = "NULL";
		if(this.POId.trim().length() <= 0) this.POId = "NULL";
		if(this.nhkyquyId.trim().length() <= 0) this.nhkyquyId = "NULL";
		
		String query = 	"INSERT ERP_DIEUCHUYENTIEN(SOCHUNGTU, NGAYCHUNGTU, TIENTE_FK, TIGIA, NGANHANGCHUYEN_FK, NGANHANGNHAN_FK, \n" +
						"SOTIENNT, SOTIENVND, TRICHPHI, NHTRICHPHI_FK, PHINT, PHIVND, VATNT, VATVND, TRANGTHAI, CONGTY_FK, NGUOISUA, NGAYSUA, \n" +
						"MAHOADON, MAUHOADON, KYHIEU, SOHOADON, NGAYHOADON, TENNGANHANG, MST, GHICHU, \n" +
						"NCC_FK, MUAHANG_FK, NHKYQUY_FK, LOAI ) \n"+
						"VALUES(N'" + this.sochungtu + "', '" + this.ngaychungtu + "', " + this.ttId + ", " + this.tigia.replaceAll(",", "") + ", " + this.nhchuyenId + ", " + this.nhnhanId + ", " +
						"" + this.sotienNT.replaceAll(",", "") + ", " + this.sotienVND.replaceAll(",", "") + ", " + "0" + ", " + "0" + ", " + "0"  + ", " +
						"" + "0 ," + "0 ," + "0 ," + "0 "+","+  this.congtyId +","+ this.userId +",'"+ this.getDateTime() +"' ," +
						"N'" + this.mahoadon + "', N'" + this.mauhoadon + "', N'" + this.kyhieu + "', N'" + this.sohoadon + "', N'" + this.ngayhoadon + "', N'" + this.tenNH_VAT + "', N'" + this.mst + "', N'"+ this.ghichu +"',"
					 + " "+ this.nccId +", "+ this.POId +", "+ this.nhkyquyId +", '"+ this.loaidc +"' )";
		
		System.out.println("asdasda"+query);
		
		if(!this.db.update(query)){
			return false;
		}
		
		query = "SELECT SCOPE_IDENTITY() AS ID";
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			this.Id = rs.getString("ID");
			rs.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean Capnhatdieutien()
	{
		if(this.nccId.trim().length() <= 0) this.nccId = "NULL";
		if(this.POId.trim().length() <= 0) this.POId = "NULL";
		if(this.nhkyquyId.trim().length() <= 0) this.nhkyquyId = "NULL";
		
		String query = 	"UPDATE ERP_DIEUCHUYENTIEN SET SOCHUNGTU = N'" + this.sochungtu + "', NGAYCHUNGTU = '" + this.ngaychungtu + "', TIENTE_FK = " + this.ttId + " , GHICHU = N'"+ this.ghichu +"', " +
						"TIGIA = " + this.tigia.replaceAll(",", "") + ", NGANHANGCHUYEN_FK = " + this.nhchuyenId + ", NGANHANGNHAN_FK = " + this.nhnhanId + ", SOTIENNT = " + this.sotienNT.replaceAll(",", "") + ", " +
						"SOTIENVND = " + this.sotienVND.replaceAll(",", "") + ", TRICHPHI = " + this.trichphi + ", NHTRICHPHI_FK = " + this.nhtrichphiId + ", PHINT = " + this.phiNT.replaceAll(",", "") + ", " +
						"PHIVND = " + this.phiVND.replaceAll(",", "") + ", VATNT = " + this.thueNT.replaceAll(",", "") + ", VATVND = " + this.thueVND.replaceAll(",", "") + ", TRANGTHAI = '0', CONGTY_FK = " + this.congtyId + ", NGUOISUA = " + this.userId + ", NGAYSUA = '" + this.getDateTime() + "', " +
						"MAHOADON = N'" + this.mahoadon + "', MAUHOADON = N'" + this.mauhoadon + "', KYHIEU = N'" + this.kyhieu + "', SOHOADON = N'" + this.sohoadon + "', NGAYHOADON = N'" + this.ngayhoadon + "', TENNGANHANG = N'" + this.tenNH_VAT + "', MST = N'" + this.mst + "', "
					  + "NCC_FK = "+ this.nccId +", MUAHANG_FK = "+ this.POId +", NHKYQUY_FK = "+ this.nhkyquyId +", LOAI = '"+ this.loaidc +"' " +
						"WHERE PK_SEQ = " + this.Id + "";
		
		System.out.println(query);
		
		if(!this.db.update(query)){
			return false;
		}
		
		return true;
	}	

	public boolean Chot(){
		
		dbutils db= new dbutils();
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE ERP_DIEUCHUYENTIEN SET TRANGTHAI = 1 WHERE PK_SEQ = " + this.Id + "";
			if(!db.update(query))
			{
				this.msg = "C1.1 Khong the chot ERP_DIEUCHUYENTIEN: " + query;
				db.getConnection().rollback();
				return false;
			}
		
			//PHẦN ĐỊNH KHOẢN KẾ TOÁN
			query = " select ISNULL(dct.sochungtu,'') as sochungtu, ISNULL(dct.ghichu,'') as diengiai, dct.NGAYCHUNGTU, ISNULL(dct.LOAI,0) as loaidc, dct.SOTIENVND,dct.PHIVND, dct.VATVND, \n " +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NGANHANGCHUYEN_FK) as NHCHUYENID,  \n " +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NGANHANGNHAN_FK)as NHNHANID,  \n " +
					"  (select TEN from ERP_DOITUONGKYQUY where PK_SEQ = dct.NHKYQUY_FK)as NHKYQUYID,  \n " +
					"  (select NGANHANG_FK from ERP_NGANHANG_CONGTY where PK_SEQ = dct.NHTRICHPHI_FK)as NHTRICHPHIID,  \n " +
					
			//		"  CASE WHEN dct.NGANHANGCHUYEN_FK IS NOT NULL THEN   \n " +
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NGANHANGCHUYEN_FK) as taikhoan_NHChuyen,  \n "+
			//		"  ELSE \n " +
			//		" (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = " + this.congtyId + " ) \n " +
			//		"  END AS taikhoan_NHChuyen \n " +
					
			//		"  CASE WHEN dct.NGANHANGNHAN_FK IS NOT NULL THEN   \n " +
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NGANHANGNHAN_FK) AS taikhoan_NHNhan, \n  " +
			//		"  ELSE  \n " +
			//		"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '11110000' AND CONGTY_FK = " + this.congtyId + " )  \n " +
			//		"  END AS taikhoan_NHNhan, \n " +
					
					"  (select TaiKhoanKT_FK from ERP_DOITUONGKYQUY where PK_SEQ=dct.NHKYQUY_FK) as taikhoan_NHKyquy,  \n "+
					"  (select TaiKhoan_FK from ERP_NGANHANG_CONGTY where PK_SEQ=dct.NHTRICHPHI_FK) as taikhoanCO_NHTP,  \n "+
					"  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN= '64251000' and TRANGTHAI = 1 and CONGTY_FK = " + this.congtyId + ") as taikhoanNO_PHI,  \n "+
					"  (select PK_SEQ from ERP_TAIKHOANKT where SOHIEUTAIKHOAN= '13311000' and TRANGTHAI = 1 and CONGTY_FK = " + this.congtyId + ") as taikhoanNO_VAT  \n "+
					" from ERP_DIEUCHUYENTIEN dct  \n "+
					" where PK_SEQ= "+ this.Id +" ";
			ResultSet rs = db.get(query);
			System.out.println("CAU QUERY KT \n" + query + "\n----------------------------------------------------------");
			if(rs!= null)
			{
				String NHChuyenId= "";
				String NHNhanId = "";
//				String NHKyquyId = "";
				String NHTrichphi= "";
				String taikhoanNO_fk="";
				String taikhoanCO_fk="";
				String maChungTu = "";
				
				
				while(rs.next())
				{
					
				  String diengiai=	 rs.getString("diengiai");
				  
			      NHChuyenId= rs.getString("NHCHUYENID")== null ? "":rs.getString("NHCHUYENID") ;
			      NHNhanId= rs.getString("NHNHANID")== null ? "": rs.getString("NHNHANID");
//			      NHKyquyId= rs.getString("NHKYQUYID")== null ? "": rs.getString("NHKYQUYID");
			      NHTrichphi = rs.getString("NHTRICHPHIID")== null ? "":  rs.getString("NHTRICHPHIID");
			      maChungTu = rs.getString("SOCHUNGTU");
				  String nam = rs.getString("NGAYCHUNGTU").substring(0, 4);
				  String thang = rs.getString("NGAYCHUNGTU").substring(5, 7);
				  
				  String madoituongno = NHNhanId;
				  
			      //GHI NHAN SO TIEN DIEU CHUYEN
			      double sotien= rs.getDouble("SOTIENVND");
			      if(sotien > 0)
			      {
			    	  if(nhkyquyId.trim().length() > 0)
			    	  {
			    		  taikhoanNO_fk= rs.getString("taikhoan_NHKyquy")== null ? "":rs.getString("taikhoan_NHKyquy") ;
			    		  madoituongno = nhkyquyId;
			    	  }else{
			    		  taikhoanNO_fk= rs.getString("taikhoan_NHNhan")== null ? "":rs.getString("taikhoan_NHNhan") ;
			    	  }
			    	  
			    	  taikhoanCO_fk = rs.getString("taikhoan_NHChuyen")== null ? "":rs.getString("taikhoan_NHChuyen");
			    	  
			    	  if(taikhoanCO_fk.trim().length() < 0 || taikhoanNO_fk.trim().length() < 0)
			    	  {
			    		  this.msg = "C1.2 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại dữ liệu nền";
			    		  db.getConnection().rollback();
			    		  return false;
			    	  }
			    	  this.msg = util.Update_TaiKhoan_DienGiai(db, thang, nam, rs.getString("NGAYCHUNGTU"), rs.getString("NGAYCHUNGTU"),"Điều chuyển tiền", this.Id, taikhoanNO_fk, taikhoanCO_fk, "",
			    			  Double.toString(sotien), Double.toString(sotien), "Ngân hàng", madoituongno, "Ngân hàng", NHChuyenId, "0", "", "", "100000", "", "1", Double.toString(sotien), Double.toString(sotien), "Số tiền điều chuyển",diengiai,maChungTu);
			     
			    	  if(this.msg.length() >0)
			    	  {
			    		  this.msg = "C1.3 " + this.msg;
			    		  rs.close();
			    		  db.getConnection().rollback();
			    		  return false;
			    	  }
			      }
			      //GHI NHAN PHI 
			      double phi= rs.getDouble("PHIVND");
			      if(phi > 0)
			      {
			    	  taikhoanNO_fk= rs.getString("taikhoanNO_PHI");
			    	  taikhoanCO_fk = rs.getString("taikhoanCO_NHTP");
			    	  
			    	  if(taikhoanNO_fk == null || taikhoanCO_fk.trim().length() < 0 || taikhoanNO_fk == null || taikhoanNO_fk.trim().length() < 0)
			    	  {
			    		  this.msg = "C1.4 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại dữ liệu nền";
			    		  db.getConnection().rollback();
			    		  return false;
			    	  }
			    	  this.msg = util.Update_TaiKhoan_DienGiai(db, thang, nam, rs.getString("NGAYCHUNGTU"), rs.getString("NGAYCHUNGTU"),"Điều chuyển tiền", this.Id, taikhoanNO_fk, taikhoanCO_fk, "",
			    			  Double.toString(phi), Double.toString(phi), "", "", "Ngân hàng", NHTrichphi, "0", "", "", "100000", "", "1", Double.toString(phi), Double.toString(phi), "",diengiai,maChungTu);
			     
			    	  if(this.msg.length() >0)
			    	  {
			    		  this.msg = "C1.5 " + this.msg;
			    		  rs.close();
			    		  db.getConnection().rollback();
			    		  return false;
			    	  }
			      }
			      //GHI NHAN VAT
			      double vat= rs.getDouble("VATVND");
			      if(vat > 0)
			      {
			    	  taikhoanNO_fk= rs.getString("taikhoanNO_VAT");
			    	  taikhoanCO_fk = rs.getString("taikhoanCO_NHTP");
			    	  
			    	  if(taikhoanCO_fk.trim().length() < 0 || taikhoanNO_fk.trim().length() < 0)
			    	  {
			    		  this.msg = "C1.6 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại dữ liệu nền";
			    		  db.getConnection().rollback();
			    		  return false;
			    	  }
			    	  this.msg = util.Update_TaiKhoan_Diengiai(db, thang, nam, rs.getString("NGAYCHUNGTU"), rs.getString("NGAYCHUNGTU"),"Điều chuyển tiền", this.Id, taikhoanNO_fk, taikhoanCO_fk, "",
			    			  Double.toString(vat), Double.toString(vat), "", "", "Ngân hàng", NHTrichphi, "0", "", "", "100000", "", "1", Double.toString(vat), Double.toString(vat), "",diengiai);
			     
			    	  if(this.msg.length() >0)
			    	  {
			    		  this.msg = "C1.7 " + this.msg;
			    		  rs.close();
			    		  db.getConnection().rollback();
			    		  return false;
			    	  }
				}				
			}
			rs.close();
		  }
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}catch(java.sql.SQLException e){
			e.printStackTrace();
			try 
			{
				db.getConnection().rollback();
				this.msg = "C1.8 Lỗi khi chốt điều chuyển tiền: " + e.getMessage();
			}
			catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void DBclose() 
	{
		try{
			if(ttRs != null) this.ttRs.close();
			if(nhchuyenRs != null) this.nhchuyenRs.close();
			if(nhnhanRs != null) this.nhnhanRs.close();
			if(nhtrichphiRs != null) nhtrichphiRs.close();
			if(nhkyquyRs != null) nhkyquyRs.close();
			if(nccRs != null) nccRs.close();
			if(PORs != null) PORs.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		this.db.shutDown();
	}


	public String getLoaidc()
	{
		return this.loaidc ;
	}


	public void setLoaidc(String loaidc) 
	{
		this.loaidc = loaidc ;
	}


	public String getNccId() 
	{
		return this.nccId ;
	}

	public void setNccId(String nccId) 
	{
		this.nccId = nccId;
	}

	public ResultSet getNccRs() 
	{
		return this.nccRs ;
	}

	public void setNccRs(ResultSet nccRs) 
	{
		this.nccRs = nccRs ;
	}


	public String getPOId() 
	{
        
		return this.POId ;
	}


	public void setPOId(String POId) 
	{
		this.POId = POId ;
		
	}

	public ResultSet getPORs() 
	{
		return this.PORs ;
	}


	public void setPORs(ResultSet PORs) 
	{
		this.PORs = PORs ;
	}

	public String getNhKyquyId() 
	{
		return this.nhkyquyId ;
	}

	public void setNhKyquyId(String nhkyquyId) 
	{
		this.nhkyquyId = nhkyquyId ;
	}

	public ResultSet getNHKyquyRs() 
	{
		return this.nhkyquyRs ;
	}

	public void setNHKyquyRs(ResultSet nhkyquyRs) 
	{
		this.nhkyquyRs = nhkyquyRs ;
	}

}

