<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.loaitaikhoan.IErpLoaiTaiKhoan"%>
<%@page import="geso.traphaco.erp.beans.loaitaikhoan.imp.ErpLoaiTaiKhoan"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
	IErpLoaiTaiKhoan ltk = (IErpLoaiTaiKhoan) session.getAttribute("ltk");
	ResultSet Rsltk = ltk.getRsLoaiTaiKhoan();
	 int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpLoaiTaiKhoanSvl","",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Style-Type" content="text/css">
<title>Loại tài khoản kế toán</title>
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script language="javascript" type="text/javascript">
	function xoaform() {
		document.LoaiTaiKhoanForm.Ma.value = "";
		document.LoaiTaiKhoanForm.Ten.value = "";
		Search();
	}

	function Search() {
		document.forms['LoaiTaiKhoanForm'].action.value = 'Search';
		document.forms['LoaiTaiKhoanForm'].submit();
	}

	function newform() {
		document.forms['LoaiTaiKhoanForm'].action.value = 'New';
		document.forms['LoaiTaiKhoanForm'].submit();

	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="LoaiTaiKhoanForm" method="post" action="../../ErpLoaiTaiKhoanSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="action" value='1'>
		<input type="hidden" name="chixem" value='<%= ltk.getChixem() %>'>
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Kế toán &gt; Loại tài khoản </td>
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
														<td class="plainlabel">Mã tắt</td>
														<TD class="plainlabel"><input type="text" name="Ma" value='<%=ltk.getMa()%>' onchange="Search()"></td>
													</tr>
													<tr>
														<td class="plainlabel">Tên</td>
														<TD class="plainlabel"><input type="text" name="Ten" value='<%=ltk.getTen()%>' onchange="Search()"></td>
													</tr>
													<tr>
														<td colspan="2" class="plainlabel">
															<a class="button2" href="javascript:xoaform()"> <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
															</a>
														</td>
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
									<legend class="legendtitle">
										&nbsp;Loại tài khoản  &nbsp;&nbsp;&nbsp; 
										<%if(quyen[0]!=0 && ltk.getChixem().equals("0") ){ %>
										 <a class="button3" href="javascript:newform()"> <img style="top: -4px;"
											src="../images/New.png" alt="">Tạo mới
										</a>
										<%} %>
									</legend>
									<table width="100%" border="0" cellpadding="0" cellspacing="0">
										<tr>
											<td align="left" colspan="4">
												<table width="100%" border="0" cellspacing="1" cellpadding="4">
													<tr class="tbheader">
														<th width="9%" align="center">Mã</th>
														<th width="20%" align="center">Tên</th>
														<th width="10%" align="center">Trạng thái</th>
														<th width="9%" align="center">Ngày tạo</th>
														<th width="9%" align="center">Người tạo</th>
														<th width="9%" align="center">Ngày sửa</th>
														<th width="9%" align="center">Người sửa</th>
														<th width="7%" align="center">&nbsp;Tác vụ</th>
													</tr>
													<%
														int m = 0;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														if (Rsltk != null)
															while (Rsltk.next()) {
																String trangthai = Rsltk.getString("TrangThai");
																if (trangthai.equals("0"))
																	trangthai = "Ngưng hoạt động";
																else
																	trangthai = "Hoạt động";
																if (m % 2 != 0) {
													%>
													<tr class=<%=lightrow%>>
														<%
															} else {
														%>
													
													<tr class=<%=darkrow%>>
														<%
															}
														%>
														<td align="center"><%=Rsltk.getString("Ma")%></td>
														<td align="left"><%=Rsltk.getString("Ten")%></td>
														<td align="center"><%=trangthai%></td>
														<td align="center"><%=Rsltk.getString("NgayTao")%></td>
														<td align="center"><%=Rsltk.getString("NguoiTao")%></td>
														<td align="center"><%=Rsltk.getString("NgaySua")%></td>
														<td align="center"><%=Rsltk.getString("NguoiSua")%></td>
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td> 
																		<%if(quyen[2]!=0 && ltk.getChixem().equals("0") ){ %>
																	   <a href="../../ErpLoaiTaiKhoanUpdateSvl?userid=<%=userId%>&update=<%=Rsltk.getString("PK_SEQ")%>"><img
																		title="Cập nhật"  src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp;
																		<%} %>
																	</td>
																	<td>   
																		<%if(quyen[1]!=0 && ltk.getChixem().equals("0") ){ %>
																        <a href="../../ErpLoaiTaiKhoanSvl?userid=<%=userId%>&delete=<%=Rsltk.getString("PK_SEQ")%>"><img
																		title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																		onclick="if(!confirm('Bạn muốn xóa loại tài khoản  này?')) return false;"> </a>&nbsp;  
																		<%} %>
																	</td>
																	<td> 
																	<!-- QUYEN VIEW DLN --> 
																	   <a href="../../ErpLoaiTaiKhoanUpdateSvl?userid=<%=userId%>&display=<%=Rsltk.getString("PK_SEQ")%>">
																	   <img title="Hiển thị" src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																	<!-- END QUYEN VIEW DLN --> 
																	</td>
																</tr>
															</table>
													</tr>
													<%
														m++;
															}
													%>
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
	if(Rsltk != null) Rsltk.close();
	ltk.closeDB();
	session.setAttribute("ltk",null);
}%>`
