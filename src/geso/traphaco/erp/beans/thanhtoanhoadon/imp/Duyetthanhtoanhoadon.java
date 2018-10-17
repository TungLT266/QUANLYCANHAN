package geso.traphaco.erp.beans.thanhtoanhoadon.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.thanhtoanhoadon.IDuyetthanhtoanhoadon;

import java.sql.ResultSet;

import javax.servlet.http.HttpServletRequest;

public class Duyetthanhtoanhoadon extends Phan_Trang implements IDuyetthanhtoanhoadon {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String userId;
	String ctyId;
	
	String nvId;
	ResultSet nvRs;
	
	String khId;
	ResultSet khRs;
	
	String ngayghinhan;
	String maTTHD;
	String nccId;
	ResultSet nccList;
	String duyetchi;
	
	String loaiCT;
	ResultSet polist;
	
	HttpServletRequest request;	
	String msg;
	String nppdangnhap;
	dbutils db;
    Utility util;
    
    private int num;
	private int[] listPages;
	private int currentPages;

	public Duyetthanhtoanhoadon(){
		this.userId = "";
		//this.ctyId = "100001";
		this.ctyId="";
		this.nvId = "";
		this.khId = "";
		this.ngayghinhan="";
		this.maTTHD = "";
		this.nccId ="";
		this.loaiCT = "";
		this.msg = "";
		this.nppdangnhap = "";
		this.duyetchi="";
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		this.util=new Utility();
	}
	public int getNum() {
		return this.num;
	}

	public void setNum(int num) {
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

	public void setNccList(ResultSet nccList)
	{
		this.nccList = nccList;
	}
	
	public ResultSet getNccList() 
	{
		return nccList;
	}
	
	public void setNccId(String nccId)
	{
		this.nccId = nccId;
	}
	
	public String getNccId() 
	{
		return nccId;
	}
	
	public void setMaTTHD(String maTTHD) 
	{
		this.maTTHD = maTTHD;
	}
	
	public String getMaTTHD() 
	{
		return maTTHD;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getNgayghinhan(){
		return this.ngayghinhan;
	}
	
	public void setNgayghinhan(String ngayghinhan){
		this.ngayghinhan = ngayghinhan;
	}
	
	public String getNvId(){
		return this.nvId;
	}
	
	public void setNvId(String nvId){
		this.nvId = nvId;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getNvRs(){
		return this.nvRs ;
	}
	
	public void setNvRs(ResultSet nvRs){
		this.nvRs = nvRs;
	}


	public ResultSet getPoList(){
		return this.polist ;
	}
	
	public void setPoList(ResultSet polist){
		this.polist = polist;
	}

	public void setRequest(HttpServletRequest request){
		this.request = request;
	}
	public int getLastPage() {
		ResultSet rs = db.get("select count(*) as c from ERP_THANHTOANHOADON");
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage) {
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}

	public void init()
	{
		this.getNppInfo();
		String query =  "\n SELECT TTHD.PK_SEQ AS TTHDID, TTHD.NGAYGHINHAN, ISNULL(TTHD.SOCHUNGTU,'') AS SOCHUNGTU ,TTHD.HTTT_FK ,  "+
						"\n        TTHD.TRANGTHAI, ISNULL(TTHD.ISKTTDUYET,0) AS ISKTTDUYET, TTHD.SOTIENTT AS TONGTIENAVAT, NV1.TEN AS NGUOITAO,   "+
				        "\n        CASE WHEN NCC.PK_SEQ IS NOT NULL THEN NCC.TEN "+
				        "\n             WHEN NV.PK_SEQ IS NOT NULL THEN NV.TEN "+
				        "\n             WHEN KH.PK_SEQ IS NOT NULL THEN KH.TEN "+
				        "\n             WHEN DVTH.PK_SEQ IS NOT NULL THEN DVTH.TEN "+
				        "\n             ELSE '' END TENDT, "+
				        "\n        CASE WHEN TTHD.HTTT_FK = 100000 THEN N'Phiếu chi' ELSE N'Ủy nhiệm chi' END AS LOAICT, 0 as LOAICTID, "+
				        "\n       (select count(*) from ERP_CHUCDANH where NHANVIEN_FK = "+ this.userId +" and isnull(isKTT,0) = 1 ) as isKTT "+
		         		"\n FROM ERP_THANHTOANHOADON TTHD "+
		         		"\n      LEFT JOIN ERP_NHACUNGCAP NCC ON TTHD.NCC_FK = NCC.PK_SEQ "+
		         		"\n      LEFT JOIN ERP_NHANVIEN NV ON TTHD.NHANVIEN_FK = NV.PK_SEQ "+
		         		"\n      LEFT JOIN  nhaPhanPhoi KH ON TTHD.KHACHHANG_FK = KH.PK_SEQ "+
		         		"\n      LEFT JOIN ERP_DONVITHUCHIEN DVTH ON TTHD.DVTH_FK = DVTH.PK_SEQ "+
		         		"\n      INNER JOIN NHANVIEN NV1 ON TTHD.NGUOITAO = NV1.PK_SEQ "+
		         		"\n WHERE TTHD.TRANGTHAI = 0 AND ISNULL(TTHD.ISKTTDUYET,0) = 0 " +
		         		"\n AND " + this.userId + " in (select nhanVien_FK from erp_chucDanh where trangThai = 1 and isktt = 1) " + 
		         		"\n AND TTHD.HTTT_FK IN (100000, 100001, 100003) and TTHD.CONGTY_FK = "+this.ctyId+"" ;

		
		if(this.ngayghinhan!=null && this.ngayghinhan.trim().length() > 0)
		{
			query += " AND TTHD.NGAYGHINHAN = '" + this.ngayghinhan + "' ";
		}
		if(this.nccId !=null && this.nccId.trim().length() > 0)
		{
			query += " AND TTHD.NCC_FK = '"+ this.nccId +"' ";
		}
		if(this.nvId !=null && this.nvId.trim().length() > 0)
		{
			query += " AND TTHD.NHANVIEN_FK = '"+ this.nvId +"' ";
		}
		if(this.khId !=null && this.khId.trim().length() > 0)
		{
			query += " AND TTHD.KHACHHANG_FK = '"+ this.khId +"' ";
		}
		if(this.maTTHD !=null && this.maTTHD.trim().length() > 0)
		{
			query += " AND TTHD.SOCHUNGTU  LIKE '%"+ this.maTTHD +"%'  ";
			System.out.println("====query==" + query);
			System.out.println("====query==");
		}
		if(this.loaiCT.trim().length() > 0)
		{
			if(this.loaiCT.equals("0")) // PHIẾU CHI
				query += " AND TTHD.HTTT_FK = '100000' ";
			else if(this.loaiCT.equals("1")) // ỦY NHIỆM CHI
				query += " AND TTHD.HTTT_FK = '100001' ";
		}
		
		//query += "ORDER BY LOAICTID, HTTT_FK ASC, TTHDID ASC , NGAYGHINHAN ASC";
 
		System.out.println(" 1. init duyet TTHD :" + query);
		
		
		this.polist = createSplittingDataNew(this.db, 10, 10, "LOAICTID, HTTT_FK ASC, TTHDID ASC , NGAYGHINHAN ASC", query);
		
//		System.out.println(" 1. init duyet TTHD :" + query_init);
		
//		this.polist = this.db.get(query_init);
		
		this.nccList = this.db.get("SELECT PK_SEQ, MA + '-' + TEN AS TENNCC FROM ERP_NHACUNGCAP WHERE TRANGTHAI = '1' and duyet = '1' and CONGTY_FK = "+this.ctyId+" ");
		this.nvRs = this.db.get("SELECT PK_SEQ, MA + '-' + TEN AS TENNV FROM ERP_NHANVIEN WHERE TRANGTHAI = '1' AND CONGTY_FK = "+this.ctyId+" ");
		//this.khRs = this.db.get("SELECT PK_SEQ, MA + '-' + TENXUATHD  AS TENKH FROM KHACHHANG WHERE TRANGTHAI = '1' AND NPP_FK = "+this.nppdangnhap+" ");
	}
	
	public boolean  Duyetmuahang(String Id, String loaiId){
		try
		{
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
			
			if(loaiId.equals("0"))  // Phieu chi / UNC
			{
				String query=" SELECT pk_Seq FROM ERP_THANHTOANHOADON WHERE PK_SEQ="+Id+" AND HTTT_FK =100001 AND ISNULL(SOTAIKHOAN,'') =''";
				
				ResultSet rscheck=db.get(query);
				if(rscheck.next()){
					db.getConnection().rollback();
					this.msg= "Vui lòng chọn tài khoản ngân hàng để chi tiền";
					return false;
					
				}
				rscheck.close();
				
				  query = " UPDATE ERP_THANHTOANHOADON SET NGUOIDUYET = " + this.userId + " " +
							   " WHERE PK_SEQ = '" + Id + "' ";
				System.out.println(query);
				if(!this.db.update(query)) 
				{
					db.getConnection().rollback();
					this.msg=  "Không thể cập nhật dòng lệnh,vui lòng đăng xuất ra để thử lại, không đượ vui lòng báo Admin để được trợ giúp .Error : "+query ;
					return false;
				}
				//cập nhật tooltip
				db.execProceduce2("CapNhatTooltip_NguoiDuyetUNC", new String[] {Id});
				
				// KIỂM TRA NGƯỜI DUYỆT CÓ PHẢI LÀ KTT KHÔNG >> PHẢI : ISKTTDUYET = 1
				query = " SELECT COUNT(*) as KT FROM ERP_CHUCDANH WHERE NHANVIEN_FK = '"+ this.userId +"' AND CONGTY_FK = "+this.ctyId+" AND isnull(isKTT,0) = 1 AND TRANGTHAI = 1 ";
				System.out.println(query);
				ResultSet rs = db.get(query);
				int isKTT = 0;
				
				if(rs!= null)
				{
					while(rs.next())
					{
						isKTT = rs.getInt("KT");
					}rs.close();
				}
				
				if(isKTT >= 1)
				{
					query = " UPDATE ERP_THANHTOANHOADON SET ISKTTDUYET = '1' , KETOANTRUONG_FK = "+this.userId+"  WHERE PK_SEQ = '" + Id + "' ";
					System.out.println(query);
					if(!this.db.update(query)) 
					{
						db.getConnection().rollback();
						this.msg=  "Không thể cập nhật dòng lệnh,vui lòng đăng xuất ra để thử lại, không đượ vui lòng báo Admin để được trợ giúp .Error : "+query ;
						return false;
					}
					
					// cho số chứng từ nhảy tự động theo hình thức thanh toán					
										
								
					query = " UPDATE ERP_MUAHANG SET ISHOANTAT = 1 WHERE PK_SEQ IN ( \n"+
							" SELECT A.HOADON_FK dem FROM ERP_THANHTOANHOADON_HOADON A " +
					 		" WHERE A.LOAIHD = 6 AND A.THANHTOANHD_FK = "+Id+" ) "; // CẬP NHẬT ĐỀ NGHỊ THANH TOÁN = HOÀN TẤT
					System.out.println(query);
					if(!this.db.update(query)) 
					{
						db.getConnection().rollback();
						this.msg=  "Không thể cập nhật dòng lệnh,vui lòng đăng xuất ra để thử lại, không đượ vui lòng báo Admin để được trợ giúp .Error : "+query ;
						return false;
					}
					
					query = " UPDATE ERP_TAMUNG SET ISHOANTAT = 1 WHERE PK_SEQ IN ( \n"+
							" SELECT A.HOADON_FK dem FROM ERP_THANHTOANHOADON_HOADON A " +
					 		" WHERE A.LOAIHD = 1 AND A.THANHTOANHD_FK = "+Id+" ) "; // CẬP NHẬT TẠM ỨNG = HOÀN TẤT
					System.out.println(query);
					if(!this.db.update(query)) 
					{
						db.getConnection().rollback();
						this.msg=  "Không thể cập nhật dòng lệnh,vui lòng đăng xuất ra để thử lại, không đượ vui lòng báo Admin để được trợ giúp .Error : "+query ;
						return false;
					}
			
				}
			
			}
			else  // Phieu thu
			{
				String query =  " UPDATE ERP_THUTIEN SET NGUOIDUYET = " + this.userId + " " +
						   		" WHERE PK_SEQ = '" + Id + "' ";
				if(!this.db.update(query)) 
				{
					db.getConnection().rollback();
					this.msg=  "Không thể cập nhật dòng lệnh,vui lòng đăng xuất ra để thử lại, không đượ vui lòng báo Admin để được trợ giúp .Error : "+query ;
					return false;
				}
				
				// KIỂM TRA NGƯỜI DUYỆT CÓ PHẢI LÀ KTT KHÔNG >> PHẢI : ISKTTDUYET = 1
				query = " SELECT COUNT(*) as KT FROM ERP_CHUCDANH WHERE NHANVIEN_FK = '"+ this.userId +"' AND CONGTY_FK = "+this.ctyId+" AND isnull(isKTT,0) = 1 AND TRANGTHAI = 1 ";
				ResultSet rs = db.get(query);
				int isKTT = 0;
				
				if(rs!= null)
				{
					while(rs.next())
					{
						isKTT = rs.getInt("KT");
					}rs.close();
				}
				
				if(isKTT >= 1)
				{
					query = " UPDATE ERP_THUTIEN SET ISKTTDUYET = '1', TRANGTHAI = 1  WHERE PK_SEQ = '" + Id + "' ";
					if(!this.db.update(query)) 
					{
						db.getConnection().rollback();
						this.msg=  "Không thể cập nhật dòng lệnh,vui lòng đăng xuất ra để thử lại, không đượ vui lòng báo Admin để được trợ giúp .Error : "+query ;
						return false;
					}
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			this.msg=  "Không thể cập nhật dòng lệnh,vui lòng đăng xuất ra để thử lại, không đượ vui lòng báo Admin để được trợ giúp"+e.getMessage();
			return false;
		}
		
		return true;
	}
	
/*	public String getDaduyet(String mhId){
		String tmp = "";
		String query =  "SELECT DUYETMUAHANG.MUAHANG_FK AS MHID, NV.PK_SEQ AS NVID, NV.DANGNHAP AS NVTEN " +
						"FROM ERP_THANHTOANHOADON TTHD " +						
						"LEFT JOIN NHANVIEN NV ON NV.PK_SEQ = TTHD.NGUOIDUYET_FK " +
						"LEFT JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.NHANVIEN_FK = NV.PK_SEQ " +
						"WHERE ISNULL(TTHD.ISKTTDUYET,0) = '0' AND MUAHANG_FK = " + mhId + "  ";
		ResultSet rs = this.db.get(query);
		try{
			if(rs != null){
				while(rs.next()){
					tmp = tmp  + rs.getString("NVTEN") + " ; " ;
				}
				if(tmp.length() > 0)
					tmp = tmp.substring(0, tmp.length()-2);
				rs.close();
				return tmp;
			}else{
				return tmp;
			}
		}catch(java.sql.SQLException e){
			return tmp;
		}

	}*/
	
	public void DBclose(){
		try{
			if(this.nvRs != null) this.nvRs.close();
			if(this.polist != null) this.polist.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		this.db.shutDown();
	}

	public String getKhId(){
		return this.khId;
	}
	
	public void setKhId(String khId){
		this.khId = khId;
	}
	
	public ResultSet getKhRs(){
		return this.khRs;
	}
	
	public void setKhRs(ResultSet khRs){
		this.khRs = khRs;
	}

	public String getLoaiCT() 
	{
		return this.loaiCT;
	}

	public void setLoaiCT(String loaiCT) 
	{
		this.loaiCT = loaiCT;
		
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.userId);
	}
	public String getDuyetchi() {
		return duyetchi;
	}
	public void setDuyetchi(String duyetchi) {
		this.duyetchi = duyetchi;
	}


}
