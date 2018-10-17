<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.donvikinhdoanh.IDonvikinhdoanh" %>
<%@ page  import = "geso.traphaco.center.beans.nhacungcap.INhacungcap" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% IDonvikinhdoanh dvkdBean = (IDonvikinhdoanh)session.getAttribute("dvkdBean"); 
   ResultSet ncclist = (ResultSet)dvkdBean.getNccListByDvkd(dvkdBean.getId());
   ResultSet newncc = (ResultSet)dvkdBean.getNewNcc(dvkdBean.getId());
   %>

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
  
</SCRIPT>
<SCRIPT language="JavaScript" type="text/javascript">
<!--HPB_SCRIPT_CODE_40
//-->
function submitform()
{
    document.forms["dvkdForm"].submit();
}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="dvkdForm" method="post" action="../../DvkdUpdateSvl">
<INPUT name="userId" type="hidden" value='<%=userId %>' size="30">
<INPUT name="dvkdId" type="hidden" value='<%=dvkdBean.getId() %>' size="30">

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
							   		&nbsp;Dữ liệu nền > Cơ bản &gt; Đơn vị kinh doanh> Cập nhật</TD>
							   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %> &nbsp;</td> 
					      </tr>
   
						</TABLE>
					</TD>
				</TR>
			</TABLE>	
			<TABLE width="100%" border="0" cellpadding="3" cellspacing="0">
				<TR ><TD >
						<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR class = "tbdarkrow">
									<TD width="30" align="left"><A href="../../DonvikinhdoanhSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
								    <TD width="2" align="left" ></TD>
								    <TD width="30" align="left" ><A href="javascript: submitform()" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
				</TD></TR>
			</TABLE>
				
			<TABLE width = 100% cellpadding = "3" cellspacing = "0" border = "0">
				  	<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
			    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1" ><%=dvkdBean.getMessage()%></textarea>
								<%dvkdBean.setMessage(""); %>
								</FIELDSET>
						   </TD>
					</tr>			
				  	<tr>
						   <TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin đơn vị kinh doanh
								</LEGEND>
								<TABLE class="tblight" width="100%" cellspacing="0" cellpadding="6">
									<TR>
									  <TD width="15%" class="plainlabel" >Đơn vị kinh doanh <FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT name="dvkd" size="20" type="text" value ='<%=dvkdBean.getDvkd()%>'></TD>
								  	  <TD  class="plainlabel" >Diễn giải </TD>
									  <TD class="plainlabel" ><INPUT name="diengiai" size="60" type="text" value ='<%=dvkdBean.getDiengiai()%>' ></TD>
								  </TR>
									
								  
									<TR>
									  <TD colspan="4" class="plainlabel" >
									  	<label>
									  	<% if (dvkdBean.getTrangthai().equals("1")){ %>
										 		<input name="trangthai" type="checkbox" value="1" checked >
										 <%}else{ %>
										 		<input name="trangthai" type="checkbox" value="0" >
										 <%} %>
									    Hoạt động
									    </label>
									  </TD>
								    </TR>
								</TABLE>
								</FIELDSET>
					</tr>
					<TR>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Chọn nhà cung cấp</LEGEND>
								
								<TABLE class="tblight" width="100%" cellspacing="1" cellpadding="6">								
									<TR>
									<TH width="20%" class="plainlabel" >Nhà cung cấp </TH>
									<TH width="10%" class="plainlabel" >Chọn</TH>
									</TR>
							<%
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								int m = 0;
								while (ncclist.next()){							
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>

									<TD align="left" class="textfont">
										<%=ncclist.getString("ten")%> 
									</TD>
									<TD align="center">
										<% if (ncclist.getString("checked").equals("1")){ %>
											<input name="nccId" type="checkbox" value='<%=ncclist.getString("pk_seq")%>'  checked>
										<%}else{ %>
											<input name="nccId" type="checkbox" value='<%=ncclist.getString("pk_seq")%>' >
										<%} %>
									</TD>
									</TR>
								
								<%
									m++;
								} 
								
								while (newncc.next()){							
									if (m % 2 != 0) {%>						
										<TR class= <%=lightrow%> >
									<%} else {%>
										<TR class= <%= darkrow%> >
									<%}%>

									<TD align="left" class="textfont">
										<%=newncc.getString("ten")%> 
									</TD>
									<TD align="center">
										<input name="nccId" type="checkbox" value='<%=newncc.getString("pk_seq")%>' >
										
									</TD>
									</TR>
								
								<%
									m++;
								} %>
								

								
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
	if (ncclist != null) ncclist.close();
	if (newncc != null) newncc.close(); 
	if(dvkdBean!=null){dvkdBean.DBClose();
	dvkdBean = null;}
	session.setAttribute("dvkdBean", null);	
}
%>