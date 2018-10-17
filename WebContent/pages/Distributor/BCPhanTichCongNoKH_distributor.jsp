<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% 	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet ddkds = obj.getRsddkd();
	//ResultSet npp = obj.getnpp();
	ResultSet kh = obj.getkh();
	ResultSet dvkd = obj.getdvkd();
	ResultSet dvdl = obj.getdvdl();   
	ResultSet gsbh = obj.getgsbh();
	ResultSet nvgnRs = obj.getNvgnRs();
	
    String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

<SCRIPT language="javascript" type="text/javascript">

function submitform()
{
	if (document.forms["rpForm"]["tuNgay"].value.length == 0) {
		setErrors("Vui lòng chọn ngày bắt đầu");
		return;
	}
	if (document.forms["rpForm"]["denNgay"].value.length == 0) {
		setErrors("Vui lòng chọn ngày kết thúc");
		return;
	}			
	var fieldShow = document.getElementsByName("fieldsHien");		
	for ( var i = 0; i < fieldShow.length; ++i) {
		fieldShow.item(i).checked = true;
	}	
	document.forms['rpForm'].action.value="taomoi";
	document.forms['rpForm'].submit();
	reset();
}
function seach(){
	document.forms['rpForm'].action.value="search";
	document.forms['rpForm'].submit();
}


function setErrors(errorMsg) {
	var errors = document.getElementById("errors");
	errors.value = errorMsg;
}

function reset() {
	var fieldShow = document.getElementsByName("fieldsHien");		
	for ( var i = 0; i < fieldShow.length; ++i) {
		fieldShow.item(i).checked = false;
	}		
};

</SCRIPT>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../BCPhanTichCongNoKHSvl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>
<input type="hidden" name="view" value='NPP'>
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&#160; Quản lý công nợ &#62; Báo cáo &#62; BC Phân tích công nợ KH </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%= userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
							<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
						<%= obj.getMsg() %>
					</textarea>
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
								<TR>
								  	<TD width="19%" class="plainlabel" >Từ ngày</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input type="text" class="days" name="tuNgay" size="20" style="width:230px" value = "<%=obj.gettungay()%>" >
												</TD>
												<TD>		   										
												</TD>
                                    		</TR>
										</TABLE>									</TD>
								
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input type="text"  class="days" name="denNgay" size="20" style="width:230px" value = "<%=obj.getdenngay()%>" >												</TD>
												<TD>                                        		</TD>
                                     		</TR>
										</TABLE>									</TD>
								</TR>
								
<%-- 								<TR>
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
									</TR> --%>
							
								
								<TR>			
								<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" onchange="seach();" style="width:230px">
												<option value="" selected>All</option>
												<%if (ddkds != null)
														while (ddkds.next()) {
															if (ddkds.getString("pk_seq").equals(obj.getDdkd())) {%>
														<option value="<%=ddkds.getString("pk_seq")%>" selected>
															<%=ddkds.getString("ten")%></option>
												<%} else {%>
														<option value="<%=ddkds.getString("pk_seq")%>"><%=ddkds.getString("ten")%></option>
												<%}}%>
										</select>
									</td>
															
									<TD class="plainlabel">Khách hàng</TD>
									<TD class="plainlabel" colspan="3"><select name="khId" id="khId" style="width:230px">
											<option value="" selected >All</option>
											<% if(kh != null)
											   while(kh.next()){
											   if(kh.getString("pk_seq").equals(obj.getkhId()))
											   { %>
											   <option value="<%= kh.getString("pk_seq") %>" selected><%=kh.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= kh.getString("pk_seq") %>"><%=kh.getString("ten") %></option>
											<%} }%>
									</select></TD>	
								</TR>

	<TR>	
									<TD class="plainlabel">Nhân viên giao nhận </TD>
									<TD class="plainlabel" colspan="6"><select name="nvgnId" id="nvgnId" style="width:230px">
											<option value="" selected >All</option>
											<% if(nvgnRs != null)
											{
												try
			                        			{	
											   while(nvgnRs.next()){
											   if(nvgnRs.getString("pk_seq").equals(obj.getNvgnId()  ))
											   { %>
											   <option value="<%= nvgnRs.getString("pk_seq") %>" selected><%=nvgnRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= nvgnRs.getString("pk_seq") %>"><%=nvgnRs.getString("ten") %></option>
											<%} }nvgnRs.close();} catch(SQLException ex){}%>
											<%} %>
									</select></TD>	
								</TR>
							    <TR>
									<TD  class="plainlabel" colspan="4">
									<a class="button2" href="javascript:submitform()" >
										<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo </a>
										
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
	if(kh != null) kh.close();
	if(vung != null) vung.close();
	//if(sanpham != null) sanpham.close();
	if(obj != null)  obj.DBclose();
	obj = null;
	session.setAttribute("obj", null);
}
%>