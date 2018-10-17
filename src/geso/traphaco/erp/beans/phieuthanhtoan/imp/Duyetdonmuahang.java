package geso.traphaco.erp.beans.phieuthanhtoan.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.UtilityKeToan;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon;
import geso.traphaco.erp.beans.phieuthanhtoan.IDuyetdonmuahang;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Duyetdonmuahang  extends Phan_Trang  implements IDuyetdonmuahang {
	private static final long serialVersionUID = 8085701361357405206L;
	String congtyId;
	String userId;
	String ctyId;
	
	String dvthId;
	ResultSet dvth;
	String tongTien;
	String lspId;
	String ngaymua;
	String denNgay;
	String trangThai;
	String maDMH;
	String nccId;
	String lydoxoa;
	String lydomoduyet;
	String lydosua;
	String nguoitao;
	ResultSet nccList;
	ResultSet HtttList;
	String htttid;
	ResultSet lsp;
	ResultSet nguoitaoRs;
	String nguotaoIds;
	ResultSet polist;
	

	private int num;
	private int[] listPages;
	private int currentPages;
	
	HttpServletRequest request;	
	String msg;
	dbutils db;
    Utility util;

	public Duyetdonmuahang(){
		this.userId = "";
		//this.ctyId = "100001";
		this.ctyId="";
		this.dvthId = "";
		this.lspId = "";
		this.ngaymua="";
		this.denNgay = "";
		this.trangThai="";
		this.htttid="";
		this.maDMH = "";
		this.nccId ="";
		this.msg = "";
		this.lydoxoa = "";
		this.lydomoduyet = "";
		this.lydosua = "";
		this.nguoitao = "";
		this.tongTien ="";
		currentPages = 1;
		num = 1;
		this.db = new dbutils();
		this.util=new Utility();
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
	
	public void setMaDMH(String maDMH) 
	{
		this.maDMH = maDMH;
	}
	
	public String getMaDMH() 
	{
		return maDMH;
	}
	
	public String getNguoitao(){
		return this.nguoitao;
	}
	
	public void setNguoitao(String nguoitao){
		this.nguoitao = nguoitao;
	}

	public ResultSet getNguoitaoRs() {
		return this.nguoitaoRs;
	}

	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		this.nguoitaoRs = nguoitaoRs;
	}

	public void setNguoitaoIds(String nspIds) {
		this.nguotaoIds = nspIds;
	}

	public String getNguoitaoIds() {
		return this.nguotaoIds;
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
	
	public String getNgaymua(){
		return this.ngaymua;
	}
	
	public void setNgaymua(String ngaymua){
		this.ngaymua = ngaymua;
	}
	
	public String getDenNgay(){
		return this.denNgay;
	}
	
	public void setDenNgay(String denNgay){
		this.denNgay = denNgay;
	}
	
	public String getTrangThai(){
		return this.trangThai;
	}
	
	public void setTrangThai(String trangThai){
		this.trangThai= trangThai;
	}
	
	public String getDvthId(){
		return this.dvthId;
	}
	
	public void setDvthId(String dvthId){
		this.dvthId = dvthId;
	}
	
	public String getLspId(){
		return this.lspId;
	}
	
	public void setLspId(String lspId){
		this.lspId = lspId;
	}
	
	public String getMsg(){
		return this.msg;
	}
	
	public void setMsg(String msg){
		this.msg = msg;
	}

	public ResultSet getLspList(){
		return this.lsp ;
	}
	
	public void setLspList(ResultSet lsp){
		this.lsp = lsp;
	}

	public ResultSet getDvthList(){
		return this.dvth ;
	}
	
	public void setDvthList(ResultSet dvth){
		this.dvth = dvth;
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
	
	public int getNum()
	{
		return this.num;
	}
	
	public void setNum(int num)
	{
		this.num = num;
		listPages = PhanTrang.getListPages(num);
	}
	
	public int getCurrentPage() 
	{
		return this.currentPages;
	}

	public void setCurrentPage(int current) 
	{
		this.currentPages = current;
	}

	public int[] getListPages() 
	{
		return this.listPages;
	}

	public void setListPages(int[] listPages) 
	{
		this.listPages = listPages;
	}
	
	public void setTongTien(String tongTien){
		this.tongTien = tongTien;
	}
	
	public String getTongTien(){
		return this.tongTien;
	}

	public int getLastPage() 
	{
		String query=" select  count(*) as c from  erp_muahang muahang  " +
					 " WHERE isnull(MUAHANG.ISDACHOT, 0) = '1' AND MUAHANG.ISDNTT = 1 AND MUAHANG.congty_fk = "+this.congtyId+" and MUAHANG.TYPE = '1'";
		
		ResultSet rs = db.get(query);
		return PhanTrang.getLastPage(rs);
	}

	public int[] getNewPagesList(String action, int num, int currentPage, int theLastPage, String[] listPage)
	{
		IPhanTrang pt = new PhanTrang();
		return pt.getNewPagesList(action, num, currentPage, theLastPage, listPage);
	}
	
	public void init()
	{		
		//1. LOẠI 1 : ĐỀ NGHỊ THANH TOÁN CẦN PHẢI DUYỆT; LOẠI 2: ĐỀ NGHỊ THANH TOÁN CẤP TRÊN ĐÃ DUYỆT CHỈ ĐƯỢC XEM
		String query =     "\n SELECT distinct NV.TEN as NGUOITAO, MUAHANG.PK_SEQ AS MHID, NGAYMUA AS NGAY, DVTH.TEN AS DVTH, "+
		            "\n CASE WHEN MUAHANG.NHACUNGCAP_FK IS NOT NULL THEN NCC.TEN WHEN MUAHANG.NHANVIEN_FK IS NOT NULL THEN NV1.TEN WHEN MUAHANG.DOITUONGKHAC_FK IS NOT NULL THEN DTK.TENDOITUONG  ELSE KH.TEN END AS NCC, MUAHANG.TONGTIENAVAT, " +
//					"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.MA when '1' then TS.ma else TKKT.SOHIEUTAIKHOAN+' - ' + TKKT.TENTAIKHOAN + '-' + CP.TEN end as MA, " +
//					"\n case MUAHANG.LOAIHANGHOA_FK when '0' then SP.TEN else MUAHANG_SP.diengiai end AS SP,  " +
//					"\n MUAHANG_SP.SOLUONG, isnull(MUAHANG_SP.DONGIA, 0) as DONGIA, isnull(MUAHANG_SP.THANHTIEN, 0) as THANHTIEN, " +
					"\nisnull(MUAHANG.VUOTNGANSACH, 0) as vuotNganSach, " +
					"\n MUAHANG.SOPO as SOCHUNGTU, MUAHANG.TRANGTHAI TRANGTHAI, "+
					 "(select SUM(ISTP) from ERP_CHUCDANH where NHANVIEN_FK="+ this.userId+ " and isTP=1 and congty_fk="+ this.congtyId+ "  and DVTH_FK=MUAHANG.DONVITHUCHIEN_FK and trangthai=1) as quyentp"+
					 ",(select SUM(isKTV) from ERP_CHUCDANH where NHANVIEN_FK="+ this.userId+ " and isKTV=1 and congty_fk="+ this.congtyId+ " and trangthai=1) as quyentpkt,"+
					 "(\n" 
						+"	 select SUM(isKTV) from ERP_CHUCDANH CD \n" 
					    +"   left join ERP_CHUCDANH_NHANVIEN cdnv on cd.PK_SEQ=cdnv.CHUCDANH_FK \n" 
						+"   where cdnv.NHANVIEN_FK="+this.userId +" and CD.isKTV=1 and CD.congty_fk="+ this.congtyId+ " and CD.trangthai=1" 
						+  ") as quyennvkt \n"
						+ ",(select SUM(isKTT) from ERP_CHUCDANH where NHANVIEN_FK="+ this.userId+"and isKTT=1 and congty_fk="+ this.congtyId+ " and trangthai=1) as quyenGD, "+
					"\n ISNULL(MUAHANG.ISTP,0) ISTP, ISNULL(MUAHANG.ISKTV, 0) ISKTV, ISNULL(MUAHANG.ISKTT, 0) ISKTT, ISNULL(MUAHANG.ISDACHOT,0) ISDACHOT "+
				    "\n FROM ERP_MUAHANG MUAHANG " +
					"\n INNER JOIN NHANVIEN NV ON NV.PK_SEQ = MUAHANG.NGUOITAO " +
//					"\n INNER JOIN ERP_MUAHANG_SP MUAHANG_SP ON MUAHANG_SP.MUAHANG_FK = MUAHANG.PK_SEQ  " +
					"\n INNER JOIN ERP_DONVITHUCHIEN DVTH ON DVTH.PK_SEQ = MUAHANG.DONVITHUCHIEN_FK  " +
					"\n LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = MUAHANG.NHACUNGCAP_FK " +	
					"\n LEFT JOIN ERP_DOITUONGKHAC DTK ON DTK.PK_SEQ = MUAHANG.DOITUONGKHAC_FK   " +	
					"\n LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = MUAHANG.NHANVIEN_FK   " +	
					"\n LEFT JOIN NHAPHANPHOI KH ON MUAHANG.KHACHHANG_FK = KH.PK_SEQ AND ISKHACHHANG = 1 AND KH.TRANGTHAI = '1' " +	
//					"\n LEFT JOIN SANPHAM SP ON SP.PK_SEQ = MUAHANG_SP.SANPHAM_FK  " +
//					"\n LEFT join ERP_TAISANCODINH TS on TS.pk_seq = MUAHANG_SP.TAISAN_FK " +
//					"\n LEFT JOIN ERP_NHOMCHIPHI CP on CP.PK_SEQ = MUAHANG_SP.CHIPHI_FK " + 
//					"\n LEFT JOIN ERP_TAIKHOANKT TKKT ON TKKT.SOHIEUTAIKHOAN = CP.TAIKHOAN_FK "+
				    "\n WHERE isnull(MUAHANG.ISDACHOT, 0) = '1' AND MUAHANG.ISDNTT = 1" +
				    "\n AND MUAHANG.congty_fk = '" + this.congtyId + "' " +
//					"\n AND MUAHANG.LOAIHANGHOA_FK = 2 " +
					"\nand MUAHANG.TYPE = '1' "+ // CHỈ LẤY ĐỀ NGHỊ THANH TOÁN							
					"\n AND MUAHANG.TRANGTHAI IN ( 0 , 1 ) " + // CẤP CUỐI CÙNG CHƯA DUYỆT					

					"AND" +
					 " 	(  " + this.userId + " in  ( \n " +
					 "									SELECT NHANVIEN_FK FROM ERP_CHUCDANH \n " +
					 "								  	WHERE DVTH_FK = (SELECT PHONGBAN_FK FROM NHANVIEN WHERE PK_SEQ = NV.PK_SEQ)  AND ISTP=1 and ISNULL(muahang.ISDACHOT,0)=1  \n " +
					 "								 )     \n " +
					 //"		OR " + this.userId+" = NV.PK_SEQ  \n " +
					 " 		OR 	" + this.userId + " in  ( \n " +
					 "									SELECT NHANVIEN_FK FROM ERP_CHUCDANH \n " +
					 "								  	WHERE ISKTT=1 and ISNULL(muahang.isktv,0)=1 and ISNULL(MUAHANG.ISLANHDAOKYDUYET,0)=0  ) \n " +
		
					 "      OR " + this.userId + " in  ( \n " +
					 "									SELECT NHANVIEN_FK FROM ERP_CHUCDANH \n " +
					 "								  	WHERE ISKTV=1 and ISNULL(muahang.istp,0)=1   ) \n " +
					 "      OR " + this.userId + " in  ( \n " +
					 "									SELECT CDNV.NHANVIEN_FK FROM ERP_CHUCDANH  CD " +
					 "									LEFT JOIN ERP_CHUCDANH_NHANVIEN CDNV ON CD.PK_SEQ=CDNV.CHUCDANH_FK\n " +
					 "								  	WHERE CD.ISKTV=1 AND ISNULL(MUAHANG.ISTP,0)=1 ) \n " +
					 " )" ; 	
					
			
					if(this.dvthId.length() > 0){
						query += "\n AND MUAHANG.DONVITHUCHIEN_FK = " + this.dvthId + " ";
					}
					
					if(this.nccId.length() > 0){
						query += "\n AND MUAHANG.NHACUNGCAP_FK = " + this.nccId + " ";
					}
					
					if(this.htttid.length() > 0){
						query += "\n AND MUAHANG.HTTT_FK = " + this.htttid + " ";
					}
					
			
					if(this.ngaymua.length() > 0){
						query += "\n AND MUAHANG.NGAYMUA >= '" + this.ngaymua + "' ";
					}
					if(this.denNgay.length() >0){
						query += "\n AND MUAHANG.NGAYMUA <= '" + this.denNgay + "'";
					}
			
					if(this.nguoitao.length() > 0){
						query += "\n AND MUAHANG.NGUOITAO = '" + this.nguoitao + "' ";
					}
					if(this.tongTien.length() > 0){
						query +="\n AND MUAHANG.TONGTIENAVAT = '" +this.tongTien + "' ";
					}
			
					if(this.maDMH.length() > 0){
						query += "\n AND MUAHANG.SOPO = '" + this.maDMH + "' ";
					}
					
					if(this.trangThai.length() > 0 )
					{
						//0 : chưa chốt
						//1 : đã chốt
						//2 : đã duyệt(trưởng phòng)
						//3 : đã duyệt (kế toán gán mã chi phí)
						//4 : đã duyệt (kế toán trưởng)
						//5 : đã hoàn tất
						//6 : đã xóa
						//7 : đã hủy
						
						if (this.trangThai.equals("0")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 0 AND ISNULL(MUAHANG.ISDACHOT,0) = 0 ";
						}
						if (this.trangThai.equals("1")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 0 AND ISNULL(MUAHANG.ISDACHOT,0) = 1 AND ISNULL(MUAHANG.ISTP,0) = 0"
									+ "\n AND ISNULL(MUAHANG.ISKTV,0) = 0 AND ISNULL(MUAHANG.ISKTT,0) = 0 ";
						}
						if (this.trangThai.equals("2")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 0 AND ISNULL(MUAHANG.ISTP,0) = 1 ";
						}
						if (this.trangThai.equals("3")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 0 AND ISNULL(MUAHANG.ISKTV,0) = 1 ";
						}
						if (this.trangThai.equals("4")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 0 AND ISNULL(MUAHANG.ISKTT,0) = 1 ";
						}
						if (this.trangThai.equals("5")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 1 ";
						}
						if (this.trangThai.equals("6")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 2 ";
						}
						if (this.trangThai.equals("7")){
							query += "\n AND ISNULL(MUAHANG.TRANGTHAI,0)= 3 ";
						}
					}

	//	query += " ORDER BY NGAY DESC , TRANGTHAI ASC ";
		System.out.println(" 1. init duyet :\n" + query + "\n---------------------------------------------------------------");
		

		this.polist = createSplittingDataNew(this.db, 30, 10, " NGAY DESC , TRANGTHAI ASC  ", query);
		query = "SELECT PK_SEQ AS DVTHID, TEN AS DVTH FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' " ;
		this.dvth = this.db.get(query) ;
		this.lsp = this.db.get("SELECT PK_SEQ AS LSPID, TEN AS LSP FROM ERP_LOAISANPHAM WHERE TRANGTHAI = '1' ");
		this.nccList = this.db.get("SELECT PK_SEQ, MA + '-' + TEN AS TENNCC FROM ERP_NHACUNGCAP WHERE TRANGTHAI = '1'  and duyet = '1'");
		this.HtttList = this.db.get("SELECT PK_SEQ,  TEN AS TEN FROM ERP_HINHTHUCTHANHTOAN WHERE TRANGTHAI = '1' and  isNull(isDungChoDeNghiThanhToan,0)=1");
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_MUAHANG ) ";
		this.nguoitaoRs = db.get(query);
	}
	
	public boolean Duyetmuahang(String Id, String action ){
		try
		{
			db.getConnection().setAutoCommit(false);
						
			String query = " select ISNULL(ISKTT,0) AS ISKTT,ISNULL(ISKTV,0) AS ISKTV, TRANGTHAI from ERP_MUAHANG WHERE PK_SEQ = " +Id;
			String daduyetKTT = "";
			String daduyetKTV = "";
			String tt = "";
			ResultSet rscheck = db.get(query);
			while(rscheck.next())
			{
				daduyetKTT = rscheck.getString("ISKTT");
				daduyetKTV = rscheck.getString("ISKTV");
				tt = rscheck.getString("TRANGTHAI");
			}
			rscheck.close();
			if(daduyetKTV.equals("1") ||daduyetKTT.equals("1") || tt.equals("1"))
			{
				this.msg="Phiếu đã duyệt";
				db.getConnection().rollback();
				return false;
			}
			if(action.equals("duyetTP")) // TRUONG PHONG DUYET
			{
				query = " SELECT NHANVIEN_FK, SUM(ISNULL(isTP,0)) isTP \n"+
						" FROM ERP_CHUCDANH WHERE DVTH_FK IN (SELECT PHONGBAN_FK FROM NHANVIEN WHERE PK_SEQ = ( SELECT NGUOITAO FROM ERP_MUAHANG WHERE PK_SEQ = "+Id+")) \n"+
						" AND NHANVIEN_FK = "+this.userId+" AND TRANGTHAI = 1 \n"+
						" GROUP BY NHANVIEN_FK \n";
				
				ResultSet Rs = db.get(query);
				
				int isTP = 0;
				
				if(Rs!=null)
				{
					while(Rs.next())
					{
						isTP = Rs.getInt("isTP");
					}
					Rs.close();
				}
				
				if(isTP==0)
				{
					this.msg = " Bạn không được cấp quyền trưởng phòng để duyệt phiếu này. Vui lòng xem lại dữ liệu nền chức danh";
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
				else
				{
					query = " UPDATE ERP_MUAHANG SET ISTP = 1, TRUONGPHONG_FK = "+this.userId +" WHERE PK_SEQ = " +Id;
					
					if(!db.update(query))
					{
						this.msg = "Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! ";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
				}				
			}
			
			if(action.equals("duyetKTV"))
			{
				query = " SELECT NHANVIEN_FK, SUM(ISNULL(isKTV,0)) isKTV \n"+
						" FROM ERP_CHUCDANH \n " +
						" WHERE NHANVIEN_FK = "+this.userId+" AND TRANGTHAI = 1 \n"+
						" GROUP BY NHANVIEN_FK \n";
		
				ResultSet Rs = db.get(query);
				
				int isKTV = 0;
				
				if(Rs!=null)
				{
					while(Rs.next())
					{
						isKTV = Rs.getInt("isKTV");
					}
					Rs.close();
				}
				
				if(isKTV==0)
				{
					this.msg = " Bạn không được cấp quyền kế toán gán mã chi phí. Vui lòng xem lại dữ liệu nền chức danh";
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
				else
				{
					query = " UPDATE ERP_MUAHANG SET ISKTV = 1,TRANGTHAI=1, KETOANVIEN_FK = "+this.userId +" WHERE PK_SEQ = " +Id+" and trangthai=0";
					
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! ";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
					
					//Kiểm tra nếu user có quyền kế toán trưởng thì duyệt luôn quyền kế toán trưởng
//					int ISKTT = kiemTraDuyetKKT(Id);
					
//					if(ISKTT != 0)
					{
						//Sửa lại: kế toán viên duyệt luôn quyền kế toán trưởng
						boolean result = duyetKKT(Id);
						if (result == false)
						{
							db.getConnection().rollback();
							if (this.msg.trim().length() == 0)
								this.msg = "Lỗi khi duyệt với quyền kế toán trưởng";
							return false;
						}
					}

				}				
			}
			
			if(action.equals("duyetKTT"))
			{
				int ISKTT = kiemTraDuyetKKT(Id);
				
				if(ISKTT == 0)
				{
					this.msg = " Bạn không được cấp quyền kế toán trưởng. Vui lòng xem lại dữ liệu nền chức danh";
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
				else
				{
					query = " UPDATE ERP_MUAHANG SET ISKTT = 1, KETOANTRUONG_FK = "+this.userId +" WHERE PK_SEQ = " +Id;
					
					if(!db.update(query))
					{
						this.msg = "Không thể duyệt được phiếu thanh toán. Vui lòng liên hệ GESO! ";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
					
					boolean result = duyetKKT(Id);
					if (result == false)
					{
						db.getConnection().rollback();
						if (this.msg.trim().length() == 0)
							this.msg = "Lỗi khi duyệt kế toán trưởng";
						return false;
					}
/*					query="SELECT KHOANMUCCHI FROM ERP_MUAHANG WHERE KHOANMUCCHI IS NOT NULL AND PK_SEQ =" +Id;
					ResultSet rs=db.get(query);
					if(rs.next())
					{
						boolean resultUpdate = updateNguyenGia(Id);
						if (resultUpdate == false)
						{
							db.getConnection().rollback();
							if (this.msg.trim().length() == 0)
								this.msg = "Lỗi khi duyệt kế toán trưởng";
							return false;
						}
					}*/
				}
				
				
		
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		return true;
	}
	
	private int kiemTraDuyetKKT(String Id) 
	{
		String query = " SELECT NHANVIEN_FK, SUM(ISNULL(ISKTT,0)) ISKTT \n"+
		" FROM ERP_CHUCDANH \n " +
		" WHERE NHANVIEN_FK = "+this.userId+" AND TRANGTHAI = 1 \n"+
		" GROUP BY NHANVIEN_FK \n";

		int ISKTT = 0;
		try{
		ResultSet Rs = db.get(query);
		
		if(Rs!=null)
		{
			while(Rs.next())
			{
				ISKTT = Rs.getInt("ISKTT");
			}
			Rs.close();
		}
		}catch (Exception e) {
			e.printStackTrace();
			return 0;
		}
		
		return ISKTT;
	}

	private boolean duyetKKT(String Id) {
		try{
			
		String query = " UPDATE ERP_MUAHANG SET ISKTV = 1, KETOANVIEN_FK = "+this.userId +", TRANGTHAI = 1 WHERE PK_SEQ = " +Id;
		
		
		long soTTDuyet = 1;
		query = "SELECT ISNULL(MAX(SOTTDUYET), 0)+1 AS SOTTDUYET\r\n" + 
				"FROM ERP_MUAHANG \r\n" + 
				"WHERE MONTH(NGAYMUA) = (SELECT MONTH(NGAYMUA) FROM ERP_MUAHANG WHERE PK_SEQ = "+Id+")  AND SOTTDUYET IS NOT NULL\n";
		System.out.println("Query lay sott:"+ query);
		ResultSet rs1 = db.get(query);
		soTTDuyet = (rs1.next()) ? rs1.getLong("SOTTDUYET") : soTTDuyet;
		
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Calendar cal = Calendar.getInstance();
		String ngayDuyet = "";
		ngayDuyet = dateFormat.format(cal.getTime());
		
		query="update ERP_MUAHANG SET SOTTDUYET = "+soTTDuyet+", NGAYDUYET = '"+ngayDuyet+"'  where pk_seq="+Id;
		if(!db.update(query))
		{
			
			this.msg="Không thể cập nhật hóa đơn :  "+query;
			return false;
		}
		
		/*if(!db.update(query))
		{
			this.msg = "Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! ";
			return false;
		}*/
		
		// KIỂM TRA ĐỊNH KHOẢN ĐÚNG HAY KHÔNG
		boolean kq = kiemTraDinhKhoan(Id);
		if (kq == false)
		{
			if(this.msg.trim().length() ==0)
			{ 
				this.msg = "Không thể duyệt được phiếu DNTT. Vui lòng liên hệ GESO! ";
			}
			
			return false;
		}
			
		
		//Kế toán trưởng duyệt => định khoản luôn nếu là công nợ, ko cho qua UNC, phiếu chi
		query = "select quanlycongno from ERP_MUAHANG where PK_SEQ = " + Id;
		String quanLyCongNo = "0";
		
		ResultSet rs = this.db.get(query);
		if(rs != null)
		{
			if (rs.next())
			{
				quanLyCongNo = rs.getString("quanlycongno");
			}
		}
		
		if (quanLyCongNo.trim().equals("1"))
		{
			String[] param = {Id, "1"};
			String result = this.db.execProceduce2("XuLyKeToan_DDNTT_PHATSINHKT", param);
			if (result.trim().length() > 0)
			{
				
				this.msg = "DKTT1.1 Không thể duyệt được phiếu tạm ứng: Không thể hạch toán\n" + result;
				System.out.println(this.msg);
				return false;
			}
			
			query="SELECT KHOANMUCCHI FROM ERP_MUAHANG WHERE KHOANMUCCHI IS NOT NULL AND PK_SEQ =" +Id;
			ResultSet rs2=db.get(query);
			if(rs2.next())
			{
				boolean resultUpdate = updateNguyenGia(Id);
				if (resultUpdate == false)
				{
					db.getConnection().rollback();
					if (this.msg.trim().length() == 0)
						this.msg = "Lỗi khi duyệt kế toán trưởng";
					return false;
				}
			}
		}
		else
		{				
			//TỰ TẠO PHIẾU CHI || ỦY NHIỆM CHI
			kq = createPhieuChiUNC(Id);
			if (kq == false)
			{
				
				if (this.msg.trim().length() == 0)
					this.msg = "DKTT1.2 Không tạo được phiếu chi, ủy nhiệm chi";
				return false;
			}else{ 
				// CHỈ PHÁT SINH KẾ TOÁN TRONG TRƯỜNG HỢP ĐNTT CHO NHÂN VIÊN / nhà cung cấp và CÓ CẤN TRỪ TẠM ỨNG
//				query = 				
//						"SELECT KM.TAIKHOAN_FK AS SOHIEU_TK_NO\n" +
//						", tkNV.SOHIEUTAIKHOAN AS SOHIEU_TK_CO\n" +
//						", '13311000' AS SOHIEU_TK_NO_VAT\n" +
//						", tkNV.SOHIEUTAIKHOAN AS SOHIEU_TK_CO_VAT\n" +
//						", case when DN_CP.SCLON_FK is not null then N'Mã sửa chữa lớn'\n" +
//						"	   when DN_CP.cpTraTruoc_fk is not null then N'Chi phí trả trước'\n" +
//						"	   else N'Khoản mục chi phí' \n" +
//						"  end as DOITUONG_NO\n" +
//						", case when DN_CP.SCLON_FK is not null then DN_CP.SCLON_FK\n" +
//						"	   when DN_CP.cpTraTruoc_fk is not null then DN_CP.cpTraTruoc_fk\n" +
//						"	   else dn.KHOANMUCCHI\n" +
//						"  end as MADOITUONG_NO\n" +
//						" , N'Nhân viên' AS DOITUONG_CO, DN.NHANVIEN_FK AS MADOITUONG_CO, \n" +
//						" N'Thanh toán tạm ứng' AS LOAICHUNGTU,  103575 as SOCHUNGTU, DN.TIENTE_FK AS TIENTEGOC, DN.TyGiaQuyDoi, \n" +
//						" SUM(DN_CP.SOLUONG*DN_CP.DONGIAVIET) AS BVAT, SUM(DN_CP.SOLUONG*DN_CP.DONGIAVIET*PhanTramThue/100) AS VAT, \n" +
//						" DN.NGAYMUA AS NGAYGHINHAN, SUM(DN_CP.SOLUONG*DONGIA) AS SOTIENNT, DN.HTTT_FK AS HTTTID \n" +
//						" , ISNULL(dn.LYDOTT, dn.GHICHU) as dienGiai, isNull(DN.soChungTu_Chu + DN.soChungTu_So, DN.pk_seq) maChungTu\n" +
//						" , case when dn.KHOANMUCCHI is null then KM.PK_SEQ\n" +
//						"		else dn.KHOANMUCCHI end as KHOANMUCCHI\n" +
//						"FROM ERP_MUAHANG DN \n" +
//						" INNER JOIN ERP_MUAHANG_SP DN_CP ON DN_CP.MUAHANG_FK = DN.PK_SEQ \n" +
//						" left JOIN ERP_NHOMCHIPHI KM ON KM.PK_SEQ = DN_CP.CHIPHI_FK\n" +
//						" left join ERP_NHANVIEN nv on nv.PK_SEQ = dn.NHANVIEN_FK \n" +
//						" left join ERP_TAIKHOANKT tkNV on tkNV.PK_SEQ = nv.TAIKHOAN_FK\n" +
//						" WHERE DN.PK_SEQ = " + Id + " \n" +
//						" --AND DN.TONGTIENCANTRU > 0 \n" +
//						" --AND DN.NHANVIEN_FK IS NOT NULL AND ISDNTT = 1 \n" +
//						" GROUP BY KM.TAIKHOAN_FK, DN.NHANVIEN_FK, DN.TIENTE_FK, DN.TyGiaQuyDoi, DN.NGAYMUA, DN.HTTT_FK\n" +
//						", dn.LYDOTT, dn.ghiChu , DN.soChungTu_Chu, DN.soChungTu_So, DN.pk_seq\n" +
//						", tkNV.PK_SEQ, tkNV.SOHIEUTAIKHOAN, DN_CP.SCLON_FK, DN_CP.cpTraTruoc_fk\n" +
//						", km.PK_SEQ, dn.KHOANMUCCHI\n " ;
				query =
					"SELECT KM.TAIKHOAN_FK AS SOHIEU_TK_NO, isNull(tkNV.SOHIEUTAIKHOAN, tkNCC.SOHIEUTAIKHOAN) AS SOHIEU_TK_CO\n" +
					", '13311000' AS SOHIEU_TK_NO_VAT, isNull(tkNV.SOHIEUTAIKHOAN, tkNCC.SOHIEUTAIKHOAN) AS SOHIEU_TK_CO_VAT\n" +
					", case when DN_CP.SCLON_FK is not null then N'Mã sửa chữa lớn'\n" +
					"	   when DN_CP.cpTraTruoc_fk is not null then N'Chi phí trả trước'\n" +
					"	   else N'Khoản mục chi phí' \n" +
					"  end as DOITUONG_NO\n" +
					", case when DN_CP.SCLON_FK is not null then convert(nvarchar, DN_CP.SCLON_FK)\n" +
					"	   when DN_CP.cpTraTruoc_fk is not null then convert(nvarchar, DN_CP.cpTraTruoc_fk)\n" +
					"	   when dn_cp.CHIPHI_FK is not null then convert(nvarchar, dn_cp.CHIPHI_FK)\n" +
					"	   else convert(nvarchar, dn.KHOANMUCCHI)\n" +
					"  end as MADOITUONG_NO\n" +
					" , case when dn.nhanVien_FK is not null then N'Nhân viên'\n" +
					"		 when dn.NHACUNGCAP_FK is not null then N'Nhà cung cấp' end AS DOITUONG_CO\n" +
					" , case when DN.NHANVIEN_FK is not null then DN.NHANVIEN_FK\n" +
					"		 when dn.NHACUNGCAP_FK is not null then dn.NHACUNGCAP_FK end AS MADOITUONG_CO\n" +
					" , N'Thanh toán tạm ứng' AS LOAICHUNGTU,  '" + Id + "' as SOCHUNGTU\n" +
					" , DN.TIENTE_FK AS TIENTEGOC, DN.TyGiaQuyDoi, DN_CP.SOLUONG*DN_CP.DONGIAVIET AS BVAT, DN_CP.SOLUONG*DN_CP.DONGIAVIET*PhanTramThue/100 AS VAT\n" +
					" , DN.NGAYMUA AS NGAYGHINHAN, DN_CP.SOLUONG*DONGIA AS SOTIENNT, DN.HTTT_FK AS HTTTID \n" +
					" , ISNULL(dn.LYDOTT, dn.GHICHU) as dienGiai, isNull(DN.soChungTu_Chu + DN.soChungTu_So, DN.pk_seq) maChungTu\n" +
					" , case when KM.PK_SEQ is null then convert(nvarchar, dn.KHOANMUCCHI)\n" +
					"		else convert(nvarchar, KM.PK_SEQ) end as KHOANMUCCHI\n" +
					"FROM ERP_MUAHANG DN \n" +
					" INNER JOIN ERP_MUAHANG_SP DN_CP ON DN_CP.MUAHANG_FK = DN.PK_SEQ \n" +
					" left JOIN ERP_NHOMCHIPHI KM ON KM.PK_SEQ = DN_CP.CHIPHI_FK\n" +
					" left join ERP_NHANVIEN nv on nv.PK_SEQ = dn.NHANVIEN_FK \n" +
					" left join ERP_TAIKHOANKT tkNV on tkNV.PK_SEQ = nv.TAIKHOAN_FK\n" +
					" left join ERP_NHACUNGCAP ncc on ncc.PK_SEQ = dn.NHACUNGCAP_FK\n" +
					" left join ERP_TAIKHOANKT tkNCC on tkNCC.PK_SEQ = ncc.TAIKHOAN_FK\n" +
					" WHERE DN.PK_SEQ = '" + Id + "' \n" +
					" AND DN.TONGTIENCANTRU > 0 \n" +
					" AND (DN.NHANVIEN_FK IS NOT NULL or dn.NHACUNGCAP_FK IS NOT NULL) AND ISDNTT = 1 \n";
									
				System.out.println("Lay dinh khoan: \n" + query + "\n----------------------------------------------");
				rs = this.db.get(query);
				if (rs == null)
				{
					
					this.msg = "DKTT1.3 Không thể duyệt DNTT\n";
					return false;
				}
				
//				String htttId = "";
				String taikhoanNO_SoHieu = "";
				String taikhoanCO_SoHieu = "";
				String taikhoanNO_SoHieu_VAT = "";
				String taikhoanCO_SoHieu_VAT = "";

				String ngayghinhan = "";
				String thang = "" ;
				String nam = "";
				String ngaychungtu = "";
				String loaichungtu = "";
				String sochungtu = Id;
				
				String NO_BVAT = "";
				String CO_BVAT = "";

				String NO_VAT = "";
				String CO_VAT = "";

				String DOITUONG_CO = "";
				String MADOITUONG_CO = "";
				
				String DOITUONG_NO = "";
				String MADOITUONG_NO = "";
				
				String TIENTEGOC_FK = "";
				String TIGIA_FK = "";
				
				String TONGGIATRI_BVAT = "";
				String TONGGIATRI_VAT = "";
				
				String TONGGIATRINT = "";
				if(rs != null){
					while(rs.next()){
						String dienGiai = rs.getString("dienGiai");
						String maChungTu = rs.getString("maChungTu");
						
//						htttId = rs.getString("HTTTID");
						taikhoanNO_SoHieu = rs.getString("SOHIEU_TK_NO");
						taikhoanCO_SoHieu = rs.getString("SOHIEU_TK_CO");
						
						taikhoanNO_SoHieu_VAT = rs.getString("SOHIEU_TK_NO_VAT");
						taikhoanCO_SoHieu_VAT = rs.getString("SOHIEU_TK_CO_VAT");

						ngayghinhan = rs.getString("NGAYGHINHAN");
						thang = "" + (Integer.parseInt(ngayghinhan.substring(5, 7)));
						nam = ngayghinhan.substring(0, 4);
						ngaychungtu = ngayghinhan;
						loaichungtu = rs.getString("LOAICHUNGTU");
						sochungtu = Id;
						
						NO_BVAT = rs.getString("BVAT");
						CO_BVAT = rs.getString("BVAT");

						NO_VAT = rs.getString("VAT");
						CO_VAT = rs.getString("VAT");

						DOITUONG_CO = rs.getString("DOITUONG_CO");
						MADOITUONG_CO = rs.getString("MADOITUONG_CO");
						
						DOITUONG_NO = rs.getString("DOITUONG_NO");
						MADOITUONG_NO = rs.getString("MADOITUONG_NO");
						
						TIENTEGOC_FK = rs.getString("TIENTEGOC");
						TIGIA_FK = rs.getString("TyGiaQuyDoi");
						
						TONGGIATRI_BVAT = rs.getString("BVAT");
						TONGGIATRI_VAT = rs.getString("VAT");
						
						TONGGIATRINT = rs.getString("SOTIENNT");
						
						if(taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							
							this.msg = "DKTT1.4 Vui lòng nhập khoản mục chi phí đầy đủ ";
							return false;
						}	
						
						UtilityKeToan ukt = new UtilityKeToan(loaichungtu, sochungtu, ngaychungtu,ngayghinhan
								, taikhoanNO_SoHieu, taikhoanCO_SoHieu, NO_BVAT, CO_BVAT
								, TIGIA_FK, TIENTEGOC_FK, thang, nam
								, DOITUONG_NO, MADOITUONG_NO, DOITUONG_CO, MADOITUONG_CO
								, maChungTu, dienGiai);
						ukt.setKhoanMucChiPhiNo_FK(rs.getString("khoanMucChi"));
						ukt.setTongGiaTri(TONGGIATRI_BVAT);
						if (TIENTEGOC_FK != null && !TIENTEGOC_FK.equals("100000"))
							ukt.setTongGiaTriNT(TONGGIATRINT);
						ukt.setKhoanMucNoCo("Tiền hàng");
//						UtilityKeToan(String loaiChungTu, String soChungTu, String ngayChungTu, String ngayGhiNhan
//								, String taiKhoanNo_FK, String taiKhoanCo_FK, String no, String co
//								, String tiGia_FK, String tienTeGoc_FK, String thang, String nam
//								, String doiTuongNo, String maDoiTuongNo, String doiTuongCo, String maDoiTuongCo
//								, String maChungTu, String dienGiai
//								)
								
						if(rs.getDouble("BVAT") > 0){
							this.msg = ukt.Update_TaiKhoanBySoHieu(db, "1");
							
//							this.msg = util.Update_TaiKhoan_TheoSoHieu_DoiTuong_NoCo(this.ctyId, db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, 
//							taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", NO_BVAT, CO_BVAT, "", "", "0", DOITUONG_CO, MADOITUONG_CO, "0", 
//							"NULL", "NULL", TIENTEGOC_FK, "NULL", TIGIA_FK, TONGGIATRI_BVAT, TONGGIATRINT, "", maChungTu, dienGiai);
							
							if (this.msg.trim().length() > 0)
							{
								
								this.msg = "DKTT1.5 Không thể duyệt DNTT\n" + this.msg;
								return false;
							}
						}
						
						if(rs.getDouble("VAT") > 0){
							ukt = new UtilityKeToan(loaichungtu, sochungtu, ngaychungtu,ngayghinhan
									, taikhoanNO_SoHieu_VAT, taikhoanCO_SoHieu_VAT, NO_VAT, CO_VAT
									, TIGIA_FK, TIENTEGOC_FK, thang, nam
									, "", "", DOITUONG_CO, MADOITUONG_CO
									, maChungTu, dienGiai);
							ukt.setKhoanMucChiPhiNo_FK(rs.getString("khoanMucChi"));
							ukt.setTongGiaTri(TONGGIATRI_VAT);
							ukt.setKhoanMucNoCo("Tiền thuế");
							
							this.msg = ukt.Update_TaiKhoanBySoHieu(db, "1");
							
//							this.msg = util.Update_TaiKhoan_TheoSoHieu_DoiTuong_NoCo(this.ctyId, db, thang, nam, ngaychungtu, ngayghinhan, loaichungtu, sochungtu, 
//							taikhoanNO_SoHieu_VAT, taikhoanCO_SoHieu_VAT, "", NO_VAT, CO_VAT, "", "", "0", DOITUONG_CO, MADOITUONG_CO, "0", 
//							"NULL", "NULL", TIENTEGOC_FK, "NULL", TIGIA_FK, "" + TONGGIATRI_VAT, "0", "", maChungTu, dienGiai);
							if (this.msg.trim().length() > 0)
							{
								
								this.msg = "DKTT1.6 Không thể duyệt DNTT\n" + this.msg;
								return false;
							}
						}
					}
					rs.close();
				}
			}
		}
		
		
		}catch (Exception e)
		{
			e.printStackTrace();
			this.msg = "1.Exception " + e.getMessage();
			return false;
		}
		return true;
	}

	private boolean createPhieuChiUNC(String Id) {
		try{
			String query = 
			" SELECT MH.NGAYMUA, MH.NHACUNGCAP_FK, MH.NHANVIEN_FK, MH.KHACHHANG_FK, " +
			" MH.TONGTIENCANTRU, " +
			" CASE WHEN MH.TONGTIENCANTRU > 0 THEN (MH.TONGTIENAVAT - MH.TONGTIENCANTRU) ELSE MH.TONGTIENAVAT END AS TONGTIENAVAT, \n " +
			" MH.TIENTE_FK, MH.HTTT_FK HTTTID, MH.SOPO, MH.NGUOITAO, MH.LYDOTT, MH.TYGIAQUYDOI AS TIGIA \n \n" +
			" , isNull(mh.lyDoTT, '') as dienGiai, khachHang_NPP_FK, mh.DOITUONGKHAC_FK \n" +
			" FROM ERP_MUAHANG MH \n"+
			" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ =  MH.NHACUNGCAP_FK \n"+
			" LEFT JOIN ERP_NHANVIEN NV ON MH.NHANVIEN_FK = NV.PK_SEQ \n"+
			" LEFT JOIN NHAPHANPHOI KH ON MH.KHACHHANG_FK = KH.PK_SEQ AND ISKHACHHANG = 1 AND KH.TRANGTHAI = '1'   \n"+
			" WHERE MH.PK_SEQ = "+Id+" \n";
	
			System.out.println(query);
			ResultSet RsDNTT = db.get(query);
			
			String ncc_fk = "";
			String nhanvienfk = "";
			String khachhang_fk = "";
			double sotienavat = 0;
			double sotienavat_Viet = 0;
			double sotiencantru = 0;
			String htttId = "";
			String tiente_fk = "";
			String nguoitao = "";
//			String lydo = "";
			String ngaymua = "";
			String tigia = "";
			String dienGiai = "";
			String khachHang_NPP_FK = null;
			String DOITUONGKHAC_FK = null;
			if (RsDNTT == null)
			{
				return false;
			}
			
			if(RsDNTT!=null)
			{
				try 
				{
					while (RsDNTT.next())
					{
						dienGiai = RsDNTT.getString("dienGiai");
						ncc_fk = RsDNTT.getString("NHACUNGCAP_FK");
						nhanvienfk =  RsDNTT.getString("NHANVIEN_FK");
						khachhang_fk = RsDNTT.getString("KHACHHANG_FK");
						ngaymua = RsDNTT.getString("NGAYMUA");
						tigia = RsDNTT.getString("TIGIA");
						sotienavat = RsDNTT.getDouble("TONGTIENAVAT");
						sotienavat_Viet = sotienavat;
						if (!htttId.trim().equals("100000"))
							sotienavat_Viet = Math.round(sotienavat * Double.parseDouble(tigia));
						sotiencantru = RsDNTT.getDouble("TONGTIENCANTRU");
						
						htttId = RsDNTT.getString("HTTTID");
						tiente_fk = RsDNTT.getString("tiente_fk");
						nguoitao = RsDNTT.getString("NGUOITAO");
//						lydo = RsDNTT.getString("LYDOTT");
						khachHang_NPP_FK = RsDNTT.getString("khachHang_NPP_FK");
						DOITUONGKHAC_FK = RsDNTT.getString("DOITUONGKHAC_FK");
					}
				} catch (SQLException e) {
					e.printStackTrace();
					return false;
				}
			}
							
			String prefix = "";
			
			if(htttId.equals("100000"))
				prefix = "DNPC";
			
			if(htttId.equals("100001"))
				prefix = "DNBN";
			
			ngaymua = getDateTime();
			
			String soChungTu_Chu = prefix + ngaymua.substring(5,7)+ ngaymua.substring(0, 4);
			String soChungTu_So = geso.traphaco.center.util.Utility.generataSoChungTu(db, soChungTu_Chu,
					geso.traphaco.center.util.Utility.tblPC_UNC , 
					htttId, "" , ngaymua);
//			String soChungTu = Utility.getSoChungTuMax(db, "ERP_ThanhToanHoaDon", htttId);
			
			//Khách hàng này của HO nên nằm trong bảng NHAPHANPHOI
			String isnpp = "null";
			if(khachhang_fk!= null && khachhang_fk.length()>0)
				isnpp = "1";
			
			
			query = "";
			if(sotiencantru > 0 && nhanvienfk != null){
				if(sotienavat > 0){
//					String sotaikhoan = "";
//					String nganhangId = null;
//					String chinhanhId = null;
//					
//					query = "SELECT TOP 1 SOTAIKHOAN, NGANHANG_FK, CHINHANH_FK FROM ERP_NGANHANG_CONGTY WHERE NPP_FK = " + this.congtyId + " ";
//					ResultSet rs = this.db.get(query);
//					if(rs.next()){
//						sotaikhoan = rs.getString("SOTAIKHOAN");
//						nganhangId = rs.getString("NGANHANG_FK");
//						chinhanhId = rs.getString("CHINHANH_FK");
//					}
//					rs.close();
					
					query = "Insert ERP_THANHTOANHOADON " +
					"( DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK\n" +
					", HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN\n" +
					", NOIDUNGTT, SOTIENTT, SOTIENTTNT, SOTIENHD\n" +
					", SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT\n" +
					", VATNT, CHENHLECHVND, TRICHPHI, SOTAIKHOAN_TP\n" +
					", NGANHANG_TP_FK, CHINHANH_TP_FK, " +
					"  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, " +
					"  CTKEMTHEO, CONGTY_FK, PREFIX, DNTT_FK, ISKTTDUYET, TRANGTHAI \n" +
					"  ,  soChungTu,soChungTu_Chu, soChungTu_So, khachHang_NPP_FK, DOITUONGKHAC_FK\n" +
					",ISNPP) " +
					"values(NULL, '" + ngaymua + "', " + ncc_fk + "," + " "+nhanvienfk +"\n" +
					", '" + htttId + "', " + " NULL, NULL , NULL \n" +
					", N'" + dienGiai + "', " + sotienavat_Viet + ", "+ sotienavat + ", " + sotienavat_Viet  + "\n" +
					", " + sotienavat + " , " +
					" 0 , 0 , 0 ,0, 0, 0, '', null , null , '"  + getDateTime() + "', '" + nguoitao + "', '" + getDateTime() + "', '" 
					+ nguoitao + "',0, " + tiente_fk + ", " + tigia + " , '0', "+khachhang_fk+" , N'', "+this.congtyId+", '"+prefix+ "', " + Id + ", '0', '0'\n" +
					", '" + (soChungTu_Chu + soChungTu_So) + "','"+soChungTu_Chu+"', '" + soChungTu_So + "', " + khachHang_NPP_FK + ", " + DOITUONGKHAC_FK + ","+isnpp+")";
			
				}
			}else { // ĐÂY LÀ TRƯỜNG HỢP NHÂN VIÊN ĐÃ TẠM ỨNG, RỒI LÀM ĐỀ NGHỊ THANH TOÁN VÀ SỐ TIỀN CẤN TRỪ > SỐ TIỀN TẠM ỨNG NÊN PHÁT SINH PHIỀU CHI/UNC
				String qr = "select COUNT(THANHTOANHOADON_FK) from ERP_DENGHITT_THANHTOANHOADON where THANHTOANHOADON_FK = " + Id;
				ResultSet rs1 = this.db.get(qr);
				int num = 0;
				
				if (rs1 != null)
				{
					if (rs1.next())
						num = rs1.getInt(1);
					rs1.close();
				}
				
				if (!(sotiencantru == 0 && num > 0))
					if(sotienavat > 0){
						query = 
								"Insert ERP_THANHTOANHOADON " +
								"( DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
								"  SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
								"  TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
								"  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, " +
								"  CTKEMTHEO, CONGTY_FK, PREFIX, DNTT_FK, TRANGTHAI \n" +
								"  ,  soChungTu, soChungTu_Chu, soChungTu_So, khachHang_NPP_FK, DOITUONGKHAC_FK\n" +
								",ISNPP) " +
								"values(NULL, '" + ngaymua + "', " + ncc_fk + "," + " "+nhanvienfk +", '" + htttId + "', " +
								" NULL, NULL , NULL , N'" + dienGiai + "', " +
								"" + sotienavat_Viet + ", "+ sotienavat + ", " + sotienavat_Viet  + ", " + sotienavat + " , " +
								" 0 , 0 , 0 ,0, 0, 0, '', null , null , '"  + getDateTime() + "', '" + nguoitao + "', '" + getDateTime() + "', '" 
								+ nguoitao + "',0, " + tiente_fk + ",  " + tigia + "  , '0', "+khachhang_fk+" , N'', "+this.congtyId+", '"+prefix+ "', " + Id + ", '0'\n" +
								", '" + (soChungTu_Chu + soChungTu_So) + "','"+soChungTu_Chu+"', '" + soChungTu_So + "', " + khachHang_NPP_FK + ", " + DOITUONGKHAC_FK + ","+isnpp+")";
					}
			}
			System.out.println(query);
			
			if (query.trim().length() > 0)
			{
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi Phiếu Chi/UNC: " + query;
					return false;
				}
				
				query = "select IDENT_CURRENT('ERP_THANHTOANHOADON') as tthdId";
				
				ResultSet rsTthd = db.get(query);	
				String tthdCurrent = "";
				if(rsTthd.next())
				{
					tthdCurrent = rsTthd.getString("tthdId");
					rsTthd.close();
				}
				
				// CẬP NHẬT MÃ CHỨNG TỪ
				query = " update ERP_THANHTOANHOADON set machungtu =  Prefix + SUBSTRING(NGAYGHINHAN, 6, 2) + SUBSTRING(NGAYGHINHAN, 0, 5) + '-' + dbo.LaySoChungTu( " + tthdCurrent + " ) " + 
						" where pk_seq = '" + tthdCurrent + "' ";
				
				System.out.println("[ERP_THANHTOANHOADON] error message:" + query);
				
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_THUTIEN: " + query;
					System.out.println("[ErpThutien.createTTHD] error message:" + this.msg);
					return false;
				}
				
				//TRONG BẢNG ERP_THANHTOANHOADON_HOADON LOAIHD = 6 LÀ ĐỀ NGHỊ THANH TOÁN
				
				query = "Insert ERP_THANHTOANHOADON_HOADON( THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT\n" +
						", SOTIENNT, CONLAI, LOAIHD, SOHOADON) " +
						"values('" + tthdCurrent + "', '" + Id + "', '" + sotienavat + "', '" + sotienavat + "'\n" +
						", " + sotienavat + ", 0 , '6', '"+ Id +"')";				
				
				System.out.println(query);
				
				if(!db.update(query))
				{
					this.msg = "Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
					return false;
				}
			}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private boolean kiemTraDinhKhoan(String Id) {
		String taikhoanCO_DS = "";
		String taikhoanNO_DS = "";
		
		String taikhoanCO_VAT = "";
		String taikhoanNO_VAT = "";
		
//		String loaidoituongCO = "";
//		String madoituongCO = "";
//		
//		String loaidoituongNO = "";
//		String madoituongNO = "";
		
		String query = 
			   "  SELECT	N'CHI PHÍ ' AS LOAIDOITUONGNO, E.PK_SEQ AS MADOITUONGNO,    \n" +  
			   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN N'NHÀ CUNG CẤP'  WHEN A.NHANVIEN_FK  IS NOT NULL THEN N'NHÂN VIÊN' when a.DOITUONGKHAC_FK is not null then N'Đối tượng khác' ELSE N'KHÁCH HÀNG' END AS LOAIDOITUONGCO,   \n" +  
			   "  CASE  WHEN  A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.PK_SEQ WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.PK_SEQ  when a.DOITUONGKHAC_FK is not null then a.DOITUONGKHAC_FK ELSE KH.PK_SEQ END AS MADOITUONGCO, \n" +  
			   "  A.NGAYMUA AS NGAYHOADON, (D.SOLUONG* D.DONGIA)  AS DOANHSO,(D.SOLUONG* D.DONGIA)*  D.PHANTRAMTHUE/100 AS THUE   ,    \n" +  
			   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK when A.khachHang_NPP_FK is not null then NPP.taiKhoan_FK when a.taiKhoanDoiTuongKhac_FK is not null then a.taiKhoanDoiTuongKhac_FK ELSE KH.TAIKHOAN_FK END AS TAIKHOANCO_DS,   \n" +  
			   "  CASE  WHEN A.NHACUNGCAP_FK  IS NOT NULL THEN NCC.TAIKHOAN_FK WHEN A.NHANVIEN_FK  IS NOT NULL THEN NV.TAIKHOAN_FK when A.khachHang_NPP_FK is not null then NPP.taiKhoan_FK when a.taiKhoanDoiTuongKhac_FK is not null then a.taiKhoanDoiTuongKhac_FK ELSE KH.TAIKHOAN_FK END AS TAIKHOANCO_VAT,   \n" +  
			   "  (SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = E.TAIKHOAN_FK AND CONGTY_FK = A.CONGTY_FK) AS TAIKHOANNO_DS,   \n" +  
			   "  ( SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13311000' AND CONGTY_FK = A.CONGTY_FK  ) AS TAIKHOANNO_VAT   \n" +
			   "  , isNull(convert(nvarchar, a.httt_fk), '') httt_fk, isNull(convert(nvarchar, a.doiTuongKhac_FK), '') doiTuongKhac_FK\n" +  
			   "  FROM ERP_MUAHANG A  \n" +  
			   "  LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = A.NHANVIEN_FK    \n" +  
			   "  LEFT JOIN ERP_NHACUNGCAP NCC ON A.NHACUNGCAP_FK = NCC.PK_SEQ  \n" + 
			   "  LEFT JOIN NHAPHANPHOI KH ON A.KHACHHANG_FK = KH.PK_SEQ AND ISKHACHHANG = 1 AND KH.TRANGTHAI = '1'    \n" +
			   "  LEFT JOIN NHAPHANPHOI NPP ON A.KHACHHANG_NPP_FK = NPP.PK_SEQ   \n" +
			   "  LEFT JOIN erp_doiTuongKhac dtk ON A.DOITUONGKHAC_FK = dtk.PK_SEQ   \n" +
			   "  LEFT JOIN ERP_MUAHANG_SP D ON A.PK_SEQ = D.MUAHANG_FK   \n" +  
			   "  LEFT JOIN ERP_NHOMCHIPHI E ON D.CHIPHI_FK = E.PK_SEQ   \n" +
			   "  LEFT JOIN ERP_MASCLON MSC ON MSC.PK_SEQ=D.SCLON_FK   \n" +
			   "    LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ=D.CPTRATRUOC_FK   \n" +
			   "    LEFT JOIN Erp_LOAICCDC LCCDC ON LCCDC.pk_seq=CCDC.LOAICCDC_FK   \n" +
			   "  WHERE A.PK_SEQ = " + Id;
		
		System.out.println("Cau tk1: \n" + query + "\n--------------------------------------------------------------");
		try{
		ResultSet rsTk = db.get(query);
		if(rsTk!= null)
		{
			while(rsTk.next())
			{
				double totalDS = Math.round(rsTk.getDouble("DOANHSO"));
				double totalVAT = Math.round(rsTk.getDouble("THUE"));
				
				taikhoanCO_DS = rsTk.getString("taikhoanCO_DS") == null ? "": rsTk.getString("taikhoanCO_DS") ;
				taikhoanNO_DS = rsTk.getString("taikhoanNO_DS") == null ? "": rsTk.getString("taikhoanNO_DS")  ;
				
				taikhoanCO_VAT = rsTk.getString("taikhoanCO_VAT") == null ? "": rsTk.getString("taikhoanCO_VAT")  ;
				taikhoanNO_VAT = rsTk.getString("taikhoanNO_VAT") == null ? "": rsTk.getString("taikhoanNO_VAT") ;
				
//				loaidoituongCO = rsTk.getString("loaidoituongCO");
//				madoituongCO = rsTk.getString("madoituongCO");
//				loaidoituongNO = rsTk.getString("loaidoituongNO");
//				madoituongNO = rsTk.getString("madoituongNO");

//				String ngayghinhan = rsTk.getString("ngayhoadon");
//				String nam = ngayghinhan.substring(0, 4);
//				String thang = ngayghinhan.substring(5, 7);
				
//				String tiente_fk = "100000";					
				String httt_fk = rsTk.getString("httt_fk");
				String doiTuongKhac_FK = rsTk.getString("doiTuongKhac_FK");
				System.out.println("httt_fk: " + httt_fk);
				System.out.println("doiTuongKhac_FK: " + doiTuongKhac_FK);
				if(totalDS > 0)
				{
					if(taikhoanCO_DS.trim().length() <= 0 && !(!httt_fk.trim().equals("100004") && doiTuongKhac_FK.trim().length() > 0))
					{
						this.msg = "KTDK1.1 Vui lòng kiểm tra tài khoản bên dưới Nhà cung cấp / Chi nhánh / Nhân viên / Khách hàng / Đối tượng khác";
						return false;
					}
					if(taikhoanNO_DS.trim().length() <= 0 )
					{
						this.msg = "KTDK1.2 Vui lòng nhập khoản mục chi phí đầy đủ ";
						return false;
					}
				}
				
				if(totalVAT > 0)
				{
					if(taikhoanCO_VAT.trim().length() <= 0
							&& !(!httt_fk.trim().equals("100004") && doiTuongKhac_FK.trim().length() > 0))
					{
						this.msg = "KTDK1.3 Vui lòng kiểm tra tài khoản bên dưới Nhà cung cấp / Chi nhánh / Nhân viên / Khách hàng / Đối tượng khác";
						return false;
					}	
					
					if(taikhoanNO_VAT.trim().length() <= 0)
					{
						this.msg = "KTDK1.4 Vui lòng nhập khoản mục chi phí đầy đủ ";
						return false;
					}	
				}
			}
			rsTk.close();
		}
		}catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	public String getDaduyet(String mhId){
		String tmp = "";
		String query =  "SELECT DUYETMUAHANG.MUAHANG_FK AS MHID, NV.PK_SEQ AS NVID, NV.DANGNHAP AS NVTEN " +
						"FROM ERP_DUYETMUAHANG DUYETMUAHANG " +
						"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = CHUCDANH.NHANVIEN_FK " +
						"WHERE DUYETMUAHANG.TRANGTHAI = '1' AND MUAHANG_FK = " + mhId + "  ";
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
			e.printStackTrace();
			return tmp;
		}
	}
	
	public void DBclose(){
		try{
			if(this.dvth != null) this.dvth.close();
			if(this.lsp != null) this.lsp.close();
			if(this.polist != null) this.lsp.close();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
		}
		if (this.db != null)
			this.db.shutDown();
		this.db = null;
	}
	
	public void NewDbUtil()
	{
		  if(this.db == null )
		  {
			   this.db  = new dbutils();
		  }
	}
	
	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}
	
	public boolean BoDuyetmuahang(String Id) {
		try
		{
			db.getConnection().setAutoCommit(false);
						
			//1. KIỂM TRA CẤP DUYỆT CỦA USER ĐĂNG NHẬP
			
			String nhanvien_fk = this.userId;
			
			String query =   	"\n SELECT distinct A.LOAICAP_FK, A.NHANVIEN_FK " +
								"\n FROM ( "+
								
								"\n 		SELECT A.LOAICAP_FK, A.NHANVIEN_FK  " +
								"\n 		FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ " +
								"\n 		WHERE A.CONGTY_FK = "+this.congtyId+" AND B.TRANGTHAI = 1 AND A.NHANVIEN_FK = " + nhanvien_fk +
								
								"\n 		UNION ALL "+
								
								"\n 		SELECT A.LOAICAP_FK, NVQL_FK NHANVIEN_FK " +
								"\n 		FROM ERP_CAPQUANLY A WHERE A.CONGTY_FK = "+this.congtyId+" AND A.TRANGTHAI = 1 AND A.NVQL_FK = "+nhanvien_fk +
								
								"\n ) A  ";
			
			System.out.println(query);
			ResultSet RsKt = db.get(query);
			int stt = 0;
			String loaicap_fk = "";
			
			if(RsKt!=null)
			{
				while(RsKt.next())
				{
					stt++;
					loaicap_fk =  RsKt.getString("LOAICAP_FK");
				}
				RsKt.close();
			}
			
			// NẾU 1 NHÂN VIÊN CÓ 2 QUYỀN => BÁO LỖI
			if(stt > 1)
			{
				this.msg = "Nhân viên này có 2 quyền. Vui lòng ktra lại";
				db.getConnection().rollback();
				return false;
			}
			
			// UPDATE QUYỀN CỦA USER
			query = " UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = 0 , lydomoduyet = N'"+this.lydomoduyet+"' WHERE NHANVIEN_FK = "+nhanvien_fk+" AND LOAICAP_FK = "+loaicap_fk+" AND MUAHANG_FK = "+Id;
			
			System.out.println(query);
			
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// UPDATE QUYỀN VÀO BẢNG ERP_MUAHANG
			
			if(loaicap_fk.equals("10000")) // CẤP QUẢN LÝ TRỰC TIẾP
			{
				query = " UPDATE ERP_MUAHANG SET ISQLTT = 0 WHERE PK_SEQ = "+Id;	
				
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaicap_fk.equals("10001")) // QUẢN LÝ CS
			{
				query = " UPDATE ERP_MUAHANG SET ISCS = 0 WHERE PK_SEQ = "+Id;	
				
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaicap_fk.equals("10002")) // DUYỆT ĐNTT/ĐNTU
			{
				query = " UPDATE ERP_MUAHANG SET ISDUYETCHI = 0 WHERE PK_SEQ = "+Id;	
				
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaicap_fk.equals("10003")) // KẾ TOÁN TỔNG HỢP
			{
				query = " UPDATE ERP_MUAHANG SET ISKTTH = 0 WHERE PK_SEQ = "+Id;	
				
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaicap_fk.equals("10004")) // KẾ TOÁN TRƯỞNG
			{
				// KIẾM TRA XEM PHIẾU NÀY ĐÃ RA PHIẾU CHI CHƯA - NẾU RA RỒI KHÔNG ĐƯỢC PHÉP BỎ DUYỆT
				
				query =  " SELECT count(A.HOADON_FK) dem FROM ERP_THANHTOANHOADON_HOADON A INNER JOIN ERP_THANHTOANHOADON B ON A.THANHTOANHD_FK = B.PK_SEQ" +
						 " WHERE A.LOAIHD = 6 AND B.TRANGTHAI != 2 AND A.HOADON_FK = "+Id+"  ";
				
				ResultSet rs = db.get(query);
				
				int count = 0;
				if(rs!=null)
				{
					if(rs.next())
					{
						count = rs.getInt("dem");
					}
					rs.close();	
				}
				
				if(count <= 0)
				{				
					query = " UPDATE ERP_MUAHANG SET ISKTT = 0 , TRANGTHAI = 0, isDACHI = 0 WHERE PK_SEQ = "+Id+" and trangthai=1";	
					
					if (db.updateReturnInt(query)!=1)
					{
						this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					this.msg = "Đề nghị thanh toán đã ra phiếu chi rồi. Bạn không được phép bỏ duyệt.";
					db.getConnection().rollback();
					return false;
				}
			}
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		return true;
	}
	
	public String getLydoxoa() {
		return this.lydoxoa;
	}
	
	public void setLydoxoa(String lydoxoa) {
		this.lydoxoa = lydoxoa;
	}

	public String getLydomoduyet() {
		return this.lydomoduyet;
	}

	public void setLydomoduyet(String lydomoduyet) {
		this.lydomoduyet = lydomoduyet;
	}
	
	public boolean Xoamuahang(String Id) {
		try
		{
			db.getConnection().setAutoCommit(false);
						
			//1. KIỂM TRA CẤP DUYỆT CỦA USER ĐĂNG NHẬP
			
			String nhanvien_fk = this.userId;
			
			String query =  "\n SELECT distinct A.LOAICAP_FK, A.NHANVIEN_FK " +
							"\n FROM ( "+
							
							"\n 		SELECT A.LOAICAP_FK, A.NHANVIEN_FK  " +
							"\n 		FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ " +
							"\n 		WHERE A.CONGTY_FK = "+this.congtyId+" AND B.TRANGTHAI = 1 AND A.NHANVIEN_FK = " + nhanvien_fk +
							
							"\n 		UNION ALL "+
							
							"\n 		SELECT A.LOAICAP_FK, NVQL_FK NHANVIEN_FK " +
							"\n 		FROM ERP_CAPQUANLY A WHERE A.CONGTY_FK = "+this.congtyId+" AND A.TRANGTHAI = 1 AND A.NVQL_FK = "+nhanvien_fk +
							
							"\n ) A  ";
			
			System.out.println(query);
			ResultSet RsKt = db.get(query);
			int stt = 0;
			String loaicap_fk = "";
			
			if(RsKt!=null)
			{
				while(RsKt.next())
				{
					stt++;
					loaicap_fk =  RsKt.getString("LOAICAP_FK");
				}
				RsKt.close();
			}
			
			// NẾU 1 NHÂN VIÊN CÓ 2 QUYỀN => BÁO LỖI
			if(stt > 1)
			{
				this.msg = "Nhân viên này có 2 quyền. Vui lòng ktra lại";
				db.getConnection().rollback();
				return false;
			}
			
			
			// CẬP NHẬT QUYỀN CỦA USER TRONG ERP_DUYETMUAHANG
			
			query = " SELECT count(MUAHANG_FK) dem FROM ERP_DUYETMUAHANG WHERE NHANVIEN_FK = "+nhanvien_fk +" AND LOAICAP_FK = "+loaicap_fk+ " AND MUAHANG_FK = "+Id;
			
			System.out.println(query);
			RsKt = db.get(query);
			int count = 0;
			
			if(RsKt!=null)
			{
				while(RsKt.next())
				{
					stt++;
					count = RsKt.getInt("dem");
				}
				RsKt.close();
			}
			
			if(count>0) // ĐÃ CÓ TRONG BẢNG DUYỆT
				query = " UPDATE ERP_DUYETMUAHANG SET TRANGTHAI = 2, lydoxoa = N'"+this.lydoxoa+"' WHERE NHANVIEN_FK = "+nhanvien_fk+" AND LOAICAP_FK = "+loaicap_fk+" AND MUAHANG_FK = "+Id;			
			else			
				query = " INSERT ERP_DUYETMUAHANG ( MUAHANG_FK, NHANVIEN_FK, LOAICAP_FK, TRANGTHAI, THUTU , lydoxoa) SELECT MUAHANG_FK, NHANVIEN_FK, LOAICAP_FK, 1, THUTU, N'"+this.lydoxoa+"'  FROM ERP_DUYETMUAHANG_NHAP WHERE MUAHANG_FK = "+Id+" AND NHANVIEN_FK = "+nhanvien_fk;
						
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
						

			// KIẾM TRA XEM PHIẾU NÀY ĐÃ RA PHIẾU CHI CHƯA - NẾU CÓ RÀNG K CHO XÓA
			
			query =  " SELECT count(A.HOADON_FK) dem FROM ERP_THANHTOANHOADON_HOADON A INNER JOIN ERP_THANHTOANHOADON B ON A.THANHTOANHD_FK = B.PK_SEQ" +
					 " WHERE A.LOAIHD = 6 AND B.TRANGTHAI != 2 AND A.HOADON_FK = "+Id+"  ";
			
			ResultSet rs = db.get(query);
			
			count = 0;
			if(rs!=null)
			{
				if(rs.next())
				{
					count = rs.getInt("dem");
				}
				rs.close();	
			}
			
			if(count>0)
			{
				this.msg = "Đề nghị thanh toán này đã ra phiếu chi rồi. Bạn không thể xóa phiếu này. ";
				db.getConnection().rollback();
				return false;
			}
						
			// UPDATE QUYỀN VÀO BẢNG ERP_MUAHANG
			
			query = " UPDATE ERP_MUAHANG SET TRANGTHAI = 3 WHERE PK_SEQ = "+Id+" and trangthai=0";	
			
			System.out.println(query);
			
			if (db.updateReturnInt(query)!=1)
			{
				this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
								
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		return true;
	}

	public String getLydosua() {
		return this.lydosua;
	}

	public void setLydosua(String lydosua) {
		this.lydosua = lydosua;
	}
	
	public boolean Suamuahang(String Id) {
		try
		{
			db.getConnection().setAutoCommit(false);
										
			String query = " INSERT ERP_DUYETMUAHANG ( MUAHANG_FK, NHANVIEN_FK, LYDOSUA ) SELECT "+Id+", "+this.userId+" , N'"+this.lydosua+"' ";
						
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// CẬP NHẬT NGƯỜI SỬA PHIẾU
			query = " UPDATE ERP_MUAHANG SET NGUOISUA = "+this.userId+" WHERE PK_SEQ = "+Id;
			
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			return false;
		}
		
		return true;
	}

	public boolean boChot(String id,String action)
	{
		try {
			db.getConnection().setAutoCommit(false);
						
			String query = "";
			if(action.equals("boChotNhanVien")) // TRUONG PHONG DUYET
			{
				query = " SELECT NHANVIEN_FK, SUM(ISNULL(isTP,0)) isTP \n"+
						" FROM ERP_CHUCDANH WHERE DVTH_FK IN (SELECT PHONGBAN_FK FROM NHANVIEN WHERE PK_SEQ = ( SELECT NGUOITAO FROM ERP_MUAHANG WHERE PK_SEQ = "+id+")) \n"+
						" AND NHANVIEN_FK = "+this.userId+" AND TRANGTHAI = 1 \n"+
						" GROUP BY NHANVIEN_FK \n";
				
				ResultSet Rs = db.get(query);
				
				int isTP = 0;
				
				if(Rs!=null)
				{
					while(Rs.next())
					{
						isTP = Rs.getInt("isTP");
					}
					Rs.close();
				}
				
				if(isTP==0)
				{
					this.msg = " Bạn không được cấp quyền trưởng phòng để mở duyệt phiếu này. Vui lòng xem lại dữ liệu nền chức danh";
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
				else
				{
					query = " UPDATE ERP_MUAHANG SET ISCS = 0,ISDACHOT=0, TRUONGPHONGMOCHOT_FK = "+this.userId +",NGAYMOCHOTNHANVIEN=getdate() WHERE PK_SEQ = " +id;
					
					if(!db.update(query))
					{
						this.msg = "Không thể mở duyệt được phiếu. Vui lòng liên hệ GESO! ";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
				}				
			}
			
			if(action.equals("boChotTruongPhong"))
			{
				query = " SELECT NHANVIEN_FK, SUM(ISNULL(isKTV,0)) isKTV \n"+
						" FROM ERP_CHUCDANH \n " +
						" WHERE NHANVIEN_FK = "+this.userId+" AND TRANGTHAI = 1 \n"+
						" GROUP BY NHANVIEN_FK \n";
		
				ResultSet Rs = db.get(query);
				
				int isKTV = 0;
				
				if(Rs!=null)
				{
					while(Rs.next())
					{
						isKTV = Rs.getInt("isKTV");
					}
					Rs.close();
				}
				
				if(isKTV==0)
				{
					this.msg = " Bạn không được cấp quyền kế toán gán mã chi phí. Vui lòng xem lại dữ liệu nền chức danh";
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
				else
				{
					query = " UPDATE ERP_MUAHANG SET ISTP = 0, KETOANMOCHOT_FK = "+this.userId +",NGAYMOCHOTTRUONGPHONG=GETDATE() WHERE PK_SEQ = " +id;
					
					if(!db.update(query))
					{
						this.msg = "Không thể mở duyệt được phiếu. Vui lòng liên hệ GESO! ";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
					

		
				}				
			}
			

			
			db.getConnection().commit();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return true;
	}	

	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public ResultSet getHtttList() {
		return HtttList;
	}

	public void setHtttList(ResultSet htttList) {
		HtttList = htttList;
	}

	public String getHtttid() {
		return htttid;
	}

	public void setHtttid(String htttid) {
		this.htttid = htttid;
	}
	
	private boolean updateNguyenGia(String Id) {
		try
		{
			
		String query=" Select mh.PK_SEQ AS SOCHUNGTU, mh.ngaymua,sp.THANHTIENVIET AS NGUYENGIA," +
				" SCLON_FK as MASCLON,CPTRATRUOC_FK AS CPTRATRUOC," +
				" CASE WHEN sp.SCLON_FK IS not null then 1 " +
				" when sp.CPTRATRUOC_FK IS not null then 2 " +
				" else '' end as loai From erp_muahang_sp sp " +
				" inner join ERP_MUAHANG mh on mh.PK_SEQ=sp.MUAHANG_FK  WHERE MH.PK_SEQ="+Id;
		
		ResultSet rs= db.get(query);
		while (rs.next())
		{
			String loai = rs.getString("loai");
			String ngaytang= rs.getString("ngaymua");
			String maSCLon_fk = rs.getString("MASCLON");
			String cpTraTruoc = rs.getString("CPTRATRUOC");
			double thanhtien = rs.getDouble("NGUYENGIA");
			String sochungtu= rs.getString("SOCHUNGTU");
			if(loai.equals("1"))
			{
				this.msg=Erp_MaSCLon.InsertDieuChinhMSCL(db, maSCLon_fk, ngaytang, Double.toString(thanhtien), sochungtu, "Đề nghị thanh toán", "ERP_MUAHANG");
				if(this.msg.length()>0)
				{
					
					return false;
				}
			}else if(loai.equals("2"))
			{
				query = " select * from erp_congcudungcu where pk_seq = "+cpTraTruoc +" and trangthai = 0";
				ResultSet rsCP = db.get(query);
				if(rsCP.next())
				{
					query="Update ERP_CONGCUDUNGCU set trangthai=1, dongia= "+Double.toString(thanhtien) +", soluong=1 , thanhtien ="+Double.toString(thanhtien) +"" +
							" where pk_seq = "+cpTraTruoc ;
					int result= db.updateReturnInt(query);
					if(result<=0)
					{
						
						this.msg= "Không thể cập nhập nguyên giá chi phí phân bổ";
						return false;
					}
				}else
				{
					query ="insert into ERP_CONGCUDUNGCU_DIEUCHINH values( "+cpTraTruoc +" ,"+Double.toString(thanhtien)+", N'Đề nghị thanh toán' , 'ERP_MUAHANG', "+sochungtu+" ," +
							"'"+ngaytang+"',0) ";
					int result= db.updateReturnInt(query);
					if(result<=0)
					{
						this.msg= "Không thể cập nhập nguyên giá chi phí phân bổ";
						return false;
					}
				}
			}
		}
		}catch (Exception e) {
			e.printStackTrace();
			this.msg="Lỗi khi chốt. Vui lòng liên hệ admin để xử lý";
			return false;
		}
		return true;
	}
	public static void main ( String args [  ]  )   {
		  
  		try{
  			Duyetdonmuahang  mh= new Duyetdonmuahang();
  			String[] param = {"111522", "1"};
  			String result = mh.db.execProceduce2("XuLyKeToan_DDNTT_PHATSINHKT", param);
  			if (result.trim().length() > 0)
  			{
  				
  				mh.msg = "DKTT1.1 Không thể duyệt được phiếu tạm ứng: Không thể hạch toán\n" + result;
  				System.out.println(mh.msg);
  				return ;
  			}
  		
  		
  			
  		}catch(Exception er){
  			er.printStackTrace();
  		}
  		
  		 
    }
	
	
	
}