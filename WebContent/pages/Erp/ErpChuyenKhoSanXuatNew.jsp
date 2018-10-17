 <%@page import="java.text.DecimalFormat"%>
<%@page import="java.text.NumberFormat"%>
<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.sql.SQLException"%>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuat.*" %>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuat.imp.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.*" %>
<%@ page  import = "geso.traphaco.erp.beans.phieuxuatkho.imp.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.util.Hashtable" %>
<%@ page  import = "java.util.List" %>

<% IErpChuyenkhoSX lsxBean = (IErpChuyenkhoSX)session.getAttribute("lsxBean"); %>
<% ResultSet ndxList = lsxBean.getNdxList(); %>
<% ResultSet khoxuatRs = lsxBean.getKhoXuatRs(); %>
<% ResultSet khonhapRs = lsxBean.getKhoNhapRs(); %>
<% ResultSet nccxuatRs = lsxBean.getNccChuyenRs(); %>
<% ResultSet nccnhapRs = lsxBean.getNccNhanRs(); %>


<% List<IYeucau> spList = (List<IYeucau>)lsxBean.getSpList(); %>

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
<script type="text/javascript" src="../scripts/erp-spChuyenKhoList.js"></script>

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
	function TinhTongSoLuongXuat()
	{

			var sanpham = document.getElementsByName("idsp");
			 
			var soluongxuat = document.getElementsByName("soluongchuyen");
			
			for(var i = 0; i < sanpham.length; i++)
			{
				 
				if(sanpham.item(i).value != "")
				{
					var tongluong = 0;
					var id =  sanpham.item(i).value; 
					 
					var solo = document.getElementsByName(id + 'solo');
					var soluong = document.getElementsByName(id + 'soluongxuat');
	  
					for( var j = 0; j < soluong.length; j++)
					{
						if(solo.item(j).value != "" && soluong.item(j).value != "")
						{

							var slg = soluong.item(j).value.replace(/,/g,"");
							tongluong = parseFloat(tongluong) + parseFloat(slg);
							 
							 
						}
						else
						{
							if( solo.item(j).value == "" )
							{
								soluong.item(j).value = "";
								 
							}
						}
					}
					
					soluongxuat.item(i).value = tongluong.toFixed(6);
					
					 
				}
			}
			
	}
	
	function replaces()
	{
		
		 var spma = document.getElementsByName("masp");
		 var spten1 = document.getElementsByName("tensp");
		 var spId = document.getElementsByName("idsp");
		 var dvt1 = document.getElementsByName("donvi");
		 var tonhientai=document.getElementsByName("tonhientai");
		 var soluong1 = document.getElementsByName("soluongchuyen");
		 var isreload = document.getElementsByName("isreload");
		 
		 
			for(i=0; i < spma.length; i++)
			{
				
				 var tem=spma.item(i).value;
				 var pos=parseInt(tem.indexOf(" -- "));
				 if(pos  > 0){
					 	spma.item(i).value = tem.substring(0, parseInt(tem.indexOf(" -- ")));
						tem = tem.substr(parseInt(tem.indexOf(" -- ")) +3);
						 
						spten1.item(i).value = tem.substring(0, tem.indexOf("[") );
						
						tem = tem.substr(parseInt(tem.indexOf("["))+1 );
						 
						dvt1.item(i).value = tem.substring(0, tem.indexOf("]") );
						tem = tem.substr(parseInt(tem.indexOf("["))+1 );
						
						tonhientai.item(i).value = tem.substring(0, tem.indexOf("]") );
						
						
						tem = tem.substr(parseInt(tem.indexOf("["))+1 );
						
						spId.item(i).value =tem.substring(0, tem.indexOf("]") );
						isreload.item(i).value='1';
						
						document.forms['hctmhForm'].action.value= 'reload_sp';
					    document.forms['hctmhForm'].submit();
				 }
		 
			 if(spma.item(i).value==""){
				 spten1.item(i).value="";
				 dvt1.item(i).value="";
				 soluong1.item(i).value="";
				 spId.item(i).value="";
				 tonhientai.item(i).value="";
			 }
			}
		
		
		var idsp = document.getElementsByName("idsp");
		var masp = document.getElementsByName("masp");
		var tensp = document.getElementsByName("tensp");  
		var donvi = document.getElementsByName("donvi");
		var soluongchuyen = document.getElementsByName("soluongchuyen");
		
		var tongSL= 0;
		var i;
		for(i = 0; i < masp.length; i++)
		{
			if(masp.item(i).value != "")
			{
				var sp = masp.item(i).value;
				 
					if(soluongchuyen.item(i).value != "")
				  {
				    tongSL = tongSL + parseFloat(soluongchuyen.item(i).value.replace(",",""));
				  }
			}
			else
			{
				idsp.item(i).value = "";
				masp.item(i).value = "";
			 
				soluongchuyen.item(i).value ="";
				 
				donvi.item(i).value = "";
			}
		}	
		document.getElementById("tongSLchuyen").value = DinhDangDonGia((tongSL).toFixed(3));
		TinhTongSoLuongXuat();
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
	
</head>
<BODY leftmargin="0" bottommargin="0" topmargin="0" rightmargin="0">
<form name="hctmhForm" method="post" action="../../ErpChuyenkhoSXUpdateSvl">
<input type="hidden" name="userId" value='<%= userId %>'>
<input type="hidden" name="action" value='1'>

<input type="hidden" name="task" value='<%=lsxBean.gettask()%>'>
<input type="hidden" name="isnhanHang" value='<%=lsxBean.getIsnhanHang()%>'>
<input type="hidden" name="yeucauchuyenkhoid" value='<%= lsxBean.getYeucauchuyenkhoId() %>'>
<input type="hidden" name="lsxid" value='<%= lsxBean.getLsxIds()%>'>
<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Quản lý tồn kho > <%=(lsxBean.gettask().equals("LSX")?"Chuyển nguyên liệu":"Chuyển kho")%> > Tạo mới
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng bạn <%= userTen %> &nbsp;
        </div>
    </div>
  
  	<div align="left" id="button" style="width:100%; height:32px; padding:0px; float:none" class="tbdarkrow">
    	<A href= "../../ErpChuyenkhoSXSvl?userId=<%= userId %>&task=<%=lsxBean.gettask()%>&isnhanHang=<%=lsxBean.getIsnhanHang()%>" >
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
    	<legend class="legendtitle">Yêu cầu xuất kho </legend>
        	<div style="float:none; width:100%" align="left">
            <TABLE width="100%" cellpadding="4" cellspacing="0">							
                <TR>
                    <TD width="120px" class="plainlabel" valign="top">Ngày xuất </TD>
                    <TD colspan="4" class="plainlabel" valign="top" >
                    	<input type="text" class="days" readonly="readonly"  name="ngaychuyen" value="<%= lsxBean.getNgayyeucau() %>"/></TD>
                </TR>
                <TR>
                    <TD class="plainlabel" valign="top">Lý do </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="text"  name="lydo" value="<%= lsxBean.getLydoyeucau() %>"/>
                    </TD>
                </TR>
                  <TR>
                 <% if(!lsxBean.getNdxId().equals("100030")){ %>
                    <TD class="plainlabel" valign="top">Người nhận </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="text"  name="nguoinhan" value="<%= lsxBean.getNguoinhan() %>"/>
                    </TD>
                <% }else{ %>
                    <TD class="plainlabel" valign="top">Tài sản xây dựng dở dang </TD>
                    <TD class="plainlabel" valign="top">
                    	<SELECT NAME = "tsddId" style="width: 250px">
                    	<OPTION VALUE = "" >&nbsp;</OPTION>
                    <% 
                    	ResultSet tsddRs = lsxBean.getTsddRs();
                    	while (tsddRs.next()) {
                    	
                    		if(tsddRs.getString("tsddId").equals(lsxBean.getTsddId())){ %>
                    
                    		<OPTION VALUE = <%= tsddRs.getString("tsddId") %> SELECTED >	<%= tsddRs.getString("tsdd") %></OPTION>
                    		
                    <%		}else{	%>
                    
                    		<OPTION VALUE = <%= tsddRs.getString("tsddId") %>  >	<%= tsddRs.getString("tsdd") %></OPTION>
                    		
                    <%		} %>
                    	
                    <% } %>
                    	</SELECT>
                    </TD>
                    
                    <TD class="plainlabel" valign="top">Người nhận </TD>
                    <TD colspan="2" class="plainlabel" valign="top">
                    	<input type="text"  name="nguoinhan" value="<%= lsxBean.getNguoinhan() %>"/>
                    </TD>                
                <% } %>
                
              	  </TR>
                
               	 <TR>
                    <TD class="plainlabel">Nội dung xuất</TD>
                    <TD class="plainlabel" colspan="4" >
                        <select name="noidungxuat" id="noidungxuat" onChange="submitform();">
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
                
                <% if(lsxBean.getNdxId().trim().length() > 0) { %>
                <TR>
                    <TD class="plainlabel" valign="top">Kho chuyển </TD>
                    <TD width="230px" class="plainlabel" valign="top" <%= lsxBean.getNdxId().equals("100015") ? "" : ( nccxuatRs != null ? "" : "colspan='3'") %>  >
                    	<select name = "khoxuatId" onchange="changeKHO();" >
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
                     
                      <% if(nccxuatRs != null) { %> 
                      	 <TD width="150px" class="plainlabel">Nhà cung cấp chuyển</TD>
	                     <TD class="plainlabel" >
	                        	<select name="nccchuyenId" onchange="submitform();">
	                    		<option value=""></option>
		                    		<% while(nccxuatRs.next()) 
		                    		{ 
		                    			if(lsxBean.getNccChuyenIds().equals(nccxuatRs.getString("PK_Seq"))) 
		                    			{ %>
		                    				<option value="<%= nccxuatRs.getString("PK_Seq") %>" selected="selected"><%= nccxuatRs.getString("Ten") %></option>
		                    			<% } 
		                    			else { %> 
		                    				<option value="<%= nccxuatRs.getString("PK_Seq") %>" ><%= nccxuatRs.getString("Ten") %></option>
		                    			<% }  %> 
		                    		<% } %>
		                    		
		                    	</select>
	                      </TD> 
                      	
                      
                      <% } else { %> <td class="plainlabel" colspan="2" >&nbsp;</td> <% } %>
                    
                </TR>
                <!-- CHUYỂN KHO BÊN NGOÀI -->
                <%if( lsxBean.getNdxId().equals("100025") ){ %>
                <TR>
                	<TD class="plainlabel">Ký hiệu</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="kyhieu" value="<%=lsxBean.getKyHieu() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Số chứng từ</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="sochungtu" value="<%= lsxBean.getSochungtu()%>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Lệnh điều động số</TD>
                    	<TD class="plainlabel">
                    	<input type="text"  name="lenhdieudong" value="<%=lsxBean.getLenhdieudong() %>"/></TD>
                    <TD  class="plainlabel" valign="top">Ngày  </TD>
                   		 <TD  colspan="2" class="plainlabel" valign="top" >
                    	<input  type="text" class="days" name="ngaydieudong" value="<%=lsxBean.getNgaydieudong() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Của</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="nguoidieudong" value="<%=lsxBean.getNguoidieudong() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Về việc</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="veviec" value="<%=lsxBean.getVeviec() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Người vận chuyển</TD>
                    	<TD class="plainlabel" colspan="4" >
                    	<input type="text"  name="nguoivanchuyen" value="<%=lsxBean.getNguoivanchuyen() %>"/></TD>
                </TR>
                <TR>
                	<TD class="plainlabel">Phương tiện vận chuyển</TD>
                    	<TD class="plainlabel" >
                    	<input type="text"  name="phuongtien" value="<%=lsxBean.getPhuongtien() %>"/></TD>
                    <TD class="plainlabel">Hợp đồng</TD>
                    	<TD class="plainlabel" colspan="2" >
                    	<input type="text"  name="hopdong" value="<%=lsxBean.getHopdong() %>"/></TD>
                </TR>
                <%} %>
                
                <% if(lsxBean.getNdxId().equals("100039") || lsxBean.getNdxId().equals("100025") || lsxBean.getNdxId().equals("100006") || lsxBean.getNdxId().equals("100010") || lsxBean.getNdxId().equals("100011") || lsxBean.getNdxId().equals("100026") ) { %>
	                <TR>
	                	
	                	<TD class="plainlabel" valign="top">Kho nhận </TD>
	                    <TD <%= nccnhapRs != null ? "" : " colspan='4' " %>  class="plainlabel" valign="top"  width="230px" >
	                    	<select name = "khonhapId" onchange="submitform();" >
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
	                    
	                    <% if(nccnhapRs != null) { %>
	                    
	                    	 <TD width="150px" class="plainlabel">Nhà cung cấp nhận</TD>
		                     <TD class="plainlabel" >
		                        	<select name="nccnhanId" >
		                    		<option value=""></option>
			                    		<% while(nccnhapRs.next()) 
			                    		{ 
			                    			if(lsxBean.getNccNhanIds().equals(nccnhapRs.getString("PK_Seq"))) 
			                    			{ %>
			                    				<option value="<%= nccnhapRs.getString("PK_Seq") %>" selected="selected"><%= nccnhapRs.getString("Ten") %></option>
			                    			<% } 
			                    			else { %> 
			                    				<option value="<%= nccnhapRs.getString("PK_Seq") %>" ><%= nccnhapRs.getString("Ten") %></option>
			                    			<% }  %> 
			                    		<% } %>
			                    		
			                    	</select>
		                      </TD> 
	                    
	                    <% } %>
	                	
	                </TR>
                <% } %>
                
                <% } %>
               <%if(lsxBean.getChoPhepChuyenhangdat()){%>
	                 <TR>
	                    <TD class="plainlabel" valign="top">Chuyển hàng không đạt  </TD>
	                    <TD colspan="4" class="plainlabel" valign="top">
	                    	<%if(lsxBean.getTrangthaiSP().equals("-1")) { %>
	                    		<input type="checkbox"  name="chuyenhangkhongdat" id="chuyenhangkhongdat" style="text-align: right;" value="-1" checked="checked" />
	                    	<%}else{ %>
	                    		<input type="checkbox"  name="chuyenhangkhongdat" id="chuyenhangkhongdat" style="text-align: right;" value="-1" />
	                    	<%} %>
	                    </TD>
	                </TR>
                <%} %>
               <TR>
                    <TD class="plainlabel" valign="top">Tổng SL chuyển </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="text"  name="tongSLchuyen" id="tongSLchuyen" value="<%= lsxBean.getTongSLYC() %>"/>
                    </TD>
                </TR>
               
               <TR>
                    <TD class="plainlabel" valign="top">Ghi chú </TD>
                    <TD colspan="4" class="plainlabel" valign="top">
                    	<input type="text"  name="ghichu" value="<%= lsxBean.getGhichu() %>"/>
                    </TD>
                </TR>
               <tr  >
                        <td class="plainlabel">Chuyển hàng sản xuất </td>
                        <td class="plainlabel" colspan="4" >
                        	<%if(lsxBean.getIsChuyenHangSX().equals("-1")) { %>
                        		<input type="checkbox" name="IsChuyenHangSX" value="-1" checked="checked">
                        	<%} else { %>
                        		<input type="checkbox" name="IsChuyenHangSX" value="-1">
                        	<%} %>
                         </td>
                    </tr>  
            </TABLE>
			<hr />
			
			<table cellpadding="0px" cellspacing="1px" width="100%">
			<tr class="tbheader">
				<th align="center" width="15%">Mã sản phẩm</th>
				<th align="center" width="40%">Tên sản phẩm</th>
				<th align="center" width="10%">Đơn vị</th>
			 <th align="center" width="10%"> Tồn hiện tại</th>
				<th align="center" width="10%">Số lượng YC</th>
				<th align="center" width="15%">Số lượng chuyển</th>
				<th align="center" width="15%">Chọn số lô</th>
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
						<td>
						<INPUT id = "isreload" name="isreload" type="hidden"  value="0" style="width:100%">
							<input type="hidden" name="idsp" value="<%= yeucau.getId() %>" > 
							<input type="text" name="masp" value="<%= yeucau.getMa() %>" style="width: 100%"   onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
						</td>
						<td> <input type="text" name="tensp" value="<%= yeucau.getTen() %>" style="width: 100%" readonly="readonly"> </td>
						
						<td> <input type="text" name="donvi" value="<%= yeucau.getDonViTinh() %>" style="width: 100%; text-align: right;" readonly="readonly"> </td>
						
						<td> <input type="text" name="tonhientai" value="<%= yeucau.getTonhientai() %>" style="width: 100%; text-align: right;" readonly="readonly"> </td>
						
						<td> <input type="text" name="soluongyeucau" value="<%= yeucau.getSoluongYC() %>" style="width: 100%; text-align: right; " onkeypress="return keypress(event);" readonly="readonly" > </td>
						
						<td> <input type="text" name="soluongchuyen" readonly="readonly" value="<%= yeucau.getSoluongchuyen() %>" style="width: 100%; text-align: right; " onkeypress="return keypress(event);" > </td>
						<td>
							<a href="" id="<%= i+ "pobup"%>" rel="subcontent<%= i %>">
		           	 							<img alt="Vi tri luu kho" src="../images/vitriluu.png"></a>
           	 					<DIV  id="subcontent<%= i %>" style=" z-index=10; position:absolute; visibility: hidden; border: 9px solid #80CB9B;
			                             background-color: white; max-height :500px;width: 500px; padding: 1px; overflow: auto; ">
			                    <table width="100%" align="center">
			                        <tr>
			                        	
			                            <%if(lsxBean.getIsQuanlykhuvuc_khoxuat().equals("1")){ %>
				                             	<th width="10%">Khu vực kho</th>
				                             <%} %>
			                             <th width="20%">Số lô</th>
			                             <th width="20%">Ngày nhập kho  </th>
			                             <th width="20%">Số lượng tồn</th>
 										 <th width="20%" >Số lượng xuất</th>
			                        </tr>
			                        <%
			                        	List<ISpDetail> spConList = yeucau.getSpDetailList();
			                        	int stt = 1; 
			                        	if(spConList.size() > 0)
			                        	{
			                        		for(int sd = 0; sd < spConList.size(); sd ++)
			                        		{
			                        			ISpDetail spCon = spConList.get(sd);
			                        			
			                        			double soluongct =0;
			                        			try{
			                        				soluongct=Double.parseDouble(spCon.getSoluong().replaceAll(",",""));
			                        			}catch(Exception er){
			                        				
			                        			}
			                        			
			                        		%>
			                        			<tr>
			                        			<%if(lsxBean.getIsQuanlykhuvuc_khoxuat().equals("1")){ %>
			                        			<td> 
			                        				
			                        				<input name="<%= yeucau.getId() + "khuvuc_id" %>" 
						                            	type="hidden" style="width: 100%" value="<%= spCon.getKhuId() %>" readonly="readonly" /> 
						                            	
						                            	<input name="<%= yeucau.getId() + "khuvuc_ten" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getKhu() %>" readonly="readonly" /> 
						                            </td>
						                            <%} %>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "solo" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getSolo() %>" readonly="readonly" /> 
						                            </td>
						                            <td> 
						                            	<input name="<%= yeucau.getId() + "ngaynhapkho" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getNgaybatdau() %>" readonly="readonly" /> 
						                            </td>
						                            <td>
						                            	<input name="<%= yeucau.getId() + "soluongton" %>" 
						                            	type="text" style="width: 100%" value="<%= spCon.getSoluongton()%>"  />
						                            	</td>
						                             
						                            	<td>
						                            	<input name="<%= yeucau.getId() + "soluongxuat" %>" 
						                            		type="text" style="width: 100%" value="<%= formatter3.format(soluongct)%>" onkeypress="return keypress(event);" /> 
						                            	</td>
						                            	
						                           
						                        </tr>
			                        		<%  } 
			                        	} 
			                        	spConList.clear();
			                        %>
			                         
			                    </table>
			                     <div align="right"> 
			                   
			                     <a href="javascript:dropdowncontent.hidediv('subcontent<%= i %>');">Hoàn tất</a> 
			                     </div>
			                </DIV>
						</td>
					</tr>
					
				<% count++; } } %>
			 
			 	<% 
			 
			 	int j=count;
			 	int dem=count+5;
			 	
			 	
				for(  j = count; j < dem; j++)
				{ 
					 
					 
					%>
					
					<tr>
						<td>
							<INPUT id = "isreload" name="isreload" type="hidden"  value="0" style="width:100%">
							<input type="hidden" name="idsp" value="" > 
							<input type="text" name="masp" value="" style="width: 100%"   onkeyup="ajax_showOptions(this,'donmuahang',event)" AUTOCOMPLETE="off" > 
						</td>
						<td> <input type="text" name="tensp" value="" style="width: 100%" readonly="readonly"> </td>
						
						<td> <input type="text" name="donvi" value="" style="width: 100%; text-align: right;" readonly="readonly"> </td>
						
						<td> <input type="text" name="tonhientai" value="" style="width: 100%; text-align: right;" readonly="readonly"> </td>
						
						<td> <input type="text" name="soluongyeucau" value="" style="width: 100%; text-align: right; " readonly="readonly" onkeypress="return keypress(event);" > </td>
						<td> <input type="text" name="soluongchuyen" readonly="readonly" value="" style="width: 100%; text-align: right; " onkeypress="return keypress(event);" > </td>
						<td>
							<a href="" id="<%= j+ "pobup"%>" rel="subcontent<%= j %>">
		           	 							<img alt="Vi tri luu kho" src="../images/vitriluu.png"></a>
           	 					<DIV  id="subcontent<%= j %>" style=" z-index=10; position:absolute; visibility: hidden; border: 9px solid #80CB9B;
			                             background-color: white; max-height :500px;width: 500px; padding: 1px; overflow: auto; ">
			                    <table width="100%" align="center">
			                        <tr>
			                        	
			                            <%if(lsxBean.getIsQuanlykhuvuc_khoxuat().equals("1")){ %>
				                             	<th width="10%">Khu vực kho</th>
				                             <%} %>
			                             <th width="20%">Số lô</th>
			                             <th width="20%">Ngày nhập kho  </th>
			                             <th width="20%">Số lượng tồn</th>
 										 <th width="20%" >Số lượng xuất</th>
			                        </tr>
			                         
			                    </table>
			                     <div align="right"> 
			                   
			                     <a href="javascript:dropdowncontent.hidediv('subcontent<%=j%>');">Hoàn tất</a> 
			                     </div>
			                </DIV>
						</td>
					</tr>
					
				<% count++; }   %>
			 
			 
				
			</table>
				
            </div>
     </fieldset>	
    </div>
</div>

<script type="text/javascript">

	  replaces();

	<% 
		for(int i = 0; i < dem; i++)
		{
	%>
		   dropdowncontent.init('<%=i+ "pobup"%>', "left-top", 500, "click");
	<%} %>


</script>


<%
	try
	{
		lsxBean.DBclose(); 
		spList.clear();
		khonhapRs.close();
		
		if(nccnhapRs != null)
			nccnhapRs.close();
		if(nccxuatRs != null)
			nccxuatRs.close();
		
		 
		
		session.setAttribute("lsxBean", null) ;  ; 
		
	}
	catch(Exception er){ }

	} %>
</form>
</BODY>
</html>