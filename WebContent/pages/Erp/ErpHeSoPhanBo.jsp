<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.khoasothang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<% IErpHesophanbo obj = (IErpHesophanbo)session.getAttribute("obj"); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% 
String userTen = (String) session.getAttribute("userTen"); 
ResultSet giavonRs = obj.getGiavonRs(); 
ResultSet giavonCTRs = obj.getGiavonCTRs();

NumberFormat formatter = new DecimalFormat("#,###,###");
NumberFormat formatter3 = new DecimalFormat("#,###,###.###");

String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{	

%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
    <script type="text/javascript">
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    

    <SCRIPT language="javascript" type="text/javascript">
	    function upload()
		 {
			document.forms['erpCngn'].setAttribute('enctype', "multipart/form-data", 0);
		    document.forms['erpCngn'].submit();
		 }
	</SCRIPT>
</head>
<body>
<form name="erpCngn" method="post" action="../../ErpHesophanboSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
             Quản lý tồn kho > Khóa sổ > Hệ số phân bổ
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width: 100%; float: none; clear: left">
		<fieldset>
			<legend class="legendtitle"> Thông báo</legend>
			<textarea id="errors" name="errors" rows="2"  style="width: 100%;text-align: left; color:#F00 ; font-weight:bold">
				<%= obj.getMsg() %> </textarea>
		</fieldset>
	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle">Hệ số phân bổ</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px">								                          
                    <tr class="plainlabel">
					<td width="120px;" class="plainlabel" valign="middle">Năm </td>
					<td>
							<select name="nam" id="nam" onchange="submitview()"  style="width :70px" >
							<option value=0> </option>  
							<%
								for( int n=2017; n < 2030; n++){
								if(obj.getNam().equals( "" + n ) ){
							%>
								<option value=<%=n%> selected="selected" > <%=n%></option> 
							<% } else { %>
								<option value=<%=n%> > <%=n%></option> 
							<% } } %>
							</select>
						</td>
					</tr>
					<tr class="plainlabel">
						<td class="plainlabel" valign="middle"> Tháng </td>
						<td>
						<select name="thang"  onchange="submitview()" id="thang"  style="width :70px" >
							<option value=0> </option>  
							<%
							int k=1;
							for(k=1;k<=12;k++){
								
							  if(obj.getThang().equals(k+"")){
							%>
								<option value=<%=k%> selected="selected" > <%=k%></option> 
							<% }else{ %>
								<option value=<%=k%> > <%=k%></option> 
							<% } } %>
							</select>
						</td>
					</tr>
					
					<tr>

                        <td  class="plainlabel" colspan="2" >
                        	
                        	<INPUT type="file" name="filename" size="40" value=''>&nbsp;&nbsp;&nbsp;&nbsp;
							<a class="button2"  href="javascript:upload();"><img style="top: -4px;" src="../images/button.png" alt="">Upload hệ số</a>

                        </td>
                    </tr>    
								
                    <tr>
                        <td colspan="2" class="plainlabel">
                            <a class="button2" href="javascript:void(0);">
                                <img style="top: -4px;" src="../images/button.png" alt="">Export hệ số</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>   
                    
                </TABLE>                      
        </fieldset>                      
    	</div>
        
    </div>  
</div>
</form>
<%

try{
	session.setAttribute("obj",null);	
	 obj.DbClose();
	
}catch(Exception er){
	
}
} %>
</body>
</html>
