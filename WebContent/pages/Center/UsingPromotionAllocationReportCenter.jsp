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
	ResultSet rsNhans = obj.getnhanhang();
	ResultSet rsChungLoai = obj.getchungloai();
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
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		}			
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}		
		document.forms["frm"]["action"].value = "Taomoi";
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
});
</script>


</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../UsingPromotionTTSvl">
		<input type="hidden" value="1" name="action"  >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý khuyến mãi &#62; Báo cáo &#62; Sử dụng phân bổ khuyến mãi</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn &nbsp; <%= obj.getuserTen() %> </div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="#"> <img src="../images/Back30.png" alt="Quay ve"
					title="Quay ve" border="1px" longdesc="Quay ve"
					style="border-style: outset"></A> <A href="javascript:saveform()">
					<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai"
					border="1px" style="border-style: outset">
				</A>
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
									<TD class="plainlabel"><input type="text" name="Sdays" onchange="search();"
										class="days" value="<%=obj.gettungay() %>" /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel"><input type="text" onchange="search();" name="Edays"
										class="days" value="<%=obj.getdenngay() %>" /></TD>
								</TR>
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel"><select onchange="search();"  name="kenhId" id="kbhId" id="loaiCt"  style="width:202px" >
											<option value="">All</option>
											<% if(rsKenh!=null){
													while(rsKenh.next()){
														String kenhId = rsKenh.getString(1);
														if(kenhId.equals(obj.getkenhId())){%>
															<option selected="selected" value="<%=rsKenh.getString(1) %>">
																	<%=rsKenh.getString(2)%></option>
														<%}else{%>
															<option value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
											<%}}}%>
									</select></TD>
																		<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select  name="dvkdId"  id="dvkdId" style="width:202px" >
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
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="vungId" id="vungId" style="width:202px" >
											<option value="">All</option>
											<% if(rsVung!=null){
													while(rsVung.next()){
														String vungId = rsVung.getString(1);
														if(vungId.equals(obj.getvungId())){%>
															<option selected="selected"  value="<%=rsVung.getString(1) %>"><%=rsVung.getString(2)%></option>
														<%}else{%>
														<option  value="<%=rsVung.getString(1) %>"><%=rsVung.getString(2)%></option>
															
											<%}}}%>										
										</select>
									</TD>
									<TD class="plainlabel">PTT / GĐ CN 2</TD>
									<TD class="plainlabel">
										<select name="gsbhs" id="gsbhId" onchange="search();"  style="width:202px" >											
											<option value="" >All</option>
											<% if(rsGsbh!=null){
													while(rsGsbh.next()){
														String gsbhId = rsGsbh.getString(1);
														if(gsbhId.equals(obj.getgsbhId())){
															%>
															<option selected="selected" value="<%=rsGsbh.getString(1) %>"><%=rsGsbh.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsGsbh.getString(1) %>"><%=rsGsbh.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>

								</TR>

								<TR>									
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="khuvucId" id="khuvucId" style="width:202px" >
											<option value="">All</option>
											<% if(rsKhuVuc!=null){
													while(rsKhuVuc.next()){
														String khuvucId = rsKhuVuc.getString(1);
														if(khuvucId.equals(obj.getkhuvucId())){%>
															<option selected="selected" value="<%=rsKhuVuc.getString(1)%>"><%=rsKhuVuc.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsKhuVuc.getString(1)%>"><%=rsKhuVuc.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>
									<TD class="plainlabel">Chi nhánh / Đối tác</TD>
									<TD class="plainlabel">
										<select  onchange="search();"  name="nppId" id="nppId" style="width:202px" >
											<option value="">All</option>
											<% if(rsNpp!=null){
													while(rsNpp.next()){
														String nppId = rsNpp.getString(1);
														if(nppId.equals(obj.getnppId())){%>
															<option selected="selected" value="<%=rsNpp.getString(1)%>"><%=rsNpp.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsNpp.getString(1)%>"><%=rsNpp.getString(2)%></option>
											<%}}}%>
										</select>
									</TD>

								</TR>
								
								<TR>						
									<TD class="plainlabel"></TD><TD class="plainlabel"></TD>			
									<TD class="plainlabel">Chương trình khuyến mãi</TD>
									<TD class="plainlabel">
										<select  name="programs" onchange="search();"  id="programs" style="width:202px" >
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
						<div style="width: 100%; float: none;display: none">
							<div style="width: 30%; float: left;">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"> Dữ liệu hiển thị </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn ẩn</th>
											</tr>
											<tr class="tbdarkrow">
												<td>Kênh bán hàng</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="KenhBanHang"></td>
											</tr>
											<tr class="tblightrow">
												<td>Miền</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="ChiNhanh"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Khu Vực</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="KhuVuc"></td>
											</tr>
											<tr class="tblightrow">
												<td>Chi nhánh / Đối tác</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NhaPhanPhoi"></td>
											</tr>											
											<tr class="tbdarkrow">
												<td>Mã chương trình</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaChuongTrinh"></td>
											</tr>
										
											<tr class="tblightrow">
												<td>Tên chương trình</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="TenChuongTrinh"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Ngân sách phân bổ</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NganSachPhanBo"></td>
											</tr>
											<tr class="tblightrow">
												<td>Đã sử dụng </td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="DaSuDung"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Ngân sách còn lại</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NganSachConLai"></td>
											</tr>
											<tr class="tblightrow">
												<td>Số suất còn lại</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SoSuatConLai"></td>
											</tr>
											
											<tr class="tbdarkrow">
												<td>% Sử dụng </td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="%SuDung"></td>
											</tr>
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle;display: none"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick= "ChuyenSangPhai();"><br />

								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
						</div>
						<div style="width: 30%; float: left ; display: none">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Dữ liệu ẩn </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn hiện</th>
											</tr>
											<tr class="tblightrow">
												<td>Mã Chi nhánh / Đối tác</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="MaNhaPhanPhoi"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Từ ngày</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="TuNgay"></td>
											</tr>
											<tr class="tblightrow">
												<td>Đến ngày</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="DenNgay"></td>
											</tr>
											<tr class="tblightrow">
												<td>Hình thức</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="HinhThuc">
												</td>
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
		if(rsKenh != null) rsKenh.close();		
		if(rsKhuVuc != null) rsKhuVuc.close();
		if(rsVung != null) rsVung.close();
		if(rsNpp != null) rsNpp.close();
		if(rsGsbh != null) rsGsbh.close();
		if(rsDdkd != null) rsDdkd.close();
		if(rsNhans != null) rsNhans.close();
		if(!(rsChungLoai == null)) rsChungLoai.close();
		if(rsDVKD != null) rsDVKD.close();	
		if(rsProgram != null) rsProgram.close();
		
		if(obj != null) {obj.DBclose();
		obj = null;}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
}
%>
