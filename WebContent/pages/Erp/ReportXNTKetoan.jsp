 
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
 
<%@page import="java.sql.ResultSet"%>
 
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
	IStockintransit obj = (IStockintransit) session.getAttribute("obj");
  
	ResultSet rsKho = obj.GetRsErpKho();
  
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Phanam - Project</TITLE>  
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
	$("#ddkdId").select2();
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
	function search()
	{
		document.forms['frm'].action.value= 'search';
		document.forms["frm"].submit();
	}
	function submitform() {
		if (document.forms["frm"]["Sdays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày bắt đầu");
			return ;
		}
		if (document.forms["frm"]["Edays"].value.length == 0) {
			setErrors("Vui lòng chọn ngày kết thúc");
			return ;
		}
		var fieldShow = document.getElementsByName("fieldsHien");
		for ( var i = 0; i < fieldShow.length; ++i) {
			fieldShow.item(i).checked = true;
		}
		//document.getElementById("btnTaoBC").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho thuc hien..' border='1' longdesc='cho thuc hien..' style='border-style:outset'> Processing...</a>";

		document.forms['frm'].action.value= 'tao';
		document.forms["frm"].submit();
		reset();
	//	for ( var i = 0; i < fieldShow.length; ++i) {
	//		fieldShow.item(i).checked = false;
	//	}
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
	
 
	function capnhat_check(){
		
		
		var layctnxsolo = document.getElementById("layctnxsolo");
		var layctnxsolo_tong = document.getElementById("layctnxsolo_tong");
		 
		if(layctnxsolo.checked  && layctnxsolo_tong.checked ){
			layctnxsolo.checked =!layctnxsolo_tong.checked;
		}
		
	}
	
	function capnhat_check1(){
		
		
		var layctnxsolo = document.getElementById("layctnxsolo");
		var layctnxsolo_tong = document.getElementById("layctnxsolo_tong");
		 
		if(layctnxsolo.checked  && layctnxsolo_tong.checked ){
			 layctnxsolo_tong.checked=false ;
		}
		
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
</script>
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
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post"
		action="../../ErpReportXNTKetoanSvl">
		<input type="hidden" name="action" value='1'> 
		<input type="hidden" id="userId" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					class="tbnavigation">Quản lý tồn kho &#62; Báo cáo &#62; Xuất - Nhập - Tồn KT</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%></div>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Báo lỗi nhập liệu</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100% ; color:#F00 ; font-weight:bold"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left"
				style="width: 100%; float: none; clear: left; font-size: 0.7em">
				<fieldset>
					<legend class="legendtitle">Xuất nhập tồn</legend>
					<div style="width: 100%; float: none" align="left">
						<div style="width: 100%; float: none" align="left"
							class="plainlabel">

							<TABLE width="70%" cellpadding="6" cellspacing="0">
								<TR>
									<TD class="plainlabel">Từ ngày</TD>
									<TD class="plainlabel"><input type="text" name="Sdays"
										class="days" value='<%=obj.gettungay()%>' /></TD>
									<TD class="plainlabel">Đến ngày</TD>
									<td><input type="text" name="Edays" class="days"
										value='<%=obj.getdenngay()%>' /></td>
								</TR>
								 
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
		                        					while(rsKho.next()){ 
		                        						if( !rsKho.getString("loai").equals("7")){
		                        					%>
				                        			<tr>
			                        					<td class="plainlabel" align="left"><%= rsKho.getString("ten") %></td>
			                        					<td class="plainlabel">
			                        					<%
			                        					
			                        					if(obj.getkhoId().contains(rsKho.getString("pk_seq"))) { %>
					                    						<input type="checkbox" id="khoId" name="khoId" value="<%= rsKho.getString("pk_seq") %>" checked="checked" >
					                    					<% } else { %>
					                    						<input type="checkbox" id="khoId" name="khoId" value="<%= rsKho.getString("pk_seq") %>"  >
					                    					<% } %>
			                        					</td>
			                        				</tr>
		                        				<%}} }%>
			                        		</table>
			                        	</div>
			                        	</div>
			                        	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
        								<a class="button" href="javascript:search();">
        								<img style="top: -4px;" src="../images/button.png" alt=""> Chọn </a>
									</TD>								
								
								</TR>
								
						 
								
								<TR>
									<td colspan="4">
									<div id="btnTaoBC">
									<a class="button"
										href="javascript:submitform();"> <img style="top: -4px;"
											src="../images/button.png" alt=""> Tạo báo cáo </a>
											</div>
									</td>
								</TR>
								
							</TABLE>
						</div>
						<hr>
		
				
					</div>
				</fieldset>
			</div>
		</div>
		<br /> <br /> <br /> <br />
	</form>
</body>
</html>

<% 
	 
		if(rsKho!=null){
			rsKho.close();
		}
	 if(obj != null)  obj.DBclose(); 
	session.setAttribute("obj",null);
}%>