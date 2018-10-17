<%@page import="geso.traphaco.center.beans.report.IBckho"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@page import="java.sql.ResultSet"%>

<%	
	IBckho obj = (IBckho)session.getAttribute("obj"); 	
	ResultSet kenh = obj.getNhomkenhRs();
	ResultSet loaikhoRs = obj.getLoaikhoRs();
	ResultSet doituongRs = obj.getDoituongRs();
	ResultSet rsKho = obj.getKhoRs();
 %>


<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
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
	});
</script>




<SCRIPT language="javascript" type="text/javascript">

function submitform()
{
	document.forms['rpForm'].action.value = "submit";
	document.forms['rpForm'].submit();
}

function taobaocao()
{
	document.forms['rpForm'].action.value = "taobaocao";
	document.forms['rpForm'].submit();
}
function checkedAll(){
	var chkAll = document.getElementById("checkKho");
 	var khoId = document.getElementsByName("khoId");
	 console.log("vao day");
	 if(chkAll.checked)
	 {
		 for(i = 0; i < khoId.length; i++)
		 {
			 khoId.item(i).checked = true;
		 }
	 }
	 else
	 {
		 for(i = 0; i < khoId.length; i++)
		 {
			 khoId.item(i).checked = false;
		 }
	 }
 }
</SCRIPT>
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
    <script src="../scripts/colorBox/jquery.colorbox.js"></script>
    <script>
        $(document).ready(function()
        {
        	$(".kholist").colorbox({width:"46%", inline:true, href:"#kholist"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Viethung - Project.");
                return false;
            });
            $(".splist").colorbox({width:"46%", inline:true, href:"#splist"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Viethung - Project.");
                return false;
            });
        });
    </script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../Iventorynpp">
<input type="hidden" name="userId" value= <%= obj.getUserId() %> >
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
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý tồn kho &gt; Báo cáo &gt; Tồn hiện tại </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= obj.getUserTen() %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%=obj.getMsg()%></textarea>
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
							<TABLE  width="100%" cellpadding="6" cellspacing="0">
								<TR>								
					  				<TD class="plainlabel" width="5%">Nhóm kênh</TD>										
									<TD class="plainlabel" width="25%">
										<select name="kenhId" id="kenhId">
											<option value="" selected>All</option>
											<% if(kenh != null)
											   while(kenh.next()){
											   if(kenh.getString("pk_seq").equals(obj.getNhomkenhId()))
											   { %>
											   <option value="<%= kenh.getString("pk_seq") %>" selected><%=kenh.getString("diengiai") %></option>
											   <%}else { %>
											   <option value="<%= kenh.getString("pk_seq") %>"><%=kenh.getString("diengiai") %></option>
											<%} }%>
										</select>
									</TD>
								</TR>	
								<TR>								
					  				<TD class="plainlabel" >Loại kho</TD>										
									<TD class="plainlabel" >
										<select name="loaikho" id="loaikho" onchange="submitform();" >
											<option value="" selected></option>
											<% if(loaikhoRs != null)
											   while(loaikhoRs.next()){
											   if(loaikhoRs.getString("pk_seq").equals(obj.getLoaikho()))
											   { %>
											   <option value="<%= loaikhoRs.getString("pk_seq") %>" selected><%=loaikhoRs.getString("diengiai") %></option>
											   <%}else { %>
											   <option value="<%= loaikhoRs.getString("pk_seq") %>"><%=loaikhoRs.getString("diengiai") %></option>
											<%} } loaikhoRs.close(); %>
										</select>
									</TD>
								</TR>	
								<TR>
									<TD class="plainlabel">Kho</TD>
									<TD class="plainlabel">
										<a class="kholist" href="#" >
		                        		<img style="top: -4px;" src="../images/vitriluu.png" title="Danh sách kho"></a>
				                		<div style='display:none'>
			                        	<div id='kholist' style='padding:0px 5px; background:#fff;'>
			                        		<table cellpadding="4px" cellspacing="2px" width="100%" align="center">
			                        			<tr class="tbheader">
			                        				<th align="center">Kho</th>
			                        				<th align="center">Chọn <input type="checkbox" id="checkKho" onchange="checkedAll();" ></th>
			                        			</tr>
		                        				<% if(rsKho != null){
		                        					while(rsKho.next()){%>
				                        			<tr>
			                        					<td class="plainlabel" align="left"><%= rsKho.getString("ten") %></td>
			                        					<td class="plainlabel">
			                        					<% if(obj.getKhoId().contains(rsKho.getString("pk_seq"))) { %>
					                    						<input type="checkbox" id="khoId" name="khoId" value="<%= rsKho.getString("pk_seq") %>" checked="checked" >
					                    					<% } else { %>
					                    						<input type="checkbox" id="khoId" name="khoId" value="<%= rsKho.getString("pk_seq") %>"  >
					                    					<% } %>
			                        					</td>
			                        				</tr>
		                        				<%} }%>
			                        		</table>
			                        	</div>
			                        	</div>
			                        	<!-- &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        								<a class="button" href="javascript:search();">
        								<img style="top: -4px;" src="../images/button.png" alt=""> Chọn </a> -->
									</TD>		
								</TR>
								<% if( obj.getLoaikho().equals("5") || obj.getLoaikho().equals("8") || obj.getLoaikho().equals("9") ) { %>
								<TR>								
					  				<TD class="plainlabel" >Khách hàng</TD>										
									<TD class="plainlabel" >
										<select name="doituongId" id="doituongId" class="select2" style="width: 400px;" >
											<option value="" selected></option>
											<% if(doituongRs != null)
											   while(doituongRs.next()){
											   if(doituongRs.getString("pk_seq").equals(obj.getDoituongId()))
											   { %>
											   <option value="<%= doituongRs.getString("pk_seq") %>" selected><%=doituongRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= doituongRs.getString("pk_seq") %>"><%=doituongRs.getString("ten") %></option>
											<%} } doituongRs.close(); %>
										</select>
									</TD>
								</TR>	
								<% } %>									
				  				<TR>
				  					<TD class="plainlabel" colspan = '3' style="font-size: 12px; font-weight: bold;" >
				  						<input type="radio" value="0" name="avail"  />Lấy tất cả
				  						<input type="radio" value="1" name="avail" checked="checked"  />Chỉ lấy số lượng lớn hơn 0										
									</TD>
									
                           		</TR> 
                           		<TR>
                           			<TD class="plainlabel" colspan = '3'>
                           			<%if(obj.getLaysolo().trim().equals("1")){ %>
							  				<input name="laysolo" checked="checked" value="1"  type="checkbox" /> Lấy có số Lô
							  				<%}else{ %>
							  				<input name="laysolo" value="1"  type="checkbox" /> Lấy có số Lô
							  				<%} %>	
                           			</TD>
                           		</TR>                                    										
							    <TR>
									<TD colspan="2" class="plainlabel">
									<div id="btnTaoBC">
									<a class="button2" href="javascript:taobaocao();" >
										<img style="top: -4px;" src="../images/button.png" alt="">Tạo báo cáo</a>
									</div>
									&nbsp;&nbsp;&nbsp;&nbsp;                                    
									</TD>
								</TR>
							</TABLE>
							</FIELDSET>						</TD>
						</TR>
					</TABLE>					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
</TABLE>
</form>
</BODY>
</HTML>
<%
try{
	if(obj!=null){
		obj.DBclose();
		obj=null;
		
	}
	session.setAttribute("obj",null);
	
}catch(Exception er){
	
}
%>

