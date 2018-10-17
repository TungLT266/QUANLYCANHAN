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
import java.util.Hashtable;

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
//import com.aspose.cells.a.a.a.q;

public class SalesreperformanceGroupSku extends HttpServlet {
	private static final long serialVersionUID = 1L; 
    public SalesreperformanceGroupSku() {
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
	    obj.init();
	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", userId);
		String nextJSP = "/TraphacoHYERP/pages/Center/SalesrepPerfomanceGroupSku.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();	
	    Utility util = new Utility();
	  
	    obj.setuserId((String)session.getAttribute("userId")==null?"":
	    				(String) session.getAttribute("userId"));
	    
	    obj.setuserTen((String)session.getAttribute("userTen")==null? "":
	    					(String) session.getAttribute("userTen"));
	    
   	 	obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))==null?"":
   	 						util.antiSQLInspection(request.getParameter("nppId")));
   	 	
   	 	obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))==null? "":
   	 						util.antiSQLInspection(request.getParameter("kenhId")));
   	 	
	   	 obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("dvkdId")));
	   	 
	   	 obj.setMonth(util.antiSQLInspection(request.getParameter("month"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("month")));
	   	 
	   	 obj.setYear(util.antiSQLInspection(request.getParameter("year"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("year")));	   	 
	 	 
	   	 obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("vungId")));	   	 
	   	 
	   	 obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))==null? "":
	   		 				util.antiSQLInspection(request.getParameter("khuvucId")));	 
	   	 	   	 
	   	
		 obj.setdvdlId(util.antiSQLInspection(request.getParameter("dvdlId"))==null? "":
			 				util.antiSQLInspection(request.getParameter("dvdlId")));		 
		
		 obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId"))==null? "":
			 				util.antiSQLInspection(request.getParameter("ddkdId")));
		 obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId"))==null? "":
				util.antiSQLInspection(request.getParameter("gsbhId")));
		 
		 
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);		 
	 
		 
		String nextJSP = "/TraphacoHYERP/pages/Center/SalesrepPerfomanceGroupSku.jsp";		 
		try{
			String action=util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", 
		        		"attachment; filename=ThucHienChiTieuTT.xlsm");
		        OutputStream out = response.getOutputStream();
		        String query = setQuery(obj);
		        CreatePivotTable(out,obj,query);
			}			
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
		}
		obj.init();	    
		session.setAttribute("obj", obj);
		session.setAttribute("userId", obj.getuserId());		
		response.sendRedirect(nextJSP);
	}
	private String setQuery( IStockintransit obj) {
		
		String fromYear = obj.getYear();
		String fromMonth = obj.getMonth();
		String query="";
		//long restWD = 0;
		long numofDay = 0;
		geso.traphaco.center.db.sql.dbutils db=new geso.traphaco.center.db.sql.dbutils();
		 String sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
					" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
					" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
					" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		 
		 if(obj.getdvkdId().length()>0){
			 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
			 
		 }
		 if(obj.getkenhId().length()>0){
			 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			 
		 }
		 
		 ResultSet rs=db.get(sql);
		 String chuoi="";
		 String[] arraychuoi= new String[10];
		 String chuoiselct="";
		 String chuoingoac="";//co dau []
		 if(rs!=null){
			 int i=0;
			 try {
				while (rs.next()){
					
					 if(i==0){
						 chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
						 chuoi=rs.getString("nhomsanpham_fk");
						 chuoiselct="isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						 chuoiselct=chuoiselct+", isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk");
						 chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoi[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		 }
		

		 
		obj.setFieldShow(arraychuoi);
		 
		sql= 
		" SELECT KBH.TEN AS KENH,DVKD.DONVIKINHDOANH ,VUNG.TEN AS VUNG,KV.TEN AS KHUVUC,NPP.TEN AS NHAPHANPHOI, "+ 
		"  CTNHOM.NPP_FK,CTNHOM.DDKD_FK,CTNHOM.KENH_FK,CTNHOM.DVKD_FK,'1'+cast(DDKD.PK_SEQ as nvarchar(10)) AS DDKDID, DDKD.TEN AS DAIDIENKINHDOANH,SONGAYLAMVIEC, ISNULL(ds1.DOPHU,0) AS THUCDATDOPHU,ISNULL(ds1.DOANHSO,0)-ISNULL(TRAVE.DOANHSO,0) AS THUCDATDOANHSO ,ISNULL(ds1.sodonhang,0) AS THUCDATDONHANG, CTNHOM.CTDONHANG,CTNHOM.CTDOPHU,CTNHOM.CTDOANHSO "+
		" , "+ chuoiselct +
		" FROM "+
		" ("+
		" SELECT CTNPP.NHAPP_FK AS NPP_FK,CTNPP.PK_SEQ,CTNPP.KENH_FK,CTNPP.DVKD_FK, B.DDKD_FK  " +
		" ,CTNPP.SONGAYLAMVIEC,A.SODONHANG AS CTDONHANG ,A.DOPHU AS CTDOPHU,A.CHITIEU AS CTDOANHSO , B.CHITIEU,B.NHOMSANPHAM_FK"+
		" FROM CHITIEUNPP_DDKD_NHOMSP B INNER JOIN CHITIEUNPP_DDKD A ON "+
		" A.CHITIEUNPP_FK=B.CHITIEUNPP_FK AND A.DDKD_FK=B.DDKD_FK "+
		" INNER JOIN CHITIEUNPP CTNPP ON CTNPP.PK_SEQ=B.CHITIEUNPP_FK   " +
		" WHERE CTNPP.THANG="+obj.getMonth()+" AND CTNPP.NAM="+obj.getYear()+" and trangthai<>'2' ) P"+
		" PIVOT "+
		" ( "+
		" SUM(CHITIEU) "+
		" FOR NHOMSANPHAM_FK IN "+
		" ( "+chuoingoac+" ) "+
		" ) AS CTNHOM "+
		" INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CTNHOM.NPP_FK "+
		" INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK "+
		" INNER JOIN VUNG  ON VUNG.PK_SEQ=KV.VUNG_FK "+
		" INNER JOIN KENHBANHANG KBH ON KBH.PK_SEQ=CTNHOM.KENH_FK "+
		" INNER JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=CTNHOM.DVKD_FK "+
		" INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ=CTNHOM.DDKD_FK " +
		" INNER JOIN  (SELECT NPP_FK,MAX(NGAYKS)  AS NGAYKHOASO  FROM KHOASONGAY  GROUP BY NPP_FK ) AS KHOASO  ON KHOASO.NPP_FK=NPP.PK_SEQ  "+
		" LEFT join(" +
		"					select dh.npp_fk, dh.ddkd_fk, dh.kbh_fk,sp2.dvkd_fk,sum(dh_sp.soluong * dh_sp.giamua) as 'doanhso',count(distinct dh.pk_seq) as 'sodonhang', count(dh_sp.soluong) as 'tongsku' ,count(distinct khachhang_fk) as 'dophu', '6' as tieuchi" +
		"					from donhang dh inner join donhang_sanpham dh_sp on dh.pk_seq = dh_sp.donhang_fk" +
		"					inner join sanpham sp2 on sp2.pk_seq = dh_sp.sanpham_fk" +
		"					where substring(dh.ngaynhap, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' and dh.trangthai ='1'" +
		"					group by dh.ddkd_fk, dh.npp_fk,sp2.dvkd_fk, dh.kbh_fk" +
		" ) ds1 on CTNHOM.npp_fk = ds1.npp_fk and CTNHOM.KENH_FK = ds1.kbh_fk and CTNHOM.ddkd_fk =ds1.ddkd_fk and ds1.dvkd_fk = CTNHOM.dvkd_fk " +
		//THEM PHAN DON HANG TRA VE
		"  LEFT JOIN (" +
		"	SELECT dh.npp_fk, DH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, DH.DDKD_FK AS DDKD_FK ,  "+   	
		"		SUM(	ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) *    "+
		"		 ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  "+    
		"		FROM  DONHANGTRAVE DH    "+
		"	LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   "+  	
		"	LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK   "+
		"	INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK)  "+   
		"	WHERE DH.TRANGTHAI = 3   "+
		"		AND SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "'   "+ 	
		"		GROUP BY 	 dh.npp_fk, DH.KBH_FK, SP2.DVKD_FK, DH.DDKD_FK   ) TRAVE " +
		" ON CTNHOM.npp_fk = TRAVE.npp_fk and CTNHOM.KENH_FK =TRAVE.kbh_fk and CTNHOM.ddkd_fk = TRAVE.ddkd_fk and TRAVE.dvkd_fk = CTNHOM.dvkd_fk  "+

		" LEFT JOIN  "+
		" ( "+
		" SELECT DVKD_FK, DDKD_FK,KBH_FK , "+chuoingoac +
		" FROM (  "+
		
		" SELECT SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK, SUM( SOLUONG* DH_SP.GIAMUA) AS SANLUONG "+
		" FROM DONHANG DH INNER JOIN  "+
		" DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK "+
		" INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK "+
		" INNER JOIN ( "+
		" SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM "+
		" WHERE NSP_FK IN ("+chuoi+") "+
		" GROUP BY SP_FK "+
		" ) AS NSP ON NSP.SANPHAM_FK=SP.PK_SEQ "+
		" WHERE  DH.TRANGTHAI='1' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' "+
		" GROUP BY SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK  "+
		//THEMPHAN TRA HANG
		"  UNION ALL "+
		"  SELECT SP2.DVKD_FK AS DVKD_FK, DH.KBH_FK,  DH.DDKD_FK AS DDKD_FK , NSP.NHOMSP_FK ,  " ;

			sql= sql+	"(-1)*	 SUM(	ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) *   ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  " ;
		
		
		sql= sql+		"  FROM  DONHANGTRAVE DH    "+
		" LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ "+    	
		" LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK "+
		" INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) "+
		" INNER JOIN (   "+
		" SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM "+
		" WHERE NSP_FK IN ("+chuoi+") "+
		" GROUP BY SP_FK  "+
		" ) AS NSP ON NSP.SANPHAM_FK=SP2.PK_SEQ  "+
		" WHERE  DH.TRANGTHAI='3' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' "+
		" GROUP BY SP2.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK  "+
		
		" ) A "+
		" PIVOT "+
		" ( "+
		" SUM( SANLUONG) "+
		" FOR NHOMSP_FK IN "+
		" ( "+chuoingoac+" ) "+
		" ) AS T "+
		" ) AS DS ON   "+
		" CTNHOM.KENH_FK=DS.KBH_FK AND CTNHOM.DVKD_FK=DS.DVKD_FK AND CTNHOM.DDKD_FK=DS.DDKD_FK " ;
		
	
		
		
				geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		
		
			query += " where   npp.pk_seq in "+ ut.quyen_npp(obj.getuserId()) + " and kbh.pk_seq in " + ut.quyen_kenh(obj.getuserId());
			if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			if(obj.getnppId().length() >0)
				query += " and npp.pk_seq = '"+obj.getnppId()+"'";
			if(obj.getvungId().length() > 0)
				query += " and vung.pk_seq = '"+obj.getvungId()+"'";
			if(obj.getdvkdId().length() > 0)
				query += " and dvkd.pk_seq = '"+obj.getdvkdId()+"'";
			if(obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
			if(obj.getDdkd().length() > 0)
				query += " and ddkd.pk_seq = '"+obj.getDdkd()+"'";
			
			query = query + " order by kbh.ten, dvkd.donvikinhdoanh, vung.ten, kv.ten, npp.ten, ddkd.ten ";
		sql=sql+ query;
		System.out.println("1.Query SalesRep : " + sql);
		
		return sql;
	}

			private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
		    {   
				try{
					String chuoi=getServletContext().getInitParameter("path") + "\\SrThucHienChiTieuTTGroupSku.xlsm";
					FileInputStream fstream = new FileInputStream(chuoi);
					Workbook workbook = new Workbook();
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
					
					CreateStaticHeader(workbook,obj); 
					FillData(workbook,obj.getFieldShow(), query, obj); 
					workbook.save(out);
					fstream.close();
			     }catch(Exception ex){
			    	 throw new Exception(ex.getMessage());
			     }	    
		   }
			private Hashtable< Integer, String > htbValueCell (){
				Hashtable<Integer, String> htb=new Hashtable<Integer, String>();
				htb.put(1,"DA");htb.put(2,"DB");htb.put(3,"DC");htb.put(4,"DD");htb.put(5,"DE");
				htb.put(6,"DF");htb.put(7,"DG");htb.put(8,"DH");htb.put(9,"DI");htb.put(10,"DJ");
				htb.put(11,"DK");htb.put(12,"DL");htb.put(13,"DM");htb.put(14,"DN");htb.put(15,"DO");
				htb.put(16,"DP");htb.put(17,"DQ");htb.put(18,"DR");htb.put(19,"DS");htb.put(20,"DT");
				htb.put(21,"DU");htb.put(22,"DV");htb.put(23,"DW");htb.put(24,"DX");htb.put(25,"DY");
				htb.put(26,"DZ");htb.put(27,"EA");htb.put(28,"EB");htb.put(29,"EC");htb.put(30,"ED");
				htb.put(31,"EE");htb.put(32,"EF");htb.put(33,"EG");htb.put(34,"EH");htb.put(35,"EI");
				htb.put(36,"EJ");htb.put(37,"EK");htb.put(38,"EL");htb.put(39,"EM");htb.put(40,"EN");				
				return htb; 
			}
	private void CreateStaticHeader(Workbook workbook, IStockintransit obj) 
	{
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
		Worksheet worksheet = worksheets.getSheet(0);
		worksheet.setName("Sheet1");
		Cells cells = worksheet.getCells();

		Style style;		
		cells.setRowHeight(0, 20.0f);
		Cell cell = cells.getCell("A1");
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU NHÂN VIÊN BÁN HÀNG");

		style = cell.getStyle();

		Font font2 = new Font();
		font2.setColor(Color.RED);// mau chu
		font2.setSize(14);// size chu
		font2.setBold(true);
		style.setFont(font2);
		style.setHAlignment(TextAlignmentType.LEFT);// canh le cho chu
		cell.setStyle(style);

	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("A3");
	    
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Tháng : " + obj.getMonth() + "" );
	    
	    cells.setRowHeight(3, 18.0f);
	    cell = cells.getCell("B3"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Năm : " + obj.getYear() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo: " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Được tạo bởi:  " + obj.getuserTen());
	   
	    cell = cells.getCell(htb.get(1)+"1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell(htb.get(2)+"1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell(htb.get(3)+"1"); cell.setValue("Mien");
	    cell = cells.getCell(htb.get(4)+"1"); cell.setValue("Vung");
	    cell = cells.getCell(htb.get(5)+"1"); cell.setValue("MaNhaPhanPhoi");	
	    cell = cells.getCell(htb.get(6)+"1");cell.setValue("NhaPhanPhoi");  
	    cell = cells.getCell(htb.get(7)+"1");cell.setValue("Ma NVBH");  
	    
	    cell = cells.getCell(htb.get(8)+"1"); cell.setValue("NhanVienBanHang");	    	    
	    cell = cells.getCell(htb.get(9)+"1"); cell.setValue("NgayLamViec");
	   
	    cell = cells.getCell(htb.get(10)+"1"); cell.setValue("ChiTieuSoDH");	    
	    cell = cells.getCell(htb.get(11)+"1"); cell.setValue("ThucDatSoDH");
	    cell = cells.getCell(htb.get(12)+"1"); cell.setValue("%DonHang");	
	    cell = cells.getCell(htb.get(13)+"1"); cell.setValue("ChiTieuDoPhu");	    
	    cell = cells.getCell(htb.get(14)+"1"); cell.setValue("ThucDatDoPhu");
	    cell = cells.getCell(htb.get(15)+"1"); cell.setValue("%DoPhu");	
	    cell = cells.getCell(htb.get(16)+"1"); cell.setValue("ChiTieuSellsOut");	    
	    cell = cells.getCell(htb.get(17)+"1"); cell.setValue("ThucDatSellsOut");
	    cell = cells.getCell(htb.get(18)+"1"); cell.setValue("%SellsOut");
	    //cell = cells.getCell(htb.get(31)+"1"); cell.setValue("NgayCongThucTe");
	    
	    String sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
		" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
		" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
		" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
	    	dbutils db=new dbutils();
				if(obj.getdvkdId().length()>0){
				 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
				 
				}
				if(obj.getkenhId().length()>0){
				 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
				 
				}
				 int i=19;
				ResultSet rs=db.get(sql);
			
				if(rs!=null){
				
				 try {
					while (rs.next()){
						 
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieu-"+rs.getString("ten"));	
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDat-"+rs.getString("ten"));
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTram-"+rs.getString("ten"));
						 i=i+1;
					 }
					rs.close();
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				}
			
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("TongChiTieuNhom");	
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("TongThucDatCTNhom");
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTramCTNhom");
				
					
				 
				db.shutDown();
				
				cell = cells.getCell("M1"); 
			    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9,(26*4+i)+"");
	   
	}

	private void FillData(Workbook workbook,String[] fieldShow, String query, IStockintransit obj)throws Exception 
	{
		
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
		
	    cells.setColumnWidth(0, 10.0f);
		cells.setColumnWidth(1, 15.0f);
		cells.setColumnWidth(2, 15.0f);
		cells.setColumnWidth(3, 15.0f);
		cells.setColumnWidth(4, 15.0f);
		cells.setColumnWidth(5, 15.0f);
		cells.setColumnWidth(6, 15.0f);
		cells.setColumnWidth(7, 15.0f);
		cells.setColumnWidth(8, 15.0f);
		cells.setColumnWidth(9, 15.0f);
		cells.setColumnWidth(10, 15.0f);
		cells.setColumnWidth(11, 15.0f);
		cells.setColumnWidth(12, 15.0f);
		cells.setColumnWidth(13, 15.0f);
		cells.setColumnWidth(14, 15.0f);
		cells.setColumnWidth(15, 15.0f);
		cells.setColumnWidth(16, 15.0f);
		
		dbutils db = new dbutils();		
		
		
		
		
		
		ResultSet rs = db.get(query);	
		
		int indexRow = 3;
		try 
			{				
				Cell cell = null;
				float phantramMTD = 0;
				

				while(rs.next())
				{ 				
					
				    cell = cells.getCell(htb.get(1) + Integer.toString(indexRow)); cell.setValue(rs.getString("KENH"));
				    cell = cells.getCell(htb.get(2) + Integer.toString(indexRow)); cell.setValue(rs.getString("DONVIKINHDOANH"));
					cell = cells.getCell(htb.get(3) + Integer.toString(indexRow)); cell.setValue(rs.getString("VUNG"));
					cell = cells.getCell(htb.get(4) + Integer.toString(indexRow)); cell.setValue(rs.getString("KHUVUC"));					
					cell = cells.getCell(htb.get(5) + Integer.toString(indexRow));cell.setValue(rs.getString("NPP_FK"));				
					cell = cells.getCell(htb.get(6) + Integer.toString(indexRow));  cell.setValue(rs.getString("NHAPHANPHOI"));
					
					cell = cells.getCell(htb.get(7) + Integer.toString(indexRow));  cell.setValue(rs.getString("DDKDID"));
					
					cell = cells.getCell(htb.get(8)+ Integer.toString(indexRow)); cell.setValue(rs.getString("DAIDIENKINHDOANH"));
					cell = cells.getCell(htb.get(9)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("SONGAYLAMVIEC"));
					
					cell = cells.getCell(htb.get(10)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("CTDONHANG")*rs.getFloat("SONGAYLAMVIEC"));
					cell = cells.getCell(htb.get(11)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("THUCDATDONHANG"));
					float PhanTramDonhang=0;
					if(rs.getFloat("CTDONHANG")*rs.getFloat("SONGAYLAMVIEC") >0){
						PhanTramDonhang=	100* rs.getFloat("THUCDATDONHANG")/(rs.getFloat("CTDONHANG")*rs.getFloat("SONGAYLAMVIEC"));
					}
					cell = cells.getCell(htb.get(12)+ Integer.toString(indexRow)); cell.setValue(PhanTramDonhang);
					
					cell = cells.getCell(htb.get(13)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("CTDOPHU"));
					cell = cells.getCell(htb.get(14)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("THUCDATDOPHU"));
					float PhanTramDoPhu=0;
					if(rs.getFloat("CTDOPHU") >0){
						PhanTramDoPhu=	100* rs.getFloat("THUCDATDOPHU")/(rs.getFloat("CTDOPHU"));
					}
					cell = cells.getCell(htb.get(15)+ Integer.toString(indexRow)); cell.setValue(PhanTramDoPhu);

					cell = cells.getCell(htb.get(16)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("CTDOANHSO"));
					cell = cells.getCell(htb.get(17)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("THUCDATDOANHSO"));
					float PhanTramDoanhSo=0;
					if(rs.getFloat("CTDOANHSO") >0){
						PhanTramDoanhSo=	100* rs.getFloat("THUCDATDOANHSO")/(rs.getFloat("CTDOANHSO"));
					}
					cell = cells.getCell(htb.get(18)+ Integer.toString(indexRow)); cell.setValue(PhanTramDoanhSo);
					
					/*cell = cells.getCell(htb.get(31)+ Integer.toString(indexRow)); cell.setValue(rs.getString("ngaycongthucte"));*/

					
					String []chuoi =obj.getFieldShow();
					int j=19;
					float SumChiTieuDDKD=0;
					float SumThucDatCTDDKD=0;
					for (int i=0;i<chuoi.length ;i++){
						if(chuoi[i]!=null){
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("CT"+chuoi[i]));	
							SumChiTieuDDKD= SumChiTieuDDKD+rs.getFloat("CT"+chuoi[i]);
							SumThucDatCTDDKD= SumThucDatCTDDKD+rs.getFloat("DS"+chuoi[i]);
							j=j+1;
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DS"+chuoi[i]));
							j=j+1;
							phantramMTD =0;
							
							if(rs.getFloat("CT"+chuoi[i]) >0){
								phantramMTD=rs.getFloat("DS"+chuoi[i])*100/rs.getFloat("CT"+chuoi[i]);
							}
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
							j=j+1;
						}
					}
					
					cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(SumChiTieuDDKD);					
					
					j=j+1;
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumThucDatCTDDKD);
					j=j+1;
					float SumphantramMTD =0;
					
					if(SumChiTieuDDKD >0){
						SumphantramMTD=SumThucDatCTDDKD*100/SumChiTieuDDKD;
					}
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumphantramMTD);
					
					indexRow++;
				
				}
				if(rs != null) rs.close();
				if(db!=null){
					db.shutDown();
				}

		    	
			}catch(java.sql.SQLException err){
				System.out.println(err.toString());
				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
	}
}
