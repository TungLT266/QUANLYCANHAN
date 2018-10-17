<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.nhomchiphi.INhomchiphi" %>
<%@ page  import = "geso.traphaco.erp.beans.nhomchiphi.imp.Nhomchiphi" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.Utility" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% INhomchiphi ncpBean = (INhomchiphi)session.getAttribute("ncpBean"); 
   ResultSet tklist = ncpBean.getTkList();
   ResultSet ttcplist = ncpBean.getTtcpList();	
   ResultSet nhomlist = ncpBean.getNhomList();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="ncpForm" method="post" action="../../Erp_NhomchiphiUpdateSvl" >
<input type="hidden" name="userId" value='<%=userId%>'>
<input type="hidden" name="ncpId" value='<%= ncpBean.getId()  %>'>
<input type="hidden" name="ctyId" value='<%= ncpBean.getCtyId()  %>'>
<input type="hidden" name="dvttId" value='<%= ncpBean.getDvttId()  %>'>
<input type="hidden" name="thanhvien" value='<%= ncpBean.getLoai()  %>'>
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF"><!--begin body Dossier-->
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Tài chính  &gt; Nhóm chi phí / Chi phí &gt; Cập nhật</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../Erp_NhomchiphiSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
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
						<LEGEND class="legendtitle" style="color:black">Thông tin nhóm chi phí / chi phí</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  		
						  <TR>
						   		<TD class="plainlabel">Đơn vị / Phòng ban</TD>
						  	  	<TD class="plainlabel">
                                    <INPUT type="text" name="donvi" style="width:300px" value='<%= ncpBean.getDonvi() %>' readonly=readonly>
						  	  	</TD>
						  </TR>

							<TR>
								<TD width="29%" class="plainlabel" >Tên nhóm chi phí<FONT class="erroralert"> *</FONT></TD>
								<TD class="plainlabel"><INPUT type="text" name="ten" style="width:300px" value='<%= ncpBean.getTen()%>'></TD>
							</TR>
							<TR>
							  	<TD class="plainlabel">Diễn giải</TD>
						  	  	<TD class="plainlabel"><INPUT type="text" name="diengiai" style="width:300px" value='<%= ncpBean.getDiengiai() %>'></TD>
						  </TR>
							<TR>
							  <TD class="plainlabel">Loại </TD>
							  <TD width="71%" class="plainlabel">
                              <% if (ncpBean.getLoai().equals("1")){ %>
                                    <INPUT type="text" name="loaicp" style="width:300px" value='Nhóm chi phí' readonly=readonly>
                              <%}else{ %>
                                    <INPUT type="text" name="loaicp" style="width:300px" value='Chi phí' readonly=readonly>                              
							  <%} %>                              
                              </TD>
						  </TR>
						  <TR>
						  		<%if (ncpBean.getLoai().equals("2")){ %>
						   		<TD class="plainlabel">Thuộc về nhóm <FONT class="erroralert"> *</TD>
						   		<%}else{ %>
						   		<TD class="plainlabel">Thuộc về nhóm </TD>
						   		<%} %>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="nhomId" ">												
														<option value=""  ></option>												
											<% 	
												if(nhomlist != null){
												try{
													while (nhomlist.next()){%>
																
													<%	if(nhomlist.getString("NHOMID").equals(ncpBean.getParentId())){ %>											
														<option value= <%=nhomlist.getString("NHOMID")%> selected><%= nhomlist.getString("TENNHOM") %></option>															
													  <%}else{%>
														<option value= <%=nhomlist.getString("NHOMID")%> ><%= nhomlist.getString("TENNHOM") %></option>																																		
													  <%}
													}
															
													}catch(java.sql.SQLException e){
													}				
												}
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  	  	</TD>
						  </TR>

						  				  	  		
						  <%if (ncpBean.getLoai().equals("2")){ %>
						  <TR>
						   		<TD class="plainlabel">Tài khoản kế toán</TD>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="tkId" ">												
														<option value=""  ></option>												
											<% 	if(tklist != null){
												try{
													while (tklist.next()){%>
																
													<%	if(tklist.getString("TKID").equals(ncpBean.getTkId())){ %>											
														<option value= <%=tklist.getString("TKID")%> selected><%= tklist.getString("MA") + " - " + tklist.getString("TEN") %></option>															
													  <%}else{%>
														<option value= <%=tklist.getString("TKID")%> ><%= tklist.getString("MA")  + " - " +  tklist.getString("TEN") %></option>																																		
													  <%}
													}
															
													}catch(java.sql.SQLException e){
													}				
												}
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  	  	</TD>
						  </TR>
						  <TR>
						   		<TD class="plainlabel">Trung tâm chi phí</TD>
								<TD class="plainlabel" >
									<TABLE cellpadding="0" cellspacing="0" border="0">
										<TR>
									  		<TD><SELECT  name="ttcpId" >												
														<option value=""  ></option>												
											<% 	
												if(ttcplist != null){
												try{													
													while (ttcplist.next()){%>
																
													<%	if(ttcplist.getString("TTCPID").equals(ncpBean.getTtcpId())){ %>											
														<option value= <%=ttcplist.getString("TTCPID")%> selected><%= ttcplist.getString("MA") + " - " + ttcplist.getString("DIENGIAI") %></option>															
													  <%}else{%>
														<option value= <%=ttcplist.getString("TTCPID")%> ><%= ttcplist.getString("MA")  + " - " +  ttcplist.getString("DIENGIAI") %></option>																																		
													  <%}
													}
															
													}catch(java.sql.SQLException e){
													}
												}
													%>														                                           
                                            	</SELECT>
                                         	 </TD>
											</TR>
										</TABLE>									
									</TD>
						  	  	</TD>
						  </TR>
						  
						 <%} %>
						 
						<TR>
							<% if(ncpBean.getTrangthai().equals("1")) {%>						
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="1" checked>
						  	<%}else{ %>
						  		<TD class="plainlabel"><input name="trangthai" type="checkbox" value="0" >
						  	<%} %>
							      Hoạt động </TD>
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
<% 	if(tklist != null) tklist.close(); 
	if(ttcplist != null) ttcplist.close();
	if(nhomlist != null) nhomlist.close();
	ncpBean.DBClose();
}%>