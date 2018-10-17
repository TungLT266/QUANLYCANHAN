<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.report.IBcTheKho"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatter_Gia = new DecimalFormat("#,###,###.####"); %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBcTheKho obj = (IBcTheKho) session.getAttribute("obj");
	ResultSet ddkd = obj.getDdkdRs();
	ResultSet khRs = obj.getKhRs();
	ResultSet spRs = obj.getSpRs();
	ResultSet khoRs = obj.getKhoRs();
	ResultSet hdRs = obj.getHoadonRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet totalRs = obj.getTotalRs();
	

	ResultSet rsdoituong = obj.getRsDoiTuong();
	 

	ResultSet vungRs = obj.getVungRs();
	ResultSet ttRs = obj.getTtRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet nhomRs = obj.getNhomRs();
	
	
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
	document.forms['ckParkForm'].action.value='';
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
     $(document).ready(function() { $("#spId").select2(); });
     
 </script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ckParkForm" method="post" action="../../BcTheKhoSvl">
		<input type="hidden" name="action" value=''>
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
		<% obj.setNextSplittings(); %>
		<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý kho chi nhánh &#62; Báo cáo &#62; Thẻ Kho</div>
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
					<legend class="legendtitle">Báo cáo Thẻ Kho </legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >
							<TR >
									<TD class="plainlabel" width="120px">Từ ngày</TD>
									<TD class="plainlabel" width="250px" >
								 	<input type="text" name="Sdays"	class="days" value='<%=obj.getTuNgay()%>'  /></TD>
								 	
								 	<TD class="plainlabel"> Chi nhánh/ĐT </TD>
									<TD class="plainlabel" colspan="3">
										<select name="nppId" id="nppId" style="width:250px" >
											<option value="" >All</option>
											<%if (nppRs != null)
													while (nppRs.next()) {
														if (nppRs.getString("pk_seq").equals(obj.getNppId()   )) {%>
											   <option value="<%=nppRs.getString("pk_seq")%>" selected><%=nppRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=nppRs.getString("pk_seq")%>"><%=nppRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD> 
									
						   </TR>
						   
						   
						   <TR>
						   
						   <TD class="plainlabel" width="100px" > Đến ngày</TD>
									<TD class="plainlabel" >
										<input type="text" name="Edays" class="days" value='<%=obj.getDenNgay()%>'   /></td> 
						   <TD class="plainlabel"> Sản phẩm </TD>
									<TD class="plainlabel" colspan="3">
										<select name="spId" id="spId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (spRs != null)
													while (spRs.next()) {
														if (spRs.getString("pk_seq").equals(obj.getSpId() )) {%>
											   <option value="<%=spRs.getString("pk_seq")%>" selected><%=spRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
						   
						   <TD class="plainlabel" width="100px" >Kho xuất</TD>
									<TD class="plainlabel" >
										<select name="khoId" id="khoId" style="width:200px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (khoRs != null)
													while (khoRs.next()) {
														if (khoRs.getString("pk_seq").equals(obj.getKhoId() )) {%>
											   <option value="<%=khoRs.getString("pk_seq")%>" selected><%=khoRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=khoRs.getString("pk_seq")%>"><%=khoRs.getString("ten")%></option>
											<%}}%>
										</select>
										</td> 
						   
									 	<TD class="plainlabel" width="120px">Số lô</TD>
									<TD class="plainlabel" width="250px"  colspan="3">
								 	<input type="text" name="solo" onchange="search();" value='<%=obj.getSolo()%>'  /></TD>
								</TR>
			 
					<%
								System.out.println("kho id " + obj.getKhoId());
								if(obj.getIsKhoCoDoituong())
								{
								%>
								<TR>
									<TD class="plainlabel">Chọn đối tượng xem báo cáo</TD>
									<TD class="plainlabel"><select name="doituongid" id="doituongid" style="width: 200px;">
										<option value="" ></option>
										<%
										if (rsdoituong != null)
											while (rsdoituong.next()) {
												if (rsdoituong.getString("pk_seq").equals(obj.getDoituongId())) {
												%>
											<option value="<%=rsdoituong.getString("pk_seq")%>" selected><%=rsdoituong.getString("TEN")%></option>
											<%
											} else {
											%>
											<option value="<%=rsdoituong.getString("pk_seq")%>"><%=rsdoituong.getString("TEN")%></option>
											<%
											}
										}
										%>
									</select></TD>
									
								</TR>
								<%
								} %>
			
					<TR>
						<TD class="plainlabel" colspan="6">
								<a class="button2" href="javascript:search();"> 
									<img style="top: -4px;" src="../images/button.png" alt=""> Xem </a>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="button2" href="javascript:xuatExcel();"> 
								<img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel </a>&nbsp;&nbsp;&nbsp;&nbsp;
					</TR> 
			
								
							</TABLE>
						</div>
			</div>
			</fieldset>
		</div>
		
		
		 <div id="tabKM" class="tab_content">
											<TABLE width="100%" border="1" cellspacing="1px" cellpadding="3px" >
												<TR class="plainlabel" valign="bottom" >
													<th  width="12.5%"  align="center"> NGÀY CHỨNG TỪ</th>
													<th  width="12.5%"  align="center">SỐ CHỨNG TỪ</th>
													<th  width="12.5%"  align="center">MÃ KHÁCH HÀNG   </th>
													<th  width="12.5%" align="left">TÊN KH</th>
													<th  width="12.5%" align="left">NGHIỆP VỤ</th>
													<th  width="12.5%" align="left">SỐ LÔ</th>
													<th  width="12.5%"  align="right">NHẬP </th>
													<th  width="12.5%" align="right">XUẤT </th>
												</TR>
												<tr style="font-weight: bold;font-size:15px;"  >
													<td colspan="6">
													Đầu kỳ: 
													</td>
														<td  colspan="2"  >
														<%=formatter.format(obj.getDauKy())%>
														
														</td>
														 
												</tr>
																	
								<%
					
					%>
					<%
												double cuoiky=obj.getDauKy();
												double tongnhap = 0;
												double tongxuat = 0;
												if(hdRs!=null){
													try{
														int m = 1;
														String lightrow = "tblightrow";
														String darkrow =  "tbdarkrow";
														while (hdRs.next())
														{
															boolean bien = true;
															if(obj.getSolo().length()> 0){
																if(!obj.getSolo().equals(hdRs.getString("Số Lô"))){
																	 bien = false;		
																}
															}
																
															if(bien){
															cuoiky = cuoiky + hdRs.getDouble("SOLUONGNHAP")-hdRs.getDouble("SOLUONGXUAT");
															tongnhap += hdRs.getDouble("SOLUONGNHAP");
															tongxuat += hdRs.getDouble("SOLUONGXUAT");
															if (m % 2 != 0) {%>						
															<TR class= <%=lightrow%> >
														<%} else {%>
															<TR class= <%= darkrow%> >
														<%}%>
															<Td align="left"><%=hdRs.getString("Ngày chứng từ") %></Td>
															<Td align="left"><%=hdRs.getString("Số chứng từ") %></Td>
															<Td align="left"><%=hdRs.getString("Mã đối tượng") %></Td>
															<Td align="left"><%=hdRs.getString("Tên đối tượng")  %></Td>
															<Td align="left"><%=hdRs.getString("Diễn giải") %></Td>
															<Td align="left"><%=hdRs.getString("Số Lô") %></Td>
															<Td align="right"><%=    formatter.format(hdRs.getDouble("SOLUONGNHAP"))%></Td>
															<Td align="right"><%=    formatter.format(hdRs.getDouble("SOLUONGXUAT"))%></Td>
															
												<% m++;
															}
												}}catch(Exception e){ 
													e.printStackTrace();
												}} %>
					 
										 
												</TR>
												<tr style="font-weight: bold;font-size:15px;" >
													<td colspan="6">
													</td>
													<td >
														<%=formatter.format(tongnhap)%>
													</td>
												 	<td >
														<%=formatter.format(tongxuat)%>
													</td>
												</tr>
												<tr style="font-weight: bold;font-size:15px;" >
													<td colspan="6">
													Cuối kỳ: 
													</td>
													<td colspan="2"  >
														<%=formatter.format(cuoiky)%>	
													</td>
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