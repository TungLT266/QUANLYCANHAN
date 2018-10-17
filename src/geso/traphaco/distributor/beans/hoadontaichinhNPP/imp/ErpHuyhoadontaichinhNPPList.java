package geso.traphaco.distributor.beans.hoadontaichinhNPP.imp;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import geso.traphaco.distributor.beans.hoadontaichinhNPP.IErpHuyhoadontaichinhNPPList;
//import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.center.util.IPhanTrang;
import geso.traphaco.center.util.PhanTrang;
import geso.traphaco.center.util.Phan_Trang;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.util.Utility;
public class ErpHuyhoadontaichinhNPPList extends Phan_Trang implements IErpHuyhoadontaichinhNPPList
{

	String userId;
	String trangthai;
	String sochungtu;
	String tungay;
	String denngay;
	String khId;
	String sohoadon;
	String msg;
	String ctyId;
	ResultSet huyhoadontaichinhRs;
	ResultSet khRs;
	
	String huyhoadontaichinhId;
	
	
	private int num;
	private int[] listPages;
	private int currentPages;

	dbutils db;

	public ErpHuyhoadontaichinhNPPList()
	{
		this.userId = "";
		this.trangthai= "";
		this.sochungtu="";
		this.msg= "";
		this.tungay="";
		this.denngay="";
		this.khId="";
		this.sohoadon = "";
		currentPages =1;
		num = 1;

		this.huyhoadontaichinhId = "";
		this.ctyId = "";
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

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	
	public void init(String query)
	{
		String sql = "";

		if(query.length() == 0){
			sql =	" SELECT HHD.PK_SEQ, HHD.HOADON_FK, HHD.SOHOADON, \n " +
					" CASE WHEN HHD.KHACHHANG_FK IS NOT NULL THEN \n " +
					"	'KH' + CONVERT(VARCHAR, HHD.KHACHHANG_FK)  \n " +
					" ELSE \n " +
					"	'NPP' + CONVERT(VARCHAR, HHD.NPP_FK) \n " +
					" END AS KHID, \n " +

					" CASE WHEN HHD.KHACHHANG_FK IS NOT NULL THEN	 \n " +
					" 	KH.TEN  \n " +
					" ELSE \n " +
					"	NPP.TEN	 \n " +
					" END AS KHTEN,  \n " +
					" HHD.TRANGTHAI, HHD.NGAYTAO, NT.TEN AS NVTEN1, HHD.NGAYSUA, NS.TEN AS NVTEN2, HHD.LOAIHD " +
					" FROM ERP_HUYHOADONTAICHINH HHD "+
					" LEFT JOIN KHACHHANG KH ON HHD.KHACHHANG_FK= KH.PK_SEQ  " +
					" LEFT JOIN NHAPHANPHOI NPP ON HHD.NPP_FK = NPP.PK_SEQ " +
					" INNER JOIN NHANVIEN NS ON HHD.NGUOISUA= NS.PK_SEQ " +
					" INNER JOIN NHANVIEN NT ON HHD.NGUOITAO = NT.PK_SEQ  " +
					" WHERE 1=1 ";
			
		
			System.out.println("1. init list :" +sql);
		}else{
			sql = query;
		}

		this.huyhoadontaichinhRs = createSplittingData(30, 10, "NGAYTAO DESC", sql);
		
		
		query = "SELECT  'KH'+CAST(PK_SEQ AS NVARCHAR(50)) AS PK_SEQ, '[KH] '+ MAFAST MA, TEN \n " +
				"FROM KHACHHANG \n " +
				"WHERE TRANGTHAI = 1 AND CONGTY_FK IN(" + this.ctyId + ")  \n " +

				"UNION ALL  \n " +
				"SELECT 'NPP' + CONVERT(VARCHAR, PK_SEQ), MA, TEN  \n " +
				"FROM NHAPHANPHOI  \n " +
				"WHERE TRANGTHAI = 1 AND CONGTY_FK IN(" + this.ctyId + ")   \n ";
		System.out.println("2. khRs : "+ query);
		this.khRs = this.db.get(query);
		
	}

	public void DbClose()
	{
		try
		{
			if(this.khRs != null) this.khRs.close();
			
			if(this.huyhoadontaichinhRs != null)
				this.huyhoadontaichinhRs.close();
			
			this.db.shutDown();
		}
		catch (java.sql.SQLException e) {}
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

	public boolean kiemtraphatsinh(String Id, String trangthai){
		int num = 0;
		if(trangthai.equals("1")){
			String query = "SELECT COUNT(*) AS NUM FROM ERP_PHATSINHKETOAN WHERE SOCHUNGTU = " + Id + " AND LOAICHUNGTU LIKE N'Hủy hóa đơn%' ";
			ResultSet rs = this.db.get(query);
			try{
				rs.next();
				num = rs.getInt("NUM");
				rs.close();
			

			}catch(java.sql.SQLException e){}
		
			if(num > 0) return true;
			else return false;
		}else{
			return true;
		}
	}
	
	public boolean chot(String Id, String loaiHD) {

		try
		{
			Utility util = new Utility();
			msg = util.Check_Huy_NghiepVu_KhoaSo("ERP_HUYHOADONTAICHINH", Id, "NGAYHUY", db);
			if(msg.length()>0)
				return false;
			
			String query = "SELECT NGAYHUY FROM ERP_HUYHOADONTAICHINH WHERE PK_SEQ = " + Id + "";
			
			ResultSet rs = db.get(query);
			rs = db.get(query);
			rs.next();
			String ngayhuy = rs.getString("NGAYHUY");
			rs.close();
			
			db.getConnection().setAutoCommit(false);
						
			query = "update ERP_HOADONNPP set trangthai = '5', NgaySua='" + this.getDateTime()+"' " +
					"where pk_seq IN (SELECT HOADON_FK FROM ERP_HUYHOADONTAICHINH WHERE PK_SEQ = '" + Id + "')   ";
			System.out.println(query);
			
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Hóa đơn đã chuyển trạng thái ,vui lòng xem lại ";
				db.getConnection().rollback();
				return false;
			}
			
			query = "update ERP_HUYHOADONTAICHINH set trangthai = '1', NgaySua='" + this.getDateTime()+"' " +
					"where pk_seq = '" + Id + "' and trangthai in (0)  ";
			System.out.println(query);
	
			if(db.updateReturnInt(query)!=1)
			{
				msg = "Chốt hủy hóa đơn không thành công ";
				db.getConnection().rollback();
				return false;
			}

			query = "INSERT INTO ERP_HOADONNPP(NGAYXUATHD, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, DONDATHANGNPP_FK, SOHOADON, KYHIEU, \n " +
				    "CHIETKHAU, TONGTIENAVAT, VAT, TONGTIENBVAT, HINHTHUCTT, GHICHU, LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, HOPDONG,  \n " +
					"CREATED_DATE, NGUOIMUA, FROM_HOADON_GOC, INNGUOIMUA, TENKHIN, LOAIHOADON, KHO_FK, MAUHOADON, DDKD_FK, KBH_FK, DAINPGH, TENKHACHHANG, \n " +
					"DIACHI, MASOTHUE, MAVV, NHOMKENH_FK, TENXUATHD, KHGHINO, CONGTY_FK, GHICHU2, ISKM, TIENTE_FK, NGAYGHINHAN, TYGIA, ISTACHHD, NHANVIEN_FK, \n " +
					"THOIDIEMCHOT, NGUOICHOT, KHACHHANGKG_FK, PK_SEQ_TAM, TOOLTIP, CHUYENSALES) " +
					
					"SELECT  " +
					"'" + ngayhuy + "', '2', '" + this.getDateTime() + "', " + this.userId + ", '" + this.getDateTime() + "',  " + this.userId + ", " +
					"DONDATHANGNPP_FK, SOHOADON, KYHIEU, \n " +
				    "(-1)*CHIETKHAU, (-1)*TONGTIENAVAT, (-1)*VAT, (-1)*TONGTIENBVAT, HINHTHUCTT, GHICHU, LOAIXUATHD, NPP_FK, KHACHHANG_FK, NPP_DAT_FK, HOPDONG,  \n " +
					"CREATED_DATE, NGUOIMUA, FROM_HOADON_GOC, INNGUOIMUA, TENKHIN, LOAIHOADON, KHO_FK, MAUHOADON, DDKD_FK, KBH_FK, DAINPGH, TENKHACHHANG, \n " +
					"DIACHI, MASOTHUE, MAVV, NHOMKENH_FK, TENXUATHD, KHGHINO, CONGTY_FK, GHICHU2, ISKM, TIENTE_FK, NGAYGHINHAN, TYGIA, ISTACHHD, NHANVIEN_FK, \n " +
					"THOIDIEMCHOT, NGUOICHOT, KHACHHANGKG_FK, PK_SEQ_TAM, TOOLTIP, CHUYENSALES " +
					"FROM ERP_HOADONNPP WHERE PK_SEQ  IN (SELECT HOADON_FK FROM ERP_HUYHOADONTAICHINH WHERE PK_SEQ = '" + Id + "')" ;
			
			System.out.println(query);
			if(!db.update(query)){
				msg = "Chốt hủy hóa đơn không thành công ";
				db.getConnection().rollback();
				return false;
				
			}
			
			query = "SELECT SCOPE_IDENTITY() AS HDID ";
			//CÀI KẾ TOÁN

			rs = db.get(query);
			rs.next();
			String hdId = rs.getString("HDID");
			rs.close();
			
					
			query = "INSERT INTO ERP_HOADONNPP_SP(HOADON_FK, SANPHAM_FK, SCHEME, SOLUONG, DONGIA, TIENBVAT, VAT, TIENAVAT, DONVITINH, CHIETKHAU, THANHTIEN, " +
					"SANPHAMTEN, TIENVAT, KHO_FK, DONGIASAUCK, SOLUONG_CHUAN, DONGIA_CHUAN, DONVI_CHUAN, CHONIN, PK_SEQ_TAM, SECHAYLAI) " +
					
					"SELECT " + hdId + ", SANPHAM_FK, SCHEME, SOLUONG, (-1)*DONGIA, (-1)*TIENBVAT, (-1)*VAT, (-1)*TIENAVAT, DONVITINH, (-1)*CHIETKHAU, (-1)*THANHTIEN, " +
					"SANPHAMTEN, (-1)*TIENVAT, KHO_FK, (-1)*DONGIASAUCK, SOLUONG_CHUAN, (-1)*DONGIA_CHUAN, DONVI_CHUAN, CHONIN, PK_SEQ_TAM, SECHAYLAI " +
					"FROM ERP_HOADONNPP_SP WHERE HOADON_FK IN (SELECT HOADON_FK FROM ERP_HUYHOADONTAICHINH WHERE PK_SEQ = '" + Id + "')" ;
			System.out.println(query);
			
			if(!db.update(query)){
				msg = "Chốt hủy hóa đơn không thành công ";
				db.getConnection().rollback();
				return false;
				
			}
			
			query =
				"	SELECT	A.NGAYXUATHD,A.PK_SEQ, B.SANPHAM_FK, B.SOLUONG, ISNULL(B.DONGIASAUCK ,B.DONGIA) DONGIA,ISNULL(B.CHIETKHAU,0) CHIETKHAU, ISNULL(A.NGAYGHINHAN, A.NGAYXUATHD) as NGAYGHINHAN, \n"+ 
				"			ISNULL( ISNULL(A.KHGHINO, A.NPP_DAT_FK), A.nhanvien_fk ) KHACHHANG_FK, \n"+
				"			CASE WHEN A.KHGHINO IS NOT NULL THEN F.TAIKHOAN_FK  WHEN A.NPP_DAT_FK IS NOT NULL THEN E.TAIKHOAN_FK when A.nhanvien_fk is not null then G.TAIKHOAN_FK END TAIKHOANNO, \n"+
				"			B.VAT, (SELECT LOAISP.TAIKHOANKT_FK " +
				"					FROM ERP_LOAISANPHAM LOAISP INNER JOIN ERP_TAIKHOANKT TAIKHOAN ON LOAISP.TAIKHOANKT_FK = TAIKHOAN.SOHIEUTAIKHOAN \n"+
				"			 		WHERE C.LOAISANPHAM_FK =  LOAISP.PK_SEQ AND TAIKHOAN.SOHIEUTAIKHOAN = LOAISP.TAIKHOANKT_FK AND TAIKHOAN.CONGTY_FK = " + this.ctyId + " ) LOAISP, \n"+
				"			(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51110000' AND CONGTY_FK = " + this.ctyId + ") as a51110000,  "+
				"			(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51120000' AND CONGTY_FK = " + this.ctyId + ") as a51120000,  "+
				"			(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '52110000' AND CONGTY_FK = " + this.ctyId + ") as a52110000, "+
				"			(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '33311000' AND CONGTY_FK = " + this.ctyId + ") as a33311000, "+
				"			(SELECT PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '51180000' AND CONGTY_FK = " + this.ctyId + ") as a51180000, ISNULL(A.TIENTE_FK, 100000) TIENTE_FK, ISNULL(A.TYGIA, 1) TYGIA, B.THANHTIEN "+
				"   FROM ERP_HOADONNPP A INNER JOIN ERP_HOADONNPP_SP B ON A.PK_SEQ = B.HOADON_FK \n"+
				" 	INNER JOIN SANPHAM C ON B.SANPHAM_FK = C.PK_SEQ \n"+	 
				" 	LEFT JOIN KHACHHANG D ON A.KHACHHANG_FK = D.PK_SEQ \n"+
				" 	LEFT JOIN NHAPHANPHOI E ON A.NPP_DAT_FK = E.PK_SEQ \n"+
				"	LEFT JOIN KHACHHANG F ON A.KHGHINO = F.PK_SEQ LEFT JOIN ERP_NHANVIEN G ON A.nhanvien_fk = G.PK_SEQ  \n"+
				"	WHERE A.PK_SEQ IN (SELECT HOADON_FK FROM ERP_HUYHOADONTAICHINH WHERE PK_SEQ = '" + Id + "') AND LEN(ISNULL(SCHEME,'')) = 0  ";

			System.out.println(query);
			ResultSet kt  = db.get(query);
			String nam = "", thang = "";
			if(kt!=null)
			{
				while(kt.next())
				{
					String khachhang_fk = kt.getString("KHACHHANG_FK");
					double soluong = kt.getDouble("SOLUONG");
					double dongia = kt.getDouble("DONGIA"); // ĐƠN GIÁ SAU KHI GIẢM

					double tienhang = kt.getDouble("THANHTIEN");
					String SOHIEUTAIKHOAN = kt.getString("LOAISP")== null ? "": kt.getString("LOAISP") ;
					double tienthue = kt.getDouble("THANHTIEN")*kt.getDouble("VAT")/100;
					
					String sanpham_fk = kt.getString("SANPHAM_FK");
					
					double tienchietkhau = kt.getDouble("CHIETKHAU");
					
					String ngaychungtu = ngayhuy;
					String ngayghinhan = ngayhuy;
					
					nam = ngayghinhan.substring(0, 4);
					thang = ngayghinhan.substring(5, 7);
					
					String tiente_fk = kt.getString("TIENTE_FK");
					double tygia = kt.getDouble("TYGIA");
					
					String doituong_no = "";
					String madoituong_no =  "";
					
					String doituong_co = "";
					String madoituong_co = "";
					
					String TAIKHOANNO = "";
					String TAIKHOANCO ="";
					
					if(SOHIEUTAIKHOAN.trim().length()<=0 || SOHIEUTAIKHOAN.trim().length() <=0 || SOHIEUTAIKHOAN == null || SOHIEUTAIKHOAN == null)
					{
						kt.close();
						this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
						
						
					if(SOHIEUTAIKHOAN.equals("15610000")) // SẢN PHẨM LÀ LOẠI HÀNG HÓA
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51110000") == null ? "": kt.getString("a51110000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {						    	
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
						    }		
						    
						    this.msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan,  "Hủy hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, 
					    			"", Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", 
					    			"", "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), Double.toString(tienhang), "Hủy hóa đơn - Tiền hàng");
					    
					   
							if(this.msg.trim().length()>0)
							{
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								kt.close();
								db.getConnection().rollback();
								return false;
							}
						}
							
					}
					else if(SOHIEUTAIKHOAN.equals("15510000"))
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51120000")== null ? "": kt.getString("a51120000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {
						    	this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
						    	System.out.println(this.msg);
						    	kt.close();								
								db.getConnection().rollback();
								return false;
						    }
						    this.msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan,  "Hủy hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, 
						    			"", Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", 
						    			"", "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), Double.toString(tienhang), "Hủy hóa đơn - Tiền hàng");
						    
						   
							if(this.msg.trim().length()>0)
							{
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
							}
						}
					}
					else
					{
						if(tienhang>0)
						{
							doituong_no = "Khách hàng";
							madoituong_no = khachhang_fk;
							
							doituong_co = "Sản phẩm";
						    madoituong_co = sanpham_fk;
						    
						    
						    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
						    TAIKHOANCO = kt.getString("a51180000") == null ? "": kt.getString("a51180000") ;
						    
						    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
						    {						    	
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
						    }
						    this.msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan,  "Hủy hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, 
						    			"", Double.toString(tienhang), Double.toString(tienhang), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", 
						    			"", "", tiente_fk, "", Double.toString(tygia), Double.toString(tienhang), Double.toString(tienhang), "Hủy hóa đơn - Tiền hàng");
						    
						   
							if(this.msg.trim().length()>0)
							{								
								this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								System.out.println(this.msg);
								kt.close();
								db.getConnection().rollback();
								return false;
							}
						}
					}
					
					if(tienthue>0)
					{
						doituong_no = "Khách hàng";
						madoituong_no = khachhang_fk;
						
						doituong_co = "Sản phẩm";
					    madoituong_co = sanpham_fk;
					    
					    
					    TAIKHOANNO = kt.getString("TAIKHOANNO") == null ? "": kt.getString("TAIKHOANNO") ;
					    TAIKHOANCO = kt.getString("a33311000") == null ? "": kt.getString("a33311000") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
					    }
					    
					    this.msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan,  "Hủy hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, 
					    			"", Double.toString(tienthue), Double.toString(tienthue), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", 
					    			"", "", tiente_fk, "", Double.toString(tygia), Double.toString(tienthue), Double.toString(tienthue), "Hủy hóa đơn - Tiền thuế");
					    
					   
						if(this.msg.trim().length()>0)
						{							
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
						}
					}
					
					if(tienchietkhau > 0)
					{
						doituong_no = "Sản phẩm";
						madoituong_no = sanpham_fk;
						
						doituong_co = "Khách hàng";
					    madoituong_co = khachhang_fk;
					    
					    TAIKHOANNO = kt.getString("a52110000")== null ? "": kt.getString("a52110000") ;
					    TAIKHOANCO = kt.getString("TAIKHOANNO")== null ? "": kt.getString("TAIKHOANNO") ;
					    
					    if(TAIKHOANNO.trim().length()<=0 || TAIKHOANCO.trim().length() <=0 || TAIKHOANNO == null || TAIKHOANCO == null)
					    {
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();
							db.getConnection().rollback();
							return false;
					    }
					    
					    this.msg = util.Update_TaiKhoan(db, thang, nam, ngaychungtu, ngayghinhan,  "Hủy hóa đơn tài chính", hdId, TAIKHOANNO, TAIKHOANCO, 
					    			"", Double.toString(tienchietkhau), Double.toString(tienchietkhau), doituong_no, madoituong_no, doituong_co, madoituong_co, "0", 
					    			"", "", tiente_fk, "", Double.toString(tygia), Double.toString(tienchietkhau), Double.toString(tienchietkhau), "Hủy hóa đơn - Tiền chiết khấu");
					    
						if(this.msg.trim().length()>0)
						{							
							this.msg = "Khách hàng tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							System.out.println(this.msg);
							kt.close();							
							db.getConnection().rollback();
							return false;
						}
					}
				}
				kt.close();
			}
			
			
			//LUU LAI THONG TIN
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			//CAP NHAT TOOLTIP
			//db.execProceduce2("CapNhatTooltip_HoaDon", new String[] { this.id } );
		}
		catch (Exception e) 
		{
			db.update("rollback");
			e.printStackTrace();
			this.msg = "Exception: " + e.getMessage();
			return false;
		}
		return true;
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


	public String getsohoadon() {
		
		return this.sohoadon;
	}


	public void setsohoadon(String sohoadon) {
		this.sohoadon = sohoadon;
		
	}

	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

}
