package geso.traphaco.distributor.servlets.reports;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.DecimalFormat;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.WritableCellFormat;


import jxl.write.NumberFormat;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


import java.io.*;
import java.text.SimpleDateFormat;


import org.apache.poi.hssf.usermodel.examples.MergedCells;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.examples.MergingCells;
import org.apache.poi.hssf.record.MergeCellsRecord.MergedRegion;

import Z.DB;

import com.extentech.formats.XLS.Mergedcells;


public class BCPhanTichCongNoKHSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    public BCPhanTichCongNoKHSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util = new Utility();
		HttpSession session = request.getSession();
		String userTen = (String) session.getAttribute("userTen");		
		String querystring = request.getQueryString();
		String userId = util.getUserId(querystring);
		
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	session.setAttribute("loi", "");
    		  	
		//obj.settungay("");
		//obj.setdenngay("");
		//obj.setMsg("");
		//obj.setnppId("");
		//obj.init();
    	
    	String nppId= util.getIdNhapp(userId);
    	
    	String view = request.getParameter("view");
 	    if(view == null)
 	    	view = "";
 	    
 	   System.out.println("view :"+view);
 	    
 	   if(nppId!=null)
 		   view = "NPP";
 	   if(nppId==null)
 		   view ="TT";
    	
    	String nextJSP = "";
 	    
 	    if(!view.equals("TT"))
 		{
 			nextJSP = "/TraphacoHYERP/pages/Distributor/BCPhanTichCongNoKH_distributor.jsp"; 			
 		}
 	    
 	    else
 		{
 	    	System.out.println("vào đây");
 	    	nextJSP = "/TraphacoHYERP/pages/Center/BCPhanTichCongNoKH_center.jsp";
 		}
 	    
    	
		obj.setuserId(userId);
		obj.createKhRS1();
		session.setAttribute("obj", obj);
		session.setAttribute("userTen", userTen);
		//String nextJSP = "/TraphacoHYERP/pages/Distributor/BCPhanTichCongNoKH_distributor.jsp";
		response.sendRedirect(nextJSP);
	}
//
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		OutputStream out = response.getOutputStream();
		IStockintransit obj = new Stockintransit();	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
		Utility util = new Utility();
		
		String tuNgay = util.antiSQLInspection(request.getParameter("tuNgay"));
        obj.settungay(tuNgay);
        System.out.println("tungay: "+tuNgay);
        String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
        obj.setdenngay(denNgay);
        
        String KhachHangid = util.antiSQLInspection(request.getParameter("khId"));
        if(KhachHangid==null)
        	KhachHangid="";
        obj.setkhId(KhachHangid);        
        
        String nvgnId = request.getParameter("nvgnId");
	    if(nvgnId == null)
	    	nvgnId = "";
	    obj.setNvgnId(nvgnId);
	    	    
        String nppID = util.antiSQLInspection(request.getParameter("nppId"));
        if(nppID==null)
        	nppID = "";
        obj.setNppId(nppID);
        
        session.setAttribute("tuNgay", tuNgay);
        session.setAttribute("denNgay", denNgay);
        String action = request.getParameter("action");
        String userTen = (String)session.getAttribute("userTen");
        obj.setuserTen(userTen);
        System.out.println("user Ten: "+userTen);
        String userId = (String) session.getAttribute("userId"); 
        
        obj.setuserId(userId);
        
        String nppId= util.getIdNhapp(userId);
        //System.out.println("nppId: "+ nppId);
       

       String view=request.getParameter("view");
 		if(view == null)
 			view = "";
		 		
 		String ddkdId = util.antiSQLInspection(request.getParameter("ddkdId"));
         if(ddkdId==null)
        	 ddkdId="";
         obj.setDdkd(ddkdId);
         
         	if(view.equals("TT"))
			{
				nppId = util.antiSQLInspection(request.getParameter("nppId"));
				if (nppId == null)
						nppId = "";
					obj.setNppId(nppId);
					System.out.println("nppIdTT:"+nppId);
			}else
			{
				nppId=util.getIdNhapp(userId);
				obj.setNppId(nppId);
			}
  		 	
		session.setAttribute("obj", obj);	
		 								 
						
			if(action.equals("taomoi"))
			{
				String query = "";
				
				
				String condition="";
				String condition1="";
				
				 
				 
		    	if(KhachHangid.trim().length()>0)
		    		condition =" and kh.PK_SEQ='"+KhachHangid+"'";
		    	if(nppId.trim().length()>0)
		    		condition1=" and kh.NPP_FK='"+nppId+"'";
		    	
				String condition2="";
				dbutils db = new dbutils();
				query+= "select MONTH('"+tuNgay+"')-1 as Month";	
		    	ResultSet month = db.get(query);
		    	if(month!=null){
		    		try {
						while(month.next()){
							condition2= month.getString("Month");
						}
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
		    	}
		    	
				String conditionDetails_OTC ="";
		    	String conditionDetails_ETC ="";
		    	String condition_DuNoDauKy="";
		    	
		    	String conditionDetails_OTC2 ="";
		    	String conditionDetails_ETC2 ="";
		    	String condition_DuNoDauKy2="";
		    	
		    	String Search_ddkd="";
		    	String Search_kh="";
		    	String Search_npp="";
		    	String Search_ddkd_DNDK="";
		    	 if(ddkdId.trim().length()>0)
				   {
		    		 Search_ddkd=
						"		  and hd.KHACHHANG_FK in \n"+
						"		  						( \n"+
						"			 						SELECT  c.KHACHHANG_FK \n"+ 
						"									FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+   			
						"											inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"									WHERE a.PK_SEQ ='"+ddkdId+"'  \n"+
						"		  						) \n";
		    		 Search_ddkd_DNDK= " and dnkh.ddkd_fk='"+ddkdId+"' ";
				   }
		    	 
		    	 if(KhachHangid.trim().length() >0){
		    		 Search_kh += " 						\n	and kh.PK_SEQ in ("+KhachHangid+") \n";
				 }
		    	 
					
		    	 if(nppId.trim().length()>0)
		    	 {
						Search_npp+= " 						\n  and kh.NPP_FK ='"+nppId+"' \n";
				 }
		    	 
				
				query = 
				" SELECT kh.maFAST, kh.TEN, isnull(THOIHANNO, 90) as thoihanNO, isnull(ndk.tongtienAVAT, 0) as nodauky, \n" +
				"	 isnull(psn.tongtienAVAT, 0) as phatsinhno, isnull(psc.sotienTT, 0) as phatsinhco, isnull(dcdk.SOTIENTT,0) as dcdk, \n"+
				" 	 isnull(no_trong_han.tongtienAVAT, 0) as notronghan, \n"+
				" 	 isnull(co_trong_han.SOTIENTT, 0) as cotronghan, \n"+
				" 	 isnull(no_qua_han.tongtienAVAT, 0) as noquahan, \n"+
				" 	 isnull(co_qua_han.SOTIENTT, 0) as coquahan, \n" +				
				" 	 isnull(no_qua_han_1_60.tongtienAVAT, 0) as no_qua_han_1_60, \n"+
				" 	 isnull(co_qua_han_1_60.SOTIENTT, 0) as co_qua_han_1_60, \n"+
				" 	 isnull(no_qua_han_61_120.tongtienAVAT, 0) as no_qua_han_61_120, \n"+ 
				" 	 isnull(co_qua_han_61_120.SOTIENTT, 0) as co_qua_han_61_120, \n"+
				"	 isnull(no_qua_han_121_180.tongtienAVAT, 0) as no_qua_han_121_180, \n"+ 
				"	 isnull(co_qua_han_121_180.SOTIENTT, 0) as co_qua_han_121_180, \n"+
				"	 isnull(no_qua_han_tren_180.tongtienAVAT, 0) as no_qua_han_tren_180, \n"+
				"	 isnull(co_qua_han_tren_180.SOTIENTT, 0) as co_qua_han_tren_180, \n"+
				"		( \n" +
		    	"			SELECT  top(1) isnull(a.TEN,'') DDKD FROM 	DAIDIENKINHDOANH a INNER JOIN TUYENBANHANG b on b.DDKD_FK = a.PK_SEQ    \n" +
		    	"					inner join KHACHHANG_TUYENBH c on c.TBH_FK = b.PK_SEQ  " + ( ddkdId.trim().length() > 0 ? " and a.pk_seq = '" + ddkdId.trim() + "' " : "" ) +
		    	"			WHERE c.KHACHHANG_FK = kh.PK_SEQ \n" +
		    	"		) as DDKD \n " +
				" FROM 	KHACHHANG kh left join \n"+	
				"		( \n"+
				"			SELECT psn_dk.PK_SEQ KHACHHANG_FK, SUM(ISNULL(psn_dk.tongtienAVAT,0)) as tongtienAVAT   \n" +
				"			FROM   \n" +
		    	"				(	  \n" +
		    	"					SELECT psn.PK_SEQ, sum(tongtienAVAT) as tongtienAVAT   \n" +
		    	"					FROM   \n" +
		    	"						   (   \n" +
		    	"								SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT   \n" +
		    	"								FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
		    	"								WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '" + tuNgay + "' ";
				
		    	if(nppId.trim().length()>0) //DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
		    		query += "and kh.NPP_FK = '" + nppId + "'  \n";
		    	
	    		query+=			    			
		    	"            					GROUP BY hd.KHACHHANG_FK   \n" +
		    	"							UNION ALL \n" +	    
		    	"								SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(isnull(avat,0)) tongtienAVAT   \n" +
		    	"								FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
		    	"								WHERE 	1=1  and hd.TRANGTHAI = 1 and hd.NGAYHOADON < '" + tuNgay + "' ";
	    		
		    	if(nppId.trim().length()>0) //DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
		    		query += "and kh.NPP_FK = '" + nppId + "'  \n";
		    	
	    		query+=			    			
		    	"            					GROUP BY hd.KHACHHANG_FK   \n" +
		    	"							UNION ALL \n" +
		    	"								SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT   \n" +
		    	"								FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK    \n" +
		    	"								WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '" + tuNgay + "' " ;	    		
		    	if(nppId.trim().length()>0) //DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
		    			query += " 	and btcn.NPP_FK = '" + nppId + "' ";
		    	
		    	query+=
		    	"								GROUP BY btcn_hd.KHACHHANG_FK   \n" +
		    	"							UNION ALL	  \n" +
		    	"								SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat   \n" +
		    	"								FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"										inner join (  \n" +
		    	"													SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   \n" +
		    	"													FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"													WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
		    	"													GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"													)   \n" +
		    	"													bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	"					 					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
		    	"					 			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'" + tuNgay + "' " ;
		    	if(nppId.trim().length()>0) // DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
			    	query += "	and kh.NPP_FK ='" + nppId + "'   \n";
		    	
		    	query+=
		    	"							UNION ALL	  \n" +
		    	"								SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat   \n" +
		    	"								FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"										INNER JOIN (   " +
		    	"													SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   \n" +
		    	"													FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"													WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
		    	"													GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"													)   \n" +
		    	"										bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	"					 					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
		    	"								WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'" + tuNgay + "' ";
		    	if(nppId.trim().length()>0) // DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
			    	query += "and kh.NPP_FK ='" + nppId + "'   \n";
		    	
		    	query+=
		    	"							UNION ALL   \n" +
		    	"								SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat    \n" +
		    	"								FROM   \n" +
		    	"									(   \n" +
		    	"										SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,   \n" +
		    	"												sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      \n" +
		    	"												sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,    \n" +
		    	"												sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   \n" +
		    	"												round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             " +
		    	"												sum(isnull(thuexuat,0)) as BVAT_CK       \n" +
		    	"										FROM (     \n" +
		    	"												SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   " +
		    	"														(     \n" +
		    	"															SELECT top(1) bb.DDKD_FK     \n" +
		    	"															FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK     \n" +
		    	"															WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n" +
		    	"														) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,    \n" +
		    	"														case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n" +
		    	"												FROM  ERP_HOADONNPP a     \n" +
		    	"							 	 					  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    \n" +
		    	"							  						  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n" +
		    	"							  						  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n" +
		    	"												WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'" + tuNgay + "'   \n" +
		    	"											 )ETC									  " +
		    	"										GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								  \n" +
		    	"									)   \n" +
		    	"									hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK   \n" +
		    	"					 				LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n" +
		    	"								WHERE 1=1 ";
		    	if(nppId.trim().length()>0) // DÙNG CHO PAGE TRUNG TÂM NẾU CHỌN NHÀ PHÂN PHỐI
			    	query += "and hd.NPP_FK ='" + nppId + "'   \n";
		    	
		    	query+=
		    	"            					GROUP BY hd.khachhang_fk   \n" +
		    	"							)   \n" +
		    	"							psn   \n" +
		    	"					GROUP BY psn.PK_SEQ   \n" +
		    	"					UNION ALL   \n" +
		    	"					SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT   \n" +
		    	"					FROM   	DUNO_KHACHHANG dnkh   \n" +
		    	"							LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n" +
		    	"							LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n" +
		    	"							LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK   \n" +
		    	"	    			WHERE 	1=1 and dnkh.NgayDuNo < '" + tuNgay + "' and dnkh.SONO >= 0  ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and  dnkh.NPP_FK='" + nppId + "'			";	
								    	
		    	query+=	
		    	"					GROUP BY dnkh.KHACHHANG_FK   \n" +
		    	"					)   \n" +
		    	"					psn_dk   \n" +
		    	"				GROUP BY psn_dk.PK_SEQ   \n" +
		    	"	)  \n" +
				"	ndk on kh.pk_seq = ndk.khachhang_fk \n " +				
				"	LEFT JOIN \n"+								
			    "	( \n"+
			    "		SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT   \n" +
		    	"		FROM   \n" +
		    	"			(   \n" +
		    	"				SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
		    	"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"						INNER JOIN (   " +
		    	"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   " +
		    	"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   " +
		    	"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '" + tuNgay + "' and  tthd.LOAIHD = 0 \n" +
		    	"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	" 								   ) 	  \n" +
		    	" 									tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   \n" +
		    	"				WHERE 	1=1 ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and kh.NPP_FK = '" + nppId + "'   \n";		
		    	
		    	query+=
		    	"				GROUP BY hd.KHACHHANG_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT   \n" +
		    	"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   \n" +
		    	"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '" +tuNgay + "'   \n";
		    	if(nppId.trim().length()>0)
		    		query+=	"		  and btcn.NPP_FK='" + nppId + "' \n";
		    	
		        query+=
		    	"  				GROUP BY btcn_hd.KHACHHANG_FK   \n" +
		    	"			UNION ALL  \n" +
		    	"				SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
		    	"				FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"	 				   INNER JOIN   \n" +
		    	"	 					(   \n" +
		    	"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   \n" +
		    	"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
		    	"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" + tuNgay + "' and tthd.LOAIHD = 2 ";
		    	if(	nppId.trim().length()>0)
		    		query+=	"			and tt.NPP_FK = '" + nppId + "'						  \n";
		    	
		    	query+=			    						
		    	"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	"		 			)  \n" +
		    	"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK   \n" +
		    	"		 		WHERE 1=1 " ;
		    	if(nppId.trim().length()>0)
		    		query+=	" and kh.NPP_FK = '" + nppId + "'   \n" ;
		    	
		    	query+=
		    	"		 		GROUP BY dnkh.KHACHHANG_FK   \n" +
		    	"			UNION ALL	 \n" +
		    	"				SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		  \n" +
		    	"				FROM   	Erp_HangTraLaiNpp  tttl				 \n" +
		    	"				WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '" + tuNgay + "' and KHACHHANG_FK is not null ";
		    	if(nppId.trim().length()>0)
		    		query+="		and tttl.NPP_FK = '" + nppId + "' 	  \n";
		    	
		    	query+=		
		    	"				GROUP BY tttl.KHACHHANG_FK   \n" +
		    	"			UNION ALL	  \n" +
		    	"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   \n" +
		    	"				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  \n" +
		    	"			   			INNER JOIN (   \n" +
		    	" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   \n" +
		    	" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
		    	" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" + tuNgay + "' and tthd.LOAIHD = 0  \n" +
		    	" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	"  									)   \n" +
		    	"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK   \n" +
		    	"				WHERE 1=1 ";
		    	if(nppId.trim().length()>0)
		    		query+="and kh.NPP_FK = '" + nppId + "'   \n";
		    	
		    	query+=
		    	"				GROUP BY  hd.KHACHHANG_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   \n" +
		    	"				FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ  \n" +
		    	"			   			INNER JOIN (   \n" +
		    	" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   \n" +
		    	" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
		    	" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '" + tuNgay + "' and tthd.LOAIHD = 1  \n" +
		    	" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	"  									)   \n" +
		    	"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK   \n" +
		    	"				WHERE 1=1 ";
		    	
		    	if(nppId.trim().length()>0)
		    		query+="and kh.NPP_FK = '" + nppId + "'   \n";
		    	query+=
		    	"				GROUP BY  hd.KHACHHANG_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA   \n" +
		    	"				FROM   	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   \n" +
		    	"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 )   \n" +
		    	"				WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '" + tuNgay+ "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and xnkh.NPP_FK = '" + nppId + "'		  \n";
		    	
		    	query+=			    			
		    	"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
		    	"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
		    	"						INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0  )   \n" +
		    	"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '" + tuNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and xnkh.NPP_FK = '" + nppId + "'		  \n";
		    	
		    	query+=
		    	"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
		    	"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
		    	"						INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2 )   \n" +
		    	"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '" + tuNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and xnkh.NPP_FK = '" + nppId + "'		  \n";
		    	
		    	query+=			    			
		    	"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
		    	"		  	UNION ALL   \n" +
		    	"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
		    	"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
		    	"						INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 )   \n" +
		    	"				WHERE 	1=1 and xnkh.TRANGTHAI = 1 and XNKH.NGAYCHUNGTU < '" + tuNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and xnkh.NPP_FK = '" + nppId + "'		  \n";
		    	
		    	query+=			    			
		    	"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
		    	"			UNION ALL   \n" +
		    	" 				SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
		    	"  				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"   					INNER JOIN (   \n" +
		    	"	  								 SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
		    	"	  								 FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"	  								 WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
		    	"	  								 GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"	 					   			)   \n" +
		    	"	 								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	" 				WHERE 	1=1 and hd.NGAYXUATHD < '" + tuNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and kh.NPP_FK = '" + nppId + "'   \n";
		    	
		    	query+=				    			
		    	" 				GROUP BY hd.KHACHHANG_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
		    	"  				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"   					INNER JOIN (   \n" +
		    	"	  								 SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
		    	"	  								 FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"	  								 WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
		    	"	  								 GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"	 								)   \n" +
		    	"					 				bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	" 				WHERE 	1=1 and hd.NGAYXUATHD < '" + tuNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and kh.NPP_FK = '" + nppId + "'   \n";
		    	
		    	query+=				    			
		    	" 				GROUP BY hd.KHACHHANG_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT   \n" +
		    	"				FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ   \n" +
		    	"				WHERE 	1 =1 and tt.NGAYCHUNGTU < '" + tuNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and ttna.NPP_FK = '" + nppId + "'   	  \n";
		    	
		    	query+=				    			
		    	"				GROUP BY ttna.KHACHHANG_FK   \n" +
		    	"			UNION ALL   \n" +
		    	"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT  \n" +
		    	"				FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   \n" +
		    	"						INNER JOIN (   \n" +
		    	"									 SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU   \n" +
		    	"									 FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   \n" +
		    	"									 WHERE  ct.TRANGTHAI = 1   \n" +
		    	"									 GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK   \n" +
		    	"									)   \n" +
		    	"									ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK   \n" +
		    	"				WHERE 	1=1 and hd.NGAYXUATHD <'" + tuNgay + "'  " ;
		    	if(nppId.trim().length()>0)
		    		query+=	"and hd.NPP_FK='" + nppId + "'	  \n";
		    	
		    	query+=					    			
		    	"				GROUP BY hd.KHACHHANG_FK   \n" +			    	
				"   		UNION ALL \n"+ 
				"				SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT   \n"+ 
				"				FROM   	DUNO_KHACHHANG dnkh   \n"+ 
				"						LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n"+ 
				"						LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n"+ 
				"						LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK   \n"+ 
			    "				WHERE 	1=1 and dnkh.NgayDuNo < '" + tuNgay+ "' and dnkh.SONO < 0 ";
			    if(nppId.trim().length()>0)
		    		query+=	" and  dnkh.NPP_FK = '"+ nppId +"' \n";
			    
		    	query+=					    					
			    "		GROUP BY dnkh.KHACHHANG_FK  \n"+ 
		    	"	)   " +
		    	"	psc group by psc.KHACHHANG_FK   \n" +
			    " ) dcdk on kh.PK_SEQ = dcdk.KHACHHANG_FK \n";
		    	
		    	
		    	query+=
			    "LEFT JOIN \n"+
			    
			    "(   \n" +
		    	"	SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT   \n" +
		    	"	FROM   \n" +
		    	"	(   \n" +
		    	"		SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT   \n" +
		    	"		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
		    	"		WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '" + tuNgay + "' and hd.NGAYXUATHD <= '" + denNgay+ "' \n";
		    	if(nppId.trim().length()>0)
		    		query+=	"	and kh.NPP_FK ='" + nppId + "'   \n";
		    	
		    	query+=
		    	"       GROUP BY hd.KHACHHANG_FK   \n" +
		    	"	UNION ALL  \n" +
		    	"		SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT   \n" +
		    	"		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK    \n" +
		    	"		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '" + tuNgay + "' and btcn.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and btcn.NPP_FK='" + nppId + "'                                 \n";
		    	query+=		
		    	"		GROUP BY btcn_hd.KHACHHANG_FK   \n" +
		    	"	UNION ALL	  \n" +
		    	"		SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat   \n" +
		    	"		FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"				inner join (   " +
		    	"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   \n" +
		    	"								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
		    	"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"							)   \n" +
		    	"							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	"				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
		    	"		WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD >= '" + tuNgay + "' and hd.NGAYXUATHD <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	" and kh.NPP_FK ='" + nppId + "'   \n";
		    	query+=				    			
		    	"	UNION ALL	  \n" +
		    	"		SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat   \n" +
		    	"		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"				INNER JOIN (   \n" +
		    	"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat  \n" +
		    	"								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"								WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   \n" +
		    	"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"							)   \n" +
		    	"							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	"				 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n" +
		    	"		WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '" + tuNgay+ "' and hd.NGAYXUATHD <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	" and kh.NPP_FK ='" + nppId + "'   \n";
		    	query+=				    			
		    	"	UNION ALL   \n" +
		    	"		SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat    \n" +
		    	"		FROM   \n" +
		    	"		(   \n" +
		    	"			SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,   \n" +
		    	"					sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       \n" +
		    	"					sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT,    \n" +
		    	"					sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  \n" +
		    	"					round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,            \n" +
		    	"					sum(isnull(thuexuat,0)) as BVAT_CK       \n" +
		    	"			FROM (     \n" +
		    	"					SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   \n" +
		    	"						(     \n" +
		    	"							SELECT top(1) bb.DDKD_FK     \n" +
		    	"							FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK     \n" +
		    	"							WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n" +
		    	"						) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong,    \n" +
		    	"							case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n" +
		    	"					FROM  ERP_HOADONNPP a     \n" +
		    	"						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    \n" +
		    	"						  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n" +
		    	"						  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n" +
		    	"					WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '" + tuNgay + "' and a.NGAYXUATHD <= '" + denNgay + "'   \n" +
		    	"				)ETC									  " +
		    	"			GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk								  \n" +
		    	"		)   \n" +
		    	"		hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK   \n" +
		    	"				  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n" +
		    	"		WHERE 1=1 ";
		    	if(nppId.trim().length()>0)
		    		query+=	"	and hd.NPP_FK ='" + nppId + "'   \n";
		    	query+=				    	
		    	"        GROUP BY hd.khachhang_fk   \n" +
		    	" \n"+
		    	"	UNION ALL \n"+
		    	"   	 SELECT a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienavat   \n"+
		    	"   	 FROM 	ERP_HoaDonPheLieu a \n"+
		    	"   	 WHERE 	1 = 1 AND trangthai = 1 AND a.ngayhoadon >= '"+tuNgay+"' and a.ngayhoadon <= '"+denNgay+"' \n ";
		    	if(nppId.trim().length()>0)
		    		query+=	" 	and a.npp_fk = '"+nppId+"' \n";
		    	
		    	query+=
		    	"   GROUP BY a.khachhang_fk \n"+ 
		    	"	)   \n" +
		    	"	psn   \n" +
		    	"	GROUP BY psn.PK_SEQ   \n" +
			    ") psn ";
		    	
		    	query+=
				" on kh.pk_seq = psn.KHACHHANG_FK " +
				" LEFT JOIN \n"+
				
				"(   \n" +
		    	"	SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT  \n" +
		    	"	FROM   \n" +
		    	"	(   \n" +
		    	"		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
		    	"		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"				INNER JOIN (   \n" +
		    	"  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   \n" +
		    	"  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
		    	"  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '" +tuNgay + "'  and tt.NGAYCHUNGTU <= '" + denNgay + "' and tthd.LOAIHD = 0  \n" +
		    	"  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	" 							) 	  \n" +
		    	" 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   \n"+
		    	"		WHERE 	1=1 ";
		    	if(nppId.trim().length()>0)
		    		query+=	"		and kh.NPP_FK = '" + nppId + "'   \n";
		    	
		    	query+=				    	
		    	"		GROUP BY hd.KHACHHANG_FK   \n" +
		    	"	UNION ALL   \n" +
		    	"		SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
		    	"		FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"				INNER JOIN (   \n" +
		    	"  								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   \n" +
		    	"  								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
		    	"  								WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '" +tuNgay + "'  and tt.NGAYCHUNGTU <= '" + denNgay + "' and tthd.LOAIHD = 1  \n" +
		    	"  								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	" 							) 	  \n" +
		    	" 							tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   \n"+
		    	"		WHERE 	1=1 ";
		    	if(nppId.trim().length()>0)
		    		query+=	"		and kh.NPP_FK = '" + nppId + "'   \n";
		    	query+=				    	
		    	"		GROUP BY hd.KHACHHANG_FK   \n" +
		    	"	UNION ALL   \n" +
		    	"		SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT   \n" +
		    	"		FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK   \n" +
		    	"		WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '" + tuNgay + "' and btcn.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and btcn.NPP_FK='" + nppId + "'   \n";
		    	
		    	query+=				    			
		    	"		GROUP BY btcn_hd.KHACHHANG_FK   \n" +
		    	"	UNION ALL  \n" +
		    	"		SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT   \n" +
		    	"		FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"	 				INNER JOIN   \n" +
		    	"	 				(   \n" +
		    	"						SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT   \n" +
		    	"						FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK and tthd.LOAIHD = 2  \n" +
		    	"						WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '" + tuNgay + "' and tt.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and tt.NPP_FK = '" + nppId + "'   \n";
		    	
		    	query+=
		    	"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	"		 			) \n" +
		    	"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK   \n" +
		    	"		 WHERE 1=1 ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and kh.NPP_FK ='" + nppId + "'   \n";
		    	
		    	query+=				    	
		    	"		 GROUP BY dnkh.KHACHHANG_FK   \n" +
		    	"	UNION ALL	  \n" +
		    	"		SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT 		 \n" +
		    	"		FROM   	Erp_HangTraLaiNpp  tttl				 \n" +
		    	"		WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '" +tuNgay + "' and tttl.NGAYTRA <= '" + denNgay + "' and KHACHHANG_FK is not null ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and tttl.NPP_FK = '" + nppId + "' 	  \n";
		    	
		    	query+=				    			
		    	"		GROUP BY tttl.KHACHHANG_FK   \n" +
		    	"	UNION ALL	 \n" +
		    	"		SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT   \n" +
		    	"		FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ   \n" +
		    	"			   INNER JOIN (   \n" +
		    	" 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT   \n" +
		    	" 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +
		    	" 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '" + tuNgay + "' and tt.NGAYCHUNGTU <= '" + denNgay + "' and  tthd.LOAIHD = 0 \n" +
		    	" 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n" +
		    	"  							)   \n" +
		    	"  							tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK  \n" +
		    	"		WHERE 1=1 ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and kh.NPP_FK = '" + nppId + "'   \n";
		    	
		    	query+=	
		    	"		GROUP BY  hd.KHACHHANG_FK   \n" +
		    	"	UNION ALL   \n" +
		    	"		SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA   \n" +
		    	"		FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   \n" +
		    	"		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 )   \n" +
		    	"		WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + tuNgay + "' and XNKH.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+=	"and xnkh.NPP_FK='" + nppId + "'		  \n";
		    	
		    	query+=				    			
		    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   " +
		    	"	UNION ALL   " +
		    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
		    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK   \n" +
		    	"				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD =0 )   \n" +
		    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + tuNgay + "' and XNKH.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+="and xnkh.NPP_FK='" + nppId + "'		 \n";
		    	
		    	query+=				    			
		    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +
		    	"   UNION ALL  " +
		    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
		    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
		    	"				INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2)   \n" +
		    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + tuNgay + "' and XNKH.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+="and xnkh.NPP_FK='" + nppId + "'		 \n";
		    	
		    	query+=				    			
		    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +		    	
		    	"   UNION ALL  " +
		    	"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n" +
		    	"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n" +
		    	"				INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1)   \n" +
		    	"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '" + tuNgay + "' and XNKH.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+="and xnkh.NPP_FK='" + nppId + "'		 \n";
		    	
		    	query+=				    			
		    	"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n" +		    	
		    	"	UNION ALL   \n" +
		    	" 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
		    	"  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"   				INNER JOIN (   \n" +
		    	"	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
		    	"	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"	  						WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
		    	"	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"	 					   )   \n" +
		    	"	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	" 		WHERE 	1=1 and hd.NGAYXUATHD >= '" + tuNgay + "' and hd.NGAYXUATHD <= '" + denNgay+ "' ";
		    	if(nppId.trim().length()>0)
		    		query+="and kh.NPP_FK = '" + nppId + "'   ";
		    	
		    	query+=				    			
		    	" 		GROUP BY hd.KHACHHANG_FK   \n" +
		    	"	UNION ALL   \n" +
		    	"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT   \n" +
		    	"  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n" +
		    	"   				INNER JOIN (   \n" +
		    	"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO   \n" +
		    	"	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n" +
		    	"	  							WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n" +
		    	"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n" +
		    	"	 						)   \n" +
		    	"					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK   \n" +
		    	" 		WHERE 	1=1 and hd.NGAYXUATHD >= '" + tuNgay + "' and hd.NGAYXUATHD <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+="and kh.NPP_FK = '" + nppId + "'   ";
		    	
		    	query+=				    			
		    	" 		GROUP BY hd.KHACHHANG_FK   \n" +
		    	"  \n" +
		    	"	UNION ALL   \n" +
		    	"		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT   \n" +
		    	"		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ   " +
		    	"		WHERE 1 =1 and tt.NGAYCHUNGTU >= '" + tuNgay + "' and tt.NGAYCHUNGTU <= '" + denNgay + "' ";
		    	if(nppId.trim().length()>0)
		    		query+="and ttna.NPP_FK = '" + nppId + "'   	  \n";
		    	
		    	query+=				    			
		    	"		GROUP BY ttna.KHACHHANG_FK   \n" +
		    	" 	UNION ALL \n"+
		    	"		SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+   
		    	"		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+   
		    	"				INNER JOIN ( \n"+   
		    	"								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+   
		    	"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+   
		    	"								WHERE  ct.TRANGTHAI = 1 \n"+   
		    	"								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+   
		    	"							) \n"+   
		    	"							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+   
		    	"		WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
		    	if(nppId.trim().length()>0)
		    		query+="and hd.NPP_FK = '" + nppId+ "'	  \n";
		    	
		    	query+=
		    	"		GROUP BY hd.KHACHHANG_FK \n"+   
		    	"	)   " +
		    	"	psc group by psc.KHACHHANG_FK   \n" +
		    	")   \n" +
				    "psc" +			    
				    
				" on kh.pk_seq = psc.khachhang_fk ";

		    
		    	query+=
				
				"LEFT JOIN \n"+
				
				" ( \n"+ 
				"		SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT \n"+   
				"		FROM \n"+   
				"		( \n"+  		
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query += "   and kh.NPP_FK = '"+nppId+"' \n";  
				query +=
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+   
				"		UNION ALL \n " +	
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(isnull(avat,0)) tongtienAVAT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1  and hd.TRANGTHAI = 1 and hd.NGAYHOADON < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query += "  and kh.NPP_FK = '"+nppId+"' \n";  
				
				query+=
				"				and Datediff(dd,  hd.NGAYHOADON, GETDATE()) <= kh.THOIHANNO \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+   
				"		UNION ALL \n"+ 
						
				"			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
				"					inner join KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+    
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"'  \n"; 
				if(nppId.trim().length()>0)
					query +=" and btcn.NPP_FK = '"+nppId+"' \n";
				
				query +=
				"			  and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n"+							
				"			GROUP BY btcn_hd.KHACHHANG_FK \n"+ 													
				"		UNION ALL \n"+	 
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					inner join ( \n"+  
				"									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
				"									WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 \n"+   
				"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+   
				"								) \n"+   
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+   
				"			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query	+= " and kh.NPP_FK ='"+nppId+"'"; 
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+	 
				"		UNION ALL \n"+	  													
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (  \n" +
				"									SELECT 	bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat   \n"+
				"									FROM   	BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
				"									WHERE 	bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 \n"+   
				"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+   
				"								) \n"+   
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+   
				"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	" and kh.NPP_FK ='"+nppId+"' \n"; 
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+	
				"		UNION ALL \n"+  
				"			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"			FROM \n"+   
				"				( \n"+   
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia, \n"+      
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT, \n"+   
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK, sum(isnull(thuexuat,0)) as BVAT_CK \n"+        
				"					FROM ( \n"+     
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,  \n" +
				"							(  \n"+   
				"									SELECT top(1) bb.DDKD_FK \n"+    
				"									FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"									WHERE aa.HOADONNPP_FK=c.HOADON_FK \n"+    
				"								) as ddkd_fk , \n" +
				"								case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"								case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia \n"+       
				"						FROM  ERP_HOADONNPP a \n"+   
				"						      INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"	 	 					  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+    
				"	  						  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq \n"+    
				"	  						  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+    
				"						WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'"+tuNgay+"' \n"+   
				"						and Datediff(dd, a.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+
				"					 )ETC \n"+									  	
				"					 GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				) " +				   
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"				LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK \n"+   
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query += "and hd.NPP_FK ='"+nppId+"' \n";   
				query+=	
				"			GROUP BY hd.khachhang_fk \n"+ 
						
				"		UNION ALL \n"+ 
				"			SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+   
				"			FROM   	DUNO_KHACHHANG dnkh \n"+   
				"					INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK = kh.PK_SEQ \n"+
				"					LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+   
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK \n"+   
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+ 			  
				"			WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"' and dnkh.SONO >= 0  ";
				if(nppId.trim().length()>0)
					query += " and  dnkh.NPP_FK='"+nppId+"' \n";
				
				query+=				
				"				and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= kh.THOIHANNO \n"+
				"			GROUP BY dnkh.KHACHHANG_FK \n"+   
							
				// het du no dau ky
							
				"		UNION ALL \n"+						
				"			SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"			FROM   HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE  1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) \n"+			   
				"				   and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query += "  and kh.NPP_FK ='"+nppId+"' \n"; 
				
				query+=	
				"			 		and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+ 
				"	    	GROUP BY hd.KHACHHANG_FK \n"+ 
					         
				"		UNION ALL \n"+  
				"			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
				"			        INNER JOIN KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+ 
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 \n"+ 
				"					and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	" 	and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=					
				"					and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) <= kh.THOIHANNO \n"+                               
				"			GROUP BY btcn_hd.KHACHHANG_FK \n"+   
							
				"		UNION ALL \n"+	  
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					inner join (   	SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
				"									WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 \n"+   
				"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+   
				"								) \n"+   
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+   
				"			WHERE  	 1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and \n"+ 
				"			       	 hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length() >0)
					query +="     	 and kh.NPP_FK ='"+nppId+"' \n"; 
				
				query+=
				"					 and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+							       
				"		UNION ALL \n"+	  
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN ( \n"+   
				"									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+  
				"									FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
				"									WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1 \n"+   
				"									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+   
				"								) \n"+   
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+   
				"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' \n";
				if(nppId.trim().length() >0)
					query += "			        and kh.NPP_FK ='"+nppId+"' \n";
				
				query +=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) <= kh.THOIHANNO \n"+
				"		UNION ALL \n"+   
				"			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"			FROM \n"+   
				"			( \n"+   
				"				SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"						sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia, \n"+       
				"						sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"						sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT, \n"+  
				"						round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK, \n"+            
				"						sum(isnull(thuexuat,0)) as BVAT_CK \n"+       
				"				FROM ( \n"+     
				"						SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat, \n"+   
				"							( \n"+     
				"								SELECT top(1) bb.DDKD_FK \n"+     
				"								FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"								WHERE aa.HOADONNPP_FK=c.HOADON_FK \n"+    
				"							) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"								case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia \n"+       
				"						FROM  ERP_HOADONNPP a \n"+     
				"							  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+    
				"							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq \n"+    
				"							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+ 
				"							  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"						WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '"+tuNgay+"' and a.NGAYXUATHD <= '"+denNgay+"' \n"+ 
				"						      and Datediff(dd, a.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+
				"					)ETC \n"+									  			
				"					GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"			) \n"+   
				"			hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"				  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK \n"+   
				"			WHERE 1=1 ";
				if(nppId.trim().length() > 0)
					query+=" 		and hd.NPP_FK ='"+nppId+"' \n";   
				query +=
				"			GROUP BY hd.khachhang_fk   \n"+					 
				"		UNION ALL \n"+ 
				"		   SELECT	a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienavat \n"+   
				"		   FROM		ERP_HoaDonPheLieu a inner join KHACHHANG kh on a.khachhang_fk = kh.PK_SEQ \n"+
				"		   WHERE	1 = 1 AND a.trangthai = 1 AND a.ngayhoadon >= '"+tuNgay+"' and a.ngayhoadon <= '"+denNgay+"' \n";
				if(nppId.trim().length() >0)
					query += "					and a.npp_fk = '"+nppId+"' ";
				query += "					and Datediff(dd,  a.ngayhoadon, GETDATE()) <= kh.THOIHANNO \n"+
				"		   GROUP BY a.khachhang_fk \n"+ 
				"		) \n"+   
				"		psn \n"+   
				"		GROUP BY psn.PK_SEQ \n"+ 
				"	 ) \n"+ 
				"	 no_trong_han  on kh.pk_seq = no_trong_han.khachhang_fk left join \n";
				
				query+=
				" ( \n"+   
				"		SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+  
				"		FROM   \n" +				
				"		(   \n"+				
				"			---- dư nợ có quá hạn \n"+	
				"				SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and  tthd.LOAIHD = 0 \n"+
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								   ) 	  \n"+
				" 									tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"				WHERE 	1=1  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"; 
				if(nppId.trim().length()>0)
					query += " 	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"				GROUP BY hd.KHACHHANG_FK \n"+   
				"			UNION ALL   \n"+
				"				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ =  btcn_hd.KHACHHANG_FK \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' \n"+   
				"    					 and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+= " and btcn.NPP_FK='"+nppId+"'";
				
				query+=
				"  				GROUP BY btcn_hd.KHACHHANG_FK \n"+				
				"			UNION ALL \n"+  
				"				SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 				   INNER JOIN   \n"+
				"	 					( \n"+   
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 2 \n";
				if(nppId.trim().length()>0)
					query+=	"		and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" \n"+			    						
				"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+   
				"		 			) \n"+  
				"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"		 		WHERE 1=1  and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+="			 and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" \n"+
				"		 		GROUP BY dnkh.KHACHHANG_FK \n"+   
				"			UNION ALL	 \n"+
				"				SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		  
				"				FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on tttl.khachhang_fk = kh.PK_SEQ \n"+
				"				WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '"+tuNgay+"' and KHACHHANG_FK is not null \n";
				if(nppId.trim().length()>0)
					query+= "				and tttl.NPP_FK = '"+nppId+"' 	";
				
				query+=
				"				and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= kh.THOIHANNO \n"+			
				"				GROUP BY tttl.KHACHHANG_FK \n"+
				"			UNION ALL	  \n"+
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"			   			INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK    \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 0  \n"+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  									)   \n"+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+   
				"				WHERE 1=1 and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+=	"						and kh.NPP_FK = '"+nppId+"'   \n";
				
				query +=			
				"				GROUP BY  hd.KHACHHANG_FK   \n"+
			
				"			UNION ALL   \n"+
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"				FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"			   			INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +				
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 1  \n"+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  									)   \n"+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+   
				"				WHERE 1=1 and Datediff(dd, hd.ngayhoadon, GETDATE()) <= kh.THOIHANNO \n";
				
				if(nppId.trim().length()>0)
					query+= "				and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"				GROUP BY  hd.KHACHHANG_FK \n"+
				"			UNION ALL \n"+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"				FROM   	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+   
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"				WHERE  	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' \n";
				if(nppId.trim().length()>0)
					query+= "			and xnkh.NPP_FK = '"+nppId+"'	";
				
				query+=
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n"+	  		
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"			UNION ALL   \n"+
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"						INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0  ) \n"+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n";	  
				if(nppId.trim().length()>0)
					query+= "		and xnkh.NPP_FK = '"+nppId+"' \n";		  
				
				query+=
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"			UNION ALL \n"+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"						INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2 ) \n"+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK				\n"+
				"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"'  and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n";
				
				if(nppId.trim().length()>0)
					query+= "		and xnkh.NPP_FK = '"+nppId+"'	\n";
				
				query+=		    			
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"			UNION ALL \n"+  
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"						INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 ) \n"+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"				WHERE 	1=1 and xnkh.TRANGTHAI = 1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+= "						and xnkh.NPP_FK = '"+nppId+"' \n";
				
				query+=			    			
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"			UNION ALL   \n"+
				"				SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  									FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  									WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 					   			)   \n"+
				"	 					bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"				WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n";	  
				
				if(nppId.trim().length()>0)
					query += "		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=								    			
				" 				GROUP BY hd.KHACHHANG_FK   \n"+				  
				"			UNION ALL   \n"+
				"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  									WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 								)   \n"+
				"					 	bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 				WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
						query+= "	and kh.NPP_FK = '"+nppId+"'   \n";
				
				query+=	    			
				" 				GROUP BY hd.KHACHHANG_FK \n"+  
				"			UNION ALL   \n"+
				"				SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"				FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"				WHERE 1 =1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+= "	and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=	    			
				"				GROUP BY ttna.KHACHHANG_FK \n"+   
				"			UNION ALL   \n"+
				"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT \n"+  
				"				FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   \n"+
				"						INNER JOIN (   \n"+
				"										SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n" +				   
				"										FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   \n"+
				"										WHERE  ct.TRANGTHAI = 1   \n"+
				"										GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK " +
				"									) \n"+   
				"						ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+   
				"				WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"'  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= k.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+="	and hd.NPP_FK='"+nppId+"'	 \n";
				
				query+=					    			
				"				GROUP BY hd.KHACHHANG_FK   	\n"+		    	
				"  			UNION ALL  \n"+
				"				SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT \n"+    
				"				FROM   	DUNO_KHACHHANG dnkh    \n"+
				"						LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+    
				"						LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    \n"+
				"						LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK  \n"+
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ =  dnkh.KHACHHANG_FK \n"+ 
				"				WHERE 	1=1 and dnkh.NgayDuNo < ' "+tuNgay+" ' and dnkh.SONO < 0 and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= kh.THOIHANNO \n";
				
				if(nppId.trim().length()>0)
					query+=	"	 		and  dnkh.NPP_FK = '"+nppId+"' \n";
				
				query+=			    					
				"				GROUP BY dnkh.KHACHHANG_FK \n"+ 
				"			UNION ALL \n"+
				"				---- \n"+
				"				SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						INNER JOIN (   \n"+
				"  										SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  										FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  										WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 0  \n"+
				"  										GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 									) 	  \n"+
				" 						tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"				WHERE 	1=1 ";
				if(nppId.trim().length()>0)
					query+= "and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO  \n"+ 
				"				GROUP BY hd.KHACHHANG_FK   \n"+
				"			UNION ALL   \n"+
				"				SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN (   \n"+
				"  										SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  										FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  										WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 1 \n"+ 
				"  										GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 									) 	  \n"+
				" 						tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"				WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+= "			and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"						and Datediff(dd, hd.ngayhoadon, GETDATE()) <= kh.THOIHANNO \n"+   
				"				GROUP BY hd.KHACHHANG_FK   \n"+
				"			UNION ALL \n"+   
				"				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT   \n"+
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"						INNER JOIN KHACHHANG kh on btcn_hd.KHACHHANG_FK = kh.PK_SEQ \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+=	"			and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        	and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO   \n"+
				"				GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"			UNION ALL  \n"+
				"				SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 					INNER JOIN   \n"+
				"	 					(   \n"+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK and tthd.LOAIHD = 2 \n"+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"					and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"		 				) \n"+
				"		 				tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			 	WHERE 1=1 ";				
				if(nppId.trim().length()>0)
					query+= "		and kh.NPP_FK ='"+nppId+"'  ";
				
				query+=
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= kh.THOIHANNO \n"+   
				"			 	GROUP BY dnkh.KHACHHANG_FK   \n"+
				"			UNION ALL \n"+	  
				"				SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		 
				"				FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on kh.PK_SEQ = tttl.khachhang_fk \n"+
				"				WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '"+tuNgay+"' and tttl.NGAYTRA <= '"+denNgay+"' \n"+ 
				"						and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+= "		and tttl.NPP_FK = '"+nppId+"' 	  \n";
				
				query+=
				"						and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= kh.THOIHANNO    \n"+
				"				GROUP BY tttl.KHACHHANG_FK   \n"+
				"			UNION ALL \n"+	 
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ    \n"+
				"				   	   INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' and  tthd.LOAIHD = 0 \n"+ 
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  								)   \n"+
				"  					   tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+  
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= "			and kh.NPP_FK = '"+nppId+"'  ";
				
				query+=
				" 					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+    
				"			GROUP BY  hd.KHACHHANG_FK  \n"+
				"		UNION ALL \n"+   
				"			SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"			FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   		   INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+ 
				"		   		   INNER JOIN KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
				"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO    \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   	\n"+
				"		UNION ALL \n"+   		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"					INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD =0 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  \n"+
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2) \n"+
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"'";
				if(nppId.trim().length()>0)
					query+=	" 		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n"+
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				
				if(nppId.trim().length()>0)
					query+= "		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO 	 \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"		UNION ALL \n"+   
				" 			SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO    \n"+
				"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 						   )   \n"+
				"	 							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO 	 \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 							)   \n"+
				"					 			bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)				
					query+=	"			and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= kh.THOIHANNO \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   		
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"			WHERE	1 =1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) <= kh.THOIHANNO    	  \n"+
				"			GROUP BY ttna.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+  		
				"			SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+ 
				"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
				"					INNER JOIN ( \n"+
				"									SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
				"									FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
				"									WHERE  ct.TRANGTHAI = 1 \n"+
				"									GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+ 
				"								) \n"+
				"								ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+ 								
				"			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "	and hd.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= k.THOIHANNO \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+
				"		)   	psc group by psc.KHACHHANG_FK \n"+   
				"	)   \n"+
				"	co_trong_han on kh.pk_seq = co_trong_han.khachhang_fk LEFT JOIN \n"; 
				
				query+=
				"	 ( \n"+
				"			SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT \n"+   
				"			FROM \n"+   
				"			( \n"+   
				"				--- nợ quá hạn đk ------ \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query += " and kh.NPP_FK = '"+nppId+"' \n";  
				query +=
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"				GROUP BY hd.KHACHHANG_FK \n"+   
														
				"			UNION ALL \n"+ 
							
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(isnull(avat,0)) tongtienAVAT \n"+   
				"				FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				WHERE 	1=1  and hd.TRANGTHAI = 1 and hd.NGAYHOADON < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+= " 	and kh.NPP_FK = '"+nppId+"' \n";  
				
				query+=	
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) > kh.THOIHANNO \n"+
				"				GROUP BY hd.KHACHHANG_FK \n"+ 
				"			UNION ALL \n"+ 							
				"				SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
				"						inner join KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+    
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					 query+=" and btcn.NPP_FK = '"+nppId+"' \n"; 
				
				query +=
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+							
				"				GROUP BY btcn_hd.KHACHHANG_FK \n"+   	
				"			UNION ALL \n"+	  							
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN ( \n"+  
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"										FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
				"										WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1 \n"+   
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+   
				"									) \n"+   
				"						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ \n"+   
				"				WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length() >0)
					query+= "		and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+														
				"			UNION ALL \n"+															
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN (   													" +
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"										FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
				"										WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"and kh.NPP_FK ='"+nppId+"'";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"			UNION ALL   \n"+
				"				SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"				FROM   \n"+
				"					(   \n"+
				"						SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"								sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      \n"+
				"								sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"								sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   \n"+
				"								round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,   \n" +
				"								sum(isnull(thuexuat,0)) as BVAT_CK       \n"+
				"						FROM (     \n"+
				"								SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   	\n"+													
				"								  		( \n"+     
				"											SELECT top(1) bb.DDKD_FK \n"+     
				"											FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"											WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"										) as ddkd_fk , 													" +
				"										case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"										case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia  \n"+
				"								FROM  	ERP_HOADONNPP a   \n"+
				"								 	 	INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"	 	 						  		INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n"+ 
				"	  							  		INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"	  							  		INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
				"								WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'"+tuNgay+"' \n"+   
				"										and Datediff(dd, a.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"						 	)ETC									  	\n"+
				"						GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"					)   \n"+
				"					hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"					LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n"+
				"				WHERE 1=1 ";
				if(nppId.trim().length()>0)
				    query+= "	and hd.NPP_FK ='"+nppId+"' \n"; 
				
				query+= "			GROUP BY hd.khachhang_fk \n"+ 
				"			UNION ALL \n"+ 
				"				SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+   
				"				FROM   	DUNO_KHACHHANG dnkh   \n"+
				"						INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK = kh.PK_SEQ \n"+
				"						LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n"+
				"						LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n"+
				"						LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK 			  \n"+
				"				WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"' and dnkh.SONO >= 0  ";
				if(nppId.trim().length()>0)
					query+=	" 	and  dnkh.NPP_FK='"+nppId+"' \n";
				
				query+= 
				"						and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n"+
				"				GROUP BY dnkh.KHACHHANG_FK   \n"+
				"				----------- \n"+	
				"			UNION ALL \n"+	
				"				SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"				FROM   HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				WHERE  1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) 		\n"+	   
				"				   	   and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=		"and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"				   		and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"	        	GROUP BY hd.KHACHHANG_FK  \n"+
				"			UNION ALL \n"+  
				"				SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
				"			        	INNER JOIN KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 \n"+
				"						and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"	and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) > kh.THOIHANNO       \n"+                        
				"				GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						INNER JOIN (   	\n" +
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"										FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					 	LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE  	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and \n"+ 
				"			       		hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	" and kh.NPP_FK ='"+nppId+"' \n"; 
				
				query+=
				"			       		and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN (   \n"+
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+  
				"										FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					 	LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+=	"			        and kh.NPP_FK ='"+nppId+"'   \n";
				
				query+=
				"			        and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) > kh.THOIHANNO \n"+
				"			UNION ALL   \n"+
				"				SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"				FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       \n"+
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK, \n"+            
				"							sum(isnull(thuexuat,0)) as BVAT_CK       \n"+
				"					FROM 	(     \n"+
				"								SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat, \n"+   
				"										(     \n"+
				"											SELECT top(1) bb.DDKD_FK \n"+     
				"											FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"											WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"										) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"										case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"								FROM  ERP_HOADONNPP a     \n"+
				"							  		  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    \n"+
				"							  		  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"							          INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq  \n"+
				"							  		  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ  \n"+ 
				"								WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '"+tuNgay+"' and a.NGAYXUATHD <= '"+denNgay+"' \n"+ 
				"						      		  and Datediff(dd, a.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"							)ETC	\n"+
				"					GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"					  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK \n"+   
				"				WHERE 1=1 ";
				if(nppId.trim().length()>0)
				   query += "	and hd.NPP_FK ='"+nppId+"'   \n";
				
				query+=
				"	        	GROUP BY hd.khachhang_fk \n"+ 
				"			UNION ALL \n"+ 
				"		   		SELECT	a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienavat \n"+   
				"		   		FROM		ERP_HoaDonPheLieu a inner join KHACHHANG kh on a.khachhang_fk = kh.PK_SEQ \n"+
				"		   		WHERE	1 = 1 AND a.trangthai = 1 AND a.ngayhoadon >= '"+tuNgay+"' and a.ngayhoadon <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="					and a.npp_fk = '"+nppId+"' ";
				
				query+=
				"						and Datediff(dd,  a.ngayhoadon, GETDATE()) > kh.THOIHANNO \n"+
				"		   		GROUP BY a.khachhang_fk \n"+
				"			)   \n"+
				"			psn   \n"+
				"		GROUP BY psn.PK_SEQ \n"+  
				"	 ) \n"+
				"	 no_qua_han on kh.pk_seq = no_qua_han.khachhang_fk left join \n";
				
				 query+=
				" 	( \n"+   
				"		SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT  \n"+
				"		FROM   \n"+
				"		(   \n"+
				"			---- dư nợ có quá hạn \n"+
				""			+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and  tthd.LOAIHD = 0 \n"+
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 							   ) \n"+ 	  
				" 					tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 ";
				 if(nppId.trim().length()>0)
					 query+= "and kh.NPP_FK = '"+nppId+"' ";
				 
				query+=	 		
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+  
				"			GROUP BY hd.KHACHHANG_FK \n"+   
				"		UNION ALL   \n"+
				"			SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ =  btcn_hd.KHACHHANG_FK \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="    				and btcn.NPP_FK='"+nppId+"' ";
				query+= 
				" 					and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+ 
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"		UNION ALL  \n"+
				"			SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 			   INNER JOIN   \n"+
				"	 					(   \n"+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 2 \n";
				if(nppId.trim().length()>0)
					query+= "				and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+   
				"		 				) \n"+  
				"		 		   tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"		 	WHERE 1=1  and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+="			 and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"		 	GROUP BY dnkh.KHACHHANG_FK \n"+   
				"		UNION ALL	 \n"+
				"			SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		  
				"			FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on tttl.khachhang_fk = kh.PK_SEQ \n"+
				"			WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '"+tuNgay+"' and KHACHHANG_FK is not null \n"; 
				if(nppId.trim().length()>0)
					query+="				and tttl.NPP_FK = '"+nppId+"' 	";
				
				query+=
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > kh.THOIHANNO \n"+
				"			GROUP BY tttl.KHACHHANG_FK \n"+   
				"		UNION ALL	  \n"+
				"			SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"			FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"			   	   INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 0  \n"+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  							   ) \n"+   
				"  					tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+   
				"			WHERE 1=1 and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+="				and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=			
				"			GROUP BY  hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+   
				"			SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"			FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"			   	   INNER JOIN (   \n"+
				" 								SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 								FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 								WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 1  \n"+
				" 								GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  							   )   \n"+
				"  					tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+   
				"			WHERE 1=1 and Datediff(dd, hd.ngayhoadon, GETDATE()) > kh.THOIHANNO \n";
				
				if(nppId.trim().length()>0)
					query+="				and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=		
				"			GROUP BY  hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+   
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"			FROM   	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   			INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+   
				"		   			INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK = '"+nppId+"'	";
				query+= 
				"					and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+	
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"		UNION ALL   \n"+
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0  ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n";  
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK = '"+nppId+"' \n";
								
				query+=
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"		UNION ALL   \n"+
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK				\n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"'  and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+= "	and xnkh.NPP_FK = '"+nppId+"' \n";
				
				query+=  			
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"		UNION ALL \n"+   
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK  \n"+
				"					INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI = 1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+="					and xnkh.NPP_FK = '"+nppId+"' \n";
				
				query+=    			
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"		UNION ALL   \n"+
				"			SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   				INNER JOIN (   \n"+
				"	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  						WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 					   ) \n"+   
				"	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"			WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n";	  
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=	    			
				" 			GROUP BY hd.KHACHHANG_FK \n"+   
				"	UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   				INNER JOIN (   \n"+
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 						)   \n"+
				"					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"			WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n";	  
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"			GROUP BY hd.KHACHHANG_FK    \n"+
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"			WHERE 1 =1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n";				
				if(nppId.trim().length()>0)
					query+="	and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=			
				"			GROUP BY ttna.KHACHHANG_FK \n"+   
				"		UNION ALL   \n"+
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT \n"+  
				"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   \n"+
				"					INNER JOIN (   \n"+
				"								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+   
				"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   \n"+
				"								WHERE  ct.TRANGTHAI = 1   \n"+
				"								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+   
				"							)   \n"+
				"							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+   
				"			WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"'  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > k.THOIHANNO \n";	 
				if(nppId.trim().length()>0)
					query+= "	and hd.NPP_FK='"+nppId+"' \n";	  
				
				query+=			
				"			GROUP BY hd.KHACHHANG_FK \n"+   			    	
				"  		UNION ALL  \n"+
				"			SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT \n"+    
				"			FROM   	DUNO_KHACHHANG dnkh    \n"+
				"					LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+    
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK  \n"+
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ =  dnkh.KHACHHANG_FK \n"+ 
				"			WHERE 	1=1 and dnkh.NgayDuNo < ' "+tuNgay+" ' and dnkh.SONO < 0 and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n";
				if(nppId.trim().length()>0)
					query+="	 and  dnkh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"			GROUP BY dnkh.KHACHHANG_FK \n"+
				"		UNION ALL \n"+
				"		----- \n"+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+    
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 0 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 		";
				
				if(nppId.trim().length()>0)
					query+="and kh.NPP_FK = '"+nppId+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n";
				
				query+=
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL   \n"+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 1 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+="and kh.NPP_FK = '"+nppId+"' ";
				
				query+=					
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) > kh.THOIHANNO \n"+   
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+   
				"			SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"					INNER JOIN KHACHHANG kh on btcn_hd.KHACHHANG_FK = kh.PK_SEQ \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"		and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO   \n"+
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+  
				"			SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 					INNER JOIN   \n"+
				"	 					(   \n"+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK and tthd.LOAIHD = 2 \n"+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"			and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"		 				) \n"+
				"		 				tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			 WHERE 1=1 ";
				if(nppId.trim().length()>0)				
					query+="			and kh.NPP_FK ='"+nppId+"'  ";
				
				query+=
				"						and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n"+   
				"			 GROUP BY dnkh.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+	  
				"			SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		 
				"			FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on kh.PK_SEQ = tttl.khachhang_fk \n"+
				"			WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '"+tuNgay+"' and tttl.NGAYTRA <= '"+denNgay+"' \n"+ 
				"					and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+="		and tttl.NPP_FK = '"+nppId+"' 	  \n";
				
				query+=
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > kh.THOIHANNO    \n"+
				"			GROUP BY tttl.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+	 
				"			SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"			FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ    \n"+
				"				   INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' and  tthd.LOAIHD = 0 \n"+ 
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  								)   \n"+
				"  								tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+  
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= "and kh.NPP_FK = '"+nppId+"'   ";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+    
				"			GROUP BY  hd.KHACHHANG_FK  \n"+
				"		UNION ALL \n"+   
				"			SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"			FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+ 
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
				"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+= "		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO    \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   	\n"+
				"		UNION ALL \n"+   		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n"+
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"					INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD =0 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)				
					query+= "		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+  
				"	   UNION ALL \n"+ 		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2) \n"+
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"	   UNION ALL \n  		" +				
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT   \n"+
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	" 				and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO 	 \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"		UNION ALL \n"+   
				" 			SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 						   )   \n"+
				"	 							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";				
				if(nppId.trim().length()>0)
					query+=	"	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO 	 \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				"	  \n"+
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				" 			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 							)   \n"+
				"					 			bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=" 		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				"	  \n"+
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   		
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"			WHERE	1 =1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="		and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO    	  \n"+
				"			GROUP BY ttna.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+  		
				"		SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+ 
				"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
				"					INNER JOIN ( \n"+
				"									SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
				"									FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
				"									WHERE  ct.TRANGTHAI = 1 \n"+
				"									GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+ 
				"								) \n"+
				"								ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+ 								
				"			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "			and hd.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > k.THOIHANNO \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+
				"		)   	psc group by psc.KHACHHANG_FK \n"+   
				"	) \n"+  
				"	co_qua_han on kh.pk_seq = co_qua_han.khachhang_fk LEFT JOIN \n"+
				"	 ( \n"+
				"		SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT   \n"+
				"			FROM   \n"+
				"			(   \n"+
				"			---- dư nợ đầu kỳ \n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT  \n"+ 
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+= "		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL \n"+
				"			\n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(isnull(avat,0)) tongtienAVAT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1  and hd.TRANGTHAI = 1 and hd.NGAYHOADON < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) > kh.THOIHANNO \n"+
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL \n"+
				"			\n"+
				"			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
				"					inner join KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK    \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+= " and btcn.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO	+60) \n"+						
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL	  \n"+
				"			\n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					inner join (  \n"+
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"	and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO	\n"+ 
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			UNION ALL	  \n"+
				"								\n"+		
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					INNER JOIN (   													\n" +
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"					bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	" 	and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+	
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"			UNION ALL   \n"+
				"			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"			FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk,   \n"+
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      \n"+
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             												sum(isnull(thuexuat,0)) as BVAT_CK \n"+       
				"					FROM (     \n"+
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   														(\n"+     
				"										SELECT top(1) bb.DDKD_FK     \n"+
				"										FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"										WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"									) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a   \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"	 	 						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk    \n"+
				"	  							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"	  							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'"+tuNgay+"'  \n"+ 
				"							and Datediff(dd, a.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"							and Datediff(dd, a.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"						 )ETC									  	\n"+
				"						 GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"				LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n"+
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= "and hd.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"			GROUP BY hd.khachhang_fk \n"+
				"			\n"+
				"			UNION ALL \n"+ 
				"		  \n"+
				"			SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+   
				"			FROM   	DUNO_KHACHHANG dnkh   \n"+
				"					INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK = kh.PK_SEQ \n"+
				"					LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n"+
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK 			  \n"+
				"			WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"' and dnkh.SONO >= 0  " ;
				if(nppId.trim().length()>0)
						query+= " and  dnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n"+
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"			GROUP BY dnkh.KHACHHANG_FK \n"+
				"			\n"+
				"			----------- \n"+
				"			UNION ALL \n"+
				"			\n"+
				"				SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"				FROM   HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"				WHERE  1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) 		\n"+	   
				"					   and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	" and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					   and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) >(kh.THOIHANNO) \n"+
				"					   and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"				GROUP BY hd.KHACHHANG_FK  \n"+
				"		         \n"+
				"			UNION ALL \n"+  
				"				SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 \n"+
				"						and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="						and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) > (kh.THOIHANNO) \n"+
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) <= (kh.THOIHANNO+60) \n"+                              
				"				GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						inner join (   	SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"										FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and \n"+ 
				"					   hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"	and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"				       and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO) \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN (   \n"+
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+  
				"										FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+=	"						and kh.NPP_FK ='"+nppId+"' \n";
								
				query+=
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) <= (kh.THOIHANNO+60) \n"+
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) > (kh.THOIHANNO) \n"+
				"			UNION ALL   \n"+
				"				SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"				FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       \n"+
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK, \n"+            
				"							sum(isnull(thuexuat,0)) as BVAT_CK       \n"+
				"					FROM (     \n"+
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat, \n"+   
				"								(     \n"+
				"									SELECT top(1) bb.DDKD_FK \n"+     
				"									FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"									WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"								) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a     \n"+
				"								  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n"+ 
				"								  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"								  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '"+tuNgay+"' and a.NGAYXUATHD <= '"+denNgay+"' \n"+				
				"								  and Datediff(dd, a.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"								  and Datediff(dd, a.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO) \n"+
				"						)ETC		\n"+							  			
				"						GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"						  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK \n"+   
				"				WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+=" and hd.NPP_FK ='"+nppId+"'\n";
				
				query+=
				"				GROUP BY hd.khachhang_fk \n"+   
				"	\n"+	 
				"			UNION ALL \n"+ 
				"			   SELECT	a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienavat \n"+   
				"			   FROM		ERP_HoaDonPheLieu a inner join KHACHHANG kh on a.khachhang_fk = kh.PK_SEQ \n"+
				"			   WHERE	1 = 1 AND a.trangthai = 1 AND a.ngayhoadon >= '"+tuNgay+"' and a.ngayhoadon <= '"+denNgay+"' \n"; 
				if(nppId.trim().length()>0)
					query+="						and a.npp_fk = '"+nppId+"' ";
				
				query+=	
				"						and Datediff(dd,  a.ngayhoadon, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"						and Datediff(dd,  a.ngayhoadon, GETDATE()) > (kh.THOIHANNO) \n"+
				"			   GROUP BY a.khachhang_fk \n"+ 
				"			)   \n"+
				"			psn   \n"+
				"			GROUP BY psn.PK_SEQ \n"+ 
				"	 ) \n"+
				"	 no_qua_han_1_60 on kh.pk_seq = no_qua_han_1_60.khachhang_fk left join \n "+
				" (   \n"+
				"		SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+  
				"		FROM   \n"+
				"		(   \n"+
				"			---- dư nợ có quá hạn \n"+
				""			+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and  tthd.LOAIHD = 0 \n"+
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								   ) 	  \n"+
				" 									tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 ";
				if(nppId.trim().length()>0)
					query+= "		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+  
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n"+ 
				""					+
				"				GROUP BY hd.KHACHHANG_FK \n"+   
				"				UNION ALL   \n"+
				"				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ =  btcn_hd.KHACHHANG_FK \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' \n"; 
				if(nppId.trim().length()>0)
					query+="    					  and btcn.NPP_FK='"+nppId+"' ";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+ 
				"    					and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +60) \n"+				
				"  				GROUP BY btcn_hd.KHACHHANG_FK \n"+   
				"				UNION ALL  \n"+
				"				SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 				   INNER JOIN   \n"+
				"	 					( \n"+   
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 2 \n";
				if(nppId.trim().length()>0)
					query+="										and tt.NPP_FK = '"+nppId+"'						  \n";
				
				query+=
				"			    						\n"+
				"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK \n"+   
				"		 			) \n"+  
				"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"		 		WHERE 1=1  and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n"+
				"		 				and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO +60) \n";
				if(nppId.trim().length()>0)
					query+="			 and kh.NPP_FK = '"+nppId+"' \n";
								
				query+=
				""+
				"		 		GROUP BY dnkh.KHACHHANG_FK \n"+   
				"				UNION ALL \n"+	 
				"				SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT " +				 		  
				"				FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on tttl.khachhang_fk = kh.PK_SEQ \n"+
				"				WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '"+tuNgay+"' and KHACHHANG_FK is not null  \n";
				if(nppId.trim().length()>0)
					query+="		and tttl.NPP_FK = '"+nppId+"' 	";
				
				query+=		
				"				and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > kh.THOIHANNO \n"+ 
				"				and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				""		+
				"				GROUP BY tttl.KHACHHANG_FK \n"+   
				"				UNION ALL	  \n"+
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"			   			INNER JOIN (   \n"+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 0  \n"+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  									)   \n"+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+   
				"				WHERE 1=1 and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				"				and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="				and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=				
				"				GROUP BY  hd.KHACHHANG_FK \n"+				
				"				UNION ALL \n"+   
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"				FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"			   			INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 1  \n"+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  									)   \n"+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+   
				"				WHERE 1=1 and Datediff(dd, hd.ngayhoadon, GETDATE()) > kh.THOIHANNO \n"+
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) <= (kh.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="				and kh.NPP_FK = '"+nppId+"'   \n";
				
				query+=			
				"				GROUP BY  hd.KHACHHANG_FK   \n"+
				""+
				"				UNION ALL \n"+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"				FROM   	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+   
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"				WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' \n";
				if(nppId.trim().length()>0)
					query+=	"			and xnkh.NPP_FK = '"+nppId+"'	";
				
				query+=		
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+	
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				""			    			+
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"			UNION ALL   \n"+
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"						INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0  ) \n"+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+	
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 60) \n";
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK = '"+nppId+"' \n";
				
				query+=				
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"			UNION ALL \n"+				
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"						INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2 ) \n"+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK				\n"+
				"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"'  and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+	  
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK = '"+nppId+"'		  \n";
				
				query+=
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"			UNION ALL \n"+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"						INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 ) \n"+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"				WHERE 	1=1 and xnkh.TRANGTHAI = 1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+	  
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="	and xnkh.NPP_FK = '"+nppId+"'		  \n";
				
				query+=
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+   
				"			UNION ALL   \n"+
				"				SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  						WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 					   )   \n"+
				"	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 				WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO " +							
				" 						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=						    		
				" 				GROUP BY hd.KHACHHANG_FK \n"+				
				"			UNION ALL \n"+   
				"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT   \n"+
				"  				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 						)   \n"+
				"					 	bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 				WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+	
				" 						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' \n";   
				
				query+=
				"				GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"			WHERE 	1 =1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+	
				"					and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="	and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=
				""				    			+
				"		GROUP BY ttna.KHACHHANG_FK \n"+   
				"	UNION ALL \n"+   
				"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT \n"+  
				"		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   \n"+
				"				INNER JOIN (   \n"+
				"								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+   
				"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   \n"+
				"								WHERE  ct.TRANGTHAI = 1   \n"+
				"								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+   
				"							) \n"+   
				"							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+   
				"		WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"'  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > k.THOIHANNO \n"+	
				"		and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (k.THOIHANNO+60) \n";
				if(nppId.trim().length()>0)
					query+="	and hd.NPP_FK='"+nppId+"' \n";
				
				query+=					    			
				"		GROUP BY hd.KHACHHANG_FK \n"+   			    	
				" 	 UNION ALL \n"+  
				"		SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT \n"+    
				"		FROM   	DUNO_KHACHHANG dnkh    \n"+
				"				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ \n"+    
				"				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    \n"+
				"				LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK \n"+  
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ =  dnkh.KHACHHANG_FK \n"+ 
				"		WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"' and dnkh.SONO < 0 and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n	" +					
				"				and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO	+60) \n";
				if(nppId.trim().length()>0)
					query+="	 and  dnkh.NPP_FK = '"+nppId+"' \n";
				
				
				query+=
				""					    				+	
				"		GROUP BY dnkh.KHACHHANG_FK  \n"+
				""		+
				"		UNION ALL \n"+
				"		----- \n"+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 0 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+   
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL   \n"+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ  \n"+
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 1 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) > kh.THOIHANNO \n"+   
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+   
				"			SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"					INNER JOIN KHACHHANG kh on btcn_hd.KHACHHANG_FK = kh.PK_SEQ \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO   \n"+
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+  
				"			SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 					INNER JOIN   \n"+
				"	 					(  \n"+ 
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK and tthd.LOAIHD = 2 \n"+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"'";
				if(nppId.trim().length()>0)
					query+="		 and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"		 				) \n"+
				"		 				tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			 WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK ='"+nppId+"'  ";
				
				query+=
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > kh.THOIHANNO \n"+  
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			 GROUP BY dnkh.KHACHHANG_FK   \n"+
				"	\n"+		 
				"		UNION ALL \n"+	  
				"			SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		 
				"			FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on kh.PK_SEQ = tttl.khachhang_fk \n"+
				"			WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '"+tuNgay+"' and tttl.NGAYTRA <= '"+denNgay+"' \n"+
				"					and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+="		and tttl.NPP_FK = '"+nppId+"' 	  \n";
				
				query+=
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > kh.THOIHANNO \n"+
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			GROUP BY tttl.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+	 
				"			SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"			FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				   INNER JOIN (   \n"+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' and  tthd.LOAIHD = 0 \n"+ 
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  								)   \n"+
				"  								tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+  
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= "	and kh.NPP_FK = '"+nppId+"'   \n";
				
				query+=
				"					  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+    
				"					  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO + 60) \n"+
				"			GROUP BY  hd.KHACHHANG_FK  \n"+
				"			 \n"+
				"		UNION ALL   \n"+
				"			SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"			FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+ 
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
				"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO   \n"+
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   	\n"+
				" \n"+			
				"		UNION ALL \n"+   		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"					INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD =0 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+= "			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  \n"+
				"			 \n"+
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2) \n"+
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"			\n"+			
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO 	\n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+60) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				" \n"+			
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 						   )   \n"+
				"	 							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO 	\n"+
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				" \n"+	  
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 							) \n"+   
				"					 			bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' \n";    		
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > kh.THOIHANNO \n"+
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				" \n"+	  
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   		
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"			WHERE	1 =1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) > kh.THOIHANNO   \n"+
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +60) \n"+
				"			GROUP BY ttna.KHACHHANG_FK   \n"+
				" \n"+			
				" 		UNION ALL \n"+  		
				"		SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+ 
				"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
				"					INNER JOIN ( \n"+
				"									SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
				"									FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
				"									WHERE  ct.TRANGTHAI = 1 \n"+
				"									GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+ 
				"								) \n"+ 
				"								ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+ 								
				"			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and hd.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > k.THOIHANNO \n"+
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (k.THOIHANNO+60) \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+
				"		)   	psc group by psc.KHACHHANG_FK \n"+   
				"	) \n"+   
				"	co_qua_han_1_60 on kh.pk_seq = co_qua_han_1_60.khachhang_fk LEFT JOIN \n";
								
				query+=
				"	 ( \n"+
				"		SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT \n"+   
				"		FROM   \n"+
				"			(   \n"+
				"				---- dư nợ đầu kỳ \n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+= " and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +60) \n"+
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL \n"+
				"			\n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(isnull(avat,0)) tongtienAVAT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1  and hd.TRANGTHAI = 1 and hd.NGAYHOADON < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) > (kh.THOIHANNO+60) \n"+
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) <= (kh.THOIHANNO+120) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL \n"+
				"			\n"+
				"			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
				"					inner join KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK    \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"'  ";
				if(nppId.trim().length()>0)
					query+=	"	and btcn.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) \n"+
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO	+ 120) \n"+						
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL	  \n"+
				"			\n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					inner join (  \n"+
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"	and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +60) \n"+
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO +120) \n"+
				"			UNION ALL	  \n"+
				"								\n"+		
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (   													" +
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK \n"+   
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"					bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "		and kh.NPP_FK ='"+nppId+"'\n"; 
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+60) \n"+
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) \n"+
				"			UNION ALL   \n"+
				"			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"			FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      \n"+
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             												sum(isnull(thuexuat,0)) as BVAT_CK \n"+       
				"					FROM (    \n"+ 
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   														( \n"+     
				"										SELECT top(1) bb.DDKD_FK     \n"+
				"										FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"										WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"									) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a   \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"	 	 						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk   \n"+ 
				"	  							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"	  							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'"+tuNgay+"' \n"+  
				"							and Datediff(dd, a.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +60) \n"+
				"							and Datediff(dd, a.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) \n"+
				"						 )ETC									  	\n"+
				"						 GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"				LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n"+
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= " and hd.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"			GROUP BY hd.khachhang_fk \n"+ 
				"			\n"+
				"			UNION ALL \n"+ 
				"			\n"+				  
				"			SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+   
				"			FROM   	DUNO_KHACHHANG dnkh   \n"+
				"					INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK = kh.PK_SEQ \n"+
				"					LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n"+
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK 		\n"+	  
				"			WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"' and dnkh.SONO >= 0  ";
				if(nppId.trim().length()>0)
					query+=	"and  dnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+60) \n"+
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO+120) \n"+
				"			GROUP BY dnkh.KHACHHANG_FK \n"+
				"			\n"+
				"			UNION ALL \n"+
				"			----------- \n"+
				"			SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+ 
				"			FROM   HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE  1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) 		\n"+	   
				"				   and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+  
				"		         \n"+
				"			UNION ALL  \n"+
				"				SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 \n"+
				"						and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) > (kh.THOIHANNO+120) \n"+                               
				"				GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						inner join (   	SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"										FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and \n"+ 
				"					   hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"				       \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN (   \n"+
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+  
				"										FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									) \n"+   
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="						and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) > (kh.THOIHANNO+120) \n"+
				"			UNION ALL   \n"+
				"				SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"				FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       \n"+
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK, \n"+            
				"							sum(isnull(thuexuat,0)) as BVAT_CK       \n"+
				"					FROM (     \n"+
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat, \n"+   
				"								(     \n"+
				"									SELECT top(1) bb.DDKD_FK \n"+     
				"									FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"									WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"								) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a     \n"+
				"								  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+    
				"								  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"								  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '"+tuNgay+"' and a.NGAYXUATHD <= '"+denNgay+"' \n"+ 
				"								  and Datediff(dd, a.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"						)ETC	\n"+								  			
				"						GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"						  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK \n"+   
				"				WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= "and hd.NPP_FK ='"+nppId+"'   ";
				
				query+=
				" \n"+
				"				GROUP BY hd.khachhang_fk \n"+   
				"		 \n"+
				"			UNION ALL \n"+ 
				"			   SELECT	a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienavat \n"+   
				"			   FROM		ERP_HoaDonPheLieu a inner join KHACHHANG kh on a.khachhang_fk = kh.PK_SEQ \n"+
				"			   WHERE	1 = 1 AND a.trangthai = 1 AND a.ngayhoadon >= '"+tuNgay+"' and a.ngayhoadon <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="						and a.npp_fk = '"+nppId+"' ";
				
				query+=	" and Datediff(dd,  a.ngayhoadon, GETDATE()) > (kh.THOIHANNO+120)"+
				" \n"+
				"			   GROUP BY a.khachhang_fk \n"+ 
				"			)   \n"+
				"			psn   \n"+
				"			\n"+
				"			GROUP BY psn.PK_SEQ \n"+ 
				"	 ) \n"+
				"	 no_qua_han_61_120 on kh.pk_seq = no_qua_han_61_120.khachhang_fk left join \n"; 
								
				query+=
				" ( \n"+   
				"		SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+  
				"		FROM   \n"+
				"		( \n"+   
				"			---- dư nợ có quá hạn \n"+
				""		+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT "+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+  
				"						INNER JOIN (   "+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT "+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  "+ 
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and  tthd.LOAIHD = 0 "+
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				" 								   ) 	  "+
				" 									tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   "+
				"				WHERE 	1=1 ";
				if(nppId.trim().length()>0)
					query+= "			and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+60) "+
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) "+
				"				GROUP BY hd.KHACHHANG_FK "+   
				"			UNION ALL   "+
				"				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT "+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on  btcn.PK_SEQ = btcn_hd.BTCN_FK  "+
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ =  btcn_hd.KHACHHANG_FK "+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' ";   
				if(nppId.trim().length()>0)
					query+= "    					  and btcn.NPP_FK='"+nppId+"' ";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) "+
				"    					and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +120) "+
				"  				GROUP BY btcn_hd.KHACHHANG_FK "+   
				"			UNION ALL  "+
				"				SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT "+   
				"				FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ "+   
				"	 				   INNER JOIN   "+
				"	 					(   "+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT "+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK "+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 2 ";
				if(nppId.trim().length()>0)
					query+="										and tt.NPP_FK = '"+nppId+"'						  ";
				
				query+=
				"			    						"+
				"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK "+   
				"		 			)  "+
				"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK "+   
				"		 		WHERE 1=1  and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+60) "+
				"		 				and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO +120) ";
				if(nppId.trim().length()>0)
					query+="			 and kh.NPP_FK = '"+nppId+"' ";
				
				query+=				
				"		 		GROUP BY dnkh.KHACHHANG_FK "+   
				"			UNION ALL	 "+
				"				SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT "+ 		  
				"				FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on tttl.khachhang_fk = kh.PK_SEQ "+
				"				WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '"+tuNgay+"' and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+= "		and tttl.NPP_FK = '"+nppId+"' 	";
				
				query+=
				"						and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > (kh.THOIHANNO+60) "+
				"						and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= (kh.THOIHANNO+120) "+
				"				GROUP BY tttl.KHACHHANG_FK "+   
				"			UNION ALL	  "+
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT "+   
				"				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "+  
				"			   			INNER JOIN (   "+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT "+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   "+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 0  "+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				"  									)   "+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK "+   
				"				WHERE 1=1 and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+60) "+
				"						  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) ";
				if(nppId.trim().length()>0)
					query+="				and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"				GROUP BY  hd.KHACHHANG_FK "+   
				"			UNION ALL "+   
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT "+   
				"				FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "+  
				"			   			INNER JOIN (   "+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT "+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   "+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 1  "+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				"  									)   "+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK "+   
				"				WHERE 1=1 and Datediff(dd, hd.ngayhoadon, GETDATE()) > (kh.THOIHANNO+60) "+
				"					  and Datediff(dd, hd.ngayhoadon, GETDATE()) <= (kh.THOIHANNO+120) ";
				if(nppId.trim().length()>0)					
					query+="						and kh.NPP_FK = '"+nppId+"'   ";
				
				query+=
				"				GROUP BY  hd.KHACHHANG_FK "+
				"			UNION ALL "+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA "+   
				"				FROM   	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) "+   
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"				WHERE  	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' ";				
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK = '"+nppId+"'	";
				
				query+=
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) "+
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +120) "+
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+				
				"			UNION ALL "+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"						INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0  ) "+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' "+
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) "+
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 120) ";
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK = '"+nppId+"' ";
				
				query+=				
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"			UNION ALL "+  
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"						INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2 ) "+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK				"+
				"				WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"'  "+
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) "+
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+120) ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK = '"+nppId+"'		  ";
				
				query+=
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"			UNION ALL "+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"				FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"						INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 ) "+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"				WHERE 	1=1 and xnkh.TRANGTHAI = 1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' "+ 
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) "+
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+120) ";
				if(nppId.trim().length()>0)
					query+="				and xnkh.NPP_FK = '"+nppId+"'";
				
				query+=
				"				GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"			UNION ALL   "+
				" 				SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT "+   
				"  				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+  
				"   					INNER JOIN (   "+
				"	  								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO "+   
				"	  								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   "+
				"	  								WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   "+
				"	  								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   "+
				"	 					  			)   "+
				"	 					bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  "+ 
				"				WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"'  "+
				" 						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+60) "+
				" 						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				" 				GROUP BY hd.KHACHHANG_FK  "+  
				"			UNION ALL "+   
				"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT "+   
				"  				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+   
				"   					INNER JOIN (   "+
				"	  									SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO "+   
				"	  									FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK "+   
				"	  									WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   "+
				"	  									GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   "+
				"	 								)   "+
				"					 	bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK "+   
				"				WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+60) "+
				" 						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' ";
				
				query+=		    			
				" 				GROUP BY hd.KHACHHANG_FK "+   
				"			UNION ALL "+   
				"				SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT "+   
				"				FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ "+   
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK "+
				"				WHERE 	1 =1 and tt.NGAYCHUNGTU < '"+tuNgay+"' "+
				"						and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) "+
				"						and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+120) ";
				if(nppId.trim().length()>0)
					query+="			and ttna.NPP_FK = '"+nppId+"' ";
				
				query+=		    			
				"				GROUP BY ttna.KHACHHANG_FK "+   
				"			UNION ALL "+   
				"				SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT "+  
				"				FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   "+
				"						INNER JOIN (   "+
				"									SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU "+   
				"									FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   "+
				"									WHERE  ct.TRANGTHAI = 1   "+
				"									GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK "+   
				"									)   "+
				"						ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK    " +				
				"				WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"'  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (k.THOIHANNO+60) \n"+
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (k.THOIHANNO+120) \n";
				if(nppId.trim().length()>0)
					query+="	and hd.NPP_FK='"+nppId+"' ";
				
				query+=			
				"				GROUP BY hd.KHACHHANG_FK \n"+   			    	
				"  			UNION ALL  \n"+
				"				SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT "+    
				"				FROM   	DUNO_KHACHHANG dnkh    "+
				"						LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ "+    
				"						LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    "+
				"						LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK  "+
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ =  dnkh.KHACHHANG_FK "+ 
				"				WHERE 	1=1 and dnkh.NgayDuNo < ' "+tuNgay+" ' and dnkh.SONO < 0 and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO	+60) "+
				"						and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO	+120) ";
				if(nppId.trim().length()>0)	
					query+="	 		and  dnkh.NPP_FK = '"+nppId+"' ";
				
				query+=	
				"				GROUP BY dnkh.KHACHHANG_FK "+
				"			UNION ALL "+
				"				SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						INNER JOIN (   \n"+
				"  										SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  										FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  										WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 0 \n"+  
				"  										GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 									) \n"+ 	  
				" 						tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"				WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+60) \n"+
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+120) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL   \n"+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 1 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 	";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) > (kh.THOIHANNO +60) \n"+
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) <= (kh.THOIHANNO +120) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+   
				"			SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"					INNER JOIN KHACHHANG kh on btcn_hd.KHACHHANG_FK = kh.PK_SEQ \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) \n"+
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +120) \n"+
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+  
				"			SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 					INNER JOIN   \n"+
				"	 					(   \n"+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK and tthd.LOAIHD = 2 \n"+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"'";
				if(nppId.trim().length()>0)
				 	query+= "	and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"		 				)  \n"+
				"		 				tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			 WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK ='"+nppId+"'  \n";
				
				query+=
				"					   and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+60) \n"+
				"					   and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO +120) \n"+
				"			 GROUP BY dnkh.KHACHHANG_FK   \n"+
				" \n"+			 
				"		UNION ALL \n"+	  
				"			SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		 
				"			FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on kh.PK_SEQ = tttl.khachhang_fk \n"+
				"			WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '"+tuNgay+"' and tttl.NGAYTRA <= '"+denNgay+"' \n"+ 
				"					and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+="			and tttl.NPP_FK = '"+nppId+"' 	  \n";
				
				query+=
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > (kh.THOIHANNO +60) \n"+
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= (kh.THOIHANNO +120) \n"+
				"			GROUP BY tttl.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+	 
				"			SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"			FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ    \n"+
				"				   INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' and  tthd.LOAIHD = 0 \n"+ 
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  								)   \n"+
				"  								tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+  
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"'   \n";
				
				query+=
				"					  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+ 60) \n"+
				"					  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO + 120) \n"+
				"			GROUP BY  hd.KHACHHANG_FK  \n"+
				"			 \n"+
				"		UNION ALL   \n"+
				"			SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"			FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+ 
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
				"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"'";
				if(nppId.trim().length()>0)
					query+=" 		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 60) \n"+
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 120) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   	\n"+
				" \n"+			
				"		UNION ALL \n"+   		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"					INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD =0 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 60) \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 120) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  \n"+
				"	\n"+		 
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2) \n"+
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 60) \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 120) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"			\n"+
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 60 ) \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 120) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+  
				"			\n"+
				"		UNION ALL \n"+   
				" 			SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 						   )   \n"+
				"	 							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO + 60 ) \n"+
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO + 120) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n";
				
				query+=
				"	\n"+	  
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK \n"+  
				"	 							) \n"+   
				"					 			bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' \n";  
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +60) \n"+
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO +120) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				"	  \n"+
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   		
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"			WHERE	1 =1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 60) \n"+
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 120) \n"+
				"			GROUP BY ttna.KHACHHANG_FK   \n"+
				" \n"+			
				" 		UNION ALL \n"+  		
				"		SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+ 
				"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
				"					INNER JOIN ( \n"+
				"									SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
				"									FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
				"									WHERE  ct.TRANGTHAI = 1 \n"+
				"									GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+ 
				"								) \n"+
				"								ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+ 								
				"			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "			and hd.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (k.THOIHANNO +60) \n"+
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (k.THOIHANNO+120) \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+
				"		)   	psc group by psc.KHACHHANG_FK  \n"+ 
				"	)   \n"+
				"	co_qua_han_61_120 on kh.pk_seq = co_qua_han_61_120.khachhang_fk LEFT JOIN \n"+ 
				"	 ( \n"+
				"		SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT \n"+   
				"			FROM   \n"+
				"			(   \n"+
				"			---- dư nợ đầu kỳ \n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query += "and kh.NPP_FK = '"+nppId+"'";
				
				query+=
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +120) \n"+
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"										\n"+
				"		UNION ALL \n"+
				"			\n"+ 
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(isnull(avat,0)) tongtienAVAT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1  and hd.TRANGTHAI = 1 and hd.NGAYHOADON < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query+= " and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL \n"+
				"			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
				"					inner join KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK    \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"'  ";
				if(nppId.trim().length()>0)
					query+=	"and btcn.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+180)	\n"+					
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"		UNION ALL	  \n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					inner join (  \n"+
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"			UNION ALL	  \n"+
				"								\n"+		
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (   													" +
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"					bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"			UNION ALL   \n"+
				"			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"			FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      \n"+
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             												sum(isnull(thuexuat,0)) as BVAT_CK \n"+       
				"					FROM (     \n"+
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   														( \n"+     
				"										SELECT top(1) bb.DDKD_FK     \n"+
				"										FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"										WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"									) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a   \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"	 	 						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  \n"+
				"	  							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"	  							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'"+tuNgay+"' \n"+   
				"							and Datediff(dd, a.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"							and Datediff(dd, a.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"						 )ETC									  	\n"+
				"						 GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"				LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n"+
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= " and hd.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"			GROUP BY hd.khachhang_fk \n"+
				"			UNION ALL \n"+ 
				"			SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+   
				"			FROM   	DUNO_KHACHHANG dnkh   \n"+
				"					INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK = kh.PK_SEQ \n"+
				"					LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n"+
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK 			  \n"+
				"			WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"' and dnkh.SONO >= 0  ";
				if(nppId.trim().length()>0)
					query+=	"and  dnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"			GROUP BY dnkh.KHACHHANG_FK \n"+
				"			\n"+
				"			----------- \n"+
				"			UNION ALL \n"+
				"			\n"+
				"				SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"				FROM   HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				WHERE  1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) 		\n"+	   
				"					   and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "		and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					   and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"				GROUP BY hd.KHACHHANG_FK  \n"+
				"		         \n"+
				"			UNION ALL \n"+  
				"				SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 \n"+
				"						and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"		and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) > (kh.THOIHANNO+120) \n"+  
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) <= (kh.THOIHANNO+180)    \n"+                           
				"				GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"				\n"+
				"			UNION ALL \n"+	  
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						inner join (   	SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"										FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and \n"+ 
				"					   hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "		and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"				       \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN (   \n"+
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+  
				"										FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="						and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) > (kh.THOIHANNO+120) \n"+
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) <= (kh.THOIHANNO+180) \n"+
				"						\n"+
				"			UNION ALL   \n"+
				"				SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"				FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       \n"+				
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK, \n"+            
				"							sum(isnull(thuexuat,0)) as BVAT_CK       \n"+
				"					FROM (     \n"+
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat, \n"+   
				"								(     \n"+
				"									SELECT top(1) bb.DDKD_FK \n"+     
				"									FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"									WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"								) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a     \n"+
				"								  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+    
				"								  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"								  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '"+tuNgay+"' and a.NGAYXUATHD <= '"+denNgay+"' \n"+ 
				"								  and Datediff(dd, a.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"								  and Datediff(dd, a.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"						)ETC	\n"+								  			
				"						GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"						  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK \n"+   
				"				WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= "				and hd.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"				GROUP BY hd.khachhang_fk \n"+   
				"		 \n"+
				"			UNION ALL \n"+ 
				"			   SELECT	a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienavat \n"+   
				"			   FROM		ERP_HoaDonPheLieu a inner join KHACHHANG kh on a.khachhang_fk = kh.PK_SEQ \n"+
				"			   WHERE	1 = 1 AND a.trangthai = 1 AND a.ngayhoadon >= '"+tuNgay+"' and a.ngayhoadon <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="						and a.npp_fk = '"+nppId+"' ";
				
				query+=
				"						and Datediff(dd,  a.ngayhoadon, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"						and Datediff(dd,  a.ngayhoadon, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"			   GROUP BY a.khachhang_fk \n"+
				"			)   \n"+
				"			psn   \n"+
				"			GROUP BY psn.PK_SEQ \n"+ 
				"	 ) \n"+
				"	 no_qua_han_121_180 on kh.pk_seq = no_qua_han_121_180.khachhang_fk left join \n"+ 
				" ( \n"+   
				"		SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT  \n"+
				"		FROM   \n"+
				"		(   \n"+
				"			---- dư nợ có quá hạn \n"+
				""		+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT "+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+  
				"						INNER JOIN (   "+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT "+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  "+ 
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and  tthd.LOAIHD = 0 "+
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				" 								   ) 	  "+
				" 									tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   "+
				"				WHERE 	1=1 ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) "+
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) "+
				""					+
				"				GROUP BY hd.KHACHHANG_FK "+   
				"				UNION ALL   "+
				"				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT "+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on  btcn.PK_SEQ = btcn_hd.BTCN_FK  "+
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ =  btcn_hd.KHACHHANG_FK "+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+="    					and btcn.NPP_FK='"+nppId+"' ";
				
				query+=		
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) "+
				"    					and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +180) "+
				"  				GROUP BY btcn_hd.KHACHHANG_FK "+   
				"				UNION ALL  "+
				"				SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT "+   
				"				FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ "+   
				"	 				   INNER JOIN   "+
				"	 					(   "+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT "+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK "+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 2 ";
				if(nppId.trim().length()>0)
					query+="									and tt.NPP_FK = '"+nppId+"'						  ";
				
				query+=
				"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK "+   
				"		 			)  "+
				"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK "+   
				"		 		WHERE 1=1  and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+120) "+
				"		 				and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO +180) ";
				if(nppId.trim().length()>0)
					query+="			 and kh.NPP_FK = '"+nppId+"' ";
				
				query+=				
				"		 		GROUP BY dnkh.KHACHHANG_FK "+   
				"				UNION ALL	 "+
				"				SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT "+ 		  
				"				FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on tttl.khachhang_fk = kh.PK_SEQ "+
				"				WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '"+tuNgay+"' and KHACHHANG_FK is not null "; 
				if(nppId.trim().length()>0)
					query+="		and tttl.NPP_FK = '"+nppId+"' 	";
				
				query+=
				"						and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > (kh.THOIHANNO+120) "+
				"						and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= (kh.THOIHANNO+180) "+
				"				GROUP BY tttl.KHACHHANG_FK "+   
				"				UNION ALL	  "+
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT "+   
				"				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "+  
				"			   			INNER JOIN (   "+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT "+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   "+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 0  "+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				"  									)   "+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK "+   
				"					WHERE 1=1 and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) "+
				"				and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="				and kh.NPP_FK = '"+nppId+"' ";
				
				query+=				
				"				GROUP BY  hd.KHACHHANG_FK "+   
				""+
				"				UNION ALL "+   
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT "+   
				"				FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "+  
				"			   			INNER JOIN (   "+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT "+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   "+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 1  "+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				"  									)   "+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK "+   
				"				WHERE 1=1 and Datediff(dd, hd.ngayhoadon, GETDATE()) > (kh.THOIHANNO+120) "+
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) <= (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="						and kh.NPP_FK = '"+nppId+"'   ";
				
				query+=
				"				GROUP BY  hd.KHACHHANG_FK "+   
				"				UNION ALL "+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA "+   
				"				FROM   	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) "+   
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"				WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK = '"+nppId+"'	";
				
				query+=
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) "+
				"			and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +180) "+
				""			    			+
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"	UNION ALL "+   
				"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0  ) "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 180) "; 
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"	UNION ALL "+   
				""+	
				"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"				INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2 ) "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK				"+
				"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"'  "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and xnkh.NPP_FK = '"+nppId+"'		  ";
				
				query+=					    			
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+  
				"	UNION ALL "+   
				"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"				INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 ) "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"		WHERE 	1=1 and xnkh.TRANGTHAI = 1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' "+ 
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and xnkh.NPP_FK = '"+nppId+"'";
				
				
				query+=
				""			    			+
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"	UNION ALL   "+
				" 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT "+   
				"  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+  
				"   				INNER JOIN (   "+
				"	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO "+   
				"	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   "+
				"	  						WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   "+
				"	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   "+
				"	 					   )   "+
				"	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  "+ 
				"		WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"'  "+
				" 		and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) "+
				" 		and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				" 		GROUP BY hd.KHACHHANG_FK  "+  
				"	UNION ALL "+   
				"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT "+   
				"  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+   
				"   				INNER JOIN (   "+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO "+   
				"	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK "+   
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   "+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   "+
				"	 						)   "+
				"					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK "+   
				"		WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) "+
				" 		and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' ";   
				
				query+=		    			
				" 		GROUP BY hd.KHACHHANG_FK "+  
				"	UNION ALL "+   
				"		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT "+   
				"		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK "+
				"		WHERE 1 =1 and tt.NGAYCHUNGTU < '"+tuNgay+"' "+
				"		and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) "+
				"		and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and ttna.NPP_FK = '"+nppId+"' ";
				
				query+=			    			
				"		GROUP BY ttna.KHACHHANG_FK "+   
				"	UNION ALL "+   
				"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT "+  
				"		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   "+
				"				INNER JOIN (   "+
				"								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU "+   
				"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   "+
				"								WHERE  ct.TRANGTHAI = 1   "+
				"								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK "+   
				"							)   "+
				"							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK    " +				
				"		WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"'  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (k.THOIHANNO+120) \n"+
				"		and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (k.THOIHANNO+180) \n";
				if(nppId.trim().length()>0)
					query+="	and hd.NPP_FK='"+nppId+"' ";
				
				query+=						
				"		GROUP BY hd.KHACHHANG_FK \n"+   			    	
				"  UNION ALL  \n"+
				"		SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT "+    
				"		FROM   	DUNO_KHACHHANG dnkh    "+
				"				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ "+    
				"				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    "+
				"				LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK  "+
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ =  dnkh.KHACHHANG_FK "+ 
				"		WHERE 	1=1 and dnkh.NgayDuNo < ' "+tuNgay+" ' and dnkh.SONO < 0 and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO	+120) "+
				"				and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO	+180) ";
				if(nppId.trim().length()>0)
					query+="	 and  dnkh.NPP_FK = '"+nppId+"' ";
				
				query+=						
				"		GROUP BY dnkh.KHACHHANG_FK "+				
				"		UNION ALL "+				
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n" +				
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 0 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO+180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL   \n"+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 1 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) > (kh.THOIHANNO +120) \n"+
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) <= (kh.THOIHANNO +180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+   
				"			SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"					INNER JOIN KHACHHANG kh on btcn_hd.KHACHHANG_FK = kh.PK_SEQ \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO +180) \n"+
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+  
				"			SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 					INNER JOIN   \n"+
				"	 					(   \n"+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK and tthd.LOAIHD = 2 \n"+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"		 				) \n"+
				"		 				tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			 WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK ='"+nppId+"'  \n";
				
				query+=
				"					   and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+120) \n"+
				"					   and Datediff(dd, dnkh.NgayDuNo, GETDATE()) <= (kh.THOIHANNO +180) \n"+
				"			 GROUP BY dnkh.KHACHHANG_FK   \n"+
				"			 \n"+
				"		UNION ALL	  \n"+
				"			SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		 
				"			FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on kh.PK_SEQ = tttl.khachhang_fk \n"+
				"			WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '"+tuNgay+"' and tttl.NGAYTRA <= '"+denNgay+"' \n"+ 
				"					and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+="			and tttl.NPP_FK = '"+nppId+"' 	  \n";
				
				query+=
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > (kh.THOIHANNO +120) \n"+
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) <= (kh.THOIHANNO +180) \n"+
				"			GROUP BY tttl.KHACHHANG_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+	 
				"			SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"			FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				   INNER JOIN (   \n"+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK \n"+   
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' and  tthd.LOAIHD = 0 \n"+ 
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  								)   \n"+
				"  								tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+  
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+="			and kh.NPP_FK = '"+nppId+"'   \n";
				
				query+=
				"					  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+ 120) \n"+
				"					  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO + 180) \n"+
				"			GROUP BY  hd.KHACHHANG_FK  \n"+
				"	\n"+			 
				"		UNION ALL \n"+   
				"			SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"			FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+ 
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
				"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 120) \n"+
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   	\n"+
				"			\n"+
				"		UNION ALL \n"+   		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"					INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD =0 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 120) \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  \n"+
				"			 \n"+
				"	   UNION ALL \n"+ 		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2) \n"+
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 120) \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"	   UNION ALL  		\n"+
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"'";
				if(nppId.trim().length()>0)
						query+=" 		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 120 ) \n"+
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				"			\n"+
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				" 						   )   \n"+
				"	 							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO + 120 ) \n"+
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO + 180) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				" \n"+	  
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 							)   \n"+
				"					 			bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +120) \n"+
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (kh.THOIHANNO +180) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				" \n"+	  
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   		
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"			WHERE	1 =1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 120) \n"+
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 180) \n"+
				"			GROUP BY ttna.KHACHHANG_FK   \n"+
				" \n"+			
				"		UNION ALL  		\n"+
				"		SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+ 
				"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
				"					INNER JOIN ( \n"+
				"									SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
				"									FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
				"									WHERE  ct.TRANGTHAI = 1 \n"+
				"									GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+ 
				"								) \n"+
				"								ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+ 								
				"			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and hd.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (k.THOIHANNO +120) \n"+
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) <= (k.THOIHANNO+180) \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+
				"		)   	psc group by psc.KHACHHANG_FK \n"+   
				"	)   \n"+
				"	co_qua_han_121_180 on kh.pk_seq = co_qua_han_121_180.khachhang_fk LEFT JOIN \n";
				
							
				query+=
				"	 ( \n"+
				"		SELECT psn.PK_SEQ as khachhang_fk, isnull( sum(tongtienAVAT), 0 ) as tongtienAVAT \n"+   
				"			FROM   \n"+
				"			(   \n"+
				"			---- dư nợ đầu kỳ \n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query += "and kh.NPP_FK = '"+nppId+"'";
				
				query+=
				"					and Datediff(dd,  hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL \n"+
				"			\n"+ 
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, SUM(isnull(avat,0)) tongtienAVAT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"			WHERE 	1=1  and hd.TRANGTHAI = 1 and hd.NGAYHOADON < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query+= " and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  hd.NGAYHOADON, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL \n"+
				"			\n"+
				"			SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+
				"					inner join KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK    \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"'  ";
				if(nppId.trim().length()>0)
					query+=	"and btcn.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd,  btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+180) \n"+				
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"										\n"+
				"			UNION ALL	  \n"+
				"			\n"+
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					inner join (  \n"+
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"								bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "and kh.NPP_FK ='"+nppId+"' \n";
				
				
				query+=
				"				   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"			UNION ALL	  \n"+
				"								\n"+		
				"			SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (   													" +
				"								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"								FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"								)   \n"+
				"					bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"					LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"			WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD <'"+tuNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"			UNION ALL   \n"+
				"			SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"			FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,      \n"+
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,   \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK,             												sum(isnull(thuexuat,0)) as BVAT_CK \n"+       
				"					FROM (     \n"+
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat,   														( \n"+     
				"										SELECT top(1) bb.DDKD_FK     \n"+
				"										FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"										WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"									) as ddkd_fk , 													case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a   \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"	 	 						  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk  \n"+
				"	  							  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"	  							  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq    \n"+
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD <'"+tuNgay+"' \n"+   
				"							and Datediff(dd, a.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"						 )ETC									  	\n"+
				"						 GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"				LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK   \n"+
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= " and hd.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"			GROUP BY hd.khachhang_fk \n"+
				"			\n"+
				"			UNION ALL \n"+ 
				"		  \n"+
				"			SELECT 	dnkh.KHACHHANG_FK PK_SEQ, sum(round(isnull(dnkh.SONO,0),0)) as tongtienAVAT \n"+   
				"			FROM   	DUNO_KHACHHANG dnkh   \n"+
				"					INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK = kh.PK_SEQ \n"+
				"					LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ   \n"+
				"					LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK   \n"+
				"					LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK 			  \n"+
				"			WHERE 	1=1 and dnkh.NgayDuNo < '"+tuNgay+"' and dnkh.SONO >= 0  ";
				if(nppId.trim().length()>0)
					query+=	"and  dnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"			GROUP BY dnkh.KHACHHANG_FK \n"+
				"			\n"+
				"			----------- \n"+
				"			UNION ALL \n"+
				"			\n"+
				"				SELECT hd.KHACHHANG_FK PK_SEQ, SUM(tongtienavat) tongtienAVAT \n"+   
				"				FROM   HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				WHERE  1=1 and  hd.LOAIHOADON = 0 and hd.TRANGTHAI not in ( 1, 3, 5 ) 		\n"+	   
				"					   and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"				GROUP BY hd.KHACHHANG_FK  \n"+
				"		         \n"+
				"			UNION ALL \n"+  
				"				SELECT  btcn_hd.KHACHHANG_FK PK_SEQ, SUM(btcn_hd.GHINO) as tongtienAVAT \n"+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+    
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ = btcn_hd.KHACHHANG_FK \n"+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 \n"+
				"						and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+=	"and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE() ) > (kh.THOIHANNO+180) \n"+                           
				"				GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"				\n"+
				"			UNION ALL \n"+	  
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ, round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM    HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"						inner join (   	SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+   
				"										FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE  1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.LOAIHOADON = 0 and \n"+ 
				"					   hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+= "and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"					   and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"				       \n"+
				"			UNION ALL	  \n"+
				"				SELECT 	hd.KHACHHANG_FK PK_SEQ,  round(bt.tongtienavat,0) tongtienavat \n"+   
				"				FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"						INNER JOIN (   \n"+
				"										SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHINO,0),0)) as tongtienavat \n"+  
				"										FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"										WHERE bt.LoaiBuTru = 1 and bt.TRANGTHAI = 1   \n"+
				"										GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"									)   \n"+
				"									bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				"						 LEFT JOIN NHAPHANPHOI npp on hd.NPP_FK = npp.PK_SEQ   \n"+
				"				WHERE 	1=1 and hd.TRANGTHAI not in ( 1, 3, 5 ) and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="						and kh.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"						and Datediff(dd, hd.NGAYXUATHD, GETDATE() ) > (kh.THOIHANNO+180) \n"+
				"						\n"+
				"			UNION ALL   \n"+
				"				SELECT hd.khachhang_fk, SUM(round( (hdETC.AVAT - hdETC.AVAT_CK),0 )) as tongtienavat \n"+    
				"				FROM   \n"+
				"				(   \n"+
				"					SELECT  ETC.PK_SEQ ,ETC.NGAYXUATHD, ETC.SOHOADON, ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ as HOADONNPP_FK, npp_fk, \n"+   
				"							sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia,       \n"+				
				"							sum(round(( soluong * dongia ),0))  as BVAT,( sum(round((round(( soluong * dongia),0)*thuexuat/100 ),0 ))) as VAT, \n"+    
				"							sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT,  \n"+
				"							round(  sum( ( isnull(chietkhau,0)*(1+thuexuat/100))),0 ) as AVAT_CK, \n"+            
				"							sum(isnull(thuexuat,0)) as BVAT_CK       \n"+
				"					FROM (     \n"+
				"							SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau, c.vat as thuexuat, \n"+   
				"								(     \n"+
				"									SELECT top(1) bb.DDKD_FK \n"+     
				"									FROM   ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+     
				"									WHERE aa.HOADONNPP_FK=c.HOADON_FK    \n"+
				"								) as ddkd_fk , case when c.donvitinh = e.donvi then soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+    
				"									case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia       \n"+
				"							FROM  ERP_HOADONNPP a     \n"+
				"								  INNER JOIN ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+    
				"								  INNER JOIN SANPHAM d on c.sanpham_fk = d.pk_seq    \n"+
				"								  INNER JOIN DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+
				"								  INNER JOIN KHACHHANG kh on a.KHACHHANG_FK = kh.PK_SEQ \n"+  
				"							WHERE 1=1 and c.SOLUONG > 0 and a.trangthai not in ( 1, 3, 5 ) and a.NgayXuatHD >= '"+tuNgay+"' and a.NGAYXUATHD <= '"+denNgay+"' \n"+ 
				"								  and Datediff(dd, a.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"						)ETC	\n"+								  			
				"						GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK, ETC.ddkd_fk, ETC.PK_SEQ, npp_fk \n"+								  
				"				)   \n"+
				"				hdETC INNER JOIN ERP_HOADONNPP hd on hd.PK_SEQ = hdETC.HOADONNPP_FK \n"+   
				"						  LEFT JOIN DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ = hdETC.DDKD_FK \n"+   
				"				WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+= "and hd.NPP_FK ='"+nppId+"' \n";
				
				query+=
				"				GROUP BY hd.khachhang_fk \n"+   
				"		 \n"+
				"			UNION ALL \n"+ 
				"			   SELECT	a.khachhang_fk PK_SEQ, sum(round(isnull(a.avat,0),0)) as tongtienavat \n"+   
				"			   FROM		ERP_HoaDonPheLieu a inner join KHACHHANG kh on a.khachhang_fk = kh.PK_SEQ \n"+
				"			   WHERE	1 = 1 AND a.trangthai = 1 AND a.ngayhoadon >= '"+tuNgay+"' and a.ngayhoadon <= '"+denNgay+"' \n";
				if(nppId.trim().length()>0)
					query+="						and a.npp_fk = '"+nppId+"' ";
				
				query+=
				"						and Datediff(dd,  a.ngayhoadon, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"			   GROUP BY a.khachhang_fk \n"+
				"			)   \n"+
				"			psn   \n"+
				"			GROUP BY psn.PK_SEQ \n"+ 
				"	 ) \n"+
				"	 no_qua_han_tren_180 on kh.pk_seq = no_qua_han_tren_180.khachhang_fk left join \n"+ 
				" ( \n"+   
				"		SELECT 	psc.KHACHHANG_FK, round(SUM (isnull(psc.SOTIENTT,0)),0) as SOTIENTT \n"+  
				"		FROM   \n"+
				"		( \n"+   
				"			---- dư nợ có quá hạn \n"+
				""		+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT "+   
				"				FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+  
				"						INNER JOIN (   "+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT "+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK  "+ 
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and  tthd.LOAIHD = 0 "+
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				" 								   ) 	  "+
				" 									tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   "+
				"				WHERE 	1=1 ";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) "+
				""					+
				"				GROUP BY hd.KHACHHANG_FK "+   
				"				UNION ALL   "+
				"				SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT "+   
				"				FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on  btcn.PK_SEQ = btcn_hd.BTCN_FK  "+
				"						INNER JOIN KHACHHANG kh on kh.PK_SEQ =  btcn_hd.KHACHHANG_FK "+
				"				WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU < '"+tuNgay+"' ";   
				if(nppId.trim().length()>0)
					query+="    					  and btcn.NPP_FK='"+nppId+"' ";
				
				query+=
				"						and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+180) "+		
				"  				GROUP BY btcn_hd.KHACHHANG_FK "+   
				"				UNION ALL  "+
				"				SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT "+   
				"				FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ "+   
				"	 				   INNER JOIN   "+
				"	 					(   "+
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT "+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK "+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 2 ";
				if(nppId.trim().length()>0)
					query+="										and tt.NPP_FK = '"+nppId+"'						  ";
				
				query+=
				"			    						"+
				"						GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK "+   
				"		 			)  "+
				"		 			tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK "+   
				"		 		WHERE 1=1  and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="			 and kh.NPP_FK = '"+nppId+"' ";
				
				query+=				
				"		 		GROUP BY dnkh.KHACHHANG_FK "+   
				"				UNION ALL	 "+
				"				SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT "+ 		  
				"				FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on tttl.khachhang_fk = kh.PK_SEQ "+
				"				WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA < '"+tuNgay+"' and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+="		and tttl.NPP_FK = '"+nppId+"' ";
				
				query+=
				"	and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > (kh.THOIHANNO+180) "+
				"				GROUP BY tttl.KHACHHANG_FK "+   
				"				UNION ALL	  "+
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT "+   
				"				FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "+  
				"			   			INNER JOIN (   "+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT "+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   "+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 0  "+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				"  									)   "+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK "+   
				"					WHERE 1=1 and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="				and kh.NPP_FK = '"+nppId+"' ";
				
				query+=
				"				GROUP BY  hd.KHACHHANG_FK "+   
				"				UNION ALL "+   
				"				SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT "+   
				"				FROM   ERP_HOADONPHELIEU hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ "+  
				"			   			INNER JOIN (   "+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT "+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   "+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU < '"+tuNgay+"' and tthd.LOAIHD = 1  "+
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   "+
				"  									)   "+
				"  									tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK "+   
				"				WHERE 1=1 and Datediff(dd, hd.ngayhoadon, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="						and kh.NPP_FK = '"+nppId+"'   ";
				
				query+=
				"				GROUP BY  hd.KHACHHANG_FK "+   
				"				UNION ALL "+   
				"				SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA "+   
				"				FROM   	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) "+   
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"				WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK = '"+nppId+"'	";
				
				query+=
				"						and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+180) "+
				""			    			+
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"	UNION ALL "+   
				"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"				INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0  ) "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+60) "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) <= (kh.THOIHANNO + 120) "; 
				if(nppId.trim().length()>0)
					query+="		and xnkh.NPP_FK = '"+nppId+"' ";		  
				
				query+=
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"	UNION ALL "+   
				""+	
				"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"				INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2 ) "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK				"+
				"		WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"'  "+
				"		and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and xnkh.NPP_FK = '"+nppId+"'		  ";
				
				query+=				
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				""+
				"	UNION ALL "+   
				"		SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT "+   
				"		FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK "+  
				"				INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1 ) "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK "+
				"		WHERE 	1=1 and xnkh.TRANGTHAI = 1 and XNKH.NGAYCHUNGTU < '"+tuNgay+"' "+ 
				"				and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="				and xnkh.NPP_FK = '"+nppId+"'";
				
				query+=
				"		GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK "+   
				"	UNION ALL   "+
				" 		SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT "+   
				"  		FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+  
				"   				INNER JOIN (   "+
				"	  						SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO "+   
				"	  						FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   "+
				"	  						WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   "+
				"	  						GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   "+
				"	 					   )   "+
				"	 						bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK  "+ 
				"		WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"'  "+
				" 		and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' ";
				
				query+=	
				" 		GROUP BY hd.KHACHHANG_FK  "+ 
				"	UNION ALL "+   
				"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT "+   
				"  		FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ "+   
				"   				INNER JOIN (   "+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO "+   
				"	  							FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK "+   
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   "+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   "+
				"	 						)   "+
				"					 		bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK "+   
				"		WHERE 	1=1 and hd.NGAYXUATHD < '"+tuNgay+"' and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' ";
				
				query+=		    			
				" 		GROUP BY hd.KHACHHANG_FK "+   
				"	UNION ALL "+   
				"		SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT "+   
				"		FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ "+   
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK "+
				"		WHERE 1 =1 and tt.NGAYCHUNGTU < '"+tuNgay+"' "+
				"		and Datediff(dd, tt.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO+180) ";
				if(nppId.trim().length()>0)
					query+="	and ttna.NPP_FK = '"+nppId+"' ";
				
				query+=
				""+				    			
				"		GROUP BY ttna.KHACHHANG_FK "+   
				"	UNION ALL "+   
				"		SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(SOTIENCANTRU,0),0)) as tongtienTT "+  
				"		FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ   "+
				"				INNER JOIN (   "+
				"								SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU "+   
				"								FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK   "+
				"								WHERE  ct.TRANGTHAI = 1   "+
				"								GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK "+   
				"							)   "+
				"							ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK    " +				
				"		WHERE 	1=1 and hd.NGAYXUATHD <'"+tuNgay+"'  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (k.THOIHANNO+180) \n";
				if(nppId.trim().length()>0)
					query+="	and hd.NPP_FK='"+nppId+"' ";
				
				query+=							    			
				"		GROUP BY hd.KHACHHANG_FK \n"+   			    	
				"  UNION ALL  \n"+
				"		SELECT 	dnkh.KHACHHANG_FK , sum(round(isnull(dnkh.SONO*(-1),0),0)) as tongtienTT "+    
				"		FROM   	DUNO_KHACHHANG dnkh    "+
				"				LEFT JOIN NHAPHANPHOI npp on dnkh.NPP_FK = npp.PK_SEQ "+    
				"				LEFT JOIN KHUVUC kv on kv.PK_SEQ = npp.KHUVUC_FK    "+
				"				LEFT JOIN VUNG v on v.PK_SEQ = kv.VUNG_FK  "+
				"				INNER JOIN KHACHHANG kh on kh.PK_SEQ =  dnkh.KHACHHANG_FK "+ 
				"		WHERE 	1=1 and dnkh.NgayDuNo < ' "+tuNgay+" ' and dnkh.SONO < 0 and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO	+180) ";
				if(nppId.trim().length()>0)
					query+="	 and  dnkh.NPP_FK = '"+nppId+"' ";
				
				query+=	
				"		GROUP BY dnkh.KHACHHANG_FK "+
				""		+
				"		UNION ALL "+
				
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"					INNER JOIN (   \n"+
				"  									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 0 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK   \n"+
				"			WHERE 	1=1 	";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO+180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				"		UNION ALL   \n"+
				"			SELECT 	hd.KHACHHANG_FK, SUM(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM 	ERP_HOADONPHELIEU hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"					INNER JOIN (   \n"+
				" 									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"  									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				"  									WHERE tt.TRANGTHAI = 1 and tt.NGAYCHUNGTU >= '"+tuNgay+"'  and tt.NGAYCHUNGTU <= '"+denNgay+"' and tthd.LOAIHD = 1 \n"+  
				"  									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				" 								) 	  \n"+
				" 								tt on hd.KHACHHANG_FK = tt.KHACHHANG_FK and hd.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			WHERE 	1=1 		";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and Datediff(dd, hd.ngayhoadon, GETDATE()) > (kh.THOIHANNO +180) \n"+
				"			GROUP BY hd.KHACHHANG_FK   \n"+
				" \n"+			
				"		UNION ALL \n"+   
				"			SELECT  btcn_hd.KHACHHANG_FK, SUM(btcn_hd.GHICO) as tongtienTT \n"+   
				"			FROM 	BUTRUCONGNO btcn inner join BUTRUCONGNO_HOADON btcn_hd on btcn.PK_SEQ = btcn_hd.BTCN_FK \n"+  
				"					INNER JOIN KHACHHANG kh on btcn_hd.KHACHHANG_FK = kh.PK_SEQ \n"+
				"			WHERE 	btcn.LoaiBuTru = 1 and btcn.TRANGTHAI = 1 and btcn.NGAYCHUNGTU >= '"+tuNgay+"' and btcn.NGAYCHUNGTU <= '"+denNgay+"'";
				if(nppId.trim().length()>0)
					query+=" 	and btcn.NPP_FK='"+nppId+"' \n";
				
				query+=
				"			        and Datediff(dd, btcn.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO +180) \n"+
				"			GROUP BY btcn_hd.KHACHHANG_FK   \n"+
				"	\n"+		
				"		UNION ALL \n"+  
				"			SELECT dnkh.KHACHHANG_FK, sum(round(isnull(tt.sotienTT,0),0)) as SOTIENTT \n"+   
				"			FROM   DUNO_KHACHHANG dnkh INNER JOIN KHACHHANG kh on dnkh.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"	 					INNER JOIN   \n"+
				"	 					( \n"+   
				"							SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(ISNULL(tthd.sotienTT,0),0)) as sotienTT \n"+   
				"							FROM   ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK and tthd.LOAIHD = 2 \n"+  
				"							WHERE  tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
				query+=	"		and tt.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"							GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"		 				) \n"+
				"		 				tt on dnkh.KHACHHANG_FK = tt.KHACHHANG_FK and dnkh.PK_SEQ = tt.HOADONNPP_FK \n"+   
				"			 WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+="		and kh.NPP_FK ='"+nppId+"'  \n";
				
				query+=
				"					   and Datediff(dd, dnkh.NgayDuNo, GETDATE()) > (kh.THOIHANNO +180) \n"+
				"			 GROUP BY dnkh.KHACHHANG_FK   \n";
				
				
				query+=
				" \n"+			 
				"		UNION ALL \n"+	  
				"			SELECT 	tttl.KHACHHANG_FK, SUM(round(isnull(tttl.TienSauThue,0),0)) as SOTIENTT \n"+ 		 
				"			FROM   	Erp_HangTraLaiNpp  tttl	INNER JOIN KHACHHANG kh on kh.PK_SEQ = tttl.khachhang_fk \n"+
				"			WHERE 	1=1 and tttl.trangthai=1 and tttl.NGAYTRA >= '"+tuNgay+"' and tttl.NGAYTRA <= '"+denNgay+"' \n"+ 
				"					and KHACHHANG_FK is not null ";
				if(nppId.trim().length()>0)
					query+="			and tttl.NPP_FK = '"+nppId+"' 	  \n";
				
				query+=
				"					and Datediff(dd, tttl.NgayHoaDon, GETDATE()) > (kh.THOIHANNO +180) \n"+
				"			GROUP BY tttl.KHACHHANG_FK   \n"+
				" \n"+			
				"		UNION ALL \n"+	 
				"			SELECT hd.KHACHHANG_FK, Sum(round(isnull(tt.sotienTT,0),0)) as tongtienTT \n"+   
				"			FROM   ERP_HOADONNPP hd inner join KHACHHANG kh on hd.KHACHHANG_FK = kh.PK_SEQ \n"+   
				"				   INNER JOIN (   \n"+
				"									SELECT tthd.KHACHHANG_FK, tthd.HOADONNPP_FK, SUM(round(isnull(tthd.SOTIENTT,0),0)) as sotienTT \n"+   
				" 									FROM ERP_THUTIENNPP tt inner join ERP_THUTIENNPP_HOADON tthd on tt.PK_SEQ = tthd.THUTIENNPP_FK   \n"+
				" 									WHERE tt.TRANGTHAI=1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' and  tthd.LOAIHD = 0 \n"+ 
				" 									GROUP BY tthd.KHACHHANG_FK, tthd.HOADONNPP_FK   \n"+
				"  								)   \n"+
				"  								tt on hd.KHACHHANG_FK=tt.KHACHHANG_FK and hd.PK_SEQ=tt.HOADONNPP_FK \n"+  
				"			WHERE 1=1 ";
				if(nppId.trim().length()>0)
					query+="	and kh.NPP_FK = '"+nppId+"'   \n";
				
				query+=
				"					  and Datediff(dd, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO + 180) \n"+
				"			GROUP BY  hd.KHACHHANG_FK  \n"+
				" \n"+			 
				"		UNION ALL \n"+   
				"			SELECT xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as SOTIENXOA \n"+   
				"			FROM   XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"		   				INNER JOIN HOADON hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 0 ) \n"+ 
				"		   				INNER JOIN KHACHHANG kh on kh.PK_SEQ = hd.KHACHHANG_FK \n"+
				"			WHERE  1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"'";
				if(nppId.trim().length()>0)
						query+= " and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"				   and Datediff(dd, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   	\n"+
				" \n"+			
				"		UNION ALL \n"+   		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+   
				"					INNER JOIN ERP_HOADONNPP hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD =0 ) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK  \n"+
				" \n"+			 
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN DUNO_KHACHHANG hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 2) \n"+
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK   \n"+
				" \n"+			
				"	   UNION ALL \n"+  		
				"			SELECT 	xnkh_hd.KHACHHANG_FK, SUM(round(ISNULL(xnkh_hd.SOTIENXOA,0),0)) as tongtienTT \n"+   
				"			FROM 	XOANOKHACHHANG xnkh INNER JOIN XOANOKHACHHANG_HOADON xnkh_hd on xnkh.PK_SEQ = xnkh_hd.XNKH_FK \n"+  
				"					INNER JOIN ERP_HOADONPHELIEU hd on (xnkh_hd.HOADON_FK = hd.PK_SEQ and xnkh_hd.KHACHHANG_FK = hd.KHACHHANG_FK and xnkh_hd.LOAIHD = 1) \n"+   
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = xnkh_hd.KHACHHANG_FK \n"+
				"			WHERE 	1=1 and xnkh.TRANGTHAI =1 and XNKH.NGAYCHUNGTU >= '"+tuNgay+"' and XNKH.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
					query+="			and xnkh.NPP_FK='"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, xnkh.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 180) \n"+
				"			GROUP BY xnkh_hd.KHACHHANG_FK, xnkh_hd.HOADON_FK \n"+  
				" \n"+			
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, Sum(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	HOADON hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ   \n"+
				"   					INNER JOIN (   \n"+
				"	  							SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  							FROM   BUTRUCONGNO bt INNER JOIN BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  							WHERE  bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  							GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 						   ) \n"+   
				"	 							bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO + 180) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				" \n"+	  
				"		UNION ALL \n"+   
				"			SELECT  hd.KHACHHANG_FK, SUM(round(ISNULL(GHICO,0),0)) as tongtienTT \n"+   
				"  			FROM 	ERP_HOADONNPP hd INNER JOIN KHACHHANG kh on hd.KHACHHANG_FK=kh.PK_SEQ \n"+   
				"   					INNER JOIN (   \n"+
				"	  								SELECT bthd.KHACHHANG_FK, bthd.HOADON_FK, SUM(round(ISNULL(bthd.GHICO,0),0)) as GHICO \n"+   
				"	  								FROM   BUTRUCONGNO bt inner join BUTRUCONGNO_HOADON bthd on bt.PK_SEQ = bthd.BTCN_FK   \n"+
				"	  								WHERE bt.LoaiBuTru = 0 and bt.TRANGTHAI = 1   \n"+
				"	  								GROUP BY bthd.KHACHHANG_FK, bthd.HOADON_FK   \n"+
				"	 							)   \n"+
				"					 			bt on hd.KHACHHANG_FK = bt.KHACHHANG_FK and hd.PK_SEQ = bt.HOADON_FK \n"+   
				" 			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and kh.NPP_FK = '"+nppId+"' \n";
				
				query+=
				" 					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (kh.THOIHANNO +180) \n"+
				" 			GROUP BY hd.KHACHHANG_FK   \n"+
				" \n"+	  
				"		UNION ALL \n"+   
				"			SELECT 	ttna.KHACHHANG_FK, Sum(round(ISNULL(ttna.SOTIENTT,0),0)) as tongtienTT \n"+   
				"			FROM 	ERP_THUTIENNPP_HOADONTHEM ttna INNER JOIN ERP_THUTIENNPP tt on ttna.THUTIEN_FK = tt.PK_SEQ \n"+   		
				"					INNER JOIN KHACHHANG kh on kh.PK_SEQ = ttna.KHACHHANG_FK \n"+
				"		WHERE	1 =1 and tt.NGAYCHUNGTU >= '"+tuNgay+"' and tt.NGAYCHUNGTU <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="		and ttna.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, tt.NGAYCHUNGTU, GETDATE()) > (kh.THOIHANNO + 180) \n"+
				"			GROUP BY ttna.KHACHHANG_FK   \n"+
				" \n"+			
				 "		UNION ALL \n"+  		
				"		SELECT  hd.KHACHHANG_FK, isnull(SUM(round(ISNULL(SOTIENCANTRU,0),0)),0) as tongtienTT \n"+ 
				"			FROM 	HOADON hd INNER JOIN KHACHHANG k on hd.KHACHHANG_FK=k.PK_SEQ \n"+
				"					INNER JOIN ( \n"+
				"									SELECT cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
				"									FROM   CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+
				"									WHERE  ct.TRANGTHAI = 1 \n"+
				"									GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK \n"+ 
				"							) \n"+
				"								ct on hd.KHACHHANG_FK = ct.KHACHHANG_FK and hd.PK_SEQ = ct.HOADON_FK \n"+ 								
				"			WHERE 	1=1 and hd.NGAYXUATHD >= '"+tuNgay+"' and hd.NGAYXUATHD <= '"+denNgay+"' ";
				if(nppId.trim().length()>0)
						query+="			and hd.NPP_FK = '"+nppId+"' \n";
				
				query+=
				"					and DATEDIFF(DD, hd.NGAYXUATHD, GETDATE()) > (k.THOIHANNO +180) \n"+
				"			GROUP BY hd.KHACHHANG_FK \n"+
				"		)   	psc group by psc.KHACHHANG_FK \n"+   
				"	) \n"+   
				"	co_qua_han_tren_180 on kh.pk_seq = co_qua_han_tren_180.khachhang_fk \n"+
				" where 1=1 "+ condition+condition1 +
				" and  ( isnull(psn.tongtienAVAT, 0) !=0 or isnull(psc.sotienTT, 0) !=0 or isnull(dcdk.SOTIENTT,0) !=0 or \n" +
				"	 isnull(no_trong_han.tongtienAVAT, 0) !=0 or isnull(no_qua_han.tongtienAVAT, 0) !=0 or \n"+
				"	 isnull(no_qua_han_1_60.tongtienAVAT, 0) !=0 or isnull(no_qua_han_61_120.tongtienAVAT, 0) !=0 or \n"+
				"	isnull(no_qua_han_121_180.tongtienAVAT, 0) !=0 or isnull(no_qua_han_tren_180.tongtienAVAT, 0) !=0) \n"+
				 "order by kh.PK_SEQ \n";
				//System.out.println(query);	
		        	ToExcel(response, obj, query, tuNgay, denNgay, KhachHangid, userTen, nppId);	
			}
			
			 else
		        {
		        
				 	String nextJSP="";
					if(!view.equals("TT"))
			 		{
			 			nextJSP = "/TraphacoHYERP/pages/Distributor/BCPhanTichCongNoKH_distributor.jsp"; 			
			 		}
			 	    
			 	    else
			 		{
			 	    	nextJSP = "/TraphacoHYERP/pages/Center/BCPhanTichCongNoKH_center.jsp";
			 		}
		    	
					obj.createKhRS1();
		    	
					session.setAttribute("obj", obj);
		    	
					response.sendRedirect(nextJSP);
		        }
			
	}

	private void ToExcel(HttpServletResponse response, IStockintransit obj, String query, String tuNgay, String denNgay, String KhachHangid, String userTen, String nppId) throws IOException
	{
		System.out.println("User Ten:"+ userTen);
		OutputStream out = null;
		try
		{
			dbutils db = new dbutils();
			//NumberFormat formatter = new DecimalFormat("#,###,###");
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoPhanTichCongNoKH_distributor.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 7;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			WritableFont cellFont = new WritableFont(WritableFont.ARIAL, 13);
			cellFont.setColour(Colour.BLACK);
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			
			//sheet.addCell(new Label(0, 0, userTen, cellFormatTD));								
			//sheet.mergeCells(0, 0, 2, 0);
			
			/*ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+nppId+"'");
			String diachinpp="";
			if(dc!=null)
			{
				while (dc.next())
					diachinpp=dc.getString("DiaChi");
			}*/
			
			//sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
			
			sheet = w.createSheet("PhanTichCongNoKH", k);//ten sheet
			sheet.addCell(new Label(0, 1, "BÁO CÁO PHÂN TÍCH CÔNG NỢ KHÁCH HÀNG ", celltieude));			
			//sheet.addMergedRegion(new CellRangeAddress(1,1,4,1))		
			
			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.mergeCells(0, 1, 7, 1);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			sheet.addCell(new Label(0, 3, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(1, 3, tuNgay)); // lấy ngày đã chọn
			
			sheet.addCell(new Label(0, 4, "Đến ngày: "));
			sheet.addCell(new Label(1, 4, denNgay)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			WritableCellFormat cellFormat = new WritableCellFormat(cellFontWhite);

			cellFormat.setBackground(jxl.format.Colour.GRAY_80);
			cellFormat.setWrap(true);
			cellFormat.setAlignment(Alignment.CENTRE);
			cellFormat.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);

			WritableCellFormat cellFormatSpecical = new WritableCellFormat(cellFont);

			cellFormatSpecical.setBackground(jxl.format.Colour.LIGHT_ORANGE);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.addCell(new Label(0, 6, "STT", cellFormat));
			//sheet.mergeCells(0, 7, 0 , 8); //bắt đầu từ cột 0,  dòng mấy , số cột merger, số dòng merger
			sheet.addCell(new Label(1, 6, "Trình dược viên", cellFormat));
			
			sheet.addCell(new Label(2, 6, "Mã FAST", cellFormat));
			//sheet.mergeCells(1, 7, 1 , 8);
			sheet.addCell(new Label(3, 6, "Tên khách hàng", cellFormat));
			//sheet.mergeCells(2, 7, 2 , 8);
			sheet.addCell(new Label(4, 6, "Hạn TT", cellFormat));
			//sheet.mergeCells(3, 7,4 , 8);
			sheet.addCell(new Label(5, 6, "Nợ đầu kỳ", cellFormat));
			//sheet.mergeCells(4, 7, 5 , 8);
			sheet.addCell(new Label(6, 6, "PS nợ", cellFormat));
			//sheet.mergeCells(5, 7, 6 , 8);
			sheet.addCell(new Label(7, 6, "PS có", cellFormat));
			//sheet.mergeCells(6, 7, 7 , 8);
			sheet.addCell(new Label(8, 6, "Nợ CK", cellFormat));
			//sheet.mergeCells(7, 7, 8 , 8);
			sheet.addCell(new Label(9, 6, "Trong hạn", cellFormat));
			//sheet.mergeCells(8, 7, 9 , 8);
			sheet.addCell(new Label(10, 6, "Tổng quá hạn", cellFormat));
			
			sheet.addCell(new Label(11, 6, "QH 1-60", cellFormat));
			//sheet.mergeCells(9, 7, 10 , 10);
			//sheet.addCell(new Label(10, 7, "NỢ QUÁ HẠN CHIA THEO NGÀY NỢ", cellFormat));			
			//sheet.mergeCells(10, 7, 13 , 7);			
			sheet.addCell(new Label(12, 6, "QH 61-120", cellFormat));	
			sheet.addCell(new Label(13, 6, "QH 121-180", cellFormat));	
			sheet.addCell(new Label(14, 6, "QH >180", cellFormat));	
			
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 20);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 70);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			sheet.setColumnView(7, 20);
			sheet.setColumnView(8, 20);
			sheet.setColumnView(9, 20);
			sheet.setColumnView(10, 20);
			sheet.setColumnView(11, 20);
			sheet.setColumnView(12, 20);
			sheet.setColumnView(13, 20);
			sheet.setColumnView(14, 20);
			sheet.setColumnView(15, 20);

			ResultSet rs = db.get(query);

			WritableCellFormat cellFormat2 = new WritableCellFormat(cellFont);

			cellFormat2.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat2.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cellFormat3 = new WritableCellFormat(cellFont);
			cellFormat3.setBackground(jxl.format.Colour.YELLOW);
			cellFormat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cellFormat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			WritableCellFormat cformat = new WritableCellFormat(cellFont);
			
			WritableCellFormat cformat3 = new WritableCellFormat(cellFont);
			cformat3.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat3.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			WritableCellFormat cformat1 = new WritableCellFormat(cellFont);
			cformat1.setAlignment(Alignment.RIGHT);
			cformat1.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			cformat1.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			//Chinh sua
		
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);
			
			Label label;
			Number number;

			int stt = 0;
			double ndk = 0;
			double dcdk=0;
			double psn=0;
			double psc=0;
			double nck=0;
			double tqh=0;
			double nth=0;
			double qh1_60=0;
			double qh60_120=0;
			double qh121_180=0;
			double qh181=0;
						
			double totalpsn=0;
			double totalpsc=0;
			double totalnck=0;
			double totalnth=0;
			double totaltqh=0;
			double totalqh1_60=0;
			double totalqh60_120=0;
			double totalqh120_180=0;
			double totalqh181=0;
			
			double notronghan = 0;
			double cotronghan = 0;
			double noquahan = 0;
			double coquahan = 0;
			double noquahan_1_60 = 0;
			double coquahan_1_60 = 0;
			double noquahan_61_120 =0;
			double coquahan_61_120 =0;
			double noquahan_121_180 =0;
			double coquahan_121_180 =0;
			double noquahan_181 =0;
			double coquahan_181 =0;
			
			if(rs!=null)
			{
			while (rs.next())
			{
				stt++;				
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("DDKD"), cformat3);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("mafast"), cformat3);
				sheet.addCell(label);
				label = new Label(3, j, rs.getString("TEN"), cformat3);
				sheet.addCell(label);	
				label = new Label(4, j, rs.getString("thoihanNO"), cformat3);
				sheet.addCell(label);
				ndk = rs.getDouble("nodauky");
				dcdk = rs.getDouble("dcdk");
				
				number = new Number(5, j,ndk-dcdk, inFormat);sheet.addCell(number);				
				psn = rs.getDouble("phatsinhno");
				number = new Number(6, j,psn, inFormat);sheet.addCell(number);
				psc = rs.getDouble("phatsinhco");
				number = new Number(7, j,psc, inFormat);sheet.addCell(number);
				nck=ndk-dcdk+psn-psc;				
					
				number = new Number(8, j,nck, inFormat);sheet.addCell(number);
				notronghan = rs.getDouble("notronghan");
				cotronghan = rs.getDouble("cotronghan");
				nth = notronghan - cotronghan;
				if(nck <= 0) nth = 0;
				number = new Number(9, j,nth, inFormat);sheet.addCell(number);
				
				noquahan = rs.getDouble("noquahan");
				coquahan = rs.getDouble("coquahan");	
				if(nck <= 0) {
					noquahan = 0;
					coquahan = 0;
				}
				tqh = noquahan -  coquahan;
				
				
				noquahan_1_60 = rs.getDouble("no_qua_han_1_60");
				coquahan_1_60 = rs.getDouble("co_qua_han_1_60");
				
				if(nck <= 0)
				{
					noquahan_1_60 = 0;
					coquahan_1_60 = 0;
				}
					
				qh1_60=noquahan_1_60-coquahan_1_60;
				
				noquahan_61_120 = rs.getDouble("no_qua_han_61_120");
				coquahan_61_120 = rs.getDouble("co_qua_han_61_120");	
				
				if(nck <= 0)
				{
					noquahan_61_120 = 0;
					coquahan_61_120 = 0;
				}
				qh60_120 = noquahan_61_120 - coquahan_61_120;
				
				noquahan_121_180 = rs.getDouble("no_qua_han_121_180");
				coquahan_121_180 = rs.getDouble("co_qua_han_121_180");	
				
				if(nck <= 0){
					noquahan_121_180 = 0;
					coquahan_121_180 = 0;
					
				}
				qh121_180 = noquahan_121_180 - coquahan_121_180;
				
				noquahan_181 = rs.getDouble("no_qua_han_tren_180");
				coquahan_181 = rs.getDouble("co_qua_han_tren_180");		
				
				if(nck <= 0){
					noquahan_181 = 0;
					coquahan_181 = 0;
				}
				
				qh181 = noquahan_181 - coquahan_181;
								
				//tqh = qh1_60+qh60_120+qh121_180+qh181;				
				
				number = new Number(10, j,tqh, inFormat);sheet.addCell(number);
				number = new Number(11, j,qh1_60, inFormat);sheet.addCell(number);	
				number = new Number(12, j,qh60_120, inFormat);sheet.addCell(number);
				number = new Number(13, j,qh121_180, inFormat);sheet.addCell(number);
				number = new Number(14, j,qh181, inFormat);sheet.addCell(number);
				
				//SoTienTT += rs.getDouble("SoTienTT");				
				//label = new Label(7, j, formatter.format(rs.getDouble("SoTienConLai")),cformat1);
				//SoTienConLai+= rs.getDouble("SoTienConLai");
				//sheet.addCell(label);
				totalpsn+= psn;
				totalpsc+=psc;
				totalnck+=nck;
				totalnth+=nth;
				totaltqh+=tqh;
				totalqh1_60+=qh1_60;
				totalqh60_120+=qh60_120;
				totalqh120_180+=qh121_180;
				totalqh181+=qh181;
				j++;
			}
				sheet.addCell(new Label(1, j, "TỔNG CỘNG", cellFormat));
				number = new Number(6, j,totalpsn, inFormat);sheet.addCell(number);
				number = new Number(7, j,totalpsc, inFormat);sheet.addCell(number);
				number = new Number(8, j,totalnck, inFormat);sheet.addCell(number);
				number = new Number(9, j,totalnth, inFormat);sheet.addCell(number);	
				number = new Number(10, j,totaltqh, inFormat);sheet.addCell(number);
			}
			else{throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");}
			
			/*if(rs!=null)
			{
			while (rs.next())
			{
				stt++;				
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("mafast"), cformat3);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("TEN"), cformat3);
				sheet.addCell(label);	
				label = new Label(3, j, rs.getString("thoihanNO"), cformat3);
				sheet.addCell(label);
				ndk = rs.getDouble("nodauky");
				label = new Label(4, j, formatter.format(ndk), cformat1);
				sheet.addCell(label);
				psn = rs.getDouble("phatsinhno");
				label = new Label(5, j, formatter.format(psn), cformat1);
				sheet.addCell(label);
				psc = rs.getDouble("phatsinhco");
				label = new Label(6, j, formatter.format(psc), cformat1);
				sheet.addCell(label);
				nck=ndk+psn-psc;
				label = new Label(7, j, formatter.format(nck), cformat1);
				sheet.addCell(label);				
				label = new Label(8, j, formatter.format(rs.getDouble("notronghan")), cformat1);
				sheet.addCell(label);
				qh1_60=rs.getDouble("no_qua_han_1_60");
				qh60_120 = rs.getDouble("no_qua_han_61_120");
				qh121_180=rs.getDouble("no_qua_han_121_180");
				qh181 = rs.getDouble("no_qua_han_tren_180");
				tqh = qh1_60+qh60_120+qh121_180+qh181;
				
				label = new Label(9, j, formatter.format(tqh), cformat1);
				sheet.addCell(label);
				label = new Label(10, j, formatter.format(qh1_60), cformat1);
				sheet.addCell(label);
				label = new Label(11, j, formatter.format(qh60_120), cformat1);
				sheet.addCell(label);
				label = new Label(12, j, formatter.format(qh121_180), cformat1);
				sheet.addCell(label);
				label = new Label(13, j, formatter.format(qh181), cformat1);
				sheet.addCell(label);
				
				//SoTienTT += rs.getDouble("SoTienTT");				
				//label = new Label(7, j, formatter.format(rs.getDouble("SoTienConLai")),cformat1);
				//SoTienConLai+= rs.getDouble("SoTienConLai");
				//sheet.addCell(label);
				j++;
			}
			}
			else{throw new Exception("Khong có dữ liệu bao cao trong thoi gian nay...");}*/
			/*if(j>8)
			{
			sheet.addCell(new Label(1, j, "TỔNG CỘNG", cellFormat));
			label = new Label(6, j, formatter.format(SoTienTT), cformat1);
			sheet.addCell(label);
			
			label = new Label(7, j, formatter.format(SoTienConLai), cformat1);
			sheet.addCell(label);
			}*/
			w.write();
			w.close();
			rs.close();
			db.shutDown();
		} catch (Exception e)
		{
			System.out.println("Lỗi : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			if (out != null)
				out.close();

		}
	}
	
	
	
}
