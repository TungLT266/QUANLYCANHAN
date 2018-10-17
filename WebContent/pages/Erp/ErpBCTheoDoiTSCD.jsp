<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.baocaotaisancodinh.*"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
	
	<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	
	IBcTaisancodinh obj = (IBcTaisancodinh) session.getAttribute("obj");
	ResultSet tscdList = (ResultSet)obj.getTscdlist(); %>

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

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
$(document).ready(function()
{
	$("#khId").select2();
	$("#nccId").select2();
	$("#dvkdId").select2();
});
</script>
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
<script language="javascript" type="text/javascript">	
function seach()
{
	document.forms['tscdForm'].action.value= 'seach';
	document.forms["tscdForm"].submit();
}

function submitform()
{   
	var nam = document.getElementById("nam");

	if(nam.value == "")
	{
	 	alert("Vui lòng chọn năm !");
	 	return;
	}
	document.forms['tscdForm'].action.value= 'tao';
	document.forms["tscdForm"].submit();
	reset();
}

	
	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
	function reset() {
		var fieldShow = document.getElementsByName("fieldsHien");
		var fieldHidden = document.getElementsByName("fieldsAn");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = false;
		}
		for ( var i = 0; i < fieldHidden.length; ++i) {
			fieldHidden.item(i).checked = false;
		}
	};
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

</head>
<body>
<form name="tscdForm" method="post" action="../../TheodoitaisancodinhSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý tài sản > Theo dõi tài sản cố định
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Theo dõi tài sản cố định</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                                              			
								
				<TR>
					<TD class="plainlabel" width="8%"> Chọn Năm </TD> 
					<TD class="plainlabel" width="25%"> 
						<select name="nam" id="nam">
							<option value="" ></option>
							<% for(int i = 2010; i < 2018; i++ ) {
								 if( Integer.toString(i).equals(obj.getNam()) ) { %>
								 	<option value="<%= i %>" selected="selected" ><%= i %></option>
								 <% } else { %>
								 	<option value="<%= i %>" ><%= i %></option>
								 <% } %>
							<% } %>
						</select>
					</TD>
					
					<TD class="plainlabel" width="8%">Chọn tháng</TD>
					<TD  class="plainlabel" >
						<SELECT name = "month">
						<%if(obj.getMonth().equals("1")){ %>
							<option value = "1" selected>T.1</option>
						<%}else{ %>
							<option value = "1">T.1</option>
						<%} %>

						<%if(obj.getMonth().equals("2")){ %>
							<option value = "2" selected>T.2</option>
						<%}else{ %>
							<option value = "2">T.2</option>
						<%} %>

						<%if(obj.getMonth().equals("3")){ %>
							<option value = "3" selected>T.3</option>
						<%}else{ %>
							<option value = "3">T.3</option>
						<%} %>

						<%if(obj.getMonth().equals("4")){ %>
							<option value = "4" selected>T.4</option>
						<%}else{ %>
							<option value = "4">T.4</option>
						<%} %>

						<%if(obj.getMonth().equals("5")){ %>
							<option value = "5" selected>T.5</option>
						<%}else{ %>
							<option value = "5">T.5</option>
						<%} %>

						<%if(obj.getMonth().equals("6")){ %>
							<option value = "6" selected>T.6</option>
						<%}else{ %>
							<option value = "6">T.6</option>
						<%} %>

						<%if(obj.getMonth().equals("7")){ %>
							<option value = "7" selected>T.7</option>
						<%}else{ %>
							<option value = "7">T.7</option>
						<%} %>

						<%if(obj.getMonth().equals("8")){ %>
							<option value = "8" selected>T.8</option>
						<%}else{ %>
							<option value = "8">T.8</option>
						<%} %>
						
						<%if(obj.getMonth().equals("9")){ %>
							<option value = "9" selected>T.9</option>
						<%}else{ %>
							<option value = "9">T.9</option>
						<%} %>
						
						<%if(obj.getMonth().equals("10")){ %>
							<option value = "10" selected>T.10</option>
						<%}else{ %>
							<option value = "10">T.10</option>
						<%} %>
						
						<%if(obj.getMonth().equals("11")){ %>
							<option value = "11" selected>T.11</option>
						<%}else{ %>
							<option value = "11">T.11</option>
						<%} %>
						
						<%if(obj.getMonth().equals("12")){ %>
							<option value = "12" selected>T.12</option>
						<%}else{ %>
							<option value = "12">T.12</option>
						<%} %>

						</SELECT>
					
					</TD>
				</TR>
                                     
                 <TR>
                        <td colspan="4" class="plainlabel">
                           <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                 </TR> 
                    
                </TABLE>                  
       </fieldset>            					                    
    	</div>
    </div>  
</div>
<%
if(obj!=null)obj.DBClose();
	
	
	}%>
</form>
</body>
</html>