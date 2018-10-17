<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.imp.Lenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IYeucau"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.ILenhsanxuat"%>
<%@page import="geso.traphaco.erp.beans.yeucauchuyenkho.IErpChuyenkhoSX"%>
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

<% IErpChuyenkhoSX lsxBean = (IErpChuyenkhoSX)session.getAttribute("lsxBean"); %>
<% ResultSet ndxList = lsxBean.getNdxList(); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet doituongRs = lsxBean.getDoituongRs(); %>
<% ResultSet doituongNhanRs = lsxBean.getDoituongNhanRs(); %>
<% ResultSet chiphiRs = lsxBean.getChiphiRs(); %>
<% ResultSet lsxRs = lsxBean.getLsxRs(); %>
<% ResultSet muahangRs = lsxBean.getMuahangList(); %>
<% List<IYeucau> spList = (List<IYeucau>)lsxBean.getSpList(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	   NumberFormat formatter3 = new DecimalFormat("#,###,###.####");
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
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpYeuCauChuyenKhoUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="task" value='<%=lsxBean.gettask()%>'>
<input type="hidden" name="id" value='<%=lsxBean.getId()%>'>
<input type="hidden" name="action" value='1'>

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Chức năng > Yêu cầu chuyển kho > <%= ( lsxBean.getId().length() >0 ? "Cập nhật" : "Tạo mới" ) %> 
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpYeuCauChuyenKhoSvl?userId=<%= userId %>&task=<%=lsxBean.gettask()+"_back"%>" >
        	<img src="../images/Back30.png" alt="Quay ve"  title="Quay ve" border="1px" longdesc="Quay ve" style="border-style:outset"></A>
        <span id="btnSave">
	        <A href="javascript:saveform()" >
	        	<IMG src="../images/Save30.png" title="Luu lai" alt="Luu lai" border ="1px" style="border-style:outset"></A>
        </span>
        
        <%if(lsxBean.getId().length() >0){ %>  
	      <A href="../../ErpYeuCauChuyenKhoUpdateSvl?userId=<%=userId %>&print=<%=lsxBean.getId() %>" >
       		<img src="../images/Printer30.png" alt="Print"  title="Print"  border ="1px" longdesc="Print" style="border-style:outset"></A>
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
                    <TD style="width: 140px;" class="plainlabel" valign="top">Ngày xuất </TD>
                    <TD style="width: 240px;" class="plainlabel" valign="top"  >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/>
                    </TD>
                    <TD style="width: 120px;" class="plainlabel" valign="top">Lý do </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>" style="width: 400px;" />
                    </TD>
                </TR>
                <TR>
                    <TD class="plainlabel">Nội dung xuất</TD>
                    <TD class="plainlabel" colspan="3" >
                        <select name="noidungxuat" id="noidungxuat" onChange="submitform();" style="width: 400px">
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
                </TR>
                
                <TR>
                    <TD class="plainlabel" >Kho chuyển </TD>
                    <TD class="plainlabel" <%= lsxBean.getCoDoituong().equals("1") ? "" : "colspan='3'" %>  >
                    	<select name = "khoxuatId" style="width: 400px;"  onchange="changeKHO();" >
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
                     <TD class="plainlabel">Đối tượng chuyển</TD>
                     <TD class="plainlabel" >
                     		<input type="hidden" name="codoituong" value="<%= lsxBean.getCoDoituong() %>" >
                     		<input type="hidden" name="loaidoituongId" value="<%= lsxBean.getLoaidoituongId() %>" >
                        	<select name="doituongId" style="width: 400px;" onchange="submitform();" >
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
                
                <% if( lsxBean.getCochiphi().equals("1") ) { %>
                	<TR>
	                	<TD class="plainlabel" >Khoản mục phí </TD>
	                    <TD class="plainlabel" colspan="3"  >
	                    	<input type="hidden" name="coChiphi" value="<%= lsxBean.getCochiphi() %>" >
	                    	<select name = "chiphiId" style="width: 400px;"  >
	                    		<option value=""> </option>
	                        	<%
	                        		if(chiphiRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(chiphiRs.next())
	                        			{  
	                        			if( chiphiRs.getString("pk_seq").equals(lsxBean.getChiphiId())){ %>
	                        				<option value="<%= chiphiRs.getString("pk_seq") %>" selected="selected" ><%= chiphiRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= chiphiRs.getString("pk_seq") %>" ><%= chiphiRs.getString("ten") %></option>
	                        		 <% } } chiphiRs.close();} catch(SQLException ex){}
	                        		}
	                        	%>
	                    	</select>
	                    </TD>
	                </TR>
                <% } %>
                
                <% if( lsxBean.getCoKhonhan().equals("1")  ) {   %>
	                <TR>
	                	<TD class="plainlabel" >Kho nhận </TD>
	                    <TD class="plainlabel" <%= lsxBean.getCoDoituongNhan().equals("1") ? "" : "colspan='3'" %>  >
	                    	<input type="hidden" name="cokhoNhan" value="<%= lsxBean.getCoKhonhan() %>" >
	                    	<select name = "khonhapId" style="width: 400px;"  >
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
	                    
	                    <% if( lsxBean.getCoDoituongNhan().equals("1") ) { %>
	                    
	                    	 <TD class="plainlabel">Đối tượng nhận</TD>
		                     <TD class="plainlabel" >
		                     		<input type="hidden" name="codoituongNhan" value="<%= lsxBean.getCoDoituongNhan() %>" >
		                     		<input type="hidden" name="loaidoituongNhanId" value="<%= lsxBean.getLoaidoituongNhanId() %>" >
		                        	<select name="doituongNhanId" style="width: 400px;" >
		                    		<option value=""></option>
		                    			<% if( doituongNhanRs != null ) { %>
			                    		<% while(doituongNhanRs.next()) 
			                    		{ 
			                    			if(lsxBean.getDoituongNhanId().equals(doituongNhanRs.getString("PK_Seq"))) 
			                    			{ %>
			                    				<option value="<%= doituongNhanRs.getString("PK_Seq") %>" selected="selected"><%= doituongNhanRs.getString("Ten") %></option>
			                    			<% } 
			                    			else { %> 
			                    				<option value="<%= doituongNhanRs.getString("PK_Seq") %>" ><%= doituongNhanRs.getString("Ten") %></option>
			                    			<% }  %> 
			                    		<% } doituongNhanRs.close(); }  %>
			                    		
			                    	</select>
		                      </TD> 
	                    
	                    <% } %>
	                	
	                </TR>
                <% } %>
                
                <!-- XUẤT CHUYỂN NỘI BỘ -->
                <%-- <%if( lsxBean.getMaNDX(lsxBean.getNdxId()).equals("XK08") ){ %> --%>
                <TR>
                	<TD class="plainlabel">Số chứng từ</TD>
                    	<TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="sochungtu" value="<%= lsxBean.getSochungtu()%>"/></TD>
                </TR>
                <%-- <% } %> --%>
                
                <!-- XUẤT CHUYỂN GIA CONG -->
                <%if( lsxBean.getMaNDX(lsxBean.getNdxId()).equals("XK09") ||  lsxBean.getMaNDX(lsxBean.getNdxId()).equals("XK12")  ){ %>
                <TR>
                	<TD class="plainlabel">Đơn hàng gia công</TD>
                   	<TD class="plainlabel" colspan="3" >
                   		<select name="muahang_fk" style="width: 500px;"  >
                   		<option value=""></option>
                   			<% if( muahangRs != null ) { %>
                    		<% while(muahangRs.next()) 
                    		{ 
                    			if(lsxBean.getMuahang_fk().equals(muahangRs.getString("PK_Seq"))) 
                    			{ %>
                    				<option value="<%= muahangRs.getString("PK_Seq") %>" selected="selected"><%= muahangRs.getString("sopo") %></option>
                    			<% } 
                    			else { %> 
                    				<option value="<%= muahangRs.getString("PK_Seq") %>" ><%= muahangRs.getString("sopo") %></option>
                    			<% }  %> 
                    		<% } muahangRs.close(); }  %>
                    		
                    	</select>
                   	</TD>
                </TR>
                <% } %>
                <!-- XUẤT CHUYỂN GIA CONG -->
                <%if( lsxBean.getMaNDX(lsxBean.getNdxId()).equals("XK10") ){ %>
                <TR>
                	<TD class="plainlabel">Số lệnh SX</TD>
                   	<TD class="plainlabel" colspan="3" >
                   		<select name="lsxId" style="width: 500px;" onChange="submitform();" >
                   		<option value=""></option>
                   			<% if( lsxRs != null ) { %>
                    		<% while(lsxRs.next()) 
                    		{ 
                    			if(lsxBean.getLsxIds().equals(lsxRs.getString("PK_Seq"))) 
                    			{ %>
                    				<option value="<%= lsxRs.getString("PK_Seq") %>" selected="selected"><%= lsxRs.getString("diengiai") %></option>
                    			<% } 
                    			else { %> 
                    				<option value="<%= lsxRs.getString("PK_Seq") %>" ><%= lsxRs.getString("diengiai") %></option>
                    			<% }  %> 
                    		<% } lsxRs.close(); }  %>
                    		
                    	</select>
                   	</TD>
                </TR>
                <% } %>
                <!-- HO CHUYỂN CHO CHI NHÁNH -->
                <%if( lsxBean.getMaNDX(lsxBean.getNdxId()).equals("XK17") ){ %>
                <TR>
                	<TD class="plainlabel">Ký hiệu</TD>
                    	<TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="kyhieu" value="HL/14P"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Lệnh điều động số</TD>
                    <TD class="plainlabel">
                    	<input type="text"  name="lenhdieudong" value="<%=lsxBean.getLenhdieudong() %>"/></TD>
                    <TD  class="plainlabel" valign="top">Ngày  </TD>
                  	<TD  class="plainlabel" valign="top" >
                   		<input  type="text" class="days" name="ngaydieudong" value="<%=lsxBean.getNgaydieudong() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Của</TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="nguoidieudong" value=""/></TD>
                	<TD class="plainlabel">Về việc</TD>
                    <TD class="plainlabel"  >
                    	<input type="text"  name="veviec" value="Điều chuyển hàng hóa" /></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Người vận chuyển</TD>
                    	<TD class="plainlabel" colspan="3" >
                    	<input type="text"  name="nguoivanchuyen" value="<%=lsxBean.getNguoivanchuyen() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Phương tiện vận chuyển</TD>
                    	<TD class="plainlabel" >
                    	<input type="text"  name="phuongtien" value="<%=lsxBean.getPhuongtien() %>"/></TD>
                    <TD class="plainlabel">Hợp đồng</TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="hopdong" value="<%=lsxBean.getHopdong() %>"/></TD>
                </TR>
                <%} %>

                <TR>
                    <TD class="plainlabel" valign="top">Tổng SL yêu cầu </TD>
                    <TD class="plainlabel" valign="top">
                    	<input type="text"  name="tongSLYC" id="tongSLYC" style="text-align: right;" value="<%= lsxBean.getTongSLYC() %>" readonly="readonly"/>
                    </TD>
                    <TD class="plainlabel" valign="top">Diễn giải chứng từ </TD>
                    <TD class="plainlabel" >
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>" style="width: 400px;" />
                    </TD>
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
				<th align="center" width="10%">Số lượng tồn kho</th>
				<th align="center" width="20%"> Ghi chú </th>		
			</tr>
			
			<% 
			int count = 0; 
			if(spList.size() > 0) 
			{ 
				for(int i = 0; i < spList.size(); i++)
				{ 
					IYeucau yeucau = spList.get(i);

					%>
					
					<tr>
						<td style="font-size: 9pt" align="center"><%=count+1 %></td>
						<td>
							<input type="hidden" name="idsp"  value="<%= yeucau.getId() %>" > 
							<input type="text" name="masp"  value="<%= yeucau.getMa() %>" style="width: 100%"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
						</td>
						<td> <input type="text" name="tensp" value="<%= yeucau.getTen() %>" style="width: 100%" readonly="readonly"> </td>
					 	<td> <input type="text" name="donvi" value="<%= yeucau.getDonViTinh() %>" style="width: 100%; text-align: right;" readonly="readonly"> </td>
						<td> <input type="text" name="soluongyeucau" value="<%= yeucau.getSoluongYC().trim().length() <= 0 ? "0" : formatter3.format( Double.parseDouble( yeucau.getSoluongYC().replaceAll(",", "") ) ) %>"   style="width: 97%; text-align: right; " onkeypress="return keypress(event);" > </td>
						<td> 
							<input type="text" name="soluongtonkho" value="<%= yeucau.getSoluongTon().trim().length() <= 0 || yeucau.getSoluongTon()==null ? "0" : formatter3.format( Double.parseDouble(  yeucau.getSoluongTon().replaceAll(",", "") )) %>" style="width: 65%; text-align: right; " readonly="readonly"  >
							
							<% if( yeucau.getSoluongTon().trim().length() > 0 && yeucau.getSoluongTon()!=null ) { %>
								<a href="" id="scheme_<%= yeucau.getId()  %>" rel="subcontent_<%= yeucau.getId() %>">
		           	 				&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
		           	 		
	           	 		 		<DIV id="subcontent_<%= yeucau.getId() %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 300px; max-height:200px; overflow:auto; padding: 4px;z-index: 100" >
				                    <table width="100%" align="center">
				                    	
				                        <tr>
				                            <th width="100px" style="font-size: 10px">Tồn kho</th>
				                            <th width="100px" style="font-size: 10px">Số lô</th>
				                            <th width="100px" style="font-size: 10px">Ngày HH</th>
				                        </tr>
		                            	<%
		                            		ResultSet spRs = lsxBean.getTonKhoChiTiet( lsxBean.getKhoXuatId(), yeucau.getMa(),lsxBean.getLsxIds() );
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{
			                        				
			                        			%>
			                        			
			                        			<tr>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="" value="<%= formatter3.format( spRs.getDouble("AVAILABLE") )  %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="" value="<%= spRs.getString("SOLO") %>" readonly="readonly">
			                        				</td>
			                        				<td>
			                        					<input type="text" style="width: 100%;text-align: center;" name="" value="<%= spRs.getString("NGAYHETHAN") %>" readonly="readonly">
			                        				</td>
			                        				
			                        			</tr>
			                        			
			                        		 <% } } %>	 
				                     </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontent_<%= yeucau.getId()  %>')" > Đóng lại </a>
				                     </div>
					            </DIV> 
					            
					            <script type="text/javascript">
					            	dropdowncontent.init('scheme_<%= yeucau.getId() %>', "left-top", 300, "click");
					            </script>
					         <% } else { %>
					         	<a href="javascript:void(0);" >&nbsp; <img alt="Chọn số lô" src="../images/vitriluu.png"></a>
					         <% } %>
							 
						</td>
						<td> <input type="text" name="ghichu_ck"  value="<%= yeucau.getghichu_CK() %>" style="width: 100%; "  ></td>
					</tr>
					
				<% count++; } } %>
				
			<%  
		 	int bien=count+50;
			
			while(count <= bien ) { %>
			
				<tr>
				<td style="font-size: 9pt" align="center"><%=count+1 %></td>
					<td>
						<input type="hidden" name="idsp" value="" > 
						<input type="text" name="masp" value="" style="width: 100%"  onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off"  > 
					</td>
					<td> <input type="text" name="tensp" value="" style="width: 100%" readonly="readonly"> </td>
			 		<td> <input type="text" name="donvi" value="" style="width: 100%; text-align: right;" readonly="readonly"> </td>
					<td> <input type="text" name="soluongyeucau" value="" style="width: 97%; text-align: right; " onkeypress="return keypress(event);" > </td>
					<td> <input type="text" name="soluongtonkho" value="" style="width: 97%; text-align: right; " readonly="readonly"  > </td>
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
		lsxBean.DBclose(); 
		spList.clear();
		
		if( khonhapRs != null )
			khonhapRs.close();
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
</BODY>
</html>