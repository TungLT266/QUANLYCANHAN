<%-- <%@page import="com.sun.org.apache.regexp.internal.REUtil"%> --%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<% NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<% NumberFormat formatter2 = new DecimalFormat("#,###,###.##");  %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen"); 
	String sum = (String) session.getAttribute("sum");
	
	
	Utility util = (Utility) session.getAttribute("util");
	
	String url="";
	url = util.getUrl("DistributionTT_Svl","");
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet npp = obj.getnpp();
	ResultSet ddkds = obj.getRsddkd();
	ResultSet sku = obj.getsanpham();
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet ttRs = obj.getTtRs();
	ResultSet spRs=obj.getSpRs();
	ResultSet chungloai=obj.getchungloai();
	ResultSet dvkd=obj.getdvkd();
	ResultSet nhomsp=obj.GetRsNhomSP();
	String wheresku =obj.getsanphamId();
	ResultSet rsBaocao = obj.getRSBaocao();
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<link href="../css/chosen.css" rel="stylesheet" type="text/css" />
<script src="../scripts/chosen.jquery.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {
		$(".days").datepicker({
			changeMonth : true,
			changeYear : true
		});
	});
	jQuery(document).ready(function()
	{
		$("select:not(.notuseselect2)").chosen();     
		
	});
</script>

  <style type="text/css">
   ul { height: 100px; overflow:auto; width: 100px; border: 1px solid #000; }
   ul { list-style-type: none; margin: 0; padding: 0; overflow-x: hidden; width: 298px }
   li { margin: 0; padding: 0; }
   label { display: block; color: WindowText; background-color: Window; margin: 0; padding: 0; width: 100%; }
   label:hover { background-color: Highlight; color: HighlightText; }
  </style>

	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<SCRIPT language="javascript" type="text/javascript">

	function seach() {
		document.forms['rpForm'].action.value = 'seach';
		document.forms["rpForm"].submit();
	}
	
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}

	function submitform()
	{
		if (document.forms["rpForm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["rpForm"]["denngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
		
		document.forms['rpForm'].action.value = 'create';
		document.forms['rpForm'].dataerror.value="";
		document.forms['rpForm'].submit();
		
	}
	function getdata(){
		if (document.forms["rpForm"]["tungay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return;
		}
		if (document.forms["rpForm"]["denngay"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return;
		}
		
		document.forms['rpForm'].action.value = 'getdata';
		document.forms['rpForm'].dataerror.value="";
		document.forms['rpForm'].submit();
	}
	function checkall(value){
		var checkone=document.getElementsByName("checkkhuvuc1");
		var giatricheck=document.getElementsByName("checkkhuvuc");
		var chuoi;
		if(value==true){
			chuoi="1";
		}else{
			chuoi="0";
		}
		for(var i=0;i<checkone.length;i++ ){
			checkone.item(i).checked=value;
			giatricheck.item(i).value=chuoi;
		}
	}
	function recheck(){
		var checkone=document.getElementsByName("check");
		var giatricheck=document.getElementsByName("valuechecked");
		for(var i=0;i<checkone.length;i++ ){
			if(checkone.item(i).checked==true){
				giatricheck.item(i).value="1";
			}else {
				giatricheck.item(i).value="0";
			}
			
				
		}
	}
	function recheckall(value){
		 
		var check=document.getElementsByName("check");
		for(var i=0;i<check.length;i++ ){
			check.item(i).checked=value;
		}
	}
	 
</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$(document).ready(function()
	{
		$(".select2").select2();
	});
});
</script>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../DistributionTT_Svl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;<%=url %> </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					
 					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold" id="errors" style="width: 100%" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>	
				<TR>
					<TD>
						<TABLE border="0" width="100%" cellpadding="0" cellspacing="0">
							<TR>
								<TD width="100%" align="center" >
								<FIELDSET>
								  <LEGEND class="legendtitle">Thời gian xuất báo cáo</LEGEND>
								<TABLE  width="100%" cellpadding="5"  cellspacing="0">
									<TR>
									  	<TD class="plainlabel">Từ ngày</TD>
									  	<TD class="plainlabel" width="350px">
											<input type="text" class="days" name="tungay" size="20" value = "<%=obj.gettungay()%>" >																		
										</TD>																		
									
										<TD class="plainlabel" >Đến ngày </TD>
									  	<TD class="plainlabel" >
								  			<input type="text" name="denngay" class="days" size="20" value = "<%=obj.getdenngay()%>" >						
										</TD>
									</tr>
									<tr>
										<TD class="plainlabel">Miền</TD>
										<TD class="plainlabel" >
											<select name="vungId" id="vungId" onchange="seach();" style="width:300px">
												<option value="" selected>All</option>
												<%if (vung != null)
														while (vung.next()) {
															if (vung.getString("pk_seq").equals(obj.getvungId())) {%>
														<option value="<%=vung.getString("pk_seq")%>" selected><%=vung.getString("ten")%></option>
													<%} else {%>
														<option value="<%=vung.getString("pk_seq")%>"><%=vung.getString("ten")%></option>
												<%}}%>
											</select>
										</TD>
										<TD class="plainlabel">Kênh bán hàng</TD>
										<TD class="plainlabel" >
											<select name="kenhId" id="kenhId" onchange="seach();" style="width:300px">
												<option value="" selected>All</option>
												<%if (kenh != null)
														while (kenh.next()) {
															if (kenh.getString("pk_seq").equals(obj.getkenhId())) {%>
															<option value="<%=kenh.getString("pk_seq")%>" selected><%=kenh.getString("diengiai")%></option>
												<%} else { %>
													<option value="<%=kenh.getString("pk_seq")%>"><%=kenh.getString("diengiai")%></option>
												<%}}%>
											</select>
										</TD>
										<%-- <TD class="plainlabel">Đơn vị kinh doanh</TD>
										<TD class="plainlabel">
											<select name="dvkdId" id="dvkdId"	onchange="seach();" style="width:250px">
												<option value="" selected>All</option>
												<%if (dvkd != null)
														while (dvkd.next()) {
															if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
															<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
														<%} else {%>
															<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
														<%}}%>
											</select>
										</TD> --%>
									</tr>
									<%-- <tr>
										
										
										
										<TD class="plainlabel">Nhóm sản phẩm</TD>
										<TD class="plainlabel" colspan="3">
											<select name="nhomspid" onchange="seach();"  id="nhomspid" style="width:250px">
												<option value="" selected>Tất cả</option>
												<%if (chungloai != null)
														while (nhomsp.next()) {
															if (nhomsp.getString("pk_seq").equals(obj.GetNhoSPId())) {%>
															<option value="<%=nhomsp.getString("pk_seq")%>" selected><%=nhomsp.getString("diengiai")%></option>
														<%} else {%>
															<option value="<%=nhomsp.getString("pk_seq")%>"><%=nhomsp.getString("diengiai")%></option>
													<% }}%>
											</select>
										</TD>	
									</tr> --%>
									<tr>
										<%-- <TD class="plainlabel">Khu Vực</TD>
										<TD class="plainlabel">
											<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:250px">
												<option value="" selected>All</option>
												<%if (khuvuc != null)
														while (khuvuc.next()) {
															if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
																<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
														<%} else {%>
															<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
														<%}}%>
											</select>
										</TD> --%>
										<TD class="plainlabel">Địa bàn  </TD>
										<TD class="plainlabel">
											<select name="ttId" id="ttId" onchange="seach();"  style="width:300px">
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
										<TD class="plainlabel">Sản phẩm</TD>
										<TD class="plainlabel">
											<select name="spId" id="spId" style="width:300px"  multiple="multiple">
													<%if (spRs != null)
															while (spRs.next()) {
																if (obj.getsanphamId().indexOf(spRs.getString("pk_seq")) >=0){	%>
																<option value="<%=spRs.getString("pk_seq")%>" selected><%=spRs.getString("ma") + " - " +spRs.getString("ten")%></option>
															<%} else {%>
																<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ma") + " - "+spRs.getString("ten")%></option>
													<%}}%>
											</select>
										</TD>
									</tr>			
													
									<TR>
									    <TD class="plainlabel">Chi nhánh / Đối tác </TD>
									      <TD class="plainlabel">
									      	<SELECT name="nppId" id="nppId" style="width:300px;" onchange="seach();">	
									      	<option value="">All</option>							      
									  	 	<%if(npp != null) try{ while(npp.next()){ 
									    	if(npp.getString("pk_seq").trim().equals(obj.getnppId())){ %>
									      		<option value='<%=npp.getString("pk_seq") %>' selected><%=npp.getString("ten") %></option>
									      	<%}else{ %>
									     		<option value='<%=npp.getString("pk_seq") %>'><%=npp.getString("ten") %></option>
									     	<%}}}catch(java.sql.SQLException e){} %>	
	                                  </select>
								  		</TD>
								  		<%-- <TD class="plainlabel"><!-- Chủng loại --></TD>
										<TD class="plainlabel">
											<select name="chungloaiId" id="chungloaiId" onchange="seach();" style="width:250px">
												<option value="" selected>All</option>
												<%if (chungloai != null)
														while (chungloai.next()) {
															if (chungloai.getString("pk_seq").equals(obj.getchungloaiId())) {%>
															<option value="<%=chungloai.getString("pk_seq")%>" selected><%=chungloai.getString("ten")%></option>
														<%} else {%>
															<option value="<%=chungloai.getString("pk_seq")%>"><%=chungloai.getString("ten")%></option>
													<% }}%>
											</select> 
										</TD>--%>
									
										<TD class="plainlabel">Trình dược viên</TD>
										<TD class="plainlabel">
											<select name="ddkdId" id ="ddkdId" style="width:300px;">
													<option value="" selected>All</option>
													<%if (ddkds != null)
															while (ddkds.next()) {
																if (ddkds.getString("pk_seq").equals(obj.getDdkd())) {%>
															<option value="<%=ddkds.getString("pk_seq")%>" selected>
																<%=ddkds.getString("ten")%></option>
													<%} else {%>
															<option value="<%=ddkds.getString("pk_seq")%>"><%=ddkds.getString("ten")%></option>
													<%}}%>
											</select>
										</td>
									</tr>			
										
									<TR>
										<TD class="plainlabel"></TD>
										<TD class="plainlabel" colspan="3">
										<fieldset>
										<legend>Tiêu chí</legend> 
										 <% if(obj.gettype().equals("0")){ %>
											<input type="radio" name="typeid" onchange="Laytheokh();" value="0"  checked="checked"/>Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
											<input type="radio" name="typeid" onchange="LayThethoigian();" value="1"/>Theo thời gian
											<input type="radio" name="typeid"  value="2" />Độ phủ theo nhiều sản phẩm
										<%}else if(obj.gettype().equals("1")){ %>
											<input type="radio" name="typeid" onchange="Laytheokh();" value="0"/>Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
											<input type="radio" name="typeid" onchange="LayThethoigian();" value="1"  checked="checked" />Theo thời gian
											<input type="radio" name="typeid"  value="2" />Độ phủ theo nhiều sản phẩm
										<%}else{ %>
											<input type="radio" name="typeid" onchange="Laytheokh();" value="0"  />Lấy theo khách hàng &nbsp;&nbsp;&nbsp;
											<input type="radio" name="typeid" onchange="LayThethoigian();" value="1" />Theo thời gian
											<input type="radio" name="typeid"  value="2" checked="checked"  />Độ phủ theo nhiều sản phẩm
										<%} %>
										</fieldset>
										</TD>
									</TR>
									
									<TR>
										<TD class="plainlabel"></TD>
										<TD class="plainlabel" colspan="3">
										<fieldset>
										<legend>Lấy theo</legend> 
										 <% if(obj.getLaytheo().equals("0")){ %>
											<input type="radio" name="laytheo" value="0"  checked="checked"/>Tổng quát&nbsp;&nbsp;&nbsp;
											<input type="radio" name="laytheo" value="1"/>Chi tiết
										<%}else if(obj.getLaytheo().equals("1")){ %>
											<input type="radio" name="laytheo" value="0"/>Tổng quát&nbsp;&nbsp;&nbsp;
											<input type="radio" name="laytheo" value="1"  checked="checked" />Chi tiết
										<%} %>
										</fieldset>
										</TD>
									</TR>
									
									 <TR>
										<TD colspan="4" class="plainlabel">
										<a class="button2" href="javascript:submitform()" >
											<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>&nbsp;&nbsp;&nbsp;&nbsp;  
											
											<a class="button2" href="javascript:getdata()" >
											<img style="top: -4px;" src="../images/button.png" alt="">Xem dữ liệu</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    </TD>
									</TR>
									
									<%-- <tr  class="plainlabel">
									<td>
									</td>
									<td  colspan="4">
											<input type="checkbox" name="checkall"  id="checkall"  onchange="recheckall(this.checked);"  > Chọn tất cả
												  
									</td>
									</tr>
									<TR>	
										<TD width="19%" class="plainlabel" valign="top">Sản Phẩm</TD>
									  	<TD class="plainlabel" colspan="4" >
												 <ul style="height: 700px">
												 <%
												
												 if(wheresku==null){
													 wheresku="";
												 } 
												 
												 System.out.println("Chuoi nay nek : "+wheresku);
												 int i=0;
					                            	while (sku.next()){
					                            		
					                            		if(wheresku.indexOf(sku.getString("pk_seq").trim()) >=0 ){
				                            			  %>
						                            	  	<li><label  for="check<%=i%>"  >  <input type="checkbox"  id="check<%=i%>" checked="checked" name="check"    value ="<%=sku.getString("pk_seq") %>"> <%=sku.getString("pk_seq")+ " -" + sku.getString("ten")%> </label>   </li>
							                              <% 
					                            		}else{
				                            			 %>
						                            	  	<li><label for="check<%=i%>" > <input type="checkbox" name="check" id="check<%=i%>"  onchange="recheck();" value ="<%=sku.getString("pk_seq") %>"> <%=sku.getString("ma")+ " -" + sku.getString("ten")%> </label>  </li>
							                              <% 
					                            		}
					                            		i++;
					                            	}
												 %>  
												  </ul>
	                              	   		    
	                                    	 
									 								
									</TD>							
									</TR> --%>
								   
								</TABLE>
								</FIELDSET>						
								</TD>
							</TR>
						</TABLE>					
					</TD>
				</TR>
				
				<TR>
					<TD>
						<FIELDSET>
							<LEGEND class="legendtitle">Dữ liệu</LEGEND>
							<TABLE width="100%" border="1" cellspacing="1px" cellpadding="3px">
								<%if(obj.getLaytheo().equals("1")){ %>
								<TR class="plainlabel">
									<th  width="3%"  align="center"> STT</th>
									<th  width="6%"  align="center">MIỀN</th>
									<th  width="7%"  align="center"> ĐỊA BÀN </th>
									<th  width="17%" align="center">CHI NHÁNH /ĐT</th>
									<th  width="20%" align="center">SẢN PHẨM</th>
									<th  width="25%" align="center">KHÁCH HÀNG</th>
									<th  width="10%"  align="center">SỐ LƯỢNG</th>
									<th  width="10%"  align="center">DOANH THU</th>
								</TR>
								<%
								if(rsBaocao!=null){
									try{
										int m = 1;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										while(rsBaocao.next()){
											int stt = rsBaocao.getInt("STT");
											
											if(stt == 1){%>
												<TR class= <%= darkrow%> >
													<Td align="center"></Td>
													<Td align="left"><%=rsBaocao.getString("Region") %></Td>
													<Td align="left"><%=rsBaocao.getString("tinhthanh") %></Td>
													<Td align="left"><%=rsBaocao.getString("Distributor") %></Td>
													<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
													<Td align="left"><%=rsBaocao.getString("SOKH") %></Td>
													<Td align="left"><%=rsBaocao.getString("TONGSOLUONG") %></Td>
													<Td align="right"><%= formatter.format(rsBaocao.getDouble("TONGDOANHSO")) %></Td>
												</TR>
												<TR class= <%=lightrow%> >
													<Td align="center"><%= m %></Td>
													<Td align="left"></Td>
													<Td align="left"></Td>
													<Td align="left"></Td>
													<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
													<Td align="left"><%=rsBaocao.getString("custommer") %></Td>
													<Td align="left"><%=rsBaocao.getString("SOLUONG") %></Td>
													<Td align="right"><%= formatter.format(rsBaocao.getDouble("VOLUME")) %></Td>
												</TR>									
											<%}else{%>
												<tr class= <%=lightrow%> >
													<Td align="center"><%= m %></Td>
													<Td align="left"></Td>
													<Td align="left"></Td>
													<Td align="left"></Td>
													<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
													<Td align="left"><%=rsBaocao.getString("custommer") %></Td>
													<Td align="left"><%=rsBaocao.getString("SOLUONG") %></Td>
													<Td align="right"><%= formatter.format(rsBaocao.getDouble("VOLUME")) %></Td>
												</tr>
											<%} %>	
										
									<% m++;}}catch(Exception e){}} %>
								<%} else{%>
								<TR class="plainlabel">
									<th  width="3%"  align="center"> STT</th>
									<th  width="6%"  align="center">MIỀN</th>
									<th  width="7%"  align="center"> ĐỊA BÀN </th>
									<th  width="17%" align="center">CHI NHÁNH /ĐT</th>
									<th  width="20%" align="center">SẢN PHẨM</th>
									<th  width="10%" align="center">Số lượng KH ký HĐ</th>
									<th  width="10%"  align="center">SL Khách hàng phủ</th>
									<th  width="5%"  align="center">Tỷ lệ phủ</th>
									<th  width="5%"  align="center">Số lượng</th>
									<th  width="10%"  align="center">Doanh thu</th>
								</TR>
								<%
								if(rsBaocao!=null){
									try{
										int m = 1;
										String lightrow = "tblightrow";
										String darkrow = "tbdarkrow";
										while(rsBaocao.next()){
											int stt = rsBaocao.getInt("STT");
											
											if(stt == 1){%>
												<TR class= <%= darkrow%> >
													<Td align="center"></Td>
													<Td align="left"></Td>
													<Td align="left"></Td>
													<Td align="left"></Td>
													<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
													<Td align="center"><%=rsBaocao.getString("TONGKHHD") %></Td>
													<Td align="center"><%=rsBaocao.getString("TONGSOKH") %></Td>
													<Td align="center"><%=formatter2.format(rsBaocao.getFloat("TONGSOKH")/ rsBaocao.getFloat("TONGKHHD") * 100)%> %</Td>
													<Td align="center"><%=rsBaocao.getString("TONGSOLUONG") %></Td>
													<Td align="right"><%= formatter.format(rsBaocao.getDouble("TONGDOANHSO")) %></Td>
												</TR>
												<TR class= <%=lightrow%> >
													<Td align="center"><%= stt %></Td>
													<Td align="left"><%=rsBaocao.getString("Region") %></Td>
													<Td align="left"><%=rsBaocao.getString("tinhthanh") %></Td>
													<Td align="left"><%=rsBaocao.getString("Distributor") %></Td>
													<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
													<Td align="center"><%=rsBaocao.getString("SOHD") %></Td>
													<Td align="center"><%=rsBaocao.getString("SOKH") %></Td>
													<Td align="center"><%=formatter2.format(rsBaocao.getFloat("SOKH")/ rsBaocao.getFloat("SOHD") * 100)%> %</Td>
													<Td align="center"><%=rsBaocao.getString("SOLUONG") %></Td>
													<Td align="right"><%= formatter.format(rsBaocao.getDouble("VOLUME")) %></Td>
												</TR>									
											<%}else{%>
												<tr class= <%=lightrow%> >
													<Td align="center"><%= stt %></Td>
													<Td align="left"><%=rsBaocao.getString("Region") %></Td>
													<Td align="left"><%=rsBaocao.getString("tinhthanh") %></Td>
													<Td align="left"><%=rsBaocao.getString("Distributor") %></Td>
													<Td align="left"><%=rsBaocao.getString("SKU") %></Td>
													<Td align="center"><%=rsBaocao.getString("SOHD") %></Td>
													<Td align="center"><%=rsBaocao.getString("SOKH") %></Td>
													<Td align="center"><%=formatter2.format(rsBaocao.getFloat("SOKH")/ rsBaocao.getFloat("SOHD") * 100)%> %</Td>
													<Td align="center"><%=rsBaocao.getString("SOLUONG") %></Td>
													<Td align="right"><%= formatter.format(rsBaocao.getDouble("VOLUME")) %></Td>
												</tr>
											<%} %>	
										
									<% m++;}}catch(Exception e){e.printStackTrace();}} %>
								<%} %>
							</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
	if(vung!=null) vung.close();		
	if(khuvuc!=null) khuvuc.close();	
	if(ttRs!=null) ttRs.close();
	if(kenh!=null) kenh.close();		
	if(npp!=null) npp.close();		
	if(sku!=null) sku.close();		 
	if(spRs!=null) spRs.close();		
	if(chungloai!=null) chungloai.close();	
	if(dvkd!=null) dvkd.close();	 
	if(nhomsp!=null) nhomsp.close();
	if(ddkds!=null) ddkds.close();
	if(rsBaocao != null) rsBaocao.close();
	if(obj!=null){ 
		obj.DBclose();
		obj = null;
	}
	session.setAttribute("obj", null);
	session.setAttribute("checkedSKU", null);
	session.setAttribute("loi", null);
		               	  			


	}%>