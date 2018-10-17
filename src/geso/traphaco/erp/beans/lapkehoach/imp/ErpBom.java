package geso.traphaco.erp.beans.lapkehoach.imp;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import geso.traphaco.erp.beans.danhmucvattu.IDanhmucvattu_SP;
import geso.traphaco.erp.beans.danhmucvattu.imp.Danhmucvattu_SP;
import geso.traphaco.erp.beans.lapkehoach.IErpBom;
import geso.traphaco.erp.beans.lapkehoach.IErpDinhmuc;
import geso.traphaco.erp.beans.phieuxuatkho.ISpDetail;
import geso.traphaco.erp.beans.phieuxuatkho.imp.SpDetail;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

import geso.traphaco.erp.db.sql.dbutils;
import geso.traphaco.erp.servlets.baocao.ReportAPI;

public class ErpBom implements IErpBom
{
	String ctyId;
	String userId;
	String id;
	String hieuluctu;
	String hieulucden;
	String diengiai;
	String trangthai,sudung,dungsai;
	String Spma;
	String tenbom;
	String vanbanhuongdan;
	String donvitinh;
	ResultSet spRs,RsVattu, nlRs,RsNhamay,RsKbsx;
	String NhamayId;
	String KbsxId;
	String noisanxuat;
	String iskhongthoihan;
	String spId;
	double pthaohut;
	//double dien;
	//double nuoc;
	//double hoi;
	String soluongchuan;
	String cpTT;
	String CosudungHC;
	String ChuanNen;
	ResultSet RsChuanNen;
	String TypeId;
	String checktontai;
	String Trongluong;
	ResultSet rsdvld;
	
	List<IDanhmucvattu_SP> dmvtList;
	String msg;
	
	String lspId;
	ResultSet lhhRs;
	
	dbutils db;
	String dvkdId;
	ResultSet rsDvkd;
	
	String chungloaiId;
	ResultSet rsChungloai;
	List<IErpDinhmuc> dinhmucList;
	
	String ngayBH;
	String lanBH;
	String dangbaoche="";
	String quycach="";
	String quytrinhsx="";
	private String ngaybanhanhQTSX;
	private String daychuyensanxuat;
	
	public ErpBom()
	{
		this.Spma="";
		this.ChuanNen="";
		this.ctyId = "";
		this.userId = "";
		this.id = "";
		this.diengiai = "";
		this.tenbom="";
		this.vanbanhuongdan="";
		this.hieuluctu = "";
		this.hieulucden = "";
		this.soluongchuan = "";
		this.trangthai = "";
		this.spId = "";
		this.cpTT = "0";
		this.sudung="1";
		this.lspId = "";
		this.msg = "";
		this.chungloaiId="";
		this.pthaohut=0;
		this.TypeId="0";
		this.Trongluong="";
		this.donvitinh="";
		this.dvkdId="";
		this.dungsai="";
		this.CosudungHC="";
		this.donvitinh="";
		this.ngayBH = "";
		this.lanBH = "";
		this.checktontai = "0";
		this.NhamayId ="";
		this.KbsxId= "";
		this.iskhongthoihan = "";
		this.noisanxuat = "";
		this.ngaybanhanhQTSX = "";
		this.daychuyensanxuat = "";
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		this.db = new dbutils();
	}
	
	public ErpBom(String id)
	{
		this.ctyId = "";
		this.userId = "";
		
		this.NhamayId ="";
		this.KbsxId= "";
		this.id = id;
		this.diengiai = "";
		this.CosudungHC="";
		this.Spma="";
		this.hieuluctu = "";
		this.chungloaiId="";
		this.dvkdId="";
		this.tenbom="";
		this.vanbanhuongdan="";
		this.hieulucden = "";
		this.soluongchuan = "";
		this.trangthai = "";
		this.Trongluong="";
		this.donvitinh="";
		this.pthaohut=0;
		this.spId = "";
		this.TypeId="0";
		this.cpTT = "0";
		this.msg = "";
		this.lspId = "";
		this.sudung="1";
		this.ChuanNen="";
		this.dungsai="";
		this.donvitinh="";
		this.ngayBH = "";
		this.lanBH = "";
		this.noisanxuat = "";
		this.iskhongthoihan = "";
		this.checktontai = "0";
		this.ngaybanhanhQTSX = "";
		this.daychuyensanxuat = "";
		this.dmvtList = new ArrayList<IDanhmucvattu_SP>();
		this.db = new dbutils();
	}
	
	public String getQuytrinhsx() {
		return quytrinhsx;
	}

	public void setQuytrinhsx(String quytrinhsx) {
		this.quytrinhsx = quytrinhsx;
	}
	
	public String getChecktontai() {
		return checktontai;
	}

	public void setChecktontai(String checktontai) {
		this.checktontai = checktontai;
	}

	public String getIskhongthoihan() {
		return iskhongthoihan;
	}

	public void setIskhongthoihan(String iskhongthoihan) {
		this.iskhongthoihan = iskhongthoihan;
	}

	public String getNoisanxuat() {
		return noisanxuat;
	}

	public void setNoisanxuat(String noisanxuat) {
		this.noisanxuat = noisanxuat;
	}

	public String getCtyId() 
	{
		return this.ctyId;
	}

	public void setCtyId(String ctyId) 
	{
		this.ctyId = ctyId;	
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

	public String getNgayBH() 
	{
		return this.ngayBH;
	}

	public void setNgayBH(String ngayBH) 
	{
		this.ngayBH = ngayBH;
	}

	public String getLanBH() 
	{
		return this.lanBH;
	}

	public void setLanBH(String lanBH) 
	{
		this.lanBH = lanBH;
	}

	public void init() 
	{

		String query = 		" select isnull(a.NGAYBANHANHQTSX,'') as NGAYBANHANHQTSX, isnull(a.DAYCHUYENSANXUAT,'') as DAYCHUYENSANXUAT, ISNULL(a.QUYTRINHSANXUAT,'') as QUYTRINHSANXUAT, ISNULL(a.ISKHONGTHOIHAN,'0') AS ISKHONGTHOIHAN ,ISNULL(a.NOISANXUAT,'') AS NOISANXUAT ,ISNULL(A.QUYCACH,'') AS QUYCACH ,  isnull( cast(a.dvdl_fk as varchar(10)),'' ) as dvdl_fk  , isnull(a.tenbom,'') as tenbom, isnull(a.vanbanhuongdan,'') as vanbanhuongdan, " +
							" isnull(c.diengiai,'') as donvitinh, a.pthaohut, isnull( b.dvkd_fk,100004) as dvkdid, " +
							" isnull(a.masanpham,'') as mavattu, isnull( a.soluongchuan, 0) as soluongchuan, a.diengiai, " +
							" isnull( a.hieuluctu, '') as hieuluctu, isnull(a.hieulucden, '') as hieulucden, isnull( a.trangthai,0) as TrangThai," +
							" ISNULL(sudung,0) AS SuDung, isnull(b.loaisanpham_fk,0) as loaisanpham_fk, a.hieuluctu, a.hieulucden, a.ngaybh, a.lanbh, \n " +
							 " CASE WHEN ISNULL((select sum(a.DMVT_FK) from ( \n "
							  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_MUAHANG_SP WHERE DMVT_FK =a.PK_SEQ and MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI not in (2,3,4))	\n "
							  + " UNION ALL	 \n "
							  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_MUAHANG_BOM  WHERE  DANHMUCVATTU_FK =a.PK_SEQ AND MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI not in (2,3,4)) \n "
							  + " UNION ALL \n "
							  + " SELECT DISTINCT 1 AS DMVT_FK FROM ERP_LENHSANXUAT_SANPHAM WHERE  DANHMUCVT_FK =a.PK_SEQ AND LENHSANXUAT_FK  IN (SELECT PK_SEQ FROM ERP_LENHSANXUAT_GIAY WHERE TRANGTHAI not in (6,7))	 )a ),0) =0 THEN 0 ELSE 1 END AS CHECKTONTAI \n " +
							" from ERP_DANHMUCVATTU a left join ERP_SanPham b on a.masanpham = b.ma " +
							" left join DONVIDOLUONG c on a.DVDL_FK = c.PK_SEQ "+
							" where a.PK_SEQ = '" + this.id + "'";
		
		
		 System.out.println(" [init ]:  "+ query);
		
		ResultSet rs = db.get(query);
		 
			try 
			{
				while(rs.next())
				{
					
					this.dvkdId=rs.getString("dvkdid");
					this.tenbom=rs.getString("tenbom");
					this.vanbanhuongdan=rs.getString("vanbanhuongdan");
					this.pthaohut=rs.getDouble("pthaohut");
					this.Spma = rs.getString("mavattu");
					this.soluongchuan = rs.getString("soluongchuan");
					this.diengiai = rs.getString("diengiai");
					this.donvitinh = rs.getString("dvdl_fk");
					this.hieuluctu = rs.getString("hieuluctu");
					this.hieulucden = rs.getString("hieulucden");
					this.trangthai = rs.getString("trangthai");
					this.sudung= rs.getString("sudung");
					this.lspId = rs.getString("loaisanpham_fk");
					this.ngayBH = rs.getString("ngayBH");
					this.lanBH = rs.getString("lanBH"); 
					this.quycach=rs.getString("QUYCACH");
					this.iskhongthoihan=rs.getString("ISKHONGTHOIHAN");
					this.noisanxuat=rs.getString("NOISANXUAT");
					this.checktontai = rs.getString("CHECKTONTAI");
					this.quytrinhsx=rs.getString("QUYTRINHSANXUAT");
					this.ngaybanhanhQTSX = rs.getString("NGAYBANHANHQTSX");
					this.daychuyensanxuat = rs.getString("DAYCHUYENSANXUAT");
				}
				rs.close();
			} 
			catch (Exception e) 
			{
				e.printStackTrace();
				
			}
	 

		List<IDanhmucvattu_SP> dmvtList1 = new ArrayList<IDanhmucvattu_SP>();
		query = " select isnull(a.dungsai,'0') as dungsai, a.vattu_fk as vtId, a.pthaohut,b.MA as vtMa, isnull(b.TEN1 , b.TEN ) as vtTen, \n " +
				" ISNULL(a.isNGUYENLIEUTieuHao,'0') as isNGUYENLIEUTieuHao, d.DONVI as vtDonvi, a.SOLUONG, \n " +				
				" isnull(a.hamam, 0) as hamam, isnull(a.isTinhHamAm, 0) as isTinhHamAm, isnull(a.hamluong, 0) as hamluong, " +
				" isnull(a.isTinhHamLuong, 0) as isTinhHamLuong ,ISNULL(A.DONGDU,'') AS DONGDU, ISNULL(A.GHICHU,'') AS GHICHU  \n " +
				" from ERP_DANHMUCVATTU_VATTU a   inner Join ERP_SanPham b on a.vattu_fk = b.PK_SEQ \n " +
				" left join DONVIDOLUONG d on b.DVDL_FK = d.PK_SEQ \n " +
				" where a.danhmucvt_fk = '" + this.id + "' and a.vattu_fk <>-1  " +
				" ORDER BY A.SOTT ";
		System.out.println("Lấy danh sách nguyên liệu: " + query);
		
		rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				IDanhmucvattu_SP vt = null;
				while(rs.next())
				{
					vt = new Danhmucvattu_SP();
					
					vt.setIdVT(rs.getString("vtId"));
					vt.setMaVatTu(rs.getString("vtMa"));
					vt.setTenVatTu(rs.getString("vtTen"));
					vt.setIsNLTieuHao(rs.getString("isNGUYENLIEUTieuHao"));
					vt.setDvtVT(rs.getString("vtDonvi"));
					vt.setSoLuong(rs.getString("SOLUONG"));
					vt.setDungsai(rs.getString("dungsai"));
					vt.setHaoHut(rs.getDouble("pthaohut"));
//					vt.setIdVatTuThayThe(rs.getString("idVatTuTT") == null ? "" : rs.getString("idVatTuTT") );
//					vt.setMaVatTuThayThe(rs.getString("vtTTTen"));
					vt.setHamam(rs.getString("hamam"));
					vt.setHamluong(rs.getString("hamluong"));
					vt.setIsTinhHA(rs.getString("isTinhHamAm"));
					System.out.println("ham am o day : "+vt.getIsTinhHA());
					vt.setIsTinhHL(rs.getString("isTinhHamLuong"));
					vt.setGhichu(rs.getString("ghichu"));
					vt.setDongdu(rs.getString("dongdu"));
					
					dmvtList1.add(vt);
					}

				rs.close();
				
			} 
			catch (Exception e) 
			{
				System.out.println("__Loi khi khoi tao SP cua BOM: " + e.getMessage());
			}
		}
		
		this.dmvtList = dmvtList1;
		
		
		int thangHienTai = Integer.parseInt(getDateTime("MM")),
		namHienTai = Integer.parseInt(getDateTime("yyyy")),
		thangtruoc = 0, namtruoc = 0;
		
		if(thangHienTai == 1){
			thangtruoc = 12;
			namtruoc = namHienTai - 1;
		}
		else{
			thangtruoc = thangHienTai - 1;
			namtruoc = namHienTai;
		}
		
		query = " select cp.*, isnull(dm.CHIPHI,0) as CHIPHI, isnull(t.DONGIA,0) as DONGIA " +
				" from ERP_DINHMUCCHIPHI cp " +
				" left join ( " +
				"	select DINHMUCCHIPHI_FK, " +
				"   DONGIA from ERP_DINHMUCCHIPHI_THANG where THANG = '"+thangtruoc+"' and NAM = '"+namtruoc+"'" +
				" ) as t on t.DINHMUCCHIPHI_FK = cp.PK_SEQ " +
				
				" left join (" +
				"	select * from ERP_DANHMUCVATTU_DINHMUCCHIPHI where DANHMUCVT_FK = '" + this.id + "'" +
				" ) as dm on dm.DINHMUCCHIPHI_FK = cp.PK_SEQ " +
				" order by cp.loai ";

	  
		this.dinhmucList = new ArrayList<IErpDinhmuc>();
		rs = this.db.get(query);
		if(rs != null)
			try {
				while(rs.next()){
					IErpDinhmuc dm = new ErpDinhmuc();
					dm.setId(rs.getString("PK_SEQ"));
					dm.setTen(rs.getString("TEN"));
					dm.setDVT(rs.getString("DONVITINH"));
					dm.setLoai(rs.getString("LOAI"));
					dm.setSoluong(rs.getDouble("CHIPHI"));
					dm.setDongia(rs.getDouble("DONGIA"));
					 
					this.dinhmucList.add(dm);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		
			try{
				query=" select nhamay_fk from ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK ="+this.id;
				ResultSet rsnm=db.get(query);
				while(rsnm.next()){
					this.NhamayId+=(this.NhamayId.length()>0? "," :"")+rsnm.getString("nhamay_fk");
				}rsnm.close();
				
				query=" select KBSX_FK  from ERP_DANHMUCVATTU_KBSX where DANHMUCVATTU_FK ="+this.id;
				  rsnm=db.get(query);
				while(rsnm.next()){
					this.KbsxId+=(this.KbsxId.length()>0? "," :"")+rsnm.getString("KBSX_FK");
				}rsnm.close();
				
				
				
			}catch(Exception er){
				er.printStackTrace();
			}
			this.createRs();
	}
	
	public ResultSet getSpRs() 
	{
		return this.spRs;
	}

	public void setSpRs(ResultSet spRs)
	{
		this.spRs = spRs;	
	}

	public ResultSet getNLRs() 
	{
		return this.nlRs;
	}

	public ResultSet getVattuTT(String vtId){
		String query =  "SELECT VATTUTT_FK AS VTID, SP.MA + ' -- ' + SP.TEN1 + ' [' + DVT.DONVI + '] [' + CONVERT(VARCHAR, SP.PK_SEQ) + ']' AS TEN, SOLUONG \n " +
						"FROM ERP_DANHMUCVATTU_VATTU_THAYTHE VT_TT \n " +
						"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = VT_TT.VATTUTT_FK \n " +
						"LEFT JOIN DONVIDOLUONG DVT ON SP.DVDL_FK = DVT.PK_SEQ  \n " +
						"WHERE DANHMUCVT_FK = " + this.id + " AND VATTU_FK = " + vtId + "";
 
		return this.db.get(query);
	}

	public void setNLRs(ResultSet nlRs)
	{
		this.nlRs = nlRs;	
	}

	public String getSpId() 
	{
		return this.spId;
	}

	public void setSpId(String spId) 
	{
		this.spId = spId;
	}

	public boolean dongbo()
	{
		try {
			// đã có begin tran và rollback trong proc
			String query = "EXEC pro_syn_BOM "+this.id;
			if(!this.db.update(query))
			{
				this.msg = "Không thể tạo mới : " + query;
				return false;
			}
		} catch (Exception e) {
			e.printStackTrace();
			this.msg = "Loi: " + e.getMessage();
			return false;
		}
		
		return true;
	}
	public boolean createBom(HttpServletRequest request) throws ServletException, IOException 
	{	
		request.setCharacterEncoding("UTF-8");
		try 
		{
			//Check san pham
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			if(this.ctyId==null || this.ctyId.length() ==0){
				this.msg = "Vì lý do bảo mật, vui lòng đăng nhập lại";
				return false;
			}
			
			// cho de trong dien giai lun
			/*if(this.diengiai.trim().length()<=0)
			{
				this.msg = "Vui lòng nhập diễn giải cho BOM";
				return false;
			}*/
			
			if(this.tenbom.trim().length()<=0)
			{
				this.msg = "Vui lòng nhập mã định mức vật tư!";
				return false;
			}
			
			
			if(this.TypeId.trim().equals("0")){
				if(this.Spma.trim().length() <= 0)
				{
					this.msg = "Vui lòng chọn sản phẩm";
					return false;
				}
				 
				if(this.soluongchuan.trim().length() <= 0 || Float.parseFloat(this.soluongchuan) <= 0)
				{
					this.msg = "Vui lòng nhập số lượng chuẩn";
					return false;
				}
				
			}
			if(this.dmvtList.size() <= 0)
			{
				this.msg = "Vui lòng nhập danh mục vật tư";
				return false;
			}
			
			if(!this.CheckBomExist()){
				return false;
			}
			if(this.ctyId==null || this.ctyId.equals("")){
				this.msg="Vui lòng đăng nhập lại, vì lý do bảo mật,sesion của bạn đang trong tình trạng không an toàn";
			}
		
			if(this.trangthai.trim().length()<=0){
				this.trangthai="0";
			}
			
			this.db.getConnection().setAutoCommit(false);
			
			String query =  "INSERT ERP_DANHMUCVATTU(NGAYBANHANHQTSX,DAYCHUYENSANXUAT,TRANGTHAI,ISKHONGTHOIHAN,NOISANXUAT,QUYCACH,CONGTY_FK, DVKD_FK, TENBOM, DIENGIAI, VANBANHUONGDAN, LOAISANPHAM_FK, SANPHAM_FK, MASANPHAM, " +
							"SOLUONGCHUAN, DVDL_FK, PTHAOHUT, HIEULUCTU, HIEULUCDEN, NGAYBH, LANBH, SUDUNG, ISHOATDONG, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, QUYTRINHSANXUAT) " +
							" VALUES ('"+this.ngaybanhanhQTSX+"',N'"+this.daychuyensanxuat+"',0,"+this.iskhongthoihan+",N'"+this.noisanxuat+"',N'"+this.quycach+"'," + this.ctyId + ", " + this.dvkdId + ", N'" + this.tenbom + "', N'" + this.diengiai + "', " +
							"N'" + this.vanbanhuongdan + "', " + this.lspId + ", (SELECT PK_SEQ FROM ERP_SANPHAM WHERE MA = '" + this.Spma + "'), " +
							"'" + this.Spma + "', " + this.soluongchuan + ", " +
							"" + this.donvitinh + ", " + this.pthaohut + ", '" + this.hieuluctu + "', '" + this.hieulucden + "', " +
							"'" + this.ngayBH + "', '" + this.lanBH + "', " +
							" '" + this.sudung + "', '" + this.trangthai + "', '" + this.getDateTime() + "','" + this.getDateTime() + "', " +
	   						"'" + this.userId + "', '" + this.userId + "',N'" + this.quytrinhsx+"') ";
			System.out.println(query);
			if(!this.db.update(query))
			{
				this.msg = "Không thể tạo mới BOM: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			
			query = "select IDENT_CURRENT('ERP_DANHMUCVATTU') AS ID";
			ResultSet rs = this.db.get(query) ;
			rs.next();
			this.id = rs.getString("ID");
			rs.close();
			//
		
		 
			if(this.dmvtList.size() > 0)
			{
				for(int i = 0; i < this.dmvtList.size(); i++)
				{
					IDanhmucvattu_SP vattu = this.dmvtList.get(i);
					if(vattu.getIdVT().trim().length() > 0 && Double.parseDouble(vattu.getSoLuong().trim()) > 0 )
					{
						query = "insert ERP_DANHMUCVATTU_VATTU(DANHMUCVT_FK, MAVATTU, VATTU_FK, " +
								"isNGUYENLIEUTIEUHAO, SOLUONG, PTHAOHUT, HAMAM, isTINHHAMAM, HAMLUONG, isTINHHAMLUONG,GHICHU,DONGDU,dungsai  )  " +
								"VALUES ('" + this.id + "', '" + vattu.getMaVatTu() + "', '" + vattu.getIdVT() + "', '" + vattu.getIsNLTieuHao() + "', " + vattu.getSoLuong() + ", " +
								"'" + vattu.getHaoHut()+ "', " +
								"" + vattu.getHamam() + ", " +
								"" + vattu.getIsTinhHA() + ", " + vattu.getHamluong() + ", " + vattu.getIsTinhHL() + ",N'"+vattu.getGhichu()+"',N'"+vattu.getDongdu()+"',case when "+vattu.getDungsai().length()+">0 then "+vattu.getDungsai()+" else 0 end )" ;
						System.out.println(query);
						if(!db.update(query))
						{
							this.msg = "Không thể tạo mới ERP_DANHMUCVATTU_VATTU : " + query;
							db.getConnection().rollback();
							return false;
						} 
						
						String[] maNLTT = vattu.getMaNLTT();
						String[] soluongNLTT = vattu.getSoluongNLTT();
						
						for(int m = 0; m < maNLTT.length; m++){
							if(maNLTT[m].length() > 0){
								String maNL = maNLTT[m].substring(0, maNLTT[m].indexOf(" -- ")).trim();
								String nlId = maNLTT[m].substring(maNLTT[m].indexOf("] [") + 3, maNLTT[m].length() - 1).trim();
								String soluong = soluongNLTT[m];
								
								query = "INSERT INTO ERP_DANHMUCVATTU_VATTU_THAYTHE(DANHMUCVT_FK, VATTU_FK, VATTUTT_FK, MAVATTUTT, SOLUONG) VALUES( " +
										"'" + this.id + "', '" + vattu.getIdVT() + "', " + nlId + ", '" + maNL + "', " + soluong + ")";
								System.out.println(query);
								if(!db.update(query))
								{
									this.msg = "Không thể tạo mới ERP_DANHMUCVATTU_VATTU_THAYTHE : " + query;
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
					else
					{
						this.msg="Vui lòng kiểm tra số lượng của sản phẩm có mã "+vattu.getMaVatTu();
						return false;
					}
				}
			}
			
			//CHÈN ĐỊNH MỨC CHI PHÍ
			query = "";
			if(this.dinhmucList.size() > 0){
				for(int i=0; i< this.dinhmucList.size(); i++){
					IErpDinhmuc dm = this.dinhmucList.get(i);
					if(dm.getLoai().equals("0"))
						query += "INSERT INTO ERP_DANHMUCVATTU_DINHMUCCHIPHI(DANHMUCVT_FK, DINHMUCCHIPHI_FK, CHIPHI)" +
								" VALUES('"+this.id+"','"+dm.getId()+"', "+dm.getSoluong()+") \n";
					else if(dm.getLoai().equals("1"))
						query += "INSERT INTO ERP_DANHMUCVATTU_DINHMUCCHIPHI(DANHMUCVT_FK, DINHMUCCHIPHI_FK, CHIPHI)" +
						" VALUES('"+this.id+"','"+dm.getId()+"', "+dm.getThanhtien()+") \n";	
				}
			}
			if(query.length() != 0){
				if(!this.db.update(query))
				{
					this.msg = "Không thể tạo mới Định mức chi phí: " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			
			if(this.NhamayId!=null && this.NhamayId.length() >0){
					query="INSERT INTO ERP_DANHMUCVATTU_NHAMAY (DANHMUCVATTU_FK,NHAMAY_FK) SELECT "+this.id+",pk_seq FROM ERP_NHAMAY WHERE PK_SEQ IN ("+this.NhamayId+") ";
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới  : " + query;
						this.db.getConnection().rollback();
						return false;
					}
			}
			
			if(this.KbsxId!=null && this.KbsxId.length() >0){
				query="INSERT INTO ERP_DANHMUCVATTU_KBSX (DANHMUCVATTU_FK,KBSX_FK) SELECT "+this.id+",pk_seq FROM Erp_KichBanSanXuat_Giay WHERE PK_SEQ IN ("+this.KbsxId+") ";
				if(!this.db.update(query))
				{
					this.msg = "Không thể tạo mới : " + query;
					this.db.getConnection().rollback();
					return false;
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
	
	private boolean CheckBomExist() {
		
		try{
			
			/*String sql="select pk_seq from erp_danhmucvattu where masanpham = '"+this.Spma+"' AND DIENGIAI=N'"+this.diengiai+"' ";*/
			
			//THAY DOI DK TON TAI BOM 14/11/2016, KHI TRUNG TEN KHONG CHO THEM VAO
			String sql="select pk_seq from erp_danhmucvattu where masanpham = '"+this.Spma+"' AND TENBOM=N'"+this.tenbom+"' ";
			
			if(this.id.length()>0){
				sql=sql+" and pk_seq <> "+this.id;
			}
			System.out.println("sql : "+sql);
			ResultSet rs=db.get(sql);
			if(rs.next()){
		 
				this.msg="Sản phẩm và tên BOM đã tồn tại,vui lòng nhập tên khác,vui lòng kiểm tra lại";
				return false;
				
			}
			rs.close();
			
		}catch(Exception er){
			return false;
		}
		return true;
	}

	public boolean updateBom(HttpServletRequest request) throws ServletException, IOException 
	{
		System.out.println("Vao day test: ");
		try 
		{
			//Check san pham
			if(this.dvkdId.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn đơn vị kinh doanh";
				return false;
			}
			
			
			/*if(this.diengiai.trim().length()<=0)
			{
				this.msg = "Vui lòng nhập diễn giải cho BOM";
				return false;
			}*/
			
			if(this.tenbom.trim().length()<=0)
			{
				this.msg = "Vui lòng nhập mã định mức vật tư!";
				return false;
			}
			
			
			
			
			 System.out.println("sp ma : "+ this.Spma);
			if(this.Spma.trim().length() <= 0)
			{
				this.msg = "Vui lòng chọn sản phẩm";
				return false;
			}
			
			if(this.soluongchuan.trim().length() <= 0 || Float.parseFloat(this.soluongchuan) <= 0)
			{
				this.msg = "Vui lòng nhập số lượng chuẩn";
				return false;
			}
	 
			if(this.dmvtList.size() <= 0)
			{
				this.msg = "Vui lòng nhập danh mục vật tư";
				return false;
			}
			if(!this.CheckBomExist()){
				return false;
			}
			if(this.ctyId==null || this.ctyId.equals("")){
				this.msg="Vui lòng đăng nhập lại, vì lý do bảo mật,sesion của bạn đang trong tình trạng không an toàn";
			}
			
			if(this.trangthai.trim().length()<=0){
				this.trangthai="0";
			}
			db.getConnection().setAutoCommit(false);
			/*String sql = "SELECT DISTINCT DMVT_FK AS DMVT_FK FROM ERP_MUAHANG_SP WHERE DMVT_FK ="+this.id+" and MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI !=4)	\n"
						+ "UNION ALL	\n"
						+ "SELECT DISTINCT DANHMUCVATTU_FK AS DMVT_FK FROM ERP_MUAHANG_BOM  WHERE  DANHMUCVATTU_FK ="+this.id+" AND MUAHANG_FK IN (SELECT PK_SEQ FROM ERP_MUAHANG WHERE TRANGTHAI !=4)	\n"
						+ "UNION ALL	\n"
						+ "SELECT DISTINCT DANHMUCVT_FK AS DMVT_FK FROM ERP_LENHSANXUAT_SANPHAM WHERE  DANHMUCVT_FK ="+this.id+" AND LENHSANXUAT_FK  IN (SELECT PK_SEQ FROM ERP_LENHSANXUAT_GIAY WHERE TRANGTHAI !=7)	\n";
						
			boolean checktontai = false;
			ResultSet rscheck = this.db.get(sql);
			if(rscheck.next())
			{
				rscheck.close();
				checktontai = true;
			}*/
			

			String query = 	" UPDATE ERP_DANHMUCVATTU set NGAYBANHANHQTSX='"+this.ngaybanhanhQTSX+"',DAYCHUYENSANXUAT=N'"+this.daychuyensanxuat+"', ISKHONGTHOIHAN = "+this.iskhongthoihan+",NOISANXUAT = N'"+this.noisanxuat+"',quycach= N'"+this.quycach+"' , DIENGIAI = N'" + this.diengiai + "', HIEULUCTU = '" + this.hieuluctu + "', " +
							" TENBOM=N'"+this.tenbom+"', VANBANHUONGDAN=N'"+this.vanbanhuongdan+"', "+ 
							" HIEULUCDEN = '" + this.hieulucden + "', MASANPHAM = '" + this.Spma + "', SANPHAM_FK = (SELECT PK_SEQ FROM ERP_SANPHAM WHERE MA = '" + this.Spma + "'), " +
							" SOLUONGCHUAN = '" + this.soluongchuan + "', " +
							" NGUOISUA = '" + this.userId + "', NGAYSUA = '" + this.getDateTime() + "', " +
							" ISHOATDONG = '" + this.trangthai + "', SuDung='"+this.sudung+"', " ;
			//if(checktontai == false)
				query +=		" TRANGTHAI =0," ;
			query +=		" ISDADUYET =0, " +
							" LOAISANPHAM_FK = '" + this.lspId + "', " +
							" PTHAOHUT = " + this.pthaohut + ", " +
							" DVDL_FK = " + this.donvitinh + ", " +
							" NGAYBH = '" + this.ngayBH + "', " +
							" LANBH = '" + this.lanBH + "', " +
							" DVKD_FK = " + this.dvkdId + ", " +
							" QUYTRINHSANXUAT = N'" + this.quytrinhsx + "' " +
							" WHERE pk_seq = '" + this.id + "' "; 
		 
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			//CHÈN ĐỊNH MỨC CHI PHÍ
			query = " delete from ERP_DANHMUCVATTU_DINHMUCCHIPHI WHERE DANHMUCVT_FK='"+this.id+"'";					
			
		 
			if(!this.db.update(query))
			{
				this.msg = "Không thể cập nhật Định mức chi phí Điện: " + query;
				this.db.getConnection().rollback();
				return false;
			}
			query = "";
			
			if(this.dinhmucList.size() > 0){
				for(int i=0; i< this.dinhmucList.size(); i++){
					IErpDinhmuc dm = this.dinhmucList.get(i);
					if(dm.getLoai().equals("0"))
						query = "INSERT INTO ERP_DANHMUCVATTU_DINHMUCCHIPHI(DANHMUCVT_FK, DINHMUCCHIPHI_FK, CHIPHI)" +
								" VALUES('"+this.id+"','"+dm.getId()+"', "+dm.getSoluong()+") \n";
					else if(dm.getLoai().equals("1"))
						query = "INSERT INTO ERP_DANHMUCVATTU_DINHMUCCHIPHI(DANHMUCVT_FK, DINHMUCCHIPHI_FK, CHIPHI)" +
						" VALUES('"+this.id+"','"+dm.getId()+"', "+dm.getThanhtien()+") \n";
					
					System.out.println(query);
					if(!this.db.update(query))
					{
						this.msg = "Không thể tạo mới Định mức chi phí: " + query;
						this.db.getConnection().rollback();
						return false;
					}

				}
			}
		 
			query = "delete ERP_DANHMUCVATTU_VATTU where DANHMUCVT_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete ERP_DANHMUCVATTU_NHAMAY where DANHMUCVATTU_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			query = "delete ERP_DANHMUCVATTU_KBSX where DANHMUCVATTU_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			query = "delete ERP_DANHMUCVATTU_VATTU_ThayThe where DANHMUCVT_FK = '" + this.id + "'";
			if(!db.update(query))
			{
				this.msg = "Không thể cập nhật BOM: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			System.out.println("I am here 1");
			if(this.dmvtList.size() > 0)
			{
				
				for(int i = 0; i < this.dmvtList.size(); i++)
				{
				 
					IDanhmucvattu_SP vattu = this.dmvtList.get(i);
				 
					if(vattu.getMaVatTu().trim().length() > 0 && Double.parseDouble(vattu.getSoLuong().trim()) > 0)
					{ 
							query = "insert ERP_DANHMUCVATTU_VATTU(DANHMUCVT_FK, MAVATTU, VATTU_FK, " +
									"isNGUYENLIEUTIEUHAO, SOLUONG, PTHAOHUT, HAMAM, isTINHHAMAM, HAMLUONG, isTINHHAMLUONG,GHICHU,DONGDU, dungsai  )  " +
									"VALUES ('" + this.id + "', '" + vattu.getMaVatTu() + "', '" + vattu.getIdVT() + "', '" + vattu.getIsNLTieuHao() + "', " + vattu.getSoLuong() + ", " +
									"'" + vattu.getHaoHut()+ "', " +
									"" + vattu.getHamam() + ", " +
									"" + vattu.getIsTinhHA() + ", " + vattu.getHamluong() + ", " + vattu.getIsTinhHL() + " ,N'"+vattu.getGhichu()+"',N'"+vattu.getDongdu()+"',case when "+vattu.getDungsai().length()+">0 then "+vattu.getDungsai()+" else 0 end)" ;
					 
							if(!db.update(query))
							{
								this.msg = "Không thể tạo mới ERP_DANHMUCVATTU_VATTU : " + query;
								db.getConnection().rollback();
								return false;
							} 

							String[] maNLTT = vattu.getMaNLTT();
							String[] soluongNLTT = vattu.getSoluongNLTT();
							
							for(int m = 0; m < maNLTT.length; m++){
								if(maNLTT[m].length() > 0){
									String maNL = maNLTT[m].substring(0, maNLTT[m].indexOf(" -- ")).trim();
									String nlId = maNLTT[m].substring(maNLTT[m].indexOf("] [") + 3, maNLTT[m].length() - 1).trim();
									String soluong = soluongNLTT[m];
									
									query = "INSERT INTO ERP_DANHMUCVATTU_VATTU_THAYTHE(DANHMUCVT_FK, VATTU_FK, VATTUTT_FK, MAVATTUTT, SOLUONG) VALUES( " +
											"'" + this.id + "', '" + vattu.getIdVT() + "', " + nlId + ", '" + maNL + "', " + soluong + ")";
									 
									if(!db.update(query))
									{
										this.msg = "Không thể tạo mới ERP_DANHMUCVATTU_VATTU_THAYTHE : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
							}

						}
						else
						{
							this.msg="Vui lòng kiểm tra số lượng sản phẩm cho sản phẩm có mã "+vattu.getMaVatTu();
							return false;
						}
					}
				}
			
			if(this.NhamayId!=null && this.NhamayId.length() >0){
				query="INSERT INTO ERP_DANHMUCVATTU_NHAMAY (DANHMUCVATTU_FK,NHAMAY_FK) SELECT "+this.id+",pk_seq FROM ERP_NHAMAY WHERE PK_SEQ IN ("+this.NhamayId+") ";
				if(!this.db.update(query))
				{
					this.msg = "Không thể tạo mới  : " + query;
					this.db.getConnection().rollback();
					return false;
				}
			}
			if(this.KbsxId!=null && this.KbsxId.length() >0){
				query="INSERT INTO ERP_DANHMUCVATTU_KBSX (DANHMUCVATTU_FK,KBSX_FK) SELECT "+this.id+",pk_seq FROM Erp_KichBanSanXuat_Giay WHERE PK_SEQ IN ("+this.KbsxId+") ";
				if(!this.db.update(query))
				{
					this.msg = "Không thể tạo mới : " + query;
					this.db.getConnection().rollback();
					return false;
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

	public void createRs() 
	{
 
		String query = "select pk_seq,donvikinhdoanh as ten from donvikinhdoanh ";
		this.rsDvkd=db.get(query);
		query="select ten from Chuannen";
		this.RsChuanNen=db.get(query);
		
		
		 query = "select pk_seq,ten from chungloai ";
			this.rsChungloai=db.get(query);
			
		
 
		 query = "select distinct  PK_SEQ, MA + ', ' + TEN as Ten from ERP_LOAISANPHAM where TRANGTHAI = '1' and isnull(is_sanxuat,'0') ='1' ";
 
		
		this.lhhRs = db.get(query);
		
		query="SELECT PK_SEQ, TENNHAMAY AS TEN FROM ERP_NHAMAY  ";
		
		this.RsNhamay=db.get(query);
		
		if(NhamayId.length() >0){
			query="SELECT  PK_SEQ,DienGiai as ten   FROM Erp_KichBanSanXuat_Giay WHERE NHAMAY_FK IN ("+this.NhamayId+") AND SANPHAM_FK in (select pk_seq from erp_sanpham where ma='"+this.Spma+"') ";
			this.RsKbsx=db.get(query);
			
			
		}
		
	 
		if(this.lspId.trim().length() > 0)
		{
			query = " SELECT distinct ma as pk_seq, Ma + '--' + TEN     as Ten " +
					" FROM ERP_SANPHAM WHERE TRANGTHAI = '1' AND LOAISANPHAM_FK = '" + this.lspId + "' AND CONGTY_FK = " + this.ctyId + "  ";
			
			if(this.dvkdId.length() >0){
				 query=query +" and DVKD_fk="+this.dvkdId;
				 
			 }
			 System.out.println("Du Lieu 2: "+query);	 
		  
			this.spRs = db.get(query);
			
			//Vattu thay the
			query = " select sp.pk_seq,sp.ma, sp.ten1 +' ('+ sp.quycach +')' as Ten  , dvdl.donvi as donvi " +
					" from erp_sanpham sp"+
			  		" inner join donvidoluong dvdl on dvdl.pk_seq = sp.dvdl_fk " +
			  		" where loaisanpham_fk = '" + this.lspId + "' and sp.congty_fk = '" + this.ctyId + "'";
			//System.out.println("LIST SP THAY THẾ: "+query);
			this.RsVattu=this.db.getScrol(query);
		}
		
		int thangHienTai = Integer.parseInt(getDateTime("MM")),
		namHienTai = Integer.parseInt(getDateTime("yyyy")),
		thangtruoc = 0, namtruoc = 0;
		
		if(thangHienTai == 1){
			thangtruoc = 12;
			namtruoc = namHienTai - 1;
		}
		else{
			thangtruoc = thangHienTai - 1;
			namtruoc = namHienTai;
		}
		
		this.dinhmucList = new ArrayList<IErpDinhmuc>();
		query = "	select cp.*, isnull(t.DONGIA,0) as DONGIA " +
				"	from ERP_DINHMUCCHIPHI cp " +
				"	left join ( select DINHMUCCHIPHI_FK, DONGIA from ERP_DINHMUCCHIPHI_THANG where THANG = '"+thangtruoc+"' and NAM = '"+namtruoc+"') " + 
				"	as t on t.DINHMUCCHIPHI_FK = cp.PK_SEQ ORDER BY LOAI ";
		ResultSet rs = this.db.get(query);
		if(rs != null)
			try {
				while(rs.next()){
					IErpDinhmuc dm = new ErpDinhmuc();
					dm.setId(rs.getString("PK_SEQ"));
					dm.setTen(rs.getString("TEN"));
					dm.setDVT(rs.getString("DONVITINH"));
					dm.setLoai(rs.getString("LOAI"));
					dm.setDongia(rs.getDouble("DONGIA"));
					this.dinhmucList.add(dm);
				}
				rs.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		//GET DONVITINH
	 
		query = " select b.pk_seq ,ISNULL(bc.TEN,'') AS  DANGBAOCHE from ERP_SanPham a "+
				" inner join DONVIDOLUONG b on a.DVDL_FK = b.PK_SEQ " +
				" left join DANGBAOCHE bc on bc.pk_seq= A.DANGBAOCHE  "+
				" where a.ma = '"+this.Spma+"'";
		rs = this.db.get(query);
			try {
				if(rs.next()){
					if(this.donvitinh.equals("")){
						this.donvitinh=rs.getString("pk_seq");
					}
					this.dangbaoche= (rs.getString("DANGBAOCHE"));
				}
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			
		//this.quycach=	ReportAPI.getQuycachSp(db,this.Spma);
			
		query=" SELECT PK_SEQ,DIENGIAI FROM DONVIDOLUONG "+ 
			  " WHERE PK_SEQ IN ( "+
			  " SELECT DVDL_FK FROM ERP_SANPHAM WHERE MA='"+this.Spma+"' "+
			  " UNION    "+
			  " SELECT DVDL2_FK FROM QUYCACH QC INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ=QC.SANPHAM_FK "+
			  " WHERE SP.MA='"+this.Spma+"')";
		
		System.out.println("[ don vi]: " +query );
		this.rsdvld=db.get(query);	
			
	}
	
	private String getdangbaoche(String str) {
		if(str==null){
			str="";
		}
		// TODO Auto-generated method stub
		String[] key = {"0","1","2","3","4"} ;
		   String[] value = {"Tây y","Thuốc nước","Thuốc mỡ","Thuốc viên","Nang mềm"};
		   
		   for(int i=0;i<key.length;i++){
			   if(key[i].equals(str)){
				   return value[i];
			   }
		   }
		   return "";
		   
	}

	private String getDateTime(String pattern) 
	{
        DateFormat dateFormat = new SimpleDateFormat(pattern);
        Date date = new Date();
      
        return dateFormat.format(date);
	}
	public String getDiengiai()
	{
		return this.diengiai;
	}

	public void setDiengiai(String diengiai)
	{
		this.diengiai = diengiai;
	}

	public String getTrangthai()
	{
		return this.trangthai;
	}

	public void setTrangthai(String trangthai)
	{
		this.trangthai = trangthai;
	}

	public String getHieuluctu()
	{
		return this.hieuluctu;
	}

	public void setHieuluctu(String hieuluctu)
	{
		this.hieuluctu = hieuluctu;
	}

	public String getHieulucden()
	{
		return this.hieulucden;
	}

	public void setHieulucden(String hieulucden)
	{
		this.hieulucden = hieulucden;
	}


	public String getSoluongchuan() 
	{
		return this.soluongchuan;
	}

	public void setSoluongchuan(String slgchuan) 
	{
		this.soluongchuan = slgchuan;
	}

	public String getChophepTT() 
	{
		return this.cpTT;
	}

	public void setChophepTT(String chophepTT) 
	{
		this.cpTT = chophepTT;
	}

	public void DbClose() 
	{
		try 
		{
			if (this.dmvtList != null)
				this.dmvtList.clear(); 
			if (this.dinhmucList != null)
				this.dinhmucList.clear(); 
			if (this.RsVattu != null)
				this.RsVattu.close();
			if (this.RsChuanNen != null)
				this.RsChuanNen.close();
			if (this.rsdvld != null)
				this.rsdvld.close();
			if (this.lhhRs != null)
				this.lhhRs.close();
			if (this.rsDvkd != null)
				this.rsDvkd.close();
			if (this.rsChungloai != null)
				this.rsChungloai.close();
			if(this.spRs != null)
				this.spRs.close();
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}finally{
			if (this.db != null)
				this.db.shutDown();
		}
	}
	
	private String getDateTime()
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	public void setListDanhMuc(List<IDanhmucvattu_SP> list) 
	{
		this.dmvtList = list;
	}

	public List<IDanhmucvattu_SP> getListDanhMuc() 
	{
		return this.dmvtList;
	}

	
	public ResultSet getRsVattu()
	{
		
		return this.RsVattu;
	}

	
	public void setRsVattu(ResultSet rsVattu)
	{
		this.RsVattu=rsVattu;
	}

	
	public String getSudung()
	{

		return this.sudung;
	}

	
	public void setSudung(String sudung)
	{
		this.sudung=sudung;
	}

	
	public String getLoaispId()
	{
		return this.lspId;
	}

	public void setLoaispId(String loaispid)
	{
		this.lspId = loaispid;
	}

	public ResultSet getLoaiList()
	{
		return this.lhhRs;
	}

	public void setLoaiList(ResultSet loaihhlist)
	{
		this.lhhRs = loaihhlist;
	}
	
	public String getDungsai()
	{
		return dungsai;
	}

	public void setDungsai(String dungsai)
	{
		this.dungsai = dungsai;
	}

	
	public String getSpMa() {
		
		return this.Spma;
	}

	
	public void setSpMa(String spMa) {
		
		this.Spma=spMa;
	}

	
	public String getCoSuDungHC() {
		
		return this.CosudungHC;
	}

	
	public void setCoSuDungHC(String _CoSuDungHC) {
		
		this.CosudungHC=_CoSuDungHC;
	}

	
	public String getDvkdId() {
		
		return this.dvkdId;
	}

	
	public void setDvkdId(String dvkdid) {
		
		this.dvkdId=dvkdid;
	}

	
	public ResultSet getRsDvkd() {
		
		return this.rsDvkd;
	}

	
	public void setRsDvkd(ResultSet _RsDvkd) {
		
		this.rsDvkd=_RsDvkd;
	}

	
	public String getChungloaiId() {
		
		return this.chungloaiId;
	}

	
	public void setChungloaiId(String clid) {
		
		this.chungloaiId=clid;
	}

	
	public ResultSet getRsChungloai() {
		
		return this.rsChungloai;
	}

	
	public void setRsChungloai(ResultSet rs) {
		
		this.rsChungloai=rs;
	}

	
	public String getTypeId() {
		
		return this.TypeId;
	}

	
	public void setTypeId(String type) {
		
		this.TypeId=type;
	}

	
	public String getChuanNen() {
		
		return this.ChuanNen;
	}

	
	public void setChuanNen(String ChuanNen) {
		
		this.ChuanNen=ChuanNen;
	}

	
	public ResultSet getRsChuanNen() {
		
		return this.RsChuanNen;
	}

	
	public void setRsChuanNen(ResultSet Rs) {
		
		this.RsChuanNen=Rs;
	}

	
	public String getTrongluong() {
		
		return this.Trongluong;
	}

	
	public void setTrongluong(String trongluong) {
		
		this.Trongluong=trongluong;
	}

	
	public double getPTHaoHut() {
		
		return this.pthaohut;
	}

	
	public void setPTHaoHut(double pthaohut) {
		
		this.pthaohut=pthaohut;
	}

	
	public List<IErpDinhmuc> getDinhmucList() {
		return this.dinhmucList;
	}

	
	public void setDinhmucList(List<IErpDinhmuc> value) {
		this.dinhmucList = value;
	}

	
	public String getDonViTinh() {
		return this.donvitinh;
	}

	
	public void setDonViTinh(String donvitinh) {
		this.donvitinh=donvitinh;
		
	}

	
	public ResultSet getRsDvdl() {
		
		return rsdvld;
	}

	
	public void setRsDvdl(ResultSet rs) {
		
		this.rsdvld=rs;
	}

	
	public String getTenBOM() {
		
		return this.tenbom;
	}

	
	public void setTenBOM(String tenbom) {
		
		this.tenbom=tenbom;
	}

	
	public String getVanBanHuongDan() {
		
		return this.vanbanhuongdan;
	}

	
	public void setVanBanHuongDan(String vanbanhuongdan) {
		
		this.vanbanhuongdan=vanbanhuongdan;
	}

	@Override
	public ResultSet getrsNhamay() {
		// TODO Auto-generated method stub
		return this.RsNhamay;
	}

	@Override
	public void setrsNhamay(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsNhamay=rs;
	}

	@Override
	public String getIdNhamay() {
		// TODO Auto-generated method stub
		return this.NhamayId;
	}

	@Override
	public void setIdNhamay(String Idnhamay) {
		// TODO Auto-generated method stub
		this.NhamayId=Idnhamay;
	}

	@Override
	public ResultSet getrsKichBanSX() {
		// TODO Auto-generated method stub
		return this.RsKbsx;
	}

	@Override
	public void setrsKichBanSX(ResultSet rs) {
		// TODO Auto-generated method stub
		this.RsKbsx=rs;
	}

	@Override
	public String getKichBanSXId() {
		// TODO Auto-generated method stub
		return this.KbsxId;
	}

	@Override
	public void setKichBanSXId(String Id) {
		// TODO Auto-generated method stub
		
		 
		this.KbsxId=Id;
	}

	@Override
	public String getDangbaoche() {
		// TODO Auto-generated method stub
		return this.dangbaoche;
	}

	@Override
	public void setDangbaoche(String Dangbaoche) {
		// TODO Auto-generated method stub
		this.dangbaoche=Dangbaoche;
	}

	@Override
	public String getquycach() {
		// TODO Auto-generated method stub
		return this.quycach;
	}

	@Override
	public void setquycach(String quycach) {
		// TODO Auto-generated method stub
		this.quycach= quycach;
	}

	public String getNgaybanhanhQTSX() {
		return ngaybanhanhQTSX;
	}

	public void setNgaybanhanhQTSX(String ngaybanhanhQTSX) {
		this.ngaybanhanhQTSX = ngaybanhanhQTSX;
	}

	public String getDaychuyensanxuat() {
		return daychuyensanxuat;
	}

	public void setDaychuyensanxuat(String daychuyensanxuat) {
		this.daychuyensanxuat = daychuyensanxuat;
	}
	

}
