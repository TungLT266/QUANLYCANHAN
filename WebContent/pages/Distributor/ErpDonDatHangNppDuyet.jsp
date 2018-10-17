<%@page import="geso.traphaco.distributor.beans.hoadontaichinh.imp.CapNhat_HDTC_Avat"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.dondathang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpDuyetddhNppList obj = (IErpDuyetddhNppList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<%-- <% ResultSet nppRs = obj.getKhRs(); %> --%>
<% ResultSet kbhRs = obj.getKbhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 
ResultSet kenhbanhang = obj.getKbhRs();
ResultSet khuvuc = obj.getKvRs();
ResultSet sanpham = obj.getSpRs();
ResultSet HTbanhang = obj.getHtbhRs();
%>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[6];
		
		String ldh = "";
		if( obj.getCapduyet().equals("CS") )
			ldh = "1";
		else
			ldh = "2";
		
		quyen = util.Getquyen("ErpDuyetddhNppSvl","&loaidonhang=" + ldh + "&capduyet=" + obj.getCapduyet(),userId);
%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>OneOne - Project</title>
	<META http-equiv="Content-Type" content="text/html; charset=utf-8">
	<META http-equiv="Content-Style-Type" content="text/css">
    <LINK rel="stylesheet" type="text/css" media="print" href="../css/impression.css">
    <LINK rel="stylesheet" href="../css/main.css" type="text/css">
    <LINK rel="stylesheet" href="../css/datepicker.css" type="text/css">
    <script language="javascript" src="../scripts/datepicker.js"></script>
   	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css"/>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery.min.js"></script>
  	<script type="text/javascript" src="../scripts/Timepicker/jquery-ui.min.js"></script>

	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	
	<script src="../scripts/ui/jquery.ui.core.js" type="text/javascript"></script>
	<script src="../scripts/ui/jquery.ui.widget.js" type="text/javascript"></script>
	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
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
   
   	<!-- <script type="text/javascript" src="../scripts/jquery-latest.js"></script>  -->
   	<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
	
  	<script type="text/javascript" src="..scripts/jquery-1.js"></script>
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
    </script>
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["ckParkForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function DuyetALL()
	 {
		 var donhangIds = document.getElementsByName("donhangIds");
		 var _dhIds = '';
		 for(var i=0; i < donhangIds.length; i++){
			 
			 _dhIds += donhangIds[i].value + ',';
		 }
		 
		 if(_dhIds == '' )
		 {
			 alert('Vui lòng chọn đơn hàng');
			 return;
		 }		
		 
		 
		 var r = confirm("Bạn chắc chắn muốn duyệt tất cả đơn hàng? ");
		 if (r == false) {		 
		     return;
		 }
		 else  //CHECK XEM CÓ KHÁCH HÀNG ĐẶC BIỆT NÀO KHÔNG
		 {
			 //CHECK XEM CO PHAI LA KHACH HANG DAC BIET HAY KHONG
			 var xmlhttp;
			 if (window.XMLHttpRequest)
			 {// code for IE7+, Firefox, Chrome, Opera, Safari
			    xmlhttp = new XMLHttpRequest();
			 }
			 else
			 {// code for IE6, IE5
			    xmlhttp = new ActiveXObject("Microsoft.XMLHTTP");
			 }
			 xmlhttp.onreadystatechange=function()
			 {
			   	if (xmlhttp.readyState == 4 && xmlhttp.status == 200)
			   	{
			   		//alert(xmlhttp.responseText);
			       	if( xmlhttp.responseText == '1')
			    	{
			       		if(!confirm("Đây là khách hàng đặc biệt. Vui lòng kiểm tra lại đầy đủ thông tin"))
			       		{
			       			return;
			       		}
			       		else
			       		{
			       			document.forms["ckParkForm"].action.value = "duyetALL";
			       		 	document.forms["ckParkForm"].submit();
			       		}
			    	}
			       	else
			       	{
			       		document.forms["ckParkForm"].action.value = "duyetALL";
			   		 	document.forms["ckParkForm"].submit();
			       	}
			   	}
			 }
			  
			 xmlhttp.open("GET","../../ErpDonhangNppETCUpdateSvl?type=checkDACBIET&dhIds=" + _dhIds, true);
			 xmlhttp.send();
		 }
		 
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].trangthai.value = "";
	    document.forms["ckParkForm"].khId.value = "";
	    document.forms["ckParkForm"].sodh.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	 function checkALL()
	 {
		 var chkALL = document.getElementById("chkALL");
		 var donhangIds = document.getElementsByName("donhangIds");
		 
		 if(chkALL.checked == true )
		 {
			 for(var i=0; i < donhangIds.length; i++){
				 donhangIds[i].checked = true;
			 }
		 }
		 else
		 {
			 for(var i=0; i < donhangIds.length; i++){
				 donhangIds[i].checked = false;
			 }
		 }
	 }
	 
	 function XacNhanXoa(dhId)
	 {
		 var r = confirm("Bạn chắc chắn mở duyệt đơn hàng ( " + dhId + " ) ");
		 if (r == false) {
		     return;
		 }
		 
		 //alert('Ly do xoa: ' + document.getElementById("lydoxoa" + dhId).value);
		 document.forms['ckParkForm'].lydoxoa.value = document.getElementById("lydoxoa" + dhId).value;
		 document.forms['ckParkForm'].dhId.value = dhId;
		 document.forms['ckParkForm'].action.value = 'moduyetSS';
		 document.forms['ckParkForm'].submit();
	 }
	 
	 function KhongDuyetSS( dhId )
	 {
		 var r = confirm("Bạn chắc chắn không duyệt đơn hàng ( " + dhId + " ) ");
		 if (r == false) {
		     return;
		 }
		 
		 //alert('Ly do xoa: ' + document.getElementById("lydoxoa" + dhId).value);
		 document.forms['ckParkForm'].lydoxoa.value = document.getElementById("lydokhongduyet" + dhId).value;
		 document.forms['ckParkForm'].dhId.value = dhId;
		 document.forms['ckParkForm'].action.value = 'khongduyet';
		 document.forms['ckParkForm'].submit();
	 }
	 
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
		$(document).ready(function()
		{
			$(".select2").select2();
		});
	</script>
	
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
	<style type="text/css">
		#dhtmltooltip
		{
			position: absolute;
			left: -300px;
			width: 150px;
			border: 1px solid black;
			padding: 2px;
			background-color: lightyellow;
			visibility: hidden;
			z-index: 100;
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
		th {
		cursor: pointer;
		}	
  	</style>
	
</head>
<body>
<form name="ckParkForm" method="post" action="../../ErpDuyetddhNppSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="capduyet" value='<%= obj.getCapduyet() %>'>
<input type="hidden" name="dhId" id="dhId"  >
<input type="hidden" name="lydoxoa" value="">
<input type="hidden" name="phanloai" value="<%= obj.getPhanloai() %>">
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Đơn bán hàng > Duyệt đơn hàng ( <%= obj.getCapduyet() %> )
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
                        <TD class="plainlabel" width="130px">Từ ngày</TD>
                        <TD class="plainlabel" width="250px" >
                            <input type="text" class="days" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    
                        <TD class="plainlabel" width="100px">Đến ngày</TD>
                        <TD class="plainlabel" colspan="3" >
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>
                    
                     <TR>
                     	<TD class="plainlabel" >Loại đơn hàng </TD>
						<TD class="plainlabel" colspan = "5">
							<select name="loaidonhang" class="select2" style="width: 200px" onchange="submitform();">
								<option value="">ALL</option>
								<% if(obj.getLoaidonhang().equals("0")) { %>
									<option value="0" selected="selected" >Bán cho ĐLPP</option>
								<% } else { %>
									<option value="0" >Bán cho ĐLPP</option>
								<% } %>
								<% if(obj.getLoaidonhang().equals("1")) { %>
									<option value="1" selected="selected" >ETC</option>
								<% } else { %>
									<option value="1" >ETC</option>
								<% } %>
								<% if(obj.getLoaidonhang().equals("2")) { %>
									<option value="2" selected="selected" >OTC</option>
								<% } else { %>
									<option value="2" >OTC</option>
								<% } %>
								<% if(obj.getLoaidonhang().equals("3")) { %>
									<option value="3" selected="selected" >Bán nội bộ</option>
								<% } else { %>
									<option value="3" >Bán nội bộ</option>
								<% } %>
							</select>
						</TD>
                     	
                     	<%-- <TD class="plainlabel" width="100px">Khách hàng</TD>
                        <TD class="plainlabel">
                            <select name = "khId" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value="">All</option>
	                        	<%
	                        		if(nppRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(nppRs.next())
	                        			{  
	                        			if( nppRs.getString("pk_seq").equals(obj.getKhTen())){ %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" selected="selected" ><%= nppRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nppRs.getString("pk_seq") %>" ><%= nppRs.getString("ten") %></option>
	                        		 <% } } nppRs.close();} catch(Exception ex){}
	                        		}
	                        	%>
	                    	</select>
                        </TD> --%>
                     	
                       
                     
                    </TR>  
                    <TR>
                    	<TD  class="plainlabel">Mã / tên đối tượng</TD>
						<TD  class="plainlabel" ><INPUT name="makhachhang" type="text" size="30" value = '<%= obj.getKhTen() %>' onChange = "submitform();"></TD>
						<TD  class="plainlabel">Số đơn hàng</TD>
						<TD  class="plainlabel" colspan="3" ><INPUT name="sodh" type="text" size="30" value = '<%= obj.getSodh()%>' onChange = "submitform();"></TD>	
					</TR>  
					 <TR>
                     <TD  class="plainlabel">Sản phẩm</TD>
							<TD  class="plainlabel"  >
							<select name="spid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(sanpham!=null){ while (sanpham.next()){ %>
								<option value="<%=sanpham.getString("pk_seq")%>" <%if(sanpham.getString("pk_seq").equals(obj.getSpId())){%> selected="selected" <%}%>><%=sanpham.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							<TD  class="plainlabel">Khu vực</TD>
							<TD  class="plainlabel" >
							<select name="kvid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(khuvuc!=null){ while (khuvuc.next()){ %>
								<option value="<%=khuvuc.getString("pk_seq")%>" <%if(khuvuc.getString("pk_seq").equals(obj.getKvId())){%> selected="selected" <%}%>><%=khuvuc.getString("ten") %>  </option>
								<%} }%>
							</select>
							</TD>
							<TD  class="plainlabel">Người tạo</TD>
							<TD  class="plainlabel" >
								<input type="text" name="nguoitao" value="<%= obj.getNguoitao() %>" >
							</TD>
                    </TR>
                    <tr>
                       <TD  class="plainlabel">Hệ thống bán hàng</TD>
							<TD  class="plainlabel" >
							<select name="htbhid" class="select2"  style="width: 200px;" onchange="submitform();">
							
								<option value="">ALL</option>
								<%if(HTbanhang!=null){ while (HTbanhang.next()){ %>
								<option value="<%=HTbanhang.getString("pk_seq")%>" <%if(HTbanhang.getString("pk_seq").equals(obj.getHtbhId())){%> selected="selected" <%}%>><%=HTbanhang.getString("diengiai") %>  </option>
								<%} }%>
							</select>
							</TD>
                     	<TD class="plainlabel" >Kênh bán hàng</TD>
                        <TD class="plainlabel">
                            <select name = "kbhId" class="select2" style="width: 200px" onchange="submitform();" >
	                    		<option value="">ALL</option>
	                        	<%
	                        		if(kbhRs != null)
	                        		{
	                        			try
	                        			{
	                        			while(kbhRs.next())
	                        			{  
	                        			if( kbhRs.getString("pk_seq").equals(obj.getKbhId())){ %>
	                        				<option value="<%= kbhRs.getString("pk_seq") %>" selected="selected" ><%= kbhRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= kbhRs.getString("pk_seq") %>" ><%= kbhRs.getString("ten") %></option>
	                        		 <% } } kbhRs.close();} catch(Exception ex){ ex.printStackTrace(); }
	                        		}
	                        	%>
	                    	</select>
                        </TD>
                 		<TD  class="plainlabel">Người sửa</TD>
						<TD  class="plainlabel" >
							<input type="text" name="nguoisua" value="<%= obj.getNguoisua() %>" >
						</TD>
                    </tr>
                    <tr>
                        <td colspan="6" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <TABLE width="100%" border="0" cellpadding="0"  cellspacing="1" >

				<TR>
				  <TD height="100%" width="100%">
        	<fieldset>
            	<legend><span class="legendtitle"> Duyệt đơn hàng </span>&nbsp;&nbsp;
            	<%-- <%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                               <%} %> --%>
                </legend>
            	<TABLE id="table" width="100%" class="tabledetail sortable" border="0" cellspacing="1" cellpadding="4">
            		<thead>
					<TR class="tbheader">
	                    <TH align="center" style="width: 12%" >Mã chứng từ</TH>
	                    <TH align="center" style="width: 7%" >Ngày đặt</TH>
	                    <TH align="center" style="width: 15%" >Đối tượng</TH>
	                    <TH align="center" style="width: 8%" >Trạng thái</TH>
	                    <TH align="center" style="width: 7%" >Ngày tạo</TH>
	                    <TH align="center" style="width: 12%" >Người tạo</TH>
	                    <TH align="center" style="width: 7%" >Ngày sửa</TH>
	                    <TH align="center" style="width: 12%" >Người sửa</TH>
	                    <TH align="center" style="width: 10%" class="nosort" >Tác vụ</TH>
	                    <TH align="center" style="width: 11%" class="nosort" >Duyệt tất cả<br /> 
	                    	<input type="checkbox" id="chkALL" onchange="checkALL();" >  
	                    	<A href = "javascript:DuyetALL();"><IMG src="../images/Chot.png" title="Duyệt tất cả đơn hàng" border=0></A>
	                    </TH>
	                </TR>
	                </thead>
	                <tbody>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			while(nhapkhoRs.next())
                 			{  	
                 				String chuoiFORMAT = "";
                 				String trangthai = "Chờ duyệt <br />";
                 				
                 				//MÀU SẮC THEO CẤP ĐỘ GIAO HÀNG
								int CAPDOGIAOHANG = nhapkhoRs.getInt("CAPDOGIAOHANG");
                 				
                 				if(CAPDOGIAOHANG > 0 && CAPDOGIAOHANG <= 4)
									chuoiFORMAT = " style='color: red; font-weight: bold;'  ";
								else if(CAPDOGIAOHANG > 4 && CAPDOGIAOHANG <= 8)
									chuoiFORMAT = " style='color: orange; font-weight: bold; '  ";
								else if(CAPDOGIAOHANG > 8 && CAPDOGIAOHANG <= 24)
									chuoiFORMAT = " style='color: blue;'  ";
                 				
                 				if( obj.getCapduyet().equals("SS") ) {
                 					
	                 				if(nhapkhoRs.getString("SS_DUYET").equals("1"))
	                 					trangthai = " Đã duyệt <br /> ";
	                 					
	                 				if(nhapkhoRs.getString("CS_DUYET").equals("1"))
	                 					trangthai += " <i>CS Đã duyệt </i><br /> ";
                 				}	
                 				else if( obj.getCapduyet().equals("ASM") ) {
                 					
	                 				if(nhapkhoRs.getString("ASM_DUYET").equals("1"))
	                 					trangthai = " Đã duyệt <br /> ";
	                 					
	                 				if(nhapkhoRs.getString("CS_DUYET").equals("1"))
	                 					trangthai += " <i>CS Đã duyệt </i><br /> ";
                 				}	
                 				else
                 				{
                 					if(nhapkhoRs.getString("CS_DUYET").equals("1"))
                     					trangthai = "Đã duyệt <br /> ";
                     					
                    				if(obj.getLoaidonhang().equals("2"))
                    				{
   	                 					if(nhapkhoRs.getString("SS_DUYET").equals("0"))
   		                 					trangthai += " <i>( SS chưa duyệt )</i><br /> ";	
                    				}
                    				else if(obj.getLoaidonhang().equals("2"))
                    				{
   	                 					if(nhapkhoRs.getString("ASM_DUYET").equals("0"))
   		                 					trangthai += " <i>( ASM chưa duyệt )</i><br /> ";	
                    				}
                 				}
                 				
                 				String msg = nhapkhoRs.getString("tooltip") == null ? "" : nhapkhoRs.getString("tooltip");
                 				msg += nhapkhoRs.getString("tooltip_scheme") == null ? "" : nhapkhoRs.getString("tooltip_scheme") + "</table>";	
                 				
                 				boolean control_hienthi_duyet = false;
                 				boolean control_hienthi_khongduyet = false;
                 				boolean control_hienthi_moduyet = false;
                 				if( obj.getCapduyet().equals("SS") )
                 				{
                 					if(nhapkhoRs.getString("SS_DUYET").equals("1"))
                 						control_hienthi_moduyet = true;
                 					if(nhapkhoRs.getString("SS_DUYET").equals("0"))
                 					{
                 						control_hienthi_duyet = true;
                 						control_hienthi_khongduyet = true;
                 					}
                 				}
                 				else //CS
                 				{
                 					if(nhapkhoRs.getString("CS_DUYET").equals("1")) //CS không cho mở duyệt
                 						control_hienthi_moduyet = false;
                 					if(nhapkhoRs.getString("CS_DUYET").equals("0"))
                 					{
                 						control_hienthi_duyet = true;
                 						control_hienthi_khongduyet = true;
                 					}
                 				}
                 				
                 				
                 				/* String lydokhongduyet = nhapkhoRs.getString("lydokhongduyet") == null ? "" : nhapkhoRs.getString("lydokhongduyet");
                 				if( lydokhongduyet.trim().length() > 0 )
                 				{
                 					trangthai = " <i>SS không duyệt</i> ";
                 					msg = "<p style='color: red;' >" + lydokhongduyet + "</p>" + msg;
                 				} */
                 				
                 				if((m % 2 ) == 0) { 
                 					
                 					/* if(nhapkhoRs.getString("NOTE").trim().length() > 0)
                 						chuoiFORMAT = " style='color: red;' onMouseover=\"ddrivetip('" + nhapkhoRs.getString("NOTE") + "', 250)\"; onMouseout='hideddrivetip()' "; */
                 				%>
		                         	<TR class='tbdarkrow' <%= chuoiFORMAT %> >
		                        <%}else{ %>
		                          	<TR class='tblightrow' <%= chuoiFORMAT %> >
		                        <%} %>
		                    <TD align="center" onMouseover="ddrivetip('<%=msg %>', 700)"; onMouseout="hideddrivetip()"><%= nhapkhoRs.getString("machungtu") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("NGAYDONHANG") %></TD>
		                    <TD ><%= nhapkhoRs.getString("nppTEN") %></TD>  
		                    <TD align="center">
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= nhapkhoRs.getString("NGAYTAO") %></TD>	
		                    <TD align="center"><%= nhapkhoRs.getString("NGUOITAO") %></TD>
         					<TD align="center"><%= nhapkhoRs.getString("NGAYSUA") %></TD>
							<TD align="center"><%= nhapkhoRs.getString("NGUOISUA") %></TD>
									
		                    <TD align="center"> 
		                    
		                    	<%if(quyen[Utility.XEM] != 0) { %>
		                    		<A href = "../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&display=<%= nhapkhoRs.getString("PK_SEQ") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Display20.png" alt="Xem đơn đặt hàng" title="Xem đơn đặt hàng" border=0></A>
		                      	<% } %>
		                      	
		                      	<%if( quyen[Utility.HUYCHOT] != 0 && control_hienthi_moduyet ) { %>
			                      	
			                      	<A id='<%= nhapkhoRs.getString("PK_SEQ") %>' href="" rel="subcontent<%="xoadhid" + nhapkhoRs.getString("PK_SEQ") %>" >
	                           	 		<img src="../images/unChot.png" alt="Mở duyệt SS" title="Mở duyệt SS" width="20" height="20" border=0 >
	                           	 	</A>
	                           	 	
	                           	 	<DIV id="subcontent<%="xoadhid" + nhapkhoRs.getString("PK_SEQ") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
																		                             background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
					                    <table width="98%" align="center" cellspacing="1" >
					                        <tr  >
					                            <td align="center" style="font-size: 10pt">Lý do mở duyệt</td>
					                        </tr>
					                        <tr >
					                            <td align="center" style="font-size: 10pt">
					                            	<input type="text" id="<%="lydoxoa" + nhapkhoRs.getString("PK_SEQ") %>" style="width: 100%" />
					                            </td>
					                        </tr>
					                        
					                    </table>
					        					                    
					                    <div align="right">
					                    
					                     	<a href="javascript:XacNhanXoa('<%= nhapkhoRs.getString("PK_SEQ") %>');" style="color: red; font-weight: bold;">Xác nhận mở duyệt</a>
					                     
					                     	&nbsp;&nbsp;|&nbsp;&nbsp;
					                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="xoadhid" + nhapkhoRs.getString("PK_SEQ") %>')" style="font-weight: bold;" >Đóng lại</a>
					                    </div>
						            </DIV> 
						            <script type="text/javascript">
										dropdowncontent.init('<%= nhapkhoRs.getString("PK_SEQ") %>', "left-top", 300, "click");
									</script>
	                           	 	
                           	 	<% } %>
		                      
	                   			<% if(quyen[Utility.CHOT] != 0) { %>
	                   				
	                   					<% if( control_hienthi_duyet ) { %>
                             				<A href = "../../ErpDonhangNppETCUpdateSvl?userId=<%=userId%>&duyet=<%= nhapkhoRs.getString("PK_SEQ") %>&capduyet=<%= obj.getCapduyet() %>"><IMG src="../images/Chot.png" alt="Duyệt đơn đặt hàng" title="Duyệt đơn đặt hàng" border=0></A>
	                   					<% } %>
	                   					
	                   					<% if( control_hienthi_khongduyet ) { %>
	                   					<A id='khongduyet_<%= nhapkhoRs.getString("PK_SEQ") %>' href="" rel="subcontent<%="khongduyetdhid" + nhapkhoRs.getString("PK_SEQ") %>" >
		                           	 		<img src="../images/file_delete.png" alt="Không duyệt " title="Không duyệt " width="20" height="20" border=0 >
		                           	 	</A>
		                           	 	
		                           	 	<DIV id="subcontent<%="khongduyetdhid" + nhapkhoRs.getString("PK_SEQ") %>" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
																			                             background-color: #FFF; width: 400px; padding: 4px; z-index: 100000009; " >
						                    <table width="98%" align="center" cellspacing="1" >
						                        <tr  >
						                            <td align="center" style="font-size: 10pt">Lý do không duyệt</td>
						                        </tr>
						                        <tr >
						                            <td align="center" style="font-size: 10pt">
						                            	<input type="text" id="<%="lydokhongduyet" + nhapkhoRs.getString("PK_SEQ") %>" style="width: 100%" />
						                            </td>
						                        </tr>
						                        
						                    </table>
						        					                    
						                    <div align="right">
						                    
						                     	<a href="javascript:KhongDuyetSS('<%= nhapkhoRs.getString("PK_SEQ") %>');" style="color: red; font-weight: bold;">Xác nhận không duyệt</a>
						                     
						                     	&nbsp;&nbsp;|&nbsp;&nbsp;
						                     	<a href="javascript:dropdowncontent.hidediv('subcontent<%="khongduyetdhid" + nhapkhoRs.getString("PK_SEQ") %>')" style="font-weight: bold;" >Đóng lại</a>
						                    </div>
							            </DIV> 
							            <script type="text/javascript">
											dropdowncontent.init('khongduyet_<%= nhapkhoRs.getString("PK_SEQ") %>', "left-top", 300, "click");
										</script>
										<% } %>	
	                   				
	                   			<%  } %>
		                    </TD>
		                    
		                    <Td align="center" >
		                    	<% if(nhapkhoRs.getString("CS_DUYET").equals("0") && quyen[Utility.CHOT] != 0 ){ %>
		                    		<input type="checkbox" name="donhangIds" value="<%= nhapkhoRs.getString("PK_SEQ") %>" >
		                    	<% } %>

		                    </Td>
		                    
		                </TR>
                     <% m++; } nhapkhoRs.close(); } %>
                     </tbody>
                     <tfoot>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	 <% obj.setNextSplittings(); %>
								 <script type="text/javascript" src="../scripts/phanTrang.js"></script>
								<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
								<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

							 	<%if(obj.getNxtApprSplitting() >1) {%>
									<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, 1, 'view')"> &nbsp;
								<%}else {%>
									<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
									<%} %>
								<% if(obj.getNxtApprSplitting() > 1){ %>
									<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) -1, 'view')"> &nbsp;
								<%}else{ %>
									<img alt="Trang Truoc" src="../images/prev_d.gif" > &nbsp;
								<%} %>
								
								<%
									int[] listPage = obj.getNextSplittings();
									for(int i = 0; i < listPage.length; i++){
								%>
								
								<% 
								
								System.out.println("Current page:" + obj.getNxtApprSplitting());
								System.out.println("List page:" + listPage[i]);
								
								if(listPage[i] == obj.getNxtApprSplitting()){ %>
								
									<a  style="color:white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
								<%}else{ %>
									<a href="javascript:View(document.forms[0].name, <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
								<%} %>
									<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
								<%} %>
								
								<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, eval(document.forms[0].nxtApprSplitting.value) +1, 'view')"> &nbsp;
								<%}else{ %>
									&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
								<%} %>
								<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
							   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
						   		<%}else{ %>
						   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View(document.forms[0].name, -1, 'view')"> &nbsp;
						   		<%} %>
						</TD>
					 </tr>
					 </tfoot>
				</TABLE>
            </fieldset>
            </TD>
            </TR>
            </TABLE>
        </div>
    </div>  
</div>
</form>

<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		//sorter.asc = 'asc'; //ascending header class name
		sorter.desc = 'desc'; //descending header class name
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = false;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table", 100);
	</script> 

</body>
</html><%
try
{
	if(HTbanhang!=null)HTbanhang.close();
	if(kbhRs!=null)kbhRs.close();
	if(kenhbanhang!=null)kenhbanhang.close();
	if(khuvuc!=null)khuvuc.close();
	if(nhapkhoRs!=null)nhapkhoRs.close();
	if(obj!=null)
	{
		obj.DBclose();
		session.setAttribute("obj",null);
	}
}catch(Exception ex)
{
	ex.printStackTrace();
}
	
	}%>