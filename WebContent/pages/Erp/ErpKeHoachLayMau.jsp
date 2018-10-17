<%@page import="geso.traphaco.erp.beans.kehoachlaymau.IKeHoachLayMauList"%>
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
	IKeHoachLayMauList obj = (IKeHoachLayMauList) session
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
	int[] quyen = new int[5];
	if (!util.check(userId, userTen, sum)) {
		response.sendRedirect("/TraphacoHYERP/index.jsp");
		return;
	} else { 
		quyen = util.Getquyen("ErpKeHoachLayMauSvl", "", userId);
	}

	 
	ResultSet rsPhongBan = obj.getRsPhongBan(); 
	ResultSet rsSanPham = obj.getRsSanPham(); 
	ResultSet rsTrangThai = obj.getRsTrangThai();
	ResultSet rsKeHoach = obj.getRsKeHoach();
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
	
	function Chot(id){
		 document.forms['form'].action.value='chot';
		 document.forms['form'].id.value=id;
	     document.forms['form'].submit();
	     
	}
	
	function Bochot(id){
		 document.forms['form'].action.value='bochot';
		 document.forms['form'].id.value=id;
	     document.forms['form'].submit();
	     
	}
	
	function Xoa(id){
		 document.forms['form'].action.value='xoa';
		 document.forms['form'].id.value=id;
	     document.forms['form'].submit();
	     
	}
 	
	function clearform() {
		document.form.tungay.value = "";
		document.form.denngay.value = "";
		document.form.loaikiemdinh.value = "";
		document.form.loaimaukn.value = "";
		document.form.soidphieu.value = "";
		document.form.sophieukd.value = "";
		document.form.sanphamkiemdinh.value = "";
		document.form.quytrinh.value = "";
		document.forms["form"].submit();
	}
	
	function TaoMoi() {
		document.forms["form"].action.value = "new";
		document.forms["form"].submit();
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

</script>
</head>
<body>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="form" method="post"
		action="../../ErpKeHoachLayMauSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="id" value=''> 
		<input
			type="hidden" name="action" value='1'>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Hồ sơ kiểm nghiệm &gt;
					Chức năng &gt; Kế hoạch lấy mẫu, đánh giá</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				 
				</span>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" readonly="readonly"
						style="width: 100%"> </textarea>

				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset id="hoadon">
					<legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" width="15%" >Ngày kế hoạch</TD>
								<TD class="plainlabel" width="30%"><input type="text" 
									name="ngaykh" id="ngaykh" value="<%=obj.getNgayKeHoach() %>"
									readonly="readonly"
									class="days"/></TD> 
							</TR>
							
							<TR>
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
								<TD class="plainlabel" width="15%">Trạng thái</TD>
								<TD class="plainlabel" colspan="3"><SELECT
									NAME="trangthai" id="trangthai"
									style="width: 200px; height: 20" onchange="filter()">
										<option value="" SELECTED></option>
										<%
											if (rsTrangThai != null) {
												try {
													while (rsTrangThai.next()) {
														if (rsTrangThai.getString("pk_seq").equals(
																obj.getTrangthai())) {
										%>
										<option value="<%=rsTrangThai.getString("pk_seq")%>"
											selected="selected"><%=rsTrangThai.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=rsTrangThai.getString("pk_seq")%>"><%=rsTrangThai.getString("ten")%></option>
										<%
											}
													}

												} catch (SQLException ex) {
												}
											}
										%>
								</SELECT></TD>
							</TR>
							 
						</TABLE>

						<hr>
					</div>

					<!-- LIST SẢN PHẨM -->
					<TABLE width="100%" cellpadding="0" cellspacing="1">
						<TR>
							<TD align="left">
								<FIELDSET>
								
									<LEGEND class="legendtitle">
										&nbsp;Kế hoạch lấy mẫu, đánh giá&nbsp;&nbsp;&nbsp;  
											<%if(quyen[0]!=0){ %>
										 <a class="button3"
											href="javascript:TaoMoi()"> 
											<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
										
										<%} %>
									</LEGEND> 
					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="3%">Số ID</th>
								<th align="center" width="5%">Ngày kế hoạch</th>
								<th align="center" width="10%">Bộ phận thực hiện</th>
								<th align="center" width="8%">Sản phẩm</th>
								<th align="center" width="5%">Người tạo</th>
								<th align="center" width="5%">Ngày tạo</th> 
								<th align="center" width="3%">Trạng thái</th> 
								<th align="center" width="5%">Tác vụ</th> 
							</tr>
							
							<% if(rsKeHoach != null){
								try{
									while(rsKeHoach.next()){
									if(rsKeHoach.getString("trangthai").equals("0")){%>
							<tr >
							<%}else if(rsKeHoach.getString("trangthai").equals("1")){ %>
							<tr >
							<%}else if(rsKeHoach.getString("trangthai").equals("2")){ %>
							<tr style="color:red">
							<%} %>
								<td align="center"  >
									 <%=rsKeHoach.getString("pk_seq") %> 
								</td>
								<td align="center">
									<%=rsKeHoach.getString("ngaykehoach") %>
								</td>
								<td align="left" >
									 <%=rsKeHoach.getString("bophanthuchien") %> 
								</td>
								<td align="left" >
									 <%=rsKeHoach.getString("sanpham") %> 
								</td>
								<td align="center" >
									 <%=rsKeHoach.getString("nguoitao") %> 
								</td>

								<td align="center" >
									 <%=rsKeHoach.getString("ngaytao") %> 
								</td>
								<%if(rsKeHoach.getString("trangthai").equals("0")){ %>
								<td align="center" >
									 Chưa chốt 
								</td>
								<TD align="center" width="5%">
									<%if(quyen[2]!=0){ %>
									<A
										href="../../ErpKeHoachLayMauUpdateSvl?task=capnhat&id=<%=rsKeHoach.getString("pk_seq")%>">
											<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat"
											width="20" height="20" longdesc="Cap nhat" border=0>
									</A>
									<%} %>
									
									<%if(quyen[3]!=0){ %>
			                    		<A
										href="../../ErpKeHoachLayMauSvl?task=hienthi&id=<%=rsKeHoach.getString("pk_seq")%> ">
											<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi"
											width="20" height="20" longdesc="Hien thi" border=0>
									</A>
			                    	<% } %>	
									
									<%if(quyen[4]!=0){ %>
									<A
										href = "javascript:Chot('<%= rsKeHoach.getString("pk_seq") %>');">
											<img title="Chốt" src="../images/Chot.png" alt="Chot"
											width="20" height="20" longdesc="Chot" border=0>
									</A>
									<%} %>
									
									<%if(quyen[1]!=0){ %>
									<A
										href="javascript:Xoa('<%= rsKeHoach.getString("pk_seq") %>');">
											<img title="Xóa" src="../images/Delete20.png" alt="Xoa"
											width="20" height="20" longdesc="Xoa" border=0>
									</A>
									<%} %> 
								</TD>
								
								<%}else if(rsKeHoach.getString("trangthai").equals("1")){%>
								<td align="center" > 
									 Đã chốt 
								</td>
								<td align="center" width="5%">
									<%if(quyen[3]!=0){ %>
			                    		<A
										href="../../ErpKeHoachLayMauSvl?task=hienthi&id=<%=rsKeHoach.getString("pk_seq")%> ">
											<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi"
											width="20" height="20" longdesc="Hien thi" border=0>
									</A>
									<% } %>	
									<%if(quyen[5]!=0){ %>
									<A
										href = "javascript:Bochot('<%= rsKeHoach.getString("pk_seq") %>');">
											<img title="Bỏ chốt" src="../images/unChot.png" alt="Bochot"
											width="20" height="20" longdesc="Bochot" border=0>
									</A>
									<%} %> 
								</td>
								<%}else if(rsKeHoach.getString("trangthai").equals("2")){%>
								<td align="center" > 
									 Đã xóa 
								</td>
								<td align="center" width="5%">
									<%if(quyen[3]!=0){ %>
			                    		<A
										href="../../ErpKeHoachLayMauSvl?task=hienthi&id=<%=rsKeHoach.getString("pk_seq")%> ">
											<img title="Hiển thị" src="../images/Display20.png" alt="Hien thi"
											width="20" height="20" longdesc="Hien thi" border=0>
									</A>
									<% } %>
								</td>
								<%} %>
							</tr>

							<%}
									}catch(Exception e){
										e.printStackTrace();
									}
							}%>

						</TABLE>
					</div>
					</TD>
					</TR>
					</TABLE>

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