<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.congdung.*"%>
<%@page import="geso.traphaco.erp.beans.congdung.imp.*"%>
<%@ page  import = "geso.traphaco.center.util.*" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpCongDung cd = (IErpCongDung) session.getAttribute("cd");
	ResultSet rsNCC = cd.getCongDungRs();
	ResultSet rsTaiKhoan = cd.getTaiKhoanRs();
	int[] quyen = new  int[5];
	 quyen = util.Getquyen("ErpCongDungSvl","",userId);

	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Cong dụng</title>
<link rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script language="javascript" type="text/javascript">
	function xoaform() {
		document.cdListForm.Ma.value = "";
		document.cdListForm.Ten.value = "";
		document.cdListForm.TaiKhoan.value = "";
		document.cdListForm.submit();
	}

	function submitform() {
		document.forms['cdListForm'].action.value = "search";
		document.forms['cdListForm'].submit();
	}

	function newform() {
		document.forms['cdListForm'].action.value = 'New';
		document.forms['cdListForm'].submit();
	}
	</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
 </script>		
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="cdListForm" method="post" action="../../ErpCongDungSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="userTen" value='<%=userTen%>'> 
		<input type="hidden" name="action" value='1'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">Quản lý tài sản  &gt;  Công dụng tài sản</td>
										<td align="right" colspan="2" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td>
								<table border="0" width="100%" cellpadding="0" cellspacing="0">
									<tr>
										<td width="100%" align="center">
											<fieldset>
												<legend class="legendtitle">Tiêu chí tìm kiếm &nbsp;</legend>
												<table width="100%" cellpadding="6" cellspacing="0">
													<tr>
														<td class="plainlabel" width="15%">Mã công dụng</td>
														<td class="plainlabel"><input type="text" name ="Ma" value = "<%= cd.getMa() %>" onChange="submitform();"/>
																											
													</tr>
													<tr>
														<td class="plainlabel" width="15%">Tên công dụng</td>
													<td class="plainlabel"><input type="text" name ="Ten" value = "<%= cd.getTen() %>" onChange="submitform();"/>
													</tr>
													<tr>
														<td class="plainlabel">Thuộc tài khoản kế toán</td>
														<TD class="plainlabel"><select name="TaiKhoan" onchange="submitform();"  style= "width:20% " >
																<option value=""></option>
																<%if (rsTaiKhoan != null)
				while (rsTaiKhoan.next()) {
					if (cd.getTaiKhoan().equals(
							rsTaiKhoan.getString("PK_SEQ"))) {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>" selected="selected"><%=rsTaiKhoan.getString("Ten")%></option>
																<%} else {%>
																<option value="<%=rsTaiKhoan.getString("PK_SEQ")%>"><%=rsTaiKhoan.getString("Ten")%></option>
																<%}
				}%>
														</select></td>
													</tr>
													
													<tr>
														<td colspan="2" class="plainlabel">
														<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
														</a></td>
													</tr>
												</table>
											</fieldset>
										</td>
									</tr>
								</table>
							</td>
						</tr>
						<tr>
							<td width="100%">
								<fieldset>
									<legend class="legendtitle">Công dụng tài sản &nbsp;&nbsp;&nbsp;&nbsp;
									
									<%if(quyen[0]!=0){ %>
							
										<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
										</a>
										<%} %>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="9%" align = "center">Mã</th>
														<th width="15%" align = "center">Tên công dụng</th>
														<th width="15%" align = "center">Tài khoản</th>
														<th width="15%" align = "center">Trạng thái</th>
														<th width="9%" align = "center">Ngày tạo</th>
														<th width="9%" align = "center">Người tạo</th>
														<th width="9%" align = "center">Ngày sửa</th>
														<th width="9%" align = "center">Người sửa</th>
														<th width="7%" align="center">&nbsp;Tác vụ</th>
													</tr>
													<%int m = 0;
			String lightrow = "tblightrow";
			String darkrow = "tbdarkrow";
			if (rsNCC != null)
				while (rsNCC.next()) {
					String trangthai = rsNCC.getString("TrangThai");
					if (trangthai.equals("0"))
						trangthai = "Ngưng hoạt động";
					else
						trangthai = "Hoạt động";
					if (m % 2 != 0) {%>
													<tr class=<%=lightrow%>>
														<%} else {%>
													
													<tr class=<%=darkrow%>>
														<%}%>
														<td align="center"><%=rsNCC.getString("Ma")%></td>
														<td align="center"><%=rsNCC.getString("Ten")%></td>
														<td align="center"><%=rsNCC.getString("TaiKhoan")%></td>
														<td align="center"><%=trangthai%></td>
														<td align="center"><%=rsNCC.getString("NgayTao")%></td>
														<td align="center"><%=rsNCC.getString("NguoiTao")%></td>
														<td align="center"><%=rsNCC.getString("NgaySua")%></td>
														<td align="center"><%=rsNCC.getString("NguoiSua")%></td>
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td> 
																			<%if(quyen[2]!=0){ %>
																	<a href="../../ErpCongDungUpdateSvl?userid=<%=userId%>&update=<%=rsNCC.getString("PK_SEQ")%>"><img
																			src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A> 
																			<%} %>
																			</td>
																	<td>&nbsp;&nbsp;</td>
																	<td>
																	<%if(quyen[1]!=0){ %>
																	<a href="../../ErpCongDungSvl?userid=<%=userId%>&delete=<%=rsNCC.getString("PK_SEQ")%>"><img
																			src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																			onclick="if(!confirm('Bạn muốn xóa công dụng này?')) return false;"> </a> 
																			<%} %>
																			</td>
																</tr>
															</table>
													</tr>
													<%m++;
				}%>
													<tr>
														<td height="26" colspan="11" align="center" class="tbfooter">&nbsp;</td>
													</tr>
												</table>
											</td>
										</tr>
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
	try{
		if(rsNCC != null) rsNCC.close();
		if(rsTaiKhoan != null) rsTaiKhoan.close();
	} catch (Exception ex)
	{
		ex.printStackTrace();
	}finally{
		cd.DBClose();
	}
}
%>
