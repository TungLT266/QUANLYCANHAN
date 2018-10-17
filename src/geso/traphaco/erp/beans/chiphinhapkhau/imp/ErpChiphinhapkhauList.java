package geso.traphaco.erp.beans.chiphinhapkhau.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import geso.traphaco.erp.beans.chiphinhapkhau.IErpChiphinhapkhauList;
import geso.traphaco.center.util.DinhKhoanKeToan;
import geso.traphaco.center.util.IDinhKhoanKeToan;
import geso.traphaco.center.util.IThongTinHienThi;
import geso.traphaco.center.util.ThongTinHienThi;
import geso.traphaco.center.db.sql.dbutils;

public class ErpChiphinhapkhauList implements IErpChiphinhapkhauList 
{
	String userId;
	String congtyId;
	String poId;
	String ncc;
	String nccId;
	
	String ma;
	String diengiai;
	String trangthai; 
	String msg;
	String Sotokhai;
	String tungay;
	String denngay;
	String nguoitaoId;
	String tusotien;
	String densotien;
	
	List<IThongTinHienThi> hienthiList;
	
	ResultSet cpnkRs;
	ResultSet nguoitaoRs;
	dbutils db;
	
	public ErpChiphinhapkhauList()
	{
		this.userId = "";
		this.poId = "";
		this.ncc = "";
		this.nccId = "";
		
		this.ma = "";
		this.trangthai = "";
		this.diengiai = "";
		
		this.msg = "";
		this.Sotokhai="";
		this.tungay = "";
		this.denngay = "";
		this.hienthiList = new ArrayList<IThongTinHienThi>();
		this.nguoitaoId="";
		this.tusotien = "";
		this.densotien = "";
		this.db = new dbutils();
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMa() 
	{
		return this.ma;
	}

	public void setMa(String ma) 
	{
		this.ma = ma;
	}
	

	public void setNcc(String ncc) 
	{
		
		this.ncc = ncc;
	}

	
	public String getNcc()
	{
		
		return this.ncc;
	}
	
	public void setNccId(String nccId) 
	{
		
		this.nccId = nccId;
	}

	
	public String getNccId()
	{
		
		return this.nccId;
	}

	public void setPoId(String poId) 
	{
		
		this.poId = poId;
	}

	
	public String getPoId()
	{
		
		return this.poId;
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
	
	public String getTusotien() {
		return tusotien;
	}

	public void setTusotien(String tusotien) {
		this.tusotien = tusotien;
	}

	public String getDensotien() {
		return densotien;
	}

	public void setDensotien(String densotien) {
		this.densotien = densotien;
	}

	private String LayDuLieu(String id) {
		String query ="";
		
		if(query.trim().length()<=0){
			query = "SELECT '' NO_CO, '' PK_SEQ, '' NGAYHOADON, '' SOTIEN, '' SOHIEUTAIKHOAN, '' DOITUONG, '' TRUNGTAMCHIPHI, " +
					"'' TRUNGTAMDOANHTHU, 1 STT, 1 SAPXEP \n " +
					"FROM ERP_THUENHAPKHAU \n " +
					"WHERE PK_SEQ = '"+id+"'";
		}
		System.out.println(query);		
		
		return query;
	}
	

	private boolean isUserDuyet() {
		String query = "SELECT PQ.NHANVIEN_FK FROM NHOMQUYEN NQ\r\n" + 
				"INNER JOIN PHANQUYEN PQ ON NQ.DMQ_FK = PQ.DMQ_FK\r\n" + 
				"INNER JOIN UNGDUNG UD ON UD.PK_SEQ=NQ.UNGDUNG_FK\r\n" + 
				"WHERE UD.SERVLET = 'ERPCHIPHINHAPKHAUSVL'  AND UD.TRANGTHAI = 1 AND\r\n" + 
				"PQ.NHANVIEN_FK = "+this.userId+" AND (CHOT = 1 OR CHUYEN = 1 OR HUYCHOT = 1)\r\n";
		ResultSet rs = db.get(query);
		try {
			if (rs.next()) {
				return true;
			} 
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
		
	}

	public void init(String query) 
	{
		String sql = "";
		
		if(query.length() > 0)
			sql = query;
		else
		{	
			sql =   " SELECT CP.PK_SEQ, CP.NGAY AS NGAYNHAP,  CP.GHICHU,CP.TRANGTHAI, B.TEN AS NGUOITAO, CP.NGAYTAO, C.TEN AS NGUOISUA, CP.NGAYSUA  , "+
					"  isnull(CP.tongtienavat,'0') as tongtienavat, " +
					" isnull( ( SELECT  ISNULL( TNK.SOCHUNGTU,'')   + ', ' AS [text()] " +
					" FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH  "+
					" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+
					" INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK "+  
					" WHERE CP_NH.CHIPHINHAPKHAU_FK=CP.PK_SEQ "+
					" for XML PATH ('') ),'')  AS SOTOKHAI  "+
					" FROM ERP_CHIPHINHAPKHAU CP " +
					" inner JOIN NHANVIEN B ON CP.NGUOITAO = B.PK_SEQ "+   
					" INNER JOIN NHANVIEN C ON CP.NGUOISUA = C.PK_SEQ    ";
		}
		if(poId.length() > 0){
			sql += " and CP.PK_SEQ  in ( "+
				   " SELECT CP_NH.CHIPHINHAPKHAU_FK "+
				   " FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH "+  
				   " INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+  
				   " INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SOTOKHAI_FK "+ 
				   " INNER JOIN ERP_THUENHAPKHAU_HOADONNCC TNKHD ON TNKHD.THUENHAPKHAU_FK=TNK.PK_SEQ "+
				   " INNER JOIN ERP_HOADONNCC_DONMUAHANG HDNCC_DMH ON HDNCC_DMH.HOADONNCC_FK=TNKHD.HOADONNCC_FK "+
				   " INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ=HDNCC_DMH.MUAHANG_FK "+
				   " WHERE MH.SOPO LIKE '%"+poId+"%') ";
		}
		
		if(nccId.length() > 0){
			sql += " and CP.PK_SEQ  in ( SELECT CP_NH.CHIPHINHAPKHAU_FK "+ 
			" FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH "+
			" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+
			" INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SoToKhai_fk "+
			" WHERE TNK.ncc_fk ="+nccId+" )";
		}
		if(tungay.length() > 0)
			sql += " and CP.NGAY >= '" + tungay + "'\n";
		
		if(denngay.length() > 0)
			sql += " and CP.NGAY <= '" + denngay + "'\n";

		if(this.diengiai.length() > 0)
			sql += " and CP.ghichu like N'%" + this.diengiai + "%' ";
		
		if(this.trangthai.length() > 0)
			sql += " and CP.trangthai = '" + this.trangthai + "' ";
		
		if(this.Sotokhai.length() >0){
			
			sql=sql +" and cp.pk_seq in ( SELECT CP_NH.CHIPHINHAPKHAU_FK "+ 
			" FROM ERP_CHIPHINHAPKHAU_NHANHANG CP_NH "+
			" INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ=CP_NH.NHANHANG_FK "+
			" INNER JOIN ERP_THUENHAPKHAU TNK ON TNK.PK_SEQ=NH.SoToKhai_fk "+
			" WHERE TNK.SOCHUNGTU LIKE N'%"+this.Sotokhai+"%' )";
		}
			if(this.nguoitaoId.length() >0){
				
				sql+=" and CP.NGUOITAO = '" + this.nguoitaoId + "' ";
		
			
			}
			
		if(this.tusotien.length() > 0){
			sql += " and CAST(CP.tongtienavat AS INT) >= " + this.tusotien ;
		}
		
		if(this.densotien.length() > 0){
			sql += " and CAST(CP.tongtienavat AS INT) <= " + this.densotien ;
		}
		
	
		 

		if (!isUserDuyet()) {
			sql += " AND CP.NGUOITAO = "+this.userId;
		}
		sql += " ORDER BY CP.PK_SEQ DESC";
		//this.cpnkRs = db.get(sql);
		
		System.out.println("Câu init "+sql);
		
		ResultSet rs = db.get(sql);
		this.nguoitaoRs = db.get("select pk_seq,ten from nhanvien   where trangthai = '1' ");
		List<IThongTinHienThi> htList = new ArrayList<IThongTinHienThi>();
		
		
		
		
		try
		{
			if(rs!= null)
			{
				IThongTinHienThi ht = null;
				while(rs.next())
				{			
					ht = new ThongTinHienThi();
					//LAY DINH KHOAN KE TOAN
					String dk = LayDuLieu(rs.getString("PK_SEQ"));					
					
					
					ResultSet rsKT = db.get(dk);
					List<IDinhKhoanKeToan> ktList = new ArrayList<IDinhKhoanKeToan>();
						if(rsKT!= null)
						{
							IDinhKhoanKeToan kt = null;
							while(rsKT.next())
							{
								kt = new DinhKhoanKeToan(rsKT.getString("NO_CO"), rsKT.getString("NO_CO"),rsKT.getString("SOHIEUTAIKHOAN"),
											rsKT.getString("SOTIEN"),rsKT.getString("DOITUONG"),
											rsKT.getString("TRUNGTAMCHIPHI"),rsKT.getString("TRUNGTAMDOANHTHU"), "");
								//System.out.println("Số tiền "+kt.getSotien());
								ktList.add(kt);
							}
							
							rsKT.close();
						}
												
					// INIT					
						
						ht.setId(rs.getString("PK_SEQ"));
						ht.setsotokhai(rs.getString("SOTOKHAI"));
						ht.setNgaychungtu(rs.getString("ngaynhap"));
						ht.setGhichu(rs.getString("ghichu"));
						ht.setTRANGTHAI(rs.getString("TRANGTHAI"));
						ht.setNGAYTAO(rs.getString("NGAYTAO"));
						ht.setNGUOITAO(rs.getString("NGUOITAO"));
						ht.setNGUOISUA(rs.getString("NGUOISUA"));
						ht.setNGAYSUA(rs.getString("NGAYSUA"));
						
						ht.setLayDinhkhoanKT(ktList);
					
					htList.add(ht);																	
				}
				rs.close();
			}
		
		
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
		this.hienthiList = htList;
		
		
		this.cpnkRs = db.get(sql);
		
	}

	public ResultSet getDinhkhoan(String Id){
		ResultSet rs ;
		
		String query = 	"SELECT CASE WHEN NO > 0 THEN N'NỢ' ELSE N'CÓ' END AS NOCO, TK.SOHIEUTAIKHOAN, \n" +
						"CASE WHEN NO > 0 THEN NO ELSE CO END AS SOTIEN, DOITUONG \n" +
						"FROM ERP_PHATSINHKETOAN PSKT \n" +
						"INNER JOIN ERP_TAIKHOANKT TK ON TK.PK_SEQ = PSKT.TAIKHOAN_FK \n" +
						"WHERE SOCHUNGTU = " + Id + " and loaiChungTu like N'Chi phí nhập khẩu'\n" ;
		//System.out.println("lay dinh khoan chi phi nhap khau:\n" + query + "\n---------------------------------------");
		rs = this.db.get(query);
		return rs;
	}

	public ResultSet getChiphinhapkhauRs() 
	{
		return this.cpnkRs;
	}

	public void setChiphinhapkhauRs(ResultSet cpnkRs) 
	{
		this.cpnkRs = cpnkRs;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	public void DbClose() 
	{
		try 
		{
			if(this.cpnkRs != null)
				this.cpnkRs.close();
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		if (this.db != null)
			this.db.shutDown();
	}

	
	public String getSotokhai() {
		
		return this.Sotokhai;
	}

	
	public void setSotokhai(String Sotokhai) {
		
		this.Sotokhai=Sotokhai;
	}

	
	public List<IThongTinHienThi> getHienthiList() {
		
		return this.hienthiList;
	}

	
	public void setHienthiList(List<IThongTinHienThi> hienthiList) {
		
		this.hienthiList = hienthiList;
	}

	public String getTungay() {
		return tungay;
	}

	public void setTungay(String tungay) {
		this.tungay = tungay;
	}

	public String getDenngay() {
		return denngay;
	}

	public void setDenngay(String denngay) {
		this.denngay = denngay;
	}

	public ResultSet getNguoitaoRs() {
		return nguoitaoRs;
	}

	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		this.nguoitaoRs = nguoitaoRs;
	}

	public String getNguoitaoId() {
		return nguoitaoId;
	}

	public void setNguoitaoId(String nguoitaoId) {
		this.nguoitaoId = nguoitaoId;
	}

}
