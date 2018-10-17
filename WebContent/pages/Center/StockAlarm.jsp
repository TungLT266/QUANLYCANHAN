<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%	
	String userId = (String) session.getAttribute("userId");  
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

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function submitform() {
/*		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui long chon ngay bat dau");
			return ;
		}		
		var fieldShow = document.getElementsByName("fieldsHien");
		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}*/
	/*	for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = true;
		}
	*/
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		reset();
	}
	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
		
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
		
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
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
	$("#kenhId").select2();
	$("#sanphamId").select2();
});
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../StockAlarm">
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"> &#160; Quản lý kho chi nhánh &#62; Báo cáo &#62; Cảnh báo hàng tồn kho</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen %></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="../../CtkhuyenmaiSvl?userId="> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A href="javascript:saveform()"> <IMG src="../images/Save30.png"
					title="Luu lai" alt="Luu lai" border="1px"
					style="border-style: outset"></A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
						<%= obj.getMsg() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> Cảnh báo hàng tồn kho</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									
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
										<TD class="plainlabel">Đơn vị kinh doanh</TD>
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
										<select name="vungId" id="vungId" onchange="seach();" style="width:202px">
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
									<TD class="plainlabel"><select name="nppId" id="nppId" style="width:202px">
											<option value="" selected>All</option>
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
									<TD class="plainlabel">Số ngày tồn kho tối thiểu</TD>
									<TD class="plainlabel" colspan="1" > 
										<input type="text" name="lessday" value ="<%= obj.getlessday() == "0" ? "14" : obj.getlessday() %>" /> 
									</TD>	
									<TD class="plainlabel">Số ngày tồn kho tiêu chuẩn</TD>
									<TD class="plainlabel" colspan="1" > 
										<input type="text" name="moreday" value ="<%= obj.getmoreday() == "0" ? "21" : obj.getmoreday() %>" /> 
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
<%/* %>						<hr>
						<div style="width: 100%; float: none;">
						<div style="width: 30%; float: left">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Dữ liệu hiển thị </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn ẩn</th>
											</tr>
											<tr class="tbdarkrow">
												<td>KenhBanHang</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="KenhBanHang"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>DonViKinhDoanh</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="DonViKinhDoanh"></td>
											</tr>
											<tr class="tblightrow">
												<td>ChiNhanh</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="ChiNhanh"></td>
											</tr>
											<tr class="tblightrow">
												<td>KhuVuc</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="KhuVuc"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>MaNhaPhanPhoi</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MaNhaPhanPhoi"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>NhaPhanPhoi</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="NhaPhanPhoi"></td>
											</tr>
											<tr class="tblightrow">
												<td>MaVaTenSanPham</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SKU"></td>
											</tr>											
											<tr class="tbdarkrow">
												<td>Stock(Inv+GIT)</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Stock(Inv+GIT)"></td>
											</tr>
										
											<tr class="tblightrow">
												<td>Avg. daily second sales</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Avg_daily_second_sales"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Inventory Days</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Inventory_Days"></td>
											</tr>											
										
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							<div
								style="width: 35px; float: left; min-height: 200px; vertical-align: middle"
								align="center">
								<br> <br> <br> <img src="../images/Back30.png"
									border="0" class="imageClick" onClick="ChuyenSangPhai();"><br />
								<br> <br> <img src="../images/Next30.png" border="0"
									class="imageClick" onClick="ChuyenSangTrai();">
							</div>
							<div style="width: 30%; float: left;">
								<fieldset style="min-height: 200px;">
									<legend class="legendtitle"> Tất cả các Fields </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chọn hiện</th>
											</tr>
											<tr class="tbdarkrow">
												<td>TinhThanh</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="TinhThanh"></td>
											</tr>
											<tr class="tblightrow">
												<td>QuanHuyen</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="QuanHuyen"></td>
											</tr>
											<tr class="tblightrow">
												<td>NhanHang</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="NhanHang"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>ChungLoai</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="ChungLoai"></td>
											</tr>																				
										</tbody>
									</TABLE>
								</fieldset>
							</div>
							
							
						</div>
					</div>
				</fieldset>
			</div>
		</div> <%*/ %>
	</form>
</body>
</html>
<%
	if(kenh != null) kenh.close(); 
	if(vung != null) vung.close();
	if(khuvuc != null) khuvuc.close();
	if(npp != null) npp.close();
	if (dvkd != null) dvkd.close();

	if (dvdl != null) dvdl.close();  
	if(sanpham != null) sanpham.close();
	if(gsbh != null) gsbh.close();
	if(obj != null)  obj.DBclose();
	obj = null;
	session.setAttribute("obj", null);
}
%>

