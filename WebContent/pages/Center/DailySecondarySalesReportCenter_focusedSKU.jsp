<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String groupCustomer = (String)session.getAttribute("groupCustomer");
	
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
	ResultSet kenh = obj.getkenh();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet npp = obj.getnpp();
	ResultSet dvkd = obj.getdvkd();
	ResultSet nsp = obj.GetRsNhomSP();
	ResultSet dvdl = obj.getdvdl();
	ResultSet sanpham = obj.getsanpham();
	ResultSet gsbh = obj.getgsbh();
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
	$(document).ready(function() {
		$(".button1").hover(function() {
			$(".button1 img").animate({
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

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">
	function search() {
		document.forms['frm'].action.value = 'search';
		document.forms["frm"].submit();
	}

	function submitform() 
	{
		document.forms['frm'].action.value = 'Taomoi';
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
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


</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../DailySecondarySalesTT_focusedSKUSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=obj.getuserId()%>'>

			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62; Doanh số &#62; Doanh số SKU tập trung</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=obj.getuserTen()%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle">Thông báo</legend>
					<textarea id="errors" name="errors" rows="1"  style="width: 100% ; color:#F00 ; font-weight:bold">
						<%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset >
					<legend class="legendtitle">Bán hàng hàng ngày</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel">
										<input type="text" name="Sdays"	class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td>
										<input type="text" name="Edays" class="days" value='<%=obj.getdenngay()%>' /></td>
								</TR>
								<TR>
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel">
										<select name="kenhId" id="kenhId" onchange="seach();">
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
									<TD class="plainlabel">PTT / GĐ CN 2</TD>
									<TD class="plainlabel">
										<select name="gsbhs" id="gsbhsId"
											onchange="seach();">
												<option value="" selected>All</option>
												<%if (gsbh != null)
														while (gsbh.next()) {
															if (gsbh.getString("pk_seq").equals(obj.getgsbhId())) {%>
														<option value="<%=gsbh.getString("pk_seq")%>" selected>
															<%=gsbh.getString("ten")%></option>
												<%} else {%>
														<option value="<%=gsbh.getString("pk_seq")%>"><%=gsbh.getString("ten")%></option>
												<%}}%>
										</select>
									</td>
								</TR>
								<TR>
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();">
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
									<TD class="plainlabel">Chi nhánh / Đối tác </TD>
									<TD class="plainlabel">
										<select name="nppId" onchange="seach();" id="nppId">
											<option value="" selected>All</option>
											<%if (npp != null)
													while (npp.next()) {
														if (npp.getString("pk_seq").equals(obj.getnppId())) {%>
															<option value="<%=npp.getString("pk_seq")%>" selected><%=npp.getString("ten")%></option>
													<%} else {%>
														<option value="<%=npp.getString("pk_seq")%>"><%=npp.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
								</TR>
								<TR>
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel" >
										<select name="khuvucId" id="khuvucId" onchange="seach();">
											<option value="" selected>All</option>
											<%if (khuvuc != null)
													while (khuvuc.next()) {
														if (khuvuc.getString("pk_seq").equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvuc.getString("pk_seq")%>" selected><%=khuvuc.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvuc.getString("pk_seq")%>"><%=khuvuc.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel">
										
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Đơn vị kinh doanh</TD>
									<TD class="plainlabel" colspan="3">
										<select name="dvkdId" id="dvkdId"	onchange="seach();">
											<option value="" selected>All</option>
											<%if (dvkd != null)
													while (dvkd.next()) {
														if (dvkd.getString("pk_seq").equals(obj.getdvkdId())) {%>
														<option value="<%=dvkd.getString("pk_seq")%>" selected><%=dvkd.getString("diengiai")%></option>
													<%} else {%>
														<option value="<%=dvkd.getString("pk_seq")%>"><%=dvkd.getString("diengiai")%></option>
													<%}}%>
										</select>
									</TD>

								</TR>							
								<TR>									
									<TD class="plainlabel">Bao gồm chiết khấu</TD>
									<TD class="plainlabel" colspan="3">
										<%
											if(obj.getdiscount().equals("1")){%>
												<input type="radio" name="discount"	value="1"  checked="checked" />Có &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0" />Không
											<%}else{%>
												<input type="radio" name="discount"	value="1"  />Có &nbsp;&nbsp;&nbsp; 
												<input type="radio" name="discount" value="0"  checked="checked"/>Không
											<%}%>										
									</TD>									
								</TR>
							
									<TR>
									<TD width="19%" class="plainlabel" valign="top">Nhóm Sản Phẩm</TD>
								  	<TD class="plainlabel" colspan="3" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR>
											<TD>
											
											 <ul >
											 	
											 <%
											 String wheresku = (String)session.getAttribute("checkedSKU");
											 String[] checkedSKU=wheresku.split(",");
				                           	 if(checkedSKU==null){
											 while(nsp.next())

				                               {%>
			                            	  	<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" name="check"  onchange="recheck();" value ="<%=nsp.getString("pk_seq") %>"> <%=nsp.getString("pk_seq")+ " -" + nsp.getString("diengiai")%> </label>  <input name ="skutest"  type="hidden" value="<%=nsp.getString("pk_seq") %>"></li>
				                              <%}
				                           	 }else{
				                            	while (nsp.next()){
				                            		int i=0;
				                            		for (i=0;i<checkedSKU.length;i++){
				                            			if(checkedSKU[i].trim().equals(nsp.getString("pk_seq").trim())){
				                            				break;
				                            			}
				                            		}
				                            		if(i< checkedSKU.length){
				                            			 {%>
						                            	  	<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" checked="checked" name="check"  onchange="recheck();" value ="<%=nsp.getString("pk_seq") %>"> <%=nsp.getString("pk_seq")+ " -" + nsp.getString("diengiai")%> </label>  <input name ="skutest"  type="hidden" value="<%=nsp.getString("pk_seq") %>"></li>
							                              <%}
				                            		}else{
				                            			 {%>
						                            	  	<li><label  for="check" > <input type ="hidden" value="" name="valuechecked"><input type="checkbox" name="check"  onchange="recheck();" value ="<%=nsp.getString("pk_seq") %>"> <%=nsp.getString("pk_seq")+ " -" + nsp.getString("diengiai")%> </label>  <input name ="skutest"  type="hidden" value="<%=nsp.getString("pk_seq") %>"></li>
							                              <%}
				                            		}
				                            		
				                            	}
				                            }
											 %>  

											  </ul>
                              	   										</TD>
                                    		</TR>
								</TABLE>									
								</TD>							
								</TR>	
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a></td>
								</TR>
								
							</TABLE>
						</div>
			</div>
			</fieldset>
		</div>
		
		<br /> <br /> <br /> <br />
	</form>
	<%
		if(kenh !=null) kenh.close();
		if(vung!=null)	vung.close();
		if(khuvuc!=null)	khuvuc.close();
		if(npp!=null)	npp.close();
		if(dvkd!=null)	dvkd.close();
		if(nsp!=null)	nsp.close();
		if(dvdl!=null)	dvdl.close();
		if(sanpham!=null)	sanpham.close();
		if(gsbh!=null)	gsbh.close();
		if(obj!=null){obj.DBclose();
		obj = null;}
		session.setAttribute("obj",null);
	%>
</body>
</html>