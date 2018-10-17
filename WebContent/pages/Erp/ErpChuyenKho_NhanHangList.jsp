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
<% ResultSet nvList = (ResultSet)obj.getNhanvienRs(); %>
<% ResultSet nv2List = (ResultSet)obj.getNhanvien2Rs(); %>
<% ResultSet khonhanRs = (ResultSet)obj.getKhonhanRs(); %>
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
		document.forms["erpLsxForm"].tungaySX.value = "";
		document.forms["erpLsxForm"].denngaySX.value = "";
	    document.forms["erpLsxForm"].trangthai.value = "";
	    document.forms["erpLsxForm"].sophieu.value = "";
	    document.forms["erpLsxForm"].nguoitao.value = "";
	    document.forms["erpLsxForm"].nguoisua.value = "";
	    document.forms["erpLsxForm"].khonhanId.value = "";
	    document.forms["erpLsxForm"].lydo.value = "";
	    <%if(obj.gettask().equals("LSX")){ %>
	    document.forms["erpLsxForm"].solenhsx.value = "";
	    <%}%>
	    document.forms["erpLsxForm"].solenhsx1.value = "";
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
     $(document).ready(function() { $("#nguoitao,#nguoisua,#khonhanId").select2(); });
     
     
     
 </script>
</head>
<body>
<form name="erpLsxForm" method="post" action="../../ErpChuyenkhoSXSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="task" value="<%=obj.gettask() %>" >
<input type="hidden" name="isnhanHang" value="<%=obj.getIsnhanHang()%>" >
<input type="hidden" name="currentPage" value="<%= obj.getCurrentPage() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
    	<%if(obj.gettask().equals("LSX")){ %>
        	Quản lý cung ứng > Sản xuất > Nhận hàng
        	<%} else { %>
        	Quản lý cung ứng > Quản lý tồn kho > Nhận hàng
        	<%} %>
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
                        <TD class="plainlabel" width="10%">Ngày chuyển:</TD>
                        <TD class="plainlabel" width="10%">Từ ngày </TD>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" onchange="submitform()"
                                   id="tungaySX" name="tungaySX" value="<%= obj.getTungayTao() %>" maxlength="10"  />
                        </TD>
                        
                        <TD class="plainlabel"  width="10%" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" onchange="submitform()"
                                   id="denngaySX" name="denngaySX" value="<%= obj.getDenngayTao() %>" maxlength="10" />
                        </TD>
                    </TR>
                    <TR>
                    	
                    </TR>
                    
                     <TR>
                     	<TD class="plainlabel">&nbsp;</TD>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onChange="submitform();">
								<% if (obj.getTrangthai().equals("1")){%>
									<option value=""> </option>
									<option value="1" selected >Chưa nhận hàng</option>
								  	<option value="2" >Đã nhận hàng</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value="4">Đã hủy</option>
								  	
								  
								<% } else { if(obj.getTrangthai().equals("2")) { %>
									<option value=""> </option>
									<option value="1"  >Chưa nhận hàng</option>
								  	<option value="2" selected >Đã nhận hàng</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value="4">Đã hủy</option>
								  	
								  	
								<% } else { if(obj.getTrangthai().equals("3")) { %>	
									<option value="1"  >Chưa nhận hàng</option>
								  	<option value="2" >Đã nhận hàng</option>
								  	<option value="3" selected>Hoàn tất</option>
								  	<option value="4">Đã hủy</option>
								  	<option value=""> </option>				  		  	
								<%} else { if(obj.getTrangthai().equals("3")) { %>
									<option value=""> </option>	
									<option value="1"  >Chưa nhận hàng</option>
								  	<option value="2" >Đã nhận hàng</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value="4" selected>Đã hủy</option>
								  	
								
								<% } else { %>
									<option value="" selected> </option>
									<option value="1"  >Chưa nhận hàng</option>
								  	<option value="2" >Đã nhận hàng</option>
								  	<option value="3">Hoàn tất</option>
								  	<option value="4" >Đã hủy</option>
								  		
								  	
								<% } } } }  %>
                           </select>
                        </TD>
                         <TD class="plainlabel" >Số chứng từ </TD>
                        <TD class="plainlabel">
                            <input type="text"  onchange="submitform()"
                                   id="sophieu" name="sophieu" value="<%= obj.getSophieu() %>" maxlength="10" />
                        </TD>                        
                    </TR>
                 <%--    <%if(obj.gettask().equals("LSX")){ %>
                    <TR>
                    <TD class="plainlabel">&nbsp;</TD>
                        <TD class="plainlabel" width="15%">Số lệnh sản xuất</TD>
                        <TD class="plainlabel" colspan="4">
                            <input type="text" 
                                   id="solenhsx" name="solenhsx" value="<%= obj.getLsxId() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>  
		        	<%}%>
                     --%>
                     
                     <TR>
                    	<TD class="plainlabel" width="10%">&nbsp;</TD>
                        <TD class="plainlabel" >Người tạo </TD>
        
                        
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
                   <TD class="plainlabel" width="10%">&nbsp;</TD>
                        <TD class="plainlabel" >Kho nhận </TD>
        
                        
                         <TD class="plainlabel" width="200px" >
                          <select name="khonhanId" id="khonhanId" onChange="submitform();" style="width: 200px">
                        	<option value=""> </option>
                        	<%
                        		if(khonhanRs != null)
                        		{
                        			try
                        			{
                        			while(khonhanRs.next())
                        			{  
                        			if( khonhanRs.getString("pk_seq").equals(obj.getKhonhanid())){ %>
                        				<option value="<%= khonhanRs.getString("pk_seq") %>" selected="selected" ><%= khonhanRs.getString("ten") %></option>
                        			<%}else { %>
                        				<option value="<%= khonhanRs.getString("pk_seq") %>" ><%= khonhanRs.getString("ten") %></option>
                        		 <% } } khonhanRs.close();} catch(SQLException ex){}
                        		}
                        	%> 
                          </select>
                        </TD>
                        
                        <TD class="plainlabel" >Số lệnh sản xuất</TD>
                        <TD class="plainlabel">
                            <input type="text"  id="solenhsx" name="solenhsx1" value="<%= obj.getLsxId() %>" maxlength="10" onchange="submitform()" />
                        </TD>

                   </TR>
                    <%if(!obj.gettask().equals("LSX")){ %>
                    <TR>
                    <TD class="plainlabel">&nbsp;</TD>
                        <TD class="plainlabel" width="15%">Lý do (Phiếu kiểm định)</TD>
                        <TD class="plainlabel" colspan="4">
                            <input type="text" 
                                   id="lydo" name="lydo" value="<%= obj.getLydo() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>  
		        	<%}%>
                   
                   
                   
                   
                   
                   
                   
                    <tr>
                        <td colspan="5" class="plainlabel">
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
        	<fieldset>
        	
            	<legend><span class="legendtitle"> Nhận hàng </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="7%">Số phiếu</TH>
	                    <TH align="center" width="9%">Ngày chuyển</TH>
	                    <TH align="center" width="13%">Lý do</TH>
	                      <%if(!obj.gettask().equals("LSX")){ %>
		                    <TH align="center" width="5%">Số lệnh SX</TH>
		                     <TH align="center" width="6%">Ngày SX</TH>
	                     <%} %>
	                    <TH align="center" width="10%">Trạng thái</TH>
	                    <TH align="center" width="8%">Ngày tạo</TH>
	                    <TH align="center" width="12%"> Người tạo</TH>
	                    <TH align="center" width="8%">Ngày sửa</TH>
	                    <TH align="center" width="12%">Người sửa</TH>
	                    <TH align="center" width="10%" >Tác vụ</TH>
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
		                    <TD align="center"><%= lsxList.getString("ngaychuyen") %></TD>
		                    <TD ><%= lsxList.getString("lydo") %></TD>
		                    
		                    <%if(!obj.gettask().equals("LSX")){ %>
			                    <TD align="center"><%= lsxList.getString("solenhsx") %></TD>
			                    <TD align="center"><%= lsxList.getString("ngaysanxuat") %></TD>
			                <%} %>    
			                    
		                      <%
		                    		String trangthai = "";
		                    		String tt = lsxList.getString("trangthai");
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    			{
		                    				trangthai = "Chờ nhận hàng";
		                    			}
		                    			else if(tt.equals("2"))
		                    			{
			                    			trangthai = "Đã nhận hàng";
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
		                    		if(tt.equals("4")){%>
		                    		<TD align="center" style="color: red;">
		                    		<%}else{ 
		                    			%>
		                    			<TD align="center">
		                    			
		                    	<%}%>
		                    	
		                    	<%= trangthai %>
		                    </TD>   									                                    
					     	<TD align="center"><%= lsxList.getString("Ngaytao") %></TD>	
		                    <TD ><%= lsxList.getString("NguoiTao") %></TD>
         					<TD align="center"><%= lsxList.getString("NgaySua") %></TD>
							<TD ><%= lsxList.getString("NguoiSua") %></TD>
									
		                    <TD align="center"> 
		                     									
		                    <%if( tt.equals("1") || tt.equals("2")  ) { %>
		                    
		                    	<A href = "../../ErpChuyenkhoSXUpdateSvl?userId=<%=userId%>&display=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" border=0></A>&nbsp;	
		                    	<%if(quyen[2]!=0){ %>
		                    	<A href = "../../ErpChuyenkhoSXUpdateSvl?userId=<%=userId%>&nhanhang=<%= lsxList.getString("PK_SEQ") %>&tungay=<%= obj.getTungayTao() %>&denngay=<%= obj.getDenngayTao() %>&trangthai=<%= obj.getTrangthai()%>&sophieu=<%=obj.getSophieu()%>&nguoitao=<%= obj.getNguoitao()%>&nguoisua=<%=obj.getNguoisua()%>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>">
		                    	<IMG src="../images/nhanhang.png" alt="Nhận hàng" title="Nhận hàng" width="20" height="20"  border=0></A>&nbsp;
		                    	<%} %>
		                    	<% if(tt.equals("2")) { %>
		                    	<%if(quyen[4]!=0){ %>
		                    		<A href = "../../ErpChuyenkhoSXSvl?userId=<%=userId%>&hoantat=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"  onclick="if(!confirm('Bạn có muốn hoàn tất phiếu yêu cầu nhận hàng này?')) return false;" ><IMG src="../images/Chot.png" alt="Hoàn tất" title="Hoàn tất" width="20" height="20"  border=0></A>&nbsp;	
		                    	<% } }%>	
		                    <% } else {  %> 
		                    	<% if(tt.equals("3")) { %>
		                    	<%if(quyen[4]!=0){ %>
		                    		<A href = "../../ErpChuyenkhoSXUpdateSvl?userId=<%=userId%>&hoantat=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"   ><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" border=0></A>&nbsp;
		                    		<%} %>
		                    		
		                    		<%if(!lsxList.getString("is_doiquycach").equals("1") &&  lsxList.getString("ndxId").equals("100026") ){ %>
		                    		<A href = "../../ErpChuyenkhoSXUpdateSvl?userId=<%=userId%>&doiquycach=<%= lsxList.getString("PK_SEQ") %>"   ><IMG src="../images/Next.png" alt="Đổi quy cách" title="Đổi quy cách" width="20" height="20" border=0></A>&nbsp;
		                    		<%} %>	
		                    			
		                    	<% } else { %>
		                    	
		                    		<A href = "../../ErpChuyenkhoSXUpdateSvl?userId=<%=userId%>&display=<%= lsxList.getString("PK_SEQ") %>&task=<%=obj.gettask()%>&isnhanHang=<%=obj.getIsnhanHang()%>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" width="20" height="20" border=0></A>&nbsp;	
		                    	<% } %>
		                    <%  }   %>
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