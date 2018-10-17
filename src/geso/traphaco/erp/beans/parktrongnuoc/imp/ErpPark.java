package geso.traphaco.erp.beans.parktrongnuoc.imp;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.IDonvi;
import geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.Donvi;
import geso.traphaco.erp.beans.nhapkhoNK.ISpDetail;
import geso.traphaco.erp.beans.nhapkhoNK.imp.SpDetail;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadon;
import geso.traphaco.erp.beans.parktrongnuoc.IErpPark;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadonSp;
import geso.traphaco.erp.beans.shiphang.ISanpham;
import geso.traphaco.erp.beans.shiphang.imp.Sanpham;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;
import geso.traphaco.erp.util.DinhDang;

public class ErpPark implements IErpPark
{
	String ID,NCC, NCCID, LOAIHANG,NGAYGHINHAN,USERID,TRANGTHAI,MSG,TinhThueNhapKhau,CtyId,tigia, nppdangnhap, trangthaiHd;
	String ttId, tiente;
	List<IErpHoadon> hdList;

	ResultSet Loaihanglist;
	List<IErpHoadonSp> pnkList;
	ResultSet ttRs;
	ResultSet ParkList;
	ResultSet nccRs;
	
	String poNkId;
	ResultSet poNKRs;
	ResultSet poNK_SPRs;
	ResultSet poNK_SP_ChonRs;
	dbutils db;
	HttpServletRequest req;
	
	String tongsoluong;
	
	
	// Dùng cho Loại hd: mua khu chế xuất
	ResultSet poRs;
	String poId;
	
	ResultSet loaidonmhRs;
	String loaidonmhId;
	
	ResultSet chitietPoRs;
	String thuesuat; // Thuesuat của PO
	
	String nccThayTheTen;
	String nccThayTheDiaChi;
	String nccThayTheMST;
	String nccThayTheId ;
	String cophieuchi;
	
	// 19/5/2016
	String mauSoHoaDon;
	String kyHieuHoaDon;
	String ngayHoaDon ;
	String soTienChuaThue ;
	String thuegtgt ;
	double tong ;
	String soHoaDon;
	private List<ISanpham> spList;
	private String hoadonNCC;	
	private String muahang_fk;
	
	ResultSet rsKhoBietTru;
	ResultSet rsKhoTonTru;
	
	String idKhoBietTru;
	String idKhoTonTru;
	
	List<IDonvi> listDonvi;
	int dungsai;
	String dienGiaiCT;
	private int duyet;
	private List<IErpChuyenkho> phieuCKList;

	private int isDisplay;
	String ChenhlechHd="";
	
	
	public String getChenhlechHd() {
		return ChenhlechHd;
	}

	public void setChenhlechHd(String chenhlechHd) {
		ChenhlechHd = chenhlechHd;
	}

	public ErpPark()
	{
		this.db = new dbutils();
		this.ID="";
		this.CtyId="";
		this.LOAIHANG="";
		this.NGAYGHINHAN="";
		this.TinhThueNhapKhau="";
		this.NCCID = "";
		this.ttId = "";
		this.tiente = "";
		this.MSG="";
		this.TRANGTHAI="";
		this.poNkId = "";
		this.hdList = new ArrayList<IErpHoadon>();
		this.pnkList = new ArrayList<IErpHoadonSp>();
		this.tongsoluong = "";
		this.tigia = "";
		
		this.poId = "";
		this.thuesuat = "0";
		
		this.nccThayTheId = "";
		this.nccThayTheTen = "";
		this.nccThayTheMST = "";
		this.nccThayTheDiaChi = "";
		this.nppdangnhap = "";
		this.loaidonmhId = "";
		this.cophieuchi = "";
		this.trangthaiHd = "";
		
		this.spList = new ArrayList<ISanpham>();
		this.mauSoHoaDon="";
		this.kyHieuHoaDon ="";
		this.ngayHoaDon = "" ;
		this.soTienChuaThue = "" ;
		this.thuegtgt = "" ;
		this.tong =0 ;
		this.soHoaDon = "";
		this.muahang_fk = "";
		this.idKhoBietTru = "";
		this.idKhoTonTru = "";
		this.dungsai = 0;
		this.listDonvi = new ArrayList<IDonvi>();
		this.dienGiaiCT = "";
		this.duyet = 0;
		this.phieuCKList = new ArrayList<IErpChuyenkho>();
		this.isDisplay = 0;
	}
	
	public List<IDonvi> getListDonvi() {
		return listDonvi;
	}


	public void setListDonvi(List<IDonvi> listDonvi) {
		this.listDonvi = listDonvi;
	}


	public int getDungsai() {
		return dungsai;
	}

	public void setDungsai(int dungsai) {
		this.dungsai = dungsai;
	}

	public ResultSet getRsKhoBietTru() {
		return rsKhoBietTru;
	}

	public void setRsKhoBietTru(ResultSet rsKhoBietTru) {
		this.rsKhoBietTru = rsKhoBietTru;
	}

	public ResultSet getRsKhoTonTru() {
		return rsKhoTonTru;
	}

	public void setRsKhoTonTru(ResultSet rsKhoTonTru) {
		this.rsKhoTonTru = rsKhoTonTru;
	}

	public String getIdKhoBietTru() {
		return idKhoBietTru;
	}

	public void setIdKhoBietTru(String idKhoBietTru) {
		this.idKhoBietTru = idKhoBietTru;
	}

	public String getIdKhoTonTru() {
		return idKhoTonTru;
	}

	public void setIdKhoTonTru(String idKhoTonTru) {
		this.idKhoTonTru = idKhoTonTru;
	}


	public String getSoHoaDon() {
		return soHoaDon;
	}

	public void setSoHoaDon(String soHoaDon) {
		this.soHoaDon = soHoaDon;
	}

	public ErpPark(String id)
	{
		this.db=new dbutils();
		this.ID=id;
		this.CtyId="";
		this.NGAYGHINHAN="";
		this.TinhThueNhapKhau="";
		this.LOAIHANG="";
		this.NCCID = "";
		this.MSG="";
		this.tiente = "";
		this.TRANGTHAI="";
		this.poNkId = "";
		this.hdList = new ArrayList<IErpHoadon>();
		this.pnkList = new ArrayList<IErpHoadonSp>();
		this.tongsoluong = "";
		this.tigia = "";
	
		this.poId = "";
		this.thuesuat = "0";
		
		this.nccThayTheId = "";
		this.nccThayTheTen = "";
		this.nccThayTheMST = "";
		this.nccThayTheDiaChi = "";
		this.nppdangnhap = "";
		this.loaidonmhId = "";
		this.cophieuchi = "";
		this.trangthaiHd = "";
		
		this.kyHieuHoaDon ="";
		this.ngayHoaDon = "" ;
		this.soTienChuaThue = "" ;
		this.thuegtgt = "" ;
		this.tong =0 ;
		this.soHoaDon = "";
		this.muahang_fk = "";
		this.listDonvi = new ArrayList<IDonvi>();
		this.phieuCKList = new ArrayList<IErpChuyenkho>();
	}
	
	
	public String getMuahang_fk() {
		return muahang_fk;
	}

	public void setMuahang_fk(String muahang_fk) {
		this.muahang_fk = muahang_fk;
	}

	public String getHoadonNCC() {
		return hoadonNCC;
	}

	public void setHoadonNCC(String hoadonNCC) {
		this.hoadonNCC = hoadonNCC;
	}
	public String getKyHieuHoaDon() {
		return kyHieuHoaDon;
	}

	public void setKyHieuHoaDon(String kyHieuHoaDon) {
		this.kyHieuHoaDon = kyHieuHoaDon;
	}

	public String getNgayHoaDon() {
		return ngayHoaDon;
	}

	public void setNgayHoaDon(String ngayHoaDon) {
		this.ngayHoaDon = ngayHoaDon;
	}

	public String getSoTienChuaThue() {
		return soTienChuaThue;
	}

	public void setSoTienChuaThue(String soTienChuaThue) {
		this.soTienChuaThue = soTienChuaThue;
	}

	public String getThuegtgt() {
		return thuegtgt;
	}

	public void setThuegtgt(String thuegtgt) {
		this.thuegtgt = thuegtgt;
	}

	public double getTong() {
		return tong;
	}

	public void setTong(double tong) {
		this.tong = tong;
	}

	public List<ISanpham> getSpList() {
		return spList;
	}

	public void setSpList(List<ISanpham> spList) {
		this.spList = spList;
	}
	
	public String getTongsoluong() {
		return tongsoluong;
	}

	public void setTongsoluong(String tongsoluong) {
		this.tongsoluong = tongsoluong;
	}

	
	public String getUserId() 
	{		
		return this.USERID;
	}

	public void setUserId(String userId) 
	{		
		this.USERID=userId;
	}

	public String getId() 
	{
		
		return this.ID;
	}

	public void setNccId(String nccId) 
	{
		this.NCCID = nccId;
	}

	public String getNccId() 
	{	
		return this.NCCID;
	}

	public void setLoaihang(String loaihang)
	{
		this.LOAIHANG=loaihang;
	}

	public String getLoaihang() 
	{
		return this.LOAIHANG;
	}

	public void setId(String id) 
	{	
		this.ID=id;
	}

	public String getNgayghinhan() 
	{
		return this.NGAYGHINHAN;
	}

	public void setNgayghinhan(String ngayghinhan) 
	{	
		this.NGAYGHINHAN=ngayghinhan;
	}

	public String getTtId() 
	{
		return this.ttId;
	}

	public void setTtId(String ttId) 
	{	
		this.ttId = ttId;
	}

	public String getTiente() 
	{
		return this.tiente;
	}

	public void setTiente(String tiente) 
	{	
		this.tiente = tiente;
	}

	public ResultSet getLoaihangList() 
	{
		return this.Loaihanglist;
	}

	public void setLoaihangList(ResultSet loaihanglist) 
	{
		this.Loaihanglist=loaihanglist;
	}

	public List<IErpHoadon> getHdList() 
	{
		return this.hdList;
	}

	public void setHdList(List<IErpHoadon> hdList) 
	{
		this.hdList = hdList;
	}

	public String getTrangthai() 
	{
		return this.TRANGTHAI;
	}

	public void setTrangthai(String trangthai) 
	{	
		this.TRANGTHAI = trangthai;
	}

	public ResultSet getTienteRs() 
	{
		return this.ttRs;
	}
	
	public ResultSet getTienteRs_Display(){
		return this.ttRs ;
	}

	public void setTienteRs(ResultSet ttRs) 
	{	
		this.ttRs = ttRs;
	}

	public String getMsg() 
	{
		return this.MSG;
	}

	public void setMsg(String msg) 
	{
		this.MSG = msg;
	}

	public void clearHdlist(){
		this.hdList.clear();
	}
	
	public void createRsDisplay()
	{
		List<IErpHoadon> hdList = new ArrayList<IErpHoadon>();
		if(this.ID.length() > 0 && this.hdList.size()<= 0)
		{
			String query = 	"SELECT DISTINCT HD.*, TT.MA AS TIENTE " +
							"FROM ERP_HOADONNCC HD " +
							"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " +
							"INNER JOIN ERP_HOADONNCC_PHIEUNHAP PN ON PN.HOADONNCC_FK = HD.PK_SEQ " +
							"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PN.MUAHANG_FK " +
							"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = MH.TIENTE_FK AND TT.PK_SEQ = " + this.ttId + " " +
							"WHERE HD.PARK_FK = " + this.ID ;
			
			System.out.println("Query khoi tao: " + query);
			
			ResultSet hdRs = db.get(query);
			if(hdRs != null)
			{
				try 
				{
					while(hdRs.next())
					{
						//Tao hoa don
						IErpHoadon hd = null;
						List<IErpHoadonSp> pnkdetailList = null;
						pnkdetailList = new ArrayList<IErpHoadonSp>();
						String pk_seq = hdRs.getString("pk_seq");
						String kyhieu = hdRs.getString("kyhieu");
						String sohoadon = hdRs.getString("sohoadon");
						String ngayhoadon = hdRs.getString("ngayhoadon");
						String sotienavat = hdRs.getString("sotienAVAT");
						String sotienchietkhau = hdRs.getString("sotienchietkhau");
						String vat = hdRs.getString("vat");
						String sotienbvat = hdRs.getString("sotienBVAT");
						String park = hdRs.getString("park_fk");
						int thuesuat= hdRs.getInt("thuesuat");
						//int thuesuat = (int) ((int) ((Double.parseDouble(vat)*100))/(Double.parseDouble(sotienbvat)-Double.parseDouble(sotienchietkhau)));
						String trangthai = hdRs.getString("trangthai");
						String tiente = hdRs.getString("tiente");
						
						hd = new  ErpHoadon(pk_seq, kyhieu,String.valueOf(thuesuat), sohoadon, park,ngayhoadon, sotienavat,sotienbvat, vat,String.valueOf(sotienchietkhau), trangthai, tiente);
						//Tao pnk
						if(this.NCCID.length() > 0)
						{
							String querypnk = 	"SELECT MH.SOPO, MH.PK_SEQ AS POID, NH.PK_SEQ AS NHID, NH.NGAYCHOT AS NGAYNHAN, TT.MA AS TIENTE " + 
												"ROUND(SUM(PN.SOTIENPHIEUNHAP),0) AS SOTIEN, ROUND(SUM(PN.SOTIENHOADON),0) AS SOTIENHD, " +
												"1 AS CHON " +
												"FROM ERP_HOADONNCC_PHIEUNHAP PN " +
												"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = PN.MUAHANG_FK " +
												"INNER JOIN ERP_NHANHANG NH ON NH.PK_SEQ = PN.PHIEUNHAN_FK " +
												"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = MH.TIENTE_FK " +
												"WHERE PN.HOADONNCC_FK = '" + pk_seq + "' " +
												"GROUP BY MH.SOPO, MH.PK_SEQ, NH.PK_SEQ, NH.NGAYCHOT,TT.MA " ; 
							
							
							System.out.println("Query hien thi PNK: "+ querypnk);
							
							ResultSet rspnk = db.get(querypnk);
							if(rspnk != null)
							{
								try 
								{
									while(rspnk.next())
									{
										IErpHoadonSp pnk = null;
										
										pnk = new ErpHoadonSp();

										pnk.setPoId(rspnk.getString("POID"));
										
										/*pnk.setSOPO(rspnk.getString("SOPO"));
										

										if(!rspnk.getString("NHID").equals("0")){
						    				pnk.setSopnk(rspnk.getString("NHID"));
						    			}else{
						    				pnk.setSopnk("");
						    			}

						    			pnk.setNgaynhaphang(rspnk.getString("NGAYNHAN"));

//						    			pnk.setTiente(rspnk.getString("TIENTE"));
						    			
						    			pnk.setSotienpnk(rspnk.getString("SOTIEN"));

						    			pnk.setSotienhd(rspnk.getString("SOTIENHD"));*/
						    			
						    			pnk.setChon(rspnk.getString("CHON"));
						    			pnkdetailList.add(pnk);	
									}
									rspnk.close();
								} 
								catch (SQLException e) {}
								
								for(int i = 0;i < this.pnkList.size(); i++)
								{
									IErpHoadonSp pnktong = this.pnkList.get(i);
									pnkdetailList.add(pnktong);
								}
								//add nhung dong trong vao pnk cua hoa don
								
								Collections.sort(pnkdetailList);
								hd.setPnkList(pnkdetailList);
								
							}
						}
						//add hoa don vao hoa don list cua pnk
						hdList.add(hd);
					}
					hdRs.close();
				} 
				catch (SQLException e) { System.out.println("Exception: " + e.getMessage()); }				
				this.hdList = hdList;
				
			}
		}
	}
	
	private void TinhDungSai(){
		try{
			if(this.poId.length() >0)
			{
				String query = " select top(1) isnull(dungsai,0) as dungsai from ERP_MUAHANG where PK_SEQ in ("+this.poId+")";
				ResultSet rs = this.db.get(query);
				if(rs!=null){
					if(rs.next()){
						this.dungsai = rs.getInt("dungsai");
					}
					rs.close();
				}
			}
		} catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public void createRs() 
	{ 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
		 String today = sdf.format(date);
		 
		this.getNppInfo();
		// thong tin donvi
		
		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1' ");
		this.listDonvi.clear();
		if (donvi != null) {
			try {
				while (donvi.next()) {
					this.listDonvi.add(new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		this.nccRs = db.get("select PK_SEQ, MA + ', ' + TEN as TEN from ERP_NHACUNGCAP WHERE CONGTY_FK = "+this.CtyId 
							+ " and ISNULL (TAIKHOAN_FK,100097)='100097' ");

		this.loaidonmhRs = db.get("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");
		this.rsKhoBietTru = db.get(" select PK_SEQ, MA+'-'+TEN as TEN from ERP_KHOTT where trangthai ='1' ");
		this.rsKhoTonTru = db.get(" select PK_SEQ, MA+'-'+TEN as TEN from ERP_KHOTT where trangthai ='1' ");
		
		
		// tinhs dung sai
		TinhDungSai();
		
		if(this.NCCID.length() > 0)
		{
			
			String query = "";	
			if(this.poId.trim().length() >0){
				
				query = " select pk_seq as TTID, MA AS TIENTE from ERP_TIENTE  where trangthai = '1' " +
						" AND PK_SEQ = (select TIENTE_FK from ERP_MUAHANG where PK_SEQ in( "+this.poId+"))";
				
				
				/*query = "  select top(1) a.pk_seq as TTID, MA AS TIENTE, b.TIGIAQUYDOI from ERP_TIENTE a " +
						"  inner join ERP_TIGIA b on a.PK_SEQ = b.TIENTE_FK " +
						"  where a.trangthai = '1'  AND a.PK_SEQ = (select  top(1) TIENTE_FK " +
						"  from ERP_MUAHANG where PK_SEQ in ( "+this.poId+") " +
						"  and (b.TuNgay <='"+today+"') and ('"+today+"' <= b.DenNgay))";*/
				System.out.println("___LAY TIEN TE 22: " + query);
				ttRs = this.db.get(query);
			}
			
			
			// Tiền việt > tỉ giá : 1
			if(this.ID.trim().length() <= 0 && this.ttId.trim().length() > 0)
			{
				if(this.ttId.equals("100000")) this.tigia = "1";
				
				
			}
			
			// lay dmh NHAPKHAU
			if(this.loaidonmhId.trim().length()>0)
			{				
				if(this.ID.trim().length() == 0){
					query =  " select  distinct mh.PK_SEQ, mh.SOPO +'-' + mh.NGAYMUA +'-' + isnull(tt.MA,'') as TEN from ERP_MUAHANG  mh " +
					 		 " left join ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK " +
					 		 " left join ERP_TIENTE tt on tt.PK_SEQ = mh.TIENTE_FK "+
					 		 " where mh.LOAI = 1 and mh.TRANGTHAI =1 and dmh.QUYETDINH = 1 and " +
					 		 " dmh.TRANGTHAI=1 and mh.NHACUNGCAP_FK = "+ this.NCCID +
					 		 " and mh.PK_SEQ not in(  select distinct c.MUAHANG_FK from ERP_PARK a " +
					 		 " inner join ERP_HOADONNCC b on a.pk_seq = b.park_fk " +
					 		 " inner join ERP_HOADONNCC_DONMUAHANG c on c.HOADONNCC_FK = b.pk_seq where a.trangthai =0 ) " +
					 		 " and  ( (ISNULL(DAYEUCAUNL,'0')='1' AND ISNULL(ISGIACONG,'') ='1') OR (ISNULL(ISGIACONG,'') ='0') ) ";
				} 
				
				if(this.ID.trim().length() > 0 && this.poId.length() >0){
					query = " select distinct mh.PK_SEQ, mh.SOPO +'-' + mh.NGAYMUA +'-' + isnull(tt.MA,'')  as TEN from ERP_MUAHANG  mh " +
			 		 " left join ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK " +
			 		 " left join ERP_TIENTE tt on tt.PK_SEQ = mh.TIENTE_FK "+
			 		 " where (mh.LOAI = 1 and mh.TRANGTHAI =1 and dmh.QUYETDINH = 1 and " +
			 		 " dmh.TRANGTHAI=1 and mh.NHACUNGCAP_FK = "+ this.NCCID +") or (mh.PK_SEQ in ("+this.poId+")) " +
			 		 		"  and  ( (ISNULL(DAYEUCAUNL,'0')='1' AND ISNULL(ISGIACONG,'') ='1') OR (ISNULL(ISGIACONG,'') ='0') )  ";
				}
				 this.poRs = db.get(query);
				 System.out.println("câu lấy đơn trong nước "+ query);
			}
			// load lên sp
			if( this.ID.trim().length() <= 0 && this.poId.trim().length() > 0)
			{
				String	query1 = " SELECT DISTINCT mh.PK_SEQ as MUAHANGID, sp.PK_SEQ as SPID, sp.MA as MASP, sp.TEN +'-' +isnull(' maquette: ' +m.MA, '') as TENSP, " +
						        " mhsp.DONVI as DONVI, mhsp.SOLUONG, '' as NGAYNHAN, mhsp.thuexuat as vat, mhsp.dongia, isnull(mh.DUNGSAI,0) as dungsai," +
						       
						        " isnull(hansudung, 0) hansudung, m.PK_SEQ as idmarquette , " +
						        
						        // cho nay
						        " isnull((select SUM(SOLUONG) from ERP_HOADONNCC_DONMUAHANG a inner join ERP_HOADONNCC b on b.pk_seq=a.HOADONNCC_FK  " +
						        "  where    b.trangthai not in (4) and a.MUAHANG_FK = mh.PK_SEQ and a.SANPHAM_FK = mhsp.SANPHAM_FK "
						        + " and mhsp.DONGIA = a.DONGIA " +
						        "	group by a.MUAHANG_FK, a.SANPHAM_FK,a.DONGIA),0) as danhan, mhsp.PK_SEQ" +
						         
						        "  FROM ERP_MUAHANG mh INNER JOIN ERP_MUAHANG_SP " +
						        " mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK INNER JOIN ERP_SANPHAM sp on mhsp.SANPHAM_FK = sp.PK_SEQ  " +
						        " left join DONVIDOLUONG dv on dv.PK_SEQ = mhsp.DONVI " +
						        " left join marquette m on m.PK_SEQ = mhsp.IDMARQUETTE and m.SANPHAM_FK = sp.PK_SEQ " +
						        " WHERE mh.PK_SEQ in (  "+ this.poId +")"+
								"  ORDER BY mhsp.PK_SEQ ";
				
					System.out.println("ds sanpham :"+ query1);
					
					ResultSet rs = db.get(query1);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					
					NumberFormat formatter = new DecimalFormat("#,###,###.###");
					NumberFormat formatter1 = new DecimalFormat("#,###,###.####");
					if(rs!=null){
						ISanpham sp = null;
						try{
						while(rs.next()){
							sp= new Sanpham();
							sp.setIdmarquette(rs.getString("idmarquette"));
							sp.setMuahang_fk(rs.getString("MUAHANGID"));
							sp.setMa(rs.getString("MASP"));
							sp.setId(rs.getString("SPID"));
							sp.setDiengiai(rs.getString("TENSP"));
							sp.setSoluongdat(formatter1.format(rs.getDouble("SOLUONG")));
							sp.setDongia(formatter1.format(rs.getDouble("DONGIA")));
							sp.setSoluongnhan("");
							sp.setDvdl(rs.getString("DONVI"));
							sp.setNgaynhandukien(rs.getString("NGAYNHAN"));	
							
							sp.setVat(rs.getString("vat"));
							sp.setHansudung(rs.getString("hansudung"));
							
							// cho nay
							sp.setSoluongDaNhan(rs.getString("danhan"));
							// cho nay
							sp.setSongayluukho("");
							if(rs.getInt("dungsai") == 0){
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG")));
							} else {
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG") * rs.getDouble("dungsai")));
							}
							
							// tính tiền
							sp.setthanhtien("0");
							sp.setThanhtienVATVND("0");
							
							
							spList.add(sp);
							
						}rs.close();}catch(Exception e){
							e.printStackTrace();
						}	
					}
					
					this.spList = spList;
					
					
				}
			
			// load lên sp
			if( this.ID.trim().length() > 0 && this.poId.trim().length() > 0)
			{

				String tempQuery = "";
				if (isDisplay == 1) {
					tempQuery = " \n and isnull(( select SUM(a.SOLUONG) from ERP_HOADONNCC_DONMUAHANG  a "+
							" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = mhsp.SANPHAM_FK  " 
							+ " and round(mhsp.DONGIA,0) = round(a.DONGIA,0) " +
							" and a.MUAHANG_FK = mh.PK_SEQ),0) > 0 \n";
				} 
				   String query1 = " SELECT DISTINCT mh.PK_SEQ as MUAHANGID, sp.PK_SEQ as SPID, sp.MA as MASP, sp.TEN +'-' +isnull(' maquette: ' +m.MA, '') as TENSP, " +
						        " mhsp.DONVI, mhsp.SOLUONG, '' as NGAYNHAN, mhsp.thuexuat as vat, mhsp.dongia, isnull(mh.DUNGSAI,0) as dungsai," +
						       
						        " isnull(hansudung, 0) hansudung, m.PK_SEQ as idmarquette ,  " +
						        " isnull(( select SUM(a.SOLUONG) from ERP_HOADONNCC_DONMUAHANG  a "+
								" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = mhsp.SANPHAM_FK  " 
								+ " and mhsp.DONGIA = a.DONGIA " +
								" and a.MUAHANG_FK = mh.PK_SEQ),0) as soluongtong, "+
						        
						        // cho nay
						        " isnull((select SUM(SOLUONG) from ERP_HOADONNCC_DONMUAHANG a inner join ERP_HOADONNCC b on b.pk_seq=a.HOADONNCC_FK   where    b.trangthai not in (4) and a.MUAHANG_FK = mh.PK_SEQ "
						        + "and a.SANPHAM_FK = mhsp.SANPHAM_FK " 
						        + " and mhsp.DONGIA = a.DONGIA " +
						        "	group by a.MUAHANG_FK, a.SANPHAM_FK, a.DONGIA),0) as danhan, mhsp.PK_SEQ" +
						        
						        
						        "  FROM ERP_MUAHANG mh INNER JOIN ERP_MUAHANG_SP " +
						        " mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK INNER JOIN ERP_SANPHAM sp on mhsp.SANPHAM_FK = sp.PK_SEQ  " +
						        " left join DONVIDOLUONG dv on dv.PK_SEQ = mhsp.DONVI " +
						        " left join marquette m on m.PK_SEQ = mhsp.IDMARQUETTE and m.SANPHAM_FK = sp.PK_SEQ " +
						        " WHERE mh.PK_SEQ in (  "+ this.poId +") "+
						        tempQuery+
								"  ORDER BY mhsp.PK_SEQ ";
						 				
					System.out.println("ds sanpham :"+ query1);
					
					ResultSet rs = db.get(query1);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					
					NumberFormat formatter = new DecimalFormat("#,###,###.###");
					NumberFormat formatter1 = new DecimalFormat("#,###,###.####");
					if(rs!=null){
						ISanpham sp = null;
						try{
						while(rs.next()){
							sp= new Sanpham();
					
							sp.setIdmarquette(rs.getString("idmarquette"));
							sp.setMuahang_fk(rs.getString("MUAHANGID"));
							sp.setMa(rs.getString("MASP"));
							sp.setId(rs.getString("SPID"));
							sp.setDiengiai(rs.getString("TENSP"));
							sp.setSoluongdat(formatter1.format(rs.getDouble("SOLUONG")));
							sp.setDongia(formatter1.format(rs.getDouble("DONGIA")));
							sp.setDvdl(rs.getString("DONVI"));
							sp.setNgaynhandukien(rs.getString("NGAYNHAN"));	
							sp.setVat(rs.getString("vat"));
							sp.setHansudung(rs.getString("hansudung"));
							
							// tính tiền
							sp.setthanhtien(formatter1.format(rs.getDouble("soluongtong") * rs.getDouble("DONGIA")));
							sp.setThanhtienVATVND("0");
							//cho nay
							sp.setSoluongnhan(formatter1.format(rs.getDouble("soluongtong")));
							
							sp.setSongayluukho("");
							if(rs.getInt("dungsai") == 0){
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG")));
							} else {
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG") * rs.getDouble("dungsai")));
							}
							
							spList.add(sp);
							
						}rs.close();}catch(Exception e){
							e.printStackTrace();
						}	
					}
					
					this.spList = spList;
					
					
				}
			
		}
	}
	
	public void createRs_update() 
	{ 
		 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		 Date date = new Date();
		 String today = sdf.format(date);
		 
		this.getNppInfo();
		// thong tin donvi
		
		ResultSet donvi = db.get("select PK_SEQ, DONVI from DONVIDOLUONG where TRANGTHAI = '1' ");
		this.listDonvi.clear();
		if (donvi != null) {
			try {
				while (donvi.next()) {
					this.listDonvi.add(new Donvi(donvi.getString("pk_seq"), donvi.getString("donvi")));
				}
				donvi.close();
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		
		this.nccRs = db.get("select PK_SEQ, MA + ', ' + TEN as TEN from ERP_NHACUNGCAP WHERE CONGTY_FK = "+this.CtyId);

		this.loaidonmhRs = db.get("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");
		this.rsKhoBietTru = db.get(" select PK_SEQ, MA+'-'+TEN as TEN from ERP_KHOTT where trangthai ='1' ");
		this.rsKhoTonTru = db.get(" select PK_SEQ, MA+'-'+TEN as TEN from ERP_KHOTT where trangthai ='1' ");
		
		
		// tinhs dung sai
		TinhDungSai();
		
		if(this.NCCID.length() > 0)
		{
			
			String query = "";	
			if(this.poId.trim().length() >0){
				
				query = " select pk_seq as TTID, MA AS TIENTE from ERP_TIENTE  where trangthai = '1' " +
						" AND PK_SEQ = (select TIENTE_FK from ERP_MUAHANG where PK_SEQ in( "+this.poId+"))";
				
				
				/*query = "  select top(1) a.pk_seq as TTID, MA AS TIENTE, b.TIGIAQUYDOI from ERP_TIENTE a " +
						"  inner join ERP_TIGIA b on a.PK_SEQ = b.TIENTE_FK " +
						"  where a.trangthai = '1'  AND a.PK_SEQ = (select  top(1) TIENTE_FK " +
						"  from ERP_MUAHANG where PK_SEQ in ( "+this.poId+") " +
						"  and (b.TuNgay <='"+today+"') and ('"+today+"' <= b.DenNgay))";*/
				System.out.println("___LAY TIEN TE 22: " + query);
				ttRs = this.db.get(query);
			}
			
			
			// Tiền việt > tỉ giá : 1
			if(this.ID.trim().length() <= 0 && this.ttId.trim().length() > 0)
			{
				if(this.ttId.equals("100000")) this.tigia = "1";
				
				
			}
			
			// lay dmh NHAPKHAU
			if(this.loaidonmhId.trim().length()>0)
			{				
				if(this.ID.trim().length() == 0){
					query =  " select  distinct mh.PK_SEQ, mh.SOPO +'-' + mh.NGAYMUA +'-' + isnull(tt.MA,'') as TEN from ERP_MUAHANG  mh " +
					 		 " inner join ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK " +
					 		 " left join ERP_TIENTE tt on tt.PK_SEQ = mh.TIENTE_FK "+
					 		 " where mh.LOAI = 1 and mh.TRANGTHAI =1 and dmh.QUYETDINH = 1 and " +
					 		 " dmh.TRANGTHAI=1 and mh.NHACUNGCAP_FK = "+ this.NCCID +
					 		 " and mh.PK_SEQ not in(  select distinct c.MUAHANG_FK from ERP_PARK a " +
					 		 " inner join ERP_HOADONNCC b on a.pk_seq = b.park_fk " +
					 		 " inner join ERP_HOADONNCC_DONMUAHANG c on c.HOADONNCC_FK = b.pk_seq where a.trangthai =0 ) " +
					 		 " and  ( (ISNULL(DAYEUCAUNL,'0')='1' AND ISNULL(ISGIACONG,'') ='1') OR (ISNULL(ISGIACONG,'') ='0') ) ";
				} 
				
				if(this.ID.trim().length() > 0 && this.poId.length() >0){
					query = " select distinct mh.PK_SEQ, mh.SOPO +'-' + mh.NGAYMUA +'-' + isnull(tt.MA,'')  as TEN from ERP_MUAHANG  mh " +
			 		 " inner join ERP_DUYETMUAHANG dmh on mh.PK_SEQ = dmh.MUAHANG_FK " +
			 		 " left join ERP_TIENTE tt on tt.PK_SEQ = mh.TIENTE_FK "+
			 		 " where (mh.LOAI = 1 and mh.TRANGTHAI =1 and dmh.QUYETDINH = 1 and " +
			 		 " dmh.TRANGTHAI=1 and mh.NHACUNGCAP_FK = "+ this.NCCID +") or (mh.PK_SEQ in ("+this.poId+")) " +
			 		 		"  and  ( (ISNULL(DAYEUCAUNL,'0')='1' AND ISNULL(ISGIACONG,'') ='1') OR (ISNULL(ISGIACONG,'') ='0') )  ";
				}
				 this.poRs = db.get(query);
				 System.out.println("câu lấy đơn trong nước "+ query);
			}
			
			// load lên sp
			if( this.ID.trim().length() <= 0 && this.poId.trim().length() > 0)
			{
				/*String	query1 = " SELECT DISTINCT mh.PK_SEQ as MUAHANGID, sp.PK_SEQ as SPID, sp.MA as MASP, sp.TEN +'-' +isnull(' maquette: ' +m.MA, '') as TENSP, \n " +
						        " mhsp.DONVI as DONVI, mhsp.SOLUONG, '' as NGAYNHAN, mhsp.thuexuat as vat, mhsp.dongia, isnull(mh.DUNGSAI,0) as dungsai,\n " +
						       
						        " isnull(hansudung, 0) hansudung, m.PK_SEQ as idmarquette , \n " +
						        
						        // cho nay
						        " isnull((select SUM(SOLUONG) from ERP_HOADONNCC_DONMUAHANG a where MUAHANG_FK = mh.PK_SEQ and a.SANPHAM_FK = mhsp.SANPHAM_FK "
						        + " and mhsp.DONGIA = a.DONGIA \n " +
						        "	group by MUAHANG_FK, a.SANPHAM_FK,a.DONGIA),0) as danhan\n " +
						        "  FROM ERP_MUAHANG mh INNER JOIN ERP_MUAHANG_SP \n " +
						        " mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK INNER JOIN ERP_SANPHAM sp on mhsp.SANPHAM_FK = sp.PK_SEQ  \n " +
						        " left join DONVIDOLUONG dv on dv.PK_SEQ = mhsp.DONVI \n " +
						        " left join marquette m on m.PK_SEQ = mhsp.IDMARQUETTE and m.SANPHAM_FK = sp.PK_SEQ \n " +
						        " WHERE mh.PK_SEQ in (  "+ this.poId +")";*/
				
				//3_11_2016 : Thu gang buoc so luong nhan (loai hoa don update hien tai)
				
				String	query1 = " SELECT DISTINCT mh.PK_SEQ as MUAHANGID, sp.PK_SEQ as SPID, sp.MA as MASP, sp.TEN +'-' +isnull(' maquette: ' +m.MA, '') as TENSP, \n " +
		        " mhsp.DONVI as DONVI, mhsp.SOLUONG, '' as NGAYNHAN, mhsp.thuexuat as vat, mhsp.dongia, isnull(mh.DUNGSAI,0) as dungsai,\n " +
		       
		        " isnull(hansudung, 0) hansudung, m.PK_SEQ as idmarquette , \n " +
		        
		        // cho nay
		        " isnull((select SUM(SOLUONG) from ERP_HOADONNCC_DONMUAHANG a where MUAHANG_FK = mh.PK_SEQ and a.SANPHAM_FK = mhsp.SANPHAM_FK  \n"+ 
		        " and mhsp.DONGIA = a.DONGIA \n " +
		       /* " and a.hoadonncc_fk not in (select  hd.pk_seq from erp_hoadonncc hd inner join erp_park park on park.pk_seq=hd.park_fk where  park.pk_seq="+ this.ID+") \n" +*/
		        " group by MUAHANG_FK, a.SANPHAM_FK,a.DONGIA),0) as danhan \n " +
		        
		        
		        " FROM ERP_MUAHANG mh INNER JOIN ERP_MUAHANG_SP \n " + 
		        " mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK INNER JOIN ERP_SANPHAM sp on mhsp.SANPHAM_FK = sp.PK_SEQ  \n " +
		        " left join DONVIDOLUONG dv on dv.PK_SEQ = mhsp.DONVI \n " +
		        " left join marquette m on m.PK_SEQ = mhsp.IDMARQUETTE and m.SANPHAM_FK = sp.PK_SEQ \n " +
		        " WHERE mh.PK_SEQ in (  "+ this.poId +")";
				
				
						 				
					System.out.println("ds sanpham update:"+ query1); 
					
					ResultSet rs = db.get(query1);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					
					NumberFormat formatter = new DecimalFormat("#,###,###.###");
					NumberFormat formatter1 = new DecimalFormat("#,###,###.####");
					if(rs!=null){
						ISanpham sp = null;
						try{
						while(rs.next()){
							sp= new Sanpham();
							sp.setIdmarquette(rs.getString("idmarquette"));
							sp.setMuahang_fk(rs.getString("MUAHANGID"));
							sp.setMa(rs.getString("MASP"));
							sp.setId(rs.getString("SPID"));
							sp.setDiengiai(rs.getString("TENSP"));
							sp.setSoluongdat(formatter1.format(rs.getDouble("SOLUONG")));
							sp.setDongia(formatter1.format(rs.getDouble("DONGIA")));
							sp.setSoluongnhan("");
							sp.setDvdl(rs.getString("DONVI"));
							sp.setNgaynhandukien(rs.getString("NGAYNHAN"));	
							
							sp.setVat(rs.getString("vat"));
							sp.setHansudung(rs.getString("hansudung"));
							
							// cho nay
							sp.setSoluongDaNhan(rs.getString("danhan"));
							// cho nay
							sp.setSongayluukho("");
							if(rs.getInt("dungsai") == 0){
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG")));
							} else {
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG") * rs.getDouble("dungsai")));
							}
							
							// tính tiền
							sp.setthanhtien("0");
							sp.setThanhtienVATVND("0");
							
							
							spList.add(sp);
							
						}rs.close();}catch(Exception e){
							e.printStackTrace();
						}	
					}
					
					this.spList = spList;
					
					
				}
			
			// load lên sp
			if( this.ID.trim().length() > 0 && this.poId.trim().length() > 0)
			{
				
				   String query1 = " SELECT DISTINCT mh.PK_SEQ as MUAHANGID, sp.PK_SEQ as SPID, sp.MA as MASP, sp.TEN +'-' +isnull(' maquette: ' +m.MA, '') as TENSP, \n " +
						        " mhsp.DONVI, mhsp.SOLUONG, '' as NGAYNHAN, mhsp.thuexuat as vat, mhsp.dongia, isnull(mh.DUNGSAI,0) as dungsai,\n " +
						       
						        " isnull(hansudung, 0) hansudung, m.PK_SEQ as idmarquette ,  \n " +
						        " isnull(( select SUM(a.SOLUONG) from ERP_HOADONNCC_DONMUAHANG  a "+
								" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = mhsp.SANPHAM_FK  " 
								+ " and mhsp.DONGIA = a.DONGIA \n " +
								" and a.MUAHANG_FK = mh.PK_SEQ),0) as soluongtong, "+
						        
						        // cho nay
						        " isnull((select SUM(SOLUONG) from ERP_HOADONNCC_DONMUAHANG a where MUAHANG_FK = mh.PK_SEQ "
						        + "and a.SANPHAM_FK = mhsp.SANPHAM_FK " 
						        + " and mhsp.DONGIA = a.DONGIA \n " +
						     /*   " and a.hoadonncc_fk not in (select  hd.pk_seq from erp_hoadonncc hd inner join erp_park park on park.pk_seq=hd.park_fk where  park.pk_seq="+this.ID +") \n\n " +*/
						        "	group by MUAHANG_FK, a.SANPHAM_FK, a.DONGIA),0) as danhan\n " +
						        "  FROM ERP_MUAHANG mh INNER JOIN ERP_MUAHANG_SP \n " +
						        " mhsp on mh.PK_SEQ = mhsp.MUAHANG_FK INNER JOIN ERP_SANPHAM sp on mhsp.SANPHAM_FK = sp.PK_SEQ  \n " +
						        " left join DONVIDOLUONG dv on dv.PK_SEQ = mhsp.DONVI \n " +
						        " left join marquette m on m.PK_SEQ = mhsp.IDMARQUETTE and m.SANPHAM_FK = sp.PK_SEQ \n " +
						        " WHERE mh.PK_SEQ in (  "+ this.poId +") ";
						 				
					System.out.println("ds sanpham update2:"+ query1);
					
					ResultSet rs = db.get(query1);
					List<ISanpham> spList = new ArrayList<ISanpham>();
					
					NumberFormat formatter = new DecimalFormat("#,###,###.###");
					NumberFormat formatter1 = new DecimalFormat("#,###,###.####");
					if(rs!=null){
						ISanpham sp = null;
						try{
						while(rs.next()){
							sp= new Sanpham();
					
							sp.setIdmarquette(rs.getString("idmarquette"));
							sp.setMuahang_fk(rs.getString("MUAHANGID"));
							sp.setMa(rs.getString("MASP"));
							sp.setId(rs.getString("SPID"));
							sp.setDiengiai(rs.getString("TENSP"));
							sp.setSoluongdat(formatter1.format(rs.getDouble("SOLUONG")));
							sp.setDongia(formatter1.format(rs.getDouble("DONGIA")));
							sp.setDvdl(rs.getString("DONVI"));
							sp.setNgaynhandukien(rs.getString("NGAYNHAN"));	
							sp.setVat(rs.getString("vat"));
							sp.setHansudung(rs.getString("hansudung"));
							
							// tính tiền
							sp.setthanhtien(formatter.format(rs.getDouble("soluongtong") * rs.getDouble("DONGIA")));
							sp.setThanhtienVATVND("0");
							
							// cho nay
							sp.setSoluongDaNhan(rs.getString("danhan"));
							
							//cho nay
							sp.setSoluongnhan(formatter1.format(rs.getDouble("soluongtong")));
							
							sp.setSongayluukho("");
							if(rs.getInt("dungsai") == 0){
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG")));
							} else {
								sp.setSoluongMaxNhan(formatter1.format(rs.getDouble("SOLUONG") * rs.getDouble("dungsai")));
							}
							
							spList.add(sp);
							
						}rs.close();}catch(Exception e){
							e.printStackTrace();
						}	
					}
					
					this.spList = spList;
					
					
				}
			
		}
	}	
	public void createRsLocNcc() 
	{
		this.Loaihanglist= this.db.get("select * from ERP_LOAISANPHAM" );
		List<IErpHoadonSp> pnkList = new ArrayList<IErpHoadonSp>();
		if(this.NCCID.length() > 0 &&  this.pnkList.size() <= 0)
		{
			String query= 
			  "select 	phieunhap.nkId, phieunhap.SOPHIEUNHAPHANG, phieunhap.ngaynhapkho,"+
			  " 		phieunhap.tonggiatri - isnull(danhan.dasudung, '0') as tonggiatri"+
			  " from"+
			  "("+
			  " select 	a.pk_seq as nkId,a.SOPHIEUNHAPHANG, a.ngaynhapkho, "+
			  " 		ROUND(sum(b.dongia * b.soluongnhap),0) as tonggiatri"+
			  " from 	erp_nhapkho a inner join erp_nhapkho_sanpham b on a.pk_seq = b.sonhapkho_fk"+
			  " where 	a.sophieunhaphang in (select pk_seq "+
			  							"from erp_nhanhang "+
			  							"where muahang_fk in (select pk_seq from erp_muahang where a.trangthai = '1' and nhacungcap_fk = '" +this.NCCID+ "'))"+
			  " group by a.pk_seq, a.ngaynhapkho,a.SOPHIEUNHAPHANG ) phieunhap "+
			  " left join"+
			 " ("+
			  "	select phieunhap_fk,sum(sotienhoadon) as dasudung "+
			  	"from ERP_HOADONNCC_PHIEUNHAP " +
			  	"where phieunhap_fk in (select pk_seq from erp_nhapkho where trangthai = 1)  ";
			
			
			query += "group by  phieunhap_fk"+
			 " ) danhan"+
			"  on  phieunhap.nkId = danhan.phieunhap_fk "+
			 " where phieunhap.tonggiatri - isnull(danhan.dasudung, '0') > 0 " +
			 "order by phieunhap.nkId ASC";
			
			System.out.println("cau query nhao kho la "+query);
			ResultSet pnkRs = db.get(query);
			if(pnkRs != null)
			{
				try 
				{
					IErpHoadonSp pnkDetail = null; 
					while(pnkRs.next())
					{
						pnkDetail = new ErpHoadonSp();
							 
						String nkId = pnkRs.getString("nkId");
						String sopnk=pnkRs.getString("SOPHIEUNHAPHANG");
						String ngaynhapkho = pnkRs.getString("ngaynhapkho");
						String tonggiatri = pnkRs.getString("tonggiatri");
						//System.out.println("Id bean "+nkId );
						/*pnkDetail.setId(nkId);
						pnkDetail.setSopnk(sopnk);
						pnkDetail.setNgaynhaphang(ngaynhapkho);
						pnkDetail.setSotienpnk(tonggiatri);
						pnkDetail.setSotienhd("");*/
						//System.out.println("get "+ pnkDetail.getId());
						pnkList.add(pnkDetail);
					}
					pnkRs.close();
				} 
				catch (SQLException e) {}				
			}
			System.out.println("toi day");
			this.pnkList = pnkList;
		}

	}
	
	public void init() 
	{
		try{
		String query = 
		" SELECT count(TTHD.HOADON_FK) cophieuchi FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
		" WHERE TT.NCC_FK is not null AND TTHD.LOAIHD = 0 AND TT.TRANGTHAI != 2 AND TTHD.HOADON_FK = "+this.ID;

		System.out.println("init: " + query);
		ResultSet rs_hd = db.get(query);
		
		int phieuchi = 0;
		if(rs_hd != null)
		{
			try 
			{
				if(rs_hd.next())
				{
					phieuchi = rs_hd.getInt("cophieuchi");
				}
				rs_hd.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		this.cophieuchi = phieuchi + "";
		
		// lấy thông tin hoá đơn nhập khẩu

		query ="  select NGAYGHINHAN, p.NCC_FK, PO_NHAPKHAU, p.TIENTE_FK, isNull(hoaDon.tiGia, '1') as TIGIA, p.LOAIHD,isnull( MAUSOHOADON,'') as  MAUSOHOADON, " +
		       "  kyhieu, sohoadon, ngayhoadon ,sotienAVAT,vat,sotienBVAT,hoadon.PK_SEQ as hoadonncc,isnull(KHOBIETTRU_FK,0) as KHOBIETTRU_FK," +
		       "  isnull(KHOTONTRU_FK,0) as KHOTONTRU_FK , ISNULL(P.DIENGIAI,'') AS DIENGIAI,p.trangthai ,ISNULL( hoadon.CHENHLECHHD,0) CHENHLECHHD  " +
		       "  from ERP_PARK p inner join ERP_HOADONNCC hoadon on p.pk_seq = hoadon.park_fk " +
		       "  where p.pk_seq = "+ this.ID;
		
		System.out.println("query thông tin HD"+ query);
		
		ResultSet rs = this.db.get(query);
		if(rs!=null){
			if(rs.next()){
				this.loaidonmhId = "0";
				this.NGAYGHINHAN = rs.getString("NGAYGHINHAN");
				this.NCCID = rs.getString("NCC_FK");
				this.ttId = rs.getString("TIENTE_FK");
				this.mauSoHoaDon = rs.getString("MAUSOHOADON");
				this.kyHieuHoaDon = rs.getString("kyhieu");
				this.soHoaDon = rs.getString("sohoadon");
				this.ngayHoaDon = rs.getString("ngayhoadon");
				this.tong = rs.getDouble("sotienAVAT");
				this.thuegtgt = rs.getString("vat");
				this.soTienChuaThue = rs.getString("sotienBVAT");
				this.hoadonNCC = rs.getString("hoadonncc");
				this.poId = rs.getString("PO_NHAPKHAU");
				this.tigia = rs.getString("TIGIA");
				System.out.println("asdsadsadsa"+tigia);
				this.TRANGTHAI = rs.getString("trangthai");
				System.out.println("sadsadsasda"+TRANGTHAI);
				// thêm 2 kho tồn trữ và biệt trữ
				this.idKhoBietTru = rs.getString("KHOBIETTRU_FK");
				if(this.idKhoBietTru.equals("0")){
					this.idKhoBietTru = "";
				}
				this.idKhoTonTru =  rs.getString("KHOTONTRU_FK");
				if(this.idKhoTonTru.equals("0")){
					this.idKhoTonTru = "";
				}
				this.dienGiaiCT = rs.getString("DIENGIAI");
				
				this.ChenhlechHd=rs.getString("CHENHLECHHD");
			}
			
			rs.close();
		}
		
		
		this.createRs();
		
		// đã có list sản phẩm chỉnh lại vài thông số: tinh lai so luong da nhan roi
		
		if( this.hoadonNCC.trim().length() >0){
			
			List<String> sanpham_fk = new ArrayList<String>();
			List<String> soluongdat = new ArrayList<String>();
			List<String> thanhtien = new ArrayList<String>();
			List<String> ngaynhap = new ArrayList<String>();
			List<String> muahang_fk = new ArrayList<String>();
			
			List<Double> dongia = new ArrayList<Double>();
			
			/*query = "  select max( dh.sott) as sott  , dh.sanpham_fk, isnull((select SUM(SOLUONG) from ERP_HOADONNCC_DONMUAHANG a "+
					" where a.MUAHANG_FK = dh.MUAHANG_FK and a.SANPHAM_FK = dh.SANPHAM_FK "+
					" group by MUAHANG_FK, a.SANPHAM_FK),0) as sl, dh.MUAHANG_FK ," +
					" isnull(sum(dh.thanhtien),0) as thanhtien, dh.ngaynhandk, isnull(dh.dongia,0) as dongia  " +
					" from ERP_HOADONNCC_DONMUAHANG dh " +
					" inner join ERP_HOADONNCC hd on hd.PK_SeQ = dh.HOADONNCC_FK  " +
					" inner join ERP_PARK p on p.PK_SEQ = hd.park_fk " +
					"  where dh.MUAHANG_FK  in ( " + this.poId + " )"+
					"  and p.trangthai = 1 "+
					"  group by dh.sanpham_fk, dh.ngaynhandk,dh.MUAHANG_FK, dh.dongia  " + 
					"  order by  dh.MUAHANG_FK ,sott ";*/
			
			
			
			//so luong da nhan tinh bang cac phieu tu da chot => 7/11/2016 _ANHTHU
			query = "  select max( dh.sott) as sott  , dh.sanpham_fk, isnull(sum(dh.soluongdat),0) as sl, dh.MUAHANG_FK , isnull(dh.dongia,0) as dongia ," +
			" isnull(sum(dh.thanhtien),0) as thanhtien, dh.ngaynhandk " +
			" from ERP_HOADONNCC_DONMUAHANG dh " +
			" inner join ERP_HOADONNCC hd on hd.PK_SeQ = dh.HOADONNCC_FK  " +
			" inner join ERP_PARK p on p.PK_SEQ = hd.park_fk " +
			"  where dh.MUAHANG_FK  in ( " + this.poId + " )"+
			"  and p.trangthai not in (0,4) "+
			/*"  and p.trangthai = 1 "+*/
			"  group by dh.sanpham_fk, dh.ngaynhandk,dh.MUAHANG_FK, dh.dongia  " + 
			"  order by SANPHAM_FK, dh.MUAHANG_FK, sott";
			
			
			
			rs = this.db.get(query);
			System.out.println("số lượng đặt haha"+ query);
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			//NumberFormat formatter_4le = new DecimalFormat("#,###,###.###");
			if(rs!=null){
				while(rs.next()){
					sanpham_fk.add(rs.getString("sanpham_fk"));
					soluongdat.add(DinhDang.dinhdangkho(rs.getDouble("sl")));
					thanhtien.add(formatter.format(rs.getDouble("thanhtien")));
					ngaynhap.add(rs.getString("ngaynhandk"));
					muahang_fk.add(rs.getString("muahang_fk"));
					dongia.add(rs.getDouble("dongia"));
				}
				rs.close();
			}
			
			// xử lý cập nhật lại list
			for(int i=0; i< this.spList.size();i++){
				// trường hợp câu select ở trên không trả
				// được về kết quả nào thì gán mặc định =0
				if(sanpham_fk.size() == 0){
					ISanpham sp = this.spList.get(i);
					sp.setSoluongDaNhan("0");
					sp.setNgaynhandukien("");
					sp.setthanhtien("0");
					sp.setThanhtienVATVND("0");
					this.spList.set(i, sp);
				} else {
					for(int j=0; j< sanpham_fk.size();j++){
						if(sanpham_fk.get(j).equals(this.spList.get(i).getId())  
								&& muahang_fk.get(j).equals(this.spList.get(i).getMuahang_fk())  
								&& dongia.get(j) == Double.parseDouble(this.spList.get(i).getDongia().replaceAll(",", ""))){
							ISanpham sp = this.spList.get(i);
							sp.setSoluongDaNhan(soluongdat.get(j));
							sp.setNgaynhandukien(ngaynhap.get(j));
							sp.setthanhtien("0");
							sp.setThanhtienVATVND("0");
							this.spList.set(i, sp);
							break;
						}
					}
				}
			}
			
			//Chi hien thi ra nhung mat hang co so luong hoa don > 0
			
			
			// xử lý hiện lại số lô
			query = " select dh.muahang_fk, dh.sanpham_fk, dh.soluong,isnull(NGAYSANXUAT,'') as nsx, " +
					" isnull(( select SUM(a.SOLUONG) from ERP_HOADONNCC_DONMUAHANG  a "+
					" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = dh.SANPHAM_FK " +
					" and a.MUAHANG_FK = dh.MUAHANG_FK and a.dongia = dh.dongia ),0) as soluongtong, "+
					" isnull(( select SUM(a.THANHTIENVIET) from ERP_HOADONNCC_DONMUAHANG  a "+
					" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = dh.SANPHAM_FK " +
					" and a.MUAHANG_FK = dh.MUAHANG_FK and a.dongia = dh.dongia),0) as thanhtienVIET, isnull(dh.DONGIAVIET,0) as DONGIAVIET, "+
					
					" isnull(( select SUM(a.THANHTIENVATVIET) from ERP_HOADONNCC_DONMUAHANG  a "+
					" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = dh.SANPHAM_FK " +
					" and a.MUAHANG_FK = dh.MUAHANG_FK and a.dongia = dh.dongia ),0) as thanhtienVATVIET,"+
					
					" isnull(NGAYHETHAN,'') as ngayhethan, dh.SOLO,isnull(dh.DONGIA, 0) as DONGIA,isnull(nsx_fk,0) nsx_fk,  "
					+ " isnull((select isnull(ten,'') from erp_nhasanxuat q where nsx_fk=q.pk_seq),'') nsxten, isnull(marrquet,'') marrquet  " +
					" from ERP_HOADONNCC_DONMUAHANG dh " +
					" where dh.HOADONNCC_FK =  " + this.hoadonNCC +
					" order by   dh.muahang_fk,dh.sott  ";
			
			rs = this.db.get(query);
			System.out.println("Xử lý số lô:"+ query);
			List<ISpDetail> listct = new ArrayList<ISpDetail>();
			List<String> soluongtong = new ArrayList<String>();
			List<String> thanhtienVND = new ArrayList<String>();
			List<String> dongiaVND = new ArrayList<String>();
			List<String> thanhtienVATVND = new ArrayList<String>();
			
			if(rs!=null){
				while(rs.next()){
					ISpDetail ct = new SpDetail();
					ct.setMa(rs.getString("sanpham_fk"));
					ct.setSoluong(DinhDang.dinhdangkho(rs.getDouble("soluong")));
					ct.setNgaySx(rs.getString("nsx"));
					ct.setNgayHethan(rs.getString("ngayhethan"));
					ct.setMuahang_fk(rs.getString("muahang_fk"));
					ct.setSolo(rs.getString("SOLO"));
					ct.setDongia(rs.getDouble("DONGIA"));
					ct.setNSXid(rs.getString("nsx_fk"));
					ct.setMarrquet(rs.getString("marrquet"));
					ct.setNSXTen(rs.getString("nsxten"));
					
					listct.add(ct);
					soluongtong.add(rs.getString("soluongtong"));
					thanhtienVND.add(rs.getString("thanhtienViet"));
					dongiaVND.add(rs.getString("DONGIAVIET"));
					thanhtienVATVND.add(rs.getString("THANHTIENVATVIET"));
				}
				rs.close();
			}
			
			// add list số lô vào
			for(int i=0; i< listct.size(); i++){
				ISpDetail temp  = listct.get(i);
				for(int j=0; j< this.spList.size(); j++){
					ISanpham sp = this.spList.get(j);
					if(temp.getMa().equals(sp.getId())   && temp.getMuahang_fk().equals(sp.getMuahang_fk())
							&& temp.getDongia() == Double.parseDouble(sp.getDongia().replaceAll(",", ""))){
						// add số lô từ danh sách lấy ở bước trên
						List<ISpDetail> listemp = sp.getSpDetail();
						listemp.add(temp);
						
						// tính lại số lượng nhận và thành tiền
						sp.setSoluongnhan(DinhDang.dinhdangkho(Double.parseDouble(soluongtong.get(i).replaceAll(",", ""))));
						Double tt = Double.parseDouble(soluongtong.get(i).replaceAll(",", "")) * Double.parseDouble(sp.getDongia().replaceAll(",", ""));
						sp.setDongiaVND(dongiaVND.get(i));
						sp.setThanhtienVND(thanhtienVND.get(i));
						sp.setThanhtienVATVND(thanhtienVATVND.get(i));
						
						sp.setthanhtien(formatter.format(tt));
						sp.setSpDetail(listemp);
						this.spList.set(j, sp);
					}
				}
			}
			
			// chỉnh lại những sản phẩm nào có số lượng nhận = số lượng đơn hàng thì gach bỏ ra khỏi list sản phẩm
			/*for(int i=0; i< this.spList.size();i++){
				double n = Double.parseDouble(this.spList.get(i).getSoluongDaNhan().replaceAll(",", ""));
				double m = Double.parseDouble(this.spList.get(i).getSoluongdat().replaceAll(",", ""));
				if( n==m){
					this.spList.remove(i);
				}
			}*/
		}
		
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	
	public void init_Update() 
	{
		try{
		String query = 
		" SELECT count(TTHD.HOADON_FK) cophieuchi FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
		" WHERE TT.NCC_FK is not null AND TTHD.LOAIHD = 0 AND TT.TRANGTHAI != 2 AND TTHD.HOADON_FK = "+this.ID;

		System.out.println("init_update: " + query);
		ResultSet rs_hd = db.get(query);
		
		int phieuchi = 0;
		if(rs_hd != null)
		{
			try 
			{
				if(rs_hd.next())
				{
					phieuchi = rs_hd.getInt("cophieuchi");
				}
				rs_hd.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}	
		this.cophieuchi = phieuchi + "";
		
		// lấy thông tin hoá đơn nhập khẩu

		query ="  select NGAYGHINHAN, p.NCC_FK, PO_NHAPKHAU, p.TIENTE_FK, isNull(hoaDon.tiGia, '1') as TIGIA, p.LOAIHD, MAUSOHOADON" +
		       "  kyhieu, sohoadon, ngayhoadon ,sotienAVAT,vat,sotienBVAT,hoadon.PK_SEQ as hoadonncc,isnull(KHOBIETTRU_FK,0) as KHOBIETTRU_FK," +
		       "  isnull(KHOTONTRU_FK,0) as KHOTONTRU_FK , ISNULL(P.DIENGIAI,'') AS DIENGIAI,p.trangthai " +
		       "  from ERP_PARK p inner join ERP_HOADONNCC hoadon on p.pk_seq = hoadon.park_fk " +
		       "  where p.pk_seq = "+ this.ID;
		
		System.out.println("query thông tin HD"+ query);
		
		ResultSet rs = this.db.get(query);
		if(rs!=null){
			if(rs.next()){
				this.loaidonmhId = "0";
				this.NGAYGHINHAN = rs.getString("NGAYGHINHAN");
				this.NCCID = rs.getString("NCC_FK");
				this.ttId = rs.getString("TIENTE_FK");
				this.mauSoHoaDon = rs.getString("kyhieu");
				this.kyHieuHoaDon = rs.getString("kyhieu");
				this.soHoaDon = rs.getString("sohoadon");
				this.ngayHoaDon = rs.getString("ngayhoadon");
				this.tong = rs.getDouble("sotienAVAT");
				this.thuegtgt = rs.getString("vat");
				this.soTienChuaThue = rs.getString("sotienBVAT");
				this.hoadonNCC = rs.getString("hoadonncc");
				this.poId = rs.getString("PO_NHAPKHAU");
				this.tigia = rs.getString("TIGIA");
				// thêm 2 kho tồn trữ và biệt trữ
				this.idKhoBietTru = rs.getString("KHOBIETTRU_FK");
				if(this.idKhoBietTru.equals("0")){
					this.idKhoBietTru = "";
				}
				this.idKhoTonTru =  rs.getString("KHOTONTRU_FK");
				if(this.idKhoTonTru.equals("0")){
					this.idKhoTonTru = "";
				}
				this.dienGiaiCT = rs.getString("DIENGIAI");
			}
			
			rs.close();
		}
		
		
		this.createRs_update();
		
		// đã có list sản phẩm chỉnh lại vài thông số: tinh lai so luong da nhan roi
		if( this.hoadonNCC.trim().length() >0){
			
			List<String> sanpham_fk = new ArrayList<String>();
			List<String> soluongdat = new ArrayList<String>();
			List<String> thanhtien = new ArrayList<String>();
			List<String> ngaynhap = new ArrayList<String>();
			List<String> muahang_fk = new ArrayList<String>();
			
			List<Double> dongia = new ArrayList<Double>();
			
			query = "  select max( dh.sott) as sott  , dh.sanpham_fk, isnull((select SUM(SOLUONG) from ERP_HOADONNCC_DONMUAHANG a    \n " +
					" where a.MUAHANG_FK = dh.MUAHANG_FK and a.SANPHAM_FK = dh.SANPHAM_FK  and hoadonncc_fk not in (   \n "+
					" select a.pk_seq from ERP_HOADONNCC a inner join ERP_PARK b on b.pk_seq=a.park_fk where b.pk_seq = " + this.ID+ ")   \n " +
					" group by MUAHANG_FK, a.SANPHAM_FK),0) as sl, dh.MUAHANG_FK ," +
					" isnull(sum(dh.thanhtien),0) as thanhtien, dh.ngaynhandk, isnull(dh.dongia,0) as dongia  " +
					" from ERP_HOADONNCC_DONMUAHANG dh " +
					" inner join ERP_HOADONNCC hd on hd.PK_SeQ = dh.HOADONNCC_FK  " +
					" inner join ERP_PARK p on p.PK_SEQ = hd.park_fk " +
					"  where dh.MUAHANG_FK  in ( " + this.poId + " )   \n " +
					"  and p.trangthai = 1    \n " +
					"  group by dh.sanpham_fk, dh.ngaynhandk,dh.MUAHANG_FK, dh.dongia  " + 
					"  order by  dh.MUAHANG_FK ,sott ";
			
			rs = this.db.get(query);
			System.out.println("số lượng đặt haha"+ query);
			NumberFormat formatter = new DecimalFormat("#,###,###.###");
			NumberFormat formatter_4le = new DecimalFormat("#,###,###.####");
			if(rs!=null){
				while(rs.next()){
					sanpham_fk.add(rs.getString("sanpham_fk"));
					soluongdat.add(formatter_4le.format(rs.getDouble("sl")));
					thanhtien.add(formatter.format(rs.getDouble("thanhtien")));
					ngaynhap.add(rs.getString("ngaynhandk"));
					muahang_fk.add(rs.getString("muahang_fk"));
					dongia.add(rs.getDouble("dongia"));
				}
				rs.close();
			}
			
			// xử lý cập nhật lại list
			for(int i=0; i< this.spList.size();i++){
				// trường hợp câu select ở trên không trả
				// được về kết quả nào thì gán mặc định =0
				if(sanpham_fk.size() == 0){
					ISanpham sp = this.spList.get(i);
					sp.setSoluongDaNhan("0");
					sp.setNgaynhandukien("");
					sp.setthanhtien("0");
					sp.setThanhtienVATVND("0");
					this.spList.set(i, sp);
				} else {
					for(int j=0; j< sanpham_fk.size();j++){
						if(sanpham_fk.get(j).equals(this.spList.get(i).getId())  
								&& muahang_fk.get(j).equals(this.spList.get(i).getMuahang_fk())  
								&& dongia.get(j) == Double.parseDouble(this.spList.get(i).getDongia().replaceAll(",", ""))){
							ISanpham sp = this.spList.get(i);
							sp.setSoluongDaNhan(soluongdat.get(j));
							sp.setNgaynhandukien(ngaynhap.get(j));
							sp.setthanhtien("0");
							sp.setThanhtienVATVND("0");
							this.spList.set(i, sp);
							break;
						}
					}
				}
			}
			
			// xử lý hiện lại số lô
			query = " select dh.muahang_fk, dh.sanpham_fk, dh.soluong,isnull(NGAYSANXUAT,'') as nsx, " +
					" isnull(( select SUM(a.SOLUONG) from ERP_HOADONNCC_DONMUAHANG  a "+
					" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = dh.SANPHAM_FK " +
					" and a.MUAHANG_FK = dh.MUAHANG_FK and a.dongia = dh.dongia ),0) as soluongtong, "+
					" isnull(( select SUM(a.THANHTIENVIET) from ERP_HOADONNCC_DONMUAHANG  a "+
					" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = dh.SANPHAM_FK " +
					" and a.MUAHANG_FK = dh.MUAHANG_FK and a.dongia = dh.dongia),0) as thanhtienVIET, isnull(dh.DONGIAVIET,0) as DONGIAVIET, "+
					
					" isnull(( select SUM(a.THANHTIENVATVIET) from ERP_HOADONNCC_DONMUAHANG  a "+
					" where a.HOADONNCC_FK = "+this.hoadonNCC +" and a.SANPHAM_FK = dh.SANPHAM_FK " +
					" and a.MUAHANG_FK = dh.MUAHANG_FK and a.dongia = dh.dongia ),0) as thanhtienVATVIET,"+
					
					" isnull(NGAYHETHAN,'') as ngayhethan, dh.SOLO,isnull(dh.DONGIA, 0) as DONGIA  " +
					" from ERP_HOADONNCC_DONMUAHANG dh " +
					" where dh.HOADONNCC_FK =  " + this.hoadonNCC +
					" order by   dh.muahang_fk,dh.sott  ";
			
			rs = this.db.get(query);
			System.out.println("Xử lý số lô:"+ query);
			List<ISpDetail> listct = new ArrayList<ISpDetail>();
			List<String> soluongtong = new ArrayList<String>();
			List<String> thanhtienVND = new ArrayList<String>();
			List<String> dongiaVND = new ArrayList<String>();
			List<String> thanhtienVATVND = new ArrayList<String>();
			
			if(rs!=null){
				while(rs.next()){
					ISpDetail ct = new SpDetail();
					ct.setMa(rs.getString("sanpham_fk"));
					ct.setSoluong(formatter_4le.format(rs.getDouble("soluong")));
					ct.setNgaySx(rs.getString("nsx"));
					ct.setNgayHethan(rs.getString("ngayhethan"));
					ct.setMuahang_fk(rs.getString("muahang_fk"));
					ct.setSolo(rs.getString("SOLO"));
					ct.setDongia(rs.getDouble("DONGIA"));
					
					listct.add(ct);
					soluongtong.add(rs.getString("soluongtong"));
					thanhtienVND.add(rs.getString("thanhtienViet"));
					dongiaVND.add(rs.getString("DONGIAVIET"));
					thanhtienVATVND.add(rs.getString("THANHTIENVATVIET"));
				}
				rs.close();
			}
			
			// add list số lô vào
			for(int i=0; i< listct.size(); i++){
				ISpDetail temp  = listct.get(i);
				for(int j=0; j< this.spList.size(); j++){
					ISanpham sp = this.spList.get(j);
					if(temp.getMa().equals(sp.getId())   && temp.getMuahang_fk().equals(sp.getMuahang_fk())
							&& temp.getDongia() == Double.parseDouble(sp.getDongia().replaceAll(",", ""))){
						// add số lô từ danh sách lấy ở bước trên
						List<ISpDetail> listemp = sp.getSpDetail();
						listemp.add(temp);
						
						// tính lại số lượng nhận và thành tiền
						sp.setSoluongnhan(formatter_4le.format(Double.parseDouble(soluongtong.get(i).replaceAll(",", ""))));
						Double tt = Double.parseDouble(soluongtong.get(i).replaceAll(",", "")) * Double.parseDouble(sp.getDongia().replaceAll(",", ""));
						sp.setDongiaVND(dongiaVND.get(i));
						sp.setThanhtienVND(thanhtienVND.get(i));
						sp.setThanhtienVATVND(thanhtienVATVND.get(i));
						
						sp.setthanhtien(formatter.format(tt));
						sp.setSpDetail(listemp);
						this.spList.set(j, sp);
					}
				}
			}
			
			// chỉnh lại những sản phẩm nào có số lượng nhận = số lượng đơn hàng thì gach bỏ ra khỏi list sản phẩm
			/*for(int i=0; i< this.spList.size();i++){
				double n = Double.parseDouble(this.spList.get(i).getSoluongDaNhan().replaceAll(",", ""));
				double m = Double.parseDouble(this.spList.get(i).getSoluongdat().replaceAll(",", ""));
				if( n==m){
					this.spList.remove(i);
				}
			}*/
		}
		
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}

	
	
	
	private void createSanPhamDetail(){
		try{
			String sql =" select muahang_fk, sanpham_fk,dvt, soluong, dongia, " +
					    " thanhtien, soluongdat,ngaynhandk, VAT, TIENVAT, SOLO, NGAYSANXUAT, NGAYHETHAN  " +
					    " from ERP_HOADONNCC_DONMUAHANG  where HOADONNCC_FK =" + this.hoadonNCC+
					    " order by SANPHAM_FK";
			ResultSet rs = this.db.get(sql);
			List<ISanpham> list = new ArrayList<ISanpham>();
			if( rs!=null){
				while(rs.next()){
				
				}
			}
			
		} catch(Exception ex){
			ex.printStackTrace();
		}
	}
	public void initDisplay() 
	{
		String query = 
		" SELECT count(TTHD.HOADON_FK) cophieuchi, ( SELECT isnull(trangthai,0) FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") TRANGTHAIHD \n" +
		" FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ  \n"+
		" WHERE TT.NCC_FK is not null AND TTHD.LOAIHD = 0 AND TT.TRANGTHAI != 2 AND TTHD.HOADON_FK = "+this.ID;

		System.out.println("init: " + query);
		ResultSet rs_hd = db.get(query);
		
		int phieuchi = 0;
		if(rs_hd != null)
		{
			try 
			{
				if(rs_hd.next())
				{
					phieuchi = rs_hd.getInt("cophieuchi");
					this.trangthaiHd = rs_hd.getInt("TRANGTHAIHD")+"";
					System.out.println("asdsadsasadsa"+trangthaiHd);
				}
				rs_hd.close();
			}
			catch (SQLException e) {
				e.printStackTrace();
			}
		}
		
		this.cophieuchi = phieuchi + "";
		
		query = "SELECT DISTINCT PARK.TRANGTHAI,PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, HD.TINHTHUENHAPKHAU,  ISNULL(PARK.TIGIA,1) AS TIGIA ,  \n" +
				"  		TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU, \n" +
				"  		NCCTT.PK_SEQ AS NCCTTID, ISNULL(NCCTT.TEN,'') AS NCCTTTEN, ISNULL(NCCTT.MASOTHUE,'') AS MST, ISNULL(NCCTT.DIACHI,'') DIACHI, HD.LOAIHD  \n"+
				"FROM ERP_HOADONNCC HD \n" +
				"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK \n" + 
				"INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_DMH ON HD_DMH.HOADONNCC_FK = HD.PK_SEQ \n" +
				"INNER JOIN ERP_NHAPKHONHAPKHAU KHONK ON KHONK.PK_SEQ = HD_DMH.MUAHANG_FK \n"+
				"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = KHONK.MUAHANG_FK \n" +
				"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = MH.TIENTE_FK \n" +
				"LEFT JOIN ERP_NHACUNGCAPTHAYTHE NCCTT ON NCCTT.PK_SEQ = PARK.NCCTHAYTHE_FK \n" +
				"WHERE HD.PARK_FK = '" + this.ID + "'";  


		System.out.println("init: " + query);
		ResultSet rs = db.get(query);
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{

					this.ttId = rs.getString("TTID");
					this.tiente = rs.getString("TIENTE");
					this.tigia = rs.getString("TIGIA");

					this.NCCID = rs.getString("NCCID");
					this.NGAYGHINHAN=rs.getString("NGAYGHINHAN");
					this.TinhThueNhapKhau=rs.getString("TINHTHUENHAPKHAU");	
					this.loaidonmhId = rs.getString("LOAIHD");
					this.TRANGTHAI = rs.getString("TRANGTHAI");
					
					// Load NCC thay thế nếu có
					
					this.nccThayTheId = rs.getString("NCCTTID");	
					this.nccThayTheTen = rs.getString("NCCTTTEN");	
					this.nccThayTheMST = rs.getString("MST");	
					this.nccThayTheDiaChi = rs.getString("DIACHI");	
					
					this.poNkId = rs.getString("PO_NHAPKHAU");	
					
				}else{
					// Truong hop Display nhung chung tu dau ky
						query = " SELECT DISTINCT PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, PARK.TINHTHUENHAPKHAU, " +
								" TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU " +
								" FROM ERP_HOADONNCC HD " +
								" INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " + 
								" INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = PARK.TIENTE_FK " +
								" WHERE HD.PARK_FK = '" + this.ID + "'";  
					System.out.println("Hien thi: " + query);
					rs = db.get(query);
					if(rs != null)
					{
						try 
						{
							while(rs.next())
							{
								this.ttId = rs.getString("TTID");
								this.tiente = rs.getString("TIENTE");

								this.NCCID = rs.getString("NCCID");
								this.NGAYGHINHAN=rs.getString("NGAYGHINHAN");
								this.TinhThueNhapKhau=rs.getString("TINHTHUENHAPKHAU");				
								this.poNkId = rs.getString("PO_NHAPKHAU");
							}
							rs.close();

						} 
						catch (Exception e) {}
					}	
				}
					
			} 
			catch (SQLException e) {}
		}		
		
		
		 
		this.getNppInfo();
		
		this.nccRs = db.get("select PK_SEQ, MA + ', ' + TEN as TEN from ERP_NHACUNGCAP WHERE CONGTY_FK = "+this.CtyId);

		this.loaidonmhRs = db.get("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");
		
		System.out.println("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");
		
		
		if(this.NCCID.length() > 0)
		{
			query = "";			
			query = "select pk_seq as TTID, MA AS TIENTE from ERP_TIENTE where trangthai = '1' AND PK_SEQ = 100000 ";
			
			System.out.println("___LAY TIEN TE 22: " + query);
			ttRs = this.db.get(query);
			
			// Tiền việt > tỉ giá : 1
			if(this.ID.trim().length() <= 0 && this.ttId.trim().length() > 0)
			{
				if(this.ttId.equals("100000")) this.tigia = "1";
			}
			
			// Load POLIST
			
			if(this.loaidonmhId.trim().length()>0)
			{
				query = " select distinct PK_SEQ, NGAYNHAP NGAYMUA,  PK_SEQ AS SOPO,tooltip, 0 LOAI \n"+   
						" from ERP_NHAPKHONHAPKHAU a  " +
						" WHERE a.PK_SEQ IN (SELECT B.MUAHANG_FK FROM ERP_HOADONNCC A INNER JOIN ERP_HOADONNCC_DONMUAHANG B ON A.pk_seq = B.HOADONNCC_FK" +
						"					INNER JOIN ERP_PARK C ON A.park_fk = C.pk_seq " +
						"					WHERE C.PK_SEQ = "+this.ID+"  )"+
						"		AND a.CONGTY_FK = "+this.CtyId+"  "+
						" order by NGAYMUA desc";
					
				
			}	
			
			System.out.println("LAY PO : " + query );
			this.poNKRs = db.get(query);
			
			
		}
	  
				List<IErpHoadon> hdList = new ArrayList<IErpHoadon>();
								
				if(this.ID.trim().length() > 0&& this.hdList.size() <= 0)
				{
					query =	
						"\n SELECT DISTINCT HD.*, isnull(HD.TONGSOLUONG,0) as TONGLUONG ,  TT.MA AS TIENTE, TT.PK_SEQ AS TTID " + 
						"\n FROM ERP_HOADONNCC HD " +
						"\n INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " + 
						"\n INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = PARK.TIENTE_FK " + 
						"\n WHERE HD.PARK_FK = " + this.ID; 
						
						System.out.println(query);
						
						ResultSet hdRs = db.get(query);
						
						List<IErpHoadonSp> spList = new ArrayList<IErpHoadonSp>();
						
						if(hdRs != null)
						{
							 IErpHoadon hoadon = null;	
							try 
							{							 
								while(hdRs.next())
								{
									String mauhoadon = "";
									if(hdRs.getString("mauhoadon") != null)
										mauhoadon = hdRs.getString("mauhoadon").toString();
									
									String mausohoadon = "";
									if(hdRs.getString("mausohoadon") != null)
										mausohoadon = hdRs.getString("mausohoadon").toString();
									
									String pk_seq = hdRs.getString("pk_seq");
									String kyhieu = hdRs.getString("kyhieu");
									String sohoadon = hdRs.getString("sohoadon");
									String ngayhoadon = hdRs.getString("ngayhoadon");
									String sotienavat = hdRs.getString("sotienAVAT");
									String vat = hdRs.getString("vat");
									String sotienbvat = hdRs.getString("sotienBVAT");
									String park = hdRs.getString("park_fk");
									int thuesuat= hdRs.getInt("thuesuat");
									
									String trangthai = hdRs.getString("trangthai");
									String tiente = hdRs.getString("tiente");
									this.ttId = hdRs.getString("TTID");
									
									hoadon = new ErpHoadon(pk_seq, kyhieu, String.valueOf(thuesuat), sohoadon, park, ngayhoadon, sotienavat, sotienbvat, vat, "", trangthai, "");
									hoadon.setMauhoadon(mauhoadon);
									hoadon.setMausohoadon(mausohoadon);
									
									// LẤY SẢN PHẨM HÓA ĐƠN
									query =				
										
									"\n SELECT distinct A.PK_SEQ, A.NGAYNHAP, A.PK_SEQ SOPO,a.tooltip, 0 DUNGSAI, "+ 
									"\n 0 AS LOAI, B.SANPHAM_FK AS SPID, SP.MA, SP.TEN, ISNULL(B.DONVI, '') DONVI, "+
									"\n B.SOLUONG - isnull(HD.SOLUONG,0) as SOLUONGDAT, ISNULL(HD.SOLUONG,0) AS SOLUONGDARAHD, "+
									"\n isnull(HDSP.SOLUONG,0) AS SOLUONGHD,  ISNULL(HDSP.DONGIA, 0) DONGIA, ISNULL( HDSP.THANHTIEN, 0) THANHTIEN, "+
									"\n 0 as chiphikhac, isnull(B.VAT,0) thuesuat, ISNULL(HDSP.TIENVAT,0) TIENVAT, ISNULL(B.SOLO,'') SOLO  "+
									"\n FROM ERP_NHAPKHONHAPKHAU A "+
									"\n INNER JOIN ERP_NHAPKHONHAPKHAU_SP_CHITIET B ON A.PK_SEQ = B.NHAPKHO_FK "+
									"\n INNER JOIN SANPHAM SP ON SP.PK_SEQ = B.SANPHAM_FK "+
									"\n LEFT JOIN "+
									"\n ( "+
									"\n   SELECT A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '') SOLO , A.DONGIA ,A.THANHTIEN, A.TIENVAT, SUM(SOLUONG) AS SOLUONG "+
									"\n   FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
									"\n   WHERE B.trangthai !=4 AND A.HOADONNCC_FK = "+pk_seq+" AND A.SOLUONG > 0 "+
									"\n   GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, ''),A.DONGIA , A.THANHTIEN, A.TIENVAT "+
									"\n )HDSP ON HDSP.MUAHANG_FK = A.PK_SEQ AND HDSP.SANPHAM_FK = B.SANPHAM_FK AND HDSP.SOLO = B.SOLO "+
									"\n LEFT JOIN "+
									"\n ( "+
									"\n SELECT A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '') SOLO , SUM(SOLUONG) AS SOLUONG "+
									"\n FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
									"\n WHERE trangthai !=4 AND A.HOADONNCC_FK != "+pk_seq+" AND A.SOLUONG > 0 "+ 
									"\n GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '') "+
									"\n )HD ON HD.MUAHANG_FK = A.PK_SEQ AND HD.SANPHAM_FK = B.SANPHAM_FK AND HD.SOLO = B.SOLO "+
									"\n WHERE (B.SOLUONG  - ISNULL(HD.SOLUONG, 0)) > 0 and A.PK_SEQ IN ("+this.poNkId+")";
									 
									
									/*" SELECT distinct A.PK_SEQ, A.NGAYNHAP, A.PK_SEQ SOPO,a.tooltip, 0 DUNGSAI, \n"+
									"	0 AS LOAI, B.SANPHAM_FK AS SPID, SP.MA, SP.TEN,B.DONVI, \n"+
									"	B.SOLUONG - isnull(HD.SOLUONG,0) as SOLUONGDAT, ISNULL(HD.SOLUONG,0) AS SOLUONGDARAHD, \n"+
									"	isnull(HDSP.SOLUONG,0) AS SOLUONGHD,  HDSP.DONGIA, HDSP.THANHTIEN, \n"+ 
									"	0 as chiphikhac, \n"+
									"	(SELECT distinct SHSP.NGAYNHAN \n"+ 
									"	 FROM ERP_SHIPHANG SH inner join ERP_SHIPHANG_SANPHAM SHSP on SH.PK_SEQ = SHSP.SHIPHANG_FK \n"+ 
									"	 where SH.MUAHANG_FK = MH.PK_SEQ and SH.NCC_FK = A.NCC_FK AND SH.TRANGTHAI = 1 \n"+ 		 
									"	 ) NGAYNHANDK, isnull(B.VAT,0) thuesuat, ISNULL(HDSP.TIENVAT,0) TIENVAT  \n"+
									" FROM ERP_NHAPKHONHAPKHAU A \n"+
									" INNER JOIN ERP_NHAPKHONHAPKHAU_SANPHAM B ON A.PK_SEQ = B.NHAPKHO_FK \n"+
									" INNER JOIN SANPHAM SP ON SP.PK_SEQ = B.SANPHAM_FK \n"+ 
									" INNER JOIN ERP_MUAHANG_SP MHSP ON A.MUAHANG_FK = MHSP.MUAHANG_FK AND B.SANPHAM_FK = MHSP.SANPHAM_FK \n"+
									" INNER JOIN ERP_MUAHANG MH ON A.MUAHANG_FK = MH.PK_SEQ \n"+  
									" LEFT JOIN \n"+ 
									" ( \n"+ 
									"	SELECT A.* \n"+ 
									"   FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq \n"+ 
									"   INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ \n"+
									"	WHERE C.trangthai !=4 AND A.HOADONNCC_FK = "+pk_seq+" \n"+
									" )HDSP ON HDSP.MUAHANG_FK = A.PK_SEQ AND HDSP.SANPHAM_FK = B.SANPHAM_FK \n"+ 
									" LEFT JOIN \n"+ 
									" ( \n"+ 
									" SELECT A.MUAHANG_FK, A.SANPHAM_FK, SUM(SOLUONG) AS SOLUONG \n"+  								 
									" FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq \n"+ 
									" WHERE trangthai !=4 AND A.HOADONNCC_FK != "+pk_seq+" \n"+
									" GROUP BY A.MUAHANG_FK, A.SANPHAM_FK \n"+  
									" )HD ON HD.MUAHANG_FK = A.PK_SEQ AND HD.SANPHAM_FK = B.SANPHAM_FK \n"+ 
									" WHERE (B.SOLUONG  - ISNULL(HD.SOLUONG, 0)) > 0 and A.PK_SEQ IN ("+this.poNkId+")";*/
										  							   
									System.out.println(query);
									ResultSet hdsp = db.get(query);
									
									while(hdsp.next())
									{
										String poId = hdsp.getString("PK_SEQ");
										String poTen = hdsp.getString("SOPO");
										String sanphamId = hdsp.getString("SPID");
										String sanphamten = hdsp.getString("TEN");
										String sanphamma = hdsp.getString("MA");
										String soluongdat = hdsp.getString("SOLUONGDAT");
										String soluonghd = hdsp.getString("SOLUONGHD");					
										String dongia = hdsp.getString("DONGIA");
										String thanhtien = hdsp.getString("THANHTIEN");
										String donvi = hdsp.getString("DONVI");
										String loai = hdsp.getString("LOAI");
										String chon = "0";
										String tiente_fk = "";
										String dungsai = hdsp.getString("DUNGSAI");
										String solo = hdsp.getString("SOLO");
										//String ngaynhandk = hdsp.getString("NGAYNHANDK");
										String soluongdaraHD = hdsp.getString("SOLUONGDARAHD");
										String sp_vat = hdsp.getString("thuesuat");
										String sp_tienvat = hdsp.getString("TIENVAT");
										
										IErpHoadonSp sp = new ErpHoadonSp(poId, poTen, sanphamId, sanphamten, soluongdat, dongia, thanhtien, donvi, loai, chon, tiente_fk);
										sp.setSanphamMa(sanphamma);
										sp.setSoluonghd(soluonghd);
										sp.setDungsai(dungsai);	
										sp.setSoluongRaHD(soluongdaraHD);
										//sp.setNgaynhan(ngaynhandk);
										sp.setSolo(solo);
										sp.setVAT(sp_vat);
										sp.setTienVat(sp_tienvat);
										spList.add(sp);
									}
									
									hoadon.setPnkList(spList);
									hdList.add(hoadon);
									
								}
							}
							catch (Exception e) { 
								e.printStackTrace(); 
							}
						}
						  
						this.hdList = hdList;
					}
		
	
	}
	
	private boolean CheckPOCungTienTie(){
		return false;
	}
	public boolean createPark() 
	{
		// tam thoi bo rang, de sau lam
		/*if(ktsohoadon_ncc()==true){
			return false;
		}*/
		
		String ngaytao = this.getDateTime();
		
		if(this.loaidonmhId.trim().length()<=0)
		{
			this.MSG =  "Vui lòng chọn loại hóa đơn";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.MSG = "Vui lòng nhập thông tin hóa đơn";
			return false;
		}
		
		if(this.NCCID.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn nhà cung cấp";
			return false;
		}
		
		if(this.poId.trim().length() <=0 )
		{
			this.MSG = "Vui lòng chọn PO";
			return false;
		}
		
		if(this.loaidonmhId.equals("0")) // HÓA ĐƠN  NHẬP KHẨU
		{
			if(this.NCCID.length()== 0)
			{
				this.MSG = "Không thể tạo hóa đơn,thiếu thông tin vui lòng kiểm tra lại!";
				return false;
			}
		}
		
		if(this.hdList.size() > 0)
		{
			for(int i = 0; i < this.hdList.size(); i++)
			{
				IErpHoadon hd = hdList.get(i);
				double sotientong=Double.parseDouble(hd.getSotienavat());
				if(sotientong<0)
				{
					this.MSG = "Tổng tiền là "+ sotientong +" không thể tạo hóa đơn!";
					return false;
				}
			}
		}
		
		//gang bat buoc phai co kho ton tru va biet tr
		if(this.idKhoBietTru.trim().length()<=0){
			this.MSG = "Vui lòng chọn đầy đủ kho biệt trữ và kho tồn trữ ";
			return false;
		}
		
		if(this.idKhoTonTru.trim().length()<=0){
			this.MSG = "Vui lòng chọn đầy đủ kho biệt trữ và kho tồn trữ ";
			return false;
		}
		
		
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			String query = "";
					
			
			// LẤY TỈ GIÁ
			// THUE NHAP KHAU = 1 >>  TIGIA lấy lúc nhập vào
			// CÒN LẠI >> LẤY NHƯ DƯỚI
			
			if(this.ttId.equals("100000") )
			{
				this.tigia = "1";
			}
			
			
			
			query = " insert ERP_PARK(NGAYGHINHAN, NCC_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, PO_NHAPKHAU, TIENTE_FK, TIGIA, LOAIHD, NPP_FK,DIENGIAI) " +
					" values('"+ this.NGAYGHINHAN + "', '" + this.NCCID + "', '" + ngaytao + "', '" + ngaytao + "', '" + this.USERID + "', '" + this.USERID + "', '0', '"+
					 this.CtyId+"', '" + this.poId + "', " + this.ttId + ", " + this.tigia + ", "+this.loaidonmhId+", "+this.nppdangnhap+" ,N'"+this.dienGiaiCT+"' )";
			
			if(!db.update(query))
			{
				this.MSG = "Không thể tạo Park lỗi: " + query;
				db.getConnection().rollback();
				return false;
			}
			
			String parkCurrent = "";//Lay so PK_SEQ park vua insert
			query = "select SCOPE_IDENTITY() as parkId";
			
			ResultSet rsNh = db.get(query);						
			if(rsNh.next())
			{
				parkCurrent = rsNh.getString("parkId");
				rsNh.close();
			}
					
			// kiểm tra thời hạn nợ của NCC.
			query ="select isnull(THOIHANNO,0) as thoihanno from ERP_NHACUNGCAP where PK_SEQ = "+ this.NCCID;
			int thoihanno= 0;
			ResultSet rs = this.db.get(query);
			if(rs!=null){
				if(rs.next())
					thoihanno = rs.getInt("thoihanno");
				rs.close();
			}
			
			String ngaythanhtoan = ngaytao;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(ngaythanhtoan));
			c.add(Calendar.DATE, thoihanno);  // number of days to add
			ngaythanhtoan = sdf.format(c.getTime()); 
			
			
			// insert hoá đơn NCC. 1 hoá đơn chứ mấy
			if(this.loaidonmhId.equals("0")) 
				this.TinhThueNhapKhau = "1";
			else
				this.TinhThueNhapKhau = "0";
			
			if(this.idKhoBietTru.trim().length() == 0){
				this.idKhoBietTru = "NULL";
			}
			
			if(this.idKhoTonTru.trim().length() == 0){
				this.idKhoTonTru = "NULL";
			}
			
			query = " insert ERP_HOADONNCC(CHENHLECHHD,  mauhoadon, mausohoadon, kyhieu, sohoadon, ngayhoadon ,sotienAVAT," +
					" sotienchietkhau,vat,sotienBVAT,park_fk, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA," +
					" TRANGTHAI, NGAYDENHANTT, TINHTHUENHAPKHAU,CONGTY_FK, NPP_FK, LOAIHD, KHOBIETTRU_FK, KHOTONTRU_FK) " +
					" values("+this.ChenhlechHd+",'', '"+this.mauSoHoaDon+"', '" + this.kyHieuHoaDon.toUpperCase() + "', '" + this.soHoaDon + "', '" + 
					this.ngayHoaDon+ "'," + this.tong+","+0+ "," + this.thuegtgt+","
					+ this.soTienChuaThue+",'"+ parkCurrent +"', '"+ ngaytao + "','" + ngaytao + "', '" + this.USERID + "'," +
					" '" + this.USERID + "', '0', '"+ ngaythanhtoan+"', "+TinhThueNhapKhau+", "+
					this.CtyId+", "+this.nppdangnhap+", "+this.loaidonmhId+","+this.idKhoBietTru+","+this.idKhoTonTru+")";
			
			System.out.println(query);
			
			if(!db.update(query))//Luu vào bảng ERP_HOADONNCC
			{
				this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			String hdCurrent = "";//Lay PK_SEQ vua insert
			query = "select IDENT_CURRENT('ERP_HOADONNCC') as hdId";
		
			ResultSet rsHd = db.get(query);						
			if(rsHd.next())
			{
				hdCurrent = rsHd.getString("hdId");
				rsHd.close();
			}

			// insert phần chi tiết
			if(this.spList.size() > 0){
				
				for(int i=0; i < this.spList.size(); i++ ){
					ISanpham sanpham = this.spList.get(i);
					
					long pbThanhTienVND_temp = 0;
					long pbThanhTienVATVND_temp = 0;
					
					for(int j = 0; j < sanpham.getSpDetail().size(); j++){	
						
						ISpDetail sp = sanpham.getSpDetail().get(j);
						
						double tienvat =0;
						double thanhtien = 0;
						tienvat = Double.parseDouble(sp.getSoluong().replaceAll(",", "")) 
						* Double.parseDouble(sanpham.getDongia().replaceAll(",", "")) * Double.parseDouble(sanpham.getVat())/100;
						long tienvat_temp = Math.round(tienvat);
						
						
						thanhtien = Double.parseDouble(sp.getSoluong().replaceAll(",", "")) 
						* Double.parseDouble(sanpham.getDongia().replaceAll(",", ""));
						long thanhtien_temp = Math.round(thanhtien);
						
						String nsxid=(sp.getNSXid()==null || sp.getNSXid().trim().length()==0)?null:sp.getNSXid();
						String marrquet=sp.getMarrquet()==null?"":sp.getMarrquet();
						
						if(marrquet.trim().length()>0){
							String queryCheck="select count(*) dem from MARQUETTE where MA like N'"+marrquet+"' and sanpham_fk="+sanpham.getId()+" ";
							System.out.println("CHECK MARQUETTE: " + queryCheck);
							ResultSet rsCheckMAQR=db.get(queryCheck);
							if(rsCheckMAQR!=null){
								while(rsCheckMAQR.next()){
									if(rsCheckMAQR.getDouble("dem")!=1){
										this.MSG = "Không tìm được mã marquette tương ứng ";
										rsCheckMAQR.close();
										db.getConnection().rollback();
										return false;
									}
								}
								rsCheckMAQR.close();
							}
						}
						
						if(j < (sanpham.getSpDetail().size()-1)){
							pbThanhTienVND_temp += thanhtien_temp;
							pbThanhTienVATVND_temp +=  tienvat_temp;
						}
						String is_khongthue="(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sanpham.getMuahang_fk()+"' and SANPHAM_FK='"+sanpham.getId()+"')";
						query = " insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk, sanpham_fk, " +
								" dvt, soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, " +
								" SOLO, NGAYSANXUAT, NGAYHETHAN, thanhtienVIET, thanhtienVATVIET,is_khongthue,nsx_fk,marrquet) " +
								"values('" + hdCurrent + "', " + sanpham.getMuahang_fk()+ ",  "+sanpham.getId()
								+", N'"+sanpham.getDvdl()+"' ," + sp.getSoluong().replaceAll(",", "") 
								+", '"+ sanpham.getDongia().replaceAll(",", "") +"'," + thanhtien_temp + ", '"
								+sp.getSoluong().replaceAll(",", "")+"', '"+sanpham.getNgaynhandukien()+"', "
								+sanpham.getVat()+", "+ tienvat_temp +", '"+sp.getSolo()+"', '"+sp.getNgaySx()+"', '"
								+sp.getNgayHethan()+"',"+thanhtien_temp+","+tienvat_temp+","+is_khongthue+","+nsxid+",'"+marrquet+"')";
						
						// phân bổ lại lô cuối cùng để cộng phần lẻ VAT và phần lẻ thành tiền VIỆT
						if(j == (sanpham.getSpDetail().size()-1)){
							long pbThanhTienVND = Long.parseLong(sanpham.getthanhtien().replaceAll(",", ""));
							long pbThanhTienVATVND = Long.parseLong(sanpham.getThanhtienVATVND().replaceAll(",", ""));
							
							// phần tử cuối cùng.
							long last_item_thanhtien = pbThanhTienVND-pbThanhTienVND_temp;
							long last_item_thanhtien_VAT = pbThanhTienVATVND - pbThanhTienVATVND_temp;
							
							query = " insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk, sanpham_fk, " +
							" dvt, soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, " +
							" SOLO, NGAYSANXUAT, NGAYHETHAN, thanhtienVIET, thanhtienVATVIET,is_khongthue,nsx_fk,marrquet) " +
							"values('" + hdCurrent + "', " + sanpham.getMuahang_fk()+ ",  "+sanpham.getId()
							+", N'"+sanpham.getDvdl()+"' ," + sp.getSoluong().replaceAll(",", "") 
							+", '"+ sanpham.getDongia().replaceAll(",", "") +"'," + last_item_thanhtien + ", '"
							+sp.getSoluong().replaceAll(",", "")+"', '"+sanpham.getNgaynhandukien()+"', "
							+sanpham.getVat()+", "+ last_item_thanhtien_VAT +", '"+sp.getSolo()+"', '"+sp.getNgaySx()+"', '"
							+sp.getNgayHethan()+"',"+last_item_thanhtien+","+last_item_thanhtien_VAT+","+is_khongthue+","+nsxid+",'"+marrquet+"')";
						}
						
						System.out.println("  Insert PN: " + query);
						if(!db.update(query)){
							this.MSG = "Không thể tạo mới ERP_HOADONNCC_DONMUAHANG : " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
					}
				}
			}
			
			
			// cập nhật ĐƠN MUA HÀNG sang trạng thái số 2.
			if( this.muahang_fk.trim().length() >0){
				boolean check  = UpdateTrangThaiDonMuaHang();
				if( check == false){
					this.db.update("rollback");
					this.MSG ="Không update được trạng thái đơn mua hàng";
					return false;
				} 
			}
			
			// CẬP NHẬT TOOLTIP
		
			db.execProceduce2("CapNhatTooltip_HoaDonNCC", new String[] { parkCurrent });

			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e)
		{
			this.db.update("rollback");
			this.MSG = "Lỗi : "+ e.toString();
			e.printStackTrace();
			return false;
		}
	}

	// hàm kiểm tra xem còn số lượng sản phẩm của đơn nhận hàng hay không? nếu còn thì hiện ra tiếp cho những lần sau nhận hàng
	// nếu hết thì chuyển trạng thái nhận hàng hết thành 1 để khỏi phải lấy PO này lên
	private boolean UpdateTrangThaiDonMuaHang(){
		try{
			
			// truy vấn mua hàng
			String sql =  " select a.MUAHANG_FK, SUM(a.SOLUONG +  a.soluong* isnull(mh.dungsai,0)/100    ) as muahang " +
				      " from ERP_MUAHANG_SP  a inner join erp_muahang mh on mh.pk_seq= a.muahang_fk  " +
				      " where a.MUAHANG_FK  in ("+this.muahang_fk+") " +
				      " group by a.MUAHANG_FK  order by a.muahang_fk desc";
			
			List<String> list = new ArrayList<String>();
			List<Double> listmuahang = new ArrayList<Double>();
			
			System.out.println("truy vấn mua hàng"+ sql);
			ResultSet rs = this.db.get(sql);
			if( rs!=null){
				while(rs.next()){
					list.add(rs.getString("MUAHANG_FK"));
					listmuahang.add(rs.getDouble("muahang"));
				}
				rs.close();
			}
			
			
			
			List<String> list2 = new ArrayList<String>();
			List<Double> listhoadon = new ArrayList<Double>();
			
			sql =  " select a.MUAHANG_FK, SUM(a.SOLUONG) as hoadon " +
			      " from ERP_HOADONNCC_DONMUAHANG  a inner join ERP_HOADONNCC b on b.pk_seq=a.HOADONNCC_FK   where    b.trangthai not in (4) and" +
			      "  a.MUAHANG_FK  in ("+this.muahang_fk+") " +
			      " group by a.MUAHANG_FK order by a.muahang_fk desc";
			
			System.out.println("truy vấn hoá đơn: "+ sql);
			rs = this.db.get(sql);
			if( rs!=null){
				while(rs.next()){
					list2.add(rs.getString("MUAHANG_FK"));
					listhoadon.add(rs.getDouble("hoadon"));
				}
				rs.close();
			}
			
			for(int i=0; i< list.size(); i++){
				// trạng thái ==2
				if(listhoadon.get(i) >= listmuahang.get(i)){
					list2.set(i, "");
				} else { // trạng thái ==1
					list.set(i, "");
				}
			}
			
		   String muahang = "";
		   if (list.size() >0 )
			{
				for(int i = 0; i < list.size(); i++)
					muahang += list.get(i) + ",";
				
				if(muahang.trim().length() > 0)
				{
					muahang = muahang.substring(0, muahang.length() - 1);
				}
			}
		   
		   String hoadon = "";
		   if (list2.size() >0 )
			{
				for(int i = 0; i < list2.size(); i++)
					hoadon += list2.get(i) + ",";
				
				if(hoadon.trim().length() > 0)
				{
					hoadon = hoadon.substring(0, hoadon.length() - 1);
				}
			}
		   
		   if( hoadon.trim().length() >0){
			   db.update("update ERP_MUAHANG set trangthai = '1' where pk_seq in ( "+hoadon+")");
		   }
		   if( muahang.trim().length() >0){
			   db.update("update ERP_MUAHANG set trangthai = '2' where pk_seq in ( "+muahang+")");
		   }
		
		   return true;
		} catch ( Exception ex){
			ex.printStackTrace();
			return false;
		}
		
	}
	
	
	public boolean updatePark() 
	{
		//System.out.println("vao ham update");
		String ngaytao = this.getDateTime();
		
		if(this.loaidonmhId.trim().length()<=0)
		{
			this.MSG =  "Vui lòng chọn loại hóa đơn";
			return false;
		}
		
		if(this.spList.size() <= 0)
		{
			this.MSG = "Vui lòng nhập thông tin hóa đơn";
			return false;
		}
		
		if(this.NCCID.trim().length() <= 0)
		{
			this.MSG = "Vui lòng chọn nhà cung cấp";
			return false;
		}
		
		if(this.poId.trim().length() <=0 )
		{
			this.MSG = "Vui lòng chọn Số nhập ";
			return false;
		}
		
		if(this.loaidonmhId.equals("0")) // HÓA ĐƠN NHẬP KHẨU - KIỂM TRA TỈ GIÁ
		{
			 SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			 Date date = new Date();
			 String today = sdf.format(date);
			 
			 // xác định tỉ giá dựa vào PO
			String query = "  select top(1) a.pk_seq as TTID, MA AS TIENTE, b.TIGIAQUYDOI from ERP_TIENTE a " +
			"  inner join ERP_TIGIA b on a.PK_SEQ = b.TIENTE_FK " +
			"  where a.trangthai = '1'  AND a.PK_SEQ = (select top(1) TIENTE_FK " +
			"  from ERP_MUAHANG where PK_SEQ in ( "+this.poId+") " +
			"  and b.TuNgay <='"+today+"' and '"+today+"' <= b.DenNgay)";
			
			ResultSet rs = this.db.get(query);
			try{
				if(rs!=null){
					if(rs.next()){
						this.tigia = rs.getString("TIGIAQUYDOI");
					}
					rs.close();
				}
			} catch(Exception ex){
				ex.printStackTrace();
				this.MSG =" không xác định được tỉ giá";
				return false;
			}
			
			if(this.tigia == null)
				this.tigia = "0";
			
			if(this.tigia.trim().length() <= 0) this.tigia = "0";
			if(!this.ttId.equals("100000")){
				if(Double.parseDouble(this.tigia.replaceAll(",", "")) <= 0) 
				{
					this.MSG = "Tỉ giá phải nhập lớn hơn 0 !";
					return false;
				}
			}
		}
		
		
		//gang bat buoc phai co kho ton tru va biet tr
		if(this.idKhoBietTru.trim().length()<=0){
			this.MSG = "Vui lòng chọn đầy đủ kho biệt trữ và kho tồn trữ ";
			return false;
		}
		
		if(this.idKhoTonTru.trim().length()<=0){
			this.MSG = "Vui lòng chọn đầy đủ kho biệt trữ và kho tồn trữ ";
			return false;
		}
		

		try 
		{
			db.getConnection().setAutoCommit(false);
			String query = 	"";
			
			// LẤY TỈ GIÁ
						// THUE NHAP KHAU = 1 >>  TIGIA lấy lúc nhập vào
						// CÒN LẠI >> LẤY NHƯ DƯỚI
						
			if(this.ttId.equals("100000") )
			{
				this.tigia = "1";
			}
						
			query = " UPDATE  ERP_PARK  " +
					" SET NGAYGHINHAN='" + this.NGAYGHINHAN + "', NGAYSUA = '" + getDateTime() + "', " +
				   	" NCC_FK='" + this.NCCID + "', TINHTHUENHAPKHAU = '" + this.TinhThueNhapKhau + "', PO_NHAPKHAU = '" + this.poId + "'," +
				   	" TIENTE_FK = " + this.ttId + ", TIGIA =  " + tigia + " " + "," +
				   	" DIENGIAI = N'"+this.dienGiaiCT+"'" +
				   	" WHERE PK_SEQ='"+this.ID+"'";
			System.out.println(query);
			
			if(!db.update(query))
			{
				this.MSG = "1.Không cập nhật Park lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}
			// cập nhật hoá đơn NCC
			
			
			// xoá hoá đơn NCC
			query = "delete ERP_HOADONNCC_DONMUAHANG where HOADONNCC_FK IN (SELECT PK_SEQ FROM ERP_HOADONNCC WHERE park_fk = '" + this.ID + "') ";
			System.out.println(query);
			if(!db.update(query))
			{
				this.MSG = "3.Không cập nhật Park lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}
			
			// kiểm tra thời hạn nợ của NCC.
			query ="select isnull(THOIHANNO,0) as thoihanno from ERP_NHACUNGCAP where PK_SEQ = "+ this.NCCID;
			int thoihanno= 0;
			ResultSet rs1 = this.db.get(query);
			if(rs1!=null){
				if(rs1.next())
					thoihanno = rs1.getInt("thoihanno");
				rs1.close();
			}
			
			String ngaythanhtoan = ngaytao;
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			Calendar c = Calendar.getInstance();
			c.setTime(sdf.parse(ngaythanhtoan));
			c.add(Calendar.DATE, thoihanno);  // number of days to add
			ngaythanhtoan = sdf.format(c.getTime()); 
			
			
			// insert hoá đơn NCC. 1 hoá đơn chứ mấy
			if(this.loaidonmhId.equals("0")) 
				this.TinhThueNhapKhau = "1";
			else
				this.TinhThueNhapKhau = "0";
			
		
			query = " update ERP_HOADONNCC set kyhieu =?, sohoadon= ?, ngayhoadon = ?, sotienAVAT =?," +
					" sotienchietkhau =?,vat =? ,sotienBVAT = ?,park_fk= ?, NGAYSUA = ?, NGUOISUA= ?" +
					" ,NGAYDENHANTT= ?, TINHTHUENHAPKHAU= ?, LOAIHD= ? , KHOBIETTRU_FK = ?, KHOTONTRU_FK = ?, MAUSOHOADON = ?, CHENHLECHHD=? " +
					" where PARK_FK =  ?";
			
			
			PreparedStatement pre = this.db.getConnection().prepareStatement(query);
			pre.setString(1, this.kyHieuHoaDon.toUpperCase() );
			pre.setString(2, this.soHoaDon);
			pre.setString(3, this.ngayHoaDon);
			pre.setDouble(4, this.tong);
			pre.setDouble(5, 0);
			pre.setDouble(6, Double.parseDouble(this.thuegtgt));
			pre.setDouble(7, Double.parseDouble(this.soTienChuaThue));
			pre.setString(8, this.ID);
			pre.setString(9, ngaytao);
			pre.setString(10, this.USERID);
			pre.setString(11,ngaythanhtoan);
			pre.setString(12, TinhThueNhapKhau);
			pre.setString(13, this.loaidonmhId);
			if(this.idKhoBietTru.trim().length() > 0){
				pre.setInt(14, Integer.parseInt(this.idKhoBietTru));
			} else{
				pre.setString(14, null);
			}
			
			if(this.idKhoTonTru.trim().length() > 0){
				pre.setInt(15, Integer.parseInt(this.idKhoTonTru));
			} else{
				pre.setString(15, null);
			}
			pre.setString(16, this.mauSoHoaDon);
			pre.setString(17, this.ChenhlechHd);
			pre.setString(18, this.ID);
			
			int k = pre.executeUpdate();
			if( k !=1){
				this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
				db.getConnection().rollback();
				pre.close();
				return false;
			}
			pre.close();
						
			
			// lấy hoá đơn NCC id
			query =" select PK_SEQ from ERP_HOADONNCC where PARK_FK= "+ this.ID ;
			ResultSet rs = this.db.get(query);
			if( rs!=null){
				if(rs.next()){
					 this.hoadonNCC = rs.getString("PK_SEQ");
				}
				rs.close();
			}
			
			// insert phần chi tiết
			if(this.spList.size() > 0){
				
				for(int i=0; i < this.spList.size(); i++ ){
					ISanpham sanpham = this.spList.get(i);
					
					long pbThanhTienVND_temp = 0;
					long pbThanhTienVATVND_temp = 0;
					
					for(int j = 0; j < sanpham.getSpDetail().size(); j++){	
						
						ISpDetail sp = sanpham.getSpDetail().get(j);
						
						double tienvat =0;
						double thanhtien = 0;
						tienvat = Double.parseDouble(sp.getSoluong().replaceAll(",", "")) 
						* Double.parseDouble(sanpham.getDongia().replaceAll(",", "")) * Double.parseDouble(sanpham.getVat())/100;
						long tienvat_temp = Math.round(tienvat);
						
						thanhtien = Double.parseDouble(sp.getSoluong().replaceAll(",", "")) 
						* Double.parseDouble(sanpham.getDongia().replaceAll(",", ""));
						long thanhtien_temp = Math.round(thanhtien);
						
						String nsxid=(sp.getNSXid()==null || sp.getNSXid().trim().length()==0)?null:sp.getNSXid();
						String marrquet=sp.getMarrquet()==null?"":sp.getMarrquet();
						
						if(marrquet.trim().length()>0){
							String queryCheck="select count(*) dem from MARQUETTE where MA like N'"+marrquet+"' and sanpham_fk="+sanpham.getId()+" ";
							System.out.println("CHECK MARQUETTE: " + queryCheck);
							ResultSet rsCheckMAQR=db.get(queryCheck);
							if(rsCheckMAQR!=null){
								while(rsCheckMAQR.next()){
									if(rsCheckMAQR.getDouble("dem")!=1){
										this.MSG = "Không tìm được mã marquette tương ứng ";
										rsCheckMAQR.close();
										db.getConnection().rollback();
										return false;
									}
								}
								rsCheckMAQR.close();
							}
						}
						
						if(j < (sanpham.getSpDetail().size()-1)){
							pbThanhTienVND_temp += thanhtien_temp;
							pbThanhTienVATVND_temp +=  tienvat_temp;
						}
						String is_khongthue="(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sanpham.getMuahang_fk()+"' and SANPHAM_FK='"+sanpham.getId()+"')";

						query = " insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk, sanpham_fk, " +
								" dvt, soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, " +
								" SOLO, NGAYSANXUAT, NGAYHETHAN, thanhtienVIET, thanhtienVATVIET,is_khongthue,nsx_fk,marrquet) " +
								"values('" + this.hoadonNCC + "', " + sanpham.getMuahang_fk()+ ",  "+sanpham.getId()
								+", N'"+sanpham.getDvdl()+"' ," + sp.getSoluong().replaceAll(",", "") 
								+", '"+ sanpham.getDongia().replaceAll(",", "") +"'," + thanhtien + ", '"
								+sp.getSoluong().replaceAll(",", "")+"', '"+sanpham.getNgaynhandukien()+"', "
								+sanpham.getVat()+", "+ tienvat +", '"+sp.getSolo()+"', '"+sp.getNgaySx()+"', '"
								+sp.getNgayHethan()+"',"+thanhtien+","+tienvat+","+is_khongthue+","+nsxid+",'"+marrquet+"')";
						
						// phân bổ lại lô cuối cùng để cộng phần lẻ VAT và phần lẻ thành tiền VIỆT
						if(j == (sanpham.getSpDetail().size()-1)){
							long pbThanhTienVND = Long.parseLong(sanpham.getthanhtien().replaceAll(",", ""));
							long pbThanhTienVATVND = Long.parseLong(sanpham.getThanhtienVATVND().replaceAll(",", ""));
							
							// phần tử cuối cùng.
							long last_item_thanhtien = pbThanhTienVND-pbThanhTienVND_temp;
							long last_item_thanhtien_VAT = pbThanhTienVATVND - pbThanhTienVATVND_temp;
							
							query = " insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk, sanpham_fk, " +
							" dvt, soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, " +
							" SOLO, NGAYSANXUAT, NGAYHETHAN, thanhtienVIET, thanhtienVATVIET,is_khongthue,nsx_fk,marrquet) " +
							"values('" + this.hoadonNCC + "', " + sanpham.getMuahang_fk()+ ",  "+sanpham.getId()
							+", N'"+sanpham.getDvdl()+"' ," + sp.getSoluong().replaceAll(",", "") 
							+", '"+ sanpham.getDongia().replaceAll(",", "") +"'," + last_item_thanhtien + ", '"
							+sp.getSoluong().replaceAll(",", "")+"', '"+sanpham.getNgaynhandukien()+"', "
							+sanpham.getVat()+", "+ last_item_thanhtien_VAT +", '"+sp.getSolo()+"', '"+sp.getNgaySx()+"', '"
							+sp.getNgayHethan()+"',"+last_item_thanhtien+","+last_item_thanhtien_VAT+","+is_khongthue+","+nsxid+",'"+marrquet+"')";
					
						}
						
						System.out.println("  Insert PN: " + query);
						if(!db.update(query)){
							this.MSG = "Không thể tạo mới ERP_HOADONNCC_DONMUAHANG : " + query;
							db.getConnection().rollback();
							return false;
						}
						
						
					}
				}
			}
			
			// cập nhật ĐƠN MUA HÀNG sang trạng thái số 2.
			if( this.muahang_fk.trim().length() >0){
				boolean check  = UpdateTrangThaiDonMuaHang();
				if( check == false){
					this.db.update("rollback");
				} 
			}
			
			// CẬP NHẬT TOOLTIP
			db.execProceduce2("CapNhatTooltip_HoaDonNCC", new String[] { this.ID });
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
	}


	public boolean updatePark_Duyet() 
	{
		//System.out.println("vao ham update");
		String ngaytao = this.getDateTime();
		
		
		if(this.loaidonmhId.equals("0")) // HÓA ĐƠN NHẬP KHẨU - KIỂM TRA TỈ GIÁ
		{
			if(this.tigia.trim().length() <= 0) this.tigia = "0";
			if(!this.ttId.equals("100000")){
				if(Double.parseDouble(this.tigia.replaceAll(",", "")) <= 0) 
				{
					this.MSG = "Tỉ giá phải nhập lớn hơn 0 !";
					return false;
				}
			}
		}
		
		//Kiem tra thong tin hoa don
		if(this.hdList.size() > 0)
		{
			for(int i = 0; i < this.hdList.size(); i++)
			{
				IErpHoadon hd = hdList.get(i);
				try{
					Double.parseDouble(hd.getThuesuat());
				}catch(Exception er){
					hd.setThuesuat("0");
				}
				
				if(this.loaidonmhId.equals("0")) // HÓA ĐƠN  NHẬP KHẨU
				{
					if(this.NCCID.length()== 0|| hd.getSotienavat().length()==0||hd.getSotienbvat().length()==0)
					{
						this.MSG = "Không thể tạo hóa đơn " + hd.getSohoadon() + " thiếu thông tin vui lòng kiểm tra lại!";
						return false;
					}
				}
				
				List<IErpHoadonSp> pnkdetailList = hd.getPnkList();				
				
				for(int j = 0; j < pnkdetailList.size(); j++)
				{						
					IErpHoadonSp spham = pnkdetailList.get(j);
					
					double slMAX = Double.parseDouble(spham.getSoluong()) - Double.parseDouble(spham.getSoluongRaHD()) ;
					slMAX = slMAX + (Double.parseDouble(spham.getSoluong())*Double.parseDouble(spham.getDungsai())/100);
					
					if(slMAX - Double.parseDouble(spham.getSoluonghd()) < 0)
					{
						this.MSG = "Số lượng sản phẩm " + spham.getSanphamMa() + " không được vượt quá số lượng đặt (có dung sai). Vui lòng kiểm tra lại ";
						return false;
					}
				}
				
								
			}
		}
			if(this.loaidonmhId.trim().length()<=0)
			{
				this.MSG =  "Vui lòng chọn loại hóa đơn";
				return false;
			}
			
			if(this.hdList.size() <= 0)
			{
				this.MSG = "Vui lòng nhập thông tin hóa đơn";
				return false;
			}
			
			if(this.NCCID.trim().length() <= 0)
			{
				this.MSG = "Vui lòng chọn nhà cung cấp";
				return false;
			}
			
		try 
		{
			db.getConnection().setAutoCommit(false);
			String query = 	"";
			
				query = " SELECT MUAHANG_FK " +
						" FROM ERP_HOADONNCC_DONMUAHANG " +
						" WHERE MUAHANG_FK NOT IN (" + this.poNkId + ") " +
						" AND HOADONNCC_FK IN (SELECT PK_SEQ FROM ERP_HOADONNCC WHERE PARK_FK = " + this.ID + ") ";
			
				System.out.println(query);
			ResultSet rs = this.db.get(query);
			if(rs != null){
				while(rs.next()){
					if(this.poNkId.length() > 0){
						this.poNkId = this.poNkId + "," + rs.getString("MUAHANG_FK") ;
					}else{
						this.poNkId = rs.getString("MUAHANG_FK") ;
					}
				}
				rs.close();
			}
			
			// LẤY TỈ GIÁ
						// THUE NHAP KHAU = 1 >>  TIGIA lấy lúc nhập vào
						// CÒN LẠI >> LẤY NHƯ DƯỚI
						
				if(this.ttId.equals("100000") )
				{
					this.tigia = "1";
				}
			
			 // Lưu NCC thay thế (nếu là NCC thay thế mới)
			   if(this.nccThayTheId == null)
			   {
				   query = " insert ERP_NHACUNGCAPTHAYTHE(TEN, MASOTHUE, DIACHI) " +
							" values(N'" + this.nccThayTheTen+ "', '" +  this.nccThayTheMST+ "', N'" +  this.nccThayTheDiaChi + "' )";
					if(!db.update(query))
					{
						this.MSG = "Không thể tạo ERP_NHACUNGCAPTHAYTHE lỗi: " + query;
						db.getConnection().rollback();
						return false;
					}
					
					query = "select SCOPE_IDENTITY() as nccTTId";
					
					ResultSet rs1 = db.get(query);						
					if(rs1.next())
					{
						this.nccThayTheId = rs1.getString("nccTTId");
						rs1.close();
					}
			   }
						
			query = " UPDATE  ERP_PARK  " +
					" SET NCCTHAYTHE_FK = "+ this.nccThayTheId +", NGAYGHINHAN='" + this.NGAYGHINHAN + "', NGAYSUA = '" + getDateTime() + "', " +
				   	" NCC_FK='" + this.NCCID + "', TINHTHUENHAPKHAU = '" + this.TinhThueNhapKhau + "', PO_NHAPKHAU = '" + this.poNkId + "'," +
				   	" TIENTE_FK = " + this.ttId + ", TIGIA =  " + tigia + " " +
				   	" WHERE PK_SEQ='"+this.ID+"'";
			System.out.println(query);
			
			if(!db.update(query))
			{
				this.MSG = "1.Không cập nhật Park lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}

				query = "delete ERP_HOADONNCC_DONMUAHANG where HOADONNCC_FK IN (SELECT PK_SEQ FROM ERP_HOADONNCC WHERE park_fk = '" + this.ID + "') ";
				System.out.println(query);
				if(!db.update(query))
				{
					this.MSG = "3.Không cập nhật Park lỗi : " + query;
					db.getConnection().rollback();
					return false;
				}
			
			
			query = "delete ERP_HOADONNCC where park_fk = '" + this.ID + "' ";
			System.out.println(query);
			if(!db.update(query))
			{
				this.MSG = "3.Không cập nhật Park lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}
			
			
			//////CHI CAP NHAT KY HIEU VA SO HOA DON
			if(this.hdList.size() > 0)
			{
				for(int i = 0; i < this.hdList.size(); i++)
				{
					IErpHoadon hd = hdList.get(i);
					 
					// TÍNH NGÀY ĐẾN HẠN THANH TOÁN (DÙNG TRONG PHIẾU CHI) : ngày hóa đơn(Hóa đơn NCC) + thời hạn nợ(DLN)
					query = "SELECT CONVERT(nvarchar(10), (dateadd(DAY, ISNULL(THOIHANNO,0), '" + hd.getNgayhoadon()+ "')),120 ) as ngaydenhantt " +
							"FROM ERP_NHACUNGCAP " +
							"WHERE PK_SEQ = '"+ this.NCCID +"'";
					ResultSet rsThoihanno = db.get(query);
					if(rsThoihanno!= null)
					{
						while(rsThoihanno.next())
						{
							 hd.setNgaydenhanTT(rsThoihanno.getString("ngaydenhantt")) ;
						}
						rsThoihanno.close();
					}
					
					if(this.loaidonmhId.equals("0")) this.TinhThueNhapKhau = "1";
					else
						this.TinhThueNhapKhau = "0";
					
						String mauhoadon = "";
						if(hd.getMausohoadon().trim().length() > 6)
							mauhoadon = hd.getMausohoadon().substring(0, 6);
						else
							mauhoadon = hd.getMausohoadon();
																	
						
						query = "insert ERP_HOADONNCC(mauhoadon, mausohoadon, kyhieu, sohoadon, ngayhoadon ,sotienAVAT,sotienchietkhau,vat,sotienBVAT,thuesuat,park_fk, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, NGAYDENHANTT, TINHTHUENHAPKHAU,CONGTY_FK, NPP_FK, LOAIHD) " +
								"values('" + mauhoadon.toUpperCase() + "', '" + hd.getMausohoadon().toUpperCase() + "', '" + hd.getKyhieu().toUpperCase() + "', '" + hd.getSohoadon() + "', '" + hd.getNgayhoadon()+ "', '" + hd.getSotienavat() +"'," +
										" '"+hd.getChieckhau()+ "',  '" + hd.getVAT() +"', '"+ hd.getSotienbvat()+"', '"+ hd.getThuesuat()+"', '"+ this.ID +"', '"+ ngaytao + "', '" + ngaytao + "', '" + this.USERID + "'," +
										" '" + this.USERID + "', '0', '"+ hd.getNgaydenhanTT() +"', "+TinhThueNhapKhau+", "+this.CtyId+", "+this.nppdangnhap+", "+this.loaidonmhId+")";
						System.out.println(query);
						
						if(!db.update(query))//Luu vào bảng ERP_HOADONNCC
						{
							this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
							db.getConnection().rollback();
							return false;
						}
						String hdCurrent = "";//Lay PK_SEQ vua insert
						query = "select IDENT_CURRENT('ERP_HOADONNCC') as hdId";
					
						ResultSet rsHd = db.get(query);						
						if(rsHd.next())
						{
							hdCurrent = rsHd.getString("hdId");
							rsHd.close();
						}
					
						List<IErpHoadonSp> pnkdetailList = hd.getPnkList();//Moi hoa don tuong ung ta luu vao ERP_HOADONNCC_DONMUAHANG
						System.out.println("pnkdetailList.size():"+pnkdetailList.size());
						
						for(int j = 0; j < pnkdetailList.size(); j++)
						{	
							
							IErpHoadonSp sp = pnkdetailList.get(j);
							
							
							System.out.println("Size 00 "+sp.getSoluonghd().replaceAll(",", ""));
							
							if(Double.parseDouble(sp.getSoluonghd().replaceAll(",", ""))>0)
							{	
								query = "SELECT LOAIHANGHOA_FK FROM ERP_MUAHANG WHERE PK_SEQ = "+sp.getPoId();
								System.out.println("Câu lấy Loai hh"+ query);						
								ResultSet loaihhrs = db.get(query);	
								String loaihh = "";
								if(loaihhrs.next())
								{
									loaihh = loaihhrs.getString("LOAIHANGHOA_FK");
									loaihhrs.close();
								}
								
								if(loaihh.equals("0")) //SẢN PHẨM
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,sanpham_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk) " +
											"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"')";
								
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
								if(loaihh.equals("1"))// TÀI SẢN CỐ ĐỊNH
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,taisan_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk) " +
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"')";
								
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
								if(loaihh.equals("2"))// CHI PHÍ
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,chiphi_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk) " +
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"')";
								
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
								if(loaihh.equals("2"))// CÔNG CỤ DỤNG CỤ
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,ccdc_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk) " +
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"')";
								
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
								
								
							}
							
						}
				}
			}
			
			db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			this.db.update("rollback");
			e.printStackTrace();
			return false;
		}
	}

	
	public boolean Duyet()
	{
		Utility util = new Utility();
		
		try 
		{
			db.getConnection().setAutoCommit(false);
			
			
			String query = "";

			
			// TỰ ĐỘNG TẠO 1 HÓA ĐƠN NCC NẾU SỐ LƯỢNG HĐ < SL PO
				
			    // Kiểm tra SL còn lại > 0
				query = "SELECT  dmh.soluong, dmh.thanhtien, dmh.soluongdat, "
					  + "      isnull((select sum(a.soluong) "
					  + "              from ERP_HOADONNCC_DONMUAHANG a inner join ERP_HOADONNCC b on a.hoadonncc_fk = b.pk_seq "
					  + "                  inner join ERP_PARK c on b.PARK_FK = c.PK_SEQ"
					  + "              where c.TRANGTHAI != 4 and c.PK_SEQ != "+ this.ID +" and a.MUAHANG_FK = dmh.MUAHANG_FK and   "
					  + "              ( a.sanpham_fk = dmh.sanpham_fk or a.taisan_fk = dmh.taisan_fk or a.ccdc_fk = dmh.ccdc_fk or a.chiphi_fk = dmh.chiphi_fk ) )"
					  + "            ,0) SOLUONGDARAHD "
					  + "FROM ERP_HOADONNCC hd inner join ERP_HOADONNCC_DONMUAHANG dmh on hd.PK_SEQ = dmh.HOADONNCC_FK "
					  + "WHERE hd.PARK_FK = "+ this.ID +" ";
				ResultSet rsKT = db.get(query);
				System.out.println("Câu KT ..."+query);
				boolean isKiemtra = false;
				
				if(rsKT != null)
				{
					while(rsKT.next())
					{
						if(rsKT.getDouble("soluongdat") - rsKT.getDouble("SOLUONGDARAHD")- rsKT.getDouble("soluong") > 0)
							isKiemtra = true;
							
					}rsKT.close();
				}
				
				if(isKiemtra )
				{
					// Lay TT Park
					query = "SELECT P.NCCTHAYTHE_FK, P.NGAYGHINHAN, P.NCC_FK, P.CONGTY_FK, P.PO_NHAPKHAU, P.TIENTE_FK, P.TIGIA, P.LOAIHD, P.NPP_FK,  "
						  +"        HD.mauhoadon, HD.mausohoadon, HD.kyhieu, HD.sohoadon, HD.ngayhoadon, HD.sotienAVAT, HD.sotienchietkhau,"
						  + "       HD.vat, HD.sotienBVAT, HD.thuesuat, HD.NGAYDENHANTT, HD.TINHTHUENHAPKHAU "
						  + "FROM ERP_PARK P INNER JOIN ERP_HOADONNCC HD ON P.PK_SEQ = HD.PARK_FK "
						  + "WHERE P.PK_SEQ = "+ this.ID +" ";
					System.out.println("Câu lấy TT PARK "+query);
					ResultSet rsTT = db.get(query);
					String parkCurrent = "";
					String hdCurrent = "";
					double thuesuat = 0;
					double Bvat = 0;
					double Vat = 0;
				
					if(rsTT != null)
					{
						while(rsTT.next())
						{
							//PARK
							query = " insert ERP_PARK( NCCTHAYTHE_FK ,NGAYGHINHAN, NCC_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, PO_NHAPKHAU, TIENTE_FK, TIGIA, LOAIHD, NPP_FK) " +
									" values("+ rsTT.getString("NCCTHAYTHE_FK") +", '" + rsTT.getString("NGAYGHINHAN") + "', '" + rsTT.getString("NCC_FK") + "', '" + this.getDateTime() + "', '" + this.getDateTime() + "', "+
								    " '" + this.USERID + "', '" + this.USERID + "', '0', '"+ rsTT.getString("CONGTY_FK") +"', '" +  rsTT.getString("PO_NHAPKHAU") + "', " + rsTT.getString("TIENTE_FK") + ", " + rsTT.getString("TIGIA") + ", "+rsTT.getString("LOAIHD")+", "+rsTT.getString("NPP_FK")+" )";
							if(!db.update(query))
							{
								this.MSG = "Không thể tạo Park lỗi: " + query;
								db.getConnection().rollback();
								return false;
							}
												
							query = "select SCOPE_IDENTITY() as parkId";
							
							ResultSet rsNh = db.get(query);						
							if(rsNh.next())
							{
								parkCurrent = rsNh.getString("parkId");
								rsNh.close();
							}
							
							// HÓA ĐƠN NCC
							thuesuat = rsTT.getDouble("thuesuat");
									
							query = "insert ERP_HOADONNCC(mauhoadon, mausohoadon, kyhieu, sohoadon, ngayhoadon , thuesuat, park_fk, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, NGAYDENHANTT, TINHTHUENHAPKHAU,CONGTY_FK, NPP_FK, LOAIHD) " +
									"values('" + rsTT.getString("mauhoadon") + "', '" +  rsTT.getString("mausohoadon") + "', '" + rsTT.getString("kyhieu") + "', '" + rsTT.getString("sohoadon") + "', '" + rsTT.getString("ngayhoadon") + "', " +
											"  '"+ thuesuat +"', '"+ parkCurrent +"', "+
										     " '"+ this.getDateTime() + "', '" + this.getDateTime() + "', '" + this.USERID + "',  '" + this.USERID + "', '0', '"+ rsTT.getString("NGAYDENHANTT") +"', "+rsTT.getString("TINHTHUENHAPKHAU")+", "+rsTT.getString("CONGTY_FK")+", "+rsTT.getString("NPP_FK")+", "+rsTT.getString("LOAIHD")+")";
							
							if(!db.update(query))
							{
								this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
								db.getConnection().rollback();
								return false;
							}
							
							query = "select IDENT_CURRENT('ERP_HOADONNCC') as hdId";
						
							ResultSet rsHd = db.get(query);						
							if(rsHd.next())
							{
								hdCurrent = rsHd.getString("hdId");
								rsHd.close();
							}
							
						}
						rsTT.close();
					}
				
				
					// HÓA ĐƠN NCC - ĐƠN MUA HÀNG
					if(hdCurrent.trim().length() > 0)
					{
						query = "SELECT  dmh.muahang_fk, dmh.sanpham_fk, dmh.taisan_fk, dmh.ccdc_fk, dmh.chiphi_fk, dmh.dvt , dmh.soluong, dmh.dongia, dmh.thanhtien, dmh.soluongdat, dmh.ngaynhandk,  "
							  + "      isnull((select sum(a.soluong) "
							  + "              from ERP_HOADONNCC_DONMUAHANG a inner join ERP_HOADONNCC b on a.hoadonncc_fk = b.pk_seq "
							  + "                  inner join ERP_PARK c on b.PARK_FK = c.PK_SEQ"
							  + "              where c.TRANGTHAI != 4 and c.PK_SEQ != "+ this.ID +" and a.MUAHANG_FK = dmh.MUAHANG_FK and   "
							  + "              ( a.sanpham_fk = dmh.sanpham_fk or a.taisan_fk = dmh.taisan_fk or a.ccdc_fk = dmh.ccdc_fk or a.chiphi_fk = dmh.chiphi_fk ) )"
							  + "            ,0) SOLUONGDARAHD "
							  + "FROM ERP_HOADONNCC hd inner join ERP_HOADONNCC_DONMUAHANG dmh on hd.PK_SEQ = dmh.HOADONNCC_FK "
							  + "WHERE hd.PARK_FK = "+ this.ID +"   ";
						System.out.println("Câu lấy TT HOADON_DONMUAHANG "+query);
						ResultSet rsCT= db.get(query);
						
						if(rsCT!= null)
						{
							while(rsCT.next())
							{
								double soluongHd = rsCT.getDouble("soluongdat") - rsCT.getDouble("SOLUONGDARAHD")- rsCT.getDouble("soluong");						
								double thanhtien = Math.round(soluongHd*rsCT.getDouble("dongia"));
								
								Bvat = Bvat + thanhtien;
								Vat = Vat + Math.round(thanhtien*thuesuat/100);
								
								if(soluongHd > 0)
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk, sanpham_fk, taisan_fk, ccdc_fk, chiphi_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk) " +
											"values('" + hdCurrent + "', " + rsCT.getString("muahang_fk") + ",  "+ rsCT.getString("sanpham_fk") +",  "+ rsCT.getString("taisan_fk") +" ,  "+ rsCT.getString("ccdc_fk") +",   "+ rsCT.getString("chiphi_fk") +", " +
										    " N'" + rsCT.getString("dvt")  +"' ," + soluongHd +", '"+ rsCT.getString("dongia") +"', " + thanhtien + ", '"+ rsCT.getString("soluongdat") +"', '"+ rsCT.getString("ngaynhandk") +"')";
										
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới ERP_HOADONNCC_DONMUAHANG : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
										
							}rsCT.close();
						}
						
						
					}
				
					// Cập nhật lại tiền
					query = "update ERP_HOADONNCC set sotienAVAT = "+ (Bvat + Vat) +", sotienchietkhau = '0', vat = "+ Vat +" , sotienBVAT = "+ Bvat +" where PK_SEQ = "+ hdCurrent +" ";
					
					if(!db.update(query))
					{
						this.MSG = "Không thể cập nhật hóa đơn nhà cung cấp lỗi : " + query;
						db.getConnection().rollback();
						return false;
					}
				}
				
				
			// END	
			
			
			// CÀI KẾ TOÁN
				query = "\n  select  a.thuesuat, a.pk_seq, a.sohoadon, b.ngayghinhan, b.ncc_fk,  " +
						"\n    		d.SANPHAM_FK, d.TAISAN_FK, d.CCDC_FK, d.SOLUONG, d.DONGIA , d.CHIPHI_FK,   " +
						"\n 			isnull(a.sotienchietkhau, 0) as tienCK_HoaDon,  " +
						"\n 			a.VAT as VAT_HOADON, a.park_fk,  isnull(b.tinhthuenhapkhau, 0) as tinhthueNK,   " +
						"\n 			0 as loaihanghoa_fk, c.taikhoan_fk as taikhoanNCC ,  " +
						"\n 			ISNULL(b.tiente_fk, '100000') as tiente_fk,  "+
						"\n 			ISNULL(b.tigia,1 ) as tigia, " +
						"\n			(SELECT distinct C.PK_SEQ " +
						"\n			 FROM SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.PK_SEQ " +
						"\n							INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOANKT_FK = C.SOHIEUTAIKHOAN" +
						"\n		     WHERE A.PK_SEQ = d.SANPHAM_FK AND C.CONGTY_FK = "+this.CtyId+") TAIKHOANNO_SANPHAM, \n"+ 
						"\n			(select distinct C.PK_SEQ from ERP_TAISANCODINH A INNER JOIN Erp_LOAITAISAN B ON A.LOAITAISAN_FK = B.pk_seq \n" +
						"\n					INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOAN_FK = C.SOHIEUTAIKHOAN \n"	+
						"\n				WHERE A.PK_SEQ = d.TAISAN_FK AND C.CONGTY_FK = "+this.CtyId+") TAIKHOANNO_TAISAN, \n"+ 
						"\n			( select  distinct C.PK_SEQ from ERP_CONGCUDUNGCU A  INNER JOIN Erp_LOAICCDC B ON A.LOAICCDC_FK = B.pk_seq " +
						"\n					INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOAN_FK = C.SOHIEUTAIKHOAN \n"	+
						"\n				WHERE A.PK_SEQ = d.CCDC_FK AND C.CONGTY_FK = "+this.CtyId+" ) TAIKHOANNO_CCDK, \n"+
						"\n			(  select distinct TAIKHOAN_FK from ERP_NHOMCHIPHI WHERE PK_SEQ =  d.CHIPHI_FK AND CONGTY_FK = "+this.CtyId+" ) TAIKHOANNO_CHIPHI, \n"+
						"\n			( select PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13310000' AND CONGTY_FK = "+this.CtyId+") TAIKHOANNO_VAT, "+
						"\n			( select PK_SEQ FROM ERP_TAIKHOANKT WHERE SOHIEUTAIKHOAN = '13320000' AND CONGTY_FK = "+this.CtyId+") TAIKHOANNO_VAT_TSCD "+
						"\n from 	ERP_HOADONNCC a inner join ERP_PARK b on a.park_fk = b.pk_seq   " +
						"\n 			inner join ERP_NHACUNGCAP c on b.ncc_fk = c.pk_seq   "+
						"\n 		inner join ERP_HOADONNCC_DONMUAHANG d on d.HOADONNCC_FK = a.PK_SEQ " +
						"\n where b.pk_seq = '" + this.ID + "'  ";
			
			
			System.out.println("---INIT CHOT: " + query); 
			
	        ResultSet psktRs = db.get(query);
	        
	        
	        String TAIKHOANNO_TIENHANG = "";
	        String TAIKHOANCO_TIENHANG = "";
	        
	        String TAIKHOANNO_VAT = "";
	        String TAIKHOANCO_VAT = "";
	        
	        if(psktRs != null)
	        {
              while(psktRs.next())
              {
                    String hoadonncc_fk = psktRs.getString("pk_seq");
                    
                    String namNV = psktRs.getString("ngayghinhan").substring(0, 4);
        			String thangNV = psktRs.getString("ngayghinhan").substring(5, 7);
        			
        			String ncc_fk = psktRs.getString("ncc_fk");
        			String taikhoanncc_fk = psktRs.getString("taikhoanNCC")== null?"":psktRs.getString("taikhoanNCC") ;
                    
        			String tiente_fk = psktRs.getString("tiente_fk");
        			double tygia = (psktRs.getDouble("tigia"));
        			if(tiente_fk.equals("100000"))
        				tygia = 1;
        			
        			String sanphamId= "";
        			String taisanId = "";
        			String ccdcId = "";
        			String chiphiId = "";
        			
        			double soluong = 0;
        			double dongia =  0;       			
        		    double sotienBVAT = 0;
        		    double thuesuat = 0;
        		    
    				sanphamId = psktRs.getString("sanpham_fk") == null ? "":psktRs.getString("sanpham_fk")  ;
    				taisanId = psktRs.getString("taisan_fk") == null ? "":psktRs.getString("taisan_fk");
    				ccdcId = psktRs.getString("ccdc_fk") == null ? "":psktRs.getString("ccdc_fk");
    				chiphiId = psktRs.getString("CHIPHI_FK") == null ? "":psktRs.getString("CHIPHI_FK");
        			soluong = psktRs.getDouble("SOLUONG");
        			dongia =  psktRs.getDouble("DONGIA");        			
        		    sotienBVAT = Math.round(soluong*dongia);
        		    thuesuat = psktRs.getDouble("thuesuat");    
        			
                    double VAT = soluong*dongia*thuesuat/100 ;    
                    
                    String doituongno = "";
                    String madoituongno = "";
                    String doituongco = "";
                    String madoituongco ="";
                                    	
                	if( tygia <= 0 && tiente_fk.equals("100000") )
                	{
                		this.MSG = "Vui lòng kiểm tra lại thông tin tỷ giá.";
                		psktRs.close();
						db.getConnection().rollback();
						return false;
                	}
                	
                	doituongco = "Nhà cung cấp";
            		madoituongco = ncc_fk; 
                	
            		
                	if(sanphamId.trim().length()>0)
                	{
                		doituongno = "Sản phẩm";
                		madoituongno = sanphamId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_SANPHAM")== null ? "":psktRs.getString("TAIKHOANNO_SANPHAM");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT")== null ? "":psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                	}
                	
                	if(ccdcId.trim().length()>0)
                	{
                		doituongno = "Công cụ dụng cụ";
                		madoituongno = ccdcId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_CCDK") == null ? "":psktRs.getString("TAIKHOANNO_CCDK");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT") == null ? "":psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                	}
                	
                	if(taisanId.trim().length()>0)
                	{
                		doituongno = "Tài sản";
                		madoituongno = taisanId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_TAISAN")== null ? "":psktRs.getString("TAIKHOANNO_TAISAN");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT_TSCD")== null ? "":psktRs.getString("TAIKHOANNO_VAT_TSCD");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                		
                	}
                	
                	if(chiphiId.trim().length()>0)
                	{
                		doituongno = "Chi phí";
                		madoituongno = chiphiId;
                		
                		TAIKHOANNO_TIENHANG = psktRs.getString("TAIKHOANNO_CHIPHI") == null ? "":psktRs.getString("TAIKHOANNO_CHIPHI");
                		TAIKHOANCO_TIENHANG = taikhoanncc_fk;
                		
                		TAIKHOANNO_VAT = psktRs.getString("TAIKHOANNO_VAT") == null ? "":psktRs.getString("TAIKHOANNO_VAT");
                		TAIKHOANCO_VAT = taikhoanncc_fk;
                	}
                	
                	
                	if(sotienBVAT>0)
                	{
                		
                		if(TAIKHOANNO_TIENHANG.trim().length() <= 0 || TAIKHOANCO_TIENHANG.trim().length() <= 0)
    					{
                			this.MSG = "Loại sản phẩm, công cụ dụng cụ hoặc chi phí tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
                			psktRs.close();
    						db.getConnection().rollback();
    						return false;

    					}
                		
                		System.out.println("Cai ke toan");
                		this.MSG = util.Update_TaiKhoan( db, thangNV, namNV, psktRs.getString("ngayghinhan"), psktRs.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOANNO_TIENHANG, TAIKHOANCO_TIENHANG, "", 
								Double.toString(sotienBVAT), Double.toString(sotienBVAT), doituongno, madoituongno, doituongco, madoituongco, "0", "", "", tiente_fk, "", Double.toString(tygia), Double.toString(sotienBVAT), Double.toString(sotienBVAT), "Hóa đơn - Tiền hàng" );
						if(this.MSG.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return false;
						}
                	}
                	
                	if(VAT>0)
                	{
                		if(TAIKHOANNO_VAT.trim().length() <= 0 || TAIKHOANCO_VAT.trim().length() <= 0)
    					{
                			this.MSG = "Loại sản phẩm, công cụ dụng cụ hoặc chi phí tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
                			psktRs.close();
    						db.getConnection().rollback();
    						return false;

    					}
                		
                		this.MSG = util.Update_TaiKhoan( db, thangNV, namNV, psktRs.getString("ngayghinhan"), psktRs.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOANNO_VAT, TAIKHOANCO_VAT, "", 
								Double.toString(VAT), Double.toString(VAT), doituongno, madoituongno, doituongco, madoituongco, "0", "", "", tiente_fk, "", Double.toString(tygia), Double.toString(VAT), Double.toString(VAT), "Hóa đơn - Tiền vat" );
						if(this.MSG.trim().length() > 0)
						{
							psktRs.close();
							db.getConnection().rollback();
							return false;
						}
                	}
                	
              	}
              	psktRs.close();
	        }
	        
	        query = "update ERP_PARK set trangthai = '2' where pk_seq = '" + this.ID + "'";
		    if(!db.update(query))
		    {
		    	this.MSG = "Không thể cập nhật ERP_PARK: " + query;
                db.getConnection().rollback();
                return false;
		    }
		        
	    	db.getConnection().commit();
			db.getConnection().setAutoCommit(true);
			return true;
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
			this.db.update("rollback");
			this.MSG = "Lỗi : "+ e.toString();
			return false;
		}

	}
	

	public List<IErpHoadonSp> getPnkList() 
	{
		return this.pnkList;
	}
	
	public void setPnkList(List<IErpHoadonSp> pnkList) 
	{
		this.pnkList = pnkList;
	}
	
	private String getDateTime() 
	{
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	
	public String getTinhThueNhapKhau() {
		
		return this.TinhThueNhapKhau;
	}

	
	public void setTinhThueNhapKhau(String _TinhThueNhapKhau) {
		
		this.TinhThueNhapKhau=_TinhThueNhapKhau;
	}

	
	public String getCtyId() {
		
		return this.CtyId;
	}

	
	public void setCtyId(String _CtyId) {
		
		this.CtyId=_CtyId;
	}

	public void close() 
	{
		this.db.shutDown();
		try 
		{
			if(ttRs != null) ttRs.close();
			if(this.Loaihanglist != null)
				this.Loaihanglist.close();
		}
		catch (SQLException e) {}	
	}

	
	public ResultSet getNccRs()
	{
		return this.nccRs;
	}

	public void setNccRs(ResultSet nccRs) 
	{
		this.nccRs = nccRs;
	}

	
	public ResultSet getPoNkRs() {
		
		return this.poNKRs;
	}

	
	
	public void setPoNkRs(ResultSet poNKRs) {
		
		this.poNKRs = poNKRs;
	}
	
	public void setPoNk_SPRs(ResultSet poNK_SPRs) {
		
		this.poNK_SPRs = poNK_SPRs;
	}

	public ResultSet getPoNk_SPRs() {
		
		return this.poNK_SPRs;
	}


	public void setPoNk_SP_ChonRs(ResultSet poNK_SP_ChonRs) {
		
		this.poNK_SP_ChonRs = poNK_SP_ChonRs;
	}

	public ResultSet getPoNk_SP_ChonRs(String hdId) {

		
		String query =
			" SELECT distinct ISNULL(MH.CHIPHIKHAC,0)as chiphikhac, MH.PK_SEQ, MH.NGAYMUA,  MH.soPO, ISNULL(MH.DUNGSAI,0) DUNGSAI, \n"+
			"      CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN '0'  \n"+
		    "           WHEN MHSP.TAISAN_FK IS NOT NULL THEN '1'  \n"+
		    "           WHEN MHSP.CCDC_FK IS NOT NULL THEN '2' \n"+
		    "      ELSE '' END AS LOAI,  \n"+
			"      CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ  \n"+
		    "           WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ  \n"+
		    "           WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ \n"+
		    "      ELSE '' END AS SPID,  \n"+
		    "      CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.MA \n"+
		    "           WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.MA \n"+
		    "           WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.MA \n"+
		    "           ELSE '' END AS MA,  \n"+
		    "      CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.TEN \n"+
			"           WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.DIENGIAI \n"+
		    "           WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.DIENGIAI \n"+
		    "           ELSE '' END AS TEN,     \n"+
		    "      MHSP.DONVI, HD_DMH.SOLUONG AS SOLUONG, MHSP.DONGIA, HD_DMH.THANHTIEN,  \n"+
		    " 		CASE WHEN MH.LOAI = 0 THEN " +
			"      (SELECT b.NGAYNHAN FROM ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK where a.MUAHANG_FK = mh.PK_SEQ and b.SANPHAM_FK = mhsp.SANPHAM_FK AND a.TRANGTHAI = 1 ) " +
			" 		ELSE ISNULL(MHSP.NGAYNHAN,'') END NGAYNHANDK \n"+
		    " FROM ERP_HOADONNCC_DONMUAHANG HD_DMH  \n"+
		    "      INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = HD_DMH.MUAHANG_FK  \n"+
			"	   INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n"+
			"                 AND (MHSP.SANPHAM_FK = HD_DMH.SANPHAM_FK  OR MHSP.TAISAN_FK = HD_DMH.TAISAN_FK OR MHSP.CCDC_FK =  HD_DMH.CCDC_FK) \n"+
			"	   LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MHSP.SANPHAM_FK  \n"+
			"	   LEFT JOIN ERP_TAISANCODINH TSCD ON MHSP.TAISAN_FK = TSCD.PK_SEQ \n"+
			"	   LEFT JOIN ERP_CONGCUDUNGCU CCDC ON MHSP.CCDC_FK = CCDC.PK_SEQ \n"+
		    " WHERE HD_DMH.HOADONNCC_FK = " + hdId + "  \n"+
		    " ORDER BY MH.NGAYMUA DESC \n";
		
			/*"SELECT MH.PK_SEQ, MH.NGAYMUA, \n" + 
		 	" 		MH.SOPO, ISNULL(MH.DUNGSAI,0) as DUNGSAI, \n" +
		 	" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN '0'  \n"+
			"      WHEN MHSP.TAISAN_FK IS NOT NULL THEN '1' \n"+
			" 	   WHEN MHSP.CCDC_FK IS NOT NULL THEN '2' \n"+
			"      ELSE '' \n"+
			"      END  AS LOAI ,\n"+
			" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ  \n"+
			"      WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ \n"+
			" 	   WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ \n"+
			"      END  AS SPID ,\n"+
			" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.MA  \n"+
			"      WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.MA \n"+
			" 	   WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.MA \n"+
			"      END  AS MA , \n"+
			" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.TEN  \n"+
			"      WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.DIENGIAI \n"+
			" 	   WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.DIENGIAI \n"+
			"      END  AS TEN , \n"+
			"   MHSP.DONVI, \n" +
			"  CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN (MHSP.SOLUONG - ISNULL(HD.SOLUONG, 0)) \n"+
			"       WHEN MHSP.TAISAN_FK IS NOT NULL THEN (MHSP.SOLUONG - ISNULL(HD_TSCD.SOLUONG, 0)) \n"+
			"       WHEN MHSP.CCDC_FK IS NOT NULL THEN (MHSP.SOLUONG - ISNULL(HD_CCDC.SOLUONG, 0)) \n"+
			"       ELSE '0' END AS SOLUONG , \n" +
			" MHSP.DONGIA, MHSP.THANHTIEN , ISNULL(MH.CHIPHIKHAC,0)as chiphikhac,  \n" +
			" CASE WHEN MH.LOAI = 0 THEN " +
			"      (SELECT b.NGAYNHAN FROM ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK where a.MUAHANG_FK = mh.PK_SEQ and b.SANPHAM_FK = mhsp.SANPHAM_FK ) " +
			" ELSE ISNULL(MHSP.NGAYNHAN,'') END NGAYNHANDK \n"+
			"FROM ERP_MUAHANG MH \n" +
			"      INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ \n" +
			" 	   LEFT JOIN SANPHAM SP ON SP.PK_SEQ = MHSP.SANPHAM_FK \n" +
			"      LEFT JOIN \n" +
			"      ( \n" +
			"		 SELECT A.MUAHANG_FK, A.SANPHAM_FK, SUM(SOLUONG) AS SOLUONG \n"+ 
		    "  		 FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq \n"+
		    "  		 WHERE trangthai !=4 \n"+
		    "  		 GROUP BY A.MUAHANG_FK, A.SANPHAM_FK  \n"+
			"      )HD ON HD.MUAHANG_FK = MH.PK_SEQ AND HD.SANPHAM_FK = MHSP.SANPHAM_FK \n" +
			"     LEFT JOIN ERP_TAISANCODINH TSCD ON TSCD.PK_SEQ = MHSP.TAISAN_FK \n" +
			"     LEFT JOIN \n" + 
			"     ( \n" +
			"     SELECT A.MUAHANG_FK, A.TAISAN_FK, SUM(SOLUONG) AS SOLUONG \n"+
			"	  FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq \n"+
			"	  WHERE trangthai !=4 \n"+
			"	  GROUP BY A.MUAHANG_FK, A.TAISAN_FK \n"+
			"     )HD_TSCD ON HD_TSCD.MUAHANG_FK = MH.PK_SEQ AND HD_TSCD.TAISAN_FK = MHSP.TAISAN_FK \n" +
			"    LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ = MHSP.CCDC_FK \n" +
			"    LEFT JOIN \n" + 
			"    ( \n" +
			"		 SELECT A.MUAHANG_FK, A.CCDC_FK, SUM(SOLUONG) AS SOLUONG \n"+ 
			"	     FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq \n"+
			"	      WHERE trangthai !=4 \n"+
			"	    GROUP BY A.MUAHANG_FK, A.CCDC_FK \n"+
			"    )HD_CCDC ON HD_CCDC.MUAHANG_FK = MH.PK_SEQ AND HD_CCDC.CCDC_FK = MHSP.CCDC_FK \n" +
			"	WHERE (MHSP.SOLUONG  - ISNULL(HD.SOLUONG, 0)) > 0  AND (MHSP.SOLUONG  - ISNULL(HD_TSCD.SOLUONG, 0)) > 0 AND (MHSP.SOLUONG - ISNULL(HD_CCDC.SOLUONG, 0)) > 0  "+
			"			AND HD_DMH.HOADONNCC_FK = " + hdId + " ";
			//"      AND MH.TIENTE_FK = " + this.ttId + " AND MH.TRANGTHAI = '1' AND MH.CONGTY_FK = "+this.CtyId+" \n";
	
			if(this.poNkId.length() > 0){
				query = query +	"AND MH.PK_SEQ IN( " + this.poNkId + ") " ;
			}
*/
		
		System.out.println("1. getPoNk_SP_ChonRs:" + query);
		return this.db.get(query);
	
	}
	
	public void setPoNkIds(String poNkId) {
		
		this.poNkId = poNkId;
	}

	
	public String getPoNkIdsId() {
		
		return this.poNkId;
	}
	
	public void setRequest(HttpServletRequest request){
		this.req = request;
	}
	
	
	//**************** KIỂM TRA SỐ  HÓA ĐƠN ******************//
	
	public boolean ktsohoadon_ncc(){
		
		String query="";
		if(this.hdList.size() >0){
			for(int i = 0; i < this.hdList.size(); i++)
			{
				IErpHoadon hd = hdList.get(i);
				
				query= 	" SELECT A.PK_SEQ, A.SOHOADON, A.NGAYTAO, B.NCC_FK " +
						" FROM ERP_HOADONNCC A " +
						" INNER JOIN ERP_PARK B ON A.PARK_FK=B.PK_SEQ " +
						" WHERE A.SOHOADON= '"+hd.getSohoadon()+"' AND A.NGAYTAO='"+this.NGAYGHINHAN+"' AND B.NCC_FK='"+this.NCCID+"' ";
				System.out.println("kt so hoa don : "+ query);
				ResultSet kt = db.get(query);
				try {
					if(kt.next()){
						String pk_seq= kt.getString("pk_seq");
						this.MSG= "Hiện tại số hóa đơn trùng với " +pk_seq +" được tạo vào ngày " +this.NGAYGHINHAN+ " trong hệ thống. Vui lòng kiểm tra !!!";
						return true;
					}
				} catch (Exception e) {
					e.printStackTrace();
					return false;
				}
				
			}
		}
		
		return false;
	}


	public String getTigia() 
	{
		return this.tigia;
	}

	public void setTigia(String tigia) 
	{

		this.tigia = tigia;
	}

	public void setPoId(String poId) 
	{
		this.poId = poId;
		
	}

	public String getPoId() 
	{
		return this.poId;
	}

	public void setPoRs(ResultSet poRs) 
	{
		this.poRs = poRs;
	}

	public ResultSet getPoRs() 
	{
		return this.poRs;
	}


	public void setChitietPoRs(ResultSet ChitietPoRs)
	{
		this.chitietPoRs = ChitietPoRs;
	}

	public ResultSet getChitietPoRs() 
	{
		return this.chitietPoRs ;
	}
	
	public String getThuesuat() {
		return this.thuesuat;
	}

	public void setThuesuat(String thuesuat) {
		this.thuesuat = thuesuat;
	}

	public void setNCCThayTheId(String NCCThayTheId) 
	{
		this.nccThayTheId = NCCThayTheId;
	}

	public String getNCCThayTheId() 
	{
		return this.nccThayTheId;
	}

	public void setNCCThayTheTen(String NCCThayTheTen) 
	{
		this.nccThayTheTen = NCCThayTheTen;
	}

	public String getNCCThayTheTen() 
	{
		return this.nccThayTheTen ;
	}

	public void setNCCThayTheMST(String NCCThayTheMST) 
	{
		this.nccThayTheMST = NCCThayTheMST;
	}

	public String getNCCThayTheMST() 
	{
		return this.nccThayTheMST;
	}

	public void setNCCThayTheDiaChi(String NCCThayTheDiaChi) 
	{
		this.nccThayTheDiaChi = NCCThayTheDiaChi;
	}

	public String getNCCThayTheDiaChi() 
	{
		return 	this.nccThayTheDiaChi;
	}

	
	public String getnppdangnhap() {
		
		return this.nppdangnhap;
	}

	
	public void setnppdangnhap(String nppdangnhap) {
		
		this.nppdangnhap = nppdangnhap;
	}

	private void getNppInfo()
	{		
		//Phien ban moi
		geso.traphaco.distributor.util.Utility util=new geso.traphaco.distributor.util.Utility();
		this.nppdangnhap=util.getIdNhapp(this.USERID);
	}

	
	public void setLoaidonmhRs(ResultSet LoaidonmhRs) {
		
		this.loaidonmhRs = LoaidonmhRs;
	}

	
	public ResultSet getLoaidonmhRs() {
		
		return this.loaidonmhRs;
	}

	
	public String getLoaidonmh() {
		
		return this.loaidonmhId;
	}

	
	public void setLoaidonmh(String loaidonmh) {
		
		this.loaidonmhId = loaidonmh;
	}

	
	public String getcophieuchi() {
		
		return this.cophieuchi;
	}

	
	public String getTrangthaiHD() {
	
		return this.trangthaiHd;
	}
	@Override
	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}
	@Override
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
	}

	@Override
	public String getMauSoHoaDon() {
		return mauSoHoaDon;
	}

	@Override
	public void setMauSoHoaDon(String mauSoHoaDon) {
		this.mauSoHoaDon = mauSoHoaDon;
	}


	@Override
	public List<IErpChuyenkho> getPhieuCKList() {
		return phieuCKList;
	}

	@Override
	public void setPhieuCKList(List<IErpChuyenkho> phieuCKList) {
		this.phieuCKList = phieuCKList;
	}

	@Override
	public int getDuyet() {
		return duyet;
	}

	@Override
	public void setDuyet(int duyet) {
		this.duyet = duyet;
	}
	

	@Override
	public int getIsDisplay() {
		return isDisplay;
	}

	@Override
	public void setIsDisplay(int isDisplay) {
		this.isDisplay = isDisplay;
	}
	//Dung cho chuc nang in phieu nhap kho trong Duyet HD NCC
	//Neu sua chuc nang nay cua 1 trong 3 chuc nang lon nhu hoa don nhap khau, hoa don trong nuoc , hoa don TSCD thi phai sua ca 3
	//Begin
	@Override
	public List<ISanpham> initPNK_HD_SP() {
		String query = "--INIT SAN PHAM\r\n" + 
				"SELECT SP.MA, SP.TEN, \r\n" + 
				"	(SELECT TAIKHOANKT_FK FROM ERP_LOAISANPHAM LSP\r\n" + 
				"	WHERE LSP.PK_SEQ = SP.LOAISANPHAM_FK) TAIKHOAN,\r\n" + 
				"	(SELECT DIENGIAI FROM DONVIDOLUONG DVDL\r\n" + 
				"	WHERE DVDL.PK_SEQ = SP.DVDL_FK) AS DONVI,\r\n" + 
				"	SUM(HDMH.SOLUONG) AS SOLUONG,\r\n" + 
				"	ISNULL(HDMH.DONGIAVIET, HDMH.DONGIA) DONGIA,\r\n" + 
				"	ROUND(CASE WHEN (SELECT LOAIHD FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") = 0 THEN (SELECT SUM(ISNULL(TNK_CT.VATNK, 0)) FROM ERP_THUENHAPKHAU_CHITIET TNK_CT WHERE TNK_CT.THUENHAPKHAU_FK = (SELECT PK_SEQ FROM ERP_THUENHAPKHAU TNK WHERE TNK.HOADONNCC = HDMH.HOADONNCC_FK) \r\n" + 
						"													AND TNK_CT.SANPHAM_FK = HDMH.SANPHAM_FK)\r\n" + 
				"	ELSE SUM(ISNULL(HDMH.THANHTIENVATVIET, 0)) END, 0) AS THUEVAT,\r\n" + 
				"	ROUND(SUM(HDMH.SOLUONG)*ISNULL(HDMH.DONGIAVIET, HDMH.DONGIA), 0) THANHTIENVIET  \r\n" + 
				"FROM ERP_HOADONNCC_DONMUAHANG HDMH \r\n" + 
				"INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HDMH.SANPHAM_FK\r\n" + 
				"WHERE HDMH.HOADONNCC_FK IN (SELECT PK_SEQ FROM ERP_HOADONNCC HD WHERE HD.PARK_FK =  "+this.ID+") \n" + 
				"GROUP BY SP.PK_SEQ, SP.MA, SP.TEN, LOAISANPHAM_FK, SP.DVDL_FK, HDMH.DONGIAVIET, HDMH.DONGIA, HDMH.HOADONNCC_FK, HDMH.SANPHAM_FK \n"+
				"HAVING SUM(HDMH.SOLUONG) > 0";

		ResultSet rs = db.get(query);
		System.out.println("initPNK_HD_SP: "+query);
		List<ISanpham> tempSpList = new ArrayList<ISanpham>();
		try {
			while (rs.next()) {
				ISanpham sanPham = new Sanpham();
				sanPham.setMa(rs.getString("MA"));
				sanPham.setDiengiai(rs.getString("TEN"));
				sanPham.setTaiKhoan(rs.getString("TAIKHOAN"));
				sanPham.setDvdl(rs.getString("DONVI"));
				sanPham.setSoLuong2(rs.getDouble("SOLUONG"));
				sanPham.setDongiaViet(rs.getString("DONGIA"));
				sanPham.setThanhtienVATVND(rs.getString("THUEVAT"));
				sanPham.setThanhtienVND(rs.getString("THANHTIENVIET"));
				tempSpList.add(sanPham);
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return tempSpList;
		
	}

	@Override
	public String[] initPNK_StringArray() {
		int soCot = 0;
		//1 HOA DON CO THE CO NHIEU PHIEU MUA HANG VOI NHIEU NGUOI TAO VA NHA CUNG CAP KHAC NHAU
		String query = "\r\n" + 
				"SELECT DISTINCT \r\n" + 
				"(SELECT NGAYGHINHAN FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") AS NGAYGHINHAN,\r\n" + 
				"(SELECT ISNULL(SOTTDUYET, 0) FROM ERP_PARK WHERE PK_SEQ = HD.PARK_FK) AS SO,\r\n" + 
				"( STUFF((    SELECT DISTINCT '; ' + (SELECT NV.TEN FROM NHANVIEN NV WHERE NV.PK_SEQ = MH.NGUOITAO) AS [text()]\r\n" + 
				"                        FROM ERP_MUAHANG MH\r\n" + 
				"						WHERE MH.PK_SEQ IN (SELECT DISTINCT MUAHANG_FK FROM ERP_HOADONNCC_DONMUAHANG WHERE HOADONNCC_FK = HD.PK_SEQ)\r\n" + 
				"                        FOR XML PATH('') \r\n" + 
				"                        ), 1, 1, '' )) TENNGUOIMUA,\r\n" + 
				"\r\n" + 
				"REPLACE(( STUFF((    SELECT DISTINCT '; ' + (SELECT NCC.TEN+' - '+NCC.MA	 FROM ERP_NHACUNGCAP NCC WHERE NCC.PK_SEQ = MH.NHACUNGCAP_FK) AS [text()]\r\n" + 
				"                        FROM ERP_MUAHANG MH\r\n" + 
				"						WHERE MH.PK_SEQ IN (SELECT DISTINCT MUAHANG_FK FROM ERP_HOADONNCC_DONMUAHANG WHERE HOADONNCC_FK = HD.PK_SEQ)\r\n" + 
				"                        FOR XML PATH('') \r\n" + 
				"                        ), 1, 1, '' )), 'amp;', '') TENNCC,\r\n" + 
				"( STUFF((    SELECT DISTINCT '; ' + (SELECT NCC.DIACHI FROM ERP_NHACUNGCAP NCC WHERE NCC.PK_SEQ = MH.NHACUNGCAP_FK) AS [text()]\r\n" + 
				"						FROM ERP_MUAHANG MH\r\n" + 
				"						WHERE MH.PK_SEQ IN (SELECT DISTINCT MUAHANG_FK FROM ERP_HOADONNCC_DONMUAHANG WHERE HOADONNCC_FK = HD.PK_SEQ)\r\n" + 
				"                        FOR XML PATH('') \r\n" + 
				"                        ), 1, 1, '' )) DIACHI,\r\n" + 
				"HD.SOHOADON, HD.KYHIEU SERI, HD.NGAYHOADON AS NGAYHOADON,\r\n" + 
				"N'NHẬP HÓA ÐƠN MUA HÀNG' AS NOIDUNG,\r\n" + 
				"(STUFF((    SELECT DISTINCT '; ' + (SELECT SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = (SELECT TAIKHOAN_FK FROM ERP_NHACUNGCAP NCC WHERE NCC.PK_SEQ = MH.NHACUNGCAP_FK)) AS [text()]\r\n" + 
				"                        FROM ERP_MUAHANG MH\r\n" + 
				"						WHERE MH.PK_SEQ IN (SELECT DISTINCT MUAHANG_FK FROM ERP_HOADONNCC_DONMUAHANG WHERE HOADONNCC_FK = HD.PK_SEQ)\r\n" + 
				"                        FOR XML PATH('') \r\n" + 
				"                        ), 1, 1, '' )) AS TAIKHOAN,\r\n" + 
				"ISNULL(CASE WHEN HD.KHOBIETTRU_FK IS NOT NULL THEN (SELECT TEN FROM ERP_KHOTT WHERE PK_SEQ = HD.KHOBIETTRU_FK) \r\n" + 
				"ELSE (SELECT TEN FROM ERP_KHOTT WHERE PK_SEQ = HD.KHOTONTRU_FK) END, '') AS KHONHAN\r\n" + 
				"FROM ERP_HOADONNCC HD \r\n" + 
				"WHERE HD.PARK_FK = "+this.ID;
		ResultSet rs = db.get(query);
		System.out.println("initPNK_StringArray: "+query);
		try {
			ResultSetMetaData rsmd = rs.getMetaData();
			soCot = rsmd.getColumnCount();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String []listThongTin = new String[soCot];
			try {
				if (rs!=null)
					if (rs.next()) {
						for (int i = 0; i < listThongTin.length; i++) {
							listThongTin[i] = rs.getString(i+1);
						}
					}
				rs.close();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	
		return listThongTin;
		
	}
	//end

	public ResultSet getParkList() {
		return ParkList;
	}

	public void setParkList(ResultSet parkList) {
		ParkList = parkList;
	}
	
}