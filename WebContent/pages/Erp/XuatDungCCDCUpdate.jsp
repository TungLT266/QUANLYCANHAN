<%-- <%@page import="geso.dms.center.util.Utility"%> --%>
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
	System.out.println("");
	Erp_XuatDungCCDC obj = (Erp_XuatDungCCDC)session.getAttribute("obj");
	String action = (String)session.getAttribute("action");
	List<Erp_Item> khoList = obj.getKhoList();
	List<Erp_Item> ccdcList = obj.getCcdcList();
	List<Erp_VatTu> vatTuList = obj.getVatTuList();
	int[] quyen = new  int[5];
	int[] quyen1 = new  int[5];
	/* //quyen = util.Getquyen("57",userId); */
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	quyen1 = util.Getquyen("58",userId);
	
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

function checkKho(index)
{
	var khoId = document.getElementsByName("khoId");
	var error = document.getElementById("msg");
	if (khoId.item(index).value == "")
	{
		khoId.item(index).focus();
		error.value = ('Vui lòng chọn "Kho" trước khi nhập "Mã vật tư"');
	}
}

function replaces()
{
// 	console.log("inside");
	var idsp = document.getElementsByName("sanPhamId");
	var masp = document.getElementsByName("maSanPham");
	var tensp = document.getElementsByName("tenVatTu");
	var donvitinh = document.getElementsByName("donViTinh");
	var soluong = document.getElementsByName("soLuong");
	var tonHienTai = document.getElementsByName("tonHienTai");
	
	var sodong =  masp.length;
	var i;
	for(i = 0; i < sodong; i++)
	{
		if( masp.item(i).value != "")
		{
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" -- "));
			var slg = soluong.item(i).value.replace(/,/g,""); 
			
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
				{
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				}
				
				
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var nhomHang = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				tonHienTai.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
			}
			
			soluong.item(i).value = slg;
// 			if(dongia.item(i).value != "")
// 			{
// 				var dgia = dongia.item(i).value.replace(/,/g,""); 
// 				dongia.item(i).value = dgia;
				
// 				var tt = parseFloat(slg) * parseFloat(dgia);
// 				thanhtien.item(i).value =  DinhDangDonGia( Math.round( tt * 1000 ) / 1000 );
// 			}
		}
// 		console.log("DinhDangDonGia(tonHienTai.item(i).value): " + DinhDangDonGia(tonHienTai.item(i).value));
		tonHienTai.item(i).value = DinhDangDonGia(Math.round(parseFloat(tonHienTai.item(i).value.replace(/,/g,""))* 1000 ) / 1000);
		soluong.item(i).value = DinhDangDonGia(Math.round(parseFloat(soluong.item(i).value.replace(/,/g,""))* 1000 ) / 1000);
	}
	
// 	TinhTien();
	setTimeout(replaces, 500);
}

function replacesOLD()
{
// 	console.log("inside");
	var idsp = document.getElementsByName("sanPhamId");
	var masp = document.getElementsByName("maSanPham");
	var tensp = document.getElementsByName("tenVatTu");
	var donvitinh = document.getElementsByName("donViTinh");
	var soluong = document.getElementsByName("soLuong");
	var dongia = document.getElementsByName("donGia");
	var thanhtien = document.getElementsByName("thanhTien");
	
	var sodong =  masp.length;
	var i;
	for(i = 0; i < sodong; i++)
	{
		if( masp.item(i).value != "")
		{
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" -- "));
			var slg = soluong.item(i).value.replace(/,/g,""); 
			
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
				{
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				}
				
				
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var nhomHang = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				
				idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
			}
			
			soluong.item(i).value = slg;
			if(dongia.item(i).value != "")
			{
				var dgia = dongia.item(i).value.replace(/,/g,""); 
				dongia.item(i).value = dgia;
				
				var tt = parseFloat(slg) * parseFloat(dgia);
				thanhtien.item(i).value =  DinhDangDonGia( Math.round( tt * 1000 ) / 1000 );
			}
		}
	}
	
	TinhTien();
	setTimeout(replaces, 500);
}
	
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
		 var ccdcId = document.getElementById("ccdcId");
		 var ghiChu = document.getElementById("ghiChu");
		 var error = document.getElementById("msg");
		 var tenCC = $("#ccdcId option:selected").text();;
		 
		 console.log("tenCCs: " + tenCC);
		 
		 if(tenCC.includes("C--") == true)
		 {
			 ccdcId.focus();

			 error.value = '"Mã công cụ dụng cụ" đã có khấu hao, bạn có muốn xóa khấu hao và lưu lại?';
			 
			 var r = confirm('"Mã công cụ dụng cụ" đã có khấu hao, bạn có muốn xóa khấu hao và lưu lại?');
			 if (r == false)
			 {
			     return;
			 }
			 document.getElementById("xoaKhauHao").value = 'true';
		 }
		 
		 if(ccdcId.value=="")
		 {
			 ccdcId.focus();
			 error.value = 'Vui lòng chọn "Mã công cụ dụng cụ"!';
			 return;
		 }
		 
		 var soLuong = document.getElementsByName("soLuong");
		 var tonHienTai = document.getElementsByName("tonHienTai");
		 for (var index = 0; index < soLuong.length; index++)
		 {
			 if (soLuong.item(index) != null && tonHienTai.item(index) != null)
			 {
// 				 console.log("parseFloat(soLuong(index).value: " + soLuong.item(index));
// 				 console.log("parseFloat(soLuong(index).value: " + soLuong.item(index).value);
		 		if (parseFloat(soLuong.item(index).value.replace(/,/g,"")) > parseFloat(tonHienTai.item(index).value.replace(/,/g,"")))
		 		{
		 			soLuong.item(index).focus();
		 			error.value = '"Số lượng" không được phép lớn hơn "Tồn hiện tại"!';
			 		return;
		 		}
			 }
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
// 	 	 document.forms['dmhForm'].action.value='new';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function TinhTien()
	 {
		var soluong = document.getElementsByName("soLuong");
		var dongia = document.getElementsByName("donGia");
		var thanhtien = document.getElementsByName("thanhTien");	
		var tongTien = 0;
		
		var tongluong = 0;
		
		for(var index =0;index< soluong.length; index++)
		{
			if(dongia.item(index).value!="")
				dongia.item(index).value = DinhDangDonGia(parseFloat(dongia.item(index).value.replace(/,/g,"")));				
			if(soluong.item(index).value!="")
			{
				try{
					soluong.item(index).value = DinhDangDonGia(parseFloat(soluong.item(index).value.replace(/,/g,"")));
					tongluong += parseFloat(soluong.item(index).value.replace(/,/g,"")) ;
					thanhtien.item(index).value = DinhDangDonGia(parseFloat(soluong.item(index).value.replace(/,/g,"")) * parseFloat(dongia.item(index).value.replace(/,/g,"")));
					tongTien += parseFloat(soluong.item(index).value.replace(/,/g,"")) * parseFloat(dongia.item(index).value.replace(/,/g,""));
				}catch (e)
				{
					console.log("Error is: " + e);
				}
			}
		}
		document.getElementById("tongLuong").value = tongluong;
		document.getElementById("tongTien").value = DinhDangDonGia(tongTien);
// 		console.log("tongTien: " + tongTien + "\n");
// 		tongtien = parseFloat(tongtien) + parseFloat(document.getElementById("cpKhac").value.replace(/,/g,""));
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
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../Erp_XuatDungCCDCUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'>
		<input type="hidden" name="xoaKhauHao" id="xoaKhauHao" value='false'> 
		<input type="hidden" name="action" value="<%=action %>" />
		<input type="hidden" name="id" value="<%= obj.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
			<%if (action.trim().equals("display")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý công cụ dụng cụ &gt; Xuất dùng Vật tư - CCDC &gt; Hiển thị</div>
			<%}else if (action.trim().equals("update")){ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý công cụ dụng cụ &gt; Xuất dùng Vật tư - CCDC &gt; Cập nhật</div>
			<%}else{ %>
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý công cụ dụng cụ &gt; Xuất dùng Vật tư - CCDC &gt; Tạo mới</div>
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
<!-- 				Start popup -->
				<A href="" id="ktlist" rel="subcontentKT">&nbsp; <img alt="Định khoản kế toán" src="../images/vitriluu.png"> </A> &nbsp;
				<DIV id="subcontentKT" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 750px; max-height:250px; overflow-y:scroll; padding: 4px; z-index: 100">
					<table width="90%" align="center">
                    	<tr>
							<th width="100px">Nợ/Có</th>
							<th width="150px">Số hiệu tài khoản</th>
							<th width="150px">Số tiền</th>
							<th width="150px">Đối tượng</th>
							<th width="150px">Trung tâm CP</th>	
							<th width="150px">Trung tâm DT</th>										                       
                    	</tr>
	               
                       	<% 	List<DinhKhoanKeToan> ktList = obj.getDinhKhoanList();							                       	 	
                     		for(int sd = 0; sd < ktList.size(); sd++) 
                     		{ 
                     			IDinhKhoanKeToan kt = ktList.get(sd);
                       		%> 
                      			<tr>
                      				<td>
                      					<input type="text" style="width: 100%" readonly="readonly" name="no_co" value="<%= kt.getNO_CO() %>" />
                      				</td>
	                            <td>											                            	
	                            	<input type="text" style="width: 100%" readonly="readonly" name="sohieutk" value="<%= kt.getSoHieu() %>" />
	                            </td>
	                            <td>
	                            	<%if(kt.getSotien().trim().length() > 0){ %>
	                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= formatter.format(Double.parseDouble(kt.getSotien())) %>" style="text-align: left" />
	                            	<%} else {%>
	                            		<input type="text" style="width: 100%" readonly="readonly" name="sotien" value="<%= kt.getSotien() %>" style="text-align: left" />
	                            	<%} %>		
	                            <td>
	                            	<input type="text"  style="width: 100%" name="doituong" value="<%= kt.getDoiTuong() %>" />
	                            </td>
	                            <td>
	                            	<input type="text"  style="width: 100%" name="trungtamcp" value="<%= kt.getTrungtamCP()  %>" />
	                            </td>
	                            <td>
	                            	<input type="text"  style="width: 100%" name="trungtamdt" value="<%= kt.getTrungtamDT()  %>" />
	                            </td>
	                        </tr>
                      <%  }
                     		if (ktList != null) 
                    			ktList.clear();
                       %> 
	
					</table>
	                   <div align="right">
	                   	<label style="color: red" ></label> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	                   	<a href="javascript:dropdowncontent.hidediv('subcontentKT')">Hoàn tất</a>
	                   </div>
				</DIV>
<!-- 				End poup -->
				</span>
				
<%-- 									<A href="../../ErpDonmuahangUpdate_GiaySvl?userId=<%=userId %>&dmhId=<%= obj.getId() %>&task=print" > --%>
<!-- 						<img src="../images/Printer30.png" alt="Print"  title="Print" border="1" longdesc="Print" style="border-style:outset"></A> -->
									
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="msg" rows="1" readonly="readonly" style="width: 100%%"><%=obj.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Xuất dùng Công cụ dụng cụ - Vật tư</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" valign="top" width="150px"> Mã chi phí trả trước</TD>
								<TD class="plainlabel" valign="top"  colspan="3">
									<select name="ccdcId" id="ccdcId" style="width: 500px;">
										<option value=""></option>
										<% if (ccdcList != null)
										{
											for (Erp_Item item : ccdcList)
											{
												if (item.getValue().equals(obj.getCcdcId()))
												{
												%>
													<option value="<%=item.getValue()%>" selected="selected"><%=item.getDifField() + item.getName()%></option>
												<% } else { %>
													<option value="<%=item.getValue()%>"><%=item.getName()%></option>
										<% } }} %>
									</select>
								</TD>
							</TR>
						
<!-- 							<TR> -->
<!-- 								<TD class="plainlabel" valign="top" width="150px">Tổng tiền</TD> -->
<!-- 								<TD class="plainlabel" valign="top"  colspan="3" > -->
<!-- 									<input type="text" name="tongTien" id="tongTien" value="" style="text-align: right;" -->
<!-- 									readonly="readonly"> -->
<!-- 								</TD> -->
<!-- 							</TR> -->
							
<!-- 							<TR> -->
<!-- 								<TD class="plainlabel" valign="top" width="150px">Tổng lượng</TD> -->
<!-- 								<TD class="plainlabel" valign="top"  colspan="3" > -->
<!-- 									<input type="text" name="tongLuong" id="tongLuong" value="" style="text-align: right;" -->
<!-- 									readonly="readonly"> -->
<!-- 								</TD> -->
<!-- 							</TR> -->
							
							<TR>
								<TD class="plainlabel">Diễn giải</TD>
								<TD class="plainlabel" colspan="3">
									<input type="text" id="ghiChu" name="ghiChu" style="width: 700px;"
											value="<%= obj.getGhiChu()%>" maxlength="99" />
								</TD>	
							</TR>
							<TR>
								<TD width="19%" class="plainlabel">Ngày xuất dùng</TD>
								<TD class="plainlabel" colspan=5><INPUT name="ngayXuatDung" id="ngayXuatDung" class="days" type="text" size="30" value='<%=obj.getNgayXuatDung()%>' onChange="submitform();" readonly="readonly"></TD>
							</TR>
						</TABLE>
						<hr>
					</div>
					
<%-- 					<%  if(obj.getLoaihanghoa().trim().length() > 0 ) { %> --%>
					
					<div align="left" style="width: 100%; float: none; clear: none;">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="5%">STT</TH>
								<TH align="center" width="10%">Mã vật tư - CCDC</TH>
								<TH align="center" width="40%">Tên vật tư</TH>
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="10%">Tồn hiện tại</TH>
								<TH align="center" width="10%">Số lượng</TH>
<!-- 								<TH align="center" width="10%">Số lượng</TH> -->
<!-- 								<TH align="center" width="10%">Đơn giá</TH> -->
<!-- 								<TH align="center" width="10%">Thành tiền</TH> -->
								<TH align="center" width="20%">Kho</TH>
							</TR>
							
							<% int count = 0; 
								if(vatTuList.size() > 0) { 
									for(int i = 0; i < vatTuList.size(); i++) { 
										Erp_VatTu vatTu = vatTuList.get(i); %>
										
										<tr>
											<TD align="center">
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="<%= vatTu.getSanPhamId() %>" name="sanPhamId" style="width: 100%" readonly="readonly" >  
											</TD>
											
											<TD align="center">
												<input type="text" value="<%= vatTu.getMaSanPham() %>" name="maSanPham" style="width: 100%" onkeyup="checkKho(<%=i%>); ajax_showOptions(this,'donmuahang_<%=vatTu.getKhoId()%>>',event)" autocomplete="off">
											</TD>
											
											<TD align="center">
												<input type="text" value="<%= vatTu.getTenVatTu() %>" name="tenVatTu" style="width: 100%">  
											</TD>
											<TD align="center">
												<input type="text" value="<%= vatTu.getDonViTinh() %>" name="donViTinh" style="width: 100%" > 
											</TD>
											<TD align="center">
												<input type="text" value="<%= vatTu.getTonHienTai() %>" name="tonHienTai" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)"> 
											</TD>
											<TD align="center">
												<input type="text" value="<%= vatTu.getSoLuongTinh() %>" name="soLuong" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)"> 
											</TD>
<!-- 											<TD align="center"> -->
<%-- 												<input type="text" value="<%= vatTu.getDonGia() %>" name="donGia" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)">  --%>
<!-- 											</TD> -->
<!-- 											<TD align="center"> -->
<%-- 												<input type="text" value="<%= vatTu.getThanhTien() %>"  name="thanhTien" style="width: 100%; text-align: right;" readonly="readonly">  --%>
<!-- 											</TD> -->
											
											<TD align="center">
												<select style="width: 100%" name="khoId" onchange="changeKho(<%=i%>);">
													<option value=""></option>
													<% System.out.println("kho id: " + vatTu.getKhoId());if (khoList.size() > 0)
														{
															for (int j = 0; j < khoList.size(); j++)
															{
																Erp_Item kho = khoList.get(j);
																
																if(kho.getValue().equals(vatTu.getKhoId())){
															%>
																<option value="<%=kho.getValue()%>" selected="selected"><%=kho.getName()%></option>
															<% } else { %>
																<option value="<%=kho.getValue()%>"><%=kho.getName()%></option>
															 <% } } }  %>
												</select>
											</TD>
										</tr>
										
									<% count++; }
								} %>
							
							<% for(int i = count; i < 40; i++) { %>
							
								<tr>
									<TD align="center" width="2%">
										<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
										<input type="hidden" value="" name="sanPhamId" style="width: 100%" readonly="readonly" >  
									</TD>
											
									<TD align="center" width="2%">
										<input type="text" value="" name="maSanPham" style="width: 100%" onkeyup="checkKho(<%=i %>);ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off">
									</TD>
									
									<TD align="center" width="2%">
										<input type="text" value="" name="tenVatTu" style="width: 100%">  
									</TD>
									<TD align="center" width="6%">
										<input type="text" value="" name="donViTinh" style="width: 100%" > 
									</TD>
									<TD align="center" width="7%">
										<input type="text" value="0" name="tonHienTai" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)"> 
									</TD>
									<TD align="center" width="7%">
										<input type="text" value="0" name="soLuong" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)"> 
									</TD>
<!-- 									<TD align="center" width="8%"> -->
<!-- 										<input type="text" value="0" name="donGia" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)">  -->
<!-- 									</TD> -->
<!-- 									<TD align="center" width="8%"> -->
<!-- 										<input type="text" value="0"  name="thanhTien" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)" readonly="readonly">  -->
<!-- 									</TD> -->
											
									<TD align="center" width="8%">
										<select style="width: 100%" name="khoId"  onchange="changeKho(<%=i%>);">
											<option value="" selected="selected"></option>
											<% if (khoList.size() > 0)
												{
													for (int j = 0; j < khoList.size(); j++)
													{
														Erp_Item kho = khoList.get(j);
														%>
														<option value="<%=kho.getValue()%>"><%=kho.getName()%></option>
													 <%  } }  %>
										</select>
									</TD>
								</tr>
							<% } %>
							
							
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
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
		obj.DbClose();
	%>
</form>
</BODY>
</html>
<% } %>