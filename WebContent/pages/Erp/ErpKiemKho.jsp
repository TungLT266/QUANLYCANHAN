<%@page import="geso.traphaco.erp.beans.kiemkho.imp.ErpKiemKhoList"%>
<%@page import="geso.traphaco.erp.beans.kiemkho.IErpKiemKhoList"%>
<%@page import="geso.traphaco.erp.beans.erp_dieuchinhsolokhott.IErp_DieuChinhSoLoKho_List"%>
<%@page import="java.sql.ResultSet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<%@ page  import = "geso.traphaco.center.util.*" %>
<%
	IErpKiemKhoList obj = (ErpKiemKhoList) session.getAttribute("obj");
	ResultSet rsKho = obj.getRsKho();
	ResultSet rsKhu=obj.getRsKhu();
	ResultSet rsdctktt = obj.getRsDieuChinhTonKho();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Diageo/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpKiemKhoSvl","",userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title>Diageo - Project</title>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
<script language="javascript" src="../scripts/datepicker.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
</script>

<script type="text/javascript" src="..scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<script type="text/javascript">
	$(document).ready(function() {
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
	$(document).ready(function() {
		$(".button2").hover(function() {
			$(".button2 img").animate({
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
	$(document).ready(function() {
		$(".button3").hover(function() {
			$(".button3 img").animate({
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
	
	function newform() {
		
		document.forms["StockForm"].action.value = "AddNew";
		document.forms["StockForm"].submit();
	}
	function Search() {
		document.forms["StockForm"].action.value = "Search";
		document.forms["StockForm"].submit();
	}
	
	function ClearSearch() {
		
		document.forms["StockForm"].khott_fk.value="";
		document.forms["StockForm"].tuthang.value="0";
		document.forms["StockForm"].tunam.value="0";
		document.forms["StockForm"].denthang.value="0";
		document.forms["StockForm"].dennam.value="0";
		
		document.forms["StockForm"].trangthai.value="";
		
		
		
		document.forms["StockForm"].action.value = "Search";
		document.forms["StockForm"].submit();
	}
	
	
	function SelectStock() {
		document.forms["StockForm"].action.value = "SelectStock";
		document.forms["StockForm"].submit();
	}
	 function thongbao()
	 {
		 var tt = document.forms["StockForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["StockForm"].msg.value);
	 }
	 
	 
</script>
</head>
<body>
	<form name="StockForm" action="../../ErpKiemKhoSvl" method="post">
		<input type="hidden" name="action" value="1"> 
		<input type="hidden" name="userId" value="<%=userId%>">
		<input type="hidden" name="msg" value="<%=obj.getMSG()%>">
		<script language="javascript" type="text/javascript">
    thongbao();
    </script>

		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left" class="tbnavigation">Quản lý cung ứng > Quản lý tồn kho > kiểm kho</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%=userTen%>
				</div>
			</div>
			<div id="cotent" style="width: 100%; float: none">
				<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset style="margin-top: 5px">
						<legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
						<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">
							
						
							<TR>
							<TD class="plainlabel">Từ tháng</TD>
									<TD class="plainlabel">
									 <select name="tuthang"  style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
  										int k=1;
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getFromMonth().equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="tunam"  style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
									  
  										int n;
  										for(n=2008;n<2015;n++){
  										if(obj.getFromYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select>
									</TD>
							</TR>
							<tr>
							<TD class="plainlabel">Tới tháng</TD>
									<TD class="plainlabel">
									 <select name="denthang" style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
  									
  									for(k=1;k<=12;k++){
  										String chuoi=k<10?"0"+k:""+k;
  									  if(obj.getToMonth().equals(chuoi)){
  									%>
									<option value=<%=k%> selected="selected" > <%=k%></option> 
									<%
 										}else{
 									%>
									<option value=<%=k%> ><%=k%></option> 
									<%
 										}
 									  }
 									%>
									</select>
									<select name="dennam"  style="width :50px" onchange="loctien()">
									<option value=0> </option>  
									<%
									  
  									
  										for(n=2008;n<2015;n++){
  										if(obj.getToYear().equals(""+n)){
  									%>
									<option value=<%=n%> selected="selected" > <%=n%></option> 
									<%
 										}else{
 									%>
									<option value=<%=n%> ><%=n%></option> 
									<%
 										}
 									 }
 									%>
 									</select>
									</TD>
									
							</tr>
							
							<TR >
								<TD class="plainlabel" width="15%">Kho </TD>
								<TD class="plainlabel"><select name="khott_fk" id="khott_fk" onchange="Search();">
										<option value=""> </option>  
										<%while (rsKho.next()) {
				if (rsKho.getString("PK_SEQ").equals(obj.getKhoTT_FK())) {%>
										<option value="<%=rsKho.getString("PK_SEQ")%>" selected="selected"><%=rsKho.getString("Ten")%></option>
										<%} else {%>
										<option value="<%=rsKho.getString("PK_SEQ")%>"><%=rsKho.getString("Ten")%></option>

										<%}
			}%>
								</select></TD>
							</TR>
							<TR>
								<TD class="plainlabel" width="15%">Trạng thái</TD>
								<TD class="plainlabel"><select name="trangthai" id="trangthai" onchange="Search();">
										<%if (obj.getTrangThai().equals("0")) {%>
										<option value=""></option>
										<option value="0" selected="selected">Chưa chốt</option>
										<option value="1">Đã chốt</option>


										<%} else if (obj.getTrangThai().equals("1")) {%>
										<option value=""></option>
										<option value="0">Chưa chốt</option>
										<option value="1" selected="selected">Đã chốt</option>



										<%} else {%>
										<option value=""></option>
										<option value="0">Chưa chốt</option>
										<option value="1">Đã chốt</option>


										<%}%>
								</select></TD>
							</TR>
							<tr>
								<td colspan="2" class="plainlabel"><a class="button" href="javascript:Search()"> <img
										style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm
								</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2" href="javascript:ClearSearch()"> <img
										style="top: -4px;" src="../images/button.png" alt="">Nhập lại
								</a>&nbsp;&nbsp;&nbsp;&nbsp;</td>
							</tr>
						</TABLE>
					</fieldset>
				</div>
				<!-- End div left-->
				<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
					<fieldset>
						<legend>
							<span class="legendtitle"> Kiểm kho </span>&nbsp;&nbsp;<%if(quyen[0]!=0){ %> <a class="button3" href="javascript:newform()"> <img
								style="top: -4px;" src="../images/New.png" alt="">Tạo mới
							</a><%} %>
						</legend>
						<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
							<TR class="tbheader">
								<TH align="center">Mã số</TH>
								<TH align="center">Ngày kiểm</TH>
								 
								<TH align="center">Kho</TH>
								<TH align="center">Diễn giải</TH>
								<TH align="center">Trạng Thái</TH>
								<TH align="center">Ngày tạo</TH>
								<TH align="center">Ngày sửa</TH>
								<TH align="center">Người tạo</TH>
								<TH align="center">Người sửa</TH>
								<TH align="center">Tác vụ</TH>
							</TR>
							<!--End Header Table -->
							<%int i = 0;%>
							<%if (rsdctktt != null) {
				while (rsdctktt.next()) {
					i++;
					if (i % 2 == 0) {%>
							<TR class='tbdarkrow'>
								<%} else {%>
							
							<TR class='tblightrow'>
								<%}%>
								<td align="center"><%=rsdctktt.getString("PK_SEQ")%></td>
								<td align="center"><%=rsdctktt.getString("ngaykiem")%></td>
								 
								<td align="center"><%=rsdctktt.getString("tenkho")%></td>
								<td align="center"><%=rsdctktt.getString("diengiai")%></td>
								<td align="center">
									<%String trangthai = "";
					String tt = rsdctktt.getString("TrangThai");
					if (tt.equals("0"))
						trangthai = "Chưa chốt";
					else {
						if (tt.equals("1"))
							trangthai = "Đã chốt";
						else if (tt.equals("2")) {

							trangthai = "Đã hủy";
						} 
					}%> <%=trangthai%>
								</td>
								<td align="center"><%=rsdctktt.getString("NgayTao")%></td>
								<td align="center"><%=rsdctktt.getString("NgaySua")%></td>
								<td align="center"><%=rsdctktt.getString("NguoiTao")%></td>
								<td align="center"><%=rsdctktt.getString("NguoiSua")%></td>
								<TD align="center">
									<%if (tt.equals("0")) {if(quyen[2]!=0){%> <A
									href="../../ErpKiemKhoUpdateSvl?userId=<%=userId%>&update=<%=rsdctktt.getString("PK_SEQ")%>"> <IMG
										src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0>
								</A><%}if(quyen[4]!=0){ %> <A
									href="../../ErpKiemKhoSvl?userId=<%=userId%>&approve=<%=rsdctktt.getString("PK_SEQ")%>&khott_fk=<%=rsdctktt.getString("KhoTT_FK")%>">
										<img src="../images/Chot.png" alt="Chốt" title="Chốt" width="20" height="20" border=0
										onclick="if(!confirm('Bạn có muốn chốt điều chỉnh tồn kho này?')) return false;">
								</A><%}if(quyen[1]!=0){ %> <A
									href="../../ErpKiemKhoSvl?userId=<%=userId%>&delete=<%=rsdctktt.getString("PK_SEQ")%>">
										<img src="../images/Delete20.png" alt="Xóa" title="Xóa" width="20" height="20" border=0
										onclick="if(!confirm('Bạn có muốn xóa điều chỉnh tồn kho này?')) return false;">
								</A> <%}} else {
									if (tt.equals("1")) {if(quyen[3]!=0){%> <A
									href="../../ErpKiemKhoUpdateSvl?userId=<%=userId%>&display=<%=rsdctktt.getString("PK_SEQ")%>">
										<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
								</A> <%}} else {if(quyen[3]!=0){%> <A
									href="../../ErpKiemKhoUpdateSvl?userId=<%=userId%>&display=<%=rsdctktt.getString("PK_SEQ")%>">
										<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0>
								</A> <%}}
					}%>
								</TD>
							</TR>
							<%}
			}%>
			<tr class="tbfooter">
								<TD align="center" valign="middle" colspan="13" class="tbfooter">
									<% obj.setNextSplittings(); %> <script type="text/javascript"
										src="../scripts/phanTrang.js"></script> <input type="hidden"
									name="crrApprSplitting"
									value="<%= obj.getCrrApprSplitting() %>"> <input
									type="hidden" name="nxtApprSplitting"
									value="<%= obj.getNxtApprSplitting() %>"> <%if(obj.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
									<%}else {%> <img alt="Trang Dau" src="../images/first.gif">
									&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
									alt="Trang Truoc" src="../images/prev.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')">
									&nbsp; <%}else{ %> <img alt="Trang Truoc"
									src="../images/prev_d.gif"> &nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int j = 0; j < listPage.length; j++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[j]);
												
												if(listPage[j] == obj.getNxtApprSplitting()){ %> <a
									style="color: white;"><%= listPage[j] %>/ <%=obj.getTheLastSplitting() %></a>
									<%}else{ %> <a
									href="javascript:View(document.forms[0].name, <%= listPage[j] %>, 'view')"><%= listPage[j] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[j] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')">
									&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
									src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
									<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
									<img alt="Trang Cuoi" src="../images/last.gif"
									style="cursor: pointer;"
									onclick="View(document.forms[0].name, -1, 'view')">
									&nbsp; <%} %>
								</TD>
							</tr>
						</TABLE>
					</fieldset>
				</div>
				<!-- End div Table Result -->
			</div>
			<!-- End div cotent -->
		</div>
		<!-- End div Main -->
	</form>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
	<!-- End form -->
</body>
</html>
<%
try{
	if(rsdctktt!=null){
		rsdctktt.close();
	}
	if(rsKho!=null){
		rsKho.close();
	}
	if(rsKhu!=null){
		rsKhu.close();
	}
	obj.DbClose();
	session.setAttribute("obj", null) ;  
	
}catch(Exception er){
	
}
	
	}%>