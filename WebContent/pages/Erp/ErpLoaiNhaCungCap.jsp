<%@page import="java.sql.SQLException"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.loainhacungcap.IErpLoaiNhaCungCap"%>
<%@page import="geso.traphaco.erp.beans.loainhacungcap.imp.ErpLoaiNhaCungCap"%>
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
	IErpLoaiNhaCungCap lncc = (IErpLoaiNhaCungCap) session.getAttribute("lncc");
	ResultSet Rslncc = lncc.getRsLoaiNhaCungCap();
	 int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpLoaiNhaCungCapSvl","",userId);
	
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
		document.LoaiNhaCungCapForm.Ma.value = "";
		document.LoaiNhaCungCapForm.Ten.value = "";
		Search();
	}

	function Search() {
		document.forms['LoaiNhaCungCapForm'].action.value = 'Search';
		document.forms['LoaiNhaCungCapForm'].submit();
	}

	function newform() {
		document.forms['LoaiNhaCungCapForm'].action.value = 'New';
		document.forms['LoaiNhaCungCapForm'].submit();

	}
	function msg(){
		if (!msg.equal("")){
			alert(msg);
		}
	}
	
	function thongbao()
	{
		if(document.getElementById("msg").value != ''&& document.getElementById("msg").value!=null)
		{alert(document.getElementById("msg").value);
		}
	}
</script>
</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="LoaiNhaCungCapForm" method="post" action="../../ErpLoaiNhaCungCapSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="userTen" value='<%=userTen%>'> <input
			type="hidden" name="action" value='1'>
			
			<input type="hidden" id="msg" value="<%=lncc.getMsg() %>">
			<input type="hidden" name="chixem" value='<%= lncc.getChixem() %>'>
			
			<script language="javascript" type="text/javascript">
			    thongbao();
			</script> 
			
		<table width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<tr>
				<td colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<table width="100%" cellpadding="0" cellspacing="1">
						<tr>
							<td align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<td align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Mua hàng &gt; Loại nhà cung cấp </td>
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
														<TD class="plainlabel"><input type="text" name="Ma" value='<%=lncc.getMa()%>' onchange="Search()"></td>
													</tr>
													<tr>
														<td class="plainlabel">Tên</td>
														<TD class="plainlabel"><input type="text" name="Ten" value='<%=lncc.getTen()%>' onchange="Search()"></td>
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
										&nbsp;Loại nhà cung cấp  &nbsp;&nbsp;&nbsp; 
										<%if(quyen[0]!=0 ){ %>
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
														if (Rslncc != null)
															while (Rslncc.next()) {
																String trangthai = Rslncc.getString("TrangThai");
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
														<td align="center"><%=Rslncc.getString("Ma")%></td>
														<td align="left"><%=Rslncc.getString("Ten")%></td>
														<td align="center"><%=trangthai%></td>
														<td align="center"><%=Rslncc.getString("NgayTao")%></td>
														<td align="center"><%=Rslncc.getString("NguoiTao")%></td>
														<td align="center"><%=Rslncc.getString("NgaySua")%></td>
														<td align="center"><%=Rslncc.getString("NguoiSua")%></td>
														<td align="center">
															<table border=0 cellpadding="0" cellspacing="0">
																<tr>
																	<td> 
																		<%if(quyen[2]!=0 ){ %>
																	   <a href="../../ErpLoaiNhaCungCapUpdateSvl?userid=<%=userId%>&update=<%=Rslncc.getString("PK_SEQ")%>"><img
																		title="Cập nhật" src="../images/Edit20.png" alt="Chinh sua" width="20" height="20" longdesc="Chinh sua" border=0></A>&nbsp;
																		<%} %>
																	</td>
																	<td>   
																		<%if(quyen[1]!=0  ){ %>
																        <a href="../../ErpLoaiNhaCungCapSvl?userid=<%=userId%>&delete=<%=Rslncc.getString("PK_SEQ")%>"><img
																		title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0
																		onclick="if(!confirm('Bạn muốn xóa loại nhà cung cấp này?')) return false;"> </a>&nbsp;  
																		<%} %>
																	</td>
																	<!-- QUYEN VIEW DLN -->
																	<td> 
																	   <a href="../../ErpLoaiNhaCungCapUpdateSvl?userid=<%=userId%>&display=<%=Rslncc.getString("PK_SEQ")%>"><img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																	</td>
																	<!-- END QUYEN VIEW DLN --> 
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
	if(Rslncc != null) Rslncc.close();
	lncc.closeDB();
	session.setAttribute("lncc",null);
}%>`
