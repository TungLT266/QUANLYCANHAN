<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.nhomchiphi.IChiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.nhomchiphi.imp.Chiphi" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	IChiphi ncpBean = (IChiphi)session.getAttribute("ncpBean"); 
	ResultSet dvttlist = ncpBean.getDvttList();
	ResultSet tklist = ncpBean.getTkList();	
	ResultSet ttcplist = ncpBean.getTtcpList();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Khoản mục chi phí</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["ncpForm"].submit();
}

function saveform()
{
	document.forms["ncpForm"].action.value = "save";
	document.forms["ncpForm"].submit();
}

</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ncpForm" method="post" action="../../Erp_ChiphiUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="ncpId" value='<%= ncpBean.getId()  %>'>
<input type="hidden" name="action" value="new">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Khoản mục chi phí &gt; Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../Erp_ChiphiSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100%" readonly="readonly" rows="1"><%=ncpBean.getMsg() %></textarea>
						<% ncpBean.setMsg("") ; %>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thông tin khoản mục chi phí</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

							<TR>
								<TD width="29%" class="plainlabel" >Tên khoản mục chi phí<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT type="text" name="ten" style="width:300px" value='<%= ncpBean.getTen()%>'></TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width:300px" value='<%= ncpBean.getDiengiai() %>'></TD>
						  </TR>

						  	<TR>
						   		<TD class="plainlabel">Đơn vị / Phòng ban thực hiện</TD>
						  	  	<TD class="plainlabel">
						  			<SELECT  name="dvttId" style=" width: 250px" >
						  					<option value=""  ></option>
						  				<%
						  					if(ncpBean.getCtyId().length()>0 & dvttlist != null){
											try{%>
												
											<% 	while (dvttlist.next()){%>
													
													<%	if(dvttlist.getString("DVTTID").equals(ncpBean.getDvttId())){ %>											
															
															<option value= <%=dvttlist.getString("DVTTID")%> selected><%= dvttlist.getString("DVTTTEN") %></option>															
														<%}else{%>
															<option value= <%=dvttlist.getString("DVTTID")%> ><%= dvttlist.getString("DVTTTEN") %></option>																																		
														<%	}
												}
															
											}catch(java.sql.SQLException e){
												e.printStackTrace();
											}
						  					}
											%>													
																											                                           
                           			</SELECT>
						  	  	</TD>
						  	</TR>
						  
						  <TR>
						   		<TD class="plainlabel">Tài khoản kế toán</TD>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="tkId" onChange = "submitform();" style=" width: 250px">												
														<option value=""  ></option>												
											<% 	if(tklist != null){
												try{
													while (tklist.next()){%>
																
													<%	if(tklist.getString("MA").trim().equals(ncpBean.getTkId())){ %>											
														<option value= <%=tklist.getString("MA")%> selected><%= tklist.getString("MA") + " - " + tklist.getString("TEN") %></option>															
													  <%}else{%>
														<option value= <%=tklist.getString("MA")%> ><%= tklist.getString("MA")  + " - " +  tklist.getString("TEN") %></option>																																		
													  <%}
													}
															
													}catch(java.sql.SQLException e){
														e.printStackTrace();
													}				
												}
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  </TR>
						  <TR>
						   		<TD class="plainlabel">Trung tâm chi phí</TD>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="ttcpId" style=" width: 250px" >												
														<option value=""  ></option>												
											<% 	try{
													while (ttcplist.next()){%>
																
													<%	if(ttcplist.getString("TTCPID").equals(ncpBean.getTtcpId())){ %>											
														<option value= <%=ttcplist.getString("TTCPID")%> selected><%= ttcplist.getString("MA") + " - " + ttcplist.getString("DIENGIAI") %></option>															
													  <%}else{%>
														<option value= <%=ttcplist.getString("TTCPID")%> ><%= ttcplist.getString("MA")  + " - " +  ttcplist.getString("DIENGIAI") %></option>																																		
													  <%}
													}
															
													}catch(java.sql.SQLException e){
													}														
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  </TR>
						  
						 
						<TR>
							<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" checked>
							      Hoạt động </TD>
							<TD class="plainlabel">&nbsp;</TD>
						</TR>
						 	<TR>
							<TD class="plainlabel"><input name="kmdacbiet" type="checkbox" value="1" checked>
							      Khoản mục chi phí đặt biệt </TD>
							<TD class="plainlabel">&nbsp;</TD>
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
<%  if(dvttlist != null) dvttlist.close();
	if(tklist != null) tklist.close(); 
	if(ttcplist != null) ttcplist.close();
	ncpBean.DBClose();
}%>