<%@page import="geso.traphaco.center.beans.donmuahang.ISanPhamTraKM"%>
<%@page import="java.util.List"%>
<%@page import="geso.traphaco.center.beans.donmuahang.ITaodonhangKm"%>
<%@page import="geso.traphaco.center.beans.congthucdndh.imp.Congthucdndh"%>
<%@page import="geso.traphaco.center.beans.congthucdndh.ICongthucdndh"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.mokhaibaochitieu.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 
	ITaodonhangKm dhkm=(ITaodonhangKm)session.getAttribute("dhkm");
	String thang = dhkm.getThang();
   String nam = dhkm.getNam();
   String msg = dhkm.getMsg();
   ResultSet rsctkm=dhkm.getRsCTKM();
   ResultSet rsnpp=dhkm.getRsNpp();
   
   ResultSet rskenh=dhkm.getRsKenh();

   ResultSet rssp=dhkm.getRsSp();
   
   List<ISanPhamTraKM> listsp1=dhkm.getListSanPham();
   String ctkmchon=dhkm.getCTKMChon();
   String NPPchon=dhkm.getNppChon();
   
   String NPPchon_ttt=dhkm.getNppChon_ThanhToanTien();
   
   
%>



<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<SCRIPT language="JavaScript" type="text/javascript">
	
	$(document).ready(function()
		{
			$("#ctkmId").select2();
		});		

	function thuchien()
	{
		  if(!confirm("Bạn có chắc muốn thực hiện không ?"))
		   {
			  return;	
		   }
			document.forms["mkbctBean"].action.value="thuchien";   	
    	document.forms['mkbctBean'].submit();
	}	
	function submit1()
	{
		 
			document.forms["mkbctBean"].action.value="chance";   	
    		document.forms['mkbctBean'].submit();
	}
	
	
	 function sellectAll(cbId1,cbId2 )
	 {
		 var chkAll_Lct = document.getElementById(cbId1);
		 var loaiCtIds = document.getElementsByName(cbId2);
		 
		// alert(cbId1);
		 
		 if(chkAll_Lct.checked)
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				 loaiCtIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				 loaiCtIds.item(i).checked = false;
			 }
		 }
	 }


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="mkbctBean" method="post" action="../../TaoDonHangTraKhuyenMaiSvl" >
<input type = "hidden" name="action" value = "1">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="1" height="100%">
	<TR>
		<TD align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>				
					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<TR height="22">
				   				<TD align="left" class="tbnavigation">
				   					&nbsp;Quản lý bán hàng &gt; Tạo đơn hàng khuyến mãi</TD>
				   				<TD  align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD> 
		     				</TR>
   
						</TABLE>
					</TD>								
				</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="1"  cellspacing="1" >
			  <tr>
				<TD align="left" colspan="4" class="legendtitle">
				<FIELDSET>
				<LEGEND class="legendtitle">Thông báo</LEGEND>
			
   				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= msg %></textarea>
				</FIELDSET>
				</TD>
			 </tr> 
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black"> Tạo đơn hàng khuyến mãi tháng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="20%" class="plainlabel">Chọn tháng </TD>
						  	  	<TD width="80%" class="plainlabel">
									<select onchange="submit1();" name="thang"  style="width :50px" ">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=""+k;
  									  if(thang.equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option  value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Chọn năm </TD>
						  	  	<TD class="plainlabel">
									<select onchange="submit1();" name="nam" style="width :50px" ">
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2008;n<2020;n++){
  										if(nam.equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select> 
						  	  	</TD>
						  </TR>
 							
						 <TR>
						  			
								  	<TD colspan="2" class="plainlabel" width="100%"> 
								  		<table   width="100%"> 
								  		<tr class="tbheader" >
								  			<th >
								  				Chương trình khuyến măi
								  			</th>
								  			<th>
								  				Chọn
								  				<input type="checkbox" name="ctkmid"  id="ctkmid" onClick="sellectAll('ctkmid','ctkmchon')">
								  			</th>
								  			
								  		</tr>
											<% 
											
											if(rsctkm!=null){
												while (rsctkm.next()){ %>
														<tr class= 'tblightrow'>
													   	<td>
													    <%=rsctkm.getString("scheme") +"--" +rsctkm.getString("diengiai") %>
													   	</td>
															<td>
															<%if(ctkmchon.indexOf(rsctkm.getString("pk_seq").trim()) >0 ) { %>
															<input type="checkbox"  checked="checked" onchange="submit1();"  name= "ctkmchon" value="<%=rsctkm.getString("pk_seq")%>" >
															<%}else{ %>
															<input type="checkbox"  name= "ctkmchon" onchange="submit1();"  value="<%=rsctkm.getString("pk_seq")%>" >
															<%} %> 
													 	  </td>
													 	
													 	  
														</tr>
												<%} 
											}
											
											%>
								  		</table>
								  	 </TD>				
						  </TR>

						  
						   <TR>
						  			
								  	<TD colspan="2" class="plainlabel" width="100%"> 
								  		<table   width="100%"> 
								  		<tr class="tbheader" >
								  			<th >
								  				Chi nhánh / Đối tác
								  			</th>
								  			<th>
								  				Chọn
								  				<input type="checkbox" name="cbNppId"  id="cbNppId" onClick="sellectAll('cbNppId','nhappchon')">
								  			</th>
								  			<th style="display:none">
								  				Chọn thanh toán tiền
								  				<input type="checkbox" name="cbCtkmId" id="cbCtkmId" onClick="sellectAll('cbCtkmId','nhappchon_ttt')">
								  			</th>
								  		</tr>
											<% 
											
											if(rsnpp!=null){
												while (rsnpp.next()){ %>
														<tr class= 'tblightrow'>
													   	<td>
													    <%=rsnpp.getString("sitecode") +"--" +rsnpp.getString("ten") %>
													   	</td>
															<td>
															<%if(NPPchon.indexOf(rsnpp.getString("pk_seq").trim()) >0 ) { %>
															<input type="checkbox"  checked="checked" name= "nhappchon" value="<%=rsnpp.getString("pk_seq")%>" >
															<%}else{ %>
															<input type="checkbox"  name= "nhappchon" value="<%=rsnpp.getString("pk_seq")%>" >
															<%} %> 
													 	  </td>
													 	  <td  style="display:none">
															<%if(NPPchon_ttt.indexOf(rsnpp.getString("pk_seq").trim()) >0 ) { %>
															<input type="checkbox" checked="checked" name= "nhappchon_ttt" value="<%=rsnpp.getString("pk_seq")%>" >
															<%}else{ %>
															<input type="checkbox" name= "nhappchon_ttt" value="<%=rsnpp.getString("pk_seq")%>" >
															<%} %> 
													 	  </td>
													 	  
														</tr>
												<%} 
											}
											
											%>
								  		</table>
								  	 </TD>				
						  </TR>

 						  <TR>							  	
 						   <TD><a class="button2" href="javascript:thuchien();">
							<img style="top: -4px;" src="../images/button.png" alt="">Tạo đơn hàng khuyến mãi</a>&nbsp;&nbsp;&nbsp;&nbsp;
 						  </TD>
						 </TR>

						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
		</TABLE>
	</TD>
</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
}%>