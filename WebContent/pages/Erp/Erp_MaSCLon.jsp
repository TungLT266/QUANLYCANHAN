<%@page import="geso.traphaco.erp.beans.masclon.IErp_MaSCLonList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.xuatdungccdc.*"%>
<%@ page import="geso.traphaco.erp.beans.xuatdungccdc.Erp_Item"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%@ page import="geso.traphaco.center.util.Erp_ListItem"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
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
	IErp_MaSCLonList obj = (IErp_MaSCLonList) session.getAttribute("obj");
		List<Erp_ListItem> hienThiList = obj.getList();
		List<Erp_Item> taiKhoanList = obj.getTaiKhoanList();
		List<Erp_Item> taiSanList = obj.getTaiSanCDList();
%>
<%
	int[] quyen = new int[5];
		quyen = util.Getquyen("Erp_MaSCLonSvl","", userId);
%>
<%
	obj.setNextSplittings();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
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
 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
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
	filter: progid :   DXImageTransform.Microsoft.Shadow (   color =   gray,
		direction =   135 );
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
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
    <script type="text/javascript">
    
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
  
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
		document.xdForm.maCCDC.value = "";
		document.xdForm.soChungTu.value = "";
		document.xdForm.trangThai.selectedIndex = 0;
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

	function chot(thangDaPB)
	{
		if (thangDaPB > 0)
		{
			var r = confirm("Mã tài sản đã phân bổ đã phân bổ, bạn có muốn chốt phiếu và xóa phân bổ (bạn phải tự chạy phân bổ lại)");
			if (r == true) {
			    return true;
			} else {
			    return false;
			}
		}

		var r = confirm("Bạn thực sự muốn chuyển mã sửa chữa lớn");
		if (r == true) {
		    return true;
		} else {
		    return false;
		}
	}

</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="xdForm" method="post" action="../../Erp_MaSCLonSvl">
		<input type="hidden" name="userId" value='<%=userId%>'>
		<%
			System.out.println("obj.getMsg(): " + obj.getMsg());
		%>
		<input type="hidden" name="action" value='1'> 
		<input
			type="hidden" id="msg" name="msg" value='<%=obj.getMsg()%>'>
		<input type="hidden" name="crrApprSplitting"
			value="<%=obj.getCrrApprSplitting()%>"> 
			<input type="hidden"
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
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản
											lý tài sản &gt; Mã SC lớn/XDCB TSCD</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%>&nbsp;</TD>
									</TR>
								</TABLE></TD>
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
														<TD width="19%" class="plainlabel">Mã SC/XDCB</TD>
														<TD class="plainlabel" colspan=5><INPUT name="ma"
															type="text" size="30" value='<%=obj.getMa()%>'
															onChange="submitform();">
														</TD>

														<TD width="19%" class="plainlabel">Số chứng từ</TD>
														<TD class="plainlabel" colspan=5><INPUT
															name="soChungTu" type="text" size="30"
															value='<%=obj.getSoChungTu()%>' onChange="submitform();">
														</TD>
													</TR>
													<TR>
														<TD width="19%" class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel" colspan=5><INPUT
															name="ngayBatDau" class="days" type="text" size="30"
															value='<%=obj.getNgayBatDau()%>' onChange="submitform();">
														</TD>

														<TD width="19%" class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel" colspan=5><INPUT
															name="ngayKetThuc" class="days" type="text" size="30"
															value='<%=obj.getNgayKetThuc()%>'
															onChange="submitform();">
														</TD>
													</TR>

													<TR>
														<TD width="19%" class="plainlabel">Tài khoản kế toán</TD>
														<TD class="plainlabel" colspan=5><select
															name="taiKhoanId" id="taiKhoanId" style="width: 200px;"
															onChange="submitform();">
																<option value=""></option>
																<%
																	if (taiKhoanList != null) {
																			for (Erp_Item item : taiKhoanList) {
																				if (item.getValue().equals(obj.getTaiKhoanId())) {
																%>
																<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
																<%
																	} else {
																%>
																<option value="<%=item.getValue()%>"><%=item.getName()%></option>
																<%
																	}
																			}
																		}
																%>
														</select></TD>

														<TD width="19%" class="plainlabel">Tài sản cố định</TD>
														<TD class="plainlabel" colspan=5><select
															name="taiSanId" id="taiSanId" style="width: 200px;"
															onChange="submitform();">
																<option value=""></option>
																<%
																	if (taiSanList != null) {
																			for (Erp_Item item : taiSanList) {
																				if (item.getValue().equals(obj.getTaiSanId())) {
																%>
																<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
																<%
																	} else {
																%>
																<option value="<%=item.getValue()%>"><%=item.getName()%></option>
																<%
																	}
																			}
																		}
																%>
														</select></TD>
													</TR>

													<TR>
														<TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel"><select name="trangThai"
															onChange="submitform();">
																<%
																	if (obj.getTrangThai().equals("0")) {
																%>
																<option value=""></option>
																<option value="2">Đã xóa</option>
																<option value="1">Đã chốt</option>
																<option value="0" selected>Chưa chốt</option>
																<option value="3">Đã chuyển</option>
																<%
																	} else if (obj.getTrangThai().equals("1")) {
																%>
																<option value=""></option>
																<option value="2">Đã xóa</option>
																<option value="1" selected>Đã chốt</option>
																<option value="0">Chưa chốt</option>
																<option value="3">Đã chuyển</option>
																<%
																	} else if (obj.getTrangThai().equals("1")) {
																%>
																<option value=""></option>
																<option value="2">Đã xóa</option>
																<option value="1" selected>Đã chốt</option>
																<option value="0">Chưa chốt</option>
																<option value="3">Đã chuyển</option>
																<%
																	} else {
																%>
																<option value="" selected></option>
																<option value="2">Đã xóa</option>
																<option value="1">Đã chốt</option>
																<option value="0">Chưa chốt</option>
																<option value="3">Đã chuyển</option>
																<%
																	}
																%>
														</select>
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													<TR>
														<TD class="plainlabel" colspan="2"><a class="button2"
															href="javascript:clearform()"> <img
																style="top: -4px;" src="../images/button.png" alt="">Nhập
																lại </a>&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
															href="javascript:submitform();""> <img
																style="top: -4px;" src="../images/button.png" alt="">Tìm
																kiếm </a>
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
												</TABLE>
											</FIELDSET></TD>
									</TR>
								</TABLE>
								<TABLE width="100%" cellpadding="0" cellspacing="1">
									<TR>
										<TD width="100%">
											<FIELDSET>
												<LEGEND class="legendtitle">
													&nbsp;Mã SC lớn/XDCB TSCD &nbsp;&nbsp;

													<%
														if (quyen[0] != 0) {
													%>
													<a class="button3" href="javascript:newform()"> <img
														style="top: -4px;" src="../images/New.png" alt="">Tạo
														mới </a>
													<%
														}
													%>
												</LEGEND>
												<TABLE width="100%">
													<TR>
														<TD width="98%">
															<TABLE width="100%" border="0" cellspacing="1"
																cellpadding="2">
																<TR class="tbheader">
																	<TH width="5%">STT</TH>
																	<TH width="10%">Số chứng từ</TH>
																	<TH width="10%">Mã SC/XDCB</TH>
																	<TH width="10%">Tên hạng mục</TH>
																	<TH width="10%">Trạng thái</TH>
																	<TH width="10%">Ngày tạo</TH>
																	<TH width="10%">Người tạo</TH>
																	<TH width="10%">Ngày sửa</TH>
																	<TH width="10%">Người sửa</TH>
																	<TH width="25%">Tác vụ</TH>
																</TR>
																<%
																	if (hienThiList != null) {
																			try {
																				int m = 0;
																				String lightrow = "tblightrow";
																				String darkrow = "tbdarkrow";
																				for (Erp_ListItem hienThi : hienThiList) {
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
																	<TD align="left"><%=m%></TD>
																	<TD align="left"><%=hienThi.getId()%></TD>
																	<TD align="left"><%=hienThi.getMa()%></TD>
																	<TD align="left"><%=hienThi.getTen()%></TD>
																	<% String trangthai="";
																	   if(hienThi.getTrangThai().equals("1"))
																	   {
																		   trangthai= "Đã chốt";
																	   }
																	   else   if(hienThi.getTrangThai().equals("2"))
																	   {
																		   trangthai= "Đã xóa";
																	   }else if(hienThi.getTrangThai().equals("3"))
																	   {
																		   trangthai= "Đã kết chuyển";
																	   }else
																	   {
																		   trangthai= "chưa chốt";
																	   }
																	%>
																	<TD align="center"><%=trangthai%></TD>
																	<TD align="left"><%=hienThi.getNgayTao()%></TD>
																	<TD align="left"><%=hienThi.getNguoiTao()%></TD>
																	<TD align="left"><%=hienThi.getNgaySua()%></TD>
																	<TD align="left"><%=hienThi.getNguoitSua()%></TD>
																	<TD align="center">
																		<TABLE>
																			<TR>
																				<TD>
																					<%
																						if (quyen[2] != 0
																												&& hienThi.getTrangThai().trim()
																														.equals("0")) {
																					%> <A
																					href="../../Erp_MaSCLonUpdateSvl?userId=<%=userId%>&update=<%=hienThi.getId()%>">
																						<img title="Cập nhật" src="../images/Edit20.png"
																						alt="Cap nhat" width="20" height="20"
																						longdesc="Cap nhat" border=0> </A>&nbsp; <%
 	}
 %> <%
 	if (quyen[1] != 0
 							&& hienThi.getTrangThai().trim()
 									.equals("0")) {
 %> <A
																					href="../../Erp_MaSCLonSvl?userId=<%=userId%>&delete=<%=hienThi.getId()%>">
																						<img title="Xóa" src="../images/Delete20.png"
																						alt="Xoa" width="20" height="20" longdesc="Xoa"
																						border=0
																						onclick="if(!confirm('Bạn có muốn xóa sản phẩm này ?')) return false;">
																				</A>&nbsp; <%
 	}
 %> <!-- QUYEN VIEW DLN --> <%
 	if (quyen[1] != 0
 							&& (hienThi.getTrangThai().trim()
 									.equals("1")
 									|| hienThi.getTrangThai().trim()
 											.equals("2") || hienThi
 									.getTrangThai().trim().equals("3"))) {
 %> <A
																					href="../../Erp_MaSCLonUpdateSvl?userId=<%=userId%>&display=<%=hienThi.getId()%>">
																						<img title="Hiển thị"
																						src="../images/Display20.png" alt="Hiển thị"
																						width="20" height="20" longdesc="Hiển thị"
																						border=0> </A> <%
 	}
 %> <%
 	if (quyen[1] != 0
 							&& (hienThi.getTrangThai().trim()
 									.equals("0"))) {
 %> <A
																					href="../../Erp_MaSCLonSvl?userId=<%=userId%>&chot=<%=hienThi.getId()%>">
																						<img title="Chốt" src="../images/Chot.png"
																						alt="Chốt" width="20" height="20"
																						longdesc="Hiển thị" border=0> </A> <%
 	}
 %> <%
 	if (quyen[1] != 0
 							&& (hienThi.getTrangThai().trim()
 									.equals("1"))) {
 %> <A onclick="return chot(<%=hienThi.getField1()%>);"
																					href="../../Erp_MaSCLonSvl?userId=<%=userId%>&chuyen=<%=hienThi.getId()%>">
																						<img title="Chuyển" src="../images/Next20.png"
																						alt="Chuyển" width="20" height="20"
																						longdesc="Chuyển" border=0> </A> <%
 	}
 if (quyen[1] != 0&& (hienThi.getTrangThai().trim().equals("3"))) {
		%> <A onclick="return mochot(<%=hienThi.getField1()%>);"
														href="../../Erp_MaSCLonSvl?userId=<%=userId%>&mochot=<%=hienThi.getId()%>">
															<img title="Mở chốt" src="../images/unChot.png"
															alt="Chuyển" width="20" height="20"
															longdesc="Chuyển" border=0> </A> <%
}



 
 %> <!-- END QUYEN VIEW DLN --> <%
																						if (quyen[2] != 0
																												&& hienThi.getTrangThai().trim()
																														.equals("1")) {
																					%> <A
																					href="../../Erp_MaSCLonUpdateSvl?userId=<%=userId%>&update_ngaykc=<%=hienThi.getId()%>">
																						<img title="Cập nhật ngày kết chuyển"
																						src="../images/Edit20.png" alt="Cap nhat"
																						width="20" height="20" longdesc="Cap nhat"
																						border=0> </A>&nbsp; <%
 	}
 %>
																				</TD>
																			</TR>
																		</TABLE>
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
															</TABLE></TD>
													</TR>
												</TABLE>
											</FIELDSET></TD>
									</TR>
								</TABLE></TD>
						</TR>
					</TABLE></TD>
			</TR>
		</TABLE>
	</form>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</HTML>
<%
	obj.DBClose();
%>
<%
	}
%>