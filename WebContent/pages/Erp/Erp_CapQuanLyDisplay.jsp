<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.erp_capquanly.IErp_capquanly" %>
<%@ page  import = "geso.traphaco.erp.beans.erp_capquanly.imp.Erp_capquanly" %>
<%@ page  import = "java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>

<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 

%>

<%IErp_capquanly cnBean = (IErp_capquanly)session.getAttribute("cnBean");%>

<% ResultSet loaicapRs = cnBean.getLoaicapRs(); %>
<% ResultSet quanlycapRs = cnBean.getQuanlycapRs(); %>
<% ResultSet nhanvienRs = cnBean.getNhanvienRs(); %>
<% ResultSet nhanvienchonRs = cnBean.getNhanvienChonRs(); 
   ResultSet rsCty = (ResultSet)cnBean.getRsCty();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<TITLE>Phanam - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.scrollTableBody-1.0.0.js" type="text/javascript"></script>

<script type="text/javascript">

function submitform()
{
	document.forms["cnForm"].action.value='submit';
	document.forms["cnForm"].submit();	
}

function saveform()
{	
	 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 document.forms['cnForm'].action.value='save';
     document.forms['cnForm'].submit();
}

</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
	function goBack(){
		window.history.back();
	}
</script>

</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="cnForm" method="post" action="../../Erp_CapQuanLyUpdateSvl">
<input type="hidden" value="save" name="action">
<INPUT type="hidden" name="userId" value='<%=userId %>' size="30">
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type = "hidden" value = "<%=cnBean.getID()%>" name = "id">
<table width="100%" border="0" cellspacing="0" cellpadding="0">
	<tr>
		<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<table width="100%" cellpadding="0" cellspacing="1">
			<tr>
					<td align="left" class="tbnavigation">
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr height="22">
						<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Cấp quản lý &gt; Hiển thị </TD>
						<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
					</tr>
					</table>
					</td>
				</tr>
			</table>
				
			<table width="100%" border="0" cellpadding="3" cellspacing="0">
				<tr>
					<td>
					<table width="100%" border="0" cellpadding="0" cellspacing="0">
					<tr class = "tbdarkrow">
						<td width="30" align="left"> <a href="javascript:goBack()" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </a></td>
					    <td width="2" align="left" ></td>
						<td width="30" align="left" >
							
						</td>
						<td>&nbsp;</td>						
					</tr>
					</table>
					</td>
				</tr>
			</table>
								  				
					<table width="100%" cellspacing="0" cellpadding="6">
					<tr>
						<td align="left" colspan="4" class="legendtitle">
						<fieldset>
							<legend class="legendtitle">Thông báo </legend>
			    			<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1" ><%=cnBean.getMsg()%></textarea>
						</fieldset>
						</td>
					</tr>	
					
					<tr>
						<TD align="left" colspan="4" class="legendtitle">
						<fieldset>
						<LEGEND class="legendtitle">Thông tin cấp quản lý
						</LEGEND>
						<TABLE  width="100%" cellspacing="0" cellpadding="6">
									
						<tr>
							 <TD class="plainlabel">Loại cấp quản lý</TD>
                       		 <TD class="plainlabel" colspan="3">
				                  <select class="select2" name="loaicapId" id="loaicapId" style="width: 200px" readonly="readonly">
				                  	<option value=""> </option>
		                        	<%
		                        		if(loaicapRs != null)
		                        		{
		                        			try
			                        		{
			                        			while(loaicapRs.next())
			                        			{  
			                        			if( loaicapRs.getString("pk_seq").equals(cnBean.getLoaicapId())){ %>
			                        				<option value="<%= loaicapRs.getString("pk_seq") %>" selected="selected" ><%= loaicapRs.getString("ten")%></option>
			                        			<%}else { %>
			                        				<option value="<%= loaicapRs.getString("pk_seq") %>" ><%= loaicapRs.getString("ten") %></option>
			                        		 <% } } loaicapRs.close();
			                        		} catch(SQLException ex){}
		                        		}
		                        	%>
		                        </select>
		               		 </TD> 		       
						</tr>

						<tr>
					  		<td class="plainlabel">Mã cấp</td> <td class="plainlabel"> 
					  			<input type = "text" name = "macap" value="<%=cnBean.getMacap()%>" style="border-radius:4px; height: 22px;" readonly="readonly"> 
					  		</td>
						</tr>
						
						<tr>
					  		<td class="plainlabel">Tên cấp</td> <td class="plainlabel"> 
					  			<input type = "text" name = "tencap" value="<%=cnBean.getTencap()%>" style="border-radius:4px; height: 22px;" readonly="readonly"> 
					  		</td>
						</tr>
						
						<tr>
					  		<td class="plainlabel">Quản lý cấp</td> 
					  		<TD class="plainlabel" colspan="3">
				                  <select name="quanlycapId" id="quanlycapId" class="select2" style="width: 200px" onChange="submitform();" readonly="readonly" >
				                  	<option value=""> </option>
		                        	<%
		                        		if(quanlycapRs != null)
		                        		{
		                        			try
		                        			{
		                        			while(quanlycapRs.next())
		                        			{  
		                        			if( quanlycapRs.getString("pk_seq").equals(cnBean.getQuanlycapId())){ %>
		                        				<option value="<%= quanlycapRs.getString("pk_seq") %>" selected="selected" ><%= quanlycapRs.getString("ten")%></option>
		                        			<%}else { %>
		                        				<option value="<%= quanlycapRs.getString("pk_seq") %>" ><%= quanlycapRs.getString("ten") %></option>
		                        		 <% } } quanlycapRs.close();} catch(SQLException ex){}
		                        		}
		                        	%>
		                        </select>
		               		 </TD> 		     
						</tr>
						
						<tr>
					  		<td class="plainlabel">Email quản lý cấp </td> <td class="plainlabel"> 
					  			<input type = "text" name = "email" value="<%=cnBean.getTEN()%>" style="border-radius:4px; height: 22px;" readonly="readonly"> 
					  		</td>
						</tr>
						
					</table>
					
					<TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px">
							<TR class="tbheader">
		                        <TH align="center" width="10%"> Tên Nhân viên</TH>
		                        <TH align="left" width="50%"> Chọn </TH>    
	                   		</TR>
	                    
                  		<% 	int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							if(nhanvienchonRs !=null)
							{
							 	while(nhanvienchonRs.next())
							 	{ 
								 	if (m % 2 != 0) {%>
										<TR class=<%=lightrow%> align="left">
									<%} else { %>
										<TR class=<%= darkrow%> align="left">
									<%} %>
											<TH width="20%"><%=nhanvienchonRs.getString("Ten")%></TH>
											<TH width="10%"><input type="checkbox" name="nv_chon"
												value="<%=nhanvienchonRs.getString("pk_seq")%>" checked></TH>
										</TR>
									<% m++; 
								}
							}
							
                             if(nhanvienRs !=null)
                             {
						 		while(nhanvienRs.next())
						 		{
							 		if (m % 2 != 0) { %>
										<TR class=<%=lightrow%> align="left">
									<%} else { %>
										<TR class=<%= darkrow%> align="left">
									<%} %>
										<TH width="20%"><%=nhanvienRs.getString("Ten")%></TH>
										<TH width="10%"><input type="checkbox" name="nv_chon"
											value="<%=nhanvienRs.getString("pk_seq")%>"></TH>
										</TR>
									<% m++; 
                                } 
						 	  }%>

								<TR>
									<TD align="center" colspan="10" class="plainlabel">&nbsp;</TD>
								</TR>
				   </TABLE>
				</fieldset>
				</td>
			</tr>
</table>				
</td></tr>
</table></form>
</body>
</html>
<%
	cnBean.DBClose();
	} %>
