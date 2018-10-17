<%@page import="geso.traphaco.erp.beans.stockintransit.IStockintransit"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "java.sql.ResultSet" %>

<% IStockintransit obj = (IStockintransit)session.getAttribute("obj"); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 

	ResultSet RsNkh=obj.getRsNhomKh();
	ResultSet RsKh=obj.GetRsKhErp();
	ResultSet RsKbh=obj.getkenh();
	ResultSet rsdvkd=obj.getdvkd();
	ResultSet ctyRs = obj.getCtyRs();
	String ctyIds = obj.getErpCongtyId();
	//System.out.println("ctyIds:" + ctyIds);
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
	
	    document.forms["THCongnoKHForm"].action.value="change";
	    document.forms["THCongnoKHForm"].submit();
	 }
	 
	 function submitform()
	 {   
	
	    document.forms["THCongnoKHForm"].action.value="Taomoi";
	    document.forms["THCongnoKHForm"].submit();
	 }
	 function submitform1()
	 {   
	
	    document.forms["THCongnoKHForm"].action.value="";
	    document.forms["THCongnoKHForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["THCongnoKHForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["THCongnoKHForm"].msg.value);
	 }
	 
	 function sellectAll()
	 {
		 var chkAll = document.getElementById("chkAll");
		 var khIds = document.getElementsByName("khIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
			 }
		 }
	 }
	 function sellectAll1()
	 {
		 var chkAll = document.getElementById("chkAll1");
		 var khIds = document.getElementsByName("nkhIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < khIds.length; i++)
			 {
				 khIds.item(i).checked = false;
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
<form name="THCongnoKHForm" method="post" action="../../ErpBCCongNoTongHopKH">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="view" value="<%= obj.getView() %>" >
<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Báo cáo quản trị > Công nợ > Công nợ tổng hợp khách hàng (mới)
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

               <%--  <% if(obj.getView().equals("TT")){ %>							                          
                    <TR>
                        <TD class="plainlabel" width="15%">Chọn công ty </TD>
                        <TD class="plainlabel" >
                            <SELECT NAME = "ctyIds" multiple  style = "width:300px;size:5" onChange = "Change();">
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
				<%} %> --%>
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
                   
                   <%--  <TR>
                        <TD class="plainlabel" valign="middle" >Kênh bán hàng </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="kbhid" onChange="submitform1();" style="width: 200px">
                           		<option value=""> </option>
								<%  
   									if(RsKbh != null)
   									{
   										while(RsKbh.next())
   										{
   											if(RsKbh.getString("pk_seq").equals(obj.getkenhId())){ %>
   												<option value="<%= RsKbh.getString("pk_seq") %>" selected="selected" ><%= RsKbh.getString("DIENGIAI") %></option>
   											<%} else { %> 
   												<option value="<%= RsKbh.getString("pk_seq") %>"><%= RsKbh.getString("DIENGIAI") %></option>
   											<%}
   										}
   										RsKbh.close();
   									}
   								%>
                           </select>
                        </TD>                
                    </TR>  --%>
                    
                    <%-- <TR>
						<TD class="plainlabel"> Mức lấy  </TD>
						<TD class="plainlabel" colspan="3">
							<%
								if(obj.gettype().equals("0")){
									%>
									<input type="radio" name="type" value="1"  /> Tên xuất hóa đơn &nbsp; &nbsp;
									<input type="radio" name="type" value="0"  checked="checked"  /> Tên nội bộ 
									<%
								}
								else
								{
									%>
										<input type="radio" name="type" value="1"  checked="checked"   /> Tên xuất hóa đơn   &nbsp; &nbsp;
										<input type="radio" name="type" value="0"  /> Tên nội bộ
									<%
								}
							%>
								
						</TD>									
					</TR> --%>
					
            <!--         
                     <TR>
                        <TD class="plainlabel" valign="middle" >Đơn vị kinh doanh </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="dvkdId" onChange="submitform1();" style="width: 200px">
                           		<option value=""> </option>
								<%  
   									if(rsdvkd != null)
   									{
   										while(rsdvkd.next())
   										{
   											if(rsdvkd.getString("pk_seq").equals(obj.getdvkdId())){ %>
   												<option value="<%= rsdvkd.getString("pk_seq") %>" selected="selected" ><%= rsdvkd.getString("ten") %></option>
   											<%} else { %> 
   												<option value="<%= rsdvkd.getString("pk_seq") %>"><%= rsdvkd.getString("ten") %></option>
   											<%}
   										}
   										rsdvkd.close();
   									}
   								%>
                           </select>
                        </TD>                        
                    </TR> 
              -->                   	
       <% // if(!obj.getView().equals("TT")){ %>	                                      
          <!--          <TR>
                        <TD class="plainlabel" valign="middle" >Chọn khách hàng </TD>
                        <TD class="plainlabel" valign="middle" >
                         <a href="" id="khId" rel="subcontentKh">
	           	 		 	&nbsp; <img alt="Chọn khách hàng" src="../images/vitriluu.png"></a>
	           	 		  <DIV id="subcontentKh" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B; background-color: white; width: 760px; padding: 4px; max-height: 200px; overflow: auto; ">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="70px">Mã khách hàng</th>
				                            <th width="300px">Tên khách hàng</th>
				                            <th width="50px" align="center">Chọn <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
				                        </tr>
		                            	<%
			                        		if( RsKh!= null)
			                        		{
			                        			while(RsKh.next())
			                        			{ 
			                        				%>
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= RsKh.getString("ma") %>" readonly="readonly"></td>
			                        				<td><input style="width: 100%" value="<%= RsKh.getString("ten") %>" readonly="readonly"></td>
			                        				<td align="center">
			                        				<% if(obj.GetKhId().indexOf(RsKh.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="khIds" checked="checked" value="<%= RsKh.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="khIds" value="<%= RsKh.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } RsKh.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentKh')">Hoàn tất</a>
				                     </div>
	  				 </DIV> 
                        </TD>                        
                    </TR> -->
          <% //} %>          
              <!--       <TR>
                        <TD class="plainlabel" valign="middle" colspan = 2>                         
                        <% if(obj.gettype().equals("1")){ %>
                        <input type="checkbox" checked="checked"  value="1" name ="kh_khongcosolieu" id= "kh_khongcosolieu" >  
                        <label  id="khno" for="kh_khongcosolieu">   Bao gồm  khách hàng không phát sinh số liệu  </label> 
                        <%}else{ %>
                        <input type="checkbox"  name ="kh_khongcosolieu" value="1" id= "kh_khongcosolieu" >  
                        <label  id="khno" for="kh_khongcosolieu">   Bao gồm  khách hàng không phát sinh số liệu  </label>
                        <%} %>
                        </TD>                       
                         
                    </TR>  
              --> 
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
dropdowncontent.init('nkhId', "right-bottom", 300, "click");
dropdowncontent.init('khId', "right-bottom", 300, "click");
dropdowncontent.init('nspId', "right-top", 300, "click");
</script>
</html>
<%
	if(RsNkh != null) RsNkh.close();
	if(RsKh != null) RsKh.close();

	if(rsdvkd != null) rsdvkd.close();
	if(ctyRs != null) ctyRs.close();

	obj.DBclose();%>