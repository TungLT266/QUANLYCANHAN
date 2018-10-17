﻿<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String groupCustomer = (String)session.getAttribute("groupCustomer");
	String gorupSKU = (String)session.getAttribute("gorupSKU");
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet nhanhang = obj.getnhanhang();
	ResultSet chungloai = obj.getchungloai();
	ResultSet dvdl = obj.getdvdl();
	ResultSet sanpham = obj.getsanpham();
	ResultSet gsbh = obj.getgsbh();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

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
		$(".button").hover(function() {
			$(".button img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
				top : "-10px"
			}, 200).animate({
				top : "-4px"
			}, 200) // first jump
			.animate({
				top : "-7px"
			}, 100).animate({
				top : "-4px"
			}, 100) // second jump
			.animate({
				top : "-6px"
			}, 100).animate({
				top : "-4px"
			}, 100); // the last jump
		});
	});
</script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() 
	{
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
	}
	
	function toPdf() 
	{
		document.forms['frm'].action.value = 'toPdf';
		document.forms["frm"].submit();
	}
	
	
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	
	function AnThang()
	{
		document.getElementById("XemNgay").style.display = "";
		document.getElementById("XemThang").style.display = "none";
	}
	function HienThang()
	{
		document.getElementById("XemThang").style.display = "";
		document.getElementById("XemNgay").style.display = "none";
	}
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$(".select2").select2();
	
});
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="frm" method="post" action="../../DailyPrimarySales">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62; Doanh số &#62; Mua hàng</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo mua hàng </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Xem theo</TD>
									<TD class="plainlabel" colspan="3">
									<% System.out.println("FromMonth : "+obj.getFromMonth()+" - ToMonth : "+obj.getToMonth());
									   if(obj.getFromMonth() != "" && obj.getToMonth() != ""){ %>
										<input type="radio" name="xemtheo" onchange="AnThang()" value="0" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="HienThang()" value="1" checked="checked"/>Tháng
									<%}else{ %>
										<input type="radio" name="xemtheo" onchange="AnThang()" value="0" checked="checked" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="HienThang()" value="1" />Tháng
									<%} %>
									</TD>
								</TR>
								<% if(obj.getFromMonth() != "" && obj.getToMonth() != ""){ %>
									<TR id="XemNgay"  style="display: none">
								<%} else { %>
									<TR id="XemNgay" >
								<%} %>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								<% if(obj.getFromMonth() != "" && obj.getToMonth() != ""){ %>
									<TR id="XemThang">
								<%}else{ %>
									<TR id="XemThang" style="display: none">
								<%} %>
										<TD class="plainlabel">Từ tháng</TD>
									<TD class="plainlabel">
									 <select name="tuthang"  style="width :50px" >
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getFromMonth().equals(chuoi)){
  									%>
									<option value=<%=chuoi%> selected="selected" > <%=chuoi%></option> 
									<%
 										}else{
 									%>
									<option value=<%=chuoi%> ><%=chuoi%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="tunam"  style="width: 50px;" >
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2008;n<2015;n++){
  										if(obj.getFromYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select>
									</TD>
								
									<TD class="plainlabel">Tới tháng</TD>
									<TD class="plainlabel">
									 <select name="denthang" style="width :50px" ">
									<option value=0> </option>  
									<%
  									
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getToMonth().equals(chuoi)){
  									%>
									<option value=<%=chuoi%> selected="selected" > <%=chuoi%></option> 
									<%
 										}else{
 									%>
									<option value=<%=chuoi%> ><%=chuoi%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="dennam"  style="width :50px">
									<option value=0> </option>  
									<%
									  
  									
  										for(n=2008;n<2015;n++){
  										if(obj.getToYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();" style="width:250px" class="select2" >
											<option value="" selected>All</option>
											<%if (kenh != null)
													while (kenh.next()) {
														if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
														<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
											<%} else { %>
												<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">PTT / GĐ CN 2</TD>
									<TD class="plainlabel">
										<select name="gsbhs" id="gsbhId" onchange="seach();" style="width:250px">
												<option value="" selected>All</option>
												<%if (gsbh != null)
														while (gsbh.next()) {
															if (gsbh.getString("pk_seq").equals(obj.getgsbhId())) {%>
														<option value="<%=gsbh.getString("pk_seq")%>" selected>
															<%=gsbh.getString("ten")%></option>
												<%} else {%>
														<option value="<%=gsbh.getString("pk_seq")%>"><%=gsbh.getString("ten")%></option>
												<%}}%>
										</select>
									</td>
								</TR>
								<TR>
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%if (vung != null)
													while (vung.next()) {
														if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
													<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Chi nhánh / Đối tác </TD>
									<TD class="plainlabel">
										<select name="nppId" onchange="seach();" id="nppId" style="width:250px">
											<option value="" selected>All</option>
											<%if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {%>
															<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
													<%} else {%>
														<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"><!-- Nhãn hàng --></TD>
									<TD class="plainlabel">
										<%-- <select name="nhanhangId" id="nhanhangId" onchange="seach();" style="width:250px">
												<option value="" selected>All</option>
												<%if (nhanhang != null)
														while (nhanhang.next()) {
															if (nhanhang.getString("pk_seq")
																	.equals(obj.getnhanhangId())) {	%>
															<option value="<%=nhanhang.getString("pk_seq")%>" selected><%=nhanhang.getString("ten")%></option>
														<%} else {%>
															<option value="<%=nhanhang.getString("pk_seq")%>"><%=nhanhang.getString("ten")%></option>
												<%}}%>
										</select> --%>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select name="dvkdId" id="dvkdId"	onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
														<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"><!-- Chủng loại --></TD>
									<TD class="plainlabel">
										<%-- <select name="chungloaiId" id="chungloaiId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<%if (chungloai != null)
													while (chungloai.next()) {
														if (chungloai.getString("pk_seq").equals(obj.getchungloaiId())) {%>
														<option value="<%=chungloai.getString("pk_seq")%>" selected><%=chungloai.getString("ten")%></option>
													<%} else {%>
														<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
												<% }}%>
										</select> --%>
									</TD>
								</TR>
								<%if(obj.getkenhId().equals("100052")){ %>
								<TR>
									<TD class="plainlabel">Mức lấy</TD>
									<TD class="plainlabel">
										<select name="muclay" onchange="seach();">
											<option value="0">All </option>
											<%if(obj.getMuclay().equals("1")){ %>
											<option value="1" selected="selected">Tổng thầu </option>
											<option value="2">Store </option>
											<%}else if(obj.getMuclay().equals("2")) {%>
											<option value="1" >Tổng thầu </option>
											<option value="2" selected="selected">Store </option>
											<%}else { %>
											<option value="1" >Tổng thầu </option>
											<option value="2" >Store </option>
											<%} %>
										</select>
									</TD>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel"></TD>
								</TR>	
								<%} %>
															
								<TR>									
									<TD class="plainlabel">Trừ chiết khấu</TD>
									<TD class="plainlabel">
										<%
											if(obj.getdiscount().equals("1")){%>
												<input type="radio" name="discount"	value="1"  checked="checked" />Có &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0" />Không
											<%}else{%>
												<input type="radio" name="discount"	value="1"  />Có &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0"  checked="checked"/>Không
											<%}%>										
									</TD>									
								</TR>
								<tr>
									<TD class="plainlabel"> VAT</TD>
									<TD class="plainlabel">
										<%if(obj.getvat().equals("1")){%>
													<input type="radio" value="1"	name="vats" checked="checked" />Có &nbsp;&nbsp;&nbsp; 
													<input type="radio" value="0" name="vats" />Không
										<%}else{%> 
													<input type="radio" value="1"	name="vats" />Có &nbsp;&nbsp;&nbsp; 
													<input type="radio" value="0" name="vats" checked="checked"  />Không
										<%}%>
									</TD>
									
								</tr>
								<TR style="display:none">
									<TD class="plainlabel">Bao gồm khuyến mại:</TD>
									<%if(obj.getpromotion().trim().equals("1")) { %>
										<TD class="plainlabel">
											<input type="radio" name="promotion" id="promotion" value="1" checked="checked"/> Có &nbsp;&nbsp;&nbsp;
											<input type="radio" name="promotion" value="0" /> Không
										</TD>
									<%} else { %>
										<TD class="plainlabel">
											<input type="radio" name="promotion" id="promotion" value="1" /> Có &nbsp;&nbsp;&nbsp;
											<input type="radio" name="promotion" value="0" checked="checked"/> Không
										</TD>
									<%} %>
								</TR>
								<TR>
									<td ><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a></td>
									<TD width="30" align="left" ><A href="javascript: toPdf();" ><img src="../images/pdf.jpg" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A></TD>
								</TR>
							</TABLE>
							</div>
					
						</div> 
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	<%
		if(kenh !=null)kenh.close();
		if(vung!=null)vung.close();
		if(khuvuc!=null)khuvuc.close();
		if(npp!=null)npp.close();
		if(dvkd!=null)dvkd.close();
		if(nhanhang!=null)nhanhang.close();
		if(chungloai!=null)chungloai.close();
		if(dvdl!=null)dvdl.close();
		if(sanpham!=null)sanpham.close();
		if(gsbh!=null)gsbh.close();
		if(obj!=null){obj.DBclose();
		obj = null;	}
		session.setAttribute("obj",null);
	%>
</body>
</html>