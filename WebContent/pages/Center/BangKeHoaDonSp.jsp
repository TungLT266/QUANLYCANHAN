<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.bangkehoadonsp.IBangKeHoaDonSpList"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBangKeHoaDonSpList obj = (IBangKeHoaDonSpList) session.getAttribute("obj");
	ResultSet ddkd = obj.getDdkdRs();
	ResultSet khRs = obj.getKhRs();
	ResultSet spRs = obj.getSpRs();
	ResultSet hdRs = obj.getHoadonRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet totalRs = obj.getTotalRs();
	
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
<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">

function clearform()
{   
	document.forms["ckParkForm"].Sdays.value="";
	document.forms["ckParkForm"].Edays.value="";
	document.forms["ckParkForm"].khId.value="";
	document.forms["ckParkForm"].spId.value="";
	document.forms["ckParkForm"].ddkdId.value="";
	document.forms["ckParkForm"].kbhId.value="";
   document.forms["ckParkForm"].submit();
}

function submitform()
{   
   document.forms["ckParkForm"].submit();
}

function xuatExcel()
{
	document.forms['ckParkForm'].action.value= 'excel';
	document.forms['ckParkForm'].submit();
}

function search()
{
	
	document.forms['ckParkForm'].action.value= 'search';
	document.forms['ckParkForm'].submit();
}


</script>

 <link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	 

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ckParkForm" method="post" action="../../BangKeHoaDonSpSvlTT">
		<input type="hidden" name="action" value='1'>
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
		<% obj.setNextSplittings(); %>
		<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý bán hàng &#62; Báo cáo &#62; Bảng kê HĐ theo mặt hàng</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Doanh số theo khách hàng </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
							<TR >
									<TD class="plainlabel" width="120px">Từ ngày</TD>
									<TD class="plainlabel" width="250px" >
								 	<input type="text" name="Sdays"	class="days" value='<%=obj.getTuNgay()%>'  /></TD> 
									<TD class="plainlabel" width="100px" > Đến ngày</TD>
									<TD class="plainlabel" colspan="3">
										<input type="text" name="Edays" class="days" value='<%=obj.getDenNgay()%>'  /></td> 
						   </TR>
							
								<TR>
									<TD class="plainlabel">Khách hàng </TD>
									<TD class="plainlabel">
										<select name="khId" id="khId" style="width:250px"  >
											<option value="" >All</option>
											<%if (khRs != null)
													while (khRs.next()) {
														if (khRs.getString("pk_seq").equals(obj.getKhId()  )) {%>
											   <option value="<%=khRs.getString("pk_seq")%>" selected><%=khRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=khRs.getString("pk_seq")%>"><%=khRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
									<TD class="plainlabel">Sản phẩm </TD>
									<TD class="plainlabel" colspan="3">
										<select name="spId" id="spId" style="width:250px"  >
											<option value="" >All</option>
											<%if (spRs != null)
													while (spRs.next()) {
														if (spRs.getString("pk_seq").equals(obj.getSpId())) {%>
											   <option value="<%=spRs.getString("pk_seq")%>" selected><%=spRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR >
									<TD class="plainlabel" width="120px">Lấy chi tiết</TD>
									<TD class="plainlabel" width="250px" >
									<% if (obj.getLoaiBaoCao().equals("1")){ %>
								 		<input type="checkbox" name="loaibaocao" id="loaibaocao" value='1' checked="checked" />
								 	<% }else{ %>
								 		<input type="checkbox" name="loaibaocao" id="loaibaocao" value='1' />
								 	<% } %>
								 	</TD> 
								 </TR>
								
								<%-- <TR>
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId" style="width:250px"   class="notuseselect2" >
											<option value="" >All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getDdkdId()  )) {%>
											   <option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel" colspan="3">
										<select name="kbhId" id="kbhId" style="width:250px"  onchange="search();"  class="notuseselect2" >
											<option value="" >All</option>
											<%if (kbhRs != null)
													while (kbhRs.next()) {
														if (kbhRs.getString("pk_seq").equals(obj.getKbhId()  )) {%>
											   <option value="<%=kbhRs.getString("pk_seq")%>" selected><%=kbhRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=kbhRs.getString("pk_seq")%>"><%=kbhRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									 
								</TR>
								 --%>
				<%
					double DoanhSo=0;
					double Thue=0;
					double DoanhThu=0;
					while(totalRs!=null && totalRs.next())
					{
						DoanhSo+=totalRs.getDouble("BVAT");
						Thue+=totalRs.getDouble("VAT");
						DoanhThu+=totalRs.getDouble("AVAT");
					}
					
					%>
								<TR><TD class="plainlabel" colspan="6"></TD>
								<TR>
									<TD class="plainlabel" >Doanh số</TD>
									<td class="plainlabel"><input type="text" name="ds" size="6" value="<%= formatter.format(DoanhSo   ) %>"></td>
									<TD class="plainlabel" >Thuế</TD>
									<td class="plainlabel"><input type="text" name="ck" size="6" value="<%= formatter.format( Thue ) %>"></td>
									<TD class="plainlabel" >Doanh thu</TD>
									<td class="plainlabel"><input type="text" name="dt" size="6" value="<%= formatter.format(  DoanhThu) %>"></td>
							
								</TR>
								
					<TR>
						<TD class="plainlabel" colspan="6">
								<a class="button2" href="javascript:search();"> 
									<img style="top: -4px;" src="../images/button.png" alt=""> Tìm kiếm</a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="button2" href="javascript:xuatExcel();"> 
								<img style="top: -4px;" src="../images/button.png" alt="">Xuất dữ liệu </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR> 
			
								
							</TABLE>
						</div>
			</div>
			</fieldset>
		</div>
		
		
		<div id="tabKM" class="tab_content">
											<TABLE width="100%" border="0" cellspacing="1px" cellpadding="3px">
												<TR class="plainlabel" valign="bottom">
													<th  width="5%"  align="center"> STT</th>
													<th  width="5%" align="left"> Mã KH(FAST)</th>
													
													<th  width="10%" align="left"> Tên KH</th>
													<th  width="13%" align="left"> Địa chỉ</th>
													<th  width="5%" align="left" > Số HĐ</th>
													<th  width="5%" align="left"> Mã SP</th>
													<th  width="12%" align="left"> Sản phẩm</th>
												<% if (obj.getLoaiBaoCao().equals("1")){ %>
													<th  width="6%" align="left"> Số lô</th>
												<% } %>
													<th  width="5%" align="left"> Đơn vị</th>
													<th  width="5%"  align="right"> Số lượng</th>
													<th  width="7%" align="right"> Đơn giá</th>
													<th  width="7%" align="right"> Thành tiền</th>
													<th  width="7%" align="right">Thuế</th>
													<th  width="9%" align="right"> Tổng tiền Thuế</th>
												
												</TR>
												
													
												<%
												if(hdRs!=null){
													try{
														int m = 1;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														while (hdRs.next()){
															if (m % 2 != 0) {%>						
															<TR class= <%=lightrow%> >
														<%} else {%>
															<TR class= <%= darkrow%> >
														<%}%>
															<Td align="center"><%= hdRs.getInt("_no") %></Td>
															<Td align="left"><%= hdRs.getString("khMa") %></Td>
															
															<Td align="left"><%= hdRs.getString("khTen") %></Td>
															<Td align="left"><%= hdRs.getString("khDiaCHI")%></Td>
															<Td align="left"><%= hdRs.getString("SoHoaDon") %></Td>
															<Td align="left"><%=hdRs.getString("spMa") %></Td>
															<Td align="left"><%=hdRs.getString("spTen") %></Td>
														<% if (obj.getLoaiBaoCao().equals("1")){ %>
															<Td align="left"><%=hdRs.getString("solo") %></Td>
														<% } %>
															<Td align="left"><%=hdRs.getString("donvitinh") %></Td>
															<Td align="right"><%= formatter.format(hdRs.getDouble("SoLuong"))  %></Td>
															<Td align="right"><%= formatter.format(hdRs.getDouble("dongia")) %></Td>
															<Td align="right"><%= formatter.format(hdRs.getDouble("BVAT")) %></Td>
															<Td align="right"><%= formatter.format(hdRs.getDouble("VAT")) %></Td>
															<Td align="right"><%= formatter.format(hdRs.getDouble("AVAT")) %></Td>
															
														
												<% m++;}}catch(Exception e){}} %>
												</TR>
												
												 <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="14"
															class="tbfooter">
															<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
															src="../images/first.gif" style="cursor: pointer;"
															onclick="View('ckParkForm', 1, 'view')"> &nbsp; <%}else {%>
															<img alt="Trang Dau" src="../images/first.gif">
															&nbsp; <%} %> <% if(obj.getNxtApprSplitting() > 1){ %> <img
															alt="Trang Truoc" src="../images/prev.gif"
															style="cursor: pointer;"
															onclick="Prev('ckParkForm', 'prev')"> &nbsp; <%}else{ %>
															<img alt="Trang Truoc" src="../images/prev_d.gif">
															&nbsp; <%} %> <%
													int[] listPage = obj.getNextSplittings();
													for(int i = 0; i < listPage.length; i++){
												%> <% 							
											
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
															style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
															<%}else{ %> <a
															href="javascript:View('ckParkForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
															<%} %> <input type="hidden" name="list"
															value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
															style="cursor: pointer;"
															onclick="Next('ckParkForm', 'next')"> &nbsp; <%}else{ %>
															&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif">
															&nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
															<img alt="Trang Cuoi" src="../images/last.gif">
															&nbsp; <%}else{ %> <img alt="Trang Cuoi"
															src="../images/last.gif" style="cursor: pointer;"
															onclick="View('dhForm', -1, 'view')"> &nbsp; <%} %>
														</TD>
													</tr>
												
												
											</TABLE>
										</div>
		
	</div>
	</form>
	<%
		if(obj!=null){obj.closeDB();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>