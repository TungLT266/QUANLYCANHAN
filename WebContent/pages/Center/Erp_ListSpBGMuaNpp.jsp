<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="ISO-8859-1"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.distributor.db.sql.dbutils" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import = "java.net.URLDecoder" %>

<%  
	String nhappid = (String) session.getAttribute("nhappid");
	String kenhid=(String)session.getAttribute("kenhid");
	String dvkdid=(String)session.getAttribute("dvkdid");
	String donhangid=(String)session.getAttribute("donhangid");
	String ngaygiaodich=(String)session.getAttribute("ngaygiaodich");
	
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
	
	//Lay Ma Nha Phan Phoi Tu userId
	

	String command = "";
	String gia="giamuanpp";
	if(tuvanchuyen.equals("1")){
		 gia="giamuanpp_tuvc";
	}
 	command=
 	"	select    b.pk_seq, b.ma, b.ten,isnull(bgsp.giamuanpp ,'0')* qc.soluong1/qc.soluong2 as giachuan ,isnull(bgsp."+gia+" ,'0')* qc.soluong1/qc.soluong2 as dongia, isnull(d.donvi,'') as donvi \n"+ 
 	"		,0 as available   ,  qc.soluong1/qc.soluong2* isnull(b.trongluong,0) as trongluong, qc.soluong1/qc.soluong2* isnull(b.thetich,0) as thetich , \n"+
 	"		vc.soluong2/vc.soluong1 as goivc \n"+ 
 	"	from  \n"+
 	"	( \n"+
 	"		SELECT B.PK_SEQ,B.KENH_FK,B.DVKD_FK,C.NPP_FK \n"+ 
 	"		FROM BANGGIAMUANPP B  \n"+
 	"			INNER JOIN BANGGIAMUANPP_NPP C ON B.PK_SEQ=C.BANGGIAMUANPP_FK \n"+  
 	"		WHERE  B.TUNGAY <='"+ngaygiaodich+"' AND   \n"+
 	"		B.PK_SEQ = \n"+
 	"		( \n"+   
 	"			SELECT TOP(1) BG.PK_SEQ FROM BANGGIAMUANPP BG \n"+
 	"			INNER JOIN BANGGIAMUANPP_NPP BGNPP ON BG.PK_SEQ=BGNPP.BANGGIAMUANPP_FK \n"+ 
 	"			WHERE BG.TUNGAY <= '"+ngaygiaodich+"' AND BGNPP.NPP_FK = C.NPP_FK AND BG.KENH_FK=B.KENH_FK \n"+
 	"			ORDER BY TUNGAY DESC \n"+
 	"		)  AND C.NPP_FK="+nhappid+" AND B.DVKD_FK="+dvkdid+" AND B.KENH_FK="+kenhid+" \n"+
 	"	)bg inner join bgmuanpp_sanpham bgsp on bgsp.bgmuanpp_fk=bg.pk_seq \n"+
 	"	inner join sanpham b on b.pk_seq=bgsp.sanpham_fk \n"+
 	"	left join quycach qc on qc.sanpham_fk=b.pk_seq and qc.dvdl2_fk=100018 \n"+ 
 	"	left  join  dbo.donvidoluong as d on d.pk_seq = qc.dvdl2_fk  \n"+
 	"	left join quycach vc on vc.sanpham_fk=b.pk_seq and vc.dvdl2_fk=100052 \n"+ 
 	"	left join (select soluong ,sanpham_fk from dondathang_sp where dondathang_fk=null ) ddh_sp  on ddh_sp.sanpham_fk=b.pk_seq \n"+  
 	"	where     (b.trangthai = '1') ";
 
	System.out.println("Sql : ListSpbangGiaMuaNhaPP DON HANG New ; "+command);
	
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
					
					
					if(masp.toUpperCase().startsWith(query.toUpperCase()) ||masp.toUpperCase().indexOf(query.toUpperCase()) >= 0 
							|| tensp.toUpperCase().indexOf(query.toUpperCase()) >= 0 || donvi.toUpperCase().indexOf(query.toUpperCase()) >= 0 )
					{
						tenSP=sp.getString("ten");
						if(tenSP.length() > 50)
							tenSP = tenSP.substring(0, 50);
						out.print("###" + sp.getString("ma") + " - " + tenSP+" [" +  sp.getString("donvi") + "] ["+sp.getFloat("giachuan") + "] ["+ sp.getFloat("dongia") + "] ["+ sp.getString("available") + "] ["+ sp.getString("trongluong") + "] ["+ sp.getString("thetich") + "] ["+ sp.getFloat("goiVC") + "] ["+ sp.getString("PK_SEQ") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						m++;
					}
				}
			}
			sp.close();
			db.shutDown();
		}
		catch(SQLException e){}
	}
	
	
%>