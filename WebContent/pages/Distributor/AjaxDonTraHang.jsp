<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>

<%
	String dvkdId = "100001";
	if(session.getAttribute("dvkdId") != null )
		dvkdId = (String) session.getAttribute("dvkdId");
	
	String kbhId = "-1";
	if(session.getAttribute("kenhId") != null )
		kbhId = (String) session.getAttribute("kenhId");
	
	
	
	String nppId = "-1";
	if(session.getAttribute("nppId") != null )
		nppId = (String) session.getAttribute("nppId");
	
	String khonhapId = "-1";
	if(session.getAttribute("khoId") != null )
		khonhapId = (String) session.getAttribute("khoId");
	
	String khId = "-1";
	if(session.getAttribute("khachhangId") != null ) {
		khId = (String) session.getAttribute("khachhangId");
	}
	
	
	String khachhang_fk = khId;
	String npp_tra_fk = khId;
	if(khId.substring(0,2).equals("KH")){
		khachhang_fk = khId.substring(2,khId.length());
		npp_tra_fk = "NULL";
	}
	else{
		khachhang_fk = "NULL";
		npp_tra_fk = khId.substring(3,khId.length());
	}

	System.out.println("dvkdId:"+dvkdId +", kbhId:"+kbhId+", nppId:"+nppId);
	
	if( dvkdId.trim().length() > 0 && kbhId.trim().length() > 0 && nppId.trim().length() > 0 )
	{
		dbutils db = new dbutils();
		String sql = " select tructhuoc_fk, loaiNPP, dungchungkenh from NHAPHANPHOI where pk_seq = '" + nppId + "' ";
		ResultSet rs = db.get(sql);
		String tructhuocId = "";
		String loaiNPP = "";
		String dungchungkenh="";
		String nhomkenhId = "( select nk_fk from NHOMKENH_KENHBANHANG where kbh_fk = '" + kbhId + "' )";
		if(rs != null)
		{
			try 
			{
				if(rs.next())
				{
					loaiNPP = rs.getString("loaiNPP");
					tructhuocId = rs.getString("tructhuoc_fk");
					dungchungkenh=rs.getString("dungchungkenh");
				}
				rs.close();
				/* 
				if(dungchungkenh.equals("1"))
				{
					nhomkenhId = "100000";
				} */
			} 
			catch (Exception e) { e.printStackTrace(); }
		}
		
		try
		{				
			 //BAN DAU LAY GIA CHUAN SAU DO CHON LAI GIA THI CAP NHAT LAI GIA THEO DON VI (BANG GIA BEN NAY KHONG CHIA THEO KENH)
			 String command = "";
			 if(khId.substring(0,2).equals("KH")){
				 
			 command =" select (select kho.available from nhapp_kho kho where kho.sanpham_fk=a.pk_seq and kho.KHO_FK= " + khonhapId + " and NPP_FK = '" + nppId + "' and kho.NHOMKENH_FK = " + nhomkenhId + " )as soluongton,a.ma, a.ten, b.donvi, ISNULL(trongluong, 0) as trongluong, ISNULL(thetich, 0) as thetich, \n" +
					  " cast (  isnull( ( select soluong2 / soluong1 from QUYCACH where SANPHAM_FK = a.PK_SEQ and DVDL1_FK = a.DVDL_FK	and DVDL2_FK = '100018' ), 0 ) as numeric(18, 2) ) as qc,	\n" +
					  " isnull( dbo.LayDonGia_Search(1, " + nppId + ", " + khachhang_fk + ", null, " + nhomkenhId + ", a.pk_seq), 0) as giamua, a.thuexuat  \n" +
					  " from SANPHAM a left join DONVIDOLUONG b on a.dvdl_fk = b.pk_seq \n" +
					  " inner join NGANHHANG c on a.nganhhang_fk = c.pk_seq \n" +
					  " where a.trangthai = '1' and a.pk_seq > 0 and a.DVKD_FK = '" + dvkdId + "'  \n";
					  
			 }else{
				 
			 command =" SELECT 0 AS SOLUONGTON ,SP.MA, SP.TEN, DVDL.DONVI, ISNULL(TRONGLUONG, 0) AS TRONGLUONG, ISNULL(THETICH, 0) AS THETICH, \n"+ 
					  " CAST (  ISNULL( ( SELECT SOLUONG2 / SOLUONG1 FROM QUYCACH WHERE SANPHAM_FK = SP.PK_SEQ AND DVDL1_FK = SP.DVDL_FK	AND DVDL2_FK = '100018' ), 0 ) AS NUMERIC(18, 2) ) AS QC,  \n"+	
					  " BGSP.GIAMUA_SAUCK AS GIAMUA, SP.thuexuat AS thuexuat  \n"+
					  " FROM BANGGIAMUANPP BG  \n"+
					  " INNER JOIN BGMUANPP_SANPHAM BGSP ON BG.PK_SEQ=BGSP.BGMUANPP_FK \n"+
					  " INNER JOIN SANPHAM SP ON SP.PK_SEQ=BGSP.SANPHAM_FK \n"+
					  " LEFT JOIN DONVIDOLUONG DVDL ON DVDL.PK_SEQ=SP.DVDL_FK \n"+
					  " inner join NGANHHANG c on sp.nganhhang_fk = c.pk_seq \n"+
					  " WHERE BG.NHOMKENH_FK="+nhomkenhId+
					  " AND BG.PK_SEQ IN (SELECT BANGGIAMUANPP_FK FROM BANGGIAMUANPP_NPP WHERE NPP_FK= "+npp_tra_fk+") AND BG.TRANGTHAI = 1 ";
			 
		 }
							 
			System.out.println("Lay san pham: " + command);
			
			response.setHeader("Content-Type", "text/html");
			String query = (String)request.getParameter("letters");
			
			ResultSet sp = db.get(command);
			int j = 0;
			if(sp != null)
			{
				while(sp.next())
				{
					//double quycach = rs.get
					if(sp.getString("ma").toUpperCase().contains(query.toUpperCase()) || sp.getString("ten").toUpperCase().contains(query.toUpperCase()) )
					{
						String tensp = sp.getString("ten");
						//out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("trongluong") + "] [" + sp.getString("thetich") + "] [" + sp.getString("qc") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
						out.print("###" + sp.getString("ma") + " - " + tensp + " [" + sp.getString("donvi") + "] [" + sp.getString("giamua") + "] [" + sp.getString("thuexuat") + "] [" + sp.getString("soluongton") + "]|"); //luu y: bat buoc phai co dau phan cach '|' o cuoi moi dong'
					}	
					
				}	
				sp.close();
			}
			
			db.shutDown();
		}
		catch(Exception ex)
		{ 
			ex.printStackTrace();
		}
	}
	
	 
	/* session.setAttribute("dvkdId", null);
	session.setAttribute("kbhId", null);
	session.setAttribute("nppId",null);
	session.setAttribute("lsxBean", null);
	session.setAttribute("khoId", null); */
	
%>

