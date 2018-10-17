package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.aspose.cells.Cell;
import com.aspose.cells.Cells;
import com.aspose.cells.Color;
import com.aspose.cells.FileFormatType;
import com.aspose.cells.Font;

import com.aspose.cells.Style;
import com.aspose.cells.TextAlignmentType;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class TinhHinhThucHienCTNPPSvl extends HttpServlet 
{
	private static final long serialVersionUID = 1L;
	
	public TinhHinhThucHienCTNPPSvl() {
        super();
        
    }    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();
	    Utility util = new Utility();
	    
	    String querystring = request.getQueryString();
	    String userId = util.getUserId(querystring);
	    obj.setuserId(userId);
	    String nppId = util.getIdNhapp(userId);
		String nppTen = util.getTenNhaPP();

		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Distributor/TinhhinhthuchienCTNpp.jsp";
		response.sendRedirect(nextJSP);	
	}
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit(); 
	    String userId = (String) session.getAttribute("userId");  
	    String userTen = (String) session.getAttribute("userTen"); 

	    if(userId == null)    	userId ="";
	    obj.setuserId(userId);
	    Utility util = new Utility();
	    String nppId = util.getIdNhapp(userId);
		String nppTen = util.getTenNhaPP();
		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
   	 	obj.setuserTen(userTen);
   	 	String kenhId=util.antiSQLInspection(request.getParameter("kenhId"));
   	 	if(kenhId == null)
	   		 kenhId ="";
   	 	obj.setkenhId(kenhId);
	   	String dvkdId=util.antiSQLInspection(request.getParameter("dvkdId"));
	   	if(dvkdId == null)
	   		dvkdId ="";
	   	obj.setdvkdId(dvkdId);
	   	obj.setMonth(util.antiSQLInspection(request.getParameter("tuthang")));
	   	obj.setYear(util.antiSQLInspection(request.getParameter("tunam")));
	   	 
	 	String vungId=util.antiSQLInspection(request.getParameter("vungId"));
	   	if(vungId ==null)
	   		 vungId = "";
	   	
	   	obj.setvungId(vungId);
	   	 
	   	String khuvucId=util.antiSQLInspection(request.getParameter("khuvucId"));
	   	if(khuvucId == null)
	   		 khuvucId ="";
	   	obj.setkhuvucId(khuvucId);
	   	 
	   	String dvdlId=util.antiSQLInspection(request.getParameter("dvdlId"));
		if(dvdlId == null)
			 dvdlId ="";
		obj.setdvdlId(dvdlId);
		 
		
		String tuthang = request.getParameter("tuthang");
		if(tuthang == null)
			tuthang = "";
		
		obj.setFromMonth( (tuthang.length()>1? tuthang:"0"+tuthang ));
		System.out.println("Tu thang: " + tuthang);
		
		String denthang = request.getParameter("denthang");
		if(denthang == null)
			denthang = "";
		obj.setToMonth((denthang.length()>1? denthang:"0"+denthang ));
		System.out.println("Den thang: " + denthang);
		
		String tunam = request.getParameter("tunam");
		if(tunam == null)
			tunam = "";
		obj.setFromYear(tunam);
		
		String dennam = request.getParameter("dennam");
		if(dennam == null)
			dennam = "";
		obj.setToYear(dennam);
		System.out.println("Den thang: " + denthang);
		
		
		String sql ="";
		
		String action = util.antiSQLInspection(request.getParameter("action"));
		
		
		
		if(action.equals("Taomoi"))
		{
			try{						
				response.setContentType("application/xlsm");
				response.setHeader("Content-Disposition", "attachment; filename=ThucDatSoVoiChiTieuNPP_"+util.setTieuDe(obj)+".xlsm");
		        OutputStream out = response.getOutputStream();
		        String query =  setQuery(sql,obj, userId);
		        CreatePivotTable(out,obj,query);	
		        
			}catch(Exception ex){
				obj.setMsg(ex.getMessage());
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", userId);
				String nextJSP = "/TraphacoHYERP/pages/Distributor/TinhhinhthuchienCTNpp.jsp";
				response.sendRedirect(nextJSP);
			}
		} else{			 
			 obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", userId);
			String nextJSP = "/TraphacoHYERP/pages/Distributor/TinhhinhthuchienCTNpp.jsp";
			response.sendRedirect(nextJSP);
		}
	}
	
	private String setQuery(String sql, IStockintransit obj, String userId) 
	{
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		
		String tungay=obj.getYear() + "-"+ (obj.getMonth().length() >1?obj.getMonth():"0"+obj.getMonth())+"-" + "01";
		
		String denngay=obj.getYear() + "-"+ (obj.getMonth().length() >1?obj.getMonth():"0"+obj.getMonth())+"-" +"31"; 
		
		String thangcuoi=( Integer.parseInt(obj.getMonth())+1)+"";
		
		String thangcuoi1=obj.getYear() + "-"+ (thangcuoi.length() >1?thangcuoi:"0"+thangcuoi)+"-" +"01";
		
		String ngaycuoithang="";
		String sql1=" SELECT  replace(convert(char(10), DATEADD(day, - 1,'"+thangcuoi1+"') , 102) , '.', '-' ) as ngay   ";
		
		dbutils db=new dbutils();
		ResultSet rs=db.get(sql1);
		try{
			if(rs.next()){
				ngaycuoithang=rs.getString("ngay");
			}
		}catch(Exception er){
			er.printStackTrace();
		}
		
		sql1="select DISTINCT t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong,b.Tu from TIEUCHITINHTHUONG t "+ 
		" inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  "+
		" where t.THANG="+obj.getMonth()+" and t.NAM="+obj.getYear()+" and t.LOAI=5 and b.STT=1 and b.Tieuchi=1 and t.TRANGTHAI=1";
			
		String mucngay="0";
		String thuongdunghan="0";
		rs=db.get(sql1);
		try{
			if(rs.next()){
				mucngay=rs.getString("Tu");
				thuongdunghan=""+(rs.getDouble("Thuong")/100);
			}
		}catch(Exception er){
			er.printStackTrace();
		}
		/***Thuong tren % doanh so */
		String qr =
			 "  SELECT KBH.TEN AS CHANNEL, QH.TEN AS PROVINCE, DVKD.DONVIKINHDOANH AS BUSINESS_UNIT,	  \n"+ 
			 "  V.TEN AS VUNG, KV.TEN AS KHUVUC,		NPP.TEN AS DISTRIBUTOR,NPP.MA AS DISCODE    \n"+ 
			 "  ,CTPRI.CHITIEU AS CHITIEUPRI,ISNULL(CTSEC.CHITIEU,0) AS CHITIEUSEC,ISNULL(DSPRI.DOANHSO,0) AS DOANHSOPRI,  \n"+ 
			 "  ISNULL(DSSEC.DOANHSO,0)-ISNULL(TRAVE.DOANHSO,0) AS DSSEC  \n"+ 
			 "  , ISNULL(TON.doanhsoton *thuongtonkho.phantramthuong,0) AS THUONGTONKHO,  \n"+ 
			 "  ISNULL(DSPRI.DOANHSO * KHOASO.THUONG,0) AS THUONGDUNGHAN , \n"+
			 "  thuongdssec.thuongdssec * (ISNULL(DSSEC.DOANHSO,0)-ISNULL(TRAVE.DOANHSO,0)) as thuongdssec , \n"+
			 "  thuongdspri.thuongdspri * ISNULL(DSPRI.DOANHSO,0) as thuongdspri,isnull(TON.DoanhSoTon,0) as DoanhSoTon,tdKhoaSo.NgayGioKhoaSo  \n"+ 
			 "  FROM   \n"+ 
			 "   (         \n"+ 
			 "  	SELECT THANG, NAM, CTPRINPP.NHAPP_FK AS NPP_FK, KENH_FK AS KBH_FK, CTPRI.DVKD_FK, CTPRINPP.CHITIEU     \n"+ 
			 "  	FROM CHITIEU CTPRI INNER JOIN CHITIEU_NHAPP CTPRINPP ON CTPRI.PK_SEQ = CTPRINPP.CHITIEU_FK    \n"+ 
			 "  	WHERE CTPRI.TRANGTHAI<> '2'   AND   CTPRI.THANG ="+obj.getMonth()+" AND CTPRI.NAM =   "+obj.getYear() + 
			 "  ) CTPRI    \n"+ 
			 "    FULL OUTER JOIN(         \n"+ 
			 "   SELECT THANG,NAM, CTSECNPP.NHAPP_FK as NPP_FK, KENH_FK AS KBH_FK, CTSEC.DVKD_FK, CTSECNPP.CHITIEU         \n"+ 
			 "   FROM CHITIEU_SEC CTSEC       \n"+ 
			 "   INNER JOIN CHITIEU_NHAPP_SEC CTSECNPP      \n"+ 
			 "   ON CTSEC.PK_SEQ = CTSECNPP.CHITIEU_SEC_FK              \n"+ 
			 "   WHERE CTSEC.TRANGTHAI <> 2  AND  CTSEC.THANG ="+obj.getMonth()+" AND CTSEC.NAM = "+ obj.getYear() +
			 "  ) CTSEC      \n"+ 
			 "  ON  CTPRI.NPP_FK = CTSEC.NPP_FK   \n"+ 
			 "  AND CTPRI.DVKD_FK = CTSEC.DVKD_FK   AND CTPRI.KBH_FK = CTSEC.KBH_FK    \n"+ 
			 "  LEFT JOIN   \n"+ 
			 "  (  \n"+ 
			 "  		SELECT NH.NPP_FK,NH.KBH_FK,SP1.DVKD_FK ,SUM(ISNULL(NHSP.GIANET,0)* ISNULL(NHSP.SOLUONG,0)) -sum(isnull(cktt,0))  AS DOANHSO      \n"+ 
			 "  		FROM (    \n"+ 
			 "  		SELECT * FROM NHAPHANG WHERE NGAYCHUNGTU>= '"+tungay+"' AND NGAYCHUNGTU  <='"+denngay+"' and   pk_seq not in(select nhaphang_fk from huynhaphang WHERE TRANGTHAI=1 ) and loaihoadon=0 AND TRANGTHAI!=2   \n"+ 
			 "  		) NH      		INNER JOIN NHAPHANG_SP NHSP ON NHSP.NHAPHANG_FK = NH.PK_SEQ        \n"+ 
			 "  		LEFT JOIN SANPHAM SP1 ON SP1.MA = NHSP.SANPHAM_FK         \n"+ 
			 "  		GROUP BY NH.NPP_FK,NH.KBH_FK,SP1.DVKD_FK    \n"+ 
			 "  ) DSPRI ON DSPRI.DVKD_FK=CTPRI.DVKD_FK AND DSPRI.KBH_FK=CTPRI.KBH_FK AND DSPRI.NPP_FK=CTPRI.NPP_FK  \n"+ 
			 "  LEFT JOIN   \n"+ 
			 "  (  \n"+ 
			 "  		SELECT DH.NPP_FK,DH.KBH_FK,SP.DVKD_FK,SUM(DHSP.SOLUONG*DHSP.GIAMUA/1.1*0.97) AS DOANHSO          \n"+ 
			 "  		FROM (   \n"+ 
			 "  		    SELECT * FROM DONHANG WHERE TRANGTHAI ='1' AND NGAYNHAP >= '"+tungay+"' AND NGAYNHAP <='"+denngay+"'  \n"+ 
			 "  			AND PK_SEQ NOT IN  (   "+ 
			 "  						SELECT DONHANG_FK FROM DONHANGTRAVE DHT WHERE DHT.TRANGTHAI=3 AND DHT.DONHANG_FK    \n"+ 
			 "  					    IS NOT NULL  AND DHT.NGAYNHAP >= '"+tungay+"' AND DHT.NGAYNHAP <='"+denngay+"'  \n"+ 
			 "  		    )  \n"+ 
			 "  		) DH INNER JOIN DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK = DH.PK_SEQ   \n"+ 
			 "  		  INNER JOIN SANPHAM SP ON SP.PK_SEQ = DHSP.SANPHAM_FK           \n"+ 
			 "  		GROUP BY DH.NPP_FK,DH.KBH_FK,SP.DVKD_FK    \n"+ 
			 "  		  \n"+ 
			 "  ) DSSEC ON  DSSEC.DVKD_FK=CTPRI.DVKD_FK AND DSSEC.KBH_FK=CTPRI.KBH_FK AND DSSEC.NPP_FK=CTPRI.NPP_FK  \n"+ 
			 "    \n"+ 
			 "  LEFT JOIN   \n"+ 
			 "  (   \n"+ 
			 "  	SELECT DHT.NPP_FK,DHT.KBH_FK,SP.DVKD_FK,-1 *SUM(DHTSP.SOLUONG*DHTSP.GIAMUA/1.1*0.97) AS DOANHSO         FROM  DONHANGTRAVE DHT   \n"+ 
			 "  	INNER JOIN DONHANGTRAVE_SANPHAM DHTSP ON DHT.PK_SEQ=DHTSP.DONHANGTRAVE_FK  \n"+ 
			 "  	INNER JOIN SANPHAM SP ON SP.PK_SEQ = DHTSP.SANPHAM_FK     \n"+ 
			 "  	WHERE DHT.TRANGTHAI=3 AND DHT.DONHANG_FK    \n"+ 
			 "  	IS  NULL  AND DHT.NGAYNHAP >= '"+tungay+"' AND DHT.NGAYNHAP <='"+denngay+"'  \n"+ 
			 "  	GROUP BY  DHT.NPP_FK,DHT.KBH_FK,SP.DVKD_FK   \n"+ 
			 "  ) TRAVE ON TRAVE.DVKD_FK=CTSEC.DVKD_FK AND TRAVE.KBH_FK=CTSEC.KBH_FK AND TRAVE.NPP_FK=CTSEC.NPP_FK   \n"+ 
			 "    \n"+ 
			 " LEFT JOIN   \n"+ 
			 " (  \n"+ 
			 "		 SELECT TONKHO.DVKD_FK,TONKHO.NPP_FK,TONKHO.KBH_FK ,SUM(SOLUONG* GIAMUANPP * 0.97)   AS  doanhsoton \n"+   
			 "			FROM (  \n"+  
			 "						SELECT SP.DVKD_FK, NPP_FK,KBH_FK, KHO_FK, SANPHAM_FK ,SOLUONG \n"+			    
			 "						FROM TONKHONGAY 	     \n"+
			 "		 					INNER JOIN SANPHAM SP ON SP.PK_SEQ=SANPHAM_FK \n"+	   
			 "						WHERE NGAY = replace(convert(char(10), DATEADD(day, - 1,'"+thangcuoi1+"') , 102) , '.', '-' ) \n"+	    
			 "						UNION ALL \n"+
			 "						SELECT C.DVKD_FK,B.NPP_FK,B.KBH_FK,ISNULL( CTKM.KHO_FK,B.KHO_FK) AS KHO_FK,C.PK_SEQ AS SANPHAM_FK,SUM(A.SOLUONG) AS SOLUONG \n"+
			 "						FROM NHAPHANG_SP A INNER JOIN NHAPHANG B ON A.NHAPHANG_FK = B.PK_SEQ	 \n"+
			 "								LEFT JOIN CTKHUYENMAI CTKM ON CTKM.SCHEME=A.SCHEME	 \n"+
			 "								INNER JOIN SANPHAM C ON C.MA = A.SANPHAM_FK  \n"+
			 "						WHERE ISNULL(B.ISTHAYTHE,0) !=1   AND B.TRANGTHAI !=2 \n"+  
			 "							AND B.NGAYCHUNGTU >='"+tungay+"' AND B.NGAYCHUNGTU <='"+denngay+"'  \n"+
			 "							AND ISNULL(B.ISTHAYTHE,0) !=1 AND B.PK_SEQ NOT IN  \n"+
			 "							( \n"+
			 "								SELECT PK_SEQ FROM  NHAPHANG WHERE TRANGTHAI=1 AND NGAYNHAN >='"+tungay+"' \n"+ 
			 "								AND NGAYNHAN <='"+denngay+"'					 \n"+
			 "								AND NGAYNHAN <= (SELECT MAX(NGAYKS) FROM KHOASONGAY WHERE NPP_FK=B.NPP_FK) \n"+
			 "							) \n"+
			 "						GROUP BY C.DVKD_FK,B.NPP_FK,B.KBH_FK,ISNULL( CTKM.KHO_FK,B.KHO_FK),C.PK_SEQ \n"+
			 "					) 	AS  TONKHO LEFT JOIN      \n"+
			 "			( 	     \n"+
			 "				SELECT  B.KENH_FK,B.DVKD_FK ,B.NPP_FK,D.SANPHAM_FK,D.GIAMUANPP,isnull(Giamuanpp_tuvc,0) as Giamuanpp_tuvc \n"+
			 "				FROM        \n"+
			 "				( \n"+ 			  
			 "						SELECT B.PK_SEQ,B.KENH_FK,B.DVKD_FK,C.NPP_FK \n"+  
			 "						FROM BANGGIAMUANPP B   \n"+
			 "						INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK \n"+   
			 "						WHERE  B.TUNGAY <='"+tungay+"' AND    \n"+
			 "						B.PK_SEQ =   \n"+
			 "						( \n"+   
			 "							SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG \n"+  
			 "							INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK \n"+   
			 "							WHERE BG.TUNGAY <= '"+tungay+"' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK \n"+  
			 "							ORDER BY TUNGAY DESC   \n"+
			 "						) \n"+    
			 "				) AS B	INNER JOIN BGMUANPP_SANPHAM D ON B.PK_SEQ=D.BGMUANPP_FK \n"+  			
			 "	 ) NPPK ON NPPK.NPP_FK = TONKHO.NPP_FK AND NPPK.SANPHAM_FK = TONKHO.SANPHAM_FK   AND NPPK.KENH_FK=TONKHO.KBH_FK AND TONKHO.KHO_FK=100000 \n"+  
			 "	 GROUP BY TONKHO.DVKD_FK,TONKHO.NPP_FK,TONKHO.KBH_FK      \n"+
			 "  ) AS  TON ON TON.DVKD_FK=CTPRI.DVKD_FK AND TON.KBH_FK=CTPRI.KBH_FK AND TON.NPP_FK=CTPRI.NPP_FK   \n"+  
			 "  LEFT JOIN   \n"+ 
			 "  (  \n"+ 
			 "  		SELECT  NPP_FK ,"+thuongdunghan+" AS THUONG,NGAYGIO as NgayGioKhoaSo  "+ 
			 "  		FROM KHOASONGAY  "+ 
			 "  		WHERE NGAYKS='"+ngaycuoithang+"' AND  "+ 
			 "  		replace(convert(char(10),  NGAYGIO  , 102) , '.', '-' )   "+ 
			 "  		<= replace(convert(char(10), DATEADD(day, + "+mucngay+", CAST ('"+ngaycuoithang+"' AS DATETIME)) , 102) , '.', '-' )   "+ 
			 "    "+ 
			 "  ) AS  KHOASO ON KHOASO.NPP_FK=CTPRI.NPP_FK " +
			 " left join  "+ 
			"	( "+   		
		    "	SELECT  NPP_FK ,0.003 AS THUONG,NGAYGIO as NgayGioKhoaSo  FROM KHOASONGAY  WHERE NGAYKS='"+ngaycuoithang+"' "+
		    "	)as tdKhoaSo on tdKhoaSo.NPP_FK=CTPRI.NPP_FK "+
			 "  LEFT JOIN ( " +
			 "  	select DISTINCT t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong/100 as phantramthuong ,b.Tu  from TIEUCHITINHTHUONG t \n"+ 
			 " 		inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  \n"+
			 " 		where t.THANG="+obj.getMonth()+" and t.NAM="+obj.getYear()+" and t.LOAI=5  and b.STT=3 and b.Tieuchi=2 and t.TRANGTHAI=1 \n"+
			 		" )  thuongtonkho on thuongtonkho.DVKD_FK=CTPRI.DVKD_FK AND thuongtonkho.KBH_FK=CTPRI.KBH_FK  \n"+
			 "   left join ( \n"+
			 " select  t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong /100  as thuongdssec,b.Tieuchi,b.Tu,b.Den  from TIEUCHITINHTHUONG t \n"+ 
			 " inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  \n"+
			 " where t.THANG="+obj.getMonth()+" and t.NAM="+obj.getYear()+" and t.LOAI=5  and b.STT=4 and b.Tieuchi=3 and t.TRANGTHAI=1 \n"+
			 " ) as thuongdssec on  thuongdssec.DVKD_FK=CTSEC.DVKD_FK AND thuongdssec.KBH_FK=CTSEC.KBH_FK     \n"+
			 "  and    DSSec.doanhso *100 / (isnull(CTSec.CHITIEU,0)+0.001) >= thuongdssec.tu   \n"+
			 
			 "   left join ( \n"+
			 " select  t.THANG,t.NAM,t.KBH_FK,t.DVKD_FK,b.Thuong /100  as thuongdspri,b.Tieuchi,b.Tu,b.Den  from TIEUCHITINHTHUONG t \n"+ 
			 " inner join TIEUCHITINHTHUONG_CHITIET b on t.PK_SEQ=b.TIEUCHITINHTHUONG_FK  \n"+
			 " where t.THANG="+obj.getMonth()+" and t.NAM="+obj.getYear()+" and t.LOAI=5  and b.Tieuchi=4 and t.TRANGTHAI=1 \n"+
			 " ) as thuongdspri on  thuongdspri.DVKD_FK=CTPRI.DVKD_FK AND thuongdspri.KBH_FK=CTPRI.KBH_FK     \n"+
			 "  and   ISNULL(DSPRI.doanhso,0) *100 / (isnull(CTPRI.CHITIEU,0)+0.001) > = thuongdspri.tu  \n"+
			 "  AND   ISNULL(DSPRI.doanhso,0) *100 / (isnull(CTPRI.CHITIEU,0)+0.001) <  thuongdspri.DEN \n"+
			 "  LEFT JOIN KENHBANHANG KBH ON  KBH.PK_SEQ = isnull( CTPRI.KBH_FK,ctsec.kBH_fk)       \n"+ 
			 "  LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ =isnull( CTPRI.DVKD_FK ,ctsec.dvkd_fk)  \n"+ 
			 "  LEFT JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ =isnull( CTPRI.NPP_FK,ctsec.npp_fk) AND NPP.TRANGTHAI='1'    \n"+ 
			 "  LEFT JOIN KHUVUC KV ON KV.PK_SEQ = NPP.KHUVUC_FK    \n"+ 
			 "  LEFT JOIN VUNG V ON V.PK_SEQ = KV.VUNG_FK    \n"+ 
			 "  LEFT JOIN QUANHUYEN QH ON QH.PK_SEQ = NPP.QUANHUYEN_FK where 1=1 ";
			if(obj.getnppId().length() > 0)
				qr += " and npp.pk_seq = '"+obj.getnppId()+"' ";
			if(obj.getkenhId().length() > 0)
				qr += " and kbh.pk_seq = '"+obj.getkenhId()+"' ";
		   if(obj.getvungId().length()>0)
			   qr += " and v.pk_seq ='"+obj.getvungId()+"'";
		   if(obj.getdvkdId().length()>0)
			   qr += " and dvkd.pk_seq='"+obj.getdvkdId() + "'";
		   if(obj.getkhuvucId().length()>0)
			   qr +=" and kv.pk_seq = '"+obj.getkhuvucId()+"'";
		   	System.out.println("lenh sql: "+qr);
		return qr;
	}
	private void CreatePivotTable(OutputStream out, IStockintransit obj, String query) throws Exception
    {   
	     try{
	    		String chuoi=getServletContext().getInitParameter("path") + "\\ThucDatSoVoiChiTieuNPP.xlsm";
	 		FileInputStream fstream = new FileInputStream(chuoi);	
	 		Workbook workbook = new Workbook();
	 		
	 		workbook.open(fstream);
	 		
	 		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

	 		CreateStaticHeader(workbook,obj); 
			
	 		FillData(workbook,obj.getFieldShow(),query); 
			
	 		workbook.save(out);
	 		
	 		fstream.close();
	     }catch(Exception ex){
	    	 ex.printStackTrace();
	    	 throw new Exception(ex.getMessage());
	     }
   }
	
	private void CreateStaticHeader(Workbook workbook,IStockintransit obj) throws Exception 
	{
		try{
	 		Worksheets worksheets = workbook.getWorksheets();
			Worksheet worksheet = worksheets.getSheet(0);
			worksheet.setName("Sheet1");
			Cells cells = worksheet.getCells();
			
		    Style style;
		    Font font = new Font();
		    font.setColor(Color.RED);//mau chu
		    font.setSize(16);// size chu
		   	font.setBold(true);
		   	
		    cells.setRowHeight(0, 20.0f);
		    Cell cell = cells.getCell("A1");
		    style = cell.getStyle();
		    style.setFont(font); 
		    style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu 	        
		    
		    ReportAPI.getCellStyle(cell,Color.RED, true, 14, "BÁO CÁO THỰC ĐẠT SO VỚI CHỈ TIÊU NHÀ PHÂN PHỐI");
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("A3");
		    
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Từ ngày : " + obj.gettungay() + "" );
		    
		    cells.setRowHeight(3, 18.0f);
		    cell = cells.getCell("C3"); 
		    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Đến ngày : " + obj.getdenngay() + "" );
			
		    cells.setRowHeight(4, 18.0f);
		    cell = cells.getCell("A4");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + obj.getDateTime());
		    
		    cells.setRowHeight(5, 18.0f);
		    cell = cells.getCell("A5");
		    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
					    
		    cell = cells.getCell("AA1"); cell.setValue("KenhBanHang");
		    cell = cells.getCell("AB1"); cell.setValue("DonViKinhDoanh");
		    cell = cells.getCell("AC1"); cell.setValue("Mien");
		    cell = cells.getCell("AD1"); cell.setValue("Vung");
		    cell = cells.getCell("AE1"); cell.setValue("MaNhaPhanPhoi");
		    cell = cells.getCell("AF1"); cell.setValue("NhaPhanPhoi");
		    cell = cells.getCell("AG1"); cell.setValue("Tinh/Thanh");	
		    cell = cells.getCell("AH1"); cell.setValue("ChiTieuBanRa");
		    cell = cells.getCell("AI1"); cell.setValue("ChiTieuMuaVao");
		    cell = cells.getCell("AJ1"); cell.setValue("ThucDat(BanRa)");
		    cell = cells.getCell("AK1"); cell.setValue("ThucDat(MuaVao)");
		    cell = cells.getCell("AL1"); cell.setValue("%ThucDat/ChiTieu(BanRa)");
		    cell = cells.getCell("AM1"); cell.setValue("%ThucDat/ChiTieu(MuaVao)");	
		    cell = cells.getCell("AN1"); cell.setValue("ThuongMuaVao(1)");	
		    cell = cells.getCell("AO1"); cell.setValue("ThuongBanRa(2)");	
		    cell = cells.getCell("AP1"); cell.setValue("ThuongTonKho(3)");	
		    cell = cells.getCell("AQ1"); cell.setValue("ThuongBCDungHan(4)");
		    cell = cells.getCell("AR1"); cell.setValue("DoanhSoTonKho");
		    cell = cells.getCell("AS1"); cell.setValue("TONGTHUONG(1+2+3+4)");
		    cell = cells.getCell("AT1"); cell.setValue("NgayGioKhoaSo)");
		    
		}catch(Exception ex){
			ex.printStackTrace();
			throw new Exception("Khong the tao duoc Header cho bao cao...");
		}
	}
	private void FillData(Workbook workbook,String[] fieldShow, String query)throws Exception 
	{
		try{
			Worksheets worksheets = workbook.getWorksheets();
		    Worksheet worksheet = worksheets.getSheet(0);
		    Cells cells = worksheet.getCells();
		    dbutils db = new dbutils();
		    ResultSet rs = db.get(query);
		    double PhanTramThucDatSec=0;
			double PhanTramThucDatPri=0;
		    int index = 2;
		    Cell cell = null;	    
		    while (rs.next()) {		    	
		    	cell = cells.getCell("AA" + Integer.toString(index));cell.setValue(rs.getString("Channel"));
		    	cell = cells.getCell("AB" + Integer.toString(index));cell.setValue(rs.getString("Business_unit"));
		    	cell = cells.getCell("AC" + Integer.toString(index));cell.setValue(rs.getString("Vung"));
				cell = cells.getCell("AD" + Integer.toString(index));cell.setValue(rs.getString("Khuvuc"));				
				cell = cells.getCell("AE" + Integer.toString(index));cell.setValue(rs.getString("DisCode"));
				cell = cells.getCell("AF" + Integer.toString(index));cell.setValue(rs.getString("Distributor"));
				cell = cells.getCell("AG" + Integer.toString(index));cell.setValue(rs.getString("Province"));
				
				
				cell = cells.getCell("AH" + Integer.toString(index));cell.setValue(rs.getDouble("chitieusec"));
				cell = cells.getCell("AI" + Integer.toString(index));cell.setValue(rs.getDouble("chitieupri"));
				cell = cells.getCell("AJ" + Integer.toString(index));cell.setValue(rs.getDouble("dssec"));
				cell = cells.getCell("AK" + Integer.toString(index));cell.setValue(rs.getDouble("doanhsopri"));
				
				if(rs.getDouble("chitieupri") >0) {
					PhanTramThucDatPri=rs.getDouble("doanhsopri")*100/ rs.getDouble("chitieupri");
				}
				if(rs.getDouble("chitieusec") >0) {
					PhanTramThucDatSec =rs.getDouble("dssec")*100/ rs.getDouble("chitieusec");
				}
				
				cell = cells.getCell("AL" + Integer.toString(index));cell.setValue(PhanTramThucDatSec);
				cell = cells.getCell("AM" + Integer.toString(index));cell.setValue(PhanTramThucDatPri);
				cell = cells.getCell("AN" + Integer.toString(index));cell.setValue(rs.getDouble("thuongdspri"));
				cell = cells.getCell("AO" + Integer.toString(index));cell.setValue(rs.getDouble("thuongdssec"));
				cell = cells.getCell("AP" + Integer.toString(index));cell.setValue(rs.getDouble("THUONGTONKHO"));
				cell = cells.getCell("AQ" + Integer.toString(index));cell.setValue(rs.getDouble("THUONGDUNGHAN"));
				cell = cells.getCell("AR" + Integer.toString(index));cell.setValue(rs.getDouble("DoanhSoTon"));
				cell = cells.getCell("AS" + Integer.toString(index));cell.setValue(rs.getDouble("THUONGDUNGHAN")+ rs.getDouble("THUONGTONKHO")+rs.getDouble("thuongdssec")+rs.getDouble("thuongdspri") );
				
				cell = cells.getCell("AT" + Integer.toString(index));cell.setValue(rs.getString("NgayGioKhoaSo"));
				index++;
			
			}
		    if(rs!=null)
		    {
		    	rs.close();
		    }
		    
		    if(db != null) db.shutDown();
		    
		    if(index==2)
		    {
		    	throw new Exception("Xin loi. Khong co bao cao voi dieu kien da chon...!!!");
		    }
		    ReportAPI.setHidden(workbook, 50);
		}catch(Exception ex)
		{
			ex.printStackTrace();
			throw new Exception(ex.getMessage());
			
		}	

	}	
}
