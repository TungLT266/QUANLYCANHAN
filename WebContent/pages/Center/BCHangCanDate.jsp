<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.distributor.beans.reports.IBcNghiepVuHT"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
	
<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	IBcNghiepVuHT obj = (IBcNghiepVuHT) session.getAttribute("obj"); %>
<% 	ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% 	ResultSet lspRs = (ResultSet)obj.getLoaiSpRs(); %>
<% 	ResultSet spRs = (ResultSet)obj.getSpRs(); %>
<%	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ 
%>
<% 

 
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

<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"type="text/javascript"></script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#nppId").select2();
	$("#sanphamId").select2();
});
</script>

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
<script language="javascript" type="text/javascript">	

	function submitform() {
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		reset();
	}
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
		
	});
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BCHangCanDateSvl">
	<input type="hidden" name="action" value='1'>
	<input type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">&#160; Quản lý tồn kho &#62; Báo cáo &#62; Báo cáo hàng cận date</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%=userTen %></div>
			</div>
				<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%= session.getAttribute("errors") %></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Tiêu chí lấy báo cáo</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">
							
							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
			                        <TD class="plainlabel" valign="middle" >Kho </TD>
			                        <TD class="plainlabel" valign="middle" >
			                            <select name="khoId" id="khoId" onchange="locsanpham()" class="select2" style="width: 600px"  >
			                            	<option value="">Tất cả</option>
			                            	<%
				                        		if(khoRs != null)
				                        		{
				                        			while(khoRs.next())
				                        			{  
				                        			if( khoRs.getString("pk_seq").equals(obj.getKhoId())){ %>
				                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("khoTen") %></option>
				                        			<%}else { %>
				                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("khoTen") %></option>
				                        		 <% } } khoRs.close();
				                        		}
				                        	%>
			                            </select>
			                        </TD>                        
			                    </TR>
			                    <TR>
			                        <TD class="plainlabel" valign="middle" >Loại sản phẩm </TD>
			                        <TD class="plainlabel" valign="middle" >
			                        	<select name = "loaisanpham"   id="loaisanpham" onchange="locsanpham()" class="select2" style="width: 600px" multiple="multiple" >
			                         		<option value="" >Tất cả</option>
				                        	<%
				                        		if(lspRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(lspRs.next())
				                        			{  
				                        			if( obj.getLoaiSpId().contains(lspRs.getString("pk_seq"))  ){ %>
				                        				<option value="<%= lspRs.getString("pk_seq") %>" selected="selected" ><%= lspRs.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= lspRs.getString("pk_seq") %>" ><%= lspRs.getString("ten") %></option>
				                        		 <% } } lspRs.close();} catch(Exception ex){}
				                        		}
				                        	%>
				                    	</select>                            
			                        </TD>                        
			                    </TR> 
			                    
			                    <TR>
			                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
			                        <TD class="plainlabel" valign="middle" >
			                         	<select name = "spIds" id="spIds" class="select2" style="width: 600px" multiple="multiple" >
			                         		<option value="" >Tất cả</option>
				                        	<%
				                        		if(spRs != null)
				                        		{
				                        			try
				                        			{
				                        			while(spRs.next())
				                        			{  
				                        			if( obj.getSpId().contains(spRs.getString("pk_seq"))  ){ %>
				                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ma") + ", " + spRs.getString("ten") %></option>
				                        			<%}else { %>
				                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ma") + ", " + spRs.getString("ten") %></option>
				                        		 <% } } spRs.close();} catch(Exception ex){}
				                        		}
				                        	%>
				                    	</select>                
			                        </TD>                        
			                    </TR>	
								<TR>
									<td colspan="4"><a class="button" href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>
								
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

}
%>