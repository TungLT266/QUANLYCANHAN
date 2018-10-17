<%@page import="geso.traphaco.center.util.Utility"%>
<%@page import="java.sql.SQLException"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.distributor.beans.thutienNPP.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "java.text.DecimalFormat" %>
<%@ page  import = "java.text.NumberFormat" %>


<% IErpThutienNPPList obj = (IErpThutienNPPList)session.getAttribute("obj"); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>
<% ResultSet nvgnList = (ResultSet)obj.getNvgnList(); %>
<% ResultSet tdvList = (ResultSet)obj.getTdvList();%>
<% ResultSet tthdList = (ResultSet)obj.getTThoadonList(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  


String sum = (String) session.getAttribute("sum");
Utility util = (Utility) session.getAttribute("util");
if(!util.check(userId, userTen, sum)){
	response.sendRedirect("/TraphacoHYERP/index.jsp");
}else{	
obj.setNextSplittings();   
/* int[] quyen = new  int[5];
quyen = util.Getquyen("114",userId); */
int[] quyen = new  int[6];
quyen = util.Getquyen("ErpThutienNPPSvl","",userId);

NumberFormat formatterNT = new DecimalFormat("#,###,###.##"); 
NumberFormat formatterVND = new DecimalFormat("#,###,###"); 
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

	    document.forms["erpDmhForm"].khId.value = "";
	    document.forms["erpDmhForm"].tungay.value = "";
	    document.forms["erpDmhForm"].denngay.value = "";
	    document.forms["erpDmhForm"].tdvId.value = "";
	    document.forms["erpDmhForm"].nvgnId.value = "";
	    document.forms["erpDmhForm"].maphieu.value = "";	  
	    document.forms["erpDmhForm"].sohoadon.value = "";
	    document.forms["erpDmhForm"].sophieunop.value = "";
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
	 
	 function xuatExcel() {
			document.forms['erpDmhForm'].action.value = 'excel';
			document.forms['erpDmhForm'].submit();
		}
	</SCRIPT>
</head>

<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>

<body>
<form name="erpDmhForm" method="post" action="../../ErpThutienNPPSvl">
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
    	<div style="width:55%; padding:5px; float:left" class="tbnavigation">
        	Quản lý công nợ > Thu tiền khách hàng
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
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="tungay" name="tungay" value="<%= obj.getTungay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Nhân viên giao nhận </TD>
                        <TD class="plainlabel">
                            <select name="nvgnId" id="nvgnId" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(nvgnList != null)
	                        		{
	                        			while(nvgnList.next())
	                        			{  
	                        			if( nvgnList.getString("pk_seq").equals(obj.getNvgnId())){ %>
	                        				<option value="<%= nvgnList.getString("pk_seq") %>" selected="selected" ><%= nvgnList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nvgnList.getString("pk_seq") %>" ><%= nvgnList.getString("ten") %></option>
	                        		 <% } } nvgnList.close();
	                        		}
	                        	%>
                            </select>
                          </TD>
                          
                        <TD class="plainlabel" >Trình dược viên </TD>
                        <TD class="plainlabel">
                            <select name="tdvId" id="tdvId" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(tdvList != null)
	                        		{
	                        			while(tdvList.next())
	                        			{  
	                        			if( tdvList.getString("pk_seq").equals(obj.getTdvId())){ %>
	                        				<option value="<%= tdvList.getString("pk_seq") %>" selected="selected" ><%= tdvList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= tdvList.getString("pk_seq") %>" ><%= tdvList.getString("ten") %></option>
	                        		 <% } } tdvList.close();
	                        		}
	                        	%>
                            </select>
                          </TD>
                    </TR>
                    <TR>
                        <TD class="plainlabel" >Mã phiếu </TD>
                        <TD class="plainlabel">
                            <input type="text"  id="maphieu" name="maphieu" value="<%= obj.getMaphieu() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                     
                        <TD class="plainlabel" valign="middle" >Khách hàng </TD>
                        <TD class="plainlabel" valign="middle">
                            <select name="khId" id="khId" style = "width:200px" onchange="submitform()">
                            	<option value=""></option>
                            	<%
	                        		if(nccList != null)
	                        		{
	                        			while(nccList.next())
	                        			{  
	                        			if( nccList.getString("pk_seq").equals(obj.getNccId())){ %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("ten") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("ten") %></option>
	                        		 <% } } nccList.close();
	                        		}
	                        	%>
                            </select>
                        </TD>                        
                    </TR>  
                    <TR>
                    	<TD class="plainlabel" >Số hóa đơn</TD>
                        <TD class="plainlabel" >
                            <input type="text"  id="sohoadon" name="sohoadon" value="<%= obj.getSoHoaDon() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                        <TD class="plainlabel" >Số phiếu nộp tiền</TD>
                        <TD class="plainlabel">
                            <input type="text"  id="sophieunop" name="sophieunop" value="<%= obj.getSophieunop()%>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                    <tr>
                        <td colspan="5" class="plainlabel">
                            <a class="button" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                            <!-- <a class="button3" href="javascript:xuatExcel()"> <img style="top: -4px;" src="../images/button.png" alt="">Xuất Excel</a> -->
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Thu tiền khách hàng&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
            	<%if(quyen[Utility.THEM]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a>
                 <%} %>          
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width = "6%">Mã phiếu</TH>
	                    <TH align="center" width = "7%">Ngày </TH>
	                    <TH align="center" width = "8%">Số phiếu nộp tiền </TH>
	                    <TH align="center" width = "10%">Người nộp tiền </TH>
	                    <TH align="center" width = "8%">Thực thu</TH>
	                    <TH align="center" width = "8%">Số tiền còn lại</TH>
	                    <TH align="center" width = "7%">Trạng thái</TH>
	                    <TH align="center" width = "8%"> Ngày sửa </TH>
	                    <TH align="center" width = "8%"> Người sửa </TH>
	                    <TH align="center" width = "9%">Tác vụ</TH>
	                </TR>
					<%
						if(tthdList != null)
						{
							try
							{
								int m = 0;
								while(tthdList.next())
								{
									if((m % 2 ) == 0) {%>
		                         		<TR class="tbdarkrow">
			                     <%}else{ %>
			                          	<TR class="tblightrow">
			                        <%} %>
	
									<TD align="center"><b><%= tthdList.getString("tthdId") %></b></TD>
									<TD align="center"><b><%= tthdList.getString("ngaychungtu") %></b></TD>
									<TD align="left"><b><%= tthdList.getString("noptienids") %></b></TD>
									<TD align="left"><b><%= tthdList.getString("tennguoinop") %></b></TD>
									
									<TD align="right">									
										<b><%= formatterVND.format(Double.parseDouble(tthdList.getString("THUCTHU"))) %></b>										
									</TD>
									<TD align="right">									
										<b><%= formatterVND.format(Double.parseDouble(tthdList.getString("SOTIENCONLAI"))) %></b>										
									</TD>
									<TD align="center">
										<%
											String trangthai = "";									    
											String tt = tthdList.getString("trangthai");
											if(tt.equals("0"))
												trangthai = "Chưa chốt";
											else
											{
												if(tt.equals("1"))
												{
													trangthai = "Đã chốt";
												}
												else
												{
													if(tt.equals("2"))
														trangthai = "Đã xóa";
													else
														trangthai = "Đã hủy";
												}
											}
										%>
										<b><%= trangthai %></b>
									</TD>
									<TD align="center"><b><%= tthdList.getString("ngaysua") %></b></TD>
									<TD align="center"><b><%= tthdList.getString("nguoisua") %></b></TD>
									<TD align="center"> 
				                    <% if(tt.equals("0")){ %>
				                    
				                    	 <%if(quyen[Utility.SUA]!=0){ %>
				                    	 <A href = "../../ErpThutienNPPUpdateSvl?userId=<%=userId%>&update=<%= tthdList.getString("tthdId") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;		                               
		                                 <%} %>
		                                
		                                <%if(quyen[Utility.CHOT]!=0){ %>
		                                 <A id='chotphieu<%=tthdList.getString("tthdId")%>'
							       			href=""><img
							       			src="../images/Chot.png" alt="Chốt thu tiền"
							       			width="20" height="20" title="Chốt thu tiền"
							      			border="0" onclick="if(!confirm('Bạn có chắc chốt phiếu thu tiền này?')) {return false ;}else{ processing('<%="chotphieu"+tthdList.getString("tthdId")%>' , '../../ErpThutienNPPSvl?userId=<%=userId%>&chot=<%= tthdList.getString("tthdId") %>');}"  >
										 </A>
										 <%} %>
										 
										 <%if(quyen[Utility.XOA]!=0){ %>
		                                <A href = "../../ErpThutienNPPSvl?userId=<%=userId%>&delete=<%= tthdList.getString("tthdId") %>"><img src="../images/Delete20.png" alt="Xóa thanh toán" title="Xóa thanh toán" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa phiếu thu tiền này?')) return false;"></A>								
				                   		<%} %>
				                   
				                    <%}else{ %>
				                    	<%if(quyen[Utility.XEM]!=0){ %>
				                    	<A href = "../../ErpThutienNPPUpdateSvl?userId=<%=userId%>&display=<%= tthdList.getString("tthdId") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>&nbsp; 
				                    	<%} %>
				                    	
				                    	 <%if(quyen[Utility.XOA]!=0){ %>
		                                <A href = "../../ErpThutienNPPSvl?userId=<%=userId%>&delete=<%= tthdList.getString("tthdId") %>"><img src="../images/Delete20.png" alt="Xóa thanh toán" title="Xóa thanh toán" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa phiếu thu tiền này?')) return false;"></A>								
				                   		<%} %>
				                    <%} %>
				                    </TD>
				                    </TR>
				                    
				                    <% 
				                    if( tthdList.getString("khachhangs")!= null)
				                    {
				                     String[] khachhangs = tthdList.getString("khachhangs").split(",");				                     
				                     for(int z = 0; z <khachhangs.length; z++) {       
			                           if((m % 2 ) == 0) {%>
		                         		<TR class="tbdarkrow">
			                           <%}else{ %>
			                          	<TR class="tblightrow">
			                         <%} %>
		                               <% if(z == 0) { %>
		                               		<TD align="right" colspan="2" rowspan="<%=khachhangs.length%>"><b></b></TD>                                    	
									   <% } %>
		                                    <TD align="left" colspan="8"><%= khachhangs[z] %> </TD>
			                           </TR>
		                               <% }
				                    }
								 m++;}
							}
							catch(SQLException ex){}
						}
					%>
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
<%
try{
	if( nccList!=null){
		nccList.close();
	}
	if(tdvList!=null){
		tdvList.close();
	}
	if(nvgnList!=null){
		nvgnList.close();
	}
	if(tthdList!=null){
		tthdList.close();
	}
	obj.DBclose(); 
	session.setAttribute("obj",null);
}catch(Exception er){
	
}

}%>
</form>
</body>
</html>