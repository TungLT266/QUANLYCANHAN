<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "geso.traphaco.erp.beans.thutien.*" %>
<%@ page  import = "geso.traphaco.erp.beans.thutien.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% NumberFormat formatter2 = new DecimalFormat("#,###,###.##"); %>

<% IErpThutienCK tthdBean = (IErpThutienCK)session.getAttribute("tthdBean"); %>
<% ResultSet kbhList = tthdBean.getKbhRs(); %>
<% ResultSet nppList = tthdBean.getNppRs(); %>
<% ResultSet htttList = tthdBean.getHtttRs(); %>
<% ResultSet ndList = tthdBean.getNoidungRs(); %>
<% ResultSet nccList = tthdBean.getNccRs(); %>
<% ResultSet nhomkhttList = tthdBean.getNhomkhttRs(); %>
<% ResultSet nvtutList = tthdBean.getNvtuRs(); %>
<% ResultSet nganhangList = tthdBean.getNganhangRs(); %>
<% ResultSet ChinhanhRs = tthdBean.getChinhanhRs(); %>
<% ResultSet tienteList = tthdBean.getTienteRs(); %>
<% List<IHoadon> hoadonList = tthdBean.getHoadonRs(); %>

<% ResultSet sotkRs = tthdBean.getSotkRs(); %>
<% ResultSet RSTaiKhoan = tthdBean.getTaikhoanRs(); %>

<% List<IDinhkhoanco> dinhkhoancoList = tthdBean.getDinhkhoancoRs(); %>
<% ResultSet SanphamRs = tthdBean.getSanphamList(); %>
<% ResultSet NganhangRs = tthdBean.getNganhangList(); %>
<% ResultSet NccRs = tthdBean.getNccList(); %>
<% ResultSet KhachhangRs = tthdBean.getKhachhangList(); %>
<% ResultSet NhanvienRs = tthdBean.getNhanvienList(); %>
<% ResultSet TaisanRs = tthdBean.getTaisanList(); %>
<% ResultSet DoituongkhacRs = tthdBean.getDoituongkhacList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%	NumberFormat formatter = new DecimalFormat("#,###,###"); %>
<%	NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<TITLE>TraphacoERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
	<LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
	<LINK rel="stylesheet" href="../css/main.css" type="text/css">
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />

<link rel="stylesheet" type="text/css" href="../css/speechbubbles.css" />
<script type="text/javascript" src="../scripts/jquery.min.js"></script>
<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
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
	function upperLetters(element)
	{
		element.value = element.value.toUpperCase();
	}

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
		 Thanhtoan(100000000);
	}
	function replaces()
	{
		var nppTen =  document.getElementsByName("npp");
		var nppId =  document.getElementsByName("nppId");
	
			if(nppTen.item(0).value != "" )
			{
				var temp = nppTen.item(0).value;
				var pos = parseInt(temp.indexOf(" - "));
				if(pos > 0 && temp.indexOf(" [ ")>0)
				{
					/// PK_SEQ VÀ nppTEN phân cách bằng ' - ', mã và tên phân cách bằng ' -- '
					
					nppId.item(0).value = temp.substring(0, pos);

					temp = temp.substr(parseInt(temp.indexOf(" [ ")) + 3);
					nppTen.item(0).value = temp.substring(0, parseInt(temp.indexOf(" ]")));
					if(nppId.item(0).value != "")
					{
						ChangeTienTe();
					}
				}
			}
			else
			{
				nppId.item(0).value = "";
				nppTen.item(0).value = "";
			}
			setTimeout(replaces, 500);
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
		 
		 var sotienphanbo = document.getElementById("sotienthanhtoan").value.replace(/,/g,"");
		 //alert(sotienphanbo);

		 var tongtien = 0;
		 for(i = 0; i < kyhieuhd.length; i++)
		 {
			tienAvat =  avat.item(i).value.replace(/,/g, "");
			tongtien = parseFloat(tongtien) + parseFloat(tienAvat);
			
			if(tongtien < parseFloat(sotienphanbo))
			{
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienAvat, 0));
				conlai.item(i).value = 0;
				trahet.item(i).checked = true;
			}
			else
			{
				tongtien = parseFloat(tongtien) - parseFloat(tienAvat);
				var tienconlai = parseFloat(sotienphanbo) - parseFloat(tongtien);
				
				thanhtoan.item(i).value = DinhDangTien(roundNumber(tienconlai, 0));
				conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(tienAvat) - parseFloat(tienconlai), 0));
				if(parseFloat(tienAvat) - parseFloat(tienconlai) <= 0)
					trahet.item(i).checked = true;
				else
					trahet.item(i).checked = false;
				
				break;
			}
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
	
	function validateSpecialCharacters(e) 
	{ 
		 
		var keypressed = null;
		if (window.event)
			keypressed = window.event.keyCode; //IE
		else
			keypressed = e.which; //NON-IE, Standard
		
		if (keypressed < 48 || (keypressed > 57 & keypressed < 65) || (keypressed > 90 & keypressed < 97) || (keypressed > 122))
		{ 
			if (keypressed == 8 || keypressed == 127 || keypressed == 46|| keypressed == 45 || keypressed == 47	)
			{
				return;
			}
			return false;
		}
	} 
	
	 function saveform()
	 {	
		 var ngayghinhan = document.getElementById("ngaychungtu");
		 if(ngayghinhan.value == "")
		 {
			alert("Vui lòng nhập ngày chứng từ");
			return false;
		 }

		 var noidungtt = document.getElementById("noidungId");
		 if(noidungtt.value != '100002')
		 {
			 var thuduoc = document.getElementById("thuduoc");
			 if(thuduoc.value == "")
			 {
				alert("Vui lòng nhập số tiền thực thu của khách hàng");
				return false;
			 }
		 }
		 
		 var isthuhoCN = document.getElementById("isthuhoCN");
		 var nppId = document.getElementById("nppId");
		 
		 if (isthuhoCN != null && isthuhoCN.value == "1" && (nppId == null || nppId.value == ""))
		 {
			alert('Vui lòng chọn "Khách hàng"!');
			return false;
		 }

		 var ischuyenCN = document.getElementById("ischuyenCN");
		 nppId = document.getElementById("nppId");
		 
		 if (ischuyenCN != null && ischuyenCN.value == "1" && (nppId == null || nppId.value == ""))
		 {
			alert('Vui lòng chọn "Khách hàng"!');
			return;
		 }
		 var avat = document.getElementsByName("avat");
		 var thanhtoan=document.getElementsByName("thanhtoan");
		 var sohd=document.getElementsByName("sohd");
		 for(i = 0; i < avat.length; i++)
		 {
			 var Avat =  parseFloat(avat.item(i).value.replace(/,/g, ""));
			 console.log("Avat "+Avat);
			
			 var Thanhtoan=parseFloat(thanhtoan.item(i).value.replace(/,/g, ""));
			 console.log("thanhtoan "+Thanhtoan);
			 var Sohd=sohd.item(i).value;
			 console.log("Sohd "+Sohd);
			 if(Thanhtoan>Avat)
			 {
				 console.log("dau xanh dau nan, dau do "+Sohd);
			 	alert('Vui lòng nhập số tiền thanh toán nhỏ hoặc bằng tiền hóa đơn ở số hóa đơn :'+Sohd);
				return false; 
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
	 
	
	function totalThanhToan()
    {
        var thanhtoan = document.getElementsByName("thanhtoan");
        var avat = document.getElementsByName("avat" );
        var stgoc = document.getElementsByName("stgoc" );
        var avatNT = document.getElementsByName("sotienNT" );
         
        var tongthanhtoan=0;
        var tongavat=0;
        var tongavatNT=0;
        var tongconlai=0;
        var tongstgoc=0;
        
        var tienteId = document.getElementById("tienteId").value;
        
        if(tienteId == "100000")  // VND
        {
	           for(i = 0; i < stgoc.length; i++)
	           {
	                   var STgoc;
	                   if(stgoc.item(i).value != '' ){
	                	   STgoc = stgoc.item(i).value.replace(/,/g, "");
	                        
	                	   tongstgoc = parseFloat(tongstgoc) + parseFloat(STgoc);  
	                  } else{
	                	  STgoc = 0;
	                  }
	           }
	          
	           for(i = 0; i < thanhtoan.length; i++)
	          {
	                  var tientt;
	                  if(thanhtoan.item(i).value != '' ){
	                       tientt = thanhtoan.item(i).value.replace(/,/g, "");
	                       
	                       tongthanhtoan = parseFloat(tongthanhtoan) + parseFloat(tientt);  
	                 } else{
	                       tientt = 0;
	                 }
	          }
	          
	           for(i = 0; i < avat.length; i++)
	          {
	                  var tienavat;
	                  if(avat.item(i).value != '' ){
	                       tienavat = avat.item(i).value.replace(/,/g, "");
	                       tongavat = parseFloat(tongavat) + parseFloat(tienavat);
	                       
	                 } else{
	                       avat = 0;
	                       
	                 }      
	                 
	          }
	           tongconlai =  parseFloat(tongavat) - parseFloat(tongthanhtoan);

	           document.getElementById( "totalthanhtoan").value = DinhDangTien(roundNumber(tongthanhtoan, 0));
	           document.getElementById( "totaltongtien").value = DinhDangTien(roundNumber(tongavat, 0));
	           document.getElementById( "totaltongtien2").value = DinhDangTien(roundNumber(tongstgoc, 0));
	           document.getElementById( "totalconlai").value = DinhDangTien(roundNumber(tongconlai, 0));
        }
        else // NGOAI TE
        {
     	   for(i = 0; i < stgoc.length; i++)
	           {
	                   var STgoc;
	                   if(stgoc.item(i).value != '' ){
	                	   STgoc = stgoc.item(i).value.replace(/,/g, "");
	                        
	                	   tongstgoc = parseFloat(tongstgoc) + parseFloat(STgoc);  
	                  } else{
	                	  STgoc = 0;
	                  }
	           }
	          
	           for(i = 0; i < thanhtoan.length; i++)
	          {
	                  var tientt;
	                  if(thanhtoan.item(i).value != '' ){
	                       tientt = thanhtoan.item(i).value.replace(/,/g, "");
	                       
	                       tongthanhtoan = parseFloat(tongthanhtoan) + parseFloat(tientt);  
	                 } else{
	                       tientt = 0;
	                 }
	          }
	          
	           for(i = 0; i < avat.length; i++)
	          {
	                  var tienavat;
	                  if(avat.item(i).value != '' ){
	                       tienavat = avat.item(i).value.replace(/,/g, "");
	                       tongavat = parseFloat(tongavat) + parseFloat(tienavat);
	                       
	                 } else{
	                       avat = 0;
	                       
	                 }      	                 
	          }
	           
	           for(i = 0; i < avatNT.length; i++)
		       {
		                  var tienavatNT;
		                  if(avatNT.item(i).value != '' ){
		                       tienavatNT = avatNT.item(i).value.replace(/,/g, "");
		                       tongavatNT = parseFloat(tongavatNT) + parseFloat(tienavatNT);
		                       
		                 } else{
		                       avatNT = 0;
		                       
		                 }      	                 
		        }
	           
	           tongconlai =  parseFloat(tongavatNT) - parseFloat(tongthanhtoan);

	           document.getElementById( "totalthanhtoan").value = DinhDangDonGia(tongthanhtoan.toFixed(2));
	           document.getElementById( "totaltongtien").value = DinhDangTien(roundNumber(tongavat, 0));
	           document.getElementById( "totaltongtienNT").value = DinhDangDonGia(tongavatNT.toFixed(2));
	           document.getElementById( "totaltongtien2").value = DinhDangDonGia(tongstgoc.toFixed(2));
	           document.getElementById( "totalconlai").value = DinhDangDonGia(tongconlai.toFixed(2));
	           
        }
 }


	
	 
	 function ThanhToan(n)
	 {
		 var trahet = document.getElementsByName("trahet");
		 var avat = document.getElementsByName("avat");
		 var thanhtoan = document.getElementsByName("thanhtoan");
		 var conlai = document.getElementsByName("conlai");
		 
		 var tigia = document.getElementsByName("tigia");
		 var tg = tigia.item(0).value.replace(/,/g,"");
		 
		 var thuduoc = document.getElementsByName("thuduoc");
		 var thuduocNT = document.getElementsByName("thuduocNT");
		 
		 var cpnganhang = document.getElementsByName("cpnganhang");
		 var cpnganhangNT = document.getElementsByName("cpnganhangNT");
		 
		 var chietkhau = document.getElementsByName("chietkhau");
		 var chietkhauNT = document.getElementsByName("chietkhauNT");
		 
		 var chenhlech = document.getElementsByName("chenhlech");
		 
		 var tienteId = document.getElementsByName("tienteId");
		 	 
		 var tongtienNT = 0;
		 var tongtienVND = 0;
		 
		 var ndId = document.getElementsByName("noidungId");
		 
		 var sotienNT;
		 
		 var tigiaHD=0;
		 
		 if(ndId.item(0).value == "100000" || ndId.item(0).value == "100003" ||ndId.item(0).value == "100004"){ // Thu tien ban hang +TU +KQ
		 	for(i = 0; i < trahet.length; i++)
		 	{
				//alert(i); 
				
				if(trahet.item(i).checked ) // truong hop chon tra het
				{
					if(n == 100000000){  // tuong hop click chon tra het nhung thay doi thanh toan
						var tt;
						if(thanhtoan.item(i).value != ''){
							tt = thanhtoan.item(i).value.replace(/,/g,"");
						}else{
							tt = 0;
						}												
						
						if(tienteId.item(0).value == "100000"){ // Neu tien te la VND
						
							if( Math.abs(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
								conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));

								tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
					
								thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
								trahet.item(i).checked = false;
							
							}else{
								
								conlai.item(i).value = "0";
															
								var tt = thanhtoan.item(i).value.replace(/,/g,"");

								tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);					
								
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));

								thanhtoan.item(i).value = avat.item(i).value;

								trahet.item(i).checked = true;
							}
						}else{ // Neu tien la ngoai te
							
							sotienNT = document.getElementsByName("sotienNT");

							if(sotienNT.item(i).value.replace(/,/g,"") > 0)
							{
								tigiaHD = parseFloat(avat.item(i).value.replace(/,/g,""))/ parseFloat(sotienNT.item(i).value.replace(/,/g,""));
							}
				   					    
							if( Math.abs(parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)) > 0){
								trahet.item(i).checked = false;
								
								conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));

								tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
								tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tigiaHD));
					
								thanhtoan.item(i).value = DinhDangDonGia((parseFloat(tt)).toFixed(2));
								
								var temp = parseFloat(sotienNT.item(i).value.replace(/,/g,""));
								
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
								sotienNT.item(i).value = DinhDangDonGia((temp).toFixed(2));
								
															
							}else{								

								conlai.item(i).value = "0";
								thanhtoan.item(i).value = sotienNT.item(i).value;
								
								var tt = thanhtoan.item(i).value.replace(/,/g,"");

								tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
								tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tigiaHD));
							
								trahet.item(i).checked = true;
								
								avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
								
								var temp = parseFloat(sotienNT.item(i).value.replace(/,/g,""));
								sotienNT.item(i).value = DinhDangDonGia((temp).toFixed(2));

								thanhtoan.item(i).value = sotienNT.item(i).value;
							}
							
						}
					}else{ // Chon tra het
						
						if(tienteId.item(0).value == "100000"){ // Neu tien te la VND
//							alert("I am here 1");
							conlai.item(i).value = "0";
							
							var tt = avat.item(i).value.replace(/,/g,"");

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);							
							
							thanhtoan.item(i).value = avat.item(i).value;
							
						}else{
							sotienNT = document.getElementsByName("sotienNT");
							
							if(sotienNT.item(i).value.replace(/,/g,"") > 0)
							{
								tigiaHD = parseFloat(avat.item(i).value.replace(/,/g,""))/ parseFloat(sotienNT.item(i).value.replace(/,/g,""));
							}
							 
							conlai.item(i).value = "0.00";
							
							thanhtoan.item(i).value = sotienNT.item(i).value;
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");

							tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)* parseFloat(tigiaHD));
																			
						}
					}
				}
				else if(i == n){ // truong hop bo chon tra het
					thanhtoan.item(i).value = "0";				
				
					if(tienteId.item(0).value == "100000"){ // Neu tien te la VND
						conlai.item(i).value = avat.item(i).value;
					}else{
						sotienNT = document.getElementsByName("sotienNT");
						conlai.item(i).value = sotienNT.item(i).value;
					}
					
				}else  // Thay doi tai cac field du lieu
				{
					var tt;
					if(thanhtoan.item(i).value != ''){
						tt = thanhtoan.item(i).value.replace(/,/g,"");
						 
					}else{
						tt = 0;
					}					
					
					if(tienteId.item(0).value == "100000"){ // Neu tien te la VND
						if( Math.abs(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0 ){
							conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(avat.item(i).value.replace(/,/g,"")) - parseFloat(tt), 0));
					
							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);

							thanhtoan.item(i).value = DinhDangTien(roundNumber(tt, 0));
							
							avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
							
						}else{
							thanhtoan.item(i).value = avat.item(i).value;
							conlai.item(i).value = parseFloat(avat.item(i).value) - parseFloat(thanhtoan.item(i).value);
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");

							tongtienVND = parseFloat(tongtienVND) + parseFloat(tt);
							
							trahet.item(i).checked = true;

							var temp = sotienNT.item(i).value.replace(/,/g,"");
							sotienNT.item(i).value = DinhDangDonGia((parseFloat(temp)).toFixed(2));
						    
						}
					}else{  // Neu la ngoai te
						sotienNT = document.getElementsByName("sotienNT");
						if(sotienNT.item(i).value.replace(/,/g,"") > 0)
						{
							tigiaHD = parseFloat(avat.item(i).value.replace(/,/g,""))/ parseFloat(sotienNT.item(i).value.replace(/,/g,""));
						}
						
						if( Math.abs(parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)) >= 0){
							conlai.item(i).value = DinhDangDonGia((parseFloat(sotienNT.item(i).value.replace(/,/g,"")) - parseFloat(tt)).toFixed(2));
					
							tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tigiaHD));

							avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
							thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tt).toFixed(2));
														
						}else{
							conlai.item(i).value = "0";
							thanhtoan.item(i).value = sotienNT.item(i).value;
							
							var tt = thanhtoan.item(i).value.replace(/,/g,"");

							tongtienNT = parseFloat(tongtienNT) + parseFloat(tt);
							tongtienVND = parseFloat(tongtienVND) + (parseFloat(tt)*parseFloat(tigiaHD));
						
							trahet.item(i).checked = true;
							
							avat.item(i).value = DinhDangTien(roundNumber(avat.item(i).value.replace(/,/g,""), 0));
							
							var temp = parseFloat(sotienNT.item(i).value.replace(/,/g,""));
							sotienNT.item(i).value = DinhDangDonGia((temp).toFixed(2));

							thanhtoan.item(i).value = sotienNT.item(i).value;


						}
						var temp = sotienNT.item(i).value.replace(/,/g,"");
						sotienNT.item(i).value = DinhDangDonGia((parseFloat(temp)).toFixed(2));
					}
				}
				
			}
		 
		 			
		 
		 	var thu = 0;
		 
		 	var cp = 0;
		 	
		 	var ck = 0;
		 
		 	if(tienteId.item(0).value == "100000"){ // Neu tien te la VND
			 	/* if(thuduoc.item(0).value != ''){
					 thu = thuduoc.item(0).value.replace(/,/g,"");
				 
				 }else{
					 thu = 0;
				 } */
				 
				 // Từ ngày 21/1: sửa  Số tiền thực thu = Số tiền thanh toán
				 thu = tongtienVND;

			 	if(cpnganhang.item(0).value != ''){
					 cp = cpnganhang.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 cp = 0;
			 	}
			 	
			 	if(chietkhau.item(0).value != ''){
					 ck = chietkhau.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 ck = 0;
			 	}

		 		document.getElementById("sotienthanhtoan").value = DinhDangTien(roundNumber(tongtienVND, 0));
		 		
		 		document.getElementById("chietkhau").value = DinhDangTien(roundNumber(ck, 0));
		 		
		 		document.getElementById("cpnganhang").value = DinhDangTien(roundNumber(cp, 0));
	 		//	document.getElementById("sotienthanhtoanNT").value = "0";
	 			
		 		document.getElementById("thuduoc").value = DinhDangTien(roundNumber(thu, 0));
		 		
		 		var cl = parseFloat(tongtienVND) - parseFloat(thu) - parseFloat(cp) - parseFloat(ck);

		 		document.getElementById("chenhlech").value = DinhDangTien(roundNumber(cl, 0));
		 
		 	}else{
		 		
		 		document.getElementById("sotienthanhtoanNT").value = DinhDangDonGia((tongtienNT).toFixed(2) );
		 		
		 		document.getElementById("sotienthanhtoan").value = DinhDangTien(roundNumber(tongtienVND, 0));
				 		 			
				/* if(thuduocNT.item(0).value != ''){
					thu = thuduocNT.item(0).value.replace(/,/g,"");
				}else{
					thu = 0;
				} */
				
				// Từ ngày 21/1: sửa  Số tiền thực thu = Số tiền thanh toán
				 thu = tongtienNT;
				
		 		document.getElementById("thuduoc").value = DinhDangTien(roundNumber(parseFloat(thu)*parseFloat(tg), 0) );
							 	
			 	document.getElementById("thuduocNT").value = DinhDangDonGia(thu);
			 	
			 	if(cpnganhangNT.item(0).value != ''){
					 cp = cpnganhangNT.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 cp = 0;
			 	}

			 	if(chietkhauNT.item(0).value != ''){
					 ck = chietkhauNT.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 ck = 0;
			 	}
			 	
			 	
				document.getElementById("cpnganhang").value = DinhDangTien(roundNumber(parseFloat(cp)*parseFloat(tg), 0));
								
				document.getElementById("chietkhau").value = DinhDangTien(roundNumber(parseFloat(ck)*parseFloat(tg), 0));
				

		 		var cl = parseFloat(tongtienVND) - (parseFloat(thu) + parseFloat(cp) + parseFloat(ck))*parseFloat(tg);

		 		document.getElementById("chenhlech").value = DinhDangTien(roundNumber(cl, 0));
			 
			 	document.getElementById("tigia").value = DinhDangTien(roundNumber(tg, 0));
		 		
		 	}
		 }else{ // Truong hop chon Khach hang tra truoc
			if(tienteId.item(0).value != "100000"){	// Truong hop ngoai te		 
				 var thuNT = 0;
				 if(thuduocNT.item(0).value != ''){
					 thuNT = thuduocNT.item(0).value.replace(/,/g,"");
					 
				 }else{
					 thuNT = 0;
				 }
				 
				 document.getElementById("thuduoc").value = DinhDangTien(roundNumber(parseFloat(thuNT)*parseFloat(tg), 0));
				 document.getElementById("thuduocNT").value = DinhDangDonGia(document.getElementById("thuduocNT").value);

				var cpNT = 0;
				if(cpnganhangNT.item(0).value != ''){
					 cpNT = cpnganhangNT.item(0).value.replace(/,/g,"");
						 
				}else{
					 cpNT = 0;
				}

				document.getElementById("cpnganhang").value = DinhDangTien(roundNumber(parseFloat(cpNT)*parseFloat(tg), 0));
				document.getElementById("cpnganhangNT").value = DinhDangDonGia(document.getElementById("cpnganhangNT").value);
				
				var tongNT = parseFloat(thuNT) + parseFloat(cpNT);
				
				
				document.getElementById("tongVND").value = DinhDangTien(roundNumber(parseFloat(tongNT)*parseFloat(tg), 0));
				document.getElementById("tongNT").value = DinhDangDonGia(tongNT);
				
			}else{ //Khach hang tra truoc, tien VND
				 var thu = 0;
				 if(thuduoc.item(0).value != ''){
					 thu = thuduoc.item(0).value.replace(/,/g,"");
					 
				 }else{
					 thu = 0;
				 }
				 document.getElementById("thuduoc").value = DinhDangDonGia(document.getElementById("thuduoc").value); 


				var cp = 0;
				if(cpnganhang.item(0).value != ''){
					 cp = cpnganhang.item(0).value.replace(/,/g,"");
						 
				}else{
					 cp = 0;
				}

				document.getElementById("cpnganhang").value = DinhDangDonGia(document.getElementById("cpnganhang").value);
				
				var tongVND = parseFloat(thu) + parseFloat(cp);
								
				document.getElementById("tongVND").value = DinhDangTien(roundNumber(tongVND, 0));				
			}
			 
	 	}
		totalThanhToan();
	 }
	 
	 function ChangeTienTe(){
		 document.forms['tthdForm'].action.value= 'changeTT';
	     document.forms["tthdForm"].submit();
	 }

	 function submitform()
	 { 
		 //ThanhToan(100);
		 document.forms['tthdForm'].action.value='submit';
	     document.forms["tthdForm"].submit();
	 }
	 
	 function checkThuHoCN()
	 { 
	 	document.getElementById("checkThuHoChiNhanh").value = "1";
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
	
	 function PhanBoTien()
		{	
			 var kyhieuhd = document.getElementsByName("kyhieuhd");
			 var trahet = document.getElementsByName("trahet");
			 var avat = document.getElementsByName("avat");
			 var thanhtoan = document.getElementsByName("thanhtoan");
			 var conlai = document.getElementsByName("conlai");
			 var tigiahd = document.getElementsByName("tigiaHD");
			 
			 var tienteId = document.getElementById("tienteId").value;
			 
			 for(j = 0; j < kyhieuhd.length; j++)
			 {
				 thanhtoan.item(j).value = "";
				 conlai.item(j).value = "";
				 trahet.item(j).checked = false;
			 }
			 
			 var sotienphanbo = document.getElementById("sotienthanhtoanNT").value.replace(/,/g,"");
			 var sotienphanboVND = document.getElementById("sotienthanhtoan").value.replace(/,/g,"");
			 
			 var phinganhang = document.getElementById("cpnganhangNT").value.replace(/,/g,"");
			 var phinganhangVND = document.getElementById("cpnganhang").value.replace(/,/g,"");
			 var thuduoc = document.getElementById("thuduoc").value.replace(/,/g,"");
			 var chietkhau = document.getElementById("chietkhauNT").value.replace(/,/g,"");
			 var chietkhauVND = document.getElementById("chietkhau").value.replace(/,/g,"");
			 var chenhlech = document.getElementsByName("chenhlech");
			 var chenhlechVND = document.getElementsByName("chenhlechVND");
			 
			 

			 var tongtien = 0;
			 var tongtienVND = 0;
			 var tongthanhtoan = 0;
			 var tongthanhtoanVND = 0;
			 
			 for(i = 0; i < kyhieuhd.length; i++)
			 {
				tienAvat =  avat.item(i).value.replace(/,/g, "");
				tongtien = parseFloat(tongtien) + parseFloat(tienAvat);
				
				var tg = tigiahd.item(i).value.replace(/,/g, "");
				
				if(tongtien < parseFloat(sotienphanbo))
				{
					if(tienteId == "100000")
					{
						thanhtoan.item(i).value = DinhDangTien(roundNumber(tienAvat, 0));
						tongthanhtoan = tongthanhtoan + parseFloat(tienAvat);
						conlai.item(i).value = 0;
						trahet.item(i).checked = true;
					}else
					{
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tienAvat).toFixed(2));
						tongthanhtoan = tongthanhtoan + parseFloat(tienAvat);
						tongthanhtoanVND = tongthanhtoanVND + (parseFloat(tienAvat)*parseFloat(tg)) ;
						conlai.item(i).value = 0;
						trahet.item(i).checked = true;
					}	
				}
				else
				{
					tongtien = parseFloat(tongtien) - parseFloat(tienAvat);
					var tienconlai = parseFloat(sotienphanbo) - parseFloat(tongtien);
					
					if(tienteId == "100000")
					{
						thanhtoan.item(i).value = DinhDangTien(roundNumber(tienconlai, 0));
						tongthanhtoan = tongthanhtoan + tienconlai;
						
						conlai.item(i).value = DinhDangTien(roundNumber(parseFloat(tienAvat) - parseFloat(tienconlai), 0));
					}else
					{
						thanhtoan.item(i).value = DinhDangDonGia(parseFloat(tienconlai).toFixed(2));
						tongthanhtoan = tongthanhtoan + tienconlai;
						tongthanhtoanVND = tongthanhtoanVND + parseFloat(tienconlai)*parseFloat(tg) ;
						
						conlai.item(i).value = DinhDangDonGia((parseFloat(tienAvat) - parseFloat(tienconlai)).toFixed(2));
					}
				
					
					if(parseFloat(tienAvat) - parseFloat(tienconlai) <= 0)
						trahet.item(i).checked = true;
					else
						trahet.item(i).checked = false;
					
					break;
				}			
			 }
			 
			 
			
			 
			 if(tienteId == "100000")
			 {
			    
				chenhlech.item(0).value = DinhDangTien(roundNumber(parseFloat(sotienphanbo) - tongthanhtoan), 0);
				 
			 }else
			 {				
				 chenhlech.item(0).value = DinhDangDonGia((parseFloat(sotienphanbo) - tongthanhtoan).toFixed(2));

				 var clVND= parseFloat(sotienphanboVND) - parseFloat(tongthanhtoanVND);
				
				 chenhlechVND.item(0).value = DinhDangTien(roundNumber(clVND, 0));
			 }
			 		 
		}
	 
	 function TinhChenhLech()
	 {
		var ndId = document.getElementsByName("noidungId");
		 
		var tigia = document.getElementsByName("tigia");
		var tg = tigia.item(0).value.replace(/,/g,"");
		 
		var sotienthanhtoan = document.getElementsByName("sotienthanhtoan");
		var sotienthanhtoanNT = document.getElementsByName("sotienthanhtoanNT");
		
		var thuduoc = document.getElementsByName("thuduoc");
		var thuduocNT = document.getElementsByName("thuduocNT");
		 
		var cpnganhang = document.getElementsByName("cpnganhang");
		var cpnganhangNT = document.getElementsByName("cpnganhangNT");
		 
		var chietkhau = document.getElementsByName("chietkhau");
		var chietkhauNT = document.getElementsByName("chietkhauNT");
		 
		var chenhlech = document.getElementsByName("chenhlech");
		 
	    var tienteId = document.getElementsByName("tienteId");
	    		 
	    var thu = 0;
	    var stthanhtoan = 0;
	    var stthanhtoanNT = 0;
	 	var cp = 0;	 	
	 	var ck = 0;
	 
	 	if(ndId.item(0).value == "100000" || ndId.item(0).value == "100003"){ // Thu tien ban hang
	 		if(tienteId.item(0).value == "100000"){ // Neu tien te la VND

		 		if(sotienthanhtoan.item(0).value != ''){
		 			stthanhtoan = sotienthanhtoan.item(0).value.replace(/,/g,"");
				 
			 	}else{
			 		stthanhtoan = 0;
			 	}
		 	
		 		if(thuduoc.item(0).value != ''){
					 thu = thuduoc.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 thu = 0;
			 	}
		 	
			 	if(cpnganhang.item(0).value != ''){
					 cp = cpnganhang.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 cp = 0;
			 	}
			 	
			 	if(chietkhau.item(0).value != ''){
					 ck = chietkhau.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 ck = 0;
			 	}

		 		document.getElementById("sotienthanhtoan").value = DinhDangTien(roundNumber(stthanhtoan, 0));
		 		
		 		document.getElementById("chietkhau").value = DinhDangTien(roundNumber(ck, 0));
		 		
		 		document.getElementById("cpnganhang").value = DinhDangTien(roundNumber(cp, 0));
	 			
		 		document.getElementById("thuduoc").value = DinhDangTien(roundNumber(thu, 0));
		 		
		 		var cl = parseFloat(stthanhtoan) - parseFloat(thu) - parseFloat(cp) - parseFloat(ck);

		 		document.getElementById("chenhlech").value = DinhDangTien(roundNumber(cl, 0));
		 
		 	}else{
		 		if(sotienthanhtoanNT.item(0).value != ''){
		 			stthanhtoanNT = sotienthanhtoanNT.item(0).value.replace(/,/g,"");
				 
			 	}else{
			 		stthanhtoanNT = 0;
			 	}
		 		
		 		if(sotienthanhtoan.item(0).value != ''){
		 			stthanhtoan = sotienthanhtoan.item(0).value.replace(/,/g,"");
				 
			 	}else{
			 		stthanhtoan = 0;
			 	}
		 		
		 		if(thuduoc.item(0).value != ''){
					 thu = thuduoc.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 thu = 0;
			 	}
		 		
		 		document.getElementById("sotienthanhtoanNT").value = DinhDangDonGia((stthanhtoanNT).toFixed(2) );
		 		
		 		document.getElementById("sotienthanhtoan").value = DinhDangTien(roundNumber(stthanhtoan, 0));
				 		 			
				
		 		document.getElementById("thuduoc").value = DinhDangTien(roundNumber(parseFloat(thu)*parseFloat(tg), 0) );
							 	
			 	document.getElementById("thuduocNT").value = DinhDangDonGia(thu);
			 	
			 	if(cpnganhangNT.item(0).value != ''){
					 cp = cpnganhangNT.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 cp = 0;
			 	}

			 	if(chietkhauNT.item(0).value != ''){
					 ck = chietkhauNT.item(0).value.replace(/,/g,"");
				 
			 	}else{
					 ck = 0;
			 	}
			 	
			 	
				document.getElementById("cpnganhang").value = DinhDangTien(roundNumber(parseFloat(cp)*parseFloat(tg), 0));
								
				document.getElementById("chietkhau").value = DinhDangTien(roundNumber(parseFloat(ck)*parseFloat(tg), 0));
				

		 		var cl = parseFloat(stthanhtoan) - (parseFloat(thu) + parseFloat(cp) + parseFloat(ck))*parseFloat(tg);

		 		document.getElementById("chenhlech").value = DinhDangTien(roundNumber(cl, 0));
			 
			 	document.getElementById("tigia").value = DinhDangTien(roundNumber(tg, 0));
			 		
			 }
	 	
	 	}else // Khách hàng trả trước && Thu khác
	 	{
			if(tienteId.item(0).value != "100000"){	// Ngoai te		 
				 var thuNT = 0;
				 if(thuduocNT.item(0).value != ''){
					 thuNT = thuduocNT.item(0).value.replace(/,/g,"");
					 
				 }else{
					 thuNT = 0;
				 }
				 
				 document.getElementById("thuduoc").value = DinhDangTien(roundNumber(parseFloat(thuNT)*parseFloat(tg), 0));
				 document.getElementById("thuduocNT").value = DinhDangDonGia(document.getElementById("thuduocNT").value);

				var cpNT = 0;
				if(cpnganhangNT.item(0).value != ''){
					 cpNT = cpnganhangNT.item(0).value.replace(/,/g,"");
						 
				}else{
					 cpNT = 0;
				}

				document.getElementById("cpnganhang").value = DinhDangTien(roundNumber(parseFloat(cpNT)*parseFloat(tg), 0));
				document.getElementById("cpnganhangNT").value = DinhDangDonGia(document.getElementById("cpnganhangNT").value);
				
				var tongNT = parseFloat(thuNT) + parseFloat(cpNT);
				
				
				document.getElementById("tongVND").value = DinhDangTien(roundNumber(parseFloat(tongNT)*parseFloat(tg), 0));
				document.getElementById("tongNT").value = DinhDangDonGia(tongNT);
				
			}else{ //Tien VND
				 var thu = 0;
				 if(thuduoc.item(0).value != ''){
					 thu = thuduoc.item(0).value.replace(/,/g,"");
					 
				 }else{
					 thu = 0;
				 }
				 document.getElementById("thuduoc").value = DinhDangDonGia(document.getElementById("thuduoc").value); 


				var cp = 0;
				if(cpnganhang.item(0).value != ''){
					 cp = cpnganhang.item(0).value.replace(/,/g,"");
						 
				}else{
					 cp = 0;
				}

				document.getElementById("cpnganhang").value = DinhDangDonGia(document.getElementById("cpnganhang").value);
				
				var tongVND = parseFloat(thu) + parseFloat(cp);
								
				document.getElementById("tongVND").value = DinhDangTien(roundNumber(tongVND, 0));				
			}
			 
	 	
	 	}
	 	
	 }
	 	
	 function TinhTienDKC()
	 {
		 var taikhoanId = document.getElementsByName("taikhoanId");
		 var sotienco = document.getElementsByName("sotienco");
		 
		 var tienteId = document.getElementsByName("tienteId");		 		
		 
		 var tigia = document.getElementsByName("tigia");
		  var tg = tigia.item(0).value.replace(/,/g,"");
		  
		  if(tg == '') tg = '0' ;
			
		  var thuduocNT = 0;
		  var thuduoc = 0;
		
		 for(var i = 0; i < taikhoanId.length ; i ++)
		 {
			 if(taikhoanId.item(i).value != '' )
			 {				 		
				 var stc = sotienco.item(i).value.replace(/,/g,"") ;
				 if(stc=='') stc = '0';
				 
				 if(tienteId.item(0).value != '100000')  // Ngoại tệ
				 {							 											 
						 sotienco.item(i).value = DinhDangDonGia(parseFloat(stc).toFixed(2)) ;
						 thuduocNT = parseFloat(thuduocNT) + roundNumber(parseFloat(stc), 2) ;
						 thuduoc =  parseFloat(thuduoc) + Math.round(roundNumber(parseFloat(stc), 2)*parseFloat(tg)) ;
				 }
				 else  // Tiền việt
				 {					 										 
						 sotienco.item(i).value = DinhDangTien(Math.round(stc)) ;
						 thuduoc = parseFloat(thuduoc) + Math.round(parseFloat(stc)) ;
				 }
			 }
			 
		 }
		 
		 if(tienteId.item(0).value != '100000')  // Ngoại tệ
		 {
			 document.getElementById("thuduocNT").value = DinhDangDonGia(thuduocNT.toFixed(2));	
		 }
		 document.getElementById("thuduoc").value = DinhDangTien(Math.round(thuduoc));		 
	 }

	 function goBack()
	 {
	  	window.history.back();
	 }
	 function ChangeNCT()
	 { 
		 var ngaychungtu = document.getElementById("ngaychungtu");
		 var ngayghiso = document.getElementById("ngayghiso");
		  if(ngaychungtu.value!="")
		{
			  	ngayghiso.value=ngaychungtu.value;
			  //submit form để load lại số chứng từ
				document.forms['tthdForm'].action.value='changeNgayChungTu';
				document.forms["tthdForm"].submit();
		 }	
	 }
</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2(); });
     
</script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="tthdForm" method="post" action="../../ErpThutienCKUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value="<%= tthdBean.getId() %>">
<input type="hidden" name="checkThuHoChiNhanh" id="checkThuHoChiNhanh" value="">

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ &gt; Công nợ phải thu &gt;Giấy báo có &gt; Cập nhật
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "javascript: goBack();" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
       <%--  <A href="../../ErpThutienPdfSvl?userId=<%= tthdBean.getUserId() %>&id=<%= tthdBean.getId() %>" >
	        <IMG src="../images/Printer30.png" title="In phieu" alt="In phieu" border ="1px" style="border-style:outset"></A> --%>
    </div>
  	
  	<div align="left" style="width:100%%; float:none; clear:left">
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
                    <TD width="220px" class="plainlabel" valign="top">Ngày chứng từ</TD>
                    <TD class="plainlabel" width="330px" valign="top">
                    	<input type="text"  class="days" id="ngaychungtu" name="ngaychungtu" value="<%= tthdBean.getNgaychungtu() %>" 
                    		onchange="ChangeNCT();" readonly  /></TD>
                    <TD width="200px" class="plainlabel" valign="top">Ngày ghi sổ</TD>
                    <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  class="days" id="ngayghiso" name="ngayghiso" value="<%= tthdBean.getNgayghiso() %>" 
                    		 readonly  /></TD>
                </TR> 
                
                <TR>
					<TD class="plainlabel">Nguời nộp tiền </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="nguoinoptien" value="<%= tthdBean.getNguoinoptien() %>" style = "width:200px"> 
                     </TD>
				</TR> 
				
                <TR>
                    <TD class="plainlabel">Nội dung thanh toán</TD>
                    <TD class="plainlabel" colspan="5">
                        <select name="noidungId" id="noidungId" style="width: 200px" onChange="submitform();">
                        	<option value=""> </option>
                        	<%
                        		if(ndList != null)
                        		{
                        			try
                        			{
                        			while(ndList.next())
                        			{  
                        			if( ndList.getString("pk_seq").equals(tthdBean.getNoidungId())){ %>
                        				<option value="<%= ndList.getString("pk_seq") %>" selected="selected" ><%= ndList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ndList.getString("pk_seq") %>" ><%= ndList.getString("ten") %></option>
                        		 <% } } ndList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                    </TD>  
                </TR>
                <TR>
                    <TD class="plainlabel">Số chứng từ</TD>
                    <TD class="plainlabel">
                        <input type="text" name="soChungTu_Chu" id="soChungTu_Chu" value="<%= tthdBean.getSoChungTu_Chu() %>" onKeyPress = "return validateSpecialCharacters(event);" readonly="readonly" onchange="upperLetters(this);" style="width: 100px;">
                        <input type="text" name="soChungTu_So" id="soChungTu_So" value="<%= tthdBean.getSoChungTu_So() %>" style="width: 50px;" onkeypress="return keypress(event);">
                    </TD>  
                    <TD class="plainlabel">Tiền tệ</TD>
                    <TD class="plainlabel" style = "width:100px">
                    	
                        <select name="tienteId" id="tienteId" style = "width:150px" onChange = "ChangeTienTe();">
                        		
                        	<%
                        		if(tienteList != null)
                        		{
                        			try
                        			{
                        			while(tienteList.next())
                        			{  
                        			if( tienteList.getString("pk_seq").equals(tthdBean.getTienteId())){ %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" selected="selected" ><%= tienteList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= tienteList.getString("pk_seq") %>" ><%= tienteList.getString("ten") %></option>
                        		 <% } } tienteList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 

                      <TD class="plainlabel" style="width:50px" align = "right">Tỉ giá</TD>
                      <TD class="plainlabel">
                      		<input type="text" name="tigia" Id="tigia" value="<%= formatter.format(Double.parseDouble(tthdBean.getTigia().replaceAll(",","")))  %>" style = "width:100px;text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);">
                      </TD> 
               
                </TR>
                <%if(tthdBean.getNoidungId().equals("100000")) { // THU TIỀN BÁN HÀNG + KHÁCH HÀNG TRẢ TRƯỚC
                    %> 
                    <% String nkhtt=tthdBean.getNhomkhtt(); %>
                     <TR>
                        <%if(tthdBean.getNoidungId().equals("100000")) { %>
                        <TD class="plainlabel">Khách hàng /Nhóm khách hàng
                        	<%if (nkhtt.equals("1")  ){%>
    							 <input type="checkbox" name="nhomkhtt" value="1" checked="checked" onChange="submitform();"> 
    						<%} else  {%> 
    							<input type="checkbox" name="nhomkhtt" value="0" onChange="submitform();"> 
    						<%}%>
                        </TD>
                        <%} else {%>
                        <TD class="plainlabel">Khách hàng </TD>
                         <%} %>
                         
                         <%if(nkhtt.equals("1")){ %>
                         <TD colspan = 5 class="plainlabel"  >	
                            <select name="nhomkhttId" id="nhomkhttId" style="width: 300px" onChange="ChangeTienTe();">
                            	<option value=""> </option>
                            	<%
                            		if(nhomkhttList != null)
                            		{
                            			try
                            			{
                            			while(nhomkhttList.next())
                            			{  
                            			if( nhomkhttList.getString("pk_seq").equals(tthdBean.getNhomkhttId())){ %>
                            				<option value="<%= nhomkhttList.getString("pk_seq") %>" selected="selected" ><%= nhomkhttList.getString("ten") %></option>
                            			<%}else { %>
                            				<option value="<%= nhomkhttList.getString("pk_seq") %>" ><%= nhomkhttList.getString("ten") %></option>
                            		 <% } } nhomkhttList.close();} catch(SQLException ex){}
                            		}
                            	%>
                            </select>
                         </TD> 
                         <%}else{ %>
                      		 <TD class="plainlabel"  colspan="5">
		                     <input type="text" id="npp" name="npp" value = "<%= tthdBean.getKh_npp_Ten() %>" style="width: 300px"  >  
		                     <input type="hidden" id="nppId" name="nppId" onChange="ChangeTienTe();" value = "<%= tthdBean.getnppIdgoc() %>" style="width: 300px"  >
		                     </TD>
                         
                         <TD class="plainlabel" style = "display:none;">Kênh bán hàng</TD>
                         <TD class="plainlabel" colspan="3" style = "display:none;">                        
                      		<select name="kbhId" id="kbhId" style="width: 200px" >                 			
                            	<%
                            		if(kbhList != null)
                            		{
                            			try
                            			{
                            			while(kbhList.next())
                            			{  
                            			if( kbhList.getString("pk_seq").equals(tthdBean.getKbhId())){ %>
                            				<option value="<%= kbhList.getString("pk_seq") %>" selected="selected" ><%= kbhList.getString("Ten") %></option>
                            			<%}else { %>
                            				<option value="<%= kbhList.getString("pk_seq") %>" ><%= kbhList.getString("Ten") %></option>
                            		 <% } } kbhList.close();} catch(SQLException ex){}
                            		}
                            	%>
                            </select>
                         </TD> 
                         
                         <%} %> 
                         
                                             
                    </TR> 
                <%} else if(tthdBean.getNoidungId().equals("100001")) // THU TIỀN KHÁCH HÀNG TRẢ TRƯỚC
	       		{ %>
       			<TR style = "display:none;">
 				 <TD class="plainlabel" >Chuyển xuống chi nhánh </TD>
               <TD class="plainlabel" colspan="<%=tthdBean.getIsChuyenCN().equals("1") ? "1" : "5"%>">
                  <%
                  String ischuyenCN = tthdBean.getIsChuyenCN();	
                  System.out.print("ischuyenCN" +ischuyenCN);
                  
                  if (ischuyenCN.equals("1")){ %>
					 <input type="checkbox" name="ischuyenCN" id="ischuyenCN"  value="1" checked="checked" onChange="submitform();"> 
					<%} else  { %> 
						<input type="checkbox" name="ischuyenCN" id="ischuyenCN"  value="0" onChange=" submitform();"> 
					<%} %>
               </TD>
               <% if(tthdBean.getIsChuyenCN().equals("1")) { // HIỆN CHI NHÁNH CHO HO CHỌN %>
               <TD class="plainlabel" >Thu hộ hóa đơn CN </TD>
                   <TD class="plainlabel" colspan="5" >
                      <%
                      String isthuhoCN = tthdBean.getIsthuhoCN();	 
                      
                      if (isthuhoCN.equals("1")){ %>
						 <input type="checkbox" name="isthuhoCN" id="isthuhoCN" value="1" checked="checked" onChange="submitform();"> 
						<%} else  { %> 
							<input type="checkbox" name="isthuhoCN" id="isthuhoCN" value="0" onChange="submitform();"> 
						<%} %>
                   </TD>
               <%} %>    
           </TR>
            <% if(tthdBean.getIsChuyenCN().equals("1")) { // HIỆN CHI NHÁNH CHO HO CHỌN %>
            <TR>
              	                     
               	<TD class="plainlabel">Chi nhánh </TD>
     				<TD class="plainlabel" colspan="5">                        
                		<select name="chinhanhId" id="chinhanhId" style="width: 300px" onChange="ChangeTienTe();">
                          <option value="" SELECTED> </option>
                      	<%
                      		if(ChinhanhRs != null)
                      		{
                      			try
                      			{
                      			while(ChinhanhRs.next())
                      			{  
                      			if( ChinhanhRs.getString("pk_seq").equals(tthdBean.getNPPChinhanhId())){ %>
                      				<option value="<%= ChinhanhRs.getString("pk_seq") %>" selected="selected" ><%= ChinhanhRs.getString("nppTen") %></option>
                      			<%}else { %>
                      				<option value="<%= ChinhanhRs.getString("pk_seq") %>" ><%= ChinhanhRs.getString("nppTen") %></option>
                      		 <% } } 
                      			
                      			} catch(SQLException ex){}
                      		}
                      	%>
                      </select>						
                 </TD>
 			</TR>
 			<%} %>
 			
<%--  			<% if(!(tthdBean.getIsChuyenCN().equals("1") && tthdBean.getIsthuhoCN().equals("0"))) {%> --%>
  			<TR>
  				<TD class="plainlabel">Khách hàng </TD>
      			<TD class="plainlabel" colspan="5" >
		                     <input type="text" id="npp" name="npp" value = "<%= tthdBean.getKh_npp_Ten() %>" style="width: 300px"  >  
		                     <input type="hidden" id="nppId" onChange="ChangeTienTe();" name="nppId" value = "<%= tthdBean.getnppIdgoc() %>" style="width: 300px"  >
		        </TD>                          
           </TR>
<%--            <%} %> --%>
 	<% 	}       		
                else if(tthdBean.getNoidungId().equals("100002")) { // THU TIỀN KHÁC %>
       			    			 
       			<%--  <TR>
                    <TD width="20%" class="plainlabel" valign="top">Định khoản có</TD>
					<TD colspan="5" class="plainlabel" valign="top">
					     <input  type="text" name="dinhkhoanco" id="dinhkhoanco" value="<%= tthdBean.getDinhkhoanco()%>"  style="width:400px" ondblclick = "ChangeTienTe();">
					     <input type="hidden" id="dinhkhoancoId" name="dinhkhoancoId" value = "<%=tthdBean.getDinhkhoancoId() %>" style="width: 400px">
					 </TD>                                                                                  
                </TR>               
                
                <TR>   
				 	<%if( !(tthdBean.getDoiTuongDinhKhoan().equals("")) ) { %>
					            <TD class="plainlabel">Mã và tên đối tượng</TD>
					             <TD colspan="5" class="plainlabel" valign="middle"  >
					                      	<input type="text" name="doituongdinhkhoan" id="doituongdinhkhoan" value="<%= tthdBean.getMaTenDoiTuongDinhKhoan()%>"  style="width:400px"  onchange="chancencc()"> 
					             </TD> 		                    
 					 <%}%> 				          		  					                      
				</TR> --%>
				
				<% // Cho chọn nhiều định khoản có %>
<!-- 				<TR> -->
<!-- 					<TD width="20%" class="plainlabel" valign="top">Định khoản có</TD> -->
<!-- 					<TD class="plainlabel" colspan="5"> -->
<!-- 						<A href="" id="dinhkhoanco" rel="subcontentDKC">&nbsp; <img alt="Chi tạm ứng" src="../images/vitriluu.png"></A> -->
<!-- 								 <DIV id="subcontentDKC" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 700px; max-height:250px; overflow-y:scroll; padding: 4px;"> -->
<!--                   						<table width="100%" align="center"> -->
<!-- 				                        <tr class="plainlabel" > -->
<!-- 				                            <th width="120px">Số hiệu tài khoản</th> -->
<!-- 				                            <th width="120px">Đối tượng</th>				                           	 -->
<!-- 				                            <th width="100px">Số tiền</th>							                            										                        -->
<!-- 				                        </tr> -->
				                        
<%-- 				                           <% --%>
<!-- 		                        			   int j = 0; -->
<!-- 				                         	   int count = 0; -->
<!-- 				                            if(dinhkhoancoList.size() > 0) -->
<!-- 				                            {   -->
<!-- 		                        			  for(j = 0; j < dinhkhoancoList.size(); j ++) -->
<!-- 		                        			  { -->
		                        				
<!-- 		                        				 IDinhkhoanco dkc = dinhkhoancoList.get(j) ; -->
<!-- 		                        				 ResultSet doituongrs = null; -->
<!-- 		                        			  %> -->
<!-- 		                        			<tr > -->
<!-- 		                        				<td> -->
<!-- 		                        					<select name="taikhoanId" id="taikhoanId" onchange = "submit();" style=" width: 250px"> -->
		                        																							
<%-- 																<% try { --%>
<!-- 																	RSTaiKhoan.beforeFirst(); -->
<!-- 																if (RSTaiKhoan != null) -->
<!-- 																	{ -->
<!-- 																		while (RSTaiKhoan.next()){ -->
																			
<!-- 																				if(dkc.getTaikhoanId().equals(RSTaiKhoan.getString("pk_seq"))){ %> -->
<%-- 																					<option value="<%= RSTaiKhoan.getString("pk_seq") %>" selected><%= RSTaiKhoan.getString("sohieutk")%> </option> --%>
<%-- 																			<% 	}else{ %> --%>
<%-- 																					<option value="<%= RSTaiKhoan.getString("pk_seq") %>" ><%= RSTaiKhoan.getString("sohieutk")%> </option> --%>
<%-- 																			<% 	}																																						 --%>
<!-- 																		} -->
<!-- 																	}  -->
<!-- 																} catch (java.sql.SQLException e){} -->
<!-- 																%> -->
<!-- 													</select> -->
<!-- 		                        				</td> -->
<!-- 					                            <td> -->
<%-- 					                            	<input type="hidden" style="width: 100%"  name="doituongdinhkhoan" id="doituongdinhkhoan" value="<%= dkc.getDoituongdinhkhoan() %>" 	/>										                            	 --%>
					                            	
<%-- 					                            	<%  --%>
<!-- 					                            	   if(dkc.getDoituongdinhkhoan().equals("1"))  doituongrs = SanphamRs; -->
<!-- 					                            	   else if (dkc.getDoituongdinhkhoan().equals("2")) doituongrs = NganhangRs; -->
<!-- 					                            	   else if (dkc.getDoituongdinhkhoan().equals("3")) doituongrs = NccRs; -->
<!-- 					                            	   else if (dkc.getDoituongdinhkhoan().equals("4")) doituongrs = TaisanRs; -->
<!-- 					                            	   else if (dkc.getDoituongdinhkhoan().equals("5")) doituongrs = KhachhangRs; -->
<!-- 					                            	   else if (dkc.getDoituongdinhkhoan().equals("6")) doituongrs = NhanvienRs; -->
<!-- 					                            		%> -->
<%-- 					                            	<%if(dkc.getDoituongdinhkhoan().trim().length() > 0 && !dkc.getDoituongdinhkhoan().equals("0")){%> --%>
<!-- 					                            	<select name="doituongId" id="doituongId" style=" width: 250px"> -->
<!-- 					                            				<option value = "" ></option>															 -->
<%-- 																<% try { --%>
<!-- 																	doituongrs.beforeFirst(); -->
<!-- 																if (doituongrs != null) -->
<!-- 																	{ -->
<!-- 																		while (doituongrs.next()){	 -->
<!-- 																			System.out.println("Dinh khoan no: " + dkc.getDoituongId()); -->
<!-- 																				if(dkc.getDoituongId().equals(doituongrs.getString("pk_seq"))){ %> -->
<%-- 																					<option value="<%= doituongrs.getString("pk_seq") %>" selected><%= doituongrs.getString("ten")%> </option> --%>
<%-- 																			<% 	}else{ %> --%>
<%-- 																					<option value="<%= doituongrs.getString("pk_seq") %>" ><%= doituongrs.getString("ten")%> </option> --%>
<%-- 																			<% 	} --%>
																																						
<!-- 																		}  -->
<!-- 																	}  -->
<!-- 																} catch (java.sql.SQLException e){ -->
<!-- 																	e.printStackTrace(); -->
<!-- 																} -->
<!-- 																%> -->
<!-- 													</select> -->
<%-- 													<%}else{ %> --%>
<!-- 													<input type="text" style="width: 100%"  name="doituongId" id="doituongId" readonly="readonly" value="" 	/> -->
<%-- 													<%} %> --%>
													
<!-- 					                            </td> -->

<!-- 					                            <td> -->
					                            	
<%-- 					                            	<input type="text" style="width: 100%" name="sotienco" id = "sotienco" value="<%=dkc.getSotien() %>" align="left"  onchange="TinhTienDKC();" /> --%>
<!-- 					                            </td>								                             -->
<!-- 					                        </tr> -->
<%-- 						                   <% count ++; --%>
<!-- 						                     }  -->
<!-- 						                      }  -->
<!-- 						                        for(int m = 0; m < 5 - count ; m ++) {%> -->
<!-- 						                      <tr > -->
<!-- 		                        				<td> -->
<!-- 		                        					<select name="taikhoanId" id="taikhoanId" onchange = "submit();" style=" width: 250px"> -->
		                        					        														
<%-- 																<% try { --%>
<!-- 																	RSTaiKhoan.beforeFirst(); -->
<!-- 																if (RSTaiKhoan != null) -->
<!-- 																	{ -->
<!-- 																		while (RSTaiKhoan.next()){	 %> -->
<!-- 																		    <option value = "" ></option> -->
<%-- 																			<option value="<%= RSTaiKhoan.getString("pk_seq") %>" ><%=RSTaiKhoan.getString("sohieutk")%> </option> --%>
<%-- 																		<%	 --%>
<!-- 																		}  -->
<!-- 																	}  -->
<!-- 																} catch (java.sql.SQLException e){} -->
<!-- 																%> -->
<!-- 													</select> -->
<!-- 		                        				</td> -->
<!-- 					                            <td> -->
<!-- 					                            	<input type="hidden" style="width: 100%"  name="doituongdinhkhoan" id="doituongdinhkhoan" value="" 	/>										                            	 -->
<!-- 					                            	<select name="doituongId" id="doituongId" style=" width: 250px">															 -->
<!-- 														<option value="" > </option> -->
<!-- 													</select> -->
<!-- 					                            </td> -->
					                          
<!-- 					                            <td>					                             -->
<!-- 					                            	<input type="text" style="width: 100%" name="sotienco" id = "sotienco" value="" style="text-align: left" onchange="TinhTienDKC();" /> -->
<!-- 					                        </tr> -->
<%-- 						                      <%} %> --%>
						                      
<!--                   							</table> -->
<!-- 					                     <div align="right"> -->
<!-- 					                     	<label style="color: red" ></label> -->
<!-- 					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; -->
<!-- 					                     	<a href="javascript:dropdowncontent.hidediv('subcontentDKC')">Hoàn tất</a> -->
<!-- 					                     </div> -->
<!--               							</DIV> -->
<!-- 					</TD> -->
<!-- 				</TR> -->
				
				 <TR>
				 <%if(!tthdBean.getTienteId().equals("100000")){ %>
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (ngoại tệ)</TD>
    	             <TD class="plainlabel" valign="top">					
                   		<input type="text"  id="thuduocNT" name="thuduocNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getThuduocNT().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
                   	 </TD>	
                  <%} %> 	                 
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (VNĐ)</TD>
    	             <TD class="plainlabel" valign="top" <%if(tthdBean.getTienteId().equals("100000")){ %> colspan = 5 <%}else{%> colspan = 3 <%} %>>
                    	<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    </TD>											
                </TR>
				
       		<%}else if(tthdBean.getNoidungId().equals("100003")){ //THU HỒI TẠM ỨNG %>
       			 <TR>
                  <TD class="plainlabel">Đối tượng tạm ứng</TD>
                    <TD class="plainlabel" valign="top">
                    	 <select name="doituongtamung" id="doituongtamung" style="width: 200px" onChange="ChangeTienTe();">
                        	
                        	<%
                        			if(tthdBean.getDoiTuongTamUng().equals("1")){ %>
                        				<option value="1" selected="selected" >Nhà cung cấp</option>
                        				<option value="0"  >Nhân viên</option>
                        				
                        			<%}else if(tthdBean.getDoiTuongTamUng().equals("0")){ %>
                        				<option value="1"  >Nhà cung cấp</option>
                        				<option value="0" selected="selected" >Nhân viên</option>
                        		 <% } %>
                 						                       		
                        </select>
                    </TD>
                    <%if(tthdBean.getDoiTuongTamUng().equals("1")) { %>
                      <TD class="plainlabel">Nhà cung cấp</TD>
                       <TD class="plainlabel" colspan="3">
		                  <select name="nccId" id="nccId" style="width: 200px" onChange="ChangeTienTe();">
                        	<option value=""> </option>
                        	<%
                        		if(nccList != null)
                        		{
                        			try
                        			{
                        			while(nccList.next())
                        			{  
                        			if( nccList.getString("pk_seq").equals(tthdBean.getNccId())){ %>
                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("ma")%> - <%= nccList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("ma")%> - <%= nccList.getString("ten") %></option>
                        		 <% } } nccList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
		               </TD> 		                    
                    <%}else{ %>
                      <TD class="plainlabel">Nhân viên</TD>
                       <TD class="plainlabel"  colspan="3" >
		                 <select name="nvtuId" id="nvtuId" style="width: 200px" onChange="ChangeTienTe();">
                        	<option value=""> </option>
                        	<%
                        		if(nvtutList != null)
                        		{
                        			try
                        			{
                        			while(nvtutList.next())
                        			{  
                        			if( nvtutList.getString("pk_seq").equals(tthdBean.getNvtuId())){ %>
                        				<option value="<%= nvtutList.getString("pk_seq") %>" selected="selected" ><%= nvtutList.getString("ma")%> - <%= nvtutList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= nvtutList.getString("pk_seq") %>" ><%= nvtutList.getString("ma")%> - <%= nvtutList.getString("ten") %></option>
                        		 <% } } nvtutList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>       	 
		               </TD> 
                    <%} %>
                    
                	
                </TR> 
       	
       		<%} else if(tthdBean.getNoidungId().equals("100006")){ %> <!-- Thu tiền khách hàng quầy --> 
       		<TR style = "display:none;">
 				 <TD class="plainlabel" >Chuyển xuống chi nhánh </TD>
               <TD class="plainlabel" colspan="<%=tthdBean.getIsChuyenCN().equals("1") ? "1" : "5"%>">
                  <%
                  String ischuyenCN = tthdBean.getIsChuyenCN();	 
                  
                  if (ischuyenCN.equals("1")){ %>
					 <input type="checkbox" name="ischuyenCN" id="ischuyenCN"  value="1" checked="checked" disabled  onChange="submitform();"> 
					<%} else  { %> 
						<input type="checkbox" name="ischuyenCN" id="ischuyenCN"  value="0" disabled  onChange="checkThuHoCN(); submitform();"> 
					<%} %>
               </TD>
               <% if(tthdBean.getIsChuyenCN().equals("1")) { // HIỆN CHI NHÁNH CHO HO CHỌN %>
             	<!-- Không cần hiển thị Thu hộ hóa đơn CN - Mặc định không tích chọn-->
             <!--   <TD class="plainlabel" >Thu hộ hóa đơn CN </TD> -->
                   <TD class="plainlabel" colspan="6" >
                   	<input type="hidden" name ="isthuhoCN" id ="isthuhoCN" value= "0">
                      <%-- <%
                      String isthuhoCN = tthdBean.getIsthuhoCN();	 
                 
                      if (isthuhoCN.equals("1")){ %>
						 <input type="checkbox" name="isthuhoCN" id="isthuhoCN" value="1" checked="checked" onChange="submitform();"> 
						<%} else  { %> 
							<input type="checkbox" name="isthuhoCN" id="isthuhoCN" value="0" onChange="submitform();"> 
						<%} %> --%>
                   </TD>
               <%} %>    
           </TR>
            <% if(tthdBean.getIsChuyenCN().equals("1")) { // HIỆN CHI NHÁNH CHO HO CHỌN %>
            <TR>
              	                     
               	<TD class="plainlabel">Quầy / Trung tâm PP </TD>
     				<TD class="plainlabel" colspan="5">                        
                		<select name="chinhanhId" id="chinhanhId" style="width: 300px" onChange="ChangeTienTe();">
                          <option value="" SELECTED> </option>
                      	<%
                      		if(ChinhanhRs != null)
                      		{
                      			try
                      			{
                      			while(ChinhanhRs.next())
                      			{  
                      			if( ChinhanhRs.getString("pk_seq").equals(tthdBean.getNPPChinhanhId())){ %>
                      				<option value="<%= ChinhanhRs.getString("pk_seq") %>" selected="selected" ><%= ChinhanhRs.getString("nppTen") %></option>
                      			<%}else { %>
                      				<option value="<%= ChinhanhRs.getString("pk_seq") %>" ><%= ChinhanhRs.getString("nppTen") %></option>
                      		 <% } } 
                      			
                      			} catch(SQLException ex){}
                      		}
                      	%>
                      </select>						
                 </TD>
 			</TR>
  			<%} %>
  			<% if(!(tthdBean.getIsChuyenCN().equals("1") && tthdBean.getIsthuhoCN().equals("0"))) {%>
  			<TR>
  				<TD class="plainlabel" >Khách hàng</TD>
  				<TD class="plainlabel" colspan="5" >
		                     <input type="text" id="npp" name="npp" value = "<%= tthdBean.getKh_npp_Ten() %>" style="width: 300px"  >  
		                     <input type="hidden" id="nppId" name="nppId" onChange="ChangeTienTe();" value = "<%= tthdBean.getnppIdgoc() %>" style="width: 300px"  >
		        </TD>                  
           </TR>
           <%} %>
                <%} %>
       		
       		
       		<TR>
       			    <TD class="plainlabel">Hình thức thanh toán</TD>
                    <TD class="plainlabel" colspan = 5>
                        <select name="htttId" id="htttId" style="width: 200px" 
                          <% if(!tthdBean.getNoidungId().equals("100002")){ %> onChange =  "ChangeTienTe();" <%}else{ %> onChange = "submit();" <%} %>>
                   <!--      	<option value=""> </option> -->
                        	<%
                        		if(htttList != null)
                        		{
                        			try
                        			{
                        			while(htttList.next())
                        			{  
                        			if( htttList.getString("pk_seq").equals(tthdBean.getHtttId())){ %>
                        				<option value="<%= htttList.getString("pk_seq") %>" selected="selected" ><%= htttList.getString("ten")%></option>
                        			<%}else { %>
                        				<option value="<%= htttList.getString("pk_seq") %>" ><%= htttList.getString("ten") %></option>
                        		 <% } } htttList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
       		</TR>
       	      			
       		<TR>
                	  <% if(tthdBean.getHtttId().equals("100001")){ %>
                     <TD class="plainlabel">Số tài khoản</TD>
                     <TD class="plainlabel" colspan ="5">
                     
                        <select name="sotaikhoan" id="sotaikhoan" style="width: 600px" >
                        	
                        	<%
                        		if(sotkRs != null)
                        		{
                        			try
                        			{
                        			while(sotkRs.next())
                        			{  
                        			if( sotkRs.getString("SOTAIKHOAN").equals(tthdBean.getSotaikhoan())){ %>
                        				<option value="<%= sotkRs.getString("SOTAIKHOAN") %>" selected="selected" ><%= sotkRs.getString("TAIKHOAN")%></option>
                        			<%}else { %>
                        				<option value="<%= sotkRs.getString("SOTAIKHOAN") %>" ><%= sotkRs.getString("TAIKHOAN") %></option>
                        		 <% } } sotkRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>

                     </TD>
		          <%} %>
               </TR>
       		<%	if( (tthdBean.getNoidungId().equals("100004") && !tthdBean.getTienteId().equals("100000"))||(tthdBean.getNoidungId().equals("100000") && !tthdBean.getTienteId().equals("100000"))|| (tthdBean.getNoidungId().equals("100003") && !tthdBean.getTienteId().equals("100000")) ){ // Thu tien bán hàng/ Thu hoi tam ung va tien ngoai te%>
       			<TR>
                    <TD class="plainlabel">Số tiền hóa đơn (ngoại tệ) </TD>       			
                    <TD class="plainlabel">
                         <input type="text" style="text-align: right;" name="sotienthanhtoanNT" id = "sotienthanhtoanNT" 
                    	   		value="<%= formatterNT.format(Double.parseDouble(tthdBean.getSotienttNT().replaceAll(",","")))%>"  AUTOCOMPLETE="off" readonly>
                     </TD>

                    <TD class="plainlabel">Số tiền hóa đơn (VNĐ) </TD>
                    <TD class="plainlabel" colspan = 3>
                        <input type="text" style="text-align: right;" name="sotienthanhtoan" id = "sotienthanhtoan" 
                        		value="<%= formatter.format(Double.parseDouble(tthdBean.getSotientt().replaceAll(",","")))%>"  AUTOCOMPLETE="off" readonly> 
                     </TD>

       			</TR>
                <TR>
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (ngoại tệ)</TD>
    	             <TD class="plainlabel" valign="top">
					
                   		<input type="text"  id="thuduocNT" name="thuduocNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getThuduocNT().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/></TD>
	                
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (VNĐ)</TD>
    	             <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    </TD>											
                </TR>
                
            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (ngoại tệ)</TD>
                    <TD class="plainlabel" valign="top">
                    
                    	<input type="text"  id="cpnganhangNT" name="cpnganhangNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getChiphinganhangNT().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/></TD>
                     
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;"  readonly />
                    </TD>

                </TR>
                <% if(tthdBean.getNoidungId().equals("100000")){%>
                <TR>
                	<TD width="140px" class="plainlabel" valign="top">Chiết khấu thanh toán (ngoại tệ)</TD>
                	<TD class="plainlabel" valign="top"> 
                		<input type="text" name="chietkhauNT" id="chietkhauNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getChietkhauNT().replaceAll(",", ""))) %>" style="text-align: right;" onchange="TinhChenhLech();" onKeyPress = "return keypress(event);">
                	</TD>
                	
                	<TD class="plainlabel" valign="top">Chiết khấu thanh toán (VNĐ)</TD>
                	<TD class="plainlabel" colspan = "3" valign="top"> 
                		<input type="text" name="chietkhau" id="chietkhau" value="<%= formatter.format(Double.parseDouble(tthdBean.getChietkhau().replaceAll(",", ""))) %>" style="text-align: right;" readonly>
                	</TD>
                </TR>
                <%} %>
            	<TR>
                   <TD class="plainlabel">Diễn giải chứng từ</TD>
                   <TD class="plainlabel" >                   
                       <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                    </TD> 
                     
                   <TD width="140px" class="plainlabel" valign="top">Chênh lệch (VNĐ)</TD>
                   <TD class="plainlabel" valign="top" colspan = "3">
                   	<input type="text"  id="chenhlech" name="chenhlech" value="<%= formatter.format(Double.parseDouble(tthdBean.getChenhlech().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                   </TD>
                </TR>
                
                
       			         
             <%}else if( (tthdBean.getNoidungId().equals("100000")&& tthdBean.getTienteId().equals("100000")) || (tthdBean.getNoidungId().equals("100003") && tthdBean.getTienteId().equals("100000"))|| (tthdBean.getNoidungId().equals("100004") && tthdBean.getTienteId().equals("100000")) ){ //Thu tien ban hang/Thu hoi tam ung va VND  %> 
       			<TR>
                    <TD  width="200px" class="plainlabel">Số tiền hóa đơn (VNĐ) </TD>
                    <TD class="plainlabel" colspan = 5>
                        <input type="text" style="text-align: right;" name="sotienthanhtoan" id = "sotienthanhtoan" 
                        		value="<%= formatter.format(Double.parseDouble(tthdBean.getSotientt().replaceAll(",","")))%>"  AUTOCOMPLETE="off" readonly> 
                     </TD>

       			</TR>
                <TR>
	                 <TD width="200px" class="plainlabel" valign="top">Số tiền thực thu (VNĐ)</TD>
    	             <TD class="plainlabel" valign="top" colspan = 5>
						<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
					 </TD>
                </TR>
                
            	<TR>
                    <TD width="200px" class="plainlabel" valign="top">Phí ngân hàng (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
                    </TD>

                </TR>
                
                <TR>
                	<TD class="plainlabel" valign="top">Chiết khấu thanh toán (VNĐ)</TD>
                	<TD class="plainlabel" valign="top" colspan = 5> 
                		<input type="text" name="chietkhau" id="chietkhau" value="<%= formatter.format(Double.parseDouble(tthdBean.getChietkhau().replaceAll(",", ""))) %>" style="text-align: right;" onchange="TinhChenhLech();" onKeyPress = "return keypress(event);">
                	</TD>
                </TR>
                
            	<TR>

                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" >                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                   <%  if(!tthdBean.getNoidungId().equals("100004")) {%>
                    <TD width="140px" class="plainlabel" valign="top">Chênh lệch (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = "3">
                    	<input type="text"  id="chenhlech" name="chenhlech" value="<%= formatter.format(Double.parseDouble(tthdBean.getChenhlech().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/></TD>
               	   <% } else { %>
               	 	  <TD width="140px" class="plainlabel" valign="top"></TD>
               	 	  <TD class="plainlabel" valign="top" colspan = "3">
               	   <% } %>
                </TR>                

			<%}else  if(tthdBean.getNoidungId().equals("100001") && !tthdBean.getTienteId().equals("100000")){ //Thu tien khach hang ung truoc va ngoai te  %>
                <TR>
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (ngoại tệ)</TD>
    	             <TD class="plainlabel" valign="top">
					
                   		<input type="text"  id="thuduocNT" name="thuduocNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getThuduocNT().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/></TD>
	                
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (VNĐ)</TD>
    	             <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    </TD>											
                </TR>
                
            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (ngoại tệ)</TD>
                    <TD class="plainlabel" valign="top">
                    
                    	<input type="text"  id="cpnganhangNT" name="cpnganhangNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getChiphinganhangNT().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/></TD>
                     
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;" readonly/>
                    </TD>

                </TR>

            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Tổng tiền trả trước (ngoại tệ)</TD>
                    <TD class="plainlabel" valign="top">
                    
                    	<input type="text"  id="tongNT" name="tongNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getTongNT().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/></TD>
                     
                    <TD width="140px" class="plainlabel" valign="top">Tổng tiền trả trước (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="tongVND" name="tongVND" value="<%= formatter.format(Double.parseDouble(tthdBean.getTongVND().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    </TD>

                </TR>

            	<TR>

                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                </TR>                
			
			
			<%}else  if(tthdBean.getNoidungId().equals("100001") && tthdBean.getTienteId().equals("100000")){ //Thu tien khach hang ung truoc va VND  %>
                <TR>	                
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (VNĐ)</TD>
    	             <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
                    </TD>											
                </TR>
                
            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
                    </TD>

                </TR>

            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Tổng tiền trả trước (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="tongVND" name="tongVND" value="<%= formatter.format(Double.parseDouble(tthdBean.getTongVND().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    </TD>

                </TR>

            	<TR>

                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                </TR>                
			
			<%// THU TIỀN KHÁC VÀ NGOẠI TỆ 
				}else  if(tthdBean.getNoidungId().equals("100002") && !tthdBean.getTienteId().equals("100000")){ %>       			
                
                 <%if(tthdBean.getHtttId().equals("100001")){ %>
            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (ngoại tệ)</TD>
                    <TD class="plainlabel" valign="top">
                    
                    	<input type="text"  id="cpnganhangNT" name="cpnganhangNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getChiphinganhangNT().replaceAll(",",""))) %>"  style="text-align: right;"   onKeyPress = "return keypress(event);"/></TD>
                     
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;" readonly/>
                    </TD>
                </TR>
                <%} %>
           	

				<%-- <TR>
					<TD class="plainlabel">Nguời nộp tiền </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="nguoinoptien" value="<%= tthdBean.getNguoinoptien() %>" style = "width:200px"> 
                     </TD>
				</TR> --%>
				<TR>
					<TD class="plainlabel">Địa chỉ</TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="diachi" value="<%= tthdBean.getDiachi()%>" style = "width:300px"> 
                     </TD>
				</TR>
            	<TR>
                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                </TR> 
       			<%}else  if(tthdBean.getNoidungId().equals("100006") && !tthdBean.getTienteId().equals("100000")){ //Thu tien khach hang ung truoc va ngoai te  %>
                <TR>
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (ngoại tệ)</TD>
    	             <TD class="plainlabel" valign="top">
					
                   		<input type="text"  id="thuduocNT" name="thuduocNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getThuduocNT().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/></TD>
	                
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (VNĐ)</TD>
    	             <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    	<input type="hidden"  id="cpnganhangNT" name="cpnganhangNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getChiphinganhangNT().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
                    	<input type="hidden"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;" readonly/>
                    </TD>											
                </TR>
                
            	<TR>
                    <TD width="140px" class="plainlabel" valign="top" style="display: none;">Tổng tiền trả trước (ngoại tệ)</TD>
                    <TD class="plainlabel" valign="top" style="display: none;">
                    
                    	<input type="text"  id="tongNT" name="tongNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getTongNT().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/></TD>
                     
                    <TD width="140px" class="plainlabel" valign="top" style="display: none;">Tổng tiền trả trước (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 3 style="display: none;">
                    	<input type="text"  id="tongVND" name="tongVND" value="<%= formatter.format(Double.parseDouble(tthdBean.getTongVND().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    </TD>

                </TR>

            	<TR>

                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                </TR>                
			
			
			<%}else  if(tthdBean.getNoidungId().equals("100006") && tthdBean.getTienteId().equals("100000")){ //Thu tien khach hang ung truoc va VND  %>
                <TR>	                
	                 <TD width="140px" class="plainlabel" valign="top">Số tiền thực thu (VNĐ)</TD>
    	             <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="thuduoc" name="thuduoc" value="<%= formatter.format(Double.parseDouble(tthdBean.getThuduoc().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
                    	<input type="hidden"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;"  onchange="TinhChenhLech();" onKeyPress = "return keypress(event);"/>
                    </TD>											
                </TR>

            	<TR style="display: none;">
                    <TD width="140px" class="plainlabel" valign="top">Tổng tiền trả trước (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="tongVND" name="tongVND" value="<%= formatter.format(Double.parseDouble(tthdBean.getTongVND().replaceAll(",",""))) %>"  style="text-align: right;"  readonly/>
                    </TD>

                </TR>

            	<TR>

                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                </TR>      
			
			
			<%// THU TIỀN KHÁC VÀ NGOẠI TỆ 
				}else  if(tthdBean.getNoidungId().equals("100002") && !tthdBean.getTienteId().equals("100000")){ %>       			
                
                 <%-- <%if(tthdBean.getHtttId().equals("100001")){ %>
            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (ngoại tệ)</TD>
                    <TD class="plainlabel" valign="top">
                    
                    	<input type="text"  id="cpnganhangNT" name="cpnganhangNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getChiphinganhangNT().replaceAll(",",""))) %>"  style="text-align: right;"   onKeyPress = "return keypress(event);"/></TD>
                     
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 3>
                    	<input type="text"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;" readonly/>
                    </TD>
                </TR>
                <%} %> --%>
           	

				<%-- <TR>
					<TD class="plainlabel">Nguời nộp tiền </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="nguoinoptien" value="<%= tthdBean.getNguoinoptien() %>" style = "width:200px"> 
                     </TD>
				</TR> --%>
				<TR>
					<TD class="plainlabel">Địa chỉ</TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="diachi" value="<%= tthdBean.getDiachi()%>" style = "width:300px"> 
                         <%if(tthdBean.getHtttId().equals("100001")){ %>
                         	<input type="hidden"  id="cpnganhangNT" name="cpnganhangNT" value="<%= formatterNT.format(Double.parseDouble(tthdBean.getChiphinganhangNT().replaceAll(",",""))) %>"  style="text-align: right;"   onKeyPress = "return keypress(event);"/>
                         	<input type="hidden"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;" readonly/>
                         <%} %>
                     </TD>
				</TR>
            	<TR>
                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                </TR> 
       			
       		<%}// THU KHÁC VÀ VND 
				else  if(tthdBean.getNoidungId().equals("100002") && tthdBean.getTienteId().equals("100000")){ %>       			 
                
                <%if(tthdBean.getHtttId().equals("100001")){ %>
            	<TR>
                    <TD width="140px" class="plainlabel" valign="top">Phí ngân hàng (VNĐ)</TD>
                    <TD class="plainlabel" valign="top" colspan = 5>
                    	<input type="text"  id="cpnganhang" name="cpnganhang" value="<%= formatter.format(Double.parseDouble(tthdBean.getChiphinganhang().replaceAll(",",""))) %>"  style="text-align: right;"  onKeyPress = "return keypress(event);"/>
                    </TD>

                </TR>
                <%} %>
            	
<%-- 
				<TR>
					<TD class="plainlabel">Nguời nộp tiền </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="nguoinoptien" value="<%= tthdBean.getNguoinoptien() %>" style = "width:200px"> 
                     </TD>
				</TR> --%>
				<TR>
					<TD class="plainlabel">Địa chỉ</TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="diachi" value="<%= tthdBean.getDiachi()%>" style = "width:300px" > 
                     </TD>
				</TR>
				
            	<TR>
                    <TD class="plainlabel">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ghichu" value="<%= tthdBean.getNoidungtt() %>" style = "width:300px"> 
                     </TD> 
                      
                </TR>
       			
            <%} %>
            	<TR>
            		<TD class="plainlabel">Chứng từ kèm theo</TD>
                    <TD class="plainlabel" colspan = "5">                   
                        <input type="text" name="ctkemtheo" value="<%= tthdBean.getChungTuKemTheo() %>" style = "width:300px"> 
                    </TD> 
            	</TR>
            </TABLE>
       
            </div>
        
        <%if(tthdBean.getNoidungId().equals("100002")) { %>
         <hr> 
           	<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
	            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
		            <tr class="tbheader">
	                    <th width="30%">Số hiệu tài khoản</th>
	                    <th width="35%">Đối tượng</th>				                           	
	                    <th width="35%">Số tiền</th>							                            										                       
	                </tr>
				                        
                <%
                int j = 0;
                int count = 0;
                if(dinhkhoancoList.size() > 0)
                {  
                	System.out.println("dinhkhoancoList.size(): " + dinhkhoancoList.size());
	      			  for(j = 0; j < dinhkhoancoList.size(); j ++)
	      			  {
	      				 IDinhkhoanco dkc = dinhkhoancoList.get(j) ;
	      				 ResultSet doituongrs = null;
	      			  %>
             		<tr >
          				<td>
          					<select name="taikhoanId" id="taikhoanId" onchange = "submit();" style=" width: 100%">
						<% try {
							RSTaiKhoan.beforeFirst();
						if (RSTaiKhoan != null)
							{
								while (RSTaiKhoan.next()){
									
										if(dkc.getTaikhoanId().equals(RSTaiKhoan.getString("pk_seq"))){ %>
											<option value="<%= RSTaiKhoan.getString("pk_seq") %>" selected><%= RSTaiKhoan.getString("sohieutk")%> </option>
									<% 	}else{ %>
											<option value="<%= RSTaiKhoan.getString("pk_seq") %>" ><%= RSTaiKhoan.getString("sohieutk")%> </option>
									<% 	}																																						
								}
							} 
						} catch (java.sql.SQLException e){
							e.printStackTrace();
						}
						%>
							</select>
            			</td>
                        <td>
                        	<input type="hidden" style="width: 100%"  name="doituongdinhkhoan" id="doituongdinhkhoan" value="<%= dkc.getDoituongdinhkhoan() %>" 	/>										                            	
                        	
                        	<% 
                        	   if(dkc.getDoituongdinhkhoan().equals("1"))  doituongrs = SanphamRs;
                        	   else if (dkc.getDoituongdinhkhoan().equals("2")) doituongrs = NganhangRs;
                        	   else if (dkc.getDoituongdinhkhoan().equals("3")) doituongrs = NccRs;
                        	   else if (dkc.getDoituongdinhkhoan().equals("4")) doituongrs = TaisanRs;
                        	   else if (dkc.getDoituongdinhkhoan().equals("5")) doituongrs = KhachhangRs;
                        	   else if (dkc.getDoituongdinhkhoan().equals("6")) doituongrs = NhanvienRs;
                        	   else if (dkc.getDoituongdinhkhoan().equals("8")) doituongrs = DoituongkhacRs;
                        		%>
                        	<%if(dkc.getDoituongdinhkhoan().trim().length() > 0 && !dkc.getDoituongdinhkhoan().equals("0")){%>
                        	<select name="doituongId" id="doituongId" style=" width: 100%">
                        				<option value = "" ></option>															
							<% try {
								doituongrs.beforeFirst();
							if (doituongrs != null)
								{
									while (doituongrs.next()){																																						
											if(dkc.getDoituongId().equals(doituongrs.getString("pk_seq"))){ %>
												<option value="<%= doituongrs.getString("pk_seq") %>" selected><%= doituongrs.getString("ma")%> - <%= doituongrs.getString("ten")%> </option>
										<% 	}else{ %>
												<option value="<%= doituongrs.getString("pk_seq") %>" ><%= doituongrs.getString("ma")%> - <%= doituongrs.getString("ten")%> </option>
										<% 	}
									} 
								} 
							} catch (java.sql.SQLException e){}
							%>
							</select>
							<%}else{ %>
							<input type="text" style="width: 100%"  name="doituongId" id="doituongId" readonly="readonly" value="" 	/>
							<%} %>
				
                        </td>

                         <td>
                            <%if (Double.parseDouble(dkc.getSotienNT().replaceAll(",", "").equals("")?"0":dkc.getSotienNT().replaceAll(",", "")) > 0 ){ %>
                         	<input type="text" style="width: 100%" name="sotienco" id = "sotienco" value="<%=dkc.getSotienNT() %>" align="left"  onchange="TinhTienDKC();" />
                          <%}else { %>
                            <input type="text" style="width: 100%" name="sotienco" id = "sotienco" value="<%=dkc.getSotien() %>" align="left"  onchange="TinhTienDKC();" />
                          <%} %>
                         </td>								                            
                     </tr>
                 <% count ++;
                   } 
                    } 
                      for(int m = 0; m < 5 - count ; m ++) {%>
                    <tr >
                  				<td>
                  					<select name="taikhoanId" id="taikhoanId" onchange = "submit();" style=" width: 100%">
                  					        														
								<% try {
									RSTaiKhoan.beforeFirst();
								if (RSTaiKhoan != null)
									{
										while (RSTaiKhoan.next()){	 %>
										    <option value = "" ></option>
											<option value="<%= RSTaiKhoan.getString("pk_seq") %>" ><%=RSTaiKhoan.getString("sohieutk")%> </option>
										<%	
										} 
									} 
								} catch (java.sql.SQLException e){}
								%>
					</select>
                  				</td>
                         <td>
                         	<input type="hidden" style="width: 100%"  name="doituongdinhkhoan" id="doituongdinhkhoan" value="" 	/>										                            	
                         	<select name="doituongId" id="doituongId" style=" width: 100%">															
						<option value="" > </option>
					</select>
                         </td>
                       
                         <td>					                            
                         	<input type="text" style="width: 100%" name="sotienco" id = "sotienco" value="" style="text-align: left" onchange="TinhTienDKC();" />
                     </tr>
                    <%} %>
	            
	            </TABLE>
            </div>
        <%} %>        
        <% if(tthdBean.getNoidungId().equals("100000")|| tthdBean.getNoidungId().equals("100003")|| tthdBean.getNoidungId().equals("100004")) { %>  
            <hr> 
         
           	<div align="left" style="width:100%; float:none; clear:none;" class="plainlabel">
            <TABLE class="tabledetail" width="100%" border="0" cellpadding="1" cellspacing="1" >
            <% // Don vi tien la VND
            	if(tthdBean.getTienteId().equals("100000")){ %>
                <TR class="tbheader">
                <%if(tthdBean.getNoidungId().equals("100000")){ %>
                	<TH align="center" width="10%">Loại hóa đơn</TH> 
                	<TH align="center" width="10%">Số đơn hàng</TH>
                <%}%>
                	<TH align="center" width="10%">Ký hiệu hóa đơn</TH>
                	<TH align="center" width="10%">Số hóa đơn</TH>
                	<TH align="center" width="9%">Ngày hóa đơn</TH>
                	<TH align="center" width="9%">Ngày đến hạn TT</TH>
                 	<TH align="center" width="10%">Số tiền gốc HĐ</TH>
                 	
                	<TH align="center" width="9%">Số dư HĐ (đã có VAT)</TH>

               	 	<TH align="center" width="10%">Thanh toán (VNĐ)</TH>
               	 	<TH align="center" width="10%">Còn lại</TH>
               	 	<TH align="center" width="7%">Trả hết</TH>
                </TR>
                
                   <TR >
              		<%if(tthdBean.getNoidungId().equals("100000")){ %>
                      <TH align= "center" width ="10%"> </TH>
                      <TH align= "center" width ="10%"> </TH>
                 	<%} %>	
                      	<TH align= "center" width ="10%"> </TH>
                      	<TH align= "center" width ="10%"> </TH>
                      	<TH align= "center" width ="10%"> </TH>
                      	<TH align= "center" width ="10%"> </TH>
                        <TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totaltongtien2" id= "totaltongtien2" readonly="readonly" > </TH>
						<TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totaltongtien" id= "totaltongtien" readonly="readonly" > </TH>
                        <TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totalthanhtoan"   id= "totalthanhtoan"   readonly="readonly" > </TH>
                        <TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totalconlai" id= "totalconlai" readonly="readonly" ></TH>
                        <TH align= "center" width ="5%"> </TH>
                </TR>
                
  
                
            <%// Ngoai te
            	}else{ %>
                <TR class="tbheader"> 
                <%if(tthdBean.getNoidungId().equals("100000")){ %>
                	<TH align="center" width="10%">Loại hóa đơn</TH>
                	<TH align="center" width="10%">Số đơn hàng</TH>
                <%} %>
                	<TH align="center" width="10%">Ký hiệu hóa đơn</TH>
                	<TH align="center" width="10%">Số hóa đơn</TH>
                	<TH align="center" width="9%">Ngày hóa đơn</TH>
					<TH align="center" width="9%">Ngày đến hạn TT</TH>
                 	<TH align="center" width="10%">Số tiền gốc HĐ</TH>
                 	
                	<TH align="center" width="10%">Số dư HĐ (VNĐ)</TH>

                	<TH align="center" width="9%">Số dư hóa đơn (Ngoại tệ)</TH>
                	                	
               	 	<TH align="center" width="9%">Thanh toán (Ngoại tệ)</TH>
               	 	<TH align="center" width="8%">Còn lại</TH>
               	 	<TH align="center" width="8%">Trả hết</TH>
                </TR>
                
                
                      <TR >
            	   <%if(tthdBean.getNoidungId().equals("100000")){ %>
                      <TH align= "center" width ="10%"> </TH>
                      <TH align= "center" width ="10%"> </TH>
                 	<%} %>	
                      	<TH align= "center" width ="10%"> </TH>
                     	<TH align= "center" width ="10%"> </TH>
          				<TH align= "center" width ="10%"> </TH>
          				<TH align= "center" width ="10%"> </TH>
                        <TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totaltongtien2" id= "totaltongtien2" readonly="readonly" > </TH>
						<TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totaltongtien" id= "totaltongtien" readonly="readonly" > </TH>
						<TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totaltongtienNT" id= "totaltongtienNT" readonly="readonly" > </TH>                       
						<TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totalthanhtoan"   id= "totalthanhtoan"   readonly="readonly" > </TH>
                        <TH align= "center" width ="10%"> <input type ="text" style="width : 100%; text-align: right;" value ="0" name= "totalconlai" id= "totalconlai" readonly="readonly" ></TH>
                       
                </TR>
      
				<%} %>
		  
                <%  
					int i;
                System.out.println("SSSS: "+hoadonList.size());
    	            for(i = 0; i < hoadonList.size(); i++)
                	{
                		IHoadon hoadon = hoadonList.get(i);
	               		%>
	               		<tr>
	               			<%if(tthdBean.getNoidungId().equals("100000")){ %>	
	               			<td align="center">
            					         									
								
								<input type="text" style="width: 100%;" value="<%= hoadon.getTenloaihd() %>" name= "tenhd" readonly="readonly" >

           	 				<td align="center">           	 					           	 					
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getMahopdong() %>" name= "mahd" readonly="readonly" >
           	 				</td>
						<%} else {%>
							<td align="center" style="display: none">
            					         									
								
								<input type="text" style="width: 100%;" value="<%= hoadon.getTenloaihd() %>" name= "tenhd" readonly="readonly" >

           	 				<td align="center" style="display: none">           	 					           	 					
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getMahopdong() %>" name= "mahd" readonly="readonly" >
           	 				</td>
						<% } %>
           	 				<td align="center">
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getLoaihd() %>" name= "loaihd"  >
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getId() %>" name= "idHd"  > 
           	 					<input type="hidden" style="width: 100%;" value="<%= hoadon.getTygia() %>" name= "tigiaHd"  > 
           	 					<input type="text" style="width: 100%;" value="<%= hoadon.getKyhieu() %>" name= "kyhieuhd" readonly="readonly" >
           	 				</td>
           	 				<td align="center">							
           	 					<input type="text" style="width: 100%;text-align: center;" value="<%= hoadon.getSo() %>" name= "sohd" readonly="readonly" >

           	 				</td>
           	 				<td align="center">          	 				
           	 					<input type="text" style="width: 100%; text-align: center;" value="<%= hoadon.getNgay() %>" name= "ngayhd" readonly="readonly" >
   
           	 				
           	 				<td align="center">
	       	 					<input type="text" style="width: 100%; text-align: center;" value="<%= hoadon.getNgaydenhanTT() %>" name= "ngaydenhanTT" id="ngaydenhanTT<%= i %>" readonly="readonly" >
	       	 				</td>
           	 	<%if(tthdBean.getTienteId().equals("100000")){ %>								

	       	 				<td align="center">
	       	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getSotiengoc().replaceAll(",",""))) %>" name= "stgoc" id="stgoc<%= i %>" readonly="readonly" >
	       	 				</td>         	 				
                        <%}else{ %>
	                        <td align="center">
	       	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatterNT.format(Double.parseDouble(hoadon.getSotiengoc().replaceAll(",",""))) %>" name= "stgoc" id="stgoc<%= i %>" readonly="readonly" >
	       	 				</td> 
                        <%} %>         	 				

           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getTongtiencoVAT().replaceAll(",",""))) %>" name= "avat" id="avat<%= i %>" readonly="readonly" >
           	 				</td>         	 				
  
           	 				
           	 		<%  // Ngoại tệ 
           	 	if(!tthdBean.getTienteId().equals("100000")){ %>
           	 				<td align="center">							          	 				
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= formatterNT.format(Double.parseDouble(hoadon.getSotienNT().replaceAll(",",""))) %>" name= "sotienNT" id="sotienNT<%= i %>" readonly="readonly" >

           	 				<td align="center">
       	 						<input type="text" style="width: 100%; text-align: right;" value="<%= formatterNT.format(Double.parseDouble(hoadon.getThanhtoan().replaceAll(",",""))) %>"  name= "thanhtoan" id="thanhtoan<%= i %>"  onchange="ThanhToan(300)" onKeyPress = "return keypress(event);" >
       	 					</td>
       	 				<%}else{ %>
       	 					<td align="center">
       	 						<input type="text" style="width: 100%; text-align: right;" value="<%= formatter.format(Double.parseDouble(hoadon.getThanhtoan().replaceAll(",",""))) %>"  name= "thanhtoan" id="thanhtoan<%= i %>"  onchange="ThanhToan(300)" onKeyPress = "return keypress(event);">
       	 					</td>
       	 				<%} %>          	 			
           	 				<td align="center">
           	 					<input type="text" style="width: 100%; text-align: right;" value="<%= hoadon.getConlai() %>" name= "conlai" id="conlai<%= i %>" readonly="readonly" >
           	 				</td>
           	 				<td align="center">
           	 				<% 	if(hoadon.getConlai().equals("0")){ %>
           	 					<input type="checkbox" value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" checked="checked" onchange="ThanhToan(<%= i %>)" >
           	 				<%} else { %>
           	 					<input type="checkbox"  value="<%= hoadon.getId() %>" name = "trahet" id="trahet<%= i %>" onchange="ThanhToan(<%= i %>)" >
           	 				<%} %>
           	 				</td>
           	 			</tr>
           	 	<%	} %>         	 
           	 		
            </TABLE> 
        	</div>    
       <% } %>  
             </fieldset>	
    </div>
</div>

</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript">
	jQuery(function()
	{		
		$("#npp").autocomplete("ErpKhachHangHOList.jsp");
	});	

</script>

<script type="text/javascript">
	replaces();
	
	
</script>


<script type="text/javascript">
dropdowncontent.init("dinhkhoanco", "center-top", 300, "click");

jQuery(function()
{		
	$("#dinhkhoanco").autocomplete("ErpTaiKhoanKeToanList.jsp");
	$("#doituongdinhkhoan").autocomplete("ErpDoiTuongTaiKhoanKeToanList.jsp");
				
});
	ThanhToan(100);
</script>
</BODY>
</html>
<%
try{
	if(kbhList != null) kbhList.close(); 
	if(nppList != null) nppList.close(); 
	if(htttList != null) htttList.close(); 
	if(ndList != null) ndList.close();
	if(nccList != null) nccList.close();
	if(nhomkhttList != null) nhomkhttList.close(); 
	if(nvtutList != null) nvtutList.close();
	if(nganhangList != null) nganhangList.close();
	//if(chinhanhList != null) chinhanhList.close();
	if(tienteList != null) tienteList.close();
	if(hoadonList != null) hoadonList.clear(); 
	if(sotkRs != null) sotkRs.close();
	if(RSTaiKhoan != null) RSTaiKhoan.close();
	
	if(dinhkhoancoList != null) dinhkhoancoList.clear(); 
	if(SanphamRs != null) SanphamRs.close();
	if(NganhangRs != null) NganhangRs.close();
	if(NccRs != null) NccRs.close();
	if(KhachhangRs != null) KhachhangRs.close();
	if(NhanvienRs != null) NhanvienRs.close();
	if(DoituongkhacRs != null) DoituongkhacRs.close();
	if(TaisanRs != null) TaisanRs.close();
	kbhList = null;
	nppList = null;
	htttList = null;
	ndList = null;
	nccList = null;
	nhomkhttList = null;
	nvtutList = null;
	nganhangList = null;
	//chinhanhList = null;
	tienteList = null;
	hoadonList = null;
	sotkRs = null;
	RSTaiKhoan = null;
	
	dinhkhoancoList = null;
	SanphamRs = null;
	NganhangRs = null;
	NccRs = null;
	KhachhangRs = null;
	NhanvienRs = null;
	DoituongkhacRs = null;
	TaisanRs = null;
}catch (Exception ex)
{
	ex.printStackTrace();
}finally{
	tthdBean.DBclose();
	tthdBean = null;
	session.setAttribute("tthdBean", null);
}
%>