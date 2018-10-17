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
	String groupCustomer = (String)session.getAttribute("groupCustomer");
	String gorupSKU = (String)session.getAttribute("gorupSKU");
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet ddkd = obj.getRsddkd();
	ResultSet kh = obj.getKhrsnew();
	ResultSet kbhRs = obj.getkenh();
	ResultSet khuvuc = obj.getkhuvuc();	
	ResultSet sanpham  = obj.getsanpham();	
	//ResultSet totalRs = obj.getTotalRs();
	ResultSet khors = obj.getKhors();	
	ResultSet rsdiaban = obj.getRsdiaban();
	ResultSet tinhRs = obj.getTinhRs();
	ResultSet loaidhRs =obj.getLoaidhRs();
	ResultSet rsNpp = obj.getNppRs();
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
	<form name="frm" method="post" action="../../DailySecondarySalesTTSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Báo cáo quản trị &#62; Theo dõi doanh số &#62; Doanh số Sales Out</div>
				<div align="right" style="padding: 5px" class="tbnavigation">Chào mừng bạn<%=obj.getuserTen()%></div>
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
					<legend class="legendtitle"> Doanh số Sales Out </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px"
								style="margin-top: 5px">
								<TR>
									<TD class="plainlabel" width="120px">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" class="days" name="Sdays" value='<%=obj.gettungay()%>' style="width: 250px" /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td>
										<input type="text" class="days" name="Edays" value='<%=obj.getdenngay()%>' style="width: 250px" /></td>
								</TR>
								<TR>
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" class="select2" style="width: 250px" onchange="search();">
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
									<TD class="plainlabel" colspan="3"><select name="khId"
										class="select2" id="khId" style="width: 250px"
										onchange="search();">
											<option value="" selected>All</option>
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
								<tr>
								
								<TD class="plainlabel">Kho</TD>
									<TD class="plainlabel" colspan="1"><select name="khoid"
										class="select2" id="khoid" style="width: 250px"
										onchange="search();">
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
									  <TD class="plainlabel" >Kênh bán hàng</TD>
                        <TD class="plainlabel">
                            <select name = "kenhId" class="select2" style="width: 250px"  >
	                    		<option value="">ALL</option>
	                        	<%
	                        		if(kbhRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(kbhRs.next())
	                        			{  
	                        			if( kbhRs.getString("pk_seq").equals(obj.getkenhId())){ %>
	                        				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected" ><%= kbhRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= kbhRs.getString("pk_seq") %>" ><%= kbhRs.getString("ten") %></option>
	                        		 <% } } kbhRs.close();} catch(Exception ex){ ex.printStackTrace(); }
	                        		}
	                        	%>
	                    	</select>
                        </TD>
                     <TR>
                     <TD  class="plainlabel">Sản phẩm</TD>
							<TD  class="plainlabel"  >
							<select name="spid" class="select2"  style="width: 250px;" >
							
								<option value="">ALL</option>
								<%if(sanpham!=null){ while (sanpham.next()){ %>
								<option value="<%=sanpham.getString("pk_seq")%>" <%if(sanpham.getString("pk_seq").equals(obj.getsanphamId())){%> selected="selected" <%}%>><%=sanpham.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							<TD  class="plainlabel">Khu vực</TD>
							<TD  class="plainlabel" >
							<select name="khuvucId" class="select2"  style="width: 250px;" >
							
								<option value="">ALL</option>
								<%if(khuvuc!=null){ while (khuvuc.next()){ %>
								<option value="<%=khuvuc.getString("pk_seq")%>" <%if(khuvuc.getString("pk_seq").equals(obj.getkhuvucId())){%> selected="selected" <%}%>><%=khuvuc.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							
                    </TR>
                    
                    <TR>
                     <TD  class="plainlabel">Tỉnh</TD>
							<TD  class="plainlabel"  >
							<select name="tinh" class="select2"  style="width: 250px;" >
							
								<option value="">ALL</option>
								<%if(tinhRs!=null){ while (tinhRs.next()){ %>
								<option value="<%=tinhRs.getString("pk_seq")%>" <%if(tinhRs.getString("pk_seq").equals(obj.getsanphamId())){%> selected="selected" <%}%>><%=tinhRs.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							<TD  class="plainlabel">Loại đơn hàng</TD>
							<TD class="plainlabel">
									<select name="loaidh" class="select2" style="width: 250px" onchange="submitform();">
										<option value="">ALL</option>
										<% if(obj.getLoaidh().equals("0")) { %>
											<option value="0" selected="selected" >Bán cho ĐLPP</option>
										<% } else { %>
											<option value="0" >Bán cho ĐLPP</option>
										<% } %>
										<% if(obj.getLoaidh().equals("1")) { %>
											<option value="1" selected="selected" >ETC</option>
										<% } else { %>
											<option value="1" >ETC</option>
										<% } %>
										<% if(obj.getLoaidh().equals("2")) { %>
											<option value="2" selected="selected" >OTC</option>
										<% } else { %>
											<option value="2" >OTC</option>
										<% } %>
										<% if(obj.getLoaidh().equals("3")) { %>
											<option value="3" selected="selected" >Bán nội bộ</option>
										<% } else { %>
											<option value="3" >Bán nội bộ</option>
										<% } %>
									</select>
							</TD>
							
                    </TR>
                    
                    <TR>
								
								    <TD class="plainlabel">Địa bàn</TD>
									<TD class="plainlabel" ><select name="diabanid"
										class="select2" id="khoid" style="width: 250px"
										onchange="search();">
											<option value="" selected>All</option>
											<% if(rsdiaban != null)
											   while(rsdiaban.next()){
											   if(rsdiaban.getString("pk_seq").equals(obj.getDiabanid()))
											   { %>
											<option value="<%= rsdiaban.getString("pk_seq") %>" selected><%=rsdiaban.getString("ten") %></option>
											<%}else { %>
											<option value="<%= rsdiaban.getString("pk_seq") %>"><%=rsdiaban.getString("ten") %></option>
											<%} }%>
									</select></TD>
									
									<TD class="plainlabel">Công ty MTV / ĐLPP</TD>
									<TD class="plainlabel" >
										<select name="nppId" class="select2" id="nppId" style="width: 250px" >
											<option value="" selected>All</option>
											<% if(rsNpp != null)
											   while(rsNpp.next()){
											   if(rsNpp.getString("pk_seq").equals(obj.getnppId()))
											   { %>
											<option value="<%= rsNpp.getString("pk_seq") %>" selected><%=rsNpp.getString("ten") %></option>
											<%}else { %>
											<option value="<%= rsNpp.getString("pk_seq") %>"><%=rsNpp.getString("ten") %></option>
											<%} }%>
										</select>
									</TD>
									
								</TR>

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
												<option value="1" selected="selected" >Sales Out OTC</option>
											<% } else { %>
												<option value="1" >Sales Out OTC</option>
											<% } %>
											<% if( obj.gettype().equals("2") ) { %>
												<option value="2" selected="selected" >Sales Out ETC</option>
											<% } else { %>
												<option value="2" >Sales Out ETC</option>
											<% } %>
										</select>
									</TD>

								</TR>
								<TR>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel" colspan="5" >
									
										<% if(obj.getDongiagoc().equals("1")) {%> 
											<input type="checkbox" name="laygiaGoc" value="1" checked="checked" /> Đơn giá có VAT &nbsp; &nbsp; 
										<% }else {%> 
											<input type="checkbox" name="laygiaGoc" value="1" /> Đơn giá có VAT &nbsp; &nbsp; <% } %>
									
										&nbsp;&nbsp;&nbsp;
										
										<% if(obj.getChuyenSale().equals("1")) {%> 
											<input type="checkbox" name="laychuyenSale" value="1" checked="checked" /> Lấy chuyển sale &nbsp; &nbsp; 
										<% }else {%> 
											<input type="checkbox" name="laychuyenSale" value="1" /> Lấy chuyển sale &nbsp; &nbsp; <% } %>
											
										&nbsp;&nbsp;&nbsp;
										
										<% if(obj.getKhuyenmai().equals("1")) {%> 
											<input type="checkbox" name="laykhuyenMai" value="1" checked="checked" /> Lấy khuyến mại &nbsp; &nbsp; 
										<% }else {%> 
											<input type="checkbox" name="laykhuyenMai" value="1" /> Lấy khuyến mại &nbsp; &nbsp; <% } %>
									
										<%-- <%
										if(obj.getCn1().equals("1")) {%> 
											<input type="checkbox" name="cn1" value="1" checked="checked" /> Lấy số liệu CN1 bán tại khu vực &nbsp; &nbsp; 
										<% }else {%> 
											<input type="checkbox" name="cn1" value="1" /> Lấy số liệu CN1 bán tại khu vực &nbsp; &nbsp; <%} %> --%>
									</TD>
									
								</TR>
								
								<TR>
									<td class="plainlabel"colspan="4">
										<a class="button" href="javascript:submitform();"> 
										<img style="top: -5px;" src="../images/button.png" alt="">Tạo báo cáo </a>&nbsp; &nbsp;&nbsp; 
										<a class="button" href="javascript:search();">  
										<img style="top: -5px;" src="../images/button.png" alt="">Tìm kiếm </a>
										</td>
									<!-- <td class="plainlabel"><a class="button" href="javascript:search();"> 
										<img style="top: -5px;" src="../images/button.png" alt="">Tìm kiếm </a></td> -->
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
			                <tbody >
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
</tfoot>
						</TABLE>
					</fieldset>


						</div>
					</div>
	</form>
	<%
		if(npp!=null)npp.close();
		if(obj.getRsSearch()!=null)	obj.getRsSearch().close();
		if(ddkd!=null)ddkd.close();
		if(obj!=null){obj.DBclose();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>