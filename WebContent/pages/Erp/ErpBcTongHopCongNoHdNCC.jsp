<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.donmuahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% 	IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<% 	String userId = (String) session.getAttribute("userId");   %>
<% 	String userTen = (String) session.getAttribute("userTen"); %>
<% 	String view = (String) session.getAttribute("view");  
	ResultSet RsLoaiNcc = obj.getRsErpNCCId();
	ResultSet rsncc = obj.getRsNCC();
	ResultSet ctyRs = obj.getCtyRs();
	String ctyIds = obj.getErpCongtyId();
%>

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
	 
	 function Change()
	 {   
	
	    document.forms["erpDmhForm"].action.value="change";
	    document.forms["erpDmhForm"].submit();
	 }
	 
	 function submitform()
	 {   
	
	    document.forms["erpDmhForm"].action.value="Taomoi";
	    document.forms["erpDmhForm"].submit();
	 }
	
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function ChonAllNccId()
	 {
		 var chkAll = document.getElementById("ChonAll");
		 var nccIds = document.getElementsByName("ChonIdNcc");
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
<form name="erpDmhForm" method="post" action="../../ErpBCCongNoTongHopHdNCC">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Báo cáo quản trị > Công nợ > BC Công nợ tổng hợp NCC theo hợp đồng
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
                
                <% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn công ty </TD>
                        <TD class="plainlabel" >
                            <SELECT NAME = "ctyIds" multiple style = "width:250px;size:5" onChange = "Change();">
                            	<OPTION VALUE = "">&nbsp;</OPTION>
					<%	if(ctyRs != null){ 
							while(ctyRs.next()){ 
								if(ctyIds.indexOf(ctyRs.getString("PK_SEQ")) >= 0){
							
							%>
								
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" SELECTED ><%= ctyRs.getString("TEN")%></OPTION>
					<%			}else if(!ctyIds.equals("100000")){ %>
					
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" ><%= ctyRs.getString("TEN")%></OPTION>
					
					<%			}else{ %>
					
								<OPTION VALUE = "<%= ctyRs.getString("PK_SEQ")%>" SELECTED ><%= ctyRs.getString("TEN")%></OPTION>
								
					<%			}

							} 
						}%>
                            </SELECT>
                        </TD>
                    </TR>
				<%} %>
				
                    <TR>
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days"  
                                   id="tungay" name="tungay" value="<%= obj.gettungay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getdenngay() %>" maxlength="10" />
                        </TD>
                    </TR>
          <% if(!obj.getView().equals("TT")){ %>	          
                   <%--  <TR>
                        <TD class="plainlabel" valign="middle" >Chọn loại nhà cung cấp </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="nccId" rel="subcontentNcc">
	           	 							&nbsp; <img alt="Chọn loại nhà cung cấp" src="../images/vitriluu.png"></a>
	           	 		<DIV id="subcontentNcc" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 760px; padding: 4px; max-height: 200px; overflow: auto; ">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="70px">Mã tắt</th>
				                            <th width="300px">Loại nhà cung cấp </th>
				                            <th width="50px" align="center">Chọn <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
				                        </tr>
		                            	<%
			                        		if( RsLoaiNcc!= null)
			                        		{
			                        			while(RsLoaiNcc.next())
			                        			{ 
			                        				%>
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= RsLoaiNcc.getString("ma") %>" readonly="readonly"></td>
			                        				<td><input style="width: 100%" value="<%= RsLoaiNcc.getString("ten") %>" readonly="readonly"></td>
			                        				<td align="center">
			                        				<% if(obj.GetKhId().indexOf(RsLoaiNcc.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="nccIds" checked="checked" value="<%= RsLoaiNcc.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="nccIds" value="<%= RsLoaiNcc.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } RsLoaiNcc.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentNcc')">Hoàn tất</a>
				                     </div>
				            </DIV>  
                                          
                        </TD>                        
                    </TR>  --%>
                    
                     <TR>
                     <TD class="plainlabel" width="15%">Nhà cung cấp </TD>
                     <TD class="plainlabel" >                        
                  		<select name="nccId" id="nccId" style="width: 200px" onChange="ChangeTienTe();">
                            <option value="" SELECTED> </option>
                        	<%
                        		if(rsncc != null)
                        		{
                        			while(rsncc.next())
                        			{  
	                        			if( rsncc.getString("pk_seq").equals(obj.getErpNCCId())){ %>
	                        				<option value="<%= rsncc.getString("pk_seq") %>" selected="selected" ><%= rsncc.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= rsncc.getString("pk_seq") %>" ><%= rsncc.getString("ten") %></option>
	                        		 	<% }                         			
                        			} rsncc.close();
                        		}
                        	%>
                        </select>
                     </TD>
                     
                    </TR> 
            <%} %>  
                     <tr>
                        <td colspan="2" class="plainlabel">
                           <a class="button" href="javascript:submitform();"> 
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
dropdowncontent.init('nccIdStr', "right-bottom", 300, "click");
 
</script>
</html>
<%
	if(RsLoaiNcc != null) RsLoaiNcc.close();
	obj.DBclose();
%>