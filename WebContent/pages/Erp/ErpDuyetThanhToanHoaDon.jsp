<%@page import="geso.traphaco.center.util.Utility"%>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.thanhtoanhoadon.*" %>
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
	}else{
		IDuyetthanhtoanhoadon obj = (IDuyetthanhtoanhoadon)session.getAttribute("dtthdBean"); %>
<% ResultSet nvList = (ResultSet)obj.getNvRs() ;%>
<% ResultSet khList = (ResultSet)obj.getKhRs(); %>
<% ResultSet nccList = (ResultSet)obj.getNccList(); %>

<% ResultSet polist = (ResultSet)obj.getPoList(); %>
<% NumberFormat formatter = new DecimalFormat("#,###,###");  
	int[] quyen = new  int[5];
	quyen = util.Getquyen("ErpDuyetthanhtoanhoadonSvl","",userId);
	
	obj.setNextSplittings();
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

    <script type="text/javascript" src="../scripts/jquery.min.js"></script>
	<script type="text/javascript" src="../scripts/speechbubbles.js"></script>
	<script type="text/javascript" src="../scripts/dropdowncontent.js"></script>
	<script type="text/javascript" src="../scripts/jquery.autocomplete.js"></script>
	
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
	function replaces()
	{
		var ncc = document.getElementsByName("ncc");
		var nccId = document.getElementsByName("nccId");
		var nccId_old = nccId[0].value;
		var nccId_new = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")))
			
		if(nccId_old != nccId_new){
			nccId[0].value = ncc[0].value.substring(0, parseInt(ncc[0].value.indexOf(" - ")));
			document.forms["erpDmhForm"].submit();
		}
			
		setTimeout(replaces, 300);
	}
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
	 
	 function clearform()
	 {   
	    document.forms["erpDmhForm"].loaict.value = "";
	    document.forms["erpDmhForm"].nvId.value = "";
	  
	    document.forms["erpDmhForm"].ngayghinhan.value = "";
	    document.forms["erpDmhForm"].maTTHD.value = "";
	    document.forms["erpDmhForm"].nccId.value = "";
	    document.forms["erpDmhForm"].submit();
	 }

	 function duyetform(id, loaiid){
	    document.forms["erpDmhForm"].Id.value = id;
	    document.forms["erpDmhForm"].LoaiId.value = loaiid;
	    document.forms["erpDmhForm"].action.value = "duyet";
	    document.forms["erpDmhForm"].submit();
			 
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpDmhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpDmhForm"].msg.value);
	 }
	 

	</SCRIPT>
<link href="../css/select2.css" rel="stylesheet" />
<script src="../scripts/select2.js"></script>
<script>
     $(document).ready(function() { $("select").select2();  });
     
</script>
</head>
<body>
<form name="erpDmhForm" method="post" action="../../ErpDuyetthanhtoanhoadonSvl">
<input type="hidden" name="ctyId" value="<%= obj.getCtyId() %>" >
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="Id" value="" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>">
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>"> 
<input type="hidden" name="LoaiId" value="" >

<input type="hidden" name="action" value="1" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">Quản lý công nợ > Công nợ phải trả > Duyệt phiếu chi/ Uỷ nhiệm chi
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
                    	<TD class="plainlabel" valign="middle" >Mã phiếu chi/ UNC</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" name="maTTHD" id="maTTHD"  value="<%= obj.getMaTTHD() %>"   onchange="submitform()"/>
                    	 </TD>
                    	
                    	<TD class="plainlabel" valign="middle" >Ngày ghi nhận</TD>	
                    	<TD class="plainlabel" valign="middle" >
                    		<input type="text" class="days" name="ngayghinhan" id="ngayghinhan"  value="<%= obj.getNgayghinhan() %>" maxlength="10"  onchange="submitform()"/>
                    	 </TD>
                    </TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle" >Nhà cung cấp</TD>
                        <TD class="plainlabel" valign="middle" >
		                    <select name="nccId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(nccList != null)
	                        		{
	                        			while(nccList.next())
	                        			{  
	                        			  if( nccList.getString("pk_seq").equals(obj.getNccId() )){ %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" selected="selected" ><%= nccList.getString("tenncc") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nccList.getString("pk_seq") %>" ><%= nccList.getString("tenncc") %></option>
	                        		 <% } } nccList.close();
	                        		}
	                        	%>
                            </select>  
                        </TD>    
                        
                        <TD class="plainlabel" valign="middle" >Nhân viên</TD>
                        <TD class="plainlabel" valign="middle" >
		                    <select name="nvId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(nvList != null)
	                        		{
	                        			while(nvList.next())
	                        			{  
	                        			  if( nvList.getString("pk_seq").equals(obj.getNvId() )){ %>
	                        				<option value="<%= nvList.getString("pk_seq") %>" selected="selected" ><%= nvList.getString("tennv") %></option>
	                        			<%}else { %>
	                        				<option value="<%= nvList.getString("pk_seq") %>" ><%= nvList.getString("tennv") %></option>
	                        		 <% } } nvList.close();
	                        		}
	                        	%>
                            </select>  
                        </TD>                     
                    </TR>
                    
                    <TR>
                    	<%-- <TD class="plainlabel" valign="middle" >Khách hàng</TD>
                        <TD class="plainlabel" valign="middle" >
		                    <select name="khId" onchange="submitform()" style = "width:200px">
                            	<option value=""></option>
                            	<%
	                        		if(khList != null)
	                        		{
	                        			while(khList.next())
	                        			{  
	                        			  if( khList.getString("pk_seq").equals(obj.getKhId() )){ %>
	                        				<option value="<%= khList.getString("pk_seq") %>" selected="selected" ><%= khList.getString("tenkh") %></option>
	                        			<%}else { %>
	                        				<option value="<%= khList.getString("pk_seq") %>" ><%= khList.getString("tenkh") %></option>
	                        		 <% } } khList.close();
	                        		}
	                        	%>
                            </select>  
                        </TD>  --%>
                        
                         <TD class="plainlabel" valign="middle" >Loại chứng từ </TD> 
                        <TD class="plainlabel" valign="middle" colspan="3">
							<select name = "loaict" id= "loaict" style = "width:200px" onchange="submitform()">
								<%if(obj.getLoaiCT().equals("0")){ %>
								<option value=""></option>
								<option value="0" selected="selected">Phiếu chi</option>
								<option value="1">Ủy nhiệm chi</option>
								<option value="2">Phiếu thu</option>
								<%}else if(obj.getLoaiCT().equals("1")){ %>
								<option value=""></option>
								<option value="0">Phiếu chi</option>
								<option value="1" selected="selected">Ủy nhiệm chi</option>
								<option value="2">Phiếu thu</option>	
								<%}else if(obj.getLoaiCT().equals("2")){ %>
								<option value=""></option>
								<option value="0">Phiếu chi</option>
								<option value="1" >Ủy nhiệm chi</option>
								<option value="2" selected="selected">Phiếu thu</option>						
								<%} else { %>
								<option value="" selected="selected"></option>
								<option value="0">Phiếu chi</option>
								<option value="1" >Ủy nhiệm chi</option>
								<option value="2">Phiếu thu</option>		
								<%} %>
							</select>
                        </TD>  
                    </TR>
                    
                    <tr>
                        <td colspan="4" class="plainlabel">
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại </a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>
                </TABLE>  
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle">Duyệt phiếu chi/ Uỷ nhiệm chi </span>&nbsp;&nbsp;
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center" width="9%">Duyệt</TH>
	                    <TH align="center" width="9%">Mã phiếu chi/ UNC</TH>
	                    <TH align="center" width="20%">Nhà cung cấp/ Nhân viên/ Khách hàng</TH>	                    
	                    <TH align="center" width="10%">Tổng tiền</TH>
	                    <TH align="center" width="9%">Ngày</TH>
	                    <TH align="center" width="9%">Người tạo</TH>
	                    <TH align="center" width="9%">Loại chứng từ</TH>	     
	                    
	                </TR>
	                
		<%	try
			{
				int m = 0;
				String lightrow = "tblightrow";
				String darkrow = "tbdarkrow";
				String mhId = "";
				String mhId_bk = "";
				
				while(polist.next())
				{
							
					
					if (m % 2 != 0) { %>						
									<TR class= <%=lightrow%> >
							<%  } else { %>
									<TR class= <%= darkrow%> >
							<%  } %>
							<TD align = "center" > 
							
								<%if(quyen[4]!=0 && polist.getInt("isKTT") >= 0){ %>
								<a  href="javascript:duyetform(<%= polist.getString("TTHDID") %>, <%= polist.getString("LOAICTID") %>)">
								<img style="top: -4px;" src="../images/Chot.png" alt=""></a>&nbsp;&nbsp;&nbsp;&nbsp;
								<%} %>
								
								 <%if(quyen[0] != 0 ){ 
									 if(polist.getString("LOAICTID").equals("0")) {  // PHIEU CHI / UNC 
									     if(polist.getString("HTTT_FK").equals("100000")) { // PHIẾU CHI %>
				                		<A href = "../../ErpTTHoadonUpdateSvl?userId=<%=userId%>&update=<%= polist.getString("TTHDID") %>&duyetchi=1">
	                            		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
	                                 <% }else if(polist.getString("HTTT_FK").equals("100001")) { // ỦY NHIỆM CHI %>
	                                 	<A href = "../../ErpUynhiemchiUpdateSvl?userId=<%=userId%>&update=<%= polist.getString("TTHDID") %>&duyetchi=1">
	                            		<IMG src="../images/Edit20.png" alt="Cap nhat" title="Cập nhật" border=0></A>&nbsp;
	                                 <%}
								      } else { // PHIEU THU %>
								      <A href = "../../ErpThutienUpdateSvl?userId=<%=userId%>&update=<%=polist.getString("TTHDID")  %>">
								      <IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>&nbsp;
								  <%}} %>
							</TD>
							<TD align = "center"><%= polist.getString("SOCHUNGTU") %> </TD>
							<TD align = "left" ><%= polist.getString("TENDT")  %> </TD>
							<TD align = "right" ><%= formatter.format(Double.parseDouble(polist.getString("TONGTIENAVAT")))  %> </TD>
							<TD align = "center"><%= polist.getString("NGAYGHINHAN") %> </TD>
							<TD align = "center"><%= polist.getString("NGUOITAO") %> </TD>	
							<TD align = "center"><%= polist.getString("LOAICT") %> </TD>							
						</TR>			

		<%				
					

					m++;
				}
			}catch(Exception e){ e.printStackTrace();}%>
				<tr class="tbfooter">
									<TD align="center" valign="middle" colspan="13" class="tbfooter">
										<%if(obj.getNxtApprSplitting() >1) {%> <img alt="Trang Dau"
										src="../images/first.gif" style="cursor: pointer;"
										onclick="View('erpDmhForm', 1, 'view')"> &nbsp; <%}else {%>
										<img alt="Trang Dau" src="../images/first.gif"> &nbsp; <%} %>
										<% if(obj.getNxtApprSplitting() > 1){ %> <img alt="Trang Truoc"
										src="../images/prev.gif" style="cursor: pointer;"
										onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) -1, 'view')">
										&nbsp; <%}else{ %> <img alt="Trang Truoc"
										src="../images/prev_d.gif"> &nbsp; <%} %> <%
														int[] listPage = obj.getNextSplittings();
														for(int i = 0; i < listPage.length; i++){
													%> <% 
													
															System.out.println("Current page:" + obj.getNxtApprSplitting());
															System.out.println("List page:" + listPage[i]);
													
													if(listPage[i] == obj.getNxtApprSplitting()){ %> <a
										style="color: white;"><%= listPage[i] %>/ <%=obj.getTheLastSplitting() %></a>
										<%}else{ %> <a
										href="javascript:View('erpDmhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
										<%} %> <input type="hidden" name="list"
										value="<%= listPage[i] %>" /> &nbsp; <%} %> <% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
										&nbsp; <img alt="Trang Tiep" src="../images/next.gif"
										style="cursor: pointer;"
										onclick="View('erpDmhForm', eval(erpDmhForm.nxtApprSplitting.value) +1, 'view')">
										&nbsp; <%}else{ %> &nbsp; <img alt="Trang Tiep"
										src="../images/next_d.gif"> &nbsp; <%} %> <%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
										<img alt="Trang Cuoi" src="../images/last.gif"> &nbsp; <%}else{ %>
										<img alt="Trang Cuoi" src="../images/last.gif"
										style="cursor: pointer;"
										onclick="View('erpDmhForm', -1, 'view')"> &nbsp; <%} %>
									</TD>
								</tr>
	                
				</TABLE>
            </fieldset>
        </div>
    </div>  

</form>
<script type="text/javascript" src="../scripts/loadingv2.js"></script>
</body>
</html>
<% 
try{
if(polist!=null){
	polist.close();
}
obj.DBclose();
session.setAttribute("dtthdBean",null);
}catch(Exception er){
	
}
	}
%>