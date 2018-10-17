<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Iterator"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.db.sql.dbutils" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
	
<%
	dbutils db = new dbutils();
	
	try{
		String userId = (String)session.getAttribute("userId");
		String NhaPhanPhoiId = (String) session.getAttribute("NhaPhanPhoiId");
		
		System.out.println("Đã vào đây");
		String khoId = (String) session.getAttribute("KhoId");
		System.out.println("khoId:" +khoId);

		String nhomkenhId = (String) session.getAttribute("nhomkenhId");
		if(nhomkenhId == null){
			nhomkenhId = "";
		}
		System.out.println("Nhóm kênh:"+ nhomkenhId);
		String nvid_xuat = (String) session.getAttribute("nvid_xuat");
		if(nvid_xuat == null){
			nvid_xuat = "";
		}
		System.out.println("Nhân viên:"+ nvid_xuat);
		String khid_xuat = (String) session.getAttribute("khid_xuat");
		if(khid_xuat == null){
			khid_xuat = "";
		}
		System.out.println("Khách hàng:" + khid_xuat);
		String nccchuyenId = (String) session.getAttribute("nccchuyenId");
		if(nccchuyenId == null){
			nccchuyenId = "";
		}
		System.out.println("Nhà cung cấp:" + nccchuyenId);
		
		//khoId= "100001";
		String query = (String)request.getQueryString(); 
	   	query = new String(query.substring(query.indexOf("&letters=") + 9, query.length()).getBytes("UTF-8"), "UTF-8");
	   	query = URLDecoder.decode(query, "UTF-8").replace("+", " ");
	   	
	   	
	   	Utility Ult = new Utility();
	   	query = Ult.replaceAEIOU(query);
	   	response.setHeader("Content-Type", "text/html");
		request.setCharacterEncoding("UTF-8");
		String sql ="";
		sql = " select top(30) sp.PK_SEQ as idsp,MA AS masp ," +
						 " TEN  AS tensp, dv.DONVI as donvi, k.SOLUONG as soluongton "+ 
						 " from NHAPP_KHO k inner join SANPHAM sp  on k.SANPHAM_FK= sp.PK_SEQ " + 
			             " inner join DONVIDOLUONG dv on sp.DVDL_FK= dv.PK_SEQ where k.KHO_FK= "+khoId+" "+
			             " and k.NPP_FK =" + NhaPhanPhoiId +
			             " and sp.TIMKIEM like N'%"+query+"%'";
		
		// kho ký gửi khách hàng
       if(khid_xuat.trim().length()>0){
    	sql = " select top(30) sp.PK_SEQ as idsp,MA AS masp ," +
			 " TEN  AS tensp, dv.DONVI as donvi, k.SOLUONG as soluongton "+ 
			 " from NHAPP_KHO_KYGUI k inner join SANPHAM sp  on k.SANPHAM_FK= sp.PK_SEQ " + 
             " inner join DONVIDOLUONG dv on sp.DVDL_FK= dv.PK_SEQ where k.KHO_FK= "+khoId+" "+
             " and k.NPP_FK =" + NhaPhanPhoiId +
             " and k.khachhang_fk = " + khid_xuat +
             " and sp.TIMKIEM like N'%"+query+"%'";
       } else
		// kho ký gửi nhà cung cấp
		if(nccchuyenId.trim().length() >0){
			sql = " select top(30) sp.PK_SEQ as idsp,MA AS masp ," +
			 " TEN  AS tensp, dv.DONVI as donvi, k.SOLUONG as soluongton "+ 
			 " from NHAPP_KHO_NCC k inner join SANPHAM sp  on k.SANPHAM_FK= sp.PK_SEQ " + 
             " inner join DONVIDOLUONG dv on sp.DVDL_FK= dv.PK_SEQ where k.KHO_FK= "+khoId+" "+
             " and k.NPP_FK =" + NhaPhanPhoiId +
             " and k.NCC_FK = " + nccchuyenId +
             " and sp.TIMKIEM like N'%"+query+"%'";
		} else
		// kho ký gửi nhân viên
		if(nvid_xuat.trim().length() >0){
			sql = " select top(30) sp.PK_SEQ as idsp,MA AS masp ," +
			" TEN  AS tensp, dv.DONVI as donvi, k.SOLUONG as soluongton "+ 
			" from NHAPP_KHO_DDKD k inner join SANPHAM sp  on k.SANPHAM_FK= sp.PK_SEQ " + 
            " inner join DONVIDOLUONG dv on sp.DVDL_FK= dv.PK_SEQ where k.KHO_FK= "+khoId+" "+
            " and k.NPP_FK =" + NhaPhanPhoiId +
            " and k.ddkd_fk = " + nvid_xuat +
            " and sp.TIMKIEM like N'%"+query+"%'";
		}
		if(nhomkenhId.trim().length()>0)
			sql += " and nhomkenh_fk in ("+nhomkenhId+")";
		System.out.println(sql);
		System.out.println(query);
	
		ResultSet rs = db.get(sql);
		
		if(rs!=null){
			while(rs.next()){
				String idsp = rs.getString("idsp");
				String masp = rs.getString("masp");
				String tensp = rs.getString("tensp");
				String donvi = rs.getString("donvi");
				String soluongton = rs.getString("soluongton");
				
				out.print("###" + idsp + " -- " + masp + " [" +  tensp + "] ["+ donvi+ "] ["+ soluongton+"]|");
			}
		}
		rs.close();
		//pre.close();

	}catch(Exception e){		
			System.out.println("Exception lay SP: " + e.getMessage());
	} finally{
		db.getConnection().close();
	}
%>