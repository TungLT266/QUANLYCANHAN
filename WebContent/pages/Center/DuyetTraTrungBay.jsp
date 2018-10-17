<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.duyettratrungbay.IDuyettratrungbay" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DecimalFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IDuyettratrungbay dttbBean = (IDuyettratrungbay)session.getAttribute("dttbBean"); %>
<% String schemeId = (String)session.getAttribute("schemeId"); %>
<% ResultSet scheme = (ResultSet)dttbBean.getSchemeRS() ; %>
<% ResultSet vung = (ResultSet)dttbBean.getVung() ; %>
<% ResultSet kv = (ResultSet)dttbBean.getKv() ; %>
<% ResultSet npp = (ResultSet)dttbBean.getNpp(); %>
<% ResultSet kh = (ResultSet)dttbBean.getKh() ; %>
<% Hashtable<String, String> usedPro = (Hashtable<String, String>)dttbBean.getusedPro(); %>
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
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function save()
{
	document.dttbForm.action.value='save';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function chot()
{
	document.dttbForm.action.value='chot';
	document.forms['dttbForm'].setAttribute('enctype', '', 0);
    document.forms['dttbForm'].submit();
}

function upload()
{
	document.forms['dttbForm'].setAttribute('enctype', "multipart/form-data", 0);
    document.forms['dttbForm'].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="dttbForm" ENCTYPE="multipart/form-data" method="post" action="../../DuyettratrungbaySvl" >
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
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý trưng bày &gt; Khai báo &gt; Duyệt trả trưng bày </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left">
								<%  if( !dttbBean.getDaduyet().equals("1") ) { %>
									<A href="javascript:save();"  >
										<img src="../images/Save30.png" alt="Luu lai"  title="Luu lai" border="1" longdesc="Luu lai" style="border-style:outset">
									</A>
								<% } %>
							</TD>

 							<TD width="2">&nbsp; </TD> 
	 							<% if(!dttbBean.getDaduyet().equals("1")){ %>
		 							<TD width="30" align="left"><A href="javascript:chot();"  >
		 								<img src="../images/Chot.png" alt="Duyet"  title="Duyet" border="1" longdesc="Duyet" style="border-style:outset"></A>
		 							</TD> 
	 							<% } %>
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
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" rows="1"><%= dttbBean.getMessage() %></textarea>

						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Duyệt trả trưng bày</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD width="15%" class="plainlabel">Chương trình</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="schemeId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(scheme.next()){ 
								    	if(scheme.getString("pk_seq").equals(dttbBean.getSchemeId())){ %>
								      		<option value='<%=scheme.getString("pk_seq")%>' selected><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=scheme.getString("pk_seq")%>'><%=scheme.getString("scheme") + "_" + scheme.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Lần thanh toán</TD>
						  	  	<TD class="plainlabel">
									<SELECT name="lantt" onChange = "submitform();">
									<option value="0"></option>
								    <% 
								      	int n = dttbBean.getSolantt();
								      	for (int i=1; i <= n; i++)
								        { 
								    	if(i == dttbBean.getLantt()){ %>
								      		<option value='<%=i%>' selected><%="Lần thanh toán thứ " + i %></option>
								      	<%}else{ %>
								     		<option value='<%=i%>' ><%="Lần thanh toán thứ " + i %></option>
								     	<%}}%>
								    	
                        				</SELECT>						  	  	
						  	  	
						  	  	</TD>
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Trạng thái</TD>
							 <% if(dttbBean.getTrangthai().length()==0){ %>
						  	  	<TD class="plainlabel">chưa có đề nghị trả trưng bày </TD>
						  	 <%} %>
							 <% if(dttbBean.getTrangthai().equals("0")){ %>
						  	  	<TD class="plainlabel">Chưa duyệt đề nghị trả trưng bày</TD>
						  	 <%} %> 	
							 <% if(dttbBean.getTrangthai().equals("1")){ %>
						  	  	<TD class="plainlabel">Đã duyệt đề nghị trả trưng bày</TD>
						  	 <%} %> 	
						  	  	
						  </TR>

						  <TR>
							  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel"><INPUT type="file" name="filename" size="40" value=''></TD>
						  </TR>
						  
 						  <TR>							  	
 						   <TD><a class="button2" href="javascript:upload();">
							<img style="top: -4px;" src="../images/button.png" alt="">Cập nhật</a>&nbsp;&nbsp;&nbsp;&nbsp;
 						  </TD>
						 </TR>

						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Khách hàng </LEGEND>
						<TABLE border="0" width="100%" cellpadding="1" cellspacing="1">
						  <TR>
						    	<TD class="legendtitle"><B></b>Khu Vực </TD>
							    <TD class="tblightrow"><SELECT name="vungId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(vung.next()){ 
								    	if(vung.getString("pk_seq").equals(dttbBean.getVungId())){ %>
								      		<option value='<%=vung.getString("pk_seq")%>' selected><%=vung.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=vung.getString("pk_seq")%>'><%=vung.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
						</TR>
						<TR>
							    
							    <TD class="legendtitle"><B></B>Khu Vực </TD>
							    <TD class="tblightrow"><SELECT name="kvId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(kv.next()){ 
								    	if(kv.getString("pk_seq").equals(dttbBean.getKvId())){ %>
								      		<option value='<%=kv.getString("pk_seq")%>' selected><%=kv.getString("diengiai") %></option>
								      	<%}else{ %>
								     		<option value='<%=kv.getString("pk_seq")%>'><%=kv.getString("diengiai") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
                        		
                        				
					      </TR>

						  <TR>
						    	<TD class="legendtitle"><B></b>Chi nhánh / Đối tác</TD>
							    <TD class="tblightrow"><SELECT name="nppId" onChange = "submitform();">
								    <option value="0"></option>
								      <% try{while(npp.next()){ 
								    	if(npp.getString("pk_seq").equals(dttbBean.getNppId())){ %>
								      		<option value='<%=npp.getString("pk_seq")%>' selected><%=npp.getString("ten") %></option>
								      	<%}else{ %>
								     		<option value='<%=npp.getString("pk_seq")%>'><%=npp.getString("ten") %></option>
								     	<%}}}catch(java.sql.SQLException e){} %>	  
                        				</SELECT>			
                        		</TD>
							    
					      </TR>
							<tr class="tbheader">
								<TH width="10%" >Mã Đối tác </TH>
								<TH width="25%" >Tên Đối tác </TH>
							  	<TH width="10%" >Mã KH </TH>
							  	<TH width="25%" >Tên khách hàng</TH>
							  	<TH width="10%" >Đăng ký </TH>
							  	<TH width="10%" >Đề nghị</TH>
							  	<TH width="10%" >Duyệt</TH>
						  </tr>
						  <%NumberFormat formatter = new DecimalFormat("#,###,###"); %>
						  <% try{
							    String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								if(kh != null){
									while(kh.next()){ 
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>
											<TD align="left"><div align="left"><%= kh.getString("nppMa")%></div></TD>
											<TD align="left"><div align="left"><%= kh.getString("nppTen")%></div></TD>
											<TD align="left"><div align="left"><%= kh.getString("smartId")%></div></TD>                                   
											<TD align="left"><div align="left"><%=kh.getString("khTen") %></div></TD>    
											                               
							  				<TD align="left"><div align="right"><%=kh.getString("xuatdangky") %></div></TD>							  			
							  				<TD align="left"><div align="right"><%=kh.getString("xuatdenghi") %></div></TD>
							  				<TD align="left"><div align="right">
							  				<INPUT style="width:100%;text-align:right " type="text" name = "<%="duyet" + kh.getString("khId") %>" value="<%= kh.getString("xuatduyet")%>">
							  				</div>
							  				<INPUT type="hidden" name = "<%="dangky" + kh.getString("khId") %>" value="<%= kh.getString("xuatdangky")%>">
											<INPUT type="hidden" name = "khIds" value="<%= kh.getString("khId")%>">	
							  				</TD>
										</TR>
						  		<% m++ ;} 
						  		
						  		}%>		
						  		
						  <%}catch(java.sql.SQLException e){}%>
							<tr class="tbfooter"><td colspan="12" > &nbsp; </td></tr>
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