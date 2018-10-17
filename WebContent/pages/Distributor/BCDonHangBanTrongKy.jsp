<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.report.Ireport"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");

	ResultSet ddkd = obj.getRsddkd();
	ResultSet khoRs = obj.getkho();
	ResultSet kbhRs = obj.getkenh();
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("BCDonHangBanTrongKy", "&view=TT");
	
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

<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript">
		$(document).ready(function() {		
			$( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button1").hover(function(){
                $(".button1 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        });
		
    </script>
<script type="text/javascript"
	src="../scripts/report-js/action-report.js"></script>
<script type="text/javascript">
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length==0) {
			alert("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length==0) {
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		

		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	window.onload = function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
		}
	};
	function seach()
	{
		document.forms['frm'].action.value= 'seach';
		document.forms["frm"].submit();
	}
</script>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BCDonHangBanTrongKy">
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="view" value='NPP'>
		
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen %></div>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>

					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" value="<%= session.getAttribute("errors") %>" 
						name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo đơn hàng bán trong kỳ</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
										<TD class="plainlabel">Từ ngày</TD>
											<TD class="plainlabel">	<input type="text" name="Sdays" class="days" value='<%= obj.gettungay() %>' />
											</TD>
											<TD class="plainlabel">Đến ngày</TD>
											<TD class="plainlabel">
												<input type="text" name="Edays" class="days" value='<%= obj.getdenngay() %>'/>
											</TD>
									</TR>
								<TR>
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel"><select name="ddkdId" id="ddkdId" >
											<option value="" >All</option>
										<% if(ddkd !=null){ 
											while(ddkd.next())
											{ 
											if(ddkd.getString("pk_seq").equals(obj.getDdkd())) {
											%>
											<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
											<%} else { %>
											<option value="<%=ddkd.getString("pk_seq")%>" ><%=ddkd.getString("ten")%></option>
											<%}}} %>
											</select>
									</TD>
								</TR>
								
								<TR>
								  <TD class="plainlabel">Kênh</TD>
									<TD class="plainlabel"><select name="kbhId" >
											<option value="" >All</option>
										<% if(kbhRs !=null){ 
											while(kbhRs.next())
											{ 
											if(kbhRs.getString("pk_seq").equals(obj.getkenhId())) {
											%>
											<option value="<%=kbhRs.getString("pk_seq")%>" selected><%=kbhRs.getString("ten")%></option>
											<%} else { %>
											<option value="<%=kbhRs.getString("pk_seq")%>" ><%=kbhRs.getString("ten")%></option>
											<%}}} %>
											</select>
								  </TD>
								  <TD class="plainlabel">Kho</TD>
								  <TD class="plainlabel"><select name="khoId" id="khoId" >
										<option value="" >All</option>
									<% if(khoRs !=null){ 
										while(khoRs.next())
										{ 
										if(khoRs.getString("pk_seq").equals(obj.getkhoId())) {
										%>
										<option value="<%=khoRs.getString("pk_seq")%>" selected><%=khoRs.getString("ten")%></option>
										<%} else { %>
										<option value="<%=khoRs.getString("pk_seq")%>" ><%=khoRs.getString("ten")%></option>
										<%}}} %>
										</select>
									</TD>
							  
							  </TR>
								
							 <TR>
							 	<TD class="plainlabel"></TD>
							 	<TD class="plainlabel" colspan="3" >
							 		
							 		<% if(obj.getDongiagoc().equals("1")) {%> 
										<input type="checkbox" name="laygiaGoc" value="1" checked="checked" /> Đơn giá có VAT  &nbsp; &nbsp; 
									<% }else {%> 
										<input type="checkbox" name="laygiaGoc" value="1" /> Đơn giá có VAT  &nbsp; &nbsp; <% } %>
								
									&nbsp;&nbsp;&nbsp;
									
									<%-- <% if(obj.getChuyenSale().equals("1")) {%> 
										<input type="checkbox" name="laychuyenSale" value="1" checked="checked" /> Lấy chuyển sale &nbsp; &nbsp; 
									<% }else {%> 
										<input type="checkbox" name="laychuyenSale" value="1" /> Lấy chuyển sale &nbsp; &nbsp; <% } %>
										
									&nbsp;&nbsp;&nbsp; --%>
									
									<% if(obj.getKhuyenmai().equals("1")) {%> 
										<input type="checkbox" name="laykhuyenMai" value="1" checked="checked" /> Lấy khuyến mại &nbsp; &nbsp; 
									<% }else {%> 
										<input type="checkbox" name="laykhuyenMai" value="1" /> Lấy khuyến mại &nbsp; &nbsp; <% } %>
							 		
							 	</TD>
							 </TR>	
						
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
								</TR>
								
							</TABLE>
						</div>
						<hr>
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
		if(!(ddkd == null))
			ddkd.close();
		if(!(kbhRs == null))
			kbhRs.close();
		
		 if(obj != null){
	    obj.DBclose();	   
		;} 
		session.setAttribute("obj",null);
	}catch(java.sql.SQLException e){}
	
%>