<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.mokhaibaotrungbay.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("MokhaibaotrungbaySvl","");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IMokhaibaotrungbay mkbtbBean = (IMokhaibaotrungbay)session.getAttribute("mkbtbBean"); %>
<% String schemeId = (String)mkbtbBean.getSchemeId(); %>
<% String nppId = (String)mkbtbBean.getNppId(); %>
<% ResultSet scheme = (ResultSet)mkbtbBean.getScheme() ; %>
<% ResultSet npp = (ResultSet)mkbtbBean.getNpp(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
    	document.forms['mkbtbForm'].submit();
	}	

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="mkbtbForm" method="post" action="../../MokhaibaotrungbaySvl" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="1" height="100%">
	<TR>
		<TD align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD>				
					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
			  				<TR height="22">
				   				<TD align="left" class="tbnavigation">
				   					&nbsp;<%=url %></TD>
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
			
   				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= mkbtbBean.getMsg() %></textarea>
				</FIELDSET>
				</TD>
			 </tr> 
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Mở khai báo trưng bày</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel">Chọn chương trình</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="schemeId" onChange=submitform()>
								    <option value=""></option>
								      <% try{
								    	  if(scheme != null){
								    	  	while(scheme.next()){ 
								    	  		if(scheme.getString("pk_seq").equals(schemeId)){%>
								    	  			<option value='<%=scheme.getString("pk_seq")%>' selected><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
									 	<%	  	}else{  %> 								    	  	
								     			<option value='<%=scheme.getString("pk_seq")%>'><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
								     		<%	}
								    	  	}
								    	  }
								      }catch(java.sql.SQLException e){} %>	  
                        				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Chọn Chi nhánh / Đối tác</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="nppId" >
								    <option value=""></option>
								      <% try{
								    	  if(npp != null){
								    	  	while(npp.next()){ 
								    	  		if(npp.getString("pk_seq").equals(nppId)){ 	  %>
								      				<option value='<%=npp.getString("pk_seq")%>' selected><%= npp.getString("ten")%></option>
								      		<%	}else{ %>
								      				<option value='<%=npp.getString("pk_seq")%>' ><%= npp.getString("ten")%></option>
								       		<%	}
								    	  	}
								    	  }
								      }catch(java.sql.SQLException e){} %>	  
                        			</SELECT>						  	  	
						  	  	
						  	  	
						  	  	</TD>
						  </TR>

 						  <TR>							  	
 						   <TD><a class="button2" href="javascript:submitform();">
							<img style="top: -4px;" src="../images/button.png" alt="">Thực hiện</a>&nbsp;&nbsp;&nbsp;&nbsp;
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
<%}%>