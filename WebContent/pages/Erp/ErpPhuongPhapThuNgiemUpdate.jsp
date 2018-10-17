<%@page import="geso.traphaco.erp.beans.phuongphapthunghiemthamso.IPhuongPhapThuNghiemTieuDeMau"%>
<%@page import="geso.traphaco.erp.beans.phuongphapthunghiemthamso.PhuongPhapThuNghiemThamSo"%>
<%@page import="java.util.ArrayList"%>
<%@page import="geso.traphaco.erp.beans.phuongphapthunghiemthamso.imp.IPhuongPhapThuNghiemThamSo"%>
<%@page import="java.util.List"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.phuongphapthunghiem.IPhuongPhapThuNghiem"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	IPhuongPhapThuNghiem obj=(IPhuongPhapThuNghiem)session.getAttribute("obj");
	String userId=(String)session.getAttribute("userId");
	String userTen=(String)session.getAttribute("userName");
	ResultSet rsSanPham=obj.getRsSanPham();
	ResultSet rsLoaiTieuChi=obj.getRsLoaiTieuChi();
	ResultSet rsYeuCauKyThuat=obj.getRsYeuCauKyThuat();
	
	
	List<IPhuongPhapThuNghiemThamSo> listTS=new ArrayList<IPhuongPhapThuNghiemThamSo>();
	List<IPhuongPhapThuNghiemTieuDeMau> listTDM=obj.getListTieuDeMau();
	ResultSet rsDonViDoLuong=obj.getRsDonViDoLuong();
	
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript"
	src="../scripts/jquery.tools.min.js"></script>
<script type="text/javascript" src="../scripts/ajax.js"></script>

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>

<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link rel="stylesheet" type="text/css" href="../css/style.css" />


<link rel="stylesheet" href="../css/cdtab.css" type="text/css">
<link rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs.css">
<link rel="stylesheet" type="text/css" media="screen"
	href="../css/tabs-panes.css">
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>



<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
/* Big box with list of options */
		#ajax_listOfOptions{
			position:absolute;	/* Never change this one */
			width:auto;	/* Width of box */
			height:200px;	/* Height of box */
			overflow:auto;	/* Scrolling features */
			border:1px solid #317082;	/* Dark green border */
			background-color:#C5E8CD;	/* White background color */
	    	color: black;
			text-align:left;
			font-size:1.0em;
			z-index:100200;
		}
		#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
			margin:1px;		
			padding:1px;
			cursor:pointer;
			font-size:1.0em;
		}
		#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
			
		}
		#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
			background-color:#317082; /*mau khi di chuyen */
			color:#FFF;
		}
		#ajax_listOfOptions_iframe{
			background-color:#F00;
			position:absolute;
			z-index:5;
		}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>

<link media="screen" rel="stylesheet" href="../css/colorbox.css">
 <script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script type="text/javascript">
     $(document).ready(function() { 
    	
    	 $(".dieukienkhuyenmai").colorbox({width:"65%", inline:true, href:"#dieukienkhuyenmai"});
    	 $(".tieudemau").colorbox({width:"30%", inline:true, href:"#tieudemau"}); 
         //Example of preserving a JavaScript event for inline calls.
         $("#click").click(function(){ 
             $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit","height":"500px"}).text("");
             return false;
         });	 
     });
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
	
</script>
<script type="text/javascript">
function keypress(e) {    
	var keypressed = null;
	if(window.event)
		keypressed = window.event.keyCode;
	else
		keypressed = e.which;
	
	if((keypressed>31 && keypressed<43) || (keypressed>43 && keypressed<45) || keypressed == 47 || keypressed>57) {
        return false;
    }
    return true;
}
	function save(){
		var sl=document.getElementById("sl");
		if(sl.value=='' || sl.value<=0 )
		 {
			 alert('Vui lòng nhập số lượng tham số nhập liệu > 0!');
			 return false;
		 }
		var slmau=$('#slmau').val();
		if(parseFloat('0' + slmau) <= 0 )
		 {
			 alert('Vui lòng nhập số lượng mẫu > 0!');
			 return false;
		 }
		document.forms["pptnForm"].action.value="capnhat";
		document.forms["pptnForm"].submit();
	}
	
	function istich(a){
		var congthuc = $('input[name=congthuc]');
		if($('#loaitick_'+a).attr('checked')){
			$(congthuc[a]).val("");
			congthuc[a].readOnly = true;
			$('#min_'+a).prop('disabled', true);
			$('#max_'+a).prop('disabled', true);
			$('#avg_'+a).prop('disabled', true);
			$('#sum_'+a).prop('disabled', true);
			$('#min_'+a).prop('checked', false);
			$('#max_'+a).prop('checked', false);
			$('#avg_'+a).prop('checked', false);
			$('#sum_'+a).prop('checked', false);
		}else{
			congthuc[a].readOnly = false;
			$('#min_'+a).prop('disabled', false);
			$('#max_'+a).prop('disabled', false);
			$('#avg_'+a).prop('disabled', false);
			$('#sum_'+a).prop('disabled', false);
		}
	}
</script>

 

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="pptnForm" method="post" action="../../ErpPhuongPhapThuNghiemUpdateSvl">

		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="">

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
			height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left" class="tbnavigation">
								<table width="100%" border="0" cellpadding="0" cellspacing="0">
									<tr height="22">
										<TD align="left" colspan="2" class="tbnavigation">&nbsp;Dữ
											liệu nền > Hồ Sơ Kiểm Nghiệm > Phương Pháp Thử Nghiệm > Cập Nhật</TD>
										<TD colspan="2" align="right" class="tbnavigation">Chào
											mừng bạn <%=userTen%></TD>
									</tr>
								</table>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<TR>
							<TD>
								<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
									<TR class="tbdarkrow">
										<TD width="30" align="left"><A
											href="../../ErpPhuongPhapThuNghiemSvl?userId=<%=userId%>"><img
												src="../images/Back30.png" alt="Quay ve" title="Quay ve"
												border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
										<TD width="2" align="left"></TD>
										<TD width="30" align="left">
											<div id="btnSave">
												<A href="javascript: save()"><IMG
													src="../images/Save30.png" title="Luu lai" alt="Luu lai"
													border="1" style="border-style: outset"></A>
											</div>
										</TD>
										<TD>&nbsp;</TD>
									</TR>
								</TABLE>
							</TD>
						</TR>
					</TABLE>
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
						<tr>
							<TD align="left" colspan="4" class="legendtitle">
								<fieldset>
						    		<legend class="legendtitle"> Thông báo</legend>
						    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= obj.getMsg() %></textarea>
								         <% obj.setMsg(""); %>
    							</fieldset>
							</TD>
						</tr>
						<TR>
							<TD height="100%" width="100%">
								<FIELDSET>
									<LEGEND class="legendtitle" style="color: black">Thông tin phương pháp thử nghiệm </LEGEND>
									<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
									<input type="hidden" name="pk_seq" id="pk_seq" value="<%=obj.getPK_SEQ() %>">
										<TR>
										    <TD class="plainlabel"  valign="middle">Mã phương pháp thử nghiệm</TD>
											<TD class="plainlabel"  > 
											<input type="text" name="mapptn" id= "mapptn"  value="<%=obj.getMaPPTN() %>">
											</TD>
											 <TD class="plainlabel"  valign="middle">Tên viết tắt</TD>
											<TD class="plainlabel"  > 
											<input type="text" name="tenvt" id= "tenvt"  value="<%=obj.getTenPPTN() %>">
											</TD>
										</TR>
										<TR>
										   
										</TR>
										<TR>
										    <TD class="plainlabel"  valign="middle">Mô tả diễn giải</TD>
											<TD class="plainlabel"  > 
										<%-- 	<input type="text" name="mota" id= "mota"  value="<%=obj.getDienGiai() %>"> --%>
												<textarea rows="8" cols="8" name="mota" id= "mota" ><%=obj.getDienGiai()%></textarea>
											
											</TD>
											 <TD class="plainlabel"  valign="middle">Sản phẩm</TD>
											<TD class="plainlabel" valign="middle"><select
												name="sanphamid" id="sanphamid" class="select2" onChange="" style="width: 200px" multiple="multiple">
												<option value=""></option>
													 <%
														if (rsSanPham != null) {
																while (rsSanPham.next()) {
																	if (obj.getMasanpham().indexOf(rsSanPham.getString("PK_SEQ"))>=0) {
													%>
													
													<option value="<%=rsSanPham.getString("PK_SEQ")%>"
														selected="selected"><%=rsSanPham.getString("ma")%>-<%=rsSanPham.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=rsSanPham.getString("pk_seq")%>"><%=rsSanPham.getString("ma")%>-<%=rsSanPham.getString("ten")%></option>
													<%
														}
																}
																rsSanPham.close();
															}
													%> 
											</select></TD>
										</TR>
										
										<TR>
											<TD class="plainlabel" valign="middle" width="15%">Loại Tiêu Chí</TD>
											<TD class="plainlabel" valign="middle"><select
												name="loaitieuchi" id="loaitieuchi" class="select2" onChange="" style="width: 200px">
												<option value=""></option>
													 <%
														if (rsLoaiTieuChi != null) {
																while (rsLoaiTieuChi.next()) {
																	if (rsLoaiTieuChi.getString("PK_SEQ").equals(obj.getMaLoaiTieuChi())) {
													%>
													
													<option value="<%=rsLoaiTieuChi.getString("PK_SEQ")%>"
														selected="selected"><%=rsLoaiTieuChi.getString("ma")%>-<%=rsLoaiTieuChi.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=rsLoaiTieuChi.getString("pk_seq")%>"><%=rsLoaiTieuChi.getString("ma")%>-<%=rsLoaiTieuChi.getString("ten")%></option>
													<%
														}
																}
																
															}
													%> 
											</select></TD>
											<TD class="plainlabel" valign="middle" width="15%">Yêu cầu kỹ thuật</TD>
											<TD class="plainlabel" valign="middle"><select
												name="yeucaukythuat" id="yeucaukythuat" class="select2" onChange="" style="width: 200px">
												<option value=""></option>
													<%
														if (rsYeuCauKyThuat != null) {
																while (rsYeuCauKyThuat.next()) {
																	if (rsYeuCauKyThuat.getString("pk_seq").equals(obj.getMaYeuCauKyThuat())) {
													%>
													
													<option value="<%=rsYeuCauKyThuat.getString("pk_seq")%>"
														selected="selected"><%=rsYeuCauKyThuat.getString("ten")%></option>
													<%
														} else {
													%>
													<option value="<%=rsYeuCauKyThuat.getString("pk_seq")%>"><%=rsYeuCauKyThuat.getString("ma")%>-<%=rsYeuCauKyThuat.getString("ten")%></option>
													<%
														}
																}
																
															}
													%>
											</select></TD>

										</TR>
											
										<!-- pop-up -->
										<TR class="plainlabel">
										 <TD class="plainlabel"  valign="middle">Số Lượng Tham Số Nhập Liệu</TD>
											<TD class="plainlabel"  > 
											<input type="number" onkeypress="return keypress(event);" name="sl" id= "sl"  value="<%=obj.getListThamSo().size() %>">
											</TD>
										<TD colspan="1">Tham số</TD>
											<td class="plainlabel"  align=left>
											<A href=""
												id="thamso" class="dieukienkhuyenmai" onclick ="">&nbsp; <img
													alt="Tham So" src="../images/vitriluu.png">
											</A> &nbsp;
										<div style='display:none' >
											<div id="dieukienkhuyenmai" style='padding:0px 5px; background:#fff; overflow: auto'>
										
										<table  align="center" id="tblThamSo" width="100%">
											<tr>
												<th width="5%" style="font-size: 11px">Ký hiệu</th>
												<th width="20%" style="font-size: 11px">Tham Số</th>
												<th width="5%" style="font-size: 11px">DVT</th>
												<th width="3%" style="font-size: 11px">Loại tick</th>
					                            <th width="5%" style="font-size: 11px">Tham số Group</th>
					                            <th width="30%" style="font-size: 11px">Công thức</th>
					                            <th width="5%" style="font-size: 11px">Min</th>
					                            <th width="5%" style="font-size: 11px">Max</th>
					                            <th width="5%" style="font-size: 11px">Avg</th>
					                            <th width="5%" style="font-size: 11px">Sum</th>
					                          
											</tr> 
											<%
												if(listTS!=null)
													listTS=obj.getListThamSo();
												for(int y=0;y < listTS.size();y++){
													IPhuongPhapThuNghiemThamSo ts=listTS.get(y);
													%>
														<tr>
															<td >
																<input readonly="readonly" type="text" style="width: 100%; text-align: center;" name="kyhieuThamSo" value="<%=ts.getKyhieuThamso() %>">
															</td>
															<td >
																<input type="text" style="width: 100%; text-align: right;" name="popupThamSo" value="<%=ts.getPopupThamSo() %>">
															</td>
															<TD ><select name="dvdl_<%=y %>" style="width: 110px">
																<option value=""></option>
													<%
														if (rsDonViDoLuong != null) {
															rsDonViDoLuong.beforeFirst();
																while (rsDonViDoLuong.next()) {
																	if(ts.getMaDVDL()!=null){
																		if (ts.getMaDVDL().equals(rsDonViDoLuong.getString("PK_SEQ"))) {
																		%>
																		
																		<option value="<%=rsDonViDoLuong.getString("pk_seq")%>" selected><%=rsDonViDoLuong.getString("DONVI")%></option>
																		<%
																			} else {
																		%>
																		<option value="<%=rsDonViDoLuong.getString("pk_seq")%>"><%=rsDonViDoLuong.getString("DONVI")%>-<%=rsDonViDoLuong.getString("DienGiai")%></option>
																		<%
																		}
																		
																	}else
																	{
																		%>
																		<option value="<%=rsDonViDoLuong.getString("pk_seq")%>"><%=rsDonViDoLuong.getString("DONVI")%>-<%=rsDonViDoLuong.getString("DienGiai")%></option>
																		<%
																	}
																}
																
															}
													%>
											</select></TD>
															<td align="center">
																<%
																	if(ts.getLoaiTick().equals("1"))
																	{
																		%>
																			<input type="checkbox" id="loaitick_<%=y %>" name="loaitick_<%=y %>" checked="checked" onchange="istich(<%=y %>)">
																		<% 
																	}else{
																		%>
																			<input type="checkbox" id="loaitick_<%=y %>" name="loaitick_<%=y %>" onchange="istich(<%=y %>)">
																	<% 
																	}
																%>
																
															</td>
															<td align="center">
																<%
																	if(ts.getThamSoPopup().equals("1"))
																	{
																		%>
																			<input type="checkbox"  name="thamsogroup_<%=y %>" checked="checked">
																		<% 
																	}else{
																		%>
																			<input type="checkbox"  name="thamsogroup_<%=y %>" >
																	<% 
																	}
																%>
															</td>
															<td >
																<input type="text" style="width: 100%; text-align: right;"  <% if(ts.getLoaiTick().equals("1")) {%> readonly="readonly" <% }%> name="congthuc" value="<%=ts.getCongthuc() %>">
															</td>
															<td align="center">
																<%
																	if(ts.getMin().equals("1"))
																	{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled" <% }%> id="min_<%=y %>"  name="min_<%=y %>" checked="checked">
																		<% 
																	}else{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled" <% }%> id="min_<%=y %>" name="min_<%=y %>" >
																	<% 
																	}
																%>
															</td>
															<td align="center">
																<%
																	if(ts.getMax().equals("1"))
																	{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled" <% }%> id="max_<%=y %>" name="max_<%=y %>" checked="checked">
																		<% 
																	}else{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled" <% }%> id="max_<%=y %>" name="max_<%=y %>" >
																	<% 
																	}
																%>
															</td>
															<td align="center">
																<%
																	if(ts.getAvg().equals("1"))
																	{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled"<% }%> id="avg_<%=y %>" name="avg_<%=y %>" checked="checked">
																		<% 
																	}else{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled" <% }%> id="avg_<%=y %>" name="avg_<%=y %>" >
																	<% 
																	}
																%>
															</td>
															<td align="center">
																<%
																	if(ts.getSum().equals("1"))
																	{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled" <% }%> id="sum_<%=y %>" name="sum_<%=y %>" checked="checked">
																		<% 
																	}else{
																		%>
																			<input type="checkbox" <% if(ts.getLoaiTick().equals("1")) {%> disabled="disabled" <% }%> id="sum_<%=y %>" name="sum_<%=y %>" >
																	<% 
																	}
																%>
															</td>
												</tr>
													<%
													
												}
											%>
												<input type="hidden" name="y" id="y" value='<%=obj.getListThamSo().size() %>'>

												
											
											</table>	
											
										</div>
										</div>
										</TR>
									
										<TR>
										    <TD class="plainlabel"  valign="middle">Số Lượng Mẫu</TD>
											<TD class="plainlabel" colspan="1" > 
											<input type="number" onkeypress="return keypress(event);" name="slmau" id= "slmau"  value="<%=obj.getSoLuongMau() %>">
											</TD>
										<TD class="plainlabel"  colspan="1">Tiêu đề mẫu</TD>
											<td class="plainlabel"  align=left>
											<A href=""
												id="Tieudemau1" class="tieudemau" onclick ="">&nbsp; <img
													alt="tieu de mau" src="../images/vitriluu.png">
											</A> &nbsp;
										<div style='display:none' >
											<div id="tieudemau" style='padding:0px 5px; background:#fff; overflow: auto' >
										
										<table  align="center" id="tblTieudemau" width="100%">
											<tr>
												<th width="10%" style="font-size: 11px">STT</th>
												<th width="90%" style="font-size: 11px">Tiêu đề mẫu</th>
												
					                          
											</tr> 
											<%
												if(listTDM!=null)
													listTDM=obj.getListTieuDeMau();
												for(int y=0;y < listTDM.size();y++){
													IPhuongPhapThuNghiemTieuDeMau tdm =listTDM.get(y);
													%>
														<tr>
															<td >
																<input readonly="readonly" type="text" style="width: 100%; text-align: center;" name="sttTDM" value="<%=tdm.getStt() %>">
															</td>
															<td >
																<input type="text" style="width: 100%; text-align: left;" name="tieudemau" value="<%=tdm.getTieudemau() %>">
															</td>
															
												</tr>
													<%
													
												}
											%>
												<input type="hidden" name="y" id="y" value='<%=obj.getListTieuDeMau().size() %>'>

												
											
											</table>	
											
										</div>
										</div>
										</td>	
											
										</TR>
										<TR>
										     <TD class="plainlabel"  valign="middle">Hoạt Động</TD>
											<TD class="plainlabel" colspan="3" > 
											<input type="checkbox" checked name="trangthai" id="trangthai"  value="1" >
											</TD>
										</TR>
											
									</TABLE>
									<select name="tem" id="tem"  style="width: 100px;display:none">
												
												<%
													if (rsDonViDoLuong != null) {
														rsDonViDoLuong.beforeFirst();
															while (rsDonViDoLuong.next()) {
												%>
																	<option value="<%=rsDonViDoLuong.getString("pk_seq")%>" ><%=rsDonViDoLuong.getString("DONVI")%>-<%=rsDonViDoLuong.getString("DIENGIAI")%></option>
												<%
																
											
															}
															
														}
												%>
											</select> 
								</FIELDSET>
							</TD>
						</TR>
					</TABLE>
				</TD>
			</TR>
		</TABLE>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
	<script type="text/javascript">
	

		$('body').delegate('#sl', 'change', function(event) {
			var sl=$(this).val();
			var s='';
			var r=parseInt($(this).val())-parseInt($('#y').val());
			/* if(parseInt($('#y').val()) < parseInt($(this).val()))
			{ */
				var sl=$(this).val();
			var s='<tr><th width="10%" style="font-size: 11px">Ký Hiệu Tham Số</th><th width="50%" style="font-size: 11px">Tên Tham Số</th><th width="5%" style="font-size: 11px">DVT</th><th width="3%" style="font-size: 11px">Loại tick</th><th width="10%" style="font-size: 11px">Tham số công thức</th><th width="10%" style="font-size: 11px">Công thức</th><th width="10%" style="font-size: 11px">Min</th><th width="10%" style="font-size: 11px">Max</th><th width="10%" style="font-size: 11px">Avg</th><th width="10%" style="font-size: 11px">Sum</th></tr> ';
				for(var i=0;i<sl;i++){
					s+='<tr> <td width="10%" > <input readonly="readonly" style="width: 100%; text-align: center;" name="kyhieuThamSo" value='+String.fromCharCode(97 + i)+'> </td>';
					s+='<td > <input type="text" style="width: 150px; text-align: right;" name="popupThamSo"> </td>';
					s+='<td ><select name="dvdl_'+i+'" style="width: 110px"><option></option>';
					
					$.each($('#tem option'),function(index,val) {
						s+='<option value='+val.value+'>'+val.text+'</option>';
					})
					s+='</select></td>';
					s+='<td align="center"> <input type="checkbox" onchange="istich('+i+')" id="loaitick_'+i+'"  name="loaitick_'+i+'"> </td> ';
					s+='<td align="center"> <input type="checkbox"  name="thamsogroup_'+i+'"> </td> ';
					s+='<td > <input type="text" style="width: 200px; text-align: right;" name="congthuc"> </td>';
					s+='<td align="center"> <input type="checkbox"  name="min_'+i+'"> </td> ';
					s+='<td align="center"> <input type="checkbox"  name="max_'+i+'"> </td> ';
					s+='<td align="center"> <input type="checkbox"  name="avg_'+i+'"> </td> ';
					s+='<td align="center"> <input type="checkbox"  name="sum_'+i+'"> </td> </tr>';
					s+=$('#tblThamSo:not(:first)').remove();
					$('#tblThamSo').html(s);
				}
			/* }
			
			else{
				var o=parseInt($('#y').val())+r;
				$('#tblThamSo tr').each(function(k,v){
					if(k > o){
					 $(this).html("");
					}
				});
			} */
			
		});
		
		$('body').delegate('#slmau', 'change', function(event) {
			var slm=$(this).val();
			var s='';
			var r=parseInt($(this).val())-parseInt($('#y').val());
			/* if(parseInt($('#y').val()) < parseInt($(this).val()))
			{ */
				var slm=$(this).val();
				var s='<tr><th width="5%" style="font-size: 11px">STT</th><th width="25%" style="font-size: 11px">Tiêu đề mẫu</th></tr> ';
				for(var i=0;i<slm;i++){
					s+='<tr> <td width="10%" > <input readonly="readonly" style="width: 100%; text-align: center;" name="sttTDM" value='+(i+1)+'> </td>';
					s+='<td > <input type="text" style="width: 250px; text-align: left;" name="tieudemau"> </td> </tr>';
					s+=$('#tblTieudemau:not(:first)').remove();
					$('#tblTieudemau').html(s);
				}
			
		});
	
	
	</script>
	</form>
	

	
</BODY>
</HTML>

