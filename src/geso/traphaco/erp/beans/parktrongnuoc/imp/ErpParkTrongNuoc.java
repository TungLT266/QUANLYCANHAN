package geso.traphaco.erp.beans.parktrongnuoc.imp;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadon;
import geso.traphaco.erp.beans.parktrongnuoc.IErpParkTrongNuoc;
import geso.traphaco.erp.beans.parktrongnuoc.IErpHoadonSp;

public class ErpParkTrongNuoc implements IErpParkTrongNuoc
{
	String ID,NCC, NCCID, LOAIHANG,NGAYGHINHAN,USERID,TRANGTHAI,MSG,TinhThueNhapKhau,CtyId,tigia, nppdangnhap, trangthaiHd;
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
	
	String cophieuchi;
	
	public ErpParkTrongNuoc()
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
	}
	
	public ErpParkTrongNuoc(String id)
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
				}				
				this.hdList = hdList;
			}
		}
	}
	
	public void createRs() 
	{ 
		this.getNppInfo();
		
		this.nccRs = db.get("select PK_SEQ, MA + ', ' + TEN as TEN from ERP_NHACUNGCAP WHERE CONGTY_FK = "+this.CtyId);

		this.loaidonmhRs = db.get("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");
		
		System.out.println("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");
		
		
		if(this.NCCID.length() > 0)
		{
			String query = "";			
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
				query = "\n select distinct a.PK_SEQ, a.NGAYMUA,  a.SOPO, isnull(a.LOAI,0) LOAI, a.tooltip "+   
						"\n from ERP_MUAHANG a "+
						"\n LEFT JOIN "+ 
						"\n (SELECT B.MUAHANG_FK,sum(isnull(B.SOLUONG,0)) as SOLUONG "+
						"\n	FROM ERP_HOADONNCC A INNER JOIN ERP_HOADONNCC_DONMUAHANG B ON A.pk_seq = B.HOADONNCC_FK "+
						"\n   INNER JOIN ERP_PARK C ON A.park_fk = C.pk_seq "+
						"\n 	WHERE A.trangthai != 4 ";
				if(this.ID.trim().length()>0)
						query+= "\n	AND C.PK_SEQ != "+this.ID+" ";
				
				query+=	"\n GROUP BY B.MUAHANG_FK) c on a.PK_SEQ = c.MUAHANG_FK "+
						"\n where NHACUNGCAP_FK = '" + this.NCCID + "'  AND a.TIENTE_FK = " + this.ttId + " "+
						"\n AND a.LOAI = "+this.loaidonmhId;
				if(this.loaidonmhId.equals("1")){
					
				}
					/*query+= "\n AND isnull(a.isduocphanbo,0) = 1 ";*/
				
				query+= "\n	AND a.CONGTY_FK = "+this.CtyId+"  "+
				    	"\n order by a.NGAYMUA desc";				
			}	
			
			System.out.println("LAY PO : " + query );
			this.poNKRs = db.get(query);
			
			
		}
		
		String query = "";
		
		if(this.poNkId.trim().length()>0)
		{	  
			query = 	"\n SELECT distinct MH.PK_SEQ, MH.NGAYMUA, "+
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
					 	"\n INNER JOIN SANPHAM SP ON SP.PK_SEQ = PBCT.SANPHAM_FK "+
					    "\n LEFT JOIN "+
					    "\n ( "+ 
						"\n	 SELECT A.MUAHANG_FK, A.SANPHAM_FK, SUM(SOLUONG) AS SOLUONG , ISNULL(A.SOLO, '') SOLO "+
					  	"\n	 FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
					  	"\n	 INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ "+
					  	"\n	 WHERE C.trangthai !=4 AND SOLUONG > 0 "+
					  	"\n	 GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, '') "+
					    "\n  )HDSP ON HDSP.MUAHANG_FK = MH.PK_SEQ AND HDSP.SANPHAM_FK = PBCT.SANPHAM_FK AND HDSP.SOLO = PBCT.SOLO "+    
						"\n WHERE (PBCT.SOLUONG  - ISNULL(HDSP.SOLUONG, 0)) > 0 "+
					    "\n  AND MH.TIENTE_FK = " + this.ttId + "  AND MH.CONGTY_FK = "+this.CtyId;
		
			
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
								String tiente = hdRs.getString("tiente");
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
										"\n	ISNULL(HDSP_HT.DONGIA, 0) DONGIA , ISNULL(HDSP_HT.THANHTIEN,0) AS THANHTIEN , "+
										"\n	ISNULL(MH.CHIPHIKHAC,0)as chiphikhac, "+
										"\n	( SELECT top(1) isnull(isnull(A.thuexuat,A.VAT),0) thuesuat "+
										"\n	  FROM ERP_MUAHANG_SP A WHERE MH.PK_SEQ  = A.MUAHANG_FK AND PBCT.SANPHAM_FK = A.SANPHAM_FK ) thuesuat, "+
										"\n	ISNULL(HDSP_HT.TIENVAT,0) TIENVAT , "+
										"\n	 PBCT.PHANBO_FK, MH.tooltip "+
										"\n	FROM ERP_MUAHANG MH "+
										"\n	INNER JOIN ERP_PHANBOMUAHANG_SP_CHITIET PBCT ON mh.CONGTY_FK = PBCT.CONGTY_FK AND MH.PK_SEQ = PBCT.MUAHANG_FK "+  
										"\n	INNER JOIN SANPHAM SP ON SP.PK_SEQ = PBCT.SANPHAM_FK "+
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
										"\n	SELECT A.MUAHANG_FK, A.SANPHAM_FK, A.DONGIA ,SUM(SOLUONG) AS SOLUONG , ISNULL(A.SOLO, '') SOLO, A.THANHTIEN, A.TIENVAT "+
										"\n	FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
										"\n INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ "+
										"\n WHERE A.HOADONNCC_FK = "+pk_seq+" AND SOLUONG > 0 "+
										"\n GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, ''), A.THANHTIEN, A.TIENVAT, A.DONGIA "+
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
				catch (SQLException e) {
					e.printStackTrace();
				}				
			}
			this.pnkList = pnkList;
		}
	}
	
	public void init() 
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
		
		query = "SELECT DISTINCT PARK.TRANGTHAI,PARK.NGAYGHINHAN, PARK.NCC_FK AS NCCID, HD.TINHTHUENHAPKHAU,  ISNULL(PARK.TIGIA,1) AS TIGIA ,  \n" +
				"  		TT.MA AS TIENTE, TT.PK_SEQ AS TTID, ISNULL(PO_NHAPKHAU, '') AS PO_NHAPKHAU, \n" +
				"  		NCCTT.PK_SEQ AS NCCTTID, ISNULL(NCCTT.TEN,'') AS NCCTTTEN, ISNULL(NCCTT.MASOTHUE,'') AS MST, ISNULL(NCCTT.DIACHI,'') DIACHI, HD.LOAIHD  \n"+
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
		String query = 
		" SELECT count(TTHD.HOADON_FK) cophieuchi,  ( SELECT isnull(trangthai,0) FROM ERP_PARK WHERE PK_SEQ = "+this.ID+") TRANGTHAIHD  \n" +
		" FROM ERP_THANHTOANHOADON_HOADON TTHD inner join ERP_THANHTOANHOADON TT on TTHD.THANHTOANHD_FK = TT.PK_SEQ \n"+
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
		
		System.out.println("select PK_SEQ, TENLOAI TEN from ERP_LOAIDONMUAHANG WHERE TRANGTHAI = 1");
		
		
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
				query = " select distinct PK_SEQ, NGAYMUA,  SOPO, isnull(a.LOAI,0) LOAI,a.tooltip \n"+   
						" from ERP_MUAHANG a  " +
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
							query = "\n	SELECT distinct MH.PK_SEQ, MH.NGAYMUA, "+
									"\n	MH.SOPO, ISNULL(MH.DUNGSAI,0) as DUNGSAI, "+
									"\n	0 LOAI , PBCT.SANPHAM_FK  SPID , SP.MA  AS MA , PBCT.SOLO, "+
									"\n	SP.TEN TEN , PBCT.DONVI, PBCT.SOLUONG -  isnull(HDSP.SOLUONG,0) AS SOLUONGDAT, "+
									"\n	ISNULL(HDSP.SOLUONG, 0) SOLUONGDARAHD , ISNULL(HDSP_HT.SOLUONG, 0) AS SOLUONGHD , "+ 
									"\n	HDSP_HT.DONGIA , ISNULL(HDSP_HT.THANHTIEN,0) AS THANHTIEN , "+
									"\n	ISNULL(MH.CHIPHIKHAC,0)as chiphikhac, "+
									"\n	( SELECT top(1) isnull(isnull(A.thuexuat,A.VAT),0) thuesuat "+
									"\n	  FROM ERP_MUAHANG_SP A WHERE MH.PK_SEQ  = A.MUAHANG_FK AND PBCT.SANPHAM_FK = A.SANPHAM_FK ) thuesuat, "+
									"\n	ISNULL(HDSP_HT.TIENVAT,0) TIENVAT , "+
									"\n	 PBCT.PHANBO_FK, MH.tooltip "+
									"\n	FROM ERP_MUAHANG MH "+
									"\n	INNER JOIN ERP_PHANBOMUAHANG_SP_CHITIET PBCT ON MH.PK_SEQ = PBCT.MUAHANG_FK AND mh.CONGTY_FK = PBCT.CONGTY_FK "+  
									"\n	INNER JOIN SANPHAM SP ON SP.PK_SEQ = PBCT.SANPHAM_FK "+
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
									"\n	SELECT A.MUAHANG_FK, A.SANPHAM_FK, ISNULL( A.DONGIA, 0) DONGIA,  SUM(SOLUONG) AS SOLUONG , ISNULL(A.SOLO, '') SOLO, A.THANHTIEN, A.TIENVAT "+
									"\n	FROM ERP_HOADONNCC_DONMUAHANG A INNER JOIN ERP_HOADONNCC B ON A.HOADONNCC_FK = B.pk_seq "+
									"\n INNER JOIN ERP_PARK C ON B.PARK_FK = C.PK_SEQ "+
									"\n WHERE A.HOADONNCC_FK = "+pk_seq+" AND SOLUONG > 0 "+
									"\n GROUP BY A.MUAHANG_FK, A.SANPHAM_FK, ISNULL(A.SOLO, ''), A.THANHTIEN, A.TIENVAT, ISNULL(A.DONGIA, 0) "+
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
	
	public boolean createPark() 
	{
		
		if(ktsohoadon_ncc()==true){
			return false;
		}
		
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
			
			String query = "";
					
			
			// LẤY TỈ GIÁ
			// THUE NHAP KHAU = 1 >>  TIGIA lấy lúc nhập vào
			// CÒN LẠI >> LẤY NHƯ DƯỚI
			
			if(this.ttId.equals("100000") )
			{
				this.tigia = "1";
			}
			
			
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
		   
		   
			query = " insert ERP_PARK( NCCTHAYTHE_FK ,NGAYGHINHAN, NCC_FK, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, CONGTY_FK, PO_NHAPKHAU, TIENTE_FK, TIGIA, LOAIHD, NPP_FK) " +
					" values("+ this.nccThayTheId +", '" + this.NGAYGHINHAN + "', '" + this.NCCID + "', '" + ngaytao + "', '" + ngaytao + "', '" + this.USERID + "', '" + this.USERID + "', '0', '"+this.CtyId+"', '" + this.poNkId + "', " + this.ttId + ", " + this.tigia + ", "+this.loaidonmhId+", "+this.nppdangnhap+" )";
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
																	
						
						query = "insert ERP_HOADONNCC(mauhoadon, mausohoadon, kyhieu, sohoadon, ngayhoadon ,sotienAVAT,sotienchietkhau,vat,sotienBVAT,thuesuat,park_fk, NGAYTAO, NGAYSUA, NGUOITAO, NGUOISUA, TRANGTHAI, NGAYDENHANTT, TINHTHUENHAPKHAU,CONGTY_FK, NPP_FK, LOAIHD) " +
								"values('" + mauhoadon.toUpperCase() + "', '" + hd.getMausohoadon().toUpperCase() + "', '" + hd.getKyhieu().toUpperCase() + "', '" + hd.getSohoadon() + "', '" + hd.getNgayhoadon()+ "', '" + hd.getSotienavat() +"'," +
										" '"+hd.getChieckhau()+ "',  '" + hd.getVAT() +"', '"+ hd.getSotienbvat()+"', '"+ hd.getThuesuat()+"', '"+ parkCurrent +"', '"+ ngaytao + "', '" + ngaytao + "', '" + this.USERID + "'," +
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
								
								if(loaihh.equals("0")) //SẢN PHẨM
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,sanpham_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, SOLO) "+
											"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+", '"+sp.getSolo()+"')";
								}
								
								if(loaihh.equals("1"))// TÀI SẢN CỐ ĐỊNH
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,taisan_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT) "+
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+")";
								}
								
								if(loaihh.equals("2"))// CHI PHÍ
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,chiphi_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT) "+
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+")";
								}
								
								if(loaihh.equals("3"))// CÔNG CỤ DỤNG CỤ
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,ccdc_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT) "+
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+")";
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
								
								if(loaihh.equals("0")) //SẢN PHẨM
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,sanpham_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT, SOLO) " +
											"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+", '"+sp.getSolo()+"')";
								
									System.out.println(query);
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
								if(loaihh.equals("1"))// TÀI SẢN CỐ ĐỊNH
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,taisan_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT) " +
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+")";
								
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
								if(loaihh.equals("2"))// CHI PHÍ
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,chiphi_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT) " +
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+")";
								
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
								}
								
								if(loaihh.equals("3"))// CÔNG CỤ DỤNG CỤ
								{
									query = "insert ERP_HOADONNCC_DONMUAHANG(hoadonncc_fk, muahang_fk,ccdc_fk, dvt ,soluong, dongia, thanhtien, soluongdat, ngaynhandk, VAT, TIENVAT) " +
									"values('" + hdCurrent + "', " + sp.getPoId() + ",  "+sp.getSanphamId()+", N'"+sp.getDonvi()+"' ," + sp.getSoluonghd().replaceAll(",", "") +", '"+ sp.getDongia().replaceAll(",", "") +"',  '" + sp.getThanhtien().replaceAll(",", "") + "', '"+sp.getSoluong().replaceAll(",", "")+"', '"+sp.getNgaynhan()+"', "+sp.getVAT()+", "+sp.getTienVat()+")";
								
									if(!db.update(query))
									{
										this.MSG = "Không thể tạo mới hóa đơn nhà cung cấp lỗi : " + query;
										db.getConnection().rollback();
										return false;
									}
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
		try 
		{
			if(ttRs != null) ttRs.close();
			if(this.Loaihanglist != null)
				this.Loaihanglist.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
		}	
		this.db.shutDown();
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

}
