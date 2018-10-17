package geso.traphaco.erp.beans.park.imp;

import geso.traphaco.center.db.sql.Idbutils;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon;
import geso.traphaco.erp.beans.park.IErpHoadon;
import geso.traphaco.erp.beans.park.IErpHoadonSp;
import geso.traphaco.erp.beans.park.IErpPark;
import geso.traphaco.erp.beans.shiphang.ISanpham;
import geso.traphaco.erp.beans.shiphang.imp.Sanpham;
import geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkho;
import geso.traphaco.erp.beans.yeucauchuyenkho.imp.ErpChuyenkho;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

public class ErpPark implements IErpPark
{
	String ID,NCC, NCCID, LOAIHANG,NGAYGHINHAN,USERID,TRANGTHAI,MSG,TinhThueNhapKhau,CtyId,tigia, nppdangnhap, loaihd;
	String ttId, tiente;
	List<IErpHoadon> hdList;

	ResultSet Loaihanglist;
	List<IErpHoadonSp> pnkList;
	ResultSet ttRs;

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

	// Ktra có phiếu chi hay chưa

	String cophieuchi;
	String nguongochh;
	String dienGiaiCT;
	private List<IErpChuyenkho> phieuCKList;
	private int duyet;



	String idKhoNhan="";
	ResultSet rsKhoNhan;
	String Duyethd="";

	public ResultSet getRsKhoNhan() {
		return rsKhoNhan;
	}

	public void setRsKhoNhan(ResultSet rsKhoNhan) {
		this.rsKhoNhan = rsKhoNhan;
	}

	public String getIdKhoNhan() {
		return idKhoNhan;
	}

	public void setIdKhoNhan(String idKhoNhan) {
		this.idKhoNhan = idKhoNhan;
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

		this.loaihd = "";
		this.cophieuchi = "";
		this.nguongochh = "";
		this.dienGiaiCT = "";
		this.duyet = 0;
		this.Duyethd="";
		phieuCKList = new ArrayList<IErpChuyenkho>();
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

		this.loaihd = "";
		this.cophieuchi = "";
		this.nguongochh = "";
		this.dienGiaiCT = "";
		this.duyet = 0;
		phieuCKList = new ArrayList<IErpChuyenkho>();
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
								catch (SQLException e) {
									e.printStackTrace();
								}

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
				catch (SQLException e) {
					e.printStackTrace();
					System.out.println("Exception: " + e.getMessage()); }				
				this.hdList = hdList;

			}
		}
	}

	public void createRs() 
	{ 
		this.getNppInfo();

		//== laay kho nhan

		this.rsKhoNhan = db.get(" select PK_SEQ, MA+'-'+TEN as TEN from ERP_KHOTT where trangthai ='1' ");


		//== laay kho nhan

		this.nccRs = db.get("select PK_SEQ, MA + ', ' + TEN as TEN from ERP_NHACUNGCAP WHERE CONGTY_FK = "+this.CtyId);

		this.loaidonmhRs = db.get("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");

		System.out.println("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");

		if(this.loaihd.equals("0")) // HÓA ĐƠN NHẬP KHẨU
		{
			if(this.NCCID.length() > 0)
			{
				String query = "";			
				query = "select pk_seq as TTID, MA AS TIENTE from ERP_TIENTE where trangthai = '1' AND PK_SEQ = 100000";

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
					query = " select distinct a.PK_SEQ, a.NGAYNHAP NGAYMUA, a.PK_SEQ SOPO, 0 as LOAI,a.tooltip \n"+ 
					" from ERP_NHAPKHONHAPKHAU a \n"+
					" LEFT JOIN \n"+ 
					" ( \n"+
					"	SELECT NHAPKHO_FK, SUM(ISNULL(SOLUONGNHAN,0)) as SOLUONG \n"+
					"	FROM ERP_NHAPKHONHAPKHAU_SANPHAM \n"+
					"	GROUP BY NHAPKHO_FK \n"+
					" ) b on a.PK_SEQ = b.NHAPKHO_FK \n"+
					" LEFT JOIN \n"+
					" ( \n"+
					" SELECT B.MUAHANG_FK,sum(isnull(B.SOLUONG,0)) as SOLUONG \n"+ 
					" FROM ERP_HOADONNCC A INNER JOIN ERP_HOADONNCC_DONMUAHANG B ON A.pk_seq = B.HOADONNCC_FK \n"+
					" INNER JOIN ERP_PARK C ON A.park_fk = C.pk_seq \n" ;
					if(this.ID.trim().length()>0)
						query+= "	AND C.PK_SEQ != "+this.ID+" ";
					query+= " WHERE A.LOAIHD = 0 \n"+
					" GROUP BY B.MUAHANG_FK \n"+
					" )c on a.PK_SEQ = c.MUAHANG_FK \n"+
					" INNER JOIN ERP_MUAHANG MH ON A.MUAHANG_FK = MH.PK_SEQ \n"+
					" WHERE a.NCC_FK = '" + this.NCCID + "' AND MH.TIENTE_FK = " + this.ttId + " AND a.congty_fk = '"+this.CtyId+"' "+
					" AND (isnull(b.SOLUONG,0) - isnull(c.SOLUONG,0)) >0 AND a.TRANGTHAI = 1 "+
					" ORDER BY a.NGAYNHAP desc"; 			
				}	

				System.out.println("LAY SỐ NHẬP KHO NHÀ NHẬP KHẨU : " + query );
				this.poNKRs = db.get(query);


			}

			String query = "";

			if(this.poNkId.trim().length()>0)
			{
				
				
				
				// danh sach san pham
				query = "\n SELECT distinct A.PK_SEQ, A.NGAYNHAP, A.PK_SEQ SOPO,a.tooltip, 0 DUNGSAI, "+
				"\n 0 AS LOAI, B.SANPHAM_FK AS SPID, SP.MA, SP.TEN, ISNULL(B.DONVI, '') DONVI, "+
				"\n B.SOLUONG - isnull(HD.SOLUONG,0) as SOLUONGDAT, ISNULL(HD.SOLUONG,0) AS SOLUONGDARAHD, "+
				"\n '0' AS SOLUONGHD,  ISNULL(B.DONGIA,0) DONGIA, '0' THANHTIEN, "+
				"\n 0 as chiphikhac,isnull(B.VAT,0) VAT, 0 TIENVAT, ISNULL( B.SOLO , '') SOLO "+
				"\n FROM ERP_NHAPKHONHAPKHAU A "+
				"\n INNER JOIN ERP_MUAHANG MH ON A.MUAHANG_FK = MH.PK_SEQ "+
				"\n INNER JOIN ERP_NHAPKHONHAPKHAU_SP_CHITIET B ON A.PK_SEQ = B.NHAPKHO_FK "+
				"\n INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = B.SANPHAM_FK  "+
				"\n LEFT JOIN "+
				"\n  ( "+
				"\n	 SELECT A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '') SOLO , SUM(SOLUONG) AS SOLUONG "+
				"\n	 FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
				"\n	 WHERE trangthai !=4 AND SOLUONG > 0 "+
				"\n	 GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '')  "+
				"\n  )HD ON HD.MUAHANG_FK = A.PK_SEQ AND HD.SANPHAM_FK = B.SANPHAM_FK AND B.SOLO = HD.SOLO "+
				"\n WHERE (B.SOLUONG  - ISNULL(HD.SOLUONG, 0)) > 0 "+
				"\n   AND A.CONGTY_FK = "+this.CtyId+" AND A.TRANGTHAI = 1 ";

				if(this.poNkId.length() > 0){
					query = query +	"AND A.PK_SEQ IN( " + this.poNkId + ") " ;
				}
				System.out.println("2. this.poNK_SPRs: " + query);
				this.poNK_SPRs = this.db.get(query);
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
							//								String tiente = hdRs.getString("tiente");
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
								"\n INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = B.SANPHAM_FK "+
								"\n INNER JOIN ERP_MUAHANG MH ON A.MUAHANG_FK = MH.PK_SEQ "+
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
		else if(this.loaihd.equals("1")) // HÓA ĐƠN TRONG NƯỚC
		{
			if(this.NCCID.length() > 0)
			{
				String query = "";			

				// Load POLIST

				if(this.loaidonmhId.trim().length()>0)
				{
					query = "\n select distinct a.PK_SEQ, a.NGAYMUA,  a.SOPO, isnull(a.LOAI,0) LOAI, a.tooltip "+   
					"\n from ERP_MUAHANG a  LEFT JOIN "+ 
					"\n (SELECT PBMH.PODUOCPB MUAHANG_FK,sum(isnull(SOLUONG,0)) as SOLUONG "+
					"\n	 FROM erp_phanbomuahang_sp_chitiet PBCT INNER JOIN ERP_PHANBOMUAHANG_PO PBMH ON PBCT.PHANBO_FK = PBMH.PHANBO_FK "+
					"\n	 INNER JOIN ERP_PHANBOMUAHANG PB ON PBMH.PHANBO_FK = PB.PK_SEQ "+
					"\n	 WHERE PB.TRANGTHAI IN ( 0,1) AND PBCT.CONGTY_FK = "+ this.CtyId +
					"\n	 GROUP BY PBMH.PODUOCPB) b on a.PK_SEQ = b.MUAHANG_FK "+
					"\n LEFT JOIN "+ 
					"\n (SELECT B.MUAHANG_FK,sum(isnull(B.SOLUONG,0)) as SOLUONG "+
					"\n	FROM ERP_HOADONNCC A INNER JOIN ERP_HOADONNCC_DONMUAHANG B ON A.pk_seq = B.HOADONNCC_FK "+
					"\n   INNER JOIN ERP_PARK C ON A.park_fk = C.pk_seq "+
					"\n 	WHERE A.trangthai != 4 ";
					if(this.ID.trim().length()>0)
						query+= "\n	AND C.PK_SEQ != "+this.ID+" ";

					query+=	"\n GROUP BY B.MUAHANG_FK) c on a.PK_SEQ = c.MUAHANG_FK "+
					"\n where a.trangthai = 1 and NHACUNGCAP_FK = '" + this.NCCID + "'  "+
					"\n AND (isnull(b.SOLUONG,0) - isnull(c.SOLUONG,0)) >0  " +
					"\n AND a.LOAI = "+this.loaidonmhId;
					if(this.loaidonmhId.equals("1"))
						query+= "\n AND isnull(a.isduocphanbo,0) = 1 ";

					query+= "\n	AND a.CONGTY_FK = "+this.CtyId+"  "+
					"\n order by a.NGAYMUA desc";				
				}	

				System.out.println("LAY PO : " + query );
				this.poNKRs = db.get(query);


			}

			String query = "";

			if(this.poNkId.trim().length()>0)
			{	  
				query = "\n SELECT distinct MH.PK_SEQ, MH.NGAYMUA, "+
				"\n MH.SOPO, '0' as DUNGSAI, "+
				"\n 0 LOAI , PBCT.SANPHAM_FK  SPID , SP.MA  AS MA , PBCT.SOLO, "+
				"\n SP.TEN TEN , PBCT.DONVI, PBCT.SOLUONG -  isnull(HDSP.SOLUONG,0) AS SOLUONGDAT, "+
				"\n ISNULL(HDSP.SOLUONG, 0) SOLUONGDARAHD , 0 AS SOLUONGHD ,  " +						
				"\n PBCT.DONGIA , 0 AS THANHTIEN , "+
				"\n ISNULL(MH.CHIPHIKHAC,0)as chiphikhac, "+
				"\n ( SELECT top(1) isnull(isnull(A.thuexuat,A.VAT),0) thuesuat "+
				"\n  FROM ERP_MUAHANG_SP A WHERE MH.PK_SEQ  = A.MUAHANG_FK AND PBCT.SANPHAM_FK = A.SANPHAM_FK ) thuesuat, "+
				"\n 0 TIENVAT , "+
				"\n PBCT.PHANBO_FK, MH.tooltip "+

				"\n FROM ERP_MUAHANG MH "+
				"\n INNER JOIN ERP_PHANBOMUAHANG_SP_CHITIET PBCT ON mh.CONGTY_FK = PBCT.CONGTY_FK AND MH.PK_SEQ = PBCT.MUAHANG_FK "+
				"\n INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = PBCT.SANPHAM_FK "+
				"\n LEFT JOIN "+
				"\n ( "+ 
				"\n	 SELECT A.MUAHANG_FK, A.SANPHAM_FK, SUM(SOLUONG) AS SOLUONG , ISNULL(A.SOLO, '') SOLO "+
				"\n	 FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
				"\n	 INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ "+
				"\n	 WHERE C.trangthai !=4 AND SOLUONG > 0 "+
				"\n	 GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '') "+
				"\n  )HDSP ON HDSP.MUAHANG_FK = MH.PK_SEQ AND HDSP.SANPHAM_FK = PBCT.SANPHAM_FK AND HDSP.SOLO = PBCT.SOLO "+    
				"\n WHERE (PBCT.SOLUONG  - ISNULL(HDSP.SOLUONG, 0)) > 0 "+
				"\n  AND MH.CONGTY_FK = "+this.CtyId;


				if(this.poNkId.length() > 0){
					query = query +	"AND MH.PK_SEQ IN( " + this.poNkId + ") " ;
				}
				System.out.println("2. this.poNK_SPRs: " + query);
				this.poNK_SPRs = this.db.get(query);
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
							//								String tiente = hdRs.getString("tiente");
							this.ttId = hdRs.getString("TTID");

							hoadon = new ErpHoadon(pk_seq, kyhieu, String.valueOf(thuesuat), sohoadon, park, ngayhoadon, sotienavat, sotienbvat, vat, "", trangthai, "");
							hoadon.setMauhoadon(mauhoadon);
							hoadon.setMausohoadon(mausohoadon);

							// LẤY SẢN PHẨM HÓA ĐƠN
							query = "\n	SELECT distinct MH.PK_SEQ, MH.NGAYMUA, "+
							"\n	MH.SOPO, ISNULL(MH.DUNGSAI,0) as DUNGSAI, "+
							"\n	0 LOAI , PBCT.SANPHAM_FK  SPID , SP.MA  AS MA , PBCT.SOLO, "+
							"\n	SP.TEN TEN , PBCT.DONVI, PBCT.SOLUONG -  isnull(HDSP.SOLUONG,0) AS SOLUONGDAT, "+
							"\n	ISNULL(HDSP.SOLUONG, 0) SOLUONGDARAHD , ISNULL(HDSP_HT.SOLUONG, 0) AS SOLUONGHD , "+ 
							"\n	ISNULL(HDSP_HT.DONGIA,0) DONGIA , ISNULL(HDSP_HT.THANHTIEN,0) AS THANHTIEN , "+
							"\n	ISNULL(MH.CHIPHIKHAC,0)as chiphikhac, "+
							"\n	( SELECT top(1) isnull(isnull(A.thuexuat,A.VAT),0) thuesuat "+
							"\n	  FROM ERP_MUAHANG_SP A WHERE MH.PK_SEQ  = A.MUAHANG_FK AND PBCT.SANPHAM_FK = A.SANPHAM_FK ) thuesuat, "+
							"\n	ISNULL(HDSP_HT.TIENVAT,0) TIENVAT , "+
							"\n	 PBCT.PHANBO_FK, MH.tooltip "+
							"\n	FROM ERP_MUAHANG MH "+
							"\n	INNER JOIN ERP_PHANBOMUAHANG_SP_CHITIET PBCT ON MH.PK_SEQ = PBCT.MUAHANG_FK AND mh.CONGTY_FK = PBCT.CONGTY_FK "+  
							"\n	INNER JOIN ERP_SANPHAM SP ON SP.PK_SEQ = PBCT.SANPHAM_FK "+
							"\n	LEFT JOIN "+
							"\n	( "+
							"\n	SELECT A.MUAHANG_FK, A.SANPHAM_FK, SUM(SOLUONG) AS SOLUONG , ISNULL(A.SOLO, '') SOLO "+
							"\n	FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
							"\n	INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ "+
							"\n	WHERE C.trangthai !=4 AND A.HOADONNCC_FK != "+pk_seq+" AND SOLUONG > 0 "+
							"\n	GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '') "+
							"\n	)HDSP ON HDSP.MUAHANG_FK = MH.PK_SEQ AND HDSP.SANPHAM_FK = PBCT.SANPHAM_FK AND HDSP.SOLO = PBCT.SOLO "+
							"\n LEFT JOIN "+
							"\n (  "+
							"\n	SELECT A.MUAHANG_FK, A.SANPHAM_FK, isnull( A.DONGIA, 0) DONGIA , SUM(SOLUONG) AS SOLUONG , ISNULL(A.SOLO, '') SOLO, A.THANHTIEN, A.TIENVAT "+
							"\n	FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
							"\n INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ "+
							"\n WHERE A.HOADONNCC_FK = "+pk_seq+" AND SOLUONG > 0 "+
							"\n GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, ''), A.THANHTIEN, A.TIENVAT,  isnull( A.DONGIA, 0) "+
							"\n )HDSP_HT ON HDSP_HT.MUAHANG_FK = MH.PK_SEQ AND HDSP_HT.SANPHAM_FK = PBCT.SANPHAM_FK AND HDSP_HT.SOLO = PBCT.SOLO "+
							"\n	WHERE (PBCT.SOLUONG  - ISNULL(HDSP.SOLUONG, 0)) > 0 "+
							"\n	AND MH.TIENTE_FK = "+ this.ttId +"  AND MH.PK_SEQ  IN ("+this.poNkId+")";

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
								String solo = hdsp.getString("SOLO");
								String chon = "0";
								String tiente_fk = "";
								//String dungsai = hdsp.getString("DUNGSAI");
								String dungsai = "0";
								//String ngaynhandk = hdsp.getString("NGAYNHANDK");
								String soluongdaraHD = hdsp.getString("SOLUONGDARAHD");
								String sp_vat = hdsp.getString("thuesuat");
								String sp_tienvat = hdsp.getString("TIENVAT");
								IErpHoadonSp sp = new ErpHoadonSp(poId, poTen, sanphamId, sanphamten, soluongdat, dongia, thanhtien, donvi, loai, chon, tiente_fk);
								sp.setSoluonghd(soluonghd);
								sp.setDungsai(dungsai);	
								sp.setSoluongRaHD(soluongdaraHD);
								//sp.setNgaynhan(ngaynhandk);
								sp.setSolo(solo);
								sp.setSanphamMa(sanphamma);
								sp.setVAT(sotienbvat);
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
		else // CHI PHÍ, DỊCH VỤ
		{
			if(this.NCCID.length() > 0)
			{
				String query = "";			
				query = "select pk_seq as TTID, MA AS TIENTE from ERP_TIENTE where trangthai = '1' ";

				System.out.println("___LAY TIEN TE 22: " + query);
				ttRs = this.db.get(query);

				// Load POLIST
				if(this.loaidonmhId.trim().length()>0)
				{
					query = "\n select distinct a.PK_SEQ, a.NGAYMUA,  a.SOPO, isnull(a.LOAI,0) LOAI,  a.LOAIHANGHOA_FK AS LOAIHANGHOA "+   
					"\n from ERP_MUAHANG a  LEFT JOIN "+ 
					"\n (SELECT MUAHANG_FK,sum(isnull(SOLUONG,0)) as SOLUONG "+
					"\n	FROM ERP_MUAHANG_SP "+
					"\n	GROUP BY MUAHANG_FK) b on a.PK_SEQ = b.MUAHANG_FK "+
					"\n LEFT JOIN "+ 
					"\n (SELECT B.MUAHANG_FK,sum(isnull(B.SOLUONG,0)) as SOLUONG "+
					"\n	FROM ERP_HOADONNCC A INNER JOIN ERP_HOADONNCC_DONMUAHANG B ON A.pk_seq = B.HOADONNCC_FK "+
					"\n   INNER JOIN ERP_PARK C ON A.park_fk = C.pk_seq "+
					"\n 	WHERE A.trangthai != 4 ";
					if(this.ID.trim().length()>0)
						query+= "\n	AND C.PK_SEQ != "+this.ID+" ";

					query+=	"\n GROUP BY B.MUAHANG_FK) c on a.PK_SEQ = c.MUAHANG_FK "+
					"\n where a.trangthai = 1 and NHACUNGCAP_FK = '" + this.NCCID + "' "+
					"\n AND (isnull(b.SOLUONG,0) - isnull(c.SOLUONG,0)) >0  " +
					"\n AND a.LOAI = "+this.loaidonmhId;
					if(this.loaidonmhId.equals("1"))
						query+= "\n AND isnull(a.isduocphanbo,0) = 1 ";

					query+= "\n	AND a.CONGTY_FK = "+this.CtyId+"  "+
					"\n order by a.NGAYMUA desc";				
				}	

				System.out.println("LAY PO : " + query );
				this.poNKRs = db.get(query);
			}

			String query = "";

			if(this.poNkId.trim().length()>0)
			{




				//LAY DANH SACH SAN PHAM CUA DON
				query="  SELECT DISTINCT   MHSP.PK_SEQ AS MHSP_FK,MH.PK_SEQ, MH.NGAYMUA, '' SOLO , \n " + 
				"  		MH.SOPO, ISNULL(MH.DUNGSAI,0) as DUNGSAI,   \n" + 
				"  CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN '0'    \n" + 
				"       WHEN MHSP.TAISAN_FK IS NOT NULL THEN '1'  \n " + 
				"  	 WHEN MHSP.CCDC_FK IS NOT NULL THEN '2'   \n" + 
				"  	 WHEN MHSP.CHIPHI_FK IS NOT NULL THEN '3'  \n " + 
				"       ELSE ''   \n" + 
				"       END  AS LOAI ,   \n" + 
				"  CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ    \n" + 
				"       WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ   \n" + 
				"  	 WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ   \n" + 
				"  	 WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.PK_SEQ  \n " + 
				"       END  AS SPID ,   \n" + 
				"  CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.MA    \n" + 
				"       WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.MA   \n" + 
				"  	 WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.MA   \n" + 
				" 		 WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.TEN  \n " + 
				"       END  AS MA ,   \n" + 
				"  CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.TEN    \n" + 
				"       WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.TEN   \n" + 
				"  	 WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.DIENGIAI   \n" + 
				" 		 WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.DIENGIAI   \n" + 
				"       END  AS TEN ,   \n" + 
				"    MHSP.DONVI, MHSP.SOLUONG -   \n" + 
				" ISNULL(HD.SOLUONG,0)  AS SOLUONGDAT,   \n" + 
				"    HD.SOLUONG AS SOLUONGDARAHD , '0' AS SOLUONGHD ,  \n" + 
				"  MHSP.DONGIA, MHSP.THANHTIEN , ISNULL(MH.CHIPHIKHAC,0)as chiphikhac,    \n" + 
				"  CASE WHEN MH.LOAI = 0 THEN   \n" + 
				"       (SELECT b.NGAYNHAN FROM ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK where a.MUAHANG_FK = mh.PK_SEQ and b.SANPHAM_FK = mhsp.SANPHAM_FK AND a.TRANGTHAI = 1 )   \n" + 
				"  ELSE ISNULL(MHSP.NGAYNHAN,'') END NGAYNHANDK, isnull(isnull(MHSP.thuexuat,MHSP.VAT),0) VAT, 0 TIENVAT    \n" + 
				"  FROM ERP_MUAHANG MH   \n" + 
				"       INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ   \n" + 
				"  	   LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MHSP.SANPHAM_FK   \n" + 
				"       LEFT JOIN   \n" + 
				"       (   \n" + 
				" 		 SELECT A.MUAHANG_FK, A.MHSP_FK, SUM(SOLUONG) AS SOLUONG   \n" + 
				"   		 FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq   \n" + 
				"   		 WHERE trangthai !=4   \n" + 
				"   		 GROUP BY A.MUAHANG_FK, A.MHSP_FK    \n" + 
				"       )HD ON HD.MUAHANG_FK = MH.PK_SEQ AND HD.MHSP_FK = MHSP.PK_SEQ   \n" + 
				"      LEFT JOIN ERP_MASCLON TSCD ON TSCD.PK_SEQ = MHSP.TAISAN_FK   \n" + 
				"     LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ = MHSP.CCDC_FK   \n" + 
				"  LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = MHSP.CHIPHI_FK   \n" + 
				" WHERE (MHSP.SOLUONG  - ISNULL(HD.SOLUONG, 0)) > 0    \n " + 
				"   AND MH.CONGTY_FK = "+this.CtyId;


				if(this.poNkId.length() > 0){
					query = query +	"AND MH.PK_SEQ IN( " + this.poNkId + ") \n " ;
				}
				System.out.println("2. this.poNK_SPRs: " + query);
				this.poNK_SPRs = this.db.get(query);

				query = "select LOAIHANGHOA_FK, nguongochh, tiente_fk, tygiaquydoi from erp_muahang where pk_seq in ("+this.poNkId +")";
				System.out.println("[thongtintiente] "+query);
				ResultSet rsdmh = db.get(query);
				try
				{
					if(rsdmh != null)
					{
						while(rsdmh.next())
						{
							this.nguongochh = rsdmh.getString("nguongochh");
							this.ttId = rsdmh.getString("tiente_fk");
							this.LOAIHANG=rsdmh.getString("LOAIHANGHOA_FK");
							// loai hang 0: nhap kho ccdc/phu tung
							//loai hang 1: tai san co dinh
							//loai hang 2: chi phi dich vu
							
							if(this.tigia.trim().length() <= 0)
								this.tigia = rsdmh.getString("tygiaquydoi");
						}rsdmh.close();
					}
				}
				catch(Exception e)
				{
					e.printStackTrace();
				}
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
							//									String tiente = hdRs.getString("tiente");
							this.ttId = hdRs.getString("TTID");

							hoadon = new ErpHoadon(pk_seq, kyhieu, String.valueOf(thuesuat), sohoadon, park, ngayhoadon, sotienavat, sotienbvat, vat, "", trangthai, "");
							hoadon.setMauhoadon(mauhoadon);
							hoadon.setMausohoadon(mausohoadon);

							// LẤY SẢN PHẨM HÓA ĐƠN
							query=	" SELECT   MHSP.PK_SEQ AS MHSP_FK , MH.PK_SEQ, MH.NGAYMUA,  \n " + 
							" MH.SOPO, ISNULL(MH.DUNGSAI,0) as DUNGSAI,   \n" + 
							" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN '0'  \n " + 
							" WHEN MHSP.TAISAN_FK IS NOT NULL THEN '1'   \n" + 
							" WHEN MHSP.CCDC_FK IS NOT NULL THEN '2'   \n" + 
							" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN '3'   \n" + 
							" ELSE ''   \n" + 
							" END  AS LOAI ,  \n " + 
							" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ   \n" + 
							" WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ   \n" + 
							" WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ   \n" + 
							" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.PK_SEQ   \n" + 
							" END  AS SPID ,  \n " + 
							" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.MA   \n" + 
							" WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.MA  \n " + 
							" WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.MA  \n " + 
							" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.TEN   \n" + 
							" END  AS MA ,  \n " + 
							" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.TEN   \n" + 
							" WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.TEN   \n" + 
							" WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.DIENGIAI   \n" + 
							" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.DIENGIAI   \n" + 
							" END  AS TEN ,  \n " + 
							" MHSP.DONVI, MHSP.SOLUONG -   \n " + 
							"  ISNULL(HD.SOLUONG,0) AS SOLUONGDAT,   \n" + 
							"  ISNULL(HD.SOLUONG,0)  AS SOLUONGDARAHD ,  \n" + 
							" ISNULL(HD_CR.SOLUONG,0)  AS SOLUONGHD ,  \n" + 
							" ISNULL(HD_CR.DONGIA,0) AS  DONGIA ,   \n" + 
							" ISNULL(HD_CR.THANHTIEN,0)  AS THANHTIEN ,   \n" + 
							" ISNULL(MH.CHIPHIKHAC,0)as chiphikhac,   \n" + 
							" CASE WHEN MH.LOAI = 0 THEN   \n" + 
							" (SELECT b.NGAYNHAN FROM ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b  \n " + 
							" on a.PK_SEQ = b.SHIPHANG_FK where a.MUAHANG_FK = mh.PK_SEQ and b.SANPHAM_FK = mhsp.SANPHAM_FK AND a.TRANGTHAI = 1 )   \n" + 
							" ELSE ISNULL(MHSP.NGAYNHAN,'') END NGAYNHANDK, isnull(isnull(MHSP.thuexuat,MHSP.VAT),0) thuexuat,   \n" + 
							"   \n" + 
							" isnull(HD_CR.TIENVAT,0)  AS TIENVAT    \n" + 
							" FROM ERP_MUAHANG MH  \n " + 
							" INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ   \n" + 
							" LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MHSP.SANPHAM_FK   \n" + 
							" LEFT JOIN   \n" + 
							" (  \n " + 
							" SELECT A.MUAHANG_FK, A.SANPHAM_FK ,A.TAISAN_FK,A.CHIPHI_FK,A.CCDC_FK,A.MHSP_FK  , SUM(SOLUONG) AS SOLUONG  \n " + 
							" FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq   \n" + 
							" INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ   \n" + 
							" WHERE C.trangthai !=4 AND A.HOADONNCC_FK != "+pk_seq+"   \n" + 
							" GROUP BY A.MUAHANG_FK, A.SANPHAM_FK ,A.TAISAN_FK,A.CHIPHI_FK,A.CCDC_FK,A.MHSP_FK   \n " + 
							"   \n" + 
							" )HD ON HD.MUAHANG_FK = MHSP.MUAHANG_FK AND ISNULL(HD.MHSP_FK,0) =ISNULL(MHSP.PK_SEQ,0)  \n" + 
							" LEFT JOIN  \n " + 
							" (   \n" + 
							" SELECT A.MUAHANG_FK, A.SANPHAM_FK ,A.TAISAN_FK,A.CHIPHI_FK,A.CCDC_FK,A.MHSP_FK ,A.DONGIA,A.THANHTIEN,A.TIENVAT  ,  SOLUONG  AS SOLUONG   \n" + 
							" FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq   \n" + 
							" INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ  \n " + 
							" WHERE A.HOADONNCC_FK = "+pk_seq+"   \n" + 
							"   " + 
							" )HD_CR ON HD_CR.MUAHANG_FK = MHSP.MUAHANG_FK AND ISNULL(HD_CR.MHSP_FK,0) =ISNULL(MHSP.PK_SEQ,0)  \n" + 
							"   \n" + 
							" LEFT JOIN ERP_MASCLON TSCD ON TSCD.PK_SEQ = MHSP.TAISAN_FK  \n " + 
							" LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ = MHSP.CCDC_FK   \n" + 
							"  \n " + 
							" LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = MHSP.CHIPHI_FK  \n " + 
							" WHERE (MHSP.SOLUONG  - ISNULL(HD.SOLUONG, 0)) > 0   \n " + 
							" AND MH.PK_SEQ  IN ("+this.poNkId+")  \n";		

							System.out.println("GET SP CHI TIET 1111:"+query);
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
								String ngaynhandk = hdsp.getString("NGAYNHANDK");
								String soluongdaraHD = hdsp.getString("SOLUONGDARAHD");
								String sp_vat = hdsp.getString("thuexuat");
								String sp_tienvat = hdsp.getString("TIENVAT");

								IErpHoadonSp sp = new ErpHoadonSp(poId, poTen, sanphamId, sanphamten, soluongdat, dongia, thanhtien, donvi, loai, chon, tiente_fk);
								sp.setSoluonghd(soluonghd);
								sp.setDungsai(dungsai);	
								sp.setSoluongRaHD(soluongdaraHD);
								sp.setNgaynhan(ngaynhandk);
								sp.setSanphamMa(sanphamma);
								sp.setVAT(sp_vat);
								sp.setTienVat(sp_tienvat);
								sp.setMHSP_FK(hdsp.getString("MHSP_FK"));
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

						//						String nkId = pnkRs.getString("nkId");
						//						String sopnk=pnkRs.getString("SOPHIEUNHAPHANG");
						//						String ngaynhapkho = pnkRs.getString("ngaynhapkho");
						//						String tonggiatri = pnkRs.getString("tonggiatri");
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
				catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			System.out.println("toi day");
			this.pnkList = pnkList;
		}
	}

	public void init() 
	{

		String query = "";  

		query = " SELECT count(TTHD.HOADON_FK) cophieuchi FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
		" WHERE TT.NCC_FK is not null AND TTHD.LOAIHD = 0 AND TT.TRANGTHAI !=2 AND TTHD.HOADON_FK = "+this.ID;

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

		if(this.loaihd.equals("0")) // HÓA ĐƠN NHẬP KHẨU
		{
			query = "SELECT DISTINCT PARK.TRANGTHAI,PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, HD.TINHTHUENHAPKHAU,  ISNULL(PARK.TIGIA,1) AS TIGIA ,  \n" +
			"  		TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU, HD.nguongochh, \n" +
			"  		NCCTT.PK_SEQ AS NCCTTID, ISNULL(NCCTT.TEN,'') AS NCCTTTEN, ISNULL(NCCTT.MASOTHUE,'') AS MST, ISNULL(NCCTT.DIACHI,'') DIACHI, HD.LOAIHD,ISNULL(PARK.DIENGIAI,'') AS DIENGIAI, isnull(HD.KHOTONTRU_FK,0) AS KHOTONTRU_FK  \n"+
			"FROM ERP_HOADONNCC HD \n" +
			"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK \n" + 
			"INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_DMH ON HD_DMH.HOADONNCC_FK = HD.PK_SEQ \n" +
			"INNER JOIN ERP_NHAPKHONHAPKHAU KHONK ON KHONK.PK_SEQ = HD_DMH.MUAHANG_FK \n"+
			"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = KHONK.MUAHANG_FK \n" +
			"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = MH.TIENTE_FK \n" +
			"LEFT JOIN ERP_NHACUNGCAPTHAYTHE NCCTT ON NCCTT.PK_SEQ = PARK.NCCTHAYTHE_FK \n" +
			"WHERE HD.PARK_FK = '" + this.ID + "'";  
		}
		else if (this.loaihd.equals("1")) // TRONG NƯỚC
		{
			query = 	"SELECT DISTINCT PARK.TRANGTHAI,PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, HD.TINHTHUENHAPKHAU,  ISNULL(PARK.TIGIA,1) AS TIGIA ,  \n" +
			"  		TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU, HD.nguongochh, \n" +
			"  		NCCTT.PK_SEQ AS NCCTTID, ISNULL(NCCTT.TEN,'') AS NCCTTTEN, ISNULL(NCCTT.MASOTHUE,'') AS MST, ISNULL(NCCTT.DIACHI,'') DIACHI, HD.LOAIHD,ISNULL(PARK.DIENGIAI,'') AS DIENGIAI ,isnull(HD.KHOTONTRU_FK,0) AS KHOTONTRU_FK \n"+
			"FROM ERP_HOADONNCC HD \n" +
			"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK \n" + 
			"INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_DMH ON HD_DMH.HOADONNCC_FK = HD.PK_SEQ \n" + 
			"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = HD_DMH.MUAHANG_FK \n" +
			"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = MH.TIENTE_FK \n" +
			"LEFT JOIN ERP_NHACUNGCAPTHAYTHE NCCTT ON NCCTT.PK_SEQ = PARK.NCCTHAYTHE_FK \n" +
			"WHERE HD.PARK_FK = '" + this.ID + "'";  		
		}
		else  // CHI PHÍ DỊCH VỤ
		{
			query = "SELECT DISTINCT PARK.TRANGTHAI,PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, HD.TINHTHUENHAPKHAU,  ISNULL(PARK.TIGIA,1) AS TIGIA ,  \n" +
			"  		TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU, HD.nguongochh, \n" +
			"  		NCCTT.PK_SEQ AS NCCTTID, ISNULL(NCCTT.TEN,'') AS NCCTTTEN, ISNULL(NCCTT.MASOTHUE,'') AS MST, ISNULL(NCCTT.DIACHI,'') DIACHI, HD.LOAIHD,ISNULL(PARK.DIENGIAI,'') AS DIENGIAI ,isnull(HD.KHOTONTRU_FK,0) AS KHOTONTRU_FK  \n"+
			"FROM ERP_HOADONNCC HD \n" +
			"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK \n" + 
			"INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_DMH ON HD_DMH.HOADONNCC_FK = HD.PK_SEQ \n" + 
			"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = HD_DMH.MUAHANG_FK \n" +
			"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = MH.TIENTE_FK \n" +
			"LEFT JOIN ERP_NHACUNGCAPTHAYTHE NCCTT ON NCCTT.PK_SEQ = PARK.NCCTHAYTHE_FK \n" +
			"WHERE HD.PARK_FK = '" + this.ID + "'";  
		}

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
					this.nguongochh = rs.getString("nguongochh");
					this.dienGiaiCT = rs.getString("DIENGIAI");

					this.idKhoNhan=rs.getString("KHOTONTRU_FK");

				}else{
					// Truong hop Display nhung chung tu dau ky
					query = " SELECT DISTINCT PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, PARK.TINHTHUENHAPKHAU, " +
					" TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU,ISNULL(PARK.DIENGIAI,'') AS DIENGIAI, isnull(HD.KHOTONTRU_FK,0) AS KHOTONTRU_FK " +
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
								this.dienGiaiCT = rs.getString("DIENGIAI");

								this.idKhoNhan=rs.getString("KHOTONTRU_FK");
							}
							rs.close();

						} 
						catch (Exception e) {
							e.printStackTrace();
						}
					}	
				}

			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}		
		this.createRs();
	}

	public void initDisplay() 
	{
		String query = " SELECT count(TTHD.HOADON_FK) cophieuchi FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
		" WHERE TT.NCC_FK is not null AND TTHD.LOAIHD = 0 AND TT.TRANGTHAI !=2 AND TTHD.HOADON_FK = "+this.ID;

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

		query ="SELECT DISTINCT  HD.KHOTONTRU_FK, PARK.TRANGTHAI,PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, HD.TINHTHUENHAPKHAU,  ISNULL(PARK.TIGIA,1) AS TIGIA ,  \n" +
		"  		TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU, HD.nguongochh, \n" +
		"  		NCCTT.PK_SEQ AS NCCTTID, ISNULL(NCCTT.TEN,'') AS NCCTTTEN, ISNULL(NCCTT.MASOTHUE,'') AS MST, ISNULL(NCCTT.DIACHI,'') DIACHI, HD.LOAIHD,ISNULL(PARK.DIENGIAI,'') AS DIENGIAI ,isnull(HD.KHOTONTRU_FK,0) AS KHOTONTRU_FK  \n"+
		"FROM ERP_HOADONNCC HD \n" +
		"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK \n" + 
		"INNER JOIN ERP_HOADONNCC_DONMUAHANG HD_DMH ON HD_DMH.HOADONNCC_FK = HD.PK_SEQ \n" + 
		"INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = HD_DMH.MUAHANG_FK \n" +
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
					this.LOAIHANG=rs.getString("KHOTONTRU_FK");

					// Load NCC thay thế nếu có

					this.nccThayTheId = rs.getString("NCCTTID");	
					this.nccThayTheTen = rs.getString("NCCTTTEN");	
					this.nccThayTheMST = rs.getString("MST");	
					this.nccThayTheDiaChi = rs.getString("DIACHI");	

					this.poNkId = rs.getString("PO_NHAPKHAU");	
					this.nguongochh = rs.getString("nguongochh");
					this.dienGiaiCT = rs.getString("DIENGIAI");

					this.idKhoNhan=rs.getString("KHOTONTRU_FK");
				}else{
					// Truong hop Display nhung chung tu dau ky
					query = " SELECT DISTINCT PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, PARK.TINHTHUENHAPKHAU, " +
					" TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU,ISNULL(PARK.DIENGIAI,'') AS DIENGIAI, isnull(HD.KHOTONTRU_FK) AS KHOTONTRU_FK " +
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
								this.dienGiaiCT = rs.getString("DIENGIAI");

								this.idKhoNhan=rs.getString("KHOTONTRU_FK");
							}
							rs.close();

						} 
						catch (Exception e) {
							e.printStackTrace();
						}
					}	
				}
			} 
			catch (SQLException e) {
				e.printStackTrace();
			}
		}		

		this.getNppInfo();

		this.nccRs = db.get("select PK_SEQ, MA + ', ' + TEN as TEN from ERP_NHACUNGCAP WHERE CONGTY_FK = "+this.CtyId);

		this.loaidonmhRs = db.get("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");

		this.rsKhoNhan = db.get(" select PK_SEQ, MA+'-'+TEN as TEN from ERP_KHOTT where trangthai ='1' ");

		if(this.NCCID.length() > 0)
		{
			query = "";			
			query = "select pk_seq as TTID, MA AS TIENTE from ERP_TIENTE where trangthai = '1' ";

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
				query = " select distinct PK_SEQ, NGAYMUA,  SOPO, isnull(a.LOAI,0) LOAI \n"+   
				" from ERP_MUAHANG a  " +
				" WHERE a.trangthai = 1 and a.PK_SEQ IN (SELECT B.MUAHANG_FK FROM ERP_HOADONNCC A INNER JOIN ERP_HOADONNCC_DONMUAHANG B ON A.pk_seq = B.HOADONNCC_FK" +
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
			query =	"SELECT DISTINCT HD.*, isnull(HD.TONGSOLUONG,0) as TONGLUONG ,  TT.MA AS TIENTE, TT.PK_SEQ AS TTID " + 
			"FROM ERP_HOADONNCC HD " +
			"INNER JOIN ERP_PARK PARK ON PARK.PK_SEQ = HD.PARK_FK " + 
			"INNER JOIN ERP_TIENTE TT ON TT.PK_SEQ = PARK.TIENTE_FK " + 
			"WHERE HD.PARK_FK = " + this.ID; 

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
						//								String tiente = hdRs.getString("tiente");
						this.ttId = hdRs.getString("TTID");

						hoadon = new ErpHoadon(pk_seq, kyhieu, String.valueOf(thuesuat), sohoadon, park, ngayhoadon, sotienavat, sotienbvat, vat, "", trangthai, "");
						hoadon.setMauhoadon(mauhoadon);
						hoadon.setMausohoadon(mausohoadon);


						query=	" SELECT   MHSP.PK_SEQ AS MHSP_FK , MH.PK_SEQ, MH.NGAYMUA,  \n " + 
						" MH.SOPO, ISNULL(MH.DUNGSAI,0) as DUNGSAI,   \n" + 
						" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN '0'  \n " + 
						" WHEN MHSP.TAISAN_FK IS NOT NULL THEN '1'   \n" + 
						" WHEN MHSP.CCDC_FK IS NOT NULL THEN '2'   \n" + 
						" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN '3'   \n" + 
						" ELSE ''   \n" + 
						" END  AS LOAI ,  \n " + 
						" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ   \n" + 
						" WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ   \n" + 
						" WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ   \n" + 
						" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.PK_SEQ   \n" + 
						" END  AS SPID ,  \n " + 
						" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.MA   \n" + 
						" WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.MA  \n " + 
						" WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.MA  \n " + 
						" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.TEN   \n" + 
						" END  AS MA ,  \n " + 
						" CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.TEN   \n" + 
						" WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.TEN   \n" + 
						" WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.DIENGIAI   \n" + 
						" WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.DIENGIAI   \n" + 
						" END  AS TEN ,  \n " + 
						" MHSP.DONVI, MHSP.SOLUONG -   \n " + 
						"  ISNULL(HD.SOLUONG,0) AS SOLUONGDAT,   \n" + 
						"  ISNULL(HD.SOLUONG,0)  AS SOLUONGDARAHD ,  \n" + 
						" HD_CR.SOLUONG  AS SOLUONGHD ,  \n" + 
						" HD_CR.DONGIA AS  DONGIA ,   \n" + 
						" HD_CR.THANHTIEN AS THANHTIEN ,   \n" + 
						" ISNULL(MH.CHIPHIKHAC,0)as chiphikhac,   \n" + 
						" CASE WHEN MH.LOAI = 0 THEN   \n" + 
						" (SELECT b.NGAYNHAN FROM ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b  \n " + 
						" on a.PK_SEQ = b.SHIPHANG_FK where a.MUAHANG_FK = mh.PK_SEQ and b.SANPHAM_FK = mhsp.SANPHAM_FK AND a.TRANGTHAI = 1 )   \n" + 
						" ELSE ISNULL(MHSP.NGAYNHAN,'') END NGAYNHANDK, isnull(isnull(MHSP.thuexuat,MHSP.VAT),0) thuexuat,   \n" + 
						"   \n" + 
						" HD_CR.TIENVAT  AS TIENVAT    \n" + 
						" FROM ERP_MUAHANG MH  \n " + 
						" INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ   \n" + 
						" LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MHSP.SANPHAM_FK   \n" + 
						" LEFT JOIN   \n" + 
						" (  \n " + 
						" SELECT A.MUAHANG_FK, A.SANPHAM_FK ,A.TAISAN_FK,A.CHIPHI_FK,A.CCDC_FK,A.MHSP_FK  , SUM(SOLUONG) AS SOLUONG  \n " + 
						" FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq   \n" + 
						" INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ   \n" + 
						" WHERE C.trangthai !=4 AND A.HOADONNCC_FK != "+pk_seq+"   \n" + 
						" GROUP BY A.MUAHANG_FK, A.SANPHAM_FK ,A.TAISAN_FK,A.CHIPHI_FK,A.CCDC_FK,A.MHSP_FK   \n " + 
						"   \n" + 
						" )HD ON HD.MUAHANG_FK = MHSP.MUAHANG_FK AND ISNULL(HD.MHSP_FK,0) =ISNULL(MHSP.PK_SEQ,0)  \n" + 
						" LEFT JOIN  \n " + 
						" (   \n" + 
						" SELECT A.MUAHANG_FK, A.SANPHAM_FK ,A.TAISAN_FK,A.CHIPHI_FK,A.CCDC_FK,A.MHSP_FK ,A.DONGIA,A.THANHTIEN,A.TIENVAT  ,  SOLUONG  AS SOLUONG   \n" + 
						" FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq   \n" + 
						" INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ  \n " + 
						" WHERE A.HOADONNCC_FK = "+pk_seq+"   \n" + 
						"   " + 
						" )HD_CR ON HD_CR.MUAHANG_FK = MHSP.MUAHANG_FK AND ISNULL(HD_CR.MHSP_FK,0) =ISNULL(MHSP.PK_SEQ,0)  \n" + 
						"   \n" + 
						" LEFT JOIN ERP_MASCLON TSCD ON TSCD.PK_SEQ = MHSP.TAISAN_FK  \n " + 
						" LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ = MHSP.CCDC_FK   \n" + 
						"  \n " + 
						" LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = MHSP.CHIPHI_FK  \n " + 
						" WHERE (MHSP.SOLUONG  - ISNULL(HD.SOLUONG, 0)) > 0 AND ISNULL(HD_CR.SOLUONG, 0) > 0   \n " + 
						" AND MH.PK_SEQ  IN ("+this.poNkId+")  \n";		
						System.out.println("CAU LAY CT"+ query);
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
							String ngaynhandk = hdsp.getString("NGAYNHANDK");
							String soluongdaraHD = hdsp.getString("SOLUONGDARAHD");
							String sp_vat = hdsp.getString("thuexuat");
							String sp_tienvat = hdsp.getString("TIENVAT");
							IErpHoadonSp sp = new ErpHoadonSp(poId, poTen, sanphamId, sanphamten, soluongdat, dongia, thanhtien, donvi, loai, chon, tiente_fk);
							sp.setSoluonghd(soluonghd);
							sp.setDungsai(dungsai);	
							sp.setSoluongRaHD(soluongdaraHD);
							sp.setNgaynhan(ngaynhandk);
							sp.setSanphamMa(sanphamma);
							sp.setVAT(sp_vat);
							sp.setTienVat(sp_tienvat);
							spList.add(sp);

						}

						hoadon.setPnkList(spList);
						System.out.println("spList.size()"+hoadon.getPnkList().size());
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

	public boolean createPark() 
	{
/*
		if(ktsohoadon_ncc()==true){
			return false;
		}
*/
		String ngaytao = this.getDateTime();

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

		if(this.poNkId.trim().length() <=0 )
		{
			this.MSG = "Vui lòng chọn PO";
			return false;
		}

		// kiem tra cac so PO cung loai

		int demcungloai=0;
		String query="SELECT  MAX(STT) AS SOLUONG  FROM "+
		"\n (SELECT ROW_NUMBER() over ( ORDER BY LOAIHANGHOA_FK,TIENTE_FK ASC)  AS STT , "+
		"\n LOAIHANGHOA_FK, TIENTE_FK FROM ERP_MUAHANG WHERE PK_SEQ IN ("+ this.poNkId +") group by LOAIHANGHOA_FK, TIENTE_FK ) DATA";
		ResultSet rsloai=db.get(query);
		if(rsloai!=null){
			try {
				while(rsloai.next()){
					demcungloai=rsloai.getInt("SOLUONG");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(demcungloai>1){
			this.MSG = "Vui lòng chọn số PO cùng loại ( cùng đơn vị tiền tệ và cùng loại hàng hóa)";
			return false;
		}

		// bat buoc chon kho nhan voi lai sp, dung cu, doi voi TSCD VA CPDV KHONG CHON KHO
		String loaihanghoa="";
		query="\n SELECT LOAIHANGHOA_FK, TIENTE_FK FROM ERP_MUAHANG WHERE PK_SEQ IN ("+ this.poNkId +") group by LOAIHANGHOA_FK, TIENTE_FK";
		rsloai=db.get(query);
		if(rsloai!=null){
			try {
				while(rsloai.next()){
					loaihanghoa=rsloai.getString("LOAIHANGHOA_FK");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if( !loaihanghoa.equals("1") && !loaihanghoa.equals("2"))
		{
			if(this.idKhoNhan.trim().length()<=0)
			{
				this.MSG =  "Vui lòng chọn loại kho nhan";
				return false;
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
					er.printStackTrace();
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

					double slMAX = 0;

					slMAX = Double.parseDouble(spham.getSoluong()) + (Double.parseDouble(spham.getSoluong())*Double.parseDouble(spham.getDungsai())/100);

					System.out.println("SL:"+spham.getSoluong() +", SoluongRaHD: "+spham.getSoluongRaHD());
					if(slMAX - Double.parseDouble(spham.getSoluonghd()) < 0)
					{
						this.MSG = "Số lượng sản phẩm " + spham.getSanphamMa() + " không được vượt quá số lượng đặt (có dung sai). Vui lòng kiểm tra lại ";
						return false;
					}
				}


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

		try 
		{
			db.getConnection().setAutoCommit(false);

			 query = "";


			// LẤY TỈ GIÁ
			// THUE NHAP KHAU = 1 >>  TIGIA lấy lúc nhập vào
			// CÒN LẠI >> LẤY NHƯ DƯỚI

			if(this.ttId.equals("100000") )
			{
				this.tigia = "1";
			}
			System.out.println("b.tiente "+this.ttId);

			// Lưu NCC thay thế (nếu là NCC thay thế mới)
			if(this.nccThayTheId.trim().length() <= 0)
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

				ResultSet rs = db.get(query);						
				if(rs.next())
				{
					this.nccThayTheId = rs.getString("nccTTId");
					rs.close();
				}
			}


			query = " insert ERP_PARK( NCCTHAYTHE_FK ,NGAYGHINHAN, NCC_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, PO_NHAPKHAU, TIENTE_FK, TIGIA, LOAIHD, NPP_FK, nguongochh,DIENGIAI) " +
			" values("+ this.nccThayTheId +", '" + this.NGAYGHINHAN + "', '" + this.NCCID + "', '" + ngaytao + "', '" + ngaytao + "', '" + this.USERID + "', '" + this.USERID + "', '0', " +
			" '"+this.CtyId+"', '" + this.poNkId + "', " + this.ttId + ", " + this.tigia + ", "+this.loaidonmhId+", "+this.nppdangnhap+", '"+this.nguongochh+"',N'"+this.dienGiaiCT+"' )";
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


			query = "delete ERP_HOADONNCC where park_fk = '" + parkCurrent + "' ";
			System.out.println(query);
			if(!db.update(query))
			{
				this.MSG = "3.Không cập nhật Park lỗi : " + query;
				db.getConnection().rollback();
				return false;
			}

			System.out.println("this.hdList.size():"+this.hdList.size());



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


					query = "insert ERP_HOADONNCC(mauhoadon, mausohoadon, kyhieu, sohoadon, ngayhoadon ,sotienAVAT,sotienchietkhau,vat,sotienBVAT,thuesuat,park_fk, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, NGAYDENHANTT, TINHTHUENHAPKHAU,CONGTY_FK, NPP_FK, LOAIHD, nguongochh, KHOTONTRU_FK) " +
					"values('" + mauhoadon.toUpperCase() + "', '" + hd.getMausohoadon().toUpperCase() + "', '" + hd.getKyhieu().toUpperCase() + "', '" + hd.getSohoadon() + "', '" + hd.getNgayhoadon()+ "', '" + hd.getSotienavat() +"'," +
					" '"+hd.getChieckhau()+ "',  '" + hd.getVAT() +"', '"+ hd.getSotienbvat()+"', '"+ hd.getThuesuat()+"', '"+ parkCurrent +"', '"+ ngaytao + "', '" + ngaytao + "', '" + this.USERID + "'," +
					" '" + this.USERID + "', '0', '"+ hd.getNgaydenhanTT() +"', "+TinhThueNhapKhau+", "+this.CtyId+", "+this.nppdangnhap+", "+this.loaidonmhId+", '"+this.nguongochh+"','"+ this.idKhoNhan+"')";
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
						/*if(Double.parseDouble(sp.getSoluonghd())>0)
							{*/

						query = "SELECT LOAIHANGHOA_FK FROM ERP_MUAHANG WHERE PK_SEQ = "+sp.getPoId();
						ResultSet loaihhrs = db.get(query);	
						String loaihh = "";
						if(loaihhrs.next())
						{
							loaihh = loaihhrs.getString("LOAIHANGHOA_FK");
							loaihhrs.close();
						}
						double tigia = Double.parseDouble(this.tigia);
						double soluong = Double.parseDouble(sp.getSoluonghd().replaceAll(",", ""));
						double dongia = Double.parseDouble(sp.getDongia().replaceAll(",", ""));
						double dongiaviet = dongia * tigia;
						double thanhtienviet = dongiaviet * soluong;

						if(loaihh.equals("0")) //SẢN PHẨM
						{
							query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,sanpham_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
							"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  SANPHAM_FK='"+sp.getSanphamId()+"'))";
						}

						if(loaihh.equals("1"))// TÀI SẢN CỐ ĐỊNH
						{
							query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,taisan_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
							"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  taisan_fk='"+sp.getSanphamId()+"'))";
						}

						if(loaihh.equals("2"))// CHI PHÍ
						{
							query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,chiphi_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
							"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  chiphi_fk='"+sp.getSanphamId()+"'))";
						}

						if(loaihh.equals("3"))// CÔNG CỤ DỤNG CỤ
						{
							query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,ccdc_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
							"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  ccdc_fk='"+sp.getSanphamId()+"'))";
						}

						System.out.println("  Insert PN: " + query);
						if(!db.update(query))
						{
							this.MSG = "Không thể tạo mới ERP_HOADONNCC_DONMUAHANG : " + query;
							db.getConnection().rollback();
							return false;
						}
						/*}*/

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
			this.MSG = "Lỗi : "+ e.toString();
			e.printStackTrace();
			return false;
		}
	}


	public boolean updatePark() 
	{
		//System.out.println("vao ham update");
		//		String ngaytao = this.getDateTime();

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

		if(this.poNkId.trim().length() <=0 )
		{
			this.MSG = "Vui lòng chọn PO";
			return false;
		}

		// kiem tra cac so PO cung loai

		int demcungloai=0;
		String query="SELECT  MAX(STT) AS SOLUONG  FROM "+
		"\n (SELECT ROW_NUMBER() over ( ORDER BY LOAIHANGHOA_FK,TIENTE_FK ASC)  AS STT , "+
		"\n LOAIHANGHOA_FK, TIENTE_FK FROM ERP_MUAHANG WHERE PK_SEQ IN ("+ this.poNkId +") group by LOAIHANGHOA_FK, TIENTE_FK ) DATA";
	    System.out.println(" kiem tra loai p0: "+query);
		ResultSet rsloai=db.get(query);
		if(rsloai!=null){
			try {
				while(rsloai.next()){
					demcungloai=rsloai.getInt("SOLUONG");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		if(demcungloai>1){
			this.MSG = "Vui lòng chọn số PO cùng loại ( cùng đơn vị tiền tệ và cùng loại hàng hóa)";
			return false;
		}

		// bat buoc chon kho nhan voi lai sp, dung cu, doi voi TSCD VA CPDV KHONG CHON KHO
		String loaihanghoa="";
		query="\n SELECT LOAIHANGHOA_FK, TIENTE_FK FROM ERP_MUAHANG WHERE PK_SEQ IN ("+ this.poNkId +") group by LOAIHANGHOA_FK, TIENTE_FK";
		rsloai=db.get(query);
		if(rsloai!=null){
			try {
				while(rsloai.next()){
					loaihanghoa=rsloai.getString("LOAIHANGHOA_FK");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		if( !loaihanghoa.equals("1") && !loaihanghoa.equals("2"))
		{
			if(this.idKhoNhan.trim().length()<=0)
			{
				this.MSG =  "Vui lòng chọn loại kho nhan";
				return false;
			}
		}




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
					er.printStackTrace();
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

				/*List<IErpHoadonSp> pnkdetailList = hd.getPnkList();				

					for(int j = 0; j < pnkdetailList.size(); j++)
					{						
						IErpHoadonSp spham = pnkdetailList.get(j);

						double slMAX = Double.parseDouble(spham.getSoluong()) - Double.parseDouble(spham.getSoluongRaHD()) ;
						slMAX = slMAX + (Double.parseDouble(spham.getSoluong())*Double.parseDouble(spham.getDungsai())/100);

						System.out.println("SL:"+spham.getSoluong() +", SoluongRaHD: "+spham.getSoluongRaHD());

						if(slMAX - Double.parseDouble(spham.getSoluonghd()) < 0)
						{
							this.MSG = "Số lượng sản phẩm " + spham.getSanphamMa() + " không được vượt quá số lượng đặt (có dung sai). Vui lòng kiểm tra lại.  "+"SL:"+spham.getSoluong() +", SoluongRaHD: "+spham.getSoluongRaHD();
							return false;
						}
					}
				 */

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
			query = 	"";
			String hdCurrent = "";

			query = " SELECT MUAHANG_FK, HOADONNCC_FK " +
			" FROM ERP_HOADONNCC_DONMUAHANG " +
			" WHERE MUAHANG_FK IN (" + this.poNkId + ") " +
			" AND HOADONNCC_FK IN (SELECT PK_SEQ FROM ERP_HOADONNCC WHERE PARK_FK = " + this.ID + ") ";

			System.out.println(query);
			ResultSet rs = this.db.get(query);
			if(rs != null){
				while(rs.next()){					
					hdCurrent = rs.getString("HOADONNCC_FK") ;
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
			" TIENTE_FK = " + this.ttId + ", TIGIA =  " + tigia + ", nguongochh = '"+this.nguongochh+"' " + "," +
			" DIENGIAI = N'"+this.dienGiaiCT+"'"+
			" WHERE PK_SEQ='"+this.ID+"' and trangthai=0";
			System.out.println(query);

			if(db.updateReturnInt(query)!=1)
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

					query = " UPDATE ERP_HOADONNCC set mauhoadon = '" + mauhoadon.toUpperCase() + "', mausohoadon = '" + hd.getMausohoadon().toUpperCase() + "', kyhieu = '" + hd.getKyhieu().toUpperCase() + "', "+
					" sohoadon = '" + hd.getSohoadon() + "', ngayhoadon = '" + hd.getNgayhoadon()+ "', sotienAVAT = '" + hd.getSotienavat() +"', sotienchietkhau =  '"+hd.getChieckhau()+ "' ,"+
					" vat = '" + hd.getVAT() +"', sotienBVAT =  '"+ hd.getSotienbvat()+"', thuesuat = '"+ hd.getThuesuat()+"', CONGTY_FK =  "+this.CtyId+" , NPP_FK = "+this.nppdangnhap+", LOAIHD = "+this.loaidonmhId+", "+
					" nguongochh = '"+this.nguongochh+"',  KHOTONTRU_FK = '"+this.idKhoNhan +"'  where park_fk = "+this.ID;

					/*query = "insert ERP_HOADONNCC(mauhoadon, mausohoadon, kyhieu, sohoadon, ngayhoadon ,sotienAVAT,sotienchietkhau,vat,sotienBVAT,thuesuat,park_fk, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, NGAYDENHANTT, TINHTHUENHAPKHAU,CONGTY_FK, NPP_FK, LOAIHD) " +
								"values('" + mauhoadon.toUpperCase() + "', '" + hd.getMausohoadon().toUpperCase() + "', '" + hd.getKyhieu().toUpperCase() + "', '" + hd.getSohoadon() + "', '" + hd.getNgayhoadon()+ "', '" + hd.getSotienavat() +"'," +
										" '"+hd.getChieckhau()+ "',  '" + hd.getVAT() +"', '"+ hd.getSotienbvat()+"', '"+ hd.getThuesuat()+"', '"+ this.ID +"', '"+ ngaytao + "', '" + ngaytao + "', '" + this.USERID + "'," +
										" '" + this.USERID + "', '0', '"+ hd.getNgaydenhanTT() +"', "+TinhThueNhapKhau+", "+this.CtyId+", "+this.nppdangnhap+", "+this.loaidonmhId+")";*/
					System.out.println(query);

					if(!db.update(query))//Luu vào bảng ERP_HOADONNCC
					{
						this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
						db.getConnection().rollback();
						return false;
					}

					List<IErpHoadonSp> pnkdetailList = hd.getPnkList();//Moi hoa don tuong ung ta luu vao ERP_HOADONNCC_DONMUAHANG
					System.out.println("pnkdetailList.size():"+pnkdetailList.size());


					if(this.loaihd.equals("0"))// HÓA ĐƠN NHẬP KHẨU
					{
						for(int j = 0; j < pnkdetailList.size(); j++)
						{									
							IErpHoadonSp sp = pnkdetailList.get(j);
							/*if(Double.parseDouble(sp.getSoluonghd())>0)
								{*/

							query = "insert ERP_HOADONNCC_DONMUAHANG(MHSP_FK,hoadonncc_fk, muahang_fk, sanpham_fk, dvt, soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, SOLO) " +
							"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+", '"+sp.getSolo()+"')";

							System.out.println("  Insert PN: " + query);
							if(!db.update(query))
							{
								this.MSG = "Không thể tạo mới ERP_HOADONNCC_DONMUAHANG : " + query;
								db.getConnection().rollback();
								return false;
							}									
							/*}*/

						}
					} 
					else
					{
						for(int j = 0; j < pnkdetailList.size(); j++)
						{	

							IErpHoadonSp sp = pnkdetailList.get(j);


							System.out.println("Size 00 "+sp.getSoluonghd().replaceAll(",", ""));

							/*if(Double.parseDouble(sp.getSoluonghd().replaceAll(",", ""))>0)
								{*/	
							query = "SELECT LOAIHANGHOA_FK FROM ERP_MUAHANG WHERE PK_SEQ = "+sp.getPoId();
							System.out.println("Câu lấy Loai hh"+ query);						
							ResultSet loaihhrs = db.get(query);	
							String loaihh = "";
							if(loaihhrs.next())
							{
								loaihh = loaihhrs.getString("LOAIHANGHOA_FK");
								loaihhrs.close();
							}
							double tigia = Double.parseDouble(this.tigia);
							double soluong = Double.parseDouble(sp.getSoluonghd().replaceAll(",", ""));
							double dongia = Double.parseDouble(sp.getDongia().replaceAll(",", ""));
							double dongiaviet = dongia * tigia;
							double thanhtienviet = dongiaviet * soluong;
							
							if(loaihh.equals("0")) //SẢN PHẨM
							{
								query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,sanpham_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
								"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  SANPHAM_FK='"+sp.getSanphamId()+"'))";
							}

							if(loaihh.equals("1"))// TÀI SẢN CỐ ĐỊNH
							{
								query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,taisan_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
								"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  taisan_fk='"+sp.getSanphamId()+"'))";
							}

							if(loaihh.equals("2"))// CHI PHÍ
							{
								query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,chiphi_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
								"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  chiphi_fk='"+sp.getSanphamId()+"'))";
							}

							if(loaihh.equals("3"))// CÔNG CỤ DỤNG CỤ
							{
								query = "insert ERP_HOADONNCC_DONMUAHANG(mhsp_fk,hoadonncc_fk, muahang_fk,ccdc_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, dongiaviet, thanhtienviet,is_khongthue) " +
								"values("+sp.getMHSP_FK()+",'" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT().replaceAll(",", "")+", "+sp.getTienVat().replaceAll(",", "")+", "+dongiaviet+", "+thanhtienviet+",(select is_khongthue from ERP_MUAHANG_SP where MUAHANG_FK='"+sp.getPoId()+"' and  ccdc_fk='"+sp.getSanphamId()+"'))";
							}

							System.out.println("  Insert PN: " + query);
							if(!db.update(query))
							{
								this.MSG = "Không thể tạo mới ERP_HOADONNCC_DONMUAHANG : " + query;
								db.getConnection().rollback();
								return false;
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


	public boolean updatePark_Duyet() 
	{					
		try 
		{
			db.getConnection().setAutoCommit(false);

			String query = "";
			//////CHI CAP NHAT KY HIEU VA SO HOA DON
			if(this.hdList.size() > 0)
			{
				for(int i = 0; i < this.hdList.size(); i++)
				{
					IErpHoadon hd = hdList.get(i);

					query = "update ERP_HOADONNCC set SOHOADON = "+hd.getSohoadon() +" WHERE PARK_FK = "+this.ID;

					System.out.println(query);

					if(!db.update(query))//Luu vào bảng ERP_HOADONNCC
					{
						this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
						db.getConnection().rollback();
						return false;
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

	private   String Revert_KeToan_loaichungtu(Idbutils db,String loaichungtu,
			String sochungtu ) {

		try 
		{
			
		//GHI NHAN NGUOC LAI TAI KHOAN NO - CO
		String query =  "  delete ERP_PHATSINHKETOAN " +
					    "  where LOAICHUNGTU = N'" + loaichungtu.trim() + "' and SOCHUNGTU = '" + sochungtu + "'  ";
	 
			 
			if(!db.update(query))
			{
				return "Không thể hủy ERP_PHATSINHKETOAN " + query;
				 
			}			
			return "";
			
		} 
		catch (Exception e) {
			e.printStackTrace();
			return e.getMessage();
			
		}
	  
	}
	
	
	public boolean Duyet()
	{
		Utility util = new Utility();

		try 
		{
			db.getConnection().setAutoCommit(false);
			
			
			String msg1=Revert_KeToan_loaichungtu(db,"Duyệt hóa đơn NCC",this.ID);
			
			if(msg1.length()>0){

				this.MSG =msg1;
				db.getConnection().rollback();
				return false;
			}
			
			String query = "";
			
			// LOAIHD = 0: NHẬP KHẨU
			// LOAIHD = 1: TRONG NƯỚC
			// LOAIHD = 2: DỊCH VỤ
			int loaihd = 0;
			query = " select LOAIHD from ERP_PARK WHERE PK_SEQ = '" + this.ID + "'  ";

			ResultSet rsloaihd = db.get(query);

			if(rsloaihd!=null)
			{
				while(rsloaihd.next())
				{
					loaihd = rsloaihd.getInt("LOAIHD");
				}
				rsloaihd.close();
			}

			if(loaihd == 2)// HÓA ĐƠN MUA CPDV
			{
				query = " select HD.NGUONGOCHH, HD.park_fk PK_SEQ, HDSP.TAISAN_FK, HDSP.CCDC_FK, HDSP.CHIPHI_FK, HDSP.SANPHAM_FK, HDSP.SOLUONG, HDSP.DONGIA, \n"+
				" PARK.TIGIA, PARK.TIENTE_FK, PARK.ngayghinhan, PARK.ncc_fk, \n"+
				" (SELECT TAIKHOAN_FK from ERP_MASCLON A WHERE A.PK_SEQ = HDSP.TAISAN_FK ) TAIKHOANNO_SC, \n"+
				" (SELECT distinct C.PK_SEQ from ERP_SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.pk_seq \n"+
				"  INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOANKT_FK = C.SOHIEUTAIKHOAN \n"+
				"  WHERE A.PK_SEQ = HDSP.SANPHAM_FK AND C.CONGTY_FK = "+this.CtyId+") TAIKHOANNO_SANPHAM, HD.SOHOADON, NCC.TAIKHOAN_FK TAIKHOAN_NCC, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE KT.SOHIEUTAIKHOAN = CP.TAIKHOAN_FK AND KT.CONGTY_FK = "+this.CtyId+" and npp_fk = 1) TAIKHOANNO_CHIPHI, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '13311000' AND CONGTY_FK = "+this.CtyId+" and npp_fk = 1) TAIKHOAN_THUE, round(HDSP.TIENVAT,0) as TIENVAT , \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15120000' AND CONGTY_FK = "+this.CtyId+" and npp_fk = 1) TAIKHOAN_15120000, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15110000' AND CONGTY_FK = "+this.CtyId+" and npp_fk = 1) TAIKHOAN_15110000 \n" +
				"	, ROUND(HDSP.thanhTien,0) AS  THANHTIEN , HDSP.thanhTienViet, HDSP.thanhTienVATViet\n" +
				"	, isNull(PARK.dienGiai, '') as dienGiai\n"+
				" from  \n" +
				" ERP_HOADONNCC_DONMUAHANG HDSP INNER JOIN ERP_HOADONNCC HD ON HDSP.HOADONNCC_FK = HD.pk_seq \n"+
				" INNER JOIN ERP_PARK PARK ON HD.park_fk = PARK.pk_seq \n"+
				" INNER JOIN ERP_NHACUNGCAP NCC ON PARK.NCC_FK = NCC.PK_SEQ  \n"+
				" LEFT JOIN ERP_NHOMCHIPHI CP ON HDSP.CHIPHI_FK = CP.PK_SEQ \n"+
				" WHERE PARK.PK_SEQ = "+this.ID+" \n";

				System.out.println("hoa don mua cpdv:\n"  + query + "\n-------------------------------------------------------------");
				ResultSet rsnk = db.get(query);

				if(rsnk!=null)
				{
					while(rsnk.next())
					{
						String dienGiai = rsnk.getString("dienGiai");
						double soluong = 0;
						double dongia_VND =  0;   
						double dongia_NT =  0;  

						double sotienBVAT_VND = 0;
						double sotienBVAT_NT = 0;

						double sotienVAT_VND = 0;
						double sotienVAT_NT = 0;

						String namNV = rsnk.getString("ngayghinhan").substring(0, 4);
						String thangNV = rsnk.getString("ngayghinhan").substring(5, 7);

						String ncc_fk = rsnk.getString("ncc_fk") == null?"":rsnk.getString("ncc_fk") ;
						String TAIKHOAN_CO = "";
						String TAIKHOAN_NO = "";
						String TAIKHOANNO_SC = rsnk.getString("TAIKHOANNO_SC") == null?"":rsnk.getString("TAIKHOANNO_SC") ;
						String TAIKHOANNO_CHIPHI = rsnk.getString("TAIKHOANNO_CHIPHI") == null?"":rsnk.getString("TAIKHOANNO_CHIPHI") ;
						//			        			String TAIKHOANNO_SANPHAM = rsnk.getString("TAIKHOANNO_SANPHAM") == null?"":rsnk.getString("TAIKHOANNO_SANPHAM") ;

						String TAIKHOAN_THUE = rsnk.getString("TAIKHOAN_THUE") == null?"":rsnk.getString("TAIKHOAN_THUE") ;			        			
						String TAIKHOAN_NCC = rsnk.getString("TAIKHOAN_NCC") == null?"":rsnk.getString("TAIKHOAN_NCC") ;
						String TAIKHOAN_15120000 = rsnk.getString("TAIKHOAN_15120000") == null?"":rsnk.getString("TAIKHOAN_15120000") ;
						String TAIKHOAN_15110000 = rsnk.getString("TAIKHOAN_15110000") == null?"":rsnk.getString("TAIKHOAN_15110000") ;

						String sanpham_fk =  rsnk.getString("SANPHAM_FK") == null ? "":rsnk.getString("sanpham_fk")  ;
						String taisan_fk = rsnk.getString("TAISAN_FK") == null ? "":rsnk.getString("TAISAN_FK")  ;
						String chiphi_fk = rsnk.getString("CHIPHI_FK") == null ? "":rsnk.getString("CHIPHI_FK")  ;

						String tiente_fk = rsnk.getString("tiente_fk");
						String sohoadon = rsnk.getString("sohoadon");

						String nguongochh = rsnk.getString("NGUONGOCHH") == null ? "":rsnk.getString("NGUONGOCHH")  ;

						double tygia = (rsnk.getDouble("tigia"));

						soluong = rsnk.getDouble("SOLUONG");

						if(tiente_fk.equals("100000")) // TIỀN VIỆT
						{
							tygia = 1;
							dongia_VND = rsnk.getDouble("DONGIA");
							dongia_NT  = 0;

							sotienBVAT_VND = rsnk.getDouble("thanhTien");
							sotienBVAT_NT = rsnk.getDouble("thanhTien");

							sotienVAT_VND =  rsnk.getDouble("TIENVAT")  ;
							sotienVAT_NT = rsnk.getDouble("TIENVAT");
						}
						else
						{
							dongia_VND = rsnk.getDouble("DONGIA")*tygia;
							dongia_NT = rsnk.getDouble("DONGIA");

							sotienBVAT_VND = rsnk.getDouble("thanhTienViet");
							sotienBVAT_NT = rsnk.getDouble("thanhTien");

							sotienVAT_VND = (rsnk.getDouble("thanhTienVATViet"));
							sotienVAT_NT = rsnk.getDouble("TIENVAT");
						}

						String doituongno = "";
						String madoituongno = "";
						String doituongco = "";
						String madoituongco = "";

						if(nguongochh.equals("TN")) 
						{			        				
							if(chiphi_fk.trim().length() > 0)
							{				                		
								if(sotienBVAT_VND > 0) // TIỀN HÀNG
								{
									doituongno = "Chi phí";
									madoituongno = chiphi_fk;

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOANNO_CHIPHI;
									TAIKHOAN_CO = TAIKHOAN_NCC;

									if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
									{
										this.MSG = "D1.1 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
										rsnk.close();
										db.getConnection().rollback();
										return false;

									}

									this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
											Double.toString(sotienBVAT_VND), Double.toString(sotienBVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND), 
											Double.toString(sotienBVAT_NT), "Hóa đơn - Tiền hàng", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

									if(this.MSG.trim().length() > 0)
									{
										this.MSG = "D1.2 " + this.MSG;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}
								}	

								if(sotienVAT_VND > 0) // TIỀN THUẾ
								{
									doituongno = "";
									madoituongno = "";

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOAN_THUE;
									TAIKHOAN_CO = TAIKHOAN_NCC;

									if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
									{
										this.MSG = "D1.3 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
										rsnk.close();
										db.getConnection().rollback();
										return false;

									}

									this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
											Double.toString(sotienVAT_VND), Double.toString(sotienVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienVAT_VND), 
											Double.toString(sotienVAT_NT), "Hóa đơn - Tiền thuế", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

									if(this.MSG.trim().length() > 0)
									{
										this.MSG = "D1.4 " + this.MSG;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}
								}
							}

							if(taisan_fk.trim().length()>0)
							{				                		
								if(sotienBVAT_VND > 0) // TIỀN HÀNG
								{
									//Thêm điều chỉnh cho mã sửa chữa lớn
									String result = Erp_MaSCLon.InsertDieuChinhMSCL
									(db, taisan_fk, rsnk.getString("ngayghinhan"), Double.toString(sotienBVAT_VND)
											, this.ID, "Duyệt hóa đơn nhà cung cấp", "ERP_PARK");

									if (result.trim().length() > 0)
									{
										this.MSG = "D1.5.1 Không thể duyệt hóa đơn \n" + result;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}

									doituongno = "SC/XDCB TSCD";
									madoituongno = taisan_fk;

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOANNO_SC;
									TAIKHOAN_CO = TAIKHOAN_NCC;

									if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
									{
										this.MSG = "D1.6 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
										rsnk.close();
										db.getConnection().rollback();
										return false;

									}

									this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
											Double.toString(sotienBVAT_VND), Double.toString(sotienBVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND), 
											Double.toString(sotienBVAT_NT), "Hóa đơn - Tiền hàng", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

									if(this.MSG.trim().length() > 0)
									{
										this.MSG = "D1.7 " + this.MSG;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}

								}	

								if(sotienVAT_VND > 0) // TIỀN VAT
								{
									doituongno = "";
									madoituongno = "";

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOAN_THUE;
									TAIKHOAN_CO = TAIKHOAN_NCC;

									if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
									{
										this.MSG = "D1.8 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
										rsnk.close();
										db.getConnection().rollback();
										return false;

									}

									this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
											Double.toString(sotienVAT_VND), Double.toString(sotienVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienVAT_VND), 
											Double.toString(sotienVAT_NT), "Hóa đơn - Tiền thuế", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

									if(this.MSG.trim().length() > 0)
									{
										this.MSG = "D1.9 " + this.MSG;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}

								}
							}

							if(sanpham_fk.trim().length()>0)
							{				                		
								if(sotienBVAT_VND > 0)
								{
									doituongno = "Sản phẩm";
									madoituongno = sanpham_fk;

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOAN_15110000;
									TAIKHOAN_CO = TAIKHOAN_NCC;

									if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
									{
										this.MSG = "D1.9 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
										rsnk.close();
										db.getConnection().rollback();
										return false;

									}

									this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
											Double.toString(sotienBVAT_VND), Double.toString(sotienBVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND), 
											Double.toString(sotienBVAT_NT), "Hóa đơn - Tiền hàng", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

									if(this.MSG.trim().length() > 0)
									{
										this.MSG = "D1.10 " + this.MSG;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}

								}	

								if(sotienVAT_VND > 0)
								{
									doituongno = "";
									madoituongno = "";

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOAN_THUE;
									TAIKHOAN_CO = TAIKHOAN_NCC;


									if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
									{
										this.MSG = "D1.11 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
										rsnk.close();
										db.getConnection().rollback();
										return false;

									}

									this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
											Double.toString(sotienVAT_VND), Double.toString(sotienVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienVAT_VND), 
											Double.toString(sotienVAT_NT), "Hóa đơn - Tiền thuế", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

									if(this.MSG.trim().length() > 0)
									{
										this.MSG = "D1.12 " + this.MSG;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}

								}
							}			        				

						}
						else if (nguongochh.equals("NN"))
						{
							if(taisan_fk.trim().length()>0)
							{				                		
								if(sotienBVAT_VND > 0)
								{
									doituongno = "SC/XDCB TSCD";
									madoituongno = taisan_fk;

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOANNO_SC;
									TAIKHOAN_CO = TAIKHOAN_NCC;

								}	

							}

							if(sanpham_fk.trim().length()>0)
							{				                		
								if(sotienBVAT_VND > 0)
								{
									doituongno = "Sản phẩm";
									madoituongno = sanpham_fk;

									doituongco = "Nhà cung cấp";
									madoituongco = ncc_fk;

									TAIKHOAN_NO = TAIKHOAN_15120000;
									TAIKHOAN_CO = TAIKHOAN_NCC;

								}	
							}

							// KIỂM TRA TIỀN HÀNG
							if(sotienBVAT_VND > 0)
							{
								if(taisan_fk.trim().length()>0)
								{
									//Thêm điều chỉnh cho mã sửa chữa lớn
									String result = Erp_MaSCLon.InsertDieuChinhMSCL
									(db, taisan_fk, rsnk.getString("ngayghinhan"), Double.toString(sotienBVAT_VND)
											, this.ID, "Duyệt hóa đơn nhà cung cấp", "ERP_PARK");

									if (result.trim().length() > 0)
									{
										this.MSG = "D1.5.1 Không thể duyệt hóa đơn \n" + result;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}
								}
								if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
								{
									this.MSG = "D1.13 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rsnk.close();
									db.getConnection().rollback();
									return false;

								}

								this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(sotienBVAT_VND), Double.toString(sotienBVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND), 
										Double.toString(sotienBVAT_NT), "Hóa đơn - Tiền hàng", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

								if(this.MSG.trim().length() > 0)
								{
									this.MSG = "D1.14 " + this.MSG;
									rsnk.close();
									db.getConnection().rollback();
									return false;
								}

							}
						}

					}
					rsnk.close();
				}

			}			
			else if(loaihd==0) // HÓA ĐƠN NHẬP KHẨU
			{
				query = " select HD.NGUONGOCHH, HD.park_fk PK_SEQ, HDSP.TAISAN_FK, HDSP.CCDC_FK, HDSP.CHIPHI_FK, HDSP.SANPHAM_FK, HDSP.SOLUONG, HDSP.DONGIA, \n"+
				" PARK.TIGIA, PARK.TIENTE_FK, PARK.ngayghinhan, PARK.ncc_fk, \n"+
				" (SELECT TAIKHOAN_FK from ERP_MASCLON A WHERE A.PK_SEQ = HDSP.TAISAN_FK ) TAIKHOANNO_TAISAN, \n"+
				" (SELECT distinct C.PK_SEQ from ERP_SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.pk_seq \n"+
				"  INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOANKT_FK = C.SOHIEUTAIKHOAN \n"+
				"  WHERE A.PK_SEQ = HDSP.SANPHAM_FK AND C.CONGTY_FK = "+this.CtyId+") TAIKHOANNO_SANPHAM, HD.SOHOADON, NCC.TAIKHOAN_FK TAIKHOAN_NCC, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE KT.SOHIEUTAIKHOAN = CP.TAIKHOAN_FK AND KT.CONGTY_FK = "+this.CtyId+") TAIKHOANNO_CHIPHI, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '13311000' AND CONGTY_FK = "+this.CtyId+" and npp_fk = 1 and trangThai = 1) TAIKHOAN_THUE, HDSP.TIENVAT, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15120000' AND CONGTY_FK = "+this.CtyId+" and npp_fk = 1 and trangThai = 1) TAIKHOAN_15120000, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15110000' AND CONGTY_FK = "+this.CtyId+" and npp_fk = 1 and trangThai = 1) TAIKHOAN_15110000 \n" +
				"	, HDSP.thanhTien, HDSP.thanhTienViet, HDSP.thanhTienVATViet\n" +
				"	, isNull(park.DIENGIAI,'') as DIENGIAI\n"+
				" from  \n"+
				" ERP_HOADONNCC_DONMUAHANG HDSP INNER JOIN ERP_HOADONNCC HD ON HDSP.HOADONNCC_FK = HD.pk_seq \n"+
				" INNER JOIN ERP_PARK PARK ON HD.park_fk = PARK.pk_seq \n"+
				" INNER JOIN ERP_NHACUNGCAP NCC ON PARK.NCC_FK = NCC.PK_SEQ  \n"+
				" LEFT JOIN ERP_NHOMCHIPHI CP ON HDSP.CHIPHI_FK = CP.PK_SEQ \n"+
				" WHERE PARK.PK_SEQ = "+this.ID+" \n";
				System.out.println(query);
				ResultSet rsnk = db.get(query);

				if(rsnk!=null)
				{
					while(rsnk.next())
					{
						String dienGiai = rsnk.getString("dienGiai");
						double soluong = 0;
						double dongia_VND =  0;   
						double dongia_NT =  0;  

						double sotienBVAT_VND = 0;
						double sotienBVAT_NT = 0;

						//	        		    double sotienVAT_VND = 0;
						//	        		    double sotienVAT_NT = 0;

						String namNV = rsnk.getString("ngayghinhan").substring(0, 4);
						String thangNV = rsnk.getString("ngayghinhan").substring(5, 7);

						String ncc_fk = rsnk.getString("ncc_fk") == null?"":rsnk.getString("ncc_fk") ;
						String TAIKHOAN_CO = "";
						String TAIKHOAN_NO = "";
						//	        			String TAIKHOANNO_TAISAN = rsnk.getString("TAIKHOANNO_TAISAN") == null?"":rsnk.getString("TAIKHOANNO_TAISAN") ;
						//	        			String TAIKHOANNO_CHIPHI = rsnk.getString("TAIKHOANNO_CHIPHI") == null?"":rsnk.getString("TAIKHOANNO_CHIPHI") ;
						//	        			String TAIKHOANNO_SANPHAM = rsnk.getString("TAIKHOANNO_SANPHAM") == null?"":rsnk.getString("TAIKHOANNO_SANPHAM") ;

						//	        			String TAIKHOAN_THUE = rsnk.getString("TAIKHOAN_THUE") == null?"":rsnk.getString("TAIKHOAN_THUE") ;			        			
						String TAIKHOAN_NCC = rsnk.getString("TAIKHOAN_NCC") == null?"":rsnk.getString("TAIKHOAN_NCC") ;
						String TAIKHOAN_15120000 = rsnk.getString("TAIKHOAN_15120000") == null?"":rsnk.getString("TAIKHOAN_15120000") ;
						//	        			String TAIKHOAN_15110000 = rsnk.getString("TAIKHOAN_15110000") == null?"":rsnk.getString("TAIKHOAN_15110000") ;

						String sanpham_fk =  rsnk.getString("SANPHAM_FK") == null ? "":rsnk.getString("sanpham_fk")  ;
						//	        			String taisan_fk = rsnk.getString("TAISAN_FK") == null ? "":rsnk.getString("TAISAN_FK")  ;
						//	        			String chiphi_fk = rsnk.getString("CHIPHI_FK") == null ? "":rsnk.getString("CHIPHI_FK")  ;

						String tiente_fk = rsnk.getString("tiente_fk");
						String sohoadon = rsnk.getString("sohoadon");

						//	        			String nguongochh = rsnk.getString("NGUONGOCHH") == null ? "":rsnk.getString("NGUONGOCHH")  ;

						double tygia = (rsnk.getDouble("tigia"));

						soluong = rsnk.getDouble("SOLUONG");

						if(tiente_fk.equals("100000")) // TIỀN VIỆT
						{
							tygia = 1;
							dongia_VND = rsnk.getDouble("DONGIA");
							dongia_NT  = 0;

							sotienBVAT_VND = rsnk.getDouble("thanhTien");
							sotienBVAT_NT = rsnk.getDouble("thanhTien");

							//	        				sotienVAT_VND = rsnk.getDouble("TIENVAT");
							//	        				sotienVAT_NT = rsnk.getDouble("TIENVAT");
						}
						else
						{
							dongia_VND = rsnk.getDouble("DONGIA")*tygia;
							dongia_NT = rsnk.getDouble("DONGIA");

							//	        				sotienBVAT_VND = Math.round(soluong*dongia_VND);
							//	        				sotienBVAT_NT = soluong*dongia_NT;
							sotienBVAT_VND = rsnk.getDouble("thanhTienViet");
							sotienBVAT_NT = rsnk.getDouble("thanhTien");

							//	        				sotienVAT_VND = Math.round(rsnk.getDouble("TIENVAT")*tygia);
							//	        				sotienVAT_NT = rsnk.getDouble("TIENVAT");
						}

						String doituongno = "";
						String madoituongno = "";
						String doituongco = "";
						String madoituongco = "";


						if(sanpham_fk.trim().length()>0)
						{				                		
							if(sotienBVAT_VND > 0)
							{
								doituongno = "Sản phẩm";
								madoituongno = sanpham_fk;

								doituongco = "Nhà cung cấp";
								madoituongco = ncc_fk;

								TAIKHOAN_NO = TAIKHOAN_15120000;
								TAIKHOAN_CO = TAIKHOAN_NCC;

							}	

							// KIỂM TRA TIỀN HÀNG
							if(sotienBVAT_VND > 0)
							{
								if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
								{
									this.MSG = "D1.15 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rsnk.close();
									db.getConnection().rollback();
									return false;

								}

								this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(Math.round(sotienBVAT_VND)), Double.toString(Math.round(sotienBVAT_VND)), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(Math.round(sotienBVAT_VND)), 
										Double.toString(Math.round(sotienBVAT_NT)), "Hóa đơn - Tiền hàng", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");



								if(this.MSG.trim().length() > 0)
								{
									this.MSG = "D1.16 " + this.MSG;
									rsnk.close();
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
					rsnk.close();
				}
				
				
				//----------- DINH KHOAN O CHENH LECH : THU 29/9/2017
				// nhập số dương và định khoản: Nợ TK bên dưới NCC /Có TK 71180000
				// nhập số âm và định khoản: Nợ TK 71180000 /Có TK bên dưới NCC 
				query=" select PARK.CONGTY_FK, PARK.NPP_FK ,  HD.NGUONGOCHH, HD.park_fk PK_SEQ,  \n" + 
						  " PARK.TIGIA, PARK.TIENTE_FK, PARK.ngayghinhan, PARK.ncc_fk, HD.SOHOADON,  \n" + 
						  " isNull(park.DIENGIAI,'') as DIENGIAI,ROUND (    ISNULL(HD.CHENHLECHHD,0) * round( PARK.TIGIA,2) ,0) AS TIENCHENHLECH, HD.TIGIA , \n" + 
						  " NCC.TAIKHOAN_FK TAIKHOAN_NCC, \n" + 
						  " (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '71180000' AND CONGTY_FK = '"+ this.CtyId+"' and trangThai = 1 and npp_fk = 1 )  \n" + 
						  " AS TAIKHOAN_71180000 \n" + 
					  " from  \n" + 
						  " ERP_HOADONNCC HD     \n" + 
						  " INNER JOIN ERP_PARK PARK ON HD.park_fk = PARK.pk_seq   \n" + 
						  " INNER JOIN ERP_NHACUNGCAP NCC ON PARK.NCC_FK = NCC.PK_SEQ   \n" + 
					  " WHERE PARK.PK_SEQ = '"+this.ID+"' \n";
				System.out.println( "----------- DINH KHOAN O CHENH LECH :"+ query);
				rsnk = db.get(query);
				while(rsnk.next())
				{
					String dienGiai = "";
					String nppid=rsnk.getString("npp_fk");
					String congtyid=rsnk.getString("congty_fk");
					double TIENCHENHLECH=rsnk.getDouble("TIENCHENHLECH");
					String tiente_fk = rsnk.getString("tiente_fk");
					String sohoadon = rsnk.getString("sohoadon");
					String namNV = rsnk.getString("ngayghinhan").substring(0, 4);
					String thangNV = rsnk.getString("ngayghinhan").substring(5, 7);
					String ncc_fk = rsnk.getString("ncc_fk") == null?"":rsnk.getString("ncc_fk") ;
					String TAIKHOAN_NCC = rsnk.getString("TAIKHOAN_NCC") == null?"":rsnk.getString("TAIKHOAN_NCC") ;
					String TAIKHOAN_71180000=rsnk.getString("TAIKHOAN_71180000") == null?"":rsnk.getString("TAIKHOAN_71180000") ;
					double tygia = (rsnk.getDouble("tigia"));
					double soluong = 0;
					double dongia_VND =  0;   
					double dongia_NT =  0; 
					String TAIKHOAN_CO = "";
					String TAIKHOAN_NO = "";
					soluong = 0;
					tygia = 1;
					dongia_VND = 0;
					dongia_NT  = 0;
					String doituongno = "";
					String madoituongno = "";
					String doituongco = "";
					String madoituongco = "";
					
					if(TIENCHENHLECH !=0){
						if(TIENCHENHLECH >0){
							TAIKHOAN_NO = TAIKHOAN_NCC ;
							TAIKHOAN_CO = TAIKHOAN_71180000;
							doituongno = "Nhà cung cấp";
							madoituongno =ncc_fk;
							doituongco = "Đối tượng khác";
							madoituongco ="" ;
						}else{
							TAIKHOAN_NO =  TAIKHOAN_71180000 ;
							TAIKHOAN_CO = TAIKHOAN_NCC ;
							doituongno = "Đối tượng khác";
							madoituongno ="";
							doituongco = "Nhà cung cấp";
							madoituongco = ncc_fk;
						}
					
						TIENCHENHLECH= Math.abs(TIENCHENHLECH);
						// KIỂM TRA TIỀN HÀNG
						if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
						{
							this.MSG = "D1.17 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
							rsnk.close();
							db.getConnection().rollback();
							return false;
						}
						
						this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH
								( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), 
								"Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
								Double.toString(TIENCHENHLECH), Double.toString(TIENCHENHLECH), doituongno, madoituongno, doituongco, madoituongco, 
								"0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(TIENCHENHLECH), 
								Double.toString(0), "Hóa đơn - Tiền chênh lệch", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");
						if(this.MSG.trim().length() > 0)
						{
							this.MSG = "D1.21 " + this.MSG;
							rsnk.close();
							db.getConnection().rollback();
							return false;
						}
					}
				}
				rsnk.close();
				//----------- DINH KHOAN O CHENH LECH
				
			}
			else if(loaihd == 1)
			{
				query = " select ISNULL(ISGIACONG,'0') AS ISGIACONG ,HD.NGUONGOCHH, HD.park_fk PK_SEQ, HDSP.TAISAN_FK, HDSP.CCDC_FK, HDSP.CHIPHI_FK, HDSP.SANPHAM_FK\n" +
				", HDSP.SOLUONG, HDSP.DONGIA, round(HDSP.THANHTIEN,0) as THANHTIEN , HDSP.donGiaViet, HDSP.thanhTienViet, HDSP.thanhTienVATViet\n"+
				" , PARK.TIGIA, PARK.TIENTE_FK, PARK.ngayghinhan, PARK.ncc_fk, \n"+
				" (SELECT TAIKHOAN_FK from ERP_MASCLON A WHERE A.PK_SEQ = HDSP.TAISAN_FK ) TAIKHOANNO_TAISAN, \n"+
				" (SELECT distinct C.PK_SEQ from ERP_SANPHAM A INNER JOIN ERP_LOAISANPHAM B ON A.LOAISANPHAM_FK = B.pk_seq \n"+
				"  INNER JOIN ERP_TAIKHOANKT C ON B.TAIKHOANKT_FK = C.SOHIEUTAIKHOAN \n"+
				"  WHERE A.PK_SEQ = HDSP.SANPHAM_FK AND C.CONGTY_FK = "+this.CtyId+") TAIKHOANNO_SANPHAM, HD.SOHOADON, NCC.TAIKHOAN_FK TAIKHOAN_NCC, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE KT.SOHIEUTAIKHOAN = CP.TAIKHOAN_FK AND KT.CONGTY_FK = "+this.CtyId+" and trangThai = 1 and npp_fk = 1) TAIKHOANNO_CHIPHI, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '13311000' AND CONGTY_FK = "+this.CtyId+" and trangThai = 1 and npp_fk = 1) TAIKHOAN_THUE, round(HDSP.TIENVAT,0) as TIENVAT , \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15120000' AND CONGTY_FK = "+this.CtyId+" and trangThai = 1 and npp_fk = 1) TAIKHOAN_15120000, \n"+
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15110000' AND CONGTY_FK = "+this.CtyId+" and trangThai = 1 and npp_fk = 1) TAIKHOAN_15110000 , \n" +
				"  (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '15419000' AND CONGTY_FK = "+this.CtyId+" and trangThai = 1 and npp_fk = 1) TAIKHOAN_15419000 \n" +
				"  , isNull(park.dienGiai, '') as dienGiai\n"+
				" from  \n"+
				" ERP_HOADONNCC_DONMUAHANG HDSP INNER JOIN ERP_HOADONNCC HD ON HDSP.HOADONNCC_FK = HD.pk_seq \n"+
				" INNER JOIN ERP_PARK PARK ON HD.park_fk = PARK.pk_seq \n"+
				" INNER JOIN ERP_NHACUNGCAP NCC ON PARK.NCC_FK = NCC.PK_SEQ  \n"+
				" LEFT JOIN ERP_NHOMCHIPHI CP ON HDSP.CHIPHI_FK = CP.PK_SEQ  "
				+ " LEFT JOIN ERP_MUAHANG MH ON MH.PK_SEQ= HDSP.MUAHANG_FK  \n"+
				" WHERE PARK.PK_SEQ = "+this.ID+" \n";

				System.out.println(query);
				ResultSet rsnk = db.get(query);

			 
					while(rsnk.next())
					{
						String dienGiai = rsnk.getString("dienGiai");
						double soluong = 0;
						double dongia_VND =  0;   
						double dongia_NT =  0;  

						double sotienBVAT_VND = 0;
						double sotienBVAT_NT = 0;

						double sotienVAT_VND = 0;
						double sotienVAT_NT = 0;

						String namNV = rsnk.getString("ngayghinhan").substring(0, 4);
						String thangNV = rsnk.getString("ngayghinhan").substring(5, 7);

						String ncc_fk = rsnk.getString("ncc_fk") == null?"":rsnk.getString("ncc_fk") ;
						String TAIKHOAN_CO = "";
						String TAIKHOAN_NO = "";
						//		    			String TAIKHOANNO_TAISAN = rsnk.getString("TAIKHOANNO_TAISAN") == null?"":rsnk.getString("TAIKHOANNO_TAISAN") ;
						//		    			String TAIKHOANNO_CHIPHI = rsnk.getString("TAIKHOANNO_CHIPHI") == null?"":rsnk.getString("TAIKHOANNO_CHIPHI") ;
						//		    			String TAIKHOANNO_SANPHAM = rsnk.getString("TAIKHOANNO_SANPHAM") == null?"":rsnk.getString("TAIKHOANNO_SANPHAM") ;

						String TAIKHOAN_THUE = rsnk.getString("TAIKHOAN_THUE") == null?"":rsnk.getString("TAIKHOAN_THUE") ;			        			
						String TAIKHOAN_NCC = rsnk.getString("TAIKHOAN_NCC") == null?"":rsnk.getString("TAIKHOAN_NCC") ;
						//		    			String TAIKHOAN_15120000 = rsnk.getString("TAIKHOAN_15120000") == null?"":rsnk.getString("TAIKHOAN_15120000") ;
						String TAIKHOAN_15110000 = rsnk.getString("TAIKHOAN_15110000") == null?"":rsnk.getString("TAIKHOAN_15110000") ;
						
						if(rsnk.getString("ISGIACONG").equals("1")){
							// lay tai khoan cua gia cong 
							TAIKHOAN_15110000 = rsnk.getString("TAIKHOAN_15419000") == null?"":rsnk.getString("TAIKHOAN_15419000") ;
						}	
							
						

						String sanpham_fk =  rsnk.getString("SANPHAM_FK") == null ? "":rsnk.getString("sanpham_fk")  ;
						//		    			String taisan_fk = rsnk.getString("TAISAN_FK") == null ? "":rsnk.getString("TAISAN_FK")  ;
						//		    			String chiphi_fk = rsnk.getString("CHIPHI_FK") == null ? "":rsnk.getString("CHIPHI_FK")  ;

						String tiente_fk = rsnk.getString("tiente_fk");
						String sohoadon = rsnk.getString("sohoadon");

						//		    			String nguongochh = rsnk.getString("NGUONGOCHH") == null ? "":rsnk.getString("NGUONGOCHH")  ;

						double tygia = (rsnk.getDouble("tigia"));

						soluong = rsnk.getDouble("SOLUONG");

						if(tiente_fk.equals("100000")) // TIỀN VIỆT
						{
							tygia = 1;
							dongia_VND = rsnk.getDouble("DONGIA");
							dongia_NT  = 0;

							sotienBVAT_VND = rsnk.getDouble("thanhTien");
							sotienBVAT_NT = rsnk.getDouble("thanhTien");

							sotienVAT_VND = rsnk.getDouble("TIENVAT");
							sotienVAT_NT = rsnk.getDouble("TIENVAT");
						}
						else
						{
							dongia_VND = rsnk.getDouble("DONGIA") * tygia;
							dongia_NT = rsnk.getDouble("DONGIA");

							sotienBVAT_VND = rsnk.getDouble("thanhTienViet");
							sotienBVAT_NT = rsnk.getDouble("thanhTien");

							sotienVAT_VND = (rsnk.getDouble("thanhTienVATViet"));
							sotienVAT_NT = rsnk.getDouble("TIENVAT");
						}

						String doituongno = "";
						String madoituongno = "";
						String doituongco = "";
						String madoituongco = "";


						if(sanpham_fk.trim().length()>0)
						{				                		
							if(sotienBVAT_VND > 0)
							{
								doituongno = "Sản phẩm";
								madoituongno = sanpham_fk;

								doituongco = "Nhà cung cấp";
								madoituongco = ncc_fk;

								TAIKHOAN_NO = TAIKHOAN_15110000;
								TAIKHOAN_CO = TAIKHOAN_NCC;

								// KIỂM TRA TIỀN HÀNG
								if(sotienBVAT_VND > 0)
								{
									if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
									{
										this.MSG = "D1.17 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}

									this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
											Double.toString(sotienBVAT_VND), Double.toString(sotienBVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienBVAT_VND), 
											Double.toString(sotienBVAT_NT), "Hóa đơn - Tiền hàng", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

									if(this.MSG.trim().length() > 0)
									{
										this.MSG = "D1.18 " + this.MSG;
										rsnk.close();
										db.getConnection().rollback();
										return false;
									}

								}
							}


							if(sotienVAT_VND > 0)
							{
								doituongno = "";
								madoituongno = "";

								doituongco = "Nhà cung cấp";
								madoituongco = ncc_fk;

								TAIKHOAN_NO = TAIKHOAN_THUE;
								TAIKHOAN_CO = TAIKHOAN_NCC;


								if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
								{
									this.MSG = "D1.19 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
									rsnk.close();
									db.getConnection().rollback();
									return false;

								}

								this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), "Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
										Double.toString(sotienVAT_VND), Double.toString(sotienVAT_VND), doituongno, madoituongno, doituongco, madoituongco, "0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(sotienVAT_VND), 
										Double.toString(sotienVAT_NT), "Hóa đơn - Tiền thuế", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");

								if(this.MSG.trim().length() > 0)
								{
									this.MSG = "D1.20 " + this.MSG;
									rsnk.close();
									db.getConnection().rollback();
									return false;
								}
							}
						}
					}
					rsnk.close();
			 
					//----------- DINH KHOAN O CHENH LECH : THU 29/9/2017
					// nhập số dương và định khoản: Nợ TK bên dưới NCC /Có TK 71180000
					// nhập số âm và định khoản: Nợ TK 71180000 /Có TK bên dưới NCC 
					query=" select PARK.CONGTY_FK, PARK.NPP_FK ,  HD.NGUONGOCHH, HD.park_fk PK_SEQ,  \n" + 
							  " PARK.TIGIA, PARK.TIENTE_FK, PARK.ngayghinhan, PARK.ncc_fk, HD.SOHOADON,  \n" + 
							  " isNull(park.DIENGIAI,'') as DIENGIAI,ISNULL(HD.CHENHLECHHD,0) * PARK.TIGIA AS TIENCHENHLECH, HD.TIGIA , \n" + 
							  " NCC.TAIKHOAN_FK TAIKHOAN_NCC, \n" + 
							  " (SELECT PK_SEQ FROM ERP_TAIKHOANKT KT WHERE SOHIEUTAIKHOAN = '71180000' AND CONGTY_FK = '"+ this.CtyId+"' and trangThai = 1 and npp_fk = 1 )  \n" + 
							  " AS TAIKHOAN_71180000  \n" + 
						  " from  \n" + 
							  " ERP_HOADONNCC HD     \n" + 
							  " INNER JOIN ERP_PARK PARK ON HD.park_fk = PARK.pk_seq   \n" + 
							  " INNER JOIN ERP_NHACUNGCAP NCC ON PARK.NCC_FK = NCC.PK_SEQ   \n" + 
						  " WHERE PARK.PK_SEQ = '"+this.ID+"' \n";
					System.out.println( "----------- DINH KHOAN O CHENH LECH :"+ query);
					rsnk = db.get(query);
					while(rsnk.next())
					{
						String dienGiai = "";
						String nppid=rsnk.getString("npp_fk");
						String congtyid=rsnk.getString("congty_fk");
						double TIENCHENHLECH=rsnk.getDouble("TIENCHENHLECH");
						String tiente_fk = rsnk.getString("tiente_fk");
						String sohoadon = rsnk.getString("sohoadon");
						String namNV = rsnk.getString("ngayghinhan").substring(0, 4);
						String thangNV = rsnk.getString("ngayghinhan").substring(5, 7);
						String ncc_fk = rsnk.getString("ncc_fk") == null?"":rsnk.getString("ncc_fk") ;
						String TAIKHOAN_NCC = rsnk.getString("TAIKHOAN_NCC") == null?"":rsnk.getString("TAIKHOAN_NCC") ;
						String TAIKHOAN_71180000=rsnk.getString("TAIKHOAN_71180000") == null?"":rsnk.getString("TAIKHOAN_71180000") ;
						double tygia = (rsnk.getDouble("tigia"));
						double soluong = 0;
						double dongia_VND =  0;   
						double dongia_NT =  0; 
						String TAIKHOAN_CO = "";
						String TAIKHOAN_NO = "";
						soluong = 0;
						tygia = 1;
						dongia_VND = 0;
						dongia_NT  = 0;
						String doituongno = "";
						String madoituongno = "";
						String doituongco = "";
						String madoituongco = "";
						
						if(TIENCHENHLECH !=0){
							if(TIENCHENHLECH >0){
								TAIKHOAN_NO = TAIKHOAN_NCC ;
								TAIKHOAN_CO = TAIKHOAN_71180000;
								doituongno = "Nhà cung cấp";
								madoituongno =ncc_fk;
								doituongco = "Đối tượng khác";
								madoituongco ="" ;
							}else{
								TAIKHOAN_NO =  TAIKHOAN_71180000 ;
								TAIKHOAN_CO = TAIKHOAN_NCC ;
								doituongno = "Đối tượng khác";
								madoituongno ="";
								doituongco = "Nhà cung cấp";
								madoituongco = ncc_fk;
							}
						
							TIENCHENHLECH= Math.abs(TIENCHENHLECH);
							// KIỂM TRA TIỀN HÀNG
							if(TAIKHOAN_NO.trim().length() <= 0 || TAIKHOAN_CO.trim().length() <= 0)
							{
								this.MSG = "D1.17 Loại sản phẩm, chi phí hoặc nhà cung cấp tương ứng chưa có tài khoản kế toán đi kèm, vui lòng kiểm tra lại dữ liệu nền.";
								rsnk.close();
								db.getConnection().rollback();
								return false;
							}
							
							this.MSG = util.Update_TaiKhoan_Vat_DienGiai_SP_KBH
									( db, thangNV, namNV, rsnk.getString("ngayghinhan"), rsnk.getString("ngayghinhan"), 
									"Duyệt hóa đơn NCC", this.ID, TAIKHOAN_NO, TAIKHOAN_CO, "", 
									Double.toString(TIENCHENHLECH), Double.toString(TIENCHENHLECH), doituongno, madoituongno, doituongco, madoituongco, 
									"0", Double.toString(soluong), Double.toString(dongia_VND), tiente_fk, Double.toString(dongia_NT), Double.toString(tygia), Double.toString(TIENCHENHLECH), 
									Double.toString(0), "Hóa đơn - Tiền chênh lệch", "0" , dienGiai, sohoadon,"NULL" ,"NULL" , "NULL", "NULL", "NULL");
							if(this.MSG.trim().length() > 0)
							{
								this.MSG = "D1.21 " + this.MSG;
								rsnk.close();
								db.getConnection().rollback();
								return false;
							}
						}
						
					}
					rsnk.close();
					//----------- DINH KHOAN O CHENH LECH
					
					
			}
			
			//REVERT SOTTDUYET 
			
			long soTTDuyet = 1;
			query = "SELECT CASE WHEN (SELECT TRANGTHAI FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") != 2 THEN ISNULL(MAX(SOTTDUYET), 0)+1 "
					+ "ELSE (SELECT SOTTDUYET FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") END AS SOTTDUYET\r\n" + 
					"FROM ERP_PARK \r\n" + 
					"WHERE MONTH(NGAYGHINHAN) = (SELECT MONTH(NGAYGHINHAN) FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") "
					+" AND YEAR(NGAYGHINHAN) = (SELECT YEAR(NGAYGHINHAN) FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") "
							+ "AND SOTTDUYET IS NOT NULL AND LOAIHD = (SELECT LOAIHD FROM ERP_PARK WHERE PK_SEQ = "+this.ID+")";
			System.out.println("Query lay sott:"+ query);
			ResultSet rs = db.get(query);
			soTTDuyet = (rs.next()) ? rs.getLong("SOTTDUYET") : soTTDuyet;
			System.out.println("this.trangThai "+this.TRANGTHAI);
			
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Calendar cal = Calendar.getInstance();
			String ngayDuyet = "";
			ngayDuyet = dateFormat.format(cal.getTime());
		
			
	        query = "update ERP_PARK set trangthai = '2', SOTTDUYET = "+soTTDuyet+", NGAYDUYET = '"+ngayDuyet+"' where pk_seq = '" + this.ID + "' and trangthai=0";
		    if(db.updateReturnInt(query)!=1)
		    {
		    	this.MSG = "D1.21 Không thể cập nhật ERP_PARK: " + query;
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
			this.MSG = "D1.22 Lỗi : "+ e.toString();
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
		catch (SQLException e) {
			e.printStackTrace();
		}	
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
			"           WHEN MHSP.CHIPHI_FK IS NOT NULL THEN '3' \n"+
			"      ELSE '' END AS LOAI,  \n"+
			"      CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.PK_SEQ  \n"+
			"           WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.PK_SEQ  \n"+
			"           WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.PK_SEQ \n"+
			"           WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.PK_SEQ \n"+
			"      ELSE '' END AS SPID,  \n"+
			"      CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.MA \n"+
			"           WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.MA \n"+
			"           WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.MA \n"+
			"           WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.TEN \n"+
			"           ELSE '' END AS MA,  \n"+
			"      CASE WHEN MHSP.SANPHAM_FK IS NOT NULL THEN SP.TEN \n"+
			"           WHEN MHSP.TAISAN_FK IS NOT NULL THEN TSCD.TEN \n"+
			"           WHEN MHSP.CCDC_FK IS NOT NULL THEN CCDC.DIENGIAI \n"+
			"           WHEN MHSP.CHIPHI_FK IS NOT NULL THEN NCP.DIENGIAI \n"+
			"           ELSE '' END AS TEN,     \n"+
			"      MHSP.DONVI, HD_DMH.SOLUONG AS SOLUONG, MHSP.DONGIA, HD_DMH.THANHTIEN,  \n"+
			" 		CASE WHEN MH.LOAI = 0 THEN " +
			"      (SELECT b.NGAYNHAN FROM ERP_SHIPHANG a inner join ERP_SHIPHANG_SANPHAM b on a.PK_SEQ = b.SHIPHANG_FK where a.MUAHANG_FK = mh.PK_SEQ and b.SANPHAM_FK = mhsp.SANPHAM_FK AND a.TRANGTHAI = 1 ) " +
			" 		ELSE ISNULL(MHSP.NGAYNHAN,'') END NGAYNHANDK \n"+
			" FROM ERP_HOADONNCC_DONMUAHANG HD_DMH  \n"+
			"      INNER JOIN ERP_MUAHANG MH ON MH.PK_SEQ = HD_DMH.MUAHANG_FK  \n"+
			"	   INNER JOIN ERP_MUAHANG_SP MHSP ON MHSP.MUAHANG_FK = MH.PK_SEQ  \n"+
			"      		AND (MHSP.SANPHAM_FK = HD_DMH.SANPHAM_FK OR MHSP.TAISAN_FK = HD_DMH.TAISAN_FK OR MHSP.CCDC_FK =  HD_DMH.CCDC_FK OR MHSP.CHIPHI_FK = HD_DMH.CHIPHI_FK) \n"+
			"	   LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = MHSP.SANPHAM_FK  \n"+
			"	   LEFT JOIN ERP_MASCLON TSCD ON MHSP.TAISAN_FK = TSCD.PK_SEQ \n"+
			"	   LEFT JOIN ERP_CONGCUDUNGCU CCDC ON MHSP.CCDC_FK = CCDC.PK_SEQ \n"+
			"	   LEFT JOIN ERP_NHOMCHIPHI NCP ON MHSP.CHIPHI_FK = NCP.PK_SEQ \n"+
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
				" WHERE A.SOHOADON= '"+hd.getSohoadon()+"' AND A.NGAYTAO='"+this.NGAYGHINHAN+"' AND B.NCC_FK='"+this.NCCID+"' and b.TRANGTHAI NOT IN ('4')";
				
				
				System.out.println("kt so hoa don : "+ query);
				
				
				ResultSet kt = db.get(query);
				try {
					if(kt.next()){
						String pk_seq= kt.getString("pk_seq");
						this.MSG= "Hiện tại số hóa đơn trùng với " +pk_seq +" được tạo vào ngày " +this.NGAYGHINHAN+ " trong hệ thống. Vui lòng kiểm tra !!!";
						
						System.out.println(" kt true");
						return true;
						
					}
				} catch (Exception e) {
					System.out.println(" kt false");
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


	public String getLoaihd() {

		return this.loaihd;
	}


	public void setLoaihd(String loaihd) {

		this.loaihd = loaihd;
	}


	public String getCoPhieuChi() {

		return this.cophieuchi;
	}

	@Override
	public String getNguongochh() {
		return this.nguongochh;
	}

	@Override
	public void setNguongochh(String nguongochh) {
		this.nguongochh = nguongochh;
	}


	public void setDienGiaiCT(String dienGiaiCT){
		this.dienGiaiCT = dienGiaiCT;
	}
	public String getDienGiaiCT(){
		return this.dienGiaiCT;
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

	//Dung cho chuc nang in phieu nhap kho trong Duyet HD NCC
	//Neu sua chuc nang nay cua 1 trong 3 chuc nang lon nhu hoa don nhap khau, hoa don trong nuoc , hoa don TSCD thi phai sua ca 3
	//Begin
	public List<ISanpham> initPNK_HD_SP() {
		String query = "--INIT SAN PHAM\r\n" + 
				"SELECT MA, TEN, TAIKHOAN, DONVI , SUM(SOLUONG) SOLUONG,DONGIA,SUM(THUEVAT) AS THUEVAT,SUM(THANHTIENVIET) as THANHTIENVIET , sum (SOTT)/COUNT (MA)  as stt from \n"+ 
				" (  SELECT SUM(HDMH.SOTT ) AS SOTT,   CASE WHEN HDMH.SANPHAM_FK IS NOT NULL THEN SP.MA   \r\n" + 
				"WHEN HDMH.TAISAN_FK IS NOT NULL THEN TSCD.MA  \r\n" + 
				"WHEN HDMH.CCDC_FK IS NOT NULL THEN CCDC.MA  \r\n" + 
				"WHEN HDMH.CHIPHI_FK IS NOT NULL THEN NCP.TEN   \r\n" + 
				"END  AS MA ,  \r\n" + 
				"CASE WHEN HDMH.SANPHAM_FK IS NOT NULL THEN SP.TEN   \r\n" + 
				"WHEN HDMH.TAISAN_FK IS NOT NULL THEN TSCD.TEN   \r\n" + 
				"WHEN HDMH.CCDC_FK IS NOT NULL THEN CCDC.DIENGIAI   \r\n" + 
				"WHEN HDMH.CHIPHI_FK IS NOT NULL THEN NCP.DIENGIAI   \r\n" + 
				"END  AS TEN ,  \r\n" + 
				"CASE WHEN HDMH.SANPHAM_FK IS NOT NULL THEN (SELECT TOP 1 TAIKHOANKT_FK FROM ERP_LOAISANPHAM LSP\r\n" + 
				"WHERE LSP.PK_SEQ = SP.LOAISANPHAM_FK)\r\n" + 
				"WHEN HDMH.TAISAN_FK IS NOT NULL THEN (SELECT TOP 1 SOHIEUTAIKHOAN FROM ERP_TAIKHOANKT WHERE PK_SEQ = TSCD.TAIKHOAN_FK)\r\n" + 
				"WHEN HDMH.CCDC_FK IS NOT NULL THEN (SELECT TOP 1 N.TAIKHOAN_FK FROM ERP_CONGCUDUNGCU CCDC1\r\n" + 
				"				INNER JOIN ERP_TRUNGTAMCHIPHI TT ON TTCP_FK = TT.PK_SEQ\r\n" + 
				"				INNER JOIN ERP_NHOMCHIPHI N ON N.TTCHIPHI_FK = TT.PK_SEQ WHERE CCDC1.PK_SEQ = CCDC.PK_SEQ)  \r\n" + 
				"					WHEN HDMH.CHIPHI_FK IS NOT NULL THEN (SELECT TOP 1 TAIKHOAN_FK FROM ERP_NHOMCHIPHI NCP WHERE PK_SEQ = NCP.PK_SEQ)\r\n" + 
				"END  AS TAIKHOAN,\r\n" + 
				"(HDMH.DVT) AS DONVI,\r\n" + 
				"SUM(HDMH.SOLUONG) SOLUONG,\r\n" + 
				"ISNULL(HDMH.DONGIAVIET, HDMH.DONGIA) DONGIA,\r\n" + 
				"ROUND(SUM(HDMH.TIENVAT), 0) AS THUEVAT,\r\n" + 
				"ROUND(SUM(HDMH.SOLUONG)*ISNULL(HDMH.DONGIAVIET, HDMH.DONGIA), 0) THANHTIENVIET \r\n" + 
				"FROM ERP_HOADONNCC_DONMUAHANG HDMH  \r\n" + 
				"LEFT JOIN ERP_SANPHAM SP ON SP.PK_SEQ = HDMH.SANPHAM_FK\r\n" + 
				"LEFT JOIN ERP_MASCLON TSCD ON TSCD.PK_SEQ = HDMH.TAISAN_FK  \r\n" + 
				"LEFT JOIN ERP_CONGCUDUNGCU CCDC ON CCDC.PK_SEQ = HDMH.CCDC_FK   \r\n" + 
				"LEFT JOIN ERP_NHOMCHIPHI NCP ON NCP.PK_SEQ = HDMH.CHIPHI_FK  \r\n" + 
				"WHERE HDMH.HOADONNCC_FK IN (SELECT PK_SEQ FROM ERP_HOADONNCC HD WHERE HD.PARK_FK =  "+this.ID+") \n"+
				"GROUP BY HDMH.SANPHAM_FK, HDMH.DVT, HDMH.DONGIA, HDMH.DONGIAVIET, SP.PK_SEQ, SP.MA, SP.TEN, SP.LOAISANPHAM_FK, HDMH.TAISAN_FK, HDMH.CCDC_FK, HDMH.CHIPHI_FK, \r\n" + 
				"CCDC.PK_SEQ, CCDC.MA, CCDC.DIENGIAI, NCP.TEN, NCP.DIENGIAI, TSCD.MA, TSCD.TEN, TSCD.TAIKHOAN_FK\r\n" + 
				"HAVING SUM(HDMH.SOLUONG) > 0" +
				" ) DATA GROUP BY MA, TEN, TAIKHOAN, DONVI , DONGIA order by sum (SOTT)/COUNT (MA) ";

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
				"REPLACE(( STUFF((    SELECT DISTINCT '; ' + (SELECT NCC.TEN+' - '+NCC.MA FROM ERP_NHACUNGCAP NCC WHERE NCC.PK_SEQ = MH.NHACUNGCAP_FK) AS [text()]\r\n" + 
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

	public String getDuyethd() {
		return Duyethd;
	}

	public void setDuyethd(String duyethd) {
		Duyethd = duyethd;
	}

		public String getCurrentMonth() {
		return this.getDate().substring(5, 7);
	}

	public String getDate() {
		DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		return dateFormat.format(date);
	}

	public static void main(String[] agrs)
	{
		/*String query = 
			"select distinct sochungtu, LOAICHUNGTU from erp_phatsinhketoan\n" + 
			"where no - round(no,0) <> 0 and LOAICHUNGTU = N'Duyệt hóa đơn NCC'";
		try {
			geso.traphaco.erp.db.sql.dbutils db = new geso.traphaco.erp.db.sql.dbutils();
			ResultSet rs = db.get(query);
			
			if (rs != null)
			{
				while (rs.next())
				{
					String id = rs.getString(1);
//					String id = "100758";
					if (!id.equals("100702"))
					{
						ErpPark park = new ErpPark(id);
						park.setCtyId("100000");
						park.Duyet();
					}
				}
				rs.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}*/
		
		/*String query = "SELECT PK_SEQ FROM ERP_PARK \n" +
					"WHERE PK_SEQ IN (100691,100693,100694,100859,100902,100908,101030,101187)";
		String idKhongDuyet = "";
		try{
			geso.traphaco.erp.db.sql.dbutils db = new geso.traphaco.erp.db.sql.dbutils();
			ResultSet rs = db.get(query);
			if (rs != null)
			{
				while (rs.next())
				{
					String id = rs.getString("PK_SEQ");
					ErpPark park = new ErpPark(id);
	e11				park.setCtyId("100000");
					if(!park.Duyet())
						idKhongDuyet = idKhongDuyet + id;

				}
				rs.close();
			}
			System.out.println("Những phiếu không duyệt được " +idKhongDuyet);
		}catch(Exception ex){
			ex.printStackTrace();
		}*/
		
		
	}
}