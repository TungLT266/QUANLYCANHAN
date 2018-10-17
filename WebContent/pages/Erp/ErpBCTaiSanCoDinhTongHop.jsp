<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.donmuahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>

<% 	IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<% 	String userId = (String) session.getAttribute("userId");  %>
<% 	String userTen = (String) session.getAttribute("userTen");   
	ResultSet rsLoaiTaiSan=obj.getLoaiTaiSanRs();
	ResultSet rsNhomTaiSan=obj.getNhomTaiSanRs();
	ResultSet rsTTCP=obj.getTTCPRs();
	ResultSet rstaisan = obj.gettaisanRs();
%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Canfoco - Project</title>
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
	 function submitform()
	 {   
		 if (document.forms["erpDmhForm"]["tuthang"].value.length == 0) {
				setErrors("Vui lòng chọn tháng");
				return;
			}
		if (document.forms["erpDmhForm"]["tunam"].value.length == 0) {
			setErrors("Vui lòng chọn năm");
			return;
		}			
			
	    document.forms["erpDmhForm"].action.value="Taomoi";
	    document.forms["erpDmhForm"].submit();
	 }
	
	 function search()
	 {   
	
	    document.forms["erpDmhForm"].action.value="search";
	    document.forms["erpDmhForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 
	 function setErrors(errorMsg) {
			var errors = document.getElementById("errors");
			errors.value = errorMsg;
		}
	</SCRIPT>
</head>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

<body>
<form name="erpDmhForm" method="post" action="../../ErpBCTaiSanCoDinhTongHopSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	&#160; Quản lý tài sản &gt; BC Tài sản cố định tổng hợp
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width: 100%; float: none; clear: left">
				<fieldset>
					<legend class="legendtitle"> Thông báo</legend>
					<textarea id="errors" name="errors" rows="1" style="width: 100%"><%=obj.getMsg()%></textarea>
					<script language="javascript" type="text/javascript">
	    				thongbao();
					</script> 
				</fieldset>
	</div>
			
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >	
                <TR>
                	<TD class="plainlabel" width="10%">Loại báo cáo </TD>
                	<TD class="plainlabel" width="30%" colspan="3">
                		<select id = "loaiBC" name = "loaiBC" onchange="search();" style="width: 300px">
                			<%if(obj.getLoaiBC().equals("0")){ %>
                				<option value =""></option>
	                			<option value ="0" selected="selected">BC Tài sản tổng hợp tất cả</option>
	                			<option value ="1">BC Tài sản tổng hợp theo loại tài sản</option>
	                			<option value ="2">BC Tài sản chi tiết theo loại tài sản</option>
	                			<option value ="3">BC Tài sản chi tiết theo trung tâm chi phí</option>
	                			<option value ="4">BC Tăng giảm tài sản cố định</option>
                			<%}else if(obj.getLoaiBC().equals("1")){ %>
	                			<option value =""></option>
	                			<option value ="0">BC Tài sản tổng hợp tất cả</option>
	                			<option value ="1" selected="selected">BC Tài sản tổng hợp theo loại</option>     
	                			<option value ="2">BC Tài sản chi tiết theo loại tài sản</option>
	                			<option value ="3">BC Tài sản chi tiết theo trung tâm chi phí</option> 
	                			<option value ="4">BC Tăng giảm tài sản cố định</option> 
	                		<%}else if(obj.getLoaiBC().equals("2")){ %>
	                			<option value =""></option>
	                			<option value ="0">BC Tài sản tổng hợp tất cả</option>
	                			<option value ="1" >BC Tài sản tổng hợp theo loại</option>   
	                			<option value ="2" selected="selected">BC Tài sản chi tiết theo loại tài sản</option>
	                			<option value ="3">BC Tài sản chi tiết theo trung tâm chi phí</option> 
	                			<option value ="4">BC Tăng giảm tài sản cố định</option> 
	                		 <%}else if(obj.getLoaiBC().equals("3")){ %>
	                			<option value =""></option>
	                			<option value ="0">BC Tài sản tổng hợp tất cả</option>
	                			<option value ="1" >BC Tài sản tổng hợp theo loại</option>   
	                			<option value ="2" >BC Tài sản chi tiết theo loại tài sản</option>
	                			<option value ="3" selected="selected">BC Tài sản chi tiết theo trung tâm chi phí</option> 
	                			<option value ="2" >BC Tăng giảm tài sản cố định</option>	
                			<%}else if(obj.getLoaiBC().equals("4")){ %>
	                			<option value =""></option>
	                			<option value ="0">BC Tài sản tổng hợp tất cả</option>
	                			<option value ="1" >BC Tài sản tổng hợp theo loại</option>   
	                			<option value ="2" >BC Tài sản chi tiết theo loại tài sản</option>
	                			<option value ="3" >BC Tài sản chi tiết theo trung tâm chi phí</option> 
	                			<option value ="4" selected="selected" >BC Tăng giảm tài sản cố định</option>	  	       			
                			<%}else if(obj.getLoaiBC().equals("")){ %>
	                			<option value ="" selected="selected"></option>
	                			<option value ="0">BC Tài sản tổng hợp tất cả</option>
	                			<option value ="1">BC Tài sản tổng hợp theo loại</option>
	                			<option value ="2">BC Tài sản chi tiết theo loại tài sản</option>
	                			<option value ="3">BC Tài sản chi tiết theo trung tâm chi phí</option>
	                			<option value ="4">BC Tăng giảm tài sản cố định</option>
                			<%} %>
                		</select>
                	</TD>
                </TR>
                <TR>
                	<TD class="plainlabel" width="10%">Xem theo </TD>
                	<TD class="plainlabel" width="20%" >
                	<%-- <% if(obj.getLoaiBC().equals("4")){ %>
                		<%if(obj.getView().equals("0")){ %>                               		
                		     <input type="radio" name="view" value="0" checked onchange="search();"> Ngày
                			 <input type="radio" name="view"  value="1" onchange="search();">  Tháng
                		<%}else{ %> 
                		    <input type="radio" name="view" value="0" onchange="search();" > Ngày
                		    <input type="radio" name="view" value="1" checked onchange="search();">  Tháng
                		<%} %>
                	<%}else{ %> --%>
                		<input type="radio" name="view" value="1" checked onchange="search();" >  Tháng
                	<%-- <%} %> --%>
				   </TD>
				   <TD class="plainlabel" width="10%" > </TD>
                	<TD class="plainlabel" >
				   </TD>
                </TR>	
                					                          
                    <%-- <TR>
                        <TD class="plainlabel" >Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days"  
                                   id="tungay" name="tungay" value="<%= obj.gettungay() %>" maxlength="10" />
                        </TD>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getdenngay() %>" maxlength="10" />
                        </TD>
                    </TR> --%>
                
                    <TR>
	                    <TD class="plainlabel">Tháng </TD> 
						<TD class="plainlabel" > 
							<select name="tuthang" id="tuthang" style="width: 200px">
								<option value="" ></option>
								<% for(int i = 1; i < 13; i++ ) {
									 if( Integer.toString(i).equals(obj.getFromMonth()) ) { %>
									 	<option value="<%= i %>" selected="selected" ><%= i %></option>
									 <% } else { %>
									 	<option value="<%= i %>" ><%= i %></option>
									 <% } %>
								<% } %>
							</select>
						</TD>
	                    <TD class="plainlabel">Năm </TD> 
						<TD class="plainlabel"> 
							<select name="tunam" id="tunam" style="width: 200px">
								<option value="" ></option>
								<% for(int i = 2010; i < 2020; i++ ) {
									 if( Integer.toString(i).equals(obj.getFromYear()) ) { %>
									 	<option value="<%= i %>" selected="selected" ><%= i %></option>
									 <% } else { %>
									 	<option value="<%= i %>" ><%= i %></option>
									 <% } %>
								<% } %>
							</select>
						</TD>
						
                    </TR>
                    <% if(obj.getLoaiBC().equals("1") || obj.getLoaiBC().equals("2")){ %>
                     <TR>
	                    <TD class="plainlabel">Loại tài sản </TD> 
						<TD class="plainlabel" colspan="3"> 
							<select name="loaits" id="loaits" style="width: 200px">
								<option value=""> </option>
								<%  
   									if(rsLoaiTaiSan != null)
   									{
   										while(rsLoaiTaiSan.next())
   										{
   											if(rsLoaiTaiSan.getString("pk_seq").equals(obj.getLoaiTaiSanId())){ %>
   												<option value="<%= rsLoaiTaiSan.getString("pk_seq") %>" selected="selected" ><%= rsLoaiTaiSan.getString("DIENGIAI") %></option>
   											<%} else { %> 
   												<option value="<%= rsLoaiTaiSan.getString("pk_seq") %>"><%= rsLoaiTaiSan.getString("DIENGIAI") %></option>
   											<%}
   										}
   										rsLoaiTaiSan.close();
   									}
   								%>
							</select>
						</TD>
					</TR>
					<%} %>	
					<%if( obj.getLoaiBC().equals("3")  ){ %>
					<TR>
						<TD class="plainlabel">Trung tâm chi phí </TD> 
						<TD class="plainlabel" colspan="3"> 
							<select name="ttcpid" id="ttcpid" style="width: 200px">
								<option value=""> </option>
								<%  
   									if(rsTTCP != null)
   									{
   										while(rsTTCP.next())
   										{
   											if(rsTTCP.getString("pk_seq").equals(obj.getTTCPId())){ %>
   												<option value="<%= rsTTCP.getString("pk_seq") %>" selected="selected" ><%= rsTTCP.getString("DIENGIAI") %></option>
   											<%} else { %> 
   												<option value="<%= rsTTCP.getString("pk_seq") %>"><%= rsTTCP.getString("DIENGIAI") %></option>
   											<%}
   										}
   										rsTTCP.close();
   									}
   								%>
							</select>
						</TD>
					</TR>
					<%} %>
                    
                                       
                  <%--  <%if(obj.getLoaiBC().equals("3")){ %> 
                    <TR class="plainlabel">
                        <TD class="plainlabel" valign="middle" >Tài sản</TD>
                        <TD class="plainlabel" valign="middle" colspan="3">
                         
                         <a href="" id="taisanId" rel="subcontentTS">
	           	 			&nbsp; <img alt="Chọn tài sản" src="../images/vitriluu.png"></a>
	           	 		<DIV id="subcontentTS" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 760px; padding: 4px; max-height: 200px; overflow: auto; ">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="70px">Mã tài sản</th>
				                            <th width="300px">Loại tài sản </th>
				                            <th width="50px" align="center">Chọn<input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
				                        </tr>
		                            	<%
			                        		if( rstaisan!= null)
			                        		{
			                        			while(rstaisan.next())
			                        			{ 
			                        				%>
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= rstaisan.getString("ma") %>" readonly="readonly"></td>
			                        				<td><input style="width: 100%" value="<%= rstaisan.getString("ten") %>" readonly="readonly"></td>
			                        				<td align="center">
			                        				<% if(obj.gettaisanId().indexOf(rstaisan.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="taisanIds" checked="checked" value="<%= rstaisan.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="taisanIds" value="<%= rstaisan.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } rstaisan.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentTS')">Hoàn tất</a>
				                     </div>
				            </DIV>  
                                          
                        </TD>                                               
                    </TR>                     
              		<%} %> --%>
              
                     <tr>
                        <td colspan="4" class="plainlabel">
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
dropdowncontent.init('taisanId', "right-bottom", 300, "click"); 
</script>
</html>
<%
try{
	if (rsLoaiTaiSan != null)
		rsLoaiTaiSan.close();
	if (rsNhomTaiSan != null)
		rsNhomTaiSan.close();
	if (rsTTCP != null)
		rsTTCP.close();
	if (rstaisan !=null)
		rstaisan.close();
}catch (Exception ex)
{
	ex.printStackTrace();
}
finally
{
	if (obj != null)
	{
		obj.DBclose();
		obj = null;
	}
	session.removeAttribute("obj");
}
%>