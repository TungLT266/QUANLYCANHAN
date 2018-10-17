<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="geso.traphaco.erp.beans.loaisanpham.*"%>
<%@ page import="geso.traphaco.erp.beans.loaisanpham.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpLoaiSanPham lspBean = (ErpLoaiSanPham) session.getAttribute("lspBean");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	ResultSet rsTaiKhoan = lspBean.getTaiKhoanRs();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Loại sản phẩm >> Tạo mới</title>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	function Save() {
		var msg = document.getElementById("msg");
		msg.value=CheckValid();
		if(msg.value==''){
		document.forms['lspForm'].action.value = 'Create';
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
		if(document.getElementById("thuoclsp").value == "1"){
			if (taikhoan.value == "") {
				taikhoan.focus();
				return 'Vui lòng chọn tài khoản kế toán';
			}
		}
		
			
return '';
	}
	
	function hideShowTkkt() {
		if (document.getElementById("thuoclsp").value == "1") {
			document.getElementById("thuoctkkt1").style.display = "";
		} else {
			document.getElementById("thuoctkkt1").style.display = "none";
		}
	}
	
</script>
	
		<link href="../css/select2.css" rel="stylesheet" />
		<script src="../scripts/select2.js"></script>
		<script>
			$(document).ready(function()
			{
				$(".select2").select2();
			});
		</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="lspForm" method="post" action="../../ErpLoaiSanPhamUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>' /> <input type="hidden" name="action" value='1' />
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#ffffff">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Loại sản phẩm > Tạo mới</TD>
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
										<td width="30" align="left"><a href="../../ErpLoaiSanPhamSvl"> <img src="../images/Back30.png"
												alt="Quay ve" title="Quay ve" border="1" longdesc="Quay ve" style="border-style: outset">
										</a></td>
										<td width="2" align="left"></td>
										<td width="30" align="left"><a href="javascript: Save()"><img src="../images/Save30.png"
												title="Luu lai" alt="Luu lai" border="1" style="border-style: outset"></a></td>
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
									<textarea id="msg" name="dataerror" style="width: 100%" readonly="readonly" rows="1"><%=lspBean.getMsg()%></textarea>
								</fieldset>
							</td>
						</tr>
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
									<LEGEND class="legendtitle">Thông tin loại sản phẩm</LEGEND>
									<TABLE width="100%" cellspacing="0" cellpadding="6">
										<tr>
											<td width="15%" class="plainlabel">Thuộc loại sản phẩm</td>
											<td class="plainlabel">
												<select name="thuoclsp" id="thuoclsp" style= "width:300px" class="select2" onchange="hideShowTkkt();">
													<%if(lspBean.getThuoclsp().equals("2")){ %>
														<option value="1">Loại sản phẩm thông thường</option>
														<option selected="selected" value="2">Loại sản phẩm kiểm nghiệm</option>
													<%} else { %>
														<option selected="selected" value="1">Loại sản phẩm thông thường</option>
														<option value="2">Loại sản phẩm kiểm nghiệm</option>
													<%} %>
												</select>
											</td>
										</tr>
										<tr>
											<td width="15%" class="plainlabel">Mã sản phẩm <font class="erroralert">*</font> </td>
											<td class="plainlabel"><input type="text" name="ma" id="ma" value="<%=lspBean.getMa()%>" /></td>
										</tr>
										<tr>
											<td class="plainlabel">Tên sản phẩm</td>
											<td class="plainlabel"><input type="text" id="ten" name="ten" value="<%=lspBean.getTen()%>" /></td>
										</tr>
										<TR <%=lspBean.getThuoclsp().equals("1") ? "" : "style=\"display: none;\"" %> id="thuoctkkt1">
											<TD class="plainlabel">Thuộc tài khoản kế toán <font class="erroralert">*</font>  </TD>
											<TD class="plainlabel"><select name="taikhoan" id="taikhoan" style= "width:300px" class="select2">
													<option value=""></option>
													<%
														if (rsTaiKhoan != null)
															while (rsTaiKhoan.next())
															{
																if (lspBean.getTaiKhoan().equals(rsTaiKhoan.getString("Ma")))
																{
													%>
													<option value="<%=rsTaiKhoan.getString("Ma")%>" selected="selected"><%= rsTaiKhoan.getString("Ma") + " - " + rsTaiKhoan.getString("Ten")%></option>
													<%
														}
																else
																{
													%>
													<option value="<%=rsTaiKhoan.getString("Ma")%>"><%= rsTaiKhoan.getString("Ma") + " - " + rsTaiKhoan.getString("Ten")%></option>
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
												%> <input type="checkbox" name="trangthai"
												value="<%=lspBean.getTrangThai()%>" checked="checked"> <%
 	}
 	else
 	{
 %> <input type="checkbox"
												name="trangthai" value="<%=lspBean.getTrangThai()%>"> <%
 	}
 %>
											</td>
										</TR>
										
										<TR>
											<TD class="plainlabel">Chọn sản xuất</TD>
											<TD class="plainlabel">
												<%if (lspBean.getIsSanxuat().equals("1")){%>
													<input type="checkbox" name="issanxuat" value="1" checked="checked">
												<% } else { %>
													<input type="checkbox" name="issanxuat" value="1">
												<% } %>
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