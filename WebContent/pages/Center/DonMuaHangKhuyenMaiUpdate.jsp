<%@page import="geso.traphaco.center.beans.donmuahang.IDonvi"%>
<%@page import="geso.traphaco.center.beans.donmuahang.IERP_DonDatHang_SP"%>
<%@page import="geso.traphaco.center.beans.donmuahang.IERP_DonDatHang"%>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<% IERP_DonDatHang hdBean = (IERP_DonDatHang)session.getAttribute("obj"); %>
<% List<IERP_DonDatHang_SP> splist = (List<IERP_DonDatHang_SP>)hdBean.getListSanPham(); %>
<% String userId = (String)session.getAttribute("userId");
String userTen=(String)session.getAttribute("userTen");
%>
<% Hashtable<String, Integer> spThieuList = hdBean.getSpThieuList(); %>
<%	ResultSet rs_nhacc=hdBean.GetRsnhacc();
	
	ResultSet rs_khott=hdBean.GetRskhott();
	
	ResultSet rs_nhapp=hdBean.GetRsnhapp();
	
	ResultSet rs_kenhbanhang=hdBean.GetRsKbh();
	
	ResultSet rs_dvkd=hdBean.getrsdvkd();
	Hashtable<String, String> ctkmList=hdBean.getCtkmList();
	NumberFormat formatter=new DecimalFormat("#,###,###"); 
	NumberFormat formatter2=new DecimalFormat("#,###,###");
	List<IDonvi> dvList = (List<IDonvi>)hdBean.getDvList();

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
	#mainContainer{
		width:660px;
		margin:0 auto;
		text-align:left;
		height:100%;
		border-left:3px double #000;
		border-right:3px double #000;
	}
	#formContent{
		padding:5px;
	}
	/* END CSS ONLY NEEDED IN DEMO */
		
	/* Big box with list of options */
	#ajax_listOfOptions{
		position:absolute;	/* Never change this one */
		width:auto;	/* Width of box */
		height:200px;	/* Height of box */
		overflow:auto;	/* Scrolling features */
		border:1px solid #317082;	/* Dark green border */
		background-color:#C5E8CD;	/* White background color */
    	color: black;
		text-align:left;
		font-size:1.0em;
		z-index:100;
	}
	#ajax_listOfOptions div{	/* General rule for both .optionDiv and .optionDivSelected */
		margin:1px;		
		padding:1px;
		cursor:pointer;
		font-size:1.0em;
	}
	#ajax_listOfOptions .optionDiv{	/* Div for each item in list */
		
	}
	#ajax_listOfOptions .optionDivSelected{ /* Selected item in the list */
		background-color:#317082; /*mau khi di chuyen */
		color:#FFF;
	}
	#ajax_listOfOptions_iframe{
		background-color:#F00;
		position:absolute;
		z-index:5;
	}
	form{
		display:inline;
	}
	#dhtmltooltip
	{
		position: absolute;
		left: -300px;
		width: 150px;
		border: 1px solid black;
		padding: 2px;
		background-color: lightyellow;
		visibility: hidden;
		z-index: 100;
		/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
		filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
	}	
	#dhtmlpointer
	{
		position:absolute;
		left: -300px;
		z-index: 101;
		visibility: hidden;
	}
	
</style>
<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />


<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/ajax_donhangkm.js"></script>

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
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>		
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
		if(manpp.value.indexOf(" -- ") >0)
		{
			var str = manpp.value;
			manpp.value = str.substring(0, manpp.value.indexOf(" -- "));
			GetKenhBanHang(manpp.value);
			GetGiaVanChuyen();
			document.getElementById("tennpp").value =str.substring(str.indexOf(" -- ") + 4);
		}
		
		var GiaVanChuyen=  document.getElementById("GiaVanChuyen").value ;
		
		if(GiaVanChuyen==''){
			GiaVanChuyen=0;
		}
		
 
		var spId = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var donvitinh = document.getElementsByName("donvitinh");
		
		var dongia = document.getElementsByName("dongia");
		var giachuan = document.getElementsByName("giachuan");
		
		var soluong = document.getElementsByName("soluong");
		var soluongle = document.getElementsByName("soluongle");
		var thanhtien = document.getElementsByName("thanhtien");
		var quycach=document.getElementsByName("quycach");
		var trongluong = document.getElementsByName("trongluong");
		var thetich = document.getElementsByName("thetich");
		var goivc = document.getElementsByName("goivc");
		var ctkmId=document.getElementsByName("ctkmId");
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
						if((masp[i].value == masp[k].value) && masp[k].value.length !=0  &&ctkmId[i].value==ctkmId[k].value  )
						{
							alert("Sản phẩm hiện tại đã có!");
							masp.item(k).value='';
						}
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
				    quycach.item(i).value= sp.substring(0, parseInt(sp.indexOf("] [")));
				    
				   	sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				   	trongluong.item(i).value= sp.substring(0, parseInt(sp.indexOf("] [")));
				   	
				    sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				  	thetich.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
				  	
				  	sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					goivc.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
					valuegia= parseFloat(valuegia)-  parseFloat(goivc.item(i).value) * parseFloat(GiaVanChuyen);
					dongia.item(i).value =formattien(valuegia);
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					spId.item(i).value= sp.substring(0, parseInt(sp.indexOf("]")));
				}
				if(parseFloat(soluong.item(i).value) > 0||parseFloat(soluongle.item(i).value)>0)
				{				
					var don_gia = dongia.item(i).value;
					var quy_cach=quycach.item(i).value.replace(",","");
					var so_luong_le=soluongle.item(i).value.replace(",","");
					var so_luong = soluong.item(i).value.replace(",","");
					
					if(so_luong=="")
						so_luong="0";
					if(so_luong_le=="")
						so_luong_le="0";
					
					//alert("--+"+so_luong +'--[]--'+quy_cach+'--  --'+so_luong_le);
					
					so_luong = parseFloat(so_luong)+parseFloat(so_luong_le)/parseFloat(quy_cach);
					while(don_gia.match(","))
					{
						don_gia=don_gia.replace(",","");
					}
					var tt = parseFloat(so_luong) * parseFloat(don_gia);
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
	var  trongluong=document.getElementsByName("trongluong");
	var  thetich=document.getElementsByName("thetich");
	var  soluong=document.getElementsByName("soluong");
	var  soluongle=document.getElementsByName("soluongle");
	var goivc=document.getElementsByName("goivc");
	var quycach=document.getElementsByName("quycach");
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
			var so_luong=soluong.item(i).value.replace(",","");
			var so_luong_le=soluongle.item(i).value.replace(",","");
			
			if(so_luong=="")
				so_luong="0";
			
			if(so_luong_le=="")
				so_luong_le="0";
			
			var quy_cach=quycach.item(i).value.replace(",","");
			tongsoluong=parseFloat(tongsoluong)+parseFloat(so_luong)+parseFloat(so_luong_le)/parseFloat(quy_cach);
			var thanh_tien = thanhtien.item(i).value.replace(",", "");
			while(thanh_tien.match(","))
			{
				thanh_tien=thanh_tien.replace(",","");
			}
			tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
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
	document.getElementById("SoTienChuaVat").value=formattien(tongtien);
	document.getElementById("tongtrongluong").value=formattien(tongtrongluong);
	document.getElementById("tongthetich").value=formattien(tongthetich);
	document.getElementById("tongthung").value=formattien(tongsoluong);
	document.getElementById("tonggoivc").value=DinhDangTien(tonggoivc);
	
	var vat = document.getElementById("VAT").value;
	if(vat == "")
		vat = "10";
	
	var tiencothue=(parseFloat(vat) * tongtien) / 100 + tongtien;
	
	
	document.getElementById("SoTienCoVAT").value =formattien( Math.round(tiencothue));
   	document.getElementById("doctien").value=DocTienBangChu(Math.round(tiencothue));
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
						
	}
	function saveform()
	 {	 
		 
		 
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 var soluongle = document.getElementsByName("soluongle");
		 var ctkmId = document.getElementsByName("ctkmId");
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
		
		
		 for(var k = 0; k < masp.length; k++)
		 {
			if(masp.item(k).value.length != 0)
			{
				if(  ( soluongle.item(k).value == "0" && soluong.item(k).value  == "0" )||(  soluongle.item(k).value == "" && soluong.item(k).value  == "" ) || tensp.item(k).value == "")			
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return ;
				}
				if(ctkmId.item(k).value=="")			
				{
					alert("Vui lòng chọn Scheme khuyến mại");
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
		 
		 var masp = document.getElementsByName("masp");
		 var tensp = document.getElementsByName("tensp");
		 var soluong = document.getElementsByName("soluong");
		 for(var k = 0; k < masp.length; k++)
		 {
			 if(masp.item(k).value != "")
				if(soluong.item(k).value == "" || tensp.item(k).value == "")
				{
					alert("Kiểm tra lại tên và số lượng sản phẩm, Phải nhập tên và số lượng cho sản phẩm được chọn");
					return;
				}
		 }
		 document.forms['dhForm'].action.value='chotdonhang';
		 document.forms['dhForm'].submit();
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
	
	 function addRow(pos)
		{
			var table = $('#tbSanPham');
			table.append(
	                '<tr>'+
					'<TD align="center" >'+
						'<input name="masp" type="text" value="" onkeyup="ajax_showOptions(this,"abc",event)" style="width:100%" AUTOCOMPLETE="off">'+
					'</TD>'+
					'<TD align="left" >'+
						'<input name="tensp1" type="text" disabled="disabled" value="" style="width:100%; color:black; ">'+
						'<input name="tensp" type="hidden" value="" style="width:100%">'+
					'</TD>'+
					'<TD align = "center" >'+
				    	'<input name="tonkho1" disabled="disabled" type="text"  style="width:100%;text-align:center; color:black; ">'+
				    	'<input name="tonkho" type="hidden"  style="width:100%;text-align:center">'+
				    '</TD>'+
				     '<TD align = "center" >'+
				    	'<input name="soluong1" id="soluong1_'+pos+'" type="hidden" value=""  style="width:100%;text-align:right; color: black;">'+
				    	'<input name="soluong2" id="soluong2_'+pos+'" type="hidden" value=""  style="width:100%;text-align:right; color: black;">'+
				    	'<input name="soluongThung" id="soluongThung_'+pos+'" type="text" onkeyup="QuyRaLe('+pos+')" onkeypress="return keypress(event);" value=""  style="width:100%;text-align:right; color: black;">'+
				    '</TD>'	+
				    '<TD align = "center" >'+
			        	'<input name="soluong" id="soluong_'+pos+'" type="text" value="" style="width:100%; text-align:right" onkeyup="Dinhdang(this)">'+
			       ' </TD>'+						        
				     '<TD align = "center" >'+
				    	'<input name="donvitinh1" disabled="disabled" type="text"  style="width:100%;text-align:center; color:black; ">'+
				    	'<input name="donvitinh" type="hidden"  style="width:100%;text-align:center">'+
				    '</TD>'+
				    '<TD align = "center" >'+
				    	'<input type="text" name="dongia1" disabled="disabled" style="width:100% ;text-align:right; color: black;">'+
				    	'<input type="hidden" name="dongia" readonly style="width:100% ;text-align:right">'+
				    '</TD>'+
				     '<TD align = "center" >'+
				    	'<input type="text" name="dongiathung" readonly="readonly" style="width:100% ;text-align:right; color: black;">'+
				    '</TD>'+
				    '<TD align = "center" >'+
				    	'<input name="spchietkhau1" type="text" disabled="disabled"  style="width:100%;text-align:right; color: black;">'+
				    	'<input name="spchietkhau" type="hidden" readonly style="width:100%;text-align:right">'+
				    '</TD>'+
				    '<TD align = "center" >'+
				    	'<input name="thanhtien1" type="text" disabled="disabled"  style="width:100%;text-align:right; color: black;">'+
				    	'<input name="thanhtien" type="hidden" readonly  style="width:100% ;text-align:right">'+
				    '</TD>'+
				    '<TD align = "center" >'+
				    	'<input name="ctkm1" type="text" disabled="disabled" style="width:100%; color: black;">'+
				    	'<input name="ctkm" type="hidden" readonly style="width:100%">'+
				    '</TD>'+
				'</TR>');
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
		
		function GetKenhBanHang(nppId)
		{
			var kenhId = document.getElementById("kenhId");
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				   kenhId.innerHTML = xmlhttp.responseText;
			   }
			}
			xmlhttp.open("POST","../../AjaxDonDatHang?type=GetKenhBanHang&nppId=" + nppId ,true);
			xmlhttp.send();
		}
		
		function GetChietKhau()
		{
			var nppId=document.getElementById("nhappid").value;
			var chietkhauId = document.getElementById("chietkhau");
			
			var ngaydat = document.getElementById("ngaygiaodich").value;
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				   chietkhauId.value = xmlhttp.responseText;
			   }
			}
			xmlhttp.open("POST","../../AjaxDonDatHang?type=GetChietKhau&nppId=" + nppId+"&ngaydat="+ngaydat ,true);
			xmlhttp.send();
		}
		
		
		function GetGiaVanChuyen()
		{
			var nppId=document.getElementById("nhappid").value;
			var chietkhauId = document.getElementById("GiaVanChuyen");
			
			
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				   chietkhauId.value = xmlhttp.responseText;
			   }
			}
			xmlhttp.open("POST","../../AjaxDonDatHang?type=GetGiaVanChuyen&nppId=" + nppId ,true);
			xmlhttp.send();
		}
		
		
		function GetChietKhauThuongMai()
		{
			var nppId=document.getElementById("nhappid").value;
			var chietkhauId = document.getElementById("sotienCktm");
			var ngaydat = document.getElementById("ngaygiaodich").value;
			var dhId=null;
			var xmlhttp;
			if (window.XMLHttpRequest)
			{
			   xmlhttp = new XMLHttpRequest();
			}
			else
			{
			   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			}
			
			xmlhttp.onreadystatechange=function()
			{
			   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   {
				   chietkhauId.value = xmlhttp.responseText;
			   }
			}
			var query="../../AjaxDonDatHang?type=GetChietKhauThuongMai&nppId=" + nppId+"&ngaydat="+ngaydat+"&ddhId="+dhId;
			xmlhttp.open("POST", query,true);
			xmlhttp.send();
		}
		
		
		
		function QuyRaLe(pos)
		{
			
			var nppId= document.getElementById("nppId");
			var kenhId= document.getElementById("kenhId");
			var dvkdId = document.getElementById("dvkdId");
			var dvdlId = document.getElementsByName("dvdlId");
			var spId = document.getElementsByName("spMa");
			var spMa = document.getElementsByName("spMa");
			var khoId = document.getElementsByName("khoId");
			
			if( spId.item(pos).value != '' && dvdlId.item(pos).value != '' &&khoId.item(pos).value!='' )
			{
				var xmlhttp;
				if (window.XMLHttpRequest)
				{
				   xmlhttp = new XMLHttpRequest();
				}
				else
				{
				   xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
				}
				
				xmlhttp.onreadystatechange=function()
				{
				   if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
				   {
					   soluong.item(pos).value = xmlhttp.responseText;
				   }
				}
				xmlhttp.open("POST","../../AjaxDonDatHang?type=GetDonGia&spId=" + spId.item(pos).value + "&dvdlId=" + dvdlId.item(pos).value + "&nppId=" + nppId+"&kenhId="+kenhId+"&dvkdId="+dvkdId+"&khoId="+khoId.item(pos).value+"",true);
				xmlhttp.send();
			}
			else
			{
				soluong.item(pos).value = "0";
			}
		}
		
		
</script>

</HEAD>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" >
<form name="dhForm" method="post" action="../../DonmuahangKmUpdateSvl">
<input type="hidden" name="userId" value='<%=userId %>'>
<input type="hidden" name="userTen" value='<%=userTen %>'>
<input type="hidden" name="action" value='1'>
<INPUT type="hidden" name="trangthai" value=''>
<INPUT type="hidden" name="ischot" value=''>   
<INPUT type="hidden" name="id" value='<%=hdBean.getId()%>'>   
<input type="hidden" name='tenform' value="newform">

   
<TABLE width="100%" border="0" cellspacing="0" cellpadding="0" height="100%">
	<TR>
		<TD colspan="4" align='left' valign='top' bgcolor="#FFFFFF">
				<TABLE border =0 width = "100%" cellpadding="2" cellspacing="0">
				<TBODY>
					<TR height="22">
						<TD align="left" >
							<TABLE width="100%" cellpadding="0" cellspacing="0" >
								<TR>
									<TD align="left">
									   <TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										   <TR height="22">
			 								   <TD align="left"  class="tbnavigation">&nbsp;Quản lý bán hàng > Đơn hàng KM > Cập nhật </TD>								   
			 								   <TD align="right" class="tbnavigation">Chào mừng bạn&nbsp;&nbsp;  <%= userTen%> &nbsp; </TD>
					    				 </TR>
									  </TABLE>
								  </TD>
							  </TR>	
						  	</TABLE>
							<TABLE width="100%" border="0" cellpadding="1" cellspacing="0">
								<TR ><TD >
									<TABLE width="100%" border="0" cellpadding="0" cellspacing="0">
										<TR class = "tbdarkrow">
											<TD width="30" align="left"><A href = "../../DonmuahangKmSvl?userId=<%=userId %>" ><img src="../images/Back30.png" alt="Quay về"  title="Quay về" border="1" longdesc="Quay về" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
										    <TD width="30" align="left" ><A id = "btnSave" href="javascript:saveform()" ><img src="../images/Save30.png" alt="Lưu lại" title="Lưu lại" border="1" longdesc="Lưu lại" style="border-style:outset"></A></TD>
										    <TD width="2" align="left" >&nbsp;</TD>
							    			<TD width="30" align="left"></TD>
								    		<TD align="left" >&nbsp;</TD>
										</TR>
									</TABLE>
								</TD></TR>
							</TABLE>												
							<TABLE border="1" width="100%" cellpadding = "1" cellspacing = "0" style="border-color:gray;" >
								<tr>
								
								 <TD align="left" class="legendtitle">
									<fieldset  >
									<legend> Thông báo</legend>
				    				<textarea name="dataerror"  style="width:100%;margin:0px " readonly="readonly" rows="1"><%=hdBean.getMessage() %></textarea>
							  	</fieldset>
							  	 </TD>
								</tr>
								
								<TR class="plainlabel">
									<TD align="left" >						
										
										<TABLE  border="0" bordercolor="white" width="100%" cellpadding = "3" cellspacing = "0" style="padding-left:2px ;" >
											<tr class="plainlabel">
											<th width='15%' ></th>
											<th width='35%' ></th>
											<th width='15%' ></th>
											<th width='35%' ></th>
											</tr>
											<tr class="plainlabel" >
											<td >Nhà cung cấp  </td>
											<td >
												<select name='nhaccid' style="width: 100%">
													  <% if(rs_nhacc != null){
														  try{ while(rs_nhacc.next()){ 													 
											    			if(rs_nhacc.getString("pk_seq").trim().equals(hdBean.getIdNhaCungCap())){ %>
											      				<option value='<%=rs_nhacc.getString("pk_seq")%>' selected><%=rs_nhacc.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_nhacc.getString("pk_seq")%>'><%=rs_nhacc.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											<td > Chọn ĐVKD </td>
											<td>
											 <select name='dvkdid' style="width: 100%" onchange="loadcontent();">
													  <% if(rs_dvkd != null){
														  try{ while(rs_dvkd.next()){ 													 
											    			if(rs_dvkd.getString("pk_seq").trim().equals(hdBean.getdvkdid())){ %>
											      				<option value='<%=rs_dvkd.getString("pk_seq")%>' selected><%=rs_dvkd.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_dvkd.getString("pk_seq")%>'><%=rs_dvkd.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select>	
											</td>
											</tr>
											<TR class="plainlabel">
											  <TD class="plainlabel">Ngày giao dịch </TD>
											  <TD class="plainlabel">                               
                                                 <input type="text" size="10" class="days" id="ngaygiaodich" name="ngaygiaodich" value="<%=hdBean.getNgaygiaodich()%>" maxlength="10"   /> 
											    </TD>

                                          <!-- chen vao kenh ban hang -->
                                          <td>Kênh bán hàng</td>
									          <td>
									          <select  id="kenhId"  name='kenhbanhang' style="width: 100%" onchange="loadcontent();"  >
													  <% if(rs_kenhbanhang != null){
														  try{ while(rs_kenhbanhang.next()){ 				
											    			if(rs_kenhbanhang.getString("pk_seq").trim().equals(hdBean.getIDKenhBanHang())){ %>
											      				<option value='<%=rs_kenhbanhang.getString("pk_seq")%>' selected><%=rs_kenhbanhang.getString("ten") %></option>
											      			<%}else{ %>
											     				<option value='<%=rs_kenhbanhang.getString("pk_seq")%>'><%=rs_kenhbanhang.getString("ten") %></option>
											     			<%}}}catch(java.sql.SQLException e){}} %>	
												</select> 
									          </td>
                                          
                                          	</TR>
											<TR class="plainlabel" >
												<TD >Chi nhánh / Đối tác </TD>
												<td >
													 <input type="text" id="nhappid" name="nhappid"  value="<%= hdBean.getNppId() %>">
												</td>
												<td>
													Tên Chi nhánh / Đối tác
												</td>
												<td>
												<input type="text" id="tennpp" name="tennpp"  style="width: 100%" value="<%=hdBean.getNppTen() %>">
												</td>
											</TR>
											
											
											  <TR class="plainlabel" >
											  <TD  >Tổng số tiền(Chưa VAT) </TD>
											  <TD  ><input name="SoTienChuaVat" id="SoTienChuaVat"  type="text" readonly="readonly" 
											  	  value="<%=  formatter.format(hdBean.getTongtientruocVAT())%>" >
											  	VND </TD>
											  	 <TD  class="plainlabel">VAT (%) </TD>
											  <TD  class="plainlabel"><input name="VAT" id="VAT" type="text" value="<%= Math.round(hdBean.getVAT()) %>" onkeypress = "return keypress(event);">%</TD>
											  	 
											  </TR>
											  
									       <tr>
					                        	<TD  class="plainlabel">Tổng tiền(sau VAT) </TD>	     
										        <TD   class="plainlabel"><input name="SoTienCoVAT" id="SoTienCoVAT" type="text" readonly="readonly" 
										        	value="<%= formatter.format(hdBean.getTongtiensauVAT())%>"> 
										          VND </TD>
										          
										          <TD  class="plainlabel"> </TD>	     
										        <TD   class="plainlabel"></TD>
					                        	</tr>
												<TR class="plainlabel" >
											  <TD  >Ghi chú</TD>
											  <TD> <textarea rows="2" name="ghichu" id="ghichu" style="width:100% ;font-size:11pt "  > <%=hdBean.getGhichu()%> </textarea></TD>
											  	
											  </TR>
										 
											<TR class="plainlabel" >
											  <TD  >Tổng thùng</TD>
											  <TD>
											  	<input name="tongthung" id="tongthung" style="width: 100px" type="text" value="<%=  formatter.format( hdBean.GetTongThung()    )   %>" >
											   </TD>
                                          	 <TD  class="plainlabel">Tổng trọng lượng</TD>
											  <TD  class="plainlabel"> 
											  <input name="tongtrongluong"  id="tongtrongluong" style="width:120px" type="text" value="<%= formatter.format(hdBean.GetTrongLuong() )%>" >(Kg)  
											     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;Thể Tích 
											  <input name="tongthetich"  id="tongthetich" style="width:120px" type="text" value="<%=formatter2.format(hdBean.GetTheTich()) %> " >(m3)
											  </TD>
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
											  <td>
											 	Hình thức vận chuyển
											 	<%String ht[]={"CTVC","KHVC","TRVC"}; %>
											  </td>
											  <td colspan="3"> 
											 	 <select name ="hinhthucvanchuyen" >
											 	 <option value="" ></option>
											 	 	<%for(int i=0;i<3;i++){
											 	 		if(ht[i].equals(hdBean.getHinhthucvanchuyen())){
											 	 		%>
											 	 		<option value="<%=ht[i]%>" selected="selected"><%=ht[i]%></option>
											 	 	<%}else { %>
											 	 		<option value="<%=ht[i]%>" ><%=ht[i]%></option>
											 	 	<%}} %>
											 	  </select>
											  </td>
										  </tr>										 										 
										 
										  <tr class="plainlabel">
											  <td>
											  Thông tin DH NPP
											  </td>
											  <td colspan="3"> 
											 	 <textarea rows="1" style="width:100% ;font-size:11pt "  readonly="readonly" id="thongtin"> <%=hdBean.getThongTin() %> </textarea>
											  </td>
										  </tr>
										 
										  <tr class="plainlabel">
										  <td>
										  Số tiền bằng chữ
										  </td>
										  <td colspan="3"> 
										  <textarea rows="1" style="width:100% ;font-size:11pt "  readonly="readonly" id="doctien">  </textarea>
										  </td>
										  </tr>

										</TABLE>
								
								  </TD>

							   </TR>	
							   
							   <TR>
							   		<TD>
										<TABLE class="tabledetail" width = "100%" border="0" cellpadding="0" cellspacing="1">
										<tbody id="san_pham">
											<TR class="tbheader" >
												<TH width="15%" height="20">Mã sản phẩm </TH>
												<TH width="25%">Tên sản phẩm</TH>
												<TH width="5%">Số lượng thùng</TH>
												<TH width="5%">Số lượng lẻ</TH>
												<TH width="25%">Scheme </TH>
												<TH width="10%">Đơn giá </TH>
												<TH width="15%">Thành tiền </TH>	
											</TR>
									<%
									int m = 0;
							if(splist != null){
							IERP_DonDatHang_SP sanpham;
							int size = splist.size();
							while (m < size){
								sanpham = splist.get(m); 
								%>
									<TR class= 'tblightrow' >
									<TD align="left" >
										<input name='idsp' type='hidden' value=<%=sanpham.getIdSanPham() %>>
										<input name="masp" type="text" value="<%=sanpham.getMaSanPham()%>" autocomplete='off'  onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%">
									</TD>
									<TD  align="left" >
										<input name="tensp" type="text" readonly value="<%=sanpham.getTenSanPham()%>" style="width:100%" >
									</TD>
							        <TD align = "left" >
								        <input name="soluong" type="text" value="<%= sanpham.getSoLuong() %>" onkeypress="return keypress(event);" style="text-align:left;width:100%;">
								     </TD>
								     <TD align = "left" >
								        <input name="soluongle" type="text" value="<%= sanpham.getSoluongle() %>" onkeypress="return keypress(event);" style="text-align:left;width:100%;">
								     </TD>
								     <td align="center">
				                   		<select style="width: 100%" name="ctkmId" id="ctkmId_<%=m%>">
				                   			<option value=""> </option>
				                   			<%
				                   				if(ctkmList!=null)
				                   				{
				                   					Enumeration<String> keys = ctkmList.keys();
				                   					while(keys.hasMoreElements())
				                   					{
				                   						String key = keys.nextElement();
				                   						if(key.toString().equals(sanpham.getCtkmId()  )){
				                   							%>
				                   							<option selected="selected" value="<%=key%>"><%= ctkmList.get(key)%></option>
				                   							<%
				                   						}else{
				                   							%>
				                   							<option  value="<%=key%>"><%= ctkmList.get(key)%></option>
				                   							<%
				                   						}
				                   						
				                   					     
				                   					}
				                   				}
				                   			%>
				                   		</select>
			                   		</td>
							        
								     <%
								    	double giachuan=sanpham.getGiachuan();
								    	double dongia=sanpham.getDonGia();
								    	double quycach=sanpham.getQuyCach();
								    	giachuan=quycach*giachuan;
							    		dongia=quycach*dongia;
								    %>
								    <TD align = "center" >
								    	<input type="text" name="dongia" value="<%= formatter.format( dongia   ) %>" readonly style="width:100% ;text-align:right">
								    </TD>
								    <TD align = "center" > 
								    	<input name="thanhtien" type="text" value="<%=formatter.format(sanpham.getThanhTien()) %>" readonly  style="width:100% ;text-align:right">
								    	<input type="hidden" name="quycach" value="<%=sanpham.getQuyCach()%>" readonly style="width:100% ;text-align:right"> 
								    	<input name="trongluong" type="hidden" value="<%=sanpham.getTrongluong()%>" readonly  style="width:100% ;text-align:right">
								    	<input name="thetich" type="hidden" value="<%=sanpham.getThetich()%>" readonly  style="width:100% ;text-align:right">
								     	<input name="goivc" type="hidden" value="<%=sanpham.getGoiVc()%>" readonly  style="width:100% ;text-align:right">
								     	<input type="hidden" name="giachuan" value="<%= formatter.format( dongia   ) %>" readonly style="width:100% ;text-align:right">
								     	<input type="hidden" name="tonkho" value="0" readonly style="width:100% ;text-align:right">
								     	<input type="hidden" name="donvitinh" value="<%=sanpham.getDonViTinh() %>" readonly style="width:100% ;text-align:right">
								     	
								    </TD>
								</TR>
								<% m++; }}%>		
								<%
									int soSp=m;
									while(soSp < 40){ 
								%>
								<TR class= 'tblightrow'>
									<TD align="center" >
									<input name='idsp' type='hidden'>
										<input name="masp" type="text" value="" autocomplete='off' onkeyup="ajax_showOptions(this,'abc',event)" style="width:100%" >
									</TD>
									<TD align="left" >
										<input name="tensp" type="text" readonly value="" style="width:100%">
									</TD>
								    <TD align = "left" >
							        	<input name="soluong" type="text" value="" style="width:100%; text-align:left" onkeypress="return keypress(event);">
							        </TD>
							         <TD align = "center" >
								    	<input name="soluongle" type="text" value=""  style="width:100%">
								    </TD>
								    <td align="center">
				                   		<select style="width: 100%" name="ctkmId" id="ctkmId_<%=soSp%>">
				                   			<option value=""> </option>
				                   			<%
				                   				if(ctkmList!=null)
				                   				{
				                   					Enumeration<String> keys = ctkmList.keys();
				                   					while(keys.hasMoreElements())
				                   					{
				                   						String key = keys.nextElement();
				                   							%>
				                   							<option  value="<%=key%>"><%= ctkmList.get(key)%></option>
				                   							<%
				                   						}
				                   					}
				                   			%>
				                   		</select>
			                   		</td>
								    
								    <TD align = "center" >
								    	<input name="dongia" type="text" value="" readonly style="width:100%;text-align:right">
								    </TD>
								    
								    <TD align = "center" > 
								    <input name="thanhtien" type="text" value="" readonly style="width:100%;text-align:right">
								    <input name="quycach" type="hidden" value="" readonly  style="width:100% ;text-align:right">
								    <input name="trongluong" type="hidden" value="" readonly  style="width:100% ;text-align:right">
								    <input name="thetich" type="hidden" value="" readonly  style="width:100% ;text-align:right">
								     <input name="goivc" type="hidden" value="" readonly  style="width:100% ;text-align:right">
								     <input name="donvitinh" type="hidden" value="" readonly  style="width:100% ;text-align:right">
								     <input type="hidden" name="giachuan" value="" readonly style="width:100% ;text-align:right">
								     <input type="hidden" name="tonkho" value="" readonly style="width:100% ;text-align:right">
								    </TD>
								</TR>
								<% soSp++;} %>
								</tbody>
								</TABLE>															  
							</TD>
							  </TR>	
							  	<tr>
							  	<td>
							  		&nbsp;&nbsp;&nbsp;&nbsp;	<a class="button2" href="javascript:ThemSanPham()">
	                         			<img style="top: -4px;" src="../images/add.png" alt="">Thêm sản phẩm</a>&nbsp;&nbsp;&nbsp;&nbsp;
							  	
							  	</td>
							  	</tr>	  							  					   
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
var ctkmId=document.getElementsByName("ctkmId");

	$(document).ready(function()
	{
		for(var i=0; i < ctkmId.length; i++)
		{
			$("#ctkmId_"+i).select2();
		}
	} ) ;		


	replaces();
	jQuery(function()
	{		
		$("#nhappid").autocomplete("Erp_NhaPhanPhoiList.jsp");
	});	
</script>
</form>
<script type="text/javascript">
		dropdowncontent.init('ckDetail', "left-top", 300, "click");
</script>
</BODY>
</HTML>
<%
try{
 if(rs_nhacc!=null){
	 rs_nhacc.close();
 }
 if(rs_khott!=null){
	 rs_khott.close();
 }
 if(rs_nhapp!=null){
	 rs_nhapp.close();
 }
 if(rs_kenhbanhang!=null){
	 rs_kenhbanhang.close();
 }
 if(rs_dvkd!=null){
	 rs_dvkd.close();
 }
	hdBean.DBClose();
	ctkmList.clear();
	
}catch(Exception er){
	
}
%>

