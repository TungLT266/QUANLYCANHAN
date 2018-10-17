<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.imp.Lenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.ILenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IErpDoisolo"%>
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

<% IErpDoisolo lsxBean = (IErpDoisolo)session.getAttribute("ckBean"); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet doituongRs = lsxBean.getDoituongRs(); %>

<% 
	List<IYeucau> spList = (List<IYeucau>)lsxBean.getSpList(); 
	Hashtable<String, String> sanpham_soluong = lsxBean.getSanpham_Soluong();
	Hashtable<String, String> sanpham_soluongDAXUAT = lsxBean.getSanpham_SoluongDAXUAT_THEODN();
	Hashtable<String, String> sanpham_thongtindoi = lsxBean.getSanpham_Thongtindoi();
	Hashtable<String, String> sanpham_ngayhethanDoi = lsxBean.getSanpham_Ngayhethandoi();
	NumberFormat formater = new DecimalFormat("##,###,###.###");
%>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	   NumberFormat formatter3 = new DecimalFormat("#,###,###.######");
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
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
<script type="text/javascript" src="../scripts/erp_splist_yeucauchuyenkho.js"></script>
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
		var soluongtonkho = document.getElementsByName("soluongtonkho");
		var slyc = document.getElementsByName("soluongyeucau");
		
		var tongSL = 0;
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
					soluongtonkho.item(i).value = sp.substring(0, parseInt(sp.indexOf("]")));
					
					slyc.item(i).value = "0";
					submitform();
				}
				
				if(slyc.item(i).value != "")
				{
				    tongSL = tongSL + parseFloat(slyc.item(i).value.replace(",",""));
				}
			}
			else
			{
				idsp.item(i).value = "";
				masp.item(i).value = "";
				tensp.item(i).value = "";
			    slyc.item(i).value = "";
			    soluongtonkho.item(i).value = "";
				donvi.item(i).value = "";
			}
		}	
		
		document.getElementById("tongSLYC").value = DinhDangDonGia((tongSL).toFixed(3));
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
<form name="hctmhForm" method="post" action="../../ErpDoisoloUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="id" value='<%=lsxBean.getId()%>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Chức năng > Điều chỉnh thông tin kho > <%= ( lsxBean.getId().length() >0 ? "Cập nhật" : "Tạo mới" ) %> 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpDoisoloSvl?userId=<%= userId %>" >
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
    	<legend class="legendtitle">Điều chỉnh thông tin kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD style="width: 140px;" class="plainlabel" valign="top">Ngày điều chỉnh </TD>
                    <TD style="width: 240px;" class="plainlabel" valign="top"  >
                    	<input type="text" class="days" readonly="readonly"  onchange="submitform();" name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    <TD style="width: 120px;" class="plainlabel" valign="top">Lý do </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>" style="width: 400px;" />
                    </TD>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Kho điều chỉnh </TD>
                    <TD class="plainlabel" <%= lsxBean.getCoDoituong().equals("1") ? "" : "colspan='3'" %>  >
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
 
                	 <% if( lsxBean.getCoDoituong().equals("1") ) { %> 
                     <TD class="plainlabel">Đối tượng </TD>
                     <TD class="plainlabel" >
                     		<input type="hidden" name="codoituong" value="<%= lsxBean.getCoDoituong() %>" >
                     		<input type="hidden" name="loaidoituongId" value="<%= lsxBean.getLoaidoituongId() %>" >
                        	<select name="doituongId" style="width: 200px;" onchange="submitform();" class="select" >
                    		<option value=""></option>
                    			<% if( doituongRs != null ) { %>
	                    		<% while(doituongRs.next()) 
	                    		{ 
	                    			if(lsxBean.getDoituongId().equals(doituongRs.getString("PK_Seq"))) 
	                    			{ %>
	                    				<option value="<%= doituongRs.getString("PK_Seq") %>" selected="selected"><%= doituongRs.getString("Ten") %></option>
	                    			<% } 
	                    			else { %> 
	                    				<option value="<%= doituongRs.getString("PK_Seq") %>" ><%= doituongRs.getString("Ten") %></option>
	                    			<% }  %> 
	                    		<% } doituongRs.close(); } %>
	                    		
	                    	</select>
                      </TD> 
                      <% }  %>
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Loại điều chỉnh </TD>
                    <TD class="plainlabel" colspan='3'  >
                    	<select name = "loaidieuchinh" style="width: 200px;"  onchange="submitform();" class="select" >
                    		<option value=""> </option>
                        	<% if( lsxBean.getLoaidieuchinh().equals("0") ) { %>
                        		<option value="0" selected="selected" >Số lô</option>
                        	<% } else { %>
                        		<option value="0" >Số lô</option>
                        	<% } %>
                        	<% if( lsxBean.getLoaidieuchinh().equals("1") ) { %>
                        		<option value="1" selected="selected" >Marquette</option>
                        	<% } else { %>
                        		<option value="1" >Marquette</option>
                        	<% } %>
                        	<% if( lsxBean.getLoaidieuchinh().equals("2") ) { %>
                        		<option value="2" selected="selected" >Phiếu kiểm nghiệm</option>
                        	<% } else { %>
                        		<option value="2" >Phiếu kiểm nghiệm</option>
                        	<% } %>
                        	<% if( lsxBean.getLoaidieuchinh().equals("3") ) { %>
                        		<option value="3" selected="selected" >Thùng</option>
                        	<% } else { %>
                        		<option value="3" >Thùng</option>
                        	<% } %>
                        	<% if( lsxBean.getLoaidieuchinh().equals("4") ) { %>
                        		<option value="4" selected="selected" >Mẻ</option>
                        	<% } else { %>
                        		<option value="4" >Mẻ</option>
                        	<% } %>
                        	<% if( lsxBean.getLoaidieuchinh().equals("5") ) { %>
                        		<option value="5" selected="selected" >Phiếu định tính</option>
                        	<% } else { %>
                        		<option value="5" >Phiếu định tính</option>
                        	<% } %>
                        	<% if( lsxBean.getLoaidieuchinh().equals("6") ) { %>
                        		<option value="6" selected="selected" >Hàm ẩm & hàm lượng</option>
                        	<% } else { %>
                        		<option value="6" >Hàm ẩm & hàm lượng</option>
                        	<% } %>
                        	<% if( lsxBean.getLoaidieuchinh().equals("7") ) { %>
                        		<option value="7" selected="selected" >Nhà sản xuất</option>
                        	<% } else { %>
                        		<option value="7" >Nhà sản xuất</option>
                        	<% } %>
                    	</select>
                     </TD>
                </TR>
                
                <TR style="display: none;" >
                    <TD class="plainlabel" valign="top">Tổng SL chuyển </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  name="tongSLYC" id="tongSLYC" style="text-align: right;" value="<%= lsxBean.getTongSLYC() %>" readonly="readonly"/>
                    </TD>
                    <TD class="plainlabel" valign="top">Ghi chú </TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 400px;" />
                    </TD>
                </TR>
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
			<tr class="tbheader">
				<th align="center" width="2%">STT</th>
				<th align="center" width="20%">Mã sản phẩm</th>
				<th align="center" width="35%">Tên sản phẩm</th>
				<th align="center" width="10%"> Đơn vị</th>
				<th align="center" width="20%">Ghi chú </th>	
				<th align="center" width="10%" style="display: none;" >Tồn kho</th>
				<th align="center" width="20%">Số lượng </th>	
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
						<td> <input type="text" name="ghichu_ck"  value="<%= yeucau.getghichu_CK() %>" style="width: 100%; "  ></td>
						<td style="display: none;" > <input type="text" name="soluongtonkho" value="0" style="width: 97%; text-align: right; " readonly="readonly"  > </td>
						<td align='right' > 
							<input type="text" name="soluongyeucau" value="<%= formatter3.format(soluongyeucau)%>"   style="width: 50%; text-align: right; display: none; " onkeypress="return keypress(event);" onchange="submitform();" > 
							<% if( soluongyeucau >= 0 && lsxBean.getKhoXuatId().trim().length() > 0  ) { %>
								<a href="" id="scheme_<%= yeucau.getMa() + "__ "  %>" rel="subcontent_<%= yeucau.getMa() + "__ " %>">
		           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
		           	 		
	           	 		 		<DIV id="subcontent_<%= yeucau.getMa() + "__ " %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: <%= lsxBean.getLoaidieuchinh().equals("0") ? "1120px" : "1120px" %>; max-height:200px; overflow:auto; padding:1px;">
				                    <table width="100%" align="center">
				                    	<tr>
				                    		<td colspan="3" ><b>Tổng xuất</b></td>
				                    		<td colspan="11" > <input type="text" name="soluong2" value="<%= soluongyeucau %>" style="width: 100px; text-align: right;" readonly="readonly" >	</td>
				                    	</tr>
				                        <tr>
				                            <th width="95px" style="font-size: 11px; color: red;">Số lượng đổi</th>
				                            <% if( lsxBean.getLoaidieuchinh().equals("0") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">Số lô mới</th>
				                            	<th width="80px" style="font-size: 11px; color: red;">Ngày HH mới</th>
				                            <% } else if( lsxBean.getLoaidieuchinh().equals("1") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">Marquette mới</th>
				                            <% } else if( lsxBean.getLoaidieuchinh().equals("2") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">Mã phiếu mới</th>
				                            <% } else if( lsxBean.getLoaidieuchinh().equals("3") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">Thùng mới</th>
				                            <% } else if( lsxBean.getLoaidieuchinh().equals("4") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">Mẻ mới</th>
				                            <% } else if( lsxBean.getLoaidieuchinh().equals("5") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">Phiếu định tính mới</th>
				                            <% } else if( lsxBean.getLoaidieuchinh().equals("6") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">Hàm ẩm mới</th>
				                            	<th width="80px" style="font-size: 11px; color: red;">Hàm lượng mới</th>
				                            <% } else if( lsxBean.getLoaidieuchinh().equals("7") ) { %>
				                            	<th width="80px" style="font-size: 11px; color: red;">NSX mới</th>
				                            <% } %>
				                            <th width="80px" style="font-size: 11px">Tồn hiện tại</th>
				                            <th width="120px" style="font-size: 10px">Số lô</th>
				                            <th width="73px" style="font-size: 10px">Ngày HH</th>
				                            <th width="73px" style="font-size: 10px">Ngày NK</th>
				                            <th width="20px" style="font-size: 10px">Mẻ</th>
				                            <th width="20px" style="font-size: 10px">Thùng</th>
				                            <th width="100px" style="font-size: 10px">Vị trí</th>
				                            <th width="120px" style="font-size: 10px">Mã phiếu</th>
				                            <th width="80px" style="font-size: 10px">Phiếu ĐT</th>
				                            <th width="50px" style="font-size: 10px">Phiếu EO</th>
				                             <th width="100px" style="font-size: 10px">NSX</th>
				                            <th width="100px" style="font-size: 10px">Marquette</th>
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
			                        				String temID = yeucau.getMa() + "__ ";
			                        				String key = yeucau.getMa() + "__ " + "__" + spRs.getString("SOLO") + "__" + spRs.getString("NGAYHETHAN") + "__" + spRs.getString("NGAYNHAPKHO")  
			                        													+ "__" + spRs.getString("MAME") + "__" + spRs.getString("MATHUNG") + "__" + spRs.getString("VITRI")
			                        													+ "__" + spRs.getString("MAPHIEU") + "__" + spRs.getString("PHIEUDT") + "__" + spRs.getString("PHIEUEO")
			                        													+ "__" + spRs.getString("nsx_fk")
			                        													+ "__" + spRs.getString("MARQ") + "__" + spRs.getString("HAMLUONG") + "__" + spRs.getString("HAMAM");
			                        				System.out.println("::: KEY:: " + key );
			                        			%>
			                        			
			                        			<tr>
			                        				<td>
			                        				<% if(sanpham_soluong.get( key ) != null ) { %>
			                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="<%= formater.format(Double.parseDouble( sanpham_soluong.get( key ))) %>" onkeyup="CheckSoLuong_DeXuat(this);" >
			                        				<% } else { %>
			                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spSOLUONG" value="" onkeyup="CheckSoLuong_DeXuat(this);" >
			                        				<% } %>
			                        				</td>
			                        				
			                        				<td>
		                        						<% if(sanpham_thongtindoi.get( key ) != null ) { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTHONGTINDOI" value="<%= sanpham_thongtindoi.get( key ) %>"  >
				                        				<% } else { %>
				                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spTHONGTINDOI" value=""  >
				                        				<% } %>
			                        				</td>
			                        				
			                        				<% if( lsxBean.getLoaidieuchinh().equals("0") || lsxBean.getLoaidieuchinh().equals("6") ) { %>
			                        				<td>
			                        					<% if( lsxBean.getLoaidieuchinh().equals("0") ) { %>
			                        						<% if(sanpham_ngayhethanDoi.get( key ) != null ) { %>
					                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spNGAYHHDOI" value="<%= sanpham_ngayhethanDoi.get( key ) %>" readonly="readonly" class="days" >
					                        				<% } else { %>
					                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spNGAYHHDOI" value=""  readonly="readonly" class="days"  >
					                        				<% } %>
				                        				<% } else { %>
				                        					<% if(sanpham_ngayhethanDoi.get( key ) != null ) { %>
					                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spNGAYHHDOI" value="<%= sanpham_ngayhethanDoi.get( key ) %>"  >
					                        				<% } else { %>
					                        					<input type="text" style="width: 100%;text-align: right;" name="<%= temID %>_spNGAYHHDOI" value=""    >
					                        				<% } %>
				                        				<% } %>
			                        				</td>
			                        				<% } %>
			                        				
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: right;font-size: 12px" name="<%= temID %>_spTONKHO" value="<%= formater.format(spRs.getDouble("AVAILABLE")) %>" readonly="readonly">
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
			                        					<input type="text" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spNSX" value="<%= spRs.getString("NSXTEN") %>" readonly="readonly">
			                        					<input type="hidden" style="width: 100%;text-align: center;font-size: 12px" name="<%= temID %>_spNSXID" value="<%= spRs.getString("NSX_FK") %>" readonly="readonly">
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
			                        			
			                        			<script type="text/javascript">
			                        				<%if( lsxBean.getLoaidieuchinh().equals("7") ) { %>
			                        					jQuery(function()
														{		
															$("input[name='<%= temID %>_spTHONGTINDOI']").autocomplete("NhaSanXuatList_DoiSL.jsp?");
														});	
			                        				<%} %>
												</script>
			                        			
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
					</tr>
					
				<% count++; } } %>
				
			<%  
		 	int bien=count+50;
			while(count <= bien ) { %>
			
				<tr>
					<td style="font-size: 9pt" align="center"><%= count+1 %></td>
					<td>
						<input type="hidden" name="idsp"  value="" > 
						<input type="text" name="masp"  value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
					</td>
					<td> <input type="text" name="tensp" value="" style="width: 100%" readonly="readonly"> </td>
				 	<td> <input type="text" name="donvi" value="" style="width: 100%; text-align: right;" readonly="readonly"> </td>
					<td> <input type="text" name="ghichu_ck"  value="" style="width: 100%; "  ></td>
					<td style="display: none;" > <input type="text" name="soluongtonkho" value="" style="width: 97%; text-align: right; " readonly="readonly"  > </td>
					<td align='right' > 
						<input type="text" name="soluongyeucau" value=""   style="width: 50%; text-align: right; display: none; " onkeypress="return keypress(event);" onchange="submitform();" > 
				        <a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
					</td>
				</tr>
			
			<% count ++; } %>
				
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
		spList.clear();

		if( khoxuatRs != null )
			khoxuatRs.close();

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
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</BODY>
</html>