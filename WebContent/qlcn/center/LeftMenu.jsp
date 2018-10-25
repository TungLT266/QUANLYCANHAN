<%@page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.sql.ResultSet"%>
<%@page import="db.Dbutils"%>

<%
String userId = (String) session.getAttribute("userId");
%>

<html>
<head>
<meta http-equiv="content-type" content="text/html; charset=UTF-8">
<title>jQuery Accordion Example</title>

<link id="sitetheme" rel="stylesheet" href="../css/style1280.css" type="text/css">
<link rel="stylesheet" href="../css/styleLeftMenu.css" type="text/css">

<script type="text/javascript" src="../scripts/jquery.js"></script>
<script type="text/javascript" src="../scripts/accordion.js"></script>
<script type="text/javascript">
	jQuery().ready(function() {
		jQuery("a").click(function(e) {
			jQuery("a").css("color", "black");
			jQuery(this).css("color", "red");
			
			if (e.target.id.indexOf('Final')>=0){
				jQuery("h5.selected").toggleClass('selected');
				jQuery('#sub'+e.target.id).addClass('head selected');
			}
		});

		// applying the settings
		jQuery('#theMenu').Accordion({
			active : 'h3.selected',
			header : 'h3.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu1').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu2').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu3').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu4').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu5').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

		jQuery('#xtraMenu6').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu7').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu8').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#xtraMenu9').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu10').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu11').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		jQuery('#xtraMenu12').Accordion({
			active : 'h4.selected',
			header : 'h4.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});
		
		jQuery('#subMenu').Accordion({
			active : 'h6.selected',
			header : 'h6.head',
			alwaysOpen : false,
			animated : true,
			showSpeed : 400,
			hideSpeed : 800
		});

	});
</script>
</head>
<body>
	<div  class="scrollbar" id="style-3" >
		<ul id="theMenu">
			<%
			Dbutils db = new Dbutils();
			String query = "SELECT A.TEN AS TENLV1, A.PARAMETERS AS THAMSOLV1, A.SERVLETS AS SVLLV1,"
					+ "\n	ISNULL(("
					+ "\n		SELECT COUNT(*) FROM MENUQLTS A1 LEFT JOIN MENUQLTS B1 ON B1.PARENTID = A1.ID AND B1.TrangThai = 1 WHERE A1.TRANGTHAI = 1 AND A1.PARENTID = A.ID"
					+ "\n	),0) AS SOLANLAP1, B.TEN AS TENLV2, B.PARAMETERS AS THAMSOLV2, B.SERVLETS AS SVLLV2,"
					+ "\n	ISNULL((SELECT COUNT(*) FROM MENUQLTS A2 WHERE A2.TRANGTHAI = 1 AND A2.PARENTID = B.ID),0) AS SOLANLAP2,"
					+ "\n 	C.TEN AS TENLV3, C.PARAMETERS AS THAMSOLV3, C.SERVLETS AS SVLLV3"
					+ "\n FROM MENUQLTS A"
					+ "\n LEFT JOIN MENUQLTS B ON B.PARENTID = A.ID AND B.TrangThai = 1"
					+ "\n LEFT JOIN MENUQLTS C ON C.PARENTID = B.ID AND C.TrangThai = 1"
					+ "\n WHERE A.LEVEL = 1 AND A.TRANGTHAI = 1 ORDER BY A.STT, B.STT, C.STT";
			System.out.println(query);
			ResultSet rs = db.get(query);
			
			int sttLv1 = 1;
			int sttCuoi = 1;
			%>
			
			<%if(rs != null) { %>
				<%while(rs.next()) { %>
					<%if(rs.getInt("SOLANLAP1") > 0) { %>
						<li style='position: static;'>
							<h3 class='head'><a href='' class='head'><%=rs.getString("TENLV1") %></a></h3>
							<ul style='display: block;'>
								<li>
									<ul id='xtraMenu<%=sttLv1 %>'>
										<%if(rs.getInt("SOLANLAP2") > 0) { %>
											<li>
												<h4 class='head' ><a href=''><%=rs.getString("TENLV2") %></a></h4>
												<ul style='display: none; max-height: 300px;'>
													<li>
														<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
															<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
														</h5>
													</li>
													<%sttCuoi++; %>
														
													<%for(int j = 1; j < rs.getInt("SOLANLAP2"); j++) { %>
														<%rs.next(); %>
														<li>
															<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
																<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
															</h5>
														</li>
														<%sttCuoi++; %>
													<%} %>
												</ul>
											</li>
										<%} else { %>
											<li>
												<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
													<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV2") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV2") %></a>
												</h5>
											</li>
											<%sttCuoi++; %>
										<%} %>
									
										<%for(int i = 1; i < rs.getInt("SOLANLAP1"); i++) { %>
											<%rs.next(); %>
											<%if(rs.getInt("SOLANLAP2") > 0) { %>
												<li>
													<h4 class='head' ><a href=''><%=rs.getString("TENLV2") %></a></h4>
													<ul style='display: none; max-height: 300px;'>
														<li>
															<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
																<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
															</h5>
														</li>
														<%sttCuoi++; %>
															
														<%for(int j = 1; j < rs.getInt("SOLANLAP2"); j++) { %>
															<%rs.next(); %>
															<li>
																<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
																	<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV3") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV3") %></a>
																</h5>
															</li>
															<%sttCuoi++; %>
														<%} %>
													</ul>
												</li>
											<%} else { %>
												<li>
													<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
														<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV2") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV2") %></a>
													</h5>
												</li>
												<%sttCuoi++; %>
											<%} %>
										<%} %>
									</ul>
								</li>
							</ul>
						</li>
					<%} else { %>
						<li>
							<h5 id='subFinal<%=sttCuoi + sttLv1 %>' class='head'>
								<a id='Final<%=sttCuoi + sttLv1 %>' href='/QUANLYCANHAN/<%=rs.getString("SVLLV1") %>?userId=<%=userId %>' target='content'><%=rs.getString("TENLV1") %></a>
							</h5>
						</li>
						<%sttCuoi++; %>
					<%} %>
					<%sttLv1++; %>
				<%} %>
				<%rs.close(); %>
			<%} %>
		</ul>
	</div>
</body>
</html>
<%
db.shutDown();
%>

