package geso.traphaco.distributor.servlets.reports;

import geso.traphaco.center.beans.stockintransit.IStockintransit;
import geso.traphaco.center.beans.stockintransit.imp.Stockintransit;
import geso.traphaco.center.servlets.report.ReportAPI;
import geso.traphaco.distributor.db.sql.dbutils;
import geso.traphaco.distributor.util.Utility;

import java.util.Hashtable;
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

import com.aspose.cells.Style;
import com.aspose.cells.Workbook;
import com.aspose.cells.Worksheet;
import com.aspose.cells.Worksheets;

public class Srperfomance extends HttpServlet
{

	private static final long serialVersionUID = 1L;

	public Srperfomance()
	{
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");
		Utility Ult = new Utility();
		HttpSession session;
		session = request.getSession();

		String querystring = request.getQueryString();
		String userId = Ult.getUserId(querystring);
		session.setAttribute("loi", "");
		IStockintransit obj = new Stockintransit();
		obj.setuserId(userId);
		String nppId = Ult.getIdNhapp(userId);
		String nppTen = Ult.getTenNhaPP();

		obj.setnppId(nppId);
		obj.setnppTen(nppTen);
		obj.init();

		session.setAttribute("obj", obj);

		String nextJSP = "/TraphacoHYERP/pages/Distributor/SRperformace.jsp";
		response.sendRedirect(nextJSP);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
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
		
		 obj.setDdkd(util.antiSQLInspection(request.getParameter("ddkdId"))==null? "":util.antiSQLInspection(request.getParameter("ddkdId")));
		 
		 obj.setgsbhId(util.antiSQLInspection(request.getParameter("gsbhId"))==null? "":util.antiSQLInspection(request.getParameter("gsbhId")));
		 
		 
		 String []fieldsHien = request.getParameterValues("fieldsHien");
		 obj.setFieldShow(fieldsHien);		 
		String nextJSP = "/TraphacoHYERP/pages/Distributor/SRperformace.jsp";
		String action=util.antiSQLInspection(request.getParameter("action"));
		if(action.equals("Taomoi")){
			try{
			response.setContentType("application/xlsm");
	        response.setHeader("Content-Disposition", "attachment; filename=ThucHienChiTieuSR_"+util.setTieuDe(obj)+".xlsm");
	        OutputStream out = response.getOutputStream();
	        String query = setQuery(obj);
	        CreatePivotTable(out,obj,query);
			}catch(Exception ex){
				ex.printStackTrace();
				obj.setMsg(ex.getMessage());
			}
		}else{
			obj.init();	    
			session.setAttribute("obj", obj);
			session.setAttribute("userId", obj.getuserId());		
			response.sendRedirect(nextJSP);
		}
	
	
}
	
private String setQuery( IStockintransit obj) {
		
		
		String query="";

		geso.traphaco.center.db.sql.dbutils db=new geso.traphaco.center.db.sql.dbutils();
		 String sql=
		" select pk_seq  as nhomsanpham_fk " +
 		" from nhomfocus a where a.NAM="+ obj.getYear()+" and THANG="+obj.getMonth()+"" +
 		" and DOITUONG=0  and isnull( ISNHOMTHUONGBanRa,'0') ='0' and trangthai=1  ";
		 
		 if(obj.getdvkdId().length()>0)
		 {
			 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
		 }
		 if(obj.getkenhId().length()>0)
		 {
			 sql=sql+ " and a.kbh_fk= "+ obj.getkenhId();
		 }
		 if(obj.getkhuvucId().length()>0)
			 sql+=" and a.pk_seq in (select nhomfocus_fk from nhomfocus_vung where vung_fk='"+obj.getkhuvucId()+"'  )";
		 
		 System.out.println("[NhomFocus]"+sql);
		 
		 ResultSet rs=db.get(sql);
		 String chuoi="0";
		 String[] arraychuoi= new String[50];
		 String chuoiselct="0";
		 String chuoingoac="[0]";
		int i=0;
			 try {
				while (rs.next()){
					
					 if(i==0){
						 chuoingoac="["+rs.getString("nhomsanpham_fk")+"]";
						 chuoi=rs.getString("nhomsanpham_fk");
						 chuoiselct="isnull(NHOMFC.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMFC"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoi=chuoi+","+rs.getString("nhomsanpham_fk");
						 chuoiselct=chuoiselct+ " ,isnull(NHOMFC.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMFC"+rs.getString("nhomsanpham_fk");
						 chuoingoac=chuoingoac+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoi[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
				
			  sql="select NV.PK_SEQ,UPPER(NV.TIEUCHI) AS TIEUCHI from NHIEMVUNHANVIEN A  "+
			  " inner join NHIEMVUNHANVIEN_CHITIET B ON A.PK_SEQ=B.NHIEMVUNHANVIEN_FK "+
			  " INNER JOIN NHIEMVU NV ON NV.PK_SEQ=B.NHIEMVU_FK "+
			  " WHERE A.THANG="+obj.getMonth()+" AND A.NAM="+obj.getYear()+" AND A.DOITUONG='SR'";
		  
			  	 if(obj.getdvkdId().length()>0){
					 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
				 }
				 if(obj.getkenhId().length()>0){
					 sql=sql+ " and a.KBH_FK = "+ obj.getkenhId();				 
				 }
				 
				 System.out.println("[NHIEMVUNHANVIEN_CHITIET]"+sql);
				 
				 rs=db.get(sql);
				 String chuoinv="0";
				 String[] arraychuoinv= new String[10];
				 String chuoiselctnv="0";
				 String chuoingoacnv="[0]";//co dau []
			 
				  i=0;
				 try {
					while (rs.next()){
						
						 if(i==0){
							 chuoingoacnv="[0],["+rs.getString("PK_SEQ")+"]";
							 chuoinv=rs.getString("PK_SEQ");
							 chuoiselctnv=" isnull(NHIEMVU.["+rs.getString("PK_SEQ")+"],0) AS NHIEMVU"+rs.getString("PK_SEQ");
						 }else{
							 chuoingoacnv=chuoingoacnv+",["+rs.getString("PK_SEQ")+"]";
							 chuoinv=chuoinv+","+rs.getString("PK_SEQ");
							 chuoiselctnv= chuoiselctnv + ",isnull(NHIEMVU.["+rs.getString("PK_SEQ")+"],0) AS NHIEMVU"+rs.getString("PK_SEQ");

						 }
						 arraychuoinv[i]=rs.getString("PK_SEQ");
						 i++;
					 }
				} catch (Exception e) 
				{
				
					e.printStackTrace();
				}
				  sql="select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
					" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
					" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
					" where    nsp.PK_SEQ<200000 and  b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
		 
				 if(obj.getdvkdId().length()>0){
					 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
					 
				 }
				 if(obj.getkenhId().length()>0){
					 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
					 
				 }
				 
				 System.out.println("[chitieunpp_ddkd_nhomsp]"+sql);
		 
		  rs=db.get(sql);
		 String chuoiskuout="0";
		 String[] arraychuoiskuout= new String[10];
		 String chuoiselctskuout="0";
		 String chuoingoacskuout="[0]";//co dau []
		 
		 if(rs!=null){
			  i=0;
			 try {
				while (rs.next()){
					
					 if(i==0){
						 chuoingoacskuout="[0],["+rs.getString("nhomsanpham_fk")+"]";
						 chuoiskuout=rs.getString("nhomsanpham_fk");
						 chuoiselctskuout=" ,isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CTNHOM"+rs.getString("nhomsanpham_fk")+",ISNULL(DS1.["+rs.getString("nhomsanpham_fk")+"],0) AS DS1"+rs.getString("nhomsanpham_fk");
					 }else{
						 chuoiskuout=chuoiskuout+","+rs.getString("nhomsanpham_fk");
						 chuoiselctskuout=chuoiselctskuout+", isnull(CTNHOM.["+rs.getString("nhomsanpham_fk")+"],0) AS CTNHOM"+rs.getString("nhomsanpham_fk")+",ISNULL(DS1.["+rs.getString("nhomsanpham_fk")+"],0) AS DS1"+rs.getString("nhomsanpham_fk");
						 chuoingoacskuout=chuoingoacskuout+",["+rs.getString("nhomsanpham_fk")+"]";
					 }
					 arraychuoiskuout[i]=rs.getString("nhomsanpham_fk");
					 i++;
					 
				 }
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
		 }
		obj.setFieldShow(arraychuoi);
		obj.setFieldHidden(arraychuoinv);
		
		// NHÓM THƯỞNG BanRa ISNHOMTHUONGBanRa=1
		 sql=" select pk_seq  as nhomsanpham_fk, dbo.ftBoDau(a.DIENGIAI) as ten ,isnull(ISNHOMTHUONGBanRa,'0') as ISNHOMTHUONGBanRa  ,tienthuong  " +
			" from nhomfocus a where a.NAM="+ obj.getYear()+" and THANG="+obj.getMonth()+" and DOITUONG=0 AND ISNHOMTHUONGBanRa=1 and trangthai=1 ";
		 
			 if(obj.getdvkdId().length()>0)
			 {
				 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
			 }
			 if(obj.getkenhId().length()>0)
			 {
				 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
			 }
			 if(obj.getkhuvucId().length()>0)
				 sql+=" and a.pk_seq in (select nhomfocus_fk from nhomfocus_vung where vung_fk='"+obj.getkhuvucId()+"'  )";
			 
			 System.out.println("[ISNHOMTHUONGBanRa]"+sql);
		
			 String chuoithuongBanRa="0";
			 String[] arraythuongBanRa= new String[10];
			 String chuoiselctthuongBanRa="";
			 String chuoingoacthuongBanRa="[0]";//co dau []
			 rs=db.get(sql);
			 try {
					while (rs.next()){
						
						 if(i==0){
							 chuoingoacthuongBanRa="[0],["+rs.getString("nhomsanpham_fk")+"]";
							 chuoithuongBanRa=rs.getString("nhomsanpham_fk");
							 chuoiselctthuongBanRa=" ,isnull(NHOMBanRa.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMBanRa"+rs.getString("nhomsanpham_fk")+",ISNULL(DSTHUONGBanRa.["+rs.getString("nhomsanpham_fk")+"],0) AS DSTHUONGBanRa"+rs.getString("nhomsanpham_fk");
						 }else{
							 chuoithuongBanRa=chuoithuongBanRa+","+rs.getString("nhomsanpham_fk");
							 chuoiselctthuongBanRa=chuoiselctthuongBanRa+", isnull(NHOMBanRa.["+rs.getString("nhomsanpham_fk")+"],0) AS NHOMBanRa"+rs.getString("nhomsanpham_fk")+",ISNULL(DSTHUONGBanRa.["+rs.getString("nhomsanpham_fk")+"],0) AS DSTHUONGBanRa"+rs.getString("nhomsanpham_fk");
							 chuoingoacthuongBanRa=chuoingoacthuongBanRa+",["+rs.getString("nhomsanpham_fk")+"]";
						 }
						 arraythuongBanRa[i]=rs.getString("nhomsanpham_fk");
						 i++;
						 
					 }
				} catch (Exception e) 
				{
					e.printStackTrace();
				}
			 
				geso.traphaco.center.util.Utility ut = new geso.traphaco.center.util.Utility();
			 
		 sql=
		  " SELECT VUNG.TEN AS VUNG,KV.TEN AS KHUVUC,DDKD.manhanvien AS DDKDID, DDKD.TEN AS DAIDIENKINHDOANH,SONGAYLAMVIEC,KBH.TEN AS KENH,DVKD.DONVIKINHDOANH   \n" +   
		  "  ,VUNG.TEN AS VUNG,KV.TEN AS KHUVUC,NPP.TEN AS NHAPHANPHOI, CT.CHITIEU,CT.DDKD_FK,CT.DVKD_FK   \n" +   
		  "  ,CT.KENH_FK,CT.NPP_FK,CT.SONGAYLAMVIEC ,CT.CHITIEU  as CTDOANHSO ,ISNULL(DS.DOANHSO ,0) AS DOANHSO , \n" +   
		  "  " +chuoiselctnv +    
		  "  ," +chuoiselct + 
		  " , "+chuoiselctskuout+ 
		  "  "+chuoiselctthuongBanRa;
		  
		  if(obj.getMonth().equals("12") &&obj.getYear().equals("2013"))
			  sql+=" ,tdBanRa.tdBanRa ";
		  sql+=
		  "  FROM ( SELECT CTNPP.NHAPP_FK AS NPP_FK,CTNPP.KENH_FK,CTNPP.DVKD_FK, A.DDKD_FK     \n" +   
		  "  ,CTNPP.SONGAYLAMVIEC, A.CHITIEU  \n" +   
		  "  FROM CHITIEUNPP_DDKD A   \n" +   
		  "  INNER JOIN CHITIEUNPP CTNPP ON CTNPP.PK_SEQ=A.CHITIEUNPP_FK      \n" +   
		  "  WHERE CTNPP.THANG="+obj.getMonth()+" AND CTNPP.NAM="+obj.getYear()+" and trangthai<>'2'   \n" +   
		  "  ) AS CT  \n" +   
		  "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=CT.NPP_FK   \n" +   
		  "  INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK   \n" +   
		  "  INNER JOIN VUNG  ON VUNG.PK_SEQ=KV.VUNG_FK   \n" +   
		  "  LEFT JOIN KENHBANHANG KBH ON KBH.PK_SEQ=CT.KENH_FK   \n" +   
		  "  LEFT JOIN DONVIKINHDOANH DVKD ON DVKD.PK_SEQ=CT.DVKD_FK   \n" +   
		  "  INNER JOIN DAIDIENKINHDOANH DDKD ON DDKD.PK_SEQ=CT.DDKD_FK  \n" +   
		  "  LEFT JOIN   \n" +   
		  "  (   \n" +   
		  "    \n" +   
		  "  SELECT DH.NPP_FK, DH.DDKD_FK, DH.KBH_FK,SP2.DVKD_FK,SUM(DH_SP.SOLUONG * DH_SP.GIAMUA/1.1*"+ut.ChietKhau(obj.getYear())+")  AS DOANHSO  \n" +   
		  "  FROM DONHANG DH INNER JOIN DONHANG_SANPHAM DH_SP ON DH.PK_SEQ = DH_SP.DONHANG_FK   \n" +   
		  "  INNER JOIN SANPHAM SP2 ON SP2.PK_SEQ = DH_SP.SANPHAM_FK   \n" +   
		  "  WHERE SUBSTRING(DH.NGAYNHAP, 1 , 7)  = '"+obj.getYear()+"-"+obj.getMonth()+"' AND DH.TRANGTHAI ='1'   \n" +   
		  "  GROUP BY DH.DDKD_FK, DH.NPP_FK,SP2.DVKD_FK, DH.KBH_FK    \n" +   
		  "    \n" +   
		  "  ) AS DS ON DS.DDKD_FK=CT.DDKD_FK AND DS.DVKD_FK=CT.DVKD_FK AND CT.KENH_FK=DS.KBH_FK AND CT.NPP_FK=DS.NPP_FK  \n" +   
		  "    \n" +   
		  "  LEFT JOIN   \n" +   
		  "  (  \n" +   
		  "  select  NHIEMVU.KBH_FK,NHIEMVU.DVKD_FK,NHIEMVU.NHANVIEN_FK ,"+chuoingoacnv+" from   \n" +   
		  "  (  \n" +   
		  "  select NVNV.DVKD_FK,NVNV.KBH_FK,thnv.NHANVIEN_FK,NHIEMVU_FK, CAST(thnv.DAT AS INT)  AS DAT from THUCHIENNHIEMVUTHANG thnv   \n" +   
		  "  inner join NHIEMVUNHANVIEN NVNV on thnv.NHIEMVUNHANVIEN_FK=NVNV.PK_SEQ   \n" +   
		  "  where NVNV.DOITUONG='SR' and NVNV.THANG="+obj.getMonth()+" and NVNV.NAM="+obj.getYear()+"  \n" +   
		  "  ) P  \n" +   
		  "  PIVOT   \n" +   
		  "  (   \n" +   
		  "  SUM(DAT)   \n" +   
		  "  FOR NHIEMVU_FK IN   \n" +   
		  "  ("+chuoingoacnv+" )   \n" +   
		  "  ) AS NHIEMVU   \n" +   
		  "  ) AS  NHIEMVU ON NHIEMVU.DVKD_FK=CT.DVKD_FK AND NHIEMVU.KBH_FK=CT.KENH_FK AND CT.DDKD_FK=NHIEMVU.NHANVIEN_FK  \n" +   
		  "  LEFT JOIN   \n" +   
		  "  (  \n" +   
		  "  SELECT DH.NPP_FK,SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP_SP.NHOMFOCUS_FK   \n" +   
		  "  , CAST( SUM  ( SOLUONG /( QC.SOLUONG1/QC.SOLUONG2)  * isnull(QUYDOITHUNGTHUONG,1)) AS INT )   AS THUNG  \n" +   
		  "  FROM DONHANG DH INNER JOIN    \n" +   
		  "  DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK   \n" +   
		  "  INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK       \n" +   
		  "  INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ    \n" +   
		  "  AND SP.DVDL_FK=QC.DVDL1_FK AND DVDL2_FK=100018    \n" +   
		  "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK  \n" +   
		  "  INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK   \n" +   
		  "  INNER JOIN nhomfocus_sp NSP_SP ON DH_SP.SANPHAM_FK=NSP_SP.SANPHAM_FK  \n" +   
		  "  AND NSP_SP.NHOMFOCUS_FK IN ("+chuoi+")   \n" +   
		  "    " +   
		  "  WHERE  DH.TRANGTHAI='1' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%'  \n" +   
		  "  AND NSP_SP.NHOMFOCUS_FK IN (   \n" +   
		  "  	SELECT NSP_V.NHOMFOCUS_FK FROM nhomfocus_vung NSP_V WHERE  NSP_V.NHOMFOCUS_FK  IN ("+chuoi+") AND NSP_V.Vung_fk=KV.PK_SEQ \n" +   
		  "   ) AND DH.PK_SEQ    \n" +   
		  "   NOT IN (   \n" +   
		  "  	SELECT DHT.DONHANG_FK FROM DONHANGTRAVE DHT   \n" +   
		  "  	WHERE DHT.TRANGTHAI=3 AND DHT.NGAYDUYET  LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%' and  DHT.DONHANG_FK is not null)  \n" +   
		  "  	GROUP BY DH.NPP_FK,DH.KBH_FK,DDKD_FK,NSP_SP.NHOMFOCUS_FK ,SP.DVKD_FK \n" +
		  "  ) P   \n" +   
		  "  PIVOT   \n" +   
		  "  (   \n" +   
		  "  SUM(THUNG)   \n" +   
		  "  FOR NHOMFOCUS_FK  IN   \n" +   
		  "  ("+chuoingoac+" )   \n" +   
		  "  ) AS NHOMFC ON NHOMFC.DDKD_FK=CT.DDKD_FK AND NHOMFC.DVKD_FK=CT.DVKD_FK  \n" +
		  "  AND CT.KENH_FK=NHOMFC.KBH_FK AND CT.NPP_FK=NHOMFC.NPP_FK   \n" +
		  	"  left join ( \n" +
			"  SELECT CTNPP.NHAPP_FK AS NPP_FK,CTNPP.PK_SEQ,CTNPP.KENH_FK as KBH_FK  ,CTNPP.DVKD_FK, B.DDKD_FK  \n" +
			" , B.CHITIEU AS CHITIEUNHOM ,B.NHOMSANPHAM_FK \n" +
			" FROM CHITIEUNPP_DDKD_NHOMSP B INNER JOIN CHITIEUNPP_DDKD A ON \n" +
			" A.CHITIEUNPP_FK=B.CHITIEUNPP_FK AND A.DDKD_FK=B.DDKD_FK "+
			" INNER JOIN CHITIEUNPP CTNPP ON CTNPP.PK_SEQ=B.CHITIEUNPP_FK   " +
			" WHERE CTNPP.THANG="+obj.getMonth()+" AND CTNPP.NAM="+obj.getYear()+" and trangthai<>'2'   AND B.NHOMSANPHAM_FK < 200000) P \n" +
			" PIVOT \n" +
			" ( \n" +
			"  SUM(CHITIEUNHOM) \n" +
			"  FOR NHOMSANPHAM_FK IN \n" +
			" ( "+chuoingoacskuout+" ) \n" +
			" ) AS CTNHOM  ON CTNHOM.DDKD_FK=CT.DDKD_FK AND CTNHOM.DVKD_FK=CT.DVKD_FK  \n" +
			"  AND CT.KENH_FK=CTNHOM.KBH_FK AND CT.NPP_FK=CTNHOM.NPP_FK   \n" +
			//
			" LEFT JOIN  \n" +
			" ( \n" +
			" SELECT DVKD_FK, DDKD_FK,KBH_FK , "+chuoingoacskuout +
			" FROM (  \n"+
			" SELECT SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK, SUM(SOLUONG /  CAST (QC.SOLUONG1/QC.SOLUONG2 AS INT) * isnull(QUYDOITHUNGTHUONG,1)) AS SANLUONG \n"+
			" FROM DONHANG DH INNER JOIN  \n"+
			" DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK \n"+
			" INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK  \n"+
			  "  INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ    \n"+   
			  "  AND SP.DVDL_FK=QC.DVDL1_FK AND DVDL2_FK=100018    \n"+   
			" INNER JOIN (  \n"+
			" SELECT MAX(NSP_FK) AS NHOMSP_FK,SP_FK AS SANPHAM_FK FROM NHOMSANPHAM_SANPHAM  \n"+
			" WHERE NSP_FK IN ("+chuoiskuout+")  \n"+
			" GROUP BY SP_FK  \n"+
			" ) AS NSP ON NSP.SANPHAM_FK=SP.PK_SEQ  \n"+
			" WHERE  DH.TRANGTHAI='1' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%'  \n"+
			" GROUP BY SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP.NHOMSP_FK   \n"+
			" ) A   \n"+
			" PIVOT  \n"+
			" (   \n"+
			" SUM( SANLUONG)  \n"+
			" FOR NHOMSP_FK IN  \n"+
			" ( "+chuoingoacskuout+" )  \n"+
			" ) AS T   \n"+
			" ) AS DS1 ON    \n"+
			" CT.KENH_FK=DS1.KBH_FK AND CT.DVKD_FK=DS1.DVKD_FK AND CT.DDKD_FK=DS1.DDKD_FK  \n"+ 
			// chi tieu nhom thuong BanRa
			 "  LEFT JOIN   \n"+   
			  "  (   \n"+   
			 
			  "  SELECT DH.NPP_FK,SP.DVKD_FK,DH.KBH_FK,DDKD_FK,NSP_SP.NHOMFOCUS_FK    \n"+   
			  "  , CAST( SUM  ( SOLUONG /( QC.SOLUONG1/QC.SOLUONG2)  * isnull(QUYDOITHUNGTHUONG,1)) AS INT )   AS THUNG  \n"+   
			  "  FROM DONHANG DH INNER JOIN    \n"+   
			  "  DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK   \n"+   
			  "  INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK       \n"+   
			  "  INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ    \n"+   
			  "  AND SP.DVDL_FK=QC.DVDL1_FK AND DVDL2_FK=100018    \n"+   
			  "  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK  \n"+   
			  "  INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK    \n"+   
			  "  INNER JOIN nhomfocus_sp NSP_SP ON DH_SP.SANPHAM_FK=NSP_SP.SANPHAM_FK  \n"+   
			  "  AND NSP_SP.NHOMFOCUS_FK IN ("+chuoithuongBanRa+")   \n"+   
			  "    " +   
			  "  WHERE  DH.TRANGTHAI='1' AND DH.NGAYNHAP LIKE '"+obj.getYear()+"-"+obj.getMonth()+"%'  \n"+   
			  "  AND NSP_SP.NHOMFOCUS_FK IN (   \n"+   
			  "  	SELECT NSP_V.NHOMFOCUS_FK FROM nhomfocus_vung NSP_V WHERE  NSP_V.NHOMFOCUS_FK  IN ("+chuoithuongBanRa+") AND NSP_V.Vung_fk=KV.PK_SEQ  \n"+   
			  "   )  	GROUP BY DH.NPP_FK,DH.KBH_FK,DDKD_FK,NSP_SP.NHOMFOCUS_FK ,SP.DVKD_FK   \n"+
			  "  ) P  \n"+   
			  "  PIVOT   \n"+   
			  "  (   \n"+   
			  "  SUM(THUNG)   \n"+   
			  "  FOR NHOMFOCUS_FK  IN   \n"+   
			  "  ("+chuoingoacthuongBanRa+" )   \n"+   
			  "  ) AS DSTHUONGBanRa ON DSTHUONGBanRa.DDKD_FK=CT.DDKD_FK AND DSTHUONGBanRa.DVKD_FK=CT.DVKD_FK  \n"+
			  "  AND CT.KENH_FK=DSTHUONGBanRa.KBH_FK AND CT.NPP_FK=DSTHUONGBanRa.NPP_FK   \n"+
			  // CHI TIEU NHOM THUONG BanRa
			 	"  left join ( \n"+
				" SELECT CTNPP.NHAPP_FK AS NPP_FK,CTNPP.PK_SEQ,CTNPP.KENH_FK as KBH_FK  ,CTNPP.DVKD_FK, B.DDKD_FK  \n"+
				" ,B.CHITIEU AS CHITIEUNHOM ,B.NHOMSANPHAM_FK \n"+
				" FROM CHITIEUNPP_DDKD_NHOMSP B INNER JOIN CHITIEUNPP_DDKD A ON \n"+
				" A.CHITIEUNPP_FK=B.CHITIEUNPP_FK AND A.DDKD_FK=B.DDKD_FK \n"+
				" INNER JOIN CHITIEUNPP CTNPP ON CTNPP.PK_SEQ=B.CHITIEUNPP_FK   \n"+
				" WHERE CTNPP.THANG="+obj.getMonth()+" AND CTNPP.NAM="+obj.getYear()+" and trangthai<>'2'  AND B.NHOMSANPHAM_FK >=200000) P \n"+
				" PIVOT \n"+
				" (  \n"+
				"  SUM(CHITIEUNHOM)  \n"+
				"  FOR NHOMSANPHAM_FK IN \n"+
				" ( "+chuoingoacthuongBanRa+" ) \n"+
				" ) AS NHOMBanRa  ON NHOMBanRa.DDKD_FK=CT.DDKD_FK AND NHOMBanRa.DVKD_FK=CT.DVKD_FK  " +
				"  AND CT.KENH_FK=NHOMBanRa.KBH_FK AND CT.NPP_FK=NHOMBanRa.NPP_FK   " +
				
				/*********************Thuong thang 12******************/
				
				" LEFT JOIN    (  SELECT DH.NPP_FK,SP.DVKD_FK,DH.KBH_FK,DDKD_FK   \n"+   
				"  , CAST( SUM  ( SOLUONG /( QC.SOLUONG1/QC.SOLUONG2)  * isnull(QUYDOITHUNGTHUONG,1)) AS INT )   AS tdBanRa  \n"+   
				"  FROM DONHANG DH INNER JOIN    \n"+   
				"  DONHANG_SANPHAM DH_SP ON DH.PK_SEQ=DH_SP.DONHANG_FK   \n"+   
				"  INNER JOIN SANPHAM SP ON SP.PK_SEQ=DH_SP.SANPHAM_FK       \n"+   
				"  INNER JOIN QUYCACH QC ON QC.SANPHAM_FK=SP.PK_SEQ    \n"+   
				"  AND SP.DVDL_FK=QC.DVDL1_FK AND DVDL2_FK=100018    \n"+   
				"  INNER JOIN NHAPHANPHOI NPP ON NPP.PK_SEQ=DH.NPP_FK  \n"+   
				"  INNER JOIN KHUVUC KV ON KV.PK_SEQ=NPP.KHUVUC_FK    \n"+   
				"  INNER JOIN nhomfocus_sp NSP_SP ON DH_SP.SANPHAM_FK=NSP_SP.SANPHAM_FK  \n"+   
				"  AND NSP_SP.NHOMFOCUS_FK IN (select pk_seq from nhomfocus where thang="+obj.getMonth()+" and nam="+obj.getYear()+"  and doituong=0 and isnhomthuongBanRa=1 and thuongrieng=1 and trangthai=1 )  WHERE DH.TRANGTHAI=1 AND DH.NGAYNHAP>='2013-11-28' AND DH.NGAYNHAP<='2013-12-31' " +
				"   GROUP BY DH.NPP_FK,DH.KBH_FK,DDKD_FK ,SP.DVKD_FK            		" +
				"    )AS tdBanRa ON  tdBanRa.DVKD_FK=CT.DVKD_FK AND tdBanRa.KBH_FK=CT.KENH_FK AND tdBanRa.DDKD_FK=CT.DDKD_FK " +
				"      AND tdBanRa.NPP_FK=CT.NPP_FK      ";   
			
		
		
			query += " where   1=1 ";
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
			query=query+ " order by vung.ten,kv.ten,npp.ten ";
				sql=sql+ query;
		System.out.println("1.Query SalesRep : " + sql);
		
		return sql;
	}

			private void CreatePivotTable(OutputStream out,IStockintransit obj,String query) throws Exception
		    {   
				try{
					String chuoi=getServletContext().getInitParameter("path") + "\\SrPerformanceGroupSku.xlsm";
					FileInputStream fstream = new FileInputStream(chuoi);
					Workbook workbook = new Workbook();
					workbook.open(fstream);
					workbook.setFileFormatType(FileFormatType.EXCEL2007XLSM);
					
					CreateStaticHeader(workbook,obj); 
					FillData(workbook,obj.getFieldShow(), query, obj); 
					workbook.save(out);
					fstream.close();
			     }catch(Exception ex){
			    	 ex.printStackTrace();
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
				htb.put(41,"EO");htb.put(42,"EP");htb.put(43,"EQ");htb.put(44,"ER");htb.put(45,"ES");	
				htb.put(46,"ET");htb.put(47,"EU");htb.put(48,"EV");htb.put(49,"EW");htb.put(50,"EX");	
				htb.put(51,"EY");htb.put(52,"EZ");htb.put(53,"FA");htb.put(54,"FB");htb.put(55,"FC");	
				htb.put(56,"FD");htb.put(57,"FE");htb.put(58,"FF");htb.put(59,"FG");htb.put(60,"FH");
			
				
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
		Cell cell = cells.getCell("N1");
		style = cell.getStyle();

 
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
	    

	    
	    dbutils db=new dbutils();
		String  sql="select NV.PK_SEQ,UPPER(NV.TIEUCHI ) AS TIEUCHI,THUONG from NHIEMVUNHANVIEN A  "+
		  " inner join NHIEMVUNHANVIEN_CHITIET B ON A.PK_SEQ=B.NHIEMVUNHANVIEN_FK "+
		  " INNER JOIN NHIEMVU NV ON NV.PK_SEQ=B.NHIEMVU_FK "+
		  " WHERE A.THANG="+obj.getMonth()+" AND A.NAM="+obj.getYear()+" AND A.DOITUONG='SR'";
		  int i=8;
		  if(obj.getdvkdId().length()>0)
		  {
				 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
		  }
		 if(obj.getkenhId().length()>0)
		 {
			 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
		 }
					// nhiem vu phan thuong
		 ResultSet 	 rs_nhiemvu=db.getScrol(sql);
		 try {
			 	while (rs_nhiemvu.next())
			 	{
					cell = cells.getCell(htb.get(i)+"1"); cell.setValue(rs_nhiemvu.getDouble("THUONG"));
					cell.setStyle(style);
					cell = cells.getCell(htb.get(i)+"2"); cell.setValue(rs_nhiemvu.getString("TIEUCHI"));
					cell.setStyle(style);
					i=i+1;
			 	}
			} catch (Exception e) 
			{
				e.printStackTrace();
			}
						
		cell = cells.getCell(htb.get(i)+"2"); cell.setValue("CHITIEU");
		cell.setStyle(style);
		 
		i=i+1;
		cell = cells.getCell(htb.get(i)+"2"); cell.setValue("THUCHIEN");
		cell.setStyle(style);
		i=i+1;
		cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PHANTRAM");
		cell.setStyle(style);
		i=i+1;
		cell = cells.getCell(htb.get(i)+"2"); cell.setValue("THUONGDOANHSO(2)");
		cell.setStyle(style);
		i=i+1;
		
			// Nhóm thưởng
			 sql=" select pk_seq  as nhomsanpham_fk ,a.DIENGIAI  as ten,tienthuong  " +
			" from nhomfocus a where a.NAM="+ obj.getYear()+" and THANG="+obj.getMonth()+" and DOITUONG=0 and isnull(ISNHOMTHUONGBanRa,'0')='0' and trangthai=1 ";
		 
			 if(obj.getdvkdId().length()>0)
			 {
				 sql=sql+ " and a.dvkd_fk= "+ obj.getdvkdId();
			 }
			 if(obj.getkenhId().length()>0)
			 {
				 sql=sql+ " and a.KBH_FK= "+ obj.getkenhId();
			 }
			 if(obj.getkhuvucId().length()>0)
				 sql+=" and a.pk_seq in (select nhomfocus_fk from nhomfocus_vung where vung_fk='"+obj.getkhuvucId()+"'  )";
			 System.out.println(sql);
			 ResultSet rs=db.get(sql);
											
			 try 
			 {
				 while (rs.next())
				 {
					 cell = cells.getCell(htb.get(i)+"1"); cell.setValue(rs.getDouble("tienthuong"));
					 cell.setStyle(style);
					 cell = cells.getCell(htb.get(i)+"2"); cell.setValue(rs.getString("ten")+"(Thùng)");
					 cell.setStyle(style);
					 i=i+1;
				 }
				 rs.close();
			 } catch (Exception e) 
			 {
				 e.printStackTrace();
			 }
						
						//nhóm chỉ tiêu
				try 
				{
				rs_nhiemvu.beforeFirst();
					while (rs_nhiemvu.next())
					{
						 cell = cells.getCell(htb.get(i)+"2"); 
						 cell.setValue(rs_nhiemvu.getString("TIEUCHI"));
						 cell.setStyle(style);
						 i=i+1;
					 }
				} catch (Exception e) 
				{
					e.printStackTrace();
				}							
			   sql= " select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
					" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
					" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
					" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
							    
				if(obj.getdvkdId().length()>0)
				{
				 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
				}
				if(obj.getkenhId().length()>0)
				{
				 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
				 
				}
				rs=db.get(sql);		
				if(rs!=null)
				{
										
					   try 
					   {
						while (rs.next())
						{
							 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ChiTieu-"+rs.getString("ten"));	
							 i=i+1;
							 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThucDat-"+rs.getString("ten"));
							 i=i+1;
							 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PhanTram-"+rs.getString("ten"));
							 i=i+1;
						 }
						rs.close();
						} catch (Exception e) 
						{
				
							e.printStackTrace();
						}
				}
				// NHÓM THƯỞNG BanRa ISNHOMTHUONGBanRa=1
				/* sql=" select pk_seq  as nhomsanpham_fk, dbo.ftBoDau(a.DIENGIAI) as ten ,isnull(ISNHOMTHUONGBanRa,'0') as ISNHOMTHUONGBanRa  as ten,tienthuong  " +
					" from nhomfocus a where a.NAM="+ obj.getYear()+" and THANG="+obj.getMonth()+" and DOITUONG=0 AND ISNHOMTHUONGBanRa=1 ";
				 */
				
				  sql= " select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.DIENGIAI) as ten,nsp.TIENTHUONG  from  chitieunpp_ddkd_nhomsp "+  
					" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
					" inner join nhomfocus nsp on nsp.pk_seq=nhomsanpham_fk  "+
					" where nhomsanpham_fk>=200000 AND  ISNHOMTHUONGBanRa=1  and nsp.doituong=0 and nsp.trangthai=1 and b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
					if(obj.getdvkdId().length()>0)
					{
					 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
					}
					if(obj.getkenhId().length()>0)
					{
					 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
					 
					}
					System.out.println("ISNHOMTHUONGBanRa : "+sql);
			rs=db.get(sql);		
		 
									
				   try 
				   {
					while (rs.next())
					{
						 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ChiTieuNhomThuongBanRa-"+rs.getString("ten"));	
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThucDatNhomThuongBanRa-"+rs.getString("ten"));
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("PhanTramNhomThuongBanRa-"+rs.getString("ten"));
						 i=i+1;
						 cell = cells.getCell(htb.get(i)+"1"); cell.setValue(rs.getDouble("TIENTHUONG"));
						 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThuongNhomThuongBanRa-"+rs.getString("ten"));
						 i=i+1;
					 }
					rs.close();
					} catch (Exception e) 
					{
			
						e.printStackTrace();
					}
		 
				   /**
				    * Thuong thang 12
				    */
				   
				   if(obj.getMonth().equals("12")&&obj.getYear().equals("2013"))
					{
				   
					   sql= " select  pk_seq as nhomsanpham_fk,dbo.ftBoDau(nsp.DIENGIAI) as ten,nsp.TIENTHUONG  from nhomfocus  nsp "+  
								" where pk_seq>=200000 AND  ISNHOMTHUONGBanRa=1  and nsp.doituong=0 and nsp.ThuongRieng=1 and  nsp.trangthai=1 and nsp.thang="+obj.getMonth()+" and nsp.nam="+ obj.getYear() ;
						if(obj.getdvkdId().length()>0)
						{
						 sql=sql+ " and nsp.dvkd_fk= "+ obj.getdvkdId();
						}
						if(obj.getkenhId().length()>0)
						{
						 sql=sql+ " and nsp.kbh_fk= "+ obj.getkenhId();
						 
						}
						System.out.println("Thuong Thang 12 : "+sql);
						rs=db.get(sql);		
										
					   try 
					   {
						   while (rs.next())
							{
								 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThucDatNhomThuongBanRa-"+rs.getString("ten"));
								 i=i+1;
								
								 cell = cells.getCell(htb.get(i)+"2"); cell.setValue("ThuongNhomThuongBanRa-"+rs.getString("ten"));
								 i=i+1;
							 }
							rs.close();
						} catch (Exception e) 
						{
							e.printStackTrace();
						}
				   
			}
				
				cell = cells.getCell(htb.get(i)+"2"); cell.setValue("TONG THUONG NHIEMVU(3)");
				
				cell.setStyle(style);
				i=i+1;
				
				cell = cells.getCell(htb.get(i)+"2"); cell.setValue("KHU VUC");
				cell.setStyle(style);
				i=i+1;
				
				
		db.shutDown();				
		cell = cells.getCell("M1"); 
	    ReportAPI.getCellStyle(cell,Color.NAVY,true, 9,(26*4+i-1)+"");
	   
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
		
		Cell cell1=cells.getCell("M1");
		Cell cell2=cells.getCell("N1");
		Style style;
		style = cell1.getStyle();
		Style style1;
		style1 = cell2.getStyle();
		 
		String	sql=" select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.ten) as ten  from  chitieunpp_ddkd_nhomsp "+  
			" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
		" inner join nhomsanpham nsp on nsp.pk_seq=nhomsanpham_fk "+
		" where b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;

			if(obj.getdvkdId().length()>0){
			 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
			}
			if(obj.getkenhId().length()>0){
			 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			}
		ResultSet  rs=db.get(sql);
			
			 String[] arraychuoiskuout= new String[20];
			
			 
			 if(rs!=null){
				 int i=0;
				 try {
					while (rs.next()){
						
						 arraychuoiskuout[i]=rs.getString("nhomsanpham_fk");
						 i++;
						 
					 }
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 }
		
			 
			// NHÓM THƯỞNG BanRa ISNHOMTHUONGBanRa=1
			 sql= " select distinct nhomsanpham_fk,dbo.ftBoDau(nsp.DIENGIAI) as ten  from  chitieunpp_ddkd_nhomsp "+  
				" inner join chitieunpp b on b.pk_Seq=chitieunpp_fk  "+
				" inner join nhomfocus nsp on nsp.pk_seq=nhomsanpham_fk  "+
				" where nhomsanpham_fk>=200000 and ISNHOMTHUONGBanRa=1  and nsp.doituong=0 and trangthai=1 AND  b.thang="+obj.getMonth()+" and b.nam="+ obj.getYear() ;
						    
			if(obj.getdvkdId().length()>0)
			{
			 sql=sql+ " and b.dvkd_fk= "+ obj.getdvkdId();
			}
			if(obj.getkenhId().length()>0)
			{
			 sql=sql+ " and b.kenh_fk= "+ obj.getkenhId();
			 
			}
		
				 rs=db.get(sql);
				 
				 String[] thuongBanRa= new String[20];
					
				 
				 if(rs!=null){
					 int i=0;
					 try {
						while (rs.next()){
							
							thuongBanRa[i]=rs.getString("nhomsanpham_fk");
							 i++;
							 
						 }
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				 }
				 
				 
		
		 rs = db.get(query);	
		
		int indexRow =3;
		try 
			{				
				Cell cell = null;
				float phantramMTD = 0;
				
				double tongthuog=0;
				double thuongSLBanRa=0;
				double thuongnhiemvu=0;
				
				
				while(rs.next())
				{ 				
					thuongnhiemvu=0;
					thuongSLBanRa=0;
					tongthuog=0;
				    cell = cells.getCell("DA" + Integer.toString(indexRow)); cell.setValue(indexRow-9);
				    cell.setStyle(style);
				    cell = cells.getCell("DB" + Integer.toString(indexRow)); cell.setValue(rs.getString("DDKDID"));
				    cell.setStyle(style);
				    cell = cells.getCell("DC" + Integer.toString(indexRow)); cell.setValue(rs.getString("DAIDIENKINHDOANH"));
				    cell.setStyle(style);
					cell = cells.getCell("DD" + Integer.toString(indexRow)); cell.setValue("NVBH");		
					cell.setStyle(style);
					cell = cells.getCell("DE" + Integer.toString(indexRow));cell.setValue(rs.getString("NHAPHANPHOI"));				
					cell.setStyle(style);
					
					
					String []chuoi =obj.getFieldShow();
					
					String []chuoinv =obj.getFieldHidden();
					int j=8;
					
					for (int i=0;i<chuoinv.length ;i++)
					{
						if(chuoinv[i]!=null)
						{
							cell = cells.getCell(htb.get(j)+"1");							
							double thuong=0;
							try
							{
								thuong=(Double)cell.getValue();
							}catch(Exception er)
							{
								er.printStackTrace();
							}
							thuongnhiemvu=thuongnhiemvu + rs.getFloat("NHIEMVU"+chuoinv[i])*thuong;
							
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("NHIEMVU"+chuoinv[i])*thuong );
							cell.setStyle(style);
							j=j+1;
						}
					}
				 
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("CTDOANHSO"));
					cell.setStyle(style);
					j=j+1;
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DOANHSO"));
					cell.setStyle(style);
					j=j+1;
					
					float PhanTramDoanhSo=0;
					if(rs.getFloat("CTDOANHSO") >0){
						PhanTramDoanhSo=	100* rs.getFloat("DOANHSO")/(rs.getFloat("chitieu"));
					}
					
			 
					cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(PhanTramDoanhSo);
					cell.setStyle(style);
					j=j+1;
					
					 sql=" SELECT B.Thuong  FROM TIEUCHITINHTHUONG A INNER JOIN TIEUCHITINHTHUONG_CHITIET B  "+
						" ON A.PK_SEQ=B.TIEUCHITINHTHUONG_FK "+
						" WHERE A.LOAI=1 AND THANG="+obj.getMonth()+" AND NAM="+obj.getYear()+" AND B.TIEUCHI=1 AND B.Tu < " +PhanTramDoanhSo+" AND B.Den >"+PhanTramDoanhSo;
					 
						ResultSet rsthuong =db.get(sql);
						double thuongsec=0;
						if(rsthuong!=null)
						{
							if(rsthuong.next())
							{
									thuongsec=rsthuong.getDouble("thuong");
							}
							rsthuong.close();
						}
						cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(thuongsec);
						cell.setStyle(style);
						j=j+1;	
					for (int i=0;i<chuoi.length ;i++)
					{
						if(chuoi[i]!=null)
						{
							cell = cells.getCell(htb.get(j)+"1");
							double thuong=0;
							try
							{
								thuong=(Double)cell.getValue();
							}catch(Exception er)
							{
								System.out.println(htb.get(j)+"1");
								er.printStackTrace();
							}
							thuongSLBanRa=thuongSLBanRa +thuong* rs.getFloat("NHOMFC"+chuoi[i]);
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("NHOMFC"+chuoi[i]) );
							cell.setStyle(style);
							j=j+1;
						}						
					}
					
					
					for (int i=0;i<chuoinv.length ;i++){
						if(chuoinv[i]!=null){
							String duyetdat="";
							if(rs.getString("NHIEMVU"+chuoinv[i]).trim().equals("1")){
								duyetdat="Đạt";
							}
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(duyetdat);
							cell.setStyle(style);
							j=j+1;
							
						}
						
						
					}
					
				
					
					
					float SumChiTieuDDKD=0;
					float SumThucDatCTDDKD=0;
					for (int i=0;i<arraychuoiskuout.length ;i++){
						if(arraychuoiskuout[i]!=null){
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("CTNHOM"+arraychuoiskuout[i]));	
							SumChiTieuDDKD= SumChiTieuDDKD+rs.getFloat("CTNHOM"+arraychuoiskuout[i]);
							SumThucDatCTDDKD= SumThucDatCTDDKD+rs.getFloat("DS1"+arraychuoiskuout[i]);
							j=j+1;
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DS1"+arraychuoiskuout[i]));
							j=j+1;
							phantramMTD =0;
							
							if(rs.getFloat("CTNHOM"+arraychuoiskuout[i]) >0){
								phantramMTD=rs.getFloat("DS1"+arraychuoiskuout[i])*100/rs.getFloat("CTNHOM"+arraychuoiskuout[i]);
							}
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
							j=j+1;
						}
					}
					double totalthuongnhomBanRa=0;
					
					for (int i=0;i<thuongBanRa.length ;i++){
						if(thuongBanRa[i]!=null){
							cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue( rs.getFloat("NHOMBanRa"+thuongBanRa[i]));	
						 
							j=j+1;
							
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getFloat("DSTHUONGBanRa"+thuongBanRa[i]));
							j=j+1;
							
							phantramMTD =0;
							
							if(rs.getFloat("NHOMBanRa"+thuongBanRa[i]) >0){
								phantramMTD=rs.getFloat("DSTHUONGBanRa"+thuongBanRa[i])*100/rs.getFloat("NHOMBanRa"+thuongBanRa[i]);
							}
							
							cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(phantramMTD);
							j=j+1;
							
							cell = cells.getCell(htb.get(j)+"1");
							
							double thuong=0;
							try{
								
					 
								thuong=(Double)cell.getValue();
								
								
							}catch(Exception er){
							
							} 
							if(phantramMTD>=100){
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(thuong);
								totalthuongnhomBanRa=totalthuongnhomBanRa+thuong;
							}else{
								cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(0);
							}
							
							j=j+1;
						}
					}
					
					double thuong12=0;
					if(obj.getMonth().equals("12")&&obj.getYear().equals("2013"))
					{
						cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(rs.getInt("tdBanRa")); j=j+1;
						cell.setStyle(style);
						int thucdatBanRa= rs.getInt("tdBanRa");
						
						if(thucdatBanRa>0)
							thuong12=thucdatBanRa*500;
						
						cell = cells.getCell(htb.get(j)+ Integer.toString(indexRow)); cell.setValue(thuong12);j=j+1;
						cell.setStyle(style);
					}
					
					cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(thuongnhiemvu);
					cell.setStyle(style);
					j=j+1;
					
					cell = cells.getCell(htb.get(j) + Integer.toString(indexRow));  cell.setValue(rs.getString("Khuvuc"));
					cell.setStyle(style);
				
					
					
					cell = cells.getCell("DG" + Integer.toString(indexRow));cell.setValue(thuongSLBanRa);
					cell.setStyle(style);
					
					
					cell = cells.getCell("DF" + Integer.toString(indexRow));cell.setValue(thuongSLBanRa+thuongnhiemvu+totalthuongnhomBanRa+thuong12+thuongsec);
					cell.setStyle(style);
					indexRow++;
				}
				if(indexRow==3)
				{
					throw new Exception("Không có báo cáo với điều kiện đã chọn !");
				}
				if(rs != null) rs.close();
				if(db!=null){
					db.shutDown();
				}

		    	
			}catch(Exception err)
			{
				err.printStackTrace();

				throw new Exception("Khong the tao duoc bao cao trong thoi gian nay. Error :"+err.toString());
			}
	}


}
