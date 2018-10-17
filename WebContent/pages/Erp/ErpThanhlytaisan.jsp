<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.thanhlytaisan.*" %>
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
<% IErpThanhlytaisanList obj = (IErpThanhlytaisanList)session.getAttribute("obj"); %>
<% ResultSet dtltsList = (ResultSet)obj.getDtltsList(); 

	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpThanhlytaisanSvl","",userId);

 NumberFormat formatter = new DecimalFormat("#,###,###");  %>
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
	    document.forms["erpDtltsForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpDtltsForm"].action.value = "Tao moi";
	    document.forms["erpDtltsForm"].submit();
	 }
	 function clearform()
	 {  
		document.forms["erpDtltsForm"].loai.value = "";
		document.forms["erpDtltsForm"].sodonthanhlytaisan.value = "";
		document.forms["erpDtltsForm"].kh.value = "";			
// 	    document.forms["erpDtltsForm"].dvth.value = "";
	    document.forms["erpDtltsForm"].tongtien.value = "";
	    document.forms["erpDtltsForm"].tungay.value = "";
	    document.forms["erpDtltsForm"].denngay.value = "";
	    document.forms["erpDtltsForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpDtltsForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDtltsForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 
	</SCRIPT>
</head>
<body>
<form name="erpDtltsForm" method="post" action="../../ErpThanhlytaisanSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
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
        	Quản lý tài sản &gt; Thanh lý tài sản
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
	                    <TD class="plainlabel" valign="middle">Mã / Tên khách hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="kh" value="<%= obj.getKH() %>" onchange="submitform()">
                        </TD>

                        <TD class="plainlabel" valign="middle">Số thanh lý tài sản </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sodonthanhlytaisan" value="<%= obj.getSodonthanhlytaisan() %>" onchange="submitform()">
                        </TD>                        
                                                
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Loại </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="loai" onchange="submitform()">
                            	<option value=""></option>
                            	<%
                        			if(obj.getLoai().equals("1")){ %>
                        				<option value="1" selected >Tài sản</option>
                        				<option value="2"  >Công cụ dụng cụ</option>
                        		<%	}else if(obj.getLoai().equals("2")){ %>
                        				<option value="1" >Tài sản</option>
                        				<option value="2" selected >Công cụ dụng cụ</option>
	                        	<%	}else{ %>
                        				<option value="1" >Tài sản</option>
                        				<option value="2" >Công cụ dụng cụ</option>
								<%	} %>	                        	
	                        	
	                        	
                            </select>
                        </TD>                        

                        <TD class="plainlabel" valign="middle">Tổng tiền </TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                            <input type="text" name="tongtien" value="<%= obj.getTongtiensauVat() %>" onchange="submitform()">
                        </TD>                        

                    </TR> 
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
            	<legend><span class="legendtitle"> Thanh lý tài sản </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
            	
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="7%">Mã </TH>
	                    <TH align="center" width="7%">Ngày</TH>
	                    <TH align="center" width="15%">Khách hàng</TH>
	                    <TH align="center" width="7%">Trạng thái</TH>
	                    <TH align="center" width="7%">Tổng tiền</TH>
	                    <TH align="center" width="7%">Ngày tạo</TH>
	                    <TH align="center" width="10%"> Người tạo </TH>
	                    <TH align="center" width="7%"> Ngày sửa </TH>
	                    <TH align="center" width="10%"> Người sửa </TH>
	                    <TH align="center" width="10%">Tác vụ</TH>
	                </TR>
					<%
                   		if(dtltsList != null)
                   		{
                   			int m = 0;
                   			while(dtltsList.next())
                   			{  		
                   				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
				                    <TD align="center"><%= dtltsList.getString("SOCHUNGTU") %></TD>
				                    <TD align="center"><%= dtltsList.getString("ngay") %></TD>
				                    <TD align="left"><%= dtltsList.getString("khTen") %></TD>	
				                    <TD align="center">
				                    	<%
				                    		String tt = "";
									
				                    		if(dtltsList.getString("trangthai").equals("0"))
				                    		{
				                    			tt = "Chưa duyệt";
				                    		}
				                    		else
				                    		{
				                    			if(dtltsList.getString("trangthai").equals("1"))
				                    				tt = "Đã duyệt";
				                    			else
				                    				if(dtltsList.getString("trangthai").equals("2"))
				                    					tt = "Đã chốt";
				                    				else
				                    				{
				                    					if(dtltsList.getString("trangthai").equals("3"))
				                    						tt = "Đã xuất hóa đơn";
				                    					else
				                    					{
															tt = "Đã hủy";
				                    					}
				                    				}
				                    		}
				                    		
				                    		
				                    	%>
				                    	<%= tt %>
				                    </TD>									                                    
				                    <TD align="right"><%= formatter.format(dtltsList.getDouble("tongtienBvat")) %></TD>
				                    <TD align="center"><%= dtltsList.getString("ngaytao") %></TD>
				                    <TD align="center"><%= dtltsList.getString("nguoitao") %></TD>
				                    <TD align="center"><%= dtltsList.getString("ngaysua") %></TD>	
				                    <TD align="center"><%= dtltsList.getString("nguoisua") %></TD>				
				                    <TD align="center">
				                <% if(dtltsList.getString("trangthai").equals("0")){ %>
				                				                
		                               <A href = "../../ErpThanhlytaisanUpdateSvl?userId=<%=userId%>&update=<%= dtltsList.getString("dtltsId") %>">
		                               		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
		                               
                                		<A href = "../../ErpThanhlytaisanSvl?userId=<%=userId%>&delete=<%= dtltsList.getString("dtltsId") %>"><img src="../images/Delete20.png" width="20" height="20" border=0 
                                					 alt="Xóa Thanh lý tài sản" title="Xóa Thanh lý tài sản" onclick="if(!confirm('Bạn có muốn xóa  Thanh lý tài sản này?')) return false;"></A>	

		                            	<A href = "../../ErpThanhlytaisanSvl?userId=<%=userId%>&duyet=<%= dtltsList.getString("dtltsId") %>"><IMG src="../images/Chot.png" alt="Duyệt Thanh lý tài sản" title="Duyệt thanh lý tài sản" border=0></A>&nbsp;
		                                	
		                         <% } else {  %>
		                         		
		                            	<A href = "../../ErpThanhlytaisanUpdateSvl?userId=<%=userId%>&display=<%= dtltsList.getString("dtltsId") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                            		
		                         <% } %>
				                    </TD>
				                </TR>
		                     <% m++; } dtltsList.close(); } %>
						
						<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
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
</body>
</html>
<% 

   obj.DBclose(); 
	}
%>