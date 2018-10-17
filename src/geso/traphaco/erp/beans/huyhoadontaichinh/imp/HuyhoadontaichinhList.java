package geso.traphaco.erp.beans.huyhoadontaichinh.imp;

import geso.traphaco.erp.beans.huyhoadontaichinh.IHuyhoadontaichinhList;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;

import java.sql.ResultSet;
import java.sql.SQLException;

public class HuyhoadontaichinhList extends Phan_Trang implements IHuyhoadontaichinhList
{



	String userId;
	String trangthai;
	String sochungtu;
	String tungay;
	String denngay;
	String khId;
	String msg;

	ResultSet huyhoadontaichinhRs;
	ResultSet khRs;
	
	String huyhoadontaichinhId;
	
	
	private int num;
	private int[] listPages;
	private int currentPages;

	dbutils db;

	public HuyhoadontaichinhList()
	{
		this.userId = "";
		this.trangthai= "";
		this.sochungtu="";
		this.msg= "";
		this.tungay="";
		this.denngay="";
		this.khId="";

		currentPages =1;
		num = 1;

		this.huyhoadontaichinhId = "";
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

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getMsg()
	{
		return this.msg;
	}

	public void setMsg(String msg)
	{
		this.msg = msg;
	}

	public void init(String query)
	{
		String sql = "";

		
			sql =	" SELECT HHD.PK_SEQ, HHD.HOADON_FK, KHACHHANG_FK, KH.TEN AS KHTEN, HHD.TRANGTHAI, HHD.NGAYTAO, NT.TEN AS NVTEN1, HHD.NGAYSUA, NS.TEN AS NVTEN2 "+
					" FROM ERP_HUYHOADONTAICHINH HHD "+
					" INNER JOIN ERP_KHACHHANG KH ON HHD.KHACHHANG_FK= KH.PK_SEQ "+
					" INNER JOIN NHANVIEN NT ON HHD.NGUOITAO= NT.PK_SEQ "+
					" INNER JOIN NHANVIEN NS ON HHD.NGUOISUA= NS.PK_SEQ "+
					" WHERE 1=1 ";
			
		if(this.tungay.length() >0){
			sql += " AND HHD.NGAYTAO>='"+this.tungay+"'";
		}
		
		if(this.denngay.length() >0){
			sql += " AND HHD.NGAYTAO<='"+this.denngay+"'";
		}
		
		if(this.trangthai.length() >0){
			sql += " AND HHD.TRANGTHAI="+this.trangthai+" ";
		}
		
		if(this.sochungtu.length() >0){
			sql += " AND HHD.HOADON_FK='"+this.sochungtu+"'";
		}
		
		if(this.khId.length() >0){
			sql += " AND HHD.KHACHHANG_FK='"+this.khId+"'";
		}
		
		System.out.println("1. init list :" +sql);

	/*	this.huyhoadontaichinhRs = db.get(sql);*/
		this.huyhoadontaichinhRs = createSplittingData(30, 10, "NGAYTAO ", sql);
		
		
		query= " SELECT PK_SEQ, MA, TEN FROM ERP_KHACHHANG ";
		System.out.println("2. khRs : "+ query);
		this.khRs= this.db.get(query);
		
		
		
	}

	public void DbClose()
	{
		try
		{
			if(this.huyhoadontaichinhRs != null)
				this.huyhoadontaichinhRs.close();
			this.db.shutDown();
		}
		catch (SQLException e) {}
	}

	public ResultSet getHuyhoadontaichinhRs()
	{
		return this.huyhoadontaichinhRs;
	}

	public void setHuyhoadontaichinhRs(ResultSet huyhoadontaichinhRs)
	{
		this.huyhoadontaichinhRs = huyhoadontaichinhRs;
	}

	public String getHuyhoadontaichinhId()
	{
		return this.huyhoadontaichinhId;
	}

	public void setHuyhoadontaichinhId(String huyhoadontaichinhId)
	{
		this.huyhoadontaichinhId = huyhoadontaichinhId;
	}


	public String getsochungtu() {
	
		return this.sochungtu;
	}


	public void setsochungtu(String sochungtu) {
		this.sochungtu= sochungtu;
		
	}


	public int getCurrentPage() {
		
		return this.currentPages;
	}


	public void setCurrentPage(int current) {
		this.currentPages= current;
		
	}


	public int[] getListPages() {
	
		return this.listPages;
	}


	public void setListPages(int[] listPages) {
		this.listPages = listPages;
		
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


	public void chot(String Id) {
		
		String query="";
		dbutils db = new dbutils();
		
		try{
		db.getConnection().setAutoCommit(false);
		Utility util = new Utility();
		
		query="update ERP_HUYHOADONTAICHINH set TRANGTHAI=1 where PK_SEQ='"+Id+"' ";
		if(!(db.update(query))){
			
			this.msg=" Lỗi" + query;
			db.getConnection().rollback();
		}
		
		//GHI NHẬN TÀI KHOẢN KẾ TOÁN
		 
		String ngayghinhan = "";
		String ngaychungtu= "";
		String loaidonhang = "";
		String khachhangId = "";
		String loaisp = "";
		String kenhbanhangId = "";
		String tienteId = "";
		String tigia = "";
		
		String taikhoanNO_SOHIEU = "";
		String taikhoanCO_SOHIEU = "";
		
		String nam = "";
		String thang = "";
		
		double tienhang = 0;
		double tienvat = 0;
		double chietkhau = 0;
		
		query = 
			"SELECT HD.PK_SEQ, HD.NGAYXUATHD, HD.NGAYGHINHAN, DH.LOAIDONHANG, HD.KHACHHANG_FK, ISNULL(TIGIA,1) AS TIGIA, "
		  + "      HDSP.TIENHANG, ISNULL(HD.TIENCKTHUONGMAI,0) AS TIENCKTHUONGMAI , HD.VAT, ISNULL(HD.TIENTE_FK,100000) as TIENTEID, \n"+
			"      (SELECT TOP 1 b.LOAISANPHAM_FK FROM ERP_HOADON_SP a inner join ERP_SANPHAM b on a.SANPHAM_FK = b.PK_SEQ WHERE HOADON_FK = HD.PK_SEQ) as LOAISP_FK , \n"+
			"      KH.KENHBANHANG_FK, \n"+
			"      (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = KH.TAIKHOAN_FK ) AS TAIKHOAN_NOKH \n"+
			"FROM ERP_HOADON HD INNER JOIN ERP_HOADON_DDH HDDH on HD.PK_SEQ = HDDH.HOADON_FK "
			+ "                 INNER JOIN "
			+ "                 ( SELECT HOADON_FK, SUM(SOLUONG*DONGIA-TIENCK) AS TIENHANG "
			+ "                   FROM ERP_HOADON_SP"
			+ "                   GROUP BY HOADON_FK) HDSP ON HD.Pk_SEQ = HDSP.HOADON_FK \n"+
			"				    INNER JOIN ERP_DONDATHANG DH ON HDDH.DDH_FK = DH.PK_SEQ \n"+
			"				    INNER JOIN ERP_KHACHHANG KH ON HD.KHACHHANG_FK = KH.PK_SEQ \n"+
			"WHERE HD.PK_SEQ = (SELECT HOADON_FK FROM ERP_HUYHOADONTAICHINH WHERE PK_SEQ = "+ Id +" )";
		
		System.out.println("Câu lấy DL "+ query);
		
		System.out.println("Câu lấy DL "+ query);
		ResultSet rs = db.get(query);
		if(rs!= null)
		{
			while(rs.next())
			{
				 ngayghinhan = rs.getString("NGAYGHINHAN");
				 ngaychungtu = rs.getString("NGAYXUATHD");
				 loaidonhang = rs.getString("LOAIDONHANG");
				 khachhangId = rs.getString("KHACHHANG_FK");
				 loaisp =  rs.getString("LOAISP_FK");
				 kenhbanhangId =  rs.getString("KENHBANHANG_FK");
				 tienteId =  rs.getString("tienteId");
				 tigia =  rs.getString("tigia");
					
									
				 tienhang = (-1)*rs.getDouble("tienhang");
				 tienvat = (-1)*rs.getDouble("VAT");
				 chietkhau = (-1)*rs.getDouble("TIENCKTHUONGMAI") ;
						
				 nam = ngayghinhan.substring(0, 4);
				 thang = ngayghinhan.substring(5, 7);
				
			}
			rs.close();
		}
		
		String queryLayTK = "";
		
		if(loaidonhang.equals("1"))  // DON HANG BAN
		{
			if(loaisp.equals("100006")) // LOẠI SP LÀ THANHPHAM
			{
			 queryLayTK ="SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO,"
					         + "       (case when "+ kenhbanhangId +" = '100000' then '51121100' "
					         + "       		 when "+ kenhbanhangId +" = '100001' then '51121200' "
					         + "        	 when "+ kenhbanhangId +" = '100007' then '51122000' "
					         + "       		 else '51121300' "
					         + "       end) as TAIKHOAN_CO ,"
					         + "       (case when "+ kenhbanhangId +" = '100000' then '52110000' "
					         + "       		 when "+ kenhbanhangId +" = '100001' then '52120000' "
					         + "        	 when "+ kenhbanhangId +" = '100007' then '52140000' "
					         + "        	 when "+ kenhbanhangId +" = '100008' then '52130000' "
					         + "       end) as TAIKHOAN_NOCK "
					         + "FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ  "
					         + "WHERE a.PK_SEQ = "+ khachhangId +" ";
			
			}
			
		}else if(loaidonhang.equals("2") || loaidonhang.equals("3") || loaidonhang.equals("4"))  // BIEU TANG
		{
			 queryLayTK ="SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO,"
			         + "         '51122000' as TAIKHOAN_CO,"
			         + "       (case when "+ kenhbanhangId +" = '100000' then '52110000' "
			         + "       		 when "+ kenhbanhangId +" = '100001' then '52120000' "
			         + "        	 when "+ kenhbanhangId +" = '100007' then '52140000' "
			         + "        	 when "+ kenhbanhangId +" = '100008' then '52130000' "
			         + "       end) as TAIKHOAN_NOCK "
			         + "FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ  "
			         + "WHERE a.PK_SEQ = "+ khachhangId +" ";
			
		}else if(loaidonhang.equals("5") ) // DON HANG NOI BO
		{
			queryLayTK ="SELECT b.SOHIEUTAIKHOAN as TAIKHOAN_NO,"
			         + "         '51121000' as TAIKHOAN_CO , "
			         + "       (case when "+ kenhbanhangId +" = '100000' then '52110000' "
			         + "       		 when "+ kenhbanhangId +" = '100001' then '52120000' "
			         + "        	 when "+ kenhbanhangId +" = '100007' then '52140000' "
			         + "        	 when "+ kenhbanhangId +" = '100008' then '52130000' "
			         + "       end) as TAIKHOAN_NOCK "
			         + "FROM  ERP_KHACHHANG a inner join ERP_TAIKHOANKT b on a.TAIKHOAN_FK = b.PK_SEQ  "
			         + "WHERE a.PK_SEQ = "+ khachhangId +" ";
		}
		// gài tài khoan kế toán 
		
		System.out.println("5.Query lay tai khoan: " + queryLayTK);
		if(queryLayTK.trim().length()>0)
		{		
			ResultSet tkRs = db.get(queryLayTK);
			if(tkRs != null)
			{
				while(tkRs.next())
					{
						taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
						taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_CO");
					    
				
					if(tienhang > 0)
					{
						if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
						{
							tkRs.close();
							this.msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							db.getConnection().rollback();
	
						}
					
					//UPDATE NO-CO NEW
					
						this.msg = util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
								Double.toString(tienhang), Double.toString(tienhang), "Khách hàng", khachhangId, "0", "", "", tienteId, "", tigia, Double.toString(tienhang), Double.toString(tienhang), "Hóa đơn - Tiền hàng ");
						if(this.msg.trim().length()>0)
						{
							tkRs.close();
							this.msg = "Loi update: " +this.msg;
							System.out.println("Loi khi chot: " +this.msg);
							db.getConnection().rollback();
						}
					}
					
					// VAT
					if(tienvat > 0)
					{

						taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
						taikhoanCO_SOHIEU = "33311000";
						
						if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
						{
							this.msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							tkRs.close();
							db.getConnection().rollback();
	
						}
					
					
						this.msg = util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn", Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
								Double.toString(tienvat), Double.toString(tienvat), "Khách hàng", khachhangId, "0", "", "", tienteId, "", tigia, Double.toString(tienvat), Double.toString(tienvat), "Hóa đơn - VAT ");
						if(this.msg.trim().length()>0)
						{
							tkRs.close();
							this.msg = "Loi update: " +this.msg;
							System.out.println("Loi khi chot: " +this.msg);
							db.getConnection().rollback();
						}
					
					}
					
					// CHIẾT KHẤU
					if(chietkhau > 0)
					{

						taikhoanNO_SOHIEU = tkRs.getString("TAIKHOAN_NOCK");
						taikhoanCO_SOHIEU = tkRs.getString("TAIKHOAN_NO");
						
						if(taikhoanNO_SOHIEU.trim().length() <= 0 || taikhoanCO_SOHIEU.trim().length() <= 0)
						{
							this.msg = "Khách hàng hoặc nội dung nhập tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							tkRs.close();
							db.getConnection().rollback();
	
						}
					
					
						this.msg = util.Update_TaiKhoan_TheoSoHieu(db, thang, nam, ngaychungtu, ngayghinhan, "Hóa đơn",Id, taikhoanNO_SOHIEU, taikhoanCO_SOHIEU, "",
								Double.toString(chietkhau), Double.toString(chietkhau), "Khách hàng", khachhangId, "0", "", "", tienteId, "", tigia, Double.toString(chietkhau), Double.toString(chietkhau), "Hóa đơn -Chiết khấu ");
						if(this.msg.trim().length()>0)
						{
							tkRs.close();
							this.msg = "Loi update: " +this.msg;
							System.out.println("Loi khi chot: " +this.msg);
							db.getConnection().rollback();
						}
					
					}
					
			  }tkRs.close();
			}
		}
		db.getConnection().commit();
		db.getConnection().setAutoCommit(true);
		
		}
		catch(Exception er)
		{
			db.update("rollback");
			er.printStackTrace();
			this.msg =  "LOI KHONG THE CHOT :";	
		}
	}
		


	public String gettungay() {
		
		return this.tungay;
	}


	public void settungay(String tungay) {
		this.tungay=tungay;
		
	}


	public String getdenngay() {

		return this.denngay;
	}


	public void setdenngay(String denngay) {
		this.denngay=denngay;
		
	}



	public void delete(String Id) {
		String query="";
		query="update ERP_HUYHOADONTAICHINH set TRANGTHAI=2 where PK_SEQ='"+Id+"' ";
		if(!(db.update(query))){
			this.msg=" Lỗi" + query;
			
		}
	}


	public ResultSet getkhachhang() {
	
		return this.khRs;
	}


	public void setkhachhang(ResultSet khachhang) {
		this.khRs= khachhang;
		
	}


	public String getkhId() {
		
		return this.khId;
	}


	public void setkhId(String khId) {
		this.khId= khId;
		
	}




	
}
