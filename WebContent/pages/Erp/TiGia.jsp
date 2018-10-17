<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="javax.naming.spi.DirStateFactory.Result"%>
<%@page import="geso.traphaco.center.db.sql.dbutils"%>
<%@page import="geso.traphaco.erp.beans.tigia.*"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	ITigiaList tigia = (ITigiaList) session.getAttribute("tgList");
%>
<%
	ResultSet tienteList = tigia.getTienteList();
%>
<%
	ResultSet tigiaList = tigia.getTigiaList();
%>
<%
	NumberFormat formatter = new DecimalFormat("#,###,###.#####");
int[] quyen = new  int[5];
quyen = util.Getquyen("TigiaSvl","",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});

      });   
</script>
<script type="text/javascript">
	function clearform() {
		document.tigiaForm.TuNgay.value = "";
		document.tigiaForm.DenNgay.value = "";
		document.tigiaForm.TyGiaQuyDoi.value = "";
		document.tigiaForm.loaitien.selectedIndex = -1;
		document.tigiaForm.trangthai.selectedIndex = 2;
		Submitform();
	}
	function Submitform() {
		document.forms["tigiaForm"].action.value = "search";
		document.forms["tigiaForm"].submit();
	}
	function TaoMoi() {
		document.forms["tigiaForm"].action.value = "new";
		document.forms["tigiaForm"].
submit();
	}
</script>
</head>
<body>
	<form name="tigiaForm" method="post" action="../../TigiaSvl">
		<input type="hidden" name="action">
		<input type="hidden" name="chixem" value='<%= tigia.getChixem() %>'>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top'>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền > Mua hàng > Tỉ giá</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">Tiêu chí tìm kiếm </LEGEND>
												<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
													<TR>
														<TD class="plainlabel">Từ Ngày</TD>
														<TD class="plainlabel"><input type="text" class="days" id="TuNgay" name="TuNgay" size="20" value="<%=tigia.getTuNgay()%>"
															onchange="Submitform()"></TD>
													</TR>
													
													<TR>
														<TD class="plainlabel">Đến Ngày</TD>
														<TD class="plainlabel"><input type="text" class="days" id="DenNgay" name="DenNgay" size="20" value="<%=tigia.getDenNgay()%>"
															onchange="Submitform()"></TD>
													</TR>
													
													<TR>
														<TD class="plainlabel">Tỉ giá quy đổi</TD>
														<TD class="plainlabel"><input type="text" class="txtsearch" id="TyGiaQuyDoi" name="TyGiaQuyDoi" size="20" value="<%=tigia.getTIGIAQUYDOI()%>"
															onchange="Submitform()"></TD>
													</TR>
													<TR>
														<TD width="20%" class="plainlabel">Chọn loại tiền</TD>
														<TD width="80%" class="plainlabel"><select id="loaitien" name="loaitien" class="txtsearch" onchange="Submitform()">
																<option></option>
																<%
																	if (tienteList != null) {
																		try {
																			while (tienteList.next()) {
																				if (tigia.getTIENTE_FK().equals(
																						tienteList.getString("pk_seq"))) {
																%>
																<option value="<%=tienteList.getString("pk_seq")%>" selected="selected"><%=tienteList.getString("ten")%></option>
																<%
																	} else {
																%>
																<option value="<%=tienteList.getString("pk_seq")%>"><%=tienteList.getString("ten")%></option>
																<%
																	}
																			}
																			tienteList.close();
																		} catch (SQLException ex) {
																		}
																	}
																%>
														</select></TD>
													</TR>
													<TR>
														<TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel"><select name="trangthai" onchange="Submitform()">
																<%
																	if (tigia.getTRANGTHAI().equals("1")) {
																%>
																<option value="1" selected>Hoạt động</option>
																<option value="0">Ngưng hoạt động</option>
																<option value="2"></option>
																<%
																	} else if (tigia.getTRANGTHAI().equals("0")) {
																%>
																<option value="0" selected>Ngưng hoạt động</option>
																<option value="1">Hoạt động</option>
																<option value="2"></option>
																<%
																	} else {
																%>
																<option value="1">Hoạt động</option>
																<option value="0">Ngưng hoạt động</option>
																<option value="2" selected></option>
																<%
																	}
																%>
														</select></TD>
													</TR>
													<TR>
														<TD class="plainlabel" colspan=2><a class="button2" href="javascript:clearform()"> <img style="top: -4px;"
																src="../images/button.png" alt="">Nhập lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
													</TR>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<FIELDSET>
									<LEGEND class="legendtitle">
										&nbsp;Tỉ giá&nbsp;&nbsp;&nbsp;  
										
											<%if(quyen[0]!=0 && tigia.getChixem().equals("0") ){ %>
											<a class="button3" href="javascript:TaoMoi()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
											</a>
											<%} %>
									</LEGEND>
									<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR>
											<TD width="98%">
												<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
													<TR class="tbheader" align = "center">
														<TH>Công ty</TH>
														<TH>Loại tiền</TH>
														<TH>Từ Ngày</TH>
														<TH>Đến Ngày</TH>
														<TH>Tỷ giá</TH>
														<TH>Trạng thái</TH>
														<TH>Người tạo</TH>
														<TH>Người sửa</TH>
														<TH>Ngày tạo</TH>
														<TH>Ngày sửa</TH>
														<TH>Tác vụ</TH>
													</TR>
													<%
														if (tigiaList != null) {
															try {
																int m = 0;
																String lightrow = "tblightrow";
																String darkrow = "tbdarkrow";
																while (tigiaList.next()) {
																	if (m % 2 != 0) {
													%>
													<TR class=<%=lightrow%>>
														<%
															} else {
														%>
													
													<TR class=<%=darkrow%>>
														<%
															}
														%>
														<TD align="center"><%=tigiaList.getString("MACTY")%></TD>
														<TD align="center"><%=tigiaList.getString("TENTIEN")%></TD>
														<TD align="center"><%=tigiaList.getString("TuNgay")%></TD>
														<TD align="center"><%=tigiaList.getString("DenNgay")%></TD>
														<TD align="center"><%=formatter.format(Double.parseDouble(tigiaList
								.getString("TIGIAQUYDOI")))%></TD>
														<%
															String tt = tigiaList.getString("TRANGTHAI");
																		if (tt.equals("1")) {
														%>
														<TD align="center">Hoạt động</TD>
														<%
															} else {
														%>
														<TD align="center">Không hoạt động</TD>
														<%
															}
														%>
														<TD align="center"><%=tigiaList.getString("TENNV")%></TD>
														<TD align="center"><%=tigiaList.getString("TENNVS")%></TD>
														<TD align="center"><%=tigiaList.getString("NGAYTAO")%></TD>
														<TD align="center"><%=tigiaList.getString("NGAYSUA")%></TD>
														<TD align="center">
															<TABLE border="0" cellpadding="0" cellspacing="0">
																<TR>
																	<%if(quyen[0]!=0 && tigia.getChixem().equals("0") ){ %>
																	<TD> 
																		<A href="../../TigiaUpdateSvl?task=capnhat&id=<%=tigiaList.getString("PK_SEQ")%>"> <img src="../images/Edit20.png"
																		title="Cập nhật" alt="Cap nhat" width="20" height="20" longdesc="Cap nhat" border=0></A>&nbsp; 
																	</TD>
																	<%} %>
																	<%if(quyen[1]!=0 && tigia.getChixem().equals("0") ){ %>
																	<TD>
																		<A href="../../TigiaSvl?task=xoa&id=<%= tigiaList.getString("PK_SEQ")%>")>
																			<img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa" border=0 onclick="return confirm('Bạn có muốn xóa tỷ giá này không ?');">
																		</A>&nbsp;
																	</TD>
																	<%} %>
																	<!-- QUYEN VIEW DLN -->
																	<TD> 
																		<A href="../../TigiaUpdateSvl?task=display&id=<%=tigiaList.getString("PK_SEQ")%>"> <img src="../images/Display20.png" title="Hiển thị" alt="Hien thi" width="20" height="20" longdesc="Hien thi" border = 0></A>
																	</TD>
																	<!-- END QUYEN VIEW DLN --> 
																	<TD>&nbsp;</TD>
																</TR>
															</TABLE>
														</TD>
													</TR>
													<%
														m++;
																}
																tigiaList.close();
															} catch (SQLException ex) {
																System.out.print(ex.toString());
															}
														}
													%>
													<TR>
														<TD height="" colspan="11"  align="center" class="tbfooter">&nbsp;</TD>
													</TR>
												</TABLE>
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
	</form>
</body>
</html>
<%

	tigia.closeDB();
}
%>
