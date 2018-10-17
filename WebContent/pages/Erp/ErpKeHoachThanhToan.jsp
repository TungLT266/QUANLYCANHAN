<%@page
	import="geso.traphaco.erp.beans.ThoiHanThanhToan.IerpKeHoachThanhToan"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.util.Calendar"%>
<%@ page import="java.util.Date"%>
<%@ page import="java.sql.SQLException"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	IerpKeHoachThanhToan obj = (IerpKeHoachThanhToan) session.getAttribute("obj");
ResultSet nccRs = (ResultSet)obj.getNccRs();
		ResultSet kehoachRs = obj.getKeHoachRs();
		NumberFormat formater = new DecimalFormat("#,###,###");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<script type="text/javascript" language="JavaScript"
	src="../scripts/Numberformat.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
</script>


<SCRIPT language="javascript" type="text/javascript">
	function submitform() {
		document.forms['khtt'].action.value = 'search';
		document.forms['khtt'].submit();
	}

	function clearform() {
		document.forms['khtt'].tuNgay.value = "";
		document.forms['khtt'].denNgay.value = "";
		document.forms['khtt'].soPO.value = "";
		document.forms['khtt'].ncc.value = "";
		document.forms['khtt'].action.value = 'search';
		document.forms['khtt'].submit();
	}
</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="khtt" method="post" action="../../erpKeHoachThanhToanSvl">
		<input type="hidden" name="userId" value="<%=userId%>"> <input
			type="hidden" name="action" value="1">
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="2">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;
										Quản lý mua hàng > Mua hàng nhập khẩu > Kế hoạch thanh toán</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%></TD>
									</tr>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="center">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Kế hoạch thanh
													toán &nbsp;</LEGEND>

												<TABLE width="100%" cellpadding="6" cellspacing="0">
													<TR>
														<TD width="20%" class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel"><input type="text"
															class="days" id="tuNgay" name="tuNgay"
															value="<%=obj.getTuNgay()%>" onChange="submitform();") >
														</TD>


													</TR>
													<TR>
														<TD width="20%" class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel"><input type="text"
															class="days" id="denNgay" name="denNgay"
															value="<%=obj.getDenNgay()%> " onChange="submitform();">
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel" width="20%">Số PO</TD>
														<TD class="plainlabel"><input type="text" id="soPO"
															name="soPO" value="<%=obj.getSoPO()%>" maxlength="10"
															onChange="submitform();" /></TD>
													</TR>
													<%-- <TR>
														<TD class="plainlabel" width="20%">Nhà cung cấp</TD>
														<TD class="plainlabel"><input type="text" id="ncc"
															name="ncc" value="<%=obj.getNCC()%>" maxlength="10"
															onChange="submitform();" /></TD>
													</TR> --%>
													<tr>
														<TD class="plainlabel" valign="middle">Nhà cung cấp</TD>
														<TD class="plainlabel" valign="middle"><select
															name="ncc" onchange="submitform()" style = "width:200px">
																<option value=""></option>
																<%
																	if (nccRs != null) {
																			while (nccRs.next()) {
																				if (nccRs.getString("pk_seq").equals(obj.getNCC())) {
																%>
																<option value="<%=nccRs.getString("pk_seq")%>"
																	selected="selected"><%=nccRs.getString("ten")%></option>
																<%
																	} else {
																%>
																<option value="<%=nccRs.getString("pk_seq")%>"><%=nccRs.getString("ten")%></option>
																<%
																	}
																			}
																			nccRs.close();
																		}
																%>
														</select></TD>
													</tr>
													<TR>
														<TD width="120px" class="plainlabel" colspan="4"><a
															class="button2" href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">
																Nhập lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp;</TD>
													</TR>

													<TR>
														<TD colspan="4">
															<TABLE width="100%" border="0" cellspacing="1"
																cellpadding="4">
																<TR class="tbheader">
																	<TH width="3%" align="center">Số PO</TH>
																	<TH width="27%" align="center">Nhà cung cấp</TH>
																	<TH width="10%" align="center">Ngày thanh toán</TH>
																	<TH width="10%" align="center">Số tiền</TH>
																</TR>
																<%
																	if (kehoachRs != null) {
																			int m = 0;
																			String style = "";
																%>


																<%
																	while (kehoachRs.next()) {

																				if ((m % 2) == 0) {
																%>
																<TR style="<%=style%>" class='tbdarkrow'>
																	<%
																		} else {
																	%>
																
																<TR style="<%=style%>" class='tblightrow'>
																	<%
																		}
																	%>



																	<td><%=kehoachRs.getString("sopo")%></td>
																	<td><%=kehoachRs.getString("ten") == null ? "" : kehoachRs.getString("ten")%></td>
																	<td><%=kehoachRs.getString("ngaythanhtoan")%></td>
																	<td style="text-align: right;"><%=formater.format(kehoachRs.getDouble("sotien"))%></td>
																</TR>

																<%
																	m++;
																			}
																			kehoachRs.close();
																%>

																<TR class="tbfooter">
																	<TD align="center" colspan="15">&nbsp;</TD>
																	<%
																		}
																	%>
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
				</TD>
			</TR>
		</TABLE>
	</form>
</BODY>
</html>
<%
	try {
			if (kehoachRs != null)
				kehoachRs.close();

			if (obj != null) {
				obj.dbClose();
				obj = null;
			}
			session.setAttribute("obj", null);
		} catch (Exception e) {
		}
%>
<%
	}
%>