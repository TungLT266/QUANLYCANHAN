<%-- <%@page import="geso.dms.center.util.Utility"%> --%>
<%@page import="geso.traphaco.center.beans.sodudauky.imp.SoDuDauKyItem"%>
<%@page import="geso.traphaco.center.beans.sodudauky.imp.SoDuDauKy"%>
<%@ page import="geso.traphaco.center.util.Utility"%>
<%-- <%@page import="geso.dms.center.util.DinhKhoanKeToan"%> --%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.center.beans.sodudauky.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<!-- geso.traphaco.center.util.DinhKhoanKeToan -->
<%@ page import = "geso.traphaco.center.util.DinhKhoanKeToan" %>
<%@ page import = "geso.traphaco.center.util.IDinhKhoanKeToan" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%	
	System.out.print("");
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<%
	SoDuDauKy obj = (SoDuDauKy)session.getAttribute("obj");
	String action = (String)session.getAttribute("action");
// 	List<Erp_Item> taiKhoanList = obj.getTaiKhoanList();
// 	List<Erp_Item> taiSanList = obj.getTaiSanCDList();
// 	List<Erp_Item> loaiList = obj.getLoaiList();
	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
// 	quyen1 = util.Getquyen("58",userId);
	
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>   
<%	NumberFormat formatter1 = new DecimalFormat("#,###,###.###"); %>  
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Traphaco - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<script type="text/javascript"	src="../scripts/jquery.min.1.7.js"></script>
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>

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
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">

<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		//$("select:not(.notuseselect2)").select2({ width: 'resolve' }); 
		$(".select2").select2();
		
		$("ul.tabs").tabs("div.panes > div");
		
	});
</script>

<script type="text/javascript" src="../scripts/ajax_erp_KH_SP_CK.js"></script>

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
	
	function upload()
	{
		var vafilename = document.getElementById("filename");
		if (filename != null && filename.value == "")
		{
			document.getElementById("msg").value = "Vui lòng chọn tập tin trước khi upload";
			return;
		}
		
		document.forms['dmhForm'].action.value='upload';
		document.forms['dmhForm'].setAttribute('enctype', "multipart/form-data", 0);
	   	document.forms['dmhForm'].submit();
	}
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='new';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function ktdl()
	 {	
	 	 document.forms['dmhForm'].action.value='ktdl';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function taoDoiTuong()
	 {	
	 	 document.forms['dmhForm'].action.value='taoDoiTuong';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function submit()
	 { 		
		 document.forms['dmhForm'].action.value='submit';
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
	<form name="dmhForm" method="post" action="../../SoDuDauKyUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="<%=action %>" />
<%-- 		<input type="hidden" name="id" value="<%= obj.getId() %>" /> --%>
<!-- 			<table style="width: 100%; height: 100%;"></table> -->
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
			<%if (action.trim().equals("display")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản trị hệ thống &gt; Đưa số dư đầu kỳ &gt; Hiển thị</div>
			<%}else if (action.trim().equals("update")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản trị hệ thống &gt; Đưa số dư đầu kỳ &gt; Cập nhật</div>
			<%}else if (action.trim().equals("update_ngaykc")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản trị hệ thống &gt; Đưa số dư đầu kỳ &gt; Cập nhật - Ngày kết chuyển</div>
			<%}else{ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản trị hệ thống &gt; Đưa số dư đầu kỳ &gt; Tạo mới</div>
			<%} %>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> 
					<img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px" longdesc="Quay ve" style="border-style: outset">
				</A> 
				<span id="btnSave">
				<%if (!action.trim().equals("display")) {%> 
				<A href="javascript:saveform();"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				<%} %>
				
				<%if (!action.trim().equals("display")) {%> 
				<A href="javascript:ktdl();"> 
					<img title="Kiểm tra dữ liệu" alt="Kiểm tra dữ liệu" src="../images/vitriluu.png">
				</A>
				<%} %>
				
				<%if (!action.trim().equals("display")) {%> 
				<A href="javascript:taoDoiTuong();"> 
					<img title="Tạo mới đối tượng" alt="Tạo mới đối tượng" src="../images/New.png">
				</A>
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
					<legend class="legendtitle"> Đưa số dư đầu kỳ</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel">Chọn tập tin</TD>
								<TD class="plainlabel"><INPUT type="file" id="filename" name="filename"size="40" value='upload'></TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" colspan="4">
									<label id="btUpload">
										<a class="button" href="javascript:upload();"> <img style="top: -4px;" src="../images/button.png" alt=""> Upload </a>
									</label>
								</TD>

							</TR>
						</TABLE>
						<hr>
						
						<div align="left" style="width: 100%; float: none; clear: none;">
						<ul class="tabs">
							<%if (obj.getSheetNames() != null){ 
							for (String sheetName : obj.getSheetNames()){%>
							<li>
							<input type="hidden" value="<%= sheetName %>" name="sheetNames">
							<a href="#" style="color: red; font-weight: bold; " ><%=sheetName%></a>
							</li>
							<%}} %>
               			</ul>
						<div class="panes">
						<% 
						String preSheetName = "";
						int count = 0; 
						if(obj.getItemList().size() > 0) { 
							for(int i = 0; i < obj.getItemList().size(); i++) {
								SoDuDauKyItem item = obj.getItemList().get(i); 
								String curSheetName = item.getSheetName();
								String fontColor = item.getTrangThai() == 1 ? "black" : "red";
								%>
						<%if (!preSheetName.equals(curSheetName)) {
						int g = 0;%>
						<div>
						<TABLE class="tabledetail" width="2060px" border="0" cellpadding="1" cellspacing="1">
						
						<%if (curSheetName.contains("TKCT")){ %>
							<TR class="tbheader">
								<TH align="center" width="50px">STT</TH>
								<TH align="center" width="100px">Số chứng từ</TH>
								<TH align="center" width="100px">Ngày chứng từ</TH>
								<TH align="center" width="100px">Mã fast đối tượng</TH>
								<TH align="center" width="100px">Mã erp đối tượng</TH>
								<TH align="center" width="200px">Tên đối tượng</TH>
								<TH align="center" width="100px">Loại đối tượng</TH>
								<TH align="center" width="100px">Tài khoản nợ</TH>
								<TH align="center" width="100px">Tài khoản có</TH>
								<TH align="center" width="100px">Số tiền VND</TH>
								<TH align="center" width="100px">Ngoại tệ</TH>
								<TH align="center" width="100px">Tỉ giá</TH>
								<TH align="center" width="100px">Số tiền ngoại tệ</TH>
								
								<TH align="center" width="100px">Số hóa đơn gốc</TH>
								<TH align="center" width="100px">Ngày hóa đơn gốc</TH>
								<TH align="center" width="100px">Tài khoản PM Fast</TH>
								<TH align="center" width="100px">Số tiền hóa đơn gốc</TH>
								<TH align="center" width="200px">Lỗi</TH>
								<TH align="center" width="50px">Tạo mới đối tượng</TH>
							</TR>
						<%}else if (curSheetName.contains("TSCD")){ %>
							<TR class="tbheader">
								<TH align="center" width="50px">STT</TH>
								<TH align="center" width="100px">Số chứng từ</TH>
								<TH align="center" width="100px">Ngày chứng từ</TH>
								<TH align="center" width="100px">Mã fast đối tượng</TH>
								<TH align="center" width="100px">Mã erp đối tượng</TH>
								<TH align="center" width="200px">Tên đối tượng</TH>
								<TH align="center" width="100px">Loại đối tượng</TH>
								<TH align="center" width="100px">Tài khoản nợ</TH>
								<TH align="center" width="100px">Tài khoản có</TH>
								<TH align="center" width="100px">Số tiền VND</TH>
								<TH align="center" width="100px">Ngoại tệ</TH>
								<TH align="center" width="100px">Tỉ giá</TH>
								<TH align="center" width="100px">Số tiền ngoại tệ</TH>
								<TH align="center" width="100px">Tài khoản nợ</TH>
								<TH align="center" width="100px">Tài khoản có</TH>
								<TH align="center" width="100px">Số tiền VND</TH>
								<TH align="center" width="200px">Lỗi</TH>
								<TH align="center" width="50px">Tạo mới đối tượng</TH>
							</TR>
						<%}else if (curSheetName.contains("XDCB")){ %>
							<TR class="tbheader">
								<TH align="center" width="50px">STT</TH>
								<TH align="center" width="100px">Số chứng từ</TH>
								<TH align="center" width="100px">Ngày chứng từ</TH>
								<TH align="center" width="100px">Mã fast đối tượng</TH>
								<TH align="center" width="100px">Mã erp đối tượng</TH>
								<TH align="center" width="200px">Tên đối tượng</TH>
								<TH align="center" width="100px">Loại đối tượng</TH>
								<TH align="center" width="100px">Tài khoản nợ</TH>
								<TH align="center" width="100px">Tài khoản có</TH>
								<TH align="center" width="100px">Số tiền VND</TH>
								<TH align="center" width="200px">Lỗi</TH>
								<TH align="center" width="50px">Tạo mới đối tượng</TH>
							</TR>
						<%} %>
						<%if (curSheetName.contains("CPTT")){ %>
							<TR class="tbheader">
								<TH align="center" width="50px">STT</TH>
								<TH align="center" width="100px">Số chứng từ</TH>
								<TH align="center" width="100px">Ngày chứng từ</TH>
								<TH align="center" width="100px">Mã fast đối tượng</TH>
								<TH align="center" width="100px">Mã erp đối tượng</TH>
								<TH align="center" width="200px">Tên đối tượng</TH>
								<TH align="center" width="100px">Loại đối tượng</TH>
								<TH align="center" width="100px">Tài khoản nợ</TH>
								<TH align="center" width="100px">Tài khoản có</TH>
								<TH align="center" width="100px">Số tiền VND</TH>
								<TH align="center" width="100px">Ngoại tệ</TH>
								<TH align="center" width="100px">Tỉ giá</TH>
								<TH align="center" width="100px">Số tiền ngoại tệ</TH>
								<TH align="center" width="200px">Lỗi</TH>
								<TH align="center" width="50px">Tạo mới đối tượng</TH>
							</TR>
						<%} %>
						<%} %>
							
<%-- 							<% int count = 0;  --%>
<!-- 								if(obj.getItemList().size() > 0) {  -->
<!-- 									for(int i = 0; i < obj.getItemList().size(); i++) {  -->
<!-- 										SoDuDauKyItem item = obj.getItemList().get(i); %> -->
										
										<tr>
											<TD align="center">
												
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="<%= item.getSheetName() %>" name="sheetName">
												<input type="hidden" value="<%= item.getTrangThai() %>" name="trangThai">
												<input type="hidden" value="<%= item.getMaChiNhanh() %>" name="maChiNhanh">
												<input type="hidden" value="<%= item.getChiNhanhId() %>" name="chiNhanhId">
												<input type="hidden" value="<%= item.getDoiTuongId() %>" name="doiTuongId">
												<input type="hidden" value="<%= item.getLoaiDoiTuongId() %>" name="loaiDoiTuongId">
												<input type="hidden" value="<%= item.getIsDoiTuongNo() %>" name="isDoiTuongNo">
												<input type="hidden" value="<%= item.getTaiKhoanNoId() %>" name="taiKhoanNoId">
												<input type="hidden" value="<%= item.getTaiKhoanCoId() %>" name="taiKhoanCoId">
												<input type="hidden" value="<%= item.getTienTeId() %>" name="tienTeId">
												<input type="hidden" value="<%= item.getTaiKhoanNoId1() %>" name="taiKhoanNoId1">
												<input type="hidden" value="<%= item.getTaiKhoanCoId1() %>" name="taiKhoanCoId1">
												<input type="hidden" value="<%= item.getBTTH_Id() %>" name="BTTH_Id">
												<input type="hidden" value="<%= item.getBTTH_CT_Id() %>" name="BTTH_CT_Id">
												<input type="hidden" value="<%= item.getIsNPP() %>" name="isNPP">
												<input type="hidden" value="<%= item.getMaQuay() %>" name="maQuay">
												<input type="hidden" value="<%= item.getQuayId() %>" name="quayId">
											</TD>
											
											<TD align="center">
												<input type="text" value="<%= item.getSoChungTu() %>" name="soChungTu" style="width: 100%; color:<%=fontColor%>;">
											</TD>
											
											<TD align="center">
												<input type="text" value="<%= item.getNgayChungTu() %>" name="ngayChungTu" style="width: 100%; color:<%=fontColor%>;">  
											</TD>
											<TD align="center">
												<input type="text" value="<%= item.getMaDoiTuongFast() %>" name="maDoiTuongFast" style="width: 100%; color:<%=fontColor%>;" > 
											</TD>
											<TD align="center">
												<input type="text" value="<%= item.getMaDoiTuongErp() %>" name="maDoiTuongErp" style="width: 100%; color:<%=fontColor%>;" > 
											</TD>
											<TD align="center">
												<input type="text" value="<%= item.getTenDoiTuong() %>" name="tenDoiTuong" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center">
												<input type="text" value="<%= item.getTenLoaiDoiTuong() %>" name="tenLoaiDoiTuong" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center">
												<input type="text" value="<%= item.getSoHieuTaiKhoanNo() %>" name="soHieuTaiKhoanNo" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center">
												<input type="text" value="<%= item.getSoHieuTaiKhoanCo() %>"  name="soHieuTaiKhoanCo" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center">
												<input type="text" value="<%= formatter.format(item.getSoTienVND()) %>"  name="soTienVND" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											
											<TD align="center" style="display: <%=item.getTenTienTeDisplay()%>">
												<input type="text" value="<%= item.getTenTienTe() %>" name="tenTienTe" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center" style="display: <%=item.getTiGiaDisplay()%>">
												<input type="text" value="<%= item.getTiGia() %>"  name="tiGia" style="width: 100%; text-align: right; color:<%=fontColor%>;" onkeyup="FormatNumber(this)"> 
											</TD>
											<TD align="center" style="display: <%=item.getSoTienNgoaiTeDisplay()%>">
												<input type="text" value="<%= formatter.format(item.getSoTienNgoaiTe()) %>"  name="soTienNgoaiTe" style="width: 100%; text-align: right; color:<%=fontColor%>;" onkeyup="FormatNumber(this)"> 
											</TD>
											
											
											<TD align="center" style="display: <%=item.getSoHieuTaiKhoanNo1Display()%>">
												<input type="text" value="<%= item.getSoHieuTaiKhoanNo1() %>" name="soHieuTaiKhoanNo1" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center" style="display: <%=item.getSoHieuTaiKhoanCo1Display()%>">
												<input type="text" value="<%= item.getSoHieuTaiKhoanCo1() %>"  name="soHieuTaiKhoanCo1" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center" style="display: <%=item.getSoTienVND1Display()%>">
												<input type="text" value="<%= formatter.format(item.getSoTienVND1()) %>"  name="soTienVND1" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											
											
											
											<TD align="center" style="display: <%=item.getSoHoaDonGocDisplay()%>">
												<input type="text" value="<%= item.getSoHoaDonGoc() %>" name="soHoaDonGoc" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											<TD align="center" style="display: <%=item.getNgayHoaDonGocDisplay()%>">
												<input type="text" value="<%= item.getNgayHoaDonGoc() %>"  name="ngayHoaDonGoc" style="width: 100%; text-align: right; color:<%=fontColor%>;" readonly="readonly"> 
											</TD>
											<TD align="center" style="display: <%=item.getTaiKhoanPhanMemFastDisplay()%>">
												<input type="text" value="<%= item.getTaiKhoanPhanMemFast() %>"  name="taiKhoanPhanMemFast" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											
											<TD align="center" style="display: <%=item.getSoTienHoaDonGocDisplay()%>">
												<input type="text" value="<%= formatter.format(item.getSoTienHoaDonGoc()) %>"  name="soTienHoaDonGoc" style="width: 100%; text-align: right; color:<%=fontColor%>;"> 
											</TD>
											
											<TD align="center">
												<input type="text" value="<%= item.getMsg() %>"  name="itemMsg" style="width: 100%; text-align: right; color:<%=fontColor%>;" readonly="readonly"> 
											</TD>
											<td align="center">
				           	 				<% 	if(item.getTrangThai() == 0 && (item.getDoiTuongId() == null || item.getDoiTuongId().trim().length() == 0) 
				           	 						&& item.getLoaiDoiTuongId() != null && item.getLoaiDoiTuongId().trim().length() > 0
				           	 						&& (item.getTenLoaiDoiTuong().toLowerCase().contains("đối tượng khác") || item.getTenLoaiDoiTuong().toLowerCase().contains("nhân viên") || item.getTenLoaiDoiTuong().toLowerCase().contains("nhà cung cấp"))
				           	 						)
				           	 				{
				           	 						if (item.getIsTaoMoiDoiTuong() == 1) { %>
				           	 					<input type="checkbox" value="<%= item.getIsTaoMoiDoiTuong() %>" name = "isTaoMoiDoiTuong<%= i %>" id="isTaoMoiDoiTuong<%= i %>" checked="checked">
				           	 				<%		} else { %>
				           	 					<input type="checkbox"  value="<%= item.getIsTaoMoiDoiTuong() %>" name = "isTaoMoiDoiTuong<%= i %>" id="isTaoMoiDoiTuong<%= i %>">
				           	 				<%		}
		           	 						} else {%>
		           	 							<input type="checkbox"  value="<%= item.getIsTaoMoiDoiTuong() %>" name = "isTaoMoiDoiTuong<%= i %>" id="isTaoMoiDoiTuong<%= i %>" disabled="disabled">
		           	 						<%} %>
				           	 				</td>
										</tr>
										
<%-- 									<% count++; } --%>
<!-- 								} %> -->
<%-- 						<%if (!preSheetName.equals(curSheetName) && preSheetName.trim().length() > 0) { --%>
						<%
						if (i == obj.getItemList().size() - 1 || (i != obj.getItemList().size() - 1 &&  !curSheetName.equals(obj.getItemList().get(i + 1).getSheetName()))) {
							int h = 0;%>
						<tr class="tbfooter">
								<td colspan="18">&nbsp;</td>
							</tr>
						</TABLE>
						</div>
						<%} 
						preSheetName = curSheetName;
						%>
						<% count++; }
								} %>
					</div>
					</div>
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
</form>
</BODY>
</html>
<% } %>