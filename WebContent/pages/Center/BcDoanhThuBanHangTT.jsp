<%@page import="org.w3c.dom.Element"%>
<%@page import="org.w3c.dom.NodeList"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.report.IBcDoanhThuBanHangTTList"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "org.w3c.dom.NodeList;" %>


<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBcDoanhThuBanHangTTList obj = (IBcDoanhThuBanHangTTList) session.getAttribute("obj");
	ResultSet ddkd = obj.getDdkdRs();
	ResultSet khRs = obj.getKhRs();
	ResultSet spRs = obj.getSpRs();
	ResultSet hdRs = obj.getHoadonRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet totalRs = obj.getTotalRs();
	ResultSet nhomhangERPRs = obj.getRsnhomhangERPid();
	ResultSet nhomkhERPRs = obj.getRsnhomkhERPid();
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("BcDoanhThuBanHangTTSvl","&view=TT");

	ResultSet vungRs = obj.getVungRs();
	ResultSet kvRs = obj.getKvRs();
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

<!--  <link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { 
    		$("select:not(.notuseselect2)").select2({ width: 'resolve' });     
    	});
    </script>	 --> 

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ckParkForm" method="post" action="../../BcDoanhThuBanHangTTSvl">
		<input type="hidden" name="action" value=''>
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden"name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
		<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>">
		<% obj.setNextSplittings(); %>
		<input type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>
		
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %></div>
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
					<legend class="legendtitle">Doanh thu bán hàng </legend>
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
										<input type="text" name="Edays" class="days" value='<%=obj.getDenNgay()%>'   /></td> 
						   </TR>
						   
						   
						  <%--  <TR>
									<TD class="plainlabel">Miền  </TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (vungRs != null)
													while (vungRs.next()) {
														if (vungRs.getString("pk_seq").equals(obj.getVungId()  )) {%>
											   <option value="<%=vungRs.getString("pk_seq")%>" selected><%=vungRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=vungRs.getString("pk_seq")%>"><%=vungRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">Khu vực  </TD>
									<TD class="plainlabel">
										<select name="kvId" id="kvId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (kvRs != null)
													while (kvRs.next()) {
														if (kvRs.getString("pk_seq").equals(obj.getKvId()  )) {%>
											   <option value="<%=kvRs.getString("pk_seq")%>" selected><%=kvRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=kvRs.getString("pk_seq")%>"><%=kvRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
								</TR>
								
								<TR>
									
									<TD class="plainlabel">Địa bàn  </TD>
									<TD class="plainlabel">
										<select name="ttId" id="ttId" style="width:250px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (ttRs != null)
													while (ttRs.next()) {
														if (ttRs.getString("pk_seq").equals(obj.getTtId()  )) {%>
											   <option value="<%=ttRs.getString("pk_seq")%>" selected><%=ttRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=ttRs.getString("pk_seq")%>"><%=ttRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel">  Kênh bán hàng </TD>
									<TD class="plainlabel" colspan="3">
										<select name="kbhId" id="kbhId" style="width:250px"  onchange="submitform();"  >
											<option value="" >All</option>
											<%if (kbhRs != null)
													while (kbhRs.next()) {
														if (kbhRs.getString("pk_Seq").equals(obj.getKbhId())) {%>
											   <option value="<%=kbhRs.getString("pk_Seq")%>" selected><%=kbhRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=kbhRs.getString("pk_Seq")%>"><%=kbhRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
								</TR> --%>
						<TR>
						<TD class="plainlabel"> Khách hàng </TD>
									<TD class="plainlabel" >
										<select name="khId" id="khId" style="width:200px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (khRs != null)
													while (khRs.next()) {
														if (khRs.getString("pk_seq").equals(obj.getKhId()   )) {%>
											   <option value="<%=khRs.getString("pk_seq")%>" selected><%=khRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=khRs.getString("pk_seq")%>"><%=khRs.getString("ten")%></option>
											<%}}%>
										</select>
										</TD>
							<TD class="plainlabel"> Nhóm hàng  </TD>
									<TD class="plainlabel">
										<select name="nhomId" id="nhomId" style="width:200px"   onchange="submitform();" >
											<option value="" >All</option>
											<%if (nhomRs != null)
													while (nhomRs.next()) {
														if (nhomRs.getString("pk_seq").equals(obj.getNhomId()   )) {%>
											   <option value="<%=nhomRs.getString("pk_seq")%>" selected><%=nhomRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=nhomRs.getString("pk_seq")%>"><%=nhomRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									
							
						</TR>
								
							<%-- <TR>
							<TD class="plainlabel"> Nhóm doanh thu  </TD>
									<TD class="plainlabel">
										<select name="nhomhangERPid" id="nhomhangERPid" style="width:250px"   >
											<option value="" >All</option>
											<%if (nhomhangERPRs != null)
													while (nhomhangERPRs.next()) {
														if (nhomhangERPRs.getString("pk_seq").equals(obj.getNhomhangERPid()  )) {%>
											   <option value="<%=nhomhangERPRs.getString("pk_seq")%>" selected><%=nhomhangERPRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=nhomhangERPRs.getString("pk_seq")%>"><%=nhomhangERPRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
							<TD class="plainlabel"> Chi nhánh/ĐT </TD>
									<TD class="plainlabel" colspan="3">
										<select name="nppId" id="nppId" style="width:250px"  onchange="submitform();" >
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
									
											<TD class="plainlabel"> Nhóm khách hàng ERP  </TD>
									<TD class="plainlabel">
										<select name="nhomkhERPid" id="nhomkhERPid" style="width:250px"   >
											<option value="" >All</option>
											<%if (nhomkhERPRs != null)
													while (nhomkhERPRs.next()) {
														if (nhomkhERPRs.getString("pk_seq").equals(obj.getNhomkhERPid()  )) {%>
											   <option value="<%=nhomkhERPRs.getString("pk_seq")%>" selected><%=nhomkhERPRs.getString("ten")%></option>
											   <%} else {%>
											   <option value="<%=nhomkhERPRs.getString("pk_seq")%>"><%=nhomkhERPRs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
						</TR>	 --%>
								
				<%
					double DoanhSo=0;
					double Thue=0;
					double DoanhThu=0;
					if(totalRs !=null){
					while( totalRs.next() ) 
					{
						
						DoanhSo+=totalRs.getDouble("BVAT");
						Thue+=totalRs.getDouble("VAT");
						DoanhThu+=totalRs.getDouble("AVAT");
					}
					}
					if(obj.getAction().length()>0)
					{
					%>
					
					<TR>
						<TD class="plainlabel" colspan="6"></TD>
								<TR>
									<TD class="plainlabel" >DOANH THU</TD>
									<td class="plainlabel"><input type="text" name="ds" size="6" value="<%= formatter.format(DoanhSo   ) %>"></td>
									<TD class="plainlabel" >THUẾ GTGT</TD>
									<td class="plainlabel"><input type="text" name="ck" size="6" value="<%= formatter.format( Thue ) %>"></td>
									<TD class="plainlabel" >TỔNG TIỀN</TD>
									<td class="plainlabel"><input type="text" name="dt" size="6" value="<%= formatter.format(  DoanhThu) %>"></td>
							
								</TR>
					<%} %>
								
						   
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
													<th  width="10%"  align="center"> STT</th>
													<th  width="10%"  align="center"> Mã KH </th>
													<th  width="30%"  align="center"> Tên Khách hàng </th>
													<th  width="35%" align="left">Địa chỉ</th>
													<th  width="20%"  align="right">DOANH THU </th>
												</TR>
																	
								<%
					 DoanhSo=0;
					 Thue=0;
					 DoanhThu=0;
					
					%>
								
													
												<%
												if(hdRs!=null){
													try{
														int m = 1;
														int i=0;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														while(hdRs.next() )
														{
															
															
															DoanhSo +=hdRs.getDouble("BVAT"); 
															Thue +=hdRs.getDouble("VAT"); 
															DoanhThu +=hdRs.getDouble("AVAT"); 
															
															
															if (m % 2 != 0) {%>						
															<TR class= <%=lightrow%> >
														<%} else {%>
															<TR class= <%= darkrow%> >
														<%}%>
															<Td align="center"><%=i+1 %></Td>
															<Td align="center"><%=hdRs.getString("nppMa")%></Td>
															<Td align="center"><%=hdRs.getString("nppTen")%></Td>
															<Td align="left"><%=hdRs.getString("diachi")%></Td>
															<Td align="right"><%=formatter.format(hdRs.getDouble("AVAT") ) %></Td>
															
														
												<%i++; m++;}}catch(Exception e){}} %>
												</TR>
												<tr class="tbfooter">
													<Td align="center" colspan="4" >TỔNG CỘNG</Td>
													<Td align="right"><%= formatter.format( DoanhThu ) %></Td>
												</TR>
												
												
											</TABLE>
										</div>
		
	</div>
	</form>
	<%
	if(totalRs!=null)totalRs.close();
	if(hdRs!=null)hdRs.close();
		if(obj!=null){obj.closeDB();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>