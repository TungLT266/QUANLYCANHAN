package geso.traphaco.erp.beans.taisancodinh.imp;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.util.*;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.erp.beans.taisancodinh.IErp_CongCuDungCu;
import geso.traphaco.erp.beans.taisancodinh.IPhanBo;
public class Erp_CongCuDungCu implements IErp_CongCuDungCu
{
	dbutils db;
	String Id , Diengiai , Ma , LccdcId, LccdcTen, NccdcId , ctyId , SothangKH , Dvt , ThangbatdauKH , PpKH , Msg , Nguoitao ,
	Nguoisua , Ngaytao , Ngaysua , Trangthai , userTen , userId , SoLuong , DonGia , ThanhTien,DvthId;

	String[] cdccdcIds;
	String[] ttcpIds;

	String ttcp;
	String loaiCCDC;
	ResultSet RsTtcp;
	
	ResultSet NccdcList;
	ResultSet DvkdList;
	ResultSet CdList;
	ResultSet dieuChuyenCCDCRs;
	ResultSet CcdcList;
	ResultSet DvtList , PPKHrS;
	ResultSet NsList;
	ResultSet LccdcList;
	ResultSet ttcpRs, cdccdcRs;
	ResultSet DvthList;
	boolean enable;
	List<IPhanBo> phanBoList = new ArrayList<IPhanBo>();
	
	public Erp_CongCuDungCu( )
	{
		this.Id = "";
		this.Ma = "";
		this.Diengiai = "";
		this.LccdcId = "";
		this.LccdcTen = "";
		this.NccdcId = "";
		this.ctyId = "";
		this.Dvt = "";
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
		this.SoLuong = "1";
		this.DonGia = "0";
		this.ThanhTien = "0";
		this.ttcp = "";
		this.DvthId="";
		this.loaiCCDC = "Được kế thừa từ Nhóm Công Cụ Dụng Cụ";
		this.db = new dbutils();
		
	}
	
	public Erp_CongCuDungCu( String id )
	{
		this.Id = id;
		this.Ma = "";
		this.Diengiai = "";
		this.LccdcId = "";
		this.LccdcTen = "";
		this.NccdcId = "";
		this.ctyId = "";
		this.Dvt = "";
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
		this.SoLuong = "1";
		this.DonGia = "0";
		this.ThanhTien = "0";
		this.ttcp = "";
		this.loaiCCDC = "Được kế thừa từ Nhóm Tài Sản";
		this.DvthId="";
		this.db = new dbutils();
		this.Msg = "";
		
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

	public void setTtcp(String ttcp)
	{
		this.ttcp = ttcp;
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
	
	public ResultSet getRsCcdc()
	{
		return CcdcList;
	}
	
	public void setRsCcdc(ResultSet RsCcdc)
	{
		this.CcdcList = RsCcdc;
	}
	
	public ResultSet getRsLoaiCCDC()
	{
		return this.LccdcList;
	}
	
	public void setRsLoaiCCDC(ResultSet LccdcList)
	{
		this.LccdcList = LccdcList;
	}

	public String getLoaiCCDC()
	{
		return this.loaiCCDC;
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

	public void setLccdcId(String lccdcId)
	{
		this.LccdcId = lccdcId;
	}
	
	public String getLccdcId()
	{
		return this.LccdcId;
	}

	public void setLccdcTen(String LccdcTen)
	{
		this.LccdcTen = LccdcTen;
	}
	
	public String getLccdcTen()
	{
		return this.LccdcTen;
	}
	
	public ResultSet getRsCdccdc()
	{

		return this.cdccdcRs;
	}

	public void setRsCdccdc(ResultSet cdccdcRs)
	{

		this.cdccdcRs = cdccdcRs;
	}


	public void setCdccdcIds(String[] cdccdcIds)
	{

		this.cdccdcIds = cdccdcIds;
	}

	public String[] getCdccdcIds()
	{

		return cdccdcIds;
	}
	
	
	public ResultSet getRsTtcp()
	{

		return this.ttcpRs;
	}

	public void setRsTtcp(ResultSet ttcpRs)
	{

		this.ttcpRs = ttcpRs;
	}


	public void setTtcpIds(String[] ttcpIds)
	{

		this.ttcpIds = ttcpIds;
	}

	public String[] getTtcpIds()
	{

		return ttcpIds;
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
	
	public String getNccdcId()
	{
		return NccdcId;
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
	
	public void setNccdcId(String NccdcId)
	{
		this.NccdcId = NccdcId;
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
	
	public ResultSet getRsNccdc()
	{
		return NccdcList;
	}
	
	public void setRsNccdc(ResultSet rsNccdc)
	{
		this.NccdcList = rsNccdc;
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
						" FROM ERP_CONGCUDUNGCU_CHITIET " +
						" WHERE CCDC_FK = '"+ this.Id + "'";
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
				query = "SELECT COUNT(CONGDUNG_FK) AS NUM FROM ERP_CONGCUDUNGCU_CONGDUNG WHERE CCDC_FK = " + this.Id + " ";
			
				ResultSet rs = this.db.get(query);
				rs.next();
				
				query = "";
				if(rs.getString("NUM").equals("0")){ // nghia la cong dung chua duoc luu trong ERP_CCDC_CONGDUNG
					
					if(this.NccdcId.trim().length() >0)
					{
						query = 	"SELECT CD.PK_SEQ AS CDCCDCID, CD.TEN AS DIENGIAI, 0 AS PHANTRAM \n" +
									"FROM ERP_NHOMCCDC_CONGDUNG NTS_CD \n" +
									"INNER JOIN ERP_CONGDUNGCCDC CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK \n" +
									"WHERE NHOMCCDC_FK = " + this.NccdcId + " ORDER BY CD.PK_SEQ \n";					
					}
						
					
				}else{
					
					query = "SELECT CD.PK_SEQ AS CDCCDCID, CD.TEN AS DIENGIAI, CCDC_CD.PHANTRAM  \n" +
							"FROM ERP_CONGCUDUNGCU_CONGDUNG CCDC_CD \n" +
							"INNER JOIN ERP_CONGDUNGCCDC CD ON CD.PK_SEQ = CCDC_CD.CONGDUNG_FK \n" +
							"WHERE CCDC_CD.CCDC_FK = " + this.Id + " ORDER BY CD.PK_SEQ \n";
					
					
					
				}
				rs.close();

				System.out.println("getChoncongdung " + query);
				
				if(query.trim().length()>0)
					return this.db.get(query);
				else
					return null;
				
				
			}catch(java.sql.SQLException e){ return null;}

		}else{
			if(this.NccdcId.length() > 0){
				query = 	"SELECT CD.PK_SEQ AS CDCCDCID, CD.TEN AS DIENGIAI, 0 AS PHANTRAM \n" +
							"FROM ERP_NHOMCCDC_CONGDUNG NTS_CD \n" +
							"INNER JOIN ERP_CONGDUNGCCDC CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK \n" +
							"WHERE NHOMCCDC_FK = " + this.NccdcId + " ORDER BY CD.PK_SEQ ";
			
				if(query.trim().length()>0)
					return this.db.get(query);
				else
					return null;
				
			}else{
				return null;
			}
		}
		
		
	}
	

	public ResultSet getChoncongdungthem(){
		String query = "";
		if(this.Id.length() > 0){
		
			if(this.NccdcId.trim().length()>0)
			{
				query = 	"SELECT PK_SEQ AS CDCCDCID, TEN AS DIENGIAI " +
							"FROM ERP_CONGDUNGCCDC WHERE PK_SEQ NOT IN (" +
							"SELECT CD.PK_SEQ " +
							"FROM ERP_NHOMCCDC_CONGDUNG NTS_CD " +
							"INNER JOIN ERP_CONGDUNGCCDC CD ON CD.PK_SEQ = NTS_CD.CONGDUNG_FK " +
							"WHERE NHOMCCDC_FK = " + this.NccdcId + ") ORDER BY PK_SEQ ";
			}
			
			if(query.trim().length()>0)
				return this.db.get(query);
			else
				return null;

		}else{
			query = 	"SELECT PK_SEQ AS CDCCDCID, TEN AS DIENGIAI " +
						"FROM ERP_CONGDUNGCCDC ORDER BY PK_SEQ ";
			
			return this.db.get(query);
		}
				
	}
	
	
	
	public ResultSet getChonTTCP(){
		String query = "";
		if(this.Id.length() > 0){
			try{
				query = "SELECT (A.NUM1 - B.NUM2) AS NUM, A.NUM1, B.NUM2 " +
						"FROM( " +
						"		SELECT TSCD.PK_SEQ AS TSID, COUNT(NTS_PB.TTCP_FK) AS NUM1 " +
						"		FROM ERP_CONGCUDUNGCU TSCD " +
						"		LEFT JOIN ERP_NHOMCCDC_PHANBOKHAUHAO NTS_PB ON NTS_PB.NHOMCCDC_FK = TSCD.NHOMCCDC_FK " +
						"		WHERE TSCD.PK_SEQ = " + this.Id + " " +
						"		GROUP BY TSCD.PK_SEQ " +
						")A " +
						"LEFT JOIN " +
						"( " +
						"		SELECT TSCD.PK_SEQ AS TSID, COUNT(PB.TTCPNHAN_FK) AS NUM2 " +
						"		FROM ERP_CONGCUDUNGCU TSCD " +
						"		LEFT JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TSCD.TTCP_FK = PB.TTCPCHO_FK AND TSCD.PK_SEQ = PB.CCDC_FK " +
						"		WHERE TSCD.PK_SEQ = " + this.Id + " " +
						"		GROUP BY TSCD.PK_SEQ " +
						")B ON A.TSID = B.TSID " +
						"WHERE A.TSID = " + this.Id + " " ;	
					
				System.out.println(query);
				
				ResultSet rs = this.db.get(query);
				
				rs.next();
				
				if(!rs.getString("NUM1").equals("0") & !rs.getString("NUM2").equals("0")){ 
					// nghia la da luu trong ERP_TRUNGTAMCHIPHI_PHANBO
					query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, PB.PHANTRAM " +
								"FROM ERP_CONGCUDUNGCU TSCD " +
								"LEFT JOIN ERP_TRUNGTAMCHIPHI_PHANBO PB ON TSCD.TTCP_FK = PB.TTCPCHO_FK AND TSCD.PK_SEQ = PB.CCDC_FK " + 
								"LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCPNHAN_FK " +
								"WHERE TSCD.PK_SEQ =  " + this.Id + " " ;

				}else{ //nghia la chua duoc luu trong ERP_TRUNGTAMCHIPHI_PHANBO

					query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, 0 AS PHANTRAM " +
								"FROM ERP_NHOMCCDC_PHANBOKHAUHAO PB " +
								"LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK " +
								"WHERE NHOMCCDC_FK = (SELECT NHOMCCDC_FK FROM ERP_CONGCUDUNGCU WHERE PK_SEQ = " + this.Id + ") ORDER BY TTCP.PK_SEQ ";
				}
				
				System.out.println("getChonTTCP " + query);
				rs.close();
				return this.db.get(query);
				
			}catch(java.sql.SQLException e){
				return null;
			}
		
		}else{
			if(this.NccdcId.length() > 0){
				query = 	"SELECT TTCP.PK_SEQ AS TTCPID, TTCP.MA, TTCP.DIENGIAI, 0 AS PHANTRAM \n" +
							"FROM ERP_NHOMCCDC_PHANBOKHAUHAO PB \n" +
							"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK \n" +
							"WHERE NHOMCCDC_FK = " + this.NccdcId + " ORDER BY TTCP.PK_SEQ \n";
				System.out.println("lay trung tam chi phi phan bo khau hao:\n" + query + "\n---------------------------------");
				return this.db.get(query);
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
						"FROM ERP_NHOMCCDC_PHANBOKHAUHAO PB " +
						"INNER JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = PB.TTCP_FK " +
						"WHERE NHOMCCDC_FK = " + this.NccdcId + " ) ORDER BY PK_SEQ ";
			
			return this.db.get(query);

		}else{
			query = 	"SELECT PK_SEQ AS TTCPID, MA, DIENGIAI "+
						"FROM ERP_TRUNGTAMCHIPHI ORDER BY PK_SEQ";
			
			return this.db.get(query);
		}
		
	}
	
	public ResultSet getChonkhoanmucchiphi(){
		String query = "";
		
		query = 	" SELECT NCP.PK_SEQ AS NCPID,TK.SOHIEUTAIKHOAN+'-'+ TEN +'-'+ DIENGIAI AS DIENGIAI,0 AS PHANTRAM " +
		" FROM ERP_NHOMCHIPHI NCP " +
		" INNER JOIN ERP_TAIKHOANKT TK ON TK.SOHIEUTAIKHOAN = NCP.TAIKHOAN_FK AND TK.NPP_FK=1" +
		" WHERE NCP.TRANGTHAI = 1 AND NCP.CONGTY_FK = "+this.ctyId +
		" ORDER BY NCP.PK_SEQ ";

		System.out.println("Get KMCP List : " + query);
		return this.db.getScrol(query);
		
		
	}
	
	public String getCdIdsList(){
		String tmp = "";
		if(this.cdccdcIds != null){
			for(int i = 0; i < this.cdccdcIds.length; i++){
				tmp = tmp +  this.cdccdcIds[i] + ";" ;
			}
		}
		return tmp;
	}


	public String getTtcpIdsList(){
		String tmp = "";
		if(this.ttcpIds != null){
			for(int i = 0; i < this.ttcpIds.length; i++){
				tmp = tmp +  this.ttcpIds[i] + ";" ;
			}
		}
		return tmp;
	}
	
	public boolean isKhauhao(String tsId){
		boolean result = false;
		String query = " SELECT isnull(khauhao.num,0) + ISNULL(SOTHANGDAKHAUHAO,0) AS NUM \n"
			+ "	FROM ERP_CONGCUDUNGCU CCDC  \n"
			+ "	left join  \n"
			+ "	( \n"
			+ "	select CCDC_FK, COUNT(*) as num from ERP_KHAUHAOCCDC \n"
			+ "	where CCDC_FK="
			+ tsId
			+ " \n"
			+ "	group by CCDC_FK \n"
			+ "	) khauhao on CCDC.pk_seq=khauhao.CCDC_FK \n"
			+ "	WHERE CCDC.pk_seq = " + tsId + " \n";
		System.out.println(query);
		
		ResultSet rs = this.db.get(query);
		try{
			rs.next();
			int tmp = rs.getInt("NUM");
			rs.close();
			
			if(tmp==0){
				return true;
			}else{
				return false;
			}
			
		}catch(java.sql.SQLException e){}
		
		return result;
	}
	
	public boolean themmoiMa(HttpServletRequest request)
	{
		String query = "";
		int thang = 0;
		int nam = 0;
		int sodong = 0;
		ResultSet rs = null;
		boolean error = false;
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

	

		if(this.ThangbatdauKH.length() == 10){
			thang = Integer.valueOf(this.ThangbatdauKH.substring(5, 7));
			nam = Integer.valueOf(this.ThangbatdauKH.substring(0, 4));

			query =" Select count(PK_SEQ) as sodong From Erp_KhoaSoThang Where " + " " + thang + " IN ( Select ThangKS From Erp_KhoaSoThang Where Nam=" + nam + ")";
			rs = this.db.get(query);
		}
			
		
		try
		{

			if (rs != null)
			{
				while (rs.next())
				{
					sodong = rs.getInt("sodong");
				}
				rs.close();
				if (sodong > 0)
				{
					System.out.println("So dong " + sodong);
					this.setMsg("TMM1.1 Vui lòng chọn lại tháng bắt đầu khấu hao vì tháng " + thang + " năm " + nam + " đã khóa sổ rồi!");
					error = true;
				}
			}

			if(!error){
				this.db.getConnection().setAutoCommit(false);

			
				query =	"INSERT INTO ERP_CONGCUDUNGCU(ma, diengiai, loaiCCDC_fk, congty_fk, dvth_fk, dongia, thanhtien, sothangKH, thangbatdauKH, " +
						"trangthai, nguoitao, nguoisua, ngaytao, ngaysua, soLuong) " +
						"VALUES ( '" +this.Ma +"', N'" +this.Diengiai +"',"+this.LccdcId +",'"+this.ctyId +"',"  + this.DvthId + ", '0', '0', '" + this.SothangKH + "', " +
						"'" + this.ThangbatdauKH + "', '0', " + this.Nguoitao + ", " + this.Nguoisua + ", '" + this.getDateTime() + "', " +
						"'" + this.getDateTime() + "', 1 " +
						" )";
			
				System.out.println("them moi tai san: " + query);
					
				if (this.db.update(query)){
						
					query = "SELECT SCOPE_IDENTITY() AS ID";
					rs = this.db.get(query);
					rs.next();
						
					this.Id = rs.getString("ID");

					for (int i=0;i<phanBoList.size();i++)
					{
						IPhanBo phanBo = phanBoList.get(i);
						query = "INSERT INTO Erp_CongCuDungCu_KhoanMucChiPhi(CCDC_FK, NHOMCHIPHI_FK, PHANTRAM) VALUES (" + this.Id + ", " + phanBo.getKhoanMucId() + ", " + phanBo.getPhanTram() + " )";
						
						System.out.println(query);
						if(!this.db.update(query))
						{
							this.Msg = "4.Error: " + query;
							this.db.getConnection().rollback();
							return false;
						}
					}

				
//					this.CapnhatChovaNhan();
				
					String[] param = new String[1];
					param[0] = this.Id;
//					String result = this.db.execProceduce2("KeHoachPhanBo", param);
//					if (result.trim().length() > 0)
//					{
//						this.setMsg("TMM1.2 Không thể tạo mới tài sản cố định \n" + result);
//						this.db.getConnection().rollback();
//						return false;
//					}
//					this.Msg = "Dữ liệu đã được lưu thành công";
					
//					if(this.Khauhao()){
//						this.Msg = "Dữ liệu đã được lưu thành công";
//					}

					this.db.getConnection().commit();
					this.db.getConnection().setAutoCommit(true);
					
					return true;
				}else
				{
					this.setMsg("TMM1.3 Không thể tạo mới tài sản cố định " + query);
					this.db.getConnection().rollback();
					return false;
				}
				
			}else{
				return false;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.setMsg("TMM1.4 Không thể tạo mới tài sản cố định " + query);
			return false;
		}
	
		
	}
	
	public boolean UpdateMa(HttpServletRequest request)
	{
		String query = "";
		int thang = 0;
		int nam = 0;
		int sodong = 0;
		ResultSet rs = null;
		boolean error = false;
		
		Utility util = new Utility();
		try
		{
			
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
		
			query = "SELECT COUNT(THANG) AS NUM FROM ERP_CONGCUDUNGCU_CHITIET WHERE CCDC_FK = " + this.Id + " AND KHAUHAOTHUCTE > 0";
			
			rs = this.db.get(query);
			rs.next();
			
			if(Double.parseDouble(this.SothangKH) < rs.getInt("NUM")){
				this.Msg = "UDM1.1 Số tháng khấu hao dự kiến phải lớn hơn số tháng khấu hao đã thực hiện trên công cụ dụng cụ";
				error = true;
			}
			
			
			if(this.ThangbatdauKH.length() == 10){
				thang = Integer.valueOf(this.ThangbatdauKH.substring(5, 7));
				nam = Integer.valueOf(this.ThangbatdauKH.substring(0, 4));

				query =	"Select count(PK_SEQ) as sodong From Erp_KhoaSoThang Where " + " " + thang + " " +
						"IN (Select ThangKS From Erp_KhoaSoThang Where Nam = " + nam + ")";
				rs = this.db.get(query);
			}
		
			if (rs != null)
			{
				while (rs.next())
				{
					sodong = rs.getInt("sodong");
				}
				rs.close();
				if (sodong > 0)
				{
					System.out.println("So dong " + sodong);
					this.setMsg("UDM1.2 Vui lòng chọn lại tháng bắt đầu khấu hao vì tháng " + thang + " năm " + nam + " đã khóa sổ rồi!");
					error = true;
				}
			}
			
			if(!error){
				query = "UPDATE Erp_CONGCUDUNGCU SET ma = '" + this.Ma + "', diengiai = N'" + this.Diengiai + "',  sothangKH = '" + this.SothangKH + "', " +
						"thangbatdauKH = '" + this.ThangbatdauKH + "', dvth_fk = '" + this.DvthId + "', " +
						"nguoitao = '" + this.Nguoitao + "'," + " nguoisua = '" + this.Nguoisua + "', ngaytao = '" + this.Ngaytao + "', ngaysua = '" + this.Ngaysua + "', loaiccdc_fk =" +this.LccdcId + " "+   
						" WHERE pk_seq = '" + this.Id + "' ";
					
				System.out.println("1.Update: " + query);
			
				if (!db.update(query))
				{

				
					this.Msg = "4.Error: " + query;
					this.db.getConnection().rollback();
					return false;

				}
				query="DELETE Erp_CongCuDungCu_KhoanMucChiPhi WHERE CCDC_FK ="+this.Id+" ";
				if(!this.db.update(query))
				{
					this.Msg = "4.Error: " + query;
					this.db.getConnection().rollback();
					return false;
				}
				for (int i=0;i<phanBoList.size();i++)
				{
					IPhanBo phanBo = phanBoList.get(i);
					query = "INSERT INTO Erp_CongCuDungCu_KhoanMucChiPhi(CCDC_FK, NHOMCHIPHI_FK, PHANTRAM) VALUES (" + this.Id + ", " + phanBo.getKhoanMucId() + ", " + phanBo.getPhanTram() + " )";
					
					System.out.println(query);
					if(!this.db.update(query))
					{
						this.Msg = "4.Error: " + query;
						this.db.getConnection().rollback();
						return false;
					}
				}
				return true;
			}else{
				return false;
			}
			
		}
		catch (Exception e)
		{
			e.printStackTrace();
			this.setMsg(this.Msg + "UDM1.7 Không thể tạo cập nhật tài sản cố định " + query);
			return false;
		}
		
	}

	private void CapnhatChovaNhan(){
		String query;
		// Cap nhat lai NHANPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 0 WHERE PK_SEQ IN ( " +
				"SELECT PK_SEQ " +
				"FROM ERP_TRUNGTAMCHIPHI TTCP " +
				"WHERE NHANPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE CCDC_FK IS NOT NULL) )" ;
		System.out.println(query);
		this.db.update(query);
		
		// Cap nhat lai NHANPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET NHANPHANBO = 1 " +
				"WHERE PK_SEQ IN  (SELECT TTCPNHAN_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0  AND CCDC_FK IS NOT NULL) " ;
		System.out.println(query);
		this.db.update(query);

		// Cap nhat lai CHOPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 0 WHERE PK_SEQ IN ( " +
				"SELECT PK_SEQ " +
				"FROM ERP_TRUNGTAMCHIPHI TTCP " +
				"WHERE CHOPHANBO = 1 AND PK_SEQ NOT IN (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO  WHERE CCDC_FK IS NOT NULL) )" ;
		System.out.println(query);
		this.db.update(query);
		
		// Cap nhat lai CHOPHANBO cho dung
		query = "UPDATE ERP_TRUNGTAMCHIPHI SET CHOPHANBO = 1 " +
				"WHERE PK_SEQ IN  (SELECT TTCPCHO_FK FROM ERP_TRUNGTAMCHIPHI_PHANBO WHERE PHANTRAM > 0 AND CCDC_FK IS NOT NULL) " ;
		System.out.println(query);
		this.db.update(query);

	}
	public void Delete(String id) 
	{		
		
		String query = "SELECT TRANGTHAI FROM ERP_CONGCUDUNGCU WHERE PK_SEQ = " + id + " "; 
		
		System.out.println(query);
		ResultSet rs = this.db.get(query);
		
		try{
			rs.next();
			// Chua thuc hien khau hao thuc te
			if(rs.getString("TRANGTHAI").equals("0")){
				rs.close();
				
					query = "DELETE FROM ERP_CONGCUDUNGCU_CHITIET WHERE CCDC_FK = " + id + "";
					System.out.println(query);
					
					this.db.update(query);
					
					
					query = "DELETE FROM Erp_CongCuDungCu_KhoanMucChiPhi WHERE CCDC_FK = " + id + "";
					System.out.println(query);
					this.db.update(query);

//					query = "DELETE FROM ERP_CONGCUDUNGCU_CONGDUNG WHERE CCDC_FK = " + id + "";
//					System.out.println(query);
//
//					this.db.update(query);
//					
					 
					/*query = "DELETE FROM ERP_TRUNGTAMCHIPHI_PHANBO " +
							"WHERE TTCPCHO_FK IN (SELECT TTCP_FK FROM ERP_CONGCUDUNGCU WHERE PK_SEQ = " + id + ")";
					System.out.println(query);
					this.db.update(query);

					
					query = "DELETE FROM ERP_TRUNGTAMCHIPHI " +
							"WHERE PK_SEQ IN (SELECT TTCP_FK FROM ERP_CONGCUDUNGCU WHERE PK_SEQ = " + id + ")";
					System.out.println(query);
					*/
					this.db.update(query);

					query = "DELETE FROM ERP_CONGCUDUNGCU WHERE PK_SEQ = " + id + "";
					System.out.println(query);
					
					this.db.update(query);
					
//					this.CapnhatChovaNhan();
				  
			}			
			
		}catch(java.sql.SQLException e){}
		
	}

	
	public void init(String sql)
	{
		String query = "";
		if(this.Id.length() == 0){
			if (sql.length() > 0)
			{
				query = sql;
				this.CcdcList = this.db.get(query);
			}
			else
			{
				query =	"SELECT CCDC.PK_SEQ, CCDC.MA, CCDC.DIENGIAI, CCDC.TRANGTHAI,CCDC.LOAICCDC_FK AS LOAICCDC_FK, \n" +
						"ISNULL((DVTH.MA+'-'+DVTH.TEN),'') AS DVTH,(LCCDC.MA+'-'+LCCDC.DIENGIAI) AS LOAICCDC, \n" +
						"CCDC.SOTHANGKH, CCDC.THANGBATDAUKH,CCDC.DVTH_FK AS DVTH_FK \n" +
						"FROM ERP_CONGCUDUNGCU CCDC \n" +
						"LEFT JOIN ERP_NHOMCCDC NCCDC ON NCCDC.PK_SEQ = CCDC.NHOMCCDC_FK \n" +
						"LEFT JOIN ERP_DONVITHUCHIEN  DVTH ON CCDC.DVTH_FK = DVTH.PK_SEQ \n" +
						"LEFT JOIN ERP_LOAICCDC  LCCDC ON CCDC.LOAICCDC_FK = LCCDC.PK_SEQ \n" +
						"WHERE CCDC.CONGTY_FK = " + this.ctyId + " ORDER BY CCDC.MA ";

				System.out.println("ds ccdc: \n" + query + "\n------------------------------------");
				this.CcdcList = this.db.get(query);
			}
		}else{
			String[] param = new String[1];
			param[0] = this.Id;
//			String result = this.db.execProceduce2("KeHoachPhanBo", param);
//			this.Khauhao(); // Cap nhat lai ke hoach khau hao du kien
			this.Msg = "";
			 query = 	"SELECT CCDC.PK_SEQ, CCDC.MA, CCDC.DIENGIAI, ISNULL(CCDC.DONVI, '') AS DONVI,CCDC.LOAICCDC_FK as LOAICCDC_FK, \n" +
						"       ISNULL(CCDC.SOTHANGKH,0) AS SOTHANGKH, CCDC.THANGBATDAUKH, CCDC.TRANGTHAI, CCDC.NGUOITAO, CCDC.NGUOISUA, \n" +
						"       CCDC.NGAYTAO, CCDC.NGAYSUA, CCDC.SOLUONG, CCDC.NHOMCCDC_FK, \n" +
						"       TTCP.PK_SEQ AS TTCP, CCDC.LOAICCDC_FK AS LCCDCID,CCDC.DVTH_FK as DVTH_FK \n" +
						"		, CCDC.DONGIA + isNull((select sum(GIATRI) as DONGIA from ERP_CONGCUDUNGCU_DIEUCHINH where CCDC_FK = " + this.Id + " group by CCDC_FK), 0) as DONGIA\n" +
						"		, CCDC.SOLUONG * (CCDC.DONGIA + isNull((select sum(GIATRI) as DONGIA from ERP_CONGCUDUNGCU_DIEUCHINH where CCDC_FK = " + this.Id + " group by CCDC_FK), 0)) as THANHTIEN\n" +
						"FROM ERP_CONGCUDUNGCU CCDC \n" +
						"     LEFT JOIN ERP_TRUNGTAMCHIPHI TTCP ON TTCP.PK_SEQ = CCDC.TTCP_FK \n" +
						"WHERE CCDC.PK_SEQ = '" + this.Id + "' ";
			System.out.println(query);
			
			ResultSet rs = db.get(query);
			try
			{
				if (rs.next())
				{
					this.Ma = rs.getString("ma");
					this.Diengiai = rs.getString("diengiai");
					this.NccdcId = rs.getString("nhomCCDC_fk")== null ? "" : rs.getString("nhomCCDC_fk");
					this.LccdcId = rs.getString("LCCDCID");
					System.out.println("Chạy tới đây");
					this.ttcp = rs.getString("ttcp")== null ? "" : rs.getString("ttcp");
					this.Dvt = rs.getString("donvi");
					this.SothangKH = rs.getString("sothangKH");
					this.ThangbatdauKH = rs.getString("thangbatdauKH")== null ? "" : rs.getString("thangbatdauKH");
					this.Trangthai = rs.getString("trangthai");
					this.Nguoitao = rs.getString("nguoitao");
					this.Nguoisua = rs.getString("nguoisua");
					this.Ngaytao = rs.getString("ngaytao");
					this.Ngaysua = rs.getString("ngaysua");
					this.ThanhTien = rs.getString("ThanhTien");
					this.SoLuong = rs.getString("SoLuong");
					this.DonGia = rs.getString("DonGia");
					this.LccdcId=rs.getString("LOAICCDC_FK")==null?"":rs.getString("LOAICCDC_FK");
					this.DvthId=rs.getString("DVTH_FK")==null?"":rs.getString("DVTH_FK");
					System.out.println("don gia cc: " + DonGia);
					System.out.println("don gia cc: " + rs.getString("DonGia"));
					
					
				}
				if (rs != null)
				{
					rs.close();
				}
				query="SELECT NHOMCHIPHI_FK KHOANMUCCHIPHI_FK, ISNULL(PHANTRAM,0) AS PHANTRAM FROM ERP_CONGCUDUNGCU_KHOANMUCCHIPHI WHERE CCDC_FK = "+this.Id+"";
				ResultSet rsKMPB=db.get(query);
				while(rsKMPB.next())
				{
					IPhanBo phanBo=new PhanBo();
					phanBo.setKhoanMucId(rsKMPB.getString("KHOANMUCCHIPHI_FK")==null?"":rsKMPB.getString("KHOANMUCCHIPHI_FK"));
					phanBo.setPhanTram(rsKMPB.getString("PHANTRAM"));
					this.phanBoList.add(phanBo);
				}
				rsKMPB.close();
			}
			catch (Exception er)
			{
				er.printStackTrace();
				this.Msg = "Loi" + er.toString();
			}
			
			query = " SELECT LOAICHUNGTU,SOCHUNGTU,NGAYDIEUCHINH,ISNULL(GIATRI,0) AS GIATRI,ISNULL(SOTHANG,0) AS SOTHANG " +
					"FROM ERP_CONGCUDUNGCU_DIEUCHINH where CCDC_FK ="+ this.Id;
				dieuChuyenCCDCRs = db.get(query);			
		}
		
		System.out.println("init " + query);
	
	}
	
	public void createRs()
	{
		
/*		String query = "SELECT * FROM ERP_CONGDUNGCCDC WHERE TRANGTHAI = '1'";
		this.CdList = this.db.get(query);*/
			
		String  query = "SELECT * FROM ERP_NHOMCCDC WHERE  TRANGTHAI = '1'  ORDER BY MA";
		this.NccdcList = this.db.get(query);
		
		if(this.NccdcId.trim().length() > 0)
		{
			query = " SELECT lccdc.PK_SEQ, lccdc.DIENGIAI  "
				  + " FROM ERP_NHOMCCDC nccdc inner join Erp_LOAICCDC lccdc on nccdc.LOAICCDC_FK = lccdc.PK_SEQ "
				  + " WHERE nccdc.PK_SEQ = "+ this.NccdcId +" ";
		
			ResultSet rs = db.get(query);
			try
			{
				if(rs!= null)
				{
					while(rs.next())
					{
						this.LccdcId = rs.getString("PK_SEQ");
						this.LccdcTen = rs.getString("DIENGIAI");
					}
					rs.close();
				}
			}
		    catch(Exception e)
			{
		    	e.printStackTrace();
			}
			
		}
		
//		query = "SELECT PK_SEQ, TEN FROM ERP_PHUONGPHAPKHAUHAO ";
//		this.PPKHrS = this.db.get(query);
		
		query = "SELECT pk_seq,MA +'-' + diengiai  as TEN FROM ERP_LOAICCDC  WHERE TRANGTHAI = '1' ";
	
		this.LccdcList = this.db.get(query);
		System.out.println("asdsad"+query);
		
	/*	query = "select PK_SEQ, MA +'-' + DIENGIAI  as TEN from ERP_TRUNGTAMCHIPHI WHERE TRANGTHAI = 1  and congty_fk="+this.ctyId+"  ORDER BY MA ";
		this.ttcpRs = db.get(query);
		System.out.println(query);*/
		
		query="select pk_seq,MA +'-' + ten  as TEN from erp_donvithuchien where trangthai=1 and congty_fk="+this.ctyId+" ";
		this.DvthList=db.get(query);
		System.out.println("Don vi thuc hien:"+query);
		
		
		
		/*System.out.println("NccdcId "+ NccdcId);
		if(this.NccdcId.trim().length()>0){
			query = "SELECT loaiCCDC_fk FROM ERP_NHOMCCDC  WHERE PK_SEQ = '"+this.NccdcId+"'";
			CCDC.PK_SEQ
			System.out.println(" query: "+ query);
			ResultSet rs = this.db.get(query);
			try{
			if(rs != null){
				rs.next();
				this.loaiCCDC = rs.getString("loaiCCDC_fk");
			}
			}
			catch (Exception e) {
				e.printStackTrace();
			}
		}*/
		
	}
	
	public ResultSet Laykhauhao(){
		return this.db.get("SELECT * FROM ERP_CONGCUDUNGCU_CHITIET WHERE CCDC_FK = '" + this.Id + "'"); 
	}
	
	public boolean Khauhao(){
		if(this.Id.length() == 0){
			return false;
		}else{
//			UpdateMa();
			String query = 	" SELECT CCDC.THANHTIEN, CCDC.SOTHANGKH, ISNULL(THANGDAKHAUHAO.THANG, 0) AS THANGDAKHAUHAO, " +
					        " (CCDC.SOTHANGKH - CONVERT(INT, ISNULL(THANGDAKHAUHAO.THANG, 0)) ) AS THANGCONKH, " +
					        " ISNULL(SOTHANGDAKHAUHAO.SOTHANGDAKHAUHAO, 0) AS SOTHANGDAKHAUHAO, " +
					        " ISNULL(TIENDAKHAUHAO.TIENDAKHAUHAO,0) as TIENDAKHAUHAO " +
							" FROM ERP_CONGCUDUNGCU CCDC " +
							" LEFT JOIN " +
							"( " +
							"	SELECT TOP 1 THANG, CCDC_FK " +
							"	FROM ERP_CONGCUDUNGCU_CHITIET " +
							"	WHERE KHAUHAOTHUCTE > 0 " +
							"	AND CCDC_FK = '" + this.Id + "'  " +
							"   ORDER BY THANG DESC " +
							")THANGDAKHAUHAO ON THANGDAKHAUHAO.CCDC_FK = CCDC.PK_SEQ " +
							"LEFT JOIN " +
							"( " +
							"	SELECT CCDC_FK, COUNT(KHAUHAOTHUCTE) AS SOTHANGDAKHAUHAO " +
							"	FROM ERP_CONGCUDUNGCU_CHITIET " +
							"	WHERE CCDC_FK = '" + this.Id + "' AND KHAUHAOTHUCTE > 0 " +
							"	GROUP BY CCDC_FK " +
							")SOTHANGDAKHAUHAO ON SOTHANGDAKHAUHAO.CCDC_FK =  CCDC.PK_SEQ  " +

							"LEFT JOIN " +
							"( " +
							"	SELECT CCDC_FK, SUM(KHAUHAOTHUCTE) AS TIENDAKHAUHAO " +
							"	FROM ERP_CONGCUDUNGCU_CHITIET " +
							"	WHERE CCDC_FK = '" + this.Id + "' AND KHAUHAOTHUCTE > 0 " +
							"	GROUP BY CCDC_FK " +
							")TIENDAKHAUHAO ON TIENDAKHAUHAO.CCDC_FK =  CCDC.PK_SEQ  " +

							"WHERE CCDC.PK_SEQ = '" + this.Id + "' ";
			System.out.println(query);
			
			ResultSet rs = this.db.get(query);
			try{
				if(rs != null){
			
					rs.next();
					double thanhtien = Double.parseDouble(rs.getString("THANHTIEN"));
					double tiendaKH = Double.parseDouble(rs.getString("TIENDAKHAUHAO"));
					int thangdaKH = rs.getInt("THANGDAKHAUHAO"); 
					int sothangKH = Integer.parseInt(rs.getString("SOTHANGKH"));
					int sothangdaKH = Integer.parseInt(rs.getString("SOTHANGDAKHAUHAO"));
					
					if(thanhtien > 0 & sothangKH >0 ){ 											
						if(thangdaKH == 0){ 											// nghia la chua co ke hoach khau hao
							System.out.println("Thuc hien tao moi khau hao du kien");
							this.Tinhkhauhao_New(sothangKH, thanhtien);
						}else{															// da co ke hoach khau hao roi, neu cap nhat lai	
							System.out.println("Thuc hien update khau hao du kien");
							this.Tinhkhauhao_Update(sothangKH, thangdaKH, sothangdaKH, thanhtien, tiendaKH);
						}
					}
					rs.close();
					
				}
			}catch(java.sql.SQLException e){
				System.out.println("Error : "+e.toString());
			}
		
			return true;
		}

	}
	
	private void Tinhkhauhao_New(int n,  double thanhtien){
		int month = 1;

		double[] khdkVal = new double[n];
		double[] lkdkVal = new double[n];
		double[] gtdkVal = new double[n];
		
		lkdkVal[0] = 0;

		String query = "DELETE FROM ERP_CONGCUDUNGCU_CHITIET WHERE CCDC_FK = " + this.Id + " AND KHAUHAOTHUCTE = 0";
		System.out.println(query);
		this.db.update(query);

		for(int i = 0; i < n; i++)
		{
		
//			String m, y;
//			String thang = "";
								
			if(i == (n - 1))
			{
				 if(i > 1)
					 khdkVal[i] = thanhtien - lkdkVal[i-1];
				 else
					 khdkVal[i] = thanhtien;

				if(i > 1) 
					lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
				else
					lkdkVal[i] = khdkVal[i] ;

			 	gtdkVal[i] = thanhtien - lkdkVal[i];
			}
			else
			{
			 	khdkVal[i] = Math.round(thanhtien/n);
				if(i > 0) 
					lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
				else
					lkdkVal[i] = khdkVal[i] ;
			 	
			 	gtdkVal[i] = thanhtien-lkdkVal[i];
			}					
			 
			query = "INSERT INTO ERP_CONGCUDUNGCU_CHITIET (CCDC_FK, THANG, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, " +
					"KHAUHAOTHUCTE, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE) " +
					"VALUES( '" + this.Id + "', '" + month + "'," + khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", '0', '0', '0')";
			 
			System.out.println("khauhao_taomoi: " + query);
			this.db.update(query);
			month++;
		}

	}

	private boolean Tinhkhauhao_Update(int sothangKH, int thangdaKH,  int sothangdaKH, double thanhtien, double tiendaKH){
		
		double sotienconlai = thanhtien;
		double luykebandau = tiendaKH;
		double conlaibandau = thanhtien - tiendaKH;
		int n = sothangKH;
		double[] khdkVal = new double[n + 1];
		double[] lkdkVal = new double[n + 1];
		double[] gtdkVal = new double[n + 1];
		
		String query = "DELETE FROM ERP_CONGCUDUNGCU_CHITIET WHERE CCDC_FK = " + this.Id + " AND THANG > " + thangdaKH + " AND KHAUHAOTHUCTE = 0";
		System.out.println(query);
		this.db.update(query);
		

		try{

			query = "SELECT THANG, CCDC_FK, ISNULL(KHAUHAOTHUCTE, 0) AS KHAUHAOTHUCTE " +
					"FROM ERP_CONGCUDUNGCU_CHITIET " +
					"WHERE KHAUHAOTHUCTE > 0 " +
					"AND CCDC_FK = '" + this.Id + "'  " +
					"ORDER BY THANG ASC " ;
			
			ResultSet rs = this.db.get(query);
			lkdkVal[0] = 0;
			
			gtdkVal[0] = sotienconlai;
			
			double KHThucte = 0;
			int j = 1;
			while(rs.next()){
				KHThucte = rs.getDouble("KHAUHAOTHUCTE");
				System.out.println("j : " + j);
				lkdkVal[j] = lkdkVal[j - 1] + KHThucte;
				gtdkVal[j] = gtdkVal[j - 1] - KHThucte;
				
				query = "UPDATE ERP_CONGCUDUNGCU_CHITIET SET " +
						"KHAUHAODUKIEN = KHAUHAOTHUCTE, " +
						"KHAUHAOLUYKEDUKIEN = " + lkdkVal[j] + ", " +
						"KHAUHAOLUYKETHUCTE = " + lkdkVal[j] + ", " +
						"GIATRICONLAIDUKIEN = " + gtdkVal[j - 1] + " - KHAUHAOTHUCTE, " +
						"GIATRICONLAITHUCTE = " + gtdkVal[j - 1] + " - KHAUHAOTHUCTE " +
						"WHERE CCDC_FK = " + rs.getString("CCDC_FK") + " AND THANG = " + rs.getDouble("THANG") + "";
				System.out.println(query);
				this.db.update(query);
				j++;
				
			}
			rs.close();
			
			//System.out.println("n: " + n);
			int m = n - thangdaKH;
			int month = thangdaKH + 1;
//			int sothangconlai = n - sothangdaKH;
			//System.out.println("m: " + m);
			
			for(int i = 0 ; i < m; i++)
			{
										
				if(i == (m - 1))  // thang cuoi cung
				{		
					if(i > 0)
						khdkVal[i] = thanhtien - lkdkVal[i-1];
					else
						khdkVal[i] = sotienconlai;
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;

					gtdkVal[i] = thanhtien - lkdkVal[i];
				}
				else
				{
					khdkVal[i] = Math.round(conlaibandau/(n - thangdaKH ));
					
					if(i > 0) 
						lkdkVal[i] = khdkVal[i] + lkdkVal[i-1];
					else
						lkdkVal[i] = luykebandau + khdkVal[i] ;
					
					gtdkVal[i] = thanhtien - lkdkVal[i];
				}					
			 
				if(i != (m-1)){
					query = " INSERT INTO ERP_CONGCUDUNGCU_CHITIET (CCDC_FK, THANG, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, " +
							" KHAUHAOTHUCTE, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE) " +
							" VALUES( '" + this.Id + "', '" + month + "', "+ khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", '0', " +
							"" + luykebandau + ", " + conlaibandau + " )";
				}else{
					query = " INSERT INTO ERP_CONGCUDUNGCU_CHITIET (CCDC_FK, THANG, KHAUHAODUKIEN, KHAUHAOLUYKEDUKIEN, GIATRICONLAIDUKIEN, " +
							" KHAUHAOTHUCTE, KHAUHAOLUYKETHUCTE, GIATRICONLAITHUCTE) " +
							" VALUES( '" + this.Id + "', '" + month + "', "+ khdkVal[i] + ", " + lkdkVal[i] + ", " + gtdkVal[i] + ", '0', " +
							"" + khdkVal[i] + ", " + conlaibandau + " )";					
				}
				//System.out.println("khauhao_update " + month + ": " + query);
				this.db.update(query);
				month++;
				}
								
		}catch(java.sql.SQLException e){
			System.out.println("khauhao_update: " + e.toString());
			return false;
		}
		return true;
	}

	

	public void DBClose()
	{
		try
		{
			if (CcdcList != null)
			{
				CcdcList.close();
			}
			if (CdList != null)
			{
				CdList.close();
			}
			if (NccdcList != null)
			{
				NccdcList.close();
			}
			if (db != null)
			{
				db.shutDown();
			}
		}
		catch (Exception er)
		{
		}
	}



	public ResultSet getDvthList() {
		return DvthList;
	}

	public void setDvthList(ResultSet dvthList) {
		DvthList = dvthList;
	}

	public String getDvthId() {
		return DvthId;
	}

	public void setDvthId(String dvthId) {
		DvthId = dvthId;
	}

	public List<IPhanBo> getPhanBoList() {
		return phanBoList;
	}

	public void setPhanBoList(List<IPhanBo> phanBoList) {
		this.phanBoList = phanBoList;
	}

	public ResultSet getDieuChuyenCCDCRs() {
		return dieuChuyenCCDCRs;
	}

	public void setDieuChuyenCCDCRs(ResultSet dieuChuyenCCDCRs) {
		this.dieuChuyenCCDCRs = dieuChuyenCCDCRs;
	}






	
	
}
