	<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.donmuahang.*"%>
<%@ page import="geso.traphaco.erp.beans.donmuahang.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.denghimuahang.*"%>
<%@ page import="geso.traphaco.erp.beans.denghimuahang.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpDenghimuahang dmhBean = (IErpDenghimuahang) session.getAttribute("dmhBean");
	ResultSet dvthList = dmhBean.getDvthList();
	ResultSet loaihhList = dmhBean.getLoaiList();
	List<ISanpham> spList = dmhBean.getSpList();
	List<IDonvi> dvList = dmhBean.getDvList();
	List<ITiente> ttList = dmhBean.getTienteList();
	ResultSet rs = dmhBean.getDuyet();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	String[] cpkDienGiai = dmhBean.getCpkDienGiai();
	String[] cpkSotien = dmhBean.getCpkSoTien();
	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
	Utility util = (Utility) session.getAttribute("util");
	quyen = util.Getquyen("","57",userId);
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	quyen1 = util.Getquyen("","58",userId);
	
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>Phanam - Project</TITLE>
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
		 $('.addspeech').speechbubble();})
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
<script type="text/javascript" src="../scripts/erp-spdenghiList.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">

function replaces()
{
	
	/* if(document.getElementById("loaihh").value!=2)
	{ */
	var idsp = document.getElementsByName("idsp");
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var soluong = document.getElementsByName("soluong");
	
	//var nguongoc = document.getElementByName("nguongoc").value;
	
	var sodong =  masp.length;
	var i;
	for(i = 0; i < sodong; i++)
	{
		var slg = soluong.item(i).value.replace(/,/g,""); 
		
		if(  masp.item(i).value != ""    )
		{	
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" -- "));
			
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
			}
		}
		else 
		{
			 if(document.getElementById("loaihh").value == 0){		
					idsp.item(i).value = "";
					tensp.item(i).value = "";
						donvitinh.item(i).value = "";
//						nhomhang.item(i).value = "";
					soluong.item(i).value = "";
			 }
			 
			 // NẾU LÀ TÀI SẢN - KHÔNG CẦN NHẬP MÃ MẶC ĐỊNH SỐ LƯỢNG LÀ 1
			 if(document.getElementById("loaihh").value == 1)
			 {
				 if(tensp.item(i).value != "" && slg == "" )
				 {
					 soluong.item(i).value = "1";
				 }
				 
				 if(tensp.item(i).value == "" )
				 {
					 soluong.item(i).value = "";
				 }
			 }
		}
	}
 	
	setTimeout(replaces, 500);
}
	
replaces();
	
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
		 var dvth = document.getElementById("dvthId");
		 var nguongoc = document.getElementById("nguongoc");
		 var tiente = document.getElementById("tiente_fk");
		 var lhh = document.getElementById("loaihh");
		 var error = document.getElementById("Msg");
		 
		 if(dvth.value == "")
		 {
			 error.value="Vui lòng chọn đơn vị thực hiện";
			 dvth.focus();
			 return;
		 }
	
		 if(nguongoc.value == "")
		 {
		 	nguongoc.focus();
		 	error.value="Vui lòng chọn nguồn gốc hàng hóa";
			return;
		 }
		 
		 if(tiente.value=="")
		 {
		 	tiente.focus();
		 	error.value="Vui lòng chọn loại tiền tệ!";
			return;
		 }
		 
		 if(lhh.value=="")
		 {
			 lhh.focus();
		 	error.value="Vui lòng chọn loại loại hàng hóa!";
			return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='save';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function checkSanPham()
	 {	
		 
	 
		 var masp = document.getElementsByName("masp");
			if( document.getElementById("loaihh").value == 2 ){
				masp = document.getElementsByName("tensp");
			}
		 for(var hh = 0; hh < masp.length; hh++)
		 {
			if(masp.item(hh).value != "")
			{
				return true;
			}
		 }
		 return false;
	 }
	 
	 function submitform()
	 { 		
		 var dvth = document.getElementById("dvthId");
		 var nguongoc = document.getElementsByName("nguongoc");
		 var tiente = document.getElementById("tiente_fk");
		 var lhh = document.getElementById("loaihh");
		
		 document.forms['dmhForm'].action.value='submit';
	     document.forms["dmhForm"].submit();
	 }
	 
	 function changeNoiDung()
	 {
		 document.forms['dmhForm'].action.value='changeSP';
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
	 function changeNoiDia()
	 {
		document.forms['dmhForm'].nccId.value= "";
		document.forms['dmhForm'].action.value='checkedNoiDia';
		document.forms["dmhForm"].submit();
		
	 }
	 
	 function addRow(pos)
	{
		var table = $('#nn_'+pos);
		table.append(
	    	'<tr>'+
	       		'<td> <input type="text" name="sub_ngaynhan_'+pos+'" class="days"> </td>'+
				'<td> <input type="text" name="sub_soluong_'+pos+'"> </td>'+
	    	'</tr>'
	   			);
	}
	
	function ThemNgay(pos)
	{
		 var sl = window.prompt("Nhấp số lượng ngày muốn thêm", '');
		 if(isNaN(sl) == false && sl < 30)
		 {
			 for(var i=0; i < sl ; i++)
				 addRow(pos);
			 $( ".days" ).datepicker({			    
					changeMonth: true,
					changeYear: true				
			});
		 }
		 else
		 {
			 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa ngày');
			 return;
		 }
	}
	function LuuFile()
	{
		 document.forms["dmhForm"].action.value="download";
		 document.forms["dmhForm"].submit();
	}
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" enctype="multipart/form-data" action="../../ErpDuyetdenghimuahangUpdateSvl">
		<input type="hidden" id = "userId" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
<%-- 		<input type="hidden" name="duyet" value="<%= dmhBean.getCanDuyet() %>" /> --%>
		<input type="hidden" id = "id" name="id" value="<%= dmhBean.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Mua VT/ CPDV/ TSCĐ/ CCDC/ > Duyệt đề nghị mua hàng > Cập nhật</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave"> 
				<A href="javascript:saveform();"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				</span>
				
<%-- 							<% if(dmhBean.getCanDuyet().equals("0")) { %> --%>
								<%if(quyen[3]!=0){ %>
			                                 <A id='chotphieu<%=dmhBean.getId()%>'
										      href=""><img
										      src="../images/Chot.png" alt="Chốt"
										      width="30" height="30" title="Chốt" style="border-style: outset"  border="1px"
										      border="0" onclick="if(!confirm('Bạn có muốn chốt đơn hàng này?')) {return false ;}else{ processing('<%="chotphieu"+dmhBean.getId()%>' , '../../ErpDenghimuahangSvl?userId=<%=userId%>&chot=<%= dmhBean.getId()%>');}"  >
										    </A>
									    <%} %>
<%-- 									<% } %> --%>
									
									<A href="../../ErpDuyetdenghimuahangUpdateSvl?userId=<%=userId %>&id=<%= dmhBean.getId() %>&task=print" >
						<img src="../images/Printer30.png" alt="Print"  title="Print" border="1" longdesc="Print" style="border-style:outset"></A>
									
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Đề nghị mua hàng</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
						
								<TD class="plainlabel" valign="top" width="150px">Ngày đề nghị</TD>
								<TD class="plainlabel" valign="top" colspan =3 >
									<input type="text" class="days" id="ngaydenghi" name="ngaydenghi"
											value="<%= dmhBean.getNgaydenghi() %>" maxlength="10" readonly />
								</TD>                      
								
							</TR>
							<TR>
								<TD class="plainlabel">Đơn vị thực hiện</TD>
								<TD class="plainlabel" width="280px">
									<select name="dvthId" id="dvthId" >
										<option value=""></option>
										<% if (dvthList != null)
										{
											while (dvthList.next())
											{
												if (dvthList.getString("pk_seq").equals(dmhBean.getDvthId()))
												{
												%>
													<option value="<%=dvthList.getString("pk_seq")%>" selected="selected"><%=dvthList.getString("ten")%></option>
												<% } else { %>
													<option value="<%=dvthList.getString("pk_seq")%>"><%=dvthList.getString("ten")%></option>
										<% } } dvthList.close(); } %>
									</select>
								</TD>	
								<TD class="plainlabel" width="150px">Nguồn gốc</TD>
								<TD class="plainlabel">
									<select name="nguongoc" id="nguongoc" onChange="submitform();">
											<option value=""></option>
											<% if (dmhBean.getNguonGocHH().equals("TN")) { %>
												<option value="TN" selected="selected">Nội địa</option>
												<option value="NN">Nhập khẩu</option>
											<% } else if (dmhBean.getNguonGocHH().equals("NN")) { %>
												<option value="TN">Nội địa</option>
												<option value="NN" selected="selected">Nhập khẩu</option>
											<% } else { %>
												<option value="TN">Nội địa</option>
												<option value="NN">Nhập khẩu</option>
											<% } %>
									</select>
									
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" >Tiền tệ</TD>
								<TD class="plainlabel" >
									<Select name="tiente_fk" id="tiente_fk" onChange="submitform();">
										<option value=""></option>
										<% if (ttList.size() > 0)
											{
												int size = ttList.size();
												for (int i = 0; i < size; i++)
												{
													ITiente t = (Tiente) ttList.get(i);
													if (dmhBean.getTienTe_FK() != null && dmhBean.getTienTe_FK().equals(t.getId()))
													{  %>
														<option value='<%= t.getId() %>' selected="selected"><%= t.getMa() %></option>
													<% } else { %>
														<option value='<%= t.getId() %>'><%= t.getMa() %></option>
										<% } } } %>
									</Select>
								</TD>
								<TD class="plainlabel">Loại hàng hóa</TD>
								<TD class="plainlabel">
								<%-- <% if(dmhBean.getNccLoai().equals("100003")) { %>
									<input type="text" value="Sản phẩm nhập kho" readonly="readonly" >
									<input name="loaihh" id="loaihh" type="hidden" value="0"  >
								<% } else { %> --%>
									<select name="loaihh" id="loaihh" onChange="changeNoiDung();">
										<option value=""></option>
									<% if(dmhBean.getLoaihanghoa().equals("1")) { %>
										<option value="1" selected="selected">Tài sản cố định</option>
										<option value="0">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
										<option value="2">Chi phí dịch vụ</option>
									<% } else if(dmhBean.getLoaihanghoa().equals("2")){ %>
										<option value="2"  selected="selected">Chi phí dịch vụ</option>
										<option value="1">Tài sản cố định</option>
										<option value="0">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
									<% } else  if(dmhBean.getLoaihanghoa().equals("0")){  %> 
										<option value="0" selected="selected">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
									<%} else {%>
										<option value="0">Nhập kho CCDC, VT, Phụ tùng thay thế</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
									<%} %>
									</select>
								<%-- <% } %> --%>
								
									
									
								</TD>
								
<%-- 								<% if(dmhBean.getLoaihanghoa().equals("0")) { %> --%>
<!-- 									<TD class="plainlabel"> </TD> -->
<!-- 									<TD class="plainlabel"> -->
									<%-- <% if(dmhBean.getNccLoai().equals("100003")) { %>
										<input type="hidden" name="loaisp" id="loaisp" value="<%= dmhBean.getLoaispId() %>" >
										<select  disabled="disabled" >
											<option value=""></option>
											<% if (loaihhList != null)
											   {
													while (loaihhList.next())
													{
														if (loaihhList.getString("pk_seq").equals(dmhBean.getLoaispId()))
														{ %>
														<option value="<%=loaihhList.getString("pk_seq")%>" selected="selected"><%=loaihhList.getString("ten")%></option>
											<% } else { %>
												<option value="<%=loaihhList.getString("pk_seq")%>"><%=loaihhList.getString("ten")%></option>
											<% } } loaihhList.close(); } %>
										</select>
									<% } else { %> --%>
										<select name="loaisp" id="loaisp"  style=" display: none" onChange="submitform();">
<!-- 											<option value=""></option> -->
<%-- 											<% if (loaihhList != null) --%>
<!-- // 											   { -->
<!-- // 													while (loaihhList.next()) -->
<!-- // 													{ -->
<!-- // 														if (loaihhList.getString("pk_seq").equals(dmhBean.getLoaihanghoa())) -->
<%-- 														{ %> --%>
<%-- 														<option value="<%=loaihhList.getString("pk_seq")%>" selected="selected"><%=loaihhList.getString("ten")%></option> --%>
<%-- 											<% } else { %> --%>
<%-- 												<option value="<%=loaihhList.getString("pk_seq")%>"><%=loaihhList.getString("ten")%></option> --%>
<%-- 											<% } } loaihhList.close(); } %> --%>
<!-- 										</select> -->
<%-- 									<% } %> --%>
												
										
<!-- 									</TD> -->
<%-- 								<% } else { %> --%>
<!-- 									<td class="plainlabel" colspan="2">&nbsp;</td> -->
<%-- 								<% } %> --%>
							</TR>
							
							<TR>
								<TD class="plainlabel">Ghi chú</TD>
								<TD class="plainlabel" >
									<input type="text" name="ghichu" value="<%= dmhBean.getGhiChu() %>" style="width: 200px" />
								</TD>
								<TD class="plainlabel">Công văn được ký</TD>
								<TD class="plainlabel" >
									<!-- <INPUT type="file" name="filename" value=""> -->
						  	  		<input type="hidden" id="valueten" name="congvan" value="<%= dmhBean.getCongvan() %>">
						  	  		<% if(dmhBean.getCongvan().length() > 0 ) {	%>
						  	  			<div id="tenfilea"><p><%= dmhBean.getCongvan() %><img src="../images/Save20.png" onclick="LuuFile();" style="cursor: pointer;" alt="Xóa" width="20" height="20" longdesc="Cap nhat" border = 0></p></div>
						  	  		<%} %>
								</TD>
							</TR>
							
						</TABLE>
						<hr>
					</div>
					
					<%  if(dmhBean.getLoaihanghoa().trim().length() > 0 ) { %>
					
					<div align="left" style="width: 100%; float: none; clear: none;">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="30px">STT</TH>
							<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
									<TH align="center" width="10%" >Mã hàng mua</TH>
									<TH align="center" width="15%"> Tên hàng mua</TH>
								<% } else if(dmhBean.getLoaihanghoa().trim().equals("1")) { %> 
									<TH align="center" width="10%" >Mã tài sản</TH>
									<TH align="center" width="15%"> Diễn giải</TH>
								<% } else if (dmhBean.getLoaihanghoa().trim().equals("2")){ %>
									<%--  <%if(quyen1[0]!=0){ %>
									<TH align="center" width="10%" >Mã chi phí</TH>
									<%}else{ %>
									<TH align="center" width="10%" style="display: none;">Mã chi phí</TH>
									<%} %> --%>
									<TH align="center" width="10%" >Mã chi phí</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								 <% } else if(dmhBean.getLoaihanghoa().trim().equals("3")){%>
								 	<TH align="center" width="10%" >Mã CCDC</TH>
									<TH align="center" width="15%">Tên</TH>
								 <%}  %>
								
								
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="5%">Số lượng</TH>
								<TH align="center" width="5%">Số lượng duyệt</TH>
								

<%-- 								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %> --%>
<!-- 									<TH align="center" width="10%" style="display: none;" >Nhóm hàng</TH> -->
<%-- 								<% } else { if(dmhBean.getLoaihanghoa().trim().equals("1")) { %>  --%>
<!-- 									<TH align="center" width="10%" style="display: none;">Nhóm tài sản</TH> -->
<%-- 								<% } else { %> --%>
<!-- 									<TH align="center" width="10%" style="display: none;">Nhóm chi phí</TH> -->
<%-- 								 <% } } %> --%>
								
								
							</TR>
							
							<% int count = 0; 
								if(spList.size() > 0) { 
									for(int i = 0; i < spList.size(); i++) { 
										ISanpham sp = spList.get(i); %>
										<tr>
											<TD align="center" width="2%">
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="<%= sp.getPK_SEQ() %>" name="idsp" style="width: 100%" readonly="readonly" >
												<input type="hidden" value="<%= sp.getMNLId() %>" name="mnlId" style="width: 100%" readonly="readonly" >  
											</TD>
											<%-- <%if(  dmhBean.getLoaihanghoa().trim().equals("0") || dmhBean.getLoaihanghoa().trim().equals("2") ){ %> --%>
											<TD  align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%" value="<%= sp.getMasanpham() %>"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
											</TD>
											<%-- <%}else{ %>
											<TD      align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%" value="<%= sp.getMasanpham() %>"  readonly="readonly" > 
											</TD>
											<%} %> --%>
										 
											<TD align="left" width="10%">
												<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { System.out.println("_____TEN SP: " + sp.getTensanpham()); %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" readonly="readonly" > 
												<% } else {  %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" > 
												<%  } %>
											</TD>
											<TD align="center" width="6%">
												<input type="text" value="<%= sp.getDonvitinh() %>" name="donvitinh" style="width: 100%" > 
											</TD>
											<td>
												<input type="text" value="<%= sp.getSoluong() %>" name="soluong" style="width: 100%; text-align: right;" readonly="readonly" > 
											</td>
											<TD align="center" width="7%">
												
												<input type="text" value="<%= sp.getSoluongduyet() %>" name="soluongduyet" style="width: 100%; text-align: right;" >
											</TD>
											
											
										</tr>
										
									<% count++; }
								} %>
							
							<% for(int i = count; i < 40; i++) { %>
								<tr>
									<TD align="center" width="2%">
										<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
										<input type="hidden" value="" name="idsp" style="width: 100%" readonly="readonly" > 
										<input type="hidden" value="" name="mnlId" style="width: 100%" readonly="readonly" > 
									</TD>
									
									<%-- <%if(  dmhBean.getLoaihanghoa().trim().equals("0") || dmhBean.getLoaihanghoa().trim().equals("2") ){ %> --%>
										<TD  align="center" width="8%" >
											<input type="text" value="" name="masp" style="width: 100%" onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
										</TD>
									<%-- <%}else{ %>
										<TD   align="center" width="8%" >
											<input type="text" value="" name="masp" style="width: 100%" readonly="readonly" > 
										</TD>
									<%} %> --%>
									<TD align="left" width="10%">
									<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
										<input type="text" value="" name="tensp" style="width: 100%" readonly="readonly" > 
									<% } else { %>
										<input type="text" value="" name="tensp" style="width: 100%" > 
									<% } %>
									</TD>
									<TD align="center" width="6%">
										<input type="text" value="" name="donvitinh" style="width: 100%" > 
									</TD>
									<td>
										<input type="text" value="" name="soluong" style="width: 100%; text-align: right;"  readonly="readonly" > 
									</td>
									<TD align="center" width="7%">
										
										<input type="text" value="" name="soluongduyet" style="width: 100%; text-align: right;" > 
									</TD>
									
									
								</tr>
							<% } %>
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					
					<% } %>
					
				</fieldset>
			</div>
		</div>
	<script type="text/javascript">
		replaces();
		dropdowncontent.init("noidungGhiChu", "right-top", 500, "click");
			
	</script>

	<%
		dmhBean.close();
	%>
</form>
</BODY>
</html>