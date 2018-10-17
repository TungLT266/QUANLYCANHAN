<%@page import="org.w3c.dom.Element"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.report.IBangKeHoaDonSpList"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "org.w3c.dom.NodeList;" %>

<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatter_Gia = new DecimalFormat("#,###,###.####"); %>

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
	
	ResultSet nhomhangERPRs = obj.getRsnhomhangERPid();
	ResultSet nhomkhERPRs = obj.getRsnhomkhERPid();
	ResultSet vungRs = obj.getVungRs();
	ResultSet kvRs = obj.getKvRs();
	ResultSet ttRs = obj.getTtRs();
	ResultSet nppRs = obj.getNppRs();
	ResultSet nhomRs = obj.getNhomRs();
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("BangKeHoaDonSpTTSvl","&view=TT");
	
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

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
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
	document.forms['ckParkForm'].action.value='search';
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
	<form name="ckParkForm" method="post" action="../../BangKeHoaDonSpTTSvl">
		<input type="hidden" name="action" value=''>
		<input type="hidden" name="userId" value='<%=userId%>'>
		
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
					<legend class="legendtitle">Doanh Số theo sản phẩm </legend>
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
						   
						   
						 <%--   <TR>
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
									
								</TR> --%>
								
								
								
								
								<TR>
									<TD class="plainlabel"> Nhóm hàng  </TD>
									<TD class="plainlabel">
										<select name="nhomId" id="nhomId" style="width:250px"   onchange="submitform();" >
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
									<TD class="plainlabel"> Sản phẩm </TD>
									<TD class="plainlabel">
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
									
										<TR>
										<TR>
									<TD class="plainlabel"> Khách hàng </TD>
									<TD class="plainlabel" colspan="3">
										<select name="khId" id="khId" style="width:250px"  onchange="submitform();">
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
								</TR>
							<%-- <TD class="plainlabel">Nhóm doanh thu</TD>
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
							
						</TR>	
									
									<TD class="plainlabel"  style="display:none">Loại đơn hàng </TD>
									<TD class="plainlabel" colspan="3" style="display:none" >
										<select name="loaidonhang"  onchange="submitform();" >
                            	<option value="" ></option>
                            	<% if(obj.getLoaiHoaDon().equals("0")) { %>
                            		<option value="0" selected="selected" >Hàng bán</option>
                            	<% } else { %>
                            		<option value="0" >Hàng bán</option>
                            	<% } %>
                            	<% if(obj.getLoaiHoaDon().equals("1")) { %>
                            		<option value="1" selected="selected" >Hàng khuyến mại</option>
                            	<% } else { %>
                            		<option value="1" >Hàng khuyến mại</option>
                            	<% } %>
                            </select>
									</TD>
								</TR> --%>
								
								<%-- <TR>
									<TD class="plainlabel">Bao gồm CN/ĐT </TD>
									<TD class="plainlabel">
							<%
								if(obj.getMucCN_DT().equals("1")){ %>
								<input type="checkbox" name="cndt" value="1" checked="checked"  /> CN/DT &nbsp; &nbsp;
							<% }else { %>
								    <input type="checkbox" name="cndt" value="1"  /> CN/DT &nbsp; &nbsp;
							 <%} %>
						</TR> --%>
								
								
					
				<%
					double DoanhSo=0;
					double Thue=0;
					double DoanhThu=0;
					if(totalRs!=null)
					while( totalRs.next() ) 
					{
						
						
						DoanhSo+=totalRs.getDouble("BVAT");
						Thue+=totalRs.getDouble("VAT");
						DoanhThu+=totalRs.getDouble("AVAT");
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
					
					
					<TR style="display: none;">
					
						<TD class="plainlabel"  colspan="6" >
						
						Không lấy nhóm nhập khẩu   
						<select name="klaynhomnk" id="klaynhomnk" style="width:250px"    >  
							<option value="0" <%if(obj.getLaynk().equals("0")) {%>  selected="selected"<%} %> >tất cả</option>
							<option value="1" <%if(obj.getLaynk().equals("1")) {%> selected="selected" <%} %>>Sản phẩm Traphaco</option>
							<option value="2" <%if(obj.getLaynk().equals("2")) {%> selected="selected" <%} %>>Nhóm SP Sandoz </option>
								
						</select>
						</TD>
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
													<th  width="8%"  align="center"> MÃ VẬT TƯ</th>
													<th  width="25%"  align="center"> TÊN VẬT TƯ</th>
													<th  width="8%" align="left">ĐVT</th>
													<th  width="8%"  align="right"> SỐ LƯỢNG </th>
													<th  width="11%" align="right"> ĐƠN GIÁ </th>
													<th  width="11%" align="right"> DOANH THU </th>
													<th  width="11%" align="right">THUẾ GTGT</th>
													<th  width="11%" align="right"> TỔNG TIỀN</th>
												
												</TR>
																	
								<%
					 /* DoanhSo=0;
					 Thue=0;
					 DoanhThu=0; */
					
					%>
								
													
												<%
												if(hdRs!=null){
													try{
														int m = 1;
														String lightrow = "tblightrow";
														String darkrow = "tbdarkrow";
														int i=0;
														while(hdRs.next() ){
															
															/* DoanhSo +=hdRs.getDouble("BVAT");
															Thue +=hdRs.getDouble("VAT");
															DoanhThu +=hdRs.getDouble("AVAT"); */
															
															if (m % 2 != 0) {%>						
															<TR class= <%=lightrow%> >
														<%} else {%>
															<TR class= <%= darkrow%> >
														<%}%>
															<Td align="center"><%= i+1 %></Td>
															<Td align="left"><%=hdRs.getString("spMa") %></Td>
															<Td align="left"><%=hdRs.getString("spTen") %></Td>
															<Td align="left"><%=hdRs.getString("spDonVi")  %></Td>
															<Td align="right"><%= formatter.format(hdRs.getDouble( "SoLuong" )) %></Td>
															<Td align="right"><%= formatter_Gia.format(( hdRs.getDouble( "BVAT" )   )/(hdRs.getDouble( "SoLuong" ) )  ) %></Td>
															<Td align="right"><%= formatter.format(( hdRs.getDouble( "BVAT" )  )) %></Td>
															<Td align="right"><%= formatter.format( hdRs.getDouble( "VAT" ) ) %></Td>
															<Td align="right"><%= formatter.format( hdRs.getDouble( "AVAT" ) ) %></Td>
															
														
												<%i++; m++;}}catch(Exception e){}} %>
												</TR>
												<tr class="tbfooter">
													<Td align="center" colspan="6" >TỔNG CỘNG</Td>
													<Td align="right"><%= formatter.format(DoanhSo  ) %></Td>
													<Td align="right"><%= formatter.format(  Thue  )  %></Td>
													<Td align="right"><%= formatter.format( DoanhThu ) %></Td>
												</TR>
												
												 <%-- <tr class="tbfooter">
														<TD align="center" valign="middle" colspan="13"
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
												 --%>
												
											</TABLE>
										</div>
		
	</div>
	</form>
	<%
		if(totalRs!=null){totalRs.close();}
		if(hdRs!=null){hdRs.close();}
		if(obj!=null){obj.closeDB();
		obj = null;}
		session.setAttribute("obj", null);
	%>
</body>
</html>