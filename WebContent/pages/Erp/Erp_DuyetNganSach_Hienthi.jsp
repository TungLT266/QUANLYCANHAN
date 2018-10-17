<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "geso.traphaco.erp.beans.lapngansach.*" %>
<%@ page import = "geso.traphaco.center.util.*" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<% 	ILapngansach lnsBean = (ILapngansach)session.getAttribute("lnsBean"); %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>    

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">

#diengiai {

    width: 300px;
}
#tab tr:HOVER{
cursor:pointer;
background: #E2F0D9;
}
#tabc tr input:HOVER{
cursor:pointer;
background: #E2F0D9;
}
</style>


<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script>
//perform JavaScript after the document is scriptable.
$(function() {
 // setup ul.tabs to work as tabs for each div directly under div.panes
 	$("ul.tabs").tabs("div.panes > div");
});
</script>

<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" language="javascript">
	$(document).ready(function() {
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});
	


	var flag=false;
	function isNumberKey(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) && charCode!='46')
	      return false;
	
	   return true;
	}
	
	function isNumberKey2(evt)
	{
	   var charCode = (evt.which) ? evt.which : event.keyCode;
	   if (charCode > 31 && (charCode < 48 || charCode > 57) )
	      return false;
	
	   return true;
	}
	
	 function saveform()
	 { 
		 document.forms['lnsForm'].action.value='save';
	     document.forms['lnsForm'].submit();
	 }

	 function exportExcel()
	 { 
		 document.forms['lnsForm'].action.value='excel';
	     document.forms['lnsForm'].submit();
	 }

	 function duyetform()
	 { 
		 document.forms['lnsForm'].action.value='duyet';
	     document.forms['lnsForm'].submit();
	 }

	 function chonform()
	 { 
		 document.forms['lnsForm'].action.value='chon';
	     document.forms['lnsForm'].submit();
	 }

	 function submitform()
	 { 
		 document.forms['lnsForm'].action.value='new';
	     document.forms['lnsForm'].submit();
	 }

	function DinhDangTien(num) 
 	{
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num)) num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		    num = num.substring(0,num.length-(4*i+3))+','+ num.substring(num.length-(4*i+3));
		return (((sign)?'':'-') + num);
	}

	function Dinhdang(element)
	{
		 
	 	element.value = DinhDangTien(element.value);
	 	if(element.value== '' )
	 	{
	 		element.value= '';
	 	}
		 	
	}
	 


	 
</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name="lnsForm" method="post" action="../../DuyetngansachSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="Id" value='<%=lnsBean.getId()  %>'>
<input type="hidden" name="year" value='<%=lnsBean.getNam()  %>'>
<input type="hidden" name="action" value='1'>


<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">Quản lý ngân sách > Duyệt ngân sách > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>

<%if(lnsBean.getTrangthai().equals("0")){ %>  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
		
        <span id="btnDuyet">
	        <A href="javascript:duyetform()" >
	        	<IMG src="../images/Chot.png" title="Duyệt" alt="Duyệt" border ="1px" style="border-style:outset"></A>
        </span>
        
        <span id="btnDuyet">
	        <A href="javascript:exportExcel()" >
	        	<IMG src="../images/excel30.gif" title="Excel" alt="Excel" border ="1px" style="border-style:outset;"></A>
        </span>
        
    </div>
<%}else{ %>  	
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="">
		
        <span id="btnDuyet">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Lưu lại" alt="Lưu lại" border ="1px" style="border-style:outset"></A>
        </span>

        <span id="btnDuyet">
	        <A href="javascript:exportExcel()" >
	        	<IMG src="../images/excel30.gif" title="Excel" alt="Excel" border ="1px" style="border-style:outset;"></A>
        </span>
        
        
    </div>
<%} %>

  	<div align="left" style="width:100%%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= lnsBean.getMsg() %></textarea>
		         <% lnsBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <DIV align="left" style="width:100%; float:none; clear:left">
    <FIELDSET>
			<LEGEND class="legendtitle">Thông tin ngân sách </LEGEND>
						
			<TABLE  width="100%" cellspacing="0" cellpadding="6">

				<TR>
					<TD class="plainlabel">Diễn giải</td> 
					<TD class="plainlabel"> <INPUT TYPE = "text" NAME = "diengiai" VALUE = "<%=lnsBean.getDiengiai() %>"> </TD>
				</TR>
					
				<TR>
					<TD class="plainlabel">Năm </TD> 
					<TD class="plainlabel"> 
						<INPUT TYPE = "text" NAME = "nam" VALUE = "<%=lnsBean.getNam() %>" readonly=readonly >
					</TD>
				</TR>
				
				<TR>
					<TD class="plainlabel">Hiệu lực </TD> 
					<TD class="plainlabel">
					  				
					<% if(lnsBean.getHieuluc().equals("1")) {%>
					  				
					 	<INPUT type = "checkbox" name = "hieuluc" value="1" checked  ></TD>
					  				
					<%}else{ %>
				  				
						<INPUT type = "checkbox" name = "hieuluc" value="1" ></TD>
					  				
					<%} %>
				</TR>
			
				<TR>
					<TD class="plainlabel">Đã duyệt </TD> 
					<TD class="plainlabel">
				  				
					<% if(lnsBean.getTrangthai().equals("1")) {%>
					  				
					 	<INPUT type = "checkbox" name = "trangthai" value="1" checked disabled = disabled></TD>
					  				
					<%}else{ %>
					  				
						<INPUT type = "checkbox" name = "trangthai" value="1" disabled = disabled ></TD>
					  				
					<%} %>
				</TR>


			</TABLE>
			
	</FIELDSET>
	</DIV>
<!--	
	<DIV align="left" style="width:120%; float:none; clear:left">
		<TABLE  width="100%" cellspacing="0" cellpadding="6">
			<TR>
				<TD colspan="6">
															
					<UL class="tabs">
									
						<LI><A href="#" class= "legendtitle"> P&L Công ty</A></LI>
																
						<LI><A href="#" class= "legendtitle"> P&L Xưởng Nhôm</A></LI>
						
						<LI><A href="#" class= "legendtitle"> P&L Xưởng Lõi</A></LI>
							
					</UL>
									
					<DIV class="panes">

						<DIV style="width: 99%">
							<TABLE style="width: 99%" cellpadding="4px" cellspacing="1px" align="center">
								<TR class="tbheader">
									<TH width="28%" align = center >Hạng mục</TH>
									<TH width="6%" align = center >T.1</TH>
									<TH width="6%" align = center >T.2 </TH>
									<TH width="6%" align = center >T.3</TH>
									<TH width="6%" align = center >T.4</TH>
									<TH width="6%" align = center >T.5</TH>
									<TH width="6%" align = center >T.6</TH>
									<TH width="6%" align = center >T.7</TH>
									<TH width="6%" align = center >T.8</TH>
									<TH width="6%" align = center >T.9</TH>
									<TH width="6%" align = center >T.10</TH>
									<TH width="6%" align = center >T.11</TH>
									<TH width="6%" align = center >T.12</TH>
								</TR>
						<% //		<jsp:include page="Erp_PL_Company.jsp" /> %>
							</TABLE>	

						</DIV>
		
						<DIV>
							<TABLE style="width: 99%" cellpadding="4px" cellspacing="1px" align="center">
								<TR class="tbheader">
									<TH width="28%" align = center >Hạng mục</TH>
									<TH width="6%" align = center >T.1</TH>
									<TH width="6%" align = center >T.2 </TH>
									<TH width="6%" align = center >T.3</TH>
									<TH width="6%" align = center >T.4</TH>
									<TH width="6%" align = center >T.5</TH>
									<TH width="6%" align = center >T.6</TH>
									<TH width="6%" align = center >T.7</TH>
									<TH width="6%" align = center >T.8</TH>
									<TH width="6%" align = center >T.9</TH>
									<TH width="6%" align = center >T.10</TH>
									<TH width="6%" align = center >T.11</TH>
									<TH width="6%" align = center >T.12</TH>
								</TR>

						<% //	<jsp:include page="Erp_PL_Lamination.jsp" /> %>
							</TABLE>	

						</DIV>
		
						<DIV>
							<TABLE style="width: 99%" cellpadding="4px" cellspacing="1px" align="center">
								<TR class="tbheader">
									<TH width="28%" align = center >Hạng mục</TH>
									<TH width="6%" align = center >T.1</TH>
									<TH width="6%" align = center >T.2 </TH>
									<TH width="6%" align = center >T.3</TH>
									<TH width="6%" align = center >T.4</TH>
									<TH width="6%" align = center >T.5</TH>
									<TH width="6%" align = center >T.6</TH>
									<TH width="6%" align = center >T.7</TH>
									<TH width="6%" align = center >T.8</TH>
									<TH width="6%" align = center >T.9</TH>
									<TH width="6%" align = center >T.10</TH>
									<TH width="6%" align = center >T.11</TH>
									<TH width="6%" align = center >T.12</TH>
								</TR>
						<% //	<jsp:include page="Erp_PL_PaperCore.jsp" /> %>
							
							</TABLE>	
						</DIV>

					</DIV>		
		</TABLE>	
	
	</DIV>	-->
</FORM>

</BODY>

</html>
<% 
	
	lnsBean.closeDB(); 
	}
%>
