package geso.traphaco.erp.beans.vayvon.imp;

import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.vayvon.IThanhtoannovay;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Thanhtoannovay implements IThanhtoannovay {
	String Id;
	String ctyId;
	String soHD;
	String ntvId;
	String ngay;
	String hinhthuc;
	String tkctyId;
	String tienlai;
	String tiengoc;
	String tienphat;
	String phikhac;
	String tiente;
	String ttId;
	String ghichu;
	String msg;
	String userId;
	dbutils db;
	ResultSet HDRS;
	ResultSet ttRs;
	ResultSet NTVRS;
	ResultSet TKCTYRS;
	String action;
	
	private double tyGia;
	private ResultSet dinhKhoanRs;
	public Thanhtoannovay()
	{   
		this.ctyId = "";
		this.Id = "";
		this.soHD = "";
		this.ntvId = "";
		this.ttId = "";
		this.ngay = "";
		this.hinhthuc = "";
		this.tienlai = "0";
		this.tiengoc = "0";
		this.tienphat = "0";
		this.phikhac = "0";
		this.tiente = "";
		this.msg = "";
		this.ghichu = "";
		this.userId = "";
		this.db = new dbutils();
		this.action = "";
		this.tyGia = 0;
	}
	public Thanhtoannovay(String Id)
	{   
		this.ctyId = "";
		this.Id = Id;
		this.soHD = "";
		this.ntvId = "";
		this.ttId = "";
		this.ngay = "";
		this.hinhthuc = "";
		this.tienlai = "0";
		this.tiengoc = "0";
		this.tienphat = "0";
		this.phikhac = "0";
		this.tienlai = "0";
		this.tiente = "";
		this.msg = "";
		this.ghichu = "";
		this.userId = "";
		this.db = new dbutils();
		this.action = "";
		this.tyGia = 0;
	}
	
	public String getAction() {
		return action;
	}
	public void setAction(String action) {
		this.action = action;
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
	
	public void setTtId(String ttId) {
		this.ttId = ttId;
		try{
			if(this.ttId.length() > 0){
				System.out.println("SELECT MA FROM ERP_TIENTE WHERE PK_SEQ = " + this.ttId + " ");
				
				ResultSet rs = this.db.get("SELECT PK_SEQ, MA FROM ERP_TIENTE WHERE PK_SEQ = " + this.ttId + " ");
			
				rs.next();			
				this.tiente = rs.getString("MA");
				rs.close();
			}
		}catch(java.sql.SQLException e){}
	}

	public String getTtId() {
		return this.ttId;
	}

	public void setSoHD(String soHD) {
		this.soHD = soHD;
	}

	public String getSoHD() {
		return this.soHD;
	}
	
	public void setNtvId(String ntvId) {
		this.ntvId = ntvId;
		
	}

	public String getNtvId() {
		return this.ntvId;
	}

	public void setHinhthuc(String hinhthuc) {
		this.hinhthuc = hinhthuc;
		
	}

	public String getHinhthuc() {
		return this.hinhthuc;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}
	
	public void setTkCtyId(String tkctyId) {
		this.tkctyId = tkctyId;
		
	}

	public String getTkCtyId() {
		return this.tkctyId;
	}

	public void setTienlai(String tienlai) {
		this.tienlai = tienlai;
		
	}

	public String getTienlai() {
		return this.tienlai;
	}
	
	public void setTiengoc(String tiengoc) {
		this.tiengoc = tiengoc;
		
	}

	public String getTiengoc() {
		return this.tiengoc;
	}
	
	public void setTienphat(String tienphat) {
		this.tienphat = tienphat;
		
	}

	public String getTienphat() {
		return this.tienphat;
	}

	public void setPhikhac(String phikhac) {
		this.phikhac = phikhac;
		
	}

	public String getPhikhac() {
		return this.phikhac;
	}

	public void setTiente(String tiente) {
		this.tiente = tiente;
		
	}

	public String getTiente() {
		return this.tiente;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
		
	}

	public String getGhichu() {
		return this.ghichu;
	}

	public void setTtRs(ResultSet ttRs) {
		
		this.ttRs = ttRs;
	}
	
	public ResultSet getTtRs() {
		if(this.soHD.length() > 0){
			String query = 		"SELECT DISTINCT TT.PK_SEQ AS TTID, TT.MA AS TT \n" +
								"FROM ERP_TIENTE TT \n" +
								"INNER JOIN ERP_HOPDONGVAY HDV ON HDV.TIENTE_FK = TT.PK_SEQ \n" +
								"WHERE TT.TRANGTHAI = 1 and HDV.PK_SEQ = " + this.soHD + "\n" ;
			
		
			if(this.ntvId.length() > 0)
				query = query + " AND TT.PK_SEQ IN (SELECT TIENTE_FK FROM ERP_NHANTIENVAY WHERE PK_SEQ = " + this.ntvId + ")";
			System.out.println("Cau lenh lay tien te rs: \n" + query + "\n=========================================================");
			try {
				ResultSet rs = db.get(query);
				if (rs != null)
				{
					if (rs.next())
					{
						this.ttId = rs.getString("TTID");
						this.tiente = rs.getString("TT");
					}
					rs.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
			System.out.println("ttId: " + ttId);
			return this.db.get(query);	
		}else{
			return null;
		}
	}
	
	public void setHDRS(ResultSet HDRS) {
		
		this.HDRS = HDRS;
	}
	
	public ResultSet getHDRS() {
		
		return this.HDRS;
	}

	public void setNTVRS(ResultSet NTVRS) {
		
		this.NTVRS = NTVRS;
	}
	
	public ResultSet getNTVRS() {
		
		return this.NTVRS;
	}

	public void setTKCTYRS(ResultSet TKCTYRS) {
		
		this.TKCTYRS = TKCTYRS;
	}
	
	public ResultSet getTKCTYRS() {
		
		return this.TKCTYRS;
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
			getDinhKhoan();
			try {
				String sql =	"SELECT	HDV.PK_SEQ AS SOHD, TTNV.NHANTIENVAY_FK AS NTVID, TTNV.NGAY, TTNV.HINHTHUC, TTNV.TKCONGTY_FK AS TKCTYID, \n" +
								"isnull(TTNV.TIENTE_FK, HDV.TIENTE_FK) as TIENTE_FK, ISNULL(TTNV.TIENLAI, 0) AS TIENLAI, ISNULL(TTNV.TIENGOC, 0) AS TIENGOC, \n" +
								"ISNULL(TTNV.TIENPHAT, 0) AS TIENPHAT, ISNULL(TTNV.PHIKHAC, 0) AS PHIKHAC, TTNV.TRANGTHAI, TT.MA AS TT, TTNV.GHICHU \n" +
								", ISNULL(\n" +
								"	(select distinct top 1 TIGIAQUYDOI \n" +
								"	from ERP_TIGIA where TIENTE_FK = HDV.TIENTE_FK and TuNgay <= TTNV.NGAY and TTNV.NGAY <= DenNgay and TRANGTHAI = 1), 0) as TYGIA \n" +
								"FROM ERP_HOPDONGVAY HDV \n" +
								"INNER JOIN ERP_THANHTOANNOVAY TTNV ON TTNV.HOPDONG_FK = HDV.PK_SEQ \n" +
								"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = HDV.TIENTE_FK \n" +
								"WHERE TTNV.PK_SEQ = " + this.Id + " \n" +
								" order by ttnv.ngaySua, ttnv.pk_seq \n";
								
				
				System.out.println("query init thanh toan no vay: \n" + sql + "\n=================================================");
				ResultSet rs = db.get(sql);
				if (rs != null)
				{
					while(rs.next())
					{
						this.tyGia = rs.getDouble("TYGIA");
						this.soHD = rs.getString("SOHD");
						this.ntvId = rs.getString("NTVID");
						this.ngay = rs.getString("NGAY");
						this.hinhthuc = rs.getString("HINHTHUC");
						this.tkctyId = rs.getString("TKCTYID");
						this.tienlai = rs.getString("TIENLAI");
						this.tiengoc = rs.getString("TIENGOC");
						this.tienphat = rs.getString("TIENPHAT");
						this.phikhac = rs.getString("PHIKHAC");
						this.tiente = rs.getString("TT");
						this.ttId = rs.getString("TIENTE_FK");
						this.ghichu = rs.getString("GHICHU");
						this.msg = "";
						this.setTtId(rs.getString("TIENTE_FK"));
					}
					rs.close();
				}				
			
			} catch (SQLException e) {
				e.printStackTrace();
			}
			
		}
		init_RS();
		System.out.println("tyGia: " + tyGia);
		if (this.tyGia <= 0 && this.ngay.trim().length() > 0 && this.tiente.trim().length() > 0)
		{
			this.msg += "Chưa có tỷ giá cho  loại tiền tệ " + this.tiente + " vào ngày " + this.ngay +". Vui lòng xem lại dữ liệu nền.\n";
		}
	}
   
	private void getDinhKhoan() {
		String query = 
			"select ps.pk_seq, (case ps.NO when 0 then N'CÓ' else N'NỢ' end) as No_Co \n" +
			"	, (case ps.NO when 0 then ps.CO else ps.NO end) as soTien \n" +
			"	, (select tk.SOHIEUTAIKHOAN from ERP_TAIKHOANKT tk where tk.PK_SEQ = ps.TAIKHOAN_FK) as soHieuTk \n" +
			"	, isnull((case ps.DOITUONG when N'Ngân hàng' then (select nh.TEN from ERP_NGANHANG nh where cast(PK_SEQ as nvarchar(50)) = ps.MADOITUONG) \n" +
			"						when N'Nhà cung cấp' then (select ncc.TEN from ERP_NHACUNGCAP ncc where cast(ncc.PK_SEQ as nvarchar(50)) = ps.MADOITUONG) end \n" +
			"	), '') as doiTuong \n" +
			" , isnull((case (tk.SOHIEUTAIKHOAN) when '63510000' then (select ttcp.TEN  \n" +
			"														from ERP_NHOMCHIPHI ncp  \n" +
			"														LEFT join ERP_TRUNGTAMCHIPHI ttcp on ttcp.PK_SEQ = ncp.TTCHIPHI_FK \n" +
			"														LEFT join ERP_TAIKHOANKT tk on tk.PK_SEQ = ncp.TAIKHOAN_FK \n" +
			"														where tk.SOHIEUTAIKHOAN = '63510000')  \n" +
			"			else (case (tk1.SOHIEUTAIKHOAN) when '63510000' then (select ttcp.TEN \n" +
			"														from ERP_NHOMCHIPHI ncp  \n" +
			"														LEFT join ERP_TRUNGTAMCHIPHI ttcp on ttcp.PK_SEQ = ncp.TTCHIPHI_FK \n" +
			"														LEFT join ERP_TAIKHOANKT tk on tk.PK_SEQ = ncp.TAIKHOAN_FK \n" +
			"														where tk.SOHIEUTAIKHOAN = '63510000') else '' end) \n" +
			"			end), '') as trungTamChiPhi \n" +
			"from ERP_PHATSINHKETOAN ps \n" +
			"left join ERP_TAIKHOANKT tk on tk.PK_SEQ = ps.TAIKHOAN_FK \n" +
			"left join ERP_TAIKHOANKT tk1 on tk1.PK_SEQ = ps.TAIKHOANDOIUNG_FK \n" +
			"where LOAICHUNGTU like N'Thanh toán nợ vay' and ps.SOCHUNGTU = " + this.Id + " \n";

		System.out.println("Query lay dinh khoan ke toan : \n" + query + "\n====================================");
		this.dinhKhoanRs = this.db.get(query);
	}
	public void init_RS(){ 
		this.HDRS = this.db.get("SELECT PK_SEQ AS HDID, SOHD + ' - ' + DIENGIAI AS HD FROM ERP_HOPDONGVAY WHERE TRANGTHAI = 1");
		String query = "";
		System.out.println("So HD: "+this.soHD);
		if(this.soHD.length() > 0){
			query = " SELECT NTV.PK_SEQ AS NTVID,  TKVAY + ' - [' + CONVERT(VARCHAR, \n"+ 
					" CAST(  (case when NTV.TIENTE_FK=100000 then NTV.SOTIENVND else NTV.SOTIENNT end ) AS MONEY), 1) + ' ' + isnull(TT.MA,'') + '] \n"+ 
					" - ' + NTV.GHICHU AS NTV FROM ERP_HOPDONGVAY HDV  \n"+
					" INNER JOIN ERP_NHANTIENVAY NTV ON NTV.HOPDONG_FK = HDV.PK_SEQ \n"+ 
					" INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NTV.TIENTE_FK \n" +
					" WHERE HDV.PK_SEQ = " + this.soHD + " AND NTV.TRANGTHAI = '1' \n" +
					"	and ntv.PK_SEQ not in ( \n" + 
					"		select ntv.PK_SEQ \n" + 
					"		from ERP_NHANTIENVAY ntv \n" + 
					"		left join ERP_HOPDONGVAY hdv on hdv.PK_SEQ = ntv.HOPDONG_FK \n" + 
					"		left join ERP_THANHTOANNOVAY ttnv on ttnv.NHANTIENVAY_FK = ntv.PK_SEQ \n" + 
					"		where hdv.TRANGTHAI = 1 \n" + 
					"		group by ntv.PK_SEQ, ntv.SOTIENVND \n" + 
					"		having sum(ttnv.TIENGOC) >= ntv.SOTIENVND \n" + 
					"	) \n";
			if (this.Id.length() > 0)
				query += 
					"union all\n" + 
					" SELECT NTV.PK_SEQ AS NTVID,  TKVAY + ' - [' + CONVERT(VARCHAR, \n" +
					" CAST(  (case when NTV.TIENTE_FK=100000 then NTV.SOTIENVND else NTV.SOTIENNT end ) AS MONEY), 1) + ' ' + isnull(TT.MA,'') + '] \n" +
					" - ' + NTV.GHICHU AS NTV \n" +
					" FROM ERP_HOPDONGVAY HDV  \n" +
					" INNER JOIN ERP_NHANTIENVAY NTV ON NTV.HOPDONG_FK = HDV.PK_SEQ \n" +
					" INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NTV.TIENTE_FK \n" +
					" inner join ERP_THANHTOANNOVAY ttnv on ttnv.NHANTIENVAY_FK = NTV.PK_SEQ\n" +
					" WHERE HDV.PK_SEQ = " + this.soHD + " AND NTV.TRANGTHAI = '1' \n" +
					"and ttnv.PK_SEQ = " + this.Id;
			System.out.println("query lay lan giai ngan: \n" + query + "\n================================================");
			this.NTVRS = this.db.get(query);
		}
	
/*		this.TKCTYRS = 	this.db.get("SELECT NHCTY.PK_SEQ AS TKID, NH.MA + ' - ' + NH.TEN AS TK " +
									"FROM ERP_NGANHANG_CONGTY NHCTY " +
									"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NHCTY.NGANHANG_FK WHERE NHCTY.CONGTY_FK = " + this.ctyId + " "); */
		if (this.ntvId.trim().length() > 0)
		{
			query = "SELECT NH_CTY.PK_SEQ AS TKID, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TK \n" +
					"FROM ERP_NGANHANG_CONGTY NH_CTY \n" +
					"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK \n" + 
					"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK \n" +
					"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK \n" +
					"WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "' \n" +
					"AND TT.PK_SEQ IN (SELECT TIENTE_FK FROM ERP_NHANTIENVAY WHERE PK_SEQ = " + this.ntvId + ") \n";
			try{ 
				System.out.println("cau lenh lay tai khoan ngan hang: \n" + query + "\n=========================================");
				this.TKCTYRS = db.get(query);
			} catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		if (this.ttId.trim().equals("100000"))
			this.tyGia = 1;
		else if (this.ttId.trim().length() > 0 && this.ngay.trim().length() > 0)
		{
			query =  "select isnull(TIGIAQUYDOI, 0) as TIGIAQUYDOI from ERP_TIGIA where TIENTE_FK = " + this.ttId + " and TuNgay <= '" + this.ngay + "' and '" + this.ngay + "' <= DenNgay";
			try{ 
				System.out.println("lenh get ty gia: \n" + query + "\n============================");
				ResultSet rs = db.get(query);
				if (rs != null)
					if (rs.next())
						this.tyGia = rs.getDouble("TIGIAQUYDOI");
			} catch(Exception ex){
				ex.printStackTrace();
			}
			
		}
		System.out.println("tyGia: " + tyGia);
		if (this.tyGia <= 0 && this.ngay.trim().length() > 0 && this.tiente.trim().length() > 0)
		{
			this.msg += "Chưa có tỷ giá cho  loại tiền tệ " + this.tiente + " vào ngày " + this.ngay +". Vui lòng xem lại dữ liệu nền.\n";
		}
		if(!this.action.equals("display")){
			TinhTienGoc();
		}
//		this.Tinhlaivay();
	}
	
	// true là tính lại số tiền, false là không thay đổi số tiền đã nhập
	public boolean TinhTienGoc(){
		// tính tiền gốc cần phải trả: kiểm tra tiền gốc, trong trường hợp chỉnh sửa hoặc tạo mới
		Double TienGocDB = new Double(0);
		try{
		if(this.ntvId.trim().length() >0){
			String query = " select (select isnull(SOTIEN,0) from ERP_NHANTIENVAY where PK_SEQ = ? and TRANGTHAI =1) as a, \n" +
					" (select isnull(SUM(TIENGOC),0) from ERP_THANHTOANNOVAY where NHANTIENVAY_FK = ? and (TRANGTHAI =1 or trangthai = 0)) as b ";
			PreparedStatement pre = this.db.getConnection().prepareStatement(query);
			pre.setString(1, this.ntvId);
			pre.setString(2, this.ntvId);
			ResultSet rsTienGoc = pre.executeQuery();
			
			Double a=  new Double(0);
			Double b=  new Double(0);
			if(rsTienGoc !=null){
				if(rsTienGoc.next()){
					a = rsTienGoc.getDouble("a");
					b = rsTienGoc.getDouble("b");
				}
			}
			a = a- b;
			//this.tiengoc = a.toString();
			if(this.tiengoc.equals("0")){
				this.tiengoc = a.toString();
			}
			
			TienGocDB = a;
			
			Double d = new Double(this.tiengoc);
			if( d- TienGocDB >0){
				this.msg =" Không được nhập số tiền gốc lớn hơn số tiền giải ngân đã bù trừ những lần thanh toán khoản vay";
				this.tiengoc = TienGocDB.toString();
				return true;
			}
		}
		} catch( Exception ex){
			ex.printStackTrace();
		}
		
		return false;
	}
	
	private void Tinhlaivay(){
		String query;
		if(this.ngay.length() > 0 & this.ntvId.length() > 0){
			try{
				query = "SELECT NTV.LAISUAT, NTV.NGAYNHAN, (NTV.SOTIEN - ISNULL(SUM(TIENGOC), 0)) AS CONLAI " +
						"FROM ERP_NHANTIENVAY NTV " +
						"LEFT JOIN ERP_THANHTOANNOVAY TT ON TT.NHANTIENVAY_FK = NTV.PK_SEQ AND TT.TRANGTHAI = 1 " +
						"WHERE  NTV.TRANGTHAI = 1 AND NTV.PK_SEQ = " + this.ntvId  + " " +
						"GROUP BY NTV.SOTIEN, NTV.LAISUAT, NTV.NGAYNHAN " ;
				
				System.out.println(query);
				ResultSet rs = this.db.get(query);
				rs.next();
				String laisuat = rs.getString("LAISUAT");
				String ngaynhan = rs.getString("NGAYNHAN");
				double conlai = Double.parseDouble(rs.getString("CONLAI"));
				rs.close();

				query = "SELECT NGAY FROM ERP_THANHTOANNOVAY WHERE CONGTY_FK = " + this.ctyId + " ORDER BY NGAY DESC ";
				System.out.println(query);
				rs = this.db.get(query);
				
				if(rs.next()){
					ngaynhan = rs.getString("NGAY");
					rs.close();
				}
				
				query = "SELECT DATEDIFF(day,'" + ngaynhan + "','" + this.ngay + "') AS DIFF";
				System.out.println(query);
				rs = this.db.get(query);
				rs.next();
				
				double laivay = (conlai*Double.parseDouble(rs.getString("DIFF"))*Double.parseDouble(laisuat)/360)/100;
				System.out.println("laivay: " + laivay);
				if(laivay <= 0) laivay = 0;
				
				System.out.println("laivay: " + laivay);
				
				if(this.tienlai.equals("0")) this.tienlai += "" + laivay;
				
			
				rs.close();
			}catch(java.sql.SQLException e){
				System.out.println(e.toString());
			}
		}
	}

	public void setmsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	
	public void Xoa()
	{
		String query = "DELETE ERP_THANHTOANNOVAY WHERE PK_SEQ = " + this.Id + " AND TRANGTHAI = 0 ";
						
		this.db.update(query);
		
	}
	
	public String Hoantat()
	{
		System.out.println("Chot thanh toan no vay");
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE ERP_THANHTOANNOVAY SET TRANGTHAI = '1' WHERE PK_SEQ = " + this.Id + "";		
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "HT1.1 Lỗi chốt thanh toán nợ vay: " + query;
			}
			
			//GHI NHAN BOOK TOAN
			Utility util = new Utility();
			query =  
				"select  a.TIENTE_FK, isnull( ( select TIGIAQUYDOI from ERP_TIGIA where trangThai = 1 and TIENTE_FK = a.TIENTE_FK and TuNgay <= a.NGAY and a.NGAY <= DenNgay ), 0 ) as TYGIA, \n" +    
				"		a.TIENGOC, a.TIENLAI, a.TIENPHAT, a.PHIKHAC, a.NGAY, \n" +
				"		case a.hinhthuc when 2 then '11110000'  --Tien mat \n" +
				"						else ( select SOHIEUTAIKHOAN  --Chuyen khoan -> tk duoi ngan hang \n" +
				"							   from ERP_TAIKHOANKT \n" +
				"							   where pk_seq in ( \n" +
				"												select TaiKhoan_fk \n" + 
				"												from ERP_NGANHANG_CONGTY \n" + 
				"												where PK_SEQ = a.TKCONGTY_FK   ) ) \n" +    
				"		end as sohieutaikhoanCO, \n" +
				"		case when b.loai = 1 then --Hop dong vay ngan han \n" +  
				"							(case a.TIENTE_FK when '100000' then '34113000'--Giai ngan la VND \n" +
				"					        			      when '100001' then '34114000'--Giai ngan la USD \n" +
				"							end) \n" +
				"			when b.loai = 2 then --Hop dong vay dai han \n" +
				"							(case a.TIENTE_FK when '100000' then '34111000'--Giai ngan la VND \n" +
				"						        			  when '100001' then '34112000'--Giai ngan la USD \n" +
				"							end) \n" +
				"		end as sohieuTK_NO_TIENGOC \n" +
				"		, case a.hinhthuc when 2 then (select cast(hdv.NGANHANG_FK as nvarchar) from ERP_HOPDONGVAY hdv  --Tien mat: ngân hàng cho vay \n" +
				"										where hdv.PK_SEQ = a.HINHTHUC)  \n" +
				"							else (select cast(nh_ct.NganHang_FK as nvarchar)  --Tiền ngân hàng: ngân hàng bên dưới 112 \n" +
				"								  from ERP_NGANHANG_CONGTY nh_ct \n" +
				"								  where nh_ct.PK_SEQ = a.TKCONGTY_FK) \n" +
				"		end as maDoiTuongNoGoc \n" +
				"		, N'Ngân hàng' doiTuongNoGoc \n" +
				"		, case a.hinhthuc when 2 then '' --Tien mat  \n" +
				"							else (select cast(nh_ct.NganHang_FK as nvarchar)  --Tiền ngân hàng \n" +
				"								  from ERP_NGANHANG_CONGTY nh_ct \n" +
				"								  where nh_ct.PK_SEQ = a.TKCONGTY_FK) \n" +
				"		end as maDoiTuongCoGoc \n" +
				"		, case a.hinhthuc when 2 then ''  --Tien mat  \n" +
				"							else N'Ngân hàng'  --Tiền ngân hàng \n" +
				"		end as doiTuongCoGoc \n" +
				"		, case a.hinhthuc when 2 then ''  --Tien mat  \n" +
				"							else (select  cast(nh_ct.NganHang_FK as nvarchar)  --Tiền ngân hàng \n" +
				"								  from ERP_NGANHANG_CONGTY nh_ct \n" +
				"								  where nh_ct.PK_SEQ = a.TKCONGTY_FK) \n" +
				"		end as maDoiTuongCoLaiVay \n" +
				"		, case a.hinhthuc when 2 then ''  --Tien mat \n" + 
				"							else N'Ngân hàng' --Tiền ngân hàng \n" +
				"		end as doiTuongCoLaiVay \n" +
				"		, '63510000' as sohieuTK_NO_TIENLAI, '81180000' as sohieuTK_NO_TIENPHAT, '64253000' as sohieuTK_NO_PHIKHAC \n" +   
				"from ERP_THANHTOANNOVAY a \n" +
				"inner join ERP_HOPDONGVAY b on a.HOPDONG_FK = b.PK_SEQ \n" +   
				"where a.PK_SEQ = '" + this.Id  + "'" ;
			
			System.out.println("__XAC DINH TAI KHOAN: " + query);
			ResultSet psktRs = db.get(query);
			if(psktRs != null)
			{
				while(psktRs.next())
				{
					double tygia = psktRs.getDouble("TYGIA");
					
					double tiengoc = Math.round(psktRs.getDouble("TIENGOC"));
					double tiengocViet = Math.round( tygia * tiengoc );
					
					String nam = psktRs.getString("NGAY").substring(0, 4);
					String thang = psktRs.getString("NGAY").substring(5, 7);
					String tiente_fk = psktRs.getString("TIENTE_FK");
					
					System.out.println("---TIEN GOC: " + tiengoc + "  --- TIEN GOC VIET: " + tiengocViet);
//					TIỀN GỐC
					if(tiengoc > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENGOC") == null ? "" : psktRs.getString("sohieuTK_NO_TIENGOC") ;
						String maDoiTuongCoGoc = psktRs.getString("maDoiTuongCoGoc");
						String maDoiTuongNoGoc = psktRs.getString("maDoiTuongNoGoc");
						String doiTuongCoGoc = psktRs.getString("doiTuongCoGoc");
						String doiTuongNoGoc = psktRs.getString("doiTuongNoGoc");
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "HT1.2 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							System.out.println("____MSG: " + msg);
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, this.ctyId, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tiengocViet), Double.toString(tiengocViet), doiTuongNoGoc, maDoiTuongNoGoc, "0", doiTuongCoGoc, maDoiTuongCoGoc, "0", "", "", tiente_fk, "", "1", Double.toString(tiengocViet), Double.toString(tiengoc), "Tiền gốc" );
						//System.out.println("____MSG: " + msg);
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return "HT1.3 " + msg;
						}
					}
					
					
					double tienlai = Math.round(psktRs.getDouble("TIENLAI"));
					double tienlaiViet = Math.round( tygia * tienlai );
//					TIỀN LÃI
					if(tienlai > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENLAI") == null ? "" : psktRs.getString("sohieuTK_NO_TIENLAI") ;
						String maDoiTuongCoLai = psktRs.getString("maDoiTuongCoLaiVay");
						String maDoiTuongNoLai = "";
						String doiTuongCoLai = psktRs.getString("doiTuongCoLaiVay");
						String doiTuongNoLai = "";
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "HT1.4 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, this.ctyId, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tienlaiViet), Double.toString(tienlaiViet), doiTuongNoLai, maDoiTuongNoLai, "0", doiTuongCoLai, maDoiTuongCoLai, "0","", "", tiente_fk, "", "1", Double.toString(tienlaiViet), Double.toString(tienlai), "Tiền lãi" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return "HT1.5 " + msg;
						}
					}
					
					
					double tienphat = Math.round(psktRs.getDouble("TIENPHAT"));
					double tienphatViet = Math.round( tygia * tienphat );
//					TIỀN PHẠT
					if(tienphat > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENPHAT") == null ? "" : psktRs.getString("sohieuTK_NO_TIENPHAT") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "HT1.6 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu(this.ctyId, db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tienphatViet), Double.toString(tienphatViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(tienphatViet), Double.toString(tienphat), "Tiền phạt" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return "HT1.7 " + msg;
						}
					}
					
					
					double phikhac = Math.round(psktRs.getDouble("PHIKHAC"));
					double phikhacViet = Math.round( tygia * phikhac );
					
					if(phikhac > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_PHIKHAC") == null ? "" : psktRs.getString("sohieuTK_NO_PHIKHAC") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "HT1.8 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu(this.ctyId ,db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(phikhacViet), Double.toString(phikhacViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(phikhacViet), Double.toString(phikhac), "Phí khác" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return "HT1.9 " + msg;
						}
					}
				}
				psktRs.close();
			}
			else
			{
				db.getConnection().rollback();
				return "HT1.10 Lỗi chốt thanh toán nợ vay: " + query;
			}
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch(Exception e)
		{
			db.update("rollback");
			return "HT1.1 Lỗi chốt thanh toán nợ vay: " + e.getMessage();
		}
		
		return msg;
	}

	public String HoantatOld()
	{
		String msg = "";
		try
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "UPDATE ERP_THANHTOANNOVAY SET TRANGTHAI = '1' WHERE PK_SEQ = " + this.Id + "";		
			if(!db.update(query))
			{
				db.getConnection().rollback();
				return "Lỗi chốt thanh toán nợ vay: " + query;
			}
			
			//GHI NHAN BOOK TOAN
			Utility util = new Utility();
			query =  "select  a.TIENTE_FK, isnull( ( select TIGIAQUYDOI from ERP_TIGIA where TIENTE_FK = a.TIENTE_FK and TuNgay <= a.NGAY and a.NGAY <= DenNgay ), 0 ) as TYGIA,  " + 
					 "		a.TIENGOC, a.TIENLAI, a.TIENPHAT, a.PHIKHAC, a.NGAY,  " + 
					 "		case a.hinhthuc when 2 then '111100'   " + 
					 "						else ( select SOHIEUTAIKHOAN from ERP_TAIKHOANKT where pk_seq in ( select TaiKhoan_fk from ERP_NGANHANG_CONGTY where PK_SEQ = a.TKCONGTY_FK   ) )  " + 
					 "		end as sohieutaikhoanCO, " + 
					 "		case when b.loai = 1 then  (case b.nganhang_fk when '100039' then '311100' " +
					 "										               when '100003' then '311200' " +
					 "                                                     when '100002' then '311300' end) " + 
					 "			 when b.loai = 2 then '341000' " + 
					 "		end as sohieuTK_NO_TIENGOC, '635100' as sohieuTK_NO_TIENLAI, '811000' as sohieuTK_NO_TIENPHAT, '642020' as sohieuTK_NO_PHIKHAC " + 
					 "from ERP_THANHTOANNOVAY a " +
					 "inner join ERP_HOPDONGVAY b on a.HOPDONG_FK = b.PK_SEQ " + 
					 "where a.PK_SEQ = '" + this.Id + "' ";
			
			System.out.println("__XAC DINH TAI KHOAN: " + query);
			ResultSet psktRs = db.get(query);
			if(psktRs != null)
			{
				while(psktRs.next())
				{
					double tygia = psktRs.getDouble("TYGIA");
					
					double tiengoc = Math.round(psktRs.getDouble("TIENGOC"));
					double tiengocViet = Math.round( tygia * tiengoc );
					
					String nam = psktRs.getString("NGAY").substring(0, 4);
					String thang = psktRs.getString("NGAY").substring(5, 7);
					String tiente_fk = psktRs.getString("TIENTE_FK");
					
					System.out.println("---TIEN GOC: " + tiengoc + "  --- TIEN GOC VIET: " + tiengocViet);
					if(tiengoc > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENGOC") == null ? "" : psktRs.getString("sohieuTK_NO_TIENGOC") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "1.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							System.out.println("____MSG: " + msg);
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu(this.ctyId, db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tiengocViet), Double.toString(tiengocViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(tiengocViet), Double.toString(tiengoc), "Tiền gốc" );
						//System.out.println("____MSG: " + msg);
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
					
					double tienlai = Math.round(psktRs.getDouble("TIENLAI"));
					double tienlaiViet = Math.round( tygia * tienlai );
					
					if(tienlai > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENLAI") == null ? "" : psktRs.getString("sohieuTK_NO_TIENLAI") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "2.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu(this.ctyId, db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tienlaiViet), Double.toString(tienlaiViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(tienlaiViet), Double.toString(tienlai), "Tiền lãi" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
					
					double tienphat = Math.round(psktRs.getDouble("TIENPHAT"));
					double tienphatViet = Math.round( tygia * tienphat );
					
					if(tienphat > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_TIENPHAT") == null ? "" : psktRs.getString("sohieuTK_NO_TIENPHAT") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "3.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu(this.ctyId, db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(tienphatViet), Double.toString(tienphatViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(tienphatViet), Double.toString(tienphat), "Tiền phạt" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
					
					double phikhac = Math.round(psktRs.getDouble("PHIKHAC"));
					double phikhacViet = Math.round( tygia * phikhac );
					
					if(phikhac > 0)
					{
						String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
						String taikhoanNO_SoHieu = psktRs.getString("sohieuTK_NO_PHIKHAC") == null ? "" : psktRs.getString("sohieuTK_NO_PHIKHAC") ;
						
						if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
						{
							msg = "4.Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
							db.getConnection().rollback();
							return msg;
						}
						
						msg = util.Update_TaiKhoan_TheoSoHieu(this.ctyId, db, thang, nam, psktRs.getString("NGAY"), psktRs.getString("NGAY"), "Thanh toán nợ vay", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
													Double.toString(phikhacViet), Double.toString(phikhacViet), "", "", "0", "", "", tiente_fk, "", "1", Double.toString(phikhacViet), Double.toString(phikhac), "Phí khác" );
						if(msg.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return msg;
						}
					}
					
				}
				psktRs.close();
			}
			
			
			db.getConnection().commit();
			db.shutDown();
		}
		catch(Exception e)
		{
			db.update("rollback");
			return "Lỗi chốt thanh toán nợ vay: " + e.getMessage();
		}
		
		return msg;
		
	}

	public boolean save() {
		if(this.tkctyId=="") this.tkctyId= "NULL";
		if(this.Id.length()>0 ){
		
			if(this.soHD.length() == 0 || this.ntvId.length() == 0 || this.ngay.length() == 0|| this.hinhthuc.length() == 0 ||  tkctyId.length()== 0 || this.ttId.length() == 0 )
			{  
				this.msg = "Ban phai nhap du thong tin";
				return false;
			}
		
			String sql;

			sql =	"UPDATE ERP_THANHTOANNOVAY SET HOPDONG_FK = '" + this.soHD + "', NHANTIENVAY_FK = '" + this.ntvId + "', ngay = '"+ this.ngay +"', " +
					"HINHTHUC = '" + this.hinhthuc + "', TKCONGTY_FK = "+ this.tkctyId + ", " +
					"TIENLAI = '" + this.tienlai + "', TIENGOC = '" + this.tiengoc + "', TIENPHAT = '" + this.tienphat + "', " +
					"PHIKHAC = '" + this.phikhac + "', GHICHU = N'" + this.ghichu + "', " +
					"TIENTE_FK = '" + this.ttId + "', " +
					"NGAYSUA ='"+ this.getDateTime() +"', NGUOISUA  ='"+ this.userId+"' WHERE PK_SEQ ='"+ this.Id +"' ";
				
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
			String sql;
			if(this.ngay.length() == 0) this.ngay = this.getDateTime();
			
			sql =	"INSERT INTO ERP_THANHTOANNOVAY(HOPDONG_FK, NHANTIENVAY_FK, NGAY, HINHTHUC, TKCONGTY_FK, TIENTE_FK, TIENLAI, TIENGOC, TIENPHAT, PHIKHAC, GHICHU, TRANGTHAI, NGAYSUA, NGUOISUA,  CONGTY_FK) \n" +
					"VALUES('"+ this.soHD + "', '" + this.ntvId + "', '" + this.ngay + "', '" + this.hinhthuc + "'," + this.tkctyId + ", '" + this.ttId + "', \n" +
							"'"+ this.tienlai +"', '"+ this.tiengoc + "','" + this.tienphat + "','" + this.phikhac +"', N'" + this.ghichu + "', '0', \n" +
							"'" + this.getDateTime() + "', '" + this.userId + "', " + this.ctyId + " )\n";
			
			System.out.println("ERP_THANHTOANNOVAY: \n" + sql + "\n======================");
			try {
				db.getConnection().setAutoCommit(false);
				if(!db.update(sql))
				{	
					db.update("rollback");
					this.msg =sql;
					return false;
				}
					 //System.out.println(sql);
				sql = "SELECT IDENT_CURRENT('ERP_THANHTOANNOVAY') AS TTNVID";
					
				ResultSet rs = this.db.get(sql);						
				rs.next();
				this.Id = rs.getString("TTNVID");
				rs.close();
									
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				this.Id="";
				e.printStackTrace();
			}
		}
		
		
		return true;
	}
	
	
	public String getDateTime() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}
	
	
	@Override
	public void DbClose() {
		try{
			if(this.HDRS != null) this.HDRS.close();
			
			db.shutDown();
		}catch(Exception er){
			
		}
	}

	public void setDinhKhoanRs(ResultSet dinhKhoanRs) {
		this.dinhKhoanRs = dinhKhoanRs;
	}

	public ResultSet getDinhKhoanRs() {
		return dinhKhoanRs;
	}

	public void setTyGia(double tyGia) {
		this.tyGia = tyGia;
	}

	public double getTyGia() {
		return tyGia;
	}
}