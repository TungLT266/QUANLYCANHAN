
<%@page import="geso.traphaco.erp.servlets.baocao.HangNhapKhoObj"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.baocao.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>

<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaiSanPhamRs(); %>
<% ResultSet clRs = (ResultSet)obj.getChungloaiRs(); %>
<% ResultSet spRs = (ResultSet)obj.getSanPhamRs(); %>
<% ResultSet ndnRs = (ResultSet)obj.getNdnhapRs(); %>
<% ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% List<HangNhapKhoObj> listXNT = (List<HangNhapKhoObj>)session.getAttribute("ListXNT");  %>
<% String ListXNT_check = (String) session.getAttribute("ListXNT_check"); %>
<% String loaibaocao = (String) obj.getLoaibaocao(); %>
<% DecimalFormat formatter = new DecimalFormat("###,###,###"); %>
<% DecimalFormat formatter1 = new DecimalFormat("###,###.###"); %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Diageo - Project</title>
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
		 document.forms["erpDmhForm"].action.value = "submit";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 
	 function submitweb()
	 { 
		 if(document.getElementById("tungay").value == "")
		 {
			 alert('Vui lòng chọn thời gian bắt đầu lấy báo cáo');
			 return;
		 }
		 
		 if(document.getElementById("denngay").value == "")
		 {
			 alert('Vui lòng chọn thời gian kết thúc lấy báo cáo');
			 return;
		 }
		 
		 document.forms["erpDmhForm"].action.value = "web";
	     document.forms["erpDmhForm"].submit();
	 }
	 
	 
	 
	 
	 
	 function filter()
	 {   
		 document.forms["erpDmhForm"].action.value = "filter";
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
		 var spIds = document.getElementsByName("spIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAllkho()
	 {
		 var chkAll = document.getElementById("chkhoid");
		 var spIds = document.getElementsByName("khoids");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 
	 function sellectAll2()
	 {
		 var chkAll = document.getElementById("chkAll2");
		 var spIds = document.getElementsByName("clIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
			 }
		 }
	 }
	 function sellectAll3()
	 {
		 var chkAll = document.getElementById("chkAll3");
		 var spIds = document.getElementsByName("ndnIds");
		 
		 if(chkAll.checked)
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = true;
			 }
		 }
		 else
		 {
			 for(i = 0; i < spIds.length; i++)
			 {
				 spIds.item(i).checked = false;
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
<form name="erpDmhForm" method="post" action="../../ErpBCHangnhapSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	 Quản lý tồn kho > Báo cáo > Hàng nhập kho
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror" style="width: 100%;color: red" readonly="readonly" rows="1" readonly="readonly"><%= obj.getMsg() %></textarea>
		         <% obj.setMsg(""); %>
    	</fieldset>
  	</div>
  	<div id="cotent" style="width:100%; float:none">
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        <fieldset style="margin-top:5px" >
            <legend class="legendtitle"> Tiêu chí tìm kiếm</legend>
                <TABLE width="100%" cellpadding="6px" cellspacing="0px" style="margin-top: 5px " >								                          
                    <TR >
                        <TD class="plainlabel" width="15%">Từ ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTuNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                     <TR >
                        <TD class="plainlabel" width="15%" >Đến ngày </TD>
                        <TD class="plainlabel" >
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenNgay() %>" maxlength="10" />
                        </TD>
                    </TR>
                    
                    
                      <TR>
                        <TD class="plainlabel" valign="middle" >Chọn kho </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="khoid" rel="subcontentkhoid">
	           	 							&nbsp; <img alt="Chọn kho lấy báo cáo" src="../images/vitriluu.png" ></a>
	           	 		
                                          
                        </TD>                        
                    </TR> 
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="loaisanpham" onchange="filter()" style="width: 400px;">
                            	<option value=""></option>
                            	<%
                            	try{	
	                        		if(lspRs != null)
	                        		{
	                        			while(lspRs.next())
	                        			{  
	                        			if( lspRs.getString("PK_SEQ").equals(obj.getLoaiSanPhamIds())){ %>
	                        				<option value="<%= lspRs.getString("PK_SEQ") %>" selected="selected" ><%= lspRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= lspRs.getString("PK_SEQ") %>" ><%= lspRs.getString("ten") %></option>
	                        		 <% } } lspRs.close();
	                        		} } catch(Exception e){e.printStackTrace();}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                   
                    <TR style="display: none">
                     
                        <TD class="plainlabel" valign="middle" > <label for="getpivot"> Xem theo Pivot Excel </label> 
                        <%if(obj.getPivot().trim().equals("1")){ %>
                         <input type="checkbox" value="1" checked="checked" id="getpivot" name="getpivot">  
                         <%}else{ %>
                           <input type="checkbox" value="1" name="getpivot" id="getpivot">  
                         <%} %>
                         </TD>
                        <TD class="plainlabel" valign="middle" >
                                  
                        </TD>                        
                    </TR>
                
                    
                    <TR style="display: none">
                        <TD class="plainlabel" valign="middle" >Chọn chủng loại </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="clId" rel="subcontentCl">
	           	 							&nbsp; <img alt="Thông tin chủng loại" src="../images/vitriluu.png" ></a>
	           	 		
                                          
                        </TD>                        
                    </TR>   
                   <!--  <TR>
                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="spId" rel="subcontentSp">
	           	 							&nbsp; <img alt="Thông tin sản phẩm" src="../images/vitriluu.png"></a>
	           	 		 
                                      
                        </TD>                        
                    </TR>
                     -->
                    
                     <TR>
                    	<TD class="plainlabel" valign="middle" >Sản phẩm </TD>
                     <TD class="plainlabel" valign="middle" ><select
									name="sanpham" id="sanpham" 
									style="width: 400px;" multiple>
										<option value=""></option>
										<%
											if (spRs != null) {
												while (spRs.next()) {
													if (obj.getSanPhamIds().contains(spRs.getString("pk_seq"))) {
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
                        <TD class="plainlabel" valign="middle" >Nội dung nhập </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="ndnId" rel="subcontentNdn">
	           	 							&nbsp; <img alt="Nội dung nhập" src="../images/vitriluu.png"></a>
	           	 		 
                                      
                        </TD>                        
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle" >Loại báo cáo</TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <select name="loaibaocao" id="loaibaocao" style="width: 400px;" onchange="filter();" >
                       		  	<%if(loaibaocao.equals("1")){ %>
									<option value="1" selected="selected"> Báo cáo tổng quát</option>
									<option value="2">Báo cáo chi tiết</option>
								<%} else if ( loaibaocao.equals("2")){ %>
									<option value="1" > Báo cáo tổng quát</option>
									<option value="2"  selected="selected">Báo cáo chi tiết</option>
								<%} else { %>
									<option value="1" > Báo cáo tổng quát</option>
									<option value="2" >Báo cáo chi tiết</option>
								<%} %>
	           	 		 </select>
                                      
                        </TD>                        
                    </TR>
                     
                     
                     
                     
                     <tr>
                        <td  class="plainlabel">
                            <a class="button" href="javascript:submitform();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Tạo báo cáo  </a>
                        </td>
                        
                         <td  class="plainlabel">
                            <a class="button" href="javascript:submitweb();"> 
                            	<img style="top: -4px;" src="../images/button.png" alt=""> Xem trên web </a>
                        </td>
                    </tr> 
                    
                    </TABLE>                  
       </fieldset> 			                    
    	</div>
    	        <DIV id="subcontentkhoid" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã kho</th>
				                            <th width="350px">Tên kho </th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAllkho()" id="chkhoid" ></th>
				                        </tr>
		                            	<%
			                        		if(khoRs != null)
			                        		{	
			                        		
			                        			String [] khoIds = obj.getKhoIds().split(",");
			                        			while(khoRs.next())
			                        			{  
			                        						
			                        	%>
				
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= khoRs.getString("pk_seq") %>"></td>
			                        				<td><input style="width: 100%" value="<%= khoRs.getString("khoTen") %>"></td>
			                        				<td align="center">
			                        				<% if(obj.getKhoIds().indexOf(khoRs.getString("pk_seq")) >=0 ){ %>
			                        					<input type="checkbox" name="khoids" checked="checked" value="<%= khoRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="khoids" value="<%= khoRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } khoRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentkhoid')" onclick="filter()" >Hoàn tất</a>
				                     </div>
				            </DIV>    
				            <DIV id="subcontentCl" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã CL</th>
				                            <th width="350px">Tên </th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll2()" id="chkAll2" ></th>
				                        </tr>
		                            	<%
			                        		if(clRs != null)
			                        		{
			                        			while(clRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= clRs.getString("pk_seq") %>"></td>
			                        				<td><input style="width: 100%" value="<%= clRs.getString("ten") %>"></td>
			                        				<td align="center">
			                        				<% if(obj.getChungloaiIds().indexOf(clRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="clIds" checked="checked" value="<%= clRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="clIds" value="<%= clRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } clRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCl')" onclick="filter()" >Hoàn tất</a>
				                     </div>
				            </DIV>               
  
  
    </div>  
      			<%--  <DIV id="subcontentSp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="100px">Mã sản phẩm</th>
				                            <th width="350px">Tên sản phẩm</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll()" id="chkAll" ></th>
				                        </tr>
		                            	<%
			                        		if(spRs != null)
			                        		{
			                        			while(spRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%= spRs.getString("ma") %>"></td>
			                        				<td><input style="width: 100%" value="<%= spRs.getString("ten") %>"></td>
			                        				<td align="center">
			                        				<% if(obj.getSanPhamIds().indexOf(spRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="spIds" checked="checked" value="<%= spRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="spIds" value="<%= spRs.getString("pk_seq") %>">
			                        				<%} %>
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% } spRs.close(); } %>
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentSp')">Hoàn tất</a>
				                     </div>
				            </DIV>
				             --%>
				             <DIV id="subcontentNdn" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="10px">STT</th>
				                            <th width="350px">Nội dung nhập</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll3()" id="chkAll3" ></th>
				                        </tr>			                    			
			                        			<%
			                        		if(ndnRs != null)
			                        		{
			                        			int i = 1;
			                        			while(ndnRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%=i%>"></td>
			                        				<td><input style="width: 100%" value="<%= ndnRs.getString("ten") %>"></td>
			                        				<td align="center">
			                                        <% if(obj.getNdnhapIds().indexOf(ndnRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="ndnIds" checked="checked" value="<%= ndnRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="ndnIds" value="<%= ndnRs.getString("pk_seq") %>">
			                        				<%} %>
			                        			
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% i++; }  ndnRs.close();   } %>
			                        			
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentNdn')">Hoàn tất</a>
				                     </div>
				            </DIV>
</div>
    
    
    
    <!-- phần dành cho hiển thị của web -->
    	<% if(ListXNT_check!=null){ %>
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Kết quả  </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
            	
					<TR class="tbheader">
	                    <TH align="center" width="5%">STT</TH>
	                    <TH align="center" width="8%">Mã kho</TH>
	                 	<TH align="center" width="15%">Tên kho</TH>  
	                    <TH align="center" width="10%">Loại sản phẩm</TH>
	                    <TH align="center" width="10%">Mã sản phẩm</TH>
	                    <TH align="center" width="20%" >Tên sản phẩm</TH>
	                    <TH align="center" width="6%" >ĐVT</TH>
	                    <TH align="center" width="8%" >Số lượng</TH>
	                    <TH align="center" width="8%" >Đơn giá</TH>
	                    <TH align="center" width="10%" >Thành tiền</TH>

	                </TR>
	                
	                <%
	                	if(listXNT!=null){
	                		for(int i=0; i< listXNT.size();i++){
	                			HangNhapKhoObj temp = listXNT.get(i);
	                			
	                			if((i % 2 ) == 0) {%>
	                         	<TR class='tbdarkrow'>
	                        <%}else{ %>
	                          	<TR class='tblightrow'>
	                        <%} %>
	                        	<TD align="center"><%=i +1 %></TD>
								<TD align="left"><%=temp.getMakho() %></TD>
								<TD align="left"><%=temp.getTenkho() %></TD>
								<TD align="left"><%=temp.getLoaisp() %></TD>
								<TD align="left"><%=temp.getMasp() %></TD>
								<TD align="left"><%=temp.getTensp()%></TD>
								<TD align="center"><%=temp.getDonvi() %></TD>
								<TD align="center"><%= formatter1.format(temp.getSoluong())  %></TD>
								<TD align="center"><%= formatter1.format(temp.getDongia())  %></TD>
								<TD align="center"><%= formatter.format(temp.getThanhtien())  %></TD>
								
								
							</TR>	
	                		<%}
	                	}
	                %>
	                
	                
				</TABLE>
            </fieldset>
        </div>
        <%} %>
    
    

</form>

</body>
<script type="text/javascript">
	dropdowncontent.init('spId', "right-bottom", 300, "click");
	dropdowncontent.init('clId', "right-bottom", 300, "click");
	dropdowncontent.init('ndnId', "right-bottom", 300, "click");
	dropdowncontent.init('khoid', "right-bottom", 300, "click");
	
</script>
</html>
<% if(clRs != null) clRs.close(); %>
<% if(lspRs != null) lspRs.close(); %>
<% if(spRs != null) spRs.close(); %>
<% if(khoRs != null) khoRs.close(); %>
<% if(ndnRs != null) ndnRs.close(); %>

<%

try{
	
	session.removeAttribute("ListXNT");
	if(ListXNT_check!=null)
		session.removeAttribute("ListXNT_check");
}catch(Exception err){
	
}

%>

<% if(obj != null)  obj.close();obj=null;  

session.setAttribute("obj", null) ; 
%>



