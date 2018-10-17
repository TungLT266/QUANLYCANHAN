
<%@page import="geso.traphaco.center.beans.donmuahang.IDonvi"%>
<%@page import="geso.traphaco.center.beans.donmuahang.IERP_DonDatHang_SP"%>
<%@page import="geso.traphaco.center.beans.donmuahang.IERP_DonDatHang"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page import="java.util.Iterator"%>
<%@ page import="java.util.List"%>
<%@ page import="java.util.ArrayList"%>
<%@ page import="geso.traphaco.distributor.beans.donhang.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.text.DecimalFormat"%>
<%@ page import="java.text.NumberFormat"%>
<%@ page import="java.text.DateFormat"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.text.SimpleDateFormat"%>
<%
	IERP_DonDatHang hdBean = (IERP_DonDatHang) session.getAttribute("obj");
%>
<%
	List<IERP_DonDatHang_SP> splist = (List<IERP_DonDatHang_SP>) hdBean.getListSanPham();
	List<IERP_DonDatHang_SP> spKmList = (List<IERP_DonDatHang_SP>) hdBean.getSpKmList();
%>
<%
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
%>
<%
	Hashtable<String, Integer> spThieuList = hdBean.getSpThieuList();
%>
<%
	ResultSet rs_nhacc = hdBean.GetRsnhacc();

	ResultSet rs_khott = hdBean.GetRskhott();

	ResultSet rs_nhapp = hdBean.GetRsnhapp();

	ResultSet rs_kenhbanhang = hdBean.GetRsKbh();

	ResultSet rs_dvkd = hdBean.getrsdvkd();

	NumberFormat formatter = new DecimalFormat("#,###,###");
	NumberFormat formatter2 = new DecimalFormat("#,###,###.##");
	List<IDonvi> dvList = (List<IDonvi>) hdBean.getDvList();
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<HTML>
<HEAD>
<TITLE>Best - Project</TITLE>
<META http-equiv="Content-Type" content="text/html; charset=utf-8">
<META http-equiv="Content-Style-Type" content="text/css">
<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
<LINK rel="stylesheet" href="../css/main.css" type="text/css">
<LINK rel="stylesheet" href="../css/calendar.css" type="text/css">
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
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />


<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_erp_bgmuanpp.js"></script>

<script type="text/javascript" src="../scripts/DocTienTiengViet.js"></script>
<script type="text/javascript" src="../scripts/formattien.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
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
<script type="text/javascript" language="JavaScript" src="../scripts/jquery.tools.min.js"></script>
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs.css">
<LINK rel="stylesheet" type="text/css" media="screen" href="../css/tabs-panes.css">
<script>
	$(function() {
	 	$("ul.tabs").tabs("div.panes > div");
	});
	</script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
function Kiemtra(is)
{
	if(is.value==""){};
}
function ShowPop()
{
	var masp = document.getElementsByName("ckDetail.scheme");
	var tensp = document.getElementsByName("ckDetail.sotien");
	var ck = document.getElementById("chietkhau");
	var what=document.getElementById("loaick");
	if(!what.checked) 
    {
    	chietkhau.setAttribute('readonly',1);
		for(var k = 0; k < 6; k++)
		{
			masp.item(k).value = '';
			tensp.item(k).value = '';
		}
        obj1 = document.getElementById('ckDetail');
        obj1.style.display = '';
        ck.value='0';
    } 
    else 
    {
    	chietkhau.removeAttribute("readonly",0);
    	for(var k = 0; k < 6; k++)
		{
			masp.item(k).value = '';
			tensp.item(k).value = '';
		}
    	
        obj1 = document.getElementById('ckDetail');
    	obj1.value="";
        obj1.style.display = 'none';   
        ck.value='0.0';
    }
}
function replaces()
{
	var manpp=document.getElementById("nhappid");
	if(manpp.value.indexOf(" -- ") >0){
		var str = manpp.value;
		manpp.value = str.substring(0, manpp.value.indexOf(" -- "));
		document.getElementById("tennpp").value =str.substring(str.indexOf(" -- ") + 4);
		loadcontent();
	}
	
	var spId = document.getElementsByName("idsp");
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var giachuan = document.getElementsByName("giachuan");
	var dongia = document.getElementsByName("dongia");
	var chietkhau = document.getElementsByName("chietkhau");
	var soluong = document.getElementsByName("soluongduyet");
	var thanhtien = document.getElementsByName("thanhtien");
	var tonkho = document.getElementsByName("tonkho");
	var trongluong = document.getElementsByName("trongluong");
	var thetich = document.getElementsByName("thetich");
	var goivc = document.getElementsByName("goivc");
	var i;
	var sodong=masp.length;
	for(i=0; i < sodong ; i++)
	{
		if(masp.item(i).value != "")
		{
			for(var k = 0;k <sodong ;k++)
			{
				if(parseInt(k)!=parseInt(i))//khong phai ma hien tai
				{
					
				}
			}
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" - "));
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var gia_chuan= sp.substring(0, parseInt(sp.indexOf("] [")));
			    giachuan.item(i).value= formattien(gia_chuan);
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var valuegia= sp.substring(0, parseInt(sp.indexOf("] [")));
				dongia.item(i).value =formattien(valuegia);
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
			    tonkho.item(i).value= sp.substring(0, parseInt(sp.indexOf("] [")));
			    
			   	sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
			   	trongluong.item(i).value= sp.substring(0, parseInt(sp.indexOf("] [")));
			   	
			    sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
			  	thetich.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
			  	
			  	sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				goivc.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				spId.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
			}
			if(parseInt(soluong.item(i).value) > 0)
			{				
				var don_gia = dongia.item(i).value;
				while(don_gia.match(",")){
					don_gia=don_gia.replace(",","");
					}
				var tt = parseFloat(soluong.item(i).value) * parseFloat(don_gia);
				thanhtien.item(i).value = formattien(tt);
			}
			else			
			{
				
				thanhtien.item(i).value = "";
			}
			
			if(checkMasp(masp.item(i).value) == true)
			{
				masp.item(i).parentNode.parentNode.bgColor = "#9FC";
			}
		}
		else
		{
			tensp.item(i).value = "";
			donvitinh.item(i).value = "";
			dongia.item(i).value = "";
		
			soluong.item(i).value = "";
			thanhtien.item(i).value = "";
			tonkho.item(i).value = "";
			
		}
		TinhTien();
	}	
	setTimeout(replaces, 20);
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
		return false;}
		
}	
		
function TinhTien()
{
	var thanhtien = document.getElementsByName("thanhtien");
	var  chietkhau=document.getElementsByName("chietkhau");
	var chietkhaudh=document.getElementsByName("chietkhaudh");
	var  trongluong=document.getElementsByName("trongluong");
	var  thetich=document.getElementsByName("thetich");
	var  soluong=document.getElementsByName("soluongduyet");
	var goivc=document.getElementsByName("goivc");
	var quycach=document.getElementsByName("quycach");
	var donviduyetId=document.getElementsByName("donviduyetId");
	var tongtien = 0;
	var tongtienck=0;
	var tongtrongluong=0;
	var tongthetich=0;
	var tongsoluong=0;
	var tonggoivc=0;
	for(var i=0; i < thanhtien.length; i++)
	{
		if(thanhtien.item(i).value != "")
		{
			
			var so_luong=soluong.item(i).value;
			var quy_cach=quycach.item(i).value;
			while(so_luong.match(","))
			{
				so_luong=so_luong.replace(",","");
				
			}
			if(donviduyetId.item(i).value!='100018')
			{
				so_luong=parseFloat(so_luong)/parseFloat(quy_cach);
			}
			tongsoluong=parseFloat(tongsoluong)+parseFloat(so_luong);
			var thanh_tien = thanhtien.item(i).value.replace(",", "");
			while(thanh_tien.match(","))
			{
				thanh_tien=thanh_tien.replace(",","");
			}
			var chietkhaudh_=chietkhaudh.item(i).value.replace(",", "");
			
			while(chietkhaudh_.match(","))
			{
				chietkhaudh_=chietkhaudh_.replace(",","");
			}
			if(chietkhaudh_=="")
			{
				chietkhaudh_=0;
			}
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien) - parseFloat(thanh_tien) / 100* chietkhaudh_;
			var trong_luong=trongluong.item(i).value;
			while(trong_luong.match(","))
			{
				trong_luong=trong_luong.replace(",","");
			}
			
			var the_tich=thetich.item(i).value;
			while(the_tich.match(","))
			{
				the_tich=the_tich.replace(",","");
			}
			var goi_vc=goivc.item(i).value;
			while(goi_vc.match(","))
			{
				goi_vc=goi_vc.replace(",","");
			}
			tonggoivc=parseFloat(tonggoivc)+parseFloat(goi_vc* so_luong);
			tongtrongluong =parseFloat(tongtrongluong)+parseFloat(trong_luong* so_luong/1000);
			tongthetich =parseFloat(tongthetich)+parseFloat(the_tich* so_luong/1000000);
		}
	}
	
	
	//document.forms['dhForm'].SoTienChuaVAT.value=tongtien;
	document.getElementById("SoTienChuaVat").value=formattien(tongtien);
	document.getElementById("tongtrongluong").value=formattien(tongtrongluong);
	document.getElementById("tongthetich").value=formattien(tongthetich);
	document.getElementById("tongthung").value=formattien(tongsoluong);
	document.getElementById("tonggoivc").value=formattien(tonggoivc);
	
	var chietkhau=document.getElementById("chietkhau").value;	
	tienck= document.getElementById("chietkhau").value;
	if(tienck==""){
		tienck=0;
	}
	var sotienck=tienck;
	sotienck= parseFloat(tongtien)* parseFloat(tienck)/100;
	var sotienCktm=document.getElementById("sotienCktm").value;
	
	while(sotienCktm.match(",")){
		sotienCktm=sotienCktm.replace(",","");
		}
		
	tongtien=	parseFloat(tongtien)-parseFloat(sotienck)-parseFloat(sotienCktm);
	
	var vat = document.getElementById("VAT").value;
	if(vat == "")
		vat = "10";
	var tiencothue=(parseFloat(vat) * tongtien) / 100 + tongtien;
	if(tiencothue<0)
		tiencothue=1000;
	
	document.getElementById("SoTienCoVAT").value =formattien( Math.round(tiencothue));
	document.getElementById("sotienCktm").value =formattien( Math.round(sotienCktm));  	
   		document.getElementById("doctien").value=DocTienBangChu(Math.round(tiencothue));
   		//KiemTraGioiHangCongNo();
}

	function checkMasp(masanpham)
	{
		var masp = document.getElementsByName("masp");
		for(i = 0; i < masp.length ; i++)
		{
			if(masp.item(i).value == masanpham)
				return true;
		}
		return false;
	}
	
	function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	
	function Tinh()
	{
		var masp = document.getElementsByName("ckDetail.scheme");
		var tensp = document.getElementsByName("ckDetail.sotien");
		var chietkhau = document.getElementById("chietkhau");
		chietkhau.removeAttribute("readonly",0);				
	}
	function saveform()
	 {	 
		 congDonSPCungMa();
		 
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		var dongia=document.getElementsByName("dongia");
		var nhapp=document.forms['dhForm'].nhappid.value;
		
		if(nhapp==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn Chi nhánh / Đối tác để xuất hàng ";
			return;
		}
		
		var kenhbanhang=document.forms['dhForm'].kenhbanhang.value;
		if(kenhbanhang==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn kênh bán hàng để xuất hàng ";
			return;
		}
		var ngaygiaodich=document.forms['dhForm'].ngaygiaodich.value;
		if(ngaygiaodich==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn ngày giao dịch để xuất hàng ";
			return;
		}
		var nhaccid =document.forms['dhForm'].nhaccid.value;
		if(nhaccid==""){
			document.forms['dhForm'].dataerror.value="Vui lòng chọn nhà cung cấp  để xuất hàng ";
			return;
		}
		
	/* 	 while(tongtien.match(',')){
			tongtien=tongtien.replace(',',''); 
		}  */
		
		//kiem tra neu gia =0 thi khong the thuc hien luu
		
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value.length != 0)
			{
				if(soluong.item(k).value == "" || soluong.item(k).value  == "0" || tensp.item(k).value == "")			
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return ;
				}
				if(dongia.item(k).value == "" || dongia.item(k).value  == "0" || dongia.item(k).value == ""){
					document.forms['dhForm'].dataerror.value="Đơn giá của mặt hàng "+ tensp.item(k).value + ",không xác định. ";
					return ;	
				}
			} 
		 }
		 document.getElementById("btnSave").innerHTML = "<a href=\"#\"><img src=\"../images/waiting.gif\" title=\"cho luu..\" border=\"1\" style=\"border-style:outset\"> Processing...</a>";
	 	 document.forms['dhForm'].action.value='save';
	     document.forms['dhForm'].submit();
	 }
	
	 function submitform()
	 { 
		congDonSPCungMa();
		document.forms['dhForm'].action.value='submit';
	    document.forms["dhForm"].submit();
	 }
	 function loadcontent()
	 {             				
		document.forms['dhForm'].action.value='reload';
	    document.forms["dhForm"].submit();
	 }
	 
	 function loadcontent1()
	 {             
		document.forms['dhForm'].action.value='reload_npp';
	    document.forms["dhForm"].submit();
	 }
	 function ChotForm()
	 {		 
		 document.forms['dhForm'].ischot.value='1';
		 document.getElementById("btnChot").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='0' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
		 saveform();
	 }
	 function congDonSPCungMa()
	 {
		var masp = document.getElementsByName("masp");
		var soluong = document.getElementsByName("soluong");
		var ii;
		for(ii = 0; ii < (masp.length - 1) ; ii++)
		{
			for( j = ii + 1; j < masp.length; j++)
			{
				if(masp.item(ii).value != "" && masp.item(ii).value == masp.item(j).value)
				{
					//alert(masp.item(ii).value + "-" + masp.item(j).value);				
					if(soluong.item(j).value == "")
						soluong.item(j).value = "0";
					
					soluong.item(ii).value = parseInt(soluong.item(ii).value) + parseInt(soluong.item(j).value);
					masp.item(j).value = "";
				}
			}
		}
	 }
	function addRow(name)
		{
			tableName = document.getElementById(name);
			
			var tr = document.createElement("TR");
			var maspAdd = document.createElement("TD");
			var tenspAdd = document.createElement("TD");
			var tonkhoadd = document.createElement("TD");
			var soluongAdd = document.createElement("TD");
			var dvtinhAdd = document.createElement("TD");
			var dongiaAdd = document.createElement("TD");
			var chietkhauAdd = document.createElement("TD");
			var thanhtienAdd = document.createElement("TD");
			var ctkmAdd = document.createElement("TD");
			
			var idsp = document.createElement("input");
			idsp.setAttribute("type", "hidden");
			idsp.setAttribute("readonly", "readonly");
			//idsp.setAttribute("style","background-color:white;width:100%");
			idsp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");
			idsp.name = 'idsp';
			maspAdd.appendChild(idsp);
			
			var masp = document.createElement("input");
			masp.setAttribute("type", "textbox");
			masp.setAttribute("autocomplete","off");
			masp.setAttribute("onkeyup", "ajax_showOptions(this,'abc',event)");
			//masp.setAttribute("style","background-color:white;width:100%");
			masp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

			masp.name = 'masp';
			maspAdd.appendChild(masp);
			
			var tensp = document.createElement("input");
			tensp.setAttribute("type", "textbox");
			tensp.setAttribute("readonly", "readonly");
			//tensp.setAttribute("style","background-color:white;width:100%");
			tensp.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

			tensp.name = 'tensp';
			tenspAdd.appendChild(tensp);
			
			
			var tonkho = document.createElement("input");
			tonkho.setAttribute("type", "textbox");
			tonkho.setAttribute("readonly", "readonly");
			//dvt.setAttribute("style","background-color:white;width:100%");
			tonkho.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

			tonkho.value = "";
			
			tonkho.name = 'tonkho';
			tonkhoadd.appendChild(tonkho);
			
			var soluong = document.createElement("input");
			soluong.setAttribute("type", "textbox");
			soluong.setAttribute("onkeypress","return keypress(event)");
			soluong.value = "";
			//soluong.setAttribute("style","background-color:white;width:100%");	
			soluong.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

			soluong.name = "soluong";
			soluongAdd.appendChild(soluong);
			
			var dvt = document.createElement("input");
			dvt.setAttribute("type", "textbox");
			dvt.setAttribute("readonly", "readonly");
			//dvt.setAttribute("style","background-color:white;width:100%");
			dvt.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;");

			dvt.value = "";
			
			dvt.name = 'donvitinh';
			dvtinhAdd.appendChild(dvt);
			
			var dongia = document.createElement("input");
			dongia.setAttribute("type", "textbox");
			dongia.setAttribute("readonly", "readonly");
			dongia.value = "";
			//dongia.setAttribute("style","background-color:white;width:100%");
			dongia.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;text-align:right");

			dongia.name = 'dongia';
			dongiaAdd.appendChild(dongia);
			
		
			
			var thanhtien = document.createElement("input");
			thanhtien.setAttribute("type", "textbox");
			thanhtien.setAttribute("readonly", "readonly");
			thanhtien.value = "";
			//thanhtien.setAttribute("style","background-color:white;width:100%");
			thanhtien.setAttribute("style","width:100%;border:1px;	border-style:solid;border-color: #808080;text-align:right");
			thanhtien.name = "thanhtien";
			thanhtienAdd.appendChild(thanhtien);
			
			
			
			
			tr.appendChild(maspAdd);
			tr.appendChild(tenspAdd);
			tr.appendChild(tonkhoadd);
			tr.appendChild(soluongAdd);
			tr.appendChild(dvtinhAdd);
			tr.appendChild(dongiaAdd);
			tr.appendChild(thanhtienAdd);
			tr.appendChild(thanhtienAdd);
			
			tableName.appendChild(tr);
		}
		function ThemSanPham()
		{
			 var sl = window.prompt("Nhấp số lượng sản phẩm muốn thêm", '');
			 if(isNaN(sl) == false && sl < 30)
			 {
				 for(var i=0; i < sl ; i++)
					 addRow("san_pham");
			 }
			 else
			 {
				
				 alert('Số lượng bạn nhập không hợp lệ. Mọi lần bạn chỉ được thêm tối đa 30 sản phẩm');
				 return;
			 }
		 }
		
		function getinfoddh(){
			document.forms['dhForm'].action.value='loadddh';
			 document.forms['dhForm'].submit();
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
		function Dinhdang(element)
		{
			element.value=DinhDangTien(element.value);
			if(element.value== '' )
			{
				element.value= '';
			}
			setTTienbVAT();
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
			num = Math.floor(num*100+0.50000000001);
			num = Math.floor(num/100).toString();
			for (var i = 0; i < Math.floor((num.length-(1+i))/3); i++)
			num = num.substring(0,num.length-(4*i+3))+','+
			num.substring(num.length-(4*i+3));

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
		
		function roundNumber(num, dec) 
		{
			var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
			return result;
		}
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="Tinh()">
	<form name="dhForm" method="post" action="../../ERP_DonDatHangUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> <input type="hidden" name="userTen" value='<%=userTen%>'> <input type="hidden" name="action" value='1'> <INPUT type="hidden" name="ischot" value=''> <input
			type="hidden" name='tenform' value="Updateform"> <input type="hidden" name='id' value='<%=hdBean.getId()%>'>

		<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
			<TR>
				<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
					<TABLE border=0 width="100%" cellpadding="2" cellspacing="0">
						<TBODY>
							<TR height="22">
								<TD align="left">
									<TABLE width="100%" cellpadding="0" cellspacing="0">
										<TR>
											<TD align="left">
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR height="22">
														<TD align="left" class="tbnavigation">&nbsp;Quản lý bán hàng > Tạo đơn hàng > Hiển thị</TD>
														<TD align="right" class="tbnavigation">Chào mừng bạn&nbsp;&nbsp; <%=userTen%> &nbsp;
														</TD>
													</TR>
												</TABLE>
											</TD>
										</TR>
									</TABLE>
									<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
										<TR>
											<TD>
												<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
													<TR class="tbdarkrow">
														<TD width="30" align="left"><A href="../../DonmuahangSvl?userId=<%=userId%>"><img src="../images/Back30.png" alt="Quay về" title="Quay về" border="1" longdesc="Quay ve" style="border-style: outset"></A></TD>
														<TD width="2" align="left">&nbsp;</TD>
														<TD width="30" align="left"><A href="../../Erp_InDonMuaHangSvl?userId=<%=userId%>&print=<%=hdBean.getId()%> "><img src="../images/Printer30.png" alt="In" title="In chung tu" width="30" height="30" longdesc="In chung tu"
																border=1 style="border-style: outset"></A></TD>
														<TD align="left">&nbsp;</TD>
													</TR>
												</TABLE>
											</TD>
										</TR>
									</TABLE>
									<TABLE border="1" width="100%" cellpadding="1" cellspacing="0" style="border-color: gray;">
										<tr>

											<TD align="left" class="legendtitle">
												<fieldset>
													<legend> Thông báo</legend>
													<textarea name="dataerror" style="width: 100%; margin: 0px" readonly="readonly" rows="1"><%=hdBean.getMessage()%></textarea>
												</fieldset>
											</TD>
										</tr>

										<TR class="plainlabel">
											<TD align="left">

												<TABLE border="0" bordercolor="white" width="100%" cellpadding="3" cellspacing="0" style="padding-left: 2px;">
													<tr class="plainlabel">
														<th width='15%'></th>
														<th width='35%'></th>
														<th width='15%'></th>
														<th width='35%'></th>
													</tr>
													<tr class="plainlabel">
														<td>Nhà cung cấp</td>
														<td><select name='nhaccid' style="width: 100%">
																<%
																	if (rs_nhacc != null)
																	{
																		try
																		{
																			while (rs_nhacc.next())
																			{
																				if (rs_nhacc.getString("pk_seq").trim().equals(hdBean.getIdNhaCungCap()))
																				{
																%>
																<option value='<%=rs_nhacc.getString("pk_seq")%>' selected><%=rs_nhacc.getString("ten")%></option>
																<%
																	} else
																				{
																%>
																<option value='<%=rs_nhacc.getString("pk_seq")%>'><%=rs_nhacc.getString("ten")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e)
																		{
																		}
																	}
																%>
														</select></td>
														<td>Chọn ĐVKD</td>
														<td><select name='dvkdid' style="width: 100%" onchange="loadcontent();">
																<%
																	if (rs_dvkd != null)
																	{
																		try
																		{
																			while (rs_dvkd.next())
																			{
																				if (rs_dvkd.getString("pk_seq").trim().equals(hdBean.getdvkdid()))
																				{
																%>
																<option value='<%=rs_dvkd.getString("pk_seq")%>' selected><%=rs_dvkd.getString("ten")%></option>
																<%
																	} else
																				{
																%>
																<option value='<%=rs_dvkd.getString("pk_seq")%>'><%=rs_dvkd.getString("ten")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e)
																		{
																		}
																	}
																%>
														</select></td>
													</tr>
													<TR class="plainlabel">
														<TD class="plainlabel">Ngày giao dịch</TD>
														<TD class="plainlabel"><input type="text" size="10" class="days" id="ngaygiaodich" name="ngaygiaodich" value="<%=hdBean.getNgaygiaodich()%>" maxlength="10" onchange="loadcontent();" /></TD>

														<!-- chen vao kenh ban hang -->
														<td>Kênh bán hàng</td>
														<td><select name='kenhbanhang' style="width: 100%" onchange="loadcontent();">
																<%
																	if (rs_kenhbanhang != null)
																	{
																		try
																		{
																			while (rs_kenhbanhang.next())
																			{
																				if (rs_kenhbanhang.getString("pk_seq").trim().equals(hdBean.getIDKenhBanHang()))
																				{
																%>
																<option value='<%=rs_kenhbanhang.getString("pk_seq")%>' selected><%=rs_kenhbanhang.getString("ten")%></option>
																<%
																	} else
																				{
																%>
																<option value='<%=rs_kenhbanhang.getString("pk_seq")%>'><%=rs_kenhbanhang.getString("ten")%></option>
																<%
																	}
																			}
																		} catch (java.sql.SQLException e)
																		{
																		}
																	}
																%>
														</select></td>

													</TR>
													<TR class="plainlabel">
														<TD>Chi nhánh / Đối tác</TD>
														<td><input type="text" id="nhappid" name="nhappid" value="<%=hdBean.getNppId()%>"></td>
														<td>Tên Chi nhánh / Đối tác</td>
														<td><input type="text" id="tennpp" name="tennpp" style="width: 100%" value="<%=hdBean.getNppTen()%>"></td>
													</TR>


													<TR class="plainlabel">
														<TD>Tổng số tiền(Chưa VAT)</TD>
														<TD><input name="SoTienChuaVat" id="SoTienChuaVat" type="text" readonly="readonly" value="<%=formatter.format(hdBean.getTongtientruocVAT())%>"> VND</TD>
														<TD class="plainlabel">VAT (%)</TD>
														<TD class="plainlabel"><input name="VAT" id="VAT" type="text" value="<%=Math.round(hdBean.getVAT())%>" onkeypress="return keypress(event);">%</TD>

													</TR>
													<TR class="plainlabel">
														<TD class="plainlabel">Chiết khấu thương mại</TD>
														<TD class="plainlabel">
															<%
																if (hdBean.getIDKenhBanHang().equals("100025"))
																{
															%> <input name="sotienCktm" id="sotienCktm" onkeyup="Dinhdang(this)" type="text" onkeypress="return keypress(event);" value="<%=formatter.format(hdBean.getChietKhauThuongMai())%>" readonly="readonly"> <%
 	} else
 	{
 %> <input name="sotienCktm" id="sotienCktm" onkeyup="Dinhdang(this)" type="text" onkeypress="return keypress(event);" value="<%=formatter.format(hdBean.getChietKhauThuongMai())%>"> <%
 	}
 %> VND
														</TD>
														<TD class="plainlabel">Chiết khấu</TD>
														<TD class="plainlabel"><input name="chietkhau" id="chietkhau" type="text" value="<%=formatter2.format(hdBean.getChietkhau())%>" readonly="readonly" onkeypress="return keypress(event);"></TD>
													</TR>
													<tr>
														<TD class="plainlabel">Tổng tiền(sau VAT)</TD>
														<TD class="plainlabel"><input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly="readonly" value="<%=formatter.format(hdBean.getTongtiensauVAT())%>"> VND</TD>

														<TD class="plainlabel"></TD>
														<TD class="plainlabel">
															<%
																if (hdBean.getTuVanChuyen().equals("1"))
																{
															%> <input name="tuvanchuyen" onchange="loadcontent();" id="tuvanchuyen" checked="checked" type="checkbox" readonly="readonly" value="1"> <%
 	} else
 	{
 %> <input name="tuvanchuyen" onchange="loadcontent();" id="tuvanchuyen" type="checkbox" readonly="readonly" value="1"> <%
 	}
 %> Chi nhánh / Đối tác tự vận chuyển
														</TD>
													</tr>
													<TR class="plainlabel">
														<TD>Ghi chú</TD>
														<TD><textarea rows="2" name="ghichu" id="ghichu" style="width: 100%; font-size: 11pt"> <%=hdBean.getGhichu()%> </textarea></TD>

														<TD class="plainlabel">Ngày đề nghị giao hàng</TD>
														<TD class="plainlabel"><input name="ngaydenghigh" style="width: 100%" id="ngaydenghigh" type="text" value="<%=hdBean.getNgaydenghigh()%>"></TD>
													</TR>

													<TR class="plainlabel">
														<TD>Tổng thùng</TD>
														<TD><input name="tongthung" id="tongthung" style="width: 100px" type="text" value="<%=formatter.format(hdBean.GetTongThung())%>">  
														
													</TD>
														<TD class="plainlabel">Tổng trọng lượng</TD>
														<TD class="plainlabel"><input name="tongtrongluong" id="tongtrongluong" style="width: 120px" type="text" value="<%=formatter.format(hdBean.GetTrongLuong())%>">(Kg) &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Thể Tích <input
															name="tongthetich" id="tongthetich" style="width: 120px" type="text" value="<%=formatter2.format(hdBean.GetTheTich())%> ">(m3)</TD>
													</TR>
										<TR class="plainlabel" >
											  <TD  >Tổng gói vận chuyển</TD>
											  <TD>
											  	 	 <input name="tonggoivc" id="tonggoivc" style="width: 100px" type="text" value="<%=  formatter.format(hdBean.getTongGoiVC()) %>" >
											  	  </TD>
											  	   <TD  >Đơn giá gói vận chuyển</TD>
											  <TD>
											  	 	 <input name="GiaVanChuyen" id="GiaVanChuyen" style="width: 100px" type="text" value="<%=  formatter2.format(hdBean.getGiaVanChuyen()) %>" >
											  	  </TD>
											  	  </TR>
													<tr class="plainlabel">
														<td>Hình thức vận chuyển <%
															String ht[] =
															{ "CTVC", "KHVC", "TRVC" };
														%>
														</td>
														<td colspan="3"><select name="hinhthucvanchuyen">
																<option value=""></option>
																<%
																	for (int i = 0; i < 3; i++)
																	{
																		if (ht[i].equals(hdBean.getHinhthucvanchuyen()))
																		{
																%>
																<option value="<%=ht[i]%>" selected="selected"><%=ht[i]%></option>
																<%
																	} else
																		{
																%>
																<option value="<%=ht[i]%>"><%=ht[i]%></option>
																<%
																	}
																	}
																%>
														</select></td>
													</tr>


													<tr class="plainlabel">
														<td>Thông tin DH NPP</td>
														<td colspan="3"><textarea rows="1" style="width: 100%; font-size: 11pt" readonly="readonly" id="thongtin"> <%=hdBean.getThongTin()%> </textarea></td>
													</tr>


													<tr class="plainlabel">
														<td>Số tiền bằng chữ</td>
														<td colspan="3"><textarea rows="1" style="width: 100%; font-size: 11pt" readonly="readonly" id="doctien">  </textarea></td>
													</tr>
												</TABLE>


												<ul class="tabs">
													<li><a href="#">Thùng</a></li>
													<%if(spKmList.size()>0){ %>
													<li><a href="#">Lẻ</a></li>
													<%} %>
												</ul>


											</TD>

										</TR>

										<TR>
											<TD>
												<div class="panes">
													<div>
														<TABLE class="tabledetail" width="100%" border="0" cellpadding="0" cellspacing="1">
															<tbody id="san_pham">
																<TR class="tbheader">
																	<TH width="15%" height="20">Mã sản phẩm</TH>
																	<TH width="25%">Tên sản phẩm</TH>
																	<TH style="display: none" width="8%">Tồn kho</TH>
																	<TH width="8%" style="display: none">Scheme</TH>
																	<TH width="5%">Số lượng</TH>
																	<TH width="5%">ĐVT</TH>
																	<TH width="8%">Số lượng duyệt</TH>
																	<TH width="8%">ĐVT duyệt</TH>
																	<TH width="8%">Gía chuẩn</TH>
																	<TH width="8%">Đơn giá</TH>
																	<TH width="12%">Thành tiền</TH>
																	<TH width="8%">Chiết khấu</TH>
																</TR>
																<%
																	if (splist != null)
																	{
																		IERP_DonDatHang_SP sanpham;
																		int size = splist.size();
																		int m = 0;
																		while (m < size)
																		{
																			sanpham = splist.get(m);
																%>
																<TR class='tblightrow'>
																	<TD align="left"><input name='idsp' type='hidden' value=<%=sanpham.getIdSanPham()%>> <input name="masp" type="text" value="<%=sanpham.getMaSanPham()%>" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)"
																		style="width: 100%"></TD>
																	<TD align="left"><input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width: 100%"></TD>
																	<TD style="display: none" align="left"><input name="tonkho" type="text" readonly value="<%=sanpham.getsoluongton()%>" style="width: 100%"></TD>

																	<TD align="left" style="display: none"><input name="scheme" type="text" readonly value="<%=sanpham.getSHEME()%>" style="width: 100%"></TD>


																	<%
																		if (spThieuList.containsKey(sanpham.getMaSanPham()))
																				{
																	%>
																	<TD align="left" class="addspeech"><input name="soluong" type="text" value="<%=sanpham.getSoLuong()%>" onkeypress="return keypress(event);" style="cursor: pointer; background-color: 99CCCC; text-align: left; width: 100%"
																		title="San pham nay con toi da <%=spThieuList.get(sanpham.getMaSanPham())%> san pham, vui long chon lai so luong"></TD>
																	<%
																		} else
																				{
																	%>
																	<TD align="left"><input name="soluong" type="text" value="<%=sanpham.getSoLuong()%>" onkeypress="return keypress(event);" style="text-align: left; width: 100%;" readonly="readonly"></TD>
																	<%
																		}
																	%>
																	<TD align="center"><input name="donvitinh" type="text" value="<%=sanpham.getDonViTinh()%>" readonly style="width: 100%"></TD>
																	<TD align="left"><input name="soluongduyet" type="text" value="<%=sanpham.getsoluongduyet()%>" onkeypress="return keypress(event);" style="text-align: left; width: 100%;"></TD>
																	<TD><select style="width: 100%" name="donviduyetId" onchange="loadcontent();" disabled="disabled">
																			<%
																				if (dvList.size() > 0)
																						{
																							for (int j = 0; j < dvList.size(); j++)
																							{
																								IDonvi tt = (IDonvi) dvList.get(j);
																								if (tt.getId().equals(sanpham.getDonviduyetId()))
																								{
																			%>
																			<option value='<%=tt.getId()%>' selected="selected"><%=tt.getDonvi()%></option>
																			<%
																				} else
																								{
																			%>
																			<option value='<%=tt.getId()%>'><%=tt.getDonvi()%></option>
																			<%
																				}
																							}
																						}
																			%>
																	</select></td>
																	<%
																		double giachuan = sanpham.getGiachuan();
																				double dongia = sanpham.getDonGia();
																				double quycach = sanpham.getQuyCach();
																				if (sanpham.getDonviduyetId().equals("100018"))
																				{
																					giachuan = quycach * giachuan;
																					dongia = quycach * dongia;
																				}
																	%>

																	<TD align="center"><input type="text" name="giachuan" value="<%=formatter.format(giachuan)%>" readonly style="width: 100%; text-align: right"></TD>
																	<TD align="center"><input type="text" name="dongia" value="<%=formatter.format(dongia)%>" readonly style="width: 100%; text-align: right"></TD>
																	<TD align="center"><input name="thanhtien" type="text" value="<%=formatter.format(sanpham.getThanhTien())%>" readonly style="width: 100%; text-align: right"> <input name="quycach" type="hidden"
																		value="<%=sanpham.getQuyCach()%>" readonly style="width: 100%; text-align: right"> <input name="trongluong" type="hidden" value="<%=sanpham.getTrongluong()%>" readonly style="width: 100%; text-align: right"> <input
																		name="thetich" type="hidden" value="<%=sanpham.getThetich()%>" readonly style="width: 100%; text-align: right"> <input name="goivc" type="hidden" value="<%=sanpham.getGoiVc()%>" readonly
																		style="width: 100%; text-align: right"></TD>
																	<TD align="center"><input name="chietkhaudh" type="text" value="<%=formatter.format(sanpham.getChietKhau())%>" onkeypress="return keypress(event);" style="width: 100%; text-align: right"></TD>
																</TR>
																<%
																	m++;
																		}
																	}
																%>

															</tbody>
														</TABLE>
													</div>
												</div>
												<%if(spKmList.size()>0){ %>
												<div class="panes">
													<div>
														<TABLE class="tabledetail" width="100%" border="0" cellpadding="0" cellspacing="1">
															<tbody id="san_pham">
																<TR class="tbheader">
																	<TH width="15%" height="20">Mã sản phẩm</TH>
																	<TH width="28%">Tên sản phẩm</TH>
																	<TH style="display: none" width="8%">Tồn kho</TH>
																	<TH width="8%" style="display: none">Scheme</TH>
																	<TH width="8%">Số lượng</TH>
																	<TH width="5%">ĐVT</TH>
																	<TH width="10%">Gía chuẩn</TH>
																	<TH width="10%">Đơn giá</TH>
																	<TH width="9%">Thành tiền</TH>
																	<TH width="9%">Chiết khấu</TH>
																</TR>
																<%
																	if (spKmList != null)
																	{
																		IERP_DonDatHang_SP sanpham;
																		int size = spKmList.size();
																		int m = 0;
																		while (m < size)
																		{
																			sanpham = spKmList.get(m);
																%>
																<TR class='tblightrow'>
																	<TD align="left"><input name='idsp' type='hidden' value=<%=sanpham.getIdSanPham()%>> <input name="masp" type="text" value="<%=sanpham.getMaSanPham()%>" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)"
																		style="width: 100%"></TD>
																	<TD align="left"><input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width: 100%"></TD>
																	<TD style="display: none" align="left"><input name="tonkho" type="text" readonly value="<%=sanpham.getsoluongton()%>" style="width: 100%"></TD>

																	<TD align="left" style="display: none"><input name="scheme" type="text" readonly value="<%=sanpham.getSHEME()%>" style="width: 100%"></TD>


																	<%
																		if (spThieuList.containsKey(sanpham.getMaSanPham()))
																				{
																	%>
																	<TD align="left" class="addspeech"><input name="soluong" type="text" value="<%=sanpham.getSoLuong()%>" onkeypress="return keypress(event);" style="cursor: pointer; background-color: 99CCCC; text-align: left; width: 100%"
																		title="San pham nay con toi da <%=spThieuList.get(sanpham.getMaSanPham())%> san pham, vui long chon lai so luong"></TD>
																	<%
																		} else
																				{
																	%>
																	<TD align="left"><input name="soluong" type="text" value="<%=sanpham.getSoLuong()%>" onkeypress="return keypress(event);" style="text-align: left; width: 100%;" readonly="readonly"></TD>
																	<%
																		}
																	%>
																	<TD align="center"><input name="donvitinh" type="text" value="<%=sanpham.getDonViTinh()%>" readonly style="width: 100%"></TD>																																		
																	<%
																		double giachuan = sanpham.getGiachuan();
																				double dongia = sanpham.getDonGia();
																				double quycach = sanpham.getQuyCach();
																				if (sanpham.getDonviduyetId().equals("100018"))
																				{
																					giachuan = quycach * giachuan;
																					dongia = quycach * dongia;
																				}
																	%>

																	<TD align="center"><input type="text" name="giachuan" value="<%=formatter.format(giachuan)%>" readonly style="width: 100%; text-align: right"></TD>
																	<TD align="center"><input type="text" name="dongia" value="<%=formatter.format(dongia)%>" readonly style="width: 100%; text-align: right"></TD>
																	<TD align="center"><input name="thanhtien" type="text" value="<%=formatter.format(sanpham.getThanhTien())%>" readonly style="width: 100%; text-align: right"> <input name="quycach" type="hidden"
																		value="<%=sanpham.getQuyCach()%>" readonly style="width: 100%; text-align: right"> <input name="trongluong" type="hidden" value="<%=sanpham.getTrongluong()%>" readonly style="width: 100%; text-align: right"> <input
																		name="thetich" type="hidden" value="<%=sanpham.getThetich()%>" readonly style="width: 100%; text-align: right"> <input name="goivc" type="hidden" value="<%=sanpham.getGoiVc()%>" readonly
																		style="width: 100%; text-align: right"></TD>
																	<TD align="center"><input name="chietkhaudh" type="text" value="<%=formatter.format(sanpham.getChietKhau())%>" onkeypress="return keypress(event);" style="width: 100%; text-align: right"></TD>
																</TR>
																<%
																	m++;
																		}
																	}
																%>

															</tbody>
														</TABLE>
													</div>
												</div>
												<%} %>
											</TD>

										
										</TR>
									</TABLE>
								</TD>
							</TR>

						</TBODY>
					</TABLE>
				</td>
			</tr>

		</TABLE>
		<!-- Pop up  -->

		<!--  -->

		<script type="text/javascript">
//replaces();
</script>
	</form>
	<script type="text/javascript" src="../scripts/loadingv2.js"></script>

</BODY>
</HTML>
<%
	try
	{
		if (rs_nhacc != null)
		{
			rs_nhacc.close();
		}
		if (rs_khott != null)
		{
			rs_khott.close();
		}
		if (rs_nhapp != null)
		{
			rs_nhapp.close();
		}
		if (rs_kenhbanhang != null)
		{
			rs_kenhbanhang.close();
		}
		if (rs_dvkd != null)
		{
			rs_dvkd.close();
		}
		hdBean.DBClose();

	} catch (Exception er)
	{

	}
%>

