<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.park.*" %>
<%@ page  import = "geso.traphaco.erp.beans.park.imp.*" %>
<%@page import="java.util.Formatter"%>
<%@page import="java.util.Formattable"%>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.text.DateFormat" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.text.SimpleDateFormat" %>
<% IErpPark pBean = (IErpPark)session.getAttribute("pBean"); %>
<% List<IErpHoadonSp> pnkList = pBean.getPnkList(); %>
<% ResultSet rsNcc = pBean.getNccRs(); %>
<% ResultSet ttRs = pBean.getTienteRs(); %>
<% ResultSet poNKRs = pBean.getPoNkRs(); %>
<% ResultSet poRs = pBean.getPoRs(); %>
<% ResultSet ChitietPoRs = pBean.getChitietPoRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% NumberFormat formatter=new DecimalFormat("#,###,###.##"); %>
<% NumberFormat formatter1=new DecimalFormat("#,###,###.######"); %>

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

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<style type="text/css">
.color
{
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
<script src="../scripts/ui/jquery.ui.datepicker.js" type="text/javascript"></script>
<script type="text/javascript">
	$(document).ready(function() {		
		$( ".days" ).datepicker({			    
				changeMonth: true,
				changeYear: true				
		});            
	});	
</script>

<script language="javascript" type="text/javascript">

	function replaces()
	{							
		var nccTen = document.getElementById("nccTTTen");
		var nccMST = document.getElementById("nccTTMST");
		var nccDC = document.getElementById("nccTTDiachi");
		var nccId= document.getElementById("nccTTId");
		
		var  tem = nccMST.value;
		if(tem == "")
		{
			nccMST.value = "";
			nccTen.value = ""; 
			nccDC.value = "";
			
		}
		else
		{
			if(parseInt(tem.indexOf(" - ")) > 0)
			{
				nccMST.value = tem.substring(0, parseInt(tem.indexOf(" - ")));
				
				tem = tem.substr(parseInt(tem.indexOf(" - ")) + 3);
				nccTen.value = tem.substring(0, tem.indexOf(" [ ") );			
				
				tem = tem.substr(parseInt(tem.indexOf(" [ ")) + 3);
				nccDC.value = tem.substring(0, tem.indexOf(" ]") );
				
				tem = tem.substr(parseInt(tem.indexOf(" [ ")) + 3);
				nccId.value = tem.substring(0, tem.indexOf(" ]") );

			}
		} 
			  		 	
		setTimeout(replaces, 500);
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
			
			//alert(kq);
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

		return true;
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
				tongsoluong += soluong[n].value.replace(/,/g,"");

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
			
			var tongsotienchuathue = 0;
			var tongluong = 0;
			
			var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
			var thuesuat =  document.getElementById('thuesuat' + thutu);
			var vat =  document.getElementById('vat' + thutu);
			var tongcong =  document.getElementById('tongcong' + thutu);
									
			for(var n = 0; n < soluong.length; n++)  
			{
				if(parseFloat(soluong[n].value.replace(/,/g,"")) > parseFloat(soluonghd[n].value.replace(/,/g,"")))
					soluong[n].value = soluonghd[n].value;
					
				soluong[n].value = soluong[n].value.replace(/,/g,"");
				dongia[n].value = dongia[n].value.replace(/,/g,"");
				
				var tt = parseFloat(soluong[n].value)*parseFloat(dongia[n].value);
				tt = Math.round(tt*100);
				tt = tt/100;
					
				thanhtien[n].value = DinhDangTien(tt);
								
				tongsotienchuathue = tongsotienchuathue + tt;
				
				//tongsoluong += parseFloat(soluong[n].value.replace(/,/g,""));

			}
						
			sotienchuathue.value = DinhDangTien(Math.round(tongsotienchuathue));
			thuesuat.value = '10';
			vat.value = DinhDangTien(Math.round(tongsotienchuathue*0.1));
			tongcong.value = DinhDangTien(Math.round(tongsotienchuathue*0.1)+Math.round(tongsotienchuathue));
				
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
		
		
		var tongsotienchuathue = 0;
		var tongluong = 0;
		
		var sotienchuathue =  document.getElementById('sotienchuathue' + thutu);
		var thuesuat =  document.getElementById('thuesuat' + thutu);
		var vat =  document.getElementById('vat' + thutu);
		var tongcong =  document.getElementById('tongcong' + thutu);
		
		for(var n = 0; n < soluong.length; n++)  
		{
			if(parseFloat(soluong[n].value.replace(/,/g,"")) > parseFloat(soluonghd[n].value.replace(/,/g,"")))
				soluong[n].value = soluonghd[n].value;
			
			soluong[n].value = soluong[n].value.replace(/,/g,"");
			dongia[n].value = dongia[n].value.replace(/,/g,"");
			
			var tt = parseFloat(soluong[n].value)*parseFloat(dongia[n].value);
			/* tt = Math.round(tt*100);
			tt = tt/100; */
				
			thanhtien[n].value = DinhDangTien(tt);
			
			tongsotienchuathue = tongsotienchuathue + tt;
		}
			
		var thuevat1= 0;
		sotienchuathue.value = DinhDangTien(Math.round(tongsotienchuathue));
		thuevat1 =  thuesuat.value ;
		vat.value = DinhDangTien(Math.round(tongsotienchuathue*thuevat1/100));
		tongcong.value = DinhDangTien(Math.round(tongsotienchuathue*thuevat1/100)+Math.round(tongsotienchuathue));
			
		return;
		
	}
	
	
	function Kiemtra3(num)
	{
		
		var tienteId = document.getElementById("ttId").value;
		var tigia = document.getElementById("tigia").value;
		
		
		var thutu = num;
		//alert(thutu);
		
		var soluong = document.getElementsByName('sohoadon' + thutu + '.soluong');
		var dongia = document.getElementsByName('sohoadon' + thutu + '.dongia');
		var thanhtien = document.getElementsByName('sohoadon' + thutu + '.thanhtien');
		
		var tienhang = document.getElementsByName('tienhang' + thutu);	
		var chietkhau = document.getElementsByName('chietkhau' + thutu);	
		var tongcong = document.getElementsByName('tongcong' + thutu);	
		
		var chiphikhac = document.getElementsByName('sohoadon' + thutu + '.chiphikhac');
		var sopo = document.getElementsByName('sohoadon' + thutu + '.soPO');
		
		var tongsoluong = document.getElementById('tongsoluong');
		
		var tongtienhang = 0;
		var tongchiphikhac = 0;
		var tongluong = 0;
		for(var n = 0; n < soluong.length; n++)  // duyet tung dong chi tiet ben trong cua hoa don (= thutu)
		{
			soluong[n].value = soluong[n].value.replace(/,/g,"");
			dongia[n].value = dongia[n].value.replace(/,/g,"");
			
			var tt = parseFloat(soluong[n].value)*parseFloat(dongia[n].value);
			tt = Math.round(tt*100);
			tt = tt/100;
			thanhtien[n].value = DinhDangTien(tt);
			dongia[n].value = DinhDangTien5(dongia[n].value);
			
			var check = document.getElementsByName('sohoadon' + thutu + '.check' + n);
			
			if(check[0].checked == true) {
				tongtienhang = tongtienhang + tt;
			}
			
			if(check[0].checked == true) {
				tongluong = tongluong + parseFloat(soluong[n].value);
			}	
						
			chiphikhac[n].value = chiphikhac[n].value.replace(/,/g,"");
			var chiphi = parseFloat(chiphikhac[n].value);
			
			if(n==0)
			{
				if(check[0].checked == true) {
					tongchiphikhac = tongchiphikhac + chiphi;
				}
			}
			else
			{
				if( sopo[n].value != sopo[n-1].value ){
					if(check[0].checked == true) {
						tongchiphikhac = tongchiphikhac + chiphi;
					}
				}
			} 
			soluong[n].value = DinhDangTien(soluong[n].value);
		}
		tongtienhang = Math.round(tongtienhang*100);
		tongtienhang = tongtienhang/100;
		
		chietkhau[0].value = tongchiphikhac;
		
		chietkhau[0].value = chietkhau[0].value.replace(/,/g,"");

		chietkhau[0].value = chietkhau[0].value.replace(/,/g,"");
		if(chietkhau[0].value == null || chietkhau[0].value == ''){
			chietkhau[0].value = '0';
		}
		
		tongcong[0].value =  parseFloat(tongtienhang) + parseFloat(chietkhau[0].value);
		
		if(tienteId != "100000")
		{			
			var tongcongVND = document.getElementsByName('tongcongVND' + thutu);	
			if(tigia == '') tigia = '0';
			var tienvnd = parseFloat(tongcong[0].value) * parseFloat(tigia.replace(",",""));
			tongcongVND[0].value = DinhDangTien(tienvnd);
		}
		
		tienhang[0].value = DinhDangTien(tongtienhang);
		chietkhau[0].value = DinhDangTien(chietkhau[0].value);
		tongcong[0].value = DinhDangTien(tongcong[0].value);
		tongsoluong.value = DinhDangTien(tongluong);
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
				var a = 0;
				a = Math.round(( Math.round(bvat) + parseFloat(dc) ) * parseFloat(ts) / 100) ;
				
				vat.value = DinhDangTien( a );
				
				var b =  Math.round(Math.round(bvat) + parseFloat(dc) + a);
				
				tongcong.value =  DinhDangTien(  b );
				/* 
				
				var thuevat1= 0;
				sotienchuathue.value = DinhDangTien(Math.round(tongsotienchuathue));
				thuevat1 =  thuesuat.value ;
				vat.value = DinhDangTien(Math.round(tongsotienchuathue*thuevat1/100));
				tongcong.value = DinhDangTien(Math.round(tongsotienchuathue*thuevat1/100)+Math.round(tongsotienchuathue)); */
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
		
	/* 	//---- kiểm tra số lượng MAX ----//
	 	var dungsai = document.getElementsByName("sohoadon0.dungsai"); 
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
			
		} 
	 */
	
		
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
	
</script>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2();  });
	     
	</script>
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0" onload="Kiemtratable()" >
<form name="erpParkForm" method="post" action="../../ErpParkHoadonUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" id="sophieunhaptinh"  value='<%= pBean.getPnkList().size() %>'>
<input type="hidden" name="id" value='<%= pBean.getId() %>'>
<input type="hidden" id="thuesuatPO" value="<%= pBean.getThuesuat() %>" />
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Công nợ phải trả > Hóa đơn nhà cung cấp > Hiển thị
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<%if(pBean.getDuyet()==1)  {%>
  		<A href= "../../ErpParkHoadonduyetSvl?userId=<%=userId %>&back=1">
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
  		<% } else { %>
    	<A href= "javascript:goBack()" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <% } %>
        	
        	 <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%%"><%= pBean.getMsg() %></textarea>
		         <% pBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset id="hoadon" >
    	<legend class="legendtitle"> Hóa đơn NCC</legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">
            		<TR>
		                     <TD class="plainlabel" width="15%" >Loại đơn mua hàng</TD>
		                     <TD class="plainlabel" width="22%" colspan="3">
		                    <select id="loaidonmuahangId" name="loaidonmuahangId" style="width: 350px;" onChange="submitform();" >
		                    	<option value=""> </option>
									<% if (pBean.getLoaidonmh().equals("0")){%>
									  	<option value="0" selected>Đơn mua hàng nhập khẩu</option>
									  	<option value="1">Đơn mua hàng trong nước</option>
									  	<option value="2">Đơn mua hàng chi phí dịch vụ</option>
									  
									<%}else if(pBean.getLoaidonmh().equals("1")) {%>
									 	<option value="0" >Đơn mua hàng nhập khẩu</option>
									  	<option value="1" selected>Đơn mua hàng trong nước</option>
									  	<option value="2">Đơn mua hàng chi phí dịch vụ</option>
									  	
									<%}else if(pBean.getLoaidonmh().equals("2")){%>											
									 	<option value="0" >Đơn mua hàng nhập khẩu</option>
									  	<option value="1" >Đơn mua hàng trong nước</option>
									  	<option value="2" selected>Đơn mua hàng chi phí dịch vụ</option>
									  	  	
									<%}else{%>
										<option value="0" >Đơn mua hàng nhập khẩu</option>
									  	<option value="1" >Đơn mua hàng trong nước</option>
									  	<option value="2" >Đơn mua hàng chi phí dịch vụ</option>
									<%} %>
									
		                    </select>
		                     </TD>    
		              </TR>	
		              	
                <TR>
                    <TD class="plainlabel" width="15%">Ngày ghi nhận </TD>
                    <TD class="plainlabel" colspan="3">
                        <input type="text" class="days" 
                               id="ngayghinhan" readonly="readonly" name="ngayghinhan" value="<%= pBean.getNgayghinhan() %>" maxlength="10"  readonly = readonly/>
                    </TD>
                </TR>
                   <TR>
                        <TD class="plainlabel" width="15%" >Nhà cung cấp</TD>
                        <TD class="plainlabel" width="22%">
		                     <select id="nccId" name="nccId" style="width: 350px;" onChange="submitform();" >
		                     	<option value="" ></option>
		                     	<% if(rsNcc != null) {
		                     		while(rsNcc.next())
		                     		{
		                     			if(pBean.getNccId().equals(rsNcc.getString("pk_seq")) ) { %>
		                     				<option value="<%= rsNcc.getString("pk_seq") %>" selected="selected" ><%= rsNcc.getString("TEN") %></option>
		                     			<% } else { %>
		                     				<option value="<%= rsNcc.getString("pk_seq") %>" ><%= rsNcc.getString("TEN") %></option>
		                     			<% }
		                     		}
		                     		rsNcc.close();
		                     	} %>
		                     </select>
                        </TD>    
                        
                         <TD class="plainlabel" width="11%" >Nhà cung cấp thay thế </TD>
                        <TD class="plainlabel" >
		                     <A href="" id="nccthaythe" rel="subcontentNCCTT">&nbsp; 
											     <img alt="Chi tạm ứng" src="../images/vitriluu.png">
										</A>
											 <DIV id="subcontentNCCTT" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 600px; max-height:250px; overflow-y:scroll; padding: 4px;">
	                    						<table width="100%" align="center">
							                        <tr class="plainlabel" >
							                            <th width="80px">Tên</th>
							                            <th width="80px">Mã số thuế</th>
							                            <th width="220px">Địa chỉ</th>							                            										                       
							                        </tr>
					                        			<tr >
					                        				<td>
					                        					<input type="text" style="width: 100%"  name="nccTTTen" id = "nccTTTen" value="<%= pBean.getNCCThayTheTen() %>" />
					                        					<input type="hidden" style="width: 100%"  name="nccTTId" id = "nccTTId" value="<%= pBean.getNCCThayTheId() %>" />
					                        				</td>
								                            <td>											                            	
								                            	<input type="text" style="width: 100%"  name="nccTTMST" id="nccTTMST" value="<%= pBean.getNCCThayTheMST() %>" />
								                            </td>
								                            <td>
								                            	<input type="text" style="width: 100%" name="nccTTDiachi" id = "nccTTDiachi" value="<%= pBean.getNCCThayTheDiaChi() %>" style="text-align: left" />
								                            </td>								                            
								                        </tr>
									            
			
	                    							</table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentNCCTT')">Hoàn tất</a>
								                     </div>
	                							</DIV>

                        </TD>                     
                    </TR>
                    
                    <TR >
                        <TD class="plainlabel" width="15%">	Chọn tiền tệ</TD>
                       	<TD class="plainlabel" colspan="3">
							<SELECT id="ttId" NAME = "ttId" style="width: 200px;height:20" onChange = "submitform();">
										<OPTION VALUE = ""></OPTION>
								<% 	if(ttRs != null){
										while(ttRs.next()){			
											if(ttRs.getString("TTID").equals(pBean.getTtId())){	%>	
												<OPTION VALUE = "<%= ttRs.getString("TTID") %>" SELECTED><%= ttRs.getString("TIENTE") %></OPTION>
								<%			}else{ %>
												<OPTION VALUE = "<%= ttRs.getString("TTID") %>" ><%= ttRs.getString("TIENTE") %></OPTION>
								<%			} 
										}		
									}	
										%>
							
							</SELECT>							
							
						</TD>
                    </TR>
                    
                   <% if(pBean.getLoaidonmh().equals("0") ) { %>
                    	<% if(!pBean.getTtId().equals("100000")){ %> 
		                    <TR>
		                    	<TD class="plainlabel" width="15%">Tỉ giá </TD>
		                       	<TD class="plainlabel" colspan="3">
		                       		<input name = "tigia" id = "tigia" value = "<%= pBean.getTigia() %>" onchange="DinhDangTiGia();" onKeyPress = "return keypress(event);" />
		                       	</TD>
		                    </TR>
                    	<%} %>
                   <%} %>
                      
                    <TR >
                        <TD class="plainlabel" width="15%">Số PO </TD>
                       	<TD class="plainlabel" colspan="3">
							
							<a href="" id="soPOLIST" rel="subcontentPO"><img alt="Số PO" src="../images/vitriluu.png"></a>
	                   		<DIV id="subcontentPO" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 350px; padding: 4px; max-height: 200px; overflow: auto;" >
					          <table width="100%" align="center">
						        <tr>
						         <th width="100px">Số PO</th>
						         <th width="100px">Ngày mua </th>
						         <th width="100px" align="center" >Chọn</th>
						        </tr>
						        <%
						        	if(poNKRs != null)
						        	{ 
						        		while(poNKRs.next()){
						        	%>
						        		<tr >
						        		  <td>						        		  
						        		  	<input  type="text"  readonly="readonly" value="<%= poNKRs.getString("soPO") %>"  style="width: 100%">
						        		  	
						        		  </td>
						        		  <td>
						        		  	<input  type="text"  readonly="readonly" value="<%= poNKRs.getString("ngaymua") %>"  style="width: 100%">
						        		  </td>
						        		  <td align="center" >
						        		  	<% if( pBean.getPoNkIdsId().contains(poNKRs.getString("pk_seq")) ) { %>
						        		  		<input value="<%= poNKRs.getString("pk_seq") %>" type="checkbox" name="poNKID" checked="checked"  > 
						        		  	<% } else { %>
						        		  		<input value="<%= poNKRs.getString("pk_seq") %>" type="checkbox" name="poNKID" > 
						        		  	<% } %>
						        		  </td>
						        		</tr>
						        	<% } } %>
						      </table>
						       <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:{dropdowncontent.hidediv('subcontentPO');submitform();}">Hoàn tất</a>
				                     </div>
						     </DIV>
							
							<script type="text/javascript">
									dropdowncontent.init("soPOLIST", "right-top", 300, "click");
							</script>
							
						</TD>
					</TR> 
                  <%--   
                  <% if(pBean.getLoaidonmh().equals("0") ) { %>
                     <TR>
						 <TD class="plainlabel" width="15%">Tổng số lượng </TD>
						  <TD class="plainlabel" colspan="3">
                            <input type="text" readonly="readonly"
                                   id="tongsoluong" name="tongsoluong" value="<%= formatter.format(Double.parseDouble(pBean.getTongsoluong())) %>" />
                        </TD>
						
                    </TR> 
               	 <%} %> --%>
                    
                             		
            </TABLE>
            <hr> 
            </div>
           	<div  align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
	      
	      	 <!-- HÓA ĐƠN NỘI ĐỊA  -->
                	
		 	      <TR class="tbheader"> 
                	<TH align="center" style="width: 10%" >Ký hiệu HĐ</TH>
                	<TH align="center" style="width: 10%" >Số hóa đơn</TH>
                	<TH align="center" style="width: 10%" >Ngày hóa đơn</TH>
               	 	<TH align="center" style="width: 10%" >Số tiền chưa thuế</TH>
               	 	<TH align="center" style="width: 12%" >Tiền điều chỉnh (+ / -)</TH>
               	 	<TH align="center" style="width: 10%" >Thuế suất (%)</TH>
               	 	<TH align="center" style="width: 10%" >Thuế GTGT</TH>
               	 	<TH align="center" style="width: 10%" >Tổng cộng</TH>
               	 	
                </TR>
                <%
                 	int sodong=10;
               	 	int h = 0;
               	 	
	               	 	
            		IErpHoadon hd = new ErpHoadon();
            		IErpHoadonSp sp = new ErpHoadonSp();
            		
               	 	if(pBean.getHdList() != null){
            			h = pBean.getHdList().size();
               	 	}
               	  	System.out.println("this.hdList.size1()"+h);
            		int conlai= 10 - h;
                	
            		for(int i = 0; i < h ;i++)
                	{  
                		hd = pBean.getHdList().get(i);
                		
                	%>
                	<TR>
                		<TD>
                        	<input type="hidden"   id="mauhoadon<%= i %>" name="mauhoadon<%= i %>" value="<%=hd.getMauhoadon() %>"  readonly="readonly"  />
                   		
                        	<input type="hidden"  id="mausohoadon<%= i %>" name="mausohoadon<%= i %>" value="<%=hd.getMausohoadon() %>"    />
                   		
                        	<input type="text"   id="kyhieuhoadon<%= i %>" name="kyhieuhoadon<%= i %>" value="<%=hd.getKyhieu() %>"   />
                   		</TD> 
                   		<TD >
                        	<input type="text" id="sohoadon<%= i %>" name="sohoadon<%= i %>" value="<%=hd.getSohoadon() %>" onkeyup="Kiemtrasohd(this)"  />
                   		</TD> 
                   		<TD>
                        	<input type="text" readonly="readonly" id="ngayhoadon<%= i %>" class="days" name="ngayhoadon<%= i %>" value="<%=hd.getNgayhoadon() %>" />
                   		</TD> 
                   		<TD align="left">
                       		<input type="text"  id="sotienchuathue<%= i %>" readonly="readonly" name="sotienchuathue<%= i %>" value="<%  if(hd.getSotienbvat().length()==0) {%> <%} else  %><%=formatter.format(Double.parseDouble(hd.getSotienbvat())) %>" style="width: 80%; text-align: right;" />              		
                       	 	
	                   		<a href="" id="ckDetail<%= i %>" rel="subcontent<%= i %>"><img alt="Số tiền chi tiết" title="Số tiền chi tiết" src="../images/vitriluu.png"></a>
	                   		<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 500px; padding: 4px; max-height: 200px; overflow: auto; ">
					          <table width="100%" align="center">
						        <tr>
						         <th width="100px">Số PO</th>
						         <th width="100px">Mã hàng</th>
						         <th width="100px">Tên hàng</th>
						         <th width="100px">ĐVT</th>
						         <th width="100px">Số lượng đặt</th>
						         <th width="100px">Dung sai</th>
						         <th width="100px">Số lượng HĐ</th> 
						         <th width="100px">Đơn giá</th>
						         <th width="100px">Thành tiền</th>
						        </tr>
						         <%
					        						        						      
						        int d = 0;
						        for(d = 0; d < hd.getPnkList().size(); d ++){
						        	sp = hd.getPnkList().get(d);
						        	%>
						        	<tr >
						        		<td>						        		  
						        		  	<input type="hidden"  readonly="readonly" value="<%= sp.getLoai() %>" name="sohoadon<%= i %>.loai" style="width: 100%">
						        		  	<input type="hidden"  readonly="readonly" value="<%= sp.getPoId() %>" name="sohoadon<%= i %>.poId" style="width: 100%">
						        		  	<%-- <input type="hidden"  readonly="readonly" value="<%= sp.getDungsai() %>" name="sohoadon<%= i %>.dungsai" style="width: 100%"> --%>
						        		  	<input  type="text"  readonly="readonly" value="<%= sp.getPoTen() %>" name="sohoadon<%= i %>.soPO" style="width: 100%">
						        		</td>
				                   			
			                   			<td>	
					        		  		<input type="text"  readonly="readonly" value="<%= sp.getSanphamMa()%>" name="sohoadon<%= i %>.ma" style="width: 100%">
					        		  		<input type = "hidden" value="<%= sp.getSanphamId() %>" name="sohoadon<%= i %>.mahang" style="width: 100%">
			                   			
					        		  	</td>
						        		  	
					        		  	<td><input type="text"  readonly="readonly" value="<%= sp.getTen()  %>" name="sohoadon<%= i %>.tenhang" style="width: 100%"></td>

					        		  	<td>					        		  	
											<input type="text"  readonly="readonly" value="<%=  sp.getDonvi()  %>" name="sohoadon<%= i %>.donvi" style="width: 100%">
										</td>
											
					        		  	<td>
					        		  		<input type="text" id="sohoadon<%= i %>.soluongdat<%= d %>" readonly="readonly"   value="<%= formatter.format( Double.parseDouble(sp.getSoluong())) %>" name="sohoadon<%= i %>.soluongdat" style="width: 100%" onKeyPress = "return keypress(event);" onkeyup="Kiemtra2(this)" onchange="ktMaxnhan();" >
					        		  						        		  		
					        		  	</td>
					        		  	<td>
				        		  			<input type="text"  readonly="readonly" value="<%= sp.getDungsai()  %>" name="sohoadon<%= i %>.dungsai" style="width: 100%">	
				        		  		</td>
					        		  	<td>
					        		  		<input type="text" id="sohoadon<%= i %>.soluong<%= d %>"   value="<%= formatter.format( Double.parseDouble(sp.getSoluonghd())) %>" name="sohoadon<%= i %>.soluong" style="width: 100%" onKeyPress = "return keypress(event);" onkeyup="Kiemtra2(this)" onchange="ktMaxnhan();" readonly="readonly" >
					        		  		<input type="hidden"  value="<%= sp.getSoluongRaHD() %>" name="sohoadon<%= i %>.soluongRaHD" >				        		  		
					        		  	</td>
						        		  	
					        		  	<td>
					        		  		<%if(pBean.getLoaidonmh().equals("2")) { // NẾU LÀ HÓA ĐƠN LOẠI DỊCH VỤ THÌ KHÔNG CHO SỬA GIÁ %>
					        		  			<input type="text" id="sohoadon<%= i %>.dongia<%= d %>"   value="<%= formatter1.format( Double.parseDouble(sp.getDongia())) %>" name="sohoadon<%= i %>.dongia" style="width: 100%"  onkeyup="Kiemtra5(this);" onKeyPress = "return keypress(event);" readonly="readonly">
					        		  		<%} else { %>
					        		  			<input type="text" id="sohoadon<%= i %>.dongia<%= d %>"  value="<%= formatter1.format( Double.parseDouble(sp.getDongia())) %>" name="sohoadon<%= i %>.dongia" style="width: 100%"  onkeyup="Kiemtra5(this);" onKeyPress = "return keypress(event);">
					        		  		<%} %>				        		  		
					        		  	</td>

					        		  	<td>
					        		  		<input type="text" readonly="readonly" id="sohoadon<%= i %>.thanhtien<%= d %>"   value="<%= formatter.format( Double.parseDouble(sp.getThanhtien())) %>" name="sohoadon<%= i %>.thanhtien" style="width: 100%"  >
					        		  		<input type="hidden" readonly="readonly" id="sohoadon<%= i %>.ngaynhandk<%= d %>"   value="<%= sp.getNgaynhan() %>" name="sohoadon<%= i %>.ngaynhandk" style="width: 100%"  >
					        		  	</td>

					        		  								        		  						        		  	
					        		</tr>
						        	<%		
							       
							        }
					        %>
						      </table>
						       <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:{dropdowncontent.hidediv('subcontent<%= i %>');Kiemtra3(<%= i %>);}">Đóng cửa sổ</a>
				                     </div>
						     </DIV>
                   		</TD> 
                   		
                   		<TD class="plainlabel" valign="middle">
                   			<%  
                   				String tienCK = "";
                   				if(hd.getChieckhau().length() > 0) 
                   				{
                   					tienCK = formatter.format(Double.parseDouble(hd.getChieckhau()));
                   				}
                   			%>
                        	<input type="text" onkeyup="Dinhdang(this)" id="chietkhau<%= i %>" name="chietkhau<%= i %>" value="<%= tienCK %>"   style="text-align: right;" />
                   		</TD> 
                   		<TD class="plainlabel" valign="middle">
                   			<%  
                   				String thuesuat= "";

                   				if(hd.getThuesuat().length() > 0 && !hd.getThuesuat().equals("0")) 
                   				{
                   					thuesuat = hd.getThuesuat();
                   				} 
                   			%>
                        	<input type="text" onKeyPress = "return keypress(event);" onkeyup="Dinhdangthue(this)" id="thuesuat<%= i %>" name="thuesuat<%= i %>" value="<%=thuesuat %>"  style=" text-align: right"  />
                   		</TD> 
                   		<TD class="plainlabel" valign="middle">
                   			<%  
                   				String vat= "";
                   				if(hd.getVAT().length() > 0) 
                   				{
                   					vat = formatter.format(Double.parseDouble(hd.getVAT()));
                   				}
                   			%>
                        	<input type="text" name="vat<%= i %>"  id="vat<%= i %>" style="text-align: right;" style="text-align: right;"  value="<%=vat%>" onKeyPress = "return keypress(event);" />
                   		</TD> 
                   		<TD class="plainlabel" valign="middle">
                        	<input type="text" readonly="readonly" style="text-align: right;"  id="tongcong<%= i %>" name="tongcong<%= i %>" value="<%=formatter.format(Double.parseDouble(hd.getSotienavat())) %>" />
                   		</TD> 
                   		</TR>
                   <% 
               			
                	}%>
                	
                	
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">

						</TD>
					 </tr>
		
            </TABLE> 
        	</div>      
     </fieldset>	
    </div>
</div>
<script type="text/javascript">
replaces();
</script>
</form>
<script type="text/javascript">
dropdowncontent.init("nccthaythe", "center-bottom", 300, "click");

jQuery(function()
		{		
			$("#nccTTMST").autocomplete("ErpNhaCungCap_ThayThe.jsp");
		});	
		
	<% 
	if(!pBean.getTinhThueNhapKhau().equals("1")) {
		for(int i = 0; i < 10 ;i++)
		{
	%>
		dropdowncontent.init("ckDetail<%= i %>", "left-top", 300, "click");
	<% } 
	}else{ %> 
		dropdowncontent.init("ckDetail0", "left-top", 300, "click");
		
<%} %> 
</script>
</BODY>
</html>
<% 
	if(rsNcc != null) rsNcc.close();
	if(ttRs != null) ttRs.close(); 

	pBean.close(); %>
