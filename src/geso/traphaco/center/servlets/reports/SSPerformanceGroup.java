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


public class SSPerformanceGroup extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SSPerformanceGroup() {
        super();
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
		htb.put(41,"EO");htb.put(42,"EP");htb.put(43,"EQ");htb.put(44,"ER");htb.put(45,"ES");
		htb.put(46,"ET");htb.put(47,"EU");htb.put(48,"EV");htb.put(49,"EW");htb.put(50,"EX");
		htb.put(51,"EY");htb.put(52,"EZ");
		
		
		return htb; 
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
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformanceGroup.jsp";
		response.sendRedirect(nextJSP);
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		request.setCharacterEncoding("UTF-8");
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("text/html; charset=UTF-8");
		HttpSession session = request.getSession();
	    IStockintransit obj = new Stockintransit();	
	    Utility util = new Utility();
	  
	    obj.setuserId((String)session.getAttribute("userId")==null?"":(String) session.getAttribute("userId"));
	    
	    obj.setuserTen((String)session.getAttribute("userTen")==null? "":(String) session.getAttribute("userTen"));
	    
   	 	obj.setnppId(util.antiSQLInspection(request.getParameter("nppId"))==null?"":util.antiSQLInspection(request.getParameter("nppId")));
   	 	
   	 	obj.setkenhId(util.antiSQLInspection(request.getParameter("kenhId"))==null? "":util.antiSQLInspection(request.getParameter("kenhId")));
   	 	
	   	 obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId")));
	   	 
	   	 obj.setMonth(util.antiSQLInspection(request.getParameter("month"))==null? "":util.antiSQLInspection(request.getParameter("month")));
	   	 
	   	 obj.setYear(util.antiSQLInspection(request.getParameter("year"))==null? "":util.antiSQLInspection(request.getParameter("year")));	   	 
	 	 
	   	 obj.setvungId(util.antiSQLInspection(request.getParameter("vungId"))==null? "":util.antiSQLInspection(request.getParameter("vungId")));	   	 
	   	 
	   	 obj.setkhuvucId(util.antiSQLInspection(request.getParameter("khuvucId"))==null? "":util.antiSQLInspection(request.getParameter("khuvucId")));	 
	   	 	   	    	
		 obj.setdvkdId(util.antiSQLInspection(request.getParameter("dvkdId"))==null? "":util.antiSQLInspection(request.getParameter("dvkdId")));		 
		
		 obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId"))==null? "":util.antiSQLInspection(request.getParameter("gsbhId")));
	 
		 
		String nextJSP = "/TraphacoHYERP/pages/Center/SSPerformanceGroup.jsp";		 
		try{
			String action=util.antiSQLInspection(request.getParameter("action"));
			if(action.equals("Taomoi")){
				response.setContentType("application/xlsm");
		        response.setHeader("Content-Disposition", "attachment; filename=GSThucHienChiTieuTT.xlsm");
		        OutputStream out = response.getOutputStream();
		        CreatePivotTable(out,obj);
			}	else{
				obj.init();	    
				session.setAttribute("obj", obj);
				session.setAttribute("userId", obj.getuserId());		
				response.sendRedirect(nextJSP);
			}
		}catch(Exception ex){
			obj.setMsg(ex.getMessage());
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
		}
	
	}
	
	private String setQuery( IStockintransit obj) {
		
		String query="";
		long numofDay = 0;
		geso.traphaco.center.db.sql.dbutils db= new geso.traphaco.center.db.sql.dbutils();
		geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
		
	
		
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
						 chuoiselct="isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk") ;  // +" ,ISNULL(CTPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS CTPRI"+rs.getString("nhomsanpham_fk")+",ISNULL(DSPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS DSPRI"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						 chuoiselct=chuoiselct+" ,isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CT"+rs.getString("nhomsanpham_fk")+",ISNULL(DS.["+rs.getString("nhomsanpham_fk")+"],0) AS DS"+rs.getString("nhomsanpham_fk") ; // +",ISNULL(CTPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS CTPRI"+rs.getString("nhomsanpham_fk")+",ISNULL(DSPRI.["+rs.getString("nhomsanpham_fk")+"],0) AS DSPRI"+rs.getString("nhomsanpham_fk");
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
		//dung file show de luu chuoi;
		 
		 obj.setFieldShow(arraychuoi);
		 
		
			
			
		 // set loai chi tieu san luong hay tie
				//loai chi tieu sanluong la 2
			 boolean sanluong=false;
			
		 
		 
		query=
		"SELECT   "+
		"	    kbh.ten as kenh,dvkd.donvikinhdoanh ,vung.ten as vung,kv.ten as khuvuc,"+
		"		ctnhom.kenh_fk,ctnhom.dvkd_fk, ctnhom.donhang  as ctdonhang,ctnhom.sellsout as ctdoanhso , ctnhom.dophu as ctdophu"+
		"		,DS1.SODONHANG as thucdatdonhang ,ISNULL( DS1.DOANHSO,0)-ISNULL(TRAVE.TIENTRAVE,0)  as thucdatdoanhso,DS1.dophu as thucdatdophu , "+
		"		'5'+cast(ctnhom.gsbh_fk as nvarchar(10)) as gsbh_fk,gsbh.ten as giamsat,songaylamviec, "+chuoiselct +
		"		FROM  "+
		"		( "+
		"		SELECT  b.gsbh_fk ,ct.kenh_fk,ct.dvkd_fk,a.donhang,a.dophu, a.chitieu as sellsout ,ct.songaylamviec, b.nhomsanpham_fk,a.chitieu ,b.chitieu as chitieunhom "+
		"		FROM chitieusec_gsbh_nhomsp b inner join chitieusec_gsbh a on  "+
		"		 a.chitieusec_fk=b.chitieusec_fk and  a.GSBH_FK=b.GSBH_FK  "+
		"		 inner join chitieu_sec ct on ct.pk_seq=b.chitieusec_fk  where ct.thang="+obj.getMonth()+" and ct.nam="+obj.getYear()+" "+ 
		"		) p "+
		"		PIVOT "+
		"		( "+
		"		 sum(chitieunhom) "+
		"		FOR nhomsanpham_fk IN "+
		"		("+chuoingoac+") "+
		"		) AS ctnhom "+
		"		inner join giamsatbanhang gsbh on gsbh.pk_seq=ctnhom.gsbh_fk "+
		" left  join "+
		" ( "+
		" select GSBH_FK,max(KHUVUC_FK) as khuvucid "+
		" from GSBH_KHUVUC group by gsbh_fk "+
		" ) a on a.GSBH_FK=gsbh.PK_SEQ "+
		" left join khuvuc kv on kv.pk_seq=a.khuvucid "+ 
		"		left join vung  on vung.pk_Seq=kv.vung_fk "+
		"		left join kenhbanhang kbh on kbh.pk_seq=ctnhom.kenh_fk "+
		"		left join donvikinhdoanh dvkd on dvkd.pk_seq=ctnhom.dvkd_fk "+

		"		left join  "+
		"		( "+
		"		select dvkd_fk, gsbh_fk,kbh_fk ,  "+ chuoingoac+
		"		from (  ";
		query=query+		"		select sp.dvkd_fk,dh.kbh_fk,gsbh_fk,nsp.nhomsp_fk, sum( soluong* dh_sp.giamua) as sanluong ";
		query=query+		"		from donhang dh inner join  "+
		"		donhang_sanpham dh_sp on dh.pk_seq=dh_sp.donhang_fk "+
		"		inner join sanpham sp on sp.pk_seq=dh_sp.sanpham_fk "+
		"		inner join ( "+
		"		select max(nsp_fk) as nhomsp_fk,sp_fk as sanpham_fk from nhomsanpham_sanpham "+
		"		where nsp_fk in ("+chuoi+") "+
		"		group by sp_fk "+
		"		) as nsp on nsp.sanpham_fk=sp.pk_seq "+
		"		where dh.trangthai=1 and dh.ngaynhap like '"+obj.getYear()+"-"+obj.getMonth()+"%' "+
		"		group by sp.dvkd_fk,dh.kbh_fk,gsbh_fk,nsp.nhomsp_fk  "+
		//THEM PHAN DON HANG TRAVE 
		" UNION ALL " +
		"  SELECT SP2.DVKD_FK AS DVKD_FK, DH.KBH_FK,  DH.GSBH_FK AS GSBH_FK , NSP.NHOMSP_FK ," ;
		if(!sanluong){
			query=query+		"(-1)*	 SUM(	ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) *   ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  " ;
		}else{
			query=query+		"(-1)*	SUM(TRONGLUONG  *   ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS DOANHSO  ";
		}
		query=query+	 "		FROM  DONHANGTRAVE DH     "+
		"		LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   "+  	
		"		LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK  "+
		"		INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK) "+
		"		INNER JOIN (   "+
		"		SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM  "+
		"		WHERE NSP_FK IN ("+chuoi+")  "+
		"		GROUP BY SP_FK   "+
		"		) AS NSP ON NSP.SANPHAM_FK=SP2.PK_SEQ  "+
		"		WHERE  DH.TRANGTHAI='3' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' "+
		"		GROUP BY SP2.DVKD_FK,DH.KBH_FK,GSBH_FK,NSP.NHOMSP_FK "+
		"				) a "+
		"				pivot "+
		"		( "+
		"		  sum( sanluong) "+
		"		FOR nhomsp_fk IN "+
		"		("+chuoingoac+" ) "+
		"			) as t "+
		"		) as ds on  "+
		"		ctnhom.kenh_fk=ds.kbh_fk and ctnhom.dvkd_fk=ds.dvkd_fk and ctnhom.gsbh_fk=ds.gsbh_fk  "+
		" LEFT JOIN " +
		"(	" +									
		"	SELECT DH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, DH.GSBH_FK AS GSBHID, " + 
		"	SUM(DH_SP.SOLUONG * DH_SP.GIAMUA) AS 'DOANHSO', COUNT(DH.PK_SEQ) AS 'SODONHANG' ,  COUNT(DH_SP.SANPHAM_FK) AS 'TONGSKU',count(distinct khachhang_fk) as 'dophu' " +	
		"	FROM DONHANG DH INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK " + 	
		"	INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = DH_SP.SANPHAM_FK	" +	
		"	WHERE SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "' AND DH.TRANGTHAI ='1' " + 	
		"	GROUP BY DH.KBH_FK, SP2.DVKD_FK, DH.GSBH_FK " +
		" ) DS1 ON ctnhom.kenh_fk = DS1.KBH_FK AND DS.DVKD_FK = ctnhom.dvkd_fk AND ctnhom.gsbh_fk = DS1.GSBHID  " +	
		//THEM PHAN DON HANG TRA VE
		"  LEFT JOIN (" +
		"	SELECT  DH.KBH_FK, SP2.DVKD_FK AS DVKD_FK, DH.GSBH_FK AS GSBHID ,  "+   	
		"		SUM(	ISNULL(DH_SP.GIAMUA, DH_SP1.GIAMUA) *    "+
		"		 ISNULL(DH_SP.SOLUONG, DH_SP1.SOLUONG) ) AS TIENTRAVE  "+    
			"		FROM  DONHANGTRAVE DH    "+
			"	LEFT OUTER JOIN  DONHANGTRAVE_SANPHAM DH_SP ON DH_SP.DONHANGTRAVE_FK = DH.PK_SEQ   "+  	
			"	LEFT OUTER JOIN  DONHANG_SANPHAM DH_SP1 ON DH.DONHANG_FK = DH_SP1.DONHANG_FK   "+
			"	INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = ISNULL(DH_SP.SANPHAM_FK, DH_SP1.SANPHAM_FK)  "+   
			"	WHERE DH.TRANGTHAI = 3   "+
			"		AND SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '" + obj.getYear() + "-" + obj.getMonth() + "'   "+ 	
			"		GROUP BY 	 DH.KBH_FK, SP2.DVKD_FK, DH.GSBH_FK  ) TRAVE" +
			" ON ctnhom.kenh_fk = TRAVE.KBH_FK AND TRAVE.DVKD_FK = ctnhom.dvkd_fk AND ctnhom.gsbh_fk = TRAVE.GSBHID   "; 

		
		query += " where  kbh.pk_seq in " + ut.quyen_kenh(obj.getuserId());
			if(obj.getkenhId().length() > 0)
				query += " and kbh.pk_seq='"+obj.getkenhId()+"'";
			if(obj.getvungId().length() > 0)
				query += " and vung.pk_seq = '"+obj.getvungId()+"'";
			if(obj.getdvkdId().length() > 0)
				query += " and dvkd.pk_seq = '"+obj.getdvkdId()+"'";
			if(obj.getkhuvucId().length() > 0)
				query += " and kv.pk_seq = '"+obj.getkhuvucId()+"'";
			if(obj.getgsbhId().length() > 0)
				query += " and gsbh.pk_seq = '"+obj.getgsbhId()+"'";
			
//			query = query + " order by kbh.ten, dvkd.donvikinhdoanh, v.ten, kv.ten, npp.ten, cast(dvkd.pk_seq as nvarchar(10) )+'_'+ gsbh.ten ";
		System.out.println("Query SS khong duoc luan chuyen  : " + query);
		
		return query;
	}

	
	private void CreatePivotTable(OutputStream out,IStockintransit obj) throws Exception
    {   
		try{
			
			String chuoi=getServletContext().getInitParameter("path") + "\\SS_ThucHienChiTieuTTGroupSku.xlsm";
		
			FileInputStream fstream = new FileInputStream(chuoi);
			Workbook workbook = new Workbook();
			workbook.open(fstream);
			workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
			CreateStaticHeader(workbook,obj);
			int row = 2;
			FillData(workbook, obj, row);
			
			workbook.save(out);
			fstream.close();
	     }catch(Exception ex){
	    	 throw new Exception(ex.getMessage());
	     }	    
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
		cell.setValue("TÌNH HÌNH THỰC HIỆN CHỈ TIÊU PTT / GĐ CN 2");

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
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9, "Nam : " + obj.getYear() + "" );
		
	    cells.setRowHeight(4, 18.0f);
	    cell = cells.getCell("A4");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ngày báo cáo : " + ReportAPI.NOW("yyyy-MM-dd"));
	    
	    cells.setRowHeight(5, 18.0f);
	    cell = cells.getCell("A5");
	    ReportAPI.getCellStyle(cell, Color.NAVY, true, 9, "Ðược tạo bởi :  " + obj.getuserTen());
	   
	
	    cell = cells.getCell(htb.get(1)+"1"); cell.setValue("KenhBanHang");
	    cell = cells.getCell(htb.get(2)+"1"); cell.setValue("DonViKinhDoanh");
	    cell = cells.getCell(htb.get(3)+"1"); cell.setValue("Mien");
	    cell = cells.getCell(htb.get(4)+"1"); cell.setValue("Vung");
	    cell = cells.getCell(htb.get(5)+"1"); cell.setValue("MAGSBH");	
	    cell = cells.getCell(htb.get(6)+"1");cell.setValue("GIAMSATBANHANG");  	    
	       	    
	    cell = cells.getCell(htb.get(7)+"1"); cell.setValue("NgayLamViec");
	   
	    cell = cells.getCell(htb.get(8)+"1"); cell.setValue("ChiTieuSoDH");	    
	    cell = cells.getCell(htb.get(9)+"1"); cell.setValue("ThucDatSoDH");
	    cell = cells.getCell(htb.get(10)+"1"); cell.setValue("PhanTramDonHang");
	    
	    cell = cells.getCell(htb.get(11)+"1"); cell.setValue("ChiTieuDoPhu");	    
	    cell = cells.getCell(htb.get(12)+"1"); cell.setValue("ThucDatDoPhu");
	    cell = cells.getCell(htb.get(13)+"1"); cell.setValue("PhanTramDoPhu");
	    
	    
	    
	    cell = cells.getCell(htb.get(14)+"1"); cell.setValue("ChiTieuSellsOut");	    
	    cell = cells.getCell(htb.get(15)+"1"); cell.setValue("ThucDatSellsOut");
	    cell = cells.getCell(htb.get(16)+"1"); cell.setValue("PhanTramDatSellsOut");
	    /*cell = cells.getCell(htb.get(29)+"1"); cell.setValue("NgayCongThucTe");*/
	    
	  
	    
	    int i=17;
	  
	    
	    
	  
	
		 
		
	    
	    
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
				
				ResultSet rs=db.get(sql);
			
				if(rs!=null){
				
				 try {
					while (rs.next()){
						 
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ChiTieuSec-"+rs.getString("ten"));	
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("ThucDatSec-"+rs.getString("ten"));
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTramSec-"+rs.getString("ten"));
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
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("TongThucDatSecNhom");
				 i=i+1;
				 cell = cells.getCell(htb.get(i)+"1"); cell.setValue("PhanTramThucDatNhom");
				
				 	
				 
				db.shutDown();	    
				
				cell = cells.getCell("M1"); 
			    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9,(26*4+i)+"");
				
	    
	}

	private void FillData(Workbook workbook,IStockintransit obj, int row)throws Exception 
	{
		Hashtable<Integer, String> htb=this.htbValueCell();
		Worksheets worksheets = workbook.getWorksheets();
	    Worksheet worksheet = worksheets.getSheet(0);
	    Cells cells = worksheet.getCells();
				
		dbutils db = new dbutils();	
		String query = setQuery( obj);
		
		System.out.println("1.Tieu Chi Thuong GSBH: " + query);
		db.update("SET DATEFORMAT ymd");
		ResultSet rs = db.get(query);	
		
		int indexRow = row;
		try 
			{				
				Cell cell = null;
				if(rs != null){
				while(rs.next())
				{ 				
					
					    cell = cells.getCell(htb.get(1) + Integer.toString(indexRow)); cell.setValue(rs.getString("KENH"));
					    cell = cells.getCell(htb.get(2) + Integer.toString(indexRow)); cell.setValue(rs.getString("DONVIKINHDOANH"));
						cell = cells.getCell(htb.get(3) + Integer.toString(indexRow)); cell.setValue(rs.getString("VUNG"));
						cell = cells.getCell(htb.get(4) + Integer.toString(indexRow)); cell.setValue(rs.getString("KHUVUC"));					
						cell = cells.getCell(htb.get(5) + Integer.toString(indexRow));cell.setValue(rs.getString("GSBH_FK"));				
						cell = cells.getCell(htb.get(6) + Integer.toString(indexRow));  cell.setValue(rs.getString("GIAMSAT"));					
						cell = cells.getCell(htb.get(7)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("SONGAYLAMVIEC"));
						
						cell = cells.getCell(htb.get(8)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("CTDONHANG")*rs.getDouble("SONGAYLAMVIEC"));
						cell = cells.getCell(htb.get(9)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("THUCDATDONHANG"));
						double PhanTramDonhang=0;
						if(rs.getDouble("CTDONHANG") >0){
							PhanTramDonhang=	100* rs.getDouble("THUCDATDONHANG")/(rs.getDouble("CTDONHANG")*rs.getDouble("SONGAYLAMVIEC"));
						}
						cell = cells.getCell(htb.get(10)+ Integer.toString(indexRow)); cell.setValue(PhanTramDonhang);
						
						
						
						cell = cells.getCell(htb.get(11)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("ctdophu"));
						cell = cells.getCell(htb.get(12)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("thucdatdophu"));
						double PhanTramDoPhu=0;
						if(rs.getDouble("ctdophu") >0){
							PhanTramDoPhu=	100* rs.getDouble("thucdatdophu")/(rs.getDouble("ctdophu"));
						}
						cell = cells.getCell(htb.get(13)+ Integer.toString(indexRow)); cell.setValue(PhanTramDoPhu);
			
						cell = cells.getCell(htb.get(14)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("ctdoanhso"));
						cell = cells.getCell(htb.get(15)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("thucdatdoanhso"));
						double PhanTramDoanhSo=0;
						if(rs.getDouble("ctdoanhso") >0){
							PhanTramDoanhSo=	100* rs.getDouble("thucdatdoanhso")/(rs.getDouble("ctdoanhso"));
						}
						cell = cells.getCell(htb.get(16)+ Integer.toString(indexRow)); cell.setValue(PhanTramDoanhSo);
						/*cell = cells.getCell(htb.get(29)+ Integer.toString(indexRow)); cell.setValue(rs.getString("ngaycongthucte"));*/

						String []chuoi =obj.getFieldShow();
						int j=17;
						double SumChiTieuDDKD=0;
						double SumThucDatCTDDKD=0;
						for (int i=0;i<chuoi.length ;i++){
							if(chuoi[i]!=null){
								cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(rs.getDouble("CT"+chuoi[i]));					
								SumChiTieuDDKD= SumChiTieuDDKD+rs.getDouble("CT"+chuoi[i]);
								SumThucDatCTDDKD= SumThucDatCTDDKD+rs.getDouble("DS"+chuoi[i]);
								j=j+1;
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getDouble("DS"+chuoi[i]));
								j=j+1;
								double phantramMTD =0;
								
								if(rs.getDouble("CT"+chuoi[i]) >0){
									phantramMTD=rs.getDouble("DS"+chuoi[i])*100/rs.getDouble("CT"+chuoi[i]);
								}
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
								j=j+1;
							}
						}
						
						cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(SumChiTieuDDKD);					
						
						j=j+1;
						cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumThucDatCTDDKD);
						j=j+1;
						double SumphantramMTD =0;
						
						if(SumChiTieuDDKD >0){
							SumphantramMTD=SumThucDatCTDDKD*100/SumChiTieuDDKD;
						}
						cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(SumphantramMTD);
						
						j=j+1;
						
						
						
						indexRow++;
					
				}
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
