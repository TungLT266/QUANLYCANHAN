package geso.traphaco.erp.beans.taikhoankt.imp;

import java.sql.ResultSet;
import java.sql.SQLException;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.taikhoankt.ITaikhoanktList;
import geso.traphaco.erp.util.UtilitySyn;

public class TaikhoanktList  extends Phan_Trang implements ITaikhoanktList
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3755729915162071408L;
	String Id, UserId, SoHieuTaiKhoan, TenTaiKhoan, CongTyId, LoaiTaiKhoanId, TrangThai, TaiKhoanCoChiTiet,
	TaiKhoanCoChiPhi, msg;
	dbutils db;
	ResultSet CongTyRs, LoaiTaiKhoanRs,TaiKhoanRs;
	
	private int num;
	private int[] listPages;
	private int currentPages;
	
	String chixem;
	
	public TaikhoanktList()
	{
		this.db = new dbutils();
		this.UserId = "";
		this.SoHieuTaiKhoan = "";
		this.TenTaiKhoan = "";
		this.CongTyId = "";
		this.LoaiTaiKhoanId = "";
		this.TrangThai = "";
		this.TaiKhoanCoChiPhi = "";
		this.TaiKhoanCoChiTiet = "";
		this.msg = "";
		this.chixem = "0";
		
		currentPages = 1;
		num = 1;
	}
	public void CreateRs()
	{
		this.LoaiTaiKhoanRs =this. db.get("select PK_SEQ,Ten from erp_loaitaikhoan WHERE TRANGTHAI=1 ");
		this.CongTyRs =this. db.get("select PK_SEQ,TEN FROM ERP_CONGTY ");
		
	}
	
	public void init(String query) 
	{
		Utility util = new Utility();
		
		String sql= "";
		
		if(query.trim().length() > 0)
			sql = query;
		else
		{
			 sql = " SELECT TK.PK_SEQ,TK.SOHIEUTAIKHOAN,TK.TENTAIKHOAN,"+
					" CASE "+ 
					"	WHEN TK.TAIKHOANCOCHITIET=1 THEN N'Có ' "+
					"	WHEN TK.TAIKHOANCOCHITIET=0 THEN N'Không' "+
					" END TAIKHOANCOCHITIET, "+
					"LTK.TEN AS LOAITAIKHOAN,NT.TEN AS NGUOITAO,TK.NGAYTAO,NS.TEN AS NGUOISUA,TK.NGAYSUA," + 
					" CASE "+ 
					"	WHEN TK.TRANGTHAI=1 THEN N'Hoạt động'"+
					"	WHEN TK.TRANGTHAI=0 THEN N'Ngưng hoạt động'"+
					" END TRANGTHAI ,CTY.TEN AS CONGTY"+
				" FROM ERP_TAIKHOANKT  TK "+
				" INNER JOIN ERP_LOAITAIKHOAN LTK ON LTK.PK_SEQ=TK.LOAITAIKHOAN_FK "+
				" INNER JOIN NHANVIEN NT ON NT.PK_SEQ=TK.NGUOITAO "+
				" INNER JOIN NHANVIEN NS ON NS.PK_SEQ=TK.NGUOISUA "+
				" INNER JOIN ERP_CONGTY CTY ON CTY.PK_SEQ=TK.CONGTY_FK " +
				" WHERE TK.pk_seq > 0 AND TK.CONGTY_FK = '" + this.CongTyId + "' ";
			 
		 if(this.SoHieuTaiKhoan.trim().length()>0)
			 sql+=" AND TK.SoHieuTaiKhoan like N'%"+this.SoHieuTaiKhoan+"%' ";
		 if(this.TenTaiKhoan.trim().length()>0)
			 sql+=" AND dbo.ftBoDau(TK.TenTaiKhoan) LIKE N'%"+ util.replaceAEIOU(this.TenTaiKhoan)+"%' ";
		 if(this.CongTyId.trim().length()>0)
			 sql+=" AND TK.CONGTY_FK='"+this.CongTyId+"'";
		 if(this.LoaiTaiKhoanId.trim().length()>0)
			 sql+=" AND  TK.LOAITAIKHOAN_FK='"+this.LoaiTaiKhoanId+"' ";
		 if(this.TrangThai.trim().length()>0)
			 sql+=" AND  TK.TRANGTHAI='"+this.TrangThai+"' ";
		 if(this.TaiKhoanCoChiTiet.trim().length()>0)
			 sql+=" AND  TK.TaiKhoanCoChiTiet='"+this.TaiKhoanCoChiTiet+"' ";
		}
		 
		 System.out.println("Lay tai khoan: " + sql);
		 
		 this.TaiKhoanRs = createSplittingDataNew(this.db, 50, 10, " SOHIEUTAIKHOAN ASC, TRANGTHAI, CONGTY  ", sql);
		 
		 
		 this.CreateRs();
	}
	
	public String Delete(String Id){
		String msg = "";		
		
		String query = "SELECT ISNULL(SUM(GIATRICOVND) + SUM(GIATRINOVND) + SUM(GIATRINGUYENTE),0) AS SUM , (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = "+Id+") SOHIEUTAIKHOAN " +
					   "FROM ERP_TAIKHOAN_NOCO " +
					   "WHERE TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = "+Id+")) ";
				
		ResultSet rs = this.db.get(query);
		
		String sohieutk = "";
		
		try{
			rs.next();
			sohieutk = rs.getString("SOHIEUTAIKHOAN");
			if(Float.parseFloat(rs.getString("SUM")) == 0){
				query = "DELETE FROM ERP_TAIKHOAN_NOCO WHERE TAIKHOANKT_FK IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = "+Id+")) ";
				this.db.update(query);
				
				query = "DELETE FROM ERP_TAIKHOANKT WHERE PK_SEQ IN (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = "+Id+")) ";
				this.db.update(query);
				
				//SYN QUA DMS
				UtilitySyn.SynData(db, "ERP_TAIKHOANKT", "ERP_TAIKHOANKT", "SOHIEUTAIKHOAN", sohieutk, "2", true);
			}
			else
			{
				msg = "TÀI KHOẢN NÀY ĐÃ GHI NHẬN CÔNG NỢ, KHÔNG THỂ XÓA!";
			}
			rs.close();
			
			
			
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		
		return msg;
		
	}
	public void closeDB()
	{

		try
		{
			if (this.LoaiTaiKhoanRs != null)
				this.LoaiTaiKhoanRs.close();
			
			if (this.CongTyRs != null)
				this.CongTyRs.close();
					
			if(this.TaiKhoanRs!=null)
				this.TaiKhoanRs.close();
			
			if (this.db != null)
				this.db.shutDown();
			
		} catch (SQLException e)
		{
			e.printStackTrace();
			System.out.println("closeDB Exception");
		}

	}

	public String getUserId()
	{

		return this.UserId;
	}

	public void setUserId(String userId)
	{
		this.UserId = userId;
	}

	public String getId()
	{

		return this.Id;
	}

	public void setId(String Id)
	{
		this.Id = Id;
	}

	public String getLoaiTaiKhoanId()
	{

		return this.LoaiTaiKhoanId;
	}

	public void setLoaiTaiKhoanId(String LoaiTaiKhoanId)
	{
		this.LoaiTaiKhoanId = LoaiTaiKhoanId;
	}

	public String getSoHieuTaiKhoan()
	{

		return this.SoHieuTaiKhoan;
	}

	public void setSoHieuTaiKhoan(String SoHieuTaiKhoan)
	{
		this.SoHieuTaiKhoan = SoHieuTaiKhoan;
	}

	public String getTenTaiKhoan()
	{

		return this.TenTaiKhoan;
	}

	public void setTenTaiKhoan(String TenTaiKhoan)
	{
		this.TenTaiKhoan = TenTaiKhoan;
	}

	public String getTaiKhoanCoChiTiet()
	{

		return this.TaiKhoanCoChiTiet;
	}

	public void setTaiKhoanCoChiTiet(String TaiKhoanCoChiTiet)
	{
		this.TaiKhoanCoChiTiet = TaiKhoanCoChiTiet;

	}

	public String getCongTyId()
	{

		return this.CongTyId;
	}

	public void setCongTyId(String CongTyId)
	{

		this.CongTyId = CongTyId;
	}

	public String getTrangThai()
	{

		return this.TrangThai;
	}

	public void setTrangThai(String TrangThai)
	{
		this.TrangThai = TrangThai;

	}

	public String getTaiKhoanCoChiPhi()
	{

		return this.TaiKhoanCoChiPhi;
	}

	public void setTaiKhoanCoChiPhi(String TaiKhoanCoChiPhi)
	{
		this.TaiKhoanCoChiPhi = TaiKhoanCoChiPhi;
	}

	public ResultSet getCongTyRs()
	{

		return this.CongTyRs;
	}

	public void setCongTyRs(ResultSet CongTyRs)
	{
		this.CongTyRs = CongTyRs;

	}

	

	public ResultSet getLoaiTaiKhoanRs()
	{

		return this.LoaiTaiKhoanRs;
	}

	public void setLoaiTaiKhoanRs(ResultSet LoaiTaiKhoanRs)
	{
		this.LoaiTaiKhoanRs = LoaiTaiKhoanRs;

	}
	
	public ResultSet getTaiKhoanRs()
	{

		return this.TaiKhoanRs;
	}

	public void setTaiKhoanRs(ResultSet TaiKhoanRs)
	{
		this.TaiKhoanRs = TaiKhoanRs;

	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}
	
	
	public int getNum(){
		return this.num;
	}
	public void setNum(int num){
		this.num = num;
		listPages = PhanTrang.getListPages(num);

	}

	
	public int getCurrentPage() {
		return this.currentPages;
	}

	
	public void setCurrentPage(int current) {
		this.currentPages = current;
	}

	
	public int[] getListPages() {
		return this.listPages;
	}

	
	public void setListPages(int[] listPages) {
		this.listPages = listPages;
	}

	
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from erp_taikhoankt");
		return PhanTrang.getLastPage(rs);
	}

	
	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public void setChixem(String chixem) {
		
		this.chixem = chixem;
	}

	public String getChixem() {
		
		return this.chixem;
	}
	
}
