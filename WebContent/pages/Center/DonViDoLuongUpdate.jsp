<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.donvidoluong.IDonvidoluong" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IDonvidoluong dvdlBean = (IDonvidoluong)session.getAttribute("dvdlBean"); %>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<SCRIPT language="javascript" type="text/javascript">
function submitform()
{
    document.forms["dvdlForm"].submit();
}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dvdlForm" method="post" action="../../DonvidoluongUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="dvdlId" type="hidden" value='<%=dvdlBean.getId() %>' size="30">

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">
							   		&nbsp;Dữ liệu nền > &nbsp;Sản phẩm &gt; Đơn vị đo lường > Cập nhật </TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;  </TD> 
						    </tr>
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../DonvidoluongSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD >&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "4" cellspacing = "0" border = "0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
			    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" style="width: 100%" readonly="readonly" rows="1" ><%=dvdlBean.getMessage()%></textarea>
								<%dvdlBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>

				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin đơn vị đo lường.
								</LEGEND>
								<TABLE  width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD class="plainlabel" >Đơn vị đo lường <FONT class="erroralert">*</FONT></TD>
									  <TD class="plainlabel" ><INPUT name="dvdl" type="text" size="20" value='<%=dvdlBean.getDonvi() %>'></TD>
								 	  <TD class="plainlabel" >Diễn giải </TD>
									  <TD class="plainlabel" ><INPUT name="diengiai" type="text" size="20" value='<%=dvdlBean.getDiengiai() %>'></TD>
								  </TR>
									
									<TR>
										<TD class="plainlabel" colspan="4" ><label>
											<% if (dvdlBean.getTrangthai().equals("1")){ %>
										  		<input name="trangthai" type="checkbox" value = "1" checked >
										  	<%}else{ %>
										  		<input name="trangthai" type="checkbox" >
										  	<%} %>
									    	Hoạt động</label></TD>
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
	dvdlBean.DBClose();
	}%>