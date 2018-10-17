<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@page import="geso.traphaco.center.util.Utility"%>
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
	 ResultSet RsKh =obj.GetRsKhErp();
	 ResultSet RsKbh =obj.getkenh();
	 ResultSet RsVung =obj.getvung();
	 ResultSet RsKhuvuc =obj.getkhuvuc();
	 
	 
	 
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
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var khIds = document.getElementsByName("khIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
	 }
	 function sellectAll_kbh()
	 {
		 var chkAll = document.getElementById("chkAll_kbh");
		 var khIds = document.getElementsByName("kbhids");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll_vung()
	 {
		 var chkAll = document.getElementById("chkAll_vung");
		 var khIds = document.getElementsByName("vungids");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
	 }
	 function sellectAll_khuvuc()
	 {
		 var chkAll = document.getElementById("chkAll_khuvuc");
		 var khIds = document.getElementsByName("khuvucids");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
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
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="frm" method="post"
		action="../../ErpReportNhapXuatChitiet">
		<input type="hidden" name="action" value='1'> <input
			type="hidden" name="userId" value='<%=userId%>'>
		<div id="main" style="width: 99%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 70%; padding: 5px; float: left"
					 class="tbnavigation">Báo cáo quản trị &#62; Báo cáo khác &#62;  Xuất nhập chi tiết </div>
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
		<script type="text/javascript">
 	dropdowncontent.init('khId', "right-bottom", 300, "click");
 	dropdowncontent.init('kbhId', "right-bottom", 300, "click");
 	dropdowncontent.init('VungId', "right-bottom", 200, "click");
 	dropdowncontent.init('khuvucId', "right-bottom", 200, "click");
 	
 	
</script>
	
	
</body>
</html>

<% 
	 
	 if(obj != null)  obj.DBclose(); 
	session.setAttribute("obj",null);
}%>