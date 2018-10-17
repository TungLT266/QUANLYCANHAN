<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
  	ResultSet kenh = obj.getkenh();
   	ResultSet vung = obj.getvung();
   	ResultSet khuvuc = obj.getkhuvuc();
   	ResultSet npp = obj.getnpp();
   	ResultSet dvkd = obj.getdvkd();

   	ResultSet dvdl = obj.getdvdl();
   	ResultSet sanpham = obj.getsanpham();
  	ResultSet gsbh = obj.getgsbh();
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

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

function submitform()
{
	document.forms['rpForm'].action.value="taomoi";
	document.forms['rpForm'].dataerror.value="";
	document.forms['rpForm'].submit();
}
function seach(){
	document.forms['rpForm'].action.value="search";
	document.forms['rpForm'].submit();
}

function toPdf() 
{
	document.forms['rpForm'].action.value = 'toPdf';
	document.forms["rpForm"].submit();
}


</SCRIPT>
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
});
</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../DailyStockTTSvl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&#160; Quản lý kho chi nhánh &#62; Báo cáo &#62; Tồn kho tháng </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
					<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
						<TR>
							<TD width="100%" align="center" >
							<FIELDSET>
							  <LEGEND class="legendtitle">Thời gian xuất báo cáo</LEGEND>
							<TABLE  width="100%" cellpadding="6px" cellspacing="0px">
							
							<TR id="XemThang">
									<TD class="plainlabel"  width="10%" >Từ tháng</TD>
									<TD class="plainlabel"  width="20%" >
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
									<select name="tunam"  style="width :50px" ">
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2014;n<2025;n++){
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
									
									<TD class="plainlabel" width="10%" >Tới tháng</TD>
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
									<select name="dennam"  style="width :50px">
									<option value=0> </option>  
									<%
									  
  									
  										for(n=2014;n<2025;n++){
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
							
							
								<%-- <TR>
								  	<TD class="plainlabel" width="10%">Từ ngày</TD>
									<TD class="plainlabel" width="20%">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' />
									</TD>
									<TD class="plainlabel" width="10%">Đến ngày</TD>
									<td class="plainlabel">
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' />
									</td>
								</TR> --%>
								
								<TR>
									<TD class="plainlabel">Kênh</TD>
									
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();" style="width:202px">
												<option value="" selected>All</option>
												<% if(kenh != null)
												   while(kenh.next()){
												   if(kenh.getString("pk_seq").equals(obj.getkenhId()))
												   { %>
												   <option value="<%= kenh.getString("pk_seq") %>" selected><%=kenh.getString("diengiai") %></option>
												   <%}else { %>
												   <option value="<%= kenh.getString("pk_seq") %>"><%=kenh.getString("diengiai") %></option>
												<%} }%>
										</select>
									<TD class="plainlabel" width="130px">Đơn vị kinh doanh</TD>
									<TD class="plainlabel"><select name="dvkdId" id="dvkdId" style="width:202px">
											<option value="" selected>All</option>
											<% if(dvkd != null)
											   while(dvkd.next()){
											   if(dvkd.getString("pk_seq").equals(obj.getdvkdId()))
											   { %>
											   <option value="<%= dvkd.getString("pk_seq") %>" selected><%=dvkd.getString("diengiai") %></option>
											   <%}else { %>
											   <option value="<%= dvkd.getString("pk_seq") %>"><%=dvkd.getString("diengiai") %></option>
											<%} }%>
									</select>
									</TD>
									
								</TR>
								<TR>
								<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();"  style="width:202px">
											<option value="" selected>All</option>
											<% if(vung != null)
											   while(vung.next()){
											   if(vung.getString("pk_seq").equals(obj.getvungId()))
											   { %>
											   <option value="<%= vung.getString("pk_seq") %>" selected><%=vung.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= vung.getString("pk_seq") %>"><%=vung.getString("ten") %></option>
											<%} }%>
										</select>
										</TD>
										<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
									<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:202px">
											<option value="" selected>All</option>
											<% if(khuvuc != null)
											   while(khuvuc.next()){
											   if(khuvuc.getString("pk_seq").equals(obj.getkhuvucId()))
											   { %>
											   <option value="<%= khuvuc.getString("pk_seq") %>" selected><%=khuvuc.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= khuvuc.getString("pk_seq") %>"><%=khuvuc.getString("ten") %></option>
											<%} }%>
									</select>
									</TD>
									</TR>
					
								<TR>									
									<TD class="plainlabel">Chi nhánh / Đối tác</TD>
									<TD class="plainlabel" colspan="3"><select name="nppId" id="nppId" style="width:202px">
											<option value="" selected >All</option>
											<% if(npp != null)
											   while(npp.next()){
											   if(npp.getString("pk_seq").equals(obj.getnppId()))
											   { %>
											   <option value="<%= npp.getString("pk_seq") %>" selected><%=npp.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= npp.getString("pk_seq") %>"><%=npp.getString("ten") %></option>
											<%} }%>
									</select></TD>	
								</TR>

							    <TR>
									<TD  class="plainlabel" colspan="4">
									<a class="button2" href="javascript:submitform()" >
										<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo </a>
										
										<!-- <A href="javascript: toPdf();" ><img src="../images/pdf.jpg" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu" border=1 style="border-style:outset"></A> -->
									</TD>
									
								</TR>
							</TABLE>
							</FIELDSET>	</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<% 

	if (dvkd != null) dvkd.close();

	if (dvdl != null) dvdl.close();  
	if(gsbh != null) gsbh.close();
	if(kenh != null) kenh.close();
	if(khuvuc != null) khuvuc.close();
	if(npp != null) npp.close();
	if(vung != null) vung.close();
	if(sanpham != null) sanpham.close();
	if(obj != null)  obj.DBclose();
	obj = null;
	session.setAttribute("obj", null);
}
%>