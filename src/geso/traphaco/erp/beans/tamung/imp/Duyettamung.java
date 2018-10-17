package geso.traphaco.erp.beans.tamung.imp;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.tamung.IDuyettamung;

import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class Duyettamung implements IDuyettamung {
	
	String congtyId;
	String userId;
	String ctyId;
	String Id;
	String dvthId;
	ResultSet dvth;
	
	String lspId;
	String ngaytao;
	String maDNTU;
	String nccId;
	String lydoxoa;
	String lydomoduyet;
	String lydosua;
	ResultSet nccList;
	ResultSet lsp;
	ResultSet nguoitaoRs;
	ResultSet polist;
	String nguoitaoIds;
	
	String tuNgay, denNgay, trangThai;
	
	HttpServletRequest request;	
	String msg;
	dbutils db;
    Utility util;

	public Duyettamung(){
		this.userId = "";
		//this.ctyId = "100001";
		this.ctyId="";
		this.dvthId = "";
		this.lspId = "";
		this.ngaytao="";
		this.maDNTU = "";
		this.nccId ="";
		this.msg = "";
		this.lydoxoa = "";
		this.lydomoduyet = "";
		this.lydosua = "";
		this.nguoitaoIds = "";
		this.maDNTU = "";
		this.Id = "";
		tuNgay = "";
		denNgay = "";
		trangThai = "";
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
	
	public void setMaDNTU(String maDNTU) 
	{
		this.maDNTU = maDNTU;
	}
	
	public String getMaDNTU() 
	{
		return maDNTU;
	}
	
	public String getUserId(){
		return this.userId;
	}
	
	public void setUserId(String userId){
		this.userId = userId;
	}

	public String getId(){
		return this.Id;
	}
	
	public void setId(String Id){
		this.Id = Id;
	}

	public String getCtyId(){
		return this.ctyId;
	}
	
	public void setCtyId(String ctyId){
		this.ctyId = ctyId;
	}
	
	public String getNgaytao(){
		return this.ngaytao;
	}
	
	public void setNgaytao(String ngaytao){
		this.ngaytao = ngaytao;
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
	@Override
	public String getTuNgay() {
		return tuNgay;
	}

	@Override
	public void setTuNgay(String tuNgay) {
		this.tuNgay = tuNgay;
	}

	@Override
	public String getDenNgay() {
		return denNgay;
	}

	@Override
	public void setDenNgay(String denNgay) {
		this.denNgay = denNgay;
	}

	@Override
	public String getTrangThai() {
		return trangThai;
	}

	@Override
	public void setTrangThai(String trangThai) {
		this.trangThai = trangThai;
	}

	public void init()
	{
		String query = "";
				
		query =  "	SELECT 	distinct NV.TEN as NGUOITAO, TAMUNG.PK_SEQ AS MHID, TAMUNG.NGAYTAMUNG AS NGAY,  \n"+
				 "			CASE WHEN TAMUNG.NCC_FK IS NOT NULL THEN NCC.TEN WHEN TAMUNG.NHANVIEN_FK IS NOT NULL THEN NV1.TEN END AS NCC, \n"+
				 "			TAMUNG.SOTIENTAMUNG TONGTIENAVAT, TAMUNG.TRANGTHAI, TAMUNG.PK_SEQ SOCHUNGTU, " +
				 "	ISNULL(TAMUNG.ISTP,0) AS ISTP,  \n"+
				 
				 "  ISNULL(TAMUNG.ISKTV, 0) ISKTV, ISNULL(TAMUNG.ISKTT, 0) ISKTT, ISNULL(TAMUNG.ISDACHOT,0) ISDACHOT \n"+
				 "	FROM ERP_TAMUNG TAMUNG \n"+
				 
				 "	INNER JOIN NHANVIEN NV ON NV.PK_SEQ = TAMUNG.NGUOITAO  \n " +
				 "	LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TAMUNG.NCC_FK   \n " +
				 "	LEFT JOIN ERP_NHANVIEN NV1 ON NV1.PK_SEQ = TAMUNG.NHANVIEN_FK    \n " +
				 "	WHERE TAMUNG.TRANGTHAI IN ( 0 , 1 ) AND isnull(TAMUNG.ISDACHOT, 0) = '1' \n " +
				 "  AND TAMUNG.CONGTY_FK = '"+ this.congtyId + "' \n"+
				 "  AND ISNULL( TAMUNG.ISHOANTAT, 0 ) = 0  \n" +

				 //Nhân viên là trưởng phòng nào lấy ra những đề nghị tạm ứng chưa duyệt của phòng đó
				 "  AND  " +
				 " (  " +
				 "		(" + this.userId + " in  ( \n " +
				 "									SELECT NHANVIEN_FK FROM ERP_CHUCDANH \n " +
				 "								  	WHERE DVTH_FK = (SELECT PHONGBAN_FK FROM NHANVIEN WHERE PK_SEQ = NV.PK_SEQ) AND isTP = 1  \n " +
				 " 									UNION ALL \n " +
				 "									SELECT NHANVIEN_FK FROM ERP_CHUCDANH \n " +
				 "								  	WHERE DVTH_FK = (SELECT DVTH_FK FROM ERP_NHANVIEN WHERE PK_SEQ = NV1.PK_SEQ) AND isTP = 1 AND TAMUNG.NHANVIEN_FK IS NOT NULL \n " +

				 "								 )     \n " +
				 "		) " +

				 "    	OR " + 
	
				 //Kế toán duyệt
				 " ( 	" + this.userId + " in (select nhanVien_FK from erp_chucDanh where trangThai = 1 and (isktv = 1 or isktt = 1)) " +  // phải là KTV hay KTT
				 "		AND (TAMUNG.KETOANVIEN_FK IS NULL OR TAMUNG.KETOANVIEN_FK = " + this.userId + " ) " +	// hoặc kê toán chưa duyệt, hoặc nếu đã duyệt thì chỉ hiện những ĐNTU đã được userId duyệt
				 "		AND ((ISNULL(TAMUNG.ISTP, 0) = 1 AND TAMUNG.NCC_FK IS NULL) OR TAMUNG.NCC_FK IS NOT NULL))" + // nếu ĐNTU cho NV thì phải có trưởng phòng duyệt trước

		 		"	)  \n " ;

		if(this.dvthId.length() > 0){
			query += " AND NV1.DVTH_FK = " + this.dvthId + " ";
		}
		
		if(this.nccId.length() > 0){
			query += " AND TAMUNG.NCC_FK = " + this.nccId + " ";
		}

		/*if(this.ngaytao.length() > 0){
			query += " AND TAMUNG.NGAYTAO = " + this.ngaytao + " ";
		}*/

		if(this.nguoitaoIds.length() > 0){
			query += " AND TAMUNG.NGUOITAO = '" + this.nguoitaoIds + "' ";
		}


		if(this.maDNTU.length() > 0){
			query += " AND TAMUNG.PK_SEQ = '" + this.maDNTU + "' ";
		}
		
		if(this.tuNgay.length() > 0){
			query += " AND TAMUNG.NGAYTAMUNG >= '" + this.tuNgay + "' ";
		}
		
		if(this.denNgay.length() > 0){
			query += " AND TAMUNG.NGAYTAMUNG <= '" + this.denNgay + "' ";
		}
		
		if(this.trangThai.length() > 0){
			if (this.trangThai.equals("isdachot"))
				query += " AND TAMUNG.ISDACHOT = 1 AND ISNULL(ISTP, 0) = 0 AND ISNULL(ISKTV, 0) = 0 AND ISNULL(ISKTT, 0) = 0 AND ISNULL(TAMUNG.TRANGTHAI, 0) = 0";
			else if (this.trangThai.equals("istp"))
				query += " AND TAMUNG.ISDACHOT = 1 AND ISNULL(ISTP, 0) = 1 AND ISNULL(ISKTV, 0) = 0 AND ISNULL(ISKTT, 0) = 0 AND ISNULL(TAMUNG.TRANGTHAI, 0) = 0";
			else if (this.trangThai.equals("isktv"))
				query += " AND TAMUNG.ISDACHOT = 1 AND ISNULL(ISKTV, 0) = 1 AND ISNULL(ISKTT, 0) = 0 AND ISNULL(TAMUNG.TRANGTHAI, 0) = 0";
			else if (this.trangThai.equals("isktt"))
				query += " AND TAMUNG.ISDACHOT = 1 AND ISNULL(ISKTT, 0) = 1 AND ISNULL(TAMUNG.TRANGTHAI, 0) = 1";
			else if (this.trangThai.equals("1"))
				query += " AND TAMUNG.TRANGTHAI = 1 ";
			else if (this.trangThai.equals("2"))
				query += " AND TAMUNG.TRANGTHAI = 2 ";
		}

		query += "ORDER BY MHID DESC , NGAY ASC\n";

		System.out.println(" 1. init duyet :\n" + query + "\n-----------------------------------------------------");
		
		this.polist = this.db.get(query);
		query=" SELECT PK_SEQ AS DVTHID, TEN AS DVTH FROM ERP_DONVITHUCHIEN WHERE TRANGTHAI = '1' " ;
		this.dvth = this.db.get(query) ;
		this.lsp = this.db.get("SELECT PK_SEQ AS LSPID, TEN AS LSP FROM ERP_LOAISANPHAM WHERE TRANGTHAI = '1' ");
		this.nccList = this.db.get("SELECT PK_SEQ, MA + '-' + TEN AS TENNCC FROM ERP_NHACUNGCAP WHERE TRANGTHAI = '1'  and duyet = '1'");
		
		query = "select pk_seq, ten from NHANVIEN where trangthai = '1' and pk_seq in ( select distinct NGUOITAO from ERP_TAMUNG ) ";
		System.out.println("TEN NV: "+query);
		this.nguoitaoRs = db.get(query);
	}
	
	private String getDateTime() 
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public boolean Duyettamung(String Id, String capduyet){
		try
		{
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
			String query_CHECK = " select ISNULL(ISKTT,0) AS ISKTT,ISNULL(ISKTV,0) AS ISKTV, TRANGTHAI from ERP_TAMUNG WHERE PK_SEQ = " +Id;
			String daduyetKTT = "";
			String daduyetKTV = "";
			String tt = "";
			ResultSet rscheck = db.get(query_CHECK);
			while(rscheck.next())
			{
				daduyetKTT = rscheck.getString("ISKTT");
				daduyetKTV = rscheck.getString("ISKTV");
				tt = rscheck.getString("TRANGTHAI");
			}
			rscheck.close();
			if(daduyetKTV.equals("1") ||daduyetKTT.equals("1") || tt.equals("1"))
			{
				this.msg="Phiếu đã duyệt  ";
				db.getConnection().rollback();
				return false;
			}			
			String query = "";
			if(capduyet.equals("duyetTP")) // TRUONG PHONG DUYET
			{
				query = " SELECT NHANVIEN_FK, SUM(ISNULL(istp,0)) isTP \n"+
						" FROM ERP_CHUCDANH WHERE DVTH_FK IN (SELECT PHONGBAN_FK FROM NHANVIEN WHERE PK_SEQ = ( SELECT NGUOITAO FROM ERP_TAMUNG WHERE PK_SEQ = "+Id+")) \n"+
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
					this.msg = "DMH1.1 Bạn không được cấp quyền trưởng phòng để duyệt phiếu này. Vui lòng xem lại dữ liệu nền chức danh";
					db.getConnection().rollback();
					return false;
				}
				else
				{
					query = " UPDATE ERP_TAMUNG SET ISTP = 1, TRUONGPHONG_FK = "+this.userId +" WHERE PK_SEQ = " +Id;
					
					if(!db.update(query))
					{
						this.msg = "DMH1.2 Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! ";
						db.getConnection().rollback();
						return false;
					}
				}				
			}			
			
			if(capduyet.equals("duyetKTV"))
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
					this.msg = "DMH1.3 Bạn không được cấp quyền kế toán gán mã chi phí. Vui lòng xem lại dữ liệu nền chức danh";
					db.getConnection().rollback();
					return false;
				}
				else
				{
					query = " UPDATE ERP_TAMUNG SET ISKTV = 1, KETOANVIEN_FK = "+this.userId +" WHERE PK_SEQ = " +Id+" and trangthai=0";
					
					if(db.updateReturnInt(query)!=1)
					{
						this.msg = "DMH1.4 Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! ";
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
					
					//Kế toán viên duyệt luôn phần kế toán trưởng
					boolean result = duyetKTT(Id, false);
					if(result == false)
					{
						this.msg = "DMH1.5 Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! " + this.msg;
						System.out.println(this.msg);
						db.getConnection().rollback();
						return false;
					}
				}				
			}
			
			if(capduyet.equals("duyetKTT"))
			{
				boolean result = duyetKTT(Id, true);
				if(result == false)
				{
					this.msg = "DMH1.6 Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! ";
					System.out.println(this.msg);
					db.getConnection().rollback();
					return false;
				}
			}
			
			db.getConnection().commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
			return false;
		}
		
		return true;
	}
	
	private boolean duyetKTT(String Id, boolean isKiemTraKTT) {
		try {
			
			int ISKTT = 1;
			if (isKiemTraKTT == true)
				ISKTT = kiemTraDuyetKTT(Id);
			
			if(ISKTT == 0)
			{
				this.msg = "DMH1.7 Bạn không được cấp quyền kế toán trưởng. Vui lòng xem lại dữ liệu nền chức danh";
				return false;
			}
			else
			{
				String query = " UPDATE ERP_TAMUNG SET ISKTT = 1, KETOANVIEN_FK = "+this.userId +", TRANGTHAI = 1 WHERE PK_SEQ = " +Id+" and trangthai=0";
				
				if(db.updateReturnInt(query)!=1)
				{
					this.msg = "DMH1.8 Không thể duyệt được phiếu tạm ứng. Vui lòng liên hệ GESO! ";
					return false;
				}
				
				//TỰ TẠO PHIẾU CHI || ỦY NHIỆM CHI
				query = " SELECT TU.NGAYTAMUNG, TU.NCC_FK, TU.NHANVIEN_FK, TU.SOTIENTAMUNG, TU.LYDOTAMUNG, TU.TIENTE_FK, TU.HTTT_FK HTTTID, TU.NGUOITAO,"
						+ " ISNULL(TU.SOTIENTAMUNGNT,0) AS SOTIENTAMUNGNT,ISNULL(TIGIA,1) AS TIGIA, TU.LYDOTAMUNG AS LYDO \n" +
						" , isNull(TU.lyDoTamUng, '') as dienGiai\n" +
						" FROM ERP_TAMUNG TU " +
						" LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = TU.NCC_FK "+
						" LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = TU.NHANVIEN_FK "+
						" LEFT JOIN NHANVIEN N ON N.PK_SEQ = TU.NGUOITAO "+
						" WHERE TU.PK_SEQ = '"+Id+"' AND TU.CONGTY_FK = "+this.congtyId;
				System.out.println("[SELECT ERP_TAMUNG] ---- "+ query);
				ResultSet RsTamUng = db.get(query);
				
				String ncc_fk = "";
				String nhanvienfk = "";
				double sotientamung = 0;
				String htttId = "";
				String tiente_fk = "";
				String nguoitao = "";
				double soTienTamUngNT = 0;
				double tiGia = 0;
				String lydo = "";
				String ngaymua = "";
				String dienGiai = "";
				
				while (RsTamUng.next())
				{
					dienGiai = RsTamUng.getString("dienGiai");
					ncc_fk = RsTamUng.getString("NCC_FK");
					nhanvienfk =  RsTamUng.getString("NHANVIEN_FK");
					sotientamung = RsTamUng.getDouble("SOTIENTAMUNG");
					htttId = RsTamUng.getString("HTTTID");
					tiente_fk = RsTamUng.getString("tiente_fk");
					nguoitao = RsTamUng.getString("nguoitao");
					soTienTamUngNT = RsTamUng.getDouble("SOTIENTAMUNGNT");
					tiGia = RsTamUng.getDouble("TIGIA");
					lydo =  RsTamUng.getString("LYDO");
					ngaymua = RsTamUng.getString("NGAYTAMUNG");
				}
				RsTamUng.close();
				 
				 
				if(soTienTamUngNT == 0 && tiente_fk.equals("100000") ){
					soTienTamUngNT = sotientamung;
				}
				double SoTienHD_VND = 0;
				double SoTienHD_NT = 0;
				SoTienHD_VND  =  sotientamung;
				SoTienHD_NT = soTienTamUngNT;
	
				
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
				
//				String soChungTu = Utility.getSoChungTuMax(db, "ERP_ThanhToanHoaDon", htttId);
				
				query = "Insert ERP_THANHTOANHOADON " +
						"( DVTH_FK, NGAYGHINHAN, NCC_FK ,NHANVIEN_FK, HTTT_FK, NGANHANG_FK, CHINHANH_FK, SOTAIKHOAN, NOIDUNGTT, " +
						"  SOTIENTT, SOTIENTTNT, SOTIENHD, SOTIENHDNT, PHINGANHANG, PHINGANHANGNT, VAT, VATNT, CHENHLECHVND, " +
						"  TRICHPHI, SOTAIKHOAN_TP, NGANHANG_TP_FK, CHINHANH_TP_FK, " +
						"  NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAITHANHTOAN, TIENTE_FK, TIGIA , THANHTOANTUTIENVAY, KHACHHANG_FK, " +
						" CTKEMTHEO, CONGTY_FK, PREFIX \n" +
						"  ,  soChungTu,soChungTu_Chu, soChungTu_So, DNTU_FK\n" +
						") " +
						"values(NULL, '" + ngaymua + "', " + ncc_fk + "," + " "+nhanvienfk +", '" + htttId + "', " +
						" NULL, NULL , NULL , N'" + dienGiai + "', " +
						"" + sotientamung + ", '"+ soTienTamUngNT + "', " + SoTienHD_VND  + ", " + SoTienHD_NT + " , " +
						" 0 , 0 , 0 ,0, 0, 0, '', null , null , '"  + getDateTime() + "', '" + nguoitao + "', '" + getDateTime() + "', '" 
						+ nguoitao + "',0, " + tiente_fk + ", "+tiGia+" , '0', null , N'', "+this.congtyId+", '"+prefix+ "'\n" +
						", '" + (soChungTu_Chu + soChungTu_So) + "','"+soChungTu_Chu+"', '" + soChungTu_So + "', " + Id + ")";
				
				if(!db.update(query))
				{
					this.msg = "DMH1.9 Khong the tao moi Phiếu Chi/UNC: " + query;
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
				
				// cập nhật mã chứng từ
				query = " update ERP_THANHTOANHOADON set machungtu =  Prefix + SUBSTRING(NGAYGHINHAN, 6, 2) + SUBSTRING(NGAYGHINHAN, 0, 5) + '-' + dbo.LaySoChungTu( " + tthdCurrent + " ) " + 
						" where pk_seq = '" + tthdCurrent + "' ";
				
				System.out.println("[ERP_THANHTOANHOADON] error message:" + query);
				
				if(!db.update(query))
				{
					this.msg = "DMH1.10 Khong the tao moi ERP_THUTIEN: " + query;
					return false;
				}
				
				//TRONG BẢNG ERP_THANHTOANHOADON_HOADON LOAIHD = 1 LÀ ĐỂ NGHỊ TẠM ỨNG
				
				query = "Insert ERP_THANHTOANHOADON_HOADON(THANHTOANHD_FK, HOADON_FK, SOTIENTT, SOTIENAVAT, SOTIENNT, CONLAI, LOAIHD, SOHOADON, TIGIA, TIENTE_FK) " +
						"values('" + tthdCurrent + "', '" + Id + "', '" + (tiente_fk.equals("100000")?sotientamung:soTienTamUngNT) + "', '" + sotientamung + "','" +
						soTienTamUngNT+"', 0 , '1', '"+ Id +"', " + tiGia + ", '"+tiente_fk+"')";				
				
				if(!db.update(query))
				{
					this.msg = "DMH1.11 Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
					return false;
				}
					
				query = "INSERT INTO ERP_THANHTOANHOADON_PHINGANHANG(THANHTOANHD_FK, MAHOADON, MAUHOADON, KYHIEU, SOHOADON, " +
						"NGAYHOADON, TENNCC, MST, TIENHANG, THUESUAT, TIENTHUE, NGANHANG_FK, CHINHANH_FK) " +
						"VALUES(" + tthdCurrent + ", N'', N'', N'', ''," +
						"'" + getDateTime() + "', N'', '', 0, 0, 0, " +
						"null, null)";
		
				if(!db.update(query))
				{
					this.msg = "DMH1.12 Khong the tao moi ERP_THANHTOANHOADON_HOADON: " + query;
					return false;
				}
			}				
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		
		return true;
	}

	private int kiemTraDuyetKTT(String id) {
		String query = " SELECT NHANVIEN_FK, SUM(ISNULL(ISKTT,0)) ISKTT \n"+
		" FROM ERP_CHUCDANH \n " +
		" WHERE NHANVIEN_FK = "+this.userId+" AND TRANGTHAI = 1 \n"+
		" GROUP BY NHANVIEN_FK \n";
		int ISKTT = 0;
		
		System.out.println(query);
		try {
			ResultSet Rs = db.get(query);
			
			if(Rs!=null)
			{
				while(Rs.next())
				{
					ISKTT = Rs.getInt("ISKTT");
				}
				Rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return ISKTT;
	}

	public String getDaduyet(String mhId){
		String tmp = "";
		/*String query =  "SELECT DUYETMUAHANG.TAMUNG_FK AS MHID, NV.PK_SEQ AS NVID, NV.DANGNHAP AS NVTEN " +
						"FROM ERP_DUYETTAMUNG DUYETMUAHANG " +
						"INNER JOIN ERP_CHUCDANH CHUCDANH ON CHUCDANH.PK_SEQ = DUYETMUAHANG.CHUCDANH_FK " +
						"INNER JOIN NHANVIEN NV ON NV.PK_SEQ = CHUCDANH.NHANVIEN_FK " +
						"WHERE DUYETMUAHANG.TRANGTHAI = '1' AND TAMUNG_FK = " + mhId + "  ";
		ResultSet rs = this.db.get(query);*/
		return tmp;
	}
	public void DBclose(){
		try{
			if(this.dvth != null) this.dvth.close();
			if(this.lsp != null) this.lsp.close();
			if(this.polist != null) this.lsp.close();
			this.db.shutDown();
		}catch(java.sql.SQLException e){
			e.printStackTrace();
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
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
						
			//1. KIỂM TRA CẤP DUYỆT CỦA USER ĐĂNG NHẬP
			
			String nhanvien_fk = this.userId;
			
			String query =  " SELECT distinct A.LOAICAP_FK, A.NHANVIEN_FK \n" +
							" FROM ( \n"+
					
							" 		SELECT A.LOAICAP_FK, A.NHANVIEN_FK  \n" +
							" 		FROM ERP_CAPQUANLY_CT A INNER JOIN ERP_CAPQUANLY B ON A.CAPQUANLY_FK = B.PK_SEQ \n" +
							" 		WHERE B.TRANGTHAI = 1 AND A.NHANVIEN_FK = " + nhanvien_fk +
							
							" 		UNION ALL \n"+
							
							" 		SELECT A.LOAICAP_FK, NVQL_FK NHANVIEN_FK \n" +
							" 		FROM ERP_CAPQUANLY A WHERE A.TRANGTHAI = 1 AND A.NVQL_FK = "+nhanvien_fk +
							
							") A  \n";
			
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
			query = " UPDATE ERP_DUYETTAMUNG SET TRANGTHAI = 0 , lydomoduyet = N'"+this.lydomoduyet+"' WHERE NHANVIEN_FK = "+nhanvien_fk+" AND LOAICAP_FK = "+loaicap_fk+" AND TAMUNG_FK = "+Id;
			
			System.out.println(query);
			
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUYETTAMUNG: " + query;
				db.getConnection().rollback();
				return false;
			}
						
			
			// UPDATE QUYỀN VÀO BẢNG ERP_MUAHANG
			
			if(loaicap_fk.equals("10000")) // CẤP QUẢN LÝ TRỰC TIẾP
			{
				query = " UPDATE ERP_TAMUNG SET ISQLTT = 0 WHERE PK_SEQ = "+Id;	
				
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaicap_fk.equals("10001")) // Quản lý CS
			{
				query = " UPDATE ERP_TAMUNG SET ISCS = 0 WHERE PK_SEQ = "+Id;	
				
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}		
			else if(loaicap_fk.equals("10002")) // Duyệt ĐNTT/ĐNTU
			{
				query = " UPDATE ERP_TAMUNG SET ISDUYETCHI = 0 WHERE PK_SEQ = "+Id;	
				
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaicap_fk.equals("10003")) // Kế toán tổng hợp
			{
				
				query = " UPDATE ERP_TAMUNG SET ISKTTH = 0 WHERE PK_SEQ = "+Id;	
				
				System.out.println(query);
				if (!db.update(query))
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
			}
			else if(loaicap_fk.equals("10004")) // KTT
			{
				query = " UPDATE ERP_TAMUNG SET ISKTT = 0, TRANGTHAI = 0, isDaChi = 0 WHERE PK_SEQ = "+Id+" and trangthai=1";	
				
				System.out.println(query);
				if (db.updateReturnInt(query)!=1)
				{
					this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
					db.getConnection().rollback();
					return false;
				}
				
				// KIẾM TRA XEM PHIẾU NÀY ĐÃ RA PHIẾU CHI CHƯA
				
				query =  " SELECT count(A.HOADON_FK) dem FROM ERP_THANHTOANHOADON_HOADON A INNER JOIN ERP_THANHTOANHOADON B ON A.THANHTOANHD_FK = B.PK_SEQ" +
						 " WHERE A.LOAIHD = 1 AND B.TRANGTHAI != 2 AND A.HOADON_FK = "+Id+"  ";
				
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
					query = " UPDATE ERP_TAMUNG SET ISGANMACP = 0 , TRANGTHAI = 0, isDaChi = 0 WHERE PK_SEQ = "+Id;	
					
					System.out.println(query);
					if (!db.update(query))
					{
						this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				else
				{
					this.msg = "Tạm ứng đã ra phiếu chi rồi. Bạn không được phép bỏ duyệt.";
					db.getConnection().rollback();
					return false;
				}
				
				
				
			}
								
			db.getConnection().commit();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
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
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
			
			String query = " INSERT ERP_DUYETTAMUNG ( TAMUNG_FK , NHANVIEN_FK, LYDOXOA ) SELECT "+Id+", "+this.userId+" , N'"+this.lydoxoa+"' ";
						
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// UPDATE QUYỀN VÀO BẢNG ERP_MUAHANG
			
			query = " UPDATE ERP_TAMUNG SET TRANGTHAI = 2 WHERE PK_SEQ = "+Id+" and trangthai=0";	
			
			System.out.println(query);
			
			if (db.updateReturnInt(query)!=1)
			{
				this.msg = "Khong the cap nhat ERP_MUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
												
			db.getConnection().commit();
			
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
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

	public ResultSet getNguoitaoRs() {
		
		return this.nguoitaoRs;
	}

	
	public void setNguoitaoRs(ResultSet nguoitaoRs) {
		
		this.nguoitaoRs = nguoitaoRs;
	}

	public void setNguoitaoIds(String nguoitaoIds) {
		
		this.nguoitaoIds = nguoitaoIds;
	}

	
	public String getNguoitaoIds() {
		
		return this.nguoitaoIds;
	}

	public boolean boChot(String id)
	{
		try {
			this.db.getConnection().setAutoCommit(false);
			
			String query = "update ERP_TAMUNG SET ISDACHOT = 0 where pk_seq = " + id;
			if (!this.db.update(query))
			{
				this.msg = "Lỗi khi bỏ chốt";
				this.db.getConnection().rollback();
				return false;
			}
			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(false);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean Suamuahang(String Id) {
		
		try
		{
			db = new dbutils();
						
			db.getConnection().setAutoCommit(false);
												
			String query = " INSERT ERP_DUYETTAMUNG ( TAMUNG_FK , NHANVIEN_FK, LYDOSUA ) SELECT "+Id+", "+this.userId+" , N'"+this.lydosua+"' ";
						
			if (!db.update(query))
			{
				this.msg = "Khong the cap nhat ERP_DUYETMUAHANG: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			db.getConnection().commit();
		}
		catch (Exception e) 
		{
			e.printStackTrace();
			db.update("rollback");
			db.shutDown();
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
						" FROM ERP_CHUCDANH WHERE DVTH_FK IN (SELECT PHONGBAN_FK FROM NHANVIEN WHERE PK_SEQ = ( SELECT NGUOITAO FROM ERP_TAMUNG WHERE PK_SEQ = "+id+")) \n"+
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
					query = " UPDATE ERP_TAMUNG SET  ISDACHOT=0, TRUONGPHONGMOCHOT_FK = "+this.userId +",NGAYMOCHOTNHANVIEN=getdate() WHERE PK_SEQ = " +id;
					
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
					query = " UPDATE ERP_TAMUNG SET ISTP = 0, KETOANMOCHOT_FK = "+this.userId +",NGAYMOCHOTTRUONGPHONG=GETDATE() WHERE PK_SEQ = " +id;
					
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
}