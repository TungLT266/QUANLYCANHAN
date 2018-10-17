<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.bobaocaotaichinhhopnhat.IBoBaoCaoTaiChinhHopNhat"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBoBaoCaoTaiChinhHopNhat obj = (IBoBaoCaoTaiChinhHopNhat) session.getAttribute("obj");
	ResultSet nppRs = obj.getNppRs();
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoERP - Project</TITLE>
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
	document.forms['frm'].action.value= 'seach';
	document.forms["frm"].submit();
}

function Change()
{   

   document.forms["frm"].action.value="change";
   document.forms["frm"].submit();
}

	function submitform() {
		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
	}

	function setErrors(errorMsg) {
		var errors = document.getElementById("errors");
		errors.value = errorMsg;
	}
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BoBaoCaoTaiChinhHopNhatSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="view" value="<%= obj.getView() %>" >
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Báo cáo quản trị > Kế toán - Tài chính > Bộ báo cáo tài chính hợp nhất</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%=userTen%> &nbsp;</div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Bộ báo cáo tài chính
					</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
							 <%-- <% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn chi nhánh </TD>
                        <TD class="plainlabel" colspan = 2 >
                            <input type ="hidden" name = "nppId" id="nppId" value ="<%=obj.getNppId() %>" />
                            <SELECT NAME = "nppId"  style = "width:300px;size:5" onChange = "Change();" id = "nppId" disabled>
                            	<OPTION VALUE = "">&nbsp;</OPTION>
                         
                    <%
                    if(nppRs != null){ 
                        while(nppRs.next()){
                          if(obj.getNppId().equals(nppRs.getString("PK_SEQ"))){
                        %>  
                          <OPTION VALUE = "<%= nppRs.getString("PK_SEQ")%>" SELECTED ><%= nppRs.getString("TEN")%></OPTION>
                    <%      }
                          else{ %>
                          <OPTION VALUE = "<%= nppRs.getString("PK_SEQ")%>" ><%= nppRs.getString("TEN")%></OPTION>
                    <%      }
          
                        } 
                      }%>
                                      </SELECT>           
                             </TR>
                  <%} %>   --%>
								<TR>
									<TD class="plainlabel">Chọn năm</TD>
									
									<TD class="plainlabel">
										<SELECT name = "year">
										<option value=0> </option>  
										<%
									  
  										int n;
  										for(n = 2010; n < 2018; n++){
  										if(obj.getYear().equals(""+n)){
	  									%>
										<option value=<%=n%> selected="selected" > <%=n%></option> 
										<%
	 										}else{
	 									%>
										<option value=<%=n%> ><%=n%></option> 
										<%
	 										}
	 									 }
 										%>

										</SELECT>										
									</TD>
									
									<TD class="plainlabel">Chọn tháng</TD>
									<TD>
										<SELECT name = "month" style = "width:70px;">
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
								<TR>
									<td colspan="4"><a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>
									</td>
								</TR>
							</TABLE>
						</div>
						</div>
					</div>
			</div>
	</form>
</body>
</html>
<%
	obj.DBClose();
%>