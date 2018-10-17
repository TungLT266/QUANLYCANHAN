
<%@page import="geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhuongPhap"%>
<%@page import="geso.traphaco.erp.beans.kiemnghiemchitiet.imp.PhieuKiemDinh"%>
<%@page import="geso.traphaco.erp.beans.kiemnghiemchitiet.IErpKiemNghiemChiTiet"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>

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
IErpKiemNghiemChiTiet obj = (IErpKiemNghiemChiTiet) session.getAttribute("obj");
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
		quyen = util.Getquyen("ErpKiemNghiemChiTietSvl", "", userId);
	}

	 
	ResultSet rsPhongBan = obj.getRsPhongBan();
	ResultSet rsHoSo = obj.getRsHoSo();
	ResultSet rsTieuChuan = obj.getRsTieuChuan();
	ResultSet rsSanPham = obj.getRsSanPham();
	ResultSet rsYeuCau = obj.getRsYeuCau();
	ResultSet rsDanhGia = obj.getRsDanhGia();
	DecimalFormat format = new DecimalFormat("###,###,###.###");
	format.setDecimalSeparatorAlwaysShown(true);
	format.setMinimumFractionDigits(3);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=getServletContext().getInitParameter("TITLENAME")%></title>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<link rel="stylesheet" href="//cdnjs.cloudflare.com/ajax/libs/timepicker/1.3.5/jquery.timepicker.min.css">
<script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
<script> 
$(document).ready(function(){
    $(".flip").click(function(){
        
        const i = $('.flip').index(this);
        $(".panel").filter(x => x != i).slideUp("slow");
        $($(".panel")[i]).slideToggle("slow");
    });
    
	$(".flip1").click(function(){
        
        const i = $('.flip1').index(this);
        $(".panel1").filter(x => x != i).slideUp("slow");
        $($(".panel1")[i]).slideToggle("slow");
    });
    
	$('input.timepicker').timepicker({
		'timeFormat': 'H:00'
	});
 
});
</script>

<!-- Begin Footer auto -->
<%@ include file="../Parts/header.jsp"%>
<!-- End Footer auto -->
<script type="text/javascript">

	function filter(){
		 document.forms['form'].action.value='filter';
	     document.forms['form'].submit();
	     
	}
	
	
	function saveform(){
		 document.forms['form'].action.value='save';
		 if(validate() == true){
	     	document.forms['form'].submit();
		 }
	}
	
	function validate(){
		
		if($('#ngaychungtu').val()=="" || $('#phongbanId').val()=="" || $('#hosokiemnghiem').val()=="" ||
		$('#sanphamkn').val()=="" || $('#tieuchuankn').val()=="" || $('#yckythuat').val()=="" || $('#thoigianbd').val()=="" ||
		$('#thoigiankt').val()=="" || $('#tongthoiluongtn').val()=="" || $('#danhgia').val()==""){
			
			$('#dataerrorid').val("Vui lòng nhập đầy đủ thông tin!");
			return false;
		}
		
		if($('#soluongtt').val()==""){
			$('#dataerrorid').val("Chưa nhập số lương thực tế!");
			return false;
		}
		 
		return true;
	}
	
	function validate_active(i){
		
		if($('#phuongphap_'+i).hasClass( "active" ) == false){
			
			$('#dataerrorid').val("Vui lòng nhập đầy đủ các phương pháp!");
			return false;
		}
		return true;
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
	 
	function filter_yckt(){
		document.forms['form'].action.value='filter_yckt';
	    document.forms['form'].submit();
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
	
	function replaceCongThuc(k){
		const __size = _isNullOrNumeric( $('#pp_size' + k).val() );
		const __thamso = $('input[name=pp_kyhieu'+(k+1)+']').length;
		if(__size > 0){
			for(i = 0; i < __size; i++){
				try {
					var congthuc = $('#pp_thamso_group' + (k+1) ).val();
					const dapan = $('#pp_mau_group' + (k+1) + '_' + (i+1));
					
					const kh = $('input[name=pp_kyhieu'+(k+1)+']');
					const a = $('input[name=pp_mau' + (k+1) + '_'+(i+1)+']').not(dapan);	
					
					for(j = 0; j < a.length; j++){
						console.log(a[j]);
						console.log(kh[j]);
						congthuc = congthuc.replace(new RegExp(kh[j].value, 'g'),_isNullOrNumeric(a[j].value) );
					}
					
					console.log(congthuc);
					
					$('#pp_mau_group' + (k+1) + '_'+(i+1)).val(eval(congthuc));
					
					 
				}catch(e){
					console.log(e);
				}
			}
				
		}
	
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
		let pp_size = $('input[name=pp_size]');
		
		for(i = 0; i < pp_size.length; i++){
			replaceCongThuc(i);	
		}
		setTimeout(replaces, 500);
	}
	
	function toNum(num){
		if(num === "") return "0";
		return num;
	}
	
	function goBack(){
		window.history.back();
	}
 
	function time(){
 
		var ngay = "13";
		var thang = "02";
		var nam = "2018";
		
		var thoigianbd = $('#thoigianbd').val();
		var thoigiankt = $('#thoigiankt').val();
		var timebd = thoigianbd.split(':');
		var timekt = thoigiankt.split(':');
		var hour1 = timebd[0];
		var minute1 = timebd[1];
		var hour2 = timekt[0];
		var minute2 = timekt[1];
		var time1 = new Date(ngay,thang,nam,hour1,minute1);
		var time2 = new Date(ngay,thang,nam,hour2,minute2);
		var time3 = time2.getHours() - time1.getHours(); 
		$('#tongthoiluongtn').val(time3);
	}
	
	function filter(){
		
		document.forms['form'].action.value='filter';
     	document.forms['form'].submit();
	}

	function background(i){
		var ketqua = $('input[name=ketquapp_]');
		
		if(ketqua[i].value !== ""){
			$('#phuongphap_'+i).addClass('active');
		}
	}
	
	function danhgia(k, j, i){
		
		
		let e = $('#pp_danhgia'+j+'_'+i+'_' +k+ ']');
		if(e.prop('checked')){
			$('input[name=pp_mau'+j+'_'+i+']')[k].value = "1";
		}else{
			$('input[name=pp_mau'+j+'_'+i+']')[k].value = "0";
		}
	}
		
	
	const danhGiaMucDo = (k, j, i) => {
		let e = $('#pp_danhgia'+j+'_'+i+'_' +k );
		if(e.prop('checked')){
			$('input[name=pp_mau'+j+'_'+i+']')[k].value = "1";
		}else{
			$('input[name=pp_mau'+j+'_'+i+']')[k].value = "0";
		}
	};
	
</script>
</head>
<body>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="form" method="post"
		action="../../ErpKiemNghiemChiTietUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		 <input type="hidden" name="id" value='<%=obj.getId()%>'> 
		<input
			type="hidden" name="action" value='1'>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Dữ liệu nền &gt; Hồ sơ kiểm nghiệm &gt;
					Kiểm nghiệm chi tiết &gt; Cập nhật</div>
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
					<textarea name="dataerror" rows="1" readonly="readonly"
						style="width: 100%"> <%=obj.getMsg() %> </textarea>

				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset id="hoadon">
					<legend class="legendtitle"> Kiểm nghiệm chi tiết</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
				
							<TR>
								<TD class="plainlabel" width="15%">Ngày chứng từ</TD>
								<TD class="plainlabel" width="30%"><input type="text" 
									name="ngaychungtu" id="ngaychungtu" value="<%=obj.getNgayChungTu()%>"
									readonly="readonly"
									class="days"
									 /></TD>
								<TD class="plainlabel" width="15%">Phòng ban TH</TD>
								<TD class="plainlabel" width="30%"><SELECT style="width: 200px; height: 20" 
									NAME="phongbanId" id="phongbanId">
									<option value="" SELECTED></option>
										<%if(rsPhongBan != null){
											try{
												while(rsPhongBan.next()){
													if(rsPhongBan.getString("pk_seq").equals(obj.getPhongBanId())){%>
									<option value="<%=rsPhongBan.getString("pk_seq")%>"
											selected="selected"><%=rsPhongBan.getString("ten")%></option>					
													<%}else{%>
									<option value="<%=rsPhongBan.getString("pk_seq")%>"><%=rsPhongBan.getString("ten")%></option>					
													<% }
												}
											}catch(Exception e){}
											}%>
									</SELECT>
							 	</TD>

							</TR>
							 
							<TR>
								<TD class="plainlabel" width="15%">Hồ sơ kiểm nghiệm</TD>
								<TD class="plainlabel" colspan="1"><SELECT
									NAME="hosokiemnghiem" id="hosokiemnghiem"
									style="width: 200px; height: 20" onchange="filter()">
										<option value="" SELECTED></option>
										<%
											if (rsHoSo != null) { 
												try {
													while (rsHoSo.next()) {
														if (rsHoSo.getString("hosoid").equals(
																obj.getHoSoId())) {
										%>
										<option value="<%=rsHoSo.getString("hosoid")%>"
											selected="selected"><%=rsHoSo.getString("ma")%></option>
										<%
											} else {
										%>
										<option value="<%=rsHoSo.getString("hosoid")%>"><%=rsHoSo.getString("ma")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT></TD>
								<TD class="plainlabel" width="15%">Sản phẩm kiểm nghiệm</TD>
								<TD class="plainlabel" width="30%">
									<input type="hidden" 
									name="sanphamkn" id="sanphamkn" value="<%=obj.getSanphamId()%>"/>
									<SELECT
									style="width: 200px; height: 20" disabled="disabled">
										<option ></option>
										<% 
										System.out.println("-------KICH THUOC-------: " + rsTieuChuan);
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
								</SELECT>							 	</TD>

								 
							</TR>

							 
							<TR>
								<TD class="plainlabel" width="15%">Tiêu chuẩn kiểm nghiệm</TD>
								<TD class="plainlabel" width="30%">
									<input type="hidden" 
									name="tieuchuankn" id="tieuchuankn" value="<%=obj.getTieuChuanId()%>" />
									<SELECT
									style="width: 200px; height: 20" disabled="disabled">
										<option ></option>
										<%
											if (rsTieuChuan != null) {
												try {
													while (rsTieuChuan.next()) {
														if (rsTieuChuan.getString("pk_seq").equals(
																obj.getTieuChuanId())) {
										%>
										<option value="<%=rsTieuChuan.getString("pk_seq")%>"
											selected="selected"><%=rsTieuChuan.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsTieuChuan.getString("pk_seq")%>"><%=rsTieuChuan.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT>
							 	</TD> 
							 	
								<TD class="plainlabel" width="15%">Yêu cầu kỹ thuật</TD>
								<TD class="plainlabel" colspan="3"><SELECT
									NAME="yckythuat" id="yckythuat"
									style="width: 200px; height: 20" onchange="filter_yckt">
										<option value="" SELECTED></option>
										<%
											if (rsYeuCau != null) {
												try {
													while (rsYeuCau.next()) {
														if (rsYeuCau.getString("pk_seq").equals(
																obj.getYeuCauId())) {
										%>
										<option value="<%=rsYeuCau.getString("pk_seq")%>"
											selected="selected"><%=rsYeuCau.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsYeuCau.getString("pk_seq")%>"><%=rsYeuCau.getString("ten")%></option>
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
								<TD class="plainlabel" width="15%">Thời gian bắt đầu TN</TD>
								<TD class="plainlabel" width="30%"><input class="timepicker" type="text"
									name="thoigianbd" id="thoigianbd" value="<%=obj.getThoiGianBD()%>" 
									onchange="time();"/>
							 	</TD> 
							 	
							 	<TD class="plainlabel" width="15%">Thời gian kết thúc TN</TD>
								<TD class="plainlabel" width="30%"><input class="timepicker" type="text" 
									name="thoigiankt" id="thoigiankt" value="<%=obj.getThoiGianKT()%>" 
									onchange="time();"/>
							 	</TD> 
							</TR>
							
							<TR>
								<TD class="plainlabel" width="15%">Tổng thời lượng TN</TD>
								<TD class="plainlabel" width="30%"><input type="text" 
									name="tongthoiluongtn" id="tongthoiluongtn" value="<%=obj.getTongThoiLuong() %>"
									readonly="readonly" onclick="time();"/>
							 	</TD> 
							 	
							 	<TD class="plainlabel" width="15%">Đánh giá</TD>
								<TD class="plainlabel" colspan="3"><SELECT
									NAME="danhgia" id="danhgia"
									style="width: 200px; height: 20">
										<option value="" SELECTED></option>
										<%
											if (rsDanhGia != null) {
												try {
													while (rsDanhGia.next()) {
														if (rsDanhGia.getString("ma").equals(
																obj.getDanhGia())) {
										%>
										<option value="<%=rsDanhGia.getString("ma")%>"
											selected="selected"><%=rsDanhGia.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsDanhGia.getString("ma")%>"><%=rsDanhGia.getString("ten")%></option>
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
								
								<TD class="plainlabel" width="15%" valign="top">Diễn giải chứng từ</TD>
								<TD class="plainlabel" width="30%" colspan="3">
									<textarea rows="15" cols="4" name="diengiai" id="diengiai" style="width:350px; height:100px"><%=obj.getDienGiai() %></textarea>
							 	</TD> 
							</TR>
						</div>
					</div> 
				</fieldset>
			</div> 
		</div> 
</TABLE>
	
	<Table>
	<tr>
		<td>
		<div class="plainlabel flip">Phương pháp - Kết quả</div>
		</td> 
		<td>
		<div class="plainlabel flip">Hoá chất kiểm nghiệm</div>
		</td> 
	</tr>
	</Table>
			
			<div class="panel" style="width: 1280px;">
			<%for(int j=0; j<obj.getDsPhieuKiemDinhs().size(); j++){
				PhuongPhap pp = obj.getDsPhieuKiemDinhs().get(j);
			
			%>
				<TABLE>
					<tr>
						<td><div class="plainlabel flip1 <% if(pp.getDienGiai().trim().length() > 0) {%> active <% }%>" id="phuongphap_<%=j%>"><%=pp.getTenPhuongPhap() %></div></td>
						<td align="center" width="100px" class="custom">Kết quả</td>
						<td><input type="text" style="width: 500px;" class="important"
									name="ketquapp_" id="ketquapp_<%=j%>" value="<%=pp.getDienGiai() %>" onchange="background(<%=j%>);"
									/>
									<input type="hidden" style="width: 80px; text-align: center;" value="<%=pp.getTenPhuongPhap() %>"   name= "pp_ten"  readonly="readonly">
									<input type="hidden" style="width: 80px; text-align: center;" value="<%=pp.getId() %>"   name= "pp_pkseq"  readonly="readonly">
									<input type="hidden" name="pp_size" id="pp_size<%=j %>" value='<%=pp.get__sizePhuongPhap()%>'>
									</td>
					</tr>
				</TABLE>
				<div class="panel1" style="overflow-x: scroll;width: 1150px;">
					<TABLE class="tabledetail" style="" border="0" cellpadding="1"
						cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="80px">STT</th>
								<th align="center" width="100px">Ký hiệu</th> 
								<th align="center" width="100px">Tham số</th> 
								<th align="center" width="100px">Đơn vị tính</th>
								<% for(int i = 0; i < pp.get__sizePhuongPhap(); i++) {%>
									<th align="center" width="90px">Mẫu <%=i+1 %></th>
								<%} %>
								 
							</tr>
						 
							 <%
							 int k = 0;
							 for(PhieuKiemDinh pkd : pp.getDsPhieuKiemDinhs()){ %>
							<tr >
								<td align="center" width="80px">
									<input type="text" style="width: 80px; text-align: center;" value="<%=pkd.getSott() %>"   name= "sott<%=j+1 %>"  readonly="readonly">
									<input type="hidden" style="width: 80px; text-align: center;" value="<%=pkd.__loai() %>"   name= "pp_loai<%=j+1 %>"  readonly="readonly">
									<input type="hidden" style="width: 80px; text-align: center;" value="<%=pkd.isDanhGia() %>"   name= "pp_isdanhgia<%=j+1 %>"  readonly="readonly">
									<input type="hidden" style="width: 80px; text-align: center;" value="<%=pkd.isCongThuc() %>"   name= "pp_iscongthuc<%=j+1 %>"  readonly="readonly">
									
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getKyHieu() %>" <% if(pkd.isCongThuc()) {%> id="pp_kyhieu_group<%=j+1 %>" <% }%>  name= "pp_kyhieu<%=j+1 %>" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getTenThamSo() %>" <% if(pkd.isCongThuc()) {%> id="pp_thamso_group<%=j+1 %>" <% }%> name= "pp_tenthamso<%=j+1 %>" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getDonViTinh() %>"  name= "pp_donvitinh<%=j+1 %>" readonly="readonly">
								</td>
								
								<% if(!pkd.isDanhGia()) {%>
									<% for(int i = 0; i <  pkd.getThamSoMau().size(); i ++) {%>
									<td align="center" width="90px">
										<input type="text" style="width: 90px; text-align: right;" onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=j+1 %>_<%=i+1 %>"
										 readonly="readonly" style="background-color: gray;" <% }%> value="<%=format.format(Double.parseDouble(pkd.getThamSoMau().get(i))) %>"  name= "pp_mau<%=j+1 %>_<%=i+1 %>" >
									</td>
									<%} %>
									
									<% for(int i = pkd.getThamSoMau().size(); i < pp.get__sizePhuongPhap(); i++) {%>
										<td align="center" width="90px">
											<input type="text" style="width: 90px; text-align: right;"  onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=j+1 %>_<%=i+1 %>" 
											readonly="readonly" style="background-color: gray;" <% }%> value="" name= "pp_mau<%=j+1 %>_<%=i+1 %>"   >
										</td>
									<%} %>
								<%} else { %>
								
									<% for(int i = 0; i <  pkd.getThamSoMau().size(); i ++) {%>
									<td align="center" width="90px">
										<input type="checkbox" style="width: 90px; text-align: right;" <%if(Double.parseDouble(pkd.getThamSoMau().get(i)) == 1.0 ) {%> checked="checked" <%} %> onchange="danhGiaMucDo(<%=k %>, <%=j+1 %>, <%=i+1 %>);"  value="1" id= "pp_danhgia<%=j+1 %>_<%=i+1 %>_<%=k %>" name= "pp_danhgia<%=j+1 %>_<%=i+1 %>"   />
										<input type="hidden" style="width: 90px; text-align: right;" value="<%=pkd.getThamSoMau().get(i) %>" name= "pp_mau<%=j+1 %>_<%=i+1 %>" >
									</td>
									<%} %>
									<% for(int i = pkd.getThamSoMau().size() ; i < pp.get__sizePhuongPhap(); i++) {%>
										<td align="center" width="90px">
											<input type="checkbox" style="width: 90px; text-align: right;"  onchange="danhGiaMucDo(<%=k %>, <%=j+1 %>, <%=i+1 %>);"  value="1" id= "pp_danhgia<%=j+1 %>_<%=i+1 %>_<%=k %>" name= "pp_danhgia<%=j+1 %>_<%=i+1 %>"   />
											<input type="hidden" style="width: 90px; text-align: right;" value="0" name= "pp_mau<%=j+1 %>_<%=i+1 %>" >
										</td>
									<%} %>
								<%} %>
								
 
							</tr>
 							<%
 								k++;
							 } 
 							%>
 							
 							
						</TABLE>
				</div>
				<%} %>
 <%-- <TABLE>
					<tr>
						<td><div class="plainlabel flip1">Phương pháp B</div></td>
						<td align="center" width="100px" class="custom">Kết quả</td>
						<td><input type="text" style="width: 500px;"
									name="ketquapp2" id="ketquapp2" value=""
									/></td>
					</tr>
				</TABLE>
								<div class="panel1" style="overflow-x: scroll;width: 1150px;">
					<TABLE class="tabledetail" style="" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="80px">STT</th>
								<th align="center" width="100px">Ký hiệu</th> 
								<th align="center" width="100px">Tham số</th> 
								<th align="center" width="100px">Đơn vị tính</th>
								<% for(int i = 0; i < obj.__sizePhuongPhap(); i++) {%>
									<th align="center" width="90px">Mẫu <%=i+1 %></th>
								<%} %>
								 
							</tr>
						 
							 <%
							 int k1 = 0;
							 for(PhieuKiemDinh pkd : obj.getDsPhieuKiemDinhs()){ %>
							<tr >
								<td align="center" width="80px">
									<input type="text" style="width: 80px; text-align: center;" value="<%=pkd.getSott() %>"   name= "sott"  readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getKyHieu() %>" <% if(pkd.isCongThuc()) {%> id="pp_kyhieu_group<%=k1+1 %>" <% }%>  name= "pp_kyhieu" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getTenThamSo() %>" <% if(pkd.isCongThuc()) {%> id="pp_thamso_group<%=k1++  +  1 %>" <% }%> name= "pp_tenthamso" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getDonViTinh() %>"  name= "pp_donvitinh" readonly="readonly">
								</td>
								<% for(int i = 0; i <  pkd.getThamSoMau().size(); i ++) {%>
									<td align="center" width="90px">
										<input type="text" style="width: 90px; text-align: right;" onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=i+1 %>" readonly="readonly" style="background-color: gray;" <% }%> value="<%=format.format(Double.parseDouble(pkd.getThamSoMau().get(i))) %>"  name= "pp_mau<%=i+1 %>" >
									</td>
								<%} %>
								
								<% for(int i = pkd.getThamSoMau().size(); i < obj.__sizePhuongPhap(); i++) {%>
									<td align="center" width="90px">
										<input type="text" style="width: 90px; text-align: right;"  onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=i+1 %>" readonly="readonly" style="background-color: gray;" <% }%> value="" name= "pp_mau<%=i+1 %>"   >
									</td>
								<%} %>
 
							</tr>
 							<%} 
 							%>
 							
 							
						</TABLE>
				</div>
				
				<TABLE>
					<tr>
						<td><div class="plainlabel flip1">Phương pháp C</div></td>
						<td align="center" width="100px" class="custom">Kết quả</td>
						<td><input type="text" style="width: 500px;"
									name="ketquapp3" id="ketquapp3" value=""
									/></td>
					</tr>
				</TABLE>
				<div class="panel1" style="overflow-x: scroll;width: 1150px;">
					<TABLE class="tabledetail" style="" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="20px">STT</th>
								<th align="center" width="100px">Ký hiệu</th> 
								<th align="center" width="100px">Tham số</th> 
								<th align="center" width="100px">Đơn vị tính</th>
								<% for(int i = 0; i < obj.__sizePhuongPhap(); i++) {%>
									<th align="center" width="90px">Mẫu <%=i+1 %></th>
								<%} %>
								 
							</tr>
						 
							 <%
							 int k2 = 0;
							 for(PhieuKiemDinh pkd : obj.getDsPhieuKiemDinhs()){ %>
							<tr >
								<td align="center" width="80px">
									<input type="text" style="width: 80px; text-align: center;" value="<%=pkd.getSott() %>"   name= "sott"  readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getKyHieu() %>" <% if(pkd.isCongThuc()) {%> id="pp_kyhieu_group<%=k2+1 %>" <% }%>  name= "pp_kyhieu" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getTenThamSo() %>" <% if(pkd.isCongThuc()) {%> id="pp_thamso_group<%=k2++  +  1 %>" <% }%> name= "pp_tenthamso" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getDonViTinh() %>"  name= "pp_donvitinh" readonly="readonly">
								</td>
								<% for(int i = 0; i <  pkd.getThamSoMau().size(); i ++) {%>
									<td align="center" width="90px">
										<input type="text" style="width: 90px; text-align: right;" onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=i+1 %>" readonly="readonly" style="background-color: gray;" <% }%> value="<%=format.format(Double.parseDouble(pkd.getThamSoMau().get(i))) %>"  name= "pp_mau<%=i+1 %>" >
									</td>
								<%} %>
								
								<% for(int i = pkd.getThamSoMau().size(); i < obj.__sizePhuongPhap(); i++) {%>
									<td align="center" width="90px">
										<input type="text" style="width: 90px; text-align: right;"  onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=i+1 %>" readonly="readonly" style="background-color: gray;" <% }%> value="" name= "pp_mau<%=i+1 %>"   >
									</td>
								<%} %>
 
							</tr>
 							<%} 
 							%>
 							
 							
						</TABLE>
				</div> 
 --%>				
				</div>
				<%-- <TABLE class='customers' class="tabledetail" style="" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="80px">STT</th>
								<th align="center" width="100px">Ký hiệu</th> 
								<th align="center" width="100px">Tham số</th> 
								<th align="center" width="100px">Đơn vị tính</th>
								<% for(int i = 0; i < obj.__sizePhuongPhap(); i++) {%>
									<th align="center" width="90px">Mẫu <%=i+1 %></th>
								<%} %>
								 
							</tr>
						 
							 <%
							 int k = 0;
							 for(PhieuKiemDinh pkd : obj.getDsPhieuKiemDinhs()){ %>
							<tr >
								<td align="center" width="80px">
									<input type="text" style="width: 80px; text-align: center;" value="<%=pkd.getSott() %>"   name= "sott"  readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getKyHieu() %>" <% if(pkd.isCongThuc()) {%> id="pp_kyhieu_group<%=k+1 %>" <% }%>  name= "pp_kyhieu" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getTenThamSo() %>" <% if(pkd.isCongThuc()) {%> id="pp_thamso_group<%=k++  +  1 %>" <% }%> name= "pp_tenthamso" readonly="readonly">
								</td>
								<td align="center" width="100px">
									<input type="text" style="width: 100px; text-align: center;" value="<%=pkd.getDonViTinh() %>"  name= "pp_donvitinh" readonly="readonly">
								</td>
								<% for(int i = 0; i <  pkd.getThamSoMau().size(); i ++) {%>
									<td align="center" width="90px">
										<input type="text" style="width: 90px; text-align: right;" onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=i+1 %>" readonly="readonly" style="background-color: gray;" <% }%> value="<%=format.format(Double.parseDouble(pkd.getThamSoMau().get(i))) %>"  name= "pp_mau<%=i+1 %>" >
									</td>
								<%} %>
								
								<% for(int i = pkd.getThamSoMau().size(); i < obj.__sizePhuongPhap(); i++) {%>
									<td align="center" width="90px">
										<input type="text" style="width: 90px; text-align: right;"  onchange="DinhDangNSoLe(this, 3)" <% if(pkd.isCongThuc()) {%> id="pp_mau_group<%=i+1 %>" readonly="readonly" style="background-color: gray;" <% }%> value="" name= "pp_mau<%=i+1 %>"   >
									</td>
								<%} %>
 
							</tr>
 							<%} 
 							%>
 							
 							
						</TABLE>
 --%>				
			<div class="panel">
				<TABLE class='customers' class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="1%">STT</th>
								<th align="center" width="10%">Hóa chất</th> 
								<th align="center" width="1%">Số lượng định mức</th>
								<th align="center" width="1%">Số lượng thực tế</th>
								<th align="center" width="3%">Mã số</th>
								<th align="center" width="3%">Cách pha</th>  
							</tr>
						 
							 <%for(PhieuKiemDinh pkd : obj.getDsHoaChatKiemNghiem()){  
							 %>
							<tr >
								<td align="center" width="1%">
									<input type="text" style="width: 100%; text-align: center;" value="<%=pkd.getStt() %>" name= "stt" readonly="readonly">
								</td> 
								<td align="center" width="10%">
									<input type="text" style="width: 100%;" value="<%=pkd.getHoaChat() %>" name= "hoachat" readonly="readonly">
								</td> 
								<td align="center" width="1%">
									<input type="text" style="width: 100%; text-align:right" value="<%=pkd.getSoLuongDinhMuc() %>" name= "soluongdm" readonly="readonly">
								</td>
								<td align="center" width="1%">
									<input type="text" style="width: 100%; text-align:right" value="<%=pkd.getSoLuongThucTe() %>" name= "soluongtt" id="soluongtt">
								</td>
								<td align="center" width="3%">
									<input type="text" style="width: 100%;" value="<%=pkd.getMaSo() %>" name= "maso" >
								</td>
								<td align="center" width="3%">
									<input type="text" style="width: 100%;" value="<%=pkd.getCachPha()%>" name= "cachpha" >
								</td>
								<%-- <td align="center" width="8%">
									<input type="text" style="width: 100%;" value="<%=pkd.getGhiChu() %>" name= "maso" >
								</td>
								<td align="center" width="8%">
									<input type="text" style="width: 100%;" value="<%=pkd.getGhiChu() %>" name= "cachpha" >
								</td> --%> 
							</tr> 
 							<%} 
 							%>
						</TABLE>
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