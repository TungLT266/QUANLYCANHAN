<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="geso.traphaco.center.beans.stockintransit.imp.Stockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% 
IStockintransit obj = (Stockintransit) session.getAttribute("obj");
   ResultSet kbhRs = obj.getkenh();
   ResultSet vungRs = obj.getvung();
   ResultSet khuvucRs = obj.getkhuvuc();
   ResultSet nppRs = obj.getnpp();
   ResultSet dvkdRs = obj.getdvkd();
   ResultSet nhanhangRs = obj.getnhanhang();
   ResultSet chungloaiRs = obj.getchungloai();
   ResultSet spRs = obj.getsanpham();
  ResultSet gsbhRs = obj.getgsbh();
 
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

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>


<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
function seach()
{
	document.forms['frm'].action.value= 'seach';
	document.forms["frm"].submit();
}
	function submitform() 
	{
		document.forms['frm'].action.value= 'Excel';
		document.forms["frm"].submit();
		
	}	
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
	$("#nhanhangId").select2();
	$("#chungloaiId").select2();
	$("#programs").select2();
	$("#dvkdId").select2();
	$("#gsbhId").select2();
	$("#kenhId").select2();
	$("#spId").select2();
});
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../XuatNhapTonStoreSvl">
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">&#160; Quản lý tồn kho &#62; Báo cáo &#62; Báo cáo xuất - nhập - tồn (Store)</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen %></div>
			</div>
				<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Tiêu chí lấy báo cáo</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR id="TheoThang">
									<TD class="plainlabel">Từ tháng</TD>
									<TD class="plainlabel">
									 <select name="fromMonth"  style="width :50px" onchange="loctien()">
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
									<select name="fromYear"  style="width :50px" onchange="loctien()">
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
									</TD>
								
									<TD class="plainlabel">Tới tháng</TD>
									<TD class="plainlabel">
									 <select name="toMonth" style="width :50px" onchange="loctien()">
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
									<select name="toYear"  style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
									  
  									
  										for(n=2010;n<2025;n++){
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
								
								<TD class="plainlabel">Kênh bán hàng</TD>
									
									<TD class="plainlabel">
										<select name="kbhId" id="kbhId" onchange="seach();" style="width:250px">
												<option value="" selected>All</option>
												<% if(kbhRs != null)
												   while(kbhRs.next()){
												   if(kbhRs.getString("pk_seq").equals(obj.getkenhId()))
												   { %>
												   <option value="<%= kbhRs.getString("pk_seq") %>" selected><%=kbhRs.getString("diengiai") %></option>
												   <%}else { %>
												   <option value="<%= kbhRs.getString("pk_seq") %>"><%=kbhRs.getString("diengiai") %></option>
												<%} }%>
										</select>
									</TD>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel"><select name="dvkdId" id="dvkdId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<% if(dvkdRs != null)
											   while(dvkdRs.next()){
											   if(dvkdRs.getString("pk_seq").equals(obj.getdvkdId()))
											   { %>
											   <option value="<%= dvkdRs.getString("pk_seq") %>" selected><%=dvkdRs.getString("diengiai") %></option>
											   <%}else { %>
											   <option value="<%= dvkdRs.getString("pk_seq") %>"><%=dvkdRs.getString("diengiai") %></option>
											<%} }%>
									</select>
									</TD>
							
									</TR>
									<TR>
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<% if(vungRs != null)
											   while(vungRs.next()){
											   if(vungRs.getString("pk_seq").trim().equals(obj.getvungId()))
											   { %>
											   <option value="<%= vungRs.getString("pk_seq") %>" selected><%=vungRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= vungRs.getString("pk_seq") %>"><%=vungRs.getString("ten") %></option>
											<%} }%>
										</select>
										</TD>
									<TD class="plainlabel">Nhãn hàng</TD>
									<TD class="plainlabel">
										<select name="nhanhangId" id="nhanhangId" onchange="seach();" style="width:250px">
												<option value="" selected>All</option>
													<% if(nhanhangRs != null)
												   while(nhanhangRs.next()){
												   if(nhanhangRs.getString("pk_seq").equals(obj.getnhanhangId()))
												   { %>
												   <option value="<%= nhanhangRs.getString("pk_seq") %>" selected><%=nhanhangRs.getString("ten") %></option>
												   <%}else { %>
												   <option value="<%= nhanhangRs.getString("pk_seq") %>"><%=nhanhangRs.getString("ten") %></option>
												<%} }%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<% if(khuvucRs != null)
											   while(khuvucRs.next()){
											   if(khuvucRs.getString("pk_seq").equals(obj.getkhuvucId()))
											   { %>
											   <option value="<%= khuvucRs.getString("pk_seq") %>" selected><%=khuvucRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= khuvucRs.getString("pk_seq") %>"><%=khuvucRs.getString("ten") %></option>
											<%} }%>
									</select>
									</TD>
									<TD class="plainlabel">Chủng Loại</TD>
									<TD class="plainlabel">
									<select name="chungloaiId" id="chungloaiId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
											<% if(chungloaiRs != null)
											   while(chungloaiRs.next()){
											   if(chungloaiRs.getString("pk_seq").equals(obj.getchungloaiId()))
											   { %>
											   <option value="<%= chungloaiRs.getString("pk_seq") %>" selected><%=chungloaiRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= chungloaiRs.getString("pk_seq") %>"><%=chungloaiRs.getString("ten") %></option>
											<%} }%>
									</select>
									</TD>
								</TR>
                             <TR>
                             <TD class="plainlabel">Chi nhánh / Đối tác</TD>
									<TD class="plainlabel"><select name="nppId" id="nppId" style="width:250px">
											<option value="" selected>All</option>
											<% if(nppRs != null)
											   while(nppRs.next()){
											   if(nppRs.getString("pk_seq").equals(obj.getnppId()))
											   { %>
											   <option value="<%= nppRs.getString("pk_seq") %>" selected><%=nppRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= nppRs.getString("pk_seq") %>"><%=nppRs.getString("ten") %></option>
											<%} }%>
									</select></TD>
	                           </TR>
								<TR>									
									<TD class="plainlabel">Tên Sản Phẩm</TD>
									<TD class="plainlabel"><select name="spId" id="spId" onchange="seach();" style="width:250px">
											<option value="" selected>All</option>
												<% if(spRs != null)
											   while(spRs.next()){
											   if(spRs.getString("pk_seq").equals(obj.getsanphamId()))
											   { %>
											   <option value="<%= spRs.getString("pk_seq") %>" selected><%=spRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= spRs.getString("pk_seq") %>"><%=spRs.getString("ten") %></option>
											<%} }%>
									</select>
									</TD>
								</TR>
								
								
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
								</TR>
						</TABLE>
						</div>
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />		
	</form>
</body>
</html>

<%
	if(chungloaiRs != null) chungloaiRs.close(); 
	if (dvkdRs != null) dvkdRs.close(); 
	if (nhanhangRs != null) nhanhangRs.close(); 
	if(gsbhRs != null) gsbhRs.close();
	if(kbhRs != null) kbhRs.close();
	if(khuvucRs != null) khuvucRs.close();
	if(nppRs != null) nppRs.close();
	if(vungRs != null) vungRs.close();
	if(spRs != null) spRs.close();
	if(obj != null)  obj.DBclose();
	obj = null;
	session.setAttribute("obj", null);
}
%>