<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.erp.beans.congdung.*"%>
<%@ page import="geso.traphaco.erp.beans.congdung.imp.*"%>
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
	IErpCongDung cdBean = (ErpCongDung) session.getAttribute("cdBean");
	ResultSet rsTaiKhoan = cdBean.getTaiKhoanRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Công dụng >> Tạo mới</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript">
	function Save() {
		var msg = document.getElementById("msg");
		msg.value=CheckValid();
		if(msg.value==''){
		document.forms['cdForm'].action.value = 'save';
		document.forms['cdForm'].submit();
		}else
			{
			return;
			}
			
	}
	function Submit() {
		document.forms['cdForm'].submit();
	}
function CheckValid() {
		
		var taikhoan = document.getElementById("taikhoan");
		var ma = document.getElementById("ma");
		var ten = document.getElementById("ten");
		if (ma.value == "") {
			ma.focus();
			return 'Vui lòng nhập mã';
		}
		if (ten.value == "") {
			ten.focus();
			return 'Vui lòng nhập tên !';
		}
		if (taikhoan.value == "") {
			taikhoan.focus();
			return 'Vui lòng chọn tài khoản kế toán';
		}
			
return '';
	}
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="cdForm" method="post" action="../../ErpCongDungUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' />
		 <input type="hidden" name="action" value='1' />
		 <input type="hidden" name="id" value='<%=cdBean.getId() %>' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">Quản lý tài sản > Công dụng tài sản > Cập nhật</TD>
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
										<td width="30" align="left"><a href="/TraphacoHYERP/ErpCongDungSvl"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
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
									<textarea id="msg" name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%=cdBean.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Công dụng</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
											<tr>
												<td class="plainlabel" width="15%">Mã công dụng <font class="erroralert">*</font></td>
												<td class="plainlabel"><input type="text" name ="ma" value="<%=cdBean.getMa()%>" id="ma"/>
													
											</tr>
											
											<tr>
												<td class="plainlabel" width="15%">Tên công dụng <font class="erroralert">*</font></td>
												<td class="plainlabel"><input type="text" name ="ten" value="<%=cdBean.getTen()%>" id="ten"/>
											</tr>
											<tr>
												<td class="plainlabel" width="15%">Tài khoản chi phí nhận khấu hao <font class="erroralert">*</font></td>
												<TD class="plainlabel"><select name="taikhoan" onchange="Submit()" id="taikhoan">
														<option value=""></option>
														<%if (rsTaiKhoan != null)
														while (rsTaiKhoan.next()) {
															if (cdBean.getTaiKhoan().equals(
																	rsTaiKhoan.getString("PK_SEQ"))) {%>
														<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>" selected="selected"><%=rsTaiKhoan.getString("ma") + " - " + rsTaiKhoan.getString("Ten")%></option>
														<%} else {%>
														<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"><%= rsTaiKhoan.getString("ma") + " - " + rsTaiKhoan.getString("Ten")%></option>
														<%}
												}%>
												</select></td>
											</tr>
													
													
										<TR>
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel">
												<%if (cdBean.getTrangThai().equals("1")) {%> <input type="checkbox" name="trangthai" value="<%=cdBean.getTrangThai()%>"
												checked="checked"> <%} else {%> <input type="checkbox" name="trangthai" value="<%=cdBean.getTrangThai()%>"> <%}%>
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
	
	if(rsTaiKhoan != null) rsTaiKhoan.close();
	cdBean.DBClose();
	session.setAttribute("cdBean", null) ; 
	}
%>