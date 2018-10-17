<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet rsKenh = obj.getkenh();
	ResultSet rsKhuVuc = obj.getkhuvuc();
	ResultSet rsVung = obj.getvung();	
	ResultSet rsNpp = obj.getnpp();		
	ResultSet rsNhans = obj.getnhanhang();
	ResultSet rsChungLoai = obj.getchungloai();
	ResultSet rsDVKD = obj.getdvkd();
	ResultSet rsProgram = obj.getRsPrograms();
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OneOne - Project</TITLE>
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
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function search(){
		document.forms["frm"]["action"].value = "search";
		document.forms["frm"].submit();
	}	
	
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}			
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}		
		document.forms["frm"]["action"].value = "create";
		document.forms["frm"].submit();
		reset();
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
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../UsingPromoTT">
		<input type="hidden" value="1" name="action"  >
		<%-- <input type="hidden" name="nppId" value="<%= obj.getnppId() %>" > --%>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý khuyến mãi &#62; Báo cáo &#62; Sử dụng và chi trả khuyến mãi</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn&nbsp;<%=userTen %></div>
			</div>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Sử dụng và chi trả khuyến mãi</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel" width="17%">Từ ngày</TD>
									<TD class="plainlabel"><input onchange="search();" type="text" name="Sdays"
										class="days" value="<%=obj.gettungay() %>" /></TD>
									<TD class="plainlabel" width="13%">Đến ngày</TD>
									<TD class="plainlabel"><input type="text" name="Edays" onchange="search();"
										class="days" value="<%=obj.getdenngay() %>" /></TD>
								</TR>
									<TR>
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select  name="vung" onchange="search();">
											<option value="">All</option>
											<% if(rsVung!=null){
										 		while(rsVung.next()){
													String vungId = rsVung.getString("pk_seq");
													if(vungId.equals(obj.getvungId())){%>
														<option selected="selected" value="<%=rsVung.getString("pk_seq")%>">
															<%=rsVung.getString("ten")%></option>
													<%}else{%>
														<option value="<%=rsVung.getString("pk_seq")%>">
															<%=rsVung.getString("ten")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>
									
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
										<select  name="khuvuc" onchange="search();">
											<option value="">All</option>
											<% if(rsKhuVuc!=null){
										 		while(rsKhuVuc.next()){
													String kvId = rsKhuVuc.getString("pk_seq");
													if(kvId.equals(obj.getkhuvucId())){%>
														<option selected="selected" value="<%=rsKhuVuc.getString("pk_seq")%>">
															<%=rsKhuVuc.getString("ten")%></option>
													<%}else{%>
														<option value="<%=rsKhuVuc.getString("pk_seq")%>">
															<%=rsKhuVuc.getString("ten")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>
									</TR>
								
									<TR>
									
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel"><select onchange="search();"  name="kenhId" id="loaiCt">
											<option value="">All</option>
											<% if(rsKenh!=null){
													while(rsKenh.next()){
														String kenhId = rsKenh.getString(1);
														if(kenhId.equals(obj.getkenhId())){
															%>
															<option selected="selected" value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
														<%}else{%>	
															<option value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
											<%}}}%>
									</select></TD>
									
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select  name="dvkdId" onchange="search();" >
											<option value="">All</option>
										 	<% if(rsDVKD != null){
										 		while(rsDVKD.next()){
										 			String dvkdId = rsDVKD.getString(1);
										 			if(dvkdId.equals(obj.getdvkdId())){%>
										 				<option selected="selected" value="<%=rsDVKD.getString(1)%>"><%=rsDVKD.getString(2)%></option>
										 			<%}else{%>
										 				<option  value="<%=rsDVKD.getString(1)%>"><%=rsDVKD.getString(2)%></option>
										 			
										 	<%}}}%>
										</select>
									</TD>																																				
									</TR>															
								
								<TR>
									
									<TD class="plainlabel">Chương trình khuyến mãi</TD>
									<TD class="plainlabel">
										<select  name="programs" onchange="search();">
											<option value="">All</option>
											<% if(rsProgram!=null){
										 		while(rsProgram.next()){
													String programId = rsProgram.getString("pk_seq");
													if(programId.equals(obj.getPrograms())){%>
														<option selected="selected" value="<%=rsProgram.getString("pk_seq")%>">
															<%=rsProgram.getString("DIENGIAI")%></option>
													<%}else{%>
														<option value="<%=rsProgram.getString("pk_seq")%>">
															<%=rsProgram.getString("DIENGIAI")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>
									
									<TD class="plainlabel">Chi nhánh / Đối tác</TD>
									<TD class="plainlabel">
										<select  name="npp" onchange="search();">
											<option value="">All</option>
											<% if(rsNpp!=null){
										 		while(rsNpp.next()){
													String nppId = rsNpp.getString("pk_seq");
													if(nppId.equals(obj.getnppId())){%>
														<option selected="selected" value="<%=rsNpp.getString("pk_seq")%>">
															<%=rsNpp.getString("ten")%></option>
													<%}else{%>
														<option value="<%=rsNpp.getString("pk_seq")%>">
															<%=rsNpp.getString("ten")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>												
																		
									
																		
								</TR>								
								<TR>									
									<TD class="plainlabel">Mức phân tích</TD>
									<TD class="plainlabel" colspan="3" >
										<select name="mucphantich" >
											<% if(obj.getMuclay().equals("0")) { %>
												<option value="0" selected="selected" >Scheme</option>
											<% } else { %> 
												<option value="0"  >Mã CTKM</option>
											<% } %>
											
											<% if(obj.getMuclay().equals("1")) { %>
												<option value="1" selected="selected" >Sản phẩm</option>
											<% } else { %> 
												<option value="1"  >Sản phẩm</option>
											<% } %>
											
											<% if(obj.getMuclay().equals("2")) { %>
												<option value="2" selected="selected" >Tổng sản phẩm</option>
											<% } else { %> 
												<option value="2"  >Tổng sản phẩm</option>
											<% } %>
										</select>									
									</TD>									
								</TR>	
								
								
								
								<TR>									
									<TD class="plainlabel">Bao gồm VAT</TD>
									<TD class="plainlabel" colspan="3" >
										<%
											if(obj.getvat().equals("1")){%>
												<input type="radio" name="VAT"	value="1"  checked="checked" />Có  &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="VAT" value="0" />Không
											<%}else{%>
												<input type="radio" name="VAT"	value="1"  />Có &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="VAT" value="0"  checked="checked"/>Không
											<%}%>										
									</TD>									
								</TR>	
								
								<TR>
									<TD colspan="2">																			
									<a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a>
									</td>
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
	try
	{
		if(!(rsChungLoai == null))
			rsChungLoai.close();		
		if(rsDVKD != null)
			rsDVKD.close();		
		if(rsKenh != null)
			rsKenh.close();				
		if(rsNhans != null)
			rsNhans.close();
		if(rsNpp != null)
			rsNpp.close();
		if(rsProgram != null)
			rsProgram.close();	
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>