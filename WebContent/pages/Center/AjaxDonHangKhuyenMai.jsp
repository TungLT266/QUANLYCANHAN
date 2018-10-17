<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>
<%  
	String nhappid = (String) session.getAttribute("nhappid");
	String kenhid=(String)session.getAttribute("kenhid");
	String dvkdid=(String)session.getAttribute("dvkdid");
	String donhangid=(String)session.getAttribute("donhangid");
	
	String tuvanchuyen=(String)session.getAttribute("tuvanchuyen");
	if(tuvanchuyen==null){
		tuvanchuyen="0";
	}
%>

<% dbutils db = new dbutils(); %>
<%
	String maSP = "";
	String tenSP = "";
	String dvtSP = "";
	String dongiaSP = "";
	

	String command = "";
	String gia="giamuanpp";
	if(tuvanchuyen.equals("1")){
		 gia="giamuanpp_tuvc";
	}
	
 	command=" SELECT    b.PK_SEQ, b.MA, b.TEN,isnull(a.giachuan ,'0')* qc.soluong1/qc.soluong2 as giachuan ,isnull(a.dongia ,'0')* qc.soluong1/qc.soluong2 as dongia, isnull(d.DONVI,'') as donvi \n"+ 
 	" ,0 as available   ,  qc.soluong1/qc.soluong2* isnull(b.trongluong,0) as trongluong, qc.soluong1/qc.soluong2* isnull(b.thetich,0) as thetich , \n"+
 	"	vc.SOLUONG2/vc.SOLUONG1 as goiVC,isnull(qc.soluong1/qc.soluong2,0) as quycach \n"+ 
 	" FROM \n"+
 		" dbo.SANPHAM AS b INNER JOIN  ( select SANPHAM_FK, "+gia+" AS dongia,GIAMUANPP as GiaChuan  FROM     \n"+
        	" 	dbo.BGMUANPP_SANPHAM  d inner join BangGiaMuaNPP p on p.pk_seq=d.BGMuaNPP_fk  inner join BANGGIAMUANPP_NPP \n"+
        		"  npp on npp.Banggiamuanpp_fk=p.pk_seq where kenh_fk="+kenhid+" and dvkd_fk="+dvkdid+" and npp.npp_fk="+nhappid+" and p.trangthai=1 ) \n"+
 				"  AS a ON a.SANPHAM_FK = b.PK_SEQ \n"+ 
        		" left join quycach qc on qc.sanpham_fk=b.pk_seq and qc.dvdl2_fk=100018 \n"+
        		" LEFT  JOIN  dbo.DONVIDOLUONG AS d ON d.PK_SEQ = qc.dvdl2_fk \n"+
        		"  left join QUYCACH vc on vc.SANPHAM_FK=b.PK_SEQ and vc.DVDL2_FK=100052 \n"+
        		" left join (select soluong ,sanpham_fk from dondathang_sp where dondathang_fk="+donhangid+" ) ddh_sp  on ddh_sp.sanpham_fk=b.pk_seq  \n"+
        		" WHERE     (b.TRANGTHAI = '1') ";
 
 	//System.out.println("San pham:"+ command);
	
 			/* response.setHeader("Content-Type", "text/html");
 			String query = (String)request.getParameter("letters"); */
 			response.setHeader("Content-Type", "text/html");
 			request.setCharacterEncoding("UTF-8");
 			
 			//String query = (String)request.getParameter("letters"); 
 		   	String query = (String)request.getQueryString(); 
 		   	
 		   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
 		   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
 		   	
 		   	Utility Ult = new Utility();
 		   	query = Ult.replaceAEIOU(query);
 			
 			ResultSet sp = db.get(command);
	if(sp != null)
	{
		int m = 0;
		try
		{
			while(sp.next())
			{
				if(sp.getString("ma") != null)
				{
					String masp = Ult.replaceAEIOU(sp.getString("ma"));
					String tensp = Ult.replaceAEIOU(sp.getString("ten"));
					String donvi = Ult.replaceAEIOU(sp.getString("donvi"));					
					if(masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 ||tenSP.toUpperCase().startsWith(query.toUpperCase())
							|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						tenSP=sp.getString("ten");
						out.print("###" + sp.getString("ma") + " - " + tenSP+" [" +  sp.getString("donvi") + "] ["+sp.getFloat("giachuan") + "] ["+ sp.getFloat("dongia") + "] ["+ sp.getString("quycach") + "] ["+ sp.getString("trongluong") + "] ["+ sp.getString("thetich") + "] ["+ sp.getFloat("goiVC") + "] ["+ sp.getString("PK_SEQ") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						m++;
					}
				}
			}
			sp.close();
			db.shutDown();
		}
		catch(SQLException e){e.printStackTrace();}
	}
	
	
%>