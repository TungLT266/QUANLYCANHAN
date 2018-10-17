<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.erp.beans.khuvuckho.*"%>
<%@ page import="geso.traphaco.erp.beans.khuvuckho.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpViTriKho   kvkBean = (ErpViTriKho) session.getAttribute("kvkBean");
	ResultSet rsKho = kvkBean.getKhoRs();
	ResultSet rsKhuvuc = kvkBean.getKhuVucKhoRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Định vị trí</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript">
	function Save() 
	{
		document.forms['cdForm'].action.value = 'Update';
		document.forms['cdForm'].submit();	
	}
	
	function Submit()
	{
		document.forms['cdForm'].action.value = 'submit';
		document.forms['cdForm'].submit();
	}
	
	</script>
	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="cdForm" method="post" action="../../ErpViTriKhoUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' />
		<input type="hidden" name="Id" value='<%= kvkBean.getId() %>' />  
		<input type="hidden" name="action" value='1' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kho vận &gt; Khai báo vị trí kho &gt; Cập nhật</TD>
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
										<td width="30" align="left"><a href="/TraphacoHYERP/ErpViTriKhoSvl"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
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
					<table width="100%" cellspacing="0" cellpadding="6">
						<tr>
							<td align="left" colspan="4" class="legendtitle">
								<fieldset>
									<legend class="legendtitle">Thông báo </legend>
									<textarea id="msg" name="dataerror" style="background-color: white; width: 100%;color: red" readonly="readonly" rows="1"><%=kvkBean.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Vị trí kho</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
											<tr>
												<td class="plainlabel">Mã vị trí <font class="erroralert">*</font></td>
												<td class="plainlabel"><input type="text" name ="ma" value="<%=kvkBean.getMa()%>" id="ma"/>
											
											</tr>
											<tr>
												<td class="plainlabel">Tên vị trí <font class="erroralert">*</font></td>
											<td class="plainlabel"><input type="text" name ="ten" value="<%=kvkBean.getDienGiai()%>" id="ten"/>
											</tr>
											<tr>
												<td class="plainlabel">Thuộc kho <font class="erroralert">*</font></td>
												<TD class="plainlabel">
													<select name="KhoId" onchange="Submit()"  class="select" style="width: 400px;" >
														<option value=""></option>
														<%if (rsKho != null)
														while (rsKho.next()) {
															if (kvkBean.getKhoId().equals(
																rsKho.getString("PK_SEQ"))) {%>
																<option value="<%=rsKho.getString("PK_SEQ")%>" selected="selected"><%=rsKho.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsKho.getString("PK_SEQ")%>"><%=rsKho.getString("Ten")%></option>
																<%}
														}%>
													</select>
												</td>
											</tr>
											
											<tr>
												<td class="plainlabel">Thuộc khu vực <font class="erroralert">*</font></td>
												<TD class="plainlabel">
													<select name="KhuvucId" class="select" style="width: 400px;" >
														<option value=""></option>
														<%if (rsKhuvuc != null) {
														while (rsKhuvuc.next()) {
															if (kvkBean.getKhuvucId().equals(rsKhuvuc.getString("PK_SEQ"))) {%>
																<option value="<%=rsKhuvuc.getString("PK_SEQ")%>" selected="selected"><%=rsKhuvuc.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsKhuvuc.getString("PK_SEQ")%>"><%=rsKhuvuc.getString("Ten")%></option>
																<%}
														} rsKhuvuc.close(); } %>
													</select>
												</td>
											</tr>
													
											<TR>
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel">
												<input type="checkbox" name="trangthai" value="1" checked="checked">
											</td>
										</TR>
									</table>
									</fieldset>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>
<%
	try{
		if( rsKho != null ) rsKho.close();
		if (kvkBean != null) kvkBean.Close();
		session.removeAttribute("kvkBean");
		session.setAttribute("kvkBean", null) ; 
		
	}catch (Exception ex)
	{
		ex.printStackTrace();
	}
}
%>