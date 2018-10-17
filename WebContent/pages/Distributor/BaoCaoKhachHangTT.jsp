
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet rsKenh = obj.getkenh();
	ResultSet khuvucs = obj.getkhuvuc();
	ResultSet vungs = obj.getvung();
	ResultSet npps = obj.getnpp();
	 ResultSet hch = (ResultSet)obj.GetRsHangCuahang();
	 ResultSet vtch = (ResultSet)obj.GetRsVitriCuahang(); 
	 ResultSet lch = (ResultSet)obj.GetRsLoaicuahang();
	 ResultSet rstinhthanh = (ResultSet)obj.GetRsTinhThanh(); 
	ResultSet rsquanhuyen= (ResultSet)obj.GetRsQuanHuyen();
	

%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OneOne - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery-1.js"></script>
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>

<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
	function search(){
		document.forms["frm"]["action"].value = "search";
		document.forms["frm"].submit();
	}	
	
	function submitform() {
		
		document.forms["frm"]["action"].value = "create";
		document.forms["frm"].submit();
		
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");		
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}		
	};
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BaoCaoKhachHangTT">
		<input type="hidden" value="1" name="action"  >
		<input type="hidden" name="nppId" value="<%= obj.getnppId() %>" >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị &#62; Báo cáo khác &#62; Báo cáo khách hàng</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn, &nbsp; <%=userTen %></div>
			</div>
			
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMsg() %>
					</textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo khách hàng</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								
								<TR>
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select name="vungid"  onchange="search();">
											<option value="" selected>All</option>
											<%if (vungs != null)
													while (vungs.next()) {
														if (vungs.getString("pk_seq").trim().equals(obj.getvungId())) {%>
													<option value="<%=vungs.getString("pk_seq")%>" selected><%=vungs.getString("ten")%></option>
												<%} else {%>
													<option value="<%=vungs.getString("pk_seq")%>"><%=vungs.getString("ten")%></option>
											<%}}%>
										</select>
									</TD>
									<TD class="plainlabel"> </TD>
									<TD class="plainlabel">
										
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
										<select name="khuvucid" name="khuvucId" onchange="search();">
											<option value="" selected>All</option>
											<%if (khuvucs != null)
													while (khuvucs.next()) {
														if (khuvucs.getString("pk_seq").trim().equals(obj.getkhuvucId())) {%>
															<option value="<%=khuvucs.getString("pk_seq")%>" selected><%=khuvucs.getString("ten")%></option>
													<%} else {%>
														<option value="<%=khuvucs.getString("pk_seq")%>"><%=khuvucs.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
								</TR>
									<TR>	
									<TD class="plainlabel">Vị trí cửa hàng</TD>
	                                <TD  class="plainlabel"><SELECT name="vtchid" onChange = "search();">
    		                            <option value=""> </option>
            		                    <% try{ while(vtch.next()){ 
								    			if(vtch.getString("pk_seq").equals(obj.GetIdVitriCuahang())){ %>
								      				<option value='<%=vtch.getString("pk_seq")%>' selected><%=vtch.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=vtch.getString("pk_seq")%>'><%=vtch.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
                                        </SELECT></TD>
									
									<TD class="plainlabel">Kênh bán hàng</TD>
									<TD class="plainlabel"><select onchange="search();"  name="kenhid" >
											<option value="">All</option>
											<% if(rsKenh!=null){
													while(rsKenh.next()){
														String kenhId = rsKenh.getString(1);
														if(kenhId.equals(obj.getkenhId())){
															%>
															<option selected="selected" value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
														<%}else{%>	
															<option value="<%=rsKenh.getString(1) %>"><%=rsKenh.getString(2)%></option>
											<%}}}%>
									</select></TD>											
								</TR>
								<TR>
																		
								
									<TD class="plainlabel">Loại cửa hàng</TD>
								  	<TD  class="plainlabel"><SELECT name="lchid"  onChange = "submitform();">
								    	<option value=""> </option>
								    	<% try{ while(lch.next()){ 
								    			if(lch.getString("pk_Seq").equals(obj.GetIdLoaicuahang())){ %>
								      				<option value='<%=lch.getString("pk_seq")%>' selected><%=lch.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=lch.getString("pk_seq")%>'><%=lch.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
										</SELECT>
									</TD>
									<TD width="16%" class="plainlabel">Hạng cửa hàng</TD>
 
									<TD width="38%" class="plainlabel">
										<SELECT name="hchid" onChange = "search();">
										  <option value=""> </option>
										  <% try{ while(hch.next()){ 
								    			if(hch.getString("pk_seq").equals(obj.GetIdHangCuahang())){ %>
								      				<option value='<%=hch.getString("pk_seq")%>' selected><%= hch.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=hch.getString("pk_seq")%>'><%= hch.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
								  </TD>
								</TR>								
								<TR>
																		
								
									<TD class="plainlabel">Tỉnh thành</TD>
								  	<TD  class="plainlabel"><SELECT name="tinhthanhid"  onChange = "search();">
								    	<option value=""> </option>
								    	<% try{ while(rstinhthanh.next()){ 
								    			if(rstinhthanh.getString("pk_Seq").equals(obj.GetIdTinhThanh())){ %>
								      				<option value='<%=rstinhthanh.getString("pk_seq")%>' selected><%=rstinhthanh.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rstinhthanh.getString("pk_seq")%>'><%=rstinhthanh.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>
										</SELECT>
									</TD>
									<TD width="16%" class="plainlabel">Quận huyện</TD>
 
									<TD width="38%" class="plainlabel">
										<SELECT name="quanhuyenid" onChange = "search();">
										  <option value=""> </option>
										  <% try{ while(rsquanhuyen.next()){ 
								    			if(rsquanhuyen.getString("pk_seq").equals(obj.GetIdQuanHuyen())){ %>
								      				<option value='<%=rsquanhuyen.getString("pk_seq")%>' selected><%= rsquanhuyen.getString("ten") %></option>
								      			<%}else{ %>
								     				<option value='<%=rsquanhuyen.getString("pk_seq")%>'><%= rsquanhuyen.getString("ten") %></option>
								     			<%}}}catch(java.sql.SQLException e){} %>	 
                                        </SELECT>
								  </TD>
								</TR>			
								<TR>
									<TD colspan="2">										
											
									    <a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
								</TR>
							</TABLE>
						</div>												
						
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />		
	</form>
</body>
</html>
<%
	try
	{
		
		if(rsKenh != null)
			rsKenh.close();
		if(khuvucs != null)
			khuvucs.close();
		if(vungs != null)
			vungs.close();
		
		if(npps != null)
			npps.close();
		
		if(obj != null){
			obj.DBclose();
			obj = null;
		}
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	}
%>