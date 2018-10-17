<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.xuatkho.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpGuiSMSTDVList obj = (IErpGuiSMSTDVList)session.getAttribute("obj"); %>
<% ResultSet nhapkhoRs =  obj.getDondathangRs(); %>
<% ResultSet nppRs = obj.getKhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
		int[] quyen = new  int[11];
		quyen = util.GetquyenNew("ErpThongkenhanhangNppSvl", "", userId);
		ResultSet nvbanhangIdRs = obj.getNvbanhangIdRs();
		ResultSet khachhangRs = obj.getKhachhangRs();
		ResultSet khuvucRs = obj.getKhuvucRs();
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
  	<script type="text/javascript" src="../scripts/phanTrang.js"></script>
	<script type="text/javascript" src="../scripts/jquery.min.1.7.js"></script>
	<link href="../css/jquery-ui.css" rel="stylesheet" type="text/css" />
	<script type="text/javascript" src="../scripts/cool_DHTML_tootip.js"></script>
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
    
    <!-- <script type="text/javascript" src="../scripts/jquery-latest.js"></script>  -->
   	<script type="text/javascript" src="../scripts/jquery.tablesorter.js"></script>
   	
    <SCRIPT language="javascript" type="text/javascript">
	 function submitform()
	 {   
	    document.forms["ckParkForm"].submit();
	 }
	 function taobaocao()
	 {
		 document.forms["ckParkForm"].action.value = "taobaocao";
		 document.forms["ckParkForm"].submit();
	 }
	 
	 function newform()
	 {   
		document.forms["ckParkForm"].action.value = "Tao moi";
	    document.forms["ckParkForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["ckParkForm"].tungay.value = "";
	    document.forms["ckParkForm"].denngay.value = "";
	    document.forms["ckParkForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["ckParkForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["ckParkForm"].msg.value);
	 }
	 
	 function duyetFAX()
	 {
	 	var trangthaiNEW = document.getElementsByName("fax_trangthai");
	 	var ttNEW = '';
	 	for( i = 0; i < trangthaiNEW.length; i++ )
	 	{
	 		if( trangthaiNEW.item(i).checked == true )
	 			ttNEW += trangthaiNEW.item(i).value + '-1,';
	 		else
	 			ttNEW += trangthaiNEW.item(i).value + '-0,';
	 	}
	 	
	 	//alert( 'TT NEW: ' + ttNEW );
	 	document.getElementById("trangthaiCAPNHAT").value = ttNEW;
	 	
	 	document.forms['ckParkForm'].action.value= 'changeTRANGTHAI';
	 	document.forms['ckParkForm'].submit();
	 }
	 
	 function duyetSMS()
	 {
	 	var trangthaiNEW = document.getElementsByName("sms_trangthai");
	 	var ttNEW = '';
	 	for( i = 0; i < trangthaiNEW.length; i++ )
	 	{
	 		if( trangthaiNEW.item(i).checked == true )
	 			ttNEW += trangthaiNEW.item(i).value + '-1,';
	 		else
	 			ttNEW += trangthaiNEW.item(i).value + '-0,';
	 	}
	 	
	 	//alert( 'TT NEW: ' + ttNEW );
	 	document.getElementById("trangthaiSMSCAPNHAT").value = ttNEW;
	 	
	 	document.forms['ckParkForm'].action.value= 'changeSMSTRANGTHAI';
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
<form name="ckParkForm" method="post" action="../../ErpThongkenhanhangNppSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<input type="hidden" name="nppId" value='<%= obj.getNppId() %>'>
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="trangthaiCAPNHAT" id='trangthaiCAPNHAT' value=''>
<input type="hidden" name="trangthaiSMSCAPNHAT" id='trangthaiSMSCAPNHAT' value=''>

<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	&nbsp;Quản lý bán hàng > Bán hàng > Báo cáo gửi hàng tỉnh
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
                    
                        <TD class="plainlabel" width="130px">Đến ngày</TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform();" />
                        </TD>
                    </TR>

                     <TR>
                     
                        <TD class="plainlabel" valign="middle">Trạng thái SMS</TD>
                        <TD class="plainlabel" valign="middle"  >
                           <select name="trangthai" onchange="submitform();"  >
								<% if (obj.getTrangthai().equals("0")){%>
								  	<option value="0" selected>Chờ xác nhận</option>
								  	<option value="1">Đã xác nhận</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getTrangthai().equals("1")) {%>
								 	<option value="0" >Chờ xác nhận</option>
								  	<option value="1" selected>Đã xác nhận</option>
								  	<option value="" >ALL</option>
								<%} else  {%>
								 	<option value="0" >Chờ xác nhận</option>
								  	<option value="1">Đã xác nhận</option>
								  	<option value="" selected >ALL</option>
							<%} %>
                           </select>
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Người tạo</TD>
                        <TD class="plainlabel">
	                    	<input type="text" name = "nguoitao"  value="<%= obj.getNguoitao() %>" onchange="submitform();" >
                        </TD> 
                        
                   </TR>                           
                   
                   <TR>
                     
                        <TD class="plainlabel" valign="middle">Mã số SMS</TD>
                        <TD class="plainlabel" valign="middle"  >
                           <input type="text" name = "masoSMS"  value="<%= obj.getMasoSMS() %>" onchange="submitform();" >
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Khu vực</TD>
                        <TD class="plainlabel">
	                    	<select name="khuvuc" class="select2"  style="width: 200px;" onchange="submitform();">
								<option value="">ALL</option>
								<%if(khuvucRs!=null){ while (khuvucRs.next()){ %>
								<option value="<%=khuvucRs.getString("pk_seq")%>" 
								<%if(khuvucRs.getString("pk_seq").equals(obj.getKhuvuc())){%> 
								selected="selected" <%}%>><%=khuvucRs.getString("ten") %>  </option>
								<%} khuvucRs.close(); }%>
							</select>
                        </TD> 
                        
                   </TR>
                   
                   <TR>
                     
                        <TD class="plainlabel" valign="middle">Mã chứng từ</TD>
                        <TD class="plainlabel" valign="middle"  >
                           <input type="text" name = "machungtu"  value="<%= obj.getMachungtu() %>" onchange="submitform();" >
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Khách hàng</TD>
                        <TD class="plainlabel">
	                    	<select name="khachhang" class="select2"  style="width: 200px;" onchange="submitform();">
								<option value="">ALL</option>
								<%if(khachhangRs!=null){ while (khachhangRs.next()){ %>
								<option value="<%=khachhangRs.getString("pk_seq")%>" 
								<%if(khachhangRs.getString("pk_seq").equals(obj.getKhachhang())){%> 
								selected="selected" <%}%>><%=khachhangRs.getString("ten") %>  </option>
								<%} khachhangRs.close();}%>
							</select>
                        </TD> 
                        
                   </TR>
                   
                   <TR>
                        <TD class="plainlabel" valign="middle"> Ngày giao</TD>
                        <TD class="plainlabel" valign="middle"  >
                           <input type="text" class="days" name = "ngaygiao"  value="<%= obj.getNgaygiao() %>" onchange="submitform();" >
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Ngày giao dự kiến</TD>
                        <TD class="plainlabel">
	                    	<input type="text" class="days" name = "ngaygiaodukien"  value="<%= obj.getNgaygiaodukien() %>" onchange="submitform();" >
                        </TD> 
                        
                   </TR>
                   
                    <TR>
                        <TD class="plainlabel" valign="middle"> Số lượng</TD>
                        <TD class="plainlabel" valign="middle"  >
                           <input type="text" name = "soluong"  value="<%= obj.getSoluong() %>" onchange="submitform();" >
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Ghi chú</TD>
                        <TD class="plainlabel">
	                    	<input type="text" name = "ghichu"  value="<%= obj.getGhichu() %>" onchange="submitform();" >
                        </TD> 
                        
                   </TR>
                   
                   <TR>
                        <TD class="plainlabel" valign="middle"> Chành xe</TD>
                        <TD class="plainlabel" valign="middle"  >
                           <input type="text" name = "chanhxe"  value="<%= obj.getChanhxe() %>" onchange="submitform();" >
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Nhân viên bán hàng</TD>
                        <TD class="plainlabel">
	                    	<select name="nvbanhangId" class="select2"  style="width: 200px;" onchange="submitform();">
								<option value="">ALL</option>
								<%if(nvbanhangIdRs!=null){ while (nvbanhangIdRs.next()){ %>
								<option value="<%=nvbanhangIdRs.getString("pk_seq")%>" 
								<%if(nvbanhangIdRs.getString("pk_seq").equals(obj.getNvbanhangId())){%> 
								selected="selected" <%}%>><%=nvbanhangIdRs.getString("ten") %>  </option>
								<%} nvbanhangIdRs.close();}%>
							</select>
                        </TD> 
                        
                   </TR>
                   <TR>
                        <TD class="plainlabel" valign="middle"> Ngày xác nhận SMS</TD>
                        <TD class="plainlabel" valign="middle"  >
                           <input type="text" name = "ngayxacnhanSMS"  value="<%= obj.getNgayxacnhanSMS() %>" onchange="submitform();" class="days">
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Ngày xác nhận FAX</TD>
                        <TD class="plainlabel">
	                    	<input type="text" name = "ngayxacnhanFax"  value="<%= obj.getNgayxacnhanFax() %>" onchange="submitform();" class="days" >
                        </TD> 
                        
                   </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle"> Xác nhân bởi SMS</TD>
                        <TD class="plainlabel" valign="middle"  >
                          <select name="xacnhanSMS" onchange="submitform();"  >
								<% if (obj.getXacnhanSMS().equals("0")){%>
								  	<option value="0" selected>Chờ xác nhận</option>
								  	<option value="1">Đã xác nhận</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getXacnhanSMS().equals("1")) {%>
								 	<option value="0" >Chờ xác nhận</option>
								  	<option value="1" selected>Đã xác nhận</option>
								  	<option value="" >ALL</option>
								<%} else  {%>
								 	<option value="0" >Chờ xác nhận</option>
								  	<option value="1">Đã xác nhận</option>
								  	<option value="" selected >ALL</option>
							<%} %>
                           </select>
                        </TD> 
                        
                        <TD class="plainlabel" width="100px">Xác nhận bỏi FAX</TD>
                        <TD class="plainlabel">
	                    	<select name="xacnhanFax" onchange="submitform();"  >
								<% if (obj.getXacnhanFax().equals("0")){%>
								  	<option value="0" selected>Chờ xác nhận</option>
								  	<option value="1">Đã xác nhận</option>
								  	<option value="" >ALL</option>
								<%}else if(obj.getXacnhanFax().equals("1")) {%>
								 	<option value="0" >Chờ xác nhận</option>
								  	<option value="1" selected>Đã xác nhận</option>
								  	<option value="" >ALL</option>
								<%} else  {%>
								 	<option value="0" >Chờ xác nhận</option>
								  	<option value="1">Đã xác nhận</option>
								  	<option value="" selected >ALL</option>
							<%} %>
                           </select>
                        </TD> 
                        
                   </TR>
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button" href="javascript:submitform();">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform();">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;
                           <a class="button2" href="javascript:taobaocao();">
                                <img style="top: -4px;" src="../images/button.png" alt="">Xuất EXCEL </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Báo cáo gửi hàng tỉnh </span>&nbsp;&nbsp; </legend>
            	<TABLE id="table" class="tabledetail sortable" width="100%" border="0" cellspacing="1" cellpadding="4">
            	<thead>
					<TR class="tbheader">
						<TH align="center" style="width: 3%" rowspan="2" >STT</TH>
	                    <TH align="center" style="width: 8%" >Mã số SMS</TH>
	                    <TH align="center" style="width: 8%" >Ngày lập</TH>
	                    <TH align="center" style="width: 8%" >Ngày dự kiến hàng đến</TH>
	                    <TH align="center" style="width: 8%" >Ngày NVBH xác nhận</TH>
	                    
	                    
	                    <TH width="10%">
	                    	<% if( quyen[Utility.SMS] != 0 ) { %>
							<A href="javascript: duyetSMS();">
								<IMG src="../images/Chot.png" width="25px;" title="Xác nhận FAX" alt="Xác nhận SMS" border="1" style="border-style: outset">
							</A>
							<% } %>
							 Xác nhận bởi SMS
						</TH>
						
	                    <TH width="10%">
	                    	<% if( quyen[Utility.FAX] != 0 ) { %>
							<A href="javascript: duyetFAX();">
								<IMG src="../images/Chot.png" width="25px;" title="Xác nhận FAX" alt="Xác nhận FAX" border="1" style="border-style: outset">
							</A>
							<% } %>
							Xác nhận bởi FAX
						</TH>
						
						<TH align="center" style="width: 9%" >SOS</TH>
	                    <TH align="center" style="width: 10%">TDV</TH>
	                    <TH align="center" style="width: 10%">SUP</TH>
	                    <TH align="center" style="width: 10%" >Chành xe</TH>
	                    <TH align="center" style="width: 5%" >Số lượng</TH>
	                </TR>
	                
	                <TR class="tbheader" >
	                	<TH align="center" colspan="2" >Đơn đặt hàng</TH>
	                    <TH align="center" >Ngày gửi</TH>
	                    <TH align="center" >Mã KH</TH>
	                    <TH align="center" colspan="4" >Khách hàng</TH>
	                    <TH align="center" >Ngày lập</TH>
	                    <TH align="center" colspan="2" >Tỉnh thành</TH>
	                </TR>
	                
	                </thead>
	                <tbody>
					<%
                 		if(nhapkhoRs != null)
                 		{
                 			int m = 0;
                 			String style = "color: red;";
                 			while(nhapkhoRs.next())
                 			{  	
                 				//màu xanh đã hoàn thành
                 				if( nhapkhoRs.getString("trangthaiSMS").equals("1") && nhapkhoRs.getString("trangthaiFAX").equals("1") )
                 					style = "color: blue;";
                 				else
                 					style = "color: red;";	
                 				
                 				if((m % 2 ) == 0) { 
                 				%>
		                         	<TR class='tbdarkrow' style='<%= style %>' >
		                        <%}else{ %>
		                          	<TR class='tbdarkrow' style='<%= style %>' >
		                        <%} %>
		                    <TD align="center" ><%= ( m + 1 ) %></TD>
		                    <TD align="center" ><%= nhapkhoRs.getString("machungtu") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("ngaygiaohang") %></TD>
		                    <TD ><%= nhapkhoRs.getString("ngaydukienHANGDEN") %></TD>  
		                    <TD align="center"><%= nhapkhoRs.getString("ngayNVBH_XACNHAN") == null ? "" : nhapkhoRs.getString("ngayNVBH_XACNHAN") %></TD>   	

         					<TD align="center">
         					<% if( quyen[Utility.SMS] != 0 ) { %>
         						<% if( nhapkhoRs.getString("ngaynhanSMS") != null ) { %>
         							<%= nhapkhoRs.getString("ngaynhanSMS").substring(0, 19) %>
         						<% } else { if( nhapkhoRs.getString("trangthaiSMS").equals("1") ) { %>
         							<input type="checkbox" name="sms_trangthai" checked="checked" value="<%= nhapkhoRs.getString("PK_SEQ") %>" >
         						<% } else { %>
         							<input type="checkbox" name="sms_trangthai" value="<%= nhapkhoRs.getString("PK_SEQ") %>" >
         						<% } } %>
         					<% } else { %>
         						<%= nhapkhoRs.getString("ngaynhanSMS") == null ? "" : nhapkhoRs.getString("ngaynhanSMS").substring(0, 19) %>
         					<% } %>
         					</TD>
         					
							<TD align="center">
							<% if( quyen[Utility.FAX] != 0 ) { %>
         						<% if( nhapkhoRs.getString("ngaynhanFAX") != null ) { %>
         							<%= nhapkhoRs.getString("ngaynhanFAX").substring(0, 19) %>
         						<% } else { if( nhapkhoRs.getString("trangthaiFAX").equals("1") ) { %>
         							<input type="checkbox" name="fax_trangthai" checked="checked" value="<%= nhapkhoRs.getString("PK_SEQ") %>" >
         						<% } else { %>
         							<input type="checkbox" name="fax_trangthai" value="<%= nhapkhoRs.getString("PK_SEQ") %>" >
         						<% } } %>
         					<% } else { %>
         						<%= nhapkhoRs.getString("ngaynhanFAX") == null ? "" : nhapkhoRs.getString("ngaynhanFAX").substring(0, 19) %>
         					<% } %>
         					</TD>
         					
         					<TD align="center">
         						<% if( nhapkhoRs.getInt("sos") > 0 ) { %>
         							<%= nhapkhoRs.getInt("sos") %>
         						<% } %>
         					</TD>  
         					<TD align="center"><%= nhapkhoRs.getString("tdv") %></TD>  
         					<TD align="center"><%= nhapkhoRs.getString("ss") %></TD>  
         					
         					<TD align="center" ><%= nhapkhoRs.getString("chanhxe") %></TD>
		                    <TD align="center"><%= nhapkhoRs.getString("soluong") %></TD>
									
		                </TR>
		                
		                <% 
		                	ResultSet chungtuRS = obj.getChungtuRs( nhapkhoRs.getString("PK_SEQ") );
		                    if( chungtuRS != null )
		                    {
		                    	while( chungtuRS.next() )
		                    	{ %>
		                    		
		                    		<TR class='tblightrow' >
		                    			<TD></TD>
					                	<TD align="center" colspan="2" ><%= chungtuRS.getString("machungtu") %></TD>
					                    <TD align="center" ><%= chungtuRS.getString("ngaylap") %></TD>
					                    <TD align="center" ><%= chungtuRS.getString("maKH") %></TD>
					                    <TD align="center" colspan="4" ><%= chungtuRS.getString("tenKH") %></TD>
					                    <TD align="center" ><%= chungtuRS.getString("ngaylap") %></TD>
					                    <TD align="center" colspan="2" ><%= chungtuRS.getString("tinhthanh") %></TD>
					                </TR>
		                    		
		                    	<% }
		                    	chungtuRS.close();
		                    }
		                %>
		                
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
        </div>
    </div>  
</div>
</form>
<script type="text/javascript" src="../scripts/script-table-sorter.js"></script>
	<script type="text/javascript">
		var sorter = new TINY.table.sorter("sorter");
		sorter.head = "head";
		sorter.asc = 'asc'; //ascending header class name
		sorter.desc = 'desc'; //descending header class name
		sorter.even = "tblightrow";
		sorter.odd = "tbdarkrow";
		sorter.evensel = "evenselected";
		sorter.oddsel = "oddselected";
		sorter.paginate = true;
		sorter.currentid = "currentpage";
		sorter.limitid = "pagelimit";
		sorter.init("table", 100);
	</script> 
</body>
</html><%
 if(obj!=null)
 {
	 obj.DBclose();
	 session.setAttribute("obj",null);
	
 }
	
	
	
	}%>