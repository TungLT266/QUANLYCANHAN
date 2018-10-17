<%@page import="geso.traphaco.erp.beans.hoadonkhacncc.imp.*"%>
<%@page import="geso.traphaco.erp.beans.hoadonkhacncc.*"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
 
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%
	IErpHoadonkhacNcc  dmhBean = (IErpHoadonkhacNcc) session.getAttribute("dmhBean");
	ResultSet dvthList = dmhBean.getDvthList();
	ResultSet loaihhList = dmhBean.getLoaiList();
	List<ISanpham> spList = dmhBean.getSpList();
	List<IDonvi> dvList = dmhBean.getDvList();
	List<ITiente> ttList = dmhBean.getTienteList();
	List<IKho> khoList = dmhBean.getKhoList();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	double tiGiaNguyenTe =dmhBean.getTiGiaNguyenTe();
	String[] cpkDienGiai = dmhBean.getCpkDienGiai();
	String[] cpkSotien = dmhBean.getCpkSoTien();
	ResultSet nccRs = dmhBean.getNccRs();
	
	//nếu có quyền sửa duyệt mua hàng thì được phép ghõ mã chi phí.
	int[] quyen = new  int[5];
	Utility util = (Utility) session.getAttribute("util");
	quyen = util.Getquyen("ErpDonmuahangUpdate_GiaySvl","",userId);
	DecimalFormat formatter = new DecimalFormat("###,###,###.##");
	
	DecimalFormat formatter_0 = new DecimalFormat("###,###,###");
	
	
%>
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
	font-size: 1.2em;
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
<script type="text/javascript" src="../scripts/erp-spListHdNcc.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
<script language="javascript" type="text/javascript">

	function replaces()
	{
			
		 	
		 	
			var nccId = document.getElementById("nccId");
			var nccTen = document.getElementById("nccTen");
			var masothue = document.getElementById("masothue");
			var diachi = document.getElementById("diachi");
		 
			var tem = nccId.value;
			if(tem == "")
			{
				nccTen.value = "";
				nccLoai.value = "";
				masothue.value = "";
				diachi.value="";
				
			}
			else
			{
				if(parseInt(tem.indexOf(" - ")) > 0)
				{
					nccId.value = tem.substring(0, parseInt(tem.indexOf(" - ")));
					
					tem = tem.substr(parseInt(tem.indexOf(" - ")) + 3);
					nccTen.value = tem.substring(0, tem.indexOf(" [ ") );
					 
					tem = tem.substr(parseInt(tem.indexOf(" [ ")) + 3);
					masothue.value = tem.substring(0, tem.indexOf(" ] ") );
					
					tem = tem.substr(parseInt(tem.indexOf(" [ ")) + 3);
					diachi.value = tem.substring(0, tem.indexOf(" ]") );
					
					
				}
			}
		 
			
		var idsp = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var donvitinh = document.getElementsByName("donvitinh");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtiennguyente");
 
		var thanhtientruocthue = document.getElementsByName("thanhtientruocthue");
 		
		var phantramthue = document.getElementsByName("phantramthue");
		var tienthue = document.getElementsByName("tienthue");
		var thanhtiensauthue = document.getElementsByName("thanhtiensauthue");
		
		var tiGiaNguyenTe = document.getElementById("tiGiaNguyenTe");
		  
		var taikhoankt = document.getElementsByName("taikhoankt");
		var sohieutaikhoan = document.getElementsByName("sohieutaikhoan");
		
		
		var bvat = 0;
		var avat = 0;
		
		var sodong =  masp.length;
		var i;
		for(i = 0; i < sodong; i++)
		{ 
			if( sohieutaikhoan.item(i).value != "")
			{
				var sp1 = sohieutaikhoan.item(i).value;
				var pos1 = parseInt(sp1.indexOf(" -- "));
				if(pos1 > 0)
				{
					taikhoankt.item(i).value = sp1.substring(0, pos1);
					sp1 = sp1.substr(parseInt(sp1.indexOf(" -- ")) + 3);
					sohieutaikhoan.item(i).value =sp1;
				}
			}else{
				taikhoankt.item(i).value="";
			}
			
			if( masp.item(i).value != "")
			{
				
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" -- "));
				var slg = soluong.item(i).value.replace(/,/g,""); 
				var tx = phantramthue.item(i).value.replace(/,/g,"");
				
				if(pos > 0)
				{
					masp.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					
					console.log("log  : "+sp.substring(0, parseInt(sp.indexOf("] ["))));
					
					//donvitinh.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					$("#donvitinh_"+i).select2("val", sp.substring(0, parseInt(sp.indexOf("] [")))); 
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					//nhomhang.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
					 
					 
				}
				
				if(soluong.item(i).value != "")
				{
					if(dongia.item(i).value != "")
					{
						var dgia = dongia.item(i).value.replace(/,/g,""); 
						//dongia.item(i).value =  DinhDangDonGia(dgia);
						var tt = 0;
						// sai cho nay
					  
							
							tt = parseFloat(slg) * parseFloat(dgia);
							
							console.log("Ti gia nguyen te: "+tiGiaNguyenTe.value);
							 var ttbvat =tt * parseFloat(tiGiaNguyenTe.value.replace(/,/g,""));
							 
							// var ttbvat = parseFloat(tt) + parseFloat(tt) * parseFloat(tx) / 100;
						 
							thanhtien.item(i).value =  DinhDangDonGia( tt.toFixed(2) );
						 
							thanhtientruocthue.item(i).value =DinhDangDonGia( ttbvat.toFixed(0) );
					 
					 	
					}
				}
				 
			}
			else if( masp.item(i).value == ""){ 
				tensp.item(i).value = "";
				donvitinh.item(i).value = "";
				 
				idsp.item(i).value = "";
				 
				//dongia.item(i).value = "";
				 
				//phantramthue.item(i).value = "";
				 
				//thanhtien.item(i).value  = "";
			  
				//soluong.item(i).value = "";
			  
			}  
		}
		
		 
		 
	/* 	document.getElementById("BVAT").value = DinhDangTien( Math.round(bvat) );
		document.getElementById("AVAT").value = DinhDangTien( Math.round(avat) );
		
		if(document.getElementById("AUSDVAT") && document.getElementById("BUSDVAT")){
			document.getElementById("AUSDVAT").value= Math.round( (parseFloat(Math.round(avat))/ parseFloat(tiGiaNguyenTe.value)) *100)/100 ;
			document.getElementById("BUSDVAT").value= Math.round( (parseFloat(Math.round(bvat))/ parseFloat(tiGiaNguyenTe.value))*100)/100 ;	
		}
		 */
		 
		setTimeout(replaces, 500);
	}
	
	 
	 
	
	function TinhTien(n)
	{
		var idsp = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var donvitinh = document.getElementsByName("donvitinh");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtiennguyente");
 
		var thanhtientruocthue = document.getElementsByName("thanhtientruocthue");
 		
		var phantramthue = document.getElementsByName("phantramthue");
		var tienthue = document.getElementsByName("tienthue");
		var thanhtiensauthue = document.getElementsByName("thanhtiensauthue");
		
		var tiGiaNguyenTe = document.getElementById("tiGiaNguyenTe");
		  
		var totalnguyente=0;
		var bvat = 0;
		var totaltienvat  = 0;
		
		var sodong =  masp.length;
		var i;
				for(i = 0; i < sodong; i++)
				{
					
					if( soluong.item(i).value != "")
					{ 
						if(soluong.item(i).value != "")
						{
							if(dongia.item(i).value != "")
							{
								var slg = soluong.item(i).value.replace(/,/g,""); 
								
								var dgia = dongia.item(i).value.replace(/,/g,""); 
								dongia.item(i).value =  DinhDangDonGia(dgia);
								var tt = 0;
								// sai cho nay
							   
								tt = parseFloat(slg) * parseFloat(dgia);
								
								//console.log("Ti gia nguyen te: "+tiGiaNguyenTe.value);
								 var ttbvat =  tt * parseFloat(tiGiaNguyenTe.value.replace(/,/g,""));
							 
								thanhtien.item(i).value =  DinhDangDonGia( tt.toFixed(2) );
							 
								totalnguyente+= parseFloat(tt.toFixed(2));
								
								
								thanhtientruocthue.item(i).value =DinhDangDonGia( ttbvat.toFixed(0) );
						 		
								bvat+=parseFloat(ttbvat.toFixed(0));
								
								 
								var phantramthue_=0;
								
								if(phantramthue.item(i).value == ""){
									phantramthue.item(i).value=0;
								}else{
									phantramthue_=parseFloat(phantramthue.item(i).value.replace(/,/g,""));
								}
								var   tienthue_=0;
								
								if(i==n){
									    tienthue_  =  parseFloat(ttbvat.toFixed(0))  * parseFloat(phantramthue_) / 100;
									tienthue.item(i).value = DinhDangDonGia( tienthue_.toFixed(0) );
									 
								}else{
									tienthue_=  parseFloat(tienthue.item(i).value.replace(/,/g,""));
								}
								var tiensauthue_=   parseFloat(ttbvat.toFixed(0))  +   parseFloat(tienthue_.toFixed(0));
								
								thanhtiensauthue.item(i).value=DinhDangDonGia(tiensauthue_.toFixed(0));
								
								
								totaltienvat += parseFloat (tienthue.item(i).value.replace(/,/g,""));
								
							}
						}
						 
					}
				 
			}
				
				document.getElementById("BUSDVAT").value = DinhDangTien( Math.round(totalnguyente) ); 
				
				document.getElementById("BVAT").value = DinhDangTien( Math.round(bvat) ); 
				
				document.getElementById("tienVAT").value = DinhDangTien( Math.round(totaltienvat) ); 
				document.getElementById("AVAT").value =DinhDangTien(bvat+totaltienvat);
				 
	}
	
	function Change_Tienthue(n)
	{
		var idsp = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var donvitinh = document.getElementsByName("donvitinh");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtiennguyente");
 
		var thanhtientruocthue = document.getElementsByName("thanhtientruocthue");
 		
		var phantramthue = document.getElementsByName("phantramthue");
		var tienthue = document.getElementsByName("tienthue");
		var thanhtiensauthue = document.getElementsByName("thanhtiensauthue");
		
		var tiGiaNguyenTe = document.getElementById("tiGiaNguyenTe");
		
		var totalnguyente=0;
		var bvat = 0;
		var totaltienvat  = 0;
		
		 
		
		var sodong =  masp.length;
		var i;
				for(i = 0; i < sodong; i++)
				{
					
					if( soluong.item(i).value != "")
					{ 
						if(soluong.item(i).value != "")
						{
							if(dongia.item(i).value != "")
							{
								var slg = soluong.item(i).value.replace(/,/g,""); 
								
								var dgia = dongia.item(i).value.replace(/,/g,""); 
								dongia.item(i).value =  DinhDangDonGia(dgia);
								var tt = 0;
								// sai cho nay
								tt = parseFloat(slg) * parseFloat(dgia);
								
								totalnguyente+= parseFloat(tt.toFixed(2));
								
								//console.log("Ti gia nguyen te: "+tiGiaNguyenTe.value);
								var ttbvat =  tt * parseFloat(tiGiaNguyenTe.value.replace(/,/g,""));
							 
								thanhtien.item(i).value =  DinhDangDonGia( tt.toFixed(2) );
							 
								thanhtientruocthue.item(i).value =DinhDangDonGia( ttbvat.toFixed(0) );
								bvat+=parseFloat(ttbvat.toFixed(0));
								
								//var phantramthue_=0;
								 if(i==n){
									var   tienthue_  =  parseFloat(tienthue.item(i).value.replace(/,/g,""));
									tienthue.item(i).value = DinhDangDonGia( tienthue_.toFixed(0) );
									var tiensauthue_=   parseFloat(ttbvat.toFixed(0))  +   parseFloat(tienthue_.toFixed(0));
									thanhtiensauthue.item(i).value=DinhDangDonGia(tiensauthue_.toFixed(0));
								 }
								 totaltienvat += parseFloat (tienthue.item(i).value.replace(/,/g,""));
								 
							}
						}
						 
				 
				 }
			}
				
			document.getElementById("BUSDVAT").value = DinhDangTien( Math.round(totalnguyente) ); 
				
				document.getElementById("BVAT").value = DinhDangTien( Math.round(bvat) ); 
				
				document.getElementById("tienVAT").value = DinhDangTien( Math.round(totaltienvat) ); 
				document.getElementById("AVAT").value =DinhDangTien(bvat+totaltienvat);
				
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
	
	function roundNumber(num, dec) 
	{
		var result = Math.round(num*Math.pow(10,dec))/Math.pow(10,dec);
		return result;
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
		if( checkInput() == true){
			 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
		 	 document.forms['dmhForm'].action.value='save';
		     document.forms['dmhForm'].submit();
		}
	 }
	 function replaceAll(str, find, replace) {
		  return str.replace(new RegExp(find, 'g'), replace);
	 }
	 function checkInput(){
		 
		 var tiente = document.getElementById("tiente_fk");		 
		 var nccId = document.getElementById("nccId");		 
		 
		 var error = document.getElementById("Msg");
		  
		 if(tiente.value=="")
		 {
		 	tiente.focus();
		 	error.value="Vui lòng chọn loại tiền tệ!";
			return false;
		 }
		  
		 
		 var tongtienavat=document.getElementById("AVAT").value;
		  if(tongtienavat ==0){
			 error.value="Vui lòng nhập giá trị hóa đơn  <> 0";
				return false; 
		  }  
	 
		return true;
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
	 
	 function submitform()
	 { 		
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
	 
	 function changeNoiDia()
	 {
		document.forms['dmhForm'].nccId.value= "";
		document.forms['dmhForm'].action.value='checkedNoiDia';
		document.forms["dmhForm"].submit();
		
	 } 
	 
	 function inPhieuNhapKho(){
			document.forms['dmhForm'].action.value='inPhieuNhapKho';
	     	document.forms['dmhForm'].submit();
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
	$(document).ready(function() {
	    $(".uppercase").keyup(function() {

	        var val = $(this).val();
	        $(this).val(val.toUpperCase());
	    });
	});
	 
	function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
	}
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../ErpHoadonkhacNccUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="loai" id="loai" value='<%=dmhBean.getLoai()%>'> 
		<input type="hidden" name="action" value="1" >
		<input type="hidden" name="id" value="<%=dmhBean.getId() %>" />
		
		
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation"> Quản lý mua hàng > Nghiệp vụ khác > Hóa đơn điều chỉnh giá NCC  > Hiển thị</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="javascript:goBack();"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave">  
				</span>
				<A href="javascript:inPhieuNhapKho()" >
	    	<img src="../images/Printer30.png" alt="In" title="In phieu nhap vat tu bao bi 1" longdesc="In phieu nhap vat tu bao bi 1" border=1 style="border-style:outset"></A>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Đơn mua hàng</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
						 
						  <TR>
									<td class="plainlabel" valign="middle">Loại hàng hóa</td>
	                      <TD class="plainlabel" valign="middle" colspan="3"   >
	                      
	                            <select name="loaihanghoa" id="loaihanghoa" style="width: 200px">
	                              <option value=""></option>
	                       
	                             <%
	                            	String loaihanghoa = dmhBean.getLoaihanghoa();	
	                            	if(loaihanghoa.equals("0"))
	                            	{%>
	                            	<option value="0" selected="selected">Sản phẩm</option>
	                            	<option value="1" >Mã sửa chữa lớn</option>
	                              	<option value="2" >Chi phí trả trước</option>
	                                <option value="3" >Chi phí dịch vụ</option>
	                               <%}else if(loaihanghoa.equals("1")) { %>
	                         		 <option value="0" >Sản phẩm</option>
	                           		 <option value="1" selected="selected">Mã sửa chữa lớn</option>
	                               	 <option value="2" >Chi phí trả trước</option>
	                               	 <option value="3" >Chi phí dịch vụ</option>
	                            	<%} else if (loaihanghoa.equals("2")) { %>
	                            	 <option value="0" >Sản phẩm</option>
	                           		 <option value="1" >Mã sửa chữa lớn</option>
	                               	 <option value="2" selected="selected" >Chi phí trả trước</option>
	                                 <option value="3" >Chi phí dịch vụ</option>
	                               
	                            	<%} else if (loaihanghoa.equals("3")) { %>
	                             		 <option value="0" >Sản phẩm</option>
	                            		 <option value="1" >Mã sửa chữa lớn</option>
	                                	 <option value="2"  >Chi phí trả trước</option>
	                                 	 <option value="3" selected="selected" >Chi phí dịch vụ</option>
	                                
	                             	<%}
	                            	else { %>
	                            	 <option value="0" >Sản phẩm</option>
	                           		 <option value="1" >Mã sửa chữa lớn</option>
	                              	 <option value="2" >Chi phí trả trước</option>
	                              	 <option value="3" >Chi phí dịch vụ</option>
	                              	 
	                            	<%} %>
	                              
	  
	                            </select>
	                       </TD>
                       </TR>
						 
						 
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày ghi nhận</TD>
								<TD class="plainlabel" valign="top" >
									<input type="text" class="days" id="ngayghinhan" name="ngayghinhan"
											value="<%= dmhBean.getNgaymuahang() %>" maxlength="10" readonly />
								</TD>
								
								 <TD class="plainlabel">Số chứng từ</TD>
								<TD class="plainlabel"> <input type="text"  id="sochungtu" name="sochungtu"
											value="<%= dmhBean.getSochungtu() %>"  /> </TD>
								
							</TR>
						 
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
							 
							<TR>
								<TD class="plainlabel">Mã số thuế</TD>
								<TD class="plainlabel">
									<input type="text" id="masothue" name="masothue" value="<%= dmhBean.getMasothue() %>" >
								</TD>
								<TD class="plainlabel">Địa chỉ</TD>
								<TD class="plainlabel">
									<input type="text" id="diachi" name="diachi" value="<%= dmhBean.getDiachiNcc() %>"  style="width: 100%;" >
								 
								</TD>
							</TR>
							<TR class="plainlabel">
							
								<TD class="plainlabel">Tiền tệ</TD>
								<TD class="plainlabel">
									<Select name="tiente_fk" id="tiente_fk" onChange="submitform();">
										<!-- <option value=""></option> -->
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
									
									Tỉ giá
									<input type="text" value='<%=tiGiaNguyenTe %>' onchange="TinhTien(-1)"  name="tiGiaNguyenTe" id="tiGiaNguyenTe">
								</TD>
								 <TD class="plainlabel"></TD>
								<TD class="plainlabel">
								 
								</TD>
								
							</TR>
							
							 <TR>
								<TD class="plainlabel">Mẫu hóa đơn</TD>
								<TD class="plainlabel"><input class = "uppercase" type="text" name="mauhoadon" id="mauhoadon" value="<%=dmhBean.getMauhoadon()%>" style="text-align: left;"
									 > </TD>
								
								<TD class="plainlabel">Ký hiệu hóa đơn</TD>
								<TD class="plainlabel"><input class="uppercase" type="text" name="kyhieuhoadon" id="kyhieuhoadon" value="<%=dmhBean.getKyhieu()%>" style="text-align: left;"
									 > </TD>
							</TR>
							
							 <TR>
								<TD class="plainlabel">Số hóa đơn</TD>
								<TD class="plainlabel"><input class="uppercase" type="text" name="sohoadon" id="sohoadon" value="<%=dmhBean.getSohoadon()%>" style="text-align: left;"
									 > </TD>
								
								<TD class="plainlabel">Ngày hóa đơn</TD>
								<TD class="plainlabel"><input type="text" name="ngayhoadon" id="ngayhoadon" class="days"  value="<%=dmhBean.getNgayhoadon()%>" style="text-align: left;"
									 > </TD>
							</TR>
							<tr>
								<td class="plainlabel">Tổng tiền Nguyên Tệ chưa VAT</td>
									<td class="plainlabel"><input type="text" name="BUSDVAT" id="BUSDVAT"    readonly="readonly" style="text-align: right;">
									</td>
										<TD class="plainlabel">Nhập tại kho</TD>
								<TD class="plainlabel"><input type="text" class = "uppercase" name="nhaptaikho" id="nhaptaikho" value="<%=dmhBean.getNhaptaikho()%>" style="text-align: left;"
									 > </TD>
							</tr>
						 
							<TR>
								<TD class="plainlabel">Tổng tiền chưa VAT</TD>
								<TD class="plainlabel"><input type="text" name="BVAT" id="BVAT" value="<%=dmhBean.getTongtienchuaVat()%>" style="text-align: right;"
									readonly="readonly"> </TD>
								
								<TD class="plainlabel">Tiền VAT</TD>
								<TD class="plainlabel"><input type="text" readonly="readonly" name="tienVAT" id="tienVAT" value="<%=dmhBean.getVat()%>" style="text-align: right;"
									 > </TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Tổng tiền sau VAT</TD>
								<TD class="plainlabel"><input type="text" name="AVAT" id="AVAT" value="<%=dmhBean.getTongtiensauVat()%>" style="text-align: right;"
									readonly="readonly"> </TD>
								
								<TD class="plainlabel">Diễn giải</TD>
								<TD class="plainlabel" >
								<input type="text" name="diengiai" id="diengiai" value="<%= dmhBean.getGhiChu() %>" style="text-align: left;width: 100%">
								</TD>
							</TR>
							
						 	
							
							 
							 
							 
						</TABLE>
						<hr>
					</div>
					
				 
					
					<div align="left" style="width: 100%; float: none; clear: none;">
						<TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1">
							<TR class="tbheader">
								<TH align="center" width="30px">STT</TH>
								<TH align="center" width="7%" >Tài khoản</TH>
								
								<TH align="center" width="7%" >Mã hàng mua</TH>
								<TH align="center" width="18%">Tên hàng mua</TH>
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="5%">Số lượng</TH>
								<TH align="center" width="5%">Đơn giá nguyên tệ tăng/giảm</TH>
								<TH align="center" width="5%">Thành tiền nguyên tệ</TH>
							 	<TH align="center" width="5%">Thành tiền trước VAT (VND)</TH>
							    <TH align="center" width="5%">% VAT</TH>
							 	<TH align="center" width="5%">Tiền VAT</TH>
							 	<TH align="center" width="5%">Tiền sau VAT(VND)</TH> 
							</TR>
							
							<% int count = 0; 
								if(spList.size() > 0) { 
									for(int i = 0; i < spList.size(); i++) { 
										ISanpham sp = spList.get(i);
										%>
										
										<tr>
											<TD align="center" width="2%">
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="<%= sp.getPK_SEQ() %>" name="idsp" style="width: 100%" readonly="readonly" > 

											</TD>
											 <TD  align="center" width="8%" >
												 <input type="hidden" value="<%= sp.getTaikhoanKTId() %>" name="taikhoankt" style="width: 100%" readonly="readonly" >
												 <input type="text"  name="sohieutaikhoan" style="width: 100%" value="<%= sp.getSohieutaikhoan() %>"  onkeyup="ajax_showOptions(this,'taikhoankt',event)" AUTOCOMPLETE="off" > 
											</TD>
											<TD  align="center" width="8%" >
												<input type="text"  name="masp" style="width: 100%" value="<%= sp.getMasanpham() %>"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
											</TD>
											 
											<TD align="left" width="10%">
											   <input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" > 
											</TD>
											<TD align="center" width="6%">
											 
												<select style="width: 100%" name="donvitinh" id="<%="donvitinh_"+i %>" style=" width: 200px" >
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
												<input type="text" value="<%= sp.getSoluong() %>" name="soluong" onchange="TinhTien(<%=i%>)" style="width: 100%; text-align: right;" > 
												 
											</TD>
											 <TD align="center" width="7%">
												<input type="text" value="<%=  sp.getDongia() %>" name="dongia" onchange="TinhTien(<%=i%>)" style="width: 100%; text-align: right;" > 
												 
											</TD>
											 
											<TD align="center" width="8%">
												 
												<input type="text" value="<%= formatter.format(sp.getThanhtienNguyenTe()) %>"  name="thanhtiennguyente" readonly="readonly" style="width: 100%; text-align: right;" > 
											</TD>
											 <TD align="center" width="8%">
												<input type="text" value="<%= formatter_0.format(sp.getThanhtien()) %>"  name="thanhtientruocthue"  readonly="readonly"  style="width: 100%; text-align: right;" > 
											</TD>
											 <TD align="center" width="8%">
												<input type="text" value="<%= sp.getPhantramthue() %>"  name="phantramthue" onchange="TinhTien(<%=i%>)" style="width: 100%; text-align: right;" > 
											</TD>
											 	 <TD align="center" width="8%">
												<input type="text" value="<%=formatter_0.format(sp.getTienthue()) %>"  name="tienthue" onchange="Change_Tienthue(<%=i%>)"  style="width: 100%; text-align: right;" > 
											</TD>
												 <TD align="center" width="8%">
												<input type="text" value="<%=formatter_0.format(sp.getThanhtien()+ sp.getTienthue()) %>"  readonly="readonly"  name="thanhtiensauthue" style="width: 100%; text-align: right;" > 
											</TD>
											
										 
										</tr>
										
									<% count++; }
								} %>
							
							<% 
							int t=count+40;
							for(int i = count; i < t; i++) { %>
							
								<tr>
								 	<TD align="center" width="2%">
												<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
												<input type="hidden" value="" name="idsp" style="width: 100%" readonly="readonly" > 

											</TD>
											 
											  <TD  align="center" width="8%" >
												 <input type="hidden" value="" name="taikhoankt" style="width: 100%" readonly="readonly" >
												<input type="text"  name="sohieutaikhoan" style="width: 100%" value=""  onkeyup="ajax_showOptions(this,'taikhoankt',event)" AUTOCOMPLETE="off" > 
											</TD>
											
											
											<TD  align="center" width="8%" >
												<input type="text"  name="masp" style="width: 100%" value=""  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
											</TD>
											 
											<TD align="left" width="10%">
											   <input type="text" value="" name="tensp" style="width: 100%" > 
											</TD>
											<TD align="center" width="6%">
											 
												<select style="width: 100%" name="donvitinh" id="<%="donvitinh_"+i %>" style=" width: 200px" >
															<option value=""></option>
															<% if (dvList.size() > 0)
															   {
																for (int j = 0; j < dvList.size(); j++)
																{
																	IDonvi donvi =  dvList.get(j);
																	  %>
																	<option value="<%=donvi.getId()%>"><%=donvi.getDonvi()%></option>
																	 <% 
																	}
																}  %>
												</select>
												
												
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="" name="soluong"  onchange="TinhTien(<%=i%>)" style="width: 100%; text-align: right;" > 
												 
											</TD>
											 <TD align="center" width="7%">
												<input type="text" value="" name="dongia" onchange="TinhTien(<%=i%>)"  style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value=""  name="thanhtiennguyente"  readonly="readonly"  style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value=""  name="thanhtientruocthue"  readonly="readonly"  style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value=""  name="phantramthue" onchange="TinhTien(<%=i%>)" style="width: 100%; text-align: right;" > 
											</TD>
											<TD align="center" width="8%">
												<input type="text" value=""  name="tienthue" onchange="Change_Tienthue(<%=i%>)" style="width: 100%; text-align: right;" > 
											</TD>
											 <TD align="center" width="8%">
												<input type="text" value=""  name="thanhtiensauthue"   readonly="readonly"  style="width: 100%; text-align: right;" > 
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
	
	
	jQuery(function()
	{		
		$("#nccId").autocomplete("ErpNccHoadonkhacList.jsp");
	});	
</script>


<script type="text/javascript">
    <%if(spList.size() > 0) { 
	 %>
	 TinhTien(<%=spList.size()%>);
	 <%}%>
</script>

	<%
		if(spList!=null){
			spList.clear();
		}
 		if(dvList!=null){
 			dvList.clear();
 		}
 		if(ttList!=null){
 			ttList.clear();
 		}
 		if(khoList!=null){
 			khoList.clear();
 		}
 		session.setAttribute("dmhBean",null);
		dmhBean.close();
	%>
</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>