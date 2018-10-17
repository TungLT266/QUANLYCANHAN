<%@page import="java.sql.ResultSet"%>
<%@page import="geso.traphaco.erp.beans.bangketaisan.IBangketaisan"%>
<%@page import="java.util.Calendar"%>
<%@page import="java.sql.Date"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%
	
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	IBangketaisan obj = (IBangketaisan) session.getAttribute("obj");
	ResultSet RsLoaiTS = obj.getRsLoaiTS();
	
	ResultSet RsTTCP = obj.getRsTrungTamCP();
	
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

<script type="text/javascript" src="../scripts/report-js/action-report.js"></script>
<script language="javascript" type="text/javascript">	
function seach()
{
	document.forms['frm'].action.value= 'seach';
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
	
	 function thongbao()
	 {
		var tt = document.forms['frm'].msg.value;
	 	if(tt.length>0)
	     alert(document.forms['frm'].msg.value);
	 } 
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post" action="../../BangketaisanSvl">
	
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" name="userId" value='<%=userId%>'>
			
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý tài sản > Bảng kê tài sản </div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng Bạn <%=userTen%> &nbsp;</div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%"><%=obj.getMsg()%></textarea>
					<script language="javascript" type="text/javascript">
	    				thongbao();
					</script> 
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Bảng kê tài sản
					</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left" class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel" width="250px">Từ ngày</TD>
									
									<TD>
										<input type="text" class="days"  id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
									</TD>
									
									<TD class="plainlabel" width="250px" >Đến ngày</TD>
									<TD>
									<input type="text" class="days"  id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
									</TD>
								</TR>
								
								<tr>
						      		<td class = "plainlabel" >Loại tài sản</td>
									<td class = "plainlabel">
										<select name = "loaits" style= "width:200px ">
										<option  value=""> </option>
										
										<%
										if(RsLoaiTS != null)
										{
										while(RsLoaiTS.next())
										{
										if(obj.getLoaitsId().equals(RsLoaiTS.getString("pk_seq"))){
										%>
											<option selected="selected" value="<%= RsLoaiTS.getString("pk_seq") %>"><%= RsLoaiTS.getString("TEN") %> </option>
										<%
										}else
										{
											%>
											<option value="<%= RsLoaiTS.getString("pk_seq") %>"><%= RsLoaiTS.getString("TEN") %> </option>
											<%
										}
										
										}}
										%>		
										</select>
									</td>
									
									<td class = "plainlabel" width="250px">Trung tâm chi phí</td>
									<td class = "plainlabel">
										<select name = "ttcp" style= "width:200px " >
										<option  value=""> </option>
										
										<%
										if(RsTTCP != null)
										{
										while(RsTTCP.next())
										{
										if(obj.getTTCpId().equals(RsTTCP.getString("pk_seq"))){
										%>
											<option selected="selected" value="<%= RsTTCP.getString("pk_seq") %>"><%= RsTTCP.getString("TEN") %> </option>
										<%
										}else
										{
											%>
											<option value="<%= RsTTCP.getString("pk_seq") %>"><%= RsTTCP.getString("TEN") %> </option>
											<%
										}
										
										}}
										%>		
										</select>
									</td>
								</tr>

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
if(RsLoaiTS!=null){
	RsLoaiTS.close();
}
if(RsTTCP!=null){
	RsTTCP.close();
}
obj.DBClose();
session.setAttribute("obj", null) ; 

%>