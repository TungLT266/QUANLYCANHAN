package geso.traphaco.erp.beans.lapkehoach.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.erp.beans.lapkehoach.IErpLenhmuahangdkList;
import geso.traphaco.erp.db.sql.dbutils;

public class ErpLenhmuahangdkList implements IErpLenhmuahangdkList 
{
	String ctyId;
	String userId;
	String tungay;
	String denngay;
	String trangthai; 
	String nam;
	String thang;
	String diengiai;
	String msg;
	String loai;
	ResultSet nccRs;
	ResultSet mhdkRs;
	ResultSet sanphamRS;
	ResultSet nmRS;
	String nmId;
	String spId;
	dbutils db;
	
	public ErpLenhmuahangdkList()
	{
		this.ctyId = "";
		this.userId = "";
		this.tungay = "";
		this.denngay = "";
		this.trangthai = "";
		this.diengiai = "";
		this.msg = "";
		this.loai = "100000";
		this.nmId = "";
		this.spId = "";
		this.nam = this.getDateTime().substring(0, 4);
		this.thang = "" + (Integer.parseInt(this.getDateTime().substring(5, 7)));
		this.db = new dbutils();
	}
	
	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
	}
	
	public String getNam() 
	{
		return this.nam;
	}

	public void setNam(String nam) 
	{
		this.nam = nam;	
	}

	public String getThang() 
	{
		return this.thang;
	}

	public void setThang(String thang) 
	{
		this.thang = thang;	
	}

	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;	
	}

	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getTungay()
	{
		return this.tungay;
	}

	public void setTungay(String tungay) 
	{
		this.tungay = tungay;
	}

	public String getDenngay()
	{
		return this.denngay;
	}

	public void setDenngay(String denngay)
	{
		this.denngay = denngay;
	}

	public String getTrangthai() 
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai) 
	{
		this.trangthai = trangthai;
	}

	public String getDiengiai() 
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai) 
	{
		this.diengiai = diengiai;
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public void init() 
	{
		try
		{
			String query = 	"SELECT SP.PK_SEQ, SP.TEN " +
							"FROM ERP_SANPHAM SP " +
							"WHERE TRANGTHAI = '1' AND CONGTY_FK = " + this.ctyId + " " +
							"AND LOAISANPHAM_FK IN (100000, 100008, 100013)";

			this.sanphamRS = this.db.get( query );

			query = 	"SELECT PK_SEQ AS NMID, MANHAMAY + ' - ' + TENNHAMAY AS TEN \n " +
						"FROM ERP_NHAMAYTONG WHERE TRANGTHAI = 1 AND CONGTY_FK = " + this.ctyId + " ";

			this.nmRS = this.db.get( query );

			//Câu này bị double khi mua hàng nhiều lần
			/*query = 	"SELECT DMHDK.PK_SEQ AS DMHID, SP.PK_SEQ AS SPID, SP.MA, SP.TEN, " +
						"DMHDK.SOLUONG AS SOLUONG, ISNULL(DMHDK.SOLUONGTT, 0) AS SOLUONGTT, ISNULL(DMHDK.SANPHAMTT_FK, 0) AS VTTTID, \n " +
						"ISNULL(SPTT.MA, '') AS MATT, ISNULL(SPTT.TEN, '') AS TENTT, \n " +
						"DMHDK.NGAYDATHANG, DMHDK.NGAYNHANHANG, YC.NAM, YC.THANG, YC.PK_SEQ AS ID, ISNULL(MHSP.SOLUONG, 0) +  ISNULL(DMHDK.SOLUONGTT, 0) AS DADATHANG \n " +
						"\n FROM ERP_YEUCAUNHANNGUYENLIEU YC  \n " +
						"\n INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = YC.SANPHAM_FK \n " +
						"\n INNER JOIN ERP_DONMUAHANGDUKIEN DMHDK ON DMHDK.YEUCAUNHANNGUYENLIEU_FK = YC.PK_SEQ \n " +
						"\n LEFT JOIN ERP_MUAHANG MH ON MH.SOTHAMCHIEU = CONVERT(VARCHAR, DMHDK.PK_SEQ) \n " +
						"\n LEFT JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ AND MHSP.SANPHAM_FK = DMHDK.SANPHAM_FK \n " +
						"\n LEFT JOIN ERP_SANPHAM SPTT ON SPTT.PK_SEQ = DMHDK.SANPHAMTT_FK \n " +
						"\n WHERE CONVERT(INT, SUBSTRING(DMHDK.NGAYNHANHANG, 6, 2)) = " + this.thang + " \n " +
						" AND SUBSTRING(DMHDK.NGAYNHANHANG, 1, 4) = " + this.nam + " \n " ;*/
			
			
			query =  "\nSELECT DMHDK.PK_SEQ AS DMHID, SP.PK_SEQ AS SPID, SP.MA, SP.TEN, isnull(DMHDK.SOLUONG_YC, DMHDK.SOLUONG) AS SOLUONG, DMHDK.HAMAM_YC as HAMAM, DMHDK.HAMLUONG_YC as HAMLUONG, ISNULL(DMHDK.SOLUONGTT, 0) AS SOLUONGTT, ISNULL(DMHDK.SANPHAMTT_FK, 0) AS VTTTID, " + 
					 "\n	ISNULL(SPTT.MA, '') AS MATT, ISNULL(SPTT.TEN, '') AS TENTT, " + 
					 "\n	DMHDK.NGAYDATHANG, DMHDK.NGAYNHANHANG, YC.NAM, YC.THANG, YC.PK_SEQ AS ID, " + 
					 "\n	ISNULL( (  " + 
					 "\n		select sum( MHSP.soluong ) " + 
					 "\n		from ERP_MUAHANG MH " + 
					 "\n			LEFT JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ " + 
					 "\n		where MH.SOTHAMCHIEU = CONVERT(VARCHAR, DMHDK.PK_SEQ) AND MHSP.SANPHAM_FK = DMHDK.SANPHAM_FK ), 0 ) +  ISNULL(DMHDK.SOLUONGTT, 0) AS DADATHANG " + 
					 "\nFROM ERP_YEUCAUNHANNGUYENLIEU YC  " + 
					 "\n	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = YC.SANPHAM_FK " + 
					 "\n	INNER JOIN ERP_DONMUAHANGDUKIEN DMHDK ON DMHDK.YEUCAUNHANNGUYENLIEU_FK = YC.PK_SEQ " + 
					 "\n	LEFT JOIN ERP_SANPHAM SPTT ON SPTT.PK_SEQ = DMHDK.SANPHAMTT_FK " + 
					 "\nWHERE CONVERT(INT, SUBSTRING(DMHDK.NGAYNHANHANG, 6, 2)) =  " + this.thang + 
					 "\n	AND SUBSTRING(DMHDK.NGAYNHANHANG, 1, 4) =  " + this.nam;
			
			if(this.loai.length() > 0){
				query += " AND SP.LOAISANPHAM_FK = " + this.loai + " \n ";
			}
			
			
			if(this.spId.length() > 0){
				query += " AND SP.PK_SEQ = " + spId + " \n ";
			}
			
			if(this.nmId.length() > 0){
				//query += " AND YC.NHAMAY_FK = " + this.nmId + " ";
				query += " AND YC.NHAMAY_FK in ( select pk_seq from ERP_NHAMAY where NHAMAYTONG_FK = '" + this.nmId + "' ) ";
			}
			
			System.out.println(":::: LENH MH DU KIEN: " + query);
			this.mhdkRs = this.db.get(query);
		}
		catch(Exception e)
		{
			e.printStackTrace();
			System.out.println("Exception YC: " + e.getMessage() );
		}
	}

	public ResultSet getMa_ket(String spId, String ngay)
	{
		String query = 
						"SELECT DISTINCT MK.PK_SEQ AS MKID, MK.MA + ' - ' + MK.DIENGIAI  AS MAKET, MK.TUNGAY, MK.DENNGAY, \n " +
						"ISNULL((SELECT SUM(SOLUONG) FROM ERP_KHOTT_SP_CHITIET WHERE IDMARQUETTE = MK.PK_SEQ AND SANPHAM_FK = " + spId + "), 0) AS TONHIENTAI \n " +
						"FROM ERP_SANPHAM SP 	 \n " +
						"INNER JOIN MARQUETTE MK ON MK.SANPHAM_FK = SP.PK_SEQ \n " +
						"WHERE SP.PK_SEQ = " + spId + " AND MK.TUNGAY <= '" + ngay + "' AND MK.DENNGAY >= '" + ngay + "' " +
						"ORDER BY MK.DENNGAY DESC ";

		return this.db.getScrol(query);
	}

	public ResultSet getNLThaythe(String spId){
		String query = 
						"SELECT DISTINCT VT_TT.VATTUTT_FK AS VTTTID, SP.MA + ' - ' + SP.TEN  AS TENTT \n " +
						
						"FROM ERP_SANPHAM SP 	 \n " +
						"INNER JOIN ERP_DANHMUCVATTU_VATTU_THAYTHE VT_TT ON SP.PK_SEQ = VT_TT.VATTUTT_FK \n " +
						"WHERE VT_TT.VATTU_FK = " + spId + " ";

		return this.db.getScrol(query);
	}
	public void delete(String id ){
		String query = "DELETE FROM ERP_MUANGUYENLIEUDUKIEN WHERE PK_SEQ = '" + id + "'";
		this.db.update(query);
	}

	public void DbClose() 
	{
		try 
		{
			if(this.mhdkRs != null)
				this.mhdkRs.close();
			this.db.shutDown();
		} 
		catch (SQLException e) {}
	}

	public ResultSet getLenhmuahangdkRs() 
	{
		return this.mhdkRs;
	}

	public void setLenhmuahangdkRs(ResultSet mhdkRs) 
	{
		this.mhdkRs = mhdkRs;
	}
	
	public String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}


	public ResultSet getNccRs() {

		return this.nccRs;
	}


	public void setNccRs(ResultSet nccRs) {
		
		this.nccRs = nccRs;
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;	
	}
	
	public ResultSet getSanphamRS(){
		return this.sanphamRS;
	}
	
	public void setSanphamRS(ResultSet spRS){
		this.sanphamRS = spRS;
	}

	public ResultSet getNhamayRS(){
		return this.nmRS;
	}
	
	public void setNhamayRS(ResultSet nmRS){
		this.nmRS = nmRS;
	}
	
	public String getNhamayId(){
		return this.nmId;
	}
	
	public void setNhamayId(String nmId){
		this.nmId = nmId;
	}
}
