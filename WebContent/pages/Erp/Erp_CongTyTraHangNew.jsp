<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="geso.traphaco.erp.beans.donmuahang.*"%>
<%@ page import="geso.traphaco.erp.beans.donmuahang.imp.*"%>
<%@ page import="geso.traphaco.erp.beans.congtytrahang.IErpCongtytrahang "%>
<%@ page import="geso.traphaco.erp.beans.congtytrahang.imp.ErpCongtytrahang "%>
<%@ page import="java.sql.ResultSet"%>
<%@ page import="java.util.Hashtable"%>
<%@ page import="java.util.List"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%
	IErpCongtytrahang dmhBean = (IErpCongtytrahang) session.getAttribute("dmhBean");
	ResultSet dvthList = dmhBean.getDvthList();
	ResultSet loaihhList = dmhBean.getLoaiList();
	List<ISanpham> spList = dmhBean.getSpList();
	List<IDonvi> dvList = dmhBean.getDvList();
	List<ITiente> ttList = dmhBean.getTienteList();
	List<IKho> khoList = dmhBean.getKhoList();
	String userId = (String) session.getAttribute("userId");
	String userTen = (String) session.getAttribute("userTen");
	DecimalFormat formatter = new DecimalFormat("###,###,###");
	NumberFormat formater = new DecimalFormat("##,###,###.####");
	Hashtable<String, String> sanpham_soluong = dmhBean.getSanpham_Soluong();
	Hashtable<String, String> sanpham_soluongDAXUAT = dmhBean.getSanpham_SoluongDAXUAT_THEODN();
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

<script type="text/javascript" src="../scripts/ajax.js"></script>
<script type="text/javascript" src="../scripts/erp-spTraNCCList.js"></script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script language="javascript" type="text/javascript">

	function replaces()
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
					document.forms['dmhForm'].action.value='submit';
				    document.forms["dmhForm"].submit();
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
		var nguongoc = document.getElementById("nguongoc").value;
		var tiente = document.getElementById("tiente_fk");
		var khonhan= document.getElementsByName("khonhan");
		if(nguongoc == 'NN'){
			var donGiaUSD = document.getElementsByName("donGiaUSD");
		}
		else
			{
			// trong nuoc co thue xuat
			var thuexuat = document.getElementsByName("thuexuat");
			var tienvat = document.getElementsByName("tienvat");
			var flag_tienvat = document.getElementsByName("flag_tienvat");
			
			}
		
		
		
		var pos = parseInt(tiente.value.indexOf(" - ")) + 3;
		var tygia = tiente.value.substring(pos);
		
		var sodong =  masp.length;
		var i;
		var bvat = 0;
		var avat = 0;
		var bvatNguyenTe = 0;
		var avatNguyenTe = 0;
		
		for(i = 0; i < sodong; i++)
		{
			if(masp.item(i).value != "")
			{
				for(var k = 0;k <sodong ;k++)
		 		{
		 			if(parseInt(k)!=parseInt(i))//khong phai ma hien tai
		 			{
		 				if((idsp[i].value == idsp[k].value) && idsp[k].value.length !=0 && ngaynhan[i].value == ngaynhan[k].value && ngaynhan[k].value.length != 0 )
	 					{
	 						ngaynhan.item(k).value= '';
	 					}
		 			}
		 		}
				
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" -- "));
				var slg = soluong.item(i).value.replace(/,/g,""); 
				
				if(pos > 0)
				{
					masp.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" -- ")) + 3);
					
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					$("#donvitinh_"+i).select2("val", sp.substring(0, parseInt(sp.indexOf("] ["))));


					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					if(nguongoc == 'NN'){
						
					}
					else
						{
						// trong nuoc co thue xuat
						thuexuat.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
						}
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					nhomhang.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
				}
				var khonhanid = khonhan.item(i).value.replace(/,/g,""); 
				if(khonhanid !='')
				{
					soluong.item(i).readOnly = false;
				}
				/* if(soluong.item(i).value != "")
				{	
					soluong.item(i).value = DinhDangDonGia(slg);
					if(dongia.item(i).value != "")
					{
						// don gia nguyen te
						var dgia = dongia.item(i).value.replace(/,/g,""); 
						dongia.item(i).value = DinhDangDonGia_sole (dgia,4);  // don gia nguyen te
						
						var tt;
						var tienthue;
						// tinh thanh tien tung dong
						if(nguongoc == 'NN'){ // nhap khau ko co thue

						
							var temp_x = parseFloat(replaceAll(dgia,",","")) * parseFloat(replaceAll(tygia,",",""));
							donGiaUSD.item(i).value =DinhDangDonGia(  Math.round (temp_x )); // don gia viet
							tt = Math.round ( parseFloat(slg) * parseFloat(donGiaUSD.item(i).value.replace(/,/g,"")) )  ;
							
							// tien viet
							bvat = bvat + tt;
							avat = avat + tt;
							// nguyen te
							//console.log("xx :"+   roundNumber(  parseFloat(slg) * parseFloat(dongia.item(i).value.replace(/,/g,"")) ,2) );
							bvatNguyenTe +=  roundNumber(  parseFloat(slg) * parseFloat(dongia.item(i).value.replace(/,/g,"")) ,2) ;
							avatNguyenTe += roundNumber(  parseFloat(slg) * parseFloat(dongia.item(i).value.replace(/,/g,"")) ,2) ;
							
						} else {// trong nuoc
							
							if(flag_tienvat.item(i).value =="1" ){
								
								tienthue=parseFloat ( tienvat.item(i).value.replace(/,/g,""));
							}
							else
								{
								tienthue=  Math.round ( Math.round( parseFloat(slg) * parseFloat(dgia) ) * thuexuat.item(i).value.replace(/,/g,"")/100 );
								}
							   
							tt =   Math.round ( parseFloat(slg) * parseFloat(dgia),0)  + tienthue ;
							
							
							tienvat.item(i).value = DinhDangDonGia( Math.round  (tienthue) );
						
							bvat = bvat + Math.round ( parseFloat(slg) * parseFloat(dgia));
							avat = avat + tt;
							
							bvatNguyenTe +=  Math.round ( parseFloat(slg) * parseFloat(dgia));
							avatNguyenTe += tt;
						}
					    //console.log("thanh tien: "+ DinhDangDonGia( Math.round  (tt) ))
						thanhtien.item(i).value = DinhDangDonGia( Math.round  (tt) );
						
					} 
					
					
					
				}
				else			
				{
					dongia.item(i).value = "";
					thanhtien.item(i).value = "";	
				}*/
			}
			else
			{
				idsp.item(i).value = "";
				tensp.item(i).value = "";
				donvitinh.item(i).value = "";
				nhomhang.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";
			
				
				if(nguongoc == 'NN'){
					donGiaUSD.item(i).value="";
				}
				else
					{
					// trong nuoc co thue xuat
					thuexuat.item(i).value="";
					}
			}
		}	
		
		//TinhTien();
		// tinh tien ô tổng
		//console.log(" avat :"+ avat);
		/* document.getElementById("BVAT").value = DinhDangTien( Math.round(bvat) );
		document.getElementById("AVAT").value = DinhDangTien( Math.round(avat) );
		
		if(document.getElementById("AUSDVAT") && document.getElementById("BUSDVAT")){
			console.log(" ausdvat :"+bvatNguyenTe );
			console.log(" xXX :"+bvatNguyenTe );
			document.getElementById("AUSDVAT").value= DinhDangDonGia (roundNumber(avatNguyenTe ,2));
			document.getElementById("BUSDVAT").value= DinhDangDonGia (roundNumber ( bvatNguyenTe ,2));	
		}  */
		
		setTimeout(replaces, 300);
	}
	
	
	
	function Tinhtien (){
		var nccId = document.getElementById("nccId");
		var nccTen = document.getElementById("nccTen");
		var nccLoai = document.getElementById("nccLoai");
		var idsp = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");
		var donvitinh = document.getElementsByName("donvitinh");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		
		
		
		var thanhtien = document.getElementsByName("thanhtien");
		var nhomhang = document.getElementsByName("nhomhang");
		var ngaynhan = document.getElementsByName("ngaynhan");
		var nguongoc = document.getElementById("nguongoc").value;
		var tiente = document.getElementById("tiente_fk");
		
		if(nguongoc == 'NN'){
			var donGiaUSD = document.getElementsByName("donGiaUSD");
		}
		else
			{
			// trong nuoc co thue xuat
			var thuexuat = document.getElementsByName("thuexuat");
			var tienvat = document.getElementsByName("tienvat");
			var flag_tienvat = document.getElementsByName("flag_tienvat");
			
			}
		
		
		
		var pos = parseInt(tiente.value.indexOf(" - ")) + 3;
		var tygia = tiente.value.substring(pos);
		
		var sodong =  masp.length;
		var i;
		var bvat = 0;
		var avat = 0;
		var bvatNguyenTe = 0;
		var avatNguyenTe = 0;
		console.log(" vo tinh tien: ")
		for(i = 0; i < sodong; i++)
		{
			if(masp.item(i).value != "")
			{
				
				var dongiadong = parseFloat(dongia.item(i).value.replace(/,/g,""));
				console.log("masp: "+masp.item(i).value )
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" -- "));
				var slg = soluong.item(i).value.replace(/,/g,""); 
				if(soluong.item(i).value != "")
				{	
					soluong.item(i).value = DinhDangDonGia(slg);
					console.log("gia: "+ dongia.item(i).value  )
					if(dongia.item(i).value != "")
					{
						console.log(" gia mua: "+ dongia.item(i).value)
						// don gia nguyen te
						var dgia = dongia.item(i).value.replace(/,/g,""); 
						dongia.item(i).value = DinhDangDonGia_sole (dgia,4);  // don gia nguyen te
						
						var tt;
						var tienthue;
						// tinh thanh tien tung dong
						if(nguongoc == 'NN'){ // nhap khau ko co thue

						
							var temp_x = parseFloat(replaceAll(dgia,",","")) * parseFloat(replaceAll(tygia,",",""));
							donGiaUSD.item(i).value =DinhDangDonGia(  Math.round (temp_x )); // don gia viet
							tt = Math.round ( parseFloat(slg) * parseFloat(donGiaUSD.item(i).value.replace(/,/g,"")) )  ;
							
							// tien viet
							bvat = bvat + tt;
							avat = avat + tt;
							// nguyen te
							//console.log("xx :"+   roundNumber(  parseFloat(slg) * parseFloat(dongia.item(i).value.replace(/,/g,"")) ,2) );
							bvatNguyenTe +=  roundNumber(  parseFloat(slg) * parseFloat(dongia.item(i).value.replace(/,/g,"")) ,2) ;
							avatNguyenTe += roundNumber(  parseFloat(slg) * parseFloat(dongia.item(i).value.replace(/,/g,"")) ,2) ;
							
						} else {// trong nuoc
							console.log(" trong nuoc : ");
							var soluongCT = document.getElementsByName(masp.item(i).value+ "_spSOLUONG");
							var hethanCT = document.getElementsByName(masp.item(i).value+ "_spNGAYHETHAN");
							var tienchitiet =0;
							var tienthue = 0;
							var thuevat1 =thuexuat.item(i).value.replace(/,/g,"");
							if(thuevat1 =='')
								thuevat1 ="0";
							var soluongchitiet=0;
							

							/*   for(var j = 0; j < soluongCT.length; j++)
							{
								 
								var slct = soluongCT.item(j).value.replace(/,/g,"");
								if(slct != "")
								{
									
									var t = parseFloat(dongia.item(i).value.replace(/,/g,""))*parseFloat(slct);
									var rt= Math.round(parseFloat(t)*100);
									rt = Math.round(parseFloat(rt)/100);
									tienchitiet = parseFloat(tienchitiet) +   parseFloat(rt) ;
									tienthue = parseFloat(tienthue) +  Math.round( ( parseFloat(rt))  * parseFloat(thuevat1) / 100);
									
									
 								}
							} */  
							 
					
						  //tinh tien moi
						    var n; // số lượng phần tử mảng a
						    var m=0;// số lượng phần tử mảng b
						    var bhethan = [];// mảng b chính là mảng phụ chứa các phần tử phân biệt của mảng 
							n=hethanCT.length;
						 	m = 0;
						 	  for(var f = 0; f < hethanCT.length; f++)
								{
						 		 var hhct = hethanCT.item(f).value.replace(/,/g,"");
						 		 if(f==0)
						 			 {
						 			 bhethan[m] = hhct; // phần tử đầu tiên của mảng a chính là phần tử phân biệt

						 			 }
						 									 		  
								}
						 	  
						 	 console.log(" so luong 1 : "+bhethan[0]);
						 //	
						   
						 //	console.log(" hethanCT.item(0).value: " + soluongCT.item(0).value.replace(/,/g,""));
						 	m++; // số lượng phần tử mảng b tăng lên 1 đơn vị
						    
						    
						 // vòng lặp duyệt từng phần tử của mảng a
						     for (var g = 1; g < n; g++)
						    {
						      // duyệt từng phần tử của mảng a, duyệt từ vị trí i - 1 đến 0
						      // <=> duyệt từ vị trí i-1 về đầu mảng, để tìm xem a[i] trước đó có tồn tại hay chưa
						      // nếu a[i] mà duyệt về trước đó mà chưa có tồn tại thì a[i] chính là phần tử phân biệt
						      var Kiem_Tra = true; // giả sử a[i] sẽ là phần tử phân biệt
						      for (var d = g - 1; d >= 0; d--)
						      {
						        // nếu a[i] bằng a[j] <=> a[i] đã có tồn tại trước đó 
						        if (hethanCT.item(g).value ==hethanCT.item(d).value)
						        {
						          Kiem_Tra = false; // cập nhật lại a[i] không phải là phần tử phân biệt
						          break; // dừng việc kiểm tra
						        }
						      }
						      if (Kiem_Tra == true)
						      {
						    	  bhethan[m] = hethanCT.item(g).value; // đưa phần tử phân biệt vào mảng b
						        m++; // số lượng phần tử mảng b tăng lên 1 đơn vị
						      }
						    } 
						    
						    console.log( "so phan tu là: "  + m);
						      for (var t = 0; t < m; t++)
					            {
					                var dem = 0;
					                var tonggiatri=0;
					                // duyệt từng phần tử của mảng b với toàn bộ mảng a để tìm số lần xuất hiện
					                 for (var h = 0; h < n; h++)
					                {
					                	 var slct = soluongCT.item(h).value.replace(/,/g,"");  
					                	 if(slct=="" || slct==null)
					                		 slct=0;
					                	 
					                    if (bhethan[t] == hethanCT.item(h).value)
					                    {
					                        dem++; // số lần xuất hiện của phần tử b[i] tăng lên 1 
					                        
					                       
					                        tonggiatri += parseFloat(slct);
					                    }
					                }
					                

									tienchitiet = parseFloat(tienchitiet) +   Math.round(parseFloat(tonggiatri*dongiadong))
									tienthue = parseFloat(tienthue) +  Math.round(Math.round(parseFloat(tonggiatri*dongiadong))  * parseFloat(thuevat1) / 100);
									
					             }  
							    
							  //het tinh tien moi
							  

							      

							      
							tt = parseFloat(tienchitiet)+ parseFloat(tienthue);
							
							tienvat.item(i).value = DinhDangDonGia( Math.round  (tienthue) );
						
							bvat = bvat + parseFloat(tienchitiet);
							avat = avat + tt;
							
							bvatNguyenTe += parseFloat(tienchitiet);
							avatNguyenTe += tt;
						}
					    //console.log("thanh tien: "+ DinhDangDonGia( Math.round  (tt) ))
						thanhtien.item(i).value = DinhDangDonGia( Math.round  (tt) );
						
					}
					
					
					
				}
				else			
				{
					dongia.item(i).value = "";
					thanhtien.item(i).value = "";	
				}
			}
			else
			{
				idsp.item(i).value = "";
				tensp.item(i).value = "";
				donvitinh.item(i).value = "";
				nhomhang.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";
			
				
				if(nguongoc == 'NN'){
					donGiaUSD.item(i).value="";
				}
				else
					{
					// trong nuoc co thue xuat
					thuexuat.item(i).value="";
					}
			}
		}	
		
		//TinhTien();
		// tinh tien ô tổng
		//console.log(" avat :"+ avat);
		document.getElementById("BVAT").value = DinhDangTien( Math.round(bvat) );
		document.getElementById("AVAT").value = DinhDangTien( Math.round(avat) );
		
		if(document.getElementById("AUSDVAT") && document.getElementById("BUSDVAT")){
			console.log(" ausdvat :"+bvatNguyenTe );
			console.log(" xXX :"+bvatNguyenTe );
			document.getElementById("AUSDVAT").value= DinhDangDonGia (roundNumber(avatNguyenTe ,2));
			document.getElementById("BUSDVAT").value= DinhDangDonGia (roundNumber ( bvatNguyenTe ,2));	
		}
	}
	
	
	
	
	 function replaceAll(str, find, replace) {
		  return str.replace(new RegExp(find, 'g'), replace);
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
		 
		 if(checkSanPham() ==false)
		 {	
			 error.value="Không có sản phẩm nào được chọn";
			return;
		 }
		 
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['dmhForm'].action.value='save';
	     document.forms['dmhForm'].submit();
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
	 
	 
	 function DinhDangDonGia_sole(num, thapphan) 
		{
		 	//console.log(" so dau vao :"+ num);
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
				sole = sole.substring(0,thapphan+1);
				kq = (((sign)?'':'-') + num) + sole;
			}
			else
				kq = (((sign)?'':'-') + num);
			
			//alert(kq);
			return kq;
		}
	 
	 
	 function FormatNumber(e)
		{
			e.value = DinhDangDonGia_sole(e.value, 4);
		}
	 
	 
	 function ChageTienVat(id) 
	 {
		 console.log("id flag :"+ id)
		 var stt=id.substring(id.indexOf("____"));
		 console.log("stt :"+ stt)
		 document.getElementById("flag_tienvat"+stt).value="1";
		 Tinhtien ();
	 }
		
	 
	 
</script>
<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
	<form name="dmhForm" method="post" action="../../ErpCongtytrahangUpdateSvl">
		<input type="hidden" name="userId" value='<%=userId%>'> 
		<input type="hidden" name="action" value="1" />
		
		<div id="main" style="width: 100%; padding-left: 2px">
			<div align="left" id="header" style="width: 100%; float: none">
				<div style="width: 60%; padding: 5px; float: left" class="tbnavigation">Quản lý mua hàng > Nghiệp vụ khác > Đơn trả hàng NCC > Tạo mới</div>
				<div align="right" style="padding: 5px" class="tbnavigation">
					Chào mừng bạn <%= userTen %>
				</div>
			</div>
			<div align="left" id="button" style="width: 100%; height: 32px; padding: 0px; float: none" class="tbdarkrow">
				<A href="../../ErpCongtytrahangSvl?userId=<%=userId%>"> <img src="../images/Back30.png" alt="Quay ve" title="Quay ve" border="1px"
					longdesc="Quay ve" style="border-style: outset"></A> <span id="btnSave"> <A href="javascript:saveform()"> <IMG
						src="../images/Save30.png" title="Luu lai" alt="Luu lai" border="1px" style="border-style: outset"></A>
				</span>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea name="dataerror" id="Msg" rows="1" readonly="readonly" style="width: 100%"><%=dmhBean.getMsg()%></textarea>
				</fieldset>
			</div>
			<div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Đơn trả hàng</legend>
					<div style="float: none; width: 100%" align="left">
						<TABLE width="100%" cellpadding="4" cellspacing="0">
							<TR>
								<TD class="plainlabel" valign="top" width="150px">Ngày trả hàng</TD>
								<TD class="plainlabel" valign="top" colspan="3" >
									<input type="text" class="days" id="ngaymuahang" name="ngaymuahang"
											value="<%= dmhBean.getNgaymuahang() %>" maxlength="10" readonly />
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Đơn vị thực hiện</TD>
								<TD class="plainlabel" width="280px">
									<select name="dvthId" id="dvthId" style="width: 200px">
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
								<TD class="plainlabel" width="150px">Nguồn gốc</TD>
								<TD class="plainlabel">
									<select name="nguongoc" id="nguongoc" onChange="submitform();" style="width: 200px">
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
											<option value=""></option>
									</select>
								</TD>
							</TR>
							<TR>
								<TD class="plainlabel">Mã nhà cung cấp</TD>
								<TD class="plainlabel">
									<input type="text" id="nccId" name="nccId" value="<%= dmhBean.getNCC() %>" >
								</TD>
								<TD class="plainlabel">Tên nhà cung cấp</TD>
								<TD class="plainlabel">
									<input type="text" id="nccTen" style="width: 300px;" name="nccTen" value="<%= dmhBean.getNccTen() %>" readonly="readonly" >
									<input type="hidden" id="nccLoai" name="nccLoai" value="<%= dmhBean.getNccLoai() %>" readonly="readonly" >
								</TD>
							</TR>
							<TR class="plainlabel">
								<TD>Tiền tệ</TD>
								<TD>
									<Select name="tiente_fk" id="tiente_fk" onChange="submitform();" style="width: 200px">
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
							
								
								<TD>Tỉ giá</TD>
								<TD>
									<input type="text" style="text-align: right;"
									<%if( dmhBean.GetTyGiaQuyDoi()!= null &&  dmhBean.GetTyGiaQuyDoi()>0){%>
									 value="<%=formatter.format( dmhBean.GetTyGiaQuyDoi())%>" 
									 <%} else{ %>
									 value="<%= dmhBean.GetTyGiaQuyDoi()%>"
									 <%} %>
									 readonly="readonly" >
								</TD>
							</TR>
							
							<TR>
								<TD class="plainlabel">Loại hàng hóa</TD>
								<TD class="plainlabel" colspan="3">
								<% if(dmhBean.getNccLoai().trim().equals("100003")) { %>
									<input type="text" value="Sản phẩm nhập kho" readonly="readonly" >
									<input name="loaihh" id="loaihh" type="hidden" value="0"  >
								<% } else { %>
									<select name="loaihh" id="loaihh" onChange="changeNoiDung();" style="width: 200px">
									<% if(dmhBean.getLoaihanghoa().equals("1")) { %>
										<option value="1" selected="selected">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
										<!--  <option value="2">Chi phí dịch vụ</option> -->
									<% } else { if(dmhBean.getLoaihanghoa().equals("2")){ %>
										<!-- <option value="2"  selected="selected">Chi phí dịch vụ</option>-->
										<option value="1">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
									<% } else {  %> 
										<option value="0" selected="selected">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<!--  <option value="2">Chi phí dịch vụ</option>-->
									<%} } %>
									</select>
								<% } %>
																	
								</TD>
									
								
							</TR>

							<TR>
								<TD class="plainlabel">Tổng tiền chưa VAT</TD>
								<TD class="plainlabel" >
								<input type="text" name="BVAT" id="BVAT" value="<%=dmhBean.getTongtienchuaVat()%>" style="text-align: right;"readonly="readonly"> VNĐ
								</TD>
								
								
								<%if(dmhBean.getNguonGocHH().equals("NN")) { %>
								<td class="plainlabel"  style="width: 200px" >Tổng tiền Nguyên Tệ chưa VAT</td>
									<td class="plainlabel"><input type="text" name="BUSDVAT" id="BUSDVAT" readonly="readonly" style="text-align: right;">
									</td>	
									
								<%} else{ %>
								<TD class="plainlabel" colspan="2">
								<%} %>
									
								<%-- <TD class="plainlabel">VAT</TD>
								<TD class="plainlabel"><input type="text" name="VAT" id="VAT" value="<%=dmhBean.getVat()%>" style="text-align: right;"
									onkeypress="return keypress(event);"> %</TD> --%>
							</TR>
							
							
							
							<tr>
									<TD class="plainlabel">Tổng tiền sau VAT</TD>
									<TD class="plainlabel"><input type="text" name="AVAT" id="AVAT" value="<%=dmhBean.getTongtiensauVat()%>" style="text-align: right;"
									readonly="readonly"> VNĐ</TD>
									
								<%if(dmhBean.getNguonGocHH().equals("NN")) { %>	
									<td class="plainlabel" style="width: 200px">Tổng tiền Nguyên Tệ sau VAT</td>
									<td class="plainlabel"><input type="text" name="AUSDVAT" id="AUSDVAT" readonly="readonly" style="text-align: right;">
									</td>
										
								<%} else{ %>
								<TD class="plainlabel" colspan="2">
								<%} %>
										
									
							</tr>
							
							<TR>
								
								<TD class="plainlabel">Dung sai (+/-)</TD>
								<TD class="plainlabel" colspan="3"><input type="text" name="dungsai" id="dungsai" value="<%=dmhBean.getDungsai()%>" style="text-align: right;"
									onkeypress="return keypress(event);"> %</TD>
							</TR>
							
							
							<TR>
								<TD class="plainlabel">Ghi chú</TD>
								<TD class="plainlabel" ><input type="text" name="ghichu" id="ghichu" value="<%= dmhBean.getGhiChu()%>" style="text-align: left;"></TD>
								
								<TD class="plainlabel">Lý do trả hàng</TD>
								<TD class="plainlabel" ><input type="text" name="lydotrahang" id="lydotrahang" value="<%= dmhBean.getLydotrahang()%>" style="text-align: left;"></TD>
							</TR>
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
								<% } else { %>
									<TH align="center" width="10%" >Mã chi phí</TH>
									<TH align="center" width="15%">Diễn giải</TH>
								 <% } } %>
								
								<TH align="center" width="5%">ĐVT</TH>
								<TH align="center" width="5%">Số lượng</TH>
								
								<%if(dmhBean.getNguonGocHH().equals("NN")) { // Mua hàng nhập khẩu mới chọn loại%>
								<TH align="center" width="12%">
										<table>
											<tr>
												<th colspan="2">Đơn giá</th>
											</tr>
											<tr>
												<th style="text-align: center; border: 1px" >Nguyên Tệ </th>
												<th style="text-align: center; border: 1px" >VND</th>
										</table>
								</TH>	
								<%} else{ %>
									<TH align="center" width="7%">Đơn giá  </TH>
									<TH align="center" width="7%">Thuế suất </TH>
									<TH align="center" width="7%">Tiền Vat </TH>
								<%} %>
								
								
								<TH align="center" width="8%">Thành tiền</TH>
								
								

								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
									<TH align="center" width="10%">Nhóm hàng</TH>
								<% } else { if(dmhBean.getLoaihanghoa().trim().equals("1")) { %> 
									<TH align="center" width="10%">Nhóm tài sản</TH>
								<% } else { %>
									<TH align="center" width="10%">Nhóm chi phí</TH>
								 <% } } %>
								
								<TH align="center" width="5%">Ngày trả</TH>

								<% if ( dmhBean.getLoaihanghoa().trim().equals("0") )
								   { %>
									<TH align="center" width="10%" >Kho trả</TH>
								<% } else { %>
									<TH align="center" width="10%" style="display: none" >Kho nhận</TH>
								<% } %>
								
								<!-- <TH align="center"  width="5%">Tên in PO</TH> -->
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
											</TD>
											<TD align="center" width="8%" >
												<input type="text" name="masp" style="width: 100%" value="<%= sp.getMasanpham() %>"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
											</TD>
											<TD align="left" width="10%">
												<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" readonly="readonly" > 
												<% } else {  %>
													<input type="text" value="<%= sp.getTensanpham() %>" name="tensp" style="width: 100%" > 
												<%  } %>
											</TD>
											<TD align="center" width="6%">
												<%-- <input type="text" value="<%= sp.getDonvitinh() %>" name="donvitinh" style="width: 100%" > --%> 
												
												<select style="width: 100%" name="donvitinh" id="<%= "donvitinh_"+i %>" style=" width: 200px" >
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
												<input type="text" value="<%= sp.getSoluong() %>" name="soluong" style="width: 50%; text-align: right;" readonly onchange=" Tinhtien ();submitform();" > 
											<% if(sp.getSoluong().trim().length() > 0&& sp.getKhonhan() !=null && sp.getKhonhan().trim().length()>0) { %>
										<a href="" id="scheme_<%= sp.getMasanpham()   %>" rel="subcontent_<%=sp.getMasanpham()  %>">
				           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
				           	 		
			           	 		 		<DIV id="subcontent_<%= sp.getMasanpham()   %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
						                             background-color: white; width: 660px; max-height:300px; overflow:auto; padding: 4px;">
						                    <table width="98%" align="center">
						                    	<tr>
						                    		<td ><b>Tổng xuất</b></td>
						                    		<td colspan="3" > <input type="text" name="soluong2" value="<%= sp.getSoluong() %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
						                    	</tr>
						                        <tr>
					                            <th width="100px" style="font-size: 11px">Số lượng</th>
					                            <th width="100px" style="font-size: 11px">Số lô</th>
					                            <th width="100px" style="font-size: 11px">Ngày hết hạn</th>
												<th width="150px" style="font-size: 11px">Vị trí</th>
												<th width="100px" style="font-size: 11px">Mã thùng</th>
												<th width="100px" style="font-size: 11px">Mã mẻ</th>
												<th width="100px" style="font-size: 11px">Mã phiếu</th>
												<th width="100px" style="font-size: 11px">Hàm lượng</th>
												<th width="100px" style="font-size: 11px">Hàm ẩm</th>
												<th width="100px" style="font-size: 11px">Ngày nhập kho</th>
												<th width="100px" style="font-size: 11px">Mã RQ</th>
												<th width="100px" style="font-size: 11px">Nhà sản xuất</th>
					                            <th width="100px" style="font-size: 11px">Tồn kho</th>
					                        </tr>
			                            	<%
			                            		ResultSet spRs = dmhBean.getSoloTheoSp(sp.getMasanpham() , sp.getDonvitinh(),sp.getSoluong(),sp.getKhonhan());
				                        		if(spRs != null)
				                        		{
				                        			while(spRs.next())
				                        			{
				                        				String tudeXUAT = "";
				                        				if(spRs.getString("tuDEXUAT").trim().length() > 0)
				                        					tudeXUAT = formater.format(spRs.getDouble("tuDEXUAT"));
				                        				
				                        				String temID = sp.getMasanpham() ;
				                        				String key = sp.getMasanpham()  + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN") + "__" + spRs.getString("VITRI") + "__" + spRs.getString("MATHUNG") + "__" + spRs.getString("MAME")+ "__" + spRs.getString("MAPHIEU")+ "__" + spRs.getString("HAMLUONG")+ "__" + spRs.getString("HAMAM")+ "__" + spRs.getString("NGAYNHAPKHO")+ "__" + spRs.getString("MARQ")+ "__" + spRs.getString("NSX_FK");
				                        				
				                        				String key2 = sp.getMasanpham()  + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN") + "__" + spRs.getString("VITRI") + "__" + spRs.getString("MATHUNG") + "__" + spRs.getString("MAME")+ "__" + spRs.getString("MAPHIEU")+ "__" + spRs.getString("HAMLUONG")+ "__" + spRs.getString("HAMAM")+ "__" + spRs.getString("NGAYNHAPKHO")+ "__" + spRs.getString("MARQ")+ "__" + spRs.getString("NSX_FK");
				                        				System.out.println("::: KEY 2 : " + key2  );
				                        				System.out.println("::: tudeXUAT : " + tudeXUAT  );
				                        				if( sanpham_soluongDAXUAT.get(key2) == null && tudeXUAT.trim().length() > 0 )
				                        				{
				                        					
				                        					sanpham_soluongDAXUAT.put(key2, tudeXUAT);
				                        				}
				                        				else
				                        				{
				                        					System.out.println("::: vo day : "   );
				                        					double tempSL = 0;
				                        					if( tudeXUAT.trim().length() > 0 )
				                        						tempSL = Double.parseDouble( tudeXUAT.replaceAll(",", "" ) );
				                        					
				                        					System.out.println("::: KEY 2.1 : " + key + " -- VALUE: " +  sanpham_soluong.get(key) );
				                        					double soluongXUAT = tempSL; 
				                        					if( sanpham_soluongDAXUAT.get(key2) != null && sanpham_soluongDAXUAT.get(key2).trim().length() > 0 )
				                        						soluongXUAT +=	Double.parseDouble( sanpham_soluongDAXUAT.get(key2) );
				                        					
				                        					sanpham_soluongDAXUAT.put(key2, Double.toString(soluongXUAT));
				                        				}
				                        				System.out.println("sanpham_soluong.get( key ) " + sanpham_soluong.get( key )  );
				                        			%>
				                        			
				                        			<tr>
				                        				<td>
				                        				<% if(sanpham_soluong.get( key ) != null ) { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater.format(Double.parseDouble( sanpham_soluong.get( key ).replaceAll(",", ""))) %>"  onchange=" Tinhtien ()">
				                        				<% } else { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= tudeXUAT  %>"  onchange=" Tinhtien ()">
				                        				<% } %>
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spSOLO" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYHETHAN" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spVITRI" value="<%= spRs.getString("VITRI") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spMATHUNG" value="<%= spRs.getString("MATHUNG") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spMAME" value="<%= spRs.getString("MAME") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spMAPHIEU" value="<%= spRs.getString("MAPHIEU") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spHAMLUONG" value="<%= spRs.getString("HAMLUONG") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spHAMAM" value="<%= spRs.getString("HAMAM") %>" readonly="readonly">
				                        				</td>
				                        				<td >
				                        					<input type="text" style="width: 100%;text-align: center;" name="<%= temID %>_spNGAYNHAPKHO" value="<%= spRs.getString("NGAYNHAPKHO") %>" readonly="readonly">
				                        				</td>
				                        				<td>
				                        					
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spMARQ" value="<%= spRs.getString("MARQ") %>" readonly="readonly">
				                        					
				                        				</td>
				                        				<td>
				                        					<input type="hidden" style="width: 100%;text-align: right;" name="<%= temID %>_spNSXID" value="<%= spRs.getString("NSX_FK") %>" readonly="readonly">
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spNSXTEN" value="<%= spRs.getString("NSXTEN") %>" readonly="readonly">
				                        					
				                        				</td>
				                        				<td>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTONKHO" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
				                        				</td>
				                        			</tr>
				                        			
				                        		 <% } } %>	 
					                     </table>
					                     <div align="right">
					                     	<label style="color: red" ></label>
					                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= sp.getMasanpham()   %>')" > Đóng lại </a>
					                     </div>
						            </DIV> 
						            
						            <script type="text/javascript">
						            	dropdowncontent.init('scheme_<%= sp.getMasanpham()    %>', "right-top", 300, "click");
						            </script>
						         <% } else { %>
						         	<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
						         <% } %>
											
											</TD>
											
												<%-- <input type="text" value="<%= sp.getDongia() %>" name="dongia" style="width: 100%; text-align: right;" >  --%>
											
													<% if (dmhBean.getNguonGocHH().equals("NN") ){ %>
													<TD align="center" width="8%">
														<table>
														<tr>
															<TD align="center" width="8%">
															<input type="text" value="<%= sp.getDongia() %>" name="dongia" style="width: 100%; text-align: right;" onchange=" Tinhtien ()" >
														    </TD>
														    <TD align="center" width="8%">
														    <input type="text" name="donGiaUSD"  style="width:100%;text-align:right" readonly="readonly">
															</TD>
														</tr>
														</table>
													</TD>
														<%} else {%>
															<TD align="center" width="8%">
																<input type="text" value="<%= sp.getDongia() %>" name="dongia"  style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)" onchange=" Tinhtien ()" >
															</TD>
															<TD align="center" width="8%">
																<input type="text" value="<%= sp.getThuexuat() %>" name="thuexuat" style="width: 100%; text-align: right;" onchange=" Tinhtien ()"  >
															</TD>
															
															<TD align="center" width="8%">
																<input type="text" value="<%= sp.getTienVAT() %>" id="tienvat____<%=i%>"  name="tienvat" style="width: 100%; text-align: right;"  onchange="ChageTienVat('tienvat____<%=i%>')">
																
																<input type="hidden" value="" name="flag_tienvat"  id="flag_tienvat____<%=i%>"  style="width: 100%; text-align: right;" readonly="readonly" >
															</TD>
														<%} %> 
											<input type="hidden" value="<%= sp.getDongiaOLD() %>" name="dongiaOld" style="width: 100%; text-align: right;" >
	 
											
											
											
											
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getThanhtien() %>"  name="thanhtien" style="width: 100%; text-align: right;" > 
											</TD>
											
											
											<TD align="center" width="8%">
												<input type="text" value="<%= sp.getNhomhang() %>" style="width: 100%" name="nhomhang" readonly="readonly" > 
											</TD>
											<TD align="center" width="7%">
												<input type="text" value="<%= sp.getNgaynhanstr() %>" style="width: 100%" name="ngaynhan" readonly class="days" > 
											</TD>
			
											<% if ( dmhBean.getLoaihanghoa().trim().equals("0") )
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
												 	
												 		<select style="width: 100%; min-width: 150px;" name="khonhan">
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
											
											<%-- <td align="center">
		           	 						<a href="" id="tenhd_<%= i %>" rel="subcontent<%= i %>">
			           	 							<img alt="Tên in trên PO" src="../images/vitriluu.png"></a>
			           	 					<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
						                             background-color: white; width: 520px; padding: 4px;">
						                    <table width="90%" align="left">
						                        <tr>
						                            <th width="100px">Tên in PO</th>
						                        </tr>
			                        			<tr>
						                            <td>
						                            	<input type="text" size="100px" name="tenhoadon" value="" onchange="replaces()"/>
						                            </td>
						                        </tr>
						                    </table>
						                     <div align="right">
						                     	<label style="color: red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')" class="legendtitle" >Hoàn tất</a>
						                     </div>
						               		 </DIV>
		           	 						</td> --%>
											
										</tr>
										
									<% count++; }
								} %>
							
							<% for(int i = count; i < 40; i++) { %>
							
								<tr>
									<TD align="center" width="2%">
										<input  type="text" value="<%= i + 1 %>" style="text-align: center;" readonly="readonly">
										<input type="hidden" value="" name="idsp" style="width: 100%"  >
										<input type="hidden" value="" name="mnlId" style="width: 100%" readonly="readonly" > 
									</TD>
									<TD align="center" width="8%" >
										<input type="text" value="" name="masp" style="width: 100%" onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
									</TD>
									<TD align="left" width="10%">
									<% if ( dmhBean.getLoaihanghoa().trim().equals("0") ) { %>
										<input type="text" value="" name="tensp" style="width: 100%" readonly="readonly" > 
									<% } else { %>
										<input type="text" value="" name="tensp" style="width: 100%" > 
									<% } %>
									</TD>
									<TD align="center" width="6%">
										<!-- <input type="text" value="" name="donvitinh" style="width: 100%" >  -->
										
										<select style="width: 100%" name="donvitinh"  id=<%= "donvitinh_"+ i%> style=" width: 200px">
															<option value=""></option>
														<% if (dvList.size() > 0)
															{
																for (int j = 0; j < dvList.size(); j++)
																{
																	IDonvi donvi =  dvList.get(j);
																	
														 %>
																	<option value="<%=donvi.getId()%>"><%=donvi.getDonvi()%></option>
																 <% } } %>
										</select>
										
									</TD>
									<TD align="center" width="7%">
										<input type="text" value="" name="soluong" style="width: 50%; text-align: right;"   onchange=" Tinhtien ();submitform();" readonly > 
									<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
									</TD>
									
									<%-- <TD align="center" width="8%">
													<table>
													<tr>
									
										<!-- <input type="text" value="" name="dongia" style="width: 100%; text-align: right;" >  -->
										<% if (dmhBean.getNguonGocHH().equals("NN") ){ %>
											<TD align="center" width="8%">
												<input type="text" value="" name="dongia" style="width: 100%; text-align: right;"  >
											</td>
											<TD align="center" width="8%">
											    <input type="text" name="donGiaUSD"  style="width:100%;text-align:right" >
											</td>
										<%} else {%>
											<TD align="center" width="8%">
												<input type="text" value="" name="dongia"  style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)" >
											</td>
											<TD align="center" width="8%">
												<input type="text" value="" name="thuexuat" style="width: 100%; text-align: right;" >
											</td>
										<%} %> 
											<input type="hidden" value="" name="dongiaOld" style="width: 100%; text-align: right;" >
										
										</tr>
									</table>
									</TD> --%>
									
									
									<% if (dmhBean.getNguonGocHH().equals("NN") ){ %>
											<TD align="center" width="8%">
												<table>
												<tr>
													<TD align="center" width="8%">
													<input type="text" value="" name="dongia" style="width: 100%; text-align: right;" onchange=" Tinhtien ()"  >
												    </TD>
												    <TD align="center" width="8%">
												    <input type="text" name="donGiaUSD"  style="width:100%;text-align:right" readonly="readonly" >
													</TD>
												</tr>
												</table>
											</TD>
										<%} else {%>
											<TD align="center" width="8%">
												<input type="text"  value="" name="dongia"  style="width: 100%; text-align: right;" onkeyup="FormatNumber(this)" onchange=" Tinhtien ()"  >
											</TD>
											<TD align="center" width="8%">
												<input type="text"  value="" name="thuexuat" style="width: 100%; text-align: right;"  onchange=" Tinhtien ()" >
											</TD>
											<TD align="center" width="8%">
												<input type="text" value="" name="tienvat"  style="width: 100%; text-align: right;"  onchange="ChageTienVat('tienvat____<%=i%>')">
												<input type="hidden" value="" name="flag_tienvat"  id="flag_tienvat____<%=i%>"  style="width: 100%; text-align: right;" readonly="readonly" >
											</TD>
										<%} %> 
											<input type="hidden"  value="" name="dongiaOld" style="width: 100%; text-align: right;" >
	 
									
									
									
									
									<TD align="center" width="8%">
										<input type="text" value=""  name="thanhtien" style="width: 100%; text-align: right;" > 
									</TD>
									
									
									
									<TD align="center" width="8%">
										<input type="text" value="" style="width: 100%" name="nhomhang" readonly="readonly" > 
									</TD>
									<TD align="center" width="7%">
										<input type="text" value="" style="width: 100%" name="ngaynhan" readonly class="days" > 
									</TD>
	
									<% if ( dmhBean.getLoaihanghoa().trim().equals("0") )
									   { %>
										<TD align="center" width="8%" >
											
											<% if(dmhBean.getKhoId().trim().length() > 0) { %>	
											
											<% 
												for (int j = 0; j < khoList.size(); j++)
												{
													IKho kho = (Kho) khoList.get(j);
													
													if(kho.getId().equals(dmhBean.getKhoId())){
												%>
													<input type="text" value="<%= kho.getMakho() %>" readonly="readonly" >
													<input type="hidden" name="khonhan" value="<%= kho.getId() %>" >
												<% } } %>
												
											<% } else { %>
										
												<select style="width: 100%" name="khonhan">
													<option value=""></option>
													<% if (khoList.size() > 0)
														{
															for (int j = 0; j < khoList.size(); j++)
															{
																IKho kho = (Kho) khoList.get(j);
														%>
															<option value="<%=kho.getId()%>"><%=kho.getMakho()%></option>
														<% } }  %>
												</select>
											<% } %>
										</TD>
									<% } else { %>
										<TD align="center" width="8%" style="display: none" >							
											<input type="hidden" value=""  name="khonhan"  > 
										</TD>
									<% } %>
										<%-- <td align="center">
		           	 						<a href="" id="tenhd_<%= i %>" rel="subcontent<%= i %>">
			           	 							<img alt="Tên in trên PO" src="../images/vitriluu.png"></a>
			           	 							
			           	 					<DIV id="subcontent<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
						                             background-color: white; width: 520px; padding: 4px;">
						                    <table width="90%" align="left">
						                        <tr>
						                            <th width="100px">Tên in PO</th>
						                        </tr>
			                        			<tr>
						                            <td>
						                            	<input type="text" size="100px" name="tenhoadon" value="" onchange="replaces()"/>
						                            </td>
						                        </tr>
						                    </table>
						                     <div align="right">
						                     	<label style="color: red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>')" class="legendtitle" >Hoàn tất</a>
						                     </div>
						               		 </DIV>
		           	 					</td> --%>
									
								</tr>
							<% } %>
							
							
							<tr class="tbfooter">
								<td colspan="12">&nbsp;</td>
							</tr>
						</TABLE>
					</div>
					
					<% } %>
					
				</fieldset>
			</div>
		</div>
<script type="text/javascript">
	replaces();
	Tinhtien ();
	dropdowncontent.init("searchlink", "right-bottom", 500, "click");
	jQuery(function()
	{		
		$("#nccId").autocomplete("ErpNccMuaHangListGiay.jsp");
	});	
</script>
<script type="text/javascript">
	
	for(var i = 0; i < 40; i++) {
		dropdowncontent.init('tenhd_'+i, "left-top", 300, "click");
		
	}
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