<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.parknhapkhau.*"%>
<%@ page import="geso.traphaco.erp.beans.parknhapkhau.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.shiphang.ISanpham"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%@ page import="geso.traphaco.erp.beans.nhapkhoNK.*"%>
<%@ page import="geso.traphaco.erp.beans.donmuahangtrongnuoc.*"%>

<%
	IErpPark pBean = (IErpPark) session.getAttribute("pBean");
%>
<%
	List<IErpHoadonSp> pnkList = pBean.getPnkList();
%>
<%
	ResultSet rsNcc = pBean.getNccRs();
    ResultSet RsKhoBietTru = pBean.getRsKhoBietTru();
    ResultSet RsKhoTonTru = pBean.getRsKhoTonTru();
%>
<%
	ResultSet rsLoaidonmuahang = pBean.getLoaidonmhRs();
	List<IDonvi> dvList = pBean.getListDonvi();
%>
<%
	ResultSet ttRs = pBean.getTienteRs();
%>
<%
	ResultSet poNKRs = pBean.getPoNkRs();
%>
<!-- cái này dành để lấy số đơn hàng -->
<%
	ResultSet poRs = pBean.getPoRs();
%>
<%
	ResultSet ChitietPoRs = pBean.getChitietPoRs();
%>
<%
	String userId = (String) session.getAttribute("userId");
%>
<%
	String userTen = (String) session.getAttribute("userTen");
%>
<%
	NumberFormat formatter = new DecimalFormat("#,###,###.##");
%>
<%
	NumberFormat formatter2 = new DecimalFormat("#,###,###.###");
%>
<%
	NumberFormat formatter1 = new DecimalFormat("#,###,###.######");
%>


<%
	List<ISanpham> spList = pBean.getSpList();
%>


<%
 String khobiettruid="";
%>


<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>TraphacoERP - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print"
	href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<style type="text/css">
.color {
	background-color: red;
}
</style>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js"
	type="text/javascript"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<style type="text/css">
#dhtmltooltip {
	position: absolute;
	left: -300px;
	width: 300px;
	border: 1px solid black;
	padding: 5px;
	background-color: lightyellow;
	visibility: hidden;
	z-index: 100;
	font-size: 1.2em;
	/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
	filter: progid:DXImageTransform.Microsoft.Shadow(color=gray, direction=135);
}

#dhtmlpointer {
	position: absolute;
	left: -300px;
	z-index: 101;
	visibility: hidden;
}
</style>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script language="javascript" type="text/javascript">

function replaceAll(str, find, replace) {
	  return str.replace(new RegExp(find, 'g'), replace);
}
function padNumber(number) {
    var string  = '' + number;
    string      = string.length < 2 ? '0' + string : string;
    return string;
}
function addDays(theDate, days) {
    return new Date(theDate.getTime() + days*24*60*60*1000);
}
function TangDate(ngay, songay)
{   
	var date = new Date(ngay);
	var next_date  = addDays(new Date(ngay), parseInt(songay));
    formatted = next_date.getFullYear() + '-' + padNumber(next_date.getMonth() + 1) + '-' + padNumber(next_date.getDate());
	return formatted;
}

function replaceAll(str, find, replace) {
	  return str.replace(new RegExp(find, 'g'), replace);
}

//hàm điều chỉnh tiền
function DieuChinhTien(){
	var thanhtienVND = document.getElementsByName("thanhtienVND");
	
	var tong =0;
	for(var i =0; i< thanhtienVND.length; i++){
		tong += parseInt(replaceAll(thanhtienVND.item(i).value,",",""));
	}
	
	// set tổng tiền và thuế giá trị gia tăng
	document.getElementById("tongVND").value= DinhDangDonGia(tong);
	
	// Định dạng đơn giá 1 lần nữa.
}

function replaces()
{	
	
	var tigia = document.getElementById("tigia");
	
	var idhangmua = document.getElementsByName("idhangmua");
	var soluongnhan = document.getElementsByName("soluongnhan");
	var hansudung = document.getElementsByName("hansudung");
	var muahang_fk = document.getElementsByName("muahang_fk");
	
	var thanhtien = document.getElementsByName("thanhtien");
	var thanhtienVND = document.getElementsByName("thanhtienVND");
	
	
	var dongia = document.getElementsByName("dongiaMua");
	var dongiaVND = document.getElementsByName("dongiaMuaVND");
	
	var vat = document.getElementsByName("vat");
	
	var tongtien = 0;
	var tienthuegtgt =0;
	var tongtienVND = 0;
	var tienthuegtgtVND = 0;
	
	
	for(var i = 0; i < idhangmua.length; i++)
	{
		var idhangmuaX = idhangmua.item(i).value;
		var muahang_fkX = muahang_fk.item(i).value;
		var id = idhangmuaX +'.' + muahang_fkX+i;
		
		
		var solo = document.getElementsByName(id + '.solo');
		var soluong = document.getElementsByName(id + ".soluong");
		var ngaysanxuat = document.getElementsByName(id  + '.ngaysanxuat');
		var ngayhethan = document.getElementsByName(id + '.ngayhethan');
		
		var thanhtieni =0;
		var thanhtieniVND  = 0;
		var tienthuegtgti =0;
		var tienthuegtgtiVND = 0;
		var sodong =  soluong.length;
		var sln = 0;
		
		for(var j = 0; j < sodong; j++)
		{
			if(solo.item(j).value != "" && soluong.item(j).value != "")
			{
				var flag = false;
				for(var k = 0; k < j; k++)
				{
					if(solo.item(k).value == solo.item(j).value)
					{
						flag = true;
						break;
					}
				}
				
				if(flag)
				{
					var msg = 'Bạn đã nhập lô ' + solo.item(j).value + ' rồi, Vui lòng nhập số lô khác';
					alert(msg);
					
					solo.item(j).value = "";
					soluong.item(j).value = "";
					ngaysanxuat.item(j).value = "";
					ngayhethan.item(j).value = "";
				}
				
				if(ngaysanxuat.item(j).value != "" && hansudung.item(i).value != "" && ngayhethan.item(j).value=="")
				{
					var songay = parseFloat(hansudung.item(i).value);
					ngayhethan.item(j).value = TangDate(ngaysanxuat.item(j).value, songay);
				}
				
				sln = sln + parseFloat(roundnumber4so(soluong.item(j).value.replace(/,/g,""),4));
				
			}
			else
			{
				if( solo.item(j).value == "" )
				{
					soluong.item(j).value = "";
					ngaysanxuat.item(j).value = "";
					ngayhethan.item(j).value = "";
				}
			}
		}
		soluongnhan.item(i).value = sln.toFixed(4);
		// bổ sung thành tiền, thuế
		thanhtieni = thanhtieni + parseFloat(replaceAll(dongia.item(i).value, ",", ""))* sln;
		thanhtieniVND = thanhtieniVND + parseFloat(replaceAll(dongia.item(i).value, ",", ""))* sln* parseFloat(tigia.value);
		
		// chia 100 để ra phần trăm
		tienthuegtgti = thanhtieni *(parseFloat(replaceAll(vat.item(i).value, ",", ""))/100);
		tienthuegtgtiVND = thanhtieniVND *(parseFloat(replaceAll(vat.item(i).value, ",", ""))/100);
		
		tongtien = tongtien + thanhtieni;
		tienthuegtgt = tienthuegtgt + tienthuegtgti;
		
		tongtienVND = tongtienVND + Math.round(thanhtieniVND);
		tienthuegtgtVND = tienthuegtgtVND + tienthuegtgtiVND;
		
		thanhtien.item(i).value = Math.round(thanhtieni.toString()*1000)/1000;
		thanhtienVND.item(i).value = DinhDangDonGia(parseFloat(Math.round(thanhtieniVND.toString())));
		
		// don gia
		/* var dongiaviet_=parseFloat(   (dongia.item(i).value) * parseFloat(tigia.value); */
		
		var dongiaviet_=parseFloat( replaceAll (dongia.item(i).value,",","")) * parseFloat( replaceAll (tigia.value,",",""));
		 
	/* 	console.log(" don gia : "+ replaceAll (dongia.item(i).value,",","")) + "* ti gia: "+parseFloat( replaceAll (tigia.value,",",""))+" = "  + dongiaviet_ ) ; */
		
		dongiaVND.item(i).value =  DinhDangDonGia( (dongiaviet_.toFixed(4)));
	}
	
	// set tổng tiền và thuế giá trị gia tăng
	document.getElementById("thuegtgt").value= Math.round((tienthuegtgt)*1000)/1000;
	document.getElementById("tong").value= Math.round((tongtien+ tienthuegtgt)*1000)/1000;
	
	document.getElementById("tongVND").value = DinhDangDonGia(Math.round(tongtienVND+ tienthuegtgtVND));
	
	document.getElementById("sotienchuathue").value = Math.round((tongtien)*1000)/1000;
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
	
	function keypresstru(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox  cho phep go dau -
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if ( (keypressed < 48 || keypressed > 57) &&  keypressed != 45  )
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46  )
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	
	function DinhDangTien(num) 
	 {
	    /* num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));

	    return (((sign)?'':'-') + num); */
		num = num.toString().replace(/\$|\,/g,'');
		
		//num = (Math.round( num * 1000 ) / 1000).toString();
		
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
			//sole = parseFloat(sole).toFixed(2);
			//sole = sole.substring(sole.indexOf('.'));
		}
		
		if(isNaN(num))
			num = "0";
		sign = (num == (num = Math.abs(num)));
		if(num){
			num = Math.floor(num*100);
			num = Math.floor(num/100).toString();
			for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
				num = num.substring(0,num.length-(4*i+3)) + ',' + num.substring(num.length-(4*i+3));
		}
		var kq;
		if(sole.length >= 0)
		{
			kq = (((sign)?'':'-') + num) + sole;
		}
		else
			kq = (((sign)?'':'-') + num);
		
		if(kq.indexOf(".") > 0)
		{
			//alert(kq.indexOf(".") + '  -- LEN: ' + kq.length );
			if( ( parseFloat(kq.indexOf(".")) + 3 ) < kq.length )
			{
				//alert('Cat toi: ' + ( parseFloat(kq.indexOf(".")) + 3 ) );
				kq = kq.substring(0, parseFloat(kq.indexOf(".")) + 3);
			}
		}
		
		return kq;
	    
	}
	
	function DinhDangTien5(num) 
	{
	   /* num = num.toString().replace(/\$|\,/g,'');
	   if(isNaN(num))
	   num = "0";
	   sign = (num == (num = Math.abs(num)));
	   num = Math.floor(num*100+0.50000000001);
	   num = Math.floor(num/100).toString();
	   for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	   num = num.substring(0,num.length-(4*i+3))+','+
	   num.substring(num.length-(4*i+3));

	   return (((sign)?'':'-') + num); */
		num = num.toString().replace(/\$|\,/g,'');
		
		//num = (Math.round( num * 1000 ) / 1000).toString();
		
		var sole = '';
		if(num.indexOf(".") >= 0)
		{
			sole = num.substring(num.indexOf('.'));
			//sole = parseFloat(sole).toFixed(2);
			//sole = sole.substring(sole.indexOf('.'));
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
		
		if(kq.indexOf(".") > 0)
		{
			//alert(kq.indexOf(".") + '  -- LEN: ' + kq.length );
			if( ( parseFloat(kq.indexOf(".")) + 5 ) < kq.length )
			{
				//alert('Cat toi: ' + ( parseFloat(kq.indexOf(".")) + 3 ) );
				kq = kq.substring(0, parseFloat(kq.indexOf(".")) + 5);
			}
		}
		
		//alert(kq);
		return kq;
	   
	}
	
	function DinhDangTiGia()
	{			
		var giatrinhap = document.getElementById("tigia").value;
		giatrinhap = parseFloat(giatrinhap.replace(/,/g,""));
		
		var loaihd = document.getElementById("loaihd").value;
		
		if(loaihd == '1') // Loai hd nhập khẩu
		{
			var thutu = 0;	
			var tongcong = document.getElementsByName('tongcong' + thutu);	
			var tongcongVND = document.getElementsByName('tongcongVND' + thutu);	
			
			
			if(tongcong[0].value == '') tongcong[0].value = '0';
			if(parseFloat(tongcong[0].value.replace(/,/g,"")) > 0)
			{
				var tienvnd = parseFloat(tongcong[0].value.replace(/,/g,""))* giatrinhap;
				tongcongVND[0].value = DinhDangTien(tienvnd);
			} 
		}
		
		if(loaihd == '2') // Loại hd mua khu chế xuất
		{
			TinhTienBvat();
		}
		
		document.getElementById("tigia").value = DinhDangDonGia(giatrinhap.toFixed(2));
	}
	
	function DinhDangDonGia(num) 
	{
		num = num.toString().replace(/\$|\,/g,'');
			
		//num = (Math.round( num * 1000 ) / 1000).toString();
			
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
			
		return kq;
	}
	
	function saveform()
	{
		if(Kiemtramoithu())
		{
		 	document.forms['erpParkForm'].action.value='save';
	     	document.forms['erpParkForm'].submit();
		}
	}
	
	function changePO(){
		document.forms['erpParkForm'].action.value='changePO';
     	document.forms['erpParkForm'].submit();
	}
	
	function Kiemtramoithu()
	{
		var ngay=document.getElementById("ngayghinhan");
		if(ngay.value=='')
		 {
			 alert('Vui lòng chọn ngày ghi nhận!');
			 return false;
		 }
		var nccId=document.getElementById("nccId");
		if(nccId.value== "")
		 {
			 alert('Vui lòng chọn nhà cung cấp!');
			 return false;
		 }
		var poId=document.getElementById("poId");
		if(poId.value== "")
		 {
			 alert('Vui lòng chọn Đơn mua hàng!');
			 return false;
		 }
		var kyhieuhoadon = document.getElementById("kyhieuhoadon");
		if(kyhieuhoadon.value== "")
		 {
			 alert('Vui lòng nhập ký hiệu hoá đơn!');
			 return false;
		 }
		
		var sohoadon=document.getElementById("sohoadon");
		if(sohoadon.value== "")
		 {
			 alert('Vui lòng nhập số hoá đơn!');
			 return false;
		 }
		var ngayhoadon=document.getElementById("ngayhoadon");
		if(ngayhoadon.value== "")
		 {
			 alert('Vui lòng chọn ngày hoá đơn!');
			 return false;
		 }
		
		
		var khobiettru=document.getElementById("khobiettruId");
		var khotontru=document.getElementById("khotontruId");
		if(khobiettru.value=='' || khotontru.value=='')
		 {
			 alert('Vui lòng chọn chọn kho tồn trữ và kho biệt trữ!');
			 return false;
		 }
		
		var tenhangmua= document.getElementsByName("tenhangmua");
		var dem =0;
		for(var i=0; i< tenhangmua.length; i++){
			if(tenhangmua.item(i).value.trim().length >0){
				dem++;
			}
		}
		if(dem =0){
			 alert('Vui lòng chọn sản phẩm!');
			 return false;
		}
		
		// check trường hợp số lượng Hoá Đơn lớn hơn so với số lượng được phép nhận
		var idhangmua = document.getElementsByName("idhangmua");
		var soluongdanhan = document.getElementsByName("soluongdanhan");
		var soluongdat = document.getElementsByName("soluongdat");
		var soluongnhan = document.getElementsByName("soluongnhan");
		var dungsai = document.getElementById("dungsai").value;
		
		for(var i=0; i< idhangmua.length; i++){
			if(soluongdanhan.item(i).value.trim().length >0){
				
				var soluongdat_ = parseFloat(replaceAll(soluongdat.item(i).value,",",""));
				var soluongdanhan_ = parseFloat(replaceAll(soluongdanhan.item(i).value,",",""));
				var soluongnhan_ = parseFloat(replaceAll(soluongnhan.item(i).value,",",""));
				var a = parseFloat(soluongnhan_) - (parseFloat(soluongdat_) - parseFloat(soluongdanhan_) );
				
				if( parseFloat(a) > (parseFloat(dungsai) * parseFloat(soluongdat_)/100)) {
					 alert('Không được phép nhận quá số lượng cho phép ở dòng thứ' + (i+1));
					 return false;
				}
				
			}
		}

		return true;
	}
	
	function replaceAll(str, find, replace) {
		  return str.replace(new RegExp(find, 'g'), replace);
	}
	
	function submitform()
	{
		document.forms['erpParkForm'].action.value='locphieunhapkhoa';
	    document.forms["erpParkForm"].submit();
	}
	
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value == '' || element.value == '0' )
		{
			element.value= '';
		}
	} 
	
	function Dinhdangthue(element)
	{
		Tinhtienkhongvat();
		element.value = DinhDangTien(element.value);
		if(element.value == '' || element.value == '0' )
		{
			element.value= '';
		}
	} 

	function Dinhdangvat(element)
	{
		Tinhtiencovat();
		element.value = DinhDangTien(element.value);
		if(element.value == '' || element.value == '0' )
		{
			element.value= '';
		}
	} 
	
	function Check(id)
	{
		if(id.checked == false) //uncheck
		{
			id.value = '0';			
			
			// tigia
				
			var name = id.id;
			var thutu = name.substring(name.indexOf('sohoadon') + 8, name.indexOf('.check') );
			var check = name.substring(name.indexOf('check') + 5, name.length );
			
			var soluong = document.getElementsByName('sohoadon' + thutu + '.soluong');
			var dongia = document.getElementsByName('sohoadon' + thutu + '.dongia');
			var thanhtien = document.getElementsByName('sohoadon' + thutu + '.thanhtien');
			
			var tongsotienchuathue = 0;
			var tongluong = 0;
			
			var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
			var thuesuat =  document.getElementById('thuesuat' + thutu);
			var vat =  document.getElementById('vat' + thutu);
			var tongcong =  document.getElementById('tongcong' + thutu);
			
			for(var n = 0; n < soluong.length; n++)  
			{
				soluong[n].value = soluong[n].value.replace(/,/g,"");
				dongia[n].value = dongia[n].value.replace(/,/g,"");
				
				var tt = parseFloat(soluong[n].value)*parseFloat(dongia[n].value);
				tt = Math.round(tt*100);
				tt = tt/100;
					
				thanhtien[n].value = DinhDangTien(tt);
				
				var check = document.getElementsByName('sohoadon' + thutu + '.check' + n);
				var chon = document.getElementById('sohoadon' + thutu + '.chon' + n);
								
				if(check[0].checked == true) {
					tongsotienchuathue = tongsotienchuathue + tt;
					chon.value = '1';
				}
			}
						
			sotienchuathue.value = DinhDangTien(tongsotienchuathue);
			thuesuat.value = '10';
			vat.value = DinhDangTien(tongsotienchuathue*0.1);
			tongcong.value = DinhDangTien(tongsotienchuathue*0.1+tongsotienchuathue);
				
			return;
		}
		else
		{
			id.value = "1";
			
			var name =id.id;
			var thutu = name.substring(name.indexOf('sohoadon') + 8, name.indexOf('.check') );
			var check = name.substring(name.indexOf('check') + 5, name.length );
			
			var soluong = document.getElementsByName('sohoadon' + thutu + '.soluong');
			var dongia = document.getElementsByName('sohoadon' + thutu + '.dongia');
			var thanhtien = document.getElementsByName('sohoadon' + thutu + '.thanhtien');
			
			var tongsotienchuathue = 0;
			var tongluong = 0;
						
			var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
			var thuesuat =  document.getElementById('thuesuat' + thutu);
			var vat =  document.getElementById('vat' + thutu);
			var tongcong =  document.getElementById('tongcong' + thutu);
				
			for(var n = 0; n < soluong.length; n++)  
			{
				soluong[n].value = soluong[n].value.replace(/,/g,"");
				dongia[n].value = dongia[n].value.replace(/,/g,"");
				
				var tt = parseFloat(soluong[n].value)*parseFloat(dongia[n].value);
				tt = Math.round(tt*100);
				tt = tt/100;

				thanhtien[n].value = DinhDangTien(tt);
				
				var check = document.getElementsByName('sohoadon' + thutu + '.check' + n);
				var chon = document.getElementById('sohoadon' + thutu + '.chon' + n);
				
				if(check[0].checked == true) {
					tongsotienchuathue = tongsotienchuathue + tt;
					chon.value = '1';
				}
			}
			
			sotienchuathue.value = DinhDangTien(tongsotienchuathue);
			thuesuat.value = '10';
			vat.value = DinhDangTien(tongsotienchuathue*0.1);
			tongcong.value = DinhDangTien(tongsotienchuathue*0.1+tongsotienchuathue);
			
			return;
		}
	}
	function replaces_1(){
		 var idhangmua = document.getElementsByName("idhangmua");
		 var muahang_fk = document.getElementsByName("muahang_fk");
		 
		 for(var i = 0; i < idhangmua.length; i++)
		 {
		  var idhangmuaX = idhangmua.item(i).value;
		  var muahang_fkX = muahang_fk.item(i).value;
		  var id = idhangmuaX +'.' + muahang_fkX+i;
		  
		  var nsxs=document.getElementsByName(id + ".nsxten");
		  var nsxid=document.getElementsByName(id + ".nsxid");
		  
		  var sodong =  nsxs.length;
		  var sln = 0;
		  
		  for(var j = 0; j < sodong; j++)
		  {
		   if(nsxs.item(j).value != "")
		   {
		    var nsx = nsxs.item(j).value;
		    var pos = parseInt(nsx.indexOf("-->["));

		    if(pos > 0)
		    {
		     nsxid.item(j).value = nsx.substring(0, pos);
		     nsx = nsx.substr(parseInt(nsx.indexOf("-->[")) + 4);
		     
		     nsxs.item(j).value = nsx.substring(0, parseInt(nsx.indexOf("][")));
		     
		    }
		   }
		   else
		   {
		    
		    nsxid.item(j).value = "";
		    
		   }
		  }
		 }
		 
		 setTimeout(replaces_1, 500);
		 
		}
	function Kiemtra(e)
	{
		var tiente=document.getElementById("ttId").value;
		var name = e.name;
		var thutu = e.id.substring(e.id.indexOf('sohoadon') + 8, e.id.indexOf('.sotienhd') );
		var dong = e.id.substring(e.id.indexOf('sotienhd') + 8, e.id.length );
						
		var chuathue = 0;
		
		for(var n = 0; n < sotienhd.length; n++)  // duyet tung dong chi tiet ben trong cua hoa don (= thutu)
		{
			sotienhd[n].value = sotienhd[n].value.replace(/,/g,"");
			sotienpnk[n].value = sotienpnk[n].value.replace(/,/g,"");
 			
			if(sotienhd[n].value != '')
			{
				chuathue += parseFloat(sotienhd[n].value);
				THUE_PO = THUE_VAT.item(0).value;
			}
			
			if(n == dong)
			{
				//CHECK XEM CO PHIEU NAO HOAN TAT KHONG
				
				for (var j = 0; j < 10; j++) // Duyet nhung dong hoa don khac, ke ca dong chua co hoa don
				{  
					if(j != thutu && j > thutu )
					{
						var sotienpnk2 = document.getElementsByName('sohoadon' + j + '.sotienpnk');
						var poId2 = document.getElementsByName('sohoadon' + j + '.poId');
						var pnkId2 = document.getElementsByName('sohoadon' + j + '.pnkId');
					
						for (var m = 0; m < sotienpnk2.length; m++)
						{
							if(poId2[m].value == poId[n].value && pnkId2[m].value == pnkId[n].value)
							{
								var check2 = document.getElementById('sohoadon' + j + '.check' + m);
								if(check2.value != '1')
								{
									var cl = parseFloat(sotienpnk[n].value) - parseFloat(sotienhd[n].value);
									if(cl < 0)
										cl = 0;
									
									sotienpnk2[m].value = DinhDangTien(cl);
								}
							}
						}
					}
				}
			} 
			
			sotienhd[n].value=DinhDangTien(sotienhd[n].value);
			sotienpnk[n].value = DinhDangTien(sotienpnk[n].value);		
		}
		
		//TINH TONG CHENH LECH CUA CAC PHIEU NHAP
		var coPOhoantat = 0;
		var chenhlech = 0;
		for(var n = 0; n < sotienhd.length; n++)  // duyet tung dong chi tiet ben trong cua hoa don (= thutu)
		{
			//alert('sohoadon' + thutu + '.checkHT' + n);
			var checkHT2 = document.getElementById('sohoadon' + thutu + '.checkHT' + n);
			if(checkHT2.checked == true )
			{
				coPOhoantat = 1;
			}
			
			//var check2 = document.getElementById('sohoadon' + thutu + '.check' + n);
			if(parseFloat(sotienhd[n].value.replace(/,/g,"")) > 0 )
			{
				chenhlech += parseFloat(sotienhd[n].value.replace(/,/g,"")) - parseFloat(sotienpnk[n].value.replace(/,/g,""));
				//alert('So tien HD: ' + parseFloat(sotienhd[n].value.replace(/,/g,"")) + '  -- So tien PNK: ' + parseFloat(sotienpnk[n].value.replace(/,/g,"")) );
			}
		}
		
		//alert('CHUA THUE: ' + chuathue);
		var sotienchuathue = document.getElementById('sotienchuathue'+ thutu);
		sotienchuathue.value = DinhDangTien(chuathue);
		
		var thuesuat = document.getElementById('thuesuat'+ thutu);
		if(thuesuat.value == '')
			thuesuat.value = THUE_PO;
		
		var chietkhau = document.getElementById('chietkhau'+ thutu);
		if(coPOhoantat == 1)
		{
			chietkhau.value = DinhDangTien(chenhlech);
			chietkhau.setAttribute("readonly", "readonly");
		}
		
		var ck = 0;
		if(chietkhau.value != '')
			ck = parseFloat(chietkhau.value.replace(/,/g,""));
		
		//Không cộng tiền điều chỉnh vào
		ck = 0;
		
		var vat = document.getElementById('vat'+ thutu);
		//neu la vnd thi lam tron ,khong co so le
		if(tiente="100000"){
			var tienthue1=( parseFloat( chuathue ) + parseFloat( ck ) ) * parseFloat( thuesuat.value) / 100; 
			vat.value = DinhDangTien(  tienthue1.toFixed(0));
		}else{
			vat.value = DinhDangTien( ( parseFloat( chuathue ) + parseFloat( ck ) ) * parseFloat( thuesuat.value) / 100 );
		}
		var tongcong = document.getElementById('tongcong'+ thutu);
		tongcong.value = DinhDangTien( parseFloat( chuathue ) + parseFloat( vat.value.replace(/,/g,"") ) );
		
		return;
	}
	
	function Kiemtra2(id)
	{				
		var name = id.id;
		var thutu = name.substring(name.indexOf('sohoadon') + 8, name.indexOf('.soluong') );
		var check = name.substring(name.indexOf('soluong') + 8, name.length );
		
		var soluong = document.getElementsByName('sohoadon' + thutu + '.soluong'); // số lượng hd 
		
		var soluonghd = document.getElementsByName('sohoadon' + thutu + '.soluongdat'); // số lượng đặt
		
		var dongia = document.getElementsByName('sohoadon' + thutu + '.dongia');
		var thanhtien = document.getElementsByName('sohoadon' + thutu + '.thanhtien');
		
		var dungsai = document.getElementsByName('sohoadon' + thutu + '.dungsai');
		
		var spthuesuat = document.getElementsByName('sohoadon' + thutu + '.spthuesuat');
		
		var sptienvat = document.getElementsByName('sohoadon' + thutu + '.sptienvat');
		
		var sptienavat = document.getElementsByName('sohoadon' + thutu + '.sptienavat');
		
		var tongsotienchuathue = 0;
		var tongluong = 0;
		
		var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
		var thuesuat =  document.getElementById('thuesuat' + thutu);
		var vat =  document.getElementById('vat' + thutu);
		var tongcong =  document.getElementById('tongcong' + thutu);
		
		var tienthue = 0;
								
		for(var n = 0; n < soluong.length; n++)  
		{				
			//alert("soluong:"+soluong[n].value.replace(/,/g,""));
			//alert("soluongdat:"+parseFloat(soluonghd[n].value.replace(/,/g,""))*(1+dungsai[n].value.replace(/,/g,"")));
			
			if(parseFloat(soluong[n].value.replace(/,/g,"")) > (parseFloat(soluonghd[n].value.replace(/,/g,""))*(1+dungsai[n].value.replace(/,/g,"")/100)))
				soluong[n].value = soluonghd[n].value;
				
			soluong[n].value = soluong[n].value.replace(/,/g,"");
			dongia[n].value = dongia[n].value.replace(/,/g,"");
			
			var tt = parseFloat(soluong[n].value)*parseFloat(dongia[n].value);
			tt = Math.round(tt*100);
			tt = tt/100;
				
			var tv = Math.round(tt*spthuesuat[n].value.replace(/,/g,"")/100);
			
			tienthue = tienthue  + tv;
			
			sptienvat[n].value = DinhDangTien(tv);
			
			sptienavat[n].value = 	DinhDangTien(tt + tv);
			
			thanhtien[n].value = DinhDangTien(tt);
			dongia[n].value = DinhDangTien(dongia[n].value);
			soluong[n].value = DinhDangTien(soluong[n].value);
							
			tongsotienchuathue = tongsotienchuathue + tt;
			//
			
			
			//tongsoluong += parseFloat(soluong[n].value.replace(/,/g,""));

		}
						
		sotienchuathue.value = DinhDangTien(tongsotienchuathue);
		//thuesuat.value = '10';
		vat.value = DinhDangTien(tienthue);
		tongcong.value = DinhDangTien(tienthue+tongsotienchuathue);
			
		return;
	}
	
	function Kiemtra15(id)
	{		
		var name = id.id;
		var thutu = name.substring(name.indexOf('sohoadon') + 8, name.indexOf('.thanhtien') );
		var check = name.substring(name.indexOf('soluong') + 8, name.length );
						
		var soluong = document.getElementsByName('sohoadon' + thutu + '.soluong'); // số lượng hd 
		var dongia = document.getElementsByName('sohoadon' + thutu + '.dongia');
		var thanhtien = document.getElementsByName('sohoadon' + thutu + '.thanhtien');
				
		var soluonghd = document.getElementsByName('sohoadon' + thutu + '.soluongdat'); // số lượng đặt
		
		var spthuesuat = document.getElementsByName('sohoadon' + thutu + '.spthuesuat');
		
		var sptienvat = document.getElementsByName('sohoadon' + thutu + '.sptienvat');
		
		var sptienavat = document.getElementsByName('sohoadon' + thutu + '.sptienavat');
		
		var tongsotienchuathue = 0;
		var tongluong = 0;
		
		var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
		var thuesuat =  document.getElementById('thuesuat' + thutu);
		var vat =  document.getElementById('vat' + thutu);
		var tongcong =  document.getElementById('tongcong' + thutu);
		
		var tienthue = 0;
		var tt = 0;
		for(var n = 0; n < soluong.length; n++)  
		{
			//if(parseFloat(soluong[n].value.replace(/,/g,"")) > parseFloat(soluonghd[n].value.replace(/,/g,"")))
				//soluong[n].value = soluonghd[n].value;
			
			tt = tt + parseFloat(thanhtien[n].value.replace(/,/g,""));
			
			var tt1 = parseFloat(thanhtien[n].value.replace(/,/g,""));
			var tv = Math.round(tt1*spthuesuat[n].value.replace(/,/g,"")/100);
			
			tienthue = tienthue + tv;
			
			sptienvat[n].value = DinhDangTien(tv);
			
			sptienavat[n].value = 	DinhDangTien(tt1 + tv);
			
			thanhtien[n].value = DinhDangTien(thanhtien[n].value.replace(/,/g,""));
			
			//dongia[n].value = dongia[n].value.replace(/,/g,"");
			
			//var tt = tt + parseFloat(tt[n].value)*parseFloat(dongia[n].value);
			/* tt = Math.round(tt*100);
			tt = tt/100; */
				
			//thanhtien[n].value = DinhDangTien(tt);
		}
		
		tongsotienchuathue = tongsotienchuathue + tt;
		//alert(tongsotienchuathue);
		
		var thuevat1= 0;
		sotienchuathue.value = DinhDangTien(Math.round(tongsotienchuathue));
		//thuevat1 =  thuesuat.value ;
		vat.value = DinhDangTien(tienthue);
		tongcong.value = DinhDangTien(Math.round(tongsotienchuathue)+tienthue);
			
		return;
	}
	
	function Kiemtra155(id)
	{		
		var name = id.id;
		var thutu = name.substring(name.indexOf('sohoadon') + 8, name.indexOf('.sptienvat') );
		var check = name.substring(name.indexOf('soluong') + 8, name.length );
						
		var soluong = document.getElementsByName('sohoadon' + thutu + '.soluong'); // số lượng hd 
		var dongia = document.getElementsByName('sohoadon' + thutu + '.dongia');
		var thanhtien = document.getElementsByName('sohoadon' + thutu + '.thanhtien');
				
		var soluonghd = document.getElementsByName('sohoadon' + thutu + '.soluongdat'); // số lượng đặt
		
		var spthuesuat = document.getElementsByName('sohoadon' + thutu + '.spthuesuat');
		
		var sptienvat = document.getElementsByName('sohoadon' + thutu + '.sptienvat');
		
		var sptienavat = document.getElementsByName('sohoadon' + thutu + '.sptienavat');
		
		var tongsotienchuathue = 0;
		var tongluong = 0;
		
		var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
		var thuesuat =  document.getElementById('thuesuat' + thutu);
		var vat =  document.getElementById('vat' + thutu);
		var tongcong =  document.getElementById('tongcong' + thutu);
		
		var tienthue = 0;
		var tt = 0;
		var tv = 0;
		for(var n = 0; n < soluong.length; n++)  
		{
			//if(parseFloat(soluong[n].value.replace(/,/g,"")) > parseFloat(soluonghd[n].value.replace(/,/g,"")))
				//soluong[n].value = soluonghd[n].value;
			
			tt = tt + parseFloat(thanhtien[n].value.replace(/,/g,""));
			
			tv = tv + parseFloat(sptienvat[n].value.replace(/,/g,""));
									
			sptienavat[n].value = 	DinhDangTien(tt + tv);
			
			thanhtien[n].value = DinhDangTien(thanhtien[n].value.replace(/,/g,""));
			
			sptienvat[n].value = DinhDangTien(sptienvat[n].value.replace(/,/g,""));
		}
		
		tongsotienchuathue = tongsotienchuathue + tt;
		
		var thuevat1= 0;
		sotienchuathue.value = DinhDangTien(Math.round(tongsotienchuathue));
		vat.value = DinhDangTien(tv);
		tongcong.value = DinhDangTien(Math.round(tongsotienchuathue)+tv);
			
		return;
		
	}

	function Kiemtra5(id)
	{		
		var name = id.id;
		var thutu = name.substring(name.indexOf('sohoadon') + 8, name.indexOf('.dongia') );
		var check = name.substring(name.indexOf('soluong') + 8, name.length );
						
		var soluong = document.getElementsByName('sohoadon' + thutu + '.soluong'); // số lượng hd 
		var dongia = document.getElementsByName('sohoadon' + thutu + '.dongia');
		var thanhtien = document.getElementsByName('sohoadon' + thutu + '.thanhtien');
				
		var soluonghd = document.getElementsByName('sohoadon' + thutu + '.soluongdat'); // số lượng đặt
		
		var dungsai = document.getElementsByName('sohoadon' + thutu + '.dungsai');
		
		var spthuesuat = document.getElementsByName('sohoadon' + thutu + '.spthuesuat');
		
		var sptienvat = document.getElementsByName('sohoadon' + thutu + '.sptienvat');
		
		var sptienavat = document.getElementsByName('sohoadon' + thutu + '.sptienavat');
				
		var tongsotienchuathue = 0;
		var tongluong = 0;
		
		var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
		var thuesuat =  document.getElementById('thuesuat' + thutu);
		var vat =  document.getElementById('vat' + thutu);
		var tongcong =  document.getElementById('tongcong' + thutu);
		
		var tienthue = 0;
		
		for(var n = 0; n < soluong.length; n++)  
		{				
			//alert("soluong:"+soluong[n].value.replace(/,/g,""));
			//alert("soluongdat:"+parseFloat(soluonghd[n].value.replace(/,/g,""))*(1+dungsai[n].value.replace(/,/g,"")));
			
			if(parseFloat(soluong[n].value.replace(/,/g,"")) > (parseFloat(soluonghd[n].value.replace(/,/g,""))*(1+dungsai[n].value.replace(/,/g,"")/100)))
				soluong[n].value = soluonghd[n].value;
				
			soluong[n].value = soluong[n].value.replace(/,/g,"");
			dongia[n].value = dongia[n].value.replace(/,/g,"");
			
			var tt = parseFloat(soluong[n].value)*parseFloat(dongia[n].value);
			tt = Math.round(tt*100);
			tt = tt/100;
				
			var tv = Math.round(tt*spthuesuat[n].value.replace(/,/g,"")/100);
			
			tienthue = tienthue  + tv;
			
			sptienvat[n].value = DinhDangTien(tv);
			
			sptienavat[n].value = 	DinhDangTien(tt + tv);
			
			thanhtien[n].value = DinhDangTien(tt);
			dongia[n].value = DinhDangTien(dongia[n].value);
			soluong[n].value = DinhDangTien(soluong[n].value);
							
			tongsotienchuathue = tongsotienchuathue + tt;
			//
			
			
			//tongsoluong += parseFloat(soluong[n].value.replace(/,/g,""));

		}
					
		sotienchuathue.value = DinhDangTien(tongsotienchuathue);
		//thuesuat.value = '10';
		vat.value = DinhDangTien(tienthue);
		tongcong.value = DinhDangTien(tienthue+tongsotienchuathue);
		
		return;
		
	}
	
	function Kiemtra3(num)
	{
	}
	
	function KiemtraChiPhiKhac(num)
	{
		var thutu = num;
		
		var tienhang = document.getElementsByName('tienhang' + thutu);	
		var chietkhau = document.getElementsByName('chietkhau' + thutu);	
		var tongcong = document.getElementsByName('tongcong' + thutu);	
	
		for(var n = 0; n < tienhang.length; n++)  // duyet tung dong chi tiet ben trong cua hoa don (= thutu)
		{
			chietkhau[0].value = DinhDangTien(chietkhau[0].value);
			
			tienhang[n].value = tienhang[n].value.replace(/,/g,"");
			if(tienhang[0].value == null || tienhang[0].value == ''){
				tienhang[0].value = '0';
			}
			
			chietkhau[n].value = chietkhau[n].value.replace(/,/g,"");
			if(chietkhau[0].value == null || chietkhau[0].value == ''){
				chietkhau[0].value = '0';
			}
			
			var tt = parseFloat(tienhang[n].value) + parseFloat(chietkhau[n].value);
			tt = Math.round(tt*100);
			tt = tt/100;
			tongcong[n].value = DinhDangTien(tt);
			tienhang[0].value = DinhDangTien(tienhang[0].value);
			chietkhau[0].value = DinhDangTien(chietkhau[0].value);
		}
	}
	
	function CheckHoanTat(thutu)
	{
		var sotienpnk = document.getElementsByName('sohoadon' + thutu + '.sotienpnk');
		var sotienhd = document.getElementsByName('sohoadon' + thutu + '.sotienhd');
		var poId = document.getElementsByName('sohoadon' + thutu + '.poId');
		
		var poHoanTat = '';
		
		//TINH TONG CHENH LECH CUA CAC PHIEU NHAP
		var coPOhoantat = 0;
		var chenhlech = 0;
		for(var n = 0; n < sotienhd.length; n++)  // duyet tung dong chi tiet ben trong cua hoa don (= thutu)
		{
			//alert('sohoadon' + thutu + '.checkHT' + n);
			var checkHT2 = document.getElementById('sohoadon' + thutu + '.checkHT' + n);
			if(checkHT2.checked == true )  // Check nút Hoàn Tất
			{
				coPOhoantat = 1;
				poHoanTat += poId[n].value + ',';
				
				if(parseFloat(sotienhd[n].value.replace(/,/g,"")) > 0 )
				{
					chenhlech += parseFloat(sotienhd[n].value.replace(/,/g,"")) - parseFloat(sotienpnk[n].value.replace(/,/g,""));
				}
				
			}else
			{				
				coPOhoantat = 0;
			}
		}
		
		//NEU CHECK VO HOAN TAT, TU DONG MANG CHENH LECH XUONG TIEN DIEU CHINH
		
		var chietkhau = document.getElementById('chietkhau'+ thutu);
		if(coPOhoantat == 1)
		{
			chietkhau.value = DinhDangTien(chenhlech);
			chietkhau.setAttribute("readonly", "readonly");
		}
		else
		{
			chietkhau.value = DinhDangTien(chenhlech);
			chietkhau.removeAttribute("readonly");
		}
		
		
		//NHUNG HOA DON DUOI NEU CO CHECK HON TAT THI SE BANG 0
		if(poHoanTat != '')
		{
			for (var j = 0; j < 10; j++) // Duyet nhung dong hoa don khac, ke ca dong chua co hoa don
			{  
				if(j > thutu )
				{
					var sotienpnk2 = document.getElementsByName('sohoadon' + j + '.sotienpnk');
					var sotienhd2 = document.getElementsByName('sohoadon' + j + '.sotienhd');
					var poId2 = document.getElementsByName('sohoadon' + j + '.poId');
				
					for (var m = 0; m < sotienpnk2.length; m++)
					{
						if( poHoanTat.indexOf( poId2[m].value) >= 0 )
						{
							sotienpnk2[m].value = 0;
							sotienhd2[m].setAttribute("disabled", "disabled");
						}
					}
				}
			}
		}
		
		
		return;
		
	}
	
 	function Kiemtrasohd(id)
	{
		var values = id.value;
		if(values!='')
		{
			var vitri= id.name.substring(8, 9);
			var sodong = 10;
			for(var i=0; i< sodong ; i++)
				{
					if(i!=vitri){
						var kyhieu=document.getElementById('sohoadon'+i);
						if(kyhieu.value == values)
							{
								alert("Số hóa đơn đã tồn tại ở dòng thứ " + (i+1));
								var kyhieu=document.getElementById('sohoadon'+vitri);
								kyhieu.value='';
								return;
							}
					}
				}
		}
		return; 
	} 
 	
 	function Kiemtratable()
 	{
 		//Tinhtienkhongvat();
 	}
 	
 	
 	function ChonKhoBietTru()
 	{
 		/* document.forms['erpParkForm'].action.value = 'submit'; */
 		document.forms['erpParkForm'].action.value = 'chonkhotontru';
 	    document.forms["erpParkForm"].submit();
 	}
 	
	function Tinhtienkhongvat()
	{
		var sodong = 10;//So hoa don toi da
		for(var i = 0; i < sodong; i++)
		{
			var chuathue = document.getElementById('sotienchuathue'+ i);
			var bvat = chuathue.value.replace(/,/g,"");
			if(bvat.length == 0)
				bvat = '0';
			
			var tiendieuchinh = document.getElementById('chietkhau'+ i);
			var dc = tiendieuchinh.value.replace(/,/g,"");
			if(dc == 0)
				dc = '0';
			
			//Không cộng tiền điều chỉnh vào
			dc = 0;
			
			var thuesuat = document.getElementById('thuesuat'+ i);
			var ts = thuesuat.value.replace(/,/g,"");
			if(ts.length == 0)
				ts = '0';
			
			var vat = document.getElementById('vat'+ i);
			var tongcong = document.getElementById('tongcong'+ i);
			
			if( parseFloat(bvat) != 0 )
			{
				vat.value = DinhDangTien( ( parseFloat(bvat) + parseFloat(dc) ) * parseFloat(ts) / 100  );
				tongcong.value =  DinhDangTien( parseFloat(bvat) + parseFloat(dc) + parseFloat(vat.value.replace(/,/g,""))  );
			}
			else
			{
				chuathue.value = '';
				tiendieuchinh.value = '';
				thuesuat.value = '';
				vat.value = '';
				tongcong.value = '';
			}
		}
		
		//setTimeout(Tinhtienkhongvat, 300);	
	}
	
	function Tinhtiencovat()
	{
		var sodong = 10;//So hoa don toi da
		for(var i = 0; i < sodong; i++)
		{
			var chuathue = document.getElementById('sotienchuathue'+ i);
			var bvat = chuathue.value.replace(/,/g,"");
			if(bvat.length == 0)
				bvat = '0';
			
			var tiendieuchinh = document.getElementById('chietkhau'+ i);
			var dc = tiendieuchinh.value.replace(/,/g,"");
			if(dc == 0)
				dc = '0';
			
			//Không cộng tiền điều chỉnh vào
			dc = 0;
			
/* 			var thuesuat = document.getElementById('thuesuat'+ i);
			var ts = thuesuat.value.replace(/,/g,"");
			if(ts.length == 0)
				ts = '0'; */
			
			var vat = document.getElementById('vat'+ i);
			var tongcong = document.getElementById('tongcong'+ i);
			
			if( parseFloat(bvat) != 0 )
			{
				vat.value = DinhDangTien(vat.value);
				tongcong.value =  DinhDangTien( parseFloat(bvat) + parseFloat(dc) + parseFloat(vat.value.replace(/,/g,""))  );
				
			}
			else
			{
				chuathue.value = '';
				tiendieuchinh.value = '';
				thuesuat.value = '';
				vat.value = '';
				tongcong.value = '';
			}
		}
		
		//setTimeout(Tinhtienkhongvat, 300);	
	}
	
	function keypress2(e)
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed == 13)
		{
			document.forms['erpParkForm'].action.value = 'submit';
		    document.forms["erpParkForm"].submit();
		}
	}
	
	function ktMaxnhan(){
		
	 	/* //---- kiểm tra số lượng MAX ----//
		var soluongmoi= document.getElementsByName("sohoadon0.soluong");
		var soluongcu= document.getElementsByName("sohoadon0.soluongcu"); 
		
	
	 	for(var i=0; i< soluongmoi.length; i++){
	 	
			var slnhan= soluongmoi.item(i).value.replace(/,/g,"");
		 	var slnhancu= soluongcu.item(i).value.replace(/,/g,"");   
			var dungsaimax= dungsai.item(i).value.replace(/,/g,"");
			var slmax=0;
			
			if(parseFloat(dungsaimax) > 0){
				slmax= slnhancu*(1+dungsaimax/100);
			}
			else{
				slmax=slnhancu;
			}
			
		 	if(parseFloat(slnhan) > parseFloat(slmax)){
				var msg = 'Số lượng hiện tại là '+slnhan+ ' vượt quá số lượng tối đa '+Math.round(slmax,0)+' trong đơn đặt hàng với dung sai là '+dungsaimax+'. Vui lòng nhập lại số lượng';
				soluongmoi.item(i).value=slnhancu;
				alert(msg);
			}
			else{
				
				soluongmoi.item(i).value=slnhan;
			}  
			
		}  */
	}
	
	function TinhTienBvat()
	{
		var tigiaHD = document.getElementById("tigia").value;
		var thuesuat = document.getElementById("thuesuatPO").value;
	    if(tigiaHD.replace(/,/g,"") == '') tigiaHD = "0" ;	    
	    if(thuesuat.replace(/,/g,"") == '') thuesuat = "0" ;
	    
		var masp = document.getElementsByName("spCTId"); 
	    var sldat = document.getElementsByName("slDatCT"); 
	    var slnhan = document.getElementsByName("slNhanCT"); 
	    var dongia = document.getElementsByName("dongiaCT");

	    var tongtien = "0";
	    var thanhtien = "0";
	    
	    // Kiểm tra số lượng nhận <= số lượng đặt
        for(var i = 0 ; i < masp.length ; i++)
        {
        	if(slnhan[i].value.replace(/,/g,"") == '')  slnhan[i].value = "0";
        	if( parseFloat(slnhan[i].value.replace(/,/g,"")) - parseFloat(sldat[i].value.replace(/,/g,"")) > 0 )
        	{
        		alert("Số lượng nhận không được vượt quá số lượng đặt !");
        	}else
        	{
        		thanhtien = Math.round( parseFloat(slnhan[i].value.replace(/,/g,""))*parseFloat(dongia[i].value.replace(/,/g,""))*parseFloat(tigiaHD.replace(/,/g,"")) ) ;
        		tongtien =  parseFloat(tongtien) +  parseFloat(thanhtien) ;
        	}
        }
	    
	   
	    document.getElementById("sotienchuathue0").value = DinhDangTien(tongtien);
	    
	    document.getElementById("thuesuat0").value = DinhDangTien(parseFloat(thuesuat.replace(/,/g,"")));
	    
	    var tienvat = Math.round(parseFloat(thuesuat.replace(/,/g,""))/100*tongtien) ;
	    
	    document.getElementById("vat0").value = DinhDangTien(tienvat);
	    
	    var tongcong = parseFloat(tongtien) +  parseFloat(tienvat);
	    
	    document.getElementById("tongcong0").value = DinhDangTien(tongcong);
	}
	
	function keypress1(e) //Hàm dùng ngan khong cho nhap ki tu dac biet vao textbox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed == 39)
		{ 
			return false;
		}
	}
	
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	     $(document).ready(function() { $("select").select2();  });
	     
</script>
<script language="javascript" type="text/javascript">
	function goBack(){
		window.history.back();
	}
	function roundnumber4so(value, decimals) {
	    return Number(Math.round(value + 'e' + decimals) + 'e-' + decimals);
	}
</script>
 <script type="text/javascript" src="../scripts/general.js"></script>
 
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0"
	onload="Kiemtratable()">
	<form name="erpParkForm" method="post"
		action="../../ErpParkHoadonnhapkhauUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input
			type="hidden" name="action" value='1'> <input type="hidden"
			id="sophieunhap" value="<%=pnkList.size()%>" /> <input type="hidden"
			id="thuesuatPO" value="<%=pBean.getThuesuat()%>" />
			<input type="hidden" name="dungsai" id="dungsai" value='<%= pBean.getDungsai()%>'>
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left"
					class="tbnavigation">Quản lý mua hàng &gt; Mua hàng nhập khẩu &gt; Hóa đơn nhập khẩu &gt; Tạo mới</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn
					<%=userTen%>
					&nbsp;
				</div>
			</div>

			<div align="left" id="button"
				style="width: 100%; height: 32px; padding: 0px; float: none"
				class="tbdarkrow">
				<A href="javascript:goBack()"> <img
					src="../images/Back30.png" alt="Quay ve" title="Quay ve"
					border="1px" longdesc="Quay ve" style="border-style: outset"></A>
				<span id="btnSave"> <A href="javascript:saveform()"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai"
						border="1px" style="border-style: outset"></A>
				</span>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" rows="1" readonly="readonly"
						style="width: 100%"><%=pBean.getMsg()%></textarea>
					<%
						pBean.setMsg("");
					%>
				</fieldset>
			</div>

			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset id="hoadon">
					<legend class="legendtitle"> Hóa đơn NCC</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">

							<TR>
								<TD class="plainlabel" width="15%">Loại đơn mua hàng</TD>
								<TD class="plainlabel" width="22%"><select
									id="loaidonmuahangId" name="loaidonmuahangId"
									style="width: 350px;" onChange="submitform();">
										<option value="0" selected>Đơn mua hàng nhập khẩu</option>
								</select></TD>
								<TD class="plainlabel" width="15%">Ngày ghi nhận</TD>
								<TD class="plainlabel" colspan= "3" ><input type="text"
									readonly="readonly" class="days" id="ngayghinhan"
									name="ngayghinhan" value="<%=pBean.getNgayghinhan()%>"
									maxlength="10" /></TD>
								
							</TR>
							<TR>
								<TD class="plainlabel" width="15%">Nhà thụ hưởng</TD>
								<TD class="plainlabel" width="22%"><select id="nccId"
									name="nccId" style="width: 350px;" onChange="submitform();">
										<option value=""></option>
										<%
											if (rsNcc != null) {
												while (rsNcc.next()) {
													if (pBean.getNccId().equals(rsNcc.getString("pk_seq"))) {
										%>
										<option value="<%=rsNcc.getString("pk_seq")%>"
											selected="selected"><%=rsNcc.getString("TEN")%></option>
										<%
											} else {
										%>
										<option value="<%=rsNcc.getString("pk_seq")%>"><%=rsNcc.getString("TEN")%></option>
										<%
											}
												}
												rsNcc.close();
											}
										%>
								</select></TD>
								<TD class="plainlabel" width="15%">Chọn tiền tệ</TD>
								<TD class="plainlabel" colspan="3"><SELECT NAME="ttId"
									id="ttId" style="width: 200px; height: 20"
									onChange="submitform();">
										<%
									if (ttRs != null) {
										while (ttRs.next()) {
											if(!ttRs.getString("TTID").equals("100000")){
												if (ttRs.getString("TTID").equals(pBean.getTtId())) {
										%>
										<OPTION VALUE="<%=ttRs.getString("TTID")%>" SELECTED><%=ttRs.getString("TIENTE")%></OPTION>
										<%
												} else {
										%>
										<OPTION VALUE="<%=ttRs.getString("TTID")%>"><%=ttRs.getString("TIENTE")%></OPTION>
										<%
												}
											}
										}
									}
										%>

								</SELECT></TD>
							</TR>
							
                    	
		                    <TR>
		                    
		                    	<TD class="plainlabel" valign="middle">Số PO</TD>
								<TD class="plainlabel" valign="middle" ><select
									name="poId" id="poId" onchange="changePO();"
									style="width: 400px;" multiple>
										<option value=""></option>
										<%
											if (poRs != null) {
												while (poRs.next()) {
													if (pBean.getPoId().contains(poRs.getString("pk_seq"))) {
										%>
										<option value="<%=poRs.getString("pk_seq")%>"
											selected="selected"><%=poRs.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=poRs.getString("pk_seq")%>"><%=poRs.getString("ten")%></option>
										<%
											}
												}
												poRs.close();
											}
										%>
								</select></TD>
								
		                    	<TD class="plainlabel" width="15%">Tỉ giá </TD>
		                       	<TD class="plainlabel">
		                       		<input name = "tigia" type="text" id = "tigia" value = "<%= pBean.getTigia() %>" onchange="replaces();" onKeyPress = "return keypress(event);" />
		                       	</TD>
		                       	
		                       
								
		                    </TR>
       					



							<TR>
								<TD class="plainlabel" width="15%">Mẫu hoá đơn</TD>
								<TD class="plainlabel" width="15%"><input type="text"
									name="mausohoadon" id="mausohoadon" value="<%=pBean.getMauSoHoaDon()%>" />
								</TD>
								
								<TD class="plainlabel" width="15%">Tổng tiền nguyên tệ</TD>
								<TD class="plainlabel" width="15%"><input type="text"
									readonly name="tong" id="tong" value="<%=pBean.getTong()%>" />
								</TD>
								

							</TR>

							<TR>
								<TD class="plainlabel" width="15%">Số hoá đơn</TD>
								<TD class="plainlabel" width="15%"><input type="text"
									name="sohoadon" id="sohoadon" value="<%=pBean.getSoHoaDon()%>" />
									<input type="hidden"
									readonly name="sotienchuathue" id="sotienchuathue"
									value="<%=pBean.getSoTienChuaThue()%>" />
								</TD>
								
								<TD class="plainlabel" width="15%">Tổng tiền VND</TD>
								<TD class="plainlabel" width="15%"><input type="text"
									name="tongVND" id="tongVND"  readonly value="<%= 0 %>" />
								</TD>

							</TR>

							<TR>
								<TD class="plainlabel" width="15%">Ký hiệu hoá đơn</TD>
								<TD class="plainlabel" width="15%" colspan= "3" ><input type="text"
									name="kyhieuhoadon" id="kyhieuhoadon"
									value="<%=pBean.getKyHieuHoaDon()%>" />
									
									<input type="hidden"
									readonly name="thuegtgt" id="thuegtgt"
									value="<%=pBean.getThuegtgt()%>" />
								</TD>

							</TR>
							
							<TR>
								<TD class="plainlabel" width="15%">Ngày hoá đơn</TD>
								<TD class="plainlabel" width="15%" colspan= "1" ><input type="text"
									name="ngayhoadon" id="ngayhoadon"
									value="<%=pBean.getNgayHoaDon()%>" class="days" /></TD>
								<TD class="plainlabel" width="15%">Chênh lệch nguyên tệ</TD>
								<TD class="plainlabel" width="15%"   ><input type="text"
									name="chenhlechhd" id="chenhlechhd"
									value="<%=pBean.getChenhlechHd()%>" />
									 
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel" width="15%">Kho biệt trữ</TD>
								<TD class="plainlabel" width="22%"><select id="khobiettruId" onchange="ChonKhoBietTru();"
									name="khobiettruId" style="width: 200px;" >
										<option value=""></option>
										<%
											if (RsKhoBietTru != null) {
												while (RsKhoBietTru.next()) {
													if (pBean.getIdKhoBietTru().equals(RsKhoBietTru.getString("pk_seq"))) {
										%>
										<option value="<%=RsKhoBietTru.getString("pk_seq")%>"
											selected="selected"><%=RsKhoBietTru.getString("TEN")%></option>
										<%
											} else {
										%>
										<option value="<%=RsKhoBietTru.getString("pk_seq")%>"><%=RsKhoBietTru.getString("TEN")%></option>
										<%
											}
												}
												RsKhoBietTru.close();
											}
										%>
								 </select></TD>
								
								<TD class="plainlabel" width="15%">Kho tồn trữ</TD>
								<TD class="plainlabel" colspan="3"><SELECT NAME="khotontruId"
									id="khotontruId" style="width: 200px;">
									<option value=""></option>
										<%
											if (RsKhoTonTru != null) {
												while (RsKhoTonTru.next()) {
													if (RsKhoTonTru.getString("pk_seq").equals(pBean.getIdKhoTonTru())) {
										%>
										<OPTION VALUE="<%=RsKhoTonTru.getString("pk_seq")%>" SELECTED><%=RsKhoTonTru.getString("ten")%></OPTION>
										<%
											} else {
										%>
										<OPTION VALUE="<%=RsKhoTonTru.getString("pk_seq")%>"><%=RsKhoTonTru.getString("ten")%></OPTION>
										<%
											}
												}
											}
										%>

								</SELECT></TD>
							</TR>
							<TR>
				          		<TD class="plainlabel" valign="middle" >Diễn giải chứng từ</TD>
								<TD class="plainlabel" valign="middle"  colspan="4">
									<input type="text" name="dienGiaiCT" value="<%= pBean.getDienGiaiCT() %>"  onKeyPress = "return keypress1(event);">
								</TD>
							</TR>	
							
							
							


						</TABLE>

						<hr>
					</div>

					<!-- LIST SẢN PHẨM -->
					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1"
							cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="3%">STT</TH>
								<TH align="center" width="8%">Mã sản phẩm</TH>
								<TH align="center" width="23%">Tên sản phẩm</TH>
								<TH align="center" width="7%">ĐVT</TH>
								<TH align="center" width="8%">Số lượng đặt</TH>
								<TH align="center" width="7%">Đơn giá</TH>
								
								<TH align="center" width="8%">Đơn giá VND</TH>
								
								<!-- <TH align="center" width="5%">VAT</TH> -->
								<TH align="center" width="11%">Số lượng đã nhận</TH>
								<TH align="center" width="8%">Số lượng HĐ</TH>
								<TH align="center" width="8%">Thành tiền</TH>
								
								<TH align="center" width="14%">Thành tiền VND</TH>


							</TR>


							<!--  LOAD SẢN PHẨM RA -->

							<%
								if (pBean.getPoId().trim().length() > 0) {
									for (int i = 0; i < spList.size(); i++) {
										ISanpham sp = spList.get(i);
							%>
							<tr>
								<td align="center"><input type="text"
									style="width: 100%; text-align: center;" value="<%=i + 1%>"
									name="stt" readonly="readonly"></td>


								<td align="left"><input type="text"
									style="width: 100%; text-align: left;" value="<%=sp.getMa()%>"
									name="mahangmua" readonly="readonly"> <input
									type="hidden" value="<%=sp.getId()%>" name="idhangmua">
									<input type="hidden" value="<%=sp.getMuahang_fk()%>"
									name="muahang_fk"> <input type="hidden"
									value="<%=sp.getHansudung()%>" name="hansudung"> <input
									type="hidden" value="<%=sp.getIdmarquette()%>"
									name="idmarquette"></td>
								<td align="left"><input type="text"
									style="width: 100%; text-align: left;"
									value="<%=sp.getDiengiai()%>" name="tenhangmua"
									readonly="readonly"></td>

								<td align="left">
									<input type="hidden"
									style="width: 100%; text-align: left;"
									value="<%=sp.getDvdl()%>" name="dvdl" readonly="readonly">
									
									<select style="width: 100%" name="dvdl" disabled style=" width: 200px">
															<option value=""></option>
														<% if (dvList.size() > 0)
															{
																for (int j = 0; j < dvList.size(); j++)
																{
																	IDonvi donvi =  dvList.get(j);
																	
																	if(donvi.getId().equals(sp.getDvdl())){
																%>
																	<option value="<%=donvi.getId()%>" selected="selected"><%=donvi.getDonvi()%></option>
																<% } else { %>
																	<option value="<%=donvi.getId()%>"><%=donvi.getDonvi()%></option>
																 <% } } }  %>
									</select>
									
								</td>
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%=sp.getSoluongdat()%>" name="soluongdat"
									readonly="readonly"></td>
									
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%=sp.getDongia()%>" name="dongiaMua"
									readonly="readonly">
									
									
										
								</td>
								<td>
									<input type="text"
									style="width: 100%; text-align: right;"
									value="<%=sp.getDongiaVND()%>" name="dongiaMuaVND"
									readonly="readonly">
									<input type="hidden"
									style="width: 100%; text-align: right;"
									value="<%=formatter2.format(Double.parseDouble(sp.getVat().replaceAll(",","")))%>"
									name="vat" readonly="readonly">
								</td>
								
									
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%=formatter2.format(Double.parseDouble(sp.getSoluongDaNhan().replaceAll(",","")))%>"
									name="soluongdanhan" readonly="readonly"></td>
								
								<td align="right"><input type="text"
									style="width: 70%; text-align: right;"
									value="<%=sp.getSoluongnhan()%>" name="soluongnhan"
									readonly="readonly" onKeyPress="return keypress(event);"
									onchange="DinhDang();"> <input type="hidden"
									value="<%=sp.getSoluongMaxNhan()%>" name="soluongMAXnhan">
									<a href="" id="<%=sp.getId() + sp.getMuahang_fk()+i%>"
									rel="subcontent<%=i%>"> <img alt="Số lô - Số lượng nhận"
										src="../images/vitriluu.png"></a>

									<DIV id="subcontent<%=i%>"
										style="position: absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 450px; padding: 4px;">
										<table width="90%" align="center">
											<tr>
												<th width="100px">Số lô</th>
												<th width="100px">Số lượng</th>
												<th width="100px">NSX</th>
											<th width="100px">Marrquet</th>
												<th width="100px">Ngày sản xuất</th>
												<th width="100px">Ngày hết hạn</th>
											</tr>
											<%
												List<ISpDetail> spDetailList = sp.getSpDetail();
														int stt = 0;

														if (spDetailList.size() > 0) {
															for (int sd = 0; sd < spDetailList.size(); sd++) {
																ISpDetail spDetail = spDetailList.get(sd);
											%>
											<tr>
												<td><input type="text" size="100px"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".solo"%>"
													value="<%=spDetail.getSolo()%>" onchange="replaces()" /></td>
												<td><input type="text" size="100px" onkeyup="Lamtronso(this)"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluong"%>"
													value="<%=spDetail.getSoluong()%>"
													style="text-align: right;" onchange="replaces()" /></td>
													<td><input type="text" size="100px"  
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxten"%>"
													value="<%=spDetail.getNSXTen()%>" 
													  />
													<input type="hidden" size="100px"  
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxid"%>"
													value="<%=spDetail.getNSXid()%>" 
													 />
													</td>
													<td><input type="text" size="100px"  
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".marrquet"%>"
													value="<%=spDetail.getMarrquet()%>" 
													 /></td>
												<td><input type="text" size="100px" class="days"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngaysanxuat"%>"
													value="<%=spDetail.getNgaySx()%>" 
													onchange="replaces()" /></td>
												<td><input type="text" size="100px" class="days"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethan"%>"
													value="<%=spDetail.getNgayHethan()%>" readonly="readonly" />
												</td>
											</tr>
											<%
												stt++;
															}
														}
											%>
											<%
												for (int k = 0; k < 10 - spDetailList.size(); k++) {
											%>
											<tr>
												<td><input type="text" size="100px"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".solo"%>"
													value="" onchange="replaces()" /></td>
												<td><input type="text" size="100px" onkeyup="Lamtronso(this)"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".soluong"%>"
													value="" style="text-align: right;" onchange="replaces()" /></td>
													 <td><input type="text" size="100px"  
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxten"%>"
													value="" 
													 />
													<input type="hidden" size="100px"  
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxid"%>"
													value="" 
													  />
													</td>
													<td><input type="text" size="100px"  
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".marrquet"%>"
													value="" 
													  /></td>
												<td><input type="text" size="100px" class="days"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngaysanxuat"%>"
													value=""  onChange="replaces()" /></td>
												<td><input type="text" size="100px"
													name="<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".ngayhethan"%>"
													class="days" value="" readonly="readonly" /></td>
											</tr>
											<%
												stt++;
														}
											%>
										</table>
										<div align="right">
											<label style="color: red"></label>
											&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; <a
												href="javascript:dropdowncontent.hidediv('subcontent<%=i%>')">Hoàn
												tất</a>
										</div>
									</DIV></td>
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;"
									value="<%=sp.getthanhtien()%>" name="thanhtien"
									readonly="readonly"></td>
									
								<td align="right"><input type="text"
									style="width: 100%; text-align: right;" onchange ="DieuChinhTien();"
									value="<%=sp.getThanhtienVND()%>" name="thanhtienVND"
									></td>
									
 									<script type="text/javascript">
	
															jQuery(function()
																{		
																	$("input[name='<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".nsxten"%>']").autocomplete("NhaSanXuatList.jsp?");
																});	
															jQuery(function()
																	{		
																		$("input[name='<%=sp.getId() + "." + sp.getMuahang_fk() +i+ ".marrquet"%>']").autocomplete("MarrquetList.jsp?sanphamId=<%=sp.getId()%>");
																	});	
												</script>
							</tr>
							<%
								}
								}
							%>
						</TABLE>
					</div>

				</fieldset>
			</div>
		</div>
		<script type="text/javascript">
</script>

	</form>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>
	<script type="text/javascript">
</script>
</BODY>
<script type="text/javascript">
replaces_1();
	<%if (spList != null) {
				for (int i = 0; i < spList.size(); i++) {
					ISanpham sp = spList.get(i);%>
		   dropdowncontent.init('<%=sp.getId() + sp.getMuahang_fk()+i %>', "left-top", 300, "click");
	<%}
			}%>
</script>
</html>
<%
	/* if(rsNcc != null) rsNcc.close(); */
	if (ttRs != null)
		ttRs.close();
	
	pBean.close();
%>
