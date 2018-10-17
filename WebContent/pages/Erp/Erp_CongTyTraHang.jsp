<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.congtytrahang.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>

<% IErpCongtytrahangList obj = (IErpCongtytrahangList)session.getAttribute("obj"); %>
<% ResultSet dvthList = (ResultSet)obj.getDvthList(); %>
<% ResultSet dmhList = (ResultSet)obj.getDmhList(); %>
<% ResultSet lspList = (ResultSet)obj.getLoaisanpham(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen"); 

	String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/TraphacoHYERP/index.jsp");
	}else{
    NumberFormat formatter = new DecimalFormat("#,###,###");  %>
<% obj.setNextSplittings(); 
int[] quyen = new  int[5];
quyen = util.Getquyen("ErpCongtytrahangSvl","",userId);
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
	    document.forms["erpDmhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpDmhForm"].action.value = "Tao moi";
	    document.forms["erpDmhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].dvth.value = "";
	    document.forms["erpDmhForm"].tongtien.value = "";
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    document.forms["erpDmhForm"].loaisanpham.value="";
	    document.forms["erpDmhForm"].sodonmuahang.value="";
	    document.forms["erpDmhForm"].ncc.value="";
	    document.forms["erpDmhForm"].trangthai.value="";
	    document.forms["erpDmhForm"].submit();
	 }
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	 function processing(id,chuoi)
	 {
 	    document.getElementById(id).innerHTML = "<a href='#'><img src='../images/waiting.gif' width = '20' height = '20' title='cho thuc hien..' border='0' longdesc='cho thuc hien..' style='border-style:outset'> Proc...</a>"; 		  
 	 	document.getElementById(id).href=chuoi;
 	 }
	 
	</SCRIPT>
	<link href="../css/select2.css" rel="stylesheet" />
	<script src="../scripts/select2.js"></script>
	<script>
    	$(document).ready(function() { $("select").select2(); });
     
 	</script>	
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpCongtytrahangSvl">
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
        	Quản lý mua hàng > Nghiệp vụ khác > Đơn trả hàng NCC
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
                        <TD class="plainlabel" valign="middle" >Đơn vị thực hiện </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="dvth" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(dvthList != null)
	                        		{
	                        			while(dvthList.next())
	                        			{  
	                        			if( dvthList.getString("pk_seq").equals(obj.getDvthId())){ %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" selected="selected" ><%= dvthList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= dvthList.getString("pk_seq") %>" ><%= dvthList.getString("ten") %></option>
	                        		 <% } } dvthList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                        <TD class="plainlabel" valign="middle">Mã / Tên nhà cung cấp </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="ncc" value="<%= obj.getNCC() %>" onchange="submitform()">
                        </TD>                        
                    </TR>
                    <TR>
                        <TD class="plainlabel" valign="middle" >Loại sản phẩm </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="loaisanpham" onchange="submitform()" style="width: 200px">
                            	<option value=""></option>
                            	<%
	                        		if(lspList != null)
	                        		{
	                        			while(lspList.next())
	                        			{  
	                        			if( lspList.getString("pk_seq").equals(obj.getLoaisanphamid())){ %>
	                        				<option value="<%= lspList.getString("pk_seq") %>" selected="selected" ><%= lspList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= lspList.getString("pk_seq") %>" ><%= lspList.getString("ten") %></option>
	                        		 <% } } lspList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                        <TD class="plainlabel" valign="middle">Số đơn trả hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <input type="text" name="sodonmuahang" value="<%= obj.getSodonmuahang() %>" onchange="submitform()">
                        </TD>                        
                    </TR> 
                    <TR>
                  					  <TD class="plainlabel">Trạng thái</TD>
														<TD class="plainlabel" colspan="1" >
														<select id="trangthai" style="width: 200px" name="trangthai" onchange="submitform()" >
														 <option value=""></option>
														<%
														String[] mang=new String[]{  "-1","0", "1","2","3","4" };
							 								String[] mangten=new String[]{ "Chưa chốt","Chưa duyệt","Đã duyệt ","Đã xuất kho" , "Ðã xuất HD GTGT","Đã hủy"};
							 								
							 								 for(int j=0;j<mang.length;j++){
							 									 if(obj.getTrangthai().trim().equals(mang[j])){
							 									 %>
							 									 <option selected="selected" value="<%=mang[j]%>"> <%=mangten[j] %> </option>
							 									 <%
							 									 }else{
							 									 %>
							 									 <option value="<%=mang[j]%>"> <%=mangten[j] %> </option>
							 									 <% 
							 									 }
							 								 }
							 								 %>
																 
														</select>
														</TD>
                        <TD class="plainlabel" valign="middle">Tổng tiền </TD>
                        <TD class="plainlabel" valign="middle" >
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
            	<legend><span class="legendtitle"> Đơn mua hàng </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                           <%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="7%">Mã ĐTH</TH>
	                    <TH align="center" width="7%">Ngày</TH>
	                    <TH align="center" width="10%">Đơn vị thực hiện</TH>
	                    <TH align="center" width="15%">Nhà cung cấp</TH>
	                    <TH align="center" width="7%">Trạng thái</TH>
	                    <TH align="center" width="7%">Tổng tiền</TH>
	                    <TH align="center" width="7%">Ngày tạo</TH>
	                    <TH align="center" width="10%"> Người tạo </TH>
	                    <TH align="center" width="7%"> Ngày sửa </TH>
	                    <TH align="center" width="10%"> Người sửa </TH>
	                    <TH align="center" width="10%">Tác vụ</TH>
	                </TR>
					<%
                   		if(dmhList != null)
                   		{
                   			int m = 0;
                   			while(dmhList.next())
                   			{  	String tt = dmhList.getString("trangthai").trim();	
                   				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
				                    <TD align="center" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("dmhId") %></TD>
				                    <TD align="center" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("ngaymua") %></TD>
				                    <TD align="center" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("ten") %></TD>	
				                    <TD align="left" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("nccTen") %></TD>	
				                    <TD align="center" <%if(tt.equals("4")) {%> style="color: red" <%} %>>
				                    	<%
				                    		String trangthai = "";
				                    		
				                    		String dachot = dmhList.getString("dachot").trim();
				                    		
				                    		if(tt.equals("0")){
				                    				trangthai = "Chưa duyệt";
				                    				if(dachot.equals("1")){
				                    					trangthai += "(Đã chốt)";
				                    				}
				                    				else {
				                    					trangthai += "(Chưa chốt)";
				                    				}
				                    		}
				                    		else if(tt.equals("1")){
				                    				trangthai = "Đã duyệt";
				                    		}
			                    			else if(tt.equals("2")) {
			                    					trangthai = "Đã xuất kho";
			                    			}
			                    			else if(tt.equals("3")){
		                    						trangthai = "Ðã xuất HD GTGT";
		                    				 } 
		                    				else if(tt.equals("4")){
		                    						trangthai = "Đã hủy";
		                    				}
				                    		else {
				                    						trangthai = "NA";
				                    						}
				                    				 
				                    		 
				                    	%>
				                    	<%= trangthai %>
				                    </TD>									                                    
				                    <TD align="right" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%=formatter.format(dmhList.getDouble("tongtienAvat")) + dmhList.getString("TIENTE")%></TD>
				                    <TD align="center" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("ngaytao") %></TD>
				                    <TD align="left" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("nguoitao") %></TD>
				                    <TD align="center" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("ngaysua") %></TD>	
				                    <TD align="left" <%if(tt.equals("4")) {%> style="color: red" <%} %>><%= dmhList.getString("nguoisua") %></TD>				
				                    <TD align="center" <%if(tt.equals("4")) {%> style="color: red" <%} %>>
				                   
				                   
				                    <% if(tt.equals("0") && dachot.equals("0")){ %>
						                       <%if(quyen[2]!=0){ %>
				                               <A href = "../../ErpCongtytrahangUpdateSvl?userId=<%=userId%>&update=<%= dmhList.getString("dmhId") %>"><IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;		                                
				                               <%} %>
				                               <%if(quyen[3]!=0){ %>
				                               <A id='chotphieu<%=dmhList.getString("dmhId")%>'
											      href=""><img
											      src="../images/Chot.png" alt="Chốt"
											      width="20" height="20" title="Chốt" 
											      border="0" onclick="if(!confirm('Bạn có muốn chốt đơn trả hàng này?')) {return false ;}else{ processing('<%="chotphieu"+dmhList.getString("dmhId")%>' , '../../ErpCongtytrahangSvl?userId=<%=userId%>&chot=<%= dmhList.getString("dmhId")%>');}"  >
											   </A>
				                                 <%} %>  
		                                   <%if(quyen[1]!=0){ %>
		                                	<A href = "../../ErpCongtytrahangSvl?userId=<%=userId%>&delete=<%= dmhList.getString("dmhId") %>"><img src="../images/Delete20.png" width="20" height="20" border=0 
		                                					 alt="Xóa Quản lý mua hàng" title="Xóa đơn trả hàng" onclick="if(!confirm('Bạn có muốn xóa đơn đơn trả hàng này?')) return false;"></A>
		                                					 <%} %>	
		                           
		                            <% }   
				                    	else if(dachot.equals("1")){ %>
		                            
												<A href = "../../ErpCongtytrahangUpdateSvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>">
		                            			<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                            			
				                               <%if(quyen[3]!=0){ %>
				                               <A id='chotphieu<%=dmhList.getString("dmhId")%>'
											      href=""><img    src="../images/unChot.png" alt="Bỏ chốt" title="Bỏ chốt"
											      width="20" height="20" 
											      border="0" onclick="if(!confirm('Bạn có muốn bỏ chốt đơn trả hàng này?')) {return false ;}else{ processing('<%="chotphieu"+dmhList.getString("dmhId")%>' , '../../ErpCongtytrahangSvl?userId=<%=userId%>&unchot=<%= dmhList.getString("dmhId")%>');}"  >
											   </A>
				                                 <%} %>  
		                                  
				                    <%  }else { %>
		                            	<A href = "../../ErpCongtytrahangUpdateSvl?userId=<%=userId%>&display=<%= dmhList.getString("dmhId") %>">
		                            			<IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp;
		                            	<%} 								
		                            %>
				                    </TD>
				                </TR>
		                     <% m++; } dmhList.close(); } %>
						
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
</form><script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>
<%obj.DBclose();  
session.setAttribute("obj",null);

	} %>