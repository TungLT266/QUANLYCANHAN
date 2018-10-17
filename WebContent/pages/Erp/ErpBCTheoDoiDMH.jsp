<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.donmuahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% IErpDonmuahangList obj = (IErpDonmuahangList)session.getAttribute("obj"); %>
<% ResultSet dvthRs = (ResultSet)obj.getDvthList(); %>
<% ResultSet nccRs = (ResultSet)obj.getNccRs(); %>
<% ResultSet nspRs = (ResultSet)obj.getNspRs(); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaisanpham(); %>
<% ResultSet nguoitaoRs = (ResultSet)obj.getNguoitaoRs(); %>
<% ResultSet spRs = (ResultSet)obj.getSanphamRs(); %>

<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings(); %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Phanam - Project</title>
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
  	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
   
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
		document.forms["erpDmhForm"].action.value = "";
	    document.forms["erpDmhForm"].submit();
	 }
	 
	 function taoBC()
	 {   
		 var tuNgay = document.getElementById("tungay");
		 var denNgay = document.getElementById("denngay");
		 if (tuNgay.value.trim().length == 0){
			 alert ("Vui lòng nhập ngày bắt đầu lấy báo cáo ");
			 return false;
		 }
		 if (denNgay.value.trim().length == 0){
			 alert("Vui lòng nhập ngày kết thức lấy báo cáo");
			 return false;
		 }
		 document.forms["erpDmhForm"].action.value = "taoBC";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 
	 function xemNH()
	 {   
		 document.forms["erpDmhForm"].action.value = "xemNH";
	     document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var nccIds = document.getElementsByName("nccIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = false;
			 }
		 }
	 }
	 function sellectNhomAll()
	 {
		 var chkAll = document.getElementById("chkNhAll");
		 var nccIds = document.getElementsByName("nspIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < nccIds.length; i++)
			 {
				 nccIds.item(i).checked = false;
			 }
		 }
	 }
	</SCRIPT>
	
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
	     $(document).ready(function() { $("select").select2(); });
	     
	 </script>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpBCDonmuahangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
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
        	Quản lý mua hàng > Nghiệp vụ khác > Báo cáo theo dõi đơn mua hàng
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm
            	
            </legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR>
                        <TD class="plainlabel" width="15%">Loại ngày </TD>
                        <TD class="plainlabel" >
                            <select name="loaingay" id= "loaingay" onchange="submitform();" style="width: 200px">
                            	<%-- <%if(obj.getLoaingay().equals("0")){ %>
                            	<option value = "0" selected="selected">Ngày mua hàng</option>
                            	<!-- <option value = "1">Ngày nhận hàng</option>
                            	<option value = "2">Ngày hóa đơn</option> -->
                            	<%}else if(obj.getLoaingay().equals("1")){ %>
                            	<option value = "0" >Ngày mua hàng</option>
                            	<!-- <option value = "1" selected="selected">Ngày nhận hàng</option>
                            	<option value = "2">Ngày hóa đơn</option> -->
                            	<%}else if(obj.getLoaingay().equals("2")){ %>
                            	<!--<option value = "0" >Ngày mua hàng</option>
                            	 <option value = "1" >Ngày nhận hàng</option>-->
                            	<option value = "2" selected="selected">Ngày hóa đơn</option> 
                            	<%} %> --%>
                            	
                            	<%if(obj.getLoaingay().equals("0")){ %>
                            	<option value = "0" selected="selected">Ngày mua hàng</option>
                            	<option value = "2" >Ngày hóa đơn</option> 
                            	<%}else if(obj.getLoaingay().equals("2")){ %>
                            	<option value = "0" >Ngày mua hàng</option>
                            	<option value = "2" selected="selected">Ngày hóa đơn</option> 
                            	<%} %>
                            	
                            </select>
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days"  
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR >
                        <TD class="plainlabel" valign="middle" >Đơn vị thực hiện </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="dvth" style="width: 300px;" onchange="submitform();">
                            	<option value=""></option>
                            	<%
	                        		if(dvthRs != null)
	                        		{
	                        			while(dvthRs.next())
	                        			{  
	                        			if( dvthRs.getString("pk_seq").equals(obj.getDvthId())){ %>
	                        				<option value="<%= dvthRs.getString("pk_seq") %>" selected="selected" ><%= dvthRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvthRs.getString("pk_seq") %>" ><%= dvthRs.getString("ten") %></option>
	                        		 <% } } dvthRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                   
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Trạng thái mua hàng </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="trangthai" style="width: 300px;">
                            	<option value=""></option>
                            	<option value="0">Chưa duyệt</option>
                            	<option value="1">Đã duyệt</option>
                            	<option value="-1">Đã nhận hàng</option>                            	
                            	<option value="2">Hoàn tất</option>
                            	<option value="3">Đã xóa</option>
                            	<option value="4">Đã hủy</option>
                            </select>
                        </TD>                        
                    </TR> 
                                                          
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Sản phẩm </TD>
                       <%--  <TD class="plainlabel" valign="middle" >
                            <select name="sanpham" style="width: 300px;">
                            	<option value=""></option>
                            	<%
	                        		if(spRs != null)
	                        		{
	                        			while(spRs.next())
	                        			{  
	                        			if( spRs.getString("pk_seq").equals(obj.getSanphamId())){ %>
	                        				<option value="<%= spRs.getString("pk_seq") %>" selected="selected" ><%= spRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= spRs.getString("pk_seq") %>" ><%= spRs.getString("ten") %></option>
	                        		 <% } } spRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>     --%>
                        
                        <TD class="plainlabel" valign="middle" ><select
									name="sanpham" id="sanpham" 
									style="width: 400px;" multiple>
										<option value=""></option>
										<%
											if (spRs != null) {
												while (spRs.next()) {
													if (obj.getSanphamId().contains(spRs.getString("pk_seq"))) {
										%>
											<option value="<%=spRs.getString("pk_seq")%>"
											selected="selected"><%=spRs.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=spRs.getString("pk_seq")%>"><%=spRs.getString("ma") +" - "+ spRs.getString("ten")%></option>
										<%
											}
												}
												spRs.close();
											}
										%>
								</select>
                           </TD>          
                        
                    </TR>
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" >Chọn nhà cung cấp </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                        <%--  <a href="" id="nccId" rel="subcontentNcc" >
	           	 							&nbsp; <img alt="Chọn nhà cung cấp" src="../images/vitriluu.png"></a>
	           	 		
                          <DIV id="subcontentNcc" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;background-color: white; width: 520px; height:200px; overflow:auto; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="70px">Mã NCC</th>
				                            <th width="300px">Tên NCC</th>
				                            <th width="50px" align="center">Chọn <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
				                        </tr>
		                            	<%
			                        		if(nccRs != null)
			                        		{
			                        			while(nccRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= nccRs.getString("nccMa") %>" readonly="readonly"></td>
			                        				<td><input style="width: 100%" value="<%= nccRs.getString("nccTen") %>" readonly="readonly"></td>
			                        				<td align="center">
			                        				<% if(obj.getNccIds().indexOf(nccRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="nccIds" checked="checked" value="<%= nccRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="nccIds" value="<%= nccRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } nccRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentNcc')">Hoàn tất</a>
				                     </div>
				            </DIV>                 
                         --%>
									 <select name="nccIds" id="nccIds" 
									style="width: 400px;" multiple>
										<option value=""></option>
										<%
											if (nccRs != null) {
												while (nccRs.next()) {
													if (obj.getNccIds().contains(nccRs.getString("pk_seq"))) {
										%>
											<option value="<%=nccRs.getString("pk_seq")%>"
											selected="selected"><%=nccRs.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=nccRs.getString("pk_seq")%>"><%=nccRs.getString("ma") +" - "+ nccRs.getString("ten")%></option>
										<%
											}
												}
												nccRs.close();
											}
										%>
								</select></TD>
                         
                         
                                    
                    </TR> 
                    <TR>
                    	<TD class="plainlabel" valign="middle" >Người mua hàng</TD>
                       <TD class="plainlabel" valign="middle" ><select
									name="nguoitaoids" id="nguoitaoids" 
									style="width: 400px;" multiple>
										<option value=""></option>
										<%
											if (nguoitaoRs != null) {
												while (nguoitaoRs.next()) {
													if (obj.getNguoitaoIds().contains(nguoitaoRs.getString("pk_seq"))) {
										%>
											<option value="<%=nguoitaoRs.getString("pk_seq")%>"
											selected="selected"><%=nguoitaoRs.getString("ten")%></option>
										<%
											} else {
										%>
										<option value="<%=nguoitaoRs.getString("pk_seq")%>"><%=nguoitaoRs.getString("ten")%></option>
										<%
											}
												}
												nguoitaoRs.close();
											}
										%>
								</select>
								
								</TD>  
                    </TR> 
                    
                    
                    
                    <tr>
                        <td class="plainlabel" style="display: none">Xem theo PIVOT </td>
                        <td class="plainlabel" style="display: none">
                        	<% if(obj.getPivot().equals("1")){ %>
                        		<input type="checkbox" name="pivot" value="1" checked="checked">
                        	<%} else { %>
                        		<input type="checkbox" name="pivot" value="1">
                        	 <% } %>
                         </td>
                    </tr>  
                                      
                     <tr>
                        <td class="plainlabel" colspan="3">
                           <a class="button" href="javascript:taoBC();" > 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                       
                    </tr> 
                    
                    </TABLE>                  
       </fieldset>            					                    
    	</div>
    	       
    </div>  
</div>
</form>
</body>
<script type="text/javascript">
	dropdowncontent.init('nccId', "right-bottom", 300, "click");
	dropdowncontent.init('nspId', "right-top", 300, "click");
</script>
</html>
<%obj.DBclose();%>