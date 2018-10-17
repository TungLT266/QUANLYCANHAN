<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.khoasothang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% IErpCapnhatgianhapList obj = (IErpCapnhatgianhapList)session.getAttribute("obj"); %>
<% ResultSet cngnList = (ResultSet)obj.getCngnRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  

String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{	
		 int[] quyen = new  int[5];
		 quyen = util.Getquyen("ErpCapnhatgianhapkhoSvl","",userId);



%>

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
	    document.forms["erpCngn"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpCngn"].action.value = "Tao moi";
	    document.forms["erpCngn"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpCngn"].tungay.value = "";
	    document.forms["erpCngn"].denngay.value = "";
	    document.forms["erpCngn"].trangthai.value = "";
	    document.forms["erpCngn"].macapnhat.value = "";
	    document.forms["erpCngn"].diengiai.value = "";
	    document.forms["erpCngn"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpCngn"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpCngn"].msg.value);
	 }
	 
	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	</SCRIPT>
</head>
<body>
<form name="erpCngn" method="post" action="../../ErpCapnhatgianhapkhoSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="msg" value='<%= obj.getmsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
             Quản lý cung ứng > Quản lý tồn kho > Cập nhật giá nhập kho
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
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" width="25%">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                   
                        <TD class="plainlabel" width="15%">Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Mã </TD>
                        <TD class="plainlabel" >
                            <input type="text" 
                                   id="macapnhat" name="macapnhat" value="<%= obj.getMacapnhat() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                   
                        <TD class="plainlabel" >Diễn giải </TD>
                        <TD class="plainlabel">
                            <input type="text" 
                                   id="diengiai" name="diengiai" value="<%= obj.getDiengiai() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                           <select name="trangthai" onChange="submitform();">
                           		<option value=""> </option>
								<% if (obj.getTrangthai().equals("1")){%>
								  	<option value="1" selected>Đã chốt</option>
								  	<option value="0">Chưa chốt</option>
								  	<option value=""> </option>
								  
								<% } else { if(obj.getTrangthai().equals("0")) { %>
								 	<option value="0" selected>Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>
								  	<option value="" > </option>
								  	
								<%} else { %> 
									<option value="" selected> </option>
									<option value="0" >Chưa chốt</option>
								  	<option value="1" >Đã chốt</option>	
								<% } }  %>
                           </select>
                        </TD>                        
                    </TR>    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Cập nhật giá nhập </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
            	
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Mã </TH>
	                    <TH align="center">Diễn giải</TH>
	                    <TH align="left">Từ ngày</TH>
	                    <TH align="center">Đến ngày</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="left"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="left"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
                 		if(cngnList != null)
                 		{
                 			int m = 0;
                 			while(cngnList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= cngnList.getString("pk_seq") %></TD>
		                    <TD align="left"><%= cngnList.getString("ghichu") %></TD>
		                    <TD align="center"><%= cngnList.getString("tungay") %></TD>	
		                    <TD align="center"><%= cngnList.getString("denngay") %></TD>	
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = cngnList.getString("trangthai");
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			trangthai = "Đã chốt";
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>									                                    
		                    <TD align="center"><%= cngnList.getString("ngaytao") %></TD>
		                    <TD align="left"><%= cngnList.getString("nguoitao") %></TD>
		                    <TD align="center"><%= cngnList.getString("ngaysua") %></TD>
		                    <TD align="left"><%= cngnList.getString("nguoisua") %></TD>				
		                    <TD align="center"> 
		                    	<A href = "../../ErpCapnhatgnkUpdateSvl?userId=<%=userId%>&display=<%= cngnList.getString("pk_seq") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
		                    </TD>
		                </TR>
                     <% m++; } cngnList.close(); } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	&nbsp;
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

try{
	session.setAttribute("obj",null);

obj.DBclose();	
}catch(Exception er){
	
} 

	}%>
