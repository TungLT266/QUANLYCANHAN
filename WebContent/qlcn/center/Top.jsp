<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="qlcn.center.util.Utility"%>
<%@page import="db.Dbutils"%>
<%@page import="java.sql.ResultSet"%>

<%
Utility util = new Utility();
String userTen = (String) session.getAttribute("userTen");
String userId = (String) session.getAttribute("userId");

Dbutils db = new Dbutils();
String query = "select ID, ten from TAIKHOAN where trangthai=1 and USERID = " + userId;
ResultSet rs = db.getScroll(query);
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
					document.getElementById('tongtien').innerHTML = taikhoan.substr(0, taikhoan.indexOf("[==]"));
					taikhoan = taikhoan.substr(taikhoan.indexOf("[==]")+4);
					document.getElementById('tongthu').innerHTML = taikhoan.substr(0, taikhoan.indexOf("[==]"));
					taikhoan = taikhoan.substr(taikhoan.indexOf("[==]")+4);
					
					if(taikhoan.includes("[==]")){
						document.getElementById('tongchi').innerHTML = taikhoan.substr(0, taikhoan.indexOf("[==]"));
					} else {
						document.getElementById('tongchi').innerHTML = taikhoan;
					}
					
					while(1){
						if(!taikhoan.includes("[==]")){
							document.getElementById(taikhoan.substr(0, taikhoan.indexOf(",,"))).innerHTML = taikhoan.substr(taikhoan.indexOf(",,")+2);
							break;
						}
						
						taikhoan = taikhoan.substr(taikhoan.indexOf("[==]")+4);
						document.getElementById(taikhoan.substr(0, taikhoan.indexOf(",,"))).innerHTML = taikhoan.substr(taikhoan.indexOf(",,")+2, taikhoan.indexOf("[==]")-8);
					}
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
		<table width="100%" border="0" cellspacing="1" cellpadding="4">
			<tr>
				<th class="tbheader">Tổng tiền</th>
				<th class="tbheader">Tổng thu tháng</th>
				<th class="tbheader">Tổng chi tháng</th>
				<%if(rs != null){ %>
					<%while(rs.next()){ %>
						<th class="tbheader"><%=rs.getString("ten") %></th>
					<%} %>
				<%} %>
				<td align="right" class="blanc" rowspan="2">
					<a href="../../LogoutSvl" target="_parent">Đăng xuất&nbsp;&nbsp;</a>
				</td>
			</tr>
			
			<tr>
				<th id="tongtien" align="center"></th>
				<td id="tongthu" align="center"></td>
				<td id="tongchi" align="center"></td>
				<%rs.beforeFirst(); %>
				<%if(rs != null){ %>
					<%while(rs.next()){ %>
						<td id="<%=rs.getString("id") %>" align="center"></td>
					<%} %>
				<%} %>
			</tr>
		</table>
	</form>

	<script type="text/javascript">
		replaces();
	</script>
</body>
</html>


