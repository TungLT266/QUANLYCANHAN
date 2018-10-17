 <!-- DỮ LIỆU LINK TỪ DMS QUA  -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.kenhbanhang.IKenhbanhang" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IKenhbanhang kbhBean = (IKenhbanhang)session.getAttribute("kbhBean"); %>
<% ResultSet htbhRs = (ResultSet)kbhBean.getHtbhRs(); %>
<% ResultSet nkRs = (ResultSet)kbhBean.getNkRs(); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<SCRIPT language="javascript" type="text/javascript">
 function confirmLogout(){
    if(confirm("Ban co muon dang xuat?"))
    {
		top.location.href = "home.jsp";
    }
    return
  }
 function submitform()
 {
     document.forms['kbhForm'].submit();
 }
 function saveform()
 {
	 var kbhTen = document.getElementById('kbh');
	 var diengiai = document.getElementById('diengiai');
	 if(kbhTen.value == "")
	 {
		 alert('Vui Long Nhap Ten Kenh Ban Hang');
		 return;
	 }
	 if(diengiai.value == "")
	 {
		 alert('Vui Long Nhap Dien Giai');
		 return;
	 }	
 	 document.forms['kbhForm'].action.value= 'save';
     document.forms['kbhForm'].submit();
 }
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name='kbhForm' method="post" action="../../KenhbanhangUpdateSvl">
<input type="hidden" name="action" value='1'>
<INPUT name="userId" type="hidden" value='<%= userId %>' size="30">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   	Thiết lập dữ liệu nền > Dữ liệu nền kinh doanh > Kênh bán hàng> tạo mới </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn<%=userTen %>&nbsp;  </TD> 
						    </tr>
   
						   	<tr>
						   		<TD align="left" height="5" colspan="4" class=""></td>
   
  							</tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../KenhbanhangSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: saveform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
			  	<TR>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
		    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%= kbhBean.getMessage() %></textarea>
						</FIELDSET>
				   </TD>
				</TR>
				
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin kênh bán hàng
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel" >Kênh bán hàng <FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="kenhbanhang" id="kbh"
										type="text" value='<%= kbhBean.getKenhbanhang() %>' size="20"></TD>
								  </TR>
									<TR>
									  <TD class="plainlabel" >Diễn giải </TD>
									  <TD class="plainlabel" >
									  	<INPUT name="diengiai" id="diengiai" type="text" value='<%= kbhBean.getDiengiai() %>' size="80"></TD>
								  </TR>
									
								  <%-- <TR>
										<TD class="plainlabel" >Hệ thống bán hàng </TD>
										<TD class="plainlabel" >
											<SELECT name="htbhId" id="Hethongbanhang" style="width: 200px;" >
								    		<option value=""></option>
								      		<% if(htbhRs != null) 
								      		try{while(htbhRs.next()){ 
								    			if(htbhRs.getString("pk_seq").trim().equals(kbhBean.getHtbhId().trim())){ %>
								      			<option value='<%=htbhRs.getString("pk_seq")%>' selected><%=htbhRs.getString("ten") %></option>
								      			<%}else{ %>
								     			<option value='<%=htbhRs.getString("pk_seq")%>'><%=htbhRs.getString("ten") %></option>
								     		<%}}}catch(java.sql.SQLException e){} %>	  
                        					</SELECT>	
                        				</TD>
										</TR>
									<TR>
										<TD class="plainlabel" >Nhóm kênh <FONT class="erroralert">*</FONT></TD>
										<TD class="plainlabel" >
											<SELECT name="nkId" id="Nhomkenh" class="select2" style="width: 200px;" multiple>
								    		<option value=""></option>
								      		<% if(nkRs != null) 
								      		try{while(nkRs.next()){ 
								    			if(nkRs.getString("pk_seq").trim().equals(kbhBean.getNkId().trim())){ %>
								      			<option value='<%=nkRs.getString("pk_seq")%>' selected><%=nkRs.getString("ten") %></option>
								      			<%}else{ %>
								     			<option value='<%=nkRs.getString("pk_seq")%>'><%=nkRs.getString("ten") %></option>
								     		<%}}}catch(java.sql.SQLException e){} %>	  
                        					</SELECT>	
                        				</TD>
										</TR> --%>
										
									<TR>
									  <TD  class="plainlabel" ><label>
										<%  if (kbhBean.getTrangthai().equals("1")){%>
										  	<input name="trangthai" type="checkbox" value ="1" checked>
										<%} else {%>
											<input name="trangthai" type="checkbox" value ="0">
										<%} %>
									    Hoạt động</label></TD>
										<TD  class="plainlabel" >&nbsp;</TD>
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
	kbhBean.DBClose();
	}%>