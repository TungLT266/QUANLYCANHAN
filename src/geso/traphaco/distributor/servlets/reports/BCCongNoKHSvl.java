package geso.traphaco.distributor.servlets.reports;


import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;
import geso.traphaco.distributor.beans.reports.IBCCongNoKH;
import geso.traphaco.distributor.beans.reports.imp.BCCongNoKH;

import java.io.IOException;
import java.io.OutputStream;
import java.sql.Date;
import java.sql.ResultSet;
import java.text.DateFormat;


import java.io.*;
import java.text.SimpleDateFormat;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.examples.MergedCells;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.examples.MergingCells;
import org.apache.poi.hssf.record.MergeCellsRecord.MergedRegion;

import Z.DB;

import com.extentech.formats.XLS.Mergedcells;

import jxl.format.Alignment;
import jxl.format.Border;
import jxl.format.BorderLineStyle;
import jxl.format.Colour;
import jxl.format.VerticalAlignment;
import jxl.write.Label;
import jxl.write.Number;
import jxl.write.NumberFormat;
import jxl.write.WritableCellFormat;
import jxl.write.WritableFont;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;

public class BCCongNoKHSvl extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
       
    public BCCongNoKHSvl() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub	
		
		IBCCongNoKH obj = new BCCongNoKH();
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
		Utility util=new Utility();
		String userTen = (String)session.getAttribute("userTen");
		String querystring=request.getQueryString();
		String userId = util.getUserId(querystring);
		String nppId= util.getIdNhapp(userId);
		
		session.setAttribute("userTen", userTen);
		session.setAttribute("userId", userId);
		session.setAttribute("tungay", "");
 		session.setAttribute("denngay","");
    	session.setAttribute("loi", "");
    	
    	System.out.println("userId" + userId);
    	
    	obj.setUserId(userId);
    	obj.setUserName(userTen);
    	System.out.println("DA VAO DAY.....");
    		  		
    	obj.init1();
    	
    	String view="";
    	if(nppId!=null)
   		   view = "NPP";
   	   if(nppId==null)
   		   view ="TT";
    	    	
    	
    	//System.out.println("DA VAO DAY.....");
    	
    	String nextJSP ="";
    	if(view.equals("TT"))
    	{
    		nextJSP = "/TraphacoHYERP/pages/Center/BCCongNoKH_TT.jsp";
    	}
    	if(view.equals("NPP"))
    	{
    		nextJSP = "/TraphacoHYERP/pages/Distributor/BCCongNoKH.jsp";
    	}
		
    	session.setAttribute("obj", obj);
		response.sendRedirect(nextJSP);
	}
//
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		IBCCongNoKH obj = new BCCongNoKH();
		
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
	//	PrintWriter out = response.getWriter();
		HttpSession session = request.getSession();
        Utility util = new Utility();
        String tuNgay = util.antiSQLInspection(request.getParameter("tuNgay"));
        obj.setTuNgay(tuNgay);
        String denNgay = util.antiSQLInspection(request.getParameter("denNgay"));
        obj.setDenNgay(denNgay);
        
        String KhachHang = util.antiSQLInspection(request.getParameter("KhachHang"));
        if(KhachHang==null)
        	KhachHang="";
        obj.setKHId(KhachHang);
        
        session.setAttribute("tungay", tuNgay);
        session.setAttribute("denngay", denNgay);
        String action = request.getParameter("action");
        String userTen = (String)session.getAttribute("userTen");
        String userId = (String) session.getAttribute("userId"); 
        
        
        obj.setUserId(userId);
        obj.setUserName(userTen);
        
        String nppId= util.getIdNhapp(userId);
        System.out.println("nppId: "+ nppId);
        
        String nppID="";
        
        String view="";
    	if(nppId!=null)
   		   view = "NPP";
    	
   	   	if(nppId==null)//nếu null là trung tâm
   		   {
   		   		view ="TT";
   		   		nppID = request.getParameter("nppId");
   		   		System.out.println("asgfhasgd:"+nppID );
   		   		if(nppID == null)
   		   			nppID = "";
   		   		obj.setNppId(nppID);
   		   }
   	   	
   	   
  	    String ddkdId = request.getParameter("ddkdId");
  	    if(ddkdId == null)
  	    	ddkdId = "";
  	    obj.setDdkdId(ddkdId);

  	    
  	    
  	    String nvgnId = request.getParameter("nvgnId");
  	    if(nvgnId == null)
  	    	nvgnId = "";
  	    obj.setNvgnId(nvgnId);
   	   	
    
        if(action.equals("excel")){
        	     	   
	           	String npp1 = "";
	           	String npp2 = "";
	           	if(nppId!=null)// phân phối
	           	{
	           		npp1+= " and d.NPP_FK ='"+nppId+"' \n ";
	           		npp2+= " and a.NPP_FK ='"+nppId+"' \n ";
	           	}
	           	
	           	if(nppID.length()>0)//trung tâm	           		
	        	{
	           		npp1+= " and d.NPP_FK ='"+nppID+"' \n ";
	           		npp2+= " and a.NPP_FK ='"+nppID+"' \n ";
	           	}
	           	
	           	
	           	String conditionDetails_OTC ="";
	    	    String conditionDetails_ETC ="";
	    	    String condition_DuNoDauKy="";
	    	    
	    	    String condition_HoaDonKhac = "";
	    	    	
	    	    	
	    	    	
	    	    	if(ddkdId.length()>0)
	    	    	{
	    	    		conditionDetails_OTC+= "   and kh.pk_seq in \n"+
	    						"                                 	(\n"+
	    						"                                     	SELECT c.KHACHHANG_FK \n"+
	    						"                                     	FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
	    						"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
	    						"                                     	WHERE  a.PK_SEQ ='"+ddkdId+"' \n"+
	    						"                                  	) \n";
	    					
	    	    		
	    	    		conditionDetails_ETC+="   and kh.pk_seq in \n"+
						"                                 	(\n"+
						"                                     	SELECT c.KHACHHANG_FK \n"+
						"                                     	FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
						"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"                                     	WHERE  a.PK_SEQ ='"+ddkdId+"' \n"+
						"                                  	) \n";
	    	    		
	    	    		condition_DuNoDauKy+= " and a.ddkd_fk='"+ddkdId+"' ";
	    	    		
	    	    		condition_HoaDonKhac+= "   and kh.pk_seq in \n"+
						"                                 	(\n"+
						"                                     	SELECT c.KHACHHANG_FK \n"+
						"                                     	FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
						"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"                                     	WHERE  a.PK_SEQ ='"+ddkdId+"' \n"+
						"                                  	) \n";
	    	    	
	    	    		
	    	    	}
	    	    	
	    	    	if(nvgnId.length()>0)
	    	    	{
	    	    		conditionDetails_ETC=" and a.khachhang_Fk in  (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+nvgnId+"' ) \n ";
	    	    	
	    	    		condition_DuNoDauKy+= " and a.khachhang_Fk in (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+nvgnId+"' )  \n ";
	    	    		
	    	    		
	    	    		conditionDetails_OTC+= "  and kh.pk_seq in (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+nvgnId+"' ) \n";
	    	    		
	    	    		condition_HoaDonKhac=" and a.khachhang_Fk in  (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+nvgnId+"' ) \n ";
	    			    
	    	    	}
	           	
	           		           	
	           	String query="";  
                query=	" SELECT bangcc.*  \n";
                
                query+= " FROM \n ";
                /*-----------------------------CAU LAY HOA DON OTC --------------------------*/
                query +=" 	  (SELECT	a.PK_SEQ SoChungTu,a.KYHIEU as KYHIEU, a.NGAYTAO, a.NGAYXUATHD,a.KHACHHANG_FK MaKH,(kh.maFAST +' - '+kh.TEN) as TENKH,a.NPP_FK MaNPP, a.SOHOADON,a.tongtienavat SoTienHD, \n " + 
        				" 				isnull(c.SoTienTT,0) SoTienTT , (a.tongtienavat - isnull(c.SoTienTT,0) - ISNULL(ct.SOTIENCANTRU,0)) SoTienConLai, ISNULL(ct.SOTIENCANTRU,0) AS TienBuTru \n "+
        				" 	   FROM		HoaDon a " +
        				"				LEFT JOIN \n" +
        				"						( \n" +
        				"						   SELECT 	b.HoaDonNPP_FK, b.KHACHHANG_FK, Sum(b.SOTIENTT) SoTienTT \n " +
        				" 						   FROM 	ERP_THUTIENNPP_HOADON b inner join ERP_THUTIENNPP d on b.THUTIENNPP_FK=d.PK_SEQ \n "+
        				" 						   WHERE 	d.TRANGTHAI = 1 and b.loaihd = 0 "+ npp1 +
        				" 						   GROUP BY b.HoaDonNPP_FK, b.KHACHHANG_FK" +
        				"						) c \n " +
        				" 				on (a.PK_SEQ = c.HOADONNPP_FK and a.KHACHHANG_FK = c.KHACHHANG_FK) \n " +
				        "				LEFT JOIN ( \n"+ 
				    	"						SELECT ct.PK_SEQ,isnull(ct.NGAYCHUNGTU,'') NGAYCHUNGTU,cthd.KHACHHANG_FK, cthd.HOADON_FK, SUM(round(ISNULL(cthd.SOTIENCANTRU,0),0)) as SOTIENCANTRU \n"+ 
				    	"						FROM 	CANTRUCONGNO ct INNER JOIN CANTRUCONGNO_HOADON cthd on ct.PK_SEQ = cthd.CANTRUCONGNO_FK \n"+ 
				    	"						WHERE   ct.TRANGTHAI = 1 \n"+
				    	"						GROUP BY cthd.KHACHHANG_FK, cthd.HOADON_FK, ct.PK_SEQ,ct.NGAYCHUNGTU \n"+ 
				    	"					) \n"+ 
				    	"					ct on a.KHACHHANG_FK = ct.KHACHHANG_FK and a.PK_SEQ = ct.HOADON_FK \n"+ 	
        				" 					inner join KHACHHANG kh on kh.PK_SEQ = a.KHACHHANG_FK \n " +
        				" 		WHERE	a.LOAIHOADON = 0 and a.TRANGTHAI in (2,4) and (a.tongtienavat - isnull(c.SoTienTT,0)- ISNULL(ct.SOTIENCANTRU,0)) >0 "+conditionDetails_OTC+" " + npp2;
		                if(tuNgay.length()>0)
		                	query+=" and a.NGAYXUATHD >='"+ tuNgay +"' \n";		                
		                if(denNgay.length()>0)
		                	query+=" and a.NGAYXUATHD <='" + denNgay+ "' \n";
		                if(KhachHang.length()>0)
		                	query+=" and kh.PK_SEQ = '"+ KhachHang+"' \n";
                query +="\n    UNION ALL \n";
                
                /* ---------------------------CAU LAY HOA DON ETC ---------------------------*/
                query +=" 		SELECT	a.PK_SEQ SoChungTu,a.KYHIEU as KYHIEU,a.NGAYTAO,a.NGAYXUATHD, a.KHACHHANG_FK MaKH,(kh.maFAST +' - ' + kh.TEN) as TenKH , a.NPP_FK MaNPP, a.SOHOADON,a.tongtienavat SoTienHD, \n " +
        				" 				isnull(c.SoTienTT,0) SoTienTT, cast(( a.tongtienavat - isnull(c.SoTienTT,0)) as numeric(18,0)) SoTienConLai, 0 AS TienBuTru \n " +
        				" 		FROM	" +
        				" 			(" +
        				"				SELECT 	hd.TRANGTHAI,hd.NGAYTAO,hd.KYHIEU,hdETC.NPP_FK,hdETC.KHACHHANG_FK, hdETC.PK_SEQ HOADON_FK,hdETC.SOHOADON,hdETC.NGAYXUATHD,kh.PK_SEQ, \n"+ 
						"						round(SUM(hdETC.AVAT - hdETC.AVAT_CK),0) as tongtienavat \n"+ 
						"				FROM \n"+ 
						"					( \n"+  
						"						SELECT  ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ as HOADONNPP_FK,npp_fk, \n"+
						"								sum(soluong) as soluong, ( sum( soluong * dongia ) / sum(soluong) ) as dongia, \n"+ 
						"								sum( soluong * dongia )  as BVAT,( sum( soluong * dongia*thuexuat/100 ) ) as VAT, \n"+
						"								sum( soluong * dongia*(1+thuexuat/100 ) ) as AVAT, \n" +
						"								sum(isnull(chietkhau,0)*(1+thuexuat/100)) as AVAT_CK, \n"+
						"								sum(isnull(thuexuat,0)) as BVAT_CK \n"+
						"						FROM ( \n"+
						"								SELECT  c.HOADON_FK as PK_SEQ,a.NGAYXUATHD,a.SOHOADON,a.KHACHHANG_fk,c.HOADON_FK as HOADONNPP_FK, a.NPP_FK,c.chietkhau,c.vat, \n"+ 
						"										( \n"+
						"										  SELECT  top(1) bb.DDKD_FK \n"+ 
						"										  FROM 	  ERP_HOADONNPP_DDH aa inner join ERP_DONDATHANGNPP bb on bb.PK_SEQ=aa.DDH_FK \n"+ 
						"										  WHERE   aa.HOADONNPP_FK=c.HOADON_FK \n"+
 						"										) as ddkd_fk , \n"+
						"										case when c.donvitinh = e.donvi then c.soluong else c.soluong * ( select SOLUONG1 / SOLUONG2 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as soluong, \n"+ 
						"										case when c.donvitinh = e.donvi then c.dongia else c.dongia * ( select SOLUONG2 / SOLUONG1 from QUYCACH where sanpham_fk = c.sanpham_fk and DVDL2_FK = ( select pk_seq from DONVIDOLUONG where donvi = c.donvitinh ) and DVDL1_FK = d.DVDL_FK ) end as dongia, c.vat as thuexuat \n"+ 
						"								FROM 	ERP_HOADONNPP a \n"+
						"											inner join ERP_HOADONNPP_SP c on a.pk_seq = c.hoadon_fk \n"+
						"											inner join SANPHAM d on c.sanpham_fk = d.pk_seq \n"+
						"											inner join DONVIDOLUONG e on d.DVDL_FK = e.pk_seq \n"+
						"								WHERE 	1=1 and c.SOLUONG > 0 and a.trangthai in ( 2 , 4 ) \n"+
						"										and a.NgayXuatHD>='"+tuNgay+"' \n"+
						"										and a.NgayXuatHD<='"+denNgay+"' \n"+
						"							)ETC \n"+
						"  		    			GROUP BY ETC.PK_SEQ,ETC.NGAYXUATHD, ETC.SOHOADON,ETC.KHACHHANG_FK,ETC.ddkd_fk,ETC.PK_SEQ,npp_fk \n"+ 
						"					)as hdETC inner join ERP_HOADONNPP hd on hd.PK_SEQ=hdETC.HOADONNPP_FK \n"+
						"		   	  		  left join DAIDIENKINHDOANH ddkd on ddkd.PK_SEQ=hdETC.DDKD_FK \n"+
						"		   	  		  inner join KHACHHANG kh on kh.PK_SEQ=hdETC.KHACHHANG_FK \n"+
						"		   	  		  inner join NHAPHANPHOI npp on npp.PK_SEQ=hdETC.NPP_FK \n"+
 						"  				WHERE 1=1 and hd.NPP_FK ='"+nppId+"' \n";
			if(tuNgay.length()>0)
				query+= "  				  		  and hd.NGAYXUATHD >='"+tuNgay+"' \n";
			if(denNgay.length()>0)
				query+=	"   			  		  and hd.NGAYXUATHD <='"+denNgay+"' \n";
			if(KhachHang.length()>0)
 				query+=	"   			  		  and kh.PK_SEQ='"+KhachHang+"' \n";
			if(ddkdId.length()>0)
				{
					query+=
						"   			  		  and kh.pk_seq in \n"+
						"                                 (\n"+
						"                                    SELECT c.KHACHHANG_FK \n"+
						"                                    FROM   DAIDIENKINHDOANH a inner join TUYENBANHANG b on b.DDKD_FK=a.PK_SEQ \n"+
						"                                            	inner join KHACHHANG_TUYENBH c on c.TBH_FK=b.PK_SEQ \n"+
						"                                    WHERE  a.PK_SEQ ='"+ddkdId+"' \n"+
						"                                  ) \n";
 				}
			if(nvgnId.length()>0)
 				query+= " 				  		  and hd.khachhang_Fk in (select KHACHHANG_FK from NVGN_KH where nvgn_fk='"+nvgnId+"' )  ";
 						
 						query+=
						"  		 		GROUP BY hdETC.NPP_FK,hdETC.KHACHHANG_FK, hdETC.SOHOADON,hdETC.NGAYXUATHD,kh.PK_SEQ,hdETC.PK_SEQ,hd.TRANGTHAI,hd.NGAYTAO,hd.KYHIEU \n" +
						" 			) a \n"+
        				" 			LEFT JOIN \n" +
        				"			(SELECT b.HoaDonNPP_FK, b.KHACHHANG_FK, Sum(b.SOTIENTT) SoTienTT \n "+
        				" 			 FROM 	ERP_THUTIENNPP_HOADON b inner join ERP_HOADONNPP d on b.HOADONNPP_FK = d.PK_SEQ \n "+
        				" 			 WHERE  d.TRANGTHAI = 1 and b.LOAIHD = 0  " + npp1 +
        				" 			 GROUP BY b.HoaDonNPP_FK, b.KHACHHANG_FK) c \n "+
        				" 			 on (a.PK_SEQ = c.HOADONNPP_FK and a.KHACHHANG_FK = c.KHACHHANG_FK) \n " +
        				" 			 inner join KHACHHANG kh on kh.PK_SEQ = a.KHACHHANG_FK \n "+
        				" 		WHERE	a.TRANGTHAI in (2,4) and cast(( a.tongtienavat - isnull(c.SoTienTT,0)) as numeric(18,0))>0 "+conditionDetails_ETC+" " +npp2;
            if(tuNgay.length()>0)
            	query+=	" 				and a.NGAYXUATHD >='"+ tuNgay +"' \n";		                
            if(denNgay.length()>0)
		        query+=	" 				and a.NGAYXUATHD <='" + denNgay+ "' \n";
		    if(KhachHang.length()>0)
		        query+=	" 				and kh.PK_SEQ = '"+ KhachHang+"' \n";
                
                query += " 		UNION ALL ";
                
                /* --------------****-------LAY DU NO------****------------------*/
                
                query += " 		SELECT	a.PK_SEQ SoChungTu,'GESO' as KYHIEU, c.NGAYTAO,c.NGAYXUATHD, a.KHACHHANG_FK MaKH,(kh.maFAST +' - ' + kh.TEN) as TenKH ,a.NPP_FK MaNPP,'GESO', a.SONO SoTienHD, \n"+
        				 " 				isnull(c.SoTienTT,0) SoTienTT,cast((a.SONO - isnull(c.SoTienTT,0))as numeric(18,0)) SoTienConLai,0 AS TienBuTru \n"+
        				 " 		FROM	DUNO_KHACHHANG a left join \n" +
        				 "				(SELECT d.NGAYTAO,d.NGAYXUATHD,b.HoaDonNPP_FK, b.KHACHHANG_FK, Sum(b.SOTIENTT) SoTienTT \n"+
        				 " 				 FROM 	ERP_THUTIENNPP_HOADON b inner join ERP_HOADONNPP d on b.HOADONNPP_FK = d.PK_SEQ \n"+
        				 " 				 WHERE  d.TRANGTHAI = 1 " + npp1+
        				 " 				 GROUP BY b.HoaDonNPP_FK, b.KHACHHANG_FK, d.NGAYTAO,d.NGAYXUATHD) c "+
        				 " 				on a.PK_SEQ = c.HOADONNPP_FK and a.KHACHHANG_FK = c.KHACHHANG_FK "+ 
        				 " 				inner join KHACHHANG kh on a.KHACHHANG_FK=kh.PK_SEQ \n "+          
        				 " 		WHERE 	cast((a.SONO - isnull(c.SoTienTT,0))as numeric(18,0))>0  "+condition_DuNoDauKy+" " +npp2 +" " ;
    		if(tuNgay.length()>0)
                query+=	 " 				and c.NGAYXUATHD >='"+ tuNgay +"' \n";
        		                
            if(denNgay.length()>0)
            	query+=	 " 				and c.NGAYXUATHD <='" + denNgay+ "' \n";
            if(KhachHang.length()>0)
            	query+=	 " 				and kh.PK_SEQ = '"+ KhachHang+"' \n";
            if(ddkdId.length()>0)
            	query+=	 "  			and a.DDKD_FK='"+ddkdId+"' \n";
     
        	
            
            /* --------------****-------HOA DON KHAC------****------------------*/
            query += " 		UNION ALL ";
            
            query += 
            	" SELECT	a.PK_SEQ SoChungTu,a.kyhieuhoadon as KYHIEU, c.NGAYTAO,a.ngayhoadon NGAYXUATHD, a.KHACHHANG_FK MaKH,(kh.maFAST +' - ' + kh.TEN) as TenKH ,a.NPP_FK MaNPP,a.sohoadon, a.avat SoTienHD, \n"+ 
 				"			isnull(c.SoTienTT,0) SoTienTT,cast((isnull(a.avat,0) - isnull(c.SoTienTT,0))as numeric(18,0)) SoTienConLai,0 AS TienBuTru \n"+ 
 				" FROM	ERP_HoaDonPheLieu a left join \n"+ 
 				"		(SELECT d.NGAYTAO,d.NGAYXUATHD,b.HoaDonNPP_FK, b.KHACHHANG_FK, Sum(b.SOTIENTT) SoTienTT \n"+ 
 				" 		 FROM 	ERP_THUTIENNPP_HOADON b inner join ERP_HOADONNPP d on b.HOADONNPP_FK = d.PK_SEQ \n"+ 
 				" 		 WHERE  d.TRANGTHAI = 1  and b.LOAIHD = 1 "+ npp1+
  				" 		 GROUP BY b.HoaDonNPP_FK, b.KHACHHANG_FK, d.NGAYTAO,d.NGAYXUATHD \n"+
  				" 		) c \n"+  				
  				" 		on a.PK_SEQ = c.HOADONNPP_FK and a.KHACHHANG_FK = c.KHACHHANG_FK \n"+  				
  				" 		inner join KHACHHANG kh on a.KHACHHANG_FK=kh.PK_SEQ \n"+ 
  				" WHERE  a.trangthai = 1 and cast((isnull(a.avat,0) - isnull(c.SoTienTT,0))as numeric(18,0)) >0 \n"+npp2 +" "+ condition_HoaDonKhac;
  				if(tuNgay.length()>0)
  	                query+=	 " 				and a.ngayhoadon >='"+ tuNgay +"' \n";
  				if(denNgay.length()>0)
  	            	query+=	 " 				and a.ngayhoadon <='" + denNgay+ "' \n";
  				if(KhachHang.length()>0)
  	            	query+=	 " 				and kh.PK_SEQ = '"+ KhachHang+"' \n";
  				
  				query += "	) bangcc  WHERE 1=1 ";    
  				
				if(tuNgay.length()>0)
			       query+=	 " 			and NGAYXUATHD >='"+ tuNgay +"'";
			       
			   if(denNgay.length()>0)
			   	query+=	 " 			and NGAYXUATHD <='" + denNgay+ "'";               
			       
			   if(KhachHang.length()>0)
			       query+=	 " 			and MaKH = '"+ KhachHang+"'";
  	            
              	query += " ORDER BY bangcc.NGAYXUATHD ";
           	   
                System.out.println("CAU BAO CAO: "+ query);                
                
        	ToExcel(response, query, tuNgay, denNgay, KhachHang, userTen, nppId);	
        }
        
        else
        {
        
    	String nextJSP ="";
    	if(view.equals("TT"))
    	{
    		nextJSP = "/TraphacoHYERP/pages/Center/BCCongNoKH_TT.jsp";
    	}
    	if(view.equals("NPP"))
    	{
    		nextJSP = "/TraphacoHYERP/pages/Distributor/BCCongNoKH.jsp";
    	}
    	
    	obj.init1();
    	
    	session.setAttribute("obj", obj);
    	
			response.sendRedirect(nextJSP);
        }
	}

	private void ToExcel(HttpServletResponse response, String query, String tuNgay, String denNgay, String KhachHang, String UserTen, String nppId) throws IOException
	{
		IBCCongNoKH obj = new BCCongNoKH();
		
		OutputStream out = null;
		dbutils db = new dbutils();
		try
		{
	
			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=BaoCaoCongNoKH.xls");
			WritableWorkbook w = jxl.Workbook.createWorkbook(response.getOutputStream());

			int k = 0;
			int j = 11;

			WritableSheet sheet = null;

			WritableFont cellTitle = new WritableFont(WritableFont.TIMES, 15);
			cellTitle.setColour(Colour.BLACK);
			cellTitle.setBoldStyle(WritableFont.BOLD);
			
			WritableCellFormat celltieude = new WritableCellFormat(cellTitle);
			celltieude.setAlignment(Alignment.CENTRE);
			
			sheet = w.createSheet("CongNoKH", k);//ten sheet			
			WritableFont cellFont = new WritableFont(WritableFont.TIMES, 13);
			cellFont.setColour(Colour.BLACK);

			WritableFont cellFontWhite = new WritableFont(WritableFont.TIMES, 13);
			cellFontWhite.setColour(Colour.WHITE);
			
			WritableCellFormat cellFormatTD = new WritableCellFormat(cellFont);
			
			sheet.addCell(new Label(0, 1, UserTen, cellFormatTD));								
			sheet.mergeCells(0, 1, 2, 1);
			
			ResultSet dc = db.get("select DiaChi from NHAPHANPHOI where PK_SEQ='"+nppId+"'");
			String diachinpp="";
			if(dc!=null)
			{
				while (dc.next())
					diachinpp=dc.getString("DiaChi");
			}
			
			sheet.addCell(new Label(0, 2, diachinpp, cellFormatTD));
			//sheet.addMergedRegion(new CellRangeAddress(1,1,4,1))		
			
			//sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 7));
			//mergeCells(int col1, int row1, int col2, int row2)
			sheet.addCell(new Label(0, 4, "CÔNG NỢ KHÁCH HÀNG ", celltieude));	
			sheet.mergeCells(0, 4, 7, 4);
			
			sheet.addCell(new Label(0, 6, "Từ ngày: "));// cột dòng
			sheet.addCell(new Label(1, 6, tuNgay)); // lấy ngày đã chọn
			
			sheet.addCell(new Label(0, 7, "Đến ngày: "));
			sheet.addCell(new Label(1, 7, denNgay)); // lấy ngày đã chọn
			//sheet.addCell(new Label(1, 2, "" + getDateTime()));
			
			String TenKH= "select (maFAST +' - '+TEN) as TENKH from KhachHang where PK_SEQ ='"+KhachHang+"'";
			ResultSet laytenkh = db.get(TenKH);
			String ten = "";
			if(laytenkh!=null)
			{
				while(laytenkh.next())
				{
					ten=laytenkh.getString("TENKH");
				}
			}
			sheet.addCell(new Label(0, 8, "Khách hàng: "));
			if(KhachHang.length()>0)
				sheet.addCell(new Label(1, 8, ten));// lấy tên khách hàng đã chọn
			else
				sheet.addCell(new Label(1, 8, "Tất cả các khách hàng của nhà cung cấp"));
			

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

			cellFormatSpecical.setBackground(jxl.format.Colour.GRAY_80);
			cellFormatSpecical.setWrap(true);
			cellFormatSpecical.setAlignment(Alignment.CENTRE);
			cellFormatSpecical.setVerticalAlignment(VerticalAlignment.CENTRE);
			cellFormatSpecical.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.WHITE);
			cellFormatSpecical.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.WHITE);
			

			sheet.addCell(new Label(0, 10, "STT", cellFormat));
			sheet.addCell(new Label(1, 10, "KHÁCH HÀNG", cellFormat));
			sheet.addCell(new Label(2, 10, "SỐ CHỨNG TỪ", cellFormat));
			sheet.addCell(new Label(3, 10, "KÝ HIỆU", cellFormat));
			sheet.addCell(new Label(4, 10, "SỐ HÓA ĐƠN", cellFormat));
			sheet.addCell(new Label(5, 10, "SỐ TIỀN HÓA ĐƠN", cellFormat));			
			sheet.addCell(new Label(6, 10, "SỐ TIỀN THANH TOÁN", cellFormat));
			sheet.addCell(new Label(7, 10, "TIỀN BÙ TRỪ", cellFormat));
			sheet.addCell(new Label(8, 10, "CÒN LẠI", cellFormat));
						
			sheet.setRowView(100, 4);

			sheet.setColumnView(0, 12);
			sheet.setColumnView(1, 70);
			sheet.setColumnView(2, 20);
			sheet.setColumnView(3, 20);
			sheet.setColumnView(4, 20);
			sheet.setColumnView(5, 20);
			sheet.setColumnView(6, 20);
			sheet.setColumnView(7, 20);
			sheet.setColumnView(8, 20);
			

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
			
			Label label;
			Number number;
			
			
			NumberFormat dp3 = new NumberFormat("#,###,###,##");
			WritableCellFormat inFormat = new WritableCellFormat(dp3);
			inFormat.setFont(cellFont);
		
			inFormat.setBorder(Border.LEFT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.RIGHT, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.TOP, BorderLineStyle.THIN, Colour.BLACK);
			inFormat.setBorder(Border.BOTTOM, BorderLineStyle.THIN, Colour.BLACK);

			int stt = 0;
			double SoTienTT = 0;
			double SoTienConLai=0;
			double SoTienHDTT=0;
			
			while (rs.next())
			{
				stt++;				
				String type = "0";
				cformat = type.equals("1") ? cellFormat3 : cellFormat2;

				number = new Number(0, j, stt, cformat);
				sheet.addCell(number);
				label = new Label(1, j, rs.getString("TENKH"), cformat3);
				sheet.addCell(label);
				label = new Label(2, j, rs.getString("SoChungTu"), cformat3);
				sheet.addCell(label);	
				label = new Label(3, j, rs.getString("KYHIEU"), cformat3);
				sheet.addCell(label);	
				label = new Label(4, j, rs.getString("SOHOADON"), cformat3);
				sheet.addCell(label);
								
				number =new Number(5, j,rs.getDouble("SoTienHD"), inFormat);sheet.addCell(number);
				number =new Number(6, j,rs.getDouble("SoTienTT"), inFormat);sheet.addCell(number);
				
				SoTienHDTT+=rs.getDouble("SoTienHD");
				
				SoTienTT += rs.getDouble("SoTienTT");	
				number =new Number(7, j,rs.getDouble("Tienbutru"), inFormat);sheet.addCell(number);
				number =new Number(8, j,rs.getDouble("SoTienConLai"), inFormat);sheet.addCell(number);
				
				
				SoTienConLai+= rs.getDouble("SoTienConLai");
				j++;
			}
			
		
			
			sheet.addCell(new Label(0, j, "TỔNG CỘNG", cellFormat));
			sheet.mergeCells(0, j, 4, j);// bắt đầu từ cột thứ 0, dòng thứ mấy , 7 cột để merger, 1 dòng để merger
			
			number =new Number(5, j,SoTienHDTT, inFormat);sheet.addCell(number);
			number =new Number(6, j,SoTienTT, inFormat);sheet.addCell(number);			
			number =new Number(8, j,SoTienConLai, inFormat);sheet.addCell(number);
			
			w.write();
			w.close();
			rs.close();
		
		} catch (Exception e)
		{
			System.out.println("Lỗi : " + e.getMessage());
			e.printStackTrace();
		} finally
		{
			db.shutDown();
			if (out != null)
				out.close();

		}
	}
	
}
