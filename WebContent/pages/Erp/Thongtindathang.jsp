<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import = "java.util.Iterator" %>
<%@ page import = "java.util.List" %>
<%@ page import = "java.util.ArrayList" %>
<%@ page import = "java.sql.ResultSet" %>
<%@ page import = "geso.traphaco.erp.beans.thongtindathang.*" %>
<%@ page import="geso.traphaco.center.util.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>

<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
		IThongtindathangList obj =(IThongtindathangList)session.getAttribute("obj");
		obj.setNextSplittings();
		ResultSet thongtindathangRs = obj.getThongtindathangRs();
		ResultSet nccRs = obj.getNccRs();
		ResultSet spRs = obj.getSpRs();
		ResultSet lspRs = obj.getLspRs();
		
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ThongtindathangSvl","&loaiNPP=0&isKH=0",userId);
		
		System.out.println(quyen[0]);
		System.out.println(quyen[1]);
		System.out.println(quyen[2]);
		System.out.println(quyen[3]);	
		System.out.println(quyen[4]);
		NumberFormat formatter = new DecimalFormat("#,###,###"); 
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>Best - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
	<script language="javascript" src="../scripts/datepicker.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	$(document).ready(function() { $("select").select2(); });
	</script>
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
		$(document).ready(function(){
			$(".button3").hover(function(){
				$(".button3 img")
				.animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
				.animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
				.animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
			});
		});

	</script>

	<SCRIPT language="javascript" type="text/javascript">
		function confirmLogout()
		{
			if(confirm("Bạn có muốn đăng xuất?"))
				top.location.href = "home.jsp";
			return
		}

		function submitform()
		{
			document.forms["form"].submit();
		}

		function clearform()
		{
			document.forms["form"].spId.value = "";
			document.forms["form"].nccId.value = "";
			document.forms["form"].submit();
		}

		function thongbao()
		{
			var tt = document.forms["form"].msg.value;
			if(tt.length>0)
				alert(document.forms["form"].msg.value);
		}

		function newform() {
			document.forms['form'].action.value = 'New';
			document.forms['form'].submit();
		}

	</SCRIPT>
</head>
<body>
<form name="form" method="post" action="../../ThongtindathangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >
<input type="hidden" name="msg" value='<%=obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
	thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
		<div style="width:55%; padding:5px; float:left" class="tbnavigation">
			Dữ liệu nền > Kho vận > Thông tin lập kế hoạch
		</div>
		<div align="right" style="padding:5px" class="tbnavigation">
			Chào mừng Bạn <%= userTen %> &nbsp;
		</div>
	</div>
	<div id="cotent" style="width:100%; float:none">
		<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
			<fieldset style="margin-top:5px" >
				<legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
				<TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >

					<TR>
						<TD width="15%" class="plainlabel">Loại sản phẩm </TD>
						<TD class="plainlabel" width="25%" >
							<select name="lspId" onChange="submitform();" style="width: 250px">
								<option value=""></option>
							<% while(lspRs.next()){ 
									if(lspRs.getString("ID").equals(obj.getLspId())){
							%>	
								<option value="<%= lspRs.getString("ID")%>" SELECTED><%= lspRs.getString("LSPTEN")%></option>
								
							<%		}else{ %>	
								<option value="<%= lspRs.getString("ID")%>" ><%= lspRs.getString("LSPTEN")%></option>
							<% 		}
								}%>
							</select>
						</TD>
						
						<TD width="15%" class="plainlabel">Hình thức </TD>
						<TD class="plainlabel" width="25%">
							<select name="isMua" onChange="submitform();" style="width: 150px">
								<option value=""></option>
							  
						<%	if(obj.getIsMua().equals("1")){ %>
								
								<option value="1" SELECTED >Mua bên ngoài</option>
								<option value="0" >Sản xuất</option>
									
						<%	}else if(obj.getIsMua().equals("0")){ %>	
								<option value="1" >Mua bên ngoài</option>
								<option value="0" SELECTED >Sản xuất</option>
							 		
						<%	}else{ %>
								<option value="1" SELECTED >Mua bên ngoài</option>
								<option value="0"  >Sản xuất</option>							
						<%  } %>
							</select>
						</TD>
					</TR>	
					<TR>
						<TD width="15%" class="plainlabel">Sản phẩm </TD>
						<TD class="plainlabel" width="25%">
							<select name="spId" onChange="submitform();" style="width: 300px">
								<option value=""></option>
							<% while(spRs.next()){ 
									if(spRs.getString("ID").equals(obj.getSpId())){
							%>	
								<option value="<%= spRs.getString("ID")%>" SELECTED><%= spRs.getString("SPTEN")%></option>
								
							<%		}else{ %>	
								<option value="<%= spRs.getString("ID")%>" ><%= spRs.getString("SPTEN")%></option>
							<% 		}
								}%>
							</select>
						</TD>

						<TD width="15%" class="plainlabel">Nhà cung cấp </TD>
						<TD class="plainlabel" width="25%">
							<select name="nccId" onChange="submitform();" style="width: 300px">
								<option value=""></option>
							<% while(nccRs.next()){ 
									if(nccRs.getString("ID").equals(obj.getNccId())){
							%>	
								<option value="<%= nccRs.getString("ID")%>" SELECTED><%= nccRs.getString("NCCTEN")%></option>
								
							<%		}else{ %>	
								<option value="<%= nccRs.getString("ID")%>" ><%= nccRs.getString("NCCTEN")%></option>
							<% 		}
								}%>

							</select>
						</TD>
							
					</TR>

					<tr style="background-color:#C5E8CD">
						<td colspan="4">
							<a class="button2" href="javascript:clearform()">
								<img style="top: -4px;" src="../images/button.png" alt="">Nhập lại
							</a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						</td>
					</tr>
				</TABLE>
			</fieldset>
		</div>

		<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
			<fieldset>
			<legend>
				<span class="legendtitle">Thông tin lập kế hoạch</span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
												
				<%if(quyen[0]!=0){ %>
					<a class="button3" href="javascript:newform()"> <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới
					</a>
				<%} %>
	
			</legend>
			<TABLE width="100%" border="0" cellspacing="2" cellpadding="4">
				<TR class="tbheader">
				<% if(obj.getIsMua().equals("0")){ // Sản xuất%>
					<TH align="center" width="5%">STT</TH>
					<TH align="center" width="18%" >Sản phẩm</TH>
					<TH align="center" width="10%">Hình thức</TH>
					<TH align="center" width="8%">Số lượng mẻ</TH>
					<TH align="center" width="12%">Thời gian sản xuất trung bình (giờ)</TH>
					<TH align="center" width="10%">Vệ sinh sau sản xuất (giờ)</TH>
					<TH align="center" width="5%">Tác vụ</TH>
				<% }else{ %>
					<TH align="center" width="5%">STT</TH>
					<TH align="center" width="20%" >Sản phẩm</TH>
					<TH align="center" width="10%">Hình thức</TH>
					<TH align="center" width="18%">Nhà cung cấp</TH>
					<TH align="center" width="10%">Đặt tối thiểu</TH>
					<TH align="center" width="10%">Vận chuyển (ngày)</TH>
					<TH align="center" width="5%">Tác vụ</TH>				
				
				<% } %>
				</TR>
				<%
					int m = 0;
					if(thongtindathangRs != null){
					while (thongtindathangRs.next()){
				%>
				<%	if((m%2)== 0) {%>
				<TR class='tbdarkrow'>
				<%	}else{ %>
				<TR class='tblightrow'>
				<%	} %>
					<TD align="center"><%= m + 1 %></TD>
					
					<TD align="left"><%= thongtindathangRs.getString("TENSP") %></TD>
					
				<% if (thongtindathangRs.getString("ISMUA").equals("1")){ %>
					<TD align="center">Mua bên ngoài</TD>
					<TD align="left"><%=thongtindathangRs.getString("TENNCC")%></TD>
					<TD align="center"><%= formatter.format(Double.parseDouble(thongtindathangRs.getString("MOU"))) %></TD>
					<TD align="center"><%= thongtindathangRs.getString("LEADTIME") %></TD>
					
				<% }else{ %>
					<TD align="center">Sản xuất</TD>
					
					<TD align="center"><%= formatter.format(Double.parseDouble(thongtindathangRs.getString("LOTSIZE"))) %>
					<TD align="center"><%= formatter.format(Double.parseDouble(thongtindathangRs.getString("THOIGIANSX"))) %>
					<TD align="center"><%= formatter.format(Double.parseDouble(thongtindathangRs.getString("CLEANUP"))) %>
				<% } %>	
				
					<TD align="center">
					
						<%if(quyen[2]!=0){ %>
						<A href = "../../ThongtindathangUpdateSvl?userId=<%=userId%>&update=<%=thongtindathangRs.getString("ID")%>">
							<img src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" width="20" height="20" longdesc="Cap nhat" border = 0>
						</A>&nbsp;
						<%} %>
						
						<%if(quyen[3]!=0){ %>
						<A href = "../../ThongtindathangUpdateSvl?userId=<%=userId%>&display=<%=thongtindathangRs.getString("ID")%>">
							<img src="../images/Display20.png" alt="Hien thi" title="Hiển thị" width="20" height="20" longdesc="Hien thi" border = 0>
						</A>&nbsp;
						<%} %>
						
					</TD>
				</TR>
				<%	m++; }} %>
				<tr class="tbfooter" >
					<TD align="center" valign="middle" colspan="8" class="tbfooter">
					<%	if(obj.getNxtApprSplitting() >1) {%>
						<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;"
							onclick="View('form', 1, 'view')"> &nbsp;
					<%	}else {%>
						<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
					<%	} %>
					<% 	if(obj.getNxtApprSplitting() > 1){ %>
						<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;"
							onclick="View('form', eval(form.nxtApprSplitting.value) -1, 'view')"> &nbsp;
					<%	}else{ %>
						<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
					<%	} %>
					<%
						int[] listPage = obj.getNextSplittings();
						for(int i = 0; i < listPage.length; i++){
					%>
						<% 	if(listPage[i] == obj.getNxtApprSplitting()){ %>
							<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
						<%	}else{ %>
							<a href="javascript:View('form', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
						<%	} %>
						<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
					<%	} %>
					<% 	if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
						&nbsp;
						<img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;"
							onclick="View('form', eval(form.nxtApprSplitting.value) +1, 'view')"> &nbsp;
					<%	}else{ %>
						&nbsp;
						<img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
					<%	} %>
					<%	if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					<%	}else{ %>
						<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" 
							onclick="View('form', -1, 'view')"> &nbsp;

					<%	} %>
					</TD>
				</tr>

			</TABLE>
		</fieldset>
	</div>
</div>
</form>
</body>
</html>

<%
	try
	{
		if(lspRs != null) lspRs.close();
		if(thongtindathangRs != null)
			thongtindathangRs.close();
		if(spRs != null) spRs.close();
		if(nccRs != null) nccRs.close();
		if(obj != null)
		{
			obj.DbClose();
			obj = null;
		}
		session.setAttribute("obj", null);
	}
	catch (Exception e) {} %>
<%}%>
