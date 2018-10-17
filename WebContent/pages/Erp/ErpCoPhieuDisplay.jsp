<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.cophieu.IErpCoPhieu" %>
<%@ page  import = "geso.traphaco.erp.beans.cophieu.imp.*" %>
<%@ page  import = "java.sql.ResultSet"%>
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
	IErpCoPhieu nhBean =(ErpCoPhieu)session.getAttribute("nhBean");
%>
<script type="text/javascript">
	function goBack(){
		window.history.back();
	}
</script>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">

<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="nhForm" method="post" action="../../ErpCoPhieuUpdateSvl">
<input type="hidden" value="save" name="action">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type = "hidden" value = "<%=nhBean.getID() %>" name = "id">
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"	>
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#ffffff">
			<TABLE width="100%" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">

					   	<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Cơ bản &gt; Danh mục cổ phiếu &gt; Hiển thị
							   </TD>
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
									<TD width="30" align="left"> <A href="javascript:goBack();" > <img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"> </A></TD>
									<TD>&nbsp; </TD>						
								</TR>
						</TABLE>
					</TD>
				</TR>
										  	
									
				<TR>
						   	<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
								<LEGEND class="legendtitle">Thông tin cổ phiếu
								</LEGEND>
								<TABLE width="100%" cellspacing="0" cellpadding="6">
									<TR>		
									  <TD width="15%" class="plainlabel">Mã cổ phiếu<FONT class="erroralert">*</FONT></TD>
									  <TD  class="plainlabel" ><INPUT type = "text" name="Ma" size="40"
										type="text" value="<%=nhBean.getMA() %>" >  
										
										</TD>
										
										
								    </TR>
	
									<tr>
									<td class="plainlabel">Tên cổ phiếu<font class="erroralert">*</font></td> <td class = "plainlabel"> <input type = "text" readonly name = "Ten" value="<%=nhBean.getTEN() %>"> </td>
									</tr>
									
									<tr>
									<td class="plainlabel">Công ty phát hành cổ phiếu<font class="erroralert">*</font></td> <td class = "plainlabel"> <input type = "text" readonly name = "congtyphathanh" value="<%=nhBean.getCongTyPhatHanh() %>"> </td>
									</tr>
									
									<TR>
									<TD class = "plainlabel">
									Hoạt động
									</TD>
									<TD class="plainlabel">
									<%
								
									if(nhBean.gettrangthai().equals("1")){ %>
										<input name="hoatdong" checked="checked" type="checkbox" disabled value="1">
									<%}else{ %>
										<input name="hoatdong" type="checkbox" disabled value="1" >
									<%} %>
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
</FORM>

</BODY>
</HTML>
<%
	nhBean.DBClose();
	}
%>