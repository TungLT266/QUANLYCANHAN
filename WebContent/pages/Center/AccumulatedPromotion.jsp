<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<%	String userId =  (String)session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String baoloi = (String) session.getAttribute("baoloi");
	
	ResultSet ctkmIds = (ResultSet)session.getAttribute("ctkmIds");
	String ctkmId = (String)session.getAttribute("ctkmTL"); 
		
	Utility util = (Utility) session.getAttribute("util"); 
 /*	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ */%>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/simplecalendar.js"></script>
<SCRIPT language="javascript" type="text/javascript">

function submitform()
{
	if(document.getElementById("ctkmId").value == '' )
	{
		alert('Vui lòng chọn chương trình khuyến mại.');
		return;
	}
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].action.value="Taomoi";
	document.forms['rpForm'].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../AccumulatedPromotion">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">Quản lý khuyến mãi &#62; Báo cáo &gt; Trả khuyến mãi tích lũy</TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=baoloi%></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Chọn chương trình</LEGEND>
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
				             	<TR>
                					<TD class="plainlabel"  width="10%">Chương trình </TD>
                					<TD class="plainlabel" >
                    					<select name="ctkmId" id="ctkmId" >
                       						<option value = ''> </option>
                            				<% if(ctkmIds != null){
					  								try{ 
					  									while(ctkmIds.next()){ 
		    												if(ctkmIds.getString("pk_seq").equals(ctkmId)){ %>
		      													<option value='<%=ctkmIds.getString("pk_seq")%>' selected><%=ctkmIds.getString("scheme") %></option>
		      												<%}else{ %>
		      													<option value='<%=ctkmIds.getString("pk_seq")%>'><%=ctkmIds.getString("scheme") %></option>
		      												<%}
		    												} 
					  									ctkmIds.close(); 
		    										}catch(java.sql.SQLException e){} 
		    									}%>
                     					</select>
                					</TD>
           					 	</TR>	

							    <TR>
									<TD colspan="2" class="plainlabel">
									<a class="button2" href="javascript:submitform()" >
										<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    
									</TD>
								</TR>
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
try{
	if(ctkmIds!=null){
		ctkmIds.close();
	}
	
}catch(Exception er){
	
}
	%>