<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="qlcn.center.util.Utility"%>
<%@page import="db.Dbutils"%>
<%@page import="java.sql.ResultSet"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

Dbutils db = new Dbutils();
String query = "select sotien from TAIKHOAN where id=110007 and USERID = " + userId;
ResultSet rs;
String str = "";
int i = 0;
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<title></title>
<meta http-equiv="Content-Style-Type" content="text/css">
<link rel="stylesheet" href="../css/main.css" type="text/css">
<link rel="stylesheet" href="../css/calendar.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

<script language="JavaScript" type="text/javascript">
	function replaces() {
		var xmlhttp;
		if (window.XMLHttpRequest) {
			xmlhttp = new XMLHttpRequest();
		} else {
			xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
		}

		xmlhttp.onreadystatechange = function() {
			if (xmlhttp.readyState == 4 && xmlhttp.status == 200) {
				var taikhoan = xmlhttp.responseText;
				if(taikhoan.length > 5){
					document.getElementById('tongtien').innerHTML = "&nbsp;&nbsp;&nbsp;<b>Tổng tiền: </b>" + taikhoan.substr(0, taikhoan.indexOf("[==]"));
					taikhoan = taikhoan.substr(taikhoan.indexOf("[==]")+4);
					document.getElementById('tongthu').innerHTML = "&nbsp;&nbsp;&nbsp;<b>Tổng thu: </b>" + taikhoan.substr(0, taikhoan.indexOf("[==]"));
					taikhoan = taikhoan.substr(taikhoan.indexOf("[==]")+4);
					document.getElementById('tongchi').innerHTML = "&nbsp;&nbsp;&nbsp;<b>Tổng chi: </b>" + taikhoan;
				}
			}
		}

		xmlhttp.open("GET", "/QUANLYCANHAN/AjaxTop?userId="+<%=userId %>, true);
		xmlhttp.send();

		setTimeout(replaces, 2000);
	}
</script>

</head>
<body leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="topForm" method="post" action="/QUANLYCANHAN/LogoutSvl">
		<table width="100%" border="0" cellspacing="0" cellpadding="0">
			<tr style="height: 30px">
				<td id="tongtien" align="left" style="font-size: 15px;" colspan="2">
					<!-- <img src="../images/salesup_erp.jpg" width="140" height="50" align="right"> &nbsp;&nbsp; <img src="../images/logo.gif" height="30" align="right" style="padding-top: 10px;" /> &nbsp; -->
					<!-- &nbsp;&nbsp;&nbsp;<b>Tổng tiền:</b>
					<input type="text" name="tongtien" value=""> -->
				</td>
				<td align="right" class="blanc" rowspan="2">
					<a href="../../LogoutSvl" target="_parent">Đăng xuất&nbsp;&nbsp;</a>
					<!-- <div>
						<iframe style="width: 200px; height: 20px" id="frame1" src="counter.jsp" frameborder="0" scrolling="no"></iframe>
					</div> -->
				</td>
			</tr>
			
			<tr>
				<td id="tongthu" align="left" style="font-size: 15px"></td>
				<td id="tongchi" align="left" style="font-size: 15px"></td>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
		replaces();
	</script>
	
	<%-- <% while(true){ %>
		<%i++; %>
		<script language="JavaScript" type="text/javascript">
			document.forms['topForm'].tongtien.value = "<%=i %>";
		</script>
	<%} %> --%>
</body>
</html>


