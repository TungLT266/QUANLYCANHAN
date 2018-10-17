
<%@page import="geso.traphaco.erp.beans.kehoachlaymau.imp.PhieuKiemDinh"%>
<%@page import="geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMau"%>
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
	IKeHoachLayMau obj = (IKeHoachLayMau) session
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
		quyen = util.Getquyen("ErpKeHoachLayMauSvl", "", userId);
	}

	 
	ResultSet rsPhongBan = obj.getRsPhongBan();
	 
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
	     document.forms['form'].submit();
	     
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
		action="../../ErpKeHoachLayMauUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="id" value='<%=obj.getId()%>'>
		 <input
			type="hidden" name="action" value='1'>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Dữ liệu nền &gt; Hồ sơ kiểm nghiệm &gt;
					Kế hoạch lấy mẫu, đánh giá &gt; Tạo mới</div>
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
					<legend class="legendtitle"> Kế hoạch lấy mẫu</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
				
							<TR>
								<TD class="plainlabel" width="15%">Ngày kế hoạch</TD>
								<TD class="plainlabel" width="30%"><input type="text" 
									name="ngaykh" id="ngaykh" value="<%=obj.getNgayKeHoach() %>"
									readonly="readonly"
									class="days"
									 /></TD>
								<TD class="plainlabel" width="15%">Bộ phận thực hiện</TD>
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

							 
							<TR>
								  
								<TD class="plainlabel" width="15%">Diễn giải/Ghi chú</TD>
								<TD class="plainlabel" colspan="3" width="60%" >
									<textarea rows="5" cols="5" name="diengiai" id="diengiai" style="width:730px"><%=obj.getDienGiai() %></textarea>
	 								
								</TD>
 
							</TR>
							 
						</TABLE>

						<hr>
					</div> 
					
					
					<fieldset id="chitiet">
					<legend class="legendtitle"> Kế hoạch chi tiết</legend>
					<!-- LIST SẢN PHẨM -->
					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="1%">STT</th>
								<th align="center" width="3%">Ngày lấy mẫu/đánh giá</th> 
								<th align="center" width="3%">Ngày cần đánh giá lại</th>
								<th align="center" width="8%">Ghi chú</th>
								 
							</tr>
						 
							 <%for(PhieuKiemDinh pkd : obj.getDsPhieuKiemDinhs()){
								 System.out.println("SLLLLLLLLLLLLL: " + obj.getDsPhieuKiemDinhs().size());%>
							<tr >
								<td align="center" width="1%">
									<input type="text" style="width: 100%; text-align: center;" value="<%=pkd.getSott() %>" name= "sott" readonly="readonly">
								</td>
								<td align="center" width="3%">
									<input type="text" style="width: 100%;" value="<%=pkd.getNgayDanhGia() %>" name= "ngaydanhgia" class="days" readonly="readonly">
								</td>
								<td align="center" width="3%" style="display:none;">
									<input type="text" style="width: 100%;" value="" name= "ngaythucte" class="days" readonly="readonly">
								</td>
								<td align="center" width="5%">
									<input type="text" style="width: 100%;" value="<%=pkd.getNgayDanhGiaLai() %>"  name= "ngaydanhgialai" class="days" readonly="readonly">
								</td>
								<td align="center" width="8%">
									<input type="text" style="width: 100%;" value="<%=pkd.getGhiChu() %>" name= "ghichu" >
								</td> 
							</tr>
 							<%} 
 							%>
 							 
 							 <%for(int i=obj.getDsPhieuKiemDinhs().size(); i <= 10 - obj.getDsPhieuKiemDinhs().size(); i++){%>
								<tr >
								<td align="center" width="1%">
									<input type="text" style="width: 100%; text-align: center;" value="<%=i+1 %>" name= "sott" readonly="readonly">
								</td>
								<td align="center" width="3%">
									<input type="text" style="width: 100%;" value="" name= "ngaydanhgia" class="days" readonly="readonly">
								</td>
								<td align="center" width="3%" style="display:none;">
									<input type="text" style="width: 100%;" value="" name= "ngaythucte" class="days" readonly="readonly">
								</td>
								<td align="center" width="3%">
									<input type="text" style="width: 100%;" value="" name= "ngaydanhgialai" class="days" readonly="readonly">
								</td>
								<td align="center" width="8%">
									<input type="text" style="width: 100%;" value="" name= "ghichu" >
								</td>
 
							</tr>
							<%} %>
						</TABLE>
					</div>

				</fieldset>
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