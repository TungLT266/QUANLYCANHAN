<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.SoTongHopChuTMotTaiKhoanKH.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.util.List"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%
	System.out.println("");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else {
%>
<%
	ISoTongHopChuTMotTaiKhoanDT obj = (ISoTongHopChuTMotTaiKhoanDT) session.getAttribute("obj");
	ResultSet chiNhanhRs = obj.getChiNhanhRs();
	ResultSet taiKhoanRs = obj.getTaiKhoanRs();
	ResultSet khachhangRs = obj.getKhachhangRs();
	ResultSet nhomKhachHangRs = obj.getNhomKhachHangRs();
	NumberFormat formatter = new DecimalFormat("#,###,###.##");

	
%>
<%
int[] quyen = new  int[5];
quyen = util.Getquyen("SoTongHopChuTMotTaiKhoanDTSvl","",userId);
%>
<%-- <% obj.setNextSplittings(); %> --%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<!-- <script type="text/javascript" src="../scripts/ajax_erp_loadspvattu.js"></script> -->
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
<script type="text/javascript" src="../scripts/ajax.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<style type="text/css">
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
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


<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			float:left;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		th {
		cursor: pointer;
		}	
		
		.fixed {position:fixed; top:0; left:10; z-index:999999; width:99%;}
	
		
  	</style>
  	
  	
  
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform(check) {
		//Có kiểm tra điều kiện lọc
		if(!kiemTra(check)){
			return false;
		}
		
		document.xdForm.action.value = 'search';
		document.forms["xdForm"].submit();
	}
	function kiemTra(check){
		if (check == "1")
		{
			var soHieuTaiKhoanNo = document.getElementById("taiKhoan").value;
			if (soHieuTaiKhoanNo == "")
			{
				alert("Vui lòng chọn tài khoản");
				return false;
			}
		}
		var tuNgay = document.getElementById("tuNgay");
		var denNgay = document.getElementById("denNgay");
		if (tuNgay.value.trim().length ==0 || denNgay.value.trim().length == 0){
			alert ('Vui lòng nhập ngày bắt đầu và ngày kết thúc lấy báo cáo .');
			return false;
		}else if(tuNgay.value.trim() <= '2016-09-30' || denNgay.value.trim() <='2016-09-30'){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true;
	}

	function clearform() {
		document.xdForm.chiNhanh.selectedIndex = 1;
		document.xdForm.taiKhoan.selectedIndex = 0;
		document.xdForm.tuNgay.value = "";
		document.xdForm.denNgay.value = "";
		submitform(0);
	}
	function exportExcel()
	{
		document.forms['xdForm'].action.value= 'exportExcel';
		document.forms['xdForm'].submit();
	}
	
	
	function submit()
	{
		document.forms['xdForm'].action.value= 'submit';
		document.forms['xdForm'].submit();
	}
	
	
	function submitform(check) {
		//Có kiểm tra điều kiện lọc
		if(!kiemTra(check)){
			return false;
		}
		document.xdForm.action.value = 'search';
		document.forms["xdForm"].submit();
	}
	
	function changTK(){
		document.forms['xdForm'].action.value = 'changTK';
		document.forms['xdForm'].submit();
	}
	
	function kiemTra(check){
		if (check == "1")
		{
			var soHieuTaiKhoanNo = document.getElementById("soHieuTaiKhoanNo").value;
			if (soHieuTaiKhoanNo == "")
			{
				alert("Vui lòng chọn tài khoản công nợ");
				return false;
			}
		}
		
		var tuNgay = document.getElementById("tuNgay");
		var denNgay = document.getElementById("denNgay");
		if (tuNgay.value.trim().length ==0 || denNgay.value.trim().length == 0){
			alert ('Vui lòng nhập ngày bắt đầu và ngày kết thúc lấy báo cáo .');
			return false;
		}else if(tuNgay.value.trim() <= '2016-09-30' || denNgay.value.trim() <='2016-09-30'){
			alert ( 'Ngày DKSD : 30-09-2016 . Vui lòng nhập thời gian lấy báo cáo từ ngày 01-10-2016');
			return false;
		}
		return true;
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
	<form name="xdForm" method="post" action="../../SoTongHopChuTMotTaiKhoanDTSvl">
		<input type="hidden" id="action" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<%-- <input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" > --%>
		<input type="hidden" name="loaiNhom" value="<%= obj.getDieuKienLoc().getLoaiNhom() %>" >
		<script language="JavaScript" type="text/javascript">
    	thongbao();
		</script>
		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý kế toán &gt; Báo cáo &gt; Sổ tổng hợp chữ T đối tượng</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen%>&nbsp;
										</TD>
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
												<LEGEND class="legendtitle">&nbsp;Tiêu chí tìm kiếm &nbsp;</LEGEND>
												<TABLE class="tblight" width="100%" cellpadding="6" cellspacing="0">
													<TR>
														<TD width="19%" class="plainlabel">Từ ngày</TD>
														<TD class="plainlabel" colspan=5><INPUT name="tuNgay" id="tuNgay" class="days" type="text" size="30" value='<%=obj.getTuNgay()%>' readonly="readonly"></TD>
													
														<TD width="19%" class="plainlabel">Đến ngày</TD>
														<TD class="plainlabel" colspan=5><INPUT name="denNgay" id="denNgay" class="days" type="text" size="30" value='<%=obj.getDenNgay()%>' readonly="readonly"></TD>
													</TR>
<!-- 													style="display: none;" -->
												<TR>
														<TD class="plainlabel">Chi nhánh</TD>
														<TD class="plainlabel">
															<select name="chiNhanh" style="width: 300px;">
																<option value="All" selected>Tất cả</option>
																<%
																	if (chiNhanhRs != null) {
																		while (chiNhanhRs.next()) {
																			String selected = "";
																			if (chiNhanhRs.getString(1).trim().equals(obj.getChiNhanh())){
																				selected = "selected";
																			}
																	
																%>
																	<option value="<%=chiNhanhRs.getString(1) %>" <%=selected %>><%=chiNhanhRs.getString(2) %> - <%=chiNhanhRs.getString(3) %></option>
																<%} } %>
															</select>
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
														<TR>
														<TD class="plainlabel">Tài khoản công nợ</TD>
														<TD class="plainlabel">
															<select name="soHieuTaiKhoanNo" onchange="changTK()" id="soHieuTaiKhoanNo" style="width: 300px;">
																<option value=""></option>
																<%System.out.print("obj.getDieuKienLoc().getSoHieuTaiKhoanNo(): " + obj.getDieuKienLoc().getSoHieuTaiKhoanNo());
																	for (Erp_Item item: obj.getDieuKienLoc().getTaiKhoanCongNoList())
																		if (obj.getDieuKienLoc().getSoHieuTaiKhoanNo().equals(item.getValue())){
																%>
																	<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																<%
																		} else {
																%>
																	<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																<%
																	} 
																%>
														</select></TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													<TR class="plainlabel" <%if(obj.getDieuKienLoc().getLoaiNhom().trim().length() == 0) {%> style="display:none;" <%}%>>
													<TD >
														<%if(obj.getDieuKienLoc().getLoaiNhom().equals("0")) {%>
														Nhóm khách hàng
														<%}else if(obj.getDieuKienLoc().getLoaiNhom().equals("1"))  {%>
														Nhóm nhân viên
														<%}else if(obj.getDieuKienLoc().getLoaiNhom().equals("2"))  { %>
														Nhóm nhà cung cấp
														<%}else if(obj.getDieuKienLoc().getLoaiNhom().equals("3"))  { %>
														Nhóm đối tượng khác
														<%}%>
														</TD>
														<TD class="plainlabel" colspan = "5" <%if(obj.getDieuKienLoc().getLoaiNhom().trim().length() == 0) {%> style="display:none;" <%}%>>
															<select name="nhomdoituong" id="nhomdoituong" style="width: 300px;">
																<option value=""></option>
																<%
																	for (Erp_Item item: obj.getDieuKienLoc().getNhomDoiTuong())
																		if (obj.getDieuKienLoc().getNhomDoiTuongId().trim().equals(item.getValue())){
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
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													<TR>
														<TD class="plainlabel">Loại đối tượng</TD>
														<TD class="plainlabel">
															<select name="loaiDoiTuongNoId" onchange="submit();" style="width: 300px;">
																<option value=""></option>
																<%
																	for (Erp_Item item: obj.getDieuKienLoc().getLoaiDoiTuongList())
																		if (obj.getDieuKienLoc().getLoaiDoiTuongNoId().trim().equals(item.getValue())){
																%>
																	<option value="<%=item.getValue() %>" selected><%=item.getName() %></option>
																<%
																		} else {
																%>
																	<option value="<%=item.getValue() %>"><%=item.getName() %></option>
																<%
																	}
																%>
														</select></TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													
													<TR>
														<TD class="plainlabel">Tên đối tượng</TD>
														<TD class="plainlabel">
															<select name="doiTuongNoId" id="doiTuongNoId" style="width: 300px;">
																<option value=""></option>
																<%
																	for (Erp_Item item: obj.getDieuKienLoc().getDoiTuongNoList())
																		if (obj.getDieuKienLoc().getDoiTuongNoId().trim().equals(item.getValue())){
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
														<TD colspan="6" class="plainlabel"></TD>
													</TR>
													
													
													<TR style="display: none">
														<TD class="plainlabel">Nhóm khách hàng</TD>
														<TD class="plainlabel">
															<select name="nhomkhachhang" id="nhomkhachhang" style="width: 300px;">
																<option value="" selected></option>
																<%
																if (nhomKhachHangRs != null) {
																	while (nhomKhachHangRs.next()) {
																		String selected = "";
																		if (nhomKhachHangRs.getString("pk_seq").trim().equals(obj.getNhomKhachHang())){
																			selected = "selected";
																		}
																	
																%>
																	<option value="<%=nhomKhachHangRs.getString("pk_seq") %>" <%=selected %>><%=nhomKhachHangRs.getString(2) %></option>
																<%} } %>
															</select>
														</TD>
														<TD colspan="6" class="plainlabel"></TD>
													</TR>		
												
											
													
													<TR>
														<TD class="plainlabel" colspan="2">
															<a class="button2" href="javascript:clearform()"> 
																<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
														</a>&nbsp;&nbsp;&nbsp;&nbsp; 
														<a class="button2" href="javascript:submitform(1);""> 
															<img style="top: -4px;" src="../images/button.png" alt="">Tìm kiếm
														</a>&nbsp;&nbsp;&nbsp;&nbsp; 
														
														<a class="button3" href="javascript:exportExcel()"><img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>
														
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
													&nbsp;Sổ tổng hợp chữ T một tài khoản&nbsp;&nbsp; 
												</LEGEND>
												<TABLE width="100%">
													<TR>
														<TD width="98%">
															<TABLE width="100%" border="0" cellspacing="2" cellpadding="7">
																<TR class="tbheader">
																	
																	<%
																	String[] colunmNameList = {"&nbsp;", "&nbsp;", "&nbsp;", "Nợ", "Có"};
																	String[] width = {"5%", "10%", "25%", "10%", "10%"};
																	int i = 0;
																	for (String colunmName : colunmNameList){ 
																	%>
																	<TH width="<%=width[i]%>"><%=colunmName %></TH>
																		
																	<%i++;} %>
																</TR>
																<% ResultSet phatSinhRs = obj.getPhatSinhRs();
																if (phatSinhRs != null) 
																{
																	try 
																	{
																		
																		phatSinhRs.beforeFirst(); 
																		while(phatSinhRs.next())
																		{
																			if(phatSinhRs.getString("LOAI").equals("3")) 
																			{
																		%>
																		
																		
																			<TR class="tbdarkrow">
																				<TD align="right" colspan="3" class="plainlabel">Số dư nợ đầu kỳ</TD>
																				<TD align="right" class="plainlabel"><%=formatter.format(phatSinhRs.getDouble("NODAUKY"))%></TD>
																				<TD align="right" class="plainlabel"><%=formatter.format(phatSinhRs.getDouble("CODAUKY"))%> </TD>
																			</TR>
																			
																			<TR class="tbdarkrow">
																				<TD align="right" colspan="3" class="plainlabel">Tổng phát sinh</TD>
																				<TD align="right" class="plainlabel"><%=formatter.format(phatSinhRs.getDouble("NOPHATSINH"))%></TD>
																				<TD align="right" class="plainlabel"><%=formatter.format(phatSinhRs.getDouble("COPHATSINH"))%></TD>
																			</TR> 
																			
																			<TR class="tbdarkrow">
																				<TD align="right" colspan="3" class="plainlabel">Số dư cuối kỳ</TD>
																				<TD align="right" class="plainlabel"><%=formatter.format(phatSinhRs.getDouble("NOCUOIKY"))%></TD>
																				<TD align="right" class="plainlabel"><%=formatter.format(phatSinhRs.getDouble("COCUOIKY"))%></TD>
																			</TR>
																			<% }
																			} 
																	}catch ( Exception ex){ex.printStackTrace();}
																}%>
																<TABLE width="100%" cellpadding="7" cellspacing="2">
																	<TR class="tbheader" id=task_flyout>
																		<%
																		String[] colunmNameList2 = {"STT", "Tài khoản đối ứng", "Tên tài khoản", "Phát sinh nợ", "Phát sinh có"};
																		i = 0;
																		for (String colunmName : colunmNameList2){ 
																		%>
																		<TH width="<%=width[i]%>"><%=colunmName %></TH>
																			
																		<%i++;} %>
																	
																	</TR>
																	<%
																			
																			if (phatSinhRs != null) {
																				try {
																					phatSinhRs.beforeFirst();
																					int m = 1;
																					String tbrow = "";
																					while (phatSinhRs.next()) {
																						if(phatSinhRs.getString("LOAI").equals("2")) 
																						{
																						tbrow = (m % 2 != 0) ? "tblightrow" : "tbdarkrow";
																						%>
																						<TR class=<%=tbrow%>>
																							<TD align="center" width="<%=width[0]%>" ><%=m%></TD>
																							<TD align="right" width="<%=width[1]%>" ><%=phatSinhRs.getString(2)%></TD>
																							<TD align="left" width="<%=width[2]%>"><%=phatSinhRs.getString(3)%></TD>
<!-- 																							6.7 là trong kỳ -->
																							<TD align="right" width="<%=width[3]%>"><%=formatter.format(phatSinhRs.getDouble(6))%></TD>
																							<TD align="right" width="<%=width[4]%>"><%=formatter.format(phatSinhRs.getDouble(7))%></TD>
																						</TR>
																						<%
																				m++;
																						}
																					}
																				} catch (Exception e) {
																					e.printStackTrace();
																				} finally {phatSinhRs.close();}
																			}
																		%>
																</TABLE>
																<%-- <tr class="tbfooter" > 
																	 <TD align="center" valign="middle" colspan="13" class="tbfooter">
																	 	<%if(obj.getNxtApprSplitting() >1) {%>
																			<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('xdForm', 1, 'view')"> &nbsp;
																		<%}else {%>
																			<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
																			<%} %>
																		<% if(obj.getNxtApprSplitting() > 1){ %>
																			<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('xdForm', 'prev')"> &nbsp;
																		<%}else{ %>
																			<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
																		<%} %>
																		
																		<%
																			int[] listPage = obj.getNextSplittings();
																			if (listPage != null)
																			for(int i = 0; i < listPage.length; i++){
																		%>
																		
																		<% 							
																	
																		if(listPage[i] == obj.getNxtApprSplitting()){ %>
																		
																			<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
																		<%}else{ %>
																			<a href="javascript:View('xdForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
																		<%} %>
																			<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
																		<%} %>
																		
																		<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
																			&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('xdForm', 'next')"> &nbsp;
																		<%}else{ %>
																			&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
																		<%} %>
																		<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
																	   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
																   		<%}else{ %>
																   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('xdForm', -1, 'view')"> &nbsp;
																   		<%} %>
																	</TD>
																 </tr>   --%>
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
<%
	obj.DBClose();
	}
%>
