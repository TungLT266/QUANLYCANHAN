<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.donmuahangtrongnuoc.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IErpDonmuahangList_Giay obj = (IErpDonmuahangList_Giay)session.getAttribute("obj"); %>
<% ResultSet dvthList = (ResultSet)obj.getDvthList(); %>
<% ResultSet dmhList = (ResultSet)obj.getDmhList(); %>
<% ResultSet nguoitaoRs = (ResultSet)obj.getNguoitaoRs(); %>
<% ResultSet rsSP= obj.getSpRs();%>  
<% ResultSet lspList = (ResultSet)obj.getLoaisanpham(); 


	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpDonmuahangtrongnuocList_GiaySvl","&loai="+obj.getLoai(),userId);

 NumberFormat formatter = new DecimalFormat("#,###,###.##");  %>
<% obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<TITLE>TraphacoHYERP - Project</TITLE>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
   
    <script type="text/javascript" src="../scripts/jquery.js"></script>
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<LINK rel="stylesheet" type="text/css" href="../css/style.css" />
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 300px;
			border: 1px solid black;
			padding: 5px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
			font-size: 1.2em;
			/*Remove below line to remove shadow. Below line should always appear last within this CSS*/
			filter: progid:DXImageTransform.Microsoft.Shadow(color=gray,direction=135);
		}	
		#dhtmlpointer
		{
			position:absolute;
			left: -300px;
			z-index: 101;
			visibility: hidden;
		}
		
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
	font-size: 1.2em;
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
		 $('.addspeech').speechbubble();});
</script>
  	
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.datepicker.js"
		type="text/javascript"></script>
			
	<script type="text/javascript">
		$(document).ready(function() {		
				$( ".days" ).datepicker({			    
						changeMonth: true,
						changeYear: true				
				});            
	        }); 		
			
	</script>
   
   
  	<link media="screen" rel="stylesheet" href="../css/colorbox.css">
<script src="../scripts/colorBox/jquery.colorbox.js"></script>
  	
  	<script type="text/javascript" src="../scripts/ajax.js"></script>
<!-- <script type="text/javascript" src="../scripts/erp-spList.js"></script> -->

<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
  	<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

<!-- <script type="text/javascript" src="..scripts/jquery-1.js"></script>
 <script type="text/javascript" src="../scripts/general.js"></script> -->
	<link type="text/css" rel="stylesheet" href="../css/mybutton.css">
	
    <script type="text/javascript">
    
        $(document).ready(function(){
            $(".button").hover(function(){
                $(".button img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button2").hover(function(){
                $(".button2 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
		$(document).ready(function(){
            $(".button3").hover(function(){
                $(".button3 img")
                .animate({top:"-10px"}, 200).animate({top:"-4px"}, 200) // first jump
                .animate({top:"-7px"}, 100).animate({top:"-4px"}, 100) // second jump
                .animate({top:"-6px"}, 100).animate({top:"-4px"}, 100); // the last jump
            });
        }); 
    </script>
    <SCRIPT language="javascript" type="text/javascript">
    
    function replaces()
	{
    	
		var tensanpham = document.getElementById("tensanpham").value;
	
		var pk_seq="",ma="";
		if(tensanpham.length>0)
		{
				var tem=tensanpham;
				if(parseInt(tem.indexOf(" [ ")) > 0)
				{
					console.log("vao ne:"+tensanpham);
					pk_seq = tem.substring(0, parseInt(tem.indexOf(" -- ")));
					console.log("tem 0 "+pk_seq);
					tem = tem.substr(parseInt(tem.indexOf(" -- ")) + 3);
					console.log("tem 1 "+tem);
					ma = tem.substring(1, tem.indexOf(" [ ") );
					console.log("tem 2 "+ma + "  "+tem);
					document.getElementById("mactsp").value=ma;
						
					tem = tem.substr(parseInt(tem.indexOf(" [ ")) + 3);
					
					tensanpham = tem.substring(0, tem.indexOf(" ]") );
					console.log("vao "+tensanpham);
					document.getElementById("tensanpham").value=tensanpham;
					
					if(tensanpham.value != '')
					{
					    document.forms["erpDmhForm"].submit();
					}
				}
			}
	
		setTimeout(replaces, 500);
	}
    
	 function confirmLogout()
	 {
	    if(confirm("Bạn có muốn đăng xuất?"))
	    {
			top.location.href = "home.jsp";
	    }
	    return
	 }
	 function submitform()
	 {   
	    document.forms["erpDmhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpDmhForm"].action.value = "Tao moi";
	    document.forms["erpDmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].dvth.value = "";
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    /* document.forms["erpDmhForm"].loaisanpham.value = ""; */
	    document.forms["erpDmhForm"].trangthai.value = "";
	    document.forms["erpDmhForm"].ncc.value = "";
	    document.forms["erpDmhForm"].loaiDonMuaHang.value = "";
	    document.forms["erpDmhForm"].sodonmuahang.value = "";
	    document.forms["erpDmhForm"].nguoitao.value = "";
	    document.forms["erpDmhForm"].loaihh.value = "";
	    document.forms["erpDmhForm"].sothamchieu.value = "";
//	    document.forms["erpDmhForm"].mactsp.value = "";
	    document.forms["erpDmhForm"].tensanpham.value = "";
	    document.forms["erpDmhForm"].sanphamct.value = "";
	    document.forms["erpDmhForm"].submit();
	 } 
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
		     $(document).ready(function() { $("select").select2();  });
		     
	</script>
	
	<style>
		select {
			width: 200px;
		}
	</style>


</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpDonmuahangtrongnuocList_GiaySvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="loai" value="<%= obj.getLoai() %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Mua hàng trong nước > Đơn mua hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="150px">Từ ngày </TD>
                        <TD class="plainlabel" width="210px">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" width="150px" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Đơn vị thực hiện </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="dvth" onchange="submitform()" style="width: 200px;">
                            	<option value=""></option>
                            	<%
	                        		if(dvthList != null)
	                        		{
	                        			while(dvthList.next())
	                        			{  
	                        			if( dvthList.getString("pk_seq").equals(obj.getDvthId())){ %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
	                        		 <% } } dvthList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                        <TD class="plainlabel" valign="middle">Mã / Tên nhà cung cấp </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="ncc" value="<%= obj.getNCC() %>" onchange="submitform()">
                        </TD>                        
                    </TR>
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Loại hàng hóa </TD>
                        <TD class="plainlabel" valign="middle">
							<select name="loaihh" id="loaihh" onChange="submitform();" style="width: 200px;">
								<option value=""></option>
									<% if(obj.getLoaihanghoa().equals("1")) { %>
										<option value="1" selected="selected">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3">Công cụ dụng cụ</option>
									<% } else if(obj.getLoaihanghoa().equals("2")){ %>
										<option value="2"  selected="selected">Chi phí dịch vụ</option>
										<option value="1">Tài sản cố định</option>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="3">Công cụ dụng cụ</option>
									<% } else  if(obj.getLoaihanghoa().equals("0")){  %> 
										<option value="0" selected="selected">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3">Công cụ dụng cụ</option>
									<% } else if(obj.getLoaihanghoa().equals("3")){%>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3"  selected="selected">Công cụ dụng cụ</option>
									<%} else {%>
										<option value="0">Sản phẩm nhập kho</option>
										<option value="1">Tài sản cố định</option>
										<option value="2">Chi phí dịch vụ</option>
										<option value="3" >Công cụ dụng cụ</option>									
									
									<%} %>

							</select>
                        </TD>
                        <%-- <TD class="plainlabel" valign="middle" >Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="loaisanpham" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(lspList != null)
	                        		{
	                        			while(lspList.next())
	                        			{  
	                        			if( lspList.getString("pk_seq").equals(obj.getLoaisanphamid())){ %>
	                        				<option value="<%= lspList.getString("pk_seq") %>" selected="selected" ><%= lspList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= lspList.getString("pk_seq") %>" ><%= lspList.getString("ten") %></option>
	                        		 <% } } lspList.close();
	                        		}
	                        	%>
                            </select>
                        </TD> --%>
                        <TD class = "plainlabel" valign = "middle">Loại đơn mua hàng</TD>
                           	<TD class = "plainlabel" valign = "middle" cosplan = "3">
                            	<select name = "loaiDonMuaHang" onchange = "submitform()" style="width: 200px;">   
                            			<option value = ""></option>                            			                            		
                            		<% if (obj.getLoaiDonMuaHang().equals("0")) { %>
                            			<option value = "0" selected = "selected"> Đơn mua hàng </option>
                            		<% } else { %>
                            			<option value = "0"> Đơn mua hàng </option>
                            		<% } %>
                            		<% if (obj.getLoaiDonMuaHang().equals("1")) { %>
                            			<option value = "1" selected = "selected"> Đơn gia công </option>
                            		<% } else {%>
                            			<option value = "1"> Đơn gia công </option>
                            		<% } %>
                           		
                            		
										
                            	</select>                                                                          
                        </TD>                      
                    </TR> 
                    
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="trangthai" onchange="submitform()" style="width: 200px;">
                            	<option value=""></option>                            	
                            	  <% if(obj.getTrangthai().equals("5")) { %>
                            		<option value="5" selected="selected" >Chưa chốt</option>
                            	<% } else { %>
                            		<option value="5" >Chưa chốt</option>
                            	 <% }  %>
                            	 
                            	<% if(obj.getTrangthai().equals("0")) { %>
                            		<option value="0" selected="selected" >Chưa duyệt</option>
                            	<% } else { %> 	
                            		<option value="0" >Chưa duyệt</option>
                            	<%  } %>
                            	<% if(obj.getTrangthai().equals("-1")) { %>
                            		<option value="-1" selected="selected" >Đã duyệt</option>
                            	<% } else { %> 
                            		<option value="-1" >Đã duyệt</option>
                            	<%  } %>
                            	
                            	 <% if(obj.getTrangthai().equals("-2")) { %>
                            		<option value="-2" selected="selected" >Đã có hóa đơn</option>
                            	<% } else { %>
                            		<option value="-2" >Đã có hóa đơn</option>
                            	 <% }  %>
                            	 <% if(obj.getTrangthai().equals("2")) { %>
                            		<option value="2" selected="selected" >Hoàn tất</option>
                            	<% } else { %>
                            		<option value="2" >Hoàn tất</option>
                            	 <% }  %>
                            	  <% if(obj.getTrangthai().equals("3")) { %>
                            		<option value="3" selected="selected" >Đã xóa</option>
                            	<% } else { %>
                            		<option value="3" >Đã xóa</option>
                            	 <% }  %>
                            	 <% if(obj.getTrangthai().equals("4")) { %>
                            		<option value="4" selected="selected" >Đã hủy</option>
                            	<% } else { %>
                            		<option value="4" >Đã hủy</option>
                            	 <% } %>                            	                             	                            	                             	
                            </select>
                           </TD>
                           <TD class="plainlabel" width="10%">Sản phẩm </TD>
                        	<TD class="plainlabel" valign="top"  colspan="4">
			                    	<select name="sanphamct" id="sanphamct" onchange="submitform();"  style="width: 200px;">
			                    		<option value=""> </option>
			                        	<%
			                        		if(rsSP != null)
			                        		{
			                        			try
			                        			{
			                        			while(rsSP.next())
			                        			{  
			                        			if( rsSP.getString("pk_seq").equals(obj.getSanphamct())){ %>
			                        				<option value="<%= rsSP.getString("pk_seq") %>" selected="selected" ><%=rsSP.getString("ma") +" -- "+  rsSP.getString("ten") %></option>
			                        			<%}else { %>
			                        				<option value="<%= rsSP.getString("pk_seq") %>" ><%=rsSP.getString("ma") +"--"+  rsSP.getString("ten") %></option>
			                        		 <% } } rsSP.close();} catch(Exception ex){}
			                        		}
			                        	%>
			                        </select>
			                    </TD>    
                                 
                        </TR>
                        <%-- <TD class="plainlabel" valign="middle">Tổng tiền </TD>
                        <TD class="plainlabel" valign="middle" >
                            <input type="text" name="tongtien" value="<%= obj.getTongtiensauVat() %>" onchange="submitform()">
                        </TD> --%>                        
                       
                    <TR>
                        <TD class="plainlabel" valign="middle" >Người tạo </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="nguoitao" onchange="submitform()"  style="width: 200px;">
                            	<option value=""></option>
                            	<%
	                        		if(nguoitaoRs != null)
	                        		{
	                        			while(nguoitaoRs.next())
	                        			{  
	                        			if( nguoitaoRs.getString("pk_seq").equals(obj.getNguoitaoIds())){ %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" selected="selected" ><%= nguoitaoRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nguoitaoRs.getString("pk_seq") %>" ><%= nguoitaoRs.getString("ten") %></option>
	                        		 <% } } nguoitaoRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>    
		                <TD class="plainlabel" valign="middle"> Số đề nghị mua hàng </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                            <input type="text" name="sothamchieu" value="<%= obj.getsothamchieu() %>" onchange="submitform()">
                        </TD>          
						                                          
                    </TR>   
                    <TR>
                        <TD class="plainlabel" valign="middle">Số đơn mua hàng </TD>
                        <TD class="plainlabel" valign="middle" >
                            <input type="text" name="sodonmuahang" value="<%= obj.getSodonmuahang() %>" onchange="submitform()">
                        </TD>   
                        
                        <td class="plainlabel">Số items
                        </td>
                        <td class="plainlabel" >
							<input type="text" name="soItems" value="<%= obj.getSoItems() %>" onchange="submitform()">
                        </td>                   
                                       
                    </TR>
						
                    <%-- <TR>
                     
                        <TD class="plainlabel" valign="middle">Mã,Tên sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                            <input type="text" name="tensanpham"  id="tensanpham" value="<%= obj.getTensanpham() %>" onchange="replaces()" >
                            <input type="hidden" name="mactsp"  id="mactsp" value="<%= obj.getTensanpham() %>"  >
                        </TD>   
                                      
                                       
                    </TR> --%>
                    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                </TABLE>  
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Đơn mua hàng </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0 && !obj.getLoai().equals("2")){  // Mua hàng nhập khẩu & trong nước mới cho tạo mới%>
            	
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
					 	<!-- <TH align="center" width="4%">Số đề nghị mua hàng</TH> -->
	                    <TH align="center" width="7%">Mã ĐMH</TH>
	                     <TH align="center" width="10%">Sản phẩm</TH>
	                    <TH align="center" width="7%">Ngày</TH>
	                    <TH align="center" width="7%">ĐVTH</TH>
	                    <%if( !obj.getLoai().equals("0")) {%>
	                    <TH align="center" width="12%">Nhà cung cấp</TH>
	                    <%} %>
	                    <TH align="center" width="7%">Trạng thái</TH>
	                    <TH align="center" width="7%">Tổng tiền</TH>
	                    <TH align="center" width="7%">Tổng lượng</TH>
	                    <TH align="center" width="7%" style="display:none;">PO gốc</TH>
	                    <TH align="center" width="7%">Ngày tạo</TH>
	                    <TH align="center" width="7%"> Người tạo </TH>
	                    <TH align="center" width="7%"> Ngày sửa </TH>
	                    <TH align="center" width="7%"> Người sửa </TH>
	                    <TH align="center" width="7%"  style="display:none;"> Đề nghị tạm ứng </TH>
	                    <TH align="center" width="8%">Tác vụ</TH>
	                </TR>
					<%
                   		if(dmhList != null)
                   		{
                   			int m = 0;
                   			while(dmhList.next())
                   			{  		
                   				String style="";
                   				if(dmhList.getString("noibo").equals("1")){
                   					style="color: #006400;font-weight: bold; ";
                   				}
                   				String msg= dmhList.getString("tooltip")==null?"":dmhList.getString("tooltip");
                   				if((m % 2 ) == 0) { %>
		                         	<TR class='tbdarkrow' <%= dmhList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + dmhList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >
		                        <%}else{ %>
		                          	<TR class='tblightrow' <%= dmhList.getString("NOTE").trim().length() > 0 ? " style='color: red;' onMouseover=\"ddrivetip('" + dmhList.getString("NOTE") + "', 300)\"; onMouseout='hideddrivetip();' " : ""  %>  >
		                        <%} %>

								<TD align="center" onMouseover='ddrivetip("<%= msg %>" , 700)'
									onMouseout="hideddrivetip()"><%= dmhList.getString("SOCHUNGTU") %></TD>
									<TD align="center"><%= dmhList.getString("SANPHAMTEN") %></TD>
								<TD align="center"><%= dmhList.getString("ngaymua") %></TD>
				                    <TD align="center"><%= dmhList.getString("ten") %></TD>	
				                  <%if( !obj.getLoai().equals("0")) {%>
				                    <TD align="left"><%= dmhList.getString("nccTen") %></TD>
				                  <%} %>
				                    <%String tt = dmhList.getString("trangthai");
				                   		 if( !tt.equals("3")){ %>	
				                    <TD align="center">
				                    <%} else{ %>
				                    <TD align="center" style="color: red">
				                    	<%}
				                    		String duyet = dmhList.getString("DUYET");				                    	
				                    		String str = "";
				                    	
				                    		String dachot = dmhList.getString("dachot");
				                    		
				                    		String trangthai = "";
				                    		
				                    		
				                    		String hoantatnhanhang=  dmhList.getString("HOANTAT_NHANHANG").equals("1") &&  (trangthai.equals("1")|| trangthai.equals("0")) ? "(Hoàn tất nhận hàng)":"" ;
				                    		
				                    		int ktPO= dmhList.getInt("ktPO");
				                    	 
					                    		if(tt.equals("0"))
					                    		{
					                    				trangthai = "Chưa duyệt";
					                    				if(dachot.equals("1")){
					                    					trangthai = "Chưa duyệt(Đã chốt)";
					                    				}
					                    			
					                    		}
					                    		else
					                    		{
					                    			if(tt.equals("1"))
					                    				trangthai = "Đã duyệt"  ;
					                    			else
					                    				if(tt.equals("2"))
					                    					trangthai = "Hoàn tất";
					                    				else
					                    				{
					                    					if(tt.equals("3"))
					                    						trangthai = "Đã xóa";
					                    					else
					                    						trangthai = "Đã hủy";
					                    				}
					                    		}
				                    		 
				                    		if(str.trim().length() > 0)
				                    			trangthai = trangthai + " ( " + str + " ) ";
				                    		
				                    		if(ktPO > 0){
				                    			trangthai = trangthai + "(Đã có hóa đơn)";
				                    		}
				                    		
				                    	%>
				                    	<%= trangthai +hoantatnhanhang%>
				                    </TD>									                                    
				                    <TD align="right"><%= formatter.format(dmhList.getDouble("tongtienAvat")) + " " + dmhList.getString("tiente") + "" %></TD>
				                    <TD align="center"><%= formatter.format(dmhList.getDouble("tongluong")) %></TD>
				                    <TD align="center"  style="display:none;"><%= dmhList.getString("pogoc") %></TD>
				                    <TD align="center"><%= dmhList.getString("ngaytao") %></TD>
				                    <TD align="left"><%= dmhList.getString("nguoitao") %></TD>
				                    <TD align="center"><%= dmhList.getString("ngaysua") %></TD>	
				                    <TD align="left"><%= dmhList.getString("nguoisua") %></TD>		
				                     <TD align="left"  style="display:none;"><%= dmhList.getString("DENGHITAMUNG") %></TD>				
				                    <TD align="center">
				                    
				                    
				                    <% System.out.println(" trang thai jsp: "+ tt);  %>
				                <% if(tt.equals("0")){ %>
				                
				               			 <A href = "../../ErpDonmuahangtrongnuocUpdate_GiaySvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>&duyet=<%= duyet %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
				              			 <%if(quyen[0] != 0 ){ %>
				                		
		                               <A href = "../../ErpDonmuahangtrongnuocUpdate_GiaySvl?userId=<%=userId%>&update=<%= dmhList.getString("dmhId") %>&duyet=<%= duyet %>">
		                               		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
		                               
		                               <%} %>		                                
		                               
			                    	<%  
			                    		//if( !dmhList.getString("DUYET").equals("0") && dachot.equals("0") ){
			                    		if(dachot.equals("0") ){
			                    			%>
		                               <%if(quyen[3]!=0){ %>
			                                 <A id='chotphieu<%=dmhList.getString("dmhId")%>'
										      href=""><img
										      src="../images/Chot.png" alt="Chốt"
										      width="20" height="20" title="Chốt" 
										      border="0" onclick="if(!confirm('Bạn có muốn chốt đơn hàng này?')) {return false ;}else{ processing('<%="chotphieu"+dmhList.getString("dmhId")%>' , '../../ErpDonmuahangtrongnuocList_GiaySvl?userId=<%=userId%>&chot=<%= dmhList.getString("dmhId")%>&loaidh=<%= obj.getLoai() %>');}"  >
										    </A>
									    <%} %>
									    
		                            <%	} %>
		                            
                                    <%if(quyen[2]!=0){ %>
                                		<A href = "../../ErpDonmuahangtrongnuocList_GiaySvl?userId=<%=userId%>&delete=<%= dmhList.getString("dmhId") %>&loaidh=<%= obj.getLoai() %>"><img src="../images/Delete20.png" width="20" height="20" border=0 
                                					 alt="Xóa Quản lý mua hàng" title="Xóa Quản lý mua hàng" onclick="if(!confirm('Bạn có muốn xóa đơn mua hàng này?')) return false;"></A>&nbsp;
                                	<%} %>
                                	 <%if(quyen[3]!=0){ %>
	                                	<%if(dmhList.getString("DUYET").equals("0")) { %>
	                                		<A href = "../../ErpDonmuahangtrongnuocList_GiaySvl?userId=<%=userId%>&hoantat=<%= dmhList.getString("dmhId") %>&loaidh=<%= obj.getLoai() %>"><IMG src="../images/hoantat.gif" alt="Hoàn tất đơn mua hàng" title="Hoàn tất đơn mua hàng" border=0 onclick="if(!confirm('Bạn có muốn hoàn tất đơn mua hàng này?')) return false;" ></A>&nbsp;
	                                	<%} %>
                                	<%} %>
		                                	
		                         <% } else{ if(tt.equals("1")){ %>
		                         		
		                            	<A href = "../../ErpDonmuahangtrongnuocUpdate_GiaySvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>&duyet=<%= duyet %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                            <%if(obj.getLoai().equals("1")){ %>
		                            <%	String cohoadon = dmhList.getString("iscohoadon")==null?"0":dmhList.getString("iscohoadon");
		                            	if(!cohoadon.trim().equals("1")){ %>
		                               <%if(quyen[3]!=0){ %>
			                                 <A id='chotphieu<%=dmhList.getString("dmhId")%>'
										      href=""><img
										      src="../images/unChot.png" alt="Bỏ chốt" title="Bỏ chốt"
											  width="20" height="20" longdesc="Bỏ chốt"							
										      border="0" onclick="if(!confirm('Bạn có muốn bỏ chốt ghi nhận này?')) {return false ;}else{ processing('<%="chotphieu"+dmhList.getString("dmhId")%>' , '../../ErpDonmuahangtrongnuocList_GiaySvl?userId=<%=userId%>&unchot=<%= dmhList.getString("dmhId") %>&loaidh=<%= obj.getLoai() %>');}"  >
											</A>
									    <%} }}%>
                                	
                                	
                                	<% System.out.println(" duyet jsp: "+ dmhList.getString("DUYET"));  %>
                                	<%if(dmhList.getString("DUYET").equals("0")) { %>
                                		<A href = "../../ErpDonmuahangtrongnuocList_GiaySvl?userId=<%=userId%>&hoantat=<%= dmhList.getString("dmhId") %>&loaidh=<%= obj.getLoai() %>"> <IMG src="../images/hoantat.gif"  alt="Hoàn tất đơn mua hàng" title="Hoàn tất đơn mua hàng" border=0 onclick="if(!confirm('Bạn có muốn hoàn tất đơn mua hàng này?')) return false;"></A>&nbsp;
                                	<%} 
		                            	 
		                            	if(obj.getLoai().equals("1")&&tt.equals("1"))
									{
										int daxuathd = Integer.parseInt(dmhList.getString("daxuathd"));
										
										int isduocphanbo = Integer.parseInt(dmhList.getString("isduocphanbo"));
										
										if(daxuathd == 0 && isduocphanbo == 1 )
										{ %>			
																	
										<A href="../../ErpParkHoadontrongnuocSvl?userId=<%=userId%>&convert=<%= dmhList.getString("dmhId") %>&nccId=<%=dmhList.getString("NCCID") %>&poId=<%=dmhList.getString("dmhId")  %>">
										<IMG src="../images/Next.png" alt="Chuyển thành hóa đơn NCC" title="Chuyển thành hóa đơn NCC" border=0></A>&nbsp;&nbsp;
											
									<%	}
											
									} 		
		                         } else { 
		                         %>		                         
		                            	<A href = "../../ErpDonmuahangtrongnuocUpdate_GiaySvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>&duyet=<%= duyet %>">
		                            			<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                    			
		                    			<% if(obj.getUserId().equals("100211")){%>
		                    			 <A id='chotphieu<%= dmhList.getString("dmhId") %>'
											       href=""><img
											      src="../images/unChot.png" alt="Bỏ chốt" title="Bỏ chốt"
													width="20" height="20" longdesc="Bỏ chốt"							
											       border="0" onclick="if(!confirm('Bạn có muốn bỏ chốt ghi nhận này?')) {return false ;}else{ processing('<%="chotphieu"+dmhList.getString("dmhId")%>' , '../../ErpDonmuahangtrongnuocList_GiaySvl?userId=<%=userId%>&unchot=<%= dmhList.getString("dmhId") %>&loaidh=<%= obj.getLoai() %>');}"  >
												</A>
										<%} %>
										
										
										
										<%-- <%if(dmhList.getString("DUYET").equals("0")) { %>
                                		<A href = "../../ErpDonmuahangtrongnuocList_GiaySvl?userId=<%=userId%>&hoantat=<%= dmhList.getString("dmhId") %>&loaidh=<%= obj.getLoai() %>"> <IMG src="../images/hoantat.gif"  alt="Hoàn tất đơn mua hàng" title="Hoàn tất đơn mua hàng" border=0 onclick="if(!confirm('Bạn có muốn hoàn tất đơn mua hàng này?')) return false;"></A>&nbsp;
                                	   <%} %> --%>
										
										
										
		                            	<% 
		                            	} 								
		                          }%>
				                    </TD>
				                </TR>
		                     <% m++; } dmhList.close(); } %>
						
						<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="14" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpDmhForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpDmhForm', 'prev')"> &nbsp;
							<%}else{ %>
								<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
							<%} %>
							
							<%
								int[] listPage = obj.getNextSplittings();
								for(int i = 0; i < listPage.length; i++){
							%>
							
							<% 							
						
							if(listPage[i] == obj.getNxtApprSplitting()){ %>
							
								<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
							<%}else{ %>
								<a href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpDmhForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpDmhForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr>  
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>

</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
<script type="text/javascript">


	jQuery(function()
	{		
		$("#tensanpham").autocomplete("ErpSanPhamlist.jsp");
	});
	replaces();
</script>
</body>
</html>
<%
   obj.DBclose(); 
	}
%>