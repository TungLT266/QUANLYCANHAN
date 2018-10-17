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
<%@ page import="geso.traphaco.erp.beans.yclaymaukiemnghiem.imp.*"%>
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
	IYCLayMauKiemNghiemList obj = (YCLayMauKiemNghiemList) session.getAttribute("obj");
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
//	ResultSet rsYCLMKD = obj.getRsYCLPKN();
	DecimalFormat format = new DecimalFormat("###,###,###.##");
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title><%=getServletContext().getInitParameter("TITLENAME")%></title>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">

<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<LINK rel="stylesheet" type="text/css" href="../css/style.css" />


<script language="javascript" src="../scripts/datepicker.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript"
	src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>


<script type="text/javascript" src="../scripts/ajax.js"></script>
<!-- <script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script> -->
    <SCRIPT language="javascript" type="text/javascript">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>


	<script type="text/javascript">
			$(document).ready(function() {		
					$( ".days" ).datepicker({			    
							changeMonth: true,
							changeYear: true				
					});            
		</script>
	
	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<script type="text/javascript">
	        $(document).ready(function(){
	            $(".button").hover(function(){
	                $(".button img")
	                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
	                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
	                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
	            });
	        }); 
			$(document).ready(function(){
	            $(".button2").hover(function(){
	                $(".button2 img")
	                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
	                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
	                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
	            });
	        }); 
			$(document).ready(function(){
	            $(".button3").hover(function(){
	                $(".button3 img")
	                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
	                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
	                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
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
	
	function search() {
		document.forms["form"].action.value = "search";
		document.forms["form"].submit();
	}
	
	function thaydoiLoaiKD(){
		 document.forms['form'].action.value='thaydoiLoaiKD';
	     document.forms['form'].submit();
	     
	}
		
	 function submitform()
	 {   
	    document.forms["form"].submit();
	 }
	 
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	
</script>
</head>
<body>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="form" method="post"
		action="../../YCLayMauKiemNghiemSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value='1'>
				<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
			<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>"> 
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Dữ liệu nền &gt; Hồ sơ kiểm nghiệm &gt;
					Yêu cầu lấy mẫu kiểm nghiệm &gt; Tạo mới</div>
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
								<TD class="plainlabel" width="15%">Từ ngày</TD>  
								<TD class="plainlabel" width="30%" colspan="3"><input type="text" 
									name="tungay" id="tungay" value="<%=obj.getTungay() %>"
									readonly="readonly"
									class="days" onchange="submitform()"
									 /></TD>
								<TD class="plainlabel" width="15%">Đến ngày</TD>
								<TD class="plainlabel" width="15%"><input type="text" name="denngay" id="denngay" value="<%=obj.getDenngay() %>" readonly="readonly" onchange="submitform()"	class="days" /></TD> 
									 <TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
							</TR>
 
			 				<TR>
								<TD class="plainlabel" width="15%">Loại kiểm định</TD>
								<TD class="plainlabel" width="30%" colspan="3" onchange="submitform()"
								<% if(obj.getLoaiKiemDinh().equals("1")) { %> colspan="3"  <%} %>><SELECT NAME="loaikiemdinh"
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
								
								<TD class="plainlabel" width="15%">Loại mẫu KN</TD>
								<TD class="plainlabel">
								<SELECT NAME="loaimaukn" onchange="submitform()"
									id="loaimaukn" style="width: 200px; height: 20">
										<option value="" SELECTED></option>
										<%
											if (rsMauKiemNghiem != null) {
												try {
													while (rsMauKiemNghiem.next()) {
														if (rsMauKiemNghiem.getString("pk_seq").equals(	obj.getMauKiemNghiemId())) {
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
								<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
								 
							</TR>
							
							<TR>
								<TD class="plainlabel" width="15%">Số ID phiếu</TD>
								<TD class="plainlabel" width="30%" colspan="3"><input type="text" onchange="submitform()"
									name="soidphieu" id="soidphieu" value="<%=obj.getSophieu()%>"></TD>
									
								<TD class="plainlabel" width="15%">Số phiếu kiểm định</TD> 
								<TD class="plainlabel" width="15%"><input type="text" onchange="submitform()"
									name="sophieukd" id="sophieukd" value="<%=obj.getSophieukiemdinh()%>"></TD>
									<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
							</TR>

							<TR>
								<TD class="plainlabel" width="15%">Sản phẩm kiểm định</TD>
								<TD class="plainlabel" width="30%" colspan="3" onchange="submitform()"><SELECT
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
								<TD class="plainlabel" width="15%">Quy trình lấy mẫu số</TD>
								<TD class="plainlabel" width="15%"><input type="text" onchange="submitform()"
									name="quytrinh" id="quytrinh" value="<%=obj.getQuytrinhmauso()%>"></TD>
									<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
							</TR> 
							
							<TR>
								<TD class="plainlabel" colspan="3">  
								<a class="button" href="javascript:search()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm 
                                </a> 
                                
                                <a class="button2"
									href="javascript:clearform()"> <img
										style="top: -4px;" src="../images/button.png" alt="">Nhập
										lại
								</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</TD>
								<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
								<TD class="plainlabel"></TD>
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
										&nbsp;Yêu cầu lấy mẫu kiểm nghiệm&nbsp;&nbsp;&nbsp;  
											<%if(quyen[0]!=0){ %>
										 <a class="button3"
											href="javascript:TaoMoi()"> 
											<img style="top: -4px;" src="../images/New.png" alt="">Tạo mới</a>
										
										<%} %>
									</LEGEND> 
									
					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="customers" class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<tr class="tbheader">
								<th align="center" width="8%">Số ID</th>
								<th align="center" width="10%">Số phiếu kiểm định</th>
								<th align="center" width="8%">Ngày chứng từ</th>
								<th align="center" width="10%">Loại kiểm định</th>
								<th align="center" width="10%">Sản phẩm</th> 
								<th align="center" width="8%">Quy trình lấy mẫu số</th>
								<th align="center" width="8%">Trạng thái</th> 
								<th align="center" width="8%">Tác vụ</th> 
							</tr>
							
							
						<%
						int m = 0;
						for(PhieuKiemDinh pkd : obj.getDsPhieuKiemDinhs()){ 
						
							if((m % 2 ) == 0 & pkd.getTrangthai().equals("2")) {%>
                     		<TR class="tbdarkrow" style="color: red;">
                     	<%}else if((m % 2 ) != 0 & pkd.getTrangthai().equals("2")) { %>
                     		<TR class="tblightrow" style="color: red;">
                     	<%}else if((m % 2 ) == 0) { %>
                     		<TR class="tbdarkrow" >
                     	<%}else{ %>
                          	<TR class="tblightrow">
                        <%}
						%>
							
								<td align="center" width="8%">
									<%= pkd.getId() %>
								</td>
								<td align="center" width="10%">
									<%= pkd.getSophieukiemdinh() %>
								</td>
								<td align="center" width="5%">
									<%= pkd.getNgaychungtu() %>
								</td>
								
								<td align="center" width="15%">
									<%= pkd.getLoaikiemdinh() %>
								</td>
								
								<td align="center" width="15%">
									<%= pkd.getTenSp() %>
								</td>
								<td align="center" width="7%">
									<%= pkd.getQuytrinhlaymau() %>
								</td>

								<%String []trangthai={"Chưa chốt","Đã chốt","Đã xóa"};
								  String tt=pkd.getTrangthai();%>
								<td align="center" width="7%">
									<%= trangthai[Integer.parseInt(tt)] %>
								</td>
													
								<TD align="center" width="10%">
								
									<%if(tt.equals("0")){
									if(quyen[2]!=0){ %>
									<A
										href="../../YCLayMauKiemNghiemUpdateSvl?userId=<%=userId%>&capnhat=<%=pkd.getId()%>">
											<img title="Cập nhật" src="../images/Edit20.png" alt="Cap nhat"
											width="20" height="20" longdesc="Cap nhat" border=0>
									</A>
									<%} %>
									
									<%if(quyen[4]!=0){ %>
									<A id='chotphieu<%=pkd.getId()%>'
						       			href=""><img title="Chốt" src="../images/Chot.png" alt="Chot"
											width="20" height="20" longdesc="Chot" border=0  
											onclick="if(!confirm('Bạn có chắc chốt phiếu này?')) {return false ;}else{ 
											processing('<%="chotphieu"+  pkd.getId()%>' , '../../YCLayMauKiemNghiemSvl?userId=<%=userId%>&chotphieu=<%=pkd.getId()%>');}"  >
									</A>
									<%} %>
									
									<%if(quyen[1]!=0){ %>
									<A id='xoaphieu<%=pkd.getId()%>'
						       			href="">
											<img title="Xóa" src="../images/Delete20.png" alt="Xoa"
											width="20" height="20" longdesc="Xoa" border=0
											onclick="if(!confirm('Bạn có chắc xoa phiếu này?')) {return false ;}else{ 
											processing('<%="xoaphieu"+  pkd.getId()%>' , '../../YCLayMauKiemNghiemSvl?userId=<%=userId%>&xoaphieu=<%=pkd.getId()%>');}"  >
											
									</A>
									<%} 
									} else if(tt.equals("1")){
									if(quyen[2]!=0){ %>
									<A
										href="../../YCLayMauKiemNghiemUpdateSvl?userId=<%=userId%>&display=<%=pkd.getId()%>">
											<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
									</A>
									<%} %>
									<%if(quyen[5]!=0){ %>
									<A id='bochotphieu<%=pkd.getId()%>'
						       			href=""><img title="Bỏ chốt" src="../images/unChot.png" alt="Bochot"
											width="20" height="20" longdesc="Bochot" border=0  
											onclick="if(!confirm('Bạn có chắc bỏ chốt phiếu này?')) {return false ;}else{ 
											processing('<%="bochotphieu"+  pkd.getId()%>' , '../../YCLayMauKiemNghiemSvl?userId=<%=userId%>&bochotphieu=<%=pkd.getId()%>');}"  >
									</A>
									<%} %>
									<%} else if(tt.equals("2")){
									if(quyen[2]!=0){ %>
									<A
										href="../../YCLayMauKiemNghiemUpdateSvl?userId=<%=userId%>&display=<%=pkd.getId()%>">
											<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
									</A>
									<%} %>
									<%} %>
								 
								</TD>
								</TR>
 							<% m++;
							 }%> 

				<%--  <tr class="tbfooter">
									<TD align="center" valign="middle" colspan="8" class="tbfooter">
										<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
										src="../images/first.gif" style="cursor: pointer;"
										onclick="View('form', 1, 'view')"> &nbsp; <%}else {%>
										<img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%} %>
										<% if(obj.getNxtApprSplitting() > 1){ %> <img alt="Trang Truoc"
										src="../images/prev.gif" style="cursor: pointer;"
										onclick="View('form', eval(form.nxtApprSplitting.value) -1, 'view')">
										&nbsp; <%}else{ %> <img alt="Trang Truoc"
										src="../images/prev_d.gif"> &nbsp; <%} %> <%
														int[] listPage = obj.getNextSplittings();
														for(int i = 0; i < listPage.length; i++){
													%> <% 
													
													System.out.println("Current page:" + obj.getNxtApprSplitting());
													System.out.println("List page:" + listPage[i]);
													
													if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
										style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
										<%}else{ %> <a
										href="javascript:View('form', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
										<%} %> <input type="hidden" name="list"
										value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
										&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
										style="cursor: pointer;"
										onclick="View('form', eval(form.nxtApprSplitting.value) +1, 'view')">
										&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
										src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
										<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
										<img alt="Trang Cuoi" src="../images/last.gif"
										style="cursor: pointer;"
										onclick="View('form', -1, 'view')"> &nbsp; <%} %>
									</TD>
								</tr>   --%>
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
	
</BODY>
</html>