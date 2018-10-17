<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet lncc = obj.getRsLoaiNCC();
	ResultSet ncc = obj.getRsNCC();
	ResultSet lsp = obj.GetRsLoaiSP();
	
	String[] lnccIds = obj.getLNccIds();	
	String[] nccIds = obj.getNccIds();
	String[] lspIds = obj.getLSPIds();
	
	String lncclist = "";
	String ncclist = "";
	String lsplist = "";
	
	if(lnccIds != null){
		if(lnccIds.length > 0){
			for(int i = 0; i < lnccIds.length; i++){
				if(lncclist.length() == 0){
					lncclist = lncclist + lnccIds[i];
				}else{
					lncclist = lncclist + ", " + lnccIds[i];
				}
			}
			System.out.println(lncclist);
		}
	}
	
	if(nccIds != null){
		if(nccIds.length > 0){
			for(int i = 0; i < nccIds.length; i++){
				if(ncclist.length() == 0){
					ncclist = ncclist + nccIds[i];
				}else{
					ncclist = ncclist + ", " + nccIds[i];
				}
			}
		}
	}
	
	if(lspIds != null){
		if(lspIds.length > 0){
			for(int i = 0; i < lspIds.length; i++){
				if(lsplist.length() == 0){
					lsplist = lsplist + lspIds[i];
				}else{
					lsplist = lsplist + ", " + lspIds[i];
				}
			}
		}
	}
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoHYERP - Project</TITLE>
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
	function search() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() 
	{
		document.forms['frm'].action.value = 'Taomoi';
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
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="frm" method="post" action="../../Erp_TheoDoiGiaMuaNCC">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý mua hàng > Theo dõi giá mua </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100%">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Theo dõi giá mua </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR >
									<TD class="plainlabel">Xem theo</TD>
									<TD class="plainlabel" colspan="3">
									<% 
								
									
									if(obj.gettype().trim().equals("1")){ %>
										<input type="radio" name="xemtheo" onchange="AnThang()" value="0" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="HienThang()" value="1" checked="checked"/>Tháng
									<%}else{ %>
										<input type="radio" name="xemtheo" onchange="AnThang()" value="0" checked="checked" />Ngày &nbsp;&nbsp;&nbsp;
										<input type="radio" name="xemtheo" onchange="HienThang()" value="1" />Tháng
									<%} %>
									</TD>
								</TR>
								<% if(obj.gettype().trim().equals("1") ){ %>
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
								<% if(obj.gettype().trim().equals("1")){ %>
									<TR id="XemThang">
								<%}else{ %>
									<TR id="XemThang" style="display: none">
								<%} %>
									<TD class="plainlabel">Từ tháng  </TD>
									<TD class="plainlabel">
										 <select name="tuthang"  style="width :50px" >
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getFromMonth().equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="tunam"  style="width :70px">
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2008;n<2025;n++){
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
									</TD>
									<TD class="plainlabel">Đến tháng</TD>
									<TD class="plainlabel">
										 <select name="denthang" style="width :50px">
									<option value=0> </option>  
									<%
  									
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getToMonth().equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="dennam"  style="width :70px">
									<option value=0> </option>  
									<%
									  
  									
  										for(n=2008;n<2025;n++){
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
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Loại nhà cung cấp</TD>
									<TD class="plainlabel">
										<select name="lnccId" id="lnccId" multiple="multiple"  onchange="search();">
										<% if(lncclist.length() == 0){ %>
											<option value="" selected>Tất cả</option>
										<% }else{ %>
											<option value="" >Tất cả</option>
										<% } %>
											<%if (lncc != null)
													while (lncc.next()) {
														if (lncclist.indexOf(lncc.getString("pk_seq")) >= 0) {%>
														<option value="<%=lncc.getString("pk_seq")%>" selected><%=lncc.getString("ten")%></option>
											<%			}else { %>
														<option value="<%=lncc.getString("pk_seq")%>"><%=lncc.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Nhà cung cấp </TD>
									<TD class="plainlabel">
										<select name="nccId" id="nccId" multiple="multiple" onchange="search();" >
										<% if(ncclist.length() == 0){ %>
											<option value="" selected>Tất cả</option>
										<% }else{ %>
											<option value="" >Tất cả</option>
										<% } %>

											<%if (ncc != null)
													while (ncc.next()) {
														if (ncclist.indexOf(ncc.getString("pk_seq")) >= 0) {%>
															<option value="<%=ncc.getString("pk_seq")%>" selected><%=ncc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=ncc.getString("pk_seq")%>"><%=ncc.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
								</TR>
								<TR >
									<TD class="plainlabel">Loại sản phẩm</TD>
									<TD class="plainlabel">
										<select name="lspId" id="lspId"  multiple="multiple"  onchange="search();">
										<% if(lsplist.length() == 0){ %>
											<option value="" selected>Tất cả</option>
										<% }else{ %>
											<option value="" >Tất cả</option>
										<% } %>
											
											<%if (lsp != null)
													while (lsp.next()) {
														if (lsplist.indexOf(lsp.getString("pk_seq")) >= 0) {%>
													<option value="<%=lsp.getString("pk_seq")%>" selected><%=lsp.getString("ten")%></option>
												<%} else {%>
													<option value="<%=lsp.getString("pk_seq")%>"><%=lsp.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a></td>
								</TR>
							</TABLE>
						</div> 
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	<%
	if(lncc != null) lncc.close();
	if(ncc != null) ncc.close();
	if(lsp != null) lsp.close();
	obj.DBclose();
	%>
</body>
</html>