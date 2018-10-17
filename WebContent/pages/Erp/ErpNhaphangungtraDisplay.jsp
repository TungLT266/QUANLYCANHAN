  
<%@page import="geso.traphaco.erp.util.DinhDang"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="geso.traphaco.erp.beans.nhaphangungtra.IYeucau"%>
<%@page import="geso.traphaco.erp.beans.nhaphangungtra.IErpNhaphangungtra"%>
 
 
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
 
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpNhaphangungtra lsxBean = (IErpNhaphangungtra)session.getAttribute("lsxBean"); 
	System.out.println("======================"+lsxBean.getLspId());
%>
<% ResultSet ndxList = lsxBean.getNdxList(); %>
<% ResultSet lspList = lsxBean.getLspRs(); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet nccxuatRs = lsxBean.getNccChuyenRs(); %>
<% ResultSet nccnhapRs = lsxBean.getNccNhanRs(); %>
<% ResultSet NvRs_nhan = lsxBean.getNvRs_nhan(); %>
<% ResultSet NvRs_xuat = lsxBean.getNvRs_xuat(); %>
<% ResultSet KhRs_nhan = lsxBean.getKhachHangRs_nhan(); %>
<% ResultSet KhRs_xuat = lsxBean.getKhachHangRs_xuat(); %>
<% ResultSet RsKenh = lsxBean.getRsKenh(); %>
<% ResultSet RsDoiTuongUngHang = lsxBean.getRsDoiTuongUngHang(); %>

<% ResultSet donvithuchienRs = lsxBean.getDonvithuchienRs(); %>
<% ResultSet nhomchiphiRs = lsxBean.getNhomChiPhiRs(); %>
<% List<IYeucau> spList = (List<IYeucau>)lsxBean.getSpList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	
	 
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoERP/index.jsp");
	} else { %>

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
<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/jquery.min.js"></script>

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
<script type="text/javascript" src="../scripts/erp_splist_nhaphangungtra.js"></script>
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
		document.getElementById("tongSLYC").value = DinhDangDonGia((tongSL).toFixed(4));
		
		setTimeout(replaces, 300);
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
	
	
	 
	 // dinh dang sole  ( giatri, so chu so thap phan)
	 function DinhDangDonGia_sole(num, thapphan) 
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
				sole = sole.substring(0,thapphan+1);
				kq = (((sign)?'':'-') + num) + sole;
			}
			else
				kq = (((sign)?'':'-') + num);
			
			//alert(kq);
			return kq;
		}
		
		
	 	function Dinhdang(element)
		{
			//element.value=DinhDangTien(element.value);
			element.value=DinhDangDonGia_sole(element.value,4);
			if(element.value== '' ||element.value=='0' )
			{
				element.value= '';
			}
		}
	 	
	 	
	 
	 
	 
	 
</script>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpNhaphangungtraUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="task" value='<%=lsxBean.gettask()%>'>
<input type="hidden" name="id" value='<%=lsxBean.getId()%>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho &gt; Chức năng &gt; Yêu cầu nhận hàng khác &gt; Hiển thị  
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpNhaphangungtraSvl?userId=<%= userId %>&task=<%=lsxBean.gettask()%>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>

        <%if(lsxBean.getId().length() >0){ %>
	        <% if(lsxBean.getNdxId().equals("100025")) { %>
		      <A href="../../ErpPhieuxuatchuyennoiboPdfSvl?userId=<%=userId %>&print=<%=lsxBean.getId() %>" >
	       		<img src="../images/Printer30.png" alt="Print"  title="Print"  border ="1px" longdesc="Print" style="border-style:outset"></A>
	       	<%} %>	
        	
        <%} %>
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
    	<legend class="legendtitle">Yêu cầu xuất kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="15%" class="plainlabel" valign="top">Ngày nhập </TD>
                    <TD  class="plainlabel" valign="top" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/></TD>
                    	
                    <TD class="plainlabel">Đơn vị thực hiện</TD>
                    <TD class="plainlabel" colspan="4" >
                       <select name="donvithuchienid" id="donvithuchienid" onChange="submitform();"  style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        	 
                        		if(donvithuchienRs != null)
                        		{
                        			try
                        			{
                        			while(donvithuchienRs.next())
                        			{  
                        			if( donvithuchienRs.getString("pk_seq").equals(lsxBean.getDonvithuchienId())){ %>
                        				<option value="<%= donvithuchienRs.getString("pk_seq") %>" selected="selected" ><%= donvithuchienRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= donvithuchienRs.getString("pk_seq") %>" ><%= donvithuchienRs.getString("ten") %></option>
                        		 <% } } donvithuchienRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                </TR>
                <TR>
                     <TD width="15%" class="plainlabel">Nội dung nhập</TD>
                    <TD class="plainlabel"  >
                        <select name="noidungxuat" id="noidungxuat" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(ndxList != null)
                        		{
                        			try
                        			{
                        			while(ndxList.next())
                        			{  
                        			if( ndxList.getString("pk_seq").equals(lsxBean.getNdxId())){ %>
                        				<option value="<%= ndxList.getString("pk_seq") %>" selected="selected" ><%= ndxList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= ndxList.getString("pk_seq") %>" ><%= ndxList.getString("ten") %></option>
                        		 <% } } ndxList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                    	<TD class="plainlabel" valign="top">Kho nhận </TD>
	                      <TD class="plainlabel" colspan="4" >
	                    	<select name = "khonhapId" onchange="" style="width: 200px" >
	                    		<option value=""> </option>
	                        	<%
	                        		if(khonhapRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(khonhapRs.next())
	                        			{  
	                        			if( khonhapRs.getString("pk_seq").equals(lsxBean.getKhoNhapId())){ %>
	                        				<option value="<%= khonhapRs.getString("pk_seq") %>" selected="selected" ><%= khonhapRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khonhapRs.getString("pk_seq") %>" ><%= khonhapRs.getString("ten") %></option>
	                        		 <% } } khonhapRs.close();} catch(SQLException ex){}
	                        		}
	                        	%>
	                    	</select>
	                    </TD>
                </TR>
                <TR>
                   	 <%if(lsxBean.getNdxId().equals("100051")){ %>
                    <TD width="15%" class="plainlabel" style="display:none">Loại Sản Phẩm</TD>
                    <TD class="plainlabel" style="display:none">
                    	<input type="hidden" name="loaisanphamcu" value="<%=lsxBean.getLspId()%>"/>
                        <select name="loaisanpham" id="loaisanpham" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	 <%
                        		if(lspList != null)
                        		{
                        			try
                        			{
                        			while(lspList.next())
                        			{  
                        			if( lspList.getString("pk_seq").equals(lsxBean.getLspId())){ %>
                        				<option value="<%= lspList.getString("pk_seq") %>" selected="selected" ><%= lspList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= lspList.getString("pk_seq") %>" ><%= lspList.getString("ten") %></option>
                        		 <% } } lspList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                     <TD class="plainlabel" valign="top"></TD>
                    <TD class="plainlabel" valign="top"></TD>
                     <TD class="plainlabel" valign="top">Tổng SL yêu cầu </TD>
                    <TD class="plainlabel" valign="top"  colspan="4" >
                    	<input type="text"  name="tongSLYC" id="tongSLYC" style="text-align: right;" value="<%= lsxBean.getTongSLYC() %>" readonly="readonly"/>
                    </TD>
                    
                     <%}
               			else{ %>
                     <TD width="15%" class="plainlabel">Loại Sản Phẩm</TD>
                    <TD class="plainlabel" >
                    	<input type="hidden" name="loaisanphamcu" value="<%=lsxBean.getLspId()%>"/>
                        <select name="loaisanpham" id="loaisanpham" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	 <%
                        		if(lspList != null)
                        		{
                        			try
                        			{
                        			while(lspList.next())
                        			{  
                        			if( lspList.getString("pk_seq").equals(lsxBean.getLspId())){ %>
                        				<option value="<%= lspList.getString("pk_seq") %>" selected="selected" ><%= lspList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= lspList.getString("pk_seq") %>" ><%= lspList.getString("ten") %></option>
                        		 <% } } lspList.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD>
                     <TD class="plainlabel" valign="top">Tổng SL yêu cầu </TD>
                    <TD class="plainlabel" valign="top"  colspan="4" >
                    	<input type="text"  name="tongSLYC" id="tongSLYC" style="text-align: right;" value="<%= lsxBean.getTongSLYC() %>" readonly="readonly"/>
                    </TD>
                     <%} %>
                    
                </TR>
                
                <TR>
                <TD width="15%" class="plainlabel" valign="top">Lý do </TD>
                    <TD  class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>"/>
                    </TD>
                   
                       <TD class="plainlabel" valign="top">Ghi chú </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>"/>
                    </TD> 
                </TR>
                 <TR>
                  <TD width="15%" class="plainlabel" valign="top">Người yêu cầu </TD>
                    <TD  class="plainlabel" valign="top">
                    	<input type="text"  name="nguoinhan" value="<%= lsxBean.getNguoinhan() %>"/>
                    </TD>
                    
                      <%if(lsxBean.getNdxId().equals("100078")){%>
                      	<td colspan="6" class="plainlabel"></td>
                      <%}else{
                    	  %>
                    	  	 <TD class="plainlabel">Khoản mục chi phí</TD>
                    <TD class="plainlabel" colspan="4" >
                      <select name="nhomchiphiid" id="nhomchiphiid"  style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        	 
                        		if(nhomchiphiRs != null)
                        		{
                        			try
                        			{
                        			while(nhomchiphiRs.next())
                        			{  
                        			if( nhomchiphiRs.getString("pk_seq").equals(lsxBean.getNhomChiPhiId())){ %>
                        				<option value="<%= nhomchiphiRs.getString("pk_seq") %>" selected="selected" ><%= nhomchiphiRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nhomchiphiRs.getString("pk_seq") %>" ><%= nhomchiphiRs.getString("ten") %></option>
                        		 <% } } nhomchiphiRs.close();} catch(SQLException ex){}
                        		}
                        	%>
                        </select>
                     </TD> 
                    	  <%
                      } %>
                </TR>
             
  

          
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
			<tr class="tbheader">
				<th align="center" width="2%">STT</th>
				<th align="center" width="15%">Mã sản phẩm</th>
				<th align="center" width="35%">Tên sản phẩm</th>
				<th align="center" width="5%"> Đơn vị</th>
				<th align="center" width="10%">Số lượng yêu cầu</th>
				
				<th align="center" width="10%" style="display: none">Số lượng tồn kho</th>
				<th align="center" width="20%"> Ghi chú </th>	
					
			</tr>
			
			<% 
			int count = 0; 
			if(spList.size() > 0) 
			{ 
				for(int i = 0; i < spList.size(); i++)
				{ 
					IYeucau yeucau = spList.get(i);
					double soluongtonkho=0;
					try{
						
						  soluongtonkho=Double.parseDouble(yeucau.getSoluongTon());
					
					}catch(Exception err){
						err.printStackTrace();
					}
					double soluongyeucau =0;
					try{
						
						soluongyeucau=Double.parseDouble(yeucau.getSoluongYC());
					
					}catch(Exception err){
						err.printStackTrace();
					}
					
					%>
					
					<tr>
						<td style="font-size: 9pt" align="center"><%=count+1 %></td>
						<td>
							<input type="hidden" name="idsp"  value="<%= yeucau.getId() %>" > 
							<input type="text" name="masp"  value="<%= yeucau.getMa() %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
						</td>
						<td> <input type="text" name="tensp" value="<%= yeucau.getTen() %>" style="width: 100%" readonly="readonly"> </td>
					 	<td> <input type="text" name="donvi" value="<%= yeucau.getDonViTinh() %>" style="width: 100%; text-align: right;" readonly="readonly"> </td>
						<td> <input type="text" name="soluongyeucau" value="<%= DinhDang.dinhdangkho(soluongyeucau)%>"   style="width: 97%; text-align: right; " onkeyup="Dinhdang(this)"    onkeypress="return keypress(event);" > </td>
						<td  style="display: none"> <input type="text" name="soluongtonkho" value="<%= DinhDang.dinhdangkho(soluongtonkho) %>" style="width: 97%; text-align: right; " readonly="readonly"  > </td>
						<td> <input type="text" name="ghichu_ck"  value="<%= yeucau.getghichu_CK() %>" style="width: 98%; text-align: right; "  ></td>
					</tr>
					
				<% count++; } } %>
				
			<%  
		 	int bien=count+20;
			
			while(count <= bien ) { %>
			
				<tr>
				<td style="font-size: 9pt" align="center"><%=count+1 %></td>
					<td>
						<input type="hidden" name="idsp" value="" > 
						<input type="text" name="masp" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off"  > 
					</td>
					<td> <input type="text" name="tensp" value="" style="width: 100%" readonly="readonly"> </td>
			 		<td> <input type="text" name="donvi" value="" style="width: 100%; text-align: right;" readonly="readonly"> </td>
					<td> <input type="text" name="soluongyeucau" value="" style="width: 97%; text-align: right; " onkeypress="return keypress(event);"   onkeyup="Dinhdang(this)"    > </td>
					<td style="display: none"> <input type="text" name="soluongtonkho" value="" style="width: 97%; text-align: right; " readonly="readonly"  > </td>
					<td> <input type="text" name="ghichu_ck" value="" style="width: 100%; text-align: left; "  ></td>
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
			
		if(nccnhapRs != null)
			nccnhapRs.close();
		if(nccxuatRs != null)
			nccxuatRs.close();
		if(ndxList != null)
			ndxList.close();
		if(khoxuatRs != null)
			khoxuatRs.close();
		if(khonhapRs != null)
			khonhapRs.close();
		if(NvRs_nhan != null)
			NvRs_nhan.close();
		if(NvRs_xuat != null)
			NvRs_xuat.close();
		if(KhRs_nhan != null)
			KhRs_nhan.close();
		if(KhRs_xuat != null)
			KhRs_xuat.close();
		if(RsKenh != null)
			RsKenh.close();
		if(RsDoiTuongUngHang != null)
			RsDoiTuongUngHang.close();
		if(donvithuchienRs != null)
			donvithuchienRs.close();
		if(nhomchiphiRs != null)
			nhomchiphiRs.close();
		if(spList != null)
			spList.clear();
		if(lsxBean!=null )
			lsxBean.DBclose();
		session.removeAttribute("lsxBean");

	}
	catch(Exception er){ }

	} %>
</form>
<script type='text/javascript' src='../scripts/loadingv2.js'></script>
</BODY>
</html>