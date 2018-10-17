<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.distributor.beans.hoadontaichinh.*" %>
<%@ page  import = "geso.traphaco.distributor.beans.donhang.ISanpham" %>
<%@ page  import = "geso.traphaco.distributor.beans.hoadontaichinh.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@page import="java.util.Enumeration"%>
<%@ page  import = "java.util.List" %>
<%@page import="java.text.NumberFormat"%>
<%@page import="java.text.DecimalFormat"%>

<% IHoadontaichinh lsxBean = (IHoadontaichinh)session.getAttribute("lsxBean"); %>
<% ResultSet khRs = lsxBean.getKhRs(); %>
<%NumberFormat formatter2 = new DecimalFormat("#,###,###.###"); %>
<% ResultSet ddhRs = lsxBean.getDondathangRs(); 
 NumberFormat formatter = new DecimalFormat("#,###,###"); 
   String[] ddh= lsxBean.getDonDatHang();
	Hashtable htbddh = new Hashtable<String, String>();
	int j=0;
	if(ddh!=null){
	while(j< ddh.length){
		System.out.println("Dhdhdd :"+ddh[j]);
		if(ddh[j]!=null){	
			htbddh.put(ddh[j],ddh[j]);
		}
		j=j+1;
	}
	}
	%>
<% Hashtable<String, Float> scheme_tongtien = lsxBean.getScheme_Tongtien(); %>
<% Hashtable<String, Float> scheme_chietkhau = lsxBean.getScheme_Chietkhau(); %>
<% List<ISanpham> scheme_sanpham = (List<ISanpham>)lsxBean.getScheme_SpList(); %>
<% Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong(); %>
<% String ck = lsxBean.getChietkhau();
	String[] tichluy_scheme = lsxBean.getTichLuy_Scheme();
	String[] tichluy_vat = lsxBean.getTichLuy_VAT();
	String[] tichluy_tongtien = lsxBean.getTichLuy_Tongtien();


	String[] spId = lsxBean.getSpId();
	String[] spMa = lsxBean.getSpMa(); 
	String[] spTen = lsxBean.getSpTen();
	String[] spDonvi = lsxBean.getSpDonvi();
	String[] spLoai = lsxBean.getSpLoai();
	String[] spSoluong = lsxBean.getSpSoluong();
	String[] spDongia = lsxBean.getSpDongia();
	String[] spChietkhau = lsxBean.getSpChietkhau();
	String[] spSCheme = lsxBean.getSpScheme();
	String[] spVat= lsxBean.getSpVat();
	String[] spThue= lsxBean.getSpThue();
	NumberFormat formater = new DecimalFormat("##,###,###");
	
	String[] dhCk_diengiai = lsxBean.getDhck_diengiai();
	String[] dhCk_giatri = lsxBean.getDhck_giatri();
	String[] dhCk_loai = lsxBean.getDhck_loai();
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	} else { %>

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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>


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

.mySCHME tr,td input
{
	color: red;
}

.mySCHME input
{
	color: red;
}

</style>

<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
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
<script type="text/javascript" src="../scripts/erp-SpListDonDatHang.js"></script>

<script language="javascript" type="text/javascript">

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
	
	function replaces()
	{/* 
		var spHansd = document.getElementsByName("spHansd");
		var spMa = document.getElementsByName("spMa");
		var spTen = document.getElementsByName("spTen");  
		var donvi = document.getElementsByName("donvi");
		var soluong = document.getElementsByName("soluong");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		
		var i;
		for(i = 0; i < spMa.length; i++)
		{
			if(spMa.item(i).value != "")
			{
				var sp = spMa.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));

				if(pos > 0)
				{
					spMa.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					spTen.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					
					donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					
					dongia.item(i).value = DinhDangTien(sp.substring(0, parseInt(sp.indexOf("]")))); 
				}
			}
			else
			{
				spMa.item(i).value = "";
				spTen.item(i).value = "";
				donvi.item(i).value = "";
				soluong.item(i).value = "";
				dongia.item(i).value = "";
				thanhtien.item(i).value = "";		
			}
		}	
		 */
		TinhTien();
		setTimeout(replaces, 300);
	}
	
	
	 function TinhTien()
	 {
		
			var thanhtien = document.getElementsByName("thanhtien");
			var chietkhau = document.getElementsByName("spChietkhau");
			var dongia = document.getElementsByName("spDongia");
			var soluong = document.getElementsByName("soluong");
			var ptVAT = document.getElementsByName("spVat");
			var sptienthue = document.getElementsByName("spTienthue");
			
			var tongtien = 0;
			var tongchietkhau = 0;
			var tongvat = 0;
			for(var i = 0; i < thanhtien.length; i++)
			{
				var don_gia = dongia.item(i).value.replace(/,/g, "");
				var so_luong = soluong.item(i).value.replace(/,/g, "");
				
				if( don_gia != '' && so_luong != '' )
					tongtien = parseFloat(tongtien) +  Math.round(parseFloat(don_gia) * parseFloat(so_luong));
				
				var chiet_khau = chietkhau.item(i).value.replace(/,/g, "");
				if(chiet_khau != '')
					tongchietkhau = parseFloat(tongchietkhau) +  parseFloat(chiet_khau);
				
				var _vat = ptVAT.item(i).value;
				if(_vat != '')
					tongvat = parseFloat(tongvat) +  Math.round( ( Math.round( parseFloat(so_luong) * parseFloat(don_gia) ) - parseFloat(chiet_khau) ) * parseFloat( _vat ) / 100 );
				 
			}
			var tienchuaVAT = tongtien;
			document.getElementById("tongtien").value = DinhDangTien(Math.round(tongtien));
			
			var tienchietkhau = tongchietkhau;
			document.getElementById("tienck").value = DinhDangTien(Math.round(tienchietkhau));
		
			 var tiensauCK = tienchuaVAT - tienchietkhau;
			document.getElementById("tiensauCK").value = DinhDangTien(Math.round(parseFloat(tiensauCK)) );

			var tongtiencoVAT = tiensauCK;
		
		
			document.getElementById("tienKM").value = DinhDangTien(Math.round(Math.abs( parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()) )));
			
			var SoTienSauCKKM = tongtiencoVAT - Math.abs( parseFloat(TongchietkhauKM()) + parseFloat(Tongtienkhuyenmai()) ); 
			document.getElementById("tiensauKM").value = DinhDangTien(Math.round(SoTienSauCKKM));
			
			document.getElementById("tienvat").value = DinhDangTien(Math.round(parseFloat(tongvat)- Math.abs( parseFloat(Tongtienkhuyenmai_Vat()) ))  );
			document.getElementById("tiensauvat").value = DinhDangTien(Math.round(parseFloat(tongvat)- Math.abs( parseFloat(Tongtienkhuyenmai_Vat())) + parseFloat(SoTienSauCKKM))); 
			
		}
		function Tongtienkhuyenmai()
		{
			var ttTrakm = document.getElementsByName("ttTrakm_chuaVat");
			var sum = 0;
			if(ttTrakm.length > 0)
			{
				for(h =0; h < ttTrakm.length; h++)
				{
					var thanh_tien = ttTrakm.item(h).value;
					while(thanh_tien.match(","))
					{
						thanh_tien = thanh_tien.replace(",","");
					}
					if(thanh_tien!= "")
						sum = parseFloat(sum) + parseFloat(thanh_tien);
				}
			}
			return sum;
		}
		function Tongtienkhuyenmai_Vat()
		{
			var ttTrakm = document.getElementsByName("ttTrakm_Vat");
			var sum = 0;
			if(ttTrakm.length > 0)
			{
				for(h =0; h < ttTrakm.length; h++)
				{
					var thanh_tien = ttTrakm.item(h).value;
					while(thanh_tien.match(","))
					{
						thanh_tien = thanh_tien.replace(",","");
					}
					if(thanh_tien!= "")
						sum = parseFloat(sum) + parseFloat(thanh_tien);
				}
			}
			return sum;
		}
		function TongchietkhauKM()
		{
			var ckTrakm = document.getElementsByName("ckTrakm");
			var sum = 0;
			if(ckTrakm.length > 0)
			{
				for(h =0; h < ckTrakm.length; h++)
				{
					var thanh_tien = ckTrakm.item(h).value;
					while(thanh_tien.match(","))
					{
						thanh_tien = thanh_tien.replace(",","");
					}
					if(thanh_tien != "")
						sum = parseFloat(sum) + parseFloat(thanh_tien);
				}
			}
			return sum;
		}
	
	 function saveform()
	 {	
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function duyetDCform()
	 {	
		 document.getElementById("btnSave2").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'duyetDIEUCHINH';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
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
		element.value = DinhDangTien(element.value);
		if(element.value== '' )
		{
			element.value= '';
		}
		
		//UPDATE
		Update_SoLuong();
		
	}	
	
	function Update_SoLuong()
	{
		var spTen = document.getElementsByName("spTen");
		var soluong = document.getElementsByName("soluong");
		var soluong2 = document.getElementsByName("soluong2");
		
		var soluongDAT = document.getElementsByName("soluongDAT");
		var soluongDANHAN = document.getElementsByName("daxuat");
		
		for(var i = 0; i < soluong.length; i++ )
		{
			var toida = parseFloat(soluongDAT.item(i).value.replace(/,/g,"")) - parseFloat(soluongDANHAN.item(i).value.replace(/,/g,""));
			
			if( parseFloat(soluong.item(i).value.replace(/,/g,"")) > toida )
			{
				soluong.item(i).value = DinhDangTien(toida);
				soluong2.item(i).value = soluong.item(i).value;
				
				alert('Số lượng xuất của sản phẩm (' +  spTen.item(i).value + ') không được phép vượt quá tổng đặt và đã xuất ( ' + toida + ' )');
			}
			
			soluong2.item(i).value = soluong.item(i).value;
		}
		
	}
	
	 function loadcontent()
	 {
		document.forms['hctmhForm'].action.value='reload';
	    document.forms["hctmhForm"].submit();
	 }
	 
	 function goBack()
	 {
	  	window.history.back();
	 }

</script>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
	$(document).ready(function()
	{
		$(".select2").select2();
	});
</script>

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>

</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../HoadontaichinhUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>
<input type="hidden" name="id" value='<%= lsxBean.getId() %>'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý bán hàng > Bán hàng OTC > Điều chỉnh hóa đơn > Điều chỉnh
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href=" javascript:goBack(); "  >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        
        <span id="btnSave2">
	        <A href="javascript:duyetDCform()" >
	        	<IMG src="../images/Chot.png" title="Duyệt điều chỉnh" alt="Duyệt điều chỉnh" border ="1px" style="border-style:outset"></A>
        </span>
    	
    </div>
  	
  	<div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror"  rows="1" readonly="readonly" style ="width:100%"><%= lsxBean.getMsg() %></textarea>
		         <% lsxBean.setMsg(""); %>
    	</fieldset>
  	</div>
  	
    <div align="left" style="width:100%; float:none; clear:left">
    <fieldset>
    	<legend class="legendtitle">Điều chỉnh hóa đơn tài chính </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="130px" class="plainlabel" valign="top">Ngày xuất hóa đơn </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  name="ngayxuat" value="<%= lsxBean.getNgayxuatHD() %>"/>
                    </TD>
                    
                    <TD width="150px" class="plainlabel" valign="top">Ngày ghi nhận công nợ </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text" readonly="readonly"  name="ngayghinhan" value="<%= lsxBean.getNgayghinhanCN() %>"/>
                    </TD>
                </TR>
         
                <TR>
                	<TD class="plainlabel">Ký hiệu hóa đơn</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"  name="kyhieuhoadon" value="<%= lsxBean.getKyhieuhoadon() %>" readonly="readonly" />
                    </TD>
                    
                    <TD class="plainlabel">Số hóa đơn</TD>
                	<TD class="plainlabel" valign="top">
                    	<input type="text"   name="sohoadon" value="<%= lsxBean.getSohoadon() %>" readonly="readonly" />
                    </TD>
                </TR>
                
                 <TR>
                	<TD class="plainlabel" >Khách hàng </TD>
                    <TD class="plainlabel"  width="250px" >
                    	<select name = "khId" class="select2" style="width: 200px" disabled="disabled" >
                    		<option value=""> </option>
                        	<%
                        		if(khRs != null)
                        		{
                        			try
                        			{
                        			while(khRs.next())
                        			{  
                        			if( khRs.getString("pk_seq").equals(lsxBean.getKhId())){ %>
                        				<option value="<%= khRs.getString("pk_seq") %>" selected="selected" ><%= khRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khRs.getString("pk_seq") %>" ><%= khRs.getString("ten") %></option>
                        		 <% } } khRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                    </TD> 
                    
                   <TD class="plainlabel" >Hình thức thanh toán </TD>
                    <TD class="plainlabel" >
                    	<select name="hinhthuctt" id="hinhthuctt" style="width: 200px;" disabled="disabled" >
							    <% 
							   String[] mangchuoi=new String[]{"TM/CK","Chuyển khoản","Tiền mặt"};
							   for(int k=0;k < mangchuoi.length;k ++ ){
								   
								   if(lsxBean.getHinhthucTT().equals(mangchuoi[k])) {
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
                	<TD class="plainlabel" >Ghi chú </TD>
                    <TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 500px" readonly="readonly" />
                    </TD>
                </TR>
                
                <TR>
                	<TD class="plainlabel" >Đơn đặt hàng </TD>
                    <TD class="plainlabel" colspan="2" >
                    	<fieldset  >
							<legend> Chọn đơn đặt hàng</legend>
							<table width="100%" cellpadding="4" cellspacing="0">
										<TR class="tbheader">
								             <TH align="center" width="30%">Số đơn hàng</TH>
								             <TH align="left" width="50%"> Ngày </TH>
								                        <%  %>
								              <TH align="center" width="20%"> Chọn </TH>
                   						</TR>                  								
                   							<%
                   								if(ddhRs!=null)
                   									while(ddhRs.next()){ %>
                   								<tr class= 'tblightrow'>
                   									<td>
                   										 <%=ddhRs.getString("pk_seq") %> 		
                   									</td>
                   									<td>                   										
                   										 <%=ddhRs.getString("NgayDonHang") %> 	
                   									</td>
                   										<% 
                   										 if(htbddh.containsValue(ddhRs.getString("pk_seq").trim())){%>
                       											<TD align="center"><input type ="checkbox" checked="checked" name ="ddhid" value ="<%=ddhRs.getString("pk_seq") %>" disabled="disabled" ></TD>
                       											<%}else{ %>
                       											<TD align="center"><input type ="checkbox" id="ddhid" name ="ddhid" value ="<%=ddhRs.getString("pk_seq") %>" disabled="disabled" ></TD>
                       											<%}
                   											%>
                   										</tr>
                   									<%} %>                   								                  							
                   			</table>
                   		</fieldset>
                    </TD> 
                    
                    <TD class="plainlabel" colspan="2" valign="bottom" >
                		<table class="plainlabel" width="100%"  style="padding-top:0 ; margin-top:0">                 								                   								                   								
                  			    <tr style="display: none;">
                  					<td width="30%" >Tiền CK</td>                 								     
                  					<td width="70%">
                  					   
                  						<input type="text" style="text-align:right" id="tienck" name="tienck" value="<%=formater.format(lsxBean.getTienCK()) %>" readonly="readonly" >
                  					</td>
                  				</tr>
                  				<tr style="display: none;">
                  					<td>Tiền sau CK</td>
                  					<td>
                  						 <input type="text" style="text-align:right"  id="tiensauCK" name="tiensauCK" value="<%=formater.format(lsxBean.getTienSauCK())%>" readonly="readonly" >
                  					</td>
                  				</tr> 
                  				<tr>
                  					<td>Tổng tiền </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tongtien" name="tongtien" value="" readonly="readonly" >
                  					</td>
                  				</tr>
                  				<tr>
                  					<td>Tiền Chiết khấu </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tienKM" name="tienKM" value="<%=formater.format(lsxBean.getTienKM())%>" readonly="readonly" >
                  					</td>
                  				</tr>
                  				 <tr style="display: none;">
                  					<td>Tiền sau KM </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tiensauKM" name="tiensauKM" value="<%=formater.format(lsxBean.getTienSauCK()+ lsxBean.getTienKM())%>" readonly="readonly" >
                  					</td>
                  				</tr> 
                  				
                  				<tr>
                  					<td>Tiền Vat </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tienvat" name="tienvat" value="<%=formater.format(lsxBean.getTienVAT())%>" readonly="readonly" >
                  					</td>
                  				</tr>
                  				
                  				<tr>
                  					<td>Tiền thực thu </td>
                  					<td>
                  						<input type="text" style="text-align:right" id="tiensauvat" name="tiensauvat" value="<%=formater.format(lsxBean.getTienSauCK()+ lsxBean.getTienKM())%>" readonly="readonly" >
                  					</td>
                  				</tr>
                  		</table>
                  	</TD>  
                </TR>
                
            </TABLE>
			<hr />
			
			 <table cellpadding="0px" cellspacing="1px" width="100%">
				<tr class="tbheader">
					<th align="center" width="15%" >Mã sản phẩm</th>
					<th align="center" width="20%" >Tên sản phẩm</th>
					<th align="center" width="10%" >Đơn vị</th>
					<th align="center" width="8%" >Số lượng </th>
					<th align="center" width="10%" >Đơn giá</th>
					<th align="center" width="8%" >VAT</th>
					<th align="center" width="10%" >Chiết khấu</th>
					<th align="center" width="10%" >Tiền thuế</th>
					<th align="center" width="10%" >Thành tiền</th>
					<th align="center" width="10%" >CTKM</th>
					
				</tr>
				
				<%
					int count = 0;
					if(spMa != null)
					{
						for(int i = 0; i < spMa.length; i++)
						{%>
					
						<tr>
							<td>
								<input type="hidden" name="spId" value="<%= spId[i] %>" style="width: 100%"   > 
								<%-- <input type="hidden" name="spLoai" value="<%= spLoai[i] %>" style="width: 100%"   >  --%>
								<input type="text" name="spMa" value="<%= spMa[i] %>" style="width: 100%"  readonly="readonly"  > 
							</td>
							<td> <input type="text" name="spTen" value="<%= spTen[i] %>" style="width: 100%" readonly="readonly"> </td>
							<td>
								<input type="text" name="donvi" value="<%= spDonvi[i] %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="soluong" value="<%= formater.format(Double.parseDouble(spSoluong[i])) %>" style="width: 100%; text-align: right;" >							
							</td>
							<td>
								<input type="text" name="spDongia" value="<%= formatter2.format(Double.parseDouble(spDongia[i])) %>" style="width: 100%; text-align: right;" >							
							</td>
							<td>
								<input type="text" name="spVat" value="<%= formater.format(Double.parseDouble(spVat[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="spChietkhau" value="<%= formater.format(Double.parseDouble(spChietkhau[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="spTienthue" value="<%= formater.format(Double.parseDouble(spThue[i])) %>" style="width: 100%; text-align: right;" readonly="readonly">							
							</td>
							<td>
								<input type="text" name="thanhtien" value="<%= formater.format(Double.parseDouble(spSoluong[i])*Double.parseDouble(spDongia[i])- Double.parseDouble(spChietkhau[i])+ Double.parseDouble(spThue[i]) ) %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " >							
							</td>
							<td>
								<input type="text" name="scheme"  value="<%= spSCheme[i] %>" style="width: 100%; text-align: right;" onkeypress="return keypress(event); " >							
							</td>
							
						</tr>	
							
					<% count ++; } } %>
				<%if(scheme_tongtien.size() > 0)
								{
									Enumeration<String> keys = scheme_tongtien.keys();
									while(keys.hasMoreElements())
									{
										String key = keys.nextElement(); %>
										<TR class= 'tblightrow'>
											<TD align="center" ><input type="text" size="18" readonly></TD>
											<TD align="left" ><input type="text" readonly style="width:100%"></TD>
											<TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" style="width:100%" readonly></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input type="text" value="" readonly style="width:100%"></TD>
										    <TD align = "center" ><input name="ttTrakm" type="text" value="<%= "-" + Float.toString(scheme_tongtien.get(key)) %>" readonly style="width:100%;text-align:right"></TD>
										    <TD align = "center" ><input name="trakmSpScheme" type="text" value="<%= key %>" style="width:100%" readonly></TD>
											<TD align = "center" style="display: none;" >
										    	<input name="khoNVBH1" type="checkbox"  value="0" onclick="return false;" >
										    	<input name="khoNVBH" type="hidden"  value="0"  >
										    </TD>
										</TR>
								<% count++; }}%>						
								
								<% if(tichluy_scheme != null) 
								{ 
									for(int i = 0; i < tichluy_scheme.length; i++)
									{
										double _vat = Double.parseDouble(tichluy_vat[i].replaceAll(",", ""));
										double _chietkhau = Double.parseDouble(tichluy_tongtien[i].replaceAll(",", ""));
										
										double _tienthue = _vat * _chietkhau / 100;
										double _thanhtien = _chietkhau + _tienthue;
										
										%>
										
										<TR class= 'tblightrow'>
										    <TD align = "right" colspan="5"  ><input type="text" value="<%= tichluy_scheme[i] %>" style="width:100%; text-align: right;" disabled="disabled"></TD>
										    <TD align = "center" ><input type="text" value="<%= tichluy_vat[i] %>"  style="width:100%; text-align: right;" disabled="disabled" ></TD>
										    <TD align = "center" ><input  name = "ttTrakm_chuaVat" type="text" value="<%= tichluy_tongtien[i] %>"  style="width:100%; text-align: right;" disabled="disabled"  ></TD>
										    <TD align = "center" ><input name = "ttTrakm_Vat" type="text" value="<%= formatter.format(_tienthue) %>"  style="width:100%; text-align: right;" disabled="disabled" ></TD>
										    <TD align = "center" ><input name="ttTrakm" type="text" value="<%= formatter.format(_thanhtien) %>"  style="width:100%;text-align:right" disabled="disabled" ></TD>
										    <TD align = "center" ><input name="trakmSpScheme" type="text" value=" " style="width:100%" readonly></TD>
											<TD align = "center" style="display: none;" >
										    	<input name="khoNVBH1" type="checkbox"  value="0" onclick="return false;" >
										    	<input name="khoNVBH" type="hidden"  value="0"  >
										    </TD>
										</TR>
										
									<% } } %>
			</table> 
				
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">
	replaces();
</script>

<%
	try
	{
		lsxBean.DBclose(); 
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>