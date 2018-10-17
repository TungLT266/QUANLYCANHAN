<%@page import="geso.traphaco.center.util.ViewItem"%>
<%@page import="geso.traphaco.erp.beans.khaibaomau.*"%>
<%@page import="geso.traphaco.erp.beans.khaibaomau.imp.ErpKhaiBaoMauList"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%@ page import="geso.traphaco.center.util.TitleItem"%>
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
		IErpKhaiBaoMauList obj = (IErpKhaiBaoMauList) session.getAttribute("obj");
		List<TitleItem> titleList = obj.getTitlelist();
		List<List<ViewItem>> viewList = obj.getViewList();
		String svl = "ErpKhaiBaoMauSvl";
		String updateSvl = "ErpKhaiBaoMauUpdateSvl";
%>
<%
	int[] quyen = new int[5];
		quyen = util.Getquyen("33", userId);
%>
<%
	obj.setNextSplittings();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<!-- 	 <script>
	     $(document).ready(function() { 
	      $("select:not(.notuseselect2)").select2({ width: 'resolve' });   
	     });
	    </script> -->
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
	<form name="xdForm" method="post" action="../../<%=svl%>">
		<input type="hidden" name="userId" value='<%=userId%>'>
		<%
			System.out.println("obj.getMsg(): " + obj.getMsg());
		%>
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" id="msg" name="msg" value='<%=obj.getMsg()%>'>
		<input type="hidden" name="crrApprSplitting" value="<%=obj.getCrrApprSplitting()%>"> 
		<input
			type="hidden" name="nxtApprSplitting"
			value="<%=obj.getNxtApprSplitting()%>">

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
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán &gt; Báo cáo &gt; Bảng cân đối phát sinh (TT200) &gt; Khai báo mẫu</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;</TD>
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
														<TD width="19%" class="plainlabel">Mã số</TD>
														<TD class="plainlabel" colspan=5>
															<INPUT name="ma" type="text" size="30" value='<%=obj.getMa()%>' onChange="submitform();">
														</TD>

														<TD width="19%" class="plainlabel">Tên</TD>
														<TD class="plainlabel" colspan=5>
															<INPUT name="ten" type="text" size="30" value='<%=obj.getTen()%>' onChange="submitform();">
														</TD>
													</TR>
													<TR style="display: none;">
														<TD width="19%" class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel" colspan=5>
															<INPUT name="ngayBatDau" class="days" type="text" size="30" value='<%=obj.getNgayBatDau()%>' onChange="submitform();">
														</TD>

														<TD width="19%" class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel" colspan=5>
															<INPUT name="ngayKetThuc" class="days" type="text" size="30" value='<%=obj.getNgayKetThuc()%>' onChange="submitform();">
														</TD>
													</TR>
													<TR>
														<TD class="plainlabel" colspan="2">
															<a class="button2" href="javascript:clearform()"> 
																<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại 
															</a>&nbsp;&nbsp;&nbsp;&nbsp; 
															<a class="button2" href="javascript:submitform();""> 
																<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm 
															</a>
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
													&nbsp;Khai báo mẫu &nbsp;&nbsp;

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
																<%for (TitleItem item : titleList){ %>
																	<TH width="<%= item.getWidth()%>%"><%=item.getTitleName() %></TH>
																<%} %>
																</TR>
																<%
																	if (viewList != null) {
																		int m = 0;
																		String lightrow = "tblightrow";
																		String darkrow = "tbdarkrow";
																		for (List<ViewItem> row : viewList) 
																		{
																			if (m % 2 != 0) {
																%>
																<TR class=<%=lightrow%>>
																	<%} else {%>
																<TR class=<%=darkrow%>>
																	<%}%>
																	<%for (int j = 0; j < row.size(); j++){
																		ViewItem item = row.get(j);%>
																	<TD align="<%=item.getAlign()%>">
																		<%if (j < row.size() - 1){%>
																	<%=item.getValue()%>
																	<%}else{ %>
																		<%if(quyen[2] != 0 && item.getValue().contains("update")){ %>
																		<A href="../../<%=updateSvl %>?userId=<%=userId%>&update=<%=row.get(1).getValue() %>">
																			<img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border=0/>
																		</A>&nbsp;
																		<%} %>
																		
																		<%if(quyen[2] != 0 && item.getValue().contains("display")){ %>
																		<A href="../../<%=updateSvl %>?userId=<%=userId%>&display=<%=row.get(1).getValue() %>">
																			<img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0>
																		</A>&nbsp;
																		<%} %>
																		
																		<%if(quyen[4] != 0 && item.getValue().contains("chot")){ %>
																		<A href="../../<%=updateSvl %>?userId=<%=userId%>&chot=<%=row.get(1).getValue() %>">
																			<img src="../images/Chot.png" alt="Chốt thu tiền" width="20" height="20" title="Chốt thu tiền" border="0" onclick="if(!confirm('Bạn có chắc chốt phiếu thu tiền này?')) {return false ;}else{ processing('<%="chotphieu"+  row.get(1).getValue()%>' , '../../<%=svl %>?userId=<%=userId%>&chot=<%=row.get(1).getValue()%>');}"  >
																		</A>&nbsp;
																		<%} %>
																		
																		<%if(quyen[1]!=0 && item.getValue().contains("delete")){ %>
																		<A href = "../../<%=svl %>?userId=<%=userId%>&delete=<%=row.get(1).getValue() %>">
																			<img src="../images/Delete20.png" alt="Xoa" title="Xóa" width="20" height="20" longdesc="Xoa" border=0 onclick="if(!confirm('Bạn có muốn xóa sản phẩm này ?')) return false;">
																		</A>
																		<%}
																		} %>
																	<%} %>
																		
																	</TD>
																	<%} %>
																</TR>
																<%
																	m++;
															}
															%>
																<tr class="tbfooter">
																	<TD align="center" valign="middle" colspan="13" class="tbfooter">
																		<%if (obj.getNxtApprSplitting() > 1) {%> 
																		<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('xdForm', 1, 'view')"> &nbsp; 
																		<%} else {%>
																		<img alt="Trang Dau" src="../images/first.gif">
																		&nbsp; 
																		<%}%> 
																		<%if (obj.getNxtApprSplitting() > 1) {%> 
																		<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('xdForm', 'prev')"> &nbsp; 
																		<%} else {%>
																		<img alt="Trang Truoc" src="../images/prev_d.gif">
																		&nbsp; 
																		<%}%> 
																		<%int[] listPage = obj.getNextSplittings();
																	 		if (listPage != null)
																	 			for (int i = 0; i < listPage.length; i++) {%> 
															 			<%if (listPage[i] == obj.getNxtApprSplitting()) {%> 
																	 	<a style="color: white;"><%=listPage[i]%>/ <%=obj.getTheLastSplitting()%></a>
																		<%} else {%> 
																		<a href="javascript:View('xdForm', <%=listPage[i]%>, 'view')"><%=listPage[i]%></a>
																		<%}%> 
																		<input type="hidden" name="list" value="<%=listPage[i]%>" /> &nbsp; 
																		<%}%> 
																		<%if (obj.getNxtApprSplitting() < obj.getTheLastSplitting()) {%>
																		&nbsp; 
																		<img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('xdForm', 'next')"> &nbsp; 
																		<%} else { %>
																		&nbsp; 
																		<img alt="Trang Tiep" src="../images/next_d.gif"> &nbsp; 
																		<%}%> 
																		<%if (obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
																		<img alt="Trang Cuoi" src="../images/last.gif">
																		&nbsp; 
																		<%} else {%> 
																		<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('xdForm', -1, 'view')"> &nbsp; 
																		<%}%>
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
</BODY>
</HTML>
<%
	}
%>