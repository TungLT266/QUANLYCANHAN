<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatter1 = new DecimalFormat("#,###,###.##"); %>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet npp = obj.getnpp();	
	ResultSet ddkd = obj.getRsddkd();
	ResultSet kh = obj.getKhrsnew();
	ResultSet rsdiaban = obj.getRsdiaban();
	ResultSet khors = obj.getKhors();	
	
	ResultSet rsKhuvuc = obj.getkhuvuc();
	ResultSet rsTinh = obj.getTinhthanh();
	ResultSet rsSanpham = obj.getSpRs();
	Utility util = (Utility) session.getAttribute("util");
	int[] quyen = new  int[11];
	quyen = util.GetquyenNew("BCDoanhsoSvl", "&phanloai=2",userId);
	
	
	
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
<LINK rel="stylesheet" type="text/css" media="screen"href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
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

<script type="text/javascript">
	 
		$(document).ready(function() {

		    //When page loads...
		    $(".tab_content").hide(); //Hide all content
		    var index = $("ul.tabs li.current").show().index(); 
		    $(".tab_content").eq(index).show();
		    //On Click Event
		    $("ul.tabs li").click(function() {
		  
		        $("ul.tabs li").removeClass("current"); //Remove any "active" class
		        $(this).addClass("current"); //Add "active" class to selected tab
		        $(".tab_content").hide(); //Hide all tab content  
		        var activeTab = $(this).find("a").attr("href"); //Find the href attribute value to identify the active tab + content  
		        $(activeTab).show(); //Fade in the active ID content
		        return false;
		    });

		});
</script>

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
	function submitform() 
	{
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
		reset();
	}

	function search()	
	 {		
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
		
	 	document.forms['frm'].action.value= 'search';
	 	document.forms['frm'].submit();
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
			$(".select2").select2();
		});
	</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BCDoanhsoSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<input type="hidden" name="loaisalesin" value='<%=obj.getLoaiSalesIn() %>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">
				<% if( obj.getLoaiSalesIn().equals("0") ) { %>
					Quản lý bán hàng &#62; Báo cáo &#62; Doanh số Sales In (PBH)
				<% } else if( obj.getLoaiSalesIn().equals("1") ) {  %>
					Quản lý bán hàng &#62; Báo cáo &#62; Doanh số Sales In (BGD)
				<% } else { %>
					Quản lý bán hàng &#62; Báo cáo &#62; Doanh số Sales In (KT)
				<% } %>
				</div>
				<div align="right" style="padding: 5px" class="tbnavigation">Chào mừng bạn<%=obj.getuserTen()%>
				</div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<FIELDSET>
					<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
					<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100%; font-weight: bold">
						<%= obj.getMsg() %>
					</textarea>
				</FIELDSET>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> 
						<% if( obj.getLoaiSalesIn().equals("0") ) { %>
							Doanh số Sales In (PBH)
						<% } else if( obj.getLoaiSalesIn().equals("1") ) {  %>
							Doanh số Sales In (BGD)
						<% } else { %>
							Doanh số Sales In (KT)
						<% } %>
					</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">
								<TR>
									<TD class="plainlabel" width="120px">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" class="days" name="Sdays" value='<%=obj.gettungay()%>' style="width: 250px" /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td>
										<input type="text" class="days" name="Edays" value='<%=obj.getdenngay()%>' style="width: 250px" /></td>
								</TR>
								
								<TR>
									<TD class="plainlabel">Khu vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId" class="select2" style="width: 250px" >
											<option value="">All</option>
											<%if (rsKhuvuc != null)
													while (rsKhuvuc.next()) 
													{
														if (rsKhuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
														<option value="<%=rsKhuvuc.getString("pk_seq")%>" selected><%=rsKhuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=rsKhuvuc.getString("pk_seq")%>"><%=rsKhuvuc.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Tỉnh</TD>
									<TD class="plainlabel" colspan="3">
										<select name="tinhthanhId" class="select2" style="width: 250px" >
											<option value="" selected>All</option>
											<% if(rsTinh != null)
											   while(rsTinh.next()){
											   if(rsTinh.getString("pk_seq").equals(obj.getTinhthanhid()))
											   { %>
											<option value="<%= rsTinh.getString("pk_seq") %>" selected><%= rsTinh.getString("ten") %></option>
											<%}else { %>
											<option value="<%= rsTinh.getString("pk_seq") %>"><%= rsTinh.getString("ten") %></option>
											<%} }%>
									</select></TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" class="select2" style="width: 250px" >
											<option value="">All</option>
											<%if (ddkd != null)
													while (ddkd.next()) 
													{
														if (ddkd.getString("pk_seq").equals(obj.getDdkd())) {%>
														<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
													<%} else {%>
														<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
											<%}}%>
									</select></TD>
									<TD class="plainlabel">Khách hàng</TD>
									<TD class="plainlabel" >
										<select name="khId" class="select2" style="width: 250px" >
											<option value="" selected>All</option>
											<% if(kh != null)
											   while(kh.next()){
											   if(kh.getString("pk_seq").equals(obj.getkhId()))
											   { %>
											<option value="<%= kh.getString("pk_seq") %>" selected><%=kh.getString("ten") %></option>
											<%}else { %>
											<option value="<%= kh.getString("pk_seq") %>"><%=kh.getString("ten") %></option>
											<%} }%>
										</select>
									</TD>
								</TR>
								<tr>
									<TD class="plainlabel">Địa bàn</TD>
									<TD class="plainlabel" colspan="3" >
										<select name="diabanid" class="select2" id="khoid" style="width: 250px" >
											<option value="" selected>All</option>
											<% if(rsdiaban != null)
											   while(rsdiaban.next()){
											   if(rsdiaban.getString("pk_seq").equals(obj.getDiabanid()))
											   { %>
											<option value="<%= rsdiaban.getString("pk_seq") %>" selected><%=rsdiaban.getString("ten") %></option>
											<%}else { %>
											<option value="<%= rsdiaban.getString("pk_seq") %>"><%=rsdiaban.getString("ten") %></option>
											<%} }%>
										</select>
									</TD>
								</tr>
								<TR>
									<TD class="plainlabel">Kho</TD>
									<TD class="plainlabel" >
										<select name="khoid" class="select2" id="khoid" style="width: 250px" >
											<option value="" selected>All</option>
											<% if(khors != null)
											   while(khors.next()){
											   if(khors.getString("pk_seq").equals(obj.getkhoId()))
											   { %>
											<option value="<%= khors.getString("pk_seq") %>" selected><%=khors.getString("ten") %></option>
											<%}else { %>
											<option value="<%= khors.getString("pk_seq") %>"><%=khors.getString("ten") %></option>
											<%} }%>
									</select></TD>
									<TD class="plainlabel">Sản phẩm</TD>
									<TD class="plainlabel" >
										<select name="spId" class="select2" style="width: 250px" >
											<option value="" selected>All</option>
											<% if(rsSanpham != null)
											   while(rsSanpham.next()){
											   if(rsSanpham.getString("pk_seq").equals( obj.getsanphamId() ))
											   { %>
											<option value="<%= rsSanpham.getString("pk_seq") %>" selected><%= rsSanpham.getString("ten") %></option>
											<%}else { %>
											<option value="<%= rsSanpham.getString("pk_seq") %>"><%= rsSanpham.getString("ten") %></option>
											<%} }%>
									</select></TD>
								</TR>
								<% if(obj.getLoaiSalesIn().equals("0")) { %>
								<TR >
									<TD class="plainlabel">Loại báo cáo</TD>
									<TD class="plainlabel">
										<select name="type" class="select2" style="width: 250px" >
											<% if( obj.gettype().equals("0") ) { %>
												<option value="0" selected="selected" >Tất cả</option>
											<% } else { %>
												<option value="0" >Tất cả</option>
											<% } %>
											<% if( obj.gettype().equals("1") ) { %>
												<option value="1" selected="selected" >Sales In OTC</option>
											<% } else { %>
												<option value="1" >Sales In OTC</option>
											<% } %>
											<% if( obj.gettype().equals("2") ) { %>
												<option value="2" selected="selected" >Sales In ETC</option>
											<% } else { %>
												<option value="2" >Sales In ETC</option>
											<% } %>
										</select>
									</TD>

								</TR>
								<% } %>
								<TR>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel" colspan="5" >
									
										<% if(obj.getDongiagoc().equals("1")) {%> 
											<input type="checkbox" name="laygiaGoc" value="1" checked="checked" /> Đơn giá có VAT &nbsp; &nbsp; 
										<% }else {%> 
											<input type="checkbox" name="laygiaGoc" value="1" /> Đơn giá có VAT &nbsp; &nbsp; <% } %>
											
											<% if(obj.getLoaiSalesIn().equals("2")) { 
											 if(obj.getLaygiavon().equals("1")){
												%> 
											
											<input type="checkbox" name="laygiavon" value="1" checked="checked" /> Lấy giá vốn &nbsp; &nbsp; 
											<% }else {%> 
											<input type="checkbox" name="laygiavon" value="1" />Lấy giá vốn &nbsp; &nbsp; <% }  
											
											}%>
											
										&nbsp;&nbsp;&nbsp;
									<!-- quyền hiển thị all thì được xem giá vốn -->
										<% if( !obj.getLoaiSalesIn().equals("2") && quyen[9]==1 ) { %>
										
											<% if(obj.getChuyenSale().equals("1")) {%> 
												<input type="checkbox" name="laychuyenSale" value="1" checked="checked" /> Lấy chuyển sale &nbsp; &nbsp; 
											<% }else {%> 
												<input type="checkbox" name="laychuyenSale" value="1" /> Lấy chuyển sale &nbsp; &nbsp; <% } %>
												
											<% if(obj.getKhuyenmai().equals("1")) {%> 
												<input type="checkbox" name="laykhuyenMai" value="1" checked="checked" /> Lấy khuyến mại &nbsp; &nbsp; 
											<% }else {%> 
												<input type="checkbox" name="laykhuyenMai" value="1" /> Lấy khuyến mại &nbsp; &nbsp; <% } %>
												
										<% } %>
										
										&nbsp;&nbsp;&nbsp;
											
									</TD>
									
								</TR>

								<TR>
						
									<td class="plainlabel" colspan="4">
										<a class="button" href="javascript:submitform();"> 
										<img style="top: -5px;" src="../images/button.png" alt="">Tạo báo cáo </a> &nbsp; &nbsp;  &nbsp; 
										 <a class="button" href="javascript:search();"> 
										<img style="top: -5px;" src="../images/button.png" alt="">Tìm kiếm </a>
										</td>
									
								</TR>
							</TABLE>
							</div>
							</div>
					</FIELDSET>
					
					
					
					<fieldset>
						
					<TABLE id="table" class="tabledetail sortable" width="100%" border="1" cellspacing="1" cellpadding="4">
						<thead>
						<TR class="tbheader">
								<TH align="center" style="width: 5%" colspan="9">Nhân viên bán hàng</TH>
								<TH align="center" style="width: 15%">Tổng Tiền</TH>
								
							</TR>
							<TR class="tbheader">
								<TH align="center" style="width: 5%">Mã khách hàng</TH>
								<TH align="center" style="width: 20%">Tên khách hàng</TH>
								<TH align="center" style="width: 10%">Tên khu vực</TH>
								<TH align="center" style="width: 10%">Tỉnh thành</TH>
								<TH align="center" style="width: 5%">Mã sản phẩm</TH>
								<TH align="center" style="width: 20%">Tên sản phẩm</TH>
								<TH align="center" style="width: 5%">ĐVT</TH>
								<TH align="center" style="width: 5%">Số lượng</TH>
								<TH align="center" style="width: 5%">Giá sản phẩm</TH>
								<TH align="center" style="width: 15%">Thành Tiền</TH>
								
							</TR>
							</thead>
			                <tbody>
			             <% if(obj.getRsSearch()!=null){ 
			                	while (obj.getRsSearch().next()){
			                		String lightrow = "tblightrow";
									String darkrow = "tbdarkrow";
				                	if( obj.getRsSearch().getString("stt").equals("1"))
				                	{%>
									<TR class= <%= darkrow%>  >
										<TH align="left" style="width: 5%" colspan="9"><%= obj.getRsSearch().getString("ddkdTen")%></TH>
										<TH align="right" style="width: 20%"><%=formatter1.format( obj.getRsSearch().getDouble("TONGDOANHSO"))%></TH>
									</tr>
									<tr class= <%=lightrow%> >		
										<Th align="left" style="width: 5%"><%= obj.getRsSearch().getString("makhachhangold")==null?"":obj.getRsSearch().getString("makhachhangold")%></Th>
										<Th align="left" style="width: 20%"><%= obj.getRsSearch().getString("tenkhachhang")==null?"":obj.getRsSearch().getString("tenkhachhang")%></Th>
										<Th align="left" style="width: 10%"><%= obj.getRsSearch().getString("khuvucten")%></Th>
										<Th align="left" style="width: 10%"><%= obj.getRsSearch().getString("tinhthanhten")%></Th>
										<Th align="left" style="width: 5%"><%= obj.getRsSearch().getString("masanphamnew")%></Th>
										<Th align="left" style="width: 20%"><%= obj.getRsSearch().getString("tensanpham")%></Th>
										<Th align="left" style="width: 5%"><%=obj.getRsSearch().getString("donvi") %></TH>
										
										<Th align="right" style="width: 5%"><%= formatter1.format(obj.getRsSearch().getDouble("soluong"))%></Th>
										
										<Th align="right" style="width: 5%"><%=formatter1.format(obj.getRsSearch().getDouble("dongiadoanhso"))%></Th>
										<Th align="right" style="width: 15%"><%=formatter1.format( obj.getRsSearch().getDouble("thanhtiensauvat") ) %></Th>
										
									</TR>
									<%} else{%>
									<tr class= <%=lightrow%> >
										<Th align="left" style="width: 5%"><%=  obj.getRsSearch().getString("makhachhangold")==null?"":obj.getRsSearch().getString("makhachhangold")%></Th>
										<Th align="left" style="width: 20%"><%=obj.getRsSearch().getString("tenkhachhang")==null?"":obj.getRsSearch().getString("tenkhachhang")%></Th>
										<Th align="left" style="width: 10%"><%= obj.getRsSearch().getString("khuvucten")%></Th>
										<Th align="left" style="width: 10%"><%= obj.getRsSearch().getString("tinhthanhten")%></Th>
										<Th align="left" style="width: 5%"><%= obj.getRsSearch().getString("masanphamnew")%></Th>
										<Th align="left" style="width: 20%"><%= obj.getRsSearch().getString("tensanpham")%></Th>
										<Th align="left" style="width: 5%"><%=obj.getRsSearch().getString("donvi") %></TH>
										<Th align="right" style="width: 5%"><%= formatter1.format(obj.getRsSearch().getDouble("soluong"))%></Th>
										<Th align="right" style="width: 5%"><%=formatter1.format(obj.getRsSearch().getDouble("dongiadoanhso"))%></Th>
										<Th align="right" style="width: 15%"><%=formatter1.format( obj.getRsSearch().getDouble("thanhtiensauvat") ) %></Th>
									</tr>
									<%} %>
								<%}
		                	} %>

							</tbody>
                     <tfoot>
							<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
								
							</tr>
</tfoot>
						</TABLE>
					</fieldset>
					
					
							<!--Chỉnh sửa  -->

						</div>
					</div>
	</form>
	<%
		if(npp!=null)npp.close();
		if(ddkd!=null)ddkd.close();
		if(obj.getRsSearch()!=null)	obj.getRsSearch().close();
		if(obj!=null){obj.DBclose();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>