<%-- <%@page import="geso.dms.center.util.Utility"%> --%>
<%@page import="geso.traphaco.erp.beans.masclon.imp.Erp_MaSCLon"%>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%-- <%@page import="geso.dms.center.util.DinhKhoanKeToan"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.xuatdungccdc.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<!-- geso.traphaco.center.util.DinhKhoanKeToan -->
<%@ page import = "geso.traphaco.center.util.DinhKhoanKeToan" %>
<%@ page import = "geso.traphaco.center.util.IDinhKhoanKeToan" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	Erp_MaSCLon obj = (Erp_MaSCLon)session.getAttribute("obj");
	String action = (String)session.getAttribute("action");
	List<Erp_Item> taiKhoanList = obj.getTaiKhoanList();
	List<Erp_Item> taiSanList = obj.getTaiSanCDList();
	List<Erp_Item> loaiList = obj.getLoaiList();
	List<Erp_Item> dvkdList= obj.getDvkdList();
	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
	quyen1 = util.Getquyen("Erp_MaSCLonUpdateSvl","",userId);
	
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>   
<%	NumberFormat formatter1 = new DecimalFormat("#,###,###.###"); %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoHYERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<style type="text/css">
#mainContainer {
	width: 660px;
	margin: 0 auto;
	text-align: left;
	height: 100%;
	border-left: 3px double #000;
	border-right: 3px double #000;
}

#formContent {
	padding: 5px;
}
/* END CSS ONLY NEEDED IN DEMO */

/* Big box with list of options */
#ajax_listOfOptions {
	position: absolute; /* Never change this one */
	width: auto; /* Width of box */
	height: 200px; /* Height of box */
	overflow: auto; /* Scrolling features */
	border: 1px solid #317082; /* Dark green border */
	background-color: #C5E8CD; /* White background color */
	color: black;
	text-align: left;
	font-size: 1.0em;
	z-index: 100;
}

#ajax_listOfOptions div {
	/* General rule for both .optionDiv and .optionDivSelected */
	margin: 1px;
	padding: 1px;
	cursor: pointer;
	font-size: 1.0em;
}

#ajax_listOfOptions .optionDiv { /* Div for each item in list */
	
}

#ajax_listOfOptions .optionDivSelected { /* Selected item in the list */
	background-color: #317082; /*mau khi di chuyen */
	color: #FFF;
}

#ajax_listOfOptions_iframe {
	background-color: #F00;
	position: absolute;
	z-index: 5;
}

form {
	display: inline;
}

#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 150px;
	border: 1px solid black;
	padding: 2px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135
		);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
}
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript">
	jQuery(function($){ 
		 $('.addspeech').speechbubble();});
</script>
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
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spXuatDungVatTuList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

<script language="javascript" type="text/javascript">

	
	
	function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
		 
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
		}
		
		if(isNaN(num))
		num = "0";
		sign = (num == (num = Math.abs(num)));
		num = Math.floor(num*100);
		num = Math.floor(num/100).toString();
		for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
		num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));

		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		//alert(kq);
		return kq;
	}
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	 function saveform()
	 {	
		 var ma = document.getElementById("ma");
		 var ten = document.getElementById("ten");
		 var giaTri = document.getElementById("giaTri");
		 var loaiId = document.getElementById("loaiId");
		 var taiKhoanId = document.getElementById("taiKhoanId");
		 var taiSanId = document.getElementById("taiSanId");
		 var error = document.getElementById("msg");
		 
		 
		 if(ma == null || ma.value=="")
		 {
			 ma.focus();
			 error.value = 'Vui lòng nhập "Mã SC/XDCB"!';
			 return;
		 }
		 if(ten == null || ten.value=="")
		 {
			 ten.focus();
			 error.value = 'Vui lòng nhập "Tên hạng mục"!';
			 return;
		 }
// 		 if(giaTri == null || giaTri.value=="")
// 		 {
// 			 giaTri.focus();
// 			 error.value = 'Vui lòng nhập "Giá tri"!';
// 			 return;
// 		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
// 	 	 document.forms['dmhForm'].action.value='new';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function loadTaiSan()
	 {	
		 console.log("load ts: ");
	 	 document.forms['dmhForm'].action.value='loaiTaiSan';
	     document.forms['dmhForm'].submit();
	 }
	 

	 function submit()
	 { 		
		
		 document.forms['dmhForm'].action.value='submit';
	     document.forms["dmhForm"].submit();
	 }
	 
	 function changeKho(khoIndex)
	 { 		
		 document.forms['dmhForm'].action.value='changeKho_' + khoIndex;
	     document.forms["dmhForm"].submit();
	 }
	 
	 function goBack()
	 {
	  	window.history.back();
	 }

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../Erp_MaSCLonUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="<%=action %>" />
		<input type="hidden" name="id" value="<%= obj.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
			<%if (action.trim().equals("display")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý tài sản &gt; Mã SC lớn/XDCB TSCD &gt; Hiển thị</div>
			<%}else if (action.trim().equals("update")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý tài sản &gt; Mã SC lớn/XDCB TSCD &gt; Cập nhật</div>
			<%}else if (action.trim().equals("update_ngaykc")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý tài sản &gt; Mã SC lớn/XDCB TSCD &gt; Cập nhật - Ngày kết chuyển</div>
			<%}else{ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý tài sản &gt; Mã SC lớn/XDCB TSCD &gt; Tạo mới</div>
			<%} %>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave">
				<%if (!action.trim().equals("display")) {%> 
				<A href="javascript:saveform();"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				<%} %>
				</span>
				
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="msg" rows="1" readonly="readonly" style="width: 100%%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Mã SC lớn/XDCB TSCD</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							
							<% if (action.trim().equals("update_ngaykc")||action.trim().equals("display")){ %>
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày kết chuyển</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" class="days" name="ngayketchuyen" id="ngayketchuyen" value="<%=obj.getNgayChuyen() %>" style="text-align: left;">
								</TD>
							</TR>
							<% } %>
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Mã SC/XDCB</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="ma" id="ma" value="<%=obj.getMa() %>"  <%if(action.trim().equals("update_ngaykc")) { %>
									readonly="readonly" 
									<%}else {} %>
									style="text-align: left;">
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Tên hạng mục</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="ten" id="ten" value="<%=obj.getTen() %>" <%if(action.trim().equals("update_ngaykc")) { %>
									readonly="readonly" 
									<%}else {} %> style="text-align: left; width: 400px;">
								</TD>
							</TR>						
<%-- 							<%if (action.trim().equals("display")){ %> --%>
<!-- 							<TR> -->
<!-- 								<TD class="plainlabel" valign="top" width="150px">Ngày chuyển</TD> -->
<!-- 								<TD class="plainlabel" valign="top"  colspan="3" > -->
<%-- 									<input type="text" name="ngayChuyen" class="days" id="ngayChuyen" value="<%=obj.getNgayChuyen() %>" style="text-aligns: right;" readonly="readonly"> --%>
<!-- 								</TD> -->
<!-- 							</TR> -->
<%-- 							<%} %> --%>
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Giá trị</TD>
								<TD class="plainlabel" valign="top"  colspan="3" >
									<input type="text" name="giaTri" id="giaTri" value="<%=formatter.format(obj.getGiaTri()) %>" <%if(action.trim().equals("update_ngaykc")) { %>
									readonly="readonly" 
									<%}else {} %> style="text-align: right;" onkeyup="FormatNumber(this)" readonly="readonly">
								</TD>
							</TR>
							

							<tr>
								<TD class="plainlabel" valign="top" width="150px">Loại</TD>
								<TD class="plainlabel" valign="top"  colspan="3">
								<% if(action.trim().equals("update_ngaykc")) { %>
									<input type="hidden" name="loaiId" id="loaiId" value="<%= obj.getLoaiId() %>" />
								<% } %>
									<select name="loaiId" id="loaiId" style="width: 500px;" <%if(action.trim().equals("update_ngaykc")) { %>
									disabled
									<%}else {} %>  onChange="loadTaiSan();"">
										<option value=""></option>
										<% 
										if (loaiList != null)
										{
											for (Erp_Item item : loaiList)
											{
												if (item.getValue().equals(obj.getLoaiId() + ""))
												{
												%>
													<option value="<%=item.getValue()%>"  selected="selected"><%=item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } }} %>
									</select>
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px"> Tài khoản kế toán</TD>
								<TD class="plainlabel" valign="top"  colspan="3">
								<% if(action.trim().equals("update_ngaykc")) { %>
								    <input type="hidden" name="taiKhoanId" id="taiKhoanId" value="<%= obj.getTaiKhoanId() %>" />
								<% } %>
									<select name="taiKhoanId" id="taiKhoanId" <%if(action.trim().equals("update_ngaykc")) { %>
									disabled
									<%}else {} %>  style="width: 500px;">
										<option value=""></option>
										<% if (taiKhoanList != null)
										{
											for (Erp_Item item : taiKhoanList)
											{
												if (item.getValue().equals(obj.getTaiKhoanId()))
												{
												%>
													<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } }} %>
									</select>
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" valign="top" width="150px"> Tài sản cố định</TD>
								<TD class="plainlabel" valign="top"  colspan="3">
								<% if(action.trim().equals("update_ngaykc")) { %>
									<input type="hidden" name="taiSanId" id="taiSanId" value="<%= obj.getTaiSanId() %>" />
								<% } %>
									<select name="taiSanId" id="taiSanId" <%if(action.trim().equals("update_ngaykc")) { %>
									disabled
									<%}else {} %>  style="width: 500px;">
										<option value=""></option>
										<% if (taiSanList != null)
										{
											for (Erp_Item item : taiSanList)
											{
												if (item.getValue().equals(obj.getTaiSanId()))
												{
												%>
													<option value="<%=item.getValue()%>" selected="selected"><%=item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } }} %>
									</select>
								</TD>
							</TR>
						</TABLE>
						<hr>
					</div>
				</fieldset>
			</div>
		</div>
<script type="text/javascript">
	replaces();
	dropdowncontent.init("noidungGhiChu", "right-top", 500, "click");
	dropdowncontent.init("chiphiKHAC", "left-top", 500, "click");
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	jQuery(function()
	{		
		$("#nccId").autocomplete("ErpNccMuaHangListGiay.jsp");
	});	
	
</script>
<script type="text/javascript">
dropdowncontent.init("ktlist", "right-bottom", 250, "click");
// 	dropdowncontent.init('duyetId', "right-bottom", 300, "click");
</script>	

<script type="text/javascript">
// 	for(var i = 0; i < 40; i++) {
// 		dropdowncontent.init('tenhd_'+i, "left-top", 300, "click");
// 	}
</script>

	<%
	if(taiKhoanList!=null)taiKhoanList.clear();
	if(taiSanList!=null)taiSanList.clear();
	if(loaiList!=null)loaiList.clear();
		obj.DbClose();
		session.removeAttribute("obj");
	%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>
<% } %>