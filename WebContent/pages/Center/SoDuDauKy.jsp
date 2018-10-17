<%@page import="geso.traphaco.center.beans.sodudauky.ISoDuDauKyList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.center.beans.sodudauky.*"%>
<%@ page import="geso.traphaco.center.util.Erp_Item"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%@ page import="geso.traphaco.center.util.Erp_ListItem"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%
	System.out.print("geso.traphaco.center.beans.sodudauky.ISoDuDauKyList");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoDMS/index.jsp");
	} else {
%>
<%
	ISoDuDauKyList obj = (ISoDuDauKyList) session.getAttribute("obj");
	List<Erp_ListItem> hienThiList = obj.getList();
%>
<%
	int[] quyen = new int[5];
	quyen = util.Getquyen("SoDuDauKySvl","",userId);
%>
<%
	obj.setNextSplittings();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<!-- <script type="text/javascript" src="../scripts/ajax_erp_loadspvattu.js"></script> -->
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid : DXImageTransform.Microsoft.Shadow ( color = gray,
		direction = 135 );
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<script language="JavaScript" type="text/javascript">
	function submitform() {
		document.xdForm.action.value = 'search';
		document.forms["xdForm"].submit();
	}

	function clearform() {
		document.xdForm.chiNhanhId.value = "";
		document.xdForm.soChungTu.value = "";
		document.xdForm.ngayBatDau.value = "";
		document.xdForm.ngayKetThuc.value = "";
		submitform();
	}

	function newform() {
		document.forms['xdForm'].action.value = 'new';
		document.forms['xdForm'].submit();
	}

	function thongbao() {
		console.log("thong bao");
		var tt = document.forms['xdForm'].msg.value;
		if (tt.length > 0)
			alert(document.forms['xdForm'].msg.value);
	}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="xdForm" method="post" action="../../SoDuDauKySvl">
		<input type="hidden" name="userId" value='<%=userId%>'>
		<%
			System.out.println("obj.getMsg(): " + obj.getMsg());
		%>
		<input type="hidden" name="action" value='1'> <input
			type="hidden" id="msg" name="msg" value='<%=obj.getMsg()%>'>
		<input type="hidden" name="crrApprSplitting"
			value="<%=obj.getCrrApprSplitting()%>"> <input type="hidden"
			name="nxtApprSplitting" value="<%=obj.getNxtApprSplitting()%>">

		<script language="JavaScript" type="text/javascript">
		  	thongbao();
		</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản trị hệ thống &gt; Đưa số dư đầu kỳ</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
									<TR>
										<TD width="100%" align="left">
											<FIELDSET>
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm
													&nbsp;</LEGEND>
												<TABLE class="tblight" width="100%" cellpadding="6"
													cellspacing="0">
													<TR>
														<TD width="19%" class="plainlabel">Chi nhánh</TD>
														<TD class="plainlabel" colspan=5>
															<select name="chiNhanhId" style="width: 300px;" onChange="submitform();">
																<option value="0" selected>All</option>
																<%
																	for (Erp_Item item: obj.getChiNhanhList())
																		if (obj.getChiNhanhId().trim().equals(item.getValue())){
																%>
																	<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																<%
																		} else {
																%>
																	<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																<%
																	}
																%>
															</select>
														</TD>

														<TD width="19%" class="plainlabel">Số chứng từ</TD>
														<TD class="plainlabel" colspan=5>
															<INPUT name="soChungTu" type="text" size="30" value='<%=obj.getSoChungTu()%>' onChange="submitform();">
														</TD>
													</TR>
													<TR>
														<TD width="19%" class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel" colspan=5>
															<INPUT name="ngayBatDau" class="days" type="text" size="30" value='<%=obj.getNgayBatDau()%>' onChange="submitform();" readonly="readonly">
														</TD>

														<TD width="19%" class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel" colspan=5>
															<INPUT name="ngayKetThuc" class="days" type="text" size="30" value='<%=obj.getNgayKetThuc()%>' onChange="submitform();" readonly="readonly">
														</TD>
													</TR>
													
													<TR>
														<TD class="plainlabel" colspan="2">
															<a class="button2" href="javascript:clearform()"> 
																<img style="top: -4px;" src="../images/button.png" alt="">
																	Nhập lại 
															</a>&nbsp;&nbsp;&nbsp;&nbsp; 
															<a class="button2" href="javascript:submitform();""> 
																<img style="top: -4px;" src="../images/button.png" alt="">
																	Tìm kiếm 
															</a>
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
												</TABLE>
											</FIELDSET>
										</TD>
									</TR>
								</TABLE>
								<TABLE width="100%" cellpadding="0" cellspacing="1">
									<TR>
										<TD width="100%">
											<FIELDSET>
												<LEGEND class="legendtitle">
													&nbsp;Đưa số dư đầu kỳ &nbsp;&nbsp;

													<%
// 														if (quyen[0] != 0) {
													%>
													<a class="button3" href="javascript:newform()"> 
														<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
													<%
// 														}
													%>
												</LEGEND>
												<TABLE width="100%">
													<TR>
														<TD width="98%">
															<TABLE width="100%" border="0" cellspacing="1"
																cellpadding="2">
																<TR class="tbheader">
																	<TH width="5%">STT</TH>
																	<TH width="10%">Số Id BTTH</TH>
																	<TH width="17%">Số chứng từ BTTH</TH>
																	<TH width="40%">Chi nhánh</TH>
																	<TH width="10%">Ngày ghi nhận</TH>
																	<TH width="10%">Trạng thái</TH>
																	<TH width="8%">Tác vụ</TH>
																</TR>
																<%
																	if (hienThiList != null) {
																		System.out.print("hienThiList: " + hienThiList.size());
																			try {
																				int m = 0;
																				String lightrow = "tblightrow";
																				String darkrow = "tbdarkrow";
																				for (Erp_ListItem hienThi : hienThiList) {
																					String trangThai = "";
																					if (hienThi.getTrangThai().trim().equals("0"))
																						trangThai = "Chưa chốt";
																					else if (hienThi.getTrangThai().trim().equals("1"))
																						trangThai = "Đã chốt";
																					else if (hienThi.getTrangThai().trim().equals("2"))
																						trangThai = "Đã xóa";
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
																	<TD align="left"><%=m + 1%></TD>
																	<TD align="center"><%=hienThi.getId()%></TD>
																	<TD align="left"><%=hienThi.getMa()%></TD>
																	<TD align="left"><%=hienThi.getTen()%></TD>
																	<TD align="center"><%=hienThi.getNgayTao()%></TD>
																	<TD align="center"><%=trangThai%></TD>
																	<TD align="center">
																	<%if (quyen[1] != 0 && hienThi.getTrangThai().trim().equals("0")) {%> 
																		<A href="../../SoDuDauKySvl?userId=<%=userId%>&delete=<%=hienThi.getId()%>">
																			<img title="Xóa" src="../images/Delete20.png" alt="Xoa" width="20" height="20" longdesc="Xoa"
																				border=0
																				onclick="if(!confirm('Bạn có muốn hủy chứng từ đầu kỳ này ?')) return false;">
																		</A>&nbsp; 
																	<%}%>
																	<%if(quyen[2]!=0 && hienThi.getTrangThai().trim().equals("0")){ %>
																		<A	href="../../SoDuDauKyUpdateSvl?userId=<%=userId%>&update=<%= hienThi.getId() %>">
																			<IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0>
																		</A>&nbsp;
																	<%} %>
																	
																	<%if(quyen[4]!=0 && hienThi.getTrangThai().trim().equals("0")) { %>
																		<A id='chotphieu<%=hienThi.getId()%>' href="../../SoDuDauKySvl?userId=<%=userId%>&chot=<%=hienThi.getId()%>">
																			<img src="../images/Chot.png" alt="Chốt" width="20" height="20" title="Chốt" border="0"
																				onclick="if(!confirm('Bạn có chắc chốt phiếu này?')) {return false ;}else{ processing('<%="chotphieu"+hienThi.getId()%>' , '../../SoDuDauKySvl?userId=<%=userId%>&chot=<%= hienThi.getId() %>');}">
																		</A>&nbsp;
																	<%}%> 
										
																	<%if(quyen[4]!=0 && !hienThi.getTrangThai().trim().equals("0")) { %>
																		<A href="../../SoDuDauKyUpdateSvl?userId=<%=userId%>&display=<%= hienThi.getId() %>">
																			<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
																		</A>&nbsp; 
																	<%} %>
										
																	</TD> 			
																</TR>
																<%
																	m++;
																				}
																			} catch (Exception e) {
																				e.printStackTrace();
																			}
																		}
																%>
																<tr class="tbfooter">
																	<TD align="center" valign="middle" colspan="13"
																		class="tbfooter">
																		<%
																			if (obj.getNxtApprSplitting() > 1) {
																		%> <img alt="Trang Dau" src="../images/first.gif"
																		style="cursor: pointer;"
																		onclick="View('xdForm', 1, 'view')"> &nbsp; <%
 	} else {
 %> <img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%
 	}
 %> <%
 	if (obj.getNxtApprSplitting() > 1) {
 %> <img alt="Trang Truoc" src="../images/prev.gif"
																		style="cursor: pointer;"
																		onclick="Prev('xdForm', 'prev')"> &nbsp; <%
 	} else {
 %> <img alt="Trang Truoc" src="../images/prev_d.gif"> &nbsp; <%
 	}
 %> <%
 	int[] listPage = obj.getNextSplittings();
 		if (listPage != null)
 			for (int i = 0; i < listPage.length; i++) {
 %> <%
 	if (listPage[i] == obj.getNxtApprSplitting()) {
 %> <a style="color: white;"><%=listPage[i]%>/ <%=obj.getTheLastSplitting()%></a>
																		<%
																			} else {
																		%> <a
																		href="javascript:View('xdForm', <%=listPage[i]%>, 'view')"><%=listPage[i]%></a>
																		<%
																			}
																		%> <input type="hidden" name="list"
																		value="<%=listPage[i]%>" /> &nbsp; <%
 	}
 %> <%
 	if (obj.getNxtApprSplitting() < obj.getTheLastSplitting()) {
 %> &nbsp; <img alt="Trang Tiep" src="../images/next.gif"
																		style="cursor: pointer;"
																		onclick="Next('xdForm', 'next')"> &nbsp; <%
 	} else {
 %> &nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
																		&nbsp; <%
 	}
 %> <%
 	if (obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {
 %> <img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%
 	} else {
 %> <img alt="Trang Cuoi" src="../images/last.gif"
																		style="cursor: pointer;"
																		onclick="View('xdForm', -1, 'view')"> &nbsp; <%
 	}
 %>
																	</TD>
																</tr>
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
</HTML>
<%}%>