<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.distributor.beans.thutienNPP.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.thutienNPP.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpThutienNPP tthdBean = (IErpThutienNPP)session.getAttribute("tthdBean"); %>
<% ResultSet nppList = tthdBean.getNppRs(); %>

<% ResultSet khList = tthdBean.getKhRs(); %>
<% ResultSet hdTCList = tthdBean.getHdTCRs(); %>
<% ResultSet ndList = tthdBean.getNoidungRs(); %>
<% ResultSet khRs = tthdBean.getKhList(); %>

<% ResultSet nganhangList = tthdBean.getNganhangRs(); %>
<% ResultSet nhanvienGHList = tthdBean.getNhanvienGNRs(); %>
<% ResultSet nhanvienBHList = tthdBean.getNhanvienBHRs(); %>
<% ResultSet noptienList = tthdBean.getNoptienRs(); %>

<% ResultSet chinhanhList = tthdBean.getChinhanhRs(); %>
<% ResultSet nccList = tthdBean.getNccRs(); %>
<% ResultSet nvtutList = tthdBean.getNvtuRs(); %>
<%
  List<IHoadonNPP> hoadonList = tthdBean.getHoadonRs(); 
 Hashtable<String,Double> hsTHANHTOAN = tthdBean.getNo_KhachHangList();
  
  %>
<% ResultSet sotkRs = tthdBean.getSotkRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###.##"); %>

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

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />

<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>


<script language="javascript" type="text/javascript">
	function replaces(stt)
	{
		 var trahet = document.getElementById("trahet" + stt);
		 var avat = document.getElementById("avat" + stt);
		 var conlai = document.getElementById("conlai" + stt);
		 var thanhtoan = document.getElementById("thanhtoan" + stt);
		
		 var tienthanhtoan = thanhtoan.value.replace(/,/g,"");
		 if(tienthanhtoan == '')
			 tienthanhtoan = '0';
		 
		 thanhtoan.value = DinhDangTien(tienthanhtoan);

		 tienAvat = avat.value.replace(/,/g,"");
		 
		 if(parseFloat(tienthanhtoan) >= parseFloat(tienAvat))
		 {
			 thanhtoan.value = DinhDangTien(tienAvat);
			 conlai.value = "0";
			 trahet.checked = true;
		 }
		 else
		 {
			 conlai.value = DinhDangTien(parseFloat(tienAvat) - parseFloat(tienthanhtoan));
			 trahet.checked = false;
		 }
				
		 document.getElementById("sotienthanhtoan").value = DinhDangTien(TongTienThanhToan());
		 Thanhtoan(500);
	}
	
	function TongTienThanhToan()
	{
		var tienthanhtoan = document.getElementsByName("thanhtoan");
		var tongthanhtoan = 0;
		for(k = 0; k < tienthanhtoan.length; k++)
		{
			if(tienthanhtoan.item(k).value != "")
			{
				var ttt = tienthanhtoan.item(k).value.replace(/,/g,""); 
				tongthanhtoan = parseFloat(ttt) + parseFloat(tongthanhtoan);
			}
		}
		return tongthanhtoan;
	}
	
	function DinhDangTienTT()
	{
		var giatrinhap = document.getElementById("sotienthanhtoan");
		giatrinhap.value = DinhDangTien(giatrinhap.value);
		
//		PhanBoTien();
	}
	
	function PhanBoTien()
	{	
		// Kiem tra so tien thu <= so tien no
		
		var tongtienAvat=document.getElementById("tongtien").value.replace(/,/g,"");
		var tienthu = document.getElementById("sotienhd").value.replace(/,/g,"");
		
		if(parseFloat(tongtienAvat)- parseFloat(tienthu) < 0)
		{
			 document.getElementById("sotienhd").value= DinhDangTien(roundNumber(tongtienAvat,0));
		}
		
		 var kyhieuhd = document.getElementsByName("kyhieuhd");
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 
		 
		 for(j = 0; j < kyhieuhd.length; j++)
		 {
			 thanhtoan.item(j).value = "";
			 conlai.item(j).value = "";
			 trahet.item(j).checked = false;
		 }
		 
		 var sotienphanbo = document.getElementById("sotienhd").value.replace(/,/g,"");
		 //alert(sotienphanbo);

		 var tongtien = 0;
		 var tongthanhtoan=0;
		 for(i = 0; i < kyhieuhd.length ; i++)
		 {
			tienAvat =  avat.item(i).value.replace(/,/g, "");
			tongtien = parseFloat(tongtien) + parseFloat(tienAvat);
			
			if(tongtien < parseFloat(sotienphanbo))
			{
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienAvat, 0));
				tongthanhtoan = tongthanhtoan + parseFloat(tienAvat);
				conlai.item(i).value = 0;
				trahet.item(i).checked = true;
			}
			else
			{
				tongtien = parseFloat(tongtien) - parseFloat(tienAvat);
				var tienconlai = parseFloat(sotienphanbo) - parseFloat(tongtien);
				
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienconlai, 0));
				tongthanhtoan = tongthanhtoan + tienconlai;
				
				conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(tienAvat) - parseFloat(tienconlai), 0));
				if(parseFloat(tienAvat) - parseFloat(tienconlai) <= 0)
					trahet.item(i).checked = true;
				else
					trahet.item(i).checked = false;
				
				break;
			}
		 }
		 document.getElementById("sotienhd").value= DinhDangTien(roundNumber(sotienphanbo,0));
		 document.getElementById("SoTienconlai").value = DinhDangTien((parseFloat(tongtienAvat)-parseFloat(document.getElementById("sotienhd").value.replace(/,/g, ""))),0); 
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
	
	function keypress(e) //Hàm dùng d? ngan ngu?i dùng nh?p các ký t? khác ký t? s? vào TextBox
	{    
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || keypressed > 57)
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 37 || keypressed == 39 || keypressed == 0 || keypressed == 46|| keypressed == 45)
			{//Phím Delete và Phím Back
				return;
			}
			return false;
		}
	}
	
	function Dinhdang(element)
	{
		element.value = DinhDangTien(element.value);
		if(element.value== '' || element.value== '0')
		{
			element.value= '';
		}
	}
	
	 function saveform()
	 {	
		 var ngayghinhan = document.getElementById("ngaychungtu");
		 if(ngayghinhan.value == "")
		 {
			alert("Vui lòng nhập ngày chứng từ");
			return;
		 }

		 var thuduoc = document.getElementById("sotienhd");
		 if(thuduoc.value == "")
		 {
			alert("Vui lòng nhập số tiền hóa đơn của khách hàng");
			return;
		 }
		 
		 var add_check = document.getElementById("add_check");
		 var add_kh = document.getElementById("add_makh");
		 var add_idhd = document.getElementById("add_idhd");
		 var add_sohoadon = document.getElementById("add_sohoadon");
		 var add_ngayhd = document.getElementById("add_ngayhd");
		 var add_tongsotien = document.getElementById("add_tongsotien");
		 var add_thanhtoan = document.getElementById("add_thanhtoan");
		 
		 if(add_check.checked)
		{
			 if(add_kh.value=="")
				 {
				 	alert("Vui lòng chọn khách hàng!");
					return;
				 }
			 if(add_ngayhd.value=="")
			 {
			 	alert("Vui lòng nhập ngày hóa đơn!");
				return;
			 }
			
			 if(add_thanhtoan.value=="")
			 {
			 	alert("Vui lòng nhập tổng số tiền thanh toán!");
				return;
			 }
		}
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['tthdForm'].action.value='save';
	     document.forms['tthdForm'].submit();
	 }

	 function FormatNumber(e)
	{
		e.value = DinhDangDonGia(e.value);
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
	 
	 
	 function ThanhToan(n)
	 {
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");	
		 
		 var isKHLe = document.getElementsByName("isKHLe");	
		 
		 var thuduoc = document.getElementsByName("sotienhd");
		 
		 var sotiendanop = document.getElementById("sotiendanop").value.replace(/,/g,"");
		 var sotienthuthem = document.getElementById("sotienthuthem").value.replace(/,/g,"");
		 
		 if(parseFloat(sotiendanop) > 0 )
	 	{
		 	var chonthutuHD = document.getElementsByName("chonthuHD");
	 	}
			 
         var tongtienAvat=0;
		 var tongtienVND = 0;

		//Thêm thu tiền HOADON NA	 
		 var add_check = document.getElementById("add_check");
		 var add_thanhtoan = document.getElementById("add_thanhtoan");
		 if(add_thanhtoan=="")
			 add_thanhtoan='0';		
		 add_thanhtoan= add_thanhtoan.value.replace(/,/g,"");			
		 
		 if(add_check.checked)
			{
			 	tongtienAvat=parseFloat(add_thanhtoan)+parseFloat(tongtienAvat);
			 	tongtienVND=parseFloat(add_thanhtoan)+parseFloat(tongtienVND);
			}
		 
		 	for(i = 0; i < trahet.length; i++)
		 	{
				//alert(i); 
				tongtienAvat =parseFloat(tongtienAvat) + parseFloat(avat.item(i).value.replace(/,/g,""));
				if(trahet.item(i).checked ) // truong hop chon tra het
				{
					if(n == 500){  // tuong hop click chon tra het nhung thay doi thanh toan
						var tt;
						if(thanhtoan.item(i).value != ''){
							tt = thanhtoan.item(i).value.replace(/,/g,"");
						}else{
							tt = 0;
						}																	
						
							if((parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
								
								conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));

								tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					
								thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
								trahet.item(i).checked = false;
							
							}else{
								
								var tt = thanhtoan.item(i).value.replace(/,/g,"");

								tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);					
								
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));

								conlai.item(i).value = DinhDangTien(parseFloat(avat.item(i).value.replace(/,/g,"")) -  parseFloat(tt)) ;
								
								thanhtoan.item(i).value = DinhDangTien(tt);
							}
						
					}else{ // Chon tra het
						
//							alert("I am here 1");
							conlai.item(i).value = "0";
							
							var tt = avat.item(i).value.replace(/,/g,"");

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);							
							
							thanhtoan.item(i).value = avat.item(i).value;
							
							

					}
				}
				else if(i == n){ // truong hop bo chon tra het
					thanhtoan.item(i).value = "0";							
						conlai.item(i).value = avat.item(i).value;
					
					
				}else  // Thay doi tai cac field du lieu
				{
					
					var tt;
					if(thanhtoan.item(i).value != ''){
						tt = thanhtoan.item(i).value.replace(/,/g,"");
						 
					}else{
						tt = 0;
					}					
					/* //Lúc mới vào trang : nếu là khách hàng lẻ thì mặc định trả hết >> được thanh toán st khác
					 if( 1 - parseInt(isKHLe.item(i).value )  == 0 && tt == 0 )
					{
						thanhtoan.item(i).value = avat.item(i).value;
						conlai.item(i).value = parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value);
						
						var tt = thanhtoan.item(i).value.replace(/,/g,"");

						//tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
						
						trahet.item(i).checked = true;
					} */ 
					
						if( (parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0 ){
							conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));
					
							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);

							thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
							
							avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
						
							
						}else{
							conlai.item(i).value = DinhDangTien(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(thanhtoan.item(i).value.replace(/,/g,"")));
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
							
							avat.item(i).value = DinhDangTien(avat.item(i).value.replace(/,/g,""))	;
							
							thanhtoan.item(i).value = DinhDangTien(tt);

						
						
						
						    
						}
				}
				
			}
		 			
		 
		 	var thu = 0;
		 
		 	var cp = 0;
		 	
		 	var ck = 0;
		 
			 	if(thuduoc.item(0).value != ''){
					 thu = thuduoc.item(0).value.replace(/,/g,"");
				 
				 }else{
					 thu = 0;
				 }

		 		document.getElementById("sotienhd").value = DinhDangTien(roundNumber(tongtienVND, 0));
		 		
		 		var conlai = "0";
		 		
		 		
			 		if(parseFloat(sotiendanop) == 0 )
			 		{
			 			document.getElementById("tongtien").value = DinhDangTien(roundNumber(tongtienAvat, 0));
			 			conlai = parseFloat(tongtienAvat) - parseFloat(tongtienVND);
			 		}else
			 		{
			 			if(chonthutuHD.item(0).checked)
				 		{
				 			document.getElementById("sotienthuthem").value = DinhDangTien(roundNumber(tongtienVND, 0));
				 			var tongtien= parseFloat(sotiendanop)+ parseFloat(document.getElementById("sotienthuthem").value.replace(/,/g,""));
				 			document.getElementById("tongtien").value = DinhDangTien(roundNumber(tongtien, 0));
				 			conlai = parseFloat(tongtien) - parseFloat(tongtienVND);
				 			
				 		}else{
				 			
				 			var tongtien= parseFloat(sotiendanop)+ parseFloat(sotienthuthem);
				 			document.getElementById("tongtien").value = DinhDangTien(roundNumber(tongtien, 0));
				 			document.getElementById("sotienthuthem").value = DinhDangTien(roundNumber(sotienthuthem, 0));
				 			conlai = parseFloat(tongtien) - parseFloat(tongtienVND);
				 		}
			 		}	
		 		
				
				document.getElementById("SoTienconlai").value = DinhDangTien(roundNumber(conlai, 0));
		 
	}
	 
	 function ChangeTienTe(){
		 document.forms['tthdForm'].action.value= 'changeTT';
	     document.forms["tthdForm"].submit();
		 
	 }

	 function submitform()
	 { 
		 ThanhToan(500);
		 document.forms['tthdForm'].action.value='submit';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function nextform()
	 { 
		 document.forms['tthdForm'].action.value='next';
	     document.forms["tthdForm"].submit();
	 }
	 function changePO()
	 { 
		 document.forms['tthdForm'].action.value='changePO';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function sellectAlls(selectId, checkboxName)
	 {
		 var chkAll = document.getElementById(selectId);
		 var spIds = document.getElementsByName(checkboxName);
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");	
		 
		 var sotienno = document.getElementsByName("tongtien");
		 
		 var tongtien = sotienno.item(0).value.replace(/,/g,"");
		 
		 var tt=0;
		 
		 
		//Thêm thu tiền HOADON NA	 
		 var add_check = document.getElementById("add_check");
		 var add_thanhtoan = document.getElementById("add_thanhtoan");
		 if(add_thanhtoan=="")
			 add_thanhtoan='0';		
		 add_thanhtoan= add_thanhtoan.value.replace(/,/g,"");			
		 
		 if(add_check.checked)
			{
			 	tongtien=parseFloat(add_thanhtoan)+parseFloat(tongtien);
			 	tt=parseFloat(add_thanhtoan)+parseFloat(tt);
			}
			 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				   /*  conlai.item(i).value = "0";			
					
					avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));

					thanhtoan.item(i).value = avat.item(i).value;
					
				    spIds.item(i).checked = true; */
				    if(parseFloat(tongtien) - parseFloat(tt) > 0 )
					  {
						 if(parseFloat(tongtien) - parseFloat(tt)- parseFloat(avat.item(i).value.replace(/,/g,"")) >= 0 )
						{
						    conlai.item(i).value = "0";			
							
							avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
		
							thanhtoan.item(i).value = avat.item(i).value;
							
						    spIds.item(i).checked = true;
						    
						    tt = parseFloat(tt) + parseFloat(thanhtoan.item(i).value.replace(/,/g,""));
						}else 
						{
							thanhtoan.item(i).value = DinhDangTien(roundNumber(parseFloat(tongtien) - parseFloat(tt), 0));								
		
							conlai.item(i).value =  DinhDangTien(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(thanhtoan.item(i).value.replace(/,/g,"")));
							
							avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
							
						    spIds.item(i).checked = false;
						    
						    tt = parseFloat(tt) + parseFloat(thanhtoan.item(i).value.replace(/,/g,""));
						}
						 
					  }
			 }
			tt= tongtien; 
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				    thanhtoan.item(i).value = "0";			
					
					avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));

					conlai.item(i).value = avat.item(i).value;
					spIds.item(i).checked = false;
			 }
			 
		 }
		 
		 document.getElementById("tongtien").value = DinhDangTien(roundNumber(tongtien, 0));//chỉnh
		 
		 document.getElementById("sotienhd").value = DinhDangTien(roundNumber(tt, 0)); 
	 				 		
		var conlai = parseFloat(tongtien) - parseFloat(tt) ;			
			
		document.getElementById("SoTienconlai").value = DinhDangTien(roundNumber(conlai, 0));
	 }
	 
	 function ChangeSearch(){
		 document.forms['tthdForm'].action.value= 'locsearch';
	     document.forms["tthdForm"].submit();		 
	 }
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tthdForm" method="post" action="../../ErpThutienNPPUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%= tthdBean.getId() %>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Phiếu thu tiền > Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpThutienNPPSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
       <A href="../../ErpThutienNPPPdfSvl?userId=<%= tthdBean.getUserId() %>&inBangke=<%= tthdBean.getId() %>" >
	        <IMG src="../images/Printer30.png" title="In bảng kê thu tiền" alt="In bảng kê thu tiền" border ="1px" style="border-style:outset"></A>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= tthdBean.getMsg() %></textarea>
		         <% tthdBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle"> Thu tiền</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0" border = "0">						
                <TR>
                    <TR>
                    <TD width="15%" class="plainlabel" valign="top">Ngày thu tiền</TD>
                    <TD class="plainlabel"  valign="top" width="20%">
                    	<input type="text"  class="days" id="ngaychungtu" name="ngaychungtu" value="<%= tthdBean.getNgaychungtu() %>" 
                    		 readonly /></TD>                                     
                    <TD class="plainlabel"  width="15%" >Diễn giải </TD>
                    <TD class="plainlabel" >                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
 
                </TR>
                <TR>
       			    <TD class="plainlabel">Người nộp tiền</TD>
                    <TD class="plainlabel">
                        <input type="text" name="nguoinoptien" value="<%= tthdBean.getNguoinoptien() %>" >
                    </TD>
                    <TD class="plainlabel">Bộ phận kinh doanh</TD>
                    <TD class="plainlabel">
                        <input type="text" name="bpkinhdoanh" value="<%= tthdBean.getSochungtu() %>" >
                    </TD>
       		   </TR>
       		   <TR>
                  	<TD class="plainlabel">Tìm từ ngày</TD>
                    <TD class="plainlabel">
                        <input type="text" name="tungay"    class="days" value="<%= tthdBean.getTungay() %>" >
                    </TD>
                    <TD class="plainlabel">Đến ngày</TD>
                    <TD class="plainlabel">
                        <input type="text" name="denngay"    class="days" value="<%= tthdBean.getDenngay() %>"  >
                    </TD>
                 </TR>
                 
                   <TR>
                      <TD class="plainlabel">Tìm trình dược viên</TD>
                  		<TD class="plainlabel" >                        
                  		<select name="nvbhId" id="nvbhId" style="width: 200px"   onChange="ChangeSearch();">
                            <option value=""> </option>
                        	<%
                        		if(nhanvienBHList != null)
                        		{
                        			try
                        			{
                        			while(nhanvienBHList.next())
                        			{  
                        			if( tthdBean.getNhanvienBHIds().contains(nhanvienBHList.getString("pk_seq")) ){ %>
                        				<option value="<%= nhanvienBHList.getString("pk_seq") %>" selected="selected" ><%= nhanvienBHList.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nhanvienBHList.getString("pk_seq") %>" ><%= nhanvienBHList.getString("Ten") %></option>
                        		 <% } } nhanvienBHList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                     
                      <TD class="plainlabel" >Hình thức thanh toán </TD>
                    <TD class="plainlabel" >
                    	<select name="hinhthuctt" id="hinhthuctt" style="width: 200px;" >
							    <% 
							   String[] mangchuoi=new String[]{"Chuyển khoản","Tiền mặt"};
							   for(int k=0;k < mangchuoi.length;k ++ ){
								   
								   if(tthdBean.getHinhthucTT().equals(mangchuoi[k])) {
									   %>
									    	<option value="<%=mangchuoi[k] %>" selected="selected"><%=mangchuoi[k] %> </option>
									   <%
								   }else{
									   %>
								    	<option value="<%=mangchuoi[k] %>" ><%=mangchuoi[k] %> </option>
								  		 <%  
								   }
							   }
							  %>
												   
						</select>
                    </TD>                     
                 </TR>   
                  <TR>
                      <TD class="plainlabel">Tìm nhân viên giao nhận</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="nvgnId" id="nvgnId" style="width: 200px" onChange="ChangeSearch();">
                            <option value=""> </option>
                        	<%
                        		if(nhanvienGHList != null)
                        		{
  			    			try
                        			{
                        			while(nhanvienGHList.next())
                        			{  
                        			if( tthdBean.getNhanvienGNIds().contains(nhanvienGHList.getString("pk_seq")) ){ %>
                        				<option value="<%= nhanvienGHList.getString("pk_seq") %>" selected="selected" ><%= nhanvienGHList.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nhanvienGHList.getString("pk_seq") %>" ><%= nhanvienGHList.getString("Ten") %></option>
                        		 <% } } nhanvienGHList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>                     
                 </TR>
                
                <TR>    
                       <TD class="plainlabel">Tìm khách hàng</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="khSearchId" style="width: 200px" onChange="ChangeSearch();">
                           <option value=""> </option>
                        	<%
                        		if(khList != null)
                        		{
                        			try
                        			{
                        			while(khList.next())
                        			{  
                        			if( tthdBean.getKhIds().contains(khList.getString("pk_seq")) ){ %>
                        				<option value="<%= khList.getString("pk_seq") %>" selected="selected" ><%= khList.getString("khTen") %></option>
                        			<%}else { %>
                        				<option value="<%= khList.getString("pk_seq") %>" ><%= khList.getString("khTen") %></option>
                        		 <% } } khList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                    </TR>
                
                 <TR>
                    <TD class="plainlabel">Tìm đối tác</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="nppSearchId" style="width: 500px" multiple="multiple" onChange="ChangeSearch();">
                           <option value=""> </option>
                        	<%
                        		if(nppList != null)
                        		{
                        			try
                        			{
                        			while(nppList.next())
                        			{  
                        			if( tthdBean.getNppIds().contains(nppList.getString("pk_seq")) ){ %>
                        				<option value="<%= nppList.getString("pk_seq") %>" selected="selected" ><%= nppList.getString("nppTen") %></option>
                        			<%}else { %>
                        				<option value="<%= nppList.getString("pk_seq") %>" ><%= nppList.getString("nppTen") %></option>
                        		 <% } } nppList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                  </TR>
                
                 <%if(tthdBean.getNhanvienBHIds().trim().length() > 0 || tthdBean.getNhanvienGNIds().trim().length() > 0 || tthdBean.getKhIds().trim().length() > 0 || tthdBean.getNppIds().trim().length() > 0   ){ %>
                    <TR>
                    	 <TD class="plainlabel">Phiếu nộp tiền</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="noptienId" style="width: 500px" multiple="multiple" onChange="ChangeSearch();">
                           <option value=""> </option>
                        	<%
                        		if(noptienList != null)
                        		{
                        			try
                        			{
                        			while(noptienList.next())
                        			{  
                        			if( tthdBean.getNoptienIds().contains(noptienList.getString("pk_seq")) ){ %>
                        				<option value="<%= noptienList.getString("pk_seq") %>" selected="selected" ><%= noptienList.getString("ngay") %></option>
                        			<%}else { %>
                        				<option value="<%= noptienList.getString("pk_seq") %>" ><%= noptienList.getString("ngay") %></option>
                        		 <% } } noptienList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                    </TR>
                    <%} %>
                               
                  
                    <TR>
                      <TD class="plainlabel">Tìm hóa đơn tài chính</TD>
                  		<TD class="plainlabel" colspan="3">                        
                  		<select name="hdId" id="hdId" style="width: 500px" multiple="multiple" onChange="ChangeSearch();">
                            <option value=""> </option>
                        	<%
                        		if(hdTCList != null)
                        		{
                        			try
                        			{
                        			while(hdTCList.next())
                        			{  
                        			if( tthdBean.getHdIds().contains(hdTCList.getString("pk_seq")) ){ %>
                        				<option value="<%= hdTCList.getString("pk_seq") %>" selected="selected" ><%= hdTCList.getString("Ten") %></option>
                        			<%}else { %>
                        				<option value="<%= hdTCList.getString("pk_seq") %>" ><%= hdTCList.getString("Ten") %></option>
                        		 <% } } hdTCList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>                     
                 </TR>      
       		<% if( Double.parseDouble(tthdBean.getSotiendanop().replaceAll(",","")) > 0  ) {%>
		        <TR>
                    <TD  width="15%" class="plainlabel">Tổng tiền đã nộp </TD>
                    <TD class="plainlabel" width="20%" colspan="3">
                        <input type="text" style="text-align: right;" name="sotiendanop" id = "sotiendanop" 
                        		value="<%=tthdBean.getSotiendanop()%>"   > 
                     </TD>
                 </TR>
                 <TR>                    
					 <TD  width="15%" class="plainlabel">Tổng tiền thu trực tiếp </TD>
					 	 <TD class="plainlabel" width="20%" >
                   <%if(tthdBean.getChonthutuHD().equals("1")){ %>
                        <input type="text" style="text-align: right;" name="sotienthuthem" id = "sotienthuthem" 
                        		value="<%= formatter.format(Double.parseDouble(tthdBean.getSotienthuthem())) %>"  onKeyPress = "return keypress(event);" onchange="ThanhToan(500);" readonly="readonly"> 

                    <%}else{ %> 
                        <input type="text" style="text-align: right;" name="sotienthuthem" id = "sotienthuthem" 
                        		value="<%= formatter.format(Double.parseDouble(tthdBean.getSotienthuthem())) %>"  onKeyPress = "return keypress(event);" onchange="ThanhToan(500);">                     
                    <%} %>
                    </TD>
					    <TD  width="15%" class="plainlabel">Chọn thu tiền trực tiếp </TD>
                      <TD class="plainlabel" width="5%">
                      	<%if(tthdBean.getChonthutuHD().equals("1")){ %>
                        <input type="checkbox" style="text-align: right;" name="chonthuHD" id = "chonthuHD"  checked="checked" value= "1" onChange="ChangeTienTe();"> 
                        <%}else{ %>
                        	<input type="checkbox" style="text-align: right;" name="chonthuHD" id = "chonthuHD"  value= "0" onChange="ChangeTienTe();">
                        <%} %>
                     </TD>
       			</TR>
             <%}else{
            	%>
             	 <TR style="display: none">
                    <TD class="plainlabel" >
                        <input type="text" style="text-align: right;" name="sotiendanop" id = "sotiendanop" value="0"   > 
                     </TD>
                     <TD class="plainlabel" >
                        <input type="text" style="text-align: right;" name="sotienthuthem" id = "sotienthuthem" 
                        		value="0"  onKeyPress = "return keypress(event);" > 
                     </TD>
       			</TR>
             <%} %>
       			<TR>
                    <TD  width="200px" class="plainlabel">Tổng tiền </TD>
                    <TD class="plainlabel" colspan = 5>
                        <input type="text" style="text-align: right;" name="tongtien" id = "tongtien" 
                        		value="<%= formatter.format(Double.parseDouble(tthdBean.getSotientt().replaceAll(",","")))%>"  AUTOCOMPLETE="off" > 
                     </TD>

       			</TR>
                <TR>
	                 <TD width="200px" class="plainlabel" valign="top">Tổng thanh toán</TD>
    	             <TD class="plainlabel" valign="top" colspan = 5>
						<input type="text"  id="sotienhd" name="sotienhd" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  onKeyPress = "return keypress(event);" onchange="PhanBoTien();"/>
					 </TD>
                </TR>
                
            	<TR>
                    <TD width="200px" class="plainlabel" valign="top">Còn lại</TD>
                    <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="SoTienconlai" name="SoTienconlai" value=""  style="text-align: right;"  onchange="ThanhToan(500);" onKeyPress = "return keypress(event);"/>
                    </TD>

                </TR>
               
                <TR>
					<TD class="plainlabel" colspan="4">
						<a class="button2" href="javascript:ChangeTienTe();"> 
						<img style="top: -4px;" src="../images/Search30.png" alt="">Lọc hóa đơn </a>&nbsp;&nbsp;&nbsp;&nbsp;
				</TR>

            </TABLE>
       
            </div>
        
            <hr> 
         
           	<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
  
                <TR class="tbheader">
                	<TH align="center" width="20%">Khách hàng OTC/ETC</TH> 
                	<TH align="center" width="7%">Mã đối tác </TH>
                	<TH align="center" width="7%">Số chứng từ</TH>
                	<TH align="center" width="8%">Số hóa đơn</TH>
                	<TH align="center" width="8%">Ngày hóa đơn</TH>
                	<TH align="center" width="4%">Trạng thái</TH>
                	<TH align="center" width="9%">Tổng số tiền (đã có VAT)</TH>
               	 	<TH align="center" width="9%">Thanh toán (VNĐ)</TH>
               	 	<TH align="center" width="9%">Còn lại</TH>
               	 	<TH align="center" width="9%">Số tiền KH đưa dư</TH>
               	 	<TH align="center" width="8%">Chiết khấu tháng</TH>
               	 	<TH align="center" width="6%">Trả hết
               	 		<input type="checkbox" onchange="sellectAlls('chkAllLtt', 'trahet')" id="chkAllLtt" >
               	 	</TH>
                </TR>
                
                <TR>                	
                   <TD class="plainlabel">                        
                  		<select name="add_makh" id="add_makh" style="width: 220px">
                           <option value=""> </option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( tthdBean.get_add_makh().contains(khRs.getString("pk_seq")) ){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("TEN") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("TEN") %></option>
                        		 <% } } khRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>                     
                  <% 	String sohoadon="";
                     		sohoadon = tthdBean.get_add_sohoadon();
                     		if(sohoadon =="")
                     			sohoadon = "NA";
                     	
                     		String thanhtoanna = tthdBean.get_add_thanhtoan().replaceAll(",","");
                     		if(thanhtoanna==""||thanhtoanna== null)
                     			thanhtoanna="0";
                     		
                     	%>
                     <TD class="plainlabel">
                     	<input type="text" style="width: 100%;" readonly value="<%=tthdBean.get_add_makh() %>" id= "add_madt"  name= "add_madt"  >                     	  
                     </TD>
                     
                     <TD class="plainlabel">
                     	<input type="text" style="width: 100%;" name= "add_idhd" value="<%=tthdBean.get_add_idhd() %>"  onKeyPress = "return keypress(event);">
                     </TD>
                     
                     <TD class="plainlabel">
                     	<input type="text" style="width: 100%;" name= "add_sohoadon" id= "add_sohoadon" value="<%=sohoadon %>"  onKeyPress = "return keypress(event);">
                     </TD>
                     
                     <TD class="plainlabel">
                    	<input type="text" style="width: 100%;" class="days" id="add_ngayhd" value="<%=tthdBean.get_add_ngayhd() %>" name="add_ngayhd" />
                   	 </TD> 
                     
                     <TD class="plainlabel">
                     	<input type="text" style="width: 100%;" readonly="readonly" name= "add_trangthai" value="" >
                     </TD>
                     
                     <td align="center">
           	 			<input type="text" style="width: 100%; text-align: right;" name= "add_tongsotien" value="" readonly="readonly">
           	 		 </td>
           	 		
           	 		<td align="center">
           	 			<input type="text" style="width: 100%; text-align: right;" name= "add_thanhtoan" id= "add_thanhtoan" value="<%= formatter.format(Double.parseDouble(thanhtoanna)) %>"  onchange="ThanhToan(500)" onkeyup="Dinhdang(this)" onKeyPress = "return keypress(event);">
           	 		</td>
           	 		
           	 		<td align="center">
           	 			<input type="text" style="width: 100%; text-align: right;" name= "add_conlai" id= "add_conlai" value="" readonly="readonly">
           	 		</td>
           	 		
           	 		<td align="center">
           	 			<input type="text" style="width: 100%; text-align: right;"  name= "add_sotienduadu" id= "add_sotienduadu" value="" readonly="readonly" >
           	 		</td>
           	 		
           	 		<td align="center">
           	 			<input type="text" style="width: 100%; text-align: right;"  name= "" id= "" value="" readonly="readonly" >
           	 		</td>
           	 		
           	 		<%if(tthdBean.get_add_makh()!=""){ %>
           	 		<td align="center">
           	 			<input type="checkbox" id="add_check" name="add_check" checked="checked" onchange="ThanhToan(500)">             	 		
           	 		</td>
           	 		<%} else{ %>
           	 		<td align="center">
           	 			<input type="checkbox" id="add_check" name="add_check"  onchange="ThanhToan(500)">             	 		
           	 		</td>
           	 		<%} %>
           	 	
                </TR>
                
                <%   
					int i;
    	            for(i = 0; i < hoadonList.size(); i++)
                	{
                		IHoadonNPP hoadon = hoadonList.get(i);
                		double thanhtoan = Double.parseDouble(hoadon.getThanhtoan().replaceAll(",",""));
                		if(hsTHANHTOAN.get(hoadon.getId()) != null)
                			thanhtoan = hsTHANHTOAN.get(hoadon.getId());
	               		%>

	               			<TR>               			
           	 				<td align="center">
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getKhId() %>" name= "khId"  >
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getIsKHLe() %>" name= "isKHLe"  >
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getKhMa() %>" name= "khMa"  >   
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd() %>" name= "loaihd"  >               					
           	 				</td>
           	 				
           	 				<td align="center">
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getNppId() %>" name= "nppId"  >
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getNppMa() %>" name= "nppMa"  >           					
           	 				</td>
           	 				
	               			<td align="center">
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHd"  >          					
           	 				</td>
           	 				
           	 				<td align="center">          	 				
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getSo() %>" name= "sohd" readonly="readonly" >          	 				          	 				
           	 				</td>
           	 				
           	 				<td align="center">							           	 				
           						 <input type="text" style="width: 100%; text-align: left;" value="<%= hoadon.getNgay() %>" name= "ngayhd" readonly="readonly" >          	 				
           	 				</td>
           	 				  
           	 			<%if(hoadon.getTrangthaihd().equals("Đã hủy") || hoadon.getTrangthaihd().equals("Đã xóa") ) {%>
           	 				<td align="center">							           	 				
           	 					<input type="text" style="width: 100%; text-align: left; color: red;" value="<%= hoadon.getTrangthaihd() %>" name= "trangthaihd" readonly="readonly" >          	 				
           	 				</td>
	               		<%}else{ %>
           	 				 <td align="center">							           	 				
           	 					<input type="text" style="width: 100%; text-align: left;" value="<%= hoadon.getTrangthaihd() %>" name= "trangthaihd" readonly="readonly" >          	 				
           	 				</td> 
           	 			<%} %> 
           	 				        	 		
           	 				<td align="center" >
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getTongtiencoVAT().replaceAll(",",""))) %>" name= "avat" id="avat<%= i %>" readonly="readonly" >
           	 				</td>         	 				
           	 				
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getThanhtoan().replaceAll(",",""))) %>" name= "thanhtoan" id="thanhtoan<%= i %>"  onchange="ThanhToan(500)" onKeyPress = "return keypress(event);">
           	 				</td>
           	 			
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getConlai() %>" name= "conlai" id="conlai<%= i %>" readonly="readonly" >
           	 				</td>
           	 				
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getDuno())) %>" name= "sotienno" id="sotienno<%= i %>" readonly="readonly" >
           	 				</td>
           	 				
		           	 		<td align="center">
		           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getCantru()))%>" readonly="readonly" >
		           	 		</td>
		           	 				
           	 				<td align="center">
           	 			<% 	if(hoadon.getConlai().equals("0")){ %>
           	 					<input type="checkbox" value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" checked="checked" onchange="ThanhToan(<%= i %>)" >
           	 			<%} else {%>
           	 					<input type="checkbox"  value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" onchange="ThanhToan(<%= i %>)" >
           	 			<%} %>
           	 				</td>
           	 			</tr>
           	 	<%} %>

            
            </TABLE> 
        	</div>     
     </fieldset>	
    </div>
</div>
</form>

<script type="text/javascript">
	ThanhToan(500);
</script>
</BODY>
</html>
<%
	if(nppList != null) nppList.close(); 
	if(ndList != null) ndList.close(); 
	if(noptienList != null) noptienList.close(); 
	if(nccList != null) nccList.close();
	if(nvtutList != null) nvtutList.close();
	if(nhanvienBHList != null) nhanvienBHList.close();
	if(nhanvienGHList != null) nhanvienGHList.close();
	if(hdTCList != null) hdTCList.close();
	if(nganhangList != null) nganhangList.close(); 
	if(chinhanhList != null) chinhanhList.close();
	if(sotkRs != null) sotkRs.close();
	tthdBean.DBclose();
	%>


