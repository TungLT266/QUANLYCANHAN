<%@page import="geso.traphaco.erp.beans.doituongkyquy.IErpDoiTuongKyQuy"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpDoiTuongKyQuy lspBean = (IErpDoiTuongKyQuy) session.getAttribute("lspBean");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	ResultSet rsTaiKhoan = lspBean.getTaiKhoanRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Loại sản phẩm >> Cập nhật</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript">
	function Save() {
		var msg = document.getElementById("msg");
		msg.value=CheckValid();
		if(msg.value==''){
		document.forms['lspForm'].action.value = 'Update';
		document.forms['lspForm'].submit();
		}else
			{
			return;
			}
			
	}
	function Submit() {
		document.forms['lspForm'].submit();
	}
	function CheckValid() {
		
		var taikhoan = document.getElementById("taikhoan");
		var ma = document.getElementById("ma");
		var ten = document.getElementById("ten");
		if (ma.value == "") {
			ma.focus();
			return 'Vui lòng nhập mã loại';
		}
		if (ten.value == "") {
			ten.focus();
			return 'Vui lòng nhập tên loại!';
		}
		if (taikhoan.value == "") {
			taikhoan.focus();
			return 'Vui lòng chọn tài khoản kế toán';
		}
			
return '';
	}
	</script>
	<script>
	function goBack() {
	    window.history.back();
	}
	</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="lspForm" method="post" action="../../ErpDoiTuongKyQuyUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' /> <input type="hidden" name="action" value='1' />
		<input type="hidden" name="id" value="<%=lspBean.getId() %>"/>
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Đối tượng ký quỹ > Hiển thị</TD>
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
										<td width="30" align="left"><a href="javascript:goBack();"> <img src="../images/Back30.png"
												alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td>&nbsp;</td>
									</tr>
								</table>
							</td>
						</tr>
					</table>
					<table width="100%" cellspacing="0" cellpadding="6">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Thông tin đối tượng ký quỹ</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td width="15%" class="plainlabel">Mã đối tượng<font class="erroralert">*</font></td>
											<td class="plainlabel"><input type="text" readonly name="ma" id="ma" value="<%=lspBean.getMa()%>" /></td>
										</tr>
										<tr>
											<td class="plainlabel">Tên đối tượng</td>
											<td class="plainlabel"><input type="text" id="ten" readonly name="ten" value="<%=lspBean.getTen()%>" /></td>
										</tr>
										<TR>
											<TD class="plainlabel">Thuộc tài khoàn kế toán <font class="erroralert">*</font>  </TD>
											<TD class="plainlabel"><select disabled name="taikhoan" id="taikhoan" style= "width:300px">
													<option value=""></option>
													<%
														if (rsTaiKhoan != null)
															while (rsTaiKhoan.next())
															{
																if (lspBean.getTaiKhoan().equals(rsTaiKhoan.getString("PK_SEQ")))
																{
													%>
													<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>" selected="selected"><%= rsTaiKhoan.getString("Ma") + " - " + rsTaiKhoan.getString("Ten")%></option>
													<%
														}
																else
																{
													%>
													<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"><%= rsTaiKhoan.getString("Ma") + " - " + rsTaiKhoan.getString("Ten")%></option>
													<%
														}
															}
													%>
											</select></td>
										</TR>


										<TR>
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel">
												<%
													if (lspBean.getTrangThai().equals("1"))
													{
												%> <input type="checkbox" disabled name="trangthai"
												value="<%=lspBean.getTrangThai()%>" checked="checked"> <%
 	}
 	else
 	{
 %> <input type="checkbox"
												disabled name="trangthai" value="<%=lspBean.getTrangThai()%>"> <%
 	}
 %>
											</td>
										</TR>
									</table>
					</table>
				</td>
			</tr>
		</table>
	</form>
</body>
</html>