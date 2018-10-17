<%@page import="geso.traphaco.erp.util.DinhDang"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.thuenhapkhau.imp.*" %>
<%@ page import="geso.traphaco.erp.beans.thuenhapkhau.*" %>
<%@ page import="java.util.Calendar" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
<%@ page import="java.util.Hashtable" %>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<%@page import="java.sql.SQLException" %>

<%	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>

<%
 	IErpThuenhapkhau obj =(IErpThuenhapkhau)session.getAttribute("tnkBean");
	ResultSet rs;
	 ResultSet soHDRs = obj.getSoHoaDonNew(); 
%>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>
 <%	NumberFormat formatter2 = new DecimalFormat("#,###,###.##"); %> 
<%	NumberFormat formatter3 = new DecimalFormat("#,###,###.#########"); %>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>

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
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<SCRIPT language="JavaScript" type="text/javascript">
	function submitform()
	{
	    document.forms["tnk"].submit();
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
	
	function save()
	{
		
		var cqtId = document.getElementById("cqtId").selectedIndex;
		var dataerror = document.getElementById("dataerror");
		console.log("cqtId: " + cqtId);
		if (cqtId == "0")
		{
			dataerror.value = "Vui lòng chọn 'Cơ quan thuế'";
			return;
		}
		
		var ngaychungtu = document.getElementById("ngaychungtu").value;
		if (ngaychungtu == 0)
		{
			dataerror.value = "Vui lòng chọn 'Ngày tờ khai'";
			return;
		}
		
		var nccId = document.getElementById("nccId").selectedIndex;
		if (nccId == "0")
		{
			dataerror.value = "Vui lòng chọn 'Nhà cung cấp'";
			return;
		}
		
		var hoadonID = document.getElementsByName("hoadonID");
		var txt = "";
		var i;
		for (i = 0; i < hoadonID.length; i++) {
		    if (hoadonID[i].checked) {
		        txt = txt + hoadonID[i].value + " ";
		    }
		}
		
		if (txt == 0)
		{
			dataerror.value = "Vui lòng chọn 'Số hóa đơn (Invoice)'";
			return;
		}
    
		var diengiai = document.getElementById("diengiai").value;
		if (diengiai == 0)
		{
			dataerror.value = "Vui lòng nhập 'Diễn giải'";
			return;
		}
		
		var maHS = document.getElementById("maHS").value;
		if (maHS == 0)
		{
			dataerror.value = "Vui lòng nhập 'Mã HS'";
			return;
		}
		
		var tigia = document.getElementById("tigia").value;
		if (tigia == 0)
		{
			dataerror.value = "Vui lòng nhập 'Tỉ giá'";
			return;
		}
		
	  document.forms["tnk"].action.value = "save";
	  document.forms["tnk"].submit(); 
    }
	
	function replaces()
	{	
		var cqt = document.getElementsByName("cqt");
		var cqtId = document.getElementsByName("cqtId");
		
		if(parseInt(cqt[0].value.indexOf(" - ")) > 0){
			cqtId[0].value = cqt[0].value.substring(0, parseInt(cqt[0].value.indexOf(" - ")));
		}
		
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
		
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["tnk"].submit();
		}
		
		var po = document.getElementsByName("po");
		var poId = document.getElementsByName("poId");
		
		var poId_old = poId[0].value;
		var poId_new = po[0].value.substring(5, parseInt(po[0].value.indexOf("]")));
		
		if(poId_old != poId_new){
			poId[0].value = po[0].value.substring(5, parseInt(po[0].value.indexOf("]")));
			document.forms["tnk"].submit();
		}
		
		var vat = document.getElementsByName("VAT");
		vat[0].value = DinhDangTien(vat[0].value);
		
		var thueNK = document.getElementsByName("thueNK");
		thueNK[0].value = DinhDangTien(thueNK[0].value);
		
		setTimeout(replaces, 300);
	}
	
	function Dinhdang(element)
	{
		element.value=DinhDangTien(element.value);
		if(element.value== '' ||element.value=='0' )
		{
			element.value= '';
		}
	} 	
	
	function Dinhdangsoluong(element)
	{
		element.value = DinhDangTien2(element.value);
		if(element.value == '' || element.value == '0' )
		{
			element.value= '';
		}
	} 	
	
	function CheckSoLuong()
	{
		var tongsoluonggoc = document.getElementsByName("tongsoluonggoc");
		var tongsoluong = document.getElementsByName("tongsoluong");
		
		for (var i = 0 ; i < tongsoluong.length ; i ++)
		{
			tongsoluong[i].value = tongsoluong[i].value.replace(",","");
			tongsoluonggoc[i].value = tongsoluonggoc[i].value.replace(",","");
			
			var tonggoc = parseFloat(tongsoluonggoc[i].value);
			var tong = parseFloat(tongsoluong[i].value);
			
			if (tong > tonggoc)
			{
				tongsoluong[i].value = tongsoluonggoc[i].value;
			}

			tongsoluong[i].value = DinhDangTien2(tongsoluong[i].value);
			tongsoluonggoc[i].value = DinhDangTien2(tongsoluonggoc[i].value);
			
		}
		
	}
	
	function DinhDangTien(num) 
	 {
	    num = num.toString().replace(/\$|\,/g,'');
	    if(isNaN(num))
	    num = "0";
	    sign = (num == (num = Math.abs(num)));
	    num = Math.floor(num*100+0.50000000001);
	    num = Math.floor(num/100).toString();
	    for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
	    num = num.substring(0,num.length-(4*i+3))+','+
	    num.substring(num.length-(4*i+3));
	    return (((sign)?'':'-') + num);
	}
	
	function DinhDangTien2(num) 
	 {
	    
		num = num.toString().replace(/\$|\,/g,'');
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
			if( ( parseFloat(kq.indexOf(".")) + 3 ) < kq.length )
			{
				//alert('Cat toi: ' + ( parseFloat(kq.indexOf(".")) + 3 ) );
				kq = kq.substring(0, parseFloat(kq.indexOf(".")) + 3);
			}
		}
		
		return kq;
	    
	}

	function tinhtien(){
		var tienhang = document.getElementsByName("tienhang");
		var ptThueNK = document.getElementsByName("ptThueNK");
		var thueNK = document.getElementsByName("thueNK");

		var ptvat = document.getElementsByName("ptVAT");
		var vat = document.getElementsByName("VAT");

		tienhang[0].value = tienhang[0].value.replace(/,/g,"");
		thueNK[0].value = parseFloat(tienhang[0].value)*parseFloat(ptThueNK[0].value)/100;
		vat[0].value = (parseFloat(tienhang[0].value) + parseFloat(thueNK[0].value))*parseFloat(ptvat[0].value)/100;		

		tienhang[0].value = DinhDangTien(tienhang[0].value);
		thueNK[0].value = DinhDangTien(thueNK[0].value);
		vat[0].value = DinhDangTien(vat[0].value);
	}
	
	function tinhtien_2(){
		var ptvat = document.getElementsByName("ptVAT");
		var vat = document.getElementsByName("VAT");

		var thanhtien = document.getElementsByName("thanhtien");
		var thuesuat = document.getElementsByName("thuesuat");
		var thue = document.getElementsByName("thue");
		var cong = document.getElementsByName("cong");

		var total = 0;
		for(i = 0; i < thanhtien.length; i++){
			while(thanhtien[i].value.match(","))
			{
				thanhtien[i].value = thanhtien[i].value.replace(",","");
			}

			while(thuesuat[i].value.match(","))
			{
				thuesuat[i].value = thuesuat[i].value.replace(",","");
			}

			while(thue[i].value.match(","))
			{
				thue[i].value = thue[i].value.replace(",","");
			}
			
			thue[i].value = parseFloat(thanhtien[i].value)*parseFloat(thuesuat[i].value)/100;
			cong[i].value = parseFloat(thue[i].value) + parseFloat(thanhtien[i].value);
			
			total = total + parseFloat(cong[i].value);
						
			thanhtien[i].value = DinhDangTien(thanhtien[i].value);
			thue[i].value = DinhDangTien(Math.round(thue[i].value));
			cong[i].value = DinhDangTien(cong[i].value);
		}
		while(vat[0].value.match(",")){
			vat[0].value = vat[0].value.replace(",","");
		}
		
		var tmp = parseFloat(vat[0].value)*100/parseFloat(total);
		ptvat[0].value = Math.round(tmp);
		
//		ptvat[0].value = DinhDangTien(ptvat[0].value);
		vat[0].value = DinhDangTien(vat[0].value);
	}

	function tinhtien_3(){
		var ptvat = document.getElementsByName("ptVAT");
		var vat = document.getElementsByName("VAT");

		var thanhtien = document.getElementsByName("thanhtien");
		var thuesuat = document.getElementsByName("thuesuat");
		var thue = document.getElementsByName("thue");
		var cong = document.getElementsByName("cong");

		var total = 0;
		for(i = 0; i < thanhtien.length; i++){
			while(thanhtien[i].value.match(","))
			{
				thanhtien[i].value = thanhtien[i].value.replace(",","");
			}

			while(thuesuat[i].value.match(","))
			{
				thuesuat[i].value = thuesuat[i].value.replace(",","");
			}

			while(thue[i].value.match(","))
			{
				thue[i].value = thue[i].value.replace(",","");
			}

			var tmp = (parseFloat(thue[i].value)*100/parseFloat(thanhtien[i].value));
			thuesuat[i].value = tmp.toFixed(2);
			cong[i].value = parseFloat(thue[i].value) + parseFloat(thanhtien[i].value);
			
			total = total + parseFloat(cong[i].value);
						
			thanhtien[i].value = DinhDangTien(thanhtien[i].value);
			thue[i].value = DinhDangTien(Math.round(thue[i].value));
			cong[i].value = DinhDangTien(cong[i].value);
		}
		vat[0].value = parseFloat(ptvat[0].value)*total/100;
		vat[0].value = DinhDangTien(Math.round(vat[0].value));
	}

	function tinhtien_4(){
		var ptvat = document.getElementsByName("ptVAT");
		var vat = document.getElementsByName("VAT");
		
		var thanhtien = document.getElementsByName("thanhtien");
		var thuesuat = document.getElementsByName("thuesuat");
		var thue = document.getElementsByName("thue");
		var cong = document.getElementsByName("cong");
		
		var total = 0;
		for(i = 0; i < thanhtien.length; i++){
			while(thanhtien[i].value.match(","))
			{
				thanhtien[i].value = thanhtien[i].value.replace(",","");
			}

			while(thuesuat[i].value.match(","))
			{
				thuesuat[i].value = thuesuat[i].value.replace(",","");
			}

			while(thue[i].value.match(","))
			{
				thue[i].value = thue[i].value.replace(",","");
			}

			thue[i].value = parseFloat(thanhtien[i].value)*parseFloat(thuesuat[i].value)/100;
			cong[i].value = parseFloat(thue[i].value) + parseFloat(thanhtien[i].value);
			
			total = total + parseFloat(cong[i].value);
			
			thanhtien[i].value = DinhDangTien(thanhtien[i].value);
			thue[i].value = DinhDangTien(Math.round(thue[i].value));
			cong[i].value = DinhDangTien(cong[i].value);
		}
		vat[0].value = parseFloat(ptvat[0].value)*total/100;
		vat[0].value = DinhDangTien(Math.round(vat[0].value));

	}
	
	function DinhdangNT()
	{
		var giatrinhap = document.getElementById("tigia").value;
		giatrinhap = parseFloat(giatrinhap);
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
			
			//alert(kq);
		return kq;
	}

	function changeHoaDonNcc()
	{
		document.forms["tnk"].action.value = "changeHoaDonNcc";
		document.forms["tnk"].submit(); 
	}
	
	function setTienTe(id , value)
	{
		document.getElementById("tienTeId").value = id;
		document.getElementById("maTienTe").value = value;
	}
	
	function changeHoaDonNcc()
	{
		document.forms["tnk"].action.value = "changeHoaDonNcc";
		document.forms["tnk"].submit(); 
	}
	
	function tinhTienHangHoaTheoTiGia()
	{
		var tigia = document.getElementById("tigia").value.replace(",","");
		var donGiaVND = document.getElementsByName("donGiaVND");
		var donGiaNT = document.getElementsByName("donGiaNT");
		var thanhTien = document.getElementsByName("thanhTien");
		var tienTinhThueNhapKhau = document.getElementsByName("tienTinhThueNhapKhau");
		var soLuong = document.getElementsByName("soLuong");
		var thueNk=document.getElementsByName("thueNK");
		var VATNK=document.getElementsByName("VATNK");
		var tienHang = 0;
		for(i = 0; i < thanhTien.length; i++)
		{
			while(thanhTien[i].value.match(","))
			{
				thanhTien[i].value = thanhTien[i].value.replace(/\$|\,/g,'');
			}

			while(donGiaVND[i].value.match(","))
			{
				donGiaVND[i].value = donGiaVND[i].value.replace(/\$|\,/g,'');
			}

			while(donGiaNT[i].value.match(","))
			{
				donGiaNT[i].value = donGiaNT[i].value.replace(/\$|\,/g,'');
			}

			while(soLuong[i].value.match(","))
			{
				soLuong[i].value = soLuong[i].value.replace(/\$|\,/g,'');
			}
			
			while(thueNk[i].value.match(","))
			{
				thueNk[i].value = thueNk[i].value.replace(/\$|\,/g,'');
			}
			
			donGiaVND[i].value = parseFloat(donGiaNT[i].value)*parseFloat(tigia);
			tienTinhThueNhapKhau[i].value =DinhDangTien ( parseFloat(donGiaNT[i].value)*parseFloat(tigia) * parseFloat(soLuong[i].value) );
			tienHang += parseFloat(donGiaNT[i].value)*parseFloat(tigia) * parseFloat(soLuong[i].value);
			
			console.log(" tien thue nhap khau: "+ tienTinhThueNhapKhau[i].value);
			console.log(" tien thue nhap khau: "+ DinhDangTien (tienTinhThueNhapKhau[i].value));

			/* tienTinhThueNhapKhau[i].value = DinhDangTien(Math.round(tienTinhThueNhapKhau[i].value)); */
			donGiaVND[i].value = DinhDangTien(donGiaVND[i].value);
			
			thueNk[i].value=DinhDangTien(thueNk[i].value.replace(/\$|\,/g,'') * parseFloat(tigia));
			VATNK[i].value=DinhDangTien(VATNK[i].value.replace(/\$|\,/g,'') * parseFloat(tigia));
		}
		document.getElementById("tienhang").value = DinhDangTien2(tienHang);
		
		
		
		tinhThueVATNK_All();
	}
	
	function tinhThueNhapKhau(index)
	{
		
		var tienTinhThueNhapKhau = document.getElementById("tienTinhThueNhapKhau_" + index).value.replace(/\$|\,/g,'');
		var thueSuatNK = document.getElementById("thueSuatNK_" + index).value.replace(/\$|\,/g,'');
		var thueNK = document.getElementById("thueNK_" + index);
		
		console.log("tienTinhThueNhapKhau_: " + tienTinhThueNhapKhau);
		console.log("tienTinhThueNhapKhau: " + parseFloat(tienTinhThueNhapKhau));
		console.log("thueSuatNK: " + parseFloat(thueSuatNK));
		
		// tinh thue nk tung dong
		thueNK.value = Math.round(parseFloat(tienTinhThueNhapKhau) * parseFloat(thueSuatNK) / 100) ;
		thueNK.value = DinhDangTien(thueNK.value);
		// tinh lai tien VAT sau
		tinhThueVATNK(index);
		
		// tinh tien tong thue nk
		var thueNKArr = document.getElementsByName("thueNK");
		var thueNhapKhau = 0;
		for(i = 0; i < thueNKArr.length; i++)
		{
			/*  while(thueNKArr[i].value.match(","))
			{
				thueNKArr[i].value = thueNKArr[i].value.replace(/\$|\,/g,'');
			}  */

			thueNhapKhau += parseFloat(thueNKArr[i].value.replace(/\$|\,/g,''));
		}
		document.getElementById("thueNKTong").value = DinhDangTien(thueNhapKhau);
		
		
	}
	
	function tinhThueNK_All()
	{
		var thueNK = document.getElementsByName("thueNK");
		var VATNhapKhau = 0;
		for(i = 0; i < VATNK.length; i++)
		{
			//
			var thanhTien = document.getElementById("tienTinhThueNhapKhau_" + i).value.replace(/\$|\,/g,'');
			var phanTramThueNK = document.getElementById("hueSuatNK_" + i).value.replace(/\$|\,/g,'');
			var thueNK = document.getElementById("thueNK_" + i).value.replace(/\$|\,/g,'');
			var VATNK_ = document.getElementById("VATNK_" + i);
			
			VATNK_.value = Math.round((parseFloat(thanhTien) + parseFloat(thueNK)) * parseFloat(phanTramVATNK) / 100);
			VATNK_.value = DinhDangTien(VATNK_.value);
			//
			 while(VATNK[i].value.match(","))
			{
				VATNK[i].value = VATNK[i].value.replace(/\$|\,/g,'');
			}

			VATNhapKhau += parseFloat(VATNK[i].value.replace(/\$|\,/g,''));
		}
		
		document.getElementById("VAT").value = DinhDangTien(VATNhapKhau);
	}
	
	function tinhThueVATNK(index)
	{
		var thanhTien = document.getElementById("tienTinhThueNhapKhau_" + index).value.replace(/\$|\,/g,'');
		var phanTramVATNK = document.getElementById("phanTramVATNK_" + index).value.replace(/\$|\,/g,'');
	
		var thueNK = document.getElementById("thueNK_" + index).value.replace(/\$|\,/g,'');
		var VATNK = document.getElementById("VATNK_" + index);
		
		console.log( " dang tien tien vat nk: thanhTien: "+ thanhTien);
		console.log( " dang tien tien vat nk: tien thue nk: "+ thueNK);
		
		VATNK.value = Math.round((parseFloat(thanhTien) + parseFloat(thueNK)) * parseFloat(phanTramVATNK) / 100);
		VATNK.value = DinhDangTien(VATNK.value);
		
		tinhThueVATNK_All();
	}
		
	function tinhThueVATNK_All()
	{
		var VATNK = document.getElementsByName("VATNK");
		var THUENK = document.getElementsByName("thueNK");
		var VATNhapKhau = 0;
		var thueNKtong=0;
		for(i = 0; i < VATNK.length; i++)
		{
			//
			var thanhTien = document.getElementById("tienTinhThueNhapKhau_" + i).value.replace(/\$|\,/g,'');
			var phanTramVATNK = document.getElementById("phanTramVATNK_" + i).value.replace(/\$|\,/g,'');
			var thueNK = document.getElementById("thueNK_" + i).value.replace(/\$|\,/g,'');
			var VATNK_ = document.getElementById("VATNK_" + i);
			
			VATNK_.value = Math.round((parseFloat(thanhTien) + parseFloat(thueNK)) * parseFloat(phanTramVATNK) / 100);
			VATNK_.value = DinhDangTien(VATNK_.value);
			
			document.getElementById("thueNK_" + i).value= DinhDangTien(thueNK);
			

			thueNKtong +=  parseFloat(THUENK[i].value.replace(/\$|\,/g,''));
			VATNhapKhau += parseFloat(VATNK[i].value.replace(/\$|\,/g,''));
		}
		
		document.getElementById("VAT").value = DinhDangTien(VATNhapKhau);
		document.getElementById("thueNKTong").value = DinhDangTien(thueNKtong);
	}
	
	function tinhThueVATTuDo()
	{
		var VATNK= document.getElementsByName("VATNK");
		var sumVAT=0;
		for(i = 0; i < VATNK.length; i++){
			
			sumVAT+=parseFloat(VATNK[i].value.replace(/\$|\,/g,''));
		}
		
		document.getElementById("VAT").value = DinhDangTien(sumVAT);
	}
	
	
	
	
</SCRIPT>

	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name ="tnk" method="post" action="../../ErpThuenhapkhauUpdateSvl" >
<input type="hidden" name="userId" value='<%= userId %>' >
<input type="hidden" name="action" value="0">
<input type="hidden" name="loaimh" value="<%=obj.getLoaiMh()%>" >

<TABLE width="100%" border="0" cellspacing="0" cellpadding="0"
	height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
			<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
				<TR>
					<TD align="left" class="tbnavigation">
						<table width="100%" border="0" cellpadding="0" cellspacing="0">
						  <tr height="22">
							 <TD align="left" colspan="2" class="tbnavigation">&nbsp;Quản lý mua hàng &gt; Mua hàng nhập khẩu &gt; Thuế nhập khẩu &gt; Tạo mới</TD> 
							 <TD colspan="2" align="right" class="tbnavigation">Chào mừng bạn <%= userTen %></TD></tr>
						</table>
			 		</TD>
				</TR>
			</TABLE>
		<TABLE width="100%" border="0" cellpadding="0" cellspacing="1">
			<TR ><TD >
					<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
						<TR class = "tbdarkrow">
							<TD width="30" align="left"><A href="../../ErpThuenhapkhauSvl?userId=<%= userId%>&loaimh=<%=obj.getLoaiMh() %>" ><img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1" longdesc="Quay ve" style="border-style:outset"></A></TD>
						    <TD width="2" align="left" ></TD>
						    <TD width="30" align="left" >
						    <div id="btnSave">
						    <A href="javascript: save();" ><IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border = "1"  style="border-style:outset"></A>
						    </div>
						    </TD>
							<TD >&nbsp; </TD>						
						</TR>
					</TABLE>
			</TD></TR>
		</TABLE>
			<TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >
			  	<tr>
					<TD align="left" colspan="4" class="legendtitle">
						<FIELDSET>
						<LEGEND class="legendtitle">Thông báo </LEGEND>
				
	    				<textarea name="dataerror" id="dataerror"  style="width: 100% ; font-weight:bold"  style="width: 100% ; color:#F00 ; font-weight:bold" readonly="readonly" rows="1"><%= obj.getMsg() %></textarea>
						</FIELDSET>
				   </TD>
				</tr>			

				<TR>
				  <TD height="100%" width="100%">
						<FIELDSET >
						<LEGEND class="legendtitle" style="color:black">Thuế nhập khẩu</LEGEND>
						<TABLE border="0" width="100%" cellpadding="6" cellspacing="0">
                          
                         <%--  <TR>
                          		<TD class="plainlabel" valign="middle" width="150px" >Ngày nhập</TD>   
		                        <TD class="plainlabel" valign="middle"  >
		                        	<input type="text" name="ngaynhap" value="<%= obj.getNgaynhap() %>" class="days"  > 
		                        </TD>          
		                  </TR>  --%>
                         
	                     <TR>
                         		<TD class="plainlabel" valign="middle" >Cơ quan thuế</TD>
                        		<TD class="plainlabel" valign="middle" colspan="5">
								<SELECT name = "cqtId" id = "cqtId"  style="width: 400px" >
										<OPTION VALUE = "" >&nbsp;</OPTION>
			               <% 	rs = obj.getCoquanthue();
        		              	if(rs != null){
        		                	while(rs.next()){	
        		               			if(obj.getCqtId().equals(rs.getString("CQTID"))){	%> 	   	
        		                	   
        		                	    <OPTION VALUE = "<%= rs.getString("CQTID")%>" SELECTED><%= rs.getString("CQT")%></OPTION>
        		                	   
        		                <% 	  	}else{ %>
        		                		<OPTION VALUE = "<%= rs.getString("CQTID")%>" ><%= rs.getString("CQT")%></OPTION>
        		                <%		}
        		                	}
        		                	rs.close();
        		                }
        		                %>
								
								</SELECT>                        	
                        		</TD>                        
						</TR>

                        <TR>
                       		<TD class="plainlabel" valign="middle" width="120px" >Ngày tờ khai</TD>   
	                    	<TD class="plainlabel" valign="middle" colspan="5">
	                        	<input type="text" name="ngaychungtu" id="ngaychungtu" value="<%= obj.getNgaychungtu() %>" class="days" readonly="readonly"> 
		                    </TD>          
		                </TR> 

                        <TR>
                         		<TD class="plainlabel" valign="middle" width="120px" >Số tờ khai</TD>   
		                        <TD class="plainlabel" valign="middle" colspan="5">
		                        	<input type="text" name="sochungtu" value="<%= obj.getSochungtu() %>"  > 
		                        </TD>          
		                </TR> 

		                <TR>
                         	<TD class="plainlabel" valign="middle" >Mã HS</TD>
                        	<TD class="plainlabel" valign="middle" colspan="5">
		                     	<input type="text" id="maHS" name="maHS" value = "<%= obj.getMaHS() %>" style="width: 100px"> 
                        	</TD>                        
						</TR>
						
	                    <TR>
                         	<TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        	<TD class="plainlabel" valign="middle" colspan="5">
								<SELECT NAME = "nccId" id = "nccId"  style="width: 400px" onChange = "submitform();">
										<OPTION VALUE = "" >&nbsp;</OPTION>
			               <% 	rs = obj.getNccList();
        		              	if(rs != null){
        		                	while(rs.next()){	
        		               			if(obj.getNccId().equals(rs.getString("NCCID"))){	%> 	   	
        		                	   
        		                	   <OPTION VALUE = "<%= rs.getString("NCCID")%>" SELECTED><%= rs.getString("NCCTEN")%></OPTION>
        		                	   
        		                <% 	  	}else{ %>
        		                		<OPTION VALUE = "<%= rs.getString("NCCID")%>" ><%= rs.getString("NCCTEN")%></OPTION>
        		                <%		}
        		                	}
        		                	rs.close();
        		                }
        		                %>
								
								</SELECT>                        	
                        	</TD>                        
						</TR>
                         
	                   <%--  <TR>
                         	<TD class="plainlabel" valign="middle" >Số đơn mua hàng (PO)</TD>
                        	<TD class="plainlabel" valign="middle">
								<SELECT NAME = "poId" id = "poId"  style="width: 400px" >
		                    <% rs = obj.getPoList();
       		                   if(rs != null){
       		                	   while(rs.next()){	
       		               	   			if(obj.getPOId().equals(rs.getString("MHID"))){	%> 	   	
        		                	   
        		                	   <OPTION VALUE = "<%= rs.getString("MHID")%>" SELECTED><%= rs.getString("PO")%></OPTION>
        		                	   
        		                <% 	  	}else{	%>
        		                	   <OPTION VALUE = "<%= rs.getString("MHID")%>" ><%= rs.getString("PO")%></OPTION>
        		                <%		}
       		                	   }
        		                   rs.close();
        		               }
        		               %>
								
								</SELECT>                        	
                        	</TD>                        
						</TR>

	                    <TR>
                         	<TD class="plainlabel" valign="middle" >Số hóa đơn (Invoice)</TD>
                        	<TD class="plainlabel" valign="middle">
		                     	<input type="text" id="sohd" name="sohd" value = "<%= obj.getSoHD() %>" >
                        	</TD>                        
						</TR> --%>
						
						 <TR>
                         	<TD class="plainlabel" valign="middle" >Số hóa đơn (Invoice)</TD>                       
											
							<TD class="plainlabel" colspan="5">
								
								<a href="" id="soHDLIST" rel="subcontentHD"><img alt="Số Invoice" src="../images/vitriluu.png"></a>
		                   		<DIV id="subcontentHD" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 450px; padding: 4px; max-height: 200px; overflow: auto;" >
						          <table width="100%" align="center">
							        <tr>
							         <th width="100px">Số Invoice</th>
							         <th width="100px">Ngày</th>
							         <th width="100px">Tiền tệ</th>
							         <th width="100px" style="display: none;">Tổng số lượng</th>
							         <th width="100px" align="center" >Chọn</th>
							        </tr>
							       <%
							        	String tienTe = "";
							        	if(soHDRs != null)
							        	{ 
							        		while(soHDRs.next()){
							        			if (tienTe.equals(""))
							        				tienTe = "";//soHDRs.getString("tienTe");
							        	%>
							        		<tr >
							        		  <td>
							        		 	<input name="pkseqsohoadon" type="hidden"  readonly="readonly" value="<%= soHDRs.getString("pk_seq") %>"  style="width: 150px">					        		  
							        		  	<input name="sohoadon" type="text"  readonly="readonly" value="<%= soHDRs.getString("sohoadon") %>"  style="width: 150px">
							        		  </td>
							        		  <td>
							        		  	<input  name="ngayhoadon" type="text"  readonly="readonly" value="<%= soHDRs.getString("ngayhoadon") %>"  style="width: 100%">
							        		  </td>
							        		  <td>
							        		  	<input name="tienTeHdId" id="tienTeHdId_<%=soHDRs.getString("pk_seq")%>" type="hidden" value="<%= soHDRs.getString("TIENTE_FK") %>">
							        		  	<input  name="tienTe" id="tienTe_<%=soHDRs.getString("pk_seq")%>" type="text"  readonly="readonly" value="<%= soHDRs.getString("maTienTe") %>"  style="width: 100%">
							        		  </td>
							        		  <td  style="display: none;">
							        		  	<input  name="tongsoluonggoc" type="hidden"  value="<%= DinhDang.dinhdangkho(Double.parseDouble(soHDRs.getString("tongsoluong"))) %>" >
							        		  	<input  name="tongsoluong" type="text"  value="<%= DinhDang.dinhdangkho(Double.parseDouble(soHDRs.getString("tongsoluong"))) %>"  style="width: 100%" onKeyPress = "return keypress(event);"  onkeyup="CheckSoLuong()">
							        		  </td>
							        		  <td align="center" >
							        		  	 <% if( obj.getHoadonIds().contains(soHDRs.getString("pk_seq")) ) { %>
<%-- 							        		  		<input value="<%= soHDRs.getString("pk_seq") %>" type="checkbox" name="hoadonID" checked="checked"  > --%>
							        		  		<input type="radio" name="hoadonID" value="<%=soHDRs.getString("pk_seq")%>" checked="checked" onclick="setTienTe('<%= soHDRs.getString("TIENTE_FK") %>', '<%= soHDRs.getString("maTienTe") %>');"> 
							        		  	<% } else {%>
<%-- 							        		  		<input value="<%= soHDRs.getString("pk_seq") %>" type="checkbox" name="hoadonID" >  --%>
							        		  		<input type="radio" name="hoadonID" value="<%=soHDRs.getString("pk_seq")%>" onclick="setTienTe('<%= soHDRs.getString("TIENTE_FK") %>', '<%= soHDRs.getString("maTienTe") %>');">
							        		  	<% } %> 
							        		  </td>
							        		</tr>
							        	<% } } %>
							      </table>
							       <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:{dropdowncontent.hidediv('subcontentHD');}; changeHoaDonNcc();">Hoàn tất</a>
					                     </div>
							     </DIV>
								
								<script type="text/javascript">
										dropdowncontent.init("soHDLIST", "right-top", 300, "click");
								</script>
								
							</TD>
						</TR>

						<TR>
                          		<TD class="plainlabel" valign="middle" >Loại hình </TD>   
		                       <TD class="plainlabel" colspan="5">
									<select name="loaihinh" style="width: 200px">
										<% if(obj.getLoaihinh().equals("CIF")){ %>
											<option value=""> </option>
											<option value="CIF" selected>CIF</option>
											<option value="FOB">FOB</option>
											<option value="Khác">Khác</option>
											
										<%} else if( obj.getLoaihinh().equals("FOB") ) { %>	
											<option value=""> </option>									
											<option value="CIF" >CIF</option>
											<option value="FOB" selected>FOB</option>	
											<option value="Khác">Khác</option>		
											
										<%} else if( obj.getLoaihinh().equals("Khác") ) { %>	
											<option value=""> </option>									
											<option value="CIF" >CIF</option>
											<option value="FOB">FOB</option>
											<option value="Khác" selected>Khác</option>			
											
										<%} else { %>	
											<option value="" selected> </option>									
											<option value="CIF" >CIF</option>
											<option value="FOB">FOB</option>
											<option value="Khác">Khác</option>			
										<%}%>									
									 </select>
								</TD>          
		                </TR> 

		                <TR>
                          		<TD class="plainlabel" valign="middle" >Diễn giải </TD>   
		                        <TD class="plainlabel" valign="middle" colspan="5">
		                        	<input type="text" name="diengiai" id="diengiai" value="<%= obj.getDiengiai() %>" style="width: 500px" > 
		                        </TD>          
		                </TR> 

                		<TR>
                          		<TD class="plainlabel" valign="middle" >Tiền tệ</TD>   
		                        <TD class="plainlabel" valign="middle" colspan="5">
		                        	<input type="hidden" name="tienTeId" id= "tienTeId" value="<%= obj.getTienteId()%>">
		                        	<input type="text" name="maTienTe" id= "maTienTe" value="<%= obj.getMaTienTe()%>" style="width: 100px;" align="left" readonly="readonly"> 
		                        </TD>          
		                </TR> 
		                
		                <TR>
                          		<TD class="plainlabel" valign="middle" >Tỉ giá</TD>   
		                        <TD class="plainlabel" valign="middle" colspan="5">
		                        	<input type="text" name="tigia" id= "tigia" value="<%= formatter2.format(Double.parseDouble(obj.getTigia())) %>" style="width: 100px" onKeyPress = "return keypress(event);" onchange = "DinhdangNT(); tinhTienHangHoaTheoTiGia();"> 
		                        </TD>          
		                </TR>
		                
	                    <TR>
                         	<TD class="plainlabel" valign="middle" >Tiền hàng (VNĐ)</TD>
                        	<TD class="plainlabel" valign="middle">
		                     	<input type="text" id="tienhang" name="tienhang" value = "<%= obj.getTienhang().equals("")?"":formatter2.format(Double.parseDouble(obj.getTienhang())) %>" style="width: 100px" onKeyUp = "Dinhdang(this);" readonly="readonly"> 
                        	</TD>                        
<!-- 						</TR> -->
	                    
<!-- 	                    <TR style="display: none;"> -->
<!--                          	<TD class="plainlabel" valign="middle" >% Thuế nhập khẩu</TD> -->
<!--                         	<TD class="plainlabel" valign="middle"> -->
<%-- 		                     	<input type="text" id="ptThueNK" name="ptThueNK" value = "<%= obj.getPtThueNK() %>" style="width: 100px" onKeyPress = "return keypress(event);" onChange = "tinhtien();" readonly="readonly"> % --%>
<!--                         	</TD>                         -->
<!-- 						</TR> -->
		                 
<!-- 	                    <TR> -->
                         	<TD class="plainlabel" valign="middle" >Thuế nhập khẩu (VNĐ)</TD>
                        	<TD class="plainlabel" valign="middle">
		                     	<input type="text" id="thueNKTong" name="thueNKTong" value = "<%= obj.getThueNK() %>" style="width: 100px" onKeyPress = "return keypress(event);" onKeyUp = "Dinhdang(this);" >
                        	</TD>                        
<!-- 						</TR> -->

<!-- 		                <TR style="display: none;"> -->
<!--                           		<TD class="plainlabel" valign="middle" >%VAT nhập khẩu</TD>    -->
<!-- 		                        <TD class="plainlabel" valign="middle"  > -->
<%-- 		                        	<input type="text" name="ptVAT" value="<%= obj.getPtVAT() %>" style="width: 100px" onKeyPress = "return keypress(event);" onChange = "tinhtien();"> % --%>
<!-- 		                        </TD>           -->
<!-- 		                </TR>  -->
		                
<!-- 		                <TR> -->
                          		<TD class="plainlabel" valign="middle" >VAT nhập khẩu (VNĐ)</TD>   
		                        <TD class="plainlabel" valign="middle">
		                        	<input type="text" name="VAT" id="VAT" value="<%= formatter.format(Double.parseDouble(obj.getVAT())) %>" style="width: 100px" onKeyPress = "return keypress(event);" onKeyUp = "Dinhdang(this);" readonly="readonly"> 
		                        </TD>          
		                </TR> 
		                
						</TABLE>
						<hr>
					<!-- LIST SẢN PHẨM -->
					<div align="left" style="width: 100%; float: none; clear: none;"
						class="plainlabel">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="0"
							cellspacing="0">
							<TR class="tbheader">
								<TH align="center" width="3%">STT</TH>
								<TH align="center" width="15%">Tên hàng</TH>
								<TH align="center" width="4%">ĐVT</TH>
								<TH align="center" width="7%">Số lô</TH>
								<TH align="center" width="5%">Số lượng</TH>
<!-- 								<TH align="center" width="8%">Đơn giá VND</TH> -->
								<TH align="center" width="7%">Đơn giá NT</TH>
<!-- 								<TH align="center" width="5%">Thuế suất</TH> -->
								<TH align="center" width="10%">Thành tiền Nt</TH>
								<TH align="center" width="10%">Tiền tính thuế NK (VNĐ)</TH>
								<TH align="center" width="8%">Thuế suất NK</TH>
								<TH align="center" width="8%">Thuế NK (VNĐ)</TH>
								<TH align="center" width="8%">% VAT NK</TH>
								<TH align="center" width="8%">VAT NK (VNĐ)</TH>
							</TR>

							<!--  LOAD SẢN PHẨM RA -->

							<%
							List<ErpSanPhamNhapKhau> spList = obj.getSanPhamList();
								if (spList != null) {
									for (int i = 0; i < spList.size(); i++) {
										ErpSanPhamNhapKhau sp = spList.get(i);
							%>
							<tr>
								<td align="center">
									<input type="text" style="width: 100%; text-align: center;" value="<%=i + 1%>" name="stt" readonly="readonly">
								</td>

								<td align="left">
									<input type="text" name="maHangHoa" id="maHangHoa_<%=i %>" value="<%=sp.getId() + " - " + sp.getTen()%>" style="width: 100%; text-align: left;" readonly="readonly"> 
									<input type="hidden" value="<%=sp.getId()%>" name="idHangHoa" id="idHangHoa_<%=i %>">
									<input type="hidden" value="<%=sp.getLoaiHangHoa()%>" name="loaiHangHoa" id="loaiHangHoa_<%=i %>">
									<input type="hidden" value="<%=sp.getHoaDonNCCId()%>" name="hoaDonNccId" id="hoaDonNccId_<%=i %>"> 
								</td>

								<td align="left">
									<input type="text" name="dvt" id="dvt_<%=i %>" value="<%=sp.getDonViTinh()%>" style="width: 100%; text-align: left;" readonly="readonly">
								</td>

								<td align="left">
									<input type="text" name="soLo"  id="soLo_<%=i %>" value="<%=sp.getSoLo()%>" style="width: 100%; text-align: left;" readonly="readonly">								
								</td>
								
								<td align="left">
									<input type="text" name="soLuong"  id="soLuong_<%=i %>" value="<%=DinhDang.dinhdangkho(sp.getSoLuong())%>" style="width: 100%; text-align: left;" readonly="readonly">								
<!-- 								</td> -->
								
<!-- 								<td align="left"> -->
									<input type="hidden" name="donGiaVND" id="donGiaVND_<%=i %>" value="<%=formatter2.format(sp.getDonGiaVND())%>" style="width: 100%; text-align: left;" readonly="readonly">								
								</td>
								
								<td align="left">
									<input type="text" name="donGiaNT" id="donGiaNT_<%=i %>" value="<%=formatter3.format(sp.getDonGiaNT())%>" style="width: 100%; text-align: left;" readonly="readonly">								
<!-- 								</td> -->

<!-- 								<td align="left"> -->
									<input type="hidden" name="thueSuat" id="thueSuat_<%=i %>" value="<%=formatter2.format(sp.getThueSuat())%>" style="width: 100%; text-align: left;" readonly="readonly">								
								</td>

								<td align="left">
									<input type="text" name="thanhTien" id="thanhTien_<%=i %>" value="<%=formatter2.format(sp.getThanhTien())%>" style="width: 100%; text-align: left;" readonly="readonly">								
								</td>

								<td align="left">
									<input type="text" name="tienTinhThueNhapKhau" id="tienTinhThueNhapKhau_<%=i %>" value="<%=formatter2.format(sp.getTienTinhThueNhapKhau())%>" style="width: 100%; text-align: left;">								
								</td>

								<td align="left">
									<input type="text" name="thueSuatNK" id="thueSuatNK_<%=i %>" value="<%=formatter2.format(sp.getThueSuatNK())%>" onchange="tinhThueNhapKhau(<%=i %>);" style="width: 100%; text-align: left;">								
								</td>
								
								<td align="left">
									<input type="text" name="thueNK" id="thueNK_<%=i %>" value="<%=formatter2.format(sp.getThueNK())%>" style="width: 100%; text-align: left;" onchange="tinhThueVATNK_All()">								
								</td>
								<td align="left">
									<input type="text" name="phanTramVATNK" id="phanTramVATNK_<%=i %>" value="<%=formatter2.format(sp.getPhanTramVATNK())%>" onchange="tinhThueVATNK(<%=i %>);" style="width: 100%; text-align: left;">								
								</td>
								<td align="left">
									<input type="text" name="VATNK" id="VATNK_<%=i %>" value="<%=formatter2.format(sp.getVATNK())%>"  onchange="tinhThueVATTuDo();" style="width: 100%; text-align: left;">								
								</td>
						</tr>
							<%
								}
								}
							%>
						</TABLE>
					</div>

						
						</FIELDSET>						
					</TD>
				</TR>
			</TABLE>
		</TD>
	</TR>
	</TABLE>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>

</BODY>
</HTML>
<%
	obj.DbClose();
	session.setAttribute("tnkBean", null);
	}%>
