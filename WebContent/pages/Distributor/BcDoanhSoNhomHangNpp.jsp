<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.reports.IBcDoanhSoNhomHangNpp"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBcDoanhSoNhomHangNpp obj = (IBcDoanhSoNhomHangNpp) session.getAttribute("obj");
	ResultSet ddkd = obj.getDdkdRs();
	ResultSet khRs = obj.getKhRs();
	ResultSet spRs = obj.getSpRs();
	ResultSet hdRs = obj.getHoadonRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet totalRs = obj.getTotalRs();
	
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("BcDoanhSoNhomHangNppSvl","");

	ResultSet vungRs = obj.getVungRs();
	ResultSet ttRs = obj.getTtRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet nhomRs = obj.getNhomRs();
	
	String[] spNhomId = obj.getSpNhomId();
	String[] spNhomTen = obj.getSpNhomTen();
	
	
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
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
</script>

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">

function clearform()
{   
	document.forms["ckParkForm"].Sdays.value="";
	document.forms["ckParkForm"].Edays.value="";
	document.forms["ckParkForm"].khId.value="";
	document.forms["ckParkForm"].spId.value="";
	document.forms["ckParkForm"].ddkdId.value="";
	document.forms["ckParkForm"].kbhId.value="";
   document.forms["ckParkForm"].submit();
}

function submitform()
{   
	document.forms['ckParkForm'].action.value='';
   document.forms["ckParkForm"].submit();
}

function xuatExcel()
{
	document.forms['ckParkForm'].action.value= 'excel';
	document.forms['ckParkForm'].submit();
	
}

function search()
{
	
	document.forms['ckParkForm'].action.value= 'search';
	document.forms['ckParkForm'].submit();
	
}



</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ckParkForm" method="post" action="../../BcDoanhSoNhomHangNppSvl">
		<input type="hidden" name="action" value=''>
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
		<% obj.setNextSplittings(); %>
		<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
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
					<legend class="legendtitle">Doanh thu bán hàng </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
							<TR >
									<TD class="plainlabel" width="120px">Từ ngày</TD>
									<TD class="plainlabel" width="250px" >
								 	<input type="text" name="Sdays"	class="days" value='<%=obj.getTuNgay()%>'  /></TD> 
									<TD class="plainlabel" width="100px" > Đến ngày</TD>
									<TD class="plainlabel" colspan="3">
										<input type="text" name="Edays" class="days" value='<%=obj.getDenNgay()%>'   /></td> 
						   </TR>
						   
						   
						   <TR>
									<TD class="plainlabel">Miền  </TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (vungRs != null)
													while (vungRs.next()) {
														if (vungRs.getString("pk_seq").equals(obj.getVungId()  )) {%>
											   <option value="<%=vungRs.getString("pk_seq")%>" selected><%=vungRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=vungRs.getString("pk_seq")%>"><%=vungRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel">  Kênh bán hàng </TD>
									<TD class="plainlabel" colspan="3">
										<select name="kbhId" id="kbhId" style="width:250px"  onchange="submitform();"  >
											<option value="" >All</option>
											<%if (kbhRs != null)
													while (kbhRs.next()) {
														if (kbhRs.getString("pk_Seq").equals(obj.getKbhId())) {%>
											   <option value="<%=kbhRs.getString("pk_Seq")%>" selected><%=kbhRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=kbhRs.getString("pk_Seq")%>"><%=kbhRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Địa bàn  </TD>
									<TD class="plainlabel">
										<select name="ttId" id="ttId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (ttRs != null)
													while (ttRs.next()) {
														if (ttRs.getString("pk_seq").equals(obj.getTtId()  )) {%>
											   <option value="<%=ttRs.getString("pk_seq")%>" selected><%=ttRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=ttRs.getString("pk_seq")%>"><%=ttRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel"> Chi nhánh/ĐT </TD>
									<TD class="plainlabel" colspan="3">
										<select name="nppId" id="nppId" style="width:250px"  onchange="submitform();" >
											<option value="" >All</option>
											<%if (nppRs != null)
													while (nppRs.next()) {
														if (nppRs.getString("pk_seq").equals(obj.getNppId()   )) {%>
											   <option value="<%=nppRs.getString("pk_seq")%>" selected><%=nppRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=nppRs.getString("pk_seq")%>"><%=nppRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								
								
								<TR>
									<TD class="plainlabel">Trình dược viên </TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" style="width:250px"  >
											<option value="" >All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getDdkdId()  )) {%>
											   <option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel"> </TD>
									<TD class="plainlabel" colspan="3">
									</TD>
								</TR>
								
								
								
														   
					<TR>
						<TD class="plainlabel" colspan="6">
								<a class="button2" href="javascript:search();"> 
									<img style="top: -4px;" src="../images/button.png" alt=""> Tìm kiếm</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="button2" href="javascript:xuatExcel();"> 
								<img style="top: -4px;" src="../images/button.png" alt="">Xuất dữ liệu </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR> 
			
								
							</TABLE>
						</div>
			</div>
			</fieldset>
		</div>
		
		
		
		<TABLE width="100%" cellpadding="0" cellspacing="1">
			    	<TR>
						<TD align="left" >
							<FIELDSET><LEGEND class="legendtitle">&nbsp;Doanh số&nbsp;&nbsp;&nbsp;</LEGEND>
							<TABLE class="" width="100%" border="0" cellpadding="0" cellspacing="0">
								<TR>
									<TD width="98%">
										<TABLE width="100%" border="0" cellpadding="6">
											<TR class="tbheader">
												<TH width="4%" rowspan="2">STT</TH>
												<TH width="6%" rowspan="2">Miền</TH>
											    <TH width="7%" rowspan="2">Tỉnh/Tp </TH>
											    <TH width="18%" rowspan="2">Chi nhánh / Đối tác</TH>
											    
											    <%for(int i=0;i<spNhomId.length;i++) { %>											    
											    	<TH width="18%" colspan="2"><%=spNhomTen[i] %>   </TH>
											    <%}%>
											  	
											  	<TH width="11%" rowspan="2">DS Tổng(%)</TH>
										  	</TR>
										  	<TR class="tbheader">
										  	 <%for(int i=0;i<spNhomId.length;i++) { %>											    
											    	<TH width="9%">DS</TH>										
											  		<TH width="9%">Tỷ lệ/DS (%)</TH>
											    <%}%>											  		
										  	</TR>
										<% 						
										int m = 0;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										try{
											if(hdRs != null)
											while (hdRs.next())
											{
												double total_DoanhSo=0;
												for(int i=0;i<spNhomId.length;i++)
													total_DoanhSo+=hdRs.getDouble(spNhomId[i].trim());
												if (m % 2 != 0) {%>						
													<TR class= <%=lightrow%> >
												<%} else {%>
													<TR class= <%= darkrow%> >
												<%}%>
													<TD align="center"><%=m+1%></TD>
													<TD align="center"><%=hdRs.getString("kvTEN")%></TD>
													<TD align="center"><%=hdRs.getString("ttTEN")%></TD>
													<TD align="left"><div align="left"><%=hdRs.getString("nppten") %></div></TD> 
													<%for(int i=0;i<spNhomId.length;i++)
													{ %>
														<TD align="center"><%=formatter.format( hdRs.getDouble(spNhomId[i].trim()))%></TD>
														<TD align="center"><%=formatter.format( ( hdRs.getDouble(spNhomId[i].trim()) / total_DoanhSo )*100    )%></TD>
													<%} %>
														<TD align="center"><%=formatter.format(total_DoanhSo )%></TD>
													                                  
												</TR>
												<%m++; } 
											}catch(Exception er){er.printStackTrace();} %>
								
											<TR>	
												<TD height="" colspan="11" align="center" class="tbfooter">&nbsp;</TD>
											</TR>
									</TABLE>
								</TD>
							</TR>
						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
		</TABLE>
		
		
		
		
	</div>
	</form>
	<%
		if(obj!=null){obj.closeDB();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>