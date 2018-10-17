<%@page import="java.sql.SQLException"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.reports.IBCCongNoKH"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	String loi=(String)session.getAttribute("loi");
	String tungay=(String)session.getAttribute("tungay");
	String denngay=(String)session.getAttribute("denngay");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

	<% 
		IBCCongNoKH obj = (IBCCongNoKH)session.getAttribute("obj"); 
	%>
	
	<% ResultSet KhachHangList = (ResultSet)obj.getKHList(); %>
<% ResultSet ddkdRs = obj.getDdkdRs();%>
<% ResultSet nvgnRs = obj.getNvgnRs();%>
	
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">

<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
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
           
        }); 
		
		
    </script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
		{
			$("#KhachHang").select2();
		});


</script>


<script type="text/javascript">
function submitform1()
{
	if (document.forms["rpForm"]["tuNgay"].value.length == 0) {
		setErrors("Vui lòng chọn ngày bắt đầu");
		return;
	}
	if (document.forms["rpForm"]["denNgay"].value.length == 0) {
		setErrors("Vui lòng chọn ngày kết thúc");
		return;
	}			
	var fieldShow = document.getElementsByName("fieldsHien");		
	for ( var i = 0; i < fieldShow.length; ++i) {
		fieldShow.item(i).checked = true;
	}		
	
	document.forms['rpForm'].action.value="excel";
	document.forms['rpForm'].submit();
	reset();
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
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="rpForm" method="post" action="../../BCCongNoKHSvl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>
<input type="hidden" name="view" value='NPP'>

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ &gt; Báo cáo &gt; Công nợ khách hàng </TD>
   
						   <TD colspan="2" align="right" class="tbnavigation">Chào mừng Bạn <%=userTen %>&nbsp;</TD>
						  </tr>
 					  </table>					</TD>
				</TR>
				<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
	    				<textarea id="errors" readonly="readonly" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold">
						
					</textarea>
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
								  	<TD width="19%" class="plainlabel" > Từ ngày</TD>
									<TD class="plainlabel" > 
										<input type="text" class="days" name="tuNgay" size="20" value = "<%=tungay%>" style="width: 230px;">
									</TD>
									<TD width="19%" class="plainlabel" >Đến ngày</TD>
											<TD class="plainlabel" >
													<input type="text"  class="days" name="denNgay" size="20" value = "<%=denngay%>" style="width: 230px;">												</TD>
												<TD>                                        		</TD>	
								  	
								<TR>
								
								
								
								<TR>									
									<TD class="plainlabel">Nhân viên giao nhận </TD>
									<TD class="plainlabel" colspan="1"><select name="nvgnId" id="nvgnId" style="width:202px">
											<option value="" selected >All</option>
											<% if(nvgnRs != null)
											{
												try
			                        			{	
											   while(nvgnRs.next()){
											   if(nvgnRs.getString("pk_seq").equals(obj.getNvgnId()  ))
											   { %>
											   <option value="<%= nvgnRs.getString("pk_seq") %>" selected><%=nvgnRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= nvgnRs.getString("pk_seq") %>"><%=nvgnRs.getString("ten") %></option>
											<%} }nvgnRs.close();} catch(SQLException ex){}%>
											<%} %>
									</select></TD>	
									
									<TD class="plainlabel">Trình dược viên</TD>
									<TD class="plainlabel" colspan="1"><select name="ddkdId" id="ddkdId" style="width:202px">
											<option value="" selected >All</option>
											<% if(ddkdRs != null)
											{
												try
			                        			{	
											   while(ddkdRs.next()){
											   if(ddkdRs.getString("pk_seq").equals(obj.getDdkdId() ))
											   { %>
											   <option value="<%= ddkdRs.getString("pk_seq") %>" selected><%=ddkdRs.getString("ten") %></option>
											   <%}else { %>
											   <option value="<%= ddkdRs.getString("pk_seq") %>"><%=ddkdRs.getString("ten") %></option>
											<%} }ddkdRs.close();} catch(SQLException ex){}%>
											<%} %>
									</select></TD>	
									
					</TR>
								
								 <TR>
										  <TD class="plainlabel">Khách Hàng</TD>
								      		<TD class="plainlabel" colspan="3">
								      		  <SELECT name="KhachHang" id="KhachHang" style="width: 230px;">
								      			<option value="" selected>All</option>
								        	<% try{
								        		if(KhachHangList != null)
								        		{
								        		 while(KhachHangList.next()){ 
								    	      if(KhachHangList.getString("PK_SEQ").equals(obj.getKHId())){ %>
								      			<option value='<%= KhachHangList.getString("PK_SEQ") %>' selected> <%= KhachHangList.getString("TEN") %></option>
								      	    <%}else{ %>
								     			<option value='<%=KhachHangList.getString("PK_SEQ") %>'> <%= KhachHangList.getString("TEN") %></option>
								     	   <%}
								    	        }KhachHangList.close();
								        	  }
								        	  }catch(java.sql.SQLException e){} %>
								          </SELECT></TD>
								</TR>
									 	
							    <TR>
									<TD colspan="4" class="plainlabel">
									<a class="button2" href="javascript:submitform1()" >
	<img style="top: -4px;" src="../images/button.png" alt="">EXCEL</a>&nbsp;&nbsp;&nbsp;&nbsp;                                    
									
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

if(obj!=null)
{
	obj.dbClose();
}

	}%>