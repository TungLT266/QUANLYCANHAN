<%@page import="geso.traphaco.center.beans.chitieunhanvienetc.IChiTieuNhanvienETC"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="geso.traphaco.center.util.Utility"%>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
	<%
		IChiTieuNhanvienETC obj = (IChiTieuNhanvienETC) session.getAttribute("obj");
		ResultSet kenhRs = obj.getKbhRs();
		ResultSet nhanvienRs = obj.getNhanvienRs();
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
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>

<script language="javascript" type="text/javascript">	

	function TaoBaoCao() 
	{
		var thang = document.getElementById("thang").value;
		var nam = document.getElementById("nam").value;
		var loaichitieu = document.getElementById("loaichitieu").value;
		
		if( thang == '' )
		{
			alert('Vui lòng chọn tháng');
			return;
		}
		if( nam == '' )
		{
			alert('Vui lòng chọn năm');
			return;
		}
		if( loaichitieu == '' )
		{
			alert('Vui lòng chọn loại chỉ tiêu');
			return;
		}
		
		document.forms['frm'].action.value= 'Taomoi';
		document.forms["frm"].submit();
	}
	
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BCThucDatChiTieuETC">
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="view" value='TT'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý chỉ tiêu > Báo cáo > Thực hiện chỉ tiêu </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen%></div>
			</div>
			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="#"> 
					<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<A href="javascript:saveform()"> 
					<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
					<%= obj.getMessage() %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle"> Thực hiện chỉ tiêu</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
							
								<TR>
									<TD class="plainlabel">Chọn năm</TD>
									
									<TD class="plainlabel">
										<select name="nam" id='nam'   >
											<option value = '' > </option>  
											<%
										  		Calendar c2 = Calendar.getInstance();
		 										int t=c2.get(Calendar.YEAR) + 6;
		 										for(int n = 2016; n < t; n++){
		 										if( Integer.toString(n).equals( obj.getNam() ) ){
		 									%>
											<option value='<%=n%>' selected="selected" > <%=n%></option> 
											<% }else{ %>
												<option value='<%=n%>' ><%=n%></option> 
											<% } } %>
										</select>								
									</TD>
									
									<TD class="plainlabel">Chọn tháng</TD>
									<TD>
										<SELECT name = "thang" id='thang' >
										<option value = ''> </option>  
										<%
		 									for(int k = 1; k <= 12; k++){
		 									  if( Integer.toString(k).equals( obj.getThang() ) ){
		 									%>
											<option value='<%=k%>' selected="selected" > <%=k%></option> 
										<% } else { %>
											<option value='<%=k%>' ><%=k%></option> 
										<% } } %>
										</SELECT>
									
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Loại chỉ tiêu</TD>
							  	  	<TD class="plainlabel" >
							  	  		<select name="loaichitieu" id='loaichitieu' >
							  	  			<% if( obj.getLoai().equals("0") ) { %>
							  	  				<option value="0" selected="selected" >OTC</option>
							  	  			<% } else { %>
							  	  				<option value="0"  >OTC</option>
							  	  			<% } %>
							  	  			<% if( obj.getLoai().equals("1") ) { %>
							  	  				<option value="1" selected="selected" >ETC</option>
							  	  			<% } else { %>
							  	  				<option value="1"  >ETC</option>
							  	  			<% } %>
							  	  		</select> 
							  	  	</TD>
									<TD class="plainlabel">Kênh bán hàng</TD>									
									<TD class="plainlabel">
										<select name="kenhId" id="kbhId" >
											<option value="" selected>All</option>
											<%if (kenhRs != null)
													while (kenhRs.next()) {
														if (kenhRs.getString("pk_seq").equals(obj.getKbhId())) {%>
											   		<option value="<%=kenhRs.getString("pk_seq")%>" selected><%=kenhRs.getString("ten")%></option>
											   <%} else {%>
											   	<option value="<%=kenhRs.getString("pk_seq")%>"><%=kenhRs.getString("ten")%></option>
												<%}}%>
										</select>
									</TD>
								</TR>
								
								<TR>
									<TD class="plainlabel">Chỉ tiêu của</TD>
							  	  	<TD class="plainlabel" colspan="3" >
							  	  		<select name="loainhanvien" id='loainhanvien' >
							  	  			<% if( obj.getLoainhanvien().equals("0") ) { %>
							  	  				<option value="0" selected="selected" >Trình dược viên</option>
							  	  			<% } else { %>
							  	  				<option value="0"  >Trình dược viên</option>
							  	  			<% } %>
							  	  			<% if( obj.getLoainhanvien().equals("1") ) { %>
							  	  				<option value="1" selected="selected" >Giám sát</option>
							  	  			<% } else { %>
							  	  				<option value="1"  >Giám sát</option>
							  	  			<% } %>
							  	  			<% if( obj.getLoainhanvien().equals("2") ) { %>
							  	  				<option value="2" selected="selected" >ASM</option>
							  	  			<% } else { %>
							  	  				<option value="2"  >ASM</option>
							  	  			<% } %>
							  	  		</select> 
							  	  	</TD>
									
								</TR>
					
								<TR>
									<td colspan="4">
										<a class="button" href="javascript:TaoBaoCao();"> <img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo </a>
									</td>
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
		if(obj != null){
			obj.closeDB();
			obj = null;
		}
	}catch(Exception e){}
	}
%>