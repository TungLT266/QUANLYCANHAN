package geso.traphaco.center.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.db.sql.dbutils;
import geso.traphaco.center.util.Utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
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

@WebServlet("/UsingPromoTT")
public class UsingPromoTT extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UsingPromoTT() {
        super();
        
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility util=new Utility();
		HttpSession session = request.getSession();
		IStockintransit obj = new Stockintransit();	  
		
		String userTen = (String)session.getAttribute("userTen");
		obj.setuserTen(userTen==null? "":userTen);
		
		String querystring=request.getQueryString();
		String userId=	util.getUserId(querystring);
		obj.setuserId(userId==null? "":userId);
		
		obj.init();
		session.setAttribute("obj", obj);	
		session.setAttribute("userTen", userTen);
		String nextJSP = "/TraphacoHYERP/pages/Center/UsingPromoTT.jsp";
		response.sendRedirect(nextJSP);		
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			response.setContentType("text/html; charset=UTF-8");
			HttpSession session = request.getSession();	 		
	 		IStockintransit obj = new Stockintransit();	
	 		OutputStream out = response.getOutputStream();
	 		try
	 		{
				String userId = (String) session.getAttribute("userId");
				String userTen = (String) session.getAttribute("userTen");	
				obj.setuserId(userId==null? "":userId);
				obj.setuserTen(userTen==null? "":userTen);
				obj.settungay(request.getParameter("Sdays")==null? "":request.getParameter("Sdays"));			
				obj.setdenngay(request.getParameter("Edays")==null? "":request.getParameter("Edays"));
 
				obj.setkenhId(request.getParameter("kenhId")==null? "":request.getParameter("kenhId"));				
				obj.setdvkdId(request.getParameter("dvkdId")==null? "":request.getParameter("dvkdId"));
				obj.setnhanhangId(request.getParameter("nhanhangId")==null? "":request.getParameter("nhanhangId"));
				obj.setchungloaiId(request.getParameter("chungloaiId")==null? "":request.getParameter("chungloaiId"));				
				obj.setPrograms(request.getParameter("programs")==null? "":request.getParameter("programs"));
				obj.setnppId(request.getParameter("npp")==null? "":request.getParameter("npp"));
				obj.setvungId(request.getParameter("vung")==null? "":request.getParameter("vung"));
				obj.setkhuvucId(request.getParameter("khuvuc")==null? "":request.getParameter("khuvuc"));
				obj.setvat(request.getParameter("VAT")==null? "":request.getParameter("VAT"));
				obj.setMuclay(request.getParameter("mucphantich"));
				
				
				Utility Ult = new Utility();
			
				String condition = " AND CTKM.kho_fk = '100000' ";
				if(obj.getvungId().length()>0)
					condition +=" and v.pk_seq = '"+ obj.getvungId() +"'";
				
				if(obj.getkhuvucId().length()>0)
					condition +=" and kv.pk_seq = '"+ obj.getkhuvucId() +"'";
				
				if(obj.getnppId().length()>0)
					condition +=" and npp.pk_seq = '"+ obj.getnppId() +"'";
				
				if(obj.getkenhId().length()>0)
					condition +=" and kbh.pk_seq ='" + obj.getkenhId() +"'";
				
				if(obj.getPrograms().length()>0)
					condition +=" and ctkm.pk_seq = '" + obj.getPrograms() +"'";
				
				if(obj.getdvkdId().length()>0)
					condition +=" and dvkd.pk_seq = '" + obj.getdvkdId() +"'";
				
				if(obj.getMuclay().equals("0"))
				{
					if(obj.getnhanhangId().length()>0)
						condition +=" and nh.pk_seq = '" + obj.getnhanhangId() +"'";
					
					if(obj.getchungloaiId().length()>0)
						condition +=" and cl.pk_seq = '" + obj.getchungloaiId() +"'";
				}
				
				condition=condition +  " and npp.pk_seq in "+ Ult.quyen_npp(obj.getuserId())  +
				"  and kbh.pk_seq in "+ Ult.quyen_kenh(obj.getuserId()) ;
				
				Utility util= new Utility();
				String action = request.getParameter("action");				
				if (action.equals("create")) {
					response.setContentType("application/xlsm");
					response.setHeader("Content-Disposition", "attachment; filename=SuDungChiTraKhuyenMai(TT)"+util.setTieuDe(obj)+".xlsm");
					CreatePivotTable(out,obj,condition);
				}		        
		     }
		    catch (Exception ex)
		    {
		       obj.setMsg(ex.getMessage());
		    }
		    obj.init();
			session.setAttribute("obj", obj);	
			session.setAttribute("userTen", obj.getuserTen());
			String nextJSP = "/TraphacoHYERP/pages/Center/UsingPromoTT.jsp";
			response.sendRedirect(nextJSP);		
	}
	
	private void CreatePivotTable(OutputStream out,IStockintransit obj, String condition) throws Exception
    {       
 		String strfstream = null;
 		
 		if(obj.getMuclay().equals("0"))
 		{
 			strfstream = getServletContext().getInitParameter("path")+"\\SuDungChiTraKhuyenMaiTT.xlsm";
 		}
 		else
 		{
 			if(obj.getMuclay().equals("1"))
 			{
 				strfstream = getServletContext().getInitParameter("path")+"\\SuDungChiTraKhuyenMaiTT_SKU.xlsm";
 			}
 			else
 			{
 				strfstream = getServletContext().getInitParameter("path")+"\\SuDungChiTraKhuyenMaiTT_TONGSKU.xlsm";
 			}
 		}
 		
 		FileInputStream fstream = new FileInputStream(strfstream);
		Workbook workbook = new Workbook();
		workbook.open(fstream);
		workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);

		CreateStaticHeader(workbook,obj);	     
	    CreateStaticData(workbook, obj, condition);
	    workbook.save(out);
	    fstream.close();
	}
 	
 	private void CreateStaticHeader(Workbook workbook, IStockintransit obj)throws Exception 
 	{
 		
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
	    
	    cell.setValue("BÁO CÁO SỬ DỤNG VÀ CHI TRẢ KHUYẾN MÃI");  getCellStyle(workbook,"A1",Color.RED,true,14);	  	
	    
	  /*  Utility  util = new  Utility();
		 cells.setRowHeight(3, 18.0f);
		 cell = cells.getCell("A2");
		 ReportAPI.getCellStyle(cell,Color.RED,true, 9, util.setTieuDe(obj));
		*/
	    
	    
	    cells.setRowHeight(2, 18.0f);
	    cell = cells.getCell("A3"); 
	    getCellStyle(workbook,"A3",Color.NAVY,true,10);	    
	    cell.setValue("Từ ngày: " + obj.gettungay());
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); getCellStyle(workbook,"B3",Color.NAVY,true,9);	        
	    cell.setValue("Đến ngày: " + obj.getdenngay());    
	    
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");getCellStyle(workbook,"A4",Color.NAVY,true,9);
	    cell.setValue("Ngày báo cáo: " + this.getDate());
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");getCellStyle(workbook,"A5",Color.NAVY,true,9);
	    cell.setValue("Được tạo bởi:  " + obj.getuserTen());
			  
	    if(obj.getMuclay().equals("0"))
	    {
	    	cell = cells.getCell("EA1"); cell.setValue("Kenh");
			cell = cells.getCell("EB1"); cell.setValue("DonViKinhDoanh");
			cell = cells.getCell("EC1"); cell.setValue("Vung");
	 	    cell = cells.getCell("ED1"); cell.setValue("Khu vuc");  	
	 	    cell = cells.getCell("EE1"); cell.setValue("MaChiNhanh/DoiTac");
		    cell = cells.getCell("EF1"); cell.setValue("ChiNhanh/DoiTac");
		    cell = cells.getCell("EG1"); cell.setValue("Ma Chuong Trinh KM");
		    cell = cells.getCell("EH1"); cell.setValue("Chuong Trinh KM");
		    
		    cell = cells.getCell("EI1"); cell.setValue("NhanHang");
		    cell = cells.getCell("EJ1"); cell.setValue("ChungLoai");
		    cell = cells.getCell("EK1"); cell.setValue("MaSanPham");
		    cell = cells.getCell("EL1"); cell.setValue("TenSanPham");
		    
		    cell = cells.getCell("EM1"); cell.setValue("SoLuongMua(1)");
		    cell = cells.getCell("EN1"); cell.setValue("DoanhSoMua(1)");
		    cell = cells.getCell("EO1"); cell.setValue("SoLuongMua(1)(Thung)");
		    cell = cells.getCell("EP1"); cell.setValue("SoLuongMua(1)(Le)");
		    
		    cell = cells.getCell("EQ1"); cell.setValue("SoLuongKM(2)");
		    cell = cells.getCell("ER1"); cell.setValue("TongGiaTriKM(2)");
		    cell = cells.getCell("ES1"); cell.setValue("SoLuongKM(2)(Thung)");
		    cell = cells.getCell("ET1"); cell.setValue("SoLuongKM(2)(Le)");
		    cell = cells.getCell("EU1"); cell.setValue("TienCongNo");
		    
		    cell = cells.getCell("EV1"); cell.setValue("SoLuongTra(3)");
		    cell = cells.getCell("EW1"); cell.setValue("TongGiaTriTra(3)");
		    cell = cells.getCell("EX1"); cell.setValue("SoLuongTra(3)(Thung)");
		    cell = cells.getCell("EY1"); cell.setValue("SoLuongTra(3)(Le)");
		    cell = cells.getCell("EZ1"); cell.setValue("TyLe(2/1)");
		    cell = cells.getCell("FA1"); cell.setValue("LoaiChuongTrinh");
	    }
	    else
	    {
	    	if(obj.getMuclay().equals("1"))
	 	    {
	    		cell = cells.getCell("EA1"); cell.setValue("Kenh");
				cell = cells.getCell("EB1"); cell.setValue("DonViKinhDoanh");
				cell = cells.getCell("EC1"); cell.setValue("Vung");
		 	    cell = cells.getCell("ED1"); cell.setValue("KhuVuc");  	
		 	    cell = cells.getCell("EE1"); cell.setValue("MaNhaPP");
			    cell = cells.getCell("EF1"); cell.setValue("NhaPhanPhoi");
			    cell = cells.getCell("EG1"); cell.setValue("MaSanPham");
			    cell = cells.getCell("EH1"); cell.setValue("TenSanPham");
			    cell = cells.getCell("EI1"); cell.setValue("SCHEME");
			    cell = cells.getCell("EJ1"); cell.setValue("TongLuongMua");
			    cell = cells.getCell("EK1"); cell.setValue("DoanhSoMua");
			    cell = cells.getCell("EL1"); cell.setValue("TongLuongTraKM");
			    cell = cells.getCell("EM1"); cell.setValue("TongTienTraKM");
			    cell = cells.getCell("EN1"); cell.setValue("TyLe");
	 	    }
	    	else
	    	{
	    		cell = cells.getCell("EA1"); cell.setValue("Kenh");
				cell = cells.getCell("EB1"); cell.setValue("DonViKinhDoanh");
				cell = cells.getCell("EC1"); cell.setValue("Vung");
		 	    cell = cells.getCell("ED1"); cell.setValue("Khu vuc");  	
		 	    cell = cells.getCell("EE1"); cell.setValue("MaNhaPP");
			    cell = cells.getCell("EF1"); cell.setValue("Nha Phan Phoi");
			    
			    cell = cells.getCell("EG1"); cell.setValue("NhanHang");
			    cell = cells.getCell("EH1"); cell.setValue("ChungLoai");
			    cell = cells.getCell("EI1"); cell.setValue("MaSanPham");
			    cell = cells.getCell("EJ1"); cell.setValue("TenSanPham");
			    
			    cell = cells.getCell("EK1"); cell.setValue("SoLuongMua(1)");
			    cell = cells.getCell("EL1"); cell.setValue("DoanhSoMua(1)");
			    cell = cells.getCell("EM1"); cell.setValue("SoLuongMua(1)(Thung)");
			    cell = cells.getCell("EN1"); cell.setValue("SoLuongMua(1)(Le)");
			    
			    cell = cells.getCell("EO1"); cell.setValue("SoLuongKM(2)");
			    cell = cells.getCell("EP1"); cell.setValue("TongGiaTriKM(2)");
			    cell = cells.getCell("EQ1"); cell.setValue("SoLuongKM(2)(Thung)");
			    cell = cells.getCell("ER1"); cell.setValue("SoLuongKM(2)(Le)");
			    cell = cells.getCell("ES1"); cell.setValue("TienCongNo");
			    
			    cell = cells.getCell("ET1"); cell.setValue("SoLuongTra(3)");
			    cell = cells.getCell("EU1"); cell.setValue("TongGiaTriTra(3)");
			    cell = cells.getCell("EV1"); cell.setValue("SoLuongTra(3)(Thung)");
			    cell = cells.getCell("EW1"); cell.setValue("SoLuongTra(3)(Le)");
			    cell = cells.getCell("EX1"); cell.setValue("TyLe(2/1)");
			    //cell = cells.getCell("FY1"); cell.setValue("LoaiChuongTrinh");
	    	}
	    	
	    }
	     
 	}
 	
	private void CreateStaticData(Workbook workbook, IStockintransit obj, String condition) throws Exception
 	{
 		Worksheets worksheets = workbook.getWorksheets();
 	    Worksheet worksheet = worksheets.getSheet(0);
 	    Cells cells = worksheet.getCells();
 	    dbutils db = new dbutils();	  
	
 	  
 	    String sql= "";
 	    
 	    if(obj.getMuclay().equals("0"))
 	    {
	 	    sql = " SELECT KBH.DIENGIAI AS KENH,V.TEN AS VUNG,KV.TEN AS KHUVUC,NPP.SITECODE,NPP.TEN AS TENNPP,CTKM.SCHEME,CTKM.DIENGIAI AS   "+
				  " CTKM,DVKD.DIENGIAI AS DVKD,   	NH.TEN AS NHANHANG,CL.TEN AS CHUNGLOAI,SP.MA AS MASP,SP.TEN AS TENSP,NHAP.DS_SOLUONG," +
				 
				 " NHAP.DS_TONGGIATRI, "+
				 " NHAP.SD_SOLUONG,NHAP.SD_TONGGIATRI,NHAP.TRA_SOLUONG,NHAP.TRA_TONGGIATRI    	,QC.SOLUONG1 AS QC1,QC.SOLUONG2 AS QC2   "+
				 " ,  ISNULL( (  " +
			
				 
				 " SELECT top 1 BGSP.GIAMUANPP "+
				 " FROM BANGGIAMUANPP B  "+
				 " INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK   "+
				 " INNER JOIN BGMUANPP_SANPHAM BGSP ON BGSP.BGMUANPP_FK=B.PK_SEQ "+
				 " WHERE  B.TUNGAY <='"+obj.gettungay()+"' AND   "+
				 " B.PK_SEQ = "+
				 " (   "+
				 " SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG "+
				 " INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK  "+
				 " WHERE BG.TUNGAY <= '"+obj.gettungay()+"' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK "+
				 " ORDER BY TUNGAY DESC "+
				 " )  AND C.NPP_FK=NHAP.NPP_FK  AND B.KENH_FK=NHAP.KBH_FK AND BGSP.SANPHAM_FK=SP.PK_SEQ  "+
				 " ) ,0) AS DONGIA ,ISNULL(TONGDS.TONGGIATRI,0) AS TONGDS,ISNULL(TONGKM.TONGGIATRI,0) AS TONGKM ," +
				 " CASE WHEN CTKM.LOAICT = 1 THEN N'Bình thường' WHEN CTKM.LOAICT = 2 THEN 'On Top'  " +
				 " WHEN CTKM.LOAICT = 3 THEN N'Tích lũy' else 'Không xác định' END AS PROGRAM_TYPE   "+
				 " FROM   "+
				 " (    "+
				 " SELECT  ISNULL(SD.CTKMID ,ISNULL( TRA.PK_SEQ ,DS.CTKMID )) AS CTKMID , "+
				 " ISNULL(SD.KBH_FK ,ISNULL( TRA.KBH_FK ,DS.KBH_FK )) AS KBH_FK , "+
				 " ISNULL(SD.NPP_FK ,ISNULL( TRA.NPP_FK ,DS.NPP_FK )) AS NPP_FK , "+
				 " ISNULL(SD.SANPHAM_FK ,ISNULL( TRA.SANPHAM_FK ,DS.MA )) AS SANPHAM_FK  "+
				 " ,ISNULL(DS.SOLUONG,0)  AS DS_SOLUONG, ISNULL(DS.TONGGIATRI,0) AS DS_TONGGIATRI "+
				 " ,ISNULL(SD.SOLUONG,0)  AS SD_SOLUONG, ISNULL(SD.TONGGIATRI,0) AS SD_TONGGIATRI "+
				 " ,ISNULL(TRA.SOLUONG,0)  AS TRA_SOLUONG, ISNULL(TRA.TONGGIATRI,0) AS TRA_TONGGIATRI "+
				 " FROM  (	 "+
				 " SELECT DH.NPP_FK,DH.KBH_FK,TRAKM.SPMA AS SANPHAM_FK,TRAKM.CTKMID, SUM(TRAKM.SOLUONG) AS SOLUONG,    "+
				 " SUM(TRAKM.TONGGIATRI) AS TONGGIATRI  ,N'SỬ DỤNG' AS TYPE "+
				 " FROM DONHANG DH INNER JOIN DONHANG_CTKM_TRAKM TRAKM ON DH.PK_SEQ=TRAKM.DONHANGID   "+
				 " WHERE   DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"'    "+
				 " AND  DH.TRANGTHAI=1     "+
				 " GROUP BY DH.NPP_FK,DH.KBH_FK,TRAKM.SPMA,TRAKM.CTKMID    "+
				 " ) SD  FULL OUTER JOIN  "+
				 " ( "+
				 " SELECT NH.NPP_FK,NH.KBH_FK,NH_SP.SANPHAM_FK,CTKM.PK_SEQ, SUM(CAST( SOLUONG AS INT) ) AS SOLUONG    "+
				 " ,SUM(CAST( SOLUONG AS INT) * 0 ) AS TONGGIATRI ,N'TRẢ KM' AS TYPE "+
				 " FROM NHAPHANG NH    	  "+
				 " INNER JOIN NHAPHANG_SP NH_SP ON NH.PK_SEQ=NH_SP.NHAPHANG_FK   	 "+
				 " INNER JOIN CTKHUYENMAI CTKM ON CTKM.SCHEME=NH_SP.SCHEME   	 "+
				 " WHERE NH_SP.SCHEME IS NOT NULL AND NH_SP.SCHEME <> '' AND NH.NGAYCHUNGTU >='"+obj.gettungay()+"' "+
				 " AND NH.NGAYCHUNGTU <='"+obj.getdenngay()+"'   	GROUP BY NH.NPP_FK,NH_SP.SANPHAM_FK,CTKM.PK_SEQ,NH.KBH_FK "+
				 " ) TRA ON TRA.KBH_FK=SD.KBH_FK AND TRA.NPP_FK=SD.NPP_FK AND TRA.SANPHAM_FK=SD.SANPHAM_FK AND  "+
				 " SD.CTKMID=TRA.PK_SEQ  "+
				 " FULL OUTER JOIN   "+
				 " ( "+
				 " SELECT    DH.NPP_FK,DH.KBH_FK,SP.MA,  B.CTKMID,  "+
				 " SUM(C.SOLUONG) AS SOLUONG , SUM( C.SOLUONG * C.GIAMUA)  AS TONGGIATRI,N'HÀNG BÁN' AS TYPE "+
				 " FROM  CTKHUYENMAI AS A INNER JOIN "+
				 " ( "+ 
				 "			SELECT    CTKMID,DONHANGID "+
				 "			FROM  "+ 
				 "			DONHANG_CTKM_TRAKM  "+
				 "			GROUP BY CTKMID,DONHANGID "+
				 " )AS B ON A.PK_SEQ = B.CTKMID INNER JOIN "+ 
				 " DONHANG_SANPHAM AS C ON B.DONHANGID = C.DONHANG_FK  "+
				 " INNER JOIN SANPHAM SP ON SP.PK_SEQ=C.SANPHAM_FK "+
				 " INNER JOIN DONHANG  DH ON DH.PK_SEQ = C.DONHANG_FK INNER JOIN "+
				 " CTKM_DKKM AS D ON A.PK_SEQ = D.CTKHUYENMAI_FK INNER JOIN "+
				 " DIEUKIENKM_SANPHAM AS E ON D.DKKHUYENMAI_FK = E.DIEUKIENKHUYENMAI_FK AND C.SANPHAM_FK = E.SANPHAM_FK "+
				 " WHERE      DH.NGAYNHAP>='"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"'  "+
				 " AND DH.TRANGTHAI=1 "+
				 " GROUP BY  DH.NPP_FK,DH.KBH_FK,SP.MA,  B.CTKMID "+
				 " ) AS DS   ON DS.KBH_FK=SD.KBH_FK AND DS.NPP_FK=SD.NPP_FK AND DS.MA=SD.SANPHAM_FK AND  "+
				 " SD.CTKMID=DS.CTKMID  "+
				 "  "+
				 " ) NHAP  " +
				 " left join " +
				 " ( SELECT DH.NPP_FK,DH.KBH_FK,TRAKM.CTKMID, "+   
				 " SUM(TRAKM.TONGGIATRI) AS TONGGIATRI  "+
				 " FROM DONHANG DH INNER JOIN DONHANG_CTKM_TRAKM TRAKM ON DH.PK_SEQ=TRAKM.DONHANGID "+  
				 " WHERE    DH.NGAYNHAP >='"+obj.gettungay()+"'  AND DH.NGAYNHAP <='"+obj.getdenngay()+"'  "+  
				 " AND  DH.TRANGTHAI=1     "+
				 " GROUP BY DH.NPP_FK,DH.KBH_FK,TRAKM.CTKMID   ) " +
				 " as TONGKM  ON TONGKM.NPP_FK= NHAP.NPP_FK AND NHAP.KBH_FK = TONGKM.KBH_FK AND NHAP.CTKMID= TONGKM.CTKMID  " +
				 " LEFT JOIN " +
				 " ( SELECT    DH.NPP_FK,DH.KBH_FK, B.CTKMID , SUM(C.SOLUONG * C.GIAMUA )  AS TONGGIATRI " +
				 " FROM  CTKHUYENMAI AS A INNER JOIN " +
				 " ( "+ 
				 "			SELECT    CTKMID,DONHANGID "+
				 "			FROM  "+ 
				 "			DONHANG_CTKM_TRAKM  "+
				 "			GROUP BY CTKMID,DONHANGID "+
				 " )AS B  ON A.PK_SEQ = B.CTKMID INNER JOIN "+
				 " DONHANG_SANPHAM AS C ON B.DONHANGID = C.DONHANG_FK  " +
				 " INNER JOIN DONHANG  DH ON DH.PK_SEQ = C.DONHANG_FK INNER JOIN " +
				 " CTKM_DKKM AS D ON A.PK_SEQ = D.CTKHUYENMAI_FK INNER JOIN " +
				 " DIEUKIENKM_SANPHAM AS E ON D.DKKHUYENMAI_FK = E.DIEUKIENKHUYENMAI_FK AND C.SANPHAM_FK = E.SANPHAM_FK " +
				 " WHERE    DH.NGAYNHAP>='"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"' " + 
				 " AND DH.TRANGTHAI=1 " +
				 " GROUP BY  DH.NPP_FK,DH.KBH_FK,  B.CTKMID " +
				 " ) AS TONGDS ON TONGDS.NPP_FK= NHAP.NPP_FK AND NHAP.KBH_FK = TONGDS.KBH_FK AND NHAP.CTKMID= TONGDS.CTKMID  " +
				 "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NHAP.NPP_FK   "+
				 " LEFT JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK   LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK   "+
				 " LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=NHAP.KBH_FK    "+
				 " INNER JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=NHAP.CTKMID   "+
				 " LEFT JOIN SANPHAM SP ON SP.MA=NHAP.SANPHAM_FK    "+
				 " LEFT JOIN QUYCACH QC ON SP.PK_SEQ = QC.SANPHAM_FK AND QC.DVDL2_FK=100018    "+
				 " LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=SP.DVKD_FK   "+
				 " LEFT JOIN NHANHANG NH ON NH.PK_SEQ=SP.NHANHANG_FK    "+
				 " LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ=SP.CHUNGLOAI_FK "+
				 " WHERE 1=1     " +condition + 
				 " ORDER BY NHAP.KBH_FK,NHAP.NPP_FK,NHAP.CTKMID ";
 	    }
 	    else
 	    {
 	    	if(obj.getMuclay().equals("1"))
 	    	{
	 	    
 	    		
 	    		sql = 	" select KBH.TEN as kbhTen, DVKD.DONVIKINHDOANH, V.TEN as vungTen, KV.TEN as kvTen, NPP.TEN as nppTen, NPP.MA as nppMa, SP.TEN as spTen, SP.MA as spMA, " +
			 	    	" 		DOANHSO.DS_SOLUONG, DOANHSO.DS as DOANHSO, isnull(ctkm.SCHEME, '') as Scheme, isnull(KHUYENMAI.totalTRA, '0') as tongSLTra, ISNULL(KHUYENMAI.totalTienTRA, '0') as tongTienTra  " +
			 	    	" from " +
			 	    	" ( " +
			 	    	" 	select a.NPP_FK, a.KBH_FK, b.SANPHAM_FK, SUM(SOLUONG) as DS_SOLUONG, SUM(b.SOLUONG * b.GIAMUA) as DS " +
			 	    	" 	from DONHANG a inner join DONHANG_SANPHAM b on a.PK_SEQ = b.DONHANG_FK  " +
			 	    	" 	where a.TRANGTHAI != 2 and a.NGAYNHAP >= '" + obj.gettungay() + "' and a.NGAYNHAP <= '" + obj.getdenngay() + "' " +
			 	    	" 	group by a.NPP_FK, a.KBH_FK, b.SANPHAM_FK " +
			 	    	" ) " +
			 	    	" DOANHSO left join  " +
			 	    	" ( " +
			 	    	"	select a.NPP_FK, a.KBH_FK, c.PK_SEQ as spId, b.CTKMID as SCHEME, SUM(b.SOLUONG) as totalTRA, SUM(b.TONGGIATRI) as totalTienTRA  " +
			 	    	"	from DONHANG a inner join DONHANG_CTKM_TRAKM b on a.PK_SEQ = b.DONHANGID  " +
			 	    	"		inner join SANPHAM c on b.SPMA = c.MA  " +
			 	    	"   where a.TRANGTHAI != 2 and a.NGAYNHAP >= '" + obj.gettungay() + "' and a.NGAYNHAP <= '" + obj.getdenngay() + "' and b.SOLUONG != 0  " +
			 	    	"	group by a.NPP_FK, a.KBH_FK, c.PK_SEQ, b.CTKMID  " +
			 	    	" ) " +
			 	    	" KHUYENMAI on KHUYENMAI.NPP_FK = DOANHSO.NPP_FK and KHUYENMAI.KBH_FK = DOANHSO.KBH_FK and DOANHSO.SANPHAM_FK = KHUYENMAI.spId " +
			 	    	" inner join NHAPHANPHOI NPP on DOANHSO.NPP_FK = NPP.PK_SEQ " +
			 	    	" inner join KHUVUC KV on NPP.KHUVUC_FK = KV.PK_SEQ " +
			 	    	" inner join VUNG V on KV.VUNG_FK = V.PK_SEQ " +
			 	    	" inner join SANPHAM SP on DOANHSO.SANPHAM_FK = SP.PK_SEQ " +
			 	    	" inner join DONVIKINHDOANH DVKD on SP.DVKD_FK = DVKD.PK_SEQ " +
			 	    	" inner join KENHBANHANG KBH on DOANHSO.KBH_FK = KBH.PK_SEQ " +
			 	    	" left join CTKHUYENMAI CTKM on KHUYENMAI.SCHEME = CTKM.PK_SEQ where 1 = 1 ";
	 	    	
	 	    	if(condition.trim().length() > 0)
	 	    		sql += condition;
 	    	}
 	    	else
 	    	{
 	    		sql = " SELECT KBH.DIENGIAI AS KENH,V.TEN AS VUNG,KV.TEN AS KHUVUC,NPP.SITECODE,NPP.TEN AS TENNPP,'' as SCHEME, '' as CTKM  "+
				  " ,DVKD.DIENGIAI AS DVKD,   	NH.TEN AS NHANHANG,CL.TEN AS CHUNGLOAI,SP.MA AS MASP,SP.TEN AS TENSP,NHAP.DS_SOLUONG," +
				 
				 " NHAP.DS_TONGGIATRI, "+
				 " NHAP.SD_SOLUONG,NHAP.SD_TONGGIATRI,NHAP.TRA_SOLUONG,NHAP.TRA_TONGGIATRI    	,QC.SOLUONG1 AS QC1,QC.SOLUONG2 AS QC2   "+
				 " ,  ISNULL( (  " +
			
				 
				 " SELECT top 1 BGSP.GIAMUANPP "+
				 " FROM BANGGIAMUANPP B  "+
				 " INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK   "+
				 " INNER JOIN BGMUANPP_SANPHAM BGSP ON BGSP.BGMUANPP_FK=B.PK_SEQ "+
				 " WHERE  B.TUNGAY <='"+obj.gettungay()+"' AND   "+
				 " B.PK_SEQ = "+
				 " (   "+
				 " SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG "+
				 " INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK  "+
				 " WHERE BG.TUNGAY <= '"+obj.gettungay()+"' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK "+
				 " ORDER BY TUNGAY DESC "+
				 " )  AND C.NPP_FK=NHAP.NPP_FK  AND B.KENH_FK=NHAP.KBH_FK AND BGSP.SANPHAM_FK=SP.PK_SEQ  "+
				 " ) ,0) AS DONGIA ,ISNULL(TONGDS.TONGGIATRI,0) AS TONGDS,ISNULL(TONGKM.TONGGIATRI,0) AS TONGKM ," +
				 " '' AS PROGRAM_TYPE   "+
				 " FROM   "+
				 " (    "+
				 " SELECT  '-1' AS CTKMID , "+
				 " ISNULL(SD.KBH_FK ,ISNULL( TRA.KBH_FK ,DS.KBH_FK )) AS KBH_FK , "+
				 " ISNULL(SD.NPP_FK ,ISNULL( TRA.NPP_FK ,DS.NPP_FK )) AS NPP_FK , "+
				 " ISNULL(SD.SANPHAM_FK ,ISNULL( TRA.SANPHAM_FK ,DS.MA )) AS SANPHAM_FK  "+
				 " ,ISNULL(DS.SOLUONG,0)  AS DS_SOLUONG, ISNULL(DS.TONGGIATRI,0) AS DS_TONGGIATRI "+
				 " ,ISNULL(SD.SOLUONG,0)  AS SD_SOLUONG, ISNULL(SD.TONGGIATRI,0) AS SD_TONGGIATRI "+
				 " ,ISNULL(TRA.SOLUONG,0)  AS TRA_SOLUONG, ISNULL(TRA.TONGGIATRI,0) AS TRA_TONGGIATRI "+
				 " FROM  (	 "+
					 " SELECT DH.NPP_FK, DH.KBH_FK, TRAKM.SPMA AS SANPHAM_FK, '-1' as CTKMID, SUM(TRAKM.SOLUONG) AS SOLUONG,    "+
					 	" SUM(TRAKM.TONGGIATRI) AS TONGGIATRI  ,N'SỬ DỤNG' AS TYPE "+
					 " FROM DONHANG DH INNER JOIN DONHANG_CTKM_TRAKM TRAKM ON DH.PK_SEQ=TRAKM.DONHANGID INNER JOIN CTKHUYENMAI CTKM ON TRAKM.CTKMID = CTKM.pk_seq  "+
					 " WHERE  TRAKM.SOLUONG >0 AND DH.NGAYNHAP >='"+obj.gettungay()+"' AND DH.NGAYNHAP <='"+obj.getdenngay()+"'  AND TRAKM.SPMA IS NOT NULL   "+
					 " AND  DH.TRANGTHAI=1  AND CTKM.KHO_FK = '100000'   "+
					 " GROUP BY DH.NPP_FK,DH.KBH_FK,TRAKM.SPMA    "+
				 " ) SD  FULL OUTER JOIN  "+
				 " ( "+
					 " SELECT NH.NPP_FK,NH.KBH_FK,NH_SP.SANPHAM_FK, '-1' as CTKM_FK, SUM(CAST( SOLUONG AS INT) ) AS SOLUONG    "+
					 	" ,SUM(CAST( SOLUONG AS INT) * 0 ) AS TONGGIATRI ,N'TRẢ KM' AS TYPE "+
					 " FROM NHAPHANG NH    	  "+
						 " INNER JOIN NHAPHANG_SP NH_SP ON NH.PK_SEQ=NH_SP.NHAPHANG_FK   	 "+
						 " INNER JOIN CTKHUYENMAI CTKM ON CTKM.SCHEME=NH_SP.SCHEME   	 "+
						 " WHERE NH_SP.SCHEME IS NOT NULL AND NH_SP.SCHEME <> '' AND NH.NGAYCHUNGTU >='"+obj.gettungay()+"' "+
						 " AND NH.NGAYCHUNGTU <='"+obj.getdenngay()+"'  AND CTKM.KHO_FK = '100000'	" +
				 	 " GROUP BY NH.NPP_FK,NH_SP.SANPHAM_FK, NH.KBH_FK "+
				 " ) TRA ON TRA.KBH_FK=SD.KBH_FK AND TRA.NPP_FK=SD.NPP_FK AND TRA.SANPHAM_FK=SD.SANPHAM_FK "+
				 " FULL OUTER JOIN   "+
				 " ( "+
				"	  SELECT    DH.NPP_FK,DH.KBH_FK,SP.MA,  '-1' as CTKMID, "+  
				"	 	 SUM(DHSP.SOLUONG) AS SOLUONG ,  "+
				"	 	 SUM( DHSP.SOLUONG * DHSP.GIAMUA)  AS TONGGIATRI,N'HÀNG BÁN' AS TYPE "+ 
				"	  FROM  "+
				"	  ( "+
				"		SELECT DISTINCT DH.NPP_FK,DH.KBH_FK ,TRAKM.DONHANGID,TRAKM.CTKMID "+
				"		FROM DONHANG DH INNER JOIN DONHANG_CTKM_TRAKM TRAKM ON TRAKM.DONHANGID=DH.PK_SEQ "+ 
				"			INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ=TRAKM.CTKMID "+
				"		WHERE DH.TRANGTHAI=1 AND DH.NGAYNHAP >='"+obj.gettungay()+"' "+
				"		AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' 	 AND KM.LOAICT!='2' AND TRAKM.SOLUONG>0 "+
				"		AND KM.KHO_FK = '100000' "+
				"	  ) AS DH INNER JOIN  DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK=DH.DONHANGID "+
				"		  INNER JOIN SANPHAM SP ON SP.PK_SEQ=DHSP.SANPHAM_FK  "+
				"		  INNER JOIN CTKM_DKKM AS D ON DH.CTKMID = D.CTKHUYENMAI_FK "+
				"		  INNER JOIN DIEUKIENKM_SANPHAM AS E ON D.DKKHUYENMAI_FK = E.DIEUKIENKHUYENMAI_FK "+ 
				"		  AND DHSP.SANPHAM_FK = E.SANPHAM_FK  "+
				"	  GROUP BY  DH.NPP_FK, DH.KBH_FK, SP.MA "+
				 " ) AS DS   ON DS.KBH_FK=SD.KBH_FK AND DS.NPP_FK=SD.NPP_FK AND DS.MA=SD.SANPHAM_FK AND  "+
				 " SD.CTKMID=DS.CTKMID  "+
				 " ) NHAP  " +
				 " left join " +
				 " ( " +
					 " SELECT DH.NPP_FK,DH.KBH_FK, '-1' as CTKMID, "+   
					 " SUM(TRAKM.TONGGIATRI) AS TONGGIATRI  "+
					 " FROM DONHANG DH INNER JOIN DONHANG_CTKM_TRAKM TRAKM ON DH.PK_SEQ=TRAKM.DONHANGID INNER JOIN CTKHUYENMAI CTKM on TRAKM.CTKMID = CTKM.pk_seq "+  
					 " WHERE TRAKM.SOLUONG >0  AND DH.NGAYNHAP >='"+obj.gettungay()+"'  AND DH.NGAYNHAP <='"+obj.getdenngay()+"'  AND TRAKM.SPMA IS NOT NULL "+  
					 " AND  DH.TRANGTHAI=1  AND CTKM.KHO_FK = '100000'   "+
					 " GROUP BY DH.NPP_FK, DH.KBH_FK   " +
				 ") " +
				 " as TONGKM  ON TONGKM.NPP_FK= NHAP.NPP_FK AND NHAP.KBH_FK = TONGKM.KBH_FK AND NHAP.CTKMID= TONGKM.CTKMID  " +
				 " LEFT JOIN " +
				 " ( " +
				 "	  SELECT    DH.NPP_FK,DH.KBH_FK,  '-1' as CTKMID, "+  
					"	 	 SUM(DHSP.SOLUONG) AS SOLUONG ,  "+
					"	 	 SUM( DHSP.SOLUONG * DHSP.GIAMUA)  AS TONGGIATRI "+ 
					"	  FROM  "+
					"	  ( "+
					"		SELECT DISTINCT DH.NPP_FK,DH.KBH_FK ,TRAKM.DONHANGID,TRAKM.CTKMID "+
					"		FROM DONHANG DH INNER JOIN DONHANG_CTKM_TRAKM TRAKM ON TRAKM.DONHANGID=DH.PK_SEQ "+ 
					"			INNER JOIN CTKHUYENMAI KM ON KM.PK_SEQ=TRAKM.CTKMID "+
					"		WHERE DH.TRANGTHAI=1 AND DH.NGAYNHAP >='"+obj.gettungay()+"' "+
					"		AND DH.NGAYNHAP <= '"+obj.getdenngay()+"' 	 AND KM.LOAICT!='2' AND TRAKM.SOLUONG>0 "+
					"		AND KM.KHO_FK = '100000' "+
					"	  ) AS DH INNER JOIN  DONHANG_SANPHAM DHSP ON DHSP.DONHANG_FK=DH.DONHANGID "+
					"		  INNER JOIN SANPHAM SP ON SP.PK_SEQ=DHSP.SANPHAM_FK  "+
					"		  INNER JOIN CTKM_DKKM AS D ON DH.CTKMID = D.CTKHUYENMAI_FK "+
					"		  INNER JOIN DIEUKIENKM_SANPHAM AS E ON D.DKKHUYENMAI_FK = E.DIEUKIENKHUYENMAI_FK "+ 
					"		  AND DHSP.SANPHAM_FK = E.SANPHAM_FK  "+
					"	  GROUP BY  DH.NPP_FK, DH.KBH_FK "+
				 " ) AS TONGDS ON TONGDS.NPP_FK= NHAP.NPP_FK AND NHAP.KBH_FK = TONGDS.KBH_FK AND NHAP.CTKMID= TONGDS.CTKMID  " +
				 "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=NHAP.NPP_FK   "+
				 " LEFT JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK   LEFT JOIN VUNG V ON V.PK_SEQ=KV.VUNG_FK   "+
				 " LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=NHAP.KBH_FK    "+
				 " LEFT JOIN CTKHUYENMAI CTKM ON CTKM.PK_SEQ=NHAP.CTKMID   "+
				 " LEFT JOIN SANPHAM SP ON SP.MA=NHAP.SANPHAM_FK    "+
				 " LEFT JOIN QUYCACH QC ON SP.PK_SEQ = QC.SANPHAM_FK AND QC.DVDL2_FK=100018    "+
				 " LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=SP.DVKD_FK   "+
				 " LEFT JOIN NHANHANG NH ON NH.PK_SEQ=SP.NHANHANG_FK    "+
				 " LEFT JOIN CHUNGLOAI CL ON CL.PK_SEQ=SP.CHUNGLOAI_FK "+
				 " WHERE 1=1     " + condition.replace(" AND CTKM.kho_fk = '100000' ", "") + 
				 " ORDER BY NHAP.KBH_FK,NHAP.NPP_FK ";
 	    		
 	    	
 	    		
 	    	}
 	    }
 	    
 	    
 	   System.out.println("Get Sql : "+sql);
 	   	ResultSet rs = db.get(sql); 	   
 	    int i = 2;
 	    
 		if(rs!=null)
 		{
 			try 
 			{
 				for(int ii=0;ii<10;ii++)
 				{
 					cells.setColumnWidth(ii, 19.0f);
 				}
 				 				
 				Cell cell = null;
 				Style style; 		

 				while(rs.next())
 				{  				
 					if(obj.getMuclay().equals("0"))
 					{
 						cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(rs.getString("kenh"));
 						cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(rs.getString("dvkd"));			
 						cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(rs.getString("vung"));
 	 					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(rs.getString("khuvuc"));
 	 					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(rs.getString("sitecode"));
 						cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(rs.getString("tennpp"));
 						cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(rs.getString("scheme"));
 						cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(rs.getString("ctkm"));
 						cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(rs.getString("nhanhang"));
 						cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(rs.getString("chungloai"));
 						cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(rs.getString("masp"));
 						cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(rs.getString("tensp"));
 						
 						style = cell.getStyle();
 						style.setNumber(2);
 						cell.setStyle(style);
 						double soluong=rs.getDouble("DS_SOLUONG");
 						cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(soluong);
 						if(obj.getvat().equals("1")){
 							cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(rs.getLong("DS_TONGGIATRI"));					
 						}else{
 							cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(rs.getLong("DS_TONGGIATRI")/ 1.1);	
 						}
 						int qc1=rs.getInt("qc1");
 						int qc2=rs.getInt("qc2");
 						
 						if(qc1 <= 0)
 							qc1 = 1;
 						if(qc2 <=0 )
 							qc2 = 1;
 						
 						double slThg= 0;
 						long soluongle=Math.round(soluong % (qc1/qc2));
 						slThg=(soluong- soluongle)/(qc1/qc2);
 						cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(slThg);
 						style = cell.getStyle();style.setNumber(2);
 						cell.setStyle(style);
 						cell = cells.getCell("EP" + Integer.toString(i)); cell.setValue(soluongle);
 						 
 						soluong=rs.getDouble("SD_SOLUONG");
 						cell = cells.getCell("EQ" + Integer.toString(i)); cell.setValue(soluong);
 						if(obj.getvat().equals("1"))
 						{
 							
 							if(rs.getString("masp")==null)
 							{
 								cell = cells.getCell("ER" + Integer.toString(i)); cell.setValue(rs.getLong("SD_TONGGIATRI") );
 							}
 							else 
 							{
 								cell = cells.getCell("ER" + Integer.toString(i)); cell.setValue(rs.getLong("SD_TONGGIATRI")*1.1);
 							}
 						}else
 						{
 							if(rs.getString("masp")==null)
 							{
 								cell = cells.getCell("ER" + Integer.toString(i)); cell.setValue(rs.getLong("SD_TONGGIATRI")/1.1 );
 							}
 							else 
 							{
 								cell = cells.getCell("ER" + Integer.toString(i)); cell.setValue(rs.getLong("SD_TONGGIATRI") );
 							}
 							
 						}
 						  qc1=rs.getInt("qc1");
 						  qc2=rs.getInt("qc2");
 						
 						if(qc1 <= 0)
 							qc1 = 1;
 						if(qc2 <=0 )
 							qc2 = 1;
 						
 						  slThg= 0;
 						  soluongle=Math.round(soluong % (qc1/qc2));
 						slThg=(soluong- soluongle)/(qc1/qc2);
 						cell = cells.getCell("ES" + Integer.toString(i)); cell.setValue(slThg);
 						style = cell.getStyle();style.setNumber(2);
 						cell.setStyle(style);
 						cell = cells.getCell("ET" + Integer.toString(i)); cell.setValue(soluongle);
 						
 						if(obj.getvat().equals("1"))
 						{
 							cell = cells.getCell("EU" + Integer.toString(i)); cell.setValue(soluongle*rs.getDouble("dongia")*1.1);
 						}else 
 						{
 							cell = cells.getCell("EU" + Integer.toString(i)); cell.setValue(soluongle*rs.getDouble("dongia"));
 						}
 					
 						// tra
 						
 						soluong=rs.getDouble("TRA_SOLUONG");
 						cell = cells.getCell("EV" + Integer.toString(i)); cell.setValue(soluong);
 						if(obj.getvat().equals("1")){
 							cell = cells.getCell("EW" + Integer.toString(i)); cell.setValue(rs.getLong("TRA_TONGGIATRI")*1.1);					
 						}else{
 							cell = cells.getCell("EW" + Integer.toString(i)); cell.setValue(rs.getLong("TRA_TONGGIATRI"));
 						}
 						qc1=rs.getInt("qc1");
 						qc2=rs.getInt("qc2");
 						
 						if(qc1 <= 0)
 							qc1 = 1;
 						if(qc2 <=0 )
 							qc2 = 1;
 						slThg= 0;
 						soluongle=Math.round(soluong % (qc1/qc2));
 						slThg=(soluong- soluongle)/(qc1/qc2);
 						cell = cells.getCell("EX" + Integer.toString(i)); cell.setValue(slThg);
 						style = cell.getStyle();style.setNumber(2);
 						cell.setStyle(style);
 						cell = cells.getCell("EY" + Integer.toString(i)); cell.setValue(soluongle);
 						if(rs.getDouble("TONGDS") >0){
 							if(obj.getvat().equals("1")){
 								cell = cells.getCell("EZ" + Integer.toString(i)); cell.setValue((rs.getDouble("TONGKM")*1.1 )/(rs.getDouble("TONGDS")) );
 							}else{
 								cell = cells.getCell("EZ" + Integer.toString(i)); cell.setValue(rs.getDouble("TONGKM")/(rs.getDouble("TONGDS")/1.1));
 							}
 						}else{
 							cell = cells.getCell("EZ" + Integer.toString(i)); cell.setValue(0);
 						}
 						cell = cells.getCell("FA" + Integer.toString(i)); cell.setValue(rs.getString("PROGRAM_TYPE"));
 					}
 					else
 					{
 						if(obj.getMuclay().equals("1"))
 						{
 							cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(rs.getString("kbhTEN"));
 	 						cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(rs.getString("DONVIKINHDOANH"));			
 	 						cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(rs.getString("vungTEN"));
 	 	 					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(rs.getString("kvTEN"));
 	 	 					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(rs.getString("nppMa"));
 	 						cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(rs.getString("nppTen"));
 	 						cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(rs.getString("spMa"));
 	 						cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(rs.getString("spTen"));
 	 						cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(rs.getString("SCHEME"));
 	 						
 	 						cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(rs.getDouble("DS_SOLUONG"));
 	 						
 	 						double DS = rs.getDouble("DOANHSO");
 	 						if(!obj.getvat().equals("1"))
 	 							DS = DS / 1.1;
 	 						cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(DS);
 	 						
 	 						cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(rs.getDouble("tongSLTra"));
 	 						
 	 						double tongTienTra = rs.getDouble("tongTienTra");
 	 						if(obj.getvat().equals("1"))
 	 							tongTienTra = tongTienTra * 1.1;
 	 						cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(tongTienTra);
 	 						
 	 						double tyle = 0;
 	 						
 	 						if(rs.getDouble("DOANHSO") != 0)
 	 							tyle = rs.getDouble("tongTienTra") / rs.getDouble("DOANHSO");
 	 						
 	 						cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(tyle);
 						}
 						else
 						{
 							cell = cells.getCell("EA" + Integer.toString(i)); cell.setValue(rs.getString("kenh"));
 	 						cell = cells.getCell("EB" + Integer.toString(i)); cell.setValue(rs.getString("dvkd"));			
 	 						cell = cells.getCell("EC" + Integer.toString(i)); cell.setValue(rs.getString("vung"));
 	 	 					cell = cells.getCell("ED" + Integer.toString(i)); cell.setValue(rs.getString("khuvuc"));
 	 	 					cell = cells.getCell("EE" + Integer.toString(i)); cell.setValue(rs.getString("sitecode"));
 	 						cell = cells.getCell("EF" + Integer.toString(i)); cell.setValue(rs.getString("tennpp"));
 	 						cell = cells.getCell("EG" + Integer.toString(i)); cell.setValue(rs.getString("nhanhang"));
 	 						cell = cells.getCell("EH" + Integer.toString(i)); cell.setValue(rs.getString("chungloai"));
 	 						cell = cells.getCell("EI" + Integer.toString(i)); cell.setValue(rs.getString("masp"));
 	 						cell = cells.getCell("EJ" + Integer.toString(i)); cell.setValue(rs.getString("tensp"));
 	 						
 	 						style = cell.getStyle();
 	 						style.setNumber(2);
 	 						cell.setStyle(style);
 	 						double soluong=rs.getDouble("DS_SOLUONG");
 	 						cell = cells.getCell("EK" + Integer.toString(i)); cell.setValue(soluong);
 	 						if(obj.getvat().equals("1")){
 	 							cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(rs.getLong("DS_TONGGIATRI"));					
 	 						}else{
 	 							cell = cells.getCell("EL" + Integer.toString(i)); cell.setValue(rs.getLong("DS_TONGGIATRI")/ 1.1);	
 	 						}
 	 						int qc1=rs.getInt("qc1");
 	 						int qc2=rs.getInt("qc2");
 	 						
 	 						if(qc1 <= 0)
 	 							qc1 = 1;
 	 						if(qc2 <=0 )
 	 							qc2 = 1;
 	 						
 	 						double slThg= 0;
 	 						long soluongle=Math.round(soluong % (qc1/qc2));
 	 						slThg=(soluong- soluongle)/(qc1/qc2);
 	 						cell = cells.getCell("EM" + Integer.toString(i)); cell.setValue(slThg);
 	 						style = cell.getStyle();style.setNumber(2);
 	 						cell.setStyle(style);
 	 						cell = cells.getCell("EN" + Integer.toString(i)); cell.setValue(soluongle);
 	 						 
 	 						soluong=rs.getDouble("SD_SOLUONG");
 	 						cell = cells.getCell("EO" + Integer.toString(i)); cell.setValue(soluong);
 	 						if(obj.getvat().equals("1"))
 	 						{
 	 							cell = cells.getCell("EP" + Integer.toString(i)); cell.setValue(rs.getLong("SD_TONGGIATRI")*1.1);					
 	 						}else{
 	 							cell = cells.getCell("EP" + Integer.toString(i)); cell.setValue(rs.getLong("SD_TONGGIATRI") );
 	 						}
 	 						  qc1=rs.getInt("qc1");
 	 						  qc2=rs.getInt("qc2");
 	 						
 	 						if(qc1 <= 0)
 	 							qc1 = 1;
 	 						if(qc2 <=0 )
 	 							qc2 = 1;
 	 						
 	 						  slThg= 0;
 	 						  soluongle=Math.round(soluong % (qc1/qc2));
 	 						slThg=(soluong- soluongle)/(qc1/qc2);
 	 						cell = cells.getCell("EQ" + Integer.toString(i)); cell.setValue(slThg);
 	 						style = cell.getStyle();style.setNumber(2);
 	 						cell.setStyle(style);
 	 						cell = cells.getCell("ER" + Integer.toString(i)); cell.setValue(soluongle);
 	 						
 	 						if(obj.getvat().equals("1"))
 	 						{
 	 							cell = cells.getCell("ES" + Integer.toString(i)); cell.setValue(soluongle*rs.getDouble("dongia")*1.1);
 	 						}else 
 	 						{
 	 							cell = cells.getCell("ES" + Integer.toString(i)); cell.setValue(soluongle*rs.getDouble("dongia"));
 	 						}
 	 					
 	 						// tra
 	 						
 	 						soluong=rs.getDouble("TRA_SOLUONG");
 	 						cell = cells.getCell("ET" + Integer.toString(i)); cell.setValue(soluong);
 	 						if(obj.getvat().equals("1")){
 	 							cell = cells.getCell("EU" + Integer.toString(i)); cell.setValue(rs.getLong("TRA_TONGGIATRI")*1.1);					
 	 						}else{
 	 							cell = cells.getCell("EU" + Integer.toString(i)); cell.setValue(rs.getLong("TRA_TONGGIATRI"));
 	 						}
 	 						qc1=rs.getInt("qc1");
 	 						qc2=rs.getInt("qc2");
 	 						
 	 						if(qc1 <= 0)
 	 							qc1 = 1;
 	 						if(qc2 <=0 )
 	 							qc2 = 1;
 	 						slThg= 0;
 	 						soluongle=Math.round(soluong % (qc1/qc2));
 	 						slThg=(soluong- soluongle)/(qc1/qc2);
 	 						cell = cells.getCell("EV" + Integer.toString(i)); cell.setValue(slThg);
 	 						style = cell.getStyle();style.setNumber(2);
 	 						cell.setStyle(style);
 	 						cell = cells.getCell("EW" + Integer.toString(i)); cell.setValue(soluongle);
 	 						if(rs.getDouble("TONGDS") >0){
 	 							if(obj.getvat().equals("1")){
 	 								cell = cells.getCell("EX" + Integer.toString(i)); cell.setValue((rs.getDouble("TONGKM")*1.1 )/(rs.getDouble("TONGDS")) );
 	 							}else{
 	 								cell = cells.getCell("EX" + Integer.toString(i)); cell.setValue(rs.getDouble("TONGKM")/(rs.getDouble("TONGDS")/1.1));
 	 							}
 	 						}else{
 	 							cell = cells.getCell("EX" + Integer.toString(i)); cell.setValue(0);
 	 						}
 	 						//cell = cells.getCell("EZ" + Integer.toString(i)); cell.setValue(rs.getString("PROGRAM_TYPE"));
 						}
 						
 					}
					 
					i++;
 				}
			    setAn(workbook, 50);
 		}catch (Exception e)
 		{ 	
 			e.printStackTrace();
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay. Loi : "+e.toString());
 		}
 		finally
 		{
 			if(rs != null)
 			rs.close();
 			if(db!=null)
 			{
 				db.shutDown();
 			}
 		}
 		}else
 		{
 			throw new Exception("Khong tao duoc bao cao trong thoi gian nay...");
 		}
 	}

 	private void getCellStyle(Workbook workbook, String a, Color mau, Boolean dam, int size)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
		Style style;
		Cell cell = cells.getCell(a); 
		style = cell.getStyle();
	    Font font1 = new Font();
	    font1.setColor(mau);
	    font1.setBold(dam);
	    font1.setSize(size);
	    style.setFont(font1);
	    
		 
	    style.setHAlignment(TextAlignmentType.LEFT);
	    cell.setStyle(style);
	}

	private String getDate() 
	{
        DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy: hh:mm:ss");
        Date date = new Date();
        return dateFormat.format(date);	
	}

	private void setAn(Workbook workbook,int i)
	{
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	   	   
	    Cells cells = worksheet.getCells();
	    for(int j = 26; j <= i; j++)
	    { 
	    	cells.hideColumn(j);
	    }
		
	}

}
