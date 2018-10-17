<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.imp.Lenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.ILenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoiquycach"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%@ page  import = "java.util.Enumeration" %>

<% IErpDoiquycach lsxBean = (IErpDoiquycach)session.getAttribute("ckBean"); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet khonhanRs = lsxBean.getKhoNhapRs(); %>

<% 
	List<IYeucau> spList = (List<IYeucau>)lsxBean.getSpList(); 
	Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	Hashtable<String, String> sanpham_soluongDAXUAT = lsxBean.getSanpham_SoluongDAXUAT_THEODN();
	
	List<IYeucau> spListNHAN = (List<IYeucau>)lsxBean.getSpNhanList();
	Hashtable<String, String> sanpham_soluongNHAN = lsxBean.getSanpham_SoluongNHAN();
	
	NumberFormat formater = new DecimalFormat("##,###,###.###");
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
<script type="text/javascript" src="../scripts/erp_splist_doiquycach.js"></script>
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
	{
		var idsp = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");  
		var donvi = document.getElementsByName("donvi");
		var dongia = document.getElementsByName("dongia");
		var thanhtien = document.getElementsByName("thanhtien");
		var slyc = document.getElementsByName("soluongyeucau");
		
		var tongSL = 0;
		var tongGT = 0;
		var i;
		for(i = 0; i < masp.length; i++)
		{
			if(masp.item(i).value != "")
			{
				var sp = masp.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));

				if(pos > 0)
				{
					masp.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					tensp.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					idsp.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
			 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					donvi.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					dongia.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
					slyc.item(i).value = "0";
					//submitform();
				}
				
				if( slyc.item(i).value != '' )
				{
				    tongSL = tongSL + parseFloat(slyc.item(i).value.replace(",",""));
				    
				    if( dongia.item(i).value != '' )
				    {
				    	var tt = parseFloat(slyc.item(i).value.replace(",","")) * parseFloat(dongia.item(i).value.replace(",",""));
				    	
				    	thanhtien.item(i).value = tt
				    	tongGT = parseFloat( tongGT ) +  parseFloat( tt );
				    }
				}
			}
			else
			{
				idsp.item(i).value = "";
				masp.item(i).value = "";
				tensp.item(i).value = "";
			    slyc.item(i).value = "";
			    dongia.item(i).value = "";
			    thanhtien.item(i).value = "";
				donvi.item(i).value = "";
			}
		}	
		
		tongSL = tongSL.toFixed(3);
		tongGT = tongGT.toFixed(0);
		
		document.getElementById("tongSLYC").value = DinhDangDonGia(tongSL);
		document.getElementById("tongTTYC").value = DinhDangDonGia(tongGT);
		
		//Chi phí
		var totalChiphi = 0;
		var sotaikhoanCP = document.getElementsByName("sotaikhoanCP");
		var chiphi = document.getElementsByName("chiphi");
		for(i = 0; i < sotaikhoanCP.length; i++)
		{
			if(sotaikhoanCP.item(i).value != "" && chiphi.item(i).value )
			{
				totalChiphi = parseFloat( totalChiphi ) +  parseFloat( chiphi.item(i).value.replace(",","") );
			}
		}
		
		var tonggiatriTruocQD = parseFloat( totalChiphi ) +  parseFloat( tongGT );
		
		document.getElementById("tongchiphiTruocQD").value = DinhDangDonGia(totalChiphi);
		document.getElementById("tonggiatriTruocQD").value = DinhDangDonGia(tonggiatriTruocQD);
		
		//Bên nhận
		var idspNHAN = document.getElementsByName("idspNHAN");
		var maspNHAN = document.getElementsByName("maspNHAN");
		var tenspNHAN = document.getElementsByName("tenspNHAN");  
		var donviNHAN = document.getElementsByName("donviNHAN");
		var dongiaNHAN = document.getElementsByName("dongiaNHAN");
		var dongiaNHAN_saupb = document.getElementsByName("dongiaNHAN_saupb");
		var thanhtienNHAN = document.getElementsByName("thanhtienNHAN");
		var soluongNHAN = document.getElementsByName("soluongNHAN");
		
		for(i = 0; i < maspNHAN.length; i++)
		{
			if(maspNHAN.item(i).value != "")
			{
				var sp = maspNHAN.item(i).value;
				var pos = parseInt(sp.indexOf(" - "));

				if(pos > 0)
				{
					maspNHAN.item(i).value = sp.substring(0, pos);
					sp = sp.substr(parseInt(sp.indexOf(" - ")) + 3);
					
					tenspNHAN.item(i).value = sp.substring(0, parseInt(sp.indexOf(" [")));
					
					sp = sp.substr(parseInt(sp.indexOf(" [")) + 2);
					idspNHAN.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
			 
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					donviNHAN.item(i).value = sp.substring(0, parseInt(sp.indexOf("] [")));
					
					sp = sp.substr(parseInt(sp.indexOf("] [")) + 3);
					dongiaNHAN.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
				}
				
				if( dongiaNHAN.item(i).value != '' )
				{
					var slgNHAN = soluongNHAN.item(i).value.replace(",","");
					var tongtienNHAN_Truoc = 0;
					
					for( j = 0; j < i; j++ )
					{
						if( dongiaNHAN.item(j).value != '' && soluongNHAN.item(j).value != '' )
						{
							tongtienNHAN_Truoc = parseFloat(tongtienNHAN_Truoc) +  parseFloat(dongiaNHAN.item(j).value.replace(",","")) * parseFloat(soluongNHAN.item(j).value.replace(",",""));
						}
					}

					var dgn_sauPB =  ( parseFloat(tonggiatriTruocQD) - parseFloat(tongtienNHAN_Truoc) ) / parseFloat( slgNHAN );
					var tnt_sauPB = parseFloat( dgn_sauPB ) * parseFloat( slgNHAN );
					
					dongiaNHAN_saupb.item(i).value = DinhDangDonGia( dgn_sauPB.toFixed(3) );
					thanhtienNHAN.item(i).value = DinhDangDonGia(tnt_sauPB);
				}
			}
			else
			{
				idspNHAN.item(i).value = "";
				maspNHAN.item(i).value = "";
				tenspNHAN.item(i).value = "";
			    soluongNHAN.item(i).value = "";
				donviNHAN.item(i).value = "";
				dongiaNHAN.item(i).value = "";
				dongiaNHAN_saupb.item(i).value = "";
				thanhtienNHAN.item(i).value = "";
			}
		} 
		
		//Display 
		for( i = 0; i < 5; i++ )
		{
			var temID = "spNhan_" + i;
			var soluongNHAN = document.getElementsByName(temID + "_spSOLUONG");
			
			var totalSL_NHAN = 0;
			for( j = 0; j < soluongNHAN.length; j++ )
			{
				if( soluongNHAN.item(j).value != '' )
				{
					totalSL_NHAN = parseFloat(totalSL_NHAN) +  parseFloat(soluongNHAN.item(j).value.replace(",",""));
				}
			}
			
			document.getElementsByName("soluongNHAN").item(i).value = totalSL_NHAN;
			document.getElementsByName("soluongNHAN2").item(i).value = totalSL_NHAN;
		}
		
		setTimeout(replaces, 500);
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
		 document.getElementById("btnSave").innerHTML = "<a href='#'><img src='../images/waiting.gif'  title='cho luu..' border='1' longdesc='cho luu..' style='border-style:outset'> Processing...</a>";
	 	 document.forms['hctmhForm'].action.value = 'save';
	     document.forms['hctmhForm'].submit();
	 }
	 
	 function submitform()
	 { 
		 document.forms['hctmhForm'].action.value='submit';
	     document.forms["hctmhForm"].submit();
	 }
	 
	 function changeKHO()
	 {
		document.forms["hctmhForm"].action.value = "changeKHO";
	    document.forms["hctmhForm"].submit();
	 }
	 
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $(".select").select2();  });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpDoiquycachUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%=lsxBean.getId()%>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Chức năng > Đổi quy cách > <%= ( lsxBean.getId().length() >0 ? "Cập nhật" : "Tạo mới" ) %> 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpDoiquycachSvl?userId=<%= userId %>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
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
    	<legend class="legendtitle">Đổi quy cách </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD style="width: 140px;" class="plainlabel" valign="top">Ngày chứng từ </TD>
                    <TD style="width: 240px;" class="plainlabel" valign="top"  >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    <TD style="width: 120px;" class="plainlabel" valign="top">Diễn giải </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>" style="width: 400px;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Kho đổi </TD>
                    <TD class="plainlabel"   >
                    	<select name = "khoxuatId" style="width: 200px;"  onchange="changeKHO();" class="select" >
                    		<option value=""> </option>
                        	<%
                        		if(khoxuatRs != null)
                        		{
                        			try
                        			{
                        			while(khoxuatRs.next())
                        			{  
                        			if( khoxuatRs.getString("pk_seq").equals(lsxBean.getKhoXuatId())){ %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" selected="selected" ><%= khoxuatRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khoxuatRs.getString("pk_seq") %>" ><%= khoxuatRs.getString("ten") %></option>
                        		 <% } } khoxuatRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                     </TD>
                     
                     <TD class="plainlabel" >Kho nhận </TD>
                     <TD class="plainlabel"   >
                    	<select name = "khonhanId" style="width: 200px;"  onchange="submitform();" class="select" >
                    		<option value=""> </option>
                        	<%
                        		if(khonhanRs != null)
                        		{
                        			try
                        			{
                        			while(khonhanRs.next())
                        			{  
                        			if( khonhanRs.getString("pk_seq").equals(lsxBean.getKhoNhapId())){ %>
                        				<option value="<%= khonhanRs.getString("pk_seq") %>" selected="selected" ><%= khonhanRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khonhanRs.getString("pk_seq") %>" ><%= khonhanRs.getString("ten") %></option>
                        		 <% } } khonhanRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                    	</select>
                      </TD>
                </TR>
                
                <tr>
                	<td colspan="4" class="plainlabel" >
                	
                		<fieldset><legend>Sản phẩm đổi quy cách</legend>
                		<table cellpadding="0px" cellspacing="1px" width="100%">
							<tr class="tbheader">
								<th align="center" width="2%">STT</th>
								<th align="center" width="15%">Mã sản phẩm</th>
								<th align="center" width="38%">Tên sản phẩm</th>
								<th align="center" width="10%">Đơn vị</th>
								<th align="center" width="10%">Đơn giá</th>
								<th align="center" width="15%">Số lượng </th>
								<th align="center" width="10%">Tổng giá trị</th>	
							</tr>
							
							<% 
							int count = 0; 
							if(spList.size() > 0) 
							{ 
								for(int i = 0; i < spList.size(); i++)
								{ 
									IYeucau yeucau = spList.get(i);
									double soluongyeucau = 0;
									if( yeucau.getSoluongYC().trim().length() > 0 )
										soluongyeucau=Double.parseDouble(yeucau.getSoluongYC().replaceAll(",", ""));
									%>
									
									<tr>
										<td style="font-size: 9pt" align="center"><%= count+1 %></td>
										<td>
											<input type="hidden" name="idsp"  value="<%= yeucau.getId() %>" > 
											<input type="text" name="masp"  value="<%= yeucau.getMa() %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
										</td>
										<td> <input type="text" name="tensp" value="<%= yeucau.getTen() %>" style="width: 100%" readonly="readonly"> </td>
									 	<td> <input type="text" name="donvi" value="<%= yeucau.getDonViTinh() %>" style="width: 100%; text-align: right;" readonly="readonly"> </td>
										<td> <input type="text" name="dongia"  value="<%= yeucau.getDongia() %>" style="width: 100%; text-align: right; "  ></td>
										
										<td > 
											<input type="text" name="soluongyeucau" value="<%= formater.format(soluongyeucau)%>"   style="width: 60%; text-align: right;  " onkeypress="return keypress(event);" onchange="submitform();" > 
											<% if( soluongyeucau >= 0 && lsxBean.getKhoXuatId().trim().length() > 0  ) { %>
												<a href="" id="scheme_<%= yeucau.getMa() + "__ "  %>" rel="subcontent_<%= yeucau.getMa() + "__ " %>">
						           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
						           	 		
					           	 		 		<DIV id="subcontent_<%= yeucau.getMa() + "__ " %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 945px; max-height:200px; overflow:auto; padding: 4px;">
								                    <table width="100%" align="center">
								                    	<tr>
								                    		<td colspan="3" ><b>Tổng xuất</b></td>
								                    		<td colspan="11" > <input type="text" name="soluong2" value="<%= soluongyeucau %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
								                    	</tr>
								                        <tr>
								                            <th width="80px" style="font-size: 10px">Số lượng</th>
								                            <th width="80px" style="font-size: 10px">Tồn kho</th>
								                         	<th width="90px" style="font-size: 10px">Số lô</th>
								                            <th width="70px" style="font-size: 10px">Ngày HH</th>
								                            <th width="70px" style="font-size: 10px">Ngày NK</th>
								                            <th width="25px" style="font-size: 10px">Mẻ</th>
								                            <th width="25px" style="font-size: 10px">Thùng</th>
								                            <th width="100px" style="font-size: 10px">Vị trí</th>
								                            <th width="80px" style="font-size: 10px">Mã phiếu</th>
								                            <th width="80px" style="font-size: 10px">Phiếu ĐT</th>
								                            <th width="50px" style="font-size: 10px">Phiếu EO</th>
								                            <th width="50px" style="font-size: 10px">Marq</th>
								                            <th width="25px" style="font-size: 10px">Hàm lượng</th>
								                            <th width="25px" style="font-size: 10px">Hàm ẩm</th>
								                        </tr>
						                            	<%
						                            		ResultSet spRs = lsxBean.getSoloTheoSp(yeucau.getMa(), yeucau.getDonViTinh(), yeucau.getSoluongYC() );
							                        		if(spRs != null)
							                        		{
							                        			String vitrinhanSELECTED = "";
							                        			while(spRs.next())
							                        			{
							                        				String tudeXUAT = "";
							                        				if(spRs.getString("tuDEXUAT").trim().length() > 0)
							                        					tudeXUAT = formater.format(spRs.getDouble("tuDEXUAT"));
							                        				
							                        				String temID = yeucau.getMa() + "__ ";
							                        				String key = yeucau.getMa() + "__ " + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN") + "__" + spRs.getString("NGAYNHAPKHO")  
							                        													+ "__" + spRs.getString("MAME") + "__" + spRs.getString("MATHUNG") + "__" + spRs.getString("VITRI")
							                        													+ "__" + spRs.getString("MAPHIEU") + "__" + spRs.getString("PHIEUDT") + "__" + spRs.getString("PHIEUEO")
							                        													+ "__" + spRs.getString("MARQ") + "__" + spRs.getString("HAMLUONG") + "__" + spRs.getString("HAMAM");
							                        				//System.out.println("::: KEY:: " + key );
							                        			%>
							                        			
							                        			<tr>
							                        				<td>
							                        				<% if(sanpham_soluong.get( key ) != null ) { %>
							                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater.format(Double.parseDouble( sanpham_soluong.get( key ))) %>"  >
							                        				<% } else { %>
							                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= tudeXUAT %>"  >
							                        				<% } %>
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTONKHO" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spSOLO" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spNGAYHETHAN" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spNGAYNHAPKHO" value="<%= spRs.getString("NGAYNHAPKHO") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAME" value="<%= spRs.getString("MAME") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMATHUNG" value="<%= spRs.getString("MATHUNG") %>" readonly="readonly">
							                        				</td>
							                        				<td>			                        					
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spVitri" value="<%= spRs.getString("VITRI") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAPHIEU" value="<%= spRs.getString("MAPHIEU") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieudt" value="<%= spRs.getString("PHIEUDT") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieueo" value="<%= spRs.getString("PHIEUEO") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMarq" value="<%= spRs.getString("MARQ") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamluong" value="<%= spRs.getString("HAMLUONG") %>" readonly="readonly">
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamam" value="<%= spRs.getString("HAMAM") %>" readonly="readonly">
							                        				</td>
							                        				
							                        			</tr>
							                        			
							                        		 <% } } %>	 
								                     </table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= yeucau.getMa() + "__ " %>')" > Đóng lại </a>
								                     </div>
									            </DIV> 
									            
									            <script type="text/javascript">
									            	dropdowncontent.init('scheme_<%= yeucau.getMa() + "__ "  %>', "left-top", 300, "click");
									            </script>
									         <% } else { %>
									         	<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
									         <% } %>
										</td>
										
										<td> <input type="text" name="thanhtien"  value="<%= yeucau.getghichu_CK() %>" style="width: 100%;text-align: right; " readonly="readonly" ></td>
									</tr>
									
								<% count++; } } %>
								
							<%  
						 	int bien = count;
							while(count <= bien ) { %>
							
								<tr>
									<td style="font-size: 9pt" align="center"><%= count+1 %></td>
									<td>
										<input type="hidden" name="idsp"  value="" > 
										<input type="text" name="masp"  value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
									</td>
									<td> <input type="text" name="tensp" value="" style="width: 100%" readonly="readonly"> </td>
								 	<td> <input type="text" name="donvi" value="" style="width: 100%; text-align: right;" readonly="readonly"> </td>
									<td> <input type="text" name="dongia"  value="" style="width: 100%; text-align: right; "  ></td>
									
									<td > 
										<input type="text" name="soluongyeucau" value=""   style="width: 60%; text-align: right;" onkeypress="return keypress(event);" onchange="submitform();" > 
								        <a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
									</td>
									
									<td> <input type="text" name="thanhtien"  value="" style="width: 100%; text-align: right; " readonly="readonly"  ></td>
								</tr>
							
							<% count ++; } %>
						
							<tr >
								<td align="right" colspan="4"><b> Tổng số lượng đổi</b></td>
								<td >
									<input type="text"  name="tongSLYC" id="tongSLYC" style="text-align: right; width: 60%;" value="<%= lsxBean.getTongSLYC() %>" readonly="readonly"/>
								</td>
					
								<td ><b>Thành tiền</b></td>	
								<td >
									<input type="text"  name="tongTTYC" id="tongTTYC" style="text-align: right; width: 100%;" value="<%= lsxBean.getTongSLYC() %>" readonly="readonly"/>
								</td>
							</tr>
								
						</table>
                		</fieldset>
                		
                	</td>
                </tr>
                
                <TR >
                    <TD class="plainlabel" valign="top">Tổng chi phí </TD>
                    <TD class="plainlabel" colspan="3">
                    
                    	<input type="text" id='tongchiphiTruocQD' value="<%= lsxBean.getGhichu() %>" style="text-align: right; width: 180px;" readonly="readonly" />
                    	
                    	<a href="" id="schemeChiphi" rel="subcontentChiphi">
	           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
	           	 		
          	 		 	<DIV id="subcontentChiphi" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
		                             background-color: white; width: 500px; max-height:200px; overflow:auto; padding: 4px;">
		                    <table width="100%" align="center">
		                    	<tr class="tbheader">
									<th align="center" width="60%">Tài khoản</th>
									<th align="center" width="40%">Chi phí</th>
								</tr>
								
								<%
									String[] sotaikhoan = lsxBean.getSotaikhoanChiphi();
									String[] chiphi = lsxBean.getChiphi();
									
									int counSTK = 0;
									if( sotaikhoan != null )
									{
										for( int i = 0; i < sotaikhoan.length; i++ ) { counSTK ++; %>
											<tr>
												<td>
													<input type="text" name="sotaikhoanCP"  value="<%= sotaikhoan[i] %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'laytaikhoan',event)" AUTOCOMPLETE="off" > 
												</td>
												<td> <input type="text" name="chiphi"  value="<%= chiphi[i] %>" style="width: 100%; text-align: right; "  ></td>
											</tr>
										<% }
									}
								%>
								<% for( int i = counSTK; i < 8; i++ ) { %>
								<tr>
									<td>
										<input type="text" name="sotaikhoanCP"  value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'laytaikhoan',event)" AUTOCOMPLETE="off" > 
									</td>
									<td> <input type="text" name="chiphi"  value="" style="width: 100%; text-align: right; "  ></td>
								</tr>
								<% } %>
		                    </table>
		                    <div align="right">
		                     	<label style="color: red" ></label>
		                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		                     	<a href="javascript:dropdowncontent.hidediv('subcontentChiphi')" > Đóng lại </a>
		                     </div>
		                </DIV>
		                
		                <script type="text/javascript">
			            	dropdowncontent.init('schemeChiphi', "right-top", 300, "click");
			            </script>
			               
                    </TD>
                </tr>
                <tr>
                    <TD class="plainlabel" valign="top" colspan="4" >Tổng giá trị trước đổi QC &nbsp;
                    	<input type="text"  name="tonggiatriTruocQD" id='tonggiatriTruocQD' value="<%= lsxBean.getGhichu() %>" style="text-align: right;" readonly="readonly" />
                    </TD>
                </TR>
                
                <tr>
                	<td colspan="4" class="plainlabel" >
                		
                		<fieldset><legend>Sản phẩm nhận đổi quy cách</legend>
                		<table cellpadding="0px" cellspacing="1px" width="100%">
							<tr class="tbheader">
								<th align="center" width="2%">STT</th>
								<th align="center" width="13%">Mã sản phẩm</th>
								<th align="center" width="35%">Tên sản phẩm</th>
								<th align="center" width="10%"> Đơn vị</th>
								<th align="center" width="10%">Đơn giá</th>
								<th align="center" width="10%">Số lượng </th>
								<th align="center" width="10%">Đơn giá sau PB</th>
								<th align="center" width="10%">Tổng giá trị</th>	
							</tr>
							
							<% 
							count = 0; 
							if(spListNHAN.size() > 0) 
							{ 
								for(int i = 0; i < spListNHAN.size(); i++)
								{ 
									IYeucau yeucau = spListNHAN.get(i);
									double soluongyeucau = 0;
									if( yeucau.getSoluongYC().trim().length() > 0 )
										soluongyeucau = Double.parseDouble(yeucau.getSoluongYC().replaceAll(",", ""));
									%>
									
									<tr>
										<td style="font-size: 9pt" align="center"><%= count+1 %></td>
										<td>
											<input type="hidden" name="idspNHAN"  value="<%= yeucau.getId() %>" > 
											<input type="text" name="maspNHAN"  value="<%= yeucau.getMa() %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'loadsanphamNHAN',event)" AUTOCOMPLETE="off" > 
										</td>
										<td> <input type="text" name="tenspNHAN" value="<%= yeucau.getTen() %>" style="width: 100%" readonly="readonly"> </td>
									 	<td> <input type="text" name="donviNHAN" value="<%= yeucau.getDonViTinh() %>" style="width: 100%; text-align: right;" readonly="readonly"> </td>
										
										<td> <input type="text" name="dongiaNHAN"  value="<%= yeucau.getDongia() %>" style="width: 100%; text-align: right; "  ></td>
										
										<td > 
											<input type="text" name="soluongNHAN" value="<%= formater.format(soluongyeucau)%>"   style="width: 60%; text-align: right;  " onkeypress="return keypress(event);" readonly="readonly" > 
												<a href="" id="schemeNHAN_<%= i %>" rel="subcontentNHAN_<%= i %>">
						           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
						           	 		
					           	 		 		<DIV id="subcontentNHAN_<%= i %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
								                             background-color: white; width: 915px; max-height:200px; overflow:auto; padding: 4px;">
								                    <table width="95%" align="center">
								                    	<tr>
								                    		<td colspan="3" ><b>Tổng nhận</b></td>
								                    		<td colspan="11" > <input type="text" name="soluongNHAN2" value="<%= soluongyeucau %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
								                    	</tr>
								                        <tr>
								                            <th width="80px" style="font-size: 10px">Số lượng</th>

								                         	<th width="80px" style="font-size: 10px">Số lô</th>
								                            <th width="70px" style="font-size: 10px">Ngày HH</th>

								                            <th width="25px" style="font-size: 10px">Mẻ</th>
								                            <th width="25px" style="font-size: 10px">Thùng</th>
								                            <th width="170px" style="font-size: 10px">Vị trí</th>
								                            <th width="80px" style="font-size: 10px">Mã phiếu</th>
								                            <th width="80px" style="font-size: 10px">Phiếu ĐT</th>
								                            <th width="50px" style="font-size: 10px">Phiếu EO</th>
								                            <th width="50px" style="font-size: 10px">Marq</th>
								                            <th width="60px" style="font-size: 10px">Hàm lượng</th>
								                            <th width="50px" style="font-size: 10px">Hàm ẩm</th>
								                        </tr>
						                            	<%
						                            		int countSP_Nhan = 0;
					                            			String temID = "spNhan_" + i;
					                            			
							                        		if( sanpham_soluongNHAN.size() > 0 )
							                        		{
							                        			Enumeration<String> keys = sanpham_soluongNHAN.keys();
							                        			while( keys.hasMoreElements() )
							                        			{
							                        				String key = keys.nextElement();
							                        				String soluongNHAN = sanpham_soluongNHAN.get(key);
							                        				System.out.println("::: KEY NHAN:: " + key );
							                        				
							                        				if( key.startsWith( i + "__" ) ) {
							                        				
							                        				String[] spNHAN = Utility.mySplit(key, "__");
							                        				String slgNHAN = sanpham_soluongNHAN.get(key);
							                        			%>
							                        			
							                        			<tr>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= slgNHAN %>"  >
							                        				</td>

							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spSOLO" value="<%= spNHAN[2] %>" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spNGAYHETHAN" value="<%= spNHAN[3] %>" class="days" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAME" value="<%= spNHAN[4] %>" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMATHUNG" value="<%= spNHAN[5] %>" >
							                        				</td>
							                        				<td>			                        					
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spVitri" value="<%= spNHAN[6] %>" onkeyup="ajax_showOptions(this,'loadvitriNHAN',event)" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAPHIEU" value="<%= spNHAN[7] %>" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieudt" value="<%= spNHAN[8] %>" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieueo" value="<%= spNHAN[9] %>" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMarq" value="<%= spNHAN[10] %>" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamluong" value="<%= spNHAN[11] %>" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamam" value="<%= spNHAN[12] %>" >
							                        				</td>
							                        				
							                        			</tr>
							                        			
							                        		 <% countSP_Nhan ++; } } } %>	 
							                        		 
							                        		 <% while( countSP_Nhan <= 5 ) { %>
							                        		 	
							                        		 	<tr>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value=""  >
							                        				</td>

							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spSOLO" value="" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spNGAYHETHAN" value="" class="days" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAME" value="" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMATHUNG" value="" >
							                        				</td>
							                        				<td>			                        					
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spVitri" value="" onkeyup="ajax_showOptions(this,'loadvitriNHAN',event)" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAPHIEU" value="" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieudt" value="" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieueo" value="" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMarq" value="" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamluong" value="" >
							                        				</td>
							                        				<td>
							                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamam" value="" >
							                        				</td>
							                        				
							                        			</tr>
							                        		 	
							                        		 <% countSP_Nhan ++; } %>
								                     </table>
								                     <div align="right">
								                     	<label style="color: red" ></label>
								                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
								                     	<a href="javascript:dropdowncontent.hidediv('subcontentNHAN_<%= i %>')" > Đóng lại </a>
								                     </div>
									            </DIV> 
									            
									            <script type="text/javascript">
									            	dropdowncontent.init('schemeNHAN_<%= i  %>', "left-top", 300, "click");
									            </script>
									         
										</td>
										
										<td> <input type="text" name="dongiaNHAN_saupb"  value="<%= yeucau.getDongia2() %>" style="width: 100%; text-align: right;  " readonly="readonly" ></td>
										<td> <input type="text" name="thanhtienNHAN"  value="" style="width: 100%;text-align: right; " readonly="readonly"  ></td>
									</tr>
									
								<% count++; } } %>
								
							<%  
						 	bien = count;
							while(count <= 4 ) { %>
							
								<tr>
									<td style="font-size: 9pt" align="center"><%= count + 1 %></td>
									<td>
										<input type="hidden" name="idspNHAN"  value="" > 
										<input type="text" name="maspNHAN"  value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'loadsanphamNHAN',event)" AUTOCOMPLETE="off" > 
									</td>
									<td> <input type="text" name="tenspNHAN" value="" style="width: 100%" readonly="readonly"> </td>
								 	<td> <input type="text" name="donviNHAN" value="" style="width: 100%; text-align: right;" readonly="readonly"> </td>
									<td> <input type="text" name="dongiaNHAN"  value="" style="width: 100%; text-align: right; "  ></td>
									
									<td > 
										<input type="text" name="soluongNHAN" value=""   style="width: 60%; text-align: right;" onkeypress="return keypress(event);" readonly="readonly"  > 
								        
								        <a href="" id="schemeNHAN_<%= count %>" rel="subcontentNHAN_<%= count %>">
						           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
						           	 		
			           	 		 		<DIV id="subcontentNHAN_<%= count %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
						                             background-color: white; width: 915px; max-height:200px; overflow:auto; padding: 4px;">
						                    <table width="95%" align="center">
						                    	<tr>
						                    		<td colspan="3" ><b>Tổng nhận</b></td>
						                    		<td colspan="11" > <input type="text" name="soluongNHAN2" value="0" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
						                    	</tr>
						                        <tr>
						                            <th width="80px" style="font-size: 10px">Số lượng</th>

						                         	<th width="80px" style="font-size: 10px">Số lô</th>
						                            <th width="70px" style="font-size: 10px">Ngày HH</th>

						                            <th width="25px" style="font-size: 10px">Mẻ</th>
						                            <th width="25px" style="font-size: 10px">Thùng</th>
						                            <th width="170px" style="font-size: 10px">Vị trí</th>
						                            <th width="80px" style="font-size: 10px">Mã phiếu</th>
						                            <th width="80px" style="font-size: 10px">Phiếu ĐT</th>
						                            <th width="50px" style="font-size: 10px">Phiếu EO</th>
						                            <th width="50px" style="font-size: 10px">Marq</th>
						                            <th width="60px" style="font-size: 10px">Hàm lượng</th>
						                            <th width="50px" style="font-size: 10px">Hàm ẩm</th>
						                        </tr>
						                        
						                        <% for( int j = 0 ; j <= 5; j++ ) {  
						        			
						        					String temID = "spNhan_" + j;
						        				%>
							                        		 	
			                        		 	<tr>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value=""  >
			                        				</td>
	
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spSOLO" value="" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spNGAYHETHAN" value="" class="days" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAME" value="" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMATHUNG" value="" >
			                        				</td>
			                        				<td>			                        					
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spVitri" value="" onkeyup="ajax_showOptions(this,'loadvitriNHAN',event)" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMAPHIEU" value="" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieudt" value="" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spPhieueo" value="" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spMarq" value="" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamluong" value="" >
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spHamam" value="" >
			                        				</td>
			                        				
			                        			</tr>
			                        		<% } %>   
						                    </table>    
						                    
						                    <div align="right">
						                     	<label style="color: red" ></label>
						                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontentNHAN_<%= count %>')" > Đóng lại </a>
						                     </div>
						                    
						        		</DIV>
										
										<script type="text/javascript">
							            	dropdowncontent.init('schemeNHAN_<%= count  %>', "left-top", 300, "click");
							            </script>
									            
									</td>
									
									<td> <input type="text" name="dongiaNHAN_saupb"  value="" style="width: 100%; text-align: right; " readonly="readonly" ></td>
									<td> <input type="text" name="thanhtienNHAN"  value="" style="width: 100%;text-align: right; " readonly="readonly"  ></td>
								</tr>
							
							<% count ++; } %>

						</table>
                		</fieldset>
                		
                	</td>
                </tr>
                
            </TABLE>

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
		spList.clear();
	}
	catch(Exception er)
	{
		er.printStackTrace();
	}
	finally{
		session.setAttribute("lsxBean", null) ; 
	}
	} %>
</form>
</BODY>
</html>