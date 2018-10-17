<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.center.beans.dutrungansach.IDutrungansach" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IDutrungansach pbkmBean = (IDutrungansach)session.getAttribute("pbkm"); %>
<% String schemeId = (String)session.getAttribute("schemeId"); %>
<% ResultSet scheme = (ResultSet)pbkmBean.getSchemeRS() ; %>
<% ResultSet schemeKogh = (ResultSet)pbkmBean.getSchemeKoGioiHanRs(); %>
<%
Hashtable<String, String> usedPro = (Hashtable<String, String>)pbkmBean.getusedPro(); 
ResultSet dutruRs=pbkmBean.getDutruRs();

NumberFormat formatter = new DecimalFormat("#,###,###");
%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<%@page import="java.sql.SQLException"%>
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
    
    <script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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

<SCRIPT language="JavaScript" type="text/javascript">

	function submitform()
	{
		document.forms['pbkhForm'].setAttribute('enctype', '', 0);
	    document.forms['pbkhForm'].submit();
	}
	
	function search()
	{
		 document.forms['pbkhForm'].action.value='search';
	    document.forms['pbkhForm'].submit();
	}
	function phanboCTKM_Ko_Gh()
	{
		 document.forms['pbkhForm'].action.value='phanbo';
	    document.forms['pbkhForm'].submit();
	}
	
	function upload()
	{
		document.forms['pbkhForm'].setAttribute('enctype', "multipart/form-data", 0);
	    document.forms['pbkhForm'].submit();
	}
	
	function sellectAll(cbId1,cbId2 )
	{
		 var chkAll_Lct = document.getElementById(cbId1);
		 var loaiCtIds = document.getElementsByName(cbId2);
		 

		 
		 if(chkAll_Lct.checked )
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				/*  if(!loaiCtIds.item(i).disabled)
				{ */
					 loaiCtIds.item(i).checked = true;
				/*  }*/
			 }
		 }
		 else
		 {
			 for(var i = 0; i < loaiCtIds.length; i++)
			 {
				 loaiCtIds.item(i).checked = false;
			 }
		 }
	}
</SCRIPT>
</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="pbkhForm"  method="post" action="../../DutrungansachSvl" >
<input type="hidden" name="action" value="0">
<input type="hidden" name="flag" value="<%= pbkmBean.getFlag() %>">
<input type="hidden" name="userId" value="<%=userId %>">


<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý khuyến mãi  &gt; Khai báo &gt; Dự trù ngân sách </TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %>&nbsp;  </TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD >&nbsp; </TD>				
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Báo lỗi nhập liệu </LEGEND>
				
	    				<textarea name="dataerror"  style="width: 100% ; font-weight:bold"  style="width:100%" rows="1" readonly="readonly" ><%= pbkmBean.getMessage() %></textarea>
    					
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Dự trù ngân sách </LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
						  <TR>
							  	<TD class="plainlabel">Chọn tập tin</TD>
						  	  	<TD class="plainlabel"  >
						  	  		<INPUT type="file" name="filename" size="40" value=''>&nbsp;&nbsp;&nbsp;&nbsp;
							  	  		<a class="button2"  href="javascript:upload();"><img style="top: -4px;" src="../images/button.png" alt="">Upload</a>
						  	  	</TD>
						  </TR>
						 </TABLE>
						 </FIELDSET>
						 
						 
						 <FIELDSET >
						<LEGEND class="legendtitle" style="color:black">CTKM </LEGEND>
						  <TABLE class="tabledetail" width="100%" border="0" cellspacing="1px" cellpadding="0px"> 
						 	  <TR class="tbheader">
                        		<TH align="center" width="35%"> Scheme</TH>
                        		<TH align="center" width="30%"> Vùng </TH>
                        		<TH align="center" width="35%"> Ngân sách </TH>
                    		</TR>
						<% int k=0;
                     	if(dutruRs != null)
                    	{ 
	                     	while(dutruRs.next())
	                     	{ 
	                     		
	                     			if(k % 2 == 0){ 
	                       			%> 
	                        			<TR class='tbdarkrow' style="line-height:25px">
	    	                    	<%}else{ %> 
	    	                    		 <TR class='tblightrow' style="line-height:25px" >
	    	                    	<% } %>
	    	                    	<TD align="left">
		                    			<%=dutruRs.getString("SCHEME")%>
		                    		</TD>
	                    			<TD align="left">
	                    				<%=dutruRs.getString("VUNG")%>
	                    			</TD>
	                    			<TD align="left">
	                    				<%=formatter.format(dutruRs.getDouble("NGANSACH"))%>
	                    			</TD>
	                    		</TR>
	                    	<%	k++;
		                  	} }%> 
					
						</TABLE>
						</FIELDSET>
					</TD>
				</TR>
				
					
			</TABLE>
		</TD>
		</TR>
		</TABLE>
		</form>
		</BODY>
		</HTML>
<%
	pbkmBean.DBClose();
	pbkmBean=null;	
}%>
