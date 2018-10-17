<%@page import="geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.PhieuKiemDinh"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="geso.traphaco.erp.beans.yclaymaukiemnghiem.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@page import="java.sql.SQLException"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
	IYCLayMauKiemNghiem obj = (IYCLayMauKiemNghiem) session
			.getAttribute("obj");
%>
<%
	String userId = (String) session.getAttribute("userId");
%>
<%
	String userTen = (String) session.getAttribute("userTen");
%>
<%
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
		return;
	} else {
		int[] quyen = new int[5];
		quyen = util.Getquyen("YCLayMauKiemNghiemSvl", "", userId);
	}

	ResultSet rsMauKiemNghiem = obj.getRsMauKiemNghiem();
	ResultSet rsPhongBan = obj.getRsPhongBan();
	ResultSet rsPhieuNhanHang = obj.getRsPhieuNhanHang();
	ResultSet rsKhoBietTru = obj.getRsKhoBietTru();
	ResultSet rsKhoTonTru = obj.getRsKhoTonTru();
	ResultSet rsKhoXuatMau = obj.getRsKhoXuatMau();
	ResultSet rsPhieuKiemDinh = obj.getRsPhieuKiemDinh();
	ResultSet rsLoaiKiemDinh = obj.getRsLoaiKiemDinh();
	ResultSet rsKhoanMuc = obj.getRsKhoanMuc();
	ResultSet rsSanPham = obj.getRsSanPham();
	DecimalFormat format = new DecimalFormat("###,###,###.##");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=getServletContext().getInitParameter("TITLENAME")%></title>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<!-- Begin Footer auto -->
<%@ include file="../Parts/header.jsp"%>
<!-- End Footer auto -->
<script type="text/javascript">

	function filter(){
		 document.forms['form'].action.value='filter';
	     document.forms['form'].submit();
	     
	}
	
	
	function saveform(){
		var loaikiemdinh=document.getElementById("loaikiemdinh").value;
		var sophieukd=document.getElementById("sophieukd").value;
		var phongbanthId=document.getElementById("phongbanthId").value;
		var sanphamkiemdinh=document.getElementById("sanphamkiemdinh").value;
		
		var khoxuatmauId=document.getElementById("khoxuatmauId").value;
		var khobiettruId=document.getElementById("khobiettruId").value;
		var khotontruId=document.getElementById("khotontruId").value;
		var khoanmuc=document.getElementById("khoanmuc").value;
		
		
		if(loaikiemdinh.length<0|| sophieukd.length<0||phongbanthId.length<0|| sanphamkiemdinh.length<0
				||khoxuatmauId.length<0|| khobiettruId.length<0||khotontruId.length<0|| khoanmuc.length<0){
			document.getElementById("dataerror").value="Vui lòng nhập đầy đủ thộng tin !!!";
		}
		else{
		
		 document.forms['form'].action.value='save';
	     document.forms['form'].submit();
		}
	}
	
	function thaydoiLoaiKD(){
		 document.forms['form'].action.value='thaydoiLoaiKD';
	     document.forms['form'].submit();
	     
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
			
		//num = (Math.round( num * 1000 ) / 1000).toString();
			
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
			
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));
	
		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
			
			//alert(kq);
		return kq;
	}
	 
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
	}
	
	function replaces(){
		
		var masp = $('input[name=masp]');
		var soluongphieunop = $('input[name=soluongphieunop]');
		var soluonglaymau = $('input[name=soluonglaymau]');
		var soluongmauluu = $('input[name=soluongmauluu]');
		var soluongtd = $('input[name=soluongtd]');
		
		for(i = 0; i < masp.length ; i++){
			if(masp[i].value !== ""){
				var sl1 = parseFloat( toNum(soluongphieunop[i].value).replace(/,/g, ""))
				var sl2 = parseFloat( toNum(soluonglaymau[i].value).replace(/,/g, ""))
				var sl3 = parseFloat( toNum(soluongmauluu[i].value).replace(/,/g, ""))
				var sl4 = parseFloat( toNum(soluongtd[i].value).replace(/,/g, ""))
				$('input[name=soluongconlai]')[i].value = DinhDangDonGia(parseFloat( sl1 - sl2 - sl3 - sl4) );
				
			}
		}
		
		setTimeout(replaces, 500);
	}
	
	function TinhSl(){
		var masp = $('input[name="masp"]');
		 for(var i=0; i<masp.length; i++){
			//var a = masp[i].value;
			//So luong lay mau
			var sllm_name = masp[i].value+"__ _spSLlaymau"; 
			var b = sllm_name.split("__ _")[0];
			var sllm_index = _.findIndex(masp, x => x.value = b);
			var soluonglaymau = $('input[name="soluonglaymau"]');
			soluonglaymau[sllm_index].value =_.sum(_.map( $('input[name="'+sllm_name+'"]'), x => parseFloat(x.value)));
			
			//So luong mau luu
			var slml_name = masp[i].value+"__ _spSLmauluu";
			var c = slml_name.split("__ _")[0];
			var slml_index = _.findIndex(masp, x => x.value = c);
			var soluongmauluu = $('input[name="soluongmauluu"]');
			soluongmauluu[slml_index].value = _.sum(_.map($('input[name="'+slml_name+'"]'), x => parseFloat(x.value)));
			
			//So luong theo doi do on dinh
			var slod_name = masp[i].value+"__ _spSLDoOnDinh";
			var d = slod_name.split("__ _")[0];
			var slod_index = _.findIndex(masp, x => x.value = d);
			var soluongondinh = $('input[name="soluongtd"]');
			soluongondinh[slod_index].value = _.sum(_.map($('input[name="'+slod_name+'"]'), x => parseFloat(x.value)));
		 } 
	}
	
	function toNum(num){
		if(num === "") return "0";
		return num;
	}
	
	function goBack(){
		window.history.back();
	} 
</script>
</head>
<body>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="form" method="post"
		action="../../YCLayMauKiemNghiemUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
			<input type="hidden" name="id" value='<%=obj.getId()%>'> <input
			type="hidden" name="action" value='1'>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Dữ liệu nền &gt; Hồ sơ kiểm nghiệm &gt;
					Yêu cầu lấy mẫu kiểm nghiệm &gt; Cập nhập</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="javascript:goBack()"> <img src="../images/Back30.png"
					alt="Quay ve" title="Quay ve" border="1px" longdesc="Quay ve"
					style="border-style: outset"></A> <span id="btnSave"> <A
					href="javascript:saveform()"> <IMG src="../images/Save30.png"
						title="Luu lai" alt="Luu lai" border="1px"
						style="border-style: outset"></A>
				</span>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="dataerror" rows="1" readonly="readonly"
						style="width: 100%"> <%=obj.getMsg() %> </textarea>

				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset id="hoadon">
					<legend class="legendtitle"> Yêu cầu lấy mẫu kiểm nghiệm</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">

							<TR>
								<TD class="plainlabel" width="15%">Ngày chứng từ</TD>
								<TD class="plainlabel" width="15%"><input type="text" 
									name="ngayct" id="ngayct" value="<%=obj.getNgayChungTu() %>"
									readonly="readonly"
									class="days"
									 /></TD>
								<TD class="plainlabel" width="15%">Phòng ban TH</TD>
								<TD class="plainlabel" colspan="3"><SELECT
									NAME="phongbanthId" id="phongbanthId"
									style="width: 200px; height: 20" onchange="filter()">
										<option value="" SELECTED></option>
										<%
											if (rsPhongBan != null) {
												try {
													while (rsPhongBan.next()) {
														if (rsPhongBan.getString("pk_seq").equals(
																obj.getPhongBanId())) {
										%>
										<option value="<%=rsPhongBan.getString("pk_seq")%>"
											selected="selected"><%=rsPhongBan.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsPhongBan.getString("pk_seq")%>"><%=rsPhongBan.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT></TD>

							</TR>
							
							
							
							<TR>
								<TD class="plainlabel" width="15%">Loại kiểm định</TD>
								<TD class="plainlabel"
								<% if(obj.getLoaiKiemDinh().equals("1")) { %> colspan="3"  <%} %>
								
								><SELECT NAME="loaikiemdinh"
									id="loaikiemdinh" style="width: 200px; height: 20" onchange="thaydoiLoaiKD()">
										<option value="" SELECTED></option>
										<%
											if (rsLoaiKiemDinh != null) {
												try {
													while (rsLoaiKiemDinh.next()) {
														if (rsLoaiKiemDinh.getString("pk_seq").equals(
																obj.getLoaiKiemDinh())) {
										%>
										<option value="<%=rsLoaiKiemDinh.getString("pk_seq")%>"
											selected="selected"><%=rsLoaiKiemDinh.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsLoaiKiemDinh.getString("pk_seq")%>"><%=rsLoaiKiemDinh.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT></TD>

								<% if(!obj.getLoaiKiemDinh().equals("1")) { %>
								<TD class="plainlabel" width="15%">Số phiếu kiểm định chất
									lượng</TD>
								<TD class="plainlabel" width="15%">
									<SELECT
									NAME="sophieukd" id="sophieukd"
									style="width: 200px; height: 20" onchange="filter()">
										<option value="" SELECTED></option>
										<%
											if (rsPhieuKiemDinh != null) {
												try {
													while (rsPhieuKiemDinh.next()) {
														if (rsPhieuKiemDinh.getString("pk_seq").equals(
																obj.getSoPhieuKiemDinh())) {
										%>
										<option value="<%=rsPhieuKiemDinh.getString("pk_seq")%>"
											selected="selected"><%=rsPhieuKiemDinh.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsPhieuKiemDinh.getString("pk_seq")%>"><%=rsPhieuKiemDinh.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT>
									
									</TD>
									<%} %>
							</TR>
							
							<TR>
								<TD class="plainlabel" width="15%">Số chứng từ nhập mẫu</TD>
								<TD class="plainlabel" width="15%"><input type="text"
									name="soctmau" id="soctmau" value="<%=obj.getSoChungTuNhapMau() %>"></TD>
								<TD class="plainlabel" width="15%">Sản phẩm kiểm định</TD>
								<TD class="plainlabel" colspan="3"><SELECT
									NAME="sanphamkiemdinh" id="sanphamkiemdinh"
									style="width: 200px; height: 20" onchange="filter()">
										<option value="" SELECTED></option>
										<%
											if (rsSanPham != null) {
												try {
													while (rsSanPham.next()) {
														if (rsSanPham.getString("pk_seq").equals(
																obj.getSanphamId())) {
										%>
										<option value="<%=rsSanPham.getString("pk_seq")%>"
											selected="selected"><%=rsSanPham.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsSanPham.getString("pk_seq")%>"><%=rsSanPham.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT></TD>
							</TR>

							<TR style="display:none">
								<TD class="plainlabel" width="15%">Loại mẫu KN</TD>
								<TD class="plainlabel"><SELECT NAME="loaimaukn"
									id="loaimaukn" style="width: 200px; height: 20">
										<option value="" SELECTED></option>
										<%
											if (rsMauKiemNghiem != null) {
												try {
													while (rsMauKiemNghiem.next()) {
														if (rsMauKiemNghiem.getString("pk_seq").equals(
																obj.getMauKiemNghiemId())) {
										%>
										<option value="<%=rsMauKiemNghiem.getString("pk_seq")%>"
											selected="selected"><%=rsMauKiemNghiem.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsMauKiemNghiem.getString("pk_seq")%>"><%=rsMauKiemNghiem.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT></TD>
								</TR>
								<tr>
								<TD class="plainlabel" width="15%">Chọn kho xuất mẫu</TD>
								<TD class="plainlabel" width="22%" colspan="3"><select
									id="khoxuatmauId" onchange="ChonKhoBietTru();"
									name="khoxuatmauId" style="width: 200px;">
										<option value="" SELECTED></option>
										<%
											if (rsKhoXuatMau != null) {
												try {
													while (rsKhoXuatMau.next()) {
														if (rsKhoXuatMau.getString("pk_seq").equals(
																obj.getKhoXuatMauId())) {
										%>
										<option value="<%=rsKhoXuatMau.getString("pk_seq")%>"
											selected="selected"><%=rsKhoXuatMau.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsKhoXuatMau.getString("pk_seq")%>"><%=rsKhoXuatMau.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>

								</select></TD>
								


							</TR>

							<TR>
								<TD class="plainlabel" width="15%">Hồ sơ kèm theo</TD>
								<TD class="plainlabel" width="15%"><input type="text"
									name="hosokemtheo" id="hosokemtheo" value="<%=obj.getHosokemtheo() %>"></TD>

								<TD class="plainlabel" width="15%">Chọn kho lưu mẫu</TD>
								<TD class="plainlabel" width="22%"><select
									id="khobiettruId" onchange="ChonKhoBietTru();"
									name="khobiettruId" style="width: 200px;">
										<option value="" SELECTED></option>
										<%
											if (rsKhoBietTru != null) {
												try {
													while (rsKhoBietTru.next()) {
														if (rsKhoBietTru.getString("pk_seq").equals(
																obj.getKhoBietTruId())) {
										%>
										<option value="<%=rsKhoBietTru.getString("pk_seq")%>"
											selected="selected"><%=rsKhoBietTru.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsKhoBietTru.getString("pk_seq")%>"><%=rsKhoBietTru.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>

								</select></TD>
									

							</TR>

							<TR>
								<TD class="plainlabel" width="15%">Khoản mục chi phí</TD>
								<TD class="plainlabel" width="15%" ><select
									id="khoanmuc" onchange="ChonKhoBietTru();"
									name="khoanmuc" style="width: 200px;">
										<option value="" SELECTED></option>
										<%
											if (rsKhoanMuc != null) {
												try {
													while (rsKhoanMuc.next()) {
														if (rsKhoanMuc.getString("pk_seq").equals(
																obj.getKhoanMucId())) {
										%>
										<option value="<%=rsKhoanMuc.getString("pk_seq")%>"
											selected="selected"><%=rsKhoanMuc.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsKhoanMuc.getString("pk_seq")%>"><%=rsKhoanMuc.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>

								</select></TD>
								
								<TD class="plainlabel" width="15%">Chọn kho theo dõi độ ổn định</TD>
								<TD class="plainlabel" width="22%"><select
									id="khotontruId" onchange="ChonKhoBietTru();"
									name="khotontruId" style="width: 200px;">
										<option value="" SELECTED></option>
										<%
											if (rsKhoTonTru != null) {
												try {
													while (rsKhoTonTru.next()) {
														if (rsKhoTonTru.getString("pk_seq").equals(
																obj.getKhoBietTruId())) {
										%>
										<option value="<%=rsKhoTonTru.getString("pk_seq")%>"
											selected="selected"><%=rsKhoTonTru.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsKhoTonTru.getString("pk_seq")%>"><%=rsKhoTonTru.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>

								</select></TD>

							</TR>

							<TR>
								<TD class="plainlabel" width="15%">Diễn giải</TD>
								<TD class="plainlabel" width="15%" colspan="1">
								<input type="text"
									name="dienGiai" id="dienGiai" value="<%=obj.getDienGiai()%>"></TD>                
								<TD class="plainlabel" width="15%">Quy trình lấy mẫu số</TD>
								<TD class="plainlabel" width="15%"><input type="text"
									name="soctmau" id="soctmau" value="<%=obj.getSoChungTuNhapMau() %>"></TD>
							</TR>

						</TABLE>

						<hr>
					</div>

					<!-- LIST SẢN PHẨM -->
					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="8%">Mã sản phẩm</th>
								<th align="center" width="10%">Tên sản phẩm</th>
								<th align="center" width="5%">ĐVT</th>
								<th align="center" width="8%">Số lượng (phiếu nhập)</th>

								<th align="center" width="5%">Số lượng lấy mẫu</th>
								<th align="center" width="5%">Số lượng mẫu lưu</th>
								<th align="center" width="8%">Số lượng theo dõi độ ổn định</th>
								<!-- <th align="center" width="8%">Số lượng còn lại</th> -->
								<th align="center" width="15%">Thời gian hủy mẫu dự kiến</th>

							</tr>
							
							<%
							int count =0;
							for(PhieuKiemDinh pkd : obj.getDsPhieuKiemDinhs()){ %>
							<tr >
								<td align="center" width="8%">
									<input type="text" style="width: 100%;" value="<%= pkd.getMaSp() %>" id="masp" name= "masp" readonly="readonly" >
								</td>
								<td align="center" width="10%">
									<input type="text" style="width: 100%;" value="<%= pkd.getTenSp() %>" name= "tensp" readonly="readonly" >
								</td>
								<td align="center" width="5%">
									<input type="text" style="width: 100%;text-align: center" value="<%= pkd.getDvt() %>" name= "donvi" readonly="readonly" >
								</td>
								<%-- <td align="center" width="8%">
									<input type="text" style="width: 100%;" value="<%= pkd.getLoHang() %>" name= "lohang" readonly="readonly" >
								</td>
								<td align="center" width="7%">
									<input type="text" style="width: 100%;" value="<%= pkd.getNgaySX() %>" name= "ngaysx" readonly="readonly" >
								</td>

								<td align="center" width="7%">
									<input type="text" style="width: 100%;" value="<%= pkd.getNgayHetHan() %>" name= "ngayhh" readonly="readonly" >
								</td>
								<td align="center" width="5%">
									<input type="text" style="width: 100%;text-align: center" value="<%= pkd.getMaThung() %>" name= "mathung" readonly="readonly" >
								</td>
								<td align="center" width="5%">
								<input type="text" style="width: 100%;text-align: center;" value="<%= pkd.getMaMe() %>" name= "mame" readonly="readonly" >
								</td> --%>
								<td align="center" width="8%">
									<input type="text" style="width: 100%; text-align: right" value="<%= format.format(Double.parseDouble( pkd.getSoLuongPhieuNop())) %>" name= "soluongphieunop" readonly="readonly" >
								</td>

								<td align="center" width="5%">
									<input type="text" style="width: 100%; text-align: right" value="<%= format.format(Double.parseDouble( "0" + pkd.getSoLuongLayMau())) %>" id="soluonglaymau_<%=count %>" name= "soluonglaymau"  readonly="readonly" >
								</td>
								<td align="center" width="5%">
									<input type="text" style="width: 100%; text-align: right" value="<%= format.format(Double.parseDouble( "0" + pkd.getSoLuongMauLuu())) %>" id="soluongmauluu_<%=count %>" name= "soluongmauluu"  readonly="readonly" >
								</td>
								<td align="center" width="8%">
									<input type="text" style="width: 100%;text-align: right" value="<%= format.format(Double.parseDouble( "0" +  pkd.getSoLuongTheoDoiDoOnDinh())) %>" id="soluongtd_<%=count %>" name= "soluongtd" readonly="readonly"  >
								</td>
								 <td align="center" width="8%" style="display:none">  
									<input type="text" style="width: 100%; text-align: right" value="<%= format.format(Double.parseDouble( "0" + pkd.getSoLuongConLai())) %>" name= "soluongconlai" readonly="readonly" >
								</td> 
								<td align="center" width="8%">
									<input type="text" class="days" style="width: 80%; text-align: center" value="<%= pkd.getThoiGianHuyMai() %>" name= "thoigiandukien" readonly="readonly" >
								
								<a href="" id="scheme_<%=  pkd.getMaSp() + "__ "+count  %>" rel="subcontent_<%= pkd.getMaSp()  + "__ "+count %>">
		           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu_2.png"></a>
								<DIV id="subcontent_<%= pkd.getMaSp() + "__ "+count %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 1100px; max-height:200px; overflow:auto; padding: 4px;z-index: 100" >
				                    <table width="100%" align="center">
				                    	
				                        <tr>
				                        	<th width="120px" style="font-size: 10px;">Số lượng</th>
				                            <th width="120px" style="font-size: 10px; color:red ;">Số lượng lấy mẫu</th>
				                            <th width="70px" style="font-size: 10px; color:red ; ">Số lượng mẫu lưu</th>
				                            <th width="70px" style="font-size: 10px; color:red ;">Số lượng theo dõi độ ổn định</th>
				                            
				                            <th width="120px" style="font-size: 10px">Lô hàng</th>
				                            <th width="70px" style="font-size: 10px">Ngày SX</th>
				                            <th width="70px" style="font-size: 10px">Ngày HH</th>
				                            <th width="25px" style="font-size: 10px">Mã thùng</th>
				                            <th width="25px" style="font-size: 10px">Mã mẻ</th>
				                            <th width="100px" style="font-size: 10px">Vị trí</th>
				                            <th width="120px" style="font-size: 10px">Mã phiếu</th>
				                            <th width="80px" style="font-size: 10px">Phiếu ĐT</th>
				                            <th width="50px" style="font-size: 10px">Phiếu EO</th>
				                            <th width="100px" style="font-size: 10px">Marquette</th>
				                            <th width="100px" style="font-size: 10px">Nhà sản xuất</th>
				                            <th width="25px" style="font-size: 10px">Hàm lượng</th>
				                            <th width="25px" style="font-size: 10px">Hàm ẩm</th>
				                        </tr>
		                            	 		<%
		                            	 		ResultSet spRs = obj.getRsLayChiTiet(obj.getSoPhieuKiemDinh(),pkd.getMaSp());
				                        		if(spRs != null)
				                        		{
				                        			while(spRs.next())
				                        			{
		                            	
			                        				String temID= pkd.getMaSp() + "__ ";
			                        			%>
			                        			
			                        			<tr>
			                        			<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" id="spSoluong_<%=count%>" name="<%=temID%>_spSoluong" readonly value="<%= format.format(Double.parseDouble( spRs.getString("SOLUONG")))%>" >
			                        				</td>
			                        				
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" id="spSLlaymau_<%=count%>" name="<%=temID%>_spSLlaymau" onkeyup="TinhSl();" value="<%= spRs.getString("SOLUONGLAYMAU")%>" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" id="spSLmauluu_<%=count%>" name="<%=temID%>_spSLmauluu" onkeyup="TinhSl();" value="<%= spRs.getString("SOLUONGMAULUU")%>">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" id="spSLDoOnDinh_<%=count%>" name="<%=temID%>_spSLDoOnDinh"  onkeyup="TinhSl();" value="<%= spRs.getString("SLOLUONGONDINH")%>" >
			                        					<input type="hidden" style="width: 100%; text-align: right" value="<%= format.format(Double.parseDouble( "0" + pkd.getSoLuongConLai())) %>" name= "<%=temID%>soluongconlai" readonly="readonly" >
			                        				</td>
			                        			
													<td>
			                        					<input type="hidden" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_spId" value="<%= spRs.getString("PK_SEQ")%>" readonly="readonly">
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_lohang" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%=temID%>_ngaysx" value="<%=spRs.getString("NGAYNHAPKHO")%>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%=temID%>_ngayhh" value="<%=spRs.getString("NGAYHETHAN")%>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%=temID%>_mathung" value="<%= spRs.getString("MATHUNG")%>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%=temID%>_mame" value="<%=spRs.getString("MAME") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_spVitri" value="<%= spRs.getString("vitri") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_spMAPHIEU" value="<%= spRs.getString("maphieu") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_spPhieudt" value="<%=spRs.getString("phieudt") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_spPhieueo" value="<%=spRs.getString("phieueo") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_spMarq" value="<%=spRs.getString("MARQ")%>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%=temID%>_spNhasanxuat" value="<%=spRs.getString("NSXTEN") %>" readonly="readonly">
			                        					<input type="hidden" name="<%=temID%>_spNhasanxuatID" value="<%=spRs.getString("nsxPk_seq")  %>">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%=temID%>_spHamluong" value="<%=spRs.getString("hamluong")%>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="<%=temID%>_spHamam" value="<%= spRs.getString("hamam")%>" readonly="readonly">
			                        				</td>
			                        				
			                        			</tr>
			                        			<% } } %>
			                        		
				                     </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%=pkd.getMaSp() + "__ "+count %>')" > Đóng lại </a>
				                     </div>
					            </DIV> 
					            
					            <script type="text/javascript">
					            	dropdowncontent.init('scheme_<%=pkd.getMaSp() + "__ "+count  %>', "left-top", 300, "click");
					            </script>
					            </td>
							</tr>

							<% count++;} %>

						</TABLE>
					</div>

				</fieldset>
			</div>
		</div>

	</form>
	<!-- Begin Footer auto -->
	<%@ include file="../Parts/footer.jsp"%>
	<!-- End Footer auto -->
	<script type="text/javascript">
	replaces();
	</script>
</BODY>
</html>