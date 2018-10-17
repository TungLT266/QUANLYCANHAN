<%@page import="geso.traphaco.erp.beans.lenhsanxuatgiay.ISpSanxuatChitiet"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.donmuahangtrongnuoc.*"%>
<%@ page import="geso.traphaco.erp.beans.donmuahangtrongnuoc.imp.*"%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%
	IErpDonmuahang_Giay dmhBean = (IErpDonmuahang_Giay) session.getAttribute("dmhBean");
	ResultSet dvthList = dmhBean.getDvthList();
	ResultSet loaihhList = dmhBean.getLoaiList();
	List<ISanpham> spList = dmhBean.getSpList();
	List<IDonvi> dvList = dmhBean.getDvList();
	List<ITiente> ttList = dmhBean.getTienteList();
	List<IKho> khoList = dmhBean.getKhoList();
	ResultSet rs = dmhBean.getDuyet();
	Long tiGiaNguyenTe =dmhBean.getTiGiaNguyenTe();
	ResultSet KenhRs = dmhBean.getKenhRs();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	
	String[] cpkDienGiai = dmhBean.getCpkDienGiai();
	String[] cpkSotien = dmhBean.getCpkSoTien();
	ResultSet nccRs = dmhBean.getNccRs();
	
	NumberFormat formatter_3sole = new DecimalFormat("#,###,###.###");
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<TITLE>SalesUp - Project</TITLE>
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
<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
<script>
        $(document).ready(function()
        {
        	$(".ndThongtinhdngoai").colorbox({width:"46%", inline:true, href:"#ndThongtinhdngoai"});
            //Example of preserving a JavaScript event for inline calls.
            $("#click").click(function(){ 
                $('#click').css({"background-color":"#f00", "color":"#fff", "cursor":"inherit"}).text("Viethung - Project.");
                return false;
            });
        });
</script>
<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spbomList.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">

function replaces()
{
	var loaidh = document.getElementById("loai").value;
	
	if(loaidh != '0')
	{
		var nccId = document.getElementById("nccId");
		var nccTen = document.getElementById("nccTen");
		var nccLoai = document.getElementById("nccLoai");
		
		var tem = nccId.value;
		if(tem == "")
		{
			nccTen.value = "";
			nccLoai.value = "";
		}
		else
		{
			if(parseInt(tem.indexOf(" - ")) > 0)
			{
				nccId.value = tem.substring(0, parseInt(tem.indexOf(" - ")));
				
				tem = tem.substr(parseInt(tem.indexOf(" - ")) + 3);
				nccTen.value = tem.substring(0, tem.indexOf(" [ ") );
				
				tem = tem.substr(parseInt(tem.indexOf(" [ ")) + 3);
				nccLoai.value = tem.substring(0, tem.indexOf(" ]") );
				
				if(nccId.value != '')
				{
					document.forms['dmhForm'].action.value='changeNCC';
				    document.forms["dmhForm"].submit();
				}
			}
		}
	}
		
		
	var idsp = document.getElementsByName("idsp");
	var masp = document.getElementsByName("masp");
	var tensp = document.getElementsByName("tensp");
	var donvitinh = document.getElementsByName("donvitinh");
	var soluong = document.getElementsByName("soluong");
	var dongia = document.getElementsByName("dongia");
	var thanhtien = document.getElementsByName("thanhtien");
	var nhomhang = document.getElementsByName("nhomhang");
	var ngaynhan = document.getElementsByName("ngaynhan");
	var thuexuat = document.getElementsByName("thuexuat");
	var idmarquette = document.getElementsByName("idmarquette");
	var phantramthue = document.getElementsByName("phantramthue");
	var thuenhapkhau = document.getElementsByName("thuenhapkhau");
	var tiGiaNguyenTe = document.getElementById("tiGiaNguyenTe");
	var donGiaUSD = document.getElementsByName("donGiaUSD");
	var nguongoc = document.getElementById("nguongoc").value;
	var ngaynhandukien = document.getElementsByName("ngaynhandukien");
	
	var bvat = 0;
	var avat = 0;
	
	var sodong =  masp.length;
	var i;
	for(i = 0; i < sodong; i++)
	{
		if( masp.item(i).value != "")
		{
			
			
			var sp = masp.item(i).value;
			var pos = parseInt(sp.indexOf(" -- "));
			var slg = soluong.item(i).value.replace(/,/g,""); 
			var tx = thuexuat.item(i).value.replace(/,/g,"");
			
			if(pos > 0)
			{
				masp.item(i).value = sp.substring(0, pos);
				sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
				tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
				
				sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
				donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				nhomhang.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				thuexuat.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
				
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				idmarquette.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				
				mienthue.item(i).value =idsp.item(i).value ;
				sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
				var chonthue=sp.substring(0, parseInt(sp.indexOf("]")));
				if(chonthue=="1")
				{
					mienthue.item(i).checked = true;
					thuexuat.item(i).readOnly = true;
					
				}
				else if(chonthue=="0")
				{
					mienthue.item(i).checked = false;
					thuexuat.item(i).readOnly = false;
				}
			}
			
			if(soluong.item(i).value != "")
			{
				if(dongia.item(i).value != "")
				{
					var dgia = dongia.item(i).value.replace(/,/g,""); 
					dongia.item(i).value =  DinhDangDonGia(dgia);
					var tt = 0;
					// sai cho nay
					if(document.getElementById("AUSDVAT") && document.getElementById("BUSDVAT")){
						donGiaUSD.item(i).value = parseFloat(dgia) * parseFloat(tiGiaNguyenTe.value);
						tt = Math.round(parseFloat(slg) * parseFloat(donGiaUSD.item(i).value));
					} else {
						
						tt = Math.round(parseFloat(slg) * parseFloat(dgia));
					}
					
					var ttavat =Math.round( parseFloat(tt)) +Math.round( parseFloat(tt) * parseFloat(tx) / 100 );
					if(loaidh == '0')
					{
						thanhtien.item(i).value =  DinhDangDonGia(  ttavat.toFixed(0) );
					}
					else
					{
						thanhtien.item(i).value =  DinhDangDonGia(  ttavat.toFixed(0));
					}
					
					if(nguongoc == 'NN')
					{
						if(phantramthue.item(i).value != "")
						{
							var ptramthue = phantramthue.item(i).value.replace(/,/g,"");
							var tnhapkhau = parseFloat(slg) * parseFloat(dgia) * parseFloat(ptramthue) / 100;
							
							phantramthue.item(i).value = ptramthue;
							thuenhapkhau.item(i).value = tnhapkhau;
						}
						
					}
					
					bvat = bvat + tt;
					avat = avat + ttavat;
				}
			}
			else if(document.getElementById("loaihh").value != 2 && loaidh != '1')		
			{
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";
				
				phantramthue.item(i).value = "";
				thuenhapkhau.item(i).value = "";
			}
		}
		else 
		{
			tensp.item(i).value = "";
			donvitinh.item(i).value = "";
			nhomhang.item(i).value = "";
			idsp.item(i).value = "";
			thuexuat.item(i).value = "";
			dongia.item(i).value = "";
			idmarquette.item(i).value = "";
			phantramthue.item(i).value = "";
			thuenhapkhau.item(i).value = "";
			mienthue.item(i).value = "";
			thanhtien.item(i).value  = "";
			dongia.item(i).value = "";
			soluong.item(i).value = "";
			ngaynhandukien.item(i).value = "";
		}
	}
	
	//CAP NHAT CHI PHI KHAC
	var sotienCPK = document.getElementsByName("sotienCPK");
	var totalCPK = 0;
	
	for(var j = 0; j < sotienCPK.length; j++ )
	{
		if( sotienCPK.item(j).value != '' )
		{
			totalCPK += parseFloat(sotienCPK.item(j).value.replace(/,/g,""));
			sotienCPK.item(j).value = DinhDangDonGia(sotienCPK.item(j).value.replace(/,/g,""));
		}
	}
	
	document.getElementById("cpKhac").value = DinhDangDonGia(totalCPK); 
	document.getElementById("BVAT").value = DinhDangTien( Math.round(bvat) );
	document.getElementById("AVAT").value = DinhDangTien( Math.round(avat) );
	
	if(document.getElementById("AUSDVAT") && document.getElementById("BUSDVAT")){
		document.getElementById("AUSDVAT").value=DinhDangTien(parseFloat(Math.round(avat))/ parseFloat(tiGiaNguyenTe.value));
		document.getElementById("BUSDVAT").value=DinhDangTien(parseFloat(Math.round(bvat))/ parseFloat(tiGiaNguyenTe.value));	
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
		 var dvth = document.getElementById("dvthId");
		 var nguongoc = document.getElementById("nguongoc");
		 var tiente = document.getElementById("tiente_fk");
		 var nccId = document.getElementById("nccId");
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
		 
		 if(nccId.value == "")
		 {
			 nccId.focus(); 
			 error.value="Vui lòng chọn nhà cung cấp";
			return;
		 }
		 
		 if(lhh.value=="")
		 {
			 lhh.focus();
		 	error.value="Vui lòng chọn loại loại hàng hóa!";
			return;
		 }
		 
		 if( !checkSanPham())
		 {	
			 error.value="Không có sản phẩm nào được chọn";
			return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='save';
	     document.forms['dmhForm'].submit();
	 }
	 
	 function TinhTien()
		{
			var loaidh = document.getElementById("loai").value;
			
			var loaiHH = document.getElementById("loaihh").value;
			var soluong = document.getElementsByName("soluong");
			var dongia = document.getElementsByName("dongia");
			var thanhtien = document.getElementsByName("thanhtien");	
			var tiente = document.getElementById("tiente_fk");
			var tongtien = 0;
			
			var nguongoc = document.getElementById("nguongoc").value;
			var thuenhapkhau = document.getElementsByName("thuenhapkhau");
			var tongluong = 0;
			
			for(var index =0;index< soluong.length; index++)
				{
					if(soluong.item(index).value!="")
						{
							tongluong+=parseFloat(soluong.item(index).value.replace(/,/g,"")) ;
						}
				}
			document.getElementById("tongluong").value = tongluong.toFixed(4);
			
			if( loaiHH == '0' )
			{			
				if( loaidh == '2' )
				{
					for(var i = 0; i < thanhtien.length; i++)
					{
						if( thanhtien.item(i).value != "" )
						{
							dongia.item(i).setAttribute("readonly", "readonly");
							soluong.item(i).setAttribute("readonly", "readonly");
							thanhtien.item(i).setAttribute("readonly", "readonly");	
							tongtien = parseFloat(tongtien) +  parseFloat(thanhtien.item(i).value.replace(/,/g,"")); 
						}
					}
				}
	/* 			else
				{
					for(var i = 0; i < thanhtien.length; i++)
					{
						if( thanhtien.item(i).value != "" )
						{
							var thanh_tien = thanhtien.item(i).value.replace(/,/g,"");
								tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien); 
							
						}
					}
				} */
			}
			/* else
			{
				for(var i = 0; i < thanhtien.length; i++)
				{
					if( thanhtien.item(i).value != "" )
					{
						var thanh_tien = thanhtien.item(i).value.replace(/,/g,"");
						 
							tongtien = parseFloat(tongtien) +  parseFloat(thanh_tien);
					}
				}
			} */
			
			/* //Chi tien viet moi format lam tron so luc hien thi
			var pos = parseInt(tiente.value.indexOf(" - "));
			var tien_te = tiente.value.substring(0, pos);
			
			if(document.getElementById("cpKhac").value != '')
				tongtien = parseFloat(tongtien) + parseFloat(document.getElementById("cpKhac").value.replace(/,/g,""));
			
			if(tien_te == '100000' )
			{
				var tienchuaVAT = tongtien;
				document.getElementById("BVAT").value = DinhDangTien(tienchuaVAT);
			
				var vat = document.getElementById("VAT").value;
				if(vat == "")
					vat = "0";
			
				var tongtiencoVAT = (parseFloat(tienchuaVAT) * parseFloat(vat) / 100) + parseFloat(tienchuaVAT);
				document.getElementById("AVAT").value = DinhDangTien(Math.round(tongtiencoVAT));
			}
			else
			{
				var tienchuaVAT = tongtien;
				document.getElementById("BVAT").value = DinhDangDonGia( tienchuaVAT.toFixed(0) );
			
				var vat = document.getElementById("VAT").value;
				if(vat == "")
					vat = "0";
			
				var tongtiencoVAT = (parseFloat(tienchuaVAT) * parseFloat(vat) / 100) + parseFloat(tienchuaVAT);
				document.getElementById("AVAT").value = DinhDangDonGia( tongtiencoVAT.toFixed(0) );
			} */
			
		}
	 
	 function checkSanPham()
	 {
		 var masp = document.getElementsByName("masp");
		 for(var hh = 0; hh < masp.length; hh++)
		 {
			if(masp.item(hh).value != "")
			{
				return true;
			}
		 }
		 return false;
	 }
	 
	 function goBack()
	 {
	  	window.history.back();
	 }
	 
	function capnhatBom(){ 
		 document.forms['dmhForm'].action.value='capnhatBom';
	     document.forms['dmhForm'].submit();
	 }
	
	function Yeucaunguyenlieu(){ 
		 document.forms['dmhForm'].action.value='Yeucaunguyenlieu';
	     document.forms['dmhForm'].submit();
	 }
	function Yeucaunguyenlieuthem(){ 
		 document.forms['dmhForm'].action.value='Yeucauthemnguyenlieu';
	     document.forms['dmhForm'].submit();
	 }
	
	
	function reload_Danhmucvattu(){ 
		 document.forms['dmhForm'].action.value='reload_Danhmucvattu';
	     document.forms['dmhForm'].submit();
	 }
	
	
	
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../ErpDonmuahangtrongnuocUpdate_GiaySvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="loai" id="loai" value='<%=dmhBean.getLoai()%>'> 
		<input type="hidden" name="action" value="1" />
		<input type="hidden" name="duyet" value="<%= dmhBean.getCanDuyet() %>" />
		<input type="hidden" name="id" value="<%= dmhBean.getId() %>" />
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Mua hàng trong nước > Đơn mua hàng > Hiển thị</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave"> 
				<!-- <A href="javascript:saveform();"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A> -->
				</span>
				
				<%-- <% if(dmhBean.getCanDuyet().equals("0")) { %> --%>
					<%-- <A href="../../ErpDonmuahangUpdate_GiaySvl?userId=<%=userId %>&dmhId=<%= dmhBean.getId() %>&task=print" >
						<img src="../images/Printer30.png" alt="Print"  title="Print" border="1" longdesc="Print" style="border-style:outset"></A>
					 --%>
					
					
					<A href="../../InDonDatHang?userId=<%=userId %>&Id=<%= dmhBean.getId() %>&task=print&loai=indonhang" >
				   <img src="../images/Printer30.png" alt="Print"  title="In Don Hang" border="1" longdesc="In Don Hang" style="border-style:outset"></A>
						
				
				<% if( Double.parseDouble(dmhBean.getTongtiensauVat().replaceAll(",","") )  >100000000 )  {%>
			
					<A href="../../InDonDatHang?userId=<%=userId %>&Id=<%= dmhBean.getId() %>&task=print&loai=inhopdong" >
				   <img src="../images/Pdf30.jpg" alt="Print"  title="
							In Họp Đồng Phụ Lục " border="1" longdesc="In Họp Đồng Phụ Lục" style="border-style:outset"></A>
					
						
				<%} %>		
						
						<A href="../../ErpInDonMuaHangVaGiaCongPdfSvl?userId=<%=userId %>&display=<%= dmhBean.getId() %>&duyet= <%= dmhBean.getLoai()%>">
						<img src="../images/Printer30.png" alt="In don hang va gia cong"  title="In đơn hàng và gia công" border="1" longdesc="Print" style="border-style:outset"></A>
				
						<A href="../../ErpInDonHangVaGiaCongMau2PdfSvl?userId=<%=userId %>&display=<%= dmhBean.getId() %>&duyet= <%= dmhBean.getLoai()%>&action=printpdf">
						<img src="../images/Pdf30.jpg" alt="In đơn hàng và gia công mẫu 2"  title="In đơn hàng và gia công mẫu 2" border="1" longdesc="Print" style="border-style:outset"></A>
					
						<A href="../../InDonDatHang?userId=<%=userId %>&Id=<%= dmhBean.getId() %>&loai=inexcell">
						<img src="../images/excel.gif" alt="Print"  title="Print Don Dat Hang Excel" border="1" longdesc="Print Don Dat Hang Excel" style="border-style:outset;width:30px; height:30px"></A>
				
				
						<A href="../../ErpInDonHangVaGiaCongMau2PdfSvl?userId=<%=userId %>&display=<%= dmhBean.getId() %>&duyet= <%= dmhBean.getLoai()%>&action=printExcel">
						<img src="../images/excel.gif"  alt="In đơn hàng và gia công mẫu 2 Excell"  title="In đơn hàng và gia công mẫu 2 Excell" border="1" longdesc="Print" style="border-style:outset; width:30px; height:30px"></A>
					
				
				
				<%-- <% } %> --%>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Đơn mua hàng</legend>
					<input type="hidden" value='<%=tiGiaNguyenTe %>' name="tiGiaNguyenTe" id="tiGiaNguyenTe">
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
						
						<TR>
								<TD class="plainlabel" valign="top" width="150px"> Mã đơn mua hàng</TD>
								<TD class="plainlabel" valign="top" >
									<input type="text" id="mamuahang" name="mamuahang"
											value="<%= dmhBean.getmaDMH() %>" maxlength="10" readonly />
								</TD>
								
								<%if(dmhBean.getLoaihanghoa().equals("2")){ %>
								<TD class="plainlabel" valign="top" width="150px"> Kênh</TD>
								<TD class="plainlabel" valign="top"  >
									<select name="kenhId" id="kenhId" >
										<option value=""></option>
										<% if (KenhRs != null)
										{
											while (KenhRs.next())
											{
												if (KenhRs.getString("pk_seq").equals(dmhBean.getKenhId()))
												{
												%>
													<option value="<%=KenhRs.getString("pk_seq")%>" selected="selected"><%=KenhRs.getString("ten")%></option>
												<% } else { %>
													<option value="<%=KenhRs.getString("pk_seq")%>"><%=KenhRs.getString("ten")%></option>
										<% } } KenhRs.close(); } %>
									</select>
								</TD>
								<%}else{ %>
								<TD class="plainlabel" valign="top" width="150px" colspan="3"> </TD>
								<%} %>
							</TR>
							
							<%if(dmhBean.getLoai().equals("0")) { // Mua hàng nhập khẩu mới chọn loại%>
							<TR>
							
								<TD class="plainlabel" valign="top" width="150px">Loại</TD>
								<TD class="plainlabel" valign="top">
									<select name="loaiDMH_NK" id="loaiDMH_NK" onChange="submitform();">
										<% if (dmhBean.getLoaiDMH_NK().equals("0")) { %>
											<option value=""></option>
											<option value="0" selected="selected">Hợp đồng</option>
											<option value="1">Đơn mua hàng</option>
										<% } else if (dmhBean.getLoaiDMH_NK().equals("1")) { %>
											<option value=""></option>
											<option value="0" >Hợp đồng</option>
											<option value="1"  selected="selected">Đơn mua hàng</option>
										<% } else { %>
											<option value="" selected="selected"></option>
											<option value="0" >Hợp đồng</option>
											<option value="1" >Đơn mua hàng</option>
										<% } %>
											<option value=""></option>
									</select>
								</TD>
								<%if(dmhBean.getLoaiDMH_NK().equals("0")){ %>
								<TD class="plainlabel" valign="top" width="150px">Thời hạn nợ</TD>
								<TD class="plainlabel" valign="top">
									<input type="text" name = "thoihanno" value = "<%= dmhBean.getThoihanno()%>" > ngày
								</TD>
								<%}else{ %>
								<TD class="plainlabel" valign="top" colspan="2"></TD>
								<%} %>
							</TR>
							<%} %>
							<%if(dmhBean.getLoaiDMH_NK().equals("0")){ %>
							<tr>
								<td class="plainlabel">Hợp đồng ngoại
								</td>
								<td class="plainlabel" colspan = 3>
									<a class="ndThongtinhdngoai" href="#"  >
	           	 					&nbsp; <img alt="Thông tin hợp đồng ngoại" src="../images/vitriluu.png"></a>
	           	 					<div style='display:none'>
               							<DIV id="ndThongtinhdngoai" style='padding:0px 5px; background:#fff;'>
                 							<h4 align="left">Thông tin hợp đồng ngoại</h4>
                 							<table width="100%" align="center">
                            				<tr>
                            					<td width=200px><font size=2>Số hợp đồng</font></td>
                            					<td colspan = 2>
                            						<INPUT type="text" name="sohopdong" style="width: 200px" value='<%=dmhBean.getSohopdong() %>'>&nbsp;
                            					</td>
                            				</tr>
                            				<%-- <tr>
			                            		<td width=200px><font size=2>Số lượng</font></td>
			                            		<td colspan = 2>
			                            			<INPUT type="text" name="soluonghd" style="width: 200px" value='<%=dmhBean.getSoluong() %>' onkeypress="return keypress(event);">&nbsp;
			                            		</td>
			                            	</tr> --%>
			                            	<tr>
			                            		<TD > <font size=2> Tên nhà nhập khẩu </font> </TD>
												<TD > 
													<INPUT type="text" name="tennhank" style="width: 200px" value='<%=dmhBean.getTennhanhapkhau() %>'>&nbsp;
												</TD>
			                            	</tr>
			                            	<tr>
			                            		<td width=200px><font size=2>Tên nhà sản xuất</font></td>
			                            		<td colspan = 2>
			                            			<INPUT type="text" name="tennhasx" style="width: 200px" value='<%=dmhBean.getTennhasanxuat() %>'>&nbsp;
			                            		</td>
			                            	</tr>
			                            	<tr>
			                            		<td width=200px><font size=2>Ngày ship</font></td>
			                            		<td colspan = 2>
			                            			<input class="days" type="text" name="ngayship" value='<%=dmhBean.getNgayship() %>'  size="20">
			                            		</td>
			                            	</tr>
			                            	<tr>
			                            		<td width=200px><font size=2>Ngày nhập kho</font></td>
			                            		<td colspan = 2>
			                            			<input class="days" type="text" name="ngaynhapkho" value='<%=dmhBean.getNgaynhapkho() %>'  size="20">
			                            		</td>
			                            	</tr>
	                    					</table>
				            			</DIV> 
				            		</div>
								</td>
							</tr>
							<%} %>
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày mua hàng</TD>
								<% if(dmhBean.getIsGiaCong().equals("1")) { %>
								<TD class="plainlabel" valign="top"  >
								<%} else {%>
								<TD class="plainlabel" valign="top" colspan=3  >
								<%} %>
									<input type="text" class="days" id="ngaymuahang" name="ngaymuahang"
											value="<%= dmhBean.getNgaymuahang() %>" maxlength="10" readonly />
								</TD>
								
								<% if(dmhBean.getIsGiaCong().equals("1")) { %>
								<TD class="plainlabel" valign="top" width="150px">Ngày bắt đầu</TD>
								<TD class="plainlabel" valign="top"  >
									<input type="text" class="days" id="ngaybatdau" name="ngaybatdau"
											value="<%= dmhBean.getNgaybatdau() %>" maxlength="10" readonly />
								</TD>
								<%} %>             
								
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
								
								<TD class="plainlabel">PO nội bộ</TD>
								<TD class="plainlabel">			
									<% if(dmhBean.getCheckedNoiBo().equals("1")) { %>
									<input type="checkbox" id="noibo" name="noibo" value="1" checked = "checked" onChange="changeNoiDia();">
									<% } else {  %>
										<input type="checkbox" id="noibo" name="noibo" value="1" onChange="changeNoiDia();">
									<% } %>
																		                       
                        		</TD>      
							</TR>
							<%if(dmhBean.getLoai().equals("1")) {%> 
							<tr>
								<TD class="plainlabel">Đơn vị chịu trách nhiệm</TD>
								<TD class="plainlabel" >
									<input type="text" id="dvchiutrachnhiem" name="dvchiutrachnhiem" value="<%= dmhBean.getDvChiuTrachNhiem() %>" >
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
									
									&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
									<%-- <span <%= ( dmhBean.getNguonGocHH().equals("NN") ? " " : " style='display: none;'  " ) %> > --%>
									<span  >
									  <input type="hidden" name="cpKhac" id="cpKhac" readonly="readonly" style="text-align: right;"   ></span>
									
								 
	           	 							
	           	 					<a href=""  style="visibility: hidden" id="chiphiKHAC" rel="cpKHAC" >
	           	 							&nbsp; <img alt="Diễn giải" src="../images/vitriluu.png"></a>
	           	 		
                          			<DIV id="cpKHAC" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    	<table width="100%" align="center">
				                        <tr>
				                            <th width="230px" style="text-align: center;" >Diễn giải</th>
				                            <th width="100px" style="text-align: center;" >Số tiền</th>
				                        </tr>
				                        
				                        <% int count2 = 0; 
				                        	if(cpkDienGiai != null) { 
				                        	for(int i = 0; i < cpkDienGiai.length; i++)
				                        	{ %>
				                        		
				                        	 <tr>
					                        	<td><input type="text" style="width: 100%" value="<%= cpkDienGiai[i] %>"  name="diengiaiCPK" ></td>
					                        	<td><input type="text" style="width: 100%; text-align: right;" value="<%= cpkSotien[i] %>"  name="sotienCPK"  ></td>
					                        </tr>
				                        	
				                        <%  count2++; } 
				                        	} %>
				                        
				                        <% for(int i = count2; i < 4; i++ ) { %>
				                        	<tr>
					                        	<td><input type="text" style="width: 100%" name="diengiaiCPK" ></td>
					                        	<td><input type="text" style="width: 100%; text-align: right;" name="sotienCPK"  ></td>
					                        </tr>
				                        <% } %>
				                        
				                    	</table>
					                     <div align="right">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('cpKHAC')" >Đóng lại</a>
					                     </div>
				            		</DIV>   
									
								</TD>
							</tr>	
							<%}%>
							
							<%if(!dmhBean.getLoai().equals("0")) { // Mua hàng nhập khẩu ko chọn NCC %>
							<TR>
								<TD class="plainlabel">Mã nhà cung cấp</TD>
								<TD class="plainlabel">
									<input type="text" id="nccId" name="nccId" value="<%= dmhBean.getNCC() %>" >
								</TD>
								<TD class="plainlabel">Tên nhà cung cấp</TD>
								<TD class="plainlabel">
									<input type="text" id="nccTen" name="nccTen" value="<%= dmhBean.getNccTen() %>" readonly="readonly" style="width: 100%;" >
									<input type="hidden" id="nccLoai" name="nccLoai" value="<%= dmhBean.getNccLoai() %>" readonly="readonly" >
								</TD>
							</TR>
							<%} %>
							
							<TR class="plainlabel">
								<TD class="plainlabel" style="display:none;">Ghi nhận công nợ</TD>
								<TD class="plainlabel" style="display:none;">
								<% if(dmhBean.getQuanlycongno().equals("1")) { %>
									<input type="checkbox" name="qlcongno" value="1" checked = "checked">
								<% } else {  %>
									<input type="checkbox" name="qlcongno" value="1" >
								<% } %>
								</TD>
								<TD></TD>
								<td></td>
								<TD class="plainlabel">Tiền tệ</TD>
								<TD class="plainlabel">
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
														<option value='<%= t.getId() + " - " + t.getTygiaquydoi()%>' selected="selected"><%= t.getMa() %></option>
													<% } else { %>
														<option value='<%= t.getId() + " - " + t.getTygiaquydoi()%>'><%= t.getMa() %></option>
										<% } } } %>
									</Select>
								</TD>
							</TR>
							
							<%if(dmhBean.getLoai().equals("2")) { // Mua CP/TS/CCDC mới chọn loại hh %> 
							<TR>
								<TD class="plainlabel">Loại hàng hóa</TD>
								<TD class="plainlabel">
									<% if(dmhBean.getCheckedNoiBo().equalsIgnoreCase("1")){ %>
										<select name="loaihh" id="loaihh" onChange="changeNoiDung();">		
										<option value="2"  selected="selected">Chi phí dịch vụ</option>
										</select>
									<%} else{ %>
									<select name="loaihh" id="loaihh" onChange="changeNoiDung();">
									<% if(dmhBean.getLoaihanghoa().equals("1")) { %>
										<option value="1" selected="selected">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3">Công cụ dụng cụ</option>
									<% } else if(dmhBean.getLoaihanghoa().equals("2")){ %>
										<option value="2"  selected="selected">Chi phí dịch vụ</option>
										<option value="1">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="3">Công cụ dụng cụ</option>
									<% } else  if(dmhBean.getLoaihanghoa().equals("0")){  %> 
										<option value="0" selected="selected">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3">Công cụ dụng cụ</option>
									<% } else if(dmhBean.getLoaihanghoa().equals("3")){%>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3"  selected="selected">Công cụ dụng cụ</option>
									<%} else {%>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3" >Công cụ dụng cụ</option>									
									
									<%} %>
									</select>
									<%} %>							
									
									
								</TD>
								
								<% if(dmhBean.getLoaihanghoa().equals("0")) { %>
									<TD class="plainlabel"></TD>
									<TD class="plainlabel">									
										<select name="loaisp" id="loaisp" style=" display: none" onChange="submitform();">
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
										
									</TD>
								<%} else { %>
									<td class="plainlabel" colspan="2">&nbsp;</td>
								<% } %>
							</TR>
							<%}else{ %>
							<TR>
								<TD style="display: none"><input type="hidden" name="loaihh" id="loaihh" value ="0" ></TD>
							</TR>
							<%} %>
							
							<TR>
								<TD class="plainlabel">Tổng tiền chưa VAT</TD>
								<TD class="plainlabel"><input type="text" name="BVAT" id="BVAT" value="<%=dmhBean.getTongtienchuaVat()%>" style="text-align: right;"
									readonly="readonly"> </TD>
								<TD class="plainlabel">VAT</TD>
								<TD class="plainlabel"><input type="hidden" name="VAT" id="VAT" value="0" style="text-align: right;"
									onkeypress="return keypress(event);"> %</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Tổng tiền sau VAT</TD>
								<TD class="plainlabel"><input type="text" name="AVAT" id="AVAT" value="<%=dmhBean.getTongtiensauVat()%>" style="text-align: right;"
									readonly="readonly"> </TD>
								<TD class="plainlabel">Dung sai (+/-)</TD>
								<TD class="plainlabel"><input type="text" name="dungsai" id="dungsai" value="<%=dmhBean.getDungsai()%>" style="text-align: right;"
									onkeypress="return keypress(event);"> %</TD>
							</TR>
							<%if(dmhBean.getLoai().equals("0")) { // Mua hàng nhập khẩu mới chọn loại%>
								<tr>
									<td class="plainlabel">Tổng tiền Nguyên Tệ chưa VAT</td>
										<td class="plainlabel"><input type="text" name="BUSDVAT" id="BUSDVAT" readonly="readonly" style="text-align: right;">
										</td>
									<td class="plainlabel">Tổng tiền Nguyên Tệ sau VAT</td>
										<td class="plainlabel"><input type="text" name="AUSDVAT" id="AUSDVAT" readonly="readonly" style="text-align: right;">
										</td>
								</tr>
							<%}%>
							<TR>
								<TD class="plainlabel">Tổng số lượng</TD>
								<TD class="plainlabel" ><input type="text" name="tongluong" id="tongluong" value="" style="text-align: right;"
									readonly="readonly"> </TD>
								<TD class="plainlabel">Hình thức thanh toán</TD>
								<TD class="plainlabel" >
									<input type="text" name="hinhthucthanhtoan" id="hinhthucthanhtoan" value="<%= dmhBean.getHinhThucTT() %>" style="text-align: left;">
									 Địa điểm giao hàng 
									<% String ddgh = ""; try { ddgh = dmhBean.getDiaDiemGiaoHang(); } catch(Exception e) { } %>
									<input type="text" name="diadiemgiaohang" id="diadiemgiaohang" value="<%= ddgh %>" style="text-align: left;" placeholder="(tùy chọn)">
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Diễn giải</TD>
								<TD class="plainlabel" >
								
									<a href="" id="noidungGhiChu" rel="ndGhiChu">
	           	 							&nbsp; <img alt="Ghi chú" src="../images/vitriluu.png"></a>
	           	 		
                          			<DIV id="ndGhiChu" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    	<table width="100%" align="center">
				                        <tr>
				                            <th width="300px">Ghi chú</th>
				                        </tr>
		                            	
		                            	<%
		                            		String[] ghichuArr = dmhBean.getGhiChuArr();
		                            		int sodong = 0;
		                            		if(ghichuArr != null)
		                            		{
		                            			for(int i = 0; i < ghichuArr.length; i++)
		                            			{
		                            				%>
		                            				<Tr>
					                            		<td><input type="text" name="ghichu" value="<%= ghichuArr[i] %>" style="width: 100%" /></td>
					                            	</Tr>
		                            			<% sodong++; }
		                            		}
		                            		
		                            		while(sodong < 5)
		                            		{
		                            			%>
		                            			
		                            			<Tr>
				                            		<td><input type="text" name="ghichu" value="" style="width: 100%" /></td>
				                            	</Tr>
		                            			
		                            		<% sodong++; }
		                            	%>
		                            	
				                    	</table>
					                     <div align="right">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('ndGhiChu')" >Hoàn tất</a>
					                     </div>
				            		</DIV>   
									
								</TD>
								<TD class="plainlabel">Số tham chiếu</TD>
								<TD class="plainlabel" ><input type="text" name="sothamchieu" id="sothamchieu" value="<%= dmhBean.getSoThamChieu() %>" style="text-align: left;"></TD>
							</TR>
							<tr>
							<TD class="plainlabel">Thời hạn thanh toán </TD>
								<TD class="plainlabel" >
								
									<a href="" id="thoiHanThanhToan" rel="thThanhToan">
	           	 							&nbsp; <img alt="Thời Hạn Thanh Toán " src="../images/vitriluu.png"></a>
	           	 		
                          			<DIV id="thThanhToan" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    	<table width="100%" align="center">
				                        <tr>
				                        	<th width="30%">Ngày Thanh Toán</th>
				                        	<th width ="20%">Phần trăm </th>
				                        	<th width ="50%">Số Tiền </th>
				                        </tr>
		                            	
		                            	<%
		                            		ResultSet thtt = dmhBean.getThttRs();
		                            		int dong = 0;
		                            		if(thtt != null)
		                            		{
		                            			while (thtt.next())
		                            			{
		                            				%>
		                            				<Tr>
					                            		<td><input type="text" class="days" name="ngayThanhToan" value="<%= thtt.getString("NGAYTHANHTOAN") %>" style="width: 100px" readonly="readonly"/></td>
					                            		<td><input type="text" name="phanTramThanhToan" value="<%= thtt.getString("PHANTRAMTHANHTOAN") %>" style="width: 70px" onchange="capNhatSoTienThanhToan()"  readonly="readonly"/></td>
					                            		<td><input type="text" name="soTienThanhToan" value="<%= thtt.getLong("SOTIEN") %>" style="width:160px;text-align: right;" onchange="capNhatPhanTramThanhToan()" readonly="readonly" /></td>
					                            	</Tr>
		                            			<% dong++; }
		                            		}
		                            			
		                            		while(dong < 5)
		                            		{
		                            			%>
		                            			
		                            			<tr>
				                            		<td><input type="text" class="days" name="ngayThanhToan" value="" style="width: 100px"  readonly="readonly"/></td>
				                            		<td><input type="text" name="phanTramThanhToan" value="" style="width: 70px" onchange="capNhatSoTienThanhToan()" readonly="readonly"/></td>
				                            		<td><input type="text" name="soTienThanhToan" value="" style="width:160px;text-align: right;" onchange="capNhatPhanTramThanhToan()" readonly="readonly"/></td>
				                            	</tr>
		                            			
		                            		<% dong++; }
		                            	%>
		                            
				                    	</table>
					                     <div align="right">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('thThanhToan')" >Hoàn tất</a>
					                     </div>
				            		</DIV>   
									
								</TD>
								
								<td class="plainlabel" valign="middle" > Loại gia công</td>
								<td class="plainlabel" valign="middle">
									<% if(dmhBean.getIsGiaCong().equals("1")) {%>
										 <input type="checkbox" name="isgiacong" value="1" checked>&nbsp;&nbsp;&nbsp;&nbsp;
									<%} else { %>
										 <input type="checkbox" name="isgiacong" value="1" >&nbsp;&nbsp;&nbsp;&nbsp;
									<%} %>
									
									
									<% if(dmhBean.getIsGiaCong().equals("1") ) {%>
									 	Ngày YC thêm  
									   <input type="text" value="<%=dmhBean.getNgayYCThemNL()%>" onchange="reload_Danhmucvattu();"  name="ngayycthem" class ="days">
		                          
									<%} %>
								</td>
								
								
							
								
							</tr>
							
							<!-- Bổ sung phần người duyệt -->
							<tr>
							<TD class="plainlabel">Người duyệt </TD>
								<TD class="plainlabel"  >
								
									<a href="" id="nguoiDuyet" rel="thNguoiDuyet">
	           	 							&nbsp; <img alt="Thời Hạn Thanh Toán " src="../images/vitriluu.png"></a>
	           	 		
                          			<DIV id="thNguoiDuyet" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 350px; min-height:150px; overflow:auto; padding: 4px;">
				                    	<table width="100%" align="center">
				                        <tr>
				                        	<th width="50%">Chức vụ </th>
				                        	<th width ="40%">Người duyệt</th>
				                        	<th width ="10%">Trạng thái</th>
				                        </tr>
		                            	
		                            	<%
		                            		ResultSet nguoiduyetRs = dmhBean.getNguoiDuyetRs();
		                      
		                            		if(nguoiduyetRs != null)
		                            		{
		                            			while (nguoiduyetRs.next())
		                            			{
		                            				%>
		                            				<Tr>
					                            		<td><input type="text" value="<%= nguoiduyetRs.getString("DIENGIAI") %>" style="width: 130px" /></td>
					                            		<td><input type="text" value="<%= nguoiduyetRs.getString("TEN") %>" style="width: 130px" /></td>
					                            		
					                            		<% String trangThaiDuyet = "";
					                            			if(nguoiduyetRs.getString("TRANGTHAI").equals("1")){
					                            				trangThaiDuyet = "Đã duyệt";
					                            			} else if (nguoiduyetRs.getString("TRANGTHAI").equals("0")){
					                            				trangThaiDuyet = "Chưa duyệt";
					                            			} 
					                            		%>
					                            		<td><input type="text" value="<%= trangThaiDuyet %>" 
					                            		style="width:70px;text-align: right;" /></td>
					                            	</Tr>
		                            			<% }
		                            			nguoiduyetRs.close();
		                            		}
		                            	%>
		                            
				                    	</table>
					                     <div align="right">
					                     	<label style="color:red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('thNguoiDuyet')" >Hoàn tất</a>
					                     </div>
				            		</DIV>   
									
								</TD>
								<td class="plainlabel" >
								</td>
								<td class="plainlabel">
							 
								<% if(dmhBean.getIsGiaCong().equals("1") && !dmhBean.getDaYeucauNguyenlieu().equals("1")) {%>
								 
                                <a class="button2" href="javascript:Yeucaunguyenlieu()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Tạo yêu cầu nguyên liệu </a>&nbsp;&nbsp;&nbsp;&nbsp;
 								<%} %>
								 	<% if(dmhBean.getIsGiaCong().equals("1") &&  dmhBean.getDaYeucauNguyenlieu().equals("1")) {%>
								 
                                <a class="button2" href="javascript:Yeucaunguyenlieuthem()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Tạo yêu cầu nguyên liệu  thêm</a>&nbsp;&nbsp;&nbsp;&nbsp;
 								<%} %>
								</td>
								
							</tr>
							 <!-- bổ sung thêm trường NCC -->
							 <%if(dmhBean.getLoai().equals("0")) { // Mua hàng nhập khẩu mới chọn loại%>
							 <tr>
								<TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
		                        <TD class="plainlabel" valign="middle" colspan="3">
		                            <select name="nhacungcapNK"  id="nhacungcapNK" onchange="submitform()" style="width: 200px">
		                            	<option value=""></option>
		                            	<%
			                        		if(nccRs != null)
			                        		{
			                        			while(nccRs.next())
			                        			{  
			                        			if( nccRs.getString("pk_seq").equals(dmhBean.getNhacungcapNK())){ %>
			                        				<option value="<%= nccRs.getString("pk_seq") %>" selected="selected" ><%= nccRs.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= nccRs.getString("pk_seq") %>" ><%= nccRs.getString("ten") %></option>
			                        		 <% } } nccRs.close();
			                        		}
			                        	%>
		                            </select>
		                        </TD>         
		                        </tr>  
		                        <% } %>
							<%-- <TR style="<%= dmhBean.getNguonGocHH().equals("NN") ? "" : "display: none;" %>" >
								<TD class="plainlabel" valign="top" width="150px">ETD</TD>
								<TD class="plainlabel" valign="top">
									<input type="text" class="days" id="ETD" name="ETD"
											value="<%= dmhBean.getETD() %>" maxlength="10" readonly />
								</TD>
								<TD class="plainlabel" valign="top" width="150px">ETA</TD>
								<TD class="plainlabel" valign="top">
									<input type="text" class="days" id="ETA" name="ETA"
											value="<%= dmhBean.getETA() %>" maxlength="10" readonly />
								</TD>
							</TR> --%>
							
							<tr>
								<td class="plainlabel" valign="middle" > Ghi chú</td>
								<td class="plainlabel" valign="middle" colspan="3">
									 <input type="text" name="ghichuGC" value="<%= dmhBean.getGhiChuGC() %>" ><br>
								</td>
								
							</tr>
							
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
									<TH align="center" width="15%">Tên hàng mua</TH>
								<% } else { if(dmhBean.getLoaihanghoa().trim().equals("1")) { %> 
									<TH align="center" width="10%" >Mã tài sản</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								<% } else if(dmhBean.getLoaihanghoa().trim().equals("2")){ %>
									<TH align="center" width="10%" >Mã chi phí</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								 <% } else if(dmhBean.getLoaihanghoa().trim().equals("3")){%>
								 	<TH align="center" width="10%" >Mã CCDC</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								 <%} } %>
								
								
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="5%">Số lượng</TH>
								<%if(dmhBean.getLoai().equals("0")) { // Mua hàng nhập khẩu mới chọn loại%>
								<TH align="center" width="5%">
								<table>
									<tr>
										<th  colspan="2">Đơn giá</th>
									</tr>
									<tr>
										<th>Nguyên Tệ</th>
										<th>VND</th>
								</table>
								</TH>	
								<%} else { %>
								<TH align="center" width="5%">Đơn giá</TH>
								<%} %>	
								<TH align="center" width="3%">Thuế suất</TH>
									<TH align="center" width="5%">Không VAT</TH>
								<TH align="center" width="7%">Thành tiền</TH>
								<TH align="center" width="5%">Ngày nhận</TH>
								
								<% if (dmhBean.getNguonGocHH().equals("NN"))
								  { %>
										<TH align="center" width="5%" style="display: none">% TNK</TH>
										<TH align="center" width="5%" style="display: none">Thuế NK</TH>
								<% } else { %>
										<TH align="center" width="5%" style="display: none">% TNK</TH>
										<TH align="center" width="5%" style="display: none">Thuế NK</TH>
								<% } %>

								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
									<TH align="center" width="10%" style="display: none;" >Nhóm hàng</TH>
								<% } else { if(dmhBean.getLoaihanghoa().trim().equals("1")) { %> 
									<TH align="center" width="10%" style="display: none;">Nhóm tài sản</TH>
								<% } else { %>
									<TH align="center" width="10%" style="display: none;">Nhóm chi phí</TH>
								 <% } } %>
								
								<% if ( dmhBean.getLoai().equals("2")){ %>
								<TH align="center" width="5%">Ngày nhận</TH>
								<%} %>
								
								<% if ( dmhBean.getLoai().equals("2") && dmhBean.getLoaihanghoa().trim().equals("0") ){ %>
									<TH align="center" width="5%" >Kho nhận</TH>
								<% } else { %>
									<TH align="center" width="5%" style="display: none" >Kho nhận</TH>
								<% } %>
								
								<%if(dmhBean.getLoaihanghoa().equals("2")){ %>
								<TH align="center" width="5%" >Sản phẩm</TH>
								<%}else{ %>
								<TH align="center" width="5%" >Tên in PO</TH>
								<%} %>
								
								<% if(dmhBean.getIsGiaCong().equals("1")) {%>
									<TH align="center" width="5%" > Chọn BOM</TH>
									 
								<%} %>
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
												<input type="hidden" value="<%=  sp.getIdmarquette() %>"  name="idmarquette" >
											</TD>
											<TD align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%" value="<%= sp.getMasanpham() %>"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
											</TD>
											<TD align="left" width="10%">
												<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { System.out.println("_____TEN SP: " + sp.getTensanpham()); %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" readonly="readonly" > 
												<% } else {  %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" > 
												<%  } %>
											</TD>
											<TD align="center" width="6%">
												<input type="hidden" value="<%= sp.getDonvitinh() %>" name="donvitinh" style="width: 100%" > 
												
												<select style="width: 100%" name="donvitinh" disabled id="<%= "donvitinh_"+i %>" style=" width: 200px">
															<option value=""></option>
														<% if (dvList.size() > 0)
															{
																for (int j = 0; j < dvList.size(); j++)
																{
																	IDonvi donvi =  dvList.get(j);
																	
																	if(donvi.getId().equals(sp.getDonvitinh())){
																%>
																	<option value="<%=donvi.getId()%>" selected="selected"><%=donvi.getDonvi()%></option>
																<% } else { %>
																	<option value="<%=donvi.getId()%>"><%=donvi.getDonvi()%></option>
																 <% } } }  %>
												</select>
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getSoluong() %>" name="soluong" style="width: 100%; text-align: right;" > 
												<input type="hidden" value="<%= sp.getSoluongOLD() %>" name="soluongOld" style="width: 100%; text-align: right;" >
											</TD>
											<TD align="center" width="8%">
											
											<% if (dmhBean.getLoai().equals("1") ){ // trong nước %> 
												<input type="text" value="<%= sp.getDongia() %>" name="dongia" style="width: 100%; text-align: right;" readonly = "readonly" >
											<%} else {%>
												<table>
												<tr>
												<td>
												<input type="text" value="<%= sp.getDongia() %>" name="dongia" style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)" >
											
												<input type="hidden" value="<%= sp.getDongiaOLD() %>" name="dongiaOld" style="width: 100%; text-align: right;" ></td>
												<td><input type="text" name="donGiaUSD" style="width:100%;text-align:right" readonly="readonly"></td></tr></table>
											<%} %> 
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%=sp.getThuexuat() %>" name="thuexuat" id="thuexuat<%=i%>" style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="7%">

											<%
												if(sp.getIsmienthue().equals("1"))
												{%>
														<input type="checkbox" value="<%=sp.getPK_SEQ() %>" checked="checked" name="mienthue" id="mienthue<%=i%>" style="width: 100%; text-align: right;" onclick="tinhthuexuat('mienthue<%=i%>','thuexuat<%=i%>');" > 
	
												<%}
												else
												{%>
													<input type="checkbox" value="<%=sp.getPK_SEQ() %>" name="mienthue" id="mienthue<%=i%>" style="width: 100%; text-align: right;" onclick="tinhthuexuat('mienthue<%=i%>','thuexuat<%=i%>');" > 
													
												<%}
											%> 
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getThanhtien() %>"  name="thanhtien" style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getNgaynhandukien() %>"  name="ngaynhandukien" style="width: 100%; text-align: right;" class="days" > 
											</TD>
											
											<% if (dmhBean.getNguonGocHH().equals("NN"))
											  { %>
													<TD align="center" width="5%" style="display: none">
														<input type="text" value="<%= sp.getPhanTramThue() %>" style="width: 100%; text-align: right;" name="phantramthue" > 
													</TD>
													<TD align="center" width="5%" style="display: none">
														<input type="text" value="<%= sp.getThueNhapKhau() %>" style="width: 100%; text-align: right;" name="thuenhapkhau" > 
													</TD>
											<% } else { %>
													<TD align="center" width="5%" style="display: none">
														<input type="text" value="" style="width: 100%" name="phantramthue" > 
													</TD>
													<TD align="center" width="5%" style="display: none">
														<input type="text" value="" style="width: 100%" name="thuenhapkhau" > 
													</TD>
											<% } %>
											
											<TD align="center" width="8%" style="display: none;" >
												<input type="text" value="<%= sp.getNhomhang() %>" style="width: 100%" name="nhomhang" readonly="readonly" > 
											</TD>
											
											<% if ( dmhBean.getLoai().equals("2")){  %>
											
											<td align="center">
			           	 						<a href="" id="ngaynhan_<%= i %>" rel="subcontent_NN<%= i %>">
				           	 							<img alt="Ngày nhận" src="../images/vitriluu.png"></a>
				           	 					<DIV id="subcontent_NN<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
							                             background-color: white; width: 250px; padding: 4px;">
							                    	<table width="90%" align="left" id= "nn_<%= i %>">
								                        <tr>
								                            <th width="150px">Ngày nhận</th>
								                            <th width="100px">Số lượng</th>
								                        </tr>
								                        <%
								                        List<INgaynhan> nn = sp.getNgaynhan();
								                        for(int j=0;j<nn.size();j++){ %>
					                        			<tr>
								                            <td><input type="text" size="150px" name="sub_ngaynhan_<%= i %>" value="<%= nn.get(j).getNgay() %>" class="days"/></td>
								                            <td><input type="text" size="100px" name="sub_soluong_<%= i %>" value="<%= nn.get(j).getSoluong() %>"/></td>
								                        </tr>
								                        <%}%>
								                    </table>
								                     <div align="right">
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent_NN<%= i %>')">Hoàn tất</a>
								                     </div>
							               	 	</DIV>
			           	 					</td>
											 <%}else{ %>										
					                           <td style="display: none">
				                            	<input type="hidden" name="sub_ngaynhan_<%= i %>" value=""/>
				                            	<input type="hidden" name="sub_soluong_<%= i %>" value=""/>
				                           	   </td>	
											<%} %>
										
											<% if (dmhBean.getLoai().equals("2") && dmhBean.getLoaihanghoa().trim().equals("0") )
											   { %>
												<TD align="center" width="8%" >
												<% if( dmhBean.getKhoId().trim().length() > 0 ) { %>	
														
													<% 
														for (int j = 0; j < khoList.size(); j++)
														{
															IKho kho = (Kho) khoList.get(j);
															
															if(kho.getId().equals(dmhBean.getKhoId())){
														%>
															<input type="text" value="<%= kho.getMakho() %>" readonly="readonly" >
															<input type="hidden" name="khonhan" value="<%= kho.getId() %>"  >
														<% } } %>
															
												 <% } else { %>
												 	
												 		<select style="width: 100%" name="khonhan">
															<option value=""></option>
															<% if (khoList.size() > 0)
																{
																	for (int j = 0; j < khoList.size(); j++)
																	{
																		IKho kho = (Kho) khoList.get(j);
																		
																		if(kho.getId().equals(sp.getKhonhan())){
																	%>
																		<option value="<%=kho.getId()%>" selected="selected"><%=kho.getMakho()%></option>
																	<% } else { %>
																		<option value="<%=kho.getId()%>"><%=kho.getMakho()%></option>
																	 <% } } }  %>
														</select>
												 	
												 <% } %>
												</TD>
											<% } else { %>
												<TD align="center" width="8%" style="display: none" >
													<input type="hidden" value=""  name="khonhan"  > 
												</TD>
											<% } %>
											
											<%if(dmhBean.getLoaihanghoa().equals("2")){ // MUA CHI PHI >> HIEN DS SP DE CHON PHAN BO%>
												<td align="center">
			           	 						<a href="" id="sanpham_<%= i %>" rel="subcontent_SP<%= i %>">
				           	 							<img alt="Sản phẩm" src="../images/vitriluu.png"></a>
				           	 					<DIV id="subcontent_SP<%= i %>"style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width:  450px; padding: 4px; max-height: 200px; overflow: auto;">
							                    	<table width="90%" align="center" id= "nn_<%= i %>">
								                        <tr>
								                            <th width="150px">Mã sản phẩm</th>
								                            <th width="150px">Tên sản phẩm</th>
								                            <th width="100px">Chọn</th>
								                        </tr>
								                        <%
								                        List<ISanPhamPhanBo> nn = sp.getSanphamPB();
								                        for(int j=0;j<nn.size();j++){ %>
					                        			<tr>
								                            <td>
								                            	<input type="hidden" name="sub_spId_<%= i %>" value="<%= nn.get(j).getSpId() %>" />
								                            	<input type="text" size="150px" name="sub_spMa_<%= i %>" value="<%= nn.get(j).getSpMa() %>" />
								                            </td>
								                            <td>								                            	
								                            	<input type="text" size="150px" name="sub_spTen_<%= i %>" value="<%= nn.get(j).getSpTen()  %>" />
								                            </td>
								                            <td align="center">
								                              <%if(nn.get(j).getSpId().equals(nn.get(j).getChon())){ %>
								                            	<input type="checkbox" size="100px" checked="checked" name="sub_spChon_<%= i %>" value="<%=nn.get(j).getSpId() %>" />
								                            <%}else{ %>
								                            	<input type="checkbox" size="100px" name="sub_spChon_<%= i %>" value="<%=nn.get(j).getSpId() %>"/>
								                            <%} %>
								                            </td>
								                        </tr>
								                        <%} %>
					                        			
								                    </table>
								                     <div align="center">
								                     &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent_SP<%= i %>')">Hoàn tất</a>
								                     </div>
							               	 	</DIV>
			           	 					</td>
											<%}else{ %>
											
											<td align="center" >
				           	 					<a href="" id="tenhd_<%= count %>" rel="subcontent<%= count %>">
					           	 							<img alt="Tên sản phẩm hóa đơn" src="../images/vitriluu.png"></a>
					           	 				<DIV id="subcontent<%= count %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 450px; padding: 4px;">
								                    <table width="90%" align="center">
								                        <tr>
								                            <th width="100px">Tên in PO</th>
								                        </tr>
					                        			<tr>
								                            <td>
								                            	<input type="text" size="100px" name="tenhoadon" value="<%= sp.getTenHD() %>" onchange="replaces()"/>
								                            </td>
								                        </tr>
								                    </table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= count %>')">Hoàn tất</a>
								                     </div>
								                </DIV>
				           	 				</td>
				           	 				<%} %>
				           	 				
				           	 				<% if(dmhBean.getIsGiaCong().equals("1")) {%>
				           	 				
				           	 				<td>
				           	 					<select name="bomchon" onchange="reload_Danhmucvattu()" > 
				           	 				<!-- 	<option value=""> </option> -->
				           	 					<% ResultSet rsbom=sp.getRsBom(); 
				           	 					
				           	 					  System.out.println(" bom id jsp: "+sp.getBomId() );
				           	 						while (rsbom.next()){
				           	 							if(sp.getBomId().equals(rsbom.getString("pk_Seq"))){
				           	 								%> 
				           	 								<option selected="selected" value="<%=rsbom.getString("PK_SEQ")%>"><%= rsbom.getString("TEN")%> </option>
				           	 								<%
				           	 							}else{
					           	 							%> 
				           	 								<option value="<%=rsbom.getString("PK_SEQ")%>"><%= rsbom.getString("TEN")%> </option>
				           	 								<%
				           	 							}
				           	 						}
				           	 						rsbom.close();
				           	 					%>
				           	 					</select>
				           	 				</td> 
				           	 				</tr>
				           	 				
				           	 				<tr>
				           	 				 
												<td align="center"  colspan="20"> 
													
													 
								           	 					<DIV  >
											                    <table width="100%" align="center" class="tabledetail" border="1">
											                        <tr style="color:blue;font-weight: bold;"  >
											                            <th width="20%" align="left">Mã </th>
											                            <th width="30%" align="left">Tên</th>
											                            <th width="10%" align="left">Đơn vị</th>
								 										<th width="10%" align="center">Số lượng chuẩn</th>
								 										<th width="10%" align="center">Số lượng đề nghị</th>
								 										<% if(dmhBean.getDaYeucauNguyenlieu().equals("1")){%>
								 										<th width="5%" >Đã YC</th>
								 										<%} %>
								 										<th width="5%" >Chọn  lô</th>
								 										
											                        </tr>
											                        <%
											                        List<ISanphamBom> spConList = sp.getSpListBom();
											                        	int stt = 0; 
											                        	 String bomid="";
											                        	if(spConList.size() > 0)
											                        	{
											                        		for(  stt = 0; stt < spConList.size(); stt ++)
											                        		{
											                        			ISanphamBom spCon = spConList.get(stt);
											                        			bomid=spCon.getDMVTId();
											                        		%>
											                        			<tr>
														                            <td> 
														                            	<input name="<%=i + "masp" %>" type="text" style="width: 100%" 
														                            		value="<%= spCon.getMasanpham() %>" onkeyup="ajax_showOptions(this,'abc',event)" autocomplete='off'  />
														                            	<input name="<%=i + "bomid" %>" type="hidden"   value="<%= bomid%>" />
														                            	<input name="<%=i + "idsp"%>"  type="hidden"   value="<%= spCon.getSpId()%>" /> 
														                            	 
														                            </td>
														                            <td> 
														                            	<input name="<%= i  + "tensp"%>" 
														                            	type="text" style="width: 100%" value="<%= spCon.gettensanpham() %>" readonly="readonly" /> 
														                            	<input name="<%= i  + "ishamam"%>" 
														                            	type="hidden" style="width: 100%" value="<%= spCon.getIsHamam() %>" readonly="readonly" />
														                            	<input name="<%= i  + "ishamluong"%>" 
														                            	type="hidden" style="width: 100%" value="<%= spCon.getIsHamluong() %>" readonly="readonly" />
														                            	<input name="<%= i  + "hamam"%>" 
														                            	type="hidden" style="width: 100%" value="<%= spCon.getHamam() %>" readonly="readonly" />
														                            	<input name="<%= i  + "hamluong"%>" 
														                            	type="hidden" style="width: 100%" value="<%= spCon.getHamluong() %>" readonly="readonly" />
														                            	 
														                            	
														                            </td>
														                            <td> 
														                            	<input name="<%= i  + "donvi"%>" 
														                            	type="text" style="width: 100%" value="<%= spCon.getDonVi() %>" readonly="readonly" /> 
														                            </td>
														                            
														                            <td>
														                            	<input name="<%=i + "soluongdenghi" %>" 
														                            	type="text" style="width: 100%" value="<%= spCon.getSoluongdenghi()%>" readonly="readonly" />
														                            </td>
														                             
														                             <% // xu ly format so luong 1.000=1
																						String s= formatter_3sole.format(Double.parseDouble( spCon.getSoluongnhap().replaceAll(",",""))) ;
																						if(s!=null){
																							if(s.length()>0){
																								String []tempt=s.split("\\.");
																								System.out.println(" tempt lengt: "+ tempt.length);
																								if(tempt.length>=2){
																									if( Double.parseDouble(tempt[1]) ==0 ){
																										s=tempt[0];
																									}
																									
																									if(tempt[1].length()==2){
																										tempt[1]=tempt[1]+"0";
																										s=tempt[0]+"."+ tempt[1];
																									}
																									
																									if(tempt[1].length()==1){
																										tempt[1]=tempt[1]+"00";
																										s=tempt[0]+"."+ tempt[1];
																									}
																								}
																							}
																						} %>
														                             
														                             
														                             
														                         <%--    <td>
														                            	<input name="<%= i + "soluongnhap" %>" 
														                            	type="text" style="width: 100%" value="<%= spCon.getSoluongnhap()%>" onkeypress="return keypress(event);" /> 
														                            </td> --%>
														                            
														                            <td>
														                            	<input name="<%= i + "soluongnhap" %>" 
														                            	type="text" style="width: 100%" value="<%=s%>" onkeypress="return keypress(event);" /> 
														                            </td>
														                            
														                            
														                            <td align="right"> 
																	                              	
																	    <a href="" id="<%=i+"__"+stt%>pubup_ton" rel="subcontenta<%=i+"__"+stt%>">
							           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
							           	 							<DIV id="subcontenta<%=i+"__"+stt%>" style=" position:absolute; visibility: hidden; border: 1px solid #80CB9B;
										                             background-color: white; max-height:300px;overflow:auto ; width:1000px; padding:1px;">
										                 		   <table width="100%" align="center" class="tabledetail">
										                        		<tr align="right" class="plainlabel" >
										                        		
											                        		<th width="140px">Tên kho</th>
											                        		<th width="100px">MARQUETTE</th>
											                        		<th width="140px">Mã SP</th>
											                        		<th width="200px">Số lô</th>
											                        		<th width="100px">Phiếu kiểm nghiệm </th>
											                        		<th width="100px">Phiếu định tính</th>
											                        		<th width="100px">Phiếu EO</th>
											                            	<th width="50px">Mã thùng</th>
											                            	<th width="50px">Mã mẻ</th>
											                            	<th width="120px">Ngày hết hạn</th>
											                            		<th width="120px">Ngày nhập kho</th>
											                            	<th style="display: none" width="100px">Khu vực</th>
											                            		<th width="100px">Vị trí</th>
											                            		
											                            	<th width="50px">Hàm ẩm</th>
											                            	<th width="50px">Hàm lượng </th>	
											                                <th width="100px">Số lượng tồn </th>		
											                            	 <th width="100px">Số lượng tồn quy đổi </th>
											                            	 <th width="100px">Số lượng đề xuất </th>	                      
										                       		 </tr>
										                       		 <%
										                       		  
										                        		List<ISpSanxuatChitiet> list_ct_daYC=spCon.getListCtSP_daYC();
										                        		
										                        			System.out.println(" size chi tiet vat tu jsp: "+ list_ct_daYC.size());
										                        			for(int t=0;t<list_ct_daYC.size();t++){
										                        				
										                        				ISpSanxuatChitiet sp1=list_ct_daYC.get(t);
										                        			
										                        				
										                        				if(sp1.getIdSpThayThe().length() >0){
										                        				%>
											                        			<tr class='tbdarkrow'>
											                        			<%}else {%>
											                        			<tr class='tblightrow'>
											                        			<%} %>
													                         <td>
													                         	<input type="hidden" size="200px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_khoid"  value="<%=sp1.getKhoId()%>" readonly="readonly" />
													                         	<input type="hidden" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_spId"  value="<%=sp1.getIdSp()%>" readonly="readonly" />
													                         	<input type="hidden" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_spIdThayThe"  value="<%=sp1.getIdSpThayThe()%>" readonly="readonly" />
													                         	<input type="hidden" size="200px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_binid_dayc"  value="<%=sp1.getBinId()%>" readonly="readonly" />
													                         	
													                         		<input type="hidden" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_KhuvucId"  value="<%=sp1.getkhuvuckhoId()%>" readonly="readonly" />
													                         		
													                         		<input type="hidden" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MARQUETTE_FK"  value="<%=sp1.getMARQUETTE_FK()%>" readonly="readonly" />
													                         		   
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_khoten"  value="<%=sp1.getKhoTen()%>" readonly="readonly" /></td>
													                            	 <td>
													                            	<input type="text" size="100px"  name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MARQUETTE"  value="<%=sp1.getMARQUETTE() %>" readonly="readonly" /></td>
													                            	 <td>
													                            	<input type="text" size="100px" style="background-color: <%=(!sp1.getIdSpThayThe().equals(sp1.getIdSp())?"blue":"") %> " name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MASP" value="<%= sp1.getMaSp() %>" readonly="readonly" /></td>
													                            	
													                            	<td>
													                            	<input type="text" size="200px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_SOLO" value="<%=sp1.getSolo() %>" readonly="readonly" /></td>
													                            	
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MAPHIEU" value="<%=sp1.getMAPHIEU() %>" readonly="readonly" /></td>
													                            	
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MAPHIEU_TINHTINH" value="<%=sp1.getMAPHIEU_TINHTINH() %>" readonly="readonly" /></td>
													                            	
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MAPHIEU_EO" value="<%=sp1.getMAPHIEU_EO() %>" readonly="readonly" /></td>
													                            	
													                            
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MATHUNG" value="<%=sp1.getMATHUNG() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_MAME" value="<%=sp1.getMAME() %>" readonly="readonly" />
													                            	</td>
													                            	
													                            	<td>
													                            	<input type="text" size="150px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_NGAYHETHAN" value="<%=sp1.getNGAYHETHAN() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
													                            	<input type="text" size="150px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_NGAYNHAPKHO" value="<%=sp1.getNGAYNHAPKHO() %>" readonly="readonly" />
													                            	</td>
													                            	
													                            	
													                            	<td style="display: none">
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_khuvuckhoTen" value="<%=sp1.getkhuvuckhoTen() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
														                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_bin_dayc" value="<%=sp1.getBin() %>" readonly="readonly" />
														                            	</td>
										                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_HAMAM" value="<%=sp1.getHAMAM() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_HALUONG" value="<%=sp1.getHAMLUONG() %>" readonly="readonly" />
													                            	</td>
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_Soluongton" value="<%=sp1.getSoluongton() %>" readonly="readonly" />
													                            	</td>
													                            		<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_Soluongtonthute" value="<%=sp1.getSoluongtonthute() %>" readonly="readonly" />
													                            	</td>
													                            	 	<td>
													                            	<input type="text" size="100px" name="<%= i+"__"+stt+""+sp.getPK_SEQ()%>_Soluongdexuat" value="<%=sp1.getSoluong() %>"   />
													                            	</td>
													                            	 
													                          		   
													                          		 </tr>
													                        		<% 
													                        		}
													                       			 %>
													                   			 </table>
													                    		 <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontenta<%= i+"__"+stt%>');">Hoàn tất</a></div>
													              			  </DIV>
														                              	
														                            </td>
														                        
														                          <td align="right"> 
																	                              	
																	    <a href="" id="<%=i+"_"+stt%>pubup_ton" rel="subcontenta<%=i+"_"+stt%>">
							           	 							<img alt="Số lô - Vị trí hàng hóa xuất" src="../images/vitriluu.png"></a>
							           	 							<DIV id="subcontenta<%=i+"_"+stt%>" style=" position:absolute; visibility: hidden; border: 1px solid #80CB9B;
										                             background-color: white; max-height:300px;overflow:auto ; width:1000px; padding:1px;">
										                 		   <table width="100%" align="center" class="tabledetail">
										                        		<tr class="plainlabel" >
										                        		
											                        		<th width="140px">Tên kho</th>
											                        		<th width="100px">MARQUETTE</th>
											                        		<th width="140px">Mã SP</th>
											                        		<th width="200px">Số lô</th>
											                        		<th width="100px">Phiếu kiểm nghiệm </th>
											                        		<th width="100px">Phiếu định tính</th>
											                        		<th width="100px">Phiếu EO</th>
											                            	<th width="50px">Mã thùng</th>
											                            	<th width="50px">Mã mẻ</th>
											                            	<th width="120px">Ngày hết hạn</th>
											                            		<th width="120px">Ngày nhập kho</th>
											                            	<th style="display: none" width="100px">Khu vực</th>
											                            		<th width="100px">Vị trí</th>
											                            		
											                            	<th width="50px">Hàm ẩm</th>
											                            	<th width="50px">Hàm lượng </th>	
											                                <th width="100px">Số lượng tồn </th>		
											                            	 <th width="100px">Số lượng tồn quy đổi </th>
											                            	 <th width="100px">Số lượng đề xuất </th>	                      
										                       		 </tr>
										                       		 <%
										                       		  
										                        		List<ISpSanxuatChitiet> list_ct=spCon.getListCtSP();
										                        		
										                        			 
										                        			for(int t=0;t<list_ct.size();t++){
										                        				
										                        				ISpSanxuatChitiet sp1=list_ct.get(t);
										                        			
										                        				
										                        				if(sp1.getIdSpThayThe().length() >0){
										                        				%>
											                        			<tr class='tbdarkrow'>
											                        			<%}else {%>
											                        			<tr class='tblightrow'>
											                        			<%} %>
													                         <td>
													                         	<input type="hidden" size="200px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_khoid"  value="<%=sp1.getKhoId()%>" readonly="readonly" />
													                         	<input type="hidden" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_spId"  value="<%=sp1.getIdSp()%>" readonly="readonly" />
													                         	<input type="hidden" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_spIdThayThe"  value="<%=sp1.getIdSpThayThe()%>" readonly="readonly" />
													                         	<input type="hidden" size="200px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_binid_dayc"  value="<%=sp1.getBinId()%>" readonly="readonly" />
													                         	
													                         		<input type="hidden" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_KhuvucId"  value="<%=sp1.getkhuvuckhoId()%>" readonly="readonly" />
													                         		
													                         		<input type="hidden" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MARQUETTE_FK"  value="<%=sp1.getMARQUETTE_FK()%>" readonly="readonly" />
													                         		   
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_khoten"  value="<%=sp1.getKhoTen()%>" readonly="readonly" /></td>
													                            	 <td>
													                            	<input type="text" size="100px"  name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MARQUETTE"  value="<%=sp1.getMARQUETTE() %>" readonly="readonly" /></td>
													                            	 <td>
													                            	<input type="text" size="100px" style="background-color: <%=(!sp1.getIdSpThayThe().equals(sp1.getIdSp())?"blue":"") %> " name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MASP" value="<%= sp1.getMaSp() %>" readonly="readonly" /></td>
													                            	
													                            	<td>
													                            	<input type="text" size="200px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_SOLO" value="<%=sp1.getSolo() %>" readonly="readonly" /></td>
													                            	
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MAPHIEU" value="<%=sp1.getMAPHIEU() %>" readonly="readonly" /></td>
													                            	
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MAPHIEU_TINHTINH" value="<%=sp1.getMAPHIEU_TINHTINH() %>" readonly="readonly" /></td>
													                            	
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MAPHIEU_EO" value="<%=sp1.getMAPHIEU_EO() %>" readonly="readonly" /></td>
													                            	
													                            
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MATHUNG" value="<%=sp1.getMATHUNG() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_MAME" value="<%=sp1.getMAME() %>" readonly="readonly" />
													                            	</td>
													                            	
													                            	<td>
													                            	<input type="text" size="150px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_NGAYHETHAN" value="<%=sp1.getNGAYHETHAN() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
													                            	<input type="text" size="150px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_NGAYNHAPKHO" value="<%=sp1.getNGAYNHAPKHO() %>" readonly="readonly" />
													                            	</td>
													                            	
													                            	
													                            	<td style="display: none">
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_khuvuckhoTen" value="<%=sp1.getkhuvuckhoTen() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
														                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_bin_dayc" value="<%=sp1.getBin() %>" readonly="readonly" />
														                            	</td>
										                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_HAMAM" value="<%=sp1.getHAMAM() %>" readonly="readonly" />
													                            	</td>
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_HALUONG" value="<%=sp1.getHAMLUONG() %>" readonly="readonly" />
													                            	</td>
													                            	
													                            	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_Soluongton" value="<%=sp1.getSoluongton() %>" readonly="readonly" />
													                            	</td>
													                            		<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_Soluongtonthute" value="<%=sp1.getSoluongtonthute() %>" readonly="readonly" />
													                            	</td>
													                            	 	<td>
													                            	<input type="text" size="100px" name="<%= i+"_"+stt+""+sp.getPK_SEQ()%>_Soluongdexuat" value="<%=sp1.getSoluong() %>"   />
													                            	</td>
													                            	 
													                          		   
													                          		 </tr>
													                        		<% 
													                        		}
													                       			 %>
													                   			 </table>
													                    		 <div align="right"><a href="javascript:dropdowncontent.hidediv('subcontenta<%= i+"_"+stt%>');">Hoàn tất</a></div>
													              			  </DIV>
														                              	
														                            </td>
														                        </tr>
											                        		<%  } 
											                        	}
											                        	 
											                        %>
											                        
											                        
											                         
											                    </table>
											                     <div align="right"> 
											                     
											                     <% if(!dmhBean.getDaYeucauNguyenlieu().trim().equals("1")){%> 
											                     
											                   
											                     <a class="button2" href="javascript:reload_Danhmucvattu()">
									                                <img style="top: -4px;" src="../images/button.png" alt="">Reload BOM</a>&nbsp;&nbsp;&nbsp;&nbsp;
									                                
																	<a class="button2" href="javascript:capnhatBom()">
									                                <img style="top: -4px;" src="../images/button.png" alt="">Cập nhật BOM</a>&nbsp;&nbsp;&nbsp;&nbsp;
									                                <% } %>
                                
											                     </div>
											                </DIV>
																					
													</td>
											<%} %>
				           	 				
				           	 				
										</tr>
										
									<% count++; }
								} %>
							
							
							
							
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					
					<% } %>
					
					
					<% 
					 
							
					%>
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
	dropdowncontent.init('duyetId', "right-bottom", 300, "click");
	dropdowncontent.init("thoiHanThanhToan","right-top",500,"click");
	dropdowncontent.init("nguoiDuyet","right-top",500,"click");
</script>	

<script type="text/javascript">
	for(var i = 0; i < 40; i++) {
		dropdowncontent.init('tenhd_'+i, "left-top", 300, "click");
		
		dropdowncontent.init('ngaynhan_'+i, "left-top", 300, "click");
		dropdowncontent.init('sanpham_'+i, "left-bottom", 300, "click");
	}
	
	<% 
	for(int i = 0; i < spList.size(); i++) 
		{
			ISanpham sp = spList.get(i); 
		%>
		dropdowncontent.init('<%=i+"-"+ sp.getPK_SEQ()+ "pobup"%>', "left-top", 300, "click");
		<%
		  List<ISanphamBom> spConList = sp.getSpListBom();
		  for(int k = 0; k < spConList.size(); k++) 
			{
			 	%>
				dropdowncontent.init('<%=i+"_"+k+"pubup_ton"%>', "left-top", 300, "click");
				dropdowncontent.init('<%=i+"__"+k+"pubup_ton"%>', "left-top", 300, "click");
				
				<%
			  System.out.println(i+"_"+k);
			}
		  
		} %>
	
	replaces();
</script>
	<%
		dmhBean.close();
	%>
</form>
</BODY>
</html>