<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.phanbomuahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%
	String userId = (String) session.getAttribute("userId");  
	String userTen = (String) session.getAttribute("userTen");  	
	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{ %>
<% IErpPhanbomuahangList obj = (IErpPhanbomuahangList)session.getAttribute("obj"); %>
<% ResultSet dnmhRs = (ResultSet)obj.getDnmhRs(); %>
<% ResultSet timnccList = obj.getTimNccList(); %>
<% 
	int[] quyen = new  int[5];
	//quyen = util.Getquyen("","57",userId);
	quyen = util.Getquyen("ErpPhanbomuahangSvl","",userId);
%>
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
		
  	</style>
  	
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
	    document.forms["erpTimnccForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpTimnccForm"].action.value = "Tao moi";
	    document.forms["erpTimnccForm"].submit();
	 }
	 function clearform()
	 {  
		document.forms["erpTimnccForm"].pbId.value = "";
	    document.forms["erpTimnccForm"].sopo.value = "";
	    document.forms["erpTimnccForm"].action.value = "";
	    document.forms["erpTimnccForm"].submit();
	 } 
	 function thongbao()
	 {
		 var tt = document.forms["erpTimnccForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpTimnccForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 
	</SCRIPT>
</head>
<body>
<form name="erpTimnccForm" method="post" action="../../ErpPhanbomuahangSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý mua hàng > Mua hàng trong nước > Phân bổ cho Pha Nam MTV
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
                        <TD class="plainlabel" valign="middle">Mã phân bổ </TD>
                        <TD class="plainlabel" valign="middle" colspan = 2>
                            <input type="text" name="pbId" value="<%= obj.getPBId() %>" onchange="submitform()">
                        </TD>             
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle">Số PO </TD>
                        <TD class="plainlabel" valign="middle" colspan = 2>
                            <input type="text" name="sopo" value="<%= obj.getSodenghi() %>" onchange="submitform()">
                        </TD>             
                    </TR>
                    <%-- <TR>
                    	<TD class="plainlabel" valign="middle" >Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="trangthai" onchange="submitform()">
                            	<option value=""></option>
                            	                       	 
                            	<% if(obj.getTrangthai().equals("0")) { %>
                            		<option value="0" selected="selected" >Chưa chốt</option>
                            	<% } else { %> 
                            		<option value="0" >Chưa chốt</option>
                            	<%  } %>
                            	<% if(obj.getTrangthai().equals("1")) { %>
                            		<option value="1" selected="selected" >Đã chốt</option>
                            	<% } else { %>
                            		<option value="1" >Đã chốt</option>
                            	<% }  %>                             	
                            </select>
                        </TD>                       
                    </TR> --%>
                    <tr>
                    	<td class="plainlabel">Số items
                        </td>
                        <td class="plainlabel">
							<input type="text" name="soItems" value="<%= obj.getSoItems() %>" onchange="submitform()">
                        </td>
                    </tr>
                    <tr>
                        <td class="plainlabel" colspan = 2>
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>
                        </td>
                        
                    </tr>
                </TABLE>  
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Phân bổ  </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
            	
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
					 	<!-- <TH align="center" width="4%">Số đề nghị mua hàng</TH> -->
	                    <TH align="center" width="7%">Mã phân bổ</TH>
	                    <TH align="center" width="7%">Diễn giải</TH>
	                    <TH align="center" width="7%">Số PO</TH>
	                    <TH align="center" width="7%">Trạng thái</TH>
	                    <TH align="center" width="7%">Ngày tạo</TH>
	                    <TH align="center" width="10%"> Người tạo </TH>
	                    <TH align="center" width="7%"> Ngày sửa </TH>
	                    <TH align="center" width="7%"> Người sửa </TH>
	                    <TH align="center" width="9%">Tác vụ</TH>
	                </TR>
					<%
                   		if(timnccList != null)
                   		{
                   			int m = 0;
                   			while(timnccList.next())
                   			{  		
                   				String style="";
                   				
                   				if((m % 2 ) == 0) { %>
		                         	<TR class='tbdarkrow'   >
		                        <%}else{ %>
		                          	<TR class='tblightrow'  >
		                        <%} %>
				                    <TD align="center"><%= timnccList.getString("id") %></TD>
				                    <TD align="center"><%= timnccList.getString("diengiai") %></TD>
				                    <TD align="center"><%= timnccList.getString("sopo") %></TD>
				                    <%String tt = timnccList.getString("trangthai");%>
				                    <TD align="center" style="color: red">
				                    	<%
				                    		String trangthai = "";
				                    	
				                    		if(tt.equals("0"))
				                    		{
				                    			trangthai = "Chưa chốt";	
				                    		}
				                    		else
				                    		{
				                    			trangthai = "Đã chốt";
				                    		}
				                    		
				                    	%>
				                    	<%= trangthai %>
				                    </TD>									                      
				                    <TD align="center"><%= timnccList.getString("ngaytao") %></TD>
				                    <TD align="left"><%= timnccList.getString("nguoitao") %></TD>
				                    <TD align="center"><%= timnccList.getString("ngaysua") %></TD>	
				                    <TD align="left"><%= timnccList.getString("nguoisua") %></TD>				
				                    <TD align="center">
				                	<% if(tt.equals("0")){ %>
				                
				               			<A href = "../../ErpPhanbomuahangUpdateSvl?userId=<%=userId%>&display=<%= timnccList.getString("id") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
				              			<%if(quyen[0] != 0 ){ %>
				                		
		                               	<A href = "../../ErpPhanbomuahangUpdateSvl?userId=<%=userId%>&update=<%= timnccList.getString("id") %>">
		                               		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
		                               	<%} %> 
		                               	<%-- <%if(quyen[3]!=0){ %>
			                                 <A id='chotphieu<%=timnccList.getString("id")%>'
										      href=""><img
										      src="../images/Chot.png" alt="Chốt"
										      width="20" height="20" title="Chốt" 
										      border="0" onclick="if(!confirm('Bạn có muốn chốt phân bổ này?')) {return false ;}else{ processing('<%="chotphieu"+timnccList.getString("id")%>' , '../../ErpPhanbomuahangSvl?userId=<%=userId%>&chot=<%= timnccList.getString("id")%>');}"  >
										    </A>
									    	<%} %> --%>
		                               		                
		                               
		                            
	                                    <%if(quyen[2]!=0){ %>
	                                		<A href = "../../ErpPhanbomuahangSvl?userId=<%=userId%>&delete=<%= timnccList.getString("id") %>"><img src="../images/Delete20.png" width="20" height="20" border=0 
	                                					 alt="Xóa phân bổ mua hàng" title="Xóa phân bổ mua hàng" onclick="if(!confirm('Bạn có muốn xóa phân bổ này?')) return false;"></A>&nbsp;
	                                	<%} %>	 
	                                	                              	
		                         	<%} else{  %>
		                         		<A href = "../../ErpPhanbomuahangUpdateSvl?userId=<%=userId%>&display=<%= timnccList.getString("id") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                            <%} %>
				                    </TD>
				                </TR>
		                     <% m++; } timnccList.close(); } %>
						
						<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpTimnccForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpTimnccForm', 'prev')"> &nbsp;
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
								<a href="javascript:View('erpTimnccForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpTimnccForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpTimnccForm', -1, 'view')"> &nbsp;
					   		<%} %>
						</TD>
					 </tr>  
				</TABLE>
            </fieldset>
        </div>
    </div>  
</div>
</form>
</body>
</html>
<%
   obj.DBclose(); 
	}
%>