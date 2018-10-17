<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.baocao.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@page import="geso.traphaco.erp.servlets.baocao.HangXuatKhoObj"%>

<% IBaocao obj = (IBaocao)session.getAttribute("obj"); %>
<% ResultSet lspRs = (ResultSet)obj.getLoaiSanPhamRs(); %>
<% ResultSet clRs = (ResultSet)obj.getChungloaiRs(); %>
<% ResultSet spRs = (ResultSet)obj.getSanPhamRs(); %>
<% ResultSet khoRs = (ResultSet)obj.getKhoRs(); %>
<% ResultSet khonhanRs = (ResultSet)obj.getKhonhanRs(); %>
<% ResultSet ndxRs = (ResultSet)obj.getNdxuatRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>

<% List<HangXuatKhoObj> listXNT = (List<HangXuatKhoObj>)session.getAttribute("ListXNT");  %>
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
	 
	 function locsanpham()
	 {   
		 document.forms["erpDmhForm"].action.value = "search";
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
		 var spIds = document.getElementsByName("ndxIds");
		 
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
<form name="erpDmhForm" method="post" action="../../ErpBCHangxuatSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="userTen" value="<%= userTen %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="khoTen" id="khoTen" value="" >

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:60%; padding:5px; float:left" class="tbnavigation">
        	Quản lý tồn kho > Báo cáo > Hàng xuất kho
        </div>
        <div align="right" style="padding:5px" class="tbnavigation">
        	Chào mừng Bạn <%= userTen %> &nbsp;
        </div>
    </div>
    <div align="left" style="width:100%; float:none; clear:left">
  		<fieldset>
    		<legend class="legendtitle"> Thông báo</legend>
    		<textarea name="dataerror" style="width: 100%;color: red; background-color: white; " readonly="readonly" rows="1" readonly="readonly"><%= obj.getMsg() %></textarea>
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
                        <TD class="plainlabel" valign="middle" >Kho xuất</TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="khoId" id="khoId"  onchange="locsanpham()" style="width: 400px;">
                            <option value=""></option>
                            	<%
	                        		if(khoRs != null)
	                        		{
	                        			while(khoRs.next())
	                        			{  
	                        			if( khoRs.getString("pk_seq").equals(obj.getKhoIds())){ %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" selected="selected" ><%= khoRs.getString("khoTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khoRs.getString("pk_seq") %>" ><%= khoRs.getString("khoTen") %></option>
	                        		 <% } } khoRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    
                      <TR>
                        <TD class="plainlabel" valign="middle" >Kho nhận</TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="khonhanId" id="khonhanId"  onchange="locsanpham()" style="width: 400px;">
                            <option value=""></option>
                            	<%
	                        		if(khonhanRs != null)
	                        		{
	                        			while(khonhanRs.next())
	                        			{  
	                        			if( khonhanRs.getString("pk_seq").equals(obj.getKhonhanIds())){ %>
	                        				<option value="<%= khonhanRs.getString("pk_seq") %>" selected="selected" ><%= khonhanRs.getString("khoTen") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khonhanRs.getString("pk_seq") %>" ><%= khonhanRs.getString("khoTen") %></option>
	                        		 <% } } khonhanRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    
                    
                    <TR>
                        <TD class="plainlabel" valign="middle" width="15%">Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                            <select name="loaisanpham" onchange="locsanpham()" style="width: 400px;">
                            	<option value=""></option>
                            	<%
	                        		if(lspRs != null)
	                        		{
	                        			while(lspRs.next())
	                        			{  
	                        			if( lspRs.getString("pk_seq").equals(obj.getLoaiSanPhamIds())){ %>
	                        				<option value="<%= lspRs.getString("pk_seq") %>" selected="selected" ><%= lspRs.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= lspRs.getString("pk_seq") %>" ><%= lspRs.getString("ten") %></option>
	                        		 <% } } lspRs.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR> 
                    
                   
                    <TR style="display: none">
                        <TD class="plainlabel" valign="middle" >Chọn chủng loại </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="clId" rel="subcontentCl">
	           	 							&nbsp; <img alt="Thông tin chủng loại" src="../images/vitriluu.png"></a>
	           	 		
                                           
                        </TD>                        
                    </TR>   
                    <!-- <TR>
                        <TD class="plainlabel" valign="middle" >Chọn sản phẩm </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="spId" rel="subcontentSp">
	           	 							&nbsp; <img alt="Thông tin sản phẩm" src="../images/vitriluu.png"></a>
	           	 		
                                      
                        </TD>                        
                    </TR> -->
                    
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
                        <TD class="plainlabel" valign="middle" >Nội dung xuất </TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <a href="" id="ndxId" rel="subcontentNdx">
	           	 							&nbsp; <img alt="Nội dung xuất" src="../images/vitriluu.png"></a>
	           	 		 
                                      
                        </TD>                        
                    </TR>
                    
                    
                     <TR>
                        <TD class="plainlabel" valign="middle" >Loại báo cáo</TD>
                        <TD class="plainlabel" valign="middle" >
                         
                         <select name="loaibaocao" id="loaibaocao" style="width: 400px;" onchange="locsanpham();" >
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
                     <tr >
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
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentCl')" onclick="locsanpham()" >Hoàn tất</a>
				                     </div>
				            </DIV>      
 					   </div>  
           <%-- 	 <DIV id="subcontentSp" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
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
				            <DIV id="subcontentNdx" style="position:absolute; visibility: hidden; border: 9px solid #80CB9B;
				                             background-color: white; width: 590px; height:300px; overflow-y:scroll; padding: 4px;">
				                    <table width="90%" align="center">
				                        <tr>
				                            <th width="10px">STT</th>
				                            <th width="350px">Nội dung xuất</th>
				                            <th width="100px" align="center">Chọn hết <input type="checkbox" onchange="sellectAll3()" id="chkAll3" ></th>
				                        </tr>			                    			
			                        			<%
			                        		if(ndxRs != null)
			                        		{
			                        			int i = 1;
			                        			while(ndxRs.next())
			                        			{  %>
			                        			
			                        			<tr>
			                        				<td><input style="width: 100%" value="<%=i%>"></td>
			                        				<td><input style="width: 100%" value="<%= ndxRs.getString("ten") %>"></td>
			                        				<td align="center">
			                                        <% if(obj.getNdxuatIds().indexOf(ndxRs.getString("pk_seq")) >= 0 ){ %>
			                        					<input type="checkbox" name="ndxIds" checked="checked" value="<%= ndxRs.getString("pk_seq") %>">
			                        				<%}else{ %>
			                        					<input type="checkbox" name="ndxIds" value="<%= ndxRs.getString("pk_seq") %>">
			                        				<%} %>
			                        			
			                        				</td>
			                        			</tr>
			                        			
			                        		 <% i++; }  ndxRs.close();   } %>
			                        			
				                    </table>
				                     <div align="right">
				                     	<label style="color: red" ></label>
				                     	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				                     	<a href="javascript:dropdowncontent.hidediv('subcontentNdx')">Hoàn tất</a>
				                     </div>
				            </DIV>  
				            <div>
				            	
				            </div>       
</div>

   <!-- phần dành cho hiển thị của web -->
    	<% if(ListXNT_check!=null){ %>
    	<div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Kết quả  </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
            	
					<TR class="tbheader">
	                    <TH align="center" width="4%">STT</TH>
	                    <TH align="center" width="5%">ID</TH>
	                    <TH align="center" width="7%">Kho xuất</TH>
	                 	<TH align="center" width="7%">Kho nhận</TH>  
	                 	<TH align="center" width="7%">Loại chứng từ</TH>
	                 	<TH align="center" width="6%">Số chứng từ</TH> 
	                 	<TH align="center" width="7%">Ngày chứng từ</TH> 
	                	<%if(loaibaocao.equals("1")) {%>	
	                	  <TH align="center" width="8%">Lý do </TH>
	                	<%} else {%>
	                	<TH align="center" width="8%">Lệnh sản xuất</TH>
	                	<TH align="center" width="8%">Thành phẩm sản xuất</TH>
	                	<TH align="center" width="8%">Lý do </TH>
	                 	<%} %>
	                    <TH align="center" width="8%">Loại sản phẩm</TH>
	                    <TH align="center" width="8%">Mã sản phẩm</TH>
	                    <TH align="center" width="10%" >Tên sản phẩm</TH>
	                    <TH align="center" width="4%" >ĐVT</TH>
	                    <TH align="center" width="6%" >Số lượng</TH>
	                    

	                </TR>
	                
	                <%
	                	if(listXNT!=null){
	                		for(int i=0; i< listXNT.size();i++){
	                			HangXuatKhoObj temp = listXNT.get(i);
	                			
	                			if((i % 2 ) == 0) {%>
	                         	<TR class='tbdarkrow'>
	                        <%}else{ %>
	                          	<TR class='tblightrow'>
	                        <%} %>
	                        	<TD align="center"><%=i +1 %></TD>
	                        	<TD align="left"><%=temp.getId() %></TD>
								<TD align="left"><%=temp.getMakhoxuat() %></TD>
								<TD align="left"><%=temp.getMakhonhan() %></TD>
								<TD align="left"><%=temp.getLoaichungtu() %></TD>
								<TD align="left"><%=temp.getMachungtu() %></TD>
								<TD align="left"><%=temp.getNgaychungtu()%></TD>
								
								
								<%if(loaibaocao.equals("1")) {%>	
									<TD align="left"><%=temp.getLydo()%></TD>
			                	<%} else {%>
			                	<TD align="left"><%= ( temp.getLenhsanxuat().equals("0"))? "":temp.getLenhsanxuat()%></TD>
			                	<TD align="left"><%=temp.getThanhphamsp()%></TD>
			                	<TD align="left"><%=temp.getLydo()%></TD>
			                 	<%} %>
		
								
								<TD align="center"><%=temp.getLoaisp()%></TD>
								<TD align="center"><%=temp.getMasp()%></TD>
								<TD align="left"><%=temp.getTensp()%></TD>
								<TD align="center"><%=temp.getDonvi()%></TD>
								<TD align="center"><%= formatter1.format(temp.getSoluong())  %></TD>
							
								
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
	dropdowncontent.init('ndxId', "right-bottom", 300, "click");
</script>
</html>

<%

try{
 
	if( lspRs!=null){
		lspRs.close();
		}
	if( clRs!=null){
		clRs.close();
		}
	if( spRs!=null){
		spRs.close();
		}
	if( khoRs!=null){
		khoRs.close();
		}
	if( ndxRs!=null){
		ndxRs.close();
		}
	session.setAttribute("obj", null) ; 
}catch(Exception er){
	
}
finally{
	obj.close();
}

%>
 