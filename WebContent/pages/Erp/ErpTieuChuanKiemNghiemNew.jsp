<%@page import="java.util.List"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.tieuchuankiemnghiem.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="geso.traphaco.center.util.*"%>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	IErpTieuChuanKiemNghiem tcknBean = (IErpTieuChuanKiemNghiem)session.getAttribute("tcknBean"); 
	ResultSet loaimauknRs = tcknBean.getLoaimauknRs();
	ResultSet bieumauhsRs = tcknBean.getBieumauhsRs();
	ResultSet sanphamRs = tcknBean.getSanphamRs();
	
	List<IItemLoader> yeuCauKNList = tcknBean.getYeuCauKNList();
	List<IItemLoader> thietbiList = tcknBean.getThietbiList();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Tiêu chuẩn kiểm nghiệm</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

	<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	
	<script type="text/javascript" src="../scripts/ajax.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
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
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
				);
		}
		
		#dhtmlpointer {
			position: absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		}
		</style>
	
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

<LINK rel="stylesheet" href="../css/cdtab.css" type="text/css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
				
				$(".titleTab a").click(function () { 

			        var activeTab = $(this).attr("href"); 
						//var activeTab =
			        $(".titleTab a").removeClass("active"); 
			        $(this).addClass("active");
			        $(".tabContents").hide();
			        $(activeTab).fadeIn();

			    });
	        }); 		
			
	</script>

<SCRIPT language="JavaScript" type="text/javascript">
function submitform()
{
    document.forms["tcknForm"].submit();
}

function saveform()
{
	document.forms["tcknForm"].action.value = "save";
	document.forms["tcknForm"].submit();
}

function replaces(){
	var yeucauID = document.getElementsByName("yeucauID");
	var yeucauTen = document.getElementsByName("yeucauTen");
	
	var i;
	for(i = 0; i < yeucauTen.length; i++)
	{
		if(yeucauTen.item(i).value != "")
		{
			var sp = yeucauTen.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));

			if(pos > 0)
			{
				yeucauID.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				
				var MA = sp.substring(0, parseInt(sp.indexOf("-->[")));
				sp = sp.substr(parseInt(sp.indexOf("-->[")) + 4);
				
				yeucauTen.item(i).value = MA + " <> " + sp.substring(0, parseInt(sp.indexOf("]")));
				
				loadPP(i);
			}
		}
		else
		{
			yeucauID.item(i).value = "";
			yeucauTen.item(i).value = "";
		}
	}	
	
	var thietbiID = document.getElementsByName("thietbiID");
	var thietbiMa = document.getElementsByName("thietbiMa");
	var thietbiTen = document.getElementsByName("thietbiTen");
	var thietbiGhichu = document.getElementsByName("thietbiGhichu");
	
	var i;
	for(i = 0; i < thietbiMa.length; i++)
	{
		if(thietbiMa.item(i).value != "")
		{
			var sp = thietbiMa.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));

			if(pos > 0)
			{
				thietbiID.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				
				thietbiMa.item(i).value = sp.substring(0, parseInt(sp.indexOf("-->[")));
				sp = sp.substr(parseInt(sp.indexOf("-->[")) + 4);
				
				thietbiTen.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				thietbiGhichu.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
				reload();
			}
		}
		else
		{
			thietbiID.item(i).value = "";
			thietbiMa.item(i).value = "";
			thietbiTen.item(i).value = "";
			thietbiGhichu.item(i).value = "";
		}
	}	
	
	setTimeout(replaces, 300);
}

function loadPP(i){
	document.forms["tcknForm"].action.value = "loadPP";
	document.forms["tcknForm"].item.value = i;
	document.forms["tcknForm"].submit();
}

function reload(){
	document.forms["tcknForm"].action.value = "load";
	document.forms["tcknForm"].submit();
}

function changeCheckValue(n,count){
	var ppTNCheck = document.getElementsByName("ppTNCheck" + count);
	var ppTNChon = document.getElementsByName("ppTNChon" + count );
	
	if (ppTNCheck.item(n).checked == true) {
		ppTNChon.item(n).value = "1";
	}else if (ppTNCheck.item(n).checked == false){
		ppTNChon.item(n).value = "0";
	}	
}

</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="tcknForm" method="post"
		action="../../ErpTieuChuanKiemNghiemUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="item" value=''>
		<%-- <input type="hidden" name="ncpId" value='<%= tcknBean.getMa()  %>'> --%>
		<input type="hidden" name="action" value="new"> <input
			type="hidden" name="id" value='<%= tcknBean.getId()%>'>

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<!--begin body Dossier-->
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ liệu nền &gt; Hồ sơ kiểm nghiệm &gt; Tiêu chuẩn kiểm nghiệm
											&gt; Tạo mới</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%=userTen %>&nbsp;
										</TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../ErpTieuChuanKiemNghiemSvl?userId=<%=userId %>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left"><A
											href="javascript: saveform()"><IMG
												src="../images/Save30.png" title="Luu lai" alt="Luu lai"
												border="1" style="border-style: outset"></A></TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<FIELDSET>
									<LEGEND class="legendtitle">Thông báo </LEGEND>
								<%System.out.print("LOI: " + tcknBean.getMsg()); %>
									<textarea name="dataerror" style="width: 100%"
										readonly="readonly" rows="1"><%=tcknBean.getMsg() %></textarea>
									<% tcknBean.setMsg("") ; %>
								</FIELDSET>
							</TD>
						</tr>

						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông
										tin tiêu chuẩn KN</LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">

										<TR>
											<TD style="width: 200px" class="plainlabel">Mã tiêu chuẩn kiểm nghiệm<FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><INPUT type="text" name="ma"
												style="width: 200px" value='<%= tcknBean.getMa()%>'></TD>
											<TD style="width: 150px" class="plainlabel">Ngày cấp phép <FONT class="erroralert"> *</FONT>
											</TD>
											<TD class="plainlabel"><INPUT class="days" type="text" name="ngaycap"
												style="width: 200px" value='<%= tcknBean.getNgaycap()%>' readonly="readonly"></TD>
										</TR>
										<TR>
											<TD class="plainlabel">Diễn giải/Mô tả</TD>
											<TD class="plainlabel"><textarea name="diengiai"
													style="width: 200px; color: black; height: 30px;"><%= tcknBean.getDienGiai() %></textarea>
											</TD>
											<TD class="plainlabel">Loại mẫu KN</TD>
											<TD class="plainlabel"><SELECT name="loaimauknId"
												id="loaimauknId" style="width: 200px">
													<option value=""></option>
													<%
										if (loaimauknRs != null) {
											try { 
											
												while (loaimauknRs.next()) {
													if (tcknBean.getLoaimauknId().equals(loaimauknRs.getString("PK_SEQ"))) {
														
										%>
													<option value="<%=loaimauknRs.getString("PK_SEQ")%>"
														selected><%=loaimauknRs.getString("ten")%></option>
													<%
											} else {
										%>
													<option value="<%=loaimauknRs.getString("PK_SEQ")%>"><%=loaimauknRs.getString("ten")%></option>
													<%
											}
												}
												
												} catch (java.sql.SQLException e) {
											} }
										%>
											</SELECT></TD>
										</TR>
										<tr>
											<TD class="plainlabel">Sản phẩm</TD>
											<TD class="plainlabel"><SELECT name="sanphamId"
												id="sanphamId" style="width: 200px">
													<option value=""></option>
													<%
										if (sanphamRs != null) {
											try { 
											
												while (sanphamRs.next()) {
													if (tcknBean.getSanphamId().equals(sanphamRs.getString("PK_SEQ"))) {
														
										%>
													<option value="<%=sanphamRs.getString("PK_SEQ")%>" selected><%=sanphamRs.getString("ten")%></option>
													<%
											} else {
										%>
													<option value="<%=sanphamRs.getString("PK_SEQ")%>"><%=sanphamRs.getString("ten")%></option>
													<%
											}
												}
												
												} catch (java.sql.SQLException e) {
											} }
										%>
											</SELECT></TD>

											<TD class="plainlabel">Biểu mẫu hồ sơ</TD>
											<TD class="plainlabel"><SELECT multiple="multiple"
												name="bieumauhsId" id="bieumauhsId" style="width: 300px">
													<option value=""></option>
													<%
										if (bieumauhsRs != null) {
											try { 
											
												while (bieumauhsRs.next()) {
													if (tcknBean.getBieumauhsId().indexOf(bieumauhsRs.getString("PK_SEQ"))>=0) {
														
										%>
													<option value="<%=bieumauhsRs.getString("PK_SEQ")%>" selected><%=bieumauhsRs.getString("ten")%></option>
													<%
											} else {
										%>
													<option value="<%=bieumauhsRs.getString("PK_SEQ")%>"><%=bieumauhsRs.getString("ten")%></option>
													<%
											}
												}
												
												} catch (java.sql.SQLException e) {
											} }
										%>
											</SELECT></TD>
										</tr>

										<tr>
											<TD class="plainlabel">Hoạt động</TD>
											<TD class="plainlabel" colspan="1" align=left>
												<%
										if (tcknBean.getIshoatdong().equals("1")) {
									%> <input name="hoatdong" type="checkbox" value="1" checked>
												<%
										} else {
									%> <input name="hoatdong" type="checkbox" value="1" checked> <%
										}
									%>
											</TD>
											<TD class="plainlabel" colspan="2"></TD>
										</tr>




										<TR>
											<td colspan="4">
												<div id="tabContaier">
													<div class="titleTab">
														<a class="active" href="#tab_1">Yêu cầu kỹ thuật</a>
													</div>
													<div class="titleTab">
														<a href="#tab_2">Thiết bị kiểm nghiệm</a>
													</div>


												</div>

												<div class="tabDetails">
													<div id="tab_1" class="tabContents" style="display: block">

														<table width="100%" cellpadding="4" cellspacing="2">
															<tr class="tbheader">
																<th align="center" width="2%">Thứ tự in</th>
																<th align="center" width="25%">Yêu cầu kĩ thuật</th>
																<th align="center" width="8%">Phương pháp thử nghiệm</th>

															</tr>
															<%
																int count = 0;
																	if (yeuCauKNList != null) {
																	 if (yeuCauKNList.size() > 0) {
																		for (int i = 0; i < yeuCauKNList.size(); i++) {
																			IItemLoader yeucauKN = yeuCauKNList.get(i); 
															%>


															<tr>
																<td><input type="text"
																	style="width: 100%; text-align: center;"
																	value="<%= count + 1 %>" readonly="readonly"></td>

																<td><input type="hidden" name="yeucauID"
																	style="width: 100%; text-align: left;"
																	value="<%= yeucauKN.getPk_seq() %>"> <input
																	type="text" name="yeucauTen"
																	style="width: 100%; text-align: left;"
																	value="<%= yeucauKN.getTen() %>"></td>


																<td align="center">
																	<A href="" id="PPThuNghiem<%=count%>"
																	rel="subcontentPPThuNghiem<%=count%>">&nbsp; <img
																		alt="Phương pháp thử nghiệm"
																		src="../images/vitriluu.png">
																</A> &nbsp;
																	<DIV id="subcontentPPThuNghiem<%=count%>"
																		style="position: absolute; visibility: hidden; border: 5px solid #80CB9B; background-color: white; width: 450px; max-height: 250px; overflow-y: scroll; padding: 4px;">

																		<TABLE width="100%" border="0" cellspacing="2"
																			cellpadding="4">
																			<TR class="tbheader">
																				<TH width="15%">index</TH>
																				<TH width="70%">Phương pháp thử nghiệm</TH>
																				<TH width="30%">Chọn</TH>
																			</TR>

																			<%
																			int n=0;
																			if (yeucauKN.getPpThuNghiemList() != null) {
																				 if (yeucauKN.getPpThuNghiemList().size() > 0) {
																					for (int index = 0; index < yeucauKN.getPpThuNghiemList().size(); index++) {
																						IItemLoader phuongphapKN = yeucauKN.getPpThuNghiemList().get(index); 
																			%>
																			<TR>

																				<TD><input type="text" style="width: 100%; text-align: center;"
																					 value="<%= index + 1 %>" readonly="readonly"></TD>

																				<TD><input type="hidden"
																					name="<%="ppTNId" + count%>"
																					value="<%= phuongphapKN.getPk_seq() %>"
																					style="width: 100%; text-align: center;" readonly="readonly">

																					<input type="text"
																					name="<%="ppTNTen" + count%>"
																					value="<%= phuongphapKN.getTen() %>"
																					style="width: 100%; text-align: center;" readonly="readonly">
																				</TD>
																				
																				
																				
																				<TD><input type="checkbox"
																					name="<%="ppTNCheck" + count%>" value="1"
																					style="width: 100%; text-align: center;"
																					<%if(phuongphapKN.getChon().equals("1")) { %> checked="checked" <% } %>
																					onchange="javascript:changeCheckValue(<%= n %>,<%= count %>);">
																					<input type="hidden"
																					name="<%="ppTNChon" + count%>"
																					value="<%= phuongphapKN.getChon() %>"
																					style="width: 100%; text-align: center;" readonly="readonly">
																				</TD>
																			
																			</TR>

																			<%
																				

																					 %>
																	
																				
																	<% 				
																	 n++; 		} 
																			}
																		}
																			%>
																		
																	</TABLE>
																	<div align="right">
																		<label style="color: red"></label>
																		<a
																				href="javascript:dropdowncontent.hidediv('subcontentPPThuNghiem<%=count%>')">Đóng lại </a>
																	</div>
																</DIV>
																
																<script type="text/javascript"> 
																	dropdowncontent.init('PPThuNghiem' + <%=count%>,'left-top',500,'click');
																</script>
																
																</td>
																
																
															</tr>
														
														<%
																count++;
														 } 
													} 
												}
															%>
															
															
											<%
																
													for (int i = 0; i < 1; i++) {
																			
															%>
															
															
														<tr>
														<td>
				                        					<input type="text"
																	style="width: 100%;text-align: center;"
																	value="<%= count + 1 %>" readonly="readonly">
				                        				</td>
														
														<td>
															<input type="hidden" name="yeucauID"
																	style="width: 100%;text-align: left;" value="">
				                        					<input type="text" name="yeucauTen"
																	style="width: 100%;text-align: left;" value="">
				                        				</td>
														
														
														<td align="center">
															<img
																		alt="Phương pháp thử nghiệm"
																		src="../images/vitriluu.png">
														</td>
															</tr>
														
														<%
																count++;
														 } 
												
													%>				
															
														</table>
													</div>
													<div id="tab_2" class="tabContents" style="display: none">
														<table width="100%" cellpadding="0" cellspacing="2">
															<tr class="tbheader">
																<th align="center" width="2%">STT</th>
																<th align="center" width="8%">Mã thiết bị(TSCD)</th>
																<th align="center" width="15%">Tên thiết bị (TSCD/CC)</th>
																<th align="center" width="12%">Ghi chú</th>
															</tr>
															
															<%
																int count1 = 0;
																	if (thietbiList != null) {
																	 if (thietbiList.size() > 0) {
																		for (int i = 0; i < thietbiList.size(); i++) {
																			IItemLoader thietbiKN = thietbiList.get(i); 
															%>


															<tr>
																<td><input type="text"
																	style="width: 100%; text-align: center;"
																	value="<%= count1 + 1 %>" readonly="readonly"></td>

																<td><input type="hidden" name="thietbiID"
																	style="width: 100%; text-align: left;"
																	value="<%= thietbiKN.getPk_seq() %>"> <input
																	type="text" name="thietbiMa"
																	style="width: 100%; text-align: left;"
																	value="<%= thietbiKN.getMa() %>"></td>

																<td>
																	<input
																	type="text" name="thietbiTen"
																	style="width: 100%; text-align: left;"
																	value="<%= thietbiKN.getTen() %>"></td>
																	
																<td>
																	<input
																	type="text" name="thietbiGhichu"
																	style="width: 100%; text-align: left;"
																	value="<%= thietbiKN.getGhiChu() %>"></td>
																
															</tr>
														
														<%
																count1++;
														 } 
													} 
												}
															%>
															
															
											<%
																
													for (int i = 0; i < 5; i++) {
																			
															%>
															
															
														<tr>
																<td><input type="text"
																	style="width: 100%; text-align: center;"
																	value="<%= count1 + 1 %>"></td>

																<td><input type="hidden" name="thietbiID"
																	style="width: 100%; text-align: left;"
																	value=""> <input
																	type="text" name="thietbiMa"
																	style="width: 100%; text-align: left;"
																	value=""></td>

																<td>
																	<input
																	type="text" name="thietbiTen"
																	style="width: 100%; text-align: left;"
																	value="" readonly="readonly"></td>
																	
																<td>
																	<input
																	type="text" name="thietbiGhichu"
																	style="width: 100%; text-align: left;"
																	value=""></td>
															</tr>
														
														<%
																count1++;
														 } 
												
													%>		
															
														</table>
													</div>
													
													
												</div>
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

<script type="text/javascript"> 
	replaces();
	jQuery(function()
		{		
			$("input[name=yeucauTen]").autocomplete("yeucauKNLIST_Ajax.jsp");	
			$("input[name=thietbiMa]").autocomplete("thietbiKNLIST_Ajax.jsp");
		});	

	 
	</script>

</BODY>
</HTML>
<%  /* if(dvttlist != null) dvttlist.close(); */
	tcknBean.DBclose();
}%>