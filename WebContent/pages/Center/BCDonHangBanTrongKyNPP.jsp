<%@page import="geso.traphaco.center.beans.stockintransit.IStockintransit"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.report.Ireport"%>
<%@page import="java.util.Calendar"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");
	IStockintransit obj = (IStockintransit)session.getAttribute("obj");
	ResultSet npp = obj.getnpp();
	String nppId = obj.getnppId();
	ResultSet vung = obj.getvung();
	ResultSet khuvuc = obj.getkhuvuc();
	ResultSet ddkd = obj.getRsddkd();
	ResultSet ttRs = obj.getTtRs();
	ResultSet khoRs = obj.getkho();
	Utility util = (Utility) session.getAttribute("util");
	String url="";
	url = util.getUrl("BCDonHangBanTrongKy","&view=TT");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>OneOne - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
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
<script type="text/javascript">
function seach() {
	document.forms['frm'].action.value = 'seach';
	document.forms["frm"].submit();
}
	function submitform() 
	{
		
// 		if (document.getElementById("vungId").value == "") 
// 		{
// 			alert("Vui lòng chọn vùng");
// 			return ;
// 		}
		
		if (document.getElementById("Sdays").value == "") 
		{
			alert("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.getElementById("Edays").value == "")
		{
			alert("Vui lòng chọn ngày kết thúc");
			return ;
		}		
	
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
	}
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#vungId").select2();
	$("#khuvucId").select2();
	$("#nhanhangId").select2();
	$("#chungloaiId").select2();
	$("#programs").select2();
	$("#ddkdId").select2();
	$("#gsbhId").select2();
	$("#kenhId").select2();
	$("#dvdlId").select2();
	$("#ttId").select2();
});
</script>

<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BCDonHangBanTrongKy">
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="view" value='TT'>
	<input type="hidden" name="userId" value='<%=userId%>'>
	<input type="hidden" name="congtyId" value='<%= session.getAttribute("congtyId") %>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation"><%=url %></div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %></div>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>

					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" value="<%= session.getAttribute("errors") %>" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Báo cáo đơn hàng bán trong kỳ</legend>
					<div style="width: 100%; float: none" align="left" class="plainlabel">
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
										<TD class="plainlabel">Từ ngày </TD>
											<TD class="plainlabel">	<input type="text" name="Sdays" id="Sdays" class="days" value='<%= obj.gettungay() %>' />
											</TD>
											<TD class="plainlabel">Đến ngày </TD>
											<TD class="plainlabel">
												<input type="text" name="Edays" id="Edays" class="days" value='<%= obj.getdenngay() %>'/>
											</TD>
									</TR>
									<TR>
									<TD class="plainlabel">Miền</TD>
									<TD class="plainlabel">
										<select name="vungId" id="vungId" onchange="seach();" style="width:200px">
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
									<%-- <TD class="plainlabel">Khu Vực</TD>
									<TD class="plainlabel">
										<select name="khuvucId" id="khuvucId" onchange="seach();" style="width:200px">
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
										<select name="ttId" id="ttId"  onchange="seach();"  style="width:200px" >
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
									
								</TR>
								<TR>
									<TD class="plainlabel">Chi nhánh / Đối tác </TD>
									<TD class="plainlabel" > 
										<select name="nppId" onchange="seach();" id="nppId" style="width:200px">
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
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel">
										<select name="ddkdId" id="ddkdId"	onchange="seach();" style="width:200px">
											<option value="" selected>All</option>
											<%if (ddkd != null)
													while (ddkd.next()) {
														if (ddkd.getString("pk_seq").equals(obj.getDdkd())) {%>
														<option value="<%=ddkd.getString("pk_seq")%>" selected><%=ddkd.getString("ten")%></option>
													<%} else {%>
														<option value="<%=ddkd.getString("pk_seq")%>"><%=ddkd.getString("ten")%></option>
													<%}}%>
										</select>
									</TD>
									
								</TR>
								
								<TR>
								<%if(obj.gettype().equals("0")){ %>
										
											<TD class="plainlabel">Trạng thái</TD>
											<TD class="plainlabel">
											<% String[] trangthai = new  String[] {"Chờ xác nhận","Đã xác nhận","Đã Xoá","Đã In","Đã Hủy"  } ;
													String[] idTrangThai = new  String[] {"0","1","2","3","4","5"} ;
											%> 
											<SELECT name="trangthai" >
											<option> </option>
							      		 <% for( int i=0;i<trangthai.length;i++) { 
							    			if(idTrangThai[i].equals(obj.getTrangthai()) ){ %>
							      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
							      		 	<%}else{ %>
							     				<option value='<%=idTrangThai[i]%>' ><%=trangthai[i] %></option>
							     			<%} 
							      		 }
							      		 	%>
											</SELECT>
											</TD>
								  
						  <%}else { %>
											<TD class="plainlabel">Trạng thái</TD>
											<TD class="plainlabel">
											<% String[] trangthai = new  String[] {"Chờ xác nhận","Đã xác nhận","Đã Xoá","Đã In","Đã Hủy"  } ;
													String[] idTrangThai = new  String[] {"1","2","3","4","5"} ;
											%> 
											<SELECT name="trangthai" >
											<option> </option>
							      		 <% for( int i=0;i<trangthai.length;i++) { 
							    			if(idTrangThai[i].equals(obj.getTrangthai()) ){ %>
							      				<option value='<%=idTrangThai[i]%>' selected><%=trangthai[i] %></option>
							      		 	<%}else{ %>
							     				<option value='<%=idTrangThai[i]%>'><%=trangthai[i] %></option>
							     			<%} 
							      		 }
							      		 	%>
											</SELECT>
											</TD>
								  
						  
						  <%} %>
						  
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
											</select></TD>
						  
						  </TR>
						  
						  
						  		
						  
						  
						  
						  <TR>
									<TD class="plainlabel">Doanh số tính theo </TD>
									<TD class="plainlabel">
							<%
								if(obj.getMucCN_DT().equals("1")){ %>
								<input type="checkbox" name="cndt" value="1" checked="checked"  /> CN/DT &nbsp; &nbsp;
							<% }else { %>
								    <input type="checkbox" name="cndt" value="1"  /> CN/DT &nbsp; &nbsp;
							 <%} %>
										
									<%
								if(obj.getMuc_KhachHang().equals("1")){ %>
								<input type="checkbox" name="kh" value="1" checked="checked"  /> Khách Hàng &nbsp; &nbsp;
							<% }else { %>
								    <input type="checkbox" name="kh" value="1"  /> Khách Hàng &nbsp; &nbsp;
							 <%} %>
										
									</TD>
									
								</TR>
							
						  
								
								
								<TR>
									<TD class="plainlabel"> Mức lấy  </TD>
									<TD class="plainlabel">
									<%
										if(obj.gettype().equals("0")){
											%>
											<input type="radio" name="type" value="1"  onchange="seach();" /> Hóa Đơn &nbsp; &nbsp;
											<input type="radio" name="type" value="0"  checked="checked"  onchange="seach();" /> Đơn hàng
											<%
										}
										else
										{
											%>
												<input type="radio" name="type" value="1"  checked="checked"  onchange="seach();" />Hóa Đơn  &nbsp; &nbsp;
												<input type="radio" name="type" value="0"  onchange="seach();" /> Đơn hàng
											<%
										}
									%>
										
									</TD>
									
								</TR>
								
							
								<TR>
									<td colspan="4"><a class="button" href="javascript:submitform();"> 
										<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo
									</a></td>
								</TR>
							</TABLE>
						</div>
						
				</fieldset>
			</div>
		</div>
		<br />
	</form>
</body>
</html>
<% 
if(obj!=null)
{
	obj.DBclose();
	session.setAttribute("obj",null);
}
%>