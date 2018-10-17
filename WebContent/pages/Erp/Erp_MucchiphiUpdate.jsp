<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.mucchiphi.IMucchiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.mucchiphi.IMucchiphiList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IMucchiphi mcpBean = (IMucchiphi)session.getAttribute("mcpBean"); %>
<%
	ResultSet ctylist = (ResultSet)mcpBean.getCtyList();
	ResultSet dvthlist = (ResultSet)mcpBean.getDvthList() ;
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">


<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});            
        }); 		
		
</script>


<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
	 document.mcpForm.action.value = "update";
   	 document.forms["mcpForm"].submit();
}

function searchform()
{
	 document.mcpForm.action.value = "search";
   	 document.forms["mcpForm"].submit();
}


</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="mcpForm" method="post" action="../../Erp_MucchiphiUpdateSvl">
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="Id" value='<%= mcpBean.getId() %>'>
<input type="hidden" name="action" value="1">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
				   		<table width="100%" border="0" cellpadding="0" cellspacing="0">
					  		<tr height="22">
						  		<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng  &gt; Mức chi phí  &gt; Cập nhật</TD> 
						  		<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD>
						  	</tr>
						</table></TD>
			  	</TR>
			</TABLE>
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR>
					<TD>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
					<TR class = "tbdarkrow">
						<TD width="30" align="left"> <a href="../../Erp_mucchiphiSvl?userId=<%=userId%>" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </A></TD>
					    <TD width="2" align="left" ></TD>
						<TD width="30" align="left" ><a href="javascript: submitform()" ><img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
						<TD>&nbsp;</TD>						
					</tr>
					</TABLE>
					</TD>
				</TR>
			</TABLE>								  			
			
			<TABLE width="100%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Mức chi phí &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="0" cellpadding="6">
								
								<TR>
									<TD width="19%" class="plainlabel">Số tiền từ </TD>
								    <TD width="81%" class="plainlabel">
										<INPUT name="sotientu" type="text" size="40" value='<%= mcpBean.getSotientu() %>' >
									</TD>
																		
								</TR>
								<TR>
									<TD width="19%" class="plainlabel">Số tiền đến </TD>
								    <TD width="81%" class="plainlabel">
										<INPUT name="sotienden" type="text" size="40" value='<%= mcpBean.getSotienden() %>'>
									</TD>
																		
								</TR>

						      	<TR>
									<TD width="19%" class="plainlabel">Công ty </TD>
  								  	<TD class="plainlabel" >
										<SELECT  name="ctyId" onChange="searchform();">												
											<option value=""  ></option>
												
										<% 	if(ctylist != null){
												try{
													while (ctylist.next()){%>															
														<%	if(ctylist.getString("CTYID").equals(mcpBean.getCtyId())){ %>											
															<option value= <%=ctylist.getString("CTYID")%> selected><%= ctylist.getString("TEN") %></option>															
														<%}else{%>
															<option value= <%=ctylist.getString("CTYID")%> ><%= ctylist.getString("TEN") %></option>																																		
														<%}
													}
															
												}catch(java.sql.SQLException e){}
											}														
												%>														                                           
                                        </SELECT>
                                   </TD>
							      	<TR>
										<TD width="19%" class="plainlabel">Hoạt động</TD>
  									  	<TD class="plainlabel" >
	  								  		<%if(mcpBean.getTrangthai().equals("1")){ %>
												<INPUT NAME = "trangthai" TYPE = "checkbox" VALUE = "1" checked="checked">
											<%}else{ %>
												<INPUT NAME = "trangthai" TYPE = "checkbox" VALUE = "1" >
											<%} %>
                                       </TD>
									</TR>
							</TABLE>
						</FIELDSET>
					</TD>
				</TR>							  	
			</TABLE>
			<TABLE width="50%" border="0" cellpadding="0">
				<TR>
					<TD >
						<FIELDSET><LEGEND class="legendtitle">&nbsp;Đơn vị thực hiện &nbsp;</LEGEND>
							<TABLE  width="100%" cellspacing="1" cellpadding="5">
								<TR class="tbheader">									
									<TH  > Đơn vị thực hiện </TH>
									<TH width="10%">Chọn </TH>
								</TR>
						<% 
							int m = 0;
							String lightrow = "tblightrow";
							String darkrow = "tbdarkrow";
							try{
								if(dvthlist != null){
								while (dvthlist.next()){
								if (m % 2 != 0) {%>						
								<TR class= <%=lightrow%> >
								<%} else {%>
								<TR class= <%= darkrow%> >
								<%} %>										
									<TD  ><%= dvthlist.getString("DVTH")%></TD>
  								  	<TD  align="center">
  								  		<%if(dvthlist.getString("CHON").equals("1")){ %>
										<INPUT NAME = "dvthIds" TYPE = "checkbox" VALUE = <%= dvthlist.getString("DVTHID")%> checked="checked">
										<%}else{ %>
										<INPUT NAME = "dvthIds" TYPE = "checkbox" VALUE = <%= dvthlist.getString("DVTHID")%> >
										<%} %>
									</TD>
								</TR>
							<%m++; }}
							}catch(java.sql.SQLException e){}%>
                            
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
 	if( ctylist != null)  ctylist.close();
 	if( dvthlist != null) dvthlist.close();
	mcpBean.DBClose(); 
} %>
	