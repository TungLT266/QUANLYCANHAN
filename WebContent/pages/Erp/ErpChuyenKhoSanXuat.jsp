<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.lenhsanxuat.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@page import="java.sql.SQLException"%>
<% IErpChuyenkhoSXList obj = (IErpChuyenkhoSXList)session.getAttribute("obj"); %>
<% ResultSet lsxList = (ResultSet)obj.getLsxRs(); %>
 
<% ResultSet khonhanList = (ResultSet)obj.getKhonhanRs(); %>
<% ResultSet khochuyenList = (ResultSet)obj.getKhochuyenRs(); %>  
<% ResultSet nvList = (ResultSet)obj.getNhanvienRs(); %>
<% ResultSet nv2List = (ResultSet)obj.getNhanvien2Rs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<%  

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("ErpChuyenkhoSXSvl","",userId);

obj.setNextSplittings(); %>

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
	 function submitform()
	 {   
	    document.forms["erpLsxForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpLsxForm"].action.value = "Tao moi";
	    document.forms["erpLsxForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpLsxForm"].sophieu.value = "";
	    document.forms["erpLsxForm"].sophieuyeucau.value= "";
	    document.forms["erpLsxForm"].tungaySX.value = "";
	    document.forms["erpLsxForm"].denngaySX.value = "";
	    document.forms["erpLsxForm"].trangthai.value = "";
	    document.forms["erpLsxForm"].solenhsx.value = "";
	    document.forms["erpLsxForm"].khonhanId.value = "";
	    document.forms["erpLsxForm"].nguoitao.value = "";
	    document.forms["erpLsxForm"].nguoisua.value = "";
	    document.forms["erpLsxForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpLsxForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpLsxForm"].msg.value);
	 	}
	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("#nguoitao,#nguoisua,#khonhanId,#trangthai,#khochuyenId").select2(); });
     
 </script>
</head>
<body>
<form name="erpLsxForm" method="post" action="../../ErpChuyenkhoSXSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="task" value="<%=obj.gettask()%>" >
<input type="hidden" name="isnhanhang" value="<%=obj.getIsnhanHang()%>" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
 
        	Quản lý cung ứng > Quản lý tồn kho > <%=(obj.gettask().equals("LSX")?"Chuyển nguyên liệu ":"Chuyển kho")%> 
 
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
                       
                        <TD class="plainlabel" width="10%">Từ ngày </TD>
                        <TD class="plainlabel" width="200px">
                            <input type="text" class="days" onchange="submitform()"
                                   id="tungaySX" name="tungaySX" value="<%= obj.getTungayTao() %>" maxlength="10"  />
                        </TD>
                        
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" onchange="submitform()"
                                   id="denngaySX" name="denngaySX" value="<%= obj.getDenngayTao() %>" maxlength="10" />
                        </TD>
                    </TR>
                    
                     <TR>
                     	 
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onchange="submitform()" style="width: 200px">
								<% if (obj.getTrangthai().equals("1")){%>
								    <option value=""> </option>
								    <option value="0">Chưa chốt</option>
								  	<option value="1" selected>Chờ nhận hàng / Chờ xuất kho</option>								  	
								  	<option value="2">Đã nhận hàng / Đã xuất kho</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value="4">Đã hủy</option>
								  	
								  
								<% } else { if(obj.getTrangthai().equals("0")) { %>
									<option value="0" selected>Chưa chốt</option>
								 	<option value="1" >Chờ nhận hàng / Chờ xuất kho</option>
								 	<option value="2">Đã nhận hàng / Đã xuất kho</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value="4">Đã hủy</option>
								  	<option value=""> </option>
								  	
								<% } else { if(obj.getTrangthai().equals("3")) { %>	
									<option value="3" selected>Hoàn tất</option>																			  	
								    <option value="1" >Chờ nhận hàng / Chờ xuất kho</option>
								  	<option value="2">Đã nhận hàng / Đã xuất kho</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="4">Đã hủy</option>
								  	<option value=""> </option>					  		  	
								<%} else { if(obj.getTrangthai().equals("4")) { %>
									<option value="4" selected>Đã hủy</option>
									<option value="1" >Chờ nhận hàng / Chờ xuất kho</option>
									<option value="2">Đã nhận hàng / Đã xuất kho</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value=""> </option>
								
								<%} else { if(obj.getTrangthai().equals("2")) { %>
									<option value="4" >Đã hủy</option>
									<option value="1" >Chờ nhận hàng / Chờ xuất kho</option>
									<option value="2" selected>Đã nhận hàng / Đã xuất kho</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value=""> </option>
								
								<% } else { %>
									<option value="" selected="selected"> </option>
									<option value="1" >Chờ nhận hàng/Chờ xuất kho</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value="2">Đã nhận hàng/Đã xuất kho</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value="4">Đã hủy</option>
								  	
								<% } } } }}  %>
                           </select>
                        </TD> 
                        
                        <TD class="plainlabel" width="10%">Số phiếu </TD>
                        <TD class="plainlabel" width="200px">
                            <input type="text"  onchange="submitform()"
                                   id="sophieu" name=sophieu value="<%= obj.getSophieu() %>" maxlength="10"  />
                        </TD>                       
                    </TR> 
                    <TR>
                       
                      
                        <TD class="plainlabel" width="10%">Kho nhận </TD>
                        <TD class="plainlabel" width="200px">
                            <select name="khonhanId" id="khonhanId" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(khonhanList != null)
                        		{
                        			try
                        			{
                        			while(khonhanList.next())
                        			{  
                        			if( khonhanList.getString("pk_seq").equals(obj.getKhonhanid())){ %>
                        				<option value="<%= khonhanList.getString("pk_seq") %>" selected="selected" ><%= khonhanList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khonhanList.getString("pk_seq") %>" ><%= khonhanList.getString("ten") %></option>
                        		 <% } } khonhanList.close();} catch(SQLException ex){}
                        		}
                        	%>
                          </select>
                        </TD> 
                        <TD class="plainlabel" width="10%">Kho chuyển </TD>
                        <TD class="plainlabel" width="200px">
                            <select name="khochuyenId" id="khochuyenId" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(khochuyenList != null)
                        		{
                        			try
                        			{
                        			while(khochuyenList.next())
                        			{  
                        			if( khochuyenList.getString("pk_seq").equals(obj.getKhochuyenId())){ %>
                        				<option value="<%= khochuyenList.getString("pk_seq") %>" selected="selected" ><%= khochuyenList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khochuyenList.getString("pk_seq") %>" ><%= khochuyenList.getString("ten") %></option>
                        		 <% } } khochuyenList.close();} catch(SQLException ex){}
                        		}
                        	%>
                          </select>
                        </TD> 
                        
                         
                    </TR>
                      <TR>
                       
                        <TD class="plainlabel" width="10%">Người tạo </TD>
                        <TD class="plainlabel" width="200px">
                          <select name="nguoitao" id="nguoitao" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(nvList != null)
                        		{
                        			try
                        			{
                        			while(nvList.next())
                        			{  
                        			if( nvList.getString("pk_seq").equals(obj.getNguoitao())){ %>
                        				<option value="<%= nvList.getString("pk_seq") %>" selected="selected" ><%= nvList.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nvList.getString("pk_seq") %>" ><%= nvList.getString("ten") %></option>
                        		 <% } } nvList.close();} catch(SQLException ex){}
                        		}
                        	%>
                          </select>
                        </TD>
                        
                        <TD class="plainlabel" >Người sửa </TD>
                        <TD class="plainlabel">
                          <select name="nguoisua" id="nguoisua" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(nv2List != null)
                        		{
                        			try
                        			{
                        			while(nv2List.next())
                        			{  
                        			if( nv2List.getString("pk_seq").equals(obj.getNguoisua())){ %>
                        				<option value="<%= nv2List.getString("pk_seq") %>" selected="selected" ><%= nv2List.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= nv2List.getString("pk_seq") %>" ><%= nv2List.getString("ten") %></option>
                        		 <% } } nv2List.close();} catch(SQLException ex){}
                        		}
                        	%>
                          </select>
                        </TD>
                    </TR>   
                      <TR>
                         
                         
                        
                        <TD class="plainlabel" >Số phiếu yêu cầu </TD>
                        <TD class="plainlabel" >
                            <input type="text" onchange="submitform()"
                                   id="sophieuyeucau" name="sophieuyeucau" value="<%= obj.getsochungtu() %>" />
                        </TD>
                        <%if(obj.gettask().equals("LSX")){ %>
                         <TD class="plainlabel" >Số lệnh sản xuất </TD>
                        	<TD class="plainlabel">
                            <input type="text" onchange="submitform()"
                                   id="solenhsx" name="solenhsx" value="<%= obj.getLsxId() %>" />
                        </TD> 
                        <%}else { %>
                        <TD class="plainlabel" ></TD>
                        	<TD class="plainlabel">   
                        	 <input type="hidden" type="text" onchange="submitform()"
                                   id="solenhsx" name="solenhsx" value="<%= obj.getLsxId() %>" />                         
                        </TD> 
                        <%} %>
                        
                    </TR>
                    
                    <tr>
                        <td colspan="3" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                         <td colspan="3" class="plainlabel"></td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
        	
            	<legend><span class="legendtitle"> Yêu cầu xuất kho </span>&nbsp;&nbsp;
            <%if(quyen[0]!=0 && !obj.gettask().equals("LSX")){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="5%">Số phiếu</TH>
	                    <TH align="center" width="7%">Phiếu yêu cầu</TH>
	                    <TH align="center" width="7%">Ngày yêu cầu</TH>
	                    <!-- <TH align="center" width="14%">Nội dung xuất</TH> -->
	                    <TH align="center" width="14%">Kho nhận</TH> 
	                    <TH align="center" width="13%">Lý do</TH>
	                    <TH align="center" width="10%">Trạng thái</TH>
	                    <TH align="center" width="8%">Ngày tạo</TH>
	                    <TH align="center" width="10%"> Người tạo</TH>
	                    <TH align="center" width="8%">Ngày sửa</TH>
	                    <TH align="center" width="10%">Người sửa</TH>
	                    <TH align="center" width="5%" >Tác vụ</TH>
	                </TR>
					<%
                 		if(lsxList != null)
                 		{
                 			int m = 0;
                 			while(lsxList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= lsxList.getString("pk_seq") %></TD>
		                    <TD align="center"><%= lsxList.getString("yeucauchuyenkhoid") %></TD>
		                    <TD align="center"><%= lsxList.getString("ngaychuyen") %></TD>
		                    <%-- <TD align="left"><%= lsxList.getString("noidungxuat") %></TD> --%>		                    
		                    <TD align="left"><%= lsxList.getString("khonhan") %></TD>
		                    <TD ><%= lsxList.getString("lydo") %></TD>  
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String ndxId = lsxList.getString("ndxId");
		                    		String tt = lsxList.getString("trangthai");
		                    		if(tt.equals("0"))
		                    		{
		                    			trangthai = "Chưa chốt";
		                    		}
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    			{
		                    				if( ndxId.equals("100006") || ndxId.equals("100010") || ndxId.equals("100011") || ndxId.equals("100025"))
		                    				{
		                    					trangthai = "Chờ nhận hàng";
		                    				}
		                    				else
		                    				{
		                    					trangthai = "Chờ xuất kho";
		                    				}
		                    			}
		                    			else if(tt.equals("2"))
		                    			{
		                    				if( ndxId.equals("100006")  || ndxId.equals("100010") || ndxId.equals("100011")  )
		                    				{
		                    					trangthai = "Đã nhận hàng";
		                    				}
		                    				else
		                    				{
		                    					trangthai = "Đã xuất kho";
		                    				}
		                    			}
		                    			else if(tt.equals("3"))
		                    			{
			                    			trangthai = "Hoàn tất";
		                    			}
		                    			else if(tt.equals("4"))
		                    			{
		                    				trangthai = "Đã hủy";
	                    				}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= lsxList.getString("Ngaytao") %></TD>	
		                    <TD ><%= lsxList.getString("NguoiTao") %></TD>
         					<TD align="center"><%= lsxList.getString("NgaySua") %></TD>
							<TD ><%= lsxList.getString("NguoiSua") %></TD>
									
		                    <TD align="center"> 
		                    <% if(tt.equals("0")){ %> 
		                    
		                    	<%if(quyen[2]!=0){ %>
                                <A href = "../../ErpChuyenkhoSXUpdateSvl?userId=<%=userId%>&update=<%=lsxList .getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
                                <%} %>
                                 <%if(quyen[4]!=0){ %>
                                <A href = "../../ErpChuyenkhoSXSvl?userId=<%=userId%>&chot=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"><img src="../images/Chot.png" alt="Chôt chuyển kho" title="Chôt chuyển kho" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn chốt yêu cầu chuyển kho này không?')) return false;"></A>&nbsp;
                                <%} %>
                                 <%if(quyen[1]!=0){ %>
                                <A href = "../../ErpChuyenkhoSXSvl?userId=<%=userId%>&delete=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"><img src="../images/Delete20.png" alt="Xóa chuyển kho" title="Xóa chuyển kho" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa yêu cầu chuyển kho này không?')) return false;"></A>
                                <%} %>	
                                								
		                    <% } else {  %>
		                    
		                    	<A href = "../../ErpChuyenkhoSXUpdateSvl?userId=<%=userId%>&display=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" border=0></A>&nbsp;	
		                    	
		                    <% }   %> 
		                    	
		                    </TD>
		                </TR>
                     <% m++; } lsxList.close(); } %>
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
												
												//System.out.println("Current page:" + obj.getNxtApprSplitting());
												//System.out.println("List page:" + listPage[i]);
												
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
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
<% 
try{
	if(lsxList!=null){
		lsxList.close();
	}
 	
 
	 
	obj.DBclose();
	session.setAttribute("obj",null);
}catch(Exception er){
	
}
	
	} %>
</body>
</html>