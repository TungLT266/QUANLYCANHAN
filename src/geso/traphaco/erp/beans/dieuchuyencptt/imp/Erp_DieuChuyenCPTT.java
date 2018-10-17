package geso.traphaco.erp.beans.dieuchuyencptt.imp;
import geso.dms.center.util.PhanTrang;
import geso.dms.center.util.Phan_Trang;
import geso.dms.center.util.Utility;
import geso.dms.db.sql.dbutils;
import geso.traphaco.erp.beans.dieuchuyencptt.IErp_DieuChuyenCPTT;

import geso.traphaco.erp.beans.park.IErpPhieunhapkho;
import geso.traphaco.erp.beans.taisancodinh.IPhanBo;
import geso.traphaco.erp.beans.taisancodinh.imp.PhanBo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
public class Erp_DieuChuyenCPTT extends Phan_Trang implements IErp_DieuChuyenCPTT
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	dbutils db;
	String Id , Diengiai , Ma , LtsId, NtsId , ctyId , SothangKH , Dvt , ThangbatdauKH , PpKH , Msg , Nguoitao ,
	Nguoisua , Ngaytao , Ngaysua , Trangthai , userTen , userId , SoLuong , DonGia , ThanhTien;

	String[] cdtsIds;

	String[] khoanmucIds;
	String [] phanTramKhauHao;
	
	String cdId;
	String kmId;
	String dienGiaiCT;
	String ttcp;
	String loaitaisan;
	ResultSet RsTtcp;
	List<IPhanBo> phanBoList = new ArrayList<IPhanBo>();
	ResultSet NtsList;
	ResultSet CdList;
	ResultSet RsDc;
	ResultSet TsList;
	ResultSet DvtList , PPKHrS;
	ResultSet NsList;
	ResultSet LtsList;
	ResultSet cdtsRs;
	
	String pbCu;
	String pbCuId;
	
	ResultSet ttcpRs;
	String ttcpId;
	
	String ttcpCu;
	String ttcpCuId;
	

	String tscdId;
	ResultSet tscdRs;
	ResultSet pbRs;
	String pbId;
	String sochungtu;
	String sodieuchuyen;
	String tungay;
	String denngay;
	String phongban;
	String lnsId;
	boolean enable;
	String phanloai;
	String ngaychungtu= getDateTime();
	String maTSDD = "";
	
	
	ResultSet Rskhoanmuc;
	private int num;
	private int[] listPages;
	private int currentPages;
	
	
	public Erp_DieuChuyenCPTT( )
	{
		this.Id = "";
		this.Ma = "";
		this.Diengiai = "";
		this.pbCu="";
		this.LtsId = "";
		this.NtsId = "";
		this.ctyId = "";
		this.Dvt = "";
		this.cdId="";
		this.kmId="";
		this.sodieuchuyen="";
		this.tungay="";
		this.denngay="";
		this.phongban="";
		this.SothangKH = "";
		this.ThangbatdauKH = "";
		this.Msg = "";
		this.pbId="";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.sochungtu="";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		this.SoLuong = "";
		this.DonGia = "0";
		this.tscdId="";
		this.ThanhTien = "0";
		this.ngaychungtu="";
		this.ttcpId="";
		this.pbCuId="";
		this.ttcpCu="";
		this.ttcp = "Hệ thống tự động tạo";
		this.loaitaisan = "Được kế thừa từ Nhóm Tài Sản";
		this.lnsId = "";
		currentPages = 1;
		num = 1;
		this.phanloai = "1";
		this.dienGiaiCT ="";
		this.db = new dbutils();
		
	}
	
	public Erp_DieuChuyenCPTT( String id )
	{
		this.Id = id;
		this.Ma = "";
		this.Diengiai = "";
		this.LtsId = "";
		this.pbCu="";
		this.NtsId = "";
		this.ctyId = "";
		this.Dvt = "";
		this.sodieuchuyen="";
		this.tungay="";
		this.denngay="";
		this.phongban="";
		this.ttcpId="";
		this.tscdId="";
		this.pbId="";
		this.ttcpCu="";
		this.SothangKH = "";
		this.ThangbatdauKH = "";
		this.Msg = "";
		this.Nguoitao = "";
		this.Nguoisua = "";
		this.Ngaytao = "";
		this.Ngaysua = "";
		this.Trangthai = "1";
		this.userId = "";
		this.userTen = "";
		this.SoLuong = "";
		this.DonGia = "0";
		this.ngaychungtu="";
		this.ThanhTien = "0";
		this.ttcp = "Hệ thống tự động tạo";
		this.loaitaisan = "Được kế thừa từ Nhóm Tài Sản";
		this.pbCuId="";
		currentPages = 1;
		num = 1;
		
		this.phanloai = "1";
		this.lnsId = "";
		this.db = new dbutils();
		this.Msg = "";
		this.sochungtu="";
		this.cdId="";
		this.kmId="";
		this.dienGiaiCT = "";
		
	}
	
	public String getId()
	{
		return Id;
	}
	
	public String getMa()
	{
		return Ma;
	}
	
	public String getDiengiai()
	{
		return this.Diengiai;
	}
	
	public String getTtcp()
	{
		return this.ttcp;
	}

	public String getMsg()
	{
		return Msg;
	}
	
	public void setId(String id)
	{
		this.Id = id;
	}
	
	public void setMa(String ma)
	{
		this.Ma = ma;
	}
	
	public void setDiengiai(String diengiai)
	{
		this.Diengiai = diengiai;
	}
	
	public void setMsg(String Msg)
	{
		this.Msg = Msg;
	}
	
	public ResultSet getRsts()
	{
		return TsList;
	}
	
	public void setRsts(ResultSet Rsts)
	{
		this.TsList = Rsts;
	}
	
	public ResultSet getRsLoaitaisan()
	{
		return this.LtsList;
	}
	
	public void setRsLoaitaisan(ResultSet LtsList)
	{
		this.LtsList = LtsList;
	}

	public String getLoaitaisan()
	{
		return this.loaitaisan;
	}

	public void setUserid(String userId)
	{
		this.userId = userId;
	}
	
	public String getUserid()
	{
		return userId;
	}
	
	public void setUserTen(String userTen)
	{
		this.userTen = userTen;
	}
	
	public String getUserTen()
	{
		return userTen;
	}

	public void setLtsId(String ltsId)
	{
		this.LtsId = ltsId;
	}
	
	public String getLtsId()
	{
		return this.LtsId;
	}

	public ResultSet getRsCdts()
	{

		String query = "";
	
		
		query = 	" SELECT NCP.PK_SEQ AS NCPID,TK.SOHIEUTAIKHOAN+'-'+ TEN +'-'+ DIENGIAI AS DIENGIAI,0 AS PHANTRAM " +
				" FROM ERP_NHOMCHIPHI NCP " +
				" INNER JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN = NCP.TAIKHOAN_FK AND TK.NPP_FK=1" +
				" WHERE NCP.TRANGTHAI = 1 AND NCP.CONGTY_FK = "+this.ctyId +
				" ORDER BY NCP.PK_SEQ ";
			

		System.out.println("Get KMCP List : " + query);
		return this.db.getScrol(query);
	}

	public void setRsCdts(ResultSet cdtsRs)
	{

		this.cdtsRs = cdtsRs;
	}


	public void setCdtsIds(String[] cdtsIds)
	{

		this.cdtsIds = cdtsIds;
	}

	public String[] getCdtsIds()
	{

		return cdtsIds;
	}
	
	
	public ResultSet getRsTtcp()
	{

		return this.ttcpRs;
	}

	public void setRsTtcp(ResultSet ttcpRs)
	{

		this.ttcpRs = ttcpRs;
	}



	
	public String getNgaytao()
	{
		return Ngaytao;
	}
	
	public String getNguoitao()
	{
		return Nguoitao;
	}
	
	public String getNgaysua()
	{
		return Ngaysua;
	}
	
	public String getNguoisua()
	{
		return Nguoisua;
	}
	
	public void setNgaytao(String ngaytao)
	{
		this.Ngaytao = ngaytao;
	}
	
	public void setNguoitao(String nguoitao)
	{
		this.Nguoitao = nguoitao;
	}
	
	public void setNgaysua(String ngaysua)
	{
		this.Ngaysua = ngaysua;
	}
	
	public void setNguoisua(String nguoisua)
	{
		this.Nguoisua = nguoisua;
	}
	
	public String getTrangthai()
	{
		return Trangthai;
	}
	
	public void setTrangthai(String trangthai)
	{
		this.Trangthai = trangthai;
	}
	
	public String getNtsId()
	{
		return NtsId;
	}
		
	
	public String getDvt()
	{
		return Dvt;
	}
	
	public String getSothangKH()
	{
		return SothangKH;
	}
	
	public String getThangbatdauKH()
	{
		return ThangbatdauKH;
	}
	
	public String getPpKH()
	{
		return PpKH;
	}
	
	public void setNtsId(String ntsId)
	{
		this.NtsId = ntsId;
	}
	
	public void setCtyId(String ctyId)
	{
		this.ctyId = ctyId;
	}
	

	public String getCtyId()
	{
		return this.ctyId;
	}

	public void setDvt(String dvt)
	{
		this.Dvt = dvt;
	}
	
	public void setSothangKH(String sothangKh)
	{
		this.SothangKH = sothangKh;
	}
	
	public void setThangbatdauKH(String thangbatdauKh)
	{
		this.ThangbatdauKH = thangbatdauKh;
	}
	
	public void setPpKH(String ppKh)
	{
		this.PpKH = ppKh;
	}
	
	public ResultSet getRsNts()
	{
		return NtsList;
	}
	
	public void setRsNts(ResultSet rsNts)
	{
		this.NtsList = rsNts;
	}
	
	public void setPhanloai(String phanloai)
	{
		this.phanloai = phanloai;
	}
	
	public String getPhanloai()
	{
		return this.phanloai;
	}
	
	public ResultSet getRscd()
	{
		return CdList;
	}
	
	public void setRscd(ResultSet rsCd)
	{
		this.CdList = rsCd;
	}
	
		
	public ResultSet getRsdvt()
	{
		return DvtList;
	}
	
	public void setRsdvt(ResultSet rsDvt)
	{
		this.DvtList = rsDvt;
	}

	public ResultSet getRsTrungtamchiphi()
	{
		return this.RsTtcp;
	}
	
	public void setRsTrungtamchiphi(ResultSet rsTtcp)
	{
		this.RsTtcp = rsTtcp;
	}
	
	public String getSoLuong()
	{
		return this.SoLuong;
	}
	
	public void setSoLuong(String soluong)
	{
		this.SoLuong = soluong;
	}
	
	public String getDonGia()
	{
		return this.DonGia;
	}
	
	public void setDonGia(String dongia)
	{
		this.DonGia = dongia;
	}
	
	public void setThanhTien(String thanhtien)
	{
		this.ThanhTien = thanhtien;
	}
	
	public String getThanhTien()
	{
		return this.ThanhTien;
	}
	
	
	public void setPPKHrS(ResultSet ppkh)
	{
		this.PPKHrS = ppkh;
	}
	
	
	public ResultSet getPPKHrS()
	{
		return this.PPKHrS;
	}

	
	public ResultSet getNgansachRs() {
		
		return this.NsList;
	}

	
	public void setNgansachRs(ResultSet nsRs) {
		
		this.NsList = nsRs;
	}
	
	public boolean getEnable(){
		String query = 	" SELECT ISNULL(SUM(KHAUHAOTHUCTE), 0) AS KH " +
						" FROM Erp_DieuChuyenTaiSan_CHITIET " +
						" WHERE TAISAN_FK = '"+ this.Id + "'";
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			float kh = rs.getFloat("KH");
			rs.close();
			
			if(kh == 0){
				return true;
			}else{
				return false;
			}
		}catch(java.sql.SQLException e){
			return false;
		}
	}
	
	public boolean isKhauhao(String tsId){
		boolean result = false;
		String query = 	"SELECT COUNT(*) AS NUM " +
						"FROM ERP_KHAUHAOTAISAN " +
						"WHERE TAISAN_FK = " + tsId + " AND TRANGTHAI = 1 ";
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			String tmp = rs.getString("NUM");
			rs.close();
			
			if(tmp.equals("0")){
				return true;
			}else{
				return false;
			}
			
		}catch(java.sql.SQLException e){}
		
		return result;
	}
	
	public void init_convert()
	{
		String query = "";
		if(this.Id.length() > 0){
			System.out.println("_____Khoi tao: " + query);
			query = 	"SELECT  TSCD.MA, TSCD.DIENGIAI, ISNULL(TSCD.DONVI, '') AS DONVI, " +
						"'0' AS SOTHANGKH, '' AS THANGBATDAUKH, TSCD.TRANGTHAI, TSCD.NGUOITAO, TSCD.NGUOISUA, " +
						"TSCD.NGAYTAO, TSCD.NGAYSUA, TSCD.THANHTIEN, TSCD.SOLUONG, TSCD.DONGIA, '0' AS NHOMTAISAN_FK, " +
						"'' AS TTCP, '' AS LTS, LAPNGANSACH_TAISAN_FK as lnsId, TSCD.PHANLOAI " +
						"FROM Erp_DieuChuyenTaiSan TSCD " +
						
						"WHERE TSCD.PK_SEQ = '" + this.Id + "' ";			
			System.out.println(query);
			ResultSet rs = db.get(query);
			try
			{
					if (rs.next())
					{
						this.Ma = rs.getString("ma") + " - TSCD" ;
						this.Diengiai = rs.getString("diengiai");
						this.NtsId = rs.getString("nhomtaisan_fk");
						this.loaitaisan = rs.getString("LTS");
						this.ttcp = "Hệ thống tự động tạo";
						this.Dvt = rs.getString("donvi");
						this.SothangKH = rs.getString("sothangKH");
						this.ThangbatdauKH = rs.getString("thangbatdauKH");
						this.Trangthai = rs.getString("trangthai");
						this.Nguoitao = rs.getString("nguoitao");
						this.Nguoisua = rs.getString("nguoisua");
						this.Ngaytao = rs.getString("ngaytao");
						this.Ngaysua = rs.getString("ngaysua");
						this.ThanhTien = "" + rs.getLong("ThanhTien");
						this.SoLuong = rs.getString("SoLuong");
														
						this.DonGia = "" + rs.getLong("DonGia");
						this.lnsId = rs.getString("lnsId") != null ? rs.getString("lnsId") : "";
						this.phanloai = "1";
						this.maTSDD = rs.getString("ma");
						this.Id = "";
					}
					if (rs != null)
					{
						rs.close();
					}
			}
			catch (Exception er)
			{
					this.Msg = "Loi " + er.toString();
			}
		}
		
		//System.out.println("______Init::::::: " + query);
	}

	
	private String getDateTime()
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}
	
	public ResultSet getChoncongdung(){
		String query = "";
		if(this.Id.length() > 0){
			try{
				query = 	"SELECT COUNT(CONGDUNG_FK) AS NUM FROM ERP_TAISANCODINH_CONGDUNG WHERE TAISAN_FK = " + this.Id + " ";
			
				ResultSet rs = this.db.get(query);
				rs.next();
				
				if(rs.getString("NUM").equals("0")){ // nghia la cong dung chua duoc luu trong ERP_TAISAN_CONGDUNG
					
					query = 	"SELECT CD.PK_SEQ AS CDTSID, CD.TEN AS DIENGIAI, 0 AS PHANTRAM " +
								"FROM ERP_NHOMTAISAN_CONGDUNG NTS_CD " +
								"INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK " +
								"WHERE NHOMTAISAN_FK = " + this.NtsId + " ORDER BY CD.PK_SEQ ";
					
				}else{
					
					query = "SELECT CD.PK_SEQ AS CDTSID, CD.TEN AS DIENGIAI, TS_CD.PHANTRAM  " +
							"FROM ERP_TAISANCODINH_CONGDUNG TS_CD " +
							"INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = TS_CD.CONGDUNG_FK " +
							"WHERE TS_CD.TAISAN_FK = " + this.Id + " ORDER BY CD.PK_SEQ ";
					
				}
				
				System.out.println("getChoncongdung " + query);
				rs.close();
				return this.db.get(query);
			}catch(java.sql.SQLException e){ return null;}

		}else{
			if(this.NtsId.length() > 0){
				query = 	"SELECT CD.PK_SEQ AS CDTSID, CD.TEN AS DIENGIAI, 0 AS PHANTRAM " +
							"FROM ERP_NHOMTAISAN_CONGDUNG NTS_CD " +
							"INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK " +
							"WHERE NHOMTAISAN_FK = " + this.NtsId + " ORDER BY CD.PK_SEQ ";
			
				System.out.println("getChoncongdung " + query);
				return this.db.get(query);
			}else{
				return null;
			}
		}
		
		
	}

	public ResultSet getChoncongdungthem(){
		String query = "";
		if(this.Id.length() > 0){
		
			query = 	"SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI " +
						"FROM ERP_CONGDUNG WHERE PK_SEQ NOT IN (" +
						"SELECT CD.PK_SEQ " +
						"FROM ERP_NHOMTAISAN_CONGDUNG NTS_CD " +
						"INNER JOIN ERP_CONGDUNG CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK " +
						"WHERE NHOMTAISAN_FK = " + this.NtsId + ") ORDER BY PK_SEQ ";
			
			return this.db.get(query);

		}else{
			query = 	"SELECT PK_SEQ AS CDTSID, TEN AS DIENGIAI " +
						"FROM ERP_CONGDUNG ORDER BY PK_SEQ ";
			
			return this.db.get(query);
		}
				
	}

	public ResultSet getChonTTCP(){
		String query = "";
		if(this.tscdId.length() > 0){
			try{
				query = "SELECT (A.NUM1 - B.NUM2) AS NUM, A.NUM1, B.NUM2 " +
						"FROM( " +
						"		SELECT TSCD.PK_SEQ AS TSID, COUNT(NTS_PB.TTCP_FK) AS NUM1 " +
						"		FROM ERP_TAISANCODINH TSCD " +
						"		LEFT JOIN ERP_NHOMTAISAN_PHANBOKHAUHAO NTS_PB ON NTS_PB.NHOMTAISAN_FK = TSCD.NHOMTAISAN_FK " +
						"		WHERE TSCD.PK_SEQ = " + this.tscdId + " " +
						"		GROUP BY TSCD.PK_SEQ " +
						")A " +
						"LEFT JOIN " +
						"( " +
						"		SELECT TSCD.PK_SEQ AS TSID, COUNT(PB.TTCPNHAN_FK) AS NUM2 " +
						"		FROM ERP_TAISANCODINH TSCD " +
						"		LEFT JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TSCD.TTCP_FK = PB.TTCPCHO_FK AND TSCD.PK_SEQ = PB.TAISAN_FK " +
						"		WHERE TSCD.PK_SEQ = " + this.tscdId+ " " +
						"		GROUP BY TSCD.PK_SEQ " +
						")B ON A.TSID = B.TSID " +
						"WHERE A.TSID = " + this.tscdId + " " ;	
					
				ResultSet rs = this.db.get(query);
				
				rs.next();
				
				if(!rs.getString("NUM1").equals("0") & !rs.getString("NUM2").equals("0")){ // nghia la da luu trong ERP_TRUNGTAMCHIPHI_PHANBO
					query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, PB.PHANTRAM " +
								"FROM ERP_TAISANCODINH TSCD " +
								"LEFT JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TSCD.TTCP_FK = PB.TTCPCHO_FK AND TSCD.PK_SEQ = PB.TAISAN_FK " + 
								"LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCPNHAN_FK " +
								"WHERE TSCD.PK_SEQ =  " + this.tscdId + " " ;

				}else{ //nghia la chua duoc luu trong ERP_TRUNGTAMCHIPHI_PHANBO

					query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, 0 AS PHANTRAM " +
								"FROM ERP_NHOMTAISAN_PHANBOKHAUHAO PB " +
								"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK " +
								"WHERE NHOMTAISAN_FK = " + this.tscdId + " ORDER BY TTCP.PK_SEQ ";
				}
				
				System.out.println("getChonTTCP " + query);
				rs.close();
				return this.db.get(query);
				
			}catch(java.sql.SQLException e){
				return null;
			}
		
		
		}
		System.out.println(query);
		
		return null;
	}

	public ResultSet getChonTTCPThem(){
		String query = "";
		if(this.Id.length() > 0){
		
			query = 	"SELECT PK_SEQ AS TTCPID, MA, DIENGIAI "+
						"FROM ERP_TRUNGTAMCHIPHI WHERE PK_SEQ NOT IN (" +
						"SELECT TTCP.PK_SEQ  " +
						"FROM ERP_NHOMTAISAN_PHANBOKHAUHAO PB " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK " +
						"WHERE NHOMTAISAN_FK = " + this.NtsId + " ) ORDER BY PK_SEQ ";
			
			return this.db.get(query);

		}else{
			query = 	"SELECT PK_SEQ AS TTCPID, MA, DIENGIAI "+
						"FROM ERP_TRUNGTAMCHIPHI ORDER BY PK_SEQ";
			
			return this.db.get(query);
		}
		
	}
	
	public String getCdIdsList(){
		String tmp = "";
		if(this.cdtsIds != null){
			for(int i = 0; i < this.cdtsIds.length; i++){
				tmp = tmp +  this.cdtsIds[i] + ";" ;
			}
		}
		return tmp;
	}


	
	public boolean themmoiMa(HttpServletRequest request)
	{
		Utility util = new Utility();
		int thang = 0;
		int nam = 0;
	
		try {
			
			this.db.getConnection().setAutoCommit(false);
			if(this.pbId.length()<=0)
			{
				this.Msg= "Vui lòng chọn phòng ban!";
			}
			if(this.tscdId.length()<=0)
			{
				this.Msg= "Vui lòng chọn chi phí trả trước!";
			}
			if(this.ttcpId.length()<=0)
			{
				this.Msg= "Vui lòng chọn trung tâm chi phí!";
			}
			float totalPt=0;
			for (int i=0;i<phanBoList.size();i++)
			{
				IPhanBo phanBo = phanBoList.get(i);
				totalPt+=Double.parseDouble(phanBo.getPhanTram());
			}
			if(totalPt!=100)
			{
				this.Msg = "Tổng phần trăm phân bổ phải bằng 100%";
				return false;
			}
			
			if(this.ngaychungtu.length() == 10)
			{
				thang = Integer.valueOf(this.ngaychungtu.substring(5, 7))+1;
				nam = Integer.valueOf(this.ngaychungtu.substring(0, 4));
			}
			if(this.pbCuId.length()<=0)
			{
				this.pbCuId="NULL";
			}
			if(this.ttcpCuId.length()<=0)
			{
				this.ttcpCuId="NULL";
			}
			String query="INSERT INTO ERP_DIEUCHUYENCONGCUDUNGCU (CCDC_FK,NGAYCHUNGTU,SOCHUNGTU,TRANGTHAI,THANG,NAM,NGUOITAO,NGUOISUA,NGAYTAO,NGAYSUA,TTCP_FK,TTCP_FK_OLD,DIENGIAI) " +
						"VALUES ('"+this.tscdId+"','"+this.ngaychungtu+"','"+this.sochungtu+"','0','"+thang+"','"+nam+"','"+this.userId+"','"+this.userId+"','"+this.getDateTime()+"','"+this.getDateTime()+"','"+this.ttcpId+"','"+this.ttcpCuId+"',N'"+this.dienGiaiCT+"') ";
				System.out.println("QUERY TẠO MỚI "+query);
				if(!this.db.update(query))
				{
					this.setMsg("Không thể tạo mới điều chuyển");
					return false;
				}
			
			query = "SELECT SCOPE_IDENTITY() AS ID";
			ResultSet rs = this.db.get(query);
			rs.next();
			
			this.Id = rs.getString("ID");

		

			for (int i=0;i<phanBoList.size();i++)
			{
					IPhanBo phanBo = phanBoList.get(i);
					//String pt =util.antiSQLInspection(request.getParameter("pt1" + this.cdtsIds[i]));
					query = "INSERT INTO ERP_DIEUCHUYENCONGCUDUNGCU_KHOANMUCCHIPHI(DIEUCHUYENCONGCUDUNGCU_FK,CCDC_FK,KHOANMUCCHIPHI_FK, PHANTRAM) VALUES ("+this.Id+"," + this.tscdId + ", " + phanBo.getKhoanMucId() + ", " + phanBo.getPhanTram() + " )";
				
					System.out.println("Insert chi tiet dieu chuyen tai san"+query);
					if(!this.db.update(query))
					{
						this.Msg = "4.Error: " + query;
						this.db.getConnection().rollback();
						return false;
					}

			}
				
			this.db.getConnection().commit();
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
	}
	
	public boolean UpdateMa(HttpServletRequest request)
	{
		Utility util = new Utility();
		int thang = 0;
		int nam = 0;
		try {
			
			this.db.getConnection().setAutoCommit(false);
			/*if(this.pbId.length()<=0)
			{
				this.Msg= "Vui lòng chọn phòng ban!";
				return false;
			}*/
			if(this.tscdId.length()<=0)
			{
				this.Msg= "Vui lòng chọn chi phí trả trước!";
				return false;
			}
			if(this.ttcpId.length()<=0)
			{
				this.Msg= "Vui lòng chọn trung tâm chi phí!";
				return false;
			}
		/*	float totalPt=0;
			if(totalPt!=100)
			{
				this.Msg = "Tổng phần trăm phân bổ phải bằng 100%";
				return false;
			}*/
			float totalPt=0;
			
			for (int i=0;i<phanBoList.size();i++)
			{
				IPhanBo phanBo = phanBoList.get(i);
				totalPt+=Double.parseDouble(phanBo.getPhanTram());
			}
			if(totalPt!=100)
			{
				this.Msg = "Tổng phần trăm phân bổ phải bằng 100%";
				return false;
			}
			
			if(this.ngaychungtu.length() == 10)
			{
				thang = Integer.valueOf(this.ngaychungtu.substring(5, 7));
				nam = Integer.valueOf(this.ngaychungtu.substring(0, 4));
			}
		
			/*if(this.pbCuId.length()<=0)
			{
				this.pbCuId="NULL";
			}*/
			if(this.ttcpCuId.length()<=0)
			{
				this.ttcpCuId="NULL";
			}
			String query="Update ERP_DIEUCHUYENCONGCUDUNGCU SET CCDC_FK='"+this.tscdId+"' , SOCHUNGTU='"+this.sochungtu+"',NGAYCHUNGTU='"+this.ngaychungtu+"',NGAYSUA='"+getDateTime()+"',NGUOISUA="+this.userId+" ," +
					"  TTCP_FK = " +this.ttcpId + " , TTCP_FK_OLD ="+this.ttcpCuId +",DIENGIAI = N'"+this.dienGiaiCT+"'  " +
			
					" WHERE PK_SEQ = "+this.Id;
			if(!this.db.update(query))
			{
				this.setMsg("Không thể tạo mới điều chuyển");
				this.db.getConnection().rollback();
				return false;
			}
			
			query="DELETE ERP_DIEUCHUYENCONGCUDUNGCU_KHOANMUCCHIPHI "+
			" WHERE DIEUCHUYENCONGCUDUNGCU_FK = "+this.Id;
			if(!this.db.update(query))
			{
				this.setMsg("Không thể cập nhật điều chuyển");
				this.db.getConnection().rollback();
				return false;
			}


			for (int i=0;i<phanBoList.size();i++)
			{
					IPhanBo phanBo = phanBoList.get(i);
					//String pt =util.antiSQLInspection(request.getParameter("pt1" + this.cdtsIds[i]));
					query = "INSERT INTO ERP_DIEUCHUYENCONGCUDUNGCU_KHOANMUCCHIPHI(DIEUCHUYENCONGCUDUNGCU_FK,CCDC_FK, KHOANMUCCHIPHI_FK, PHANTRAM) VALUES ("+this.Id+"," + this.tscdId + ", " + phanBo.getKhoanMucId() + ", " + phanBo.getPhanTram() + " )";
				
					System.out.println("Insert chi tiet dieu chuyen tai san"+query);
					if(!this.db.update(query))
					{
						this.Msg = "4.Error: " + query;
						this.db.getConnection().rollback();
						return false;
					}

			}
		/*		
			query="DELETE ERP_DIEUCHUYENTAISAN_PHANBO "+
			" WHERE DIEUCHUYENTAISAN_FK = "+this.Id;
			if(!this.db.update(query))
			{
				this.setMsg("Không thể cập nhật điều chuyển");
				this.db.getConnection().rollback();
				return false;
			}*/
			/*if(this.khoanmucIds != null)
			{
				for(int i = 0; i < this.khoanmucIds.length ; i++)
				{
					String pt = util.antiSQLInspection(request.getParameter("pt2" + this.khoanmucIds[i]));
					query = "INSERT INTO ERP_DIEUCHUYENTAISAN_PHANBO(DIEUCHUYENTAISAN_FK, TAISAN_FK, TTCP_FK, PHANTRAM) " +
						" VALUES ("+this.Id+"," +this.tscdId+","+this.khoanmucIds[i]+","+pt+")";
				
					if(!this.db.update(query))
					{
						this.Msg = "5.Error: " + query;
					    this.db.getConnection().rollback();
						return false;
					}

				}
		
			}
*/			this.db.getConnection().commit();
			this.db.getConnection().setAutoCommit(true);
			
			
		} catch (SQLException e) {
			
			e.printStackTrace();
			try {
				db.getConnection().rollback();
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			return false;
		}
		return true;
		
	}

	private void CapnhatChovaNhan(){
		String query;
		// Cap nhat lai NHANPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 0 WHERE PK_SEQ IN ( " +
				"SELECT PK_SEQ " +
				"FROM ERP_TRUNGTAMCHIPHI TTCP " +
				"WHERE NHANPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE TAISAN_FK IS NOT NULL) )" ;
		this.db.update(query);
		
		// Cap nhat lai NHANPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 1 " +
				"WHERE PK_SEQ IN (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0 AND TAISAN_FK IS NOT NULL) " ;
		this.db.update(query);

		// Cap nhat lai CHOPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 0 WHERE PK_SEQ IN ( " +
				"SELECT PK_SEQ " +
				"FROM ERP_TRUNGTAMCHIPHI TTCP " +
				"WHERE CHOPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE TAISAN_FK IS NOT NULL) )" ;
		this.db.update(query);
		
		// Cap nhat lai CHOPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 1 " +
				"WHERE PK_SEQ IN  (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0  AND TAISAN_FK IS NOT NULL) " ;
		this.db.update(query);

	}
	public void Delete(String id) 
	{		
		
	

			try
			{
					
					 
					String query = "DELETE FROM ERP_DIEUCHUYENTAISAN_CONGDUNG " +
							"WHERE DIEUCHUYENTAISAN_FK = "+id;
					System.out.println(query);
					this.db.update(query);
	
					
					query = "DELETE FROM ERP_DIEUCHUYENTAISAN_KHOANMUC " +
					"WHERE DIEUCHUYENTAISAN_FK = "+id;
					
					this.db.update(query);
	
					query = "UPDATE Erp_DieuChuyenTaiSan set trangthai=2 WHERE PK_SEQ = " + id + "";
					System.out.println(query);
					
					this.db.update(query);
					
							
				
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	
	public void init(String sql)
	{
		NumberFormat formatter = new DecimalFormat("#,###,###.##"); 
		try {
		String query="SELECT dc.CCDC_FK,dc.NGAYCHUNGTU,dc.SOCHUNGTU, " +
				" dc.TRANGTHAI,dc.THANG,dc.NAM,ISNULL(dc.TTCP_FK,0) AS TTCP_FK,ISNULL(dc.TTCP_FK_OLD,0) as TTCP_FK_OLD,ISNULL(TTCP.MA,'') + ISNULL(TTCP.TEN,'') AS TTCPCU,ISNULL(DC.DIENGIAI,'') AS DIENGIAI" +
				" FROM ERP_DIEUCHUYENCONGCUDUNGCU dc   " +
				" LEFT JOIN ERP_DONVITHUCHIEN TTCP ON TTCP.PK_SEQ = DC.TTCP_FK_OLD " +
				" WHERE dc.PK_SEQ ="+this.Id ;
		ResultSet dcRs= db.get(query);
		System.out.println("QUERY :"+query);
		
			while(dcRs.next())
			{
				//this.pbId=dcRs.getString("DVTH_FK");
				this.tscdId=dcRs.getString("CCDC_FK");
				this.ngaychungtu=dcRs.getString("NGAYCHUNGTU");
				this.sochungtu=dcRs.getString("SOCHUNGTU");
				this.ttcpCu=dcRs.getString("TTCPCU");
				this.ttcpId=dcRs.getString("TTCP_FK");
				this.ttcpCuId=dcRs.getString("TTCP_FK_OLD").equals("0")?"" :dcRs.getString("TTCP_FK_OLD");
				this.dienGiaiCT = dcRs.getString("DIENGIAI");
			}
			
		
			query="SELECT KHOANMUCCHIPHI_FK, PHANTRAM FROM ERP_DIEUCHUYENCONGCUDUNGCU_KHOANMUCCHIPHI WHERE DIEUCHUYENCONGCUDUNGCU_FK = "+this.Id+"";
			ResultSet rsDCPB=db.get(query);
			while(rsDCPB.next())
			{
				IPhanBo phanBo=new PhanBo();
				phanBo.setKhoanMucId(rsDCPB.getString("KHOANMUCCHIPHI_FK"));
				phanBo.setPhanTram(rsDCPB.getString("PHANTRAM"));
				this.phanBoList.add(phanBo);
			}
			rsDCPB.close();
		}
		 catch (SQLException e) {
			e.printStackTrace();
		}
			
		
		

	}
	
	public void createRs()
	{
		try { 
		String query="";	
		
		query=" SELECT PK_SEQ,MA + '-' + DIENGIAI as TEN FROM ERP_CONGCUDUNGCU WHERE TRANGTHAI=1";
		this.tscdRs=this.db.get(query);
	/*	
		query = "select PK_SEQ,MA +'-' +TEN  as TEN from ERP_DONVITHUCHIEN WHERE TRANGTHAI = 1 ORDER BY MA ";
		this.pbRs = db.get(query);
		*/
		
		query = "select PK_SEQ,MA +'-' +TEN  as TEN from erp_donvithuchien WHERE TRANGTHAI = 1 and congty_fk="+this.ctyId+" ORDER BY MA ";
		this.ttcpRs = db.get(query);
	
		if(this.tscdId.length()>0)
		{
		query=" select DVTH_FK,dvth.TEN  as TEN from ERP_CONGCUDUNGCU tscd \n" + 
				" left join ERP_DONVITHUCHIEN dvth on tscd.DVTH_FK=dvth.PK_SEQ\n" +
				" where tscd.pk_seq="+this.tscdId;
		ResultSet rs1= db.get(query);
		System.out.println("Cau lay DVTH"+query);
		while (rs1.next())
		{
			this.ttcpCuId=rs1.getString("DVTH_FK");
			this.ttcpCu=rs1.getString("TEN");
		}
		}
		//this.ttcpCu=db.get(query);
		
	
		} catch (Exception e) {
			e.printStackTrace();
			this.Msg="Tài sản này đã được khấu hao hết!";
		}

	}
	
//	public ResultSet Laykhauhao(){
//		System.out.println("'" + this.Id + "'");
//		return this.db.get("'" + this.tscdId + "'"); 
//	}
	
	
	public void DBClose()
	{
		try
		{
			if (TsList != null)
			{
				TsList.close();
			}
			if (CdList != null)
			{
				CdList.close();
			}
			if (NtsList != null)
			{
				NtsList.close();
			}
			if (this.RsTtcp != null)
			{
				this.RsTtcp.close();
			}
			if (this.DvtList != null)
			{
				this.DvtList.close();
			}
			if (this.PPKHrS != null)
			{
				this.PPKHrS.close();
			}
			if (this.NsList != null)
			{
				this.NsList.close();
			}
			if (this.LtsList != null)
			{
				this.LtsList.close();
			}
			if (this.ttcpRs != null)
			{
				this.ttcpRs.close();
			}
			if (this.cdtsRs != null)
			{
				this.cdtsRs.close();
			}
			
			
		}
		catch (Exception er)
		{
			er.printStackTrace();
		}
		finally
		{
			if (db != null)
			{
				db.shutDown();
			}
		}
	}

	

	
	public String getLapngansachId() {
		
		return this.lnsId;
	}

	
	public void setLapngansachId(String lnsId) {
		
		this.lnsId = lnsId;
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
		this.listPages= listPages;
		
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

	
	public void setTtcp(String Ttcp) {
		this.ttcp = Ttcp;		
	}
	
	

	public String getNgaychungtu() {
		return ngaychungtu;
	}

	public void setNgaychungtu(String ngaychungtu) {
		this.ngaychungtu = ngaychungtu;
	}

	public String getTscdId() {
		return tscdId;
	}

	public void setTscdId(String tscdId) {
		this.tscdId = tscdId;
	}

	public ResultSet getTscdRs() {
		return tscdRs;
	}

	public void setTscdRs(ResultSet tscdRs) {
		this.tscdRs = tscdRs;
	}

	public String getSochungtu() {
		return sochungtu;
	}

	public void setSochungtu(String sochungtu) {
		this.sochungtu = sochungtu;
	}

	public String getPbId() {
		return pbId;
	}

	public void setPbId(String pbId) {
		this.pbId = pbId;
	}

	public ResultSet getPbRs() {
		return pbRs;
	}

	public void setPbRs(ResultSet pbRs) {
		this.pbRs = pbRs;
	}


	public ResultSet getRskhoanmuc() {
		return Rskhoanmuc;
	}

	public void setRskhoanmuc(ResultSet rskhoanmuc) {
		Rskhoanmuc = rskhoanmuc;
	}

	public String[] getKhoanmucIds() {
		return khoanmucIds;
	}

	public void setKhoanmucIds(String[] khoanmucIds) {
		this.khoanmucIds = khoanmucIds;
	}

	@Override
	public String getTtcpIdsList() {
		// TODO Auto-generated method stub
		return null;
	}

	public ResultSet getRsDc() {
		return RsDc;
	}

	public void setRsDc(ResultSet rsDc) {
		RsDc = rsDc;
	}

	public String getSodieuchuyen() {
		return sodieuchuyen;
	}

	public void setSodieuchuyen(String sodieuchuyen) {
		this.sodieuchuyen = sodieuchuyen;
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

	public String getPhongban() {
		return phongban;
	}

	public void setPhongban(String phongban) {
		this.phongban = phongban;
	}

	

	public String getCdId() {
		return cdId;
	}

	public void setCdId(String cdId) {
		this.cdId = cdId;
	}

	public String getKmId() {
		return kmId;
	}

	public void setKmId(String kmId) {
		this.kmId = kmId;
	}


	public String getPbCu() {
		return pbCu;
	}

	public void setPbCu(String pbCu) {
		this.pbCu = pbCu;
	}

	public String getPbCuId() {
		return pbCuId;
	}

	public void setPbCuId(String pbCuId) {
		this.pbCuId = pbCuId;
	}

	public String getTtcpCu() {
		return ttcpCu;
	}

	public void setTtcpCu(String ttcpCu) {
		this.ttcpCu = ttcpCu;
	}

	public String getTtcpCuId() {
		return ttcpCuId;
	}

	public void setTtcpCuId(String ttcpCuId) {
		this.ttcpCuId = ttcpCuId;
	}

	public ResultSet getTtcpRs() {
		return ttcpRs;
	}

	public void setTtcpRs(ResultSet ttcpRs) {
		this.ttcpRs = ttcpRs;
	}

	public String getTtcpId() {
		return ttcpId;
	}

	public void setTtcpId(String ttcpId) {
		this.ttcpId = ttcpId;
	}
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT= dienGiaiCT;
	}
	

	public List<IPhanBo> getPhanBoList() {
		return phanBoList;
	}

	public void setPhanBoList(List<IPhanBo> phanBoList) {
		this.phanBoList = phanBoList;
	}

	public String[] getPhanTramKhauHao() {
		return phanTramKhauHao;
	}

	public void setPhanTramKhauHao(String[] phanTramKhauHao) {
		this.phanTramKhauHao = phanTramKhauHao;
	}


	
}
