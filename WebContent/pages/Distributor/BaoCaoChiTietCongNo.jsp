<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.hoadontaichinh.*" %>
<%@page import="java.sql.ResultSet"%>
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
		IBCChiTietCongNo obj = (IBCChiTietCongNo)session.getAttribute("obj"); 
	%>
	
	<% ResultSet KhachHangList = (ResultSet)obj.getKHList();
	   ResultSet nppRs = obj.getDoiTacRs();
	%>
	
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
			$("#doitacId").select2();
		});


</script>


<script type="text/javascript">

function submitform()
{  
   document.forms['rpForm'].action.value= 'search';
   document.forms['rpForm'].submit();
}

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
<form name="rpForm" method="post" action="../../BCChiTietCongNoSvl">
<input type="hidden" name="userId" value= <%= userId %> >
<input type="hidden" name="action" value='1'>
<input type="hidden" name="view" value='NPP'>
<input type="hidden" name="nppId" value="<%= obj.getNppId() %>" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" cellpadding="0" cellspacing="1">		
				<TR>
					<TD width="100%" align="left">
					  <table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
						   <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý công nợ &gt; Báo cáo &gt; BC Chi tiết công nợ khách hàng </TD>
   
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
								  	<TD width="19%" class="plainlabel" >Từ ngày</TD>
								  	<TD class="plainlabel" >
										<TABLE cellpadding="0" cellspacing="0">
											<TR><TD>
												<input type="text" class="days" name="tuNgay" size="20" value = "<%=tungay%>" style="width: 230px;">
												</TD>
												<TD>		   										
												</TD>
                                    		</TR>
										</TABLE>									</TD>
									
									<TD class="plainlabel" >Đến ngày </TD>
								  	<TD class="plainlabel" >
							  			<TABLE cellpadding="0" cellspacing="0">
							  				<TR>
							  					<TD>
													<input type="text"  class="days" name="denNgay" size="20" value = "<%=denngay%>" style="width: 230px;">												</TD>
												<TD>                                        		</TD>
                                     		</TR>
										</TABLE>									
										</TD>
								</TR>
															
								 <TR>
									<TD class="plainlabel"> Mức lấy  </TD>
									<TD class="plainlabel" colspan="3">
									<%
										if(obj.gettype().equals("0")){
											%>
											<input type="radio" name="type" value="1"  onchange="submitform();" /> Đối tác &nbsp; &nbsp;
											<input type="radio" name="type" value="0"  checked="checked"  onchange="submitform();" />Khách hàng
											<%
										}
										else
										{
											%>
												<input type="radio" name="type" value="1"  checked="checked"  onchange="submitform();" />Đối tác  &nbsp; &nbsp;
												<input type="radio" name="type" value="0"  onchange="submitform();" /> Khách hàng
											<%
										}
									%>
										
									</TD>									
							</TR>
							
							<%if(obj.gettype().equals("0")) {%>
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
							<%} %>		
							
							<%if(obj.gettype().equals("1")) {%>
							<TR>		
									<TD class="plainlabel" width="100px">Đối tác</TD>
									<TD class="plainlabel" colspan="3">
										<select name="doitacId"  id="doitacId" class="select2" style="width: 200px" onchange="submitform();"  >
											<option value="">All</option>
											<%
			                       		if(nppRs != null)
			                       		{
			                       			try
			                       			{
			                       			while(nppRs.next())
			                       			{  
			                       			if( nppRs.getString("pk_seq").equals(obj.getDoiTacId())){ %>
											<option value="<%= nppRs.getString("pk_seq") %>"
												selected="selected"><%= nppRs.getString("ten") %></option>
											<%}else { %>
											<option value="<%= nppRs.getString("pk_seq") %>"><%= nppRs.getString("ten") %></option>
											<% } } nppRs.close();} catch(Exception ex){}
			                       		}
			                       	%>
									</select></TD>
							</TR>  
						<%} %>	 	
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