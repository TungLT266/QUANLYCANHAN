<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.erp.beans.loainhacungcap.*"%>
<%@ page import="geso.traphaco.erp.beans.loainhacungcap.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
IErpLoaiNhaCungCap  lnccBean = (IErpLoaiNhaCungCap) session.getAttribute("lnccBean");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Best - Project</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript">
	

	function Save() {
		
		var Ma= document.getElementById('Ma');
		var Ten= document.getElementById('Ten');
		 if(Ma.value == "")
		 {
			 alert("Vui lòng nhập vào mã loại nhà cung cấp !");
			 Ma.focus();
			 return;
		 }
		 if(Ten.value == "")
		 {
			 alert("Vui lòng nhập vào tên loại nhà cung cấp !");
			 Ten.focus();
			 return;
		 }
		document.forms['lnccForm'].action.value = 'Create';
		document.forms['lnccForm'].submit();
	}
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="lnccForm" method="post" action="../../ErpLoaiNhaCungCapUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' /> 
		<input type="hidden" name="id" value='' />
		<input type="hidden" name="action" value='1' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Loại nhà cung cấp > Tạo mới</TD>
										<td colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%> &nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					
					<table width="100%" border="0" cellpadding="3" cellspacing="0">
						<tr>
							<td>
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr class="tbdarkrow">
										<td width="30" align="left"><a href="/SalesUp/ErpLoaiNhaCungCapSvl"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td width="2" align="left"></td>
										<td width="30" align="left"><a href="javascript: Save()"><img src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1"
												style="border-style: outset"></a></td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="4">
						<tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%= lnccBean.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Loại nhà cung cấp </LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td width="15%" class="plainlabel">Mã tắt<font class="erroralert">*</font></td>
											<td class="plainlabel"><input type="text" name="Ma" id="Ma" size="40" id="Ma" value="<%= lnccBean.getMa()%>"></td>
										</tr>
										<tr>
											<td class="plainlabel">Tên <font class="erroralert">*</font></td>
											<td class="plainlabel"><input type="text" name="Ten"  id="Ten" value="<%= lnccBean.getTen()%>"></td>
										</tr>
								
								<TR>
									<TD class = "plainlabel">Hoạt động</TD>					
									<TD colspan="7" class = "plainlabel">
										<input name="TrangThai" checked="checked" type="checkbox" value="1">
									</TD>											
						</TR>				
								
					</table>
					</fieldset>
				</td>
			</tr>
		</table>
		</td>
		</tr>
		</table>
	</form>
</body>
</html>
<%
lnccBean.closeDB();
}
%>