<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page  import = "java.util.Iterator" %>
<%@ page  import = "java.util.List" %>
<%@ page  import = "java.util.ArrayList" %>
<%@ page  import = "geso.traphaco.erp.beans.huychungtu.*" %>
<%@ page  import = "java.sql.ResultSet" %>
<%@ page  import = "geso.traphaco.center.util.*" %>
<% IErpHuychungtubanhangList obj = (IErpHuychungtubanhangList)session.getAttribute("obj"); %>
<% ResultSet hctbhList = (ResultSet)obj.getHctBhRs(); %>
<% String userId = (String) session.getAttribute("userId");  %>
<% String userTen = (String) session.getAttribute("userTen");  %>
<% obj.setNextSplittings();
String sum = (String) session.getAttribute("sum");
	Utility util = (Utility) session.getAttribute("util");
	if(!util.check(userId, userTen, sum)){
		response.sendRedirect("/Diageo/index.jsp");
	}else{
		int[] quyen = new  int[5];
		quyen = util.Getquyen("ErpHuychungtuBhSvl","",userId);
		%>

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
	    document.forms["erpHctbhForm"].submit();
	 }
	 function newform()
	 {   
		document.forms["erpHctbhForm"].action.value = "Tao moi";
	    document.forms["erpHctbhForm"].submit();
	 }
	 function clearform()
	 {   
	    document.forms["erpHctbhForm"].tungay.value = "";
	    document.forms["erpHctbhForm"].denngay.value = "";
	    document.forms["erpHctbhForm"].trangthai.value = "";
	    document.forms["erpHctbhForm"].sochungtu.value = "";
	    document.forms["erpHctbhForm"].tennguoitao.value = "";
	    document.forms["erpHctbhForm"].submit();
	 }
	 
	 function thongbao()
	 {
		 var tt = document.forms["erpHctbhForm"].msg.value;
	 	 if(tt.length>0)
	     	alert(document.forms["erpHctbhForm"].msg.value);
	 	}
	</SCRIPT>
</head>
<body>
<form name="erpHctbhForm" method="post" action="../../ErpHuychungtuBhSvl">
<input type="hidden" name="userId" value="<%= userId %>" >
<input type="hidden" name="action" value="1" >
<input type="hidden" name="crrApprSplitting" value="<%= obj.getCrrApprSplitting() %>" >
<input type="hidden" name="nxtApprSplitting" value="<%= obj.getNxtApprSplitting() %>" >

<input type="hidden" name="msg" value='<%= obj.getMsg() %>'>
<script language="javascript" type="text/javascript">
    thongbao();
</script> 

<div id="main" style="width:100%; padding-left:2px">
	<div align="left" id="header" style="width:100%; float:none">
    	<div style="width:70%; padding:5px; float:left" class="tbnavigation">
        	Quản lý cung ứng > Quản lý bán hàng > Hủy chứng từ
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
                    </TR>
                     <TR>
                        <TD class="plainlabel" >Đến ngày </TD>
                        <TD class="plainlabel">
                            <input type="text" class="days" 
                                   id="denngay" name="denngay" value="<%= obj.getDenngay() %>" maxlength="10" onchange="submitform()" />
                        </TD>
                    </TR>
                      <TR>
						<TD class="plainlabel">Số chứng từ </TD>
						<TD class="plainlabel">
							<input   type="text" name="sochungtu" value="<%=obj.getSochungtu() %>" size="20" onchange="submitform();" >
						</TD>

					</TR>
                    
                    <TR>
						<TD class="plainlabel">Người tạo</TD>
						<TD class="plainlabel" colspan="3">
								<TABLE cellpadding="0" cellspacing="0">
									<TR>
										<TD><input   type="text" name="tennguoitao" value="<%=obj.getTennguoitao() %>" size="20" onchange="submitform();" ></TD>

									</TR>
								</TABLE>
						</TD>

					</TR>
                    
                     <TR>
                        <TD class="plainlabel" valign="middle">Trạng thái </TD>
                        <TD class="plainlabel" valign="middle">
                           <select name="trangthai" onChange="submitform();">
								<% if (obj.getTrangthai().equals("1")){%>
								  	<option value="1" selected>Đã xác nhận</option>
								  	<option value="2">Đã hủy</option>
								  	<option value=""> </option>								  
								  	
								<%}else{ if(obj.getTrangthai().equals("2")) { %>											
								 	<option value="2" selected>Đã hủy</option>										  	
								  	<option value="1" >Đã xác nhận</option>
								  	<option value="" ></option> 					  		  	
								<%} else { %>
									<option value="" ></option> 									  	
								  	<option value="1" >Đã xác nhận</option>
								  	<option value="2" >Đã hủy</option>	
								<% } } %>
                           </select>
                        </TD>                        
                    </TR>    
                    <tr>
                        <td colspan="2" class="plainlabel">
                            <a class="button" href="javascript:submitform()">
                                <img style="top: -4px;" src="../images/Search30.png" alt="">Tìm kiếm </a>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
                            <a class="button2" href="javascript:clearform()">
                                <img style="top: -4px;" src="../images/button.png" alt="">Nhập lại</a>&nbsp;&nbsp;&nbsp;&nbsp;
                        </td>
                    </tr>        					
                </TABLE>                      
        </fieldset>                      
    	</div>
        <div align="left" style="width:100%; float:none; clear:left; font-size:0.7em">
        	<fieldset>
            	<legend><span class="legendtitle"> Hủy chứng từ </span>&nbsp;&nbsp;
            	<%if(quyen[0]!=0){ %>
                	<a class="button3" href="javascript:newform()">
                           <img style="top: -4px;" src="../images/New.png" alt="">Tạo mới </a><%} %>
                </legend>
            	<TABLE width="100%" border="0" cellspacing="1" cellpadding="4">
					<TR class="tbheader">
	                    <TH align="center">Số phiếu </TH>
	                    <TH align="center">Số chứng từ </TH>
	                    <TH align="center" style="display: none">Số chứng từ phát sinh</TH>
	                    <TH align="center">Trạng thái</TH>
	                    <TH align="center">Ngày tạo</TH>
	                    <TH align="left"> Người tạo </TH>
	                    <TH align="center"> Ngày sửa </TH>
	                    <TH align="left"> Người sửa </TH>
	                    <TH align="center" >Tác vụ</TH>
	                </TR>
					<%
                 		if(hctbhList != null)
                 		{
                 			int m = 0;
                 			while(hctbhList.next())
                 			{  		
                 				if((m % 2 ) == 0) {%>
		                         	<TR class='tbdarkrow'>
		                        <%}else{ %>
		                          	<TR class='tblightrow'>
		                        <%} %>
		                    <TD align="center"><%= hctbhList.getString("SOPHIEU") %></TD>
		                    <TD align="center"><%= hctbhList.getString("SOCHUNGTU") %></TD>
		                    <TD align="center" style="display: none"><%= hctbhList.getString("SOCHUNGTUPHATSINH") %></TD>	
		                    <TD align="center">
		                    	<%
		                    		String trangthai = "";
		                    		String tt = hctbhList.getString("trangthai");
		                    		if(tt.equals("0"))
		                    			trangthai = "Chưa chốt";
		                    		else
		                    		{
		                    			if(tt.equals("1"))
		                    				trangthai = "Đã xác nhận";
		                    			else
		                    			{
			                    			trangthai = "Đã hủy";
		                    			}
		                    		}
		                    	%>
		                    	<%= trangthai %>
		                    </TD>									                                    
		                    <TD align="center"><%= hctbhList.getString("ngaytao") %></TD>
		                    <TD align="left"><%= hctbhList.getString("nguoitao") %></TD>
		                    <TD align="center"><%= hctbhList.getString("ngaysua") %></TD>
		                    <TD align="left"><%= hctbhList.getString("nguoisua") %></TD>		
		                    <TD align="center"> 
		                    
		                  <% if(tt.equals("0"))
		                  	 { 
		                     	if(quyen[Utility.SUA]!=0){ %>
									  <A href = "../../ErpHuychungtuBhUpdateSvl?userId=<%=userId%>&update=<%= hctbhList.getString("SOPHIEU") %>"><IMG src="../images/Edit20.png" alt="Cập nhật" title="Cập nhật" border=0></A>
                          		<% 	} 
                          		if(quyen[Utility.CHOT]!=0){ %>
                                	<A href = "../../ErpHuychungtuBhSvl?userId=<%=userId%>&chot=<%= hctbhList.getString("SOPHIEU") %>"><img src="../images/Chot.png" alt="Duyệt" title="Duyệt" width="20" height="20" border=0 ></A>&nbsp;
                              	<%} %>
                              	
                              	<%if(quyen[Utility.XOA]!=0){ %>
                              		<A href = "../../ErpHuychungtuBhSvl?userId=<%=userId%>&delete=<%=hctbhList.getString("SOPHIEU") %>"><img src="../images/Delete20.png" alt="Xóa hóa đơn" title="Xóa hóa đơn" width="20" height="20" border=0 onclick="if(!confirm('Bạn có muốn xóa phiếu này?')) return false;"></A>									
		                  		<%} %>	
                              	
		                  <%   } %>
		                     
		                                             
	                      <%if(quyen[3]!=0){ %>
	                    	<A href = "../../ErpHuychungtuBhUpdateSvl?userId=<%=userId%>&display=<%= hctbhList.getString("SOPHIEU") %>"><IMG src="../images/Display20.png" alt="Hiển thị" title="Hiển thị" border=0></A>
	                      <%} %>
		                    </TD>
		                </TR>
                     <% m++; } hctbhList.close(); } %>
					<tr class="tbfooter" > 
						 <TD align="center" valign="middle" colspan="13" class="tbfooter">
						 	<%if(obj.getNxtApprSplitting() >1) {%>
								<img alt="Trang Dau" src="../images/first.gif" style="cursor: pointer;" onclick="View('erpHctbhForm', 1, 'view')"> &nbsp;
							<%}else {%>
								<img alt="Trang Dau" src="../images/first.gif" > &nbsp;
								<%} %>
							<% if(obj.getNxtApprSplitting() > 1){ %>
								<img alt="Trang Truoc" src="../images/prev.gif" style="cursor: pointer;" onclick="Prev('erpHctbhForm', 'prev')"> &nbsp;
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
								<a href="javascript:View('erpHctbhForm', <%= listPage[i] %>, 'view')"><%= listPage[i] %></a>
							<%} %>
								<input type="hidden" name="list" value="<%= listPage[i] %>" />  &nbsp;
							<%} %>
							
							<% if(obj.getNxtApprSplitting() < obj.getTheLastSplitting()){ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next.gif" style="cursor: pointer;" onclick="Next('erpHctbhForm', 'next')"> &nbsp;
							<%}else{ %>
								&nbsp; <img alt="Trang Tiep" src="../images/next_d.gif" > &nbsp;
							<%} %>
							<%if(obj.getNxtApprSplitting() == obj.getTheLastSplitting()) {%>
						   		<img alt="Trang Cuoi" src="../images/last.gif" > &nbsp;
					   		<%}else{ %>
					   			<img alt="Trang Cuoi" src="../images/last.gif" style="cursor: pointer;" onclick="View('erpHctbhForm', -1, 'view')"> &nbsp;
					   		<%} %>
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
if(obj!=null)
{
	obj.DBclose();
	session.setAttribute("obj",null);
}
	
	
	
	}%>