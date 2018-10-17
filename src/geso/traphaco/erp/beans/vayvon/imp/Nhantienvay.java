package geso.traphaco.erp.beans.vayvon.imp;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.uynhiemchi.IErpUynhiemchi;
import geso.traphaco.erp.beans.uynhiemchi.imp.ErpUynhiemchi;

import geso.traphaco.erp.beans.vayvon.INhantienvay;
import geso.traphaco.erp.db.sql.dbutils;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Nhantienvay implements INhantienvay {
	String ctyId;
	String Id;
	String soHD;
	String ngay;
	String sotien;
	String laisuat;
	String hinhthuc;
	String ghichu;
	String trangthai;
	String thoihan;
	String hanmuc;
	String msg;
	String[] travay;
	String[] tralai;
	String[] ngaytravay;
	String[] conlai;
	dbutils db;
	String userId;
	ResultSet HDRS;
	ResultSet UNCRS;
	ResultSet ttRs;
	String ttId;
	String tkvay;
	
	String giainganveTK;
	ResultSet sotkRs;
	String sotaikhoan;
	
	String tienteId;
	ResultSet tienteRs;
	
	String tigia;
	
	String sotienNT;
	String sotienVND;
	
	String sotienvay;
	String stConlai;
	String nppId;
	String nccId;
	ResultSet nccRs;
	
	String[] uncIds;
	String[] nccIds;
	String[] nccTen;
	String[] sotienNTs;
	String[] tgs;
	String[] sotienVNDs;
	String[] pays;
	String[] rests;
	String[] conlaiHD;
		
	NumberFormat formatter;
	
	// 3/12/2015
	String NgayDaoHan;
	
	private ResultSet dinhKhoanRs;
	
	public Nhantienvay()
	{   
		formatter = new DecimalFormat("#,###,###");
		this.ctyId = "";
		this.Id ="";
		this.soHD = "";
		this.ngay = "";
		this.sotien = "0";
		this.sotienvay ="0";
		this.laisuat = "";
		this.hinhthuc = "";
		this.ghichu = "";
		this.trangthai = "";
		this.msg="";
		this.userId = "";
		this.thoihan = "";
		this.ttId = "";
		this.tkvay = "";
		this.hanmuc = "";
		this.giainganveTK = "0";
		this.sotaikhoan = "";
		this.tienteId = "100000";
		this.tigia="1";
		this.nppId="";
		this.sotienNT = "0";
		this.sotienVND = "0";
		this.stConlai = "0";
		this.NgayDaoHan = "";
		
		this.db = new dbutils();
	}
	public Nhantienvay(String Id)
	{   
		formatter = new DecimalFormat("#,###,###");
		this.Id = Id;
		this.ctyId = "";
		this.soHD = "";
		this.ngay = "";
		this.sotien = "0";
		this.laisuat = "";
		this.hinhthuc = "";
		this.ghichu = "";
		this.trangthai = "";
		this.userId = "";
		this.thoihan = "";
		this.ttId = "";
		this.tkvay = "";
		this.hanmuc = "";
		this.giainganveTK ="0";
		this.sotaikhoan= "";
		this.tienteId = "100000";
		this.tigia = "";
		this.nppId="";
		this.sotienNT = "";
		this.sotienVND = "";
		this.stConlai = "";
		this.sotienvay ="0";
		this.msg = "";
		this.db = new dbutils();
	}
	public String getNgayDaoHan() {
		return NgayDaoHan;
	}
	public void setNgayDaoHan(String ngayDaoHan) {
		NgayDaoHan = ngayDaoHan;
	}
	
	public String getGiainganveTK() 
	{
		return giainganveTK;
	}
	public void setGiainganveTK(String giainganveTK) 
	{
		this.giainganveTK = giainganveTK;
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
	
	public void setSoHD(String soHD) {
		this.soHD = soHD;
		
	}

	public String getSoHD() {
		return this.soHD;
	}
	
	public void setTkvay(String tkvay) {
		this.tkvay = tkvay;
		
	}

	public String getTkvay() {
		return this.tkvay;
	}

	public void setNgay(String ngay) {
		this.ngay = ngay;
		
	}

	public String getNgay() {
		return this.ngay;
	}
	
	public void setSotien(String sotien) {
		this.sotien = sotien;
		
	}

	public String getSoien() {
		return this.sotien;
	}
	
	public void setLaisuat(String laisuat) {
		this.laisuat = laisuat;
		
	}

	public String getLaisuat() {
		return this.laisuat;
	}

	public void setHinhthuc(String hinhthuc) {
		this.hinhthuc = hinhthuc;
		
	}

	public String getHinhthuc() {
		return this.hinhthuc;
	}

	public void setGhichu(String ghichu) {
		this.ghichu = ghichu;
		
	}

	public String getGhichu() {
		return this.ghichu;
	}

	public void setTrangthai(String trangthai) {
		this.trangthai = trangthai;
		
	}

	public String getTrangthai() {
		return this.trangthai;
	}

	public void setTtId(String ttId) {
		this.ttId = ttId;
		
	}

	public String getTtId() {
		return this.ttId;
	}
	
	public void setUNCIds(String[] uncIds) {
		this.uncIds = uncIds;
		
	}

	public String[] getUNCIds() {
		return this.uncIds;
	}

	public void setNccIds(String[] nccIds) {
		this.nccIds = nccIds;
		
	}

	public String[] getNccIds() {
		return this.nccIds;
	}

	public void setNccTen(String[] nccTen) {
		this.nccTen = nccTen;
		
	}

	public String[] getNccTen() {
		return this.nccTen;
	}

	public void setSotienNTs(String[] sotienNTs) {
		this.sotienNTs = sotienNTs;
		
	}

	public String[] getSotienNTs() {
		return this.sotienNTs;
	}

	public void setTgs(String[] tgs) {
		this.tgs = tgs;
		
	}

	public String[] getTgs() {
		return this.tgs;
	}

	public void setSotienVNDs(String[] sotienVNDs) {
		this.sotienVNDs = sotienVNDs;
		
	}

	public String[] getSotienVNDs() {
		return this.sotienVNDs;
	}

	public void setPays(String[] pays) {
		this.pays = pays;
		
	}

	public String[] getPays() {
		return this.pays;
	}

	public void setRests(String[] rests) {
		this.rests = rests;
		
	}

	public String[] getRests() {
		return this.rests;
	}

	public ResultSet getHDRS() {
		String query = 
			"SELECT PK_SEQ AS HDID, SOHD + ' - ' + DIENGIAI AS HD \n" +
			"FROM ERP_HOPDONGVAY \n" +
			"WHERE TRANGTHAI < 2 AND CONGTY_FK = " + this.ctyId +" \n" +
			"AND SOHD NOT IN (SELECT ISNULL(THAYTHECHO, '') FROM ERP_HOPDONGVAY ) \n" +
			"and PK_SEQ not in \n" +
			"(select hd.PK_SEQ \n" +
			"from ERP_HOPDONGVAY hd \n" +
			"where " + (this.soHD.trim().length() > 0 ? (" hd.PK_SEQ != " + this.soHD + " and ") : "") + "hd.TONGTIEN <=\n" +
			"(select sum(nt.SOTIENVAY)\n" +
			" from ERP_NHANTIENVAY nt \n" +
			"where nt.TRANGTHAI != 2 and nt.HOPDONG_FK = hd.PK_SEQ))";
		System.out.println("hop dong vay : \n" + query + "\n===============================");
		this.HDRS= db.get(query);
		return this.HDRS;
	}

	public void setTtRs(ResultSet ttRs) {
		
		this.ttRs = ttRs;
	}
	
	public ResultSet getUNCRS() {
		
		return this.UNCRS;
	}

	public void setUNCRs(ResultSet UNCRs) {
		
		this.UNCRS = UNCRs;
	}

	public ResultSet getTtRs() {
		if(this.soHD.length() > 0){
			return this.db.get(	"SELECT TT.PK_SEQ AS TTID, TT.MA AS TT " +
								"FROM ERP_TIENTE TT " +
								"INNER JOIN ERP_HOPDONGVAY HDV ON HDV.TIENTE_FK = TT.PK_SEQ " +
								"WHERE TT.TRANGTHAI = 1 " +
								"UNION " +
								"SELECT TT.PK_SEQ AS TTID, TT.MA AS TT " +
								"FROM ERP_TIENTE TT " +
								"INNER JOIN ERP_HOPDONGVAY HDV ON HDV.NGOAITE_FK = TT.PK_SEQ " +
								"WHERE TT.TRANGTHAI = 1");
		}else{
			return null;
		}
	}
	
	public void setHDRS(ResultSet HDRS) {
		
		this.HDRS = HDRS;
	}
		
	public void setUserId(String userId) {
		
		this.userId = userId;
	}
	
	public String getUserId() {
		
		return this.userId;
	}

	public void setThoihan(String thoihan) {
		this.thoihan = thoihan;
		
	}

	public String getThoihan() {
		return this.thoihan;
	}
	
	public void setNgaytravay(String[] ngaytravay) {
		this.ngaytravay = ngaytravay;
		
	}

	public String[] getNgaytravay() {
		return this.ngaytravay;
	}

	public void setTravay(String[] travay) {
		this.travay = travay;
		
	}

	public String[] getTravay() {
		return this.travay;
	}
	
	public void setTralai(String[] tralai) {
		this.tralai = tralai;
		
	}

	public String[] getTralai() {
		return this.tralai;
	}

	public void setConlai(String[] conlai) {
		this.conlai = conlai;
		
	}

	public String[] getConlai() {
		return this.conlai;
	}

	public void init() 
	{	
		//changeNCC();
		try {
			if(this.Id.length() > 0)
			{
				getDinhKhoan();
				String sql =	" SELECT NTV.PK_SEQ AS NTVID, NTV.NGAYNHAN, HDV.PK_SEQ AS SOHD, " +
								" ISNULL(NTV.SOTIENVND, 0) AS SOTIENVND, ISNULL(NTV.SOTIENNT, 0) AS SOTIENNT, ISNULL(NTV.CONLAI, 0) AS CONLAI, " +
								" ISNULL(NTV.TIENTE_FK, HDV.TIENTE_FK) AS TIENTE_FK, ISNULL(NTV.LAISUAT, 0) AS LAISUAT, NTV.HINHTHUC, NTV.TKVAY, " +
								" NTV.GHICHU, NTV.TRANGTHAI, ISNULL(NTV.THOIHAN, 0) AS THOIHAN, ISNULL(VAO_TKTIEN,1) AS VAO_TKTIEN , SOTAIKHOAN, ISNULL(NTV.TIGIA,1) AS TIGIA " +
								" , NTV.NGAYDAOHAN as NGAYDAOHAN,NTV.SOTIENVAY " +
								" FROM ERP_NHANTIENVAY NTV " +
								" INNER JOIN ERP_HOPDONGVAY HDV ON HDV.PK_SEQ = NTV.HOPDONG_FK " +
								" WHERE NTV.PK_SEQ= " + this.Id + " " +
								" order by ntv.ngayTao, ntv.PK_SEQ";
				
				System.out.println("1.Khoi tao VAY: " + sql);
				ResultSet rs = db.get(sql);
			
				while(rs.next())
				{
					this.Id = rs.getString("NTVID");
					this.soHD = rs.getString("SOHD");					
					this.ngay = rs.getString("NGAYNHAN");
					this.sotienVND = rs.getString("SOTIENVND");
					this.sotienNT = rs.getString("SOTIENNT");
					this.stConlai = rs.getString("CONLAI");
					this.ttId = rs.getString("TIENTE_FK");
					this.tienteId=rs.getString("TIENTE_FK");
					this.laisuat = rs.getString("LAISUAT");
					this.hinhthuc = rs.getString("HINHTHUC");
					this.ghichu = rs.getString("GHICHU");
					this.tkvay  = rs.getString("TKVAY");
					this.trangthai = rs.getString("TRANGTHAI");
					this.thoihan = rs.getString("THOIHAN");
					this.giainganveTK =  rs.getString("VAO_TKTIEN");
					this.sotaikhoan = rs.getString("SOTAIKHOAN");
					this.tigia = rs.getString("TIGIA");
					this.NgayDaoHan = rs.getString("NGAYDAOHAN");
					this.sotienvay= rs.getString("SOTIENVAY");
				}
				rs.close();
				
				System.out.println(this.tienteId);
				this.ngaytravay = new String[Integer.parseInt(this.thoihan)+ 4];
				this.travay = new String[Integer.parseInt(this.thoihan) + 4];
				this.tralai = new String[Integer.parseInt(this.thoihan) + 4];
				this.conlai = new String[Integer.parseInt(this.thoihan) + 4];

				for(int n = 0; n < Integer.parseInt(this.thoihan) + 4; n++){
					this.ngaytravay[n] = "";
					this.travay[n]  = "0";
					this.tralai[n] = "0";
					this.conlai[n] = "0";					
				}

				String query = "SELECT COUNT(*) AS NUM FROM ERP_KEHOACHTRAVAY WHERE NHANTIENVAY_FK = " + this.Id + "";
				System.out.println("__KE HOACH TRA VAY: " + query);
				
				rs = this.db.get(query);				
				rs.next();
				if(Integer.parseInt(rs.getString("NUM")) > Integer.parseInt(this.thoihan)){
					this.db.update("DELETE ERP_KEHOACHTRAVAY WHERE NHANTIENVAY_FK = " + this.Id + "");
					rs.close();
				}else{
					rs.close();
				
					rs = this.db.get("SELECT NGAYTRAVAY, ISNULL(SOTIENTRAGOC, 0) AS SOTIENTRAGOC, ISNULL(SOTIENLAI, 0) AS SOTIENLAI, " +
								 	"ISNULL(SOTIENCONLAI, 0) AS  SOTIENCONLAI FROM ERP_KEHOACHTRAVAY WHERE NHANTIENVAY_FK = " + this.Id + "");

					int i = 0;
					if(rs != null){
						while(rs.next()){
							this.ngaytravay[i] = rs.getString("NGAYTRAVAY");
							this.travay[i]  = rs.getString("SOTIENTRAGOC");
							this.tralai[i] = rs.getString("SOTIENLAI");
							this.conlai[i] = rs.getString("SOTIENCONLAI");
							i++;
						}
					}
				
				}
			} 

		}catch (Exception e) 
		{
			e.printStackTrace();
			System.out.println("___Exception Tra vay : " + e.getMessage() );
		}
	
	}
   
	private void getDinhKhoan() {
		String query = 
			"select ps.pk_seq, (case ps.NO when 0 then N'CÓ' else N'NỢ' end) as No_Co \n" +
			"	, (case ps.NO when 0 then ps.CO else ps.NO end) as soTien \n" +
			"	, (select tk.SOHIEUTAIKHOAN from ERP_TAIKHOANKT tk where tk.PK_SEQ = ps.TAIKHOAN_FK) as soHieuTk \n" +
			"	, isnull((case ps.DOITUONG when N'Ngân hàng' then (select nh.TEN from ERP_NGANHANG nh where PK_SEQ = ps.MADOITUONG) \n" +
			"						when N'Nhà cung cấp' then (select ncc.TEN from ERP_NHACUNGCAP ncc where ncc.PK_SEQ = ps.MADOITUONG) end \n" +
			"	), '') as doiTuong \n" +
			"from ERP_PHATSINHKETOAN ps \n" +
			"where LOAICHUNGTU like N'Giải ngân' and ps.SOCHUNGTU = " + this.Id + "\n";
			
		this.dinhKhoanRs = this.db.get(query);
	}
	
	//Tính số tiền còn lại của hợp đồng sau các lần giải ngân trước
	private void TinhSoTien()
	{
		if (this.soHD.trim().length() > 0)
		{
			String query = 
				"select \n" + 
				"(select hdv.tongtien from erp_hopdongvay hdv where hdv.pk_seq = " + this.soHD + ") as tongTienHDV, \n" + 
				"(select isnull(SUM(ntv.soTienvay), 0) \n" + 
				"from erp_nhantienvay ntv \n" + 
				"where (ntv.trangThai = 1) and ntv.hopdong_fk = " + this.soHD + ") as tongTienVND \n" +
				", (select isnull(SUM(ntv.SOTIENNT), 0) \n" + 
				"from erp_nhantienvay ntv \n" + 
				"where (ntv.trangThai = 1) and ntv.hopdong_fk = " + this.soHD + ") as tongTienNgoaiTe \n";
			System.out.println("cau lenh lay so tien: \n" + query + "\n===============================================");
			Double tongTienHDV =  new Double(0);
			Double tongTienVND =  new Double(0);
			Double tongTienNgoaiTe =  new Double(0);
			
			ResultSet rs = this.db.get(query);
			try {
				if (rs.next())
				{
					tongTienHDV = rs.getDouble("tongTienHDV");
					tongTienVND = rs.getDouble("tongTienVND");
					tongTienNgoaiTe = rs.getDouble("tongTienNgoaiTe");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}finally
			{
				try {
					rs.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
			tongTienNgoaiTe = tongTienHDV - tongTienNgoaiTe;
			this.sotienNT = tongTienNgoaiTe.toString();
			if (!this.tienteId.trim().equals("100000"))
			{
				tongTienVND = tongTienNgoaiTe * Double.parseDouble(this.tigia);
			}
			else
				tongTienVND = tongTienHDV - tongTienVND;
			this.sotienVND = tongTienVND.toString();
			System.out.println("tien VND: " + this.sotienVND);
		}
	}
	
	public void XacdinhTienTe(){
		ResultSet rs;
		if(this.soHD.length() > 0){
//		if(this.tienteId == null || this.tienteId.equals("")){
			String query = "SELECT TIENTE_FK FROM ERP_HOPDONGVAY WHERE PK_SEQ = '" + this.soHD + "'";
			rs = this.db.get(query);
			try{
				rs.next();
				this.tienteId = rs.getString("TIENTE_FK");
				rs.close();
			}catch(java.sql.SQLException e){
				e.printStackTrace();
			}
//		}	
		}
		TinhSoTien();
	}
	
	public void setmsg(String msg) {
		
		this.msg = msg;
	}
	
	public String getmsg() {
		
		return this.msg;
	}
	
	public boolean Xoa()
	{
		
		
		try {
			this.db.getConnection().setAutoCommit(false);
			String query = "DELETE ERP_KEHOACHTRAVAY WHERE NHANTIENVAY_FK = " + this.Id + "";
			System.out.println(query);
			
			if (!this.db.update(query))
			{
				this.msg = "D1.1 Không thể xóa phiếu giải ngân";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "DELETE ERP_NHANTIENVAY_THANHTOANHD WHERE NHANTIENVAY_FK = " + this.Id + "";
			System.out.println(query);
			
			if (!this.db.update(query))
			{
				this.msg = "D1.2 Không thể xóa phiếu giải ngân";
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "DELETE ERP_NHANTIENVAY WHERE PK_SEQ = " + this.Id + "";
			System.out.println(query);
			
			if (!this.db.update(query))
			{
				this.msg = "D1.3 Không thể xóa phiếu giải ngân";
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			try {
				this.db.getConnection().setAutoCommit(true);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		return true;
	}
	
	public void Init_Array(){
		
		if(this.thoihan.trim().length() > 0 & this.travay == null){
			
			this.ngaytravay = new String[Integer.parseInt(this.thoihan)];
			this.travay = new String[Integer.parseInt(this.thoihan)];
			this.tralai = new String[Integer.parseInt(this.thoihan)];
			this.conlai = new String[Integer.parseInt(this.thoihan)];

			for(int i = 0; i < Integer.parseInt(this.thoihan); i++){
				System.out.println("I am here");
				this.ngaytravay[i] = "";
				this.travay[i] = "0";
				this.tralai[i] = "0";
				this.conlai[i] = this.sotien;
			}
			
		}

	}
	
	public String Hoantat()
	{
		String msg = "";
		
		try
		{
			db.getConnection().setAutoCommit(false);
			// tiền tệ gốc lúc làm hợp đồng vay
			// nếu tiền việt 100000 
			
			//Nếu giải ngân về tài khoản tiền mà chưa chọn ngân hàng công ty
			if (this.sotaikhoan != null && this.giainganveTK != null && this.sotaikhoan.trim().length() == 0 && this.giainganveTK.trim().equals("1"))
			{
				this.msg = "Giải ngân về tài khoản tiền chưa chọn tài khoản ngân hàng công ty";
				db.getConnection().rollback();
				return this.msg;
			}
			
			 String query = " SELECT TIENTE_FK FROM ERP_HOPDONGVAY WHERE  PK_SEQ  IN " +  
							" (SELECT HOPDONG_fK FROM ERP_NHANTIENVAY A WHERE PK_SEQ =    "+this.Id+" ) ";
			ResultSet rshd=db.get(query);
			String tientegoc="";
			if(rshd.next()){
				tientegoc=rshd.getString("TIENTE_FK");
			}
			rshd.close();
			
			query=" SELECT  a.pk_seq, a.TIENTE_FK,NGAYNHAN ,tien.ma   FROM ERP_NHANTIENVAY A inner join erp_tiente tien on tien.pk_seq=a.tiente_fk " +
				  " WHERE HOPDONG_FK= (SELECT HOPDONG_fK FROM ERP_NHANTIENVAY A WHERE PK_SEQ =    "+this.Id+" ) AND  "+
				  " NOT EXISTS ( select TIGIAQUYDOI from ERP_TIGIA T where T.TIENTE_FK = A.TIENTE_FK  AND TuNgay <= A.NGAYNHAN AND   A.NGAYNHAN <=DenNgay )";
			ResultSet rscheck=db.get(query);
			String msg1="";
			while(rscheck.next()){
				msg1=rscheck.getString("pk_seq") +" - "+rscheck.getString("MA");
			}
			rscheck.close();
			if(msg1.length() >0){
				db.getConnection().rollback();
				return "Chứng từ giải ngân và tiền tệ chưa có khai báo tỉ giá trong tháng :"+msg1+" ,vui lòng khai báo tỉ giá trong tháng giải ngân để thực hiện tiếp nghiệp vụ ";
			}
			
			
				//quy het ve tien goc trong hop dong de so sanh
			
			query =     " SELECT ISNULL(HDV.TONGTIEN, 0) -  ISNULL(NTV.SOTIENVIET,0)  AS CONLAI "+ 
						 " 	FROM  "+ 
						 " 	(	 "+ 
						 " 	SELECT   a.PK_SEQ ,   a.TONGTIEN  ,a.TIENTE_FK"+ 
						 " 	FROM ERP_HOPDONGVAY a  "+ 
						 " 	where a.PK_SEQ in (SELECT A.HOPDONG_FK FROM ERP_NHANTIENVAY A WHERE PK_SEQ =    "+this.Id+" )"+ 
						 " "+ 
						 "  ) HDV    "+ 
						 " 	INNER JOIN (  "+ 
						 "  "+ 
						 " 	SELECT SUM(SOTIENVIET) AS SOTIENVIET,HOPDONG_FK"+ 
						 " 	FROM("+ 
						 " 	SELECT    case when A.TIENTE_FK   ="+tientegoc+" THEN "+ 
						 " 	A.SOTIENVAY  "+ 
						 " 	ELSE (  A.SOTIEN * A.TIGIA /  "+ 
						 " "+ 
						 " 	( select TIGIAQUYDOI from ERP_TIGIA where TIENTE_FK ="+tientegoc+" AND TuNgay <= A.NGAYNHAN AND  A.NGAYNHAN<=DenNgay  ) "+ 
						 " "+ 
						 " 	) END "+ 
						 " 	AS SOTIENVIET   , A.HOPDONG_FK	 "+ 
						 " 	FROM ERP_NHANTIENVAY A	 "+ 
						 " 	WHERE A.TRANGTHAI  <> '2' 	AND A.HOPDONG_FK in(SELECT A.HOPDONG_FK FROM ERP_NHANTIENVAY A WHERE PK_SEQ =    "+this.Id+" ) "+ 
						 " "+ 
						 " 	) VAY GROUP BY VAY.HOPDONG_FK "+ 
						 "  )NTV  ON HDV.PK_SEQ = NTV.HOPDONG_FK ";
 
			
			ResultSet rs = this.db.get(query);
			
			System.out.println("câu truy vấn hoàn tất: "+ query);
			if(rs.next())
			{
				if(Double.parseDouble(rs.getString("CONLAI")) >= 0 )
				{
					query = "UPDATE ERP_NHANTIENVAY SET TRANGTHAI = '1' WHERE PK_SEQ = " + this.Id + "";
					if(!this.db.update(query))
					{
						db.getConnection().rollback();
						rs.close();
						return "Lỗi chốt giải ngân: " + query;
					}
					
					query = "UPDATE ERP_HOPDONGVAY SET TRANGTHAI = 1 WHERE PK_SEQ IN (SELECT HOPDONG_FK FROM ERP_NHANTIENVAY WHERE PK_SEQ = " + this.Id + ")";
					if(!this.db.update(query))
					{
						db.getConnection().rollback();
						rs.close();
						return "Lỗi chốt giải ngân: " + query;
					}			
				}
				else
				{
					db.getConnection().rollback();
					msg = "Hãy điều chỉnh lại số tiền giải ngân, vì tổng số tiền giải ngân không thể vượt quá tổng số tiền trong hợp đồng vay";
					return msg;
				}
			}
			rs.close();
			String NGAYNHAN="";
			
			//GHI NHAN BOOK TOAN
			Utility util = new Utility();
 
			//Không có trung tâm doanh thu va trung tâm chi phí
			//Kiểm tra xem là giải ngân về tk tiền hay giải ngân đi thẳng UNC
			String VAO_TKTIEN = "";
			query = "select a.VAO_TKTIEN from ERP_NHANTIENVAY a where PK_SEQ = " + this.Id;
			ResultSet tmpRs = db.get(query);
			if (tmpRs.next())
			{
				VAO_TKTIEN = tmpRs.getString("VAO_TKTIEN");
			}
			if (VAO_TKTIEN.trim().equals("1"))//Nếu vào tk tiền
			{
				query =  
					"select sotienvay as sotienVIET, round(sotienvay/a.tigia,0) AS SOTIENNT, a.TIENTE_FK, a.NGAYNHAN,\n" +    
					" (select SOHIEUTAIKHOAN --Chuyen khoan -> tk duoi ngan hang \n" +
					"						  from ERP_TAIKHOANKT  \n" +
					"                          where pk_seq in ( select TaiKhoan_fk \n" + 
					"											from ERP_NGANHANG_CONGTY \n" + 
					"											where ERP_NGANHANG_CONGTY.soTaiKhoan = a.soTaiKhoan) ) \n" + 
					"	 as sohieutaikhoanNO   \n" +
					"	,(case b.Loai when '1' then   --Hop dong vay ngan han \n" +
					"						   (case a.TIENTE_FK when '100000' then '34111000'--Giai ngan la VND \n" +
					"						        			  when '100001' then '34112000'--Giai ngan la USD \n" +
					"							end)  \n" +
					"				  when '2' then --Hop dong vay dai han \n" +
					"							(case a.TIENTE_FK when '100000' then '34113000'--Giai ngan la VND \n" +
					"						        			  when '100001' then '34114000'--Giai ngan la USD \n" +
					"							end) \n" +
					"	   end)							  \n" +            
					"   as sohieutaikhoanCO   \n" +
					"	, N'Ngân hàng' as doiTuongNo \n" +
					"	, (select ERP_NGANHANG_CONGTY.PK_SEQ \n" +
					"		from ERP_NGANHANG_CONGTY  \n" +
					"		where ERP_NGANHANG_CONGTY.soTaiKhoan = a.soTaiKhoan) \n" + 					
					"	as maDoiTuongNo \n" +
					"	, N'Ngân hàng' as doiTuongCo \n" +
					"	, (select ERP_NGANHANG_CONGTY.PK_SEQ \n" +
					"		from ERP_NGANHANG_CONGTY  \n" +
					"		where ERP_NGANHANG_CONGTY.soTaiKhoan = a.soTaiKhoan) \n" + 					
					"	as maDoiTuongCo,A.GHICHU \n" +
					"from ERP_NHANTIENVAY a  \n" +
					"inner join ERP_HOPDONGVAY b on a.HOPDONG_FK = b.PK_SEQ \n" +  
					"where a.PK_SEQ = '"+  this.Id  +"'";
			}
			else//Nếu vào UNC trả nợ cho NCC
			{
				query =
					//Số tiền thanh toán cho từng phiếu UNC
					"select ntv_tt.THANHTOANVND as sotienVIET, ntv_tt.THANHTOANNT as SOTIENNT, ntv.TIENTE_FK, ntv.NGAYNHAN \n" +
					"	, isnull((select tk.SOHIEUTAIKHOAN \n" +
					"			  from ERP_NHACUNGCAP ncc \n" +
					"			  inner join ERP_TAIKHOANKT tk on tk.PK_SEQ = ncc.TAIKHOAN_FK \n" +
					"			  where ncc.PK_SEQ = ntv_tt.NCC_FK), '') \n" +
					"	as sohieutaikhoanNO \n" +
					"	,(case hdv.Loai when '1' then   --Hop dong vay ngan han \n" +
					"											   (case ntv.TIENTE_FK when '100000' then '34113000'--Giai ngan la VND \n" +
					"											        			  when '100001' then '34114000'--Giai ngan la USD \n" +
					"												end)  \n" +
					"									  when '2' then --Hop dong vay dai han \n" +
					"												(case ntv.TIENTE_FK when '100000' then '34111000'--Giai ngan la VND \n" +
					"											        			  when '100001' then '34112000'--Giai ngan la USD \n" +
					"												end) \n" +
					"						   end) \n" +							              
					"	as sohieutaikhoanCO \n" +
					"	, N'Nhà cung cấp' as doiTuongNo, ntv_tt.NCC_FK as maDoiTuongNo \n" +
					"	, N'Ngân hàng' as doiTuongCo \n" +
					"	, (select ERP_NGANHANG_CONGTY.PK_SEQ \n" +
					"		from ERP_NGANHANG_CONGTY  \n" +
					"		where ERP_NGANHANG_CONGTY.soTaiKhoan = ntv.soTaiKhoan)  as maDoiTuongCo ,ntv.GHICHU --Ngân hàng cho vay \n" +//Ngân hàng cho vay
					"from ERP_NHANTIENVAY ntv \n" +
					"inner join ERP_HOPDONGVAY hdv on hdv.PK_SEQ = ntv.HOPDONG_FK \n" +
					"inner join ERP_NHANTIENVAY_THANHTOANHD ntv_tt on ntv_tt.NHANTIENVAY_FK = ntv.PK_SEQ \n" +
					"inner join ERP_THANHTOANHOADON tthd on tthd.PK_SEQ = ntv_tt.THANHTOANHOADON_FK \n" + 
					"where ntv.PK_SEQ = " + this.Id + " \n" ;

			}
			System.out.println("DVKD :"+query);
			ResultSet psktRs = db.get(query);
	 
			while(psktRs.next())
			{
				double tonggiatri = Math.round(psktRs.getDouble("sotienVIET"));
			
				if (tonggiatri > 0)
				{
					double tonggiatriNT = Math.round(psktRs.getDouble("SOTIENNT"));
					NGAYNHAN= psktRs.getString("NGAYNHAN");
					String nam = psktRs.getString("NGAYNHAN").substring(0, 4);
					String thang = psktRs.getString("NGAYNHAN").substring(5, 7);
					String tiente_fk = psktRs.getString("TIENTE_FK");
				
					String taikhoanCO_SoHieu = psktRs.getString("sohieutaikhoanCO") == null ? "" : psktRs.getString("sohieutaikhoanCO") ;
					String taikhoanNO_SoHieu = psktRs.getString("sohieutaikhoanNO") == null ? "" : psktRs.getString("sohieutaikhoanNO") ;
					String diengiai = psktRs.getString("GHICHU") == null ? "" : psktRs.getString("GHICHU") ;
					String doiTuongCo = psktRs.getString("doiTuongCo") == null ? "" : psktRs.getString("doiTuongCo") ;
					String maDoiTuongCo = psktRs.getString("maDoiTuongCo") == null ? "" : psktRs.getString("maDoiTuongCo") ;
					
					String doiTuongNo = psktRs.getString("doiTuongNo") == null ? "" : psktRs.getString("doiTuongNo") ;
					String maDoiTuongNo = psktRs.getString("maDoiTuongNo") == null ? "" : psktRs.getString("maDoiTuongNo") ;
					/*String dvkdId = psktRs.getString("DVKD") == null ? "" : psktRs.getString("DVKD") ;*/
					if(taikhoanCO_SoHieu.trim().length() <= 0 || taikhoanNO_SoHieu.trim().length() <= 0 )
					{
						msg = "1.1 Lỗi xác định tài khoản kế toán. Vui lòng kiểm tra lại thông tin dữ liệu nền trước khi chốt.";
						db.getConnection().rollback();
						return msg;
					}
					
					msg = util.Update_TaiKhoan_TheoSoHieu_ThemDoiTuong(db, this.ctyId,thang, nam, psktRs.getString("NGAYNHAN"), psktRs.getString("NGAYNHAN"), "Giải ngân", this.Id, taikhoanNO_SoHieu, taikhoanCO_SoHieu, "", 
												Double.toString(tonggiatri), Double.toString(tonggiatri)
												, doiTuongNo, maDoiTuongNo, "0", doiTuongCo, maDoiTuongCo, "0"
												, "", "", tiente_fk, "", "1", Double.toString(tonggiatri), Double.toString(tonggiatriNT), "Giải ngân" ,"0","0",diengiai);
				
					if(msg.trim().length() > 0)
					{
						psktRs.close();
						db.getConnection().rollback();
						return msg;
					}
				}				
			}
			psktRs.close();
			
			//Chốt Ủy nhiệm chi
			query = "SELECT NTV.VAO_TKTIEN, NTV_TT.THANHTOANHOADON_FK " +
					"FROM ERP_NHANTIENVAY_THANHTOANHD NTV_TT " +
					"INNER JOIN ERP_NHANTIENVAY NTV ON NTV.PK_SEQ = NTV_TT.NHANTIENVAY_FK "	+
					"WHERE NTV.PK_SEQ = " + this.Id + "";
			
			rs = this.db.get(query);
			while(rs.next()){
				if(rs.getString("VAO_TKTIEN").equals("0")){ 
					
					String thanhtoanid=rs.getString("THANHTOANHOADON_FK");
					query="update erp_thanhtoanhoadon set trangthai=1 where pk_seq="+thanhtoanid;
					if(!this.db.update(query))
					{
						db.getConnection().rollback();
						rs.close();
						return "Lỗi chốt giải ngân: " + query;
					}		

				}
			}
			rs.close();
			  
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			
			db.shutDown();
		}
		catch(Exception e)
		{
			
			e.printStackTrace();
			msg = "Lỗi chốt giải ngân: " + e.getMessage();
			db.update("rollback");
		}
		
		return msg;
	}

	

	
	public boolean save() {
		try{		
			String query;
			
			if(this.ngay.length() == 0)
			{  
				this.msg = "Vui lòng nhập ngày giải ngân";
				return false;
			}else if(this.soHD.length() == 0 ){
				this.msg = "Vui lòng chọn hợp đồng";
				return false;
								
			}else if(this.giainganveTK.equals("1") && this.sotaikhoan.length() == 0 && this.hinhthuc.trim().equals("1")){
				this.msg = "Vui lòng chọn tài khoản nhận giải ngân";
				return false;				
			}else{
				/*  query = "SELECT TIENTE_FK FROM ERP_HOPDONGVAY WHERE PK_SEQ = '" + this.soHD + "'";
				ResultSet rs = this.db.get(query);
				rs.next();
				this.tienteId = rs.getString("TIENTE_FK");
				rs.close();		*/		
			}
			if(!this.tienteId.equals("100000")){
				
				double tigia_=0;
				try{
					tigia_=Double.parseDouble(this.tigia.replaceAll(",", ""));
				}catch(Exception err){
					
				}
				if(tigia_<=1){
					this.msg="Vui lòng nhập tỉ giá giải ngân";
					return false;
				}
			}
			
		
			//boolean check = this.KiemtraSotien();
			boolean check = true;
			
			if(check){
				db.getConnection().setAutoCommit(false);
				if(this.Id.length() >0)
				{	
					
					double sotientt=0;
					try{
						if(Double.parseDouble(this.sotienNT.replace(",", "")) >0){
							sotientt=Double.parseDouble(this.sotienNT.replace(",", ""));
						}else{
							sotientt=Double.parseDouble(this.sotienVND.replace(",", ""));
						}
					}catch(Exception err){
						
					}
					if(sotientt<=0){
						this.msg="Vui lòng nhập số tiền giải ngân";
						this.db.getConnection().rollback();
						return false;
					}
					
					
					
					query =	" UPDATE ERP_NHANTIENVAY SET SOTIEN="+sotientt+", HOPDONG_FK = '" + this.soHD + "', NGAYNHAN = '"+ this.ngay +"', " +
							" SOTIENVND = "+ this.sotienVND +", SOTIENNT = " + this.sotienNT + ", CONLAI = " + this.stConlai+ ", TIGIA = " + this.tigia + ", " +
							" TIENTE_FK = " + this.tienteId + ", TKVAY = N'" + this.tkvay + "', " +
							" LAISUAT = '" + this.laisuat + "', HINHTHUC = '"+ this.hinhthuc + "', THOIHAN = '" + this.thoihan + "', " +
							" GHICHU = N'" + this.ghichu + "', NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', VAO_TKTIEN = '" + this.giainganveTK + "' " +
							" ,NGAYDAOHAN = '"+ this.NgayDaoHan +"',SOTIENVAY=  "+this.sotienvay+" "+
							" WHERE PK_SEQ ='"+ this.Id +"'";
				 
					 
					if(!db.update(query))
					{	
						db.update("rollback");
						this.msg =query;
						return false;
					}

					query  = "DELETE ERP_NHANTIENVAY_THANHTOANHD WHERE NHANTIENVAY_FK = " + this.Id + "";
					if(!db.update(query))
					{	
						db.update("rollback");
						this.msg =query;
						return false;
					}

					if(this.giainganveTK.equals("0")){
						if(this.uncIds != null){
							for(int i = 0; i < this.uncIds.length; i++){
								if(Float.parseFloat(pays[i].replaceAll(",", "")) > 0){
									query = "INSERT INTO ERP_NHANTIENVAY_THANHTOANHD(NHANTIENVAY_FK, THANHTOANHOADON_FK, NCC_FK, " +
										    "NCC, SOTIENNT, SOTIENVND, TIGIA, THANHTOANVND, CONLAIVND) VALUES(" + this.Id + ", "  +
											"" + this.uncIds[i] + ", " + this.nccIds[i] + ", N'" + this.nccTen[i] + "', " + this.sotienNTs[i].replaceAll(",", "") + ", " + this.sotienVNDs[i].replaceAll(",", "") + ", " +
											"" + this.tgs[i].replaceAll(",", "") + ", " + this.pays[i].replaceAll(",", "") + ", " + this.conlaiHD[i].replaceAll(",", "") + ")";
									 
									if(!db.update(query))
									{	
										db.update("rollback");
										this.msg =query;
										return false;
									}
									
									if(Double.parseDouble(this.sotienNTs[i].replaceAll(",", "")) > 0){
										query = "UPDATE ERP_THANHTOANHOADON SET TIGIA = " + this.tgs[i].replaceAll(",", "") + ", " +
												"SOTIENTT = " + this.sotienVNDs[i].replaceAll(",", "") + ", " +
												"CHENHLECHVND = "  + this.sotienVNDs[i].replaceAll(",", "") + " - SOTIENHD " +
												"WHERE PK_SEQ = " + this.uncIds[i] + "";
									 
										if(!db.update(query))
										{	
											db.update("rollback");
											this.msg =query;
											return false;
										}
									}
									
								}
							}
						}
					}else{
						query = "UPDATE ERP_NHANTIENVAY SET SOTAIKHOAN = N'" + this.sotaikhoan + "' WHERE PK_SEQ = " + this.Id + "";
						if(!db.update(query))
						{	
							db.update("rollback");
							this.msg =query;
							return false;
						}
					}
					
					query = "DELETE ERP_KEHOACHTRAVAY WHERE NHANTIENVAY_FK = " + this.Id + "";
					if(!db.update(query))
					{	
						db.update("rollback");
						this.msg =query;
						return false;
					}
					
					if(this.ngaytravay != null){
						String ngaytra = this.ngay;
						double laivay;
						double conlai = 0;
						
						if(this.tienteId.equals("100000"))
						{
						   conlai = Double.parseDouble(this.sotienVND.replace(",", ""));
						}else
						{
							 conlai = Double.parseDouble(this.sotienNT.replace(",", ""));
						}
						
						for(int i = 0; i < this.ngaytravay.length; i++){
							if(this.ngaytravay[i].trim().length() > 0){
								query = "SELECT DATEDIFF(day,'" + ngaytra + "','" + this.ngaytravay[i] + "') AS DIFF";
								ResultSet rs = this.db.get(query);
								rs.next();
								laivay = (conlai*Double.parseDouble(rs.getString("DIFF"))*Double.parseDouble(this.laisuat)/360)/100;
								if(laivay < 0) laivay = 0;
								rs.close();
								
								ngaytra = this.ngaytravay[i].trim();
								conlai = conlai - Double.parseDouble(this.travay[i].replace(",", ""));
								
								query = "INSERT INTO ERP_KEHOACHTRAVAY(NHANTIENVAY_FK, NGAYTRAVAY, SOTIENTRAGOC, SOTIENLAI, SOTIENCONLAI) " +
										"VALUES('" + this.Id + "', '" + this.ngaytravay[i].trim() + "', " + this.travay[i].replace(",", "") + ", " + laivay + ", " + conlai + ")" ;
								 
								if(!db.update(query))
								{	
									db.update("rollback");
									this.msg =query;
									return false;
								}
																								
							}
						}
					}
				}
				else
				{      
					
					double sotientt=0;
					try{
						if(Double.parseDouble(this.sotienNT.replace(",", "")) >0){
							sotientt=Double.parseDouble(this.sotienNT.replace(",", ""));
						}else{
							sotientt=Double.parseDouble(this.sotienVND.replace(",", ""));
						}
					}catch(Exception err){
						
					}
					if(sotientt<=0){
						this.msg="Vui lòng nhập số tiền giải ngân";
						this.db.getConnection().rollback();
						return false;
					}
					
					
					query =	"INSERT INTO ERP_NHANTIENVAY(HOPDONG_FK, NGAYNHAN,SOTIENVND, SOTIENNT, TIENTE_FK, TKVAY, LAISUAT, " +
							"HINHTHUC, THOIHAN, GHICHU, NGUOITAO, NGAYTAO, NGUOISUA, NGAYSUA, TRANGTHAI, CONGTY_FK, TIGIA, VAO_TKTIEN," +
							"NGAYDAOHAN, SOTIEN,SOTIENVAY) VALUES("+
							"'" + this.soHD + "', '" + this.ngay + "','" + this.sotienVND + "', '" + this.sotienNT + "', " + this.tienteId + ", N'" + this.tkvay + "', " +
							"'" + this.laisuat + "', '" + this.hinhthuc + "', '" + this.thoihan + "', N'" + this.ghichu + "'," +
							"'"+ this.userId +"', '" + this.getDateTime() + "','" + this.userId + "','" + this.getDateTime() +"', " +
							"'0', " + this.ctyId + "," + this.tigia + ", " + this.giainganveTK + ",'"+this.NgayDaoHan+ "',"+sotientt+","+this.sotienvay+")";
			
					if(!db.update(query))
					{	
						db.update("rollback");
						this.msg =query;
						return false;
					}
					 //System.out.println(sql);
					query = "SELECT IDENT_CURRENT('ERP_NHANTIENVAY') AS ID";
					
					ResultSet rs = this.db.get(query);						
					rs.next();
					this.Id = rs.getString("ID");
					rs.close();

					query = "DELETE ERP_NHANTIENVAY_THANHTOANHD WHERE NHANTIENVAY_FK = " + this.Id + "";
					if(!db.update(query))
					{	
						db.update("rollback");
						this.msg =query;
						return false;
					}

					if(this.giainganveTK.equals("0")){
						if(this.uncIds != null){
							for(int i = 0; i < this.uncIds.length; i++){
								if(Float.parseFloat(pays[i].replaceAll(",", "")) > 0){
									query = "INSERT INTO ERP_NHANTIENVAY_THANHTOANHD(NHANTIENVAY_FK, THANHTOANHOADON_FK, NCC_FK, " +
										    "NCC, SOTIENNT, SOTIENVND, TIGIA, THANHTOANVND, CONLAIVND) VALUES(" + this.Id + ", "  +
											"" + this.uncIds[i] + ", " + this.nccIds[i] + ", N'" + this.nccTen[i] + "', " + this.sotienNTs[i].replaceAll(",", "") + ", " + this.sotienVNDs[i].replaceAll(",", "") + ", " +
											"" + this.tgs[i].replaceAll(",", "") + ", " + this.pays[i].replaceAll(",", "") + ", " + this.conlaiHD[i].replaceAll(",", "") + ")";
									if(!db.update(query))
									{	
										db.update("rollback");
										this.msg =query;
										return false;
									}
									
									if(Double.parseDouble(this.sotienNTs[i].replaceAll(",", "")) > 0){
										query = "UPDATE ERP_THANHTOANHOADON SET TIGIA = " + this.tgs[i].replaceAll(",", "") + ", SOTIENTT = " + this.sotienVNDs[i].replaceAll(",", "") + " " +
												"WHERE PK_SEQ = " + this.uncIds[i] + "";
										if(!db.update(query))
										{	
											db.update("rollback");
											this.msg =query;
											return false;
										}
									}

								}
							}
						}
					}else{
						query = "UPDATE ERP_NHANTIENVAY SET SOTAIKHOAN = N'" + this.sotaikhoan + "' WHERE PK_SEQ = " + this.Id + "";
						if(!db.update(query))
						{	
							db.update("rollback");
							this.msg =query;
							return false;
						}
					}
					
					if(this.ngaytravay != null){
						String ngaytra = this.ngay;
						double laivay;
						double conlai =  0;
						if(this.tienteId.equals("100000"))
						{
						   conlai = Double.parseDouble(this.sotienVND.replace(",", ""));
						}else
						{
							 conlai = Double.parseDouble(this.sotienNT.replace(",", ""));
						}
					
						for(int i = 0; i < this.ngaytravay.length; i++){
							if(this.ngaytravay[i].trim().length() > 0){
								query = "SELECT DATEDIFF(day,'" + ngaytra + "','" + this.ngaytravay[i] + "') AS DIFF";
								rs = this.db.get(query);
								rs.next();
								laivay = (conlai*Double.parseDouble(rs.getString("DIFF"))*Double.parseDouble(this.laisuat)/360)/100;
								if(laivay < 0) laivay = 0;
							
								rs.close();
														
								ngaytra = this.ngaytravay[i].trim();
								conlai = conlai - Double.parseDouble(this.travay[i].replace(",", ""));
							
								query = "INSERT INTO ERP_KEHOACHTRAVAY(NHANTIENVAY_FK, NGAYTRAVAY, SOTIENTRAGOC, SOTIENLAI, SOTIENCONLAI) " +
										"VALUES('" + this.Id + "', '" + this.ngaytravay[i].trim() + "', " + this.travay[i].replace(",", "") + ", " + laivay + ", " + conlai + ")" ;
								if(!db.update(query))
								{	
									db.update("rollback");
									this.msg =query;
									return false;
								}
							}
						}
					}
				}
				db.getConnection().commit();
				db.getConnection().setAutoCommit(true);
			}else{
				this.msg = "Số tiền giải ngân phải nhỏ hơn số tiền giải ngân còn lại của hợp đồng: " + formatter.format(Double.parseDouble(this.hanmuc)) + " VNĐ . Vui lòng kiểm tra lại số tiền giải ngân";
				return false;
			}
		}catch( Exception e){
			this.msg=e.getMessage();
			db.update("rollback");
			
			return false;
		}
		return true;
	}
	
	private boolean KiemtraSotien(){	
		String query = "SELECT ISNULL(LICHSU, '') AS LICHSU FROM ERP_HOPDONGVAY WHERE PK_SEQ = " + this.soHD + "";
		ResultSet rs = this.db.get(query);
		String lichsu = "'" + this.soHD + "',";
		String[] tmp;
		try{
			rs.next();
			
			tmp = rs.getString("LICHSU").split(",");				
			rs.close();
				
			for(int i = 0; i < tmp.length; i++){
				if(!tmp[i].trim().equals("0") & tmp[i].trim().length() > 0){
					lichsu = lichsu + "'" + tmp[i].trim() + "',";
				}
			}
			
			lichsu = lichsu.substring(0, lichsu.length() - 1);
						
			query =	"SELECT (SELECT HDV.TONGTIEN*TG.TIGIAQUYDOI " +
				    "		 FROM ERP_HOPDONGVAY HDV " +
					"		 INNER JOIN ERP_TIGIA TG ON TG.PK_SEQ = HDV.TIENTE_FK " + 
					"		 WHERE TG.TUNGAY <= '" + this.ngay + "' AND TG.DENNGAY >= '" + this.ngay + "' AND HDV.PK_SEQ = " + this.soHD + " " +
					") " +
					"-		(SELECT ISNULL(SUM(NTV.SOTIEN*TG.TIGIAQUYDOI), 0) " + 
					"		 FROM ERP_NHANTIENVAY NTV " +
					" 		 INNER JOIN ERP_TIGIA TG ON TG.PK_SEQ = NTV.TIENTE_FK " +
					" 		 WHERE HOPDONG_FK IN (" + lichsu + " ) AND TG.TUNGAY <= '" + this.ngay + "' AND TG.DENNGAY >= '" + this.ngay + "' " ;
					
			if(this.Id.length() > 0){
			
				query = query +	" AND NTV.PK_SEQ <> " + this.Id + " ";

			}
					
			query +=") " +
					"-		(SELECT " + this.sotien + "*TIGIAQUYDOI " +
					" 		 FROM ERP_TIGIA " +
					" 		 WHERE TUNGAY <= '" + this.ngay + "' AND DENNGAY >= '" + this.ngay + "' AND TIENTE_FK = " + this.tienteId + " " +
					") " +
					" AS CONLAI, " +
					"		(SELECT HDV.TONGTIEN*TG.TIGIAQUYDOI " +
				    " 		 FROM ERP_HOPDONGVAY HDV " +
					" 		 INNER JOIN ERP_TIGIA TG ON TG.PK_SEQ = HDV.TIENTE_FK " + 
					" 		 WHERE TG.TUNGAY <= '" + this.ngay + "' AND TG.DENNGAY >= '" + this.ngay + "' AND HDV.PK_SEQ = " + this.soHD + " " +
					") " +
					"-		(SELECT ISNULL(SUM(NTV.SOTIEN*TG.TIGIAQUYDOI), 0) " + 
					" 		 FROM ERP_NHANTIENVAY NTV " +
					" 		 INNER JOIN ERP_TIGIA TG ON TG.PK_SEQ = NTV.TIENTE_FK " +
					" 		 WHERE HOPDONG_FK IN (" + lichsu + " ) AND TG.TUNGAY <= '" + this.ngay + "' AND TG.DENNGAY >= '" + this.ngay + "' "  ;

			if(this.Id.length() > 0){
				
				query = query +	" AND NTV.PK_SEQ <> " + this.Id + " ";

			}

			query +=" )AS HANMUC " ;
			
			System.out.println(query);
			rs = this.db.get(query);
			rs.next();
			double temp = rs.getDouble("CONLAI");
				
			if(temp >= 0){
				rs.close();
				return true;	
			}else{
				this.hanmuc = rs.getString("HANMUC");
				rs.close();
				return false;
			}
			
		}catch(java.sql.SQLException e){
			System.out.println(e.toString());
			return false;
		}
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
			if(this.sotkRs != null) this.sotkRs.close();
		}catch(Exception er){
			er.printStackTrace();
		}
		db.shutDown();
	}
	
	public ResultSet getSotkRs()
	{
		String query = 	"SELECT NH_CTY.SOTAIKHOAN, NH_CTY.SOTAIKHOAN + ' - ' + NH.TEN + ' - ' + CN.TEN + ', ' + TT.MA  AS TAIKHOAN " +
			"FROM ERP_NGANHANG_CONGTY NH_CTY " +
			"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = NH_CTY.TIENTE_FK " + 
			"INNER JOIN ERP_NGANHANG NH ON NH.PK_SEQ = NH_CTY.NGANHANG_FK " +
			"INNER JOIN ERP_CHINHANH CN ON CN.PK_SEQ = NH_CTY.CHINHANH_FK " +
			"WHERE NH_CTY.TRANGTHAI = 1 AND NH_CTY.CONGTY_FK = '" + this.ctyId + "' ";
		if(this.tienteId.length() > 0 & !this.tienteId.equals("0"))
		{
			query = query + " AND TT.PK_SEQ = " + this.tienteId + " ";
		}
		System.out.println("Query get rs tai khoan : \n" + query + "\n==========================================");
		this.sotkRs = db.get(query);				
		return 	this.sotkRs;
	}

	public void setSotkRs(ResultSet sotkRs) 
	{
		this.sotkRs = sotkRs;
	}
	
	public String getSotaikhoan() 
	{
		return this.sotaikhoan;
	}

	public void setSotaikhoan(String sotk)
	{
		this.sotaikhoan = sotk;
	}
	
	public String getTienteId() {
		
		return this.tienteId;
	}

	
	public void setTienteId(String ttId) {
		
		this.tienteId = ttId;
	}

	
	public ResultSet getTienteRs() {
		
		String query="select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE " ;
		if(this.soHD !=null && this.soHD.length() >0){
			query+=" where  pk_seq in (   SELECT TIENTE_FK    FROM ERP_HOPDONGVAY A WHERE PK_SEQ=  "+this.soHD+ 
				   " UNION ALL   "+
				   " SELECT    ISNULL(A.NGOAITE_FK,0) FROM ERP_HOPDONGVAY A WHERE PK_SEQ= "+this.soHD+")"; 
		}

		return this.tienteRs=db.get(query);
	}

	
	public void setTienteRs(ResultSet tienteRs) {
		
		this.tienteRs = tienteRs;
	}
	
	public String getTigia() 
	{
//		String query = "";
		
		/*if(this.tienteId.length() > 0 & !this.tienteId.equals("0") & this.ngay.length() > 0)
		{	
		 // neu ti gia lay ngoai da co sua
 
			    query = "SELECT TIGIAQUYDOI FROM ERP_TIGIA WHERE TUNGAY <= '" + this.ngay + "' AND DENNGAY >= '" + this.ngay + "' AND TIENTE_FK = " + this.tienteId + "";
				System.out.println(query);
				ResultSet rs = this.db.get(query);
				try{
					if(rs != null){
						rs.next();
						this.tigia = rs.getString("TIGIAQUYDOI");
						rs.close();
					}
				}catch(java.sql.SQLException e){
					System.out.println(e.toString());
				}
		 
		}*/
		
		return this.tigia;
	}
	public void setTigia(String tigia) 
	{
			this.tigia = tigia;
	}
	
	public String getSotienNT() 
	{
		return sotienNT;
	}
	public void setSotienNT(String sotienNT) 
	{
		this.sotienNT = sotienNT;
	}
	
	public String getSotienVND() 
	{
		return sotienVND;
	}
	
	public void setSotienVND(String sotienVND) 
	{
		this.sotienVND = sotienVND;
	}

	public String getStConlai() 
	{
		return this.stConlai;
	}

	public void setStConlai(String stConlai) 
	{		
		this.stConlai= stConlai;
	}
	
	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccid)
	{
		this.nccId = nccid;
	}


	public void createPhieuChi() 
	{
		String query = "";
/*			query = " SELECT TT.PK_SEQ, TT.NCC_FK, NCC.TEN AS TENNCC, ISNULL(TT.SOTIENHDNT,0) AS SOTIENHDNT, ISNULL(TT.SOTIENHD,0)AS SOTIENHD " +
					" ,TT.TIGIA, TT.TIENTE_FK " +
					" FROM ERP_THANHTOANHOADON TT " +
					" INNER JOIN ERP_NHACUNGCAP NCC ON TT.NCC_FK = NCC.PK_SEQ " +
					" WHERE TT.TRANGTHAI= '0' AND TT.THANHTOANTUTIENVAY = '1' "; //AND TT.NCC_FK IN (" + this.nccIds + ")"; */
		
		if(this.Id.length() > 0){
			
			query = "SELECT	NTV.PK_SEQ AS NTVID, NTV_TT.THANHTOANHOADON_FK AS TTID, NCC.PK_SEQ AS NCCID, NCC.TEN AS TENNCC, \n" +
					"ISNULL(NTV_TT.SOTIENNT, 0) AS SOTIENNT, ISNULL(NTV.TIGIA, 1) AS TIGIA, ISNULL(NTV_TT.SOTIENVND, 0) AS SOTIENVND, \n" +
					
					"ISNULL(NTV_TT.THANHTOANVND, 0) AS THANHTOAN, \n" +
				
					"ISNULL(NTV_TT.CONLAIVND, 0) AS CONLAI \n" + 
				
					"FROM ERP_NHANTIENVAY NTV \n" +
					"INNER JOIN ERP_NHANTIENVAY_THANHTOANHD NTV_TT ON NTV_TT.NHANTIENVAY_FK = NTV.PK_SEQ \n" +
					"INNER JOIN ERP_THANHTOANHOADON TTHD ON TTHD.PK_SEQ = NTV_TT.THANHTOANHOADON_FK \n" +
					"INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTHD.NCC_FK \n" +
					"WHERE NTV.PK_SEQ = " + this.Id + " \n" + 
					"UNION ALL \n" ;
		}else{
			this.stConlai= this.sotienVND;
		}
				
		query+=	"SELECT	0 AS NTVID, TTHD.PK_SEQ AS TTID, NCC.PK_SEQ AS NCCID, NCC.TEN AS TENNCC, \n" +
				"		CASE WHEN TTHD.TIENTE_FK <> 100000 THEN \n" +
				"			TTHD.SOTIENHDNT - ISNULL(NTV.THANHTOANNT,0) \n" +
				"		ELSE \n" +
				"			0 \n" +
				"		END AS SOTIENNT , \n" +

				"		ISNULL(TTHD.TIGIA, 1) AS TIGIA, \n" +
				"		CASE WHEN TTHD.TIENTE_FK <> 100000 THEN \n" + 
				"			TTHD.SOTIENHDNT*TTHD.TIGIA  - ISNULL(NTV.THANHTOANVND,0)  \n" +
				"		ELSE \n" +
				"			TTHD.SOTIENHD  - ISNULL(NTV.THANHTOANVND,0)  \n" +
				"		END AS SOTIENVND, \n" +
				
				"		0 AS THANHTOAN, \n" +

				"		CASE WHEN TTHD.TIENTE_FK <> 100000 THEN \n" + 
				"			TTHD.SOTIENHDNT*TTHD.TIGIA  - ISNULL(NTV.THANHTOANVND,0) \n" +
				"		ELSE \n" +
				"			TTHD.SOTIENHD  - ISNULL(NTV.THANHTOANVND,0) \n" +
				"		END AS CONLAI \n" +
				"FROM ERP_THANHTOANHOADON TTHD \n" +
				"     INNER JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TTHD.NCC_FK \n" +
				"	  LEFT JOIN \n"+
				"	  ( \n" +
				" 		SELECT a.THANHTOANHOADON_FK, SUM(a.THANHTOANNT) as THANHTOANNT, SUM(a.THANHTOANVND) as THANHTOANVND \n"+
				"	   FROM ERP_NHANTIENVAY_THANHTOANHD a inner join ERP_NHANTIENVAY b on a.NHANTIENVAY_FK = b.PK_SEQ \n"+
				"	   WHERE b.TRANGTHAI  <>  2 \n"+
				"	   GROUP BY a.THANHTOANHOADON_FK \n"+
				"	  ) NTV ON TTHD.PK_SEQ = NTV.THANHTOANHOADON_FK  \n"+
				"WHERE TTHD.THANHTOANTUTIENVAY = 1 AND TTHD.TRANGTHAI = 0 AND ISNULL(TTHD.ISKTTDUYET,0)='1' AND \n" +
				"      (CASE WHEN TTHD.TIENTE_FK <> 100000 THEN TTHD.SOTIENHDNT*TTHD.TIGIA  - ISNULL(NTV.THANHTOANVND,0) 	\n" +
				"           ELSE TTHD.SOTIENHD  - ISNULL(NTV.THANHTOANVND,0)  END)  > 0 " +
				"  AND TTHD.CONGTY_FK ="+this.ctyId+" \n" ;
		
		
		if(!this.tienteId.equals("100000") && !this.tienteId.equals("0")){
			query = query + " AND TTHD.TIENTE_FK = " + this.tienteId + " ";			
		}
		
		if(this.Id.length() > 0){
			query+=	" AND TTHD.PK_SEQ NOT IN (SELECT THANHTOANHOADON_FK FROM ERP_NHANTIENVAY_THANHTOANHD WHERE NHANTIENVAY_FK =" + this.Id + ") \n" ;
		}
		
		System.out.println("Cau lay phieu chi : \n"+query + "\n==================================");
		this.UNCRS  = db.get(query);
	}

	public void setDinhKhoanRs(ResultSet dinhKhoanRs) {
		this.dinhKhoanRs = dinhKhoanRs;
	}

	public ResultSet getDinhKhoanRs() {
		return dinhKhoanRs;
	}
	public String getSotienvay() {
		return sotienvay;
	}
	public void setSotienvay(String sotienvay) {
		this.sotienvay = sotienvay;
	}
	public String getNppId() {
		return nppId;
	}
	public void setNppId(String nppId) {
		this.nppId = nppId;
	}
	public String[] getConlaiHD() {
		return conlaiHD;
	}
	public void setConlaiHD(String[] conlaiHD) {
		this.conlaiHD = conlaiHD;
	}
}
