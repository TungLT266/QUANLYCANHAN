<%@page import="java.sql.ResultSet"%>
<%@page
	import="geso.traphaco.center.beans.report.IBcBanHangTheoChiTieuList"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page import="geso.traphaco.center.util.*"%>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>

<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBcBanHangTheoChiTieuList obj = (IBcBanHangTheoChiTieuList) session.getAttribute("obj");
	ResultSet ddkd = obj.getDdkdRs();
	ResultSet khRs = obj.getKhRs();
	ResultSet spRs = obj.getSpRs();
	ResultSet hdRs = obj.getHoadonRs();
	ResultSet kbhRs = obj.getKbhRs();
	ResultSet totalRs = obj.getTotalRs();
	String tongtien=obj.getTongcong();
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("BcBanHangTheoChiTieuSvl","");

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
<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>


<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>



<script language="javascript" type="text/javascript">
$(document).ready(function() {		
	$( ".days" ).datepicker({			    
			changeMonth: true,
			changeYear: true				
	});            
}); 

jQuery(document).ready(function()
{
	$("select:not(.notuseselect2)").chosen();     
	
}); 


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



</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="ckParkForm" method="post"
		action="../../BcBanHangTheoChiTieuSvl">
		<input type="hidden" name="action" value=''> <input
			type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="msg" id="msg" value='<%= obj.getMsg() %>'>

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
					<textarea id="errors" name="errors" rows="1"
						style="width: 100%; color: #F00; font-weight: bold">
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
							<TABLE width="100%" cellpadding="6px" cellspacing="0px"
								style="margin-top: 5px">
								<TR>
									<TD class="plainlabel" width="120px">Từ ngày</TD>
									<TD class="plainlabel" width="250px"><input type="text"
										name="Sdays" class="days" value='<%=obj.getTuNgay()%>' /></TD>
									<TD class="plainlabel" width="100px">Đến ngày</TD>
									<TD class="plainlabel" colspan="3"><input type="text"
										name="Edays" class="days" value='<%=obj.getDenNgay()%>' /></td>
								</TR>


								<TR>
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel"><select name="vungId" id="vungId"
										style="width: 250px" onchange="submitform();">
											<option value="">All</option>
											<%if (vungRs != null)
													while (vungRs.next()) {
														if (vungRs.getString("pk_seq").equals(obj.getVungId()  )) {%>
											<option value="<%=vungRs.getString("pk_seq")%>" selected><%=vungRs.getString("ten")%></option>
											<%} else {%>
											<option value="<%=vungRs.getString("pk_seq")%>"><%=vungRs.getString("ten")%></option>
											<%}}%>
									</select></TD>

									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel" colspan="3"><select name="kbhId"
										id="kbhId" style="width: 250px">
											<option value="">All</option>
											<%if (kbhRs != null)
													while (kbhRs.next()) {
														if (kbhRs.getString("pk_Seq").equals(obj.getKbhId())) {%>
											<option value="<%=kbhRs.getString("pk_Seq")%>" selected><%=kbhRs.getString("ten")%></option>
											<%} else {%>
											<option value="<%=kbhRs.getString("pk_Seq")%>"><%=kbhRs.getString("ten")%></option>
											<%}}%>
									</select></TD>
								</TR>

								<TR>
									<TD class="plainlabel">Địa bàn</TD>
									<TD class="plainlabel"><select name="ttId" id="ttId"
										style="width: 250px" onchange="submitform();">
											<option value="">All</option>
											<%if (ttRs != null)
													while (ttRs.next()) {
														if (ttRs.getString("pk_seq").equals(obj.getTtId()  )) {%>
											<option value="<%=ttRs.getString("pk_seq")%>" selected><%=ttRs.getString("ten")%></option>
											<%} else {%>
											<option value="<%=ttRs.getString("pk_seq")%>"><%=ttRs.getString("ten")%></option>
											<%}}%>
									</select></TD>

									<TD class="plainlabel">Chi nhánh/ĐT</TD>
									<TD class="plainlabel" colspan="3"><select name="nppId"
										id="nppId" style="width: 250px" onchange="submitform();">
											<option value="">All</option>
											<%if (nppRs != null)
													while (nppRs.next()) {
														if (nppRs.getString("pk_seq").equals(obj.getNppId()   )) {%>
											<option value="<%=nppRs.getString("pk_seq")%>" selected><%=nppRs.getString("ten")%></option>
											<%} else {%>
											<option value="<%=nppRs.getString("pk_seq")%>"><%=nppRs.getString("ten")%></option>
											<%}}%>
									</select></TD>
								</TR>

								<TR>
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel"><select name="ddkdId" id="ddkdId" class="chosen-select" multiple style="width: 350px;" onchange="submitform();">
											<option value="">All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (obj.getDdkdId() .indexOf(ddkd.getString("pk_seq")) >=0) {%>
											<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											<%} else {%>
											<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
											<%}}%>
									</select></TD>

									<TD class="plainlabel">Khách hàng</TD>
									<TD class="plainlabel"><select name="khId" id="khId"
										style="width: 250px" multiple="multiple">
											<option value="">All</option>
											<%if (khRs != null)
													while (khRs.next()) {
														if (obj.getKhId() .indexOf(khRs.getString("pk_seq")) >=0) {%>
											<option value="<%=khRs.getString("pk_seq")%>" selected><%=khRs.getString("ten")%></option>
											<%} else {%>
											<option value="<%=khRs.getString("pk_seq")%>"><%=khRs.getString("ten")%></option>
											<%}}%>
									</select></TD>
								</TR>

								<TR>
									<TD class="plainlabel">Sản phẩm</TD>
									<TD class="plainlabel" colspan="3"><select name="spId"
										id="spId" style="width: 250px" multiple="multiple">
											<option value="">All</option>
											<%if (spRs != null)
														while (spRs.next()) {
															if (obj.getSpId() .indexOf(spRs.getString("pk_seq")) >=0) {%>
											<option value="<%=spRs.getString("pk_seq")%>" selected><%=spRs.getString("ma")%>-<%=spRs.getString("ten")%></option>
											<%} else {%>
											<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ma")%>-<%=spRs.getString("ten")%></option>
											<%}}%>
									</select></TD>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel"></TD>
								</TR>


								<%
					double DoanhSo=0;
					double Thue=0;
					double DoanhThu=0;
					while(totalRs!=null && totalRs.next())
					{
						DoanhSo=totalRs.getDouble("BVAT");
						Thue=totalRs.getDouble("VAT");
						DoanhThu=totalRs.getDouble("AVAT");
					}
					 if(obj.getAction_().length()>0)
					{
					%>

								<%-- <TR>
									<TD class="plainlabel" colspan="6"></TD>
								<TR>
									<TD class="plainlabel">DOANH THU</TD>
									<td class="plainlabel"><input type="text" name="ds"
										size="6" value="<%= formatter.format(DoanhSo   ) %>"></td>
									<TD class="plainlabel">THUẾ GTGT</TD>
									<td class="plainlabel"><input type="text" name="ck"
										size="6" value="<%= formatter.format( Thue ) %>"></td>
									<TD class="plainlabel">TỔNG TIỀN</TD>
									<td class="plainlabel"><input type="text" name="dt"
										size="6" value="<%= formatter.format(  DoanhThu) %>"></td>

								</TR> --%>
								<%} %> 


								<TR>
									<TD class="plainlabel" colspan="6"><a class="button2"
										href="javascript:search();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tìm kiếm
									</a>&nbsp;&nbsp;&nbsp;&nbsp; <a class="button2"
										href="javascript:xuatExcel();"> <img style="top: -4px;"
											src="../images/button.png" alt="">Xuất dữ liệu
									</a>&nbsp;&nbsp;&nbsp;&nbsp;
								</TR>


							</TABLE>
						</div>
					</div>
				</fieldset>
			</div>


			<TABLE class="" width="100%">
				<TR>
					<TD width="98%">
						<TABLE width="100%" border="0" cellspacing="1px" cellpadding="4px">
							<TR class="tbheader">
								<%-- <%if(obj.getDdkdId().length()>0){ %>

								<th width="10%" align="center">STT</th>
								<th width="10%" align="center">MÃ SẢN PHẨM</th>
								<th width="30%" align="center">TÊN SÁN PHẨM</th>
								<th width="35%" align="left">SỐ LƯỢNG</th>
								<th width="35%" align="left">ĐƠN GIÁ</th>
								<th width="20%" align="right">DOANH THU</th>

								<%}else { %>
 --%>
								<th width="5%" align="center">STT</th>
								<th width="12%" align="center">TRÌNH DƯỢC VIÊN</th>
								<th width="10%" align="center">MÃ KHÁCH HÀNG</th>
								<th width="10%" align="center">MÃ HỢP ĐỒNG</th>
								<th width="15%" align="center">TÊN KHÁCH HÀNG</th>
								<th width="10%" align="center">MÃ SẢN PHẨM</th>
								<th width="10%" align="center">TÊN SÁN PHẨM</th>
								<th width="10%" align="left">SỐ LƯỢNG</th>
								<th width="10%" align="left">ĐƠN GIÁ</th>
								<th width="10%" align="right">DOANH THU</th>

								<%-- <%} %> --%>
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
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														while (hdRs.next()){
															
															DoanhSo +=hdRs.getDouble("BVAT");
															Thue +=hdRs.getDouble("VAT");
															DoanhThu +=hdRs.getDouble("AVAT");
															
															
															
															if (m % 2 != 0) {%>
							<TR class=<%=lightrow%>>
								<%} else {%>
							
							<TR class=<%= darkrow%>>
								<%}%>
								<Td align="center"><%= hdRs.getInt("_no") %></Td>
								<Td align="left"><%=hdRs.getString("ddkdTEN") %></Td>
								<Td align="left"><%=hdRs.getString("khMA") %></Td>
								<Td align="left"><%=hdRs.getString("khMAHD") %></Td>
								<Td align="left"><%=hdRs.getString("khTEN") %></Td>
								<Td align="left"><%=hdRs.getString("spMa") %></Td>
								<Td align="left"><%=hdRs.getString("spTEN") %></Td>
								<Td align="right"><%= formatter.format(hdRs.getDouble("SoLuong")) %></Td>
								<Td align="right"><%= formatter.format(hdRs.getDouble("AVAT")/hdRs.getDouble("SoLuong") ) %></Td>
								<Td align="right"><%= formatter.format(hdRs.getDouble("AVAT")) %></Td>
								<% m++;}}catch(Exception e){e.printStackTrace();}} %>
							</TR>
							

							<TR class="tbheader">
								<Td align="center" colspan="9">TỔNG CỘNG</Td>
								<Td align="right"><%= obj.getTongcong() %></Td>
							</TR>

							<tr class="tbfooter">
							
							
								<TD align="center" valign="middle" colspan="10" class="tbfooter">
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
													for(int i = 0; i < listPage.length; i++){
												%> <% 
												
												System.out.println("Current page:" + obj.getNxtApprSplitting());
												System.out.println("List page:" + listPage[i]);
												
												if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
									style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
									<%}else{ %> <a
									href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
									<%} %> <input type="hidden" name="list"
									value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
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
					</TD>
				</TR>
			</TABLE>
		</div>
	</form>
	<%
		if(obj!=null){obj.closeDB();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>