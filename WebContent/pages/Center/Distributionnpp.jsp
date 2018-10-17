<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatter2 = new DecimalFormat("#,###,###.##");  %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet sku = obj.getsanpham();
	ResultSet ddkd = obj.getRsddkd();
	ResultSet dvkd=obj.getdvkd();
	ResultSet nhomsp=obj.GetRsNhomSP();
	ResultSet kenh = obj.getkenh();
	ResultSet rsBaocao = obj.getRSBaocao();
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
<script src="../scripts/ui/jquery.ui.datepicker.js"	type="text/javascript"></script>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>

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
		$("select:not(.notuseselect2)").chosen();     
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
<script language="javascript" type="text/javascript">
	function seach() {
		document.forms['frm'].action.value = 'seach';
		document.forms["frm"].submit();
	}
	function submitform() {
		if (document.forms["frm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["frm"]["denngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
	 

		document.forms['frm'].action.value = 'create';
		document.forms["frm"].submit();
		reset();
	}
	function getdata(){
		if (document.forms["frm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["frm"]["denngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
		
		document.forms['frm'].action.value = 'getdata';
		document.forms['frm'].submit();
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
	}
	function recheckall(value){
	 
		var check=document.getElementsByName("check");
		for(var i=0;i<check.length;i++ ){
			check.item(i).checked=value;
		}
	}
	 
</script>
 <style type="text/css">
 
   label:hover { background-color: Highlight; color: HighlightText; }
  </style>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../Distributionnpp">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value="<%=obj.getuserId()%>">
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62; Theo dõi doanh số  &#62; Độ phủ của sản phẩm</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="2"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo xuất khuyến mãi</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="tungay" class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="denngay" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								
								<TR>
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" >
												<option value="" selected>Tất cả</option>
												<%if (ddkd != null)
														while (ddkd.next()) {
															if (ddkd.getString("pk_seq")
																	.equals(obj.getDdkd())) {	%>
															<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
														<%} else {%>
															<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
												<%}}%>
										 </select>
									</TD>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel">
										<select name="dvkdId" id="dvkdId"	onchange="seach();">
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
								</TR>
								
								
								<tr>
								    <%-- <TD class="plainlabel">Nhóm sản phẩm</TD>
									<TD class="plainlabel">
										<select name="nhomspid" onchange="seach();"  id="nhomspid" >
											<option value="" selected>Tất cả</option>
											<%if (nhomsp != null)
													while (nhomsp.next()) {
														if (nhomsp.getString("pk_seq").equals(obj.GetNhoSPId())) {%>
														<option value="<%=nhomsp.getString("pk_seq")%>" selected><%=nhomsp.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=nhomsp.getString("pk_seq")%>"><%=nhomsp.getString("diengiai")%></option>
												<% }}%>
										</select>
									</TD>		 --%>
									
									<TD class="plainlabel">Sản phẩm</TD>
									<TD class="plainlabel">
										<select name="check" id="spId" style="width:300px"  multiple="multiple">
												<%if (sku != null)
														while (sku.next()) {
															if (obj.getsanphamId().indexOf(sku.getString("pk_seq")) >=0){	%>
															<option value="<%=sku.getString("pk_seq")%>" selected><%=sku.getString("ma") + " - " +sku.getString("ten")%></option>
														<%} else {%>
															<option value="<%=sku.getString("pk_seq")%>"><%=sku.getString("ma") + " - "+sku.getString("ten")%></option>
												<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel" >
										<select name="kenhId" id="kenhId" onchange="seach();" style="width:250px">
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
											
								</tr>
								
									
								<TR>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel" colspan="3">
									<fieldset>
									<legend>Xem theo</legend> 
									 <% if(obj.gettype().equals("0")){ %>
										<input type="radio" name="typeid" onchange="Laytheokh();" value="0" checked="checked"/>Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
										<input type="radio" name="typeid" onchange="LayThethoigian();" value="1"/>Theo thời gian
										<input type="radio" name="typeid"  value="2" />Theo bất kỳ sản phẩm
									<%}else if(obj.gettype().equals("1")){ %>
										<input type="radio" name="typeid" onchange="Laytheokh();" value="0"/>Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
										<input type="radio" name="typeid" onchange="LayThethoigian();" value="1"  checked="checked" />Theo thời gian
										<input type="radio" name="typeid"  value="2" />Theo bất kỳ sản phẩm
									<%}else{ %>
										<input type="radio" name="typeid" onchange="Laytheokh();" value="0"  />Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
										<input type="radio" name="typeid" onchange="LayThethoigian();" value="1" />Theo thời gian
										<input type="radio" name="typeid"  value="2" checked="checked"  />Theo bất kỳ sản phẩm
									<%} %>
									</fieldset>
									</TD>
								</TR>
								
								
								<TR>
									<td colspan="4">
										<a class="button" href="javascript:submitform();"> 
										<img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>&nbsp;&nbsp;&nbsp;&nbsp; 
										<a class="button2" href="javascript:getdata()" >
										<img style="top: -4px;" src="../images/button.png" alt="">Xem dữ liệu</a> 
									</td>
								</TR>
								<%-- <tr >
									<td colspan="4" >
									
										<input type="checkbox" name="checkall"  id="checkall"  onchange="recheckall(this.checked);"  > 
										<label for="checkall"> Chọn tất cả </label>
										
										
									<fieldset>
										<legend> Chọn sản phẩm </legend>
										<ul>
										 <%											
				                               while(sku.next())
				                               {%>
													<li>  
														 
															<input type="checkbox" name="check"  value ="<%=sku.getString("pk_seq") %>" > 
														 <label> 
																<%=sku.getString("ma")+ " -" + sku.getString("ten")%>
														</label>
													</li>
				                              <%}
										 %> 
										</ul>
										</fieldset>
									</td>									
								</tr> --%>
							</TABLE>
						</div>
						<hr>
						
					</div>
				</fieldset>
			</div>
		
			<div align = "left">
				<FIELDSET>
					<LEGEND class="legendtitle">Dữ liệu</LEGEND>
					<TABLE width="100%" border="1" cellspacing="1px" cellpadding="3px">
						<TR class="plainlabel">
							<th  width="3%"  align="center"> STT</th>
							<th  width="6%"  align="center">MIỀN</th>
							<th  width="7%"  align="center"> ĐỊA BÀN </th>
							<th  width="17%" align="center">CHI NHÁNH /ĐT</th>
							<th  width="20%" align="center">SẢN PHẨM</th>
							<th  width="25%" align="center">KHÁCH HÀNG</th>
							<th  width="10%"  align="center">SỐ LƯỢNG</th>
							<th  width="10%"  align="center">DOANH THU</th>
						</TR>
						<%
						if(rsBaocao!=null){
							try{
								int m = 1;
								String lightrow = "tblightrow";
								String darkrow = "tbdarkrow";
								while(rsBaocao.next()){
									int stt = rsBaocao.getInt("STT");
									
									if(stt == 1){%>
										<TR class= <%= darkrow%> >
											<Td align="center"></Td>
											<Td align="left"><%=rsBaocao.getString("Region") %></Td>
											<Td align="left"><%=rsBaocao.getString("tinhthanh") %></Td>
											<Td align="left"><%=rsBaocao.getString("Distributor") %></Td>
											<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
											<Td align="left"><%=rsBaocao.getString("SOKH") %></Td>
											<Td align="left"><%=rsBaocao.getString("TONGSOLUONG") %></Td>
											<Td align="right"><%= formatter.format(rsBaocao.getDouble("TONGDOANHSO")) %></Td>
										</TR>
										<TR class= <%=lightrow%> >
											<Td align="center"><%= m %></Td>
											<Td align="left"></Td>
											<Td align="left"></Td>
											<Td align="left"></Td>
											<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
											<Td align="left"><%=rsBaocao.getString("custommer") %></Td>
											<Td align="left"><%=rsBaocao.getString("SOLUONG") %></Td>
											<Td align="right"><%= formatter.format(rsBaocao.getDouble("VOLUME")) %></Td>
										</TR>									
									<%}else{%>
										<tr class= <%=lightrow%> >
											<Td align="center"><%= m %></Td>
											<Td align="left"></Td>
											<Td align="left"></Td>
											<Td align="left"></Td>
											<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
											<Td align="left"><%=rsBaocao.getString("custommer") %></Td>
											<Td align="left"><%=rsBaocao.getString("SOLUONG") %></Td>
											<Td align="right"><%= formatter.format(rsBaocao.getDouble("VOLUME")) %></Td>
										</tr>
									<%} %>	
								
							<% m++;}}catch(Exception e){}} %>
	
					</TABLE>
				</FIELDSET>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
	<%

		if(ddkd!=null)
			ddkd.close();
		if(sku!=null)
			sku.close();
		if(nhomsp!=null)
			nhomsp.close();
		if(obj!=null)
			obj.DBclose();
		if(dvkd!=null){
			dvkd.close();
		}
		session.setAttribute("obj",null);
	%>
</body>
</html>
