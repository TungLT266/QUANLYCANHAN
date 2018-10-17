
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
	ResultSet rsGsbh = obj.getgsbh();
	ResultSet rsDdkd = obj.getRsddkd();
	ResultSet rsDVKD = obj.getdvkd();
	ResultSet rsProgram = obj.getRsPrograms();
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
	<form name="frm" method="post" action="../../UsingPromotionnpp">
		<input type="hidden" value="1" name="action"  >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý khuyến mãi &#62; Báo cáo &#62; Sử dụng phân bổ khuyến mãi</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn Chi nhánh / Đối tác&nbsp; <%=userTen %></div>
			</div>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" 	name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Sử dụng phân bổ khuyến mãi</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value="<%=obj.gettungay() %>" /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel"><input type="text" name="Edays"
										class="days" value="<%=obj.getdenngay() %>" /></TD>
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
										<select  name="programs">
											<option value="">All</option>
											<% if(rsProgram!=null){
										 		while(rsProgram.next()){
													String programId = rsProgram.getString("SCHEME");
													if(programId.equals(obj.getPrograms())){%>
														<option selected="selected" value="<%=rsProgram.getString("SCHEME")%>">
															<%=rsProgram.getString("DIENGIAI")%></option>
													<%}else{%>
														<option value="<%=rsProgram.getString("SCHEME")%>">
															<%=rsProgram.getString("DIENGIAI")%></option>
										 	<% }}}%>										 	
										</select>
									</TD>		
									<TD class="plainlabel">Đơn vị tính</TD>
									<TD class="plainlabel">
										<select  name="donviTinh">
											<option value="">All</option>										 	
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
						<hr>
						<div style="width: 100%; float: none; display: none">
							<div style="width: 30%; float: left;">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"> Dữ liệu hiển thị </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn  ẩn</th>
											</tr>
											<tr class="tbdarkrow">
												<td>Kênh bán hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Kenh"></td>
											</tr>										
											<tr class="tblightrow">
												<td>Tên chương trình</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TenChuongTrinh"></td>
											</tr>
											<tr class="tblightrow">
												<td>Từ ngày</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TuNgay"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Đến ngày</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="DenNgay"></td>
											</tr>																																																					

											<tr class="tbdarkrow">
												<td>Ngân sách phân bổ</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoPhanBo"></td>
											</tr>
											<tr class="tblightrow">
												<td>Đã sử dụng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="DaSuDung"></td>
											</tr>											
											<tr class="tbdarkrow">
												<td>Ngân sách còn lại</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="ConLai"></td>
											</tr>
										
											<tr class="tbdarkrow">
												<td>Số suất còn lại</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SuatConLai"></td>
											</tr>

											<tr class="tblightrow">
												<td>% Sử dụng chương trình</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="%SuDung"></td>
											</tr>										
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle; display: none"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick= "ChuyenSangPhai();"><br />

								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
						</div>
						<div style="width: 30%; float: left;display: none" >
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Dữ liệu ẩn </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn hiển thị</th>
												
											</tr>
											<tr class="tbdarkrow">
												<td>Mã chương trình</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaChuongTrinh"></td>
											</tr>
											
											<tr class="tbdarkrow">
												<td>Hình thức khuyến mãi</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="HinhThuc"></td>
											</tr>

										</tbody>
									</TABLE>
								</fieldset>
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

		if(rsDdkd != null)
			rsDdkd.close();
		if(rsDVKD != null)
			rsDVKD.close();
		if(rsGsbh != null)
			rsGsbh.close();
		if(rsKenh != null)
			rsKenh.close();
		if(rsKhuVuc != null)
			rsKenh.close();
		if(rsKhuVuc != null)
			rsKhuVuc.close();

		if(rsNpp != null)
			rsNpp.close();
		if(rsProgram != null)
			rsProgram.close();
		if(rsVung != null)
			rsVung.close();
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>