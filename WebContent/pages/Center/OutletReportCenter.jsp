<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userTen = (String)session.getAttribute("userTen");
	String userId = (String)session.getAttribute("userId");
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet rsKenh = obj.getkenh();
	ResultSet rsKhuVuc = obj.getkhuvuc();
	ResultSet rsVung = obj.getvung();
	
	ResultSet rsNpp = obj.getnpp();
	ResultSet rsGsbh = obj.getgsbh();
	ResultSet rsDdkd = obj.getRsddkd();
	
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("OutletReportCenter","");
	
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
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = true;
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
	$("#ddkdId").select2();
	$("#gsbhId").select2();
	$("#kenhId").select2();
	$("#dvdlId").select2();
});
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../OutletReportCenter">
		<input type="hidden" value="1" name="action"  >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">&nbsp;<%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen %></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="../../CtkhuyenmaiSvl?userId="> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A href="javascript:saveform()"> <IMG src="../images/Save30.png"
					title="Luu lai" alt="Luu lai" border="1px"
					style="border-style: outset"></A>
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
					<legend class="legendtitle">Đánh giá cửa hiệu</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel" >Từ ngày</TD>
									<TD class="plainlabel" ><input type="text" name="Sdays"
										class="days" value='<%=obj.gettungay() %>' /></TD>
									<TD class="plainlabel" >Đến ngày</TD>
									<TD class="plainlabel"><input type="text" name="Edays"
										class="days" value='<%=obj.getdenngay() %>' /></TD>
								</TR>
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel"><select onchange="search();"  name="kenhs" id="loaiCt" id="kenhId" style="width:202px">
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
									<TD class="plainlabel">PTT / GĐ CN 2</TD>
									<TD class="plainlabel">
										<select name="gsbhs" onchange="search();"   id="gsbhId" style="width:202px">											
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
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="vungs"  id="vungId" style="width:202px">
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
									<TD class="plainlabel">Chi nhánh / Đối tác</TD>
									<TD class="plainlabel">
										<select value="" onchange="search();"  name="npps"  id="nppId" style="width:202px">
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
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
										<select onchange="search();"  name="khuvucs"  id="khuvucId" style="width:202px">
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
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel">
										<select value="" name="ddkds"  id="ddkdId" style="width:202px">
											<option value="">All</option>
											<% if(rsDdkd!=null){
													while(rsDdkd.next()){
														String ddkdId = rsDdkd.getString(1);
														if(ddkdId.equals(obj.getDdkd())){%>
															<option selected="selected" value="<%=rsDdkd.getString(1)%>"><%=rsDdkd.getString(2)%></option>
														<%}else{%>
														<option value="<%=rsDdkd.getString(1)%>"><%=rsDdkd.getString(2)%></option>
											<%}}}%>
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
									<legend class="legendtitle"> Fields ẩn </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbShowFields">
										<tbody id="ShowFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chon an</th>
											</tr>
											<tr class="tbdarkrow">
												<td>Channel</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Channel"></td>
											</tr>
											<tr class="tblightrow">
												<td>Region</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Region"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Area</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Area"></td>
											</tr>
											<tr class="tblightrow">
												<td>Sales Sup</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SalesSup"></td>
											</tr>											
											<tr class="tbdarkrow">
												<td>Distributor</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Distributor"></td>
											</tr>
										
											<tr class="tblightrow">
												<td>Sales Rep</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SalesRep"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Customer</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Customer"></td>
											</tr>
											<tr class="tblightrow">
												<td>Sales Rep</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="SalesRep"></td>
											</tr>											
											<tr class="tbdarkrow">
												<td>Highest ever Volume</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="HighestEverVolume"></td>
											</tr>
											<tr class="tblightrow">
												<td>Monthly Avg second sale</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MonthlyAvgSecondSale"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>First Buy date</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="FirstBuyDate"></td>
											</tr>
											<tr class="tblightrow">
												<td>Last Buy date</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="LastBuyDate"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Monthly archive second sales</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="MonthlyArchiveSecondSales"></td>
											</tr>
											<tr class="tblightrow">
												<td>#Order</td>
												<td align="center"><input name="fieldsHien"
													type="checkbox" value="Order"></td>
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
						<div style="width: 30%; float: left ;display: none">
								<fieldset style="min-height: 200px">
									<legend class="legendtitle"> Tất cả các Fields </legend>
									<TABLE width="100%" border="0" cellspacing="1" cellpadding="4"
										id="tbAllFields">
										<tbody id="AllFields">
											<tr class="tbheader">
												<th></th>
												<th align="center">Chon hien</th>
											</tr>
											<tr class="tblightrow">
												<td>Province</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="Province"></td>
											</tr>
											<tr class="tbdarkrow">
												<td>Distributor code</td>
												<td align="center"><input name="fieldsAn"
													type="checkbox" value="DistributorCode"></td>
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
	<%
	try{
		if(rsKenh !=null)rsKenh.close();
		if(rsVung!=null)rsVung.close();
		if(rsKhuVuc!=null)rsKhuVuc.close();
		if(rsNpp!=null)rsNpp.close();
		if(rsDdkd!=null)rsDdkd.close();
		if(rsGsbh!=null)rsGsbh.close();
		if(obj!=null){obj.DBclose();
		obj = null;}
		session.setAttribute("obj", null);
	}catch(Exception er){
		
	}
	%>
</body>
</html>