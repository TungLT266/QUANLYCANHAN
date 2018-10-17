package geso.traphaco.erp.beans.chiphikhac.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.chiphikhac.IErpChiphikhac;

public class ErpChiphikhac implements IErpChiphikhac
{
	String userId;
	String congtyId;
	String id;
	
	String ngaynhap;
	String diengiai;
	String loai;
	String doituong;
	
//	ResultSet nccRs;
	String nccId;
	String nccTen;
	
//	ResultSet nvRs;
	String nvId;
	String nvTen;
	String tienteId;
	String nppdangnhap;
	ResultSet tienteRs;
	
	String[] chiphi;
	String[] diengiaicp;
	String[] tongtien;
	String[] tongthue;
	
	String[] maHD;
	String[] mausoHD;
	String[] kyhieu;
	String[] sohd;
	String[] ngayhd;
	String[] mst;
	String[] tenncc;
	String[] tienhang;
	String[] thuesuat;
	String[] thue;
	String[] cong;
	String[] ghichu;
	
	String msg;
	
	dbutils db;
	
	public ErpChiphikhac()
	{
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.ngaynhap = "";
		this.loai = "0";
		this.nccId = "";
		this.nccTen = "";
		this.nvId = "";
		this.nvTen = "";
		this.tienteId = "100000";
		this.msg = "";
		this.nppdangnhap = "";
		this.db = new dbutils();
	}
	
	public ErpChiphikhac(String id)
	{
		this.userId = "";
		this.id = id;
		this.diengiai = "";
		this.ngaynhap = "";
		this.loai = "0";
		this.nccId = "";
		this.nccTen = "";
		this.nvId = "";
		this.nvTen = "";
		
		this.msg = "";
		this.nppdangnhap = "";
		this.db = new dbutils();
	}
	
	
	public String getId() 
	{
		return this.id;
	}

	public void setId(String Id) 
	{
		this.id = Id;
	}
	
	public String getUserId() 
	{
		return this.userId;
	}

	public void setUserId(String userId) 
	{
		this.userId = userId;	
	}

	public String getMsg() 
	{
		return this.msg;
	}

	public void setMsg(String msg) 
	{
		this.msg = msg;
	}

	public String getLoai() 
	{
		return this.loai;
	}

	public void setLoai(String loai) 
	{
		this.loai = loai;
	}

	public String getNccId() 
	{
		return this.nccId;
	}

	public void setNccId(String nccId) 
	{
		this.nccId = nccId;
	}

	public String getNccTen() 
	{
		return this.nccTen;
	}

	public void setNccTen(String nccTen) 
	{
		this.nccTen = nccTen;
	}

	public String getNvId() 
	{
		return this.nvId;
	}

	public void setNvId(String nvId) 
	{
		this.nvId = nvId;
	}

	public String getNvTen() 
	{
		return this.nvTen;
	}

	public void setNvTen(String nvTen) 
	{
		this.nvTen = nvTen;
	}

	public String getCongtyId() 
	{
		return this.congtyId;
	}

	public void setCongtyId(String congtyId) 
	{
		this.congtyId = congtyId;
	}

	
	public String getDiengiai() {
		
		return this.diengiai;
	}

	
	public void setDiengiai(String diengiai) {
		
		this.diengiai = diengiai;
	}

	
	public String getNgaynhap() {
		
		return this.ngaynhap;
	}

	
	public void setNgaynhap(String tungay) {
		
		this.ngaynhap = tungay;
	}

	
	public String[] getChiphi() 
	{
		return this.chiphi;
	}

	public void setChiphi(String[] chiphi) 
	{
		this.chiphi = chiphi;
	}

	public String[] getDiengiaicp() 
	{
		return this.diengiaicp;
	}

	public void setDiengiaicp(String[] diengiaicp) 
	{
		this.diengiaicp = diengiaicp;
	}

	public String[] getTongtien() {
		
		return this.tongtien;
	}

	
	public void setTongtien(String[] tongtien) {
		
		this.tongtien = tongtien;
	}

	public String[] getTongthue() {
		
		return this.tongthue;
	}

	
	public void setTongthue(String[] tongthue) {
		
		this.tongthue = tongthue;
	}
	
	public String[] getGhichu() {
		
		return this.ghichu;
	}

	
	public void setGhichu(String[] ghichu) {
		
		this.ghichu = ghichu;
	}

	
	public String[] getKyhieu() {
		
		return this.kyhieu;
	}

	
	public void setKyhieu(String[] kyhieu) {
		
		this.kyhieu = kyhieu;
	}

	
	public String[] getSohd() {
		
		return this.sohd;
	}

	
	public void setSohd(String[] sohd) {
		
		this.sohd = sohd;
	}

	
	public String[] getNgayhd() {
		
		return this.ngayhd;
	}

	
	public void setNgayhd(String[] ngayhd) {
		
		this.ngayhd = ngayhd;
	}

	
	public String[] getTenNcc() {
		
		return this.tenncc;
	}

	
	public void setTenNcc(String[] tenNcc) {
		
		this.tenncc = tenNcc;
	}


	public String[] getMST() {
		
		return this.mst;
	}

	
	public void setMst(String[] mst) {
		
		this.mst = mst;
	}
	
	public String[] getTienhang() {
		
		return this.tienhang;
	}

	
	public void setTienhang(String[] tienhang) {
		
		this.tienhang = tienhang;
	}

	
	public String[] getThuesuat() {
		
		return this.thuesuat;
	}

	
	public void setThuesuat(String[] thuesuat) {
		
		this.thuesuat = thuesuat;
	}

	
	public String[] getTienthue() {
		
		return this.thue;
	}

	
	public void setTienthue(String[] tienthue) {
		
		this.thue = tienthue;
	}

	public ResultSet getTienteRs() {
		if(this.tienteRs == null){
			this.tienteRs = db.get("select pk_seq, ma + ', ' + ten as TEN from ERP_TIENTE ");
		}
			
		return this.tienteRs;
	}

	
	public void setTienteRs(ResultSet tienteRs) {
		
		this.tienteRs = tienteRs;
	}

	public String getTienteId() {
		
		return this.tienteId;
	}

	
	public void setTienteId(String ttId) {
		
		this.tienteId = ttId;
	}
	
	
	public void init() 
	{
		if(this.id.length() > 0){
			String query = 	"SELECT	CPK.NGAY, ISNULL(CPK.DIENGIAI, ' ') AS DIENGIAI, CPK.LOAI, " +
							"CASE WHEN LOAI = 1 THEN ISNULL(NV.PK_SEQ, 0) " +
							"ELSE ISNULL(NCC.PK_SEQ, 0) END AS DTID, " +
							"CASE WHEN LOAI = 1 THEN ISNULL(NV.TEN, ' ') " +
							"ELSE ISNULL(NCC.TEN, ' ') END AS DOITUONG, CPK.TIENTE_FK AS TTID " +
							"FROM  ERP_CHIPHIKHAC CPK " +
							"LEFT JOIN ERP_NHACUNGCAP NCC ON NCC.PK_SEQ = CPK.DOITUONG " +
							"LEFT JOIN ERP_NHANVIEN NV ON NV.PK_SEQ = CPK.DOITUONG " +
							"WHERE CPK.PK_SEQ = '" + this.id + "'";

			ResultSet rs = db.get(query);
			if(rs != null)
			{
				try 
				{
					while(rs.next())
					{
						this.ngaynhap = rs.getString("NGAY");
						this.diengiai = rs.getString("DIENGIAI");
						this.tienteId = rs.getString("TTID");
						this.loai = rs.getString("LOAI");
						if(this.loai.equals("1")){
							this.nvId = rs.getString("DTID");
							this.nvTen = rs.getString("DOITUONG"); 
						}else{
							this.nccId = rs.getString("DTID");
							this.nccTen = rs.getString("DOITUONG"); 							
						}
					}
					rs.close();
					
					query = "SELECT COUNT(CPK_CT.NHOMCHIPHI_FK) AS NUM " +
							"FROM ERP_CHIPHIKHAC_CHITIET CPK_CT " +
							"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CPK_CT.NHOMCHIPHI_FK " +
							"INNER JOIN ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK = DVTH.PK_SEQ AND NCP.CONGTY_FK = DVTH.CONGTY_FK " +
							"WHERE NCP.LOAI = 2 AND NCP.CONGTY_FK = " + this.congtyId + " AND CPK_CT.CHIPHIKHAC_FK = " + this.id + "";
					
					System.out.println(query);
					rs = db.get(query);
					rs.next();
					
					this.chiphi = new String[rs.getInt("NUM")];
					this.diengiaicp = new String[rs.getInt("NUM")];
					this.tongtien = new String[rs.getInt("NUM")];
					this.tongthue = new String[rs.getInt("NUM")];
					
					rs.close();
					
					query = "SELECT CPK_CT.NHOMCHIPHI_FK AS NCPID, ISNULL(NCP.DIENGIAI, ' ') AS CHIPHI, " +
							"ISNULL(DVTH.TEN, ' ') AS DVTH, ISNULL(CPK_CT.TONGTIENCHUATHUE, 0) AS TONGTIENCHUATHUE, ISNULL(CPK_CT.THUE, 0) AS THUE, CPK_CT.DIENGIAI " +
							"FROM ERP_CHIPHIKHAC_CHITIET CPK_CT " +
							"INNER JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = CPK_CT.NHOMCHIPHI_FK " +
							"INNER JOIN ERP_DONVITHUCHIEN DVTH ON NCP.DONVITHUCHIEN_FK = DVTH.PK_SEQ AND NCP.CONGTY_FK = DVTH.CONGTY_FK " +
							"WHERE NCP.LOAI = 2 AND NCP.CONGTY_FK = " + this.congtyId + " AND CPK_CT.CHIPHIKHAC_FK = " + this.id + "";
					
					System.out.println(query);
					rs = db.get(query);
					int i = 0;
					while(rs.next()){
						this.chiphi[i] = rs.getString("NCPID");
						
						if(rs.getString("DIENGIAI") == null)
							this.diengiaicp[i] = rs.getString("CHIPHI") + " [" + rs.getString("DVTH") + "]";
						else
							this.diengiaicp[i] = rs.getString("DIENGIAI");
						
						this.tongtien[i] = rs.getString("TONGTIENCHUATHUE");
						this.tongthue[i] = rs.getString("THUE");
						i++;
					}
					System.out.println("Da chay toi day");
					rs.close();
				}catch (Exception e) 
				{
					System.out.println("__Exception: " + e.getMessage());
				}
			}
			
			
		}
	}
	
	
	public void getData(String hmcpId){
		String query; 
		if(hmcpId.length() > 0 & this.id.length() > 0){
			try{			
				query = "SELECT COUNT(*) AS NUM " +
						"FROM ERP_HOADONCHIPHIKHAC " +
						"WHERE CHIPHIKHAC_FK = '" + this.id + "' AND NHOMCHIPHI_FK = '" + hmcpId + "'";
			
				System.out.println(query);
				ResultSet rs = this.db.get(query);
				rs.next();
			
				maHD = new String[Integer.parseInt(rs.getString("NUM"))];
				mausoHD = new String[Integer.parseInt(rs.getString("NUM"))];
				kyhieu = new String[Integer.parseInt(rs.getString("NUM"))];
				sohd = new String[Integer.parseInt(rs.getString("NUM"))];
				ngayhd = new String[Integer.parseInt(rs.getString("NUM"))];
				mst = new String[Integer.parseInt(rs.getString("NUM"))];
				tenncc = new String[Integer.parseInt(rs.getString("NUM"))];
				tienhang = new String[Integer.parseInt(rs.getString("NUM"))];
				thuesuat = new String[Integer.parseInt(rs.getString("NUM"))];
				thue = new String[Integer.parseInt(rs.getString("NUM"))];
				cong = new String[Integer.parseInt(rs.getString("NUM"))];
				ghichu = new String[Integer.parseInt(rs.getString("NUM"))];
				rs.close();
			
				query = "SELECT isnull(MAUHOADON, '') as MAUHOADON, isnull(MAUSOHOADON, '') as MAUSOHOADON, KYHIEU, ISNULL(SOHOADON, ' ') AS SOHOADON, ISNULL(NGAYHOADON, ' ') NGAYHOADON, " +
						"ISNULL(NCC, ' ') AS NCC, ISNULL(MASOTHUE, ' ') AS MASOTHUE, ISNULL(CHIPHICHUATHUE, 0) AS CHIPHICHUATHUE, " +
						"ISNULL(THUESUAT, 0) AS THUESUAT, ISNULL(THUE, 0) AS THUE, ISNULL(GHICHU, ' ') AS GHICHU " +
						"FROM ERP_HOADONCHIPHIKHAC " +
						"WHERE CHIPHIKHAC_FK = '" + this.id + "' AND NHOMCHIPHI_FK = '" + hmcpId + "'";
				
				System.out.println(query);
				rs = this.db.get(query);
			
				int i = 0;
				while(rs.next()){
					maHD[i] = rs.getString("MAUHOADON");
					mausoHD[i] = rs.getString("MAUSOHOADON");
					kyhieu[i] = rs.getString("KYHIEU");
					sohd[i] = rs.getString("SOHOADON");
					ngayhd[i] = rs.getString("NGAYHOADON");
					tenncc[i] = rs.getString("NCC");
					mst[i] = rs.getString("MASOTHUE");
					tienhang[i] = rs.getString("CHIPHICHUATHUE");
					thuesuat[i] = rs.getString("THUESUAT");
					thue[i] = rs.getString("THUE");
					cong[i] = "" + Double.parseDouble(tienhang[i]) + Double.parseDouble(thue[i]) ;
					ghichu[i] = rs.getString("GHICHU");
					i++;
				}
			
				rs.close();
			}catch(java.sql.SQLException e){}
		}
	}
	
	public boolean Create(HttpServletRequest request)
	{	
		try 
		{

			db.getConnection().setAutoCommit(false);
			
			if(this.ngaynhap.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn ngày nhập";
				return false;
			}
			
			if(this.loai.equals("0")){
				if(this.nccId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn Nhà Cung Cấp";
					return false;
				}				
			}else{
				if(this.nvId.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn Nhân Viên";
					return false;
				}
			}
			String tigia= "1";
			String query = "";
			
			if(this.tienteId.length() > 0)
			{
				query = "SELECT TIGIAQUYDOI FROM ERP_TIGIA " +
						"WHERE TIENTE_FK = " + this.tienteId + " AND TuNgay <= '" +  this.ngaynhap + "' AND DenNgay >= '" +  this.ngaynhap + "' AND TRANGTHAI = 1" ;

					ResultSet rs = this.db.get(query);
					if(rs != null){
						if(rs.next())
							tigia = rs.getString("TIGIAQUYDOI");
						rs.close();
					}else{
						this.msg = "Vui lòng định dữ liệu nền TỈ GIÁ cho ngày ghi nhận hóa đơn: " + this.ngaynhap;
						db.getConnection().rollback();
						return false;					
					}
				
			}
			
			
			
			if(this.loai.equals("0")){

				// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
				query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '" + this.ngaynhap+ "')),120 ) as ngaydenhantt " +
						"FROM ERP_NHACUNGCAP " +
						"WHERE PK_SEQ = '"+ this.nccId +"'";
				ResultSet rsThoihanno = db.get(query);
				String ngaydenhantt ="";
				 
				if(rsThoihanno!= null)
				{
					while(rsThoihanno.next())
					{
						ngaydenhantt = rsThoihanno.getString("ngaydenhantt") ;
					}
					rsThoihanno.close();
				}
				
				query = "insert ERP_CHIPHIKHAC(TIENTE_FK, TIGIA, NGAY, DIENGIAI, CONGTY_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAI, DOITUONG, NGAYDENHANTT, NPP_FK) " +
						"values(" + this.tienteId + ", "+ tigia +", '" + this.ngaynhap + "',  N'" + this.diengiai + "', '" + this.congtyId + "', '0', '" + this.getDateTime() + "', " +
						"'" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.loai + "', '" + this.nccId + "', '"+ ngaydenhantt +"', "+this.nppdangnhap+")";
			}else{
				query = "insert ERP_CHIPHIKHAC(TIENTE_FK, TIGIA, NGAY, DIENGIAI, CONGTY_FK, TRANGTHAI, NGAYTAO, NGUOITAO, NGAYSUA, NGUOISUA, LOAI, DOITUONG, NPP_FK ) " +
						"values(" + this.tienteId + ", "+ tigia +", '" + this.ngaynhap + "',  N'" + this.diengiai + "', '" + this.congtyId + "', '0', '" + this.getDateTime() + "', " +
						"'" + this.userId + "', '" + this.getDateTime() + "', '" + this.userId + "', '" + this.loai + "', '" + this.nvId + "', "+this.nppdangnhap+")";		
			}
			
			System.out.println(query);
			if(!this.db.update(query)){
				db.update("rollback"); 
				this.msg="Không thể thực hện được dòng lệnh :"+query; 
				return false;
			}
			
			ResultSet rs = this.db.get("SELECT SCOPE_IDENTITY() AS CPID");
			rs.next();
			this.id = rs.getString("CPID");
			System.out.println("Id :" + this.id);
			rs.close();
			
			if(this.chiphi.length > 0){
				String[] stt = request.getParameterValues("stt");
				for(int i = 0; i < this.chiphi.length; i++)
				{					
					System.out.println("---CHI PHI: " + i + " -- GIA TRI: " + this.chiphi[i]);
					
					if(this.chiphi[i].trim().length() > 0 )
					{
						mausoHD = request.getParameterValues("mausoHD" + stt[i]);
						kyhieu = request.getParameterValues("kyhieu" + stt[i]);
						sohd = request.getParameterValues("sohd" + stt[i]);		
						ngayhd = request.getParameterValues("ngayhd" + stt[i]);
						tenncc = request.getParameterValues("tenncc" + stt[i]);
						mst = request.getParameterValues("mst" + stt[i]);		
						tienhang = request.getParameterValues("tienhang" + stt[i]);		
						thuesuat = request.getParameterValues("thuesuat" + stt[i]);		
						thue = request.getParameterValues("thue" + stt[i]);		
						ghichu = request.getParameterValues("ghichu" + stt[i]);
	
						double cong = 0;
						double tongthue = 0;
						
						if(kyhieu.length > 0){
	
							for(int j = 0; j < kyhieu.length; j++)
							{
								//if(kyhieu[j].trim().length() > 0 || sohd[j].trim().length() > 0)  //NEWTOYO CHI RANG BUOC SO TIEN HANG
								if(tienhang[j].trim().length() > 0)
								{
									String mauhoadon = "";
									if(mausoHD[j].trim().length() > 6)
										mauhoadon = mausoHD[j].substring(0, 6);
									else
										mauhoadon = mausoHD[j];
									
									if(tienhang[j].trim().length() == 0) 
										tienhang[j] = "0";
									if(thue[j].trim().length() == 0) thue[j] = "0";
	
									tienhang[j] = tienhang[j].replace(",", "");
									thuesuat[j] = thuesuat[j].replace(",", "");
									thue[j] = "" + Double.parseDouble(thuesuat[j].replace(",", ""))*Double.parseDouble(tienhang[j].replace(",", ""))/100;
								
									tongthue = tongthue + Double.parseDouble(thue[j].replace(",", ""));
								
									cong = cong + Double.parseDouble(tienhang[j].replace(",", ""));  
								
									if(this.loai.equals("0")){
										query = "INSERT INTO ERP_HOADONCHIPHIKHAC(MAUHOADON, MAUSOHOADON, KYHIEU, SOHOADON, CHIPHICHUATHUE, THUESUAT, THUE, GHICHU, CHIPHIKHAC_FK, NHOMCHIPHI_FK, NGAYHOADON) " +
												"VALUES('" + mauhoadon.toUpperCase() + "', '" + mausoHD[i].toUpperCase() + "', N'" + kyhieu[j].toUpperCase() + "', N'" + sohd[j] + "',  " + tienhang[j] + ", " +
													"" + thuesuat[j] + ", " + thue[j] + ", N'" + ghichu[j] + "', " + this.id + ", " + this.chiphi[i] + ",'" + this.ngayhd[j] + "')";
									
									}else{
										query = "INSERT INTO ERP_HOADONCHIPHIKHAC(MAUHOADON, MAUSOHOADON, KYHIEU, SOHOADON, NCC, MASOTHUE, CHIPHICHUATHUE, THUESUAT, THUE, GHICHU, CHIPHIKHAC_FK, NHOMCHIPHI_FK, NGAYHOADON) " +
												"VALUES( '" + mauhoadon.toUpperCase() + "', '" + mausoHD[i].toUpperCase() + "', N'" + kyhieu[j] + "', N'" + sohd[j] + "', N'" + tenncc[j] + "', '" + mst[j] + "', " + tienhang[j] + ", " + thuesuat[j] + ", " + thue[j] + ", " +
												"N'" + ghichu[j] + "', " + this.id + ", " + this.chiphi[i] + ",'" + this.ngayhd[j] + "')";
									}
								
									if(!this.db.update(query)){
										db.update("rollback"); 
										this.msg="Không thể thực hện được dòng lệnh :"+query; 
										return false;
									}
								}
							}
						}
						
						
						query = "INSERT INTO ERP_CHIPHIKHAC_CHITIET(CHIPHIKHAC_FK, NHOMCHIPHI_FK, DIENGIAI, TONGTIENCHUATHUE, THUE) " +
								"VALUES(" + this.id + ", " + this.chiphi[i] + ", N'" + this.diengiaicp[i] + "', " + cong + ", " + tongthue + ")";
						
						if(!this.db.update(query)){
							db.update("rollback"); 
							this.msg="Không thể thực hện được dòng lệnh :"+query; 
							return false;
						}	
					}

				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	public boolean Update(HttpServletRequest request)
	{
		try 
		{
			db.getConnection().setAutoCommit(false);
						
			if(this.ngaynhap.trim().length() <= 0)
			{
				this.msg = "Vui long chon ngay nhap";
				return false;
			}
			
			
			String tigia= "1";
			String query = "";
			
			if(this.tienteId.length() > 0)
			{
				query = "SELECT TIGIAQUYDOI FROM ERP_TIGIA " +
						"WHERE TIENTE_FK = " + this.tienteId + " AND TuNgay <= '" +  this.ngaynhap + "' AND DenNgay >= '" +  this.ngaynhap + "' AND TRANGTHAI = 1" ;

					ResultSet rs = this.db.get(query);
					if(rs != null){
						if(rs.next())
							tigia = rs.getString("TIGIAQUYDOI");
						rs.close();
					}else{
						this.msg = "Vui lòng định dữ liệu nền TỈ GIÁ cho ngày ghi nhận hóa đơn: " + this.ngaynhap;
						db.getConnection().rollback();
						return false;					
					}
				
			}
			
			if(this.loai.equals("0")){
				
				// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
				query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '" + this.ngaynhap+ "')),120 ) as ngaydenhantt " +
						"FROM ERP_NHACUNGCAP " +
						"WHERE PK_SEQ = '"+ this.nccId +"'";
				ResultSet rsThoihanno = db.get(query);
				String ngaydenhantt ="";
				 
				if(rsThoihanno!= null)
				{
					while(rsThoihanno.next())
					{
						ngaydenhantt = rsThoihanno.getString("ngaydenhantt") ;
					}
					rsThoihanno.close();
				}
				
				query = 	"update ERP_CHIPHIKHAC set TIENTE_FK = " + this.tienteId + ", TIGIA= "+ tigia +", ngay = '" + this.ngaynhap + "',  diengiai = N'" + this.diengiai + "', congty_fk = '" + this.congtyId + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', loai = '" + this.loai + "', doituong = '" + this.nccId + "', ngaydenhantt = '"+ ngaydenhantt +"' " +
							"where pk_seq = '" + this.id + "' ";
			}else{
				query = 	"update ERP_CHIPHIKHAC set TIENTE_FK = " + this.tienteId + ", TIGIA= "+ tigia +", ngay = '" + this.ngaynhap + "',  diengiai = N'" + this.diengiai + "', congty_fk = '" + this.congtyId + "', " +
							"ngaysua = '" + this.getDateTime() + "', nguoisua = '" + this.userId + "', loai = '" + this.loai + "', doituong = '" + this.nvId + "' " +
							"where pk_seq = '" + this.id + "' ";				
			}
			
			System.out.println("___1.Insert: " + query);
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật ERP_CHIPHIKHAC " + query;
				db.getConnection().rollback();
				return false;
			}
						
			if(this.chiphi.length > 0){
				String[] stt = request.getParameterValues("stt");
				
				query = "DELETE ERP_CHIPHIKHAC_CHITIET WHERE CHIPHIKHAC_FK = '" + this.id + "'";
				if(!this.db.update(query)){
					db.update("rollback"); 
					this.msg="Không thể thực hện được dòng lệnh :"+query; 
					return false;
				}

				query = "DELETE ERP_HOADONCHIPHIKHAC WHERE CHIPHIKHAC_FK = '" + this.id + "' ";
				if(!this.db.update(query)){
					db.update("rollback"); 
					this.msg="Không thể thực hện được dòng lệnh :"+query; 
					return false;
				}

				for(int i = 0; i < this.chiphi.length; i++){					
					if(this.chiphi[i].length() > 0){
							
						mausoHD = request.getParameterValues("mausoHD" + stt[i]);
						kyhieu = request.getParameterValues("kyhieu" + stt[i]);
						sohd = request.getParameterValues("sohd" + stt[i]);		
						ngayhd = request.getParameterValues("ngayhd" + stt[i]);
						tenncc = request.getParameterValues("tenncc" + stt[i]);
						mst = request.getParameterValues("mst" + stt[i]);		
						tienhang = request.getParameterValues("tienhang" + stt[i]);		
						thuesuat = request.getParameterValues("thuesuat" + stt[i]);		
						thue = request.getParameterValues("thue" + stt[i]);		
						ghichu = request.getParameterValues("ghichu" + stt[i]);

						double cong = 0;
						double tongthue = 0;
					
						if(kyhieu.length > 0){

							for(int j = 0; j < kyhieu.length; j++){
								if(kyhieu[j].trim().length() > 0 || sohd[j].trim().length() > 0){
									if(tienhang[j].trim().length() == 0) tienhang[j] = "0";
									if(thue[j].trim().length() == 0) thue[j] = "0";

									tienhang[j] = tienhang[j].replace(",", "");
									thuesuat[j] = thuesuat[j].replace(",", "");
									thue[j] = "" + Double.parseDouble(thuesuat[j].replace(",", ""))*Double.parseDouble(tienhang[j].replace(",", ""))/100;
							
									tongthue = tongthue + Double.parseDouble(thue[j].replace(",", ""));
							
									cong = cong + Double.parseDouble(tienhang[j].replace(",", ""));  
							
									String mauhoadon = "";
									if(mausoHD[j].trim().length() > 6)
										mauhoadon = mausoHD[j].substring(0, 6);
									else
										mauhoadon = mausoHD[j];
									
									if(this.loai.equals("0")){
										query = "INSERT INTO ERP_HOADONCHIPHIKHAC(MAUHOADON, MAUSOHOADON, KYHIEU, SOHOADON, CHIPHICHUATHUE, THUESUAT, THUE, GHICHU, CHIPHIKHAC_FK, NHOMCHIPHI_FK, NGAYHOADON) " +
												"VALUES('" + mauhoadon.toUpperCase() + "', '" + mausoHD[j] + "', N'" + kyhieu[j] + "', N'" + sohd[j] + "', " + tienhang[j] + ", " +
												"" + thuesuat[j] + ", " + thue[j] + ", N'" + ghichu[j] + "', " + this.id + ", " + this.chiphi[i] + ",'" + this.ngayhd[j] + "')";
								
									}else{
										query = "INSERT INTO ERP_HOADONCHIPHIKHAC(MAUHOADON, MAUSOHOADON, KYHIEU, SOHOADON, NCC, MASOTHUE, CHIPHICHUATHUE, THUESUAT, THUE, GHICHU, CHIPHIKHAC_FK, NHOMCHIPHI_FK, NGAYHOADON) " +
												"VALUES('" + mauhoadon.toUpperCase() + "', '" + mausoHD[j] + "', N'" + kyhieu[j] + "', N'" + sohd[j] + "', N'" + tenncc[j] + "', '" + mst[j] + "', " + tienhang[j] + ", " + thuesuat[j] + ", " + thue[j] + ", " +
												"N'" + ghichu[j] + "', " + this.id + ", " + this.chiphi[i] + ",'" + this.ngayhd[j] + "')";
									}
							
									if(!this.db.update(query)){
										db.update("rollback"); 
										this.msg="Không thể thực hện được dòng lệnh :"+query; 
										return false;
									}
								}
							}
						}
										
						query = "INSERT INTO ERP_CHIPHIKHAC_CHITIET(CHIPHIKHAC_FK, NHOMCHIPHI_FK, DIENGIAI,  TONGTIENCHUATHUE, THUE) " +
								"VALUES(" + this.id + ", " + this.chiphi[i] + ", N'" + this.diengiaicp[i] + "', " + cong + ", " + tongthue + ")";
					
						if(!this.db.update(query)){
							db.update("rollback"); 
							this.msg="Không thể thực hện được dòng lệnh :"+query; 
							return false;
						}				
					}
				}
				
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
	
			this.msg = "Loi: " + e.getMessage();
			try 
			{
				db.getConnection().rollback();
			} 
			catch (SQLException e1) {}
			return false;
		}
		
		return true;
	}
	
	

	
	public void DbClose() 
	{
		try 
		{
			this.db.shutDown();
		} 
		catch (Exception e) {}
		
	}
	
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String[] getMaHD() {
		
		return this.maHD;
	}

	
	public void setMaHD(String[] maHD) {
		
		this.maHD = maHD;
	}

	
	public String[] getMausoHD() {
		
		return this.mausoHD;
	}

	
	public void setMausoHD(String[] mausoHD) {
		
		this.mausoHD = mausoHD;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}

	
	
	
	
	

}
