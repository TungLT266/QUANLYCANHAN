<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.phanbotrungbay.IPhanbotrungbay" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IPhanbotrungbay pbtbBean = (IPhanbotrungbay)session.getAttribute("pbtb"); %>
<% String schemeId = (String)session.getAttribute("schemeId"); %>
<% ResultSet scheme = (ResultSet)pbtbBean.getSchemeRS() ; %>
<% ResultSet vung = (ResultSet)pbtbBean.getVung() ; %>
<% ResultSet kv = (ResultSet)pbtbBean.getKv() ; %>
<% ResultSet npp = (ResultSet)pbtbBean.getNpp(); %>
<% Hashtable<String, String> usedPro = (Hashtable<String, String>)pbtbBean.getusedPro(); %>
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
	document.forms['pbtbForm'].setAttribute('enctype', '', 0);
    document.forms['pbtbForm'].submit();
}

function upload()
{
	document.forms['pbtbForm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['pbtbForm'].submit();
}

function save()
{
	
	document.forms['pbtbForm'].setAttribute('enctype', '', 0);
	document.forms['pbtbForm'].action.value='save';
    document.forms['pbtbForm'].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="pbtbForm" ENCTYPE="multipart/form-data" method="post" action="../../PhanbotrungbaySvl" >
<input type="hidden" name="action" value="0">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý trưng bày &gt; Khai báo &gt; Phân bổ suất trưng bày </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="javascript:save();"  ><img src="../images/Save30.png" alt="Quay ve"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset"></A></TD>

							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width :100%" rows="1"><%= pbtbBean.getMessage() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Phân bổ suất trưng bày </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel">Chương trình</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="schemeId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(scheme.next()){ 
								    	if(scheme.getString("pk_seq").equals(pbtbBean.getSchemeId())){ %>
								      		<option value='<%=scheme.getString("pk_seq")%>' selected><%=scheme.getString("ma")+"_"+scheme.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=scheme.getString("pk_seq")%>'><%=scheme.getString("ma")+"_"+scheme.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>


						  <TR>
							  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''></TD>
						  </TR>
						  
 						  <TR>							  	
						  	  	<TD class="plainlabel" colspan="2">
						  	  	<a class="button2" href="javascript:upload()">
	<img style="top: -4px;" src="../images/button.png" alt="">Upload &nbsp;&nbsp;&nbsp;</a>&nbsp;&nbsp;&nbsp;&nbsp;	

						  	  	</TD>
						  </TR>

						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Chi nhánh / Đối tác</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="1">
						  <TR>
						    	<TD class="legendtitle"><B></b>Vùng </TD>
							    <TD class="tblightrow"><SELECT name="vungId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(vung.next()){ 
								    	if(vung.getString("pk_seq").equals(pbtbBean.getVungId())){ %>
								      		<option value='<%=vung.getString("pk_seq")%>' selected><%=vung.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=vung.getString("pk_seq")%>'><%=vung.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
							    
							    
							    <TD class="legendtitle"><B></B>Khu vực </TD>
							    <TD class="tblightrow"><SELECT name="kvId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(kv.next()){ 
								    	if(kv.getString("pk_seq").equals(pbtbBean.getKvId())){ %>
								      		<option value='<%=kv.getString("pk_seq")%>' selected><%=kv.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=kv.getString("pk_seq")%>'><%=kv.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        		
                        				
					      </TR>
						<tr class="tbheader">
						  <TH width="15%" >Mã Đối tác </TH>
						  <TH width="40%" >Tên Chi nhánh / Đối tác</TH>
						  <TH width="15%" >Hạn mức (số suất) </TH>
						  <TH width="15%" >Đã sử dụng</TH>
						  <TH width="15%" >Còn lại</TH>
						  </tr>
						  <%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(npp != null){
									while(npp.next()){ 
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="left"><div align="center"><%= npp.getString("ma")%></div></TD>                                   
											<TD align="left"><div align="left"><%=npp.getString("ten") %></div></TD>                                   
							  				<TD align="right">
							  					<input type="text"  name="phanbo"  value=  "<%=formatter.format(Long.valueOf(npp.getString("ngansach")).longValue()) %>" />
							  					<input type="hidden"  name="nppId"  value=  "<%= npp.getString("pk_seq")%>" />
							  					<input type="hidden"  name="nppTen"  value=  "<%= npp.getString("ten")%>" />
							  				</TD>
							  			<%if(usedPro.containsKey(npp.getString("pk_seq"))){ %>
							  				<TD align="right">
							  					<input type="text"  name="sudung"  value=  "<%= formatter.format(Math.round(Float.valueOf(usedPro.get(npp.getString("pk_seq"))).floatValue())) %>" readonly="readonly" />
							  				</TD>
							  				<TD align="left"><div align="right"><%= formatter.format(Math.round(Double.valueOf(npp.getString("ngansach")).doubleValue() - Double.valueOf(usedPro.get(npp.getString("pk_seq"))).doubleValue())) %></div></TD>
							  			<%}else{ %>	
							  				<TD align="left">  <input type="text"  name="sudung"  value=  "0"  readonly="readonly"/>   </TD>
							  				<TD align="left"><div align="right"><%= formatter.format(Math.round(Float.valueOf(npp.getString("ngansach")).floatValue())) %></div></TD>
							  			<%} %>
							  			</TR>
							  
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>

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