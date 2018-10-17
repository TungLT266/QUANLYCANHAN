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

<% IMokhaibaochitieu mkbctBean = (IMokhaibaochitieu)session.getAttribute("mkbctBean"); %>
<% String year = (String)mkbctBean.getYear(); %>
<% String month = (String)mkbctBean.getMonth(); %>
<% String nppId = (String)mkbctBean.getNppId(); %>
<% String dvkdId = (String)mkbctBean.getDvkdId(); %>
<% ResultSet npp = (ResultSet)mkbctBean.getNpp(); %>
<% ResultSet dvkd = (ResultSet)mkbctBean.getDvkd(); %>

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
		document.forms['mkbctBean'].action.value = 'nosubmit';
    	document.forms['mkbctBean'].submit();
	}	

	function execute()
	{
		document.forms['mkbctBean'].action.value = '1';
    	document.forms['mkbctBean'].submit();
	}	

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="mkbctBean" method="post" action="../../MokhaibaochitieuSvl" >
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
				   					&nbsp;Quản lý chỉ tiêu &gt; Mở khai báo chỉ tiêu</TD>
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
			
   				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= mkbctBean.getMsg() %></textarea>
				</FIELDSET>
				</TD>
			 </tr> 
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Mở khai báo chỉ tiêu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel">Chọn năm</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="year">
								    <option value="<%=year%>"><%= year %></option>
                       				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD width="15%" class="plainlabel">Chọn tháng</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="month" onChange=submitform(); readonly>
									<%for(int i = 1; i <= 12; i++){ 
										if(("" + i).equals(month)){ 	%>																		
								    		<option value='<%=i%>' selected><%= i %></option>
								    <%	}else{ %>
								    		<option value='<%= i%>'><%= i %></option>
									<%} 
									}
									%>
                       				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>
						  <TR>
							  	<TD class="plainlabel">Chọn Đơn vị Kinh doanh</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="dvkdId" onChange=submitform();>
								    <option value=""></option>
								      <% try{
								    	  if(dvkd != null){
								    	  	while(dvkd.next()){ 
								    	  		if(dvkd.getString("pk_seq").equals(dvkdId)){ 	  %>
								      				<option value='<%=dvkd.getString("pk_seq")%>' selected><%= dvkd.getString("donvikinhdoanh")%></option>
								      		<%	}else{ %>
								      				<option value='<%=dvkd.getString("pk_seq")%>' ><%= dvkd.getString("donvikinhdoanh")%></option>
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
 						   <TD><a class="button2" href="javascript:execute();">
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
<%
	if(npp != null) npp.close();
	if(dvkd != null) dvkd.close();
	mkbctBean.DBClose();
}%>